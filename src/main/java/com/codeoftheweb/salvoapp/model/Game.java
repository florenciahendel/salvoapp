package com.codeoftheweb.salvoapp.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Game {

    /*-------
    ATRIBUTOS
    -------*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private LocalDateTime creationDate;

    //relación Many to Many con Player a través de la instancia intermedia GamePlayer
    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<GamePlayer> gamePlayers = new HashSet<>();

    /*-----
    METODOS
    -----*/

    //CONSTRUCTORES
    public Game() {
    }

    public Game(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    //GETTERS Y SETTERS
    public Long getId() {
        return id;
    }

    //El atributo Id, a mi parecer, no debería tener un set ya que lo genera la DB, pero...
//    public void setId(Long id) {
//        this.id = id;
//    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    //método para establecer la relación entre un objeto Game y un objeto GamePlayer
    public void addGamePlayer(GamePlayer gamePlayer) {
        //se agrega el gamePlayer que ingresa como parámetro al set declarado en los atributos
        this.gamePlayers.add(gamePlayer);
        //al gamePlayer ingresado se le agrega este game mediante su setter en la clase GamePlayer
        gamePlayer.setGame(this);
    }

    //método que retorna todos los players relacionados con el game a partir de los gamePlayers
    public List<Player> getPlayers() {
        return this.gamePlayers.stream().map(gp -> gp.getPlayer()).collect(Collectors.toList());
    }

    //DTO (data transfer object) para administrar la info de Game
    public Map<String, Object> gameDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("created", this.getCreationDate());
        dto.put("gamePlayers", this.getGamePlayers().stream().map(GamePlayer::gamePlayerDTO).collect(Collectors.toList()));
        return dto;
    }

}
