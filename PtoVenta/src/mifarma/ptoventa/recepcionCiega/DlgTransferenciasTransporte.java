package mifarma.ptoventa.recepcionCiega;


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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgListaMaestros;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgTransferenciasTransporte.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCHAVEZ      30.11.2009   Creación<br>
 * <br>
 * @author Jenny Chávez<br>
 * @version 1.0<br>
 *
 */
public class DlgTransferenciasTransporte extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgTransferenciasTransporte.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlHeader1 = new JPanelTitle();
    private JButtonLabel btnSenores = new JButtonLabel();
    private JLabelWhite lblRuc = new JLabelWhite();
    private JLabelWhite lblDireccion = new JLabelWhite();
    private JTextFieldSanSerif txtSenores = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtRuc = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDireccion = new JTextFieldSanSerif();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JTextFieldSanSerif txtTransportista = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtRucTransportista = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDireccionTransportista = new JTextFieldSanSerif();
    private JLabelWhite lblDireccionTransportista_T = new JLabelWhite();
    private JLabelWhite lblRucTransportista_T = new JLabelWhite();
    //private JLabelWhite lblPlaca_T = new JLabelWhite();
    private JButtonLabel lblPlaca_T = new JButtonLabel();
    private JTextFieldSanSerif txtPlaca = new JTextFieldSanSerif();
    private JLabelWhite lblTransportista_T = new JLabelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanelHeader pnllHeader1 = new JPanelHeader();
    private JLabelWhite lblFechaEmision = new JLabelWhite();
    private JTextFieldSanSerif txtDestino = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCodigoDestino = new JTextFieldSanSerif();
    private JComboBox cmbTipo = new JComboBox();
    private JButtonLabel btnTipo_T = new JButtonLabel();
    private JLabelWhite lblFechaEmision_T = new JLabelWhite();
    private JLabelWhite lblDestino_T = new JLabelWhite();
    private JButtonLabel btnMotivo = new JButtonLabel();
    private JComboBox cmbMotivo = new JComboBox();
    private JComboBox cmbMotivo_du = new JComboBox();
    private JComboBox cmbDefinicion = new JComboBox();
    private JButtonLabel btnDefinicion = new JButtonLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                             */
    /* ********************************************************************** */

    public DlgTransferenciasTransporte() {
        this(null, "", false);
    }

    public DlgTransferenciasTransporte(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(443, 513));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Datos de Transporte");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 160, 415, 115));
        pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlHeader1.setBackground(Color.white);
        btnSenores.setText("Señores:");
        btnSenores.setBounds(new Rectangle(20, 15, 60, 15));
        btnSenores.setMnemonic('S');
        btnSenores.setForeground(new Color(255, 130, 14));
        btnSenores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSenores_actionPerformed(e);
            }
        });
        lblRuc.setText("RUC:");
        lblRuc.setBounds(new Rectangle(20, 48, 70, 15));
        lblRuc.setForeground(new Color(255, 130, 14));
        lblDireccion.setText("Dirección:");
        lblDireccion.setBounds(new Rectangle(20, 80, 70, 15));
        lblDireccion.setForeground(new Color(255, 130, 14));
        txtSenores.setBounds(new Rectangle(95, 15, 265, 20));
        txtSenores.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtSenores_keyPressed(e);
            }
        });
        txtRuc.setBounds(new Rectangle(95, 48, 99, 20));
        txtRuc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtRuc_keyPressed(e);
            }
        });
        txtDireccion.setBounds(new Rectangle(95, 80, 265, 20));
        txtDireccion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDireccion_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 285, 415, 160));
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlTitle1.setBackground(Color.white);
        txtTransportista.setBounds(new Rectangle(115, 45, 265, 20));
        txtTransportista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtTransportista_keyPressed(e);
            }
        });
        txtRucTransportista.setBounds(new Rectangle(115, 15, 100, 20));
        txtRucTransportista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtRucTransportista_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtRucTransportista_keyReleased(e);
            }
        });
        txtDireccionTransportista.setBounds(new Rectangle(115, 80, 265, 20));
        txtDireccionTransportista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDireccionTransportista_keyPressed(e);
            }
        });
        lblDireccionTransportista_T.setText("Dirección:");
        lblDireccionTransportista_T.setBounds(new Rectangle(20, 80, 70, 15));
        lblDireccionTransportista_T.setForeground(new Color(255, 130, 14));
        lblRucTransportista_T.setText("RUC Transp:");
        lblRucTransportista_T.setBounds(new Rectangle(20, 15, 70, 15));
        lblRucTransportista_T.setForeground(new Color(255, 130, 14));
        lblPlaca_T.setText("Placa:");
        lblPlaca_T.setMnemonic('p');
        lblPlaca_T.setBounds(new Rectangle(20, 110, 70, 15));
        lblPlaca_T.setForeground(new Color(255, 130, 14));
        txtPlaca.setBounds(new Rectangle(115, 110, 100, 20));
        txtPlaca.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtPlaca_keyPressed(e);
            }
        });
        lblTransportista_T.setText("Transportista:");
        lblTransportista_T.setBounds(new Rectangle(20, 45, 85, 15));
        lblTransportista_T.setForeground(new Color(255, 130, 14));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(335, 455, 85, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(205, 455, 115, 20));
        pnllHeader1.setBounds(new Rectangle(10, 10, 415, 140));
        pnllHeader1.setBackground(Color.white);
        pnllHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblFechaEmision.setText("09/01/2006");
        lblFechaEmision.setBounds(new Rectangle(315, 15, 70, 15));
        txtDestino.setBounds(new Rectangle(135, 75, 215, 20));
        txtDestino.setEnabled(false);
        txtCodigoDestino.setBounds(new Rectangle(70, 75, 55, 20));
        txtCodigoDestino.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCodigoDestino_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtCodigoDestino_keyReleased(e);
            }
        });
        cmbTipo.setBounds(new Rectangle(70, 10, 150, 20));
        cmbTipo.setFont(new Font("SansSerif", 1, 11));
        cmbTipo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbTipo_keyPressed(e);
            }
        });
        cmbTipo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cmbTipo_itemStateChanged(e);
            }
        });
        btnTipo_T.setText("Tipo");
        btnTipo_T.setBounds(new Rectangle(20, 10, 30, 15));
        btnTipo_T.setMnemonic('T');
        btnTipo_T.setForeground(new Color(255, 130, 14));
        btnTipo_T.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTipo_T_actionPerformed(e);
            }
        });
        lblFechaEmision_T.setText("F. Emisión:");
        lblFechaEmision_T.setBounds(new Rectangle(240, 15, 70, 15));
        lblDestino_T.setText("Destino");
        lblDestino_T.setBounds(new Rectangle(20, 75, 50, 15));
        lblDestino_T.setForeground(new Color(255, 130, 14));
        btnMotivo.setText("Motivo:");
        btnMotivo.setBounds(new Rectangle(20, 40, 50, 15));
        btnMotivo.setForeground(new Color(255, 130, 14));
        btnMotivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTipo_T_actionPerformed(e);
            }
        });
        cmbMotivo.setBounds(new Rectangle(70, 40, 195, 20));
        cmbMotivo.setFont(new Font("SansSerif", 1, 11));
        cmbMotivo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbMotivo_keyPressed(e);
            }
        });
        cmbDefinicion.setBounds(new Rectangle(70, 110, 145, 20));
        cmbDefinicion.setFont(new Font("SansSerif", 1, 11));
        cmbDefinicion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbDefinicion_keyPressed(e);
            }
        });
        cmbDefinicion.setVisible(false);
        btnDefinicion.setBounds(new Rectangle(20, 110, 50, 15));
        btnDefinicion.setForeground(new Color(255, 130, 14));
        btnDefinicion.setText("Definido");
        btnDefinicion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTipo_T_actionPerformed(e);
            }
        });
        btnDefinicion.setVisible(false);
        pnllHeader1.add(btnDefinicion, null);
        pnllHeader1.add(cmbDefinicion, null);
        pnllHeader1.add(cmbMotivo, null);
        pnllHeader1.add(btnMotivo, null);
        pnllHeader1.add(lblFechaEmision, null);
        pnllHeader1.add(txtDestino, null);
        pnllHeader1.add(txtCodigoDestino, null);
        pnllHeader1.add(cmbTipo, null);
        pnllHeader1.add(btnTipo_T, null);
        pnllHeader1.add(lblFechaEmision_T, null);
        pnllHeader1.add(lblDestino_T, null);
        pnlHeader1.add(txtDireccion, null);
        pnlHeader1.add(txtRuc, null);
        pnlHeader1.add(txtSenores, null);
        pnlHeader1.add(lblDireccion, null);
        pnlHeader1.add(lblRuc, null);
        pnlHeader1.add(btnSenores, null);
        pnlTitle1.add(lblTransportista_T, null);
        pnlTitle1.add(txtPlaca, null);
        pnlTitle1.add(lblPlaca_T, null);
        pnlTitle1.add(lblRucTransportista_T, null);
        pnlTitle1.add(lblDireccionTransportista_T, null);
        pnlTitle1.add(txtDireccionTransportista, null);
        pnlTitle1.add(txtRucTransportista, null);
        pnlTitle1.add(txtTransportista, null);
        jContentPane.add(pnllHeader1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlHeader1, null);
        getContentPane().add(jContentPane, BorderLayout.CENTER);
        txtSenores.setLengthText(30);
        txtCodigoDestino.setLengthText(15);
        txtRuc.setLengthText(15);
        txtDireccion.setLengthText(120);
        txtTransportista.setLengthText(30);
        txtRucTransportista.setLengthText(15);
        txtDireccionTransportista.setLengthText(120);
        txtPlaca.setLengthText(15);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                 */
    /* ************************************************************************ */

    private void initialize() {
        initCmbTipo();
        initCmbMotivo();
        initDestino();
        initDatosTransportista();
        inhabilitaCampos();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCmbTipo() {
        ArrayList parametros = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbTipo, ConstantsRecepCiega.NOM_HASHTABLE_CMBTIPO_TRANSF,
                                   "PTOVENTA_INV.INV_GET_TIPO_TRANSFERENCIA", parametros, false);
        //dubilluz 06.11.2013
        //cmbTipo.setSelectedItem("MATRIZ");
        cmbTipo.setSelectedItem("ALMACEN");
        //dubilluz 06.11.2013
        parametros = null;
    }

    private void initCmbMotivoInterno() {
        cmbDefinicion.removeAllItems();
        ArrayList parametros = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbDefinicion, ConstantsRecepCiega.NOM_HASHTABLE_CMBTIPO_TRANSF_INTERNO,
                                   "PTOVENTA_INV.INV_GET_MOTIVO_TRANS_INTERNO", parametros, false);


        parametros = null;
    }

    private void initCmbMotivo() {
        ArrayList parametros = new ArrayList();
        cmbMotivo.removeAllItems();
        /*
      201     SOBRESTOCK
      202     VENCIDOS
      203     DETERIORADOS
      204     DIGEMID
      207     DEVOLUCION AL PROVEEDOR
      208     REGULARIZACION ALMACEN
      206     CANJES PROX VENCIMIENTO
       * */
        //FarmaLoadCVL.loadCVLFromSP(cmbMotivo,ConstantsRecepCiega.NOM_HASHTABLE_CMBMOTIVO_TRANSF,"PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_STR_TXTFRACC_MOTIVO",parametros, false);
        if (VariablesRecepCiega.vMotivoTransferencia.equalsIgnoreCase("F1")) { //POLITICA DE CANJE
            //cmbMotivo.setSelectedItem("CANJES");    /*POLITICA DE CANJE*/
            //cmbMotivo.setSelectedItem("CANJES PROX VENCIMIENTO");
            cmbMotivo.addItem("CANJES PROX VENCIMIENTO");
            cmbMotivo.setSelectedItem("CANJES PROX VENCIMIENTO");
        } else if (VariablesRecepCiega.vMotivoTransferencia.equalsIgnoreCase("F2")) { //DETERIORADO
            //cmbMotivo.setSelectedItem("DETERIORADOS");
            cmbMotivo.addItem("DETERIORADOS");
            cmbMotivo.setSelectedItem("DETERIORADOS");
        }

        // FarmaLoadCVL.loadCVLFromSP(cmbMotivo_du,ConstantsRecepCiega.NOM_HASHTABLE_CMBMOTIVO_TRANSF,"PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_STR_TXTFRACC_MOTIVO",parametros, false);

        parametros = null;
        txtCodigoDestino.setText("");
        txtDestino.setText("");
    }

    private void muestraMotivoInterno(boolean valor) {
        btnDefinicion.setVisible(valor);
        cmbDefinicion.setVisible(valor);
    }

    private void initDestino() {
        if (cmbTipo.getSelectedItem().toString().equalsIgnoreCase("ALMACEN")) {
            try {
                ArrayList array = new ArrayList();
                DBRecepCiega.getDatosMatriz(array);
                if (array.size() == 1) {
                    array = (ArrayList)array.get(0);
                    txtCodigoDestino.setText(array.get(0).toString());
                    txtDestino.setText(array.get(1).toString());
                    log.debug("txtCodigoDestino: " + array.get(0).toString() + "\ntxtDestino" +
                              array.get(1).toString());
                }
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrió un error al obtener datos de matriz : \n" +
                        sql.getMessage(), txtCodigoDestino);
            }
        } else {
            FarmaUtility.showMessage(this, "Ocurrió un error, la transferencia no es a Matriz : \n", txtCodigoDestino);
        }

    }

    private void initDatosTransportista() {
        mostrarDatosTransporte();
    }

    private void inhabilitaCampos() {
        //ERIOS 09.01.2013 Se habilita los datos de transporte
        cmbTipo.setEnabled(false);
        cmbMotivo.setEnabled(false);
        txtCodigoDestino.setEditable(false);
        txtDestino.setEditable(false);
        txtDireccion.setEditable(false);
        //txtDireccionTransportista.setEditable(false);
        txtRuc.setEditable(false);
        //txtRucTransportista.setEditable(false);
        txtSenores.setEditable(false);
        //txtTransportista.setEditable(false);

    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnTipo_T_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbTipo);
    }

    private void cmbTipo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbMotivo);
            FarmaVariables.vAceptar = false;
        } else
            chkKeyPressed(e);
    }

    private void cmbTipo_itemStateChanged(ItemEvent e) {
        initCmbMotivo();
    }

    private void cmbMotivo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCodigoDestino);
            FarmaVariables.vAceptar = false;
        } else
            chkKeyPressed(e);
    }

    private void txtCodigoDestino_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String tipoMaestro =
                FarmaLoadCVL.getCVLCode(ConstantsRecepCiega.NOM_HASHTABLE_CMBTIPO_TRANSF, cmbTipo.getSelectedIndex());
            if (tipoMaestro.trim().length() == 0)
                FarmaUtility.showMessage(this, "Seleccione un Tipo de Origen.", cmbTipo);
            else {
                if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL))
                    VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_LOCAL;
                else if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
                    VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_MATRIZ;
                else if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR))
                    VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_PROVEEDOR;

                validaCodigo(txtCodigoDestino, txtDestino, VariablesPtoVenta.vTipoMaestro);
            }
        } else
            chkKeyPressed(e);
    }

    private void txtCodigoDestino_keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && FarmaVariables.vAceptar) {
            mostrarDatosTransporte();
            FarmaVariables.vAceptar = false;
            if (cmbDefinicion.isVisible())
                FarmaUtility.moveFocus(cmbDefinicion);
            else
                FarmaUtility.moveFocus(txtSenores);
        }
    }

    private void btnSenores_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSenores);
    }

    private void txtSenores_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtRuc);
            txtSenores.setText(txtSenores.getText().toUpperCase().trim());
        } else
            chkKeyPressed(e);
    }

    private void txtRuc_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDireccion);
        } else
            chkKeyPressed(e);
    }

    private void txtDireccion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtRucTransportista);
        } else
            chkKeyPressed(e);
    }

    private void txtTransportista_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDireccionTransportista);
        } else
            chkKeyPressed(e);
    }

    private void txtRucTransportista_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) { //CHUANES 16.04.2014
            //SE CAMBIA LA CONSTANTE DE LISTA DE TRANSPORTISTA
            VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_TRANSPORTISTA;
            validaCodigo(txtRucTransportista, txtTransportista, VariablesPtoVenta.vTipoMaestro);
        } else
            chkKeyPressed(e);
    }

    private void txtRucTransportista_keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && FarmaVariables.vAceptar) {
            mostrarTransportista();
            FarmaUtility.moveFocus(txtTransportista);
            FarmaVariables.vAceptar = false;
        }
    }

    private void txtDireccionTransportista_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtPlaca);
        } else
            chkKeyPressed(e);
    }

    private void txtPlaca_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtSenores);
        } else
            chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        //FarmaUtility.moveFocus(txtPlaca);
        FarmaUtility.moveFocus(txtRucTransportista);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (validarCampos()) {
                cargarCabecera();
                if (VariablesRecepCiega.vMotivo_Transf.trim().length() > 0) {
                    log.info("VariablesRecepCiega.vMotivo_Transf:" + VariablesRecepCiega.vMotivo_Transf);
                    cerrarVentana(true);
                    DlgListaImpresorasRecep vListaImp = new DlgListaImpresorasRecep(this.myParentFrame, "", true);
                    vListaImp.setVisible(true);
                } else {
                    FarmaUtility.showMessage(this, "No se ha seleccionado el Motivo de Transferencia.", txtPlaca);
                }


            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            FarmaVariables.vAceptar = false;
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void validaCodigo(JTextField pJTextField_Cod, JTextField pJTextField_Desc, String pTipoMaestro) {
        if (pJTextField_Cod.getText().trim().length() > 0) {
            VariablesPtoVenta.vCodMaestro = pJTextField_Cod.getText().trim();
            ArrayList myArray = new ArrayList();
            buscaCodigoListaMaestro(myArray);

            if (myArray.size() == 0) {
                FarmaUtility.showMessage(this, "Codigo No Encontrado", pJTextField_Cod);
                FarmaVariables.vAceptar = false;
            } else if (myArray.size() == 1) {
                String codigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
                String descripcion = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
                pJTextField_Cod.setText(codigo);
                pJTextField_Desc.setText(descripcion);
                VariablesPtoVenta.vCodMaestro = codigo;
                FarmaVariables.vAceptar = true;
            } else {
                FarmaUtility.showMessage(this, "Se encontro mas de un registro.Verificar!!!", pJTextField_Cod);
                FarmaVariables.vAceptar = false;
            }
        } else {
            cargaListaMaestros(pTipoMaestro, pJTextField_Cod, pJTextField_Desc);
        }
    }

    private void buscaCodigoListaMaestro(ArrayList pArrayList) {
        try {
            DBPtoVenta.buscaCodigoListaMaestro(pArrayList, VariablesPtoVenta.vTipoMaestro,
                                               VariablesPtoVenta.vCodMaestro);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al buscar código en maestros :\n" +
                    sql.getMessage(), cmbTipo);
        }
    }

    private void cargaListaMaestros(String pTipoMaestro, JTextField pJTextField_Cod, JTextField pJTextField_Desc) {
        VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
        DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
        dlgListaMaestros.setVisible(true);
        if (FarmaVariables.vAceptar) {
            pJTextField_Cod.setText(VariablesPtoVenta.vCodMaestro);
            pJTextField_Desc.setText(VariablesPtoVenta.vDescMaestro);
        }
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (cmbTipo.getSelectedIndex() < 1) {
            FarmaUtility.showMessage(this, "Debe seleccionar un Tipo de Transferencia", cmbTipo);
            retorno = false;
        }
        /*
    else if(cmbMotivo.getSelectedIndex() < 1)
    {
      FarmaUtility.showMessage(this,"Debe seleccionar un Motivo de Transferencia",cmbMotivo);
      retorno = false;
    }
    */
        else if (cmbDefinicion.getSelectedIndex() < 1 && cmbDefinicion.isVisible()) {
            FarmaUtility.showMessage(this, "Debe seleccionar un Motivo de Transferencia Interna", cmbDefinicion);
            retorno = false;
        } else if (txtCodigoDestino.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Codigo de Destino", txtCodigoDestino);
            retorno = false;
        } else if (txtCodigoDestino.getText().trim().equals(FarmaVariables.vCodLocal)) {
            txtCodigoDestino.setText("");
            txtDestino.setText("");
            FarmaUtility.showMessage(this, "No puede realizar esta transferencia. Modifique el destino.",
                                     txtCodigoDestino);
            retorno = false;
            FarmaVariables.vAceptar = false;
        } else if (txtSenores.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar la Razon Social", txtSenores);
            retorno = false;
        } else if (UtilityRecepCiega.verificaRucValido(txtRuc.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO)) {
            FarmaUtility.showMessage(this, "Ingrese un Ruc Valido.", txtRuc);
            retorno = false;
        } else if (txtRuc.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Ruc del Destinatario", txtRuc);
            retorno = false;
        } else if (txtDireccion.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar la Direccion de Destino", txtDireccion);
            retorno = false;
        } else if (txtTransportista.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Transportista", txtTransportista);
            retorno = false;
        } else if (txtRucTransportista.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Ruc del Transportista", txtRucTransportista);
            retorno = false;
        } else if (UtilityRecepCiega.verificaRucValido(txtRucTransportista.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO)) {
            FarmaUtility.showMessage(this, "Ingrese un Ruc Valido.", txtRucTransportista);
            retorno = false;
        } else if (txtDireccionTransportista.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar la Direccion del Transportista", txtDireccionTransportista);
            retorno = false;
        } else if (txtPlaca.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar la Placa del Transportista", txtPlaca);
            retorno = false;
        }

        return retorno;
    }

    private void cargarCabecera() {
        VariablesRecepCiega.vTipoDestino_Transf =
                FarmaLoadCVL.getCVLCode(ConstantsRecepCiega.NOM_HASHTABLE_CMBTIPO_TRANSF, cmbTipo.getSelectedIndex());
        if (cmbDefinicion.isVisible())
            VariablesRecepCiega.vMotivo_Transf_Interno =
                    FarmaLoadCVL.getCVLCode(ConstantsRecepCiega.NOM_HASHTABLE_CMBTIPO_TRANSF_INTERNO,
                                            cmbDefinicion.getSelectedIndex());
        else
            VariablesRecepCiega.vMotivo_Transf_Interno = "";


        VariablesRecepCiega.vMotivo_Transf =
                FarmaLoadCVL.getCVLCode(ConstantsRecepCiega.NOM_HASHTABLE_CMBMOTIVO_TRANSF,
                                        cmbMotivo.getSelectedIndex());
        /**
     *
     */
        /*
    201     SOBRESTOCK
    202     VENCIDOS
    203     DETERIORADOS
    204     DIGEMID
    207     DEVOLUCION AL PROVEEDOR
    208     REGULARIZACION ALMACEN
    206     CANJES PROX VENCIMIENTO
     * */
        //FarmaLoadCVL.loadCVLFromSP(cmbMotivo,ConstantsRecepCiega.NOM_HASHTABLE_CMBMOTIVO_TRANSF,"PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_STR_TXTFRACC_MOTIVO",parametros, false);
        if (VariablesRecepCiega.vMotivoTransferencia.equalsIgnoreCase("F1")) { // politica de canje
            VariablesRecepCiega.vMotivo_Transf = "206";
        } else if (VariablesRecepCiega.vMotivoTransferencia.equalsIgnoreCase("F2")) {
            VariablesRecepCiega.vMotivo_Transf = "203";
        }


        ///---------------------
        log.debug("vMotivoTransf: " + VariablesRecepCiega.vMotivo_Transf);
        //JMIRANDA 10.12.09
        // nombre MOTIVO transf larga
        VariablesRecepCiega.vDescMotivo_Transf =
                FarmaLoadCVL.getCVLDescription(ConstantsRecepCiega.NOM_HASHTABLE_CMBMOTIVO_TRANSF,
                                               VariablesRecepCiega.vMotivo_Transf);

        ///
        ArrayList p = new ArrayList();
        ArrayList listaMotivos = new ArrayList();
        try {
            FarmaDBUtility.executeSQLStoredProcedureArrayList(listaMotivos,
                                                              "PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_STR_TXTFRACC_MOTIVO",
                                                              p);
            log.debug("listaMotivos: " + listaMotivos);
        } catch (SQLException e) {

        }
        String description = new String("");
        for (int i = 0; i < listaMotivos.size(); i++) {
            if (((String)((ArrayList)listaMotivos.get(i)).get(0)).trim().equalsIgnoreCase(VariablesRecepCiega.vMotivo_Transf))
                description = ((String)((ArrayList)listaMotivos.get(i)).get(1)).trim();
        }
        VariablesRecepCiega.vDescMotivo_Transf = description;
        ////


        VariablesRecepCiega.vDescMotivo_Transf_Larga =
                obtieneDescripcionLarga(VariablesRecepCiega.vMotivo_Transf.trim(),
                                        VariablesRecepCiega.vDescMotivo_Transf.trim());
        log.debug("Corta: " + VariablesInventario.vDescMotivo_Transf + " Larga: " +
                  VariablesInventario.vDescMotivo_Transf_Larga);
        log.debug("Corta2: " + VariablesRecepCiega.vDescMotivo_Transf + " Larga2: " +
                  VariablesRecepCiega.vDescMotivo_Transf_Larga);
        ///-------------------
        //VariablesInventario.vMotivo_Transf= FarmaLoadCVL.getCVLCode(ConstantsRecepCiega.NOM_HASHTABLE_CMBMOTIVO_TRANSF,cmbMotivo.getSelectedIndex()); //--
        //VariablesRecepCiega.vDescMotivo_Transf = FarmaLoadCVL.getCVLDescription(ConstantsRecepCiega.NOM_HASHTABLE_CMBMOTIVO_TRANSF,VariablesRecepCiega.vMotivo_Transf); //--
        VariablesRecepCiega.vCodDestino_Transf = txtCodigoDestino.getText();
        VariablesRecepCiega.vDestino_Transf = txtSenores.getText();
        VariablesRecepCiega.vRucDestino_Transf = txtRuc.getText();
        VariablesRecepCiega.vDirecDestino_Transf = txtDireccion.getText();
        VariablesRecepCiega.vTransportista_Transf = txtTransportista.getText();
        VariablesRecepCiega.vRucTransportista_Transf = txtRucTransportista.getText();
        VariablesRecepCiega.vDirecTransportista_Transf = txtDireccionTransportista.getText();
        VariablesRecepCiega.vPlacaTransportista_Transf = txtPlaca.getText();
        if (VariablesRecepCiega.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
            VariablesRecepCiega.vTransfMatriz = true;
        else
            VariablesRecepCiega.vTransfMatriz = false;
        log.debug("VariablesInventario.vTransfMatriz " + VariablesRecepCiega.vTransfMatriz);
        log.debug("VariablesInventario.vTipoDestino_Transf " + VariablesRecepCiega.vTipoDestino_Transf);

    }

    private void mostrarDatosTransporte() {
        if (!FarmaLoadCVL.getCVLCode(ConstantsRecepCiega.NOM_HASHTABLE_CMBTIPO_TRANSF,
                                     cmbTipo.getSelectedIndex()).equals(ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR)) {
            try {
                ArrayList array = new ArrayList();
                DBInventario.getDatosTransporte(array, txtCodigoDestino.getText());
                if (array.size() > 0) {
                    array = (ArrayList)array.get(0);
                    txtSenores.setText(array.get(0).toString());
                    txtRuc.setText(array.get(1).toString());
                    txtDireccion.setText(array.get(2).toString());
                    txtTransportista.setText(array.get(3).toString());
                    txtRucTransportista.setText(array.get(4).toString());
                    txtDireccionTransportista.setText(array.get(5).toString());
                    txtPlaca.setText(array.get(6).toString());
                }
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocuriió un error al obtener datos del transportista : \n" +
                        sql.getMessage(), cmbTipo);
            }
        }
    }
    //CHUANES 16.04.2014
    //OBTENCION DE LOS DATOS DE TRANSPORTISTA Y SETEADOS EN LOS COMPONENTES CORRECTOS.

    private void mostrarTransportista() {
        txtPlaca.setText("-");
        try {
            ArrayList array = new ArrayList();
            DBInventario.getDatosTransportista(array, txtRucTransportista.getText());
            if (array.size() > 0) {
                array = (ArrayList)array.get(0);
                String codTransp = array.get(0).toString();
                String rucTransp = array.get(1).toString();
                String nomTransp = array.get(2).toString();
                String dirTransp = array.get(3).toString();
                if (codTransp != null && rucTransp != null) {
                    txtRucTransportista.setText(rucTransp);
                    txtTransportista.setText(nomTransp);
                    txtDireccionTransportista.setText(dirTransp);
                }


            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al obtener datos del transportista.\n" +
                    e.getMessage(), txtRucTransportista);
        }
    }

    private void cmbDefinicion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtSenores);
        } else
            chkKeyPressed(e);

    }

    /**
     * OBTIENE DESCRIPCION LARGA DE MOTIVO DE TRANSFERENCIA
     * JMIRANDA 10.12.09
     */
    public String obtieneDescripcionLarga(String pLlaveTabGral, String pDescCorta) {
        String vDescLarga = "";
        try {
            vDescLarga = DBInventario.getObtieneDescLargaTransf(pLlaveTabGral, pDescCorta);
            if (vDescLarga.trim().equalsIgnoreCase("N")) {
                vDescLarga = "";
            }
        } catch (SQLException sql) {
            log.error("", sql);
            vDescLarga = "";
        }
        return vDescLarga;
    }
}
