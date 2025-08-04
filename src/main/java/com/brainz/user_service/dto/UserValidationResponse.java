package com.brainz.user_service.dto;


import com.brainz.user_service.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserValidationResponse {
    private Long userId;
    private String userName;
    private String email;
    private List<Roles> roles;

}

