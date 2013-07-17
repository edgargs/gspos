package mifarma.ptoventa.delivery;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;

import java.util.*;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import com.gs.mifarma.componentes.JPanelWhite;
import java.awt.Dimension;
import com.gs.mifarma.componentes.JPanelTitle;
import java.awt.Rectangle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import mifarma.common.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import com.gs.mifarma.componentes.JPanelHeader;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import java.awt.Color;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JButtonLabel;
import mifarma.ptoventa.delivery.reference.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.Font;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.ventas.reference.*;
/**
* Copyright (c) 2006 MIFARMA S.A.C.<br>
* <br>
* Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
* Nombre de la Aplicación : DlgListaClientes.java<br>
* <br>
* Histórico de Creación/Modificación<br>
* ERIOS      11.04.2006   Creación<br>
* <br>
* @author Edgar Rios Navarro<br>
* @version 1.0<br>
*
*/
public class DlgListaClientes extends JDialog
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */

  Frame myParentFrame;
  FarmaTableModel tableModel;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JScrollPane scrLista = new JScrollPane();
  private JTable tblListaClientes = new JTable();
  private JPanelWhite pnlWhite1 = new JPanelWhite();
  private JLabelOrange lblTelefono_T = new JLabelOrange();
  private JPanelTitle pnllTitle1 = new JPanelTitle();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JLabelFunction lblF4 = new JLabelFunction();
  private JLabelFunction lblF5 = new JLabelFunction();
  private JButtonLabel btnListaClientes = new JButtonLabel();
  private JLabelOrange lblDireccion_T = new JLabelOrange();
  private JLabelOrange lblTelefono = new JLabelOrange();
  private JLabelOrange lblDireccion = new JLabelOrange();
  private JLabelFunction lblF6 = new JLabelFunction();
  private JLabelOrange lblDistrito_T = new JLabelOrange();
  private JLabelOrange lblDistrito = new JLabelOrange();
  
  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgListaClientes()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  public DlgListaClientes(Frame parent, String title, boolean modal)
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
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Lista Clientes");
    this.setSize(new Dimension(496, 313));
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
    lblEsc.setBounds(new Rectangle(385, 260, 100, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblF11.setBounds(new Rectangle(275, 260, 105, 20));
    lblF11.setText("[ F11 ] Aceptar");
    scrLista.setBounds(new Rectangle(10, 90, 475, 140));
    tblListaClientes.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblListaClientes_keyPressed(e);
        }
      });
    pnlWhite1.setBounds(new Rectangle(10, 10, 475, 55));
    pnlWhite1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    lblTelefono_T.setText("Telefono:");
    lblTelefono_T.setBounds(new Rectangle(15, 10, 60, 15));
    pnllTitle1.setBounds(new Rectangle(10, 70, 475, 20));
    lblF3.setBounds(new Rectangle(10, 235, 110, 20));
    lblF3.setText("[ F3 ] Crear Cliente");
    lblF4.setBounds(new Rectangle(125, 235, 135, 20));
    lblF4.setText("[ F4 ] Modificar Cliente");
    lblF5.setBounds(new Rectangle(265, 235, 150, 20));
    lblF5.setText("[ F5 ] Modificar Direccion");
    btnListaClientes.setText("Lista Clientes");
    btnListaClientes.setBounds(new Rectangle(5, 0, 105, 20));
    btnListaClientes.setMnemonic('L');
    btnListaClientes.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnListaClientes_keyPressed(e);
        }
      });
    btnListaClientes.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnListaClientes_actionPerformed(e);
        }
      });
    lblDireccion_T.setText("Direccion:");
    lblDireccion_T.setBounds(new Rectangle(15, 30, 79, 15));
    lblTelefono.setBounds(new Rectangle(85, 10, 190, 15));
    lblTelefono.setFont(new Font("SansSerif", 1, 12));
    lblDireccion.setBounds(new Rectangle(85, 30, 390, 15));
    lblF6.setBounds(new Rectangle(145, 260, 120, 20));
    lblF6.setText("[ F6 ] Buscar Cliente");
    lblDistrito_T.setText("Distrito");
    lblDistrito_T.setBounds(new Rectangle(15, 50, 55, 15));
    lblDistrito.setBounds(new Rectangle(85, 50, 430, 15));
    pnlWhite1.add(lblDistrito, null);
    pnlWhite1.add(lblDistrito_T, null);
    pnlWhite1.add(lblDireccion, null);
    pnlWhite1.add(lblTelefono, null);
    pnlWhite1.add(lblDireccion_T, null);
    pnlWhite1.add(lblTelefono_T, null);
        jContentPane.add(lblF6, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblF3, null);
        pnllTitle1.add(btnListaClientes, null);
        jContentPane.add(pnllTitle1, null);
        jContentPane.add(pnlWhite1, null);
        scrLista.getViewport().add(tblListaClientes, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }

  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initTable();
    limpiaVariables();
    FarmaVariables.vAceptar = false;
  }

  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
    tableModel = new FarmaTableModel(ConstantsDelivery.columnsListaClientes,ConstantsDelivery.defaultValuesListaClientes,0);
    FarmaUtility.initSimpleList(tblListaClientes,tableModel,ConstantsDelivery.columnsListaClientes);
    //cargaListaClientes();
  }



  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

    private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.moveFocus(tblListaClientes);
    FarmaUtility.centrarVentana(this);
    cargaTelefono();
    if (FarmaVariables.vAceptar)
    {
      cargaListaClientes();
      cargaCabeceraDireccionTelefono();
      //cargaTableModel();
      FarmaUtility.moveFocus(tblListaClientes);
    }
    else
    {
      cerrarVentana(false);
    }
    lblF6.setVisible(false);
    lblDistrito.setVisible(false);
    lblDistrito_T.setVisible(false);

  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  private void btnListaClientes_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblListaClientes);
  }

  private void btnListaClientes_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    //FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,txtBuscar,1);
    if(e.getKeyCode() == KeyEvent.VK_F3)
    {
      muestraMantClientes(e);
      if (FarmaVariables.vAceptar)
      {
        if(!insertaDetalle()) return;
        System.out.println("InsertaDetalle");
        FarmaVariables.vAceptar = false;
        cargaListaClientes();
        String direccion = VariablesDelivery.vTipoCalle + " " +  VariablesDelivery.vNombreCalle + " " + VariablesDelivery.vNumeroCalle + " " +
        VariablesDelivery.vNombreInterior + VariablesDelivery.vNombreUrbanizacion;
        lblTelefono.setText(VariablesDelivery.vNumeroTelefono);
        lblDireccion.setText(direccion);
        FarmaUtility.findTextInJTable(tblListaClientes,VariablesDelivery.vCodCli.trim(), 0, 0);
        //lblDistrito.setText(VariablesDelivery.vNombreDistrito);
      }
    }
    else if(e.getKeyCode() == KeyEvent.VK_F4)
    {
      if(tblListaClientes.getRowCount() <= 0) return;
      muestraMantClientes(e);
      if (FarmaVariables.vAceptar)
      {
        FarmaVariables.vAceptar = false;
        cargaListaClientes();
        FarmaUtility.findTextInJTable(tblListaClientes,VariablesDelivery.vCodCli.trim(), 0, 0);
      }
    }
    else if(e.getKeyCode() == KeyEvent.VK_F5)
    {
      if(tblListaClientes.getRowCount() <= 0) return;
      muestraMantClientes(e);
      if (FarmaVariables.vAceptar)
      {
        FarmaVariables.vAceptar = false;
        cargaCabeceraDireccionTelefono();
      }
    }
    else if(e.getKeyCode() == KeyEvent.VK_F6)
    {
    //JCORTEZ 07.08.09 no se usara por ahora
    
     /* muestraBuscaDni();
      if(FarmaVariables.vAceptar){
        System.out.println("VariablesDelivery.vTipoAccionInsertarSoloCliente - " + VariablesDelivery.vTipoAccionInsertarSoloCliente );
        if(!validaRepetidos())
        {
          FarmaUtility.showMessage(this,"El cliente ya existe", tblListaClientes);
           return;
        } else{
            if(VariablesDelivery.vTipoAccionInsertarSoloCliente.equals(ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE))
            {
            //if(!insertaDetalle()) return;
            //System.out.println("InsertaDetalle");
            //FarmaVariables.vAceptar = false;
               cargaListaClientes();
          } else {
              if(VariablesDelivery.vInfoClienteBusqueda.size()<=0)
              FarmaUtility.showMessage(this,"No existe el cliente para la busqueda",tblListaClientes);
              else {
              cargaTableModel();
              FarmaUtility.moveFocusJTable(tblListaClientes);
            }
        }
        }
          //cargaCabeceraDireccionTelefono();
          //cargaListaClientes();
      }*/
    }
   
    else if(e.getKeyCode() == KeyEvent.VK_F11)
    {
      if(tblListaClientes.getRowCount()>0){
        if (VariablesDelivery.vIndFaltaDatosCli.equalsIgnoreCase(ConstantsDelivery.FALTA_DATO_CLIENTE))
        {
          VariablesDelivery.vCodDirTmpTable = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),3)).trim();
          if(lblTelefono.getText().equals("") || lblDireccion.getText().equals(""))
          {
            FarmaUtility.showMessage(this,"Debe Asociar una direccion al cliente",tblListaClientes);
            return;
          }else{
            if(VariablesDelivery.vCodDirTmpTable.equals("")){
              VariablesDelivery.vCodCli = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),0)).trim();
              if(!insertaDetalle()) return;
              System.out.println("Inserto Detalle");
            }
          }
          cargaDatosCliente();
          cargaCabeceraDireccionTelefono();
          cerrarVentana(true);
        } else{
          VariablesDelivery.vCodDirTmpTable = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),3)).trim();
          if(lblTelefono.getText().equals("") || lblDireccion.getText().equals(""))
          {
            FarmaUtility.showMessage(this,"Debe Asociar una direccion al cliente",tblListaClientes);
            return;
          }
          else{
            if(VariablesDelivery.vCodDirTmpTable.equals("")){
              VariablesDelivery.vCodCli = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),0)).trim();
              if(!insertaDetalle()) return;
              System.out.println("Inserto Detalle");
            }
          }
          cargaDatosCliente();
          cargaCabeceraDireccionTelefono();
          //muestraResumenPedido();
          cerrarVentana(true);
        }
      }
      else if (tblListaClientes.getRowCount()<=0){
        //muestraResumenPedido();
        cerrarVentana(true);
      }
    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
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

  private void muestraMantClientes(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_F3)
    {
      /*if(tblListaClientes.getRowCount()>0){
        VariablesDelivery.vTipoAccionInsertarSoloCliente = ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE;
      }else */
      if (lblTelefono.getText().equals("") || lblDireccion.getText().equals("")) {
        VariablesDelivery.vTipoAccion = ConstantsDelivery.ACCION_INSERTAR;
        VariablesDelivery.vDireccion = lblDireccion.getText();
        System.out.println("Accion vTipoAccion: " + VariablesDelivery.vTipoAccion);
        System.out.println("Accion ACCION_INSERTAR");
      }else //(lblTelefono.getText().equals("") || lblDireccion.getText().equals("") || lblDistrito.getText().equals(""))
      {
        VariablesDelivery.vTipoAccionInsertarSoloCliente = ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE;
        VariablesDelivery.vDireccion = lblDireccion.getText();
        System.out.println("Accion vTipoAccion 2 : " + VariablesDelivery.vTipoAccion);
        System.out.println("Accion ACCION_INSERTAR_SOLO_CLIENTE");
      }

      //System.out.println("Accion vTipoAccionInsertarSoloCliente: " + VariablesDelivery.vTipoAccionInsertarSoloCliente);
      DlgMantClienteDireccionNew dlgMantClienteDireccionNew = new DlgMantClienteDireccionNew(myParentFrame,"",true);
      dlgMantClienteDireccionNew.setVisible(true);

    }
    if(e.getKeyCode() == KeyEvent.VK_F4)
    {
      VariablesDelivery.vTipoAccion = ConstantsDelivery.ACCION_MODIFICAR;
      System.out.println("Accion vTipoAccion: " + VariablesDelivery.vTipoAccion);
       System.out.println("Accion ACCION_MODIFICAR");
      VariablesDelivery.vDireccion = lblDireccion.getText();
      VariablesDelivery.vNombreCliente = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),1)).trim();
      VariablesDelivery.vApellidoPaterno = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),4)).trim();
      VariablesDelivery.vApellidoMaterno = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),5)).trim();
      VariablesDelivery.vNumeroDocumento = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),2)).trim();
      VariablesDelivery.vCodCli = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),0)).trim();
      DlgMantClienteDireccionNew dlgMantClienteDireccionNew = new DlgMantClienteDireccionNew(myParentFrame,"",true);
      dlgMantClienteDireccionNew.setVisible(true);
      if(FarmaVariables.vAceptar)
      {
        cargaCabeceraDireccionTelefono();
        cargaListaClientes();
      }
    }
    if(e.getKeyCode() == KeyEvent.VK_F5)
    {
      if(VariablesDelivery.vCodigoDireccion.equalsIgnoreCase("")){
        VariablesDelivery.vTipoAccionDireccion = ConstantsDelivery.ACCION_INSERTAR_DIRECCION;
         System.out.println("Accion INSERTAR_DIRECCION");
        System.out.println("Accion vTipoAccionDireccion: " + VariablesDelivery.vTipoAccionDireccion);
        VariablesDelivery.vDireccion = lblDireccion.getText();
        VariablesDelivery.vNombreCliente = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),1)).trim();
        VariablesDelivery.vApellidoPaterno = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),4)).trim();
        VariablesDelivery.vApellidoMaterno = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),5)).trim();
        VariablesDelivery.vNumeroDocumento = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),2)).trim();
        VariablesDelivery.vCodCli = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),0)).trim();
        DlgMantClienteDireccionNew dlgMantClienteDireccionNew = new DlgMantClienteDireccionNew(myParentFrame,"",true);
        dlgMantClienteDireccionNew.setVisible(true);
        if(FarmaVariables.vAceptar)
        {
          cargaCabeceraDireccionTelefono();
          if(tblListaClientes.getRowCount() <= 0) cargaListaClientes();
        }
      }
      else
      {
        DlgMantClienteDireccionNew dlgMantClienteDireccionNew = new DlgMantClienteDireccionNew(myParentFrame,"",true);
        VariablesDelivery.vTipoAccionDireccion = ConstantsDelivery.ACCION_MODIFICAR_DIRECCION;
         System.out.println("Accion MODIFICAR_DIRECCION");
        System.out.println("Accion vTipoAccionDireccion: " + VariablesDelivery.vTipoAccionDireccion);
        VariablesDelivery.vDireccion = lblDireccion.getText();
        VariablesDelivery.vNombreCliente = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),1)).trim();
        VariablesDelivery.vApellidoPaterno = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),4)).trim();
        VariablesDelivery.vApellidoMaterno = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),5)).trim();
        VariablesDelivery.vNumeroDocumento = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),2)).trim();
        VariablesDelivery.vCodigoDireccion = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),3)).trim();
        VariablesDelivery.vCodCli = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),0)).trim();
        dlgMantClienteDireccionNew.setVisible(true);
      }
    }
  }

  private void muestraBuscaDni()
  {
    DlgBuscaDni dlgBuscaDni = new DlgBuscaDni(myParentFrame,"",true);
    dlgBuscaDni.setVisible(true);
  }

  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */
  void cargaTelefono()
  {
    DlgBuscaTelefono dlgBuscaTelefono = new DlgBuscaTelefono(myParentFrame,"",true);
    dlgBuscaTelefono.setVisible(true);
  }

  public void cargaListaClientes()
  {
    try
    {
      System.out.println(VariablesDelivery.vNumeroTelefono);
      DBDelivery.obtieneNombreDniCliente(tableModel,VariablesDelivery.vNumeroTelefono,VariablesDelivery.vCampo);
      FarmaUtility.ordenar(tblListaClientes, tableModel, 1, FarmaConstants.ORDEN_ASCENDENTE);
      FarmaUtility.moveFocus(tblListaClientes);
      if(tblListaClientes.getRowCount() <= 0)
      FarmaUtility.showMessage(this, "No se encontro ningun Telefono para esta Busqueda",tblListaClientes);
      lblTelefono.setText(VariablesDelivery.vNumeroTelefono);
    }
    catch(SQLException e)
    {
      e.printStackTrace();
      FarmaUtility.showMessage(this, "Error al listar Clientes",null);
      cerrarVentana(false);
    }
  }

  private void tblListaClientes_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void cargaCabeceraDireccionTelefono()
  {
     ArrayList infoCabecera= new ArrayList();
		try {
      System.out.println("VariablesDelivery.vNumeroTelefono : " + VariablesDelivery.vNumeroTelefono);
      System.out.println("VariablesDelivery.vCampo : " + VariablesDelivery.vCampo);
			 infoCabecera = DBDelivery.obtieneTelefonoDireccion(VariablesDelivery.vNumeroTelefono,VariablesDelivery.vCampo);
       if(infoCabecera.size()>0){
       VariablesDelivery.vNumeroTelefonoCabecera = (((String)((ArrayList) infoCabecera.get(0)).get(0)).trim() );
       VariablesDelivery.vDireccion = (((String)((ArrayList) infoCabecera.get(0)).get(1)).trim() );
       VariablesDelivery.vNombreDistrito = (((String)((ArrayList) infoCabecera.get(0)).get(2)).trim() );
       VariablesDelivery.vCodigoDireccion = (((String)((ArrayList) infoCabecera.get(0)).get(3)).trim() );
       lblTelefono.setText(VariablesDelivery.vNumeroTelefonoCabecera);
       lblDireccion.setText(VariablesDelivery.vDireccion);
       lblDistrito.setText(VariablesDelivery.vNombreDistrito);
       VariablesDelivery.vNombreCalle = (((String)((ArrayList) infoCabecera.get(0)).get(4)).trim());
       VariablesDelivery.vNumeroCalle = (((String)((ArrayList) infoCabecera.get(0)).get(5)).trim());
       VariablesDelivery.vNombreInterior = (((String)((ArrayList) infoCabecera.get(0)).get(6)).trim() );
       VariablesDelivery.vNombreUrbanizacion  = (((String)((ArrayList) infoCabecera.get(0)).get(7)).trim() );
       VariablesDelivery.vReferencia = (((String)((ArrayList) infoCabecera.get(0)).get(8)).trim() );
       VariablesDelivery.vDescTipoCalle=(((String)((ArrayList) infoCabecera.get(0)).get(9)).trim() );
       //VariablesDelivery.vTipoCalle=(((String)((ArrayList) infoCabecera.get(0)).get(10)).trim() );

       String direccion = VariablesDelivery.vDescTipoCalle + " " +  VariablesDelivery.vNombreCalle + " " + VariablesDelivery.vNumeroCalle + " " +
       VariablesDelivery.vNombreInterior + VariablesDelivery.vNombreUrbanizacion;
       //VariablesVentas.vDir_Cli_Ped=direccion;
       VariablesVentas.vDir_Cli_Ped = VariablesDelivery.vNombreCalle;
       System.out.println("vNombreCalle="+VariablesDelivery.vNombreCalle);
       System.out.println("vNumeroCalle="+VariablesDelivery.vNumeroCalle);
       System.out.println("vNombreInterior="+VariablesDelivery.vNombreInterior);
       System.out.println("vNombreUrbanizacion="+VariablesDelivery.vNombreUrbanizacion);
       System.out.println("vReferencia="+VariablesDelivery.vReferencia);
       System.out.println("vDescTipoCalle="+VariablesDelivery.vDescTipoCalle);
       System.out.println("vTipoCalle="+VariablesDelivery.vTipoCalle);

       }
		} catch (SQLException sqlException) {
      infoCabecera= new ArrayList();
			sqlException.printStackTrace();
		}
  }

  private void cargaTableModel()
  {
    ArrayList myArray = new ArrayList();
    myArray.add((((String)((ArrayList) VariablesDelivery.vInfoClienteBusqueda.get(0)).get(0)).trim()));
    myArray.add((((String)((ArrayList) VariablesDelivery.vInfoClienteBusqueda.get(0)).get(1)).trim()));
    myArray.add((((String)((ArrayList) VariablesDelivery.vInfoClienteBusqueda.get(0)).get(2)).trim()));
    myArray.add((((String)((ArrayList) VariablesDelivery.vInfoClienteBusqueda.get(0)).get(3))));
    myArray.add((((String)((ArrayList) VariablesDelivery.vInfoClienteBusqueda.get(0)).get(4)).trim()));
    myArray.add((((String)((ArrayList) VariablesDelivery.vInfoClienteBusqueda.get(0)).get(5)).trim()));
    myArray.add((((String)((ArrayList) VariablesDelivery.vInfoClienteBusqueda.get(0)).get(6)).trim()));
    tableModel.insertRow(myArray);
    System.out.println(myArray);
    VariablesDelivery.vInfoClienteBusqueda.clear();
  }

  private boolean validaRepetidos()
  {
    if(tblListaClientes.getRowCount() <= 0) return true;
    String codCli = VariablesDelivery.vCodCli;
    for(int i=0; i<tblListaClientes.getRowCount(); i++)
    {
      String codTmp = ((String)tblListaClientes.getValueAt(i,0)).trim();
      if(codCli.equalsIgnoreCase(codTmp)) return false;
    }
    return true;
  }

  private boolean insertaDetalle(){
  String resultado = "";
    try
    {
      DBDelivery.agregaDetalleClienteDireccion(VariablesDelivery.vCodigoDireccion,
                                                         VariablesDelivery.vCodCli);
      FarmaUtility.aceptarTransaccion();
      return true;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error al insertar en el Detalle de Cliente Direccion - \n" + sql ,null);
      return false;
    }
  }

  private void cargaDatosCliente()
  {
    VariablesDelivery.vNombreCliente = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),6)).trim();
    VariablesDelivery.vApellidoPaterno = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),4)).trim();
    VariablesDelivery.vApellidoMaterno = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),5)).trim();
    VariablesDelivery.vNumeroDocumento = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),2)).trim();
    VariablesDelivery.vCodigoDireccion = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),3)).trim();
    VariablesDelivery.vCodCli = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(),0)).trim();
    
    VariablesVentas.vNom_Cli_Ped= VariablesDelivery.vNombreCliente.toUpperCase().trim() + " " + VariablesDelivery.vApellidoPaterno.toUpperCase().trim() + " " + VariablesDelivery.vApellidoMaterno.toUpperCase().trim();

    //JCORTEZ 07.08.09
      VariablesDelivery.vNumeroTelefonoCabecera=lblTelefono.getText().trim();
      VariablesDelivery.vDireccion=lblDireccion.getText().trim(); 
      
 
  }

  private void limpiaVariables(){
    //VariablesDelivery.vNumeroTelefono = "";
    VariablesDelivery.vNombreInHashtable ="";
    VariablesDelivery.vNombreInHashtableVal ="";
    VariablesDelivery.vCampo = "" ;
    VariablesDelivery.vCantidadLista= "";
    VariablesDelivery.vDni_Apellido_Busqueda = "";
    VariablesDelivery.vTipoBusqueda = "";
    VariablesDelivery.vNumeroTelefonoCabecera= "";
    VariablesDelivery.vDireccion= "";
    VariablesDelivery.vDistrito = "";
    VariablesDelivery.vCodigoDireccion = "";
    VariablesDelivery.vInfoClienteBusqueda= new ArrayList();
    VariablesDelivery.vInfoClienteBusquedaApellidos= new ArrayList();
    VariablesDelivery.vCodCli = "";
    VariablesDelivery.vCodDirTable = "";
    VariablesDelivery.vCodDirTmpTable = "" ;
    VariablesDelivery.vNombreCliente = "";
    VariablesDelivery.vApellidoPaterno="";
    VariablesDelivery.vApellidoMaterno="";
    VariablesDelivery.vTipoDocumento="";
    VariablesDelivery.vTipoCliente="";
    VariablesDelivery.vNumeroDocumento="";
    VariablesDelivery.vTipoCalle="";
    VariablesDelivery.vNombreCalle="";
    VariablesDelivery.vNumeroCalle="";
    VariablesDelivery.vNombreUrbanizacion="";
    VariablesDelivery.vNombreDistrito="";
    VariablesDelivery.vReferencia="";
    VariablesDelivery.vNombreInterior="";
    VariablesDelivery.vModificacionDireccion="";
    VariablesDelivery.vTipoAccion = "";
    VariablesDelivery.vTipoAccionDireccion = "";
    VariablesDelivery.vCodTelefono = "";
    //VariablesVentas.vNumTarjCred = "";
    //VariablesVentas.vFecVencTarjCred = "" ;
    //VariablesVentas.vNomCliTarjCred = "" ;
    VariablesDelivery.vCodigoOperCobro = "";
    //VariablesVentas.vNumSecTarjCli = "";
    VariablesDelivery.vDni = "" ;
  }
/*
  private void muestraObservaciones()
  {
    DlgObservaciones dlgObservaciones = new DlgObservaciones(myParentFrame,"",true);
    dlgObservaciones.setVisible(true);
  }
  */

}