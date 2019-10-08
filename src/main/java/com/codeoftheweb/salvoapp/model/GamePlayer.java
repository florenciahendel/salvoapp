package com.codeoftheweb.salvoapp.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class GamePlayer {

    /*-------
    ATRIBUTOS
    -------*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private LocalDateTime joinDate;

    //relación Many to One con Player. Un GamePlayer tiene un solo Player
    //mientras que un player puede tener muchos gamePlayers.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    //relación Many to One con Game. Un GamePlayer tiene un solo Game
    //mientras que un game puede tener muchos gamePlayers.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Ship> ships = new ArrayList<>();


    /*-----
    METODOS
    -----*/

    //CONSTRUCTORES
    public GamePlayer() {
    }

    public GamePlayer(Game game, Player player, LocalDateTime joinDate) {
        this.game = game;
        this.player = player;
        this.joinDate = joinDate;
    }

    //GETTERS Y SETTERS
    public long getId() {
        return id;
    }

    //El atributo Id, a mi parecer, no debería tener un set ya que lo genera la DB, pero...
//    public void setId(Long id) {
//        this.id = id;
//    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void addShip(Ship ship) {
        this.ships.add(ship);
        ship.setGamePlayer(this);
    }

    public List<Ship> getShips() {
        return this.ships;
    }

    //DTO (data transfer object) para administrar la info de GamePlayer
    public Map<String, Object> gamePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("player", this.getPlayer().playerDTO());
        return dto;
    }
}
