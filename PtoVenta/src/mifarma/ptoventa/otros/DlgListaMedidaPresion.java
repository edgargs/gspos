package mifarma.ptoventa.otros;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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

import java.text.SimpleDateFormat;

import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.otros.reference.ConstantsOtros;
import mifarma.ptoventa.otros.reference.DBOtros;
import mifarma.ptoventa.otros.reference.VariablesOtros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaMedidaPresion.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      23.10.2008   Creación<br>
 * <br>
 * @author JCALLO<br>
 * @version 1.0<br>
 *
 */

public class DlgListaMedidaPresion extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgListaMedidaPresion.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaPedidos = new JScrollPane();
    private JTable tblListaPedidos = new JTable();


    private JButtonLabel btnRelacionPedidos = new JButtonLabel();

    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JTextFieldSanSerif txtFecFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFecIni = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JLabelWhite lblmensaje = new JLabelWhite();

    private JButtonLabel lblFecIni = new JButtonLabel();
    private JButtonLabel lblFecFin = new JButtonLabel();

    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaMedidaPresion() {
        this(null, "", false);
    }

    public DlgListaMedidaPresion(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        jContentPane.setSize(new Dimension(670, 560));
        this.setSize(new Dimension(670, 560));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Toma de Presión");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        pnlTitle1.setBounds(new Rectangle(10, 55, 640, 20));
        scrListaPedidos.setBounds(new Rectangle(10, 80, 640, 405));

        btnRelacionPedidos.setText("Relación de Medidas de Presion ");
        btnRelacionPedidos.setBounds(new Rectangle(10, 0, 195, 20));
        btnRelacionPedidos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionPedidos_keyPressed(e);
            }
        });
        btnRelacionPedidos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionPedidos_actionPerformed(e);
            }
        });

        jPanelHeader1.setBounds(new Rectangle(10, 5, 640, 45));
        txtFecFin.setBounds(new Rectangle(300, 15, 110, 20));
        txtFecFin.setLengthText(10);
        txtFecFin.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtFecFin_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtFecFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecFin_keyReleased(e);
            }
        });
        txtFecIni.setBounds(new Rectangle(150, 15, 110, 20));
        txtFecIni.setLengthText(10);
        txtFecIni.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtFecIni_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtFecIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecIni_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(465, 15, 85, 20));
        btnBuscar.setFont(new Font("Dialog", 1, 11));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        lblFecIni.setText("Rango de Fechas");
        lblFecIni.setMnemonic('R');
        lblFecIni.setBounds(new Rectangle(45, 15, 100, 20));
        lblFecIni.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblFecIni_actionPerformed(e);
            }
        });
        lblFecFin.setText("Hasta");
        lblFecFin.setMnemonic('H');
        lblFecFin.setVisible(false);
        lblFecFin.setBounds(new Rectangle(170, 15, 34, 20));
        lblFecFin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblFecFin_actionPerformed(e);
            }
        });

        lblF1.setText("[ F1 ] Nuevo");
        lblF1.setBounds(new Rectangle(10, 495, 100, 22));

        lblF2.setBounds(new Rectangle(120, 495, 100, 22));
        lblF2.setText("[ F2 ] Modificar");

        lblF3.setBounds(new Rectangle(230, 495, 100, 22));
        lblF3.setText("[ F3 ] Eliminar");

        lblF8.setText("[ F8 ] Ver Historial");
        lblF8.setBounds(new Rectangle(340, 495, 130, 22));

        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(515, 495, 130, 22));


        lblmensaje.setText("");
        lblmensaje.setBounds(new Rectangle(25, 55, 600, 15));
        lblmensaje.setForeground(new Color(227, 0, 0));
        lblmensaje.setFont(new Font("SansSerif", 1, 13));

        jPanelHeader1.add(lblFecFin, null);
        jPanelHeader1.add(lblFecIni, null);
        jPanelHeader1.add(btnBuscar, null);
        jPanelHeader1.add(txtFecIni, null);
        jPanelHeader1.add(txtFecFin, null);
        jContentPane.add(lblmensaje, null);

        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblEsc, null);

        scrListaPedidos.getViewport().add(tblListaPedidos, null);
        jContentPane.add(scrListaPedidos, null);
        tblListaPedidos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                chkKeyPressed(e);
            }
        });
        pnlTitle1.add(btnRelacionPedidos, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;

        aplicarSeguiridadRolUsuario();

        cargaVariables();

        //cargan la variable indicador de linea con matriz
        VariablesOtros.vIndLineaMatriz =
                FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N).trim();

    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsOtros.columnsListaMedPresion, ConstantsOtros.defaultListaListaMedPresion,
                                    0);
        FarmaUtility.initSimpleList(tblListaPedidos, tableModel, ConstantsOtros.columnsListaMedPresion);

        //obteniendo fecha actual
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fecFin = formato.format(calendario.getTime());
        calendario.add(Calendar.DAY_OF_YEAR, -30); //restando 30 dias
        String fecIni = formato.format(calendario.getTime());

        txtFecIni.setText(fecIni);
        txtFecFin.setText(fecFin);

        VariablesOtros.vFecIni = fecIni;
        VariablesOtros.vFecFin = fecFin;

        cargaListaMedPresionRegistradas(fecIni, fecFin);

    }


    private void cargaListaMedPresionRegistradas(String fecIni, String fecFin) {
        try {
            DBOtros.cargaListaMedPresionRegistradas(tableModel, fecIni, fecFin);
            FarmaUtility.ordenar(tblListaPedidos, tableModel, 0, FarmaConstants.ORDEN_DESCENDENTE);
            FarmaUtility.moveFocus(txtFecIni);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de pedidos : \n " + sql.getMessage(),
                                     btnRelacionPedidos);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnRelacionPedidos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecIni);
    }

    private void btnRelacionPedidos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    //jcallo 16.10.2008 evento del teclado

    private void txtFecIni_keyTyped(KeyEvent e) {
        //chkKeyPressed(e);
    }

    private void txtFecIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFecFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFecIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecIni, e);
    }

    private void txtFecFin_keyTyped(KeyEvent e) {
        //chkKeyPressed(e);
    }

    private void txtFecFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            btnBuscar.doClick();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFecFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecFin, e);
    }


    private void lblFecIni_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecIni);
    }

    private void lblFecFin_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecFin);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (validarFechas()) {

            VariablesOtros.vFecIni = txtFecIni.getText().trim();
            VariablesOtros.vFecFin = txtFecFin.getText().trim();

            cargaListaMedPresionRegistradas(txtFecIni.getText().trim(), txtFecFin.getText().trim());
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtFecIni);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaPedidos, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && !VariablesOtros.vFlagTeclaFxxPresionado &&
            VariablesOtros.vFlagModAdmin) {

            VariablesOtros.vFlagTeclaFxxPresionado = true;

            //verificando si ya se abrio el dialogo NUEVO PEDIDO ESPECIAL
            if (!VariablesOtros.vFlagDialogAbierto) {
                VariablesOtros.vFlagDialogAbierto = true;

                nuevoRegMedidaPresion();

                VariablesOtros.vFlagDialogAbierto = false;
            } else {
                log.debug("ya se encuentra abierto el dialogo de ingreso de nuevo registro de medida de presion");
            }

            VariablesOtros.vFlagTeclaFxxPresionado = false;

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e) &&
                   !VariablesOtros.vFlagTeclaFxxPresionado && VariablesOtros.vFlagModAdmin) {
            VariablesOtros.vFlagTeclaFxxPresionado = true;

            modificarMedidaPresion();

            VariablesOtros.vFlagTeclaFxxPresionado = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F3 && !VariablesOtros.vFlagTeclaFxxPresionado &&
                   VariablesOtros.vFlagModAdmin) {
            VariablesOtros.vFlagTeclaFxxPresionado = true;

            eliminarMedidaPresion();

            VariablesOtros.vFlagTeclaFxxPresionado = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F8 && !VariablesOtros.vFlagTeclaFxxPresionado) {
            VariablesOtros.vFlagTeclaFxxPresionado = true;
            verHistorico();
            VariablesOtros.vFlagTeclaFxxPresionado = false;
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

    private void verHistorico() {
        int row = tblListaPedidos.getSelectedRow();
        if (row > -1) {
            VariablesOtros.vDniCliente = tableModel.getValueAt(row, 1).toString().trim();
        } else {
            VariablesOtros.vDniCliente = "";
        }

        DlgBuscarHistorialXDni dlgBuscarHistorialXDni = new DlgBuscarHistorialXDni(myParentFrame, "", true);
        dlgBuscarHistorialXDni.setVisible(true);

        if (FarmaVariables.vAceptar) { //si encontro resultado de busqueda
            FarmaVariables.vAceptar = false;
            DlgHistorialMedPresionCliente dlgHistorialMedPresionCliente =
                new DlgHistorialMedPresionCliente(myParentFrame, "", true);
            dlgHistorialMedPresionCliente.setVisible(true);
        }

    }


    /**
     * @author JCALLO
     * @since 21.10.2008
     * */
    private void nuevoRegMedidaPresion() {

        FarmaVariables.vAceptar = false;

        DlgRegDatosCliente dlgRegDatosCliente = new DlgRegDatosCliente(myParentFrame, "", true);
        dlgRegDatosCliente.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;

            DlgRegMedidaPresion dlgRegMedidaPresion = new DlgRegMedidaPresion(myParentFrame, "", true);
            dlgRegMedidaPresion.setVisible(true);
            if (FarmaVariables.vAceptar) { //si llego grabar la medida de presion
                //obteniendo fecha actual
                Calendar calendario = Calendar.getInstance();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                String fecFin = formato.format(calendario.getTime());
                calendario.add(Calendar.DAY_OF_YEAR, -30); //restando 30 dias
                String fecIni = formato.format(calendario.getTime());

                txtFecIni.setText(fecIni);
                txtFecFin.setText(fecFin);

                VariablesOtros.vFecIni = fecIni;
                VariablesOtros.vFecFin = fecFin;

                cargaListaMedPresionRegistradas(fecIni, fecFin);
            }
        }
    }

    /**
     * validarFechas
     * @author JCALLO
     * @since 16.10.2008
     * */
    private boolean validarFechas() {
        //quitando los espacios en blanco si es que las ubiera
        txtFecIni.setText(txtFecIni.getText().trim());
        txtFecFin.setText(txtFecFin.getText().trim());

        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFecIni, txtFecFin, false, true, true, true))
            retorno = false;

        return retorno;


    }

    /**
     * Modificar medida de presion
     * @author JCALLO
     * @since 28.10.08
     */
    private void modificarMedidaPresion() {
        FarmaVariables.vAceptar = false;
        int row = tblListaPedidos.getSelectedRow();

        if (row > -1) {
            VariablesOtros.vNumReg = tableModel.getValueAt(row, 0).toString().trim();
            VariablesOtros.vDniCliente = tableModel.getValueAt(row, 1).toString().trim();
            VariablesOtros.vNomCliente = tableModel.getValueAt(row, 2).toString().trim();
            VariablesOtros.vMaxSistolica = tableModel.getValueAt(row, 3).toString().trim();
            VariablesOtros.vMinDiastolica = tableModel.getValueAt(row, 4).toString().trim();

            VariablesOtros.vSexo = tableModel.getValueAt(row, 6).toString().trim();
            VariablesOtros.vFecNacimiento = tableModel.getValueAt(row, 7).toString().trim();

            DlgModMedidaPresion dlgModMedidaPresion = new DlgModMedidaPresion(myParentFrame, "", true);
            dlgModMedidaPresion.setVisible(true);

            if (FarmaVariables.vAceptar) { //si llego a especificar modificar medida presion
                txtFecIni.setText(VariablesOtros.vFecIni);
                txtFecFin.setText(VariablesOtros.vFecFin);
                cargaListaMedPresionRegistradas(VariablesOtros.vFecIni, VariablesOtros.vFecFin);
            }

        } else {
            FarmaUtility.showMessage(this, "Seleccione el Registro a modificar", txtFecIni);
        }
    }

    /**
     * eliminar medida de presion
     * @author JCALLO
     * @since 28.10.08
     */
    private void eliminarMedidaPresion() {
        int row = tblListaPedidos.getSelectedRow();
        if (row > -1) {
            VariablesOtros.vNumReg = tableModel.getValueAt(row, 0).toString().trim();
            VariablesOtros.vDniCliente = tableModel.getValueAt(row, 1).toString().trim();
            VariablesOtros.vNomCliente = tableModel.getValueAt(row, 2).toString().trim();

            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "Esta seguro que desea eliminar ?" + "\nNroReg       : " +
                                                                            VariablesOtros.vNumReg +
                                                                            "\nDNI               : " +
                                                                            VariablesOtros.vDniCliente +
                                                                            "\nCliente        : " +
                                                                            VariablesOtros.vNomCliente +
                                                                            "\nFecha Reg. : " +
                                                                            tableModel.getValueAt(row,
                                                                                                  5).toString().trim())) {
                //inactivando en base de datos
                inactivarMedidaPresion();
                //despues de inactivar en base de datos volver a refrescar el listado de medida de presion
                txtFecIni.setText(VariablesOtros.vFecIni);
                txtFecFin.setText(VariablesOtros.vFecFin);
                cargaListaMedPresionRegistradas(VariablesOtros.vFecIni, VariablesOtros.vFecFin);
            }

        } else {
            FarmaUtility.showMessage(this, "Seleccione el Registro a eliminar", txtFecIni);
        }
    }


    /**
     * eliminar medida de presion
     * @author JCALLO
     * @since 28.10.08
     */
    private void inactivarMedidaPresion() {
        try {
            DBOtros.inactivarMedidaPresionEnLocal();

            if (VariablesOtros.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)) {
                DBOtros.inactivarMedidaPresionEnMatriz(FarmaConstants.INDICADOR_N);
                //haciendo commit en matriz
                FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
            }
            //haciendo commit en local
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            if (VariablesOtros.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)) {
                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
                FarmaConnectionRemoto.closeConnection();
            }
            FarmaUtility.showMessage(this, "error al Eliminar la medida de Presion :" + e, txtFecIni);
        }
    }

    /**
     * aplicar seguridad del rol del usuario
     * @author JCALLO
     * @since 28.10.08
     */
    private void aplicarSeguiridadRolUsuario() {
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) {
            lblF1.setVisible(true);
            lblF2.setVisible(true);
            lblF3.setVisible(true);
            VariablesOtros.vFlagModAdmin = true;
        } else if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS)) {
            lblF1.setVisible(true);
            lblF2.setVisible(true);
            lblF3.setVisible(true);
            VariablesOtros.vFlagModAdmin = true;
        } else {
            lblF1.setVisible(false);
            lblF2.setVisible(false);
            lblF3.setVisible(false);
            VariablesOtros.vFlagModAdmin = false;
        }
    }

    /**
     * carga parametros como la cantidad por defecto a poder imprimir
     * o la cantidad maxima posible a imprimir y tambien limpiar las variables utilizados
     * @author JCALLO
     * @since 28.10.08
     */
    private void cargaVariables() {
        try {
            /**limpiando las viriables usadas en dicho dialogo**/
            VariablesOtros.vApePatCliente = "";
            VariablesOtros.vApeMatCliente = "";
            VariablesOtros.vNomCliente = "";
            VariablesOtros.vCantItemsImprimir = 1;
            VariablesOtros.vDireccion = "";
            VariablesOtros.vDniCliente = "";
            VariablesOtros.vDocValidos = "";
            VariablesOtros.vEmail = "";
            VariablesOtros.vFecNacimiento = "";
            VariablesOtros.vCantItemsImprimir = 4;
            if (VariablesOtros.vTableModelHist != null) {
                VariablesOtros.vTableModelHist.clearTable();
            }
            /**fin de limpiado de variables*/


            /*** obteniendo parametros de base de datos ***/
            String MaxItemsImprimir = DBOtros.getCantMaxItemsImpresion();
            //separando los valores de los parametros
            String valores[] = MaxItemsImprimir.split(",");
            String defCantImpRegistro = "";
            String CantMaxImpHistorial = "";
            if (valores.length > 1) {
                defCantImpRegistro = valores[0];
                CantMaxImpHistorial = valores[0];
            } else {
                defCantImpRegistro = "4";
                CantMaxImpHistorial = "10";
            }

            VariablesOtros.paramCantImprimirXReg = Integer.parseInt(defCantImpRegistro);
            VariablesOtros.paramCantMaxImprimirXHist = Integer.parseInt(CantMaxImpHistorial);

            /** cargando doc validos **/

            /****/
        } catch (SQLException e) {
            VariablesOtros.paramCantImprimirXReg = 4;
            VariablesOtros.paramCantMaxImprimirXHist = 10;
            log.debug("ERROR : " + e);
        }

    }


}
