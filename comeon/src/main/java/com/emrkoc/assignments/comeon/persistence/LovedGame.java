package com.emrkoc.assignments.comeon.persistence;

import com.emrkoc.assignments.comeon.persistence.model.Game;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class LovedGame {
    private int count;
    private Game game;

    public void incrementLovedCount() {
        this.count++;
    }
}
