package mifarma.ptoventa.retiro;

import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.awt.event.WindowEvent;

import mifarma.common.FarmaUtility;
import mifarma.ptoventa.retiro.reference.DBRetiro;
import mifarma.ptoventa.retiro.reference.VariablesRetiro;
import java.awt.Rectangle;

import javax.swing.JDialog;

import mifarma.common.FarmaVariables;

/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgProcRetiros <br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ASOSA 07.12.2009 Creaci�n<br>
 * <br>
 * @author Alfredo Sosa Dord�n<br>
 * @version 1.0<br>
 * 
 */

public class DlgProcRetiros extends JDialog {
    private JPanelWhite jPanelWhite1 = new JPanelWhite();

    /* ********************************************************************** */
          /*                        CONSTRUCTORES                                   */
          /* ********************************************************************** */

    public DlgProcRetiros() {
        this(null, "", false);
    }

    public DlgProcRetiros(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ************************************************************************ */
          /*                                  METODO jbInit                           */
          /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(235, 103));
        this.getContentPane().setLayout( null );
        this.setTitle("Procesando Informaci�n ...");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jPanelWhite1.setBounds(new Rectangle(0, 0, 230, 80));
        this.getContentPane().add(jPanelWhite1, null);
        FarmaUtility.centrarVentana(this);
    }

    /* ************************************************************************ */
      /*                            METODOS DE EVENTOS                            */
      /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e)  {
       procesarRetiros();
    }
    
    public void procesarRetiros(){
        try{
            DBRetiro.procesarRetiros();
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this,"Proceso finalizado exitosamente",null);
            cerrarVentana(true);            
        }catch(SQLException f)   {
            f.printStackTrace();
            FarmaUtility.showMessage(this,"ERROR en el proceso de retiro de VB: \n"+f.getMessage(),null);
            FarmaUtility.liberarTransaccion();
            cerrarVentana(false);
        }
    }
    
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */
    
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }
    
}
