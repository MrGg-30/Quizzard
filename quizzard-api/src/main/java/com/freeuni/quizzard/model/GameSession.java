package com.freeuni.quizzard.model;

import com.freeuni.quizzard.dto.QuestionDto;
import lombok.Getter;

import java.util.List;

@Getter
public class GameSession {

    private List<QuestionDto> questions;
    private int currentQuestionIndex;
    private String sessionId;

    private String currentPlayer;

    private String opponentPlayer;

    public GameSession(String sessionId, String currentPlayer, String opponentPlayer, List<QuestionDto> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.sessionId = sessionId;
        this.currentPlayer = currentPlayer;
        this.opponentPlayer = opponentPlayer;
    }

    public void moveToNextQuestion() {
        if (hasMoreQuestions()) {
            currentQuestionIndex++;
        }
    }
    public boolean hasMoreQuestions() {
        return currentQuestionIndex < questions.size() - 1;
    }


    public QuestionDto getCurrentQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }
}

