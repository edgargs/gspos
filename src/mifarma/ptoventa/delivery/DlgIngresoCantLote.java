package mifarma.ptoventa.delivery;
import com.gs.mifarma.componentes.*;

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import java.util.*;

import javax.swing.*;

import mifarma.common.*;

import mifarma.ptoventa.*;
import mifarma.ptoventa.delivery.*;
import mifarma.ptoventa.delivery.reference.*;
import mifarma.ptoventa.inventario.reference.*;
import mifarma.ptoventa.reference.*;

import oracle.jdeveloper.layout.*;
import oracle.jdeveloper.layout.XYConstraints;
import java.awt.event.KeyEvent;


public class DlgIngresoCantLote extends JDialog 
{
  private Frame myParentFrame;
  private FarmaTableModel tableModelDetalleIngreso;
  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private GridLayout gridLayout1 = new GridLayout();
  private JPanelTitle pnlDatosCliente = new JPanelTitle();
  private JLabelWhite lblCodigoProducto = new JLabelWhite();
  private JLabelWhite lblDescripcionT = new JLabelWhite();
  private JLabelWhite lblCodigoT = new JLabelWhite();
  private JLabelWhite lblDescripcionProducto = new JLabelWhite();
  private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();
  private JButtonLabel btnCantidad1 = new JButtonLabel();
  private JButton btnLote = new JButton();
  private JScrollPane srcListaDetalletole = new JScrollPane();
  JPanel pnlRelacion = new JPanel();
  XYLayout xYLayout2 = new XYLayout();
  private JButtonLabel btnDetalleProdLotes = new JButtonLabel();
  private JTable tblListaIngresoLote = new JTable();
  JLabelFunction lblESC = new JLabelFunction();
  JLabelFunction lblF11 = new JLabelFunction();
  private JTextField txtLote = new JTextField();
  private JLabelWhite lblCantidadSolicitada = new JLabelWhite();
  private JLabelWhite lblCantidadSolicitada_T = new JLabelWhite();
  private JLabelWhite lblCantTmp = new JLabelWhite();
  private JLabelWhite lblCantItems_T = new JLabelWhite();
  JLabelFunction lblF12 = new JLabelFunction();

// **************************************************************************
// Constructores
// **************************************************************************
  public DlgIngresoCantLote()
  {
    this(null, "", false);
  }

