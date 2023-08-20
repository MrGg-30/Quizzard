package com.freeuni.quizzard.data.mongo.repository;

import com.freeuni.quizzard.data.mongo.model.Leaderboard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderboardRepository extends MongoRepository<Leaderboard, String> {

    List<Leaderboard> findLeaderboardByQuizCategory(String category);

    Leaderboard findLeaderboardByQuizCategoryAndUsername(String category, String username);
}
