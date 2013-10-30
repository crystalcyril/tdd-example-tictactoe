/**
 * 
 */
package com.museviral.training.tdd.example.tictactoe;

/**
 * @author Cyril
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TicTacToeEngine engine = new TicTacToeEngine();
		
		TicTacToeConsole console = new TicTacToeConsole(engine);
		
		console.start();
		
	}

}
