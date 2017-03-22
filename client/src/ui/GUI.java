package ui;

import javax.swing.*;
import src.Client;

public class GUI extends JFrame {
	
	private Client client;

	public GUI () {
		setSize( 530, 530 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "TCP Client" );
        setLocationRelativeTo( null );
	}
	
	public static void main(String[] args) {
		GUI i = new GUI();
		i.setVisible(true);
	}

}
