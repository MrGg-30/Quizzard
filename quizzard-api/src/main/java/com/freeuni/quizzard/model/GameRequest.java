package com.freeuni.quizzard.model;

import com.freeuni.quizzard.dto.QuestionDto;
import lombok.Data;

import java.util.List;

@Data
public class GameRequest {

    private String category;
    private String to;
    private String from;
    private RequestStatus status;
    private List<QuestionDto> questions;
    private String sessionId;
}