package com.example.quizapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_quizzes")
public class UserQuiz {
    @Id
    private String id;
    private String userId;
    private String quizId;
    private Integer score;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean completed;
    private Map<String, String> userAnswers; // Question ID -> Answer
}