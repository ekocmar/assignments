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
@Table(name = "player_tbl")
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYER_ID")
    private long id;

    @Column(name = "PLAYER_NAME")
    private String name;

    @Column(name = "PLAYER_SURNAME")
    private String surname;

    protected Player() {
    }

    public Player(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

}
