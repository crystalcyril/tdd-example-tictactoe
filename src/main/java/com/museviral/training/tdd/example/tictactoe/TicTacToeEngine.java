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
	
	protected int tokenCount;
	
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
		
		// reset number of token.
		tokenCount = 0;

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
		
		// place the token on the board.
		board[x][y] = getNextToken();
		
		// increment number of token placed counter.
		tokenCount++;
		
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
		
		checkWinningConditions();
		
		updateNextToken();

	}

	/**
	 * 
	 * requirement_000600
	 */
	protected void checkWinningConditions() {
		
		checkHorizontalWin();
		
		// short-circuit operation. stop checking if game is finished.
		if (gameState == GameState.Complete) return;
		
		checkVerticalWin();
		
		if (gameState == GameState.Complete) return;
		
		checkDiagonalWin();
		
		if (gameState == GameState.Complete) return;
		
		// check for draw conditions. must be called AFTER the 
		// horizontal, vertical and diagonal checkings.
		checkDraw();
		
	}
	
	/**
	 * Check for game draw condition.
	 * <p>
	 * 
	 * requirement_001700
	 */
	protected void checkDraw() {
		
		// if the board is full and no one win, the game is draw.
		if (this.tokenCount == getHeight() * getWidth() && this.winner == null && gameState == GameState.InProgress) {
			this.gameState = GameState.Complete;
		}
		
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
	
	
	/**
	 * Check for winning conditions for vertical lines (columns)
	 * <p>
	 * 
	 * Requirement: requirement_000900
	 */
	protected void checkVerticalWin() {
		
		for (int x = 0; x < getWidth(); x++) {

			Token potentialWinner = null;
			
			for (int y = 0; y < getHeight(); y++) {
				
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
	
	/**
	 * Check for diagonal winning conditions.
	 * <p>
	 * 
	 * requirement_001100, requirement_001200
	 */
	protected void checkDiagonalWin() {
		
		for (int i = 0; i < 2; i++) {
			
			int x = -1;
			int y = -1;
			int xDiff = 0;
			int yDiff = 0;
			Token potentialWinner = null;

			switch (i) {
			case 0:
				x = 0;
				y = 0;
				xDiff = 1;
				yDiff = 1;
				break;
			case 1:
				x = getWidth() - 1;
				y = 0;
				xDiff = -1;
				yDiff = 1;
				break;
			default:
				throw new RuntimeException("internal error");
			}
		
			for (; x < getWidth() && y < getHeight() && x >= 0 && y >= 0; x += xDiff, y += yDiff) {
				
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
				
			}
			
			// if this variable is non-null, the game is over and
			// we stop checking further.
			if (potentialWinner != null) {
				this.gameState = GameState.Complete;
				this.winner = potentialWinner;
				break;
			}
			
		}
		
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
	public Token getWinner() {
		return winner;
	}

	public void restart() {

		this.initialize();
		
	}

}
