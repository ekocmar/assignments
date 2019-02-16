package com.emrkoc.assignments.comeon.persistence;

import com.emrkoc.assignments.comeon.persistence.model.Game;
import com.emrkoc.assignments.comeon.persistence.model.GameLove;
import com.emrkoc.assignments.comeon.persistence.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameLoveDao extends CrudRepository<GameLove, Long> {

    List<GameLove> findAllByPlayerEqualsAndLovedEquals(Player player, boolean loved);

    List<GameLove> findAllByLovedEquals(boolean loved);

    Optional<GameLove> findByPlayerEqualsAndGameEquals(Player player, Game game);
}
