package mifarma.common;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
* Copyright (c) 2008 MIFARMA S.A.C. <br>
* <br>
* Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
* Nombre de la Aplicación : FarmaPlainDocument.java<br>
* <br>
* Histórico de Creación/Modificación<br>
* FVELIZ      25.11.08      Creación<br>
* <br>
* @author Daniel Fernando Veliz La Rosa<br>
* @version 1.0<br>
*
*/

public class FarmaPlainDocument extends PlainDocument {
    private int maxlength   = 0;
    private int type        = 0;
    private int upper       = 0;
        
    public FarmaPlainDocument(int length, int type, int upper) {
        this.maxlength = length;
        this.type = type;
        this.upper = upper;
    }
    
    public FarmaPlainDocument(int length, int type) {
        this.maxlength = length;
        this.type = type;
    }
    
    public FarmaPlainDocument(int type) {
        this.maxlength = 250;
        this.type = type;
    }
    
    public FarmaPlainDocument() {
        this.maxlength = 250;
        this.type = 3;
    }
    
    public void setLengthText(int length){
        this.maxlength = length;
    }
        
    public int getLengthText(){
        return this.maxlength;
    }
        
    public void setDocumentText(int type){
        this.type = type;
    }
    
    public void setUpper(int upper){
        this.upper = upper;
    }
        
    public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {
            
        if(this.type == 1){
            if(getLength() >= this.maxlength)
            {super.remove(offs, getLength());}
            
            if(str == null)
            {return;}
            
            try{
                for (int i=0;i<str.length();i++)
                   // si no es digito, volvemos
                   if (!Character.isDigit(str.charAt(i)))
                      return;
                // Si todos son digit, insertamos el texto en el RTextField
                super.insertString(offs, str, a);    
            }catch(Exception e){
                e.printStackTrace();
            }
        }else if(this.type == 2){
            if(getLength() >= this.maxlength)
            {super.remove(offs, getLength());}
            
            if(str == null)
            {return;}
            
            try
            {
                for(int i=0;i<str.length();i++){
                    if (!(Character.isLetter(str.charAt(i)) 
                          || (Character.isSpaceChar(str.charAt(i))))) {
                        return;
                    }
                }   
                
                if(upper != 0){
                    char charupper[] = str.toCharArray();
                    for(int i = 0; i < charupper.length; i++)
                    {
                        charupper[i] = Character.toUpperCase(charupper[i]);
                    }
                    super.insertString(offs, new String(charupper), a);
                }else{
                    super.insertString(offs, str, a);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }     
        }else if(this.type == 3){
            if(getLength() >= this.maxlength)
            {super.remove(offs, getLength());}
            
            if(str == null)
            {return;}
            
            try
            {
            
                for(int i=0;i<str.length();i++){
                    if (!(Character.isLetter(str.charAt(i)))
                    && (!(Character.isDigit(str.charAt(i)))) ) {
                        return;
                    }
                    
                }
               
                if(upper != 0){
                    char charupper[] = str.toCharArray();
                    for(int i = 0; i < charupper.length; i++)
                    {
                        charupper[i] = Character.toUpperCase(charupper[i]);
                    }
                    super.insertString(offs, new String(charupper), a);
                }else{
                    super.insertString(offs, str, a);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }   
        }
    }
    
}
