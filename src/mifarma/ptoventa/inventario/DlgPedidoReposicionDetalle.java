package mifarma.ptoventa.inventario;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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

import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.*;

import mifarma.ptoventa.inventario.reference.*;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.*;
import javax.swing.JLabel;

public class DlgPedidoReposicionDetalle extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */

  Frame myParentFrame;
  FarmaTableModel tableModel; 
  boolean ultimoPedido = false;
  //validacion de revision de RDM
  boolean revisadoRDM = false;
  int cantReviRDM=0;
  private String CodProdTemp=" ";
  
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrListaProductos = new JScrollPane();
  private JTable tblListaProductos = new JTable();
  private JPanelTitle pnlTitle2 = new JPanelTitle();
  private JPanelWhite pnlWhite2 = new JPanelWhite();
  private JLabelWhite lblLaboratorio = new JLabelWhite();
  private JButtonLabel btnRelacionProductos = new JButtonLabel();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelWhite lblLaboratorio_T = new JLabelWhite();
  private JLabelWhite lblTransito_T = new JLabelWhite();
  //Modificado por   16.09.08
  //private JLabelWhite lblRotProm_T = new JLabelWhite();
  //private JLabelWhite lblRotacionPromedio = new JLabelWhite();
  //private JLabelWhite lblMinimoDias = new JLabelWhite();
  //private JLabelWhite lblMaximoDias = new JLabelWhite();
  private JLabelWhite lblTransito = new JLabelWhite();
  private JLabelWhite lblRotProm = new JLabelWhite();
  private JButtonLabel btnProducto = new JButtonLabel();
  private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
  private JLabelWhite lblRotacionProm_T = new JLabelWhite();
  private JLabelWhite lblMinimoDias_T = new JLabelWhite();
  private JLabelWhite lblDias = new JLabelWhite();
  private JLabelWhite lblMaximoDias_T = new JLabelWhite();
  private JLabelWhite lblPedido = new JLabelWhite();
  private JLabelWhite lblPedido_T = new JLabelWhite();
  private JLabelWhite lblFechaPedido_T = new JLabelWhite();
  private JLabelWhite lblFechaPedido = new JLabelWhite();
  private JLabelWhite lblExhibicion_T = new JLabelWhite();
  private JLabelWhite lblExhibicion = new JLabelWhite();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JLabelFunction lblF4 = new JLabelFunction();
  private JPanelTitle pnlTitle3 = new JPanelTitle();
  private JLabelWhite lbl30d_T = new JLabelWhite();
  private JLabelWhite lbl60d_T = new JLabelWhite();
  private JLabelWhite lbl90d_T = new JLabelWhite();
  private JLabelWhite lbl120d_T = new JLabelWhite();
  private JLabelWhite lblNoDias_T = new JLabelWhite();
  private JPanelWhite pnlWhite3 = new JPanelWhite();
  private JLabelWhite lbl30d = new JLabelWhite();
  private JLabelWhite lbl60d = new JLabelWhite();
  private JLabelWhite lbl90d = new JLabelWhite();
  private JLabelWhite lbl120d = new JLabelWhite();
  private JLabelWhite lblRotacion_T = new JLabelWhite();
  private JLabel lblIndTipoProd = new JLabel();
  private JLabelWhite lblRotProm_T1 = new JLabelWhite();
  private JLabelWhite lblCantAdicional = new JLabelWhite();
  /**
   * Agegado para la busqueda
   * @author  
   * @since  26.09.2007
   */
  private int COLUMN_CODIGO = 0;
  private int COLUMN_CANT_PEDREP = 3;

  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgPedidoReposicionDetalle()
  {
    this(null, "", false);
  }

  public DlgPedidoReposicionDetalle(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
      FarmaUtility.centrarVentana(this);
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
    this.setSize(new Dimension(792, 563));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Detalle Pedido de Reposición");
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
    pnlHeader1.setBounds(new Rectangle(10, 10, 770, 60));
    pnlTitle1.setBounds(new Rectangle(10, 75, 770, 20));
    scrListaProductos.setBounds(new Rectangle(10, 95, 770, 300));
        tblListaProductos.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        tblListaProductos_keyReleased(e);
                    }
                });
        pnlTitle2.setBounds(new Rectangle(10, 400, 770, 20));
    pnlWhite2.setBounds(new Rectangle(10, 420, 770, 20));
    pnlWhite2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lblLaboratorio.setText("LABORATORIO");
    lblLaboratorio.setBounds(new Rectangle(5, 5, 330, 15));
    lblLaboratorio.setForeground(Color.black);
    btnRelacionProductos.setText("Relación de Productos");
    btnRelacionProductos.setBounds(new Rectangle(10, 0, 135, 20));
    btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRelacionProductos_actionPerformed(e);
                    }
                });
        lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(680, 505, 85, 20));
    lblLaboratorio_T.setText("Laboratorio");
    lblLaboratorio_T.setBounds(new Rectangle(5, 5, 70, 15));
    lblTransito_T.setText("Tránsito");
    lblTransito_T.setBounds(new Rectangle(500, 5, 55, 15));
    lblTransito_T.setHorizontalAlignment(SwingConstants.CENTER);
    
    //Modificado por   16.09.08
    //lblRotProm_T.setText("Rot Prom");
    //lblRotProm_T.setBounds(new Rectangle(425, 5, 55, 15));
    //lblRotProm_T.setHorizontalAlignment(SwingConstants.CENTER);
    //lblRotProm.setBounds(new Rectangle(425, 5, 55, 15));
    //lblRotProm.setForeground(Color.black);
    //lblRotProm.setHorizontalAlignment(SwingConstants.CENTER);
    //lblRotacionPromedio.setBounds(new Rectangle(375, 15, 30, 15));
    //lblRotacionPromedio.setFont(new Font("SansSerif", 0, 11));
    //lblRotacionPromedio.setVisible(false);
    //lblMinimoDias.setBounds(new Rectangle(375, 35, 50, 15));
    //lblMinimoDias.setFont(new Font("SansSerif", 0, 11));
    //lblMaximoDias.setBounds(new Rectangle(375, 55, 35, 15));
    //lblMaximoDias.setFont(new Font("SansSerif", 0, 11));
    
    lblTransito.setBounds(new Rectangle(495, 5, 55, 15));
    lblTransito.setForeground(Color.black);
    lblTransito.setHorizontalAlignment(SwingConstants.CENTER);
    
    btnProducto.setText("Producto:");
    btnProducto.setBounds(new Rectangle(15, 30, 55, 15));
    btnProducto.setMnemonic('P');
    btnProducto.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnBuscar_actionPerformed(e);
        }
      });
    txtBuscar.setBounds(new Rectangle(75, 30, 180, 20));
    txtBuscar.addKeyListener(new KeyAdapter()
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
    lblRotacionProm_T.setBounds(new Rectangle(275, 15, 90, 15));
    lblMinimoDias_T.setText("Mínimo de Días:");
    lblMinimoDias_T.setBounds(new Rectangle(275, 35, 95, 15));
      lblMinimoDias_T.setVisible(false);
    lblDias.setText("dia(s)");
    lblDias.setVisible(false);
    lblRotacionProm_T.setVisible(false);
    lblDias.setBounds(new Rectangle(405, 15, 70, 15));
    lblMaximoDias_T.setText("Máximo de Días:");
    lblMaximoDias_T.setBounds(new Rectangle(275, 55, 90, 15));
      lblMaximoDias_T.setVisible(false);
    lblPedido.setBounds(new Rectangle(65, 10, 70, 15));
    lblPedido.setFont(new Font("SansSerif", 0, 11));
    lblPedido_T.setText("Pedido:");
    lblPedido_T.setBounds(new Rectangle(15, 10, 45, 15));
    lblFechaPedido_T.setText("Fecha de Pedido:");
    lblFechaPedido_T.setBounds(new Rectangle(155, 10, 100, 15));
    lblFechaPedido.setBounds(new Rectangle(255, 10, 125, 15));
    lblFechaPedido.setFont(new Font("SansSerif", 0, 11));
    lblExhibicion_T.setText("Exhibición");
    lblExhibicion_T.setBounds(new Rectangle(365, 5, 70, 15));
    lblExhibicion.setBounds(new Rectangle(360, 5, 70, 15));
    lblExhibicion.setForeground(Color.black);
    lblExhibicion.setHorizontalAlignment(SwingConstants.CENTER);
    lblF2.setText("[ F2 ] Filtrar por Laboratorio");
    lblF2.setBounds(new Rectangle(10, 505, 160, 20));
    lblF2.setVisible(true);
    lblF3.setText("[ F3 ] Quitar Filtro");
    lblF3.setBounds(new Rectangle(180, 505, 115, 20));
    lblF3.setVisible(true);
    lblF4.setText("[ F4 ] Ordenar");
    lblF4.setBounds(new Rectangle(305, 505, 115, 20));
    lblF4.setVisible(true);
    pnlTitle3.setBounds(new Rectangle(10, 450, 505, 20));
    lbl30d_T.setText("30 d.");
    lbl30d_T.setBounds(new Rectangle(425, 5, 60, 15));
    lbl30d_T.setHorizontalAlignment(SwingConstants.CENTER);
    lbl60d_T.setText("60 d.");
    lbl60d_T.setBounds(new Rectangle(330, 5, 60, 15));
    lbl60d_T.setHorizontalAlignment(SwingConstants.CENTER);
    lbl90d_T.setText("90 d.");
    lbl90d_T.setBounds(new Rectangle(230, 5, 60, 15));
    lbl90d_T.setHorizontalAlignment(SwingConstants.CENTER);
    lbl120d_T.setText("120 d.");
    lbl120d_T.setBounds(new Rectangle(135, 5, 60, 15));
    lbl120d_T.setHorizontalAlignment(SwingConstants.CENTER);
    lblNoDias_T.setText("Meses");
    lblNoDias_T.setBounds(new Rectangle(5, 5, 70, 15));
    pnlWhite3.setBounds(new Rectangle(10, 470, 505, 20));
    pnlWhite3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lbl30d.setBounds(new Rectangle(415, 5, 85, 15));
    lbl30d.setForeground(Color.black);
    lbl30d.setHorizontalAlignment(SwingConstants.CENTER);
    lbl30d.setText("1");
    lbl60d.setBounds(new Rectangle(315, 5, 90, 15));
    lbl60d.setForeground(Color.black);
    lbl60d.setHorizontalAlignment(SwingConstants.CENTER);
    lbl60d.setText("1");
    lbl90d.setBounds(new Rectangle(220, 5, 85, 15));
    lbl90d.setForeground(Color.black);
    lbl90d.setHorizontalAlignment(SwingConstants.CENTER);
    lbl90d.setText("1");
    lbl120d.setBounds(new Rectangle(120, 5, 90, 15));
    lbl120d.setForeground(Color.black);
    lbl120d.setHorizontalAlignment(SwingConstants.CENTER);
    lbl120d.setText("1");
    lblRotacion_T.setText("Venta Mensual");
    lblRotacion_T.setBounds(new Rectangle(5, 5, 90, 15));
    lblRotacion_T.setForeground(Color.black);
    lblIndTipoProd.setBounds(new Rectangle(545, 0, 140, 20));
    lblIndTipoProd.setFont(new Font("SansSerif", 1, 12));
    lblIndTipoProd.setBackground(new Color(44, 146, 24));
    lblIndTipoProd.setOpaque(true);
    lblIndTipoProd.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lblIndTipoProd.setForeground(Color.white);
    lblIndTipoProd.setHorizontalAlignment(SwingConstants.CENTER);
    lblIndTipoProd.setHorizontalTextPosition(SwingConstants.CENTER);
    lblRotProm_T1.setText("Cant Adicional");
    lblRotProm_T1.setBounds(new Rectangle(400, 5, 90, 15));
    lblRotProm_T1.setHorizontalAlignment(SwingConstants.CENTER);
      lblRotProm_T1.setVisible(false);
    lblCantAdicional.setBounds(new Rectangle(405, 5, 55, 15));
    lblCantAdicional.setForeground(Color.black);
    lblCantAdicional.setHorizontalAlignment(SwingConstants.CENTER);
      lblCantAdicional.setVisible(false);
        pnlTitle3.add(lbl30d_T, null);
        pnlTitle3.add(lbl60d_T, null);
        pnlTitle3.add(lbl90d_T, null);
        pnlTitle3.add(lbl120d_T, null);
        pnlTitle3.add(lblNoDias_T, null);
        pnlWhite3.add(lbl30d, null);
        pnlWhite3.add(lbl60d, null);
        pnlWhite3.add(lbl90d, null);
        pnlWhite3.add(lbl120d, null);
        pnlWhite3.add(lblRotacion_T, null);
        pnlWhite2.add(lblCantAdicional, null);
        pnlWhite2.add(lblExhibicion, null);
        pnlWhite2.add(lblRotProm, null);
        pnlWhite2.add(lblTransito, null);
        pnlWhite2.add(lblLaboratorio, null);
        jContentPane.add(pnlWhite3, null);
        jContentPane.add(pnlTitle3, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlWhite2, null);
        //Modificado por   16.09.08
        //pnlTitle2.add(lblRotProm_T, null);
        jContentPane.add(pnlTitle2, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle2.add(lblRotProm_T1, null);
        pnlTitle2.add(lblExhibicion_T, null);
        pnlTitle2.add(lblTransito_T, null);
        pnlTitle2.add(lblLaboratorio_T, null);
        pnlTitle1.add(lblIndTipoProd, null);
        pnlTitle1.add(btnRelacionProductos, null);
        pnlHeader1.add(lblFechaPedido, null);
        //Modificado por   16.09.08
        //pnlHeader1.add(lblMaximoDias, null);
        //pnlHeader1.add(lblMinimoDias, null);
        //pnlHeader1.add(lblRotacionPromedio, null);
        pnlHeader1.add(lblFechaPedido_T, null);
        pnlHeader1.add(lblPedido_T, null);
        pnlHeader1.add(lblPedido, null);
        pnlHeader1.add(lblMaximoDias_T, null);
    pnlHeader1.add(lblDias, null);
    pnlHeader1.add(lblMinimoDias_T, null);
    pnlHeader1.add(lblRotacionProm_T, null);
        pnlHeader1.add(txtBuscar, null);
        pnlHeader1.add(btnProducto, null);
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
    validaUltimoPedido();
    FarmaVariables.vAceptar = false;
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
    tableModel = new FarmaTableModel(ConstantsInventario.columnsListaProductosPedidoReposicionDetalle,ConstantsInventario.defaultValuesListaProductosPedidoReposicionDetalle,0);
    FarmaUtility.initSimpleList(tblListaProductos,tableModel,ConstantsInventario.columnsListaProductosPedidoReposicionDetalle);
    cargaListaProductos();
    FarmaGridUtils.moveRowSelection(tblListaProductos, 0);
    UtilityInventario.muestraIndTipoProd(13,lblIndTipoProd,tblListaProductos);
    mostrarDetalles(tblListaProductos.getSelectedRow());
    
  }
  
  /**
   * Cargamos la lista de los producto en el detalle del pedido
   * */
  private void cargaListaProductos()
  {
    try
    {
      DBInventario.cargaDetallePedidoReposicion(tableModel,lblPedido.getText());
      FarmaUtility.ordenar(tblListaProductos,tableModel,1,FarmaConstants.ORDEN_ASCENDENTE);
    }catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de productos : \n" + sql.getMessage(),txtBuscar);
    }
  }
  
  private void cargaListaProductosFiltro()
  {
    try
    {
      DBInventario.cargaDetallePedidoReposicionFiltro(tableModel,lblPedido.getText(),VariablesInventario.vCodFiltro);
      FarmaUtility.ordenar(tblListaProductos,tableModel,1,FarmaConstants.ORDEN_ASCENDENTE);
    }catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de productos : \n" + sql.getMessage(),txtBuscar);
    }
  }
  
  
  /**Cargamos la lista del detalle del pedido*/
  private void cargaListaFiltro()
  {
    if (tblListaProductos.getRowCount() > 0)
    {
      VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_LABORATORIO;
      DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
      dlgListaMaestros.setVisible(true);
      if (FarmaVariables.vAceptar)
      {
        VariablesInventario.vCodFiltro = VariablesPtoVenta.vCodMaestro;
        cargaListaProductosFiltro();
        FarmaVariables.vAceptar = false;
      }
    }
  }
  
  private void initCabecera()
  {
    lblPedido.setText(VariablesInventario.vNroPed_PedRep);
    lblFechaPedido.setText(VariablesInventario.vFecPed_PedRep);
    //Modificado por   16.09.08
    //lblRotacionPromedio.setText(VariablesInventario.vRotProm_PedRep);
    //lblMinimoDias.setText(VariablesInventario.vMinDias_PedRep);
    //lblMaximoDias.setText(VariablesInventario.vMaxDias_PedRep);
    

  }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnBuscar_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtBuscar);
  }


