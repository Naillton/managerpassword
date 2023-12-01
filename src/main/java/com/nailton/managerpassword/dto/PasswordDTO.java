package com.nailton.managerpassword.dto;

import com.nailton.managerpassword.models.Passwords;
import com.nailton.managerpassword.models.User;

public record PasswordDTO(String app, String password) {

    public Passwords toEntity() {
        return new Passwords(app, password);
    }
}
