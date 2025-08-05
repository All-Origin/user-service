package com.brainz.user_service.service;

import com.brainz.user_service.entity.User;
import com.brainz.user_service.enums.Roles;
import com.brainz.user_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StartupAdminInitializer {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${admin.userName:admin}")
    private String defaultAdminUsername;
    @Value("${admin.Name:BrainZ}")
    private String defaultName;
    @Value("${admin.password:admin123}")
    private String defaultAdminPassword;

    @PostConstruct
    public void initAdmin() {
        if (!userRepository.existsByUserName(defaultAdminUsername)) {
            User admin = new User();
            admin.setName(defaultName);
            admin.setUserName(defaultAdminUsername);
            admin.setPasswordHash(passwordEncoder.encode(defaultAdminPassword));
            admin.setRoles(List.of(Roles.ROLE_ADMIN));
            userRepository.save(admin);
            log.info("Admin user initialized.");
        } else {
            log.info("Admin user Exists!.");
        }
    }
}
