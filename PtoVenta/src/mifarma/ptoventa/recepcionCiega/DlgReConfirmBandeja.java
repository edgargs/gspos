package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import oracle.jdeveloper.layout.XYConstraints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgDatosTransportista_02.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA 05.04.2010 Creación<br>
 * <br>
 *
 * @author ALFREDO SOSA DORDAN<br>
 * @version 1.0<br>
 *
 * RECEP_BULTOS
 */
public class DlgReConfirmBandeja extends JDialog {
    /* ********************************************************************** */
    /* DECLARACION PROPIEDADES */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgReConfirmBandeja.class);
    private JPanel jContentPane = new JPanel();
    private String pMotivo = "",pCodMotivo = "";
    Frame myParentFrame;
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblLimpiar = new JLabelFunction();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabelWhite lblCodPromocion = new JLabelWhite();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtBandeja = new JTextField();
    private JPanel jPanel1 = new JPanel();
    private JLabel jLabel2 = new JLabel();
    private JTextField txtReconfirmacion = new JTextField();
    public String vValorValidar  = "";
    public boolean isValidoConfirmacion  = false;

    /* ********************************************************************** */
    /* CONSTRUCTORES */
    /* ********************************************************************** */

    public DlgReConfirmBandeja() {
        this(null, "", false);
    }

    public DlgReConfirmBandeja(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();

        } catch (Exception e) {
            log.error("", e);
        }

    }

    /* ********************************************************************** */
    /* METODO JBINIT */
    /* ********************************************************************** */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(417, 153));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Confirmaci\u00f3n de N\u00famero Bandeja/Bulto");
        this.setFont(new Font("SansSerif", 0, 11));
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setFont(new Font("SansSerif", 0, 11));
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(470, 236));
        pnlTitle1.setBounds(new Rectangle(5, 10, 400, 110));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setLayout(null);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        /*
        txtPrecintos.setLengthText(6);
        txtPrecintos.setBounds(new Rectangle(155, 130, 135, 20));
        txtPrecintos.addKeyListener(new KeyAdapter() {

                    public void keyTyped(KeyEvent e) {
                        txtPrecintos_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtPrecintos_keyPressed(e);
                    }
                });
        */

        // aampuero 14.04.2014


        lblEsc.setText("[ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(305, 85, 85, 20));
        lblF11.setText("[F11] Confirmar");
        lblF11.setBounds(new Rectangle(10, 85, 95, 20));
        
        lblLimpiar.setText("[F1] Limpiar");
        lblLimpiar.setBounds(new Rectangle(195, 85, 95, 20));
        lblCodPromocion.setText("[CodPromocion]");
        lblCodPromocion.setForeground(new Color(255, 130, 14));
        lblCodPromocion.setRequestFocusEnabled(false);
        lblCodPromocion.setFocusable(false);
        lblCodPromocion.setVisible(false);
        lblCodPromocion.setBounds(new Rectangle(0, 10, 105, 15));
        //--Se cambio el tamaño de digitos
        //  12.09.2008 Dubilluz

        //INI ASOSA - 21/07/2014


        //pnlTitle1.add(jScrollPane1, new XYConstraints(115, 15, 195, 20));
        //FIN ASOSA - 25/07/2014


        //INI ASOSA - 21/07/2014
        //FIN ASOSA - 25/07/2014
        jLabel1.setText("<html>N\u00ba Bandeja / Bulto:</html>");
        jLabel1.setBounds(new Rectangle(10, 5, 95, 35));
        jLabel1.setBackground(SystemColor.window);
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        txtBandeja.setBounds(new Rectangle(120, 10, 250, 20));
        txtBandeja.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtHojaResumen_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtHojaResumen_keyTyped(e);
                }
            });
        jPanel1.setBounds(new Rectangle(10, 5, 380, 75));
        jPanel1.setLayout(null);
        jPanel1.setBackground(new Color(0, 99, 0));
        jLabel2.setText("Repetir el N\u00ba");
        jLabel2.setBounds(new Rectangle(10, 40, 95, 20));
        jLabel2.setForeground(SystemColor.window);
        jLabel2.setFont(new Font("Tahoma", 1, 11));
        txtReconfirmacion.setBounds(new Rectangle(120, 45, 250, 20));
        txtReconfirmacion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtReconfirmacion_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtReconfirmacion_keyTyped(e);
                }
            });
        jPanel1.add(txtReconfirmacion, null);
        jPanel1.add(jLabel2, null);
        jPanel1.add(txtBandeja, null);
        jPanel1.add(jLabel1, null);
        pnlTitle1.add(jPanel1, null);
        pnlTitle1.add(lblCodPromocion, new XYConstraints(0, 10, 105, 15));
        /*
        pnlTitle1.add(txtPrecintos, new XYConstraints(115, 70, 195, 20));
        */
        // AAMPUERO 14.04.2014
        pnlTitle1.add(lblEsc, null);
        pnlTitle1.add(lblF11, null);
        pnlTitle1.add(lblLimpiar,null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /* ********************************************************************** */
    /* METODO INITIALIZE */
    /* ********************************************************************** */

    private void initialize() {
        
    }

    /* ********************************************************************** */
    /* METODO DE INICIALIZACION */
    /* ********************************************************************** */

    /* ********************************************************************** */
    /* METODOS DE EVENTOS */
    /* ********************************************************************** */

    
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtBandeja);
        
    }


    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ********************************************************************** */
    /* METODOS AUXILIARES */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F1) { 
            txtBandeja.setText("");
            txtReconfirmacion.setText("");
            FarmaUtility.moveFocus(txtBandeja);
        } 
        else
        if (e.getKeyCode() == KeyEvent.VK_F11) { 
            
            txtBandeja.setText(FarmaUtility.completeWithSymbol(txtBandeja.getText(), 10, "0", "I"));
            txtReconfirmacion.setText(FarmaUtility.completeWithSymbol(txtReconfirmacion.getText(), 10, "0", "I"));            
            
            if(vValorValidar.trim().equalsIgnoreCase(txtBandeja.getText().trim())&&
               vValorValidar.trim().equalsIgnoreCase(txtReconfirmacion.getText().trim())){
            setIsValidoConfirmacion(true);
                   cerrarVentana(true);
               }
            else  {
            setIsValidoConfirmacion(false);
                FarmaUtility.showMessage(this,"Los números no son iguales al ingresado anteriormente", txtBandeja);
            }
            
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ********************************************************************** */
    /* METODOS DE LOGICA DE NEGOCIO */
    /* *********************************************************************** */
    

    public void setPMotivo(String pMotivo) {
        this.pMotivo = pMotivo;
    }

    public String getPMotivo() {
        return pMotivo;
    }

    public void setPCodMotivo(String pCodMotivo) {
        this.pCodMotivo = pCodMotivo;
    }

    public String getPCodMotivo() {
        return pCodMotivo;
    }

    private void txtHojaResumen_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
            txtBandeja.setText(FarmaUtility.completeWithSymbol(txtBandeja.getText(), 10, "0", "I"));
            if(txtBandeja.getText().trim().length()>0){
            if(!vValorValidar.trim().equalsIgnoreCase(txtBandeja.getText()))    
                FarmaUtility.showMessage(this, "El número de hoja resumen no es el mismo antes ingresado.\n" +
                    "Por favor de verificar.", txtBandeja);
            else
                FarmaUtility.moveFocus(txtReconfirmacion);
            }
            else
              FarmaUtility.moveFocus(txtReconfirmacion);
        } 
        else 
        chkKeyPressed(e);
    }

    public void setVValorValidar(String vValorValidar) {
        this.vValorValidar = vValorValidar;
    }

    public String getVValorValidar() {
        return vValorValidar;
    }

    private void txtReconfirmacion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtReconfirmacion.setText(FarmaUtility.completeWithSymbol(txtReconfirmacion.getText(), 10, "0", "I"));
            if(txtBandeja.getText().trim().length()>0){
            if(!txtBandeja.getText().trim().equalsIgnoreCase(txtReconfirmacion.getText()))
                FarmaUtility.showMessage(this, "El número de hoja resumen no es el mismo antes ingresado.\n" +
                    "Por favor de verificar.", txtReconfirmacion);
            else
                FarmaUtility.moveFocus(txtBandeja);
            }
            else
               FarmaUtility.moveFocus(txtBandeja);
        } 
        else 
        chkKeyPressed(e);
    }

    public void setIsValidoConfirmacion(boolean isValidoConfirmacion) {
        this.isValidoConfirmacion = isValidoConfirmacion;
    }

    public boolean isIsValidoConfirmacion() {
        return isValidoConfirmacion;
    }

    private void txtHojaResumen_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtBandeja, e);
    }

    private void txtReconfirmacion_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtReconfirmacion, e);
    }
}
