package com.freeuni.quizzard.model;

import lombok.Data;

@Data
public class GameResult {
    private String category;
    private String currentUsername;
    private String anotherUsername;
    private Integer score;
}
