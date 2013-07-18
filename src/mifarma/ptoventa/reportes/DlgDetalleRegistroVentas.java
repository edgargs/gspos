package mifarma.ptoventa.reportes;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.*;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.*;

import mifarma.ptoventa.reportes.reference.*;


public class DlgDetalleRegistroVentas extends JDialog 
{
  /* ************************************************************************ */
  /*                          DECLARACION PROPIEDADES                         */
  /* ************************************************************************ */
  

  FarmaTableModel tableModel;
  private Frame myParentFrame;

  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private JPanelHeader jPanelHeader1 = new JPanelHeader();
  private JLabelWhite lblCliente = new JLabelWhite();
  private JLabelWhite lblDireccion = new JLabelWhite();
  private JLabelWhite lblRuc = new JLabelWhite();
  private JLabelWhite lblNoPedido = new JLabelWhite();
  private JLabelWhite lblNoPedidoT = new JLabelWhite();
  private JLabelWhite lblFecha = new JLabelWhite();
  private JLabelWhite lblFechaT = new JLabelWhite();
  private JLabelWhite lblAtencion = new JLabelWhite();
  private JLabelWhite lblAtencionT = new JLabelWhite();
  private JLabelWhite lblRucT = new JLabelWhite();
  private JLabelWhite lblDireccionT = new JLabelWhite();
  private JLabelWhite lblClienteT = new JLabelWhite();
  private JLabelWhite lblEstado = new JLabelWhite();
  private JPanelTitle jPanelTitle1 = new JPanelTitle();
  private JLabelWhite lblProductos = new JLabelWhite();
  private JScrollPane srcDetallePedidos = new JScrollPane();
  private JTable tblDetallePedidos = new JTable();
  private JLabelWhite lblHoraT = new JLabelWhite();
  private JLabelWhite lblHora = new JLabelWhite();
  private JLabelFunction jLabelFunction1 = new JLabelFunction();
  private JLabelFunction jLabelFunction2 = new JLabelFunction();
  private JLabelWhite lblCodigolocal = new JLabelWhite();
  private JLabelWhite lblDescLocal = new JLabelWhite();
  private JPanelTitle jPanelTitle2 = new JPanelTitle();
  private JLabelWhite lblBruto = new JLabelWhite();
  private JLabelWhite lblMontoBruto = new JLabelWhite();
  private JLabelWhite lblDctoMonto = new JLabelWhite();
  private JLabelWhite lblBruto1 = new JLabelWhite();
  private JLabelWhite lblIgvMonto = new JLabelWhite();
  private JLabelWhite lblBruto2 = new JLabelWhite();
  private JLabelWhite lblTotalMonto = new JLabelWhite();
  private JLabelWhite lblBruto3 = new JLabelWhite();
  private JLabelWhite lblItems = new JLabelWhite();
  private JLabelWhite lblitem = new JLabelWhite();
  private JLabelFunction jLabelFunction3 = new JLabelFunction();
  private JLabelFunction jLabelFunction4 = new JLabelFunction();

  
  /* ************************************************************************ */
  /*                             CONSTRUCTORES                                */
  /* ************************************************************************ */
  
  public DlgDetalleRegistroVentas()
  {
    this(null, "", false);
  }

