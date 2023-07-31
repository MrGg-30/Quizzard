package com.freeuni.quizzard.model;

import lombok.Data;

import java.util.List;

@Data
public class QuizRequest {

    private String name;

    private String category;

    private String description;

    private String questionText;

    private String correctAnswer;

    private List<String> possibleAnswers;
}
