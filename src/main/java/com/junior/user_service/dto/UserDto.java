package com.junior.user_service.dto;

import com.junior.user_service.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String name;
    private String userName;
    private String email;
    private List<Roles> roles;
    private String createdAt;
    private String updatedAt;
}
