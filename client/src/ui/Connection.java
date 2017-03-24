package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

public class Connection extends JPanel implements ActionListener {
	
	private JLabel statusLabel;
	private JTextField statusText;
	private JButton connect;
	
	public Connection () {
		setLayout( new BorderLayout() );
	    setBorder( new TitledBorder( "Connection" ) );
	    setBackground( Color.WHITE );
	    
	    statusLabel = new JLabel("Estado: ");
	    add(statusLabel, BorderLayout.WEST);
	    statusText = new JTextField();
	    statusText.setEditable(false);
	    add(statusText, BorderLayout.CENTER);
	    connect = new JButton("Conectar");
	    connect.setBackground(new Color(60, 200, 100));
	    add(connect, BorderLayout.EAST);
	}
	
	public void changeEstado(String newState) {
		statusText.setText(newState);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
