package com.emrkoc.assignments.comeon.intake.exception;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException() {
        super("Player not found!");
    }

    public PlayerNotFoundException(String name) {
        super(String.format("Player with name:%s not found!", name));
    }
}
