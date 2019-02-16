package com.emrkoc.assignments.comeon.intake.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException() {
    }

    public GameNotFoundException(String message) {
        super(message);
    }
}
