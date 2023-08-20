package com.freeuni.quizzard.model;

import lombok.Data;

import java.util.Map;

@Data
public class QuizResult {

    private String category;

    private Map<String, Integer> playerScores;
}
