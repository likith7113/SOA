package com.klu.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class JwtService {

    public String generateToken(String username) {
        // Generate a simple token (in production, use JWT library like jjwt)
        return UUID.randomUUID().toString().replace("-", "");
    }

    // JWT token validation and extraction logic
    public boolean validateToken(String token) {
        // For now, we'll accept any non-empty token
        return token != null && !token.isEmpty();
    }

    public String extractUsername(String token) {
        // Extract username from token (simplified)
        return "likith";
    }
}
