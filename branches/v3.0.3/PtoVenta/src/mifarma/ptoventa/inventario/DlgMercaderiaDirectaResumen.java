package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;

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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMercaderiaDirectaResumen extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgMercaderiaDirectaResumen.class);

    FarmaTableModel tableModelOrdenCompraResumen;
    Frame myParentFrame;
    private boolean verListaProductos = true;
    private boolean esGuiaRecepcion = false;
    ArrayList arrayProductos = new ArrayList();
    ArrayList arrayOrdenCompra = new ArrayList();
    int ncantOrdComp = 0;
    String strCodigoOrdCompDetalle = "1";
    String vEstadoNota;


    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JScrollPane scrResumenOrden = new JScrollPane();
    private JPanel pnlHeader = new JPanel();
    private JButton btnResumenOrden = new JButton();
    private JTable tblResumenOrden = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelHeader pnlOrdenCompraResume = new JPanelHeader();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite lblTotal_T = new JLabelWhite();
    private JLabelWhite lblTotal = new JLabelWhite();
    private JButtonLabel lblFecha = new JButtonLabel();
    private JLabel txtFecha = new JLabel();
    private JButtonLabel lblDocumento = new JButtonLabel();
    private JButtonLabel txtProveedor = new JButtonLabel();
    private JLabel txtNumeroDoc = new JLabel();
    private JLabel txtNombreDoc = new JLabel();
    private JLabel txtCodProve = new JLabel();
    private JLabelOrange jLabelOrange2 = new JLabelOrange();
    private JLabel txtNombProve = new JLabel();
    private JButtonLabel lblFormaPago = new JButtonLabel();
    private JLabel txtTipoPago = new JLabel();
    private JLabel txtNombDocumento = new JLabel();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelWhite lblRedondeo = new JLabelWhite();
    private JLabelWhite txtRedondeo = new JLabelWhite();
    private FacadeInventario facadeInventario = new FacadeInventario();
    private JLabelWhite lblEstado = new JLabelWhite();
    private JLabelWhite txtEstado = new JLabelWhite();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgMercaderiaDirectaResumen() {
        this(null, "", false);
    }

    public DlgMercaderiaDirectaResumen(Frame parent, String title, boolean modal) {
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

    private DlgMercaderiaDirectaResumen(Frame parent, String title, boolean modal, boolean ver) {
        super(parent, title, modal);
        myParentFrame = parent;
        verListaProductos = ver;
        if (VariablesInventario.vNumOrdenCompra.equals(ConstantsPtoVenta.TIP_NOTA_RECEPCION)) {
            esGuiaRecepcion = true;
        }
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(705, 461));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Resumen Ord. Comp. - Mercaderia Directa");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(678, 395));
        scrResumenOrden.setBounds(new Rectangle(10, 110, 660, 235));
        scrResumenOrden.setBackground(new Color(255, 130, 14));
        scrResumenOrden.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        pnlHeader.setBounds(new Rectangle(10, 85, 660, 25));
        pnlHeader.setBackground(new Color(255, 130, 14));
        pnlHeader.setLayout(null);
        btnResumenOrden.setText("Resumen Orden de Compra");
        btnResumenOrden.setBounds(new Rectangle(10, 0, 170, 25));
        btnResumenOrden.setBackground(new Color(255, 130, 14));
        btnResumenOrden.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnResumenOrden.setBorderPainted(false);
        btnResumenOrden.setContentAreaFilled(false);
        btnResumenOrden.setDefaultCapable(false);
        btnResumenOrden.setFocusPainted(false);
        btnResumenOrden.setFont(new Font("SansSerif", 1, 12));
        btnResumenOrden.setForeground(Color.white);
        btnResumenOrden.setHorizontalAlignment(SwingConstants.LEFT);
        btnResumenOrden.setMnemonic('r');
        btnResumenOrden.setRequestFocusEnabled(false);
        btnResumenOrden.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnResumenOrden_keyPressed(e);
            }
        });
        btnResumenOrden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnResumenOrden_actionPerformed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(585, 390, 85, 20));
        pnlOrdenCompraResume.setBounds(new Rectangle(10, 10, 660, 65));
        jPanelTitle1.setBounds(new Rectangle(10, 345, 660, 20));
        lblTotal_T.setText("TOTAL: S/.");
        lblTotal_T.setBounds(new Rectangle(500, 0, 70, 20));
        lblTotal.setBounds(new Rectangle(570, 0, 70, 20));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setText("0.00");
        lblFecha.setText("Fec. Recepci\u00f3n :");
        lblFecha.setBounds(new Rectangle(15, 10, 95, 20));
        txtFecha.setBounds(new Rectangle(110, 10, 85, 20));
        txtFecha.setForeground(SystemColor.window);
        lblDocumento.setText("Tipo Doc. :");
        lblDocumento.setBounds(new Rectangle(15, 40, 75, 20));
        lblDocumento.setSize(new Dimension(75, 20));
        txtProveedor.setText("Proveedor :");
        txtProveedor.setBounds(new Rectangle(210, 10, 75, 20));
        txtProveedor.setSize(new Dimension(75, 20));

        txtNombreDoc.setBounds(new Rectangle(170, 40, 77, 20));
        txtNombreDoc.setSize(new Dimension(77, 20));
        txtNombreDoc.setMinimumSize(new Dimension(4, 20));
        txtNombreDoc.setPreferredSize(new Dimension(4, 20));

        txtNumeroDoc.setBounds(new Rectangle(160, 40, 120, 20));
        txtNumeroDoc.setMinimumSize(new Dimension(4, 20));
        txtNumeroDoc.setPreferredSize(new Dimension(4, 20));
        txtNumeroDoc.setForeground(SystemColor.window);

        txtCodProve.setBounds(new Rectangle(285, 10, 45, 20));
        txtCodProve.setSize(new Dimension(57, 20));

        txtCodProve.setPreferredSize(new Dimension(4, 20));
        txtCodProve.setForeground(SystemColor.window);
        txtCodProve.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelOrange2.setText("-");
        jLabelOrange2.setBounds(new Rectangle(335, 10, 15, 20));
        jLabelOrange2.setSize(new Dimension(15, 20));
        jLabelOrange2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelOrange2.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabelOrange2.setFont(new Font("SansSerif", 1, 25));
        txtNombProve.setBounds(new Rectangle(355, 10, 155, 20));

        txtNombProve.setPreferredSize(new Dimension(4, 20));
        txtNombProve.setForeground(SystemColor.window);
        txtNombProve.setFont(new Font("SansSerif", 1, 11));
        txtNombProve.setHorizontalAlignment(SwingConstants.LEFT);
        lblFormaPago.setText("Forma de Pago :");
        lblFormaPago.setBounds(new Rectangle(285, 40, 95, 20));
        txtTipoPago.setBounds(new Rectangle(385, 40, 120, 20));

        txtTipoPago.setForeground(SystemColor.window);
        txtNombDocumento.setBounds(new Rectangle(75, 40, 85, 20));
        txtNombDocumento.setPreferredSize(new Dimension(4, 20));
        txtNombDocumento.setForeground(SystemColor.window);
        txtNombDocumento.setFont(new Font("SansSerif", 1, 11));
        txtNombDocumento.setSize(new Dimension(95, 20));
        jLabelFunction1.setBounds(new Rectangle(10, 380, 160, 20));
        jLabelFunction1.setText("[ F4 ] Anular Ord. Comp.");
        lblRedondeo.setText("REDONDEO: S/.");
        lblRedondeo.setBounds(new Rectangle(290, 0, 80, 20));
        txtRedondeo.setText("0.00");
        txtRedondeo.setBounds(new Rectangle(385, 0, 55, 20));
        txtRedondeo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEstado.setText("ESTADO :");
        lblEstado.setBounds(new Rectangle(515, 40, 57, 20));
        lblEstado.setSize(new Dimension(57, 20));
        txtEstado.setBounds(new Rectangle(580, 40, 70, 20));
        txtEstado.setSize(new Dimension(70, 20));
        scrResumenOrden.getViewport();
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jPanelTitle1.add(lblTotal, null);
        jPanelTitle1.add(lblTotal_T, null);
        jPanelTitle1.add(lblRedondeo, null);
        jPanelTitle1.add(txtRedondeo, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(jPanelTitle1, null);
        pnlOrdenCompraResume.add(txtNombDocumento, null);
        pnlOrdenCompraResume.add(txtTipoPago, null);
        pnlOrdenCompraResume.add(lblFormaPago, null);
        pnlOrdenCompraResume.add(txtNombProve, null);
        pnlOrdenCompraResume.add(jLabelOrange2, null);
        pnlOrdenCompraResume.add(txtCodProve, null);
        pnlOrdenCompraResume.add(txtNumeroDoc, null);
        pnlOrdenCompraResume.add(txtProveedor, null);
        pnlOrdenCompraResume.add(lblDocumento, null);
        pnlOrdenCompraResume.add(txtFecha, null);
        pnlOrdenCompraResume.add(lblFecha, null);
        pnlOrdenCompraResume.add(lblEstado, null);
        pnlOrdenCompraResume.add(txtEstado, null);
        jContentPane.add(pnlOrdenCompraResume, null);
        jContentPane.add(lblEsc, null);
        scrResumenOrden.getViewport().add(tblResumenOrden, null);

        jContentPane.add(scrResumenOrden, null);
        pnlHeader.add(btnResumenOrden, null);
        jContentPane.add(pnlHeader, null);

    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTableResumenOrden();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */


    private void initTableResumenOrden() {
        //tableModelOrdenCompraResumen.clearTable();
        tableModelOrdenCompraResumen =
                new FarmaTableModel(ConstantsInventario.columnsListaProductoOrden, ConstantsInventario.defaultListaProductoOrden,
                                    0);
        FarmaUtility.initSimpleList(tblResumenOrden, tableModelOrdenCompraResumen,
                                    ConstantsInventario.columnsListaProductoOrden);

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnResumenOrden_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnResumenOrden);
    }

    private void btnResumenOrden_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(btnResumenOrden);
        cargarCabecera();
        mostrarDatos();
        FarmaVariables.vAceptar = false;
    }

    private void tblResumenOrden_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void cerrarVentana(boolean pAceptar) {
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    /**
     * Determina si una orden de compra ya fue regularizada(si paso por devolucion de sobrantes).
     * @author ASOSA
     * @since 30/09/2014
     * @return
     */
    private boolean isOcRegularizado() {
        boolean flag01 = false;
        flag01 = UtilityInventario.isOcRegularizado(VariablesInventario.vNumOrdenCompra.trim());
        return flag01;
    }

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblResumenOrden, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_F4) {
            //Anular
            if (!isOcRegularizado()) { //ASOSA - 30/09/2014
                anularNGuia();
                if (FarmaVariables.vAceptar) {
                    FarmaVariables.vAceptar = false;
                }
            } else {
                FarmaUtility.showMessage(this,
                                         "La orden de compra ya fue regularizada, no se puede anular ninguna recepción",
                                         null);
                cerrarVentana(false);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", btnResumenOrden);
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void mostrarDatos() {
        tableModelOrdenCompraResumen.clearTable();
        tableModelOrdenCompraResumen.data =
                VariablesInventario.vArrayOrdenCompraDetalle; //VariablesInventario.vArrayDetalleGuiaRecep

        arrayOrdenCompra =
                facadeInventario.listarProdDetRecep(VariablesInventario.vNumOrdenCompra, VariablesInventario.vSecRecepcion);
        if (arrayOrdenCompra.size() > -1) {
            tableModelOrdenCompraResumen.data = arrayOrdenCompra;
        } else {
            FarmaUtility.showMessage(this, "No se logro mostrar la información de Orden de Compra.", null);
        }
    }

    private void cargarCabecera() {
        ArrayList arrayCab = new ArrayList();
        arrayCab = null;

        arrayCab =
                facadeInventario.getCabOrdenCompraRecep(VariablesInventario.vNumOrdenCompra, VariablesInventario.vSecRecepcion);
        for (int i = 0; i < arrayCab.size(); i++) {
            txtFecha.setText(((ArrayList)arrayCab.get(i)).get(0).toString());
            txtCodProve.setText(((ArrayList)arrayCab.get(i)).get(1).toString());
            txtNombProve.setText(((ArrayList)arrayCab.get(i)).get(2).toString());
            txtNombDocumento.setText(((ArrayList)arrayCab.get(i)).get(3).toString());
            txtNumeroDoc.setText(((ArrayList)arrayCab.get(i)).get(5).toString() + " - " +
                                 ((ArrayList)arrayCab.get(i)).get(6).toString());
            txtTipoPago.setText(((ArrayList)arrayCab.get(i)).get(7).toString() + " - " +
                                ((ArrayList)arrayCab.get(i)).get(8).toString());
            lblTotal.setText(((ArrayList)arrayCab.get(i)).get(9).toString());
            txtRedondeo.setText(((ArrayList)arrayCab.get(i)).get(10).toString());
            VariablesInventario.vSerieDocument = ((ArrayList)arrayCab.get(i)).get(5).toString();
            VariablesInventario.vNumeroDocument = ((ArrayList)arrayCab.get(i)).get(6).toString();
            VariablesInventario.vIdeDocumento = ((ArrayList)arrayCab.get(i)).get(4).toString();
            VariablesInventario.vRedondeo = ((ArrayList)arrayCab.get(i)).get(9).toString();
            VariablesInventario.vImportRecep = ((ArrayList)arrayCab.get(i)).get(10).toString();
            VariablesInventario.vNumerGuia = VariablesInventario.vNumGuia;
            VariablesInventario.vEstadoOrdComp = ((ArrayList)arrayCab.get(i)).get(11).toString();
            VariablesInventario.vCodigoCia = ((ArrayList)arrayCab.get(i)).get(12).toString();
            if (VariablesInventario.vEstadoOrdComp.trim().equals("A"))
                txtEstado.setText("ACTIVO");
            else if (VariablesInventario.vEstadoOrdComp.trim().equals("N"))
                txtEstado.setText("ANULADO");
        }
        if (arrayCab.size() == 0) {
            cerrarVentana(false);
            FarmaUtility.showMessage(this, "El documento fue anulado.", null);

        }
    }

    //Anula documento

    private void anularNGuia() {
        if (verificaComprobante()) {
            if (verificaNumAjuste()) {
                //LLEIVA 27-Feb-2014 Se solicitara la contraseña de auditor para poder continuar
                DlgLogin dlgLogin = new DlgLogin(this.myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
                dlgLogin.setRolUsuario(FarmaConstants.ROL_AUDITORIA);
                dlgLogin.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "¿Está seguro de anular la " +
                                                                                    txtNombDocumento.getText().trim() +
                                                                                    "?")) {
                        if (anular()) {
                            txtEstado.setText("ANULADO");
                            FarmaUtility.showMessage(this, "El documento fue anulado.", btnResumenOrden);
                            //cerrarVentana(true);
                        } else {
                            FarmaUtility.showMessage(this, "El documento ya fue anulado.", null);
                        }
                    }
                }
            }
        }
    }

    public boolean verificaNumAjuste() {
        String codProd = null;
        String numAjuste = null;
        boolean flag = true;
        for (int i = 0; i < arrayOrdenCompra.size(); i++) {
            codProd = ((ArrayList)arrayOrdenCompra.get(0)).get(0).toString();
            numAjuste =
                    facadeInventario.getNumAjuste(codProd, VariablesInventario.vNumeroDocument, ConstantsPtoVenta.MOT_KARDEX_RECEPCION_PROVEEDOR,
                                                  VariablesInventario.vTipDocum);
            if (numAjuste.trim().equals("FALSE")) {
                flag = false;
                //FarmaUtility.showMessage(this, "La orden de compra ya fue generada en interface", null);
                // AAMPUERO 21.04.2014
            }
        }
        // AAMPUERO 21.04.2014
        if (!flag) {
            FarmaUtility.showMessage(this, "La orden de compra ya fue generada en interface", null);
        }
        return flag;
    }

    public boolean verificaComprobante() {
        boolean vComprobante = false;
        if (VariablesInventario.vEstadoOrdComp.equals("A")) {
            vComprobante = true;
        }
        return vComprobante;
    }

    public boolean anular() {
        boolean vAnular = false;
        vAnular =
                facadeInventario.anularGuiaIngreso(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal,
                                                   VariablesInventario.vNumOrdenCompra, VariablesInventario.vNumerGuia,
                                                   VariablesInventario.vIdeDocumento,
                                                   VariablesInventario.vNumeroDocument, FarmaVariables.vIdUsu,
                                                   VariablesInventario.vSerieDocument,
                                                   VariablesInventario.vSecRecepcion);
        return vAnular;

    }

    void setFacade(FacadeInventario facadeInventario) {
        this.facadeInventario = facadeInventario;
    }
}
