package mifarma.ptoventa.reportes;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.*;

import javax.swing.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.*;

import mifarma.ptoventa.reportes.reference.*;
import com.gs.mifarma.componentes.JButtonLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;


public class DlgComprobantes extends JDialog 
{
  /* ************************************************************************ */
  /*                          DECLARACION PROPIEDADES                         */
  /* ************************************************************************ */

  FarmaTableModel tableModelComprobantes;
  FarmaTableModel tableModelDetalleComprobantes;
  private Frame myParentFrame;
  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private GridLayout gridLayout1 = new GridLayout();
  private JPanelTitle jPanelTitle1 = new JPanelTitle();
  private JScrollPane srcComprobantes = new JScrollPane();
  private JPanelTitle jPanelTitle2 = new JPanelTitle();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JScrollPane srcDetalleComprobante = new JScrollPane();
  private JTable tblComprobantes = new JTable();
  private JTable tblDetalleComprobantes = new JTable();
  private JLabelFunction jLabelFunction1 = new JLabelFunction();
  private JButtonLabel btnComprobantes = new JButtonLabel();
  private JButtonLabel btnDetalleComprobante = new JButtonLabel();

  /* ************************************************************************ */
  /*                          CONSTRUCTORES                                   */
  /* ************************************************************************ */

  public DlgComprobantes()
  {
    this(null, "", false);
  }

  public DlgComprobantes(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(746, 500));
    this.getContentPane().setLayout(gridLayout1);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Comprobantes del Pedido");
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
    
    jPanelTitle1.setBounds(new Rectangle(5, 10, 720, 25));
    srcComprobantes.setBounds(new Rectangle(5, 30, 720, 155));
    jPanelTitle2.setBounds(new Rectangle(5, 195, 720, 25));
    jScrollPane2.setBounds(new Rectangle(0, 25, 685, 210));
    srcDetalleComprobante.setBounds(new Rectangle(5, 220, 720, 210));
    tblComprobantes.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyReleased(KeyEvent e)
        {
          tblComprobantes_keyReleased(e);
        }

