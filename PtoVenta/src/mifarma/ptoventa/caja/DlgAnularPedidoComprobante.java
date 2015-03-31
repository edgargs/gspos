package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mifarma.common.DlgLogin;
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
import mifarma.ptoventa.recetario.reference.DBRecetario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgAnularPedidoComprobante extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgAnularPedidoComprobante.class);

    FarmaTableModel tableModelCabeceraPedido;
    FarmaTableModel tableModelDetallePedido;
    Frame myParentFrame;
    private boolean vAceptar = false;
    private String numeroPedido = "";
    private String numeroComp = "";
    private String tipo = "";
    ButtonGroup grupo = new ButtonGroup();

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTable tblDetallePedido = new JTable();
    private JPanel jPanel4 = new JPanel();
    private JButton btnDetalle = new JButton();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel jPanel3 = new JPanel();
    private JButton btnCabecera = new JButton();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JTextField txtMonto = new JTextField();
    private JLabel jLabel4 = new JLabel();
    private JButton btnBuscar = new JButton();
    private JTextField txtNroComprobante = new JTextField();
    private JButton btnNroComprobante = new JButton();
    private JPanel jPanel1 = new JPanel();
    private JRadioButton rbtFactura = new JRadioButton();
    private JRadioButton rbtBoleta = new JRadioButton();
    private JTable tblCabeceraPedido = new JTable();
    private JComboBox cmbSerie = new JComboBox();

    private String eRCM = "";
    private boolean estrcm = false;
    private boolean estvta = false;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgAnularPedidoComprobante() {
        this(null, "", false);
    }

    public DlgAnularPedidoComprobante(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(798, 385));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Anulacion de Comprobantes");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(516, 354));
        jContentPane.setBackground(Color.white);
        jScrollPane2.setBounds(new Rectangle(15, 210, 770, 105));
        jScrollPane2.setBackground(new Color(255, 130, 14));
        jPanel4.setBounds(new Rectangle(15, 190, 770, 20));
        jPanel4.setBackground(new Color(255, 130, 14));
        jPanel4.setLayout(null);
        btnDetalle.setText("Detalle Pedido");
        btnDetalle.setBounds(new Rectangle(15, 0, 115, 20));
        btnDetalle.setMnemonic('d');
        btnDetalle.setBackground(new Color(255, 130, 14));
        btnDetalle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDetalle.setBorderPainted(false);
        btnDetalle.setContentAreaFilled(false);
        btnDetalle.setDefaultCapable(false);
        btnDetalle.setFocusPainted(false);
        btnDetalle.setForeground(Color.white);
        btnDetalle.setFont(new Font("SansSerif", 1, 11));
        btnDetalle.setHorizontalAlignment(SwingConstants.LEFT);
        btnDetalle.setRequestFocusEnabled(false);
        btnDetalle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDetalle_actionPerformed(e);
            }
        });
        btnDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnDetalle_keyPressed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(15, 120, 770, 65));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        jPanel3.setBounds(new Rectangle(15, 100, 770, 20));
        jPanel3.setBackground(new Color(255, 130, 14));
        jPanel3.setLayout(null);
        btnCabecera.setText("Cabecera Pedido");
        btnCabecera.setBounds(new Rectangle(15, 0, 115, 20));
        btnCabecera.setMnemonic('c');
        btnCabecera.setBackground(new Color(255, 130, 14));
        btnCabecera.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnCabecera.setBorderPainted(false);
        btnCabecera.setContentAreaFilled(false);
        btnCabecera.setDefaultCapable(false);
        btnCabecera.setFocusPainted(false);
        btnCabecera.setForeground(Color.white);
        btnCabecera.setFont(new Font("SansSerif", 1, 11));
        btnCabecera.setHorizontalAlignment(SwingConstants.LEFT);
        btnCabecera.setRequestFocusEnabled(false);
        btnCabecera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnCabecera_keyPressed(e);
            }
        });
        btnCabecera.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCabecera_actionPerformed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(585, 325, 85, 20));
        lblF11.setText("[ F11 ] Anular");
        lblF11.setBounds(new Rectangle(485, 325, 85, 20));
        pnlHeader.setBounds(new Rectangle(15, 55, 770, 35));
        txtMonto.setBounds(new Rectangle(355, 5, 80, 25));
        txtMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMonto_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtMonto_keyTyped(e);
            }
        });
        jLabel4.setText("Monto :");
        jLabel4.setBounds(new Rectangle(300, 5, 55, 25));
        jLabel4.setFont(new Font("SansSerif", 1, 11));
        jLabel4.setForeground(Color.white);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(445, 5, 90, 25));
        btnBuscar.setMnemonic('s');
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnBuscar_keyPressed(e);
            }
        });
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtNroComprobante.setBounds(new Rectangle(215, 5, 70, 25));
        txtNroComprobante.setFont(new Font("SansSerif", 0, 12));
        txtNroComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNroComprobante_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtNroComprobante_keyTyped(e);
            }
        });
        btnNroComprobante.setText("Nro. Comprobante :");
        btnNroComprobante.setBounds(new Rectangle(10, 10, 115, 15));
        btnNroComprobante.setMnemonic('n');
        btnNroComprobante.setBackground(new Color(255, 130, 14));
        btnNroComprobante.setBorderPainted(false);
        btnNroComprobante.setContentAreaFilled(false);
        btnNroComprobante.setDefaultCapable(false);
        btnNroComprobante.setFocusPainted(false);
        btnNroComprobante.setForeground(Color.white);
        btnNroComprobante.setHorizontalAlignment(SwingConstants.LEFT);
        btnNroComprobante.setFont(new Font("SansSerif", 1, 11));
        btnNroComprobante.setRequestFocusEnabled(false);
        btnNroComprobante.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnNroComprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNroComprobante_actionPerformed(e);
            }
        });
        jPanel1.setBounds(new Rectangle(15, 15, 770, 35));
        jPanel1.setBorder(BorderFactory.createTitledBorder(""));
        jPanel1.setBackground(Color.white);
        jPanel1.setLayout(null);
        rbtFactura.setText("FACTURA");
        rbtFactura.setBounds(new Rectangle(340, 5, 115, 25));
        rbtFactura.setBackground(Color.white);
        rbtFactura.setFont(new Font("SansSerif", 1, 14));
        rbtFactura.setForeground(new Color(43, 141, 39));
        rbtFactura.setFocusPainted(false);
        rbtFactura.setRequestFocusEnabled(false);
        rbtFactura.setMnemonic('f');
        rbtFactura.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                rbtFactura_stateChanged(e);
            }
        });
        rbtFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                rbtBoleta_keyPressed(e);
            }
        });
        rbtBoleta.setText("BOLETA");
        rbtBoleta.setBounds(new Rectangle(210, 5, 95, 25));
        rbtBoleta.setBackground(Color.white);
        rbtBoleta.setFont(new Font("SansSerif", 1, 14));
        rbtBoleta.setForeground(new Color(43, 141, 39));
        rbtBoleta.setFocusPainted(false);
        rbtBoleta.setRequestFocusEnabled(false);
        rbtBoleta.setMnemonic('b');
        rbtBoleta.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                rbtBoleta_stateChanged(e);
            }
        });
        rbtBoleta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                rbtBoleta_keyPressed(e);
            }
        });
        cmbSerie.setBounds(new Rectangle(125, 5, 85, 25));
        cmbSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbSerie_keyPressed(e);
            }
        });
        grupo.add(rbtBoleta);
        grupo.add(rbtFactura);
        jScrollPane2.getViewport();
        jPanel4.add(btnDetalle, null);
        jScrollPane1.getViewport();
        jPanel3.add(btnCabecera, null);
        pnlHeader.add(cmbSerie, null);
        pnlHeader.add(txtMonto, null);
        pnlHeader.add(jLabel4, null);
        pnlHeader.add(btnBuscar, null);
        pnlHeader.add(txtNroComprobante, null);
        pnlHeader.add(btnNroComprobante, null);
        jPanel1.add(rbtFactura, null);
        jPanel1.add(rbtBoleta, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(tblCabeceraPedido, null);
        jScrollPane2.getViewport().add(tblDetallePedido, null);
        jContentPane.add(jScrollPane2, null);
        jContentPane.add(jPanel4, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(jPanel3, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(pnlHeader, null);
        jContentPane.add(jPanel1, null);
        //
        txtNroComprobante.setDocument(new FarmaLengthText(7));
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        VariablesCaja.vIndAnulacionConReclamoNavsat = false;
        initTableCabeceraPedido();
        initTableDetallePedido();
        rbtBoleta.doClick();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableCabeceraPedido() {
        tableModelCabeceraPedido =
                new FarmaTableModel(ConstantsCaja.columnsListaPedidos, ConstantsCaja.defaultValuesListaPedidos, 0);
        FarmaUtility.initSimpleList(tblCabeceraPedido, tableModelCabeceraPedido, ConstantsCaja.columnsListaPedidos);
    }

    private void cargaListaPedidos() {
        try {
            DBCaja.getListaCabeceraPedido(tableModelCabeceraPedido, numeroPedido);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar los pedidos  - \n" +
                    e.getMessage(), txtNroComprobante);
        }
    }

    private void initTableDetallePedido() {
        tableModelDetallePedido =
                new FarmaTableModel(ConstantsCaja.columnsListaProductos, ConstantsCaja.defaultValuesListaProductos, 0);
        FarmaUtility.initSimpleList(tblDetallePedido, tableModelDetallePedido, ConstantsCaja.columnsListaProductos);
    }

    private void cargaListaProductos() {
        try {
            String tipo;
            if (rbtBoleta.isSelected())
                tipo = ConstantsVentas.TIPO_COMP_BOLETA;
            else
                tipo = ConstantsVentas.TIPO_COMP_FACTURA;
            DBCaja.getListaDetallePedido(tableModelDetallePedido, numeroPedido, tipo, numeroComp);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar los productos  - \n" +
                    e.getMessage(), txtNroComprobante);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void rbtBoleta_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(cmbSerie);
    }

    private void btnNroComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbSerie);
    }

    private void txtNroComprobante_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtNroComprobante.setText(FarmaUtility.caracterIzquierda(txtNroComprobante.getText(), 7, "0"));
            FarmaUtility.moveFocus(txtMonto);
        } else
            chkKeyPressed(e);
    }

    private void txtNroComprobante_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNroComprobante, e);
    }

    private void txtMonto_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        } else
            chkKeyPressed(e);
    }

    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (validarCampos()) {
            if (buscarComprobante()) {
                cargaListaPedidos();
                cargaListaProductos();
            } else {
                tableModelCabeceraPedido.clearTable();
                tableModelDetallePedido.clearTable();
                tblCabeceraPedido.removeAll();
                tblDetallePedido.removeAll();
            }
        }
    }

    private void btnBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNroComprobante);
        } else
            chkKeyPressed(e);
    }

    private void btnCabecera_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnCabecera);
    }

    private void btnCabecera_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblCabeceraPedido, null, 0);

        chkKeyPressed(e);
    }

    private void btnDetalle_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnDetalle);
    }

    private void btnDetalle_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblDetallePedido, null, 0);
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNroComprobante);
        cargaLogin();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void rbtBoleta_stateChanged(ChangeEvent e) {
        if (rbtBoleta.isSelected()) {
            cargaCombo(ConstantsVentas.TIPO_COMP_BOLETA);
        }
    }

    private void rbtFactura_stateChanged(ChangeEvent e) {
        if (rbtFactura.isSelected()) {
            cargaCombo(ConstantsVentas.TIPO_COMP_FACTURA);
        }
    }

    private void cmbSerie_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNroComprobante);
        } else
            chkKeyPressed(e);
    }
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (vAceptar) {
                if (tblCabeceraPedido.getRowCount() > 0) {
                    log.debug("entro a anularComprobante");
                    ArrayList vLista = tableModelCabeceraPedido.data;
                    //String indiCadorDelivery = tblCabeceraPedido.getValueAt(0,9).toString();
                    String indiCadorDelivery = //tblCabeceraPedido.getValueAt(0,9).toString();
                        FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data, 0, 9).trim();
                    //JCORTEZ 18.12.08
                    //VariablesCaja.vIndPedFidelizado= FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,10);
                    VariablesCaja.vIndPedFidelizado = FarmaUtility.getValueFieldArrayList(vLista, 0, 10);

                    //VariablesCaja.vDniCli=FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,11);
                    VariablesCaja.vDniCli = FarmaUtility.getValueFieldArrayList(vLista, 0, 11);
                    log.debug("Pedido Fidelizado: " + VariablesCaja.vIndPedFidelizado);
                    log.debug("Dni Cliente: " + VariablesCaja.vDniCli);
                    log.debug("entro a anularComprobante");
                    log.debug("Es de delivery " + indiCadorDelivery);
                    if (VariablesCaja.vIndPedFidelizado.equalsIgnoreCase("S")) //JCORTEZ Valida pedido Fidelizado 18.12.2008
                        FarmaUtility.showMessage(this,
                                                 "No puede Anular un Pedido Fidelizado por comprobante, solo por pedido completo.",
                                                 txtNroComprobante);
                    else {
                        if (indiCadorDelivery.equalsIgnoreCase("N"))
                            anularComprobante();
                        else {
                            FarmaUtility.showMessage(this,
                                                     "No puede Anular un Pedido generado por Delivery Automatico.",
                                                     txtNroComprobante);
                            tblCabeceraPedido.removeAll();
                            tableModelCabeceraPedido.clearTable();
                            tblDetallePedido.removeAll();
                            tableModelDetallePedido.clearTable();
                            txtMonto.setText("");
                            txtNroComprobante.setText("");
                            VariablesCaja.vIndAnulacionConReclamoNavsat = false;
                        }
                    }
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
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

    private void cargaLogin() {
        DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.dlgLogin = dlgLogin;
            FarmaVariables.vAceptar = false;
        } else
            cerrarVentana(false);
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (!rbtBoleta.isSelected() && !rbtFactura.isSelected()) {
            FarmaUtility.showMessage(this, "Debe seleccionar un tipo de Documento.", txtNroComprobante);
            retorno = false;
        } else if (cmbSerie.getSelectedIndex() < 0) {
            FarmaUtility.showMessage(this, "Debe indicar la serie del documento.", cmbSerie);
            retorno = false;
        } else if (txtNroComprobante.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Correlativo.", txtNroComprobante);
            retorno = false;
        } else if (txtMonto.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Monto.", txtMonto);
            retorno = false;
        } else if (!FarmaUtility.validateDecimal(this, txtMonto, "Debe ingresar un Monto Válido.", false)) {
            retorno = false;
        }
        return retorno;
    }


    //RUDY LLANTOY 23.05.13 Obtiene el estado del comprobante a partir de un pedido normal o RCM

    private boolean buscarComprobante() {
        boolean retorno = false;
        vAceptar = false;

        String vNumCompPago = "";
        char estado = ' ';
        vNumCompPago =
                FarmaLoadCVL.getCVLCode("cmbSerie", cmbSerie.getSelectedIndex()) + txtNroComprobante.getText().trim();
        //txtSerie.getText().trim()+txtNroComprobante.getText().trim();

        if (VariablesPtoVenta.vIndVerReceMagis.equals(FarmaConstants.INDICADOR_S)) {
            if (validarRCM(vNumCompPago)) {
                estado = eRCM.charAt(0);
                //'ESTADO [A=Pendiente|C=Cobrado|E=Enviado|N=Anulado|G=Guia]';
                //switch(estado)
                //{    case 'A': case'P': case 'N':
                if (estado == 'A' || estado == 'C' || estado == 'E') {
                    FarmaUtility.showMessage(this, " PEDIDO PREPARADO MAGISTRAL ", cmbSerie);
                    VariablesVentas.vestRCM = true;
                    retorno = true;
                    vAceptar = true;
                    buscarComprobanteP();
                } else { //case 'C': case'E': case'G':
                    FarmaUtility.showMessage(this, " PEDIDO PREPARADO MAGISTRAL " + "\n" +
                            " El pedido no se puede anular, el preparado magistral está en proceso de fabricación.",
                            cmbSerie);
                    limpiarCampos();
                }
            }
        } else {
            retorno = buscarComprobanteP();
        }
        return retorno;
    }

    private boolean buscarComprobanteP() { //VariablesCaja.vIndAnulacionConReclamoNavsat = false;
        boolean retorno = false;
        vAceptar = false;
        numeroPedido = "";
        numeroComp =
                FarmaLoadCVL.getCVLCode("cmbSerie", cmbSerie.getSelectedIndex()) + txtNroComprobante.getText().trim();
        try {
            tipo = (rbtBoleta.isSelected()) ? ConstantsVentas.TIPO_COMP_BOLETA : ConstantsVentas.TIPO_COMP_FACTURA;
            numeroPedido =
                    DBCaja.verificaComprobante(tipo, numeroComp, txtMonto.getText().trim()); //si es pedido por convenio o pedido de prod virtual lanzara errorsql
            //log.debug("jcallo:numero de pedido del comprobante:"+numeroPedido);

            retorno = true;
            vAceptar = true;
        } catch (SQLException e) {
            log.error(e.getErrorCode() + ":" + e.getMessage());
            retorno = false;
            vAceptar = false;
            if (e.getErrorCode() == 20001) {
                FarmaUtility.showMessage(this, "No se encuentra ningún Pedido con estos datos. ¡Verifique!",
                                         txtNroComprobante);
            } else if (e.getErrorCode() == 20012) {
                FarmaUtility.showMessage(this,
                                         "Este es un Pedido Convenio. Debe anularse por Pedido, no por Comprobante.",
                                         txtNroComprobante);
            } else if (e.getErrorCode() == 20011) {
                FarmaUtility.showMessage(this,
                                         "No se encuentra ningún Comprobante o se encuentra anulado. ¡Verifique!",
                                         txtNroComprobante);
            } else if (e.getErrorCode() == 20002) {
                FarmaUtility.showMessage(this, "El Pedido no ha sido cobrado. ¡No puede Anular este Pedido!",
                                         txtNroComprobante);
            } else if (e.getErrorCode() == 20003) {
                FarmaUtility.showMessage(this, "El Pedido ya está anulado. ¡No puede Anular este Pedido!",
                                         txtNroComprobante);
            }

            /*else if(e.getErrorCode()==20004)
      {

        if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"El pedido no pueder ser anulado porque cuenta con un producto del tipo Tarjeta Virtual.\nEsta seguro de anular de todas formas?."))
        {
          if( cargaLoginAdmLocal() )
          {
            retorno = true;
            vAceptar = true;
            VariablesCaja.vIndAnulacionConReclamoNavsat = true;
            buscarComprobante();
          } else
          {
            FarmaUtility.showMessage(this,"No es posible realizar la operación.",txtNroComprobante);
          }
        }
      } */ else if (e.getErrorCode() == 20005) {
                /**
         * SE OBTIENE EL VALOR DE TIEMPO MAXIMO QUE PASO PARA ANULAR UNA RECARGA VIRTUAL
         * @AUTHOR DUBILLUZ
         * @SINCE  09.11.2007
         */
                VariablesCaja.vIndPedidoRecargaVirtual = FarmaConstants.INDICADOR_S;
                String num_ped = getNumeroPedido(numeroComp, txtMonto.getText().trim());
                VariablesCaja.vNumPedVta_Anul = num_ped;
                String tiempo_maximo = time_max(num_ped).trim();
                String indExitoRecarga = ""; //obtieneRespuestaRecarga().trim();
                if (indExitoRecarga.trim().equals("00")) {
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "El pedido cuenta con un producto del tipo Recarga Virtual\ny fue cobrado hace mas de " +
                                                                                    tiempo_maximo +
                                                                                    " minutos. Esta seguro de anular de todas formas?.")) //AGREGADO POR DVELIZ 15.12.2008
                    {
                        if (cargaLoginAdmLocal()) {
                            retorno = true;
                            vAceptar = true;
                            VariablesCaja.vIndAnulacionConReclamoNavsat = true;
                        } else {
                            FarmaUtility.showMessage(this, "No es posible realizar la operación.", txtNroComprobante);
                        }
                    }
                } else {
                    retorno = true;
                    vAceptar = true;
                    return retorno;
                }
                VariablesCaja.vIndPedidoRecargaVirtual = "";
            } else if (e.getErrorCode() == 20013) {
                FarmaUtility.showMessage(this,
                                         "El pedido generó cupones y ya fueron usados. No puede anular el comprobante.",
                                         txtNroComprobante);
            } else if (e.getErrorCode() == 20014) {
                FarmaUtility.showMessage(this, "Este comprobante ha generado cupones, debe anularse por pedido.",
                                         txtNroComprobante);
            } else if (e.getErrorCode() == 20099) {
                FarmaUtility.showMessage(this,
                                         "Este comprobante contiene producto virtual\nDebe anularse por pedido completo !.",
                                         txtNroComprobante);
            } else {
                log.error("", e);
                FarmaUtility.showMessage(this, "Ocurrio un error al buscar un comprobante  - \n" +
                        e.getMessage(), txtNroComprobante);
            }
        }
        return retorno;
    }


    //RUDY LLANTOY 30.05.13 Limpia el formulario y pone el foco en cmbTipoCom

    private void limpiarCampos() {
        txtNroComprobante.setText("");
        txtMonto.setText("");
        rbtBoleta.requestFocus(true);
    }


    //RUDY LLANTOY 30.05.13 Valida que sea un pedido de RCM

    private boolean validarRCM(String pNumCompPago) {
        boolean est = false;
        try {
            eRCM = DBRecetario.getEstadoRCM(pNumCompPago);
            getDatosRCM(eRCM);
            est = true;

        } catch (SQLException sql) {
            log.error("", sql);
            buscarComprobanteP();
        }
        return est;
    }

    //RUDY LLANTOY 23.05.13 Obtiene datos a partir de la llamada del Store

    private void getDatosRCM(String pDato) {

        String pSeparador = ";";
        pDato.trim();
        String[] arrayLetra = pDato.split(pSeparador);
        if (arrayLetra.length > 0 && arrayLetra.length == 1) {
            eRCM = arrayLetra[0].trim();
            log.debug("Estado Preparado Magistral: " + eRCM);

        }

    }


    private int getCuponesUsados(String pNumPed) {
        String vIndLineaBD = "";
        String vEstCuponMatriz = "";
        String vCodCupon = "";
        int vNumCuponesUsados = 0;
        ArrayList listCuponesUsados = new ArrayList();

        String vIndTieneCuponesUso = "";
        String vIndTieneCuponesEmitido = "";
        try {

            vIndTieneCuponesUso = DBCaja.getIndTieneCupones(pNumPed.trim(), ConstantsCaja.CUPONES_USADOS);

            vIndTieneCuponesEmitido = DBCaja.getIndTieneCupones(pNumPed.trim(), ConstantsCaja.CUPONES_EMITIDOS);
        } catch (SQLException e) {
            log.error("", e);
            vIndTieneCuponesUso = "S";
        }

        if (vIndTieneCuponesUso.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            try {

                vIndLineaBD =
                        FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);

                DBCaja.getcuponesPedido(pNumPed, vIndLineaBD, listCuponesUsados,
                                        ConstantsCaja.CONSULTA_CUPONES_USADOS);

                if (vIndLineaBD.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    for (int i = 0; i < listCuponesUsados.size(); i++) {
                        vCodCupon = FarmaUtility.getValueFieldArrayList(listCuponesUsados, i, 0);
                        if (!vCodCupon.equalsIgnoreCase("XX")) {
                            vEstCuponMatriz =
                                    DBCaja.getEstadoCuponEnMatriz(vCodCupon, FarmaConstants.INDICADOR_S).trim();
                            if (!vEstCuponMatriz.equalsIgnoreCase("A"))
                                vNumCuponesUsados += 1;
                        }

                    }
                } else
                    vNumCuponesUsados = 0;
            } catch (SQLException e) {
                log.error(null, e);
                vNumCuponesUsados = 0;
            }
        } else {
            log.debug("No va a Matriz");
        }


        return vNumCuponesUsados;

    }

    private void anularComprobante() {
        cargarVariables();

        int vCantCuponesEmitidosUsados = 0;
        /*
      try
      {
        vCantCuponesUsados = DBCaja.cuponesUsados(txtCorrelativo.getText().trim());
        if(vCantCuponesUsados > 0)
        {
          FarmaUtility.showMessage(this,"Los cupones que generó este pedido han sido usados."+
         " Por favor verifique si se debe anular dicho canje.",txtCorrelativo);
        }
      }
      catch (SQLException e)
      {
        log.error(null,e);
      }
      */

        //--Obtiene los cupones emitidos usados
        vCantCuponesEmitidosUsados =
                UtilityCaja.getCuponesEmitidosUsados(numeroPedido.trim(), this, txtNroComprobante);

        if (vCantCuponesEmitidosUsados > 0) {
            FarmaUtility.showMessage(this,
                                     "Los cupones que generó este pedido han sido usados." + " Por favor verifique si se debe anular dicho canje.",
                                     txtNroComprobante);
        }

        if (comprobarFecha()) //|| comprobarDocumento())
        {
            if (!comprobarDocumentoNotaCredito()) {
                FarmaUtility.showMessage(this, "No se puede anular un pedido de tipo Nota de Crédito. Verifique!!!",
                                         txtNroComprobante);
            } else {
                FarmaUtility.moveFocus(txtNroComprobante);
                DlgDetalleAnularPedido dlgDetalleAnularPedido = new DlgDetalleAnularPedido(myParentFrame, "", true);
                dlgDetalleAnularPedido.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    tblCabeceraPedido.removeAll();
                    tableModelCabeceraPedido.clearTable();
                    tblDetallePedido.removeAll();
                    tableModelDetallePedido.clearTable();
                    txtMonto.setText("");
                    txtNroComprobante.setText("");
                    VariablesCaja.vIndAnulacionConReclamoNavsat = false;
                }
            }
        } else {
            if (comprobarProductosRestantes()) {
                FarmaUtility.showMessage(this,
                                         "Este Pedido corresponde a un periodo anterior. \nNo se anulará dicho pedido, pero se generará una solicitud de Nota de Crédito.",
                                         txtNroComprobante);
                DlgNotaCreditoNueva dlgNotaCreditoNueva = new DlgNotaCreditoNueva(myParentFrame, "", true);
                dlgNotaCreditoNueva.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    VariablesCaja.vIndAnulacionConReclamoNavsat = false;
                    cerrarVentana(true);
                }
            }
        }
    }

    private void cargarVariables() {
        VariablesCaja.vNumPedVta_Anul = numeroPedido;
        VariablesCaja.vTipComp_Anul = tipo;
        VariablesCaja.vNumComp_Anul = numeroComp;
        VariablesCaja.vMonto_Anul = txtMonto.getText().trim();
        VariablesCaja.vTipComp = //= tblCabeceraPedido.getValueAt(0,7).toString();
                FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data, 0, 7);
        VariablesVentas.vTip_Ped_Vta = FarmaUtility.getValueFieldJTable(tblCabeceraPedido, 0, 8);
        VariablesCaja.vIndDeliveryAutomatico = FarmaUtility.getValueFieldJTable(tblCabeceraPedido, 0, 9);
        log.debug("tipo de pedido : " + VariablesVentas.vTip_Ped_Vta);
    }

    private boolean comprobarFecha() {
        boolean retorno = true;
        try {
            String sysdate = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String fecha = //= tblCabeceraPedido.getValueAt(0,1).toString().substring(0,10);
                FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data, 0, 1).substring(0, 10);
            log.debug(sysdate + "/" + fecha);

            Date date1 = FarmaUtility.getStringToDate(sysdate, "dd/MM/yyyy");
            GregorianCalendar calendario1 = new GregorianCalendar();
            //calendario1.setGregorianChange(date1);
            calendario1.setTime(date1);
            log.debug(calendario1.toString());
            Date date2 = FarmaUtility.getStringToDate(fecha, "dd/MM/yyyy");
            GregorianCalendar calendario2 = new GregorianCalendar();
            //calendario2.setGregorianChange(date2);
            calendario2.setTime(date2);
            log.debug(calendario2.toString());
            log.debug(calendario1.get(Calendar.MONTH) + "==" + calendario2.get(Calendar.MONTH));
            if (calendario1.get(Calendar.MONTH) == calendario2.get(Calendar.MONTH) &&
                calendario1.get(Calendar.YEAR) == calendario2.get(Calendar.YEAR))
                retorno = true;
            else
                retorno = false;

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al comprobar la fecha  - \n" +
                    e.getMessage(), txtNroComprobante);
        }
        return retorno;
    }

    private boolean comprobarDocumento() {
        boolean retorno = true;
        String documento = //tblCabeceraPedido.getValueAt(0,7).toString();
            FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data, 0, 7);
        if (documento.equals(ConstantsVentas.TIPO_COMP_FACTURA)) {
            retorno = false;
        }
        return retorno;
    }

    private boolean comprobarDocumentoNotaCredito() {
        boolean retorno = true;
        String documento = //= tblCabeceraPedido.getValueAt(0,7).toString();
            FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data, 0, 7);
        if (documento.equals(ConstantsVentas.TIPO_COMP_NOTA_CREDITO)) {
            retorno = false;
        }
        return retorno;
    }


    public void cargaCombo(String doc) {
        cmbSerie.removeAllItems();
        {
            ArrayList parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(doc);

            FarmaLoadCVL.loadCVLFromSP(cmbSerie, "cmbSerie", "PTOVENTA_CAJ_ANUL.CAJ_GET_SERIE_ANUL(?,?,?)", parametros,
                                       true, true);
            parametros = null;
        }
    }

    private boolean comprobarProductosRestantes() {
        boolean retorno = true;
        try {
            int cant = DBCaja.getProductosRestantes(numeroPedido, numeroComp);
            if (cant == 0) {
                retorno = false;
                FarmaUtility.showMessage(this,
                                         "Este Pedido corresponde a un periodo anterior. \nSin embargo, no tiene productos pendientes.",
                                         txtNroComprobante);
            }
        } catch (SQLException e) {
            retorno = false;
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al comprobar productos restantes  - \n" +
                    e.getMessage(), txtNroComprobante);
        }
        return retorno;
    }

    private boolean cargaLoginAdmLocal() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            //Se validara por el rol dependiendo del tipo de pedido
            //14.11.2007  dubilluz modificacion
            if (VariablesCaja.vIndPedidoRecargaVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
                log.debug("Pedido Recarga Virtual..Solo el operador podra anular");
            } else {
                dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
                log.debug("Pedido normal..");
            }

            dlgLogin.setVisible(true);
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),
                                     null);
        }
        return FarmaVariables.vAceptar;
    }

    /**
     * Obtiene el tiempo maximo para la anulacion de un pedido recarga virtual
     * @author dubilluz
     * @since  09.11.2007
     */
    private String time_max(String pnum_ped) {
        String valor = "";
        try {
            valor = DBCaja.getTimeMaxAnulacion(pnum_ped);

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al obtener tiempo maximo de anulacion de Producto Recarga Virtual.\n" +
                    e.getMessage(), null);
        }
        return valor;
    }

    /**
     * Obtiene el tiempo maximo para la anulacion de un pedido recarga virtual
     * @author dubilluz
     * @since  09.11.2007
     */
    private String getNumeroPedido(String pnumeroComp, String pmonto) {
        String vNum_ped = "";
        try {
            vNum_ped = DBCaja.getNumeroPedido(pnumeroComp);

        } catch (SQLException e) {
            log.error("", e);
            vNum_ped = " ";
        }
        return vNum_ped;
    }

    private void obtieneInfoPedidoVirtual() {
        try {
            DBCaja.obtieneInfoPedidoVirtual(VariablesVirtual.vArrayList_InfoProdVirtual, numeroPedido);
            log.debug("vArrayList_InfoProdVirtual : " + VariablesVirtual.vArrayList_InfoProdVirtual);
            //Agregado por DVELIZ 15.12.2008
            if (VariablesVirtual.vArrayList_InfoProdVirtual.size() > 0) {
                colocaInfoPedidoVirtual();
            }
        } catch (SQLException sql) {
            VariablesVirtual.vArrayList_InfoProdVirtual.clear();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion de pedido virtual - \n" +
                    sql.getMessage(), null);
        }
    }

    private void colocaInfoPedidoVirtual() {
        VariablesCaja.vTipoProdVirtual =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 1);
        VariablesCaja.vPrecioProdVirtual =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 2);
        VariablesCaja.vNumeroCelular =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 3);
        VariablesCaja.vCodigoProv =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 4);
        VariablesCaja.vNumeroTraceOriginal =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 5);
        VariablesCaja.vCodAprobacionOriginal =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 6);
        /* 27.09.2007 ERIOS Datos necesarios para Bprepaid */
        VariablesCaja.vFechaTX =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 8);
        VariablesCaja.vHoraTX = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 9);
    }


}
