package mifarma.ptoventa.inventario;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import mifarma.common.*;
import mifarma.ptoventa.*;
import mifarma.ptoventa.inventario.reference.*;
import mifarma.ptoventa.reference.*;

/**
* Copyright (c) 2008 MIFARMA S.A.C.<br>
* <br>
* Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
* Nombre de la Aplicación : DlgPedidoReposicionAdicionalNuevo.java<br>
* <br>
* Histórico de Creación/Modificación<br>
* DVELIZ      10.09.08   Creación<br>
* <br>
* @author Daniel Fernando Veliz La Rosa<br>
* @version 1.0<br>
*
*/

public class DlgPedidoReposicionAdicionalNuevo extends JDialog 

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
  private JLabelWhite lblRotacion_T = new JLabelWhite();
  private JLabelWhite lblNoDias_T = new JLabelWhite();
  private JLabelWhite lblLaboratorio_T = new JLabelWhite();
  private JLabelWhite lblTransito_T = new JLabelWhite();
  private JLabelWhite lblCantUltPed = new JLabelWhite();
    private JLabelWhite lblMinExhib = new JLabelWhite();
  private JLabelWhite lblTransito = new JLabelWhite();
  private JLabelWhite lblCantUltPed_1 = new JLabelWhite();
    private JLabelWhite lbl120d = new JLabelWhite();
  private JLabelWhite lbl90d = new JLabelWhite();
  private JLabelWhite lbl60d = new JLabelWhite();
  private JLabelWhite lbl30d = new JLabelWhite();
  private JLabelWhite lbl120d_T = new JLabelWhite();
  private JLabelWhite lbl90d_T = new JLabelWhite();
  private JLabelWhite lbl60d_T = new JLabelWhite();
  private JLabelWhite lbl30d_T = new JLabelWhite();
  private JButtonLabel btnBuscar = new JButtonLabel();
  private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
  private JLabelWhite lblMaximoDias = new JLabelWhite();
  private JLabelFunction lblF1 = new JLabelFunction();
  private JButtonLabel lblFCero = new JButtonLabel();
    private JLabelFunction lblF3 = new JLabelFunction();
  
  // Constanst para las columnas de las tablas
  private int COL_ORD_LISTA = 0;
  private int COL_PROD = 1;
  private int COL_DESC_PROD = 2;
    private JTextField txtCantidad = new JTextField();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JButtonLabel lblCantidad = new JButtonLabel();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgPedidoReposicionAdicionalNuevo()
  {
    this(null, "", false);
  }

  public DlgPedidoReposicionAdicionalNuevo(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(795, 506));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Ingreso de Promedio de Venta Mensual (PVM)");
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
        
        pnlHeader1.setBounds(new Rectangle(10, 10, 770, 50));
    pnlTitle1.setBounds(new Rectangle(10, 65, 770, 20));
    scrListaProductos.setBounds(new Rectangle(10, 85, 770, 235));
    pnlTitle2.setBounds(new Rectangle(10, 325, 400, 20));
    pnlTitle3.setBounds(new Rectangle(10, 400, 400, 20));
    lblMinExhib_T.setText("Min Exhib");
    lblMinExhib_T.setBounds(new Rectangle(200, 5, 60, 15));
    lblMinExhib_T.setHorizontalAlignment(SwingConstants.CENTER);
    pnlWhite2.setBounds(new Rectangle(10, 345, 400, 20));
    pnlWhite2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lblLaboratorio.setBounds(new Rectangle(5, 5, 190, 15));
    lblLaboratorio.setForeground(Color.black);
    pnlWhite3.setBounds(new Rectangle(10, 420, 400, 20));
    pnlWhite3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    btnRelacionProductos.setText("Relación de Productos");
    btnRelacionProductos.setBounds(new Rectangle(10, 0, 135, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(515, 450, 90, 20));
        lblRotacion_T.setText("Venta Mensual");
    lblRotacion_T.setBounds(new Rectangle(5, 5, 90, 15));
    lblRotacion_T.setForeground(Color.black);
        lblNoDias_T.setText("Meses");
    lblNoDias_T.setBounds(new Rectangle(5, 5, 70, 15));
    lblLaboratorio_T.setText("Laboratorio");
    lblLaboratorio_T.setBounds(new Rectangle(5, 5, 70, 15));
    lblTransito_T.setText("Tránsito");
    lblTransito_T.setBounds(new Rectangle(260, 5, 55, 15));
    lblTransito_T.setHorizontalAlignment(SwingConstants.CENTER);
    lblCantUltPed.setText("Cant. Ult. Ped.");
    lblCantUltPed.setBounds(new Rectangle(310, 5, 90, 15));
    lblCantUltPed.setHorizontalAlignment(SwingConstants.CENTER);
        lblMinExhib.setBounds(new Rectangle(205, 5, 50, 15));
    lblMinExhib.setForeground(Color.black);
    lblMinExhib.setHorizontalAlignment(SwingConstants.CENTER);
    lblTransito.setBounds(new Rectangle(260, 5, 55, 15));
    lblTransito.setForeground(Color.black);
    lblTransito.setHorizontalAlignment(SwingConstants.CENTER);
    lblCantUltPed_1.setBounds(new Rectangle(330, 5, 55, 15));
    lblCantUltPed_1.setForeground(Color.black);
    lblCantUltPed_1.setHorizontalAlignment(SwingConstants.CENTER);
        lbl120d.setBounds(new Rectangle(160, 5, 55, 15));
    lbl120d.setForeground(Color.black);
    lbl120d.setHorizontalAlignment(SwingConstants.CENTER);
    lbl90d.setBounds(new Rectangle(215, 5, 55, 15));
    lbl90d.setForeground(Color.black);
    lbl90d.setHorizontalAlignment(SwingConstants.CENTER);
    lbl60d.setBounds(new Rectangle(280, 5, 55, 15));
    lbl60d.setForeground(Color.black);
    lbl60d.setHorizontalAlignment(SwingConstants.CENTER);
    lbl30d.setBounds(new Rectangle(340, 5, 60, 15));
    lbl30d.setForeground(Color.black);
    lbl30d.setHorizontalAlignment(SwingConstants.CENTER);
        lbl120d_T.setText("120 d.");
    lbl120d_T.setBounds(new Rectangle(155, 5, 60, 15));
    lbl120d_T.setHorizontalAlignment(SwingConstants.CENTER);
    lbl90d_T.setText("90 d.");
    lbl90d_T.setBounds(new Rectangle(215, 5, 60, 15));
    lbl90d_T.setHorizontalAlignment(SwingConstants.CENTER);
    lbl60d_T.setText("60 d.");
    lbl60d_T.setBounds(new Rectangle(275, 5, 60, 15));
    lbl60d_T.setHorizontalAlignment(SwingConstants.CENTER);
    lbl30d_T.setText("30 d.");
    lbl30d_T.setBounds(new Rectangle(340, 5, 60, 15));
    lbl30d_T.setHorizontalAlignment(SwingConstants.CENTER);
    btnBuscar.setText("Buscar");
    btnBuscar.setBounds(new Rectangle(15, 15, 55, 15));
    btnBuscar.setMnemonic('B');
    btnBuscar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnBuscar_actionPerformed(e);
        }
      });
    txtBuscar.setBounds(new Rectangle(75, 15, 375, 20));
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
        lblMaximoDias.setBounds(new Rectangle(545, 25, 35, 15));
    lblMaximoDias.setFont(new Font("SansSerif", 0, 11));
    lblF1.setText("[ Enter ] Ingresar Cantidad");
    lblF1.setBounds(new Rectangle(10, 450, 175, 20));
    
    lblF3.setText("[F3] Ver Histórico");
    lblF3.setBounds(new Rectangle(205, 450, 130, 20));

        txtCantidad.setBounds(new Rectangle(430, 415, 105, 25));
        txtCantidad.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtCantidad_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtCantidad_keyTyped(e);
                    }
                });
      txtCantidad.setVisible(false);
        jButtonLabel1.setBounds(new Rectangle(15, 370, 390, 25));
        jButtonLabel1.setForeground(Color.black);
        jButtonLabel1.setText("Su local ha vendido los últimos meses:");
        jButtonLabel1.setFont(new Font("SansSerif", 1, 13));
        lblCantidad.setText("Cuanto estima vender en promedio en un mes?  ");
        lblCantidad.setBounds(new Rectangle(430, 395, 325, 15));
        lblCantidad.setForeground(Color.black);
        lblCantidad.setVisible(false);
        lblCantidad.setFont(new Font("SansSerif", 1, 13));
        jLabelFunction1.setBounds(new Rectangle(385, 450, 105, 20));
        jLabelFunction1.setText("[ F4 ] Ordenar");
        lblFCero.setText("- FALTA CERO");
    lblFCero.setVisible(false);
    lblFCero.setBounds(new Rectangle(135, 0, 80, 20));
        pnlWhite2.add(lblCantUltPed_1, null);
        pnlWhite2.add(lblTransito, null);
        pnlWhite2.add(lblMinExhib, null);
        pnlWhite2.add(lblLaboratorio, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(jButtonLabel1, null);
        jContentPane.add(txtCantidad, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlWhite3, null);
        jContentPane.add(pnlWhite2, null);
        jContentPane.add(pnlTitle3, null);
        jContentPane.add(pnlTitle2, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlHeader1, null);
        jContentPane.add(lblCantidad, null);
        pnlWhite3.add(lbl30d, null);
        pnlWhite3.add(lbl60d, null);
        pnlWhite3.add(lbl90d, null);
        pnlWhite3.add(lbl120d, null);
        pnlWhite3.add(lblRotacion_T, null);
        pnlTitle3.add(lbl30d_T, null);
        pnlTitle3.add(lbl60d_T, null);
        pnlTitle3.add(lbl90d_T, null);
        pnlTitle3.add(lbl120d_T, null);
        pnlTitle3.add(lblNoDias_T, null);
        pnlTitle2.add(lblCantUltPed, null);
        pnlTitle2.add(lblTransito_T, null);
        pnlTitle2.add(lblLaboratorio_T, null);
        pnlTitle2.add(lblMinExhib_T, null);
        pnlTitle1.add(lblFCero, null);
        pnlTitle1.add(btnRelacionProductos, null);
        pnlHeader1.add(lblMaximoDias, null);
        pnlHeader1.add(txtBuscar, null);
        pnlHeader1.add(btnBuscar, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
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
    tableModel = 
    new FarmaTableModel(
    ConstantsInventario.columnsListaProductosPedidoReposicionAdicional,
    ConstantsInventario.defaultListaProductosPedidoReposicionAdicional,0);
    
    FarmaUtility.initSimpleList(tblListaProductos,
            tableModel,
            ConstantsInventario.columnsListaProductosPedidoReposicionAdicional);
    cargaListaProductos();
    mostrarDetalles(tblListaProductos.getSelectedRow());
      cargarMeses();
  }
  
  private void cargaListaProductos()
  {
    try
    {
      DBInventario.cargaListaProductosPedidoAdicional(tableModel);
      FarmaUtility.ordenar(tblListaProductos,tableModel,2,FarmaConstants.ORDEN_ASCENDENTE);
    }catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de productos : \n" + sql.getMessage(),txtBuscar);
    }
  }
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void btnBuscar_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtBuscar);
    setIngresaCantidad(false);
  }

  private void txtBuscar_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      if (tblListaProductos.getSelectedRow() >= 0)
      {
        if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtBuscar.getText().trim(), 0, 2)) ) 
        {
          FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
          return;
        }
        mostrarDetalles(tblListaProductos.getSelectedRow());
      }
    }
    
    chkKeyPressed(e);
  }
  
  private void txtBuscar_keyReleased(KeyEvent e)
  {
    FarmaGridUtils.buscarDescripcion(e,tblListaProductos,txtBuscar,2);
    if(tableModel.getRowCount() > 0 && tblListaProductos.getSelectedRow() > -1){
      mostrarDetalles(tblListaProductos.getSelectedRow());
    }
  }
  
  private void this_windowOpened(WindowEvent e) {
    FarmaUtility.moveFocus(txtBuscar);
    FarmaUtility.centrarVentana(this);
    
    }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, 
            "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,txtBuscar,2);
    
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      setIngresaCantidad(true);
      FarmaUtility.moveFocus(txtCantidad);      
      //ingresarCantidad();
    }else if(e.getKeyCode() == KeyEvent.VK_F3)
    {
      cargarCabecera();
      cargarHistorialProducto();
    }//
    else if(e.getKeyCode() == KeyEvent.VK_F4)
    {
          muestraDetalleOrd();
          
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

  private void mostrarDetalles(int row)
  {
    //String pCodProd = tblListaProductos.getValueAt(row,0).toString();
    String pCodProd = tableModel.getValueAt(row,0).toString();      
    //ArrayList lista = new ArrayList();
    String  transito = "0";
    try 
    {
      //ArrayList mylist = new ArrayList();
      transito = DBInventario.getTransito(pCodProd).trim();
        //System.out.println("transito -- pCodProd :"+ pCodProd+ "-"+transito);
        /*if(mylist.size()==0){
            lblTransito.setText("0");
            lblCantUltPed_1.setText("0");
            lblMinExhib.setText("0");
            lblLaboratorio.setText(tblListaProductos.getValueAt(row,8).toString().trim());
            lbl30d.setText("0.000");
            lbl60d.setText("0.000");
            lbl90d.setText("0.000");
            lbl120d.setText("0.000");
        }else{
            lista = (ArrayList)mylist.get(0);
            //lblMinExhib.setText(lista.get(0).toString());
            lblTransito.setText(lista.get(1).toString());
            /*lblCantUltPed_1.setText(lista.get(2).toString());
            lblLaboratorio.setText(tblListaProductos.getValueAt(row,8).toString().trim());
            lbl30d.setText(lista.get(3).toString());
            lbl60d.setText(lista.get(4).toString());
            lbl90d.setText(lista.get(5).toString());
            lbl120d.setText(lista.get(6).toString());            
        }*/
        //transito
        lblTransito.setText(transito.trim());
    } catch (SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this, "Error al obtener la cantidad del producto",txtBuscar);
    }
         /*lblMinExhib.setText(tblListaProductos.getValueAt(row,11).toString().trim());
         //lblTransito.setText(tblListaProductos.getValueAt(row,12).toString().trim());
         lblCantUltPed_1.setText(tblListaProductos.getValueAt(row,13).toString().trim());
         lblLaboratorio.setText(tblListaProductos.getValueAt(row,8).toString().trim());
         lbl30d.setText(tblListaProductos.getValueAt(row,14).toString().trim());
         lbl60d.setText(tblListaProductos.getValueAt(row,15).toString().trim());
         lbl90d.setText(tblListaProductos.getValueAt(row,16).toString().trim());
         lbl120d.setText(tblListaProductos.getValueAt(row,17).toString().trim());*/
    
         lblMinExhib.setText(tableModel.getValueAt(row,11).toString().trim());
         //lblTransito.setText(tblListaProductos.getValueAt(row,12).toString().trim());
         lblCantUltPed_1.setText(tableModel.getValueAt(row,13).toString().trim());
         lblLaboratorio.setText(tableModel.getValueAt(row,8).toString().trim());
         lbl30d.setText(tableModel.getValueAt(row,14).toString().trim());
         lbl60d.setText(tableModel.getValueAt(row,15).toString().trim());
         lbl90d.setText(tableModel.getValueAt(row,16).toString().trim());
         lbl120d.setText(tableModel.getValueAt(row,17).toString().trim());
         
  }
  
  
  /*private void ingresarCantidad()
  {
    
    cargarCabecera();
    DlgIngresoCantidadPedidoAdicional dlgPedidoReposicionIngresoCantidad = 
       new DlgIngresoCantidadPedidoAdicional(myParentFrame,"",true);
    dlgPedidoReposicionIngresoCantidad.setVisible(true);   
    if(FarmaVariables.vAceptar)
    {
      cargarCantidadIngresada();
      FarmaVariables.vAceptar=false;
      tblListaProductos.setRowSelectionInterval(VariablesInventario.vPos_PedRep,VariablesInventario.vPos_PedRep);
    }
  }*/
  
  private void cargarCabecera()
  {
    try
    {
      VariablesInventario.vFechaHora_PedRep = FarmaSearch.getFechaHoraBD(2);  
    }catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al obtener la fecha del sistema : \n" + sql.getMessage(),txtBuscar);
  
    }
    int pos = tblListaProductos.getSelectedRow();
    VariablesInventario.vPos_PedRep = pos;
    /*
    VariablesInventario.vCodProd_PedRep=tblListaProductos.getValueAt(pos,0).toString();
    VariablesInventario.vNomProd_PedRep=tblListaProductos.getValueAt(pos,2).toString();
    VariablesInventario.vUnidMed_PedRep=tblListaProductos.getValueAt(pos,3).toString();
    VariablesInventario.vNomLab_PedRep=tblListaProductos.getValueAt(pos,8).toString();
    VariablesInventario.vStkFisico_PedRep=tblListaProductos.getValueAt(pos,9).toString();
    VariablesInventario.vValFrac_PedRep=tblListaProductos.getValueAt(pos,10).toString();
*/
    VariablesInventario.vCodProd_PedRep = tableModel.getValueAt(pos,0).toString();
    VariablesInventario.vNomProd_PedRep = tableModel.getValueAt(pos,2).toString();
    VariablesInventario.vUnidMed_PedRep = tableModel.getValueAt(pos,3).toString();
    VariablesInventario.vNomLab_PedRep  = tableModel.getValueAt(pos,8).toString();
    VariablesInventario.vStkFisico_PedRep = tableModel.getValueAt(pos,9).toString();
    VariablesInventario.vValFrac_PedRep = tableModel.getValueAt(pos,10).toString();
    
      if(FarmaVariables.vEconoFar_Matriz){
          VariablesInventario.vTituloIngresoCantidadAdicional = " Autorizada ";
      }else{
          VariablesInventario.vTituloIngresoCantidadAdicional = " Solicitada "; 
      }
  }
  
  private void cargarCantidadIngresada()
  {
    int pos = VariablesInventario.vPos_PedRep;
    if(FarmaVariables.vEconoFar_Matriz){
        tblListaProductos.setValueAt(VariablesInventario.vCantAdicional,pos,7/*6*//*8*/);
        tblListaProductos.repaint(); 
    }else{
        //String cantAnterior = FarmaUtility.getValueFieldJTable(tblListaProductos,pos,6);
        int lold =  8;//cantAnterior.length();
        int lnew =  VariablesInventario.vCantAdicional.length();
        String aux = "";
        for(int i=0;i<(lold-lnew);i++)
            aux += " ";
        
        tblListaProductos.setValueAt(aux+VariablesInventario.vCantAdicional,pos,6/*6*//*8*/);
        tblListaProductos.repaint();  
    }
  }
  
  private void cargaListaProductos_filtro()
  {
    try
    {
      tableModel.clearTable();
      DlgProcesaProdReposicion dlg = new DlgProcesaProdReposicion(myParentFrame,"",true);
      dlg.setVariables(tableModel,VariablesPtoVenta.vTipoFiltro,VariablesPtoVenta.vCodFiltro);
      dlg.setVisible(true);
      
      FarmaUtility.ordenar(tblListaProductos,tableModel,1,FarmaConstants.ORDEN_ASCENDENTE);
      if(tblListaProductos.getRowCount()==0)
        mostrarDetallesBlanco();
      else{
        mostrarDetalles(0);
      }
      
    }catch(Exception sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de productos : \n" + sql.getMessage(),txtBuscar);
    }   
  }
  
  private void mostrarDetallesBlanco()
  {
    //
    lblLaboratorio.setText("");
    lblMinExhib.setText("");
    lblTransito.setText("");
    lblCantUltPed_1.setText("");
    lbl30d.setText("");
    lbl60d.setText("");
    lbl90d.setText("");
    lbl120d.setText("");
  }

    private void cargarHistorialProducto() {
        int pos = tblListaProductos.getSelectedRow();
        /*VariablesInventario.vCodProdHist=tblListaProductos.getValueAt(pos,0).toString();
        VariablesInventario.vDesProdHist=tblListaProductos.getValueAt(pos,2).toString();
        */

        VariablesInventario.vCodProdHist=tableModel.getValueAt(pos,0).toString();
        VariablesInventario.vDesProdHist=tableModel.getValueAt(pos,2).toString();
        
        DlgResumenPedidoReposicionAdicional DlgHistorial =
            new DlgResumenPedidoReposicionAdicional(myParentFrame, "", true);
        DlgHistorial.setVisible(true);    
    }
    
    private void cargarMeses(){
        ArrayList lista = new ArrayList();
        ArrayList myLista = new ArrayList();
            try {
                DBInventario.cargarMeses(lista);
            } catch (SQLException f) {
                f.printStackTrace();
            }
        myLista = (ArrayList)lista.get(0);
        lbl30d_T.setText(myLista.get(0).toString());
        lbl60d_T.setText(myLista.get(1).toString());
        lbl90d_T.setText(myLista.get(2).toString());
        lbl120d_T.setText(myLista.get(3).toString());
    }


    private void txtCantidad_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            ingresarCantidad();
        }
        else if(e.getKeyCode() == KeyEvent.VK_F3)
        {
          cargarCabecera();
          cargarHistorialProducto();
          FarmaUtility.moveFocus(txtBuscar);
        }//
        else if(e.getKeyCode() == KeyEvent.VK_F4)
        {
              muestraDetalleOrd();
            FarmaUtility.moveFocus(txtBuscar);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
          cerrarVentana(false);
            FarmaUtility.moveFocus(txtBuscar);
        }        
    }
    
    private void ingresarCantidad()
    {
      cargarCabecera();  
      String vCantidad = txtCantidad.getText();
    
      if(vCantidad.trim().length()<=0){
          setIngresaCantidad(false);
          FarmaUtility.moveFocus(txtBuscar);
          return;
      }
      
      
        if(vCantidad.trim().length()>6){
            FarmaUtility.showMessage(this,"Debe ingresar una cantidad válida.",txtCantidad);
            return;
        }
      
     if(!FarmaUtility.isInteger(vCantidad))
        FarmaUtility.showMessage(this,"Debe ingresar una cantidad válida.",txtCantidad);
     else
       /*if(FarmaUtility.getDecimalNumber(vCantidad) <= FarmaUtility.getDecimalNumber(VariablesInventario.vCantMax_PedRep) 
          && !vCantidad.equalsIgnoreCase("0") && UtilityInventario.validaCerosIzquierda(vCantidad))
        { */
        
       if(UtilityInventario.validaCerosIzquierda(vCantidad))
       { 
          FarmaUtility.showMessage(this,"La cantidad adicional tiene que ser entera",txtCantidad);
       }
       else{
            if(FarmaUtility.rptaConfirmDialog(this,"¿Está seguro que desea realizar la operación?"))
            {
              VariablesInventario.vCantAdicional = txtCantidad.getText();
              try
              {
                if(FarmaVariables.vEconoFar_Matriz){
                    return;
                }else{
                    System.out.println("Actualizando desde Local");
                    UtilityInventario.guardarCantidadPedidoAdicionalLocal(
                                      VariablesInventario.vCodProd_PedRep,
                                      VariablesInventario.vCantAdicional,
                                      "0",
                                      "N");
                    FarmaUtility.aceptarTransaccion();
                    //FarmaUtility.showMessage(this,"Se realizó la operación satisfactoriamente.",txtCantidad);
                }
              }
              catch(SQLException sql)
              {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,"Error al guardar la cantidad adicional: "+sql,txtCantidad);
              }
              //cerrarVentana(true);   
                cargarCantidadIngresada();
                
                tblListaProductos.setRowSelectionInterval(VariablesInventario.vPos_PedRep,VariablesInventario.vPos_PedRep);
                FarmaUtility.moveFocus(txtBuscar);
                txtCantidad.setText("");
                setIngresaCantidad(false);
            }
            else
            {
              FarmaUtility.showMessage(this,"Se canceló la operación.",txtBuscar);
                txtCantidad.setText("");
                setIngresaCantidad(false);
            }
      }
        
    }    

    private void txtCantidad_keyTyped(KeyEvent e) {
        
          FarmaUtility.admitirDigitos(txtCantidad,e);
        
    }
    
    private void muestraDetalleOrd()
    {
      DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame,"Ordenar",true);
      
      VariablesInventario.vNombreInHashtable =  ConstantsInventario.vNombreInHashtablePedPVM ;
      FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(),
                                     VariablesInventario.vNombreInHashtable,
                                     ConstantsInventario.vCodCampoPedPVM,
                                     ConstantsInventario.vDescCampoPedPVM,
                                     true);
      dlgOrdenar.setVisible(true);
      if(FarmaVariables.vAceptar)
      {
        FarmaVariables.vAceptar = false;
        FarmaUtility.ordenar(tblListaProductos,tableModel,VariablesInventario.vCampo,VariablesInventario.vOrden);
        tblListaProductos.repaint();
      }
    }
    
    public void setIngresaCantidad(boolean pValor){
        lblCantidad.setVisible(pValor);
        txtCantidad.setVisible(pValor);
        txtCantidad.setText("");
    }
}

