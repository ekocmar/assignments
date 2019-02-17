package com.emrkoc.assignments.comeon.intake.controller;

import com.emrkoc.assignments.comeon.intake.service.GameLoveService;
import com.emrkoc.assignments.comeon.intake.service.PlayerService;
import com.emrkoc.assignments.comeon.persistence.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

@Controller
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService, GameLoveService gameLoveService) {
        this.playerService = playerService;
    }

    /**
     * This post mapping provides an endpoint to create new player.
     *
     * @param player JSON Player object to be created.
     * @return created {@link Player}
     */
    @PostMapping(path = "/ws/v0/player/create")
    @ResponseBody
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    /**
     * This get mapping provides an endpoint to query players with the given parameters if there is any.
     * If no parameter provided, it will return all players.
     *
     * @param id id of the player to query
     * @param name name of the player to query
     * @return
     */
    @GetMapping(path = "/ws/v0/player")
    @ResponseBody
    public List<Player> getPlayer(@RequestParam(value = "id", required = false) Long id,
                                  @RequestParam(value = "name", required = false) String name) {
        if (id != null) {
            return Collections.singletonList(playerService.getPlayerById(id));
        }
        if (name != null) {
            return playerService.getPlayerByName(name);
        }

        return playerService.getAllPlayers();
    }


}
