package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.fondoSencillo.reference.DBFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.UtilityFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.VariablesFondoSencillo;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMovCaja extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgMovCaja.class);

    Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanel jPanel2 = new JPanel();
    private JLabel lblNumTurno = new JLabel();
    private JLabel lblFecha = new JLabel();
    private JLabel lblNumCaja = new JLabel();
    private JLabel lblUsuario = new JLabel();
    private JLabel lblTurno_T = new JLabel();
    private JLabel lblFecha_T = new JLabel();

    private JLabel lblUsuario_T = new JLabel();
    private JPanel pnlHeaderDatos = new JPanel();
    private JLabel lblBoleta = new JLabel();
    private JLabel lblFactura = new JLabel();
    private JTextFieldSanSerif txtBoleta = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFactura = new JTextFieldSanSerif();
    private JLabel lblDatosUser_T = new JLabel();
    private JLabel lblDiaVenta = new JLabel();
    private JLabel lblDiaVenta_T = new JLabel();
    private JComboBox cmbSerieBoleta = new JComboBox();
    private JComboBox cmbSerieFactura = new JComboBox();

    //JCORTEZ 15.04.09
    private boolean bValidaCompr = false;
    private JButtonLabel lblCaja_TT = new JButtonLabel();

    //JCORTEZ 18.05.09
    private static String vTipMovCajaAux = "";
    private JLabel lblMensajeCajero = new JLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMovCaja() {
        this(null, "", false);
    }

    public DlgMovCaja(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(385, 308));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Movimiento de Caja");
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        jContentPane.setBounds(new Rectangle(5, 5, 380, 255));
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(382, 195));
        jContentPane.setLayout(null);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(275, 250, 85, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(160, 250, 100, 20));
        jPanel2.setBounds(new Rectangle(20, 45, 340, 195));
        jPanel2.setBackground(Color.white);
        jPanel2.setBorder(BorderFactory.createTitledBorder(""));
        jPanel2.setLayout(null);
        lblNumTurno.setText("1");
        lblNumTurno.setBounds(new Rectangle(225, 40, 35, 20));
        lblNumTurno.setFont(new Font("SansSerif", 1, 12));
        lblNumTurno.setForeground(new Color(43, 141, 39));
        lblFecha.setText("12/01/2006 10:15:50");
        lblFecha.setBounds(new Rectangle(130, 75, 175, 15));
        lblFecha.setFont(new Font("SansSerif", 1, 12));
        lblFecha.setForeground(new Color(43, 141, 39));
        lblNumCaja.setText("1");
        lblNumCaja.setBounds(new Rectangle(85, 45, 40, 15));
        lblNumCaja.setFont(new Font("SansSerif", 1, 12));
        lblNumCaja.setForeground(new Color(43, 141, 39));
        lblUsuario.setText("Andres Moreno");
        lblUsuario.setBounds(new Rectangle(85, 15, 245, 20));
        lblUsuario.setFont(new Font("SansSerif", 1, 12));
        lblUsuario.setForeground(new Color(43, 141, 39));
        lblTurno_T.setText("Turno :");
        lblTurno_T.setBounds(new Rectangle(155, 45, 50, 15));
        lblTurno_T.setFont(new Font("SansSerif", 1, 12));
        lblTurno_T.setForeground(new Color(255, 130, 14));
        lblFecha_T.setText("Fecha de  :");
        lblFecha_T.setBounds(new Rectangle(15, 75, 145, 15));
        lblFecha_T.setFont(new Font("SansSerif", 1, 12));
        lblFecha_T.setForeground(new Color(255, 130, 14));
        lblUsuario_T.setText("Usuario :");
        lblUsuario_T.setBounds(new Rectangle(15, 15, 60, 20));
        lblUsuario_T.setFont(new Font("SansSerif", 1, 12));
        lblUsuario_T.setForeground(new Color(255, 130, 14));
        pnlHeaderDatos.setBounds(new Rectangle(20, 5, 340, 20));
        pnlHeaderDatos.setBackground(new Color(255, 130, 14));
        pnlHeaderDatos.setLayout(null);
        lblDatosUser_T.setText("Datos de Usuario y Caja");
        lblDatosUser_T.setBounds(new Rectangle(10, 0, 160, 20));
        lblDatosUser_T.setFont(new Font("SansSerif", 1, 11));
        lblDatosUser_T.setForeground(Color.white);
        lblDiaVenta.setText("12/01/2006");
        lblDiaVenta.setBounds(new Rectangle(130, 100, 105, 15));
        lblDiaVenta.setFont(new Font("SansSerif", 1, 12));
        lblDiaVenta.setForeground(new Color(43, 141, 39));
        lblDiaVenta_T.setText("Dia de Venta :");
        lblDiaVenta_T.setBounds(new Rectangle(15, 100, 90, 15));
        lblDiaVenta_T.setFont(new Font("SansSerif", 1, 12));
        lblDiaVenta_T.setForeground(new Color(255, 130, 14));

        // lblTurno_T.setForeground(new Color(255, 130, 14));

        lblBoleta.setText("Boleta :");
        lblBoleta.setBounds(new Rectangle(15, 135, 80, 15));
        lblBoleta.setFont(new Font("SansSerif", 1, 12));
        lblBoleta.setForeground(new Color(255, 130, 14));

        lblFactura.setText("Factura :");
        lblFactura.setBounds(new Rectangle(15, 165, 80, 15));
        lblFactura.setFont(new Font("SansSerif", 1, 12));
        lblFactura.setForeground(new Color(255, 130, 14));

        txtBoleta.setBounds(new Rectangle(165, 130, 130, 20));
        txtBoleta.setDocument(new FarmaLengthText(7));
        txtBoleta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBoleta_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtBoleta_keyTyped(e);
            }
        });

        txtFactura.setBounds(new Rectangle(165, 160, 130, 20));
        txtFactura.setDocument(new FarmaLengthText(7));
        txtFactura.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFactura_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFactura_keyTyped(e);
            }
        });

        cmbSerieBoleta.setBounds(new Rectangle(155, 215, 95, 20));
        cmbSerieBoleta.setFont(new Font("SansSerif", 1, 12));
        cmbSerieBoleta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbSerieBoleta_keyPressed(e);
            }


        });
        cmbSerieBoleta.setBounds(new Rectangle(85, 130, 65, 20));

        cmbSerieFactura.setBounds(new Rectangle(85, 160, 65, 20));
        cmbSerieFactura.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbSerieFactura_keyPressed(e);
            }

        });

        lblCaja_TT.setText("Caja :");
        lblCaja_TT.setBounds(new Rectangle(15, 45, 60, 20));
        lblCaja_TT.setFont(new Font("SansSerif", 1, 12));
        //lblCaja_TT.setForeground(Color.black);
        lblCaja_TT.setForeground(new Color(255, 130, 14));
        lblCaja_TT.setMnemonic('C');
        lblCaja_TT.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                lblCaja_TT_keyPressed(e);
            }
        });
        //lblMensajeCajero.setBounds(new Rectangle(20, 25, 340, 20)); antes
        lblMensajeCajero.setBounds(new Rectangle(3, 25, 380, 20)); //ASOSA, 18.06.2010
        lblMensajeCajero.setText("Ud. tiene asignado un fondo de sencillo de S/.XXX.XX"); //ASOSA, 20.06.2010
        lblMensajeCajero.setBackground(Color.white);
        lblMensajeCajero.setForeground(Color.red);
        lblMensajeCajero.setFont(new Font("Dialog", 1, 14));

        jPanel2.add(lblCaja_TT, null);
        jPanel2.add(cmbSerieBoleta, null);
        jPanel2.add(cmbSerieFactura, null);
        jPanel2.add(txtBoleta, null);
        jPanel2.add(txtFactura, null);
        jPanel2.add(lblFactura, null);
        jPanel2.add(lblBoleta, null);
        jPanel2.add(lblDiaVenta_T, null);
        jPanel2.add(lblDiaVenta, null);
        jPanel2.add(lblNumTurno, null);
        jPanel2.add(lblFecha, null);
        jPanel2.add(lblNumCaja, null);
        jPanel2.add(lblUsuario, null);
        jPanel2.add(lblTurno_T, null);
        jPanel2.add(lblFecha_T, null);
        jPanel2.add(lblUsuario_T, null);

        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

        jContentPane.add(lblMensajeCajero, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(jPanel2, null);
        pnlHeaderDatos.add(lblDatosUser_T, null);
        jContentPane.add(pnlHeaderDatos, null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initComboBoleta();
        initComboFactura();

        FarmaVariables.vAceptar = false;
        if (VariablesCaja.vTipMovCaja.equals(ConstantsCaja.MOVIMIENTO_APERTURA)) {
            this.setTitle("Apertura de Caja");
            lblFecha_T.setText("Fecha de Apertura : ");
            this.lblNumTurno.setText("");
            try {
                this.lblDiaVenta.setText(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA));
            } catch (Exception ex) {
                log.error("", ex);
                FarmaUtility.showMessage(this, "Error al obtener la fecha actual :\n" +
                        ex.getMessage(), null);
            }
        } else if (VariablesCaja.vTipMovCaja.equals(ConstantsCaja.MOVIMIENTO_CIERRE)) {
            this.setTitle("Cierre de Caja");
            lblFecha_T.setText("Fecha de Cierre : ");
            try {
                VariablesCaja.vNumCaja = DBCaja.obtenerCajaUsuario();
                VariablesPtoVenta.vNumCaja = VariablesCaja.vNumCaja;
                this.lblDiaVenta.setText(DBCaja.obtenerFechaApertura(VariablesCaja.vNumCaja.trim()));
            } catch (Exception ex) {
                VariablesCaja.vNumCaja = "1";
                log.error("", ex);
                FarmaUtility.showMessage(this, "Error al obtener datos de la caja del usuario :\n" +
                        ex.getMessage(), null);
            }

            try {
                lblNumTurno.setText(DBCaja.obtenerTurnoActualCaja(VariablesCaja.vNumCaja));
            } catch (Exception ex) {
                lblNumTurno.setText("01");
                log.error("", ex);
                FarmaUtility.showMessage(this, "Error al obtener Turno de caja :\n" +
                        ex.getMessage(), null);
            }
        }

        this.lblUsuario.setText(FarmaVariables.vNomUsu + " " + FarmaVariables.vPatUsu);

        try {
            VariablesCaja.vNumCaja = DBCaja.obtenerCajaUsuario();
            VariablesPtoVenta.vNumCaja = VariablesCaja.vNumCaja;
        } catch (Exception ex) {
            VariablesCaja.vNumCaja = "1";
            log.error("", ex);
            FarmaUtility.showMessage(this, "Error al obtener caja de usuario :\n" +
                    ex.getMessage(), null);
        }

        try {
            this.lblFecha.setText(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA));
            //this.lblDiaVenta.setText(DBCaja.obtenerFechaApertura(VariablesCaja.vNumCaja.trim()));
            //this.lblDiaVenta.setText(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA));
        } catch (Exception ex) {
            log.error("", ex);
            Date fec = new Date();
            lblFecha.setText(fec.toString());
            FarmaUtility.showMessage(this, "Error al obtener la fecha actual :\n" +
                    ex.getMessage(), null);
        }

        lblNumCaja.setText("" + VariablesCaja.vNumCaja);

        //Coloca el mensaje de asignacion de fondo de sencillo
        //dubilluz - 15.06.2010
        lblMensajeCajero.setText("");
        if (VariablesCaja.vTipMovCaja.equals(ConstantsCaja.MOVIMIENTO_APERTURA)) {
            lblMensajeCajero.setText(getSencilloAsignado());
        }
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initComboBoleta() {
        cmbSerieBoleta.removeAllItems();

        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());

        FarmaLoadCVL.loadCVLFromSP(this.cmbSerieBoleta, "cmbSerieBoleta",
                                   "PTOVENTA_CAJ.CAJ_LISTA_SERIES_BOLETA_CAJ(?,?)", parametros, true, true);
    }

    private void initComboFactura() {
        cmbSerieFactura.removeAllItems();

        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());

        FarmaLoadCVL.loadCVLFromSP(this.cmbSerieFactura, "cmbSerieFactura",
                                   "PTOVENTA_CAJ.CAJ_LISTA_SERIES_FACTURA_CAJ(?,?)", parametros, true, true);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);

        if (validaIP()) {
            //JCORTEZ 14/03/2009 valida si es necesario ingresar rango de comprobantes y facturas
            //if(validaCompGeneral()){
            if (VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET)) {
                log.debug("ES TICKET");
                bValidaCompr = true;
                reduceEspacio();
                lblBoleta.setVisible(false);
                cmbSerieBoleta.setEditable(false);
                cmbSerieBoleta.setVisible(false);
                txtBoleta.setEditable(false);
                txtBoleta.setVisible(false);
                obtieneCompr();
                FarmaUtility.moveFocus(cmbSerieFactura);
            } else {
                log.debug("NO ES TICKET");
                FarmaUtility.moveFocus(cmbSerieBoleta);
            }
        } else
            cerrarVentana(false);
    }

    private void this_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtFactura_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFactura.getText().length() > 0) {
                FarmaUtility.moveFocus(cmbSerieBoleta);
            } else {
                FarmaUtility.showMessage(this, "Ingrese el Número de Factura", null);
                FarmaUtility.moveFocus(cmbSerieFactura);
            }
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtBoleta_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtBoleta.getText().length() > 0) {
                FarmaUtility.moveFocus(cmbSerieFactura);
            } else {
                FarmaUtility.showMessage(this, "Ingrese el Número de Boleta", null);
                FarmaUtility.moveFocus(cmbSerieBoleta);
            }
        } else {
            chkKeyPressed(e);
        }
    }

    private void cmbSerieBoleta_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (cmbSerieBoleta.getSelectedItem().toString().trim().equals("")) {
                FarmaUtility.moveFocus(cmbSerieBoleta);
                FarmaUtility.showMessage(this, "Seleccione el Número de serie para el Comprobante Boleta.",
                                         cmbSerieBoleta);
            } else {
                FarmaUtility.moveFocus(txtBoleta);
            }
        } else {
            chkKeyPressed(e);
        }
    }

    private void cmbSerieFactura_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (cmbSerieFactura.getSelectedItem().toString().trim().equals("")) {
                FarmaUtility.moveFocus(cmbSerieFactura);
                FarmaUtility.showMessage(this, "Seleccione el Número de serie para el  Comprobante Factura.",
                                         cmbSerieFactura);
            } else {
                FarmaUtility.moveFocus(txtFactura);
            }
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtBoleta_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtBoleta, e);
    }

    private void txtFactura_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFactura, e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            funcionF11();
        }
    }


    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private boolean usuarioTieneCajasDisp() {
        return true;
    }

    void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    //

    void LimpiarTextBox() {
        txtBoleta.setText("");
        txtFactura.setText("");
        obtieneCompr();
        FarmaUtility.moveFocus(cmbSerieFactura);
    }

    /**
     * Valida ingreso de campos
     * @author ERIOS
     * @since 03.11.2013
     * @return
     */
    private boolean validarIngresoTextBox() {
        //ERIOS 03.11.2013 Comprueba ambos datos
        if (txtBoleta.getText().trim().length() > 0 && txtBoleta.isVisible() &&
            txtFactura.getText().trim().length() > 0) {
            return true;
        } else if (!txtBoleta.isVisible() && txtFactura.getText().trim().length() > 0) {
            return true;
        } else {
            FarmaUtility.showMessage(this, "Es obligatorio el ingreso de Comprobantes.", null);
            return false;
        }
    }

    // 01/02/2009
    // Descripcion :Obtiene el número de Boleta y Factura del Sistema para ser comparados al aperturar
    // y Cerrar Caja.

    void buscarComprobantes() throws Exception {

        String v_secuencialBoleta, v_secuencialFactura;

        VariablesCaja.vNumSerieLocalBoleta = cmbSerieBoleta.getSelectedItem().toString().trim();
        VariablesCaja.vNumSerieLocalFactura = cmbSerieFactura.getSelectedItem().toString().trim();

        // ArrayList infoComprobantes = new ArrayList();
        //vNumSerieLocalBoleta
        //vNumSerieLocalFactura
        ArrayList infoComprobanteBoleta = new ArrayList();
        ArrayList infoComprobanteFactura = new ArrayList();
        try {
            infoComprobanteBoleta = DBCaja.ObtieneValorComprobanteBoleta();
            infoComprobanteFactura = DBCaja.ObtieneValorComprobanteFactura();

            if (infoComprobanteBoleta.size() > 0) {
                for (int i = 0; i < infoComprobanteBoleta.size(); i++) {
                    log.info("INFORtamaño-----" + i + ": " + infoComprobanteBoleta.size());
                    v_secuencialBoleta = ((String)((ArrayList)infoComprobanteBoleta.get(i)).get(2)).trim();
                    // v_secuencialBoleta   = infoComprobanteBoleta.get(2).toString().trim();
                    VariablesCaja.vNumeroBoleta = Integer.parseInt(v_secuencialBoleta);
                    log.info("v_secuencialBoleta :" + v_secuencialBoleta);
                }
            }

            if (infoComprobanteFactura.size() > 0) {
                for (int i = 0; i < infoComprobanteFactura.size(); i++) {
                    log.info("INFORtamaño-----" + i + ": " + infoComprobanteFactura.size());
                    v_secuencialFactura = ((String)((ArrayList)infoComprobanteFactura.get(i)).get(2)).trim();
                    // v_secuencialFactura  =infoComprobanteFactura.get(2).toString().trim();
                    VariablesCaja.vNumeroFactura = Integer.parseInt(v_secuencialFactura);
                    log.info("v_secuencialFactura :" + v_secuencialFactura);

                }
            }


            /*
            infoComprobantes = DBCaja.ObtieneValorComprobantes();

            if (infoComprobantes.size() > 0){

                  for (int i = 0; i < infoComprobantes.size(); i++) {
                    log.info("INFORtamaño-----"+i+": "+ infoComprobantes.size());
                      if (i==0){

                             v_secuencialBoleta   = ((String) ((ArrayList) infoComprobantes.get(i)).get(2)).trim();
                             VariablesCaja.vNumeroBoleta = Integer.parseInt(v_secuencialBoleta);
                             log.info("v_secuencialBoleta :" + v_secuencialBoleta);
                      }
                      else {

                          v_secuencialFactura  =((String) ((ArrayList) infoComprobantes.get(i)).get(2)).trim();
                          VariablesCaja.vNumeroFactura = Integer.parseInt(v_secuencialFactura);
                          log.info("v_secuencialFactura :" + v_secuencialFactura);



                      }
                }
            }

            */


        } catch (SQLException ex) {
            log.error("", ex);

            FarmaUtility.showMessage(this, "Error al buscar número de Comprobantes" + ex.getMessage(), null);
        }
    }

    //creado por
    // Descripcion :Valida comparación para  el número de Boleta y Factura  al aperturar y Cerrar Caja.

    boolean ValidarIngreso_Boleta_Factura() {
        String c_max_dif;
        int v_max_dif = 0, v_valorBoleta_ingresada = 0, v_valorFactura_ingresada = 0, v_valorBoletaSistema =
            0, v_valorFacturaSistema = 0, v_valor_max_boleta = 0, v_valor_max_factura = 0, v_valor_abs_boleta =
            0, v_valor_abs_factura = 0;
        boolean flag = true;
        //Valores ingresados
        log.debug("txtBoleta: " + txtBoleta.getText());
        log.debug("txtFactura: " + txtFactura.getText());

        if (txtBoleta.isVisible())
            v_valorBoleta_ingresada = Integer.parseInt(txtBoleta.getText());
        else
            v_valorBoleta_ingresada = VariablesCaja.vNumeroBoleta;

        v_valorFactura_ingresada = Integer.parseInt(txtFactura.getText());
        //Valores de la BD

        v_valorBoletaSistema = VariablesCaja.vNumeroBoleta;
        v_valorFacturaSistema = VariablesCaja.vNumeroFactura;

        log.debug("v_valorBoleta_ingresada: " + v_valorBoleta_ingresada);
        log.debug("v_valorFactura_ingresada: " + v_valorFactura_ingresada);
        log.debug("v_valorBoletaSistema: " + v_valorBoletaSistema);
        log.debug("v_valorFacturaSistema: " + v_valorFacturaSistema);

        try {
            c_max_dif = DBCaja.ObtieneMaximaDiferencia();
            v_max_dif = Integer.parseInt(c_max_dif);
            v_valor_max_boleta = (v_valorBoletaSistema - v_valorBoleta_ingresada);
            v_valor_max_factura = (v_valorFacturaSistema - v_valorFactura_ingresada);

            //Obteneniendo el Valor Absoluto
            v_valor_abs_boleta = v_valor_max_boleta < 0 ? -v_valor_max_boleta : v_valor_max_boleta;
            v_valor_abs_factura = v_valor_max_factura < 0 ? -v_valor_max_factura : v_valor_max_factura;


            log.info("v_max_dif :" + v_max_dif);
            log.info("v_valor_abs_boleta :" + v_valor_abs_boleta);
            log.info("v_valor_abs_factura :" + v_valor_abs_factura);

            if (v_valor_abs_boleta <= v_max_dif && v_valor_abs_factura <= v_max_dif)

                return true;
            else
                return false;

        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error" + e.getMessage(), lblEsc);
            return false;
        }


    }

    //modificado por

    void efectuarMovimiento() throws SQLException {
        if (VariablesCaja.vTipMovCaja.equals(ConstantsCaja.MOVIMIENTO_APERTURA)) {
            DBCaja.registraMovimientoCajaAper(VariablesCaja.vNumCaja.trim());
        } else if (VariablesCaja.vTipMovCaja.equals(ConstantsCaja.PROCESO_MOVIMIENTO_CIERRE)) {
            // obtiene movimiento de apertura correspondiente a la caja actual
            String movOrigen = "";
            String resultado = "";
            String mensaje = "";
            String flag = "";
            String tipComp = "";
            movOrigen = DBCaja.obtenerMovApertura(VariablesCaja.vNumCaja);
            VariablesPtoVenta.vSecMovCaja = movOrigen;
            //log.debug("--movOrigen:"+movOrigen);
            UtilityCaja.bloqueoCajaApertura(VariablesPtoVenta.vSecMovCaja);
            /*
         ArrayList infoArqueo = new ArrayList();
         infoArqueo = DBPtoVenta.obtieneDatosArqueo();

         for (int i = 0; i < infoArqueo.size(); i++) {

                 flag = ((String) ((ArrayList) infoArqueo.get(i)).get(0)).trim();
                tipComp = ((String) ((ArrayList) infoArqueo.get(i)).get(1)).trim();
         }
         log.info("flag :" + flag);
         log.info("tipComp :" + tipComp);
        */

            // obtiene informacion de arqueo de caja y la almacena en variables temporales
            //try {
            resultado = DBPtoVenta.ProcesaDatosArqueo(ConstantsPtoVenta.TIP_MOV_CAJA_CIERRE);

            log.info("resultado :" + resultado);

            if (resultado.equalsIgnoreCase("S"))
                //FarmaUtility.showMessage(this,"Se guardó el cierre de caja correctamente", lblEsc);
                vTipMovCajaAux = ConstantsPtoVenta.TIP_MOV_CAJA_CIERRE;
            /*} catch (SQLException e) {

                if (e.getErrorCode() == 20011) {
                    FarmaUtility.showMessage(this,
                                             "No se puede cerrar caja ya que existen pedidos pendientes o en proceso de cobro. Vuelva a intentar!!! \n" +
                            mensaje, lblEsc);
                } else {
                    if (e.getErrorCode() == 20018) {
                        FarmaUtility.showMessage(this,
                                                 "No se puede cerrar caja ya que existen pedidos pendientes o en proceso de cobro. Vuelva a intentar!!! \n" +
                                mensaje, lblEsc);
                    } else {
                        mensaje = e.getMessage();
                        FarmaUtility.showMessage(this, "Error al cerrar movimiento de caja : \n" +
                                mensaje, lblEsc);
                        cerrarVentana(false);
                    }
                }
            } catch (Exception ex) {

                log.error("", ex);
                mensaje = ex.getMessage();
                FarmaUtility.showMessage(this, "Error al cerrar movimiento de caja : \n" +
                        mensaje, lblEsc);
                cerrarVentana(false);
            }*/
        }

        /*
    else if(
    //-----------------------------------------------------------------------------------------

                       VariablesCaja.vTipMovCaja.equals(ConstantsCaja.MOVIMIENTO_CIERRE) ){
			// obtiene movimiento de apertura correspondiente a la caja actual
			String movOrigen = "";
			movOrigen = DBCaja.obtenerMovApertura(VariablesCaja.vNumCaja);
			VariablesPtoVenta.vSecMovCaja = movOrigen;
			// obtiene informacion de arqueo de caja y la almacena en variables temporales
			ArrayList infoArqueo = new ArrayList();
			infoArqueo = DBPtoVenta.obtieneDatosArqueo();

		    for(int i=0;i<infoArqueo.size();i++){
		      log.info("INFOaRQUEO-----"+i+": "+ infoArqueo.get(i).toString());
		    }
			
			String flag = "";
			String tipComp = "";

			String cantBoletasGen = "0";
			String boletasGen = "0";
			String cantFacturasGen = "0";
			String facturasGen = "0";
			String cantGuiasGen = "0";
			String guiasGen = "0";
			String cantBoletasAnu = "0";
			String boletasAnu = "0";
			String cantFacturasAnu = "0";
			String facturasAnu = "0";
			String cantGuiasAnu = "0";
			String guiasAnu = "0";
      String cantNCBoletas = "0";
      String cantNCFacturas = "0";
      String ncBoletas = "0";
      String ncFacturas = "0";

			for (int i = 0; i < infoArqueo.size(); i++) {

				flag = ((String) ((ArrayList) infoArqueo.get(i)).get(0)).trim();
				tipComp = ((String) ((ArrayList) infoArqueo.get(i)).get(1)).trim();
				
        if(flag.equals("N") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_BOLETA)) {
					cantBoletasGen = ((String) ((ArrayList) infoArqueo.get(i)).get(2)).trim();
					boletasGen = ((String) ((ArrayList) infoArqueo.get(i)).get(3)).trim();
				}	else if (flag.equals("N") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_FACTURA)) {
					cantFacturasGen = ((String) ((ArrayList) infoArqueo.get(i)).get(2)).trim();
					facturasGen = ((String) ((ArrayList) infoArqueo.get(i)).get(3)).trim();
				}	else if (flag.equals("N") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_GUIA)) {
					cantGuiasGen = ((String) ((ArrayList) infoArqueo.get(i)).get(2)).trim();
					guiasGen = ((String) ((ArrayList) infoArqueo.get(i)).get(3)).trim();
				}

       	if(flag.equals("NC") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_BOLETA)) {
					cantNCBoletas = ((String) ((ArrayList) infoArqueo.get(i)).get(2)).trim();
					ncBoletas = ((String) ((ArrayList) infoArqueo.get(i)).get(3)).trim();
				}	else if (flag.equals("NC") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_FACTURA)) {
					cantNCFacturas = ((String) ((ArrayList) infoArqueo.get(i)).get(2)).trim();
					ncFacturas = ((String) ((ArrayList) infoArqueo.get(i)).get(3)).trim();
				}	

        if (flag.equals("S") && tipComp.equals(ConstantsPtoVenta.TIP_COMP_BOLETA)) {
					cantBoletasAnu = ((String) ((ArrayList) infoArqueo.get(i)).get(2)).trim();
					boletasAnu = ((String) ((ArrayList) infoArqueo.get(i)).get(3)).trim();
				}	else if (flag.equals("S")	&& tipComp.equals(ConstantsPtoVenta.TIP_COMP_FACTURA)) {
					cantFacturasAnu = ((String) ((ArrayList) infoArqueo.get(i)).get(2)).trim();
					facturasAnu = ((String) ((ArrayList) infoArqueo.get(i)).get(3)).trim();
				}	else if (flag.equals("S")	&& tipComp.equals(ConstantsPtoVenta.TIP_COMP_GUIA)) {
					cantGuiasAnu = ((String) ((ArrayList) infoArqueo.get(i)).get(2)).trim();
					guiasAnu = ((String) ((ArrayList) infoArqueo.get(i)).get(3)).trim();
				}
			}

			// calcula totales
			double totGenerados = 0;
			double totAnulados = 0;
      double totNCredito = 0;
			double totCompras = 0;

			totGenerados = totGenerados + FarmaUtility.getDecimalNumber(boletasGen);
			totGenerados = totGenerados + FarmaUtility.getDecimalNumber(facturasGen);
			totGenerados = totGenerados + FarmaUtility.getDecimalNumber(guiasGen);
      //***totGenerados = FarmaUtility.getDecimalNumberRedondeado(totGenerados);

			totAnulados = totAnulados	+ FarmaUtility.getDecimalNumber(boletasAnu);
			totAnulados = totAnulados	+ FarmaUtility.getDecimalNumber(facturasAnu);
			totAnulados = totAnulados + FarmaUtility.getDecimalNumber(guiasAnu);
      //***totAnulados = FarmaUtility.getDecimalNumberRedondeado(totAnulados);

      totNCredito = totNCredito	+ FarmaUtility.getDecimalNumber(ncBoletas);
			totNCredito = totNCredito	+ FarmaUtility.getDecimalNumber(ncFacturas);			
      //***totNCredito = FarmaUtility.getDecimalNumberRedondeado(totNCredito);

			String cantBoletasTot = "" + (Integer.parseInt(cantBoletasGen) - Integer.parseInt(cantBoletasAnu));
			String cantFacturasTot = ""	+ (Integer.parseInt(cantFacturasGen) - Integer.parseInt(cantFacturasAnu));
			String cantGuiasTot = "" + (Integer.parseInt(cantGuiasGen) - Integer.parseInt(cantGuiasAnu));
			String boletasTotal = "" + (FarmaUtility.getDecimalNumber(boletasGen) - FarmaUtility.getDecimalNumber(boletasAnu)) ;
			String facturasTotal = "" + (FarmaUtility.getDecimalNumber(facturasGen) - FarmaUtility.getDecimalNumber(facturasAnu));
			String guiasTotal = "" + (FarmaUtility.getDecimalNumber(guiasGen) - FarmaUtility.getDecimalNumber(guiasAnu));

			totCompras = (totGenerados - totAnulados) + totNCredito;
      //***totCompras = FarmaUtility.getDecimalNumberRedondeado(totCompras);
			
      // guarda el movimiento con los valores de arqueo
			String codGenerado = "";
			codGenerado = DBPtoVenta.generarArqueoCaja(ConstantsPtoVenta.TIP_MOV_CAJA_CIERRE,
                                                 cantBoletasGen,boletasGen, cantFacturasGen,
                                                 facturasGen, cantGuiasGen,guiasGen,
                                                 cantBoletasAnu, boletasAnu, cantFacturasAnu,
                                                 facturasAnu, cantGuiasAnu, guiasAnu,
                                                 cantBoletasTot,boletasTotal, cantFacturasTot,
                                                 facturasTotal,cantGuiasTot, guiasTotal,
                                                 "" + totGenerados, "" + totAnulados,
                                                 "" + totCompras, cantNCBoletas,ncBoletas,
                                                 cantNCFacturas, ncFacturas, "" + totNCredito);
			DBPtoVenta.guardaValoresComprobante(codGenerado);

      FarmaUtility.showMessage(this,"Se guardo el cierre de caja correctamente", lblEsc);

      //-------------------------------------------------------------------------------------------------------
		}
            */
    }

    public void validarParamsUser() throws SQLException {
        DBCaja.validaUsuarioOpCaja("M" + VariablesCaja.vTipMovCaja);
    }

    public void verificaAperturaCaja() throws SQLException {
        DBCaja.verificaAperturaCaja();
    }


    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /**
     * Se valida el ingreso de boletas y facturas para el ingreso en el cierre por caja
     * @author : JCORTEZ
     * @since : 14.04.09
     * */
    private boolean validaCompGeneral() {

        boolean valor = false;
        try {
            String result = DBCaja.getObtieneTipoComp2(VariablesCaja.vNumCaja.trim());
            if (result.equalsIgnoreCase("N"))
                valor = true;
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al validar tipo comprobantes - caja", lblEsc);
        }
        return valor;
    }

    /**
     * Se valida el ingreso de boletas y facturas para el ingreso en el cierre por IP
     * @author : JCORTEZ
     * @since : 10.06.09
     * */
    private boolean validaIP() {
        boolean valor = true;
        try {
            String result = "";

            //se obtiene el codigo y descripcion del tipo de boleta actual
            result = DBCaja.getObtieneTipoCompPorIP(FarmaVariables.vIpPc, ConstantsVentas.TIPO_COMP_BOLETA);

            //if(temp!=null)
            //{   result = temp.get(0).toString();
            //}
            if (result.trim().equalsIgnoreCase("N")) {
                log.debug("VariablesCaja.vTipComp 1-->" + VariablesCaja.vTipComp);
                FarmaUtility.showMessage(this,
                                         "La IP no cuenta con una impresora asignada de ticket o boleta. Verifique!!!",
                                         lblEsc);
                valor = false;
            } else {
                VariablesCaja.vTipComp = result;
                log.debug("VariablesCaja.vTipComp 2-->" + VariablesCaja.vTipComp);
            }
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al validar IP - impresora", lblEsc);
            valor = false;
        }
        return valor;
    }

    /**
     * Se obtiene correlativos automaticamente
     * */
    private void obtieneCompr() {
        ArrayList infoComprobanteBoleta = new ArrayList();
        ArrayList infoComprobanteFactura = new ArrayList();
        String v_secuencialBoleta, v_secuencialFactura;
        int CantFact = 0;

        log.debug("Error jcortez");
        VariablesCaja.vNumSerieLocalBoleta = cmbSerieBoleta.getSelectedItem().toString().trim();

        CantFact = cmbSerieFactura.getItemCount();
        log.debug("Cant Item Fact-->" + CantFact);

        if (CantFact < 2)
            cmbSerieFactura.setSelectedIndex(0);
        else
            cmbSerieFactura.setSelectedIndex(1);

        VariablesCaja.vNumSerieLocalFactura = cmbSerieFactura.getSelectedItem().toString().trim();

        try {
            infoComprobanteBoleta = DBCaja.ObtieneValorComprobanteBoleta();
            infoComprobanteFactura = DBCaja.ObtieneValorComprobanteFactura();

            if (!cmbSerieBoleta.isEditable()) {
                if (infoComprobanteBoleta.size() > 0) {
                    for (int i = 0; i < infoComprobanteBoleta.size(); i++) {
                        v_secuencialBoleta = ((String)((ArrayList)infoComprobanteBoleta.get(i)).get(2)).trim();
                        VariablesCaja.vNumeroBoleta = Integer.parseInt(v_secuencialBoleta);
                        log.info("v_secuencialBoleta :" + v_secuencialBoleta);
                    }
                }
                //txtBoleta.setText("" + VariablesCaja.vNumeroBoleta);
                //txtBoleta.setText(FarmaUtility.caracterIzquierda(txtBoleta.getText().trim(), 7, "0"));
            }

            if (!cmbSerieFactura.isEnabled()) {
                if (infoComprobanteFactura.size() > 0) {
                    for (int i = 0; i < infoComprobanteFactura.size(); i++) {
                        v_secuencialFactura = ((String)((ArrayList)infoComprobanteFactura.get(i)).get(2)).trim();
                        VariablesCaja.vNumeroFactura = Integer.parseInt(v_secuencialFactura);
                        log.info("v_secuencialFactura :" + v_secuencialFactura);
                    }
                }
                //txtFactura.setText(VariablesCaja.vNumeroFactura + "");
                //txtFactura.setText(FarmaUtility.caracterIzquierda(txtFactura.getText().trim(), 7, "0"));
            }
        } catch (SQLException ex) {
            log.error("", ex);
            FarmaUtility.showMessage(this, "Error al buscar número de Comprobantes boleta - factura" + ex.getMessage(),
                                     null);
        }
    }

    private void reduceEspacio() {
        /* this.setSize(new Dimension(385, 239));
        jPanel2.setBounds(new Rectangle(20, 45, 340, 125));
        lblEsc.setBounds(new Rectangle(275, 180, 85, 20));
        lblF11.setBounds(new Rectangle(160, 180, 100, 20));*/

        this.setSize(new Dimension(385, 273));
        jPanel2.setBounds(new Rectangle(20, 45, 340, 160));
        lblEsc.setBounds(new Rectangle(275, 215, 85, 20));
        lblF11.setBounds(new Rectangle(160, 215, 100, 20));

        lblFactura.setBounds(new Rectangle(15, 135, 80, 15));
        cmbSerieFactura.setBounds(new Rectangle(85, 130, 65, 20));
        txtFactura.setBounds(new Rectangle(165, 130, 130, 20));
    }

    private void lblCaja_T_keyPressed(KeyEvent e) {

        chkKeyPressed(e);
    }

    private void lblCaja_TT_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void funcionF11() {
        String pMensaje = "";
        if (lblMensajeCajero.getText().trim().length() > 0) {
            pMensaje = lblMensajeCajero.getText().trim() + "\n";
        }

        if (validarIngresoTextBox()) {
            //FarmaUtility.showMessage(this,"VariablesCaja.vNumCaja: "+FarmaVariables.vNuSecUsu,null);
            if ((getIndOPEN_OR_NOT_OPEN() && VariablesCaja.vTipMovCaja.equals(ConstantsCaja.MOVIMIENTO_APERTURA)) ||
                VariablesCaja.vTipMovCaja.equals(ConstantsCaja.MOVIMIENTO_CIERRE)) {
                //ASOSA, 20.06.2010
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                pMensaje + "¿Está seguro que desea efectuar la operación?")) {
                    //ERIOS 12.11.2013 Procesando
                    DlgProcesarMovCaja dlgProcesarMovCaja = new DlgProcesarMovCaja(myParentFrame, "", true);
                    dlgProcesarMovCaja.setDlgMovCaja(this);
                    dlgProcesarMovCaja.setLblEsc(lblEsc);
                    dlgProcesarMovCaja.setTxtFactura(txtFactura);
                    dlgProcesarMovCaja.setVTipMovCajaAux(vTipMovCajaAux);
                    dlgProcesarMovCaja.mostrar();
                }
            } else { //ASOSA 20.06.2010
                //FarmaUtility.showMessage(this,"Ud. probablemente no cuente con fondo de sencillo asignado.\nVerifique con el QF del local",cmbSerieFactura);
                FarmaUtility.showMessage(this, "Ud. no cuenta con fondo de sencillo asignado.\n" +
                        "Por favor coordine con su Jefe de Local.\n", cmbSerieFactura);
            }
        }
        //--add
        /*
        else {
        FarmaUtility.showMessage(this,"Es obligatorio el ingreso de Comprobantes.", null);//--add
        }
        */
    }

    public String getSencilloAsignado() {
        String pMensaje = "";
        try {
            //JMIRANDA 01.03.10 validar si tiene Fondo sencillo asignado
            // if( VariablesCaja.vTipMovCaja.equals(ConstantsCaja.MOVIMIENTO_APERTURA) ){
            if (VariablesCaja.vTipMovCaja.equals(ConstantsCaja.MOVIMIENTO_APERTURA) &&
                UtilityFondoSencillo.indActivoFondo()) {
                VariablesFondoSencillo.vIndTieneFondoSencillo =
                        UtilityFondoSencillo.getIndTieneFondoSencillo(this, FarmaVariables.vNuSecUsu,
                                                                      txtFactura).trim();
            }
            if ((VariablesFondoSencillo.vIndTieneFondoSencillo.equalsIgnoreCase("S") &&
                 VariablesCaja.vTipMovCaja.equals(ConstantsCaja.MOVIMIENTO_APERTURA)) &&
                UtilityFondoSencillo.indActivoFondo()) {
                //OBTIENE MONTO
                log.debug("Num CAJA: " + VariablesCaja.vNumCaja);
                VariablesFondoSencillo.vMontoAsignado =
                        UtilityFondoSencillo.getMontoAsignado(this, FarmaVariables.vNuSecUsu, txtFactura).trim();

                if (Double.parseDouble(VariablesFondoSencillo.vMontoAsignado.trim()) > 0.00) {
                    /*
                    int rptaInt =
                        UtilityFondoSencillo.rptaConfirmDialogDefaultNo(this,
                                                                        "Ud. Fue asignado con un Monto:\n" +
                            "S/." + VariablesFondoSencillo.vMontoAsignado +
                            "¿El monto es correcto?");
                    if (rptaInt == 0 || rptaInt == -1)
                        flag = false;
                    else {
                        flag = true;
                        vAsignaMovCajaSencillo = true;
                    }
                     * */
                    pMensaje =
                            "Ud. tiene asignado un fondo de sencillo de S/." + VariablesFondoSencillo.vMontoAsignado; //ASOSA, 18.06.2010
                }

            }
        } catch (SQLException e) {
            log.error("", e);
        } catch (Exception y) {
            log.error("", y);
        }

        return pMensaje.trim();
    }

    /**
     * Determine si abre o no abre caja en caso este activo el fondo de sencillo y si le asignaron o no sencillo
     * @author ASOSA
     * @since 20.06.2010
     * @return
     */
    private boolean getIndOPEN_OR_NOT_OPEN() {
        boolean flag = false;
        String ind = "N";
        try {
            ind = DBFondoSencillo.getIndOPEN_OR_NOT_OPEN();
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "ERROR en getIndOPEN_OR_NOT_OPEN de DlgMovCaja: " + e.getMessage(), null);
        }
        if (ind.equalsIgnoreCase("S")) {
            flag = true;
        }
        return flag;
    }

}
