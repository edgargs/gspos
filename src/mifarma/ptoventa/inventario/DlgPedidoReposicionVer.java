package mifarma.ptoventa.inventario;
import java.awt.Frame;
import java.awt.Dimension;
import java.sql.*;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import java.awt.Rectangle;
import com.gs.mifarma.componentes.JPanelTitle;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.gs.mifarma.componentes.JLabelWhite;
import javax.swing.JSplitPane;
import javax.swing.BorderFactory;
import java.awt.Color;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import javax.swing.*;
import javax.swing.SwingConstants;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import java.awt.Font;
import mifarma.common.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.*;
import mifarma.ptoventa.*;
import mifarma.ptoventa.inventario.reference.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import mifarma.ptoventa.reference.*;
import com.gs.mifarma.componentes.JLabelOrange;
import javax.swing.JLabel;

public class DlgPedidoReposicionVer extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */

  Frame myParentFrame;
  FarmaTableModel tableModel; 
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrListaProductos = new JScrollPane();
  private JTable tblListaProductos = new JTable();
  private JPanelTitle pnlTitle2 = new JPanelTitle();
  private JPanelTitle pnlTitle3 = new JPanelTitle();
  private JLabelWhite lblMinExhib_T = new JLabelWhite();
  private JPanelWhite pnlWhite2 = new JPanelWhite();
  private JLabelWhite lblLaboratorio = new JLabelWhite();
  private JPanelWhite pnlWhite3 = new JPanelWhite();
  private JButtonLabel btnRelacionProductos = new JButtonLabel();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JPanelWhite pnlWhite4 = new JPanelWhite();
  private JPanelTitle pnlTitle4 = new JPanelTitle();
  private JLabelWhite lblRotacion_T = new JLabelWhite();
  private JLabelWhite lblNoDias_T = new JLabelWhite();
  private JLabelWhite lblLaboratorio_T = new JLabelWhite();
  private JLabelWhite lblTransito_T = new JLabelWhite();
  private JLabelWhite lblMinDias_T = new JLabelWhite();
  private JLabelWhite lblMaxDias_T = new JLabelWhite();
  private JLabelWhite lblNDia_T = new JLabelWhite();
  private JLabelWhite lblRotProm_T = new JLabelWhite();
  private JLabelWhite lblCantAnterior_T = new JLabelWhite();
  private JLabelWhite lblMinExhib = new JLabelWhite();
  private JLabelWhite lblTransito = new JLabelWhite();
  private JLabelWhite lblMinDias = new JLabelWhite();
  private JLabelWhite lblMaxDias = new JLabelWhite();
  private JLabelWhite lblNDias = new JLabelWhite();
  private JLabelWhite lblRotProm = new JLabelWhite();
  private JLabelWhite lblCantAnterior = new JLabelWhite();
  private JLabelWhite lblProductos = new JLabelWhite();
  private JLabelWhite lblItemsPedidoActual = new JLabelWhite();
  private JLabelWhite lbl120d = new JLabelWhite();
  private JLabelWhite lbl90d = new JLabelWhite();
  private JLabelWhite lbl60d = new JLabelWhite();
  private JLabelWhite lbl30d = new JLabelWhite();
  private JLabelWhite lblItems_T = new JLabelWhite();
  private JLabelWhite lblProductos_T = new JLabelWhite();
  private JLabelWhite lbl120d_T = new JLabelWhite();
  private JLabelWhite lbl90d_T = new JLabelWhite();
  private JLabelWhite lbl60d_T = new JLabelWhite();
  private JLabelWhite lbl30d_T = new JLabelWhite();
  private JButtonLabel btnBuscar = new JButtonLabel();
  private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
  private JLabelWhite lblRotacionProm_T = new JLabelWhite();
  private JLabelWhite lblMinimoDias_T = new JLabelWhite();
  private JLabelWhite lblDias = new JLabelWhite();
  private JLabelWhite lblMaximoDias_T = new JLabelWhite();
  private JLabelWhite lblRotacionPromedio = new JLabelWhite();
  private JLabelWhite lblMinimoDias = new JLabelWhite();
  private JLabelWhite lblMaximoDias = new JLabelWhite();
  private JLabelFunction lblF6 = new JLabelFunction();
  private JLabelFunction lblF7 = new JLabelFunction();
  private JPanelWhite pnlWhite5 = new JPanelWhite();
  private JLabelWhite lblItemsUltimoPedido = new JLabelWhite();
  private JLabelWhite lblProductosUltimoPedido = new JLabelWhite();
  private JLabelWhite lblUltimoPedido_T = new JLabelWhite();
  private JLabelWhite lblPedidoActual_T = new JLabelWhite();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblF9 = new JLabelFunction();
  private JLabelFunction lblFOrdenar = new JLabelFunction();
  private JLabelOrange lblMensaje = new JLabelOrange();
  private JPanelWhite pnlWhite6 = new JPanelWhite();
  private JLabelWhite lblStockLocales = new JLabelWhite();
  private JLabelWhite lblStockAlmacen = new JLabelWhite();
  private JPanelTitle pnlTitle5 = new JPanelTitle();
  private JLabelWhite lblStockAlmacenT = new JLabelWhite();
  private JLabelWhite lblStockLocalesT = new JLabelWhite();
  private JLabelFunction lblFAdicionales = new JLabelFunction();
  private JLabel lblIndTipoProd = new JLabel();
  private JLabelFunction lblF8 = new JLabelFunction();

  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgPedidoReposicionVer()
  {
    this(null, "", false);
  }

  public DlgPedidoReposicionVer(Frame parent, String title, boolean modal)
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
	/*                                  METODO jbInit                           */
	/* ************************************************************************ */

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(727, 531));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Pedido de Reposicion Final");
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
    pnlHeader1.setBounds(new Rectangle(10, 10, 695, 50));
    pnlTitle1.setBounds(new Rectangle(10, 65, 695, 20));
    scrListaProductos.setBounds(new Rectangle(10, 85, 695, 180));
    pnlTitle2.setBounds(new Rectangle(10, 270, 690, 20));
    pnlTitle3.setBounds(new Rectangle(10, 320, 350, 20));
    lblMinExhib_T.setText("Min Exhib");
    lblMinExhib_T.setBounds(new Rectangle(155, 5, 60, 15));
    lblMinExhib_T.setHorizontalAlignment(SwingConstants.CENTER);
    pnlWhite2.setBounds(new Rectangle(10, 290, 690, 20));
    pnlWhite2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lblLaboratorio.setText("LABORATORIO");
    lblLaboratorio.setBounds(new Rectangle(5, 5, 145, 15));
    lblLaboratorio.setForeground(Color.black);
    pnlWhite3.setBounds(new Rectangle(10, 340, 350, 20));
    pnlWhite3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    btnRelacionProductos.setText("Relación de Productos");
    btnRelacionProductos.setBounds(new Rectangle(10, 0, 135, 20));
    btnRelacionProductos.setMnemonic('R');
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(515, 455, 85, 20));
    pnlWhite4.setBounds(new Rectangle(445, 340, 255, 20));
    pnlWhite4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    pnlTitle4.setBounds(new Rectangle(445, 320, 255, 20));
    lblRotacion_T.setText("Rotación");
    lblRotacion_T.setBounds(new Rectangle(5, 5, 70, 15));
    lblRotacion_T.setForeground(Color.black);
    lblNoDias_T.setText("No Días");
    lblNoDias_T.setBounds(new Rectangle(5, 5, 70, 15));
    lblLaboratorio_T.setText("Laboratorio");
    lblLaboratorio_T.setBounds(new Rectangle(5, 5, 70, 15));
    lblTransito_T.setText("Transito");
    lblTransito_T.setBounds(new Rectangle(215, 5, 55, 15));
    lblTransito_T.setHorizontalAlignment(SwingConstants.CENTER);
    lblMinDias_T.setText("Min Días");
    lblMinDias_T.setBounds(new Rectangle(275, 5, 55, 15));
    lblMinDias_T.setHorizontalAlignment(SwingConstants.CENTER);
    lblMaxDias_T.setText("Max Días");
    lblMaxDias_T.setBounds(new Rectangle(335, 5, 55, 15));
    lblMaxDias_T.setHorizontalAlignment(SwingConstants.CENTER);
    lblNDia_T.setText("N Dias");
    lblNDia_T.setBounds(new Rectangle(395, 5, 55, 15));
    lblNDia_T.setHorizontalAlignment(SwingConstants.CENTER);
    lblRotProm_T.setText("Rot Prom");
    lblRotProm_T.setBounds(new Rectangle(460, 5, 55, 15));
    lblRotProm_T.setHorizontalAlignment(SwingConstants.CENTER);
    lblCantAnterior_T.setText("Cant Anterior");
    lblCantAnterior_T.setBounds(new Rectangle(530, 5, 80, 15));
    lblCantAnterior_T.setHorizontalAlignment(SwingConstants.CENTER);
    lblMinExhib.setBounds(new Rectangle(155, 5, 55, 15));
    lblMinExhib.setForeground(Color.black);
    lblMinExhib.setHorizontalAlignment(SwingConstants.CENTER);
    lblMinExhib.setText("1");
    lblTransito.setBounds(new Rectangle(215, 5, 55, 15));
    lblTransito.setForeground(Color.black);
    lblTransito.setHorizontalAlignment(SwingConstants.CENTER);
    lblMinDias.setBounds(new Rectangle(275, 5, 55, 15));
    lblMinDias.setForeground(Color.black);
    lblMinDias.setHorizontalAlignment(SwingConstants.CENTER);
    lblMaxDias.setBounds(new Rectangle(335, 5, 55, 15));
    lblMaxDias.setForeground(Color.black);
    lblMaxDias.setHorizontalAlignment(SwingConstants.CENTER);
    lblNDias.setBounds(new Rectangle(395, 5, 55, 15));
    lblNDias.setForeground(Color.black);
    lblNDias.setHorizontalAlignment(SwingConstants.CENTER);
    lblRotProm.setBounds(new Rectangle(460, 5, 55, 15));
    lblRotProm.setForeground(Color.black);
    lblRotProm.setHorizontalAlignment(SwingConstants.CENTER);
    lblCantAnterior.setBounds(new Rectangle(545, 5, 55, 15));
    lblCantAnterior.setForeground(Color.black);
    lblCantAnterior.setHorizontalAlignment(SwingConstants.CENTER);
    lblProductos.setBounds(new Rectangle(170, 5, 55, 15));
    lblProductos.setForeground(Color.black);
    lblProductos.setHorizontalAlignment(SwingConstants.CENTER);
    lblProductos.setText("0");
    lblItemsPedidoActual.setBounds(new Rectangle(105, 5, 40, 15));
    lblItemsPedidoActual.setForeground(Color.black);
    lblItemsPedidoActual.setHorizontalAlignment(SwingConstants.CENTER);
    lblItemsPedidoActual.setText("0");
    lbl120d.setBounds(new Rectangle(105, 5, 55, 15));
    lbl120d.setForeground(Color.black);
    lbl120d.setHorizontalAlignment(SwingConstants.CENTER);
    lbl120d.setText("1");
    lbl90d.setBounds(new Rectangle(160, 5, 55, 15));
    lbl90d.setForeground(Color.black);
    lbl90d.setHorizontalAlignment(SwingConstants.CENTER);
    lbl90d.setText("1");
    lbl60d.setBounds(new Rectangle(225, 5, 55, 15));
    lbl60d.setForeground(Color.black);
    lbl60d.setHorizontalAlignment(SwingConstants.CENTER);
    lbl60d.setText("1");
    lbl30d.setBounds(new Rectangle(285, 5, 60, 15));
    lbl30d.setForeground(Color.black);
    lbl30d.setHorizontalAlignment(SwingConstants.CENTER);
    lbl30d.setText("1");
    lblItems_T.setText("Items");
    lblItems_T.setBounds(new Rectangle(110, 5, 45, 15));
    lblItems_T.setHorizontalAlignment(SwingConstants.CENTER);
    lblProductos_T.setText("Productos");
    lblProductos_T.setBounds(new Rectangle(165, 5, 60, 15));
    lblProductos_T.setHorizontalAlignment(SwingConstants.CENTER);
    lbl120d_T.setText("120 d.");
    lbl120d_T.setBounds(new Rectangle(100, 5, 60, 15));
    lbl120d_T.setHorizontalAlignment(SwingConstants.CENTER);
    lbl90d_T.setText("90 d.");
    lbl90d_T.setBounds(new Rectangle(160, 5, 60, 15));
    lbl90d_T.setHorizontalAlignment(SwingConstants.CENTER);
    lbl60d_T.setText("60 d.");
    lbl60d_T.setBounds(new Rectangle(220, 5, 60, 15));
    lbl60d_T.setHorizontalAlignment(SwingConstants.CENTER);
    lbl30d_T.setText("30 d.");
    lbl30d_T.setBounds(new Rectangle(285, 5, 60, 15));
    lbl30d_T.setHorizontalAlignment(SwingConstants.CENTER);
    btnBuscar.setText("Buscar");
    btnBuscar.setBounds(new Rectangle(15, 5, 55, 15));
    btnBuscar.setMnemonic('B');
    btnBuscar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnBuscar_actionPerformed(e);
        }
      });
    txtBuscar.setBounds(new Rectangle(75, 5, 180, 20));
    txtBuscar.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtBuscar_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          txtBuscar_keyReleased(e);
        }
      });
    lblRotacionProm_T.setText("Rotación Prom:");
    lblRotacionProm_T.setBounds(new Rectangle(285, 5, 90, 15));
    lblMinimoDias_T.setText("Mínimo de Días:");
    lblMinimoDias_T.setBounds(new Rectangle(285, 25, 95, 15));
    lblDias.setText("dia(s)");
    lblDias.setBounds(new Rectangle(415, 5, 100, 15));
    lblMaximoDias_T.setText("Máximo de Días:");
    lblMaximoDias_T.setBounds(new Rectangle(445, 25, 90, 15));
    lblRotacionPromedio.setBounds(new Rectangle(385, 5, 30, 15));
    lblRotacionPromedio.setFont(new Font("SansSerif", 0, 11));
    lblMinimoDias.setBounds(new Rectangle(385, 25, 50, 15));
    lblMinimoDias.setFont(new Font("SansSerif", 0, 11));
    lblMaximoDias.setBounds(new Rectangle(545, 25, 35, 15));
    lblMaximoDias.setFont(new Font("SansSerif", 0, 11));
    lblF6.setText("[ F6 ] Filtrar Productos");
    lblF6.setBounds(new Rectangle(175, 430, 145, 20));
    lblF7.setText("[ F7 ] Eliminar Filtro");
    lblF7.setBounds(new Rectangle(325, 430, 135, 20));
    pnlWhite5.setBounds(new Rectangle(445, 360, 255, 20));
    pnlWhite5.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lblItemsUltimoPedido.setBounds(new Rectangle(105, 5, 40, 15));
    lblItemsUltimoPedido.setForeground(Color.black);
    lblItemsUltimoPedido.setHorizontalAlignment(SwingConstants.CENTER);
    lblItemsUltimoPedido.setText("0");
    lblProductosUltimoPedido.setBounds(new Rectangle(170, 5, 55, 15));
    lblProductosUltimoPedido.setForeground(Color.black);
    lblProductosUltimoPedido.setHorizontalAlignment(SwingConstants.CENTER);
    lblProductosUltimoPedido.setText("0");
    lblUltimoPedido_T.setText("Último Pedido:");
    lblUltimoPedido_T.setBounds(new Rectangle(5, 5, 85, 15));
    lblUltimoPedido_T.setForeground(Color.black);
    lblPedidoActual_T.setText(" Pedido Actual:");
    lblPedidoActual_T.setBounds(new Rectangle(5, 5, 85, 15));
    lblPedidoActual_T.setForeground(Color.black);
    lblF11.setText("[ F11 ] Enviar Pedido");
    lblF11.setBounds(new Rectangle(330, 455, 145, 20));
    lblF9.setText("[ F9 ] Asignar Cantidad Maxima");
    lblF9.setBounds(new Rectangle(15, 455, 185, 20));
    lblFOrdenar.setBounds(new Rectangle(210, 455, 110, 20));
    lblFOrdenar.setText("[ F10 ] Ordenar");
    lblMensaje.setBounds(new Rectangle(10, 485, 575, 15));
    lblMensaje.setForeground(Color.red);
    lblMensaje.setLayout(null);
    pnlWhite6.setBounds(new Rectangle(10, 390, 230, 20));
    pnlWhite6.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lblStockLocales.setBounds(new Rectangle(20, 0, 50, 20));
    lblStockLocales.setForeground(Color.black);
    lblStockLocales.setHorizontalAlignment(SwingConstants.CENTER);
    lblStockAlmacen.setBounds(new Rectangle(135, 0, 55, 20));
    lblStockAlmacen.setForeground(Color.black);
    lblStockAlmacen.setHorizontalAlignment(SwingConstants.CENTER);
    pnlTitle5.setBounds(new Rectangle(10, 370, 230, 20));
    lblStockAlmacenT.setText("Stock Almacen");
    lblStockAlmacenT.setBounds(new Rectangle(115, 5, 100, 15));
    lblStockAlmacenT.setHorizontalAlignment(SwingConstants.CENTER);
    lblStockLocalesT.setText("Stock Locales");
    lblStockLocalesT.setBounds(new Rectangle(5, 5, 85, 15));
    lblFAdicionales.setBounds(new Rectangle(15, 430, 150, 20));
    lblFAdicionales.setText("[ F1 ] Ver Adicionales");
    lblIndTipoProd.setBounds(new Rectangle(545, 0, 140, 20));
    lblIndTipoProd.setFont(new Font("SansSerif", 1, 12));
    lblIndTipoProd.setBackground(new Color(44, 146, 24));
    lblIndTipoProd.setOpaque(true);
    lblIndTipoProd.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lblIndTipoProd.setForeground(Color.white);
    lblIndTipoProd.setHorizontalTextPosition(SwingConstants.CENTER);
    lblIndTipoProd.setHorizontalAlignment(SwingConstants.CENTER);
    lblF8.setBounds(new Rectangle(470, 430, 130, 20));
    lblF8.setText("[ F8 ] Adicional");
    pnlWhite6.add(lblStockLocales, null);
    pnlWhite6.add(lblStockAlmacen, null);
    pnlTitle5.add(lblStockAlmacenT, null);
    pnlTitle5.add(lblStockLocalesT, null);
    pnlWhite5.add(lblUltimoPedido_T, null);
    pnlWhite5.add(lblItemsUltimoPedido, null);
    pnlWhite5.add(lblProductosUltimoPedido, null);
    pnlWhite2.add(lblCantAnterior, null);
    pnlWhite2.add(lblRotProm, null);
    pnlWhite2.add(lblNDias, null);
    pnlWhite2.add(lblMaxDias, null);
    pnlWhite2.add(lblMinDias, null);
    pnlWhite2.add(lblTransito, null);
    pnlWhite2.add(lblMinExhib, null);
    pnlWhite2.add(lblLaboratorio, null);
    pnlTitle4.add(lblProductos_T, null);
    pnlTitle4.add(lblItems_T, null);
    jContentPane.add(lblF8, null);
    jContentPane.add(lblFAdicionales, null);
    jContentPane.add(pnlTitle5, null);
    jContentPane.add(pnlWhite6, null);
    jContentPane.add(lblMensaje, null);
    jContentPane.add(lblFOrdenar, null);
    jContentPane.add(lblF9, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(pnlWhite5, null);
    jContentPane.add(lblF7, null);
    jContentPane.add(lblF6, null);
    jContentPane.add(pnlTitle4, null);
    jContentPane.add(pnlWhite4, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(pnlWhite3, null);
    pnlWhite4.add(lblPedidoActual_T, null);
    pnlWhite4.add(lblItemsPedidoActual, null);
    pnlWhite4.add(lblProductos, null);
    jContentPane.add(pnlWhite2, null);
    jContentPane.add(pnlTitle3, null);
    pnlWhite3.add(lbl30d, null);
    pnlWhite3.add(lbl60d, null);
    pnlWhite3.add(lbl90d, null);
    pnlWhite3.add(lbl120d, null);
    pnlWhite3.add(lblRotacion_T, null);
    jContentPane.add(pnlTitle2, null);
    pnlTitle3.add(lbl30d_T, null);
    pnlTitle3.add(lbl60d_T, null);
    pnlTitle3.add(lbl90d_T, null);
    pnlTitle3.add(lbl120d_T, null);
    pnlTitle3.add(lblNoDias_T, null);
    pnlTitle2.add(lblCantAnterior_T, null);
    pnlTitle2.add(lblRotProm_T, null);
    pnlTitle2.add(lblNDia_T, null);
    pnlTitle2.add(lblMaxDias_T, null);
    pnlTitle2.add(lblMinDias_T, null);
    pnlTitle2.add(lblTransito_T, null);
    pnlTitle2.add(lblLaboratorio_T, null);
    pnlTitle2.add(lblMinExhib_T, null);
    scrListaProductos.getViewport().add(tblListaProductos, null);
    jContentPane.add(scrListaProductos, null);
    pnlTitle1.add(lblIndTipoProd, null);
    pnlTitle1.add(btnRelacionProductos, null);
    jContentPane.add(pnlTitle1, null);
    pnlHeader1.add(lblMaximoDias, null);
    pnlHeader1.add(lblMinimoDias, null);
    pnlHeader1.add(lblRotacionPromedio, null);
    pnlHeader1.add(lblMaximoDias_T, null);
    pnlHeader1.add(lblDias, null);
    pnlHeader1.add(lblMinimoDias_T, null);
    pnlHeader1.add(lblRotacionProm_T, null);
    pnlHeader1.add(txtBuscar, null);
    pnlHeader1.add(btnBuscar, null);
    jContentPane.add(pnlHeader1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initCabecera();
    initTable();
    
    FarmaVariables.vAceptar = false;
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
    tableModel = new FarmaTableModel(ConstantsInventario.columnsListaProductosPedidoReposicionVer,ConstantsInventario.defaultValuesListaProductosPedidoReposicionVer,0);
    FarmaUtility.initSimpleList(tblListaProductos,tableModel,ConstantsInventario.columnsListaProductosPedidoReposicionVer);
    //cargaListaProductos();
  }
  
  private void cargaListaProductos()
  {
    try
    {
      tableModel.clearTable();
      DBInventario.cargaListaPedidoReposicionDetalle(tableModel);
      FarmaUtility.ordenar(tblListaProductos,tableModel,1,FarmaConstants.ORDEN_ASCENDENTE);
      if(tableModel.data.size() == 0)
      {
        FarmaUtility.showMessage(this,"No hay productos por solicitar",txtBuscar);
        cerrarVentana(false);
      }
      else{
        mostrarDetalles(0);
        
        UtilityInventario.muestraIndTipoProd(24,lblIndTipoProd,tblListaProductos);
      }
    }catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de productos : \n" + sql.getMessage(),txtBuscar);
    }
  }
  
  private void initCabecera()
  {
    lblRotacionPromedio.setText(VariablesInventario.vRotProm_PedRep);
    lblMinimoDias.setText(VariablesInventario.vMinDias_PedRep);
    lblMaximoDias.setText(VariablesInventario.vMaxDias_PedRep);
    lblItemsUltimoPedido.setText(VariablesInventario.vItemsUltPed_PedRep);
    lblProductosUltimoPedido.setText(VariablesInventario.vProdsUltPed_PedRep);
  }
  
  private void initPedidoActual()
  {
    if(tableModel.data.size() != 0)
      try
      {
        ArrayList array = new ArrayList();
        int items, productos;
        DBInventario.getPedidoActualReposicion(array);
        if(array.size()>0)
        {
          ArrayList arrayAux = (ArrayList)array.get(0);
          items = Integer.parseInt(arrayAux.get(0).toString());
          productos = Integer.parseInt(arrayAux.get(1).toString());
          
          arrayAux = (ArrayList)array.get(1);
          items = items + Integer.parseInt(arrayAux.get(0).toString());
          productos = productos+Integer.parseInt(arrayAux.get(1).toString());
          
          lblItemsPedidoActual.setText(items+"");
          lblProductos.setText(productos+"");
        }
      }catch(SQLException sql)
      {
        sql.printStackTrace();
        FarmaUtility.showMessage(this,"Ocurrió un error al obtener información del pedido : \n" + sql.getMessage(),txtBuscar);
      }
  }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnBuscar_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtBuscar);
  }

  private void txtBuscar_keyPressed(KeyEvent e)
  {
     if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      if (tblListaProductos.getSelectedRow() >= 0)
      {
        if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtBuscar.getText().trim(), 0, 1)) ) 
        {
          FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
          return;
        }
        mostrarDetalles(tblListaProductos.getSelectedRow());
        UtilityInventario.muestraIndTipoProd(24,lblIndTipoProd,tblListaProductos);
      }
    }
    
    chkKeyPressed(e);
  }
  
  private void txtBuscar_keyReleased(KeyEvent e)
  {
    FarmaGridUtils.buscarDescripcion(e,tblListaProductos,txtBuscar,1);
    if(tableModel.getRowCount() > 0 && tblListaProductos.getSelectedRow() > -1){
      mostrarDetalles(tblListaProductos.getSelectedRow());
      UtilityInventario.muestraIndTipoProd(24,lblIndTipoProd,tblListaProductos);
    }
  }
  
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtBuscar);
    cargaListaProductos();
    initPedidoActual();
    //this.setTitle(this.getTitle() + " - Fecha Ultimo calculo de maximos y minimos " + VariablesInventario.vFechaCalculoMaxMin);
    mostrarUltimoCalculo();
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
    FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,txtBuscar,1);
    if(e.getKeyCode() == KeyEvent.VK_F1)
    {
      if(VariablesInventario.vMostrarAdic)
      {
        //Solo ejecutara esto si selecciono pedidos adicionales
        if(funcionF1()){
        initPedidoAdic();
        VariablesInventario.vMostrarAdic = false;
        lblFAdicionales.setText("[ F1 ] Ver Prod. Rep.");
        }
      }
      else{
        cargaListaProductos();
        initPedidoActual();
        lblFAdicionales.setText("[ F1 ] Ver Adicionales");
        VariablesInventario.vMostrarAdic = true;
      }
    }
    else if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      ingresarCantidad(); //Se ingresa la cantidad 
    }else if(e.getKeyCode() == KeyEvent.VK_F6)
    {
      cargaFiltroProductos();
    }else if(e.getKeyCode() == KeyEvent.VK_F7)
    {
      cargaListaProductos();
    }else if(e.getKeyCode() == KeyEvent.VK_F8)
    {
      if(tblListaProductos.getSelectedRow() >= 0)
        funcionF8();
      else
        FarmaUtility.showMessage(this,"Debe seleccionar un registro de la lista de productos.",txtBuscar);
    }else if(e.getKeyCode() == KeyEvent.VK_F9)
    {
      funcionF9();
    }else if(e.getKeyCode() == KeyEvent.VK_F10)
    {
      if(tblListaProductos.getRowCount()>0)
        funcionF10();
      else
        FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de ordenar.",txtBuscar);
    }else if(e.getKeyCode() == KeyEvent.VK_F11)
    {
      cargaListaProductos();
      //Se agrego para que se actualize los items actuales      
      //23.10.2007   DUBILLUZ  MODIFICACION
      initPedidoActual();
      if(tableModel.data.size() > 0)
        if(FarmaUtility.rptaConfirmDialog(this,"¿Está Seguro de enviar el Pedido de Reposicion?"))
        {
          if(enviarPedido())
          {
            FarmaUtility.showMessage(this,"Pedido Generado con éxito.",null);
            cerrarVentana(true);  
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

  private void mostrarDetalles(int row)
  {
    String cantidad ;
    lblLaboratorio.setText(tblListaProductos.getValueAt(row,10).toString());
    lblMinExhib.setText(tblListaProductos.getValueAt(row,11).toString());
    lblTransito.setText(tblListaProductos.getValueAt(row,12).toString());
    lblMinDias.setText(tblListaProductos.getValueAt(row,13).toString());
    lblMaxDias.setText(tblListaProductos.getValueAt(row,14).toString());
    lblNDias.setText(tblListaProductos.getValueAt(row,15).toString());
    lblRotProm.setText(tblListaProductos.getValueAt(row,16).toString());
    lblCantAnterior.setText(tblListaProductos.getValueAt(row,17).toString());
    //
    lbl30d.setText(tblListaProductos.getValueAt(row,18).toString());
    lbl60d.setText(tblListaProductos.getValueAt(row,19).toString());
    lbl90d.setText(tblListaProductos.getValueAt(row,20).toString());
    lbl120d.setText(tblListaProductos.getValueAt(row,21).toString());
    lblStockLocales.setText(tblListaProductos.getValueAt(row,25).toString());
    lblStockAlmacen.setText(tblListaProductos.getValueAt(row,26).toString());
    
    //if (UtilityInventario.obtieneIndLinea(this)) obtieneInfoProd(tblListaProductos.getValueAt(row,0).toString());
    
    try 
    {
      cantidad = DBInventario.obtieneCantidad(tblListaProductos.getValueAt(row,0).toString());
      lblCantAnterior.setText(cantidad);  
    } catch (SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this, "Error al obtener la cantidad del producto",txtBuscar);
    }
    
  }

  private boolean enviarPedido()
  {
    boolean retorno;
    try
    {
      DBInventario.generarPedidoReposicion(lblItemsPedidoActual.getText(),lblProductos.getText());
      FarmaUtility.aceptarTransaccion();
      retorno = true;
    }catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ha ocurrido un error al grabar el pedido  : \n"+sql.getMessage(),txtBuscar);
      retorno = false;
    }
    return retorno;
  }
 
  private void cargaFiltroProductos()
  {
    DlgFiltroProductos dlgFiltroProductos = new DlgFiltroProductos(myParentFrame,"", true);
    dlgFiltroProductos.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      cargaListaProductos_filtro();
      FarmaVariables.vAceptar = false;
    }
  }  
  
  private void cargaListaProductos_filtro()
  {
    try
    {
      tableModel.clearTable();
      DBInventario.cargaListaPedidoReposicionDetalle_filtro(tableModel,VariablesPtoVenta.vTipoFiltro, VariablesPtoVenta.vCodFiltro);
      FarmaUtility.ordenar(tblListaProductos,tableModel,1,FarmaConstants.ORDEN_ASCENDENTE);
      if(tableModel.data.size() == 0)
        mostrarDetallesBlanco();
      else{
        mostrarDetalles(0);
        UtilityInventario.muestraIndTipoProd(24,lblIndTipoProd,tblListaProductos);
      }
      
    }catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de productos-filtro: \n" + sql.getMessage(),txtBuscar);
    }   
  }
  
  private void ingresarCantidad()
  {
    if(tblListaProductos.getRowCount() > 0)
    {
      int row = tblListaProductos.getSelectedRow();  
      if(tblListaProductos.getValueAt(row,6).toString().trim().equals(""))
      {
        if(validaVentaUltimosMeses(row))
    {
      cargarCabecera();
      DlgPedidoReposicionIngresoCantidad dlgPedidoReposicionIngresoCantidad = new DlgPedidoReposicionIngresoCantidad(myParentFrame,"",true);
      dlgPedidoReposicionIngresoCantidad.setVisible(true);   
      if(FarmaVariables.vAceptar)
      {
        cargarCantidadIngresada();
        FarmaVariables.vAceptar=false;
      }
        }
    }else
    {
      FarmaUtility.showMessage(this,"No puede solicitar este producto",txtBuscar);
    }
  }
  }
  
  private void cargarCabecera()
  {
    try
    {
      VariablesInventario.vFechaHora_PedRep = FarmaSearch.getFechaHoraBD(2);  
    }catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al obtener la fecha del sistema : \n"+sql.getMessage(),txtBuscar);
    }
    int pos = tblListaProductos.getSelectedRow();
    VariablesInventario.vPos_PedRep = pos;
    VariablesInventario.vCodProd_PedRep=tblListaProductos.getValueAt(pos,0).toString();
    VariablesInventario.vNomProd_PedRep=tblListaProductos.getValueAt(pos,1).toString();
    VariablesInventario.vUnidMed_PedRep=tblListaProductos.getValueAt(pos,2).toString();
    VariablesInventario.vValFrac_PedRep=tblListaProductos.getValueAt(pos,22).toString();
    VariablesInventario.vNomLab_PedRep=tblListaProductos.getValueAt(pos,10).toString();
    VariablesInventario.vCant_PedRep=tblListaProductos.getValueAt(pos,8).toString();
    VariablesInventario.vStkFisico_PedRep=tblListaProductos.getValueAt(pos,5).toString();
    VariablesInventario.vCantSug_PedRep=tblListaProductos.getValueAt(pos,7).toString();
    VariablesInventario.vCantMax_PedRep=tblListaProductos.getValueAt(pos,23).toString();
    //Agregado DUBILLUZ
    VariablesInventario.vCantAdicional = tblListaProductos.getValueAt(pos,9).toString();
  }
  
  private void cargarCantidadIngresada()
  {
    int pos = VariablesInventario.vPos_PedRep;
    tblListaProductos.setValueAt(VariablesInventario.vCant_PedRep,pos,8);
    tblListaProductos.repaint();
  }

  private void mostrarDetallesBlanco()
  {
    //
    lblLaboratorio.setText("");
    lblMinExhib.setText("");
    lblTransito.setText("");
    lblMinDias.setText("");
    lblMaxDias.setText("");
    lblNDias.setText("");
    lblRotProm.setText("");
    lblCantAnterior.setText("");
    //
    lbl30d.setText("");
    lbl60d.setText("");
    lbl90d.setText("");
    lbl120d.setText("");
  }
  
  private boolean validaVentaUltimosMeses(int row)
  {
    boolean retorno = false;
    double total = 0.0;
    try
    {
      //total = total + FarmaUtility.getDecimalNumber(tblListaProductos.getValueAt(row,17).toString().trim());
      //total = total + FarmaUtility.getDecimalNumber(tblListaProductos.getValueAt(row,18).toString().trim());
      //total = total + FarmaUtility.getDecimalNumber(tblListaProductos.getValueAt(row,19).toString().trim());  
      total = FarmaUtility.getDecimalNumber(tblListaProductos.getValueAt(row,16).toString().trim()); 
      if(total > 0.0)
        retorno = true;
      else
        FarmaUtility.showMessage(this,"No se ha encontrado ventas para este producto.",txtBuscar);
    }catch(Exception e)
    {
      FarmaUtility.showMessage(this,""+e,txtBuscar);
    }
   
    return retorno;
  }
  
  private void funcionF9()
  {
    int pos = tblListaProductos.getSelectedRow();
    if(pos > -1)
    {
      if(validaVentaUltimosMeses(pos))
      {
        cargarCabecera();
        VariablesInventario.vCant_PedRep = VariablesInventario.vCantMax_PedRep;
        if(UtilityInventario.guardarCantidadIngresada(this,txtBuscar))
        {
          cargarCantidadIngresada();
          FarmaVariables.vAceptar=false;
          tblListaProductos.setRowSelectionInterval(pos,pos);
        }
      }
      
    }
  }
  
  private void funcionF10()
  {
    DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame,"Ordenar",true);
    String [] IND_DESCRIP_CAMPO = {"Código","Descripción","Unidad","Stock","Nro. Pedido","Calc","Solic."};
    String [] IND_CAMPO = {"0","1","2","5","6","7","8"};
    VariablesInventario.vNombreInHashtable = ConstantsInventario.VNOMBREINHASHTABLEPEDIDOREPFINAL ;
    FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(),
                                  VariablesInventario.vNombreInHashtable,
                                  IND_CAMPO,
                                  IND_DESCRIP_CAMPO,
                                  true);
    dlgOrdenar.setVisible(true);
    
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar=false;
      FarmaUtility.ordenar(tblListaProductos,
                          tableModel,
                          VariablesInventario.vCampo,
                          VariablesInventario.vOrden);
       tblListaProductos.repaint();                   
    }
  }
  
  /**NUEVO
   * @Autor: Luis Reque Orellana
   * @Fecha: 30-03-2007
   * */
  private void mostrarUltimoCalculo()
  {
    lblMensaje.setText("Fecha Ultimo calculo de Pedido de Reposición "+VariablesInventario.vFechaCalculoMaxMin);   
  }
  
  private void obtieneInfoProd(String pCodProd)
  {
    String cadena = "" ;
    String indLocales = "" ;
    String indAlmacen = "" ;
    try
    {
      cadena = DBInventario.obtieneIndicadorStock(pCodProd);
      indLocales = cadena.substring(0,cadena.indexOf("Ã")).trim();
      indAlmacen = cadena.substring(cadena.indexOf("Ã")+1).trim();
      
      lblStockLocales.setText(indLocales);      
      lblStockAlmacen.setText(indAlmacen);
      
      System.out.println(cadena);
      System.out.println(indLocales);
      System.out.println(indAlmacen);
 
    } catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error al obtener informacion del producto en Arreglo. \n" + sql.getMessage(),txtBuscar);
    } catch (Exception e)
    {
      e.printStackTrace();
      FarmaUtility.showMessage(this,"No se encontro coneccion con el local",txtBuscar);
    }
  }
  
  /**NUEVO!
   * @Autor:  Luis Reque Orellana
   * @Fecha:  25/04/2007
   * */
  private boolean funcionF1()
  {
    boolean resultado = true;
    ArrayList aProductos = new ArrayList();
    try
    {
      DBInventario.verProductosCantAdic(aProductos);
      if(aProductos.size()>0)
      {
        tableModel.data = aProductos;
        tableModel.fireTableDataChanged();
        FarmaGridUtils.showCell(tblListaProductos,0,0);
        mostrarDetalles(tblListaProductos.getSelectedRow());
        UtilityInventario.muestraIndTipoProd(24,lblIndTipoProd,tblListaProductos);
        resultado = true;
      }
      else{
        FarmaUtility.showMessage(this,"No existen cantidades adicionales ingresadas.",txtBuscar);
        resultado = false;
      }
    }
    catch(SQLException sql)
    {
      resultado = false;
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error en BD: "+sql.getMessage(),txtBuscar);      
    }
   System.out.println("Valor de ver Pedidos Adicionales : " + resultado); 
   return resultado;
  }
  

  /**NUEVO!
   * @Autor:  Luis Reque Orellana
   * @Fecha:  25/04/2007
   * */
  private void initPedidoAdic()
  {
    if(tableModel.data.size() != 0)
      try
      {
        ArrayList array = new ArrayList();
        int items, productos;
        DBInventario.getPedidoActualReposicionAdic(array);
        if(array.size()>0)
        {
          ArrayList arrayAux = (ArrayList)array.get(0);
          items = Integer.parseInt(arrayAux.get(0).toString());
          productos = Integer.parseInt(arrayAux.get(1).toString());
          
          lblItemsPedidoActual.setText(items+"");
          lblProductos.setText(productos+"");
        }
      }catch(SQLException sql)
      {
        sql.printStackTrace();
        FarmaUtility.showMessage(this,"Ocurrió un error al obtener información del pedido (adicionales) : \n" + sql.getMessage(),txtBuscar);
      }
  }
  
  /**NUEVO
   * @Autor: Paulo Cesar Ameghino Rojas
   * @Fecha: 11-06-2007
   * */  
  private void funcionF8()
  {
    cargarCabecera();
    //26.09.2007 dubilluz Modificado
    System.out.println("VariablesInventario.vCantMax_PedRep "+FarmaUtility.getDecimalNumber( VariablesInventario.vCantMax_PedRep));
    System.out.println("VariablesInventario.vCant_PedRep    "+FarmaUtility.getDecimalNumber(VariablesInventario.vCant_PedRep.trim()));
    //El sistema validara que el pedido adicional sera posible si 
    //la cantidad solicitada por el sistema sea mayor que el sistema
    //caso contrario mostraria un mensaje.
    //13.11.2007 dubilluz modificado
    if(FarmaUtility.getDecimalNumber(VariablesInventario.vCant_PedRep.trim())>0){
        if(FarmaUtility.getDecimalNumber( VariablesInventario.vCantMax_PedRep.trim()) == FarmaUtility.getDecimalNumber(VariablesInventario.vCant_PedRep.trim()))  
         {
            /////
            DlgIngresoCantidadAdicional dlgIngresoCantAdic = new DlgIngresoCantidadAdicional(myParentFrame,"",true);
            dlgIngresoCantAdic.setVisible(true);
            
            if(FarmaVariables.vAceptar)
            {
              tblListaProductos.setValueAt(VariablesInventario.vCantAdicional,tblListaProductos.getSelectedRow(),9);
              tblListaProductos.repaint();
              FarmaVariables.vAceptar=false; 
            }
            /////
         }
         else
           FarmaUtility.showMessage(this,"Para agregar adicional la cantidad solicitada debe ser igual a la cantidad maxima.",txtBuscar);
    } 
    else
       FarmaUtility.showMessage(this,"Para agregar adicional la cantidad solicitada debe ser mayor a cero.",txtBuscar);
  }
  
}