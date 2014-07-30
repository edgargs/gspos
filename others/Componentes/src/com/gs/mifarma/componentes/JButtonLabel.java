package com.gs.mifarma.componentes;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

public class JButtonLabel extends JButton {

	public JButtonLabel() {
		super();
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JButtonLabel(String arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JButtonLabel(Action arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JButtonLabel(Icon arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JButtonLabel(String arg0, Icon arg1) {
		super(arg0, arg1);
		initialize();
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11));
        this.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        this.setForeground(java.awt.Color.white);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setDefaultCapable(false);
		this.setFocusPainted(false);
		this.setRequestFocusEnabled(false);
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	}

}
