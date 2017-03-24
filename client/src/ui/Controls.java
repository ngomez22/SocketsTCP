package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Controls extends JPanel implements ActionListener {
	
	public static final String DOWNLOAD = "DOWNLOAD";
	public static final String STOP = "STOP";
	public static final String CONTINUE = "CONTINUE";
	
	private GUI gui;
	private JButton downloadBtn;
	private JButton stopBtn;
	private JButton playBtn;
	
	public Controls(GUI gui) {
		this.gui = gui;
		setLayout( new GridLayout(1,3) );
	    setBorder( new TitledBorder( "Controls" ) );
	    
	    downloadBtn = new JButton("Download");
	    downloadBtn.addActionListener(this);
	    downloadBtn.setActionCommand(DOWNLOAD);
	    stopBtn = new JButton("Stop");
	    stopBtn.addActionListener(this);
	    stopBtn.setActionCommand(STOP);
	    playBtn = new JButton("Continue");
	    playBtn.addActionListener(this);
	    playBtn.setActionCommand(CONTINUE);
	    
	    add(downloadBtn);
	    add(stopBtn);
	    add(playBtn);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
