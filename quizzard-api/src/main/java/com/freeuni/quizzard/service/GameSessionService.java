package com.freeuni.quizzard.service;

import com.freeuni.quizzard.dto.QuestionDto;
import com.freeuni.quizzard.dto.QuizDto;
import com.freeuni.quizzard.model.GameSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class GameSessionService {

    private final Map<String, GameSession> activeGameSessions = new ConcurrentHashMap<>();

    private final QuizService quizService;

    public GameSession createGameSession(String currentPlayer, String opponentPlayer, String category) {
        String sessionId = generateSessionId(currentPlayer, opponentPlayer, category);
        // If the key is already present
        if (activeGameSessions.containsKey(sessionId)) {
            return activeGameSessions.get(sessionId);
        }

        QuizDto quizDto = quizService.getRandomSequenceQuiz(category);
        if (quizDto == null) {
            throw new NotFoundException("Quiz By This Category not found");
        }
        List<QuestionDto> questions = quizDto.getQuestions();
        GameSession gameSession = new GameSession(sessionId, currentPlayer, opponentPlayer, questions);
        activeGameSessions.put(sessionId, gameSession);
        return gameSession;
    }

    public GameSession getGameSessionById(String sessionId) {
        return activeGameSessions.get(sessionId);
    }

    public void removeGameSession(String sessionId) {
        activeGameSessions.remove(sessionId);
    }

    // Other methods for managing game sessions, such as updating scores, moving to the next question, etc.

    private String generateSessionId(String username1, String username2, String category) {
        // Logic to generate a unique session ID
        return username1 + "_" + username2 + "_" + category;
    }
}

