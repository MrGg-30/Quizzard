package com.freeuni.quizzard;

public class AlreadyUsedException extends RuntimeException {

    public AlreadyUsedException(String message) {
        super(message);
    }
}
