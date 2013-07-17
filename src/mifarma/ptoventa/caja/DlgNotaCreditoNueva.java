package mifarma.ptoventa.caja;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;

import java.sql.*;
import java.util.*;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import java.awt.Rectangle;
import com.gs.mifarma.componentes.JPanelTitle;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JButtonLabel;
import javax.swing.JComboBox;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Font;
import mifarma.common.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

import java.text.SimpleDateFormat;

import mifarma.ptoventa.caja.reference.*;
import mifarma.ptoventa.reference.*;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;


public class DlgNotaCreditoNueva extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */

  Frame myParentFrame;
  FarmaTableModel tableModel;
  private static final Log log = LogFactory.getLog(DlgAnularPedidoComprobante.class);
  private final static int COL_COD_PROD = 0;
  private final static int COL_DESC_PROD = 1;
  private final static int COL_UND_MED = 2;
  private final static int COL_NOM_LAB = 3;
  private final static int COL_CANT_DISP = 4;
  private final static int COL_PREC_VTA = 5;
  private final static int COL_CANT_NC = 6;
  private final static int COL_FRAC_VTA = 7;
  private final static int COL_PREC_LTA = 8;
  private final static int COL_VAL_IGV = 9;
  private final static int COL_SEC_DET = 10;
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrListaProductos = new JScrollPane();
  private JTable tblListaProductos = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JButtonLabel btnRelacionProductos = new JButtonLabel();
  private JLabelFunction lblF11 = new JLabelFunction();

    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgNotaCreditoNueva()
  {
    this(null, "", false);
  }

  public DlgNotaCreditoNueva(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(705, 346));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Nueva Nota Credito");
    this.setDefaultCloseOperation(0);
    this.addWindowListener(new WindowAdapter()
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
    pnlTitle1.setBounds(new Rectangle(10, 15, 680, 25));
    scrListaProductos.setBounds(new Rectangle(10, 40, 680, 240));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(590, 290, 85, 20));
    btnRelacionProductos.setText("Relacion de Productos de Nota Credito");
    btnRelacionProductos.setBounds(new Rectangle(5, 5, 235, 15));
    btnRelacionProductos.setMnemonic('R');
    btnRelacionProductos.setActionCommand("Relacion de Productos de Nota Credito");
    btnRelacionProductos.addKeyListener(new KeyAdapter()
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
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(445, 290, 135, 20));
        jContentPane.add(lblF11, null);
    jContentPane.add(lblEsc, null);
    scrListaProductos.getViewport().add(tblListaProductos, null);
    jContentPane.add(scrListaProductos, null);
    pnlTitle1.add(btnRelacionProductos, null);
    jContentPane.add(pnlTitle1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initTable();
    FarmaVariables.vAceptar = false;
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
    tableModel = new FarmaTableModel(ConstantsCaja.columnsListaProductosNotaCreditoNueva,ConstantsCaja.defaultValuesListaProductosNotaCreditoNueva,0);
    FarmaUtility.initSimpleList(tblListaProductos,tableModel,ConstantsCaja.columnsListaProductosNotaCreditoNueva);
    cargaListaProductos();
  }
  
  public void cargaListaProductos()
  {
    try
    {
      DBCaja.getListaDetalleNotaCredito(tableModel,VariablesCaja.vNumPedVta_Anul,VariablesCaja.vTipComp_Anul,VariablesCaja.vNumComp_Anul);
    }catch(SQLException e)
    {
      e.printStackTrace();
      FarmaUtility.showMessage(this,"Error al listar detalle nota credito :\n" + e.getMessage(),null);
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
    
    if(e.getKeyCode() == KeyEvent.VK_F11)
    {
      if(validaDatos())
      {
    	//Modificado por FRAMIREZ 29.02.2012 se agrego para generar la nota credito del convenio BTLMF
        if (FarmaUtility.rptaConfirmDialog(this, "¿Está seguro de generar la Nota Credito?")) 
        {

        	 boolean estaGrabado = false;

        	  //ini-Agregado Por FRAMIREZ 28.02.2012 metodo que genera nota credito convenio BTLMF
              Map vtaPedido = (Map)UtilityConvenioBTLMF.obtieneConvenioXpedido(VariablesCaja.vNumPedVta_Anul,this);
              String indConvenioBTLMF = (String)vtaPedido.get("IND_CONV_BTL_MF");
              boolean esConvenioBTLMF = UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null);

              if(esConvenioBTLMF && indConvenioBTLMF != null && indConvenioBTLMF.equals("S"))
              {
            	  boolean esCompCredito = UtilityConvenioBTLMF.esCompCredito(this);
                  log.debug("Es comprobante Crédito "+ esCompCredito);

                  if (esCompCredito)
                  {
	                 String indConexionRac = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_RAC, FarmaConstants.INDICADOR_S);
	             	 System.out.println("indConexionRac :"+indConexionRac);
	             	 if (indConexionRac.equals("S"))
	             	 {
	             		String pedidoAnuladoRac ="";
                        try
                        {
	             		  pedidoAnuladoRac =  DBConvenioBTLMF.anularPedidoRac(null, FarmaConstants.INDICADOR_N);
                        }
                        catch(SQLException SQL)
                        {
                           estaGrabado = false;
                           SQL.printStackTrace();
                        }

                		log.debug("Pedido Anulado en el Rac?"+pedidoAnuladoRac);

                		 if (pedidoAnuladoRac.equals("S"))
                		 {
                				log.debug("Pedido Anulado en el 22?"+pedidoAnuladoRac);

                			  estaGrabado = grabarBTLMF();

                			  log.debug("graboo?"+estaGrabado);
                		 }
                		 else
                		 {
                			 FarmaUtility.showMessage(this,"No se pudo anular el pediddo Convenio en el RAC",tblListaProductos);
                		 }

	             	 }
	             	else
	           		 {
	           			FarmaUtility.showMessage(this,"No puede anular el pedido, porque no existe una conexion con el RAC",tblListaProductos);
	           		 }

                  }
                  else
                  {

           	   			estaGrabado = grabarBTLMF();
                  }

              }
              else
              {
           	    estaGrabado = grabar();
              }


              log.debug("Genero Nota credito satisfactoriamente:"+estaGrabado);

              //Fin
	          if(estaGrabado)
          {
              
            FarmaUtility.showMessage(this, "¡Nota Credito generada satisfactoriamente!", btnRelacionProductos);
            //JCHAVEZ 10.07.2009.sn
	  	          if(esConvenioBTLMF && indConvenioBTLMF != null && indConvenioBTLMF.equals("S"))
	              {
	  	            imprimeTicketBTLMF();
	              }
	  	          else
	  	          {
            imprimeTicket();
	  	          }
            //JCHAVEZ 10.07.2009.en
            
            cerrarVentana(true);
          }
          
         
         
        }
      }
    
    }else if(e.getKeyCode() == KeyEvent.VK_ENTER && (VariablesCaja.vTipComp.equals(ConstantsVentas.TIPO_COMP_FACTURA)))
    {
      if(tblListaProductos.getSelectedRow()>-1)
      {
        cargarCabecera();
        DlgNotaCreditoIngresoCantidad DlgNotaCreditoIngresoCantidad = new DlgNotaCreditoIngresoCantidad(myParentFrame,"",true);
        DlgNotaCreditoIngresoCantidad.setVisible(true);
        if(FarmaVariables.vAceptar)
        {
          actualizarProducto();
          FarmaVariables.vAceptar = false;
        }
      }else
        FarmaUtility.showMessage(this,"Debe seleccionar un producto",btnRelacionProductos);
      
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
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

  private void cargarCabecera()
  {
     int vFila = tblListaProductos.getSelectedRow();
    VariablesCaja.vCodProd_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos,vFila,COL_COD_PROD);
    VariablesCaja.vNomProd_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos,vFila,COL_DESC_PROD);
    VariablesCaja.vUnidMed_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos,vFila,COL_UND_MED);
    VariablesCaja.vNomLab_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos,vFila,COL_NOM_LAB);
    VariablesCaja.vCant_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos,vFila,COL_CANT_DISP);
    VariablesCaja.vValFrac_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos,vFila,COL_FRAC_VTA);
    VariablesCaja.vCantIng_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos,vFila,COL_CANT_NC);
  }
  
  private void actualizarProducto()
  {
    int vFila = tblListaProductos.getSelectedRow();
    tblListaProductos.setValueAt(VariablesCaja.vCantIng_Nota,vFila,COL_CANT_NC);
    tblListaProductos.repaint();
  }
  
  private boolean validaDatos()
  {
    boolean retorno = true;
    if(tblListaProductos.getRowCount()==0)
    {
      FarmaUtility.showMessage(this,"No ha ingresado productos a esta Nota Credito.",btnRelacionProductos);
      retorno = false;
    }else if(FarmaUtility.sumColumTable(tblListaProductos,COL_CANT_NC)==0 && (VariablesCaja.vTipComp.equals(ConstantsVentas.TIPO_COMP_FACTURA)))
    {
      FarmaUtility.showMessage(this,"No ha ingresado productos a esta Nota Credito.",btnRelacionProductos);
      retorno = false;
    }
    return retorno;
  }
  

  
  private boolean grabarBTLMF()
  {
    boolean retorno;
    //Valores totales
    double totalBruto = 0.00;
    double totalDscto = 0.00;
    double totalIGV = 0.00;
    double totalNeto = 0.00;
    double totalNetoAux = 0.00;
    //double redondeo = 0.00;
    int cantidad = 0;
    String pDatosDel = FarmaConstants.INDICADOR_N;
    try
    {
      pDatosDel = DBCaja.getDatosPedDelivery(VariablesCaja.vNumPedVta_Anul);
        //Esta decision fue tomada por gerencia
        //dubilluz 02.12.2008
      DBCaja.activarCuponesUsados(VariablesCaja.vNumPedVta_Anul);

      System.out.println("-----");
      DlgDetalleAnularPedido dlgDetalleAnularPedido = new DlgDetalleAnularPedido(myParentFrame,"",true,true);
      dlgDetalleAnularPedido.setVisible(true);

     if(FarmaVariables.vAceptar)
      {
          System.out.println("Valor de vMotivoAnulacion dentro del metodo grabar: " + VariablesCaja.vMotivoAnulacion );


        String tipoCambio = FarmaSearch.getTipoCambio(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA))+"";
	    String numera = DBCaja.agregarCabeceraNotaCredito(VariablesCaja.vNumPedVta_Anul,tipoCambio,VariablesCaja.vMotivoAnulacion);


          System.out.println("Numera JCORTEZ: " + numera);

        for(int i=0;i<tblListaProductos.getRowCount();i++)
        {

          String codProd = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_COD_PROD);
          String secPedDet = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_SEC_DET);
          String precLta = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_PREC_LTA);
          String precVta = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_PREC_VTA);
          String valIgv = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_VAL_IGV);
          String cantDisp = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_CANT_DISP);
          String cantNC = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_CANT_NC);

            //JCORTEZ 23.04.2009
            //Se considera grabar del igual forma tipo de comprobante TICKET al generar el detalle de la nota de credito
          if(VariablesCaja.vTipComp.equals(ConstantsVentas.TIPO_COMP_BOLETA)||
              VariablesCaja.vTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET))
          {
            //ERIOS 29.05.2008 Se devuelve todos el detalle del comprobante
            cantidad = Integer.parseInt(cantDisp.trim());

            totalBruto += FarmaUtility.getDecimalNumber(precLta) * cantidad;
            totalNetoAux = FarmaUtility.getDecimalNumber(precVta) * cantidad;
            totalNeto += FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNetoAux,2));
            totalIGV   += (FarmaUtility.getDecimalNumber(precVta) - (FarmaUtility.getDecimalNumber(precVta) / ( 1 + (FarmaUtility.getDecimalNumber(valIgv) / 100)))) * cantidad;

            DBCaja.agregarDetalleNotaCredito(VariablesCaja.vNumPedVta_Anul,numera,codProd,cantDisp,FarmaUtility.formatNumber(totalNetoAux),secPedDet);
          }else if(!cantNC.equals("0"))
          {
            //ERIOS 29.05.2008 Solo se devuelve aquellos registros se trabajo.
            cantidad = Integer.parseInt(cantNC);

            totalBruto += FarmaUtility.getDecimalNumber(precLta) * cantidad;
            totalNetoAux = FarmaUtility.getDecimalNumber(precVta) * cantidad;
            totalNeto += FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNetoAux,2));
            totalIGV   += (FarmaUtility.getDecimalNumber(precVta) - (FarmaUtility.getDecimalNumber(precVta) / ( 1 + (FarmaUtility.getDecimalNumber(valIgv) / 100)))) * cantidad;

            DBCaja.agregarDetalleNotaCredito(VariablesCaja.vNumPedVta_Anul,numera,codProd,cantNC,FarmaUtility.formatNumber(totalNetoAux),secPedDet);
          }
        }

        //Actualiza Montos Cabecera
        totalBruto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalBruto,2));
        totalNeto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNeto,2));
        totalIGV = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalIGV,2));
        totalDscto = (totalBruto - totalNeto);
        totalDscto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalDscto,2));

        DBCaja.actualizaMontosCabeceraNC(numera,totalBruto,totalNeto,totalIGV,totalDscto);
		//COBRA NOTA DE CREDITO
		cobrarPedido(numera);


	    FarmaUtility.aceptarTransaccion();

	    retorno = true;

      }
      else
      {
        retorno = false;
      }
    }catch(SQLException e)
    {
      e.printStackTrace();
      FarmaUtility.liberarTransaccion();
      FarmaUtility.showMessage(this,"Error al grabar nota de credito :\n" + e.getMessage(),btnRelacionProductos);
      retorno = false;
    }

    return retorno;
  }


  private boolean grabar()
  {
    boolean retorno;
    //Valores totales
    double totalBruto = 0.00;
    double totalDscto = 0.00;
    double totalIGV = 0.00;
    double totalNeto = 0.00;
    double totalNetoAux = 0.00;
    //double redondeo = 0.00;
    int cantidad = 0;
    String pDatosDel = FarmaConstants.INDICADOR_N;
    try
    {
      pDatosDel = DBCaja.getDatosPedDelivery(VariablesCaja.vNumPedVta_Anul);
        //Esta decision fue tomada por gerencia
        //dubilluz 02.12.2008
        DBCaja.activarCuponesUsados(VariablesCaja.vNumPedVta_Anul);
        
        System.out.println("-----"); 
      DlgDetalleAnularPedido dlgDetalleAnularPedido = new DlgDetalleAnularPedido(myParentFrame,"",true,true);
      dlgDetalleAnularPedido.setVisible(true);

    if(FarmaVariables.vAceptar)
      {
          System.out.println("Valor de vMotivoAnulacion dentro del metodo grabar: " + VariablesCaja.vMotivoAnulacion );
      
            
        String tipoCambio = FarmaSearch.getTipoCambio(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA))+"";
	String numera = DBCaja.agregarCabeceraNotaCredito(VariablesCaja.vNumPedVta_Anul,tipoCambio,VariablesCaja.vMotivoAnulacion);
	
        
          System.out.println("Numera JCORTEZ: " + numera);
          
        for(int i=0;i<tblListaProductos.getRowCount();i++)
        {   
            
          String codProd = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_COD_PROD);
          String secPedDet = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_SEC_DET);
          String precLta = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_PREC_LTA);
          String precVta = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_PREC_VTA);
          String valIgv = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_VAL_IGV);
          String cantDisp = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_CANT_DISP);
          String cantNC = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_CANT_NC);
            
            //JCORTEZ 23.04.2009 
            //Se considera grabar del igual forma tipo de comprobante TICKET al generar el detalle de la nota de credito
          if(VariablesCaja.vTipComp.equals(ConstantsVentas.TIPO_COMP_BOLETA)||
              VariablesCaja.vTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET)) 
          {
            //ERIOS 29.05.2008 Se devuelve todos el detalle del comprobante
            cantidad = Integer.parseInt(cantDisp.trim());
            
            totalBruto += FarmaUtility.getDecimalNumber(precLta) * cantidad;
            totalNetoAux = FarmaUtility.getDecimalNumber(precVta) * cantidad;
            totalNeto += FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNetoAux,2));
            totalIGV   += (FarmaUtility.getDecimalNumber(precVta) - (FarmaUtility.getDecimalNumber(precVta) / ( 1 + (FarmaUtility.getDecimalNumber(valIgv) / 100)))) * cantidad;
            
            DBCaja.agregarDetalleNotaCredito(VariablesCaja.vNumPedVta_Anul,numera,codProd,cantDisp,FarmaUtility.formatNumber(totalNetoAux),secPedDet);
          }else if(!cantNC.equals("0"))
          {
            //ERIOS 29.05.2008 Solo se devuelve aquellos registros se trabajo.
            cantidad = Integer.parseInt(cantNC);
            
            totalBruto += FarmaUtility.getDecimalNumber(precLta) * cantidad;
            totalNetoAux = FarmaUtility.getDecimalNumber(precVta) * cantidad;
            totalNeto += FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNetoAux,2));
            totalIGV   += (FarmaUtility.getDecimalNumber(precVta) - (FarmaUtility.getDecimalNumber(precVta) / ( 1 + (FarmaUtility.getDecimalNumber(valIgv) / 100)))) * cantidad;
            
            DBCaja.agregarDetalleNotaCredito(VariablesCaja.vNumPedVta_Anul,numera,codProd,cantNC,FarmaUtility.formatNumber(totalNetoAux),secPedDet);
          } 
        }
            
        //Actualiza Montos Cabecera
        totalBruto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalBruto,2));
        totalNeto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNeto,2));
        totalIGV = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalIGV,2));
        totalDscto = (totalBruto - totalNeto);
        totalDscto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalDscto,2));
        DBCaja.actualizaMontosCabeceraNC(numera,totalBruto,totalNeto,totalIGV,totalDscto);
	        
	//COBRA NOTA DE CREDITO        
	cobrarPedido(numera);
        FarmaUtility.aceptarTransaccion();
	//--Se anula los cupones en Matriz 
        //  04.09.2008 Dubilluz
        UtilityCaja.anulaCuponesPedido(VariablesCaja.vNumPedVta_Anul,
                                       this,
                                       btnRelacionProductos);
        
        //Activa los cupones en matriz
        //03.12.2008 dubilluz
        UtilityCaja.activaCuponesMatriz(VariablesCaja.vNumPedVta_Anul,this,btnRelacionProductos);          
          
        UtilityCaja.alertaPedidoDelivery(pDatosDel.trim());
	retorno = true;
        
      
        
      } 
      else
      {
        retorno = false;
      }
    }catch(SQLException e)
    {
      e.printStackTrace();
      FarmaUtility.liberarTransaccion();
      UtilityConvenioBTLMF.liberarTransaccionRempota(null,null,FarmaConstants.INDICADOR_S);
      FarmaUtility.showMessage(this,"Error al grabar nota de credito :\n" + e.getMessage(),btnRelacionProductos);
      retorno = false;
    }

    /*catch(Exception exc)
    {
      FarmaUtility.liberarTransaccion();
      exc.printStackTrace();
      FarmaUtility.showMessage(this, "Error en la aplicacion al grabar.\n"+exc.getMessage(),btnRelacionProductos);
      retorno = false;
    }*/
    return retorno;
  }
 
  private void cobrarPedido(String numera) throws SQLException
      {
    
    	VariablesCaja.vNumPedVta = numera;
    	VariablesVentas.vTip_Comp_Ped = ConstantsVentas.TIPO_COMP_NOTA_CREDITO;
        if(VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_NOTA_CREDITO))
          VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(ConstantsCaja.ITEMS_TOTAL_POR_NOTA_CREDITO);
        DBCaja.cobraNotaCredito(VariablesVentas.vTip_Comp_Ped);
        if(!UtilityCaja.validaAgrupacionComprobante(this, tblListaProductos))
        {
          FarmaUtility.liberarTransaccion();
          VariablesCaja.vIndPedidoCobrado = false;
          return;
        }
        VariablesCaja.vIndPedidoCobrado = true;
        //FarmaUtility.aceptarTransaccion();
        //FarmaUtility.showMessage(this, "Pedido Cobrado con Exito",tblListaProductos);
        UtilityCaja.procesoAsignacionComprobante(this, tblListaProductos);
     
  }
  

    private void imprimeTicket(){
        String caja_turno,ruta;
        try
        {
            //para agregar Fecha Creacion
            Date vFecImpr = new Date();
            String fechaImpresion;
                  
            String DATE_FORMAT = "yyyyMMdd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            // System.out.println("Today is " + sdf.format(vFecImpr));
            fechaImpresion =  sdf.format(vFecImpr);                

            ruta=DBCaja.ObtieneDirectorio();
            //ruta=ruta+"T_"+VariablesCaja.vNumPedVta_Anul+"_Anul";
            //JMIRANDA 08/07/09
            ruta=ruta+fechaImpresion+"_"+"T_"+VariablesCaja.vNumPedVta_Anul+"_Anul";
            
            caja_turno=DBCaja.obtieneCajaTurnoPedidoAnulado(VariablesCaja.vNumPedVta_Anul);
            String [] arrayDatos =  caja_turno.split("Ã");
            UtilityCaja.imprimeMensajeTicketAnulacion(VariablesCaja.vNumCaja, 
                                                      VariablesCaja.vNumTurnoCaja, 
                                                      VariablesCaja.vNumPedVta_Anul, 
                                                      "00", ruta+"_1.TXT", false, "S");
            //para montos inafectos
            UtilityCaja.imprimeMensajeTicketAnulacion(VariablesCaja.vNumCaja, 
                                                      VariablesCaja.vNumTurnoCaja, 
                                                      VariablesCaja.vNumPedVta_Anul, 
                                                      "01", ruta+"_2.TXT", false, "S");


            //Ya no va asi
            //UtilityCaja.imprimeMensajeTicketAnulacion(VariablesCaja.vNumCaja,VariablesCaja.vNumTurnoCaja,VariablesCaja.vNumPedVta_Anul,"00",ruta+"_1.TXT",true,"N");                  
            //para montos inafectos
            //UtilityCaja.imprimeMensajeTicketAnulacion(VariablesCaja.vNumCaja,VariablesCaja.vNumTurnoCaja,VariablesCaja.vNumPedVta_Anul,"01",ruta+"_2.TXT",true,"N");            
            //lblMensaje.setText("");  
            FarmaUtility.showMessage(this, "¡Imprimiendo comprobante de nota de credito!", btnRelacionProductos);
            /*
            if(VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
                if(!VariablesCaja.vDescripImpr.equalsIgnoreCase("X")||VariablesCaja.vDescripImpr.equalsIgnoreCase(""))
                  lblMensaje.setText("Recoger comprobante en : "+VariablesCaja.vDescripImpr);
            }*/
            FarmaUtility.showMessage(this,"El ticket se ha reimpreso con éxito .",null); 
        }
        catch(Exception e ){
            e.printStackTrace();
            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                          FarmaVariables.vCodLocal,
                                          //"dubilluz",
                                          VariablesPtoVenta.vDestEmailErrorAnulacion,
                                          "Error de Impresión Ticket Anulado Nota de Credito",
                                          "Error de Impresión Nota de Credito Anulado",
                                          "Error al imprimir ticket Anulado :<br>"+
                                          "Correlativo : " +VariablesCaja.vNumPedVta_Anul+"<br>"+
                                          "Error: " + e,
                                          //"joliva;operador;daubilluz@gmail.com");
                                          "");
            log.info("Error Imprimir Ticket Anulado : "+e);            
            FarmaUtility.showMessage(this,"Error .\n" + e.getMessage(), null);
        }
    }
  

    private void imprimeTicketBTLMF(){
        String caja_turno,ruta;
        try
        {
            //para agregar Fecha Creacion
            Date vFecImpr = new Date();
            String fechaImpresion;

            String DATE_FORMAT = "yyyyMMdd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            // System.out.println("Today is " + sdf.format(vFecImpr));
            fechaImpresion =  sdf.format(vFecImpr);

            ruta=DBCaja.ObtieneDirectorio();
            //ruta=ruta+"T_"+VariablesCaja.vNumPedVta_Anul+"_Anul";
            //JMIRANDA 08/07/09
            ruta=ruta+fechaImpresion+"_"+"T_"+VariablesCaja.vNumPedVta_Anul+"_Anul";

            caja_turno=DBCaja.obtieneCajaTurnoPedidoAnulado(VariablesCaja.vNumPedVta_Anul);
            String [] arrayDatos =  caja_turno.split("Ã");

            VariablesCaja.vNumPedVta = VariablesCaja.vNumPedVta_Anul;
            if (UtilityConvenioBTLMF.obtieneCompPago(new JDialog(), "", null))
            {

	           	for (int j = 0 ; j < VariablesConvenioBTLMF.vArray_ListaComprobante.size(); j++)
	           	{

           	       VariablesConvenioBTLMF.vNumCompPago = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(0)).trim();


                   UtilityConvenioBTLMF.imprimeMensajeTicketAnulacion(VariablesCaja.vNumCaja,
                                                      VariablesCaja.vNumTurnoCaja,
                                                      VariablesCaja.vNumPedVta_Anul,
                                                      "00", ruta+"_1.TXT", false, "S",
                                                      VariablesConvenioBTLMF.vNumCompPago);
		           //para montos inafectos
                   UtilityConvenioBTLMF.imprimeMensajeTicketAnulacion(VariablesCaja.vNumCaja,
		                                                      VariablesCaja.vNumTurnoCaja,
		                                                      VariablesCaja.vNumPedVta_Anul,
		                                                      "01", ruta+"_2.TXT", false, "S",
		                                                      VariablesConvenioBTLMF.vNumCompPago);
	           	}
            }



            //Ya no va asi
            //UtilityCaja.imprimeMensajeTicketAnulacion(VariablesCaja.vNumCaja,VariablesCaja.vNumTurnoCaja,VariablesCaja.vNumPedVta_Anul,"00",ruta+"_1.TXT",true,"N");
            //para montos inafectos
            //UtilityCaja.imprimeMensajeTicketAnulacion(VariablesCaja.vNumCaja,VariablesCaja.vNumTurnoCaja,VariablesCaja.vNumPedVta_Anul,"01",ruta+"_2.TXT",true,"N");
            //lblMensaje.setText("");
            FarmaUtility.showMessage(this, "¡Imprimiendo comprobante de nota de credito!", btnRelacionProductos);
            /*
            if(VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
                if(!VariablesCaja.vDescripImpr.equalsIgnoreCase("X")||VariablesCaja.vDescripImpr.equalsIgnoreCase(""))
                  lblMensaje.setText("Recoger comprobante en : "+VariablesCaja.vDescripImpr);
            }*/
            FarmaUtility.showMessage(this,"El ticket se ha reimpreso con éxito .",null);
        }
        catch(Exception e ){
            e.printStackTrace();
            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                          FarmaVariables.vCodLocal,
                                          //"dubilluz",
                                          VariablesPtoVenta.vDestEmailErrorAnulacion,
                                          "Error de Impresión Ticket Anulado Nota de Credito",
                                          "Error de Impresión Nota de Credito Anulado",
                                          "Error al imprimir ticket Anulado :<br>"+
                                          "Correlativo : " +VariablesCaja.vNumPedVta_Anul+"<br>"+
                                          "Error: " + e,
                                          //"joliva;operador;daubilluz@gmail.com");
                                          "");
            log.info("Error Imprimir Ticket Anulado : "+e);
            FarmaUtility.showMessage(this,"Error .\n" + e.getMessage(), null);
        }
    }
}