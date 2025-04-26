package com.example.quizapp.service;

import com.example.quizapp.dto.LoginRequest;
import com.example.quizapp.dto.SignupRequest;

import com.example.quizapp.model.User;
import com.example.quizapp.repository.RoleRepository;
import com.example.quizapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    public Map<String, Object> authenticateUser(LoginRequest loginRequest) {
        // Simulated authentication logic (DO NOT use in production)
        // Replace this with database check or mock as needed
        Map<String, Object> response = new HashMap<>();
        response.put("id", 1); // Dummy ID
        response.put("username", loginRequest.getUsername()); // Just use the username directly
        // If you still want to include email in the response but don't have it, you could either:
        response.put("email", loginRequest.getUsername() + "@example.com"); // Generate a dummy email if needed
        // Or simply not include email in the response
        response.put("roles", List.of("USER")); // Default role

        return response;
    }

    public User registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                signupRequest.getPassword() // No encoding
        );

        // Skip roles
        user.setRoles(null);

        return userRepository.save(user);
    }

    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(newPassword); // No encoding
        userRepository.save(user);
    }
}
