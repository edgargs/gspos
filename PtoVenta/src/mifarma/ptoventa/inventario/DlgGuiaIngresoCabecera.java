package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgListaMaestros;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgGuiaIngresoCabecera extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgGuiaIngresoCabecera.class);

    Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel pnlHeader = new JPanel();
    private JLabel lblFecha = new JLabel();
    private JLabel lblFecha_T = new JLabel();
    private JPanel pnlWhite = new JPanel();
    private JTextFieldSanSerif txtDescripcion = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCodigo = new JTextFieldSanSerif();
    private JLabel lblSeleccionar_T = new JLabel();
    private JComboBox cmbTipoGuia = new JComboBox();
    private JButton btnTipoGuiaEntrada = new JButton();
    private JTextFieldSanSerif txtNroGuia = new JTextFieldSanSerif();
    private JComboBox cmbTipo = new JComboBox();
    private JTextFieldSanSerif txtFechaGuia = new JTextFieldSanSerif();
    private JButton btnFechaGuia = new JButton();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JLabelOrange lblCiudad = new JLabelOrange();
    private JLabelOrange lblNombre = new JLabelOrange();
    private JButton btnRuc = new JButton();
    private JTextFieldSanSerif txtNombre = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCiudad = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtRuc = new JTextFieldSanSerif();
    private JLabel lblNroDoc_T = new JLabel();
    private JTextFieldSanSerif txtSerieDoc = new JTextFieldSanSerif();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgGuiaIngresoCabecera() {
        this(null, "", false);
    }

    public DlgGuiaIngresoCabecera(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(411, 352));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Guia de Ingreso - Cabecera");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(405, 229));
        pnlHeader.setBounds(new Rectangle(10, 10, 385, 30));
        pnlHeader.setBackground(new Color(43, 141, 39));
        pnlHeader.setLayout(null);
        lblFecha.setBounds(new Rectangle(65, 5, 150, 20));
        lblFecha.setFont(new Font("SansSerif", 1, 11));
        lblFecha.setForeground(Color.white);
        lblFecha_T.setText("Fecha :");
        lblFecha_T.setBounds(new Rectangle(10, 5, 55, 20));
        lblFecha_T.setFont(new Font("SansSerif", 1, 11));
        lblFecha_T.setForeground(Color.white);
        pnlWhite.setBounds(new Rectangle(10, 40, 385, 140));
        pnlWhite.setBackground(Color.white);
        pnlWhite.setLayout(null);
        pnlWhite.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        txtDescripcion.setBounds(new Rectangle(220, 105, 160, 20));
        txtDescripcion.setEnabled(false);
        txtCodigo.setBounds(new Rectangle(160, 105, 55, 20));
        txtCodigo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCodigo_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtCodigo_keyReleased(e);
            }
        });
        lblSeleccionar_T.setText("Seleccionar:");
        lblSeleccionar_T.setBounds(new Rectangle(5, 110, 145, 15));
        lblSeleccionar_T.setFont(new Font("SansSerif", 1, 11));
        lblSeleccionar_T.setForeground(new Color(255, 130, 14));
        cmbTipoGuia.setBounds(new Rectangle(160, 75, 220, 20));
        cmbTipoGuia.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbTipoGuia_keyPressed(e);
            }
        });
        btnTipoGuiaEntrada.setText("Tipo Guia de Ingreso :");
        btnTipoGuiaEntrada.setBounds(new Rectangle(5, 75, 125, 20));
        btnTipoGuiaEntrada.setBackground(new Color(255, 130, 14));
        btnTipoGuiaEntrada.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnTipoGuiaEntrada.setBorderPainted(false);
        btnTipoGuiaEntrada.setContentAreaFilled(false);
        btnTipoGuiaEntrada.setDefaultCapable(false);
        btnTipoGuiaEntrada.setFocusPainted(false);
        btnTipoGuiaEntrada.setFont(new Font("SansSerif", 1, 11));
        btnTipoGuiaEntrada.setHorizontalAlignment(SwingConstants.LEFT);
        btnTipoGuiaEntrada.setRequestFocusEnabled(false);
        btnTipoGuiaEntrada.setForeground(new Color(255, 130, 14));
        txtNroGuia.setBounds(new Rectangle(320, 45, 60, 20));
        txtNroGuia.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNroGuia_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtNroGuia_keyTyped(e);
            }
        });
        cmbTipo.setBounds(new Rectangle(160, 45, 105, 20));
        cmbTipo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbTipo_keyPressed(e);
            }
        });
        txtFechaGuia.setBounds(new Rectangle(160, 15, 105, 20));
        txtFechaGuia.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaGuia_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaGuia_keyReleased(e);
            }

        });
        btnFechaGuia.setText("Fecha  :");
        btnFechaGuia.setBounds(new Rectangle(5, 15, 100, 20));
        btnFechaGuia.setBackground(new Color(255, 130, 14));
        btnFechaGuia.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnFechaGuia.setBorderPainted(false);
        btnFechaGuia.setContentAreaFilled(false);
        btnFechaGuia.setDefaultCapable(false);
        btnFechaGuia.setFocusPainted(false);
        btnFechaGuia.setFont(new Font("SansSerif", 1, 11));
        btnFechaGuia.setForeground(new Color(255, 130, 14));
        btnFechaGuia.setHorizontalAlignment(SwingConstants.LEFT);
        btnFechaGuia.setMnemonic('f');
        btnFechaGuia.setRequestFocusEnabled(false);
        btnFechaGuia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFechaGuia_actionPerformed(e);
            }
        });
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(200, 295, 90, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(305, 295, 90, 20));
        jPanelWhite1.setBounds(new Rectangle(10, 190, 385, 95));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblCiudad.setText("Ciudad:");
        lblCiudad.setBounds(new Rectangle(15, 60, 100, 20));
        lblNombre.setText("Nombre Tienda:");
        lblNombre.setBounds(new Rectangle(15, 10, 100, 20));
        btnRuc.setText("RUC:");
        btnRuc.setBounds(new Rectangle(15, 35, 100, 20));
        btnRuc.setBackground(new Color(255, 130, 14));
        btnRuc.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnRuc.setBorderPainted(false);
        btnRuc.setContentAreaFilled(false);
        btnRuc.setDefaultCapable(false);
        btnRuc.setFocusPainted(false);
        btnRuc.setFont(new Font("SansSerif", 1, 11));
        btnRuc.setForeground(new Color(255, 130, 14));
        btnRuc.setHorizontalAlignment(SwingConstants.LEFT);
        btnRuc.setRequestFocusEnabled(false);
        btnRuc.setMnemonic('R');
        btnRuc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNombre_actionPerformed(e);
            }
        });
        txtNombre.setBounds(new Rectangle(125, 10, 235, 20));
        txtNombre.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNombre_keyPressed(e);
            }
        });
        txtCiudad.setBounds(new Rectangle(125, 60, 120, 20));
        txtCiudad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCiudad_keyPressed(e);
            }
        });
        txtRuc.setBounds(new Rectangle(125, 35, 120, 20));
        txtRuc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtRuc_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtRuc_keyTyped(e);
            }
        });
        pnlHeader.add(lblFecha, null);
        pnlHeader.add(lblFecha_T, null);
        pnlWhite.add(jLabelOrange1, null);
        pnlWhite.add(txtSerieDoc, null);
        pnlWhite.add(lblNroDoc_T, null);
        pnlWhite.add(txtDescripcion, null);
        pnlWhite.add(txtCodigo, null);
        pnlWhite.add(lblSeleccionar_T, null);
        pnlWhite.add(cmbTipoGuia, null);
        pnlWhite.add(btnTipoGuiaEntrada, null);
        pnlWhite.add(txtNroGuia, null);
        pnlWhite.add(cmbTipo, null);
        pnlWhite.add(txtFechaGuia, null);
        pnlWhite.add(btnFechaGuia, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jPanelWhite1.add(txtRuc, null);
        jPanelWhite1.add(txtCiudad, null);
        jPanelWhite1.add(txtNombre, null);
        jPanelWhite1.add(btnRuc, null);
        jPanelWhite1.add(lblNombre, null);
        jPanelWhite1.add(lblCiudad, null);
        jContentPane.add(jPanelWhite1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(pnlHeader, null);
        jContentPane.add(pnlWhite, null);
        //
        txtFechaGuia.setLengthText(10);
        txtSerieDoc.setLengthText(4);
        ;
        txtNroGuia.setLengthText(8);
        txtCodigo.setLengthText(15);

        txtNombre.setLengthText(20);
        txtCiudad.setLengthText(10);
        txtRuc.setLengthText(11);
        lblNroDoc_T.setText("Nro documento :");
        lblNroDoc_T.setBounds(new Rectangle(5, 45, 145, 20));
        lblNroDoc_T.setFont(new Font("SansSerif", 1, 11));
        lblNroDoc_T.setForeground(new Color(255, 130, 14));
        txtSerieDoc.setBounds(new Rectangle(270, 45, 35, 20));
        txtSerieDoc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtSerieDoc_keyPressed(e);
            }

            /*  public void keyTyped(KeyEvent e)
          {
            txtSerieDoc_keyTyped(e);
          } */
        });
        jLabelOrange1.setText("-");
        jLabelOrange1.setBounds(new Rectangle(305, 45, 15, 20));
        jLabelOrange1.setFont(new Font("SansSerif", 1, 25));
        jLabelOrange1.setHorizontalAlignment(SwingConstants.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        habilitarCompetencia(false);
        initCmbTipo();
        initCmbTipoGuia();
        initDatos();

        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCmbTipo() {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        FarmaLoadCVL.loadCVLFromSP(cmbTipo, ConstantsInventario.NOM_HASHTABLE_CMBTIPO,
                                   "PTOVENTA_INV.INV_GET_TIPO_DOCUMENTO(?)", parametros, false, true);
        parametros = null;
    }

    private void initCmbTipoGuia() {
        ArrayList parametros = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbTipoGuia, ConstantsInventario.NOM_HASHTABLE_CMBGUIA,
                                   "PTOVENTA_INV.INV_GET_TIPO_ORIGEN_GUIA_E", parametros, false);
        parametros = null;
    }

    private void initDatos() {
        try {
            lblFecha.setText(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA));
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener la fecha del sistema :\n" +
                    sql.getMessage(), null);
        }
        if (VariablesInventario.vFecGuia.trim().equals(""))
            txtFechaGuia.setText(lblFecha.getText().substring(0, 10));
        else
            txtFechaGuia.setText(VariablesInventario.vFecGuia);
        FarmaLoadCVL.setSelectedValueInComboBox(cmbTipo, ConstantsInventario.NOM_HASHTABLE_CMBTIPO,
                                                VariablesInventario.vTipoDoc);
        txtNroGuia.setText(VariablesInventario.vNumDoc);
        FarmaLoadCVL.setSelectedValueInComboBox(cmbTipoGuia, ConstantsInventario.NOM_HASHTABLE_CMBGUIA,
                                                VariablesInventario.vTipoOrigen);

        if (VariablesInventario.vTipoOrigen.equals(ConstantsPtoVenta.LISTA_MAESTRO_COMPETENCIA)) {
            habilitarCompetencia(true);
            //VariablesInventario.vCodOrigen = FarmaVariables.vCodLocal;
            //VariablesInventario.vDescOrigen = txtNombre.getText().trim();
            txtNombre.setText(VariablesInventario.vNombreTienda);
            txtCiudad.setText(VariablesInventario.vCiudadTienda);
            txtRuc.setText(VariablesInventario.vRucTienda);
        } else {
            txtCodigo.setText(VariablesInventario.vCodOrigen);
            txtDescripcion.setText(VariablesInventario.vDescOrigen);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaGuia);
    }

    private void btnFechaGuia_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaGuia);
    }

    private void txtFechaGuia_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(cmbTipo);
        else
            chkKeyPressed(e);
    }

    private void txtFechaGuia_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaGuia, e);
    }

    private void cmbTipo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtSerieDoc);
        else
            chkKeyPressed(e);
    }

    private void txtSerieDoc_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNroGuia);
            txtSerieDoc.setText(FarmaUtility.completeWithSymbol(txtSerieDoc.getText(), 3, "0", "I"));
        } else
            chkKeyPressed(e);
    }

    private void txtSerieDoc_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtSerieDoc, e);
    }

    private void txtNroGuia_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbTipoGuia);
            txtNroGuia.setText(FarmaUtility.completeWithSymbol(txtNroGuia.getText(), 8, "0", "I"));
        } else
            chkKeyPressed(e);
    }

    private void txtNroGuia_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNroGuia, e);
    }

    private void cmbTipoGuia_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String tipoMaestro =
                FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBGUIA, cmbTipoGuia.getSelectedIndex());
            if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_COMPETENCIA)) {
                habilitarCompetencia(true);
                /**
         *
         * dubilluz modificado 12.11.2007
         *
         **/
                txtNombre.setEnabled(false);
                FarmaUtility.moveFocus(txtRuc);
            } else {
                habilitarCompetencia(false);
                FarmaUtility.moveFocus(txtCodigo);
            }
            FarmaVariables.vAceptar = false;
        } else
            chkKeyPressed(e);
    }

    private void txtCodigo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String tipoMaestro =
                FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBGUIA, cmbTipoGuia.getSelectedIndex());
            if (tipoMaestro.trim().length() == 0)
                FarmaUtility.showMessage(this, "Seleccione un Tipo de Origen.", cmbTipoGuia);
            else {
                if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL)) {
                    VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_LOCAL;
                    VariablesInventario.vTipoMotivoKardex = ConstantsPtoVenta.MOT_KARDEX_INGRESO_LOCAL;
                } else if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ)) {
                    VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_MATRIZ;
                    VariablesInventario.vTipoMotivoKardex = ConstantsPtoVenta.MOT_KARDEX_INGRESO_MANUAL_MATRIZ;
                } else if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR)) {
                    VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_PROVEEDOR;
                    VariablesInventario.vTipoMotivoKardex = ConstantsPtoVenta.MOT_KARDEX_INGRESO_PROVEEDOR;
                } else if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_COMPETENCIA)) {
                    VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_COMPETENCIA;
                    VariablesInventario.vTipoMotivoKardex = ConstantsPtoVenta.MOT_KARDEX_COTIZACION_COMPETENCIA;
                }

                validaCodigo(txtCodigo, txtDescripcion, VariablesPtoVenta.vTipoMaestro);
            }
        } else
            chkKeyPressed(e);
    }

    private void txtCodigo_keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && FarmaVariables.vAceptar)
            FarmaUtility.moveFocus(txtFechaGuia);
    }

    private void btnNombre_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtRuc);
    }

    private void txtNombre_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCiudad);
        } else
            chkKeyPressed(e);
    }

    private void txtCiudad_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtRuc);
        } else
            chkKeyPressed(e);
    }

    private void txtRuc_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtNombre.getText().trim().length() > 0) {
                if (txtRuc.getText().trim().length() > 0)
                    FarmaUtility.moveFocus(txtCiudad);
                else
                    buscaRucEmpresa();
            } else
                buscaRucEmpresa();
        } else
            chkKeyPressed(e);
    }

    private void txtRuc_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtRuc, e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (validarCampos()) {
                //validara si el comprobante de la competencia
                //ya existia
                String tipo_guia =
                    FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBGUIA, cmbTipoGuia.getSelectedIndex());
                log.debug("tipo_guia " + tipo_guia);
                if (tipo_guia.trim().equals(ConstantsPtoVenta.LISTA_MAESTRO_COMPETENCIA)) {
                    /**
           * Colocando los Datos de la Competencia
           * Si por alguna motivo modificaron la informacion
           * 12.11.2007  dubilluz
           */
                    if (!txtNombre.getText().trim().equals(VariablesInventario.vDescripcionCompetencia.trim())) {
                        log.debug("Cambiaron el Nombre de la competencia");
                        txtNombre.setText(VariablesInventario.vDescripcionCompetencia.trim());
                    }
                    if (!txtRuc.getText().trim().equals(VariablesInventario.vRucCompetencia.trim())) {
                        txtRuc.setText(VariablesInventario.vRucCompetencia.trim());
                        log.debug("Cambiaron el RUC de la competencia");
                    }
                    if (validaCotizacion()) {
                        cargarDatos();
                        cerrarVentana(true);
                    } else {
                        //SE MODIFICARA LA VALIDACION:
                        //SI SE DESEA INGRESAR UNA COTIZACION DE LA MISMA EMPRESA CON EL MISMO NUMERO DE
                        //COMPROBANTE SE PROCEDERA A ANULAR Y A INSERTAR PERO SE REALIZARA SI ES NECESARIO
                        //FarmaUtility.showMessage(this,"El numero de documento ya existe.Verifique!!!",txtSerieDoc);

                        ArrayList array = (ArrayList)VariablesInventario.vArrayDatosCotizacion.get(0);
                        String fecha = (String)array.get(1);
                        String num_doc = (String)array.get(2);
                        String monto = (String)array.get(3);
                        String ruc = (String)array.get(4);
                        String desc_empresa = (String)array.get(5);

                        if (JConfirmDialog.rptaConfirmDialog(this, "Ya Existe un Documento para la Cotizacion.\n" +
                                "Nro: " + num_doc + "\n" +
                                "Fecha: " + fecha + "\n" +
                                "Monto: " + monto + "\n" +
                                "RUC: " + ruc + "\n" +
                                "Empresa: " + desc_empresa + "\n" +
                                "¿Desea anularla y generarla nuevamente?")) {
                            cargarDatos();
                            VariablesInventario.vNumNota_Anular = (String)array.get(0);
                            VariablesInventario.vIndAnularNota = FarmaConstants.INDICADOR_S;
                            cerrarVentana(true);
                        } else {
                            txtSerieDoc.selectAll();
                            txtNroGuia.selectAll();
                            FarmaUtility.moveFocus(txtSerieDoc);
                        }

                    }
                } else {
                    cargarDatos();
                    cerrarVentana(true);
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

    private void validaCodigo(JTextField pJTextField_Cod, JTextField pJTextField_Desc, String pTipoMaestro) {
        if (pJTextField_Cod.getText().trim().length() > 0) {
            VariablesPtoVenta.vCodMaestro = pJTextField_Cod.getText().trim();
            ArrayList myArray = new ArrayList();
            buscaCodigoListaMaestro(myArray);

            if (myArray.size() == 0) {
                FarmaUtility.showMessage(this, "Codigo No Encontrado", pJTextField_Cod);
                FarmaVariables.vAceptar = false;
            } else if (myArray.size() == 1) {
                String codigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
                String descripcion = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
                pJTextField_Cod.setText(codigo);
                pJTextField_Desc.setText(descripcion);
                VariablesPtoVenta.vCodMaestro = codigo;
                FarmaVariables.vAceptar = true;
            } else {
                FarmaUtility.showMessage(this, "Se encontro mas de un registro.Verificar!!!", pJTextField_Cod);
                FarmaVariables.vAceptar = false;
            }
        } else {
            cargaListaMaestros(pTipoMaestro, pJTextField_Cod, pJTextField_Desc);
        }
    }

    private void buscaCodigoListaMaestro(ArrayList pArrayList) {
        try {
            DBPtoVenta.buscaCodigoListaMaestro(pArrayList, VariablesPtoVenta.vTipoMaestro,
                                               VariablesPtoVenta.vCodMaestro);
        } catch (SQLException sql) {
            log.error("", sql);
            log.debug("Error al buscar codigo en lista maestro : \n" +
                    sql.getMessage());
        }
    }

    private void cargaListaMaestros(String pTipoMaestro, JTextField pJTextField_Cod, JTextField pJTextField_Desc) {
        VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
        DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
        dlgListaMaestros.setVisible(true);
        if (FarmaVariables.vAceptar) {
            pJTextField_Cod.setText(VariablesPtoVenta.vCodMaestro);
            pJTextField_Desc.setText(VariablesPtoVenta.vDescMaestro);
        }
    }

    private boolean validarCampos() {
        boolean retorno = true;
        String tipoMaestro =
            FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBGUIA, cmbTipoGuia.getSelectedIndex());

        txtSerieDoc.setText(FarmaUtility.completeWithSymbol(txtSerieDoc.getText(), 3, "0", "I"));
        txtNroGuia.setText(FarmaUtility.completeWithSymbol(txtNroGuia.getText(), 7, "0", "I"));

        if (txtFechaGuia.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe ingresar la Fecha del Documento.", txtFechaGuia);
            retorno = false;
        } else if (!FarmaUtility.validaFecha(txtFechaGuia.getText(), "dd/MM/yyyy")) {
            FarmaUtility.showMessage(this, "Ingrese la fecha correctamente.", txtFechaGuia);
            retorno = false;
        } else if (cmbTipo.getSelectedIndex() == 0 || cmbTipo.getSelectedIndex() == -1) {
            FarmaUtility.showMessage(this, "Debe indicar el Tipo de Documento.", cmbTipo);
            retorno = false;
        } else if (txtSerieDoc.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe ingresar la Serie del Documento.", txtSerieDoc);
            retorno = false;
        } else /* if(txtSerieDoc.getText().equals("000") || txtSerieDoc.getText().trim().length()!=3)
    {
      FarmaUtility.showMessage(this,"Debe ingresar la Serie del Documento, correctamente.",txtSerieDoc);
      retorno = false;
    }else */if (txtNroGuia.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe ingresar el Numero del Documento.", txtNroGuia);
            retorno = false;
        } else /* if(txtNroGuia.getText().trim().equals("0000000") || txtNroGuia.getText().trim().length()!=7)
    {
      FarmaUtility.showMessage(this,"Debe ingresar el Numero del Documento, correctamente.",txtNroGuia);
      retorno = false;
    }else */if (cmbTipoGuia.getSelectedIndex() == 0 || cmbTipoGuia.getSelectedIndex() == -1) {
            FarmaUtility.showMessage(this, "Debe indicar el Tipo de Origen.", cmbTipoGuia);
            retorno = false;
        } else if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_COMPETENCIA)) {
            if (txtNombre.getText().trim().length() == 0) {
                FarmaUtility.showMessage(this, "Debe ingresar el Nombre de la Tienda.", txtRuc);
                retorno = false;
            } else if (txtCiudad.getText().trim().length() == 0) {
                FarmaUtility.showMessage(this, "Debe ingresar la Ciudad.", txtCiudad);
                retorno = false;
            } else if (txtRuc.getText().trim().length() == 0 || txtRuc.getText().trim().length() < 11) {
                FarmaUtility.showMessage(this, "Debe ingresar el Ruc, correctamente.", txtRuc);
                retorno = false;
            } else if (UtilityInventario.verificaRucValido(txtRuc.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO)) {
                FarmaUtility.showMessage(this, "Ingrese un Ruc Valido.", txtRuc);
                retorno = false;
            } else if (validaFechaLimiteCC()) {
                FarmaUtility.showMessage(this, "La fecha de ingreso es menor a la fecha permitida. Verifique",
                                         txtFechaGuia);
                retorno = false;
            } else if (!validaFechaIngresoCC())
                retorno = false;

        } else {
            if (txtCodigo.getText().trim().length() == 0 || txtDescripcion.getText().trim().length() == 0) {
                FarmaUtility.showMessage(this, "Debe ingresar el Codigo y Desc de Origen.", txtCodigo);
                retorno = false;
            }
        }

        return retorno;
    }

    private void cargarDatos() {
        VariablesInventario.vFecGuia = txtFechaGuia.getText();
        VariablesInventario.vTipoDoc =
                FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO, cmbTipo.getSelectedIndex());
        VariablesInventario.vDescDoc =
                FarmaLoadCVL.getCVLDescription(ConstantsInventario.NOM_HASHTABLE_CMBTIPO, VariablesInventario.vTipoDoc);
        VariablesInventario.vNumDoc = txtSerieDoc.getText() + txtNroGuia.getText();
        VariablesInventario.vTipoOrigen =
                FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBGUIA, cmbTipoGuia.getSelectedIndex());
        VariablesInventario.vNomOrigen =
                FarmaLoadCVL.getCVLDescription(ConstantsInventario.NOM_HASHTABLE_CMBGUIA, VariablesInventario.vTipoOrigen);

        if (VariablesInventario.vTipoOrigen.equals(ConstantsPtoVenta.LISTA_MAESTRO_COMPETENCIA)) {
            VariablesInventario.vCodOrigen = FarmaVariables.vCodLocal;
            VariablesInventario.vDescOrigen = txtNombre.getText().trim();
            VariablesInventario.vTipoMotivoKardex = ConstantsPtoVenta.MOT_KARDEX_COTIZACION_COMPETENCIA;
        } else {
            VariablesInventario.vCodOrigen = txtCodigo.getText();
            VariablesInventario.vDescOrigen = txtDescripcion.getText();
        }

        VariablesInventario.vNombreTienda = txtNombre.getText().trim();
        VariablesInventario.vCiudadTienda = txtCiudad.getText().trim();
        VariablesInventario.vRucTienda = txtRuc.getText().trim();
    }

    private void habilitarCompetencia(boolean activar) {
        txtCodigo.setEnabled(!activar);
        txtNombre.setEnabled(activar);
        txtCiudad.setEnabled(activar);
        txtRuc.setEnabled(activar);
        if (!activar) {
            txtNombre.setText("");
            txtCiudad.setText("");
            txtRuc.setText("");
        } else {
            txtCodigo.setText("");
            txtDescripcion.setText("");
        }
    }

    private boolean validaFechaLimiteCC() {
        try {
            String v_retorno = DBInventario.obtieneFechaLimiteIngresoCC(txtFechaGuia.getText().trim());

            if (v_retorno.equalsIgnoreCase("TRUE")) {
                return true;
            } else
                return false;

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Error al obtener el minimo de fecha permitida para un Cotizacion de Competencia",
                                     txtFechaGuia);
            return false;
        }
    }


    private boolean validaFechaIngresoCC() {
        String diaVenta = txtFechaGuia.getText().trim();
        if (!FarmaUtility.isFechaValida(this, diaVenta, "Ingrese una Fecha correcta para la generacion de la guia")) {
            FarmaUtility.moveFocus(txtFechaGuia);
            return false;
        }
        return true;
    }

    /**
     * Carga el dialogo de listado de maestros de empresa.
     * @author dubilluz
     * @since  12.11.2007
     */
    private void listaCompetencias() {
        log.debug("Variables de la Competencia: ");
        log.debug("Desc : " + VariablesInventario.vDescripcionCompetencia + " ");
        log.debug("RUC  : " + VariablesInventario.vRucCompetencia);
        DlgListaCompetencias dlgListaComp = new DlgListaCompetencias(myParentFrame, "", true);
        dlgListaComp.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            //txtCodigoCajero.setText(FarmaUtility.caracterIzquierda(VariablesPtoVenta.vCodMaestro.trim(),3,"0"));
            txtNombre.setText(VariablesInventario.vDescripcionCompetencia.trim());
            txtRuc.setText(VariablesInventario.vRucCompetencia.trim());
        }

    }

    /**
     * Valida si se repite la cotizacion
     * @author dubilluz
     * @since  12.11.2007
     */
    private boolean validaCotizacion() {
        String num_doc = txtSerieDoc.getText().trim() + txtNroGuia.getText().trim();
        String tipo_doc =
            FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO, cmbTipo.getSelectedIndex());
        String ruc_empresa = txtRuc.getText().trim();

        try {
            String v_retorno = DBInventario.validaNumeroDocCompetencia(num_doc, tipo_doc, ruc_empresa);
            log.debug("¿EL Numero de Documento existe? : " + v_retorno);
            if (v_retorno.equalsIgnoreCase("S")) {
                cargaDatosCotizacion(num_doc, tipo_doc, ruc_empresa);
                return false;
            } else
                return true;

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al validar el numero de documento de la cotizacion.", txtFechaGuia);
            return true;
        }

    }

    /**
     * Realiza el proceso de busqueda de la empresa por el RUC
     * de no encontrarlo procedera a mostrar la lista general de las empresas
     * @author dubilluz
     * @since  12.11.2007
     */
    private void buscaRucEmpresa() {
        String ruc_empresa = txtRuc.getText().trim();
        log.debug("buscando empresa por ruc " + ruc_empresa);
        ArrayList arrayEmpresa_busqueda = new ArrayList();
        if (ruc_empresa.length() > 0) {

            try {
                DBInventario.buscaEmpresa(arrayEmpresa_busqueda, ruc_empresa);
                log.debug("Resultado ArrayEmpresa_busqueda: " + arrayEmpresa_busqueda);
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Error al buscar la empresa por Ruc.", txtRuc);
            }
            if (arrayEmpresa_busqueda.size() != 0) {
                VariablesInventario.vDescripcionCompetencia =
                        ((String)((ArrayList)arrayEmpresa_busqueda.get(0)).get(0)).trim();
                VariablesInventario.vRucCompetencia =
                        ((String)((ArrayList)arrayEmpresa_busqueda.get(0)).get(1)).trim();
                txtNombre.setText(VariablesInventario.vDescripcionCompetencia);
                txtRuc.setText(VariablesInventario.vRucCompetencia);
            } else {
                FarmaUtility.showMessage(this, "La empresa con el RUC " + ruc_empresa + " no se encuentra registrada.",
                                         null);
                listaCompetencias();
            }
        } else
            listaCompetencias();
    }

    /**
     * Se carga los datos de la cotizacion que ya existe para mostrarla y saber si desea
     * anular y generarla
     * @author dubilluz
     * @since  26.11.2007
     */
    public void cargaDatosCotizacion(String num_doc, String tipo_doc, String ruc_empresa) {
        VariablesInventario.vArrayDatosCotizacion = new ArrayList();
        try {
            DBInventario.getDatosCotizacion(VariablesInventario.vArrayDatosCotizacion, num_doc, tipo_doc, ruc_empresa);
            log.debug("Se cargaron los datos de la cotizacion : " + VariablesInventario.vArrayDatosCotizacion);
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener los datos de la cotizacion.", txtNroGuia);
        }
    }


}
