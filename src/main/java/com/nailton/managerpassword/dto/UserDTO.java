package com.nailton.managerpassword.dto;

import com.nailton.managerpassword.models.User;

public record UserDTO(String name, String email, String password) {
    public User toEntity() {
        return new User(name, email, password);
    }
}
