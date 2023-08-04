package com.freeuni.quizzard.controller;

import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.model.QuizRequest;
import com.freeuni.quizzard.service.QuizService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/questions")
    public ResponseEntity<QuizDto> getRandomSequenceQuiz(@RequestParam @NonNull String category) {
        QuizDto quizDto = quizService.getRandomSequenceQuiz(category);

        return ResponseEntity.status(HttpStatus.OK).
                body(quizDto);
    }

    @PostMapping()
    public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizRequest quizRequest) {
        QuizDto quizDto = quizService.createQuiz(quizRequest);

        return ResponseEntity.status(HttpStatus.CREATED).
                body(quizDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = quizService.getCategories();
        return ResponseEntity.ok(categories);
    }
}
