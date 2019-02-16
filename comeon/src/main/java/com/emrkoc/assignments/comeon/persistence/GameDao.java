package com.emrkoc.assignments.comeon.persistence;

import com.emrkoc.assignments.comeon.persistence.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameDao extends CrudRepository<Game, Long> {

    List<Game> findByNameLike(String name);
}
