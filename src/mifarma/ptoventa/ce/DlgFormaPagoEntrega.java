package mifarma.ptoventa.ce;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelWhite;

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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.fondoSencillo.DlgDevolverMontoFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.ConstantsFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.DBFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.UtilityFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.VariablesFondoSencillo;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.caja.reference.UtilitySobres;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.UtilityCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import mifarma.ptoventa.reference.VariablesPtoVenta;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFormaPagoEntrega.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * PAULO      31.07.2006   Creación<br>
 * <br>
 * @author Paulo Cesar Ameghino Rojas<br>
 * @version 1.0<br>
 *
 */
public class DlgFormaPagoEntrega extends JDialog 
{
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */
  private static final Log log = LogFactory.getLog(DlgFormaPagoEntrega.class);

  Frame myParentFrame;
  FarmaTableModel tableModelFormasPago;
  FarmaTableModel tableModelDetallePago;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanel jPanel1 = new JPanel();
  private JButton btnMoneda = new JButton();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JPanel jPanel2 = new JPanel();
  private JButton btnFormaPago = new JButton();
  private JComboBox cmbMoneda = new JComboBox();
  private JTextField txtMontoPagado = new JTextField();
  private JButton btnAdicionar = new JButton();
  private JPanel jPanel3 = new JPanel();
  private JLabel lblRazSoc_T = new JLabel();
  private JTextField txtCantidad = new JTextField();
  private JPanel pnlTotales = new JPanel();
  private XYLayout xYLayout5 = new XYLayout();
  private JLabel lblSaldoT = new JLabel();
  private JLabel lblTotal = new JLabel();
  private JScrollPane scrDetallePago = new JScrollPane();
  private JPanel pnlDetallePago = new JPanel();
  private XYLayout xYLayout1 = new XYLayout();
  private JButton btnDetallePago = new JButton();  
  private JButton btnCantidad = new JButton();
  private JButton btnMonto = new JButton();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JTable tblFormasPago = new JTable();
  private JLabelFunction jLabelFunction1 = new JLabelFunction();
  private JLabelFunction jLabelFunction2 = new JLabelFunction();
  private JTable tblDetallePago = new JTable();
  private JLabelWhite lblMontoTipoCambio = new JLabelWhite();
  private JLabelWhite lblTipoCambio = new JLabelWhite();
  private JTextField txtLote = new JTextField();
  private JButton btnLote = new JButton();
  private String[] INDICADORES_CODIGO = { "S", "N" };
  private String[] INDICADORES_DESCRIPCION = { "SI","NO" };
  private int COL_COD_FPAGO = 0,COL_DESC_FPAGO =1,COL_CANT = 2,
              COL_MONEDA = 3,COL_MONTO =4,COL_TOTAL = 5,COL_LOTE=6,
              //COL_IND_SOBRE_BD = 7,
              COL_IND_SOBRE = 7,
              COL_SOBRE=8,
              COL_SEC_CAJA=9,
              COL_MON_2 = 10,COL_IND = 11,COL_IND_AUTOMATICO = 12,
              COL_SEC_FPAGO = 13;//,COL_IND_SOBRE = 14;
              
    private JComboBox cmbSobre = new JComboBox();
    
    private String CMB_SOBRE = "CMBSOBRE";
    private JLabel lblSObre = new JLabel();
    private JTextArea mensaje = new JTextArea();
    private JLabelOrange lblmensaje = new JLabelOrange();
    private JLabelFunction btnReimp = new JLabelFunction();
    /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgFormaPagoEntrega()
  {
    this(null, "", false);
  }

