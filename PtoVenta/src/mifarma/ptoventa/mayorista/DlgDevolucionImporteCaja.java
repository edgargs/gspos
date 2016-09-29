package mifarma.ptoventa.mayorista;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.mayorista.reference.DBMayorista;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDevolucionImporteCaja extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgDevolucionImporteCaja.class);

   
    private Frame myParentFrame;

    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JPanel jPanel1 = new JPanel();
    private XYLayout xYLayout1 = new XYLayout();
    private JPanel jPanel2 = new JPanel();
    private JButtonLabel lblCodigoDevImporte = new JButtonLabel();
    private XYLayout xYLayout2 = new XYLayout();
    private JTextField txtDevolutionImport = new JTextField();
    private JLabel lblImporteDev = new JLabel();
    private JLabel lblFechaPedido = new JLabel();
   // private JTextField jTextField2 = new JTextField();
   // private JTextField jTextField3 = new JTextField();
    private JLabel lblValFechaActual = new JLabel();
    
    private boolean isLectoraLazer, isCodigoBarra, isEnter;
    private long tiempoTeclaInicial ,tiempoTeclaFinal,OldtmpT2;
    private JLabel lblMessagge = new JLabel();
    private JLabel lblValUsuario = new JLabel();
    private JLabel lblUsuario = new JLabel();
    private JLabel lblFechaActual = new JLabel();
    private JLabel lblImporte = new JLabel();
    private JLabel lblFecPedido = new JLabel();

    public DlgDevolucionImporteCaja() {
        this(null, "", false);
    }

    public DlgDevolucionImporteCaja(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(457, 241));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Devolucion de Importe");
        pnlFondo.setFocusable(false);
        pnlTitle.setFocusable(false);
        lblEsc.setText("[ Esc] Cerrar");
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setHorizontalTextPosition(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        lblF11.setText("[ F11 ] Devolucion");
        lblF11.setFocusable(false);
        lblF12.setText("[ F12 ] Cancelar");
        lblF12.setFocusable(false);        
        jPanel1.setBounds(new Rectangle(0, 0, 455, 215));
        jPanel1.setLayout(xYLayout1);
        jPanel1.setBackground(SystemColor.window);
        jPanel2.setBackground(SystemColor.window);
        jPanel2.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        jPanel2.setLayout(xYLayout2);
        lblCodigoDevImporte.setText("Codigo de devolucionde importe:");
        lblCodigoDevImporte.setFont(new Font("SansSerif", 1, 11));
        lblCodigoDevImporte.setForeground(new Color(255, 90, 33));
        lblCodigoDevImporte.setMnemonic('C');
        lblCodigoDevImporte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblCodigoDevImporte_actionPerformed(e);
            }
        });        
        txtDevolutionImport.setDocument(new FarmaLengthText(13));
        txtDevolutionImport.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDevolutionImport_keyPressed(e);

            }

        });
        txtDevolutionImport.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtDevolutionImport_focusLost(e);
                }
            });
        lblImporteDev.setText("Importe de Devolucion:");
        lblImporteDev.setFont(new Font("SansSerif", 1, 11));
        lblImporteDev.setForeground(new Color(255, 90, 33));
        lblFechaPedido.setText("Fecha del pedido:");
        lblFechaPedido.setFont(new Font("SansSerif", 1, 11));
        lblFechaPedido.setForeground(new Color(255, 90, 33));

        lblValFechaActual.setText("lblValFechaActual");
        lblValFechaActual.setBounds(new Rectangle(340, 0, 105, 15));
        lblValFechaActual.setText("12/01/2006 09:20:34");
        lblValFechaActual.setForeground(Color.white);
        lblValFechaActual.setFont(new Font("SansSerif", 1, 11));
        lblValFechaActual.setVisible(false);
        lblMessagge.setForeground(new Color(33, 33, 255));
        lblValUsuario.setText("userName");
        lblValUsuario.setBounds(new Rectangle(60, 0, 180, 15));
        lblValUsuario.setForeground(SystemColor.window);
        lblValUsuario.setFont(new Font("SansSerif", 1, 11));
        lblUsuario.setText("Usuario:");
        lblUsuario.setBounds(new Rectangle(0, 0, 55, 15));
        lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUsuario.setForeground(SystemColor.window);
        lblUsuario.setFont(new Font("SansSerif", 1, 11));
        lblFechaActual.setText("Fecha Actual:");
        lblFechaActual.setBounds(new Rectangle(255, 0, 80, 15));
        lblFechaActual.setHorizontalAlignment(SwingConstants.RIGHT);
        lblFechaActual.setForeground(SystemColor.window);
        lblFechaActual.setFont(new Font("SansSerif", 1, 11));
        lblFechaActual.setVisible(false);
        lblImporte.setForeground(new Color(0, 128, 0));
        lblImporte.setFont(new Font("SansSerif", 1, 14));
        lblFecPedido.setForeground(new Color(0, 128, 0));
        lblFecPedido.setFont(new Font("SansSerif", 1, 13));
        jPanel2.add(lblFecPedido, new XYConstraints(199, 74, 180, 20));
        jPanel2.add(lblImporte, new XYConstraints(199, 44, 180, 20));
        jPanel2.add(lblMessagge, new XYConstraints(64, 109, 345, 20));
        jPanel2.add(lblFechaPedido, new XYConstraints(4, 74, 170, 20));
        jPanel2.add(lblImporteDev, new XYConstraints(4, 44, 165, 20));
        jPanel2.add(txtDevolutionImport, new XYConstraints(199, 9, 180, 20));
        jPanel2.add(lblCodigoDevImporte, new XYConstraints(4, 9, 195, 20));
        jPanel1.add(jPanel2, new XYConstraints(5, 25, 430, 145));
        jPanel1.add(pnlTitle, new XYConstraints(0, 0, 455, 15));
        jPanel1.add(lblEsc, new XYConstraints(330, 180, 105, 25));
        jPanel1.add(lblF11, new XYConstraints(70, 180, 125, 25));
        jPanel1.add(lblF12, new XYConstraints(200, 180, 125, 25));
        pnlTitle.add(lblFechaActual, null);
        pnlTitle.add(lblUsuario, null);
        pnlTitle.add(lblValUsuario, null);
        pnlTitle.add(lblValFechaActual, null);
        pnlFondo.add(jPanel1, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
        lblF11.setEnabled(false);
        lblF12.setEnabled(false);
        
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        
  
    }
    private void lblCodigoDevImporte_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDevolutionImport);
    };
    private void this_windowOpened(WindowEvent e) {
        
        DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
                    dlgLogin.setRolUsuario(FarmaConstants.ROL_CAJERO);
                    dlgLogin.setVisible(true);
                    if (!FarmaVariables.vAceptar) {
                        cerrarVentana(false);}   
        
        lblMessagge.setForeground(new Color( 33, 33,255)); 
        lblMessagge.setText("Ingrese los caracteres completos a validar.");
        FarmaUtility.moveFocus(txtDevolutionImport);
    }
    

    
    String sRptaValidaCodDev="";
    String sCodigoDevolution="";
    String sNumPedidoVenta="";

    private void initialize() {
        lblValUsuario.setText(FarmaVariables.vNomUsu);
        Date fecha=new Date();        
        lblValFechaActual.setText(getCurrentTimeStamp());
        
        /*Timer timer = new Timer (1000, new ActionListener ()
        {
                                                        public void actionPerformed(ActionEvent e)
                                                        {
                                                            lblValFechaActual.setText(getCurrentTimeStamp());
                                                            System.err.println("------");
                                                        }
        });
        timer.start();*/


        
    }

    private void txtDevolutionImport_keyPressed(KeyEvent e) {

        chkKeyPressed(e);
    }
    
    private void chkKeyPressed(KeyEvent e) {

            sCodigoDevolution=txtDevolutionImport.getText().trim();
        

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        
                cerrarVentana(true);
                
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {  

                    if (sCodigoDevolution.substring(0, sCodigoDevolution.length()).length()<13){
                        //FarmaUtility.showMessage(this, "se procede a validar " , this); 
                        FarmaUtility.showMessage(this, "Ingrese los caracteres completos a validar." , this);
                        log.info("Ingrese los caracteres completos a validar.");
                        lblImporte.setText("");
                        lblFecPedido.setText("");                        
                        lblMessagge.setForeground(new Color( 33, 33,255)); 
                        lblMessagge.setText("Ingrese los caracteres completos a validar.");
                        lblF11.setEnabled(false);
                    }else{
                        sNumPedidoVenta=sCodigoDevolution.substring(3, 13);
                        
                        if(sCodigoDevolution.substring(0, 3).equalsIgnoreCase(FarmaVariables.vCodLocal)){
                            sRptaValidaCodDev=validaCodigoDevolution(sNumPedidoVenta);
                        }else{
                            lblMessagge.setText("Su ticket pertenece a otro local. "); 
                            lblMessagge.setForeground(new Color( 255,33, 33));
                            lblImporte.setText("");
                            lblFecPedido.setText("");
                            sRptaValidaCodDev="V";
                            lblF11.setEnabled(false);
                        }
                        
                        if(sRptaValidaCodDev.substring(0, 1).equalsIgnoreCase("S")){
                            String importe="",fecPedido="";
                            String[] valuesRpta=sRptaValidaCodDev.split("Ã");
                            String estadoDevolucion="";
                            for(int i=0;i<sRptaValidaCodDev.length();i++){
                                importe=valuesRpta[1];
                                fecPedido =valuesRpta[2];
                                estadoDevolucion=valuesRpta[3];
                            }
                                                                    
                            lblImporte.setText(importe);
                            lblFecPedido.setText(fecPedido);
                            lblMessagge.setText("Codigo de devolucion correcto.");
                            lblMessagge.setForeground(new Color( 33, 33,255));
                            txtDevolutionImport.setEditable(false);
                            txtDevolutionImport.setBackground(new Color(237, 237, 237));
                            lblF11.setEnabled(true);
                            lblF12.setEnabled(true);
                            if(estadoDevolucion.equalsIgnoreCase("D")){

                                lblMessagge.setText("El ticket ya fue devuelto el importe..");
                                lblMessagge.setForeground(new Color( 255,33, 33));
                                lblF11.setEnabled(false);  
                                txtDevolutionImport.setEditable(true);
                                txtDevolutionImport.setBackground(new Color(255, 255, 255));
                                lblF12.setEnabled(false);
                            }
                            
                        }else if(sRptaValidaCodDev.substring(0, 1).equalsIgnoreCase("N")){                
                            lblImporte.setText("");
                            lblFecPedido.setText("");
                            lblMessagge.setText("El numero de pedido no existe.");
                            lblMessagge.setForeground(new Color( 255,33, 33));
                            lblF11.setEnabled(false);
                        } 
                        
                        
                        
                    }

            }else if (e.getKeyCode() == KeyEvent.VK_F11 && lblF11.isEnabled()) {  
            
                sNumPedidoVenta=sCodigoDevolution.substring(3, 13);
                        
                        int RptaSql=0;
                        RptaSql=realizaDevolutionImporte(sNumPedidoVenta);
                        if(RptaSql==1){
                            // EXITO
                            FarmaUtility.aceptarTransaccion();
                            //FarmaUtility.showMessage(this, "Se realizo la devolucion de manera exitosa.", txtDevolutionImport);
                            lblF11.setEnabled(false); 
                            lblF12.setEnabled(false);
                            txtDevolutionImport.setEditable(true);
                            txtDevolutionImport.setBackground(new Color(255, 255, 255));
                            lblMessagge.setText("Se realizo la devolucion de manera exitosa.");
                            lblMessagge.setForeground(new Color( 33, 33,255));
                        }else if(RptaSql == -1){
                            //CAJA CERRADA
                            FarmaUtility.liberarTransaccion();
                            log.info("La caja no esta aperturada.");
                            lblF11.setEnabled(false);
                            lblF12.setEnabled(false);
                            lblMessagge.setText("Caja no aperturada.");
                            txtDevolutionImport.setEditable(true);
                            txtDevolutionImport.setBackground(new Color(255, 255, 255));
                        }else{
                            // OTRO ERRRO
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(this, "Error inesparado, reintente.\n"+
                                                           "Si persiste comuniquese con Mesa de Ayuda.", txtDevolutionImport);
                            lblF11.setEnabled(false);
                            lblF12.setEnabled(false);
                            lblMessagge.setText("");
                            txtDevolutionImport.setEditable(true);
                            txtDevolutionImport.setBackground(new Color(255, 255, 255));
                        }
            }else if (e.getKeyCode() == KeyEvent.VK_F12 && lblF12.isEnabled()) { 
                lblF11.setEnabled(false); 
                lblF12.setEnabled(false); 
                txtDevolutionImport.setEditable(true);
                FarmaUtility.moveFocus(txtDevolutionImport);
                txtDevolutionImport.setBackground(new Color(255, 255, 255));
                lblMessagge.setText("Presione [Enter] para validar.");
                lblMessagge.setForeground(new Color( 33, 33,255));  
                
            }
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    public String validaCodigoDevolution (String sNumPedidoVenta){
       
        String sExcecSql="";
        try {
            
            sExcecSql=DBMayorista.getValidaCodigoDevImport(sNumPedidoVenta);  
        } catch (SQLException sql) {
            FarmaUtility.showMessage(this, "Ocurrió un error al consultar el codigo de devolucion." +
                    sql.getMessage(), this); 
            log.info("Ocurrió un error al consultar el codigo de devolucion.");
            sExcecSql="N";
            
        }

       return sExcecSql; 
    }
    
    public int realizaDevolutionImporte (String sNumPedidoVenta){
        try {
            return DBMayorista.transacDevolucionImporte(sNumPedidoVenta);  
        } catch (Exception e) {
            log.error("", e);
            return 0;
        }
    }
    
    
    public static String getCurrentTimeStamp() {
        //SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        System.err.println(strDate);
        return strDate;
    }


    private void txtDevolutionImport_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtDevolutionImport);
    }
}
