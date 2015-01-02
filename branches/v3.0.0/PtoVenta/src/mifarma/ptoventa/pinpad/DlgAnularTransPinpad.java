package mifarma.ptoventa.pinpad;

import com.gs.mifarma.componentes.JLabelFunction;

import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.DlgDatosTarjetaPinpad;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.FacadeCajaElectronica;
import mifarma.ptoventa.pinpad.mastercard.HiloProcesoAnularPinpadMC;
import mifarma.ptoventa.pinpad.visa.HiloProcesoAnularPinpadVisa;
import mifarma.ptoventa.pinpad.visa.VariablesPinpad;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import mifarma.ptoventa.reference.UtilityPtoVenta;
import com.gs.mifarma.componentes.JConfirmDialog;

import mifarma.ptoventa.DlgProcesar;
import mifarma.ptoventa.pinpad.reference.DBPinpad;

import mifarma.ptoventa.pinpad.reference.UtilityPinpad;

import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgAnularTransPinpad extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgAnularTransPinpad.class);
    
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JPanelWhite pnlFondo = new JPanelWhite();
    
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JLabelWhite txtTitulo = new JLabelWhite();
    private JPanel jPanel1 = new JPanel();
    
    private JLabelOrange lblPedidoEnv = new JLabelOrange();
    private JLabelOrange lblMontoEnv = new JLabelOrange();
    private JLabelOrange lblFechaEnv = new JLabelOrange();
    private JLabelOrange lblNumRefEnv = new JLabelOrange();

    private JLabel lblDatoPedidoEnv = new JLabel();
    private JLabel lblDatoMontoEnv = new JLabel();
    private JLabel lblDatoFechaEnv = new JLabel();
    private JLabel lblDatoNumRefEnv = new JLabel();
    
    private JPanelTitle pnlMensajePinpad = new JPanelTitle();
    private JLabelWhite lblMensajePinpad = new JLabelWhite();
    private JPanelTitle pnlTitleResp = new JPanelTitle();
    private JPanel pnlResp = new JPanel();
    
    private JLabelWhite lblTitleResp = new JLabelWhite();
    
    private JLabelOrange lblNumAutorizacion = new JLabelOrange();
    private JLabelOrange lblCodVoucher = new JLabelOrange();
    private JLabel lblDatoNumAutorizacion = new JLabel();
    private JLabel lblDatoCodVoucher = new JLabel();

    private String numReferencia;
    private String strCodFormaPago;
    private boolean flagAnulPed;
    private Boolean flagAnulDiaLoteCerrado;
    private Double monto;
    private String numPedidoNegativo;
    private JLabel lblFlagAnulDiaLoteCerrado = new JLabel();
    private Frame myParent;
    private String tipoTarj;
    private String vConciliacionOnline;
    private JLabelWhite lblTimer = new JLabelWhite();

    public DlgAnularTransPinpad() {
        this(null, "", false);
    }

    public DlgAnularTransPinpad(Frame parent, String title, boolean modal)
    {   
        super(parent, title, modal);
        myParent = parent;
        try
        {   jbInit();
        }
        catch (Exception e)
        {   log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(571, 412));
        this.setResizable(false);
        this.getContentPane().setLayout( null );
        this.setTitle("Anulaci\u00f3n de Transacci\u00f3n de Pinpad");
        this.getContentPane().setLayout(cardLayout1);
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
                }
            });
        this.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    this_focusGained(e);
                }
            });
        lblF11.setText("[ F11 ] Continuar");
        lblF11.setBounds(new Rectangle(435, 340, 115, 30));
        
        lblEsc.setText("[ ESC ] Cancelar");
        lblEsc.setBounds(new Rectangle(10, 340, 120, 30));
        
        pnlFondo.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    pnlFondo_keyPressed(e);
                }
            });
        pnlTitulo.setBounds(new Rectangle(10, 20, 540, 20));
        pnlTitulo.setFocusable(false);
        txtTitulo.setText("Datos de Anulaci\u00f3n");
        txtTitulo.setBounds(new Rectangle(10, 0, 215, 20));
        txtTitulo.setFocusable(false);
        jPanel1.setBounds(new Rectangle(10, 40, 540, 220));
        jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        jPanel1.setLayout(null);
        jPanel1.setFocusable(false);
        lblPedidoEnv.setText("Pedido:");
        lblPedidoEnv.setBounds(new Rectangle(15, 10, 50, 20));
        lblPedidoEnv.setFocusable(false);
        lblMontoEnv.setText("Monto:");
        lblMontoEnv.setBounds(new Rectangle(15, 35, 50, 20));
        lblMontoEnv.setFocusable(false);
        lblFechaEnv.setText("Fecha:");
        lblFechaEnv.setBounds(new Rectangle(15, 65, 50, 15));
        lblFechaEnv.setFocusable(false);
        pnlMensajePinpad.setBounds(new Rectangle(10, 260, 540, 75));
        lblMensajePinpad.setBounds(new Rectangle(0, 0, 540, 50));
        lblMensajePinpad.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensajePinpad.setFocusable(false);
        lblMensajePinpad.setText("REALIZANDO LA COMUNICACI\u00d3N CON EL PINPAD");
        lblMensajePinpad.setFont(new Font("SansSerif", 1, 14));
        pnlTitleResp.setBounds(new Rectangle(10, 115, 515, 20));
        pnlTitleResp.setFocusable(false);
        pnlResp.setBounds(new Rectangle(10, 135, 515, 75));
        pnlResp.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        pnlResp.setLayout(null);
        pnlResp.setFocusable(false);
        lblTitleResp.setText("Datos retornados por el Pinpad");
        lblTitleResp.setBounds(new Rectangle(15, 0, 245, 20));
        lblTitleResp.setFocusable(false);
        lblNumAutorizacion.setText("Num. Autorizaci\u00f3n:");
        lblNumAutorizacion.setBounds(new Rectangle(10, 20, 110, 15));
        lblNumAutorizacion.setFocusable(false);
        lblCodVoucher.setText("Num: Referencia");
        lblCodVoucher.setBounds(new Rectangle(10, 45, 110, 15));
        lblCodVoucher.setFocusable(false);
        lblDatoNumAutorizacion.setBounds(new Rectangle(130, 20, 165, 15));
        lblDatoNumAutorizacion.setFocusable(false);
        lblDatoCodVoucher.setBounds(new Rectangle(130, 45, 160, 15));
        lblDatoCodVoucher.setFocusable(false);
        lblFlagAnulDiaLoteCerrado.setText("");
        lblFlagAnulDiaLoteCerrado.setBounds(new Rectangle(400, 5, 45, 15));
        lblFlagAnulDiaLoteCerrado.setVisible(false);
        lblTimer.setBounds(new Rectangle(0, 50, 540, 25));
        lblTimer.setHorizontalAlignment(SwingConstants.CENTER);
        lblTimer.setHorizontalTextPosition(SwingConstants.CENTER);
        lblTimer.setVerticalAlignment(SwingConstants.TOP);
        lblTimer.setVerticalTextPosition(SwingConstants.TOP);
        lblF5.setText("[ F5 ] Continuar sin anular trans. pinpad");
        lblF5.setBounds(new Rectangle(160, 340, 245, 30));
        
        lblDatoPedidoEnv.setText("jLabel1");
        lblDatoPedidoEnv.setBounds(new Rectangle(125, 15, 200, 15));
        lblDatoPedidoEnv.setFocusable(false);
        lblDatoMontoEnv.setText("jLabel2");
        lblDatoMontoEnv.setBounds(new Rectangle(125, 40, 200, 15));
        lblDatoMontoEnv.setFocusable(false);
        lblDatoFechaEnv.setText("jLabel3");
        lblDatoFechaEnv.setBounds(new Rectangle(125, 65, 200, 15));
        lblDatoFechaEnv.setFocusable(false);
        lblDatoNumRefEnv.setText("jLabel4");
        lblDatoNumRefEnv.setBounds(new Rectangle(125, 90, 200, 15));
        lblDatoNumRefEnv.setFocusable(false);
        lblNumRefEnv.setText("Num. Referencia:");
        lblNumRefEnv.setBounds(new Rectangle(15, 90, 100, 15));
        lblNumRefEnv.setFocusable(false);
        pnlResp.add(lblDatoCodVoucher, null);
        pnlResp.add(lblDatoNumAutorizacion, null);
        pnlResp.add(lblCodVoucher, null);
        pnlResp.add(lblNumAutorizacion, null);
        jPanel1.add(lblDatoNumRefEnv, null);
        jPanel1.add(lblDatoFechaEnv, null);
        jPanel1.add(lblDatoMontoEnv, null);
        jPanel1.add(lblDatoPedidoEnv, null);
        jPanel1.add(lblNumRefEnv, null);
        jPanel1.add(pnlResp, null);
        pnlTitleResp.add(lblTitleResp, null);
        jPanel1.add(pnlTitleResp, null);
        jPanel1.add(lblFechaEnv, null);
        jPanel1.add(lblMontoEnv, null);
        jPanel1.add(lblPedidoEnv, null);
        pnlMensajePinpad.add(lblTimer, null);
        pnlMensajePinpad.add(lblMensajePinpad, null);
        pnlFondo.add(lblF5, null);
        pnlFondo.add(pnlMensajePinpad, null);
        pnlFondo.add(jPanel1, null);
        pnlFondo.add(pnlTitulo, null);
        pnlFondo.add(lblEsc);
        pnlFondo.add(lblF11);
        pnlTitulo.add(lblFlagAnulDiaLoteCerrado, null);
        pnlTitulo.add(txtTitulo, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
    }

    public void setValores(String numPedido, String monto, String fecha, String numReferencia, String strCodFormaPago, boolean flagAnulPed)
    {   lblDatoPedidoEnv.setText(numPedido);
        lblDatoMontoEnv.setText(monto);
        lblDatoFechaEnv.setText(fecha);
        lblDatoNumRefEnv.setText(numReferencia);
        this.strCodFormaPago=strCodFormaPago;
        this.flagAnulPed=flagAnulPed;
        
        lblF5.setEnabled(false);
        lblF11.setFocusable(false);
        lblF11.setEnabled(false);
        lblEsc.setFocusable(false);
        lblEsc.setEnabled(false);
    }
    
    public void setPedidoNegativo(String numPedNeg)
    {   this.numPedidoNegativo=numPedNeg;
    }
    
    private void this_windowOpened(WindowEvent e)
    {   
        if(UtilityPinpad.validarLibrerias())
        {
            //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
            if(FarmaVariables.vTipCambio==0)
            {   FarmaUtility.showMessage(this, 
                                        "ATENCIÓN: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acción", 
                                        null);
                cerrarVentana(false);
            }
            else
            {   log.debug("Se inicia la anulación del pedido con el pinpad");
                vConciliacionOnline = DlgProcesar.cargaIndConciliaconOnline();
                //ERIOS 21.11.2014 Verificar el indicador de SIX
                DlgProcesar.cargaIndServicioFarmaSix();
                
                //Si es Visa
                //LLEIVA
                if( this.strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_VISA_PINPAD))
                {   
                    procesoAnulacionPinpadVisa();
                    tipoTarj = "VI";
                }
                //Si es Mastercard
                //ERIOS 2.4.7 Los pagos con CMR pasan por el pinpad de MC
                else if(this.strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_MC_PINPAD) ||
                    this.strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_DINERS) ||
                    this.strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_AMEX) ||
                    (ConstantsPtoVenta.FORPAG_CMR.equalsIgnoreCase(strCodFormaPago) && 
                     !VariablesPtoVenta.vIndFarmaSix.equals("S")))
                {    procesoAnulacionPinpadMC();
                    tipoTarj = "MC";
                }
            }
        }
        else
            cerrarVentana(false);
    }
    
    private void procesoAnulacionPinpadVisa()
    {   HiloProcesoAnularPinpadVisa hilo = new HiloProcesoAnularPinpadVisa();
        
        hilo.tipoMoneda = VariablesPinpad.COD_MONEDA_NACIONAL;
        hilo.codTienda=FarmaVariables.vCodLocal;
        hilo.codCaja=FarmaVariables.vCodLocal;
        hilo.pnlMensajePinpad=pnlMensajePinpad;
        hilo.lblMensajePinpad=lblMensajePinpad;
        hilo.lblF11=lblF11;
        hilo.lblF5=lblF5;
        hilo.lblEsc=lblEsc;
        hilo.pnlFondo=pnlFondo;
        hilo.lblDatoNumAutorizacion=lblDatoNumAutorizacion;
        hilo.lblDatoCodVoucher=lblDatoCodVoucher;
        hilo.lblFlagAnulDiaLoteCerrado = lblFlagAnulDiaLoteCerrado;
        hilo.lblDatoFecha = lblDatoFechaEnv;
        hilo.lblDatoPedidoEnv = lblDatoPedidoEnv;
        hilo.flagAnulPed = flagAnulPed;
        
        hilo.numReferencia=lblDatoNumRefEnv.getText();
        
        hilo.lblTimer = lblTimer;
        hilo.padre = this;
        hilo.formaPago = this.strCodFormaPago;
        
        hilo.start();
    }
    
    private void procesoAnulacionPinpadMC()
    {   HiloProcesoAnularPinpadMC hilo = new HiloProcesoAnularPinpadMC();
        
        //hilo.tipoMoneda = VariablesPinpad.COD_MONEDA_NACIONAL;
        //hilo.codTienda=FarmaVariables.vCodLocal;
        //hilo.codCaja=FarmaVariables.vCodLocal;
        hilo.pnlMensajePinpad=pnlMensajePinpad;
        hilo.lblMensajePinpad=lblMensajePinpad;
        hilo.lblF11=lblF11;
        hilo.lblEsc=lblEsc;
        hilo.lblF5=lblF5;
        hilo.pnlFondo=pnlFondo;
        hilo.monto= new Double(lblDatoMontoEnv.getText());
        hilo.lblDatoNumAutorizacion=lblDatoNumAutorizacion;
        hilo.lblDatoCodVoucher=lblDatoCodVoucher;
        hilo.lblDatoFecha = lblDatoFechaEnv;
        hilo.numReferencia=lblDatoNumRefEnv.getText();
        hilo.flagAnulPed = flagAnulPed;
        hilo.lblFlagAnulDiaLoteCerrado = lblFlagAnulDiaLoteCerrado;
        hilo.lblDatoPedidoEnv = lblDatoPedidoEnv;
        
        hilo.lblTimer = lblTimer;
        hilo.padre = this;
        hilo.formaPago = this.strCodFormaPago;
        
        hilo.start();
    }

    private void this_focusGained(FocusEvent e)
    {   pnlFondo.grabFocus();
    }

    private void pnlFondo_keyPressed(KeyEvent e)
    {   chkkeyPressed(e);
    }
    
    private void chkkeyPressed(KeyEvent e)
    {   if(lblF11.isEnabled() && UtilityPtoVenta.verificaVK_F11(e))
        {   //conciliacionAnulacionPinpad();
            cerrarVentana(true);
        }
        if(lblF5.isEnabled() && e.getKeyCode()==KeyEvent.VK_F5)
        {   
            //LLEIVA 19-Dic-2013 Se añade validacion de administrador local
            if (JConfirmDialog.rptaConfirmDialog(this,"ATENCION: El ejecutar esta opción afectara en el cierre de caja\n¿Esta seguro de continuar?"))
            {
                DlgLogin dlgLogin = new DlgLogin(myParent,ConstantsPtoVenta.MENSAJE_LOGIN,true);
                dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
                dlgLogin.setVisible(true);
                
                if(FarmaVariables.vAceptar)
                {   //LLEIVA 11-Dic-2013 Se indica el flag de la transacción que no se pudo anular por que el lote se encuentra cerrado
                    try
                    {    DBPinpad.guardarIndAnulTransCerr(lblDatoPedidoEnv.getText(), tipoTarj);
                    }
                    catch(Exception ex)
                    {   log.debug("", ex);
                    }
                    cerrarVentana(true);
                }
            }
        }
        if(lblEsc.isEnabled() && e.getKeyCode()==KeyEvent.VK_ESCAPE)
        {   cerrarVentana(false);
        }
    }
    
    public void cerrarVentana(boolean pAceptar)
    {   FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    public void conciliacionAnulacionPinpad()
    {   try
        {
            //ERIOS 2.2.8 Conciliacion offline            
            if(vConciliacionOnline.equals(FarmaConstants.INDICADOR_N)){
                log.debug("Conciliacilion offline");
                return;
            }
            
            FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
           
            ArrayList listTemp = (ArrayList)(facadeRecaudacion.getNumTarjCodAutoriz(lblDatoPedidoEnv.getText()).get(0));
            String numTarjeta = (String)listTemp.get(0);
            String numAutorizacion = (String)listTemp.get(1);
            
            String PLOCAL = "";//facadeRecaudacion.getCodLocalMigra(); 
            String PID_VENDEDOR = "";//facadeRecaudacion.obtenerDniUsuario(FarmaVariables.vNuSecUsu).trim();
            String PFECHA_VENTA = "";
            try
            {   PFECHA_VENTA = FarmaSearch.getFechaHoraBD(1);
            }
            catch(Exception e)
            {   log.error("",e);
            }
            
            double PMONTO_VENTA = new Double(lblDatoMontoEnv.getText());    //0;
            double PNUM_CUOTAS = 0;
            String PCOD_AUTORIZACION = lblDatoNumAutorizacion.getText();
            String PTRACK2 = "0"; //solo en venta
            String PCOD_AUTORIZACION_PRE = "0"; //solo en venta cuando es anulacion
            double PFPA_VALORXCUOTA = 0;  //solo en venta
            int PCAJA = Integer.parseInt   (VariablesCaja.vNumCaja);
            String PTIPO_TRANSACCION = "3"; //solo en venta 1 Venta y 3 venta Anulacion 8 Pago y 9 Pago Anulacion
            String PCODISERV = "0"; //solo en Recaudacion Pago 02 EstaCta Citibank, 04 Ripley, 07 CMR, 14 Financiero, 15 Claro, 18 Prest Terc. Citibank                
            String PFPA_NUM_OBJ_PAGO = numTarjeta; //Nro de Celular(nro recibo) o Nro de Tarjeta o Nro de Cuenta
            String PNOMBCLIE = "";
            long PVOUCHER = Long.parseLong(numPedidoNegativo);                  //Nro de Comprobante
            long PNRO_COMP_ANU = Long.parseLong(lblDatoPedidoEnv.getText());    //Anulacion-Nro de Comprobante origen
            String PFECH_COMP_ANU = lblDatoFechaEnv.getText();                  //Anulacion-Fecha Origen del Comprobante  
            String PCODIAUTOORIG = numAutorizacion;                             //lblDatoNumAutorizacion.getText(); //Anulacion-Codigo autorizacion Origen
            double PFPA_TIPOCAMBIO = FarmaVariables.vTipCambio;
            long PFPA_NROTRACE = 0; //PARA TODOS LOS CASOS SERA CERO
            int PCOD_ALIANZA = 0;
            
            PCOD_ALIANZA = facadeRecaudacion.getCodigoAlianzaConciliacion(this.strCodFormaPago);
            
            int PCOD_MONEDA_TRX = 0;
            
            ////Recaudacion 1 Soles , 2 Dolares, en venta tipo 7(CMR),9 Ripley, 5 Tcredit, 6 Tdebito , 
            String PMON_ESTPAGO = ConstantsRecaudacion.RCD_COD_MONEDA_SOLES;
    
            String descProceso = "";
            if(ConstantsPtoVenta.FORPAG_VISA_PINPAD.equals(strCodFormaPago))
                descProceso = ConstantsCajaElectronica.GUARD_CONC_ANULACION_PINPAD_VISA;
            else if(ConstantsPtoVenta.FORPAG_MC_PINPAD.equals(strCodFormaPago))
                descProceso = ConstantsCajaElectronica.GUARD_CONC_ANULACION_PINPAD_MASTERCARD;
            else if(ConstantsPtoVenta.FORPAG_DINERS.equals(strCodFormaPago))
                descProceso = ConstantsCajaElectronica.GUARD_CONC_ANULACION_PINPAD_DINERS;
            else if(ConstantsPtoVenta.FORPAG_AMEX.equals(strCodFormaPago))
                descProceso = ConstantsCajaElectronica.GUARD_CONC_ANULACION_PINPAD_AMEX;
            
            String vSalida = facadeRecaudacion.setDatosRecauConciliacion(   PLOCAL,
                                                                           PID_VENDEDOR, 
                                                                           PFECHA_VENTA, 
                                                                           PMONTO_VENTA,
                                                                           PNUM_CUOTAS,
                                                                           PCOD_AUTORIZACION, 
                                                                           PTRACK2,
                                                                           PCOD_AUTORIZACION_PRE,
                                                                           PFPA_VALORXCUOTA,                                                                         
                                                                           PCAJA,
                                                                           PTIPO_TRANSACCION,
                                                                           PCODISERV,
                                                                           PFPA_NUM_OBJ_PAGO,                                                                          
                                                                           PNOMBCLIE,                 
                                                                           PVOUCHER, 
                                                                           PNRO_COMP_ANU,
                                                                           PFECH_COMP_ANU,
                                                                           PCODIAUTOORIG,
                                                                           PFPA_TIPOCAMBIO,
                                                                           PFPA_NROTRACE, 
                                                                           PCOD_ALIANZA, 
                                                                           PCOD_MONEDA_TRX,
                                                                           PMON_ESTPAGO,
                                                                           descProceso);
            
            log.debug("Resultado conciliacion: "+vSalida);
        }
        catch(Exception ex)
        {   log.debug("", ex);
            FarmaUtility.showMessage(this, ex.getMessage(), null);
        }
    }
    
    public JLabel getAnulDiaLoteCerrado()
    {   return this.lblFlagAnulDiaLoteCerrado;
    }
}
