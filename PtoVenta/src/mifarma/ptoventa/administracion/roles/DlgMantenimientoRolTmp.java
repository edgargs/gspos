package mifarma.ptoventa.administracion.roles;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import javax.swing.JFrame;

import javax.swing.JRadioButton;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.roles.reference.ConstantsRolesTmp;
import mifarma.ptoventa.administracion.roles.reference.DBRolesTmp;
import mifarma.ptoventa.administracion.roles.reference.VariablesRolesTmp;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.cliente.DlgMantClienteNatural;

import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.cliente.reference.DBCliente;


import mifarma.ptoventa.cliente.reference.VariablesCliente;

import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


 /**
  * Copyright (c) 2015 MIFARMA S.A.C.<br>
  * <br>
  * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
  * Nombre de la Aplicación : DlgMantenimientoRolTmp.java<br>
  * <br>
  * Histórico de Creación/_fModificación<br>
  * CESAR     25.02.2015   Creación<br>
  * <br>
  * @author Cesar Huanes<br>
  * @version 1.0<br>
  *
  */
public class DlgMantenimientoRolTmp extends JDialog  {
    private static final Logger log = LoggerFactory.getLogger(DlgMantenimientoRolTmp.class);
    @SuppressWarnings("compatibility:398606826956210138")
    private static final long serialVersionUID = 1L;
    Frame myParentFrame;

    FarmaTableModel tableModel;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelHeader pnlCodigoLaboratorio = new JPanelHeader();
    private JTextFieldSanSerif txtCodigo = new JTextFieldSanSerif();
    private JLabelWhite lblCodigoLaboratorio_T = new JLabelWhite();
    private JPanelTitle pnlDatosLaboratorio = new JPanelTitle();
    private JButtonLabel btnDireccion = new JButtonLabel();
   
    private JButtonLabel btnEstado = new JButtonLabel();
    private JButtonLabel btnNombre = new JButtonLabel();
    private JButtonLabel btnApePat = new JButtonLabel();
    private JButtonLabel btnApeMat = new JButtonLabel();
    private JTextFieldSanSerif txtUsuario = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFecInicio = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFecFin = new JTextFieldSanSerif();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
 
   

    private String secuencial="";

    public DlgMantenimientoRolTmp() {
        this(null, " ", false);
    }

    public DlgMantenimientoRolTmp(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }
    
