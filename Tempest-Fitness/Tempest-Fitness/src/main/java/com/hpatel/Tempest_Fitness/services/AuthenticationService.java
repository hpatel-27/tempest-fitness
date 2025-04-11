package com.hpatel.Tempest_Fitness.services;

import com.hpatel.Tempest_Fitness.dto.AuthenticationResponse;
import com.hpatel.Tempest_Fitness.dto.LoginRequestDTO;
import com.hpatel.Tempest_Fitness.dto.RegisterRequestDTO;
import com.hpatel.Tempest_Fitness.dto.UserDTO;
import com.hpatel.Tempest_Fitness.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(JwtService jwtService, UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public UserDTO register(RegisterRequestDTO requestDTO) {
        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setRole("USER");
        userService.save(user);

        return new UserDTO(user);
    }

    public AuthenticationResponse login (LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );
        User user = userService.findByName(loginRequestDTO.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        String jwtToken = jwtService.generateToken(user);
        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setToken(jwtToken);

        return authResponse;
    }
}
