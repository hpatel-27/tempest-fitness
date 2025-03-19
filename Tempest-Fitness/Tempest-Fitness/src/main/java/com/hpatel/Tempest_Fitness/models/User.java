package com.hpatel.Tempest_Fitness.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_user")
public class User extends DomainObject implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, unique = true )
    private String username;

    private String password;

    private String role;

    /** User's first name */
    private String firstName;

    /** User's last name */
    private String lastName;

    /** User's height (cm) */
    private double height;

    /**
     * Default constructor
     */
    public User() {
        // empty constructor
    }

    @Override
    public Serializable getId() {
        return id;
    }
    /**
     * Sets the user id
     *
     * @param id
     *            id to set
     */
    @SuppressWarnings( "unused")
    public void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Gets the username
     *
     * @return username
     */
//    @Override
    public String getUsername () {
        return username;
    }

    /**
     * Sets the username
     *
     * @param username
     *            username to set
     */
    public void setUsername ( final String username ) {
        if (username == null) {
            throw new NullPointerException("No username was provided.");
        }
        else if (username.isBlank()) {
            throw new IllegalArgumentException("Empty username was provided.");
        }
        else if (username.length() > 25) {
            throw new IllegalArgumentException("Username cannot be more than 25 characters.");
        }
        this.username = username;
    }

    /**
     * Gets the user password
     *
     * @return password
     */
    public String getPassword () {
        return password;
    }

    /**
     * Sets the user password
     *
     * @param password
     *            password to set
     */
    public void setPassword ( final String password ) {
        if (password == null) {
            throw new NullPointerException("No password was provided.");
        }
        else if (password.isBlank()) {
            throw new IllegalArgumentException("Empty password was provided.");
        }
        else if (password.length() > 100) {
            throw new IllegalArgumentException("Password cannot be more than 100 characters.");
        }
        this.password = password;
    }

    /**
     * Gets the user role
     *
     * @return role
     */
    public String getRole () {
        return role;
    }

    /**
     * Sets the user role
     *
     * @param role
     *            role to set
     */
    public void setRole ( final String role ) {
        if (role == null) {
            throw new NullPointerException("No role was provided.");
        }
        else if (role.isBlank()) {
            throw new IllegalArgumentException("Empty role was provided.");
        }
        else if (role.length() > 20) {
            throw new IllegalArgumentException("Role cannot be more than 20 characters.");
        }
        if (!role.matches("^[A-Za-z ]+$")) {
            throw new IllegalArgumentException("Role should only contain characters.");
        }
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name was expected but there was none.");
        }
        if (firstName.length() > 20) {
            throw new IllegalArgumentException("Maximum character limit is 20 for first name.");
        }
        if (!firstName.matches("^[A-Za-z ]+$")) {
            throw new IllegalArgumentException("First name should only contain characters.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name was expected but there was none.");
        }
        if (lastName.length() > 20) {
            throw new IllegalArgumentException("Maximum character limit is 20 for last name.");
        }
        if (!lastName.matches("^[A-Za-z ]+$")) {
            throw new IllegalArgumentException("Last name should only contain characters.");
        }
        this.lastName = lastName;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        // Between 0 and 245 centimeters (0 to 8 feet)
        if (height < 0 || height > 245) {
            throw new IllegalArgumentException("Height is outside of permitted range.");
        }
        this.height = height;
    }

    /**
     * Gets the user's authorities
     *
     * @return Collection of user roles
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        final Set<GrantedAuthority> roles = new HashSet<>();
        roles.add( new SimpleGrantedAuthority( "ROLE_" + role ) );
        return roles;
    }

    /**
     * Returns whether the account is not expired
     *
     * @return true
     */
    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    /**
     * Returns whether the account is not locked
     *
     * @return true
     */
    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    /**
     * Returns whether the credentials are not expired
     *
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    /**
     * Returns whether the account is enabled
     *
     * @return true
     */
    @Override
    public boolean isEnabled () {
        return true;
    }

}
