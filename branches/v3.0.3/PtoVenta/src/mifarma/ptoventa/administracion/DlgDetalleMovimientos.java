package mifarma.ptoventa.administracion;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.reference.ConstantsAdministracion;
import mifarma.ptoventa.administracion.reference.VariablesAdministracion;
import mifarma.ptoventa.caja.DlgReportePedidosComprobantes;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDetalleMovimientos extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgDetalleMovimientos.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite jcontentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlComprobantesGenerados = new JPanelTitle();
    private JPanelHeader pnlTotGenerados = new JPanelHeader();
    private JPanelTitle pnlHeaderDetallesFormaPago = new JPanelTitle();
    private JPanelTitle pnlFooterTotFormPago = new JPanelTitle();
    private JScrollPane scrListaFormasPago = new JScrollPane();
    private JTable tblListaFormasPago = new JTable();
    private JLabelFunction lblImprimirDetalle = new JLabelFunction();
    private JLabelFunction lblDetalleComprobante = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelTitle pnlBusqFecVta = new JPanelTitle();
    private JLabelWhite lblFecVta = new JLabelWhite();
    private JTextFieldSanSerif txtFecVenta = new JTextFieldSanSerif();
    private JLabelWhite lblCajero_T = new JLabelWhite();
    private JLabelWhite lblCajero = new JLabelWhite();
    private JTextFieldSanSerif txtBoletasGen = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFacturasGen = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtGuiasGen = new JTextFieldSanSerif();
    private JLabelWhite lblGuiasGen_T = new JLabelWhite();
    private JLabelWhite lblTotalGenerados = new JLabelWhite();
    private JPanelHeader pnlTotAnulados = new JPanelHeader();
    private JLabelWhite lblTotalAnulados = new JLabelWhite();
    private JPanelTitle pnlComprobantesAnulados = new JPanelTitle();
    private JTextFieldSanSerif txtGuiasAnu = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFacturasAnu = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtBoletasAnu = new JTextFieldSanSerif();
    private JPanelHeader pnlTotalCompras = new JPanelHeader();
    private JLabelWhite lblTotCompras_T = new JLabelWhite();
    private JLabelWhite lblTotalCompras = new JLabelWhite();
    private JPanelTitle pnlComprobantesTotal = new JPanelTitle();
    private JTextFieldSanSerif txtGuiasTotal = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFacturasTotal = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtBoletasTotal = new JTextFieldSanSerif();
    private JLabelWhite lblFacturasGen_T = new JLabelWhite();
    private JLabelWhite lblBoletasGen_T = new JLabelWhite();
    private JLabelWhite lblCantFacturasGen = new JLabelWhite();
    private JLabelWhite lblCantBoletasGen = new JLabelWhite();
    private JTextFieldSanSerif txtTicketGen = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtTicketAn = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtTicketNC = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtTicketTot = new JTextFieldSanSerif();
    private JLabelWhite lblTicketsGen_T = new JLabelWhite();
    private JLabelWhite lblTicketsAn_T = new JLabelWhite();
    private JLabelWhite lblTicketsNC_T = new JLabelWhite();
    private JLabelWhite lblTicketsTot_T = new JLabelWhite();
    private JLabelWhite lblCantTicketsGen = new JLabelWhite();
    private JLabelWhite lblCantTicketsAn = new JLabelWhite();
    private JLabelWhite lblCantTicketsNC = new JLabelWhite();
    private JLabelWhite lblCantTicketsTot = new JLabelWhite();
    private JLabelWhite lblCantGuiasGen = new JLabelWhite();
    private JLabelWhite lblGuiasAnuT_T = new JLabelWhite();
    private JLabelWhite lblCantGuiasAnu = new JLabelWhite();
    private JLabelWhite lblFacturasAnu_T = new JLabelWhite();
    private JLabelWhite lblCantFacturasAnu = new JLabelWhite();
    private JLabelWhite lblBoletasAnu_T = new JLabelWhite();
    private JLabelWhite lblCantBoletasAnu = new JLabelWhite();
    private JLabelWhite lblTotGuias_T = new JLabelWhite();
    private JLabelWhite lblTotFacturas_T = new JLabelWhite();
    private JLabelWhite lblTotBoletas_T = new JLabelWhite();
    private JLabelWhite lblTotAnulados_T = new JLabelWhite();
    private JLabelWhite lblTotGenerados_T = new JLabelWhite();
    private JLabelWhite lblTotFormPago_T = new JLabelWhite();
    private JLabelWhite lblTotalFormasPago = new JLabelWhite();
    private JLabelWhite lblComprobantesGenerados_T = new JLabelWhite();
    private JLabelWhite lblCpmprobantesAnulados_T = new JLabelWhite();
    private JLabelWhite lblComprobantesTotal_T = new JLabelWhite();
    private JLabelWhite lblCantGuiasTot = new JLabelWhite();
    private JLabelWhite lblCantFacturasTot = new JLabelWhite();
    private JLabelWhite lblCantBoletasTot = new JLabelWhite();
    private JLabelWhite lblCaja = new JLabelWhite();
    private JLabelWhite lblCaja_T = new JLabelWhite();
    private JLabelWhite lblTurno = new JLabelWhite();
    private JLabelWhite lblTurno_T = new JLabelWhite();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JPanelTitle pnlComprobantesAnulados1 = new JPanelTitle();
    private JLabelWhite lblCpmprobantesAnulados_T1 = new JLabelWhite();
    private JLabelWhite lblCantNCBoletas = new JLabelWhite();
    private JLabelWhite lblBoletasAnu_T1 = new JLabelWhite();
    private JLabelWhite lblCantNCFacturas = new JLabelWhite();
    private JLabelWhite lblFacturasAnu_T1 = new JLabelWhite();
    private JTextFieldSanSerif txtNCFacturas = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNCBoletas = new JTextFieldSanSerif();
    private JPanelHeader pnlTotAnulados1 = new JPanelHeader();
    private JLabelWhite lblTotAnulados_T1 = new JLabelWhite();
    private JLabelWhite lblTotalNC = new JLabelWhite();
    private JTextFieldSanSerif txtNCTotal = new JTextFieldSanSerif();
    private JLabelWhite lblTotGuias_T1 = new JLabelWhite();
    private JLabelWhite lblCantNCTot = new JLabelWhite();
    private JTable tblListaFormasPago1 = new JTable();
    private JPanelTitle pnlComprobantesTotal1 = new JPanelTitle();
    private JLabelWhite lblDeliveryEmitido = new JLabelWhite();
    private JLabelWhite lblDeliveryAnulado = new JLabelWhite();
    private JLabelWhite lblComprobantesTotal_T1 = new JLabelWhite();
    private JLabelWhite lblTotBoletas_T1 = new JLabelWhite();
    private JLabelWhite lblTotFacturas_T1 = new JLabelWhite();
    private JTextFieldSanSerif txtDeliveryAnulado = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDeliveryEmitido = new JTextFieldSanSerif();
    private JPanelHeader pnlTotalCompras1 = new JPanelHeader();
    private JLabelWhite lblTotCompras_T1 = new JLabelWhite();
    private JLabelWhite lblTotalMontoDelivery = new JLabelWhite();
    private JLabelWhite lblTotalCantidadDelivery = new JLabelWhite();
    private JTextFieldSanSerif txtTicketFacGen = new JTextFieldSanSerif();
    private JLabelWhite lblTicketsFactGen_T = new JLabelWhite();
    private JLabelWhite lblCantTicketsFacGen = new JLabelWhite();
    private JLabelWhite lblCantTicketsFacTot = new JLabelWhite();
    private JLabelWhite lblTicketsFacTot_T = new JLabelWhite();
    private JTextField txtTicketFacTot = new JTextField();
    private JLabelWhite lblCantTicketsFacAn = new JLabelWhite();
    private JLabelWhite lblTicketsFacAn_T = new JLabelWhite();
    private JTextFieldSanSerif txtTicketFacAn = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtTicketFacNC = new JTextFieldSanSerif();
    private JLabelWhite lblTicketsFacNC_T = new JLabelWhite();
    private JLabelWhite lblCantTicketsFacNC = new JLabelWhite();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgDetalleMovimientos() {
        this(null, "", false);
    }

    public DlgDetalleMovimientos(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(702, 602)); // KMONCADA REDIMENSION DE LA VENTANA
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlComprobantesGenerados.setBounds(new Rectangle(10, 40, 285, 145));
        pnlComprobantesGenerados.setForeground(Color.white);
        pnlComprobantesGenerados.setBackground(Color.white);
        pnlComprobantesGenerados.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlComprobantesGenerados.setFocusable(false);
        pnlTotGenerados.setBounds(new Rectangle(10, 185, 285, 20));
        pnlTotGenerados.setFocusable(false);
        pnlHeaderDetallesFormaPago.setBounds(new Rectangle(300, 40, 380, 35));
        pnlHeaderDetallesFormaPago.setBackground(Color.white);
        pnlHeaderDetallesFormaPago.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlHeaderDetallesFormaPago.setFocusable(false);
        pnlFooterTotFormPago.setBounds(new Rectangle(300, 200, 380, 25));
        pnlFooterTotFormPago.setBackground(Color.white);
        pnlFooterTotFormPago.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlFooterTotFormPago.setFocusable(false);
        scrListaFormasPago.setBounds(new Rectangle(300, 65, 380, 135));
        scrListaFormasPago.setFocusable(false);
        tblListaFormasPago.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaFormasPago_keyPressed(e);
            }
        });
        lblImprimirDetalle.setBounds(new Rectangle(10, 535, 215, 20));
        lblImprimirDetalle.setText("[F2] Imprimir Detalle del Cierre");
        lblImprimirDetalle.setFocusable(false);
        lblDetalleComprobante.setBounds(new Rectangle(245, 535, 175, 20));
        lblDetalleComprobante.setText("[F5] Detalle Comprobantes");
        lblDetalleComprobante.setFocusable(false);
        lblEsc.setBounds(new Rectangle(535, 535, 145, 20));
        lblEsc.setText("[ESC] Cerrar");
        lblEsc.setFocusable(false);
        pnlBusqFecVta.setBounds(new Rectangle(10, 5, 670, 30));
        pnlBusqFecVta.setBackground(Color.white);
        pnlBusqFecVta.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlBusqFecVta.setFocusable(false);
        lblFecVta.setText("Fecha de Venta:");
        lblFecVta.setBounds(new Rectangle(5, 5, 90, 20));
        lblFecVta.setForeground(new Color(255, 130, 14));
        lblFecVta.setFocusable(false);
        txtFecVenta.setText("12/01/2006");
        txtFecVenta.setBounds(new Rectangle(95, 5, 85, 20));
        txtFecVenta.setHorizontalAlignment(JTextField.CENTER);
        txtFecVenta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecVenta_keyPressed(e);
            }
        });
        lblCajero_T.setText("Cajero:");
        lblCajero_T.setBounds(new Rectangle(190, 5, 45, 20));
        lblCajero_T.setForeground(new Color(255, 130, 14));
        lblCajero_T.setFocusable(false);
        lblCajero.setText("ANDRES MORENO");
        lblCajero.setBounds(new Rectangle(235, 5, 280, 20));
        lblCajero.setForeground(new Color(255, 130, 14));
        lblCajero.setFocusable(false);
        txtBoletasGen.setText("0");
        txtBoletasGen.setBounds(new Rectangle(160, 20, 115, 20));
        txtBoletasGen.setHorizontalAlignment(JTextField.RIGHT);
        txtBoletasGen.setEditable(false);
        txtFacturasGen.setText("0");
        txtFacturasGen.setBounds(new Rectangle(160, 45, 115, 20));
        txtFacturasGen.setHorizontalAlignment(JTextField.RIGHT);
        txtFacturasGen.setEditable(false);
        txtGuiasGen.setText("0");
        txtGuiasGen.setBounds(new Rectangle(160, 70, 115, 20));
        txtGuiasGen.setHorizontalAlignment(JTextField.RIGHT);
        txtGuiasGen.setEditable(false);
        lblGuiasGen_T.setText("Guías");
        lblGuiasGen_T.setBounds(new Rectangle(60, 70, 95, 20));
        lblGuiasGen_T.setForeground(new Color(255, 130, 14));
        lblGuiasGen_T.setFocusable(false);
        lblTotalGenerados.setText("0");
        lblTotalGenerados.setBounds(new Rectangle(160, 0, 115, 20));
        lblTotalGenerados.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalGenerados.setFocusable(false);
        pnlTotAnulados.setBounds(new Rectangle(10, 360, 285, 20));
        pnlTotAnulados.setFocusable(false);
        lblTotalAnulados.setText("0");
        lblTotalAnulados.setBounds(new Rectangle(160, 0, 115, 20));
        lblTotalAnulados.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalAnulados.setFocusable(false);
        pnlComprobantesAnulados.setBounds(new Rectangle(10, 210, 285, 150));
        pnlComprobantesAnulados.setForeground(Color.white);
        pnlComprobantesAnulados.setBackground(Color.white);
        pnlComprobantesAnulados.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlComprobantesAnulados.setFocusable(false);
        txtGuiasAnu.setText("0");
        txtGuiasAnu.setBounds(new Rectangle(160, 70, 115, 20));
        txtGuiasAnu.setHorizontalAlignment(JTextField.RIGHT);
        txtGuiasAnu.setEditable(false);
        txtFacturasAnu.setText("0");
        txtFacturasAnu.setBounds(new Rectangle(160, 45, 115, 20));
        txtFacturasAnu.setHorizontalAlignment(JTextField.RIGHT);
        txtFacturasAnu.setEditable(false);
        txtBoletasAnu.setText("0");
        txtBoletasAnu.setBounds(new Rectangle(160, 20, 115, 20));
        txtBoletasAnu.setHorizontalAlignment(JTextField.RIGHT);
        txtBoletasAnu.setEditable(false);
        pnlTotalCompras.setBounds(new Rectangle(300, 505, 380, 20));
        lblTotCompras_T.setText("TOTAL COMPR.: S/.");
        lblTotCompras_T.setBounds(new Rectangle(5, 0, 150, 20));
        lblTotCompras_T.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotCompras_T.setFont(new Font("SansSerif", 1, 12));
        lblTotCompras_T.setFocusable(false);
        lblTotalCompras.setText("0");
        lblTotalCompras.setBounds(new Rectangle(250, 5, 115, 15));
        lblTotalCompras.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalCompras.setFocusable(false);
        pnlComprobantesTotal.setBounds(new Rectangle(300, 335, 380, 170));
        pnlComprobantesTotal.setForeground(Color.white);
        pnlComprobantesTotal.setBackground(Color.white);
        pnlComprobantesTotal.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlComprobantesTotal.setFocusable(false);
        txtGuiasTotal.setText("0");
        txtGuiasTotal.setBounds(new Rectangle(170, 70, 115, 20));
        txtGuiasTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtGuiasTotal.setEditable(false);
        txtFacturasTotal.setText("0");
        txtFacturasTotal.setBounds(new Rectangle(170, 45, 115, 20));
        txtFacturasTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtFacturasTotal.setEditable(false);
        txtBoletasTotal.setText("0");
        txtBoletasTotal.setBounds(new Rectangle(170, 20, 115, 20));
        txtBoletasTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtBoletasTotal.setEditable(false);
        lblFacturasGen_T.setText("Facturas");
        lblFacturasGen_T.setBounds(new Rectangle(60, 45, 95, 20));
        lblFacturasGen_T.setForeground(new Color(255, 130, 14));
        lblFacturasGen_T.setFocusable(false);
        lblBoletasGen_T.setText("Boletas");
        lblBoletasGen_T.setBounds(new Rectangle(60, 20, 95, 20));
        lblBoletasGen_T.setHorizontalAlignment(SwingConstants.LEFT);
        lblBoletasGen_T.setForeground(new Color(255, 130, 14));
        lblBoletasGen_T.setFocusable(false);
        lblCantFacturasGen.setText("0");
        lblCantFacturasGen.setBounds(new Rectangle(5, 45, 45, 20));
        lblCantFacturasGen.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantFacturasGen.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantFacturasGen.setForeground(new Color(255, 130, 14));

        //INICIO parametros para comprobantes tipo ticket
        lblCantFacturasGen.setFocusable(false);
        txtTicketGen.setText("0");
        txtTicketGen.setBounds(new Rectangle(160, 95, 115, 20));
        txtTicketGen.setHorizontalAlignment(JTextField.RIGHT);
        txtTicketGen.setEditable(false);
        txtTicketAn.setText("0");
        txtTicketAn.setBounds(new Rectangle(160, 95, 115, 20));
        txtTicketAn.setHorizontalAlignment(JTextField.RIGHT);
        txtTicketAn.setEditable(false);
        txtTicketNC.setText("0");
        txtTicketNC.setBounds(new Rectangle(160, 70, 115, 20));
        txtTicketNC.setHorizontalAlignment(JTextField.RIGHT);
        txtTicketNC.setEditable(false);
        txtTicketTot.setText("0");
        txtTicketTot.setBounds(new Rectangle(170, 120, 115, 20));
        txtTicketTot.setHorizontalAlignment(JTextField.RIGHT);
        txtTicketTot.setEditable(false);
        lblTicketsGen_T.setText("Tickets Boletas");
        lblTicketsGen_T.setBounds(new Rectangle(60, 95, 95, 20));
        lblTicketsGen_T.setHorizontalAlignment(SwingConstants.LEFT);
        lblTicketsGen_T.setForeground(new Color(255, 130, 14));

        lblTicketsGen_T.setFocusable(false);
        lblTicketsAn_T.setText("Tickets Boletas");
        lblTicketsAn_T.setBounds(new Rectangle(60, 95, 95, 20));
        lblTicketsAn_T.setHorizontalAlignment(SwingConstants.LEFT);
        lblTicketsAn_T.setForeground(new Color(255, 130, 14));

        lblTicketsAn_T.setFocusable(false);
        lblTicketsNC_T.setText("Tickets Boleta");
        lblTicketsNC_T.setBounds(new Rectangle(60, 70, 95, 20));
        lblTicketsNC_T.setHorizontalAlignment(SwingConstants.LEFT);
        lblTicketsNC_T.setForeground(new Color(255, 130, 14));

        lblTicketsNC_T.setFocusable(false);
        lblTicketsTot_T.setText("Tickets Boletas");
        lblTicketsTot_T.setBounds(new Rectangle(60, 120, 95, 20));
        lblTicketsTot_T.setHorizontalAlignment(SwingConstants.LEFT);
        lblTicketsTot_T.setForeground(new Color(255, 130, 14));

        lblTicketsTot_T.setFocusable(false);
        lblCantTicketsGen.setText("0");
        lblCantTicketsGen.setBounds(new Rectangle(5, 95, 45, 20));
        lblCantTicketsGen.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantTicketsGen.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantTicketsGen.setForeground(new Color(255, 130, 14));

        lblCantTicketsGen.setFocusable(false);
        lblCantTicketsAn.setText("0");
        lblCantTicketsAn.setBounds(new Rectangle(5, 95, 45, 20));
        lblCantTicketsAn.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantTicketsAn.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantTicketsAn.setForeground(new Color(255, 130, 14));

        lblCantTicketsAn.setFocusable(false);
        lblCantTicketsNC.setText("0");
        lblCantTicketsNC.setBounds(new Rectangle(5, 70, 45, 20));
        lblCantTicketsNC.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantTicketsNC.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantTicketsNC.setForeground(new Color(255, 130, 14));

        lblCantTicketsNC.setFocusable(false);
        lblCantTicketsTot.setText("0");
        lblCantTicketsTot.setBounds(new Rectangle(5, 120, 45, 20));
        lblCantTicketsTot.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantTicketsTot.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantTicketsTot.setForeground(new Color(255, 130, 14));
        //FIN lparametros para comprobantes tipo ticket

        lblCantTicketsTot.setFocusable(false);
        lblCantBoletasGen.setText("0");
        lblCantBoletasGen.setBounds(new Rectangle(5, 25, 45, 15));
        lblCantBoletasGen.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantBoletasGen.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantBoletasGen.setForeground(new Color(255, 130, 14));
        lblCantBoletasGen.setFocusable(false);
        lblCantGuiasGen.setText("0");
        lblCantGuiasGen.setBounds(new Rectangle(5, 70, 45, 20));
        lblCantGuiasGen.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantGuiasGen.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantGuiasGen.setForeground(new Color(255, 130, 14));
        lblCantGuiasGen.setFocusable(false);
        lblGuiasAnuT_T.setText("Guías");
        lblGuiasAnuT_T.setBounds(new Rectangle(60, 70, 95, 20));
        lblGuiasAnuT_T.setForeground(new Color(255, 130, 14));
        lblGuiasAnuT_T.setFocusable(false);
        lblCantGuiasAnu.setText("0");
        lblCantGuiasAnu.setBounds(new Rectangle(5, 70, 45, 20));
        lblCantGuiasAnu.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantGuiasAnu.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantGuiasAnu.setForeground(new Color(255, 130, 14));
        lblCantGuiasAnu.setFocusable(false);
        lblFacturasAnu_T.setText("Facturas");
        lblFacturasAnu_T.setBounds(new Rectangle(60, 45, 95, 20));
        lblFacturasAnu_T.setForeground(new Color(255, 130, 14));
        lblFacturasAnu_T.setFocusable(false);
        lblCantFacturasAnu.setText("0");
        lblCantFacturasAnu.setBounds(new Rectangle(5, 45, 45, 20));
        lblCantFacturasAnu.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantFacturasAnu.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantFacturasAnu.setForeground(new Color(255, 130, 14));
        lblCantFacturasAnu.setFocusable(false);
        lblBoletasAnu_T.setText("Boletas");
        lblBoletasAnu_T.setBounds(new Rectangle(60, 20, 95, 20));
        lblBoletasAnu_T.setHorizontalAlignment(SwingConstants.LEFT);
        lblBoletasAnu_T.setForeground(new Color(255, 130, 14));
        lblBoletasAnu_T.setFocusable(false);
        lblCantBoletasAnu.setText("0");
        lblCantBoletasAnu.setBounds(new Rectangle(5, 20, 45, 20));
        lblCantBoletasAnu.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantBoletasAnu.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantBoletasAnu.setForeground(new Color(255, 130, 14));
        lblCantBoletasAnu.setFocusable(false);
        lblTotGuias_T.setText("Guías");
        lblTotGuias_T.setBounds(new Rectangle(60, 70, 95, 20));
        lblTotGuias_T.setForeground(new Color(255, 130, 14));
        lblTotGuias_T.setFocusable(false);
        lblTotFacturas_T.setText("Facturas");
        lblTotFacturas_T.setBounds(new Rectangle(60, 45, 95, 20));
        lblTotFacturas_T.setForeground(new Color(255, 130, 14));
        lblTotFacturas_T.setFocusable(false);
        lblTotBoletas_T.setText("Boletas");
        lblTotBoletas_T.setBounds(new Rectangle(60, 20, 95, 20));
        lblTotBoletas_T.setForeground(new Color(255, 130, 14));
        lblTotBoletas_T.setFocusable(false);
        lblTotAnulados_T.setText("ANULADOS: S./");
        lblTotAnulados_T.setBounds(new Rectangle(5, 0, 150, 20));
        lblTotAnulados_T.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotAnulados_T.setFocusable(false);
        lblTotGenerados_T.setText("GENERADOS: S/.");
        lblTotGenerados_T.setBounds(new Rectangle(5, 0, 150, 20));
        lblTotGenerados_T.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotGenerados_T.setFont(new Font("SansSerif", 1, 12));
        lblTotGenerados_T.setFocusable(false);
        lblTotFormPago_T.setText("TOTAL FORMAS PAGO: S/.");
        lblTotFormPago_T.setBounds(new Rectangle(60, 0, 180, 25));
        lblTotFormPago_T.setFont(new Font("SansSerif", 1, 13));
        lblTotFormPago_T.setForeground(new Color(255, 130, 14));
        lblTotFormPago_T.setFocusable(false);
        lblTotalFormasPago.setText("0");
        lblTotalFormasPago.setBounds(new Rectangle(250, 0, 125, 25));
        lblTotalFormasPago.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalFormasPago.setForeground(new Color(255, 130, 14));
        lblTotalFormasPago.setFocusable(false);
        lblComprobantesGenerados_T.setText("Comprobantes Generados");
        lblComprobantesGenerados_T.setBounds(new Rectangle(5, 0, 150, 20));
        lblComprobantesGenerados_T.setHorizontalAlignment(SwingConstants.LEFT);
        lblComprobantesGenerados_T.setVerticalAlignment(SwingConstants.TOP);
        lblComprobantesGenerados_T.setForeground(new Color(255, 130, 14));
        lblComprobantesGenerados_T.setFocusable(false);
        lblCpmprobantesAnulados_T.setText("Comprobantes Anulados");
        lblCpmprobantesAnulados_T.setBounds(new Rectangle(5, 0, 145, 20));
        lblCpmprobantesAnulados_T.setHorizontalAlignment(SwingConstants.LEFT);
        lblCpmprobantesAnulados_T.setVerticalAlignment(SwingConstants.TOP);
        lblCpmprobantesAnulados_T.setForeground(new Color(255, 130, 14));
        lblCpmprobantesAnulados_T.setFocusable(false);
        lblComprobantesTotal_T.setText("Comprobantes Total");
        lblComprobantesTotal_T.setBounds(new Rectangle(5, 0, 135, 20));
        lblComprobantesTotal_T.setHorizontalAlignment(SwingConstants.LEFT);
        lblComprobantesTotal_T.setVerticalAlignment(SwingConstants.TOP);
        lblComprobantesTotal_T.setForeground(new Color(255, 130, 14));
        lblComprobantesTotal_T.setFocusable(false);
        lblCantGuiasTot.setText("0");
        lblCantGuiasTot.setBounds(new Rectangle(5, 70, 45, 20));
        lblCantGuiasTot.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantGuiasTot.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantGuiasTot.setForeground(new Color(255, 130, 14));
        lblCantGuiasTot.setFocusable(false);
        lblCantFacturasTot.setText("0");
        lblCantFacturasTot.setBounds(new Rectangle(5, 45, 45, 20));
        lblCantFacturasTot.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantFacturasTot.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantFacturasTot.setForeground(new Color(255, 130, 14));
        lblCantFacturasTot.setFocusable(false);
        lblCantBoletasTot.setText("0");
        lblCantBoletasTot.setBounds(new Rectangle(5, 20, 45, 20));
        lblCantBoletasTot.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantBoletasTot.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantBoletasTot.setForeground(new Color(255, 130, 14));
        lblCantBoletasTot.setFocusable(false);
        lblCaja.setText("1");
        lblCaja.setBounds(new Rectangle(555, 5, 35, 20));
        lblCaja.setForeground(new Color(255, 130, 14));
        lblCaja.setFocusable(false);
        lblCaja_T.setText("Caja:");
        lblCaja_T.setBounds(new Rectangle(520, 5, 35, 20));
        lblCaja_T.setForeground(new Color(255, 130, 14));
        lblCaja_T.setFocusable(false);
        lblTurno.setText("1");
        lblTurno.setBounds(new Rectangle(635, 5, 30, 20));
        lblTurno.setForeground(new Color(255, 130, 14));
        lblTurno.setFocusable(false);
        lblTurno_T.setText("Turno:");
        lblTurno_T.setBounds(new Rectangle(590, 5, 45, 20));
        lblTurno_T.setForeground(new Color(255, 130, 14));
        lblTurno_T.setFocusable(false);
        jButtonLabel1.setText("Detalles de Formas de Pago:");
        jButtonLabel1.setBounds(new Rectangle(10, 5, 180, 15));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('D');
        jButtonLabel1.setFocusable(false);
        jButtonLabel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        pnlComprobantesAnulados1.setBounds(new Rectangle(10, 385, 285, 120));
        pnlComprobantesAnulados1.setForeground(Color.white);
        pnlComprobantesAnulados1.setBackground(Color.white);
        pnlComprobantesAnulados1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlComprobantesAnulados1.setFocusable(false);
        lblCpmprobantesAnulados_T1.setText("Notas de Crédito");
        lblCpmprobantesAnulados_T1.setBounds(new Rectangle(5, 0, 145, 20));
        lblCpmprobantesAnulados_T1.setHorizontalAlignment(SwingConstants.LEFT);
        lblCpmprobantesAnulados_T1.setVerticalAlignment(SwingConstants.TOP);
        lblCpmprobantesAnulados_T1.setForeground(new Color(255, 130, 14));
        lblCpmprobantesAnulados_T1.setFocusable(false);
        lblCantNCBoletas.setText("0");
        lblCantNCBoletas.setBounds(new Rectangle(5, 20, 45, 20));
        lblCantNCBoletas.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantNCBoletas.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantNCBoletas.setForeground(new Color(255, 130, 14));
        lblCantNCBoletas.setFocusable(false);
        lblBoletasAnu_T1.setText("Boletas");
        lblBoletasAnu_T1.setBounds(new Rectangle(60, 20, 95, 20));
        lblBoletasAnu_T1.setHorizontalAlignment(SwingConstants.LEFT);
        lblBoletasAnu_T1.setForeground(new Color(255, 130, 14));
        lblBoletasAnu_T1.setFocusable(false);
        lblCantNCFacturas.setText("0");
        lblCantNCFacturas.setBounds(new Rectangle(5, 45, 45, 20));
        lblCantNCFacturas.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantNCFacturas.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantNCFacturas.setForeground(new Color(255, 130, 14));
        lblCantNCFacturas.setFocusable(false);
        lblFacturasAnu_T1.setText("Facturas");
        lblFacturasAnu_T1.setBounds(new Rectangle(60, 45, 95, 20));
        lblFacturasAnu_T1.setForeground(new Color(255, 130, 14));
        lblFacturasAnu_T1.setFocusable(false);
        txtNCFacturas.setText("0");
        txtNCFacturas.setBounds(new Rectangle(160, 45, 115, 20));
        txtNCFacturas.setHorizontalAlignment(JTextField.RIGHT);
        txtNCFacturas.setEditable(false);
        txtNCBoletas.setText("0");
        txtNCBoletas.setBounds(new Rectangle(160, 20, 115, 20));
        txtNCBoletas.setHorizontalAlignment(JTextField.RIGHT);
        txtNCBoletas.setEditable(false);
        pnlTotAnulados1.setBounds(new Rectangle(10, 505, 285, 20));
        pnlTotAnulados1.setFocusable(false);
        lblTotAnulados_T1.setText("NOTAS CREDITO: S./");
        lblTotAnulados_T1.setBounds(new Rectangle(5, 0, 150, 20));
        lblTotAnulados_T1.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotAnulados_T1.setFocusable(false);
        lblTotalNC.setText("0");
        lblTotalNC.setBounds(new Rectangle(160, 0, 115, 20));
        lblTotalNC.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalNC.setFocusable(false);
        txtNCTotal.setText("0");
        txtNCTotal.setBounds(new Rectangle(170, 95, 115, 20));
        txtNCTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtNCTotal.setEditable(false);
        lblTotGuias_T1.setText("Notas de Crédito");
        lblTotGuias_T1.setBounds(new Rectangle(60, 95, 95, 20));
        lblTotGuias_T1.setForeground(new Color(255, 130, 14));
        lblTotGuias_T1.setFocusable(false);
        lblCantNCTot.setText("0");
        lblCantNCTot.setBounds(new Rectangle(5, 95, 45, 20));
        lblCantNCTot.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantNCTot.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantNCTot.setForeground(new Color(255, 130, 14));
        lblCantNCTot.setFocusable(false);
        tblListaFormasPago1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaFormasPago_keyPressed(e);
            }
        });
        tblListaFormasPago1.setBounds(new Rectangle(0, 0, 0, 0));
        pnlComprobantesTotal1.setBounds(new Rectangle(300, 230, 380, 80));
        pnlComprobantesTotal1.setForeground(Color.white);
        pnlComprobantesTotal1.setBackground(Color.white);
        pnlComprobantesTotal1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlComprobantesTotal1.setFocusable(false);
        lblDeliveryEmitido.setText("0");
        lblDeliveryEmitido.setBounds(new Rectangle(170, 25, 45, 20));
        lblDeliveryEmitido.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblDeliveryEmitido.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDeliveryEmitido.setForeground(new Color(255, 130, 14));
        lblDeliveryEmitido.setFocusable(false);
        lblDeliveryAnulado.setText("0");
        lblDeliveryAnulado.setBounds(new Rectangle(170, 50, 45, 20));
        lblDeliveryAnulado.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblDeliveryAnulado.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDeliveryAnulado.setForeground(new Color(255, 130, 14));
        lblDeliveryAnulado.setFocusable(false);
        lblComprobantesTotal_T1.setText("Comprobantes Total");
        lblComprobantesTotal_T1.setBounds(new Rectangle(5, 0, 135, 20));
        lblComprobantesTotal_T1.setHorizontalAlignment(SwingConstants.LEFT);
        lblComprobantesTotal_T1.setVerticalAlignment(SwingConstants.TOP);
        lblComprobantesTotal_T1.setForeground(new Color(255, 130, 14));
        lblComprobantesTotal_T1.setFocusable(false);
        lblTotBoletas_T1.setText("Delivery Emitido");
        lblTotBoletas_T1.setBounds(new Rectangle(30, 25, 95, 20));
        lblTotBoletas_T1.setForeground(new Color(255, 130, 14));
        lblTotBoletas_T1.setFocusable(false);
        lblTotFacturas_T1.setText("Delivery Anulado");
        lblTotFacturas_T1.setBounds(new Rectangle(30, 50, 95, 20));
        lblTotFacturas_T1.setForeground(new Color(255, 130, 14));
        lblTotFacturas_T1.setFocusable(false);
        txtDeliveryAnulado.setText("0");
        txtDeliveryAnulado.setBounds(new Rectangle(250, 50, 115, 20));
        txtDeliveryAnulado.setHorizontalAlignment(JTextField.RIGHT);
        txtDeliveryAnulado.setEditable(false);
        txtDeliveryEmitido.setText("0");
        txtDeliveryEmitido.setBounds(new Rectangle(250, 25, 115, 20));
        txtDeliveryEmitido.setHorizontalAlignment(JTextField.RIGHT);
        txtDeliveryEmitido.setEditable(false);
        pnlTotalCompras1.setBounds(new Rectangle(300, 310, 380, 20));
        pnlTotalCompras1.setFocusable(false);
        lblTotCompras_T1.setText("TOTAL DELIVERY:");
        lblTotCompras_T1.setBounds(new Rectangle(5, 0, 150, 20));
        lblTotCompras_T1.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotCompras_T1.setFont(new Font("SansSerif", 1, 12));
        lblTotCompras_T1.setFocusable(false);
        lblTotalMontoDelivery.setText("0");
        lblTotalMontoDelivery.setBounds(new Rectangle(245, 5, 115, 15));
        lblTotalMontoDelivery.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalMontoDelivery.setFocusable(false);
        lblTotalCantidadDelivery.setText("0");
        lblTotalCantidadDelivery.setBounds(new Rectangle(160, 5, 55, 15));
        lblTotalCantidadDelivery.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalCantidadDelivery.setFocusable(false);
        txtTicketFacGen.setBounds(new Rectangle(160, 120, 115, 20));
        txtTicketFacGen.setEditable(false);
        txtTicketFacGen.setHorizontalAlignment(JTextField.RIGHT);
        txtTicketFacGen.setText("0");
        lblTicketsFactGen_T.setText("Tickets Facturas");
        lblTicketsFactGen_T.setBounds(new Rectangle(60, 120, 95, 20));
        lblTicketsFactGen_T.setForeground(new Color(255, 130, 14));
        lblCantTicketsFacGen.setText("0");
        lblCantTicketsFacGen.setBounds(new Rectangle(5, 120, 45, 20));
        lblCantTicketsFacGen.setForeground(new Color(255, 130, 14));
        lblCantTicketsFacGen.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantTicketsFacTot.setText("0");
        lblCantTicketsFacTot.setBounds(new Rectangle(5, 145, 45, 20));
        lblCantTicketsFacTot.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantTicketsFacTot.setForeground(new Color(255, 130, 14));
        lblTicketsFacTot_T.setText("Tickets Facturas");
        lblTicketsFacTot_T.setBounds(new Rectangle(60, 145, 95, 20));
        lblTicketsFacTot_T.setForeground(new Color(255, 130, 14));
        txtTicketFacTot.setBounds(new Rectangle(170, 145, 115, 20));
        txtTicketFacTot.setEditable(false);
        txtTicketFacTot.setText("0");
        txtTicketFacTot.setHorizontalAlignment(JTextField.RIGHT);
        lblCantTicketsFacAn.setText("0");
        lblCantTicketsFacAn.setBounds(new Rectangle(5, 120, 45, 20));
        lblCantTicketsFacAn.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantTicketsFacAn.setForeground(new Color(255, 130, 14));
        lblTicketsFacAn_T.setText("Tickets Facturas");
        lblTicketsFacAn_T.setBounds(new Rectangle(60, 120, 95, 20));
        lblTicketsFacAn_T.setForeground(new Color(255, 130, 14));
        txtTicketFacAn.setBounds(new Rectangle(160, 120, 115, 20));
        txtTicketFacAn.setEditable(false);
        txtTicketFacAn.setText("0");
        txtTicketFacAn.setHorizontalAlignment(JTextField.RIGHT);
        txtTicketFacNC.setBounds(new Rectangle(160, 95, 115, 20));
        txtTicketFacNC.setText("0");
        txtTicketFacNC.setHorizontalAlignment(JTextField.RIGHT);
        txtTicketFacNC.setEditable(false);
        lblTicketsFacNC_T.setText("Tickets Factura");
        lblTicketsFacNC_T.setBounds(new Rectangle(60, 95, 95, 20));
        lblTicketsFacNC_T.setForeground(new Color(255, 130, 14));
        lblCantTicketsFacNC.setText("0");
        lblCantTicketsFacNC.setBounds(new Rectangle(5, 95, 45, 20));
        lblCantTicketsFacNC.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantTicketsFacNC.setForeground(new Color(255, 130, 14));
        pnlTotalCompras1.add(lblTotalCantidadDelivery, null);
        pnlTotalCompras1.add(lblTotCompras_T1, null);
        pnlTotalCompras1.add(lblTotalMontoDelivery, null);
        pnlComprobantesTotal1.add(lblDeliveryEmitido, null);
        pnlComprobantesTotal1.add(lblDeliveryAnulado, null);
        pnlComprobantesTotal1.add(lblComprobantesTotal_T1, null);
        pnlComprobantesTotal1.add(lblTotBoletas_T1, null);
        pnlComprobantesTotal1.add(lblTotFacturas_T1, null);
        pnlComprobantesTotal1.add(txtDeliveryAnulado, null);
        pnlComprobantesTotal1.add(txtDeliveryEmitido, null);
        pnlTotAnulados1.add(lblTotAnulados_T1, null);
        pnlTotAnulados1.add(lblTotalNC, null);
        pnlComprobantesAnulados1.add(lblCantTicketsFacNC, null);
        pnlComprobantesAnulados1.add(lblTicketsFacNC_T, null);
        pnlComprobantesAnulados1.add(txtTicketFacNC, null);
        pnlComprobantesAnulados1.add(lblCpmprobantesAnulados_T1, null);
        pnlComprobantesAnulados1.add(lblCantNCBoletas, null);
        pnlComprobantesAnulados1.add(lblBoletasAnu_T1, null);
        pnlComprobantesAnulados1.add(lblCantNCFacturas, null);
        pnlComprobantesAnulados1.add(lblFacturasAnu_T1, null);
        pnlComprobantesAnulados1.add(txtNCFacturas, null);
        pnlComprobantesAnulados1.add(txtNCBoletas, null);
        pnlComprobantesAnulados1.add(lblCantTicketsNC, null);
        pnlComprobantesAnulados1.add(lblTicketsNC_T, null);
        pnlComprobantesAnulados1.add(txtTicketNC, null);
        pnlTotalCompras.add(lblTotCompras_T, null);
        pnlTotalCompras.add(lblTotalCompras, null);
        pnlComprobantesTotal.add(txtTicketFacTot, null);
        pnlComprobantesTotal.add(lblTicketsFacTot_T, null);
        pnlComprobantesTotal.add(lblCantTicketsFacTot, null);
        pnlComprobantesTotal.add(lblCantNCTot, null);
        pnlComprobantesTotal.add(lblTotGuias_T1, null);
        pnlComprobantesTotal.add(txtNCTotal, null);
        pnlComprobantesTotal.add(lblCantBoletasTot, null);
        pnlComprobantesTotal.add(lblCantFacturasTot, null);
        pnlComprobantesTotal.add(lblCantGuiasTot, null);
        pnlComprobantesTotal.add(lblComprobantesTotal_T, null);
        pnlComprobantesTotal.add(lblTotBoletas_T, null);
        pnlComprobantesTotal.add(lblTotFacturas_T, null);
        pnlComprobantesTotal.add(lblTotGuias_T, null);
        pnlComprobantesTotal.add(txtGuiasTotal, null);
        pnlComprobantesTotal.add(txtFacturasTotal, null);
        pnlComprobantesTotal.add(txtBoletasTotal, null);
        pnlComprobantesTotal.add(lblCantTicketsTot, null);
        pnlComprobantesTotal.add(lblTicketsTot_T, null);
        pnlComprobantesTotal.add(txtTicketTot, null);
        pnlTotAnulados.add(lblTotAnulados_T, null);
        pnlTotAnulados.add(lblTotalAnulados, null);
        pnlComprobantesAnulados.add(txtTicketFacAn, null);
        pnlComprobantesAnulados.add(lblTicketsFacAn_T, null);
        pnlComprobantesAnulados.add(lblCantTicketsFacAn, null);
        pnlComprobantesAnulados.add(lblCpmprobantesAnulados_T, null);
        pnlComprobantesAnulados.add(lblCantBoletasAnu, null);
        pnlComprobantesAnulados.add(lblBoletasAnu_T, null);
        pnlComprobantesAnulados.add(lblCantFacturasAnu, null);
        pnlComprobantesAnulados.add(lblFacturasAnu_T, null);
        pnlComprobantesAnulados.add(lblCantGuiasAnu, null);
        pnlComprobantesAnulados.add(lblGuiasAnuT_T, null);
        pnlComprobantesAnulados.add(txtGuiasAnu, null);
        pnlComprobantesAnulados.add(txtFacturasAnu, null);
        pnlComprobantesAnulados.add(txtBoletasAnu, null);
        pnlComprobantesAnulados.add(lblCantTicketsAn, null);
        pnlComprobantesAnulados.add(lblTicketsAn_T, null);
        pnlComprobantesAnulados.add(txtTicketAn, null);
        pnlBusqFecVta.add(lblTurno_T, null);
        pnlBusqFecVta.add(lblTurno, null);
        pnlBusqFecVta.add(lblCaja_T, null);
        pnlBusqFecVta.add(lblCaja, null);
        pnlBusqFecVta.add(lblCajero, null);
        pnlBusqFecVta.add(lblCajero_T, null);
        pnlBusqFecVta.add(txtFecVenta, null);
        pnlBusqFecVta.add(lblFecVta, null);
        jcontentPane.add(pnlTotalCompras1, null);
        jcontentPane.add(pnlComprobantesTotal1, null);
        jcontentPane.add(tblListaFormasPago1, null);
        jcontentPane.add(pnlTotAnulados1, null);
        jcontentPane.add(pnlComprobantesAnulados1, null);
        jcontentPane.add(pnlComprobantesTotal, null);
        jcontentPane.add(pnlTotalCompras, null);
        jcontentPane.add(pnlComprobantesAnulados, null);
        jcontentPane.add(pnlTotAnulados, null);
        jcontentPane.add(pnlBusqFecVta, null);
        jcontentPane.add(lblEsc, null);
        jcontentPane.add(lblDetalleComprobante, null);
        jcontentPane.add(lblImprimirDetalle, null);
        scrListaFormasPago.getViewport().add(tblListaFormasPago, null);
        jcontentPane.add(scrListaFormasPago, null);
        jcontentPane.add(pnlFooterTotFormPago, null);
        pnlHeaderDetallesFormaPago.add(jButtonLabel1, null);
        jcontentPane.add(pnlHeaderDetallesFormaPago, null);
        jcontentPane.add(pnlTotGenerados, null);
        jcontentPane.add(pnlComprobantesGenerados, null);
        pnlFooterTotFormPago.add(lblTotalFormasPago, null);
        pnlFooterTotFormPago.add(lblTotFormPago_T, null);
        pnlTotGenerados.add(lblTotGenerados_T, null);
        pnlTotGenerados.add(lblTotalGenerados, null);
        pnlComprobantesGenerados.add(lblCantTicketsFacGen, null);
        pnlComprobantesGenerados.add(lblTicketsFactGen_T, null);
        pnlComprobantesGenerados.add(txtTicketFacGen, null);
        pnlComprobantesGenerados.add(lblComprobantesGenerados_T, null);

        pnlComprobantesGenerados.add(lblCantGuiasGen, null);
        pnlComprobantesGenerados.add(lblCantTicketsGen, null);
        pnlComprobantesGenerados.add(lblCantFacturasGen, null);
        pnlComprobantesGenerados.add(lblBoletasGen_T, null);
        pnlComprobantesGenerados.add(lblTicketsGen_T, null);
        pnlComprobantesGenerados.add(lblFacturasGen_T, null);
        pnlComprobantesGenerados.add(lblGuiasGen_T, null);
        pnlComprobantesGenerados.add(txtGuiasGen, null);
        pnlComprobantesGenerados.add(txtFacturasGen, null);
        pnlComprobantesGenerados.add(txtBoletasGen, null);
        pnlComprobantesGenerados.add(lblCantBoletasGen, null);
        pnlComprobantesGenerados.add(txtTicketGen, null);
        this.getContentPane().add(jcontentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
        iniciarValoresFecha();
        FarmaUtility.centrarVentana(this);
        obtieneDatosdelivery();
        calculoTotalDelivery();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsAdministracion.columnsListaMovimientos, ConstantsAdministracion.defaultValuesListaMovimientos,
                                    0);
        FarmaUtility.initSimpleList(tblListaFormasPago, tableModel, ConstantsAdministracion.columnsListaMovimientos);
        cargaListaFormasPago();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtFecVenta_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblListaFormasPago_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        try {
            if (VariablesPtoVenta.vTipOpMovCaja.equals(ConstantsPtoVenta.TIP_OPERACION_MOV_CAJA_REGISTRAR_VENTA)) {
                obtieneInfoArqueo();
                calcularTotales();
                mostrarDatos();
                insertarArqueo();
            } else {
                obtieneInfoArqueoConsulta();
                calcularTotales();
                mostrarDatos();
            }
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(this, "Error al obtener informacion del arqueo de caja. \n" +
                    sqlException.getMessage(), txtBoletasAnu);
        }
        txtFecVenta.setEnabled(false);
        FarmaUtility.moveFocus(tblListaFormasPago);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            VariablesCaja.vSecMovCajaOrigen = VariablesPtoVenta.vSecMovCajaOrigen;
            VariablesPtoVenta.vTipAccesoListaComprobantes = ConstantsPtoVenta.TIP_ACC_LISTA_COMP_REP;
            DlgReportePedidosComprobantes ing = new DlgReportePedidosComprobantes(myParentFrame, "", true);
            ing.setVisible(true);
        } else if (UtilityPtoVenta.verificaVK_F2(e)) {
            imprimeDetalleCierre();
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void obtieneInfoArqueo() throws SQLException {
        ArrayList infoArqueo = new ArrayList();
        infoArqueo = DBPtoVenta.obtieneDatosArqueo();

        String flag = "";
        String tipComp = "";

        for (int i = 0; i < infoArqueo.size(); i++) {
            log.debug(infoArqueo.get(i).toString());

            flag = ((String)((ArrayList)infoArqueo.get(i)).get(0)).trim();
            tipComp = ((String)((ArrayList)infoArqueo.get(i)).get(1)).trim();

            if (flag.equals("N") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_BOLETA)) {
                lblCantBoletasGen.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtBoletasGen.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            } else if (flag.equals("N") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_FACTURA)) {
                lblCantFacturasGen.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtFacturasGen.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            } else if (flag.equals("N") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_GUIA)) {
                lblCantGuiasGen.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtGuiasGen.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            } else if (flag.equals("N") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_TICKET)) {
                lblCantTicketsGen.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtTicketGen.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            } else if (flag.equals("N") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_TICKET_FACT)) {
                lblCantTicketsFacGen.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtTicketFacGen.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            } else if (flag.equals("NC") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_BOLETA)) {
                lblCantNCBoletas.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtNCBoletas.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            } else if (flag.equals("NC") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_FACTURA)) {
                lblCantNCFacturas.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtNCFacturas.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            } else if (flag.equals("NC") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_TICKET)) {
                lblCantTicketsNC.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtTicketNC.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            } else if (flag.equals("NC") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_TICKET_FACT)) {
                lblCantTicketsFacNC.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtTicketFacNC.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            }

            if (flag.equals("S") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_BOLETA)) {
                lblCantBoletasAnu.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtBoletasAnu.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            } else if (flag.equals("S") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_FACTURA)) {
                lblCantFacturasAnu.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtFacturasAnu.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            } else if (flag.equals("S") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_GUIA)) {
                lblCantGuiasAnu.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtGuiasAnu.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            } else if (flag.equals("S") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_TICKET)) {
                lblCantTicketsAn.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtTicketAn.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            } else if (flag.equals("S") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_TICKET_FACT)) {
                lblCantTicketsFacAn.setText(((String)((ArrayList)infoArqueo.get(i)).get(2)).trim());
                txtTicketFacAn.setText(((String)((ArrayList)infoArqueo.get(i)).get(3)).trim());
            }
        }
    }

    private void calcularTotales() {
        double totGenerados = 0;
        double totAnulados = 0;
        double totalNC = 0;

        totGenerados = totGenerados + FarmaUtility.getDecimalNumber(txtBoletasGen.getText().trim());
        totGenerados = totGenerados + FarmaUtility.getDecimalNumber(txtFacturasGen.getText().trim());
        totGenerados = totGenerados + FarmaUtility.getDecimalNumber(txtGuiasGen.getText().trim());
        totGenerados = totGenerados + FarmaUtility.getDecimalNumber(txtTicketGen.getText().trim());
        totGenerados = totGenerados + FarmaUtility.getDecimalNumber(txtTicketFacGen.getText().trim());
        //totGenerados = FarmaUtility.getDecimalNumberRedondeado(totGenerados);
        lblTotalGenerados.setText(FarmaUtility.formatNumber(totGenerados));

        totAnulados = totAnulados + FarmaUtility.getDecimalNumber(txtBoletasAnu.getText().trim());
        totAnulados = totAnulados + FarmaUtility.getDecimalNumber(txtFacturasAnu.getText().trim());
        totAnulados = totAnulados + FarmaUtility.getDecimalNumber(txtGuiasAnu.getText().trim());
        totAnulados = totAnulados + FarmaUtility.getDecimalNumber(txtTicketAn.getText().trim());
        totAnulados = totAnulados + FarmaUtility.getDecimalNumber(txtTicketFacAn.getText().trim());
        //totAnulados = FarmaUtility.getDecimalNumberRedondeado(totAnulados);
        lblTotalAnulados.setText(FarmaUtility.formatNumber(totAnulados));

        totalNC = totalNC + FarmaUtility.getDecimalNumber(txtNCBoletas.getText().trim());
        totalNC = totalNC + FarmaUtility.getDecimalNumber(txtNCFacturas.getText().trim());
        totalNC = totalNC + FarmaUtility.getDecimalNumber(txtTicketNC.getText().trim());
        totalNC = totalNC + FarmaUtility.getDecimalNumber(txtTicketFacNC.getText().trim());
        //totalNC = FarmaUtility.getDecimalNumberRedondeado(totalNC);
        lblTotalNC.setText(FarmaUtility.formatNumber(totalNC));

        lblCantBoletasTot.setText("" +
                                  (Integer.parseInt(lblCantBoletasGen.getText().trim()) - Integer.parseInt(lblCantBoletasAnu.getText().trim())));
        lblCantFacturasTot.setText("" +
                                   (Integer.parseInt(lblCantFacturasGen.getText().trim()) - Integer.parseInt(lblCantFacturasAnu.getText().trim())));
        lblCantGuiasTot.setText("" +
                                (Integer.parseInt(lblCantGuiasGen.getText().trim()) - Integer.parseInt(lblCantGuiasAnu.getText().trim())));
        lblCantTicketsTot.setText("" +
                                  (Integer.parseInt(lblCantTicketsGen.getText().trim()) - Integer.parseInt(lblCantTicketsAn.getText().trim())));
        lblCantTicketsFacTot.setText("" +
                                     (Integer.parseInt(lblCantTicketsFacGen.getText().trim()) - Integer.parseInt(lblCantTicketsFacAn.getText().trim())));
        lblCantNCTot.setText("" +
                             (Integer.parseInt(lblCantNCBoletas.getText().trim()) + Integer.parseInt(lblCantNCFacturas.getText().trim())) +
                             Integer.parseInt(lblCantTicketsNC.getText().trim()));

        txtBoletasTotal.setText("" +
                                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtBoletasGen.getText().trim()) -
                                                          FarmaUtility.getDecimalNumber(txtBoletasAnu.getText().trim())));
        txtFacturasTotal.setText("" +
                                 FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtFacturasGen.getText().trim()) -
                                                           FarmaUtility.getDecimalNumber(txtFacturasAnu.getText().trim())));
        txtGuiasTotal.setText("" +
                              FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtGuiasGen.getText().trim()) -
                                                        FarmaUtility.getDecimalNumber(txtGuiasAnu.getText().trim())));
        //txtGuiasTotal.setText(""+ FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtTicketGen.getText().trim()) - FarmaUtility.getDecimalNumber(txtTicketAn.getText().trim())) );
        txtTicketTot.setText("" +
                             FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtTicketGen.getText().trim()) -
                                                       FarmaUtility.getDecimalNumber(txtTicketAn.getText().trim())));
        txtTicketFacTot.setText("" +
                                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtTicketFacGen.getText().trim()) -
                                                          FarmaUtility.getDecimalNumber(txtTicketFacAn.getText().trim())));
        txtNCTotal.setText(lblTotalNC.getText().trim());

        lblTotalCompras.setText("" + FarmaUtility.formatNumber((totGenerados - totAnulados) + totalNC));
    }

    private void mostrarDatos() {
        this.lblCajero.setText(VariablesAdministracion.vCajero.trim());
        this.lblCaja.setText(VariablesPtoVenta.vNumCaja.trim()); //VariablesAdministracion.vNumCaja
        this.lblTurno.setText(VariablesAdministracion.vNumTurnoCaja.trim());
    }

    private void cargaListaFormasPago() {
        if (VariablesPtoVenta.vTipOpMovCaja.equals(ConstantsPtoVenta.TIP_OPERACION_MOV_CAJA_REGISTRAR_VENTA)) {
            try {
                DBPtoVenta.cargaListaFormasPago(tableModel);
                if (tblListaFormasPago.getRowCount() > 0)
                    FarmaUtility.ordenar(tblListaFormasPago, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);
                log.debug("se cargo la lista de de Formas Pago");
            } catch (SQLException e) {
                log.error("", e);
                FarmaUtility.showMessage(this, "Error al obtener formas de pago. \n" +
                        e.getMessage(), txtBoletasAnu);
            }
        } else {
            try {
                DBPtoVenta.cargaListaFormasPagoConsulta(tableModel);
                if (tblListaFormasPago.getRowCount() > 0)
                    FarmaUtility.ordenar(tblListaFormasPago, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);
                log.debug("se cargo la lista de de Formas Pago");
            } catch (SQLException e) {
                log.error("", e);
                FarmaUtility.showMessage(this, "Error al obtener formas de pago. \n" +
                        e.getMessage(), txtBoletasAnu);
            }
        }
        if (tblListaFormasPago.getRowCount() > 0) {
            double totFormPago = 0;
            for (int i = 0; i < tblListaFormasPago.getRowCount(); i++)
                totFormPago =
                        totFormPago + FarmaUtility.getDecimalNumber(tblListaFormasPago.getValueAt(i, 4).toString().trim());
            totFormPago = FarmaUtility.getDecimalNumberRedondeado(totFormPago);
            lblTotalFormasPago.setText("" + FarmaUtility.formatNumber(totFormPago));
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void insertarArqueo() {
        String codGenerado = "";
        try {
            codGenerado =
                    DBPtoVenta.generarArqueoCaja(ConstantsPtoVenta.TIP_MOV_CAJA_ARQUEO, lblCantBoletasGen.getText().trim(),
                                                 txtBoletasGen.getText().trim(), lblCantFacturasGen.getText().trim(),
                                                 txtFacturasGen.getText().trim(), lblCantGuiasGen.getText().trim(),
                                                 txtGuiasGen.getText().trim(), lblCantBoletasAnu.getText().trim(),
                                                 txtBoletasAnu.getText().trim(), lblCantFacturasAnu.getText().trim(),
                                                 txtFacturasAnu.getText().trim(), lblCantGuiasAnu.getText().trim(),
                                                 txtGuiasAnu.getText().trim(), lblCantBoletasTot.getText().trim(),
                                                 txtBoletasTotal.getText().trim(), lblCantFacturasTot.getText().trim(),
                                                 txtFacturasTotal.getText().trim(), lblCantGuiasTot.getText().trim(),
                                                 txtGuiasTotal.getText().trim(), lblTotalGenerados.getText().trim(),
                                                 lblTotalAnulados.getText().trim(), lblTotalCompras.getText().trim(),
                                                 lblCantNCBoletas.getText().trim(), txtNCBoletas.getText().trim(),
                                                 lblCantNCFacturas.getText().trim(), txtNCFacturas.getText().trim(),
                                                 lblTotalNC.getText().trim(), null, null, null, null, null, null, null,
                                                 null);
            FarmaSearch.updateNumera(ConstantsPtoVenta.TIP_NUMERA_MOV_CAJA);
            DBPtoVenta.guardaValoresComprobante(codGenerado);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se guardo el arqueo de caja correctamente", txtFecVenta);
        } catch (SQLException ex) {
            FarmaUtility.liberarTransaccion();
            log.error("", ex);
            FarmaUtility.showMessage(this, "Error al guardar arqueo de caja. \n" +
                    ex.getMessage(), txtBoletasAnu);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }


    private void obtieneInfoArqueoConsulta() {
        ArrayList infoArqueo = new ArrayList();
        try {
            infoArqueo = DBPtoVenta.obtieneDatosArqueoConsulta();
        } catch (SQLException sqlException) {
            infoArqueo = new ArrayList();
            log.error("", sqlException);
            FarmaUtility.showMessage(this, "Error al obtener datos de arqueo para la consulta. \n" +
                    sqlException.getMessage(), txtBoletasAnu);
        }
        if (infoArqueo.size() != 0) {
            lblCantBoletasGen.setText(((String)((ArrayList)infoArqueo.get(0)).get(0)).trim());
            txtBoletasGen.setText(((String)((ArrayList)infoArqueo.get(0)).get(1)).trim());
            lblCantFacturasGen.setText(((String)((ArrayList)infoArqueo.get(0)).get(2)).trim());
            txtFacturasGen.setText(((String)((ArrayList)infoArqueo.get(0)).get(3)).trim());
            lblCantGuiasGen.setText(((String)((ArrayList)infoArqueo.get(0)).get(4)).trim());
            txtGuiasGen.setText(((String)((ArrayList)infoArqueo.get(0)).get(5)).trim());

            lblCantBoletasAnu.setText(((String)((ArrayList)infoArqueo.get(0)).get(6)).trim());
            txtBoletasAnu.setText(((String)((ArrayList)infoArqueo.get(0)).get(7)).trim());
            lblCantFacturasAnu.setText(((String)((ArrayList)infoArqueo.get(0)).get(8)).trim());
            txtFacturasAnu.setText(((String)((ArrayList)infoArqueo.get(0)).get(9)).trim());
            lblCantGuiasAnu.setText(((String)((ArrayList)infoArqueo.get(0)).get(10)).trim());
            txtGuiasAnu.setText(((String)((ArrayList)infoArqueo.get(0)).get(11)).trim());

            lblCantBoletasTot.setText(((String)((ArrayList)infoArqueo.get(0)).get(12)).trim());
            txtBoletasTotal.setText(((String)((ArrayList)infoArqueo.get(0)).get(13)).trim());
            lblCantFacturasTot.setText(((String)((ArrayList)infoArqueo.get(0)).get(14)).trim());
            txtFacturasTotal.setText(((String)((ArrayList)infoArqueo.get(0)).get(15)).trim());
            lblCantGuiasTot.setText(((String)((ArrayList)infoArqueo.get(0)).get(16)).trim());
            txtGuiasTotal.setText(((String)((ArrayList)infoArqueo.get(0)).get(17)).trim());

            lblTotalGenerados.setText(((String)((ArrayList)infoArqueo.get(0)).get(18)).trim());
            lblTotalAnulados.setText(((String)((ArrayList)infoArqueo.get(0)).get(19)).trim());
            lblTotalCompras.setText(((String)((ArrayList)infoArqueo.get(0)).get(20)).trim());
            lblCantNCBoletas.setText(((String)((ArrayList)infoArqueo.get(0)).get(21)).trim());
            txtNCBoletas.setText(((String)((ArrayList)infoArqueo.get(0)).get(22)).trim());
            lblCantNCFacturas.setText(((String)((ArrayList)infoArqueo.get(0)).get(23)).trim());
            txtNCFacturas.setText(((String)((ArrayList)infoArqueo.get(0)).get(24)).trim());
            lblTotalNC.setText(((String)((ArrayList)infoArqueo.get(0)).get(25)).trim());
            txtNCTotal.setText(lblTotalNC.getText().trim());

            lblCantTicketsGen.setText(((String)((ArrayList)infoArqueo.get(0)).get(26)).trim());
            txtTicketGen.setText(((String)((ArrayList)infoArqueo.get(0)).get(27)).trim());
            lblCantTicketsAn.setText(((String)((ArrayList)infoArqueo.get(0)).get(28)).trim());
            txtTicketAn.setText(((String)((ArrayList)infoArqueo.get(0)).get(29)).trim());
            lblCantTicketsNC.setText(((String)((ArrayList)infoArqueo.get(0)).get(30)).trim());
            txtTicketNC.setText(((String)((ArrayList)infoArqueo.get(0)).get(31)).trim());
            lblCantTicketsTot.setText(((String)((ArrayList)infoArqueo.get(0)).get(32)).trim());
            txtTicketTot.setText(((String)((ArrayList)infoArqueo.get(0)).get(33)).trim());

            lblCantTicketsFacGen.setText(((String)((ArrayList)infoArqueo.get(0)).get(34)).trim());
            txtTicketFacGen.setText(((String)((ArrayList)infoArqueo.get(0)).get(35)).trim());
            lblCantTicketsFacAn.setText(((String)((ArrayList)infoArqueo.get(0)).get(36)).trim());
            txtTicketFacAn.setText(((String)((ArrayList)infoArqueo.get(0)).get(37)).trim());
            lblCantTicketsFacNC.setText(((String)((ArrayList)infoArqueo.get(0)).get(38)).trim());
            txtTicketFacNC.setText(((String)((ArrayList)infoArqueo.get(0)).get(39)).trim());
            lblCantTicketsFacTot.setText(((String)((ArrayList)infoArqueo.get(0)).get(40)).trim());
            txtTicketFacTot.setText(((String)((ArrayList)infoArqueo.get(0)).get(41)).trim());

            lblCantNCTot.setText((Integer.parseInt(lblCantNCFacturas.getText()) +
                                  Integer.parseInt(lblCantNCBoletas.getText()) +
                                  Integer.parseInt(lblCantTicketsNC.getText())) + "");
        }
    }

    private void iniciarValoresFecha() {
        String fecActual = "";
        try {
            fecActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        } catch (SQLException ex) {
            log.error("", ex);
            Date fec = new Date();
            fecActual = fec.toString();
            FarmaUtility.showMessage(this, "Error al obtener fecha actual. \n" +
                    ex.getMessage(), txtBoletasAnu);
        }

        txtFecVenta.setText(VariablesAdministracion.vFecDiaVta);
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaFormasPago);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
    }

    void imprimeDetalleCierre() {
        if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        //FarmaPrintService vPrint = new FarmaPrintService(45,
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        log.debug(FarmaVariables.vImprReporte);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", tblListaFormasPago);
            return;
        }
        try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.alinearIzquierda(FarmaVariables.vDescCia, 25) +
                             FarmaPRNUtility.llenarBlancos(10) + "Fecha: " + fechaActual, true);
            vPrint.printBold(FarmaPRNUtility.alinearIzquierda("Dpto. de Sistemas", 20) +
                             FarmaPRNUtility.llenarBlancos(79), true);
            vPrint.printBold("", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(18) + "RESUMEN DE VENTAS - " +
                             FarmaVariables.vDescCortaLocal, true);
            vPrint.printBold("", true);
            vPrint.activateCondensed();
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Cajero: " + lblCajero.getText(), true);
            vPrint.printBold("Nº Caja: " + lblCaja.getText(), true);
            vPrint.printBold("Turno: " + lblTurno.getText(), true);
            vPrint.printBold("Fecha : " + VariablesAdministracion.vFechaCaja, true);
            vPrint.printBold("=====================================================", true);

            vPrint.setEndHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(17) + "Cantidad              Monto", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(17) + "--------      -------------", true);
            vPrint.printLine(" Bol.Emitidas  :   " +
                             FarmaPRNUtility.alinearDerecha(lblCantBoletasGen.getText().trim(), 6) + "   " +
                             FarmaPRNUtility.alinearDerecha(txtBoletasGen.getText().trim(), 16), true);
            vPrint.printLine(" Bol.Anuladas  :   " +
                             FarmaPRNUtility.alinearDerecha(lblCantBoletasAnu.getText().trim(), 6) + "   " +
                             FarmaPRNUtility.alinearDerecha(txtBoletasAnu.getText().trim(), 16), true);
            //JMIRANDA 23.09.09
            vPrint.printLine("Tick Emitidos :   " +
                             FarmaPRNUtility.alinearDerecha(lblCantTicketsGen.getText().trim(), 6) + "   " +
                             FarmaPRNUtility.alinearDerecha(txtTicketGen.getText().trim(), 16), true);
            vPrint.printLine("Tick Anulados :   " +
                             FarmaPRNUtility.alinearDerecha(lblCantTicketsAn.getText().trim(), 6) + "   " +
                             FarmaPRNUtility.alinearDerecha(txtTicketAn.getText().trim(), 16), true);
            vPrint.printLine("Fact Emitidas  :   " +
                             FarmaPRNUtility.alinearDerecha(lblCantFacturasGen.getText().trim(), 6) + "   " +
                             FarmaPRNUtility.alinearDerecha(txtFacturasGen.getText().trim(), 16), true);
            vPrint.printLine("Fact Anuladas  :   " +
                             FarmaPRNUtility.alinearDerecha(lblCantFacturasAnu.getText().trim(), 6) + "   " +
                             FarmaPRNUtility.alinearDerecha(txtFacturasAnu.getText().trim(), 16), true);
            vPrint.printLine("Guias Emitidas :   " +
                             FarmaPRNUtility.alinearDerecha(lblCantGuiasGen.getText().trim(), 6) + "   " +
                             FarmaPRNUtility.alinearDerecha(txtGuiasGen.getText().trim(), 16), true);
            vPrint.printLine("Guias Anuladas :   " +
                             FarmaPRNUtility.alinearDerecha(lblCantGuiasAnu.getText().trim(), 6) + "   " +
                             FarmaPRNUtility.alinearDerecha(txtGuiasAnu.getText().trim(), 16), true);
            // vPrint.printLine("  P.Delivery   :   "+FarmaPRNUtility.alinearDerecha(lblCantDeli.getText().trim(),6)+"   "+FarmaPRNUtility.alinearDerecha(lblMontoDeli.getText().trim(),16),true);
            vPrint.printLine("------------------------------------------------", true);
            vPrint.printLine(" Total Venta   :  " + "   " +
                             FarmaPRNUtility.alinearDerecha(lblTotalCompras.getText().trim(), 16), true);
            vPrint.printBold("------------------------------------------------", true);
            vPrint.printBold("", true);
            vPrint.printLine("===================================================", true);
            vPrint.printBold("Codigo       Forma de Pago                    Total", true);
            vPrint.printLine("===================================================", true);
            vPrint.deactivateCondensed();


            for (int i = 0; i < tblListaFormasPago.getRowCount(); i++) {
                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda(((String)tblListaFormasPago.getValueAt(i,
                                                                                                              0)).trim(),
                                                                       10) + "   " +
                                      FarmaPRNUtility.alinearIzquierda(((String)tblListaFormasPago.getValueAt(i,
                                                                                                              1)).trim(),
                                                                       22) + "   " +
                                      FarmaPRNUtility.alinearDerecha(((String)tblListaFormasPago.getValueAt(i,
                                                                                                            4)).trim(),
                                                                     13), true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("===================================================", true);
            vPrint.printLine("Registros :  " +
                             FarmaPRNUtility.alinearIzquierda("" + tblListaFormasPago.getRowCount(), 2) +
                             FarmaPRNUtility.llenarBlancos(22) + "" +
                             FarmaPRNUtility.alinearDerecha(lblTotalFormasPago.getText().trim(), 14), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();

        } catch (SQLException err) {
            log.error("", err);
            FarmaUtility.showMessage(this, "Error al Imprimir. \n " + err.getMessage(), tblListaFormasPago);
        }
    }

    private void obtieneDatosdelivery() {
        ArrayList infoDelivery = new ArrayList();
        String flag = "";
        String tipComp = "";
        try {
            infoDelivery = DBPtoVenta.obtieneInfoDelivery();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, sql.getMessage(), tblListaFormasPago);
        }
        for (int i = 0; i < infoDelivery.size(); i++) {
            flag = ((String)((ArrayList)infoDelivery.get(i)).get(0)).trim();
            tipComp = ((String)((ArrayList)infoDelivery.get(i)).get(1)).trim();

            if (flag.equals("N") && tipComp.equals(ConstantsReporte.TIP_PEDIDO_DELIVERY)) {
                lblDeliveryEmitido.setText(((String)((ArrayList)infoDelivery.get(i)).get(2)).trim());
                txtDeliveryEmitido.setText(((String)((ArrayList)infoDelivery.get(i)).get(3)).trim());
            } else if (flag.equals("S") && tipComp.equals(ConstantsReporte.TIP_PEDIDO_DELIVERY)) {
                lblDeliveryAnulado.setText(((String)((ArrayList)infoDelivery.get(i)).get(2)).trim());
                txtDeliveryAnulado.setText(((String)((ArrayList)infoDelivery.get(i)).get(3)).trim());
            }
        }
    }

    private void calculoTotalDelivery() {
        int cantdelivery;
        double montodelivery;
        cantdelivery =
                (Integer.parseInt(lblDeliveryEmitido.getText().trim()) - Integer.parseInt(lblDeliveryAnulado.getText().trim()));
        montodelivery =
                FarmaUtility.getDecimalNumber(txtDeliveryEmitido.getText().trim()) - FarmaUtility.getDecimalNumber(txtDeliveryAnulado.getText().trim());

        lblTotalCantidadDelivery.setText("" + cantdelivery);
        lblTotalMontoDelivery.setText("" + FarmaUtility.formatNumber(montodelivery));
    }
}
