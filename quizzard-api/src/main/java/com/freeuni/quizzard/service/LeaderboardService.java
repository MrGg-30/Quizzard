package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.Leaderboard;
import com.freeuni.quizzard.data.mongo.repository.LeaderboardRepository;
import com.freeuni.quizzard.dto.LeaderboardDto;
import com.freeuni.quizzard.mapper.LeaderboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;

    private final LeaderboardMapper leaderboardMapper;

    private static final int LEADERBOARD_MAX_SIZE = 10;

    public List<LeaderboardDto> getLeaderboardByQuiz(String quizId) {
        List<Leaderboard> leaderboards = leaderboardRepository.findLeaderboardByQuizId(quizId);
        return leaderboards.stream()
                .map(leaderboardMapper::toLeaderboardDto)
                .sorted(Comparator.comparingDouble(LeaderboardDto::getScore).reversed())
                .limit(LEADERBOARD_MAX_SIZE)
                .collect(Collectors.toList());
    }
}
