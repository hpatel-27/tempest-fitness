package com.hpatel.Tempest_Fitness.dto;

public class RegisterRequest {
    /** Username provided for the register request */
    private String username;
    /** Password provided for the register request */
    private String password;

    /** Username getter and setter */
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    /** Password getter and setter */
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
