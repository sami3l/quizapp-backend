package com.example.quizapp.repository;

import com.example.quizapp.model.UserQuiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserQuizRepository extends MongoRepository<UserQuiz, String> {
    List<UserQuiz> findByUserId(String userId);
    List<UserQuiz> findByQuizId(String quizId);
    Optional<UserQuiz> findByUserIdAndQuizId(String userId, String quizId);
}