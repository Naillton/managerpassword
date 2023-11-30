package com.nailton.managerpassword.controllers;

import com.nailton.managerpassword.configuration.util.UserMiddleware;
import com.nailton.managerpassword.dto.UserDTO;
import com.nailton.managerpassword.models.User;
import com.nailton.managerpassword.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        try {
            List<User> users = this.userService.getUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        try {
            User user = this.userService.getUserById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<String> updateUser(@RequestHeader(
            HttpHeaders.AUTHORIZATION) String token, @PathVariable UUID id, @RequestBody UserDTO userDTO) {
        User user = this.userService.getUserById(id);
        String responseT = this.userService.validateToken(token);
        String responseError = "User Not Found";
        String responseTError = "Invalid Credentials";
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
        } else if(!Objects.equals(user.getEmail(), responseT)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseTError);
        } else {
            try {
                String errorCamps = "Invalid Camps";
                boolean isTrue = UserMiddleware.validCredentials(userDTO.name(), userDTO.email(), userDTO.password());
                if (!isTrue) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorCamps);
                }
                this.userService.updateUser(userDTO.toEntity(), id);
                String correct = "User Updated";
                return ResponseEntity.status(HttpStatus.OK).body(correct);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
    }
}
