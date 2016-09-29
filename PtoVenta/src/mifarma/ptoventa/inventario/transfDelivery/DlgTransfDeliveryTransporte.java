package mifarma.ptoventa.inventario.transfDelivery;


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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.DlgListaMaestros;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.inventario.transfDelivery.reference.VariablesTranfDelivery;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgTransfDeliveryTransporte.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      20.10.2008   Creación<br>
 * <br>
 * @author JCALLO<br>
 * @version 1.0<br>
 *
 */
public class DlgTransfDeliveryTransporte extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgTransfDeliveryTransporte.class);

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
    //private JTextFieldSanSerif txtDireccion_T = new JTextFieldSanSerif();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JTextFieldSanSerif txtTransportista = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtRucTransportista = new JTextFieldSanSerif();
    //private JTextFieldSanSerif txtDireccionTransportista = new JTextFieldSanSerif();
    private JLabelWhite lblDireccionTransportista_T = new JLabelWhite();
    private JLabelWhite lblRucTransportista_T = new JLabelWhite();
    private JLabelWhite lblPlaca_T = new JLabelWhite();
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
    private JComboBox cmbDefinicion = new JComboBox();
    private JButtonLabel btnDefinicion = new JButtonLabel();
    private JTextArea txtAreaDireccion = new JTextArea();
    private JTextArea txtAreaDirecTransf = new JTextArea();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgTransfDeliveryTransporte() {
        this(null, "", false);
    }

    public DlgTransfDeliveryTransporte(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(432, 556));
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
        pnlHeader1.setBounds(new Rectangle(5, 175, 410, 140));
        //pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlHeader1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
                                                                                              new Color(255, 130, 14)),
                                                              "Detalle Destino", TitledBorder.LEFT, TitledBorder.TOP,
                                                              new Font("SansSerif", 1, 11)));
        pnlHeader1.setBackground(Color.white);
        btnSenores.setText("Señores:");
        btnSenores.setBounds(new Rectangle(10, 25, 60, 20));
        btnSenores.setMnemonic('S');
        btnSenores.setForeground(new Color(255, 130, 14));
        btnSenores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSenores_actionPerformed(e);
            }
        });
        lblRuc.setText("RUC:");
        lblRuc.setBounds(new Rectangle(10, 55, 70, 20));
        lblRuc.setForeground(new Color(255, 130, 14));
        lblDireccion.setText("Dirección:");
        lblDireccion.setBounds(new Rectangle(10, 80, 70, 20));
        lblDireccion.setForeground(new Color(255, 130, 14));
        txtSenores.setBounds(new Rectangle(70, 25, 290, 20));
        txtSenores.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtSenores_keyPressed(e);
            }
        });
        txtRuc.setBounds(new Rectangle(70, 55, 100, 20));
        txtRuc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtRuc_keyPressed(e);
            }
        });
        txtAreaDireccion.setBounds(new Rectangle(70, 85, 325, 40));
        txtAreaDireccion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDireccion_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 325, 410, 170));
        /*pnlTitle1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(10, 10,
        10, 10, Color.pink),1,1,new Font("SansSerif", 1, 11),new Color(255,130,14) ));*/
        pnlTitle1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
                                                                                             new Color(255, 130, 14)),
                                                             "Transportista", TitledBorder.LEFT, TitledBorder.TOP,
                                                             new Font("SansSerif", 1, 11)));
        pnlTitle1.setBackground(Color.white);
        txtTransportista.setBounds(new Rectangle(95, 55, 265, 20));
        txtTransportista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtTransportista_keyPressed(e);
            }
        });
        txtRucTransportista.setBounds(new Rectangle(95, 25, 100, 20));
        txtRucTransportista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtRucTransportista_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtRucTransportista_keyReleased(e);
            }
        });
        txtAreaDirecTransf.setBounds(new Rectangle(95, 85, 300, 45));
        txtAreaDirecTransf.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDireccionTransportista_keyPressed(e);
            }
        });
        lblDireccionTransportista_T.setText("Dirección:");
        lblDireccionTransportista_T.setBounds(new Rectangle(10, 85, 70, 20));
        lblDireccionTransportista_T.setForeground(new Color(255, 130, 14));
        lblRucTransportista_T.setText("RUC Transp:");
        lblRucTransportista_T.setBounds(new Rectangle(10, 20, 85, 20));
        lblRucTransportista_T.setForeground(new Color(255, 130, 14));
        lblPlaca_T.setText("Placa:");
        lblPlaca_T.setBounds(new Rectangle(10, 140, 70, 20));
        lblPlaca_T.setForeground(new Color(255, 130, 14));
        txtPlaca.setBounds(new Rectangle(95, 140, 100, 20));
        txtPlaca.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtPlaca_keyPressed(e);
            }
        });
        lblTransportista_T.setText("Transportista:");
        lblTransportista_T.setBounds(new Rectangle(10, 55, 85, 20));
        lblTransportista_T.setForeground(new Color(255, 130, 14));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(330, 500, 85, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(205, 500, 115, 20));
        pnllHeader1.setBounds(new Rectangle(5, 10, 410, 155));
        pnllHeader1.setBackground(Color.white);
        //pnllHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnllHeader1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
                                                                                               new Color(255, 130,
                                                                                                         14)),
                                                               "Datos Generales", TitledBorder.LEFT, TitledBorder.TOP,
                                                               new Font("SansSerif", 1, 11)));
        lblFechaEmision.setText("09/01/2006");
        lblFechaEmision.setBounds(new Rectangle(315, 15, 70, 15));
        txtDestino.setBounds(new Rectangle(135, 90, 230, 20));
        txtCodigoDestino.setBounds(new Rectangle(70, 90, 55, 20));
        txtCodigoDestino.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCodigoDestino_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtCodigoDestino_keyReleased(e);
            }
        });
        cmbTipo.setBounds(new Rectangle(70, 25, 150, 20));
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
        btnTipo_T.setBounds(new Rectangle(10, 25, 30, 15));
        //btnTipo_T.setMnemonic('T');
        btnTipo_T.setForeground(new Color(255, 130, 14));
        btnTipo_T.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTipo_T_actionPerformed(e);
            }
        });
        lblFechaEmision_T.setText("F. Emisión:");
        lblFechaEmision_T.setBounds(new Rectangle(240, 15, 70, 15));
        lblDestino_T.setText("Destino");
        lblDestino_T.setBounds(new Rectangle(10, 90, 50, 15));
        lblDestino_T.setForeground(new Color(255, 130, 14));
        btnMotivo.setText("Motivo:");
        btnMotivo.setBounds(new Rectangle(10, 55, 50, 15));
        btnMotivo.setForeground(new Color(255, 130, 14));
        btnMotivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTipo_T_actionPerformed(e);
            }
        });
        cmbMotivo.setBounds(new Rectangle(70, 55, 195, 20));
        cmbMotivo.setFont(new Font("SansSerif", 1, 11));
        cmbMotivo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbMotivo_keyPressed(e);
            }
        });
        cmbDefinicion.setBounds(new Rectangle(70, 120, 145, 20));
        cmbDefinicion.setFont(new Font("SansSerif", 1, 11));
        cmbDefinicion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbDefinicion_keyPressed(e);
            }
        });
        btnDefinicion.setBounds(new Rectangle(10, 120, 50, 15));
        btnDefinicion.setForeground(new Color(255, 130, 14));
        btnDefinicion.setText("Definido");
        btnDefinicion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTipo_T_actionPerformed(e);
            }
        });
        //txtAreaDireccion.setBounds(new Rectangle(215, 40, 75, 35));
        pnllHeader1.add(btnDefinicion, null);
        pnllHeader1.add(cmbDefinicion, null);
        pnllHeader1.add(cmbMotivo, null);
        pnllHeader1.add(btnMotivo, null);
        pnllHeader1.add(lblFechaEmision, null);
        //pnlHeader1.add(txtAreaDireccion, null);
        pnllHeader1.add(txtDestino, null);
        pnllHeader1.add(txtCodigoDestino, null);
        pnllHeader1.add(cmbTipo, null);
        pnllHeader1.add(btnTipo_T, null);
        pnllHeader1.add(lblFechaEmision_T, null);
        pnllHeader1.add(lblDestino_T, null);
        pnlHeader1.add(txtAreaDireccion, null);
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
        pnlTitle1.add(txtAreaDirecTransf, null);
        pnlTitle1.add(txtRucTransportista, null);
        pnlTitle1.add(txtTransportista, null);
        jContentPane.add(pnllHeader1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //
        txtSenores.setLengthText(30);
        txtCodigoDestino.setLengthText(15);
        //txtDestino.setLengthText(30);
        txtCodigoDestino.setDisabledTextColor(Color.black);
        txtRuc.setLengthText(15);
        txtAreaDireccion.setLineWrap(true);
        txtTransportista.setLengthText(30);
        txtRucTransportista.setLengthText(15);
        txtAreaDirecTransf.setLineWrap(true);
        txtPlaca.setLengthText(15);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initCmbTipo();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCmbTipo() {
        ArrayList parametros = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbTipo, ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,
                                   "PTOVENTA_INV.INV_GET_TIPO_TRANSFERENCIA", parametros, false);
        parametros = null;
    }
    //Agregado por Paulo motivos de transferencia interno

    private void initCmbMotivoInterno() {
        cmbDefinicion.removeAllItems();
        ArrayList parametros = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbDefinicion, ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF_INTERNO,
                                   "PTOVENTA_INV.INV_GET_MOTIVO_TRANS_INTERNO", parametros, false);
        parametros = null;
    }

    private void initCmbMotivo() {
        cmbMotivo.removeAllItems();
        if (!FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,
                                     cmbTipo.getSelectedIndex()).equals("")) {
            ArrayList parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,
                                                   cmbTipo.getSelectedIndex()));

            FarmaLoadCVL.loadCVLFromSP(cmbMotivo, ConstantsInventario.NOM_HASHTABLE_CMBMOTIVO_TRANSF,
                                       "PTOVENTA_INV.INV_GET_MOTIVO_TRANSF(?,?)", parametros, false, true);
            parametros = null;
        }
        if (FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,
                                    cmbTipo.getSelectedIndex()).equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL)) {
            muestraMotivoInterno(true);
            initCmbMotivoInterno();
        } else
            muestraMotivoInterno(false);

        txtCodigoDestino.setText("");
        txtDestino.setText("");
    }

    private void muestraMotivoInterno(boolean valor) {
        btnDefinicion.setVisible(valor);
        cmbDefinicion.setVisible(valor);
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
                FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF, cmbTipo.getSelectedIndex());
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
            FarmaUtility.moveFocus(txtAreaDireccion);
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
            FarmaUtility.moveFocus(txtAreaDirecTransf);
        } else
            chkKeyPressed(e);
    }

    private void txtRucTransportista_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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
        /*if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtSenores);
    }
    else*/
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        //FarmaUtility.moveFocus(cmbTipo);
        //FarmaLoadCVL.setSelectedValueInComboBox(cmbTipo,ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,cmbTipo.getItemAt(2).toString());
        cmbTipo.setSelectedIndex(1);
        initCmbMotivo();
        cmbMotivo.setSelectedIndex(1);
        txtCodigoDestino.setText(VariablesTranfDelivery.vCodLocalDestino);
        VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_LOCAL;
        validaCodigo(txtCodigoDestino, txtDestino, VariablesPtoVenta.vTipoMaestro);
        mostrarDatosTransporte();
        cmbDefinicion.setSelectedIndex(2);

        cmbTipo.setEnabled(false);
        cmbMotivo.setEnabled(false);
        txtCodigoDestino.setEditable(false);
        txtDestino.setEditable(false);
        cmbDefinicion.setEnabled(false);
        txtSenores.setEditable(false);
        txtRuc.setEditable(false);
        txtAreaDireccion.setEditable(false);
        txtRucTransportista.setEditable(false);
        txtTransportista.setEditable(false);
        txtAreaDirecTransf.setEditable(false);
        txtPlaca.setEditable(false);

        FarmaUtility.moveFocus(txtPlaca);
        funcionF11();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "Desea generar la transferencia ?")) {
                funcionF11();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    public void funcionF11() {
        if (validarCampos()) {
            cargarCabecera();

            consultamotivo();
            cerrarVentana(true);
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
        } else if (cmbMotivo.getSelectedIndex() < 1) {
            FarmaUtility.showMessage(this, "Debe seleccionar un Motivo de Transferencia", cmbMotivo);
            retorno = false;
        } else if (cmbDefinicion.getSelectedIndex() < 1 && cmbDefinicion.isVisible()) {
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
        } else if (UtilityInventario.verificaRucValido(txtRuc.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO)) {
            FarmaUtility.showMessage(this, "Ingrese un Ruc Valido.", txtRuc);
            retorno = false;
        } else if (txtRuc.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Ruc del Destinatario", txtRuc);
            retorno = false;
        } else if (txtAreaDireccion.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar la Direccion de Destino", txtAreaDireccion);
            retorno = false;
        } else if (txtTransportista.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Transportista", txtTransportista);
            retorno = false;
        } else if (txtRucTransportista.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Ruc del Transportista", txtRucTransportista);
            retorno = false;
        } else if (UtilityInventario.verificaRucValido(txtRucTransportista.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO)) {
            FarmaUtility.showMessage(this, "Ingrese un Ruc Valido.", txtRucTransportista);
            retorno = false;
        } else if (txtAreaDirecTransf.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar la Direccion del Transportista", txtAreaDirecTransf);
            retorno = false;
        } else if (txtPlaca.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar la Placa del Transportista", txtPlaca);
            retorno = false;
        }

        return retorno;
    }

    private void cargarCabecera() {
        VariablesTranfDelivery.vTipoDestino_Transf =
                FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF, cmbTipo.getSelectedIndex());


        VariablesTranfDelivery.vMotivo_Transf =
                FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBMOTIVO_TRANSF,
                                        cmbMotivo.getSelectedIndex());
        VariablesInventario.vMotivo_Transf =
                VariablesTranfDelivery.vMotivo_Transf; //para modificar la clase DB  de inventario
        VariablesTranfDelivery.vDescMotivo_Transf =
                FarmaLoadCVL.getCVLDescription(ConstantsInventario.NOM_HASHTABLE_CMBMOTIVO_TRANSF,
                                               VariablesInventario.vMotivo_Transf);


        log.info("VariablesTranfDelivery.vTipoDestino_Transf" + VariablesTranfDelivery.vTipoDestino_Transf);
        log.info("VariablesTranfDelivery.vMotivo_Transf" + VariablesTranfDelivery.vMotivo_Transf);
        log.info("VariablesTranfDelivery.vDescMotivo_Transf" + VariablesTranfDelivery.vDescMotivo_Transf);

        VariablesTranfDelivery.vCodLocalDestino = txtCodigoDestino.getText();

        VariablesTranfDelivery.vCodDefinicion =
                FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF_INTERNO,
                                        cmbDefinicion.getSelectedIndex());

        //VariablesTranfDelivery.vTipoDestino_Transf

        VariablesTranfDelivery.vDestino_Transf = txtSenores.getText();
        VariablesTranfDelivery.vRucDestino_Transf = txtRuc.getText();
        VariablesTranfDelivery.vDirecDestino_Transf = txtAreaDireccion.getText();

        VariablesTranfDelivery.vTransportista_Transf = txtTransportista.getText();
        VariablesTranfDelivery.vRucTransportista_Transf = txtRucTransportista.getText();
        VariablesTranfDelivery.vDirecTransportista_Transf = txtAreaDirecTransf.getText();
        VariablesTranfDelivery.vPlacaTransportista_Transf = txtPlaca.getText();


    }

    private void mostrarDatosTransporte() {
        if (!FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,
                                     cmbTipo.getSelectedIndex()).equals(ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR)) {
            try {
                ArrayList array = new ArrayList();
                DBInventario.getDatosTransporteDelivery(array, txtCodigoDestino.getText());
                //log.debug("Array Datos Transp:"+array.size()+"");
                if (array.size() > 0) {
                    array = (ArrayList)array.get(0);
                    txtSenores.setText(array.get(0).toString());
                    txtRuc.setText(array.get(1).toString());
                    txtAreaDireccion.setText(array.get(2).toString());
                    txtTransportista.setText(array.get(3).toString());
                    txtRucTransportista.setText(array.get(4).toString());
                    txtAreaDirecTransf.setText(array.get(5).toString());
                    txtPlaca.setText(array.get(6).toString());
                }
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocuriió un error al obtener datos del transportista : \n" +
                        sql.getMessage(), cmbTipo);
            }
        }
    }

    private void mostrarTransportista() {
        txtAreaDirecTransf.setText("");
        txtPlaca.setText("");
        try {
            ArrayList array = new ArrayList();
            DBInventario.getDatosTransportista(array, txtRucTransportista.getText());
            if (array.size() > 0) {
                array = (ArrayList)array.get(0);
                txtAreaDirecTransf.setText(array.get(0).toString());
                txtPlaca.setText(array.get(1).toString());
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
     * obtiene el valor para poder saber si se habilitara el Text de Fraccion
     * @author dubilluz
     * @since  15.10.2007
     */
    public void consultamotivo() {
        log.debug("VariablesInventario.vMotivo_Transf : " + VariablesInventario.vMotivo_Transf);
        String codTipo =
            FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF, cmbTipo.getSelectedIndex());
        try {
            VariablesInventario.vIndTextFraccion =
                    DBInventario.consultaMotivo(VariablesInventario.vMotivo_Transf, codTipo);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error cuando se consultaba el motivo.\n" +
                    e.getMessage(), null);
        }

        log.debug("VariablesInventario.vIndTextFraccion : " + VariablesInventario.vIndTextFraccion);
    }
}