    private void initialize() {
        FarmaVariables.vAceptar = false;
    }
    private void jbInit() throws Exception {
        this.setSize(new Dimension(461, 260));
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Mantenimiento de Roles Temporales");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelWhite1.setLayout(null);
        jPanelWhite1.setBounds(new Rectangle(0, 0, 455, 275));
        pnlCodigoLaboratorio.setBounds(new Rectangle(5, 5, 450, 30));
        pnlCodigoLaboratorio.setSize(new Dimension(435, 30));
        pnlCodigoLaboratorio.setBackground(new Color(43, 141, 39));
        pnlCodigoLaboratorio.setLayout(null);
        pnlCodigoLaboratorio.setFont(new Font("SansSerif", 0, 11));
     
        
        lblCodigoLaboratorio_T.setText("Ingrese Dni : ");
        lblCodigoLaboratorio_T.setBounds(new Rectangle(5, 5, 100, 20));
        lblCodigoLaboratorio_T.setForeground(Color.white);
        pnlDatosLaboratorio.setBounds(new Rectangle(5, 45, 435, 130));
        pnlDatosLaboratorio.setBackground(Color.white);
        pnlDatosLaboratorio.setFont(new Font("SansSerif", 0, 11));
        pnlDatosLaboratorio.setLayout(null);
        pnlDatosLaboratorio.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
       
      
       
        btnNombre.setText("Usuario : ");
        btnNombre.setBounds(new Rectangle(10, 10, 55, 15));
        btnNombre.setForeground(new Color(255, 130, 14));
        btnNombre.setMnemonic('u');
        btnNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNombre_actionPerformed(e);
            }
        });
        btnApePat.setText("Fec. Inicio: ");
        btnApePat.setBounds(new Rectangle(10, 40, 105, 15));
        btnApePat.setForeground(new Color(255, 130, 14));
        btnApePat.setMnemonic('o');
        btnApePat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               btnApellidoPaterno_actionPerformed(e);
            }
        });
        btnApeMat.setText("Fec Fin: ");
        btnApeMat.setBounds(new Rectangle(10, 75, 105, 15));
        btnApeMat.setForeground(new Color(255, 130, 14));
        btnApeMat.setMnemonic('n');
        btnApeMat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnApellidoMaterno_actionPerformed(e);
            }
        });
        txtCodigo.setBounds(new Rectangle(115, 5, 100, 20));
        txtCodigo.setFont(new Font("SansSerif", 0, 11));
        txtCodigo.setEnabled(false);
        txtCodigo.setDocument(new FarmaLengthText(8));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCodigo_keyPressed(e);
            }
        });
        txtUsuario.setBounds(new Rectangle(115, 5, 135, 20));
        txtUsuario.setFont(new Font("SansSerif", 0, 11));
        txtUsuario.setDocument(new FarmaLengthText(30));
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtUsuario_keyPressed(e);
            }
        });
        txtUsuario.setDocument(new FarmaLengthText(50));
        txtUsuario.setEnabled(false);
        txtFecInicio.setBounds(new Rectangle(115, 35, 135, 20));
        txtFecInicio.setFont(new Font("SansSerif", 0, 11));
        txtFecInicio.setDocument(new FarmaLengthText(30));
        txtFecInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecInicio_keyPressed(e);
            }
            public void keyReleased(KeyEvent e) {
                txtFecInicio_keyReleased(e);
            }
        });
        txtFecInicio.setDocument(new FarmaLengthText(10));
        txtFecFin.setBounds(new Rectangle(115, 70, 135, 20));
        txtFecFin.setFont(new Font("SansSerif", 0, 11));
        txtFecFin.setDocument(new FarmaLengthText(30));
        txtFecFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecFin_keyPressed(e);
            }
            public void keyReleased(KeyEvent e) {
                txtFecFin_keyReleased(e);
            }
        });
        txtFecFin.setDocument(new FarmaLengthText(10));
        lblEsc.setText("[ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(355, 200, 85, 20));
        lblEsc.setFont(new Font("SansSerif", 1, 12));
        lblF1.setText("[F11] Aceptar");
        lblF1.setBounds(new Rectangle(100, 200, 85, 20));
        lblF1.setFont(new Font("SansSerif", 1, 12));
       
        pnlDatosLaboratorio.add(txtFecFin, null);
        pnlDatosLaboratorio.add(txtFecInicio, null);
        pnlDatosLaboratorio.add(txtUsuario, null);
        pnlDatosLaboratorio.add(btnApeMat, null);
        pnlDatosLaboratorio.add(btnApePat, null);
        pnlDatosLaboratorio.add(btnNombre, null);
        pnlDatosLaboratorio.add(btnEstado, null);
        pnlDatosLaboratorio.add(btnDireccion, null);
       
        pnlCodigoLaboratorio.add(txtCodigo, null);
        pnlCodigoLaboratorio.add(lblCodigoLaboratorio_T, null);
        jPanelWhite1.add(lblF1, null);
       
        jPanelWhite1.add(lblEsc, null);
        jPanelWhite1.add(pnlDatosLaboratorio, null);
        jPanelWhite1.add(pnlCodigoLaboratorio, null);
        this.getContentPane().add(jPanelWhite1, null);
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);

        if (VariablesRolesTmp.vTipo_Accion.equalsIgnoreCase(ConstantsRolesTmp.ACCION_MODIFICAR)) {
            try {
                getDatosRolTmp();
            } catch (SQLException sql) {
                log.error("", sql);
            }
        }
        if(VariablesRolesTmp.vTipo_Accion.equalsIgnoreCase(ConstantsRolesTmp.ACCION_INSERTAR)){
            
            txtCodigo.setEnabled(true);
            FarmaUtility.moveFocus(txtCodigo); 
        }
        
    }
    
    private void getDatosRolTmp() throws SQLException {
       
         txtCodigo.setText(VariablesRolesTmp.vCodigo);
         txtUsuario.setText(VariablesRolesTmp.vUsuario);
         txtFecInicio.setText(VariablesRolesTmp.vFec_Inicio);
         txtFecFin.setText(VariablesRolesTmp.vFec_Fin);
         txtUsuario.setEnabled(false);
         txtFecInicio.setEnabled(false);
         FarmaUtility.moveFocus(txtFecFin);
       
    }
    

   
   
    private void btnNombre_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtUsuario);
    }

    private void btnApellidoPaterno_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecInicio);
    }

    private void btnApellidoMaterno_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecFin);
    }
   

   
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
        
    }
    private void txtCodigo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!validaDni()){
                FarmaUtility.moveFocus(txtCodigo);    
            return;
            }
            
            if(!buscaDatosUsuario()) {
                FarmaUtility.moveFocus(txtCodigo);
             return;   
            }
            if(!verificaCodigoTrabajador(txtCodigo.getText().toString())){
                FarmaUtility.moveFocus(txtCodigo);    
                return;   
            }
            FarmaUtility.moveFocus(txtFecInicio);
            
        }
        chkKeyPressed(e);
    }
    private void txtUsuario_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //FarmaUtility.moveFocus(txtFecInicio);
            txtUsuario.setText(txtUsuario.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void txtFecInicio_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFecFin);
            txtFecInicio.setText(txtFecInicio.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void txtFecFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            cambiaPosicionCursor();
            txtFecFin.setText(txtFecFin.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }
   
    private void txtFecInicio_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecInicio, e);
    }

    private void txtFecFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecFin, e);
    }
  
    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if(!validaDni()){
                FarmaUtility.moveFocus(txtCodigo);    
            return;
            }
            
            if(!buscaDatosUsuario()) {
                FarmaUtility.moveFocus(txtCodigo);
             return;   
            }
            if(!verificaCodigoTrabajador(txtCodigo.getText().toString())){
                FarmaUtility.moveFocus(txtCodigo);    
                return;   
            }
           
            
            if (!validaDatosClientes())
                return;
             
            String resultado = "";
            if(txtFecInicio.isEnabled()){
                if(!verificaFecInicio())
                    return;
                
            }
            if(!verRangoMaxFechas()){
                return;
            }
            log.debug(VariablesRolesTmp.vTipo_Accion);
            if (VariablesRolesTmp.vTipo_Accion.equalsIgnoreCase(ConstantsRolesTmp.ACCION_INSERTAR)) {
                if(!verificaDuplicidad())
                    return;
                
                if(!verificaCruceFechas())
                    return;
                    
                resultado = registraUsuarioTmp();
                log.debug("resultado = " + resultado);
            } else if (VariablesRolesTmp.vTipo_Accion.equalsIgnoreCase(ConstantsRolesTmp.ACCION_MODIFICAR)) {
                 if(!verificaFecFin())
                     return;
                 if(!verificaCruceFechaUpdate())
                     return;
                resultado = actualizaUsuarioRol();
                log.debug("resultado = " + resultado);
            }
            if (resultado.equalsIgnoreCase(ConstantsRolesTmp.RESULTADO_GRABAR_CLIENTE_EXITO)) {
                FarmaUtility.showMessage(this, "Se grabo el Rol Temporal con Exito", null);
                cerrarVentana(true);
            } else
                FarmaUtility.showMessage(this, "Error al grabar el Rol Temporal", txtUsuario);
        }
    }
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
   
   
    private void cambiaPosicionCursor(){
        if(VariablesRolesTmp.vTipo_Accion.equalsIgnoreCase(ConstantsRolesTmp.ACCION_MODIFICAR)){
            FarmaUtility.moveFocus(txtUsuario); 
        }
        if(VariablesRolesTmp.vTipo_Accion.equalsIgnoreCase(ConstantsRolesTmp.ACCION_INSERTAR)){
            FarmaUtility.moveFocus(txtCodigo); 
        }
    }
    
    private boolean validaDatosClientes() {
        boolean retorno = true;
        if (txtUsuario.getText().equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese Nombre de Usuario", txtUsuario);
            return false;
        }
           else if (txtFecInicio.getText().trim().equals("")) {
               retorno = false;
               FarmaUtility.showMessage(this, "Ingrese Fecha de Inicio.", txtFecInicio);
           } else if (txtFecFin.getText().trim().equals("")) {
               retorno = false;
               FarmaUtility.showMessage(this, "Ingrese Fecha de Fin.", txtFecFin);
           } else if(!FarmaUtility.validaFecha(txtFecInicio.getText(), "dd/MM/yyyy")){
               retorno = false;
               FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha de Inicio", txtFecInicio);
        } else  if (!FarmaUtility.validaFecha(txtFecFin.getText(), "dd/MM/yyyy")) {
               retorno = false;
               FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha Fin", txtFecFin);
        
        } else if (!FarmaUtility.validarRangoFechas(this, txtFecInicio, txtFecFin, false, true, true, true))
                   retorno = false;
            
        
        return retorno;
    }
   
    
    private boolean  buscaDatosUsuario() {
       String dni=txtCodigo.getText().trim();
        ArrayList lstUsuario=new ArrayList();
        try {
            DBRolesTmp.buscaDatosCliente(lstUsuario, dni);
           
            if(lstUsuario.size()==0){
                txtUsuario.setText("");
               FarmaUtility.showMessage(this, "Usuario no registrado en el local", txtFecInicio);    
               return false;
            }
            if(lstUsuario.size()==1){
            String secuencial=((ArrayList)lstUsuario.get(0)).get(0).toString().trim();
            String usuario=((ArrayList)lstUsuario.get(0)).get(1).toString().trim();
            this.setSecuencial(secuencial);
            txtUsuario.setText(usuario);
          
            }
            if(lstUsuario.size()>1){
               
                FarmaUtility.showMessage(this, "Se encontro mas de un usuario con el mismo Dni\n Por favor verifique en Base de Datos ", txtFecInicio);    
            return false;
            }
        } catch (SQLException e) {
            
            FarmaUtility.showMessage(this, "Error a Obtener usuario"+e.getMessage(), txtFecInicio);    
            return false;
        }
        return true;
           
    }
    private boolean validaDni(){
        String dni=txtCodigo.getText().trim();
        if(dni.length()!=8 ){
            txtUsuario.setText("");
            FarmaUtility.showMessage(this, "Debe Ingresar 8 Digitos..!", txtFecInicio); 
            return false;
        }
        if(!this.isNumeric(dni)){
            FarmaUtility.showMessage(this, "El valor Ingresado no es numerico..!", txtFecInicio); 
           return false; 
        }
        return true;
    }
    private static boolean isNumeric(String cadena){
            try {
                    Integer.parseInt(cadena);
                    return true;
            } catch (NumberFormatException nfe){
                    return false;
            }
    }
    private String registraUsuarioTmp(){
    String resultado="FALSE";
    String fecInicio="";
    String fecFin="";
  
    String secuencial="";
     fecInicio=txtFecInicio.getText().trim();
     fecFin=txtFecFin.getText().trim();
     
    
     secuencial=this.getSecuencial();
      String registra="";
        try {
            
            registra = DBRolesTmp.registraUsuarioTmp(secuencial,fecInicio, fecFin).trim();
            System.out.println("Resultado"+registra);
            
            if(registra.equalsIgnoreCase("TRUE")){
            FarmaUtility.aceptarTransaccion(); 
            resultado="TRUE";    
            }
        } catch (SQLException e) {
            resultado="FALSE";
            e.printStackTrace();
        }
        return resultado; 
    }
    private boolean verificaDuplicidad(){
  
        String codigo=txtCodigo.getText().trim();
        String fecInicio=txtFecInicio.getText().trim();
       boolean flag=true;
        try {
            String  resultado = DBRolesTmp.verificaNoDuplicidad(codigo, fecInicio).trim();
            if(resultado.equalsIgnoreCase("TRUE")){
            FarmaUtility.showMessage(this, "Registro duplicado verifique fecha de Inicio!", txtFecInicio);
                flag=false;
            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al verificar duplicidad ..!", txtFecInicio);
            
        }
        return flag;
    }
    private boolean verificaFecInicio(){
       
        String fecInicio=txtFecInicio.getText().trim();
        boolean flag=true;
        try {
            String  resultado = DBRolesTmp.verificaFechaInicio(fecInicio).trim();
            if(resultado.equalsIgnoreCase("TRUE")){
            FarmaUtility.showMessage(this, "Fecha de Inicio no Vigente!", txtFecInicio);
            flag=false;
            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al verificar fecha de Inicio ..!", txtFecInicio);
            
        }
        return flag;
    }
    private boolean verificaFecFin(){
     
        String fecFin= txtFecFin.getText().trim();//chuanes 23/04/2015
        
        boolean flag=true;
        try {
            String  resultado = DBRolesTmp.verificaFechaInicio(fecFin).trim();//se reutiliza el procedimiento de fecha de inicio
            if(resultado.equalsIgnoreCase("TRUE")){
            FarmaUtility.showMessage(this, "Fecha de Fin no Vigente!", txtFecInicio);
            flag=false;
            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al verificar fecha de Fin ..!", txtFecInicio);
            
        }
        return flag;
    }
    private boolean verificaCruceFechas(){
        String dni="";
        String fecInicio="";
        boolean flag=true;
        dni=txtCodigo.getText().trim();
        fecInicio=txtFecInicio.getText().trim();
        String resultado;
        try {
            resultado = DBRolesTmp.verificaCruceFechas(dni,fecInicio).trim();
            if(resultado.equalsIgnoreCase("TRUE")){
            flag=false;
            FarmaUtility.showMessage(this, "Hay cruce de fechas con un registro ..!", txtFecInicio);

            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al verificar vigencia del usuario ..!", txtFecInicio);
        }
        return flag;
    }
    
    private boolean verificaCruceFechaUpdate(){
        String dni="";
        String fecFin="";
        String fecInicio="";
        boolean flag=true;
        dni=txtCodigo.getText().trim();
        fecInicio= VariablesRolesTmp.vFec_InicioForm.trim();//txtFecInicio.getText().trim();
        fecFin=txtFecFin.getText().trim();
        String resultado="";
      
        try {
            
            resultado = DBRolesTmp.verificaCruceFechaUpDate(dni,fecInicio,fecFin).trim();
            if(resultado.equalsIgnoreCase("TRUE")){
            flag=false;
            FarmaUtility.showMessage(this, "Hay cruce de fechas con un registro ..!", txtFecInicio);

            
            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al verificar vigencia del usuario ..!", txtFecInicio);
        }
        return flag;
    }
    
    private boolean verificaCodigoTrabajador(String dni){
        
        boolean flag=true;
        String resultado="";
        try {
            resultado = DBRolesTmp.verificaCodTrabajador(dni).trim();
            if(resultado.equalsIgnoreCase("FALSE")){
            flag=false;
            txtUsuario.setText("");
            FarmaUtility.showMessage(this, "El usuario seleccionado no puede ser Administrador Temporal!", txtCodigo);
            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al verificar rol temporal..!", txtCodigo);
        }
        return flag;
    }
    
    private String actualizaUsuarioRol(){
        String resultado="FALSE";
        String actualiza="";
        String codigo=txtCodigo.getText().trim();
        String fecInicio= VariablesRolesTmp.vFec_InicioForm.trim();//txtFecInicio.getText().trim();
        String fecFin=txtFecFin.getText().trim(); 
        try {
            actualiza=DBRolesTmp.actualizaDatosRolTmp( codigo, fecInicio,fecFin, FarmaVariables.vIdUsu).trim();
            if(actualiza.equalsIgnoreCase("TRUE")){
            resultado="TRUE";
            FarmaUtility.aceptarTransaccion();}
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al actualizar los datos..!"+e.getMessage(), txtFecInicio); 
            resultado="FALSE";
        }
        return resultado;
    }
    
    private boolean verRangoMaxFechas(){
        boolean flag=true;
        String resultado="";
        String cantDias="";
        try {
            cantDias=DBRolesTmp.maxRangoFechas().trim();
            resultado = DBRolesTmp.verifRangoMaxFechas(txtFecFin.getText().trim(),txtFecInicio.getText().trim()).trim();
            if(resultado.equalsIgnoreCase("FALSE")){
            flag=false;
            FarmaUtility.showMessage(this, "El maximo rango entre fechas  es "+ cantDias+" dias!", txtCodigo);
            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al verificar Rango entre Fechas!", txtCodigo);
            flag=false;
        }
        return flag;
    }

   

    public String getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(String secuencial) {
        this.secuencial = secuencial;
    }
}
