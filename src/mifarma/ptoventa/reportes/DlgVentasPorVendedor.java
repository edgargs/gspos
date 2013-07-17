package mifarma.ptoventa.reportes;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.util.ArrayList;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.*;

import mifarma.ptoventa.reportes.reference.*;
import mifarma.ptoventa.reportes.reference.VariablesReporte;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;



import javax.swing.JFrame;

public class DlgVentasPorVendedor extends JDialog 
{
  private FarmaTableModel tableModelVentasVendedor;
  private Frame myParentFrame;

  /**
   * Columnas de la tabla
   * @author Edgar Rios Navarro
   * @since 21.05.2008
   */
 /* private final int COL_COD_VEND = 0; 
  private final int COL_NOM_VEND = 1; 
  private final int COL_TOT_VTA = 2; 
  private final int COL_TOT_BONO = 3; 
  private final int COL_GPO_A = 4; 
  private final int COL_VTA_PP = 6; 
  private final int COL_NO_FARMA = 10; 
  private final int COL_NO_A = 11; 
  private final int COL_SEC_USU = 12;*/

  private final int COL_COD_VEND = 0;
  private final int COL_NOM_VEND = 1;
  private final int COL_TOT_VTA_CIGV = 2;
  private final int COL_TOT_VTA_SIGV = 3;
  private final int COL_GG = 4;
  private final int COL_G = 5;
  private final int COL_OTROS= 6;
  private final int COL_SERVICIOS = 7;
  private final int COL_PORCENTAJE = 8;
  private final int COL_CALCULADO = 9;
  private final int COL_TIPO_FILA = 11;
  private final int COL_SEC_USU = 12;
  private final int COL_ORDEN = 13;
  private final int COL_LOGIN = 14;
  
                                
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
  private JPanelTitle pnlTitulo = new JPanelTitle();
  private JPanelTitle pnlResultados = new JPanelTitle();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblVentasVendedor = new JTable();
  private JButtonLabel btnPeriodo = new JButtonLabel();
  private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
  private JButton btnBuscar = new JButton();
  private JButtonLabel btnListado = new JButtonLabel();
  private JLabel lblRegsitros_T = new JLabel();
  private JLabel lblRegistros = new JLabel();

  private JLabel lblT_G = new JLabel();
  private JLabel lbl_G = new JLabel();
  
  private JLabelFunction lblF1 = new JLabelFunction();
  private JLabelFunction lblF5 = new JLabelFunction();  
  private JLabelFunction lblF9 = new JLabelFunction();
  private JLabelFunction lblF12 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
    private JLabel jLabel1 = new JLabel();
    private JLabel lblPorcTotGar = new JLabel();

    public DlgVentasPorVendedor()
  {
    this(null, "", false);
  }

