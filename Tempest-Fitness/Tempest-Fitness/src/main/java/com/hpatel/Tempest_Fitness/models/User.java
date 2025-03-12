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
        this.username = username;
    }

    /**
     * Gets the user password
     *
     * @return password
     */
//    @Override
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
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name should have been provided.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name should have been provided.");
        }
        this.lastName = lastName;
    }

    public double getHeight() {
        return height;
    }

    private void setHeight(double height) {
        if (height <= 0 || height > 245) {
            throw new IllegalArgumentException("Height is outside of permitted range.");
        }
        this.height = height;
    }

    /**
     * Updates User with the values from the given User's object.
     * @param newUser The new user with the updated information
     */
    public void updateUser(User newUser) {
        setFirstName(newUser.getFirstName());
        setLastName(newUser.getLastName());
        setHeight(newUser.getHeight());
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
