package com.codeoftheweb.salvoapp.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Salvo {

     /*-------
    ATRIBUTOS
    -------*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private Integer turn;

    //Esta anotation de LazyCollection es para que funcionen las listas y no tire error de MultipleBagFetchException: cannot simultaneously fetch multiple bags,
    // además se sacan los fetch type de todos los @*ToMany que se relacionen
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> locations = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

     /*-----
    METODOS
    -----*/

    //CONSTRUCTORES
    public Salvo() {
    }

    public Salvo(Integer turn, List<String> locations) {
        this.turn = turn;
        this.locations = locations;
    }

    //GETTERS Y SETTERS
    public Long getId() {
        return this.id;
    }

    public Integer getTurn() {
        return this.turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public List<String> getLocations() {
        return this.locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public GamePlayer getGamePlayer() {
        return this.gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    //DTO (data transfer object) para administrar la info de Salvo
    public Map<String, Object> salvoDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("player", this.getGamePlayer().getPlayer().getId());
        dto.put("turn", this.getTurn());

        dto.put("locations", this.getLocations());
        return dto;
    }
}


