package com.github.kalaha.kalaha_game.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.github.kalaha.kalaha_game.domain.BoardImpl;
import com.github.kalaha.kalaha_game.domain.Game;
import com.github.kalaha.kalaha_game.dto.GameInfoDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KahalaController {


    private static final Logger logger = LoggerFactory.getLogger(BoardImpl.class);

	@Autowired
	private Game game;

	@CrossOrigin
	@GetMapping("/")
	public String healthCheck() {
		return "Health Check - OK";
	}

	@CrossOrigin
	@GetMapping("/start")
	public int[] startGame(@RequestParam(value = "name", defaultValue = "World") String name) {
		return game.startGame();
	}

	@CrossOrigin
	@GetMapping("/play")
	public GameInfoDTO play(@RequestParam(value = "index") int index) {
		game.makePlay(index);
		String message = "";
		if(game.isGameOver()) {
			logger.info("Game Over");
			message = game.getWinningMessage();
		}
		GameInfoDTO dto = new GameInfoDTO(game.board.getPits(),
										game.board.getCurrentPlayerAsString(),
										game.board.getPlayer1Score(),
										game.board.getPlayer2Score(), message);

		
		return dto;
	}
}