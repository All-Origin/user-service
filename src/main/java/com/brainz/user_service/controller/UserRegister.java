package com.brainz.user_service.controller;

import com.brainz.user_service.dto.LoginRequestDto;
import com.brainz.user_service.dto.RegistrationRequestDto;
import com.brainz.user_service.dto.UserDto;
import com.brainz.user_service.dto.UserValidationResponse;
import com.brainz.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserRegister {

    private final UserService userService;

    @Autowired
    public UserRegister(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register", description = "Register user with RegistrationRequestDto and provide UserDto")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegistrationRequestDto requestDto){
        UserDto userDto = userService.register(requestDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate", description = "Validate user with LoginRequestDto and provide UserValidationResponse")
    public ResponseEntity<UserValidationResponse> registerUser(@Valid @RequestBody LoginRequestDto requestDto){
        UserValidationResponse response = userService.validate(requestDto);
        return ResponseEntity.ok(response);
    }


}
