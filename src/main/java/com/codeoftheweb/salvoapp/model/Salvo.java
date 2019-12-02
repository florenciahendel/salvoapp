package com.codeoftheweb.salvoapp.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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
    private List<String> locations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
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

    public List<String> getHits(List<String> myShots, Set<Ship> opponentShips) {

        List<String> allEnemyLocs = new ArrayList<>();

        opponentShips.forEach(ship -> allEnemyLocs.addAll(ship.getLocations()));

        return myShots
                .stream()
                .filter(shot -> allEnemyLocs
                        .stream()
                        .anyMatch(loc -> loc.equals(shot)))
                .collect(Collectors.toList());

    }

    public List<Ship> getSunkenShips(Set<Salvo> mySalvoes, Set<Ship> opponentShips) {

        List<String> allShots = new ArrayList<>();

        mySalvoes.forEach(salvo -> allShots.addAll(salvo.getLocations()));

        return opponentShips
               .stream()
               .filter(ship -> allShots.containsAll(ship.getLocations()))
               .collect(Collectors.toList());
    }


    //DTO (data transfer object) para administrar la info de Salvo
    public Map<String, Object> salvoDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", this.getTurn());
        dto.put("player", this.getGamePlayer().getPlayer().getId());
        dto.put("locations", this.getLocations());

        GamePlayer opponent = this.getGamePlayer().getOpponent();

        if(opponent != null){

            Set<Ship> enemyShips = opponent.getShips();

            dto.put("hits", this.getHits(this.getLocations(),enemyShips));

            Set<Salvo> mySalvoes = this.getGamePlayer()
                    .getSalvoes()
                    .stream()
                    .filter(salvo -> salvo.getTurn() <= this.getTurn())
                    .collect(Collectors.toSet());

            dto.put("sunken", this.getSunkenShips(mySalvoes, enemyShips).stream().map(Ship::shipDTO));
        }

        return dto;
    }


}


