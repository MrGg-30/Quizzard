package com.freeuni.quizzard.mapper;

import com.freeuni.quizzard.data.mongo.model.Leaderboard;
import com.freeuni.quizzard.dto.LeaderboardDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderboardMapperTest {

    private LeaderboardMapper leaderboardMapper;

    @BeforeEach
    public void setUp() {
        leaderboardMapper = Mappers.getMapper(LeaderboardMapper.class);
    }

    @Test
    public void testToLeaderboardDto() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setId("1");
        leaderboard.setUsername("UserA");
        leaderboard.setQuizCategory("General Knowledge");
        leaderboard.setScore(100);

        LeaderboardDto leaderboardDto = leaderboardMapper.toLeaderboardDto(leaderboard);

        assertEquals("1", leaderboardDto.getId());
        assertEquals("UserA", leaderboardDto.getUsername());
        assertEquals("General Knowledge", leaderboardDto.getQuizCategory());
        assertEquals(100.0, leaderboardDto.getScore());
    }
}