/**
 * Se activga la funcion si es que esta corriendo el sistema en matriz
 * */
  private void txtBuscar_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(FarmaVariables.vEconoFar_Matriz && !FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR))
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtBuscar);
      else{    
        e.consume();
        if (tblListaProductos.getSelectedRow() >= 0)
        {
          if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtBuscar.getText().trim(), 0, 1)) ) 
          {
            FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
            return;
          }
          mostrarDetalles(tblListaProductos.getSelectedRow());
          UtilityInventario.muestraIndTipoProd(13,lblIndTipoProd,tblListaProductos);
        }
      }
    }
    chkKeyPressed(e);
  }

  private void txtBuscar_keyReleased(KeyEvent e)
  {
    FarmaGridUtils.buscarDescripcion(e,tblListaProductos,txtBuscar,1);
    if(tableModel.getRowCount() > 0 && tblListaProductos.getSelectedRow() > -1){
      mostrarDetalles(tblListaProductos.getSelectedRow());
      UtilityInventario.muestraIndTipoProd(13,lblIndTipoProd,tblListaProductos);
    }
  }
  
  private void this_windowOpened(WindowEvent e)
  {
    cantReviRDM=0;
    FarmaUtility.centrarVentana(this);
    FarmaUtility.setearPrimerRegistro(tblListaProductos,txtBuscar,1);
    FarmaUtility.moveFocus(txtBuscar); 
    System.out.println(FarmaVariables.vEconoFar_Matriz);
    if(FarmaVariables.vEconoFar_Matriz)
    {
      lblF2.setVisible(true);
      lblF3.setVisible(true);
      lblF4.setVisible(true);
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
    FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,txtBuscar,1);
    
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(FarmaVariables.vEconoFar_Matriz && !FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR))
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtBuscar);    
      else
        ingresarCantidad();
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
    else if (e.getKeyCode() == KeyEvent.VK_F2)
    { 
      if(lblF2.isVisible())
        cargaListaFiltro();
    }
    else if (e.getKeyCode() == KeyEvent.VK_F3)
    {
      if(lblF3.isVisible())
        cargaListaProductos();
    }
    else if (e.getKeyCode() == KeyEvent.VK_F4)
    {
      if(lblF4.isVisible())
        muestraDetalleOrd();
    }    
    
  }
  
  private void cerrarVentana(boolean pAceptar)
	{
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
    this.dispose();
  }
  

  private void tblListaProductos_keyReleased(KeyEvent e) {
    if(tblListaProductos.getRowCount()>0){
    lbl30d_T.setText(tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),15).toString());
    lbl60d_T.setText(tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),16).toString());
    lbl90d_T.setText(tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),17).toString());
    lbl120d_T.setText(tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),18).toString());
    }
  }

  private void btnRelacionProductos_actionPerformed(ActionEvent e) {
      FarmaUtility.moveFocus(txtBuscar);
  }

  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */
  
  private void mostrarDetalles(int row)
  {
    lblLaboratorio.setText(tblListaProductos.getValueAt(row,7).toString());
    //lblExhibicion.setText(tblListaProductos.getValueAt(row,5).toString());
    lblExhibicion.setText(tblListaProductos.getValueAt(row,6).toString());
    lblTransito.setText(tblListaProductos.getValueAt(row,8).toString());
    //Modificado por   16.09.08
    //lblRotProm.setText(tblListaProductos.getValueAt(row,14).toString());
    lbl120d.setText(tblListaProductos.getValueAt(row,12).toString());
    lbl90d.setText(tblListaProductos.getValueAt(row,11).toString());
    lbl60d.setText(tblListaProductos.getValueAt(row,10).toString());
    lbl30d.setText(tblListaProductos.getValueAt(row,9).toString());
      lbl30d_T.setText(tblListaProductos.getValueAt(row,15).toString());
      lbl60d_T.setText(tblListaProductos.getValueAt(row,16).toString());
      lbl90d_T.setText(tblListaProductos.getValueAt(row,17).toString());
      lbl120d_T.setText(tblListaProductos.getValueAt(row,18).toString());
    lblCantAdicional.setText(tblListaProductos.getValueAt(row,14).toString());
    
  }

