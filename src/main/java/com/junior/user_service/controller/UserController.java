package com.junior.user_service.controller;

import com.junior.user_service.dto.LoginRequestDto;
import com.junior.user_service.dto.RegistrationRequestDto;
import com.junior.user_service.dto.UserDto;
import com.junior.user_service.dto.UserValidationResponse;
import com.junior.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Validated
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "http://localhost:8082")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
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
    public ResponseEntity<UserValidationResponse> validateUser(@Valid @RequestBody LoginRequestDto requestDto){
        UserValidationResponse response = userService.validate(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get User Info",
            description = "Accepts JWT token, extracts user ID (sub), and returns the UserDto"
    )
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        if (authentication == null || !(authentication instanceof JwtAuthenticationToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        }

        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        Long userId = Long.valueOf(jwtAuth.getToken().getSubject());
        UserDto response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/me")
    @Operation(summary = "Update current user")
    public ResponseEntity<UserDto> updateCurrentUser(
            Authentication authentication,
            @Valid @RequestBody UserDto userDto) {
        if (authentication == null || !(authentication instanceof JwtAuthenticationToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        }
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        Long userId = Long.valueOf(jwtAuth.getToken().getSubject());
        UserDto updated = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(updated);
    }

    //Todo: we have to validate user before accessing this in future not now!!
    @DeleteMapping("/me")
    @Operation(summary = "Delete current user")
    public ResponseEntity<String> deleteCurrentUser( Authentication authentication) {
       if(authentication==null || !(authentication instanceof JwtAuthenticationToken)){
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication required");
       }
       JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        Long userId = Long.valueOf(jwtAuthenticationToken.getToken().getSubject());
        String result = userService.deleteUser(userId);
        return ResponseEntity.ok(result);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/search")
    @Operation(summary = "Search users by username future: (supports pagination & future DSA/ElasticSearch)")
    public ResponseEntity<UserDto> searchUsers(
            @Valid
            @RequestParam String username)
            {

        // TODO: Replace this with paged search or ElasticSearch in future
        UserDto result = userService.searchUser(username);
        return ResponseEntity.ok(result);
    }

    }

