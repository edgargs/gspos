package mifarma.ptoventa.cliente;

import mifarma.ptoventa.cliente.reference.*;
import java.awt.Frame;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.JDialog;
import java.awt.GridLayout;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import javax.swing.BorderFactory;
import mifarma.common.FarmaLengthText;
import java.awt.event.KeyEvent;
import com.gs.mifarma.componentes.JButtonLabel;
import java.awt.event.ActionEvent;
import com.gs.mifarma.componentes.JLabelFunction;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.*;
import mifarma.common.*;
import mifarma.ptoventa.*;
import java.sql.SQLException.*;
import mifarma.ptoventa.reference.*;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;

/**
* Copyright (c) 2006 MIFARMA S.A.C.<br>
* <br>
* Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
* Nombre de la Aplicación : DlgMantClienteNatural.java<br>
* <br>
* Histórico de Creación/Modificación<br>
*        06.03.2006   Creación<br>
* <br>
* @author   Cesar Ameghino Rojas<br>
* @version 1.0<br>
*
*/

public class DlgMantTarjetaCliente extends JDialog 
{

  FarmaTableModel tableModel;
  private Frame myParentFrame;
  private String tipoMaestro;
  private String codBusqueda;

  private FarmaTableModel tableModelListaTarjetasClientes;
  private GridLayout gridLayout1 = new GridLayout();
  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private JPanelHeader pnlCodigoLaboratorio = new JPanelHeader();
  private JLabelWhite lblCodigoLaboratorio_T = new JLabelWhite();
  private JLabelWhite lblCliente = new JLabelWhite();
  private JPanelTitle pnlDatosLaboratorio = new JPanelTitle();
  private JTextFieldSanSerif txtMes = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtNTarjeta = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtCodigoOperador = new JTextFieldSanSerif();
  private JButtonLabel btnVencimeinto = new JButtonLabel();
  private JButtonLabel btnNTarjeta = new JButtonLabel();
  private JButtonLabel btnOperador = new JButtonLabel();
  private JButtonLabel btnPropietario = new JButtonLabel();
  private JTextFieldSanSerif txtPropietario = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtDescripcionOperador = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtAño = new JTextFieldSanSerif();
  private JButtonLabel btnSeparacion = new JButtonLabel();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF1 = new JLabelFunction();
  private JPanelHeader jPanelHeader1 = new JPanelHeader();
  private JPanelTitle jPanelTitle1 = new JPanelTitle();
  private JScrollPane scrListadoTarjetas = new JScrollPane();
  private JTable tblListadoTarjetas = new JTable();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JLabelFunction lblF4 = new JLabelFunction();
  private JButtonLabel jButtonLabel1 = new JButtonLabel();
  private JLabelFunction lblSeleccionTarjeta = new JLabelFunction();
  private JLabelFunction lblF6 = new JLabelFunction();

  public DlgMantTarjetaCliente()
  {
    this(null, "", false);
  }

