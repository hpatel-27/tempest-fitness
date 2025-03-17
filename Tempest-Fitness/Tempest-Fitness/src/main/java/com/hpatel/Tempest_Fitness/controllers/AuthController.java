package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.dto.LoginRequest;
import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController extends APIController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(BASE_PATH + "/auth/login")
    public ResponseEntity<String> login (@RequestBody LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            return ResponseEntity.status(HttpStatus.OK).body("Login successful!");
            // Replace with a JWT token later when I work on that
        } catch (AuthenticationException e ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }

    @PostMapping(BASE_PATH + "/auth/logout")
    public ResponseEntity<?> logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user is currently logged in.");
        }

        SecurityContextHolder.clearContext();
        return ResponseEntity.status(HttpStatus.OK).body("Logout successful!");
    }

    @PostMapping(BASE_PATH + "/auth/register")
    public ResponseEntity<String> register (@RequestBody User user) {
        if (userService.findByName(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

}
