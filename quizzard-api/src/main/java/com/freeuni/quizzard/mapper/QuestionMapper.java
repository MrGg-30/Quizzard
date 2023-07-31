package com.freeuni.quizzard.mapper;

import com.freeuni.quizzard.data.mongo.model.Question;
import com.freeuni.quizzard.model.QuizRequest;
import org.mapstruct.Mapper;

@Mapper
public interface QuestionMapper {

    Question toQuestion(QuizRequest quizRequest);
}
