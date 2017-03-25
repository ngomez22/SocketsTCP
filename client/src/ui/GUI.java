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
		files.setVisible(false);
		controls = new Controls(this);
		controls.setVisible(false);
		
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
		getDownloads();
		getDownloadables();
		files.setVisible(true);
	}
	
	public void disconnect() {
		String status = client.endConnection();
		System.out.println(status);
		connection.changeStatus(status);
	}
	
	public void getDownloadables() {
		String[] downloadables = client.getFiles();
		files.updateDownloadables(downloadables);
	}
	
	public void getDownloads() {
		String[] downloads = client.getDownloads();
		files.updateDownloads(downloads);
	}
	
	public static void main(String[] args) {
		GUI i = new GUI();
		i.setVisible(true);
	}

}
