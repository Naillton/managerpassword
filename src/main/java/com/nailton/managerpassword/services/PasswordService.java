package com.nailton.managerpassword.services;

import com.nailton.managerpassword.models.Passwords;
import com.nailton.managerpassword.repositories.PasswordsRepository;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class PasswordService {

    @Autowired
    private PasswordsRepository passwordsRepository;

    public List<Passwords> getPass() {
        return (List<Passwords>) this.passwordsRepository.findAll();
    }

    public Passwords password(UUID id) {
        return this.passwordsRepository.findById(id).orElse(null);
    }

    public String updatePass(UUID id, Passwords password) {
        Passwords pass = this.password(id);
        if (pass == null) {
            return "Password Not Found";
        }
        pass.setApplicationName(password.getApplicationName());
        pass.setPassword(password.getPassword());
        this.passwordsRepository.save(pass);
        return "Password Updated";
    }

    public String delPassById(UUID id) {
        Passwords pass = this.password(id);
        if (pass == null) {
            return "Password Not Found";
        }
        this.passwordsRepository.deleteById(id);
        return "Password Deleted";
    }
}