  public DlgDetalleRegistroVentas(Frame parent, String title, boolean modal)
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
      e.printStackTrace();
    }

  }

  /* ************************************************************************ */
  /*                              METODO jbInit                               */
  /* ************************************************************************ */

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(751, 486));
    this.getContentPane().setLayout(null);
     this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Detalle del Pedido");
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
    jPanelWhite1.setBounds(new Rectangle(0, 0, 745, 460));
    jPanelHeader1.setBounds(new Rectangle(10, 10, 725, 120));
    jPanelHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    jPanelHeader1.setBackground(Color.white);
    lblCliente.setText("Cliente : ");
    lblCliente.setBounds(new Rectangle(5, 10, 75, 20));
    lblCliente.setForeground(new Color(255, 130, 14));
    lblDireccion.setText("Direccion : ");
    lblDireccion.setBounds(new Rectangle(5, 30, 75, 20));
    lblDireccion.setForeground(new Color(255, 130, 14));
    lblRuc.setText("Ruc: ");
    lblRuc.setBounds(new Rectangle(5, 50, 75, 20));
    lblRuc.setForeground(new Color(255, 130, 14));
    lblNoPedido.setText("No Pedido : ");
    lblNoPedido.setBounds(new Rectangle(5, 70, 75, 20));
    lblNoPedido.setForeground(new Color(255, 130, 14));
    lblNoPedidoT.setText("000");
    lblNoPedidoT.setBounds(new Rectangle(85, 70, 345, 20));
    lblNoPedidoT.setForeground(new Color(255, 130, 14));
    lblNoPedidoT.setFont(new Font("SansSerif", 0, 11));
    lblFecha.setText("Fecha :");
    lblFecha.setBounds(new Rectangle(5, 90, 75, 20));
    lblFecha.setForeground(new Color(255, 130, 14));
    lblFechaT.setBounds(new Rectangle(85, 90, 65, 20));
    lblFechaT.setForeground(new Color(255, 130, 14));
    lblFechaT.setFont(new Font("SansSerif", 0, 11));
    lblAtencion.setText("Atendido por :");
    lblAtencion.setBounds(new Rectangle(455, 90, 85, 20));
    lblAtencion.setForeground(new Color(255, 130, 14));
    lblAtencionT.setBounds(new Rectangle(545, 90, 165, 20));
    lblAtencionT.setForeground(new Color(255, 130, 14));
    lblAtencionT.setFont(new Font("SansSerif", 0, 11));
    lblRucT.setText("00000000");
    lblRucT.setBounds(new Rectangle(85, 50, 345, 20));
    lblRucT.setForeground(new Color(255, 130, 14));
    lblRucT.setFont(new Font("SansSerif", 0, 11));
    lblDireccionT.setBounds(new Rectangle(85, 30, 345, 20));
    lblDireccionT.setForeground(new Color(255, 130, 14));
    lblDireccionT.setFont(new Font("SansSerif", 0, 11));
    lblClienteT.setBounds(new Rectangle(85, 10, 345, 20));
    lblClienteT.setForeground(new Color(255, 130, 14));
    lblClienteT.setFont(new Font("SansSerif", 0, 11));
    lblEstado.setText("Cobrado");
    lblEstado.setBounds(new Rectangle(580, 30, 85, 20));
    lblEstado.setForeground(new Color(255, 130, 14));
    jPanelTitle1.setBounds(new Rectangle(10, 140, 725, 25));
    lblProductos.setText("Relacion de Productos");
    lblProductos.setBounds(new Rectangle(15, 5, 130, 15));
    srcDetallePedidos.setBounds(new Rectangle(10, 165, 725, 235));
    tblDetallePedidos.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblDetallePedidos_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          tblDetallePedidos_keyReleased(e);
        }
      });
    lblHoraT.setText("0");
    lblHoraT.setBounds(new Rectangle(300, 90, 80, 20));
    lblHoraT.setForeground(new Color(255, 130, 14));
    lblHoraT.setFont(new Font("SansSerif", 0, 11));
    lblHora.setText("Hora :");
    lblHora.setBounds(new Rectangle(255, 90, 45, 20));
    lblHora.setForeground(new Color(255, 130, 14));
    jLabelFunction1.setBounds(new Rectangle(10, 430, 150, 20));
    jLabelFunction1.setText("[ F4] Ver Comprobante");
    jLabelFunction2.setBounds(new Rectangle(615, 430, 117, 19));
    jLabelFunction2.setText("[ ESC ] Salir");
    lblCodigolocal.setText("0");
    lblCodigolocal.setBounds(new Rectangle(555, 10, 35, 20));
    lblCodigolocal.setForeground(new Color(255, 130, 14));
    lblCodigolocal.setHorizontalAlignment(SwingConstants.RIGHT);
    lblDescLocal.setBounds(new Rectangle(600, 10, 115, 20));
    lblDescLocal.setForeground(new Color(255, 130, 14));
    jPanelTitle2.setBounds(new Rectangle(10, 395, 725, 25));
    lblBruto.setText("Bruto S/");
    lblBruto.setBounds(new Rectangle(115, 5, 50, 15));
    lblMontoBruto.setBounds(new Rectangle(175, 5, 50, 15));
    lblMontoBruto.setText("0");
    lblDctoMonto.setBounds(new Rectangle(320, 5, 50, 15));
    lblDctoMonto.setText("0");
    lblBruto1.setText("Dcto S/");
    lblBruto1.setBounds(new Rectangle(270, 5, 45, 15));
    lblIgvMonto.setBounds(new Rectangle(490, 5, 55, 15));
    lblIgvMonto.setText("0");
    lblBruto2.setText("IGV S/");
    lblBruto2.setBounds(new Rectangle(440, 5, 40, 15));
    lblTotalMonto.setBounds(new Rectangle(665, 5, 50, 15));
    lblTotalMonto.setText("0");
    lblBruto3.setText("Total S/");
    lblBruto3.setBounds(new Rectangle(605, 5, 50, 15));
    lblItems.setBounds(new Rectangle(55, 5, 40, 15));
    lblItems.setText("0");
    lblitem.setText("Items");
    lblitem.setBounds(new Rectangle(10, 5, 40, 15));
    jLabelFunction3.setBounds(new Rectangle(425, 430, 125, 20));
    jLabelFunction3.setText("[ F12] Imprimir");
    jLabelFunction4.setBounds(new Rectangle(215, 430, 150, 20));
    jLabelFunction4.setText("[ F5] Ver Formas Pago");
        jPanelHeader1.add(lblDescLocal, null);
        jPanelHeader1.add(lblCodigolocal, null);
        jPanelHeader1.add(lblHora, null);
        jPanelHeader1.add(lblHoraT, null);
        jPanelHeader1.add(lblEstado, null);
        jPanelHeader1.add(lblClienteT, null);
    jPanelHeader1.add(lblDireccionT, null);
    jPanelHeader1.add(lblRucT, null);
        jPanelHeader1.add(lblAtencionT, null);
        jPanelHeader1.add(lblAtencion, null);
        jPanelHeader1.add(lblFechaT, null);
    jPanelHeader1.add(lblFecha, null);
    jPanelHeader1.add(lblNoPedidoT, null);
    jPanelHeader1.add(lblNoPedido, null);
    jPanelHeader1.add(lblRuc, null);
    jPanelHeader1.add(lblDireccion, null);
    jPanelHeader1.add(lblCliente, null);
        jPanelWhite1.add(jLabelFunction4, null);
        jPanelWhite1.add(jLabelFunction3, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jLabelFunction1, null);
    srcDetallePedidos.getViewport().add(tblDetallePedidos, null);
    jPanelWhite1.add(srcDetallePedidos, null);
    jPanelTitle1.add(lblProductos, null);
    jPanelWhite1.add(jPanelTitle1, null);
    jPanelWhite1.add(jPanelHeader1, null);
        jPanelWhite1.add(jPanelTitle2, null);
        jPanelTitle2.add(lblitem, null);
        jPanelTitle2.add(lblItems, null);
        jPanelTitle2.add(lblBruto3, null);
        jPanelTitle2.add(lblTotalMonto, null);
        jPanelTitle2.add(lblBruto2, null);
        jPanelTitle2.add(lblIgvMonto, null);
        jPanelTitle2.add(lblBruto1, null);
        jPanelTitle2.add(lblDctoMonto, null);
        jPanelTitle2.add(lblMontoBruto, null);
        jPanelTitle2.add(lblBruto, null);
        this.getContentPane().add(jPanelWhite1, null);
  }
  /* ************************************************************************ */
  /*                           METODO initialize                              */
  /* ************************************************************************ */
   private void initialize() {
    initTableListaDetalleVentas();
    FarmaVariables.vAceptar = false;
  }

  
  /* ************************************************************************ */
  /*                         METODOS INICIALIZACION                           */
  /* ************************************************************************ */
   
   private void initTableListaDetalleVentas()
  {
    tableModel = new FarmaTableModel(ConstantsReporte.columnsListaDetalleVentas,ConstantsReporte.defaultValuesListaDetalleVentas,0);
    FarmaUtility.initSimpleList(tblDetallePedidos,tableModel,ConstantsReporte.columnsListaDetalleVentas);
  }

  private void cargaDetalleVentas()
  {
    try
    {
      System.out.println(VariablesReporte.vCorrelativo);
      DBReportes.obtieneDetalleRegistroVentas(tableModel,VariablesReporte.vCorrelativo);
      FarmaUtility.ordenar(tblDetallePedidos, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);
      
    } catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this, "Error al listar el Detalle de las Ventas : \n" + sql.getMessage(),null);
      cerrarVentana(false);
    }
  }

  /* ************************************************************************ */
  /*                           METODOS DE EVENTOS                             */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(tblDetallePedidos);
    System.out.println("Numero Comprobante" + VariablesReporte.vNComprobante);
    cargaVariables();
    mostrarUsuarioVenta();
  }

  private void this_windowClosing(WindowEvent e)
  {
  FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }


  private void tblDetallePedidos_keyPressed(KeyEvent e)
  {
  	if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
      cerrarVentana(false);
		}
    else if (e.getKeyCode() == KeyEvent.VK_F4){
    lblNoPedidoT.setText(VariablesReporte.vCorrelativo);
    System.out.println(VariablesReporte.vCorrelativo);    
    listadoComprobanteVenta();
		} 
     else if (e.getKeyCode() == KeyEvent.VK_F5){
    /*lblNoPedidoT.setText(VariablesReporte.vCorrelativo);
    System.out.println(VariablesReporte.vCorrelativo);    
    listadoComprobanteVenta();*/
    listadoFormasPago();
		} else if (e.getKeyCode() == KeyEvent.VK_F12) {
      imprimir();
		}
    
  }
  
  private void tblDetallePedidos_keyReleased(KeyEvent e)
  {
    mostrarUsuarioVenta();
  }

  /* ************************************************************************ */
  /*                      METODOS AUXILIARES DE EVENTOS                       */
  /* ************************************************************************ */
      
   private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }

  /* ************************************************************************ */
  /*                      METODOS DE LOGICA DE NEGOCIO                        */
  /* ************************************************************************ */
  
  private void buscaDetalleVentas(String pCodigo)
  {
    VariablesReporte.vCorrelativo = pCodigo;
    cargaDetalleVentas();
  }


  private void listadoComprobanteVenta()
  {
    DlgComprobantes dlgComprobantes = new DlgComprobantes(myParentFrame, "", true);
    dlgComprobantes.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;
    }
  }
  /**  Lista las formas de pago del Pedido
   * @author:  
   * @since:  04/08/2007
   */
   private void listadoFormasPago()
  {
    
    DlgFormasPago dlgformaspago = new DlgFormasPago(myParentFrame, "", true);
    dlgformaspago.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;
    }
  }

  private void cargaVariables()
  {
    lblClienteT.setText (VariablesReporte.vCliente);
    lblDireccionT.setText(VariablesReporte.vDireccion);
    lblRucT.setText(VariablesReporte.vRuc);
    lblNoPedidoT.setText(VariablesReporte.vCorrelativo);
    lblFechaT.setText(VariablesReporte.vFecha.substring(0,10));
    lblHoraT.setText(VariablesReporte.vHora);
    //lblAtencionT.setText(VariablesReporte.vUsuario);
    lblEstado.setText(VariablesReporte.vEstado);
    lblCodigolocal.setText(FarmaVariables.vCodLocal);
    lblDescLocal.setText(FarmaVariables.vDescLocal);
    buscaDetalleVentas(VariablesReporte.vCorrelativo);
    lblMontoBruto.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(),8)).trim());
    lblDctoMonto.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(),9)).trim());
    lblIgvMonto.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(),10)).trim());
    lblTotalMonto.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(),11)).trim());
    lblItems.setText("" + tblDetallePedidos.getRowCount());
  }
  
  private void mostrarUsuarioVenta()
  {
    String usuarioVenta;
    int total = tblDetallePedidos.getRowCount();
    if(total > 0)
    {
      usuarioVenta = ((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(),12)).trim();
      lblAtencionT.setText(usuarioVenta);
      
    } else 
      {
        usuarioVenta = "" ;
      }
  }
  
  private void imprimir() {
    if (!FarmaUtility.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
        return;

    //FarmaPrintService vPrint = new FarmaPrintService(45, FarmaVariables.vImprReporte, true);
    FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
    System.out.println(FarmaVariables.vImprReporte);
    if (!vPrint.startPrintService()) {
        FarmaUtility.showMessage(this,
                        "No se pudo inicializar el proceso de impresión",
                        tblDetallePedidos);
        return;
    }
    
    try {
        
      String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
      String campoAlt = "________";

      vPrint.setStartHeader();
      vPrint.activateCondensed();
      vPrint.printBold(FarmaPRNUtility.llenarBlancos(40)+ " REPORTE  DETALLE DE PEDIDO", true);
      vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia, true);  
      vPrint.printBold("Fecha : " + fechaActual, true);
      vPrint.printBold("Fecha y Hora Pedido: " + VariablesReporte.vFecha.substring(0,10) + " " + VariablesReporte.vHora, true);
      vPrint.printBold("CORR : " + VariablesReporte.vCorrelativo , true);
      vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
      
      vPrint.printLine("=====================================================================================================",true);
      vPrint.printBold("Codigo Cant   Descripcion                  Unidad     Lab.                 P.Unit    Dscto      Total", true);
      vPrint.printLine("=====================================================================================================",true);
      vPrint.deactivateCondensed();
      vPrint.setEndHeader();
      for (int i = 0; i < tblDetallePedidos.getRowCount(); i++) {
        vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String) tblDetallePedidos.getValueAt(i, 0), 6)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblDetallePedidos.getValueAt(i,1), 6)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblDetallePedidos.getValueAt(i,2), 28)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblDetallePedidos.getValueAt(i,3), 10)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblDetallePedidos.getValueAt(i,4), 18)+" "+
        FarmaPRNUtility.alinearDerecha((String) tblDetallePedidos.getValueAt(i,5), 8)+" "+
        FarmaPRNUtility.alinearDerecha((String) tblDetallePedidos.getValueAt(i,6), 8)+" "+
        FarmaPRNUtility.alinearDerecha((String) tblDetallePedidos.getValueAt(i,7), 10)
                              , true);
      }
      vPrint.activateCondensed();
      vPrint.printLine("=====================================================================================================", true);
      vPrint.printBold("Total Monto S/. : " + FarmaPRNUtility.alinearDerecha(lblTotalMonto.getText(),83),true);
      vPrint.printBold("Registros Impresos: " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(tblDetallePedidos.getRowCount(), ",##0"),10), true);
      vPrint.deactivateCondensed();
      vPrint.endPrintService();
    } catch (SQLException sql) {
        sql.printStackTrace();		
        FarmaUtility.showMessage(this,"Ocurrió un error al imprimir : \n"+sql.getMessage(),null);
    }

  }
  

  
}