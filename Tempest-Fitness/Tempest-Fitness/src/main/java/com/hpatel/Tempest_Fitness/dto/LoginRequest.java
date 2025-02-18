package com.hpatel.Tempest_Fitness.dto;

/**
 * Login DTO to protect against exposing sensitive User information
 * during the login process
 */
public class LoginRequest {
    /** Username provided for the login request */
    private String username;
    /** Password provided for the login request */
    private String password;

    /** Username getter and setter */
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    /** Password getter and setter */
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
