package mifarma.ptoventa;

import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;

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

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.DlgIngresarLote;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaMaestros.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      26.01.2006   Creación<br>
 *        13.04.2010   Modificación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgListaMaestros extends JDialog 
{
    
    public String ind="N";
    public String codprodx="";
    public String indtrans="N";
  private Frame myParentFrame;
  private FarmaTableModel tableModelListaMaestro;
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JPanel pnlRelacionFiltros = new JPanel();
  private JPanel pnlIngresarProductos = new JPanel();
  private JTextField txtDescripcion = new JTextField();
  private JButton btnDescripcion = new JButton();
  private JTable tblMaestro = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JButtonLabel btnRelacionMaestros = new JButtonLabel();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JLabelFunction btnAgre = new JLabelFunction();
    private JLabelFunction btnerase = new JLabelFunction();

    // **************************************************************************
// Constructores
// **************************************************************************
  public DlgListaMaestros()
  {
    this(null, "", false);
  }

  public DlgListaMaestros(Frame parent, String title, boolean modal)
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

// **************************************************************************
// Método "jbInit()"
// **************************************************************************
  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(447, 369));
    //this.setSize(new Dimension(447, 366));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Lista de ");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    jContentPane.setBackground(Color.white);
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(623, 335));
    jContentPane.setForeground(Color.white);
    lblEnter.setText("[ ENTER ] Seleccionar");
    //lblEnter.setBounds(new Rectangle(200, 310, 130, 20));
    lblEnter.setBounds(new Rectangle(215, 310, 130, 20));
    jScrollPane1.setBounds(new Rectangle(15, 80, 405, 220));
    jScrollPane1.setBackground(new Color(255, 130, 14));
    pnlRelacionFiltros.setBounds(new Rectangle(15, 60, 405, 20));
    pnlRelacionFiltros.setBackground(new Color(255, 130, 14));
    pnlRelacionFiltros.setLayout(null);
    pnlIngresarProductos.setBounds(new Rectangle(15, 10, 405, 40));
    pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 14), 1));
    pnlIngresarProductos.setBackground(Color.white);
    pnlIngresarProductos.setLayout(null);
    pnlIngresarProductos.setForeground(Color.orange);
    txtDescripcion.setBounds(new Rectangle(105, 10, 240, 20));
    txtDescripcion.setFont(new Font("SansSerif", 1, 11));
    txtDescripcion.setForeground(new Color(32, 105, 29));
    txtDescripcion.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        txtDescripcion_keyPressed(e);
                    }

        public void keyReleased(KeyEvent e)
        {
                        txtDescripcion_keyReleased(e);
                    }
                });
    btnDescripcion.setText("Descripcion :");
    btnDescripcion.setBounds(new Rectangle(15, 10, 85, 20));
    btnDescripcion.setMnemonic('d');
    btnDescripcion.setFont(new Font("SansSerif", 1, 11));
    btnDescripcion.setDefaultCapable(false);
    btnDescripcion.setRequestFocusEnabled(false);
    btnDescripcion.setBackground(new Color(50, 162, 65));
    btnDescripcion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnDescripcion.setFocusPainted(false);
    btnDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
    btnDescripcion.setContentAreaFilled(false);
    btnDescripcion.setBorderPainted(false);
    btnDescripcion.setForeground(new Color(255, 140, 14));
    btnDescripcion.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDescripcion_actionPerformed(e);
        }
      });
    tblMaestro.setFont(new Font("SansSerif", 0, 12));
    tblMaestro.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblMaestro_keyPressed(e);
        }
      });
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(350, 310, 85, 20));
    //lblEsc.setBounds(new Rectangle(335, 310, 85, 20));
    btnRelacionMaestros.setText("Relacion de Maestros");
    btnRelacionMaestros.setBounds(new Rectangle(10, 0, 150, 20));
    btnRelacionMaestros.setMnemonic('r');
        jButtonLabel1.setText("jButtonLabel1");
        btnAgre.setBounds(new Rectangle(0, 310, 105, 20));
        btnAgre.setText("[ F1 ] Agregar lote");
        btnerase.setBounds(new Rectangle(110, 310, 95, 20));
        btnerase.setText("[ F5 ] Borrar lote");
        jScrollPane1.getViewport();
        pnlIngresarProductos.add(txtDescripcion, null);
    pnlIngresarProductos.add(btnDescripcion, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(btnerase, null);
        jContentPane.add(btnAgre, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        jScrollPane1.getViewport().add(tblMaestro, null);
    jContentPane.add(jScrollPane1, null);
    pnlRelacionFiltros.add(btnRelacionMaestros, null);
    jContentPane.add(pnlRelacionFiltros, null);
    jContentPane.add(pnlIngresarProductos, null);
        //this.getContentPane().add(jContentPane, null);
  }
  
// **************************************************************************
// Método "initialize()"
// **************************************************************************
  private void initialize()
  {
    initTable();    
  };

// **************************************************************************
// Métodos de inicialización
// **************************************************************************
  private void initTable()
  {
  
  if(VariablesPtoVenta.vTipListaMaestros.equals(ConstantsPtoVenta.TIP_LIST_MAESTRO_ORD))
   {
    tableModelListaMaestro = new FarmaTableModel(ConstantsPtoVenta.columnsListaMaestros,ConstantsPtoVenta.defaultValuesListaMaestros,0);
    FarmaUtility.initSimpleList(tblMaestro,tableModelListaMaestro,ConstantsPtoVenta.columnsListaMaestros);
   }
  else if(VariablesPtoVenta.vTipListaMaestros.equals(ConstantsPtoVenta.TIP_LIST_MAESTRO_TRANSF))
   {
    tableModelListaMaestro = new FarmaTableModel(ConstantsPtoVenta.columnsListaMaestrosTransf,ConstantsPtoVenta.defaultValuesListaMaestros,0);
    FarmaUtility.initSimpleList(tblMaestro,tableModelListaMaestro,ConstantsPtoVenta.columnsListaMaestrosTransf);
   }
     
    cargaListaMaestros();
  }
  
// **************************************************************************
// Metodos de eventos
// **************************************************************************
  private void tblMaestro_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  
  private void this_windowOpened(WindowEvent e)
  {
      btnAgre.setVisible(false);//INI  , 13.04.2010
      btnerase.setVisible(false);// , 15.04.2010
    if(ind.equalsIgnoreCase("S") && indtrans.equalsIgnoreCase("S")){ 
        btnAgre.setVisible(true);
        btnerase.setVisible(true);// , 15.04.2010
        /*this.setSize(new Dimension(447, 393));
        jContentPane.setLayout(borderLayout1);
        jContentPane.setSize(new Dimension(623, 393));
        
        lblEnter.setBounds(new Rectangle(290, 310, 130, 20));
        lblEsc.setBounds(new Rectangle(335, 340, 85, 20));
        btnAgre.setBounds(new Rectangle(70, 310, 105, 20));
        btnerase.setBounds(new Rectangle(185, 310, 95, 20));*/
    }
      //FIN  , 13.04.2010
    this.setTitle(VariablesPtoVenta.vTituloListaMaestros);
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtDescripcion);  
  }
  
  private void txtDescripcion_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e, tblMaestro, txtDescripcion, 0);
    //FarmaGridUtils.buscarCodigo_KeyPressed(e, this, tblMaestro, txtDescripcion, 0);
    /*if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      if (tblMaestro.getSelectedRow() >= 0)
      {
        if (!(FarmaUtility.findTextInJTable(tblMaestro, txtDescripcion.getText().trim(), 0, 1)) ) 
        {
          FarmaUtility.showMessage(this,"Maestro No Encontrado según Criterio de Búsqueda !!!", txtDescripcion);
          return;
        }
      }
    }*/
    chkKeyPressed(e);
  }

  private void txtDescripcion_keyReleased(KeyEvent e)
  {
      if(ind.equalsIgnoreCase("S") && indtrans.equalsIgnoreCase("S")){
          FarmaGridUtils.buscarDescripcion(e, tblMaestro, txtDescripcion, 0);
      }
  }

  private void btnDescripcion_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtDescripcion);
  }
  
