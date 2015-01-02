package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.DlgConsultaXCorrelativo;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgDatosLaboratorio.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCHAVEZ      06.07.2009   Creación <br>
 * <br>
 * @author Jenny Chavez<br>
 * @version 1.0<br>
 *
 */

public class DlgListaTicketsAnulados extends JDialog {

    FarmaTableModel tableModelCabeceraPedido;
    FarmaTableModel tableModelDetallePedido;
    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgAnularPedido.class);
    private boolean vAceptar = false;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JScrollPane srcDetallePedido = new JScrollPane();
    private JPanel pnlDetalle = new JPanel();
    private JButton btnDetalle = new JButton();
    private JScrollPane srcCabeceraPedido = new JScrollPane();
    private JPanel pnlCabecera = new JPanel();
    private JButton btnCabecera = new JButton();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JTextField txtMonto = new JTextField();
    private JLabel lblMonto = new JLabel();
    private JButton btnBuscar = new JButton();
    private JTextField txtCorrelativo = new JTextField();
    private JButton btnCorrelativo = new JButton();
    private JTable tblCabeceraPedido = new JTable();
    private JTable tblDetallePedido = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelWhite lblMensaje = new JLabelWhite();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private Object pObj = txtCorrelativo;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaTicketsAnulados() {
        this(null, "", false);
    }

    public DlgListaTicketsAnulados(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(792, 406));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Reimpresión de Tickets Anulados");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(513, 307));
        jContentPane.setBackground(Color.white);
        srcDetallePedido.setBounds(new Rectangle(10, 215, 770, 120));
        srcDetallePedido.setBackground(new Color(255, 130, 14));
        pnlDetalle.setBounds(new Rectangle(10, 195, 770, 20));
        pnlDetalle.setBackground(new Color(255, 130, 14));
        pnlDetalle.setLayout(null);
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
        btnDetalle.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnDetalle_keyPressed(e);
            }
        });
        btnDetalle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDetalle_actionPerformed(e);
            }
        });
        srcCabeceraPedido.setBounds(new Rectangle(10, 75, 770, 110));
        srcCabeceraPedido.setBackground(new Color(255, 130, 14));
        pnlCabecera.setBounds(new Rectangle(10, 55, 770, 20));
        pnlCabecera.setBackground(new Color(255, 130, 14));
        pnlCabecera.setLayout(null);
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
        btnCabecera.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnCabecera_keyPressed(e);
            }
        });
        btnCabecera.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCabecera_actionPerformed(e);
            }
        });
        pnlHeader.setBounds(new Rectangle(10, 10, 770, 35));
        txtMonto.setBounds(new Rectangle(295, 5, 80, 25));
        txtMonto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMonto_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtMonto_keyTyped(e);
            }
        });
        lblMonto.setText("Monto :");
        lblMonto.setBounds(new Rectangle(240, 5, 55, 25));
        lblMonto.setFont(new Font("SansSerif", 1, 11));
        lblMonto.setForeground(Color.white);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(385, 5, 90, 25));
        btnBuscar.setMnemonic('s');
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnBuscar_keyPressed(e);
            }
        });
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtCorrelativo.setBounds(new Rectangle(85, 5, 115, 25));
        txtCorrelativo.setFont(new Font("SansSerif", 0, 12));
        txtCorrelativo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtCorrelativo_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtCorrelativo_keyPressed(e);
            }
        });
        btnCorrelativo.setText("Correlativo :");
        btnCorrelativo.setBounds(new Rectangle(10, 10, 70, 15));
        btnCorrelativo.setMnemonic('r');
        btnCorrelativo.setBackground(new Color(255, 130, 14));
        btnCorrelativo.setBorderPainted(false);
        btnCorrelativo.setContentAreaFilled(false);
        btnCorrelativo.setDefaultCapable(false);
        btnCorrelativo.setFocusPainted(false);
        btnCorrelativo.setForeground(Color.white);
        btnCorrelativo.setHorizontalAlignment(SwingConstants.LEFT);
        btnCorrelativo.setFont(new Font("SansSerif", 1, 11));
        btnCorrelativo.setRequestFocusEnabled(false);
        btnCorrelativo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnCorrelativo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCorrelativo_actionPerformed(e);
            }
        });
        tblCabeceraPedido.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblCabeceraPedido_keyPressed(e);
            }
        });
        tblDetallePedido.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblDetallePedido_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(685, 345, 90, 20));
        lblF5.setText("[ F3 ] Reimprimir");
        lblF5.setBounds(new Rectangle(560, 345, 115, 20));
        lblMensaje.setBounds(new Rectangle(10, 345, 485, 30));
        lblMensaje.setForeground(Color.red);
        lblMensaje.setFont(new Font("Arial", 1, 15));
        jPanelWhite1.setBounds(new Rectangle(70, 20, 1, 1));
        srcDetallePedido.getViewport();
        srcCabeceraPedido.getViewport();
        pnlHeader.add(txtMonto, null);
        pnlHeader.add(lblMonto, null);
        pnlHeader.add(btnBuscar, null);
        pnlHeader.add(txtCorrelativo, null);
        pnlHeader.add(btnCorrelativo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        lblMensaje.add(jPanelWhite1, null);
        jContentPane.add(lblMensaje, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblEsc, null);
        srcDetallePedido.getViewport().add(tblDetallePedido, null);
        jContentPane.add(srcDetallePedido, null);
        pnlDetalle.add(btnDetalle, null);
        jContentPane.add(pnlDetalle, null);
        srcCabeceraPedido.getViewport().add(tblCabeceraPedido, null);
        jContentPane.add(srcCabeceraPedido, null);
        pnlCabecera.add(btnCabecera, null);
        jContentPane.add(pnlCabecera, null);
        jContentPane.add(pnlHeader, null);
        txtCorrelativo.setDocument(new FarmaLengthText(10));
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTableCabeceraPedido();
        initTableDetallePedido();
        FarmaVariables.vAceptar = false;
        lblMensaje.setText("");
        VariablesCaja.vDescripImpr = "";
        setearObjetoFocus();
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
            DBCaja.getListaCabeceraPedido(tableModelCabeceraPedido, txtCorrelativo.getText().trim());
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar los pedidos  - \n" +
                    e.getMessage(), pObj);
        }
    }

    private void initTableDetallePedido() {
        tableModelDetallePedido =
                new FarmaTableModel(ConstantsCaja.columnsListaProductos, ConstantsCaja.defaultValuesListaProductos, 0);
        FarmaUtility.initSimpleList(tblDetallePedido, tableModelDetallePedido, ConstantsCaja.columnsListaProductos);
    }

    private void cargaListaProductos() {
        try {
            DBCaja.getListaDetallePedidoAnulado(tableModelDetallePedido, txtCorrelativo.getText().trim(), "%", "%");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar los productos  - \n" +
                    e.getMessage(), pObj);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnCorrelativo_actionPerformed(ActionEvent e) {
        //JMIRANDA 22.08.2011
        if (UtilityVentas.getIndImprimeCorrelativo()) {
            txtCorrelativo.setEnabled(true);
            txtMonto.setEnabled(true);
            FarmaUtility.moveFocus(txtCorrelativo);
        } else {
            mostrarCorrelativoXComprobante();
        }
    }

    private void txtCorrelativo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtCorrelativo.setText(FarmaUtility.caracterIzquierda(txtCorrelativo.getText(), 10, "0"));
            FarmaUtility.moveFocus(txtMonto);
        } else
            chkKeyPressed(e);
    }

    private void txtCorrelativo_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCorrelativo, e);
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
        lblMensaje.setText("");
        if (validarCampos()) {
            if (buscarPedido()) {
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
            if (validarMostrarCorrelativo())
                FarmaUtility.moveFocus(txtCorrelativo);
            else
                FarmaUtility.moveFocusJTable(tblCabeceraPedido);
        } else
            chkKeyPressed(e);
    }

    private void btnCabecera_actionPerformed(ActionEvent e) {
        //FarmaUtility.moveFocus(btnCabecera);
        //JMIRANDA 22.08.2011
        if (UtilityVentas.getIndImprimeCorrelativo()) {
            FarmaUtility.moveFocus(tblCabeceraPedido);
        } else {
            FarmaUtility.moveFocusJTable(tblCabeceraPedido);
        }
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
        FarmaUtility.moveFocus(txtCorrelativo);
        cargaLogin();

        //Verifica si muestra Nueva pantalla
        if (!validarMostrarCorrelativo() && this.isVisible()) {
            mostrarCorrelativoXComprobante();
            FarmaUtility.moveFocus(tblCabeceraPedido);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", pObj);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (vAceptar) {
                if (tblCabeceraPedido.getRowCount() > 0) {
                    reimprimirPedidoAnulado();
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
        if (txtCorrelativo.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Correlativo.", txtCorrelativo);
            retorno = false;
        } else if (txtMonto.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Monto.", txtMonto);
            retorno = false;
        } else if (!FarmaUtility.validateDecimal(this, txtMonto, "Debe ingresar un Monto Válido.", false)) {
            retorno = false;
        }
        return retorno;
    }

    private boolean buscarPedido() {

        boolean retorno = false;
        vAceptar = false;
        try {
            log.debug("Antes de verificarPedido");
            DBCaja.verificaPedidoAnulado(txtCorrelativo.getText().trim(), txtMonto.getText().trim());
            log.debug("Despues de verificarpedido");
            log.debug("VariablesVirtual.vConProductoVirtual:" + VariablesVirtual.vConProductoVirtual);
            vAceptar = true;
            retorno = true;
            return retorno;

        } catch (SQLException e) {
            retorno = false;
            vAceptar = false;
            if (e.getErrorCode() == 20001) {
                FarmaUtility.showMessage(this,
                                         "No se encuentra ningun Pedido con estos datos. ¡No puede Reimprimir el ticket!",
                                         pObj);
            } else if (e.getErrorCode() == 20002) {
                FarmaUtility.showMessage(this, "El Pedido no ha sido cobrado. ¡No puede Reimprimir el ticket!", pObj);
            } else if (e.getErrorCode() == 20003) {
                FarmaUtility.showMessage(this, "El Pedido no ha sido anulado. ¡No puede Reimprimir el ticket!", pObj);
            } else {
                log.error("", e);
                FarmaUtility.showMessage(this, "Ocurrio un error al buscar un pedido  - \n" +
                        e.getMessage(), pObj);
            }

        }
        return retorno;
    }

    private void reimprimirPedidoAnulado() {
        cargarVariables();
        if (validaImprIP()) {
            obtieneImpresTicket();
            imprimeTicketAnulado();
        }
    }

    private boolean validaImprIP() {
        boolean valor = true;
        String tipComp = "";
        try {
            tipComp = VariablesCaja.vTipComp.trim();
            if (tipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)) {
                VariablesCaja.vSecImprLocalTicket =
                        DBCaja.getObtieneSecImpPorOrigen(FarmaVariables.vIpPc, tipComp, VariablesCaja.vNumPedVta_Anul);
                log.debug("VariablesCaja.vTipComp--> " + VariablesCaja.vTipComp);
                log.debug("VariablesCaja.vSecImprLocalTicket--> " + VariablesCaja.vSecImprLocalTicket);
                if (VariablesCaja.vSecImprLocalTicket.trim().equalsIgnoreCase("N")) {
                    FarmaUtility.showMessage(this, "No se cuenta con una impresora asignada de ticket. Verifique!!!",
                                             pObj);
                    valor = false;
                }
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al validar relacion IP - impresora. Verifique!!!", pObj);
            valor = false;
        }
        return valor;
    }

    private void cargarVariables() {
        VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim();
        VariablesCaja.vTipComp_Anul = "%";
        VariablesCaja.vNumComp_Anul = "%";
        VariablesCaja.vMonto_Anul = txtMonto.getText().trim();
        /*
        VariablesCaja.vTipComp = tblCabeceraPedido.getValueAt(0,7).toString();
        VariablesVentas.vTip_Ped_Vta = FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,8);
        VariablesCaja.vIndDeliveryAutomatico = FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,9);
        VariablesCaja.vIndPedFidelizado= FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,10);
        VariablesCaja.vDniCli=FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,11);
        */
        VariablesCaja.vTipComp = FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data, 0, 7);
        VariablesVentas.vTip_Ped_Vta = FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data, 0, 8);
        VariablesCaja.vIndDeliveryAutomatico =
                FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data, 0, 9);
        VariablesCaja.vIndPedFidelizado = FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data, 0, 10);
        VariablesCaja.vDniCli = FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data, 0, 11);

        log.debug("tipo de pedido : " + VariablesVentas.vTip_Ped_Vta);
    }

    private void obtieneImpresTicket() {
        log.debug("JCORTEZ: =VariablesCaja.vTipComp--> " + VariablesCaja.vTipComp);
        if (VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)) {
            try {
                String DescImpr = "";
                String result =
                    DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc, ConstantsVentas.TIPO_COMP_TICKET, "N");
                log.debug("a");

                String result2 = DBCaja.getObtieneTipoCompPorIP(FarmaVariables.vIpPc, VariablesCaja.vTipComp);


                log.debug("b");
                //verifica que el secuencial exista, caso contrario, se validara la asignada y se mostrara
                //que no tiene niguna asignacion si es el caso. 16.06.09
                String exist = "";
                exist = DBCaja.getExistSecImp(VariablesCaja.vSecImprLocalTicket, FarmaVariables.vIpPc);
                log.debug("c");
                log.debug("JCORTEZ: Secuencia Impr--> " + result);
                if (exist.trim().equalsIgnoreCase("2")) {
                    log.debug("JCORTEZ:SecImp por IP--> " + result);
                    VariablesCaja.vSecImprLocalTicket = result;
                } else if (exist.equalsIgnoreCase("X")) {
                    VariablesCaja.vSecImprLocalTicket = exist.trim();
                } else if (exist.equalsIgnoreCase("1")) {
                    log.debug("JCORTEZ: Se encontro impresora origen--> " + exist);
                } else {
                    log.debug("JCORTEZ: Sec disponible--> " + exist);
                    VariablesCaja.vSecImprLocalTicket = exist.trim();
                }
                if (!VariablesCaja.vSecImprLocalTicket.equalsIgnoreCase("X")) {
                    DescImpr = DBCaja.getNombreImpresora(VariablesCaja.vSecImprLocalTicket);
                    VariablesCaja.vDescripImpr = DescImpr;
                } else {
                    VariablesCaja.vDescripImpr = "X";
                }
            } catch (SQLException sql) {
                log.error("", sql);
            }
        }
    }

    private boolean cargaLoginAdmLocal() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
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


    private void imprimeTicketAnulado() {
        String caja_turno;
        try {

            caja_turno = DBCaja.obtieneCajaTurnoPedidoAnulado(VariablesCaja.vNumPedVta_Anul);
            String[] arrayDatos = caja_turno.split("Ã");
            boolean pRes1 = false, pRes2 = false;

            pRes1 =
                    UtilityCaja.imprimeMensajeTicketAnulacion(arrayDatos[0], arrayDatos[1], VariablesCaja.vNumPedVta_Anul,
                                                              "00", "S", "");
            //para montos inafectos
            pRes2 =
                    UtilityCaja.imprimeMensajeTicketAnulacion(arrayDatos[0], arrayDatos[1], VariablesCaja.vNumPedVta_Anul,
                                                              "01", "S", "");

            //Esto ya no VA
            //UtilityCaja.imprimeMensajeTicketAnulacion(arrayDatos[0],arrayDatos[1],VariablesCaja.vNumPedVta_Anul,"00",ruta+"_1.TXT",true,"N");
            //para montos inafectos
            //UtilityCaja.imprimeMensajeTicketAnulacion(arrayDatos[0],arrayDatos[1],VariablesCaja.vNumPedVta_Anul,"01",ruta+"_2.TXT",true,"N");
            lblMensaje.setText("");
            if (pRes1 || pRes2) {
                if (VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)) {
                    if (!VariablesCaja.vDescripImpr.equalsIgnoreCase("X") ||
                        VariablesCaja.vDescripImpr.equalsIgnoreCase(""))
                        lblMensaje.setText("Recoger comprobante en : " + VariablesCaja.vDescripImpr);
                }
                FarmaUtility.showMessage(this, "El ticket se ha reimpreso con éxito .", pObj);
            } else {
                FarmaUtility.showMessage(this, "No se encontró el pedido para imprimir.", pObj);
                log.info("No se obtuvo resultados para imprimir..");
            }

        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                    //"dubilluz",
                    VariablesPtoVenta.vDestEmailErrorAnulacion, "Error de Impresión Ticket Anulado",
                    "Error de Impresión",
                    "Error al imprimir ticket Anulado :<br>" + "Correlativo : " + VariablesCaja.vNumPedVta_Anul +
                    "<br>" + "IP PC: " + FarmaVariables.vIpPc + "<br>" + //JMIRANDA 30/07/09
                    "Error: " + e,
                    //"joliva;operador;daubilluz@gmail.com");
                    "");
            log.info("Error Imprimir Ticket Anulado : " + e);
            FarmaUtility.showMessage(this, "Error al reimprimir el comprobante Anulado.", pObj);
        }
    }

    /**
     * Valida si se muestra la nueva versión para Imprimir o
     * no Imprimir Correlativo, así como usar pantalla para Ingresar Numero Comprobante y Monto Neto si es Negativo
     * @author JMIRANDA
     * @since 22.08.2011
     * @return true si imprime correlativo
     */
    private boolean validarMostrarCorrelativo() {
        boolean flag = true;
        //si la validacion siguiente es falsa no imprime y debe ingresar nro comprobante
        if (!UtilityVentas.getIndImprimeCorrelativo()) {
            txtCorrelativo.setEnabled(false);
            txtMonto.setEnabled(false);
            flag = false;
        }
        return flag;
    }

    private void mostrarCorrelativoXComprobante() {
        DlgConsultaXCorrelativo dlgConsulta = new DlgConsultaXCorrelativo(myParentFrame, "", true);
        dlgConsulta.setVisible(true);

        if (FarmaVariables.vAceptar) {
            txtCorrelativo.setText(VariablesVentas.vNumPedVta_new);
            txtMonto.setText(VariablesVentas.vMontoNeto_new);
            btnBuscar.doClick();
            FarmaUtility.moveFocusJTable(tblCabeceraPedido);
        } else
            FarmaUtility.moveFocusJTable(tblCabeceraPedido);
    }

    private void tblCabeceraPedido_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblDetallePedido_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    //JMIRANDA 25.08.2011 Setear el Objeto para enfocar después de los mensajes.

    private void setearObjetoFocus() {
        //JMIRANDA 25.08.2011 verificar si se utiliza funcionalidad nueva
        if (validarMostrarCorrelativo())
            pObj = txtCorrelativo;
        else
            pObj = tblCabeceraPedido;
    }
}
