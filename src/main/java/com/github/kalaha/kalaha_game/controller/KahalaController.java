package com.github.kalaha.kalaha_game.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.github.kalaha.kalaha_game.domain.Board;
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

    private static final Logger logger = LoggerFactory.getLogger(KahalaController.class);

	@Autowired
	private Game game;

	@CrossOrigin
	@GetMapping("/")
	public String healthCheck() {
		return "Health Check - OK";
	}

	@CrossOrigin
	@GetMapping("/start")
	public Board startGame(@RequestParam(value = "name", defaultValue = "World") String name) {
		return game.startGame();
	}

	@CrossOrigin
	@GetMapping("/play")
	public Board play(@RequestParam(value = "index") int index, @RequestParam(value = "gameId") int gameId) {
		logger.info("GameId: " + String.valueOf(gameId));
		Board board = game.makePlay(index, gameId);
		String message = "";
		if(game.isGameOver()) {
			logger.info("Game Over");
			message = game.getWinningMessage();
		}
		
		return board;
	}
}