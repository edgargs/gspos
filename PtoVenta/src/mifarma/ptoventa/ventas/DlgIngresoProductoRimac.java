package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresoProductoRimac.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      31.12.2014   Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @type RIMAC
 * @version 1.0<br>
 *
 */

public class DlgIngresoProductoRimac extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoProductoRimac.class);

    /** Objeto Frame de la Aplicación */
    Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel pnlDetalleProducto = new JPanel();
    private XYLayout xYLayout1 = new XYLayout();
    private JTextFieldSanSerif txtCantMeses = new JTextFieldSanSerif();
    private JLabel lblDescripcionT = new JLabel();
    private JLabel lblCodigoT = new JLabel();
    private JLabel lblLaboratorio = new JLabel();
    private JLabel lblLaboratorioT = new JLabel();
    public JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
    private JLabel lblDescripcion = new JLabel();
    private JLabel lblCodigo = new JLabel();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnDNI = new JButtonLabel();
    private JButtonLabel btnCantMeses = new JButtonLabel();

    private JTextFieldSanSerif txtRepetirDni = new JTextFieldSanSerif();
    private JButtonLabel btnRepetirDni = new JButtonLabel();
    
    private int minimo = 0;
    private int maximo = 0;
    
    private int cantMeses = 0;
    private String numDni = "";

    // **************************************************************************
    // Constructores
    // **************************************************************************

    /**
     *Constructor
     */
    public DlgIngresoProductoRimac() {
        this(null, "", false);
    }

    /**
     *Constructor
     */
    public DlgIngresoProductoRimac(Frame parent, String title, boolean modal) {
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

    /**
     *Implementa la Ventana con todos sus Objetos
     */
    private void jbInit() throws Exception {
        this.setSize(new Dimension(365, 344));
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Ingreso de Datos del Asegurado");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(360, 331));
        jContentPane.setBackground(Color.white);
        pnlDetalleProducto.setBounds(new Rectangle(15, 10, 325, 260));
        pnlDetalleProducto.setLayout(xYLayout1);
        pnlDetalleProducto.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlDetalleProducto.setFont(new Font("SansSerif", 0, 11));
        pnlDetalleProducto.setBackground(Color.white);
        pnlDetalleProducto.setFocusable(false);
        txtCantMeses.setHorizontalAlignment(JTextField.RIGHT);
        txtCantMeses.setFont(new Font("SansSerif", 1, 11));
        txtCantMeses.setDocument(new FarmaLengthText(6));
        txtCantMeses.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantMeses_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantMeses_keyTyped(e);
            }
        });
        lblDescripcionT.setText("Descripcion");
        lblDescripcionT.setFont(new Font("SansSerif", 1, 11));
        lblDescripcionT.setFocusable(false);
        lblCodigoT.setText("Codigo");
        lblCodigoT.setFont(new Font("SansSerif", 1, 11));
        lblCodigoT.setFocusable(false);
        lblLaboratorio.setText("COLLIERE S.A.");
        lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
        lblLaboratorio.setFocusable(false);
        lblLaboratorioT.setText("Laboratorio :");
        lblLaboratorioT.setFont(new Font("SansSerif", 1, 11));
        lblLaboratorioT.setFocusable(false);
        txtDni.setHorizontalAlignment(JTextField.LEFT);
        txtDni.setLengthText(9);
        txtDni.setFont(new Font("SansSerif", 1, 11));
        txtDni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDni_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtDni_keyTyped(e);
            }
        });
        lblDescripcion.setText(" ");
        lblDescripcion.setFont(new Font("SansSerif", 0, 11));
        lblDescripcion.setFocusable(false);
        lblCodigo.setText(" ");
        lblCodigo.setFont(new Font("SansSerif", 0, 11));
        lblCodigo.setFocusable(false);
        lblF11.setText("[ ENTER / F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(95, 280, 150, 20));
        lblF11.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(255, 280, 85, 20));
        lblEsc.setFocusable(false);
        btnDNI.setText("DNI :");
        btnDNI.setForeground(Color.black);
        btnDNI.setMnemonic('d');
        btnDNI.setFocusable(false);
        btnDNI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNumero_actionPerformed(e);
            }
        });
        txtDni.setDocument(new FarmaLengthText(8));
        btnCantMeses.setText("Cantidad de meses");
        btnCantMeses.setForeground(Color.black);
        btnCantMeses.setMnemonic('c');
        btnCantMeses.setFocusable(false);
        btnCantMeses.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMonto_actionPerformed(e);
            }
        });
        txtRepetirDni.setLengthText(8);
        txtRepetirDni.setFont(new Font("SansSerif", 1, 11));
        txtRepetirDni.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtReingresoNumero_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtReingresoNumero_keyPressed(e);
            }
        });
        btnRepetirDni.setText("Repetir DNI:");
        btnRepetirDni.setMnemonic('R');
        btnRepetirDni.setForeground(SystemColor.windowText);
        pnlDetalleProducto.add(btnRepetirDni, new XYConstraints(9, 189, 115, 20));
        pnlDetalleProducto.add(txtRepetirDni, new XYConstraints(150, 189, 125, 20));
        pnlDetalleProducto.add(btnCantMeses, new XYConstraints(9, 224, 130, 20));
        pnlDetalleProducto.add(btnDNI, new XYConstraints(9, 155, 120, 20));
        pnlDetalleProducto.add(txtCantMeses, new XYConstraints(150, 224, 65, 20));
        pnlDetalleProducto.add(lblDescripcionT, new XYConstraints(10, 55, 95, 20));
        pnlDetalleProducto.add(lblCodigoT, new XYConstraints(10, 10, 55, 20));
        pnlDetalleProducto.add(lblLaboratorio, new XYConstraints(10, 120, 280, 20));
        pnlDetalleProducto.add(lblLaboratorioT, new XYConstraints(10, 100, 80, 20));
        pnlDetalleProducto.add(txtDni, new XYConstraints(150, 155, 125, 20));
        pnlDetalleProducto.add(lblDescripcion, new XYConstraints(10, 75, 270, 20));
        pnlDetalleProducto.add(lblCodigo, new XYConstraints(9, 29, 265, 20));
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(pnlDetalleProducto, null);
        //this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void obtenerRangoMeses() {
        String cadena = UtilityInventario.getRangoProdRimac(VariablesVentas.vCod_Prod);        
        String[] rango = cadena.split("Ã");
        
        if (rango.length == 2) {
            setMinimo((int)Integer.parseInt(rango[0]));
            setMaximo((int)Integer.parseInt(rango[1]));
        } else {
            cerrarVentana(false);
        }        
    }

    private void muestraInfoDetalleProd() {
        obtenerRangoMeses();
        lblCodigo.setText(VariablesVentas.vCod_Prod);
        lblDescripcion.setText(VariablesVentas.vDesc_Prod);
        lblLaboratorio.setText(VariablesVentas.vNom_Lab);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtCantMeses_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            aceptaCantidadIngresada();
        } else{
            chkKeyPressed(e);
        }
    }

    private void txtDni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtDni.setEditable(false);
            FarmaUtility.moveFocus(txtRepetirDni);
        } else{
            chkKeyPressed(e);
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);        
        muestraInfoDetalleProd();
        FarmaUtility.moveFocus(txtDni);
        validarStkProdRegalo();
    }
    
    private void validarStkProdRegalo(){
        String stock = UtilityCaja.getStkProdRegalo(VariablesVentas.vCod_Prod);
        String[] val = stock.split("@");
        int cant = (int)Integer.parseInt(val[1].toString());
        if (cant <= 0) {
            FarmaUtility.showMessage(this, "No hay suficiente stock para vender el producto.\nComunicarse con RDM por el producto: " + val[0], null);
            cerrarVentana(false);
        }
    }

    private void btnNumero_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDni);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnMonto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantMeses);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            aceptaCantidadIngresada();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************


    private void aceptaCantidadIngresada() {        

        cantMeses = (int)Integer.parseInt(txtCantMeses.getText().trim());
        numDni = txtDni.getText().trim();
        
        if(!isCanCorrectaDigitos(numDni, 8)){            
            return;
        }
        
        if (!validaReingresoNumero()) {
            return;
        }
        if (!isAmountInsideRange()) {
            return;
        }
        
        VariablesVentas.vCantMesRimac = cantMeses;
        VariablesVentas.vDniRimac = numDni;
        
        cerrarVentana(true);
    }
    
    private boolean isAmountInsideRange() {
        boolean flag = false;
        if (cantMeses >= getMinimo() &&
            cantMeses <= getMaximo()) {
            flag = true;
        } else {
            FarmaUtility.showMessage(this, "Cantidad de meses inválida. Rango permitido de: " 
                                           + getMinimo() + " a " + getMaximo(), txtCantMeses);
        }
        return flag;
    }


    
    private void txtDni_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtDni, e);
    }

    private void txtCantMeses_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtCantMeses, e);
    }

    private void txtReingresoNumero_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtRepetirDni, e);
    }

    private void txtReingresoNumero_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCantMeses);
        } else {
            chkKeyPressed(e);
        }
    }

    private boolean validaReingresoNumero() {
        boolean vRetorno = true;
        String numero = txtDni.getText().trim();
        String reingresoNumero = txtRepetirDni.getText().trim();
        if (numero.equals("")) {
            vRetorno = false;
            txtDni.setEditable(true);
            FarmaUtility.showMessage(this, "Debe ingresar el DNI a asegurar.", txtDni);
        } else if (reingresoNumero.equals("")) {
            vRetorno = false;
            FarmaUtility.showMessage(this, "Debe re-ingresar el DNI a asegurar.", txtRepetirDni);
        } else if (!numero.equals(reingresoNumero)) {
            vRetorno = false;
            txtDni.setText("");
            txtRepetirDni.setText("");
            txtDni.setEditable(true);
            FarmaUtility.showMessage(this, "Los números de DNI no son iguales. ¡Verifique!", txtDni);
        }
        return vRetorno;
    }
    
    private boolean isCanCorrectaDigitos(String texto, int cantidad){
        boolean flag = true;
        if (texto.length() < cantidad) {
            flag = false;
            txtDni.setText("");
            txtRepetirDni.setText("");
            txtDni.setEditable(true);
            FarmaUtility.showMessage(this, 
                                     "El DNI debe tener " + cantidad + " dígitos", 
                                     txtDni);
        }        
        return flag;
    }
    
    

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public int getCantMeses() {
        return cantMeses;
    }

    public void setCantMeses(int cantMeses) {
        this.cantMeses = cantMeses;
    }

    public String getNumDni() {
        return numDni;
    }

    public void setNumDni(String numDni) {
        this.numDni = numDni;
    }
}
