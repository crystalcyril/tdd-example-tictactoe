/**
 * 
 */
package com.museviral.training.tdd.example.tictactoe.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.museviral.training.tdd.example.tictactoe.TicTacToeEngine;
import com.museviral.training.tdd.example.tictactoe.TicTacToeEngine.Token;

/**
 * 
 * 
 * @author Cyril
 * 
 */
public class TicTacToeEngineTest {

	TicTacToeEngine game;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		game = new TicTacToeEngine();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		game = null;
	}

	/**
	 * Initial status.
	 * <p>
	 * 
	 * Game board state:
	 * 
	 * <pre>
	 * . . .
	 * . . .
	 * . . .
	 * </pre>
	 */
	@Test
	public void requirement_000100_InitialStatus() {

		assertGameIsInProgress();

		assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

		assertGameBoardIsEmpty();

		assertEquals("game width", 3, game.getWidth());
		assertEquals("game height", 3, game.getHeight());

	}

	/**
	 * A user has placed a token on the board.
	 * <p>
	 * 
	 * Game board state:
	 * 
	 * <pre>
	 * O . .
	 * . . .
	 * . . .
	 * </pre>
	 */
	@Test
	public void requirement_000200_PlaceOneValidMove() {

		assertGameIsInProgress();
		assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

		// WHEN user placed a token.
		boolean placeResult = game.place(0, 0);
		assertTrue("place result should be", placeResult);

		//
		// THEN
		//
		assertTokenAtCoordinatesShouldBe(0, 0, TicTacToeEngine.Token.CIRCLE);

		assertBoardShouldBeEmptyExceptCoordinates(new int[][] { { 0, 0 } });

		assertNextMoveShouldBe(TicTacToeEngine.Token.CROSS);

		assertGameIsInProgress();

	}

	/**
	 * A user has placed a token on the same coordinate twice.
	 * <p>
	 * 
	 * Game board state:
	 * 
	 * <pre>
	 * O . .
	 * . . .
	 * . . .
	 * </pre>
	 */
	@Test
	public void requirement_000300_PlaceSameCoordinateTwice() {

		assertGameIsInProgress();
		assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

		// WHEN user placed a token.
		boolean placeResult = game.place(0, 0);
		assertTrue("place result should be", placeResult);

		placeResult = game.place(0, 0);
		assertFalse("place result should be", placeResult);

		//
		// THEN
		//
		assertTokenAtCoordinatesShouldBe(0, 0, TicTacToeEngine.Token.CIRCLE);

		assertBoardShouldBeEmptyExceptCoordinates(new int[][] { { 0, 0 } });

		assertNextMoveShouldBe(TicTacToeEngine.Token.CROSS);

		assertGameIsInProgress();

	}

	/**
	 * A user has placed a token outside illegal coordinates
	 * <p>
	 * 
	 * Game board state:
	 * 
	 * <pre>
	 * O . .
	 * . . .
	 * . . .
	 * </pre>
	 */
	@Test
	public void requirement_000400_PlaceOutsideBoard() {

		assertGameIsInProgress();

		assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

		int xyPairs[][] = new int[][] { { -1, 0 }, { 0, -1 }, { 3, 0 },
				{ 0, 3 } };

		for (int[] xyPair : xyPairs) {

			int x = xyPair[0];
			int y = xyPair[1];

			//
			// WHEN user placed a token.
			//
			boolean placeResult = game.place(x, y);
			assertFalse("place result " + x + "," + y + " should be",
					placeResult);

			//
			// THEN
			//
			assertGameBoardIsEmpty();

			assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

			assertGameIsInProgress();

		}

	}

	/**
	 * Place two moves
	 * <p>
	 * 
	 * Game board state:
	 * 
	 * <pre>
	 * O X .
	 * . . .
	 * . . .
	 * </pre>
	 */
	@Test
	public void requirement_000500_MakeTwoMoves() {

		boolean placeResult;

		// WHEN user placed a token.
		placeResult = game.place(0, 0);
		assertTrue("place result should be", placeResult);

		placeResult = game.place(1, 0);
		assertTrue("place result should be", placeResult);

		//
		// THEN
		//
		assertTokenAtCoordinatesShouldBe(0, 0, TicTacToeEngine.Token.CIRCLE);

		assertTokenAtCoordinatesShouldBe(1, 0, TicTacToeEngine.Token.CROSS);

		assertBoardShouldBeEmptyExceptCoordinates(new int[][] { { 0, 0 },
				{ 1, 0 } });

		assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

		assertGameIsInProgress();

	}

	/**
	 * Win the top line.
	 * <p>
	 * 
	 * Game board state:
	 * 
	 * <pre>
	 * O O O
	 * X X .
	 * . . .
	 * </pre>
	 */
	@Test
	public void requirement_000600_WinTopHorizontalLine() {

		// WHEN user placed a token.
		user_successfully_placed_a_token_at(0, 0); // O

		user_successfully_placed_a_token_at(0, 1); // X

		user_successfully_placed_a_token_at(1, 0); // O

		user_successfully_placed_a_token_at(1, 1); // X

		user_successfully_placed_a_token_at(2, 0); // O

		//
		// THEN
		//

		assertBoardShouldBeEmptyExceptCoordinates(new int[][] { { 0, 0 },
				{ 0, 1 }, { 1, 0 }, { 1, 1 }, { 2, 0 } });

		// the token should not advance
		assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

		assertWinnerXHasWon(TicTacToeEngine.Token.CIRCLE);

	}

	/**
	 * Win the top line.
	 * <p>
	 * 
	 * Game board state:
	 * 
	 * <pre>
	 * . . .
	 * X X .
	 * O O O
	 * </pre>
	 */
	@Test
	public void requirement_000700_WinBottomHorizontalLine() {

		// WHEN user placed a token.
		user_successfully_placed_a_token_at(0, 2); // O

		user_successfully_placed_a_token_at(0, 1); // X

		user_successfully_placed_a_token_at(1, 2); // O

		user_successfully_placed_a_token_at(1, 1); // X

		user_successfully_placed_a_token_at(2, 2); // O

		//
		// THEN
		//

		assertBoardShouldBeEmptyExceptCoordinates(new int[][] { { 0, 2 },
				{ 0, 1 }, { 1, 2 }, { 1, 1 }, { 2, 2 } });

		// the token should not advance
		assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

		assertWinnerXHasWon(TicTacToeEngine.Token.CIRCLE);

	}

	/**
	 * User should not be able to make any further token placement if the game
	 * is completed.
	 * <p>
	 * 
	 * Game board state:
	 * 
	 * <pre>
	 * . . .
	 * X X .
	 * O O O
	 * </pre>
	 */
	@Test
	public void requirement_000800_CannotPlayFurtherAfterGameIsCompleted() {

		// WHEN user placed a token.
		user_successfully_placed_a_token_at(0, 2); // O

		user_successfully_placed_a_token_at(0, 1); // X

		user_successfully_placed_a_token_at(1, 2); // O

		user_successfully_placed_a_token_at(1, 1); // X

		user_successfully_placed_a_token_at(2, 2); // O

		// game is finished

		assertWinnerXHasWon(TicTacToeEngine.Token.CIRCLE);

		//
		// WHEN
		//

		boolean placeResult = game.place(0, 0);

		//
		// THEN
		//
		assertFalse("place operation should fail", placeResult);

		// and...
		assertTokenAtCoordinatesShouldBe(0, 0, null);

		// the token should not advance
		assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

		assertWinnerXHasWon(TicTacToeEngine.Token.CIRCLE);

	}

	/**
	 * Win the left most column. Boundary case test.
	 * <p>
	 * 
	 * Game board state:
	 * 
	 * <pre>
	 * O X .
	 * O X .
	 * O . .
	 * </pre>
	 */
	@Test
	public void requirement_000900_WinLeftmostColumn() {

		// WHEN user placed a token.
		user_successfully_placed_a_token_at(0, 0); // O

		user_successfully_placed_a_token_at(1, 0); // X

		user_successfully_placed_a_token_at(0, 1); // O

		user_successfully_placed_a_token_at(1, 1); // X

		user_successfully_placed_a_token_at(0, 2); // O

		// game is finished

		//
		// THEN
		//
		// the token should not advance
		assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

		assertWinnerXHasWon(TicTacToeEngine.Token.CIRCLE);

	}

	/**
	 * Win the right most column. Boundary case test.
	 * <p>
	 * 
	 * Game board state:
	 * 
	 * <pre>
	 * . X O
	 * . X O
	 * . . O
	 * </pre>
	 */
	@Test
	public void requirement_001000_WinRightmostColumn() {

		// WHEN user placed a token.
		user_successfully_placed_a_token_at(2, 0); // O

		user_successfully_placed_a_token_at(1, 0); // X

		user_successfully_placed_a_token_at(2, 1); // O

		user_successfully_placed_a_token_at(1, 1); // X

		user_successfully_placed_a_token_at(2, 2); // O

		// game is finished

		//
		// THEN
		//
		// the token should not advance
		assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

		assertWinnerXHasWon(TicTacToeEngine.Token.CIRCLE);

	}

	/**
	 * Win a diagonal line. Top left to bottom right (\)
	 * <p>
	 * 
	 * Game board state:
	 * 
	 * <pre>
	 * O . .
	 * . O .
	 * X X O
	 * </pre>
	 */
	@Test
	public void requirement_001100_Diagonal_TopLeftToBottomRight() {

		// WHEN user placed a token.
		user_successfully_placed_a_token_at(0, 0); // O

		user_successfully_placed_a_token_at(0, 2); // X

		user_successfully_placed_a_token_at(1, 1); // O

		user_successfully_placed_a_token_at(1, 2); // X

		user_successfully_placed_a_token_at(2, 2); // O

		// game is finished

		//
		// THEN
		//
		// the token should not advance
		assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

		assertWinnerXHasWon(TicTacToeEngine.Token.CIRCLE);

	}

	/**
	 * Win a diagonal line. Top left to bottom right (\)
	 * <p>
	 * 
	 * Game board state:
	 * 
	 * <pre>
	 * X X O
	 * . O .
	 * O . .
	 * </pre>
	 */
	@Test
	public void requirement_001200_Diagonal_TopRightToBottomLeft() {

		// WHEN user placed a token.
		user_successfully_placed_a_token_at(0, 2); // O

		user_successfully_placed_a_token_at(0, 0); // X

		user_successfully_placed_a_token_at(1, 1); // O

		user_successfully_placed_a_token_at(1, 0); // X

		user_successfully_placed_a_token_at(2, 0); // O

		// game is finished

		//
		// THEN
		//
		// the token should not advance
		assertNextMoveShouldBe(TicTacToeEngine.Token.CIRCLE);

		assertWinnerXHasWon(TicTacToeEngine.Token.CIRCLE);

	}
	
	protected void assertTokenAtCoordinatesShouldBe(int x, int y,
			Token expectedToken) {

		Token actualToken = game.getToken(x, y);
		assertEquals("token at (" + x + "," + y + ") should be", expectedToken,
				actualToken);

	}

	protected void assertGameIsInProgress() {

		assertEquals("game state should be",
				TicTacToeEngine.GameState.InProgress, game.getGameState());

		assertNull("there should be no winner for a in-progress game",
				game.getWinner());

	}

	protected void assertNextMoveShouldBe(TicTacToeEngine.Token token) {

		assertEquals("next move should be", token, game.getNextToken());

	}

	protected void assertGameBoardIsEmpty() {

		for (int x = 0; x < game.getWidth(); x++) {
			for (int y = 0; y < game.getHeight(); y++) {
				Token actualToken = game.getToken(x, y);
				assertNull("Token at (" + x + "," + y + ") should be null",
						actualToken);
			}
		}

	}

	protected void assertBoardShouldBeEmptyExceptCoordinates(
			int coordinatesPairs[][]) {

		for (int x = 0; x < game.getWidth(); x++) {

			for (int y = 0; x < game.getHeight(); x++) {

				// determine if the currently iterated coordinate
				// should be skipped
				boolean shouldExclude = false;
				if (coordinatesPairs != null && coordinatesPairs.length > 0) {
					for (int[] pair : coordinatesPairs) {

						assertNotNull(
								"internal error: cooridnate pair should not be null",
								pair);
						assertEquals("coordinate pair should be 2", 2,
								pair.length);

						if (x == pair[0] && y == pair[1]) {
							shouldExclude = true;
						}

					}
				}

				if (shouldExclude)
					continue;

				assertNull("token at " + x + "," + y + " should be null",
						game.getToken(x, y));

			}

		}

	}

	protected void user_successfully_placed_a_token_at(int x, int y) {

		boolean placeResult = game.place(x, y);
		assertTrue("place result at (" + x + "," + y + ") should be",
				placeResult);

	}

	protected void assertWinnerXHasWon(TicTacToeEngine.Token expectedWinner) {

		assertEquals("won game state should be",
				TicTacToeEngine.GameState.Complete, game.getGameState());
		assertEquals("winner should be", expectedWinner, game.getWinner());

	}

}
