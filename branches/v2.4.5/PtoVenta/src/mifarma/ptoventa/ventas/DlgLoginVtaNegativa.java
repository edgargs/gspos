package mifarma.ptoventa.ventas;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaSecurity;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import com.gs.mifarma.componentes.*;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgLoginVtaNegativa.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      27.12.2005   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgLoginVtaNegativa extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgLoginVtaNegativa.class);
    
    private String vRolUsuario = new String("");
    FarmaSecurity vSecurityLogin;
    int parametro = -1;
    JLabel lblParametro;

    //ImageIcon imageIcono = new ImageIcon(FrmInkVenta.class.getResource("./images/acceso.gif"));
    JTextField txtUsuario = new JTextField();
    JPasswordField txtClave = new JPasswordField();
    JButton btnAceptar = new JButton();
    JButton btnCancelar = new JButton();
    JLabel lblClave = new JLabel();
    private JPanel jPanel1 = new JPanel();
    private JEditorPane jepMensaje = new JEditorPane();
    private String mensajeVendedor = "";
    
    //JMIRANDA 07/08/09 
    private JPanel jPanel2 = new JPanel();
    //Declaro las variables que utilizo para Setear tamaño
    private int vHeightLogin = 0;
    private int vHeightPanel1 = 0;
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();


    // **************************************************************************
    // Constructores
    // **************************************************************************

    private String pCodProdDlg;
    private String pDescripcionProdDlg;
    private int    pCantVecesDlg=0;
    private int    pcantVecesCarga = 0;

    Frame myParentFrame;
    private JButtonLabel lblUsuario = new JButtonLabel();

    public DlgLoginVtaNegativa(Frame parent, String title, boolean modal    ,
                               String pCodProd,
                                  String pDescripcionProd,
                                  int    pCantVeces) {
        super(parent, title, modal);
        myParentFrame = parent;
        pCodProdDlg = pCodProd;
        pDescripcionProdDlg = pDescripcionProd;
        pCantVecesDlg = pCantVeces;        
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(337, 216)); //TAMAÑO NORMAL
        //this.setSize(new Dimension(369, 428));
        this.getContentPane().setLayout(null);
        this.setFont(new Font("Arial", 0, 12));
        //this.setTitle("Acceso al Punto de Ventas");
        this.setContentPane(jPanel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        txtUsuario.setBounds(new Rectangle(110, 80, 120, 20));
        txtUsuario.setBackground(new Color(255, 130, 14));
        txtUsuario.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtUsuario_keyPressed(e);
                    }
                });
        txtClave.setText("pwdClave");
        txtClave.setBounds(new Rectangle(110, 110, 120, 20));
        txtClave.setBackground(new Color(255, 130, 14));
        txtClave.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtClave_keyPressed(e);
                    }
                });
        btnAceptar.setText("Aceptar");
        btnAceptar.setBounds(new Rectangle(35, 140, 80, 20));
        btnAceptar.setMnemonic('A');
        btnAceptar.setFont(new Font("Arial", 0, 12));
        btnAceptar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        btnAceptar.setRequestFocusEnabled(false);
        btnAceptar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnAceptar_actionPerformed(e);
                    }
                });
        btnCancelar.setText("Cancelar");
        btnCancelar.setBounds(new Rectangle(135, 140, 80, 20));
        btnCancelar.setMnemonic('C');
        btnCancelar.setFont(new Font("Arial", 0, 12));
        btnCancelar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        btnCancelar.setRequestFocusEnabled(false);
        btnCancelar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnCancelar_actionPerformed(e);
                    }
                });
        lblClave.setText("Clave :");
        lblClave.setBounds(new Rectangle(30, 115, 70, 15));
        lblClave.setFont(new Font("Arial", 1, 12));
        lblClave.setForeground(new Color(250, 130, 14));
        jPanel1.setBounds(new Rectangle(5, 10, 325, 140));
        jPanel1.setLayout(null);
        jPanel1.setBackground(Color.white);
        jPanel1.setSize(new Dimension(364, 353));
        lblUsuario.setText("Usuario :");
        lblUsuario.setBounds(new Rectangle(30, 85, 55, 15));
        lblUsuario.setForeground(new Color(250, 130, 14));
        lblUsuario.setMnemonic('u');
        lblUsuario.setFont(new Font("SansSerif", 1, 12));
        lblUsuario.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblUsuario_actionPerformed(e);
                    }
                });
        jLabel5.setText("0/"+pCantVecesDlg);
        jLabel5.setBounds(new Rectangle(240, 140, 65, 20));
        jLabel5.setFont(new Font("Tahoma", 1, 11));
        jLabel5.setForeground(new Color(198, 0, 0));
        jLabel5.setVisible(false);
        jLabel4.setText(pCantVecesDlg+" Unidades de "+pDescripcionProdDlg);
        jLabel4.setBounds(new Rectangle(20, 50, 290, 25));
        jLabel4.setFont(new Font("Tahoma", 1, 12));
        jLabel4.setForeground(new Color(198, 0, 0));
        jLabel3.setText("Autoriza la venta en Negativo del Producto");
        jLabel3.setBounds(new Rectangle(10, 25, 280, 15));
        jLabel3.setFont(new Font("Tahoma", 1, 11));
        jLabel1.setText("jLabel1");
        jLabel1.setBounds(new Rectangle(105, 60, 34, 14));
        jLabel2.setText("Jefe de Local ");
        jLabel2.setBounds(new Rectangle(10, 5, 265, 20));
        jLabel2.setFont(new Font("Tahoma", 1, 11));
        jepMensaje.setBounds(new Rectangle(5, 170, 355, 180));

        /*  jPanel1.add(jepMensaje, null);
        jPanel1.add(lblMensajeUsuario, null);
        jPanel1.add(lblUsuario, null);
        jPanel1.add(lblClave, null);
        jPanel1.add(btnCancelar, null);
        jPanel1.add(btnAceptar, null);
        jPanel1.add(txtClave, null);
        jPanel1.add(txtUsuario, null);
      */
        //this.getContentPane().add(jPanel1, null);
        
        //JMIRANDA 07/08/09
        jPanel2.setSize(new Dimension(325, 165));
        jPanel2.setBackground(Color.white);
        jPanel2.setLayout(null);
        jPanel2.setBounds(new Rectangle(0, 0, 315, 170));


        jPanel2.add(jLabel5, null);
        jPanel2.add(jLabel4, null);
        jPanel2.add(jLabel3, null);
        jPanel2.add(jLabel2, null);

        //JMIRANDA 07/08/09
        jPanel2.add(btnCancelar, null);
        jPanel2.add(lblUsuario, null);
        jPanel2.add(lblClave, null);
        jPanel2.add(btnAceptar, null);
        jPanel2.add(txtClave, null);
        jPanel2.add(txtUsuario, null);
        jepMensaje.setEditable(false);
        jepMensaje.setBounds(new Rectangle(10, 175, 325, 30));

        jPanel1.add(jPanel2, null);
        jPanel1.add(jepMensaje, null);
        this.getContentPane().add(jLabel1, null);


    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        txtUsuario.setDocument(new FarmaLengthText(15));
        txtClave.setDocument(new FarmaLengthText(30));
        FarmaVariables.vAceptar = false;
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************


    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtUsuario_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            boolean afirma;
            afirma = FarmaUtility.isInteger(txtUsuario.getText().trim());
            if (afirma) {
                txtUsuario.setText(FarmaUtility.caracterIzquierda(txtUsuario.getText().trim(), 
                                                                  3, "0"));
            } else
                txtUsuario.setText(txtUsuario.getText().trim().toUpperCase());

            txtClave.setText("");
            FarmaUtility.moveFocus(txtClave);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
            /*
      if(parametro == 1)
      {
        lblParametro.setText("0");
        cerrarVentana(true);
      } else if(parametro == 0) System.exit(0);
*/
        }
    }

    private void txtClave_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnAceptar.doClick();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void btnAceptar_actionPerformed(ActionEvent e) {
        boolean accesoCorrecto = false;
        String usuario = txtUsuario.getText().trim();
        //    String clave = txtClave.getText().trim();
        String clave = (new String(txtClave.getPassword())).trim();
        if ((usuario.trim().length() == 0) || (clave.trim().length() == 0)) {
            JOptionPane.showMessageDialog(this, 
                                          "Datos invalidos. Verifique !!!", 
                                          "Mensaje del Sistema", 
                                          JOptionPane.WARNING_MESSAGE);
            clearData();
            return;
        }
        vSecurityLogin = new FarmaSecurity(usuario, clave);
        if (!vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.LOGIN_USUARIO_OK)) {
            if (vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.LOGIN_USUARIO_INACTIVO)) {
                JOptionPane.showMessageDialog(this, 
                                              "El usuario se encuentra Inactivo !!!", 
                                              "Mensaje del Sistema", 
                                              JOptionPane.WARNING_MESSAGE);
            } else if (vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.LOGIN_NO_REGISTRADO_LOCAL)) {
                JOptionPane.showMessageDialog(this, 
                                              "El usuario no se encuentra registrado en el Local !!!", 
                                              "Mensaje del Sistema", 
                                              JOptionPane.WARNING_MESSAGE);
            } else if (vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.LOGIN_CLAVE_ERRADA)) {
                JOptionPane.showMessageDialog(this, 
                                              "La clave ingresada no es correcta !!!", 
                                              "Mensaje del Sistema", 
                                              JOptionPane.WARNING_MESSAGE);
            } else if (vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.LOGIN_USUARIO_NO_EXISTE)) {
                JOptionPane.showMessageDialog(this, 
                                              "El usuario ingresado no se encuentra registrado !!!", 
                                              "Mensaje del Sistema", 
                                              JOptionPane.WARNING_MESSAGE);
            } else if (vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.ERROR_CONEXION_BD)) {
                JOptionPane.showMessageDialog(this, 
                                              "Error de conexión a la Base de Datos !!!", 
                                              "Mensaje del Sistema", 
                                              JOptionPane.WARNING_MESSAGE);
            }
            clearData();
            return;
        }
        if (!vRolUsuario.equalsIgnoreCase("")) {
            if (!vSecurityLogin.haveRole(vRolUsuario)) {
                JOptionPane.showMessageDialog(this, 
                                              "El usuario no tiene asignado el rol adecuado!!!", 
                                              "Mensaje del Sistema", 
                                              JOptionPane.WARNING_MESSAGE);
                clearData();
                return;
            }
        }
        FarmaVariables.vNuSecUsu = vSecurityLogin.getLoginSequential();
        FarmaVariables.vIdUsu = vSecurityLogin.getLoginCode();
        FarmaVariables.vNomUsu = vSecurityLogin.getLoginNombre();
        FarmaVariables.vPatUsu = vSecurityLogin.getLoginPaterno();
        FarmaVariables.vMatUsu = vSecurityLogin.getLoginMaterno();

        if (FarmaVariables.vEconoFar_Matriz) {
            FarmaVariables.vCodUsuMatriz = FarmaVariables.vNuSecUsu;
            FarmaVariables.vClaveMatriz = clave;
        }
        
        //if(pcantVecesCarga==pCantVecesDlg)
            cerrarVentana(true);
        /*
        else{
            pcantVecesCarga++;
            jLabel5.setText(pcantVecesCarga+"/"+pCantVecesDlg);
            txtUsuario.setText("");
            txtClave.setText("");
            FarmaUtility.moveFocus(txtUsuario);
            if(pcantVecesCarga==pCantVecesDlg)
                cerrarVentana(true);
        }*/
    }

    void btnCancelar_actionPerformed(ActionEvent e) {
        cerrarVentana(false);
    }

    private void this_windowOpened(WindowEvent e) {

               
        FarmaUtility.centrarVentana(this);
        txtUsuario.setText(FarmaVariables.vIdUsu.trim());
        FarmaUtility.moveFocus(txtUsuario);
        if (FarmaVariables.vEconoFar_Matriz && 
            FarmaVariables.vClaveMatriz.trim().length() > 0) {
            txtUsuario.setText(FarmaVariables.vCodUsuMatriz);
            txtClave.setText(FarmaVariables.vClaveMatriz);
            btnAceptar.doClick();
        }
        log.debug("Tamaño:"+mensajeVendedor.trim().length());
        if(mensajeVendedor.trim().length()==0||(mensajeVendedor==null))
        {                   
          this.setSize(new Dimension(337, 216)); //TAMAÑO NORMAL          
        }
        
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    void clearData() {
        txtUsuario.selectAll();
        txtClave.setText("");
        FarmaUtility.moveFocus(txtUsuario);
    }

    public void setRolUsuario(String pRolUsuario) {
        vRolUsuario = pRolUsuario;
        setMensajeUsuario(vRolUsuario);
    }

    /** Devuelve el código de Logian del Usuario Logeado
     * Ejemplo: "RCASTRO"
     */
    public String getCodigoLogin() {
        return vSecurityLogin.getLoginCode();
    }

    /** Devuelve el nombre el Usuario Logeado
     * Ejemplo: "CASTRO MORAN, Rolando"
     */
    public String getNombreLogin() {
        return vSecurityLogin.getLoginUsuario();
    }

    /** Devuelve el secuencial de la tabla usuario (Primery key Tabla Usuario)
     * Ejemplo: "001"
     */
    public String getSecuencialUsuario() {
        return vSecurityLogin.getLoginSequential();
    }

    public boolean verificaRol(String pCodigoRol) {
        return vSecurityLogin.haveRole(pCodigoRol);
    }

    private void lblUsuario_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtUsuario);
    }

    private void setMensajeUsuario(String pCodRol) {
        String mensaje = "";//,mensajeVendedor = "";
        try {
            mensaje = getMsgRol(pCodRol);
            mensajeVendedor = getMsgRoleEspecial(pCodRol);
            log.debug("get mensajeVendedor;"+mensajeVendedor);
            if(mensajeVendedor==null)
                mensajeVendedor = "";
            //txtMensaje.set
        } catch (Exception e) {
            log.error("",e);
            mensaje = "";
            mensajeVendedor  = "";
            log.debug("Entro al catch");
        }
        //lblMensajeUsuario.setText(mensaje);
        if(mensajeVendedor!=null){
        jepMensaje.setContentType("text/html");
        jepMensaje.setText(mensajeVendedor);
        }
        log.debug("mensajeVendedor;"+mensajeVendedor);
        if(mensajeVendedor.trim().length()>0) {
            //this.setSize(new Dimension(369, 428));
            //JMIRANDA 07/08/09 se Calcula El Tamaño del Login
            calculoTamanoLogin(mensajeVendedor);
            this.setSize(new Dimension(337, vHeightLogin));            
        }
    }

    private String getMsgRol(String pCodRol) throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(pCodRol.trim());
        // log.debug("Obtiene mensaje a colocar LOGIn " + vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.GET_MSG_ROL_LOGIN(?,?)", 
                                                           vParameters);
    }

    private String getMsgRoleEspecial(String pCodRol) throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(pCodRol.trim());
        log.debug("Obtiene mensaje a colocar LOGIn:PTOVENTA_MENSAJES.F_VAR2_GET_MENSAJE(?,?)+" + vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MENSAJES.F_VAR2_GET_MENSAJE(?,?)", 
                                                           vParameters);
    }    

    /** 
     * Redimensiona el Tamaño del DlgLoginVtaNegativa
     * JMIRANDA 07/08/09
     * @param vMensaje 
     *   Recibe el Html de Base de Datos, tipo String
     * */
    private void calculoTamanoLogin(String vMensaje){
        if (vMensaje.equalsIgnoreCase("")){ 
            vHeightLogin = 0;
            vHeightPanel1 = 0;
            return;
        }
        
        int vFilasM = 0;
        log.debug("Donde <b>: "+vMensaje.indexOf("<b>"));
        log.debug("Donde </b>: "+vMensaje.indexOf("</b>"));
        
        String mMens = vMensaje.substring(vMensaje.indexOf("<b>")+3,vMensaje.indexOf("</b>"));
        if (mMens.length()%28 > 0){
            vFilasM = mMens.length()/28;
            vFilasM += 3;
        }else{
            vFilasM = mMens.length()/28;
            vFilasM += 2;
        }
        log.debug("FILAS:"+vFilasM);
        //Dimensión del JEditorPane jepMensaje
        jepMensaje.setSize(355,26*vFilasM);
                
        //seteo variable Panel 1
        vHeightPanel1 = (165+26*vFilasM)+50;
        //seteo variable de la Altura LOGIN
        vHeightLogin = (165+(26*vFilasM)+45);
        
        jPanel1.setSize(305,vHeightPanel1);
        log.debug("vHeightLogin: "+ vHeightLogin);
        log.debug("jp1: "+ jPanel1.getSize().getHeight());
        log.debug("jepMensaje: "+ jepMensaje.getSize().getHeight());
        
        log.debug("Mensaje: "+mMens);    
        log.debug("Total Carac: "+mMens.length());
    }
}