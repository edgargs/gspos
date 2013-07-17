package mifarma.ptoventa.delivery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import java.util.*;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.*;
import mifarma.ptoventa.reference.*;
import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JLabelWhite;
import mifarma.ptoventa.delivery.reference.*;
import mifarma.ptoventa.ventas.reference.*;
import mifarma.ptoventa.ventas.*;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.caja.*;
import mifarma.ptoventa.caja.reference.*;
import mifarma.ptoventa.convenio.reference.*;
import mifarma.ptoventa.caja.reference.UtilityCaja.*;

public class DlgUltimosPedidos extends JDialog {

	private Frame myParentFrame;
	private FarmaTableModel tableModelDetalleUltimosPedidos;
	private FarmaTableModel tableModelCabeceraUltimosPedidos;
	private BorderLayout borderLayout1 = new BorderLayout();
	private JPanel jContentPane = new JPanel();
  private  String numPedVta = "" ;
  private  String codLocal = "" ;
	ActionMap actionMap1 = new ActionMap();
	JScrollPane scrUltimosPedidos = new JScrollPane();
	JPanel pnlRelacion = new JPanel();
	XYLayout xYLayout2 = new XYLayout();
	JScrollPane scrDetalle = new JScrollPane();
	JPanel pnlItems = new JPanel();
	XYLayout xYLayout3 = new XYLayout();
	JButton btnDetalle = new JButton();
	JLabelFunction lblEsc = new JLabelFunction();
	JTable tblDetalle = new JTable();
	JTable tblListaUltimos = new JTable();
	private JButtonLabel btnUltimosPedidos = new JButtonLabel();
  private JPanelTitle pnlDatosCliente = new JPanelTitle();
  private JLabelWhite lblNombreCliente_T = new JLabelWhite();
  private JLabelWhite lblDirecCliente_T = new JLabelWhite();
  private JLabelWhite lblTelfCliente_T = new JLabelWhite();
  private JLabelWhite lblDirecCliente = new JLabelWhite();
  private JLabelWhite lblTelfCLiente = new JLabelWhite();
  private JLabelWhite lblNomCliente = new JLabelWhite();
  JLabelFunction lblF11 = new JLabelFunction();
  private JLabelWhite lblCantItems_T = new JLabelWhite();
  private JLabelWhite lblCantItems = new JLabelWhite();
  JLabelFunction lblF8 = new JLabelFunction();
  JLabelFunction lblF9 = new JLabelFunction();
	// JLabel lblModo = new FarmaBlinkJLabel();
	// JLabel lblTipoPedido = new JLabel();

	// **************************************************************************
	// Constructores
	// **************************************************************************

	public DlgUltimosPedidos() {
		this(null, "", false);
	}

