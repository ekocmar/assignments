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

    /**
     * This get mapping provides an endpoint to query loved games
     *
     * @param top used to sublist the return list if provided otherwise all loved games will be returned
     * @return a sorted list of {@link LovedGame}
     */
    @GetMapping(path = "/ws/v0/lovedGames")
    @ResponseBody
    public List<LovedGame> getLovedGames(@RequestParam(value = "top", required = false) Integer top) {
        return gameLoveService.getLovedGames(top);
    }

    /**
     * This get mapping provides an endpoint to query all loved games by given player.
     *
     * @param playerId id of the player to query
     * @return a list of {@link Game} which loved by given player.
     */
    @GetMapping(path = "/ws/v0/player/{playerId}/lovedGames")
    @ResponseBody
    public List<Game> getPlayersLovedGames(@PathVariable("playerId") Long playerId) {
        return gameLoveService.getPlayersLovedGames(playerService.getPlayerById(playerId));
    }

    /**
     * This post mapping provides an endpoint to add/update
     * {@link com.emrkoc.assignments.comeon.persistence.model.GameLove} entity as loved.
     *
     * @param playerId id of the player loves the game
     * @param gameId   id of the game loved
     * @return success message including player name and game name
     */
    @PostMapping(path = "/ws/v0/love")
    @ResponseBody
    public String playerLovedGame(@RequestParam(value = "playerId") Long playerId,
                                  @RequestParam(value = "gameId") Long gameId) {
        Player player = playerService.getPlayerById(playerId);
        Game game = gameService.getGameById(gameId);
        gameLoveService.playerLovedGame(player, game);

        return String.format("%s loved game %s", player.getName(), game.getName());
    }

    /**
     * This post mapping provides an endpoint to add/update
     * {@link com.emrkoc.assignments.comeon.persistence.model.GameLove} entity as unloved.
     *
     * @param playerId id of the player unloved the game
     * @param gameId   id of the game unloved
     * @return success message including player name and game name
     */
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
