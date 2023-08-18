package com.freeuni.quizzard.dto;

import com.freeuni.quizzard.data.mongo.model.Question;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class QuizDto {

    private String category;

    private String description;

    private List<QuestionDto> questions;
}
