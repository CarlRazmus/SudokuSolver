package Main;
import java.util.ArrayList;

import SolverAlgorithms.RowColumnCompare;
import SolverAlgorithms.SolverAlgorithm;


public class Solver {
	
	ArrayList<SolverAlgorithm> algorithms;
	
	public Solver(){
		algorithms.add(new RowColumnCompare());
	}
	
	public boolean solveBoard(SudokuBoard board)
	{
		boolean foundNewValue = true;
		
		// Iterate through all different solving algorithms, if an algorithm finds a new number then restart on the first algorithm.
		// If none of the algorithm finds a new number then no solution can be found and solveboard will return false.
		
		while(foundNewValue){

			//begin with updating the LIST for each empty board slot.
			//this shall ofc be updated later to remove new found values instead of updating all values all the time)
			board.updateAllLists();
			
			foundNewValue = false;
			
			for(SolverAlgorithm algorithm : algorithms)
			{
				boolean foundNewValue = algorithm.searchForValues(board);
			}
		
		}
		System.out.println("No solution was found");
		board.printBoard();
		return false;
	}
}
