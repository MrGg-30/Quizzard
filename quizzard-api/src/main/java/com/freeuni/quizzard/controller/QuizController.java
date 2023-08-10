package com.freeuni.quizzard.controller;

import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.model.GameRequest;
import com.freeuni.quizzard.model.GameSession;
import com.freeuni.quizzard.model.QuizRequest;
import com.freeuni.quizzard.model.RequestStatus;
import com.freeuni.quizzard.service.GameSessionService;
import com.freeuni.quizzard.service.QuizService;
import com.freeuni.quizzard.service.WebSocketService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
    private final WebSocketService webSocketService;

    private final GameSessionService gameSessionService;

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

    @MessageMapping("/game-request")
    public void sendGameRequest(@Payload GameRequest gameRequest){
        System.out.println(gameRequest);
        webSocketService.sendGameRequest(gameRequest.getTo(), gameRequest);
    }

    @MessageMapping("/game-response")
    public void sendGameResponse(@Payload GameRequest gameRequest) {
        if (gameRequest.getStatus() == RequestStatus.ACCEPTED) {
            GameSession gameSession = gameSessionService.createGameSession(gameRequest.getFrom(), gameRequest.getTo(), gameRequest.getCategory());
            String sessionId = gameSession.getSessionId();
            gameRequest.setSessionId(sessionId);
            // This sent game response already contains the SessionId
            webSocketService.sendGameResponse(gameRequest.getFrom(), gameRequest);

            // Now to send the sessionId to the user with .getTo() username
            webSocketService.sendSessionId(gameRequest.getTo(), gameRequest);

            // and game to start?
        } else {
            // Rejected, No game to start
            webSocketService.sendGameResponse(gameRequest.getFrom(), gameRequest);
        }
    }

    @MessageMapping("/session-id")
    public void sendSessionId(@Payload GameRequest gameRequest) {

    }
}
