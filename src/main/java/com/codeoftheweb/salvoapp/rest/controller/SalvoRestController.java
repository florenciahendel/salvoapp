package com.codeoftheweb.salvoapp.rest.controller;

import com.codeoftheweb.salvoapp.model.*;
import com.codeoftheweb.salvoapp.repository.GamePlayerRepository;
import com.codeoftheweb.salvoapp.repository.GameRepository;
import com.codeoftheweb.salvoapp.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    PasswordEncoder passwordEncoder;

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

    private Map<String, Object> gameViewDTO(GamePlayer gamePlayer) {
       /* Instancia un objeto de clase Map, llamado dto (podría llamarse de cualquier manera),
         lo construye como un LinkedHashMap,
         declara un método para pasarle información que obtiene del GamePlayer que recibe por parámetro,
         y gestiona la respuesta si el GamePLayer no existiera (a través del else)*/

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

    /*    Muestra la información de los juegos:
        Si es un invitado, muestra todos los juegos creados,
        si es un usuario logueado, muestra los juegos de ese jugador*/
    @RequestMapping("/games")
    public Map<String, Object> getGames(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();
        if (!this.isGuest(authentication))
            dto.put("player", playerRepository.findPlayerByUserName(authentication.getName()).playerDTO());
        else
            dto.put("player", "guest");
        dto.put("games", gameRepository.findAll().stream().map(Game::gameDTO).collect(Collectors.toList()));
        return dto;
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    /*Muestra la información de un determinado GamePlayer, sólo si el Player está logueado*/
    @RequestMapping("/game_view/{gamePlayerId}")
    public ResponseEntity<Map<String, Object>> getGameView(@PathVariable long gamePlayerId, Authentication authentication) {
        ResponseEntity<Map<String, Object>> response;
        if (isGuest(authentication)) {
            response = new ResponseEntity<>(makeMap("error", "You must be logged in first"), HttpStatus.UNAUTHORIZED);
        } else {
            GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId).orElse(null);
            Player player = playerRepository.findPlayerByUserName(authentication.getName());
            if (gamePlayer == null) {
                response = new ResponseEntity<>(makeMap("error", "No such game"), HttpStatus.NOT_FOUND);
            } else if (gamePlayer.getPlayer().getId() != player.getId()) {
                response = new ResponseEntity<>(makeMap("error", "This is not your game"), HttpStatus.UNAUTHORIZED);
            } else {
                response = new ResponseEntity<>(this.gameViewDTO(gamePlayer), HttpStatus.OK);
            }
        }
        return response;
    }

    //Gestiona la información para la tabla de posiciones
    @RequestMapping("/leaderboard")
    public List<Map<String, Object>> getPositions() {
        return playerRepository.findAll().stream()
                .sorted(comparing(Player::getTotalPoints).reversed())
                .map(Player::playerDTO)
                .collect(Collectors.toList());
    }

    //Este método permite CREAR un USUARIO
    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createUser(@RequestParam String username, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String password) {
        ResponseEntity<Map<String, Object>> response;
        Player player = playerRepository.findPlayerByUserName(username);
        if (username.isEmpty() || password.isEmpty()) {
            response = new ResponseEntity<>(makeMap("error", "No name"), HttpStatus.FORBIDDEN);
        } else if (player != null) {
            response = new ResponseEntity<>(makeMap("error", "Username already exists"), HttpStatus.FORBIDDEN);
        } else {
            Player newPlayer = playerRepository.save(new Player(username, firstName, lastName, passwordEncoder.encode(password)));
            response = new ResponseEntity<>(makeMap("id", newPlayer.getId()), HttpStatus.CREATED);
        }
        return response;
    }

    //Este método permite CREAR un JUEGO
    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame(Authentication authentication) {
        ResponseEntity<Map<String, Object>> response;
        if (isGuest(authentication)) {
            response = new ResponseEntity<>(makeMap("error", "You must be logged in first"), HttpStatus.UNAUTHORIZED);
        } else {
            Player player = playerRepository.findPlayerByUserName(authentication.getName());
            Game newGame = gameRepository.save(new Game(LocalDateTime.now()));
            GamePlayer newGamePlayer = gamePlayerRepository.save(new GamePlayer(newGame, player, newGame.getCreationDate()));

            response = new ResponseEntity<>(makeMap("gpId", newGamePlayer.getId()), HttpStatus.CREATED);
        }

        return response;
    }

    //Este método permite UNIRSE a un juego ya creado
    @RequestMapping(path = "/games/{gameId}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> joinGame(Authentication authentication, @PathVariable long gameId) {
        ResponseEntity<Map<String, Object>> response;
        if (isGuest(authentication)) {
            response = new ResponseEntity<>(makeMap("error", "You must be logged in first"), HttpStatus.UNAUTHORIZED);
        } else {

            Game game = gameRepository.findById(gameId).orElse(null);
            if (game == null) {
                response = new ResponseEntity<>(makeMap("error", "No such game"), HttpStatus.NOT_FOUND);
            } else if (game.getGamePlayers().size() > 1) {
                response = new ResponseEntity<>(makeMap("error", "game is full"), HttpStatus.FORBIDDEN);
            } else {
                Player player = playerRepository.findPlayerByUserName(authentication.getName());
                if (game.getGamePlayers().stream().anyMatch(gp -> gp.getPlayer().getId() == player.getId())) {
                    response = new ResponseEntity<>(makeMap("error", "you can't play against yourself!"), HttpStatus.FORBIDDEN);
                } else {
                    GamePlayer newGamePlayer = gamePlayerRepository.save(new GamePlayer(game, player, LocalDateTime.now()));
                    response = new ResponseEntity<>(makeMap("gpId", newGamePlayer.getId()), HttpStatus.CREATED);
                }
            }

        }

        return response;
    }
}



