package SolverAlgorithms;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Main.BoardPosition;


public class GraphicalSudokuBoard extends JComponent{

    /**
     * 
     */
    private static final long serialVersionUID = 0;
    private JTextField f[][]= new JTextField[9][9] ;
    private JPanel p[][]= new JPanel [3][3];

    // A list of all positions that should be reset to normal layout at the new board update
    ArrayList<BoardPosition> toBeClearedList = new ArrayList<BoardPosition>();
	private long sleepTime = 0;
    static GraphicalSudokuBoard graphicalSudokuBoardObject;
    
    public static void initializeGraphicalSudokuBoardObject()
    {
    	if(graphicalSudokuBoardObject == null)
    	{
    		graphicalSudokuBoardObject = new GraphicalSudokuBoard();
    		graphicalSudokuBoardObject.setSize(200, 200);
    		graphicalSudokuBoardObject.setVisible(true);
    	}
    }
    
    public static GraphicalSudokuBoard getSingletonObject()
    {
    	return graphicalSudokuBoardObject;
    }
    
    private GraphicalSudokuBoard()
	{
        for(int row=0; row<=8; row++){
            for(int column=0; column<=8; column++){
            	JTextField textField = new JTextField(1);
            	textField.setHorizontalAlignment(JTextField.CENTER);            	 
            	textField.setFont(new Font("SansSerif", Font.BOLD, 14));
                f[row][column]= textField;
            }
        }

        for(int row=0; row<=2; row++){
            for(int column=0; column<=2; column++){
                p[row][column]=new JPanel(new GridLayout(3,3));
            }
        }

        setLayout(new GridLayout(3,3,5,5));

        for(int groupRow=0; groupRow<=2; groupRow++){
            for(int groupColumn=0; groupColumn<=2; groupColumn++){    
                for(int row=0; row<=2; row++ ){
                    for(int column=0; column<=2; column++){
                        p[groupRow][groupColumn].add(f[row+groupRow*3][column+groupColumn*3]);
                    }
                }
            add(p[groupRow][groupColumn]);
            }
        }

    }
    
    public void setTileValue(int row, int column, int value)
    {
    	clearTemporaryTileBackgroundColors();
    	
    	f[row][column].setText(String.valueOf(value));
    	
    	addTemporaryBackgroundColor(row, column, new Color(20, 200, 20, 100));
    	sleepAwhile();
    }
    
    public void setTileAsBlocked(int row, int column)
    {
    	addTemporaryBackgroundColor(row, column, Color.gray);
    	sleepAwhile();
    }
    
    public void setSleepTime(long time)
    {
    	sleepTime = time;
    }
    private void sleepAwhile()
    {
    	try
		{
			Thread.sleep(sleepTime);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
    }
    
    private void addTemporaryBackgroundColor(int row, int column, Color color)
	{
    	BoardPosition pos = new BoardPosition(row, column);
    	
    	f[pos.getRow()][pos.getColumn()].setBackground(color);
    	toBeClearedList.add(pos);
	}

	public void clearTemporaryTileBackgroundColors()
	{
    	for(BoardPosition pos : toBeClearedList)
    	{
        	f[pos.getRow()][pos.getColumn()].setBackground(Color.white);
    	}
    }

	public void setTileUnassigned(int row, int col)
	{
    	f[row][col].setText("");
	}
    
}