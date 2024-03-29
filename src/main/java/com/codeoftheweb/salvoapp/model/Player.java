package com.codeoftheweb.salvoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Player {

    /*-------
    ATRIBUTOS
    -------*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    private boolean admin;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<GamePlayer> gamePlayers = new HashSet<>();

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Score> scores = new HashSet<>();


    /*-----
    METODOS
    -----*/

    //CONSTRUCTORES
    public Player() { }

    public Player(String userName, String firstName, String lastName, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.admin = false;
    }

    public Player(String userName, String firstName, String lastName,String password, boolean isAdmin) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.admin = isAdmin;
    }

    //GETTERS Y SETTERS
    public Long getId() {
        return id;
    }

//El atributo Id, a mi parecer, no debería tener un set ya que lo genera la DB, pero...
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean isAdmin) {
        this.admin = isAdmin;
    }

    public Set<GamePlayer> getGamePlayers() {
        return this.gamePlayers;
    }

    public Set<Score> getScores() {
        return this.scores;
    }

    public void addScore(Score score) {
        this.scores.add(score);
        score.setPlayer(this);
    }

    public Score getScoreByGame(Game game) {
        return this.scores.stream()
                .filter(score -> score.getGame().getId() == game.getId())
                .findFirst()
                .orElse(null);
    }

    public Set<Score> getLossesScores() {
        return this.scores.stream()
                .filter(lossScore -> lossScore.getPoints() == 0)
                .collect(Collectors.toSet());
    }

    public Set<Score> getTiesScores() {
        return this.scores.stream()
                .filter(tieScore -> tieScore.getPoints() == 1)
                .collect(Collectors.toSet());
    }

    public Set<Score> getWonScores() {
        return this.scores.stream()
                .filter(wonScore -> wonScore.getPoints() == 3)
                .collect(Collectors.toSet());
    }

    public int getTotalPoints() {
        return this.getWonScores().size() * 3 + getTiesScores().size();
    }


    //método para establecer la relación entre un objeto Player y un objeto GamePlayer
    public void addGamePlayer(GamePlayer gamePlayer) {
        //se agrega el gamePlayer que ingresa como parámetro al set declarado en los atributos
        this.gamePlayers.add(gamePlayer);
        //al gamePlayer ingresado se le agrega este player mediante su setter en la clase GamePlayer
        gamePlayer.setPlayer(this);
    }

    //método que retorna todos los games relacionados con el player a partir de los gamePlayers
    @JsonIgnore
    public List<Game> getGames() {
        return this.gamePlayers.stream().map(gp -> gp.getGame()).collect(Collectors.toList());
    }

    //DTO (data transfer object) para administrar la info de Player
    public Map<String, Object> playerDTO() {
        Integer cantWon = this.getWonScores().size();
        Integer cantLose = this.getLossesScores().size();
        Integer cantTie = this.getTiesScores().size();
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("username", this.getUserName());
        dto.put("firstName", this.getFirstName());
        dto.put("lastName", this.getLastName());
        dto.put("won", cantWon);
        dto.put("lose", cantLose);
        dto.put("tie", cantTie);
        dto.put("total", this.getTotalPoints());
        return dto;
    }

}
