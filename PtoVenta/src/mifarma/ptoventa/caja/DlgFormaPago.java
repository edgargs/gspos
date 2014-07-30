package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.cliente.reference.VariablesCliente;
import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenio.reference.UtilityConvenio;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.delivery.reference.DBDelivery;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFormaPago.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      01.03.2005   Creación<br>
 * ASOSA       03.07.2014   Modificación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgFormaPago extends JDialog  {

  private static final Logger log = LoggerFactory.getLogger(DlgFormaPago.class);
  /** Almacena el Objeto Frame de la Aplicación - Ventana Principal */
  public Frame myParentFrame;

  private FarmaTableModel tableModelFormasPago;
  private FarmaTableModel tableModelDetallePago;

  private boolean indPedirLogueo = true;
  private boolean indCerrarPantallaAnularPed = false;
  private boolean indCerrarPantallaCobrarPed = false;
  private String valor = "" ;
  private double diferencia = 0 ;
  
  private String descProductoRecVirtual = "";   //ASOSA - 03.07.2014
  
  //JCORTEZ 08.07.08
  private boolean indBorra=false;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JLabelFunction lblF6 = new JLabelFunction();
  private JLabelFunction lblF5 = new JLabelFunction();
  private JLabelFunction lblF1 = new JLabelFunction();
  private JLabelFunction lblF4 = new JLabelFunction();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JPanel jPanel3 = new JPanel();
  private JLabel lblRUC = new JLabel();
  private JLabel lblRUC_T = new JLabel();
  private JLabel lblRazSoc = new JLabel();
  private JLabel lblRazSoc_T = new JLabel();
  private JPanel jPanel1 = new JPanel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JPanel jPanel2 = new JPanel();
  private JButton btnFormaPago = new JButton();
  private JComboBox cmbMoneda = new JComboBox();
  //private JTextField txtMontoPagado = new JTextField();
  private JTextFieldSanSerif  txtMontoPagado  = new JTextFieldSanSerif();
  
  private JButton btnAdicionar = new JButton();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
    private JPanel pnlTotales = new JPanel();
  private XYLayout xYLayout5 = new XYLayout();
  private JLabel lblSaldoT = new JLabel();
  private JLabel lblSaldo = new JLabel();
  private JLabel lblCoPagoT = new JLabel();
  private JLabel lblCoPago = new JLabel();

  private JLabel lblVueltoT = new JLabel();
  private JLabel lblVuelto = new JLabel();
  private JPanel pnlFormaPago = new JPanel();
  private JLabel lblTipoComprobante = new JLabel();
  private JLabel lblTipoComprobante_T = new JLabel();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JPanel pnlTotal = new JPanel();
  private XYLayout xYLayout2 = new XYLayout();
  private JTextField txtNroPedido = new JTextField();
  private JButton btnPedido = new JButton();
  private JLabel lblDolares = new JLabel();
  private JLabel lblSoles = new JLabel();
  private JLabel lblDolaresT = new JLabel();
  private JLabel lblSolesT = new JLabel();
  private JLabel lblTotalPagar = new JLabel();
  private JScrollPane scrDetallePago = new JScrollPane();
  private JPanel pnlDetallePago = new JPanel();
  private XYLayout xYLayout1 = new XYLayout();
  private JButton btnDetallePago = new JButton();
  private JTable tblFormasPago = new JTable();
  private JTable tblDetallePago = new JTable();
  private JButton btnMonto = new JButton();
  private JLabel lblFecPed = new JLabel();
  private JButton btnMoneda = new JButton();
  private JLabel lblTipoCambioT = new JLabel();
  private JLabel lblTipoCambio = new JLabel();
  private JLabelFunction jLabelFunction1 = new JLabelFunction();
  private JButton btnCantidad = new JButton();
  private JTextField txtCantidad = new JTextField();
  private JLabel lblMsjPedVirtual = new JLabel();
  private JLabel lblMsjNumRecarga = new JLabel();
    private JLabelFunction lblF8 = new JLabelFunction();
    private Object rowsWithOtherColor;
    private JPanel jPanel4 = new JPanel();
    private JLabel lblDNI_SIN_COMISION = new JLabel();

    // **************************************************************************
// Constructores
// **************************************************************************
  /**
  *Constructor
  */
  public DlgFormaPago() {
    this(null, "", false);
  }

  /**
  *Constructor
  *@param parent Objeto Frame de la Aplicación.
  *@param title Título de la Ventana.
  *@param modal Tipo de Ventana.
  */
  public DlgFormaPago(Frame parent, String title, boolean modal) {
    super(parent, title, modal);
    myParentFrame = parent;
    try {
      jbInit();
      initialize();
    } catch(Exception e) {
      log.error("",e);
    }
  }

// **************************************************************************
// Método "jbInit()"
// **************************************************************************
  /**
  *Implementa la Ventana con todos sus Objetos
  */
  private void jbInit() throws Exception {
    this.setSize(new Dimension(594, 591));
    this.getContentPane().setLayout(borderLayout1);
    this.setFont(new Font("SansSerif", 0, 11));
    if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)
    {
    	this.setTitle("Cobrar Pedido por Convenio: "+VariablesConvenioBTLMF.vNomConvenio);
    	lblCoPagoT.setText("Monto Empresa :  S/.");
    	lblCoPago.setText("0.00");
    }
    else
    {
    this.setTitle("Cobrar Pedido");
    	lblCoPagoT.setText(" ");
    	lblCoPago.setText("  ");
    }

    this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
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
    jContentPane.setLayout(null);
    jContentPane.setBackground(Color.white);
    jContentPane.setSize(new Dimension(554, 504));
    lblF6.setText("[ F6 ] Pedidos/Comprobantes");
    lblF6.setBounds(new Rectangle(10, 505, 175, 20));
    lblF5.setText("[ F5 ] Pedidos Pendientes");
    lblF5.setBounds(new Rectangle(190, 480, 160, 20));
    lblF1.setText("[ F1 ] Unir Pedido");
    lblF1.setBounds(new Rectangle(190, 505, 115, 20));
    lblF4.setText("[ F4 ] Corregir Forma de Pago");
    lblF4.setBounds(new Rectangle(10, 480, 175, 20));
    lblEnter.setText("[ ENTER ] Buscar Pedido");
    lblEnter.setBounds(new Rectangle(10, 455, 145, 20));
    jPanel3.setBounds(new Rectangle(10, 80, 565, 25));
    jPanel3.setBackground(new Color(255, 130, 14));
    jPanel3.setBorder(BorderFactory.createTitledBorder(""));
    jPanel3.setLayout(null);
    lblRUC.setBounds(new Rectangle(430, 5, 90, 15));
    lblRUC.setFont(new Font("SansSerif", 1, 12));
    lblRUC.setForeground(Color.white);
    lblRUC_T.setText("RUC :");
    lblRUC_T.setBounds(new Rectangle(375, 5, 45, 15));
    lblRUC_T.setFont(new Font("SansSerif", 0, 12));
    lblRUC_T.setForeground(Color.white);
    lblRazSoc.setBounds(new Rectangle(95, 5, 275, 15));
    lblRazSoc.setFont(new Font("SansSerif", 1, 12));
    lblRazSoc.setForeground(Color.white);
    lblRazSoc_T.setText("Razon Social :");
    lblRazSoc_T.setBounds(new Rectangle(5, 5, 85, 15));
    lblRazSoc_T.setFont(new Font("SansSerif", 0, 12));
    lblRazSoc_T.setForeground(Color.white);
    jPanel1.setBounds(new Rectangle(10, 135, 565, 155));
    jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jPanel1.setBackground(Color.white);
    jPanel1.setLayout(null);
    jScrollPane1.setBounds(new Rectangle(20, 30, 300, 115));
    jScrollPane1.setBackground(new Color(255, 130, 14));
    jPanel2.setBounds(new Rectangle(20, 10, 300, 20));
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
    cmbMoneda.setBounds(new Rectangle(440, 45, 90, 20));
    cmbMoneda.setEnabled(false);
    cmbMoneda.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          cmbMoneda_mouseClicked(e);
        }
      });
    cmbMoneda.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          cmbMoneda_keyPressed(e);
        }
      });

    txtMontoPagado.setText("0.00");
    txtMontoPagado.setHorizontalAlignment(JTextField.RIGHT);
    txtMontoPagado.setBounds(new Rectangle(440, 75, 90, 20));
    txtMontoPagado.setEnabled(false);
    txtMontoPagado.setLengthText(9);
        txtMontoPagado.setMinimumSize(new Dimension(4, 10));
      
        txtMontoPagado.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
                        txtMontoPagado_mouseClicked(e);
                    }
      });
    txtMontoPagado.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        txtMontoPagado_keyPressed(e);
                    }

                public void keyTyped(KeyEvent e) {
                    txtMontoPagado_keyTyped(e);
                }
            });
    btnAdicionar.setText("Adicionar");
    btnAdicionar.setFont(new Font("SansSerif", 0, 11));
    btnAdicionar.setMnemonic('a');
    btnAdicionar.setRequestFocusEnabled(false);
    btnAdicionar.setBounds(new Rectangle(390, 110, 120, 30));
    btnAdicionar.setEnabled(false);
    btnAdicionar.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          btnAdicionar_mouseClicked(e);
        }
      });
    btnAdicionar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnAdicionar_actionPerformed(e);
                    }
      });
    lblF2.setText("[ F2 ] Corregir Pedido");
    lblF2.setBounds(new Rectangle(160, 455, 140, 20));
    lblEsc.setText("[ Esc ]  Cerrar");
    lblEsc.setBounds(new Rectangle(500, 530, 80, 20));
    lblF11.setText("[ F11 ]  Aceptar");
    lblF11.setBounds(new Rectangle(400, 530, 90, 20));
        pnlTotales.setBounds(new Rectangle(10, 405, 565, 45));
    pnlTotales.setFont(new Font("SansSerif", 0, 11));
    pnlTotales.setBackground(new Color(43, 141, 39));
    pnlTotales.setLayout(xYLayout5);


    lblCoPagoT.setFont(new Font("SansSerif", 1, 13));
    lblCoPagoT.setForeground(Color.white);

    lblCoPago.setFont(new Font("SansSerif", 1, 13));
    lblCoPago.setForeground(Color.white);
    lblCoPago.setHorizontalAlignment(SwingConstants.LEFT);

    lblSaldoT.setText("TOTAL A PAGAR :  S/.");
    lblSaldoT.setFont(new Font("SansSerif", 1, 13));
    lblSaldoT.setForeground(Color.white);
    lblSaldo.setText("0.00");
    lblSaldo.setFont(new Font("SansSerif", 1, 13));
    lblSaldo.setForeground(Color.white);
    lblSaldo.setHorizontalAlignment(SwingConstants.LEFT);
    lblVueltoT.setText("Vuelto :  S/.");
    lblVueltoT.setFont(new Font("SansSerif", 1, 13));
    lblVueltoT.setForeground(Color.white);
    lblVuelto.setText("0.00");
    lblVuelto.setFont(new Font("SansSerif", 1, 13));
    lblVuelto.setForeground(Color.white);
    pnlFormaPago.setBounds(new Rectangle(10, 50, 565, 25));
    pnlFormaPago.setFont(new Font("SansSerif", 0, 11));
    pnlFormaPago.setForeground(SystemColor.inactiveCaptionText);
    pnlFormaPago.setBorder(BorderFactory.createTitledBorder(""));
    pnlFormaPago.setBackground(Color.white);
    pnlFormaPago.setLayout(null);
    lblTipoComprobante.setForeground(new Color(43, 141, 39));
    lblTipoComprobante.setFont(new Font("SansSerif", 1, 12));
    lblTipoComprobante.setBounds(new Rectangle(165, 5, 120, 15));
    lblTipoComprobante_T.setText("Tipo de Comprobante :");
    lblTipoComprobante_T.setForeground(new Color(43, 141, 39));
    lblTipoComprobante_T.setFont(new Font("SansSerif", 1, 12));
    lblTipoComprobante_T.setBounds(new Rectangle(0, 5, 155, 15));
    lblF3.setText("[ F3 ] Cambiar Tipo Comprobante");
    lblF3.setBounds(new Rectangle(305, 455, 195, 20));
    pnlTotal.setBounds(new Rectangle(10, 5, 565, 40));
    pnlTotal.setFont(new Font("SansSerif", 0, 11));
    pnlTotal.setBorder(BorderFactory.createTitledBorder(""));
    pnlTotal.setBackground(new Color(43, 141, 39));
    pnlTotal.setLayout(xYLayout2);
    txtNroPedido.setFont(new Font("SansSerif", 0, 12));
    txtNroPedido.setDocument(new FarmaLengthText(4));
    txtNroPedido.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          txtNroPedido_actionPerformed(e);
        }
      });
    txtNroPedido.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          txtNroPedido_mouseClicked(e);
        }
      });
    txtNroPedido.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        txtNroPedido_keyPressed(e);
                    }
      });
    btnPedido.setText("Pedido :");
    btnPedido.setDefaultCapable(false);
    btnPedido.setRequestFocusEnabled(false);
    btnPedido.setBorderPainted(false);
    btnPedido.setFocusPainted(false);
    btnPedido.setContentAreaFilled(false);
    btnPedido.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnPedido.setHorizontalAlignment(SwingConstants.LEFT);
    btnPedido.setMnemonic('p');
    btnPedido.setFont(new Font("SansSerif", 1, 13));
    btnPedido.setForeground(Color.white);
    btnPedido.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPedido_actionPerformed(e);
        }
      });
    lblDolares.setText("0.00");
    lblDolares.setFont(new Font("SansSerif", 1, 13));
    lblDolares.setHorizontalAlignment(SwingConstants.RIGHT);
    lblDolares.setForeground(Color.white);
    lblSoles.setText("0.00");
    lblSoles.setFont(new Font("SansSerif", 1, 13));
    lblSoles.setHorizontalAlignment(SwingConstants.RIGHT);
    lblSoles.setForeground(Color.white);
    lblDolaresT.setText("US$");
    lblDolaresT.setFont(new Font("SansSerif", 1, 13));
    lblDolaresT.setForeground(Color.white);
    lblSolesT.setText("S/.");
    lblSolesT.setFont(new Font("SansSerif", 1, 13));
    lblSolesT.setForeground(Color.white);
    lblTotalPagar.setText("TOTAL VENTA :");
    lblTotalPagar.setFont(new Font("SansSerif", 1, 13));
    lblTotalPagar.setForeground(Color.white);
    scrDetallePago.setBounds(new Rectangle(10, 325, 565, 80));
    scrDetallePago.setFont(new Font("SansSerif", 0, 11));
    scrDetallePago.setBackground(new Color(255, 130, 14));
    pnlDetallePago.setBounds(new Rectangle(10, 300, 565, 25));
    pnlDetallePago.setFont(new Font("SansSerif", 0, 11));
    pnlDetallePago.setBackground(new Color(255, 130, 14));
    pnlDetallePago.setLayout(xYLayout1);
    btnDetallePago.setText("Detalle de Pago :");
    btnDetallePago.setFont(new Font("SansSerif", 1, 11));
    btnDetallePago.setForeground(Color.white);
    btnDetallePago.setHorizontalAlignment(SwingConstants.LEFT);
    btnDetallePago.setMnemonic('d');
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
    tblFormasPago.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          tblFormasPago_mouseClicked(e);
        }
      });
    tblFormasPago.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblFormasPago_keyPressed(e);
        }
      });
    tblDetallePago.setFont(new Font("SansSerif", 0, 11));
    tblDetallePago.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          tblDetallePago_mouseClicked(e);
        }
      });
    tblDetallePago.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblDetallePago_keyPressed(e);
        }
      });
    btnMonto.setText("Monto : ");
    btnMonto.setBounds(new Rectangle(355, 75, 65, 25));
    btnMonto.setBorderPainted(false);
    btnMonto.setContentAreaFilled(false);
    btnMonto.setDefaultCapable(false);
    btnMonto.setFocusPainted(false);
    btnMonto.setHorizontalAlignment(SwingConstants.RIGHT);
    btnMonto.setMnemonic('m');
    btnMonto.setRequestFocusEnabled(false);
    btnMonto.setFont(new Font("SansSerif", 0, 11));
    btnMonto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnMonto.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnMonto_actionPerformed(e);
        }
      });
    lblFecPed.setFont(new Font("SansSerif", 1, 12));
    lblFecPed.setForeground(Color.white);
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
    btnMoneda.setBounds(new Rectangle(360, 45, 60, 20));
    btnMoneda.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnMoneda_actionPerformed(e);
        }
      });
    lblTipoCambioT.setText("Tipo Cambio : ");
    lblTipoCambioT.setBounds(new Rectangle(395, 5, 85, 15));
    lblTipoCambioT.setForeground(new Color(43, 141, 39));
    lblTipoCambioT.setFont(new Font("SansSerif", 1, 12));
    lblTipoCambio.setBounds(new Rectangle(500, 5, 55, 15));
    lblTipoCambio.setFont(new Font("SansSerif", 1, 12));
    lblTipoCambio.setForeground(new Color(43, 141, 39));
    jLabelFunction1.setBounds(new Rectangle(355, 480, 195, 20));
    jLabelFunction1.setText("[ F7 ] Configurar Comprobantes");
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
    btnCantidad.setBounds(new Rectangle(360, 15, 60, 20));
    btnCantidad.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCantidad_actionPerformed(e);
        }
      });
    txtCantidad.setText("0");
    txtCantidad.setHorizontalAlignment(JTextField.RIGHT);
    txtCantidad.setBounds(new Rectangle(440, 15, 90, 20));
    txtCantidad.setEnabled(false);
    txtCantidad.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          txtCantidad_mouseClicked(e);
        }
      });
    txtCantidad.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtCantidad_keyPressed(e);
        }
      });
    lblMsjPedVirtual.setForeground(Color.red);
    lblMsjPedVirtual.setFont(new Font("SansSerif", 1, 13));
    lblMsjPedVirtual.setBounds(new Rectangle(10, 110, 445, 20));
    lblMsjNumRecarga.setForeground(new Color(43, 141, 39));
    lblMsjNumRecarga.setFont(new Font("SansSerif", 1, 17));
    lblMsjNumRecarga.setBounds(new Rectangle(445, 110, 125, 20));
        lblF8.setBounds(new Rectangle(315, 505, 140, 20));
        lblF8.setText("[ F8 ] Ingreso Sobres");
      
      lblDNI_SIN_COMISION.setText("DNI Inválido. No aplica Prog. Atención al Cliente");
        lblDNI_SIN_COMISION.setForeground(new Color(231, 0, 0));
      lblDNI_SIN_COMISION.setFont(new Font("Dialog", 3, 14));
      lblDNI_SIN_COMISION.setBackground(Color.white);
      lblDNI_SIN_COMISION.setOpaque(true);
      lblDNI_SIN_COMISION.setVisible(true);

        jPanel3.add(lblRUC, null);
    jPanel3.add(lblRUC_T, null);
    jPanel3.add(lblRazSoc, null);
    jPanel3.add(lblRazSoc_T, null);
    jPanel1.add(btnMoneda, null);
    jPanel1.add(btnMonto, null);
    jScrollPane1.getViewport().add(tblFormasPago, null);
    jPanel1.add(jScrollPane1, null);
    jPanel2.add(btnFormaPago, null);
    jPanel1.add(jPanel2, null);
    jPanel1.add(cmbMoneda, null);
    jPanel1.add(txtMontoPagado, null);
    jPanel1.add(btnAdicionar, null);
    jPanel1.add(btnCantidad, null);
    jPanel1.add(txtCantidad, null);
    jScrollPane1.getViewport();
        pnlTotales.add(lblSaldoT, new XYConstraints(130, 20, 150, 20));
        pnlTotales.add(lblSaldo, new XYConstraints(285, 20, 105, 20));
        pnlTotales.add(lblCoPagoT, new XYConstraints(140, 0,140, 20));
        pnlTotales.add(lblCoPago, new XYConstraints(285, 0, 105, 20));
        pnlTotales.add(lblVueltoT, new XYConstraints(395, 20, 80, 20));
        pnlTotales.add(lblVuelto, new XYConstraints(480, 20, 70, 20));
    pnlFormaPago.add(lblTipoCambio, null);
    pnlFormaPago.add(lblTipoCambioT, null);
    pnlFormaPago.add(lblTipoComprobante, null);
    pnlFormaPago.add(lblTipoComprobante_T, null);
    pnlTotal.add(lblFecPed, new XYConstraints(130, 5, 70, 20));
    pnlTotal.add(txtNroPedido, new XYConstraints(65, 0, 55, 25));
    pnlTotal.add(btnPedido, new XYConstraints(0, 5, 60, 20));
    pnlTotal.add(lblDolares, new XYConstraints(470, 5, 70, 20));
        pnlTotal.add(lblSoles, new XYConstraints(365, 5, 55, 20));
        pnlTotal.add(lblDolaresT, new XYConstraints(435, 5, 35, 20));
        pnlTotal.add(lblSolesT, new XYConstraints(315, 5, 45, 20));
        pnlTotal.add(lblTotalPagar, new XYConstraints(210, 5, 105, 20));
        scrDetallePago.getViewport();
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.getContentPane().add(jPanel4, BorderLayout.NORTH);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblMsjNumRecarga, null);
        jContentPane.add(lblMsjPedVirtual, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblF6, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(jPanel3, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(pnlTotales, null);
        jContentPane.add(pnlFormaPago, null);
        jContentPane.add(pnlTotal, null);
        scrDetallePago.getViewport().add(tblDetallePago, null);
        jContentPane.add(scrDetallePago, null);
        pnlDetallePago.add(lblDNI_SIN_COMISION,
                           new XYConstraints(245, 0, 320, 25));
        pnlDetallePago.add(btnDetallePago, new XYConstraints(10, 5, 115, 15));
        jContentPane.add(pnlDetallePago, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF2, null);
        //this.getContentPane().add(jContentPane, null);


         //Agregado Por FRAMIREZ  11.05.2012
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)
        	&& VariablesConvenioBTLMF.vCodConvenio != null
        	&& VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0 )
        {
        	lblF5.setVisible(false);
        	lblF3.setVisible(false);
        	jLabelFunction1.setVisible(false);
        	lblF8.setVisible(false);
        	lblF6.setVisible(false);
        }
  }

