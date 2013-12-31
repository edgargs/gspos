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
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.*;

import mifarma.ptoventa.reportes.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgResumenDetalleVentasDetallado extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgResumenDetalleVentasDetallado.class);

  private Frame myParentFrame;
  private FarmaTableModel tableModel;
  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private GridLayout gridLayout1 = new GridLayout();
  private JPanelHeader jPanelHeader1 = new JPanelHeader();
  private JLabelWhite lblCodigoT = new JLabelWhite();
  private JLabelWhite lblDescripcionT = new JLabelWhite();
  private JLabelWhite lblUnidadT = new JLabelWhite();
  private JLabelWhite lblVentaT = new JLabelWhite();
  private JLabelWhite lblVenta = new JLabelWhite();
  private JLabelWhite lblCantidadT = new JLabelWhite();
  private JLabelWhite lblCantidad = new JLabelWhite();
  private JLabelWhite lblUnidad = new JLabelWhite();
  private JLabelWhite lbldescripcion = new JLabelWhite();
  private JLabelWhite lblCodigo = new JLabelWhite();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblResumenDetallado = new JTable();
  private JLabelFunction jLabelFunction1 = new JLabelFunction();
  private JPanelTitle jPanelTitle2 = new JPanelTitle();
  private JLabelWhite lblBruto3 = new JLabelWhite();
  private JLabelWhite lblTotalMonto = new JLabelWhite();

  public DlgResumenDetalleVentasDetallado()
  {
    this(null, "", false);
  }

  public DlgResumenDetalleVentasDetallado(Frame parent, String title, boolean modal)
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

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(711, 467));
    this.getContentPane().setLayout(gridLayout1);
    this.setTitle("Detallado Resumen Detalle Ventas");
    this.addWindowListener(new java.awt.event.WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    
    jPanelHeader1.setBounds(new Rectangle(10, 10, 690, 65));
    jPanelHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    jPanelHeader1.setBackground(Color.white);
    lblCodigoT.setText("0");
    lblCodigoT.setBounds(new Rectangle(65, 10, 45, 20));
    lblCodigoT.setForeground(new Color(255, 130, 14));
    lblCodigoT.setFont(new Font("SansSerif", 0, 11));
    lblDescripcionT.setText("0");
    lblDescripcionT.setBounds(new Rectangle(250, 10, 220, 20));
    lblDescripcionT.setForeground(new Color(255, 130, 14));
    lblDescripcionT.setFont(new Font("SansSerif", 0, 11));
    lblUnidadT.setText("0");
    lblUnidadT.setBounds(new Rectangle(60, 35, 165, 20));
    lblUnidadT.setForeground(new Color(255, 130, 14));
    lblUnidadT.setFont(new Font("SansSerif", 0, 11));
    lblVentaT.setText("0");
    lblVentaT.setBounds(new Rectangle(500, 35, 70, 20));
    lblVentaT.setForeground(new Color(255, 130, 14));
    lblVentaT.setFont(new Font("SansSerif", 0, 11));
    lblVenta.setText("Venta :");
    lblVenta.setBounds(new Rectangle(435, 35, 50, 20));
    lblVenta.setForeground(new Color(255, 130, 14));
    lblCantidadT.setText("0");
    lblCantidadT.setBounds(new Rectangle(340, 35, 60, 20));
    lblCantidadT.setForeground(new Color(255, 130, 14));
    lblCantidadT.setFont(new Font("SansSerif", 0, 11));
    lblCantidad.setText("Cantidad :");
    lblCantidad.setBounds(new Rectangle(275, 35, 60, 20));
    lblCantidad.setForeground(new Color(255, 130, 14));
    lblUnidad.setText("Unidad :");
    lblUnidad.setBounds(new Rectangle(5, 35, 50, 20));
    lblUnidad.setForeground(new Color(255, 130, 14));
    lbldescripcion.setText("Descripcion :");
    lbldescripcion.setBounds(new Rectangle(160, 10, 75, 20));
    lbldescripcion.setForeground(new Color(255, 130, 14));
    lblCodigo.setText("Codigo : ");
    lblCodigo.setBounds(new Rectangle(5, 10, 55, 20));
    lblCodigo.setForeground(new Color(255, 130, 14));
    jScrollPane1.setBounds(new Rectangle(15, 90, 685, 280));
    jScrollPane1.addKeyListener(new java.awt.event.KeyAdapter()
      {
        
      });
    tblResumenDetallado.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblResumenDetallado_keyPressed(e);
        }
      });
    jLabelFunction1.setBounds(new Rectangle(580, 405, 117, 19));
    jLabelFunction1.setText("[ ESC ] Escape");
    jPanelTitle2.setBounds(new Rectangle(15, 370, 685, 25));
    lblBruto3.setText("Total S/");
    lblBruto3.setBounds(new Rectangle(485, 5, 50, 15));
    lblTotalMonto.setBounds(new Rectangle(545, 5, 50, 15));
    lblTotalMonto.setText("0");
    jPanelTitle2.add(lblBruto3, null);
    jPanelTitle2.add(lblTotalMonto, null);
    jPanelHeader1.add(lblCodigoT, null);
    jPanelHeader1.add(lblDescripcionT, null);
    jPanelHeader1.add(lblUnidadT, null);
    jPanelHeader1.add(lblVentaT, null);
    jPanelHeader1.add(lblVenta, null);
    jPanelHeader1.add(lblCantidad, null);
    jPanelHeader1.add(lblUnidad, null);
    jPanelHeader1.add(lbldescripcion, null);
    jPanelHeader1.add(lblCodigo, null);
    jPanelHeader1.add(lblCantidadT, null);
    jScrollPane1.getViewport().add(tblResumenDetallado, null);
    jPanelWhite1.add(jPanelTitle2, null);
    jPanelWhite1.add(jLabelFunction1, null);
    jPanelWhite1.add(jScrollPane1, null);
    jPanelWhite1.add(jPanelHeader1, null);
    this.getContentPane().add(jPanelWhite1, null);
  }
   private void initialize()
  {
    initTableListaResumenDetalleVentasDetallado();
    
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    cargaDatosCabecera ();
    cargaListaDetalladoResumenVentas();
    FarmaUtility.moveFocusJTable(tblResumenDetallado);
    lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblResumenDetallado,7)));
  }
  
  private void cargaDatosCabecera ()
  {
    lblCodigoT.setText(VariablesReporte.vCodProd);
    lblDescripcionT.setText(VariablesReporte.vDesProd);
    lblUnidadT.setText(VariablesReporte.vUnidadPresentacion);
    lblCantidadT.setText(VariablesReporte.vCantidad);
    lblVentaT.setText (VariablesReporte.vVentas);
  }
  
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      //cerrarVentana(false);
      this.setVisible(false);
    }
  }
  
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  
  private void  initTableListaResumenDetalleVentasDetallado()
  {
    tableModel = new FarmaTableModel(ConstantsReporte.columnsListaDetalladoResumenVta,ConstantsReporte.defaultValuesListaDetalladoResumenVta,0);
    FarmaUtility.initSimpleList(tblResumenDetallado,tableModel,ConstantsReporte.columnsListaDetalladoResumenVta);
  }
  
  private void cargaListaDetalladoResumenVentas()
  {
    try
    {
      DBReportes.cargaListaResumenVentasDetallado(tableModel,VariablesReporte.vFechaInicio,
                                                  VariablesReporte.vFechaFin,VariablesReporte.vCodProd);
        
   } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this, "Error al listar el Resumen Detallado de Ventas : \n" + sql.getMessage(),null);
      cerrarVentana(false);
    }
  }

  private void tblResumenDetallado_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
}