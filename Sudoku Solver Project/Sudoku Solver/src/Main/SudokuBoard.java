package Main;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import SolverAlgorithms.GraphicalSudokuBoard;

public class SudokuBoard
{

	private static final int TEMPORARY_BLOCKED = 0;
	
	public static final int UNASSIGNED = -1;
	public static final int ERROR = -5;

	private int[][] board = new int[9][9];
	private int[] columnArray = new int[9];

	private HashMap<Integer, ArrayList<Integer>> legalValues = new HashMap<Integer, ArrayList<Integer>>();

	//initializes an empty sudoku board
	public SudokuBoard()
	{
		
	}
	
	public SudokuBoard(int[] initializedBoard)
	{
		for (int row = 0; row < 9; row++)
			for (int column = 0; column < 9; column++)
				if (isUnassigned(column, row))
					board[row][column] = initializedBoard[row * 9 + column];
	}


	public void updateAllLists()
	{
		for (int row = 0; row < 9; row++)
			for (int column = 0; column < 9; column++)
				if (isUnassigned(row, column))
					legalValues.put(calculateSinglePosValue(row, column), calculateLegalValues(row, column));
	}
	
	/**
	 * 0 indexed (0-80)
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public int calculateSinglePosValue(int row, int column)
	{
		return (row * 9 ) + column;
	}

	private ArrayList<Integer> calculateLegalValues(int row, int column)
	{
		ArrayList<Integer> output = new ArrayList<Integer>();

		// if the tile already has a value then return a empty array
		if (board[row][column] != UNASSIGNED)
			return output;

		// adds values 1-9 to the array
		for (int i = 1; i < 10; i++)
			output.add(i);

		// removes all the values that is in the same row from output
		for (Integer i : board[row])
			if (i != UNASSIGNED)
				output.remove(i);

		// removes all the values that is in the same column from output
		for (int i = 0; i < 9; i++)
		{
			Integer value = board[i][column];

			if (value != UNASSIGNED)
				if (output.contains(value))
					output.remove(value);
		}

		// removes all the values that is in the same square from output
		for (Integer value : getSquareValues(getSquareNr(row, column)))
		{
			if (output.contains(value))
				output.remove(value);
		}

		return output;
	}

	public ArrayList<Integer> getSquareValues(int square)
	{
		int startRow = (int) (square / 3) * 3; // starts on row 0, 3 and 6
		int startColumn = (square % 3) * 3; // starts on col 0, 3 and 6

		ArrayList<Integer> output = new ArrayList<Integer>();

		for (int row = startRow; row < startRow + 3; row++)
			for (int col = startColumn; col < startColumn + 3; col++)
					output.add(board[row][col]);

		return output;
	}
	
	public BoardPosition getBoardPosition(int square, int squareValuePos)
	{
		int row = (int) (square / 3) * 3 + (int)(squareValuePos/3); 
		int column = (square % 3) * 3 + (squareValuePos % 3); 
		
		return new BoardPosition(row, column);
	}

	public int[] getRow(int i)
	{
		return board[i];
	}

	public ArrayList<Integer> getValidNumbers(int row, int column)
	{
		return calculateLegalValues(row, column);
	}
	
	public int getSquareNr(int row, int column)
	{
		int square = ((int) (row / 3)) * 3 + ((int) (column / 3));

		return square; // returns the square as 0-indexed
	}

	public boolean isUnassigned(int row, int col)
	{
		if (board[row][col] == UNASSIGNED)
			return true;

		return false;
	}

	public boolean isSolution()
	{
		// iterates over all rows and columns and checks that each contains the
		// value-range 1-9 and no same values twice
		for (int row = 0; row < 9; row++)
		{
			if (!isContaining1to9(board[row]))
				return false;

			for (int col = 0; col < 9; col++)
				columnArray[col] = board[col][row];

			if (!isContaining1to9(columnArray))
				return false;
		}

		// iterates over all squares and checks if all contains a legal set of
		// values
		for (int i = 0; i < 9; i++)
			if (!isLegalSquare(i))
				return false;

		return true;

	}

	private boolean isLegalSquare(int square)
	{
		return isContaining1to9(getSquareValues(square));
	}

	private boolean isContaining1to9(ArrayList<Integer> array)
	{
		boolean foundValue;

		for (int value = 1; value < 10; value++)
		{
			foundValue = false;

			for (int i = 0; i < array.size(); i++)
			{
				if (array.get(i) == value)
				{
					foundValue = true;
					break;
				}
			}
			if (!foundValue)
				return false;
		}
		return true;
	}

	private boolean isContaining1to9(int[] array)
	{
		boolean foundValue;

		for (int value = 1; value < 10; value++)
		{
			foundValue = false;

			for (int i = 0; i < array.length; i++)
				if (array[i] == value)
				{
					foundValue = true;
					break;
				}

			if (!foundValue)
				return false;
		}
		return true;
	}

	public void setUnassigned(int row, int col)
	{
		board[row][col] = UNASSIGNED;
		GraphicalSudokuBoard.getSingletonObject().setTileUnassigned(row, col);
	}

	public void setValue(int row, int col, int value)
	{
		board[row][col] = value;
		GraphicalSudokuBoard.getSingletonObject().setTileValue(row, col, value);
	}

	public void printBoard()
	{
		System.out.println("+-----------------------+");

		for (int row = 0; row < 9; row++)
		{
			if (row == 3 || row == 6)
				System.out.println("+-------+-------+-------+");
			printRow(row);
		}

		System.out.println("+-------+-------+-------+");
	}

	private void printRow(int row)
	{
		for (int col = 0; col < 9; col++)
		{
			if (col == 0 || col == 3 || col == 6)
				System.out.print("| ");

			int value = board[row][col];

			if (value != UNASSIGNED)
				System.out.print(value + " ");
			else
				System.out.print("  ");
		}

		System.out.println("|");
	}

	public void printLegalValues()
	{
		for (int key : legalValues.keySet())
		{
			System.out.print("key " + key + ": ");
			for (int value : legalValues.get(key))
				System.out.print(value + " ");
			System.out.println();
		}
	}

	/* -------- help functions for statistics --------- */

