package mifarma.ptoventa.caja;

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
import java.sql.*;
import java.util.ArrayList;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.*;
import mifarma.common.FarmaBlinkJLabel;
import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.ptoventa.caja.reference.*;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.ventas.*;
import mifarma.ptoventa.ventas.reference.*;
import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgPedidosBuscar extends JDialog
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
	private static final Logger log = LoggerFactory.getLogger(DlgPedidosBuscar.class);

  Frame myParentFrame;
  FarmaTableModel tableModelDetallePedido;
  FarmaTableModel tableModelListaPendientes;
  private String numPedNuevo = "";
  private String numPedDiaNuevo = "";

  ActionMap actionMap1 = new ActionMap();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JPanel jPanel1 = new JPanel();
  JButton btnPedido = new JButton();
  JButton btnFechaPedido = new JButton();
  JTextField txtFechaPedido = new JTextField();
  JButton btnBuscar = new JButton();
  JTextField txtNumeroPedidoDia = new JTextField();
  JLabel lblSolesT = new JLabel();
  JLabel lblDolaresT = new JLabel();
  JLabel lblDolares = new JLabel();
  JLabel lblSoles = new JLabel();
  JLabel lblTipoCambioT = new JLabel();
  JLabel lblTipoCambio = new JLabel();
  JLabel lblTotalT = new JLabel();
  JLabelFunction lblF8 = new JLabelFunction();
  JScrollPane scrPendientes = new JScrollPane();
  JPanel pnlRelacion = new JPanel();
  XYLayout xYLayout2 = new XYLayout();
  JLabel lblTipoPedido = new FarmaBlinkJLabel();
  JLabel lblNumeroPendientes = new JLabel();
  JLabel lblPendientesT = new JLabel();
  JLabel lblModo = new JLabel();
  JScrollPane scrDetalle = new JScrollPane();
  JPanel pnlItems = new JPanel();
  XYLayout xYLayout3 = new XYLayout();
  JButton btnDetalle = new JButton();
  JLabel lblItemsT = new JLabel();
  JLabel lblItems = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabelFunction lblEsc = new JLabelFunction();
  JLabelFunction lblF9 = new JLabelFunction();
  JLabelFunction lblF11 = new JLabelFunction();
  JTable tblDetalle = new JTable();
  JTable tblListaPendientes = new JTable();
  private JButtonLabel btnCabecera = new JButtonLabel();
  
  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgPedidosBuscar()
  {
    this(null, "", false);
  }

  public DlgPedidosBuscar(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try {
      jbInit();
      initialize();
    } catch(Exception e) {
      log.error("",e);
    }

  }

  /* ************************************************************************ */
	/*                                  METODO jbInit                           */
	/* ************************************************************************ */

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(772, 394));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Unir Pendiente");
    this.setFont(new Font("SansSerif", 0, 11));
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
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(762, 361));
    jContentPane.setBackground(Color.white);
    jContentPane.setForeground(Color.white);
    jPanel1.setBounds(new Rectangle(10, 5, 740, 35));
    jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jPanel1.setBackground(new Color(43, 141, 39));
    jPanel1.setLayout(null);
    btnPedido.setText("No. Pedido :");
    btnPedido.setBounds(new Rectangle(5, 5, 65, 25));
    btnPedido.setFont(new Font("SansSerif", 0, 11));
    btnPedido.setHorizontalAlignment(SwingConstants.LEFT);
    btnPedido.setMnemonic('p');
    btnPedido.setRequestFocusEnabled(false);
    btnPedido.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnPedido.setBackground(new Color(43, 141, 39));
    btnPedido.setForeground(Color.white);
    btnPedido.setBorderPainted(false);
    btnPedido.setContentAreaFilled(false);
    btnPedido.setDefaultCapable(false);
    btnPedido.setFocusPainted(false);
    btnPedido.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPedido_actionPerformed(e);
        }
      });
    btnFechaPedido.setText("Fecha :");
    btnFechaPedido.setBounds(new Rectangle(155, 5, 40, 25));
    btnFechaPedido.setFont(new Font("SansSerif", 0, 11));
    btnFechaPedido.setHorizontalAlignment(SwingConstants.LEFT);
    btnFechaPedido.setMnemonic('f');
    btnFechaPedido.setRequestFocusEnabled(false);
    btnFechaPedido.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnFechaPedido.setBackground(new Color(43, 141, 39));
    btnFechaPedido.setForeground(Color.white);
    btnFechaPedido.setBorderPainted(false);
    btnFechaPedido.setContentAreaFilled(false);
    btnFechaPedido.setDefaultCapable(false);
    btnFechaPedido.setFocusPainted(false);
    btnFechaPedido.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnFechaPedido_actionPerformed(e);
        }
      });
    txtFechaPedido.setBounds(new Rectangle(205, 5, 80, 25));
    txtFechaPedido.setDocument(new FarmaLengthText(10));
    txtFechaPedido.setHorizontalAlignment(JTextField.LEFT);
    txtFechaPedido.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtFechaPedido_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          txtFechaPedido_keyReleased(e);
        }
      });
    btnBuscar.setText("Buscar");
    btnBuscar.setFont(new Font("SansSerif", 0, 11));
    btnBuscar.setMnemonic('b');
    btnBuscar.setRequestFocusEnabled(false);
    btnBuscar.setBounds(new Rectangle(295, 5, 90, 25));
    btnBuscar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnBuscar_actionPerformed(e);
        }
      });
    txtNumeroPedidoDia.setBounds(new Rectangle(70, 5, 70, 25));
    txtNumeroPedidoDia.setDocument(new FarmaLengthText(4));
    txtNumeroPedidoDia.setHorizontalAlignment(JTextField.LEFT);
    txtNumeroPedidoDia.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtNumeroPedido_keyPressed(e);
        }
      });
    lblSolesT.setText("S/.");
    lblSolesT.setFont(new Font("SansSerif", 1, 11));
    lblSolesT.setBounds(new Rectangle(460, 10, 25, 15));
    lblSolesT.setForeground(Color.white);
    lblDolaresT.setText("US $");
    lblDolaresT.setFont(new Font("SansSerif", 1, 11));
    lblDolaresT.setBounds(new Rectangle(535, 10, 35, 15));
    lblDolaresT.setForeground(Color.white);
    lblDolares.setText("0.00");
    lblDolares.setFont(new Font("Dialog", 1, 11));
    lblDolares.setBounds(new Rectangle(570, 10, 45, 15));
    lblDolares.setForeground(Color.white);
    lblSoles.setText("0.00");
    lblSoles.setFont(new Font("SansSerif", 1, 11));
    lblSoles.setBounds(new Rectangle(480, 10, 50, 15));
    lblSoles.setForeground(Color.white);
    lblTipoCambioT.setText("CAMBIO :");
    lblTipoCambioT.setFont(new Font("SansSerif", 1, 11));
    lblTipoCambioT.setForeground(Color.white);
    lblTipoCambioT.setBounds(new Rectangle(625, 10, 55, 15));
    lblTipoCambio.setText("0.00");
    lblTipoCambio.setFont(new Font("SansSerif", 1, 11));
    lblTipoCambio.setHorizontalAlignment(SwingConstants.CENTER);
    lblTipoCambio.setBounds(new Rectangle(690, 10, 35, 15));
    lblTipoCambio.setForeground(Color.white);
    lblTotalT.setText("TOTAL :");
    lblTotalT.setFont(new Font("SansSerif", 1, 11));
    lblTotalT.setForeground(Color.white);
    lblTotalT.setBounds(new Rectangle(390, 10, 50, 15));
    lblF8.setText("[ F8 ]  Deseleccionar Pedido");
    lblF8.setBounds(new Rectangle(145, 335, 190, 20));
    scrPendientes.setFont(new Font("SansSerif", 0, 11));
    scrPendientes.setBounds(new Rectangle(10, 70, 740, 70));
    scrPendientes.setBackground(new Color(255, 130, 14));
    pnlRelacion.setBackground(new Color(255, 130, 14));
    pnlRelacion.setLayout(xYLayout2);
    pnlRelacion.setFont(new Font("SansSerif", 0, 11));
    pnlRelacion.setBounds(new Rectangle(10, 45, 740, 25));
    lblTipoPedido.setFont(new Font("SansSerif", 1, 11));
    lblTipoPedido.setForeground(Color.white);
    lblNumeroPendientes.setText("0");
    lblNumeroPendientes.setForeground(Color.white);
    lblNumeroPendientes.setFont(new Font("SansSerif", 1, 11));
    lblNumeroPendientes.setHorizontalAlignment(SwingConstants.RIGHT);
    lblPendientesT.setText("pendientes");
    lblPendientesT.setFont(new Font("SansSerif", 1, 11));
    lblPendientesT.setForeground(Color.white);
    lblModo.setFont(new Font("SansSerif", 1, 11));
    lblModo.setForeground(Color.white);
    lblModo.setHorizontalAlignment(SwingConstants.CENTER);
    scrDetalle.setFont(new Font("SansSerif", 0, 11));
    scrDetalle.setBounds(new Rectangle(10, 170, 740, 140));
    scrDetalle.setBackground(new Color(255, 130, 14));
    pnlItems.setBackground(new Color(255, 130, 14));
    pnlItems.setFont(new Font("SansSerif", 0, 11));
    pnlItems.setLayout(xYLayout3);
    pnlItems.setBounds(new Rectangle(10, 145, 740, 25));
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
    btnDetalle.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnDetalle_keyPressed(e);
        }
      });
    btnDetalle.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDetalle_actionPerformed(e);
        }
      });
    lblItemsT.setText("Items");
    lblItemsT.setFont(new Font("SansSerif", 1, 11));
    lblItemsT.setForeground(Color.white);
    lblItems.setText("0");
    lblItems.setFont(new Font("SansSerif", 1, 11));
    lblItems.setForeground(Color.white);
    lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("Opciones :");
    jLabel3.setFont(new Font("SansSerif", 1, 11));
    jLabel3.setBounds(new Rectangle(10, 315, 70, 15));
    lblEsc.setText("[ Esc ]  Cerrar");
    lblEsc.setBounds(new Rectangle(650, 335, 95, 20));
    lblF9.setText("[ F9 ]  Pedidos Pendientes");
    lblF9.setBounds(new Rectangle(345, 335, 165, 20));
    lblF11.setText("[ F11 ]  Aceptar");
    lblF11.setBounds(new Rectangle(525, 335, 115, 20));
    tblDetalle.setFont(new Font("SansSerif", 0, 11));
    tblListaPendientes.setFont(new Font("SansSerif", 0, 11));
    btnCabecera.setText("Pedido pendiente de Cobranza :");
    btnCabecera.setMnemonic('e');
    btnCabecera.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnCabecera_keyPressed(e);
        }
      });
    btnCabecera.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCabecera_actionPerformed(e);
        }
      });
    jPanel1.add(btnPedido, null);
    jPanel1.add(btnFechaPedido, null);
    jPanel1.add(txtFechaPedido, null);
    jPanel1.add(btnBuscar, null);
    jPanel1.add(txtNumeroPedidoDia, null);
    jPanel1.add(lblSolesT, null);
    jPanel1.add(lblDolaresT, null);
    jPanel1.add(lblDolares, null);
    jPanel1.add(lblSoles, null);
    jPanel1.add(lblTipoCambioT, null);
    jPanel1.add(lblTipoCambio, null);
    jPanel1.add(lblTotalT, null);
    scrPendientes.getViewport();
    pnlRelacion.add(btnCabecera, new XYConstraints(10, 5, 205, 15));
    pnlRelacion.add(lblTipoPedido, new XYConstraints(210, 5, 175, 15));
    pnlRelacion.add(lblNumeroPendientes, new XYConstraints(585, 5, 50, 15));
    pnlRelacion.add(lblPendientesT, new XYConstraints(645, 5, 75, 15));
    pnlRelacion.add(lblModo, new XYConstraints(395, 5, 210, 15));
    scrDetalle.getViewport();
    pnlItems.add(btnDetalle, new XYConstraints(10, 5, 125, 15));
    pnlItems.add(lblItemsT, new XYConstraints(675, 5, 45, 15));
    pnlItems.add(lblItems, new XYConstraints(630, 5, 35, 15));
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    jContentPane.add(jPanel1, null);
    jContentPane.add(lblF8, null);
    scrPendientes.getViewport().add(tblListaPendientes, null);
    jContentPane.add(scrPendientes, null);
    jContentPane.add(pnlRelacion, null);
    scrDetalle.getViewport().add(tblDetalle, null);
    jContentPane.add(scrDetalle, null);
    jContentPane.add(pnlItems, null);
    jContentPane.add(jLabel3, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(lblF9, null);
    jContentPane.add(lblF11, null);
    //this.getContentPane().add(jContentPane, null);
  }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initCabecera();
    initTableListaPendientes();
    initTableDetallePedido();
    FarmaVariables.vAceptar=false;
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initCabecera()
  {
    try
    {
      String fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        (new FacadeRecaudacion()).obtenerTipoCambio();
      VariablesCaja.vValTipoCambioPedido = FarmaVariables.vTipCambio+"";
      txtFechaPedido.setText(fechaSistema);
      lblTipoCambio.setText(VariablesCaja.vValTipoCambioPedido);
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,sql.getMessage(),txtFechaPedido);
    }    
  }
  
  private void initTableListaPendientes()
  {
    tableModelListaPendientes = new FarmaTableModel(ConstantsCaja.columnsListaPendientesUnir,ConstantsCaja.defaultListaPendientesUnir,0);
    FarmaUtility.initSimpleList(tblListaPendientes,tableModelListaPendientes,ConstantsCaja.columnsListaPendientesUnir);
  }
   
  private void cargaListaPendientes(ArrayList myArray)
  {
    tableModelListaPendientes.insertRow(myArray);
    tableModelDetallePedido.clearTable();
  }
  
  private void initTableDetallePedido()
  {
    tableModelDetallePedido = new FarmaTableModel(ConstantsCaja.columnsDetallePedidoUnir,ConstantsCaja.defaultDetallePedidoUnir,0);
    FarmaUtility.initSimpleList(tblDetalle,tableModelDetallePedido,ConstantsCaja.columnsDetallePedidoUnir);
  }  
  
  private void cargaDetallePedido()
  {
    
    try{
      tableModelDetallePedido.clearTable();
      DBCaja.getDetallePedidoUnir(tableModelDetallePedido,VariablesCaja.vNumPedPendiente);
      lblItems.setText(tblDetalle.getRowCount()+"");
    }catch(SQLException e)
    {
      log.error("",e);
      FarmaUtility.showMessage(this,e.getMessage(),txtFechaPedido);
    }
  }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnPedido_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtNumeroPedidoDia);
  }
  
  private void txtNumeroPedido_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtFechaPedido);
      txtNumeroPedidoDia.setText(FarmaUtility.completeWithSymbol(txtNumeroPedidoDia.getText(), 4, "0", "I"));
    }
    else
      chkKeyPressed(e);
  }

  private void btnFechaPedido_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtFechaPedido);
  }
  
  private void txtFechaPedido_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      btnBuscar.doClick();
      initCabecera();
    }
    else
      chkKeyPressed(e);  
  }
  
  private void txtFechaPedido_keyReleased(KeyEvent e)
  {
    FarmaUtility.dateComplete(txtFechaPedido,e);
  }
  
  private void btnBuscar_actionPerformed(ActionEvent e)
  {
    if(validaCamposBuscar())
    {
      buscarPedido(txtNumeroPedidoDia.getText(),txtFechaPedido.getText());  
    }
  }

  private void btnCabecera_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(btnCabecera);
  }

  private void btnCabecera_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e,tblListaPendientes,null,0);
    if(tblListaPendientes.getSelectedRow()>-1)
    {
      VariablesCaja.vNumPedPendiente = tblListaPendientes.getValueAt(tblListaPendientes.getSelectedRow(),1).toString();
      cargaDetallePedido();
    }
  
    if(e.getKeyCode() == KeyEvent.VK_F8)
    {
      tableModelListaPendientes.deleteRow(tblListaPendientes.getSelectedRow());
      calcularTotales();
      lblNumeroPendientes.setText(tblListaPendientes.getRowCount()+"");
      tableModelDetallePedido.clearTable();
    }
  
    chkKeyPressed(e);
  }
  
  private void btnDetalle_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(btnDetalle);
  }
  
  private void btnDetalle_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e,tblDetalle,null,0);
  }
  
    private void this_windowOpened(WindowEvent e)
    {
        //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
        if(FarmaVariables.vTipCambio==0)
        {   FarmaUtility.showMessage(this, 
                                  "ATENCIÓN: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acción", 
                                  null);
            cerrarVentana(false);
        }
        else
        {
            FarmaUtility.centrarVentana(this);
            FarmaUtility.moveFocus(txtNumeroPedidoDia);
        }
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
    if(e.getKeyCode() == KeyEvent.VK_F9)
    {
      mostrarPedidoPendientes();
    } 
    else if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
    {
      if(validarPedidos())
      {
        if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Esta seguro que desea unir los pedidos?"))
        {
          if(unirPedidos())
          {
            FarmaUtility.showMessage(this,"Los pedidos seleccionados se han unido correctamente.",null);
            VariablesCaja.vNumPedPendiente = numPedDiaNuevo.trim();
            VariablesVentas.vNum_Ped_Diario=numPedDiaNuevo.trim();
            VariablesVentas.vVal_Neto_Ped=lblSoles.getText();
            
            DlgNumeroPedidoGenerado dlgNumeroPedidoGenerado = new DlgNumeroPedidoGenerado(myParentFrame,"",true);
            dlgNumeroPedidoGenerado.setVisible(true);
            
            cerrarVentana(true);
          }  
        }
      }
    }
    else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
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

  private boolean validaCamposBuscar()
  {
    boolean retorno = true;
    if(txtNumeroPedidoDia.getText().trim().equals(""))
    {
      FarmaUtility.showMessage(this,"Debe ingresar un numero de Pedido a buscar.",txtNumeroPedidoDia);
      retorno = false;
    }else if(txtFechaPedido.getText().trim().equals(""))
    {
      FarmaUtility.showMessage(this,"Debe ingresar una Fecha de Pedido a buscar.",txtFechaPedido);
      retorno = false;
    }else if(!FarmaUtility.validaFecha(txtFechaPedido.getText(),"dd/MM/yyyy"))
    {
      FarmaUtility.showMessage(this,"Debe ingresar una Fecha de Pedido valida.",txtFechaPedido);
      retorno = false;
    }
    return retorno;
  }

  private void buscarPedido(String numeroPedidoDia, String fechaPedido)
  {
    try
    {
      ArrayList array = new ArrayList();
      DBCaja.buscarPedidoUnir(array,numeroPedidoDia,fechaPedido);
      if(array.size()==0)
      {
        FarmaUtility.showMessage(this,"El Pedido No existe o No se encuentra pendiente de pago.",txtNumeroPedidoDia);
      }
      else
      {
        array = (ArrayList)array.get(0);
        if(buscarPedidoEnListado(array.get(0).toString(),array.get(1).toString()))
          cargaListaPendientes(array);
        calcularTotales();
        lblNumeroPendientes.setText(tblListaPendientes.getRowCount()+"");
        txtNumeroPedidoDia.setText("");
        txtFechaPedido.setText("");
        FarmaUtility.moveFocus(txtNumeroPedidoDia);
      }
    }catch(SQLException e)
    {
      log.error("",e);
      FarmaUtility.showMessage(this,e.getMessage(),txtFechaPedido);
    }
  }
  
  private void calcularTotales()
  {
    double soles = FarmaUtility.sumColumTable(tblListaPendientes,3);
    lblSoles.setText(soles+"");
    double dolares = soles/Double.parseDouble(VariablesCaja.vValTipoCambioPedido);
    String valDolares = dolares+"";
    valDolares = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(valDolares) + FarmaUtility.getRedondeo(FarmaUtility.getDecimalNumber(valDolares)));
    lblDolares.setText(valDolares);
  }

  private void mostrarPedidoPendientes()
  {
    DlgPedidosPendientes dlgPedidosPendientes = new DlgPedidosPendientes(myParentFrame,"",true);
    dlgPedidosPendientes.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      //log.debug(VariablesCaja.vNumPedPendiente);
      //log.debug(VariablesCaja.vFecPedACobrar.substring(0,10));
      txtNumeroPedidoDia.setText(VariablesCaja.vNumPedPendiente);
      txtFechaPedido.setText(VariablesCaja.vFecPedACobrar.substring(0,10));
      if(buscarPedidoEnListado(VariablesCaja.vNumPedPendiente,VariablesCaja.vNumPedVta))
        buscarPedido(VariablesCaja.vNumPedPendiente,VariablesCaja.vFecPedACobrar.substring(0,10));
    }
  }
  
  private boolean unirPedidos()
  {
    boolean retorno;
    try
    {
      String tipPed="";
      String tipComp="";
       
      ArrayList array = new ArrayList();
      
      tipComp=tblListaPendientes.getValueAt(0,7).toString().trim();
      tipPed=tblListaPendientes.getValueAt(0,8).toString().trim();
      
      DBCaja.getNumeroPedidoUnir(array,lblSoles.getText(),lblTipoCambio.getText(),tipComp,tipPed);
      array = (ArrayList)array.get(0);
      numPedNuevo = array.get(0).toString();
      numPedDiaNuevo = array.get(1).toString();
      
      for(int i=0;i<tblListaPendientes.getRowCount();i++)
      {
        String numPed = tblListaPendientes.getValueAt(i,1).toString();
        DBCaja.unirComprobante(numPedNuevo,numPed);
      }
      FarmaUtility.aceptarTransaccion();
      retorno = true;
    }catch(SQLException e)
    {
      FarmaUtility.liberarTransaccion();
      retorno = false;
      log.error("",e);
      if(e.getErrorCode() == 20032)
        FarmaUtility.showMessage(this,"No puede unir estos pedidos porque contienen productos virtuales. Verifique!!!",txtFechaPedido);
      else
      FarmaUtility.showMessage(this,e.getMessage(),txtFechaPedido);
    }
    return retorno;
  }
  
  private boolean validarPedidos()
  {
    boolean retorno=true;
    if(tblListaPendientes.getRowCount()<2)
    {
      FarmaUtility.showMessage(this,"Debe seleccionar mas de un pedido",txtNumeroPedidoDia);      
      retorno = false;
    }else
    {
      String tipDoc = ((ArrayList)tableModelListaPendientes.data.get(0)).get(7).toString();
      String tipDocAux;
      for(int i=0;i<tableModelListaPendientes.data.size();i++)
      {
        tipDocAux = (((ArrayList)tableModelListaPendientes.data.get(i)).get(7).toString());
        if(!tipDoc.equals(tipDocAux))
        {
          FarmaUtility.showMessage(this,"No puede unir pedidos de distintos tipos de documentos.",txtNumeroPedidoDia);
          retorno = false;
          break;
        }
      }

      if(retorno){
       //Comprobacion de tipo de Pedido
       String tipPed = ((ArrayList)tableModelListaPendientes.data.get(0)).get(8).toString();
       String tipoConv  =((ArrayList)tableModelListaPendientes.data.get(0)).get(9).toString();
       String tipConvAux ;
       String tipPedAux;
        for(int i=0;i<tableModelListaPendientes.data.size();i++)
        {
          tipPedAux = (((ArrayList)tableModelListaPendientes.data.get(i)).get(8).toString());
          tipConvAux = (((ArrayList)tableModelListaPendientes.data.get(i)).get(9).toString()); 
          if(!tipPed.equals(tipPedAux))
          {
            FarmaUtility.showMessage(this,"No puede unir pedidos de distintos tipos.",txtNumeroPedidoDia);
            retorno = false;
            break;
          }
          if(tipConvAux.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
          {
            FarmaUtility.showMessage(this,"No puede unir pedidos pedidos del tipo Convenio.",txtNumeroPedidoDia);
            retorno = false;
            break;
          }
          /*if(tipoConv.equals(tipConvAux))
          {
            FarmaUtility.showMessage(this,"No puede unir dos pedidos del tipo Convenio.",txtNumeroPedidoDia);
            retorno = false;
            break;
          }*/
        }
      }
     
      if(retorno && tipDoc.equals(ConstantsPtoVenta.TIP_COMP_FACTURA))
      {
        String rucDoc = ((ArrayList)tableModelListaPendientes.data.get(0)).get(4).toString();
        String rucDocAux;
        for(int i=0;i<tableModelListaPendientes.data.size();i++)
        {
          rucDocAux = (((ArrayList)tableModelListaPendientes.data.get(i)).get(4).toString());
          if(!rucDoc.equals(rucDocAux))
          {
            FarmaUtility.showMessage(this,"No puede unir pedidos de distintos clientes.",txtNumeroPedidoDia);
            retorno = false;
            break;
          }
        }  
      }
    }
    return retorno;
  }

  private boolean buscarPedidoEnListado(String vNumPedPendiente,String vNumPedVta)
  {
    boolean retorno = true;
    for(int i=0;i<tableModelListaPendientes.data.size();i++)
    {
      if(((ArrayList)tableModelListaPendientes.data.get(i)).contains(vNumPedPendiente) &&
          ((ArrayList)tableModelListaPendientes.data.get(i)).contains(vNumPedVta) )
      {
        retorno = false;
        FarmaUtility.showMessage(this,"EL Pedido seleccionado, ya está agregado para unir. Seleccione otro.",txtNumeroPedidoDia);
      }
    }
    return retorno;
  }
}
