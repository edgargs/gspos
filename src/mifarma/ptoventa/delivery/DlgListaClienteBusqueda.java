package mifarma.ptoventa.delivery;
import java.awt.*;
import java.util.*;
import java.awt.Dimension;
import javax.swing.JDialog;
import com.gs.mifarma.componentes.JPanelWhite;
import java.awt.Rectangle;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import com.gs.mifarma.componentes.JLabelFunction;
import mifarma.ptoventa.delivery.reference.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import mifarma.common.*;
import mifarma.ptoventa.delivery.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class DlgListaClienteBusqueda extends JDialog
{

  private Frame myParentFrame;
  private FarmaTableModel tableModel;
  private GridLayout gridLayout1 = new GridLayout();
  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblClientesBusqueda = new JTable();
  private JLabelFunction jLabelFunction1 = new JLabelFunction();
  private JLabelFunction jLabelFunction2 = new JLabelFunction();

  public DlgListaClienteBusqueda()
  {
    this(null, "", false);
  }

  public DlgListaClienteBusqueda(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(391, 255));
    this.getContentPane().setLayout(gridLayout1);
    this.setTitle("Listado Clientes");
    this.addWindowListener(new java.awt.event.WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    jScrollPane1.setBounds(new Rectangle(10, 10, 365, 180));
    tblClientesBusqueda.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblClientesBusqueda_keyPressed(e);
        }
      });
    jLabelFunction1.setBounds(new Rectangle(150, 200, 125, 20));
    jLabelFunction1.setText("[ Enter ] Seleccionar");
    jLabelFunction2.setBounds(new Rectangle(290, 200, 85, 20));
    jLabelFunction2.setText("[ ESC ] Cerrar");
    jScrollPane1.getViewport().add(tblClientesBusqueda, null);
    jPanelWhite1.add(jLabelFunction2, null);
    jPanelWhite1.add(jLabelFunction1, null);
    jPanelWhite1.add(jScrollPane1, null);
    this.getContentPane().add(jPanelWhite1, null);
  }
  private void initialize()
  {
    initTable();
    FarmaVariables.vAceptar = false;
  }

  private void this_windowOpened(WindowEvent e)
  {
   FarmaUtility.centrarVentana(this);
   cargaClientes();
  }

  private void initTable()
  {
    tableModel = new FarmaTableModel(ConstantsDelivery.columnsListaBusquedaApellido,ConstantsDelivery.defaultValuesListaBusquedaApellido,0);
    FarmaUtility.initSimpleList(tblClientesBusqueda,tableModel,ConstantsDelivery.columnsListaBusquedaApellido);
    //cargaListaClientesBusqueda();
  }

  private void cargaListaClientesBusqueda()
  {
    ArrayList myArray = new ArrayList();
    myArray.add("Ameghino Rojas Paulo Cesar ");
    myArray.add("42957766");
    tableModel.insertRow(myArray);
    myArray = new ArrayList();
    myArray.add("Ameghino Rojas Pamela ");
    myArray.add("42632545");
    tableModel.insertRow(myArray);
    myArray = new ArrayList();
    myArray.add("Ameghino Moreno Julio Daniel");
    myArray.add("06227718");
    tableModel.insertRow(myArray);
  }

  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }else if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      guardaRegistroCliente();
    } /*else if(e.getKeyCode() == KeyEvent.VK_F3){
      VariablesDelivery.vTipoAccionInsertarSoloCliente = ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE;
      if (FarmaUtility.rptaConfirmDialog(this,"¿Desea grabar el cliente en este momento?")) {
        muestraMantCliente();
      } else cerrarVentana(false);

      if(FarmaVariables.vAceptar)
      {
        cerrarVentana(true);
      }
    } */
  }

  private void cerrarVentana(boolean pAceptar)
	{
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
    this.dispose();
  }

  private void tblClientesBusqueda_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);

  }

  public void cargaClientes()
  {
    try
    {
      System.out.println(VariablesDelivery.vDni_Apellido_Busqueda);
      System.out.println(VariablesDelivery.vTipoBusqueda);
      DBDelivery.cargaListaClientes(tableModel,VariablesDelivery.vDni_Apellido_Busqueda, VariablesDelivery.vTipoBusqueda);
      FarmaUtility.ordenar(tblClientesBusqueda, tableModel, 2, FarmaConstants.ORDEN_ASCENDENTE);
      if(tblClientesBusqueda.getRowCount() <= 0){
        FarmaUtility.showMessage(this, "No se encontro ningun Cliente para esta Busqueda",tblClientesBusqueda);
        cerrarVentana(false);
        /*VariablesDelivery.vTipoAccionInsertarSoloCliente = ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE;
        System.out.println(VariablesDelivery.vTipoAccionInsertarSoloCliente);
        if (FarmaUtility.rptaConfirmDialog(this,"¿Desea grabar el cliente en este momento?")) {
            muestraMantCliente();
        } else cerrarVentana(false);*/
      }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
      FarmaUtility.showMessage(this, "Error al listar los Clientes",null);
      cerrarVentana(false);
    }
  }

  private void guardaRegistroCliente()
  {
    if(tblClientesBusqueda.getRowCount() > 0){
      VariablesDelivery.vInfoClienteBusqueda.clear();
      VariablesDelivery.vInfoClienteBusqueda.add(tableModel.data.get(tblClientesBusqueda.getSelectedRow()));
      System.out.println(VariablesDelivery.vInfoClienteBusqueda);
      VariablesDelivery.vCodCli = ((String)tblClientesBusqueda.getValueAt(tblClientesBusqueda.getSelectedRow(),0)).trim();
      cerrarVentana(true);
    }
  }

  /*private void muestraMantCliente()
  {
    DlgMantClienteDireccion dlgMantClienteDireccion = new DlgMantClienteDireccion(myParentFrame,"",true);
    dlgMantClienteDireccion.setVisible(true);
  }*/
}