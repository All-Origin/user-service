package com.junior.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequestDto {
    private String name;
    @NotBlank
    @Size(min = 4)
    private String userName;
    @Size(min = 4, max = 20)
    private String password;
    //@Email
    // @NotBlank
    private String email;
}
