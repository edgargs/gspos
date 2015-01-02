package mifarma.ptoventa.administracion.fondoSencillo;


import com.gs.mifarma.componentes.JButtonLabel;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.fondoSencillo.reference.ConstantsFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.DBFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.UtilityFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.VariablesFondoSencillo;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgHistoricoFondoSencillo extends JDialog {
    //Declarando Variables Globales
    Frame myParentFrame;
    FarmaTableModel tableModelHistorico;

    private static final Logger log = LoggerFactory.getLogger(DlgHistoricoFondoSencillo.class);

    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnFechaInicio = new JButtonLabel();
    private JScrollPane scrListaCajero = new JScrollPane();
    private JTable tblCajeros = new JTable();
    private JLabelFunction btnImprimir = new JLabelFunction();
    private JLabelFunction btnSalir = new JLabelFunction();
    private JPanelHeader pnlHead = new JPanelHeader();
    private JButtonLabel btnListado = new JButtonLabel();

    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JLabel lblInicio = new JLabel();
    private JLabel lblFin = new JLabel();
    private JLabelFunction btnAceptarDevolucion = new JLabelFunction();
    private JLabelFunction btnF4 = new JLabelFunction();

    private static final int ColFecha = 0;
    private static final int ColTipoLargo = 1;
    private static final int ColUsuOrigenLargo = 2;
    private static final int ColUsuDestinoLargo = 3;
    private static final int ColMonto = 4;
    private static final int ColCaja = 6;
    private static final int ColTurno = 7;
    private static final int ColSecFondSen = 8;
    private static final int ColUsuOrigen = 9;
    private static final int ColUsuDestino = 10;
    private static final int ColIndTipo = 11;
    private static final int ColEstado = 12;
    private JLabelFunction btnF5 = new JLabelFunction();
    private JComboBox cmbMostrar = new JComboBox();
    private JButtonLabel btnMostrar = new JButtonLabel();
    private JLabelFunction btnF6 = new JLabelFunction();

    //JMIRANDA 20.05.2010
    private boolean IndInicialCargaHistorico = true;


    public DlgHistoricoFondoSencillo() {
        this(null, "", false);
    }

    public DlgHistoricoFondoSencillo(Frame parent, String title, boolean modal) {
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
        //***Valores Básicos Pantalla ***
        this.setSize(new Dimension(782, 377));
        this.getContentPane().setLayout(borderLayout1);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Histórico de Fondo de Sencillo");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        //***fin ***

        pnlHead.add(btnMostrar, null);
        pnlHead.add(cmbMostrar, null);
        pnlHead.add(lblFin, null);
        pnlHead.add(btnBuscar, null);
        pnlHead.add(txtFechaFin, null);
        pnlHead.add(txtFechaIni, null);
        pnlHead.add(btnFechaInicio, null);
        jContentPane.add(btnF6, null);
        jContentPane.add(btnF5, null);
        jContentPane.add(btnF4, null);
        jContentPane.add(btnAceptarDevolucion, null);
        jContentPane.add(pnlHead, null);
        jContentPane.add(btnSalir, null);
        jContentPane.add(btnImprimir, null);
        scrListaCajero.getViewport().add(tblCajeros, null);
        jContentPane.add(scrListaCajero, null);

        pnlTitulo.add(btnListado, null);
        jContentPane.add(pnlTitulo, null);
        jContentPane.setBounds(new Rectangle(0, 0, 545, 340));
        pnlTitulo.setBounds(new Rectangle(5, 45, 765, 20));
        btnFechaInicio.setText("Desde:");
        btnFechaInicio.setMnemonic('D');
        btnFechaInicio.setBounds(new Rectangle(10, 10, 80, 20));
        btnFechaInicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFechaInicio_actionPerformed(e);
            }
        });
        scrListaCajero.setBounds(new Rectangle(5, 65, 765, 255));
        tblCajeros.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblCajeros_keyPressed(e);
            }
        });
        btnImprimir.setBounds(new Rectangle(160, 325, 85, 20));
        btnImprimir.setText("[F3] Imprimir");
        btnSalir.setBounds(new Rectangle(690, 325, 80, 20));
        btnSalir.setText("[ESC] Cerrar");
        pnlHead.setBounds(new Rectangle(5, 5, 765, 40));
        btnListado.setText("Listado de Fondo de Sencillo");
        btnListado.setMnemonic('l');
        btnListado.setBounds(new Rectangle(10, 0, 170, 20));

        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });

        txtFechaIni.setBounds(new Rectangle(60, 10, 110, 20));
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFechaIni_keyTyped(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });

        txtFechaFin.setBounds(new Rectangle(230, 10, 110, 20));
        txtFechaFin.setLengthText(10);

        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFechaFin_keyTyped(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setMnemonic('b');
        btnBuscar.setBounds(new Rectangle(630, 10, 75, 20));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });

        lblFin.setText("Hasta:");
        lblFin.setBounds(new Rectangle(185, 15, 40, 15));
        lblFin.setForeground(Color.white);
        lblFin.setFont(new Font("SansSerif", 1, 11));
        btnAceptarDevolucion.setBounds(new Rectangle(5, 325, 150, 20));
        btnAceptarDevolucion.setText("[F2] Aceptar Devolución");
        btnF4.setBounds(new Rectangle(250, 325, 140, 20));
        btnF4.setText("[F4] Anular Asignación");
        btnF5.setBounds(new Rectangle(520, 325, 155, 20));
        btnF5.setText("[F5] Rechazar Devolución");
        btnF5.setVisible(false);
        cmbMostrar.setBounds(new Rectangle(420, 10, 190, 20));
        cmbMostrar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbMostrar_keyPressed(e);
            }
        });

        cmbMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cmbMostrar_actionPerformed(e);
            }
        });
        btnMostrar.setText("Mostrar:");
        btnMostrar.setMnemonic('m');
        btnMostrar.setBounds(new Rectangle(355, 15, 75, 15));
        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMostrar_actionPerformed(e);
            }
        });
        btnF6.setBounds(new Rectangle(5, 350, 75, 20));
        btnF6.setText("[F6] Filtrar");
        btnF6.setVisible(false);

    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initCombos();
        initTableListaCajeros();
        IndInicialCargaHistorico = false;
        //permite que no se muevan las columnas de Jtable
        tblCajeros.getTableHeader().setReorderingAllowed(false);
        //permite que no se cambien el tamaño de la columna del Jtable
        tblCajeros.getTableHeader().setResizingAllowed(false);
        setJTable(tblCajeros, txtFechaIni);
    }

    private void initTableListaCajeros() {
        tableModelHistorico =
                new FarmaTableModel(ConstantsFondoSencillo.columnsListaHistorico, ConstantsFondoSencillo.defaultValuesListaHistorico,
                                    0);
        FarmaUtility.initSimpleList(tblCajeros, tableModelHistorico, ConstantsFondoSencillo.columnsListaHistorico);
        cargaListaHistorico();
    }


    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtFechaIni);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void cerrarVentana(boolean pAceptar) {
        limpiarcampos();
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void chkKeyPressed(KeyEvent e) {
        //e.consume();
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            e.consume();
            cerrarVentana(false);
        } /*else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            e.consume();
            funcionF1();
        } */
        else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            e.consume();
            funcionF2();
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            e.consume();
            funcionF3();
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            e.consume();
            funcionF4();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            e.consume();
            //funcionF5();
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            e.consume();
            //funcionF6();
        }

    }

    private void tblCajeros_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnFechaInicio_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }


    private void btnBuscar_actionPerformed(ActionEvent e) {
        /*if(tblCajeros.getRowCount() == 0 || tblCajeros.getRowCount() > 0)
          {*/
        busqueda();
        //  }
    }

    private void busqueda() {
        if (txtFechaIni.getText().trim().length() == 0 && txtFechaFin.getText().trim().length() == 0) {
            cargaListaHistorico();
            tblCajeros.repaint();
        } else {
            if (validarCampos()) {
                //if(pFecha)
                //cargaHistorico(txtFechaIni.getText().trim(),txtFechaFin.getText().trim());
                cargaListaHistoricoFechas(txtFechaIni.getText().trim(), txtFechaFin.getText().trim());
                tblCajeros.repaint();
            }
        }
    }

    private void mostrarAsignarFondoSencillo() {
        DlgAsignarSencilloCajero dlgAsignarFondo = new DlgAsignarSencilloCajero(myParentFrame, "", true);
        dlgAsignarFondo.setVisible(true);
    }

    private void setJTable(JTable pJTable, JTextFieldSanSerif pText) {
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, null, 0);
        }
        FarmaUtility.moveFocus(pText);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        //FarmaUtility.moveFocus(txtCajero);
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblCajeros, null, 0);

        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);

        chkKeyPressed(e);
    }


    private void cargaListaHistorico() {

        VariablesFondoSencillo.vFiltroTipo =
                FarmaLoadCVL.getCVLCode(ConstantsFondoSencillo.NOM_HASTABLE_CMBFILTRO_HIST,
                                        cmbMostrar.getSelectedIndex());
        log.debug("VariablesFondoSencillo.vFiltroTipo: " + VariablesFondoSencillo.vFiltroTipo);

        try {
            //  DBFondoSencillo.getListaHistorico(tableModelHistorico,VariablesFondoSencillo.vFiltroTipo);
            //JMIRANDA 04.06.2010
            DBFondoSencillo.getListaHistoricoXCajero(tableModelHistorico, VariablesFondoSencillo.vFiltroTipo,
                                                     VariablesFondoSencillo.vCajSecUsuCajero);
            /*if (tblCajeros.getRowCount() > 0)
                {
                    FarmaUtility.ordenar(tblCajeros, tableModelCajeros, 1,FarmaConstants.ORDEN_ASCENDENTE);
                }   */
            if (!IndInicialCargaHistorico) {
                if (tablaTieneRegistro(tblCajeros)) {
                } else {
                    FarmaUtility.showMessage(this, "No existen registros.", txtFechaIni);
                }
                FarmaUtility.moveFocus(txtFechaIni);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar el Histórico : \n", txtFechaIni);
        }
    }

    private void cargaListaHistoricoFechas(String pFechaIni, String pFechaFin) {
        VariablesFondoSencillo.vFiltroTipo =
                FarmaLoadCVL.getCVLCode(ConstantsFondoSencillo.NOM_HASTABLE_CMBFILTRO_HIST,
                                        cmbMostrar.getSelectedIndex());
        log.debug("VariablesFondoSencillo.vFiltroTipo: " + VariablesFondoSencillo.vFiltroTipo);

        try {
            //DBFondoSencillo.getListaHistoricoFechas(tableModelHistorico,pFechaIni,pFechaFin,VariablesFondoSencillo.vFiltroTipo);
            //JMIRANDA 04.06.2010
            DBFondoSencillo.getListaHistoricoFechasXCajero(tableModelHistorico, pFechaIni, pFechaFin,
                                                           VariablesFondoSencillo.vFiltroTipo,
                                                           VariablesFondoSencillo.vCajSecUsuCajero);
            /*if (tblCajeros.getRowCount() > 0)
                {
                    FarmaUtility.ordenar(tblCajeros, tableModelCajeros, 1,FarmaConstants.ORDEN_ASCENDENTE);
                }   */
            if (!tablaTieneRegistro(tblCajeros)) {
                FarmaUtility.showMessage(this, "No existen registros.", txtFechaIni);
            }
            FarmaUtility.moveFocus(txtFechaIni);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar el Histórico : \n", txtFechaIni);
        }
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblCajeros, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //btnBuscar.doClick();
            FarmaUtility.moveFocus(cmbMostrar);
        }
        chkKeyPressed(e);
    }

    private void txtFechaIni_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFechaIni, e);
    }

    private void txtFechaFin_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFechaFin, e);
    }

    //JMIRANDA VALIDA FECHA

    private boolean validaFecha(String pFecha, String pHora) {
        //pFecha.trim().equalsIgnoreCase("");
        boolean flag = false;
        Date fec = null;
        if (pHora.trim().equalsIgnoreCase("")) {
            pHora = "00:00:00";
        }
        try {
            if (FarmaUtility.isFechaValida(pFecha)) {
                fec = FarmaUtility.obtiene_fecha(pFecha, pHora);
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    private boolean validarCampos() {
        boolean retorno = true, flag1 = true, flag2 = true;
        try {

            if (!UtilityRecepCiega.validarFecha(txtFechaIni.getText().trim()) ||
                !validaFecha(txtFechaIni.getText().trim(), "")) {
                flag1 = false;
                retorno = false;
                FarmaUtility.showMessage(this, "Formato de fecha inicial inválido", txtFechaIni);
                setJTable(tblCajeros, txtFechaIni);
                return retorno;
            } else if (!UtilityRecepCiega.validarFecha(txtFechaFin.getText().trim()) ||
                       !validaFecha(txtFechaFin.getText().trim(), "")) {
                flag1 = false;
                retorno = false;
                FarmaUtility.showMessage(this, "Formato de fecha final inválido", txtFechaFin);
                setJTable(tblCajeros, txtFechaFin);
                return retorno;
            } else if (!flag1 || !flag2) {
                retorno = false;
                FarmaUtility.showMessage(this, "Formato fecha inválido", txtFechaIni);
                setJTable(tblCajeros, txtFechaIni);
            } else if (flag1 && flag2) {
                if (!FarmaUtility.valida_fecha_Inicial_Final(txtFechaIni.getText().trim(),
                                                             txtFechaFin.getText().trim())) {
                    retorno = false;
                    FarmaUtility.showMessage(this, "La fecha inicial es mayor a la fecha final", txtFechaIni);
                    //FarmaUtility.moveFocus(txtFechaIni);
                    setJTable(tblCajeros, txtFechaIni);
                }
            }
        } catch (Exception e) {
            retorno = false;
            //log.error("",e);
        }
        return retorno;
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }


    private void cargaHistorico(String pFechaIni, String pFechaFin) {
        VariablesFondoSencillo.vFiltroTipo =
                FarmaLoadCVL.getCVLCode(ConstantsFondoSencillo.NOM_HASTABLE_CMBFILTRO_HIST,
                                        cmbMostrar.getSelectedIndex());
        try {
            log.debug(pFechaIni);
            log.debug(pFechaFin);
            DBFondoSencillo.getListaHistoricoFechas(tableModelHistorico, pFechaIni, pFechaFin,
                                                    VariablesFondoSencillo.vFiltroTipo);
            setJTable(tblCajeros, txtFechaIni);
            if (!tablaTieneRegistro(tblCajeros)) {
                FarmaUtility.showMessage(this, "No existen registros.", txtFechaIni);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar Histórico de Fondo de Sencillo : \n" +
                    sql.getMessage(), null);
            //cerrarVentana(false);
        }
    }

    private void funcionF1() {
        if (UtilityFondoSencillo.validaUsuAdmLocal(this, FarmaVariables.vNuSecUsu, txtFechaIni)) {
            mostrarAsignarFondoSencillo();
            if (FarmaVariables.vAceptar) {
                cargaListaHistorico();
                //JMIRANDA 03.06.2010 no imprimer voucher
                /*
                UtilityFondoSencillo.imprimeVoucherDiferencias(this,
                                                            VariablesFondoSencillo.vHisSecFondoSen,txtFechaIni,true);
                VariablesFondoSencillo.vHisSecFondoSen = "";
                */

                setJTable(tblCajeros, txtFechaIni);
                //JMIRANDA 04.06.2010 CIERRO PARA MANDAR AL LISTADO DE CAJERO
                cerrarVentana(true);
            }
        } else {
            FarmaUtility.showMessage(this, "Ud. no tiene rol Administrador", txtFechaIni);
        }
    }

    private void funcionF2() {
        if (tablaTieneRegistro(tblCajeros)) {
            if (UtilityFondoSencillo.validaUsuAdmLocal(this, FarmaVariables.vNuSecUsu, txtFechaIni)) {
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "¿Desea Aceptar la Devolución?")) {
                    try {
                        if (seleccionarRegistro()) {
                            if (VariablesFondoSencillo.vHistoTipo.trim().equalsIgnoreCase(ConstantsFondoSencillo.TipoFondoDevuelve)) {
                                if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoAceptado)) {
                                    FarmaUtility.showMessage(this, "Esta Devolución ya fué aceptada.", txtFechaIni);
                                } else if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoEmitido)) {
                                    log.debug("vHisSecUsuDestino: " + VariablesFondoSencillo.vHisSecUsuDestino);
                                    log.debug("vNuSecUsu: " + FarmaVariables.vNuSecUsu);
                                    if (VariablesFondoSencillo.vHisSecUsuDestino.trim().equalsIgnoreCase(FarmaVariables.vNuSecUsu.trim())) {
                                        DBFondoSencillo.aceptaDevolucionSencilloHis(FarmaVariables.vIdUsu,
                                                                                    FarmaVariables.vIpPc,
                                                                                    VariablesFondoSencillo.vHisSecFondoSen);
                                        FarmaUtility.aceptarTransaccion();
                                        FarmaUtility.showMessage(this, "Devolución Aceptada con éxito.", txtFechaIni);
                                        cargaListaHistorico();
                                        setJTable(tblCajeros, txtFechaIni);
                                    } else {
                                        FarmaUtility.showMessage(this,
                                                                 "Ud. no puede aceptar está devolución, pues no va dirigida para Ud.",
                                                                 txtFechaIni);
                                    }
                                } else if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoRechazado)) {
                                    FarmaUtility.showMessage(this, "No puede aceptar una Devolución Rechazada.",
                                                             txtFechaIni);
                                }
                            } else {
                                FarmaUtility.showMessage(this, "No se permite Aceptar una Asignación.", txtFechaIni);
                            }
                        }
                    } catch (SQLException e) {
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(this, "Error al Aceptar una Asignación.", txtFechaIni);
                    }
                }
            } else {
                FarmaUtility.showMessage(this, "Ud. no tiene rol Administrador", txtFechaIni);
            }
        } else {
            FarmaUtility.showMessage(this, "No existen registros.", txtFechaIni);
        }
    }

    private void funcionF3() {
        try {
            if (tablaTieneRegistro(tblCajeros)) {
                //imprime Voucher
                if (seleccionarRegistro()) {
                    if (VariablesFondoSencillo.vHistoTipo.trim().equalsIgnoreCase(ConstantsFondoSencillo.TipoFondoDevuelve)) {
                        if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoAceptado)) {
                            //-
                            UtilityFondoSencillo.imprimeVoucherDiferencias(this,
                                                                           VariablesFondoSencillo.vHisSecFondoSen,
                                                                           txtFechaIni);
                            //-
                        } else if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoEmitido)) {
                            //-
                            log.debug("Se imprime");
                            UtilityFondoSencillo.imprimeVoucherDiferencias(this,
                                                                           VariablesFondoSencillo.vHisSecFondoSen,
                                                                           txtFechaIni);
                            //-

                        } else if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoRechazado)) {
                            FarmaUtility.showMessage(this, "No puede imprimir una Devolución Rechazada.", txtFechaIni);
                        }
                    } else if (VariablesFondoSencillo.vHistoTipo.trim().equalsIgnoreCase(ConstantsFondoSencillo.TipoFondoAsigna)) {
                        if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoAceptado)) {
                            //-
                            UtilityFondoSencillo.imprimeVoucherDiferencias(this,
                                                                           VariablesFondoSencillo.vHisSecFondoSen,
                                                                           txtFechaIni);
                            //-
                        } else if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoEmitido)) {
                            //-Tipo Fondo
                            UtilityFondoSencillo.imprimeVoucherDiferencias(this,
                                                                           VariablesFondoSencillo.vHisSecFondoSen,
                                                                           txtFechaIni);
                            //-
                        } else if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoAnulado)) {
                            FarmaUtility.showMessage(this, "No puede imprimir una Asignación Anulada.", txtFechaIni);
                        }
                    }
                }
            } else {
                FarmaUtility.showMessage(this, "No existen registros.", txtFechaIni);
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error F3\n" +
                    e.getMessage(), txtFechaIni);
        }
    }

    private void funcionF4() {
        if (tablaTieneRegistro(tblCajeros)) {
            if (UtilityFondoSencillo.validaUsuAdmLocal(this, FarmaVariables.vNuSecUsu, txtFechaIni)) {

                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "¿Desea Anular la Asignación?")) {
                    try {
                        if (seleccionarRegistro()) {
                            if (VariablesFondoSencillo.vHistoTipo.trim().equalsIgnoreCase(ConstantsFondoSencillo.TipoFondoAsigna)) {
                                if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoAceptado)) {
                                    FarmaUtility.showMessage(this, "No puede Anular una Asignación Aceptada.",
                                                             txtFechaIni);
                                } else if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoEmitido)) {
                                    //Anular Asignación Emitidas
                                    String rpta =
                                        DBFondoSencillo.anulaAsignacion(FarmaVariables.vIdUsu, FarmaVariables.vIpPc,
                                                                        VariablesFondoSencillo.vHisSecFondoSen,
                                                                        ConstantsFondoSencillo.EstadoAnulado,
                                                                        ConstantsFondoSencillo.TipoFondoAsigna).trim();

                                    if (rpta.equalsIgnoreCase("S")) {
                                        FarmaUtility.aceptarTransaccion();
                                        FarmaUtility.showMessage(this, "Registro Anulado Satisfactoriamente.",
                                                                 txtFechaIni);
                                        cargaListaHistorico();
                                        setJTable(tblCajeros, txtFechaIni);
                                    } else {
                                        FarmaUtility.liberarTransaccion();
                                        FarmaUtility.showMessage(this, "Registro No puede ser anulado.\n" +
                                                "Es probable que el cajero ya acepto la asignación. ¡Verifique!",
                                                txtFechaIni);
                                        cargaListaHistorico();
                                        setJTable(tblCajeros, txtFechaIni);
                                    }
                                } else if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoAnulado)) {
                                    FarmaUtility.showMessage(this, "Ya ha sido anulada esta Asignación.", txtFechaIni);
                                }
                            } else {
                                FarmaUtility.showMessage(this, "No puede Anular una Devolución.", txtFechaIni);
                            }

                        }
                    } catch (SQLException e) {
                        log.error("", e);
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(this, "Error al Anular Asiganación.", txtFechaIni);
                    }
                }
            } else {
                FarmaUtility.showMessage(this, "Ud. no tiene rol Administrador", txtFechaIni);
            }
        } else {
            FarmaUtility.showMessage(this, "No existen registros.", txtFechaIni);
        }
    }

    private void funcionF5() {
        if (UtilityFondoSencillo.validaUsuAdmLocal(this, FarmaVariables.vNuSecUsu, txtFechaIni)) {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "¿Desea Rechazar la Devolución?")) {
                try {
                    if (seleccionarRegistro()) {
                        if (VariablesFondoSencillo.vHistoTipo.trim().equalsIgnoreCase(ConstantsFondoSencillo.TipoFondoDevuelve)) {
                            if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoAceptado)) {
                                FarmaUtility.showMessage(this, "No puede Rechazar una Devolución Aceptada.",
                                                         txtFechaIni);
                            } else if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoEmitido)) {
                                //Anular Asignación Emitidas

                                if (VariablesFondoSencillo.vHisSecUsuDestino.trim().equalsIgnoreCase(FarmaVariables.vNuSecUsu.trim())) {
                                    DBFondoSencillo.anulaAsignacion(FarmaVariables.vIdUsu, FarmaVariables.vIpPc,
                                                                    VariablesFondoSencillo.vHisSecFondoSen,
                                                                    ConstantsFondoSencillo.EstadoRechazado,
                                                                    ConstantsFondoSencillo.TipoFondoDevuelve);
                                    FarmaUtility.aceptarTransaccion();
                                    FarmaUtility.showMessage(this, "Registro Rechazado Satisfactoriamente.",
                                                             txtFechaIni);
                                    cargaListaHistorico();
                                    setJTable(tblCajeros, txtFechaIni);
                                } else {
                                    FarmaUtility.showMessage(this,
                                                             "Ud. no puede rechazar está Devolución pues no va dirigida para Ud.",
                                                             txtFechaIni);
                                }
                            } else if (VariablesFondoSencillo.vHistoEstado.trim().equalsIgnoreCase(ConstantsFondoSencillo.EstadoRechazado)) {
                                FarmaUtility.showMessage(this, "Ya ha sido Rechazada esta Devolución.", txtFechaIni);
                            }
                        } else {
                            FarmaUtility.showMessage(this, "No puede Rechazar una Asignación.", txtFechaIni);
                        }
                    }
                } catch (SQLException e) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this, "Error al Rechazar Devolución.", txtFechaIni);
                }
            }
        } else {
            FarmaUtility.showMessage(this, "Ud. no tiene rol Administrador", txtFechaIni);
        }
    }

    private boolean seleccionarRegistro() {
        boolean flag = false;
        if (tblCajeros.getRowCount() <= 0)
            flag = false;

        int vFila = tblCajeros.getSelectedRow();
        log.debug("vFila: " + vFila);
        if (vFila != -1) {

            VariablesFondoSencillo.vHisSecFondoSen =
                    FarmaUtility.getValueFieldArrayList(tableModelHistorico.data, vFila, ColSecFondSen).trim();
            VariablesFondoSencillo.vHistoEstado =
                    FarmaUtility.getValueFieldArrayList(tableModelHistorico.data, vFila, ColEstado).trim();
            VariablesFondoSencillo.vHistoTipo =
                    FarmaUtility.getValueFieldArrayList(tableModelHistorico.data, vFila, ColIndTipo).trim();

            VariablesFondoSencillo.vHisSecUsuDestino =
                    FarmaUtility.getValueFieldArrayList(tableModelHistorico.data, vFila, ColUsuDestino).trim();
            VariablesFondoSencillo.vHisSecUsuOrigen =
                    FarmaUtility.getValueFieldArrayList(tableModelHistorico.data, vFila, ColUsuOrigen).trim();

            log.debug("VariablesFondoSencillo.vHisSecFondoSen: " + VariablesFondoSencillo.vHisSecFondoSen + "\n" +
                    "VariablesFondoSencillo.vHistoEstado: " + VariablesFondoSencillo.vHistoEstado + "\n" +
                    "VariablesFondoSencillo.vHistoTipo: " + VariablesFondoSencillo.vHistoTipo + "\n");
            flag = true;
        } else {
            FarmaUtility.showMessage(this, "No ha seleccionado ningún cajero.", txtFechaIni);
            flag = false;
        }
        return flag;
    }

    private void initComboFiltro() {

        String codigo[] = { "T", "A", "D" };
        String valor[] = { "TODOS", "ASIGNACION", "DEVOLUCION" };
        FarmaLoadCVL.loadCVLfromArrays(cmbMostrar, ConstantsFondoSencillo.NOM_HASTABLE_CMBFILTRO_HIST, codigo, valor,
                                       true);

    }

    private void initCombos() {
        initComboFiltro();
    }

    private void cmbMostrar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFechaIni.getText().trim().length() == 0 && txtFechaFin.getText().trim().length() == 0) {
                cargaListaHistorico();
                setJTable(tblCajeros, txtFechaIni);
                FarmaUtility.moveFocus(txtFechaIni);
            } else if (txtFechaIni.getText().trim().length() != 0 || txtFechaFin.getText().trim().length() != 0) {
                if (validarCampos()) {
                    cargaListaHistoricoFechas(txtFechaIni.getText().trim(), txtFechaFin.getText().trim());
                    //setJTable(tblCajeros,txtFechaIni);
                    //FarmaUtility.moveFocus(txtFechaIni);
                }
            }
            // FarmaUtility.moveFocus(txtFechaIni);
        }
        chkKeyPressed(e);
    }


    private void btnMostrar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbMostrar);
    }

    private void cmbMostrar_actionPerformed(ActionEvent e) {
        //btnBuscar.doClick();
        /*
            if(txtFechaIni.getText().trim().length()== 0 &&  txtFechaFin.getText().trim().length()== 0){
                cargaListaHistorico();
                setJTable(tblCajeros,txtFechaIni);
                FarmaUtility.moveFocus(txtFechaIni);
            } else if(txtFechaIni.getText().trim().length()!= 0 &&  txtFechaFin.getText().trim().length()!= 0){
                if(validarCampos()){
                    cargaListaHistoricoFechas(txtFechaIni.getText().trim(),txtFechaFin.getText().trim());
                    setJTable(tblCajeros,txtFechaIni);
                    FarmaUtility.moveFocus(txtFechaIni);
                }
            }
            FarmaUtility.moveFocus(txtFechaIni);                */
    }

    private boolean tablaTieneRegistro(JTable tbl) {
        boolean flag = true;
        if (tbl.getRowCount() <= 0)
            flag = false;

        return flag;
    }

    private void limpiarcampos() {
        VariablesFondoSencillo.vCajSecUsuCajero = "";
        log.debug("limpiarVariables volver a DlgListadoCajeros");
    }

}
