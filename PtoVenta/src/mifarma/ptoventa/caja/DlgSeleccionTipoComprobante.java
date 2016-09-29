package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;

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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.cliente.DlgBuscaClienteJuridico;
import mifarma.ptoventa.cliente.modelo.BeanClienteJuridico;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.cliente.reference.DBCliente;
import mifarma.ptoventa.cliente.reference.VariablesCliente;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgSeleccionTipoComprobante.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      21.02.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgSeleccionTipoComprobante extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgSeleccionTipoComprobante.class);
    private Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    //private JLabelFunction lblF2 = new JLabelFunction();
    private JPanel jPanel3 = new JPanel();
    private JTextField txtRUC = new JTextField();
    private JTextField txtDireccionCliente = new JTextField();
    private JTextField txtCliente = new JTextField();
    private JPanel jPanel2 = new JPanel();
    private JButton btnBuscar = new JButton();
    private JTextField txtRazonSocial_Ruc = new JTextField();
    private JButton btnRazonSocial = new JButton();
    private JPanel jPanel1 = new JPanel();
    private JRadioButton rbtFactura = new JRadioButton();
    private JRadioButton rbtBoleta = new JRadioButton();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnCliente = new JButtonLabel();
    private JButtonLabel btnRuc = new JButtonLabel();
    private JButtonLabel btnDireccion = new JButtonLabel();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();

    //KMONCADA 20.11.2014 EN CASO DE CONVENIO NO SE PODRA MODIFICAR EL TIPO DE COMPROBANTE
    private boolean isActivaF2 = true;
    private String nroDocumentoCliente;
    private String nombreCliente;
    private String direccionCliente;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgSeleccionTipoComprobante() {
        this(null, "", false);
    }

    public DlgSeleccionTipoComprobante(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(410, 320));
        this.getContentPane().setLayout(borderLayout1);
        //30.12.2013 GFONSECA. Se cambia el titulo
        //this.setTitle("Seleccion de Comprobante");
        this.setTitle("Registro de Cliente");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(407, 244));
        jContentPane.setBackground(Color.white);
        lblF2.setText("[ F2 ] Cambiar Comprobante");
        lblF2.setBounds(new Rectangle(235, 245, 165, 20));
        jPanel3.setBounds(new Rectangle(15, 95, 375, 115));
        jPanel3.setBorder(BorderFactory.createTitledBorder(""));
        jPanel3.setBackground(Color.white);
        jPanel3.setLayout(null);
        txtRUC.setBounds(new Rectangle(85, 45, 160, 20));
        txtRUC.setFont(new Font("SansSerif", 0, 12));
        txtRUC.setForeground(new Color(43, 141, 39));
        //txtRUC.setEnabled(false);
        txtRUC.setEnabled(true);
        txtRUC.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtRUC_keyPressed(e);
            }
        });
        txtDireccionCliente.setBounds(new Rectangle(85, 75, 260, 20));
        txtDireccionCliente.setFont(new Font("SansSerif", 0, 12));
        txtDireccionCliente.setForeground(new Color(43, 141, 39));
        txtDireccionCliente.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDireccionCliente_keyPressed(e);
            }
        });
        txtCliente.setBounds(new Rectangle(85, 15, 260, 20));
        txtCliente.setFont(new Font("SansSerif", 0, 12));
        txtCliente.setForeground(new Color(43, 141, 39));
        txtCliente.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCliente_keyPressed(e);
            }
        });
        jPanel2.setBounds(new Rectangle(15, 55, 375, 35));
        jPanel2.setBackground(new Color(255, 130, 14));
        jPanel2.setLayout(null);
        jPanel2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanel2.setFocusable(false);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(280, 5, 90, 25));
        btnBuscar.setMnemonic('s');
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusPainted(false);
        txtRazonSocial_Ruc.setBounds(new Rectangle(120, 5, 150, 25));
        txtRazonSocial_Ruc.setFont(new Font("SansSerif", 0, 12));
        txtRazonSocial_Ruc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtRazonSocial_keyPressed(e);
            }
        });
        btnRazonSocial.setText("Razon Social / RUC :");
        btnRazonSocial.setBounds(new Rectangle(5, 10, 115, 15));
        btnRazonSocial.setMnemonic('r');
        btnRazonSocial.setBackground(new Color(255, 130, 14));
        btnRazonSocial.setBorderPainted(false);
        btnRazonSocial.setContentAreaFilled(false);
        btnRazonSocial.setDefaultCapable(false);
        btnRazonSocial.setFocusPainted(false);
        btnRazonSocial.setForeground(Color.white);
        btnRazonSocial.setHorizontalAlignment(SwingConstants.LEFT);
        btnRazonSocial.setFont(new Font("SansSerif", 1, 11));
        btnRazonSocial.setRequestFocusEnabled(false);
        btnRazonSocial.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnRazonSocial.setFocusable(false);
        btnRazonSocial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRazonSocial_actionPerformed(e);
            }
        });
        jPanel1.setBounds(new Rectangle(15, 15, 375, 35));
        jPanel1.setBorder(BorderFactory.createTitledBorder(""));
        jPanel1.setBackground(Color.white);
        jPanel1.setLayout(null);
        rbtFactura.setText("FACTURA");
        rbtFactura.setBounds(new Rectangle(195, 5, 150, 25));
        rbtFactura.setBackground(Color.white);
        rbtFactura.setFont(new Font("SansSerif", 1, 14));
        rbtFactura.setForeground(new Color(43, 141, 39));
        rbtFactura.setFocusPainted(false);
        rbtFactura.setRequestFocusEnabled(false);
        rbtFactura.setFocusable(false);
        rbtFactura.setSelected(true);
        rbtBoleta.setText("BOLETA");
        rbtBoleta.setBounds(new Rectangle(35, 5, 135, 25));
        rbtBoleta.setBackground(Color.white);
        rbtBoleta.setFont(new Font("SansSerif", 1, 14));
        rbtBoleta.setForeground(new Color(43, 141, 39));
        rbtBoleta.setFocusPainted(false);
        rbtBoleta.setRequestFocusEnabled(false);
        rbtBoleta.setFocusable(false);
        rbtBoleta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rbtBoleta_actionPerformed(e);
            }
        });
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(20, 225, 100, 20));
        lblF11.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(305, 225, 85, 20));
        lblEsc.setFocusable(false);
        btnCliente.setText("Cliente :");
        btnCliente.setBounds(new Rectangle(10, 15, 65, 20));
        btnCliente.setForeground(new Color(43, 141, 39));
        btnCliente.setMnemonic('c');
        btnCliente.setFocusable(false);
        btnCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCliente_actionPerformed(e);
            }
        });
        btnRuc.setText("R.U.C /  DNI  :");
        btnRuc.setBounds(new Rectangle(10, 50, 75, 15));
        btnRuc.setForeground(new Color(43, 141, 39));
        btnRuc.setFocusable(false);
        btnDireccion.setText("Direccion :");
        btnDireccion.setBounds(new Rectangle(10, 80, 65, 15));
        btnDireccion.setForeground(new Color(43, 141, 39));
        btnDireccion.setMnemonic('d');
        btnDireccion.setFocusable(false);
        btnDireccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDireccion_actionPerformed(e);
            }
        });
        //GFONSECA 30.12.2013 Se deshabilita esta opcion
        lblF2.setText("[ F2 ] Cambiar Comprobante");
        lblF2.setBounds(new Rectangle(130, 250, 165, 20)); //(235, 245, 165, 20));
        lblF2.setFocusable(false);
        lblEnter.setText("[ ENTER ] Buscar Cliente");
        lblEnter.setBounds(new Rectangle(130, 225, 165, 20));
        lblEnter.setFocusable(false);
        jPanel3.add(btnDireccion, null);
        jPanel3.add(btnRuc, null);
        jPanel3.add(btnCliente, null);
        jPanel3.add(txtRUC, null);
        jPanel3.add(txtDireccionCliente, null);
        jPanel3.add(txtCliente, null);
        jPanel2.add(btnBuscar, null);
        jPanel2.add(txtRazonSocial_Ruc, null);
        jPanel2.add(btnRazonSocial, null);
        jPanel1.add(rbtFactura, null);
        jPanel1.add(rbtBoleta, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblF2, null);
        //jContentPane.add(lblF2, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(jPanel3, null);
        jContentPane.add(jPanel2, null);
        jContentPane.add(jPanel1, null);
        //this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        seleccionTipoComprobante();
        FarmaVariables.vAceptar = false;
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void seleccionTipoComprobante() {
        if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) ||
            VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET_FACT)) {
            rbtBoleta.setSelected(false);
            rbtFactura.setSelected(true);
        } else {
            rbtBoleta.setSelected(true);
            rbtFactura.setSelected(false);
        }
        //limpiaDatosCliente();
        colocaInfoInicialCliente();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        limpiaDatosCliente();
        bloqueaDatosCliente();
        //Se colocará el texto correspondiente al IP de la pc
        //si emite boleta o ticket
        //dubilluz 21.07.2009
        //inicio
        log.info("Err-" + VariablesVentas.vTip_Comp_Ped);
        String pTipoBoletaTicketPC = setTipoComprobante(ConstantsVentas.TIPO_COMP_BOLETA);
        String pTipoFacturaPC = setTipoComprobante(ConstantsVentas.TIPO_COMP_FACTURA);

        //si el tipo de comprobante configurado para la IP es una boleta, se mostrara dicho texto
        if (pTipoBoletaTicketPC.trim().equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)) {
            rbtBoleta.setText("BOLETA");
        }
        //si el tipo de comprobante configurado para la IP es un ticket, se mostrara dicho texto
        else if (pTipoBoletaTicketPC.trim().equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)) {
            rbtBoleta.setText("TICKET BOLETA");
        }
        //fin

        //LLEIVA 29-Ene-2014 Se añadio el renombramiento del label de facturas
        if (ConstantsVentas.TIPO_COMP_FACTURA.equalsIgnoreCase(pTipoFacturaPC.trim())) {
            rbtFactura.setText("FACTURA");
        }
        //si el tipo de comprobante configurado para la IP es un ticket, se mostrara dicho texto
        else if (ConstantsVentas.TIPO_COMP_TICKET_FACT.equalsIgnoreCase(pTipoFacturaPC.trim())) {
            rbtFactura.setText("TICKET FACTURA");
        }
        //fin

        //rbtBoleta.setText(pTipoBoletaTicketPC.get(1).toString());
        //rbtFactura.setText(pTipoFacturaPC.get(1).toString());

        /**
         * Si es tipo multifuncional
         * @author dubilluz
         * @since  30.04.2008
         */
        if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL)) {
            log.debug("JCORTEZ: VariablesVentas.vTip_Comp_Ped--> " + VariablesVentas.vTip_Comp_Ped);
            if (VariablesCaja.vNumPedVta.trim().length() == 0) {
                if (!VariablesVentas.vIndObligaDatosCliente) {
                    //por ahora se contara como TICKETERA
                    if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA) ||
                        VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)) {
                        guardaValoresCliente();
                        if (!validaDatosCliente())
                            return;
                        cerrarVentana(true);
                    }
                } else {
                    txtRUC.setEnabled(true);
                }
            }
        }
        colocaDatosFidelizado();
    }

    private void txtRazonSocial_keyPressed(KeyEvent e) {
        validaTextoIngresado(e);
        chkKeyPressed(e);
    }

    private void txtCliente_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtCliente.setText(txtCliente.getText().trim().toUpperCase());
            log.debug("--" + txtRUC.isEditable());
            if (txtRUC.isEditable()) {
                FarmaUtility.moveFocus(txtRUC);
            } else {
                FarmaUtility.moveFocus(txtDireccionCliente);
            }
        }
        chkKeyPressed(e);
    }

    private void txtRUC_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtRUC.getText().trim().length() > 0) {
                isValido(txtRUC, txtDireccionCliente);
            } else {
                FarmaUtility.moveFocus(txtDireccionCliente);
            }
        }
        chkKeyPressed(e);
    }

    private void isValido(Object obj, Object objF) {
        double vValor = 0.0;
        try {
            vValor = Integer.parseInt(((JTextField)obj).getText().trim());
            FarmaUtility.moveFocus(objF);
        } catch (Exception e) {
            FarmaUtility.showMessage(this, "Ingrese un Valor Correcto!!!.", obj);
        }
    }

    private void txtDireccionCliente_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtDireccionCliente.setText(txtDireccionCliente.getText().trim().toUpperCase());
            FarmaUtility.moveFocus(txtCliente);
        }
        chkKeyPressed(e);
    }

    private void btnRazonSocial_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtRazonSocial_Ruc);
    }

    private void btnCliente_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCliente);
    }

    private void btnDireccion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDireccionCliente);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F2(e) && lblF2.isVisible()) {
            cambiaTipoComprobante();
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            guardaValoresCliente();
            if (!validaDatosCliente())
                return;
            //JMIRANDA 11/08/09
            try {
                DBVentas.actualizarCabeceraPedido();
            } catch (SQLException sqlEx) {
                log.debug("Ocurrio error al Actualizar Cabecera Pedido: " + sqlEx);
            }
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    public String setTipoComprobante(String pTipComp) {
        String vTipCompNew = "";
        try {
            vTipCompNew = DBCaja.getObtieneTipoCompPorIP(FarmaVariables.vIpPc, pTipComp);
            return vTipCompNew.trim();
        } catch (SQLException e) {
            log.error("", e);
            vTipCompNew = "01"; //defecto boleta
        } finally {
            return vTipCompNew.trim();
        }
        //return vTipCompNew.trim();
    }

    private void cambiaTipoComprobante() {
        if (rbtBoleta.isSelected()) {
            rbtBoleta.setSelected(false);
            rbtFactura.setSelected(true);
            VariablesVentas.vTip_Comp_Ped = ConstantsVentas.TIPO_COMP_FACTURA;
        } else if (rbtFactura.isSelected()) {
            rbtFactura.setSelected(false);
            rbtBoleta.setSelected(true);
            //VariablesVentas.vTip_Comp_Ped = ConstantsVentas.TIPO_COMP_BOLETA;
            VariablesVentas.vTip_Comp_Ped = setTipoComprobante(ConstantsVentas.TIPO_COMP_BOLETA);
        }
        limpiaDatosCliente();
        bloqueaDatosCliente();

        /**
        * @dubilluz
        * @since  15.05.2009
        */
        if (rbtBoleta.isSelected()) {
            colocaDatosFidelizado();
        }
    }

    private void validaTextoIngresado(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtRazonSocial_Ruc.setText(txtRazonSocial_Ruc.getText().trim().toUpperCase());
            String textoBusqueda = txtRazonSocial_Ruc.getText().trim();
            if (textoBusqueda.length() >= 3) {
                boolean isNumero = true;
                int k=0;
                while(isNumero && k<textoBusqueda.length()){
                    isNumero = isNumero && Character.isDigit(textoBusqueda.charAt(k));
                    k++;
                }
                if(isNumero){
                    if(ConstantsCliente.RESULTADO_RUC_VALIDO.equalsIgnoreCase(DBCliente.verificaRucValido2(textoBusqueda))){
                        log.info("[SELECCION DE TIPO COMPROBANTE]: RUC VALIDO");
                        VariablesCliente.vRuc = textoBusqueda;
                        buscaClienteJuridico(ConstantsCliente.TIPO_BUSQUEDA_RUC, textoBusqueda);
                    }else{
                        log.info("[SELECCION DE TIPO COMPROBANTE]: RUC INVALIDO - SE BUSCARA COMO RAZON SOCIAL");
                        buscaClienteJuridico(ConstantsCliente.TIPO_BUSQUEDA_RAZSOC, textoBusqueda);
                    }
                }else{
                    log.info("[SELECCION DE TIPO COMPROBANTE]: SE BUSCARA COMO RAZON SOCIAL");
                    buscaClienteJuridico(ConstantsCliente.TIPO_BUSQUEDA_RAZSOC, textoBusqueda);
                }
                /*
                char primerkeyChar = textoBusqueda.charAt(0);
                char ultimokeyChar = textoBusqueda.charAt(textoBusqueda.length() - 1);

                // Si el primer y último character son numeros asumimos que es RUC
                if (!Character.isLetter(primerkeyChar) && !Character.isLetter(ultimokeyChar) && textoBusqueda.length() > 10) {
                    VariablesCliente.vRuc = textoBusqueda;
                    log.debug("RUC  : " + VariablesCliente.vRuc);
                    buscaClienteJuridico(ConstantsCliente.TIPO_BUSQUEDA_RUC, textoBusqueda);
                } else
                    buscaClienteJuridico(ConstantsCliente.TIPO_BUSQUEDA_RAZSOC, textoBusqueda);
                */
            } else
                FarmaUtility.showMessage(this, "Ingrese 3 caracteres como minimo para realizar la busqueda",
                                         txtRazonSocial_Ruc);
        }
    }

    private void buscaClienteJuridico(String pTipoBusqueda, String pBusqueda) {
        VariablesCliente.vTipoBusqueda = pTipoBusqueda;
        VariablesCliente.vRuc_RazSoc_Busqueda = pBusqueda;
        VariablesCliente.vIndicadorCargaCliente = FarmaConstants.INDICADOR_S;

        DlgBuscaClienteJuridico dlgBuscaClienteJuridico = new DlgBuscaClienteJuridico(myParentFrame, "", true);
        dlgBuscaClienteJuridico.setVisible(true);
        if (FarmaVariables.vAceptar) {
            if (VariablesCliente.vArrayList_Cliente_Juridico.size() == 1) {
                VariablesVentas.vCod_Cli_Local = ((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(1)).trim();
                txtRUC.setText(((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(1)).trim());
                txtCliente.setText(((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(2)).trim());
                txtDireccionCliente.setText(((String)((ArrayList)VariablesCliente.vArrayList_Cliente_Juridico.get(0)).get(3)).trim());
            }
            FarmaVariables.vAceptar = false;
        }
    }

    private void guardaValoresCliente() {
        VariablesVentas.vRuc_Cli_Ped = txtRUC.getText().trim().toUpperCase();
        VariablesVentas.vNom_Cli_Ped = txtCliente.getText().trim().toUpperCase();
        VariablesVentas.vDir_Cli_Ped = txtDireccionCliente.getText().trim().toUpperCase();
    }

    private boolean validaDatosCliente() {
        if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA)) {
            if (VariablesVentas.vCod_Cli_Local.equalsIgnoreCase("")) {
                FarmaUtility.showMessage(this, "Falta Codigo del Cliente.Verifique!!!", txtCliente);
                return false;
            }
            if (VariablesVentas.vRuc_Cli_Ped.equalsIgnoreCase("")) {
                FarmaUtility.showMessage(this, "Falta RUC del Cliente.Verifique!!!", txtCliente);
                return false;
            }
            if (VariablesVentas.vNom_Cli_Ped.equalsIgnoreCase("")) {
                FarmaUtility.showMessage(this, "Falta Razon Social del Cliente.Verifique!!!", txtCliente);
                return false;
            }
        } else {
            // KMONCADA 19.11.2014 EN CASO DE BOLETAS SOLICITARA LOS DATOS
            if ("".equalsIgnoreCase(VariablesVentas.vRuc_Cli_Ped.trim())) {
                FarmaUtility.showMessage(this, "Falta DNI del Cliente.Verifique!!!", txtCliente);
                return false;
            }
            if ("".equalsIgnoreCase(VariablesVentas.vNom_Cli_Ped.trim())) {
                FarmaUtility.showMessage(this, "Falta NOMBRE del Cliente.Verifique!!!", txtCliente);
                return false;
            }
        }

        return true;
    }

    private void limpiaDatosCliente() {
        /*txtRUC.setText("");
        txtCliente.setText("");
        txtDireccionCliente.setText("");*/
        VariablesVentas.vRuc_Cli_Ped = "";
        VariablesVentas.vNom_Cli_Ped = "";
        VariablesVentas.vDir_Cli_Ped = "";
        VariablesVentas.vCod_Cli_Local = "";

        txtRUC.setText(nroDocumentoCliente);
        txtCliente.setText(nombreCliente);
        txtDireccionCliente.setText(direccionCliente);


        /// KMONCADA 20.11.2014
        lblF2.setVisible(isActivaF2);
    }

    private void bloqueaDatosCliente() {
        if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) ||
            VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET_FACT)) {
            txtRazonSocial_Ruc.setEnabled(true);
            txtCliente.setEnabled(false);
            txtDireccionCliente.setEnabled(false);
            FarmaUtility.moveFocus(txtRazonSocial_Ruc);
        } else {
            txtRazonSocial_Ruc.setEnabled(false);
            txtCliente.setEnabled(true);
            txtDireccionCliente.setEnabled(true);
            FarmaUtility.moveFocus(txtCliente);
        }
    }

    private void colocaInfoInicialCliente() {
        txtRUC.setText(VariablesVentas.vRuc_Cli_Ped);
        txtCliente.setText(VariablesVentas.vNom_Cli_Ped);
        txtDireccionCliente.setText(VariablesVentas.vDir_Cli_Ped);
    }

    /**
     * Coloca los datos del cliente Fidelizado
     * @author Dubilluz
     * @since  15.05.2009
     */
    private void colocaDatosFidelizado() {
        if (rbtBoleta.isSelected()) {
            //Coloca los datos del cliente.
            //dubilluz 15.05.2009
            String cliente = "", dni = "", direccion = "";
            cliente =
                    cliente + " " + ((VariablesFidelizacion.vNomCliente.trim().length() == 0 || VariablesFidelizacion.vNomCliente.trim().equalsIgnoreCase("N")) ?
                                     "" : VariablesFidelizacion.vNomCliente.trim());
            //KMONCADA 16.04.2015 SE MODIFICA EL ORDEN ERRADO DE UNIR LOS APELLIDOS PATERNO Y MATERNO
            cliente =
                    cliente + " " + ((VariablesFidelizacion.vApePatCliente.trim().length() == 0 || VariablesFidelizacion.vApePatCliente.trim().equalsIgnoreCase("N")) ?
                                     "" : VariablesFidelizacion.vApePatCliente.trim());
            cliente =
                    cliente + " " + ((VariablesFidelizacion.vApeMatCliente.trim().length() == 0 || VariablesFidelizacion.vApeMatCliente.trim().equalsIgnoreCase("N")) ?
                                     "" : VariablesFidelizacion.vApeMatCliente.trim());
            dni =
(VariablesFidelizacion.vDniCliente.trim().length() == 0 || VariablesFidelizacion.vDniCliente.trim().equalsIgnoreCase("N")) ?
"" : VariablesFidelizacion.vDniCliente.trim();
            direccion =
                    (VariablesFidelizacion.vDireccion.trim().length() == 0 || VariablesFidelizacion.vDireccion.trim().equalsIgnoreCase("N")) ?
                    "" : VariablesFidelizacion.vDireccion.trim();
            if (cliente.trim().length() > 0) {
                txtCliente.setText(cliente);
            }
            if (dni.trim().length() > 0) {
                txtRUC.setText(dni);
            }
            if (direccion.trim().length() > 0) {
                txtDireccionCliente.setText(direccion);
            }
            /*txtCliente.setText((cliente.trim().length()>0)?cliente.trim():"");
      txtRUC.setText((dni.trim().length()>0)?dni.trim():"");
      txtDireccionCliente.setText((direccion.trim().length()>0)?direccion.trim():"");*/
            // Fin
        }
    }

    private void rbtBoleta_actionPerformed(ActionEvent e) {
    }

    //KMONCADA 20.11.2014 INDICADOR SI ACTIVA OPCION DE CAMBIO DE TIPO DE DOCUMENTO

    public void setIsActivaF2(boolean isActivaF2) {
        this.isActivaF2 = isActivaF2;
    }

    public void setNroDocumentoCliente(String nroDocumentoCliente) {
        this.nroDocumentoCliente = nroDocumentoCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }
}
