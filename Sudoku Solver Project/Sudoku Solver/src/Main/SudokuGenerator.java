package Main;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.plaf.SeparatorUI;

public class SudokuGenerator
{
	SudokuBoard board;
	
	public SudokuGenerator()
	{
		board = new SudokuBoard();
	}
	
	
	public SudokuBoard generateRandomBoard(int amountOfKnownStartNumbers)
	{
		for (int row = 0; row < 9; row++)
		{
			for (int column = 0; column < 9; column++)
			{
				board.setUnassigned(row, column);
			}
		}
		
		if(recursiveFindBoard(0, 0))
		{
			System.out.println("successfully generated a SUDOKU BOARD");
			board.printBoard();
		}
		else
		{
			board = null;
			
			System.out.println("unsuccessful generating of sudoku board");
		}
		
		removeNumbersFromBoard(amountOfKnownStartNumbers);
		
		return board;
	}
	
	private void removeNumbersFromBoard(int amountOfKnownStartNumbers)
	{
		
		int[][] validPlaces = new int[9][9];
		
		for (int row = 0; row < 9; row++)
		{
			for (int column = 0; column < 9; column++)
			{
				validPlaces[row][column] = -1;
			}
		}
		
		for(int i = 0; i < 81 - amountOfKnownStartNumbers; i++)
		{
			boolean successfulRemoval = false;
			int row;
			int column;
			do
			{
				row = generateRandomRowOrColumnNumber();
				column = generateRandomRowOrColumnNumber();
				
				if(validPlaces[row][column] == -1)
				{
					validPlaces[row][column] = 0;
					board.setUnassigned(row, column);
					successfulRemoval = true;
				}
			} while (!successfulRemoval );
		}
	}
	
	private int generateRandomRowOrColumnNumber()
	{
		Random r = new Random();
		
		return r.nextInt(9);
	}
	
	private boolean recursiveFindBoard(int row, int column)
	{
		if(row == 9) //this row doesn't exist, it is sent when the board is full -> check for solution
		{
			System.out.println("board is full, checking for solution");
			return board.isSolution();
		}
		
		ArrayList<Integer> validNumbers;
		Random r;
		Integer randomNumber;
		int randomNumberIndex = 0;
		int nrIterations;
		int nextRow;
		int nextColumn;

		validNumbers = board.getValidNumbers(row, column);
		nrIterations = validNumbers.size();

		r = new Random();
		
		for(int currentIteration = 0; currentIteration < nrIterations; currentIteration++)
		{
			if(validNumbers.size() == 0) // no more valid numbers exists, fall back to the last recursion.
				return false;
			
			randomNumberIndex = r.nextInt(validNumbers.size());
			randomNumber = validNumbers.get(randomNumberIndex);
			validNumbers.remove(randomNumberIndex); //removes the randomly picked number from the list so that it cannot be picked again
			
			board.setValue(row, column, randomNumber);
			
			nextColumn = column + 1;
			nextRow = row;
			
			if(nextColumn == 9)
			{
				nextColumn = 0;
				nextRow++;
			}
			
			if(recursiveFindBoard(nextRow, nextColumn))
				return true;
			else
			{
				board.setUnassigned(row, column);
			}
		}
		
		return false;
	}
	
}