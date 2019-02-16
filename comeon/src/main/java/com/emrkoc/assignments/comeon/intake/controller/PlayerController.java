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

import java.util.List;

@Controller
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService, GameLoveService gameLoveService) {
        this.playerService = playerService;
    }

    @PostMapping(path = "/ws/v0/player/create")
    @ResponseBody
    public String createPlayer(@RequestBody Player player) {
        playerService.createPlayer(player);
        return String.format("%s created.", player.getName());
    }

    @GetMapping(path = "/ws/v0/player")
    @ResponseBody
    public List<Player> getPlayer(@RequestParam("name") String name) {
        return playerService.getPlayerByName(name);
    }


}
