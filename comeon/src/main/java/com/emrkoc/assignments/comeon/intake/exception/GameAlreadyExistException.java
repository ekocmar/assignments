package com.emrkoc.assignments.comeon.intake.exception;

import com.emrkoc.assignments.comeon.persistence.model.Game;

public class GameAlreadyExistException extends RuntimeException {
    public GameAlreadyExistException() {
        super("Game already exists.");
    }

    public GameAlreadyExistException(Game game) {
        super(String.format("Game with the name:%s is already exists.", game.getName()));
    }
}
