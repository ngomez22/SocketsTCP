package ui;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class Files extends JPanel {

	private GUI gui;
	private Downloads downloads;
	private Downloadables downloadables;
	
	public Files(GUI gui) {
		this.gui = gui;
		setLayout( new GridLayout(1,2) );
	    setBorder( new TitledBorder( "Files" ) );
	    setBackground( Color.WHITE );
	    
		downloads = new Downloads();
		downloadables = new Downloadables();
	}

}
