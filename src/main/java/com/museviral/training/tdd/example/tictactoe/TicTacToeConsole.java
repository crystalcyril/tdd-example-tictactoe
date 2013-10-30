/**
 * 
 */
package com.museviral.training.tdd.example.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.museviral.training.tdd.example.tictactoe.TicTacToeEngine.Token;

/**
 * A text console for the Tic Tac Toe game.
 * 
 * @author Cyril
 * @since 0.2.0
 */
public class TicTacToeConsole {

	private TicTacToeEngine engine;

	private int inputX, inputY;

	public TicTacToeConsole(final TicTacToeEngine engine) {
		super();

		this.engine = engine;
	}

	/**
	 * Start the game. This function will return once the player choose not to
	 * continue playing.
	 */
	public void start() {

		boolean done = false;

		while (!done) {

			startGameRound();

			drawGameFinish();

			boolean newGame = askNewGame();

			if (newGame) {
				engine.restart();
			} else {
				done = true;
			}

		}

		println("");
		println("Thanks for playing!");

	}

	/**
	 * Ask the user if he wants to contineu playing or quit.
	 * 
	 * @return
	 */
	protected boolean askNewGame() {

		println("Do you want to start a new game? (y/n) ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		boolean r = false;

		while (true) {

			try {
				String s = br.readLine();
				if (s != null)
					s = s.trim();

				if ("y".equalsIgnoreCase(s) || "n".equalsIgnoreCase(s)) {
					r = "y".equalsIgnoreCase(s) ? true : false;
					break;
				} else {

					println("unknown input, please enter 'y' or 'n'");

				}

			} catch (Throwable t) {

			}

		}

		return r;

	}

	/**
	 * Draw the game result if the game is finished.
	 */
	private void drawGameFinish() {

		if (engine.getGameState() == TicTacToeEngine.GameState.Complete) {

			print("Game over.");

			if (engine.getWinner() == null) {
				print(" Game draw.");
			} else {

				print(" Winner is " + tokenToString(engine.getWinner()) + ".");

			}

			println("\n\n");
		}

	}

	/**
	 * This function handles the logic for a complete cycle of a game.
	 */
	private void startGameRound() {

		while (engine.getGameState() != TicTacToeEngine.GameState.Complete) {

			drawUI();

			askUserInput();

			handleInput();

		}
	}

	private void handleInput() {

		boolean valid = engine.place(inputX - 1, inputY - 1);
		if (!valid) {
			println("coordinates invalid, please reenter");
		}

	}

	private void askUserInput() {

		int userX = readX();
		int userY = readY();

		inputX = userX;
		inputY = userY;

	}

	protected int readX() {

		int n;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {

			print("Please enter X (1 to " + engine.getWidth() + "): ");
			String s = null;
			try {
				s = br.readLine();
			} catch (IOException e) {
			}

			try {
				n = Integer.parseInt(s);
			} catch (Throwable t) {
				println("Input invalid, please enter valid value");
				continue;
			}

			if (n <= 0) {
				println("Must be non-zero positive integer");
			} else if (n > engine.getWidth()) {
				println("Too large (>" + engine.getWidth() + ")");
			} else {
				break;
			}
		}

		return n;

	}

	protected int readY() {

		int n;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {

			print("Please enter Y (1 to " + engine.getWidth() + "): ");
			String s = null;
			try {
				s = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				n = Integer.parseInt(s);
			} catch (Throwable t) {
				println("Input invalid, please enter valid value");
				continue;
			}

			if (n <= 0) {
				println("Must be non-zero positive integer");
			} else if (n > engine.getWidth()) {
				println("Too large (>" + engine.getHeight() + ")");
			} else {
				break;
			}
		}

		return n;

	}

	/**
	 * Draw the user interface.
	 */
	public void drawUI() {

		drawCurrentPlayer();

		drawBoard();

	}

	/**
	 * Draw the board status.
	 */
	public void drawBoard() {

		for (int y = 0; y < engine.getHeight(); y++) {

			StringBuffer sb = new StringBuffer();

			// build the content of a row.
			for (int x = 0; x < engine.getWidth(); x++) {
				Token token = engine.getToken(x, y);
				if (sb.length() > 0) {
					sb.append(" ");
				}
				sb.append(tokenToString(token));
			}

			// draw the row
			println(sb.toString());

		}

		println("");

	}

	public void drawCurrentPlayer() {

		println("Current player: " + tokenToString(engine.getNextToken()));

	}

	protected void println(Object o) {
		System.out.println(o);
	}

	protected void print(Object o) {
		System.out.print(o);
	}

	/**
	 * Convert the token to string representation.
	 * 
	 * @param token
	 * @return
	 */
	public String tokenToString(TicTacToeEngine.Token token) {

		if (token == null)
			return ".";

		switch (token) {
		case CIRCLE:
			return "O";
		case CROSS:
			return "X";
		default:
			return " ";
		}
	}
}
