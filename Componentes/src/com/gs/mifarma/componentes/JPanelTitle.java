package com.gs.mifarma.componentes;

import java.awt.LayoutManager;

import javax.swing.JPanel;

public class JPanelTitle extends JPanel {

	public JPanelTitle() {
		super();
		// TODO Auto-generated constructor stub
		initialize();
	}

	public JPanelTitle(boolean arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		initialize();
	}

	public JPanelTitle(LayoutManager arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		initialize();
	}

	public JPanelTitle(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(null);
		this.setSize(300, 200);
		this.setBackground(new java.awt.Color(255,130,14));
	}

}
