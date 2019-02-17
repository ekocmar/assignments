package com.emrkoc.assignments.comeon.intake.controller;

import com.emrkoc.assignments.comeon.intake.service.GameLoveService;
import com.emrkoc.assignments.comeon.intake.service.GameService;
import com.emrkoc.assignments.comeon.intake.service.PlayerService;
import com.emrkoc.assignments.comeon.persistence.LovedGame;
import com.emrkoc.assignments.comeon.persistence.model.Game;
import com.emrkoc.assignments.comeon.persistence.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameLoveControllerTest {
    @InjectMocks
    private GameLoveController controller;

    @Mock
    private PlayerService playerService;

    @Mock
    private GameLoveService gameLoveService;

    @Mock
    private GameService gameService;

    private Player player = new Player("Emrullah", "Kocmar");
    private List<LovedGame> lovedGames;
    private Game pokerGame = new Game("Poker", "Casino", "ComeOn");
    private Game bjGame = new Game("Blackjack", "Casino", "ComeOn");
    private Game diabloGame = new Game("Diablo III", "Action RPG", "Blizzard");

    @Before
    public void init() {

        LovedGame lovedGameDiablo = new LovedGame(5, diabloGame);
        LovedGame lovedGamePoker = new LovedGame(4, pokerGame);
        LovedGame lovedGameBj = new LovedGame(3, bjGame);
        lovedGames = new ArrayList<>();
        lovedGames.add(lovedGameBj);
        lovedGames.add(lovedGameDiablo);
        lovedGames.add(lovedGamePoker);
    }

    @Test
    public void shouldReturnLovedGames() {
        when(gameLoveService.getLovedGames(null)).thenReturn(lovedGames);
        List<LovedGame> lovedGames = controller.getLovedGames(null);

        verify(gameLoveService).getLovedGames(null);

        assertThat(lovedGames).hasSize(3);
        assertThat(lovedGames.stream().map(LovedGame::getGame).collect(Collectors.toList()))
                .hasSize(3).containsExactly(bjGame, diabloGame, pokerGame);
    }

    @Test
    public void shouldLoveGame_WhenPlayerLovesAGame() {
        player.setId(1L);
        diabloGame.setId(1L);
        when(playerService.getPlayerById(1L)).thenReturn(player);
        when(gameService.getGameById(1L)).thenReturn(diabloGame);
        String response = controller.playerLovedGame(1L, 1L);

        verify(gameLoveService).playerLovedGame(player, diabloGame);

        assertThat(response).contains(player.getName(), diabloGame.getName());
    }

}