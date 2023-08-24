package com.freeuni.quizzard.api;

import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.model.GameRequest;
import com.freeuni.quizzard.model.QuizRequest;
import com.freeuni.quizzard.model.QuizResult;
import com.freeuni.quizzard.service.LeaderboardService;
import com.freeuni.quizzard.service.QuizService;
import com.freeuni.quizzard.service.WebSocketService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class QuizController implements QuizApi{

    private final QuizService quizService;

    private final LeaderboardService leaderboardService;

    private final WebSocketService webSocketService;

    public ResponseEntity<QuizDto> getRandomSequenceQuiz(@PathVariable @NonNull String category) {
        QuizDto quizDto = quizService.getRandomSequenceQuiz(category);

        return ResponseEntity.status(HttpStatus.OK).
                body(quizDto);
    }

    public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizRequest quizRequest) {
        QuizDto quizDto = quizService.createQuiz(quizRequest, null);

        return ResponseEntity.status(HttpStatus.CREATED).
                body(quizDto);
    }

    public void submitResult(@RequestBody QuizResult quizResult) {
        leaderboardService.submitScores(quizResult);
    }

    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = quizService.getCategories();
        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<List<String>> getCategoriesByPrefix(@PathVariable String category) {
        List<String> categories = quizService.getCategoriesByPrefix(category);
        return ResponseEntity.ok(categories);
    }

    @MessageMapping("/game-request")
    public void sendGameRequest(@Payload GameRequest gameRequest){
        System.out.println(gameRequest);
        webSocketService.sendGameRequest(gameRequest.getTo(), gameRequest);
    }

    @MessageMapping("/game-response")
    public void sendGameResponse(@Payload GameRequest gameRequest) {
        gameRequest.setStatus(gameRequest.getStatus());
        System.out.println(gameRequest);
        webSocketService.sendGameResponse(gameRequest.getFrom(), gameRequest);
    }
}
