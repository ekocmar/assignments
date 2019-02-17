package com.emrkoc.assignments.comeon.intake.service;

import com.emrkoc.assignments.comeon.intake.exception.GameAlreadyExistException;
import com.emrkoc.assignments.comeon.intake.exception.GameNotFoundException;
import com.emrkoc.assignments.comeon.persistence.GameDao;
import com.emrkoc.assignments.comeon.persistence.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GameService {
    private final GameDao gameDao;

    @Autowired
    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public Game createGame(Game game) {
        if (gameDao.findById(game.getId()).isPresent()) {
            throw new GameAlreadyExistException();
        }
        return gameDao.save(game);
    }

    public Game getGameById(Long gameId) {
        return gameDao.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(String.format("Game with id:%s not found!", gameId)));

    }

    public List<Game> getGameByName(String gameName) {
        return gameDao.findByNameLike(gameName);
    }

    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        gameDao.findAll().forEach(games::add);
        return games;
    }

    public Game updateGame(Game game) {
        if (gameDao.findById(game.getId()).isPresent()) {
            return gameDao.save(game);
        } else {
            log.error("Game with id:{} not found.", game.getId());
            throw new GameNotFoundException(String.format("Game with id:%s not found.", game.getId()));
        }
    }
}
