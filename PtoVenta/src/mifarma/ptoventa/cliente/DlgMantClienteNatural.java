package mifarma.ptoventa.cliente;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.cliente.reference.DBCliente;
import mifarma.ptoventa.cliente.reference.VariablesCliente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgMantClienteNatural.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * PAULO      03.03.2006   Creación<br>
 * <br>
 * @author Paulo Cesar Ameghino Rojas<br>
 * @version 1.0<br>
 *
 */


public class DlgMantClienteNatural extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantClienteNatural.class);

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelHeader pnlCodigoLaboratorio = new JPanelHeader();
    private JTextFieldSanSerif txtCodigo = new JTextFieldSanSerif();
    private JLabelWhite lblCodigoLaboratorio_T = new JLabelWhite();
    private JPanelTitle pnlDatosLaboratorio = new JPanelTitle();
    private JButtonLabel btnDireccion = new JButtonLabel();
    private JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDireccion = new JTextFieldSanSerif();
    private JButtonLabel btnDNI = new JButtonLabel();
    private JButtonLabel btnNombre = new JButtonLabel();
    private JButtonLabel btnApePat = new JButtonLabel();
    private JButtonLabel btnApeMat = new JButtonLabel();
    private JTextFieldSanSerif txtNombre = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtApePat = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtApeMat = new JTextFieldSanSerif();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();

    public DlgMantClienteNatural() {
        this(null, "", false);
    }

    public DlgMantClienteNatural(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(461, 284));
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Datos del Cliente Natural");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelWhite1.setLayout(null);
        jPanelWhite1.setBounds(new Rectangle(0, 0, 455, 275));
        pnlCodigoLaboratorio.setBounds(new Rectangle(5, 5, 450, 30));
        pnlCodigoLaboratorio.setSize(new Dimension(435, 30));
        pnlCodigoLaboratorio.setBackground(new Color(43, 141, 39));
        pnlCodigoLaboratorio.setLayout(null);
        pnlCodigoLaboratorio.setFont(new Font("SansSerif", 0, 11));
        txtCodigo.setBounds(new Rectangle(115, 5, 55, 20));
        txtCodigo.setFont(new Font("SansSerif", 0, 11));
        txtCodigo.setEnabled(false);
        lblCodigoLaboratorio_T.setText("Código : ");
        lblCodigoLaboratorio_T.setBounds(new Rectangle(5, 5, 55, 20));
        lblCodigoLaboratorio_T.setForeground(Color.white);
        pnlDatosLaboratorio.setBounds(new Rectangle(5, 45, 435, 175));
        pnlDatosLaboratorio.setBackground(Color.white);
        pnlDatosLaboratorio.setFont(new Font("SansSerif", 0, 11));
        pnlDatosLaboratorio.setLayout(null);
        pnlDatosLaboratorio.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        btnDireccion.setText("Dirección : ");
        btnDireccion.setBounds(new Rectangle(10, 145, 75, 15));
        btnDireccion.setForeground(new Color(255, 130, 14));
        btnDireccion.setMnemonic('d');
        btnDireccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDireccion_actionPerformed(e);
            }
        });
        txtDni.setBounds(new Rectangle(115, 105, 135, 20));
        txtDni.setFont(new Font("SansSerif", 0, 11));
        txtDni.setDocument(new FarmaLengthText(8));
        txtDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDni_keyPressed(e);
            }
        });
        txtDni.setDocument(new FarmaLengthText(8));
        txtDireccion.setBounds(new Rectangle(115, 140, 300, 20));
        txtDireccion.setFont(new Font("SansSerif", 0, 11));
        txtDireccion.setDocument(new FarmaLengthText(120));
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDireccion_keyPressed(e);
            }
        });
        txtDireccion.setDocument(new FarmaLengthText(250));
        btnDNI.setText("DNI : ");
        btnDNI.setBounds(new Rectangle(10, 110, 55, 15));
        btnDNI.setForeground(new Color(255, 130, 14));
        btnDNI.setMnemonic('n');
        btnDNI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDNI_actionPerformed(e);
            }
        });
        btnNombre.setText("Nombre : ");
        btnNombre.setBounds(new Rectangle(10, 10, 55, 15));
        btnNombre.setForeground(new Color(255, 130, 14));
        btnNombre.setMnemonic('n');
        btnNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNombre_actionPerformed(e);
            }
        });
        btnApePat.setText("Apellido Paterno: ");
        btnApePat.setBounds(new Rectangle(10, 40, 105, 15));
        btnApePat.setForeground(new Color(255, 130, 14));
        btnApePat.setMnemonic('p');
        btnApePat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnApellidoPaterno_actionPerformed(e);
            }
        });
        btnApeMat.setText("Apellido Materno: ");
        btnApeMat.setBounds(new Rectangle(10, 75, 105, 15));
        btnApeMat.setForeground(new Color(255, 130, 14));
        btnApeMat.setMnemonic('m');
        btnApeMat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnApellidoMaterno_actionPerformed(e);
            }
        });
        txtNombre.setBounds(new Rectangle(115, 5, 135, 20));
        txtNombre.setFont(new Font("SansSerif", 0, 11));
        txtNombre.setDocument(new FarmaLengthText(30));
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNombre_keyPressed(e);
            }
        });
        txtNombre.setDocument(new FarmaLengthText(50));
        txtApePat.setBounds(new Rectangle(115, 35, 135, 20));
        txtApePat.setFont(new Font("SansSerif", 0, 11));
        txtApePat.setDocument(new FarmaLengthText(30));
        txtApePat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtApePat_keyPressed(e);
            }
        });
        txtApePat.setDocument(new FarmaLengthText(50));
        txtApeMat.setBounds(new Rectangle(115, 70, 135, 20));
        txtApeMat.setFont(new Font("SansSerif", 0, 11));
        txtApeMat.setDocument(new FarmaLengthText(30));
        txtApeMat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtApeMat_keyPressed(e);
            }
        });
        txtApeMat.setDocument(new FarmaLengthText(50));
        lblEsc.setText("[ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(355, 230, 85, 20));
        lblEsc.setFont(new Font("SansSerif", 1, 12));
        lblF1.setText("[F11] Aceptar");
        lblF1.setBounds(new Rectangle(250, 230, 85, 20));
        lblF1.setFont(new Font("SansSerif", 1, 12));
        pnlDatosLaboratorio.add(txtApeMat, null);
        pnlDatosLaboratorio.add(txtApePat, null);
        pnlDatosLaboratorio.add(txtNombre, null);
        pnlDatosLaboratorio.add(btnApeMat, null);
        pnlDatosLaboratorio.add(btnApePat, null);
        pnlDatosLaboratorio.add(btnNombre, null);
        pnlDatosLaboratorio.add(btnDNI, null);
        pnlDatosLaboratorio.add(btnDireccion, null);
        pnlDatosLaboratorio.add(txtDni, null);
        pnlDatosLaboratorio.add(txtDireccion, null);
        pnlCodigoLaboratorio.add(txtCodigo, null);
        pnlCodigoLaboratorio.add(lblCodigoLaboratorio_T, null);
        jPanelWhite1.add(lblF1, null);
        jPanelWhite1.add(lblEsc, null);
        jPanelWhite1.add(pnlDatosLaboratorio, null);
        jPanelWhite1.add(pnlCodigoLaboratorio, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);

        if (VariablesCliente.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_MODIFICAR)) {
            try {
                cargaDatosCliente_Variables();
            } catch (SQLException sql) {
                log.error("", sql);
            }
        }
        FarmaUtility.moveFocus(txtNombre);
    }

    private String agregaClienteNatural() {
        String resultado = "";
        try {
            resultado =
                    DBCliente.agragaClienteNatural(VariablesCliente.vNombre, VariablesCliente.vApellidoPat, VariablesCliente.vApellidoMat,
                                                   VariablesCliente.vTipoDocumento, VariablesCliente.vDni,
                                                   VariablesCliente.vDireccion);

            log.debug(resultado);
            return resultado;
        } catch (SQLException sql) {
            log.error("", sql);
            return ConstantsCliente.RESULTADO_GRABAR_CLIENTE_ERROR;
        }
    }

    private String actualizaClienteNatural() {
        String resultado = "";
        try {
            resultado =
                    DBCliente.actualizaClienteNatural(VariablesCliente.vCodigo, VariablesCliente.vNombre, VariablesCliente.vApellidoPat,
                                                      VariablesCliente.vApellidoMat, VariablesCliente.vDni,
                                                      VariablesCliente.vDireccion);
            return resultado;
        } catch (SQLException sql) {
            log.error("", sql);
            return ConstantsCliente.RESULTADO_GRABAR_CLIENTE_ERROR;
        }
    }

    private void guardaValoresCliente() {
        VariablesCliente.vCodigo = txtCodigo.getText().trim();
        VariablesCliente.vNombre = txtNombre.getText().trim().toUpperCase();
        VariablesCliente.vApellidoPat = txtApePat.getText().trim().toUpperCase();
        VariablesCliente.vApellidoMat = txtApeMat.getText().trim().toUpperCase();
        VariablesCliente.vDni = txtDni.getText().trim();
        VariablesCliente.vTipoDocumento = ConstantsCliente.TIPO_NATURAL;
        VariablesCliente.vDireccion = txtDireccion.getText().trim().toUpperCase();
        VariablesCliente.vTipoBusqueda = ConstantsCliente.TIPO_BUSQUEDA_DNI;
        VariablesCliente.vRuc_RazSoc_Busqueda = txtDni.getText().trim().toUpperCase();
        ArrayList myArray = new ArrayList();
        myArray.add(VariablesCliente.vCodigo);
        myArray.add(VariablesCliente.vNombre);
        myArray.add(VariablesCliente.vApellidoPat);
        myArray.add(VariablesCliente.vApellidoMat);
        myArray.add(VariablesCliente.vDni);
        myArray.add(VariablesCliente.vDireccion);
        VariablesCliente.vArrayList_Cliente_Juridico.clear();
        VariablesCliente.vArrayList_Cliente_Juridico.add(myArray);
        log.debug("", myArray);
    }

    private boolean validaDatosClientes() {
        if (VariablesCliente.vDni.equalsIgnoreCase("") || VariablesCliente.vDni.length() < 8) {
            FarmaUtility.showMessage(this, "Ingrese un DNI Valido", txtDni);
            return false;
        }
        if (VariablesCliente.vNombre.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese Nombre correcto", txtNombre);
            return false;
        }
        if (VariablesCliente.vApellidoPat.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese Apellido Paterno correcto", txtApePat);
            return false;
        }
        if (VariablesCliente.vApellidoMat.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese Apellido Materno correcto", txtApeMat);
            return false;
        }
        if (VariablesCliente.vDireccion.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese una Direccion correcta", txtDireccion);
            return false;
        }
        return true;
    }


    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            guardaValoresCliente();
            if (!validaDatosClientes())
                return;
            String resultado = "";
            log.debug(VariablesCliente.vTipo_Accion);
            if (VariablesCliente.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_INSERTAR)) {
                resultado = agregaClienteNatural();
                log.debug("resultado = " + resultado);
            } else if (VariablesCliente.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_MODIFICAR)) {

                resultado = actualizaClienteNatural();
                log.debug("resultado = " + resultado);
            }
            if (resultado.equalsIgnoreCase(ConstantsCliente.RESULTADO_GRABAR_CLIENTE_EXITO)) {
                FarmaUtility.showMessage(this, "Se grabo el Cliente con Exito", null);
                cerrarVentana(true);
            } else
                FarmaUtility.showMessage(this, "Error al grabar el Cliente", txtNombre);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void btnRazonSocial_actionPerformed(ActionEvent e) {
    }


    private void txtRazonSocial_keyPressed(KeyEvent e) {
    }

    private void txtNombre_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtApePat);
            txtNombre.setText(txtNombre.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void txtApePat_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtApeMat);
            txtApePat.setText(txtApePat.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void txtApeMat_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDni);
            txtApeMat.setText(txtApeMat.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void txtDni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDireccion);
        }
        chkKeyPressed(e);
    }

    private void txtDireccion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNombre);
            txtDireccion.setText(txtDireccion.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void cargaDatosCliente_Variables() throws SQLException {
        ArrayList myArray = new ArrayList();
        log.debug("VariablesCliente.vCodigo : " + VariablesCliente.vCodigo);
        DBCliente.obtieneInfo_Cli_Natural(myArray);
        log.debug("myArray : " + myArray.size());
        VariablesCliente.vCodigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
        VariablesCliente.vNombre = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
        VariablesCliente.vApellidoPat = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
        VariablesCliente.vApellidoMat = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
        VariablesCliente.vDni = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
        VariablesCliente.vDireccion = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
        cargaDatosCliente_Pantalla();
    }

    private void cargaDatosCliente_Pantalla() {
        txtCodigo.setText(VariablesCliente.vCodigo);
        txtNombre.setText(VariablesCliente.vApellidoPat);
        txtApePat.setText(VariablesCliente.vApellidoMat);
        txtApeMat.setText(VariablesCliente.vDni);
        txtDni.setText(VariablesCliente.vNombre);
        txtDireccion.setText(VariablesCliente.vDireccion);

    }

    private void btnNombre_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNombre);
    }

    private void btnApellidoPaterno_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtApePat);
    }

    private void btnApellidoMaterno_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtApeMat);
    }

    private void btnDNI_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDni);
    }

    private void btnDireccion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDireccion);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

}