  public DlgVentasPorVendedor(Frame parent, String title, boolean modal)
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

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(744, 428));
    this.getContentPane().setLayout(borderLayout1);
     this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Ventas por Vendedor");
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
    pnlCriterioBusqueda.setBounds(new Rectangle(10, 15, 720, 30));
    pnlTitulo.setBounds(new Rectangle(10, 50, 720, 20));
    pnlResultados.setBounds(new Rectangle(10, 340, 720, 20));
    jScrollPane1.setBounds(new Rectangle(10, 70, 720, 270));
    tblVentasVendedor.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblVentasVendedor_keyPressed(e);
        }
      });
    btnPeriodo.setText("Periodo :");
    btnPeriodo.setBounds(new Rectangle(120, 5, 60, 20));
    btnPeriodo.setMnemonic('p');
    btnPeriodo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPeriodo_actionPerformed(e);
        }
      });
    txtFechaIni.setBounds(new Rectangle(195, 5, 101, 19));
    txtFechaIni.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtFechaIni_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          txtFechaIni_keyReleased(e);
        }
      });
    txtFechaFin.setBounds(new Rectangle(315, 5, 101, 19));
    txtFechaFin.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtFechaFin_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          txtFechaFin_keyReleased(e);
        }
      });
    btnBuscar.setText("Buscar");
    btnBuscar.setBounds(new Rectangle(445, 5, 95, 20));
    btnBuscar.setMnemonic('b');
    btnBuscar.setFont(new Font("SansSerif", 1, 11));
    btnBuscar.setFocusPainted(false);
    btnBuscar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnBuscar_actionPerformed(e);
        }
      });
    btnListado.setText("Listado de Ventas por Vendedor");
    btnListado.setBounds(new Rectangle(10, 0, 345, 20));
    btnListado.setMnemonic('l');
    btnListado.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnListado_actionPerformed(e);
        }
      });
    lblRegsitros_T.setText("Registros :");
    lblRegsitros_T.setBounds(new Rectangle(0, 0, 70, 20));
    lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
    lblRegsitros_T.setForeground(Color.white);
    lblRegistros.setText("0");
    lblRegistros.setBounds(new Rectangle(65, 0, 40, 20));
    lblRegistros.setFont(new Font("SansSerif", 1, 11));
    lblRegistros.setForeground(Color.white);
    lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);

    lblT_G.setText("Total Garantizado :");
    lblT_G.setBounds(new Rectangle(240, 0, 105, 20));
    lblT_G.setFont(new Font("SansSerif", 1, 11));
    lblT_G.setForeground(Color.white);
    lbl_G.setText("0");
    lbl_G.setBounds(new Rectangle(350, 0, 60, 20));
    lbl_G.setFont(new Font("SansSerif", 1, 11));
    lbl_G.setForeground(Color.white);
    lbl_G.setHorizontalAlignment(SwingConstants.RIGHT);
  
    
    lblF1.setBounds(new Rectangle(25, 370, 105, 20));
    lblF1.setText("[ F1 ] Ver Detalle");
      lblF5.setBounds(new Rectangle(490, 370, 105, 20));
      lblF5.setText("[ F5 ] Ver Grafico");    
      
    lblF9.setBounds(new Rectangle(185, 370, 105, 20));
    lblF9.setText("[ F9 ] Ordenar");
    lblF12.setBounds(new Rectangle(340, 370, 105, 20));
    lblF12.setText("[ F12 ] Imprimir");
    lblEsc.setBounds(new Rectangle(645, 370, 85, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    //lblF10.setBounds(new Rectangle(150, 370, 135, 20));
    //lblF10.setText("[ F8 ]Guardar Archivo");
    lblRegistros1.setText("0");
    lblRegistros1.setBounds(new Rectangle(-100, 0, 40, 20));
    lblRegistros1.setFont(new Font("SansSerif", 1, 11));
    lblRegistros1.setForeground(Color.white);
    lblRegistros1.setHorizontalAlignment(SwingConstants.RIGHT);
      lblPorcTotGar.setText("%");
      lblPorcTotGar.setBounds(new Rectangle(415, 0, 20, 20));
      lblPorcTotGar.setFont(new Font("SansSerif", 1, 11));
      lblPorcTotGar.setForeground(Color.white);
 
      
        
        jLabel1.setText("jLabel1");
        jScrollPane1.getViewport().add(tblVentasVendedor, null);
    //jContentPane.add(lblF10, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(jScrollPane1, null);
    jContentPane.add(pnlResultados, null);
        pnlResultados.add(lblPorcTotGar, null);
        pnlResultados.add(lblRegistros1, null);
    pnlResultados.add(lblRegistros, null);
    pnlResultados.add(lblRegsitros_T, null);
    pnlResultados.add(lblT_G, null);
    pnlResultados.add(lbl_G, null);
    pnlTitulo.add(btnListado, null);
    jContentPane.add(pnlTitulo, null);
    jContentPane.add(pnlCriterioBusqueda, null);
    pnlCriterioBusqueda.add(btnBuscar, null);
    pnlCriterioBusqueda.add(txtFechaFin, null);
    pnlCriterioBusqueda.add(txtFechaIni, null);
    pnlCriterioBusqueda.add(btnPeriodo, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ********************************************************************** */
  /*                            METODO INITIALIZE                           */
  /* ********************************************************************** */
  private void initialize()
  {
      System.out.println("XXXXXXXXXXXXXXXXXX");
    initTableListaVentasVendedor();
  }

  /* ********************************************************************** */
  /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
  /* ********************************************************************** */
  private void initTableListaVentasVendedor()
  {
    tableModelVentasVendedor = new FarmaTableModel(ConstantsReporte.columnsListaVentasVendedor,ConstantsReporte.defaultValuesListaVentasVendedor,0);
    FarmaUtility.initSimpleList(tblVentasVendedor,tableModelVentasVendedor,ConstantsReporte.columnsListaVentasVendedor);
  }
  
  
  private JLabelFunction lblF10 = new JLabelFunction();
  private JLabel lblRegistros1 = new JLabel();
  
  /* ********************************************************************** */
  /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
  /* ********************************************************************** */
  

  private void txtFechaFin_keyPressed(KeyEvent e)
  {
  if(e.getKeyCode() == KeyEvent.VK_ENTER){
  btnBuscar.doClick();
  }
  
  else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
  cerrarVentana(false);  
  chkKeyPressed(e);
  }

  private void tblVentasVendedor_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtFechaIni);
  }
  
  private void btnBuscar_actionPerformed(ActionEvent e)
  {
    
    //  if(VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_TOTALES)){
          busqueda();
          System.out.println("dlgventasporvendedor_if:" + VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA);
          //Limpiando la variable
         // VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA="";
          //System.out.println("Limpiando VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA:" + VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA);
          
     // }
      
     
  }
  
  private void btnPeriodo_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtFechaIni);
  }
  
  private void btnListado_actionPerformed(ActionEvent e)
  {
    if(tblVentasVendedor.getRowCount() > 0)
    {
      FarmaUtility.moveFocus(tblVentasVendedor);
    }
  }
  
  /* ********************************************************************** */
  /*                            METODOS AUXILIARES                          */
  /* ********************************************************************** */
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_F1)
    {
      int vFila = tblVentasVendedor.getSelectedRow();
      if(tblVentasVendedor.getRowCount() <= 0){
        FarmaUtility.showMessage(this,"Ingrese un criterio de Busqueda", txtFechaIni);
        FarmaUtility.moveFocus(txtFechaIni);
        return;
      }
      else
      {
        VariablesReporte.vCodigoUsuario = ((String)tblVentasVendedor.getValueAt(vFila,COL_SEC_USU)).trim();
        VariablesReporte.vNombreUsuario = ((String)tblVentasVendedor.getValueAt(vFila,COL_NOM_VEND)).trim();
        
       
        muestraDetalleVentasVendedor();
       
          
      }     
    } else if(e.getKeyCode() == KeyEvent.VK_F9)
    {
      if(tblVentasVendedor.getRowCount() <= 0)
      FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de ordenar",txtFechaIni);
      else
      muestraVentaOrdenar();
           
    } else if(e.getKeyCode() == KeyEvent.VK_F8)
    {
      if(tblVentasVendedor.getRowCount() <= 0)
      FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de guardar",txtFechaIni);
      //else ;
      //exportarDatos();
      
    } else if(e.getKeyCode() == KeyEvent.VK_F12)
    {
      if(tblVentasVendedor.getRowCount() <= 0)
        FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de imprmir",txtFechaIni);
        else
        imprimir();
      
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      this.setVisible(false);
      
    }else if (e.getKeyCode() == KeyEvent.VK_ENTER)    
    {
    
    }
    else if(e.getKeyCode() == KeyEvent.VK_F5)
    {
       if(tblVentasVendedor.getRowCount() <= 0)
           FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de ver el gráfico.",txtFechaIni);
       else
        muestraGrafico();
    }  
  }
  private void buscaRegistroVentasVendedor(String pFechaInicio, String pFechaFin)
  {
    VariablesReporte.vFechaInicio = pFechaInicio;
    VariablesReporte.vFechaFin = pFechaFin;
    
    //TOTALES
      if(VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_TOTALES)){
      
          btnListado.setText("Listado de Ventas por Vendedor");
        cargaRegistroVentasVendedor();
      
      }
      //    TIPO : MEZON
      else if(VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_MEZON)){
          System.out.println("ConstantsReporte.Tipo_Venta_Mezon:"+ ConstantsReporte.Tipo_Venta_Mezon);
         
          btnListado.setText("Listado de Ventas por Vendedor Mezon");
          cargaRegistroVentasVendedorTipo(ConstantsReporte.Tipo_Venta_Mezon);
      }
      
      //    TIPO : delivery 
      else if(VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_DELIVERY)){
          System.out.println("ConstantsReporte.Tipo_Venta_Mezon:"+ ConstantsReporte.Tipo_Venta_Delivery);
        
          btnListado.setText("Listado de Ventas por Vendedor Delivery");
          cargaRegistroVentasVendedorTipo(ConstantsReporte.Tipo_Venta_Delivery);
      }
      
      else{
          System.out.println("ConstantsReporte.Tipo_Venta:" + VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA );
           
            btnListado.setText("Listado de Ventas por Vendedor Institucional");
            cargaRegistroVentasVendedorTipo(ConstantsReporte.Tipo_Venta_Institucional);
        }
  }
  private void cargaRegistroVentasVendedor()
  {
    try
    {
      // System.out.println(VariablesReporte.vFechaInicio);
      // System.out.println(VariablesReporte.vFechaFin);
      DBReportes.cargaListaVentasporVendedor(tableModelVentasVendedor,
                                             VariablesReporte.vFechaInicio,
                                             VariablesReporte.vFechaFin);
      
      if(tblVentasVendedor.getRowCount() > 0)
      {
        lblRegistros.setText("" + tblVentasVendedor.getRowCount());
        double T_GG = 0.0;
        double T_G  = 0.0;
        for(int i=0;i<tblVentasVendedor.getRowCount();i++){
            if(FarmaUtility.getValueFieldJTable(tblVentasVendedor,i,COL_TIPO_FILA).toString().trim().
               equalsIgnoreCase("PORCENTAJE")){
                T_GG = FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblVentasVendedor,i,COL_GG ).toString().trim());
                T_G  = FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblVentasVendedor,i,COL_G ).toString().trim());
                lbl_G.setText(""+(FarmaUtility.formatNumber((T_GG + T_G))));
                break;
            }
        }
        FarmaUtility.ordenar(tblVentasVendedor,
                             tableModelVentasVendedor,
                            // COL_ORDEN,
                               0,
                             FarmaConstants.ORDEN_DESCENDENTE);
        FarmaUtility.moveFocusJTable(tblVentasVendedor);
      }
      else
      {
        FarmaUtility.showMessage(this,"No se encontro resultados para la busqueda", txtFechaIni);
        lblRegistros.setText("0");
        lbl_G.setText("0");
      }
      
    }catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this, "Error al listar Ventas por Vendedor :\n" + sql.getMessage(),txtFechaIni);
    }
  }
  
  private void cargaRegistroVentasVendedorTipo(String pTipo){
        try
        {
        
          DBReportes.cargaListaVentasporVendedorTipo(tableModelVentasVendedor,
                                                 VariablesReporte.vFechaInicio,
                                                 VariablesReporte.vFechaFin,
                                                 pTipo);
          
          if(tblVentasVendedor.getRowCount() > 0)
          {
            lblRegistros.setText("" + tblVentasVendedor.getRowCount());
            double T_GG = 0.0;
            double T_G  = 0.0;
            for(int i=0;i<tblVentasVendedor.getRowCount();i++){
                if(FarmaUtility.getValueFieldJTable(tblVentasVendedor,i,COL_TIPO_FILA).toString().trim().
                   equalsIgnoreCase("PORCENTAJE")){
                    T_GG = FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblVentasVendedor,i,COL_GG ).toString().trim());
                    T_G  = FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblVentasVendedor,i,COL_G ).toString().trim());
                    lbl_G.setText(""+(FarmaUtility.formatNumber((T_GG + T_G))));
                    break;
                }
            }
            FarmaUtility.ordenar(tblVentasVendedor,
                                 tableModelVentasVendedor,
              0, 
              FarmaConstants.ORDEN_DESCENDENTE);
            

            FarmaUtility.moveFocusJTable(tblVentasVendedor);
          }
          else
          {
            FarmaUtility.showMessage(this,"No se encontro resultados para la busqueda", txtFechaIni);
            lblRegistros.setText("0");
            lbl_G.setText("0");
          }
          
        }catch(SQLException sql)
        {
          sql.printStackTrace();
          FarmaUtility.showMessage(this, "Error al listar Ventas por Vendedor :\n" + sql.getMessage(),txtFechaIni);
        }
    }
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  
  private boolean validarCampos()
  {
    boolean retorno = true;
     if(!FarmaUtility.validarRangoFechas(this,txtFechaIni,txtFechaFin,false,true,true,true))
      retorno = false;
          
    return retorno;
  }

  private void txtFechaIni_keyReleased(KeyEvent e)
  {
  FarmaUtility.dateComplete(txtFechaIni,e);
  }

  private void txtFechaFin_keyReleased(KeyEvent e)
  {
  FarmaUtility.dateComplete(txtFechaFin,e);
  }

  private void txtFechaIni_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    FarmaUtility.moveFocus(txtFechaFin);
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    cerrarVentana(false);    
    chkKeyPressed(e);
  }
  private void busqueda()
  {
    if(validarCampos()){
      txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
      txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
      String FechaInicio = txtFechaIni.getText().trim();
      String FechaFin = txtFechaFin.getText().trim();
      if (FechaInicio.length() > 0 || FechaFin.length() > 0 )
      {
      char primerkeyCharFI = FechaInicio.charAt(0);
      char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length()-1);
      char primerkeyCharFF = FechaFin.charAt(0);
      char ultimokeyCharFF = FechaFin.charAt(FechaFin.length()-1);
      
        if ( !Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
             !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)){
              System.out.println("ESTOY EN buscaRegistroVentasVendedor");
              buscaRegistroVentasVendedor(FechaInicio,FechaFin);
              
        }
        else
          FarmaUtility.showMessage(this,"Ingrese un formato valido de fechas",txtFechaIni); 
      }
      else
      FarmaUtility.showMessage(this,"Ingrese datos para la busqueda",txtFechaIni);    
    }
  }
  
  private void muestraDetalleVentasVendedor()
  {
    DlgDetalleVentasPorVendedor dlgDetalleVentasPorVendedor = new DlgDetalleVentasPorVendedor(myParentFrame,"",true);
    
      //TOTALES
        if(VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_TOTALES))
        
            dlgDetalleVentasPorVendedor.setTitle("Listado de Ventas por Vendedor");
        
        //    TIPO : MEZON
        else if(VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_MEZON))
           
            dlgDetalleVentasPorVendedor.setTitle("Listado de Ventas por Vendedor Meson ");
            
        //    TIPO : delivery 
        else if(VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_DELIVERY))
            dlgDetalleVentasPorVendedor.setTitle("Listado de Ventas por Vendedor Delivery");
        
        
        else
              dlgDetalleVentasPorVendedor.setTitle("Listado de Ventas por Vendedor Institucional");
           
          
 
    dlgDetalleVentasPorVendedor.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;      
    }
  }
  void muestraVentaOrdenar()
  {
    DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame,"Ordenar",true);
    String [] IND_DESCRIP_CAMPO = {"Codigo","Vendedor","Tot.C.IGV",
                                   "Tot.C.IGV","GG","G",
                                   "Otros", "Servicios","%",
                                   "Calculado"};
    String [] IND_CAMPO = {"0","1","2","3","4","5","6","7","8","9"};
    
    VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLEVENDEDOR;
    FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(),
                                   VariablesReporte.vNombreInHashtable,
                                   IND_CAMPO,
                                   IND_DESCRIP_CAMPO,
                                   true);
    dlgOrdenar.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;
      FarmaUtility.ordenar(tblVentasVendedor,tableModelVentasVendedor,VariablesReporte.vCampo,VariablesReporte.vOrden);
      tblVentasVendedor.repaint();
    }
  }
  private void exportarDatos(){

		File lfFile = new File("C:\\"); 
		JFileChooser filechooser = new JFileChooser(lfFile);
		filechooser.setDialogTitle("Seleccione el directorio destino");
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showSaveDialog(this.myParentFrame) != JFileChooser.APPROVE_OPTION)
			return;
		File fileChoosen = filechooser.getSelectedFile();
		String ruta = fileChoosen.getAbsolutePath();

		FarmaUtility.saveFileRuta(ruta,
				ConstantsReporte.columnsListaVentasVendedor,
				tblVentasVendedor, new int[] { 15, 40, 20, 20,20,20,20,20,20,0 });

		FarmaUtility.showMessage(this, "Los datos se exportaron correctamente",
				txtFechaIni);

	}
  private void imprimir() {
        
            if (!FarmaUtility.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
                    return;
        
            //FarmaPrintService vPrint = new FarmaPrintService(45,
            FarmaPrintService vPrint = new FarmaPrintService(66,
                            FarmaVariables.vImprReporte, true);
            
            if (!vPrint.startPrintService()) {
                    FarmaUtility.showMessage(this,
                                    "No se pudo inicializar el proceso de impresión",
                                    txtFechaIni);
                    return;
            }
	    
	     
            // -- imprimir_modelo_1(vPrint);
            
            // -- imprimir_modelo_2(vPrint);
            
            //  RESPORTE ACTUAL
            imprimir_modelo_3(vPrint);
	}

    
  private void imprimir_modelo_2(FarmaPrintService vPrint)
  {
      ArrayList listaImprimir = new ArrayList();
      String fechaActual  = "";
      try
      {
         listaImprimir = DBReportes.cargaListaVV_Impr(VariablesReporte.vFechaInicio,
                                                      VariablesReporte.vFechaFin);
         fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
      }catch(SQLException e)
      {
        e.printStackTrace();
      }
      
      if(listaImprimir.size()>0)
      {

          String campoAlt = "________";

          vPrint.setStartHeader();
          vPrint.activateCondensed();
          vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) +
                           " REPORTE DE VENTAS POR VENDEDOR", true);
          vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);  
          vPrint.printBold("Fecha: " + fechaActual, true);
          vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
          vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
          vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
          vPrint.printLine("=========================================================================================",
                           true);
          vPrint.printBold("Codigo  Vendedor                              % Garantizados      Total S.IGV     Orden",
                           true);
          vPrint.printLine("=========================================================================================",
                           true);
          vPrint.deactivateCondensed();
          vPrint.setEndHeader();
          
          for (int i = 0; i < listaImprimir.size(); i++) {
                vPrint.printCondensed(
                  FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(listaImprimir,i,0),7)+" "+
                  FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(listaImprimir,i,1),33)+" "+
                  FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,i,2),16)+" "+
                  FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,i,3),19)+" "+
                  FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,i,4),6),true);                    
              }

          vPrint.activateCondensed();
          vPrint.printLine("=========================================================================================",
                           true);
          
          vPrint.printBold(
                           "Registros Impresos: "+
                           FarmaUtility.formatNumber(listaImprimir.size(),",##0")+" "+
                           FarmaPRNUtility.llenarBlancos(32)+
                           "",true);


          
          vPrint.deactivateCondensed();
          vPrint.endPrintService();
         
      }
      
    
      
      
  }
          
  private void imprimir_modelo_1(FarmaPrintService vPrint){
      
      try {

          String fechaActual = FarmaSearch
                          .getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
          String campoAlt = "________";

          vPrint.setStartHeader();
          vPrint.activateCondensed();
          vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) +
                           " REPORTE DE VENTAS POR VENDEDOR", true);
          vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);  
          vPrint.printBold("Fecha: " + fechaActual, true);
          vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
          vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
          vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
          vPrint.printLine("=========================================================================================================================================",
                           true);
          vPrint.printBold(//"Codigo  Vendedor              Total Venta   Total Bono    V.Grupo A     Venta PP      No Farma    Grupo No A       ",
                           "Codigo  Vendedor              Total C.IGV   Total S.IGV   Venta  GG     Venta G       V. Otros    V.Servicios     Porcentaje    Calculado",
                           true);
          vPrint.printLine("=========================================================================================================================================",
                           true);
          vPrint.deactivateCondensed();
          vPrint.setEndHeader();
      
          for (int i = 0; i < tblVentasVendedor.getRowCount(); i++) {
                vPrint.printCondensed(
                  FarmaPRNUtility.alinearIzquierda((String) tblVentasVendedor.getValueAt(i, COL_COD_VEND), 7)+" "+
                  FarmaPRNUtility.alinearIzquierda((String) tblVentasVendedor.getValueAt(i,COL_NOM_VEND), 20)+" "+
                  FarmaPRNUtility.alinearDerecha((String) tblVentasVendedor.getValueAt(i,COL_TOT_VTA_CIGV), 12)+" "+
                  FarmaPRNUtility.alinearDerecha((String) tblVentasVendedor.getValueAt(i,COL_TOT_VTA_SIGV), 12)+" "+
                  FarmaPRNUtility.alinearDerecha((String) tblVentasVendedor.getValueAt(i,COL_GG), 12)+" "+
                  FarmaPRNUtility.alinearDerecha((String) tblVentasVendedor.getValueAt(i,COL_G), 12)+" "+
                  FarmaPRNUtility.alinearDerecha((String) tblVentasVendedor.getValueAt(i,COL_OTROS), 13)+" "+
                  FarmaPRNUtility.alinearDerecha((String) tblVentasVendedor.getValueAt(i,COL_SERVICIOS), 13)+" "+
                  FarmaPRNUtility.alinearDerecha((String) tblVentasVendedor.getValueAt(i,COL_PORCENTAJE ), 13)+" "+
                  FarmaPRNUtility.alinearDerecha((String) tblVentasVendedor.getValueAt(i,COL_CALCULADO ), 13),true);                    
              }

          vPrint.activateCondensed();
          vPrint.printLine("=========================================================================================================================================",
                           true);
          /*vPrint.printBold("Registros Impresos: "+
                           FarmaUtility.formatNumber(tblVentasVendedor.getRowCount(),",##0"),
                           false);*/
          vPrint.printBold(
                           "Registros Impresos: "+
                           FarmaUtility.formatNumber(tblVentasVendedor.getRowCount(),",##0")+" "+
                           FarmaPRNUtility.llenarBlancos(32)+
                           "Total Garantizado: " + lbl_G.getText() +"",true);


          
          vPrint.deactivateCondensed();
          vPrint.endPrintService();
          
          FarmaUtility.showMessage(this,"..fin impresion ",null);
          
      }catch (SQLException sql)
      {
         sql.printStackTrace();
         FarmaUtility.showMessage(this,"Ocurrió un error al imprimir : \n"+sql.getMessage(),null);
      }      
  }
  
  private void this_windowClosing(WindowEvent e)
  {
  FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
  
  private void muestraGrafico(){
      VariablesReporte.vDataSet_Reporte = load_data_xy();
      if(VariablesReporte.vDataSet_Reporte!=null)
      {   
          DlgGrafico dlgGrafico = new DlgGrafico(myParentFrame,"Grafico",true);  
          dlgGrafico.setVisible(true);

          if(FarmaVariables.vAceptar)
          {
              FarmaVariables.vAceptar = false;
          }
      }    
  }
  
  private DefaultCategoryDataset load_data_xy(){
      
      DefaultCategoryDataset dataset_return = new DefaultCategoryDataset();
      for(int i=0;i<tblVentasVendedor.getRowCount();i++){
          if(FarmaUtility.getValueFieldJTable(tblVentasVendedor,i,COL_TIPO_FILA).toString().trim().
             equalsIgnoreCase("VENDEDOR")){
              
              dataset_return.setValue(FarmaUtility.getDecimalNumber(
                                            FarmaUtility.getValueFieldJTable(tblVentasVendedor,
                                                                             i,
                                                                             COL_PORCENTAJE).toString().trim()),
                               "Vendedores",
                               FarmaUtility.getValueFieldJTable(tblVentasVendedor,
                                                                i,
                                                                COL_LOGIN).toString().trim());
          }
      }
      
      return dataset_return;
  }
  
  
      
    private void imprimir_modelo_3(FarmaPrintService vPrint)
    {
        ArrayList listaImprimir = new ArrayList();
        String fechaActual  = "";
        try
        {
           listaImprimir = DBReportes.cargaListaVV_Impr(VariablesReporte.vFechaInicio,
                                                        VariablesReporte.vFechaFin);
           fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        }catch(SQLException e)
        {
          e.printStackTrace();
        }
        
        if(listaImprimir.size()>0)
        {
System.out.println("lista "+listaImprimir);
            String campoAlt = "________";

            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) +
                             " REPORTE DE VENTAS POR VENDEDOR", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);  
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
            vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("=========================================================================================================================================",
                             true);
            vPrint.printBold("Codigo  Vendedor                              % Garantizados      Total S.IGV     Orden     NumPed      Farma     No.Farma         %Part.",
                             true);
            vPrint.printLine("=========================================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            
            for (int i = 0; i < listaImprimir.size(); i++) {
                  vPrint.printCondensed(
                    FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(listaImprimir,i,0),7)+" "+
                    FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(listaImprimir,i,1),33)+" "+
                    FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,i,2),16)+" "+
                    FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,i,3),19)+" "+
                    FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,i,8),6)+ " "+
                    
                    FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,i,4),10)+" "+
                    FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,i,5),12)+" "+
                    FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,i,6),12)+ " "+
                    FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,i,7),13)
                    
                    ,true);
                }

            vPrint.activateCondensed();
            vPrint.printLine("=========================================================================================================================================",true);
            
            vPrint.printBold(
                             "Registros Impresos: "+
                             FarmaUtility.formatNumber(listaImprimir.size(),",##0")+" "+
                             FarmaPRNUtility.llenarBlancos(32)+
                             "",true);


            
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
           
        }
        
      
        
        
    }
           
}