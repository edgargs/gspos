package com.gs.mifarma.componentes;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class UpperCaseField extends JTextField {

	UpperCaseDocument document;
	
	public UpperCaseField() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpperCaseField(int columns) {
		super(columns);
		document = new UpperCaseDocument();
		// TODO Auto-generated constructor stub
	}

	public UpperCaseField(String text) {
		super(text);
		document = new UpperCaseDocument();
		// TODO Auto-generated constructor stub
	}

	public UpperCaseField(String text, int columns) {
		super(text, columns);
		document = new UpperCaseDocument();
		// TODO Auto-generated constructor stub
	}

	public UpperCaseField(Document doc, String text, int columns) {
		super(doc, text, columns);
		document = new UpperCaseDocument();
		// TODO Auto-generated constructor stub
	}

	protected Document createDefaultModel() {
		document =  new UpperCaseDocument();
	    return document;
   }
	
	public void setLengthText(int max){
		document.setLengthText(max);
	}
}
