package com.github.kalaha.kalaha_game.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.github.kalaha.kalaha_game.domain.Board;
import com.github.kalaha.kalaha_game.domain.GameOrchestrator;
import com.github.kalaha.kalaha_game.utils.failures.Failure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.vavr.control.Either;

@RestController
public class KalahController {

	private static final Logger logger = LoggerFactory.getLogger(KalahController.class);

	@Autowired
	private GameOrchestrator game;

	@CrossOrigin
	@GetMapping("/")
	public String healthCheck() {
		return "Health Check - OK";
	}

	@CrossOrigin
	@GetMapping("/start")
	public Board startGame() {
		return game.startGame();
	}

	@CrossOrigin
	@GetMapping("/play")
	public Board play(@RequestParam(value = "index") int index, @RequestParam(value = "gameId") int gameId) {
		Either<Failure, Board> result = game.makePlay(index, gameId);
		if (result.isRight()) {
			return result.get();
		} else {
			logger.info(result.getLeft().getMessage());
			return game.startGame();
		}
	}
}