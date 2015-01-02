package mifarma.ptoventa.pinpad;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.pinpad.mastercard.HiloProcesoPinpadVentaMC;
import mifarma.ptoventa.pinpad.reference.UtilityPinpad;
import mifarma.ptoventa.pinpad.visa.HiloProcesoPinpadVenta;
import mifarma.ptoventa.pinpad.visa.VariablesPinpad;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgInterfacePinpad extends JDialog {
    private Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgInterfacePinpad.class);

    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JPanel pnlInfo = new JPanel();
    private JLabelWhite lblTitulo = new JLabelWhite();
    private JLabelOrange lblTarjeta = new JLabelOrange();
    private JLabelOrange lblTipoTarjeta = new JLabelOrange();

    private JLabelOrange lblCliente = new JLabelOrange();
    private JLabelOrange lblMontoCobrar = new JLabelOrange();
    private JPanel jPanel1 = new JPanel();
    private JPanelTitle jPanel2 = new JPanelTitle();
    private JLabelWhite jLabel1 = new JLabelWhite();
    private JPanelTitle pnlMensajePinpad = new JPanelTitle();
    private JLabelWhite lblMensajePinpad = new JLabelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JLabelOrange lblNumAutorizacion = new JLabelOrange();
    private JLabelOrange lblCodVoucher = new JLabelOrange();
    private JLabelOrange lblCuotas = new JLabelOrange();
    private JLabelOrange lblMontoCuotas = new JLabelOrange();
    private JLabelOrange lblNombreTarjeta = new JLabelOrange();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabel lblDatoCliente = new JLabel();
    private JLabel lblDatoNumTarjeta = new JLabel();
    private JLabel lblDatoTipoTarjeta = new JLabel();
    private JLabel lblDatoMonto = new JLabel();
    private JLabel lblDatoNombreTarjeta = new JLabel();
    private JLabel lblDatoNumAutorizacion = new JLabel();
    private JLabel lblDatoCodVoucher = new JLabel();
    private JLabel lblDatoCuota = new JLabel();
    private JLabel lblDatoMontoCuota = new JLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelWhite lblTimer = new JLabelWhite();

    private String numTarjeta;
    private String tipoTarjeta;
    private Date fechaExp;
    private String nombreCliente;
    private String voucher;
    private String strCodFormaPago;
    private int codAlianza;
    private String nroTarjetaBruto;


    public DlgInterfacePinpad() {
        this(null, "", false);
    }

    public DlgInterfacePinpad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        this.myParentFrame = parent;
        try {
            jbInit();
            //initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(480, 506));
        this.setResizable(false);
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Interface Pinpad - Cobro");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlFondo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                pnlFondo_focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                pnlFondo_focusLost(e);
            }
        });
        pnlFondo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlFondo_keyPressed(e);
            }
        });
        pnlTitulo.setBounds(new Rectangle(5, 5, 465, 20));
        pnlTitulo.setFocusable(false);
        pnlInfo.setBounds(new Rectangle(5, 25, 465, 340));
        pnlInfo.setBackground(SystemColor.window);
        pnlInfo.setLayout(null);
        pnlInfo.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        pnlInfo.setFocusable(false);
        lblTitulo.setText("Datos de Cobro");
        lblTitulo.setBounds(new Rectangle(10, 0, 205, 20));
        lblTitulo.setFocusable(false);
        lblTarjeta.setText("Num. Tarjeta:");
        lblTarjeta.setBounds(new Rectangle(40, 45, 80, 20));
        lblTarjeta.setFocusable(false);
        lblTipoTarjeta.setText("Tipo Tarjeta:");
        lblTipoTarjeta.setBounds(new Rectangle(40, 75, 80, 20));
        lblTipoTarjeta.setFocusable(false);
        lblCodVoucher.setText("Num. Referencia:");
        lblCodVoucher.setBounds(new Rectangle(20, 80, 105, 20));
        lblCodVoucher.setFocusable(false);
        lblCliente.setText("Cliente:");
        lblCliente.setBounds(new Rectangle(40, 15, 80, 20));
        lblCliente.setFocusable(false);
        lblMontoCobrar.setText("Monto (S/.):");
        lblMontoCobrar.setBounds(new Rectangle(40, 105, 80, 20));
        lblMontoCobrar.setFocusable(false);
        jPanel1.setBounds(new Rectangle(20, 160, 430, 170));
        jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        jPanel1.setLayout(null);
        jPanel1.setFocusable(false);
        jPanel2.setBounds(new Rectangle(20, 140, 430, 20));
        jPanel2.setFocusable(false);
        jLabel1.setText("Datos retornados por el Pinpad");
        jLabel1.setBounds(new Rectangle(10, 0, 200, 20));
        jLabel1.setFocusable(false);
        pnlMensajePinpad.setBounds(new Rectangle(5, 365, 465, 75));
        pnlMensajePinpad.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        pnlMensajePinpad.setLayout(null);
        pnlMensajePinpad.setFocusable(false);

        lblMensajePinpad.setBounds(new Rectangle(0, 0, 465, 50));
        lblMensajePinpad.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensajePinpad.setHorizontalTextPosition(SwingConstants.CENTER);
        lblMensajePinpad.setFocusable(false);
        lblMensajePinpad.setFont(new Font("SansSerif", 1, 14));
        lblNumAutorizacion.setText("Num. Autorizaci\u00f3n:");
        lblNumAutorizacion.setBounds(new Rectangle(20, 50, 105, 15));
        lblNumAutorizacion.setFocusable(false);
        lblCuotas.setText("Cuotas:");
        lblCuotas.setBounds(new Rectangle(20, 110, 100, 15));
        lblCuotas.setFocusable(false);
        lblMontoCuotas.setText("Monto Cuota (S/.):");
        lblMontoCuotas.setBounds(new Rectangle(20, 140, 105, 15));
        lblMontoCuotas.setFocusable(false);
        lblNombreTarjeta.setText("Nombre en Tarjeta:");
        lblNombreTarjeta.setBounds(new Rectangle(20, 20, 105, 15));
        lblNombreTarjeta.setFocusable(false);
        lblF11.setText("[Cualquier tecla] Continuar");
        lblF11.setBounds(new Rectangle(275, 445, 195, 30));

        lblDatoCliente.setBounds(new Rectangle(125, 15, 310, 20));
        lblDatoCliente.setSize(new Dimension(280, 20));
        lblDatoCliente.setFocusable(false);

        lblDatoNumTarjeta.setBounds(new Rectangle(125, 45, 310, 20));
        lblDatoNumTarjeta.setFocusable(false);
        lblDatoNumTarjeta.setSize(new Dimension(280, 20));

        lblDatoTipoTarjeta.setBounds(new Rectangle(125, 75, 310, 20));
        lblDatoTipoTarjeta.setFocusable(false);
        lblDatoTipoTarjeta.setSize(new Dimension(280, 20));

        lblDatoMonto.setBounds(new Rectangle(125, 105, 310, 20));
        lblDatoMonto.setSize(new Dimension(280, 20));
        lblDatoMonto.setFocusable(false);
        lblDatoNombreTarjeta.setBounds(new Rectangle(135, 20, 240, 15));
        lblDatoNombreTarjeta.setFocusable(false);
        lblDatoNumAutorizacion.setBounds(new Rectangle(135, 50, 240, 15));
        lblDatoNumAutorizacion.setSize(new Dimension(240, 15));
        lblDatoNumAutorizacion.setFocusable(false);
        lblDatoCodVoucher.setBounds(new Rectangle(135, 80, 240, 15));
        lblDatoCodVoucher.setSize(new Dimension(240, 15));
        lblDatoCodVoucher.setFocusable(false);
        lblDatoCuota.setBounds(new Rectangle(135, 110, 240, 15));
        lblDatoCuota.setSize(new Dimension(240, 15));
        lblDatoCuota.setFocusable(false);
        lblDatoMontoCuota.setBounds(new Rectangle(135, 140, 240, 15));
        lblDatoMontoCuota.setSize(new Dimension(240, 15));
        lblDatoMontoCuota.setFocusable(false);
        lblEsc.setText("[ ESC ] Cancelar");
        lblEsc.setBounds(new Rectangle(5, 445, 130, 30));

        lblTimer.setBounds(new Rectangle(0, 50, 465, 25));
        lblTimer.setHorizontalAlignment(SwingConstants.CENTER);
        lblTimer.setHorizontalTextPosition(SwingConstants.CENTER);

        lblTimer.setVerticalAlignment(SwingConstants.TOP);
        lblTimer.setVerticalTextPosition(SwingConstants.TOP);
        pnlTitulo.add(lblTitulo, null);
        pnlMensajePinpad.add(lblTimer, null);
        pnlMensajePinpad.add(lblMensajePinpad, BorderLayout.WEST);
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(pnlMensajePinpad, null);
        pnlFondo.add(pnlTitulo, null);
        jPanel1.add(lblDatoMontoCuota, null);
        jPanel1.add(lblDatoCuota, null);
        jPanel1.add(lblDatoCodVoucher, null);
        jPanel1.add(lblDatoNumAutorizacion, null);
        jPanel1.add(lblDatoNombreTarjeta, null);
        jPanel1.add(lblNombreTarjeta, null);
        jPanel1.add(lblMontoCuotas, null);
        jPanel1.add(lblCuotas, null);
        jPanel1.add(lblNumAutorizacion, null);
        jPanel1.add(lblCodVoucher, null);
        pnlInfo.add(lblDatoMonto, null);
        pnlInfo.add(lblDatoTipoTarjeta, null);
        pnlInfo.add(lblDatoNumTarjeta, null);
        pnlInfo.add(lblDatoCliente, null);
        jPanel2.add(jLabel1, null);
        pnlInfo.add(jPanel2, null);
        pnlInfo.add(jPanel1, null);
        pnlInfo.add(lblMontoCobrar, null);
        pnlInfo.add(lblCliente, null);
        pnlInfo.add(lblTipoTarjeta, null);
        pnlInfo.add(lblTarjeta, null);
        pnlFondo.add(pnlInfo, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");

        FarmaUtility.centrarVentana(this);
    }

    public void inicializarDatos(String numTarjeta, String tipoTarjeta, String strCodFormaPago) {
        this.numTarjeta = numTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.strCodFormaPago = strCodFormaPago;

        lblDatoNumTarjeta.setText(this.numTarjeta);
        lblDatoTipoTarjeta.setText(this.tipoTarjeta);
        lblDatoCliente.setText(VariablesFidelizacion.vNomCliente);
        lblDatoMonto.setText(VariablesCaja.vValMontoPagado);

        //resetear labels
        lblF11.setEnabled(false);
        lblF11.setFocusable(false);
        lblEsc.setEnabled(false);
        lblEsc.setFocusable(false);

        lblMensajePinpad.setText("REALIZANDO LA COMUNICACIÓN CON PINPAD");
        pnlMensajePinpad.setBackground(pnlTitulo.getBackground());
    }

    private void chkkeyPressed(KeyEvent e) {
        if (lblF11.isEnabled()) {
            log.debug("Presiono F11");
            cerrarVentana(true);
        }
        if (lblEsc.isEnabled() && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            log.debug("Presiono Escape");
            cerrarVentana(false);
        }
    }

    private void procesoPagoPinpadVisa() {
        HiloProcesoPinpadVenta hilo = new HiloProcesoPinpadVenta();
        log.debug("---PROCESO VISA---");
        hilo.lblDatoNombreTarjeta = lblDatoNombreTarjeta;
        hilo.lblDatoNumAutorizacion = lblDatoNumAutorizacion;
        hilo.lblDatoCodVoucher = lblDatoCodVoucher;
        hilo.lblDatoCuota = lblDatoCuota;
        hilo.lblDatoMontoCuota = lblDatoMontoCuota;
        hilo.lblMensajePinpad = lblMensajePinpad;
        hilo.pnlMensajePinpad = pnlMensajePinpad;
        hilo.lblF11 = lblF11;
        hilo.lblEsc = lblEsc;
        hilo.fechaExp = fechaExp;
        hilo.nombreCliente = nombreCliente;
        hilo.voucher = voucher;
        hilo.pnlFondo = pnlFondo;
        hilo.lblTimer = lblTimer;

        hilo.padre = this;
        hilo.formaPago = this.strCodFormaPago;

        try {
            String monto = (String)VariablesCaja.vValMontoPagado.trim();
            hilo.monto = Double.parseDouble(monto.replaceAll(",", ""));
            hilo.tipoMoneda = VariablesPinpad.COD_MONEDA_NACIONAL;
            hilo.codTienda = FarmaVariables.vCodLocal;
            hilo.codCaja = FarmaVariables.vCodLocal;

            log.debug("VISA -   Datos del HILO VISA----->", hilo);
        } catch (Exception e) {
            log.error("", e);
        }
        hilo.start();
    }

    private void procesoPagoPinpadMC() {
        HiloProcesoPinpadVentaMC hilo = new HiloProcesoPinpadVentaMC();
        log.debug("---PROCESO MC---");
        hilo.monto = Double.parseDouble(VariablesCaja.vValMontoPagado.replaceAll(",", ""));

        hilo.lblDatoNombreTarjeta = lblDatoNombreTarjeta;
        hilo.lblDatoNumAutorizacion = lblDatoNumAutorizacion;
        hilo.lblDatoCodVoucher = lblDatoCodVoucher;
        hilo.lblDatoCuota = lblDatoCuota;
        hilo.lblDatoMontoCuota = lblDatoMontoCuota;
        hilo.lblMensajePinpad = lblMensajePinpad;
        hilo.pnlMensajePinpad = pnlMensajePinpad;
        hilo.lblF11 = lblF11;
        hilo.lblEsc = lblEsc;
        hilo.fechaExp = fechaExp;
        hilo.nombreCliente = nombreCliente;
        hilo.voucher = voucher;
        hilo.pnlFondo = pnlFondo;
        hilo.codFormaPago = this.strCodFormaPago;
        hilo.lblTimer = lblTimer;

        hilo.padre = this;
        log.debug("MC -   Datos del HILO MC----->", hilo);
        hilo.start();
    }

    private void this_windowOpened(WindowEvent e) {
        if (UtilityPinpad.validarLibrerias()) {
            lblF11.grabFocus();
            //Si es Visa
            if (this.strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_VISA_PINPAD)) {
                procesoPagoPinpadVisa();
            }
            //Si es Mastercard
            //ERIOS 2.4.5 Los pagos con CMR pasan por el pinpad de MC
            else if (this.strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_MC_PINPAD) ||
                     this.strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_DINERS) ||
                     this.strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_AMEX) ||
                     (ConstantsPtoVenta.FORPAG_CMR.equalsIgnoreCase(strCodFormaPago) &&
                      !VariablesPtoVenta.vIndFarmaSix.equals("S"))) {
                procesoPagoPinpadMC();
            }
        } else
            cerrarVentana(false);
    }

    public void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    public String getFechaExpiracion() {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        return formatoDelTexto.format(fechaExp);
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getVoucher() {
        return lblDatoCodVoucher.getText();
    }

    public String getNumAutorizacion() {
        return lblDatoNumAutorizacion.getText();
    }

    public String getCantCuotas() {
        return lblDatoCuota.getText();
    }

    public String getLote() {
        return "";
    }

    private void pnlFondo_focusGained(FocusEvent e) {
    }

    private void pnlFondo_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void pnlFondo_focusLost(FocusEvent e) {
        pnlFondo.grabFocus();
    }

    public int getCodAlianza() {
        return codAlianza;
    }

    public void setCodAlianza(int pCodAlianza) {
        this.codAlianza = pCodAlianza;
    }

    public void setNroTarjetaBruto(String nroTarjetaBruto) {
        this.nroTarjetaBruto = nroTarjetaBruto;
    }

    public String getNroTarjetaBruto() {
        return nroTarjetaBruto;
    }
}