  public DlgIngresoCantLote(Frame parent, String title, boolean modal)
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

// **************************************************************************
// Método "jbInit()"
// **************************************************************************
  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(381, 436));
    this.getContentPane().setLayout(gridLayout1);
    this.setTitle("Ingreso Cantidad");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    pnlDatosCliente.setBounds(new Rectangle(10, 10, 350, 115));
    pnlDatosCliente.setBackground(Color.white);
    pnlDatosCliente.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    lblCodigoProducto.setBounds(new Rectangle(75, 10, 165, 15));
    lblCodigoProducto.setForeground(new Color(255, 130, 14));
    lblDescripcionT.setText("Producto :");
    lblDescripcionT.setBounds(new Rectangle(5, 35, 75, 15));
    lblDescripcionT.setForeground(new Color(255, 130, 14));
    lblCodigoT.setText("Codigo :");
    lblCodigoT.setBounds(new Rectangle(5, 10, 60, 15));
    lblCodigoT.setForeground(new Color(255, 130, 14));
    lblDescripcionProducto.setBounds(new Rectangle(75, 35, 265, 15));
    lblDescripcionProducto.setForeground(new Color(255, 130, 14));
    txtCantidad.setBounds(new Rectangle(75, 85, 55, 20));
    txtCantidad.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtCantidad_keyPressed(e);
        }
      });
    btnCantidad1.setText("Cantidad :");
    btnCantidad1.setBounds(new Rectangle(5, 85, 65, 20));
    btnCantidad1.setForeground(new Color(255, 130, 14));
    btnCantidad1.setMnemonic('c');
    btnCantidad1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCantidad_actionPerformed(e);
        }
      });
    btnLote.setText("...");
    btnLote.setBounds(new Rectangle(240, 85, 35, 20));
    btnLote.setFont(new Font("Tahoma", 1, 12));
    btnLote.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnLote_actionPerformed(e);
        }
      });
    srcListaDetalletole.setBounds(new Rectangle(10, 160, 350, 205));
    pnlRelacion.setBackground(new Color(255, 130, 14));
    pnlRelacion.setLayout(xYLayout2);
    pnlRelacion.setFont(new Font("SansSerif", 0, 11));
    pnlRelacion.setBounds(new Rectangle(10, 135, 350, 25));
    btnDetalleProdLotes.setText("Detalle de ingreso de lote :");
    btnDetalleProdLotes.setMnemonic('D');
    btnDetalleProdLotes.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDetalleProdLotes_actionPerformed(e);
        }
      });
    tblListaIngresoLote.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblListaIngresoLote_keyPressed(e);
        }
      });
    lblESC.setText("[ ESC ]  Cerrar");
    lblESC.setBounds(new Rectangle(245, 375, 115, 20));
    lblF11.setText("[ F11 ]  Aceptar");
    lblF11.setBounds(new Rectangle(125, 375, 115, 20));
    txtLote.setBounds(new Rectangle(145, 85, 90, 20));
    txtLote.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtLote_keyPressed(e);
        }
      });
    lblCantidadSolicitada.setBounds(new Rectangle(130, 60, 70, 15));
    lblCantidadSolicitada.setForeground(new Color(255, 130, 14));
    lblCantidadSolicitada_T.setText("Cantidad Solicitada :");
    lblCantidadSolicitada_T.setBounds(new Rectangle(5, 60, 120, 15));
    lblCantidadSolicitada_T.setForeground(new Color(255, 130, 14));
    lblCantTmp.setText("0");
    lblCantItems_T.setText("Cantidad Tmp :");
    lblF12.setText("[ F5 ]  Eliminar");
    lblF12.setBounds(new Rectangle(10, 375, 110, 20));
    srcListaDetalletole.getViewport();
    pnlDatosCliente.add(lblCantidadSolicitada_T, null);
    pnlDatosCliente.add(lblCantidadSolicitada, null);
    pnlDatosCliente.add(txtLote, null);
    pnlDatosCliente.add(btnLote, null);
    pnlDatosCliente.add(btnCantidad1, null);
    pnlDatosCliente.add(txtCantidad, null);
    pnlDatosCliente.add(lblCodigoProducto, null);
    pnlDatosCliente.add(lblDescripcionT, null);
    pnlDatosCliente.add(lblCodigoT, null);
    pnlDatosCliente.add(lblDescripcionProducto, null);
    jPanelWhite1.add(lblF12, null);
    jPanelWhite1.add(lblF11, null);
    jPanelWhite1.add(lblESC, null);
    jPanelWhite1.add(pnlRelacion, null);
    pnlRelacion.add(lblCantItems_T, new XYConstraints(190, 5, 95, 15));
    pnlRelacion.add(lblCantTmp, new XYConstraints(300, 5, 40, 15));
    pnlRelacion.add(btnDetalleProdLotes, new XYConstraints(10, 5, 165, 15));
    srcListaDetalletole.getViewport().add(tblListaIngresoLote, null);
    jPanelWhite1.add(srcListaDetalletole, null);
    jPanelWhite1.add(pnlDatosCliente, null);
    this.getContentPane().add(jPanelWhite1, null);
  }

// **************************************************************************
// Método "initialize()"
// **************************************************************************
	private void initialize() {
		FarmaVariables.vAceptar = false;
    initTableDetalleIngreso();
  }
  
