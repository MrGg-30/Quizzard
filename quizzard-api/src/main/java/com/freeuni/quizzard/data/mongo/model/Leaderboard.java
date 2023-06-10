package com.freeuni.quizzard.data.mongo.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "leaderboard")
public class Leaderboard {

    @Id
    private String id;

    private String userId;

    private String quizId;

    private Double score;
}