  public DlgFormaPagoEntrega(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(768, 546));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Entregas por Forma de Pago");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    jPanel1.setBounds(new Rectangle(15, 40, 735, 230));
    jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jPanel1.setBackground(Color.white);
    jPanel1.setLayout(null);
    btnMoneda.setText("Moneda :");
    btnMoneda.setDefaultCapable(false);
    btnMoneda.setRequestFocusEnabled(false);
    btnMoneda.setBorderPainted(false);
    btnMoneda.setFocusPainted(false);
    btnMoneda.setContentAreaFilled(false);
    btnMoneda.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnMoneda.setHorizontalAlignment(SwingConstants.RIGHT);
    btnMoneda.setMnemonic('n');
    btnMoneda.setFont(new Font("SansSerif", 0, 11));
    btnMoneda.setBounds(new Rectangle(430, 75, 60, 20));
    btnMoneda.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnMoneda_actionPerformed(e);
        }
      });
    jScrollPane1.setBounds(new Rectangle(25, 30, 295, 190));
    jScrollPane1.setBackground(new Color(255, 130, 14));
    jPanel2.setBounds(new Rectangle(25, 10, 295, 20));
    jPanel2.setBackground(new Color(255, 130, 14));
    jPanel2.setLayout(null);
    btnFormaPago.setText("Formas de Pago");
    btnFormaPago.setDefaultCapable(false);
    btnFormaPago.setRequestFocusEnabled(false);
    btnFormaPago.setBorderPainted(false);
    btnFormaPago.setFocusPainted(false);
    btnFormaPago.setContentAreaFilled(false);
    btnFormaPago.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnFormaPago.setHorizontalAlignment(SwingConstants.LEFT);
    btnFormaPago.setMnemonic('F');
    btnFormaPago.setFont(new Font("SansSerif", 1, 11));
    btnFormaPago.setForeground(Color.white);
    btnFormaPago.setBounds(new Rectangle(5, 0, 105, 20));
    btnFormaPago.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnFormaPago_actionPerformed(e);
        }
      });
    cmbMoneda.setBounds(new Rectangle(510, 75, 90, 20));
    cmbMoneda.setEnabled(false);
    cmbMoneda.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          cmbMoneda_keyPressed(e);
        }
      });
    txtMontoPagado.setText("0.00");
    txtMontoPagado.setHorizontalAlignment(JTextField.RIGHT);
    txtMontoPagado.setBounds(new Rectangle(510, 105, 90, 20));
    txtMontoPagado.setEnabled(false);
    txtMontoPagado.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        txtMontoPagado1_keyPressed(e);
                    }
      });
    btnAdicionar.setText("Adicionar");
    btnAdicionar.setFont(new Font("SansSerif", 0, 11));
    btnAdicionar.setMnemonic('a');
    btnAdicionar.setRequestFocusEnabled(false);
    btnAdicionar.setBounds(new Rectangle(455, 185, 150, 25));
    btnAdicionar.setEnabled(false);
    btnAdicionar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnAdicionar_actionPerformed(e);
                    }
      });
    jPanel3.setBounds(new Rectangle(15, 15, 735, 25));
    jPanel3.setBackground(new Color(255, 130, 14));
    jPanel3.setBorder(BorderFactory.createTitledBorder(""));
    jPanel3.setLayout(null);
    lblRazSoc_T.setText("Seleccion de Forma de Pago");
    lblRazSoc_T.setBounds(new Rectangle(10, 5, 180, 15));
    lblRazSoc_T.setFont(new Font("SansSerif", 1, 12));
    lblRazSoc_T.setForeground(Color.white);
    txtCantidad.setText("0");
    txtCantidad.setHorizontalAlignment(JTextField.RIGHT);
    txtCantidad.setBounds(new Rectangle(510, 45, 90, 20));
    txtCantidad.setEnabled(false);
    txtCantidad.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtMontoPagado_keyPressed(e);
        }
      });
    pnlTotales.setBounds(new Rectangle(15, 445, 735, 40));
    pnlTotales.setFont(new Font("SansSerif", 0, 11));
    pnlTotales.setBackground(new Color(43, 141, 39));
    pnlTotales.setLayout(xYLayout5);
    lblSaldoT.setText("TOTAL A ENTREGAR :  S/.");
    lblSaldoT.setFont(new Font("SansSerif", 1, 13));
    lblSaldoT.setForeground(Color.white);
    lblTotal.setText("0.00");
    lblTotal.setFont(new Font("SansSerif", 1, 13));
    lblTotal.setForeground(Color.white);
    lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
    scrDetallePago.setBounds(new Rectangle(15, 320, 735, 125));
    scrDetallePago.setFont(new Font("SansSerif", 0, 11));
    scrDetallePago.setBackground(new Color(255, 130, 14));
    pnlDetallePago.setBounds(new Rectangle(15, 295, 735, 25));
    pnlDetallePago.setFont(new Font("SansSerif", 0, 11));
    pnlDetallePago.setBackground(new Color(255, 130, 14));
    pnlDetallePago.setLayout(xYLayout1);
    btnDetallePago.setText("Entregas Realizadas :");
    btnDetallePago.setFont(new Font("SansSerif", 1, 11));
    btnDetallePago.setForeground(Color.white);
    btnDetallePago.setHorizontalAlignment(SwingConstants.LEFT);
    btnDetallePago.setMnemonic('e');
    btnDetallePago.setRequestFocusEnabled(false);
    btnDetallePago.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnDetallePago.setBackground(new Color(255, 130, 14));
    btnDetallePago.setContentAreaFilled(false);
    btnDetallePago.setDefaultCapable(false);
    btnDetallePago.setBorderPainted(false);
    btnDetallePago.setFocusPainted(false);
    btnDetallePago.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDetallePago_actionPerformed(e);
        }
      });
    btnCantidad.setText("Cantidad :");
    btnCantidad.setDefaultCapable(false);
    btnCantidad.setRequestFocusEnabled(false);
    btnCantidad.setBorderPainted(false);
    btnCantidad.setFocusPainted(false);
    btnCantidad.setContentAreaFilled(false);
    btnCantidad.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
    btnCantidad.setMnemonic('c');
    btnCantidad.setFont(new Font("SansSerif", 0, 11));
    btnCantidad.setBounds(new Rectangle(430, 45, 60, 20));
    btnCantidad.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCantidad_actionPerformed(e);
        }
      });
    btnMonto.setText("Monto :");
    btnMonto.setDefaultCapable(false);
    btnMonto.setRequestFocusEnabled(false);
    btnMonto.setBorderPainted(false);
    btnMonto.setFocusPainted(false);
    btnMonto.setContentAreaFilled(false);
    btnMonto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnMonto.setHorizontalAlignment(SwingConstants.RIGHT);
    btnMonto.setMnemonic('m');
    btnMonto.setFont(new Font("SansSerif", 0, 11));
    btnMonto.setBounds(new Rectangle(430, 105, 60, 20));
    btnMonto.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnMonto_actionPerformed(e);
        }
      });
    lblEnter.setText("[ F5 ] Borrar Entrega");
    lblEnter.setBounds(new Rectangle(15, 490, 130, 20));
    tblFormasPago.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        tblFormasPago_keyPressed(e);
                    }
      });
    jLabelFunction1.setBounds(new Rectangle(165, 490, 115, 20));
    jLabelFunction1.setText("[ F11] Grabar");
    jLabelFunction2.setBounds(new Rectangle(635, 490, 115, 20));
    jLabelFunction2.setText("[ ESC ] Salir");
    tblDetallePago.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblDetallePago_keyPressed(e);
        }
      });
    lblMontoTipoCambio.setBounds(new Rectangle(520, 5, 55, 15));
    lblTipoCambio.setText("Tipo Cambio : ");
    lblTipoCambio.setBounds(new Rectangle(420, 5, 80, 15));
    txtLote.setHorizontalAlignment(JTextField.RIGHT);
    txtLote.setBounds(new Rectangle(510, 15, 90, 20));
    txtLote.setEnabled(false);
    txtLote.setDocument(new FarmaLengthText(3));
    txtLote.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtLote_keyPressed(e);
        }

        public void keyTyped(KeyEvent e)
        {
          txtLote_keyTyped(e);
        }
      });
    btnLote.setText("Lote :");
    btnLote.setDefaultCapable(false);
    btnLote.setRequestFocusEnabled(false);
    btnLote.setBorderPainted(false);
    btnLote.setFocusPainted(false);
    btnLote.setContentAreaFilled(false);
    btnLote.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnLote.setHorizontalAlignment(SwingConstants.RIGHT);
    btnLote.setMnemonic('L');
    btnLote.setFont(new Font("SansSerif", 0, 11));
    btnLote.setBounds(new Rectangle(430, 15, 60, 20));
    btnLote.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnLote_actionPerformed(e);
        }
      });

        cmbSobre.setBounds(new Rectangle(510, 155, 85, 20));
        cmbSobre.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        cmbSobre_keyPressed(e);
                    }
                });
        cmbSobre.setVisible(false);
        lblSObre.setText("");
        lblSObre.setBounds(new Rectangle(445, 130, 155, 25));
        lblSObre.setFont(new Font("Dialog", 1, 11));

        mensaje.setFont(new Font("Microsoft Sans Serif", 1, 17));
        mensaje.setForeground(Color.red);
        jPanel1.add(lblSObre, null);
        jPanel1.add(cmbSobre, null);
        jPanel1.add(btnLote, null);
        pnlTotales.add(lblSaldoT, new XYConstraints(470, 10, 170, 20));
        pnlTotales.add(lblTotal, new XYConstraints(640, 10, 90, 20));
        scrDetallePago.getViewport();
        scrDetallePago.getViewport();
        jPanel1.add(txtLote, null);
        jPanel1.add(btnMonto, null);
        jPanel1.add(btnCantidad, null);
        jPanel1.add(txtCantidad, null);
        jPanel1.add(btnMoneda, null);
        jScrollPane1.getViewport().add(tblFormasPago, null);
        jPanel1.add(jScrollPane1, null);
        jPanel2.add(btnFormaPago, null);
        jPanel1.add(jPanel2, null);
        jPanel1.add(cmbMoneda, null);
        jPanel1.add(txtMontoPagado, null);
        jPanel1.add(btnAdicionar, null);
        
        jScrollPane1.getViewport().add(tblFormasPago, null);
        jScrollPane1.getViewport();
        jScrollPane1.getViewport();
        jPanel3.add(lblTipoCambio, null);
        jPanel3.add(lblMontoTipoCambio, null);
        jPanel3.add(lblRazSoc_T, null);
        jContentPane.add(btnReimp, null);
        jContentPane.add(lblmensaje, null);
        jContentPane.add(jLabelFunction2, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(pnlDetallePago, null);
        scrDetallePago.getViewport().add(tblDetallePago, null);
        jContentPane.add(scrDetallePago, null);
        jContentPane.add(pnlTotales, null);
        mensaje.setVisible(false);
        lblmensaje.setText("LOS SOBRES APROBADOS POR EL QUIMICO FAMACEUTICO SE MUESTRAN DE COLOR ROJO");
        lblmensaje.setBounds(new Rectangle(15, 275, 655, 15));
        lblmensaje.setForeground(Color.red);
        lblmensaje.setFont(new Font("SansSerif", 1, 14));
        btnReimp.setBounds(new Rectangle(290, 490, 117, 19));
        btnReimp.setText("[ F6 ] Reimprimir");
        pnlDetallePago.add(mensaje, new XYConstraints(320, 0, 415, 25));
        pnlDetallePago.add(btnDetallePago, new XYConstraints(10, 5, 125, 15));
        jContentPane.add(jPanel3, null);
        jContentPane.add(jPanel1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
  
  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */
  
  private void initialize()
  {      
    initTableFormasPago();
    initTableDetallePago();
    cargaCombo();
    cargaFormasPago();
      

    listaFormasPagoEntrega(false);
    FarmaVariables.vAceptar=false;
      //JCORTEZ 03.11.09 Se cargan los sobres temporales automaticamente por turno.
       System.out.println("***********1.1");
      cargarSobres();
    
      System.out.println("***********1.2");
    //validando que se pueda o cambio la forma de declarar en sobres
    //jcallo 02/02/2009
    try{
        VariablesCajaElectronica.vIndChangeSobre =  DBCajaElectronica.getIndChangeComboSobre();
    }catch(Exception e){
        log.info("error al cargar indicador CAMBIO COMBO SOBRE");
    }
    log.debug("VariablesCajaElectronica.vIndChangeSobre:"+VariablesCajaElectronica.vIndChangeSobre);
    if(VariablesCajaElectronica.vIndChangeSobre.equals(FarmaConstants.INDICADOR_N)){
        log.debug("inhabilitando combo");
        //cmbSobre.setEditable(false);
        cmbSobre.setEnabled(false);// Editable(false);
    }
    //validando la cantidad de veces que puede eliminar como maximo los sobres
    try{
        VariablesCajaElectronica.vCantModSobres =  Integer.parseInt(DBCajaElectronica.getCantModificacionesSobre().trim());
    }catch(Exception e){
        log.info("error al cargar indicador CAMBIO COMBO SOBRE:"+e);
    }
    
      
      
    
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */
   private void initTableDetallePago()
  {

      tblDetallePago.getTableHeader().setReorderingAllowed(false);
      tblDetallePago.getTableHeader().setResizingAllowed(false);
      
    tableModelDetallePago = new FarmaTableModel(ConstantsCajaElectronica.columsListaDetallePago,ConstantsCajaElectronica.defaultListaDetallePago,0);
    FarmaUtility.initSimpleList(tblDetallePago,tableModelDetallePago,ConstantsCajaElectronica.columsListaDetallePago);
  }
  
  private void initTableFormasPago()
  {

      tblFormasPago.getTableHeader().setReorderingAllowed(false);
      tblFormasPago.getTableHeader().setResizingAllowed(false);
      
    tableModelFormasPago = new FarmaTableModel(ConstantsCajaElectronica.columsListaFormasPagoEntrega,ConstantsCajaElectronica.defaultListaFormasPagoEntrega,0);
    FarmaUtility.initSimpleList(tblFormasPago,tableModelFormasPago,ConstantsCajaElectronica.columsListaFormasPagoEntrega);
  }
  
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */
  

  private void btnMoneda_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(cmbMoneda);
  }

  private void btnMonto_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtMontoPagado);
  }

  private void tblFormasPago_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      validaFormaPagoSeleccionada();
    } 
    chkKeyPressed(e);
  }

  private void btnFormaPago_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocusJTable(tblFormasPago);
  }

  private void btnAdicionar_actionPerformed(ActionEvent e)
  {
    if(FarmaVariables.vEconoFar_Matriz){
      FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,tblFormasPago);
      return;
    }
    UtilityCajaElectronica.getIndicadorVB(VariablesCajaElectronica.vSecMovCaja,ConstantsCajaElectronica.TIPO_VB_CAJERO);
    if(VariablesCajaElectronica.vUsuarioCajero && VariablesCajaElectronica.vIndVBCajero.equals(FarmaConstants.INDICADOR_N))
      adicionaDetallePago();
    else
      FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblFormasPago);
  }

  private void tblDetallePago_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void btnDetallePago_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocusJTable(tblDetallePago);
  }

  private void btnCantidad_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtCantidad);
  }

  private void cmbMoneda_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtMontoPagado);
    } else chkKeyPressed(e);
  }

  private void txtMontoPagado_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(cmbMoneda);
    } else
      chkKeyPressed(e);
  }

  private void txtMontoPagado1_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
        if(cmbSobre.isVisible()&& cmbSobre.isEnabled())
        {
          FarmaUtility.moveFocus(cmbSobre);
        }
        else
        {
          btnAdicionar.doClick();
        }
    } else
      chkKeyPressed(e);  
  }
  
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    lblMontoTipoCambio.setText(FarmaUtility.formatNumber(FarmaVariables.vTipCambio));
    System.out.println("VariablesCaja.vIndTarjetaSeleccionada : " + VariablesCaja.vIndTarjetaSeleccionada);
    FarmaUtility.moveFocusJTable(tblFormasPago);
      determinarVisibilidadMsgRed();
      if(VariablesCajaElectronica.indAutoFSEN.equalsIgnoreCase("S")){ //ASOSA, 21.06.2010
          VariablesCajaElectronica.indAutoFSEN="N";
          FarmaGridUtils.moveRowSelection(tblFormasPago,2);
          validaFormaPagoSeleccionada();
      }
  }

  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */


  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      cerrarVentana(false);
    } else if (e.getKeyCode() == KeyEvent.VK_F11) {
      if(FarmaVariables.vEconoFar_Matriz){
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,tblFormasPago);
        return;
      }
      UtilityCajaElectronica.getIndicadorVB(VariablesCajaElectronica.vSecMovCaja,ConstantsCajaElectronica.TIPO_VB_CAJERO);
      if(VariablesCajaElectronica.vUsuarioCajero && VariablesCajaElectronica.vIndVBCajero.equals(FarmaConstants.INDICADOR_N))
            if(buscarSobres()){ //INI ASOSA, 09.08.2010 - otro cambio imprevisto
                if (cargaValidaLogin()) { 
                    agregaFormaPagoEntrega();
                    cargarSobres(); //ASOSA, 09.08.2010
                }else {
                    FarmaUtility.showMessage(this, "Necesita autorización del Jefe del Local.", 
                                             tblFormasPago);
                }
            }else{
                agregaFormaPagoEntrega();
            }   //FIN ASOSA, 09.08.2010 - otro cambio imprevisto
      else
        FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblFormasPago);        
    } else if (e.getKeyCode() == KeyEvent.VK_F5) {
      if(FarmaVariables.vEconoFar_Matriz){
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,tblFormasPago);
        return;
      }
      UtilityCajaElectronica.getIndicadorVB(VariablesCajaElectronica.vSecMovCaja,ConstantsCajaElectronica.TIPO_VB_CAJERO);
      if(VariablesCajaElectronica.vUsuarioCajero && VariablesCajaElectronica.vIndVBCajero.equals(FarmaConstants.INDICADOR_N))
        //eliminando
        eliminaFormaPagoEntrega();
      else
        FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblFormasPago);        
      }else if (e.getKeyCode() == KeyEvent.VK_F6){ //ASOSA, 09.05.2010
            reimprimir();
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

  private boolean cargaFormasPago()
  {
    try
    {
      //DBCaja.obtieneFormasPago(tableModelFormasPago);
      DBCajaElectronica.obtieneFormasPago(tableModelFormasPago);
      //Solo aplica para fondo de Sencillo dubilluz - 19.07.2010
      if(!UtilityFondoSencillo.indActivoFondo()) {
          //FarmaUtility.showMessage(this, "Error ",null);
          //...ahora ya no,  para que muestre el fondo de sencillo(00058) luego de efectivo soles y dolares, en tercer lugar ASOSA, 18.06.2010 - antes, ...
          FarmaUtility.ordenar(tblFormasPago, tableModelFormasPago, 0, FarmaConstants.ORDEN_ASCENDENTE); 
      }  
      
        
      return true;
    } catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this, "Error al obtener las Formas de Pago.\n" + sql.getMessage(), tblFormasPago);
      return false;
    }
  }
  
  private void cargaCombo()
  {
    FarmaLoadCVL.loadCVLfromArrays(cmbMoneda,FarmaConstants.HASHTABLE_MONEDA,FarmaConstants.MONEDAS_CODIGO,FarmaConstants.MONEDAS_DESCRIPCION,true);
    //Coloca por defecto el combo de sobre en "S" ya que tiene el indicador de SObre el local
    //dubilluz 16.12.2008
    FarmaLoadCVL.loadCVLfromArrays(cmbSobre,CMB_SOBRE,INDICADORES_CODIGO,INDICADORES_DESCRIPCION,true);
    FarmaLoadCVL.setSelectedValueInComboBox(cmbSobre,CMB_SOBRE, FarmaConstants.INDICADOR_S);
  } 
  
  private void validaFormaPagoSeleccionada()
  {
    if(tblFormasPago.getRowCount() <= 0) return;
    int fila = tblFormasPago.getSelectedRow();
    String codFormaPago = ((String)tblFormasPago.getValueAt(fila,0)).trim();
    System.out.println("codFormaPago : " + codFormaPago);
    //String codOperTarj = ((String)tblFormasPago.getValueAt(fila,2)).trim();
    String indTarjeta = ((String)tblFormasPago.getValueAt(fila,3)).trim();
    VariablesCaja.vIndTarjetaSeleccionada = false;
    VariablesCaja.vIndCuponSeleccionado = false;
    String indCupon = ((String)tblFormasPago.getValueAt(fila,4)).trim();
    VariablesCajaElectronica.vIndDebito = ((String)tblFormasPago.getValueAt(fila,5)).trim(); 
    if(codFormaPago.equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES))
    {
      System.out.println("FORMA DE PAGO - SOLES");
      FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, FarmaConstants.HASHTABLE_MONEDA, FarmaConstants.CODIGO_MONEDA_SOLES);
      cmbMoneda.setEnabled(false);
      txtLote.setEnabled(false);
      txtCantidad.setEnabled(false);
      VariablesCaja.vIndCambioMoneda = false;
      txtMontoPagado.setEnabled(true);
      btnAdicionar.setEnabled(true);
      txtLote.setText("");
      txtCantidad.setText("");
      FarmaUtility.moveFocus(txtMontoPagado);
    } else if(codFormaPago.equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES))
    {
      System.out.println("FORMA DE PAGO - DOLARES");
      FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, FarmaConstants.HASHTABLE_MONEDA, FarmaConstants.CODIGO_MONEDA_DOLARES);
      cmbMoneda.setEnabled(false);
      txtLote.setEnabled(false);
      txtCantidad.setEnabled(false);
      VariablesCaja.vIndCambioMoneda = false;
      txtMontoPagado.setEnabled(true);
      btnAdicionar.setEnabled(true);
      txtLote.setText("");
      txtCantidad.setText("");      
      FarmaUtility.moveFocus(txtMontoPagado);
    } else if( indTarjeta.equalsIgnoreCase(FarmaConstants.INDICADOR_S) && VariablesCajaElectronica.vIndDebito.equalsIgnoreCase(ConstantsCajaElectronica.IND_DEBITO))
    { 
      System.out.println("FORMA DE PAGO - TARJETA");
      VariablesCaja.vIndTarjetaSeleccionada = true;
      cmbMoneda.setEnabled(true);
      VariablesCaja.vIndCambioMoneda = true;
      txtCantidad.setEnabled(true);
      // JMIRANDA 19.07.2010
      txtLote.setEnabled(true);
      txtMontoPagado.setEnabled(true);
      btnAdicionar.setEnabled(true);
      // JMIRANDA 19.07.2010
      FarmaUtility.moveFocus(txtLote);
      //FarmaUtility.moveFocus(txtCantidad);
    } else if( indTarjeta.equalsIgnoreCase(FarmaConstants.INDICADOR_S) )
    { 
      System.out.println("FORMA DE PAGO - TARJETA");
      VariablesCaja.vIndTarjetaSeleccionada = true;
      cmbMoneda.setEnabled(true);
      VariablesCaja.vIndCambioMoneda = true;
      txtCantidad.setEnabled(true);
      txtLote.setEnabled(false);
      txtMontoPagado.setEnabled(true);
      btnAdicionar.setEnabled(true);
      FarmaUtility.moveFocus(txtCantidad);
    } 
      //JMIRANDA 10.03.2010
      else if(codFormaPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_FONDO_SENCILLO)){
          System.out.println("FORMA DE PAGO - FONDO DE SENCILLO"); 
          cmbMoneda.setEnabled(false);
          txtLote.setEnabled(false);
          txtCantidad.setEnabled(false);
          VariablesCaja.vIndCambioMoneda = false;
          txtMontoPagado.setEnabled(false);
          btnAdicionar.setEnabled(false);
          txtLote.setText("");
          txtCantidad.setText("");
          txtMontoPagado.setText("");
          VariablesFondoSencillo.vMensajeDevolver = "Nuevo Fondo de Sencillo para Devolver.";           
          ingresaFondoSencillo();
          //valida después de DevFondoSencillo      
      }
      //
    else
    {
      System.out.println("FORMA DE PAGO - NO TARJETA");
      if( indCupon.equalsIgnoreCase(FarmaConstants.INDICADOR_S) )
      {
        FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, FarmaConstants.HASHTABLE_MONEDA, FarmaConstants.CODIGO_MONEDA_SOLES);
        VariablesCaja.vIndCuponSeleccionado = true;
        cmbMoneda.setEnabled(true);
        VariablesCaja.vIndCambioMoneda = true;
        txtCantidad.setEnabled(true);
        txtMontoPagado.setEnabled(true);
        btnAdicionar.setEnabled(true);
        FarmaUtility.moveFocus(txtCantidad);
      } else
      {
        FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, FarmaConstants.HASHTABLE_MONEDA, FarmaConstants.CODIGO_MONEDA_SOLES);
        txtCantidad.setEnabled(false);
        cmbMoneda.setEnabled(true);
        VariablesCaja.vIndCambioMoneda = true;
        txtMontoPagado.setEnabled(true);
        btnAdicionar.setEnabled(true);
        FarmaUtility.moveFocus(txtMontoPagado);
      }
    }
    //Validara si la forma de pago 
    //puede o no puede ser con sobre o no
    //DUbilluz 14.01.2008
    cargaProcesoProsegur(codFormaPago.trim());   
    
  }
  
  private void adicionaDetallePago()
  {
    obtieneDatosFormaPago();
            if(!validaMontoIngresado()) return;
            
            //if(!isLocalProsegur(VariablesCaja.vCodFormaPago)){    ANTES, //ASOSA, 31.05.2010
            if(!isLocalProsegur(VariablesCaja.vCodFormaPago) && !obtenerIndSobresXFPago(VariablesCaja.vCodFormaPago)){ //ASOSA, 31.05.2010
                if(VariablesCajaElectronica.vIndSobre.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                    FarmaUtility.showMessage(this,"No puede registrar la forma de pago como Sobre. Verifique!!!", tblFormasPago);
                    return;
                }
                
                if(!validaCodigoFormaPago())
                {
                  FarmaUtility.showMessage(this,"La forma de pago ya existe para el Cierre de Turno. Verifique!!!", tblFormasPago);
                  return;
                }
            }
            operaListaDetallePago();
            lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblDetallePago,COL_TOTAL)));
            limpiaVariablesFormaPago();
            txtCantidad.setEnabled(false);
            txtLote.setEnabled(false);
            txtMontoPagado.setEnabled(false);
            btnAdicionar.setEnabled(false);
            cmbMoneda.setEnabled(false);
            FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, FarmaConstants.HASHTABLE_MONEDA, FarmaConstants.CODIGO_MONEDA_SOLES);
            
            btnFormaPago.doClick();            
       
  }
  
  private void obtieneDatosFormaPago()
  {    
        System.out.println("VariablesCaja.vIndTarjetaSeleccionada obtiene fromas de pago inicio : " + VariablesCaja.vIndTarjetaSeleccionada);
        if(tblFormasPago.getRowCount() <= 0) return;
        int fila = tblFormasPago.getSelectedRow();
          
        VariablesCaja.vCodFormaPago = ((String)tblFormasPago.getValueAt(fila,0)).trim();
        VariablesCaja.vDescFormaPago = ((String)tblFormasPago.getValueAt(fila,1)).trim();
        VariablesCajaElectronica.vIndTarjeta = ((String)tblFormasPago.getValueAt(fila,3)).trim();
        String codMoneda = FarmaLoadCVL.getCVLCode(FarmaConstants.HASHTABLE_MONEDA,cmbMoneda.getSelectedIndex());
        VariablesCaja.vCodMonedaPago = codMoneda;
        VariablesCaja.vDescMonedaPago = FarmaLoadCVL.getCVLDescription(FarmaConstants.HASHTABLE_MONEDA, codMoneda);
    //JMIRANDA 10.03.2010
    if (VariablesCaja.vCodFormaPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_FONDO_SENCILLO)) {         
        VariablesCaja.vValMontoPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesFondoSencillo.vMonto));
        VariablesCajaElectronica.vCantidad = "0";
        VariablesCajaElectronica.vLote = "";
        VariablesCajaElectronica.vIndSobre = FarmaConstants.INDICADOR_N;
        VariablesCaja.vValTotalPagado = VariablesCaja.vValMontoPagado;
    }
    else {        
        VariablesCaja.vValMontoPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtMontoPagado.getText().trim()));
        if(codMoneda.equalsIgnoreCase(FarmaConstants.CODIGO_MONEDA_SOLES))
          VariablesCaja.vValTotalPagado = VariablesCaja.vValMontoPagado;
        else
          VariablesCaja.vValTotalPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado) * FarmaVariables.vTipCambio);
          
        if(VariablesCaja.vIndTarjetaSeleccionada || VariablesCaja.vIndCuponSeleccionado){
          VariablesCajaElectronica.vCantidad = txtCantidad.getText() ;
          VariablesCajaElectronica.vLote = txtLote.getText();
        }
        else{ 
          VariablesCajaElectronica.vCantidad = "0";
          VariablesCajaElectronica.vLote = "";
        }
        
        //Para el proceso de los sobres 
        //DUBILLUZ 14.01.2008
        VariablesCajaElectronica.vIndSobre = FarmaConstants.INDICADOR_N;
        if (cmbSobre.isVisible())
        {
            VariablesCajaElectronica.vIndSobre = FarmaLoadCVL.getCVLCode(CMB_SOBRE,cmbSobre.getSelectedIndex());
            System.out.println("VariablesCajaElectronica.vIndSobre:" + 
                               VariablesCajaElectronica.vIndSobre);
        }
        //FIN
        
        System.out.println("VariablesCaja.vIndTarjetaSeleccionada obtiene fromas de pago fin : " + VariablesCaja.vIndTarjetaSeleccionada);
        
    }
  }

  private boolean validaMontoIngresado()
  {
    System.out.println("VariablesCaja.vIndTarjetaSeleccionada valida monto : " + VariablesCaja.vIndTarjetaSeleccionada);
    String monto = txtMontoPagado.getText().trim();
    String cantidad = txtCantidad.getText().trim();
    String lote = txtLote.getText().trim();
    
    if(monto.equalsIgnoreCase("") || monto.length() <= 0)
    {
      FarmaUtility.showMessage(this, "Ingrese monto a pagar.", txtMontoPagado);
      return false;
    }
    if(!FarmaUtility.isDouble(monto))
    {
      FarmaUtility.showMessage(this, "Ingrese monto a pagar valido.", txtMontoPagado);
      return false;
    }
    if(FarmaUtility.getDecimalNumber(monto) == 0)
    {
      FarmaUtility.showMessage(this, "Ingrese monto a pagar mayor a 0.", txtMontoPagado);
      return false;
    }
    if(FarmaUtility.getDecimalNumber(monto) < 0)
    {
      if(FarmaUtility.rptaConfirmDialog(this, "El monto ingresado es negativo. Está seguro de adicionar?"))
        return true;
      else
        return false;
    }
    if(VariablesCaja.vIndTarjetaSeleccionada || VariablesCaja.vIndCuponSeleccionado)
    {
      if(!FarmaUtility.isInteger(cantidad) || Integer.parseInt(cantidad) < 1 )
      {
        FarmaUtility.showMessage(this, "Ingrese una cantidad valida", txtCantidad);
        return false;
      }
    }
    /*if(VariablesCaja.vIndTarjetaSeleccionada && VariablesCajaElectronica.vIndDebito.equalsIgnoreCase(ConstantsCajaElectronica.IND_DEBITO))
    {
      if(lote.equalsIgnoreCase(""))
      {
        FarmaUtility.showMessage(this, "Ingrese un numero de lote valido", txtLote);
        return false;
      }    
    }*/
    return true;
  }
  
  private boolean validaCodigoFormaPago()
  {
    if(tblDetallePago.getRowCount() <= 0) return true;
    String codFormaPago = VariablesCaja.vCodFormaPago;
    String codMoneda = VariablesCaja.vCodMonedaPago;
    String indTarjeta = VariablesCajaElectronica.vIndTarjeta;
    String numLote = VariablesCajaElectronica.vLote;
    System.out.println("numLote : " + numLote);
    System.out.println("VariablesCajaElectronica.vIndTarjeta : " + VariablesCajaElectronica.vIndTarjeta);
    for(int i=0; i<tblDetallePago.getRowCount(); i++)
    {
      String codTmp = ((String)tblDetallePago.getValueAt(i,COL_COD_FPAGO)).trim();
      String codMonedaTmp = ((String)tblDetallePago.getValueAt(i,COL_MON_2)).trim();
      String numLoteTmp = ((String)tblDetallePago.getValueAt(i,COL_LOTE)).trim();
      System.out.println("numLoteTmp : " + numLoteTmp);
      if(indTarjeta.equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
        if(codFormaPago.equalsIgnoreCase(codTmp) && codMoneda.equalsIgnoreCase(codMonedaTmp)) return false;
      }
      else if (indTarjeta.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
        if(codFormaPago.equalsIgnoreCase(codTmp) && 
           codMoneda.equalsIgnoreCase(codMonedaTmp)&&
           numLote.equalsIgnoreCase(numLoteTmp)) return false;
      }
    }
    return true;
  }
  
  private void operaListaDetallePago()
  {
    ArrayList myArray = new ArrayList();
    myArray.add(VariablesCaja.vCodFormaPago);
    myArray.add(VariablesCaja.vDescFormaPago);
    myArray.add(VariablesCajaElectronica.vCantidad);
    myArray.add(VariablesCaja.vDescMonedaPago);
    myArray.add(VariablesCaja.vValMontoPagado);
    myArray.add(VariablesCaja.vValTotalPagado);
    myArray.add(VariablesCajaElectronica.vLote);
    myArray.add(VariablesCajaElectronica.vIndSobre);//Ind si se asociara a un sobre
    myArray.add(" ");//CODIGO DE SOBRE DUBILLUZ 14.01.2009
    myArray.add(VariablesCajaElectronica.vSecMovCaja);
    myArray.add(VariablesCaja.vCodMonedaPago);
    myArray.add(FarmaConstants.INDICADOR_N);
    myArray.add(FarmaConstants.INDICADOR_N);//INDICADOR AUTOMATICO
    myArray.add(FarmaConstants.INDICADOR_N);//SecForma PagoEntrega "N" indica que no agrego aun
    tableModelDetallePago.data.add(myArray);
    tableModelDetallePago.fireTableDataChanged();
    txtMontoPagado.setText("0.00");
  }
  
  private void limpiaVariablesFormaPago()
  {
    VariablesCajaElectronica.vIndTarjeta = "";
    VariablesCaja.vCodFormaPago = "";
    VariablesCaja.vDescFormaPago = "";
    VariablesCaja.vDescMonedaPago = "";
    VariablesCajaElectronica.vCantidad = "";
    VariablesCaja.vValMontoPagado = "";
    VariablesCaja.vValTotalPagado = "";
    VariablesCajaElectronica.vLote = "";
    txtMontoPagado.setText("0.00");
    txtCantidad.setText("0");
    txtLote.setText("");
    VariablesCaja.vIndTarjetaSeleccionada = false;
    VariablesCaja.vIndCuponSeleccionado = false;
    System.out.println("limpia pone false");
  }
  private void agregaFormaPagoEntrega()
  {
      
    //indicador para saber si es una forma de pago donde se
    //asociara con un sobre 
    //dubilluz 14.01.2009  
    String pIndSobre = FarmaConstants.INDICADOR_N;
    String pSecFormaPagoEntrega = ""; 
    try 
    {
      for(int i=0;i<tblDetallePago.getRowCount(); i++)
          {
           if(((String)tblDetallePago.getValueAt(i,COL_IND)).trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
           {
            pSecFormaPagoEntrega = DBCajaElectronica.agregaFormaPagoEntrega(((String)tblDetallePago.getValueAt(i,COL_SEC_CAJA)).trim(),
                                                     ((String)tblDetallePago.getValueAt(i,COL_COD_FPAGO )).trim(),
                                                     ((String)tblDetallePago.getValueAt(i,COL_CANT)).trim(),
                                                     ((String)tblDetallePago.getValueAt(i,COL_MON_2)).trim(),
                                                     ((String)tblDetallePago.getValueAt(i,COL_MONTO)).trim(),
                                                     ((String)tblDetallePago.getValueAt(i,COL_TOTAL)).trim(),
                                                     ((String)tblDetallePago.getValueAt(i,COL_LOTE)).trim());
            
            //INICIO 
            //Se opera el sobre para la forma de pago ingresada
            //dubilluz 14.01.2009
            System.out.println("Secuencial de Forma Pago Sec Caja:"+pSecFormaPagoEntrega); 
            pIndSobre = ((String)tblDetallePago.getValueAt(i,COL_IND_SOBRE)).trim();
            if(pIndSobre.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                DBCajaElectronica.agregaSobre(((String)tblDetallePago.getValueAt(i,COL_SEC_CAJA)).trim(),
                                              pSecFormaPagoEntrega.trim(),
                                              //JCORTEZ 04.11.09 Se envia codigo para saber si se creo el sobre
                                              ((String)tblDetallePago.getValueAt(i,8)).trim());
            }
            ArrayList listaSobres02=colectarSobresRecienDeclarados(VariablesCajaElectronica.vSecMovCaja);   //ASOSA, 13.08.2010
               DBCajaElectronica.aprobarSobres(VariablesCajaElectronica.vSecMovCaja,
                            VariablesCajaElectronica.idUsu_APRUEBA_SOBRE, 
                            VariablesCajaElectronica.pSecUsu_APRUEBA_SOBRE);    //ASOSA, 11.08.2010
            //FIN
            //--JMIRANDA 11.03.2010 
            // Graba la devolución del fondo de Sencillo
            System.err.println("JMIRANDA 11.03.2010 ");
            String CodFormPago = FarmaUtility.getValueFieldArrayList(tableModelDetallePago.data,i,0);
            if (CodFormPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_FONDO_SENCILLO)&&UtilityFondoSencillo.indActivoFondo()) {
                System.err.println("antes de ind Tiene Monto Devolver VariablesFondoSencillo.vIndTieneMontoDevolver:"+VariablesFondoSencillo.vIndTieneMontoDevolver);
                VariablesFondoSencillo.vIndTieneMontoDevolver = 
                        UtilityFondoSencillo.getIndTieneDevFondo(FarmaVariables.vNuSecUsu, 
                                                                 VariablesCajaElectronica.vSecMovCaja, 
                                                                 this, 
                                                                 tblFormasPago).trim();
                System.err.println("VariablesFondoSencillo.vIndTieneMontoDevolver:"+VariablesFondoSencillo.vIndTieneMontoDevolver);
                if(VariablesFondoSencillo.vIndTieneMontoDevolver.equalsIgnoreCase("D")){
                    System.err.println("inserta devolucion.."+i);
                DBFondoSencillo.insDevolucionSencillo(VariablesFondoSencillo.vMonto,
                                                      VariablesFondoSencillo.vSecAdmLocal, // DESTINO
                                                      FarmaVariables.vNuSecUsu, // ORIGEN
                                                      ConstantsFondoSencillo.EstadoEmitido,
                                                      ConstantsFondoSencillo.TipoFondoDevuelve,
                                                      FarmaVariables.vIdUsu,
                                                      FarmaVariables.vIpPc,
                                                      VariablesCajaElectronica.vSecMovCaja); 
                UtilityFondoSencillo.imprimeVoucherDevoluciones(this,VariablesCajaElectronica.vSecMovCaja,tblFormasPago);
                }
            }
            //--
            FarmaUtility.aceptarTransaccion();
               imprimeSobresDeclarados_0X(listaSobres02,VariablesCajaElectronica.vSecMovCaja); //ASOSA, 13.08.2010 - Ahora se imprimiran aca porq es un cambio inesperado
            if(cmbSobre.isVisible())
            {
               cmbSobre.setVisible(false);
            }
           }
          }
          FarmaUtility.showMessage(this,"Se registraron correctamente las formas de pago", tblFormasPago);
          listaFormasPagoEntrega(false);
    } catch (SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      sql.printStackTrace();

            if (sql.getErrorCode() == 20001) {
                FarmaUtility.showMessage(this, "No puede agregar el sobre", 
                                         null);
            } else if (sql.getErrorCode() == 20002) {
                FarmaUtility.showMessage(this, 
                                         "No se puede agregar el sobre.\n" +
                        "Porque el dia ya se asoció a un Remito.", null);
            } else {

                FarmaUtility.showMessage(this, 
                                         "Error al grabar forma pago pedido \n" +
                        sql.getMessage(), null);
            }
    }
  }
  
  private void eliminaFormaPagoEntrega()
  {
  
    String indSobre= tblDetallePago.getValueAt(tblDetallePago.getSelectedRow(),COL_IND_SOBRE).toString();
    String CodSobre= tblDetallePago.getValueAt(tblDetallePago.getSelectedRow(),COL_SOBRE).toString();
    String CodFormPago= tblDetallePago.getValueAt(tblDetallePago.getSelectedRow(),COL_COD_FPAGO).toString();
      
   String TipMensaje="";    
	//JCORTEZ 04.05.2009
    //Se valida que solo se verifique solo se pida pass de operador por limite al borrar para las formas de pago
    //que tengan sobres asociados, y ademas sean soles y dolares 
    if(indSobre.trim().equalsIgnoreCase("S")&&CodSobre.trim().length()>0&&
        (CodFormPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES)||
        CodFormPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES))&& validarEstadoSobre(CodSobre,CodFormPago).equalsIgnoreCase("A")){
        
        //enviar correo de intento
        TipMensaje="01";
        try{
            DBCajaElectronica.enviarCorreoSobresModificados(VariablesCajaElectronica.vFecCierreCajaAux, VariablesCajaElectronica.vSecMovCaja,TipMensaje,CodSobre);
        }catch(Exception e){
            log.debug("error al enviar correo:"+e);
        }

     //Se valida antes de eliminar que el sobre no este asociaod de un dia con remito        
        if(!validaSobreRemito(VariablesCajaElectronica.vFecCierreCajaAux,VariablesCajaElectronica.vSecMovCaja,CodSobre)){
            //FarmaUtility.showMessage(this, "No se puede eliminar este sobre ya cerrado.",tblDetallePago);
            return;
        }else {
              
            if( !cargaLoginOper() )
            {
              FarmaUtility.showMessage(this,"No se realizó la operación. Solo un usuario con Rol de\nOperador de Sistemas puede eliminar esta sobre.", tblDetallePago);
              return;
            } else {
                //si logra ingresar la clave del operador
                mensaje.setText("Se están registrando sus movimientos");
                mensaje.setVisible(true);
                
                //envia correo de termino de eliminacion
                TipMensaje="02";
                try{
                    DBCajaElectronica.enviarCorreoSobresModificados(VariablesCajaElectronica.vFecCierreCajaAux, VariablesCajaElectronica.vSecMovCaja,TipMensaje,CodSobre);
                }catch(Exception e){
                    log.debug("error al enviar correo:"+e);
                }
            }
        }
        
        
    //JCORTEZ 04.05.2009
    //Se valida que solo se verifique solo se pida pass de operador por limite al borrar para las formas de pago
    //que tengan sobres asociados, y ademas sean soles y dolares      
     /* //verificando la cantidad de sobres que elimino por turno el cajero
      int cantSobreEliminados = 0;
      try{
          cantSobreEliminados = Integer.parseInt( DBCajaElectronica.getCantSobresEliminados(VariablesCajaElectronica.vFecCierreCajaAux,
                                                                            VariablesCajaElectronica.vSecMovCaja).trim()) ;
      }
      catch(Exception e){
          log.debug("error al obtener la cantidad de sobres eliminados por turno"+e);
      }
      
      log.debug("jcallo: camtidad de sobres eliminados: "+cantSobreEliminados);
      //mostrar emnsaje si excedio la cantidad de veces que puede eliminar
      //esto tb se podria realizar por base de datos
      
       System.out.println("VariablesCajaElectronica.vCantModSobres-->"+VariablesCajaElectronica.vCantModSobres); 
        System.out.println("cantSobreEliminados-->"+cantSobreEliminados); 
        
      if(VariablesCajaElectronica.vCantModSobres <= cantSobreEliminados){
            //FarmaUtility.showMessage(this,"Ud. No puede eliminar el sobre.\nExcedió la cantidad de veces posible por turno!",tblDetallePago);
            FarmaUtility.showMessage(this,"Ud. No puede eliminar el sobre.",tblDetallePago);
            if(!cargaLoginOper()){//si no ingresa el pass de operador sale
                return;
            }else{
                //si logra ingresar la clave del operador
                mensaje.setText("             Se están registrando sus movimientos");
                mensaje.setVisible(true);
                
                try{
                    DBCajaElectronica.enviarCorreoSobresModificados(VariablesCajaElectronica.vFecCierreCajaAux, VariablesCajaElectronica.vSecMovCaja);
                }catch(Exception e){
                    log.debug("error al enviar correo:"+e);
                }
            }
      }*/
    }
    System.out.println(":::::::::::::::::::::::PRIMERA VALIDACION ES SOBRE::::::::::::::::::::::::::::::::::::::::::");
      
    if(tieneRegistroSeleccionado(tblDetallePago)){
      guardaDatosDetalle();
      if(VariablesCajaElectronica.vIndEliminacion.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
      {
        //esto solo eliminara del jtable si fuera uno que recien se haya agregado y no este guardado en base de datos
        System.out.println("Elimina registro Seleccionado");
        eliminaRegistroSeleccionado();
        
 
          System.out.println(":::::::::::::VariablesCajaElectronica.vIndSobre::::::::::::::"+VariablesCajaElectronica.vIndSobre+"/CodSobre->"+CodSobre);
                try {
                    if (VariablesCajaElectronica.vIndSobre.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)&&!CodSobre.equalsIgnoreCase(" ")) {
                        DBCajaElectronica.eliminaSobreRegistrado(VariablesCajaElectronica.vCodigoSobre);
                        FarmaUtility.aceptarTransaccion();
                    }
                } catch (SQLException sql) {
                    FarmaUtility.liberarTransaccion();
                    sql.printStackTrace();
                    if (sql.getErrorCode() == 20001) {
                        FarmaUtility.showMessage(this, 
                                                 "No puede agregar el sobre", 
                                                 null);
                    } else if (sql.getErrorCode() == 20002) {
                        FarmaUtility.showMessage(this, 
                                                 "No se puede eliminar el sobre.\n" +
                                "Porque el día ya se asoció a un Remito.",
                                null);
                    } else {
                        FarmaUtility.showMessage(this, 
                                                 "Ocurrió un error al eliminar físicamente \n " + 
                                                 sql.getMessage(), null);
                    }
                }
        lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblDetallePago,COL_TOTAL)));
      } else
        {
          try
          {
              //JMIRANDA 16.03.2010
              boolean indSigueProce = true;
            if (FarmaUtility.rptaConfirmDialog(this,"¿El sistema eliminará la forma de pago físicamente. Desea Continuar?"))
            {
              if(VariablesCajaElectronica.vIndAutomatico.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
              {
                if( !cargaLoginOper() )
                {
                  FarmaUtility.liberarTransaccion();
                  FarmaUtility.showMessage(this,"No se realizó la operación. Solo un usuario con Rol de\nOperador de Sistemas puede eliminar esta forma de pago.", tblDetallePago);
                  return;
                }
              }
                //Verifica si Tiene Sobre para eliminarlo antes de Eliminar el ingreso
                //DUBILLUZ 14.01.2008
                //Inicio 
              
              System.out.println(":::::::::::::VariablesCajaElectronica.vIndSobre::::::::::::::"+VariablesCajaElectronica.vIndSobre);
                if (VariablesCajaElectronica.vIndSobre.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    DBCajaElectronica.eliminaSobreRegistrado(VariablesCajaElectronica.vCodigoSobre);
                }
                //Fin
              //
              if(!VariablesCajaElectronica.vLote.equalsIgnoreCase("")){
                  DBCajaElectronica.eliminaFormaPagoEntega(VariablesCajaElectronica.vSecMovCaja,
                                                           VariablesCajaElectronica.vCodFormaPago,
                                                           VariablesCajaElectronica.vTipMoneda,
                                                           VariablesCajaElectronica.vLote,
                                                           VariablesCajaElectronica.vSecFPEntrega);                  
                FarmaUtility.aceptarTransaccion();       
                System.out.println("Elimina registro Seleccionado BD");
                listaFormasPagoEntrega(true);
                //JCORTEZ 03.11.09 Se cargan los sobres temporales automaticamente por turno.
                cargarSobres();
                lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblDetallePago,COL_TOTAL)));
                FarmaUtility.moveFocusJTable(tblFormasPago);
              }
              else {
                DBCajaElectronica.eliminaFormaPagoEntegaSL(VariablesCajaElectronica.vSecMovCaja,
                                                          VariablesCajaElectronica.vCodFormaPago,
                                                          VariablesCajaElectronica.vTipMoneda,
                                                          VariablesCajaElectronica.vSecFPEntrega);
                  //---- Elimina el registro del fondo de sencillo asignado
                  //JMIRANDA 11.03.2010
                  if(CodFormPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_FONDO_SENCILLO)
                    && UtilityFondoSencillo.indActivoFondo()){
                      //JMIRANDA 16.03.2010 Mensaje que no puede eliminar si tiene Fondo Sencillo
                      String indFondoAceptado = DBFondoSencillo.getIndDevolucionAceptada(VariablesCajaElectronica.vSecMovCaja).trim();
                      if(indFondoAceptado.equalsIgnoreCase("S")){
                          FarmaUtility.showMessage(this,"No puede Eliminar una Forma de Sencillo Aceptada por el Administrador del Local.",tblFormasPago);
                          indSigueProce = false;
                      }
                      else{
                          if(cargaLoginOper()){
                          DBFondoSencillo.eliminaDevolucionSencillo(FarmaVariables.vIdUsu,
                                                                    FarmaVariables.vIpPc,
                                                                    VariablesCajaElectronica.vSecMovCaja);
                              indSigueProce = true;
                          }
                          else{
                              indSigueProce = false;
                              FarmaUtility.showMessage(this,"Necesita validar con clave operador.",tblFormasPago);                          
                          }
                      }
                  }
                  //----
                if(indSigueProce)  {
                FarmaUtility.aceptarTransaccion();       
                System.out.println("Elimina registro Seleccionado BD");
                listaFormasPagoEntrega(true);
                lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblDetallePago,COL_TOTAL)));
                FarmaUtility.moveFocusJTable(tblFormasPago);
                  }
              }
            }
          } catch (SQLException sql)
          {
            FarmaUtility.liberarTransaccion();
            sql.printStackTrace();
            if (sql.getErrorCode() == 20001) {
                FarmaUtility.showMessage(this, 
                                         "No puede agregar el sobre", 
                                         null);
            } else if (sql.getErrorCode() == 20002) {
                FarmaUtility.showMessage(this, 
                                         "No se puede eliminar el sobre.\n" +
                        "Porque el día ya se asoció a un Remito.",
                        null);
            } else {
                FarmaUtility.showMessage(this, 
                                         "Ocurrió un error al eliminar físicamente \n " + 
                                         sql.getMessage(), null);
            }
          }
        }
    }
    
      cargarSobres();    
  }
  private void guardaDatosDetalle()
  {
    VariablesCajaElectronica.vIndEliminacion = ((String)tblDetallePago.getValueAt(tblDetallePago.getSelectedRow(),COL_IND)).trim();
    VariablesCajaElectronica.vSecMovCaja = ((String)tblDetallePago.getValueAt(tblDetallePago.getSelectedRow(),COL_SEC_CAJA)).trim();
    VariablesCajaElectronica.vCodFormaPago = ((String)tblDetallePago.getValueAt(tblDetallePago.getSelectedRow(),COL_COD_FPAGO)).trim();
    VariablesCajaElectronica.vTipMoneda = ((String)tblDetallePago.getValueAt(tblDetallePago.getSelectedRow(),COL_MON_2)).trim(); 
    VariablesCajaElectronica.vLote = ((String)tblDetallePago.getValueAt(tblDetallePago.getSelectedRow(),COL_LOTE)).trim();
    VariablesCajaElectronica.vIndAutomatico = ((String)tblDetallePago.getValueAt(tblDetallePago.getSelectedRow(),COL_IND_AUTOMATICO)).trim();
    VariablesCajaElectronica.vSecFPEntrega = ((String)tblDetallePago.getValueAt(tblDetallePago.getSelectedRow(),COL_SEC_FPAGO)).trim();
      
    VariablesCajaElectronica.vIndSobre = ((String)tblDetallePago.getValueAt(tblDetallePago.getSelectedRow(),COL_IND_SOBRE)).trim();          
    VariablesCajaElectronica.vCodigoSobre = ((String)tblDetallePago.getValueAt(tblDetallePago.getSelectedRow(),COL_SOBRE)).trim();
    
  }
  
  private void eliminaRegistroSeleccionado()
  {
    int seleccion = tblDetallePago.getSelectedRow();
    tableModelDetallePago.deleteRow(seleccion);
    tableModelDetallePago.fireTableDataChanged();
    if(seleccion == 0)
     FarmaUtility.moveFocus(tblFormasPago);
    else{
     FarmaGridUtils.showCell(tblDetallePago,seleccion-1,COL_COD_FPAGO);
     FarmaUtility.moveFocus(tblDetallePago);
    }
  }
  
  private void listaFormasPagoEntrega(boolean pIndElimina)
  {
        ArrayList myArray = new ArrayList();
        if (pIndElimina) {
            for (int i = 0; i < tblDetallePago.getRowCount(); i++) {
                if (((String)tblDetallePago.getValueAt(i, 
                                                       COL_SEC_FPAGO)).trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                    myArray.add((ArrayList)tableModelDetallePago.getRow(i).clone());
                }
            }
            //System.out.println("DUBILLUZ:myArray:"+myArray);
        }

        try {
            DBCajaElectronica.cargaListaDetalleFormasPago(tableModelDetallePago, 
                                                          VariablesCajaElectronica.vSecMovCaja);
            if (pIndElimina) {
                for (int i = 0; i < myArray.size(); i++) {
                    tableModelDetallePago.data.add(myArray.get(i));
                    tableModelDetallePago.fireTableDataChanged();
                    //System.out.println("Carga forma de pagos que se ingresaron...");
                }
            }

            FarmaUtility.ordenar(tblDetallePago, tableModelDetallePago, COL_COD_FPAGO, 
                                 FarmaConstants.ORDEN_ASCENDENTE);
            lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblDetallePago, 
                                                                                  COL_TOTAL)));
        } catch (SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(this, 
                                     "Error al listas las formas de pago Entrega Realizadas \n" +
                    sql.getMessage(), null);
        }
  }
  
  private boolean tieneRegistroSeleccionado(JTable pTabla) {
		boolean rpta = false;
		if (pTabla.getSelectedRow() != -1) {
			rpta = true;
		}
		return rpta;
	}
  
  private boolean cargaLoginOper()
  {
    String numsec = FarmaVariables.vNuSecUsu ;
    String idusu = FarmaVariables.vIdUsu ;
    String nomusu = FarmaVariables.vNomUsu ;
    String apepatusu = FarmaVariables.vPatUsu ;
    String apematusu = FarmaVariables.vMatUsu ;
    
    try{
      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
      dlgLogin.setVisible(true);
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
    } catch (Exception e)
    {
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
      FarmaVariables.vAceptar = false;
      e.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
    }
    return FarmaVariables.vAceptar;
  }

  private void txtLote_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtCantidad);
      if(!txtLote.getText().equalsIgnoreCase(""))
        txtLote.setText(FarmaUtility.completeWithSymbol(txtLote.getText(),3,"0","I"));
    } else
      chkKeyPressed(e);
  }

  private void btnLote_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtLote);
  }

  private void txtLote_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtLote,e);
  }
    
    /**
     * Proceso CE Seguridad
     * @author dubilluz
     * @since  13.01.2009
     */
    private void cargaProcesoProsegur(String pCodigoFormaPago) {
        boolean pVisible = isLocalProsegur(pCodigoFormaPago.trim());
        boolean flag=obtenerIndSobresXFPago(pCodigoFormaPago.trim()); //ASOSA, 31.05.2010
        System.out.println("pVisible:" + pVisible);
        System.out.println("flag:" + flag);//ASOSA, 31.05.2010
        //cmbSobre.setVisible(pVisible); //ASOSA, 31.05.2010
        if(pVisible || flag){
            lblSObre.setText("¿Lo ingresará en Sobre?");
            cmbSobre.setVisible(true); //ASOSA, 31.05.2010
        }
        else{
            lblSObre.setText("");
            cmbSobre.setVisible(false);//ASOSA, 31.05.2010
        }
        
            
    }
  
    
    /**
     * 
     * @param pCodigoFormaPago
     */
    private boolean isLocalProsegur(String pCodigoFormaPago){
        String pIndCESeguridad = "";
           boolean pVisible = false;
           try {
               pIndCESeguridad = DBCajaElectronica.getIndCESeguridad(pCodigoFormaPago);

           } catch (SQLException sql) {
               pIndCESeguridad = FarmaConstants.INDICADOR_N;
               sql.printStackTrace();
               FarmaUtility.showMessage(this, 
                                        "Error al grabar forma pago pedido \n" +
                       sql.getMessage(), null);
           }

           if (pIndCESeguridad.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
               pVisible = true;
           } else {
               pVisible = false;
           }
           return pVisible;
    }
    
    /**
     * 
     * @param e
     */
    private void cmbSobre_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(cmbSobre.isVisible())
            {
                btnAdicionar.doClick();
            }
        } else
          chkKeyPressed(e);
    }
   
   /**
    * Se cargan los registros de sobres temporales.
    * @AUTHOR JCORTEZ 
    * @SINCE 03.11.09
    * */
   private void cargarSobres(){
       //tableModelDetallePago.clearTable();
        ArrayList myArray = new ArrayList();
       try
          {
          System.out.println("**********************LISTA SOBRES ENTREGA***************************");
           System.out.println("VariablesCajaElectronica.vSecMovCaja-> "+VariablesCajaElectronica.vSecMovCaja);
          // DBCaja.getListaSobresEntrega(tableModelDetallePago,VariablesCajaElectronica.vSecMovCaja);
              DBCaja.getListaSobresEntrega(myArray,VariablesCajaElectronica.vSecMovCaja);
              System.out.println("sobres temporales-> "+myArray);
              for (int i = 0; i < myArray.size(); i++) {
                  tableModelDetallePago.data.add(myArray.get(i));
                  tableModelDetallePago.fireTableDataChanged();
                  //System.out.println("Carga forma de pagos que se ingresaron...");
              }
           
              ArrayList aux = new ArrayList();
              String isAprobado="",codFormaPago="",codSobre="";
           
              for(int i = 0; i < tableModelDetallePago.data.size(); i++){
                  //valida sobre
                  if ( ((ArrayList)tableModelDetallePago.data.get(i)).get(7).toString().trim().equalsIgnoreCase("S") )
                  { 
                      codFormaPago=((ArrayList)tableModelDetallePago.data.get(i)).get(0).toString().trim();
                      codSobre=((ArrayList)tableModelDetallePago.data.get(i)).get(8).toString().trim();  
                      System.out.println("obtiene estado-> "+codFormaPago+" - "+codSobre);
                      isAprobado = validarEstadoSobre(codSobre,codFormaPago);
                      System.out.println("obtiene estado-> "+isAprobado);
                      if(isAprobado.trim().equalsIgnoreCase("A"))//Aprobado
                        aux.add(String.valueOf(i));
                  }
              }
              FarmaUtility.initSimpleListCleanColumns(tblDetallePago, tableModelDetallePago, 
                  ConstantsCajaElectronica.columsListaDetallePago,aux,Color.white,Color.red,false);

           
          }catch (SQLException sql)
          {
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrio un error al listar los sobres entrega.\n"+sql.getMessage(),tblFormasPago);
          }
       lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblDetallePago,COL_TOTAL)));
   }
  //JMIRANDA 10.03.2010
    private void mostrarDevFondoSencillo(){
        DlgDevolverMontoFondoSencillo dlgDevMonto = new DlgDevolverMontoFondoSencillo(myParentFrame,"",true);
        dlgDevMonto.setVisible(true);
    }   

    private boolean ingresaDevMonto(String pMonto){
        boolean flag = false;
        try{
            DBFondoSencillo.insDevolucionSencillo(pMonto,
                                                  VariablesFondoSencillo.vSecAdmLocal, // DESTINO
                                                  FarmaVariables.vNuSecUsu, // ORIGEN
                                                  ConstantsFondoSencillo.EstadoEmitido,
                                                  ConstantsFondoSencillo.TipoFondoDevuelve,
                                                  FarmaVariables.vIdUsu,
                                                  FarmaVariables.vIpPc,
                                                  VariablesCajaElectronica.vSecMovCaja); 
            
            FarmaUtility.aceptarTransaccion();
            flag = true;
           }
        catch(SQLException sql){
            sql.printStackTrace();
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,"Error al devolver Monto a Administrador de Local.",tblFormasPago);
        }
        return flag;            
    }
    
    private void ingresaFondoSencillo(){
        if(FarmaVariables.vEconoFar_Matriz){
          FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,tblFormasPago);
          return;
        }          
        UtilityCajaElectronica.getIndicadorVB(VariablesCajaElectronica.vSecMovCaja,ConstantsCajaElectronica.TIPO_VB_CAJERO);        
        if(VariablesCajaElectronica.vUsuarioCajero && VariablesCajaElectronica.vIndVBCajero.equals(FarmaConstants.INDICADOR_N)) {   
            VariablesFondoSencillo.vIndTieneMontoDevolver = 
                    UtilityFondoSencillo.getIndTieneDevFondo(FarmaVariables.vNuSecUsu, 
                                                             VariablesCajaElectronica.vSecMovCaja, 
                                                             this, 
                                                             tblFormasPago).trim();
            //validar que no exista más fondo de sencillo
            
            if(VariablesFondoSencillo.vIndTieneMontoDevolver.equalsIgnoreCase("D"))
            {
                System.out.println("WAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                if (validaFondoSencilloTable()) {
                    mostrarDevFondoSencillo();   
                    if(FarmaVariables.vAceptar){                  
                        //
                        obtieneDatosFormaPago();      
                                if(!isLocalProsegur(VariablesCaja.vCodFormaPago)){
                                    if(VariablesCajaElectronica.vIndSobre.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                                        FarmaUtility.showMessage(this,"No puede registrar la forma de pago como Sobre. Verifique!!!", tblFormasPago);
                                        return;
                                    }                              
                                    if(!validaCodigoFormaPago())
                                    {
                                      FarmaUtility.showMessage(this,"La forma de pago ya existe para el Cierre de Turno. Verifique!!!", tblFormasPago);
                                      return;
                                    }
                                }
                                operaListaDetallePago();
                                lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblDetallePago,COL_TOTAL)));
                                limpiaVariablesFormaPago();
                                txtCantidad.setEnabled(false);
                                txtLote.setEnabled(false);
                                txtMontoPagado.setEnabled(false);
                                btnAdicionar.setEnabled(false);
                                cmbMoneda.setEnabled(false);
                                FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, FarmaConstants.HASHTABLE_MONEDA, FarmaConstants.CODIGO_MONEDA_SOLES);
                                
                                btnFormaPago.doClick(); 
                                //dubilluz - 05.07.2010
                                if(VariablesCajaElectronica.vUsuarioCajero && VariablesCajaElectronica.vIndVBCajero.equals(FarmaConstants.INDICADOR_N))
                                  agregaFormaPagoEntrega();                                
                                
                    }
                }
                else {
                    FarmaUtility.showMessage(this,"Ud. ya ha ingresado un Fondo de Sencillo.",tblFormasPago);
                }
            }            
        }
        else
          FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblFormasPago);
    }
    
    private boolean validaFondoSencilloTable(){
        boolean flag = true;
                
        int ind = 0;                
        for(int i=0; i<tblDetallePago.getRowCount(); i++)
        {
          String codTmp = ((String)tblDetallePago.getValueAt(i,COL_COD_FPAGO)).trim();
          
            if(codTmp.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_FONDO_SENCILLO)){
                ind++;
            }                             
        }
        if(ind>0){
            flag = false;
        }
        return flag;
    }
