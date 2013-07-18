package mifarma.ptoventa.fidelizacion;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;


import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.ConstantsFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgFidelizacionValidaDocumento extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgFidelizacionValidaDocumento.class);
    
    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();


    private JLabelFunction lblModificar = new JLabelFunction();

    private JLabelFunction lblsc = new JLabelFunction();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblDocumento = new JLabelWhite();
    private JComboBox cboDocumento = new JComboBox();
    private JTextField txtDocumento = new JTextField();


    private int vHeight = 0;
    private int vHeightPanel1 = 0;
    private JEditorPane jdMens = new JEditorPane();

    //private JLabelFunction lblF1 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgFidelizacionValidaDocumento() {
        this(null, "", false);
    }

    public DlgFidelizacionValidaDocumento(Frame parent, String title, 
                                    boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
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
        this.setSize(new Dimension(410, 364));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Valida Documento");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jContentPane.setLayout(null);
        lblModificar.setBounds(new Rectangle(90, 90, 105, 20));
        lblModificar.setText("[F11] Aceptar");
        lblsc.setBounds(new Rectangle(200, 90, 95, 20));
        lblsc.setText("[Esc]Salir");
        //lblF1.setBounds(new Rectangle(10, 260, 135, 20));
        //lblF1.setText("[ F1 ] Prueba Ticket");
        //jContentPane.add(lblF1, null);
        jPanelTitle1.setBounds(new Rectangle(10, 205, 380, 120));
        jLabelWhite1.setText("Tipo de Documento");
        //jLabelWhite1.setMnemonic("T");
        jLabelWhite1.setBounds(new Rectangle(20, 15, 115, 25));
        lblDocumento.setText("Nº de Documento");
        //lblDocumento.setMnemonic("N");

        lblDocumento.setBounds(new Rectangle(30, 45, 95, 20));
        cboDocumento.setBounds(new Rectangle(150, 20, 145, 20));
        cboDocumento.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        cboDocumento_keyPressed(e);
                    }
                });
        txtDocumento.setBounds(new Rectangle(150, 50, 145, 20));
        txtDocumento.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtDocumento_keyTyped(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtDocumento_keyReleased(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtDocumento_keyPressed(e);
                    }
                });
        jdMens.setBounds(new Rectangle(10, 10, 380, 190));
        jdMens.setEditable(false);
        jPanelTitle1.add(txtDocumento, null);
        jPanelTitle1.add(cboDocumento, null);
        jPanelTitle1.add(lblDocumento, null);
        jPanelTitle1.add(jLabelWhite1, null);
        jPanelTitle1.add(lblModificar, null);
        jPanelTitle1.add(lblsc, null);
        jContentPane.add(jdMens, null);
        jContentPane.add(jPanelTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();

    }
    
    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
       
        //  05.10.09 Se carga Mensake HTML                                    
        cargarMensaje();
        cargar_cmbTipo();
    }
    
    private void cargar_cmbTipo(){
      ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
      FarmaLoadCVL.loadCVLFromSP(cboDocumento,ConstantsFidelizacion.NOM_HASTABLE_CMB_TIP_DOC,"PTOVENTA_FIDELIZACION.FID_F_TIP_DOC(?)", parametros,true, true);   
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cboDocumento);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {

       
            if (e.getKeyCode() == KeyEvent.VK_F11) {
                //  05.10.09
                if(
                   isInteger(txtDocumento.getText().trim())&&
                   UtilityFidelizacion.validarDocIndentificacion(txtDocumento.getText().trim())
                  )
                {
                    obtenerDatos();
                }
                else
                {
                    FarmaUtility.showMessage(this,
                                             "El documento ingresado no tiene el formato válido.",
                                             txtDocumento);
                }
              
            }
		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarVentana(false);
            }
            
        
    }

    
    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
   
   private void obtenerDatos(){
        // public static String Num_Doc = "";

        VariablesFidelizacion.Tip_doc = 
                FarmaLoadCVL.getCVLCode(ConstantsFidelizacion.NOM_HASTABLE_CMB_TIP_DOC, 
                                        cboDocumento.getSelectedIndex());
        log.info("VariablesFidelizacion.Tip_doc-->" + 
                 VariablesFidelizacion.Tip_doc);

        VariablesFidelizacion.Desc_Tip_doc = 
                FarmaLoadCVL.getCVLDescription(ConstantsFidelizacion.NOM_HASTABLE_CMB_TIP_DOC, 
                                               VariablesFidelizacion.Tip_doc);
        VariablesFidelizacion.Num_Doc = txtDocumento.getText().trim();

        if (VariablesFidelizacion.Num_Doc.equalsIgnoreCase(VariablesFidelizacion.vDniCliente_bk.trim())) {

            String numsec = FarmaVariables.vNuSecUsu;
            String idusu = FarmaVariables.vIdUsu;
            String nomusu = FarmaVariables.vNomUsu;
            String apepatusu = FarmaVariables.vPatUsu;
            String apematusu = FarmaVariables.vMatUsu;

            String rol = "";
            String pIndValidaClaveConfirmacion = "";
            try {
                rol = DBFidelizacion.obtieneRolConfirmacin();
                pIndValidaClaveConfirmacion = 
                        DBFidelizacion.getIndConfClaveReniec().trim();
            } catch (SQLException e) {
                e.printStackTrace();
                rol = FarmaConstants.ROL_ADMLOCAL;
                pIndValidaClaveConfirmacion = "S";
            }

            if (pIndValidaClaveConfirmacion.equalsIgnoreCase("S")) {
                log.info("Solicita clave de Confirmacion");
                DlgLogin dlgLogin = new DlgLogin(myParentFrame, "", true);
                dlgLogin.setRolUsuario(rol.trim());
                dlgLogin.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    log.info("VariablesFidelizacion.Desc_Tip_doc-->" + 
                             VariablesFidelizacion.Desc_Tip_doc);
                    log.info("VariablesFidelizacion.vNomCliente-->" + 
                             VariablesFidelizacion.vNomCliente);
                    if (VariablesFidelizacion.vNomCliente.trim().length() < 
                        1) {
                        FarmaVariables.vNuSecUsu = numsec;
                        FarmaVariables.vIdUsu = idusu;
                        FarmaVariables.vNomUsu = nomusu;
                        FarmaVariables.vPatUsu = apepatusu;
                        FarmaVariables.vMatUsu = apematusu;
                        FarmaUtility.showMessage(this, "Datos vacios", 
                                                 txtDocumento);
                    } else {
                        FarmaVariables.dlgLogin = dlgLogin;
                        FarmaVariables.vAceptar = false;
                        VariablesFidelizacion.Usu_Confir = 
                                FarmaVariables.vNuSecUsu.trim();
                        enviarCorreo();
                        FarmaVariables.vNuSecUsu = numsec;
                        FarmaVariables.vIdUsu = idusu;
                        FarmaVariables.vNomUsu = nomusu;
                        FarmaVariables.vPatUsu = apepatusu;
                        FarmaVariables.vMatUsu = apematusu;
                        cerrarVentana(true);
                    }

                }

            } else {
                log.info("NO SOLICITA clave de Confirmacion");
                log.info("SOLO ENVIA CORREO");
                FarmaVariables.vAceptar = false;
                VariablesFidelizacion.Usu_Confir = 
                        FarmaVariables.vNuSecUsu.trim();
                enviarCorreo();
                cerrarVentana(true);
            }
        } else {
            //Este metodo se ha comentado para colocarlo en el metodo reingresoValidacionDNI.
            //20.10.2009     
            /*FarmaUtility.showMessage(this,
                                    "El documento ingresado (Nº. "+ VariablesFidelizacion.Num_Doc+" ) es diferente \n" +
                                    " al digitado en la pantalla anterior (Nº. "+ VariablesFidelizacion.vDniCliente+" ) \n \n"+
                                    "Por favor verifique nuevamente!!"
              ,txtDocumento);*/

            if (reingresoValidacionDNI()){
                log.info("Cierra la Ventana");
                FarmaVariables.vAceptar = false;
                //VariablesFidelizacion.Usu_Confir = FarmaVariables.vNuSecUsu.trim();
                //enviarCorreo();
                cerrarVentana(true);
            }
        }
   
   }
   
   /**
     * Algoritmo para validar todo en BD 
     * @author  
     * @since  20.10.2009
     * @return
     */
   private boolean reingresoValidacionDNI(){
       
       boolean vValorRetorno = false;
       
        
       String vDocumento = 
           (String)JOptionPane.showInputDialog(this,
                                               "El documento ingresado (Nº. "+ VariablesFidelizacion.Num_Doc+" ) es diferente \n" +
                                               " al digitado en la pantalla anterior (Nº. "+ VariablesFidelizacion.vDniCliente_bk+" ) \n \n"+
                                               "Por favor ingréselo nuevamente!!"+ " :\n"
                                               ,
                                               "MiFarma",
                                               JOptionPane.PLAIN_MESSAGE);
       if (vDocumento == null) vDocumento = "";
       vDocumento = vDocumento.trim().toUpperCase();
       

       if(
          isInteger(vDocumento.trim())&&
          UtilityFidelizacion.validarDocIndentificacion(vDocumento.trim())
         ){
           //Es numero inicia el algoritmo planteado.
           //Se invocara este procedimiento que realiza las validaciones Indicadas.
           log.info("***************INICIO*******************");
           log.info("1)Datos Ingresados en Formulario");
           log.info("Documento - 1:"+VariablesFidelizacion.vDniCliente_bk);
           log.info("Nombre:"+VariablesFidelizacion.vNomCliente_bk);
           log.info("Fech.Nac:"+VariablesFidelizacion.vFecNacimiento_bk);
           log.info("************************************");
           log.info("2)Datos Ingresados en pantalla Validacion");
           log.info("Documento - 2:"+ VariablesFidelizacion.Num_Doc);
           log.info("Tipo Documento:"+VariablesFidelizacion.Tip_doc);
           log.info("Desc.Documento:"+VariablesFidelizacion.Desc_Tip_doc);
           log.info("************************************");           
           log.info("3)Datos Ingresados en tercera repeticion");
           log.info("Documento - 3:"+ vDocumento);
           log.info("**************************************");
           /*
           String pFrmDni_in,
           String pFrmNombre_in,
           String pFrmFechNacimiento_in,
           String pValidaDni_in,
           String pValidaDniTercero_in
            * */
           String pDatosClienteFinales = "";
           String[] vListaDatos;
            try {
                pDatosClienteFinales = 
                        DBFidelizacion.validacionTerceraDNI(VariablesFidelizacion.vDniCliente_bk, 
                                                            VariablesFidelizacion.vNomCliente_bk, 
                                                            VariablesFidelizacion.vFecNacimiento_bk, 
                                                            VariablesFidelizacion.Num_Doc, 
                                                            vDocumento).trim();
                
                //Este metodo SOLO ASUME QUE RETORNA 3 Valores
                //dni@nombre@fechaNacimiento
                pDatosClienteFinales = pDatosClienteFinales.trim();
                log.info("pDatosClienteFinales:"+pDatosClienteFinales);
                if(pDatosClienteFinales.length()>0){
                   vListaDatos = pDatosClienteFinales.split("@");
                   log.info("vListaDatos:"+vListaDatos);
                   if(vListaDatos.length>=3){
                       
                       ArrayList vaux = new ArrayList();
                       vaux.add(vListaDatos[0]);
                       vaux.add(vListaDatos[1]);
                       vaux.add(vListaDatos[2]);
                       vaux.add(vListaDatos[3]);
                       String pClaveConfirmacion = vListaDatos[3];
                       log.info("SOLICITA CLAVE:"+pClaveConfirmacion);  
                     ///////////////////////////////////////////////////////////
                       if (pClaveConfirmacion.trim().equalsIgnoreCase("S"))
                       {

                           String numsec = FarmaVariables.vNuSecUsu;
                           String idusu = FarmaVariables.vIdUsu;
                           String nomusu = FarmaVariables.vNomUsu;
                           String apepatusu = FarmaVariables.vPatUsu;
                           String apematusu = FarmaVariables.vMatUsu;

                           String rol = "";
                           String pIndValidaClaveConfirmacion = "";
                           try {
                               rol = DBFidelizacion.obtieneRolConfirmacin();
                               pIndValidaClaveConfirmacion = 
                                       DBFidelizacion.getIndConfClaveReniec().trim();
                           } catch (SQLException e) {
                               e.printStackTrace();
                               rol = FarmaConstants.ROL_ADMLOCAL;
                               pIndValidaClaveConfirmacion = "S";
                           }
                           
                           log.info("Solicita clave de Confirmacion Tercera Validacion");
                           DlgLogin dlgLogin = new DlgLogin(myParentFrame, "", true);
                           dlgLogin.setRolUsuario(rol.trim());
                           dlgLogin.setVisible(true);

                           if (FarmaVariables.vAceptar) {
                               log.info("VariablesFidelizacion.Desc_Tip_doc-->" + 
                                        VariablesFidelizacion.Desc_Tip_doc);
                               log.info("VariablesFidelizacion.vNomCliente-->" + 
                                        VariablesFidelizacion.vNomCliente);
                               if (VariablesFidelizacion.vNomCliente.trim().length() < 
                                   1) {
                                   FarmaVariables.vNuSecUsu = numsec;
                                   FarmaVariables.vIdUsu = idusu;
                                   FarmaVariables.vNomUsu = nomusu;
                                   FarmaVariables.vPatUsu = apepatusu;
                                   FarmaVariables.vMatUsu = apematusu;
                                   FarmaUtility.showMessage(this, "Datos vacios", 
                                                            txtDocumento);
                               } else {
                                   FarmaVariables.dlgLogin = dlgLogin;
                                   FarmaVariables.vAceptar = false;
                                   VariablesFidelizacion.Usu_Confir = 
                                           FarmaVariables.vNuSecUsu.trim();
                                   enviarCorreo();
                                   FarmaVariables.vNuSecUsu = numsec;
                                   FarmaVariables.vIdUsu = idusu;
                                   FarmaVariables.vNomUsu = nomusu;
                                   FarmaVariables.vPatUsu = apepatusu;
                                   FarmaVariables.vMatUsu = apematusu;
                                   //cerrarVentana(true);
                                   vValorRetorno = true;
                               }

                           }

                       }
                       else{
                           VariablesFidelizacion.Usu_Confir = 
                                   FarmaVariables.vNuSecUsu.trim();
                           enviarCorreo();
                           vValorRetorno = true;
                       }
                       
                       
                       //solo si la variable es TRUE
                       if(vValorRetorno){
                           VariablesFidelizacion.vDatosFinalTerceraValidacion = (ArrayList)vaux.clone();
                           log.info("VariablesFidelizacion.vDatosFinalTerceraValidacion:"+
                                    VariablesFidelizacion.vDatosFinalTerceraValidacion);
                       }
                       
                     //////////////////////////////////////////////////////////

                       
                       
                       
                   }
                   
                }   
                
                log.info("Datos del Cliente:"+pDatosClienteFinales);
            } catch (SQLException e) {
                e.printStackTrace();
            }
           
           
       }
       else{
           FarmaUtility.showMessage(this,
                                    "El documento ingresado no tiene el formato correcto.",
                                    txtDocumento);
       }
       
       
       log.info("Valor Ingresado:["+vDocumento+"]"); 
       
       return vValorRetorno;
   }
    
   private boolean isInteger(String pCadena){
       int numero=0;
       boolean pRspta = false;
        try {
            numero = Integer.parseInt(pCadena.trim());
            pRspta = true;
        } catch (Exception e) {
            pRspta = false;
        }
       
       log.info("return isInteger:["+pRspta+"]");
       return pRspta;
   }
    private void enviarCorreo(){
        try{
              DBFidelizacion.enviaCorreoConfirmacion(VariablesFidelizacion.Tip_doc,
                                                        VariablesFidelizacion.Num_Doc,
                                                        VariablesFidelizacion.vNomCliente,
                                                         VariablesFidelizacion.vFecNacimiento,
                                                     VariablesFidelizacion.vNumTarjeta.trim());
              //FarmaUtility.aceptarTransaccion();                                           
          }catch(SQLException sql){
              cerrarVentana(false);
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrio un error al enviar correo de confirmacion.\n"+sql.getMessage(),txtDocumento);
            // System.out.println("Ocurrio un error al enviar correo de confirmacion");
          }
    }
    
    private void cargarMensaje() {
        String mensaje = "";
        
        try {
            mensaje = DBFidelizacion.getMsg();
            log.info("mensaje"+mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "";
            log.info("Entro al catch");
        }
        //lblMensaje.setText(mensaje);
        jdMens.setText(mensaje);
        if(mensaje!=null){
            /*lblMensaje.setContentType("text/html");
            lblMensaje.setText(mensaje);*/
            jdMens.setContentType("text/html");
            jdMens.setText(mensaje);
        }
        log.info("mensaje;"+mensaje);
        if(mensaje.trim().length()>0) {
        
            //calculoTamanoLogin(mensaje);
            //this.setSize(new Dimension(337, vHeight));            
        }

    }


    private void calculoTamanoLogin(String vMensaje){
        if (vMensaje.equalsIgnoreCase("")){ 
            vHeight = 0;
            vHeightPanel1 = 0;
            return;
        }
        
        int vFilasM = 0;
        log.info("Donde <b>: "+vMensaje.indexOf("<b>"));
        log.info("Donde </b>: "+vMensaje.indexOf("</b>"));
        
        String mMens = vMensaje.substring(vMensaje.indexOf("<b>")+3,vMensaje.indexOf("</b>"));
        if (mMens.length()%28 > 0){
            vFilasM = mMens.length()/28;
            vFilasM += 3;
        }else{
            vFilasM = mMens.length()/28;
            vFilasM += 2;
        }
        log.info("FILAS:"+vFilasM);
        //Dimensión del JEditorPane lblMensaje
        //lblMensaje.setSize(355,26*vFilasM);
                
        //seteo variable Panel 1
        vHeightPanel1 = (165+26*vFilasM)+50;
        //seteo variable de la Altura LOGIN
        vHeight = (165+(26*vFilasM)+45);
        
        jPanelTitle1.setSize(305,vHeightPanel1);
        log.info("vHeight: "+ vHeight);
        log.info("jp1: "+ jPanelTitle1.getSize().getHeight());
       // System.out.println("lblMensaje: "+ lblMensaje.getSize().getHeight());
        
        log.info("Mensaje: "+mMens);    
        log.info("Total Carac: "+mMens.length());
                
    }


    private void cboDocumento_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
           if(cboDocumento.getSelectedIndex()>-1){
           FarmaUtility.moveFocus(txtDocumento);
           }
        }
        chkKeyPressed(e);
    }

    private void txtDocumento_keyTyped(KeyEvent e) {
    
        FarmaUtility.admitirDigitos(txtDocumento,e);
    }


    private void txtDocumento_keyPressed(KeyEvent e) {
    
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
          // if(txtDocumento.getText().trim().length()>0){
           FarmaUtility.moveFocus(cboDocumento);
           //}
        }
        chkKeyPressed(e);
    }

    private void txtDocumento_keyReleased(KeyEvent e) {
    }
}
