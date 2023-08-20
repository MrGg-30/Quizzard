package com.freeuni.quizzard.data.mongo.repository;

import com.freeuni.quizzard.data.mongo.model.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends MongoRepository<Quiz, String> {
    Quiz findQuizByCategory(String category);

    @Query("{ 'category': { $regex: '^?0', $options: 'i' } }")
    List<Quiz> findQuizByCategoryPrefix(String category);
}
