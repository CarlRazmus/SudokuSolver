package SolverAlgorithms;

import Main.SudokuBoard;


public abstract class SolverAlgorithm {

	protected String algorithmName;
	
	public SolverAlgorithm(String algorithmName)
	{
		this.algorithmName = algorithmName;
	}
	
	/**
	 * Tries to find new values to insert on the board
	 * 
	 * @param boardState
	 * @return returns null if no new values was found, otherwise returns the new board state.
	 */
	public abstract boolean calcNextNumber(SudokuBoard boardState);
	
	/**
	 * Prints information about which algorithm that was used, which value was found and where
	 */
	protected void printFoundValueInfo(SudokuBoard boardState, int row, int column, int value)
	{
		System.out.println("Found new value, Algorithm: " + algorithmName + ", row: " + row + ", column: " + column);
		System.out.println("new board state:");
		boardState.printBoard();
	}
}
