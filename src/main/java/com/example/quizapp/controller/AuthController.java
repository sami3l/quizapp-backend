package com.example.quizapp.controller;



import com.example.quizapp.dto.LoginRequest;
import com.example.quizapp.dto.PasswordResetRequest;
import com.example.quizapp.dto.SignupRequest;
import com.example.quizapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @GetMapping("/hello")
    public String sayhi() {
        return "Hello from QUIZ  👋";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        authService.registerUser(signupRequest);
        // Return a JSON response instead of a plain string
        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }



    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match!");
        }

        authService.resetPassword(request.getToken(), request.getPassword());
        return ResponseEntity.ok("Password reset successful!");
    }
}
