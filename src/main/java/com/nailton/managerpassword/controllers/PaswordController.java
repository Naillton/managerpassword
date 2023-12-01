package com.nailton.managerpassword.controllers;

import com.nailton.managerpassword.configuration.util.UserMiddleware;
import com.nailton.managerpassword.dto.PasswordDTO;
import com.nailton.managerpassword.models.Passwords;
import com.nailton.managerpassword.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/password")
public class PaswordController {

    @Autowired
    private PasswordService passwordService;

    @GetMapping()
    public ResponseEntity<List<Passwords>> getPasswords() {
        try {
            List<Passwords> pass = this.passwordService.getPass();
            return ResponseEntity.status(HttpStatus.OK).body(pass);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<String> updatePass(@PathVariable UUID id, @RequestBody PasswordDTO passwordDTO) {
        String invalid = "Invalid Camps";
        boolean isTrue = UserMiddleware.validPass(passwordDTO.app(), passwordDTO.password());
        if (!isTrue) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalid);
        } else {
            try {
                String response = this.passwordService.updatePass(id, passwordDTO.toEntity());
                if (Objects.equals(response, "Password Not Found")) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> delPassById(@PathVariable UUID id) {
        try {
            String response = this.passwordService.delPassById(id);
            if (Objects.equals(response, "Password Not Found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
