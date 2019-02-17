package com.emrkoc.assignments.comeon.intake.service;

import com.emrkoc.assignments.comeon.persistence.GameLoveDao;
import com.emrkoc.assignments.comeon.persistence.LovedGame;
import com.emrkoc.assignments.comeon.persistence.model.Game;
import com.emrkoc.assignments.comeon.persistence.model.GameLove;
import com.emrkoc.assignments.comeon.persistence.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameLoveService {

    private final GameLoveDao gameLoveDao;

    @Autowired
    public GameLoveService(GameLoveDao gameLoveDao) {
        this.gameLoveDao = gameLoveDao;
    }

    public List<Game> getPlayersLovedGames(Player player) {
        return gameLoveDao.findAllByPlayerEqualsAndLovedEquals(player, true).stream()
                .map(GameLove::getGame)
                .collect(Collectors.toList());
    }

    public List<LovedGame> getLovedGames(Integer top) {
        List<LovedGame> lovedGames = new ArrayList<>();
        List<Game> allLovedGames = gameLoveDao.findAllByLovedEquals(true).stream()
                .map(GameLove::getGame)
                .collect(Collectors.toList());

        for (Game game : allLovedGames) {
            countLoved(game, lovedGames);
        }

        lovedGames.sort((g1, g2) -> Integer.compare(g2.getCount(), g1.getCount()));
        if (top != null && top < lovedGames.size()) {
            return lovedGames.subList(0, top);
        }
        return lovedGames;
    }

    public void playerLovedGame(Player player, Game game) {
        Optional<GameLove> gameLove = gameLoveDao.findByPlayerEqualsAndGameEquals(player, game);
        if (gameLove.isPresent()) {
            GameLove gameLoved = gameLove.get();
            gameLoved.setLoved(true);
            gameLoveDao.save(gameLoved);
        } else {
            GameLove newEntry = new GameLove(player, game, true);
            gameLoveDao.save(newEntry);
        }
    }

    public GameLove playerUnlovedGame(Player player, Game game) {
        Optional<GameLove> gameLove = gameLoveDao.findByPlayerEqualsAndGameEquals(player, game);
        if (gameLove.isPresent()) {
            GameLove gameUnloved = gameLove.get();
            gameUnloved.setLoved(false);
            return gameLoveDao.save(gameUnloved);
        } else {
            GameLove newEntry = new GameLove(player, game, false);
            return gameLoveDao.save(newEntry);
        }
    }

    private void countLoved(Game gameToCount, List<LovedGame> games) {
        Optional<LovedGame> foundGame = games.stream()
                .filter(lovedGame -> lovedGame.getGame().getId() == gameToCount.getId())
                .findFirst();
        if (foundGame.isPresent()) {
            foundGame.get().incrementLovedCount();
        } else {
            games.add(new LovedGame(1, gameToCount));
        }
    }
}
