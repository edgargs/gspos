package com.gs.mifarma.componentes;

import javax.swing.Icon;
import javax.swing.JLabel;

public class JLabelWhite extends JLabel {

	public JLabelWhite() {
		super();
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelWhite(String arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelWhite(String arg0, int arg1) {
		super(arg0, arg1);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelWhite(Icon arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelWhite(Icon arg0, int arg1) {
		super(arg0, arg1);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelWhite(String arg0, Icon arg1, int arg2) {
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
        this.setForeground(java.awt.Color.white);
			
	}

}
