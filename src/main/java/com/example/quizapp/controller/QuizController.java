package com.example.quizapp.controller;

import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.UserQuiz;
import com.example.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable String id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Quiz>> getQuizzesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(quizService.getQuizzesByCategory(category));
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<Quiz>> getQuizzesByDifficulty(@PathVariable String difficulty) {
        return ResponseEntity.ok(quizService.getQuizzesByDifficulty(difficulty));
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        return ResponseEntity.ok(quizService.createQuiz(quiz));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable String id, @RequestBody Quiz quiz) {
        return ResponseEntity.ok(quizService.updateQuiz(id, quiz));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteQuiz(@PathVariable String id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/start")
    public ResponseEntity<UserQuiz> startQuiz(@RequestParam String userId, @RequestParam String quizId) {
        return ResponseEntity.ok(quizService.startQuiz(userId, quizId));
    }

    @PostMapping("/submit/{userQuizId}")

    public ResponseEntity<UserQuiz> submitQuiz(@PathVariable String userQuizId, @RequestBody Map<String, String> answers) {
        return ResponseEntity.ok(quizService.submitQuiz(userQuizId, answers));
    }

    @GetMapping("/user/{userId}")

    public ResponseEntity<List<UserQuiz>> getUserQuizzes(@PathVariable String userId) {
        return ResponseEntity.ok(quizService.getUserQuizzes(userId));
    }
}