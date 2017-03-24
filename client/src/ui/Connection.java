package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

public class Connection extends JPanel implements ActionListener {
	
	public static final String CONNECT = "CONNECT";
	public static final String DISCONNECT = "DISCONNECT";
	
	private GUI gui;
	private JLabel statusLabel;
	private JTextField statusText;
	private JButton connect;
	
	public Connection (GUI gui) {
		this.gui = gui;
		setLayout( new BorderLayout() );
	    setBorder( new TitledBorder( "Connection" ) );
	    
	    statusLabel = new JLabel("Estado: ");
	    add(statusLabel, BorderLayout.WEST);
	    statusText = new JTextField();
	    statusText.setEditable(false);
	    add(statusText, BorderLayout.CENTER);
	    connect = new JButton("");
	    connectButton();
	    connect.addActionListener(this);
	    add(connect, BorderLayout.EAST);
	}
	
	public void changeStatus(String newState) {
		statusText.setText(newState);
	}
	
	public void connectButton() {
		connect.setText("Conectar");
		connect.setBackground(new Color(60, 200, 100));
	    connect.setActionCommand(CONNECT);
	    revalidate();
	    repaint();
	}
	
	public void disconnectButton() {
		connect.setText("Desconectar");
	    connect.setBackground(new Color(190, 60, 60));
	    connect.setActionCommand(DISCONNECT);
	    revalidate();
	    repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(CONNECT)) {
			gui.connect();
			disconnectButton();
		}
		if(e.getActionCommand().equals(DISCONNECT)) {
			gui.disconnect();
			connectButton();
		}
	}
}
