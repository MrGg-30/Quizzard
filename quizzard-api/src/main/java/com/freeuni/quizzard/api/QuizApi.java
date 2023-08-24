package com.freeuni.quizzard.api;

import com.freeuni.quizzard.common.api.openapi.error.OpenApi401ErrorResponse;
import com.freeuni.quizzard.common.api.openapi.error.OpenApi403ErrorResponse;
import com.freeuni.quizzard.common.api.openapi.error.OpenApi500ErrorResponse;
import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.model.QuizRequest;
import com.freeuni.quizzard.model.QuizResult;
import com.mongodb.lang.Nullable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@OpenApi401ErrorResponse
@OpenApi403ErrorResponse
@OpenApi500ErrorResponse
@RequestMapping(QuizApi.QUIZ_URL)
public interface QuizApi {

    String QUIZ_URL = "/quiz";

    @Operation(summary = "Get random sequence quiz by category",
            security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz retrieved successfully",
                         content = @Content(schema = @Schema(implementation = QuizDto.class))),
            @ApiResponse(responseCode = "404", description = "Quiz not found")
    })
    @GetMapping("/questions/{category}")
    ResponseEntity<QuizDto> getRandomSequenceQuiz(@PathVariable @NonNull String category);

    @Operation(summary = "Create a new quiz",
            security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quiz created successfully",
                         content = @Content(schema = @Schema(implementation = QuizDto.class)))
    })
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<QuizDto> createQuiz(@RequestBody QuizRequest quizRequest);

    @Operation(summary = "Submit quiz result",
            security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quiz result submitted successfully")
    })
    @PostMapping("/result")
    void submitResult(@RequestBody QuizResult quizResult);

    @Operation(summary = "Get all categories",
            security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                         content = @Content(schema = @Schema(implementation = List.class)))
    })
    @GetMapping("/categories")
    ResponseEntity<List<String>> getCategories();

    @Operation(summary = "Get categories by prefix",
            security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                         content = @Content(schema = @Schema(implementation = List.class)))
    })
    @GetMapping("/categories/{category}")
    ResponseEntity<List<String>> getCategoriesByPrefix(@PathVariable String category);
}
