package mifarma.ptoventa.convenio;

import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import com.gs.mifarma.componentes.JPanelWhite;
import java.awt.Rectangle;
import java.awt.GridLayout;
import com.gs.mifarma.componentes.JPanelHeader;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Font;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import java.awt.event.KeyEvent;
import com.gs.mifarma.componentes.JButtonLabel;
import java.awt.event.ActionEvent;
import com.gs.mifarma.componentes.JPanelTitle;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.*;
import com.gs.mifarma.componentes.JLabelFunction;
import mifarma.common.*;
import mifarma.ptoventa.convenio.reference.*;
import mifarma.ptoventa.ventas.reference.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgListaConvenios extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgListaConvenios.class);

  private Frame myParentFrame;
  FarmaTableModel tableModelListaconvenios;
  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private GridLayout gridLayout1 = new GridLayout();
  private JPanelHeader pnlCliente = new JPanelHeader();
  private JTextFieldSanSerif txtNombreConvenio = new JTextFieldSanSerif();
  private JButtonLabel btnNombre = new JButtonLabel();
  private JPanelTitle pnlRelacionCliente = new JPanelTitle();
  private JButtonLabel btnRelacion = new JButtonLabel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblConvenios = new JTable();
  private JLabelFunction jLabelFunction1 = new JLabelFunction();
  private JLabelFunction jLabelFunction2 = new JLabelFunction();
  
  // constantes de listado de convenios de clientes
  // 31.01.2008 dubilluz  creacion
  private int COLUMN_COD_CONV_    = 0;
  private int COLUMN_DESC_CONV    = 1;
  private int COLUMN_PDESC_CONV   = 2;
  private int COLUMN_PCOPAGO_CONV = 3;
  private int COLUMN_IND_CONV_DEP = 4;
  private final int COL_SOLO_CRED = 5;
  
  public DlgListaConvenios()
  {
    this(null, "", false);
  }

  public DlgListaConvenios(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(466, 373));
    this.getContentPane().setLayout(gridLayout1);
    this.setTitle("Lista de Convenios");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    pnlCliente.setBounds(new Rectangle(10, 10, 440, 40));
    txtNombreConvenio.setBounds(new Rectangle(95, 10, 295, 20));
    txtNombreConvenio.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtNombreConvenio_keyPressed(e);
        // txtClienteJuridico_keyPressed(e);
        }
        public void keyReleased(KeyEvent e)
        {
          txtNombreConvenio_keyReleased(e);
          //txtClienteJuridico_keyReleased(e);
        }
      });
    btnNombre.setText("Nombre :");
    btnNombre.setBounds(new Rectangle(20, 10, 65, 20));
    btnNombre.setMnemonic('n');
    btnNombre.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
         // btnClienteJuridico_actionPerformed(e);
        }
      });
    pnlRelacionCliente.setBounds(new Rectangle(10, 60, 440, 25));
    btnRelacion.setText("Relacion de Convenios");
    btnRelacion.setBounds(new Rectangle(10, 5, 150, 15));
    btnRelacion.setMnemonic('r');
    btnRelacion.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
         // btnRelacion_actionPerformed(e);
        }
      });
    jScrollPane1.setBounds(new Rectangle(10, 85, 440, 225));
    jLabelFunction1.setBounds(new Rectangle(205, 315, 117, 19));
    jLabelFunction1.setText("[ F11 ] Seleccionar");
    jLabelFunction2.setBounds(new Rectangle(335, 315, 115, 20));
    jLabelFunction2.setText("[ ESC ] Cerrar");
    pnlCliente.add(txtNombreConvenio, null);
    pnlCliente.add(btnNombre, null);
    pnlRelacionCliente.add(btnRelacion, null);
    jScrollPane1.getViewport().add(tblConvenios, null);
    jPanelWhite1.add(jLabelFunction2, null);
    jPanelWhite1.add(jLabelFunction1, null);
    jPanelWhite1.add(jScrollPane1, null);
    jPanelWhite1.add(pnlRelacionCliente, null);
    jPanelWhite1.add(pnlCliente, null);
    this.getContentPane().add(jPanelWhite1, null);
  }
  
  private void initialize()
  {
    initTableListaConvenios();
    FarmaVariables.vAceptar = false;
  }
  
  private void initTableListaConvenios()
  {
    tableModelListaconvenios = new FarmaTableModel(ConstantsConvenio.columnsListaConvenios,ConstantsConvenio.defaultValuesListaConvenios,0);
    FarmaUtility.initSimpleList(tblConvenios,tableModelListaconvenios,ConstantsConvenio.columnsListaConvenios);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    //limpia Variables de Cliente Dependiente    
    VariablesConvenio.vCodClienteDependiente = "";
    VariablesConvenio.vCodTrabDependiente = "";
    cargaListaConvenios();
    FarmaUtility.moveFocus(txtNombreConvenio);
  }
  
  private void cargaListaConvenios()
  {
    try
    {
      DBConvenio.listaConvenios(tableModelListaconvenios);
      FarmaUtility.ordenar(tblConvenios,tableModelListaconvenios,1,FarmaConstants.ORDEN_ASCENDENTE); 
      if(tblConvenios.getRowCount() <= 0) {
        FarmaUtility.showMessage(this, "No se encontro ningun Convenio ",txtNombreConvenio);
        cerrarVentana(false);
      }
      else {
        FarmaGridUtils.showCell(tblConvenios, 0, 0);
        FarmaUtility.setearActualRegistro(tblConvenios, txtNombreConvenio, 1);
      }    
    }catch (SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurio un error al listar los medicos \n " + sql.getMessage(),txtNombreConvenio);
    }
  }
  
  private void guardaRegistroConvenio()
  {
    int vFila = tblConvenios.getSelectedRow();
    if( vFila > -1){
      VariablesConvenio.vArrayList_ListaConvenio.clear();
      VariablesConvenio.vArrayList_ListaConvenio.add(tableModelListaconvenios.data.get(vFila));
      // Se obtiene el valor de indClienteDependiente
      VariablesConvenio.vIndConvCliDependiente = FarmaUtility.getValueFieldJTable(tblConvenios,
                                                                                  vFila,
                                                                                  COLUMN_IND_CONV_DEP);
      VariablesConvenio.vIndSoloCredito = FarmaUtility.getValueFieldJTable(tblConvenios,vFila,COL_SOLO_CRED);
    }
  }
  
  private void obtieneDatosConvenio()
  {
    if(VariablesConvenio.vArrayList_ListaConvenio.size() == 1)
    {
      VariablesConvenio.vCodConvenio  = FarmaUtility.getValueFieldArrayList(
                                                              VariablesConvenio.vArrayList_ListaConvenio,
                                                              0,
                                                              COLUMN_COD_CONV_);
      VariablesConvenio.vNomConvenio  = FarmaUtility.getValueFieldArrayList(
                                                              VariablesConvenio.vArrayList_ListaConvenio,
                                                              0,
                                                              COLUMN_DESC_CONV);
      VariablesConvenio.vPorcDctoConv = FarmaUtility.getValueFieldArrayList(
                                                              VariablesConvenio.vArrayList_ListaConvenio,
                                                              0,
                                                              COLUMN_PDESC_CONV);
      VariablesConvenio.vPorcCoPago   = FarmaUtility.getValueFieldArrayList(
                                                              VariablesConvenio.vArrayList_ListaConvenio,
                                                              0,
                                                              COLUMN_PCOPAGO_CONV);
      
      log.debug("*****************************");
      log.debug("VariablesConvenio.vCodConvenio : " + VariablesConvenio.vCodConvenio);
      log.debug("VariablesConvenio.vNomConvenio : " + VariablesConvenio.vNomConvenio);
      log.debug("VariablesConvenio.vPorcDctoConv : " + VariablesConvenio.vPorcDctoConv);
      log.debug("VariablesConvenio.vPorcCoPago : " + VariablesConvenio.vPorcCoPago);
      //muestraIngresoInfoConvenio();
    }
  }
  
  private void chkKeyPressed(KeyEvent e)
  {
   if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
    cerrarVentana(false);
   }
   else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)){
    guardaRegistroConvenio();
    obtieneDatosConvenio();
    muestraIngresoInfoConvenio();
    }
  }
  
   private void cerrarVentana(boolean pAceptar) {
		/*FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();*/
    FarmaVariables.vImprTestigo = FarmaConstants.INDICADOR_N ;
    log.debug("FarmaVariables.vImprTestigo : " + FarmaVariables.vImprTestigo);
		FarmaVariables.vAceptar = pAceptar;
    VariablesVentas.vEsPedidoConvenio = false ;
		this.setVisible(false);
    //FarmaVariables.vAceptar = true;
    this.dispose(); 
	}

  private void txtNombreConvenio_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e, tblConvenios, txtNombreConvenio, 1);
    chkKeyPressed(e);  
  }

  private void txtNombreConvenio_keyReleased(KeyEvent e)
  {
    FarmaGridUtils.buscarDescripcion(e, tblConvenios, txtNombreConvenio, 1);  
  }

  /**
   * Se muestra el dialogo de resumen de receta.
   * @author Edgar Rios Navarro
   * @since 06.12.2006
   */
   
  private void muestraIngresoInfoConvenio()
  {
    log.debug("Entro");
    DlgDatosConvenio dlgDatosConvenio = new DlgDatosConvenio(myParentFrame,"",true);
    dlgDatosConvenio.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      cerrarVentana(true);
    }
  }
}
