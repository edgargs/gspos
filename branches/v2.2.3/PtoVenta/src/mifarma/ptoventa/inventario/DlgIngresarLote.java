package mifarma.ptoventa.inventario;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.common.FarmaTableModel;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import java.sql.SQLException;

import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresarLote.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA 13.04.2010 Creación<br>
 * <br>
 *
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */

public class DlgIngresarLote extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgIngresarLote.class);
    
    Frame myParentFrame;
    public String codprodxx="";
    public FarmaTableModel tableModelListaMaestroX;
    
    private JPanelWhite pnlFondo = new JPanelWhite();
    private JTextFieldSanSerif txtfechvenc = new JTextFieldSanSerif();
    private JLabelFunction btnacep = new JLabelFunction();
    private JButtonLabel lblfechvenc = new JButtonLabel();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JTextFieldSanSerif txtnrolote = new JTextFieldSanSerif();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();

    public DlgIngresarLote() {
        this(null, "", false);        
    }

    public DlgIngresarLote(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(382, 133));
        this.getContentPane().setLayout( null );
        this.setTitle("Ingreso fecha vencimiento");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        pnlFondo.setBounds(new Rectangle(0, 0, 380, 110));
        pnlFondo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlFondo_keyPressed(e);
                    }
                });
        txtfechvenc.setBounds(new Rectangle(190, 45, 105, 20));
        txtfechvenc.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        txtfechvenc_keyReleased(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtfechvenc_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtfechvenc_keyPressed(e);
                    }
                });
        btnacep.setBounds(new Rectangle(195, 75, 100, 20));
        btnacep.setText("[ F11 ] Aceptar");
        btnacep.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        btnacep_keyPressed(e);
                    }
                });
        lblfechvenc.setText("Ingrese fecha de vencimiento:");
        lblfechvenc.setBounds(new Rectangle(10, 50, 175, 15));
        lblfechvenc.setForeground(new Color(255, 130, 14));
        lblfechvenc.setMnemonic('f');
        lblfechvenc.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        lblfechvenc_keyPressed(e);
                    }
                });
        lblfechvenc.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblfechvenc_actionPerformed(e);
                    }
                });
        jButtonLabel1.setText("Ingrese número de lote:");
        jButtonLabel1.setBounds(new Rectangle(40, 20, 140, 15));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('e');
        jButtonLabel1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });
        txtnrolote.setBounds(new Rectangle(190, 15, 105, 20));
        txtnrolote.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtnrolote_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtnrolote_keyReleased(e);
                    }
                });
        pnlFondo.add(jLabelOrange1, null);
        pnlFondo.add(txtnrolote, null);
        pnlFondo.add(jButtonLabel1, null);
        pnlFondo.add(lblfechvenc, null);
        pnlFondo.add(btnacep, null);
        pnlFondo.add(txtfechvenc, null);
        txtfechvenc.setDocument(new FarmaLengthText(10));
        txtnrolote.setDocument(new FarmaLengthText(15));
        jLabelOrange1.setText("dd/MM/yyyy");
        jLabelOrange1.setBounds(new Rectangle(300, 50, 70, 15));
        jLabelOrange1.setForeground(new Color(41, 141, 39));
        this.getContentPane().add(pnlFondo, null);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtnrolote);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void chkKeyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
      {
        cerrarVentana(false);
      }else if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)){
          aceptarFechaVenc();
      }
    }
    
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }
    
    private void aceptarFechaVenc(){
        if(validaCampoLote()){
            if(!existeLote()){
                if(validarFecha()){
                    try{
                        DBInventario.insertarLote(codprodxx.trim(),
                                                  txtnrolote.getText().trim(),
                                                  txtfechvenc.getText().trim());
                        FarmaUtility.aceptarTransaccion();
                        FarmaUtility.showMessage(this,"Lote ingresado exitosamente",null);
                        VariablesInventario.fechaVencLoteX=txtfechvenc.getText().trim();
                        VariablesInventario.nroLoteX=txtnrolote.getText().trim();
                        cerrarVentana(true);
                    }catch(SQLException e){
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(this,"Error en insertar lote, tal vez alguien ya lo ingreso: "+e.getMessage(),null);
                        log.error("",e);
                    }
                }
            }
        }
    }

    private void txtfechvenc_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtfechvenc,e);
    }

    private void txtfechvenc_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtfechvenc,e);
    }
    
    private boolean validarFecha(){
        boolean flag=true;
        if(!txtfechvenc.getText().trim().equals("") && !FarmaUtility.validaFecha(txtfechvenc.getText(),"dd/MM/yyyy")){
            flag=false;
            txtfechvenc.setText("");
            FarmaUtility.showMessage(this,"Si va a ingresar fecha de vencimiento que sea valida",txtfechvenc);
        }
        return flag;
    }


    private void pnlFondo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void lblfechvenc_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtfechvenc_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtnrolote);
        }else
            chkKeyPressed(e);
    }

    private void btnacep_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void lblfechvenc_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtfechvenc);
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtnrolote);
    }
    
    /**
       * Verifica si existe o no el lote ingresado
       * @author ASOSA
       * @since 14.04.2010
       * @return
       */
    private boolean existeLote(){
        int cant=tableModelListaMaestroX.getRowCount();
        boolean flag=false;
        for(int c=0;c<cant;c++){
            String numlote=FarmaUtility.getValueFieldArrayList(tableModelListaMaestroX.data,c,0).trim();
            log.debug("numlote: "+numlote);
            if(numlote.equalsIgnoreCase(txtnrolote.getText().trim()) && !numlote.equals("")) flag=true;
        }
        if(flag==true) FarmaUtility.showMessage(this,"Ya existe el lote ingresado",txtnrolote);
        return flag;
    }
    
    /**
       * Valida si el campo del lote no esta vacio
       * @author ASOSA
       * @since 14.04.2010
       * @return
       */
    private boolean validaCampoLote(){
        String numlote=txtnrolote.getText().trim();
        boolean flag=true;
        if(numlote.equals("")){
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese antes numero de lote",txtnrolote);
        }
        return flag;
    }

    private void txtnrolote_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtfechvenc);
        }else
            chkKeyPressed(e);
    }

    private void txtnrolote_keyReleased(KeyEvent e) {
        FarmaUtility.ponerMayuscula(e,txtnrolote);
    }
}
