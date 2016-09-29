package mifarma.ptoventa.cliente;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.cliente.modelo.BeanClienteJuridico;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.cliente.reference.DBCliente;
import mifarma.ptoventa.cliente.reference.UtilityCliente;
import mifarma.ptoventa.cliente.reference.VariablesCliente;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgMantClienteJuridico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      23.02.2006   Creación<br>
 * PAULO       03.03.2006   Modificacion
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */
public class DlgMantClienteJuridico extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantClienteJuridico.class);

    private Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JPanelTitle pnlDatosLaboratorio = new JPanelTitle();
    private JTextFieldSanSerif txtRuc = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDireccion = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtRazonSocial = new JTextFieldSanSerif();
    private JPanelHeader pnlCodigoLaboratorio = new JPanelHeader();
    private JTextFieldSanSerif txtCodigo = new JTextFieldSanSerif();
    private JLabelWhite lblCodigoLaboratorio_T = new JLabelWhite();
    private JButtonLabel btnRuc = new JButtonLabel();
    private JButtonLabel btnRazonSocial = new JButtonLabel();
    private JButtonLabel btnDireccion = new JButtonLabel();
    private BeanClienteJuridico beanCliente;
    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMantClienteJuridico() {
        this(null, "", false);
    }

    public DlgMantClienteJuridico(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(462, 218));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Datos del Cliente Jurídico");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(500, 245));
        jContentPane.setFont(new Font("SansSerif", 0, 11));
        jContentPane.setLayout(null);
        lblEsc.setText("[ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(355, 160, 85, 20));
        lblEsc.setFont(new Font("SansSerif", 1, 12));
        lblF1.setText("[F11] Aceptar");
        lblF1.setBounds(new Rectangle(250, 160, 85, 20));
        lblF1.setFont(new Font("SansSerif", 1, 12));
        pnlDatosLaboratorio.setBounds(new Rectangle(10, 45, 435, 105));
        pnlDatosLaboratorio.setBackground(Color.white);
        pnlDatosLaboratorio.setFont(new Font("SansSerif", 0, 11));
        pnlDatosLaboratorio.setLayout(null);
        pnlDatosLaboratorio.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        txtRuc.setBounds(new Rectangle(100, 5, 120, 20));
        txtRuc.setFont(new Font("SansSerif", 0, 11));
        txtRuc.setDocument(new FarmaLengthText(11));
        txtRuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtRuc_keyPressed(e);
            }
        });
        txtDireccion.setBounds(new Rectangle(100, 65, 320, 20));
        txtDireccion.setFont(new Font("SansSerif", 0, 11));
        txtDireccion.setDocument(new FarmaLengthText(120));
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDireccion_keyPressed(e);
            }
        });
        txtRazonSocial.setBounds(new Rectangle(100, 35, 210, 20));
        txtRazonSocial.setFont(new Font("SansSerif", 0, 11));
        txtRazonSocial.setDocument(new FarmaLengthText(120));
        txtRazonSocial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtRazonSocial_keyPressed(e);
            }
        });
        pnlCodigoLaboratorio.setBounds(new Rectangle(10, 10, 425, 30));
        pnlCodigoLaboratorio.setSize(new Dimension(435, 30));
        pnlCodigoLaboratorio.setBackground(new Color(43, 141, 39));
        pnlCodigoLaboratorio.setLayout(null);
        pnlCodigoLaboratorio.setFont(new Font("SansSerif", 0, 11));
        txtCodigo.setBounds(new Rectangle(100, 5, 55, 20));
        txtCodigo.setFont(new Font("SansSerif", 0, 11));
        txtCodigo.setEnabled(false);
        lblCodigoLaboratorio_T.setText("Código : ");
        lblCodigoLaboratorio_T.setBounds(new Rectangle(5, 5, 55, 20));
        lblCodigoLaboratorio_T.setForeground(Color.white);
        btnRuc.setText("RUC : ");
        btnRuc.setBounds(new Rectangle(10, 10, 55, 15));
        btnRuc.setForeground(new Color(255, 130, 14));
        btnRuc.setMnemonic('r');
        btnRuc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRuc_actionPerformed(e);
            }
        });
        btnRazonSocial.setText("Razon Social : ");
        btnRazonSocial.setBounds(new Rectangle(10, 40, 85, 15));
        btnRazonSocial.setForeground(new Color(255, 130, 14));
        btnRazonSocial.setMnemonic('s');
        btnRazonSocial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRazonSocial_actionPerformed(e);
            }
        });
        btnDireccion.setText("Dirección : ");
        btnDireccion.setBounds(new Rectangle(10, 70, 75, 15));
        btnDireccion.setForeground(new Color(255, 130, 14));
        btnDireccion.setMnemonic('d');
        btnDireccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDireccion_actionPerformed(e);
            }
        });
        pnlDatosLaboratorio.add(btnDireccion, null);
        pnlDatosLaboratorio.add(btnRazonSocial, null);
        pnlDatosLaboratorio.add(btnRuc, null);
        pnlDatosLaboratorio.add(txtRuc, null);
        pnlDatosLaboratorio.add(txtDireccion, null);
        pnlDatosLaboratorio.add(txtRazonSocial, null);
        pnlCodigoLaboratorio.add(txtCodigo, null);
        pnlCodigoLaboratorio.add(lblCodigoLaboratorio_T, null);
        /*cmbTipoInventario.addItem("FARMA");
    cmbTipoInventario.addItem("NO FARMA");*/
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(pnlDatosLaboratorio, null);
        jContentPane.add(pnlCodigoLaboratorio, null);
        txtRazonSocial.setDocument(new FarmaLengthText(150));
        txtDireccion.setDocument(new FarmaLengthText(250));
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


    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        
        txtRuc.setEnabled(true);
        FarmaUtility.moveFocus(txtRuc);
        
        if (VariablesCliente.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_MODIFICAR)) {
            colocaInfoCliente();
            txtRuc.setEnabled(false);
            FarmaUtility.moveFocus(txtRazonSocial);
        }
        /*if (VariablesCliente.vIndicadorCargaCliente.equals(FarmaConstants.INDICADOR_S)) {
            txtRuc.setText(VariablesCliente.vRuc);
        }*/
    }

    private void txtRuc_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtRazonSocial);

        }
        chkKeyPressed(e);
    }

    private void txtRazonSocial_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDireccion);
            txtRazonSocial.setText(txtRazonSocial.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void txtDireccion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (VariablesCliente.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_INSERTAR)) {
                FarmaUtility.moveFocus(txtRuc);
            }else{
                FarmaUtility.moveFocus(txtRazonSocial);
            }
            txtDireccion.setText(txtDireccion.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void btnRuc_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtRuc);
    }

    private void btnRazonSocial_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtRazonSocial);
    }

    private void btnDireccion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDireccion);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            guardaValoresCliente();
            if (!validaDatosClientes())
                return;
            String resultado = "";
            boolean isExito = true;

            if (VariablesCliente.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_INSERTAR)) {
                //resultado = agregaClienteJuridico();
                isExito = agregaClienteJuridico();
            } else if (VariablesCliente.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_MODIFICAR)) {
                isExito = actualizaClienteJuridico();
            }
            
            if(isExito){
                VariablesCliente.vCodigo = beanCliente.getNumDocumento();
                ((ArrayList)(VariablesCliente.vArrayList_Cliente_Juridico.get(0))).set(0, VariablesCliente.vCodigo);
                FarmaUtility.showMessage(this, "Se grabo el Cliente con Exito", null);
                VariablesCliente.vRuc = "";
                cerrarVentana(true);
            }else{
                FarmaUtility.showMessage(this, "Error al grabar el Cliente", txtRuc);
            }/*
            if (!resultado.equalsIgnoreCase(ConstantsCliente.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                VariablesCliente.vCodigo = resultado;
                ((ArrayList)(VariablesCliente.vArrayList_Cliente_Juridico.get(0))).set(0, VariablesCliente.vCodigo);
                FarmaUtility.showMessage(this, "Se grabo el Cliente con Exito", null);
                VariablesCliente.vRuc = "";
                cerrarVentana(true);
            } else
                FarmaUtility.showMessage(this, "Error al grabar el Cliente", txtRuc);*/
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

    private void guardaValoresCliente() {
        VariablesCliente.vCodigo = txtCodigo.getText().trim();
        VariablesCliente.vRuc = txtRuc.getText().trim().toUpperCase();
        VariablesCliente.vRazonSocial = txtRazonSocial.getText().trim().toUpperCase();
        VariablesCliente.vDireccion = txtDireccion.getText().trim().toUpperCase();
        VariablesCliente.vTipoDocumento = ConstantsVentas.TIPO_COMP_FACTURA;
        VariablesCliente.vTipoBusqueda = ConstantsCliente.TIPO_BUSQUEDA_RUC;
        VariablesCliente.vRuc_RazSoc_Busqueda = txtRuc.getText().trim().toUpperCase();
        ArrayList myArray = new ArrayList();
        myArray.add(VariablesCliente.vCodigo);
        myArray.add(VariablesCliente.vRuc);
        myArray.add(VariablesCliente.vRazonSocial);
        myArray.add(VariablesCliente.vDireccion);
        VariablesCliente.vArrayList_Cliente_Juridico.clear();
        VariablesCliente.vArrayList_Cliente_Juridico.add(myArray);
        
        //KMONCADA 13.04.2016
        guardarDatosBean();
    }
    
    /**
     * @author KMONCADA
     * @since 13.04.2016
     * @version 1.0
     */
    private void guardarDatosBean(){
        if(beanCliente == null){
            beanCliente = new BeanClienteJuridico();
        }
        beanCliente.setCodCliente(VariablesCliente.vCodigo);
        beanCliente.setNumDocumento(VariablesCliente.vRuc);
        beanCliente.setRazonSocial(VariablesCliente.vRazonSocial);
        beanCliente.setDireccion(VariablesCliente.vDireccion);
        if(VariablesCliente.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_INSERTAR)){
            beanCliente.setAgenteRetencion("N");
            beanCliente.setAgentePercepcion("N");
        }
    }

    private boolean agregaClienteJuridico() {
        //String resultado = "";
        boolean resultado = true;
        try {
            resultado = (new UtilityCliente()).registrarClienteJuridico(this, beanCliente);
          /*  resultado =
                    DBCliente.agragaClienteJuridico(VariablesCliente.vRazonSocial, VariablesCliente.vTipoDocumento, VariablesCliente.vRuc,
                                                    VariablesCliente.vDireccion);*/
            //return resultado;
        } catch (Exception sql) {
            log.error("", sql);
            //ConstantsCliente.RESULTADO_GRABAR_CLIENTE_ERROR;
            resultado = false;
        }
        return resultado;
    }

    private boolean validaDatosClientes() {
        if (VariablesCliente.vRuc.equalsIgnoreCase("") || VariablesCliente.vRuc.length() < 11 ||
            verificaRucValido().equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO)) {
            FarmaUtility.showMessage(this, "Ingrese un RUC correcto", txtRuc);
            return false;
        }
        if (VariablesCliente.vRazonSocial.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese Razon Social correcta", txtRazonSocial);
            return false;
        }
        if (VariablesCliente.vDireccion.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese una Direccion correcta", txtDireccion);
            return false;
        }
        return true;
    }

    private String verificaRucValido() {
        String resultado = "";
        try {
            resultado = DBCliente.verificaRucValido(VariablesCliente.vRuc);
            return resultado;
        } catch (SQLException sql) {
            log.error("", sql);
            return ConstantsCliente.RESULTADO_RUC_INVALIDO;
        }
    }

    private boolean actualizaClienteJuridico() {
        //String resultado = "";
        boolean resultado = true;
        try {
            resultado = (new UtilityCliente()).registrarClienteJuridico(this, beanCliente);
            /*resultado =
                    DBCliente.actualizaClienteJuridico(VariablesCliente.vCodigo, VariablesCliente.vRazonSocial, VariablesCliente.vRuc,
                                                       VariablesCliente.vDireccion);
            return resultado;*/
        } catch (Exception sql) {
            log.error("", sql);
            //return ConstantsCliente.RESULTADO_GRABAR_CLIENTE_ERROR;
            resultado = false;
        }
        return resultado;
    }

    private void colocaInfoCliente() {
        if (VariablesCliente.vArrayList_Cliente_Juridico.size() == 1) {
            txtCodigo.setText("000000");//((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(0)).trim());
            txtRuc.setText(((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(1)).trim());
            txtRazonSocial.setText(((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(2)).trim());
            txtDireccion.setText(((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(3)).trim());
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

}
