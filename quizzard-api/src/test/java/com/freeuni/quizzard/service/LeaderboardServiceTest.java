package com.freeuni.quizzard.service;


import com.freeuni.quizzard.data.mongo.model.Leaderboard;
import com.freeuni.quizzard.data.mongo.repository.LeaderboardRepository;
import com.freeuni.quizzard.dto.LeaderboardDto;
import com.freeuni.quizzard.mapper.LeaderboardMapper;
import com.freeuni.quizzard.model.QuizResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderboardServiceTest {

    @Mock
    private LeaderboardRepository leaderboardRepository;

    @Mock
    private LeaderboardMapper leaderboardMapper;

    @Mock
    private UserService userService;

    private LeaderboardService leaderboardService;

    private static final int LEADERBOARD_MAX_SIZE = 10;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        leaderboardService = new LeaderboardService(leaderboardRepository, leaderboardMapper, userService);
    }

    @Test
    public void testGetLeaderboardByQuiz() {
        // Mock the behavior of leaderboardRepository
        List<Leaderboard> leaderboards = new ArrayList<>();
        when(leaderboardRepository.findLeaderboardByQuizCategory(anyString())).thenReturn(leaderboards);

        // Mock the behavior of leaderboardMapper
        when(leaderboardMapper.toLeaderboardDto(any(Leaderboard.class))).thenReturn(new LeaderboardDto());

        // Call the service method
        List<LeaderboardDto> leaderboardDtos = leaderboardService.getLeaderboardByQuiz("TestCategory");

        // Verify that the mapper was called for each leaderboard
        verify(leaderboardMapper, times(leaderboards.size())).toLeaderboardDto(any(Leaderboard.class));

        // Verify that the returned list is not null
        assertEquals(leaderboards.size(), leaderboardDtos.size());
    }

    @Test
    public void testSubmitScores() {
        QuizResult result = new QuizResult();
        result.setCategory("TestCategory");
        Map<String, Integer> playerScores = new HashMap<>();
        playerScores.put("UserA", 100);
        playerScores.put("UserB", 80);
        result.setPlayerScores(playerScores);

        when(leaderboardRepository.findLeaderboardByQuizCategoryAndUsername(anyString(), anyString())).thenReturn(null);

        doNothing().when(userService).addScore(anyString(), anyInt());
        leaderboardService.submitScores(result);

        verify(leaderboardRepository, times(playerScores.size())).save(any(Leaderboard.class));

        verify(userService, times(playerScores.size())).addScore(anyString(), anyInt());

        verify(userService, times(1)).addWinningCount(anyString());
    }
}
