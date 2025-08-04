package com.brainz.user_service.service.impl;

import com.brainz.user_service.dto.LoginRequestDto;
import com.brainz.user_service.dto.RegistrationRequestDto;
import com.brainz.user_service.dto.UserDto;
import com.brainz.user_service.dto.UserValidationResponse;
import com.brainz.user_service.entity.User;
import com.brainz.user_service.enums.Roles;
import com.brainz.user_service.repository.UserRepository;
import com.brainz.user_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }
    @Override
    public UserDto register(RegistrationRequestDto requestDto) {
       // for now doing manually then will use generic service;
        try {
            // try to communicat with db
            // check if already contains
            User existingUser = userRepository.findByUserName(requestDto.getUserName());
            if (existingUser != null) {
                log.error("Registration failed: userName already exists: {}", requestDto.getUserName());
                throw new RuntimeException("Username already exists!");
            }
            // Create user using Builder
            List<Roles> roleList = new ArrayList<>();
            roleList.add(Roles.ROLE_USER);
            User user = User.builder()
                    .userName(requestDto.getUserName())
                    .email(requestDto.getEmail())
                    .passwordHash(passwordEncoder.encode(requestDto.getPassword())) // Use PasswordEncoder here!
                    .roles(roleList)  // Or dynamic if needed
                    .enabled(true)
                    .build();
            userRepository.save(user);
            // Map back to UserDto (use a mapper later)
            return UserDto.builder()
                    .userName(user.getUserName())
                    .email(user.getEmail())
                    .roles(user.getRoles())
                    .build();

        }catch (Exception e){
            log.error("Registration failed for user: {}", requestDto.getUserName(), e);
            throw new RuntimeException();
        }
    }
    @Override
    public UserValidationResponse validate(LoginRequestDto requestDto) {
        User user = userRepository.findByUserName(requestDto.getUsername());

        if (user == null) {
            log.warn("Login failed for username: {}", requestDto.getUsername());
            throw new RuntimeException("Invalid username or password.");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPasswordHash())) {
            log.warn("Login failed: invalid password for user {}", requestDto.getUsername());
            throw new RuntimeException("Invalid username or password.");
        }

        // Success
        return UserValidationResponse.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }
}
