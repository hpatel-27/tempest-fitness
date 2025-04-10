package com.hpatel.Tempest_Fitness.dto;

import com.hpatel.Tempest_Fitness.models.User;

public class UserDTO {
    /** User's unique identifier */
    private Long id;

    /** User's username */
    private String username;

    /** User's role (USER, ATHLETE, etc.) */
    private String role;

    /** User's email */
    private String email;

    /** User's first name */
    private String firstName;

    /** User's last name */
    private String lastName;

    /** User's height (cm) */
    private double height;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.height = user.getHeight();
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
