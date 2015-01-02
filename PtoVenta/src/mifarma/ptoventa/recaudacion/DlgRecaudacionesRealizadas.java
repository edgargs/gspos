package mifarma.ptoventa.recaudacion;


import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recaudacion.reference.UtilityRecaudacion;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRecaudacionesRealizadas extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgRecaudacionesRealizadas.class);

    private static final int COL_MONTO_PAGADO = 7;
    private static final int COL_COD_SIX = 10;
    private static final int COL_EST_TRSSC_SIX = 11;
    private static final int COL_AUTORI_RCD = 14;
    private static final int COL_TIP_RCD = 15;
    private static final int COL_MONEDA = 16;
    private static final int COL_FECHA_ORIGEN = 17;

    Frame myParentFrame;
    FarmaTableModel tableModel;
    ArrayList arrayProductos = new ArrayList();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF7 = new JLabelFunction();
    private JScrollPane scrCabeceraPedido = new JScrollPane();
    private JPanel pnlHeader = new JPanel();
    private JButton btnCabeceraPedido = new JButton();
    private JPanel pnlTitle = new JPanel();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtCodRcd = new JTextFieldSanSerif();
    private JButton btnRecaudacion = new JButton();
    private JTable tblCabeceraPedido = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabel lblMonto = new JLabel();
    private JTextField txtMonto = new JTextField();

    Long codTrsscAnulTemp = null;
    String tipoCobro;
    UtilityRecaudacion utilityRecaudacion = new UtilityRecaudacion();
    transient FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
    UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();
    private JLabelFunction lblF8 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgRecaudacionesRealizadas() {
        this(null, "", false);
    }

    public DlgRecaudacionesRealizadas(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
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
        this.setSize(new Dimension(800, 450));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Transacciones Realizadas");
        this.setDefaultCloseOperation(0);
        this.setBounds(new Rectangle(10, 10, 800, 464));
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(594, 405));
        jContentPane.setForeground(Color.white);
        lblF5.setText("[ F5 ] Anular");
        lblF5.setBounds(new Rectangle(285, 390, 100, 20));
        lblF7.setText("[ F7 ] Reimprimir");
        lblF7.setBounds(new Rectangle(395, 390, 110, 20));
        scrCabeceraPedido.setBounds(new Rectangle(10, 90, 770, 290));
        scrCabeceraPedido.setBackground(new Color(255, 130, 14));
        pnlHeader.setBounds(new Rectangle(10, 65, 770, 25));
        pnlHeader.setBackground(new Color(255, 130, 14));
        pnlHeader.setLayout(null);
        pnlHeader.setForeground(new Color(255, 130, 14));
        btnCabeceraPedido.setText("Listado de transacciones");
        btnCabeceraPedido.setBounds(new Rectangle(10, 0, 145, 25));
        btnCabeceraPedido.setBackground(new Color(255, 130, 14));
        btnCabeceraPedido.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnCabeceraPedido.setBorderPainted(false);
        btnCabeceraPedido.setContentAreaFilled(false);
        btnCabeceraPedido.setDefaultCapable(false);
        btnCabeceraPedido.setFocusPainted(false);
        btnCabeceraPedido.setFont(new Font("SansSerif", 1, 11));
        btnCabeceraPedido.setMnemonic('l');
        btnCabeceraPedido.setForeground(Color.white);
        btnCabeceraPedido.setHorizontalAlignment(SwingConstants.LEFT);
        btnCabeceraPedido.setRequestFocusEnabled(false);
        btnCabeceraPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCabeceraPedido_actionPerformed(e);
            }
        });
        pnlTitle.setBounds(new Rectangle(10, 20, 770, 40));
        pnlTitle.setBackground(new Color(43, 141, 39));
        pnlTitle.setLayout(null);
        pnlTitle.setForeground(new Color(43, 141, 39));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(545, 10, 115, 20));
        btnBuscar.setFont(new Font("SansSerif", 1, 12));
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setMnemonic('b');
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.setSize(new Dimension(115, 20));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtCodRcd.setBounds(new Rectangle(180, 10, 120, 20));
        txtCodRcd.setLengthText(5);
        txtCodRcd.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCorrelativo_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCodRcd_keyTyped(e);
            }
        });
        btnRecaudacion.setText("N\u00famero de operaci\u00f3n :");
        btnRecaudacion.setBounds(new Rectangle(25, 10, 135, 20));
        btnRecaudacion.setFont(new Font("SansSerif", 1, 12));
        btnRecaudacion.setForeground(Color.white);
        btnRecaudacion.setBackground(new Color(43, 141, 39));
        btnRecaudacion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnRecaudacion.setBorderPainted(false);
        btnRecaudacion.setContentAreaFilled(false);
        btnRecaudacion.setDefaultCapable(false);
        btnRecaudacion.setFocusPainted(false);
        btnRecaudacion.setMnemonic('N');
        btnRecaudacion.setRequestFocusEnabled(false);
        btnRecaudacion.setHorizontalAlignment(SwingConstants.LEFT);
        /*btnRecaudacion.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnCorrelativo_actionPerformed(e);
                    }
                });*/
        btnRecaudacion.setActionCommand("");
        btnRecaudacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRecaudacion_actionPerformed(e);
            }
        });
        tblCabeceraPedido.setFocusable(true);
        tblCabeceraPedido.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblCabeceraPedido_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(675, 390, 85, 20));
        lblMonto.setText("Monto :");
        lblMonto.setForeground(Color.white);
        lblMonto.setBounds(new Rectangle(330, 10, 60, 20));
        lblMonto.setFont(new Font("Dialog", 1, 11));
        lblMonto.setSize(new Dimension(60, 20));
        txtMonto.setBounds(new Rectangle(410, 10, 100, 20));
        txtMonto.setSize(new Dimension(100, 20));
        txtMonto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMonto_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtMonto_keyTyped(e);
            }
        });
        lblF8.setText("[ F8 ] Verificar Estado");
        lblF8.setBounds(new Rectangle(515, 390, 135, 20));
        scrCabeceraPedido.getViewport();
        pnlHeader.add(btnCabeceraPedido, null);
        pnlTitle.add(txtMonto, null);
        pnlTitle.add(lblMonto, null);
        pnlTitle.add(btnBuscar, null);
        pnlTitle.add(txtCodRcd, null);
        pnlTitle.add(btnRecaudacion, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF7, null);
        scrCabeceraPedido.getViewport().add(tblCabeceraPedido, null);
        jContentPane.add(scrCabeceraPedido, null);
        jContentPane.add(pnlHeader, null);
        jContentPane.add(pnlTitle, null);
    }

    private void initListaVentaCMR() {
        if (getTipoCobro().equals(ConstantsRecaudacion.TIPO_COBRO_VENTA_CMR)) {
            this.setTitle("Lista Ventas CMR");
            lblF5.setVisible(false);
            //lblF5.setBounds(570, 390, 100, 20);
            //lblF7.setVisible(false);
            //lblF8.setVisible(false);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        //Para inicializar Fecha
        utilityRecaudacion.initMensajesVentana(this, null, null, "00");
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsRecaudacion.columnsCabeceraPedidoRecaudacion, ConstantsRecaudacion.defaultCabeceraPedidoRecaudacion,
                                    0);
        FarmaUtility.initSimpleList(tblCabeceraPedido, tableModel,
                                    ConstantsRecaudacion.columnsCabeceraPedidoRecaudacion);
    }

    private void listarTabRcdCan(String pCodRcd, String Monto) {
        ArrayList tmpPrueba = new ArrayList();
        tmpPrueba =
                facadeRecaudacion.listarRcdCanceladas(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal,
                                                      pCodRcd, Monto, getTipoCobro());
        llenarTabla(tableModel, tmpPrueba);
    }


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void this_windowOpened(WindowEvent e) {
        initListaVentaCMR();
        listarTabRcdCan(ConstantsRecaudacion.MONTO_VACIO, ConstantsRecaudacion.MONTO_VACIO);
        //ERIOS 2.3.3 Valida conexion con RAC
        try {
            facadeRecaudacion.validarConexionRAC();
        } catch (Exception f) {
            log.error("", f);
        }
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCodRcd);
    }

    private void btnCorrelativo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodRcd);
    }


    private void txtCorrelativo_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblCabeceraPedido, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (!txtCodRcd.getText().equals("")) {
                txtCodRcd.setText(FarmaUtility.completeWithSymbol(txtCodRcd.getText(), 5, "0", "I"));
            }
            FarmaUtility.moveFocus(txtMonto);
        } else {
            chkkeyPressed(e);
        }
    }


    private void txtMonto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblCabeceraPedido, null, 6);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
            FarmaUtility.moveFocus(txtCodRcd);
        } else {
            chkkeyPressed(e);
        }
    }


    private void tblCabeceraPedido_keyPressed(KeyEvent e) {

        chkkeyPressed(e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();
    }

    private void btnCabeceraPedido_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblCabeceraPedido);
    }

    private void btnRecaudacion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodRcd);
    }

    private void txtCodRcd_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCodRcd, e);
    }

    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */


    private void chkkeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (ConstantsRecaudacion.TIPO_COBRO_RECAUDACION.equals(tipoCobro)) {
                if (obtenerEstadoRecaudacion()) {
                    if (validaTiempoAnulacionRecarga()) {
                        anularRecaudacion();
                    } else {
                        String tMaximo = facadeRecaudacion.tiempoMaxAnulacionRecaudacion("RCD").trim();
                        FarmaUtility.showMessage(this,
                                                 "No se puede anular, el tiempo es mayor a " + tMaximo + " minutos",
                                                 null);
                    }
                } else {
                    FarmaUtility.showMessage(this, "La recaudación ya fue anulada.", null);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            if (obtenerEstadoRecaudacion()) {
                reimprimirComprobante();
            } else {
                FarmaUtility.showMessage(this, "No se puede imprimir, la recaudación está anulada.", null);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            e.consume();
            ArrayList rptDatos = verificarEstadoTrssc();
            boolean bTipRecauValida = (Boolean)rptDatos.get(0);
            if (bTipRecauValida) {
                boolean bRptr = (Boolean)rptDatos.get(1);
                String codSix = (String)rptDatos.get(2);
                if (bRptr) {
                    FarmaUtility.showMessage(this, "Se proceso correctamente la recaudacion.", null);
                } else {
                    FarmaUtility.showMessage(this, "No se proceso correctamente la recaudacion.", null);
                }
            } else {
                FarmaUtility.showMessage(this, "Esta opción no es valida para este tipo de recaudación.", null);
            }
            FarmaUtility.moveFocus(txtCodRcd);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana();
        }
    }

    private void busqueda() {
        tableModel.clearTable();
        listarTabRcdCan(txtCodRcd.getText().trim(), txtMonto.getText().trim());
        if (tableModel.getRowCount() == 0) {
            FarmaUtility.showMessage(this, "No se encontraron registros, Inténtelo nuevamente.", txtCodRcd);
        }
        FarmaUtility.moveFocus(tblCabeceraPedido);
    }

    private void cerrarVentana() {
        this.setVisible(false);
        this.dispose();
    }


    public void llenarTabla(FarmaTableModel tblDetalle, ArrayList tmpArrayDetalle) {
        if (tmpArrayDetalle.size() < 0) {
            return;
        }
        for (int i = 0; i < tmpArrayDetalle.size(); i++) {
            String tipoRcd = FarmaUtility.getValueFieldArrayList(tmpArrayDetalle, i, 14); //tipo recaudacion
            String nroTarjeta = "", nroTarjetaReal = "";
            ArrayList tmpArray;
            //ERIOS 20.09.2013 Se oculta parcialmente el numero de todas tarjetas de recaudacion
            //if(ConstantsRecaudacion.TIPO_REC_CITI.equals(tipoRcd)){
            nroTarjeta = FarmaUtility.getValueFieldArrayList(tmpArrayDetalle, i, 3);
            nroTarjetaReal = nroTarjeta;
            if (nroTarjeta != null && !nroTarjeta.trim().equals("")) {
                nroTarjeta = nroTarjeta.substring(0, 4) + "********" + nroTarjeta.substring(12, 16);
                tmpArray = (ArrayList)tmpArrayDetalle.get(i);
                tmpArray.set(3, nroTarjeta); //en la posicion 3 guardamos el numero de tarjeta enmascarada
                tmpArray.add(nroTarjetaReal); //guardamos el numero de tarjeta real en la columna real
                tmpArrayDetalle.set(i, tmpArray);
            }
            //}
            tblDetalle.insertRow((ArrayList)tmpArrayDetalle.get(i));
        }
    }

    public void refrescarGrilla() {
        tableModel.clearTable();
        listarTabRcdCan(ConstantsRecaudacion.LISTAR_TODO, ConstantsRecaudacion.MONTO_VACIO);
        //FarmaUtility.showMessage(this,"La recaudación fue anulada.",null);
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    /**
     * Re Impresion de Recaudacion
     * @author RLLANTOY
     * @since 24.06.2013
     */
    public void reimprimirComprobante() {
        try {
            boolean bEstadoImp = false;
            String strCodRecau = tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), 0).toString();
            bEstadoImp = facadeRecaudacion.obtieneEstadoImpresionRacaudacion(strCodRecau);
            boolean rpta;
            if (bEstadoImp) {
                rpta =
com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Desea reimprimir el voucher?");
                if (rpta) {
                    if (ConstantsRecaudacion.TIPO_COBRO_RECAUDACION.equals(tipoCobro)) {
                        facadeRecaudacion.imprimirComprobantePagoRecaudacion(strCodRecau);
                        //ERIOS 2.4.0 Se permite reimprimir varias veces
                        //facadeRecaudacion.actualizaEstadoImpresionRacaudacion(strCodRecau,ConstantsRecaudacion.TIP_REIMPRE);
                    } else { //Reimpresion para venta CMR
                        facadeRecaudacion.imprimirComprobantePagoRecauVentaCMR(strCodRecau, "", "", "", "");
                    }
                    FarmaUtility.showMessage(this, "Se reimprimió el voucher satisfactoriamente.", null);
                }
            } else {
                FarmaUtility.showMessage(this, "No se puede Imprimir, el comprobante ya fue Re-Impreso !!!", null);
            }
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al imprimir.\n" +
                    e.getMessage(), null);
        }
    }

    /**
     * Modificacion, anulacion de recaudacion.
     * @author GFonseca
     * @since 25.Junio.2013
     */
    private void anularRecaudacion() {
        if (facadeRecaudacion.cargaLogin_verifica_quimico(myParentFrame)) {
            try {
                DlgDetalleAnularRecaudacion dlgDetalleAnularRecau =
                    new DlgDetalleAnularRecaudacion(myParentFrame, "", true);
                dlgDetalleAnularRecau.setVisible(true);
                if (JConfirmDialog.rptaConfirmDialogDefaultNo(this, "¿Está seguro de anular la recaudación?")) {

                    String numTarjetaTemp =
                        tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), 3).toString().trim();
                    String numTarjeta = "";
                    if (numTarjetaTemp != null && !numTarjetaTemp.trim().equals("")) {
                        numTarjeta = tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), 18).toString().trim();
                    }
                    String numTelefono =
                        tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), 4).toString().trim();
                    String codSix =
                        tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), COL_COD_SIX).toString().trim();
                    String montoPagado =
                        tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), COL_MONTO_PAGADO).toString().trim();
                    String tipoRcdDesc = tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), 2).toString();
                    String codRecauAnular = tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), 0).toString();
                    String estTrsscSix =
                        tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), COL_EST_TRSSC_SIX).toString();
                    String tipRcdCod =
                        tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), COL_TIP_RCD).toString();
                    String codMoneda =
                        tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), COL_MONEDA).toString();
                    String fechaRecauAnular = tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), 1).toString();
                    String codAutorizRecauAnular =
                        tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), COL_AUTORI_RCD).toString();
                    String fechaOrigen =
                        tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), COL_FECHA_ORIGEN).toString();

                    DlgProcesarPagoTerceros dlgProcesarPagoTerceros =
                        new DlgProcesarPagoTerceros(myParentFrame, "", true);
                    dlgProcesarPagoTerceros.procesarAnulacionTerceros(myParentFrame, numTarjeta, numTelefono, codSix,
                                                                      montoPagado, tipoRcdDesc, codRecauAnular,
                                                                      estTrsscSix, tipRcdCod, codMoneda,
                                                                      fechaRecauAnular, codAutorizRecauAnular,
                                                                      fechaOrigen);
                    dlgProcesarPagoTerceros.setStrIndProc(ConstantsRecaudacion.RCD_IND_PROCESO_ANULACION);
                    dlgProcesarPagoTerceros.mostrar();
                    //TODO
                    refrescarGrilla();
                    FarmaUtility.moveFocus(txtCodRcd);
                    /*if(FarmaVariables.vAceptar){
                        cerrarVentana(true);
                        }*/
                }

            } catch (Exception e) {
                log.info("" + e);
            }
        }
    }

    /**
     * Método para obtener el estado de una recaudación de forma manual, pulsando la tecla de la pantalla.
     * @author GFonseca
     * @since 08.08.2013
     */
    private ArrayList verificarEstadoTrssc() {

        ArrayList tmpDatos = new ArrayList();
        boolean bTipRecauValida = false;
        boolean bRpt = false;
        String codTrsscSix = tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), COL_COD_SIX).toString().trim();
        String tipoRcd = tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), 2).toString();
        String codRecau = tableModel.getValueAt(tblCabeceraPedido.getSelectedRow(), 0).toString();
        try {
            if (ConstantsRecaudacion.RCD_CMR.equals(tipoRcd) || ConstantsRecaudacion.RCD_CLARO.equals(tipoRcd) ||
                tipoRcd.equals("CMR VENTAS")) {
                bTipRecauValida = true;
                String arrayDatosTrssc[] = new String[2];
                String srtEstTrssc = "";
                String strCodAutoriz = "";
                String tmpEst =
                    facadeRecaudacion.obtenerEstadoTrssc(new Long(codTrsscSix), ConstantsRecaudacion.RCD_MODO_CONSULTA_SIX);
                arrayDatosTrssc = tmpEst.split(",");
                srtEstTrssc = arrayDatosTrssc[0].trim(); //Estado OK / FA

                if (ConstantsRecaudacion.RCD_PAGO_SIX_EST_TRSSC_CORRECTA.equals(srtEstTrssc)) {
                    //Actualizar estado de recaudacion y refresca grilla
                    strCodAutoriz = arrayDatosTrssc[1].trim(); //Codigo Autorizacion "000000100302"
                    facadeRecaudacion.actualizarEstRecauTrsscSix(codRecau, srtEstTrssc, strCodAutoriz);
                    tableModel.clearTable();
                    listarTabRcdCan(ConstantsRecaudacion.MONTO_VACIO, ConstantsRecaudacion.MONTO_VACIO);
                    bRpt = true;
                }
            }
            tmpDatos.add(bTipRecauValida);
            tmpDatos.add(bRpt);
            tmpDatos.add(codRecau);
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al consultar.\n" +
                    e.getMessage(), null);
        }
        return tmpDatos;
    }


    /**
     * Método para obtener el estado de una recaudación.
     * @author RLLantoy
     * @since 09.07.2013
     */
    private boolean obtenerEstadoRecaudacion() {
        String strCodRecau = "";
        String strEstRecau = "";
        strCodRecau = tblCabeceraPedido.getValueAt(tblCabeceraPedido.getSelectedRow(), 0).toString();
        strEstRecau = facadeRecaudacion.obtieneEstadoRecaudacion(strCodRecau).trim();
        if (strEstRecau.equals(ConstantsRecaudacion.ESTADO_COBRADO)) {
            return true;
        }

        return false;
    }


    /**
     * Método para validar el tiempo de anulación de una recaudación.
     * @author RLLantoy
     * @since 09.07.2013
     */
    private boolean validaTiempoAnulacionRecarga() {
        String strCodRecau = "";
        String strEstRecau = "";
        try {
            strCodRecau = tblCabeceraPedido.getValueAt(tblCabeceraPedido.getSelectedRow(), 0).toString();
            strEstRecau = facadeRecaudacion.validaTiempoAnulacion(strCodRecau);
            if (ConstantsRecaudacion.ESTADO_ANULADO.equals(strEstRecau.trim())) {
                return false;
            }
        } catch (Exception e) {
            log.error("ERROR ES => " + e.getMessage());
        }
        return true;
    }


    public String getTipoCobro() {
        return tipoCobro;
    }

    public void setTipoCobro(String tipoCobro) {
        this.tipoCobro = tipoCobro;
    }
}
