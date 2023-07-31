package com.freeuni.quizzard.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class QuestionDto {
    private String id;

    private String questionText;

    private List<String> possibleAnswers;

    private String correctAnswer;
}