/**
     * Se valida estado del sobre creado.
     * @AUTHOR JCORTEZ 
     * @SINCE 29.03.2010
     * */
   private String validarEstadoSobre(String codsobre,String codFormaPago){
       
    String isAprobado="";
       try {
           System.out.println("Sobre-->"+codsobre);
           System.out.println("FormaPago-->"+codFormaPago);
         isAprobado=  DBCajaElectronica.getSobreAprobado(codsobre,codFormaPago,VariablesCajaElectronica.vFecCierreCajaAux).trim(); 
           System.out.println("isAprobado-->"+isAprobado);
       }catch (SQLException sql) {
           sql.printStackTrace();
           FarmaUtility.showMessage(this,"Ocurrio un error al valida estado de sobre.\n"+sql.getMessage(),tblFormasPago);
       }
       return isAprobado;
    }
    
    
    private boolean validaSobreRemito(String diaCierre,String ceMovCaja,String codSobre){

    String isExist="";
    boolean exist=true;
       try {
           System.out.println("Sobre-->"+codSobre);
         isExist=  DBCajaElectronica.getExistSobreRemito(diaCierre,ceMovCaja,codSobre).trim(); 
           System.out.println("isExist-->"+isExist);
           if(isExist.equalsIgnoreCase("S"))
             exist=false;
       }catch (SQLException sql) {
           sql.printStackTrace();
           FarmaUtility.liberarTransaccion();
           if (sql.getErrorCode() == 20010) {
               //RAISE_APPLICATION_ERROR(-20010,'EL SOBRE '||cCodSobre ||' NO SE ENCUENTRA REGISTRADO');
                   FarmaUtility.showMessage(this, sql.getMessage().substring(10,100),tblDetallePago);
                   exist=false;
               }else if (sql.getErrorCode() == 20020) {
               //RAISE_APPLICATION_ERROR(-20020,mensaje);
                       exist=false;
                       FarmaUtility.showMessage(this, sql.getMessage().substring(10,100).trim()+"\n"+" ¡VERIFIQUE!" ,tblDetallePago);
               }else if (sql.getErrorCode() == 20021) {
               //RAISE_APPLICATION_ERROR(-20020,mensaje);
                       exist=true;
                       FarmaUtility.showMessage(this, sql.getMessage().substring(10,100).trim(),tblDetallePago);
               }else {
               FarmaUtility.showMessage(this,"Ocurrio un error al validar sobre remito.\n"+sql.getMessage(),tblDetallePago);
               }
           
       }
       return exist;
     
    }
    
    
    /**
     * Si el indicador de concepto de sobres esta en 'S' y la forma de pago puede ponerse en sobres entonces devuelve 'S' sino 'N'
     * @author ASOSA
     * @since 31.05.2010
     * @param codFpago
     * @return
     */
    private boolean obtenerIndSobresXFPago(String codFpago){
        boolean flag=false;
        String ind="";
        try{
            ind=DBCajaElectronica.obtenerIndSobresXFPago(codFpago);
        }catch(SQLException e){
            e.printStackTrace();
            ind="N";
            FarmaUtility.showMessage(this,"ERROR al obtener indicador de concepto de sobres",null);
        }
        flag=(ind.equalsIgnoreCase("S"))?true:false;
        return flag;
    }
    
    /**
     * Determina si se hace o no visible un label. Solo debe ser visible si el local tiene activo el concepto de sobres.
     */
    private void determinarVisibilidadMsgRed(){
        boolean flag=false;
        flag=obtenerIndSobresXFPago("00001");
        if(flag) lblmensaje.setVisible(true);
        else lblmensaje.setVisible(false);
    }
    
    /**
     * @author ASOSA
     * @since 09.08.2010
     * Metodo copiado para otro cambio sorpresa
     * @return
     */
    private boolean cargaValidaLogin()
    {
      VariablesCajaElectronica.pSecUsu_APRUEBA_SOBRE = "";  
      String numsec = FarmaVariables.vNuSecUsu ;
      String idusu = FarmaVariables.vIdUsu ;
      String nomusu = FarmaVariables.vNomUsu ;
      String apepatusu = FarmaVariables.vPatUsu ;
      String apematusu = FarmaVariables.vMatUsu ;
      boolean  rpta=false;
      try{
        DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);
        VariablesCajaElectronica.pSecUsu_APRUEBA_SOBRE = FarmaVariables.vNuSecUsu;
        VariablesCajaElectronica.idUsu_APRUEBA_SOBRE = FarmaVariables.vIdUsu; //ASOSA, 11.08.2010
        FarmaVariables.vNuSecUsu  = numsec ;
        FarmaVariables.vIdUsu  = idusu ;
        FarmaVariables.vNomUsu  = nomusu ;
        FarmaVariables.vPatUsu  = apepatusu ;
        FarmaVariables.vMatUsu  = apematusu ;
          rpta=FarmaVariables.vAceptar;
         // return rpta;
      } catch (Exception e)
      {
        FarmaVariables.vNuSecUsu  = numsec ;
        FarmaVariables.vIdUsu  = idusu ;
        FarmaVariables.vNomUsu  = nomusu ;
        FarmaVariables.vPatUsu  = apepatusu ;
        FarmaVariables.vMatUsu  = apematusu ;
        rpta = false;
        e.printStackTrace();
        FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
      }
      return rpta;
    }
    
    /**
     * Busca sobres recien creados
     * @author ASOSA
     * @since 09.08.2010
     * @return
     */
    private boolean buscarSobres(){
        int cant=tblDetallePago.getRowCount();
        String ind="";
        String codSobre="";
        boolean flag=false;
        for(int c=0;c<cant;c++){
            ind=FarmaUtility.getValueFieldJTable(tblDetallePago,c,7).toString().trim();
            codSobre=FarmaUtility.getValueFieldJTable(tblDetallePago,c,8).toString().trim();
            System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWw ind: "+ind);
            System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWw codSobre: "+codSobre);
            if(ind.equalsIgnoreCase("S") && codSobre.equalsIgnoreCase("")){
                flag=true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * Reimprime un sobre
     * @author ASOSA
     * @since 09.08.2010
     */
    private void reimprimir(){
        String indSobre = FarmaUtility.getValueFieldArrayList(tableModelDetallePago.data,tblDetallePago.getSelectedRow(),7).trim();
        String secSobre = FarmaUtility.getValueFieldArrayList(tableModelDetallePago.data,tblDetallePago.getSelectedRow(),8).trim();
        String pSecMovCaja = FarmaUtility.getValueFieldArrayList(tableModelDetallePago.data,tblDetallePago.getSelectedRow(),9).trim();
        System.out.println("pSecMovCaja CAmbio waaa: "+pSecMovCaja);
        System.out.println("SecSobre CAmbio waaa: "+secSobre);
        if(!secSobre.trim().equalsIgnoreCase("")){  //ASOSA, 13.08.2010
            UtilitySobres.reimprimirSobre_02(null,pSecMovCaja,secSobre);
        }else{
            if(indSobre.equalsIgnoreCase("S")){
                FarmaUtility.showMessage(this,"Para reimprimir debe grabar primero el sobre.",tblFormasPago);
            }else{
                FarmaUtility.showMessage(this,"La forma de pago no esta definida como sobre.",tblFormasPago);
            }
        }
    }
    
    /**
     * colectarSobresRecienDeclarados
     * @author ASOSA
     * @since 13.08.2010
     * @param pSecMovCaja
     * @return
     */
    private ArrayList colectarSobresRecienDeclarados(String pSecMovCaja)
    {
        ArrayList pLista =  new ArrayList();
        String pIndProsegur = FarmaConstants.INDICADOR_N;
        boolean indImp = false;
        try
        {
            pIndProsegur = DBCajaElectronica.getIndProsegur().trim();
            if(pIndProsegur.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            {
                DBCajaElectronica.getSobreDeclarados(pSecMovCaja,pLista);
            }
        }
        catch (SQLException sqlException)
        {
             //sqlException.printStackTrace();
              log.error(null,sqlException);
             FarmaUtility.showMessage(this, 
                                     "ERROR al colectar Sobres Recien Declarados.",null);
        }
        return pLista;
    }
    
    /**
     * Imprime sobres recien grabados y aprobados
     * @author ASOSA
     * @since 13.08.2010
     * @param listaS
     * @param pSecMovCaja
     */
    private void imprimeSobresDeclarados_0X(ArrayList listaS,String pSecMovCaja)
    {
      String pIndProsegur = FarmaConstants.INDICADOR_N;
      boolean indImp = false;
        try
        {
            pIndProsegur = DBCajaElectronica.getIndProsegur().trim();
            if(pIndProsegur.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            {
                String pCodSobre = "";      
                          String vIndImpre = DBCaja.obtieneIndImpresion();
                      System.out.println("vIndImpre :"+vIndImpre);
                      if (!vIndImpre.equals("N")) {                         
                         for(int f=0;f<listaS.size();f++){
                             pCodSobre = FarmaUtility.getValueFieldArrayList(listaS,f,0);
                             String html = DBCajaElectronica.getHtmlSobreDeclarados(pSecMovCaja,pCodSobre);
                             PrintConsejo.imprimirHtml(html.trim(),VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);
                             indImp = true;
                         }
                         if(indImp)
                            FarmaUtility.showMessage(this,
                                                     "Recoger Voucher de sobres declarados.",
                                                     null);
                     }           
            }

        }
        catch (SQLException sqlException)
        {
         //sqlException.printStackTrace();
          log.error(null,sqlException);
         FarmaUtility.showMessage(this, 
                                 "Error al obtener los datos de los sobres a imprimir.", null);

        }
     }

}
