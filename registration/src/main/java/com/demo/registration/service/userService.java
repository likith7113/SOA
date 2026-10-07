package com.demo.registration.service;

import com.demo.registration.repository.userRepository;
import com.demo.registration.entity.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userService {

    private final userRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public userService(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    public user register(user user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }

    public String login(String username, String password) {

        user user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

        if (passwordEncoder.matches(
                password,
                user.getPassword())) {

            return "Login successful";
        }

        throw new RuntimeException("Invalid username or password");
    }
}