package mifarma.ptoventa.puntos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import farmapuntos.bean.TarjetaBean;

import farmapuntos.util.FarmaPuntosConstante;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.hilos.SubProcesosConvenios;
import mifarma.ptoventa.puntos.reference.ConstantsPuntos;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import mifarma.ptoventa.ventas.DlgIngresoCantidad;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgComplementarios1.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ       08.04.2008   Creacion <br>
 * <br>
 * @author  Jorge Cortez Alvarez<br>
 * @version 1.0<br>
 *
 */

public class DlgValidaDocumento extends JDialog {
    /* ************************************************************************** */
    /*                           DECLARACION PROPIEDADES                          */
    /* ************************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgValidaDocumento.class);

    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JPanel pnlIngresarProductos = new JPanel();
    private JTextField txtNroDocumento = new JTextField();
    private JButton btnNroDocumento = new JButton();
    private JLabelFunction lblEnter = new JLabelFunction();

    private String textNroDocumento = "";
    private String nroDocumento = "";
    private boolean isLectoraLaser;
    private boolean isEnter;
    private boolean isCodigoBarra;
    private long tiempoTeclaInicial;
    private long tiempoTeclaFinal;

    /* ************************************************************************** */
    /*                              CONSTRUCTORES                                 */
    /* ************************************************************************** */

    public DlgValidaDocumento() {
        this(null, "", false);
    }

