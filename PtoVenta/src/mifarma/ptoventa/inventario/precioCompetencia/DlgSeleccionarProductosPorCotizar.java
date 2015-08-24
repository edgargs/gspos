package mifarma.ptoventa.inventario.precioCompetencia;
import com.gs.mifarma.componentes.JLabelFunction;

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

import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.*;

import mifarma.ptoventa.inventario.precioCompetencia.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgSeleccionarProductosPorCotizar extends JDialog 
{
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgSeleccionarProductosPorCotizar.class);   
     
  Frame myParentFrame;
  FarmaTableModel tableModel;
  ArrayList arrayProductos = new ArrayList();
  UtilityPrecioCompetencia utilityPrecioCompetencia = new UtilityPrecioCompetencia();
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JScrollPane scrListaProductos = new JScrollPane();
  private JPanel pnlHeader = new JPanel();
  private JButton btnListaProducto = new JButton();
  private JTable tblListaProductos = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();

  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgSeleccionarProductosPorCotizar()
  {
    this(null, "", false);
  }

  public DlgSeleccionarProductosPorCotizar(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(720, 370));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Seleccionar Productos por Cotizar ");
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
    lblEnter.setText("[ ENTER] Seleccionar/Deseleccionar producto");
    lblEnter.setBounds(new Rectangle(15, 305, 275, 20));
    scrListaProductos.setBounds(new Rectangle(10, 30, 695, 270));
    scrListaProductos.setBackground(new Color(255, 130, 14));
    pnlHeader.setBounds(new Rectangle(10, 5, 695, 25));
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
    tblListaProductos.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblListaProductos_keyPressed(e);
        }
      });
        lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(580, 305, 85, 20));
        jLabelFunction1.setBounds(new Rectangle(470, 305, 100, 20));
        jLabelFunction1.setText("[ F11 ] Aceptar");
        scrListaProductos.getViewport();
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        pnlHeader.add(btnListaProducto, null);
        jContentPane.add(pnlHeader, null);
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
    tableModel = new FarmaTableModel(ConstantsPrecioCompetencia.columnsSeleccionarListaProductosPorCotizar,ConstantsPrecioCompetencia.defaultcolumnsSeleccionarListaProductosPorCotizar,0);
    cargaListaProductosPorCotizar();
    FarmaUtility.initSelectList(tblListaProductos,tableModel,ConstantsPrecioCompetencia.columnsSeleccionarListaProductosPorCotizar);
    
      for (int i=0; i<tableModel.getRowCount(); i++)
        tableModel.setValueAt(new Boolean(false),i,0);
      cargaProductosSeleccionados();
      if(tblListaProductos.getRowCount() > 0)
        FarmaGridUtils.showCell(tblListaProductos,0,0);
    
  }
  
 
  
  private void cargaListaProductosPorCotizar()
  {
    try
    {
      utilityPrecioCompetencia.cargaSeleccionProductosPorCotizarDAO(tableModel, tblListaProductos);
    } catch(Exception sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Error al cargar la lista de productos : \n" + sql.getMessage(),null);
    }
    
  }
  
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(tblListaProductos);
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
    private void tblListaProductos_keyPressed(KeyEvent e) {
      chkKeyPressed(e);
    }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
          seleccionarProducto();
        }else if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) 
        {
          //VariablesInventario.vArrayGuiaIngresoProductos = arrayProductos;
          VariablesPrecioCompetencia.vArrayProductosCotizados = arrayProductos;
          cerrarVentana(true);
        }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
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
      //VariablesPrecioCompetencia.vStkFisico = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),5).toString();
      //VariablesPrecioCompetencia.vValFrac = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),9).toString();
      
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
    
}