package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.Question;
import com.freeuni.quizzard.dto.QuestionDto;
import com.freeuni.quizzard.model.GameSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameSessionService {

    private final Map<String, GameSession> activeGameSessions = new ConcurrentHashMap<>();

    public GameSession createGameSession(String currentPlayer, String opponentPlayer, String category, List<QuestionDto> questions) {
        String sessionId = generateSessionId(currentPlayer, opponentPlayer, category);
        // If the key is already present
        if (activeGameSessions.containsKey(sessionId)) {
            return activeGameSessions.get(sessionId);
        }

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

