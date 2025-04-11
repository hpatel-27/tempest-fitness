package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.dto.AuthenticationResponse;
import com.hpatel.Tempest_Fitness.dto.LoginRequestDTO;
import com.hpatel.Tempest_Fitness.dto.RegisterRequestDTO;
import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.services.AuthenticationService;
import com.hpatel.Tempest_Fitness.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController extends APIController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(BASE_PATH + "/auth/login")
    public ResponseEntity<?> login (@RequestBody LoginRequestDTO request) {
        try {
            // This should theoretically return the JWT token... allegedly (hopefully)
            return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(errorResponse("Invalid username or password."), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(BASE_PATH + "/auth/register")
    public ResponseEntity<?> register (@RequestBody RegisterRequestDTO request) {
        try {
            return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(errorResponse("Invalid registration request."), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(BASE_PATH + "/auth/logout")
    public ResponseEntity<?> logout() {
        // TODO: Clear the token on logout




        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>(errorResponse("No user is currently logged in."), HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(successResponse("Logout successful!"), HttpStatus.OK);
    }
}
