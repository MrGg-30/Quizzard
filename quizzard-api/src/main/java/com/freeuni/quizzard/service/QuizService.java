package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.Question;
import com.freeuni.quizzard.data.mongo.model.Quiz;
import com.freeuni.quizzard.data.mongo.repository.QuizRepository;
import com.freeuni.quizzard.dto.QuestionDto;
import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.mapper.QuestionMapper;
import com.freeuni.quizzard.mapper.QuizMapper;
import com.freeuni.quizzard.model.QuizRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    private final QuestionMapper questionMapper;

    private final QuizMapper quizMapper;

    private final S3Client amazonS3;

    private final String bucketName = "quizzard-pictures";


    private static final int QUIZ_QUESTIONS = 10;

    public QuizDto getRandomSequenceQuiz(String category) {
        Quiz quiz = quizRepository.findQuizByCategory(category);

        if (Objects.isNull(quiz)) {
            throw new RuntimeException("Quiz Not found");
        }


        QuizDto dto = convertToQuizDto(quiz);
        Collections.shuffle(dto.getQuestions());
        if (dto.getQuestions().size() > QUIZ_QUESTIONS) {
            dto.setQuestions(dto.getQuestions().subList(0, QUIZ_QUESTIONS));
        }
        return dto;
    }

    public QuizDto createQuiz(QuizRequest quizRequest, MultipartFile questionPicture) {
        Quiz quiz = fetchQuiz(quizRequest);
        String key = null;
        if (questionPicture != null) {
            key = uploadPicture(quizRequest, questionPicture);
        }
        Question question = questionMapper.toQuestion(quizRequest);
        if (key != null) {
            question.setQuestionPictureUrl(key);
        }
        addQuestion(quiz, question);
        Quiz savedQuiz = quizRepository.save(quiz);
        return quizMapper.toQuizDto(savedQuiz);
    }

    public List<String> getCategories() {
        return quizRepository
                .findAll()
                .stream()
                .map(Quiz::getCategory)
                .collect(Collectors.toList());
    }

    public List<String> getCategoriesByPrefix(String category) {
        return quizRepository
                .findQuizByCategoryPrefix(category)
                .stream()
                .map(Quiz::getCategory)
                .collect(Collectors.toList());
    }

    private Quiz fetchQuiz(QuizRequest quizRequest) {
        Quiz quiz = quizRepository.findQuizByCategory(quizRequest.getCategory());
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

    public String uploadPicture(QuizRequest request, MultipartFile questionPicture) {
        try {
            MultipartFile file = questionPicture;
            ByteBuffer byteBuffer = ByteBuffer.wrap(file.getBytes());

            Random rn = new Random();

            String key = request.getCategory() + rn.nextInt(Integer.MAX_VALUE);;

            amazonS3.putObject(builder -> builder.bucket(bucketName).key(key), RequestBody.fromByteBuffer(byteBuffer));

            return key;
        } catch (IOException e) {
            return null;
        }
    }

    private byte[] getQuizPicture(String url) {

        GetObjectRequest objectRequest = GetObjectRequest
                .builder()
                .key(url)
                .bucket(bucketName)
                .build();

        ResponseBytes<GetObjectResponse> objectBytes = amazonS3.getObjectAsBytes(objectRequest);
        return objectBytes.asByteArray();
    }

    private QuizDto convertToQuizDto(Quiz quiz) {
        QuizDto dto = new QuizDto();
        dto.setCategory(quiz.getCategory());
        dto.setDescription(quiz.getDescription());
        dto.setQuestions(new ArrayList<>());
        quiz.getQuestions().forEach(question -> {
            QuestionDto questionDto = quizMapper.toQuestionDto(question);
            if (question.getQuestionPictureUrl() != null) {
                byte[] picture = getQuizPicture(question.getQuestionPictureUrl());
                questionDto.setQuestionPicture(picture);
            }
            dto.getQuestions().add(questionDto);
        });
        return dto;
    }
}
