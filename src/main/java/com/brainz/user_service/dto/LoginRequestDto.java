package com.brainz.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "UserName Required")
    private String username; // or email
    @NotBlank(message = "Password is Required")
    @Size(min=6,message = "Password must be at least 6 characters")
    private String password;
}
