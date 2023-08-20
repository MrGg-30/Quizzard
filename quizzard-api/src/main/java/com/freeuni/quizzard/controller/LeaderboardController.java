package com.freeuni.quizzard.controller;

import com.freeuni.quizzard.dto.LeaderboardDto;
import com.freeuni.quizzard.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/rating/{category}")
    public ResponseEntity<List<LeaderboardDto>> getSortedLeaderboard(@PathVariable String category) {
        List<LeaderboardDto> leaderboardDtos = leaderboardService.getLeaderboardByQuiz(category);

        return ResponseEntity.status(HttpStatus.OK).
                body(leaderboardDtos);
    }
}
