package com.gs.mifarma.componentes;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class UpperCaseDocument extends PlainDocument {

	int maxLength;
	
	public UpperCaseDocument(int pMaxLength) {
		super();
		this.maxLength = pMaxLength;
	}
	
    public UpperCaseDocument() {
    	super();
    	this.maxLength = 250;
		// TODO Auto-generated constructor stub
	}
    
   public void setLengthText(int pMaxLength){
	   this.maxLength = pMaxLength;
   }

	public void insertString(int offs, String str, AttributeSet a) 
	          throws BadLocationException {
 	   if (getLength() < maxLength)
			try {
	          if (str == null) {
		      return;
	          }
	          char[] upper = str.toCharArray();
	          for (int i = 0; i < upper.length; i++) {
		      upper[i] = Character.toUpperCase(upper[i]);
	          }
	          super.insertString(offs, new String(upper), a);
	      
	          } catch (Exception e) {
				}
    }
}
