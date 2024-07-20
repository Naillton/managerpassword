package com.nailton.managerpassword.controllers;

import com.nailton.managerpassword.configuration.util.UserMiddleware;
import com.nailton.managerpassword.dto.AuthenticationDTO;
import com.nailton.managerpassword.dto.UserDTO;
import com.nailton.managerpassword.models.User;
import com.nailton.managerpassword.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> insertUser(@RequestBody UserDTO userDTO) {
        User validUser = this.userService.findUserByEmail(userDTO.email());
        String exist = "User Already Exist";
        String invalid = "Invalid Camps";
        String created = "User Created";
        if (validUser != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exist);
        } else {
            boolean isTrue = UserMiddleware.validCredentials(userDTO.name(), userDTO.email(), userDTO.password());
            if (isTrue) {
                try {
                    this.userService.insertUser(userDTO.toEntity());
                    return ResponseEntity.status(HttpStatus.CREATED).body(created);
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(invalid);
            }
        }
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginUser(@RequestBody AuthenticationDTO authenticationDTO) {
        try {
            String response = this.userService.findUserByEmailAndPassword(authenticationDTO.email(), authenticationDTO.password());
            if (Objects.equals(response, "User Not Found")) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
