package com.freeuni.quizzard.mapper;

import com.freeuni.quizzard.data.mongo.model.Question;
import com.freeuni.quizzard.model.QuizRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionMapperTest {

    private QuestionMapper questionMapper;

    @BeforeEach
    public void setUp() {
        questionMapper = Mappers.getMapper(QuestionMapper.class);
    }

    @Test
    public void testToQuestion() {
        QuizRequest quizRequest = new QuizRequest();
        quizRequest.setCategory("General Knowledge");
        quizRequest.setQuestionText("What is the capital of France?");
        quizRequest.setCorrectAnswer("Paris");
        List<String> possibleAnswers = Arrays.asList("London", "Berlin", "Madrid", "Paris");
        quizRequest.setPossibleAnswers(possibleAnswers);

        Question question = questionMapper.toQuestion(quizRequest);

        assertEquals("What is the capital of France?", question.getQuestionText());
        assertEquals("Paris", question.getCorrectAnswer());
        assertEquals(possibleAnswers, question.getPossibleAnswers());
    }
}
