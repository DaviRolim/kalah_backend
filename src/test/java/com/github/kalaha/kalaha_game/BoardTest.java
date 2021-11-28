package com.github.kalaha.kalaha_game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.kalaha.kalaha_game.domain.Board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardTest {

	@Autowired
	Board board;

	@Test
	void firstPlayerToPlayShouldBePlayer1() {
		// arrange
		board.initializePits();

		// assert
		assertEquals(board.getCurrentPlayer().ordinal(), 0);

	}

	@Test
	void shouldChangePlayerTurnIfLastIndexIsNotOnHisKalah() {
		// arrange
		board.initializePits();

		// act
		int lastIndex = board.moveUnits(0); // {0,5,5,5,5,4,0...}

		// assert
		assertEquals(board.shouldChangeCurrentPlayer(lastIndex), true);
	}

	@Test
	void shouldNotChangePlayerTurnIfLastIndexIsOnHisKalah() {
		// arrange
		board.initializePits();

		// act
		int lastIndex = board.moveUnits(2); // {4,4,0,5,5,5,1...}

		// assert
		assertEquals(board.shouldChangeCurrentPlayer(lastIndex), false);
	}

	@Test
	void shouldReturnTrueIfPlayerCanCaptureUnitsFromOpponent() {
		// arrange
		board.initializePits();

		// act
		int[] pitsToSet = { 4, 4, 4, 4, 0, 5, 1, 5, 5, 5, 4, 4, 4, 0 };
		board.setPits(pitsToSet);
		int lastIndex = board.moveUnits(0); // {0,5,5,5,1...} it was zero and now finished with 1 unit in pit

		// assert
		assertEquals(board.canCaptureOppositeSide(lastIndex), true);
	}

	@Test
	void shouldCaptureUnitsIfPlayerCanCaptureUnitsFromOpponent() {
		// arrange
		board.initializePits();
		int[] pitsToSet = { 6, 2, 6, 0, 7, 6, 1, 0, 6, 1, 1, 1, 8, 3 };

		// act
		board.setPits(pitsToSet);

		// Units on Kalah for player2 = 2
		// assert
		assertEquals(1, board.getPlayer1Score());

		// act
		int lastIndex = board.moveUnits(1); // {0,5,5,5,1...} it was zero and now finished with 1 unit in pit
		board.executeCapture(lastIndex);

		// assert
		assertEquals(3, board.getPlayer1Score()); // 1 + 4 from oposite side
	}

	@Test
	void shouldBeGameOverIfOneOfThePlayersHaveAllPitsEmpty() {
		// arrange
		board.initializePits();
		int[] pitsToSet = { 0, 0, 0, 0, 0, 1, 25, 0, 5, 3, 2, 5, 0, 7 };
		board.setPits(pitsToSet);

		// act
		board.moveUnits(5); // {0,5,5,5,1...} it was zero and now finished with 1 unit in pit

		// assert
		assertEquals(26, board.getPlayer1Score()); // adding the last unit after moving the last
		assertEquals(true, board.isGameOver()); // 1 + 4 from oposite side
	}

	@Test
	void playerScoreShouldBeTheSameNumberAsKalahUnitsInPlayerPit() {
		// arrange
		board.initializePits();

		// act
		int[] pitsToSet = { 0, 0, 0, 0, 0, 1, 25, 0, 5, 3, 2, 5, 0, 7 };
		board.setPits(pitsToSet);

		// assert
		assertEquals(25, board.getPlayer1Score());
		assertEquals(7, board.getPlayer2Score());
	}

	@Test
	void playerCantChoosePitFromOpositeSide() {
		// arrange
		board.initializePits(); // initializePits and current player is Player1 (first 6 indexes)

		// act
		int[] pitsToSet = { 0, 0, 0, 0, 0, 1, 25, 0, 5, 3, 2, 5, 0, 7 };
		board.setPits(pitsToSet);

		// assert
		assertEquals(false, board.isMoveValid(9));
	}

	@Test
	void playerCantChoosePitIfIPitHasNoUnits() {
		// arrange
		board.initializePits(); // initializePits and current player is Player1 (first 6 indexes)
		int[] pitsToSet = { 0, 0, 0, 0, 0, 1, 25, 0, 5, 3, 2, 5, 0, 7 };
		board.setPits(pitsToSet);
		assertEquals(false, board.isMoveValid(0));
	}

	@Test
	void playerCanChoosePitIfItsTheirSideOfBoardAndHasUnitsInSelectedPit() {
		// arrange
		board.initializePits(); // initializePits and current player is Player1 (first 6 indexes)

		// act
		int[] pitsToSet = { 0, 0, 0, 0, 0, 1, 25, 0, 5, 3, 2, 5, 0, 7 };
		board.setPits(pitsToSet);

		// assert
		assertEquals(true, board.isMoveValid(5));
	}

}
