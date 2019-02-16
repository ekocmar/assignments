package com.emrkoc.assignments.comeon.intake.controller;

import com.emrkoc.assignments.comeon.intake.service.GameLoveService;
import com.emrkoc.assignments.comeon.intake.service.GameService;
import com.emrkoc.assignments.comeon.intake.service.PlayerService;
import com.emrkoc.assignments.comeon.persistence.LovedGame;
import com.emrkoc.assignments.comeon.persistence.model.Game;
import com.emrkoc.assignments.comeon.persistence.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GameLoveController {
    private final GameLoveService gameLoveService;
    private final PlayerService playerService;
    private final GameService gameService;

    @Autowired
    public GameLoveController(GameLoveService gameLoveService, PlayerService playerService, GameService gameService) {
        this.gameLoveService = gameLoveService;
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @GetMapping(path = "/ws/v0/lovedGames")
    @ResponseBody
    public List<LovedGame> getLovedGames(@RequestParam(value = "top", required = false) Integer top) {
        return gameLoveService.getLovedGames(top);
    }

    @GetMapping(path = "/ws/v0/player/{playerId}/lovedGames")
    @ResponseBody
    public List<Game> getPlayersLovedGames(@PathVariable("playerId") Long playerId) {
        return gameLoveService.getPlayersLovedGames(playerService.getPlayerById(playerId));
    }

    @PostMapping(path = "/ws/v0/love")
    @ResponseBody
    public String playerLovedGame(@RequestParam(value = "playerId") Long playerId,
                                  @RequestParam(value = "gameId") Long gameId) {
        Player player = playerService.getPlayerById(playerId);
        Game game = gameService.getGameById(gameId);
        gameLoveService.playerLovedGame(player, game);

        return String.format("%s loved game %s", player.getName(), game.getName());
    }

    @PostMapping(path = "/ws/v0/unlove")
    @ResponseBody
    public String playerUnlovedGame(@RequestParam(value = "playerId") Long playerId,
                                    @RequestParam(value = "gameId") Long gameId) {
        Player player = playerService.getPlayerById(playerId);
        Game game = gameService.getGameById(gameId);
        gameLoveService.playerUnlovedGame(player, game);
        return String.format("%s unloved game %s", player.getName(), game.getName());
    }
}
