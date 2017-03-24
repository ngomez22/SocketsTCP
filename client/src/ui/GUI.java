package ui;

import java.awt.BorderLayout;

import javax.swing.*;
import src.Client;

public class GUI extends JFrame {
	
	private Client client;
	private Connection connection;

	public GUI () {
		
		connection = new Connection(this);
		
		setSize( 530, 530 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "TCP Client" );
        setLocationRelativeTo( null );
        setLayout(new BorderLayout());
        
        add(connection, BorderLayout.NORTH);
	}
	
	public void connect() {
		client = new Client();
		String status = client.startConnection();
		System.out.println(status);
		connection.changeStatus(status);
	}
	
	public void disconnect() {
		String status = client.endConnection();
		System.out.println(status);
		connection.changeStatus(status);
	}
	
	public static void main(String[] args) {
		GUI i = new GUI();
		i.setVisible(true);
	}

}
