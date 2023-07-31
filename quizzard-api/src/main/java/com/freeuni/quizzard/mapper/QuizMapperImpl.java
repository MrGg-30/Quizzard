package com.freeuni.quizzard.mapper;

import com.freeuni.quizzard.data.mongo.model.Quiz;
import com.freeuni.quizzard.dto.QuizDto;
import org.springframework.stereotype.Component;

@Component
public class QuizMapperImpl {
    public QuizDto entityToDto(Quiz quiz) {
        QuizDto dto = new QuizDto();
        dto.setName(quiz.getName()).setId(quiz.getId())
                .setCategory(quiz.getCategory()).setQuestions(quiz.getQuestions())
                .setCreatorId(quiz.getCreatorId()).setDescription(quiz.getDescription());

        return dto;
    }
}