// **************************************************************************
// Método "initialize()"
// **************************************************************************
  private void initialize()
  {
    initTableFormasPago();
    initTableDetallePago();
    cargaCombo();
    FarmaVariables.vAceptar=false;
  }

// **************************************************************************
// Métodos de inicialización
// **************************************************************************
  private void initTableFormasPago()
  {
    tableModelFormasPago = new FarmaTableModel(ConstantsCaja.columsListaFormasPago,ConstantsCaja.defaultListaFormasPago,0);
    FarmaUtility.initSimpleList(tblFormasPago,tableModelFormasPago,ConstantsCaja.columsListaFormasPago);
      //FarmaUtility.initSimpleList(tblFormasPago, tableModelFormasPago, ConstantsCaja.columsListaFormasPago,rowsWithOtherColor,Color.white,Color.red,false);
  }
  /**
   * Paremtros añadidos para el listado de Formas de Pago
   * @author : dubilluz
   * @since  : 07.09.2007
   */
  
   /*
  private boolean cargaFormasPago(String indConvenio,String codConvenio,String codCliente)
  {
  log.debug("Cargando Formas de Pago");
  String numPed=VariablesCaja.vNumPedVta;
    try{
      DBCaja.obtieneFormasPago(tableModelFormasPago,indConvenio,codConvenio,codCliente,numPed);
      FarmaUtility.ordenar(tblFormasPago, tableModelFormasPago, 0, FarmaConstants.ORDEN_ASCENDENTE);
      String fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
      lblFecPed.setText(fechaSistema);
      return true;
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this, "Error al obtener las Formas de Pago.\n" + sql.getMessage(), txtNroPedido);
      return false;
    }
  }
   */
    private boolean cargaFormasPago(String indConvenio,String codConvenio,String codCliente)
    {
      log.info("Metodo Cargando Formas de Pago :" + indConvenio);
    String numPed=VariablesCaja.vNumPedVta;
  
    String creditoSaldo="";
    String esCredito ="";
    String valorCredito="";
    boolean valor = false;

     if (indConvenio.equalsIgnoreCase("S"))
     {

    	 if(!UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null))
    	  {


         //Verificar si el tipo de convenio posee credito (si porcentaje = 100)
         try{ esCredito = DBConvenio.obtenerPorcentajeCopago(codConvenio); } catch (SQLException e) { log.error("",e);FarmaUtility.showMessage(this, "Error.", null);}

         //Si posee credito ,verificar si tiene saldo disponible
         if (esCredito.equalsIgnoreCase("S")){
             
            try{ creditoSaldo  = DBConvenio.obtieneConvenioCredito(codConvenio,codCliente,FarmaConstants.INDICADOR_S );} catch (SQLException e) { log.error("",e);FarmaUtility.showMessage(this, "Error.", null);}
             
             if (creditoSaldo.equalsIgnoreCase("S"))
                 valorCredito ="S";
             else
                 
                 valorCredito ="N";
         }
    
         else
                 valorCredito ="N";
         
         log.info("codConvenio : " +  codConvenio);
         log.info("codCliente : "  +  codCliente);
         log.info("valorCredito : "  +  valorCredito);
         
         
       
             
         
       valor = cargaFormasPagoConvenio(indConvenio,codConvenio,codCliente,valorCredito);
       
           
           //cargaFormasPagoConvenio(indConvenio,codConvenio,codCliente,cantCliente,valorCredito); 
          log.info("--------------Forma de Pago Convenio-------------");

     }


     }
     else
     {
          valor = cargaFormasPagoSinConvenio(indConvenio,codConvenio, codCliente);
             log.info("---------Forma de Pago Sin Convenio----------");

         }
         return valor;
    }

     

      private boolean cargaFormasPagoConvenio(String indConvenio,String codConvenio,String codCliente,String valorCredito) {

          log.debug("Cargando Formas de Pago Convenio");
          String numPed=VariablesCaja.vNumPedVta;
          
          
          
            try{
               
              DBCaja.obtieneFormasPagoConvenio(tableModelFormasPago,indConvenio,codConvenio,codCliente,numPed,valorCredito);
              FarmaUtility.ordenar(tblFormasPago, tableModelFormasPago, 0, FarmaConstants.ORDEN_ASCENDENTE);
              String fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
              lblFecPed.setText(fechaSistema);
              return true;
            } catch(SQLException sql)
            {
              log.error("",sql);
              FarmaUtility.showMessage(this, "Error al obtener las Formas de Pago Convenio.\n" + sql.getMessage(), txtNroPedido);
              return false;
            }

          
      }

      //Agregado Por FRAMIREZ 12.01.2012
      private boolean cargaFormasPagoConvenio(String codConvenio) {

          log.debug("<<<<Cargando Formas de Pago Convenio BTLMF>>>>");
            try{

              DBConvenioBTLMF.obtieneFormasPagoConvenio(tableModelFormasPago,codConvenio);
              FarmaUtility.ordenar(tblFormasPago, tableModelFormasPago, 0, FarmaConstants.ORDEN_ASCENDENTE);
              String fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
              lblFecPed.setText(fechaSistema);
              return true;
            } catch(SQLException sql)
            {
              log.error("",sql);
              FarmaUtility.showMessage(this, "Error al obtener las Formas de Pago Convenio BTLMF.\n" + sql.getMessage(), txtNroPedido);
              return false;
            }
      }



      private boolean cargaFormasPagoSinConvenio(String indConvenio,String codConvenio,String codCliente)
      {
      log.debug("Cargando Formas de Pago Sin Convenio");
      //String numPed=VariablesCaja.vNumPedVta;
      //DUBILLUZ - 09.06.2011
      String numPed= "";
      if(VariablesVentas.vNum_Ped_Vta.trim().length()>0){
          numPed= VariablesVentas.vNum_Ped_Vta.trim();
      }
      else
          if(VariablesCaja.vNumPedVta.trim().length()>0){
              numPed= VariablesCaja.vNumPedVta.trim();
          }
          
        try{
          DBCaja.obtieneFormasPagoSinConvenio(tableModelFormasPago,indConvenio,codConvenio,codCliente,numPed);
          FarmaUtility.ordenar(tblFormasPago, tableModelFormasPago, 0, FarmaConstants.ORDEN_ASCENDENTE);
          String fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
          lblFecPed.setText(fechaSistema);
          return true;
        } catch(SQLException sql)
        {
          log.error("",sql);
          FarmaUtility.showMessage(this, "Error al obtener las Formas de Pago Sin Convenio.\n" + sql.getMessage(), txtNroPedido);
          return false;
        }
      }
  private void initTableDetallePago()
  {
    tableModelDetallePago = new FarmaTableModel(ConstantsCaja.columsListaDetallePago,ConstantsCaja.defaultListaDetallePago,0);
    FarmaUtility.initSimpleList(tblDetallePago,tableModelDetallePago,ConstantsCaja.columsListaDetallePago);
  }

  private void cargaCombo()
  {
    FarmaLoadCVL.loadCVLfromArrays(cmbMoneda,FarmaConstants.HASHTABLE_MONEDA,FarmaConstants.MONEDAS_CODIGO,FarmaConstants.MONEDAS_DESCRIPCION,true);
  }

// **************************************************************************
// Metodos de eventos
// **************************************************************************
  private void btnPedido_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtNroPedido);
    txtMontoPagado.setText("0.00");
    txtMontoPagado.selectAll();
    txtMontoPagado.setEnabled(false);
  }

  private void this_windowOpened(WindowEvent e)
  {
      VariablesCaja.vNumPedVtaOrigenCOBRO = "";

      if(VariablesFidelizacion.vSIN_COMISION_X_DNI)lblDNI_SIN_COMISION.setVisible(true);
      else lblDNI_SIN_COMISION.setVisible(false);
      
      
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtNroPedido);
    lblMsjPedVirtual.setVisible(false);
    lblMsjNumRecarga.setVisible(false);
    VariablesCaja.vIndPedidoConProdVirtual = false;
    //Reinicia Variables
    initVariables_Auxiliares();

    log.info("<<<<<<<<<<indPedirLogueo>>>>>>>>>>>:"+indPedirLogueo);
      txtNroPedido.setText("" + VariablesCaja.vNumPedPendiente);
        

    if(indPedirLogueo){
      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      dlgLogin.setRolUsuario(FarmaConstants.ROL_CAJERO);
      dlgLogin.setVisible(true);
      if ( FarmaVariables.vAceptar ) {
        FarmaVariables.dlgLogin = dlgLogin;
        if(!UtilityCaja.existeCajaUsuarioImpresora(this, null)) cerrarVentana(false);
        FarmaVariables.vAceptar = false;

        log.debug("<<<<<<<<<<esActivoConvenioBTLMF>>>>>>>>>>>:");
        //Agregado Por FRAMIREZ CargaForma de Pago por convenio
        if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio.trim().length()>0)
  	    {
  	        cargaFormasPagoConvenio(VariablesConvenioBTLMF.vCodConvenio);
                /*KeyEvent ke = new KeyEvent( txtNroPedido, KeyEvent.KEY_PRESSED,    
  	                                       0,                          // When timeStamp   
  	                                       0,                          // Modifier   
  	                                       KeyEvent.VK_ENTER,      // Key Code   
  	                                       KeyEvent.CHAR_UNDEFINED );  // Key Char   

                txtNroPedido_keyPressed(ke);*/

  	    }
        else
        {
        cargaFormasPago("N","N","0");
        }


      } else  cerrarVentana(false);
    } else
    {
      if(!UtilityCaja.existeCajaUsuarioImpresora(this, null) || !UtilityCaja.validaFechaMovimientoCaja(this,tblFormasPago))
      {
        FarmaUtility.showMessage(this, "El Pedido sera Anulado. Vuelva a generar uno nuevo.", null);
        try{
          DBCaja.anularPedidoPendiente(VariablesVentas.vNum_Ped_Vta);
            
            //JCORTEZ 07.01.09 devuelve canje o historico
            log.debug("Devolviendo canje o Historico");      
            anularAcumuladoCanje();
            VariablesCaja.vCierreDiaAnul=false;
            
          FarmaUtility.aceptarTransaccion();
          FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
          cerrarVentana(true);
          return;
        } catch(SQLException sql)
        {
          FarmaUtility.liberarTransaccion();
          log.error("",sql);
          FarmaUtility.showMessage(this, "Error al Anular el Pedido.\n" + sql.getMessage(), null);
          cerrarVentana(true);
          return;
        }
      }
      
      if(!validaPedidoDiario()) return;
      
        //Agregado Por FRAMIREZ 12.01.2012 CargaForma de Pago por convenio
        if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)
            {
                log.debug("/*************************GOGO INICIO *******************************/");
                cargaFormasPagoConvenio(VariablesConvenioBTLMF.vCodConvenio);
                /*KeyEvent ke = new KeyEvent( txtNroPedido, KeyEvent.KEY_PRESSED,    
                                               0,                          // When timeStamp   
                                               0,                          // Modifier   
                                               KeyEvent.VK_ENTER,      // Key Code   
                                               KeyEvent.CHAR_UNDEFINED );  // Key Char   

                //KeyEvent.VK_ENTER
                 //cargaFormasPagoConvenio(VariablesConvenioBTLMF.vCodConvenio);
                txtNroPedido_keyPressed(ke);   */        
                log.debug("/*************************GOGO FIN *******************************/");
                log.debug("/*GOGO1*/"+tableModelDetallePago.getRowCount());

            }
        else
        {
        if(!cargaFormasPago("N","N","0")) return;

        }        
        
        buscaPedidoDiario();
        
      VariablesCaja.vNumPedPendiente = "";
      VariablesCaja.vFecPedACobrar = "";
      FarmaVariables.vAceptar = false;
      FarmaUtility.moveFocus(tblFormasPago);
        log.debug("/*GOGO5*/"+tableModelDetallePago.getRowCount());
      if(VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) btnFormaPago.doClick();
        log.debug("/*GOGO6*/"+tableModelDetallePago.getRowCount());
      //btnFormaPago.doClick();
    }
    
    if(FarmaVariables.vTipCaja.equals(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL))
    {
      lblF2.setVisible(false);
      lblF1.setVisible(false);
    }
      log.debug("/***************************************************************/");
      VariablesCaja.mostrarValoresVariables();
  }

  private void tblFormasPago_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      validaFormaPagoSeleccionada();
    } else if(e.getKeyCode() == KeyEvent.VK_F3)
    {

    	//Agregado Por FRAMIREZ 11.05.2012
    	if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)
    	        	&& VariablesConvenioBTLMF.vCodConvenio != null
    	        	&& VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0 )
    	 {
    		          //Funcion deshabilitado
    	 }
    	else
    	 {
      cambioTipoComprobante();
    }
    }
    chkkeyPressed(e);
  }

  private void tblDetallePago_keyPressed(KeyEvent e)
  {
    chkkeyPressed(e);
  }

  private void txtNroPedido_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      try{
        String fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        lblFecPed.setText(fechaSistema);
      } catch(SQLException sql)
      {
        //log.error("",sql);
        log.error(null,sql);
        FarmaUtility.showMessage(this,"Error al obtener fecha del sistema - \n" +sql.getMessage(),txtNroPedido);
      }
      //lblFecPed.setText("" + VariablesCaja.vFecPedACobrar);
      if(!validaPedidoDiario()) return;
      
      buscaPedidoDiario();
      //log.debug("**enter "+tblFormasPago.getSelectedRow());
      if(VariablesCaja.cobro_Pedido_Conv_Credito.equalsIgnoreCase("N") &&
         VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
          btnFormaPago.doClick();
      }

        lblTipoComprobante.setVisible(true);
        lblTipoComprobante_T.setVisible(true);
      //COLOCAR COMPROBANTES A IMPRIMIR
      if(VariablesConvenioBTLMF.vCodConvenio.trim().length()>0){
          lblTipoComprobante_T.setBounds(new Rectangle(0, 5, 390, 15));
          lblTipoComprobante_T.setText(getMensajeComprobanteConvenio(VariablesConvenioBTLMF.vCodConvenio.trim()));
          lblTipoComprobante.setVisible(false);
      }
      else{
          lblTipoComprobante_T.setBounds(new Rectangle(0, 5, 155, 15));
          lblTipoComprobante.setVisible(true);
      }
    }
    chkkeyPressed(e);
  }

  private void btnMonto_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtMontoPagado);
    //txtMontoPagado.selectAll();
  }

  private void btnAdicionar_actionPerformed(ActionEvent e)
  {
    adicionaDetallePago();
  }

  private void btnFormaPago_actionPerformed(ActionEvent e)
  {
    if(tblFormasPago.getRowCount() > 0 && VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
      FarmaGridUtils.showCell(tblFormasPago,0,0);
      FarmaUtility.moveFocus(tblFormasPago);
      /**
       * Adicionado
       * @author  dubilluz
       * @since   10.09.2007
       */
      txtMontoPagado.setText("0.00");
      txtMontoPagado.setEnabled(false);
      cmbMoneda.setEnabled(false);      
      btnAdicionar.setEnabled(false);        
      log.debug("foco a tblFormaPago");      
    }
  }

  private void btnMoneda_actionPerformed(ActionEvent e)
  {
    if(VariablesCaja.vIndCambioMoneda)
    {
      FarmaUtility.moveFocus(cmbMoneda);
    }
  }

  private void txtMontoPagado_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
        if(!isFormatoValidoMonto(txtMontoPagado.getText().trim())){
            txtMontoPagado.selectAll();
            FarmaUtility.showMessage(this,"El monto debe estar en el Rango de 0.00 hasta 999,999.99", txtMontoPagado);
            e.consume();
        }
        else
            btnAdicionar.doClick();
    } else{
        chkkeyPressed(e);
    }
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  private void cmbMoneda_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN ||
       e.getKeyCode() == KeyEvent.VK_PAGE_UP || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN){
      cmbMoneda.setEnabled(false);
      return;
    }
    if(e.getKeyCode() == KeyEvent.VK_ENTER){
      FarmaUtility.moveFocus(txtMontoPagado);
      seleccionaTarjetaCliente();
    } else
      chkkeyPressed(e);
  }

  private void btnDetallePago_actionPerformed(ActionEvent e)
  {
    if(tblDetallePago.getRowCount() == 0) return;
    FarmaUtility.moveFocusJTable(tblDetallePago);
  }
  
    private void btnCantidad_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtCantidad);
  }

  private void txtCantidad_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER){
      String cantidad = txtCantidad.getText().trim();
      String codFormaPago = ((String)tblFormasPago.getValueAt(tblFormasPago.getSelectedRow(),0)).trim();
      double montoPedido = FarmaUtility.getDecimalNumber(lblSoles.getText().trim());
      double result=0;
      if(cantidad.equalsIgnoreCase("") || cantidad.length() <= 0)
      {
        FarmaUtility.showMessage(this, "Ingrese una cantidad.", txtCantidad);
        return;
      }
      if(!FarmaUtility.isInteger(cantidad) || Integer.parseInt(cantidad) < 1)
      {
        FarmaUtility.showMessage(this, "Ingrese una cantidad correcta.", txtCantidad);
        return;
      }
      
      double monto = obtieneMontoFormaPagoCupon(codFormaPago, cantidad);//promocion
      
       //JCORTEZ 25/06/08 se valida cobro de pedido por cupones
      ArrayList array =new ArrayList();
        try{
         DBCaja.obtieneMontoFormaPagoCuponCampaña(array,codFormaPago, cantidad);
        }catch(SQLException sql){
         log.error("",sql);
         FarmaUtility.showMessage(this,"Error al obtener cantidad de cupones - \n" +sql.getMessage(),txtCantidad);
        }
      String cantCuponMax="";
      String montoCupon="";
     
      if(array.size()>0){
       cantCuponMax=((String)((ArrayList) array.get(0)).get(0)).trim();
       montoCupon=((String) ((ArrayList) array.get(0)).get(1)).trim();
        log.debug("cantCuponMax : "+cantCuponMax);
        log.debug("MontoCupon : "+montoCupon);
        if(Integer.parseInt(cantCuponMax)>=Integer.parseInt(cantidad)){
          //result=montoPedido-(Integer.parseInt(cantidad)*Integer.parseInt(montoCupon));
          result=Integer.parseInt(cantidad)*Integer.parseInt(montoCupon);
        }else{
          FarmaUtility.showMessage(this, "Se esta usando un numero de cupones mayor al permitido. Verifique!!!", txtCantidad);
          return;
        }
      }else{
       FarmaUtility.showMessage(this, "No es posible realizar el pago por cupon. Verifique!!!", txtCantidad);
       return;
      }
    
      //if( monto <= 0.00 && Integer.parseInt(cantCuponMax)<=0.00)
      if(Integer.parseInt(cantCuponMax)<=0.00)
      {
        FarmaUtility.showMessage(this, "Este pedido no puede ser cobrado con esta forma de pago. Verifique!!!", txtCantidad);
        return;
      }
      
     /* if( monto > montoPedido )
      {
        monto = montoPedido;
        txtMontoPagado.setText("" + monto);
      }*///txtMontoPagado.setText("" + monto);
      
      txtMontoPagado.setText("" + result);
      btnAdicionar.doClick();
    } else chkkeyPressed(e);
  }