// **************************************************************************
// Métodos de inicialización
// **************************************************************************
  private void initTableDetalleIngreso()
  {
    tableModelDetalleIngreso = new FarmaTableModel(ConstantsDelivery.columnsListaDetalleIngreso,ConstantsDelivery.defaultListaDetalleIngreso,0);
    FarmaUtility.initSimpleList(tblListaIngresoLote,tableModelDetalleIngreso,ConstantsDelivery.columnsListaDetalleIngreso);
    cargaListaDetalleProdLote(VariablesDelivery.vNumeroPedido, VariablesDelivery.vCodProducto);
  }
  
  private void cargaListaDetalleProdLote(String pNumPedido, String pCodProd)
  {
    try
    {
      DBDelivery.cargaListaDetProductoLote(tableModelDetalleIngreso, pNumPedido, pCodProd);
      lblCantTmp.setText("" + FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblListaIngresoLote, 0),"#####") );
    } catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error al cargar detalle Producto Lote - \n" + sql,txtCantidad);
    }
  }

// **************************************************************************
// Metodos de eventos
// **************************************************************************
  private void btnCantidad_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtCantidad);
  }
  
  private void btnDetalleProdLotes_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocusJTable(tblListaIngresoLote);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtCantidad);
    muestraInformacion();
  }
  
  private void txtCantidad_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      FarmaUtility.moveFocus(txtLote);
    }
    else chkKeyPressed(e);
  }

  private void txtLote_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      btnLote.doClick();
    }
    else chkKeyPressed(e);
  
  }

  private void btnLote_actionPerformed(ActionEvent e)
  {
    if( !validaCantidadIngresada() ) return;
    VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_LOTE;
    txtLote.setText(txtLote.getText().toUpperCase());
    validaCodigo(txtLote,VariablesPtoVenta.vTipoMaestro);
  }
  
  private void tblListaIngresoLote_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  
  // **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F5) {
      if(tblListaIngresoLote.getRowCount() <=0 ) return;
			if( FarmaUtility.rptaConfirmDialog(this, "Está seguro de eliminar el número de lote?") )
        eliminaProductoLoteCant();
		} else if (e.getKeyCode() == KeyEvent.VK_F11) {
      if(tblListaIngresoLote.getRowCount() <=0 ) return;
			if( FarmaUtility.rptaConfirmDialog(this, "Está seguro de agregar los lotes por producto?") ){
        grabaDetalleProductoLote();
      }
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      evaluaCantidadesIngresadas(true);
			cerrarVentana(false);
		}
  }
  
  private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}
  
