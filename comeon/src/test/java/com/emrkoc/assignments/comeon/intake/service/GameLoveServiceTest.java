package com.emrkoc.assignments.comeon.intake.service;

import com.emrkoc.assignments.comeon.persistence.GameLoveDao;
import com.emrkoc.assignments.comeon.persistence.model.Game;
import com.emrkoc.assignments.comeon.persistence.model.GameLove;
import com.emrkoc.assignments.comeon.persistence.model.Player;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;
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
    private Game pokerGame = new Game("Poker", "Casıno", "ComeOn");
    private Game blackjackGame = new Game("Balckjack", "Casino", "ComeOn");

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
}