package mifarma.ptoventa.ce;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.DlgNuevoCobro;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgCambioFormaPagoNew.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      03.06.2010   Creación<br>
 * <br>
 *
 */
public class DlgCambioFormaPago extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgCambioFormaPago.class);

    //Variables  para columnas a buscar
    private int COL_PEDDIARIO = 7;
    private int COL_PEDVTA = 0;
    private int COL_FECOBRAR = 3;
    private int COL_PEDIDO = 0;
    private int COL_SUMA = 7;
    private int COL_ORDEN = 0;
    private FarmaTableModel tableModelDetalleVentas;
    private FarmaTableModel tableModelFormaPago;
    private Frame myParentFrame;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JButton btnBuscar = new JButton();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnListado = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblDetalleVentas = new JTable();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JLabel lblRegistros = new JLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblListar = new JLabelFunction();

    private JLabelFunction lblF1 = new JLabelFunction();
    private JTextFieldSanSerif txtNumCorre = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTable tblFormaPago = new JTable();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JButtonLabel lblNumPedVta_t = new JButtonLabel();
    private JButtonLabel lblMonto_t = new JButtonLabel();

    public DlgCambioFormaPago() {
        this(null, "", false);
    }

    public DlgCambioFormaPago(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(729, 463));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Cambio de Foma de Pago");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlCriterioBusqueda.setBounds(new Rectangle(5, 5, 715, 50));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(590, 15, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        pnlTitulo.setBounds(new Rectangle(5, 60, 715, 20));
        btnListado.setText("Listado de Ventas :");
        btnListado.setBounds(new Rectangle(10, 0, 200, 20));
        btnListado.setMnemonic('V');
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(5, 80, 715, 185));
        tblDetalleVentas.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                tblDetalleVentas_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                tblDetalleVentas_keyPressed(e);
            }
        });
        pnlResultados.setBounds(new Rectangle(5, 265, 715, 20));
        lblRegistros.setText("0");
        lblRegistros.setBounds(new Rectangle(640, 0, 35, 20));
        lblRegistros.setFont(new Font("SansSerif", 1, 11));
        lblRegistros.setForeground(Color.white);
        lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(570, 0, 70, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        lblEsc.setBounds(new Rectangle(495, 410, 135, 20));
        lblEsc.setText("[ F5 ] Listar Todos");
        lblListar.setBounds(new Rectangle(635, 410, 85, 20));
        lblListar.setText("[ ESC ] Cerrar");

        lblF1.setBounds(new Rectangle(5, 410, 200, 20));
        lblF1.setText("[ F3 ] Cambiar Forma Pago");
        txtNumCorre.setBounds(new Rectangle(120, 15, 150, 20));
        txtNumCorre.setLengthText(10);
        txtNumCorre.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumCorre_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtNumCorre_keyTyped(e);
            }

            public void keyReleased(KeyEvent e) {
                txtNumCorre_keyReleased(e);
            }
        });
        txtMonto.setBounds(new Rectangle(445, 15, 100, 20));
        txtMonto.setLengthText(9);
        txtMonto.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtMonto_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtMonto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtMonto_keyReleased(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(5, 295, 715, 20));
        jScrollPane2.setBounds(new Rectangle(5, 315, 715, 90));
        tblFormaPago.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblFormaPago_keyPressed(e);
            }
        });
        jButtonLabel1.setText("Listado de Formas Pago :");
        jButtonLabel1.setBounds(new Rectangle(5, 0, 155, 20));
        jButtonLabel1.setMnemonic('F');
        jButtonLabel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        lblNumPedVta_t.setText("Num Ped :");
        lblNumPedVta_t.setBounds(new Rectangle(30, 15, 60, 20));
        lblNumPedVta_t.setMnemonic('N');
        lblNumPedVta_t.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNumPedVta_t_actionPerformed(e);
            }
        });
        lblMonto_t.setText("Monto :");
        lblMonto_t.setBounds(new Rectangle(380, 15, 45, 20));
        lblMonto_t.setMnemonic('M');
        lblMonto_t.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblMonto_t_actionPerformed(e);
            }
        });
        jScrollPane1.getViewport();
        pnlCriterioBusqueda.add(lblMonto_t, null);
        pnlCriterioBusqueda.add(lblNumPedVta_t, null);

        pnlCriterioBusqueda.add(txtMonto, null);
        pnlCriterioBusqueda.add(txtNumCorre, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        jScrollPane2.getViewport().add(tblFormaPago, null);
        jPanelWhite1.add(jScrollPane2, null);
        jPanelTitle1.add(jButtonLabel1, null);
        jPanelTitle1.add(lblRegistros, null);
        jPanelTitle1.add(lblRegsitros_T, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelWhite1.add(lblF1, null);
        jPanelWhite1.add(lblEsc, null);
        jPanelWhite1.add(lblListar, null);

        jPanelWhite1.add(pnlResultados, null);
        jScrollPane1.getViewport().add(tblDetalleVentas, null);
        jPanelWhite1.add(jScrollPane1, null);
        pnlTitulo.add(btnListado, null);
        jPanelWhite1.add(pnlTitulo, null);
        jPanelWhite1.add(pnlCriterioBusqueda, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        initTableListaDetalleVentas();
    }

    private void initTableListaDetalleVentas() {

        tblDetalleVentas.getTableHeader().setReorderingAllowed(false);
        tblDetalleVentas.getTableHeader().setResizingAllowed(false);
        tblFormaPago.getTableHeader().setReorderingAllowed(false);
        tblFormaPago.getTableHeader().setResizingAllowed(false);

        tableModelDetalleVentas =
                new FarmaTableModel(ConstantsCajaElectronica.columnsListaCambioFormaPago, ConstantsCajaElectronica.defaultValuesListaCambioFormaPago,
                                    0);
        FarmaUtility.initSimpleList(tblDetalleVentas, tableModelDetalleVentas,
                                    ConstantsCajaElectronica.columnsListaCambioFormaPago);

        tableModelFormaPago =
                new FarmaTableModel(ConstantsCajaElectronica.columnsListaFormasPago, ConstantsCajaElectronica.defaultValuesListaFormasPago,
                                    0);
        FarmaUtility.initSimpleList(tblFormaPago, tableModelFormaPago,
                                    ConstantsCajaElectronica.columnsListaFormasPago);

    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNumCorre);
        busqueda();
    }

    private boolean validarCampos(String val) {
        boolean retorno = true;
        if (txtNumCorre.getText().trim().equals("") && val.equalsIgnoreCase("S")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Correlativo.", txtNumCorre);
            retorno = false;
        } else if (txtMonto.getText().trim().equals("") && val.equalsIgnoreCase("S")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Monto.", txtMonto);
            retorno = false;
        } else if (!FarmaUtility.validateDecimal(this, txtMonto, "Debe ingresar un Monto Válido.", false) &&
                   txtMonto.getText().trim().length() > 0) {
            retorno = false;
        }
        return retorno;

    }

    private void buscaDetalleVentas(String pFechaInicio, String pFechaFin) {
        cargaDetalleVentas(pFechaInicio, pFechaFin);
    }

    private void cargaDetalleVentas(String pFechaInicio, String pFechaFin) {
        try {
            VariablesCajaElectronica.vNumPedVta = txtNumCorre.getText().trim();
            VariablesCajaElectronica.vMonto = txtMonto.getText().trim();

            DBCajaElectronica.cargaListaRegistroVentas(tableModelDetalleVentas, pFechaInicio, pFechaFin,
                                                       VariablesCajaElectronica.vSecMovCaja,
                                                       VariablesCajaElectronica.vNumPedVta,
                                                       VariablesCajaElectronica.vMonto,
                                                       VariablesCajaElectronica.vIndTipo);
            lblRegistros.setText("" + tblDetalleVentas.getRowCount());
            VariablesCajaElectronica.vIndTipo = "%";
            if (tblDetalleVentas.getRowCount() == 0) {
                FarmaUtility.showMessage(this, "La búsqueda no arrojó resultados.", txtNumCorre);
            } else {
                FarmaUtility.ordenar(tblDetalleVentas, tableModelDetalleVentas, 3, FarmaConstants.ORDEN_ASCENDENTE);
                moverSobreTablaDetallePedido();
                FarmaUtility.moveFocusJTable(tblDetalleVentas);
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el detalle de Ventas : \n" +
                    sql.getMessage(), txtNumCorre);
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (cargaLogin()) {
                VariablesCajaElectronica.vNumPedPendiente =
                        (String)tableModelDetalleVentas.getValueAt(tblDetalleVentas.getSelectedRow(), COL_PEDDIARIO);
                VariablesCajaElectronica.vNumPedPendiente =
                        FarmaUtility.completeWithSymbol(VariablesCajaElectronica.vNumPedPendiente, 4, "0", "I");
                VariablesCajaElectronica.vNumPedVta =
                        (String)tblDetalleVentas.getValueAt(tblDetalleVentas.getSelectedRow(), COL_PEDVTA);
                VariablesCaja.vFecPedACobrar =
                        (String)tblDetalleVentas.getValueAt(tblDetalleVentas.getSelectedRow(), COL_FECOBRAR);
                cambiaFormaPago();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            busqueda();
            FarmaUtility.moveFocus(txtNumCorre);

        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
        } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            moverSobreTablaDetallePedido();
        }
    }

    private void cargarFormasPago() {

        try {

            DBCajaElectronica.cargaListaFormasPago(tableModelFormaPago, VariablesCajaElectronica.vNumPedVta);
            FarmaUtility.ordenar(tblFormaPago, tableModelFormaPago, COL_ORDEN, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar las Formas de Pago:\n " + sql.getMessage(), null);
        }
    }


    private void busqueda() {
        String FechaInicio = VariablesCajaElectronica.vFechaDia.trim();
        String FechaFin = VariablesCajaElectronica.vFechaDia.trim();

        if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
            char primerkeyCharFI = FechaInicio.charAt(0);
            char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length() - 1);
            char primerkeyCharFF = FechaFin.charAt(0);
            char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

            if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                buscaDetalleVentas(FechaInicio, FechaFin);
            } else
                FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", null);
        } else
            FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", null);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        buscarPedido();
        FarmaUtility.moveFocus(txtNumCorre);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void txtNumCorre_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblDetalleVentas, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtNumCorre.getText().length() > 0 && tblDetalleVentas.getRowCount() > 0) {

                txtNumCorre.setText(FarmaUtility.caracterIzquierda(txtNumCorre.getText(), 10, "0"));
                FarmaGridUtils.aceptarTeclaPresionada(e, tblDetalleVentas, txtNumCorre, 0); //up or down
                if (!(FarmaUtility.findTextInJTable(tblDetalleVentas, txtNumCorre.getText().trim(), 0, 0))) {
                    FarmaUtility.showMessage(this, "Pedido No Encontrado según Criterio de Búsqueda !!!", txtNumCorre);
                    return;
                }
                txtNumCorre_keyReleased(e);
            }
            FarmaUtility.moveFocus(txtMonto);
        } else
            chkKeyPressed(e);
    }

    private void txtNumCorre_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumCorre, e);
    }

    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    }


    private void btnListado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblDetalleVentas);
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        cargarCabecera();
        FarmaUtility.moveFocus(tblFormaPago);
    }

    private void cargarCabecera() {
        VariablesCajaElectronica.vFechaPed =
                FarmaUtility.getValueFieldArrayList(tableModelDetalleVentas.data, tblDetalleVentas.getSelectedRow(),
                                                    3);
        VariablesCajaElectronica.vEstPed =
                FarmaUtility.getValueFieldArrayList(tableModelDetalleVentas.data, tblDetalleVentas.getSelectedRow(),
                                                    5);
        VariablesCajaElectronica.vNumPedVta =
                FarmaUtility.getValueFieldArrayList(tableModelDetalleVentas.data, tblDetalleVentas.getSelectedRow(),
                                                    0);

    }

    private void tblDetalleVentas_keyReleased(KeyEvent e) {
        int row = tblDetalleVentas.getSelectedRow();
        if (row > -1) {

            VariablesCajaElectronica.vNumPedVta = FarmaUtility.getValueFieldJTable(tblDetalleVentas, row, COL_PEDIDO);
            cargarFormasPago();
            lblRegistros.setText(tblFormaPago.getRowCount() + "");
        }
    }

    private void limpiar() {

        VariablesCajaElectronica.vEstPed = "";
        VariablesCajaElectronica.vFechaPed = "";
        VariablesCajaElectronica.vNumPedVta = "";
        VariablesCajaElectronica.vNumPedPendiente = "";
        VariablesCaja.vFecPedACobrar = "";

    }

    private void tblFormaPago_keyPressed(KeyEvent e) {

    }

    private void tblDetalleVentas_keyPressed(KeyEvent e) {

        chkKeyPressed(e);
    }

    private void txtNumCorre_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblDetalleVentas, txtNumCorre, 0);
    }

    private void txtMonto_keyReleased(KeyEvent e) {
        //FarmaGridUtils.buscarDescripcion(e,tblDetalleVentas,txtMonto,6);
    }


    private void lblNumPedVta_t_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNumCorre);
    }

    private void lblMonto_t_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMonto);
    }

    private void cambiaFormaPago() {
        VariablesCajaElectronica.indExitoCambioFP = false;

        DlgNuevoCobro dlgNuevoCobro = new DlgNuevoCobro(myParentFrame, "", true);
        dlgNuevoCobro.setIndPedirLogueo(false);
        //dlgNuevoCobro.setIndPantallaCerrarAnularPed(true);
        //dlgNuevoCobro.setIndPantallaCerrarCobrarPed(true);
        dlgNuevoCobro.setIndTipoCobro(ConstantsCaja.COBRO_CAJA_ELECTRONICA);
        dlgNuevoCobro.setVisible(true);


        if (FarmaVariables.vAceptar) {

            if (VariablesCajaElectronica.indExitoCambioFP) {
                FarmaUtility.showMessage(this, "Se realizo correctamente el cambio de la forma de pago del pedido",
                                         null);
            }

            cargarFormasPago();
            FarmaUtility.moveFocus(tblDetalleVentas);
            limpiar();
        }
    }

    private void txtMonto_keyPressed(KeyEvent e) {

        FarmaGridUtils.aceptarTeclaPresionada(e, tblDetalleVentas, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            buscarPedido();
        else
            chkKeyPressed(e);

    }


    private void buscarPedido() {

        VariablesCajaElectronica.vNumPedVta = txtNumCorre.getText().trim();
        VariablesCajaElectronica.vMonto = txtMonto.getText().trim();

        if (VariablesCajaElectronica.vNumPedVta.equals("") && VariablesCajaElectronica.vMonto.equals("")) {
            FarmaUtility.showMessage(this, "Ingrese algun de los datos para la Busqueda", null);
            FarmaUtility.moveFocus(txtNumCorre);
        } else {

            try {
                DBCajaElectronica.cargaListaRegVentas(tableModelDetalleVentas, VariablesCajaElectronica.vSecMovCaja,
                                                      VariablesCajaElectronica.vNumPedVta,
                                                      VariablesCajaElectronica.vMonto,
                                                      VariablesCajaElectronica.vIndTipo);
                lblRegistros.setText("" + tblDetalleVentas.getRowCount());
                VariablesCajaElectronica.vIndTipo = "%";
                if (tblDetalleVentas.getRowCount() == 0) {
                    tableModelFormaPago.clearTable(); //ASOSA, 17.06.2010 - Faltaba integrar de antes cambios de JQUISPE.
                    FarmaUtility.showMessage(this, "La búsqueda no arrojó resultados.", txtNumCorre);
                    FarmaUtility.moveFocus(txtNumCorre);
                } else {
                    FarmaUtility.ordenar(tblDetalleVentas, tableModelDetalleVentas, 3,
                                         FarmaConstants.ORDEN_ASCENDENTE);
                    FarmaUtility.moveFocusJTable(tblDetalleVentas);
                }
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Error al listar el detalle de Ventas : \n" +
                        sql.getMessage(), txtNumCorre);
                cerrarVentana(false);
            }
        }
    }

    private void moverSobreTablaDetallePedido() {
        int row = tblDetalleVentas.getSelectedRow();

        if (row > -1) {
            VariablesCajaElectronica.vNumPedVta = FarmaUtility.getValueFieldJTable(tblDetalleVentas, row, COL_PEDIDO);
            cargarFormasPago();
            lblRegistros.setText(tblFormaPago.getRowCount() + "");
        }
    }

    private boolean cargaLogin() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        boolean bRetorno = false;
        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, "Validacion de Usuario", true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
            bRetorno = FarmaVariables.vAceptar;
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al validar rol de usuario \n : " + e.getMessage(),
                                     tableModelFormaPago);
        } finally {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
        }

        return bRetorno;
    }
}
