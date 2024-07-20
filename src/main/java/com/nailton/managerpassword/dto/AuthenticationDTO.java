package com.nailton.managerpassword.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public record AuthenticationDTO(String email, String password) {
}
