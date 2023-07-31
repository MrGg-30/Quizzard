package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.Quiz;
import com.freeuni.quizzard.data.mongo.repository.QuizRepository;
import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.mapper.QuizMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public void saveQuiz() {
        // TODO
    }


}