// **************************************************************************
// Metodos auxiliares de eventos
// **************************************************************************
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
    else if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      guardaValoresMaestro();
      cerrarVentana(true);
    }else if(e.getKeyCode() == KeyEvent.VK_F1 && ind.equalsIgnoreCase("S") && indtrans.equalsIgnoreCase("S")){ //INI -  , 13.04.2010
                DlgIngresarLote objx=new DlgIngresarLote(myParentFrame,"",true);
                objx.codprodxx=codprodx;
                objx.tableModelListaMaestroX=tableModelListaMaestro;
                objx.setVisible(true);
                if(FarmaVariables.vAceptar){
                    VariablesPtoVenta.vCodMaestro = VariablesInventario.nroLoteX;
                    VariablesPtoVenta.vDescMaestro = VariablesInventario.fechaVencLoteX;                    
                    VariablesInventario.fechaVencLoteX="";
                    VariablesInventario.nroLoteX="";
                    cerrarVentana(true);
                }
                VariablesInventario.fechaVencLoteX="";
                VariablesInventario.nroLoteX="";
    }//FIN -  , 14.04.2010
    else if(e.getKeyCode() == KeyEvent.VK_F5 && ind.equalsIgnoreCase("S") && indtrans.equalsIgnoreCase("S")){ //INI -  , 15.04.2010
        String codlote=FarmaUtility.getValueFieldArrayList(tableModelListaMaestro.data,tblMaestro.getSelectedRow(),0).trim();
        String prodcod=codprodx;
        try{            
            String msg=DBInventario.eliminarLote(prodcod,
                                      codlote);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this,msg,txtDescripcion);
            cargaListaMaestros();
        }catch(SQLException f){
            FarmaUtility.liberarTransaccion();
            /*if(f.getErrorCode()==-20001){
                FarmaUtility.showMessage(this,"aaa",null);
                FarmaUtility.showMessage(this,"Usted no a agregado dicho lote en este local para este producto",txtDescripcion);
            }else if(f.getErrorCode()==-20002){
                FarmaUtility.showMessage(this,"No puede elimnar un lote luego de x dias despues de ingresado: "+f.getMessage(),txtDescripcion);
            }else{*/
                f.printStackTrace();
                FarmaUtility.showMessage(this,"ERROR al eliminar lote: "+f.getMessage(),txtDescripcion);
            //}
        }
    }//FIN -  , 15.04.2010
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

  private void cargaListaMaestros()
  {
    String tipoMaestro = VariablesPtoVenta.vTipoMaestro;
    try
    {
      DBPtoVenta.cargaListaMaestros(tableModelListaMaestro,tipoMaestro);
      //agregando la primera linea en blanco
      ArrayList myArray = new ArrayList();
      myArray.add("");
      myArray.add("");
      tableModelListaMaestro.insertRow(myArray);
      FarmaUtility.ordenar(tblMaestro, tableModelListaMaestro, 1, FarmaConstants.ORDEN_ASCENDENTE);
    } catch(SQLException e)
    {
      System.out.println("error al cargar lista de maestros : " + e);
    }
  }
  
  private void guardaValoresMaestro()
  {
    VariablesPtoVenta.vCodMaestro = ((String)tblMaestro.getValueAt(tblMaestro.getSelectedRow(),0)).trim();
    VariablesPtoVenta.vDescMaestro = ((String)tblMaestro.getValueAt(tblMaestro.getSelectedRow(),1)).trim();
    //VariablesPtoVenta.vCodMaestro = ((String)tblMaestro.getValueAt(1,0)).trim();
    //VariablesPtoVenta.vDescMaestro = ((String)tblMaestro.getValueAt(1,1)).trim();
    System.out.println("*/**/*/*/*/*/*/*");
    System.out.println(tblMaestro.getSelectedRow());
    System.out.println(VariablesPtoVenta.vCodMaestro);
    System.out.println(VariablesPtoVenta.vDescMaestro);
  }

}
