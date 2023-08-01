package com.freeuni.quizzard.controller;

import com.freeuni.quizzard.dto.LeaderboardDto;
import com.freeuni.quizzard.service.LeaderboardService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/rating")
    public ResponseEntity<List<LeaderboardDto>> getSortedLeaderboard(@RequestParam @NonNull String quizId) {
        List<LeaderboardDto> leaderboardDtos = leaderboardService.getLeaderboardByQuiz(quizId);

        return ResponseEntity.status(HttpStatus.OK).
                body(leaderboardDtos);
    }
}
