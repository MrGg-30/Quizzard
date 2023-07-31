package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.Question;
import com.freeuni.quizzard.data.mongo.model.Quiz;
import com.freeuni.quizzard.data.mongo.repository.QuizRepository;
import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.mapper.QuizMapperImpl;
import com.freeuni.quizzard.model.QuizRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    private final QuizMapperImpl quizMapper;

    private static final int QUIZ_QUESTIONS = 10;

    public QuizDto getRandomSequenceQuiz(String name) {
        Quiz quiz = quizRepository.findQuizByName(name);

        if (quiz == null) {
            throw new RuntimeException("Quiz Not found");
        }

        QuizDto dto = quizMapper.entityToDto(quiz);
        Collections.shuffle(dto.getQuestions());
        if (dto.getQuestions().size() > QUIZ_QUESTIONS) {
            dto.setQuestions(dto.getQuestions().subList(0, QUIZ_QUESTIONS));
        }
        return dto;
    }

    public void addQuestionToQuiz(QuizRequest quizRequest){
        Quiz quiz = quizRepository.findQuizByName(quizRequest.getName());

        if (quiz == null) {
            quiz = new Quiz();
            quiz.setName(quizRequest.getName());
            quiz.setDescription(quizRequest.getDescription());
        }

        Question question = new Question();
        question.setQuestionText(quizRequest.getQuestionText());
        question.setCorrectAnswer(quizRequest.getCorrectAnswer());
        question.setPossibleAnswers(quizRequest.getPossibleAnswers());

        if (quiz.getQuestions() == null) {
            quiz.setQuestions(new ArrayList<>());
        }
        quiz.getQuestions().add(question);

        quizRepository.save(quiz);
    }
}
