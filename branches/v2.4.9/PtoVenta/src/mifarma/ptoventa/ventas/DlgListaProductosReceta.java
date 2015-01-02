package mifarma.ptoventa.ventas;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgFiltroProductos;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesReceta;
import mifarma.ptoventa.ventas.reference.VariablesVentas;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaProductosReceta.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS  06.12.2006  Creación
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgListaProductosReceta
  extends JDialog
{
  private static final Logger log = LoggerFactory.getLogger(DlgListaProductosReceta.class);  
  
  private Frame myParentFrame;

  private JTable myJTable;

  private FarmaTableModel tableModelListaPrecioProductos;
  private FarmaTableModel tableModelListaProductosAlternativos;

  private String descUnidPres = "";
  private String stkProd = "";
  private String valPrecPres = "";
  private String valFracProd = "";
  private String indProdCong = "";
  private String valPrecLista = "";
  private String valPrecVta = "";
  private String descUnidVta = "";
  private String indProdHabilVta = "";

  private int totalItems = 0;
  private double totalVenta = 0;

  private String indicadorItems = "";

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JPanel jPanel3 = new JPanel();
  private JLabel lblItems = new JLabel();
  private JLabel lblItems_T = new JLabel();
  private JLabel lblPrecio = new JLabel();
  private JLabel lblPrecio_T = new JLabel();
  private JLabel lblUnidad = new JLabel();
  private JLabel lblUnidad_T = new JLabel();
  private JLabelFunction lblF6 = new JLabelFunction();
  private JLabelFunction lblF1 = new JLabelFunction();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JPanel jPanel2 = new JPanel();
  private JSeparator jSeparator2 = new JSeparator();
  private JLabel lblDescLab_Alter = new JLabel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JPanel jPanel1 = new JPanel();
  private JLabel lblDescLab_Prod = new JLabel();
  private JSeparator jSeparator1 = new JSeparator();
  private JPanel pnlIngresarProductos = new JPanel();
  private JButton btnBuscar = new JButton();
  private JTextField txtProducto = new JTextField();
  private JButton btnProducto = new JButton();
  private JTable tblProductos = new JTable();
  private JTable tblListaProductosAlternativos = new JTable();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JLabel lblTotalVenta = new JLabel();
  private JLabel lblTotalVenta_T = new JLabel();
  private JPanel jPanel4 = new JPanel();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JLabelWhite lblCliente = new JLabelWhite();
  private JLabelWhite lblCliente_T = new JLabelWhite();
  private JButtonLabel btnProdAlternativos = new JButtonLabel();
  private JButtonLabel btnRelacionProductos = new JButtonLabel();
  private JLabelFunction lblF7 = new JLabelFunction();
  private JLabelWhite lblMedico = new JLabelWhite();
  private JLabelWhite lblMedicoT = new JLabelWhite();
  private JLabel lblProdIgv = new JLabel();

  // **************************************************************************
  // Constructores
  // **************************************************************************

  public DlgListaProductosReceta()
  {
    this(null, "", false);
  }

  public DlgListaProductosReceta(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
    }
    catch (Exception e)
    {
      log.error("",e);
    }

  }

  // **************************************************************************
  // Método "jbInit()"
  // **************************************************************************

  private void jbInit()
    throws Exception
  {
    this.setSize(new Dimension(737, 532));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Lista de Productos y Precios Receta");
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
    jContentPane.setBackground(Color.white);
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(623, 439));
    jContentPane.setForeground(Color.white);
    jPanel3.setBounds(new Rectangle(15, 80, 345, 45));
    jPanel3.setBackground(new Color(43, 141, 39));
    jPanel3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jPanel3.setLayout(null);
    lblItems.setText("0");
    lblItems.setBounds(new Rectangle(145, 5, 80, 15));
    lblItems.setFont(new Font("SansSerif", 1, 14));
    lblItems.setForeground(Color.white);
    lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
    lblItems_T.setText("Items :");
    lblItems_T.setBounds(new Rectangle(15, 5, 65, 15));
    lblItems_T.setFont(new Font("SansSerif", 1, 14));
    lblItems_T.setForeground(Color.white);
    lblPrecio.setBounds(new Rectangle(90, 25, 130, 15));
    lblPrecio.setFont(new Font("SansSerif", 1, 15));
    lblPrecio.setForeground(Color.white);
    lblPrecio_T.setText("Precio : S/.");
    lblPrecio_T.setBounds(new Rectangle(10, 25, 75, 15));
    lblPrecio_T.setFont(new Font("SansSerif", 1, 13));
    lblPrecio_T.setForeground(Color.white);
    lblUnidad.setBounds(new Rectangle(90, 5, 190, 15));
    lblUnidad.setFont(new Font("SansSerif", 1, 11));
    lblUnidad.setForeground(Color.white);
    lblUnidad_T.setText("Unidad :");
    lblUnidad_T.setBounds(new Rectangle(10, 5, 60, 15));
    lblUnidad_T.setFont(new Font("SansSerif", 1, 11));
    lblUnidad_T.setForeground(Color.white);
    lblF6.setText("[ F6 ] Filtrar Productos");
    lblF6.setBounds(new Rectangle(280, 435, 140, 20));
    lblF1.setText("[ F1 ] Impulso");
    lblF1.setBounds(new Rectangle(165, 435, 110, 20));
    jScrollPane2.setBounds(new Rectangle(15, 340, 700, 90));
    jScrollPane2.setBackground(new Color(255, 130, 14));
    jPanel2.setBounds(new Rectangle(15, 320, 700, 20));
    jPanel2.setBackground(new Color(255, 130, 14));
    jPanel2.setLayout(null);
    jSeparator2.setBounds(new Rectangle(200, 0, 15, 20));
    jSeparator2.setBackground(Color.black);
    jSeparator2.setOrientation(SwingConstants.VERTICAL);
    lblDescLab_Alter.setBounds(new Rectangle(225, 0, 375, 20));
    lblDescLab_Alter.setFont(new Font("SansSerif", 1, 11));
    lblDescLab_Alter.setForeground(Color.white);
    jScrollPane1.setBounds(new Rectangle(15, 150, 700, 165));
    jScrollPane1.setBackground(new Color(255, 130, 14));
    jPanel1.setBounds(new Rectangle(15, 130, 700, 20));
    jPanel1.setBackground(new Color(255, 130, 14));
    jPanel1.setLayout(null);
    lblDescLab_Prod.setBounds(new Rectangle(160, 0, 370, 20));
    lblDescLab_Prod.setFont(new Font("SansSerif", 1, 11));
    lblDescLab_Prod.setForeground(Color.white);
    jSeparator1.setBounds(new Rectangle(150, 0, 15, 20));
    jSeparator1.setBackground(Color.black);
    jSeparator1.setOrientation(SwingConstants.VERTICAL);
    pnlIngresarProductos.setBounds(new Rectangle(15, 45, 700, 30));
    pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(Color.black, 
          1));
    pnlIngresarProductos.setBackground(new Color(43, 141, 39));
    pnlIngresarProductos.setLayout(null);
    pnlIngresarProductos.setForeground(Color.orange);
    btnBuscar.setText("Buscar");
    btnBuscar.setBounds(new Rectangle(585, 5, 90, 20));
    btnBuscar.setBackground(SystemColor.control);
    btnBuscar.setMnemonic('b');
    btnBuscar.setDefaultCapable(false);
    btnBuscar.setFocusPainted(false);
    btnBuscar.setRequestFocusEnabled(false);
    btnBuscar.setFont(new Font("SansSerif", 1, 12));
    btnBuscar.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnBuscar_actionPerformed(e);
          }
        });
    txtProducto.setBounds(new Rectangle(100, 5, 460, 20));
    txtProducto.setFont(new Font("SansSerif", 1, 11));
    txtProducto.setForeground(new Color(32, 105, 29));
    txtProducto.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
                    txtProducto_keyPressed(e);
                }

          public void keyReleased(KeyEvent e)
          {
            txtProducto_keyReleased(e);
          }
        });
    btnProducto.setText("Producto");
    btnProducto.setBounds(new Rectangle(25, 5, 60, 20));
    btnProducto.setMnemonic('p');
    btnProducto.setFont(new Font("SansSerif", 1, 11));
    btnProducto.setDefaultCapable(false);
    btnProducto.setRequestFocusEnabled(false);
    btnProducto.setBackground(new Color(50, 162, 65));
    btnProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnProducto.setFocusPainted(false);
    btnProducto.setHorizontalAlignment(SwingConstants.LEFT);
    btnProducto.setContentAreaFilled(false);
    btnProducto.setBorderPainted(false);
    btnProducto.setForeground(Color.white);
    btnProducto.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnProducto_actionPerformed(e);
          }
        });
    tblProductos.setFont(new Font("SansSerif", 0, 12));
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(525, 465, 100, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(630, 465, 85, 20));
    lblEnter.setText("[ ENTER ] Seleccionar");
    lblEnter.setBounds(new Rectangle(15, 435, 145, 20));
    lblTotalVenta.setText("0.00");
    lblTotalVenta.setBounds(new Rectangle(140, 25, 85, 15));
    lblTotalVenta.setFont(new Font("SansSerif", 1, 15));
    lblTotalVenta.setForeground(Color.white);
    lblTotalVenta.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotalVenta_T.setText("Total venta : S/.");
    lblTotalVenta_T.setBounds(new Rectangle(15, 25, 120, 15));
    lblTotalVenta_T.setFont(new Font("SansSerif", 1, 15));
    lblTotalVenta_T.setForeground(Color.white);
    jPanel4.setBounds(new Rectangle(370, 80, 345, 45));
    jPanel4.setBackground(new Color(43, 141, 39));
    jPanel4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jPanel4.setLayout(null);
    pnlTitle1.setBounds(new Rectangle(15, 10, 700, 30));
    lblCliente.setBounds(new Rectangle(60, 5, 290, 20));
    lblCliente.setText(" ");
    lblCliente_T.setText("Cliente:");
    lblCliente_T.setBounds(new Rectangle(10, 5, 55, 20));
    btnProdAlternativos.setText("Productos Alternativos");
    btnProdAlternativos.setBounds(new Rectangle(10, 0, 150, 20));
    btnProdAlternativos.setMnemonic('a');
    btnProdAlternativos.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnProdAlternativos_actionPerformed(e);
          }
        });
    btnRelacionProductos.setText("Relacion de Productos");
    btnRelacionProductos.setBounds(new Rectangle(10, 0, 140, 20));
    btnRelacionProductos.setMnemonic('r');
    btnRelacionProductos.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnRelacionProductos_actionPerformed(e);
          }
        });
    lblF7.setBounds(new Rectangle(425, 435, 117, 19));
    lblF7.setText("[ F7 ] Quitar Filtro");
    lblMedico.setBounds(new Rectangle(400, 5, 290, 20));
    lblMedico.setText(" ");
    lblMedicoT.setText("Medico:");
    lblMedicoT.setBounds(new Rectangle(350, 5, 50, 20));
    lblProdIgv.setBounds(new Rectangle(570, 0, 130, 20));
    lblProdIgv.setFont(new Font("SansSerif", 1, 11));
    lblProdIgv.setForeground(Color.white);
    lblProdIgv.setText("Producto Inafecto IGV");
    pnlTitle1.add(lblMedicoT, null);
    pnlTitle1.add(lblMedico, null);
    pnlTitle1.add(lblCliente, null);
    pnlTitle1.add(lblCliente_T, null);
    jPanel3.add(lblPrecio, null);
    jPanel3.add(lblPrecio_T, null);
    jPanel3.add(lblUnidad, null);
    jPanel3.add(lblUnidad_T, null);
    jScrollPane2.getViewport();
    jPanel2.add(btnProdAlternativos, null);
    jPanel2.add(jSeparator2, null);
    jPanel2.add(lblDescLab_Alter, null);
    jScrollPane1.getViewport();
    jPanel1.add(lblProdIgv, null);
    jPanel1.add(btnRelacionProductos, null);
    jPanel1.add(lblDescLab_Prod, null);
    jPanel1.add(jSeparator1, null);
    pnlIngresarProductos.add(btnBuscar, null);
    pnlIngresarProductos.add(txtProducto, null);
    pnlIngresarProductos.add(btnProducto, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    jPanel4.add(lblTotalVenta_T, null);
    jPanel4.add(lblItems_T, null);
    jPanel4.add(lblItems, null);
    jPanel4.add(lblTotalVenta, null);
    jContentPane.add(lblF7, null);
    jContentPane.add(pnlTitle1, null);
    jContentPane.add(jPanel4, null);
    jContentPane.add(lblEnter, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(jPanel3, null);
    jContentPane.add(lblF6, null);
    jContentPane.add(lblF1, null);
    jScrollPane2.getViewport().add(tblListaProductosAlternativos, null);
    jContentPane.add(jScrollPane2, null);
    jContentPane.add(jPanel2, null);
    jScrollPane1.getViewport().add(tblProductos, null);
    jContentPane.add(jScrollPane1, null);
    jContentPane.add(jPanel1, null);
    jContentPane.add(pnlIngresarProductos, null);
    //this.getContentPane().add(jContentPane, null);
  }

  // **************************************************************************
  // Método "initialize()"
  // **************************************************************************

  private void initialize()
  {
    initTableListaPreciosProductos();
    initTableProductosAlternativos();
    setJTable(tblProductos);
    iniciaProceso(true);
    lblMedico.setText(VariablesVentas.vNombreListaMed);
    FarmaVariables.vAceptar = false;
  }

  // **************************************************************************
  // Métodos de inicialización
  // **************************************************************************

  private void initTableListaPreciosProductos()
  {
    tableModelListaPrecioProductos = new FarmaTableModel(ConstantsVentas.columnsListaProductos, ConstantsVentas.defaultValuesListaProductos, 
          0);
    tableModelListaPrecioProductos.data = VariablesVentas.tableModelListaGlobalProductos.data;
    FarmaUtility.initSelectList(tblProductos, tableModelListaPrecioProductos,ConstantsVentas.columnsListaProductos);
    tblProductos.setName(ConstantsVentas.NAME_TABLA_PRODUCTOS_RECETA);
  }

  private void initTableProductosAlternativos()
  {
    /*tableModelListaProductosAlternativos = new FarmaTableModel(ConstantsVentas.columnsListaProductosAlternativos, ConstantsVentas.defaultValuesListaProductosAlternativos, 0);FarmaUtility.initSelectList(tblListaProductosAlternativos, tableModelListaProductosAlternativos,ConstantsVentas.columnsListaProductosAlternativos);
    tblListaProductosAlternativos.setName(ConstantsVentas.NAME_TABLA_PRODUCTOS_ALTERNATIVOS_RECETA);*/
    tableModelListaProductosAlternativos = new FarmaTableModel(ConstantsVentas.columnsListaProductos, ConstantsVentas.defaultValuesListaProductos, 0);
    FarmaUtility.initSelectList(tblListaProductosAlternativos, tableModelListaProductosAlternativos,ConstantsVentas.columnsListaProductos);
    tblListaProductosAlternativos.setName(ConstantsVentas.NAME_TABLA_PRODUCTOS_ALTERNATIVOS_RECETA);
    muestraProductosAlternativos();
  }

  public void iniciaProceso(boolean pInicializar)
  {
    for(int i=0;i < VariablesVentas.tableModelListaGlobalProductos.getRowCount();i++)
      VariablesVentas.tableModelListaGlobalProductos.setValueAt(new Boolean(false), 
                                                                i, 0);
    if (pInicializar)
    {
      VariablesReceta.vArrayList_PedidoReceta = new ArrayList();
      for(int i=0;i < VariablesReceta.vArrayList_ResumenReceta.size();i++)
        VariablesReceta.vArrayList_PedidoReceta.add((ArrayList) VariablesReceta.vArrayList_ResumenReceta.get(i));
    }
    log.debug("VariablesReceta.vArrayList_PedidoReceta : " + 
                       VariablesReceta.vArrayList_PedidoReceta.size());
    ArrayList myArray = new ArrayList();
    for(int i=0; i < VariablesReceta.vArrayList_PedidoReceta.size();i++)
      myArray.add((ArrayList) VariablesReceta.vArrayList_PedidoReceta.get(i));
    FarmaUtility.ponerCheckJTable(tblProductos, 1, myArray, 0);
    //if ( !pInicializar )  
    actualizaListaProductosAlternativos();
    muestraNombreLab(4, lblDescLab_Prod);
    muestraProductoInafectoIgv(11, lblProdIgv);
    muestraInfoProd();
    colocaTotalesPedido();
  }

  // **************************************************************************
  // Metodos de eventos
  // **************************************************************************

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    lblProdIgv.setVisible(false);
    FarmaUtility.moveFocus(txtProducto);
  }

  private void txtProducto_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e, myJTable, txtProducto, 2);
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
        //ERIOS 03.07.2013 Limpia la caja de texto
        limpiaCadenaAlfanumerica(txtProducto);
      try
      {
        e.consume();
        if (myJTable.getSelectedRow() >= 0)
        {
          String productoBuscar = 
            txtProducto.getText().trim().toUpperCase();
          if (productoBuscar.length() == 0)
            return;
          String codigo = "";
          // revisando codigo de barra
          char primerkeyChar = productoBuscar.charAt(0);
          char segundokeyChar;
          if (productoBuscar.length() > 1)
            segundokeyChar = productoBuscar.charAt(1);
          else
            segundokeyChar = primerkeyChar;
          char ultimokeyChar = 
            productoBuscar.charAt(productoBuscar.length() - 1);
          if (productoBuscar.length() > 6 && (!Character.isLetter(primerkeyChar) && (!Character.isLetter(segundokeyChar) && (!Character.isLetter(ultimokeyChar)))))
          {
            VariablesReceta.vCodigoBarra = productoBuscar;
            productoBuscar = DBVentas.obtieneCodigoProductoBarra();
          }
          if (productoBuscar.equalsIgnoreCase("000000"))
          {
            FarmaUtility.showMessage(this, 
                                     "No existe producto relacionado con el Codigo de Barra. Verifique!!!", 
                                     txtProducto);
            return;
          }
          for (int k = 0; k < myJTable.getRowCount(); k++)
          {
            codigo = ((String) myJTable.getValueAt(k, 1)).trim();
            if (codigo.equalsIgnoreCase(productoBuscar))
            {
              FarmaGridUtils.showCell(myJTable, k, 0);
              break;
            }
          }
          String actualCodigo = 
            ((String) (myJTable.getValueAt(myJTable.getSelectedRow(), 
                                           1))).trim();
          String actualProducto = 
            ((String) (myJTable.getValueAt(myJTable.getSelectedRow(), 
                                           2))).trim().toUpperCase();
          // Asumimos que codigo de producto ni codigo de barra empiezan con letra
          if (Character.isLetter(primerkeyChar))
          {
            txtProducto.setText(actualProducto);
            productoBuscar = actualProducto;
          }
          txtProducto.setText(txtProducto.getText().trim());
          // Comparando codigo y descripcion de la fila actual con el txtProducto
          if ((actualCodigo.equalsIgnoreCase(productoBuscar) || 
               actualProducto.substring(0, 
                                        productoBuscar.length()).equalsIgnoreCase(productoBuscar)))
          {
            btnBuscar.doClick();
            txtProducto.setText(actualProducto.trim());
            txtProducto.selectAll();
          }
          else
          {
            FarmaUtility.showMessage(this, 
                                     "Producto No Encontrado según Criterio de Búsqueda !!!", 
                                     txtProducto);
            return;
          }
          //muestraNombreLab(4, lblDescLab_Prod);
          //muestraInfoProd();
          //btnBuscar.doClick();
          /*if (!(FarmaUtility.findTextInJTable(myJTable, txtProducto.getText().trim(), 1, 2)) )
          {
            //FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtProducto);
            //return;
          } else{
            muestraNombreLab(4, lblDescLab_Prod);
            muestraInfoProd();
            btnBuscar.doClick();
          }*/
        }
      }
      catch (SQLException sql)
      {
        log.error("",sql);
        FarmaUtility.showMessage(this, 
                                 "Error al buscar el Producto.\n" + sql, 
                                 txtProducto);
      }
    }
    chkKeyPressed(e);
  }

  private void txtProducto_keyReleased(KeyEvent e)
  {
    FarmaGridUtils.buscarDescripcion(e, myJTable, txtProducto, 2);
    muestraNombreLab(4, lblDescLab_Prod);
    muestraProductoInafectoIgv(11, lblProdIgv);
    muestraInfoProd();
    if (!(e.getKeyCode() == KeyEvent.VK_ESCAPE))
    {
      if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS_RECETA))
      {
        actualizaListaProductosAlternativos();
      }
    }
    colocaTotalesPedido();
  }

  private void btnProdAlternativos_actionPerformed(ActionEvent e)
  {
    setJTable(tblListaProductosAlternativos);
  }

  private void btnRelacionProductos_actionPerformed(ActionEvent e)
  {
    setJTable(tblProductos);
  }

  private void btnProducto_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtProducto);
  }

  private void btnBuscar_actionPerformed(ActionEvent e)
  {
    verificaCheckJTable();
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, 
                             "Debe presionar la tecla ESC para cerrar la ventana.", 
                             null);
  }

  // **************************************************************************
  // Metodos auxiliares de eventos
  // **************************************************************************

  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
    }
    else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e))
    {
      muestraDetalleProducto();
    }
    else if (e.getKeyCode() == KeyEvent.VK_F6)
    {
      cargaListaFiltro();
    }
    else if (e.getKeyCode() == KeyEvent.VK_F7)
    {
      if (VariablesPtoVenta.vInd_Filtro.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
      {
        //lblDescFiltro.setText("");
        VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_N;
        tableModelListaProductosAlternativos.clearTable();
        tableModelListaPrecioProductos.clearTable();
        tableModelListaPrecioProductos.fireTableDataChanged();
        tableModelListaPrecioProductos.data = VariablesVentas.tableModelListaGlobalProductos.data;
        setJTable(tblProductos);
        iniciaProceso(false);
        FarmaUtility.moveFocus(txtProducto);
      }
    }
    else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
    {
      aceptaOperacion();
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cancelaOperacion();
    }
  }

  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }

  // **************************************************************************
  // Metodos de lógica de negocio
  // **************************************************************************

  private void muestraNombreLab(int pColumna, JLabel pLabel)
  {
    if (myJTable.getRowCount() > 0)
    {
      String nombreLab = 
        ((String) (myJTable.getValueAt(myJTable.getSelectedRow(), 
                                       pColumna))).trim();
      pLabel.setText(nombreLab);
    }
  }

  private void muestraProductoInafectoIgv(int pColumna, JLabel pLabel)
  {
    if (myJTable.getRowCount() > 0)
    {
      String inafectoIgv = 
        ((String) (myJTable.getValueAt(myJTable.getSelectedRow(), 
                                       pColumna))).trim();
      if (FarmaUtility.getDecimalNumber(inafectoIgv) == 0.00)
        pLabel.setVisible(true);
      else
        pLabel.setVisible(false);
    }
  }


  private void obtieneInfoProdEnArrayList(ArrayList pArrayList)
  {
    String codProd = 
      ((String) (myJTable.getValueAt(myJTable.getSelectedRow(), 
                                     1))).trim();
    try
    {
      DBVentas.obtieneInfoProducto(pArrayList, codProd);
    }
    catch (SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this, 
                               "Error al obtener informacion del producto en Arreglo. \n" + 
                               sql.getMessage(), txtProducto);
    }
  }

  private void muestraInfoProd()
  {
    if (myJTable.getRowCount() <= 0)
      return;
    ArrayList myArray = new ArrayList();
    obtieneInfoProdEnArrayList(myArray);
    if (myArray.size() == 1)
    {
      stkProd = ((String) ((ArrayList) myArray.get(0)).get(0)).trim();
      descUnidPres = ((String) ((ArrayList) myArray.get(0)).get(1)).trim();
      valFracProd = ((String) ((ArrayList) myArray.get(0)).get(2)).trim();
      valPrecPres = ((String) ((ArrayList) myArray.get(0)).get(3)).trim();
      indProdCong = ((String) ((ArrayList) myArray.get(0)).get(4)).trim();
      valPrecVta = ((String) ((ArrayList) myArray.get(0)).get(5)).trim();
      descUnidVta = ((String) ((ArrayList) myArray.get(0)).get(6)).trim();
      indProdHabilVta = ((String) ((ArrayList) myArray.get(0)).get(7)).trim();
    }
    else
    {
      stkProd = "";
      descUnidPres = "";
      valFracProd = "";
      valPrecPres = "";
      indProdCong = "";
      valPrecVta = "";
      descUnidVta = "";
      indProdHabilVta = "";
      FarmaUtility.showMessage(this, 
                               "Error al obtener Informacion del Producto", 
                               txtProducto);
    }
    lblUnidad.setText(descUnidPres);
    lblPrecio.setText(valPrecPres);
    myJTable.setValueAt(stkProd, myJTable.getSelectedRow(), 5);
    myJTable.setValueAt(valPrecVta, myJTable.getSelectedRow(), 6);
    myJTable.setValueAt(descUnidVta, myJTable.getSelectedRow(), 3);
    VariablesReceta.vVal_Frac = valFracProd;
    VariablesReceta.vInd_Prod_Habil_Vta = indProdHabilVta;
    myJTable.repaint();
  }

  private void setJTable(JTable pJTable)
  {
    myJTable = pJTable;
    txtProducto.setText("");
    if (pJTable.getRowCount() > 0)
    {
      FarmaGridUtils.showCell(pJTable, 0, 0);
      FarmaUtility.setearActualRegistro(pJTable, txtProducto, 2);
      muestraInfoProd();
    }
    FarmaUtility.moveFocus(txtProducto);
  }

  private void muestraDetalleProducto()
  {
    if (myJTable.getRowCount() == 0)
      return;
    VariablesVentas.vCod_Prod = 
        ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),1))).trim();
    VariablesVentas.vDesc_Prod = 
        ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),2))).trim();
    VariablesVentas.vNom_Lab = 
        ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),4))).trim();
    DlgDetalleProducto dlgDetalleProducto = 
      new DlgDetalleProducto(myParentFrame, "", true);
    dlgDetalleProducto.setVisible(true);
  }

  private void muestraIngresoCantidad()
  {
    if (myJTable.getRowCount() == 0)
      return;
    VariablesReceta.vCod_Prod = 
        ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),1))).trim();
    VariablesReceta.vDesc_Prod = 
        ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),2))).trim();
    VariablesReceta.vNom_Lab = 
        ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),4))).trim();
    VariablesReceta.vPorc_Igv_Prod = 
        ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),11))).trim();
    VariablesReceta.vCant_Ingresada_Temp = "0";
    DlgIngresoCantidadReceta dlgIngresoCantidadReceta = 
      new DlgIngresoCantidadReceta(myParentFrame, "", true);
    dlgIngresoCantidadReceta.setVisible(true);
    if (FarmaVariables.vAceptar)
    {
      seleccionaProducto();
      FarmaVariables.vAceptar = false;
    }
  }

  private void colocaTotalesPedido()
  {
    calculaTotalVentaPedido();
    totalItems = VariablesReceta.vArrayList_PedidoReceta.size();
    lblItems.setText("" + totalItems);
    lblTotalVenta.setText(FarmaUtility.formatNumber(totalVenta, 2));
  }

  private void calculaTotalVentaPedido()
  {
    totalVenta = 0;
    for (int i = 0; i < VariablesReceta.vArrayList_PedidoReceta.size(); i++)
      totalVenta += FarmaUtility.getDecimalNumber(((String) ((ArrayList) VariablesReceta.vArrayList_PedidoReceta.get(i)).get(7)).trim());
  }

  private void verificaCheckJTable()
  {
    if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS_RECETA))
      actualizaListaProductosAlternativos();
    int row = myJTable.getSelectedRow();
    Boolean valor = 
      (Boolean) (myJTable.getValueAt(myJTable.getSelectedRow(), 0));
    if (valor.booleanValue())
    {
      deseleccionaProducto();
    }
    else
    {
      muestraInfoProd();
      VariablesVentas.vIndProdVirtual = FarmaUtility.getValueFieldJTable(myJTable, row, 13);
      if( VariablesVentas.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
      {
        FarmaUtility.showMessage(this, "¡No puede seleccionar un Producto Virtual en una receta!", txtProducto);
        return;
      }
      muestraIngresoCantidad();
    }
    //txtProducto.selectAll();
    muestraNombreLab(4, lblDescLab_Prod);
    muestraProductoInafectoIgv(11, lblProdIgv);
    //muestraInfoProd();
  }

  private void seleccionaProducto()
  {
    Boolean valorTmp = 
      (Boolean) (myJTable.getValueAt(myJTable.getSelectedRow(), 0));
    
    VariablesReceta.vTotalPrecVtaProd = (FarmaUtility.getDecimalNumber(VariablesReceta.vCant_Ingresada) * FarmaUtility.getDecimalNumber(VariablesReceta.vVal_Prec_Vta));

    FarmaUtility.setCheckValue(myJTable, false);
    Boolean valor = 
      (Boolean) (myJTable.getValueAt(myJTable.getSelectedRow(), 0));
    operaProductoSeleccionadoEnArrayList(valor);
    pintaCheckOtroJTable(myJTable, valorTmp);
    //indicadorItems = FarmaConstants.INDICADOR_S;
    colocaTotalesPedido();
    //FarmaUtility.aceptarTransaccion();
  }

  private void deseleccionaProducto()
  {
    String cantidad = "";
    VariablesReceta.vCod_Prod = 
        ((String) (myJTable.getValueAt(myJTable.getSelectedRow(), 
                                       1))).trim();
    String codigoTmp = "";
    for (int i = 0; i < VariablesReceta.vArrayList_PedidoReceta.size(); i++)
    {
      codigoTmp = (String) ((ArrayList) VariablesReceta.vArrayList_PedidoReceta.get(i)).get(0);
      if (VariablesReceta.vCod_Prod.equalsIgnoreCase(codigoTmp))
      {
        cantidad = (String) ((ArrayList) VariablesReceta.vArrayList_PedidoReceta.get(i)).get(4);
        break;
      }
    }
    Boolean valorTmp = 
      (Boolean) (myJTable.getValueAt(myJTable.getSelectedRow(), 0));
    FarmaUtility.setCheckValue(myJTable, false);
    Boolean valor = 
      (Boolean) (myJTable.getValueAt(myJTable.getSelectedRow(), 0));
    operaProductoSeleccionadoEnArrayList(valor);
    pintaCheckOtroJTable(myJTable, valorTmp);
    //indicadorItems = FarmaConstants.INDICADOR_N;
    colocaTotalesPedido();
  }

  private void operaProductoSeleccionadoEnArrayList(Boolean valor)
  {
    ArrayList myArray = new ArrayList();
    myArray.add(VariablesReceta.vCod_Prod);
    myArray.add(VariablesReceta.vDesc_Prod);
    myArray.add(VariablesReceta.vUnid_Vta);
    myArray.add(VariablesReceta.vVal_Prec_Lista);
    myArray.add(VariablesReceta.vCant_Ingresada);
    myArray.add(VariablesReceta.vPorc_Dcto_1);
    myArray.add(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesReceta.vVal_Prec_Vta), 
          3));
    myArray.add(FarmaUtility.formatNumber(VariablesReceta.vTotalPrecVtaProd, 
          2));
    myArray.add(VariablesReceta.vVal_Bono);
    myArray.add(VariablesReceta.vNom_Lab);
    myArray.add(VariablesReceta.vVal_Frac);
    myArray.add(VariablesReceta.vPorc_Igv_Prod);
    String valIgv = FarmaUtility.formatNumber((FarmaUtility.getDecimalNumber(VariablesReceta.vVal_Prec_Vta) - (FarmaUtility.getDecimalNumber(VariablesReceta.vVal_Prec_Vta) / 
        (1 + (FarmaUtility.getDecimalNumber(VariablesReceta.vPorc_Igv_Prod) / 100)))) *FarmaUtility.getDecimalNumber(VariablesReceta.vCant_Ingresada));
    //log.debug(valIgv);
    VariablesReceta.vVal_Igv_Prod = valIgv;
    myArray.add(VariablesReceta.vVal_Igv_Prod);

    //JCALLO  13.10.2008 validar tratamiento en receta
    ArrayList infoProd = new ArrayList();
    //agregando el indicador si es aplicable el valor fraccion a dicho producto
    try {
      DBVentas.obtieneInfoProductoVta(infoProd,VariablesReceta.vCod_Prod);
      log.info("detalle del producto : "+infoProd);
      String stkFracLoc = FarmaUtility.getValueFieldArrayList(infoProd,0,11);
      if( stkFracLoc.trim().length()<1 ){
          myArray.add(FarmaConstants.INDICADOR_N);
      }else{
          myArray.add(FarmaConstants.INDICADOR_S);
      }
      
    } catch (SQLException e) {
      log.info("error obtener info prod receta : "+e);
    }
    FarmaUtility.operaListaProd(VariablesReceta.vArrayList_PedidoReceta, myArray, valor, 0);
    //log.debug("size : " + VariablesReceta.vArrayList_PedidoReceta.size());
    //log.debug("array : " + VariablesReceta.vArrayList_PedidoReceta);
  }

  private void cancelaOperacion()
  {
    cerrarVentana(false);
  }

  private void aceptaOperacion()
  {
    VariablesReceta.vArrayList_ResumenReceta = new ArrayList();
    for (int i = 0; i < VariablesReceta.vArrayList_PedidoReceta.size(); i++)
      VariablesReceta.vArrayList_ResumenReceta.add((ArrayList) VariablesReceta.vArrayList_PedidoReceta.get(i));
    cerrarVentana(true);
  }

  private void cargaListaFiltro()
  {
    DlgFiltroProductos dlgFiltroProductos = 
      new DlgFiltroProductos(myParentFrame, "", true);
    dlgFiltroProductos.setVisible(true);
    if (FarmaVariables.vAceptar)
    {
      tableModelListaProductosAlternativos.clearTable();
      txtProducto.setText("");
      filtrarTablaProductos(VariablesPtoVenta.vTipoFiltro, VariablesPtoVenta.vCodFiltro);
      setJTable(tblProductos);
      iniciaProceso(false);
      FarmaVariables.vAceptar = false;
      //lblDescFiltro.setText("(" + VariablesAdmCentral.vDesc_Cat_Filtro + " - " + VariablesAdmCentral.vDescFiltro + ")");
    }
  }

  private void filtrarTablaProductos(String pTipoFiltro, String pCodFiltro)
  {
    try
    {
      tableModelListaPrecioProductos.clearTable();
      tableModelListaPrecioProductos.fireTableDataChanged();
      DBVentas.cargaListaProductosVenta_Filtro(tableModelListaPrecioProductos, 
                                               pTipoFiltro, pCodFiltro);
      FarmaUtility.ordenar(tblProductos, tableModelListaPrecioProductos, 2, FarmaConstants.ORDEN_ASCENDENTE);
    }
    catch (SQLException sqlException)
    {
      log.error("",sqlException);
      FarmaUtility.showMessage(this, 
                               "Error al obtener Lista de Productos Filtrado!!!", 
                               txtProducto);
    }
  }

  private void muestraProductosAlternativos()
  {
    try
    {
      if (tblProductos.getSelectedRow() >= 0)
      {
        String codigoProducto = 
          (String) (tblProductos.getValueAt(tblProductos.getSelectedRow(), 
                                            1));
        DBVentas.cargaListaProductosAlternativos(tableModelListaProductosAlternativos, 
                                                 codigoProducto);
        FarmaUtility.ordenar(tblListaProductosAlternativos, 
                             tableModelListaProductosAlternativos, 13, 
                             FarmaConstants.ORDEN_DESCENDENTE);
        tblListaProductosAlternativos.repaint();
      }
    }
    catch (SQLException sqlException)
    {
      log.error("",sqlException);
      FarmaUtility.showMessage(this, 
                               "Error al Listar Productos Alternativos.\n" + 
                               sqlException, txtProducto);
    }
  }

  private void actualizaListaProductosAlternativos()
  {
    tableModelListaProductosAlternativos.clearTable();
    tableModelListaProductosAlternativos.fireTableDataChanged();
    if (esProductoFarma())
    {
      muestraProductosAlternativos();
      FarmaUtility.ponerCheckJTable(tblListaProductosAlternativos, 1, VariablesReceta.vArrayList_PedidoReceta, 
          0);
    }
  }

  private void pintaCheckOtroJTable(JTable pActualJTable, Boolean pValor)
  {
    //log.debug(pValor.booleanValue());
    if (pActualJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS_RECETA))
    {
      FarmaUtility.setearCheckInRow(tblListaProductosAlternativos, pValor, 
                                    true, true, VariablesReceta.vCod_Prod, 
          1);
      tblListaProductosAlternativos.repaint();
    }
    else if (pActualJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS_ALTERNATIVOS_RECETA))
    {
      FarmaUtility.setearCheckInRow(tblProductos, pValor, true, true, VariablesReceta.vCod_Prod, 
          1);
      tblProductos.repaint();
    }
  }

  private void buscaCodigoEnJtable(String pCodBusqueda)
  {
    String codTmp = "";
    for (int i = 0; i < tblProductos.getRowCount(); i++)
    {
      codTmp = ((String) (myJTable.getValueAt(i, 1))).trim();
      if (codTmp.equalsIgnoreCase(pCodBusqueda))
      {
        FarmaGridUtils.showCell(tblProductos, i, 0);
        FarmaUtility.setearRegistro(tblProductos, i, txtProducto, 2);
        FarmaUtility.moveFocus(txtProducto);
        return;
      }
    }
  }

  private boolean esProductoFarma()
  {
    String indicador = 
      ((String) myJTable.getValueAt(myJTable.getSelectedRow(), 12)).trim();
    if (indicador.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
      return true;
    else
      return false;
  }  
  
}