        public void keyPressed(KeyEvent e)
        {
          tblComprobantes_keyPressed(e);
        }

       
      });
    tblDetalleComprobantes.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblDetalleComprobantes_keyPressed(e);
        }
      });
    jLabelFunction1.setBounds(new Rectangle(605, 435, 117, 19));
    jLabelFunction1.setText("[ESC]Escape");
    btnComprobantes.setText("Comprobantes");
    btnComprobantes.setBounds(new Rectangle(10, 5, 100, 15));
    btnComprobantes.setMnemonic('c');
    btnComprobantes.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnComprobantes_actionPerformed(e);
        }
      });
    btnDetalleComprobante.setText("Detalle del Comprobante");
    btnDetalleComprobante.setBounds(new Rectangle(10, 5, 160, 20));
    btnDetalleComprobante.setMnemonic('D');
    btnDetalleComprobante.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDetalleComprobante_actionPerformed(e);
        }
      });
    jPanelTitle2.add(btnDetalleComprobante, null);
    jPanelTitle2.add(jScrollPane2, null);
    srcDetalleComprobante.getViewport().add(tblDetalleComprobantes, null);
    jPanelWhite1.add(jLabelFunction1, null);
    jPanelWhite1.add(srcDetalleComprobante, null);
    jPanelWhite1.add(jPanelTitle2, null);
    srcComprobantes.getViewport().add(tblComprobantes, null);
    jPanelWhite1.add(srcComprobantes, null);
    jPanelTitle1.add(btnComprobantes, null);
    jPanelWhite1.add(jPanelTitle1, null);
    this.getContentPane().add(jPanelWhite1, null);
  }
  
  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */
  private void initialize() {
    initTableListaComprobantes();
    FarmaVariables.vAceptar = false;
  }
  
  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */
   private void initTableListaComprobantes()
  {
    tableModelComprobantes = new FarmaTableModel(ConstantsReporte.columnsListaComprobantes,ConstantsReporte.defaultValuesListaComprobantes,0);
    FarmaUtility.initSimpleList(tblComprobantes,tableModelComprobantes,ConstantsReporte.columnsListaComprobantes);
  }
  private void initTableListaDetalleComprobantes()
  {
    tableModelDetalleComprobantes = new FarmaTableModel(ConstantsReporte.columnsListaComprobantesDetalle,ConstantsReporte.defaultValuesListaComprobantesDetalle,0);
    FarmaUtility.initSimpleList(tblDetalleComprobantes,tableModelDetalleComprobantes,ConstantsReporte.columnsListaComprobantesDetalle);
  }

  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */
  private void this_windowOpened(WindowEvent e)
  {
   FarmaUtility.centrarVentana(this);
   FarmaUtility.moveFocus(tblComprobantes);
   buscaComprobantes(VariablesReporte.vCorrelativo);
   System.out.println("Correlativo" + VariablesReporte.vCorrelativo);
   listaDetalleComprobantes();
           
  }
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */
   private void chkKeyPressed(KeyEvent e)
  {
  if (e.getKeyCode() != KeyEvent.VK_ESCAPE){
    VariablesReporte.vNComprobante = ((String)tblComprobantes.getValueAt(tblComprobantes.getSelectedRow(),1)).trim();
    System.out.println("variables reporte vNComprobante" + VariablesReporte.vNComprobante);
    cargaDetalleComprobantesVenta();   
		}   
  }

  
   private void tblComprobantes_keyReleased(KeyEvent e)    
  {
  chkKeyPressed(e);  
  }

  private void tblComprobantes_keyPressed(KeyEvent e)
  {
  if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
      cerrarVentana(false);
		}
  }

  /** Permite mover el foco a la tabla comprobantesw y tabla detalle de comprobantes
   * @author: JCORTEZ
   * @since: 04/07/07
   */
  private void btnComprobantes_actionPerformed(ActionEvent e)
  {
  FarmaUtility.moveFocus(tblComprobantes);
  }

  private void btnDetalleComprobante_actionPerformed(ActionEvent e)
  {
  FarmaUtility.moveFocus(tblDetalleComprobantes);
  }
  
  private void tblDetalleComprobantes_keyPressed(KeyEvent e)
  {
 if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
      cerrarVentana(false);
		}
  }

  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */
  private void listaDetalleComprobantes()
  {
    if(tblComprobantes.getRowCount() > 0)
    {
      initTableListaDetalleComprobantes();
      cargaDetalleComprobantesVenta();
    }
  }
  
   private void buscaComprobantes(String pCodigo)
  {
    VariablesReporte.vCorrelativo = pCodigo;
    cargaComprobantesVenta();
  }
   private void cargaComprobantesVenta()
  {
    try
    {
      System.out.println(VariablesReporte.vCorrelativo);
      DBReportes.obtieneComprobantes_Venta(tableModelComprobantes,VariablesReporte.vCorrelativo);
      FarmaUtility.ordenar(tblComprobantes, tableModelComprobantes, 0, FarmaConstants.ORDEN_ASCENDENTE);
    } catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error al listar los Comprobantes Venta:\n "+sql.getMessage() ,null);
      cerrarVentana(false);
    }
  }
  
  private void cargaDetalleComprobantesVenta()
  {
    try
    {
      System.out.println("Nro de comprobantes"+" "+VariablesReporte.vNComprobante);
      DBReportes.obtieneComprobantes_Venta_Detalle(tableModelDetalleComprobantes,VariablesReporte.vNComprobante);
      FarmaUtility.ordenar(tblDetalleComprobantes, tableModelDetalleComprobantes, 0, FarmaConstants.ORDEN_ASCENDENTE);
    } catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this, "Error al listar los Detalles de los Comprobantes Venta :\n" + sql.getMessage(),null);
      cerrarVentana(false);
    }
  }
  
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }  

  private void this_windowClosing(WindowEvent e)
  {
  FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

 
  
}
