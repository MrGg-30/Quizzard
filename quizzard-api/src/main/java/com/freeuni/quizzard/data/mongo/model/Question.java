package com.freeuni.quizzard.data.mongo.model;

import lombok.Data;
import java.util.List;
@Data
public class Question {
    private String questionText;
    private List<String> possibleAnswers;
    private String correctAnswer;
}
