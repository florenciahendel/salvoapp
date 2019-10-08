package com.codeoftheweb.salvoapp;

import com.codeoftheweb.salvoapp.model.Game;
import com.codeoftheweb.salvoapp.model.GamePlayer;
import com.codeoftheweb.salvoapp.model.Player;
import com.codeoftheweb.salvoapp.model.Ship;
import com.codeoftheweb.salvoapp.repository.GamePlayerRepository;
import com.codeoftheweb.salvoapp.repository.GameRepository;
import com.codeoftheweb.salvoapp.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class SalvoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalvoAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(PlayerRepository playerRepository
                                      , GameRepository gameRepository
                                      , GamePlayerRepository gamePlayerRepository
    ) {
        return (args) -> {

            Player jack = playerRepository.save(new Player("j.bauer@ctu.gov", "Jack", "Bauer"));
            Player chloe = playerRepository.save(new Player("c.obrian@ctu.gov", "Chloe", "O'Brian"));
            Player kim = playerRepository.save(new Player("kim_bauer@gmail.com", "Kim", "Bauer"));
            Player tony = playerRepository.save(new Player("t.almeida@ctu.gov", "Tony", "Almeida"));


			Game game1 = gameRepository.save(new Game(LocalDateTime.now()));
			Game game2 = gameRepository.save(new Game(LocalDateTime.now().plusHours(1)));
			Game game3 = gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));
			Game game4 = gameRepository.save(new Game(LocalDateTime.now().plusHours(3)));
			Game game5 = gameRepository.save(new Game(LocalDateTime.now().plusHours(4)));
			Game game6 = gameRepository.save(new Game(LocalDateTime.now().plusHours(5)));
			Game game7 = gameRepository.save(new Game(LocalDateTime.now().plusHours(6)));
			Game game8 = gameRepository.save(new Game(LocalDateTime.now().plusHours(7)));

			//LocalDateTime.now de GamePlayer debería tener en cuenta el creationDate de los juegos
			// para que un jugador no se una a un juego antes de que dicho juego sea creado
			GamePlayer gp1 = gamePlayerRepository.save(new GamePlayer(game1,jack,LocalDateTime.now()));
			GamePlayer gp2 = gamePlayerRepository.save(new GamePlayer(game1,chloe,LocalDateTime.now()));
			GamePlayer gp3 = gamePlayerRepository.save(new GamePlayer(game2,jack,LocalDateTime.now()));
			GamePlayer gp4 = gamePlayerRepository.save(new GamePlayer(game2,chloe,LocalDateTime.now()));
			GamePlayer gp5 = gamePlayerRepository.save(new GamePlayer(game3,chloe,LocalDateTime.now()));
			GamePlayer gp6 = gamePlayerRepository.save(new GamePlayer(game3,tony,LocalDateTime.now()));
			GamePlayer gp7 = gamePlayerRepository.save(new GamePlayer(game4,chloe,LocalDateTime.now()));
			GamePlayer gp8 = gamePlayerRepository.save(new GamePlayer(game4,jack,LocalDateTime.now()));
			GamePlayer gp9 = gamePlayerRepository.save(new GamePlayer(game5,tony,LocalDateTime.now()));
			GamePlayer gp10 = gamePlayerRepository.save(new GamePlayer(game5,jack,LocalDateTime.now()));
			GamePlayer gp11 = gamePlayerRepository.save(new GamePlayer(game6,kim,LocalDateTime.now()));
			GamePlayer gp12 = gamePlayerRepository.save(new GamePlayer(game7,tony,LocalDateTime.now()));
			GamePlayer gp13 = gamePlayerRepository.save(new GamePlayer(game8,kim,LocalDateTime.now()));
			GamePlayer gp14 = gamePlayerRepository.save(new GamePlayer(game8,tony,LocalDateTime.now()));



			gp1.addShip(new Ship("Destroyer", Arrays.asList("H2","H3","H4")));
			gp1.addShip(new Ship("Submarine", Arrays.asList("E1","F1","G1")));
			gp1.addShip(new Ship("Patrol Boat", Arrays.asList("B4","B5")));

			gp2.addShip(new Ship("Destoyer", Arrays.asList("B5","C5","D5")));
			gp2.addShip(new Ship("Patrol Boat", Arrays.asList("F1","F2")));

			gp3.addShip(new Ship("Destroyer", Arrays.asList("B5","C5","D5")));
			gp3.addShip(new Ship("Patrol Boat", Arrays.asList("C6", "C7")));

			gp4.addShip(new Ship("Submarine", Arrays.asList("A2","A3","A4")));
			gp4.addShip(new Ship("Patrol Boat", Arrays.asList("G6","H6")));

			gp5.addShip(new Ship("Destroyer", Arrays.asList("B5","C5","D5")));
			gp5.addShip(new Ship("Patrol Boat", Arrays.asList("C6","C7")));

			gp6.addShip(new Ship("Submarine", Arrays.asList("A2","A3","A4")));
			gp6.addShip(new Ship("Patrol Boat", Arrays.asList("G6","H6")));

			gp7.addShip(new Ship("Submarine", Arrays.asList("B5","C5","D5")));
			gp7.addShip(new Ship("Patrol Boat", Arrays.asList("C6","C7")));

			gp8.addShip(new Ship("Submarine", Arrays.asList("A2","A3","A4")));
			gp8.addShip(new Ship("Patrol Boat", Arrays.asList("G6","H6")));

			gp9.addShip(new Ship("Destroyer", Arrays.asList("B5","C5","D5")));
			gp9.addShip(new Ship("Patrol Boat", Arrays.asList("C6","C7")));

			gp10.addShip(new Ship("Submarine", Arrays.asList("A2","A3","A4")));
			gp10.addShip(new Ship("Patrol Boat", Arrays.asList("G6","H6")));

			gp11.addShip(new Ship("Destroyer", Arrays.asList("B5","C5","D5")));
			gp11.addShip(new Ship("Patrol Boat", Arrays.asList("C6","C7")));

			gp13.addShip(new Ship("Destroyer", Arrays.asList("B5","C5","D5")));
			gp13.addShip(new Ship("Patrol Boat", Arrays.asList("C6","C7")));

			gp14.addShip(new Ship("Submarine", Arrays.asList("A2","A3","A4")));
			gp14.addShip(new Ship("Patrol Boat", Arrays.asList("G6","H6")));


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
        };
    }

}
