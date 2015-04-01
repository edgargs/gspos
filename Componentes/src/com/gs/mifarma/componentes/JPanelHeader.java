package com.gs.mifarma.componentes;

import java.awt.LayoutManager;

import javax.swing.JPanel;

public class JPanelHeader extends JPanel {

	public JPanelHeader() {
		super();
		// TODO Auto-generated constructor stub
		initialize();
	}

	public JPanelHeader(boolean arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		initialize();
	}

	public JPanelHeader(LayoutManager arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		initialize();
	}

	public JPanelHeader(LayoutManager arg0, boolean arg1) {
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
		this.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black,1));
		this.setBackground(new java.awt.Color(43,141,39));
	}

}
