package com.freeuni.quizzard.mapper;

import com.freeuni.quizzard.data.mongo.model.Leaderboard;
import com.freeuni.quizzard.dto.LeaderboardDto;
import org.mapstruct.Mapper;

@Mapper
public interface LeaderboardMapper {

    LeaderboardDto toLeaderboardDto(Leaderboard leaderboard);
}
