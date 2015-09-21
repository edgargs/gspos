package com.gs.mifarma.componentes;

import java.awt.Insets;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class JButtonFunction extends JButton {

	public JButtonFunction() {
		super();
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JButtonFunction(String arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JButtonFunction(Action arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JButtonFunction(Icon arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JButtonFunction(String arg0, Icon arg1) {
		super(arg0, arg1);
		initialize();
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setFont(new java.awt.Font("Arial Black", java.awt.Font.BOLD, 10));
		this.setDefaultCapable(false);
		this.setFocusPainted(false);
		this.setRequestFocusEnabled(false);
		this.setMargin(new Insets(1, 5, 2, 1));
	}

}
