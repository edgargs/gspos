package mifarma.ptoventa.inventario.precioCompetencia;

import com.gs.mifarma.componentes.JConfirmDialog;
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

import mifarma.electronico.UtilityImpCompElectronico;
import mifarma.ptoventa.inventario.precioCompetencia.reference.*;

import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgProductosPorCotizar extends JDialog 
{
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgProductosPorCotizar.class);   
     
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

  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgProductosPorCotizar()
  {
    this(null, "", false);
  }

  public DlgProductosPorCotizar(Frame parent, String title, boolean modal)
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
    this.setTitle("Lista de Productos por Cotizar ");
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
    lblEnter.setText("[ F12 ] Imprimir");
    lblEnter.setBounds(new Rectangle(15, 305, 130, 20));
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
    lblEsc.setBounds(new Rectangle(555, 305, 85, 20));
    scrListaProductos.getViewport();
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
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
    tableModel = new FarmaTableModel(ConstantsPrecioCompetencia.columnsListaProductosPorCotizar,ConstantsPrecioCompetencia.defaultcolumnsListaProductosPorCotizar,0);
    cargaListaProductosPorCotizar();
    FarmaUtility.initSimpleList(tblListaProductos,tableModel,ConstantsPrecioCompetencia.columnsListaProductosPorCotizar);
  }
  
  private void cargaListaProductosPorCotizar()
  {
    try
    {
      utilityPrecioCompetencia.cargaListaProductosPorCotizarDAO(tableModel, tblListaProductos);
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
    if(e.getKeyCode() == KeyEvent.VK_F12)
    {
        imprimir();
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
  private void imprimir() {
      boolean b;
        try {
            UtilityImpCompElectronico utilityImpCompElectronico = new UtilityImpCompElectronico();
            if(tblListaProductos.getRowCount()>0){
                if (JConfirmDialog.rptaConfirmDialog(this, "Está seguro de imprimir?")){    
                    b = utilityImpCompElectronico.impresionTermica(UtilityPrecioCompetencia.formatoImpresionListaProductosPorCotizarDAO(), null );//VariablesPtoVenta.vTipoImpTermicaxIp, VariablesPtoVenta.vImpresoraActual.getName(), false, null, false);
                    if(b){
                       FarmaUtility.showMessage(this, "Imprimió listado!.", null);
                    }else{
                       FarmaUtility.showMessage(this, "No se pudo imprimir.", null);
                        }
                }    
            }else{
                 FarmaUtility.showMessage(this, "No hay productos asignados para imprimir.", null);
                }
        } catch (Exception e) {
            log.error(e.getMessage());
        } 
    }
}