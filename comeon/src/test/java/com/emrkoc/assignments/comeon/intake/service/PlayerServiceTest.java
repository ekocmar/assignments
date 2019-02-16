package com.emrkoc.assignments.comeon.intake.service;

import com.emrkoc.assignments.comeon.intake.exception.PlayerNotFoundException;
import com.emrkoc.assignments.comeon.persistence.PlayerDao;
import com.emrkoc.assignments.comeon.persistence.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {
    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerDao playerDao;

    @Mock
    private Player player;

    @Captor
    private ArgumentCaptor<Player> playerCaptor;

    String PLAYER_NAME = "Emrullah";

    @Before
    public void init() {
        when(player.getId()).thenReturn(1L);
        when(player.getName()).thenReturn(PLAYER_NAME);
    }

    @Test
    public void shouldCreatePlayer() {
        playerService.createPlayer(player);

        verify(playerDao).save(playerCaptor.capture());

        Player createdPlayer = playerCaptor.getValue();

        assertThat(player).isEqualTo(createdPlayer);
    }

    @Test
    public void shouldReturnPlayer_WhenPlayerIdProvided() {
        when(playerDao.findById(1L)).thenReturn(Optional.of(player));

        Player playerById = playerService.getPlayerById(1L);

        verify(playerDao).findById(1L);
        assertThat(playerById.getId()).isEqualTo(1L);
        assertThat(playerById.getName()).isEqualTo(PLAYER_NAME);
    }

    @Test(expected = PlayerNotFoundException.class)
    public void shouldThrowException_WhenPlayerNotFound() {
        playerService.getPlayerById(1L);
    }

    @Test
    public void shouldReturnPlayers_WhenPlayerNameProvided() {

        when(playerDao.getPlayerByNameIsLike(PLAYER_NAME)).thenReturn(Collections.singletonList(player));

        List<Player> players = playerService.getPlayerByName(PLAYER_NAME);

        verify(playerDao).getPlayerByNameIsLike(PLAYER_NAME);

        assertThat(players).hasSize(1).containsExactly(player);
    }

    @Test(expected = PlayerNotFoundException.class)
    public void shouldThrowError_WhenPlayerNotFoundWithName() {
        playerService.getPlayerByName(PLAYER_NAME);
    }


}