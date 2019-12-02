package com.codeoftheweb.salvoapp.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

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

    @OneToMany(mappedBy = "gamePlayer", cascade = CascadeType.ALL)
    private Set<Ship> ships = new HashSet<>();

    @OneToMany(mappedBy = "gamePlayer", cascade = CascadeType.ALL)
    private Set<Salvo> salvoes = new HashSet<>();

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

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<Ship> getShips() {
        return this.ships;
    }

    public void addSalvo(Salvo salvo) {
        this.salvoes.add(salvo);
        salvo.setGamePlayer(this);
    }

    public Set<Salvo> getSalvoes() {
        return this.salvoes;
    }

    public GamePlayer getOpponent() {
        return this.getGame().getGamePlayers()
                .stream().filter(gp -> gp.getId() != this.getId())
                .findFirst()
                .orElse(null);
    }


    //DTO (data transfer object) para administrar la info de GamePlayer
    public Map<String, Object> gamePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("player", this.getPlayer().playerDTO());

        Score score = this.getPlayer().getScoreByGame(this.getGame());
        if (score != null)
            dto.put("score", score.getPoints());
        else
            dto.put("score", null);

        return dto;
    }

    //Solo sirve para filtrar y que no me muestre el ID de gamePlayer
    public Map<String, Object> gamePlayerUserNameDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("player", this.getPlayer().getUserName());
        return dto;
    }
}
