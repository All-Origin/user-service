package com.brainz.user_service.dto;


import com.brainz.user_service.enums.Roles;
import lombok.Builder;

import java.util.List;

@Builder
public class UserValidationResponse {
    private Long userId;
    private String userName;
    private String email;
    private List<Roles> roles;

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String username) { this.userName = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Roles> getRoles() { return roles; }
    public void setRoles(List<Roles> role) { this.roles = role; }
}

