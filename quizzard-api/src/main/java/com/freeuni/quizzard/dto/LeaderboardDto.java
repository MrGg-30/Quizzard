package com.freeuni.quizzard.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LeaderboardDto {

    private String id;

    private String username;

    private String quizId;

    private Double score;
}
