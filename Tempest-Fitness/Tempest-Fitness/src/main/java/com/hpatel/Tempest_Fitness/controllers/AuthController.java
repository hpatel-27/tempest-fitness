package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.dto.LoginRequest;
import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController extends APIController {

    private final AuthenticationManager authenticationManagerManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManagerManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManagerManager = authenticationManagerManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(BASE_PATH + "/auth/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest request) {
        try {
            Authentication auth = authenticationManagerManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            if (auth.isAuthenticated()) {
                return ResponseEntity.ok("Login successful!"); // Replace with a JWT token later when I work on that
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials provided.");
            }
        } catch (AuthenticationException e ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }

    @PostMapping(BASE_PATH + "/auth/register")
    public ResponseEntity<?> register (@RequestBody User user) {
        if (userService.findByName(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

}
