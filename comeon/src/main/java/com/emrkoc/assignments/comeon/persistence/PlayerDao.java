package com.emrkoc.assignments.comeon.persistence;

import com.emrkoc.assignments.comeon.persistence.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerDao extends CrudRepository<Player, Long> {

    List<Player> getPlayerByNameIsLike(String name);

}
