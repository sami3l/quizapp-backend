package com.example.quizapp.dto;


import com.example.quizapp.model.Question;
import lombok.Data;

import java.util.List;

@Data
public class QuizDto {
    private String id;
    private String title;
    private String description;
    private Integer durationMinutes;
    private String category;
    private String difficulty;
    private List<Question> questions;
}