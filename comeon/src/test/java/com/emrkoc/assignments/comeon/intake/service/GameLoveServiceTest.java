package com.emrkoc.assignments.comeon.intake.service;

import com.emrkoc.assignments.comeon.persistence.GameLoveDao;
import com.emrkoc.assignments.comeon.persistence.LovedGame;
import com.emrkoc.assignments.comeon.persistence.model.Game;
import com.emrkoc.assignments.comeon.persistence.model.GameLove;
import com.emrkoc.assignments.comeon.persistence.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameLoveServiceTest {
    @InjectMocks
    private GameLoveService gameLoveService;

    @Mock
    private GameLoveDao gameLoveDao;

    @Captor
    private ArgumentCaptor<GameLove> gameLoveArgumentCaptor;

    private Player player = new Player("Emrullah", "Kocmar");
    private Player playerTwo = new Player("Test", "Player");
    private Game pokerGame = new Game("Poker", "Casıno", "ComeOn");
    private Game blackjackGame = new Game("Balckjack", "Casino", "ComeOn");
    private Game diabloGame = new Game("Diablo III", "Action RPG", "Blizzard");

    @Before
    public void init() {
        player.setId(1L);
        playerTwo.setId(2L);
        pokerGame.setId(1L);
        blackjackGame.setId(2L);
        diabloGame.setId(3L);
    }

    @Test
    public void shouldReturnLovedGames_WhenPlayerIdProvıded() {
        GameLove gameLovePoker = new GameLove(player, pokerGame, true);
        GameLove gameLoveBJ = new GameLove(player, blackjackGame, true);
        when(gameLoveDao.findAllByPlayerEqualsAndLovedEquals(player, true))
                .thenReturn(Arrays.asList(gameLovePoker, gameLoveBJ));

        List<Game> playersLovedGames = gameLoveService.getPlayersLovedGames(player);

        verify(gameLoveDao).findAllByPlayerEqualsAndLovedEquals(player, true);

        assertThat(playersLovedGames).hasSize(2).containsExactly(pokerGame, blackjackGame);
    }

    @Test
    public void shouldLoveGame_WhenPlayerAndGameProvided() {
        gameLoveService.playerLovedGame(player, pokerGame);

        verify(gameLoveDao).save(gameLoveArgumentCaptor.capture());

        GameLove captured = gameLoveArgumentCaptor.getValue();
        assertThat(captured.getGame()).isEqualTo(pokerGame);
        assertThat(captured.getPlayer()).isEqualTo(player);
        assertThat(captured.isLoved()).isTrue();
    }

    @Test
    public void shouldUpdateLoveGame_WhenGameLoveIsAlreadyExists() {
        GameLove gameLove = new GameLove(player, blackjackGame, false);
        when(gameLoveDao.findByPlayerEqualsAndGameEquals(player, blackjackGame)).thenReturn(Optional.of(gameLove));

        gameLoveService.playerLovedGame(player, blackjackGame);

        verify(gameLoveDao).save(gameLoveArgumentCaptor.capture());

        GameLove captured = gameLoveArgumentCaptor.getValue();
        assertThat(captured.getPlayer()).isEqualTo(player);
        assertThat(captured.getGame()).isEqualTo(blackjackGame);
        assertThat(captured.isLoved()).isTrue();
    }

    @Test
    public void shouldUnloveGame_WhenPlayerAndGameProvided() {
        gameLoveService.playerUnlovedGame(player, blackjackGame);

        verify(gameLoveDao).save(gameLoveArgumentCaptor.capture());

        GameLove captured = gameLoveArgumentCaptor.getValue();

        assertThat(captured.getGame()).isEqualTo(blackjackGame);
        assertThat(captured.getPlayer()).isEqualTo(player);
        assertThat(captured.isLoved()).isFalse();
    }

    @Test
    public void shouldUpdateGameLoveAsUnloved_WhenGameLoveIsAlreadyExists() {
        GameLove gameLove = new GameLove(player, pokerGame, true);
        when(gameLoveDao.findByPlayerEqualsAndGameEquals(player, pokerGame)).thenReturn(Optional.of(gameLove));

        gameLoveService.playerUnlovedGame(player, pokerGame);

        verify(gameLoveDao).findByPlayerEqualsAndGameEquals(player, pokerGame);
        verify(gameLoveDao).save(gameLoveArgumentCaptor.capture());

        GameLove captured = gameLoveArgumentCaptor.getValue();
        assertThat(captured.getPlayer()).isEqualTo(player);
        assertThat(captured.getGame()).isEqualTo(pokerGame);
        assertThat(captured.isLoved()).isFalse();
    }

    @Test
    public void shouldGetAllLovedGames() {
        GameLove pokerLove = new GameLove(player, pokerGame, true);
        GameLove bjLove = new GameLove(player, blackjackGame, true);
        when(gameLoveDao.findAllByLovedEquals(true)).thenReturn(Arrays.asList(pokerLove, bjLove));

        List<LovedGame> lovedGames = gameLoveService.getLovedGames(null);

        verify(gameLoveDao).findAllByLovedEquals(true);

        assertThat(lovedGames).hasSize(2);
        assertThat(lovedGames.stream().map(LovedGame::getGame).collect(Collectors.toList()))
                .hasSize(2).containsExactly(pokerGame, blackjackGame);
    }

    @Test
    public void shouldReturnTopTwoLovedGame() {
        GameLove pokerLovePlayerOne = new GameLove(player, pokerGame, true);
        GameLove pokerLovePlayerTwo = new GameLove(playerTwo, pokerGame, true);
        GameLove diabloLovePlayerOne = new GameLove(player, diabloGame, true);
        GameLove diabloLovePlayerTwo = new GameLove(playerTwo, diabloGame, true);
        GameLove bjLovePlayerOne = new GameLove(player, blackjackGame, true);

        when(gameLoveDao.findAllByLovedEquals(true))
                .thenReturn(Arrays.asList(pokerLovePlayerOne, pokerLovePlayerTwo, diabloLovePlayerOne,
                        diabloLovePlayerTwo, bjLovePlayerOne));

        List<LovedGame> lovedGames = gameLoveService.getLovedGames(2);

        verify(gameLoveDao).findAllByLovedEquals(true);

        assertThat(lovedGames).hasSize(2);
        assertThat(lovedGames.stream().map(LovedGame::getGame).collect(Collectors.toList()))
                .hasSize(2).containsExactly(pokerGame, diabloGame);
        assertThat(lovedGames.stream().map(LovedGame::getCount).collect(Collectors.toList()))
                .hasSize(2).containsExactly(2, 2);
    }
}