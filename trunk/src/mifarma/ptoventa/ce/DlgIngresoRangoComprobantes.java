package mifarma.ptoventa.ce;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import com.gs.mifarma.componentes.JPanelWhite;
import javax.swing.JPanel;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
 
 
import com.gs.mifarma.componentes.JLabelFunction;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import mifarma.common.*;
import java.util.*;
import mifarma.ptoventa.ce.*;
import mifarma.ptoventa.caja.reference.*; 
import mifarma.ptoventa.ce.reference.*;
import mifarma.ptoventa.reference.*;
import java.sql.*;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import com.gs.mifarma.componentes.JLabelWhite;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * Copyright (c) 2007 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresoRangoComprobantes.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      05.02.2007   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 * 
 */
public class DlgIngresoRangoComprobantes extends JDialog 
{
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */

  private Frame myParentFrame;
  private FarmaTableModel tableModelRangoComprobantes;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanel pnlDatos = new JPanel();
  private JButton btnTipoComp = new JButton();
  private JComboBox cmbTipoComp = new JComboBox();
  private JButton btnAdicionar = new JButton();
  private JPanel pnlDatos_T = new JPanel();
  private JLabel lblIngresoDatos_T = new JLabel();
  private JTextField txtRangoFin = new JTextField();
  private JScrollPane scrRangoComprobantes = new JScrollPane();
  private JPanel pnlRangoComprobantes = new JPanel();
  
  private JButton btnRanCompIngresados = new JButton();
  private JButton btnRanFin = new JButton();
  private JLabelFunction lblF5 = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JTable tblRangoComprobantes = new JTable();
  private JTextField txtRangoIni = new JTextField();
  private JButton btnRanIni = new JButton();
  private JButton btnNumSerie = new JButton();
  private JComboBox cmbNumSerie = new JComboBox();

  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgIngresoRangoComprobantes()
  {
    this(null, "", false);
  }

