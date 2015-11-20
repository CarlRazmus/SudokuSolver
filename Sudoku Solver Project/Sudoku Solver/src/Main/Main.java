package Main;

import SolverAlgorithms.GraphicalSudokuBoard;

public class Main {
	
	
	public static void main(String[] args) {
		SudokuGenerator sudokuGenerator;
		SudokuBoard board;
		Solver solver;

		DebugGUI.initialize();
		
		GraphicalSudokuBoard.getSingletonObject().setTileValue(0, 4, 3);
		
		
		sudokuGenerator = new SudokuGenerator();
		solver = new Solver();
		
		board = sudokuGenerator.generateRandomBoard(45);
		
		System.out.println("Board on start-up:");
		board.printBoard();
		
		GraphicalSudokuBoard.getSingletonObject().setSleepTime(1500);
		
		solver.solveBoard(board);
		
		System.out.println("Board after solving:");
		board.printBoard();
		
	}
}
