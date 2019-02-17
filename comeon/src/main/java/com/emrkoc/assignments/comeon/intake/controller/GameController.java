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

    /**
     * This post mapping provides an endpoint to create new game
     *
     * @param game JSON Game object to be created
     * @return created {@link Game}.
     */
    @PostMapping(path = "/ws/v0/game/create")
    @ResponseBody
    public Game createGame(@RequestBody Game game) {
        return gameService.createGame(game);
    }

    /**
     * This get mapping provides an endpoint to query games with the given parameters if there is any.
     * If no parameter provided, it will return all games
     *
     * @param gameId   id of the game queried.
     * @param gameName name of the game queried.
     * @return a list of {@link Game}
     */
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

    /**
     * This put mapping provides an endpoint to update an existing game.
     *
     * @param game JSON Game object to be updated.
     * @return updated {@link Game}
     */
    @PutMapping(path = "/ws/v0/game/update")
    @ResponseBody
    public Game updateGame(@RequestBody Game game) {
        return gameService.updateGame(game);
    }
}
