package com.hpatel.Tempest_Fitness.dto;

public class RegisterRequestDTO {
    /** Username provided for the register request */
    private String username;
    /** Password provided for the register request */
    private String password;

    private String role;

    /** Username getter and setter */
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    /** Password getter and setter */
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