    public DlgValidaDocumento(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /* ************************************************************************** */
    /*                              METODO jbInit                                 */
    /* ************************************************************************** */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(310, 101));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingrese DNI o CE Nuevamente...");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(623, 439));
        jContentPane.setForeground(Color.white);
        lblF1.setText("[ Enter ] Aceptar");
        lblF1.setBounds(new Rectangle(150, 50, 150, 20));
        pnlIngresarProductos.setBounds(new Rectangle(10, 10, 290, 35));
        pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlIngresarProductos.setBackground(new Color(43, 141, 39));
        pnlIngresarProductos.setLayout(null);
        pnlIngresarProductos.setForeground(Color.orange);
        txtNroDocumento.setBounds(new Rectangle(95, 5, 180, 20));
        txtNroDocumento.setFont(new Font("SansSerif", 1, 15));
        txtNroDocumento.setForeground(new Color(32, 105, 29));
        txtNroDocumento.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);

            }

            public void keyReleased(KeyEvent e) {
                
            }

            public void keyTyped(KeyEvent e) {
                
            }
        });
        btnNroDocumento.setText("Escanee DNI: ");
        btnNroDocumento.setBounds(new Rectangle(10, 5, 80, 20));
        btnNroDocumento.setMnemonic('i');
        btnNroDocumento.setFont(new Font("SansSerif", 1, 11));
        btnNroDocumento.setDefaultCapable(false);
        btnNroDocumento.setRequestFocusEnabled(false);
        btnNroDocumento.setBackground(new Color(50, 162, 65));
        btnNroDocumento.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnNroDocumento.setFocusPainted(false);
        btnNroDocumento.setHorizontalAlignment(SwingConstants.LEFT);
        btnNroDocumento.setContentAreaFilled(false);
        btnNroDocumento.setBorderPainted(false);
        btnNroDocumento.setForeground(Color.white);
        btnNroDocumento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProducto_actionPerformed(e);
            }
        });
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(10, 290, 145, 20));
        lblEnter.setVisible(false);

        pnlIngresarProductos.add(txtNroDocumento, null);
        pnlIngresarProductos.add(btnNroDocumento, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(pnlIngresarProductos, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //this.getContentPane().add(jContentPane, null);
        txtNroDocumento.setEditable(false);
        txtNroDocumento.setBackground(SystemColor.window);
    }

    /* ************************************************************************** */
    /*                            METODO initialize                               */
    /* ************************************************************************** */

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************** */
    /*                            METODOS DE EVENTOS                              */
    /* ************************************************************************** */

    private void this_windowOpened(WindowEvent e) {
        if(nroDocumento.trim().length()!=8){
            txtNroDocumento.setEditable(true);
            btnNroDocumento.setText("Ingrese CE: ");
        }
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNroDocumento);
        FarmaUtility.showMessage(this, "PROGRAMA DE PUNTOS: TARJETA ADICIONAL:\nSOLICITE DOCUMENTO DE IDENTIDAD E INGRESELO MEDIANTE EL LECTOR DE BARRAS.", txtNroDocumento);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Marco Fajardo: Cambio realizado el 21/04/09 - evitar le ejecucion de 2 teclas a la vez
    // **************************************************************************
    
    private boolean isNumero(char ca) {
        int numero  = 0;
        try {
            numero = Integer.parseInt(ca + "");
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    
    private void txtProducto_keyPressed(KeyEvent e) {

        if(e.getKeyCode() != KeyEvent.VK_ENTER){
            textNroDocumento = textNroDocumento + e.getKeyChar();
        }
        if(isNumero(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_ENTER ){
            if (isLectoraLaser) {
                isLectoraLaser = false;
            }
            isEnter = false;
            isLectoraLaser = false;
            if (tiempoTeclaInicial == 0){
                tiempoTeclaInicial = System.currentTimeMillis();
            }
            isCodigoBarra = true;
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                int maxTiempoLectora = UtilityPuntos.obtieneTiempoMaximoLectora();
                isLectoraLaser = false;
                tiempoTeclaFinal = System.currentTimeMillis();
                log.info("Tiem 2 " + (tiempoTeclaInicial));
                log.info("Tiem 1 " + (tiempoTeclaFinal));
                log.info("Tiempo de ingreso y ENTER " + (tiempoTeclaFinal - tiempoTeclaInicial));
                boolean validaFinal = true;
                for(int k=0;k<textNroDocumento.length();k++){
                    validaFinal = validaFinal && isNumero(textNroDocumento.toCharArray()[k]);
                }
                if (((tiempoTeclaFinal - tiempoTeclaInicial) <= maxTiempoLectora && textNroDocumento.length() > 0 && validaFinal) || txtNroDocumento.isEditable()) {
                    isLectoraLaser = true;
                    tiempoTeclaInicial = 0;
                    log.info("ES CODIGO DE BARRA");
                    isCodigoBarra = true;
                    isEnter = true;
                    txtNroDocumento.setText(textNroDocumento);
                    validarDato(e);
                        //txtProducto_keyPressed2(e);
                } else {
                    isLectoraLaser = false;
                    tiempoTeclaInicial = 0;
                    log.info("FLUJO NORMAL");
                    isCodigoBarra = false;
                    //txtProducto.setText("");
                    chkKeyPressed(e);
                    textNroDocumento = "";
                    //txtProducto_keyPressed2(e);
                }
                //FarmaUtility.moveFocus(txtTarjeta);
            }
        }else{
            isCodigoBarra = false;
            log.info("FLUJO NORMAL");
            //txtProducto_keyPressed2(e);
            //txtProducto.setText("");
            chkKeyPressed(e);
            textNroDocumento = "";
        }
    }
    
    
    private void validarDato(KeyEvent e){
        limpiaCadenaAlfanumerica(txtNroDocumento);
        textNroDocumento = txtNroDocumento.getText().trim();
        try {
            e.consume();
            if (UtilityFidelizacion.validarDocIdentidad(txtNroDocumento.getText().trim())) {
                if(!nroDocumento.equalsIgnoreCase(textNroDocumento.trim())){
                    FarmaUtility.showMessage(this, "Documentos ingresados no coinciden.", txtNroDocumento);
                    log.info("VALIDA TARJETA ADICIONAL -->"+nroDocumento+" - "+textNroDocumento);
                    txtNroDocumento.setText("");
                    textNroDocumento = "";
                }else{
                    FarmaUtility.showMessage(this, "Documento Validado.", txtNroDocumento);
                    cerrarVentana(true);
                }
            }else{
                FarmaUtility.showMessage(this, "Nro Documento invalido.", txtNroDocumento);
                txtNroDocumento.setText("");
                textNroDocumento = "";
            }
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al leer documento.\n" + sql, txtNroDocumento);
            textNroDocumento = "";
            txtNroDocumento.setText("");
        }
    }
    
    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNroDocumento);
    }


    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    public void setNroDocumento(String nroDocumento){
        this.nroDocumento = nroDocumento;
    }
    
    public String getTextNroDocumento(){
        return this.textNroDocumento;
    }
    
    
}
