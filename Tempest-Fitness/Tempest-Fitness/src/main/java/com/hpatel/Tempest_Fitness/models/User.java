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
