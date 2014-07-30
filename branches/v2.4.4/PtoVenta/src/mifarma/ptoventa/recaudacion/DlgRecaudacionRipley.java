package mifarma.ptoventa.recaudacion;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

import com.gs.mifarma.componentes.JTextFieldTarjeta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.UtilityLectorTarjeta;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recaudacion.reference.UtilityRecaudacion;
import mifarma.ptoventa.recaudacion.reference.VariablesRecaudacion;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


    public class DlgRecaudacionRipley extends JDialog {
        
        /* ********************************************************************** */
        /*                        DECLARACION PROPIEDADES                         */
        /* ********************************************************************** */
        
        static final Logger log = LoggerFactory.getLogger(DlgRecaudacionRipley.class);

        Frame myParentFrame;
        
        private JPanel jPanel1 = new JPanel();
        private JPanel jPanel2 = new JPanel();
        private BorderLayout borderLayout1 = new BorderLayout();
        private JButton btnTarjeta = new JButton();
        private JButton btnMontoPagar = new JButton();
        private JTextFieldTarjeta txtTarjeta = new JTextFieldTarjeta();
        private JComboBox cmbMoneda = new JComboBox();
        private JPanel jPanel3 = new JPanel();
        private JLabel lblUsuario = new JLabel();
        private JLabel txtUsuario = new JLabel();
        private JLabel lblFecha = new JLabel();
        private JLabel txtFecha = new JLabel();
        private JLabelFunction lblF11 = new JLabelFunction();
        private JLabelFunction lblEsc = new JLabelFunction();
        private JTextFieldSanSerif txtMontoPagar = new JTextFieldSanSerif();
        ArrayList tmpArrayPres = new ArrayList();
        ArrayList listInforTarje = new ArrayList();
        private JLabel lblTipTarjeta = new JLabel();
        private JPanel jPnlTipTarj = new JPanel();
        private JLabel jlblTipTarjD = new JLabel();
        private GridLayout gridLayout1 = new GridLayout();
        private FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();
        UtilityRecaudacion utilityRecaudacion = new UtilityRecaudacion();
        UtilityLectorTarjeta utilityLectorTarjeta = new UtilityLectorTarjeta();
        
        /* ********************************************************************** */
        /*                        CONSTRUCTORES                                   */
        /* ********************************************************************** */
        
        public DlgRecaudacionRipley() {
            this(null, "", false);
        }

        public DlgRecaudacionRipley(Frame parent, String title, boolean modal) {
            super(parent, title, modal);
            myParentFrame = parent;
            try {
                jbInit();
                initialize();            
                utilityRecaudacion.initMensajesVentana(this, txtFecha, txtUsuario, ConstantsRecaudacion.TIPO_REC_RIPLEY);
            } catch (Exception e) {
                log.error("",e);
            }
        }
        
        /* ************************************************************************ */
        /*                                  METODO jbInit                           */
        /* ************************************************************************ */

        private void jbInit() throws Exception {
            this.setSize(new Dimension(457, 289));
            this.getContentPane().setLayout(borderLayout1);
            this.setTitle("Recaudacion Ripley");
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
            btnTarjeta.setText("Tarjeta: ");
            btnTarjeta.setBackground(Color.white);
            btnTarjeta.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            btnTarjeta.setBorderPainted(false);
            btnTarjeta.setContentAreaFilled(false);
            btnTarjeta.setDefaultCapable(false);
            btnTarjeta.setFocusPainted(false);
            btnTarjeta.setFont(new Font("SansSerif", 1, 11));
            btnTarjeta.setForeground(new Color(255, 130, 14));
            btnTarjeta.setHorizontalAlignment(SwingConstants.LEFT);
            btnTarjeta.setMnemonic('T');
            btnTarjeta.setRequestFocusEnabled(false);
            btnTarjeta.setBounds(new Rectangle(15, 15, 50, 20));
            btnTarjeta.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            btnTarjeta_actionPerformed(e);
                        }
                    });
            btnMontoPagar.setText("Monto a Pagar :");
            btnMontoPagar.setBackground(Color.white);
            btnMontoPagar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            btnMontoPagar.setBorderPainted(false);
            btnMontoPagar.setContentAreaFilled(false);
            btnMontoPagar.setDefaultCapable(false);
            btnMontoPagar.setFocusPainted(false);
            btnMontoPagar.setFont(new Font("SansSerif", 1, 11));
            btnMontoPagar.setForeground(new Color(255, 130, 14));
            btnMontoPagar.setHorizontalAlignment(SwingConstants.LEFT);
            btnMontoPagar.setMnemonic('P');
            btnMontoPagar.setRequestFocusEnabled(false);
            btnMontoPagar.setBounds(new Rectangle(15, 90, 90, 20));
            btnMontoPagar.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            btnTipoPago_actionPerformed(e);
                        }
                    });
            txtTarjeta.setBounds(new Rectangle(115, 15, 170, 20));
            cmbMoneda.setBounds(new Rectangle(205, 90, 80, 20));
            cmbMoneda.setPreferredSize(new Dimension(25, 20));
            cmbMoneda.addKeyListener(new KeyAdapter() {
                        public void keyPressed(KeyEvent e) {
                        cmbTipoPago_keyPressed(e);
                    }
                    });
            cmbMoneda.addItem("Soles");
            cmbMoneda.addItem("Dolares");
            jPanel3.setBounds(new Rectangle(20, 20, 410, 55));
            jPanel3.setBackground(new Color(43, 141, 39));
            jPanel3.setForeground(new Color(0, 132, 66));
            jPanel3.setLayout(null);
            lblUsuario.setText("Usuario :");        
            lblUsuario.setSize(new Dimension(30, 20));
            lblUsuario.setFont(new Font("Dialog", 1, 11));
            lblUsuario.setBounds(new Rectangle(15, 15, 55, 20));
            lblUsuario.setForeground(Color.white);
            txtUsuario.setBounds(new Rectangle(70, 15, 100, 20));
            txtUsuario.setFont(new Font("Dialog", 1, 11));
            txtUsuario.setForeground(Color.white);
            lblFecha.setText("Fecha :");
            lblFecha.setBounds(new Rectangle(190, 15, 75, 20));
            lblFecha.setForeground(Color.white);
            lblFecha.setFont(new Font("Dialog", 1, 11));
            lblFecha.setSize(new Dimension(75, 20));        
            txtFecha.setForeground(Color.white);
            txtFecha.setFont(new Font("Dialog", 1, 11));
            txtFecha.setBounds(new Rectangle(240, 15, 150, 20));
            lblF11.setText("[ F11 ] Aceptar");
            lblF11.setBounds(new Rectangle(240, 225, 90, 20));
            lblEsc.setText("[ ESC ] Cerrar");
            lblEsc.setBounds(new Rectangle(340, 220, 90, 20));
            lblEsc.setBounds(new Rectangle(340, 225, 90, 20));
            lblTipTarjeta.setText("Tipo Tarjeta :");
            lblTipTarjeta.setBounds(new Rectangle(15, 55, 80, 20));
            lblTipTarjeta.setBackground(SystemColor.window);
            lblTipTarjeta.setForeground(new Color(255, 130, 14));
            lblTipTarjeta.setFont(new Font("SansSerif", 1, 11));
            jPnlTipTarj.setBounds(new Rectangle(115, 50, 170, 25));
            jPnlTipTarj.setBackground(new Color(255, 130, 14));
            jPnlTipTarj.setLayout(gridLayout1);
            jlblTipTarjD.setText("[TIPO]");
            jlblTipTarjD.setForeground(SystemColor.window);
            jlblTipTarjD.setFont(new Font("SansSerif", 1, 11));
            txtMontoPagar.setBounds(new Rectangle(115, 90, 80, 20));
            txtMontoPagar.setLengthText(9);
            txtMontoPagar.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtMontoPagar_keyPressed(e);
                    }
                    public void keyTyped(KeyEvent e) {
                        txtMontoPagar_keyTyped(e);
                    }
                });
            jPanel2.setBounds(new Rectangle(20, 80, 410, 130));
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
            jPanel2.add(txtMontoPagar, null);
            jPnlTipTarj.add(jlblTipTarjD, null);
            jPanel2.add(jPnlTipTarj, null);
            jPanel2.add(lblTipTarjeta, null);
            jPanel2.add(btnTarjeta, null);
            jPanel2.add(btnMontoPagar, null);
            jPanel2.add(txtTarjeta, null);
            jPanel2.add(cmbMoneda, null);
            this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        }
        
        /* ************************************************************************ */
        /*                                  METODO initialize                       */
        /* ************************************************************************ */
        
        private void initialize(){
            inicializaTxtTarjeta();
        }
            
        /* ************************************************************************ */
        /*                            METODOS INICIALIZACION                        */
        /* ************************************************************************ */
        
        private void inicializaTxtTarjeta(){
            txtTarjeta.setLblTipTarjD(jlblTipTarjD);
            txtTarjeta.setCompNextFocus(txtMontoPagar);
            txtTarjeta.setLblF11(lblF11);
            txtTarjeta.setMyParentFrame(this);
            
            //ERIOS 06.11.2013 Formas de pago
            ArrayList<String> arrayCodFormaPago = new ArrayList<String>();
            arrayCodFormaPago.add(ConstantsRecaudacion.FORMA_PAGO_RIPLEY);// "00086" //TODO Mejorar
            txtTarjeta.setArrayFormPago(arrayCodFormaPago);
            
            //ERIOS 2.2.8 Validacion de accion F11
            lblF11.setEnabled(false);
        }  
        
        
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */
    
    private void this_windowOpened(WindowEvent e)
    {
        //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
        if(VariablesRecaudacion.vTipoCambioVenta==0)
        {   FarmaUtility.showMessage(this, 
                                    "ATENCIÓN: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acción", 
                                    null);
            cerrarVentana(false);
        }
        else
        {
            FarmaUtility.centrarVentana(this);
            FarmaUtility.moveFocus(txtTarjeta);
        }
     }

        private void this_windowClosing(WindowEvent e) {
            FarmaUtility.showMessage(this, 
                                     "Debe presionar la tecla ESC para cerrar la ventana.", 
                                     null);
        }

        private void btnTarjeta_actionPerformed(ActionEvent e) {
            FarmaUtility.moveFocus(txtTarjeta);
        }

        private void btnTipoPago_actionPerformed(ActionEvent e) {
            FarmaUtility.moveFocus(cmbMoneda);
        }
        
        private void txtMontoPagar_keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                cmbMoneda.requestFocus();    
            }else{
                chkKeyPressed(e);
            }   
        }

        private void cmbTipoPago_keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){                 
                if(txtTarjeta.getText().trim().equals("")){
                    txtTarjeta.requestFocus();                      
                }else{
                    txtMontoPagar.requestFocus();
                }
            }else{
                chkKeyPressed(e);
            }  
                    
        }
        
        private void txtMontoPagar_keyTyped(KeyEvent e) {
            FarmaUtility.admitirDigitosDecimales(txtMontoPagar, e);
        }

        /* ************************************************************************ */
        /*                     METODOS AUXILIARES DE EVENTOS                        */
        /* ************************************************************************ */

        private void chkKeyPressed(KeyEvent e){
            if (UtilityPtoVenta.verificaVK_F11(e) && lblF11.isEnabled()){
                if(utilityPtoVenta.validarCampTxtField(this, txtTarjeta) &&
                         utilityPtoVenta.validarCampTxtField(this, txtMontoPagar)){                
                    funcF11();               
                }
            }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                cerrarVentana(false);
            }
        }
        
        private void cerrarVentana(boolean pAceptar){
            FarmaVariables.vAceptar = pAceptar;
            this.setVisible(false);
            this.dispose();
        }
        
        /* ************************************************************************ */
        /*                     METODOS DE LOGICA DE NEGOCIO                         */
        /* ************************************************************************ */
        
        private void funcF11(){            
            String strEstadoCuenta="";
            String strMonedaPago="";
            String strNumTarjeta = txtTarjeta.getNroTarjeta().trim();
            double dCuantoPaga = FarmaUtility.getDecimalNumber(txtMontoPagar.getText());
            double dMontoPagarSoles=0.00;
            double dMontoPagardolares=0.00;
            double TotalVenta=0.00;
            boolean tipmoneda=true;
            
           if(cmbMoneda.getSelectedIndex()==0)  {  
                strMonedaPago="01"  ;
                dMontoPagarSoles=dCuantoPaga;
                TotalVenta=dMontoPagarSoles;
                strEstadoCuenta = ConstantsRecaudacion.EST_CTA_SOLES;
            }else if(cmbMoneda.getSelectedIndex()==1) {   
                strMonedaPago="02";
                dMontoPagardolares=dCuantoPaga;
                TotalVenta=dMontoPagardolares*VariablesRecaudacion.vTipoCambioVenta;
                strEstadoCuenta = ConstantsRecaudacion.EST_CTA_DOLARES;
                tipmoneda=false;
            }
            
            ArrayList<Object> arrayCabecera = new ArrayList<Object>();
            /*0*/arrayCabecera.add(FarmaVariables.vCodGrupoCia);
            /*1*/arrayCabecera.add(FarmaVariables.vCodCia);
            /*2*/arrayCabecera.add(FarmaVariables.vCodLocal);
            /*3*/arrayCabecera.add(strNumTarjeta);
            /*4*/arrayCabecera.add(ConstantsRecaudacion.TIPO_REC_RIPLEY);
            /*5*/arrayCabecera.add("");  // TIPO PAGO //FarmaLoadCVL.getCVLCode(ConstantsRecaudacion.NOM_HASTABLE_CMB_TIPO_PAGO, cmbTipoPago.getSelectedIndex())
            /*6*/arrayCabecera.add(ConstantsRecaudacion.ESTADO_PENDIENTE); // ESTADO
            /*7*/arrayCabecera.add(strEstadoCuenta); //ESTADO DE CUENTA
            /*8*/arrayCabecera.add(""); //CODIGO DEL CLIENTE
            /*9*/arrayCabecera.add(strMonedaPago); //TIPO DE MONEDA
            /*10*/arrayCabecera.add(String.valueOf(dCuantoPaga));//Monto total(moneda original)
            /*11*/arrayCabecera.add(String.valueOf(TotalVenta));//Monto soles
            /*12*/arrayCabecera.add(String.valueOf(TotalVenta));//Monto minimo
            /*13*/arrayCabecera.add(VariablesRecaudacion.vTipoCambioVenta);//Tipo cambio
            /*14*/arrayCabecera.add("");//Fecha DE VENCIMIENTO DE RECAUDACION 
            /*15*/arrayCabecera.add("");//NOMBRE DEL CLIENTE
            /*16*/arrayCabecera.add("");//Fecha Venc Tarj (CMR)
            /*17*/arrayCabecera.add(ConstantsRecaudacion.FECHA_RCD); //FECHA DE CREACION
            /*18*/arrayCabecera.add(FarmaVariables.vIdUsu); // USUARIO CREADOR
            /*19*/arrayCabecera.add("");//FECHA DE MODIFICACION
            /*20*/arrayCabecera.add("");//USUARIO MODIFICADOR
            /*21*/arrayCabecera.add("");//codigo de autorizacion
            /*22*/arrayCabecera.add(""); //mov de caja
            /*23*/arrayCabecera.add(""); //numero de pedido solo valido para VENTA CMR
            /*24*/arrayCabecera.add(""); //tipo producto servicio
            /*25*/arrayCabecera.add(""); //numero de recibo de pago
                  
            String tmpCodigo = facadeRecaudacion.grabaCabeRecau(arrayCabecera);
           
            if(!tmpCodigo.equals("")){        
                if(facadeRecaudacion.cobrarRecaudacion(myParentFrame,tmpCodigo,arrayCabecera,ConstantsRecaudacion.TIPO_REC_RIPLEY)){                
                    cerrarVentana(true);   
                }            
            }else{
                FarmaUtility.showMessage(this, "Error al procesar el cobro." , txtTarjeta);
            }        
        }
        
    }


