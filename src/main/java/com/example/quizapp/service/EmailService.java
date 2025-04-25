package com.example.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("testsami337@gmail.com"); // should match the one in application.properties
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
    public void sendPasswordResetEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("testsami337@gmail.com"); // same as in your config
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Hello,\n\n" +
                "We received a request to reset your password. " +
                "Please click the link below to set a new password:\n\n" +
                resetLink + "\n\n" +
                "If you did not request a password reset, you can ignore this email.\n\n" +
                "Best regards,\n" +
                "The QuizApp Team");

        mailSender.send(message);
    }
}
