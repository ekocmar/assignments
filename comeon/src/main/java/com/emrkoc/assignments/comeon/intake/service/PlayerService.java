package com.emrkoc.assignments.comeon.intake.service;

import com.emrkoc.assignments.comeon.intake.exception.PlayerNotFoundException;
import com.emrkoc.assignments.comeon.persistence.PlayerDao;
import com.emrkoc.assignments.comeon.persistence.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerDao playerDao;

    @Autowired
    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void createPlayer(Player player) {
        Player savedPlayer = playerDao.save(player);
    }

    public Player getPlayerById(Long id) {
        return playerDao.findById(id).orElseThrow(PlayerNotFoundException::new);
    }

    public List<Player> getPlayerByName(String name) {
        List<Player> players = playerDao.getPlayerByNameIsLike(name);
        if (players.isEmpty()) {
            throw new PlayerNotFoundException(name);
        }
        return players;
    }
}
