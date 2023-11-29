package com.nailton.managerpassword.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "passwords")
public class Passwords {

    @Id
    private final UUID id;

    private String applicationName;
    
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Passwords() {
        this.id = UUID.randomUUID();
    }

    public Passwords(String applicationName, String password) {
        this.id = UUID.randomUUID();
        this.password = password;
        this.applicationName = applicationName;
    }

    public UUID getId() {
        return id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getPassword() {
        return password;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
