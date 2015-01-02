package mifarma.ptoventa.administracion.usuarios;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.usuarios.reference.ConstantsUsuarios;
import mifarma.ptoventa.administracion.usuarios.reference.DBUsuarios;
import mifarma.ptoventa.administracion.usuarios.reference.VariablesUsuarios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgBuscaTrabajadorLocal extends JDialog {
    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */

    private static final Logger log = LoggerFactory.getLogger(DlgBuscaTrabajadorLocal.class);

    /** * @Author Asolis
     * @Since  20.02.09
     * @description Busca Trabajador Local*/

    private Frame myParentFrame;
    FarmaTableModel tableModel;

    private final int COL_COD_TRAB = 0;
    private final int COL_COD_TRAB_RRHH = 1;
    private final int COL_APELL_PAT = 2;
    private final int COL_DNI = 5;
    private final int COL_CARNE = 7;
    private final int COL_FECHA_VENC = 8;


    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel btnDniApe = new JButtonLabel();
    private JTextFieldSanSerif txtDniApe = new JTextFieldSanSerif();
    private JScrollPane scrListaTrabajadores = new JScrollPane();
    private JTable tblListaTrabajadores = new JTable();
    private JPanelTitle pnlHeaderUsuarios = new JPanelTitle();
    private JButtonLabel btnRelacion = new JButtonLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private BorderLayout borderLayout1 = new BorderLayout();

    /* ************************************************************************ */
    /*                             CONSTRUCTORES                                */
    /* ************************************************************************ */

    public DlgBuscaTrabajadorLocal() {
        this(null, "", false);
    }

    public DlgBuscaTrabajadorLocal(Frame parent, String title, boolean modal) {
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
    /*                              METODO jbInit                               */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(635, 334));
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        jPanelTitle1.setBounds(new Rectangle(10, 10, 610, 40));
        btnDniApe.setText("DNI / Apellido:");
        btnDniApe.setBounds(new Rectangle(35, 10, 80, 20));
        btnDniApe.setMnemonic('D');
        btnDniApe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDniApe_actionPerformed(e);
            }
        });
        txtDniApe.setBounds(new Rectangle(120, 10, 195, 20));
        txtDniApe.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDniApe_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtDniApe_keyReleased(e);
            }
        });
        scrListaTrabajadores.getViewport().add(tblListaTrabajadores, null);
        scrListaTrabajadores.setBounds(new Rectangle(10, 90, 610, 175));
        pnlHeaderUsuarios.setBounds(new Rectangle(10, 65, 610, 25));
        btnRelacion.setText("Relación de Trabajadores Local:");
        btnRelacion.setBounds(new Rectangle(15, 5, 220, 15));
        lblEsc.setBounds(new Rectangle(355, 275, 95, 20));
        lblEsc.setText("[ ESC ] Salir");
        lblF11.setBounds(new Rectangle(155, 275, 180, 20));
        lblF11.setText("[ F11 ] Ingresar Carné Sanidad ");
        jPanelTitle1.add(btnDniApe, null);
        jPanelTitle1.add(txtDniApe, null);
        pnlHeaderUsuarios.add(btnRelacion, null);
        jPanelWhite1.add(lblF11, null);
        jPanelWhite1.add(lblEsc, null);
        jPanelWhite1.add(pnlHeaderUsuarios, null);
        jPanelWhite1.add(scrListaTrabajadores, null);
        jPanelWhite1.add(jPanelTitle1, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }

    /* ************************************************************************ */
    /*                           METODO initialize                              */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    /* ************************************************************************ */
    /*                         METODOS INICIALIZACION                           */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsUsuarios.columnsListaTrabajadoresLocal, ConstantsUsuarios.defaultValuesListaTrabajadoresLocal,
                                    0);
        FarmaUtility.initSimpleList(tblListaTrabajadores, tableModel, ConstantsUsuarios.columnsListaTrabajadoresLocal);
        cargaListaTrabajadores();
    }

    private void cargaListaTrabajadores() {
        try {
            DBUsuarios.obtieneListaTrabajadoresLocal(tableModel);

            if (tblListaTrabajadores.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaTrabajadores, tableModel, COL_APELL_PAT, FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.setearPrimerRegistro(tblListaTrabajadores, txtDniApe, COL_APELL_PAT);
            }
            //log.debug("Lista de trabajadores cargada!");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener los usuarios. \n " + e.getMessage(), txtDniApe);
        }
    }

    /* ************************************************************************ */
    /*                           METODOS DE EVENTOS                             */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDniApe);
        //mostrarDatos();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnDniApe_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDniApe);
    }

    private void txtDniApe_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtDniApe_keyReleased(KeyEvent e) {
        if (txtDniApe.getText().length() == 1) {
            if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
                //log.debug("BUSCA DNI");
                VariablesUsuarios.vColBuscarTrab = COL_DNI;
            } else {
                //log.debug("BUSCA APELLIDO");
                VariablesUsuarios.vColBuscarTrab = COL_APELL_PAT;
            }
        }
        FarmaGridUtils.buscarDescripcion(e, tblListaTrabajadores, txtDniApe, VariablesUsuarios.vColBuscarTrab);
    }

    /* ************************************************************************ */
    /*                      METODOS AUXILIARES DE EVENTOS                       */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaTrabajadores, txtDniApe, COL_APELL_PAT);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            cargaDatosTrabajador();
            //si no tiene nro de carné
            if (VariablesUsuarios.vCarne.length() == 0) {

                MostrarMantCarne();
                if (FarmaVariables.vAceptar) {
                    cargaListaTrabajadores();
                    FarmaVariables.vAceptar = false;
                }


            }

            else {
                //Validar la fecha de vencimiento del Carné
                //Si esta próximo  a vencer o ya venció debe permitir mostrar el MantenimientoCarne

                try {

                    String fecha_ven = "";

                    fecha_ven =
                            DBUsuarios.verificaFechaVenUsuarioCarne(VariablesUsuarios.vCodTrab); //(VariablesUsuarios.vCodTrab_RRHH);

                    log.info("fecha_ven :" + fecha_ven);
                    if (fecha_ven.trim().equalsIgnoreCase("NV")) //El Nro Carné no está vencido.

                        log.info("Ya se ha registrado el  Carné para este Trabajador ");
                    // FarmaUtility.showMessage(this, "Ya se ha registrado el Nro. Carné para este Trabajador", txtDniApe);

                    else if (fecha_ven.trim().equalsIgnoreCase("V")) { //El Nro Carné vencido.


                        if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                        "El Carné de Sanidad para este Trabajador  venció el " +
                                                                                        VariablesUsuarios.vFechaVencimiento +
                                                                                        "\n" +
                                "¿Desea ingresar el nuevo Número de Carné de Sanidad?")) {

                            VariablesUsuarios.vModificarCarne = "S";
                            MostrarMantCarne();

                            if (FarmaVariables.vAceptar) {
                                cargaListaTrabajadores();
                                FarmaVariables.vAceptar = false;
                            }

                        }
                    }

                    else { //Nro Carné proximo a vencer.
                        if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                        "El Carné de Sanidad para este Trabajador está próximo a vencer el " +
                                                                                        VariablesUsuarios.vFechaVencimiento +
                                                                                        "\n" +
                                "¿Desea ingresar el nuevo Número de Carné de Sanidad?")) {

                            VariablesUsuarios.vModificarCarne = "S";
                            MostrarMantCarne();

                            if (FarmaVariables.vAceptar) {
                                cargaListaTrabajadores();
                                FarmaVariables.vAceptar = false;
                            }

                        }

                    }
                }

                catch (SQLException sql) {
                    log.error("", sql);

                }

            }
            //  txtDniApe.setText("");
            // cerrarVentana(true);
            this.setVisible(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            VariablesUsuarios.vCodTrab = "";
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                      METODOS DE LOGICA DE NEGOCIO                        */
    /* ************************************************************************ */

    /**
     * Se carga los datos del trabajador seleccioando.
     */
    private void cargaDatosTrabajador() {
        int vFilaSelect = tblListaTrabajadores.getSelectedRow();

        VariablesUsuarios.vCodTrab = FarmaUtility.getValueFieldJTable(tblListaTrabajadores, vFilaSelect, COL_COD_TRAB);
        VariablesUsuarios.vCodTrab_RRHH =
                FarmaUtility.getValueFieldJTable(tblListaTrabajadores, vFilaSelect, COL_COD_TRAB_RRHH);

        VariablesUsuarios.vCarne = FarmaUtility.getValueFieldJTable(tblListaTrabajadores, vFilaSelect, COL_CARNE);
        VariablesUsuarios.vFechaVencimiento =
                FarmaUtility.getValueFieldJTable(tblListaTrabajadores, vFilaSelect, COL_FECHA_VENC);


        log.info("VariablesUsuarios.vCodTrab :" + VariablesUsuarios.vCodTrab);
        log.info("VariablesUsuarios.vCodTrab_RRHH :" + VariablesUsuarios.vCodTrab_RRHH);
        log.info("VariablesUsuarios.vCarne :" + VariablesUsuarios.vCarne);
        log.info("VariablesUsuarios.vFechaVencimiento :" + VariablesUsuarios.vFechaVencimiento);

    }

    private void MostrarMantCarne() {


        DlgMantCarne dlgMantCarne = new DlgMantCarne(myParentFrame, "", true);
        dlgMantCarne.setVisible(true);


    }
}
