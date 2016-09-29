package mifarma.ptoventa.inventariociclico;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;

import java.awt.BorderLayout;
import java.awt.Color;
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

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventariociclico.reference.ConstantsInvCiclico;
import mifarma.ptoventa.inventariociclico.reference.DBInvCiclico;
import mifarma.ptoventa.inventariociclico.reference.VariablesInvCiclico;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaTomasInventarioCiclico extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaTomasInventarioCiclico.class);

    FarmaTableModel tableModelInventarioCiclico;

    Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanel jContentPane = new JPanel();

    private JPanelTitle jPanelTitle1 = new JPanelTitle();

    private JScrollPane jScrollPane1 = new JScrollPane();

    private JTable tblRelacionTomas = new JTable();

    private JButtonLabel btnRelacionTomas = new JButtonLabel();

    private JLabelFunction lblF1 = new JLabelFunction();

    private JLabelFunction lblF8 = new JLabelFunction();

    private JLabelFunction lblF9 = new JLabelFunction();

    private JLabelFunction lblEscape = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaTomasInventarioCiclico() {
        this(null, "", false);
    }

    public DlgListaTomasInventarioCiclico(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(550, 374));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Tomas de Inventario");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jPanelTitle1.setBounds(new Rectangle(15, 10, 510, 30));
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        jScrollPane1.setBounds(new Rectangle(15, 40, 510, 245));
        tblRelacionTomas.getTableHeader().setReorderingAllowed(false);
        tblRelacionTomas.getTableHeader().setResizingAllowed(false); 
        tblRelacionTomas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblRelacionTomas_keyPressed(e);
            }
        });
        btnRelacionTomas.setText("Relacion de Tomas de Inventario");
        btnRelacionTomas.setBounds(new Rectangle(10, 5, 195, 20));
        btnRelacionTomas.setMnemonic('r');
        btnRelacionTomas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionTomas_actionPerformed(e);
            }
        });
        lblF1.setBounds(new Rectangle(15, 295, 90, 20));
        lblF1.setText("[ F1 ] Ver Toma");
        lblF8.setBounds(new Rectangle(410, 295, 117, 19));
        lblF8.setText("[ F8 ] Cargar Toma");
        lblF9.setBounds(new Rectangle(15, 320, 117, 19));
        lblF9.setText("[ F9 ] Anular Toma");
        lblEscape.setBounds(new Rectangle(425, 320, 100, 20));
        lblEscape.setText("[ ESCAPE ] Cerrar");
        lblF2.setBounds(new Rectangle(120, 295, 130, 20));
        lblF2.setText("[ F2 ] Ver Consolidado");
        lblF3.setBounds(new Rectangle(260, 295, 125, 20));
        lblF3.setText("[ F3 ] Ver Diferencias");
        lblF4.setBounds(new Rectangle(140, 320, 140, 20));
        lblF4.setText("[ F4 ] Imprimir Archivos");
        lblF4.setVisible(false);
        lblF5.setBounds(new Rectangle(295, 320, 115, 20));
        lblF5.setText("[ F5 ] Enviar Local");
        lblF5.setVisible(false);
        jScrollPane1.getViewport().add(tblRelacionTomas, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblEscape, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(jScrollPane1, null);
        jPanelTitle1.add(btnRelacionTomas, null);
        jContentPane.add(jPanelTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        
        // variable de simulacion a borrar
        //VariablesPtoVenta
        //VariablesPtoVenta.vTipoLocalVenta=ConstantsPtoVenta.TIPO_LOCAL_VENTA_FARMA;

        FarmaVariables.vAceptar = false;
        initTableListaTomasInventario();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaTomasInventario() {
        tableModelInventarioCiclico =
                new FarmaTableModel(ConstantsInvCiclico.columnsTomasInventario, ConstantsInvCiclico.defaultValuesTomasInventario,
                                    0);
        FarmaUtility.initSimpleList(tblRelacionTomas, tableModelInventarioCiclico,
                                    ConstantsInvCiclico.columnsTomasInventario);

        cargaTomasInventario();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL) && FarmaVariables.vEconoFar_Matriz)
            lblF4.setVisible(true);
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) && FarmaVariables.vEconoFar_Matriz)
            lblF5.setVisible(true);
        FarmaUtility.moveFocus(tblRelacionTomas);
    }

    private void tblRelacionTomas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnRelacionTomas_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblRelacionTomas);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (UtilityPtoVenta.verificaVK_F1(e)) {
            if (tieneRegistroSeleccionado(tblRelacionTomas)) {
                cargarRegistroSeleccionado();
                DlgLaboratoriosPorTomaCiclico dlgLaboratoriosPorTomaCiclico =
                    new DlgLaboratoriosPorTomaCiclico(myParentFrame, "", true);
                dlgLaboratoriosPorTomaCiclico.setVisible(true);
            }
        } else if (UtilityPtoVenta.verificaVK_F2(e)) {
            if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA)) {
                if (tieneRegistroSeleccionado(tblRelacionTomas)) {
                    cargarRegistroSeleccionado();
                    DlgConsolidadoTomaCiclico dlgConsolidadoTomaCiclico =
                        new DlgConsolidadoTomaCiclico(myParentFrame, " ", true);
                    dlgConsolidadoTomaCiclico.setVisible(true);
                }
            } else
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción",
                                         tblRelacionTomas);

        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA)) {
                if (tieneRegistroSeleccionado(tblRelacionTomas)) {
                    cargarRegistroSeleccionado();
                    DlgListaDiferenciasConsolidadoCiclico dlgListaDiferenciasConsolidadoCiclico =
                        new DlgListaDiferenciasConsolidadoCiclico(myParentFrame, " ", true);
                    dlgListaDiferenciasConsolidadoCiclico.setVisible(true);
                }
            } else
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción",
                                         tblRelacionTomas);

        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            if (lblF4.isVisible()) {
                cargarRegistroSeleccionado();
                imprimirProductosPorLaboratorio();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (lblF5.isVisible()) {
                cargarRegistroSeleccionado();
                envioTomaLocal();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblRelacionTomas);
            else {
                if (cargaLogin()) {
                    if (tieneRegistroSeleccionado(this.tblRelacionTomas)) {
                        cargarRegistroSeleccionado();
                        if (esTomaValida())
                            if (JConfirmDialog.rptaConfirmDialog(this,
                                                                                            "¿Esta seguro de cargar la toma?")) {
                                try {
                                    if (obtieneIndicadorProceso_ForUpdate(VariablesInvCiclico.vSecToma,
                                                                          VariablesInvCiclico.vIndProceso)) {
                                        cargarToma();
                                        FarmaUtility.aceptarTransaccion();
                                        FarmaUtility.showMessage(this, "La toma se cargó correctamente",
                                                                 tblRelacionTomas);
                                        cargaTomasInventario();
                                    } else {
                                        FarmaUtility.liberarTransaccion();
                                        FarmaUtility.showMessage(this,
                                                                 "No es posible realizar la operacion. La Toma ya se encuentra cargada. Verifique!!!",
                                                                 tblRelacionTomas);
                                        cargaTomasInventario();
                                        return;
                                    }
                                } catch (SQLException sql) {
                                    FarmaUtility.liberarTransaccion();
                                    log.error("", sql);
                                    FarmaUtility.showMessage(this, "Ocurrió un error al cargar la toma :\n" +
                                            sql.getMessage(), tblRelacionTomas);
                                }
                            }
                    }
                }
            }

        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblRelacionTomas);
            else {
                if (cargaLogin()) {
                    if (tieneRegistroSeleccionado(this.tblRelacionTomas)) {
                        if (JConfirmDialog.rptaConfirmDialog(this,
                                                                                        "¿Esta seguro de anular la toma?")) {
                            cargarRegistroSeleccionado();
                            try {
                                if (obtieneIndicadorProceso_ForUpdate(VariablesInvCiclico.vSecToma,
                                                                      VariablesInvCiclico.vIndProceso)) {
                                    anularToma();
                                    FarmaUtility.aceptarTransaccion();
                                    FarmaUtility.showMessage(this, "La toma se anuló correctamente", tblRelacionTomas);
                                    cargaTomasInventario();
                                } else {
                                    FarmaUtility.liberarTransaccion();
                                    FarmaUtility.showMessage(this,
                                                             "No es posible realizar la operacion. La Toma ya se encuentra anulada. Verifique!!!",
                                                             tblRelacionTomas);
                                    cargaTomasInventario();
                                    return;
                                }
                            } catch (SQLException sql) {
                                FarmaUtility.liberarTransaccion();
                                log.error("", sql);
                                FarmaUtility.showMessage(this, "Ocurrió un error al anular la toma :\n" +
                                        sql.getMessage(), tblRelacionTomas);
                            }
                        }
                    }
                }
            }
        }
    }
    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaTomasInventario() {
        try {
            DBInvCiclico.getListaTomasInv(tableModelInventarioCiclico);
            if (tblRelacionTomas.getRowCount() > 0)
                FarmaUtility.ordenar(tblRelacionTomas, tableModelInventarioCiclico, 2,
                                     FarmaConstants.ORDEN_ASCENDENTE);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener las tomas de inventario : \n" +
                    sql.getMessage(), tblRelacionTomas);
        }
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;
        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void cargarRegistroSeleccionado() {
        VariablesInvCiclico.vSecToma =
                tblRelacionTomas.getValueAt(tblRelacionTomas.getSelectedRow(), 0).toString().trim();
        VariablesInvCiclico.vIndProceso =
                tblRelacionTomas.getValueAt(tblRelacionTomas.getSelectedRow(), 4).toString().trim();
        log.debug(VariablesInvCiclico.vSecToma);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void cargarToma() throws SQLException {
        DBInvCiclico.cargarToma();
    }

    private void anularToma() throws SQLException {
        DBInvCiclico.anularToma();
    }


    private boolean esTomaValida() {
        boolean rpta = true;
        String indTomaInc = "";
        ArrayList listaLabs;
        String sListaLabs = "";
        try {
            indTomaInc = DBInvCiclico.obtenerIndTomaIncompleta().trim();
        } catch (SQLException sql) {
            rpta = false;
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al determinar el estado de la toma : \n" +
                    sql.getMessage(), tblRelacionTomas);
        }
        if (indTomaInc.equals("1")) {
            // La toma esta incompleta
            listaLabs = new ArrayList();
            rpta = false;
            try {
                listaLabs = DBInvCiclico.getListaLabsTomaIncompleta();
                for (int i = 0; i < listaLabs.size(); i++) {
                    sListaLabs = sListaLabs + listaLabs.get(i).toString() + "\n";
                }
                FarmaUtility.showMessage(this,
                                         "No se puede efectuar la operacion ya que los siguientes laboratorios no han sido completados correctamente:\n " +
                                         sListaLabs, tblRelacionTomas);
            } catch (SQLException sql) {
                FarmaUtility.showMessage(this, "Ocurrió un error en la transacción:\n" +
                        sql.getMessage(), tblRelacionTomas);
                log.error("", sql);
            }
        }
        return rpta;
    }

    public static boolean obtieneIndicadorProceso_ForUpdate(String pSecTomaInv, String pIndProceso) {
        ArrayList myArray = new ArrayList();
        String indProceso = new String();
        try {
            DBInvCiclico.obtieneIndTomaInvForUpdate(myArray, pSecTomaInv, pIndProceso);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            return false;
        } finally {
            if (myArray.size() > 0) {
                indProceso = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
                log.debug("indProceso : " + indProceso);
                VariablesInvCiclico.vIndProceso = indProceso;
                return true;
            } else
                return false;
        }
    }

    private void obtieneListaCodigosLaboratorios() {
        try {
            VariablesInvCiclico.vListaCodLab = DBInvCiclico.obtieneCodigoLaboratorios();
            FarmaUtility.ordenar(VariablesInvCiclico.vListaCodLab, 1, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            VariablesInvCiclico.vListaCodLab = new ArrayList();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener información de comprobantes : \n" +
                    sql.getMessage(), null);
        }
    }

    private void obtieneProductosPorLaboratorio() {
        try {
            VariablesInvCiclico.vListaProductos.clear();
            VariablesInvCiclico.vListaProductos =
                    DBInvCiclico.obtieneProductosPorLaboratotio(VariablesInvCiclico.vCodLab);
            FarmaUtility.ordenar(VariablesInvCiclico.vListaProductos, 1, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            VariablesInvCiclico.vListaProductos = new ArrayList();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener información de comprobantes : \n" +
                    sql.getMessage(), null);
        }
    }


    private void imprimirProductosPorLaboratorio() {
        obtieneListaCodigosLaboratorios();
        if (VariablesInvCiclico.vListaCodLab.size() == 0) {
            FarmaUtility.showMessage(this, "No se encuentran Laboratorios para imprimir", tblRelacionTomas);
            return;
        }
        log.debug("VariablesInvCiclico.vListaCodLab.size() " + VariablesInvCiclico.vListaCodLab.size());
        if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        for (int i = 0; i < VariablesInvCiclico.vListaCodLab.size(); i++) {
            VariablesInvCiclico.vCodLab = ((String)((ArrayList)VariablesInvCiclico.vListaCodLab.get(i)).get(0)).trim();
            VariablesInvCiclico.vNomLab = ((String)((ArrayList)VariablesInvCiclico.vListaCodLab.get(i)).get(1)).trim();
            obtieneProductosPorLaboratorio();
            FarmaPrintService vPrint =
                new FarmaPrintService(66, "D:\\TomaInventario\\" + FarmaVariables.vCodLocal + "\\" +
                                      FarmaVariables.vCodLocal + "_" + VariablesInvCiclico.vCodLab + ".txt", false);
            if (!vPrint.startPrintService()) {
                FarmaUtility.showMessage(this,
                                         "No se pudo inicializar el proceso de impresión\n para el laboratorio " +
                                         VariablesInvCiclico.vCodLab + " - " + VariablesInvCiclico.vNomLab,
                                         tblRelacionTomas);
                return;
            }
            try {
                String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
                vPrint.setStartHeader();
                vPrint.activateCondensed();
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(30) + " REPORTE PRODUCTOS POR LABORATORIO", true);
                vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia.trim(), true);
                vPrint.printBold("Nombre Local : " + FarmaVariables.vDescLocal.trim(), true);
                vPrint.printBold("Laboratorio: " + VariablesInvCiclico.vCodLab + " - " +
                                 VariablesInvCiclico.vNomLab.trim(), true);
                vPrint.printBold("Fecha: " + fechaActual, true);

                vPrint.printLine("=============================================================================================================",
                                 true);
                vPrint.printBold("Codigo  Descripcion                                Unid Presentacion  Unidad Venta       Entero    Fraccion  ",
                                 true);
                vPrint.printLine("=============================================================================================================",
                                 true);
                vPrint.deactivateCondensed();
                vPrint.setEndHeader();
                for (int x = 0; x < VariablesInvCiclico.vListaProductos.size(); x++) {
                    vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)VariablesInvCiclico.vListaProductos.get(x)).get(0)),
                                                                           8) +
                                          FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)VariablesInvCiclico.vListaProductos.get(x)).get(1)),
                                                                           44) +
                                          FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)VariablesInvCiclico.vListaProductos.get(x)).get(2)),
                                                                           19) +
                                          FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)VariablesInvCiclico.vListaProductos.get(x)).get(3)),
                                                                           18) + " " +
                                          FarmaPRNUtility.alinearIzquierda("______    ________", 18), true);
                }
                vPrint.activateCondensed();
                vPrint.printLine("=============================================================================================================",
                                 true);
                vPrint.printBold("Registros Impresos: " +
                                 FarmaUtility.formatNumber(VariablesInvCiclico.vListaProductos.size(), ",##0"), true);
                vPrint.deactivateCondensed();
                vPrint.endPrintServiceSinCompletar();
            } catch (Exception sqlerr) {
                log.error("", sqlerr);
                FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                        sqlerr.getMessage(), tblRelacionTomas);
            }
        }
    }

    public void envioTomaLocal() {
        try {
            if (JConfirmDialog.rptaConfirmDialog(this,
                                                                            "Esta seguro de enviar la toma de inventario al local?")) {
                DBInvCiclico.envioTomaLocal(VariablesInvCiclico.vSecToma);
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Se envio correctamente la Toma de Inventrio Ciclico al local",
                                         tblRelacionTomas);
            }
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            if (e.getErrorCode() > 20000) {
                FarmaUtility.showMessage(this, e.getMessage().substring(10, e.getMessage().indexOf("ORA-06512")),
                                         tblRelacionTomas);
            } else {
                log.error("", e);
                FarmaUtility.showMessage(this, "Error al enviar la toma al local.\n" +
                        e, tblRelacionTomas);
            }
        }
    }

    private boolean cargaLogin() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_AUDITORIA);
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

}
