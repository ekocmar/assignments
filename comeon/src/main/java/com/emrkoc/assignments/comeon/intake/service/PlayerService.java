package com.emrkoc.assignments.comeon.intake.service;

import com.emrkoc.assignments.comeon.intake.exception.PlayerNotFoundException;
import com.emrkoc.assignments.comeon.persistence.PlayerDao;
import com.emrkoc.assignments.comeon.persistence.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PlayerService {

    private final PlayerDao playerDao;

    @Autowired
    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public Player createPlayer(Player player) {
        return playerDao.save(player);
    }

    public Player getPlayerById(Long id) {
        return playerDao.findById(id).orElseThrow(PlayerNotFoundException::new);
    }

    public List<Player> getPlayerByName(String name) {
        List<Player> players = playerDao.getPlayerByNameIsLike(name);
        if (players.isEmpty()) {
            log.error("Player with the name:{} not found.", name);
            throw new PlayerNotFoundException(name);
        }
        return players;
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        playerDao.findAll().forEach(players::add);
        return players;
    }
}
