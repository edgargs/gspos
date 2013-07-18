package mifarma.ptoventa.inventario;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import mifarma.common.FarmaConnectionRemoto;

public class DlgTransferenciasVer extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */

  Frame myParentFrame;
  FarmaTableModel tableModel;
  String vEstadoNota;
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnllHeader1 = new JPanelHeader();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrListaProductos = new JScrollPane();
  private JTable tblListaProductos = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelWhite lblDestino_T = new JLabelWhite();
  private JLabelWhite lblMotivo_T = new JLabelWhite();
  private JLabelWhite lblFechaEmision_T = new JLabelWhite();
  private JButtonLabel btnRelacionProductos = new JButtonLabel();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabelWhite lblFechaEmision = new JLabelWhite();
  private JLabelWhite lblTipo_T = new JLabelWhite();
  private JLabelWhite lblNoTransferencia = new JLabelWhite();
  private JLabelWhite lblFechaEmision_T1 = new JLabelWhite();
  private JLabelWhite lblTipo = new JLabelWhite();
  private JLabelWhite lblDestino = new JLabelWhite();
  private JLabelWhite lblEstado = new JLabelWhite();
  private JLabelFunction lblF10 = new JLabelFunction();
  private JButtonLabel lblCantProductos = new JButtonLabel();
  private JButtonLabel lblCantProductosT = new JButtonLabel();

  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgTransferenciasVer()
  {
    this(null, "", false);
  }

  public DlgTransferenciasVer(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
      FarmaUtility.centrarVentana(this);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /* ************************************************************************ */
	/*                                  METODO jbInit                           */
	/* ************************************************************************ */

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(705, 422));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Ver Transferencia de Productos");
    this.setDefaultCloseOperation(0);
    this.addWindowListener(new java.awt.event.WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }

        public void windowClosing(WindowEvent e)
        {
          this_windowClosing(e);
        }
      });
    pnllHeader1.setBounds(new Rectangle(10, 10, 680, 80));
    pnlTitle1.setBounds(new Rectangle(10, 100, 680, 25));
    scrListaProductos.setBounds(new Rectangle(10, 125, 680, 220));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(600, 355, 85, 20));
    lblDestino_T.setText("Destino");
    lblDestino_T.setBounds(new Rectangle(15, 50, 50, 15));
    lblMotivo_T.setText("Estado:");
    lblMotivo_T.setBounds(new Rectangle(360, 50, 45, 15));
    lblFechaEmision_T.setText("F. Emisión:");
    lblFechaEmision_T.setBounds(new Rectangle(195, 15, 70, 15));
    btnRelacionProductos.setText("Relacion de Productos a Transferir");
    btnRelacionProductos.setBounds(new Rectangle(5, 5, 205, 15));
    btnRelacionProductos.setMnemonic('R');
    btnRelacionProductos.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnRelacionProductos_keyPressed(e);
        }
      });
    btnRelacionProductos.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionProductos_actionPerformed(e);
        }
      });
    lblF2.setText("[ F2 ] Anular");
    lblF2.setBounds(new Rectangle(15, 355, 85, 20));
    lblFechaEmision.setBounds(new Rectangle(270, 15, 70, 15));
    lblTipo_T.setText("Tipo");
    lblTipo_T.setBounds(new Rectangle(375, 10, 35, 20));
    lblNoTransferencia.setBounds(new Rectangle(130, 15, 60, 15));
    lblFechaEmision_T1.setText("No. Transferencia:");
    lblFechaEmision_T1.setBounds(new Rectangle(15, 15, 105, 15));
    lblTipo.setBounds(new Rectangle(410, 10, 125, 20));
    lblDestino.setBounds(new Rectangle(75, 50, 150, 15));
    lblEstado.setBounds(new Rectangle(435, 45, 125, 20));
    lblF10.setBounds(new Rectangle(110, 355, 135, 20));
    lblF10.setText("[ F10 ] Imprimir");
    lblCantProductos.setText("Cantidad de Productos");
    lblCantProductos.setBounds(new Rectangle(475, 5, 130, 15));
    lblCantProductosT.setBounds(new Rectangle(615, 5, 45, 15));
    scrListaProductos.getViewport().add(tblListaProductos, null);
    jContentPane.add(lblF10, null);
    jContentPane.add(lblF2, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(scrListaProductos, null);
    pnlTitle1.add(lblCantProductosT, null);
    pnlTitle1.add(lblCantProductos, null);
    pnlTitle1.add(btnRelacionProductos, null);
    jContentPane.add(pnlTitle1, null);
    pnllHeader1.add(lblEstado, null);
    pnllHeader1.add(lblDestino, null);
    pnllHeader1.add(lblTipo, null);
    pnllHeader1.add(lblFechaEmision_T1, null);
    pnllHeader1.add(lblNoTransferencia, null);
    pnllHeader1.add(lblTipo_T, null);
    pnllHeader1.add(lblFechaEmision, null);
    pnllHeader1.add(lblFechaEmision_T, null);
    pnllHeader1.add(lblDestino_T, null);
    pnllHeader1.add(lblMotivo_T, null);
    jContentPane.add(pnllHeader1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initTable();
    cargarDetalle();
    mostrarF2();
    FarmaVariables.vAceptar = false;
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
    tableModel = new FarmaTableModel(ConstantsInventario.columnsListaProductosTransferenciasVer,ConstantsInventario.defaultValuesListaProductosTransferenciasVer,0);
    FarmaUtility.initSimpleList(tblListaProductos,tableModel,ConstantsInventario.columnsListaProductosTransferenciasVer);
  }
  
  private void cargaListaProductos()
  {
    try
    {
      DBInventario.cargaDetalleTransferencia(tableModel,VariablesInventario.vNumNota);
    }catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de productos : \n" + sql.getMessage(),btnRelacionProductos);
    }
  }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnRelacionProductos_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(btnRelacionProductos);
  }

  private void btnRelacionProductos_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(btnRelacionProductos);  
    lblCantProductosT.setText(""+tblListaProductos.getRowCount());
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,null,0);
    
    if(e.getKeyCode() == KeyEvent.VK_F2)
    {
        funcion_F2();
    }else if(e.getKeyCode() == KeyEvent.VK_F10)
    {
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,btnRelacionProductos);
      else
      if(validaGuias())
        {
          if (FarmaUtility.rptaConfirmDialog(this, "¿Está seguro de reimprimir?")) 
          {
            FarmaVariables.vAceptar=false;
            DlgListaImpresorasInv dlgListaImpresorasInv=new DlgListaImpresorasInv(myParentFrame,"",true);
            dlgListaImpresorasInv.setVisible(true);          
      
            if(!FarmaVariables.vAceptar){
              return;
            }          
            if(reimprimir())
            {
              //FarmaUtility.showMessage(this, "Guías impresas satisfactoriamente!", btnRelacionProductos);
              cerrarVentana(true);
            }  
          }
        }    
     
    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
  }

  private void cerrarVentana(boolean pAceptar)
	{
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
    this.dispose();
  }
  
  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */

  private void cargarDetalle()
  {
    try
    {
      ArrayList array = new ArrayList();
      array = DBInventario.cargaCabeceraTransferencia(VariablesInventario.vNumNota);
      //System.out.println(VariablesInventario.vNumNota);
      array = (ArrayList)array.get(0);
      lblNoTransferencia.setText(VariablesInventario.vNumNota);
      lblFechaEmision.setText(array.get(0).toString());
      lblTipo.setText(array.get(1).toString());
      lblDestino.setText(array.get(2).toString());
      
      vEstadoNota = array.get(3).toString();
      //if(vEstadoNota.equals(FarmaConstants.INDICADOR_N))
      //  lblEstado.setText("ANULADO");
      lblEstado.setText(VariablesInventario.vEstadoNota);
      
      //JSANTIVANEZ 21.10.2010
      if ( lblEstado.getText().equalsIgnoreCase("LOCAL") )
        if ( vEstadoNota.equalsIgnoreCase("L") )
            lblF2.setVisible(false);
        else if(vEstadoNota.equalsIgnoreCase("C"))            
            lblF2.setVisible(false);
        
      cargaListaProductos();
    }catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al obtener datos de la transferencia :\n" + sql.getMessage(),btnRelacionProductos);
    }
  }

  private boolean anular()
  {
    boolean retorno=false;
    try
    {   
        DBInventario.grabaInicioFinAnulaTransferencia(VariablesInventario.vNumNota,"I"); //  10122009 graba inicio de anulacion de transferencia
        if(VariablesInventario.vTipoNotaOrigen.equalsIgnoreCase(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL)){
            if (vEstadoNota.equalsIgnoreCase("P") ){
                DBInventario.anularTransferencia_02(VariablesInventario.vNumNota);
                DBInventario.grabaInicioFinAnulaTransferencia(VariablesInventario.vNumNota,"F");//  10122009 graba fin de anulacion de transferencia
                FarmaUtility.aceptarTransaccion();
                retorno = true;
            }else if (vEstadoNota.equalsIgnoreCase("L") ){
                
                FarmaConnectionRemoto.closeConnection();
                String vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                if (vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {                                  
                    DBInventario.anularTransferenciaMatriz(VariablesInventario.vNumNota);               
                    DBInventario.anularTransferencia_02(VariablesInventario.vNumNota);
                    DBInventario.grabaInicioFinAnulaTransferencia(VariablesInventario.vNumNota,"F");//  10122009 graba fin de anulacion de transferencia
                    FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_S);
                    FarmaUtility.aceptarTransaccion();
                    
                    retorno = true;
                }else{
                    retorno = false;
                    FarmaUtility.showMessage(this, 
                                             "No hay linea con matriz.\n Inténtelo nuevamente si el problema persiste comuníquese con el Operador de Sistemas.", 
                                             tblListaProductos);                               
                }
            }   
            
            }else{
                DBInventario.anularTransferencia_02(VariablesInventario.vNumNota);
                DBInventario.grabaInicioFinAnulaTransferencia(VariablesInventario.vNumNota,"F");//  10122009 graba fin de anulacion de transferencia
                FarmaUtility.aceptarTransaccion();
                retorno = true;
            }        
 
    }catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      if(sql.getErrorCode() == 20002)
        FarmaUtility.showMessage(this,"La Fracción Actual no permite anular esta Guia.\n"+sql,btnRelacionProductos);
      else if (sql.getErrorCode() == 20000)//  27112009
        FarmaUtility.showMessage(this,"La transferencia ya ha sido aceptada por el local destino.\n"+sql,btnRelacionProductos);
      else if (sql.getErrorCode() == 20003)//  27112009
          FarmaUtility.showMessage(this,"La existencia de productos congelado no permite anular esta Guia.\n"+sql,btnRelacionProductos);
      else
      {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ha ocurrido un error al anular la transferencia : \n"+sql.getMessage(),btnRelacionProductos);
      }
      retorno = false;
    } finally {          	
        FarmaConnectionRemoto.closeConnection();
    }
    
    
    return retorno;
  }
  
  private boolean verificaComprobante()
  {
        boolean retorno = false;
        
      if (VariablesInventario.vTipoNotaOrigen.equalsIgnoreCase(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL)){
          //aca entra cuando son del tipo LOCAL
          if (vEstadoNota.equals("P") || vEstadoNota.equals("A") || 
              vEstadoNota.equals("X") || 
              vEstadoNota.equals("R")) //Se puede anular en estado pendiente
          {
              try {
                  String estadoProcesado = 
                      DBInventario.getEstadoProcesoSap(VariablesInventario.vNumNota, 
                                                       ConstantsPtoVenta.TIP_NOTA_SALIDA);
                  if (estadoProcesado.equals(FarmaConstants.INDICADOR_N)) {
                      retorno = true;
                  } else //(estadoProcesado.equals(FarmaConstants.INDICADOR_S))
                  {
                      if (VariablesInventario.vTipoNota.equals(ConstantsInventario.TIP_NOTA_TRANS) && 
                          VariablesInventario.vTipoNotaOrigen.equals(ConstantsInventario.TIP_NOTA_ORIGEN_TRANS)) {
                          if (cargaLoginOper())
                              retorno = true;
                          else {
                              retorno = false;
                              FarmaUtility.showMessage(this, 
                                                       "Esta Transferencia ha sido procesado por la interface SAP.\nSolo un Operador de Sistemas puede anularla.", 
                                                       btnRelacionProductos);
                          }
                      } else {
                          retorno = false;
                          FarmaUtility.showMessage(this, 
                                                   "Esta Transferencia ha sido procesado por la interface SAP. No puede ser anulada.", 
                                                   btnRelacionProductos);
                      }
                  }
              } catch (SQLException e) {
                  retorno = false;
                  e.printStackTrace();
                  FarmaUtility.showMessage(this, 
                                           "Ha ocurrido un error al consultar estado.\n" +
                          e, btnRelacionProductos);
              }
          } else if(vEstadoNota.equals("C")) //  27112009 SE COMENTO PARA ANULAR TRANSFERENCIAS, YA QUE SE CAMBIO LA TRANSFERENCIA A CONFIRMACION AUTOMÁTICA, DONDE EL ESTADO LO DEJA YA EN 'L'
          {
              //  26.10.2010
          retorno = false;
          //FarmaUtility.showMessage(this,"Esta transferencia ha sido confirmada.",btnRelacionProductos);
          //FarmaUtility.showMessage(this,"Está transferencia no puede ser Anulada.",btnRelacionProductos);
          } /*else if(vEstadoNota.equals("M") || vEstadoNota.equals("L"))  //  27112009 SE COMENTO PARA ANULAR TRANSFERENCIAS , YA QUE SE CAMBIO LA TRANSFERENCIA A CONFIRMACION AUTOMÁTICA, DONDE EL ESTADO LO DEJA YA EN 'L'
          {
          retorno = false;
          FarmaUtility.showMessage(this,"Esta transferencia ha sido enviada al local destino.",btnRelacionProductos);
          }*/ 
          else if (vEstadoNota.equals("N")) {
              retorno = false;
              FarmaUtility.showMessage(this, 
                                       "Esta transferencia ya está anulada.", 
                                       btnRelacionProductos);
          } 
          else
              //retorno = true;   
              //JSANTIVANEZ 21.10.2010
              if(vEstadoNota.equals("L")){
                  //FarmaUtility.showMessage(this,"No puede realizar esta acción.",btnRelacionProductos);
                System.out.println("No puede realizar esta acción.");
              }else
                  retorno = true;   
      }else {
            //aca entra cuando son del tipo MATRIZ
            if (vEstadoNota.equals("P") || vEstadoNota.equals("A") || 
                vEstadoNota.equals("X") || 
                vEstadoNota.equals("R")) //Se puede anular en estado pendiente
            {
                try {
                    String estadoProcesado = 
                        DBInventario.getEstadoProcesoSap(VariablesInventario.vNumNota, 
                                                         ConstantsPtoVenta.TIP_NOTA_SALIDA);
                    if (estadoProcesado.equals(FarmaConstants.INDICADOR_N)) {
                        retorno = true;
                    } else //(estadoProcesado.equals(FarmaConstants.INDICADOR_S))
                    {
                        if (VariablesInventario.vTipoNota.equals(ConstantsInventario.TIP_NOTA_TRANS) && 
                            VariablesInventario.vTipoNotaOrigen.equals(ConstantsInventario.TIP_NOTA_ORIGEN_TRANS)) {
                            if (cargaLoginOper())
                                retorno = true;
                            else {
                                retorno = false;
                                FarmaUtility.showMessage(this, 
                                                         "Esta Transferencia ha sido procesado por la interface SAP.\nSolo un Operador de Sistemas puede anularla.", 
                                                         btnRelacionProductos);
                            }
                        } else {
                            retorno = false;
                            FarmaUtility.showMessage(this, 
                                                     "Esta Transferencia ha sido procesado por la interface SAP. No puede ser anulada.", 
                                                     btnRelacionProductos);
                        }
                    }
                } catch (SQLException e) {
                    retorno = false;
                    e.printStackTrace();
                    FarmaUtility.showMessage(this, 
                                             "Ha ocurrido un error al consultar estado.\n" +
                            e, btnRelacionProductos);
                }
            } else if(vEstadoNota.equals("C")) 
            {
            retorno = false;
                FarmaUtility.showMessage(this, 
                                         "Esta transferencia ha sido confirmada.", 
                                         btnRelacionProductos);
            } else if(vEstadoNota.equals("M") || vEstadoNota.equals("L"))  
            {
            retorno = false;
                FarmaUtility.showMessage(this, 
                                         "Esta transferencia ha sido enviada al local destino.", 
                                         btnRelacionProductos);
            } else if (vEstadoNota.equals("N")) {
                retorno = false;
                FarmaUtility.showMessage(this, 
                                         "Esta transferencia ya está anulada.", 
                                         btnRelacionProductos);
            } else
                retorno = true; 
        }
       
        
    return retorno;
    
  }
  
  private boolean validaGuias()
  {
    boolean retorno;
    if(!vEstadoNota.equals(FarmaConstants.INDICADOR_N))
    {
      try
      {
        ArrayList secGuia = DBInventario.getSecuencialGuia(VariablesInventario.vNumNota,"");
        if(secGuia.size() == 0)
        {
          retorno = false;
          FarmaUtility.showMessage(this,"No existe guías pendientes de impresión.",btnRelacionProductos);
        }else
        {
          retorno = true;
        }
      }catch(SQLException e)
      {
        retorno = false;
        e.printStackTrace();
        FarmaUtility.showMessage(this,"No se ha podido determinar las guías pendientes.\n"+e,btnRelacionProductos);
      }
    }else
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Esta transferencia está anulada.",btnRelacionProductos);
    }
    return retorno;
  }
  
  private boolean reimprimir()
  {
    boolean retorno = false;
    if(cargaCabecera())
      try
      {
        //FarmaUtility.liberarTransaccion();
        //Imprimir Comprobantes
        VariablesInventario.vNumNotaEs = VariablesInventario.vNumNota;
        DBInventario.grabaInicioFinGuiasTransferencia(VariablesInventario.vNumNotaEs,"I");//  10122009 registra fecha inicio de generar guias e imprimirlas  
        UtilityInventario.procesoImpresionComprobante(this, tblListaProductos);
        DBInventario.grabaInicioFinGuiasTransferencia(VariablesInventario.vNumNotaEs,"F");//  10122009 registra fecha fin de generar guias e imprimirlas  
        FarmaUtility.aceptarTransaccion();//  10122009   
        retorno = true;
      }/*catch(SQLException sql)
      {
        FarmaUtility.liberarTransaccion();
        sql.printStackTrace();
        FarmaUtility.showMessage(this,"Ha ocurrido un error al grabar la transferencia.\n"+sql.getMessage(),btnRelacionProductos);
        retorno = false;
      }*/catch(Exception exc)
      {
        FarmaUtility.liberarTransaccion();
        exc.printStackTrace();
        FarmaUtility.showMessage(this, "Error en la aplicacion al grabar la transferencia.\n"+exc.getMessage(),btnRelacionProductos);
        retorno = false;
      }
    return retorno;
  }
  
  private boolean cargaCabecera()
  {
    boolean retorno;
    try
    {
      ArrayList cabecera = DBInventario.getCabeceraGuiaTrans(VariablesInventario.vNumNota);
      if(cabecera.size() > 0)
      {
        cabecera = (ArrayList)cabecera.get(0);
        
        VariablesInventario.vDestino_Transf = cabecera.get(0).toString();
        VariablesInventario.vRucDestino_Transf = cabecera.get(1).toString();
        VariablesInventario.vDirecDestino_Transf = cabecera.get(2).toString();
        VariablesInventario.vTransportista_Transf = cabecera.get(3).toString();
        VariablesInventario.vRucTransportista_Transf = cabecera.get(4).toString();
        VariablesInventario.vCodDestino_Transf = cabecera.get(5).toString();
        
        retorno = true;
      }else
      {
        retorno = false;
        FarmaUtility.showMessage(this,"No se cargó los datos de cabecera de la guía.\n",btnRelacionProductos);    
      }
    }catch(SQLException e)
    {
      e.printStackTrace();
      retorno = false;
      FarmaUtility.showMessage(this,"Error al obtener los datos de cabecera de la guía.\n"+e,btnRelacionProductos);
    }
    
    return retorno;
  }
  
  private boolean cargaLoginOper()
  {
    String numsec = FarmaVariables.vNuSecUsu ;
    String idusu = FarmaVariables.vIdUsu ;
    String nomusu = FarmaVariables.vNomUsu ;
    String apepatusu = FarmaVariables.vPatUsu ;
    String apematusu = FarmaVariables.vMatUsu ;
    
    try{
      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
      dlgLogin.setVisible(true);
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
    } catch (Exception e)
    {
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
      FarmaVariables.vAceptar = false;
      e.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
    }
    return FarmaVariables.vAceptar;
  }
  
  private void funcion_F2(){
      if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ) {
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,btnRelacionProductos);
      else
          //FarmaUtility.showMessage(this,"No puede realizar esta acción.",btnRelacionProductos);
      if(verificaComprobante())
      {
        if(FarmaUtility.rptaConfirmDialog(this,"¿Está seguro de anular la Transferencia?"))
        {
          if(anular())
          {
            FarmaUtility.showMessage(this,"Transferencia fue anulada.",btnRelacionProductos);
            cerrarVentana(true);
          }
        }
      }
      }
      else{
      FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opción",null);
      }
  }
  
  private void mostrarF2(){
      if ( lblEstado.getText().equalsIgnoreCase("LOCAL") ){
        if ( vEstadoNota.equalsIgnoreCase("L") )
            lblF2.setVisible(false);
      }
      if ( lblEstado.getText().equalsIgnoreCase("CONFIRMADO") ){
        if( vEstadoNota.equalsIgnoreCase("C"))            
              lblF2.setVisible(false);
      } 
  }
}