package com.freeuni.quizzard.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class QuizRequest {

    private String category;

    private String questionText;

    private String correctAnswer;

    private List<String> possibleAnswers;

    private MultipartFile questionPicture;
}
