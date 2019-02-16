package com.emrkoc.assignments.comeon.persistence;

import com.emrkoc.assignments.comeon.persistence.model.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LovedGameTest {

    @Mock
    private Game game;

    private LovedGame lovedGame = new LovedGame(0, game);

    @Test
    public void shouldIncrementGameLoveCount() {
        int lovedCount = lovedGame.getCount();
        lovedGame.incrementLovedCount();
        assertThat(lovedGame.getCount()).isEqualTo(lovedCount + 1);
    }
}