// **************************************************************************
// Metodos auxiliares de eventos
// **************************************************************************
  private void chkkeyPressed(KeyEvent e)
  {
    if(UtilityPtoVenta.verificaVK_F1(e))
    {
        if(!FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL))
        {        
          DlgPedidosBuscar dlgPedidosBuscar = new DlgPedidosBuscar(myParentFrame,"",true);
          dlgPedidosBuscar.setVisible(true);
          if(FarmaVariables.vAceptar)
            txtNroPedido.setText(VariablesCaja.vNumPedPendiente);
        }
    } else if(UtilityPtoVenta.verificaVK_F2(e))
    {
       log.debug("armaVariables.vTipCaja:::"+FarmaVariables.vTipCaja);

      if(!FarmaVariables.vTipCaja.equals(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL))
      {
        indBorra=true;//jcortez 08.07.08
        limpiarDatos();
        limpiarPagos();
        FarmaUtility.moveFocus(txtNroPedido);
      }

      //Agregado por FRAMIREZ 27.03.2012
      if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null && FarmaVariables.vTipCaja.length() > 0)
      {
    	  indBorra=true;//jcortez 08.07.08
          limpiarDatos();
          limpiarPagos();
          FarmaUtility.moveFocus(txtNroPedido);
      }

    } else if(e.getKeyCode() == KeyEvent.VK_F4)
    {
        
      if (VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
          if(tblDetallePago.getRowCount() > 0 && validaFomaPagoConvenio()) /*VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S)*/
          {
            FarmaUtility.showMessage(this, "Este pedido es un convenio.\n" +
              "Las formas de pago no pueden ser eliminadas.\n" +
              "Presione F2 si desea reiniciar el cobro del pedido.", tblFormasPago);
            return;
          }

          //Agregado por FRAMIREZ 27.03.2012
          if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null 
              && !VariablesConvenioBTLMF.vCodConvenio.equals("")
              && FarmaVariables.vTipCaja.length() > 0)
          {
        	  FarmaUtility.showMessage(this, "Este pedido es un convenio.\n" +
                      "Las formas de pago no pueden ser eliminadas.\n" +
                      "Presione F2 si desea reiniciar el cobro del pedido.", tblFormasPago);
                 return;
          }

          limpiarPagos();
          btnFormaPago.doClick();
      }
    } else if(e.getKeyCode() == KeyEvent.VK_F5)
    {
        
    	//Agregado Por FRAMIREZ 11.05.2012
    	if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)
    	        	&& VariablesConvenioBTLMF.vCodConvenio != null
    	        	&& VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0 )
    	 {
    		          //Funcion deshabilitado
    	 }
    	else
    	 {
      if(VariablesCaja.vNumPedVtaOrigenCOBRO.trim().length()==0)
         VariablesCaja.vNumPedVtaOrigenCOBRO = VariablesCaja.vNumPedVta;  
            
      DlgPedidosPendientes dlgPedidosPendientes = new DlgPedidosPendientes(myParentFrame,"",true);
      dlgPedidosPendientes.setVisible(true);
      if(FarmaVariables.vAceptar)
      {
      
         indBorra=false;
         
        //log.debug("VariablesCaja.vIndConvenio : "+VariablesCaja.vIndConvenio);
        //log.debug("VariablesCaja.vCodConvenio : "+VariablesCaja.vCodConvenio);
        //log.debug("VariablesCaja.vCodCliLocal : "+VariablesCaja.vCodCliLocal);
         
        cargaFormasPago(VariablesCaja.vIndConvenio,VariablesCaja.vCodConvenio,VariablesCaja.vCodCliLocal);
        

        txtNroPedido.setText("" + VariablesCaja.vNumPedPendiente);
        lblFecPed.setText("" + VariablesCaja.vFecPedACobrar);
        if(!validaPedidoDiario()) return;
        buscaPedidoDiario();
        
        //JCORTEZ - no se deben borrar las variables del pedido luego de seleccinar
        /*VariablesCaja.vNumPedPendiente = "";
        VariablesCaja.vFecPedACobrar = "";*/
        
        FarmaVariables.vAceptar = false;
        //añadido 21.09.2007 dubilluz
        log.debug("VariablesCaja.cobro_Pedido_Conv_Credito : "+VariablesCaja.cobro_Pedido_Conv_Credito);
        if(VariablesCaja.cobro_Pedido_Conv_Credito.equalsIgnoreCase("N"))
        {
        log.debug("VariablesCaja.cobro_Pedido_Conv_Credito : "+VariablesCaja.cobro_Pedido_Conv_Credito); 
        if(VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) btnFormaPago.doClick();
        }
        else{
        FarmaUtility.moveFocus(txtMontoPagado);
        btnMonto.doClick();
        log.debug("Foco lo coloco en TxtMonto  2222");
        }
        
        verificaMontoPagadoPedido();//jcortez

     }
       }
    } else if(e.getKeyCode() == KeyEvent.VK_F6)
    {

    	//Agregado Por FRAMIREZ 11.05.2012
    	if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)
    	        	&& VariablesConvenioBTLMF.vCodConvenio != null
    	        	&& VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0 )
    	 {
    		          //Funcion deshabilitado
    	 }
    	else
    	 {
      VariablesCaja.vSecMovCajaOrigen = VariablesCaja.vSecMovCaja;
      VariablesPtoVenta.vSecMovCajaOrigen = VariablesCaja.vSecMovCaja;
      VariablesPtoVenta.vTipAccesoListaComprobantes=ConstantsPtoVenta.TIP_ACC_LISTA_COMP_CAJA;
      DlgReportePedidosComprobantes dlgReportePedidosComprobantes = new DlgReportePedidosComprobantes(myParentFrame,"",true);
      dlgReportePedidosComprobantes.setVisible(true);

    	}
    } else if(e.getKeyCode() == KeyEvent.VK_F7)
    {

    	//Agregado Por FRAMIREZ 11.05.2012
    	if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)
    	        	&& VariablesConvenioBTLMF.vCodConvenio != null
    	        	&& VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0 )
    	 {
    		          //Funcion deshabilitado
    	 }
    	else
    	{
      configuracionComprobante();
    	}
    }else if(e.getKeyCode() == KeyEvent.VK_F8)
    {

    	//Agregado Por FRAMIREZ 11.05.2012
    	if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)
    	        	&& VariablesConvenioBTLMF.vCodConvenio != null
    	        	&& VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0 )
    	 {
    		          //Funcion deshabilitado
    	 }
    	else
         {
       //JCORTEZ 03.10.09 Se verifica si se permite o no ingreso de sobres
        if(validaIngresoSobre())
         mostrarIngresoSobres();     
        else
        FarmaUtility.showMessage(this, "Opción no habilitada por el momento", null);
         }

    } else if(UtilityPtoVenta.verificaVK_F11(e))
    {
        if(!UtilityCaja.existeStockPedido(this,VariablesCaja.vNumPedVta))
          return;
        
        //JCHAVEZ 09.07.2009.sn graba el tiempo dei nicio de cobro
        try{
            DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"I");
            FarmaUtility.aceptarTransaccion();
            log.debug("Grabo el tiempo de inicio de cobro");
        }
        catch(SQLException sql){
            //FALTA CORREGIR ESTO
            FarmaUtility.liberarTransaccion(); //ASOSA 22.02.2010            
            log.error("",sql);
            log.debug("Error al grabar el tiempo de inicio de cobro");
        }
        //JCHAVEZ 09.07.2009.en graba el tiempo de nicio de cobro
        
        //se guarda valores 
        VariablesCaja.vVuelto=lblVuelto.getText().trim();
        VariablesCaja.vValMontoPagado=lblCoPago.getText().trim();
        
            //DUBILLUZ 11.04.2014 >> 
            //INICIO //
            procesoNuevoCobroFV();
            /*
             * DUBILLUZ 11.04.2014 >> SE COMENTA LA FORMA DE COBRAR ANTIGUA
             * SE ENVIARA POR EL PROCESAR COBRO DEL NUEVO COBRO
	        if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)&&
	        		VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)
	        {
	          procesarBTLMF();
	        }
	        else
	        {
                procesar();
	        }
            */
            //FIN //        
            //DUBILLUZ

    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      //FarmaUtility.showMessage(this, "!!!"+VariablesCaja.vNumPedVta +"-"+VariablesCaja.vNumPedVtaOrigenCOBRO, null); 
      if(VariablesCaja.vNumPedVtaOrigenCOBRO.trim().length()>0){
          if(!VariablesCaja.vIndPedidoConProdVirtual) 
               eventoEscape();
          else{
              if(VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL)){
                  FarmaUtility.showMessage(this, 
                                           "La recarga se procesó exitosamente. No puede salir del cobro.\n" +
                                            "Si persiste el error llame a Mesa de Ayuda.", 
                                           null);
              }
              else if(VariablesCaja.vNumPedVtaOrigenCOBRO.trim().equalsIgnoreCase(VariablesCaja.vNumPedVta)){
                  eventoEscape();
              }
            else{
                FarmaUtility.showMessage(this, 
                                         "No puede Anular este Pedido diferente al Actual.", 
                                         null);
            }
          }
    } else{
          if(VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL)){
              FarmaUtility.showMessage(this, 
                                       "La recarga se procesó exitosamente. No puede salir del cobro.\n" +
                                        "Si persiste el error llame a Mesa de Ayuda.", 
                                       null);
          }else{
            eventoEscape();
          }
      }
        /*
        //JCORTEZ 02.07.2008 se deja el indicador de impresio de cupon por pedido en N
        if(!VariablesCaja.vNumPedVta.equalsIgnoreCase("")){
        VariablesCaja.vPermiteCampaña=verificaPedidoCamp(VariablesCaja.vNumPedVta);
          if(VariablesCaja.vPermiteCampaña.trim().equalsIgnoreCase("S")){
            actualizaPedidoCupon("",VariablesCaja.vNumPedVta,"N","S");
          }
        }
        
        indBorra=false;//jcortez
        
        if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) )
        {
          if(indCerrarPantallaAnularPed && VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
              
             //Se anulara el pedido 
              if(VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                  if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "El Pedido sera Anulado. Desea Continuar?")){
                              try{
                                DBCaja.anularPedidoPendiente(VariablesCaja.vNumPedVta);
                                FarmaUtility.aceptarTransaccion();
                                log.info("Pedido anulado.");
                                FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
                                cerrarVentana(true);
                              } catch(SQLException sql)
                              {
                                FarmaUtility.liberarTransaccion();
                                //log.error("",sql);
                                log.error(null,sql);
                                if(sql.getErrorCode()==20002)
                                  FarmaUtility.showMessage(this, "El pedido ya fue anulado!!!", null); 
                                else if(sql.getErrorCode()==20003)
                                  FarmaUtility.showMessage(this, "El pedido ya fue cobrado!!!", null); 
                                else    
                                  FarmaUtility.showMessage(this, "Error al Anular el Pedido.\n" + sql.getMessage(), null);
                                cerrarVentana(true);
                              }
                            }
              }
              else{
             //if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "El Pedido sera Anulado. Desea Continuar?")){
              try{
                DBCaja.anularPedidoPendienteSinRespaldo(VariablesCaja.vNumPedVta);
                  ///-- inicio de validacion de Campaña 
                  // DUBILLUZ 19.12.2008
                  String pIndLineaMatriz = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                  boolean pRspCampanaAcumulad = UtilityCaja.realizaAccionCampanaAcumulada
                                         (
                                          pIndLineaMatriz,
                                          VariablesCaja.vNumPedVta,this,
                                          ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                          tblFormasPago,
                                          FarmaConstants.INDICADOR_S//Aqui si liberara stock al regalo
                                          );
                  
                  if (!pRspCampanaAcumulad)
                    {
                      FarmaUtility.liberarTransaccion();
                      FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                              FarmaConstants.INDICADOR_S);
                    }

                  FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                      FarmaConstants.INDICADOR_S);


                FarmaUtility.aceptarTransaccion();
                log.info("Pedido anulado sin quitar respaldo.");
                //FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
                //cerrarVentana(true);
                cerrarVentana(false);
              } catch(SQLException sql)
              {
                FarmaUtility.liberarTransaccion();
                //log.error("",sql);
                log.error(null,sql);
                if(sql.getErrorCode()==20002)
                  FarmaUtility.showMessage(this, "El pedido ya fue anulado!!!", null); 
                else if(sql.getErrorCode()==20003)
                  FarmaUtility.showMessage(this, "El pedido ya fue cobrado!!!", null); 
                else    
                  FarmaUtility.showMessage(this, "Error al Anular el Pedido.\n" + sql.getMessage(), null);
                cerrarVentana(true);
              }
            }
            
          } else cerrarVentana(false);
        } else cerrarVentana(false);
         */
    
  }
  }

  public void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    VariablesCaja.vNumPedVta = "";
    this.setVisible(false);
    this.dispose();
  }


