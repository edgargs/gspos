package mifarma.common;

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


import java.awt.GridLayout;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaCambioClave;
import mifarma.ptoventa.reference.BeanValidaUsuario;
import mifarma.ptoventa.reference.BeanVariables;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgLogin.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      27.12.2005   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgLogin extends JDialog {

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
    private JPanel pnlAbajo = new JPanel();
    private JPanel pnlArriba = new JPanel();
    private JLabel lblMensajeUsuario = new JLabel();
    private JEditorPane jepMensaje = new JEditorPane();
    private String mensajeVendedor = "";
    
    //JMIRANDA 07/08/09 
    private JPanel jPanel2 = new JPanel();
    //Declaro las variables que utilizo para Setear tamaño
    private int vHeightLogin = 0;
    private int vHeightPanel1 = 0;
    private JPanel jPanel3 = new JPanel();

    JEditorPane jEditorPaneAba = new JEditorPane();
    // **************************************************************************
    // Constructores
    // **************************************************************************
    String pMsjCabecera = "";
    public DlgLogin() {
        this(null, "", false);
        
    
    }

    Frame myParentFrame;
    private JButtonLabel lblUsuario = new JButtonLabel();

    public DlgLogin(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DlgLogin(Frame parent, String title, boolean modal,String pMensaje) {
        super(parent, title, modal);
        myParentFrame = parent;
        pMsjCabecera = pMensaje;
        try {
            jbInit();
            initialize();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        
        if(pMsjCabecera.trim().length()>0){
        //321 545
            this.setSize(new Dimension(554, 321)); //TAMAÑO NORMAL
            //this.setSize(new Dimension(369, 428));
            this.getContentPane().setLayout(null);
            this.setFont(new Font("Arial", 0, 12));
            pnlArriba.setBackground(Color.blue);
            pnlArriba.setSize(new Dimension(364, 353));
            pnlArriba.setBounds(new Rectangle(0, 0, 545, 170));
            jEditorPaneAba.setContentType("text/html");
            //jEditorPaneAba.setText(htmlAbajo);
            jEditorPaneAba.setEditable(false);
            jEditorPaneAba.setText("");
            jEditorPaneAba.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 9));        
            pnlArriba.setLayout(new GridLayout(0, 1));
            pnlArriba.add(jEditorPaneAba);

            pnlAbajo.setSize(new Dimension(0, 0));
            pnlAbajo.setBounds(new Rectangle(0, 120, 745, 170));
            pnlAbajo.setLayout(null);
            pnlAbajo.setBackground(Color.white);
            //jPanel2.setSize(new Dimension(364, 165));
            jPanel2.setBackground(Color.white);
            jPanel2.setLayout(null);
            jPanel2.setSize(new Dimension(800, 165));
            //jPanel2.setBounds(new Rectangle(110, -5, 305, 165));
            jPanel2.setBounds(new Rectangle(100, 0, 364, 165));
            
            
            this.add(pnlArriba);
            this.add(pnlAbajo); 
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
        else{
            this.setSize(new Dimension(337, 216)); //TAMAÑO NORMAL
            this.getContentPane().setLayout(null);
            this.setFont(new Font("Arial", 0, 12));
            //this.setTitle("Acceso al Punto de Ventas");
            this.setContentPane(pnlAbajo);
            pnlAbajo.setBounds(new Rectangle(0, 0, 545, 355));
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            pnlAbajo.setLayout(null);
            pnlAbajo.setBackground(Color.white);
            //pnlAbajo.setSize(new Dimension(364, 353));
            jPanel2.setSize(new Dimension(325, 165));
            jPanel2.setBackground(Color.white);
            jPanel2.setLayout(null);
            //jPanel2.setBounds(new Rectangle(110, -5, 305, 165));
            jPanel2.setBounds(new Rectangle(0, 0, 305, 165));
        }
        
        
        this.setForeground(SystemColor.window);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        txtUsuario.setBounds(new Rectangle(145, 60, 120, 20));
        txtUsuario.setBackground(new Color(255, 130, 14));
        txtUsuario.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtUsuario_keyPressed(e);
                    }
                });
        txtClave.setText("pwdClave");
        txtClave.setBounds(new Rectangle(145, 90, 120, 20));
        txtClave.setBackground(new Color(255, 130, 14));
        txtClave.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtClave_keyPressed(e);
                    }
                });
        btnAceptar.setText("Aceptar");
        btnAceptar.setBounds(new Rectangle(60, 130, 90, 25));
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
        btnCancelar.setBounds(new Rectangle(175, 130, 90, 25));
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
        lblClave.setBounds(new Rectangle(65, 95, 70, 15));
        lblClave.setFont(new Font("Arial", 1, 12));
        lblClave.setForeground(new Color(250, 130, 14));
        
        
        lblUsuario.setText("Usuario :");
        lblUsuario.setBounds(new Rectangle(65, 65, 55, 15));
        lblUsuario.setForeground(new Color(250, 130, 14));
        lblUsuario.setMnemonic('u');
        lblUsuario.setFont(new Font("SansSerif", 1, 12));
        lblUsuario.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblUsuario_actionPerformed(e);
                    }
                });
        jPanel3.setBounds(new Rectangle(0, 0, 10, 10));
        jepMensaje.setBounds(new Rectangle(5, 170, 355, 180));
        lblMensajeUsuario.setText("");
        lblMensajeUsuario.setBounds(new Rectangle(25, 20, 280, 25));
        lblMensajeUsuario.setFont(new Font("SansSerif", 1, 12));
        lblMensajeUsuario.setForeground(new Color(33, 138, 21));
        
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
        


        pnlAbajo.add(jPanel3, null);

        //JMIRANDA 07/08/09
        pnlAbajo.add(jPanel2, null);
        pnlAbajo.add(jepMensaje, null);
        jPanel2.add(btnCancelar, null);
        jPanel2.add(lblMensajeUsuario, null);
        jPanel2.add(lblUsuario, null);
        jPanel2.add(lblClave, null);
        jPanel2.add(btnAceptar, null);
        jPanel2.add(txtClave, null);
        jPanel2.add(txtUsuario, null);
        jepMensaje.setEditable(false);
        jepMensaje.setBounds(new Rectangle(10, 175, 325, 30));


    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        cargaMsjCabecera();
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
            } else if (vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.ERROR_VERSION_NO_VALIDA)) {
                JOptionPane.showMessageDialog(this, 
                                              "La versión de Farmaventa no está actualizada. Salga y vuelva a ingresar al programa. \n" +
                                                "Si persiste el mensaje, comuníquese con Mesa de Ayuda.", 
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
        
        // dubilluz 13.01.2015
        ingresaAsistencia(vSecurityLogin.getLoginSequential());
        // dubilluz 13.01.2015        
        
        FarmaVariables.vNuSecUsu = vSecurityLogin.getLoginSequential();
        FarmaVariables.vIdUsu = vSecurityLogin.getLoginCode();
        FarmaVariables.vNomUsu = vSecurityLogin.getLoginNombre();
        FarmaVariables.vPatUsu = vSecurityLogin.getLoginPaterno();
        FarmaVariables.vMatUsu = vSecurityLogin.getLoginMaterno();

        if (FarmaVariables.vEconoFar_Matriz) {
            FarmaVariables.vCodUsuMatriz = FarmaVariables.vNuSecUsu;
            FarmaVariables.vClaveMatriz = clave;
        }
            
                    /*
                if ( verificaRol(EckerdConstants.ROL_CAJERO) ||
                     ( verificaRol(EckerdConstants.ROL_VENDEDOR) &&
                       EckerdVariables.vTipoCaja.equalsIgnoreCase(ConstantsCaja.TIPO_CAJA_MULTIFUNCIONAL) ) ) {
                  if ( loadCajaPago() )  closeWindow(true);
                } else
                  closeWindow(true);
                //
                */ 
        
         //CHUANES 26.02.2015
         //LEVANTA LA VENTANA DE CAMBIO DE CLAVE SI ESTA VENCIDO SU
         if(BeanVariables.vAccion.equalsIgnoreCase("")){
             validaFecVenc();
         }
        
        
        cerrarVentana(true);
        
    }
    
    void ingresaAsistencia(String pSecUsu){
        if(vDebeMarcarAsistencia(pSecUsu))
        {
           FarmaControlIngreso dlgControlIngreso = new FarmaControlIngreso(myParentFrame, "", true);
           dlgControlIngreso.setVisible(true);
        }
        if(vDebeMarcarAsistencia(pSecUsu))
            ingresaAsistencia(pSecUsu);
    }
    
    
    public boolean vDebeMarcarAsistencia(String vSecUsu) {
        try {
            ArrayList parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(vSecUsu);
            String pValor =
                FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_SECURITY.VALIDA_MARCA_ASISTENCIA(?,?,?)",
                                                            parametros);
            if (pValor.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                return true;
            else
                return false;
        } catch (Exception sqle) {
            // TODO: Add catch code
            return false;
        }
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
        System.err.println("Tamaño:"+mensajeVendedor.trim().length());
        
        /*if(mensajeVendedor.trim().length()==0||(mensajeVendedor==null))
        {                   
          this.setSize(new Dimension(337, 216)); //TAMAÑO NORMAL          
        }*/
      
        
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
            System.err.println("get mensajeVendedor;"+mensajeVendedor);
            if(mensajeVendedor==null)
                mensajeVendedor = "";
            //txtMensaje.set
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "";
            mensajeVendedor  = "";
            System.err.println("Entro al catch");
        }
        lblMensajeUsuario.setText(mensaje);
        if(mensajeVendedor!=null){
        jepMensaje.setContentType("text/html");
        jepMensaje.setText(mensajeVendedor);
        }
        System.err.println("mensajeVendedor;"+mensajeVendedor);
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
        vParameters.add(FarmaVariables.vCodCia);
        vParameters.add(FarmaVariables.vCodLocal);
        System.out.println("Farma_Gral.GET_MSG_ROL_LOGIN(?,?,?,?)"+vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.GET_MSG_ROL_LOGIN(?,?,?,?)", 
                                                           vParameters);
    }

    private String getMsgRoleEspecial(String pCodRol) throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(pCodRol.trim());
        System.out.println("Obtiene mensaje a colocar LOGIn:PTOVENTA_MENSAJES.F_VAR2_GET_MENSAJE(?,?)+" + vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MENSAJES.F_VAR2_GET_MENSAJE(?,?)", 
                                                           vParameters);
    }    

    /** 
     * Redimensiona el Tamaño del DlgLogin
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
        System.out.println("Donde <b>: "+vMensaje.indexOf("<b>"));
        System.out.println("Donde </b>: "+vMensaje.indexOf("</b>"));
        
        String mMens = vMensaje.substring(vMensaje.indexOf("<b>")+3,vMensaje.indexOf("</b>"));
        if (mMens.length()%28 > 0){
            vFilasM = mMens.length()/28;
            vFilasM += 3;
        }else{
            vFilasM = mMens.length()/28;
            vFilasM += 2;
        }
        System.out.println("FILAS:"+vFilasM);
        //Dimensión del JEditorPane jepMensaje
        jepMensaje.setSize(355,26*vFilasM);
                
        //seteo variable Panel 1
        vHeightPanel1 = (165+26*vFilasM)+50;
        //seteo variable de la Altura LOGIN
        vHeightLogin = (165+(26*vFilasM)+45);
        
        pnlAbajo.setSize(305,vHeightPanel1);
        System.out.println("vHeightLogin: "+ vHeightLogin);
        System.out.println("jp1: "+ pnlAbajo.getSize().getHeight());
        System.out.println("jepMensaje: "+ jepMensaje.getSize().getHeight());
        
        System.out.println("Mensaje: "+mMens);    
        System.out.println("Total Carac: "+mMens.length());
                
    }
    /**
     * CHUANES
     * 26.02.2015
     * LEVANTA LA VENTANA DE CAMBIO DE CLAVE SI ESTA ESTA  CADUCADA.
     * */
    
    public void validaFecVenc(){
            String valida = "";
            String numDiasVenc="";
            String numSecUsu=FarmaVariables.vNuSecUsu;
            String usuDebeCambiarClave="";//CHUANES 11.03.2015
            String recCambioClave="";//CHUANES 12.03.2015  
         try {
             usuDebeCambiarClave=BeanValidaUsuario.usuDebeCambiarClave().trim();
             valida =BeanValidaUsuario.validaCambioClave().trim();
             numDiasVenc=BeanValidaUsuario.recFecVenClave(numSecUsu).trim();
             recCambioClave=BeanValidaUsuario.recodCambioClave().trim();
             
         } catch (SQLException x) {
           // log.error("", x);
         } 
        //CHUANES 11.03.2015 
        if(usuDebeCambiarClave.equalsIgnoreCase("S")){
            if(!recCambioClave.equalsIgnoreCase("N")){//CHUANES 12.03.2015 SOLO DEBE RECORDAR UNA VEZ AL DIA EL VENCIMIENTO DE LA CLAVE
            if(!numDiasVenc.equalsIgnoreCase("N")){
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Estimado usuario le falta "+numDiasVenc+" dia(s) para que expire su clave\n"+
                "¿Desea cambiarla ahora?")) {
                FarmaCambioClave dlgcambio = new FarmaCambioClave(myParentFrame, "", true);
                dlgcambio.setVisible(true);
                
                if (FarmaVariables.vAceptar) {
                FarmaVariables.dlgLogin = this;
                // recuperaStock();
                } else {
                // this.clearData();
                //numSecUsu="";
                //salirSistema();
                //System.exit(0);no debe salir del sistema porque esta vigente
                }
             }
            }
            }

         // log.info("cambiar password :" + valida);
         if (valida.trim().equalsIgnoreCase("S")) {


             FarmaUtility.showMessage(this, "Usted debera cambiar su clave para poder entrar el sistema.",
                                      null);
             FarmaCambioClave dlgcambio = new FarmaCambioClave(myParentFrame, "", true);
             dlgcambio.setVisible(true);

             if (FarmaVariables.vAceptar) {
                 FarmaVariables.dlgLogin = this;
                
             } else {
                this.clearData();
                 numSecUsu="";
              
                System.exit(0);
             }

         } /*else {CHUANES 30/04/2015
             FarmaVariables.dlgLogin = this;
           
         }  */
        }
    }



    private void cargaMsjCabecera() {
        jEditorPaneAba.setText(pMsjCabecera);
    }

}