/**
 * Se podra ingresar la cantida del pedido solo si esta en Ptoventa Matriz
 * */
  private void ingresarCantidad()
  {
    if (tblListaProductos.getRowCount() > 0 && FarmaVariables.vEconoFar_Matriz)
    {
      if(ultimoPedido)
      {
        int row = tblListaProductos.getSelectedRow();
        cargarCabecera();
        
        /**
         * Valida que RDM ya haya validado el producto desde ADM 
         * @author  
         * @since 15.10.2007
         * */
        CodProdTemp=tblListaProductos.getValueAt(row,0).toString();
        validarCambioRDM(CodProdTemp);
        cantReviRDM+=1;
        
        if(revisadoRDM && cantReviRDM==1){
        System.out.println("INGRESO CANTIDAD :"+revisadoRDM);
        FarmaUtility.showMessage(this,"Este producto ya fue revisado por RDM",txtBuscar);
        }
        
        
        DlgPedidoReposicionCantidadMatriz dlgPedidoReposicionCantidadMatriz = new DlgPedidoReposicionCantidadMatriz(myParentFrame, "", true);
        dlgPedidoReposicionCantidadMatriz.setVisible(true);
        if (FarmaVariables.vAceptar)
        {
          /// 26.09.2007     AGREGADO ORDEN
          //String codigo = FarmaUtility.getValueFieldJTable(tblListaProductos,row,COLUMN_CODIGO);
          //cargaListaProductos(); 
          tblListaProductos.setValueAt(VariablesInventario.vCant_PedRep,row,COLUMN_CANT_PEDREP);
          FarmaGridUtils.moveRowSelection(tblListaProductos,row);
          mostrarDetalles(row);
          UtilityInventario.muestraIndTipoProd(13,lblIndTipoProd,tblListaProductos);
          FarmaVariables.vAceptar = false;
          /// 26.09.2007     AGREGADO ORDEN
          /*FarmaUtility.ordenar(tblListaProductos,tableModel,VariablesInventario.vCampo,VariablesInventario.vOrden);
          tblListaProductos.repaint();
          for(int a=0 ; a < tblListaProductos.getRowCount(); a++)
          {
           System.out.println("comparando" + 
           FarmaUtility.getValueFieldJTable(tblListaProductos,a,COLUMN_CODIGO) +
           " >>  " + codigo );
            if(FarmaUtility.getValueFieldJTable(tblListaProductos,a,COLUMN_CODIGO).trim()
               .equalsIgnoreCase(codigo.trim()) )
               {
                 FarmaGridUtils.moveRowSelection(tblListaProductos,a);
                 break;
               }
          }*/
          ////
        }
      }else
      {
        FarmaUtility.showMessage(this,"Este no es el último pedido. No puede modificar.",txtBuscar);
      }
    }
  }
  
  private void cargarCabecera()
  {
    int pos = tblListaProductos.getSelectedRow();
    VariablesInventario.vPos_PedRep = pos;
    /**Por cada producto selecccionado se inicializa el 0 el contador para el mensaje
     * @author  
     * @since  16.10.2007
     * */
    if(!CodProdTemp.equalsIgnoreCase(tblListaProductos.getValueAt(pos,0).toString())){
    cantReviRDM=0;
    revisadoRDM=false;
    }
    
    VariablesInventario.vCodProd_PedRep=tblListaProductos.getValueAt(pos,0).toString();
    VariablesInventario.vNomProd_PedRep=tblListaProductos.getValueAt(pos,1).toString();
    VariablesInventario.vUnidMed_PedRep=tblListaProductos.getValueAt(pos,2).toString();
    //VariablesInventario.vValFrac_PedRep=tblListaProductos.getValueAt(pos,21).toString();
    VariablesInventario.vNomLab_PedRep=tblListaProductos.getValueAt(pos,12).toString();
    VariablesInventario.vCant_PedRep=tblListaProductos.getValueAt(pos,3).toString();
    //VariablesInventario.vStkFisico_PedRep=tblListaProductos.getValueAt(pos,5).toString();
    //VariablesInventario.vCantSug_PedRep=tblListaProductos.getValueAt(pos,7).toString();
    //VariablesInventario.vCantMax_PedRep=tblListaProductos.getValueAt(pos,22).toString();
  }
  
  private void validaUltimoPedido()
  {
    try
    {
      String pedido = DBInventario.getNumUltimoPedRep();
      if(pedido.equals(VariablesInventario.vNroPed_PedRep))
        ultimoPedido = true;
    }catch(SQLException e)
    {
      if(FarmaVariables.vEconoFar_Matriz)
      {
        e.printStackTrace();
        FarmaUtility.showMessage(this,"Ha ocurrido un error al consultar el último pedido.",txtBuscar);  
      }      
    }
  }
  
  private void muestraDetalleOrd()
  {
    DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame,"Ordenar",true);
    
    VariablesInventario.vNombreInHashtable =  ConstantsInventario.vNombreInHashtablePedRep ;
    FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(),
                                   VariablesInventario.vNombreInHashtable,
                                   ConstantsInventario.vCodCampoPedRep,
                                   ConstantsInventario.vDescCampoPedRep,
                                   true);
    dlgOrdenar.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;
      FarmaUtility.ordenar(tblListaProductos,tableModel,VariablesInventario.vCampo,VariablesInventario.vOrden);
      tblListaProductos.repaint();
    }
  }

/**
 * Valida que el producto haya sido revisado por el RDM
 * @author  
 * @since 15.10.2007
 * */
 private void  validarCambioRDM(String vCodProd){
      try
      {
        String revisado = DBInventario.getRevisadoRDM(vCodProd,VariablesInventario.vNroPed_PedRep);
        if(revisado.equals("FALSE")){
        revisadoRDM = true;
        }
      }catch(SQLException e)
      {
        e.printStackTrace();
        FarmaUtility.showMessage(this,"Ha ocurrido un error al verificar la revision del RDM",txtBuscar);  
      } 
 }


}
