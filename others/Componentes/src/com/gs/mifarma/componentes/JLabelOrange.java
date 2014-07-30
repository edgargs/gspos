package com.gs.mifarma.componentes;

import javax.swing.Icon;
import javax.swing.JLabel;

public class JLabelOrange extends JLabel {

	public JLabelOrange() {
		super();
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelOrange(String arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelOrange(String arg0, int arg1) {
		super(arg0, arg1);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelOrange(Icon arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelOrange(Icon arg0, int arg1) {
		super(arg0, arg1);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelOrange(String arg0, Icon arg1, int arg2) {
		super(arg0, arg1, arg2);
		initialize();
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11));
        this.setForeground(new java.awt.Color(255,130,14));
			
	}

}
