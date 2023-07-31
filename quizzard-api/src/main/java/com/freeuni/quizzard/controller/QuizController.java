package com.freeuni.quizzard.controller;

import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.service.QuizService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/questions")
    public ResponseEntity<QuizDto> getRandomSequenceQuiz(@RequestParam @NonNull String name) {
        QuizDto quizDto = quizService.getRandomSequenceQuiz(name);

        return ResponseEntity.status(HttpStatus.OK).
                body(quizDto);
    }
}