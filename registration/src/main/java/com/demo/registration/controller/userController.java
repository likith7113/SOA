package com.demo.registration.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
class UserController {
    private final ApplicationContext applicationContext;

    public UserController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            Class<?> userType = Class.forName("com.demo.registration.entity.User");
            Object user = userType.getDeclaredConstructor().newInstance();
            userType.getMethod("setUsername", String.class).invoke(user, request.get("username"));
            userType.getMethod("setEmail", String.class).invoke(user, request.get("email"));
            userType.getMethod("setPassword", String.class).invoke(user, request.get("password"));

            Object service = userService();
            Object savedUser = service.getClass().getMethod("register", userType).invoke(service, user);
            return ResponseEntity.ok(
                    Map.of(
                            "message", "Registration successful",
                            "username", userType.getMethod("getUsername").invoke(savedUser),
                            "email", userType.getMethod("getEmail").invoke(savedUser)
                    )
            );
        } catch (ReflectiveOperationException | RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            if (username == null || username.isBlank() || password == null || password.isBlank()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Username and password are required"));
            }

            Object service = userService();
            String result = (String) service.getClass()
                    .getMethod("login", String.class, String.class)
                    .invoke(service, username, password);
            return ResponseEntity.ok(Map.of("message", result));
        } catch (ReflectiveOperationException | RuntimeException e) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    private Object userService() {
        return applicationContext.getBean("userService");
    }
}