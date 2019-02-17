package com.emrkoc.assignments.comeon.intake.service;

import com.emrkoc.assignments.comeon.intake.exception.GameAlreadyExistException;
import com.emrkoc.assignments.comeon.intake.exception.GameNotFoundException;
import com.emrkoc.assignments.comeon.persistence.GameDao;
import com.emrkoc.assignments.comeon.persistence.model.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
    @InjectMocks
    private GameService gameService;

    @Mock
    private GameDao gameDao;

    @Captor
    private ArgumentCaptor<Game> gameArgumentCaptor;

    private Game game = new Game("Poker", "Casino", "ComeOn");

    @Test
    public void shouldCreateGame() {
        gameService.createGame(game);

        verify(gameDao).save(gameArgumentCaptor.capture());

        assertThat(game).isEqualTo(gameArgumentCaptor.getValue());
    }

    @Test
    public void shouldReturnGame_WhenValidIdProvided() {
        when(gameDao.findById(1L)).thenReturn(Optional.of(game));
        Game gameById = gameService.getGameById(1L);

        verify(gameDao).findById(1L);

        assertThat(gameById).isEqualTo(game);
    }

    @Test
    public void shouldReturnGame_WhenNameIsGiven() {
        String gameName = "Poker";
        when(gameDao.findByNameLike(gameName)).thenReturn(Collections.singletonList(game));

        List<Game> games = gameService.getGameByName(gameName);

        verify(gameDao).findByNameLike(gameName);

        assertThat(games).hasSize(1).containsExactly(game);
    }

    @Test
    public void shouldReturnAllGames() {
        Iterable<Game> iterable = Arrays.asList(game);
        when(gameDao.findAll()).thenReturn(iterable);

        List<Game> allGames = gameService.getAllGames();

        verify(gameDao).findAll();

        assertThat(allGames).hasSize(1).containsExactly(game);
    }

    @Test
    public void shouldUpdateGame() {
        game.setId(1L);
        when(gameDao.findById(1L)).thenReturn(Optional.of(game));

        gameService.updateGame(game);

        verify(gameDao).findById(1L);
        verify(gameDao).save(gameArgumentCaptor.capture());

        assertThat(gameArgumentCaptor.getValue()).isEqualTo(game);
    }

    @Test(expected = GameNotFoundException.class)
    public void shouldThrowError_WhenGameNotFountToUpdate() {
        gameService.updateGame(game);
    }

    @Test(expected = GameNotFoundException.class)
    public void shouldThrowError_WhenGameNotFoundWithGivenId() {
        gameService.getGameById(1L);
    }

    @Test(expected = GameAlreadyExistException.class)
    public void shouldThrowError_WhenGameIsAlreadyExists() {
        game.setId(1L);
        when(gameDao.findById(1L)).thenReturn(Optional.of(game));

        gameService.createGame(game);
    }
}