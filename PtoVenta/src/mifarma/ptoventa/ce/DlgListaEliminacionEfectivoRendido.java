package mifarma.ptoventa.ce;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.UtilityCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaEliminacionEfectivoRendido.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      17.08.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgListaEliminacionEfectivoRendido extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgListaEliminacionEfectivoRendido.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private JTable tblListaEliminacion = new JTable();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaEliminacionEfectivoRendido() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgListaEliminacionEfectivoRendido(Frame parent, String title, boolean modal) {
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
        this.getContentPane().setLayout(borderLayout1);
        this.setSize(new Dimension(754, 306));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(645, 245, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF5.setBounds(new Rectangle(180, 245, 105, 20));
        lblF5.setText("[ F5 ] Eliminar");
        scrLista.setBounds(new Rectangle(10, 30, 730, 205));
        tblListaEliminacion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 730, 20));
        btnLista.setText("Lista");
        btnLista.setBounds(new Rectangle(5, 0, 105, 20));
        btnLista.setMnemonic('L');

        lblF1.setBounds(new Rectangle(300, 245, 160, 20));
        lblF1.setText("[ F1 ] Ingresar Cuadratura");
        lblEnter.setBounds(new Rectangle(10, 245, 155, 20));
        lblEnter.setText("[ ENTER ] Seleccionar");
        scrLista.getViewport().add(tblListaEliminacion, null);
        pnlTitle1.add(btnLista, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DEP_VENTA)) {
            initTableDepositoVenta();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_SERVICIOS_BASICOS)) {
            initTableServiciosBasicos();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_REEMBOLSO_CCH)) {
            initTableReembolsoCajaChica();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_PAGO_PLANILLA)) {
            initTablePagoPlanilla();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_COTIZA_COMPETENCIA)) {
            initTableCotizacionCompetencia();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_ENTREGAS_RENDIR)) {
            initTableEntregasRendir();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_ROBO_ASALTO)) {
            initTableRoboPorAsalto();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DINERO_FALSO_CD)) {
            initTableDineroFalso();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_OTROS_DESEMBOLSOS)) {
            initTableOtrosDesembolsos();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_FONDO_SENCILLO)) {
            initTableFondoLocalNuevo();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DCTO_PERSONAL)) {
            initTableDctoPersonal();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DELIVERY_PERDIDO)) {
            initTableDeliveryPerdido();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_ADELANTO)) {
            initTableAdelanto();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_GRATIFICACION)) {
            initTableGratificacion();
        }

        this.setTitle(VariablesCajaElectronica.vDescCuadratura);
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableDepositoVenta() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionDepositoporVenta, ConstantsCajaElectronica.defaultValuesEliminacionDepositoporVenta,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionDepositoporVenta);
    }

    private void initTableServiciosBasicos() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionServiciosBasicos, ConstantsCajaElectronica.defaultValuesEliminacionServiciosBasicos,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionServiciosBasicos);
    }

    private void initTableReembolsoCajaChica() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionReembolsoCajaCh, ConstantsCajaElectronica.defaultValuesEliminacionReembolsoCajaCh,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionReembolsoCajaCh);
    }

    private void initTablePagoPlanilla() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionPagoPlanilla, ConstantsCajaElectronica.defaultValuesEliminacionPagoPlanilla,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionPagoPlanilla);
    }

    private void initTableCotizacionCompetencia() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionCotizacionCompetencia, ConstantsCajaElectronica.defaultValuesEliminacionCotizacionCompetencia,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionCotizacionCompetencia);
    }

    private void initTableEntregasRendir() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionEntregasRendir, ConstantsCajaElectronica.defaultValuesEliminacionEntregasRendir,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionEntregasRendir);
    }

    private void initTableRoboPorAsalto() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionRoboPorAsalto, ConstantsCajaElectronica.defaultValuesEliminacionRoboPorAsalto,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionRoboPorAsalto);
    }

    private void initTableDineroFalso() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionDineroFalso, ConstantsCajaElectronica.defaultValuesEliminacionDineroFalso,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionDineroFalso);
    }

    private void initTableOtrosDesembolsos() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionOtrosDesembolsos, ConstantsCajaElectronica.defaultValuesEliminacionOtrosDesembolsos,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionOtrosDesembolsos);
    }

    private void initTableFondoLocalNuevo() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionFondoLocalNuevo, ConstantsCajaElectronica.defaultValuesEliminacionFondoLocalNuevo,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionFondoLocalNuevo);
    }

    private void initTableDctoPersonal() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionDctoPersonal, ConstantsCajaElectronica.defaultValuesEliminacionDctoPersonal,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionDctoPersonal);
    }

    private void initTableAdelanto() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionAdelanto, ConstantsCajaElectronica.defaultValuesEliminacionAdelanto,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionAdelanto);
    }

    private void initTableGratificacion() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionAdelanto, ConstantsCajaElectronica.defaultValuesEliminacionAdelanto,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionAdelanto);
    }

    private void initTableDeliveryPerdido() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionDeliveryPerdido, ConstantsCajaElectronica.defaultValuesEliminacionDeliveryPerdido,
                                    0);
        FarmaUtility.initSelectList(tblListaEliminacion, tableModel,
                                    ConstantsCajaElectronica.columnsEliminacionDeliveryPerdido);
    }


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(tblListaEliminacion);
        VariablesCajaElectronica.vArrayListEliminacionEfectivoRendido.clear();
        if (VariablesCajaElectronica.vTipCuadratura.equals(""))
            lblF1.setVisible(false);
        FarmaUtility.centrarVentana(this);
        cargaLista();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void tblLista_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                if (tblListaEliminacion.getRowCount() <= 0)
                    return;
                e.consume();
                UtilityCajaElectronica.getIndicadorVBCierreDia(VariablesCajaElectronica.vFechaCierreDia);
                if (VariablesCajaElectronica.vIndVBCierreDia.equals(FarmaConstants.INDICADOR_N))
                    verificaCheckJTable();
                else
                    FarmaUtility.showMessage(this, "No es posible realizar esta operación.", tblListaEliminacion);

            } else {
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción", null);
            }
        } else
            chkKeyPressed(e);


    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                if (FarmaVariables.vEconoFar_Matriz) {
                    FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblListaEliminacion);
                    return;
                }
                UtilityCajaElectronica.getIndicadorVBCierreDia(VariablesCajaElectronica.vFechaCierreDia);
                if (VariablesCajaElectronica.vIndVBCierreDia.equals(FarmaConstants.INDICADOR_N)) {
                    if (VariablesCajaElectronica.vArrayListEliminacionEfectivoRendido.size() > 0)
                        funcion();
                    else
                        FarmaUtility.showMessage(this, "Seleccione al menos un registro para eliminar",
                                                 tblListaEliminacion);
                } else
                    FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblListaEliminacion);

            } else {
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción", null);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DCTO_PERSONAL))
                validaMontoParcial();
            if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DINERO_FALSO_CD))
                validaMontoParcialDineroFalso();
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            if (lblF1.isVisible()) {
                if (FarmaVariables.vEconoFar_Matriz) {
                    FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblListaEliminacion);
                    return;
                }
                UtilityCajaElectronica.getIndicadorVBCierreDia(VariablesCajaElectronica.vFechaCierreDia);
                if (VariablesCajaElectronica.vIndVBCierreDia.equals(FarmaConstants.INDICADOR_N)) {
                    if (VariablesCajaElectronica.vTipCuadratura.equals("03")) {
                        cargaDatosEfectivoRendidoIngreso();
                    } else if (VariablesCajaElectronica.vTipCuadratura.equals("04")) {
                        cargaListaCotizacionCompetecia();
                    }
                } else
                    FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblListaEliminacion);
            }
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

    private void funcion() {
        //cargaVariablesRegistroSeleccionado();
        eliminacionMultiple();
        //cargaLista();
    }

    private void cargaLista() {
        try {
            /*DBCajaElectronica.listaEliminacionCuadraturaCierreDia(tableModel,
                                                            VariablesCajaElectronica.vCodCuadratura,
                                                            VariablesCajaElectronica.vFechaCierreDia);*/
            DBCajaElectronica.listaEliminacionCuadraturaCierreDia_02(tableModel,
                                                                     VariablesCajaElectronica.vCodCuadratura,
                                                                     VariablesCajaElectronica.vFechaCierreDia); //ASOSA, 25.04.2010
            VariablesCajaElectronica.vArrayListEliminacionEfectivoRendido.clear();
            if (tblListaEliminacion.getRowCount() > 0) {
                //FarmaUtility.ordenar(tblListaEliminacion,tableModel,ConstantsCajaElectronica.INDICE_COLUMNA_SEC_CUAD_CD,FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.ordenar(tblListaEliminacion, tableModel, 3, FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.moveFocusJTable(tblListaEliminacion);
            } else
                FarmaUtility.moveFocus(tblListaEliminacion);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar \n" +
                    sql.getMessage(), null);
        }
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;
        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void cargaVariablesRegistroSeleccionado() {
        if (tieneRegistroSeleccionado(tblListaEliminacion)) {
            int row = tblListaEliminacion.getSelectedRow();
            VariablesCajaElectronica.vSecCuadratura =
                    tblListaEliminacion.getValueAt(row, ConstantsCajaElectronica.INDICE_COLUMNA_SEC_CUAD_CD).toString().trim();
            VariablesCajaElectronica.vFechaCierreDia =
                    tblListaEliminacion.getValueAt(row, ConstantsCajaElectronica.INDICE_COLUMNA_FECHA_CUAD_CD).toString().trim();

            if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_COTIZA_COMPETENCIA)) {
                VariablesCajaElectronica.vNumerodeNota = tblListaEliminacion.getValueAt(row, 11).toString().trim();
                //UtilityCajaElectronica.actualizaGuiaCotizacionCompetencia(this);
            }
        }
    }

    private void eliminaCuadratura() throws SQLException {
        /*try
    {
      if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Esta seguro de eliminar la cuadratura?"))
      {*/
        DBCajaElectronica.eliminaCuadraturaCierreDia(VariablesCajaElectronica.vFechaCierreDia,
                                                     VariablesCajaElectronica.vCodCuadratura,
                                                     VariablesCajaElectronica.vSecCuadratura);
        /* FarmaUtility.aceptarTransaccion();
        FarmaUtility.showMessage(this,"Registro Eliminado satisfactoriamente", tblListaEliminacion);
      }
    } catch (SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      log.error("",sql);
      FarmaUtility.showMessage(this,"Error al eliminar una cuadratura \n" + sql.getMessage(),null);
  }*/
    }

    private void cargaDatosEfectivoRendidoIngreso() {
        DlgDatosEfectivoRendido dlgDatosEfectivoRendido = new DlgDatosEfectivoRendido(myParentFrame, "", true);
        dlgDatosEfectivoRendido.setVisible(true);
        if (FarmaVariables.vAceptar) {
            VariablesCajaElectronica.listaEliminacion.clear();
            cargaLista();
            FarmaVariables.vAceptar = false;
        }
        if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DCTO_PERSONAL)) {
            validaMontoParcial();
        }
        if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DINERO_FALSO_CD)) {
            validaMontoParcialDineroFalso();
        }
    }

    private void cargaListaCotizacionCompetecia() {
        DlgCotizacionCompetencia dlgCotizacionCompetencia = new DlgCotizacionCompetencia(myParentFrame, "", true);
        dlgCotizacionCompetencia.setVisible(true);
        if (FarmaVariables.vAceptar) {
            VariablesCajaElectronica.listaEliminacion.clear();
            cargaLista();
            FarmaVariables.vAceptar = false;
        }
    }

    private void validaMontoParcial() {
        String tipComprobante = new String();
        String numComprobante = new String();
        String tipComprobante_aux = new String();
        String numComprobante_aux = new String();
        String nombreTipComprobante = new String();
        double montoTotal = 0.00;
        double montoParcial = 0.00;
        String montoFaltante = "";
        for (int i = 0; i < tblListaEliminacion.getRowCount(); i++) {
            montoParcial = 0;
            tipComprobante = FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 9);
            numComprobante = FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 2);
            if (i > 0 && tipComprobante.equalsIgnoreCase(tipComprobante_aux) &&
                numComprobante.equalsIgnoreCase(numComprobante_aux))
                continue;
            nombreTipComprobante = FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 1);
            montoTotal = FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 5));
            for (int j = 0; j < tblListaEliminacion.getRowCount(); j++) {
                tipComprobante_aux = FarmaUtility.getValueFieldJTable(tblListaEliminacion, j, 9);
                numComprobante_aux = FarmaUtility.getValueFieldJTable(tblListaEliminacion, j, 2);
                if (tipComprobante_aux.equalsIgnoreCase(tipComprobante) &&
                    numComprobante_aux.equalsIgnoreCase(numComprobante)) {
                    montoParcial =
                            montoParcial + FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblListaEliminacion,
                                                                                                          j, 6));
                    log.debug("montoParcial : " + montoParcial + " : " + j);
                }
                tipComprobante_aux = tipComprobante;
                numComprobante_aux = numComprobante;
            }
            //log.debug("montoParcial : " + montoParcial);
            log.debug("montoTotal : " + montoTotal);
            montoFaltante = FarmaUtility.formatNumber(montoTotal - montoParcial);
            if (montoTotal != montoParcial) {
                FarmaUtility.showMessage(this,
                                         "Falta un monto parcial de " + montoFaltante + "  para completar el monto total del\ntipo de comprobante " +
                                         nombreTipComprobante + " con número " + numComprobante + ". Verifique!!!",
                                         tblListaEliminacion);
                break;
            }
        }
    }

    private void validaMontoParcialDineroFalso() {
        String campoValidar = "";
        String tipoDinero = "";
        String tipoMoneda = "";
        String serie = "";
        String campoValidarAux = "";
        String denominacion = "";
        double montoTotal = 0.00;
        double montoParcial = 0.00;
        String montoFaltante = "";
        for (int i = 0; i < tblListaEliminacion.getRowCount(); i++) {
            montoParcial = 0;
            campoValidar = FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 11);
            tipoDinero = FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 1);
            tipoMoneda = FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 2);
            serie = FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 5);
            denominacion = FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 3);

            if (i > 0 && campoValidar.equalsIgnoreCase(campoValidarAux))
                continue;
            tipoDinero = FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 1);
            tipoMoneda = FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 2);
            serie = FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 5);
            montoTotal = FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblListaEliminacion, i, 3));
            for (int j = 0; j < tblListaEliminacion.getRowCount(); j++) {
                campoValidarAux = FarmaUtility.getValueFieldJTable(tblListaEliminacion, j, 11);
                if (campoValidarAux.equalsIgnoreCase(campoValidar)) {
                    montoParcial =
                            montoParcial + FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblListaEliminacion,
                                                                                                          j, 6));
                    log.debug("montoParcial : " + montoParcial + " : " + j);
                }
                campoValidarAux = campoValidar;
            }
            log.debug("montoTotal : " + montoTotal);
            montoFaltante = FarmaUtility.formatNumber(montoTotal - montoParcial);
            if (montoTotal != montoParcial) {
                if (serie.equalsIgnoreCase("")) {
                    FarmaUtility.showMessage(this,
                                             "Falta un monto parcial de " + montoFaltante + "  para completar el tipo de " +
                                             tipoDinero + " " + tipoMoneda + " con  valor denominacion " +
                                             denominacion + ". Verifique!!!", tblListaEliminacion);
                    break;
                } else {
                    FarmaUtility.showMessage(this,
                                             "Falta un monto parcial de " + montoFaltante + "  para completar el tipo de " +
                                             tipoDinero + " " + tipoMoneda + "\n con numero de Serie " + serie +
                                             " y valor denominacion " + denominacion + ". Verifique!!!",
                                             tblListaEliminacion);
                    break;
                }
            }
        }
    }

    private void verificaCheckJTable() {
        String pEntidadFinanciera =
            tblListaEliminacion.getValueAt(tblListaEliminacion.getSelectedRow(), 5).toString().trim();
        if (pEntidadFinanciera.trim().equalsIgnoreCase("PROSEGUR")) {
            FarmaUtility.showMessage(this, "No puede seleccionar este registro de PROSEGUR", tblListaEliminacion);
            return;
        }

        String pValorBoleta =
            FarmaUtility.getValueFieldArrayList(tableModel.data, tblListaEliminacion.getSelectedRow(), 1);
        log.debug("indice de Toma : " + pValorBoleta.indexOf("-INV_D"));
        if (pValorBoleta.indexOf("-INV_D") != -1) {
            FarmaUtility.showMessage(this, "No puede seleccionar este registro Generado por el Auditor",
                                     tblListaEliminacion);
            return;
        }

        cargaVariablesEliminar();
        Boolean valor = (Boolean)(tblListaEliminacion.getValueAt(tblListaEliminacion.getSelectedRow(), 0));
        seleccionaDeseleccionaRegistro();
    }

    private void cargaVariablesEliminar() {
        int row = tblListaEliminacion.getSelectedRow();
        VariablesCajaElectronica.vSecCuadratura =
                tblListaEliminacion.getValueAt(row, ConstantsCajaElectronica.INDICE_COLUMNA_SEC_CUAD_CD).toString().trim();
        VariablesCajaElectronica.vFechaCierreDia =
                tblListaEliminacion.getValueAt(row, ConstantsCajaElectronica.INDICE_COLUMNA_FECHA_CUAD_CD).toString().trim();
        if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_COTIZA_COMPETENCIA)) {
            VariablesCajaElectronica.vNumerodeNota = tblListaEliminacion.getValueAt(row, 11).toString().trim();
            //UtilityCajaElectronica.actualizaGuiaCotizacionCompetencia(this);
        }
    }

    private void seleccionaDeseleccionaRegistro() {
        Boolean valorTmp = (Boolean)(tblListaEliminacion.getValueAt(tblListaEliminacion.getSelectedRow(), 0));
        FarmaUtility.setCheckValue(tblListaEliminacion, false);
        Boolean valor = (Boolean)(tblListaEliminacion.getValueAt(tblListaEliminacion.getSelectedRow(), 0));
        operaRegistroSeleccionadoEnArrayList(valor);
    }

    private void operaRegistroSeleccionadoEnArrayList(Boolean valor) {
        ArrayList myArray = new ArrayList();
        myArray.add(VariablesCajaElectronica.vSecCuadratura);
        myArray.add(VariablesCajaElectronica.vFechaCierreDia);
        myArray.add(VariablesCajaElectronica.vNumerodeNota);
        FarmaUtility.operaListaProd(VariablesCajaElectronica.vArrayListEliminacionEfectivoRendido, myArray, valor, 0);
        log.debug("size : " + VariablesCajaElectronica.vArrayListEliminacionEfectivoRendido.size());
        log.debug("array : " + VariablesCajaElectronica.vArrayListEliminacionEfectivoRendido);
    }

    private void eliminacionMultiple() {
        boolean funcionAgrega = false;
        try {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "¿Esta seguro de eliminar los efectivos rendidos ?")) {
                for (int i = 0; i < VariablesCajaElectronica.vArrayListEliminacionEfectivoRendido.size(); i++) {
                    VariablesCajaElectronica.vSecCuadratura =
                            (String)(((ArrayList)VariablesCajaElectronica.vArrayListEliminacionEfectivoRendido.get(i)).get(0));
                    VariablesCajaElectronica.vFechaCierreDia =
                            (String)(((ArrayList)VariablesCajaElectronica.vArrayListEliminacionEfectivoRendido.get(i)).get(1));
                    VariablesCajaElectronica.vNumerodeNota =
                            (String)(((ArrayList)VariablesCajaElectronica.vArrayListEliminacionEfectivoRendido.get(i)).get(2));
                    eliminaCuadratura();
                    funcionAgrega = true;
                    if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_COTIZA_COMPETENCIA))
                        UtilityCajaElectronica.actualizaGuiaCotizacionCompetencia(this);
                }
                if (funcionAgrega) {
                    FarmaUtility.aceptarTransaccion();
                    cargaLista();
                }
            }
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al grabar la informacion \n" +
                    sql.getMessage(), null);
        }
    }

}
