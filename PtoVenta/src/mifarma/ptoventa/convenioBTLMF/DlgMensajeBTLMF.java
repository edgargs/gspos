package mifarma.ptoventa.convenioBTLMF;

import com.gs.mifarma.componentes.JMessageAlert;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import mifarma.common.FarmaSecurity;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.impresoras.reference.ImpresoraTicket;
import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgMensajeBTLMF.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * FRAMIREZ      12.11.2011  Creación<br>
 * <br>
 * @author Fredy Ramirez C.<br>
 * @version 1.0<br>
 *
 */

public class DlgMensajeBTLMF extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMensajeBTLMF.class);

    FarmaSecurity vSecurityLogin;
    int parametro = -1;
    JLabel lblParametro;

    JTextField txtUsuario = new JTextField();
    JPasswordField txtClave = new JPasswordField();
    JButton btnAceptar = new JButton();
    JLabel lblClave = new JLabel();

    JPanel pnlArriba = new JPanel();
    JPanel pnlIzquierda = new JPanel();
    JPanel pnlDerecha = new JPanel();
    JPanel pnlAbajo = new JPanel();
    JEditorPane jEditorPaneIzq = new JEditorPane();
    JEditorPane jEditorPaneDec = new JEditorPane();
    JEditorPane jEditorPaneAba = new JEditorPane();
    String htmlIzquierdo = "", htmlDerecho = "", htmlAbajo = "";

    Dimension pantalla = null;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMensajeBTLMF() {
        this(null, "", false);
    }

    Frame myParentFrame;

    public DlgMensajeBTLMF(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setFocusable(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

        });
        //this.getContentPane().setLayout(new GridLayout(3, 2,10,10));
        this.getContentPane().setLayout(new GridBagLayout());
        //public GridLayout(int rows, int columns)
        //public GridLayout(int rows, int columns, int horizontalGap, int verticalGap)
        GridBagConstraints constraints = new GridBagConstraints();
        
        pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        pnlArriba.setBackground(new Color(255, 181, 255));
        //pnlArriba.setLayout(new GridLayout(1, 0));
        pnlArriba.setLayout(new GridBagLayout());
        pnlArriba.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlArriba_keyPressed(e);
            }
        });
        jEditorPaneIzq.setEditable(false);
        jEditorPaneIzq.setContentType("text/html");
        //Panel Izquierdo
        pnlIzquierda.setLayout(new BorderLayout());

        jEditorPaneIzq.setText(htmlIzquierdo);
        jEditorPaneIzq.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jEditorPaneIzq_keyPressed(e);
            }
        });
        jEditorPaneIzq.setLocation(0, 0);

        jEditorPaneIzq.setBorder(BorderFactory.createLineBorder(Color.RED, 9));
        //pnlIzquierda.add(jEditorPaneIzq,BorderLayout.CENTER);
        jEditorPaneDec.setContentType("text/html");
        jEditorPaneDec.setText(htmlDerecho);
        jEditorPaneDec.setEditable(false);
        jEditorPaneDec.setBorder(BorderFactory.createLineBorder(Color.BLUE, 9));

        jEditorPaneAba.setContentType("text/html");
        jEditorPaneAba.setText(htmlAbajo);
        jEditorPaneAba.setEditable(false);
        jEditorPaneAba.setBorder(BorderFactory.createLineBorder(Color.GREEN, 9));

        jEditorPaneAba.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jEditorPaneAba_keyPressed(e);
            }
        });
        
        
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.gridwidth = 1; // El área de texto ocupa dos columnas.
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        //pnlArriba.add(pnlIzquierda,constraints);
        
        pnlDerecha.setLayout(new BorderLayout());
        // pnlIzquierda.setBackground(Color.RED);
        pnlIzquierda.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlIzquierda_keyPressed(e);
            }
        });
        pnlDerecha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlDerecha_keyPressed(e);
            }
        });
        jEditorPaneDec.setLayout(new GridLayout());
        jEditorPaneDec.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jEditorPaneDec_keyPressed(e);
            }
        });
        //pnlDerecha.add(jEditorPaneDec,BorderLayout.CENTER);
        
        
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.gridwidth = 1; // El área de texto ocupa dos columnas.
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        //pnlArriba.add(pnlDerecha,constraints);
        
        //Panel Abajo
        pnlAbajo.setLayout(new GridBagLayout());
        pnlAbajo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlAbajo_keyPressed(e);
            }
        });
        
        //pnlAbajo.add(jEditorPaneAba);
        
        
        
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.gridwidth = 1; // El área de texto ocupa dos columnas.
        constraints.gridheight = 10; // El área de texto ocupa 2 filas.
        constraints.weightx = 1.0; // La fila 0 debe estirarse, le ponemos un 1.0
        constraints.fill = GridBagConstraints.BOTH;
        //this.getContentPane().add(pnlArriba,constraints);
        this.getContentPane().add(jEditorPaneIzq,constraints);
        
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.gridwidth = 1; // El área de texto ocupa dos columnas.
        constraints.gridheight = 10; // El área de texto ocupa 2 filas.
        constraints.weightx = 1.0; // La fila 0 debe estirarse, le ponemos un 1.0
        constraints.fill = GridBagConstraints.BOTH;
        //this.getContentPane().add(pnlArriba,constraints);        
        this.getContentPane().add(jEditorPaneDec,constraints);
        
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 10; // El área de texto empieza en la fila cero
        constraints.gridwidth = 2; // El área de texto ocupa dos columnas.
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        constraints.weighty = 1.0; // La fila 0 debe estirarse, le ponemos un 1.0
        constraints.weightx = 0.0; // La fila 0 debe estirarse, le ponemos un 1.0
        constraints.fill = GridBagConstraints.BOTH;
        //this.getContentPane().add(pnlAbajo,constraints);
        this.getContentPane().add(jEditorPaneAba,constraints);
        
        this.setBounds(new Rectangle(0, 0, pantalla.width, pantalla.height));
        this.setSize(pantalla);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setModal(true);

    }
    
    // **************************************************************************
    // Initialize
    // **************************************************************************
    public static void main(String[] args) {
        
        DlgMensajeBTLMF v = new DlgMensajeBTLMF();
        v.setVisible(true);
    }
    
    // **************************************************************************
    // Initialize
    // **************************************************************************

    private void initialize() {
        // TODO Auto-generated method stub
        cargaMensajes();
        jEditorPaneDec.setText(htmlDerecho);
        jEditorPaneAba.setText(htmlAbajo);
        jEditorPaneIzq.setText(htmlIzquierdo);
    }
    // **************************************************************************
    // Open y Close
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        jEditorPaneIzq.requestFocus();
    }

    private void this_windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // KEY PRESSED
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        log.debug("tecla  +  " + e.getKeyChar());
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            cerrarVentana(true);
        }
    }

    private void pnlArriba_keyPressed(KeyEvent e) {
        log.debug("tecla Arriba +  " + e.getKeyChar());
        chkKeyPressed(e);
    }

    private void pnlAbajo_keyPressed(KeyEvent e) {
        log.debug("tecla Abajo +  " + e.getKeyChar());
        chkKeyPressed(e);
    }

    private void pnlIzquierda_keyPressed(KeyEvent e) {
        log.debug("tecla izq +  " + e.getKeyChar());
        chkKeyPressed(e);
    }

    private void pnlDerecha_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jEditorPaneIzq_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jEditorPaneDec_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jEditorPaneAba_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }


    // **************************************************************************
    // LOGICA DE NEGOCIO
    // **************************************************************************

    public void cargaMensajes() {

        String nroResolucion = pantalla.width + "x" + pantalla.height;

        String vNombreBenif = VariablesConvenioBTLMF.vNombre + " " + VariablesConvenioBTLMF.vApellidoPat;

        htmlAbajo =
                UtilityConvenioBTLMF.obtieneDocVerificacion(VariablesConvenioBTLMF.vCodConvenio, ConstantsConvenioBTLMF.FLG_DOC_SOLUCION,
                                                            vNombreBenif, this, null);
        htmlDerecho =
                UtilityConvenioBTLMF.obtieneDocVerificacion(VariablesConvenioBTLMF.vCodConvenio, ConstantsConvenioBTLMF.FLG_DOC_VERIFICACION,
                                                            vNombreBenif, this, null);
        htmlIzquierdo =
                UtilityConvenioBTLMF.obtieneDocVerificacion(VariablesConvenioBTLMF.vCodConvenio, ConstantsConvenioBTLMF.FLG_DOC_RETENCION,
                                                            vNombreBenif, this, null);


        Map pantallaAbajo =
            UtilityConvenioBTLMF.obtienePantallaMensaje(nroResolucion.trim(), ConstantsConvenioBTLMF.PANTALLA_POS_ABA,
                                                        this, null);

        if (pantallaAbajo != null) {
            String margenPixelA = (String)pantallaAbajo.get(ConstantsConvenioBTLMF.COL_FACTOR_ANCHO);
            String margenPixelAH = (String)pantallaAbajo.get(ConstantsConvenioBTLMF.COL_FACTOR_ALTO);
            htmlAbajo =
                    htmlAbajo.replaceAll(ConstantsConvenioBTLMF.PANTALLA_ABA_PIXEL_ANCHO, (pantalla.width - Integer.parseInt(margenPixelA)) +
                                         "px");
            htmlAbajo =
                    htmlAbajo.replaceAll(ConstantsConvenioBTLMF.PANTALLA_ABA_PIXEL_ALTO, (pantalla.height - Integer.parseInt(margenPixelAH)) +
                                         "px");
        }

        Map pantallaDerecha =
            UtilityConvenioBTLMF.obtienePantallaMensaje(nroResolucion.trim(), ConstantsConvenioBTLMF.PANTALLA_POS_DER,
                                                        this, null);
        if (pantallaDerecha != null) {
            String margenPixelD = (String)pantallaDerecha.get(ConstantsConvenioBTLMF.COL_FACTOR_ANCHO);
            String margenPixelDH = (String)pantallaDerecha.get(ConstantsConvenioBTLMF.COL_FACTOR_ALTO);
            htmlDerecho =
                    htmlDerecho.replaceAll(ConstantsConvenioBTLMF.PANTALLA_DER_PIXEL_ANCHO, (pantalla.width - Integer.parseInt(margenPixelD)) +
                                           "px");
            htmlDerecho =
                    htmlDerecho.replaceAll(ConstantsConvenioBTLMF.PANTALLA_DER_PIXEL_ALTO, (pantalla.height - Integer.parseInt(margenPixelDH)) +
                                           "px");
        }

        Map pantallaIzquierda =
            UtilityConvenioBTLMF.obtienePantallaMensaje(nroResolucion.trim(), ConstantsConvenioBTLMF.PANTALLA_POS_IZQ,
                                                        this, null);

        if (pantallaIzquierda != null) {

            String margenPixelI = (String)pantallaIzquierda.get(ConstantsConvenioBTLMF.COL_FACTOR_ANCHO);
            String margenPixelIH = (String)pantallaIzquierda.get(ConstantsConvenioBTLMF.COL_FACTOR_ALTO);
            htmlIzquierdo =
                    htmlIzquierdo.replaceAll(ConstantsConvenioBTLMF.PANTALLA_IZQ_PIXEL_ANCHO, (pantalla.width - Integer.parseInt(margenPixelI)) +
                                             "px");
            htmlIzquierdo =
                    htmlIzquierdo.replaceAll(ConstantsConvenioBTLMF.PANTALLA_IZQ_PIXEL_ALTO, (pantalla.height - Integer.parseInt(margenPixelIH)) +
                                             "px");
        }


    }


}
