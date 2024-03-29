package com.freeuni.quizzard.data.mongo.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Accessors(chain = true)
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;

    private String lastName;

    private String username;

    private String email;

    private String profilePictureUrl;

    private List<String> friends;

    private Integer quizCount;

    private Integer wonQuizCount;

    private Integer totalPoints;
}

