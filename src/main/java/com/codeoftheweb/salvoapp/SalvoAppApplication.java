package com.codeoftheweb.salvoapp;

import com.codeoftheweb.salvoapp.model.*;
import com.codeoftheweb.salvoapp.repository.GamePlayerRepository;
import com.codeoftheweb.salvoapp.repository.GameRepository;
import com.codeoftheweb.salvoapp.repository.PlayerRepository;
import com.codeoftheweb.salvoapp.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class SalvoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalvoAppApplication.class, args);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData(PlayerRepository playerRepository
            , GameRepository gameRepository
            , GamePlayerRepository gamePlayerRepository
            , ScoreRepository scoreRepository
    ) {
        return (args) -> {

            Player jack = playerRepository.save(new Player("j.bauer@ctu.gov", "Jack", "Bauer", passwordEncoder.encode("24"),true));
            Player chloe = playerRepository.save(new Player("c.obrian@ctu.gov", "Chloe", "O'Brian", passwordEncoder.encode("42")));
            Player kim = playerRepository.save(new Player("kim_bauer@gmail.com", "Kim", "Bauer", passwordEncoder.encode("kb")));
            Player tony = playerRepository.save(new Player("t.almeida@ctu.gov", "Tony", "Almeida", passwordEncoder.encode("mole")));


            Game game1 = gameRepository.save(new Game(LocalDateTime.now()));
            Game game2 = gameRepository.save(new Game(LocalDateTime.now().plusHours(1)));
            Game game3 = gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));
            Game game4 = gameRepository.save(new Game(LocalDateTime.now().plusHours(3)));
            Game game5 = gameRepository.save(new Game(LocalDateTime.now().plusHours(4)));
            Game game6 = gameRepository.save(new Game(LocalDateTime.now().plusHours(5)));
            Game game7 = gameRepository.save(new Game(LocalDateTime.now().plusHours(6)));
            Game game8 = gameRepository.save(new Game(LocalDateTime.now().plusHours(7)));

            //LocalDateTime.now de GamePlayer debe tener en cuenta el creationDate de los juegos
            // para que un jugador no se una a un juego antes de que dicho juego sea creado
            GamePlayer gp1 = gamePlayerRepository.save(new GamePlayer(game1, jack, LocalDateTime.from(game1.getCreationDate())));
            GamePlayer gp2 = gamePlayerRepository.save(new GamePlayer(game1, chloe, LocalDateTime.from(game1.getCreationDate())));
            GamePlayer gp3 = gamePlayerRepository.save(new GamePlayer(game2, jack, LocalDateTime.from(game2.getCreationDate())));
            GamePlayer gp4 = gamePlayerRepository.save(new GamePlayer(game2, chloe, LocalDateTime.from(game2.getCreationDate())));
            GamePlayer gp5 = gamePlayerRepository.save(new GamePlayer(game3, chloe, LocalDateTime.from(game3.getCreationDate())));
            GamePlayer gp6 = gamePlayerRepository.save(new GamePlayer(game3, tony, LocalDateTime.from(game3.getCreationDate())));
            GamePlayer gp7 = gamePlayerRepository.save(new GamePlayer(game4, chloe, LocalDateTime.from(game4.getCreationDate())));
            GamePlayer gp8 = gamePlayerRepository.save(new GamePlayer(game4, jack, LocalDateTime.from(game4.getCreationDate())));
            GamePlayer gp9 = gamePlayerRepository.save(new GamePlayer(game5, tony, LocalDateTime.from(game5.getCreationDate())));
            GamePlayer gp10 = gamePlayerRepository.save(new GamePlayer(game5, jack, LocalDateTime.from(game5.getCreationDate())));
            GamePlayer gp11 = gamePlayerRepository.save(new GamePlayer(game6, kim, LocalDateTime.from(game6.getCreationDate())));
            GamePlayer gp12 = gamePlayerRepository.save(new GamePlayer(game7, tony, LocalDateTime.from(game7.getCreationDate())));
            GamePlayer gp13 = gamePlayerRepository.save(new GamePlayer(game8, kim, LocalDateTime.from(game8.getCreationDate())));
            GamePlayer gp14 = gamePlayerRepository.save(new GamePlayer(game8, tony, LocalDateTime.from(game8.getCreationDate())));


            gp1.addShip(new Ship("Destroyer", Arrays.asList("H2", "H3", "H4")));
            gp1.addShip(new Ship("Submarine", Arrays.asList("E1", "F1", "G1")));
            gp1.addShip(new Ship("Patrol Boat", Arrays.asList("B4", "B5")));

            gp2.addShip(new Ship("Destoyer", Arrays.asList("B5", "C5", "D5")));
            gp2.addShip(new Ship("Patrol Boat", Arrays.asList("F1", "F2")));

            gp3.addShip(new Ship("Destroyer", Arrays.asList("B5", "C5", "D5")));
            gp3.addShip(new Ship("Patrol Boat", Arrays.asList("C6", "C7")));

            gp4.addShip(new Ship("Submarine", Arrays.asList("A2", "A3", "A4")));
            gp4.addShip(new Ship("Patrol Boat", Arrays.asList("G6", "H6")));

            gp5.addShip(new Ship("Destroyer", Arrays.asList("B5", "C5", "D5")));
            gp5.addShip(new Ship("Patrol Boat", Arrays.asList("C6", "C7")));

            gp6.addShip(new Ship("Submarine", Arrays.asList("A2", "A3", "A4")));
            gp6.addShip(new Ship("Patrol Boat", Arrays.asList("G6", "H6")));

            gp7.addShip(new Ship("Submarine", Arrays.asList("B5", "C5", "D5")));
            gp7.addShip(new Ship("Patrol Boat", Arrays.asList("C6", "C7")));

            gp8.addShip(new Ship("Submarine", Arrays.asList("A2", "A3", "A4")));
            gp8.addShip(new Ship("Patrol Boat", Arrays.asList("G6", "H6")));

            gp9.addShip(new Ship("Destroyer", Arrays.asList("B5", "C5", "D5")));
            gp9.addShip(new Ship("Patrol Boat", Arrays.asList("C6", "C7")));

            gp10.addShip(new Ship("Submarine", Arrays.asList("A2", "A3", "A4")));
            gp10.addShip(new Ship("Patrol Boat", Arrays.asList("G6", "H6")));

            gp11.addShip(new Ship("Destroyer", Arrays.asList("B5", "C5", "D5")));
            gp11.addShip(new Ship("Patrol Boat", Arrays.asList("C6", "C7")));

            gp13.addShip(new Ship("Destroyer", Arrays.asList("B5", "C5", "D5")));
            gp13.addShip(new Ship("Patrol Boat", Arrays.asList("C6", "C7")));

            gp14.addShip(new Ship("Submarine", Arrays.asList("A2", "A3", "A4")));
            gp14.addShip(new Ship("Patrol Boat", Arrays.asList("G6", "H6")));

            gp1.addSalvo(new Salvo(1, Arrays.asList("B5", "C5", "F1")));
            gp1.addSalvo(new Salvo(2, Arrays.asList("F2", "D5")));

            gp2.addSalvo(new Salvo(1, Arrays.asList("B4", "B5", "B6")));
            gp2.addSalvo(new Salvo(2, Arrays.asList("E1", "H3", "A2")));

            gp3.addSalvo(new Salvo(1, Arrays.asList("A2", "A4", "G6")));
            gp3.addSalvo(new Salvo(2, Arrays.asList("A3", "H6")));

            gp4.addSalvo(new Salvo(1, Arrays.asList("B5", "D5", "C7")));
            gp4.addSalvo(new Salvo(2, Arrays.asList("C5", "C6")));

            gp5.addSalvo(new Salvo(1, Arrays.asList("G6", "H6", "A4")));
            gp5.addSalvo(new Salvo(2, Arrays.asList("A2", "A3", "D8")));

            gp6.addSalvo(new Salvo(1, Arrays.asList("H1", "H2", "H3")));
            gp6.addSalvo(new Salvo(2, Arrays.asList("E1", "F2", "G3")));

            gp7.addSalvo(new Salvo(1, Arrays.asList("A3", "A4", "F7")));
            gp7.addSalvo(new Salvo(2, Arrays.asList("A2", "G6", "H6")));

            gp8.addSalvo(new Salvo(1, Arrays.asList("B5", "C6", "H1")));
            gp8.addSalvo(new Salvo(2, Arrays.asList("C5", "C7", "D5")));

            gp9.addSalvo(new Salvo(1, Arrays.asList("A1", "A2", "A3")));
            gp9.addSalvo(new Salvo(2, Arrays.asList("G6", "G7", "G8")));

            gp10.addSalvo(new Salvo(1, Arrays.asList("B5", "B6", "C7")));
            gp10.addSalvo(new Salvo(2, Arrays.asList("C6", "D6", "E6")));
            gp10.addSalvo(new Salvo(3, Arrays.asList("H1", "H8")));


            gamePlayerRepository.save(gp1);
            gamePlayerRepository.save(gp2);
            gamePlayerRepository.save(gp3);
            gamePlayerRepository.save(gp4);
            gamePlayerRepository.save(gp5);
            gamePlayerRepository.save(gp6);
            gamePlayerRepository.save(gp7);
            gamePlayerRepository.save(gp8);
            gamePlayerRepository.save(gp9);
            gamePlayerRepository.save(gp10);
            gamePlayerRepository.save(gp11);
            gamePlayerRepository.save(gp13);
            gamePlayerRepository.save(gp14);

            scoreRepository.save(new Score(3, game1, jack, LocalDateTime.from(game1.getCreationDate().plusMinutes(30))));
            scoreRepository.save(new Score(0, game1, chloe, LocalDateTime.from(game1.getCreationDate().plusMinutes(30))));
            scoreRepository.save(new Score(1, game2, jack, LocalDateTime.from(game2.getCreationDate().plusMinutes(30))));
            scoreRepository.save(new Score(1, game2, chloe, LocalDateTime.from(game2.getCreationDate().plusMinutes(30))));
            scoreRepository.save(new Score(3, game3, chloe, LocalDateTime.from(game3.getCreationDate().plusMinutes(30))));
            scoreRepository.save(new Score(0, game3, tony, LocalDateTime.from(game3.getCreationDate().plusMinutes(30))));
            scoreRepository.save(new Score(1, game4, jack, LocalDateTime.from(game4.getCreationDate().plusMinutes(30))));
            scoreRepository.save(new Score(1, game4, tony, LocalDateTime.from(game4.getCreationDate().plusMinutes(30))));
        };
    }

}