  public DlgMantTarjetaCliente(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(452, 473));
    this.getContentPane().setLayout(gridLayout1);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE );
    this.setTitle("Mantenimientos de Tarjeta");
    this.addWindowListener(new java.awt.event.WindowAdapter()
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
    pnlCodigoLaboratorio.setBounds(new Rectangle(5, 5, 345, 30));
    pnlCodigoLaboratorio.setSize(new Dimension(435, 30));
    pnlCodigoLaboratorio.setBackground(new Color(43, 141, 39));
    pnlCodigoLaboratorio.setLayout(null);
    pnlCodigoLaboratorio.setFont(new Font("SansSerif", 0, 11));
    lblCodigoLaboratorio_T.setText("Cliente : ");
    lblCodigoLaboratorio_T.setBounds(new Rectangle(5, 5, 55, 20));
    lblCodigoLaboratorio_T.setForeground(Color.white);
    lblCliente.setText("XXX");
    lblCliente.setBounds(new Rectangle(65, 10, 350, 20));
    lblCliente.setForeground(Color.white);
    pnlDatosLaboratorio.setBounds(new Rectangle(10, 245, 425, 140));
    pnlDatosLaboratorio.setBackground(Color.white);
    pnlDatosLaboratorio.setFont(new Font("SansSerif", 0, 11));
    pnlDatosLaboratorio.setLayout(null);
    pnlDatosLaboratorio.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    txtMes.setBounds(new Rectangle(125, 70, 30, 20));
    txtMes.setFont(new Font("SansSerif", 0, 11));
    txtMes.setDocument(new FarmaLengthText(2));
    txtMes.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtMes_keyPressed(e);
        }
      });
    txtMes.setDocument(new FarmaLengthText(50));
   txtNTarjeta.setBounds(new Rectangle(125, 35, 135, 20));
    txtNTarjeta.setFont(new Font("SansSerif", 0, 11));
    txtNTarjeta.setDocument(new FarmaLengthText(20));
    txtNTarjeta.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtNTarjeta_keyPressed(e);
        }
      });
    txtNTarjeta.setDocument(new FarmaLengthText(50));
    txtCodigoOperador.setBounds(new Rectangle(125, 5, 55, 20));
    txtCodigoOperador.setFont(new Font("SansSerif", 0, 11));
    txtCodigoOperador.setDocument(new FarmaLengthText(30));
    txtCodigoOperador.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtCodigoOperador(e);
        }

        public void keyReleased(KeyEvent e)
        {
          txtCodigoOperador_keyReleased(e);
        }
      });
    txtCodigoOperador.setDocument(new FarmaLengthText(50));
    btnVencimeinto.setText("Fecha Vencimiento:");
    btnVencimeinto.setBounds(new Rectangle(10, 75, 120, 15));
    btnVencimeinto.setForeground(new Color(255, 130, 14));
    btnVencimeinto.setMnemonic('f');
    btnVencimeinto.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnVencimeinto_actionPerformed(e);
        }
      });
    btnNTarjeta.setText("Nº Tarjeta:");
    btnNTarjeta.setBounds(new Rectangle(10, 40, 105, 15));
    btnNTarjeta.setForeground(new Color(255, 130, 14));
    btnNTarjeta.setMnemonic('t');
    btnNTarjeta.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNTarjeta_actionPerformed(e);
        }
      });
    btnOperador.setText("Operador : ");
    btnOperador.setBounds(new Rectangle(10, 10, 90, 15));
    btnOperador.setForeground(new Color(255, 130, 14));
    btnOperador.setMnemonic('o');
    btnOperador.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnOperador_actionPerformed(e);
        }
      });
    btnPropietario.setText("Propietario:");
    btnPropietario.setBounds(new Rectangle(10, 110, 105, 15));
    btnPropietario.setForeground(new Color(255, 130, 14));
    btnPropietario.setMnemonic('p');
    btnPropietario.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPropietario_actionPerformed(e);
        }
      });
    txtPropietario.setBounds(new Rectangle(125, 105, 135, 20));
    txtPropietario.setFont(new Font("SansSerif", 0, 11));
    txtPropietario.setDocument(new FarmaLengthText(30));
    txtPropietario.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtPropietario_keyPressed(e);
        }
       
      });
    txtPropietario.setDocument(new FarmaLengthText(50));
    txtDescripcionOperador.setBounds(new Rectangle(195, 5, 225, 20));
    txtDescripcionOperador.setFont(new Font("SansSerif", 0, 11));
    txtDescripcionOperador.setDocument(new FarmaLengthText(30));
    txtDescripcionOperador.addKeyListener(new java.awt.event.KeyAdapter()
      {
        
      });
    txtDescripcionOperador.setDocument(new FarmaLengthText(50));
    txtDescripcionOperador.setEnabled(false);
    txtAño.setBounds(new Rectangle(170, 70, 30, 20));
    txtAño.setFont(new Font("SansSerif", 0, 11));
    txtAño.setDocument(new FarmaLengthText(4));
    txtAño.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtAño_keyPressed(e);
        }
      });
    txtAño.setDocument(new FarmaLengthText(50));
    btnSeparacion.setBounds(new Rectangle(160, 70, 10, 15));
    btnSeparacion.setForeground(new Color(255, 130, 14));
    btnSeparacion.setMnemonic('r');
    btnSeparacion.setText("-");
    lblEsc.setText("[ESC] Cerrar");
    lblEsc.setBounds(new Rectangle(350, 420, 85, 20));
    lblEsc.setFont(new Font("SansSerif", 1, 12));
    lblF1.setText("[F11] Aceptar");
    lblF1.setBounds(new Rectangle(350, 390, 85, 20));
    lblF1.setFont(new Font("SansSerif", 1, 12));
    jPanelHeader1.setBounds(new Rectangle(160, 80, 1, 1));
    jPanelTitle1.setBounds(new Rectangle(5, 40, 435, 30));
    scrListadoTarjetas.setBounds(new Rectangle(5, 70, 435, 160));
    tblListadoTarjetas.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyReleased(KeyEvent e)
        {
          tblListadoTarjetas_keyReleased(e);
        }

        public void keyPressed(KeyEvent e)
        {
          tblListadoTarjetas_keyPressed(e);
        }
      });
    lblF2.setText("[F11] Aceptar");
    lblF2.setBounds(new Rectangle(255, 405, 85, 20));
    lblF2.setFont(new Font("SansSerif", 1, 12));
    lblF3.setText("[F9] Modificar");
    lblF3.setBounds(new Rectangle(220, 390, 85, 20));
    lblF3.setFont(new Font("SansSerif", 1, 12));
    lblF4.setText("[F11] Aceptar");
    lblF4.setBounds(new Rectangle(255, 405, 85, 20));
    lblF4.setFont(new Font("SansSerif", 1, 12));
    jButtonLabel1.setText("Listado de Tarjetas");
    jButtonLabel1.setBounds(new Rectangle(10, 10, 180, 15));
    jButtonLabel1.setMnemonic('l');
    jButtonLabel1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButtonLabel1_actionPerformed(e);
        }
      });
    lblSeleccionTarjeta.setText("[ENTER] Seleccionar Tarjeta");
    lblSeleccionTarjeta.setBounds(new Rectangle(10, 390, 175, 20));
    lblSeleccionTarjeta.setFont(new Font("SansSerif", 1, 12));
    lblF6.setText("[F11] Aceptar");
    lblF6.setBounds(new Rectangle(255, 405, 85, 20));
    lblF6.setFont(new Font("SansSerif", 1, 12));
    pnlDatosLaboratorio.add(txtAño, null);
    pnlDatosLaboratorio.add(txtDescripcionOperador, null);
    pnlDatosLaboratorio.add(txtMes, null);
    pnlDatosLaboratorio.add(txtNTarjeta, null);
    pnlDatosLaboratorio.add(txtCodigoOperador, null);
    pnlDatosLaboratorio.add(btnVencimeinto, null);
    pnlDatosLaboratorio.add(btnNTarjeta, null);
    pnlDatosLaboratorio.add(btnOperador, null);
    pnlDatosLaboratorio.add(btnPropietario, null);
    pnlDatosLaboratorio.add(txtPropietario, null);
    pnlDatosLaboratorio.add(btnSeparacion, null);
    scrListadoTarjetas.getViewport().add(tblListadoTarjetas, null);
    lblSeleccionTarjeta.add(lblF6, null);
    jPanelWhite1.add(lblSeleccionTarjeta, null);
    lblF3.add(lblF4, null);
    jPanelWhite1.add(lblF3, null);
    jPanelWhite1.add(scrListadoTarjetas, null);
    jPanelTitle1.add(jButtonLabel1, null);
    jPanelWhite1.add(jPanelTitle1, null);
    jPanelWhite1.add(jPanelHeader1, null);
    lblF1.add(lblF2, null);
    jPanelWhite1.add(lblF1, null);
    jPanelWhite1.add(lblEsc, null);
    jPanelWhite1.add(pnlDatosLaboratorio, null);
    jPanelWhite1.add(lblCliente, null);
    pnlCodigoLaboratorio.add(lblCodigoLaboratorio_T, null);
    jPanelWhite1.add(pnlCodigoLaboratorio, null);
    this.getContentPane().add(jPanelWhite1, null);
  }



  private void btnDireccion_actionPerformed(ActionEvent e)
  {
  }

  private void txtDireccion_keyPressed(KeyEvent e)
  {
  }
  private void initialize() {
    initTableListaTarjetasClientes();
    FarmaVariables.vAceptar = false;
  }
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    
    System.out.println("dentro de la tarjeta " + VariablesCliente.vCodigo);  
    System.out.println("nombre " + VariablesCliente.vNombreCompleto);  
    lblCliente.setText (VariablesCliente.vNombreCompleto);
    buscaTarjetaCliente(VariablesCliente.vCodigo);
    tipoMaestro = ConstantsPtoVenta.LISTA_OPERADORES_TARJETA;
    System.out.println(VariablesCliente.vIndicadorCargaCliente);
    if (VariablesCliente.vIndicadorSeleccionTarjeta.equals(FarmaConstants.INDICADOR_S)){
      cargaCodigoTarjeta();
    } else {
      if(tblListadoTarjetas.getRowCount()>0)
        FarmaUtility.moveFocusJTable(tblListadoTarjetas);
      else
        FarmaUtility.moveFocus(txtCodigoOperador);
    }
  	
  }
  
   private void initTableListaTarjetasClientes()
  {
    tableModelListaTarjetasClientes = new FarmaTableModel(ConstantsCliente.columnsListaTarjetasClientes,ConstantsCliente.defaultValuesListaTarjetasClientes,0);
    FarmaUtility.initSimpleList(tblListadoTarjetas,tableModelListaTarjetasClientes,ConstantsCliente.columnsListaTarjetasClientes);
  }
  
   private void buscaTarjetaCliente(String pCodigo)
  {
    VariablesCliente.vCodigo = pCodigo;
    cargaTarjetasCliente();
  }
  
  private void cargaTarjetasCliente()
  {
    try
    {
      System.out.println(VariablesCliente.vCodigo);
      DBCliente.obtieneTarjetas_Cliente(tableModelListaTarjetasClientes,VariablesCliente.vCodigo);
      FarmaUtility.ordenar(tblListadoTarjetas, tableModelListaTarjetasClientes, 0, FarmaConstants.ORDEN_ASCENDENTE);
    } 
    catch(SQLException e)
    {
      e.printStackTrace();
      FarmaUtility.showMessage(this, "Error al listar Clientes Juridicos",null);
      cerrarVentana(false);
    }
  }
  
   private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  
   private void chkKeyPressed(KeyEvent e) {
   
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
        cerrarVentana(false);
		}
    else if (e.getKeyCode() == KeyEvent.VK_F11) 
    {
      guardaValoresTarjeta();
      if(!validaDatosTarjetas()) return;
      String resultado = "";
      System.out.println(VariablesCliente.vTipo_Accion);
      
      if(VariablesCliente.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_INSERTAR)){  
          resultado = agregaTarjetaCliente();
          System.out.println("resultado = " + resultado);
      }
      else if(VariablesCliente.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_MODIFICAR)){
          resultado = actualizaTarjetaCliente();
          System.out.println("resultado = " + resultado);
      }
      if(resultado.equalsIgnoreCase(ConstantsCliente.RESULTADO_GRABAR_CLIENTE_EXITO)){
          FarmaUtility.showMessage(this, "Se grabo Tarjeta con Exito", null);
          cargaTarjetasCliente();
          limpiar();
          FarmaUtility.moveFocus(tblListadoTarjetas);
      } 
      else FarmaUtility.showMessage(this, "Error al grabar la traejta del cliente", txtCodigoOperador);
    }
    else if (e.getKeyCode() == KeyEvent.VK_F9)
    {
    if (tblListadoTarjetas.getRowCount()>0)
    {
    VariablesCliente.vTipo_Accion=ConstantsCliente.ACCION_MODIFICAR;    
    
      if(VariablesCliente.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_MODIFICAR)){
     
          VariablesCliente.vCodigoOperA = ((String)tblListadoTarjetas.getValueAt(tblListadoTarjetas.getSelectedRow(),4)).trim();
          guardaTarjetaCliente();
          colocaInfoTarjeta();
          FarmaUtility.moveFocus(txtCodigoOperador);
        }
      }
    }
	}

  private void txtCodigoOperador(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      tipoMaestro = ConstantsPtoVenta.LISTA_OPERADORES_TARJETA;
      validaCodigo(txtCodigoOperador, txtDescripcionOperador, tipoMaestro);
    }
   
  chkKeyPressed(e);
  }

  private void txtNTarjeta_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtMes);
    }
  chkKeyPressed(e);
  }

  private void txtMes_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtAño);
    }
  chkKeyPressed(e);
  }

  private void txtAño_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtPropietario);
    }
  chkKeyPressed(e);
  }

  private void txtPropietario_keyPressed(KeyEvent e)
  {
   if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(txtCodigoOperador.isEnabled())
        FarmaUtility.moveFocus(txtCodigoOperador);
      else
        FarmaUtility.moveFocus(txtNTarjeta);
    }
  chkKeyPressed(e);
  }
  
  private void cargaListaMaestro(String pTipoMaestro, JTextField pJTextField_Cod, JTextField pJTextField_Desc)
  {
    VariablesPtoVenta.vTipoMaestro= pTipoMaestro;
    DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
    dlgListaMaestros.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
        pJTextField_Cod.setText(VariablesPtoVenta.vCodMaestro);
        pJTextField_Desc.setText(VariablesPtoVenta.vDescMaestro);
        guardaValoresMaestros(VariablesPtoVenta.vTipoMaestro, VariablesPtoVenta.vCodMaestro);
    }
  }
  
  private void guardaValoresMaestros(String pTipoMaestro, String pCodMestro)
  {
    if(pTipoMaestro.equalsIgnoreCase(ConstantsPtoVenta.LISTA_OPERADORES_TARJETA))
        VariablesPtoVenta.vCodOperador = pCodMestro;
    
  }

  private void txtCodigoOperador_keyReleased(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtNTarjeta);
    }
  }
  
   private void validaCodigo(JTextField pJTextField_Cod, JTextField pJTextField_Desc, String pTipoMaestro)
  {
    if(pJTextField_Cod.getText().trim().length() > 0)
    {
      codBusqueda = pJTextField_Cod.getText().trim();
      ArrayList myArray = new ArrayList();
      buscaCodigoListaMaestro(myArray);
      if(myArray.size() == 0)
      {
        FarmaUtility.showMessage(this,"Codigo No Encontrado",pJTextField_Cod);
        FarmaVariables.vAceptar = false;
      } else if(myArray.size() == 1)
      {
        String codigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
        String descripcion = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
        pJTextField_Cod.setText(codigo);
        pJTextField_Desc.setText(descripcion);
        VariablesPtoVenta.vCodMaestro = codigo;
        guardaValoresMaestros(tipoMaestro, VariablesPtoVenta.vCodMaestro);
        FarmaVariables.vAceptar = true;
      } else
      {
        FarmaUtility.showMessage(this,"Se encontro mas de un registro.Verificar!!!",pJTextField_Cod);
        FarmaVariables.vAceptar = false;
      }
    } else
    {
      cargaListaMaestro(pTipoMaestro,pJTextField_Cod, pJTextField_Desc);
    }
  }
  
  private void buscaCodigoListaMaestro(ArrayList pArrayList)
  {
    try
    {
      DBPtoVenta.buscaCodigoListaMaestro(pArrayList,tipoMaestro, codBusqueda);
    } 
    catch(SQLException e)
    {
      e.printStackTrace();
      System.out.println("error al buscar codigo en lista maestro : " + e);
    }
  }
  
   private void guardaValoresTarjeta()
  {
    VariablesCliente.vCodigoOperN= txtCodigoOperador.getText().trim().toUpperCase();
    VariablesCliente.vMes= txtMes.getText().trim().toUpperCase().toUpperCase();
    VariablesCliente.vAño= txtAño.getText().trim().toUpperCase().toUpperCase();
    VariablesCliente.vPropietario = txtPropietario.getText().trim().toUpperCase();
    VariablesCliente.vNumeroTarj = txtNTarjeta.getText().trim().toUpperCase();
    VariablesCliente.vNombre = txtPropietario.getText().trim().toUpperCase();
    VariablesCliente.vFechaVencConc = "01/" + VariablesCliente.vMes + "/" + VariablesCliente.vAño ;
    
    ArrayList myArray = new ArrayList();
    myArray.add(VariablesCliente.vCodigoOperN);
    myArray.add(VariablesCliente.vCodigoOperA);
    myArray.add(VariablesCliente.vMes);
    myArray.add(VariablesCliente.vAño);
    myArray.add(VariablesCliente.vPropietario);
    myArray.add(VariablesCliente.vNumeroTarj);
    myArray.add(VariablesCliente.vFechaVencConc);
    VariablesCliente.vArrayList_Cliente_Juridico.clear();
    VariablesCliente.vArrayList_Cliente_Juridico.add(myArray);
    System.out.println(myArray);
  }

  private boolean validaDatosTarjetas()
  {
    if(VariablesCliente.vCodigoOperN.equalsIgnoreCase(""))
    {
      FarmaUtility.showMessage(this, "Ingrese un Operador Valido", txtCodigoOperador);
      return false;
    }
    if(VariablesCliente.vNumeroTarj.equalsIgnoreCase(""))
    {
      FarmaUtility.showMessage(this, "Ingrese Numero de Tarjeta", txtNTarjeta);
      return false;
    }
    if(VariablesCliente.vMes.equalsIgnoreCase(""))
    {
      FarmaUtility.showMessage(this, "Ingrese un mes para la fecha de vencimiento", txtMes);
      return false;
    }
    if (Integer.parseInt(VariablesCliente.vMes) > 12 || (Integer.parseInt(VariablesCliente.vMes) < 1) )
    {
      FarmaUtility.showMessage(this, "Ingrese un mes valido para la fecha de vencimiento", txtMes);
      return false;
    }
    
    if(VariablesCliente.vAño.equalsIgnoreCase(""))
    {
      FarmaUtility.showMessage(this, "Ingrese un año para la fecha de vencimiento", txtAño);
      return false;
    }
    if(VariablesCliente.vPropietario.equalsIgnoreCase(""))
    {
      FarmaUtility.showMessage(this, "Ingrese un Propietario correcto", txtPropietario);
      return false;
    }
    return true;
  }
  
  private String agregaTarjetaCliente()
  {
    String resultado = "";
    try
    {
      resultado = DBCliente.agragaTarjetaCliente(VariablesCliente.vCodigoOperN,
                                                 VariablesCliente.vNumeroTarj,
                                                 VariablesCliente.vFechaVencConc,
                                                 VariablesCliente.vPropietario);
                                                  
      System.out.println(resultado);                                                
      return resultado;
    } 
    catch(SQLException sql)
    {
      sql.printStackTrace();
      return ConstantsCliente.RESULTADO_GRABAR_CLIENTE_ERROR;
    }
  }

  private void tblListadoTarjetas_keyReleased(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtCodigoOperador);
    }
    
  }
   private void guardaTarjetaCliente()
  {
    System.out.println(VariablesCliente.vArrayList_Cliente_Juridico.size());
    VariablesCliente.vArrayList_Cliente_Juridico.clear();
    VariablesCliente.vArrayList_Cliente_Juridico.add(tableModelListaTarjetasClientes.data.get(tblListadoTarjetas.getSelectedRow()));
    System.out.println(VariablesCliente.vArrayList_Cliente_Juridico);
    System.out.println(VariablesCliente.vArrayList_Cliente_Juridico.size());
  }
  
  private void colocaInfoTarjeta()
  {
      if(VariablesCliente.vArrayList_Cliente_Juridico.size() == 1){
      txtCodigoOperador.setText(((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(4)).trim());
      txtDescripcionOperador.setText(((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(0)).trim());
      txtNTarjeta.setText(((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(1)).trim());
      txtMes.setText(((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(5)).trim());
      txtAño.setText(((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(6)).trim());
      txtPropietario.setText(((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(2)).trim());
      }
    
  }
  
  private void guardavaloresTarjetaCobro()
  {
    if (tblListadoTarjetas.getRowCount()>0)
    {
      int fila = tblListadoTarjetas.getSelectedRow();
      VariablesCliente.vNumeroTarj = ((String)tblListadoTarjetas.getValueAt(fila,1)).trim();
      VariablesCliente.vFechaVencConc =  ((String)tblListadoTarjetas.getValueAt(fila,3)).trim();
      VariablesCliente.vPropietario = ((String)tblListadoTarjetas.getValueAt(fila,2)).trim();        
      VariablesCliente.vCodigoOperCobro = ((String)tblListadoTarjetas.getValueAt(fila,4)).trim();
      ArrayList myArray = new ArrayList();
      myArray.add(VariablesCliente.vNumeroTarj);
      myArray.add(VariablesCliente.vFechaVencConc);
      myArray.add(VariablesCliente.vPropietario);
      VariablesCliente.vArrayList_Valores_Tarjeta.clear();
      VariablesCliente.vArrayList_Valores_Tarjeta.add(myArray);
      System.out.println(myArray);     
   }
   else
   FarmaUtility.showMessage(this,"El cliente no tiene tarjeta asociada", txtCodigoOperador);
   return ;
  }

  private void tblListadoTarjetas_keyPressed(KeyEvent e)
  {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        e.consume();
        if(tblListadoTarjetas.getRowCount() == 0) return ;
        String codigoOper = ((String)tblListadoTarjetas.getValueAt(tblListadoTarjetas.getSelectedRow(),4)).trim();
        if(codigoOper.equals(VariablesCliente.vCodOperadorTarjeta)){
          guardavaloresTarjetaCobro();
          cerrarVentana(true);
        } else {
          FarmaUtility.showMessage(this,"La tarjeta Seleccionada no es la correcta ",null);
          jButtonLabel1.doClick();
        }
      }
      chkKeyPressed(e);
    }

  private void cargaCodigoTarjeta()
  {
    txtCodigoOperador.setText(""+ VariablesCliente.vCodOperadorTarjeta);
    txtCodigoOperador.setEnabled(false);
    validaCodigo(txtCodigoOperador, txtDescripcionOperador, tipoMaestro);
    if(tblListadoTarjetas.getRowCount()>0)
      FarmaUtility.moveFocusJTable(tblListadoTarjetas);
    else
      FarmaUtility.moveFocus(txtNTarjeta);
  }

  private String actualizaTarjetaCliente()
  {
    String resultado = "";
    try
    {
      resultado = DBCliente.actualizaTarjetaCliente(VariablesCliente.vCodigo,
                                                  VariablesCliente.vCodigoOperN,
                                                  VariablesCliente.vCodigoOperA,
                                                  VariablesCliente.vFechaVencConc,
                                                  VariablesCliente.vNumeroTarj,
                                                  VariablesCliente.vNombre);
      return resultado;
    } 
    catch(SQLException sql)
    {
      sql.printStackTrace();
      return ConstantsCliente.RESULTADO_GRABAR_CLIENTE_ERROR;
    }
  }

  private void jButtonLabel1_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblListadoTarjetas);
  }

  private void btnOperador_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtCodigoOperador);
  }

  private void btnNTarjeta_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtNTarjeta);
  }

  private void btnVencimeinto_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtMes);
  }

  private void btnPropietario_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtPropietario);
  }
  
  private void limpiar(){
    txtCodigoOperador.setText("");
    txtDescripcionOperador.setText("");
    txtNTarjeta.setText("");
    txtPropietario.setText("");
    txtMes.setText("");
    txtAño.setText("");
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
}