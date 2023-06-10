package com.freeuni.quizzard.data.mongo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "quizzes")
public class Quiz {
    private String name;

    private String category;

    private String creatorId;

    private String description;

    private List<Question> questions;

}
