package SolverAlgorithms;

import java.util.ArrayList;

import Main.BoardPosition;
import Main.DebugGUI;
import Main.SudokuBoard;

public class RowColumnCompareAdvance extends SolverAlgorithm
{

	public RowColumnCompareAdvance()
	{
		super("RowColumnCompareAdvance");
	}

	public boolean calcNextNumber(SudokuBoard boardState)
	{
		ArrayList<Integer> availableSquares = new ArrayList<Integer>();
		ArrayList<Integer> availableSquaresNextIteration = new ArrayList<Integer>();
		ArrayList<Integer> squareArray;
		boolean foundUnassigned;
		int posIndexInSquare;
		boolean outputBool = false;
		
		//iterate over all values
		for(int currentNr = 1; currentNr <= 9; currentNr++)
		{
			availableSquares = boardState.createBlockadesByValue(currentNr);
			availableSquaresNextIteration.addAll(availableSquares); //do not use "=", 
			
			DebugGUI.writeToTextField(String.valueOf(currentNr));
				
			//iterate over all squares that did not contain value
			for(int squareIndex : availableSquares)
			{
				
				squareArray = boardState.getSquareValues(squareIndex);
				foundUnassigned = false;
				posIndexInSquare = SudokuBoard.UNASSIGNED;
				
				for(int squarePosIndex = 0; squarePosIndex < 9; squarePosIndex++)
				{
					if(squareArray.get(squarePosIndex) == SudokuBoard.UNASSIGNED)
					{
						if(foundUnassigned)
						{
							posIndexInSquare = SudokuBoard.UNASSIGNED;
							break;
						}
						else
						{
							posIndexInSquare = squarePosIndex;
							foundUnassigned = true;
						}
					}
				}

				//place the tile if one was discovered
				if(posIndexInSquare != SudokuBoard.UNASSIGNED)
				{
					BoardPosition pos = boardState.getBoardPosition(squareIndex, posIndexInSquare);

//					System.out.println("found pos, squareIndex: " + squareIndex + ", posIndexInSquare: " + posIndexInSquareToUpdate);
//					System.out.println("found pos, row: " + pos.getRow() + ", column: " + pos.getColumn());
					
					boardState.setValue(pos.getRow(), pos.getColumn(), currentNr);
					
					printFoundValueInfo(boardState, pos.getRow(), pos.getColumn(), currentNr);
					
					outputBool = true;
					
					availableSquaresNextIteration.remove(availableSquaresNextIteration.indexOf(squareIndex));
				}
			}
			
			//TODO: should keep doing this until no new blockades are found
			
			//only do this if no solution was found!!! could lead to unnecessary iterations otherwise.
			
			//iterate again to see if any assumptions can be made depending on the temporary blocked tiles.
			for (int squareIndex : availableSquaresNextIteration)
			{
				int rowNr = SudokuBoard.UNASSIGNED;
				int columnNr = SudokuBoard.UNASSIGNED;

				squareArray = boardState.getSquareValues(squareIndex);
				
				for(int squarePosIndex = 0; squarePosIndex < 9; squarePosIndex++)
				{
					if(squareArray.get(squarePosIndex) == SudokuBoard.UNASSIGNED)
					{
						if(rowNr == SudokuBoard.UNASSIGNED && columnNr == SudokuBoard.UNASSIGNED)
						{
							rowNr = (int)(squarePosIndex / 3);
							columnNr = squarePosIndex % 3;
						}
						
						//verify that the found tile is either same row or column as last found tile.
						else
						{
							if(rowNr != SudokuBoard.ERROR)
								if(!checkIsTileSameRow(rowNr, squarePosIndex))
									rowNr = SudokuBoard.ERROR;
							if(columnNr != SudokuBoard.ERROR)
								if(!checkIsTileSameColumn(columnNr, squarePosIndex))
									columnNr = SudokuBoard.ERROR;
						}
					}
				}
				
				//update the row or column with TILE_BLOCKED status and try to find a new number when no BLOCKS are performed any more
			}
			  
			boardState.removeBlockades();
		}
		
		return outputBool;
	}

	
	private boolean checkIsTileSameRow(int rowNr, int squarePosIndex)
	{
		if(rowNr == (int)(squarePosIndex / 3))
			return true;
		
		return false;
	}

	private boolean checkIsTileSameColumn(int columnNr, int squarePosIndex)
	{
		if(columnNr == squarePosIndex % 3)
			return true;
		
		return false;
	}
}
