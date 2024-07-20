package com.nailton.managerpassword.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.nailton.managerpassword.models.User;

public record UserDTO(String name, String email, String password) {
    public User toEntity() {
        return new User(name, email, password);
    }
}
