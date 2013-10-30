/**
 * 
 */
package com.museviral.training.tdd.example.tictactoe;

/**
 * 
 * 
 * @author Cyril
 */
public class TicTacToeEngine {

	public static enum Token {

		CIRCLE,

		CROSS

	}

	public static enum GameState {

		InProgress,
		
		Complete

	}

	protected Token defaultNextToken = Token.CIRCLE;

	protected Token nextToken;

	protected Token board[][];

	/**
	 * The game state
	 * <p>
	 * 
	 * requirement_000600
	 */
	protected GameState gameState;

	/**
	 * The winner token.
	 * <p>
	 * 
	 * requirement_000600
	 */
	protected Token winner;
	
	public TicTacToeEngine() {
		super();

		initialize();
	}

	protected void initialize() {

		createNewBoard();

		resetNextToken();

		gameState = GameState.InProgress;
		
		// no winner
		winner = null;

	}

	protected void createNewBoard() {
		
		// clear the board
		board = new Token[getWidth()][getHeight()];
		
	}

	protected void resetNextToken() {
		this.nextToken = defaultNextToken;
	}

	public int getWidth() {
		return 3;
	}

	public int getHeight() {
		return 3;
	}

	/**
	 * Returns the current game state.
	 * 
	 * @return
	 */
	public GameState getGameState() {

		// requirement_000100
		return gameState;
	}

	/**
	 * Returns the next token will be placed.
	 * 
	 * @return the next token will be placed.
	 */
	public Token getNextToken() {

		// requirement_000100

		return this.nextToken;
	}

	public boolean place(int x, int y) {

		// requirement_000400: if the specified index is out of bound, report
		// error.
		if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
			return false;
		}

		// requirement_000300: check if the specified location is filled.
		if (board[x][y] != null)
			return false;
		
		// requirement_000800: if game is finished, should stop further placement
		if (gameState != GameState.InProgress) {
			return false;
		}
		

		board[x][y] = getNextToken();
		
		updateInteralState();

		return true;
	}
	
	/**
	 * Calculate the internal state.
	 * <p>
	 * 
	 * This method is created due to requirement: requirement_000600.
	 */
	protected void updateInteralState() {
		
		updateGameState();
		
		updateNextToken();

	}

	/**
	 * 
	 * requirement_000600
	 */
	protected void updateGameState() {
		
		checkHorizontalWin();
		
	}
	
	/**
	 * Check for winning conditions for horizontal lines.
	 * <p>
	 * 
	 * Requirement: requirement_000600
	 */
	protected void checkHorizontalWin() {
		
		for (int y = 0; y < getHeight(); y++) {

			Token potentialWinner = null;
			
			for (int x = 0; x < getWidth(); x++) {
				
				// stop checking the row if it is empty.
				if (board[x][y] == null) {
					potentialWinner = null;
					break;
				}
				
				// remember the first token we encountered.
				if (potentialWinner == null) {
					potentialWinner = board[x][y];
					continue;
				}
				
				// if we detect a line with different token, this line
				// does not meet the winning criteria and we 
				// skip to next row.
				if (board[x][y] != potentialWinner) {
					potentialWinner = null;
					break;
				}
				
			}	// for each column in a row
			
			// if this variable is non-null, the game is over and
			// we stop checking further.
			if (potentialWinner != null) {
				this.gameState = GameState.Complete;
				this.winner = potentialWinner;
				break;
			}
			
		}	// for each row.
		
	}
	
	public Token getToken(int x, int y) {

		return board[x][y];

	}

	protected void updateNextToken() {
		
		// requirement_000600: do nothing if the game is completed.
		if (this.gameState == GameState.Complete) return;
		
		this.nextToken = (nextToken == Token.CIRCLE) ? Token.CROSS
				: Token.CIRCLE;
	}

	/**
	 * Returns the winner of this games
	 * @return
	 */
	public Object getWinner() {
		return winner;
	}

}
