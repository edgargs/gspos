package mifarma.ptoventa.delivery;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.DlgFormaPago;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.delivery.reference.ConstantsDelivery;
import mifarma.ptoventa.delivery.reference.DBDelivery;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.inventario.transfDelivery.DlgTransfDeliveryDetalle;
import mifarma.ptoventa.inventario.transfDelivery.reference.VariablesTranfDelivery;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.DlgNumeroPedidoGenerado;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgUltimosPedidos extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgUltimosPedidos.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelDetalleUltimosPedidos;
    private FarmaTableModel tableModelCabeceraUltimosPedidos;
    private FarmaTableModel tableModelTransferencia;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private static String numPedVta = "";
    private String codLocal = "";
    private String indConv = "N";
    private String CodConvPedVta = "";
    ActionMap actionMap1 = new ActionMap();
    JScrollPane scrUltimosPedidos = new JScrollPane();
    JPanel pnlRelacion = new JPanel();
    XYLayout xYLayout2 = new XYLayout();
    JScrollPane scrDetalle = new JScrollPane();
    JPanel pnlItems = new JPanel();
    XYLayout xYLayout3 = new XYLayout();
    JLabelFunction lblEsc = new JLabelFunction();
    JTable tblDetalle = new JTable();
    JTable tblListaUltimos = new JTable();
    private JButtonLabel btnUltimosPedidos = new JButtonLabel();
    JTable tblTransferencias = new JTable();
    private JPanelTitle pnlDatosCliente = new JPanelTitle();
    private JLabelWhite lblNombreCliente_T = new JLabelWhite();
    private JLabelWhite lblDirecCliente_T = new JLabelWhite();
    private JLabelWhite lblTelfCliente_T = new JLabelWhite();
    private JLabelWhite lblDirecCliente = new JLabelWhite();
    private JLabelWhite lblTelfCLiente = new JLabelWhite();
    private JLabelWhite lblNomCliente = new JLabelWhite();
    JLabelFunction lblF11 = new JLabelFunction();
    JLabelFunction lblF5 = new JLabelFunction();
    JLabelFunction lblF9 = new JLabelFunction();
    JScrollPane scrTransferencia = new JScrollPane();
    JPanel pnlTransferencia = new JPanel();
    XYLayout xYLayout4 = new XYLayout();
    private String tipoVenta; // kmoncada para indicar los tipos de venta a mostrar en el Formulario.
    // kmoncada 21.08.2014 mostrar nombre de convenio
    private JLabelWhite lblNomConvenio_T = new JLabelWhite();
    private JLabelWhite lblNomConvenio = new JLabelWhite();

    private Timer timer;

    private JButtonLabel btnDetalle = new JButtonLabel();
    private JButtonLabel btnTransferencia = new JButtonLabel();
    // JLabel lblModo = new FarmaBlinkJLabel();
    // JLabel lblTipoPedido = new JLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgUltimosPedidos() {
        this(null, "", false);
    }

    public DlgUltimosPedidos(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(751, 620));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ultimos Pedidos Realizados");
        this.setFont(new Font("SansSerif", 0, 11));
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(657, 361));
        jContentPane.setBackground(Color.white);
        jContentPane.setForeground(Color.white);

        lblNombreCliente_T.setText("Nombre del Cliente :");
        lblNombreCliente_T.setBounds(new Rectangle(5, 10, 120, 15));
        lblNombreCliente_T.setForeground(new Color(255, 130, 14));

        lblDirecCliente_T.setText("Direccion del Cliente :");
        lblDirecCliente_T.setBounds(new Rectangle(5, 30, 120, 15));
        lblDirecCliente_T.setForeground(new Color(255, 130, 14));

        lblTelfCliente_T.setText("Teléfono :");
        lblTelfCliente_T.setBounds(new Rectangle(5, 50, 120, 15));
        lblTelfCliente_T.setForeground(new Color(255, 130, 14));
        // kmoncada 21.08.2014 mostrar nombre de convenio
        lblNomConvenio_T.setText("Convenio :");
        lblNomConvenio_T.setBounds(new Rectangle(300, 50, 70, 15));
        lblNomConvenio_T.setForeground(new Color(255, 130, 14));
        lblNomConvenio_T.setVisible(false);

        lblNomCliente.setBounds(new Rectangle(130, 10, 480, 15));
        lblNomCliente.setForeground(new Color(255, 130, 14));

        lblDirecCliente.setBounds(new Rectangle(130, 30, 480, 15));
        lblDirecCliente.setForeground(new Color(255, 130, 14));

        lblTelfCLiente.setBounds(new Rectangle(130, 50, 135, 15));
        lblTelfCLiente.setForeground(new Color(255, 130, 14));

        lblNomConvenio.setVisible(false);
        lblNomConvenio.setBounds(new Rectangle(370, 50, 325, 15));
        lblNomConvenio.setForeground(new Color(255, 130, 14));

        pnlDatosCliente.setBounds(new Rectangle(10, 5, 715, 75));
        pnlDatosCliente.setBackground(Color.white);
        pnlDatosCliente.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlDatosCliente.add(lblNomCliente, null);
        pnlDatosCliente.add(lblTelfCLiente, null);
        pnlDatosCliente.add(lblDirecCliente_T, null);
        pnlDatosCliente.add(lblNombreCliente_T, null);
        // kmoncada 21.08.2014 mostrar nombre de convenio
        pnlDatosCliente.add(lblTelfCliente_T, null);
        pnlDatosCliente.add(lblNomConvenio_T, null);
        pnlDatosCliente.add(lblNomConvenio, null);
        pnlDatosCliente.add(lblDirecCliente, null);
        jContentPane.add(pnlDatosCliente, null);


        btnUltimosPedidos.setText("Relacion Ultimos Pedidos :");
        btnUltimosPedidos.setFont(new Font("SansSerif", 1, 11));
        btnUltimosPedidos.setHorizontalAlignment(SwingConstants.LEFT);
        btnUltimosPedidos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnUltimosPedidos.setBackground(new Color(43, 141, 39));
        btnUltimosPedidos.setForeground(Color.white);
        btnUltimosPedidos.setRequestFocusEnabled(false);
        btnUltimosPedidos.setBorderPainted(false);
        btnUltimosPedidos.setContentAreaFilled(false);
        btnUltimosPedidos.setDefaultCapable(false);
        btnUltimosPedidos.setFocusPainted(false);
        btnUltimosPedidos.setMnemonic('R');
        btnUltimosPedidos.setBounds(new Rectangle(10, 0, 165, 15));
        btnUltimosPedidos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPedidosPendeintes_actionPerformed(e);
            }
        });

        pnlRelacion.setBackground(new Color(255, 130, 14));
        pnlRelacion.setLayout(xYLayout2);
        pnlRelacion.setFont(new Font("SansSerif", 0, 11));
        pnlRelacion.setBounds(new Rectangle(10, 85, 715, 20));
        pnlRelacion.add(btnUltimosPedidos, new XYConstraints(10, 0, 165, 15));
        jContentPane.add(pnlRelacion, null);


        tblListaUltimos.setFont(new Font("SansSerif", 0, 11));
        tblListaUltimos.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                tblListaUltimos_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                tblListaUltimos_keyPressed(e);
            }
        });

        scrUltimosPedidos.setFont(new Font("SansSerif", 0, 11));
        scrUltimosPedidos.setBounds(new Rectangle(10, 105, 715, 145));
        scrUltimosPedidos.setBackground(new Color(255, 130, 14));
        //scrUltimosPedidos.getViewport();
        scrUltimosPedidos.getViewport().add(tblListaUltimos, null);
        jContentPane.add(scrUltimosPedidos, null);


        btnDetalle.setText("Detalle del Pedido :");
        btnDetalle.setFont(new Font("SansSerif", 1, 11));
        btnDetalle.setHorizontalAlignment(SwingConstants.LEFT);
        btnDetalle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDetalle.setBackground(new Color(43, 141, 39));
        btnDetalle.setForeground(Color.white);
        btnDetalle.setRequestFocusEnabled(false);
        btnDetalle.setMnemonic('d');
        btnDetalle.setBorderPainted(false);
        btnDetalle.setContentAreaFilled(false);
        btnDetalle.setDefaultCapable(false);
        btnDetalle.setFocusPainted(false);
        btnDetalle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDetalle_actionPerformed(e);
            }
        });

        pnlItems.setBackground(new Color(255, 130, 14));
        pnlItems.setFont(new Font("SansSerif", 0, 11));
        pnlItems.setLayout(xYLayout3);
        pnlItems.setBounds(new Rectangle(10, 255, 715, 20));
        pnlItems.add(btnDetalle, new XYConstraints(10, 0, 125, 15));
        jContentPane.add(pnlItems, null);


        tblDetalle.setFont(new Font("SansSerif", 0, 11));
        tblDetalle.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblDetalle_keyPressed(e);
            }
        });

        scrDetalle.setFont(new Font("SansSerif", 0, 11));
        scrDetalle.setBounds(new Rectangle(10, 275, 715, 120));
        scrDetalle.setBackground(new Color(255, 130, 14));
        //scrDetalle.getViewport();
        scrDetalle.getViewport().add(tblDetalle, null);
        jContentPane.add(scrDetalle, null);


        btnTransferencia.setText("Transferencias asociadas al pedido :");
        btnTransferencia.setFont(new Font("SansSerif", 1, 11));
        btnTransferencia.setHorizontalAlignment(SwingConstants.LEFT);
        btnTransferencia.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnTransferencia.setBackground(new Color(43, 141, 39));
        btnTransferencia.setForeground(Color.white);
        btnTransferencia.setRequestFocusEnabled(false);
        btnTransferencia.setMnemonic('t');
        btnTransferencia.setBorderPainted(false);
        btnTransferencia.setContentAreaFilled(false);
        btnTransferencia.setDefaultCapable(false);
        btnTransferencia.setFocusPainted(false);
        btnTransferencia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTransferenciasActionPerformed(e);
            }
        });

        pnlTransferencia.setBackground(new Color(255, 130, 14));
        pnlTransferencia.setFont(new Font("SansSerif", 0, 11));
        pnlTransferencia.setLayout(xYLayout4);
        pnlTransferencia.setBounds(new Rectangle(10, 400, 715, 20));
        pnlTransferencia.add(btnTransferencia, new XYConstraints(10, 0, 220, 15));
        jContentPane.add(pnlTransferencia, null);


        tblTransferencias.setFont(new Font("SansSerif", 0, 11));
        tblTransferencias.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblTransferenciasKeyPressed(e);
            }
        });

        scrTransferencia.setFont(new Font("SansSerif", 0, 11));
        scrTransferencia.setBounds(new Rectangle(10, 420, 715, 120));
        scrTransferencia.setBackground(new Color(255, 130, 14));
        //scrTransferencia.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrTransferencia.getViewport().add(tblTransferencias, null);
        jContentPane.add(scrTransferencia, null);


        lblEsc.setText("[ Esc ]  Cerrar");
        lblEsc.setBounds(new Rectangle(630, 545, 95, 20));

        lblF11.setText("[ F11 ]  Generar Pedido");
        lblF11.setBounds(new Rectangle(470, 545, 150, 20));

        lblF5.setText("[ F5 ]  Actualizar Lista");
        lblF5.setBounds(new Rectangle(320, 545, 140, 20));

        lblF9.setText("[ F7 ]  Imprimir Comanda");
        lblF9.setBounds(new Rectangle(160, 545, 150, 20));

        jContentPane.add(lblF9, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);

        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        recuperarPedido();
        VariablesConvenioBTLMF.limpiaVariablesBTLMF(); // kmoncada 10.07.2014
        FarmaVariables.vAceptar = false;
        initTableDetCabUltimosPedidos();
        initTableListaCabUltimosPedidos();
        initTableTransferencias();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaCabUltimosPedidos() {
        tableModelCabeceraUltimosPedidos =
                new FarmaTableModel(ConstantsDelivery.columnsListaCabUltimosPedidos, ConstantsDelivery.defaultListaCabUltimosPedidos,
                                    0);
        FarmaUtility.initSimpleList(tblListaUltimos, tableModelCabeceraUltimosPedidos,
                                    ConstantsDelivery.columnsListaCabUltimosPedidos);
        //actualizaListaPedidos(); kmoncada para evitar que se cargen inicialmente los pedidos si haber definido los tipos de pedidos a mostrar. Delivery o institucional
    }

    private void initTableDetCabUltimosPedidos() {
        tableModelDetalleUltimosPedidos =
                new FarmaTableModel(ConstantsDelivery.columnsListaDetUltimosPedidos, ConstantsDelivery.defaultListaDetUltimosPedidos,
                                    0);
        FarmaUtility.initSimpleList(tblDetalle, tableModelDetalleUltimosPedidos,
                                    ConstantsDelivery.columnsListaDetUltimosPedidos);
    }

    private void initTableTransferencias() {
        Object[] defLstCabTransferencias = { " ", " ", " ", " " };

        FarmaColumnData[] clmnsLstDetTransferencias =
        { new FarmaColumnData("Cod.Local Origen", 120, JLabel.LEFT), new FarmaColumnData("Local Origen", 200,
                                                                                         JLabel.LEFT),
          new FarmaColumnData("Cod.Producto", 120, JLabel.LEFT),
          new FarmaColumnData("Descripción Producto", 242, JLabel.LEFT), };

        tableModelTransferencia = new FarmaTableModel(clmnsLstDetTransferencias, defLstCabTransferencias, 0);
        FarmaUtility.initSimpleList(tblTransferencias, tableModelTransferencia, clmnsLstDetTransferencias);

    }

    private void cargaListaCabecera() {
        try {
            DBDelivery.cargaListaCabUltimosPedidos(tableModelCabeceraUltimosPedidos, tipoVenta);
            if (tblListaUltimos.getRowCount() > 0) {
                //FarmaUtility.ordenar(tblListaUltimos, tableModelCabeceraUltimosPedidos, 1, FarmaConstants.ORDEN_DESCENDENTE);
                FarmaUtility.moveFocusJTable(tblListaUltimos);
                numPedVta = ((String)tblListaUltimos.getValueAt(tblListaUltimos.getSelectedRow(), 1)).trim();
                codLocal = ((String)tblListaUltimos.getValueAt(tblListaUltimos.getSelectedRow(), 9)).trim();
                cargaListaDetallePedido(numPedVta, codLocal);

            } else {
                tableModelDetalleUltimosPedidos.clearTable();
                tableModelDetalleUltimosPedidos.fireTableDataChanged();
                //lblCantItems.setText("0");
                lblDirecCliente.setText("");
                lblNomCliente.setText("");
                lblTelfCLiente.setText("");
                // KMONCADA 01.09.2014 limpia variables estaticas
                numPedVta = "";
                codLocal = "";
            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al cargar ultimos pedidos - \n" +
                    e, tblListaUltimos);
            log.error("", e);
        }
    }

    private void cargaListaDetallePedido(String pNumPedido, String pCodLocal) {
        try {
            DBDelivery.cargaListaDetUltimosPedidos(tableModelDetalleUltimosPedidos, pNumPedido, pCodLocal);
            //lblCantItems.setText("" + tblDetalle.getRowCount());
            if (tblDetalle.getRowCount() > 0) {
                FarmaUtility.ordenar(tblDetalle, tableModelDetalleUltimosPedidos, 2, "asc");
            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al cargar detalle ultimos pedidos - \n" +
                    e, tblListaUltimos);
            log.error("", e);
        }
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void tblListaUltimos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblDetalle_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblTransferenciasKeyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaUltimos);
        //Aqui se cargara la pantalla de logeo de cajero
        cargaLogin();
        //ERIOS 2.4.5 En Venta Empresa, no refresca automatico
        if (!tipoVenta.equals(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL) && this.isVisible()) {
            TimerTask timerTask = new TimerTask() {
                public void run() {
                    actualizaListaPedidos();
                }
            };
            timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 4000, 40000);
        }
    }

    private void btnPedidosPendeintes_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaUltimos);
    }

    private void btnDetalle_actionPerformed(ActionEvent e) {
        if (tblDetalle.getRowCount() > 0)
            FarmaUtility.moveFocusJTable(tblDetalle);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            /*
      if(tblListaUltimos.getRowCount() > 0 &&
         com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Está seguro de anular el pedido?"))
      {
        if(!cargaLoginAdminLocal()){
          FarmaUtility.showMessage(this,"No se realizó la operación. Solo un usuario con Rol de\nAdministrador de Local puede anular el pedido.", tblListaUltimos);
          return;
        }
        anulaPedidoDeliveryAutomatico();
        actualizaListaPedidos();
      }*/
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            //JCORTEZ 16.07.2008 Se imprime la comanda
            //if(VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("S")){
            int row = 0;
            row = tblListaUltimos.getSelectedRow();
            if (row > -1) {
                VariablesCaja.vNumPedVta = FarmaUtility.getValueFieldJTable(tblListaUltimos, row, 1);
                log.debug("VariablesCaja.vNumPedVta : " + VariablesCaja.vNumPedVta);
                UtilityCaja.imprimeDatosDelivery(this, VariablesCaja.vNumPedVta);
            }
            //}
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            actualizaListaPedidos();
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {

            /*
             * @author LTAVARA
             *Validar conexion con el EPOS
             * @since 29.09.2014
             * */
            // KMONCADA 14.11.2014 SE DEBE VALIDAR SOLO AL REALIZAR EL COBRO DEL PEDIDO
            //if(!UtilityEposTrx.vPermiteCobro())return;
            //LTAVARA

            if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                if (tblListaUltimos.getRowCount() > 0) {
                    String pIndTransferencia =
                        FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data,
                                                            tblListaUltimos.getSelectedRow(), 14);
                    if (pIndTransferencia.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {

                        operaTransferencia();
                    } else {
                        if (validaProductoInactivo() && validaFraccionLocal() && validaStockLocal() &&
                            validaCantidadEntera()) { //rherrera 04.08.2014
                            obtieneValores();
                            obtieneLocalOrigen();
                            if (VariablesDelivery.vCodLocalOrigen.equalsIgnoreCase(ConstantsDelivery.CODIGO_LOCAL_VTA_INSTITUCIONAL))
                                muestraListaDetallePedido();
                            else
                                generaPedidoDeliveryAutomatico();
                        }
                    }
                }
            } else {
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción", null);
            }
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        if (timer != null) {
            timer.cancel();
        }
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void tblListaUltimos_keyReleased(KeyEvent e) {
        if (tblListaUltimos.getRowCount() > 0) {
            numPedVta =
                    (FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                         1)).trim();
            codLocal =
                    (FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                         9)).trim();
            cargaDatosCabecera();
            cargaListaDetallePedido(numPedVta, codLocal);
            cargarListaTransferencias(numPedVta, codLocal);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaDatosCabecera() {
        if (tblListaUltimos.getRowCount() > 0) {
            String nomCli = "", dirCli = "", telefono = "", nombreConvenio = "";

            nomCli =
                    FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                        6).trim();
            dirCli =
                    (FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                         7)).trim();
            telefono =
                    (FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                         8)).trim();
            indConv =
                    (FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                         11)).trim();
            CodConvPedVta =
                    (FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                         12)).trim();
            lblNomCliente.setText(nomCli);
            lblDirecCliente.setText(dirCli);
            lblTelfCLiente.setText(telefono);

            // kmoncada 21.08.2014 mostrar nombre de convenio
            boolean mostrarConvenio = false;
            if (ConstantsVentas.TIPO_PEDIDO_DELIVERY.equals(tipoVenta)) {
                nombreConvenio =
                        FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                            22);
                if (nombreConvenio != null) {
                    if (nombreConvenio.trim().length() != 0) {
                        mostrarConvenio = true;
                        lblNomConvenio.setText(nombreConvenio);
                    }
                }
            }

            lblNomConvenio.setVisible(mostrarConvenio);
            lblNomConvenio_T.setVisible(mostrarConvenio);
        }
    }

    private void generaPedidoDeliveryAutomatico() {
        String estadoPedido = "";
        int numCaja = 0;
        String TipoComp = "";

        if (tblListaUltimos.getRowCount() <= 0)
            return;

        try {
            estadoPedido = obtieneEstadoPedido_ForUpdate();
            if (!estadoPedido.equalsIgnoreCase(ConstantsVentas.ESTADO_PEDIDO_PENDIENTE)) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "El pedido No se encuentra pendiente. Verifique!!!", tblListaUltimos);
                actualizaListaPedidos();
                return;
            }


            if (!JConfirmDialog.rptaConfirmDialog(this, "Está seguro de generar el pedido?"))
                return;

            //Dubilluz 03.07.2014
            //Asigna Motorizado
            VariablesDelivery.vNumeroPedido =
                    FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 1);
            VariablesDelivery.vCodLocal =
                    FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 9);

            if (!cargaListaMotorizado())
                return;
            //Asigna Motorizado
            //dubilluz 03.07.2014

            VariablesDelivery.vNumeroPedido =
                    FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 1);
            VariablesDelivery.vCodLocal =
                    FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 9);
            VariablesVentas.vNum_Ped_Vta = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO, 10);
            VariablesVentas.vNum_Ped_Diario = obtieneNumeroPedidoDiario();
            log.debug("VariablesVentas.vNum_Ped_Vta : " + VariablesVentas.vNum_Ped_Vta);
            log.debug("VariablesVentas.vNum_Ped_Diario : " + VariablesVentas.vNum_Ped_Diario);
            VariablesVentas.vVal_Neto_Ped =
                    FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 3);

            //JCORTEZ 14.05.09 Se obtiene tipo de comprobantes
            TipoComp = FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 5);

            if (TipoComp.trim().equalsIgnoreCase("BOLETA"))
                TipoComp = ConstantsVentas.TIPO_COMP_BOLETA;
            else if (TipoComp.trim().equalsIgnoreCase("FACTURA"))
                TipoComp = ConstantsVentas.TIPO_COMP_FACTURA;

            if (VariablesVentas.vTip_Comp_Ped.trim().length() < 2)
                VariablesVentas.vTip_Comp_Ped = TipoComp;

            //JCORTEZ 14.05.09 Si es que el numero de caja es vacio
            log.debug("FarmaVariables.vNuSecUsu : " + FarmaVariables.vNuSecUsu);
            if (VariablesCaja.vNumCaja.trim().length() < 2)
                numCaja = DBCaja.obtieneNumeroCajaUsuarioAux2();

            VariablesCaja.vNumCaja = "" + numCaja;

            log.debug("JCORTEZ: Numero de caja--> " + VariablesCaja.vNumCaja);
            log.debug("JCORTEZ: Tipo de comprobante origen --> " + VariablesVentas.vTip_Comp_Ped);
            //VariablesVentas.vTip_Comp_Ped = DBCaja.getObtieneTipoComp(VariablesCaja.vNumCaja,VariablesVentas.vTip_Comp_Ped);

            //JCORTEZ 10.06.09  Se obtiene tipo de comrpobante de la relacion maquina - impresora
            if (TipoComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET) ||
                TipoComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)) {
                VariablesVentas.vTip_Comp_Ped =
                        DBCaja.getObtieneTipoCompPorIP(FarmaVariables.vIpPc, VariablesVentas.vTip_Comp_Ped);

                log.debug("JCORTEZ: VariablesVentas.vTip_Comp_Ped--> " + VariablesVentas.vTip_Comp_Ped);
                if (VariablesVentas.vTip_Comp_Ped.trim().equalsIgnoreCase("N")) {
                    FarmaUtility.showMessage(this,
                                             "Usted no cuenta con una impresora asignada de ticket o boleta. Verifique!!!",
                                             tblListaUltimos);
                    return;
                }
            }

            log.debug("JCORTEZ: Tipo de comprobante local --> " + VariablesVentas.vTip_Comp_Ped);

            DBDelivery.generaPedidoDeliveryAutomatico();
            DBDelivery.actualuizaValoresDa();
            //DBConvenio.actualizaNumPedido(VariablesVentas.vNum_Ped_Vta,VariablesDelivery.vNumeroPedido);
            FarmaUtility.aceptarTransaccion();
            // -- Se modifico para caja multifuncional
            // dubilluz  02/05/2008
            if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL)) {
                VariablesCaja.vNumPedPendiente = VariablesVentas.vNum_Ped_Diario;
                muestraCobroPedido();
            } else
                muestraPedidoRapido();
            // muestraPedidoRapido();
        } catch (SQLException sqlex) {
            FarmaUtility.liberarTransaccion();
            log.error("", sqlex);
            FarmaUtility.showMessage(this, "Error al grabar el pedido delivery automático - \n" +
                    sqlex, tblListaUltimos);
        }
    }

    private void muestraPedidoRapido() {
        DlgNumeroPedidoGenerado dlgNumeroPedidoGenerado = new DlgNumeroPedidoGenerado(myParentFrame, "", true);
        dlgNumeroPedidoGenerado.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            actualizaListaPedidos();
        }
    }

    private String obtieneEstadoPedido_ForUpdate() {
        String estadoPedido = "";
        try {
            VariablesDelivery.vNumeroPedido =
                    FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 1);
            VariablesDelivery.vCodLocal =
                    FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 9);
            estadoPedido = DBDelivery.obtieneEstadoPedido_ForUpdate();
        } catch (SQLException sqlex) {
            FarmaUtility.liberarTransaccion();
            log.error("", sqlex);
            estadoPedido = "";
            FarmaUtility.showMessage(this, "Error al obtener estado del pedido - \n" +
                    sqlex, tblListaUltimos);
        }
        log.debug("VariablesDelivery.vNumeroPedido : " + VariablesDelivery.vNumeroPedido);
        log.debug("estadoPedido : " + estadoPedido);
        return estadoPedido;
    }

    private String obtieneNumeroPedidoDiario() throws SQLException {

        String feModNumeracion = DBVentas.obtieneFecModNumeraPed();
        String feHoyDia = "";
        String numPedDiario = "";
        if (!(feModNumeracion.trim().length() > 0))
            throw new SQLException("Ultima Fecha Modificacion de Numeración Diaria del Pedido NO ES VALIDA !!!",
                                   "Error", 0001);
        else {
            feHoyDia = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            feHoyDia = feHoyDia.trim().substring(0, 2);
            feModNumeracion = feModNumeracion.trim().substring(0, 2);
            if (Integer.parseInt(feHoyDia) != Integer.parseInt(feModNumeracion)) {
                FarmaSearch.inicializaNumeracionNoCommit(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO);
                numPedDiario = "0001";
            } else {
                // Obtiene el Numero de Atencion del Día y hace SELECT FOR UPDATE.
                numPedDiario = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO, 4);
            }
        }
        return numPedDiario;
    }

    private void anulaPedidoDeliveryAutomatico() {
        try {
            VariablesDelivery.vNumeroPedido =
                    FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 1);
            VariablesDelivery.vCodLocal =
                    FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 9);
            DBDelivery.anulaPedidoDeliveryAutomatico(VariablesDelivery.vCodLocal, VariablesDelivery.vNumeroPedido);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Pedido anulado correctamente.", tblListaUltimos);
        } catch (SQLException sqlex) {
            FarmaUtility.liberarTransaccion();
            log.error("", sqlex);
            FarmaUtility.showMessage(this, "Error al anular pedido de delivery automatico - \n" +
                    sqlex, tblListaUltimos);
        }
    }

    private void actualizaListaPedidos() {
        cargaListaCabecera();
        cargaDatosCabecera();

        if (tblListaUltimos.getRowCount() > 0) {
            String numPedVta =
                FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                    0);
            String codLocal = FarmaVariables.vCodLocal;
        }
        cargaListaDetallePedido(numPedVta, codLocal);
        cargarListaTransferencias(numPedVta, codLocal);
    }

    private boolean cargaLoginAdminLocal() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;
        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
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

    private boolean validaFraccionLocal() {
        String valorFraccion = "";
        String codigoErrado = "";
        String nomProducto = "";
        StringBuffer cadenaCodigos = new StringBuffer();
        for (int i = 0; i < tblDetalle.getRowCount(); i++) {
            valorFraccion = FarmaUtility.getValueFieldJTable(tblDetalle, i, 8);
            nomProducto = FarmaUtility.getValueFieldJTable(tblDetalle, i, 1);
            if (valorFraccion.equals(ConstantsDelivery.VALOR_FRACCION_ERROR)) {
                //codigoErrado = valorFraccion = FarmaUtility.getValueFieldJTable(tblDetalle, i, 0);
                codigoErrado =
                        codigoErrado + " : " + FarmaUtility.getValueFieldJTable(tblDetalle, i, 0) + " - " + nomProducto;


                if (cadenaCodigos.length() == 0)
                    cadenaCodigos.append(codigoErrado);
                else
                    cadenaCodigos.append(", " + codigoErrado);
            }
        }
        if (cadenaCodigos.length() == 0)
            return true;
        else {
            FarmaUtility.showMessage(this,
                                     "No se puede generar el pedido. Los siguientes codigos\nno tienen un valor de fracción correcto : \n" +
                    cadenaCodigos, tblListaUltimos);
            return false;
        }
    }

    private boolean validaStockLocal() {
        /* String cantidadPedido = "";
      String stockReal = "";
      String codigoErrado = "";
      String producto = "";
      String unidadVenta = "";
      String valorFrac = "";
      String unidadLocal = "";
      double totalpedido = 0;//02.08.2014
      boolean error = false;
        ArrayList<String> listado = null;
      ArrayList<ArrayList<String>> cadenaCodigos = new ArrayList<>();

      for(int i=0; i<tblDetalle.getRowCount(); i++)
      {
        cantidadPedido = FarmaUtility.getValueFieldJTable(tblDetalle, i, 4);
        valorFrac     = FarmaUtility.getValueFieldJTable(tblDetalle, i, 9);
        stockReal = FarmaUtility.getValueFieldJTable(tblDetalle, i, 7);
        if( FarmaUtility.isDouble(cantidadPedido) && FarmaUtility.isDouble(stockReal) )
        {
            totalpedido = Double.parseDouble(cantidadPedido)*Double.parseDouble(valorFrac) ;//02.08.2014
          log.debug("cantidad pedido : " + Double.parseDouble(cantidadPedido));
          log.debug("stock real : " + Double.parseDouble(stockReal));
          if( totalpedido//Double.parseDouble(cantidadPedido)*Double.parseDouble(valorFrac)//rherrera 02.08.2014
              > Double.parseDouble(stockReal) ) error = true;
          else error = false;
        } else error = true;
        if( error )
        {
          double faltante = (totalpedido - Double.parseDouble(stockReal))/Double.parseDouble(valorFrac); //02.08.2014

          codigoErrado = FarmaUtility.getValueFieldJTable(tblDetalle, i, 0);
            producto = FarmaUtility.getValueFieldJTable(tblDetalle, i, 1);
            unidadVenta = FarmaUtility.getValueFieldJTable(tblDetalle, i, 2);
            unidadLocal= FarmaUtility.getValueFieldJTable(tblDetalle, i, 11);
            listado = new ArrayList<>();
            listado.add(codigoErrado);
            listado.add(producto);
            listado.add(cantidadPedido);
            listado.add(unidadVenta);
            listado.add(stockReal);
            listado.add(unidadLocal);
            listado.add((new Double(faltante*Double.parseDouble(valorFrac)).intValue()+""));
            listado.add(unidadLocal);
            cadenaCodigos.add(listado);
        }
      } */

        try {
            String numPedVta =
                (FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                     1)).trim();
            String codLocal =
                (FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                     9)).trim();
            ArrayList cadenaCodigos = new ArrayList();
            //FarmaUtility.showMessage(this, "Error al Validar ",tblListaUltimos);
            DBDelivery.getFaltanteStkDLV(cadenaCodigos, numPedVta, codLocal);
            //FarmaUtility.showMessage(this, "Error al Validar 22 "+ cadenaCodigos.size(),tblListaUltimos);
            //lblCantItems.setText("" + tblDetalle.getRowCount());
            if (tblDetalle.getRowCount() > 0) {
                if (cadenaCodigos.size() == 0)
                    return true;
                else {

                    DlgStockFaltante dlgStockFaltante = new DlgStockFaltante(myParentFrame, "", true);
                    dlgStockFaltante.setListado(cadenaCodigos);
                    dlgStockFaltante.setVisible(true);
                    return false;
                }
            } else
                return true;
        } catch (Exception e) {
            FarmaUtility.showMessage(this, "Error al Validar Stock en el Local - \n" +
                    e, tblListaUltimos);
            return false;

        }
    }

    private boolean validaCantidadEntera() {
        String cantidadPedido = "";
        double cantidadPedidoDouble = 0.00;
        String codigoErrado = "";
        String nomProducto = "";
        boolean error = false;
        StringBuffer cadenaCodigos = new StringBuffer();
        for (int i = 0; i < tblDetalle.getRowCount(); i++) {
            error = false;
            nomProducto = FarmaUtility.getValueFieldJTable(tblDetalle, i, 1);
            cantidadPedido = FarmaUtility.getValueFieldJTable(tblDetalle, i, 4);
            cantidadPedidoDouble = Double.parseDouble(cantidadPedido) % FarmaUtility.trunc(cantidadPedido);
            if (cantidadPedidoDouble != 0) {
                error = true;
            }
            if (error) {
                codigoErrado = FarmaUtility.getValueFieldJTable(tblDetalle, i, 0) + " - " + nomProducto;
                ;
                if (cadenaCodigos.length() == 0)
                    cadenaCodigos.append(codigoErrado);
                else
                    cadenaCodigos.append("\n " + codigoErrado);
            }
        }
        if (cadenaCodigos.length() == 0)
            return true;
        else {
            FarmaUtility.showMessage(this,
                                     "No se puede generar el pedido. Los siguientes codigos\nno tienen una cantidad válida: \n" +
                    cadenaCodigos, tblListaUltimos);
            return false;
        }
    }

    private boolean validaProductoInactivo() {
        String valorProdInact = "";

        String codigoErrado = "";
        String nomProducto = "";
        boolean error = false;
        StringBuffer cadenaCodigos = new StringBuffer();
        for (int i = 0; i < tblDetalle.getRowCount(); i++) {
            error = false;
            valorProdInact = FarmaUtility.getValueFieldJTable(tblDetalle, i, 10);
            nomProducto = FarmaUtility.getValueFieldJTable(tblDetalle, i, 1);
            if (!valorProdInact.equalsIgnoreCase("A")) {
                error = true;
            }
            if (error) {
                codigoErrado = FarmaUtility.getValueFieldJTable(tblDetalle, i, 0) + " - " + nomProducto;
                if (cadenaCodigos.length() == 0)
                    cadenaCodigos.append(codigoErrado);
                else
                    cadenaCodigos.append("\n" +
                            codigoErrado);
            }
        }
        if (cadenaCodigos.length() == 0)
            return true;
        else {
            FarmaUtility.showMessage(this,
                                     "No se puede generar el pedido. Los siguientes codigos\nson productos Inactivos en el LOCAL: \n" +
                    cadenaCodigos, tblListaUltimos);
            return false;
        }
    }

    private String obtieneLocalOrigen() {
        try {
            VariablesDelivery.vCodLocalOrigen = DBDelivery.obtieneLocalOrigen(VariablesDelivery.vNumeroPedido);
            log.debug("VariablesDelivery.vCodLocalOrigen : " + VariablesDelivery.vCodLocalOrigen);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al obtener el codigo de origen \n" +
                    sql.getMessage(), tblListaUltimos);
            return null;
        }
        return VariablesDelivery.vCodLocalOrigen;
    }

    private void muestraListaDetallePedido() {

        //DUBILLUZ - 16.12.2009
        //Variables para consultar si Tiene todos los Lotes Ingresados
        VariablesDelivery.vNumeroPedido_bk =
                FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 1);
        VariablesDelivery.vCodLocal_bk =
                FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 9);

        DlgListaDetallePedido dlgListaDetallePedido = new DlgListaDetallePedido(myParentFrame, "", true);
        dlgListaDetallePedido.setVisible(true);
        if (FarmaVariables.vAceptar) {
            generaPedidoInstitucionalAutomatico();
        }
        VariablesDelivery.vNumeroPedido_bk = "";
        VariablesDelivery.vCodLocal_bk = "";

    }

    private void obtieneValores() {
        VariablesDelivery.vNumeroPedido =
                FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 1);
        VariablesDelivery.vNombre =
                FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 6);
    }

    private void generaPedidoInstitucionalAutomatico() {
        String estadoPedido = "";
        if (tblListaUltimos.getRowCount() <= 0)
            return;
        try {
            estadoPedido = obtieneEstadoPedido_ForUpdate();
            if (!estadoPedido.equalsIgnoreCase(ConstantsVentas.ESTADO_PEDIDO_PENDIENTE)) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "El pedido No se encuentra pendiente. Verifique!!!", tblListaUltimos);
                actualizaListaPedidos();
                return;
            }
            //if(!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Está seguro de generar el pedido?")) return;
            VariablesDelivery.vNumeroPedido =
                    FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 1);
            VariablesDelivery.vCodLocal =
                    FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 9);
            VariablesVentas.vNum_Ped_Vta = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO, 10);
            VariablesVentas.vNum_Ped_Diario = obtieneNumeroPedidoDiario();
            log.debug("VariablesVentas.vNum_Ped_Vta : " + VariablesVentas.vNum_Ped_Vta);
            log.debug("VariablesVentas.vNum_Ped_Diario : " + VariablesVentas.vNum_Ped_Diario);
            VariablesVentas.vVal_Neto_Ped =
                    FarmaUtility.getValueFieldJTable(tblListaUltimos, tblListaUltimos.getSelectedRow(), 3);
            DBDelivery.generaPedidoInstitucionalAutomatico();
            //DBDelivery.actualuizaValoresDa(); // KMONCADA 10.07.14 pedido vta empresa sin redondeo
            FarmaUtility.aceptarTransaccion();
            //KMONCADA 03.07.2014 NO BORRE EL CONTENIDO DE LA TABLA DE LOTES TEMPORALES
            //eliminaVtaInstiProd();

            // -- Se modifico para caja multifuncional
            // dubilluz  02/05/2008
            if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL)) {
                VariablesCaja.vNumPedPendiente = VariablesVentas.vNum_Ped_Diario;
                muestraCobroPedido();
            } else
                muestraPedidoRapido();


            //muestraPedidoRapido();
        } catch (SQLException sqlex) {
            FarmaUtility.liberarTransaccion();
            log.error("", sqlex);
            FarmaUtility.showMessage(this, "Error al grabar el pedido institucional automático - \n" +
                    sqlex, tblListaUltimos);
        }
    }

    private void eliminaVtaInstiProd() {
        try {
            for (int i = 0; i < tblDetalle.getRowCount(); i++) {
                VariablesDelivery.vCodProducto = FarmaUtility.getValueFieldJTable(tblDetalle, i, 0);
                DBDelivery.eliminaDetalleProducto(VariablesDelivery.vNumeroPedido, VariablesDelivery.vCodProducto);
            }
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al eliminar detalle Producto Temporal- \n" +
                    sql, tblListaUltimos);
        }
    }

    private void muestraCobroPedido() {
        Boolean vIndDatosAdic = false;
        if (indConv.equalsIgnoreCase("S")) {
            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = true;
            VariablesConvenioBTLMF.vCodConvenio = CodConvPedVta;

            /*  if (listaDatosAdic(this, null) && VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null && VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0)
        {
         int nroDatosAdi = VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size();

         for (int j = 0; j < nroDatosAdi; j++)
         {
           Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(j);

           String pCodCampo      = (String)datosAdicConv.get("COD_CAMPO");
           String pDesCampo      = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
           String pNombCampo         = (String)datosAdicConv.get("NOMBRE_CAMPO");
           String pCodValorCampo = (String)datosAdicConv.get("COD_VALOR_CAMPO");

           grabarDatosAdicionalesBTLMF(pCodCampo, pDesCampo, pNombCampo,pCodValorCampo);
         }
        }*/
        }

        DlgFormaPago dlgFormaPago = new DlgFormaPago(myParentFrame, "", true);
        dlgFormaPago.setIndPedirLogueo(false);
        dlgFormaPago.setIndPantallaCerrarAnularPed(true);
        dlgFormaPago.setIndPantallaCerrarCobrarPed(true);
        dlgFormaPago.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cerrarVentana(true);
        }
    }

    /**
     * Para indicar el tipo de venta a mostrar en el formulario.
     * @author kmoncada
     * @param tipoVenta tipo de venta
     */
    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public boolean cargaListaMotorizado() {
        boolean resulta = false;
        if (tblListaUltimos.getSelectedRow() >= 0) {
            //ERIOS 2.4.6 Indicador Delivery Provincia
            VariablesDelivery.pIndDLV_LOCAL =
                    FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data,
                                                        tblListaUltimos.getSelectedRow(), 13);
            if (VariablesDelivery.pIndDLV_LOCAL.trim().equalsIgnoreCase("S")) {
                VariablesDelivery.vTipoMaestro = ConstantsDelivery.LISTA_MOTORIZADO_RUTA;
                DlgListaMotorizados dlgListaMot = new DlgListaMotorizados(myParentFrame, "", true);
                dlgListaMot.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    VariablesDelivery.vCodMotorizado = VariablesDelivery.vCodMaestro;
                    VariablesDelivery.vDescMotorizado = VariablesDelivery.vDescMaestro;

                    resulta = true;
                } else {
                    resulta = false;
                    VariablesDelivery.vCodMotorizado = "";
                    VariablesDelivery.vDescMotorizado = "";
                }
                log.info("VariablesDelivery.vCodMotorizado>> " + VariablesDelivery.vCodMotorizado);
                log.info("VariablesDelivery.vDescMotorizado>> " + VariablesDelivery.vDescMotorizado);
            } else
                resulta = true;
        } else {
            resulta = true;
        }
        return resulta;
    }

    private void cargarListaTransferencias(String pNumPedVta, String pCodLocal) {
        try {
            DBDelivery.cargarListaDetTransferenciaPedido(tableModelTransferencia, pCodLocal, pNumPedVta);
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al cargar transferencias del pedido - \n" +
                    e, tblTransferencias);
            log.error("Error al cargar transferencias de pedido", e);
        }
    }

    private void tblListaUltimos_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(tblListaUltimos);
    }


    private void operaTransferencia() {
        VariablesTranfDelivery.vNumPedidoTransf =
                FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data,
                                                    tblListaUltimos.getSelectedRow(), 15);
        VariablesTranfDelivery.vSecGrupo =
                FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                    16);
        VariablesTranfDelivery.vCodLocalDestino =
                FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data,
                                                    tblListaUltimos.getSelectedRow(), 17);
        VariablesTranfDelivery.vFecPedidoTransf =
                FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data,
                                                    tblListaUltimos.getSelectedRow(), 18);
        VariablesTranfDelivery.vLocalDestino =
                FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data,
                                                    tblListaUltimos.getSelectedRow(), 19);
        VariablesTranfDelivery.vCodLocalDel =
                FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data,
                                                    tblListaUltimos.getSelectedRow(), 20);
        VariablesTranfDelivery.vSecTrans =
                FarmaUtility.getValueFieldArrayList(tableModelCabeceraUltimosPedidos.data, tblListaUltimos.getSelectedRow(),
                                                    21);

        DlgTransfDeliveryDetalle dlgTransfDeliveryDetalle = new DlgTransfDeliveryDetalle(myParentFrame, "", true);
        dlgTransfDeliveryDetalle.setVisible(true);
    }

    private void cargaLogin() {
        VariablesVentas.vListaProdFaltaCero = new ArrayList();
        VariablesVentas.vListaProdFaltaCero.clear();
        //limpiando variables de fidelizacion
        UtilityFidelizacion.setVariables();
        DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_VENDEDOR);
        dlgLogin.setVisible(true);
        if (FarmaVariables.vAceptar) {
            if (UtilityCaja.existeIpImpresora(this, null)) {
                if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) &&
                    !UtilityCaja.existeCajaUsuarioImpresora(this, null) ||
                    !UtilityCaja.validaFechaMovimientoCaja(this, null)) {
                    FarmaVariables.dlgLogin = dlgLogin;
                    cerrarVentana(false);
                } else {
                    FarmaVariables.dlgLogin = dlgLogin;
                    FarmaVariables.vAceptar = false;
                    actualizaListaPedidos();
                }
            } else {
                FarmaVariables.dlgLogin = dlgLogin;
                cerrarVentana(false);
            }
        } else
            cerrarVentana(false);
    }


    /**
     * @author RHERRERA
     * @since 2.4.5
     * @throws SQLException
     */
    private void recuperarPedido() {
        try {
            DBDelivery.recuperarPedidos();
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al cargar pedidos - \n" +
                    e, tblTransferencias);
        }
    }

    private void btnTransferenciasActionPerformed(ActionEvent e) {
        if (tblTransferencias.getRowCount() > 0) {
            FarmaUtility.moveFocusJTable(tblTransferencias);
        }
    }
}
