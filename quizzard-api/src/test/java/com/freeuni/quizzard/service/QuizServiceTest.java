package com.freeuni.quizzard.service;


import com.freeuni.quizzard.data.mongo.model.Quiz;
import com.freeuni.quizzard.data.mongo.repository.QuizRepository;
import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.mapper.QuestionMapper;
import com.freeuni.quizzard.mapper.QuizMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.s3.S3Client;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private QuizMapper quizMapper;

    @Mock
    private S3Client amazonS3;

    private QuizService quizService;

    private static final int QUIZ_QUESTIONS = 10;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        quizService = new QuizService(quizRepository, questionMapper, quizMapper, amazonS3);
    }

    @Test
    public void testGetRandomSequenceQuiz() {
        Quiz quiz = new Quiz();
        when(quizRepository.findQuizByCategory(anyString())).thenReturn(quiz);

        when(quizMapper.toQuizDto(quiz)).thenReturn(new QuizDto());

        verify(quizMapper, times(0)).toQuizDto(quiz);
    }

    @Test
    public void testGetRandomSequenceQuizQuizNotFound() {
        when(quizRepository.findQuizByCategory(anyString())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> quizService.getRandomSequenceQuiz("TestCategory"));
    }
}
