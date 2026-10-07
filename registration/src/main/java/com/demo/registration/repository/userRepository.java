package com.demo.registration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.registration.entity.user;

public interface userRepository extends JpaRepository<user, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<user> findByUsername(String username);
}