package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDetalleRegistroVentas extends JDialog {
    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */

    private static final Logger log = LoggerFactory.getLogger(DlgDetalleRegistroVentas.class);

    FarmaTableModel tableModel;
    FarmaTableModel tableModelFormasPago;
    private Frame myParentFrame;

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite lblCliente = new JLabelWhite();
    private JLabelWhite lblDireccion = new JLabelWhite();
    private JLabelWhite lblRuc = new JLabelWhite();
    private JLabelWhite lblNoPedido = new JLabelWhite();
    private JLabelWhite lblNoPedidoT = new JLabelWhite();
    private JLabelWhite lblFecha = new JLabelWhite();
    private JLabelWhite lblFechaT = new JLabelWhite();
    private JLabelWhite lblAtencion = new JLabelWhite();
    private JLabelWhite lblAtencionT = new JLabelWhite();
    private JLabelWhite lblRucT = new JLabelWhite();
    private JLabelWhite lblDireccionT = new JLabelWhite();
    private JLabelWhite lblClienteT = new JLabelWhite();
    private JLabelWhite lblEstado = new JLabelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JScrollPane srcDetallePedidos = new JScrollPane();
    private JTable tblDetallePedidos = new JTable();
    private JLabelWhite lblHoraT = new JLabelWhite();
    private JLabelWhite lblHora = new JLabelWhite();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabelFunction lblESC = new JLabelFunction();
    private JLabelWhite lblCodigolocal = new JLabelWhite();
    private JLabelWhite lblDescLocal = new JLabelWhite();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelWhite lblBruto = new JLabelWhite();
    private JLabelWhite lblMontoBruto = new JLabelWhite();
    private JLabelWhite lblDctoMonto = new JLabelWhite();
    private JLabelWhite lblBruto1 = new JLabelWhite();
    private JLabelWhite lblIgvMonto = new JLabelWhite();
    private JLabelWhite lblBruto2 = new JLabelWhite();
    private JLabelWhite lblTotalMonto = new JLabelWhite();
    private JLabelWhite lblBruto3 = new JLabelWhite();
    private JLabelWhite lblItems = new JLabelWhite();
    private JLabelWhite lblitem = new JLabelWhite();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanelWhite pnlDatosDelivery = new JPanelWhite();
    private JTextFieldSanSerif txtObservacionesDelivery = new JTextFieldSanSerif();
    private JLabelOrange jLabelOrange2 = new JLabelOrange();
    private JLabelOrange lblMotorizado = new JLabelOrange();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JLabelOrange jLabelOrange4 = new JLabelOrange();
    private JLabelOrange lblReferencia = new JLabelOrange();
    private JLabelOrange jLabelOrange7 = new JLabelOrange();
    private JLabelOrange lblDireccionDelivery = new JLabelOrange();
    private JLabelOrange jLabelOrange9 = new JLabelOrange();
    private JLabelOrange lblNombreDelivery = new JLabelOrange();
    private JButtonLabel btnObservaciones = new JButtonLabel();
    private JButtonLabel btnFormasPago = new JButtonLabel();
    private JTable tblListaFormasPago = new JTable();
    private JButtonLabel btlRelacionProductos = new JButtonLabel();

    private boolean indResumenDelivery;
    private boolean indVerDelivery;
    private ArrayList<ArrayList<String>> lstFormasPago;
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    private JLabelOrange lblVuelto = new JLabelOrange();
    private JPanelWhite pnlBotones = new JPanelWhite();
    private JLabelOrange lblComprobante = new JLabelOrange();
    
    // KMONCDA 25.04.2016 PERCEPCION
    private boolean isLocalAplicaPercepcion = false;
    private JLabelWhite lblPercepcion = new JLabelWhite();
    private JLabelWhite lblPctPercepcion = new JLabelWhite();
    private int longColumPercepcion = 50;
    private int espacioPercepcion = 10;
    private int anchoVentana = 750;
    private int altoVentana = 470;
    private JLabelWhite lblResumenPercepcion = new JLabelWhite();
    private JLabelWhite lblResumenMontoPercepcion = new JLabelWhite();

    /* ************************************************************************ */
    /*                             CONSTRUCTORES                                */
    /* ************************************************************************ */

    public DlgDetalleRegistroVentas() {
        this(null, "", false);
    }

    public DlgDetalleRegistroVentas(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        isLocalAplicaPercepcion = UtilityPtoVenta.isLocalAplicaPercepcion();
        if(!isLocalAplicaPercepcion){
            longColumPercepcion = 0;
        }
        setearTamanioVentana();
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                              METODO jbInit                               */
    /* ************************************************************************ */
    private void setearTamanioVentana(){
        anchoVentana = 750 + longColumPercepcion;
        altoVentana = 470;
        if (indResumenDelivery){
            altoVentana = 600;
        }
    }
    
    private void prueba(){
        jPanelWhite1.setBounds(new Rectangle(0, 0, anchoVentana, 605));
        jPanelHeader1.setBounds(new Rectangle(10, 10, anchoVentana - 25, 120));
        jPanelTitle1.setBounds(new Rectangle(10, 135, anchoVentana - 25, 20));
        srcDetallePedidos.setBounds(new Rectangle(10, 155, anchoVentana - 25, 220));
        jPanelTitle2.setBounds(new Rectangle(10, 375, anchoVentana - 25, 25));
        pnlDatosDelivery.setBounds(new Rectangle(5, 405, anchoVentana - 15, 140));
        /*
        lblBruto1.setBounds(new Rectangle(270, 5, 45, 15));
        lblDctoMonto.setBounds(new Rectangle(320, 5, 50, 15));
        lblBruto2.setBounds(new Rectangle(440, 5, 40, 15));
        lblIgvMonto.setBounds(new Rectangle(490, 5, 55, 15));
        lblBruto3.setBounds(new Rectangle(605, 5, 50, 15));
        lblTotalMonto.setBounds(new Rectangle(665, 5, 50, 15));
        
        lblBruto1.setBounds(new Rectangle(255, 5, 45, 15));
        lblDctoMonto.setBounds(new Rectangle(305, 5, 50, 15));
        lblBruto2.setBounds(new Rectangle(390, 5, 40, 15));
        lblIgvMonto.setBounds(new Rectangle(440, 5, 55, 15));
        lblBruto3.setBounds(new Rectangle(505, 5, 50, 15));
        lblTotalMonto.setBounds(new Rectangle(565, 5, 50, 15));
        */
        lblBruto1.setBounds(new Rectangle(270 - (longColumPercepcion/2), 5, 45, 15));
        lblDctoMonto.setBounds(new Rectangle(320 - (longColumPercepcion/2), 5, 50, 15));
        lblBruto2.setBounds(new Rectangle(440 - longColumPercepcion, 5, 40, 15));
        lblIgvMonto.setBounds(new Rectangle(490 - longColumPercepcion, 5, 55, 15));
        lblBruto3.setBounds(new Rectangle((605 - longColumPercepcion*2), 5, 50, 15));
        lblTotalMonto.setBounds(new Rectangle((665 - longColumPercepcion*2), 5, 50, 15));
        
        lblResumenPercepcion.setBounds(new Rectangle(645, 5, 60, 15));
        lblResumenMontoPercepcion.setBounds(new Rectangle(720, 5, 55, 15));
        lblResumenPercepcion.setVisible(isLocalAplicaPercepcion);
        lblResumenMontoPercepcion.setVisible(isLocalAplicaPercepcion);
        lblPercepcion.setVisible(isLocalAplicaPercepcion);
        lblPctPercepcion.setVisible(isLocalAplicaPercepcion);
    }
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(750, 460));
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Detalle del Pedido");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelWhite1.setBounds(new Rectangle(0, 0, 750, 605));
        jPanelHeader1.setBounds(new Rectangle(10, 10, 725, 120));
        jPanelHeader1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(255, 130,
                                                                                                          14)),
                                                                 "DATOS DEL PEDIDO", TitledBorder.CENTER,
                                                                 TitledBorder.DEFAULT_POSITION,
                                                                 new Font("SansSerif", 1, 11),
                                                                 new Color(255, 130, 14)));
        jPanelHeader1.setBackground(Color.white);
        lblCliente.setText("Cliente : ");
        lblCliente.setBounds(new Rectangle(5, 10, 75, 20));
        lblCliente.setForeground(new Color(255, 130, 14));
        lblDireccion.setText("Direcci\u00f3n : ");
        lblDireccion.setBounds(new Rectangle(5, 30, 75, 20));
        lblDireccion.setForeground(new Color(255, 130, 14));
        lblRuc.setText("Ruc: ");
        lblRuc.setBounds(new Rectangle(5, 50, 75, 20));
        lblRuc.setForeground(new Color(255, 130, 14));
        lblNoPedido.setText("No Pedido : ");
        lblNoPedido.setBounds(new Rectangle(5, 70, 75, 20));
        lblNoPedido.setForeground(new Color(255, 130, 14));
        lblNoPedidoT.setText("000");
        lblNoPedidoT.setBounds(new Rectangle(85, 70, 345, 20));
        lblNoPedidoT.setForeground(new Color(255, 130, 14));
        lblFecha.setText("Fecha :");
        lblFecha.setBounds(new Rectangle(5, 90, 75, 20));
        lblFecha.setForeground(new Color(255, 130, 14));
        lblFechaT.setBounds(new Rectangle(85, 90, 65, 20));
        lblFechaT.setForeground(new Color(255, 130, 14));
        lblAtencion.setText("Atendido por :");
        lblAtencion.setBounds(new Rectangle(455, 90, 85, 20));
        lblAtencion.setForeground(new Color(255, 130, 14));
        lblAtencionT.setBounds(new Rectangle(545, 90, 165, 20));
        lblAtencionT.setForeground(new Color(255, 130, 14));
        lblRucT.setText("00000000");
        lblRucT.setBounds(new Rectangle(85, 50, 345, 20));
        lblRucT.setForeground(new Color(255, 130, 14));
        lblDireccionT.setBounds(new Rectangle(85, 30, 345, 20));
        lblDireccionT.setForeground(new Color(255, 130, 14));
        lblClienteT.setBounds(new Rectangle(85, 10, 345, 20));
        lblClienteT.setForeground(new Color(255, 130, 14));
        lblEstado.setText("Cobrado");
        lblEstado.setBounds(new Rectangle(580, 50, 120, 20));
        lblEstado.setForeground(new Color(255, 130, 14));
        jPanelTitle1.setBounds(new Rectangle(10, 135, 725, 20));
        srcDetallePedidos.setBounds(new Rectangle(10, 155, 725, 220));
        tblDetallePedidos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblDetallePedidos_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                tblDetallePedidos_keyReleased(e);
            }
        });
        lblHoraT.setText("0");
        lblHoraT.setBounds(new Rectangle(300, 90, 80, 20));
        lblHoraT.setForeground(new Color(255, 130, 14));
        lblHora.setText("Hora :");
        lblHora.setBounds(new Rectangle(255, 90, 45, 20));
        lblHora.setForeground(new Color(255, 130, 14));
        lblF4.setBounds(new Rectangle(0, 0, 150, 20));
        lblF4.setText("[ F4] Ver Comprobante");
        lblESC.setBounds(new Rectangle(605, 0, 115, 20));
        lblESC.setText("[ ESC ] Salir");
        lblCodigolocal.setText("0");
        lblCodigolocal.setBounds(new Rectangle(455, 10, 35, 20));
        lblCodigolocal.setForeground(new Color(255, 130, 14));
        lblCodigolocal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDescLocal.setBounds(new Rectangle(500, 10, 190, 20));
        lblDescLocal.setForeground(new Color(255, 130, 14));
        jPanelTitle2.setBounds(new Rectangle(10, 375, 775, 25));
        
        /** MONTOS RESUMEN**/
        lblitem.setText("Items");
        lblitem.setBounds(new Rectangle(10, 5, 40, 15));
        
        lblItems.setBounds(new Rectangle(55, 5, 40, 15));
        lblItems.setText("0");
        
        lblBruto.setText("Bruto S/");
        lblBruto.setBounds(new Rectangle(115, 5, 50, 15));
        
        lblMontoBruto.setBounds(new Rectangle(175, 5, 50, 15));
        lblMontoBruto.setText("0");
        
        lblBruto1.setText("Dcto S/");
        lblBruto1.setBounds(new Rectangle(245, 5, 45, 15));
        
        lblDctoMonto.setBounds(new Rectangle(295, 5, 50, 15));
        lblDctoMonto.setText("0");
        
        lblBruto2.setText("IGV S/");
        lblBruto2.setBounds(new Rectangle(390, 5, 40, 15));
        
        lblIgvMonto.setBounds(new Rectangle(440, 5, 55, 15));
        lblIgvMonto.setText("0");
        
        lblBruto3.setText("Total S/");
        lblBruto3.setBounds(new Rectangle(505, 5, 50, 15));
        
        lblTotalMonto.setBounds(new Rectangle(565, 5, 50, 15));
        lblTotalMonto.setText("0");
        
        // KMONCADA 25.04.2016 PERCEPCION
        lblResumenPercepcion.setText("Percep S/");
        lblResumenPercepcion.setBounds(new Rectangle(645, 5, 60, 15));
        
        lblResumenMontoPercepcion.setText("0.00");
        lblResumenMontoPercepcion.setBounds(new Rectangle(720, 5, 55, 15));
        
        jPanelTitle2.add(lblitem, null);
        jPanelTitle2.add(lblItems, null);
        jPanelTitle2.add(lblBruto, null);
        jPanelTitle2.add(lblMontoBruto, null);
        jPanelTitle2.add(lblBruto1, null);
        jPanelTitle2.add(lblDctoMonto, null);
        jPanelTitle2.add(lblBruto2, null);
        jPanelTitle2.add(lblIgvMonto, null);
        jPanelTitle2.add(lblBruto3, null);
        jPanelTitle2.add(lblTotalMonto, null);
        jPanelTitle2.add(lblResumenPercepcion, null);
        jPanelTitle2.add(lblResumenMontoPercepcion, null);

        /** FIN MONTOS RESUMEN**/
        
        lblF12.setBounds(new Rectangle(315, 0, 125, 20));
        lblF12.setText("[ F12] Imprimir");
        lblF5.setBounds(new Rectangle(155, 0, 150, 20));
        lblF5.setText("[ F5] Ver Formas Pago");
        lblF11.setBounds(new Rectangle(490, 0, 110, 20));
        lblF11.setText("[ F11 ] Aceptar");
        pnlDatosDelivery.setBounds(new Rectangle(5, 405, 735, 140));
        pnlDatosDelivery.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(43, 141,
                                                                                                             39)),
                                                                    "DATOS DE REPARTO", TitledBorder.CENTER,
                                                                    TitledBorder.DEFAULT_POSITION,
                                                                    new Font("SansSerif", 1, 11),
                                                                    new Color(43, 141, 39)));
        txtObservacionesDelivery.setBounds(new Rectangle(95, 115, 630, 20));
        txtObservacionesDelivery.setLengthText(120);
        txtObservacionesDelivery.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtObservacionesDelivery_keyPressed(e);
            }
        });
        jLabelOrange2.setText("Motorizado:");
        jLabelOrange2.setBounds(new Rectangle(5, 90, 79, 15));
        jLabelOrange2.setForeground(new Color(43, 141, 39));
        lblMotorizado.setBounds(new Rectangle(80, 90, 305, 15));
        lblMotorizado.setText("[Nombre Motorizado]");
        lblMotorizado.setForeground(new Color(43, 141, 39));
        jScrollPane1.setBounds(new Rectangle(390, 35, 335, 55));
        jLabelOrange4.setText("Referencia:");
        jLabelOrange4.setBounds(new Rectangle(5, 65, 79, 15));
        jLabelOrange4.setForeground(new Color(43, 141, 39));
        lblReferencia.setBounds(new Rectangle(80, 65, 305, 15));
        lblReferencia.setText("[Referencia Direccion]");
        lblReferencia.setForeground(new Color(43, 141, 39));
        jLabelOrange7.setText("Direcci\u00f3n:");
        jLabelOrange7.setBounds(new Rectangle(5, 40, 79, 15));
        jLabelOrange7.setForeground(new Color(43, 141, 39));
        lblDireccionDelivery.setBounds(new Rectangle(80, 40, 305, 15));
        lblDireccionDelivery.setText("[Direccion Entrega]");
        lblDireccionDelivery.setForeground(new Color(43, 141, 39));
        jLabelOrange9.setText("Nombre:");
        jLabelOrange9.setBounds(new Rectangle(5, 20, 79, 15));
        jLabelOrange9.setForeground(new Color(43, 141, 39));
        lblNombreDelivery.setBounds(new Rectangle(80, 20, 305, 15));
        lblNombreDelivery.setText("[Nombre Cliente Delivery]");
        lblNombreDelivery.setForeground(new Color(43, 141, 39));
        btnObservaciones.setText("Observaciones");
        btnObservaciones.setBounds(new Rectangle(5, 115, 85, 15));
        btnObservaciones.setForeground(new Color(43, 141, 39));
        btnObservaciones.setActionCommand("Observ:");
        btnObservaciones.setMnemonic('O');
        btnObservaciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnObservaciones_actionPerformed(e);
            }
        });
        btnFormasPago.setText("Formas Pago:");
        btnFormasPago.setBounds(new Rectangle(395, 20, 105, 20));
        btnFormasPago.setMnemonic('F');
        btnFormasPago.setForeground(new Color(43, 141, 39));
        btnFormasPago.setVerticalAlignment(SwingConstants.TOP);
        btnFormasPago.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFormasPago.setHorizontalAlignment(SwingConstants.CENTER);
        btnFormasPago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFormasPago_actionPerformed(e);
            }
        });
        tblListaFormasPago.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaFormasPago_keyPressed(e);
            }
        });
        btlRelacionProductos.setText("Relacion Productos");
        btlRelacionProductos.setBounds(new Rectangle(10, 0, 110, 20));
        btlRelacionProductos.setMnemonic('R');
        btlRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btlRelacionProductos_actionPerformed(e);
            }
        });
        jLabelOrange1.setText("Vuelto:   S/.");
        jLabelOrange1.setBounds(new Rectangle(390, 90, 70, 15));
        jLabelOrange1.setForeground(new Color(43, 141, 39));
        lblVuelto.setText("[Vuelto]");
        lblVuelto.setBounds(new Rectangle(460, 90, 79, 15));
        lblVuelto.setForeground(new Color(43, 141, 39));
        pnlBotones.setBounds(new Rectangle(5, 545, 735, 20));
        lblComprobante.setText("");
        lblComprobante.setBounds(new Rectangle(455, 30, 260, 20));
        
        lblPercepcion.setText("Percepción:");
        lblPercepcion.setBounds(new Rectangle(455, 70, 70, 20));
        lblPercepcion.setForeground(new Color(255, 130, 14));
        
        lblPctPercepcion.setText("0.00%");
        lblPctPercepcion.setBounds(new Rectangle(530, 70, 240, 20));
        lblPctPercepcion.setForeground(new Color(255, 130, 14));
        
        jPanelHeader1.add(lblPercepcion, null);
        jPanelHeader1.add(lblPctPercepcion, null);
            
        jPanelHeader1.add(lblComprobante, null);
        jPanelHeader1.add(lblDescLocal, null);
        jPanelHeader1.add(lblCodigolocal, null);
        jPanelHeader1.add(lblHora, null);
        jPanelHeader1.add(lblHoraT, null);
        jPanelHeader1.add(lblEstado, null);
        jPanelHeader1.add(lblClienteT, null);
        jPanelHeader1.add(lblDireccionT, null);
        jPanelHeader1.add(lblRucT, null);
        jPanelHeader1.add(lblAtencionT, null);
        jPanelHeader1.add(lblAtencion, null);
        jPanelHeader1.add(lblFechaT, null);
        jPanelHeader1.add(lblFecha, null);
        jPanelHeader1.add(lblNoPedidoT, null);
        jPanelHeader1.add(lblNoPedido, null);
        jPanelHeader1.add(lblRuc, null);
        jPanelHeader1.add(lblDireccion, null);
        jPanelHeader1.add(lblCliente, null);
        pnlDatosDelivery.add(lblVuelto, null);
        pnlDatosDelivery.add(jLabelOrange1, null);
        pnlDatosDelivery.add(btnFormasPago, null);
        pnlDatosDelivery.add(btnObservaciones, null);
        pnlDatosDelivery.add(lblNombreDelivery, null);
        pnlDatosDelivery.add(jLabelOrange9, null);
        pnlDatosDelivery.add(lblDireccionDelivery, null);
        pnlDatosDelivery.add(jLabelOrange7, null);
        pnlDatosDelivery.add(lblReferencia, null);
        pnlDatosDelivery.add(jLabelOrange4, null);
        jScrollPane1.getViewport().add(tblListaFormasPago, null);
        pnlDatosDelivery.add(jScrollPane1, null);
        pnlDatosDelivery.add(lblMotorizado, null);
        pnlDatosDelivery.add(jLabelOrange2, null);
        pnlDatosDelivery.add(txtObservacionesDelivery, null);
        pnlBotones.add(lblESC, null);
        pnlBotones.add(lblF11, null);
        pnlBotones.add(lblF12, null);
        pnlBotones.add(lblF5, null);
        pnlBotones.add(lblF4, null);
        jPanelWhite1.add(pnlBotones, null);
        jPanelWhite1.add(pnlDatosDelivery, null);
        srcDetallePedidos.getViewport().add(tblDetallePedidos, null);
        jPanelWhite1.add(srcDetallePedidos, null);
        jPanelTitle1.add(btlRelacionProductos, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelWhite1.add(jPanelHeader1, null);
        jPanelWhite1.add(jPanelTitle2, null);
        this.getContentPane().add(jPanelWhite1, null);
        prueba();
    }

    /* ************************************************************************ */
    /*                           METODO initialize                              */
    /* ************************************************************************ */

    private void initialize() {
        
        initTableListaDetalleVentas();
        initTableListaFormasPago();
        FarmaVariables.vAceptar = false;
        
    }

    /* ************************************************************************ */
    /*                         METODOS INICIALIZACION                           */
    /* ************************************************************************ */

    private void initTableListaDetalleVentas() {
        FarmaColumnData columnasDetalle[] ={new FarmaColumnData("Codigo", 50, JLabel.CENTER),       //0
                                            new FarmaColumnData("Cant", 40, JLabel.RIGHT),          // 1
                                            new FarmaColumnData("Descripcion", 180, JLabel.LEFT),   // 2
                                            new FarmaColumnData("Unidad", 90, JLabel.LEFT),         //3   
                                            new FarmaColumnData("Laboratorio", 120, JLabel.LEFT),   //4
                                            new FarmaColumnData("P.Unit", 70, JLabel.RIGHT),        //5
                                            new FarmaColumnData("Dscto", 70, JLabel.RIGHT),         //6
                                            new FarmaColumnData("Importe", 100, JLabel.RIGHT),      //7
                                            new FarmaColumnData("Total", 0, JLabel.RIGHT),          //8
                                            new FarmaColumnData("Bruto", 0, JLabel.RIGHT),          //9
                                            new FarmaColumnData("Dcto", 0, JLabel.RIGHT),           //10
                                            new FarmaColumnData("Igv", 0, JLabel.RIGHT),            //11
                                            new FarmaColumnData("Neto", 0, JLabel.RIGHT),           //12
                                            new FarmaColumnData("", 0, JLabel.RIGHT),               //13
                                            new FarmaColumnData("Percep.", longColumPercepcion, JLabel.RIGHT),//14
                                            new FarmaColumnData("Total Percepcion.", 0, JLabel.RIGHT),    //15
                                            new FarmaColumnData("% Percepcion.", 0, JLabel.RIGHT)     //16
                                            } ;
        //tableModel = new FarmaTableModel(ConstantsReporte.columnsListaDetalleVentas, ConstantsReporte.defaultValuesListaDetalleVentas,0);
        tableModel = new FarmaTableModel(columnasDetalle, UtilityPtoVenta.obtenerDefaultValuesTabla(columnasDetalle.length),0);
        FarmaUtility.initSimpleList(tblDetallePedidos, tableModel, columnasDetalle);
    }

    private void initTableListaFormasPago() {
        tableModelFormasPago =
                new FarmaTableModel(ConstantsReporte.columnsListaFormasdePago, ConstantsReporte.defaultValuesListaListaFormasdePago,
                                    0);
        FarmaUtility.initSimpleList(tblListaFormasPago, tableModelFormasPago,
                                    ConstantsReporte.columnsListaFormasdePago);
    }

    private void cargaDetalleVentas() {
        try {
            log.debug(VariablesReporte.vCorrelativo);
            DBReportes.obtieneDetalleRegistroVentas(tableModel, VariablesReporte.vCorrelativo);
            FarmaUtility.ordenar(tblDetallePedidos, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el Detalle de las Ventas : \n" +
                    sql.getMessage(), null);
            cerrarVentana(false);
        }
    }

    /* ************************************************************************ */
    /*                           METODOS DE EVENTOS                             */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(tblDetallePedidos);
        log.debug("Numero Comprobante" + VariablesReporte.vNComprobante);
        cargaVariables();
        mostrarUsuarioVenta();
        this.setLocationRelativeTo(myParentFrame);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void tblDetallePedidos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblDetallePedidos_keyReleased(KeyEvent e) {
        mostrarUsuarioVenta();
    }

    private void btnObservaciones_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtObservacionesDelivery);
    }

    private void btnFormasPago_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblListaFormasPago);
    }

    private void txtObservacionesDelivery_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblListaFormasPago_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btlRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblDetallePedidos);
    }

    /* ************************************************************************ */
    /*                      METODOS AUXILIARES DE EVENTOS                       */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F4 && lblF4.isVisible()) {
            lblNoPedidoT.setText(VariablesReporte.vCorrelativo);
            log.debug(VariablesReporte.vCorrelativo);
            listadoComprobanteVenta();
        } else if (e.getKeyCode() == KeyEvent.VK_F5 && lblF5.isVisible()) { /*lblNoPedidoT.setText(VariablesReporte.vCorrelativo);
            log.debug(VariablesReporte.vCorrelativo);
            listadoComprobanteVenta();*/
            listadoFormasPago();
        } else if (UtilityPtoVenta.verificaVK_F11(e) && lblF11.isVisible()) {
            aceptarResumenDelivery();
        } else if (UtilityPtoVenta.verificaVK_F12(e) && lblF12.isVisible()) {
            imprimir();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                      METODOS DE LOGICA DE NEGOCIO                        */
    /* ************************************************************************ */

    private void buscaDetalleVentas(String pCodigo) {
        VariablesReporte.vCorrelativo = pCodigo;
        cargaDetalleVentas();
    }

    private void listadoComprobanteVenta() {
        DlgComprobantes dlgComprobantes = new DlgComprobantes(myParentFrame, "", true);
        dlgComprobantes.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
        }
    }

    /**  Lista las formas de pago del Pedido
     * @author: JCORTEZ
     * @since:  04/08/2007
     */
    private void listadoFormasPago() {
        DlgFormasPago dlgformaspago = new DlgFormasPago(myParentFrame, "", true);
        dlgformaspago.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
        }
    }

    private void cargaVariables() {

        lblClienteT.setText(VariablesReporte.vCliente);
        lblDireccionT.setText(VariablesReporte.vDireccion);
        lblRucT.setText(VariablesReporte.vRuc);
        lblNoPedidoT.setText(VariablesReporte.vCorrelativo);
        lblFechaT.setText(VariablesReporte.vFecha.substring(0, 10));
        lblHoraT.setText(VariablesReporte.vHora);
        //lblAtencionT.setText(VariablesReporte.vUsuario);
        lblEstado.setText(VariablesReporte.vEstado);
        lblCodigolocal.setText(FarmaVariables.vCodLocal);
        lblDescLocal.setText(FarmaVariables.vDescLocal);
        buscaDetalleVentas(VariablesReporte.vCorrelativo);
        lblMontoBruto.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(), 8)).trim());
        lblDctoMonto.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(), 9)).trim());
        lblIgvMonto.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(), 10)).trim());
        lblTotalMonto.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(), 11)).trim());
        lblItems.setText("" + tblDetallePedidos.getRowCount());
        lblPctPercepcion.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(), 16)).trim());
        lblResumenMontoPercepcion.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(), 15)).trim());


        //ERIOS 2.4.4 Se muestra datos delivery
        if (indResumenDelivery) {
            this.setSize(new Dimension(anchoVentana, altoVentana));//this.setSize(new Dimension(750, 600));
            pnlDatosDelivery.setVisible(true);
            lblNombreDelivery.setText(VariablesDelivery.vNombreCliente);
            lblDireccionDelivery.setText(VariablesDelivery.vDireccion);
            lblReferencia.setText(VariablesDelivery.vReferencia);
            tableModelFormasPago.data = lstFormasPago;
            lblVuelto.setText(VariablesCaja.vVuelto);
            lblF4.setVisible(false);
            lblF5.setVisible(false);
            //lblF12.setVisible(false);
            lblF12.setText("[F12] Imp.Comanda");

            if (indVerDelivery) {
                txtObservacionesDelivery.setEnabled(false);
                lblF11.setVisible(false);
                lblF12.setVisible(true);
                FarmaUtility.moveFocusJTable(tblDetallePedidos);
                VariablesCaja.vVuelto =
                        FarmaUtility.formatNumber(FarmaUtility.sumColumTableModel(tableModelFormasPago, 3));
                lblVuelto.setText(VariablesCaja.vVuelto);
            } else {
                lblF11.setVisible(true);
                lblF12.setVisible(false);
                FarmaUtility.moveFocus(txtObservacionesDelivery);
            }
        } else {
            this.setSize(new Dimension(anchoVentana, altoVentana));//this.setSize(new Dimension(750, 470));
            lblF11.setVisible(false);
            pnlDatosDelivery.setVisible(false);
            pnlBotones.setLocation(5, 405);
        }
        //ERIOS 06.10.2015 Muestra impresion Detalle
        String strIndicador = getIndImprimirDetPedido();
        if(strIndicador.equals(FarmaConstants.INDICADOR_N)){
            lblF12.setVisible(false);
        }
    }
    
    

    private void mostrarUsuarioVenta() {
        String usuarioVenta;
        int total = tblDetallePedidos.getRowCount();
        if (total > 0) {
            usuarioVenta = ((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(), 12)).trim();
            lblAtencionT.setText(usuarioVenta);
        } else {
            usuarioVenta = "";
        }
    }

    private void imprimir() {
        if (indResumenDelivery) {
            log.info("aca imprimir comanda");
            UtilityCaja.imprimeDatosDelivery(this, VariablesReporte.vCorrelativo);
        } else {
            if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
                return;

            //FarmaPrintService vPrint = new FarmaPrintService(45, FarmaVariables.vImprReporte, true);
            FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
            log.debug(FarmaVariables.vImprReporte);
            if (!vPrint.startPrintService()) {
                FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", tblDetallePedidos);
                return;
            }

            try {
                String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
                String campoAlt = "________";

                vPrint.setStartHeader();
                vPrint.activateCondensed();
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) + " REPORTE  DETALLE DE PEDIDO", true);
                vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia, true);
                vPrint.printBold("Fecha : " + fechaActual, true);
                vPrint.printBold("Fecha y Hora Pedido: " + VariablesReporte.vFecha.substring(0, 10) + " " +
                                 VariablesReporte.vHora, true);
                vPrint.printBold("CORR : " + VariablesReporte.vCorrelativo, true);
                vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);

                vPrint.printLine("=====================================================================================================",
                                 true);
                vPrint.printBold("Codigo Cant   Descripcion                  Unidad     Lab.                 P.Unit    Dscto      Total",
                                 true);
                vPrint.printLine("=====================================================================================================",
                                 true);
                vPrint.deactivateCondensed();
                vPrint.setEndHeader();

                for (int i = 0; i < tblDetallePedidos.getRowCount(); i++) {
                    vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblDetallePedidos.getValueAt(i, 0),
                                                                           6) + " " +
                                          FarmaPRNUtility.alinearIzquierda((String)tblDetallePedidos.getValueAt(i, 1),
                                                                           6) + " " +
                                          FarmaPRNUtility.alinearIzquierda((String)tblDetallePedidos.getValueAt(i, 2),
                                                                           28) + " " +
                                          FarmaPRNUtility.alinearIzquierda((String)tblDetallePedidos.getValueAt(i, 3),
                                                                           10) + " " +
                                          FarmaPRNUtility.alinearIzquierda((String)tblDetallePedidos.getValueAt(i, 4),
                                                                           18) + " " +
                                          FarmaPRNUtility.alinearDerecha((String)tblDetallePedidos.getValueAt(i, 5),
                                                                         8) + " " +
                                          FarmaPRNUtility.alinearDerecha((String)tblDetallePedidos.getValueAt(i, 6),
                                                                         8) + " " +
                                          FarmaPRNUtility.alinearDerecha((String)tblDetallePedidos.getValueAt(i, 7),
                                                                         10), true);
                }
                vPrint.activateCondensed();
                vPrint.printLine("=====================================================================================================",
                                 true);
                vPrint.printBold("Total Monto S/. : " + FarmaPRNUtility.alinearDerecha(lblTotalMonto.getText(), 83),
                                 true);
                vPrint.printBold("Registros Impresos: " +
                                 FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(tblDetallePedidos.getRowCount(),
                                                                                          ",##0"), 10), true);
                vPrint.deactivateCondensed();
                vPrint.endPrintService();
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                        sql.getMessage(), null);
            }
        }
    }

    public void setIndResumenDelivery(boolean indResumenDelivery) {
        this.indResumenDelivery = indResumenDelivery;
    }

    public void setIndVerDelivery(boolean indVerDelivery) {
        this.indVerDelivery = indVerDelivery;
    }

    public void setLstFormasPago(ArrayList<ArrayList<String>> lstFormasPago) {
        this.lstFormasPago = lstFormasPago;
    }

    private void aceptarResumenDelivery() {

        cerrarVentana(true);
    }

    public String getObserPedidoVta() {
        return txtObservacionesDelivery.getText();
    }

    public void setObserPedidoVta(String pObserPedido) {
        txtObservacionesDelivery.setText(pObserPedido);
    }

    public void setLblComprobante(String pComprobante) {
        lblComprobante.setText(pComprobante);
    }

    public void setLblMotorizado(String pMotorizado) {
        lblMotorizado.setText(pMotorizado);
    }

    /**
     * @author ERIOS
     * @since 06.10.2015
     * @return
     */
    private String getIndImprimirDetPedido() {
        String strRetorno = "N";
        try {
            strRetorno = DBPtoVenta.getIndImprimirDetPedido();
        } catch (SQLException e) {
            log.error("",e);
        }
        return strRetorno;
    }
}
