package mifarma.ptoventa.inventariociclico;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventariociclico.reference.ConstantsInvCiclico;
import mifarma.ptoventa.inventariociclico.reference.DBInvCiclico;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgProductosInvCiclico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      24.10.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgProductosInvCiclico
  extends JDialog
{
  /* ************************************************************************ */
  /*                        DECLARACION PROPIEDADES                           */
  /* ************************************************************************ */
  FarmaTableModel tableModelProductos;
  Frame myParentFrame;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrPane1 = new JScrollPane();
  private JTable tblRelacionProductos = new JTable();
  private JButtonLabel btnRelacionProductos = new JButtonLabel();
  private JButtonLabel btnProductos = new JButtonLabel();
  private JTextFieldSanSerif txtProductos = new JTextFieldSanSerif();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelWhite lblItems_T = new JLabelWhite();
  private JLabelWhite lblItems = new JLabelWhite();
  private JLabelFunction lblEnter = new JLabelFunction();

  /* ************************************************************************ */
  /*                          CONSTRUCTORES                                   */
  /* ************************************************************************ */

  public DlgProductosInvCiclico()
  {
    this(null, "", false);
  }

  public DlgProductosInvCiclico(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */

  private void jbInit()
    throws Exception
  {
    this.setSize(new Dimension(642, 416));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Productos");
    this.addWindowListener(new WindowAdapter()
        {
          public void windowOpened(WindowEvent e)
          {
            this_windowOpened(e);
          }
        });
    pnlHeader1.setBounds(new Rectangle(5, 10, 625, 50));
    pnlHeader1.setLayout(null);
    pnlTitle1.setBounds(new Rectangle(5, 65, 625, 25));
    pnlTitle1.setLayout(null);
    scrPane1.setBounds(new Rectangle(5, 90, 625, 260));
    btnRelacionProductos.setText("Relacion de Productos");
    btnRelacionProductos.setBounds(new Rectangle(10, 0, 140, 25));
    btnProductos.setText("Productos :");
    btnProductos.setMnemonic('p');
    btnProductos.setBounds(new Rectangle(30, 15, 65, 20));
    btnProductos.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnProductos_actionPerformed(e);
          }
        });
    txtProductos.setBounds(new Rectangle(115, 15, 315, 20));
    txtProductos.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            txtProductos_keyPressed(e);
          }

          public void keyReleased(KeyEvent e)
          {
            txtProductos_keyReleased(e);
          }
        });
    lblEsc.setBounds(new Rectangle(500, 360, 105, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblF11.setBounds(new Rectangle(370, 360, 117, 20));
    lblF11.setText("[ F11 ] Aceptar");
    lblItems_T.setText("Items:");
    lblItems_T.setBounds(new Rectangle(525, 0, 50, 25));
    lblItems.setText("1000");
    lblItems.setBounds(new Rectangle(565, 0, 35, 25));
    lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
    lblEnter.setBounds(new Rectangle(10, 360, 150, 20));
    lblEnter.setText("[ ENTER ] Seleccionar");
    jContentPane.add(lblEnter, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(lblEsc, null);
    scrPane1.getViewport().add(tblRelacionProductos, null);
    jContentPane.add(scrPane1, null);
    pnlTitle1.add(lblItems, null);
    pnlTitle1.add(lblItems_T, null);
    pnlTitle1.add(btnRelacionProductos, null);
    jContentPane.add(pnlTitle1, null);
    pnlHeader1.add(txtProductos, null);
    pnlHeader1.add(btnProductos, null);
    jContentPane.add(pnlHeader1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }

  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
    initTableListaProductos();
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */

  private void initTableListaProductos()
  {
    tableModelProductos = 
        new FarmaTableModel(ConstantsInvCiclico.columnsListaProductosInv, 
                            ConstantsInvCiclico.defaultValuesListaProductosInv, 
                            0);
    FarmaUtility.initSelectList(tblRelacionProductos, tableModelProductos, 
                                ConstantsInvCiclico.columnsListaProductosInv);
    cargaListaProductos();
  }

  private void cargaListaProductos()
  {
    try
    {
      DBInvCiclico.getListaProdsInv(tableModelProductos);
      if (tblRelacionProductos.getRowCount() > 0)
      {
        FarmaUtility.ordenar(tblRelacionProductos, tableModelProductos, 2, 
                             FarmaConstants.ORDEN_ASCENDENTE);
      }
      lblItems.setText(tblRelacionProductos.getRowCount() + "");
    }
    catch (SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this, 
                               "Ocurrió un error al obtener la lista de productos : \n" + 
                               sql.getMessage(), txtProductos);
    }
  }
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtProductos);
  }

  private void btnProductos_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtProductos);
  }

  private void txtProductos_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProductos, 
                                          txtProductos, 2);

    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      if (tblRelacionProductos.getSelectedRow() >= 0)
      {
        if (!(FarmaUtility.findTextInJTable(tblRelacionProductos, 
                                            txtProductos.getText().trim(), 
                                            1, 2)))
        {
          FarmaUtility.showMessage(this, 
                                   "Producto No Encontrado según Criterio de Búsqueda !!!", 
                                   txtProductos);
          return;
        }
      }
    }

    chkKeyPressed(e);
  }

  private void txtProductos_keyReleased(KeyEvent e)
  {
    FarmaGridUtils.buscarDescripcion(e, tblRelacionProductos, txtProductos, 
                                     2);
  }

  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      seleccionarProducto();
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
    else if (e.getKeyCode() == KeyEvent.VK_F11)
    {
      aceptar();
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

  private void seleccionarProducto()
  {
    FarmaUtility.setCheckValue(tblRelacionProductos, false);
  }

  /**
   * Inserta los productos seleccionados en la toma actual.
   */
  private void aceptar()
  {
    String codProd, unids, monto, lab;
    if (validaSeleccion())
    {
      if (FarmaUtility.rptaConfirmDialog(this, 
                                         "Va a insertar los seleccionado productos al inventario ciclico. ¿Desea continuar?"))
      {
        try
        {
          for (int i = 0; i < tblRelacionProductos.getRowCount(); i++)
          {
            Boolean valor;
            valor = (Boolean) tblRelacionProductos.getValueAt(i, 0);
            if (valor.booleanValue())
            {
              codProd = 
                  tblRelacionProductos.getValueAt(i, 1).toString().trim();
              unids = ""; 
                  //tblRelacionProductos.getValueAt(i, 5).toString().trim();
              monto = "";
                  //tblRelacionProductos.getValueAt(i, 6).toString().trim();
              lab = 
                  tblRelacionProductos.getValueAt(i, 6).toString().trim();

              DBInvCiclico.agregarProducto(codProd, unids, monto, lab);
            }
          }
          FarmaUtility.aceptarTransaccion();
          cerrarVentana(true);
        }
        catch (SQLException e)
        {
          FarmaUtility.liberarTransaccion();
          e.printStackTrace();
          FarmaUtility.showMessage(this, 
                                   "Ha ocurrido un error al insertar los productos.\n" + 
                                   e.getMessage(), txtProductos);
        }
      }
    }
  }

  private boolean validaSeleccion()
  {
    boolean retorno = false;
    Boolean valor;
    for (int i = 0; i < tblRelacionProductos.getRowCount(); i++)
    {
      valor = (Boolean) tblRelacionProductos.getValueAt(i, 0);
      retorno = valor.booleanValue();
      if (retorno)
        break;
    }

    if (!retorno)
      FarmaUtility.showMessage(this, 
                               "Debe seleccionar, al menos un producto.", 
                               txtProductos);

    return retorno;
  }
}
