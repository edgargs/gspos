package mifarma.ptoventa.inventario.precioCompetencia;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.*;

import mifarma.ptoventa.inventario.precioCompetencia.reference.ConstantsPrecioCompetencia;
import mifarma.ptoventa.inventario.precioCompetencia.reference.*;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgSeleccionarProductosNoCanasta extends JDialog 
{
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgSeleccionarProductosNoCanasta.class);   
     
  Frame myParentFrame;
  FarmaTableModel tableModel;
  ArrayList arrayProductos = new ArrayList();
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JScrollPane scrListaProductos = new JScrollPane();
  private JPanel pnlHeader = new JPanel();
  private JButton btnListaProducto = new JButton();
  private JPanel pnlTitle = new JPanel();
  private JButton btnBuscar = new JButton();
  private JTextFieldSanSerif txtProducto = new JTextFieldSanSerif();
  private JButton btnProducto = new JButton();
  private JTable tblListaProductos = new JTable();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();

  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgSeleccionarProductosNoCanasta()
  {
    this(null, "", false);
  }

  public DlgSeleccionarProductosNoCanasta(Frame parent, String title, boolean modal)
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

  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(720, 441));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Seleccionar Productos por Cotizar - No Canasta");
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
    jContentPane.setBackground(Color.white);
    jContentPane.setSize(new Dimension(594, 405));
    lblEnter.setText("[ ENTER ] Seleccionar/Deseleccionar Producto");
    lblEnter.setBounds(new Rectangle(15, 375, 270, 20));
    scrListaProductos.setBounds(new Rectangle(10, 90, 695, 270));
    scrListaProductos.setBackground(new Color(255, 130, 14));
    pnlHeader.setBounds(new Rectangle(10, 65, 695, 25));
    pnlHeader.setBackground(new Color(255, 130, 14));
    pnlHeader.setLayout(null);
    btnListaProducto.setText("Lista de Productos");
    btnListaProducto.setBounds(new Rectangle(10, 0, 145, 25));
    btnListaProducto.setBackground(new Color(255, 130, 14));
    btnListaProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnListaProducto.setBorderPainted(false);
    btnListaProducto.setContentAreaFilled(false);
    btnListaProducto.setDefaultCapable(false);
    btnListaProducto.setFocusPainted(false);
    btnListaProducto.setFont(new Font("SansSerif", 1, 11));
    btnListaProducto.setForeground(Color.white);
    btnListaProducto.setHorizontalAlignment(SwingConstants.LEFT);
    btnListaProducto.setRequestFocusEnabled(false);
    pnlTitle.setBounds(new Rectangle(10, 20, 695, 40));
    pnlTitle.setBackground(new Color(43, 141, 39));
    pnlTitle.setLayout(null);
    btnBuscar.setText("Buscar");
    btnBuscar.setBounds(new Rectangle(425, 10, 115, 25));
    btnBuscar.setFont(new Font("SansSerif", 1, 12));
    btnBuscar.setDefaultCapable(false);
    btnBuscar.setFocusPainted(false);
    btnBuscar.setMnemonic('b');
    btnBuscar.setRequestFocusEnabled(false);
    txtProducto.setBounds(new Rectangle(110, 10, 295, 20));
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
    btnProducto.setText("Producto :");
    btnProducto.setBounds(new Rectangle(25, 10, 70, 20));
    btnProducto.setFont(new Font("SansSerif", 1, 12));
    btnProducto.setForeground(Color.white);
    btnProducto.setBackground(new Color(43, 141, 39));
    btnProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnProducto.setBorderPainted(false);
    btnProducto.setContentAreaFilled(false);
    btnProducto.setDefaultCapable(false);
    btnProducto.setFocusPainted(false);
    btnProducto.setMnemonic('p');
    btnProducto.setRequestFocusEnabled(false);
    btnProducto.setHorizontalAlignment(SwingConstants.LEFT);
    btnProducto.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnProducto_actionPerformed(e);
        }
      });
    btnProducto.addContainerListener(new ContainerAdapter()
      {
        public void componentRemoved(ContainerEvent e)
        {
          btnProducto_componentRemoved(e);
        }
      });
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(445, 375, 105, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(555, 375, 85, 20));
    scrListaProductos.getViewport();
    pnlHeader.add(btnListaProducto, null);
    pnlTitle.add(btnBuscar, null);
    pnlTitle.add(txtProducto, null);
    pnlTitle.add(btnProducto, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    jContentPane.add(lblEsc, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(lblEnter, null);
    scrListaProductos.getViewport().add(tblListaProductos, null);
    jContentPane.add(scrListaProductos, null);
    jContentPane.add(pnlHeader, null);
    jContentPane.add(pnlTitle, null);
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
    tableModel      = new FarmaTableModel(ConstantsPrecioCompetencia.columnsListaProductosNoCanasta,ConstantsPrecioCompetencia.defaultListaProductosNoCanasta,0);
    tableModel.data = VariablesVentas.tableModelListaGlobalProductos.data;
    FarmaUtility.initSelectList(tblListaProductos,tableModel,ConstantsPrecioCompetencia.columnsListaProductosNoCanasta);
    for (int i=0; i<VariablesVentas.tableModelListaGlobalProductos.getRowCount(); i++)
      VariablesVentas.tableModelListaGlobalProductos.setValueAt(new Boolean(false),i,0);
    cargaProductosSeleccionados();
    FarmaGridUtils.showCell(tblListaProductos,0,0);
  }
  
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtProducto);
  }

  private void btnProducto_actionPerformed(ActionEvent e) {
    FarmaUtility.moveFocus(txtProducto);
  }
  
  private void txtProducto_keyPressed(KeyEvent e) {
    FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtProducto, 2);
    
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      e.consume();
      if (tblListaProductos.getSelectedRow() >= 0) {
        if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtProducto.getText().trim(), 1, 2)) ) 
        {
          FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtProducto);
          return;
        }
      }
    } 
  
    chkKeyPressed(e);
  }
  
  private void txtProducto_keyReleased(KeyEvent e)
  {
    FarmaGridUtils.buscarDescripcion(e,tblListaProductos,txtProducto,2);
  }
  
  private void btnProducto_componentRemoved(ContainerEvent e)
  {
    FarmaUtility.moveFocus(txtProducto);
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
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      seleccionarProducto();
    }else if(UtilityPtoVenta.verificaVK_F11(e)) 
    {
      VariablesPrecioCompetencia.vArrayProductosCotizados = arrayProductos;
      cerrarVentana(true);
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
  }
  
  private void cerrarVentana(boolean pAceptar) {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
    this.dispose();
  }
  
  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */

  private void seleccionarProducto()
  {
    boolean seleccion = ((Boolean)tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),0)).booleanValue();
    if(!seleccion)
    {
      cargaCabeceraIngreseCantidad();
            DlgCotizacionCantidad dlgCotizacionCantidad = new DlgCotizacionCantidad(myParentFrame,"",true);
      dlgCotizacionCantidad.setVisible(true); 
      if(FarmaVariables.vAceptar)
      {
        agregarProducto();
        FarmaUtility.setCheckValue(tblListaProductos,false);
        FarmaVariables.vAceptar = false;
        tblListaProductos.setRowSelectionInterval(VariablesPrecioCompetencia.vPos,VariablesPrecioCompetencia.vPos);
      }
    }
    else
    {
      borrarProducto();
      FarmaUtility.setCheckValue(tblListaProductos,false);
    }
    
  }

  private void cargaCabeceraIngreseCantidad()
  {
    VariablesPrecioCompetencia.vCodProd = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1).toString();
    VariablesPrecioCompetencia.vNomProd = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),2).toString();
    VariablesPrecioCompetencia.vUnidMed = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),3).toString();
    VariablesPrecioCompetencia.vNomLab = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),4).toString();
    VariablesPrecioCompetencia.vStkFisico = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),5).toString();
    VariablesPrecioCompetencia.vValFrac = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),9).toString();
    VariablesPrecioCompetencia.vCant = "";
    VariablesPrecioCompetencia.vImagen = "";
    VariablesPrecioCompetencia.vPrecUnit = "";
    VariablesPrecioCompetencia.vFileImagen = null;
    
    VariablesPrecioCompetencia.vPos = tblListaProductos.getSelectedRow();
  }
  
  private void agregarProducto()
  {
    ArrayList array = new ArrayList();
    array.add(VariablesPrecioCompetencia.vCodProd);
    array.add(VariablesPrecioCompetencia.vNomProd);
    array.add(VariablesPrecioCompetencia.vUnidMed);
    array.add(VariablesPrecioCompetencia.vNomLab);
    array.add(VariablesPrecioCompetencia.vCant);
    array.add(VariablesPrecioCompetencia.vPrecUnit);
    array.add(VariablesPrecioCompetencia.vTotal);
    array.add(VariablesPrecioCompetencia.vImagen);
    array.add(VariablesPrecioCompetencia.vValFrac);
    array.add(VariablesPrecioCompetencia.vFileImagen);
    arrayProductos.add(array);
  }
  
  private void borrarProducto()
  {
    String cod = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1).toString();
    
    for(int i=0;i<arrayProductos.size();i++)
    {
      if(((ArrayList)arrayProductos.get(i)).contains(cod))
      {
        arrayProductos.remove(i);
        break;
      }
    }
  }
  
  private void cargaProductosSeleccionados()
  {
    if(VariablesPrecioCompetencia.vArrayProductosCotizados.size()>0)
    {
      arrayProductos = VariablesPrecioCompetencia.vArrayProductosCotizados;
      String cod;
      for(int i=0;i<arrayProductos.size();i++)
      {
        cod = ((ArrayList)arrayProductos.get(i)).get(0).toString();
        for(int j=0;j<tblListaProductos.getRowCount();j++)
        {
          if(((ArrayList)tableModel.getRow(j)).contains(cod))
          {
            tableModel.setValueAt(new Boolean(true),j,0);
            break;
          }
        }
      }
    }
  }

}