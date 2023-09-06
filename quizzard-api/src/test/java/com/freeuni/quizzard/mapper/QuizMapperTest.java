package com.freeuni.quizzard.mapper;

import com.freeuni.quizzard.data.mongo.model.Question;
import com.freeuni.quizzard.data.mongo.model.Quiz;
import com.freeuni.quizzard.dto.QuestionDto;
import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.model.QuizRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuizMapperTest {

    private QuizMapper quizMapper;

    @BeforeEach
    public void setUp() {
        quizMapper = Mappers.getMapper(QuizMapper.class);
    }

    @Test
    public void testToQuiz() {
        QuizRequest quizRequest = new QuizRequest();
        quizRequest.setCategory("General Knowledge");

        Quiz quiz = quizMapper.toQuiz(quizRequest);

        assertEquals("General Knowledge", quiz.getCategory());
        assertEquals(null, quiz.getQuestions());
    }

    @Test
    public void testToQuizDto() {
        Quiz quiz = new Quiz();
        quiz.setCategory("Science");
        quiz.setDescription("Test your science knowledge");

        QuizDto quizDto = quizMapper.toQuizDto(quiz);

        assertEquals("Science", quizDto.getCategory());
        assertEquals("Test your science knowledge", quizDto.getDescription());
        assertEquals(null, quizDto.getQuestions());
    }

    @Test
    public void testToQuestionDto() {
        Question question = new Question();
        question.setQuestionText("What is the capital of Spain?");
        question.setCorrectAnswer("Madrid");
        List<String> possibleAnswers = Arrays.asList("London", "Berlin", "Madrid", "Paris");
        question.setPossibleAnswers(possibleAnswers);

        QuestionDto questionDto = quizMapper.toQuestionDto(question);

        assertEquals("What is the capital of Spain?", questionDto.getQuestionText());
        assertEquals("Madrid", questionDto.getCorrectAnswer());
        assertEquals(possibleAnswers, questionDto.getPossibleAnswers());
    }
}
