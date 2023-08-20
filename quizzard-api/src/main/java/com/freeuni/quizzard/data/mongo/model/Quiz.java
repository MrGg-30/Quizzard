package com.freeuni.quizzard.data.mongo.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Accessors(chain = true)
@Document(collection = "quizzes")
public class Quiz {

    @Id
    private String id;

    private String category;

    private String description;

    private List<Question> questions;
}
