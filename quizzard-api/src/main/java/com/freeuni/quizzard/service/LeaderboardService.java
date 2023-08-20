package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.Leaderboard;
import com.freeuni.quizzard.data.mongo.repository.LeaderboardRepository;
import com.freeuni.quizzard.dto.LeaderboardDto;
import com.freeuni.quizzard.mapper.LeaderboardMapper;
import com.freeuni.quizzard.model.QuizResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;

    private final LeaderboardMapper leaderboardMapper;

    private final UserService userService;

    private static final int LEADERBOARD_MAX_SIZE = 10;

    public List<LeaderboardDto> getLeaderboardByQuiz(String category) {
        List<Leaderboard> leaderboards = leaderboardRepository.findLeaderboardByQuizCategory(category);
        return leaderboards.stream()
                .map(leaderboardMapper::toLeaderboardDto)
                .sorted(Comparator.comparingDouble(LeaderboardDto::getScore).reversed())
                .limit(LEADERBOARD_MAX_SIZE)
                .collect(Collectors.toList());
    }

    public void submitScores(QuizResult result) {
        String category = result.getCategory();
        String winnerUsername = null;
        int winnerScore = 0;

        for (Map.Entry<String, Integer> entry : result.getPlayerScores().entrySet()) {
            String username = entry.getKey();
            Integer score = entry.getValue();
            if (score > winnerScore) {
                winnerScore = score;
                winnerUsername = username;
            }
            Leaderboard leaderboard = leaderboardRepository.findLeaderboardByQuizCategoryAndUsername(category, username);
            if (leaderboard == null) {
                leaderboardRepository.save(new Leaderboard().setScore(score).setUsername(username).setQuizCategory(category));
            } else {
                leaderboardRepository.save(leaderboard.setScore(leaderboard.getScore() + score));
            }

            userService.addScore(username, score);
        }
        userService.addWinningCount(winnerUsername);
    }
}
