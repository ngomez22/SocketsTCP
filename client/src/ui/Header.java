package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Header extends JPanel {
	
	public Header () {
		setLayout( new BorderLayout( ) );
	    setBorder( new TitledBorder( "Connection" ) );
	    setBackground( Color.WHITE );
	}
}
