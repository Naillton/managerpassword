package com.nailton.managerpassword.controllers;

import com.nailton.managerpassword.configuration.util.UserMiddleware;
import com.nailton.managerpassword.dto.PasswordDTO;
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

    private final String responseUserError = "User Not Found";

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
        String responseTError = "Invalid Credentials";
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseUserError);
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

    @PutMapping(value = "/update/passwords")
    public ResponseEntity<String> updatePass(@RequestHeader(
            HttpHeaders.AUTHORIZATION) String token, @RequestBody PasswordDTO passwordDTO) {
        String invalid = "Invalid Camps";
        boolean isTrue = UserMiddleware.validPass(passwordDTO.app(), passwordDTO.password());
        if (!isTrue) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalid);
        } else {
            try {
                String email = this.userService.validateToken(token);
                String response = this.userService.updateListPassword(email, passwordDTO.toEntity());
                if (Objects.equals(response, "User Not Exist")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @DeleteMapping(value = "/deleteall")
    public ResponseEntity<String> deleteAllUsers() {
        String deleted = "Users Deleted";
        try {
            this.userService.delAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(deleted);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteUserById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable UUID id) {
        User user = this.userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseUserError);
        } else {
            String email = this.userService.validateToken(token);
            String deleted = "User: " + email + " Deleted";
            try {
                this.userService.deleteUserById(id);
                return ResponseEntity.status(HttpStatus.OK).body(deleted);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }

    }
}
