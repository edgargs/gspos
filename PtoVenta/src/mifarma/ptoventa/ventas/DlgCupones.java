package mifarma.ptoventa.ventas;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgCupones.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      02.07.2008   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgCupones
  extends JDialog{
/* ************************************************************************** */
/*                           DECLARACION PROPIEDADES                          */
/* ************************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgCupones.class);
  
  Frame myParentFrame;
  FarmaTableModel tableModel;
  
  private final int COL_COD = 0;
  private final int COL_DESC = 1;
  private final int COL_LAB = 3;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JScrollPane scrPane1 = new JScrollPane();
  private JTable tblLista = new JTable();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JButtonLabel btnLista = new JButtonLabel();
  private JLabelFunction F11 = new JLabelFunction();

/* ************************************************************************** */
/*                              CONSTRUCTORES                                 */
/* ************************************************************************** */

  public DlgCupones(){
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      log.error("",e);
    }

  }

  public DlgCupones(Frame parent, String title, boolean modal){
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

/* ************************************************************************** */
/*                              METODO jbInit                                 */
/* ************************************************************************** */

  private void jbInit()
    throws Exception
  {
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(720, 250));
    this.setDefaultCloseOperation(0);
    this.setTitle("Cupones NO USADOS");
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
    lblEsc.setBounds(new Rectangle(5, 190, 220, 20));
    lblEsc.setText("[ESC] Para regresar al pedido ");
    scrPane1.setBounds(new Rectangle(5, 30, 700, 150));
    tblLista.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblLista_keyPressed(e);
        }
      });
    pnlTitle1.setBounds(new Rectangle(5, 10, 700, 20));
    btnLista.setText("Cupones no utilizados");
    btnLista.setBounds(new Rectangle(5, 0, 135, 20));
    F11.setBounds(new Rectangle(485, 190, 220, 20));
    F11.setText("[F11] Para continuar con la venta ");
    scrPane1.getViewport().add(tblLista, null);
    pnlTitle1.add(btnLista, null);
    jContentPane.add(F11, null);
    jContentPane.add(pnlTitle1, null);
    jContentPane.add(scrPane1, null);
    jContentPane.add(lblEsc, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }

/* ************************************************************************** */
/*                            METODO initialize                               */
/* ************************************************************************** */

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
    initTableLista();
  }

/* ************************************************************************** */
/*                          METODOS INICIALIZACION                            */
/* ************************************************************************** */

  private void initTableLista()
  {
    tableModel = new FarmaTableModel(ConstantsVentas.columnsListaCupones,ConstantsVentas.defaultValuesListaCupones,0);
    FarmaUtility.initSimpleList(tblLista,tableModel,ConstantsVentas.columnsListaCupones);
    muestraListaCupones();
  }
  
  private void muestraListaCupones()
  {
    // borramos todo de la tabla
    while(tableModel.getRowCount()>0){
      tableModel.deleteRow(0);
    }
    String cadena,descamp,cupon;
    Map mapaCupon = new HashMap();
    ArrayList aux=new ArrayList();
    log.debug("VariablesVentas.vList_CuponesNoUsados:"+VariablesVentas.vList_CuponesNoUsados);
    for(int i=0; i<VariablesVentas.vList_CuponesNoUsados.size(); i++) {
    	mapaCupon = (Map)VariablesVentas.vList_CuponesNoUsados.get(i); 
    	cupon = mapaCupon.get("COD_CUPON").toString();//((String)((ArrayList)VariablesVentas.vArrayList_Cupones.get(i)).get(0)).trim();
    	//codcamp = mapaCupon.get("COD_CAMP_CUPON").toString(); //((String)((ArrayList)VariablesVentas.vArrayList_Cupones.get(i)).get(1)).trim();
    	descamp= mapaCupon.get("DESC_CUPON").toString();//((String)((ArrayList)auxcamp.get(0)).get(1)).trim();
        cadena="El cupon  "+cupon +"  de la campaña  "+descamp+"  no ha sido usado.";
        aux.add(cadena);        
        tableModel.insertRow(aux);
    }
    tableModel.fireTableDataChanged();
    tblLista.repaint();    
  }
  
  private void obtieneInfoCamp(ArrayList auxcamp,String codcamp){
   try{
     DBVentas.obtieneInfoCamp(auxcamp,codcamp);
   }catch(SQLException e)
   {
     log.error("",e);
   }
  }
  
/* ************************************************************************** */
/*                            METODOS DE EVENTOS                              */
/* ************************************************************************** */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.moveFocus(tblLista);
    FarmaUtility.centrarVentana(this);
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, 
                             "Debe presionar la tecla ESC para cerrar la ventana.", 
                             null);
  }

 
  
  private void txtCodCupon_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  
/* ************************************************************************** */
/*                       METODOS AUXILIARES DE EVENTOS                        */
/* ************************************************************************** */

  private void chkKeyPressed(KeyEvent e)
  {
    //FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,txtBuscar,1);

    if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
    {
      cerrarVentana(true);
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
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

  private void tblLista_keyPressed(KeyEvent e)
  {
  chkKeyPressed(e);
  }

/* ************************************************************************** */
/*                       METODOS DE LOGICA DE NEGOCIO                         */
/* ************************************************************************** */

}
