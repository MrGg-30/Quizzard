package com.freeuni.quizzard.mapper;

import com.freeuni.quizzard.data.mongo.model.Question;
import com.freeuni.quizzard.data.mongo.model.Quiz;
import com.freeuni.quizzard.dto.QuestionDto;
import com.freeuni.quizzard.dto.QuizDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizMapperImpl {
    public QuizDto entityToDto(Quiz quiz) {
        QuizDto dto = new QuizDto();
        dto.setName(quiz.getName()).setId(quiz.getId())
                .setCategory(quiz.getCategory())
                .setCreatorId(quiz.getCreatorId()).setDescription(quiz.getDescription());

        List<QuestionDto> questionDtoList = new ArrayList<>();
        quiz.getQuestions().forEach(q -> questionDtoList.add(entityToDto(q)));
        dto.setQuestions(questionDtoList);
        return dto;
    }

    private QuestionDto entityToDto(Question question) {
        QuestionDto dto = new QuestionDto();
        dto.setId(question.getQuestionId())
                .setQuestionText(question.getQuestionText())
                .setCorrectAnswer(question.getCorrectAnswer())
                .setPossibleAnswers(question.getPossibleAnswers());
        return dto;
    }
}