	public DlgUltimosPedidos(Frame parent, String title, boolean modal) {
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
		this.setSize(new Dimension(650, 528));
		this.getContentPane().setLayout(borderLayout1);
		this.setTitle("Ultimos Pedidos Realizados");
		this.setFont(new Font("SansSerif", 0, 11));
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}
			public void windowClosing(WindowEvent e) {
				this_windowClosing(e);
			}
		});
		jContentPane.setLayout(null);
		jContentPane.setSize(new Dimension(657, 361));
		jContentPane.setBackground(Color.white);
		jContentPane.setForeground(Color.white);
		scrUltimosPedidos.setFont(new Font("SansSerif", 0, 11));
		scrUltimosPedidos.setBounds(new Rectangle(10, 125, 620, 155));
		scrUltimosPedidos.setBackground(new Color(255, 130, 14));
		pnlRelacion.setBackground(new Color(255, 130, 14));
		pnlRelacion.setLayout(xYLayout2);
		pnlRelacion.setFont(new Font("SansSerif", 0, 11));
		pnlRelacion.setBounds(new Rectangle(10, 100, 620, 25));
		scrDetalle.setFont(new Font("SansSerif", 0, 11));
		scrDetalle.setBounds(new Rectangle(10, 310, 620, 145));
		scrDetalle.setBackground(new Color(255, 130, 14));
		pnlItems.setBackground(new Color(255, 130, 14));
		pnlItems.setFont(new Font("SansSerif", 0, 11));
		pnlItems.setLayout(xYLayout3);
		pnlItems.setBounds(new Rectangle(10, 285, 620, 25));
		btnDetalle.setText("Detalle del Pedido :");
		btnDetalle.setFont(new Font("SansSerif", 1, 11));
		btnDetalle.setHorizontalAlignment(SwingConstants.LEFT);
		btnDetalle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnDetalle.setBackground(new Color(43, 141, 39));
		btnDetalle.setForeground(Color.white);
		btnDetalle.setRequestFocusEnabled(false);
		btnDetalle.setMnemonic('d');
		btnDetalle.setBorderPainted(false);
		btnDetalle.setContentAreaFilled(false);
		btnDetalle.setDefaultCapable(false);
		btnDetalle.setFocusPainted(false);
    btnDetalle.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDetalle_actionPerformed(e);
        }
      });
		lblEsc.setText("[ Esc ]  Cerrar");
		lblEsc.setBounds(new Rectangle(535, 465, 95, 20));
		tblDetalle.setFont(new Font("SansSerif", 0, 11));
		tblDetalle.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
          tblDetalle_keyPressed(e);
			}
		});
		tblListaUltimos.setFont(new Font("SansSerif", 0, 11));	
    tblListaUltimos.addKeyListener(new KeyAdapter()
      {

        public void keyReleased(KeyEvent e)
        {
          tblListaUltimos_keyReleased(e);
        }

        public void keyPressed(KeyEvent e)
        {
          tblListaUltimos_keyPressed(e);
        }
      });
    btnUltimosPedidos.setText("Relacion Ultimos Pedidos :");
    btnUltimosPedidos.setMnemonic('R');
    btnUltimosPedidos.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPedidosPendeintes_actionPerformed(e);
        }
      });
    pnlDatosCliente.setBounds(new Rectangle(10, 10, 620, 75));
    pnlDatosCliente.setBackground(Color.white);
    pnlDatosCliente.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    lblNombreCliente_T.setText("Nombre del Cliente :");
    lblNombreCliente_T.setBounds(new Rectangle(5, 10, 120, 15));
    lblNombreCliente_T.setForeground(new Color(255, 130, 14));
    lblDirecCliente_T.setText("Direccion del Cliente :");
    lblDirecCliente_T.setBounds(new Rectangle(5, 30, 120, 15));
    lblDirecCliente_T.setForeground(new Color(255, 130, 14));
    lblTelfCliente_T.setText("Teléfono :");
    lblTelfCliente_T.setBounds(new Rectangle(5, 50, 120, 15));
    lblTelfCliente_T.setForeground(new Color(255, 130, 14));
    lblDirecCliente.setBounds(new Rectangle(130, 30, 480, 15));
    lblDirecCliente.setForeground(new Color(255, 130, 14));
    lblTelfCLiente.setBounds(new Rectangle(130, 50, 135, 15));
    lblTelfCLiente.setForeground(new Color(255, 130, 14));
    lblNomCliente.setBounds(new Rectangle(130, 10, 480, 15));
    lblNomCliente.setForeground(new Color(255, 130, 14));
    lblF11.setText("[ F11 ]  Generar Pedido");
    lblF11.setBounds(new Rectangle(375, 465, 150, 20));
    lblCantItems_T.setText("Cantidad Items :");
    lblCantItems.setText("0");
    lblF8.setText("[ F8 ]  Actualizar Lista");
    lblF8.setBounds(new Rectangle(225, 465, 140, 20));
    lblF9.setText("[ F7 ]  Imprimir Comanda");
    lblF9.setBounds(new Rectangle(65, 465, 150, 20));
		scrUltimosPedidos.getViewport();
		scrDetalle.getViewport();
		this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    pnlDatosCliente.add(lblNomCliente, null);
    pnlDatosCliente.add(lblTelfCLiente, null);
    pnlDatosCliente.add(lblDirecCliente_T, null);
    pnlDatosCliente.add(lblNombreCliente_T, null);
    pnlDatosCliente.add(lblTelfCliente_T, null);
    pnlDatosCliente.add(lblDirecCliente, null);
    jContentPane.add(lblF9, null);
    jContentPane.add(lblF8, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(pnlDatosCliente, null);
    scrUltimosPedidos.getViewport().add(tblListaUltimos, null);
    jContentPane.add(scrUltimosPedidos, null);
    pnlRelacion.add(btnUltimosPedidos, new XYConstraints(10, 5, 165, 15));
    jContentPane.add(pnlRelacion, null);
    scrDetalle.getViewport().add(tblDetalle, null);
    jContentPane.add(scrDetalle, null);
    pnlItems.add(lblCantItems, new XYConstraints(555, 5, 40, 15));
    pnlItems.add(lblCantItems_T, new XYConstraints(440, 5, 105, 15));
    pnlItems.add(btnDetalle, new XYConstraints(10, 5, 125, 15));
    jContentPane.add(pnlItems, null);
    jContentPane.add(lblEsc, null);
		this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
	}

	// **************************************************************************
	// Método "initialize()"
	// **************************************************************************

	private void initialize() {
		FarmaVariables.vAceptar = false;
    initTableDetCabUltimosPedidos();
    initTableListaCabUltimosPedidos();
  };

