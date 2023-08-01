package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.Question;
import com.freeuni.quizzard.data.mongo.model.Quiz;
import com.freeuni.quizzard.data.mongo.repository.QuizRepository;
import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.mapper.QuestionMapper;
import com.freeuni.quizzard.mapper.QuizMapper;
import com.freeuni.quizzard.model.QuizRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    private final QuestionMapper questionMapper;

    private final QuizMapper quizMapper;

    private static final int QUIZ_QUESTIONS = 10;

    public QuizDto getRandomSequenceQuiz(String name) {
        Quiz quiz = quizRepository.findQuizByName(name);

        if (Objects.isNull(quiz)) {
            throw new RuntimeException("Quiz Not found");
        }

        QuizDto dto = quizMapper.toQuizDto(quiz);
        Collections.shuffle(dto.getQuestions());
        if (dto.getQuestions().size() > QUIZ_QUESTIONS) {
            dto.setQuestions(dto.getQuestions().subList(0, QUIZ_QUESTIONS));
        }
        return dto;
    }

    public QuizDto createQuiz(QuizRequest quizRequest){
        Quiz quiz = fetchQuiz(quizRequest);
        Question question = questionMapper.toQuestion(quizRequest);
        addQuestion(quiz, question);
        Quiz savedQuiz = quizRepository.save(quiz);
        return quizMapper.toQuizDto(savedQuiz);
    }

    private Quiz fetchQuiz(QuizRequest quizRequest) {
        Quiz quiz = quizRepository.findQuizByName(quizRequest.getName());
        if (Objects.isNull(quiz)) {
            quiz = quizMapper.toQuiz(quizRequest);
        }
        return quiz;
    }

    private void addQuestion(Quiz quiz, Question question) {
        if (Objects.isNull(quiz.getQuestions())) {
            quiz.setQuestions(new ArrayList<>());
        }
        quiz.getQuestions().add(question);
    }
}
