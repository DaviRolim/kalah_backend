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

	@Test
	void shouldReturnTrueIfPlayerCanCaptureUnitsFromOpponent() {
		board.initialize();
		int[] pitsToSet  = {4,4,4,4,0,5,1,5,5,5,4,4,4,0};
		board.setPits(pitsToSet);
		int lastIndex = board.moveUnits(0); // {0,5,5,5,1...} it was zero and now finished with 1 unit in pit
		assertEquals(board.canCaptureOppositeSide(lastIndex), true);
	}

	@Test
	void shouldCaptureUnitsIfPlayerCanCaptureUnitsFromOpponent() {
		board.initialize();
		int[] pitsToSet  = {6,2,6,0,7,6,1,0,6,1,1,1,8,3};
		board.setPits(pitsToSet);
		// Units on Kalah for player2 = 2
		assertEquals(1, board.getPlayer1Score());
		int lastIndex = board.moveUnits(1); // {0,5,5,5,1...} it was zero and now finished with 1 unit in pit
		board.executeCapture(lastIndex);
		assertEquals(3, board.getPlayer1Score()); // 1 + 4 from oposite side
	}
	@Test
	void shouldBeGameOverIfOneOfThePlayersHaveAllPitsEmpty() {
		board.initialize();
		int[] pitsToSet  = {0,0,0,0,0,1,25,0,5,3,2,5,0,7};
		board.setPits(pitsToSet);
		board.moveUnits(5); // {0,5,5,5,1...} it was zero and now finished with 1 unit in pit
		assertEquals(26, board.getPlayer1Score()); // adding the last unit after moving the last
		assertEquals(true, board.isGameOver()); // 1 + 4 from oposite side
	}

	@Test
	void playerScoreShouldBeTheSameNumberAsKalahUnitsInPlayerPit() {
		board.initialize();
		int[] pitsToSet  = {0,0,0,0,0,1,25,0,5,3,2,5,0,7};
		board.setPits(pitsToSet);
		assertEquals(25, board.getPlayer1Score());
		assertEquals(7, board.getPlayer2Score());
	}

	@Test
	void playerCantChoosePitFromOpositeSide() {
		board.initialize(); // initialize and current player is Player1 (first 6 indexes)
		int[] pitsToSet  = {0,0,0,0,0,1,25,0,5,3,2,5,0,7};
		board.setPits(pitsToSet);
		assertEquals(false, board.isMoveValid(9));
	}

	@Test
	void playerCantChoosePitIfIPitHasNoUnits() {
		board.initialize(); // initialize and current player is Player1 (first 6 indexes)
		int[] pitsToSet  = {0,0,0,0,0,1,25,0,5,3,2,5,0,7};
		board.setPits(pitsToSet);
		assertEquals(false, board.isMoveValid(0));
	}

	@Test
	void playerCanChoosePitIfItsTheirSideOfBoardAndHasUnitsInSelectedPit() {
		board.initialize(); // initialize and current player is Player1 (first 6 indexes)
		int[] pitsToSet  = {0,0,0,0,0,1,25,0,5,3,2,5,0,7};
		board.setPits(pitsToSet);
		assertEquals(true, board.isMoveValid(5));
	}

}
