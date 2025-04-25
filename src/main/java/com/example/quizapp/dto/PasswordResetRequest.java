package com.example.quizapp.dto;


import lombok.Data;

@Data
public class PasswordResetRequest {
    private String email;
    private String token;
    private String password;
    private String confirmPassword;
}