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
	    
		downloads = new Downloads(gui);
		downloadables = new Downloadables(gui);
		
		add(downloads);
		add(downloadables);
	}
	
	public void updateDownloads(String[] files) {
		for(int i=0; i<files.length; i++) {
			downloads.addFile(files[i]);
		}
	}
	
	public void updateDownloadables(String[] files) {
		for(int i=0; i<files.length; i++) {
			downloadables.addFile(files[i]);
		}
	}

}
