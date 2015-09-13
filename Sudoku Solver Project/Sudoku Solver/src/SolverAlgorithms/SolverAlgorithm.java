package SolverAlgorithms;

import Main.SudokuBoard;


public abstract class SolverAlgorithm {

	/**
	 * Tries to find new values to insert on the board
	 * 
	 * @param boardState
	 * @return returns null if no new values was found, otherwise returns the new board state.
	 */
	public abstract boolean searchForValues(SudokuBoard boardState);
}