	public BigInteger getSearchSpace()
	{
		BigInteger searchSpace = BigInteger.valueOf(1);

		for (int key : legalValues.keySet())
		{
			BigInteger size = BigInteger.valueOf(legalValues.get(key).size());
			//
			System.out.println("size " + size.intValue());

			if (size.intValue() != 0)
				searchSpace = searchSpace.multiply(size);

			// System.out.println("searchSpace " + searchSpace);
		}

		return searchSpace;
	}

	/**
	 * Sets all UNASSIGNED positions to BLOCKED in the rows, columns and blocks
	 * that has index in them
	 * 
	 * @param index
	 */
	public ArrayList<Integer> createBlockadesByValue(int value)
	{
		ArrayList<Integer> emptySquares = new ArrayList<Integer>();
		
		for(int square = 0; square < 9; square++)
		{
			ArrayList<Integer> squareValues = getSquareValues(square);
			
			if(squareValues.contains(value))
			{
				int squareValuePos = squareValues.indexOf(value);
				
				BoardPosition pos = getBoardPosition(square, squareValuePos);
				
				blockRow(pos.getRow());
				blockColumn(pos.getColumn());
			}
			else
			{
				emptySquares.add(square);
			}
		}
		
		return emptySquares;
	}

	private void blockColumn(int column)
	{
		for(int row = 0; row < 9; row++)
		{
			if(board[row][column] == UNASSIGNED)
			{
				board[row][column] = TEMPORARY_BLOCKED;
				GraphicalSudokuBoard.getSingletonObject().setTileAsBlocked(row, column);
			}
		}
	}

	private void blockRow(int row)
	{
		int[] rowArray = getRow(row);
		for(int column = 0; column < rowArray.length; column++){
			if(rowArray[column] == UNASSIGNED)
			{
				rowArray[column] = TEMPORARY_BLOCKED;
				GraphicalSudokuBoard.getSingletonObject().setTileAsBlocked(row, column);
			}
		}
	}

	public void removeBlockades()
	{
		for (int row = 0; row < 9; row++)
		{
			for (int col = 0; col < 9; col++)
				if(board[col][row] == TEMPORARY_BLOCKED)
					board[col][row] = UNASSIGNED;
		}
		GraphicalSudokuBoard.getSingletonObject().clearTemporaryTileBackgroundColors();
	}

}
