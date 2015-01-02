package mifarma.ptoventa.controlingreso;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.FarmaHora;
import mifarma.ptoventa.administracion.usuarios.reference.DBUsuarios;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.controlingreso.reference.ConstantsControlIngreso;
import mifarma.ptoventa.controlingreso.reference.DBControlIngreso;
import mifarma.ptoventa.controlingreso.reference.VariablesControlIngreso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgControlIngreso.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * PAULO      11.10.2007   Creación<br>
 * <br>
 * @author Paulo Cesar Ameghino Rojas<br>
 * @version 1.0<br>
 *
 */
public class DlgControlIngreso extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgControlIngreso.class);

  private Frame myParentFrame;
  private FarmaTableModel tableModel;
  //Se modifico el orden al añadir las dos columnas de ingreso y salida 2
  //23.11.2007  dubilluz modificacion
  private final int COL_ORD = 6;
  private BorderLayout borderLayout1 = new BorderLayout();
  private GridLayout gridLayout1 = new GridLayout();
  private BorderLayout borderLayout2 = new BorderLayout();
  private BorderLayout borderLayout3 = new BorderLayout();
  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private JLabelOrange lblHora_T = new JLabelOrange();
  private JPanelWhite jPanelWhite2 = new JPanelWhite();
  private JLabelWhite lblIndFiscalizado = new JLabelWhite();
  private JButtonLabel btnTipo = new JButtonLabel();
  private JLabelWhite lblMensaje = new JLabelWhite();
  private JButtonLabel btnDni = new JButtonLabel();
  private JComboBox cmbTipo = new JComboBox();
  private JLabelOrange lblPersonal = new JLabelOrange();
  private JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
  private JScrollPane scrLista = new JScrollPane();
  private JPanelTitle jPanelTitle1 = new JPanelTitle();
  private JLabelWhite lblItems = new JLabelWhite();
  private JLabelWhite lblItems_T = new JLabelWhite();
  private JTable tblLista = new JTable();
  FarmaHora lblHora;
  
  private boolean flag=true;


  public DlgControlIngreso()
  {
    this(null, "", false);
  }

  public DlgControlIngreso(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
  
    try
    {
      jbInit();
      initialize();
    }
    catch(Exception e)
    {
      log.error("",e);
    }

  }

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(791, 578));
    this.getContentPane().setLayout(gridLayout1);
    this.setTitle("Control de Ingreso");
    this.addWindowListener(new java.awt.event.WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    lblHora_T.setText("HORA MIFARMA:");
    lblHora_T.setBounds(new Rectangle(265, 5, 180, 45));
    lblHora_T.setFont(new Font("SansSerif", 1, 20));
    jPanelWhite2.setBounds(new Rectangle(15, 55, 750, 105));
    jPanelWhite2.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    lblIndFiscalizado.setBounds(new Rectangle(175, 40, 175, 25));
    lblIndFiscalizado.setForeground(Color.black);
    lblIndFiscalizado.setToolTipText("null");
    btnTipo.setText("Tipo:");
    btnTipo.setBounds(new Rectangle(10, 50, 30, 20));
    btnTipo.setForeground(new Color(255, 130, 14));
    btnTipo.setMnemonic('T');
    btnTipo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnTipo_actionPerformed(e);
        }
      });
    lblMensaje.setText("<html>PRESIONE <FONT COLOR=RED><FONT SIZE=+1>'E'</FONT></FONT> PARA ENTRADA O <FONT COLOR=BLUE><FONT SIZE=+1>'S'</FONT></FONT> PARA SALIDA. LUEGO <FONT SIZE=+1>F11 O ENTER </FONT> PARA GRABAR EL REGISTRO.</html>");
    lblMensaje.setBounds(new Rectangle(10, 75, 660, 25));
    lblMensaje.setForeground(Color.black);
    lblMensaje.setToolTipText("null");
    btnDni.setText("DNI:");
    btnDni.setBounds(new Rectangle(10, 15, 30, 20));
    btnDni.setForeground(new Color(255, 130, 14));
    btnDni.setMnemonic('D');
    btnDni.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDni_actionPerformed(e);
        }
      });
    cmbTipo.setBounds(new Rectangle(40, 50, 120, 20));
    cmbTipo.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        cmbTipo_keyPressed(e);
                    }
      });
    cmbTipo.addFocusListener(new FocusAdapter()
      {
        public void focusGained(FocusEvent e)
        {
          cmbTipo_focusGained(e);
        }
        public void focusLost(FocusEvent e)
        {
          cmbTipo_focusLost(e);
        }
      });
    lblPersonal.setBounds(new Rectangle(175, 5, 575, 30));
    lblPersonal.setFont(new Font("SansSerif", 1, 25));
    txtDni.setBounds(new Rectangle(40, 15, 85, 20));
    txtDni.setLengthText(8);
    txtDni.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtDni_keyPressed(e);
        }
        public void keyTyped(KeyEvent e)
        {
          txtDni_keyTyped(e);
        }
      });
    scrLista.setBounds(new Rectangle(15, 185, 750, 350));
    jPanelTitle1.setBounds(new Rectangle(15, 165, 750, 20));
    lblItems.setBounds(new Rectangle(630, 0, 70, 20));
    lblItems_T.setText("Items:");
    lblItems_T.setBounds(new Rectangle(575, 0, 50, 20));
    scrLista.getViewport();
    jPanelTitle1.add(lblItems, null);
    jPanelTitle1.add(lblItems_T, null);
    jPanelWhite2.add(lblIndFiscalizado, null);
    jPanelWhite2.add(btnTipo, null);
    jPanelWhite2.add(lblMensaje, null);
    jPanelWhite2.add(btnDni, null);
    jPanelWhite2.add(cmbTipo, null);
    jPanelWhite2.add(lblPersonal, null);
    jPanelWhite2.add(txtDni, null);
    jPanelWhite1.add(jPanelTitle1, null);
    scrLista.getViewport().add(tblLista, null);
    jPanelWhite1.add(scrLista, null);
    jPanelWhite1.add(jPanelWhite2, null);
    jPanelWhite1.add(lblHora_T, null);
    this.getContentPane().add(jPanelWhite1, null);
  }

  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */
  
  private void initialize()
  {
    lblMensaje.setVisible(false);
    lblPersonal.setText("<html><font size=-1><font color=black>Ingrese su DNI y presione ENTER.</font></font></html>");
    initCombo();
    initTable();
  }
  
  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */
  
  private void initCombo()
  {
    String codigo[] = { "01", "02" }, valor[] = { "ENTRADA", "SALIDA" };
    FarmaLoadCVL.loadCVLfromArrays(cmbTipo, ConstantsControlIngreso.CMB_TIPO_REG, codigo, valor, true);
  }

  private void initTable()
   {
     tableModel = new FarmaTableModel(ConstantsControlIngreso.columnsListaRegistro, ConstantsControlIngreso.defaultValuesListaRegistro,0);
     FarmaUtility.initSimpleList(tblLista, tableModel,ConstantsControlIngreso.columnsListaRegistro);
   }
  
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */
  

  private void btnTipo_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(cmbTipo);  
  }

  private void btnDni_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtDni);
  }

  private void cmbTipo_focusGained(FocusEvent e)
  {
    lblMensaje.setVisible(true);  
  }

  private void cmbTipo_focusLost(FocusEvent e)
  {
    lblMensaje.setVisible(false);
  }

  private void cmbTipo_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void txtDni_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtDni,e);
  }

  private void txtDni_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(buscaDni())
      {
        determinaTipoRegistro();
        FarmaUtility.moveFocus(cmbTipo);
      }
      else
      {
        FarmaUtility.moveFocus(txtDni);
      }
    }
    else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      limpiarDatos(); 
      cerrarVentana(false);
    }  
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.moveFocus(txtDni);
    FarmaUtility.centrarVentana(this);    
    
    //mostrarHora();
    cargaListaRegistro();
    //cargarFecha();
  }

  /* ************************************************************************ */
  /*                      METODOS AUXILIARES DE EVENTOS                       */
  /* ************************************************************************ */
  
  private void chkKeyPressed(KeyEvent e)
  {
    if ((mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))||(e.getKeyCode() == KeyEvent.VK_ENTER))
    {
      grabarRegisto();
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
     cerrarVentana(false); 
    }
  }

  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */

  private boolean buscaDni()
  {
    boolean retorno = false;
    if(validaDni())
    {
      String vNombre = "";
      String vInd_Fiscalizado="";
      ArrayList vArrayAux = new ArrayList();
      ArrayList vArrayInd = new ArrayList();
      
      try
      {
        DBControlIngreso.getPersonal(vArrayAux,txtDni.getText().trim());

        if(vArrayAux.size() > 0)
        {
          vArrayAux = (ArrayList)vArrayAux.get(0);
          vNombre = vArrayAux.get(0).toString().trim();
          VariablesControlIngreso.vCodCia = vArrayAux.get(1).toString().trim();
          VariablesControlIngreso.vCodTrab = vArrayAux.get(2).toString().trim();
          //VariablesControlIngreso.vCodHor = vArrayAux.get(3).toString().trim();
          VariablesControlIngreso.vSugTipo = vArrayAux.get(3).toString().trim();
          vInd_Fiscalizado=vArrayAux.get(4).toString().trim();    
          retorno = true;
        }else
        {
          vNombre = "<html>PERSONAL NO REGISTRADO. <font size=-1><font color=black>Presione ESC para corregir.</font></font></html>";
          VariablesControlIngreso.vCodCia = "";
          VariablesControlIngreso.vCodTrab = "";
          VariablesControlIngreso.vCodHor = "";
          VariablesControlIngreso.vSugTipo = "";
          vInd_Fiscalizado = "";
          retorno = false;
        }
        
        //retorno = true;
      }catch(SQLException s)
      {
        retorno = false;
        vNombre = "Error al consultar, intente de nuevo.";
        VariablesControlIngreso.vCodCia = "";
        VariablesControlIngreso.vCodTrab = "";
        VariablesControlIngreso.vCodHor = "";
        VariablesControlIngreso.vSugTipo = "";
        vInd_Fiscalizado = "";
        log.error("",s);
        FarmaUtility.showMessage(this,"Ha ocurrido un error al consulta el DNI.\n"+s.getMessage(),txtDni);
      }finally
      {
        lblPersonal.setText(vNombre);
        if(vInd_Fiscalizado.equalsIgnoreCase("N"))
        {
          lblIndFiscalizado.setText("<html><font size=-1><font color=red>(NO FISCALIZADO)</font></font></html>");
        }else
        {
          lblIndFiscalizado.setText("");
        }
      }
    }
    return retorno;
  }

  private boolean validaDni()
  {
    boolean retorno = true;
    String vDni = txtDni.getText().trim();
    if(vDni.length() < 8)
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Debe ingresar un DNI valido. ¡Verifique!",txtDni);
    }
    return retorno;
  }

  private void determinaTipoRegistro()
  {
    if(VariablesControlIngreso.vSugTipo.equals(""))
    {
      int hora = Integer.parseInt(lblHora.getText().substring(0,2));
      
      if(hora < 12)
      { 
        VariablesControlIngreso.vSugTipo = ConstantsControlIngreso.TIPO_ENTRADA;
      }else
      {
        VariablesControlIngreso.vSugTipo = ConstantsControlIngreso.TIPO_SALIDA;
      }
    }
   
    FarmaLoadCVL.setSelectedValueInComboBox(cmbTipo,ConstantsControlIngreso.CMB_TIPO_REG,VariablesControlIngreso.vSugTipo);
  }

  private void limpiarDatos()
  {
    txtDni.setText("");
    //lblPersonal.setText("");
    lblPersonal.setText("<html><font size=-1><font color=black>Ingrese su DNI y presione ENTER.</font></font></html>");
    lblIndFiscalizado.setText("");
    FarmaLoadCVL.setSelectedValueInComboBox(cmbTipo,ConstantsControlIngreso.CMB_TIPO_REG,ConstantsControlIngreso.TIPO_ENTRADA);
    VariablesControlIngreso.vCodCia = "";
    VariablesControlIngreso.vCodTrab = "";
    VariablesControlIngreso.vCodHor = "";
    VariablesControlIngreso.vSugTipo = "";
    FarmaUtility.moveFocus(txtDni);
  }

  
  private void grabarRegisto()
  {
    String Dniaux="";
    if(buscaDni())
    {
     if(existeRegistro()){
          String vTipo = FarmaLoadCVL.getCVLCode(ConstantsControlIngreso.CMB_TIPO_REG,cmbTipo.getSelectedIndex());
          String vDescTipo = FarmaLoadCVL.getCVLDescription(ConstantsControlIngreso.CMB_TIPO_REG,vTipo);
          
          
          flag=true;
            if(vTipo.trim().equalsIgnoreCase(ConstantsControlIngreso.TIPO_SALIDA)){  
             if(ValidaRolQF(txtDni.getText().trim())){
               if(!existeRegistroTemp()){
                   
                   FarmaUtility.showMessage(this,"Debe registrar temperaturas para poder marcar su salida.",cmbTipo);
                  flag=false;
               }
              }
             }
             
             if(flag){ //Verifica si es rol Administrador Local
                          
                Dniaux=txtDni.getText().trim();
                
                
                String vAvisoTrabLocal="";
               
                //PTOVENTA_INGR_PERS pakete funcion VERIFICA_ROL_TRAB_LOCAL
                //SI EL USUARIO  TIENE ROL DE CAJERO,VENDEDOR,QF DEBE APARECER EL MENSAJE 
              
              //---------------------------------------@Agregado por Asolis-------------------------------------------------------------------
               if(ValidaRolTrabLocal())
               {
                   
                   if (VariablesControlIngreso.vExisteCarne.trim().equalsIgnoreCase("S"))
                   {
                       if (VariablesControlIngreso.vFechaVencCarne.trim().equals("NV"))//No ha vencido el carne o no está proximo de vencer
                            vAvisoTrabLocal="";  
                           
            
                       else { 
                             
                             if(VariablesControlIngreso.vMensajeTiempoVencimiento.trim().equalsIgnoreCase("V"))
                                 vAvisoTrabLocal="Le recordamos que su Carné de Sanidad caducó el :" + VariablesControlIngreso.vFechaVencCarne;
                                        
                             else    
                               vAvisoTrabLocal="Le recordamos que su Carné de Sanidad caduca el :" + VariablesControlIngreso.vFechaVencCarne;
                           }
                       
                       
                   }
                   
                   else
                   {
                       vAvisoTrabLocal="Le informamos que Usted no tiene Carné de Sanidad registrado en el Sistema";
                       
                       VariablesControlIngreso.vEnviaAlerta="S";
                       
                       
                   }   
                   
                  
                   
                }
               
               else
               {
                   log.info("No tiene Rol de Administrador o Cajero o Vendedor");
                  
                   
               }
                 
                //----------------------------------------------------------------------------------------------------------
              if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,vAvisoTrabLocal + "\n" +"¿Está seguro de registrar su "+vDescTipo+"?"))
              {
               
                    
                try
                {
                  DBControlIngreso.grabarRegistro(txtDni.getText().trim(),vTipo,
                                              VariablesControlIngreso.vCodCia,
                                              VariablesControlIngreso.vCodTrab,
                                              VariablesControlIngreso.vCodHor);
                  //DBControlIngreso.validarIngreso(txtDni.getText().trim());
                  //DBControlIngreso.ActualizarDatosIngreso(txtDni.getText().trim());
                  FarmaUtility.aceptarTransaccion();          
                  cargaListaRegistro();
                  if(tblLista.getRowCount() > 0)
                  {
                    FarmaUtility.findTextInJTable(tblLista, txtDni.getText().trim(), 0, 0);
                  }
                
                    
    //-------------------------------@Agregado por Asolis-------------------------------------
    //--------------@Envia Alerta al Registrar Ingreso si no tiene Carné Registrado
                    if (vTipo.equalsIgnoreCase(ConstantsControlIngreso.TIPO_ENTRADA)){ 
                          //Envia Alerta.   
                    
                        if(VariablesControlIngreso.vEnviaAlerta.equalsIgnoreCase("S")){ 
                              
                            
                            if (EnviaAlertaAlMarcarIngresoLocal()){
                                
                                
                                
                                log.info("Alerta Satisfactoria");
                            }  
                            else
                              log.info("No se envió la alerta ");
                            
                            
                        }
                     }    
    //----------------------------------------------------------------------------------------------------------
                    
                     limpiarDatos();
                     flag=true;
      
                    //JCORTEZ 17.08.09 Se generan cupones de regalo
                     if(ValidaRolQF(Dniaux))
                      generarCuponesRegalo(vTipo,Dniaux);
                    
                }catch(SQLException s)
                {
                  FarmaUtility.liberarTransaccion();
                  if(s.getErrorCode() == 20001)
                  {
                    FarmaUtility.showMessage(this,"Usted no puede registrarse, ya que no es un trabajador fiscalizado",txtDni);  
                    lblIndFiscalizado.setText("");
                  }
                  else if(s.getErrorCode() == 20002)
                  { //13.11.2007  dubilluz  añadido
                    FarmaUtility.showMessage(this,"No puede registrar su entrada porque ya existe un registro de entrada para el día de hoy.",txtDni);  
                  }
                  else{
                    log.error("",s);
                    FarmaUtility.showMessage(this,"Ha ocurrido un error al grabar el registro.\n"+s.getMessage(),txtDni);
                  }
                }
              }
            }
    }
    }else
    {
      FarmaUtility.moveFocus(txtDni);
    }
  }
  
    /**
     * Se ingreso temperatura
     * @author JCORTEZ
     * @since 12.02.2009
     * */
    private boolean existeRegistroTemp(){
    
    boolean valor=true;
    String result="",SecUsu="";
    try
        {
            SecUsu=DBControlIngreso.getSecUsuLocal(txtDni.getText().trim());
         result=DBControlIngreso.verificaIngrTemperatura(SecUsu);
            
         if(result.equalsIgnoreCase("N")){
          valor=false;
         }
        }
        catch (SQLException e)
        {
          log.error("",e);
          FarmaUtility.showMessage(this,"Ha ocurrido un error al validar registro de temperatura .\n" + e.getMessage(), cmbTipo);
        }
        return valor;
    }
    
    
  private boolean existeRegistro(){

    boolean retorno = true;
    
      ArrayList vArrayAux = new ArrayList();
      ArrayList vArrayAux1 = new ArrayList();
      try
      {
        DBControlIngreso.getRegistro(vArrayAux,txtDni.getText().trim());
        DBControlIngreso.validaSalida(vArrayAux1,txtDni.getText().trim());
        
        if(vArrayAux.size() > 0)
        {
          FarmaUtility.showMessage(this,"Usted ya se ha registrado el dia de hoy",txtDni);
          retorno = false;
        }/*else
        {
          if(vArrayAux1.size()>0){
           if(cmbTipo.getSelectedIndex()==0){
             FarmaUtility.showMessage(this,"Usted debe registrar su salida",txtDni);
             cmbTipo.setSelectedIndex(1);
             retorno = false;
           }
          }else{
            if(cmbTipo.getSelectedIndex()==1){
              FarmaUtility.showMessage(this,"Usted debe registrar su entrada la primera vez",txtDni);
              cmbTipo.setSelectedIndex(0);
              retorno = false;
            }else{
              retorno = true;
            }
          }
        }*/
      }catch(SQLException s)
      {
        log.error("",s);
        FarmaUtility.showMessage(this,"Ha ocurrido un error al consulta el DNI.\n"+s.getMessage(),txtDni);
      }
    return retorno;
  }

  private void mostrarHora()
  {
    lblHora = new FarmaHora(); 
    lblHora.setText("");
    lblHora.setBounds(new Rectangle(500, 10, 220, 35));
    lblHora.setFont(new Font("SansSerif", 1, 20));
    jPanelWhite1.add(lblHora, null);
    lblHora.start();
  }
  

  private void cargaListaRegistro()
  {
    try
    {
      DBControlIngreso.cargaListaRegistros(tableModel);
      FarmaUtility.ordenar(tblLista,tableModel,COL_ORD,FarmaConstants.ORDEN_DESCENDENTE);
      lblItems.setText(tblLista.getRowCount()+"");
    }catch(SQLException s)
    {
      log.error("",s);
      FarmaUtility.showMessage(this,"Ha ocurrido un error al listar los registros.\n"+s.getMessage(),txtDni);
    }
  }

  private  void cargarFecha(){
    try{
      String FechaInicio=FarmaSearch.getFechaHoraBD(1);
      //lblFechaSistema.setText(FechaInicio);
    }catch(SQLException sql){
      log.error("",sql);
    }
  }
  
    private boolean ValidaRolQF(String Dni){
    
    boolean valor=true;
    String result="",SecUsu="";
    try
        {
         SecUsu=DBControlIngreso.getSecUsuLocal(Dni);
            
         result=DBControlIngreso.verificaRolUsuario(SecUsu,ConstantsControlIngreso.ROL_QF_ADMINLOCAL);
         if(result.equalsIgnoreCase("N"))
         valor=false;
        }
        catch (SQLException e)
        {
          log.error("",e);
          FarmaUtility.showMessage(this,"Ha ocurrido un error al validar el rol de usuario .\n" + e.getMessage(), txtDni);
        }
        return valor;
    }
  
  
    private boolean ValidaExistenciaCarne(){
    boolean valor = true;
    String result="";
    
    try {
         result = DBUsuarios.verificaExistenciaCarne(VariablesControlIngreso.vSecUsu);
        //No tiene Carné Registrado
          if (result.trim().equalsIgnoreCase("0")){
                valor = false;
            }
        
        }
        
    catch(SQLException e)
               {
                 log.error("",e);
                 FarmaUtility.showMessage(this,"Ha ocurrido un error al validar carne de usuario .\n" + e.getMessage(), txtDni);
               }
               return valor;
    }
  
    private boolean ValidaRolTrabLocal(){
    
    boolean valor=true;
    String result="",SecUsu="";
    try
        {
         SecUsu=DBControlIngreso.getSecUsuLocal(txtDni.getText().trim());
        VariablesControlIngreso.vSecUsu = SecUsu;
            
        //VERIFICA_ROL_TRAB_LOCAL
         result=DBControlIngreso.ValidaRolTrabLocal(SecUsu,ConstantsControlIngreso.ROL_CAJERO,ConstantsControlIngreso.ROL_VENDEDOR,ConstantsControlIngreso.ROL_QF_ADMINLOCAL);
        if(result.equalsIgnoreCase("N")){
           valor=false;
           VariablesControlIngreso.vFechaVencCarne ="";
        }
        
        else //Para obtener fecha de vencimiento ,validar que tenga carné 
            {
                if(ValidaExistenciaCarne()){
                
                VariablesControlIngreso.vExisteCarne="S";
                    
                String resultadoConsulta="",resultadoMensaje="" ;
                resultadoConsulta = DBUsuarios.verificaFechaVenUsuarioCarneControlIngreso(SecUsu);
                resultadoMensaje  = DBUsuarios.verificaFechaVenUsuarioCarne(SecUsu);
                VariablesControlIngreso.vFechaVencCarne = resultadoConsulta;
                VariablesControlIngreso.vMensajeTiempoVencimiento = resultadoMensaje;
                
                
               }
            
              else
                VariablesControlIngreso.vExisteCarne="N";
                    
            
            }
        
           
        }
        catch (SQLException e)
        {
          log.error("",e);
          FarmaUtility.showMessage(this,"Ha ocurrido un error al validar el rol de usuario .\n" + e.getMessage(), txtDni);
        }
        return valor;
    }
  
  private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}
  
    private boolean EnviaAlertaAlMarcarIngresoLocal() {
        
        boolean retorno = false;
        
    
        try {
               DBUsuarios.enviaAlertaCarneUsuarioMarcaIngreso(VariablesControlIngreso.vSecUsu); 
               retorno = true;
            
            }

        catch (SQLException sql){
                log.error("",sql);
                retorno = false;
            }
        
        
        return retorno;
    }
  
  
  /**
   * Se genera  N° cupones de X Campaña cuando el usuario marque ingreso 
   * @AUTHOR  JCORTEZ 
   * @SINCE   17.08.09
   * */
  private  boolean generarCupones(String tipo,String Dni){
  
  boolean valor=false;
  String  flag="";
  
    if(tipo.equalsIgnoreCase(ConstantsControlIngreso.TIPO_ENTRADA)){
    try
    {
        
        DBControlIngreso.generaCuponesRegalo(Dni);
        FarmaUtility.aceptarTransaccion();
        valor= true;
    }catch (SQLException e)
    {
        log.error("",e);
        FarmaUtility.showMessage(this,"Ha ocurrido un error al genera cupones regalo .\n" + e.getMessage(), Dni);
    }
    }
    return valor;
  }
  
    /**
     * Se obtiene cupones generados 
     * @AUTHOR  JCORTEZ 
     * @SINCE   17.08.09
     * */
  private void obtieneCuponesRegalo(ArrayList cuponRegalos,String Dni){
      try
      {
          
          DBControlIngreso.obtieneCuponesRegalo(cuponRegalos,Dni);
      }catch (SQLException e)
      {
          if(e.getErrorCode() == 20099)
          {
            cuponRegalos.clear();
              
          }else{
              log.error("",e);
              FarmaUtility.showMessage(this,"Ha ocurrido un error al obtener cupones regalo .\n" + e.getMessage(), Dni);
          }
      }
  }
  
  
    /***
     * valida impresion de cupones
     * @AUTHOR JCORTEZ
     * @SINCE 17.08.09
     * */
  private void generarCuponesRegalo(String vTipo,String Dniaux){

      
      ArrayList cuponesRegalo =new ArrayList();
      String dni=Dniaux;
      String codCupon="";
      if(existCuponesRegalos(Dniaux)){//VERIFICA
          if (generarCupones(vTipo,dni)){//GENERA
           obtieneCuponesRegalo(cuponesRegalo,dni);//OBTIENE
           
               if(cuponesRegalo.size()>0){
                   FarmaUtility.showMessage(this,"Se van a generar cupones de regalo.\nNo olvide recogerlos.",cmbTipo);
                    for(int i=0;i<cuponesRegalo.size();i++){
                    codCupon=((String) ((ArrayList) cuponesRegalo.get(i)).get(0)).trim();
                      UtilityCaja.imprimeCuponRegalo(this,codCupon,dni);//IMPRIME
                    }
               }
          }else{
              
          }
      }else{
      
      }
  }
  
  /***
   * Valida primera generacion de cupones
   * @AUTHOR JCORTEZ
   * @SINCE 18.08.09
   * */
  private boolean existCuponesRegalos(String Dniaux){
      String exist="";
      boolean valor=false;
          try{
          exist=DBControlIngreso.existCuponRegalo(Dniaux);
             if(exist.equalsIgnoreCase("N"))
             valor=true;
          }catch (SQLException e)
          {
              log.error("",e);
              FarmaUtility.showMessage(this,"Ha ocurrido un error al obtener cupones regalo .\n" + e.getMessage(), txtDni);
          }
      return valor;
  
  }
  
}