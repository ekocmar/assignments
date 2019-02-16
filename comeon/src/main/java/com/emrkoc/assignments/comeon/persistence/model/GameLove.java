package com.emrkoc.assignments.comeon.persistence.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "game_love_tbl")
public class GameLove implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "PLAYER_ID")
    private Player player;

    @OneToOne
    @JoinColumn(name = "GAME_ID")
    private Game game;

    @Column(name = "LOVED")
    private boolean loved;

    protected GameLove() {
    }

    public GameLove(Player player, Game game, boolean loved) {
        this.player = player;
        this.game = game;
        this.loved = loved;
    }
}
