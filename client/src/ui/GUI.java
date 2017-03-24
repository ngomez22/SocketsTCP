package ui;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.Border;

import src.Client;

public class GUI extends JFrame {
	
	private Client client;
	private Connection connection;
	private Files files;
	private Controls controls;

	public GUI () {
		
		connection = new Connection(this);
		files = new Files(this);
		controls = new Controls(this);
		
		setSize( 530, 530 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "TCP Client" );
        setLocationRelativeTo( null );
        setLayout(new BorderLayout());
        
        add(connection, BorderLayout.NORTH);
        add(files, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);
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
	
	public void getDownloads() {
		
	}
	
	public void getDownloadables() {
		
	}
	
	public static void main(String[] args) {
		GUI i = new GUI();
		i.setVisible(true);
	}

}
