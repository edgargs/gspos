package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.DlgListaMaestros;
import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

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
public class DlgIngresoTransportista_02 extends JDialog {
    /* ********************************************************************** */
    /* DECLARACION PROPIEDADES */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoTransportista_02.class);

    private JPanel jContentPane = new JPanel();

    Frame myParentFrame;
    private int vEstVigencia;
    private String FechaInicio = "";

    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JTextFieldSanSerif txtPrecintos = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtBultos = new JTextFieldSanSerif();
    // adcionado 15042014
    private JTextFieldSanSerif txtBandejas = new JTextFieldSanSerif();

    private JLabelWhite lblValor_T = new JLabelWhite();

    private JButtonLabel btnFechaInicial = new JButtonLabel();

    private JTextFieldSanSerif txtNombre = new JTextFieldSanSerif();

    private JLabelWhite lblFechaFinal_T = new JLabelWhite();


    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelFunction lblF11 = new JLabelFunction();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JLabelWhite lblCodPromocion = new JLabelWhite();
    private JLabelOrange jLabelOrange2 = new JLabelOrange();
    private JTextFieldSanSerif txtPlaca = new JTextFieldSanSerif();
    private JButtonLabel lblGlosa = new JButtonLabel();

    //INI ASOSA - 21/07/2014
    private JButtonLabel lblHojaRes = new JButtonLabel();
    private JButtonLabel lblNroBandeja = new JButtonLabel();
    private JButtonLabel lblCodValija = new JButtonLabel();
    private JTextFieldSanSerif txtHojaRes = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNroBandeja = new JTextFieldSanSerif();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblBandejas = new JTable();
    private final int COL_ORD_LISTA = 1;
    private FarmaTableModel tableModelBandejas;
    private FarmaTableModel tableModelHojaRes;
    private boolean flagExisteHojaResumen = false;
    private String nroHojaRes = "";
    private String cantBandejas = "";
    private int cont = 0;
    //FIN ASOSA - 25/07/2014

    private JTextFieldSanSerif txtGlosa = new JTextFieldSanSerif();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JTextFieldSanSerif txtcodtrans = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtnomtrans = new JTextFieldSanSerif();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();

    private int MAX_DIGITOS = 10;
    private int LENGTH_COD_VALIJA_01 = 13;
    private int LENGTH_COD_VALIJA_02 = 14;
    private String ABREV_MFA = "MFA";
    private String ABREV_BTL = "BTL";
    private String ABREV_FASA = "FASA";
    private JTextFieldSanSerif txtCodValija = new JTextFieldSanSerif();

    /* ********************************************************************** */
    /* CONSTRUCTORES */
    /* ********************************************************************** */

    public DlgIngresoTransportista_02() {
        this(null, "", false);
    }

    public DlgIngresoTransportista_02(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(529, 533));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso Datos Transportista");
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
        pnlTitle1.setBounds(new Rectangle(5, 10, 510, 455));
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
        txtBandejas.setLengthText(6);
        txtBandejas.setBounds(new Rectangle(195, 130, 135, 20));
        txtBandejas.setText("0");
        txtBandejas.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {
                txtBandejas_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtBandejas_keyPressed(e);
            }
        });

        txtBultos.setLengthText(6);
        txtBultos.setBounds(new Rectangle(195, 100, 120, 20));
        txtBultos.addKeyListener(new KeyAdapter() {


            public void keyPressed(KeyEvent e) {
                txtBultos_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtBultos_keyTyped(e);
            }
        });
        // aampuero 14.04.2014
        lblValor_T.setText("N\u00ba Bandejas que se Devuelven:");
        lblValor_T.setForeground(new Color(255, 130, 14));
        lblValor_T.setBounds(new Rectangle(10, 130, 190, 20));
        btnFechaInicial.setText("Nombre completo :");

        btnFechaInicial.setForeground(new Color(255, 130, 14));
        btnFechaInicial.setMnemonic('N');
        btnFechaInicial.setBounds(new Rectangle(10, 40, 105, 20));
        btnFechaInicial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFechaInicial_actionPerformed(e);
            }
        });
        txtNombre.setLengthText(99);
        txtNombre.setBounds(new Rectangle(195, 40, 265, 20));
        txtNombre.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNombre_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtNombre_keyTyped(e);
            }
        });
        lblFechaFinal_T.setText("Cantidad Bultos Recibidos:");
        lblFechaFinal_T.setForeground(new Color(255, 130, 14));
        lblFechaFinal_T.setBounds(new Rectangle(10, 100, 190, 20));
        lblEsc.setText("[ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(395, 475, 85, 20));
        lblF11.setText("[F11] Aceptar");
        lblF11.setBounds(new Rectangle(295, 475, 95, 20));
        lblCodPromocion.setText("[CodPromocion]");
        lblCodPromocion.setForeground(new Color(255, 130, 14));
        lblCodPromocion.setRequestFocusEnabled(false);
        lblCodPromocion.setFocusable(false);
        lblCodPromocion.setVisible(false);
        lblCodPromocion.setBounds(new Rectangle(0, 10, 105, 15));
        //--Se cambio el tamaño de digitos
        //  12.09.2008 Dubilluz
        jLabelOrange2.setText("Placa Unidad :");
        jLabelOrange2.setBounds(new Rectangle(10, 70, 125, 20));
        txtPlaca.setBounds(new Rectangle(195, 70, 175, 20));
        txtPlaca.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtPlaca_keyPressed(e);
            }
        });
        txtPlaca.setLengthText(8);
        lblGlosa.setText("Glosa :");
        lblGlosa.setBounds(new Rectangle(10, 190, 75, 15));
        lblGlosa.setForeground(new Color(255, 130, 14));
        //INI ASOSA - 21/07/2014
        lblHojaRes.setText("Nro de Hoja Resumen :");
        lblHojaRes.setBounds(new Rectangle(10, 220, 135, 15));
        lblHojaRes.setForeground(new Color(255, 130, 14));
        lblHojaRes.setMnemonic('H');
        lblHojaRes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblHojaRes_actionPerformed(e);
            }
        });
        lblNroBandeja.setText("Nro de Bandeja/Bulto :");
        lblNroBandeja.setBounds(new Rectangle(10, 250, 125, 15));
        lblNroBandeja.setForeground(new Color(255, 130, 14));
        lblNroBandeja.setMnemonic('B');
        lblNroBandeja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNroBandeja_actionPerformed(e);
            }
        });

        lblCodValija.setText("Código de Valija devuelta :");
        lblCodValija.setBounds(new Rectangle(10, 160, 165, 15));
        lblCodValija.setForeground(new Color(255, 130, 14));
        lblCodValija.setMnemonic('V');
        lblCodValija.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblCodValija_actionPerformed(e);
            }
        });

        txtHojaRes.setBounds(new Rectangle(195, 220, 150, 20));
        txtHojaRes.setLengthText(10);
        txtHojaRes.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtHojaRes_keyPressed(e);
            }
        });
        txtNroBandeja.setBounds(new Rectangle(195, 250, 150, 20));

        txtNroBandeja.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNroBandeja_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtNroBandeja_keyTyped(e);
            }
        });
        txtNroBandeja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNroBandeja_actionPerformed(e);
            }
        });
        jScrollPane1.getViewport();
        jPanelTitle1.add(jLabelWhite1, null);
        pnlTitle1.add(txtCodValija, null);
        pnlTitle1.add(jPanelTitle1, null);
        jScrollPane1.getViewport().add(tblBandejas, null);
        pnlTitle1.add(jScrollPane1, null);
        pnlTitle1.add(jScrollPane1, new XYConstraints(115, 15, 195, 20));
        tblBandejas.setFont(new Font("SansSerif", 0, 12));
        //FIN ASOSA - 25/07/2014
        txtGlosa.setBounds(new Rectangle(195, 190, 305, 20));
        txtGlosa.setLengthText(2000);
        txtGlosa.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtGlosa_keyPressed(e);
            }
        });
        jButtonLabel1.setText("Empresa de Transporte:");
        jButtonLabel1.setBounds(new Rectangle(10, 15, 140, 15));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('e');
        jButtonLabel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        jButtonLabel1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButtonLabel1_keyPressed(e);
            }
        });
        txtcodtrans.setBounds(new Rectangle(195, 15, 35, 20));
        txtcodtrans.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtcodtrans_keyPressed(e);
            }
        });
        txtnomtrans.setBounds(new Rectangle(235, 15, 185, 20));
        txtnomtrans.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtnomtrans_keyPressed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(105, 320, 240, 115));
        jPanelTitle1.setBounds(new Rectangle(105, 300, 240, 20));
        jLabelWhite1.setText("Lista de Bandejas / Bultos");
        jLabelWhite1.setBounds(new Rectangle(10, 5, 150, 15));
        jLabelFunction1.setBounds(new Rectangle(90, 475, 195, 20));
        jLabelFunction1.setText("[ F9 ] Eliminar Ultimo Ingresado");
        txtCodValija.setBounds(new Rectangle(195, 160, 135, 20));
        txtCodValija.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCodValija_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCodValija_keyTyped(e);
            }
        });
        txtCodValija.setLengthText(14);
        pnlTitle1.add(txtnomtrans, null);
        pnlTitle1.add(txtcodtrans, null);
        pnlTitle1.add(jButtonLabel1, null);
        pnlTitle1.add(txtGlosa, null);
        //INI ASOSA - 21/07/2014
        pnlTitle1.add(lblGlosa, null);
        pnlTitle1.add(lblHojaRes, null);
        pnlTitle1.add(lblCodValija, null);
        //FIN ASOSA - 25/07/2014
        pnlTitle1.add(lblNroBandeja, null);
        pnlTitle1.add(txtHojaRes, null);
        pnlTitle1.add(txtNroBandeja, null);
        pnlTitle1.add(txtPlaca, null);
        pnlTitle1.add(jLabelOrange2, null);
        pnlTitle1.add(lblCodPromocion, new XYConstraints(0, 10, 105, 15));
        /*
        pnlTitle1.add(txtPrecintos, new XYConstraints(115, 70, 195, 20));
        */
        // AAMPUERO 14.04.2014
        pnlTitle1.add(txtBandejas, new XYConstraints(115, 70, 195, 20));
        pnlTitle1.add(txtBultos, new XYConstraints(115, 45, 195, 20));
        pnlTitle1.add(lblValor_T, new XYConstraints(15, 70, 90, 15));
        pnlTitle1.add(btnFechaInicial, new XYConstraints(15, 20, 90, 15));
        pnlTitle1.add(txtNombre, new XYConstraints(115, 15, 195, 20));
        pnlTitle1.add(lblFechaFinal_T, new XYConstraints(15, 50, 90, 15));
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        txtNroBandeja.setLengthText(MAX_DIGITOS);
    }

    /* ********************************************************************** */
    /* METODO INITIALIZE */
    /* ********************************************************************** */

    private void initialize() {
        //lblDescLocal.setText(FarmaVariables.vCodLocal.trim()+" - "+FarmaVariables.vDescCortaLocal.trim());
        //jquispe 05.05.2010 lee las veces que imprimira un voucher
        initTableListaBandejas(); //ASOSA - 21/07/2014
        VariablesRecepCiega.vNumImpresiones = UtilityRecepCiega.getNumImpresiones();
        cargarFecha();
    }

    /* ********************************************************************** */
    /* METODO DE INICIALIZACION */
    /* ********************************************************************** */

    /* ********************************************************************** */
    /* METODOS DE EVENTOS */
    /* ********************************************************************** */

    private void txtFechaInicio_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnFechaInicial_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNombre);
    }

    private void txtVTa_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtNombre, e);
    }

    private void txtAlmc_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtBultos, e);
        FarmaUtility.admitirDigitosDecimales(txtBandejas, e);
    }

    private void txtFechainicial_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtBultos);
        }
        chkKeyPressed(e);
    }

    /*
    private void txtFechaFinal_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtPrecintos);
        }
        chkKeyPressed(e);
    }
    */
    // AAMPUERO 14.04.2014

    private void txtFechaFinal_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtBandejas);
        }
        chkKeyPressed(e);
    }

    private void txtValor_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNombre);
        }
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtcodtrans);
        txtnomtrans.setEnabled(false);
        txtBultos.setText("0");
        txtPrecintos.setText("0");
        cargaLogin();
    }

    private void initTableListaBandejas() {
        tableModelBandejas =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaBandeja, ConstantsRecepCiega.defaultValuesListaBandeja,
                                    0);

        tableModelHojaRes =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaHoja, ConstantsRecepCiega.defaultValuesListaHoja, 0);

        FarmaUtility.initSimpleList(tblBandejas, tableModelBandejas, ConstantsRecepCiega.columnsListaBandeja);
        tblBandejas.setName(ConstantsRecepCiega.NAME_TABLA_BANDEJAS);
        if (tableModelBandejas.getRowCount() > 0)
            FarmaUtility.ordenar(tblBandejas, tableModelBandejas, COL_ORD_LISTA, FarmaConstants.ORDEN_ASCENDENTE);
    }

    private void txtFechaFinal_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtBultos, e);
    }

    private void txtValor_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtPrecintos, e);
    }

    private void txtFecFacturacion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNombre);
        }
        chkKeyPressed(e);
    }

    private void txtFecFacturacion_keyReleased(KeyEvent e) {
        //FarmaUtility.dateComplete(txtFecFacturacion,e);
    }

    private void txtValorProv_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // FarmaUtFility.moveFocus(txtFecFacturacion);
        }
        chkKeyPressed(e);
    }

    private void txtValorProv_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtPrecintos, e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ********************************************************************** */
    /* METODOS AUXILIARES */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            //obtiene datos
            if (datosValidados() && validarCantidadBultos()) //ASOSA - 21/07/2014
            {
                if (JConfirmDialog.rptaConfirmDialog(this, "Está seguro que desea grabar el transportista ?")) {
                    //obtiene datos transportista
                    getDatosTransportista();
                    try {
                        ArrayList lista = new ArrayList();
                        lista = obtenerArrayDeBandejas();
                        String vNroRecep =
                            DBRecepCiega.ingresaDatosTrans_02("0", txtcodtrans.getText().trim(), txtHojaRes.getText().trim(),
                                                              //ASOSA - 24/07/2014
                                txtCodValija.getText().trim(), //ASOSA - 25/07/2014
                                lista) //ASOSA - 25/07/2014
                            .trim();
                        FarmaUtility.aceptarTransaccion();
                        //UtilityRecepCiega.imprimeVoucherTransportista(this,vNroRecep,txtNombre);
                        UtilityRecepCiega.imprimeVoucherTransportista_02(this, vNroRecep,
                                                                         txtNombre,true); //ASOSA - 24/07/2014

                        cerrarVentana(true);
                    } catch (SQLException sql) {
                        FarmaUtility.liberarTransaccion();
                        log.error("", sql);
                        FarmaUtility.showMessage(this,
                                                 "Error al ingresar los Datos del Transportista: " + sql.getMessage().substring(9,
                                                                                                                                sql.getMessage().indexOf("ORA",
                                                                                                                                                         2)),
                                                 txtNombre);
                    }
                    //FarmaUtility.showMessage(this,"Datos guardados correctamente.",txtPrecintos);
                    //cerrarVentana(true);
                    //mostrarGuias();
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F9) { //ASOSA - 24/07/2014
            eliminarUltimoRegistro();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    /**
     * Obtener array de las bandejas agregadas para la hoja de resumen.
     * @author ASOSA
     * @since 25/07/2014
     * @return
     */
    private ArrayList obtenerArrayDeBandejas() {
        ArrayList list = new ArrayList();
        for (int c = 0; c < tableModelBandejas.getRowCount(); c++) {
            String codigo = FarmaUtility.getValueFieldArrayList(tableModelBandejas.data, c, 0).toString().trim();
            list.add(codigo);
        }
        return list;
    }

    private void getDatosTransportista() {

        VariablesRecepCiega.vNombreTrans = txtNombre.getText().trim();

        VariablesRecepCiega.vPlacaUnidTrans = txtPlaca.getText().trim();
        VariablesRecepCiega.vCantBultos = txtBultos.getText().trim();
        /*
         VariablesRecepCiega.vCantPrecintos = txtPrecintos.getText().trim();
        */
        // AAMPUERO 15.04.2014
        VariablesRecepCiega.vCantBandejas = txtBandejas.getText().trim();

        //JMIRANDA 05.03.10
        VariablesRecepCiega.vGlosa = txtGlosa.getText().trim();

        log.debug("VariablesRecepCiega.vNombreTrans " + VariablesRecepCiega.vNombreTrans);
        log.debug("VariablesRecepCiega.vHoraTrans " + VariablesRecepCiega.vHoraTrans);
        log.debug("VariablesRecepCiega.vPlacaUnidTrans " + VariablesRecepCiega.vPlacaUnidTrans);
        log.debug("VariablesRecepCiega.vCantBultos " + VariablesRecepCiega.vCantBultos);
        log.debug("VariablesRecepCiega.vCantPrecintos " + VariablesRecepCiega.vCantPrecintos);

    }


    /**
     * Se valida ingreso de quimico (Administrador Local)
     */
    private void cargaLogin() {
        //se guarda datos
        VariablesRecepCiega.vNuSecUsu = FarmaVariables.vNuSecUsu;
        VariablesRecepCiega.vIdUsu = FarmaVariables.vIdUsu;
        VariablesRecepCiega.vNomUsu = FarmaVariables.vNomUsu;
        VariablesRecepCiega.vPatUsu = FarmaVariables.vPatUsu;
        VariablesRecepCiega.vMatUsu = FarmaVariables.vMatUsu;

        DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);

        if (FarmaVariables.vAceptar) {

            if (!FarmaVariables.vNuSecUsu.equalsIgnoreCase(VariablesRecepCiega.vNuSecUsu)) {
                FarmaUtility.showMessage(this, "El usuario no es el mismo al logueado anteriormente. Verificar!!!",
                                         txtNombre);
                cerrarVentana(false);
            }

            // se sete datos originales
            FarmaVariables.vNuSecUsu = VariablesRecepCiega.vNuSecUsu;
            FarmaVariables.vIdUsu = VariablesRecepCiega.vIdUsu;
            FarmaVariables.vNomUsu = VariablesRecepCiega.vNomUsu;
            FarmaVariables.vPatUsu = VariablesRecepCiega.vPatUsu;
            FarmaVariables.vMatUsu = VariablesRecepCiega.vMatUsu;

            FarmaVariables.dlgLogin = dlgLogin;
            FarmaVariables.vAceptar = false;
        } else
            cerrarVentana(false);
    }


    private void mostrarGuias() {

        log.debug("MOSTRANDO GUIAS");

        DlgGuiasPendientes dlgGuias = new DlgGuiasPendientes(myParentFrame, "", true);
        dlgGuias.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cerrarVentana(true);
            //FarmaVariables.vAceptar = false;
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


    private boolean datosValidados() {
        boolean retorno = true;
        if (txtcodtrans.getText().trim().length() < 1 ||
            txtnomtrans.getText().trim().length() < 1) { //ASOSA, 22.04.2010
            FarmaUtility.showMessage(this, "Ingrese codigo de empresa de transporte", txtcodtrans);
            retorno = false;
        } else if (!verificarCodigo()) { //ASOSA, 22.04.2010
            retorno = false;
        } else if (FarmaVariables.vAceptar == false) {
            FarmaUtility.showMessage(this, "Seleccione un codigo valido", txtcodtrans);
            retorno = false;
        } else if (txtNombre.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese nombre del transportista.", txtNombre);
            retorno = false;
        } else if (txtPlaca.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese Placa.", txtPlaca);
            retorno = false;
        } else if (txtBultos.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese cantidad de bultos.", txtBultos);
            retorno = false;
        } else if (txtBandejas.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese cantidad de bandejas.", txtBandejas);
            retorno = false;
            // AAMPUERO 14.04.2014

            //} else if (txtPrecintos.getText().trim().length() < 1) {
            //   FarmaUtility.showMessage(this, "Ingrese cantidad de precintos.",
            //                          txtPrecintos);
            //retorno = false;

        } else if (!validaBultos()) {
            //FarmaUtility.showMessage(this, "Debe ingresar bultos con valor mayor a cero.", txtBultos);
            retorno = false;
        } else if (!validaBandejas()) {
            //FarmaUtility.showMessage(this, "Debe ingresar bandejas con valor mayor a cero.", txtPrecintos);
            retorno = false;
        }

        // aampuero 14.04.2014 - validaBandejas x validaPrecintos
        //
        //} else if (!validaPrecintos()) {
        //FarmaUtility.showMessage(this, "Debe ingresar precintos con valor mayor a cero.", txtPrecintos);
        //    retorno = false;
        //}

        /*else if(!txtPlaca.getText().matches("[0-9]+[-]+?[A-Z]*")){
          FarmaUtility.showMessage(this, "Formato de Placa no valido.", txtPlaca);
          retorno=false;
      }*/


        /*  if(!validarPlaca(txtPlaca.getText().trim())&&txtPlaca.getText().trim().length()>0){
          FarmaUtility.showMessage(this, "Formato de Placa no valido.", txtPlaca);
          retorno=false;
          }*/

        return retorno;
    }


    /*public static boolean validarHora(String hora ) {
        log.debug("hora...........");
           boolean b = Pattern.matches("[0-9:]", hora);
        log.debug("b."+b);
           return b;
    }*/

    /*public static boolean validarPlaca( String placa ) {
    log.debug("placa...........");
           boolean b = Pattern.matches("^[a-zA-Z0-9%-]$", placa);
              log.debug("b."+b);
           return b;
          }*/

    //boolean b = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email);

    /*  private boolean validarHora() {
        boolean retorno = true;
        if (!FarmaUtility.isHoraMinutoValida(this, txtHora.getText(),
                                             "La hora ingresada no tiene el formato correcto")) {
            FarmaUtility.moveFocus(txtHora);
            retorno = false;
        }
        if (FarmaUtility.validarHora(txtHora.getText().trim())) {
            FarmaUtility.showMessage(this,
                                     "La hora ingresada no tiene el es formato.",
                                     txtHora);
            retorno = false;
        }
        return retorno;
    }*/

    private boolean validaBultos() {
        boolean valor = false;
        String cantIngreso = txtBultos.getText().trim();
        if (FarmaUtility.isInteger(cantIngreso)) {
            if (Integer.parseInt(cantIngreso) > 0) {
                valor = true;
            } else {
                valor = false;
                FarmaUtility.showMessage(this, "Ingrese Cantidad de Bultos mayor a cero.", txtBultos);
            }
        } else {
            valor = false;
            FarmaUtility.showMessage(this, "Debe ingresar cantidad entera.", txtBultos);
        }

        return valor;
    }

    // AAMPUERO 15.04.2014 - CAMBIA POR PRECINTOS

    private boolean validaBandejas() {
        boolean valor = false;
        String cantIngreso = txtBandejas.getText().trim();
        if (FarmaUtility.isInteger(cantIngreso)) {
            if (Integer.parseInt(cantIngreso) >= 0) {
                valor = true;
            } else {
                valor = false;
                FarmaUtility.showMessage(this, "Ingrese Cantidad de Bandejas mayor o igual a cero.", txtBandejas);
            }
        } else {
            valor = false;
            FarmaUtility.showMessage(this, "Debe ingresar cantidad entera.", txtBandejas);
        }
        return valor;
    }

    /*
    private boolean validaPrecintos() {
        boolean valor = false;
        String cantIngreso = txtPrecintos.getText().trim();
        if (FarmaUtility.isInteger(cantIngreso)) {
            if (Integer.parseInt(cantIngreso) >= 0) {
                valor = true;
            } else {
                valor = false;
                FarmaUtility.showMessage(this,
                                         "Ingrese Cantidad de Precintos mayor o igual a cero.",
                                         txtPrecintos);
            }
        } else {
            valor = false;
            FarmaUtility.showMessage(this, "Debe ingresar cantidad entera.",
                                     txtPrecintos);
        }
        return valor;
    }
    */

    private void cargarFecha() {
        try {
            FechaInicio = FarmaSearch.getFechaHoraBD(1);
        } catch (SQLException sql) {
            log.error("", sql);
        }
    }


    private void txtNombre_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtPlaca);
        }
        chkKeyPressed(e);
    }

    private void txtPlaca_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtBultos);
        }
        chkKeyPressed(e);
    }

    private void txtBultos_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtBandejas);
        }
        chkKeyPressed(e);
    }

    private void txtBultos_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtBultos, e);
    }


    /*
    private void txtPrecintos_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtBultos, e);
        //FarmaUtility.admitirDigitosDecimales(txtPrecintos, e);
    }
    */

    private void txtBandejas_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtBandejas, e);
    }

    /*
    private void txtPrecintos_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtGlosa);
        }
        chkKeyPressed(e);
    }
    */

    private void txtBandejas_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCodValija);
        }
        chkKeyPressed(e);
    }

    private void txtNombre_keyTyped(KeyEvent e) {

        FarmaUtility.admitirLetras(txtNombre, e);
    }

    private void txtGlosa_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtHojaRes);
        }
        chkKeyPressed(e);
    }

    //INI ASOSA - 21/07/2014

    private void txtHojaRes_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (existeTextoCajaTexto(txtHojaRes.getText())) {
                if (validarNuevoIngresoHojaRes()) {
                    if (!existsHojaResumen()) {
                        if (validarCodigoHojaRes()) {
                            FarmaUtility.moveFocus(txtNroBandeja);
                            cont = 0;
                        } else {
                            if (cont == 0) {
                                String texto =
                                    FarmaUtility.ShowInput(this, "Ingrese nuevamente el código de Hoja Resumen");
                                if (texto.equalsIgnoreCase(txtHojaRes.getText())) {
                                    txtHojaRes.setText(texto);
                                    FarmaUtility.moveFocus(txtNroBandeja);
                                    cont = cont + 1;
                                } else {
                                    txtHojaRes.setText("");
                                    FarmaUtility.showMessage(this, "Número incorrecto, comience ingresando de nuevo",
                                                             txtHojaRes);
                                    cont = 0;
                                }
                            } else {
                                FarmaUtility.moveFocus(txtHojaRes);
                                cont = 0;
                            }
                        }
                    } else {
                        FarmaUtility.showMessage(this, "El nro. de Hoja de Resumen ya existe, verifique sus datos.",
                                                 txtHojaRes);
                    }
                } else {
                    FarmaUtility.moveFocus(txtNroBandeja);
                }
            } else {
                FarmaUtility.showMessage(this, "Ingrese nro. de Hoja de Resumen", txtHojaRes);
            }
        } else {
            chkKeyPressed(e);
        }
    }

    private boolean validarNuevoIngresoHojaRes() {
        boolean flag = true;
        if (flagExisteHojaResumen || cont == 1) {
            flag =
JConfirmDialog.rptaConfirmDialogDefaultNo(this, "La hoja de resumen ya fue ingresada, ¿Desea cambiarla?");
        }
        if (flag) {
            limpiarLista();
        }
        return flag;
    }

    private void eliminarUltimoRegistro() {
        int cantidad = tableModelBandejas.getRowCount();
        if (cantidad > 0) {
            tableModelBandejas.deleteRow(cantidad - 1);
            tblBandejas.repaint();
        } else {
            FarmaUtility.showMessage(this, "No hay bandejas para eliminar de la lista", null);
        }
    }

    private void txtNroBandeja_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (existeTextoCajaTexto(txtNroBandeja.getText())) {
                if (validarCantidadDigitos()) {
                    if (existeTextoCajaTexto(txtHojaRes.getText())) {
                        if (!validarRepetidos()) {
                            if (flagExisteHojaResumen) {
                                if (validarCodigoBandeja()) {
                                    agregarCodigoBandeja();
                                } else {
                                    FarmaUtility.showMessage(this,
                                                             "El número de bandeja no existe para la Hoja de Resumen ingresada",
                                                             txtNroBandeja);
                                }
                            } else {
                                agregarCodigoBandeja();
                            }
                        } else {
                            FarmaUtility.showMessage(this, "El número de bandeja ya fue ingresado", txtNroBandeja);
                        }
                    } else {
                        FarmaUtility.showMessage(this, "Ingrese número de Hoja de Resumen", txtHojaRes);
                    }
                } else {
                    FarmaUtility.showMessage(this, "El numero de bandeja debe tener " + MAX_DIGITOS + " digitos",
                                             txtNroBandeja);
                }
            } else {
                FarmaUtility.showMessage(this, "Ingrese número de Bandeja", txtNroBandeja);
            }
        }
        chkKeyPressed(e);
    }

    private boolean validarCantidadDigitos() {
        boolean flag = false;
        if (txtNroBandeja.getText().length() == MAX_DIGITOS) {
            flag = true;
        }
        return flag;
    }

    private void limpiarLista() {
        tableModelBandejas.clearTable();
        tblBandejas.repaint();
    }

    private boolean validarCodigoHojaRes() {
        boolean flag = false;
        String pHojaResumen = txtHojaRes.getText();
        try {
            UtilityRecepCiega.obtenerDocTransporte(tableModelHojaRes, pHojaResumen);
            if (tableModelHojaRes != null && tableModelHojaRes.getRowCount() == 1) {
                nroHojaRes = FarmaUtility.getValueFieldArrayList(tableModelHojaRes.data, 0, 0);
                cantBandejas = FarmaUtility.getValueFieldArrayList(tableModelHojaRes.data, 0, 1);
                log.info("nroHojaRes: " + nroHojaRes);
                log.info("cantBandejas: " + cantBandejas);
                flagExisteHojaResumen = true;
                flag = true;
            } else {
                flagExisteHojaResumen = false;
            }
        } catch (SQLException e) {
            log.error("",e);
        }
        return flag;
    }

    private boolean validarRepetidos() {
        boolean flag = false;
        String nroBandeja = txtNroBandeja.getText().trim();
        for (int c = 0; c < tableModelBandejas.getRowCount(); c++) {
            String codigo = FarmaUtility.getValueFieldArrayList(tableModelBandejas.data, c, 0).toString();
            if (codigo.equals(nroBandeja)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private boolean existeTextoCajaTexto(String texto) {
        boolean flag = true;
        if (texto.trim().equals("")) {
            flag = false;
        }
        return flag;
    }

    private boolean validarCodigoBandeja() {
        boolean flag = false;
        String nroBandeja = txtNroBandeja.getText().trim();
        try {
            flag = UtilityRecepCiega.buscarBandejaHojaResumen(nroHojaRes, nroBandeja);
        } catch (SQLException e) {
            log.error("",e);
        }
        return flag;
    }

    private boolean existsHojaResumen() {
        boolean flag = false;
        String nroHojaRes = txtHojaRes.getText().trim();
        try {
            flag = UtilityRecepCiega.buscarHojaResumenGrabada(nroHojaRes,false);
        } catch (SQLException e) {
            log.error("",e);
        }
        return flag;
    }

    private boolean validarCantidadBultos() {
        boolean flag = false;
        boolean flagIgual = false;
        if (flagExisteHojaResumen) {
            if (txtBultos.getText().trim().equals(cantBandejas.trim())) {
                flagIgual = true;
            }
        } else {
            int cantidad = tableModelBandejas.getRowCount();
            if (Integer.parseInt(txtBultos.getText().trim()) == cantidad) {
                flagIgual = true;
            }
        }
        if (flagIgual) {
            flag = true;
        } else {
            FarmaUtility.showMessage(this,
                                     "La cantidad de bultos ingresados no es igual a la cantidad de bultos escaneados, por favor verifique",
                                     txtBultos);
        }
        return flag;
    }

    private void agregarCodigoBandeja() {
        String nroBandeja = txtNroBandeja.getText().trim();
        ArrayList lista = new ArrayList();
        lista.add(nroBandeja);
        tableModelBandejas.insertRow(lista);
        tblBandejas.repaint();
        txtNroBandeja.setText("");
    }

    //FIN ASOSA - 21/07/2014

    private void txtcodtrans_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            /*String tipoMaestro = FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,cmbTipo.getSelectedIndex());
            if(tipoMaestro.trim().length() == 0)
              FarmaUtility.showMessage(this,"Seleccione un Tipo de Origen.",txtcodtrans);
            else
            {*/
            /*if(tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL))
                VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_LOCAL;
              else if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
                VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_MATRIZ;
              else if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR))
                VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_PROVEEDOR;*/
            VariablesPtoVenta.vTipoMaestro = "15";
            validaCodigo(txtcodtrans, txtnomtrans, VariablesPtoVenta.vTipoMaestro);
            //}
            if (!txtcodtrans.getText().trim().equals("")) {
                FarmaUtility.moveFocus(txtNombre);
            }
        } else {
            chkKeyPressed(e);
        }
    }

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
                    sql.getMessage(), txtcodtrans);
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

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtcodtrans);
    }

    private void jButtonLabel1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtnomtrans_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /**
     * Validar por ultima ves el codigo de empresa de transporte
     * @author ASOSA
     * @since 22.04.2010
     * @return
     */
    private boolean verificarCodigo() {
        ArrayList list02 = new ArrayList();
        VariablesPtoVenta.vTipoMaestro = "15";
        VariablesPtoVenta.vCodMaestro = txtcodtrans.getText().trim();
        buscaCodigoListaMaestro(list02);
        boolean flag = false;
        if (list02.size() == 0) {
            FarmaUtility.showMessage(this, "Codigo No Encontrado", txtcodtrans);
            FarmaVariables.vAceptar = false;
            flag = false;
        } else if (list02.size() == 1) {
            FarmaVariables.vAceptar = true;
            flag = true;
        } else {
            FarmaUtility.showMessage(this, "Se encontro mas de un registro.Verificar!!!", txtcodtrans);
            FarmaVariables.vAceptar = false;
            flag = false;
        }
        return flag;
    }

    private void lblHojaRes_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtHojaRes);
    }

    private void lblCodValija_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodValija);
    }

    private void lblNroBandeja_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNroBandeja);
    }

    private void txtNroBandeja_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNroBandeja, e);
    }

    //INI ASOSA - 25/07/2014

    private void txtCodValija_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (validarCodigoValija()) {
                FarmaUtility.moveFocus(txtGlosa);
            }
        } else {
            chkKeyPressed(e);
        }
    }

    private boolean validarCodigoValija() {
        boolean flag = false;
        String codigo = txtCodValija.getText().trim();
        if (codigo.length() != 0) { //si no ingrese valija paso al siguiente campo porque no es obligatorio.
            if (codigo.length() == LENGTH_COD_VALIJA_01 || codigo.length() == LENGTH_COD_VALIJA_02) {

                if (codigo.length() == LENGTH_COD_VALIJA_01) {
                    String letras = codigo.substring(0, 3);
                    String numeros = codigo.substring(3, 10);
                    log.info("letras: " + letras);
                    log.info("numeros: " + numeros);
                    if ((letras.equals(ABREV_MFA) || letras.equals(ABREV_BTL)) && (FarmaUtility.isInteger(numeros))) {
                        flag = true;
                    } else {
                        FarmaUtility.showMessage(this,
                                                 "Formato del código invalido. \nEjemplo:\n - MFA1234567890\n - BTL1234567890\n - FASA1234567890",
                                                 txtCodValija);
                    }
                }

                if (codigo.length() == LENGTH_COD_VALIJA_02) {
                    String letras = codigo.substring(0, 4);
                    String numeros = codigo.substring(4, 10);
                    log.info("letras: " + letras);
                    log.info("numeros: " + numeros);
                    if (letras.equals(ABREV_FASA) && (FarmaUtility.isInteger(numeros))) {
                        flag = true;
                    } else {
                        FarmaUtility.showMessage(this,
                                                 "Formato del código invalido. \nEjemplo:\n - MFA1234567890\n - BTL1234567890\n - FASA1234567890",
                                                 txtCodValija);
                    }
                }

            } else {
                FarmaUtility.showMessage(this,
                                         "El código de valija debe de tener " + LENGTH_COD_VALIJA_01 + " o " + LENGTH_COD_VALIJA_02 +
                                         " dígitos", txtCodValija);
            }
        } else {
            flag = true;
        }
        return flag;
    }


    private void txtCodValija_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetrasNumeros(txtCodValija, e);
    }

    //INI ASOSA - 30/07/2014


}