// **************************************************************************
// Metodos de lógica de negocio
// **************************************************************************
  private void validaCodigo(JTextField pJTextField_Cod, String pTipoMaestro)
  {
    if(pJTextField_Cod.getText().trim().length() > 0)
    {
      String numLote = pJTextField_Cod.getText().trim();
      if( existeLoteProducto(VariablesDelivery.vCodProducto, numLote) )
      {
        obtieneFechaVencimiento(VariablesDelivery.vCodProducto, numLote);
        VariablesDelivery.vCantidadLista = txtCantidad.getText().trim();
        VariablesDelivery.vNumeroLote = numLote;
        adicionaDetalleIngreso();
      } else
      {
        FarmaUtility.showMessage(this,"El numero de lote no existe. Verifique!!!",txtLote);
        return;
      }
    } else{
      cargaListaMaestros(pTipoMaestro,pJTextField_Cod);
    }
  }

  private void cargaListaMaestros(String pTipoMaestro, JTextField pJTextField_Cod)
  {
    VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
    VariablesPtoVenta.vTipListaMaestros = ConstantsPtoVenta.TIP_LIST_MAESTRO_TRANSF;
    VariablesPtoVenta.vTituloListaMaestros="Seleccione el Lote del producto";
    VariablesInventario.vCodProd_Transf = VariablesDelivery.vCodProducto;
    FarmaUtility.moveFocus(txtCantidad);
    DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
    dlgListaMaestros.setVisible(true);
   
    if(FarmaVariables.vAceptar)
    {
      pJTextField_Cod.setText(VariablesPtoVenta.vCodMaestro);
      VariablesDelivery.vFechaVencimiento = VariablesPtoVenta.vDescMaestro;
      VariablesDelivery.vCantidadLista = txtCantidad.getText().trim();
      VariablesDelivery.vNumeroLote = txtLote.getText().trim();
      System.out.println("VariablesDelivery.vCantidadLista : " + VariablesDelivery.vCantidadLista);
      System.out.println("VariablesDelivery.vNumeroLote : " + VariablesDelivery.vNumeroLote);
      System.out.println("VariablesDelivery.vFechaVencimiento : " + VariablesDelivery.vFechaVencimiento);
      if(txtCantidad.getText().equalsIgnoreCase(""))
      {
        FarmaUtility.showMessage(this,"Ingrese una cantidad",txtCantidad);
      }
      else adicionaDetalleIngreso();
    }
  }
  
  private void adicionaDetalleIngreso()
  {
    if( existeLoteCargado(VariablesDelivery.vNumeroLote) )
    {
      FarmaUtility.showMessage(this, "El número de lote ya fue ingresado. Verifique!!!", txtLote);
      return;
    }
    if(!verificaCantidadProducto())
    {
      FarmaUtility.showMessage(this, "La suma de cantidades no puede ser mayor a la cantidad solicitada.", txtCantidad);
      return;
    }
    ArrayList myArray = new ArrayList();
    myArray.add(VariablesDelivery.vCantidadLista);
    myArray.add(VariablesDelivery.vNumeroLote);
    myArray.add(VariablesDelivery.vNumeroPedido);
    myArray.add(VariablesDelivery.vCodProducto);
    myArray.add(FarmaConstants.INDICADOR_N);
    myArray.add(VariablesDelivery.vFechaVencimiento);
    tableModelDetalleIngreso.data.add(myArray);
    tableModelDetalleIngreso.fireTableDataChanged();
    txtCantidad.setText("");
    txtLote.setText("");
    FarmaUtility.moveFocus(txtCantidad);
    lblCantTmp.setText("" + FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblListaIngresoLote, 0),"#####") );
  }
  
  private boolean existeLoteProducto(String pCodProd, String pNumLote)
  {
    String indicador = "";
    boolean valor = false;
    try
    {
      indicador = DBDelivery.obtieneIndicadorExisteLote(pCodProd, pNumLote);
    } catch(SQLException sql)
    {
      sql.printStackTrace();
      indicador = FarmaConstants.INDICADOR_N;
    } finally
    {
      if( indicador.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ) valor = true;
      else valor = false;
    }
    return valor;
  }
  
  private void muestraInformacion()
  {
   lblCodigoProducto.setText(VariablesDelivery.vCodProducto);
   lblDescripcionProducto.setText(VariablesDelivery.vDescProducto);
   lblCantidadSolicitada.setText(VariablesDelivery.vCantSolicitada); 
  }
  
  private boolean verificaCantidadProducto()
  {
    int cantidadPedido = Integer.parseInt(lblCantidadSolicitada.getText().trim());
    int cantidadTmp = Integer.parseInt(lblCantTmp.getText().trim());
    int cantidadIngresar = Integer.parseInt(txtCantidad.getText().trim());
    System.out.println("cantidadPedido : " + cantidadPedido);
    System.out.println("cantidadTmp : " + cantidadTmp);
    System.out.println("cantidadIngresar : " + cantidadIngresar);
    if( cantidadTmp + cantidadIngresar > cantidadPedido ) return false;
    else return true;
  }
  
  private boolean validaCantidadIngresada()
  {
    String cantIngresada = txtCantidad.getText().trim();
    if( cantIngresada.length() == 0 || !FarmaUtility.isInteger(cantIngresada) ||
        Integer.parseInt(cantIngresada) <= 0 )
    {
      FarmaUtility.showMessage(this, "Ingrese una cantidad válida.", txtCantidad);
      return false;
    }
    return true;
  }
  
  private boolean existeLoteCargado(String pNumLote)
  {
    String numLoteLista = "";
    for(int i=0; i<tblListaIngresoLote.getRowCount(); i++)
    {
      numLoteLista = FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 1);
      if( pNumLote.equalsIgnoreCase(numLoteLista) ) return true;
    }
    return false;
  }
  
  private void eliminaProductoLoteCant()
  {
    if(tblListaIngresoLote.getRowCount() <=0 ) return;
    String indicador = FarmaUtility.getValueFieldJTable(tblListaIngresoLote,tblListaIngresoLote.getSelectedRow(),4);
    if(indicador.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
      if( !eliminaBDProdLote(tblListaIngresoLote.getSelectedRow()) ) return;
    }
    tableModelDetalleIngreso.deleteRow(tblListaIngresoLote.getSelectedRow());
    tableModelDetalleIngreso.fireTableDataChanged();
    lblCantTmp.setText("" + FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblListaIngresoLote, 0),"#####") );
    FarmaUtility.moveFocusJTable(tblListaIngresoLote);
  }
  
  private boolean eliminaBDProdLote(int pRow)
  {
    try
    {
      DBDelivery.eliminaDetalleProducto(FarmaUtility.getValueFieldJTable(tblListaIngresoLote,pRow,2),
                                        FarmaUtility.getValueFieldJTable(tblListaIngresoLote,pRow,3),
                                        FarmaUtility.getValueFieldJTable(tblListaIngresoLote,pRow,1));
      FarmaUtility.aceptarTransaccion();
      return true;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error al eliminar Producto Lote - \n" + sql,txtCantidad);
      return false;
    }
  }
  
  private void eliminaVtaInstiProd() throws SQLException
  {
    DBDelivery.eliminaDetalleProducto(VariablesDelivery.vNumeroPedido, VariablesDelivery.vCodProducto);
  }
  
  private void grabaDetalleProductoLote()
  {
    try
    {
      eliminaVtaInstiProd();
      for(int i=0; i<tblListaIngresoLote.getRowCount(); i++)
      {
        DBDelivery.agregaDetalleProductoLote(FarmaUtility.getValueFieldJTable(tblListaIngresoLote,i,2),
                                             FarmaUtility.getValueFieldJTable(tblListaIngresoLote,i,3),
                                             FarmaUtility.getValueFieldJTable(tblListaIngresoLote,i,1),
                                             FarmaUtility.getValueFieldJTable(tblListaIngresoLote,i,0),
                                             FarmaUtility.getValueFieldJTable(tblListaIngresoLote,i,5));
      }
      FarmaUtility.aceptarTransaccion();
      evaluaCantidadesIngresadas(false);
      cerrarVentana(true);
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error al grabar detalle Producto Lote - \n" + sql,txtCantidad);
    }
  }
  
  private void evaluaCantidadesIngresadas(boolean pConIndicadorBD)
  {
    String indicador = "";
    int cantidad = 0;
    int cantidadProdPedido = Integer.parseInt(VariablesDelivery.vCantSolicitada);
    for(int i=0; i<tblListaIngresoLote.getRowCount(); i++)
    {
      if(pConIndicadorBD)
      {
        indicador = FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 4);
        if(indicador.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
          cantidad = cantidad + Integer.parseInt(FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 0));
      } else
      {
        cantidad = cantidad + Integer.parseInt(FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 0));
      }
    }
    if( cantidad == cantidadProdPedido ) VariablesDelivery.vCantProdLoteCompleta = true;
    else VariablesDelivery.vCantProdLoteCompleta = false;
  }
  
  private String obtieneFechaVencimiento(String pCodProd, String pNumLote)
  {
    try
    {
      VariablesDelivery.vFechaVencimiento = DBDelivery.obtieneFechaVencimientoLote(pCodProd, pNumLote);
      System.out.println("VariablesDelivery.vFechaVencimiento: " + VariablesDelivery.vFechaVencimiento );
    } catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error al obtener la fecha de vencimiento para el lote. \n" + sql.getMessage(),txtCantidad);
    } 
    return VariablesDelivery.vFechaVencimiento;
  }
  

}