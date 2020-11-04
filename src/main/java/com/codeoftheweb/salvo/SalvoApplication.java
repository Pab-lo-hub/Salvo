package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository) {
		return (args) -> {
			Player player1 = new Player ("j.bauer@ctu.gov");
			playerRepository.save(player1);

			Player player2 = new Player ("c.obrian@ctu.gov");
			playerRepository.save(player2);

			Player player3 = new Player ("t.almeida@ctu.gov");
			playerRepository.save(player3);

			Player player4 = new Player ("kim_bauer@gmail.com");
			playerRepository.save(player4);

			//GAME
			Game game1 = new Game(LocalDateTime.now());
			gameRepository.save(game1);

			Game game2 = new Game(LocalDateTime.now().plusSeconds(3600));
			gameRepository.save(game2);

			Game game3 = new Game(LocalDateTime.now().plusHours(2));
			gameRepository.save(game3);

			Game game4 = new Game(LocalDateTime.now().plusHours(3));
			gameRepository.save(game4);

			Game game5 = new Game(LocalDateTime.now().plusHours(4));
			gameRepository.save(game5);

			Game game6 = new Game(LocalDateTime.now().plusHours(5));
			gameRepository.save(game6);

			Game game8 = new Game(LocalDateTime.now().plusHours(7));
			gameRepository.save(game8);

			//GamePlayer
			GamePlayer gamePlayer1 = new GamePlayer(player1, game1);
			GamePlayer gamePlayer2 = new GamePlayer(player2, game1);
			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);

			GamePlayer gamePlayer3 = new GamePlayer(player1, game2);
			GamePlayer gamePlayer4 = new GamePlayer(player2, game2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);

			GamePlayer gamePlayer5 = new GamePlayer(player2, game3);
			GamePlayer gamePlayer6 = new GamePlayer(player3, game3);
			gamePlayerRepository.save(gamePlayer5);
			gamePlayerRepository.save(gamePlayer6);
			GamePlayer gamePlayer7 = new GamePlayer(player2, game4);
			GamePlayer gamePlayer8 = new GamePlayer(player1, game4);
			gamePlayerRepository.save(gamePlayer7);
			gamePlayerRepository.save(gamePlayer8);

			GamePlayer gamePlayer9 = new GamePlayer(player3, game5);
			GamePlayer gamePlayer10 = new GamePlayer(player1, game5);
			gamePlayerRepository.save(gamePlayer9);
			gamePlayerRepository.save(gamePlayer10);

			GamePlayer gamePlayer11 = new GamePlayer(player4, game6);
			gamePlayerRepository.save(gamePlayer11);

			GamePlayer gamePlayer12 = new GamePlayer(player4, game8);
			gamePlayerRepository.save(gamePlayer12);

			GamePlayer gamePlayer13 = new GamePlayer(player3, game8);
			gamePlayerRepository.save(gamePlayer13);



			Ship ship1 = new Ship ("Destroyer", Arrays.asList("H2", "H3", "H4"), gamePlayer1);
			shipRepository.save(ship1);

			Ship ship2 = new Ship ("Submarine", Arrays.asList("E1", "F1", "G1"), gamePlayer1);
			shipRepository.save(ship2);

			Ship ship3 = new Ship ("Patrol Boat", Arrays.asList("B4", "B5"), gamePlayer1);
			shipRepository.save(ship3);

			Ship ship4 = new Ship ("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer2);
			shipRepository.save(ship4);

			Ship ship5 = new Ship ("Patrol Boat", Arrays.asList("F1", "F2"), gamePlayer2);
			shipRepository.save(ship5);

			Ship ship6 = new Ship ("Destroyer", Arrays.asList("F1", "F2"), gamePlayer3);
			shipRepository.save(ship6);

			Ship ship7 = new Ship ("Patrol Boat", Arrays.asList("C6", "C7"), gamePlayer3);
			shipRepository.save(ship7);

			Ship ship8 = new Ship ("Submarine", Arrays.asList("C6", "C7"), gamePlayer4);
			shipRepository.save(ship8);

			Ship ship9 = new Ship ("Patrol Boat", Arrays.asList("G6", "H6"), gamePlayer4);
			shipRepository.save(ship9);

			Ship ship10 = new Ship ("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer5);
			shipRepository.save(ship10);

			Ship ship11 = new Ship ("Patrol Boat", Arrays.asList("C6", "C7"), gamePlayer5);
			shipRepository.save(ship11);

			Ship ship12 = new Ship ("Submarine", Arrays.asList("C6", "C7"), gamePlayer6);
			shipRepository.save(ship12);

			Ship ship13 = new Ship ("Patrol Boat", Arrays.asList("G6", "H6"), gamePlayer6);
			shipRepository.save(ship13);

			Ship ship14 = new Ship ("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer7);
			shipRepository.save(ship14);

			Ship ship15 = new Ship ("Patrol Boat", Arrays.asList("C6", "C7"), gamePlayer7);
			shipRepository.save(ship15);

			Ship ship16 = new Ship ("Submarine", Arrays.asList("A2", "A3", "A4"), gamePlayer8);
			shipRepository.save(ship16);

			Ship ship17 = new Ship ("Patrol Boat", Arrays.asList("G6", "H6"), gamePlayer8);
			shipRepository.save(ship17);

			Ship ship18 = new Ship ("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer9);
			shipRepository.save(ship18);

			Ship ship19 = new Ship ("Patrol Boat", Arrays.asList("C6", "C7"), gamePlayer9);
			shipRepository.save(ship19);

			Ship ship20 = new Ship ("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer10);
			shipRepository.save(ship20);

			Ship ship21 = new Ship ("Patrol Boat", Arrays.asList("C6", "C7"), gamePlayer10);
			shipRepository.save(ship21);

			Ship ship22 = new Ship ("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer11);
			shipRepository.save(ship22);

			Ship ship23 = new Ship ("Patrol Boat", Arrays.asList("C6", "C7"), gamePlayer11);
			shipRepository.save(ship23);

			Ship ship24 = new Ship ("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer12);
			shipRepository.save(ship24);

			Ship ship25 = new Ship ("Patrol Boat", Arrays.asList("C6", "C7"), gamePlayer12);
			shipRepository.save(ship25);

			Ship ship26 = new Ship ("Submarine", Arrays.asList("A2", "A3", "A4"), gamePlayer13);
			shipRepository.save(ship26);

			Ship ship27 = new Ship ("Patrol Boat", Arrays.asList("G6", "H6"), gamePlayer13);
			shipRepository.save(ship27);


			Salvo salvo1 = new Salvo (1, Arrays.asList("B5", "C5", "F1"), gamePlayer1);
			salvoRepository.save(salvo1);

			Salvo salvo2 = new Salvo (1, Arrays.asList("B4", "B5", "B6"), gamePlayer2);
			salvoRepository.save(salvo2);

			Salvo salvo3 = new Salvo (2, Arrays.asList("F2", "D5"), gamePlayer1);
			salvoRepository.save(salvo3);

			Salvo salvo4 = new Salvo (2, Arrays.asList("E1", "H3", "A2"), gamePlayer2);
			salvoRepository.save(salvo4);

			Salvo salvo5 = new Salvo (1, Arrays.asList("A2", "A4", "G6"), gamePlayer3);
			salvoRepository.save(salvo5);

			Salvo salvo6 = new Salvo (1, Arrays.asList("B5", "D5", "C7"), gamePlayer4);
			salvoRepository.save(salvo6);

			Salvo salvo7 = new Salvo (2, Arrays.asList("A3", "H6"), gamePlayer3);
			salvoRepository.save(salvo7);

			Salvo salvo8 = new Salvo (2, Arrays.asList("C5", "C6"), gamePlayer4);
			salvoRepository.save(salvo8);

			Salvo salvo9 = new Salvo (1, Arrays.asList("G6", "H6", "A4"), gamePlayer5);
			salvoRepository.save(salvo9);

			Salvo salvo10 = new Salvo (1, Arrays.asList("H1", "H2", "H3"), gamePlayer6);
			salvoRepository.save(salvo10);

			Salvo salvo11 = new Salvo (2, Arrays.asList("A2", "A3", "D8"), gamePlayer5);
			salvoRepository.save(salvo11);

			Salvo salvo12 = new Salvo (2, Arrays.asList("E1", "F2", "G3"), gamePlayer6);
			salvoRepository.save(salvo12);

			Salvo salvo13 = new Salvo (1, Arrays.asList("A3", "A4", "F7"), gamePlayer7);
			salvoRepository.save(salvo13);

			Salvo salvo14 = new Salvo (1, Arrays.asList("B5", "C6", "H1"), gamePlayer8);
			salvoRepository.save(salvo14);

			Salvo salvo15 = new Salvo (2, Arrays.asList("A2", "G6", "H6"), gamePlayer7);
			salvoRepository.save(salvo15);

			Salvo salvo16 = new Salvo (2, Arrays.asList("C5", "C7", "D5"), gamePlayer8);
			salvoRepository.save(salvo16);

			Salvo salvo17 = new Salvo (1, Arrays.asList("A1", "A2", "A3"), gamePlayer9);
			salvoRepository.save(salvo17);

			Salvo salvo18 = new Salvo (1, Arrays.asList("B5", "B6", "C7"), gamePlayer10);
			salvoRepository.save(salvo18);

			Salvo salvo19 = new Salvo (2, Arrays.asList("G6", "G7", "G8"), gamePlayer9);
			salvoRepository.save(salvo19);

			Salvo salvo20 = new Salvo (2, Arrays.asList("C6", "D6", "E6"), gamePlayer10);
			salvoRepository.save(salvo20);

			Salvo salvo21 = new Salvo (3, Arrays.asList("H1", "H8"), gamePlayer10);
			salvoRepository.save(salvo21);



			Score score1 = new Score(player1, game1, 1, LocalDateTime.now());
			scoreRepository.save(score1);

			Score score2 = new Score(player2, game1, 0, LocalDateTime.now());
			scoreRepository.save(score2);

			Score score3 = new Score(player1, game2, 0.5, LocalDateTime.now());
			scoreRepository.save(score3);

			Score score4 = new Score(player1, game2, 0.5, LocalDateTime.now());
			scoreRepository.save(score4);

			Score score5 = new Score(player2, game3, 1, LocalDateTime.now());
			scoreRepository.save(score5);

			Score score6 = new Score(player3, game3, 0, LocalDateTime.now());
			scoreRepository.save(score6);

			Score score7 = new Score(player2, game4, 0.5, LocalDateTime.now());
			scoreRepository.save(score7);

			Score score8 = new Score(player1, game4, 0.5, LocalDateTime.now());
			scoreRepository.save(score8);

			Score score9 = new Score(player3, game5, 0, LocalDateTime.now());
			scoreRepository.save(score9);

			Score score10 = new Score(player3, game5, 0, LocalDateTime.now());
			scoreRepository.save(score10);

		};
	}
}
