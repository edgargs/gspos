package com.gs.mifarma.componentes;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class JTextFieldSanSerif extends UpperCaseField {

	public JTextFieldSanSerif() {
		super();
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JTextFieldSanSerif(int arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JTextFieldSanSerif(String arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JTextFieldSanSerif(String arg0, int arg1) {
		super(arg0, arg1);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JTextFieldSanSerif(Document arg0, String arg1, int arg2) {
		super(arg0, arg1, arg2);
		initialize();
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 11));
        this.setForeground(java.awt.Color.black);			
	}

}
