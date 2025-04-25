package com.example.quizapp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private String id;
    private String text;
    private Integer points;
    private QuestionType type;
    private List<Answer> answers = new ArrayList<>();

    public enum QuestionType {
        MULTIPLE_CHOICE,
        TRUE_FALSE,
        OPEN_ENDED
    }
}