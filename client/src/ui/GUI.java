package ui;

import java.awt.BorderLayout;

import javax.swing.*;
import src.Client;

public class GUI extends JFrame {
	
	private Client client;
	private Connection connection;

	public GUI () {
		
		client = new Client();
		connection = new Connection();
		
		setSize( 530, 530 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "TCP Client" );
        setLocationRelativeTo( null );
        setLayout(new BorderLayout());
        
        add(connection, BorderLayout.NORTH);
	}
	
	public static void main(String[] args) {
		GUI i = new GUI();
		i.setVisible(true);
	}

}