  public DlgIngresoRangoComprobantes(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(502, 381));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Ingreso de Rangos de Comprobantes por Serie");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    pnlDatos.setBounds(new Rectangle(15, 40, 460, 140));
    pnlDatos.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    pnlDatos.setBackground(Color.white);
    pnlDatos.setLayout(null);
    btnTipoComp.setText("Tipo de Comprobante :");
    btnTipoComp.setDefaultCapable(false);
    btnTipoComp.setRequestFocusEnabled(false);
    btnTipoComp.setBorderPainted(false);
    btnTipoComp.setFocusPainted(false);
    btnTipoComp.setContentAreaFilled(false);
    btnTipoComp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnTipoComp.setHorizontalAlignment(SwingConstants.RIGHT);
    btnTipoComp.setMnemonic('t');
    btnTipoComp.setFont(new Font("SansSerif", 1, 11));
    btnTipoComp.setBounds(new Rectangle(25, 15, 140, 20));
    btnTipoComp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnTipoComp_actionPerformed(e);
        }
      });
    cmbTipoComp.setBounds(new Rectangle(185, 15, 90, 20));
    cmbTipoComp.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          cmbTipoComp_itemStateChanged(e);
        }
      });
    cmbTipoComp.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          cmbTipoComp_keyPressed(e);
        }
      });
    btnAdicionar.setText("Adicionar");
    btnAdicionar.setFont(new Font("SansSerif", 0, 11));
    btnAdicionar.setMnemonic('a');
    btnAdicionar.setRequestFocusEnabled(false);
    btnAdicionar.setBounds(new Rectangle(295, 95, 120, 30));
    btnAdicionar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnAdicionar_actionPerformed(e);
                    }
      });
    pnlDatos_T.setBounds(new Rectangle(15, 15, 460, 25));
    pnlDatos_T.setBackground(new Color(255, 130, 14));
    pnlDatos_T.setBorder(BorderFactory.createTitledBorder(""));
    pnlDatos_T.setLayout(null);
    lblIngresoDatos_T.setText("Ingreso de Datos");
    lblIngresoDatos_T.setBounds(new Rectangle(10, 5, 120, 15));
    lblIngresoDatos_T.setFont(new Font("SansSerif", 1, 11));
    lblIngresoDatos_T.setForeground(Color.white);
    txtRangoFin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    txtRangoFin.setBounds(new Rectangle(185, 105, 90, 20));
    txtRangoFin.setDocument(new FarmaLengthText(7));
    txtRangoFin.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtRangoFin_keyPressed(e);
        }

        public void keyTyped(KeyEvent e)
        {
          txtRangoFin_keyTyped(e);
        }
      });
    scrRangoComprobantes.setBounds(new Rectangle(15, 210, 460, 100));
    scrRangoComprobantes.setFont(new Font("SansSerif", 0, 11));
    scrRangoComprobantes.setBackground(new Color(255, 130, 14));
    pnlRangoComprobantes.setBounds(new Rectangle(15, 185, 460, 25));
    pnlRangoComprobantes.setFont(new Font("SansSerif", 0, 11));
    pnlRangoComprobantes.setBackground(new Color(255, 130, 14));
    pnlRangoComprobantes.setLayout(null);
    btnRanCompIngresados.setText("Rango de Comprobantes Ingresados :");
    btnRanCompIngresados.setFont(new Font("SansSerif", 1, 11));
    btnRanCompIngresados.setForeground(Color.white);
    btnRanCompIngresados.setHorizontalAlignment(SwingConstants.LEFT);
    btnRanCompIngresados.setMnemonic('r');
    btnRanCompIngresados.setRequestFocusEnabled(false);
    btnRanCompIngresados.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnRanCompIngresados.setBackground(new Color(255, 130, 14));
    btnRanCompIngresados.setContentAreaFilled(false);
    btnRanCompIngresados.setDefaultCapable(false);
    btnRanCompIngresados.setBorderPainted(false);
    btnRanCompIngresados.setFocusPainted(false);
    btnRanCompIngresados.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRanCompIngresados_actionPerformed(e);
        }
      });
    btnRanFin.setText("Rango Final :");
    btnRanFin.setDefaultCapable(false);
    btnRanFin.setRequestFocusEnabled(false);
    btnRanFin.setBorderPainted(false);
    btnRanFin.setFocusPainted(false);
    btnRanFin.setContentAreaFilled(false);
    btnRanFin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnRanFin.setHorizontalAlignment(SwingConstants.RIGHT);
    btnRanFin.setMnemonic('f');
    btnRanFin.setFont(new Font("SansSerif", 1, 11));
    btnRanFin.setBounds(new Rectangle(80, 105, 85, 20));
    btnRanFin.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRanFin_actionPerformed(e);
        }
      });
    lblF5.setText("[ F5 ] Borrar Rango");
    lblF5.setBounds(new Rectangle(15, 320, 130, 20));
    lblF11.setBounds(new Rectangle(230, 320, 115, 20));
    lblF11.setText("[ F11] Aceptar");
    lblEsc.setBounds(new Rectangle(360, 320, 115, 20));
    lblEsc.setText("[ ESC ] Salir");
    tblRangoComprobantes.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblRangoComprobantes_keyPressed(e);
        }
      });
    txtRangoIni.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    txtRangoIni.setBounds(new Rectangle(185, 75, 90, 20));
    txtRangoIni.setDocument(new FarmaLengthText(7));
    txtRangoIni.addKeyListener(new KeyAdapter()
      {


        public void keyPressed(KeyEvent e)
        {
          txtRangoIni_keyPressed(e);
        }

        public void keyTyped(KeyEvent e)
        {
          txtRangoIni_keyTyped(e);
        }
      });
    btnRanIni.setText("Rango Inicial :");
    btnRanIni.setDefaultCapable(false);
    btnRanIni.setRequestFocusEnabled(false);
    btnRanIni.setBorderPainted(false);
    btnRanIni.setFocusPainted(false);
    btnRanIni.setContentAreaFilled(false);
    btnRanIni.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnRanIni.setHorizontalAlignment(SwingConstants.RIGHT);
    btnRanIni.setMnemonic('i');
    btnRanIni.setFont(new Font("SansSerif", 1, 11));
    btnRanIni.setBounds(new Rectangle(80, 75, 85, 20));
    btnRanIni.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRanIni_actionPerformed(e);
        }
      });
    btnNumSerie.setText("Numero de Serie :");
    btnNumSerie.setDefaultCapable(false);
    btnNumSerie.setRequestFocusEnabled(false);
    btnNumSerie.setBorderPainted(false);
    btnNumSerie.setFocusPainted(false);
    btnNumSerie.setContentAreaFilled(false);
    btnNumSerie.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnNumSerie.setHorizontalAlignment(SwingConstants.RIGHT);
    btnNumSerie.setMnemonic('s');
    btnNumSerie.setFont(new Font("SansSerif", 1, 11));
    btnNumSerie.setBounds(new Rectangle(35, 45, 130, 20));
    btnNumSerie.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNumSerie_actionPerformed(e);
        }
      });
    cmbNumSerie.setBounds(new Rectangle(185, 45, 90, 20));
    cmbNumSerie.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          cmbNumSerie_keyPressed(e);
        }
      });
    scrRangoComprobantes.getViewport();
    scrRangoComprobantes.getViewport();
    pnlDatos.add(cmbNumSerie, null);
    pnlDatos.add(btnNumSerie, null);
    pnlDatos.add(btnRanIni, null);
    pnlDatos.add(txtRangoIni, null);
    pnlDatos.add(btnRanFin, null);
    pnlDatos.add(txtRangoFin, null);
    pnlDatos.add(btnTipoComp, null);
    pnlDatos.add(cmbTipoComp, null);
    pnlDatos.add(btnAdicionar, null);
    pnlDatos_T.add(lblIngresoDatos_T, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(lblF5, null);
    pnlRangoComprobantes.add(btnRanCompIngresados);
    jContentPane.add(pnlRangoComprobantes, null);
    scrRangoComprobantes.getViewport().add(tblRangoComprobantes, null);
    jContentPane.add(scrRangoComprobantes, null);
    jContentPane.add(pnlDatos_T, null);
    jContentPane.add(pnlDatos, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */
  
  private void initialize()
  {
    initTableRangoComprobantes();
    initComboTipoComp();
    cargaComboSerie();
    FarmaVariables.vAceptar=false;
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */
  private void initTableRangoComprobantes()
  {
    tableModelRangoComprobantes = new FarmaTableModel(ConstantsCajaElectronica.columsListaRangoComprobantes,ConstantsCajaElectronica.defaultListaRangoComprobantes,0);
    FarmaUtility.initSimpleList(tblRangoComprobantes,tableModelRangoComprobantes,ConstantsCajaElectronica.columsListaRangoComprobantes);
  }
  
  private void cargaListaRangoComprobantesMovCaja()
  {
    try
    {
      DBCajaElectronica.cargaListaRangoCompMovCaja(tableModelRangoComprobantes, VariablesCajaElectronica.vSecMovCaja);
      if(tblRangoComprobantes.getRowCount() > 0)
        FarmaUtility.ordenar(tblRangoComprobantes,tableModelRangoComprobantes,4,FarmaConstants.ORDEN_ASCENDENTE);
    } catch (SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error al listar los rangos de comprobantes del movimiento de caja.\n" + sql.getMessage(),cmbTipoComp);
    }
  }
  
  private void cargaListaRangoComprobantesCierreDia()
  {
    try
    {
      DBCajaElectronica.cargaListaRangoCompCierreDia(tableModelRangoComprobantes, VariablesCajaElectronica.vFechaCierreDia);
      if(tblRangoComprobantes.getRowCount() > 0)
        FarmaUtility.ordenar(tblRangoComprobantes,tableModelRangoComprobantes,4,FarmaConstants.ORDEN_ASCENDENTE);
    } catch (SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error al listar los rangos de comprobantes del cierre de dia.\n" + sql.getMessage(),cmbTipoComp);
    }
  }
  
  private void initComboTipoComp() {
		cmbTipoComp.removeAllItems();
		ArrayList parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		FarmaLoadCVL.loadCVLFromSP(cmbTipoComp,
                               "cmbTipoComp",
                               "PTOVENTA_CE_LMR.CE_LISTA_TIP_COMP(?)",
                               parametros,
                               true,
                               true);
	}
  
  private void initComboSerie(String pTipComp) {
		cmbNumSerie.removeAllItems();
		ArrayList parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(pTipComp);
		FarmaLoadCVL.loadCVLFromSP(cmbNumSerie,
                               "cmbNumSerie",
                               "PTOVENTA_CE_LMR.CE_LISTA_SERIES_TIP_DOC(?,?,?)",
                               parametros,
                               true,
                               true);
	}
  
  private void cargaComboSerie()
  {
    String tipComp = FarmaLoadCVL.getCVLCode("cmbTipoComp", cmbTipoComp.getSelectedIndex());
    initComboSerie(tipComp);
  }
    
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */
  

  private void btnTipoComp_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(cmbTipoComp);
  }

  private void btnAdicionar_actionPerformed(ActionEvent e)
  {
    adicionaRangoComprobante();
  }

  private void tblRangoComprobantes_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void btnRanCompIngresados_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocusJTable(tblRangoComprobantes);
  }

  private void btnCantidad_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtRangoFin);
  }

  private void cmbTipoComp_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      cargaComboSerie();
      FarmaUtility.moveFocus(cmbNumSerie);
    } else chkKeyPressed(e);
  }
  
  private void cmbNumSerie_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtRangoIni);
    } else chkKeyPressed(e);
  }

  private void txtMontoPagado_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(cmbTipoComp);
    } else
      chkKeyPressed(e);
  }
  
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    if(VariablesCajaElectronica.vTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CT))
    {
      cargaListaRangoComprobantesMovCaja();
    } else if(VariablesCajaElectronica.vTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CD))
    {
      cargaListaRangoComprobantesCierreDia();
    }
    if( VariablesCajaElectronica.vIndCompValidos.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ){
      editaIngresoComprobantes(false);
      FarmaUtility.moveFocusJTable(tblRangoComprobantes);
    } else FarmaUtility.moveFocus(cmbTipoComp);
  }
  
  private void txtRangoIni_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtRangoFin);
    } else chkKeyPressed(e);
  }

  private void btnRanIni_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtRangoIni);
  }

  private void txtRangoIni_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtRangoIni,e);
  }

  private void btnNumSerie_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(cmbNumSerie);
  }
  
  private void btnRanFin_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtRangoFin);
  }
  
  private void txtRangoFin_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      btnAdicionar.doClick();
    } else
      chkKeyPressed(e);  
  }
  
  private void txtRangoFin_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtRangoFin,e);
  }
  
  private void cmbTipoComp_itemStateChanged(ItemEvent e)
  {
    cargaComboSerie();
  }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            if (lblF11.isVisible()) {
                if (VariablesCajaElectronica.vIsVentanaCierreDiaOpen.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                    if (UtilityCaja.validaCompDesfase(VariablesCajaElectronica.vFechaCierreDia.trim())) {
                        FarmaUtility.showMessage(this, 
                                                 "Existen comprobantes desfasados.\nNo puede ingresar los comprobantes.", 
                                                 txtRangoIni);
                        return;
                    }
                }
                aceptaIngresoRangoComprobantes();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (lblF5.isVisible()) {
                eliminaRangoComprobante();
            }
        }
    }
  
  private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */

  private void eliminaRegistroSeleccionado()
  {
    int seleccion = tblRangoComprobantes.getSelectedRow();
    tableModelRangoComprobantes.deleteRow(seleccion);
    tableModelRangoComprobantes.fireTableDataChanged();
    if(seleccion == 0)
     FarmaUtility.moveFocus(tblRangoComprobantes);
    else{
     FarmaGridUtils.showCell(tblRangoComprobantes,seleccion-1,0);
     FarmaUtility.moveFocus(tblRangoComprobantes);
    }
  }
  
  private void editaIngresoComprobantes(boolean pEditable)
  {
    cmbNumSerie.setEnabled(pEditable);
    cmbTipoComp.setEnabled(pEditable);
    txtRangoIni.setEditable(pEditable);
    txtRangoFin.setEditable(pEditable);
    btnAdicionar.setEnabled(pEditable);
    lblF5.setVisible(pEditable);
    lblF11.setVisible(pEditable);
  }
  
  private void adicionaRangoComprobante()
  {
    obtieneDatosRangoComprobante();
    if(!validaInfoRangoComprobantes()) return;
    if(!validaTipoSerieComp())
    {
      FarmaUtility.showMessage(this,"El tipo y numero de serie del comprobante ya existen para el Cierre. Verifique!!!", cmbTipoComp);
      return;
    }
    operaListaRangoComprobantes();
    limpiaVariablesRangoComprobantes();
    limpiaTextoRangoComprobantes();
    FarmaUtility.moveFocus(cmbTipoComp);
  }
  
  private void obtieneDatosRangoComprobante()
  {
    
    VariablesCajaElectronica.vTipCompGeneral = FarmaLoadCVL.getCVLCode("cmbTipoComp", cmbTipoComp.getSelectedIndex());
    VariablesCajaElectronica.vNumSerieGeneral = FarmaLoadCVL.getCVLCode("cmbNumSerie", cmbNumSerie.getSelectedIndex());
    VariablesCajaElectronica.vNumeroMinGeneral = txtRangoIni.getText().trim();
    VariablesCajaElectronica.vNumeroMaxGeneral = txtRangoFin.getText().trim();
  }
  
  private boolean validaInfoRangoComprobantes()
  {
    if(VariablesCajaElectronica.vTipCompGeneral.trim().equalsIgnoreCase("") || 
      VariablesCajaElectronica.vTipCompGeneral.trim().length() <= 0)
    {
      FarmaUtility.showMessage(this, "Ingrese un tipo de comprobante.", cmbTipoComp);
      return false;
    } else if(VariablesCajaElectronica.vNumSerieGeneral.trim().equalsIgnoreCase("") || 
      VariablesCajaElectronica.vNumSerieGeneral.trim().length() <= 0)
    {
      FarmaUtility.showMessage(this, "Ingrese una serie de comprobante.", cmbNumSerie);
      return false;
    } else if(VariablesCajaElectronica.vNumeroMinGeneral.trim().equalsIgnoreCase("") || 
      VariablesCajaElectronica.vNumeroMinGeneral.trim().length() <= 0)
    {
      FarmaUtility.showMessage(this, "Ingrese un rango inicial.", txtRangoIni);
      return false;
    } else if(VariablesCajaElectronica.vNumeroMaxGeneral.trim().equalsIgnoreCase("") || 
      VariablesCajaElectronica.vNumeroMaxGeneral.trim().length() <= 0)
    {
      FarmaUtility.showMessage(this, "Ingrese un rango final.", txtRangoFin);
      return false;
    }
    return true;
  }
  
  private void operaListaRangoComprobantes()
  {
    ArrayList myArray = new ArrayList();
    myArray.add(FarmaLoadCVL.getCVLDescription("cmbTipoComp", VariablesCajaElectronica.vTipCompGeneral));
    myArray.add(VariablesCajaElectronica.vNumSerieGeneral);
    myArray.add(FarmaUtility.completeWithSymbol(VariablesCajaElectronica.vNumeroMinGeneral, 7, "0", "I"));
    myArray.add(FarmaUtility.completeWithSymbol(VariablesCajaElectronica.vNumeroMaxGeneral, 7, "0", "I"));
    myArray.add(VariablesCajaElectronica.vTipCompGeneral);
    myArray.add(FarmaConstants.INDICADOR_N);
    tableModelRangoComprobantes.data.add(myArray);
    tableModelRangoComprobantes.fireTableDataChanged();
    
      System.out.println("Datos-->"+myArray);
  }
  
  private boolean validaTipoSerieComp()
  {
    if(tblRangoComprobantes.getRowCount() <= 0) return true;
    String tipComp = VariablesCajaElectronica.vTipCompGeneral;
    String numSerie = VariablesCajaElectronica.vNumSerieGeneral;
    for(int i=0; i<tblRangoComprobantes.getRowCount(); i++)
    {
      String tipCompTmp = ((String)tblRangoComprobantes.getValueAt(i,4)).trim();
      String numSerieTmp = ((String)tblRangoComprobantes.getValueAt(i,1)).trim();
      if(tipComp.equalsIgnoreCase(tipCompTmp) && 
        numSerie.equalsIgnoreCase(numSerieTmp)) 
        return false;
    }
    return true;
  }
  
  private void limpiaVariablesRangoComprobantes()
  {
    VariablesCajaElectronica.vTipCompGeneral = "";
    VariablesCajaElectronica.vNumSerieGeneral = "";
    VariablesCajaElectronica.vNumeroMinGeneral = "";
    VariablesCajaElectronica.vNumeroMaxGeneral = "";
  }
  
  private void limpiaTextoRangoComprobantes()
  {
    txtRangoIni.setText("");
    txtRangoFin.setText("");
  }
  
  private void guardaDatosListaRangoComprobantes(int pRow)
  {
    VariablesCajaElectronica.vTipCompGeneral = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 4);
    VariablesCajaElectronica.vNumSerieGeneral = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 1);
    VariablesCajaElectronica.vNumeroMinGeneral = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 2);
    VariablesCajaElectronica.vNumeroMaxGeneral = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 3);
    VariablesCajaElectronica.vIndRangoGrabado = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 5);
  }
  
  private void eliminaRangoComprobante()
  {
    if(tblRangoComprobantes.getRowCount() <= 0) return;
    guardaDatosListaRangoComprobantes(tblRangoComprobantes.getSelectedRow());
    if(VariablesCajaElectronica.vIndRangoGrabado.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
    {
      System.out.println("Elimina registro Seleccionado");
      eliminaRegistroSeleccionado();
    } else if(VariablesCajaElectronica.vIndRangoGrabado.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
      try
      {
        if (FarmaUtility.rptaConfirmDialog(this,"¿El sistema eliminara el rango de comprobante fisicamente. Desea Continuar?"))
        {
          if(VariablesCajaElectronica.vTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CT))
          {
            DBCajaElectronica.eliminaRangoCompMovCaja(VariablesCajaElectronica.vSecMovCaja,
                                                      VariablesCajaElectronica.vTipCompGeneral,
                                                      VariablesCajaElectronica.vNumSerieGeneral);
          } else if(VariablesCajaElectronica.vTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CD))
          {
            DBCajaElectronica.eliminaRangoCompCierreDia(VariablesCajaElectronica.vFechaCierreDia,
                                                        VariablesCajaElectronica.vTipCompGeneral,
                                                        VariablesCajaElectronica.vNumSerieGeneral);
          }
          FarmaUtility.aceptarTransaccion();
          int seleccion = tblRangoComprobantes.getSelectedRow();
          tableModelRangoComprobantes.deleteRow(seleccion);
          tableModelRangoComprobantes.fireTableDataChanged();
          System.out.println("Elimina registro Seleccionado BD");
          FarmaUtility.moveFocus(cmbTipoComp);
        } 
      } catch (SQLException sql)
      {
        FarmaUtility.liberarTransaccion();
        sql.printStackTrace();
        FarmaUtility.showMessage(this,"Ocurrio un error al eliminar fisicamente \n " + sql.getMessage(),cmbTipoComp);
      }
    }
  }
  
  private boolean validaRangoComprobantesIngresado()
  {
    boolean result = true;
    boolean encontroTipoSerie = false;
    ArrayList myArray = UtilityCajaElectronica.obtieneRangoComprobantesCorrectos(this, VariablesCajaElectronica.vTipoIngresoComprobantes);
    int cantRangosIngresados = tblRangoComprobantes.getRowCount();
    int cantRangosCorrectos = myArray.size();
    for(int i=0; i<tblRangoComprobantes.getRowCount(); i++)
    {
      encontroTipoSerie = false;
      String nombretipoCompTable = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, i, 0);
      String tipoCompTable = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, i, 4);
      String numSerieTable = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, i, 1);
      String rangoIniTable = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, i, 2);
      String rangoFinTable = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, i, 3);
      for(int j=0; j<myArray.size(); j++)
      {
        String tipoCompArray = FarmaUtility.getValueFieldArrayList(myArray, j, 0);
        String numSerieArray = FarmaUtility.getValueFieldArrayList(myArray, j, 1);
        String rangoIniArray = FarmaUtility.getValueFieldArrayList(myArray, j, 2);
        String rangoFinArray = FarmaUtility.getValueFieldArrayList(myArray, j, 3);
        if( tipoCompTable.equals(tipoCompArray) &&
            numSerieTable.equals(numSerieArray) &&
            rangoIniTable.equals(rangoIniArray) &&
            rangoFinTable.equals(rangoFinArray) )
        {
          encontroTipoSerie = true;
          break;
        }
      }
      if(!encontroTipoSerie)
      {
        result = encontroTipoSerie;
        FarmaUtility.showMessage(this, "El rango de " + nombretipoCompTable + " con serie " + numSerieTable + " no coinciden con los del sistema. Verifique!!!", cmbTipoComp);
        return result;
      }
    }
    if( cantRangosIngresados != cantRangosCorrectos )
    {
      result = false;
      FarmaUtility.showMessage(this, "La cantidad de rangos de comprobantes ingresados\nno coinciden con los del sistema. Verifique!!!", cmbTipoComp);
      return result;
    }
    return result;
  }
  
  private void ingresaRangosComprobantes()
  {
    try
    {
      for(int i=0; i<tblRangoComprobantes.getRowCount(); i++)
      {
        guardaInformacionRangoComprobantes(i);
        if(VariablesCajaElectronica.vTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CT) &&
           VariablesCajaElectronica.vIndRangoGrabado.equalsIgnoreCase(FarmaConstants.INDICADOR_N) )
        {
          DBCajaElectronica.insertaRangoCompMovCaja(VariablesCajaElectronica.vSecMovCaja);
        } else if(VariablesCajaElectronica.vTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CD))
        {
          VariablesCajaElectronica.vRangoCompIngresadosCierreDia = tableModelRangoComprobantes.data;
          break;
          //DBCajaElectronica.insertaRangoCompCierreDia(VariablesCajaElectronica.vFechaCierreDia);
        }
      }
      VariablesCajaElectronica.vIndCompValidos = FarmaConstants.INDICADOR_S;
      FarmaUtility.aceptarTransaccion();
      FarmaUtility.showMessage(this,"Los rangos de comprobantes se registraron correctamente",null);
    } catch (SQLException sql)
    {
      VariablesCajaElectronica.vIndCompValidos = FarmaConstants.INDICADOR_N;
      FarmaUtility.liberarTransaccion();
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrio un error al insertar los rangos de comprobantes.\n " + sql.getMessage(),cmbTipoComp);
    } finally
    {
      if(VariablesCajaElectronica.vTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CT))
      {
        UtilityCajaElectronica.actualizaIndicadorCompValidosCT(this, VariablesCajaElectronica.vSecMovCaja, VariablesCajaElectronica.vIndCompValidos);
      } else if(VariablesCajaElectronica.vTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CD))
      {
        UtilityCajaElectronica.actualizaIndicadorCompValidosCD(this, VariablesCajaElectronica.vFechaCierreDia, VariablesCajaElectronica.vIndCompValidos);
      }
    }
  }
  
  private void guardaInformacionRangoComprobantes(int pRow)
  {
    if(VariablesCajaElectronica.vTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CT))
    {
      VariablesCajaElectronica.vTipCompUsuario = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 4);
      VariablesCajaElectronica.vNumSerieUsuario = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 1);
      VariablesCajaElectronica.vNumeroMinUsuario = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 2);
      VariablesCajaElectronica.vNumeroMaxUsuario = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 3);
      VariablesCajaElectronica.vIndRangoGrabado = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 5);
    } else if(VariablesCajaElectronica.vTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CD))
    {
      VariablesCajaElectronica.vTipCompDia = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 4);
      VariablesCajaElectronica.vNumSerieDia = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 1);
      VariablesCajaElectronica.vNumeroMinDia = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 2);
      VariablesCajaElectronica.vNumeroMaxDia = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 3);
      VariablesCajaElectronica.vIndRangoGrabado = FarmaUtility.getValueFieldJTable(tblRangoComprobantes, pRow, 5);
    }
  }
  
  private void aceptaIngresoRangoComprobantes()
  {
    if(validaRangoComprobantesIngresado())
    {
      ingresaRangosComprobantes();
      cerrarVentana(true);
    }
  }
  
}