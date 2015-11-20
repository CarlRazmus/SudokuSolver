package Main;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import SolverAlgorithms.GraphicalSudokuBoard;

public class DebugGUI extends JFrame
{
	static JPanel panel;
	static JTextField textField;
	static DebugGUI singletonObject;

	static public void initialize()
	{
		if(singletonObject == null)
		{
			singletonObject = new DebugGUI();
		}
	}
	
	static public DebugGUI getSingletonObject()
	{
		return singletonObject;
	}
	
	private DebugGUI()
	{
		super("debugGUI");
		this.setSize(1400, 700);
		GridLayout gridLayout = new GridLayout(1,2);

		panel = new JPanel();
		panel.setLayout(gridLayout);
		
		GraphicalSudokuBoard.initializeGraphicalSudokuBoardObject();
		
		textField = new JTextField();
		textField.setSize(50,50);
		textField.setText("ladida");
		
		panel.add(GraphicalSudokuBoard.getSingletonObject());
		panel.add(textField);
		
		this.add(panel);
//		this.pack();
		this.setVisible(true);
	}

	static public void writeToTextField(String text)
	{
		textField.setText(text);
	}
	
}
