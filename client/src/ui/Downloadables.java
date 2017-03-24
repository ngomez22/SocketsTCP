package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class Downloadables extends JPanel {
	
	private GUI gui;
	private JList list;
	
	public Downloadables (GUI gui) {
		this.gui = gui;
		setLayout( new BorderLayout() );
	    setBorder( new TitledBorder( "Downloadables" ) );
	    setBackground( Color.WHITE );
		
		JScrollPane scroll = new JScrollPane( );
        list = new JList( );
        scroll.setPreferredSize( new Dimension( 150, 0 ) );
        scroll.setViewportView( list );
        scroll.setVerticalScrollBarPolicy( javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setHorizontalScrollBarPolicy( javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        list.setModel( new DefaultListModel( ) );
        add(scroll, BorderLayout.CENTER);
	}
	
	public void addFile(String file) {
		DefaultListModel dlf = new DefaultListModel( );
        int i = 0;
        boolean alreadyExists = false;
        while( i < list.getModel( ).getSize( ) ) {
        	if(file.equalsIgnoreCase((String) list.getModel().getElementAt(i)))
        		alreadyExists = true;
            dlf.addElement( list.getModel( ).getElementAt( i ) );
            i++;
        }
        if(!alreadyExists) dlf.addElement( file );
        list.setModel( dlf );
	}
}
