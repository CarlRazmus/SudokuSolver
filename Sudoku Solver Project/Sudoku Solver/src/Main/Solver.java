package Main;
import java.util.ArrayList;

import SolverAlgorithms.RowColumnCompare;
import SolverAlgorithms.RowColumnCompareAdvance;
import SolverAlgorithms.SolverAlgorithm;


public class Solver {
	
	ArrayList<SolverAlgorithm> algorithms;
	
	public Solver(){
		algorithms = new ArrayList<SolverAlgorithm>();
		
//		algorithms.add(new RowColumnCompare());
		algorithms.add(new RowColumnCompareAdvance());
	}
/** 
 * Tries to find a solution to the given board by iterating through all its internal algorithms.
 * 
 * @param board
 */
	public void solveBoard(SudokuBoard board)
	{
		boolean foundNewValue;
		
		do {
			
			if(board.isSolution())
			{
				System.out.println("a solution was found");
				return;
			}
			
			foundNewValue = false;
			
			for(SolverAlgorithm algorithm : algorithms)
			{
				board.updateAllLists();
				
				if(algorithm.calcNextNumber(board))
				{
					foundNewValue = true;
					break;
				}
			}
		}
		while(foundNewValue);
		
		
		System.out.println("No solution was found");
		board.printBoard();
	}
}
