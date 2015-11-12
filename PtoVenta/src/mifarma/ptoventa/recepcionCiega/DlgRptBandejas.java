package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
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
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgRptBandejas.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ 16.06.2015 Creación<br>
 * <br>
 * @author Diego UBilluz<br>
 * @version 1.0<br>
 *
 */
public class DlgRptBandejas extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRptBandejas.class);

    private FarmaTableModel tbmlBandejas;
    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblRegistroVentas = new JTable();
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JTextField txtBandeja = new JTextField();

    private JScrollPane jcpPendiente = new JScrollPane();
    private JTable tblPendiente = new JTable();
    private FarmaTableModel tableModelPendiente;

    private JScrollPane jcpPorDevolver = new JScrollPane();
    private JTable tblPorDevolver = new JTable();
    private FarmaTableModel tableModelPorDevolver;
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel lblinfPendEnvio = new JLabel();
    private JLabel lblinfPorEnvio = new JLabel();
    private JEditorPane jepPorDevolver = new JEditorPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JEditorPane jepPorRecepcionar = new JEditorPane();


    public boolean vIsLazerIngreso = false;
    double vTiempoMaquina = 400; // MILISEGUNDOS
    long tmpT1, tmpT2, OldtmpT2;

    public DlgRptBandejas() {
        this(null, "", false);
    }

    public DlgRptBandejas(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

  private void jbInit() throws Exception {
        this.setSize(new Dimension(804, 541));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Lista de Bandejas para enviar");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 15, 780, 70));
        pnlCriterioBusqueda.setFocusable(false);
        pnlTitulo.setBounds(new Rectangle(10, 85, 780, 15));
        pnlTitulo.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(40, 405, 385, 50));
        jScrollPane1.setFocusable(false);

        jcpPendiente.setBounds(new Rectangle(10, 100, 325, 330));
        jcpPendiente.setFocusable(false);

        jcpPorDevolver.setBounds(new Rectangle(360, 100, 300, 330));
        jcpPorDevolver.setFocusable(false);


        jPanel1.setBounds(new Rectangle(100, 0, 170, 15));
        jPanel1.setLayout(null);
        jPanel2.setBounds(new Rectangle(520, 0, 135, 15));
        jPanel2.setLayout(null);
        jLabel3.setText("<html>Pendiente de Env\u00edo</html>");
        jLabel3.setBounds(new Rectangle(20, 0, 140, 15));
        jLabel3.setFont(new Font("Tahoma", 1, 14));
        jLabel3.setForeground(new Color(0, 0, 214));
        jLabel4.setText("<html>Por Devolver</html>");
        jLabel4.setBounds(new Rectangle(15, 0, 110, 15));
        jLabel4.setFont(new Font("Tahoma", 1, 14));
        jLabel4.setForeground(new Color(231, 0, 0));
        lblinfPendEnvio.setText("<html><center>Tiene 4 Bandejas  \"PENDIENTES DE ENVIAR\"</center></html>");
        lblinfPendEnvio.setBounds(new Rectangle(60, 475, 200, 35));
        lblinfPendEnvio.setFont(new Font("Tahoma", 1, 14));
        lblinfPendEnvio.setForeground(new Color(0, 66, 198));
        lblinfPendEnvio.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        lblinfPorEnvio.setText("<html><center>4 Bandejas<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp\"PARA DEVOLVER\"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</center></html>");
        lblinfPorEnvio.setBounds(new Rectangle(470, 475, 210, 35));
        lblinfPorEnvio.setFont(new Font("Tahoma", 1, 14));
        lblinfPorEnvio.setForeground(new Color(198, 0, 0));
        lblinfPorEnvio.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        jepPorDevolver.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    jepPorDevolver_keyPressed(e);
                }
        });
        jepPorDevolver.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                jepPorDevolver_mouseClicked(e);
            }
        });
        jScrollPane2.setBounds(new Rectangle(410, 100, 380, 365));
        jScrollPane2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                    jScrollPane2_mouseClicked(e);
                }
        });
        jScrollPane3.setBounds(new Rectangle(10, 100, 375, 365));
        jScrollPane3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                jScrollPane3_mouseClicked(e);
            }
        });
        jepPorRecepcionar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                jepPorRecepcionar_mouseClicked(e);
            }

            public void mouseEntered(MouseEvent e) {
                jepPorRecepcionar_mouseEntered(e);
            }
        });
        jepPorRecepcionar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jepPorRecepcionar_keyPressed(e);
            }
        });
        tblRegistroVentas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblRegistroVentas_keyPressed(e);
            }
        });
        btnPeriodo.setText("Desde ");
        btnPeriodo.setBounds(new Rectangle(10, 5, 60, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.setFocusable(false);
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        txtFechaIni.setBounds(new Rectangle(95, 10, 110, 20));
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(355, 10, 100, 20));
        txtFechaFin.setLengthText(10);
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(535, 5, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        lblEsc.setBounds(new Rectangle(695, 475, 95, 35));
        lblEsc.setText("<HTML><CENTER>[ESC] Cerrar<BR></CENTER></HTML>");
        lblEsc.setFocusable(false);
        lblF2.setBounds(new Rectangle(265, 475, 120, 35));
        lblF2.setText("<HTML><CENTER>[F1] B\u00fasqueda de Rango Fechas<BR></CENTER></HTML>");
        // jLabelFunction1.setBounds(new Rectangle(280, 370, 130, 20));
        //jLabelFunction1.setText("[ F8 ] Guardar Archivo");
        lblF2.setFocusable(false);
        //jContentPane.add(jLabelFunction1, null);
        jLabel1.setText("hasta");
        jLabel1.setBounds(new Rectangle(255, 10, 34, 14));
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        jLabel1.setForeground(SystemColor.window);
        jLabel2.setText("<html><center>Ingresa Bandeja/Bulto</center></html>");
        jLabel2.setBounds(new Rectangle(10, 30, 80, 30));
        jLabel2.setForeground(SystemColor.window);
        jLabel2.setFont(new Font("Tahoma", 1, 11));
        txtBandeja.setBounds(new Rectangle(95, 35, 225, 20));
        txtBandeja.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBandeja_keyPressed(e);
            }
        });

        jScrollPane1.getViewport().add(tblRegistroVentas, null);
        jScrollPane2.getViewport().add(jepPorDevolver, null);
        jScrollPane3.getViewport().add(jepPorRecepcionar, null);
        jContentPane.add(jScrollPane3, null);
        jContentPane.add(jScrollPane2, null);
        jContentPane.add(lblinfPorEnvio, null);
        jContentPane.add(lblinfPendEnvio, null);
        jContentPane.add(jScrollPane1, null);
        jcpPendiente.getViewport().add(tblPendiente, null);
        jcpPendiente.setVisible(false);
        jContentPane.add(jcpPendiente, null);
        jcpPorDevolver.getViewport().add(tblPorDevolver, null);
        jcpPorDevolver.setVisible(false);
        jContentPane.add(jcpPorDevolver, null);

        jContentPane.add(lblEsc, null);
        jPanel2.add(jLabel4, null);
        pnlTitulo.add(jPanel2, null);
        jPanel1.add(jLabel3, null);
        pnlTitulo.add(jPanel1, null);
        jContentPane.add(pnlTitulo, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        jContentPane.add(lblF2, null);
        tblRegistroVentas.setFocusable(false);
        pnlCriterioBusqueda.add(txtBandeja, null);
        pnlCriterioBusqueda.add(jLabel2, null);
        pnlCriterioBusqueda.add(jLabel1, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        jScrollPane1.setVisible(false);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initTableListaRegistroVentas();
    };

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaRegistroVentas() {
        tbmlBandejas =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaRptBandeja, ConstantsRecepCiega.defaultValuesListaRptBandeja,
                                    0);
        FarmaUtility.initSimpleList(tblRegistroVentas, tbmlBandejas, ConstantsRecepCiega.columnsListaRptBandeja);

        tableModelPendiente =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaRptBandejas, ConstantsRecepCiega.defaultValuesListaRptBandejas,
                                    0);
        FarmaUtility.initSimpleList(tblPendiente, tableModelPendiente, ConstantsRecepCiega.columnsListaRptBandejas);


        tableModelPorDevolver =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaRptBandejas, ConstantsRecepCiega.defaultValuesListaRptBandejas,
                                    0);
        FarmaUtility.initSimpleList(tblPorDevolver, tableModelPorDevolver, ConstantsRecepCiega.columnsListaRptBandejas);

        jScrollPane1.setVisible(false);
    }

    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            btnBuscar.doClick();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void tblRegistroVentas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
        vListaPorDefecto();
        jepPorDevolver.setEditable(false);
        jepPorRecepcionar.setEditable(false);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();
    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblRegistroVentas.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblRegistroVentas);
        }
    }

    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRegistroVentas, null, 0);
        if (UtilityPtoVenta.verificaVK_F1(e)) {
            FarmaUtility.moveFocus(txtFechaIni);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void buscaRegistroVentas(String pFechaInicio, String pFechaFin) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        cargaRegistroVentas();
    }

    private void cargaRegistroVentas() {
        try {
            log.debug(VariablesReporte.vFechaInicio);
            log.debug(VariablesReporte.vFechaFin);
            DBRecepCiega.getListaBandejas(tbmlBandejas, VariablesReporte.vFechaInicio, VariablesReporte.vFechaFin);
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultados para la búsqueda", txtFechaIni);
                return;
            } else {
                FarmaUtility.moveFocus(txtFechaIni);
            }
            FarmaUtility.ordenar(tblRegistroVentas, tbmlBandejas, 3, FarmaConstants.ORDEN_ASCENDENTE);

        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n" +
                    sql.getMessage(), txtFechaIni);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno;
    }


    private void busqueda() {
        if (validarCampos()) {
            txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
            txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
            String FechaInicio = txtFechaIni.getText().trim();
            String FechaFin = txtFechaFin.getText().trim();
            if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
                char primerkeyCharFI = FechaInicio.charAt(0);
                char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length() - 1);
                char primerkeyCharFF = FechaFin.charAt(0);
                char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                    !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                    buscaRegistroVentas(FechaInicio, FechaFin);
                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", txtFechaIni);
            } else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni);

        }
        cargaTablasVisual();

        FarmaUtility.moveFocus(txtBandeja);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void marcarParaDevolver(int i) {
        try {
            String pNroBandeja = FarmaUtility.getValueFieldArrayList(tbmlBandejas.data, i, 0).trim();
            String pNroRecep = FarmaUtility.getValueFieldArrayList(tbmlBandejas.data, i, 3).trim();
            String pEstado = FarmaUtility.getValueFieldArrayList(tbmlBandejas.data, i, 5).trim();
            if (pEstado.trim().equalsIgnoreCase("R")) {
                //if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro que desea marcar para devolver en la próxima recepción?"))
                //{
                DBRecepCiega.saveParaDevolver(pNroBandeja, pNroRecep);
                FarmaUtility.aceptarTransaccion();
                if (txtFechaIni.getText().length() > 0 && txtFechaFin.getText().length() > 0)
                    btnBuscar.doClick();
                else
                    vListaPorDefecto();

                //FarmaUtility.showMessage(this, "Se marcó para devolver en la próxima recepción.", null);
                //}
            } else
                FarmaUtility.showMessage(this, "Ya se encuentra marcado por devolver.", null);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error("",e);
            FarmaUtility.showMessage(this, "Error en el proceso de para devolver.", null);
        }
    }


    private void marcarParaRevertir(int i) {
        try {
            String pNroBandeja = FarmaUtility.getValueFieldArrayList(tbmlBandejas.data, i, 0).trim();
            String pNroRecep = FarmaUtility.getValueFieldArrayList(tbmlBandejas.data, i, 3).trim();
            String pEstado = FarmaUtility.getValueFieldArrayList(tbmlBandejas.data, i, 5).trim();
            if (pEstado.trim().equalsIgnoreCase("P")) {
                //if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro que desea volver a su estado inicial?")) {
                DBRecepCiega.saveParaRevertir(pNroBandeja, pNroRecep);
                FarmaUtility.aceptarTransaccion();
                if (txtFechaIni.getText().length() > 0 && txtFechaFin.getText().length() > 0)
                    btnBuscar.doClick();
                else
                    vListaPorDefecto();

                //FarmaUtility.showMessage(this, "Se marcó para devolver en la próxima recepción.", null);
                //}
            } else
                FarmaUtility.showMessage(this, "No se puede revertir algo que no se marcó para devolver.", null);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error("",e);
            FarmaUtility.showMessage(this, "Error en el proceso de revertir.", null);
        }
    }

    private void vListaPorDefecto() {
        try {
            DBRecepCiega.getListaBandejasDefault(tbmlBandejas);
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultados para la búsqueda", txtFechaIni);
                return;
            } else {
                FarmaUtility.moveFocus(txtFechaIni);
            }
            FarmaUtility.ordenar(tblRegistroVentas, tbmlBandejas, 3, FarmaConstants.ORDEN_ASCENDENTE);
            FarmaUtility.moveFocus(txtBandeja);
        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n" +
                    sql.getMessage(), txtFechaIni);
        }

        cargaTablasVisual();
    }

    private void txtBandeja_keyPressed(KeyEvent e) {
        setIsLazerTeclado(e, txtBandeja);

        FarmaGridUtils.aceptarTeclaPresionada(e, tblRegistroVentas, null, 0);
        if (UtilityPtoVenta.verificaVK_F1(e)) {
            FarmaUtility.moveFocus(txtFechaIni);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtBandeja.setText(FarmaUtility.completeWithSymbol(txtBandeja.getText(), 10, "0", "I"));
            operaBandeja(txtBandeja.getText().trim());
            txtBandeja.setText("");
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
    }

    private void operaBandeja(String pCodBandejaBusqueda) {
        if (isNumeroValido(pCodBandejaBusqueda)) {
            int pos = -1;
            for (int i = 0; i < tblRegistroVentas.getRowCount(); i++) {
                String pCodBandeja = FarmaUtility.getValueFieldArrayListW(tbmlBandejas.data, i, 0);
                if (pCodBandeja.trim().equalsIgnoreCase(pCodBandejaBusqueda)) {
                    pos = i;
                    break;
                }
            }
            if (pos >= 0) {
                String pEstado = FarmaUtility.getValueFieldArrayListW(tbmlBandejas.data, pos, 5);
                if (pEstado.trim().equalsIgnoreCase("P")) {
                    if (JConfirmDialog.rptaConfirmDialog(this,
                                                         "Ha seleccionado la bandeja Nº " + pCodBandejaBusqueda + " \n\n" +
                            "¿Desea volver al estado \"PENDIENTE DE ENVÍO\"?")) {
                        marcarParaRevertir(pos);
                    }
                } else if (pEstado.trim().equalsIgnoreCase("R")) {
                    if (JConfirmDialog.rptaConfirmDialog(this,
                                                         "Ha seleccionado la bandeja Nº " + pCodBandejaBusqueda + ".\n\n" +
                            "¿Desea \"DEVOLVERLA\" en su próximo envío?")) {
                        marcarParaDevolver(pos);
                    }
                }
            } else {
                //FarmaUtility.showMessage(this, "No existe la bandeja ingresada en el listado de búsqueda.", txtBandeja);
                // SI EXISTE EN EL MAESTRO DE BANDEJAS //
                /////////////////////////////////////////
                if (UtilityRecepCiega.isValidoBandeja(pCodBandejaBusqueda)) {
                    // SI EXISTE EN EL MAESTRO //
                    // CREA UNA RECEPCION y AGREGA ESTO ES INMEDIATO
                    if (UtilityRecepCiega.isValidoBandejaDevol(txtBandeja.getText())) {
                        creaBandejaExiste(pCodBandejaBusqueda);
                    } else
                        FarmaUtility.showMessage(this, "La bulto ingresado no es el tipo correcto para devolver.",
                                                 txtBandeja);
                } else {
                    String pBandejaOld = txtBandeja.getText().trim();
                    txtBandeja.setText("***********************");
                    // NO EXISTE PEDIRA CONFIRMARCION 2 veces.
                    DlgReConfirmBandeja dlgRepetir =
                        new DlgReConfirmBandeja(myParentFrame, "", true);
                    dlgRepetir.setVValorValidar(pCodBandejaBusqueda.trim());
                    dlgRepetir.setVisible(true);
                    if (FarmaVariables.vAceptar && dlgRepetir.isIsValidoConfirmacion()) {
                        //Acepto y es el mismo valor se va grabar "POR DEVOLVER"
                        creaBandejaNoExiste(pCodBandejaBusqueda);
                    }
                    txtBandeja.setText(pBandejaOld);
                }

                if (txtFechaIni.getText().length() > 0 && txtFechaFin.getText().length() > 0)
                    btnBuscar.doClick();
                else
                    vListaPorDefecto();
            }
        } else
            FarmaUtility.showMessage(this, "Por favor de ingresar una bandeja válida.", txtBandeja);
    }

    public void cargaTablasVisual() {
        if (tbmlBandejas.data.size() > 0) {
            tableModelPendiente.data.clear();
            tableModelPorDevolver.data.clear();
            for (int i = 0; i < tbmlBandejas.data.size(); i++) {
                String pEstado = FarmaUtility.getValueFieldArrayList(tbmlBandejas.data, i, 5);
                if (pEstado.trim().equalsIgnoreCase("P")) {
                    ArrayList vFila = new ArrayList();
                    vFila.add(FarmaUtility.getValueFieldArrayList(tbmlBandejas.data, i, 0));
                    vFila.add(FarmaUtility.getValueFieldArrayList(tbmlBandejas.data, i, 1));
                    tableModelPorDevolver.insertRow(vFila);
                } else if (pEstado.trim().equalsIgnoreCase("R")) {
                    ArrayList vFila = new ArrayList();
                    vFila.add(FarmaUtility.getValueFieldArrayList(tbmlBandejas.data, i, 0));
                    vFila.add(FarmaUtility.getValueFieldArrayList(tbmlBandejas.data, i, 1));
                    tableModelPendiente.insertRow(vFila);
                }
            }

            tblPendiente.repaint();
            tblPorDevolver.repaint();
        } else {
            tableModelPendiente.data.clear();
            tableModelPorDevolver.data.clear();
            tblPendiente.repaint();
            tblPorDevolver.repaint();
        }

        if (tableModelPendiente.data.size() > 0) {
            if (tableModelPendiente.data.size() == 1)
                lblinfPendEnvio.setText("<html><center>" + tableModelPendiente.data.size() +
                                        " Bandeja  <br>\"PENDIENTES DE ENVIAR\"</center></html>");
            else
                lblinfPendEnvio.setText("<html><center>" + tableModelPendiente.data.size() +
                                        " Bandejas  <br>\"PENDIENTES DE ENVIAR\"</center></html>");
            lblinfPendEnvio.setVisible(true);
        } else
            lblinfPendEnvio.setVisible(false);

        if (tableModelPorDevolver.data.size() > 0) {
            if (tableModelPorDevolver.data.size() == 1)
                lblinfPorEnvio.setText("<html><center>" + tableModelPorDevolver.data.size() +
                                       " Bandeja <br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp\"PARA DEVOLVER\"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</center></html>");
            else
                lblinfPorEnvio.setText("<html><center>" + tableModelPorDevolver.data.size() +
                                       " Bandejas <br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp\"PARA DEVOLVER\"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</center></html>");
            lblinfPorEnvio.setVisible(true);
        } else
            lblinfPorEnvio.setVisible(false);
        jepPorDevolver.setContentType("text");
        jepPorDevolver.setText("");
        jepPorRecepcionar.setContentType("text");
        jepPorRecepcionar.setText("");
        cargaHtmlPorDevolver();
        cargaHtmlPorRecepcionar();
    }

    public void cargaHtmlPorDevolver() {
        if (tableModelPorDevolver.data.size() > 0) {
            jepPorDevolver.setContentType("text/html");
            String pCodHtml = "";
            pCodHtml = "<style type=\"text/css\">\n" +
                    "body {\n" +
                    "    font-family: 'trebuchet MS', 'Lucida sans', Arial;\n" +
                    "    color: #444;\n" +
                    "    margin: 10px auto;\n" +
                    "}\n" +
                    "table {\n" +
                    "    border-spacing: 0;\n" +
                    "    font-size: 10;\n" +
                    "}\n" +
                    "table td{\n" +
                    "    width: 50px;\n" +
                    "}\n" +
                    ".bordered td, .bordered th {\n" +
                    "    padding: 4px;\n" +
                    "    text-align: center;    \n" +
                    "}.bordered th {\n" +
                    "    background-color: #ffefd6;\n" +
                    "}" + "</style><div><table border=1 class=\"bordered\">" + "<tr class=\"style4\">" +
                    "<th >Fecha</th>" + "<th><b>Bandeja</b></th>" + "<th></th>" + "<th></th>" + "<th></th>" + "</tr>";
            int pCtdColBandeja = 4;
            ArrayList vListFecha = new ArrayList();
            vListFecha = getFechaRecep(tableModelPorDevolver.data);
            for (int i = 0; i < vListFecha.size(); i++) {
                ArrayList vListBandeja = new ArrayList();
                String pFecha = vListFecha.get(i).toString().trim();
                vListBandeja = getListBandejaFecha(tableModelPorDevolver.data, pFecha);
                if (vListBandeja.size() <= pCtdColBandeja) {
                    //sin colspan
                    pCodHtml = pCodHtml + "<tr>" + "<td>" + pFecha + "</td>";
                    int pos = 0;
                    for (int k = 0; k < vListBandeja.size(); k++) {
                        pCodHtml = pCodHtml + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                        pos++;
                    }
                    for (int j = pos + 1; j <= pCtdColBandeja; j++) {
                        pCodHtml = pCodHtml + "<td></td>";
                    }
                    pCodHtml = pCodHtml + "</tr>";
                } else {

                    int pResto = 0;
                    if ((vListBandeja.size() % pCtdColBandeja) > 0)
                        pResto = 1;

                    pCodHtml =
                            pCodHtml + "<tr>" + "<td rowspan=" + ((int)(vListBandeja.size() / pCtdColBandeja) + pResto) +
                            ">" + pFecha + "</td>";
                    int pos = 0;
                    boolean pFinPrimera = false;
                    for (int k = 0; k < vListBandeja.size(); k++) {
                        pos++;
                        if (!pFinPrimera) {
                            pCodHtml = pCodHtml + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                            if (pos == pCtdColBandeja) {
                                pCodHtml = pCodHtml + "</tr>";
                                pFinPrimera = true;
                                pos = 0;
                            }
                        } else {
                            if (pos == 1) {
                                pCodHtml =
                                        pCodHtml + "<tr>" + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                            } else if (pos == pCtdColBandeja) {
                                pCodHtml = pCodHtml + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                                pCodHtml = pCodHtml + "</tr>";
                                pFinPrimera = true;
                                pos = 0;
                            } else {
                                pCodHtml = pCodHtml + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                            }
                        }
                    }
                    if (pos > 0) {
                        for (int j = pos + 1; j <= pCtdColBandeja; j++) {
                            pCodHtml = pCodHtml + "<td></td>";
                            if (j == pCtdColBandeja)
                                pCodHtml = pCodHtml + "</tr>";
                        }
                    } else {
                        pCodHtml = pCodHtml + "</tr>";
                    }
                }
            }

            pCodHtml += "</table></div>";
            log.info("" + pCodHtml);
            jepPorDevolver.setText(pCodHtml);
        } else
            jepPorDevolver.setText("");
    }

    public void cargaHtmlPorRecepcionar() {
        if (tableModelPendiente.data.size() > 0) {
            jepPorRecepcionar.setContentType("text/html");
            String pCodHtml = " ";
            pCodHtml = "<style type=\"text/css\">\n" +
                    "body {\n" +
                    "    font-family: 'trebuchet MS', 'Lucida sans', Arial;\n" +
                    "    color: #444;" + "    margin: 10px auto;\n" +
                    "}\n" +
                    "table {\n" +
                    "    border-spacing: 0;\n" +
                    "    font-size: 10;\n" +
                    "}\n" +
                    "table td{\n" +
                    "    width: 50px;\n" +
                    "}\n" +
                    ".bordered td, .bordered th {\n" +
                    "    padding: 4px;\n" +
                    "    text-align: center;    \n" +
                    "}.bordered th {\n" +
                    "    background-color: #ffefd6;\n" +
                    "}" + "</style><div ><table border=1 class=\"bordered\">" + "<tr class=\"style4\">" +
                    "<th >Fecha</th>" + "<th><b>Bandeja</b></th>" + "<th></th>" + "<th></th>" + "<th></th>" + "</tr>";
            int pCtdColBandeja = 4;
            ArrayList vListFecha = new ArrayList();
            vListFecha = getFechaRecep(tableModelPendiente.data);
            for (int i = 0; i < vListFecha.size(); i++) {
                ArrayList vListBandeja = new ArrayList();
                String pFecha = vListFecha.get(i).toString().trim();
                vListBandeja = getListBandejaFecha(tableModelPendiente.data, pFecha);
                if (vListBandeja.size() <= pCtdColBandeja) {
                    //sin colspan
                    pCodHtml = pCodHtml + "<tr>" + "<td>" + pFecha + "</td>";
                    int pos = 0;
                    for (int k = 0; k < vListBandeja.size(); k++) {
                        pCodHtml = pCodHtml + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                        pos++;
                    }
                    for (int j = pos + 1; j <= pCtdColBandeja; j++) {
                        pCodHtml = pCodHtml + "<td></td>";
                    }
                    pCodHtml = pCodHtml + "</tr>";
                } else {

                    int pResto = 0;
                    if ((vListBandeja.size() % pCtdColBandeja) > 0)
                        pResto = 1;

                    pCodHtml =
                            pCodHtml + "<tr>" + "<td rowspan=" + ((int)(vListBandeja.size() / pCtdColBandeja) + pResto) +
                            ">" + pFecha + "</td>";
                    int pos = 0;
                    boolean pFinPrimera = false;
                    for (int k = 0; k < vListBandeja.size(); k++) {
                        pos++;
                        if (!pFinPrimera) {
                            pCodHtml = pCodHtml + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                            if (pos == pCtdColBandeja) {
                                pCodHtml = pCodHtml + "</tr>";
                                pFinPrimera = true;
                                pos = 0;
                            }
                        } else {
                            if (pos == 1) {
                                //pCodHtml =pCodHtml+  "<td>"+vListBandeja.get(k).toString().trim()+"</td>";
                                pCodHtml =
                                        pCodHtml + "<tr>" + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                            } else if (pos == pCtdColBandeja) {
                                pCodHtml = pCodHtml + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                                pCodHtml = pCodHtml + "</tr>";
                                pFinPrimera = true;
                                pos = 0;
                            } else {
                                pCodHtml = pCodHtml + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                            }
                        }
                    }
                    if (pos > 0) {
                        for (int j = pos + 1; j <= pCtdColBandeja; j++) {
                            pCodHtml = pCodHtml + "<td></td>";
                            if (j == pCtdColBandeja)
                                pCodHtml = pCodHtml + "</tr>";
                        }
                    } else {
                        pCodHtml = pCodHtml + "</tr>";
                    }
                }
            }

            pCodHtml += "</table></div>";
            log.info("" + pCodHtml);
            jepPorRecepcionar.setText(pCodHtml);
        } else
            jepPorRecepcionar.setText("");
    }

    public ArrayList getListBandejaFecha(ArrayList vLista, String pFecha) {
        String pFechaAc = "";
        ArrayList vListaFechaBandeja = new ArrayList();
        for (int i = 0; i < vLista.size(); i++) {
            pFechaAc = FarmaUtility.getValueFieldArrayList(vLista, i, 1);
            if (pFecha.trim().equalsIgnoreCase(pFechaAc)) {
                vListaFechaBandeja.add(FarmaUtility.getValueFieldArrayList(vLista, i, 0));
            }
        }
        return vListaFechaBandeja;
    }

    public ArrayList getFechaRecep(ArrayList vLista) {
        String pFechaAc = "";
        ArrayList vListaFecha = new ArrayList();
        boolean vExiste = false;
        for (int i = 0; i < vLista.size(); i++) {
            pFechaAc = FarmaUtility.getValueFieldArrayList(vLista, i, 1);
            vExiste = false;
            for (int j = 0; j < vListaFecha.size(); j++) {
                if (vListaFecha.get(j).toString().trim().equalsIgnoreCase(pFechaAc.trim())) {
                    vExiste = true;
                    break;
                }
            }
            if (!vExiste)
                vListaFecha.add(pFechaAc);
        }
        return vListaFecha;
    }

    private void jScrollPane3_mouseClicked(MouseEvent e) {
        FarmaUtility.moveFocus(txtBandeja);
    }

    private void jScrollPane2_mouseClicked(MouseEvent e) {
        FarmaUtility.moveFocus(txtBandeja);
    }

    private void creaBandejaExiste(String pBandeja) {
        UtilityRecepCiega.newBandejaExiste(pBandeja);
    }

    private void creaBandejaNoExiste(String pBandeja) {
        UtilityRecepCiega.newBandejaNoExiste(pBandeja);
    }

    private void jepPorRecepcionar_mouseClicked(MouseEvent e) {
        FarmaUtility.moveFocus(txtBandeja);
    }

    private void jepPorRecepcionar_keyPressed(KeyEvent e) {
        FarmaUtility.moveFocus(txtBandeja);
    }

    private void jepPorDevolver_keyPressed(KeyEvent e) {
        FarmaUtility.moveFocus(txtBandeja);
    }

    private void jepPorDevolver_mouseClicked(MouseEvent e) {
        FarmaUtility.moveFocus(txtBandeja);
    }

    private void jepPorRecepcionar_mouseEntered(MouseEvent e) {
        FarmaUtility.moveFocus(txtBandeja);
    }


    public void setVIsLazerIngreso(boolean vIsLazerHojaResumen) {
        this.vIsLazerIngreso = vIsLazerHojaResumen;
    }

    public boolean isVIsLazerIngreso() {
        return vIsLazerIngreso;
    }

    public void setIsLazerTeclado(KeyEvent e, Object jTexto) {
        if (((JTextField)jTexto).getText().length() == 0)
            tmpT1 = System.currentTimeMillis();

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            vIsLazerIngreso = false;
            tmpT2 = System.currentTimeMillis();
            log.info("Tiem 2 " + (tmpT2));
            log.info("Tiem 1 " + (tmpT1));
            log.info("Tiempo de ingreso y ENTER " + (tmpT2 - tmpT1));
            if ((tmpT2 - tmpT1) <= vTiempoMaquina && ((JTextField)jTexto).getText().length() > 0) {
                vIsLazerIngreso = true;
                tmpT1 = 0;
                setVIsLazerIngreso(true);
                log.info("INGRESO CON LAZER");
                //FarmaUtility.showMessage(this, "Se ingreso con LAZER.", txtHojaRes);
            } else {
                vIsLazerIngreso = false;
                tmpT1 = 0;
                setVIsLazerIngreso(false);
                log.info("INGRESO CON TECLADO A MANO");
                //FarmaUtility.showMessage(this, "Se ingreso con Teclado a mano.", txtHojaRes);
            }
        }
    }

    private boolean isNumeroValido(String pCodBandejaBusqueda) {
        double valor = 0.0;
        try {
            valor = Double.parseDouble(pCodBandejaBusqueda.trim());
        } catch (NumberFormatException nfe) {
            valor = 0.0;
        }

        if (valor > 0)
            return true;
        else
            return false;
    }
}
