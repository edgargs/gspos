package com.gs.mifarma.componentes;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;

public class JLabelFunction extends JLabel {

	public JLabelFunction() {
		super();
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelFunction(String arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelFunction(String arg0, int arg1) {
		super(arg0, arg1);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelFunction(Icon arg0) {
		super(arg0);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelFunction(Icon arg0, int arg1) {
		super(arg0, arg1);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public JLabelFunction(String arg0, Icon arg1, int arg2) {
		super(arg0, arg1, arg2);
		initialize();
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setFont(new java.awt.Font("Arial Black", java.awt.Font.PLAIN, 10));
        this.setText("[ F ] JLabelFunction");
        this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.setBackground(new java.awt.Color(212,208,200));
        this.setBounds(new java.awt.Rectangle(0,0,137,20));
        this.setForeground(java.awt.Color.black);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
