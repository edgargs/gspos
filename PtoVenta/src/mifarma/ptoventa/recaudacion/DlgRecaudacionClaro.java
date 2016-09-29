package mifarma.ptoventa.recaudacion;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recaudacion.reference.UtilityRecaudacion;
import mifarma.ptoventa.recaudacion.reference.VariablesRecaudacion;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRecaudacionClaro extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgRecaudacionPrestamosCitibank.class);

    Frame myParentFrame;

    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JButton btnRecibo = new JButton();
    private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();
    private JPanelHeader jPanel3 = new JPanelHeader();
    private JLabel lblUsuario = new JLabel();
    private JLabel txtUsuario = new JLabel();
    private JLabel lblFecha = new JLabel();
    private JLabel txtFecha = new JLabel();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();

    UtilityRecaudacion utilityRecaudacion = new UtilityRecaudacion();
    UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite lblMensaje = new JLabelWhite();

    private boolean bDeuda;
    FacadeRecaudacion facadeRecaudacion;
    String codAuditoria = "";
    String responseCode = "";
    String tmpMontoPagar = "";
    String tipoProdServ = "1"; //celular
    String numRecibo = "";
    private JButton btnBuscar = new JButton();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgRecaudacionClaro() {
        this(null, "", false);
    }

    public DlgRecaudacionClaro(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            utilityRecaudacion.initMensajesVentana(this, txtFecha, txtUsuario, ConstantsRecaudacion.TIPO_REC_CLARO);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(454, 297));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Recaudación Claro");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanel1.setLayout(null);
        jPanel1.setForeground(Color.white);
        jPanel1.setBackground(Color.white);
        btnRecibo.setText("Celular :");
        btnRecibo.setBackground(Color.white);
        btnRecibo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnRecibo.setBorderPainted(false);
        btnRecibo.setContentAreaFilled(false);
        btnRecibo.setDefaultCapable(false);
        btnRecibo.setFocusPainted(false);
        btnRecibo.setFont(new Font("SansSerif", 1, 11));
        btnRecibo.setForeground(new Color(255, 130, 14));
        btnRecibo.setHorizontalAlignment(SwingConstants.LEFT);
        btnRecibo.setMnemonic('C');
        btnRecibo.setRequestFocusEnabled(false);
        btnRecibo.setBounds(new Rectangle(15, 15, 105, 20));
        btnRecibo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRecibo_actionPerformed(e);
            }
        });
        txtTelefono.setBounds(new Rectangle(125, 15, 145, 20));
        txtTelefono.setLengthText(9);
        txtTelefono.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtTelefono_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtTelefono_keyTyped(e);
            }
        });
        jPanel3.setBounds(new Rectangle(20, 20, 410, 55));
        lblUsuario.setText("Usuario :");
        lblUsuario.setSize(new Dimension(30, 20));
        lblUsuario.setFont(new Font("Dialog", 1, 11));
        lblUsuario.setBounds(new Rectangle(15, 20, 55, 20));
        lblUsuario.setForeground(Color.white);
        txtUsuario.setBounds(new Rectangle(70, 20, 100, 20));
        txtUsuario.setFont(new Font("Dialog", 1, 11));
        txtUsuario.setForeground(Color.white);
        lblFecha.setText("Fecha :");
        lblFecha.setBounds(new Rectangle(190, 20, 50, 20));
        lblFecha.setForeground(Color.white);
        lblFecha.setFont(new Font("Dialog", 1, 11));
        lblFecha.setSize(new Dimension(75, 20));
        txtFecha.setForeground(Color.white);
        txtFecha.setFont(new Font("Dialog", 1, 11));
        txtFecha.setBounds(new Rectangle(240, 20, 150, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(240, 230, 90, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(340, 230, 90, 20));
        jPanelTitle1.setBounds(new Rectangle(0, 95, 410, 45));
        lblMensaje.setText("[ MENSAJE ]");
        lblMensaje.setBounds(new Rectangle(15, 10, 380, 25));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(295, 15, 75, 21));
        btnBuscar.setMnemonic('B');
        btnBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnBuscar_keyPressed(e);
            }
        });
        jPanel2.setBounds(new Rectangle(20, 80, 410, 140));
        jPanel2.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        jPanel2.setBackground(Color.white);
        jPanel2.setLayout(null);
        jPanel2.setForeground(Color.white);
        jPanel3.add(txtFecha, null);
        jPanel3.add(lblFecha, null);

        jPanel3.add(txtUsuario, null);
        jPanel3.add(lblUsuario, null);
        jPanel1.add(jPanel2, null);
        jPanel1.add(jPanel3, null);
        jPanel1.add(lblF11, null);
        jPanel1.add(lblEsc, null);
        jPanel2.add(btnBuscar, null);
        jPanel2.add(btnRecibo, null);
        jPanel2.add(txtTelefono, null);
        jPanelTitle1.add(lblMensaje, null);
        jPanel2.add(jPanelTitle1, null);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        facadeRecaudacion = new FacadeRecaudacion();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
        if (VariablesRecaudacion.vTipoCambioVenta == 0) {
            FarmaUtility.showMessage(this,
                                     "ATENCIÓN: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acción",
                                     null);
            cerrarVentana(false);
        } else {
            FarmaUtility.centrarVentana(this);
            lblMensaje.setVisible(false);
            FarmaUtility.moveFocus(txtTelefono);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void txtTelefono_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            lblMensaje.setText("");
            FarmaUtility.moveFocus(btnBuscar);
        } else {
            chkKeyPressed(e);
        }
    }

    private void btnBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (utilityPtoVenta.validarCampTxtField(this, txtTelefono)) {
                bDeuda = buscarDeuda();
                FarmaUtility.moveFocus(txtTelefono);
            } else {
                lblMensaje.setText("");
            }
        } else {
            chkKeyPressed(e);
        }
    }

    private void btnRecibo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtTelefono);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (utilityPtoVenta.validarCampTxtField(this, txtTelefono)) {
                if (bDeuda) {
                    funcF11();
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtTelefono_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtTelefono, e);
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void funcF11() {
        ArrayList arrayCabecera = new ArrayList();
        /*0*/arrayCabecera.add(FarmaVariables.vCodGrupoCia);
        /*1*/arrayCabecera.add(FarmaVariables.vCodCia);
        /*2*/arrayCabecera.add(FarmaVariables.vCodLocal);
        /*3*/arrayCabecera.add("");
        /*4*/arrayCabecera.add(ConstantsRecaudacion.TIPO_REC_CLARO);
        /*5*/arrayCabecera.add("");
        /*6*/arrayCabecera.add(ConstantsRecaudacion.ESTADO_PENDIENTE);
        /*7*/arrayCabecera.add(ConstantsRecaudacion.EST_CTA_SOLES);
        /*8*/arrayCabecera.add(txtTelefono.getText().trim());
        /*9*/arrayCabecera.add(FarmaConstants.MONEDA_SOLES);
        /*10*/arrayCabecera.add(UtilityRecaudacion.formatNumber(tmpMontoPagar)); //Monto total(moneda original)
        /*11*/arrayCabecera.add(UtilityRecaudacion.formatNumber(tmpMontoPagar)); //Monto soles
        /*12*/arrayCabecera.add(UtilityRecaudacion.formatNumber(tmpMontoPagar)); //Monto minimo
        /*13*/arrayCabecera.add(VariablesRecaudacion.vTipoCambioVenta); //Tipo cambio
        /*14*/arrayCabecera.add(""); //Fecha Venc Tarj (CMR)
        /*15*/arrayCabecera.add("");
        /*16*/arrayCabecera.add("");
        /*17*/arrayCabecera.add(ConstantsRecaudacion.FECHA_RCD);
        /*18*/arrayCabecera.add(FarmaVariables.vIdUsu);
        /*19*/arrayCabecera.add("");
        /*20*/arrayCabecera.add("");
        /*21*/arrayCabecera.add(""); //codigo de autorizacion
        /*22*/arrayCabecera.add(""); //mov de caja
        /*23*/arrayCabecera.add(""); //numero de pedido solo valido para VENTA CMR
        /*24*/arrayCabecera.add(tipoProdServ); //tipo producto servicio
        /*25*/arrayCabecera.add(numRecibo); //numero de recibo de pago

        String tmpCodigo = facadeRecaudacion.grabaCabeRecau(arrayCabecera,"");

        if (!tmpCodigo.equals("")) {
            if (facadeRecaudacion.cobrarRecaudacion(myParentFrame, tmpCodigo, arrayCabecera,
                                                    ConstantsRecaudacion.TIPO_REC_CLARO)) {
                cerrarVentana(true);
            }
        } else {
            FarmaUtility.showMessage(this, "Error al procesar el cobro.", txtTelefono);
        }
    }

    private boolean buscarDeuda() {

        ArrayList<Object> rptSix = null;
        boolean bRpt = false;
        String strResponseCode = "";
        lblMensaje.setVisible(true);
        lblMensaje.setText(". . . ");
        DlgProcesarPagoTerceros dlgProcesarPagoTerceros = new DlgProcesarPagoTerceros(myParentFrame, "", true);
        dlgProcesarPagoTerceros.setStrIndProc(ConstantsRecaudacion.RCD_IND_PROCESO_CONSU_CLARO);
        dlgProcesarPagoTerceros.procesarConsultaClaro("TER1",
                                                      //terminal
                txtTelefono.getText().trim(), //telefono
                tipoProdServ);
        dlgProcesarPagoTerceros.mostrar();
        rptSix = dlgProcesarPagoTerceros.getRptSix();

        bRpt = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
        //bMsj = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
        strResponseCode = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
        tmpMontoPagar = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
        codAuditoria = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA);
        numRecibo = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_NUM_RECIBO);
        //strCodAutorizacion = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ);// SE GUARDA EN LA CABECERA DE RECAUDACION PARA COMPRA Y VENTA CMR

        if (bRpt) { //se devolvio respuesta
            if (ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode)) {
                lblMensaje.setText("Tiene una deuda de : S/." + tmpMontoPagar + "   Recibo Nro. " + numRecibo);
            } else {
                //respuesta con codigo de error
                if (ConstantsRecaudacion.COD_NO_RESPUESTA.equals(strResponseCode)) {
                    FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_NO_RESPUESTA, null);
                } else if (strResponseCode.equals("91") || strResponseCode.equals("94")) {
                    FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_CLARO_SERV_INACTIVO, null);
                    //}else if(ConstantsRecaudacion.COD_NO_RECIB_PEND_CLARO.equals(strResponseCode)){
                    //     FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_RECIBOS_CLARO_NO_HAY_PENDIENTES, null);
                } else {
                    //ERIOS 2.2.9 Mensaje del operador
                    FarmaUtility.showMessage(this, "Mensaje Operador" + ":\n" +
                            (String)rptSix.get(12), null);
                }
                lblMensaje.setText("No se obtuvieron resultados.");
                return false;
            }
        } else { //timeout o error en el six
            log.debug("No se encontro respuesta de la transacción en el servidor.");
            lblMensaje.setText("No se encontro respuesta de la transacción en el servidor.");
            return false;
        }

        return true;
    }
}
