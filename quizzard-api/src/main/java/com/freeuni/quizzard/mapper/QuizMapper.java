package com.freeuni.quizzard.mapper;


import com.freeuni.quizzard.data.mongo.model.Quiz;
import com.freeuni.quizzard.dto.QuizDto;

public interface QuizMapper {

    QuizDto entityToDto(Quiz quiz);
}
