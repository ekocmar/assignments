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
@Table(name = "game_tbl")
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GAME_ID")
    private Long id;

    @Column(name = "GAME_NAME")
    private String name;

    @Column(name = "GAME_GENRE")
    private String genre;

    @Column(name = "GAME_MANUFACTURER")
    private String manufacturer;

    protected Game() {
    }

    public Game(String name, String genre, String manufacturer) {
        this.name = name;
        this.genre = genre;
        this.manufacturer = manufacturer;
    }
}
