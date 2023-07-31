package com.freeuni.quizzard.dto;

import com.freeuni.quizzard.data.mongo.model.Question;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class QuizDto {
    private String id;

    private String name;

    private String category;

    private String creatorId;

    private String description;

    private List<Question> questions;
}