// **************************************************************************
// Metodos de lógica de negocio
// **************************************************************************

  private void limpiarDatos(){
    lblTipoComprobante.setText("");
    lblRazSoc.setText("");
    lblRUC.setText("");
    lblSoles.setText("");
    lblDolares.setText("");
    txtNroPedido.setText("");
    txtMontoPagado.setText("");
    lblSaldo.setText("0.00");
    lblVuelto.setText("0.00");
    VariablesCaja.vIndPedidoSeleccionado = "N";
    VariablesCaja.vIndTotalPedidoCubierto = false;
    VariablesCaja.vIndPedidoCobrado = false;
    lblMsjPedVirtual.setVisible(false);
    lblMsjNumRecarga.setVisible(false);
    VariablesCaja.vIndPedidoConProdVirtual = false;
    VariablesCaja.vIndPedidoConvenio = "";
    VariablesConvenio.vCodCliente = "";
    VariablesConvenio.vCodConvenio = "" ;
    VariablesConvenio.vCodCliente = "" ; 
    VariablesConvenio.vValCredDis = 0.00;
    /**
     * VAriables usadas para Convenio Tipo Credito
     * @author dubilluz
     * @since  08.09.2007
     */
    VariablesCaja.cobro_Pedido_Conv_Credito = "N";
    VariablesCaja.valorCredito_de_PedActual = 0.0; 
    VariablesCaja.arrayDetFPCredito = new ArrayList();
    VariablesCaja.monto_forma_credito_ingresado = "0.00";
    VariablesCaja.uso_Credito_Pedido_N_Delivery = "N";
    VariablesCaja.usoConvenioCredito = "";
    VariablesConvenio.vIndSoloCredito = "" ;
    
    /**
     * Para mostrar datos en ticket
     * @author JCORTEZ
     * @since 27.03.09
     * */
    VariablesCaja.vValEfectivo="";
    VariablesCaja.vVuelto="";
  }

  private void limpiarPagos(){
  
  tableModelDetallePago.clearTable();
  lblSaldo.setText(lblSoles.getText().trim());
  lblVuelto.setText("0.00");
  VariablesCaja.vIndTotalPedidoCubierto = false;
  VariablesCaja.vIndPedidoCobrado = false;
  txtMontoPagado.setText("0.00");
  txtCantidad.setText("0");
  txtMontoPagado.setEnabled(false);
  txtCantidad.setEnabled(false);
  btnAdicionar.setEnabled(false);

  //JCORTEZ 07.07.08 se carga el detalle de forma de pago del pedido
  //String numped=((String) ((ArrayList) pArrayList.get(0)).get(0)).trim();
   if(!indBorra){
    cargaDetalleFormaPago(VariablesCaja.vNumPedVta);
    complementarDetalle(); 
      
     ArrayList array = new ArrayList();
     String codsel="",codobt="";
     obtieneDetalleFormaPagoPedido(array,VariablesCaja.vNumPedVta);
     log.debug("detalle forma pago :"+array);
      if(array.size()>0 && !indBorra){
        log.debug("array :"+array);
        for (int j = 0; j < array.size(); j++){
          codsel=(((String) ((ArrayList) array.get(j)).get(0)).trim());     
          for (int i = 0; i < tblDetallePago.getRowCount(); i++){
            codobt=((String) tblDetallePago.getValueAt(i,0)).trim();
            log.debug("codsel :"+codsel);
            log.debug("codobt :"+codobt);
             if(!codobt.equalsIgnoreCase(codsel)){
               tableModelDetallePago.deleteRow(i);
               //tableModelDetallePago.fireTableDataChanged();
               tblDetallePago.repaint();
             }
          }
        }
      }else{
       tableModelDetallePago.clearTable();
      }
    verificaMontoPagadoPedido();
    indBorra=false;
   }
   
  }

    private void buscaPedidoDiario()
    {
        ArrayList myArray = new ArrayList();
        String numPedDiario = txtNroPedido.getText().trim();
        numPedDiario = FarmaUtility.completeWithSymbol(numPedDiario, 4, "0", "I");
        txtNroPedido.setText(numPedDiario);
        try
        {   //log.debug("VariablesCaja.vFecPedACobrar :: >>"+VariablesCaja.vFecPedACobrar);
            DBCaja.obtieneInfoCobrarPedido(myArray, numPedDiario, VariablesCaja.vFecPedACobrar);
            //log.debug("VAriables del Pedido :: >>"+myArray);
            validaInfoPedido(myArray);
        }
        catch(SQLException sql)
        {   //log.error("",sql);
            log.error(null,sql);
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Pedido.\n" + sql.getMessage(), txtNroPedido);
        }
        if(VariablesFidelizacion.vSIN_COMISION_X_DNI)
            lblDNI_SIN_COMISION.setVisible(true);
        else
            lblDNI_SIN_COMISION.setVisible(false);
    }

    private boolean validaPedidoDiario()
    {
        String numPedDiario = txtNroPedido.getText().trim();
        if(numPedDiario.equalsIgnoreCase(""))
        {   FarmaUtility.showMessage(this, "Ingrese un numero de pedido diario.", txtNroPedido);
            return false;
        }
        if(!FarmaUtility.isInteger(numPedDiario) || Integer.parseInt(numPedDiario) <= 0)
        {   FarmaUtility.showMessage(this, "Ingrese un numero de pedido diario valido.", txtNroPedido);
            return false;
        }
        return true;
    }

  private void validaInfoPedido(ArrayList pArrayList)
  {
    //log.debug("validaInfoPedido");
    if(pArrayList.size() < 1)
    {
      FarmaUtility.showMessage(this, "El Pedido No existe o No se encuentra pendiente de pago", txtNroPedido);
      VariablesCaja.vIndPedidoSeleccionado = FarmaConstants.INDICADOR_N;
      limpiarDatos();
      limpiarPagos();
      return;
    } else if(pArrayList.size() > 1)
    {
      FarmaUtility.showMessage(this, "Se encontro mas de un pedido.\n" +
        "Ponganse en contacto con el area de Sistemas.", txtNroPedido);
      VariablesCaja.vIndPedidoSeleccionado = FarmaConstants.INDICADOR_N;
      limpiarDatos();
      limpiarPagos();
      return;
    } else
    {
      
      limpiarPagos();
      limpiaVariablesFormaPago();
      VariablesCaja.vIndPedidoSeleccionado = FarmaConstants.INDICADOR_S;
      muestraInfoPedido(pArrayList);
      
      //JCORTEZ 07.07.08 se carga el detalle de forma de pago del pedido
      if(tblDetallePago.getRowCount()<1){
        cargaDetalleFormaPago(VariablesCaja.vNumPedVta);        
      }
      
      complementarDetalle(); 
      verificaMontoPagadoPedido();
    }
  }

  private void muestraInfoPedido(ArrayList pArrayList)
  {
    //log.debug("muestraInfoPedido");
    VariablesCaja.vNumPedVta = ((String)((ArrayList)pArrayList.get(0)).get(0)).trim();
    log.info("Pedido cargado: " + VariablesCaja.vNumPedVta);
    if(!UtilityCaja.verificaEstadoPedido(this, VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_PENDIENTE, txtNroPedido))
    {
      VariablesCaja.vIndPedidoSeleccionado = FarmaConstants.INDICADOR_N;
      return;
    }
    FarmaUtility.liberarTransaccion();
    
    VariablesCaja.vValTotalPagar = ((String)((ArrayList)pArrayList.get(0)).get(1)).trim();
    lblSoles.setText(VariablesCaja.vValTotalPagar);
    String valDolares = ((String)((ArrayList)pArrayList.get(0)).get(2)).trim();
    valDolares = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(valDolares) + FarmaUtility.getRedondeo(FarmaUtility.getDecimalNumber(valDolares)));
    lblDolares.setText(valDolares);
    VariablesCaja.vValTipoCambioPedido = ((String)((ArrayList)pArrayList.get(0)).get(3)).trim();
    lblTipoCambio.setText(VariablesCaja.vValTipoCambioPedido);
    VariablesVentas.vTip_Comp_Ped = ((String)((ArrayList)pArrayList.get(0)).get(4)).trim();
    lblTipoComprobante.setText(((String)((ArrayList)pArrayList.get(0)).get(5)).trim());
    VariablesVentas.vNom_Cli_Ped = ((String)((ArrayList)pArrayList.get(0)).get(6)).trim();
    VariablesVentas.vRuc_Cli_Ped = ((String)((ArrayList)pArrayList.get(0)).get(7)).trim();
    VariablesVentas.vDir_Cli_Ped = ((String)((ArrayList)pArrayList.get(0)).get(8)).trim();
    VariablesVentas.vTipoPedido =  ((String)((ArrayList)pArrayList.get(0)).get(9)).trim();
    VariablesVentas.vNumOrdeCompra = ((String)((ArrayList)pArrayList.get(0)).get(18)).trim(); 
    VariablesVentas.vPuntoLlegada =  ((String)((ArrayList)pArrayList.get(0)).get(17)).trim(); 
    VariablesVentas.vMensajeBotiquinBTLVtaInstitucional = ((String)((ArrayList)pArrayList.get(0)).get(19)).trim(); 
    
        
    if(VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA))
      lblRazSoc_T.setText("Razon Social :");
    else
      lblRazSoc_T.setText("Cliente :");
    lblRUC.setText(VariablesVentas.vRuc_Cli_Ped);
    lblRazSoc.setText(VariablesVentas.vNom_Cli_Ped);
    lblSaldo.setText(VariablesCaja.vValTotalPagar);
    VariablesCaja.vIndDistrGratuita = ((String)((ArrayList)pArrayList.get(0)).get(11)).trim();
    VariablesCaja.vIndDeliveryAutomatico = ((String)((ArrayList)pArrayList.get(0)).get(12)).trim();
    VariablesVentas.vCant_Items_Ped = ((String)((ArrayList)pArrayList.get(0)).get(13)).trim();
    //indicador de Convenio
    VariablesCaja.vIndPedidoConvenio = ((String)((ArrayList)pArrayList.get(0)).get(14)).trim();

        if (!UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)) {
            VariablesConvenio.vCodConvenio = ((String)((ArrayList)pArrayList.get(0)).get(15)).trim();
            VariablesConvenio.vCodCliente = ((String)((ArrayList)pArrayList.get(0)).get(16)).trim();
        } else {
            if (VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
                //VariablesConvenioBTLMF.vCodConvenio = ((String)((ArrayList)pArrayList.get(0)).get(15)).trim();

                log.debug("1-VariablesConvenioBTLMF.vCodConvenio:" + VariablesConvenioBTLMF.vCodConvenio);


                if (((ArrayList)pArrayList.get(0)).get(16) != null) {
                    VariablesConvenioBTLMF.vCodCliente = ((String)((ArrayList)pArrayList.get(0)).get(16)).trim();
                    log.debug("1-VariablesConvenioBTLMF.vCodCliente:" + VariablesConvenioBTLMF.vCodCliente);
                }

            }
        }
    
      //RHERRERA : SI ES VENTA EMPRESA DATOS DE LA CABECERA DEL PEDIDO.    
      if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)){
          VariablesConvenioBTLMF.vFlgImprimeImportes = "0";
          VariablesConvenioBTLMF.vInstitucion     = VariablesVentas.vNom_Cli_Ped  ;
          VariablesConvenioBTLMF.vDireccion       = VariablesVentas.vDir_Cli_Ped  ;  
          VariablesConvenioBTLMF.vRuc             = VariablesVentas.vRuc_Cli_Ped  ;
          VariablesConvenioBTLMF.vNumOrdeCompra   = VariablesVentas.vNumOrdeCompra;
          VariablesConvenioBTLMF.vPuntoLlegada    = VariablesVentas.vPuntoLlegada ;
      }else if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase("S")){
            UtilityConvenioBTLMF.cargarVariablesConvenio(VariablesConvenioBTLMF.vCodConvenio, this, myParentFrame);
            if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1")) {
                UtilityConvenioBTLMF.existeCliente(VariablesConvenioBTLMF.vCodCliente, this);
                UtilityConvenioBTLMF.consultarSaldCreditoBenif(this);
            }
      }
      
    evaluaPedidoProdVirtual(VariablesCaja.vNumPedVta);
    if(VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ||
       FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar.trim()) <= 0 )
    {
      VariablesCaja.vIndTotalPedidoCubierto = true;
      //txtMontoPagado.setEnabled(false);
      //btnAdicionar.setEnabled(false);
    } else
    {
      VariablesCaja.vIndTotalPedidoCubierto = false;
    }
     /**
      * Lista las Formas de Pago si es Por Convenio 
      * @author : dubilluz
      * @since  : 06.09.2007
      */
      if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase("S"))
      {
		    if(!UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null))
		    {
        cargaFormasPago(VariablesCaja.vIndPedidoConvenio,VariablesConvenio.vCodConvenio,VariablesConvenio.vCodCliente);
			}
			else
			{
			  if(VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)
			  {
		        cargaFormasPago(VariablesCaja.vIndPedidoConvenio,VariablesConvenioBTLMF.vCodConvenio,VariablesConvenioBTLMF.vCodCliente);
			  }
			}
	    //log.info("VariablesCaja.vIndPedidoConvenio :" + VariablesCaja.vIndPedidoConvenio);
	  }
      else if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase("N"))
        cargaFormasPago("N","N","0");
    
    if(VariablesCaja.vIndDeliveryAutomatico.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
      log.info("Es Pedido Delivery");
      colocaFormaPagoDeliveryAutomatico(VariablesCaja.vNumPedVta);
      verificaMontoPagadoPedido();
    }
    if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
      log.info("Es Pedido Convenio");
     if(!VariablesCaja.vIndDeliveryAutomatico.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
      colocaFormaPagoPedidoConvenio(VariablesCaja.vNumPedVta);
      /**
       * Colocara el Credito del Pedido si es  por Convenio
       * @author dubilluz
       * @since  08.09.2007
       */
      if(VariablesCaja.arrayDetFPCredito.size()>0) 
      carga_Credito_Convenio();
      else
      log.debug("Convenio no da Credito");
      verificaMontoPagadoPedido();
     }
    }
      log.debug("2-VariablesConvenioBTLMF.vCodConvenio:"+VariablesConvenioBTLMF.vCodConvenio);

      if (VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)
       {
         
         adicionaDetallePagoCredito();
         
       }    
  }

  private void cambioTipoComprobante()
  {
    DlgSeleccionTipoComprobante dlgSeleccionTipoComprobante = new DlgSeleccionTipoComprobante(myParentFrame,"",true);
    dlgSeleccionTipoComprobante.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      colocaInfoComprobante();
      FarmaVariables.vAceptar = false;
    }
  }

  private void colocaInfoComprobante()
  {
    if(VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA)){
      lblTipoComprobante.setText("FACTURA");
      lblRazSoc_T.setText("Razon Social :");
    } else if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)){ //JCHAVEZ 24092009.sn
        lblTipoComprobante.setText("BOLETA");
        lblRazSoc_T.setText("Cliente :");
    } else if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
        lblTipoComprobante.setText("TICKET");
        lblRazSoc_T.setText("Cliente :");
    }//JCHAVEZ 24092009.en
    lblRUC.setText(VariablesVentas.vRuc_Cli_Ped);
    lblRazSoc.setText(VariablesVentas.vNom_Cli_Ped);
  }

  private void validaFormaPagoSeleccionada()
  {
    if(tblFormasPago.getRowCount() <= 0) return;
    int fila = tblFormasPago.getSelectedRow();
    String codFormaPago = ((String)tblFormasPago.getValueAt(fila,0)).trim();
    String codOperTarj = ((String)tblFormasPago.getValueAt(fila,2)).trim();
    String indTarjeta = ((String)tblFormasPago.getValueAt(fila,3)).trim();
    String indCupon = ((String)tblFormasPago.getValueAt(fila,4)).trim();
    log.debug("VariablesCaja.vIndTotalPedidoCubierto: "+VariablesCaja.vIndTotalPedidoCubierto);
    log.debug("codFormaPago: "+codFormaPago);
    log.debug("codOperTarj : "+codOperTarj);
    log.debug("indTarjeta  : "+indTarjeta);


    if(VariablesCaja.vIndTotalPedidoCubierto)
    {
      FarmaUtility.showMessage(this, "El monto total del Pedido ya fue cubierto.\n" +
        "Presione F11 para generar comprobante(s).", tblFormasPago);
      return;
    }
    VariablesCaja.vIndDatosTarjeta = false;
    VariablesCaja.vIndTarjetaSeleccionada = false;
    VariablesCaja.vNumTarjCred = "";
    VariablesCaja.vFecVencTarjCred = "";
    VariablesCaja.vNomCliTarjCred = "";
    txtMontoPagado.setText("0.00");
    txtCantidad.setText("0");
    FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, FarmaConstants.HASHTABLE_MONEDA, FarmaConstants.CODIGO_MONEDA_SOLES);
    ////
    /**
     * Para un Convenio
     * @author  dubilluz
     * @since   10.09.2007
     */
    //Si es Convenio
    String cod_FP_Convenio = "Notiene";
     if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase("S")){//VariablesVentas.vEsPedidoConvenio){
       String  indCredConvenio = isConvenioCredito(VariablesConvenio.vCodConvenio);
        if(indCredConvenio.equalsIgnoreCase("S") && VariablesCaja.arrayDetFPCredito.size()>0)//FarmaUtility.getDecimalNumber(VariablesConvenio.vPorcCoPago)!=0)
         {
           cod_FP_Convenio = ((String)((ArrayList)(VariablesCaja.arrayDetFPCredito.get(0))).get(0)).trim();//obtiene_cod_FPago_Convenio(VariablesConvenio.vCodConvenio).trim();
         }
     }
     
     if (codFormaPago.equalsIgnoreCase(cod_FP_Convenio)) {
            log.debug(VariablesCaja.valorCredito_de_PedActual+"el valor del monto, del credito de Convenio");
            txtMontoPagado.setText(""+VariablesCaja.valorCredito_de_PedActual);//(String)VariablesConvenio.registroFP.get(3));
            txtMontoPagado.setEnabled(true);
            btnAdicionar.setEnabled(true);
            FarmaUtility.moveFocus(txtMontoPagado);
     }
    ////
    else if(codFormaPago.equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES))
    {
      FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, FarmaConstants.HASHTABLE_MONEDA, FarmaConstants.CODIGO_MONEDA_SOLES);
      cmbMoneda.setEnabled(false);
      txtCantidad.setEnabled(false);
      VariablesCaja.vIndCambioMoneda = false;
      txtMontoPagado.setEnabled(true);
      btnAdicionar.setEnabled(true);
      FarmaUtility.moveFocus(txtMontoPagado);
    } else if(codFormaPago.equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES))
    {
      FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, FarmaConstants.HASHTABLE_MONEDA, FarmaConstants.CODIGO_MONEDA_DOLARES);
      cmbMoneda.setEnabled(false);
      txtCantidad.setEnabled(false);
      VariablesCaja.vIndCambioMoneda = false;
      txtMontoPagado.setEnabled(true);
      btnAdicionar.setEnabled(true);
      FarmaUtility.moveFocus(txtMontoPagado);
    } else if( indTarjeta.equalsIgnoreCase(FarmaConstants.INDICADOR_S) )
    {
      VariablesCaja.vIndTarjetaSeleccionada = true;
      VariablesCliente.vCodOperadorTarjeta = codOperTarj;
      cmbMoneda.setEnabled(true);
      txtCantidad.setEnabled(false);
      VariablesCaja.vIndCambioMoneda = true;
      txtMontoPagado.setEnabled(false);
      btnAdicionar.setEnabled(false);
      FarmaUtility.moveFocus(cmbMoneda);

    }
    else
    {
      log.debug("FORMA DE PAGO - NO TARJETA");
      //AGREGAR LOGICA DE FORMAS DE PAGO QUE NO REPRESENTAN NI TARJETA NI EFECTIVO
      if( indCupon.equalsIgnoreCase(FarmaConstants.INDICADOR_S) )
      {
        VariablesCaja.vCodFormaPago = codFormaPago;
        if(!validaCodigoFormaPago())
        {
          FarmaUtility.showMessage(this,"La forma de pago ya existe en el Pedido. Verifique!!!", tblFormasPago);
          return;
        }
        if(tblDetallePago.getRowCount() > 0)
        {
          FarmaUtility.showMessage(this, "Esta forma de pago debe ser la primera del pedido. Por favor, verifique!!!", tblFormasPago);
          return;
        }
        FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, FarmaConstants.HASHTABLE_MONEDA, FarmaConstants.CODIGO_MONEDA_SOLES);
        cmbMoneda.setEnabled(false);
        VariablesCaja.vIndCambioMoneda = false;
        txtCantidad.setEnabled(true);
        txtMontoPagado.setEnabled(false);
        btnAdicionar.setEnabled(true);
        FarmaUtility.moveFocus(txtCantidad);
      } else
      {
        /*if(tblDetallePago.getRowCount() > 0)
        {
          FarmaUtility.showMessage(this, "Esta forma de pago debe ser única por pedido. Por favor, verifique!!!", tblFormasPago);
          return;
        }*/
        //if(!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Está seguro de adicionar esta forma de pago?")) return;
        
        FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, FarmaConstants.HASHTABLE_MONEDA, FarmaConstants.CODIGO_MONEDA_SOLES);
        cmbMoneda.setEnabled(false);
        txtCantidad.setEnabled(false);
        VariablesCaja.vIndCambioMoneda = false;
        //txtMontoPagado.setText(lblSoles.getText().trim());
        txtMontoPagado.setEnabled(true);
        btnAdicionar.setEnabled(true);
        txtMontoPagado.setText("0.00");
        FarmaUtility.moveFocus(txtMontoPagado);
        //btnAdicionar.doClick();
      }
    }
  }

  private void adicionaDetallePago()
  {
    obtieneDatosFormaPagoPedido();
  /**
   * Valida si el Monto de Ingreso , que no sea mayor al Credito que Dispone
   * @author dubilluz
   * @since  10.09.2007
   */
    if(!validaMontoCredito_Convenio()) return ; 
    if(!validaMontoIngresado()) return;
    log.debug("VariablesCaja.vIndTotalPedidoCubierto: "+VariablesCaja.vIndTotalPedidoCubierto);
    if(VariablesCaja.vIndTotalPedidoCubierto)
    {
      FarmaUtility.showMessage(this, "El monto total del Pedido ya fue cubierto.\n" +
        "Presione F11 para generar comprobante(s).", tblFormasPago);
      return;
    }
    if(!validaCodigoFormaPago())
    {
      FarmaUtility.showMessage(this,"La forma de pago ya existe en el Pedido. Verifique!!!", tblFormasPago);
      return;
    }
    if(VariablesCaja.vIndTarjetaSeleccionada && !VariablesCaja.vIndDatosTarjeta)
    {
      FarmaUtility.showMessage(this,"La forma de pago requiere datos de la tarjeta. Verifique!!!", tblFormasPago);
      return;
    }
    //obtieneDatosFormaPagoPedido();
    if(VariablesCaja.vIndTarjetaSeleccionada && FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado) > VariablesCaja.vMontoMaxPagoTarjeta)
    {
      FarmaUtility.showMessage(this,"El monto ingresado no puede ser mayor al saldo del Pedido. Verifique!!!", txtMontoPagado);
      return;
    }

    if(VariablesCaja.vIndTarjetaSeleccionada && FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado) > VariablesCaja.vMontoMaxPagoTarjeta)
    {
      FarmaUtility.showMessage(this,"El monto ingresado no puede ser mayor al saldo del Pedido. Verifique!!!", txtMontoPagado);
      return;
    }
    
    operaListaDetallePago();
    verificaMontoPagadoPedido();
    complementarDetalle();
    
  }


  private void adicionaDetallePagoCredito()
  {


	double montoCredito = UtilityConvenioBTLMF.obtieneMontoCredito(this, null, new Double(FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar)),VariablesCaja.vNumPedVta,"");

	if (montoCredito > 0)
	 {
	     //kmoncada 23.06.2014 en el caso de ventas institucionales, no agrega otra forma de pago al pedido.
	     //if(!ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL.equalsIgnoreCase(VariablesVentas.vTipoPedido)){
	         obtieneDatosFormaPagoPedidoCredito();
	     //}

		    //if(!validaMontoCredito_Convenio()) return ;
		    //if(!validaMontoIngresado()) return;
		    log.debug("VariablesCaja.vIndTotalPedidoCubierto: "+VariablesCaja.vIndTotalPedidoCubierto);
		    if(VariablesCaja.vIndTotalPedidoCubierto)
		    {
		      FarmaUtility.showMessage(this, "El monto total del Pedido ya fue cubierto.\n" +
		        "Presione F11 para generar comprobante(s).", tblFormasPago);
		      return;
		    }

		    //obtieneDatosFormaPagoPedido();
		    //if(VariablesCaja.vIndTarjetaSeleccionada && FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado) > VariablesCaja.vMontoMaxPagoTarjeta)
		    //{
		    //FarmaUtility.showMessage(this,"El monto ingresado no puede ser mayor al saldo del Pedido. Verifique!!!", txtMontoPagado);
		    //return;
		    //}

		    //if(VariablesCaja.vIndTarjetaSeleccionada && FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado) > VariablesCaja.vMontoMaxPagoTarjeta)
		    //{
		    //  FarmaUtility.showMessage(this,"El monto ingresado no puede ser mayor al saldo del Pedido. Verifique!!!", txtMontoPagado);
		    //  return;
		    //}

		    operaListaDetallePago();
		    verificaMontoPagadoPedido();
		    complementarDetalle();
                    
                    
	 }

  }


  //JCORTEZ
  private void complementarDetalle(){
  
    limpiaVariablesFormaPago();
    txtMontoPagado.setText("0.00");
    txtCantidad.setText("0");
    txtMontoPagado.setEnabled(false);
    txtCantidad.setEnabled(false);
    btnAdicionar.setEnabled(false);
    cmbMoneda.setEnabled(false);
    FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, FarmaConstants.HASHTABLE_MONEDA, FarmaConstants.CODIGO_MONEDA_SOLES);
    btnFormaPago.doClick();
  }

  private void obtieneDatosFormaPagoPedido()
  {
    VariablesCaja.vValEfectivo= txtMontoPagado.getText().trim();
    if(tblFormasPago.getRowCount() <= 0) return;
    int fila = tblFormasPago.getSelectedRow();
    VariablesCaja.vCodFormaPago = ((String)tblFormasPago.getValueAt(fila,0)).trim();
    VariablesCaja.vDescFormaPago = ((String)tblFormasPago.getValueAt(fila,1)).trim();
    VariablesCaja.vCantidadCupon = txtCantidad.getText().trim();
    //VariablesCaja.vCodOperadorTarjeta = ((String)tblFormasPago.getValueAt(fila,2)).trim();
    String codMoneda = FarmaLoadCVL.getCVLCode(FarmaConstants.HASHTABLE_MONEDA,cmbMoneda.getSelectedIndex());
    VariablesCaja.vCodMonedaPago = codMoneda;
    VariablesCaja.vDescMonedaPago = FarmaLoadCVL.getCVLDescription(FarmaConstants.HASHTABLE_MONEDA, codMoneda);
    VariablesCaja.vValMontoPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtMontoPagado.getText().trim()));
    if(codMoneda.equalsIgnoreCase(FarmaConstants.CODIGO_MONEDA_SOLES))
      VariablesCaja.vValTotalPagado = VariablesCaja.vValMontoPagado;
    else
      VariablesCaja.vValTotalPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado) * FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido));
  }

  private void obtieneDatosFormaPagoPedidoCredito()
  {
    VariablesCaja.vValEfectivo= txtMontoPagado.getText().trim();
    if(tblFormasPago.getRowCount() <= 0) return;
    int fila = tblFormasPago.getSelectedRow();
    VariablesCaja.vCodFormaPago  = ConstantsConvenioBTLMF.COD_FORMA_PAGO_CREDITO;//((String)tblFormasPago.getValueAt(fila,0)).trim();
    VariablesCaja.vDescFormaPago = UtilityConvenioBTLMF.obtieneFormaPago(this, null, ConstantsConvenioBTLMF.COD_FORMA_PAGO_CREDITO) ;//((String)tblFormasPago.getValueAt(fila,1)).trim();
    VariablesCaja.vCantidadCupon = "0";//txtCantidad.getText().trim();
    //VariablesCaja.vCodOperadorTarjeta = ((String)tblFormasPago.getValueAt(fila,2)).trim();
    String codMoneda = FarmaLoadCVL.getCVLCode(FarmaConstants.HASHTABLE_MONEDA,cmbMoneda.getSelectedIndex());
    VariablesCaja.vCodMonedaPago = codMoneda;
    VariablesCaja.vDescMonedaPago = FarmaLoadCVL.getCVLDescription(FarmaConstants.HASHTABLE_MONEDA, codMoneda);

    double montoPagar = UtilityConvenioBTLMF.obtieneMontoCredito(this, null, new Double(FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar)),VariablesCaja.vNumPedVta,"");

    VariablesCaja.vValMontoPagado = FarmaUtility.formatNumber(montoPagar);
    lblCoPago.setText(VariablesCaja.vValMontoPagado);
    String porcCopago  = FarmaUtility.formatNumber((FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado)/FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar))*100,"");


    log.debug("Porcentaje Copago:" +porcCopago);
    String porcCopagoTemp = porcCopago.replace('.', ' ');

    if (porcCopagoTemp.trim().equals("100"))
    {
      lblCoPagoT.setText("Crédito("+porcCopago+"%) :  S/.");
    }
    else
    {
      lblCoPagoT.setText("Monto Empr.("+porcCopago+"%) :  S/.");
    }

    if(codMoneda.equalsIgnoreCase(FarmaConstants.CODIGO_MONEDA_SOLES))
      VariablesCaja.vValTotalPagado = VariablesCaja.vValMontoPagado;
    else
      VariablesCaja.vValTotalPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado) * FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido));
  }

  private void operaListaDetallePago()
  {
      //ERIOS 01.04.2013 Se agrega nuevos campos vacios
    ArrayList myArray = new ArrayList();
    myArray.add(VariablesCaja.vCodFormaPago);
    myArray.add(VariablesCaja.vDescFormaPago);
    myArray.add(VariablesCaja.vCantidadCupon);
    myArray.add(VariablesCaja.vDescMonedaPago);
    myArray.add(VariablesCaja.vValMontoPagado);
    myArray.add(VariablesCaja.vValTotalPagado);
    myArray.add(VariablesCaja.vCodMonedaPago);
    myArray.add("0.00");
    myArray.add(VariablesCaja.vNumTarjCred);
    myArray.add(VariablesCaja.vFecVencTarjCred);
    myArray.add(VariablesCaja.vNomCliTarjCred);
    myArray.add("");
    myArray.add("");    
    myArray.add("");
    myArray.add("");
    
    tableModelDetallePago.data.add(myArray);
    tableModelDetallePago.fireTableDataChanged();
    txtMontoPagado.setText("0.00");
    log.info("SET VARIABLES DE FORMA DE PAGO");
    
  }

  private boolean validaMontoIngresado()
  {
    String monto = txtMontoPagado.getText().trim();
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
//    if(Double.parseDouble(monto) <= 0)
    if(FarmaUtility.getDecimalNumber(monto) <= 0)
    {
      FarmaUtility.showMessage(this, "Ingrese monto a pagar mayo a 0.", txtMontoPagado);
      return false;
    }
    return true;
  }

  private void verificaMontoPagadoPedido()
  {
    log.debug("tblDetallePago.getRowCount(): "+tblDetallePago.getRowCount());
    log.debug("vIndTotalPedidoCubierto: "+VariablesCaja.vIndTotalPedidoCubierto);
    if(tblDetallePago.getRowCount() <= 0) 
      return;
    double montoTotal = 0;
    double montoFormaPago = 0;
    for(int i=0; i<tblDetallePago.getRowCount(); i++)
    {
      montoFormaPago = FarmaUtility.getDecimalNumber(((String)tblDetallePago.getValueAt(i,5)).trim());
      montoTotal = montoTotal + montoFormaPago;
    }
    	log.debug("VariablesCaja.vValTotalPagar=" + VariablesCaja.vValTotalPagar);
    	log.debug("montoTotal=" + montoTotal);
    if( FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar) > montoTotal ){
      log.debug("No Cubierto");
      VariablesCaja.vIndTotalPedidoCubierto = false;
      VariablesCaja.vSaldoPedido = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar) - montoTotal);
      VariablesCaja.vValVueltoPedido = "0.00";
    } else{
      log.debug("Cubierto");
      VariablesCaja.vIndTotalPedidoCubierto = true;
      VariablesCaja.vSaldoPedido = "0.00";
      VariablesCaja.vValVueltoPedido = FarmaUtility.formatNumber(montoTotal - FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar));
    }
    log.debug("VariablesCaja.vSaldoPedido :"+VariablesCaja.vSaldoPedido);
    log.debug("VariablesCaja.vValVueltoPedido :"+VariablesCaja.vValVueltoPedido);
    lblSaldo.setText(VariablesCaja.vSaldoPedido);
    lblVuelto.setText(VariablesCaja.vValVueltoPedido);
  }

  private boolean validaCodigoFormaPago()
  {
    if(tblDetallePago.getRowCount() <= 0) return true;
    String codFormaPago = VariablesCaja.vCodFormaPago;
    for(int i=0; i<tblDetallePago.getRowCount(); i++)
    {
      String codTmp = ((String)tblDetallePago.getValueAt(i,0)).trim();
      if(codFormaPago.equalsIgnoreCase(codTmp)) return false;
    }
    return true;
  }

  private void limpiaVariablesFormaPago()
  {
    VariablesCaja.vCodFormaPago = "";
    VariablesCaja.vDescFormaPago = "";
    VariablesCaja.vDescMonedaPago = "";
    VariablesCaja.vValMontoPagado = "";
    VariablesCaja.vValTotalPagado = "";
    log.debug("************************LimpiaVariablesFormaPago***********************");
  }

  /*private void cobrarPedido()
  {
        DlgProcesarCobro dlgProcesarCobro = 
            new DlgProcesarCobro(myParentFrame, "", true, tblFormasPago, 
                                 lblVuelto, tblDetallePago, txtNroPedido);
        dlgProcesarCobro.setVisible(true);
        //JCORTEZ 07.01.09
        if (!FarmaVariables.vAceptar) {
            if (VariablesCaja.vCierreDiaAnul) {
                anularAcumuladoCanje();
                VariablesCaja.vCierreDiaAnul = false;
            }
        }
        // 06.03.2008 ERIOS Cierra la conexion si se utilizo credito 
        if (VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) {
            FarmaConnection.closeConnection();
            FarmaConnection.anularConnection();
        }
   }*/


  /*private void cobrarPedidoBTLMF()
  {
        DlgProcesarCobroBTLMF dlgProcesarCobro =
            new DlgProcesarCobroBTLMF(myParentFrame, "", true, tblFormasPago,
                                 lblVuelto, tblDetallePago, txtNroPedido);
        dlgProcesarCobro.setVisible(true);
        //JCORTEZ 07.01.09
        if (!FarmaVariables.vAceptar) {
            if (VariablesCaja.vCierreDiaAnul) {
                anularAcumuladoCanje();
                VariablesCaja.vCierreDiaAnul = false;
            }
        }
        // 06.03.2008 ERIOS Cierra la conexion si se utilizo credito /
        if (VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) {
            FarmaConnection.closeConnection();
            FarmaConnection.anularConnection();
        }
  }*/

  /**
   * JCORTEZ 08.01.09
   * Se movio parte del codigo
   * */
  private void anularAcumuladoCanje(){
      try{
          // DUBILLUZ 19.12.2008
          String pIndLineaMatriz = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
          log.debug("pIndLineaMatriz "+pIndLineaMatriz);
          //log.debug("pIndLineaMatriz "+pIndLineaMatriz);
          log.debug("VariablesVentas.vNum_Ped_Vta "+VariablesVentas.vNum_Ped_Vta);
          boolean pRspCampanaAcumulad = UtilityCaja.realizaAccionCampanaAcumulada
                                 (
                                  pIndLineaMatriz,
                                  VariablesVentas.vNum_Ped_Vta,this,//VariablesCaja.vNumPedVta,this,
                                  ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                  tblFormasPago,
                                  FarmaConstants.INDICADOR_S//Aqui si liberara stock al regalo
                                  );
          
          log.debug("pRspCampanaAcumulad "+pRspCampanaAcumulad);
          if (!pRspCampanaAcumulad)
            {
              log.debug("Se recupero historico y canje  XXX");
              FarmaUtility.liberarTransaccion();
              FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                      FarmaConstants.INDICADOR_S);
            }

          FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_S);
          FarmaUtility.aceptarTransaccion();
          log.info("Pedido anulado sin quitar respaldo.");
          //JMIRANDA 05.07.2010
          cerrarVentana(false);
      } catch(Exception sql){
           FarmaUtility.liberarTransaccion();
		   FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                      FarmaConstants.INDICADOR_S);           
           //log.error("",sql);
           //log.error(null,sql);
      }finally{
          FarmaConnectionRemoto.closeConnection();
      }
  }

  private boolean obtieneIndCajaAbierta_ForUpdate(String pSecMovCaja)
  {
    boolean cajaAbierta = false;
    String indCajaAbierta = "";
    try {
      indCajaAbierta = DBCaja.obtieneIndCajaAbierta_ForUpdate(pSecMovCaja);
      if(indCajaAbierta.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        cajaAbierta = true;
      return cajaAbierta;
    } catch (SQLException sqlException) {
      //log.error("",sqlException);
      log.error(null,sqlException);
      FarmaUtility.showMessage(this, "Error al obtener la fecha de movimiento de caja.",tblFormasPago);
      return false;
    }
  }

  public void setIndPedirLogueo(boolean pValor)
  {
    this.indPedirLogueo = pValor;
  }

  public void setIndPantallaCerrarAnularPed(boolean pValor)
  {
    this.indCerrarPantallaAnularPed = pValor;
  }

  public void setIndPantallaCerrarCobrarPed(boolean pValor)
  {
    this.indCerrarPantallaCobrarPed = pValor;
  }

  private void seleccionaTarjetaCliente()
  {
    //btnFormaPago.doClick();
    VariablesCliente.vIndicadorSeleccionTarjeta = FarmaConstants.INDICADOR_S;
    VariablesCliente.vIndicadorCargaCliente = FarmaConstants.INDICADOR_N;
    //DlgBuscaClienteJuridico dlgBuscaClienteJuridico = new DlgBuscaClienteJuridico(myParentFrame, "", true);
    //dlgBuscaClienteJuridico.setVisible(true);
    //DlgInformacionTarjeta dlgInformacionTarjeta = new DlgInformacionTarjeta(myParentFrame, "", true);
    //dlgInformacionTarjeta.setVisible(true);
    FarmaVariables.vAceptar = true;
    if(FarmaVariables.vAceptar)
    {
      log.debug("VariablesCliente.vArrayList_Valores_Tarjeta.size() : " + VariablesCliente.vArrayList_Valores_Tarjeta.size());
      /*if(VariablesCliente.vArrayList_Valores_Tarjeta.size() == 1){
        VariablesCaja.vIndDatosTarjeta = true;
        VariablesCaja.vNumTarjCred = ((String)((ArrayList)VariablesCliente.vArrayList_Valores_Tarjeta.get(0)).get(0)).trim();
        VariablesCaja.vFecVencTarjCred = ((String)((ArrayList)VariablesCliente.vArrayList_Valores_Tarjeta.get(0)).get(1)).trim();
        VariablesCaja.vNomCliTarjCred = ((String)((ArrayList)VariablesCliente.vArrayList_Valores_Tarjeta.get(0)).get(2)).trim();
        String codMoneda = FarmaLoadCVL.getCVLCode(FarmaConstants.HASHTABLE_MONEDA,cmbMoneda.getSelectedIndex());
        if(codMoneda.equalsIgnoreCase(FarmaConstants.CODIGO_MONEDA_SOLES))
          txtMontoPagado.setText(lblSaldo.getText().trim());
        else{
          String saldoSoles = lblSaldo.getText().trim();
          String saldoDolares = FarmaUtility.formatNumber((FarmaUtility.getDecimalNumber(saldoSoles) / FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido)));
          txtMontoPagado.setText(saldoDolares);
        }
        //adicionaDetallePago();
        VariablesCaja.vMontoMaxPagoTarjeta = FarmaUtility.getDecimalNumber(txtMontoPagado.getText().trim());
        txtMontoPagado.setEnabled(true);
        btnAdicionar.setEnabled(true);
        FarmaUtility.moveFocus(txtMontoPagado);
      } else FarmaVariables.vAceptar = false;*/
      VariablesCaja.vIndDatosTarjeta = true;
      String codMoneda = FarmaLoadCVL.getCVLCode(FarmaConstants.HASHTABLE_MONEDA,cmbMoneda.getSelectedIndex());
      if(codMoneda.equalsIgnoreCase(FarmaConstants.CODIGO_MONEDA_SOLES))
        txtMontoPagado.setText(String.valueOf(FarmaUtility.getDecimalNumber(lblSaldo.getText().trim())));
      else{
        String saldoSoles = lblSaldo.getText().trim();
        String saldoDolares = FarmaUtility.formatNumber((FarmaUtility.getDecimalNumber(saldoSoles) / FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido)));
        txtMontoPagado.setText(String.valueOf(FarmaUtility.getDecimalNumber(saldoDolares)));
      }
      VariablesCaja.vMontoMaxPagoTarjeta = FarmaUtility.getDecimalNumber(txtMontoPagado.getText().trim()) + 0.05;//se agrega 0.05 para que pase la validacion en caso se requiera.
      txtMontoPagado.setEnabled(true);
      btnAdicionar.setEnabled(true);
      txtMontoPagado.selectAll();
      //FarmaUtility.moveFocus(txtMontoPagado);
    } else VariablesCaja.vIndDatosTarjeta = false;
  }

  private void configuracionComprobante()
  {
    int indIpValida=0;
    try
    {
      indIpValida =  DBPtoVenta.verificaIPValida();
      if( indIpValida == 0 )
        FarmaUtility.showMessage(this,"La estación actual no se encuentra autorizada para efectuar la operación. ", null);
      else{
    DlgConfiguracionComprobante dlgConfiguracionComprobante = new DlgConfiguracionComprobante(myParentFrame,"",true);
    dlgConfiguracionComprobante.setVisible(true);
    if(FarmaVariables.vAceptar) FarmaVariables.vAceptar = false;
  }
    } catch(SQLException ex)
    {
      //log.error("",ex);
      log.error(null,ex);
      FarmaUtility.showMessage(this,"Error al validar IP de Configuracion de Comprobantes.\n" + ex.getMessage(), null);
      indIpValida=0;
    }
  }
  
  private double obtieneMontoFormaPagoCupon(String pCodFormaPago,
                                            String pCantCupon)
  {
    double monto = 0.00;
    try
    {
      monto = DBCaja.obtieneMontoFormaPagoCupon(pCodFormaPago, pCantCupon);
    } catch(SQLException ex)
    {
      //log.error("",ex);
      log.error(null,ex);
      FarmaUtility.showMessage(this,"Error al obtener monto de forma de pago con cupón.\n" + ex.getMessage(), tblFormasPago);
      monto = 0.00;
    }
    return monto;
  }
  
  private void colocaFormaPagoDeliveryAutomatico(String pNumPedido)
  {
    try
    {
      DBDelivery.cargaFormaPagoPedidoDelAutomatico(tableModelDetallePago.data, pNumPedido);
      tableModelDetallePago.fireTableDataChanged();
    } catch(SQLException ex)
    {
      //log.error("",ex);
      log.error(null,ex);
      FarmaUtility.showMessage(this,"Error al obtener forma de pago delivery automatico.\n" + ex.getMessage(), tblFormasPago);
    }
  }
  
  private void colocaFormaPagoPedidoConvenio(String pNumPedido)
  {
    try
    {
      /*DBCaja.cargaFormaPagoPedidoConvenio(tableModelDetallePago.data, pNumPedido);
      log.debug("Data: "+tableModelDetallePago.data);
      log.debug("Sizee: "+tableModelDetallePago.data.size());*/
    
      /*if(tableModelDetallePago.data.size()>0)
      tableModelDetallePago.fireTableDataChanged();*/
      /**
       * Modificado para que no coloque el detalle de Todo el Credito sino q pueda modificar para el uso q dara
       * @author dubilluz
       * @since  08.09.2007
       */
      DBCaja.cargaFormaPagoPedidoConvenio(VariablesCaja.arrayDetFPCredito, pNumPedido);
      log.debug("Data: "+VariablesCaja.arrayDetFPCredito);
      log.debug("Sizee: "+VariablesCaja.arrayDetFPCredito.size());      
      if(VariablesCaja.arrayDetFPCredito.size()>0)
      tableModelDetallePago.fireTableDataChanged();
      
      
    } catch(SQLException ex)
    {
      //log.error("",ex);
      log.error(null,ex);
      FarmaUtility.showMessage(this,"Error al obtener forma de pago delivery automatico.\n" + ex.getMessage(), tblFormasPago);
    }
  }
  
  private int cantidadProductosVirtualesPedido(String pNumPedido)
  {
    int cant = 0;
    try
    {
      cant = DBCaja.obtieneCantProdVirtualesPedido(pNumPedido);
    } catch(SQLException ex)
    {
      //log.error("",ex);
      log.error(null,ex);
      cant = 0;
      FarmaUtility.showMessage(this,"Error al obtener cantidad de productos virtuales.\n" + ex.getMessage(), tblFormasPago);
    }
    return cant;
  }
  
  private void evaluaPedidoProdVirtual(String pNumPedido)
  {
    int cantProdVirtualesPed = 0;
    String tipoProd = "";
    cantProdVirtualesPed = cantidadProductosVirtualesPedido(pNumPedido);
    if( cantProdVirtualesPed <= 0 )
    {
      lblMsjPedVirtual.setText("");
      lblMsjNumRecarga.setText("");
      lblMsjPedVirtual.setVisible(false);
      lblMsjNumRecarga.setVisible(false);
      VariablesCaja.vIndPedidoConProdVirtual = false;
    } else
    {
      
      tipoProd = obtieneTipoProductoVirtual(pNumPedido);
      if(tipoProd.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA))
        lblMsjPedVirtual.setText("El pedido contiene una Tarjeta Virtual. Si lo cobra, No podrá ser anulado.");
      else if(tipoProd.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA)){
       //14.11.2007 dubilluz modificado
       //lblMsjPedVirtual.setText("El pedido es una Recarga Virtual. Si lo cobra, Sólo podrá anularse dentro de 10 minutos.");
       //lblMsjPedVirtual.setText("Recarga Virtual.Sólo podrá anularse dentro de "+ time_max(pNumPedido) +" minutos." + " Telefono: " );
       lblMsjPedVirtual.setText("Recarga Virtual." + " Telefono: " );
       
       //INI ASOSA - 03/07/2014
       
       String numTelefono = num_telefono(pNumPedido);
       lblMsjNumRecarga.setText(""+ numTelefono);
       String monto = lblSoles.getText();
       UtilityCaja.imprVouVerifRecarga(this, 
                                       tblFormasPago, 
                                       descProductoRecVirtual, 
                                       numTelefono, 
                                       monto, 
                                       VariablesPtoVenta.vImpresoraActual,
                                       VariablesPtoVenta.vTipoImpTermicaxIp);
       
        //FIN ASOSA - 03/07/2014
       
      }
      else{
        lblMsjPedVirtual.setText("");
        lblMsjNumRecarga.setText("");
      }
      lblMsjPedVirtual.setVisible(true);
      lblMsjNumRecarga.setVisible(true);

      VariablesCaja.vIndPedidoConProdVirtual = true;
    }
    log.debug("VariablesCaja.vIndPedidoConProdVirtual : " + VariablesCaja.vIndPedidoConProdVirtual);
  }
  
  private void limpiaVariablesVirtuales()
  {
    VariablesVirtual.vCodigoComercio = "";
    VariablesVirtual.vTipoTarjeta = "";
    VariablesVirtual.vMonto = "";
    VariablesVirtual.vNumTerminal = "";
    VariablesVirtual.vNumSerie = "";
    VariablesVirtual.vNumTrace = "";
    VariablesVirtual.vIPHost = "";
    VariablesVirtual.vPuertoHost = "";
    VariablesVirtual.vNumeroCelular = "";
    VariablesVirtual.vCodigoProv = "";
    VariablesVirtual.vCodigoAprobacion = "";
    VariablesVirtual.vNumeroTarjeta = "";
    VariablesVirtual.vNumeroPin = "";
    VariablesVirtual.vCodigoRespuesta = "";
    VariablesVirtual.vDescripcionRespuesta = "";
    VariablesVirtual.vArrayList_InfoProdVirtual.clear();
    //VariablesVirtual.respuestaNavSatBean.ResetFields();
    VariablesVirtual.vNumTraceOriginal = "";
    VariablesVirtual.vCodAprobacionOriginal = "";
    VariablesVirtual.vFechaTX = "";
    VariablesVirtual.vHoraTX = "";
  }
  
  private String obtieneTipoProductoVirtual(String pNumPedido)
  {
    String tipoProd = "";
    try
    {
      tipoProd = DBCaja.obtieneTipoProductoVirtualPedido(pNumPedido);
    } catch(SQLException ex)
    {
      //log.error("",ex);
      log.error(null,ex);
      tipoProd = "";
      FarmaUtility.showMessage(this,"Error al obtener cantidad de productos virtuales.\n" + ex.getMessage(), tblFormasPago);
    }
    return tipoProd;
  }
  
  /**
   * @param f_fp_convenio
   * @return
   */
  private String validaCreditoCliente(int f_fp_convenio) {
	  String vRes = "";
      boolean indExisteConv = false;
      boolean indMontoValido = false;
	  try {
		  if( VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("N") ){
			  VariablesConvenio.vValCoPago = VariablesCaja.monto_forma_credito_ingresado;
		  }else if (VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("S")){
                VariablesConvenio.vValCoPago = FarmaUtility.getValueFieldJTable(tblDetallePago, 
                                                                                f_fp_convenio, 
                                                                                4).trim();
		  }
              
		  log.debug("VariablesConvenio.vValCoPago=" + VariablesConvenio.vValCoPago);
		  if(FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago)!=0){
			  log.debug("jcallo: va usar credito por convenio");
			  //verificar si hay linea con matriz y no cerrar la conexion
			  String vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
                                                     			FarmaConstants.INDICADOR_N);

			  //si hay linea
		   /*	  if ( vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                   
                    valor = DBConvenio.validaCreditoCli(VariablesConvenio.vCodConvenio, 
                                                        VariablesConvenio.vCodCliente,
                                                        VariablesConvenio.vValCoPago,
                                                        FarmaConstants.INDICADOR_S);
                    log.debug("diferencia de credito que le quedaria al cliente por convenio: " + valor);
                    diferencia = FarmaUtility.getDecimalNumber(valor);
                    if( diferencia < 0 ) {
                    	log.debug("credito insuficiente del cliente, ya que se excederia en "+diferencia);
                        vRes = "S";
                    } else {//quiere decir que tiene saldo suficiente
                    	VariablesConvenio.vValCredDis = 
                        	FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) + diferencia;
                        
                        VariablesConvenio.vCredito = 
                                DBConvenio.obtieneCredito(VariablesConvenio.vCodConvenio, 
                                                          VariablesConvenio.vCodCliente, 
                                                          FarmaConstants.INDICADOR_S);
                        log.debug("VariablesConvenio.vCredito: " +VariablesConvenio.vCredito);
                        VariablesConvenio.vCreditoUtil = 
                                DBConvenio.obtieneCreditoUtil(VariablesConvenio.vCodConvenio, 
                                                              VariablesConvenio.vCodCliente, 
                                                              FarmaConstants.INDICADOR_S);
                        log.debug("VariablesConvenio.vCreditoUtil: " +VariablesConvenio.vCreditoUtil);
                        
                        VariablesConvenio.vValCredDis = 
                                FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago.trim()); //FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago)  + diferencia ;
                        
                        vRes = "N";
                    }
                    */
                    //JMIRANDA 23.06.2010
                    //NUEVO METODO DE CONVENIO
                    if ( vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                               log.debug("Existe conexion a Matriz");
                               //Paso 1 valida que exista el convenio
                               indExisteConv = 
                                       UtilityConvenio.getIndClienteConvActivo(this, 
                                                                               tblFormasPago, 
                                                                               VariablesConvenio.vCodConvenio, 
                                                                               VariablesConvenio.vNumDocIdent,
                                                                               VariablesConvenio.vCodCliente);
                               log.error("PASO 1. FORMA PAGO.  indExisteConv: "+indExisteConv);
                               if (indExisteConv) {
                                   //Paso 2 validar el monto disponible
                                   indMontoValido = 
                                           UtilityConvenio.getIndValidaMontoConvenio(this, 
                                                                                     tblFormasPago, 
                                                                                     VariablesConvenio.vCodConvenio, 
                                                                                     VariablesConvenio.vNumDocIdent, 
                                                                                     FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago),
                                                                                        VariablesConvenio.vCodCliente
                                                                                     );
                                   log.error("PASO 2. FORMA PAGO indMontoValido: "+indMontoValido);
                                   if (indMontoValido) {
                                       log.error("eNTRO. FORMA PAGO indMontoValido: "+indMontoValido);
                                       //El convenio está activo y el monto a usar es correcto    
                                       VariablesConvenio.vValCredDis = 
                                               FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) + diferencia;
                                       
                                       VariablesConvenio.vCredito = 
                                               DBConvenio.obtieneCredito(VariablesConvenio.vCodConvenio, 
                                                                         VariablesConvenio.vCodCliente, 
                                                                         FarmaConstants.INDICADOR_S);
                                       log.debug("VariablesConvenio.vCredito: " +VariablesConvenio.vCredito);
                                       VariablesConvenio.vCreditoUtil = 
                                               DBConvenio.obtieneCreditoUtil(VariablesConvenio.vCodConvenio, 
                                                                             VariablesConvenio.vCodCliente, 
                                                                             FarmaConstants.INDICADOR_S);
                                       log.debug("VariablesConvenio.vCreditoUtil: " +VariablesConvenio.vCreditoUtil);
                                       
                                       VariablesConvenio.vValCredDis = 
                                               FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago.trim());
                                       vRes = "N";
                                   }
                               }
                              
			  } else {//quiere decir que no hay linea con matriz
				  vRes = "OUT";
			  }                         
		  } else {
			  vRes = "N";
		  }
              
              
    } catch(SQLException sql) {
      log.error("",sql);
      //FarmaUtility.showMessage(this,"Error al validar limite de credito.",null);
      FarmaUtility.moveFocus(txtNroPedido);
      vRes = "N";
    }
    
    return vRes ;
  }

  private void txtNroPedido_mouseClicked(MouseEvent e)
  {
    FarmaUtility.showMessage(this,"No puedes usar el mouse en caja. Realice un uso adecuado del sistema",txtNroPedido);
    indBorra=true;
    limpiarPagos();
    limpiarDatos();
    limpiaVariablesFormaPago();
  }

  private void tblFormasPago_mouseClicked(MouseEvent e)
  {
    FarmaUtility.showMessage(this,"No puedes usar el mouse en caja. Realice un uso adecuado del sistema",txtNroPedido);
    indBorra=true;
    limpiarPagos();
    limpiarDatos();  
    limpiaVariablesFormaPago();
  }

  private void tblDetallePago_mouseClicked(MouseEvent e)
  {
    FarmaUtility.showMessage(this,"No puedes usar el mouse en caja. Realice un uso adecuado del sistema",txtNroPedido);
    indBorra=true;
    limpiarPagos();
    limpiarDatos();  
    limpiaVariablesFormaPago();
  }

  private void txtCantidad_mouseClicked(MouseEvent e)
  {
    FarmaUtility.showMessage(this,"No puedes usar el mouse en caja. Realice un uso adecuado del sistema",txtNroPedido);
    indBorra=true;
    limpiarPagos();
    limpiarDatos();  
    limpiaVariablesFormaPago();
  }

  private void cmbMoneda_mouseClicked(MouseEvent e)
  {
    FarmaUtility.showMessage(this,"No puedes usar el mouse en caja. Realice un uso adecuado del sistema",txtNroPedido);
    indBorra=true;
    limpiarPagos();
    limpiarDatos();  
    limpiaVariablesFormaPago();
  }

  private void txtMontoPagado_mouseClicked(MouseEvent e)
  {
    FarmaUtility.showMessage(this,"No puedes usar el mouse en caja. Realice un uso adecuado del sistema",txtNroPedido);
    indBorra=true;
    limpiarPagos();
    limpiarDatos();  
    limpiaVariablesFormaPago();
  }

  private void btnAdicionar_mouseClicked(MouseEvent e)
  {
    FarmaUtility.showMessage(this,"No puedes usar el mouse en caja. Realice un uso adecuado del sistema",txtNroPedido);
    indBorra=true;
    limpiarPagos();
    limpiarDatos();  
    limpiaVariablesFormaPago();
  }
  
  private boolean validaFomaPagoConvenio()
  {
    if(!VariablesConvenio.vCodConvenio.equalsIgnoreCase("")){
      for (int i = 0 ; i <= tblDetallePago.getRowCount(); i++)
      {
        String fila = FarmaUtility.getValueFieldJTable(tblDetallePago,i,11);
        if(fila.equalsIgnoreCase(VariablesConvenio.vCodConvenio)) 
        {
          return true ;
        }
        else return false ;
      }
    } return false ;
  }
  
  /**
   * Carga la el Pedido pero en ARRAY
   * @author : dubilluz
   * @since  : 26.07.2007
   */
   
  private void colocaFormaPagoDeliveryArray(String pNumPedido)
  {
    try
    { VariablesCaja.arrayPedidoDelivery = new ArrayList();
      DBCaja.colocaFormaPagoDeliveryArray(VariablesCaja.arrayPedidoDelivery, pNumPedido);
    } catch(SQLException ex)
    {
      //log.error("",ex);
      log.error(null,ex);
      FarmaUtility.showMessage(this,"Error al obtener forma de pago delivery automatico.\n" + ex.getMessage(), tblFormasPago);
    }
  }
  /** Obtiene el Codigo de Forma del Convenio
   * @author : dubilluz
   * @since  : 26.07.2007
   */
  private String obtieneCodFormaConvenio(String pConvenio)
  { 
    String codForma = "";
    try
    {
      codForma = DBCaja.cargaFormaPagoConvenio(pConvenio);
      log.debug("CodFormaConve ***"+codForma);
    } catch(SQLException ex)
    {
      //log.error("",ex);
      log.error(null,ex);
      FarmaUtility.showMessage(this,"Error al obtener el Codigo de la forma de pago del Convenio.\n" + ex.getMessage(), tblFormasPago);
    }
    return codForma.trim();
  }
  /**
   * Valida si uso el Credito
   * @author : dubilluz
   * @since  : 26.07.2007
   */
   private int uso_Credito(String codFormaPago)
   {
     if(codFormaPago.trim().equalsIgnoreCase("N"))
     {
      if(VariablesCaja.uso_Credito_Pedido_N_Delivery.trim().equalsIgnoreCase("S"))
         return 2;
      else
         return -1;
     }
     else{
     ArrayList aux = new ArrayList();
     for(int i =0 ; i< VariablesCaja.arrayPedidoDelivery.size() ; i++)
     {
       aux = (ArrayList)VariablesCaja.arrayPedidoDelivery.get(i);
       log.debug("VAriables de formaPago >>>" +aux);
       log.debug("Comparando >>"+((String)aux.get(0)).trim()+"xxxx"+codFormaPago.trim());
       if(((String)aux.get(0)).trim().equalsIgnoreCase(codFormaPago.trim()))
        return i;
     }
     return -1;
     }
   }
  /**
   * Obtiene el Codigo de la Forma de Pago del Convenio
   *  @author : dubilluz
   *  @since  : 08/09.2007
   */   
  private String  isConvenioCredito(String codConvenio)
  {
    String indCredito ="";
    try
    {
      indCredito = DBCaja.verifica_Credito_Convenio(codConvenio.trim());
    }catch(SQLException sql)
    {
      //log.error("",sql);
      log.error(null,sql);
      FarmaUtility.showMessage(this,"Error en Obntener si da Credito el  Convenio.",null);
      FarmaUtility.moveFocusJTable(tblFormasPago);
    }
    
    return indCredito;
   }
   /**
    * Selecciona la Forma de Pago si es de Credito
    * @athor  dubilluz
    * @since  08.09.2007
    */
   private void carga_Credito_Convenio(){
   log.debug("VariablesConvenio.vPorcCoPago:"+VariablesConvenio.vPorcCoPago);
   log.debug("VariablesCaja.vIndPedidoConvenio:"+VariablesCaja.vIndPedidoConvenio);
   log.debug("VariablesConvenio.vCodConvenio: "+VariablesConvenio.vCodConvenio);
   //indicador si es credito convenio
   String indCredConvenio = isConvenioCredito(VariablesConvenio.vCodConvenio);
   String codFormaPagoActual =((String)((ArrayList)(VariablesCaja.arrayDetFPCredito.get(0))).get(0)).trim() ;
   if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase("S")){
	   log.debug("VariablesConvenio.vCodConvenio:"+VariablesConvenio.vCodConvenio );
	   if(indCredConvenio.equalsIgnoreCase("S")) {
		   //Selecciona la Forma de PAgo
		   String codFPago = "";
		   String cod_FP_Convenio = codFormaPagoActual.trim();//obtiene_cod_FPago_Convenio(VariablesConvenio.vCodConvenio);
		   cod_FP_Convenio = cod_FP_Convenio.trim() ;
		   for (int i = 0; i < tblFormasPago.getRowCount(); i++) {
			   codFPago = ((String)tblFormasPago.getValueAt(i,0)).trim();
			   log.debug("cod_FP_Convenio:"+cod_FP_Convenio +", codFPago"+codFPago);
			   if (codFPago.equalsIgnoreCase(cod_FP_Convenio)) {
				   VariablesCaja.cobro_Pedido_Conv_Credito = "S";
				   FarmaGridUtils.showCell(tblFormasPago,i,0);
				   break;
			   }
		   }
        
		   txtMontoPagado.setEnabled(true);
		   
		   //verificar si hay linea con matriz
	        VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
						 											 FarmaConstants.INDICADOR_S).trim();
	        String credito_actual = "0";
	        if(VariablesCaja.vIndLinea.equals(FarmaConstants.INDICADOR_S)){
	        	//si hay linea con matriz
	        	//Verirficamos si Tiene Saldo el Cliente..este es el que invocar a un DBLINK CORREGIR a matriz
	        	credito_actual = existsCreditoDisponible(VariablesConvenio.vCodCliente,VariablesConvenio.vCodConvenio);
	        	double cred_actual   = FarmaUtility.getDecimalNumber(credito_actual.trim());
	        	//es el campo de la Tabla Temporal
	        	double saldo_grabado_credito = FarmaUtility.getDecimalNumber(((String)((ArrayList)(VariablesCaja.arrayDetFPCredito.get(0))).get(5)).trim());
	        	log.debug("saldo_grabado_credito: " + saldo_grabado_credito);
	        	if(cred_actual > 0){
	        		//log.debug("Total a Pagar" + VariablesCaja.vValTotalPagar);
	        		VariablesCaja.valorCredito_de_PedActual = saldo_grabado_credito;      
	        		txtMontoPagado.setText("" + VariablesCaja.valorCredito_de_PedActual);
	        		txtMontoPagado.selectAll();
	        		FarmaUtility.moveFocus(txtMontoPagado);
	        		//log.debug("Foco en txtMonto");
	        	} else {
	        		FarmaUtility.showMessage(this, "El cliente no tiene saldo disponible para la forma de pago por convenio.", tblFormasPago);
	        		//FarmaUtility.moveFocus(tblFormasPago);
	        	}
        }else{
        	FarmaUtility.showMessage(this, "Error: En este momento no hay linea con matriz.\nNo podra usar la forma de pago por convenio\nSi el problema persiste comunicarse con el operador de sistema", tblFormasPago);
        }
	        btnAdicionar.setEnabled(true);
        } else {
            FarmaUtility.moveFocusJTable(tblFormasPago);
        }
      }
  }
  
  /**
   * VAlidacion de el Monto mayor al Permitido por el credito
   * @author  dubilluz
   * @since   10.09.2007
   */
  private boolean validaMontoCredito_Convenio(){
   String rpta = "S";
   String indCredConvenio="";
   if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase("S")){
     indCredConvenio = isConvenioCredito(VariablesConvenio.vCodConvenio);
     log.debug("Verificando si el Convenio " +VariablesConvenio.vCodConvenio +" tenga PorCopago > 0");
        log.debug("indCredConvenio " +indCredConvenio);
        log.debug("VariablesCaja.arrayDetFPCredito.size " +VariablesCaja.arrayDetFPCredito.size());
    if(indCredConvenio.equalsIgnoreCase("S")&& VariablesCaja.arrayDetFPCredito.size()>0)//FarmaUtility.getDecimalNumber(VariablesConvenio.vPorcCoPago)!=0)
     { 
       String cod_forma_pago_seleccionado = ((String)tblFormasPago.getValueAt(tblFormasPago.getSelectedRow(),0)).trim();
       String cod_FP_Convenio_obt = ((String)((ArrayList)(VariablesCaja.arrayDetFPCredito.get(0))).get(0)).trim() ;
       //obtiene_cod_FPago_Convenio(VariablesConvenio.vCodConvenio);
       log.debug("cod_forma_pago_seleccionado " +cod_forma_pago_seleccionado);
         log.debug("cod_FP_Convenio_obt " +cod_FP_Convenio_obt);
       if(cod_forma_pago_seleccionado.equalsIgnoreCase(cod_FP_Convenio_obt)){
       double monto_maximo = FarmaUtility.getDecimalNumber(""+VariablesCaja.valorCredito_de_PedActual); //((String)VariablesConvenio.registroFP.get(3)).trim());
       double monto_colocado = FarmaUtility.getDecimalNumber(txtMontoPagado.getText().trim());
       if(monto_colocado > monto_maximo ){
          FarmaUtility.showMessage(this, "El monto ingresado excede al Crédito.Verifique!!!", tblFormasPago);
          txtMontoPagado.setText("0.00");
          txtMontoPagado.setEnabled(false);
          FarmaUtility.moveFocus(tblFormasPago);
          rpta = "N";
        }
      else
       {
          log.info("entro AQUII"); 
          VariablesCaja.monto_forma_credito_ingresado = ""+ monto_colocado;
          VariablesCaja.uso_Credito_Pedido_N_Delivery = "S";
           log.info("VariablesCaja.monto_forma_credito_ingresado:"+VariablesCaja.monto_forma_credito_ingresado); 
           log.info("VariablesCaja.uso_Credito_Pedido_N_Delivery:"+VariablesCaja.uso_Credito_Pedido_N_Delivery); 
       }
      }
     }
    }
    if(rpta.equalsIgnoreCase("N"))
      return false;
    else
      return true;
  }
 /**
  * Verifica si el Cliente Tiene Credito
  * @author dubilluz
  * @since  10.09.2007
  */
 private String existsCreditoDisponible(String codCliente,String codConvenio){
    String resultado = "";
    try
    {
      resultado = DBCaja.getSaldoCredClienteMatriz(codCliente,codConvenio, FarmaConstants.INDICADOR_S);
      FarmaUtility.aceptarTransaccion();
      log.debug("credito del cliente : "+resultado);
    }catch(SQLException ex)
    {
      FarmaUtility.liberarTransaccion();
     //log.error("",ex);
      log.error(null,ex);
     FarmaUtility.showMessage(this,"Error al Obtener credito disponible del Cliente Actual.\n" + ex.getMessage(), tblFormasPago);
    }    
    return resultado;
 } 
 /**
  * Reinicia las Variables de Forma de Pago Auxiliares
  * @author dubilluz
  * @since  25.09.2007
  */
 public void initVariables_Auxiliares()
 {
     VariablesFidelizacion.vRecalculaAhorroPedido = false; 
    VariablesCaja.arrayDetFPCredito = new ArrayList();  
    VariablesCaja.cobro_Pedido_Conv_Credito     = "N";
    VariablesCaja.uso_Credito_Pedido_N_Delivery = "N";  
    
    VariablesCaja.arrayPedidoDelivery = new ArrayList();
    VariablesCaja.usoConvenioCredito = "";
    VariablesCaja.valorCredito_de_PedActual = 0.0;
    VariablesCaja.monto_forma_credito_ingresado = "0.00";  
 }

  private void txtNroPedido_actionPerformed(ActionEvent e)
  {
  }

  /**
    * Obtiene el tiempo maximo para la anulacion de un pedido recarga virtual
    * @author dubilluz
    * @since  09.11.2007
    */
  private String time_max(String pNumPedido)
  {
    String valor = "";
    try
      {
         valor = DBCaja.getTimeMaxAnulacion(pNumPedido);
      
      }catch(SQLException e)
      {
        //log.error("",e);
      log.error(null,e);
        FarmaUtility.showMessage(this,"Ocurrio un error al obtener tiempo maximo de anulacion de Producto Recarga Virtual.\n" + e.getMessage(),null);
      }
     return valor; 
  }
  /**
   * Retorna el numerom de telefono de recarga
   * @author dubilluz
   * @since  14.11.2007
   */
  private String num_telefono(String numPed)
  {
    String num_telefono = "";
    try
      {
         num_telefono = DBCaja.getNumeroRecarga(numPed);
      
      }catch(SQLException e)
      {
        //log.error("",e);
      log.error(null,e);
        FarmaUtility.showMessage(this,"Ocurrio un error al obtener el numero de telefono de recarga.\n" + e.getMessage(),null);
      }
     return num_telefono;     
  }
  
  /**
  * Se carga detalle forma pago campaña del pedido
  * @author JCORTEZ
  * @since 07/07/08
  * */
  private void cargaDetalleFormaPago(String NumPed){
   ArrayList array = new ArrayList();
   ArrayList myArray = new ArrayList();
   obtieneDetalleFormaPagoPedido(array,NumPed);
   log.debug("detalle forma pago :"+array);
    if(array.size()>0){
    log.debug("array :"+array);
     for (int j = 0; j < array.size(); j++){
       myArray.add(((String) ((ArrayList) array.get(j)).get(0)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(1)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(2)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(3)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(4)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(5)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(6)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(7)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(8)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(9)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(10)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(11)).trim());      
       log.debug("ROW 1 :"+myArray);
       tableModelDetallePago.data.add(myArray);
       tableModelDetallePago.fireTableDataChanged();
     }
    }
   verificaMontoPagadoPedido();
  }
  
  /**
  * Se detalle forma pago campaña del pedido
  * @author JCORTEZ
  * @since 07/07/08
  * */
  private void obtieneDetalleFormaPagoPedido(ArrayList array,String vtaNumPed){
  
   array.clear();
      try
        {
         DBCaja.getDetalleFormaPagoCampaña(array,vtaNumPed);
        }catch(SQLException e)
        {
          //log.error("",e);
        log.error(null,e);
          FarmaUtility.showMessage(this,"Ocurrio un error al obtener detalle forma pago del pedido.\n" + e.getMessage(),null);
        }
  }

  /*private void procesarBTLMF()
  {
          cobrarPedidoBTLMF(); //procesar cobro de pedido
          pedidoCobrado();
  }*/
  
  /*private void procesar(){
         
       
        //verificar si es un pedido con convenio
        if (VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
            !VariablesConvenio.vCodCliente.equalsIgnoreCase("")) {
         //if(VariablesVentas.vEsPedidoDelivery){   //JCORTEZ 06.08.09 Se valida tipo delivery, aunque sea convenio.
            log.debug("***************COBRO PEDIDO TIPO DELIVERY**********************");
            procesoCobroDelivery();
        } else {
            log.debug("*Cobro de Pedido Normal");
            //JCORTEZ 02.07.2008 la generacion de cupones no aplica convenios
            VariablesCaja.vPermiteCampaña = UtilityCaja.verificaPedidoCamp(this,VariablesCaja.vNumPedVta);
            if (VariablesCaja.vPermiteCampaña.trim().equalsIgnoreCase("S") && 
                tblDetallePago.getRowCount() > 0) {
                if (UtilityCaja.validarFormasPagoCupones(this,VariablesCaja.vNumPedVta,tblDetallePago,lblSaldo,lblVuelto)) {
                    /* Se valida las formas de pago de las campañas 
                     * de tipo Monto Descuento.
                     * Se verificara si puede permitir cobrar
                     /
                    cobrarPedido(); //procesar cobro de pedido
                }
            } else {
                cobrarPedido(); //procesar cobro de pedido
            }
            pedidoCobrado();
        }
        //Si la variable indica que de escape y recalcule to_do el ahorro del cliente
        if(VariablesFidelizacion.vRecalculaAhorroPedido){
            eventoEscape();
        }
      VariablesVentas.vProductoVirtual=false; //ASOSA, 28.04.2010
  }*/
  
  /**
   * Proceso de Cobro de Delivery
   * @author Dubilluz
   * @since  04.03.2009
   */
  /*private void procesoCobroDelivery()
  {
      log.debug("jcallo: pedido delivery con convenio");
      String valido = "S";
      colocaFormaPagoDeliveryArray(VariablesCaja.vNumPedVta);
      log.debug("VariablesCaja.arrayPedidoDelivery.size() : " + 
                VariablesCaja.arrayPedidoDelivery);
      int f_fp_convenio = -1;
      if (VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("S")) {
          f_fp_convenio = 
                  uso_Credito(obtieneCodFormaConvenio(VariablesConvenio.vCodConvenio).trim());
      } else {
          if (VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("N"))
              f_fp_convenio = uso_Credito("N");
      }

      log.debug("jcallo: numero de convenio=>" + 
                VariablesConvenio.vCodConvenio + f_fp_convenio);
      if (f_fp_convenio == -1) {
          VariablesCaja.usoConvenioCredito = "N";
      }
      if (f_fp_convenio != -1) {
          VariablesCaja.usoConvenioCredito = "S";
      }
      log.debug("Cliente su Credito f_fp_convenio: " + f_fp_convenio);
      log.debug("DUBILLUZ-24.08.2009 - VariablesCaja.usoConvenioCredito: " + VariablesCaja.usoConvenioCredito);
      if (VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) {
          String pValidaCredito = 
              validaCreditoCliente(f_fp_convenio).trim(); //aqui abre conexion remota
          if (pValidaCredito.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) { //ver si se execedio del credito disponible
              valido = "N";
              FarmaUtility.showMessage(this, 
                                       "No se puede cobrar el Pedido. \n" +
                      "El cliente excede en S/. " + (diferencia * -1) + 
                      " el limite de su crédito.", txtNroPedido);
              return;
          } else {
              if (pValidaCredito.equalsIgnoreCase("OUT")) { // NO hay conexion con matriz y no se puede cobrar el pedido
                  FarmaUtility.showMessage(this, 
                                           "En este momento no hay linea con matriz.\n " + 
                                           "Si el problema persiste comunicarse con el operador de sistema", 
                                           txtNroPedido);
                  return;
              }
          }
      }
      log.debug("estado de la validacion de credito=" + valido);
      if (valido.equalsIgnoreCase("S")) {
          cobrarPedido();
          pedidoCobrado();
      }
  }*/
  
  public void pedidoCobrado(){
      log.info("VariablesCaja.vIndPedidoCobrado:"+VariablesCaja.vIndPedidoCobrado);
	if(VariablesCaja.vIndPedidoCobrado){
		log.info("pedido cobrado !");
	    if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) && 
	    		indCerrarPantallaCobrarPed ) {
                //JCORTEZ 03.11.09 Se valida ingreso de sobre
                 log.debug("VariablesCaja.vSecMovCaja-->"+VariablesCaja.vSecMovCaja);
                    if(validaIngresoSobre()){
                        //dubilluz 20.07.2010
                        //if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Existe efectivo suficiente. Desea ingresar sobres en su turno?")){
                        if(JConfirmDialog.rptaConfirmDialog(this, "Ha excedido el importe máximo de dinero en su caja. \n" + 
                                                                "Desea hacer entrega de un nuevo sobre?\n")){
                            mostrarIngresoSobres(); 
                        }
                    }
	    	cerrarVentana(true);
	    }
	    indBorra = true;
	    limpiarDatos();
	    limpiarPagos();
	    limpiaVariablesVirtuales();
	    FarmaUtility.moveFocus(txtNroPedido);
	    VariablesVentas.vProductoVirtual=false;
	    log.debug("-********************LIMPIANDO VARIABLES***********************-");
	}
	
  }
    public void eventoEscape(){
        eventoEscape(false);
    }
  public void eventoEscape(boolean pIndAnulaPendiente){
      log.debug("VariablesCaja.vNumPedVta: "+VariablesCaja.vNumPedVta);
      log.debug("VariablesCaja.vPermiteCampaña: "+VariablesCaja.vPermiteCampaña);
      log.debug("indCerrarPantallaAnularPed: "+indCerrarPantallaAnularPed);
      log.debug("VariablesCaja.vIndPedidoSeleccionado: "+VariablesCaja.vIndPedidoSeleccionado);
      log.debug("VariablesCaja.vIndDeliveryAutomatico: "+VariablesCaja.vIndDeliveryAutomatico);
      //JCORTEZ 02.07.2008 se deja el indicador de impresio de cupon por pedido en N
      if(!VariablesCaja.vNumPedVta.equalsIgnoreCase("")){
      VariablesCaja.vPermiteCampaña=UtilityCaja.verificaPedidoCamp(this,VariablesCaja.vNumPedVta);
        if(VariablesCaja.vPermiteCampaña.trim().equalsIgnoreCase("S")){
          UtilityCaja.actualizaPedidoCupon(this,"",VariablesCaja.vNumPedVta,"N","S");
        }
      }
      
      indBorra=false;//jcortez
      
      if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) || pIndAnulaPendiente )
      {
        if(indCerrarPantallaAnularPed && VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
            
           //Se anulara el pedido 
            if(VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                if(JConfirmDialog.rptaConfirmDialog(this, "El Pedido sera Anulado. Desea Continuar?")){
                            try{
                              //DBCaja.anularPedidoPendiente(VariablesCaja.vNumPedVta); //antes ASOSA, 13.07.2010
                              DBCaja.anularPedidoPendiente_02(VariablesCaja.vNumPedVta); //ASOSA, 13.07.2010
                              FarmaUtility.aceptarTransaccion();
                              log.info("Pedido anulado.");
                              FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
                              cerrarVentana(true);
                            } catch(SQLException sql)
                            {
                              FarmaUtility.liberarTransaccion();
                              //log.error("",sql);
                              log.error(null,sql);
                              if(sql.getErrorCode()==20002)
                                FarmaUtility.showMessage(this, "El pedido ya fue anulado!!!", null); 
                              else if(sql.getErrorCode()==20003)
                                FarmaUtility.showMessage(this, "El pedido ya fue cobrado!!!", null); 
                              else    
                                FarmaUtility.showMessage(this, "Error al Anular el Pedido.\n" + sql.getMessage(), null);
                              cerrarVentana(true);
                            }
                          }                
            }
            else{
           //if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "El Pedido sera Anulado. Desea Continuar?")){
            try{
            
                //JCORTEZ 13.08.09  
              UtilityCaja.liberaProdRegalo(VariablesCaja.vNumPedVta,
                                                    ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                                        FarmaConstants.INDICADOR_S);
                
              //DBCaja.anularPedidoPendienteSinRespaldo(VariablesCaja.vNumPedVta); antes
              
              DBCaja.anularPedidoPendienteSinRespaldo_02(VariablesCaja.vNumPedVta); //ASOSA, 13.07.2010
                
                ///-- inicio de validacion de Campaña 
                // DUBILLUZ 19.12.2008
                String pIndLineaMatriz = FarmaConstants.INDICADOR_N;
                            //FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                boolean pRspCampanaAcumulad = UtilityCaja.realizaAccionCampanaAcumulada
                                       (
                                        pIndLineaMatriz,
                                        VariablesCaja.vNumPedVta,this,
                                        ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                        tblFormasPago,
                                        FarmaConstants.INDICADOR_S//Aqui si liberara stock al regalo
                                        );
                
                if (!pRspCampanaAcumulad)
                  {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                            FarmaConstants.INDICADOR_S);
                  }          
                if(pIndLineaMatriz.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                    FarmaConstants.INDICADOR_S);
                }


              FarmaUtility.aceptarTransaccion();
              log.info("Pedido anulado sin quitar respaldo.");
              //FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
              //cerrarVentana(true);
              cerrarVentana(false);
            } catch(SQLException sql)
            {
              FarmaUtility.liberarTransaccion();
              //log.error("",sql);
              log.error(null,sql);
              if(sql.getErrorCode()==20002)
                FarmaUtility.showMessage(this, "El pedido ya fue anulado!!!", null); 
              else if(sql.getErrorCode()==20003){
                    //FarmaUtility.showMessage(this,"dddddddddd",null);
                FarmaUtility.showMessage(this, "El pedido ya fue cobrado!!!", null); }
              else    
                FarmaUtility.showMessage(this, "Error al Anular el Pedido.\n" + sql.getMessage(), null);
              cerrarVentana(true);
            }
          }
          
        } else cerrarVentana(false);
      } else cerrarVentana(false);      
  }
  
  
  /**
   * Se da la opcion de ingresar sobre 
   * @AUTHOR JCORTEZ
   * @SINCE 03.11.09
   * */
  private void mostrarIngresoSobres(){
  
      DlgIngresoSobre dlgsobre = new DlgIngresoSobre(myParentFrame,"",true);
      dlgsobre.setVisible(true);
      
     /* cargarDatosSobre();
      DlgIngresoSobreParcial ingreso=new DlgIngresoSobreParcial(myParentFrame,"",true);
      ingreso.setVisible(true);*/
      
      if(FarmaVariables.vAceptar){
           cerrarVentana(true); 
      }
  }
  
  private void cargarDatosSobre(){
      
  VariablesCaja.vCajero=FarmaVariables.vIdUsu;
  
      
  }
  
  /**
   * 
   * Se valida el ingreso de sobre en local
   * @AUTHOR JCORTEZ
   * @SINCE 03.11.09
   * */
  private boolean validaIngresoSobre() {
    boolean valor=false;
    String ind="";
    try
         {
         log.debug("VariablesCaja.vSecMovCaja-->"+VariablesCaja.vSecMovCaja);
          ind=DBCaja.permiteIngreSobre(VariablesCaja.vSecMovCaja);
             log.debug("indPermiteSobre-->"+ind);
          if(ind.trim().equalsIgnoreCase("S")){
           valor=true;
           }
          
         }catch (SQLException sql){
           valor=false;
           log.error("",sql);
           FarmaUtility.showMessage(this,"Ocurrio un error validar ingreso de sobre.\n"+sql.getMessage(),null);
         }
    return valor;
  }

    public String getMensajeComprobanteConvenio(String pCodConvenio){
        String pCadena = "";
        try {

                double montoPedido = FarmaUtility.getDecimalNumber(lblSoles.getText().trim());

            pCadena = DBConvenioBTLMF.getMsgComprobante(pCodConvenio,montoPedido,VariablesConvenioBTLMF.vValorSelCopago);
            log.debug(pCadena);
        } catch (SQLException e) {
            pCadena = "N";
            log.error("",e);
        }
        return pCadena;
    }

    private void procesoNuevoCobroFV() {
        
        if (validaFormasPago()) {

        if (pValidaImpresoras()){
        //if (true){
                
                double pValorSaldo = FarmaUtility.getDecimalNumber(lblSaldo.getText().trim());


                if (pValorSaldo == 0) {

                    String pSoles = "0";
                    String pDolares = "0";
                    String pTarjeta = "0";

                    String vCodFormaPago = "";
                    String vDescFormaPago = "";
                    String vCantidadCupon = "";
                    String vDescMonedaPago = "";
                    String vValMontoPagado = "";
                    String vValTotalPagado = "";
                    String vCodMonedaPago = "";
                    //"0.00" = //VUELTO lista
                    String vNumTarjCred = "";
                    String vFecVencTarjCred = "";
                    String vNomCliTarjCred = "";
                    //"" =  lista.get(11).toS
                    String vDNITarj = "";
                    String vCodVoucher = "";
                    String vCodLote = "";


                    log.info("****  imprimeFormasPago ****");
                    for (int i = 0; i < tableModelDetallePago.data.size(); i++) {
                        ArrayList lista = (ArrayList)tableModelDetallePago.data.get(i);
                        log.info("****  FILA  **** " + i + " *****");

                        if (lista.get(0).toString().equalsIgnoreCase("00001"))
                            pSoles = lista.get(4).toString().trim();

                        if (lista.get(0).toString().equalsIgnoreCase("00002"))
                            pDolares = lista.get(4).toString().trim();

                        if (!(lista.get(0).toString().equalsIgnoreCase("00002") ||
                              lista.get(0).toString().equalsIgnoreCase("00001"))) {
                            pTarjeta = lista.get(4).toString().trim();
                            vCodFormaPago = lista.get(0).toString().trim();
                            vDescFormaPago = lista.get(1).toString().trim();
                            vCantidadCupon = lista.get(2).toString().trim();
                            vDescMonedaPago = lista.get(3).toString().trim();
                            vValMontoPagado = lista.get(4).toString().trim();
                            vValTotalPagado = lista.get(5).toString().trim();
                            vCodMonedaPago = lista.get(6).toString().trim();
                            //= //VUELTO lista.get(7).toString().trim();
                            vNumTarjCred = lista.get(8).toString().trim();
                            vFecVencTarjCred = lista.get(9).toString().trim();
                            vNomCliTarjCred = lista.get(10).toString().trim();
                            // lista.get(11).toString().trim();
                            vDNITarj = lista.get(12).toString().trim();
                            vCodVoucher = lista.get(13).toString().trim();
                            vCodLote = lista.get(14).toString().trim();

                        }

                        for (int j = 0; j < lista.size(); j++) {
                            log.info("j=" + j + "-" + lista.get(j));

                        }
                    }
                    log.info("**** INVOCA NUEVO COBRO  **");
                    log.info("**** DlgNuevoCobro **");
                    VariablesCaja.vNumPedPendiente = txtNroPedido.getText();
                    DlgNuevoCobro dlgNuevoCobro = new DlgNuevoCobro(myParentFrame, "", true);
                    dlgNuevoCobro.pEjecutaOldCobro = true;
                    dlgNuevoCobro.setIndPedirLogueo(false);
                    dlgNuevoCobro.setIndPantallaCerrarAnularPed(true);
                    dlgNuevoCobro.setIndPantallaCerrarCobrarPed(true);
                    dlgNuevoCobro.setDlgFormPago(this);
                    dlgNuevoCobro.this_windowOpened(null);
                    dlgNuevoCobro.txtMontoPagado.setText(pSoles); //SOLES
                    dlgNuevoCobro.txtMontoPagadoDolares.setText(pDolares); //DOLARES
                    dlgNuevoCobro.txtMontoTarjeta.setText(pTarjeta); //TARJETA

                    log.info("pSoles>>" + pSoles);
                    log.info("pDolares>>" + pDolares);
                    log.info("pTarjeta>>" + pTarjeta);
                    if (FarmaUtility.getDecimalNumber(pSoles.trim()) > 0)
                        dlgNuevoCobro.txtMontoPagado_keyPressed(null);

                    if (FarmaUtility.getDecimalNumber(pDolares.trim()) > 0)
                        dlgNuevoCobro.txtMontoPagadoDolares_keyPressed(null);

                    if (FarmaUtility.getDecimalNumber(pTarjeta.trim()) > 0) {

                        VariablesCaja.vIndTarjetaSeleccionada = true;
                        VariablesCaja.vIndDatosTarjeta = true;
                        //log.info("****VariablesCaja.vIndTarjetaSeleccionada  ** "+VariablesCaja.vIndTarjetaSeleccionada);
                        //log.info("****VariablesCaja.vIndDatosTarjeta  ** "+VariablesCaja.vIndDatosTarjeta);
                        //log.info("****dlgNuevoCobro.adicionaDetallePago(dlgNuevoCobro.txtMontoTarjeta)");
                        VariablesCaja.vCodFormaPago = vCodFormaPago;
                        VariablesCaja.vDescFormaPago = vDescFormaPago;
                        VariablesCaja.vCantidadCupon = vCantidadCupon;
                        VariablesCaja.vDescMonedaPago = vDescMonedaPago;
                        VariablesCaja.vValMontoPagado = vValMontoPagado;
                        VariablesCaja.vValTotalPagado = vValTotalPagado;
                        VariablesCaja.vCodMonedaPago = vCodMonedaPago;
                        //"0.00" = //VUELTO lista.get(0).toString(7).trim();
                        VariablesCaja.vNumTarjCred = vNumTarjCred;
                        VariablesCaja.vFecVencTarjCred = vFecVencTarjCred;
                        VariablesCaja.vNomCliTarjCred = vNomCliTarjCred;
                        //"" = lista.get(11).toString().trim();
                        VariablesCaja.vDNITarj = vDNITarj;
                        VariablesCaja.vCodVoucher = vCodVoucher;
                        VariablesCaja.vCodLote = vCodLote;
                        dlgNuevoCobro.adicionaDetallePago(dlgNuevoCobro.txtMontoTarjeta);
                    }
                    dlgNuevoCobro.imprimeFormasPago();                    
                    
                    if(VariablesVentas.vEsPedidoDelivery&&!(VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("S")))
                    {
                        // GRABA EL PEDIDO EN TEMPORAL
                        // DUBILLUZ 30.06.2014
                        dlgNuevoCobro.grabaPedidoProformaDelivery();
                    }
                    else{
                        UtilityCaja.grabarNuevoCobro(dlgNuevoCobro, this,myParentFrame);    
                    }
                    
                    
                    pedidoCobrado();
                } else
                    FarmaUtility.showMessage(this, "No puede cobrar , tiene saldo por Cobrar\n" +
                            "Verifique las formas de pago ingresadas.", null);
            }
                
        }
        else {
            FarmaUtility.showMessage(this,"No es valido cobrar con más de una forma de pago Tarjeta.\n" +
                "Por favor solo haga el cobro con Soles,Dolares y Una Tarjeta.",tblFormasPago);    
        }
    }

    private boolean validaFormasPago() {
        boolean presultado = false;
        String vCodigoFormaPago = "";
        int pPagoSoles=0,pPagoDolares=0,pPagoTarjeta=0,pPagoConvCredito=0;
        log.info("****  imprimeFormasPago ****");
        for (int i = 0; i < tableModelDetallePago.data.size(); i++) {
            ArrayList lista = (ArrayList)tableModelDetallePago.data.get(i);
            log.info("****  FILA  **** " + i + " *****");
            vCodigoFormaPago  = lista.get(0).toString();
            
            if (lista.get(0).toString().equalsIgnoreCase("00001"))
                pPagoSoles++;
            else
                if (lista.get(0).toString().equalsIgnoreCase("00002"))
                   pPagoDolares++;
                else
                    if (lista.get(0).toString().equalsIgnoreCase("00080"))
                       pPagoConvCredito++;
                    else //TARJETA
                       pPagoTarjeta++;
        }
        
        if(pPagoTarjeta>=2)
            presultado = false;
        else
            if((pPagoSoles+pPagoDolares+pPagoTarjeta+pPagoConvCredito)<=4)
              presultado =true;
            else
                presultado =false;
            
        return presultado;
    }
    
    public boolean pValidaImpresoras(){
        //CHUANES 14.03.2014 //Verificamos la ruta y el acceso ala impresora correspondiente a imprimir 
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF){
            //1.Recupera comprobantes por convenio
            String[] comp = UtilityConvenioBTLMF.getComprobantesConvenio(this);
            //2.Itera por cada documento
            for(String pTipoComp : comp){                    
                if(!UtilityCaja.verificaImpresora(this,null, pTipoComp)) return false;
            }
        }else if(!UtilityCaja.verificaImpresora(this,null, VariablesVentas.vTip_Comp_Ped)){
            return false;
        }        
        return true;
    }

    private void txtMontoPagado_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMontoPagado,e);
        
    }
    
    public static boolean isFormatoValidoMonto( String cCadena ) {
        boolean b = false;//Pattern.matches("[0-9]", cCadena);
        
        double mMontoMin = 0.00;
        double mMontoMax = 999999.99;
        double pMontoIngresado = Double.parseDouble(cCadena.trim());
        
        if(pMontoIngresado>=mMontoMin&&pMontoIngresado<=mMontoMax)
            b=true;
        
        return b;
       }    
    public Frame getMyParent(){
        return this.myParentFrame;
    }

    public String getDescProductoRecVirtual() {
        return descProductoRecVirtual;
    }

    public void setDescProductoRecVirtual(String descProductoRecVirtual) {
        this.descProductoRecVirtual = descProductoRecVirtual;
    }
}
