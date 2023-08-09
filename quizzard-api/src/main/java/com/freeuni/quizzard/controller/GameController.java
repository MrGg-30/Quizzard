//package com.freeuni.quizzard.controller;
//
//import com.freeuni.quizzard.dto.QuestionDto;
//import com.freeuni.quizzard.dto.QuizDto;
//import com.freeuni.quizzard.model.GameSession;
//import com.freeuni.quizzard.model.GameStartRequest;
//import com.freeuni.quizzard.model.SubmittedAnswer;
//import com.freeuni.quizzard.service.GameSessionService;
//import com.freeuni.quizzard.service.QuizService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class GameController {
//
//    private final GameSessionService gameSessionService;
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    private final QuizService quizService;
//
//
//    @PostMapping("/createGame")
//    public ResponseEntity<String> createGameSession(@RequestBody GameStartRequest request) {
//        List<QuestionDto> questions = quizService.getRandomSequenceQuiz(request.getCategory()).getQuestions();
//        if (questions == null || questions.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        GameSession gameSession = gameSessionService.createGameSession(request.getUsername1(), request.getUsername2(), request.getCategory(), questions);
//
//        return ResponseEntity.ok(gameSession.getSessionId());
//    }
//
//    @MessageMapping("/startGame/{sessionId}")
//    public void startGame(@DestinationVariable String sessionId) {
//        GameSession gameSession = gameSessionService.getGameSessionById(sessionId);
//
//        if (gameSession != null) {
//            // Logic to start the game session and send questions to players
//            QuestionDto firstQuestion = gameSession.getCurrentQuestion();
//
//            String username1 = gameSession.getUsername1();
//            String username2 = gameSession.getUsername2();
//
//            // Notify players about the start of the game and send the first question
//            messagingTemplate.convertAndSendToUser(username1, "/queue/gameStart", firstQuestion);
//            messagingTemplate.convertAndSendToUser(username2, "/queue/gameStart", firstQuestion);
//        }
//    }
//
//
//    @MessageMapping("/submitAnswer/{sessionId}")
//    public void submitAnswer(@DestinationVariable String sessionId, SubmittedAnswer submittedAnswer) {
//        GameSession gameSession = gameSessionService.getGameSessionById(sessionId);
//
//        if (gameSession != null) {
//            Player currentPlayer = gameSession.getCurrentPlayer();
//            Player opponent = gameSession.getOpponent(currentPlayer);
//
//            // Process the submitted answer and update game state
//            boolean isCorrect = processAnswer(submittedAnswer, gameSession.getCurrentQuestion());
//            gameSession.updatePlayerScore(currentPlayer, isCorrect);
//
//            // Notify the current player about the result of their answer
//            messagingTemplate.convertAndSendToUser(currentPlayer.getUsername(), "/queue/answerResult", isCorrect);
//
//            // Notify the opponent about the opponent's answer
//            messagingTemplate.convertAndSendToUser(opponent.getUsername(), "/queue/opponentAnswer", submittedAnswer);
//
//            // Move to the next question or end the game if all questions are answered
//            if (gameSession.hasMoreQuestions()) {
//                gameSession.moveToNextQuestion();
//                messagingTemplate.convertAndSendToUser(currentPlayer.getUsername(), "/queue/nextQuestion", gameSession.getCurrentQuestion());
//            } else {
//                // Determine the winner and end the game
//                Player winner = gameSession.getWinner();
//                messagingTemplate.convertAndSend("/topic/gameEnd/" + sessionId, winner);
//            }
//        }
//    }
//}
//
