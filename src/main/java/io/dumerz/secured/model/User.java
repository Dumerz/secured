package io.dumerz.secured.model;

import java.time.Instant;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private Instant createdOn;
    private Instant updateOn;

    public User(int id, String username, String password, String email, Instant createdOn, Instant updateOn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdOn = createdOn;
        this.updateOn = updateOn;
    }
    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
    public Instant getUpdateOn() {
        return this.updateOn;
    }

    public void setUpdateOn(Instant updateOn) {
        this.updateOn = updateOn;
    }

}