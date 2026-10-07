package com.klu.service;

import com.klu.model.LoginRequest;
import com.klu.model.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;

    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        // Simple authentication (in production, validate against database)
        if ("likith".equals(request.getUsername()) && "password".equals(request.getPassword())) {
            String token = jwtService.generateToken(request.getUsername());
            return new LoginResponse(token, request.getUsername(), "Login successful");
        }
        return new LoginResponse(null, null, "Invalid credentials");
    }
}
