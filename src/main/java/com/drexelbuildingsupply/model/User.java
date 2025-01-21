package com.drexelbuildingsupply.model;

public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean verified;
    private String verificationCode; // New field for verification code

    // Constructor
    public User(Long id, String username, String email, String password, boolean verified, String verificationCode) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.verified = verified;
        this.verificationCode = verificationCode;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
