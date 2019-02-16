package com.emrkoc.assignments.comeon.intake.controller;

import com.emrkoc.assignments.comeon.intake.service.GameService;
import com.emrkoc.assignments.comeon.persistence.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

@Controller
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(path = "/ws/v0/game/create")
    @ResponseBody
    public String createGame(@RequestBody Game game) {

        Game createdGame = gameService.createGame(game);

        return String.format("Game with name:%s created.", createdGame.getName());
    }

    @GetMapping(path = "/ws/v0/game")
    @ResponseBody
    public List<Game> getGame(@RequestParam(value = "id", required = false) Long gameId,
                              @RequestParam(value = "name", required = false) String gameName) {
        if (gameId != null) {
            return Collections.singletonList(gameService.getGameById(gameId));
        }
        if (gameName != null) {
            return gameService.getGameByName(gameName);
        }
        return gameService.getAllGames();
    }

    @PutMapping(path = "/ws/v0/game/update")
    @ResponseBody
    public Game updateGame(@RequestBody Game game) {
        return gameService.updateGame(game);
    }
}
