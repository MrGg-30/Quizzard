package com.freeuni.quizzard.mapper;


import com.freeuni.quizzard.data.mongo.model.Question;
import com.freeuni.quizzard.data.mongo.model.Quiz;
import com.freeuni.quizzard.dto.QuestionDto;
import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.model.QuizRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface QuizMapper {

    @Mapping(target = "questions", ignore = true)
    Quiz toQuiz(QuizRequest quizRequest);

    QuizDto toQuizDto(Quiz quiz);

    QuestionDto toQuestionDto(Question question);
}