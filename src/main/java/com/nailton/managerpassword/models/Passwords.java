package com.nailton.managerpassword.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "passwords")
public class Passwords {

    @Id
    private final UUID id;

    private String app;
    
    private String password;

    public Passwords() {
        this.id = UUID.randomUUID();
    }

    public Passwords(String app, String password) {
        this.id = UUID.randomUUID();
        this.app = app;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getApplicationName() {
        return app;
    }

    public String getPassword() {
        return password;
    }

    public void setApplicationName(String applicationName) {
        this.app = applicationName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
