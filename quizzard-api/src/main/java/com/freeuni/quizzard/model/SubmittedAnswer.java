package com.freeuni.quizzard.model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class SubmittedAnswer {

    private String answer;

    private LocalTime duration;

}
