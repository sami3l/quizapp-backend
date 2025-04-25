package com.example.quizapp.service;

import com.example.quizapp.dto.LoginRequest;
import com.example.quizapp.dto.SignupRequest;

import com.example.quizapp.model.User;
import com.example.quizapp.repository.RoleRepository;
import com.example.quizapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    public Map<String, Object> authenticateUser(LoginRequest loginRequest) {
        // Simulated authentication logic (DO NOT use in production)
        // Replace this with database check or mock as needed
        Map<String, Object> response = new HashMap<>();
        response.put("id", 1); // Dummy ID
        response.put("username", loginRequest.getEmail().split("@")[0]); // Dummy username
        response.put("email", loginRequest.getEmail());
        response.put("password" , loginRequest.getPassword());
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
