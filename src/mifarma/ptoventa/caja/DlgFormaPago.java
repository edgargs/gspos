package mifarma.ptoventa.caja;

import com.gs.mifarma.componentes.JButtonFunction;

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
import mifarma.common.FarmaConnection;
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
 
 
 
 
 
 
 
 
 
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;
 
 
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.gs.mifarma.componentes.JLabelFunction;

import java.text.SimpleDateFormat;

import java.util.Date;

 
 

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFormaPago.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      01.03.2005   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgFormaPago extends JDialog  {

  private static final Logger log = LoggerFactory.getLogger(DlgFormaPago.class);
  
  /** Almacena el Objeto Frame de la Aplicación - Ventana Principal */
  private Frame myParentFrame;

  private FarmaTableModel tableModelFormasPago;
  private FarmaTableModel tableModelDetallePago;

  private boolean indPedirLogueo = true;
  private boolean indCerrarPantallaAnularPed = false;
  private boolean indCerrarPantallaCobrarPed = false;
  private String valor = "" ;
  private double diferencia = 0 ;
  
  //  08.07.08
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
  private JTextField txtMontoPagado = new JTextField();
  private JButton btnAdicionar = new JButton();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
    private JPanel pnlTotales = new JPanel();
   
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
   
  private JTextField txtNroPedido = new JTextField();
  private JButton btnPedido = new JButton();
  private JLabel lblDolares = new JLabel();
  private JLabel lblSoles = new JLabel();
  private JLabel lblDolaresT = new JLabel();
  private JLabel lblSolesT = new JLabel();
  private JLabel lblTotalPagar = new JLabel();
  private JScrollPane scrDetallePago = new JScrollPane();
  private JPanel pnlDetallePago = new JPanel();
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
      e.printStackTrace();
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
    this.setTitle("Cobrar Pedido");
    	lblCoPagoT.setText(" ");
    	lblCoPago.setText("  ");
    
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
    pnlTotales.setLayout(null);


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
    pnlTotal.setLayout(null);
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
    pnlDetallePago.setLayout(null);
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
        pnlTotales.add(lblSaldoT);
        pnlTotales.add(lblSaldo);
        pnlTotales.add(lblCoPagoT);
        pnlTotales.add(lblCoPago);
        pnlTotales.add(lblVueltoT);
        pnlTotales.add(lblVuelto);
    pnlFormaPago.add(lblTipoCambio, null);
    pnlFormaPago.add(lblTipoCambioT, null);
    pnlFormaPago.add(lblTipoComprobante, null);
    pnlFormaPago.add(lblTipoComprobante_T, null);
    pnlTotal.add(lblFecPed);
    pnlTotal.add(txtNroPedido);
    pnlTotal.add(btnPedido);
    pnlTotal.add(lblDolares);
        pnlTotal.add(lblSoles);
        pnlTotal.add(lblDolaresT);
        pnlTotal.add(lblSolesT);
        pnlTotal.add(lblTotalPagar);
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
        pnlDetallePago.add(lblDNI_SIN_COMISION);
        pnlDetallePago.add(btnDetallePago);
        jContentPane.add(pnlDetallePago, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF2, null);
        //this.getContentPane().add(jContentPane, null);


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
   * @author :  
   * @since  : 07.09.2007
   */
  
   /*
  private boolean cargaFormasPago(String indConvenio,String codConvenio,String codCliente)
  {
  System.out.println("Cargando Formas de Pago");
  String numPed=VariablesCaja.vNumPedVta;
    try{
      DBCaja.obtieneFormasPago(tableModelFormasPago,indConvenio,codConvenio,codCliente,numPed);
      FarmaUtility.ordenar(tblFormasPago, tableModelFormasPago, 0, FarmaConstants.ORDEN_ASCENDENTE);
      String fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
      lblFecPed.setText(fechaSistema);
      return true;
    } catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this, "Error al obtener las Formas de Pago.\n" + sql.getMessage(), txtNroPedido);
      return false;
    }
  }
   */
    private boolean cargaFormasPago(String indConvenio,String codConvenio,String codCliente)
    {
      System.err.println("Metodo Cargando Formas de Pago :" + indConvenio);
    String numPed=VariablesCaja.vNumPedVta;
  
    String creditoSaldo="";
    String esCredito ="";
    String valorCredito="";
    boolean valor = false;

     if (indConvenio.equalsIgnoreCase("S"))
     {

    	 
          valor = cargaFormasPagoSinConvenio(indConvenio,codConvenio, codCliente);
             System.err.println("---------Forma de Pago Sin Convenio----------");

         }
         return valor;
    }

     

      private boolean cargaFormasPagoConvenio(String indConvenio,String codConvenio,String codCliente,String valorCredito) {

          System.out.println("Cargando Formas de Pago Convenio");
          String numPed=VariablesCaja.vNumPedVta;
          
          
          
            try{
               
              DBCaja.obtieneFormasPagoConvenio(tableModelFormasPago,indConvenio,codConvenio,codCliente,numPed,valorCredito);
              FarmaUtility.ordenar(tblFormasPago, tableModelFormasPago, 0, FarmaConstants.ORDEN_ASCENDENTE);
              String fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
              lblFecPed.setText(fechaSistema);
              return true;
            } catch(SQLException sql)
            {
              sql.printStackTrace();
              FarmaUtility.showMessage(this, "Error al obtener las Formas de Pago Convenio.\n" + sql.getMessage(), txtNroPedido);
              return false;
            }

          
      }





      private boolean cargaFormasPagoSinConvenio(String indConvenio,String codConvenio,String codCliente)
      {
      System.out.println("Cargando Formas de Pago Sin Convenio");
      //String numPed=VariablesCaja.vNumPedVta;
      //  - 09.06.2011
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
          sql.printStackTrace();
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

      if(VariablesFidelizacion.vSIN_COMISION_X_DNI)lblDNI_SIN_COMISION.setVisible(true);
      else lblDNI_SIN_COMISION.setVisible(false);
      
      
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtNroPedido);
    lblMsjPedVirtual.setVisible(false);
    lblMsjNumRecarga.setVisible(false);
    VariablesCaja.vIndPedidoConProdVirtual = false;
    //Reinicia Variables
    initVariables_Auxiliares();

    System.out.println("<<<<<<<<<<indPedirLogueo>>>>>>>>>>>:"+indPedirLogueo);
      txtNroPedido.setText("" + VariablesCaja.vNumPedPendiente);
        

    if(indPedirLogueo){
      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      dlgLogin.setRolUsuario(FarmaConstants.ROL_CAJERO);
      dlgLogin.setVisible(true);
      if ( FarmaVariables.vAceptar ) {
        FarmaVariables.dlgLogin = dlgLogin;
        if(!UtilityCaja.existeCajaUsuarioImpresora(this, null)) cerrarVentana(false);
        FarmaVariables.vAceptar = false;

        System.out.println("<<<<<<<<<<esActivoConvenioBTLMF>>>>>>>>>>>:");
        
        cargaFormasPago("N","N","0");
        

      } else  cerrarVentana(false);
    } else
    {
      if(!UtilityCaja.existeCajaUsuarioImpresora(this, null) || !UtilityCaja.validaFechaMovimientoCaja(this,tblFormasPago))
      {
        FarmaUtility.showMessage(this, "El Pedido sera Anulado. Vuelva a generar uno nuevo.", null);
        try{
          DBCaja.anularPedidoPendiente(VariablesVentas.vNum_Ped_Vta);
            
            //  07.01.09 devuelve canje o historico
            System.out.println("Devolviendo canje o Historico");      
            anularAcumuladoCanje();
            VariablesCaja.vCierreDiaAnul=false;
            
          FarmaUtility.aceptarTransaccion();
          FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
          cerrarVentana(true);
          return;
        } catch(SQLException sql)
        {
          FarmaUtility.liberarTransaccion();
          sql.printStackTrace();
          FarmaUtility.showMessage(this, "Error al Anular el Pedido.\n" + sql.getMessage(), null);
          cerrarVentana(true);
          return;
        }
      }

      
        System.out.println("/*GOGO2*/"+tableModelDetallePago.getRowCount());
      
      if(!validaPedidoDiario()) return;
        System.out.println("/*GOGO3/"+tableModelDetallePago.getRowCount());
      buscaPedidoDiario();
        System.out.println("/*GOGO4*/"+tableModelDetallePago.getRowCount());
      
      
        
        if(!cargaFormasPago("N","N","0")) return;
        
        
      VariablesCaja.vNumPedPendiente = "";
      VariablesCaja.vFecPedACobrar = "";
      FarmaVariables.vAceptar = false;
      FarmaUtility.moveFocus(tblFormasPago);
        System.out.println("/*GOGO5*/"+tableModelDetallePago.getRowCount());
      if(VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) btnFormaPago.doClick();
        System.out.println("/*GOGO6*/"+tableModelDetallePago.getRowCount());
      //btnFormaPago.doClick();
    }
    
    if(FarmaVariables.vTipCaja.equals(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL))
    {
      lblF2.setVisible(false);
      lblF1.setVisible(false);
    }
      System.out.println("/***************************************************************/");
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

    	
      cambioTipoComprobante();
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
        //sql.printStackTrace();
        log.error(null,sql);
        FarmaUtility.showMessage(this,"Error al obtener fecha del sistema - \n" +sql.getMessage(),txtNroPedido);
      }
      //lblFecPed.setText("" + VariablesCaja.vFecPedACobrar);
      if(!validaPedidoDiario()) return;
      
      buscaPedidoDiario();
      //System.out.println("**enter "+tblFormasPago.getSelectedRow());
      if(VariablesCaja.cobro_Pedido_Conv_Credito.equalsIgnoreCase("N") &&
         VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
          btnFormaPago.doClick();
      }

        lblTipoComprobante.setVisible(true);
        lblTipoComprobante_T.setVisible(true);

          lblTipoComprobante_T.setBounds(new Rectangle(0, 5, 155, 15));
          lblTipoComprobante.setVisible(true);

      
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
       * @author   
       * @since   10.09.2007
       */
      txtMontoPagado.setText("0.00");
      txtMontoPagado.setEnabled(false);
      cmbMoneda.setEnabled(false);      
      btnAdicionar.setEnabled(false);        
      System.out.println("foco a tblFormaPago");      
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
      btnAdicionar.doClick();
    } else
      chkkeyPressed(e);
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
      
       //  25/06/08 se valida cobro de pedido por cupones
      ArrayList array =new ArrayList();
        try{
         DBCaja.obtieneMontoFormaPagoCuponCampaña(array,codFormaPago, cantidad);
        }catch(SQLException sql){
         sql.printStackTrace();
         FarmaUtility.showMessage(this,"Error al obtener cantidad de cupones - \n" +sql.getMessage(),txtCantidad);
        }
      String cantCuponMax="";
      String montoCupon="";
     
      if(array.size()>0){
       cantCuponMax=((String)((ArrayList) array.get(0)).get(0)).trim();
       montoCupon=((String) ((ArrayList) array.get(0)).get(1)).trim();
        System.out.println("cantCuponMax : "+cantCuponMax);
        System.out.println("MontoCupon : "+montoCupon);
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
    if(e.getKeyCode() == KeyEvent.VK_F1)
    {
        if(!FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL))
        {        
          DlgPedidosBuscar dlgPedidosBuscar = new DlgPedidosBuscar(myParentFrame,"",true);
          dlgPedidosBuscar.setVisible(true);
          if(FarmaVariables.vAceptar)
            txtNroPedido.setText(VariablesCaja.vNumPedPendiente);
        }
    } else if(e.getKeyCode() == KeyEvent.VK_F2)
    {
       System.out.println("armaVariables.vTipCaja:::"+FarmaVariables.vTipCaja);

      if(!FarmaVariables.vTipCaja.equals(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL))
      {
        indBorra=true;//  08.07.08
        limpiarDatos();
        limpiarPagos();
        FarmaUtility.moveFocus(txtNroPedido);
      }

     
    } else if(e.getKeyCode() == KeyEvent.VK_F4)
    {
        
      if (VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
          
          

          limpiarPagos();
          btnFormaPago.doClick();
      }
    } else if(e.getKeyCode() == KeyEvent.VK_F5)
    {

    	

      DlgPedidosPendientes dlgPedidosPendientes = new DlgPedidosPendientes(myParentFrame,"",true);
      dlgPedidosPendientes.setVisible(true);
      if(FarmaVariables.vAceptar)
      {
      
         indBorra=false;
         
        //System.out.println("VariablesCaja.vIndConvenio : "+VariablesCaja.vIndConvenio);
        //System.out.println("VariablesCaja.vCodConvenio : "+VariablesCaja.vCodConvenio);
        //System.out.println("VariablesCaja.vCodCliLocal : "+VariablesCaja.vCodCliLocal);
         
        cargaFormasPago(VariablesCaja.vIndConvenio,VariablesCaja.vCodConvenio,VariablesCaja.vCodCliLocal);
        

        txtNroPedido.setText("" + VariablesCaja.vNumPedPendiente);
        lblFecPed.setText("" + VariablesCaja.vFecPedACobrar);
        if(!validaPedidoDiario()) return;
        buscaPedidoDiario();
        
        //  - no se deben borrar las variables del pedido luego de seleccinar
        /*VariablesCaja.vNumPedPendiente = "";
        VariablesCaja.vFecPedACobrar = "";*/
        
        FarmaVariables.vAceptar = false;
        //añadido 21.09.2007  
        System.out.println("VariablesCaja.cobro_Pedido_Conv_Credito : "+VariablesCaja.cobro_Pedido_Conv_Credito);
        if(VariablesCaja.cobro_Pedido_Conv_Credito.equalsIgnoreCase("N"))
        {
        System.out.println("VariablesCaja.cobro_Pedido_Conv_Credito : "+VariablesCaja.cobro_Pedido_Conv_Credito); 
        if(VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) btnFormaPago.doClick();
        }
        else{
        FarmaUtility.moveFocus(txtMontoPagado);
        btnMonto.doClick();
        System.out.println("Foco lo coloco en TxtMonto  2222");
        }
        
        verificaMontoPagadoPedido();// 

     }
       
    } else if(e.getKeyCode() == KeyEvent.VK_F6)
    {

    	
      VariablesCaja.vSecMovCajaOrigen = VariablesCaja.vSecMovCaja;
      VariablesPtoVenta.vSecMovCajaOrigen = VariablesCaja.vSecMovCaja;
      VariablesPtoVenta.vTipAccesoListaComprobantes=ConstantsPtoVenta.TIP_ACC_LISTA_COMP_CAJA;
      DlgReportePedidosComprobantes dlgReportePedidosComprobantes = new DlgReportePedidosComprobantes(myParentFrame,"",true);
      dlgReportePedidosComprobantes.setVisible(true);

    	
    } else if(e.getKeyCode() == KeyEvent.VK_F7)
    {

   
      configuracionComprobante();
    	
    }else if(e.getKeyCode() == KeyEvent.VK_F8)
    {

    
       //  03.10.09 Se verifica si se permite o no ingreso de sobres
        if(validaIngresoSobre())
         mostrarIngresoSobres();     
        else
        FarmaUtility.showMessage(this, "Opción no habilitada por el momento", null);
         

    } else if(e.getKeyCode() == KeyEvent.VK_F11)
    {
        boolean permiteCobrar = existeStockPedido(VariablesCaja.vNumPedVta);
        if(permiteCobrar){
        //  09.07.2009.sn graba el tiempo dei nicio de cobro
        try{
            DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"I");
            System.out.println("Grabo el tiempo de inicio de cobro");
        }
        catch(SQLException sql){
            //FALTA CORREGIR ESTO
            FarmaUtility.liberarTransaccion(); //  22.02.2010            
            sql.printStackTrace();
            System.out.println("Error al grabar el tiempo de inicio de cobro");
        }
        //  09.07.2009.en graba el tiempo de nicio de cobro
        
        //se guarda valores 
        VariablesCaja.vVuelto=lblVuelto.getText().trim();

	        
        procesar();
	        

        }
        else{
            
            FarmaUtility.liberarTransaccion();  
            System.out.println("libera para liberar el bloqueo de productos.");
            
            FarmaUtility.showMessage(this, 
                                     "No puede cobrar el pedido\n" +
                "Porque no hay stock suficiente para poder generarlo ó\n" +
                "Existe un Problema en la fracción de productos.", 
                                     null);
        }
        
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
        
        eventoEscape();
        /*
        //  02.07.2008 se deja el indicador de impresio de cupon por pedido en N
        if(!VariablesCaja.vNumPedVta.equalsIgnoreCase("")){
        VariablesCaja.vPermiteCampaña=verificaPedidoCamp(VariablesCaja.vNumPedVta);
          if(VariablesCaja.vPermiteCampaña.trim().equalsIgnoreCase("S")){
            actualizaPedidoCupon("",VariablesCaja.vNumPedVta,"N","S");
          }
        }
        
        indBorra=false;// 
        
        if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) )
        {
          if(indCerrarPantallaAnularPed && VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
              
             //Se anulara el pedido 
              if(VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                  if(FarmaUtility.rptaConfirmDialog(this, "El Pedido sera Anulado. Desea Continuar?")){
                              try{
                                DBCaja.anularPedidoPendiente(VariablesCaja.vNumPedVta);
                                FarmaUtility.aceptarTransaccion();
                                log.info("Pedido anulado.");
                                FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
                                cerrarVentana(true);
                              } catch(SQLException sql)
                              {
                                FarmaUtility.liberarTransaccion();
                                //sql.printStackTrace();
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
             //if(FarmaUtility.rptaConfirmDialog(this, "El Pedido sera Anulado. Desea Continuar?")){
              try{
                DBCaja.anularPedidoPendienteSinRespaldo(VariablesCaja.vNumPedVta);
                  ///-- inicio de validacion de Campaña 
                  //   19.12.2008
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
                //sql.printStackTrace();
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

  private void cerrarVentana(boolean pAceptar)
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
    
    /**
     * VAriables usadas para Convenio Tipo Credito
     * @author  
     * @since  08.09.2007
     */
    VariablesCaja.cobro_Pedido_Conv_Credito = "N";
    VariablesCaja.valorCredito_de_PedActual = 0.0; 
    VariablesCaja.arrayDetFPCredito = new ArrayList();
    VariablesCaja.monto_forma_credito_ingresado = "0.00";
    VariablesCaja.uso_Credito_Pedido_N_Delivery = "N";
    VariablesCaja.usoConvenioCredito = "";
    
    
    /**
     * Para mostrar datos en ticket
     * @author  
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

  //  07.07.08 se carga el detalle de forma de pago del pedido
  //String numped=((String) ((ArrayList) pArrayList.get(0)).get(0)).trim();
   if(!indBorra){
    cargaDetalleFormaPago(VariablesCaja.vNumPedVta);
    complementarDetalle(); 
      
     ArrayList array = new ArrayList();
     String codsel="",codobt="";
     obtieneDetalleFormaPagoPedido(array,VariablesCaja.vNumPedVta);
     System.out.println("detalle forma pago :"+array);
      if(array.size()>0 && !indBorra){
        System.out.println("array :"+array);
        for (int j = 0; j < array.size(); j++){
          codsel=(((String) ((ArrayList) array.get(j)).get(0)).trim());     
          for (int i = 0; i < tblDetallePago.getRowCount(); i++){
            codobt=((String) tblDetallePago.getValueAt(i,0)).trim();
            System.out.println("codsel :"+codsel);
            System.out.println("codobt :"+codobt);
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
    {
      //System.out.println("VariablesCaja.vFecPedACobrar :: >>"+VariablesCaja.vFecPedACobrar);
      DBCaja.obtieneInfoCobrarPedido(myArray, numPedDiario, VariablesCaja.vFecPedACobrar);
      //System.out.println("VAriables del Pedido :: >>"+myArray);
      validaInfoPedido(myArray);
    } catch(SQLException sql)
    {
      //sql.printStackTrace();
      log.error(null,sql);
      FarmaUtility.showMessage(this, "Error al obtener Informacion del Pedido.\n" + sql.getMessage(), txtNroPedido);
    }
      if(VariablesFidelizacion.vSIN_COMISION_X_DNI)lblDNI_SIN_COMISION.setVisible(true);
      else lblDNI_SIN_COMISION.setVisible(false);

  }

  private boolean validaPedidoDiario()
  {
    String numPedDiario = txtNroPedido.getText().trim();
    if(numPedDiario.equalsIgnoreCase("")){
      FarmaUtility.showMessage(this, "Ingrese un numero de pedido diario.", txtNroPedido);
      return false;
    }
    if(!FarmaUtility.isInteger(numPedDiario) || Integer.parseInt(numPedDiario) <= 0){
      FarmaUtility.showMessage(this, "Ingrese un numero de pedido diario valido.", txtNroPedido);
      return false;
    }
    return true;
  }

  private void validaInfoPedido(ArrayList pArrayList)
  {
    //System.out.println("validaInfoPedido");
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
      
      //  07.07.08 se carga el detalle de forma de pago del pedido
      if(tblDetallePago.getRowCount()<1){
        cargaDetalleFormaPago(VariablesCaja.vNumPedVta);        
      }
      
      complementarDetalle(); 
      verificaMontoPagadoPedido();
    }
  }

  private void muestraInfoPedido(ArrayList pArrayList)
  {
    //System.out.println("muestraInfoPedido");
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
    if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase("N"))
        cargaFormasPago("N","N","0");
    

    if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
      log.info("Es Pedido Convenio");
     if(!VariablesCaja.vIndDeliveryAutomatico.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
      colocaFormaPagoPedidoConvenio(VariablesCaja.vNumPedVta);
   
      verificaMontoPagadoPedido();
     }
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
    } else if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)){ //  24092009.sn
        lblTipoComprobante.setText("BOLETA");
        lblRazSoc_T.setText("Cliente :");
    } else if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
        lblTipoComprobante.setText("TICKET");
        lblRazSoc_T.setText("Cliente :");
    }//  24092009.en
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
    System.out.println("VariablesCaja.vIndTotalPedidoCubierto: "+VariablesCaja.vIndTotalPedidoCubierto);
    System.out.println("codFormaPago: "+codFormaPago);
    System.out.println("codOperTarj : "+codOperTarj);
    System.out.println("indTarjeta  : "+indTarjeta);


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
     * @author   
     * @since   10.09.2007
     */
    
    
    ////
    if(codFormaPago.equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES))
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
      System.out.println("FORMA DE PAGO - NO TARJETA");
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
        //if(!FarmaUtility.rptaConfirmDialog(this, "Está seguro de adicionar esta forma de pago?")) return;
        
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

    if(!validaMontoIngresado()) return;
    System.out.println("VariablesCaja.vIndTotalPedidoCubierto: "+VariablesCaja.vIndTotalPedidoCubierto);
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


  // 
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


  private void operaListaDetallePago()
  {
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
    
    System.out.println("ALFREDO: "+    VariablesCaja.vCodFormaPago+", "+
                        VariablesCaja.vDescFormaPago+","+
    VariablesCaja.vCantidadCupon+","+
    VariablesCaja.vDescMonedaPago+","+
    VariablesCaja.vValMontoPagado+","+
    VariablesCaja.vValTotalPagado+","+
    VariablesCaja.vCodMonedaPago+","+
    "0.00"+","+
    VariablesCaja.vNumTarjCred+","+
    VariablesCaja.vFecVencTarjCred+","+
    VariablesCaja.vNomCliTarjCred+","+
    "nada");
    
    tableModelDetallePago.data.add(myArray);
    tableModelDetallePago.fireTableDataChanged();
    txtMontoPagado.setText("0.00");
    System.err.println("SET VARIABLES DE FORMA DE PAGO");
    
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
    System.out.println("tblDetallePago.getRowCount(): "+tblDetallePago.getRowCount());
    System.out.println("vIndTotalPedidoCubierto: "+VariablesCaja.vIndTotalPedidoCubierto);
    if(tblDetallePago.getRowCount() <= 0) 
      return;
    double montoTotal = 0;
    double montoFormaPago = 0;
    for(int i=0; i<tblDetallePago.getRowCount(); i++)
    {
      montoFormaPago = FarmaUtility.getDecimalNumber(((String)tblDetallePago.getValueAt(i,5)).trim());
      montoTotal = montoTotal + montoFormaPago;
    }
    	System.out.println("VariablesCaja.vValTotalPagar=" + VariablesCaja.vValTotalPagar);
    	System.out.println("montoTotal=" + montoTotal);
    if( FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar) > montoTotal ){
      System.out.println("No Cubierto");
      VariablesCaja.vIndTotalPedidoCubierto = false;
      VariablesCaja.vSaldoPedido = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar) - montoTotal);
      VariablesCaja.vValVueltoPedido = "0.00";
    } else{
      System.out.println("Cubierto");
      VariablesCaja.vIndTotalPedidoCubierto = true;
      VariablesCaja.vSaldoPedido = "0.00";
      VariablesCaja.vValVueltoPedido = FarmaUtility.formatNumber(montoTotal - FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar));
    }
    System.out.println("VariablesCaja.vSaldoPedido :"+VariablesCaja.vSaldoPedido);
    System.out.println("VariablesCaja.vValVueltoPedido :"+VariablesCaja.vValVueltoPedido);
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
    System.out.println("************************LimpiaVariablesFormaPago***********************");
  }

  private void cobrarPedido()
  {
        DlgProcesarCobro dlgProcesarCobro = 
            new DlgProcesarCobro(myParentFrame, "", true, tblFormasPago, 
                                 lblVuelto, tblDetallePago, txtNroPedido);
        dlgProcesarCobro.setVisible(true);
        //  07.01.09
        if (!FarmaVariables.vAceptar) {
            if (VariablesCaja.vCierreDiaAnul) {
                anularAcumuladoCanje();
                VariablesCaja.vCierreDiaAnul = false;
            }
        }
        /* 06.03.2008 ERIOS Cierra la conexion si se utilizo credito */
        if (VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) {
            FarmaConnection.closeConnection();
            FarmaConnection.anularConnection();
        }
   }


  /**
   *   08.01.09
   * Se movio parte del codigo
   * */
  private void anularAcumuladoCanje(){
      try{
          //   19.12.2008
          String pIndLineaMatriz = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
          log.debug("pIndLineaMatriz "+pIndLineaMatriz);
          //System.out.println("pIndLineaMatriz "+pIndLineaMatriz);
          System.out.println("VariablesVentas.vNum_Ped_Vta "+VariablesVentas.vNum_Ped_Vta);
          boolean pRspCampanaAcumulad = UtilityCaja.realizaAccionCampanaAcumulada
                                 (
                                  pIndLineaMatriz,
                                  VariablesVentas.vNum_Ped_Vta,this,//VariablesCaja.vNumPedVta,this,
                                  ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                  tblFormasPago,
                                  FarmaConstants.INDICADOR_S//Aqui si liberara stock al regalo
                                  );
          
          System.out.println("pRspCampanaAcumulad "+pRspCampanaAcumulad);
          if (!pRspCampanaAcumulad)
            {
              System.out.println("Se recupero historico y canje  XXX");
              FarmaUtility.liberarTransaccion();
              FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                      FarmaConstants.INDICADOR_S);
            }

          FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_S);
          FarmaUtility.aceptarTransaccion();
          log.info("Pedido anulado sin quitar respaldo.");
          //  05.07.2010
          cerrarVentana(false);
      } catch(Exception sql){
           FarmaUtility.liberarTransaccion();
		   FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                      FarmaConstants.INDICADOR_S);           
           //sql.printStackTrace();
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
      //sqlException.printStackTrace();
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
      System.out.println("VariablesCliente.vArrayList_Valores_Tarjeta.size() : " + VariablesCliente.vArrayList_Valores_Tarjeta.size());
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
      //ex.printStackTrace();
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
      //ex.printStackTrace();
      log.error(null,ex);
      FarmaUtility.showMessage(this,"Error al obtener monto de forma de pago con cupón.\n" + ex.getMessage(), tblFormasPago);
      monto = 0.00;
    }
    return monto;
  }

  private void colocaFormaPagoPedidoConvenio(String pNumPedido)
  {
    try
    {
      /*DBCaja.cargaFormaPagoPedidoConvenio(tableModelDetallePago.data, pNumPedido);
      System.out.println("Data: "+tableModelDetallePago.data);
      System.out.println("Sizee: "+tableModelDetallePago.data.size());*/
    
      /*if(tableModelDetallePago.data.size()>0)
      tableModelDetallePago.fireTableDataChanged();*/
      /**
       * Modificado para que no coloque el detalle de Todo el Credito sino q pueda modificar para el uso q dara
       * @author  
       * @since  08.09.2007
       */
      DBCaja.cargaFormaPagoPedidoConvenio(VariablesCaja.arrayDetFPCredito, pNumPedido);
      System.out.println("Data: "+VariablesCaja.arrayDetFPCredito);
      System.out.println("Sizee: "+VariablesCaja.arrayDetFPCredito.size());      
      if(VariablesCaja.arrayDetFPCredito.size()>0)
      tableModelDetallePago.fireTableDataChanged();
      
      
    } catch(SQLException ex)
    {
      //ex.printStackTrace();
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
      //ex.printStackTrace();
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
       //14.11.2007   modificado
       //lblMsjPedVirtual.setText("El pedido es una Recarga Virtual. Si lo cobra, Sólo podrá anularse dentro de 10 minutos.");
       lblMsjPedVirtual.setText("Recarga Virtual.Sólo podrá anularse dentro de "+ time_max(pNumPedido) +" minutos." +
                                " Telefono: " );
       lblMsjNumRecarga.setText(""+ num_telefono(pNumPedido));
      }
      else{
        lblMsjPedVirtual.setText("");
        lblMsjNumRecarga.setText("");
      }
      lblMsjPedVirtual.setVisible(true);
      lblMsjNumRecarga.setVisible(true);

      VariablesCaja.vIndPedidoConProdVirtual = true;
    }
    System.out.println("VariablesCaja.vIndPedidoConProdVirtual : " + VariablesCaja.vIndPedidoConProdVirtual);
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
      //ex.printStackTrace();
      log.error(null,ex);
      tipoProd = "";
      FarmaUtility.showMessage(this,"Error al obtener cantidad de productos virtuales.\n" + ex.getMessage(), tblFormasPago);
    }
    return tipoProd;
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

  private void colocaFormaPagoDeliveryArray(String pNumPedido)
  {
    try
    { VariablesCaja.arrayPedidoDelivery = new ArrayList();
      DBCaja.colocaFormaPagoDeliveryArray(VariablesCaja.arrayPedidoDelivery, pNumPedido);
    } catch(SQLException ex)
    {
      //ex.printStackTrace();
      log.error(null,ex);
      FarmaUtility.showMessage(this,"Error al obtener forma de pago delivery automatico.\n" + ex.getMessage(), tblFormasPago);
    }
  }
  /** Obtiene el Codigo de Forma del Convenio
   * @author :  
   * @since  : 26.07.2007
   */
  private String obtieneCodFormaConvenio(String pConvenio)
  { 
    String codForma = "";
    try
    {
      codForma = DBCaja.cargaFormaPagoConvenio(pConvenio);
      System.out.println("CodFormaConve ***"+codForma);
    } catch(SQLException ex)
    {
      //ex.printStackTrace();
      log.error(null,ex);
      FarmaUtility.showMessage(this,"Error al obtener el Codigo de la forma de pago del Convenio.\n" + ex.getMessage(), tblFormasPago);
    }
    return codForma.trim();
  }
  /**
   * Valida si uso el Credito
   * @author :  
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
       System.out.println("VAriables de formaPago >>>" +aux);
       System.out.println("Comparando >>"+((String)aux.get(0)).trim()+"xxxx"+codFormaPago.trim());
       if(((String)aux.get(0)).trim().equalsIgnoreCase(codFormaPago.trim()))
        return i;
     }
     return -1;
     }
   }
  /**
   * Obtiene el Codigo de la Forma de Pago del Convenio
   *  @author :  
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
      //sql.printStackTrace();
      log.error(null,sql);
      FarmaUtility.showMessage(this,"Error en Obntener si da Credito el  Convenio.",null);
      FarmaUtility.moveFocusJTable(tblFormasPago);
    }
    
    return indCredito;
   }


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
    * @author  
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
        //e.printStackTrace();
      log.error(null,e);
        FarmaUtility.showMessage(this,"Ocurrio un error al obtener tiempo maximo de anulacion de Producto Recarga Virtual.\n" + e.getMessage(),null);
      }
     return valor; 
  }
  /**
   * Retorna el numerom de telefono de recarga
   * @author  
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
        //e.printStackTrace();
      log.error(null,e);
        FarmaUtility.showMessage(this,"Ocurrio un error al obtener el numero de telefono de recarga.\n" + e.getMessage(),null);
      }
     return num_telefono;     
  }
  
  /**
  * Se obtiene la informacion de campaña por pedido
  * @author  
  * @since 03/07/08
  * */
  private void obtieneFormaPagoCampaña(ArrayList array,String vtaNumPed){
  
   String result="";
   array.clear();
      try
        {
         DBCaja.getFormaPagoCampaña(array,vtaNumPed);
        }catch(SQLException e)
        {
          //e.printStackTrace();
        log.error(null,e);
          FarmaUtility.showMessage(this,"Ocurrio un error al validar la forma de pago del pedido.\n" + e.getMessage(),null);
        }
  }
  
  /**
   * Se valida los montos por productos que esten en una campaña para llevarse los cupones ganados
   * @author  
   * @since 03/07/08
   * */
  private boolean validarFormasPagoCupones(String numPedVta){
      
      boolean valor=true;
      String codSel,codobtenido="";
      String monto1,monto2,descrip="",desccamp="",indAcep="",codCamp="",numPed="";
      ArrayList array=new ArrayList();
      int maxform=0,cant=0;
      
      if(tblDetallePago.getRowCount()>0 && Double.parseDouble(lblSaldo.getText().trim())==0){
      
        obtieneFormaPagoCampaña(array,VariablesCaja.vNumPedVta);
        System.out.println("array::: "+array);
        if(array.size()>0){   
          for (int j = 0; j < array.size(); j++){
            numPed=((String) ((ArrayList) array.get(j)).get(0)).trim();
            codobtenido=((String) ((ArrayList) array.get(j)).get(1)).trim();
            monto1=((String) ((ArrayList) array.get(j)).get(2)).trim();
            desccamp=((String) ((ArrayList) array.get(j)).get(3)).trim();
            indAcep=((String) ((ArrayList) array.get(j)).get(4)).trim();
            codCamp=((String) ((ArrayList) array.get(j)).get(5)).trim();
            
            for (int i = 0; i < tblDetallePago.getRowCount(); i++){
              maxform=tblDetallePago.getRowCount();//ultima forma de pago restar vuelto
              codSel=((String) tblDetallePago.getValueAt(i,0)).trim();
              descrip=((String) tblDetallePago.getValueAt(i,1)).trim();
              
              if(tblDetallePago.getRowCount()>0){
              
                if(codSel.trim().equalsIgnoreCase(codobtenido.trim())){
                  monto2=((String) tblDetallePago.getValueAt(i,5)).trim();
                  
                  System.out.println("monto pagado :"+Double.parseDouble(monto2));
                  System.out.println("monto exacto :"+Double.parseDouble(monto1));
                  
                     if(maxform==i+1){
                       System.out.println("leyendo ultima forma de pago");
                       monto2=FarmaUtility.formatNumber(Double.parseDouble(monto2)-Double.parseDouble(lblVuelto.getText().trim()));
                       System.out.println("supuesto pago sin vuelto: "+monto2);
                     }
                   
                    cant=cant+1;
                    if(cant==1)
                    actualizaPedidoCupon(codCamp,numPed,"S","N");
                    else if(cant>1)
                    actualizaPedidoCupon(codCamp,numPed,"N","N");
                    
                }else{
                 System.out.println("forma pago diferente");
                 valor=true;
                }
              }
            }
          }
        }
        procesaCampSinFormaPago(VariablesCaja.vNumPedVta);
      }else{
       valor=true;
      }
  return valor;
  }
  
  /**
   * Se actualiza el estado del pedido cupon para emision
   * @author  
   * @since 03.07.2008
   * */
  private void actualizaPedidoCupon(String codCamp,String vtaNumPed,String estado,String indtodos){
  
    try
        {
         DBCaja.actualizaIndImpre(codCamp,vtaNumPed,estado,indtodos);
         FarmaUtility.aceptarTransaccion();
        }catch(SQLException e)
        {
        FarmaUtility.liberarTransaccion(); // , 13.07.2010 - faltaba poner
          //e.printStackTrace();
      log.error(null,e);
          FarmaUtility.showMessage(this,"Ocurrio un error al validar la forma de pago del pedido.\n" + e.getMessage(),null);
        }  
  }
  
  /**
   * Se valida que el pedido tenga productos de campaña
   * @author  
   * @since  03.07.2008
   */
  private String verificaPedidoCamp(String numPed)
  {
    String resp = "";
    try
      {
        resp = DBCaja.verificaPedidoCamp(numPed);
      }catch(SQLException e)
      {
        e.printStackTrace();
        FarmaUtility.showMessage(this,"Ocurrio un error al validar pedido por campaña.\n" + e.getMessage(),null);
      }
     return resp;     
  }
  
  /**
  * Se carga detalle forma pago campaña del pedido
  * @author  
  * @since 07/07/08
  * */
  private void cargaDetalleFormaPago(String NumPed){
   ArrayList array = new ArrayList();
   ArrayList myArray = new ArrayList();
   obtieneDetalleFormaPagoPedido(array,NumPed);
   System.out.println("detalle forma pago :"+array);
    if(array.size()>0){
    System.out.println("array :"+array);
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
       System.out.println("ROW 1 :"+myArray);
       tableModelDetallePago.data.add(myArray);
       tableModelDetallePago.fireTableDataChanged();
     }
    }
   verificaMontoPagadoPedido();
  }
  
  /**
  * Se detalle forma pago campaña del pedido
  * @author  
  * @since 07/07/08
  * */
  private void obtieneDetalleFormaPagoPedido(ArrayList array,String vtaNumPed){
  
   array.clear();
      try
        {
         DBCaja.getDetalleFormaPagoCampaña(array,vtaNumPed);
        }catch(SQLException e)
        {
          //e.printStackTrace();
        log.error(null,e);
          FarmaUtility.showMessage(this,"Ocurrio un error al obtener detalle forma pago del pedido.\n" + e.getMessage(),null);
        }
  }
  
   /**
   * Se valida la impresion de las campañas que no tengan forma de de pago relacionadas
   * @author  
   * @since  10.07.08
   * */
  private void procesaCampSinFormaPago(String vtaNumPed){
     try
        {
         DBCaja.procesaCampSinFormaPago(vtaNumPed);
         //se comento para evitar problemas de bloqueos anteriores.
         //  13.10.2011
         //FarmaUtility.aceptarTransaccion();
        }catch(SQLException e)
        {
          //e.printStackTrace();
       log.error(null,e);
          FarmaUtility.showMessage(this,"Ocurrio un error al procesar campañas sin forma de pago.\n" + e.getMessage(),null);
        }
  }

  
  private void procesar(){
         
       
      
            log.debug("*Cobro de Pedido Normal");
            //  02.07.2008 la generacion de cupones no aplica convenios
            VariablesCaja.vPermiteCampaña = 
                    verificaPedidoCamp(VariablesCaja.vNumPedVta);
            if (VariablesCaja.vPermiteCampaña.trim().equalsIgnoreCase("S") && 
                tblDetallePago.getRowCount() > 0) {
                if (validarFormasPagoCupones(VariablesCaja.vNumPedVta)) {
                    /* Se valida las formas de pago de las campañas 
                     * de tipo Monto Descuento.
                     * Se verificara si puede permitir cobrar
                     */
                    cobrarPedido(); //procesar cobro de pedido
                }
            } else {
                cobrarPedido(); //procesar cobro de pedido
            }
            pedidoCobrado();
        
        //Si la variable indica que de escape y recalcule to do el ahorro del cliente
        if(VariablesFidelizacion.vRecalculaAhorroPedido){
            eventoEscape();
        }
      VariablesVentas.vProductoVirtual=false; // , 28.04.2010
  }
  

  
  private void pedidoCobrado(){
      System.err.println("VariablesCaja.vIndPedidoCobrado:"+VariablesCaja.vIndPedidoCobrado);
	if(VariablesCaja.vIndPedidoCobrado){
		log.info("pedido cobrado !");
	    if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) && 
	    		indCerrarPantallaCobrarPed ) {
                //  03.11.09 Se valida ingreso de sobre
                 System.out.println("VariablesCaja.vSecMovCaja-->"+VariablesCaja.vSecMovCaja);
                    if(validaIngresoSobre()){
                        //  20.07.2010
                        //if(FarmaUtility.rptaConfirmDialog(this, "Existe efectivo suficiente. Desea ingresar sobres en su turno?")){
                        if(FarmaUtility.rptaConfirmDialog(this, "Ha excedido el importe máximo de dinero en su caja. \n" + 
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
	    System.out.println("-********************LIMPIANDO VARIABLES***********************-");
	}
	
  }
  
  private void eventoEscape(){
      System.out.println("VariablesCaja.vNumPedVta: "+VariablesCaja.vNumPedVta);
      System.out.println("VariablesCaja.vPermiteCampaña: "+VariablesCaja.vPermiteCampaña);
      System.out.println("indCerrarPantallaAnularPed: "+indCerrarPantallaAnularPed);
      System.out.println("VariablesCaja.vIndPedidoSeleccionado: "+VariablesCaja.vIndPedidoSeleccionado);
      System.out.println("VariablesCaja.vIndDeliveryAutomatico: "+VariablesCaja.vIndDeliveryAutomatico);
      //  02.07.2008 se deja el indicador de impresio de cupon por pedido en N
      if(!VariablesCaja.vNumPedVta.equalsIgnoreCase("")){
      VariablesCaja.vPermiteCampaña=verificaPedidoCamp(VariablesCaja.vNumPedVta);
        if(VariablesCaja.vPermiteCampaña.trim().equalsIgnoreCase("S")){
          actualizaPedidoCupon("",VariablesCaja.vNumPedVta,"N","S");
        }
      }
      
      indBorra=false;// 
      
      if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) )
      {
        if(indCerrarPantallaAnularPed && VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
            
           //Se anulara el pedido 
            if(VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                if(FarmaUtility.rptaConfirmDialog(this, "El Pedido sera Anulado. Desea Continuar?")){
                            try{
                              //DBCaja.anularPedidoPendiente(VariablesCaja.vNumPedVta); //antes  , 13.07.2010
                              DBCaja.anularPedidoPendiente_02(VariablesCaja.vNumPedVta); // , 13.07.2010
                              FarmaUtility.aceptarTransaccion();
                              log.info("Pedido anulado.");
                              FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
                              cerrarVentana(true);
                            } catch(SQLException sql)
                            {
                              FarmaUtility.liberarTransaccion();
                              //sql.printStackTrace();
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
           //if(FarmaUtility.rptaConfirmDialog(this, "El Pedido sera Anulado. Desea Continuar?")){
            try{
            
                //  13.08.09  
              UtilityCaja.liberaProdRegalo(VariablesCaja.vNumPedVta,
                                                    ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                                        FarmaConstants.INDICADOR_S);
                
              //DBCaja.anularPedidoPendienteSinRespaldo(VariablesCaja.vNumPedVta); antes
              DBCaja.anularPedidoPendienteSinRespaldo_02(VariablesCaja.vNumPedVta); // , 13.07.2010
                
                ///-- inicio de validacion de Campaña 
                //   19.12.2008
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
              //sql.printStackTrace();
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
   * @AUTHOR  
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
   * @AUTHOR  
   * @SINCE 03.11.09
   * */
  private boolean validaIngresoSobre() {
    boolean valor=false;
    String ind="";
    try
         {
         System.out.println("VariablesCaja.vSecMovCaja-->"+VariablesCaja.vSecMovCaja);
          ind=DBCaja.permiteIngreSobre(VariablesCaja.vSecMovCaja);
             System.out.println("indPermiteSobre-->"+ind);
          if(ind.trim().equalsIgnoreCase("S")){
           valor=true;
           }
          
         }catch (SQLException sql){
           valor=false;
           sql.printStackTrace();
           FarmaUtility.showMessage(this,"Ocurrio un error validar ingreso de sobre.\n"+sql.getMessage(),null);
         }
    return valor;
  }
  
    /**
     *   22.06.2011
     * @param pNumPedido
     * @return
     */
    public boolean existeStockPedido(String pNumPedido) 
    {
        //VariablesCaja.vNumPedVta
        boolean pRes = false;
        String pCadena = "N";

        try {
            pCadena = DBCaja.getPermiteCobrarPedido(pNumPedido);
            System.out.println("XXXX");
        } catch (SQLException e) {
            pCadena = "N";
            System.out.println("yyy");
            e.printStackTrace();
        }
        
        if(pCadena.trim().equalsIgnoreCase("S")) pRes = true;
        
        return pRes;
    }

}
