package SolverAlgorithms;

import java.util.ArrayList;

import Main.BoardPosition;
import Main.SudokuBoard;

public class RowColumnCompare extends SolverAlgorithm
{

	public RowColumnCompare()
	{
		super("RowColumnCompare");
	}

	public boolean calcNextNumber(SudokuBoard boardState)
	{
		ArrayList<Integer> availableSquares = new ArrayList<Integer>();
		ArrayList<Integer> squareArray;
		boolean foundUnassigned;
		int posIndexInSquare;
		boolean outputBool = false;
		
		for(int currentNr = 1; currentNr <= 9; currentNr++)
		{
			availableSquares = boardState.createBlockadesByValue(currentNr);
			
			//iterate over all squares that did not contain value
			for(int squareIndex : availableSquares)
			{
//				System.out.println("running calcNextNumber in Comparator for nr " + squareIndex);
				
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
				}
			}
			  
			boardState.removeBlockades();
		}
		
		return outputBool;
	}

}
