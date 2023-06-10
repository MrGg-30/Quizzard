package com.freeuni.quizzard.data.mongo.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Accessors(chain = true)
public class Question {

    @Id
    private String id;

    private String questionText;

    private List<String> possibleAnswers;

    private String correctAnswer;
}
