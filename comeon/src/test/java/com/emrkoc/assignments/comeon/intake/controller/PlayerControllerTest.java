package com.emrkoc.assignments.comeon.intake.controller;

import com.emrkoc.assignments.comeon.intake.service.PlayerService;
import com.emrkoc.assignments.comeon.persistence.model.Player;
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
public class PlayerControllerTest {
    @InjectMocks
    private PlayerController controller;

    @Mock
    private PlayerService playerService;

    @Mock
    private Player player;

    @Test
    public void shouldCreatePlayer() {
        String playerName = "Emrullah";
        when(player.getName()).thenReturn(playerName);

        String response = controller.createPlayer(this.player);

        verify(playerService).createPlayer(player);

        assertThat(response).contains(playerName);
    }

    @Test
    public void shouldReturnPlayersWithGivenName() {
        String playerName = "Emrullah";

        List<Player> players = Collections.singletonList(player);
        when(playerService.getPlayerByName(playerName)).thenReturn(players);

        List<Player> returnedPlayers = controller.getPlayer(playerName);

        verify(playerService).getPlayerByName(playerName);

        assertThat(returnedPlayers).hasSize(1).containsExactly(player);
    }

}