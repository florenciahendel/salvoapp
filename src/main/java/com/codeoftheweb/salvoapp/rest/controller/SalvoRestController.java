package com.codeoftheweb.salvoapp.rest.controller;

import com.codeoftheweb.salvoapp.model.*;
import com.codeoftheweb.salvoapp.repository.GamePlayerRepository;
import com.codeoftheweb.salvoapp.repository.GameRepository;
import com.codeoftheweb.salvoapp.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@RestController
@RequestMapping("/api")
public class SalvoRestController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping("/games")
    public List<Map<String, Object>> getGames() {
        return gameRepository.findAll().stream().map(Game::gameDTO).collect(Collectors.toList());
    }

    @RequestMapping("/leaderboard")
    public List<Map<String, Object>> getPositions() {
        return playerRepository.findAll().stream()
                .sorted(comparing(Player::getTotalPoints).reversed())
                .map(Player::playerDTO)
                //va por este lado, donde getTotalPoints es el atributo "total" del dto de cada player, pero no termino de redondear la idea
               // .sorted((o1,o2)-> o1.compareTo(o2))

                .collect(Collectors.toList());
    }

    private Set<Map> shipsList(Set<Ship> ships) {
        return ships.stream()
                .map(ship -> ship.shipDTO())
                .collect(Collectors.toSet());
    }

    private List<Map> salvoesList(List<Salvo> salvoes) {
        return salvoes.stream()
                .map(salvo -> salvo.salvoDTO())
                .collect(Collectors.toList());
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String, Object> getGameView(@PathVariable long gamePlayerId) {

        return this.gameViewDTO(gamePlayerRepository.findById(gamePlayerId).orElse(null));

    }

    private Map<String, Object> gameViewDTO(GamePlayer gamePlayer) {
        //Instancia un objeto de clase Map, llamado dto (podría llamarse de cualquier manera),
        // lo construye como un LinkedHashMap,
        // declara un método para pasarle información que obtiene del GamePlayer que recibe por parámetro,
        // y gestiona la respuesta si el GamePLayer no existiera (a través del else)

        Map<String, Object> dto = new LinkedHashMap<>();

        if (gamePlayer != null) {
            dto.put("gameId", gamePlayer.getGame().getId());
            dto.put("gameCreationDate", gamePlayer.getGame().getCreationDate());
            dto.put("player", gamePlayer.getPlayer().getUserName());
            dto.put("playersInThisGame", gamePlayer.getGame().getGamePlayers().stream().map(GamePlayer::gamePlayerDTO));
            //Cómo hago el mapeo al final, si solo quiero mostrar el userName del opponent? Tengo que crear otro DTO con esa info nada más, o puedo filtrar este?
            dto.put("opponent", gamePlayer.getGame().getGamePlayers().stream()
                    .filter(x -> x.getPlayer().getUserName() != gamePlayer.getPlayer().getUserName())
                    .map(GamePlayer::gamePlayerUserNameDTO));
            dto.put("ships", gamePlayer.getShips().stream().map(Ship::shipDTO));
            dto.put("salvoes", gamePlayer.getGame().getGamePlayers().stream()
                    .flatMap(gp -> gp.getSalvoes().stream()
                            .map(salvo -> salvo.salvoDTO())));
            dto.put("enemySalvoes", salvoesList(gamePlayer.getGame().getGamePlayers().stream()
                    .filter(gp -> gp.getId() != gamePlayer.getId()).findFirst()
                    .orElseThrow(() -> new RuntimeException()).getSalvoes()));
        } else {
            dto.put("error", "no such game");
        }
        return dto;
    }
}