// **************************************************************************
// Métodos de inicialización
// **************************************************************************
  private void initTableListaCabUltimosPedidos()
  {
    tableModelCabeceraUltimosPedidos = new FarmaTableModel(ConstantsDelivery.columnsListaCabUltimosPedidos, ConstantsDelivery.defaultListaCabUltimosPedidos, 0);
    FarmaUtility.initSimpleList(tblListaUltimos, tableModelCabeceraUltimosPedidos, ConstantsDelivery.columnsListaCabUltimosPedidos);
    actualizaListaPedidos();
  }

  private void initTableDetCabUltimosPedidos()
  {
    tableModelDetalleUltimosPedidos = new FarmaTableModel(ConstantsDelivery.columnsListaDetUltimosPedidos, ConstantsDelivery.defaultListaDetUltimosPedidos, 0);
    FarmaUtility.initSimpleList(tblDetalle, tableModelDetalleUltimosPedidos, ConstantsDelivery.columnsListaDetUltimosPedidos);
  }
  
  private void cargaListaCabecera()
  {
    try
    {
      DBDelivery.cargaListaCabUltimosPedidos(tableModelCabeceraUltimosPedidos);
      if (tblListaUltimos.getRowCount() > 0){
        FarmaUtility.ordenar(tblListaUltimos, tableModelCabeceraUltimosPedidos, 10, FarmaConstants.ORDEN_DESCENDENTE);
        FarmaUtility.moveFocusJTable(tblListaUltimos);
        numPedVta = ((String)tblListaUltimos.getValueAt(tblListaUltimos.getSelectedRow(),1)).trim();
        codLocal = ((String)tblListaUltimos.getValueAt(tblListaUltimos.getSelectedRow(),9)).trim();
        cargaListaDetallePedido(numPedVta, codLocal);
      } else
      {
        tableModelDetalleUltimosPedidos.clearTable();
        tableModelDetalleUltimosPedidos.fireTableDataChanged();
        lblCantItems.setText("0");
        lblDirecCliente.setText("");
        lblNomCliente.setText("");
        lblTelfCLiente.setText("");
      }
    } catch (SQLException e)
    {
      FarmaUtility.showMessage(this, "Error al cargar ultimos pedidos - \n" + e, tblListaUltimos);
      e.printStackTrace();
    }
  }

  private void cargaListaDetallePedido(String pNumPedido,
                                       String pCodLocal)
  {
    try
    {
      DBDelivery.cargaListaDetUltimosPedidos(tableModelDetalleUltimosPedidos, pNumPedido, pCodLocal);
      lblCantItems.setText("" + tblDetalle.getRowCount());
      if (tblDetalle.getRowCount() > 0){
        FarmaUtility.ordenar(tblDetalle, tableModelDetalleUltimosPedidos, 2, "asc");
      }
    } catch (SQLException e)
    {
      FarmaUtility.showMessage(this, "Error al cargar detalle ultimos pedidos - \n" + e, tblListaUltimos);
      e.printStackTrace();
    }
  }

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************
  private void tblListaUltimos_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  
	private void tblDetalle_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
	}

	private void this_windowOpened(WindowEvent e)
  {
		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(tblListaUltimos);
	}

  private void btnPedidosPendeintes_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblListaUltimos);
  }

  private void btnDetalle_actionPerformed(ActionEvent e)
  {
    if(tblDetalle.getRowCount() > 0)
      FarmaUtility.moveFocusJTable(tblDetalle);
  }
  
	private void this_windowClosing(WindowEvent e) 
  {
		FarmaUtility.showMessage(this,"Debe presionar la tecla ESC para cerrar la ventana.", null);
	}
	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cerrarVentana(false);
		} else if (e.getKeyCode() == KeyEvent.VK_F5) {/*
      if(tblListaUltimos.getRowCount() > 0 &&
         FarmaUtility.rptaConfirmDialog(this, "Está seguro de anular el pedido?"))
      {
        if(!cargaLoginAdminLocal()){
          FarmaUtility.showMessage(this,"No se realizó la operación. Solo un usuario con Rol de\nAdministrador de Local puede anular el pedido.", tblListaUltimos);
          return;
        }
        anulaPedidoDeliveryAutomatico();
        actualizaListaPedidos();
      }*/
    } else if (e.getKeyCode() == KeyEvent.VK_F7) {
      //JCORTEZ 16.07.2008 Se imprime la comanda 
      //if(VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("S")){
       int row=0;
       row=tblListaUltimos.getSelectedRow();
       if(row>-1){
        VariablesCaja.vNumPedVta=FarmaUtility.getValueFieldJTable(tblListaUltimos,row,1);
        System.out.println("VariablesCaja.vNumPedVta : "+VariablesCaja.vNumPedVta);
        UtilityCaja.imprimeDatosDelivery(this,VariablesCaja.vNumPedVta);
       }
      //}
    }else if (e.getKeyCode() == KeyEvent.VK_F8) {
      actualizaListaPedidos();
    } else if (e.getKeyCode() == KeyEvent.VK_F11) {
    if(!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ) { 
      if(tblListaUltimos.getRowCount() > 0) 
      {
       if(validaFraccionLocal() && validaStockLocal() && validaCantidadEntera()){
        obtieneValores();
        obtieneLocalOrigen();
        if(VariablesDelivery.vCodLocalOrigen.equalsIgnoreCase(ConstantsDelivery.CODIGO_LOCAL_VTA_INSTITUCIONAL))
          muestraListaDetallePedido();
        else
          generaPedidoDeliveryAutomatico();
        }        
      }
    }
  else
    {
     FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opción",null); 
     }
   }
  }

  private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

  private void tblListaUltimos_keyReleased(KeyEvent e)
  {
    if(tblListaUltimos.getRowCount() > 0){
      numPedVta = ((String)tblListaUltimos.getValueAt(tblListaUltimos.getSelectedRow(),1)).trim();
      codLocal = ((String)tblListaUltimos.getValueAt(tblListaUltimos.getSelectedRow(),9)).trim();
      cargaDatosCabecera();
      cargaListaDetallePedido(numPedVta, codLocal);
    }
  }

	// **************************************************************************
	// Metodos de lógica de negocio
	// **************************************************************************
  private void cargaDatosCabecera()
  {
    if(tblListaUltimos.getRowCount()>0){
      String nomCli = "", dirCli = "" , telefono = ""; 
      nomCli = ((String)tblListaUltimos.getValueAt(tblListaUltimos.getSelectedRow(),6)).trim();
      dirCli = ((String)tblListaUltimos.getValueAt(tblListaUltimos.getSelectedRow(),7)).trim();
      telefono = ((String)tblListaUltimos.getValueAt(tblListaUltimos.getSelectedRow(),8)).trim();
      lblNomCliente.setText(nomCli);
      lblDirecCliente.setText(dirCli);
      lblTelfCLiente.setText(telefono);
    }
  }
  
  private void generaPedidoDeliveryAutomatico()
  {
    String estadoPedido = "";
      int numCaja=0;
      String TipoComp="";
      
    if(tblListaUltimos.getRowCount() <= 0) return;
    try
    {
      estadoPedido = obtieneEstadoPedido_ForUpdate();
      if(!estadoPedido.equalsIgnoreCase(ConstantsVentas.ESTADO_PEDIDO_PENDIENTE))
      {
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(this, "El pedido No se encuentra pendiente. Verifique!!!", tblListaUltimos);
        actualizaListaPedidos();
        return;
      }
      if(!FarmaUtility.rptaConfirmDialog(this, "Está seguro de generar el pedido?")) return;
      VariablesDelivery.vNumeroPedido = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),1);
      VariablesDelivery.vCodLocal = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),9);
      VariablesVentas.vNum_Ped_Vta = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO,10);
      VariablesVentas.vNum_Ped_Diario = obtieneNumeroPedidoDiario();
      System.out.println("VariablesVentas.vNum_Ped_Vta : " + VariablesVentas.vNum_Ped_Vta);
      System.out.println("VariablesVentas.vNum_Ped_Diario : " + VariablesVentas.vNum_Ped_Diario);
      VariablesVentas.vVal_Neto_Ped = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),3);
       
      //JCORTEZ 14.05.09 Se obtiene tipo de comprobantes
      TipoComp=FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),5);
        
      if(TipoComp.trim().equalsIgnoreCase("BOLETA"))
          TipoComp=ConstantsVentas.TIPO_COMP_BOLETA;
       else if(TipoComp.trim().equalsIgnoreCase("FACTURA"))
          TipoComp=ConstantsVentas.TIPO_COMP_FACTURA;
      
        if(VariablesVentas.vTip_Comp_Ped.trim().length()<2)
        VariablesVentas.vTip_Comp_Ped=TipoComp;
        
      //JCORTEZ 14.05.09 Si es que el numero de caja es vacio 
       System.out.println("FarmaVariables.vNuSecUsu : " + FarmaVariables.vNuSecUsu);
      if(VariablesCaja.vNumCaja.trim().length()<2)
        numCaja=DBCaja.obtieneNumeroCajaUsuarioAux2();
       
        VariablesCaja.vNumCaja=""+numCaja;
        
       System.out.println("JCORTEZ: Numero de caja--> "+VariablesCaja.vNumCaja);
        System.out.println("JCORTEZ: Tipo de comprobante origen --> "+VariablesVentas.vTip_Comp_Ped);
        //VariablesVentas.vTip_Comp_Ped = DBCaja.getObtieneTipoComp(VariablesCaja.vNumCaja,VariablesVentas.vTip_Comp_Ped);
        
         //JCORTEZ 10.06.09  Se obtiene tipo de comrpobante de la relacion maquina - impresora
           if(TipoComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)||
                TipoComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)){
             VariablesVentas.vTip_Comp_Ped = DBCaja.getObtieneTipoCompPorIP( FarmaVariables.vIpPc,VariablesVentas.vTip_Comp_Ped);
                System.out.println("JCORTEZ: VariablesVentas.vTip_Comp_Ped--> "+VariablesVentas.vTip_Comp_Ped);
             if(VariablesVentas.vTip_Comp_Ped.trim().equalsIgnoreCase("N")){
                 FarmaUtility.showMessage(this,"Usted no cuenta con una impresora asignada de ticket o boleta. Verifique!!!",tblListaUltimos);
                 return;
              }
            }
            
        System.out.println("JCORTEZ: Tipo de comprobante local --> "+VariablesVentas.vTip_Comp_Ped);
        
      DBDelivery.generaPedidoDeliveryAutomatico();
      DBDelivery.actualuizaValoresDa();
      DBConvenio.actualizaNumPedido(VariablesVentas.vNum_Ped_Vta,VariablesDelivery.vNumeroPedido);
      FarmaUtility.aceptarTransaccion();
      // -- Se modifico para caja multifuncional
      // dubilluz  02/05/2008
      if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) ) {
           VariablesCaja.vNumPedPendiente = VariablesVentas.vNum_Ped_Diario;
           muestraCobroPedido();
      } else  muestraPedidoRapido();
      // muestraPedidoRapido();
    } catch(SQLException sqlex)
    {
      FarmaUtility.liberarTransaccion();
      sqlex.printStackTrace();
      FarmaUtility.showMessage(this, "Error al grabar el pedido delivery automático - \n" + sqlex, tblListaUltimos);
    }
  }
  
  private void muestraPedidoRapido() {
    DlgNumeroPedidoGenerado dlgNumeroPedidoGenerado = new DlgNumeroPedidoGenerado(myParentFrame,"",true);
    dlgNumeroPedidoGenerado.setVisible(true);
    if ( FarmaVariables.vAceptar ){
      FarmaVariables.vAceptar = false;
      actualizaListaPedidos();
    }
  }
  
  private String obtieneEstadoPedido_ForUpdate()
  {
    String estadoPedido = "";
    try
    {
      VariablesDelivery.vNumeroPedido = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),1);
      VariablesDelivery.vCodLocal = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),9);
      estadoPedido = DBDelivery.obtieneEstadoPedido_ForUpdate();
    } catch(SQLException sqlex)
    {
      FarmaUtility.liberarTransaccion();
      sqlex.printStackTrace();
      estadoPedido = "";
      FarmaUtility.showMessage(this, "Error al obtener estado del pedido - \n" + sqlex, tblListaUltimos);
    }
    System.out.println("VariablesDelivery.vNumeroPedido : " + VariablesDelivery.vNumeroPedido);
    System.out.println("estadoPedido : " + estadoPedido);
    return estadoPedido;
  }
  
  private String obtieneNumeroPedidoDiario() throws SQLException {

    String feModNumeracion = DBVentas.obtieneFecModNumeraPed();
    String feHoyDia = "";
    String numPedDiario = "";
    if ( !(feModNumeracion.trim().length()>0) )  throw new SQLException("Ultima Fecha Modificacion de Numeración Diaria del Pedido NO ES VALIDA !!!","Error",0001);
    else {
      feHoyDia = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
      feHoyDia = feHoyDia.trim().substring(0,2);
      feModNumeracion = feModNumeracion.trim().substring(0,2);
      if ( Integer.parseInt(feHoyDia)!=Integer.parseInt(feModNumeracion) ) {
        FarmaSearch.inicializaNumeracionNoCommit(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO);
        numPedDiario = "0001";
      } else {
        // Obtiene el Numero de Atencion del Día y hace SELECT FOR UPDATE.
        numPedDiario = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO,4);
      }
    }
    return numPedDiario;
  }
  
  private void anulaPedidoDeliveryAutomatico()
  {
    try
    {
      VariablesDelivery.vNumeroPedido = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),1);
      VariablesDelivery.vCodLocal = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),9);
      DBDelivery.anulaPedidoDeliveryAutomatico(VariablesDelivery.vCodLocal, VariablesDelivery.vNumeroPedido);
      FarmaUtility.aceptarTransaccion();
      FarmaUtility.showMessage(this, "Pedido anulado correctamente.", tblListaUltimos);
    } catch(SQLException sqlex)
    {
      FarmaUtility.liberarTransaccion();
      sqlex.printStackTrace();
      FarmaUtility.showMessage(this, "Error al anular pedido de delivery automatico - \n" + sqlex, tblListaUltimos);
    }
  }
  
  private void actualizaListaPedidos()
  {
    cargaListaCabecera();
    cargaDatosCabecera();
  }
  
  private boolean cargaLoginAdminLocal()
  {
    String numsec = FarmaVariables.vNuSecUsu ;
    String idusu = FarmaVariables.vIdUsu ;
    String nomusu = FarmaVariables.vNomUsu ;
    String apepatusu = FarmaVariables.vPatUsu ;
    String apematusu = FarmaVariables.vMatUsu ;
    try{
      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
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
  
  private boolean validaFraccionLocal()
  {
    String valorFraccion = "";
    String codigoErrado = "";
    StringBuffer cadenaCodigos = new StringBuffer(); 
    for(int i=0; i<tblDetalle.getRowCount(); i++)
    {
      valorFraccion = FarmaUtility.getValueFieldJTable(tblDetalle, i, 8);
      if( valorFraccion.equals(ConstantsDelivery.VALOR_FRACCION_ERROR))
      {
        codigoErrado = valorFraccion = FarmaUtility.getValueFieldJTable(tblDetalle, i, 0);
        if(cadenaCodigos.length() == 0)
          cadenaCodigos.append(codigoErrado);
        else
          cadenaCodigos.append(", " + codigoErrado);
      }
    }
    if(cadenaCodigos.length() == 0) return true;
    else
    {
      FarmaUtility.showMessage(this,"No se puede generar el pedido. Los siguientes codigos\nno tienen un valor de fracción correcto : \n" + cadenaCodigos,tblListaUltimos);
      return false;
    }
  }
  
  private boolean validaStockLocal()
  {
    String cantidadPedido = "";
    String stockReal = "";
    String codigoErrado = "";
    boolean error = false; 
    StringBuffer cadenaCodigos = new StringBuffer(); 
    for(int i=0; i<tblDetalle.getRowCount(); i++)
    {
      cantidadPedido = FarmaUtility.getValueFieldJTable(tblDetalle, i, 4);
      stockReal = FarmaUtility.getValueFieldJTable(tblDetalle, i, 7);
      if( FarmaUtility.isDouble(cantidadPedido) && FarmaUtility.isDouble(stockReal) )
      {
        System.out.println("cantidad pedido : " + Double.parseDouble(cantidadPedido));
        System.out.println("stock real : " + Double.parseDouble(stockReal));
        if( Double.parseDouble(cantidadPedido) > Double.parseDouble(stockReal) ) error = true;
        else error = false;
      } else error = true;
      if( error )
      {
        codigoErrado = FarmaUtility.getValueFieldJTable(tblDetalle, i, 0);
        if(cadenaCodigos.length() == 0)
          cadenaCodigos.append(codigoErrado);
        else
          cadenaCodigos.append(", " + codigoErrado);
      }
    }
    if(cadenaCodigos.length() == 0) return true;
    else
    {
      FarmaUtility.showMessage(this,"No se puede generar el pedido. Los siguientes codigos\nno tienen stock suficiente : \n" + cadenaCodigos,tblListaUltimos);
      return false;
    }
  }
  
  private boolean validaCantidadEntera()
  {
    String cantidadPedido = "";
    double cantidadPedidoDouble = 0.00;
    String codigoErrado = "";
    boolean error = false; 
    StringBuffer cadenaCodigos = new StringBuffer(); 
    for(int i=0; i<tblDetalle.getRowCount(); i++)
    {
      error = false;
      cantidadPedido = FarmaUtility.getValueFieldJTable(tblDetalle, i, 4);
      cantidadPedidoDouble = Double.parseDouble(cantidadPedido) % FarmaUtility.trunc(cantidadPedido);
      if( cantidadPedidoDouble != 0 )
      {
        error = true;
      }
      if( error )
      {
        codigoErrado = FarmaUtility.getValueFieldJTable(tblDetalle, i, 0);
        if(cadenaCodigos.length() == 0)
          cadenaCodigos.append(codigoErrado);
        else
          cadenaCodigos.append(", " + codigoErrado);
      }
    }
    if(cadenaCodigos.length() == 0) return true;
    else
    {
      FarmaUtility.showMessage(this,"No se puede generar el pedido. Los siguientes codigos\nno tienen una cantidad válida: \n" + cadenaCodigos,tblListaUltimos);
      return false;
    }
  }
  
  private String obtieneLocalOrigen()
  {
    try
    {
      VariablesDelivery.vCodLocalOrigen = DBDelivery.obtieneLocalOrigen(VariablesDelivery.vNumeroPedido);
      System.out.println("VariablesDelivery.vCodLocalOrigen : " + VariablesDelivery.vCodLocalOrigen);
    } catch (SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrio un error al obtener el codigo de origen \n"+sql.getMessage(),tblListaUltimos);
      return null;
    }
    return VariablesDelivery.vCodLocalOrigen;
  }
  
  private void muestraListaDetallePedido()
  {
    
    //DUBILLUZ - 16.12.2009
    //Variables para consultar si Tiene todos los Lotes Ingresados
    VariablesDelivery.vNumeroPedido_bk = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),1);
    VariablesDelivery.vCodLocal_bk = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),9);
    
    DlgListaDetallePedido dlgListaDetallePedido =  new DlgListaDetallePedido(myParentFrame,"",true);
    dlgListaDetallePedido.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      generaPedidoInstitucionalAutomatico();
    }
    VariablesDelivery.vNumeroPedido_bk = "";
    VariablesDelivery.vCodLocal_bk = "";
    
  }
  
  private void obtieneValores()
  {
    VariablesDelivery.vNumeroPedido = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),1);
    VariablesDelivery.vNombre = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),6);
  }
  
  private void generaPedidoInstitucionalAutomatico()
  {
    String estadoPedido = "";
    if(tblListaUltimos.getRowCount() <= 0) return;
    try
    {
      estadoPedido = obtieneEstadoPedido_ForUpdate();
      if(!estadoPedido.equalsIgnoreCase(ConstantsVentas.ESTADO_PEDIDO_PENDIENTE))
      {
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(this, "El pedido No se encuentra pendiente. Verifique!!!", tblListaUltimos);
        actualizaListaPedidos();
        return;
      }
      //if(!FarmaUtility.rptaConfirmDialog(this, "Está seguro de generar el pedido?")) return;
      VariablesDelivery.vNumeroPedido = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),1);
      VariablesDelivery.vCodLocal = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),9);
      VariablesVentas.vNum_Ped_Vta = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO,10);
      VariablesVentas.vNum_Ped_Diario = obtieneNumeroPedidoDiario();
      System.out.println("VariablesVentas.vNum_Ped_Vta : " + VariablesVentas.vNum_Ped_Vta);
      System.out.println("VariablesVentas.vNum_Ped_Diario : " + VariablesVentas.vNum_Ped_Diario);
      VariablesVentas.vVal_Neto_Ped = FarmaUtility.getValueFieldJTable(tblListaUltimos,tblListaUltimos.getSelectedRow(),3);
      DBDelivery.generaPedidoInstitucionalAutomatico();
      DBDelivery.actualuizaValoresDa();
      FarmaUtility.aceptarTransaccion();
      eliminaVtaInstiProd();
      
      // -- Se modifico para caja multifuncional
      // dubilluz  02/05/2008
      if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) ) {
           VariablesCaja.vNumPedPendiente = VariablesVentas.vNum_Ped_Diario;
           muestraCobroPedido();
      } else  muestraPedidoRapido();

      
      //muestraPedidoRapido();
    } catch(SQLException sqlex)
    {
      FarmaUtility.liberarTransaccion();
      sqlex.printStackTrace();
      FarmaUtility.showMessage(this, "Error al grabar el pedido institucional automático - \n" + sqlex, tblListaUltimos);
    }
  }
  
  private void eliminaVtaInstiProd()
  {
    try
    {
      for(int i=0; i<tblDetalle.getRowCount(); i++)
      {
        VariablesDelivery.vCodProducto = FarmaUtility.getValueFieldJTable(tblDetalle, i, 0);
        DBDelivery.eliminaDetalleProducto(VariablesDelivery.vNumeroPedido, VariablesDelivery.vCodProducto);
      }
      FarmaUtility.aceptarTransaccion();
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error al eliminar detalle Producto Temporal- \n" + sql,tblListaUltimos);
    }
  }
  
  private void muestraCobroPedido()
  {
    DlgFormaPago dlgFormaPago = new DlgFormaPago(myParentFrame,"",true);
    dlgFormaPago.setIndPedirLogueo(false);
    dlgFormaPago.setIndPantallaCerrarAnularPed(true);
    dlgFormaPago.setIndPantallaCerrarCobrarPed(true);
    dlgFormaPago.setVisible(true);
    if ( FarmaVariables.vAceptar ){
      FarmaVariables.vAceptar = false;
      cerrarVentana(true);
    } 
  }  
  
}