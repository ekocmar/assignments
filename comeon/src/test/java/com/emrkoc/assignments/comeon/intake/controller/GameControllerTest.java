package com.emrkoc.assignments.comeon.intake.controller;

import com.emrkoc.assignments.comeon.intake.service.GameService;
import com.emrkoc.assignments.comeon.persistence.model.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {
    @InjectMocks
    private GameController controller;

    @Mock
    private GameService gameService;

    @Mock
    private Game game;

    @Test
    public void shouldCreateGame() {
        String gameName = "Poker";
        when(game.getName()).thenReturn(gameName);
        when(gameService.createGame(game)).thenReturn(game);
        String response = controller.createGame(game);

        verify(gameService).createGame(game);

        assertThat(response).contains(gameName);
    }

    @Test
    public void shouldUpdateGame() {
        Game updatedGame = new Game("Poker", "Casino", "Zynga");
        when(gameService.updateGame(game)).thenReturn(updatedGame);

        Game response = controller.updateGame(game);

        verify(gameService).updateGame(game);

        assertThat(response).isEqualTo(updatedGame);
    }

    @Test
    public void shouldReturnGame_WhenValidIdProvided() {
        when(gameService.getGameById(1L)).thenReturn(game);

        List<Game> games = controller.getGame(1L, null);

        verify(gameService).getGameById(1L);

        assertThat(games).hasSize(1).containsExactly(game);
    }

    @Test
    public void shouldReturnGame_WhenValidNameProvided() {
        String gameName = "Poker";
        when(gameService.getGameByName(gameName)).thenReturn(Collections.singletonList(game));

        List<Game> games = controller.getGame(null, "Poker");

        verify(gameService).getGameByName(gameName);

        assertThat(games).hasSize(1).containsExactly(game);
    }

    @Test
    public void shouldReturnAllGames_WhenNoParamProvided() {
        when(gameService.getAllGames()).thenReturn(Collections.singletonList(game));

        List<Game> games = controller.getGame(null, null);

        verify(gameService).getAllGames();

        assertThat(games).hasSize(1).containsExactly(game);
    }

}