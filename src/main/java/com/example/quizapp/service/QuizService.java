package com.example.quizapp.service;

import com.example.quizapp.model.*;
import com.example.quizapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserQuizRepository userQuizRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(String id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    public List<Quiz> getQuizzesByCategory(String category) {
        return quizRepository.findByCategory(category);
    }

    public List<Quiz> getQuizzesByDifficulty(String difficulty) {
        return quizRepository.findByDifficulty(difficulty);
    }

    public Quiz createQuiz(Quiz quiz) {
        // Generate IDs for nested objects
        quiz.getQuestions().forEach(question -> {
            question.setId(UUID.randomUUID().toString());
            question.getAnswers().forEach(answer ->
                    answer.setId(UUID.randomUUID().toString())
            );
        });

        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(String id, Quiz quizDetails) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        quiz.setTitle(quizDetails.getTitle());
        quiz.setDescription(quizDetails.getDescription());
        quiz.setDurationMinutes(quizDetails.getDurationMinutes());
        quiz.setCategory(quizDetails.getCategory());
        quiz.setDifficulty(quizDetails.getDifficulty());
        quiz.setQuestions(quizDetails.getQuestions());

        return quizRepository.save(quiz);
    }

    public void deleteQuiz(String id) {
        quizRepository.deleteById(id);
    }

    public UserQuiz startQuiz(String quizId, String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!quizRepository.existsById(quizId)) {
            throw new RuntimeException("Quiz not found");
        }

        UserQuiz userQuiz = new UserQuiz();
        userQuiz.setUserId(user.getId());
        userQuiz.setQuizId(quizId);
        userQuiz.setStartTime(LocalDateTime.now());
        userQuiz.setCompleted(false);

        return userQuizRepository.save(userQuiz);
    }

    public UserQuiz submitQuiz(String userQuizId, Map<String, String> answers) {
        UserQuiz userQuiz = userQuizRepository.findById(userQuizId)
                .orElseThrow(() -> new RuntimeException("User quiz attempt not found"));

        userQuiz.setEndTime(LocalDateTime.now());
        userQuiz.setCompleted(true);
        userQuiz.setUserAnswers(answers);

        // Get quiz to calculate score
        Quiz quiz = quizRepository.findById(userQuiz.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Calculate score
        int totalPoints = 0;
        int earnedPoints = 0;

        for (Question question : quiz.getQuestions()) {
            totalPoints += question.getPoints();

            if (answers.containsKey(question.getId())) {
                String userAnswer = answers.get(question.getId());

                if (question.getType() == Question.QuestionType.MULTIPLE_CHOICE ||
                        question.getType() == Question.QuestionType.TRUE_FALSE) {

                    for (Answer answer : question.getAnswers()) {
                        if (answer.getId().equals(userAnswer) && answer.isCorrect()) {
                            earnedPoints += question.getPoints();
                            break;
                        }
                    }
                }
            }
        }

        int percentScore = totalPoints > 0 ? (earnedPoints * 100) / totalPoints : 0;
        userQuiz.setScore(percentScore);

        return userQuizRepository.save(userQuiz);
    }

    public List<UserQuiz> getUserQuizzes(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userQuizRepository.findByUserId(user.getId());
    }
}
