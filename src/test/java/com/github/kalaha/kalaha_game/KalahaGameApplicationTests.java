package com.github.kalaha.kalaha_game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.kalaha.kalaha_game.domain.Board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KalahaGameApplicationTests {

	@Autowired
	Board board;

	@Test
	void firstPlayerToPlayShouldBePlayer1() {
		board.initialize();	
		assertEquals(board.getCurrentPlayerAsString(), "Player1");

	}

	@Test
	void shouldChangePlayerTurnIfLastIndexIsNotOnHisKalah() {
		board.initialize();
		int lastIndex = board.moveUnits(0); // {0,5,5,5,5,4,0...}
		assertEquals(board.shouldChangeCurrentPlayer(lastIndex), true);
	}

	@Test
	void shouldNotChangePlayerTurnIfLastIndexIsOnHisKalah() {
		board.initialize();
		int lastIndex = board.moveUnits(2); // {4,4,0,5,5,5,1...}
		assertEquals(board.shouldChangeCurrentPlayer(lastIndex), false);
	}

}
