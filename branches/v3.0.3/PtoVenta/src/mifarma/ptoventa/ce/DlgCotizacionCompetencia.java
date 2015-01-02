package mifarma.ptoventa.ce;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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
 * Nombre de la Aplicación : DlgCotizacionCompetencia.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      23.08.2006   Creación<br>
 * <br>
 * @author Paulo Cesar Ameghino Rojas<br>
 * @version 1.0<br>
 *
 */
public class DlgCotizacionCompetencia extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgCotizacionCompetencia.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private JTable tblLista = new JTable();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JLabelFunction lblEnter = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgCotizacionCompetencia() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgCotizacionCompetencia(Frame parent, String title, boolean modal) {
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
        this.setTitle("Cuadraturas");
        this.setSize(new Dimension(537, 303));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(425, 245, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(180, 245, 120, 20));
        lblF11.setText("[ F11 ] Aceptar");
        scrLista.setBounds(new Rectangle(10, 30, 515, 205));
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 515, 20));
        btnLista.setText("Cotizaciones de Competencia");
        btnLista.setBounds(new Rectangle(5, 0, 215, 20));
        btnLista.setMnemonic('C');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        lblEnter.setBounds(new Rectangle(10, 245, 155, 20));
        lblEnter.setText("[ ENTER ] Seleccionar");
        scrLista.getViewport().add(tblLista, null);
        pnlTitle1.add(btnLista, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        VariablesCajaElectronica.vArrayListInsertarCotizacion.clear();
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsListaCotizacionCompetencia, ConstantsCajaElectronica.defaultValuesListaCotizacionCompetencia,
                                    0);
        FarmaUtility.initSelectList(tblLista, tableModel, ConstantsCajaElectronica.columnsListaCotizacionCompetencia);
    }

    private void cargaLista() {
        try {
            DBCajaElectronica.listaCotizacionCompetencia(tableModel, VariablesCajaElectronica.vFechaCierreDia);
            VariablesCajaElectronica.vArrayListInsertarCotizacion.clear();
            if (tblLista.getRowCount() > 0) {
                FarmaUtility.ordenar(tblLista, tableModel, 3, FarmaConstants.ORDEN_DESCENDENTE);
                FarmaUtility.moveFocusJTable(tblLista);
            } else
                FarmaUtility.moveFocus(tblLista);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "", btnLista);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocusJTable(tblLista);
        FarmaUtility.centrarVentana(this);
        if (VariablesCajaElectronica.vTipCuadratura.equals("05")) { //ASOSA, 12.08.2010
            cargaLista_TURNO();
        } else {
            cargaLista();
        }

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblLista);
    }

    private void tblLista_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (tblLista.getRowCount() <= 0)
                return;
            e.consume();
            UtilityCajaElectronica.getIndicadorVBCierreDia(VariablesCajaElectronica.vFechaCierreDia);
            log.debug("XXXXXX VariablesCajaElectronica.vIndVBCierreDia: " + VariablesCajaElectronica.vIndVBCierreDia);
            /*if(VariablesCajaElectronica.vIndVBCierreDia.equals(FarmaConstants.INDICADOR_N)) antes
        verificaCheckJTable();
      else
        FarmaUtility.showMessage(this, "No es posible realizar esta operación.", tblLista);*/
            if (VariablesCajaElectronica.vIndVBCierreDia.equals(FarmaConstants.INDICADOR_S)) //ASOSA, 09.08.2010
                FarmaUtility.showMessage(this, "No es posible realizar esta operación.", tblLista);
            else
                verificaCheckJTable();

        } else
            chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblLista);
                return;
            }
            UtilityCajaElectronica.getIndicadorVBCierreDia(VariablesCajaElectronica.vFechaCierreDia);
            /*if(VariablesCajaElectronica.vIndVBCierreDia.equals(FarmaConstants.INDICADOR_N)){    antes
        if(VariablesCajaElectronica.vArrayListInsertarCotizacion.size()>0){
          funcion();
        } else FarmaUtility.showMessage(this,"Seleccione al menos un registro para agregar",tblLista);
      }  else
        FarmaUtility.showMessage(this, "No es posible realizar esta operación.", tblLista);*/
            if (VariablesCajaElectronica.vIndVBCierreDia.equals(FarmaConstants.INDICADOR_S)) { //ASOSA, 11.08.2010
                FarmaUtility.showMessage(this, "No es posible realizar esta operación.", tblLista);
            } else {
                if (VariablesCajaElectronica.vArrayListInsertarCotizacion.size() > 0) {
                    funcion();
                } else
                    FarmaUtility.showMessage(this, "Seleccione al menos un registro para agregar", tblLista);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void funcion() {
        insercionMultiple();
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void insertaCuadraturaLista() throws SQLException {
        DBCajaElectronica.agregaCotizacionCompetencia(VariablesCajaElectronica.vCodCuadratura,
                                                      VariablesCajaElectronica.vFechaCierreDia,
                                                      VariablesCajaElectronica.vNumerodeNota,
                                                      VariablesCajaElectronica.vMontTotal,
                                                      VariablesCajaElectronica.vGlosa);
    }

    private void cargaVariablesInsertar() {
        VariablesCajaElectronica.vMontTotal = ((String)tblLista.getValueAt(tblLista.getSelectedRow(), 3)).trim();
        VariablesCajaElectronica.vNumerodeNota = ((String)tblLista.getValueAt(tblLista.getSelectedRow(), 4)).trim();
        VariablesCajaElectronica.vGlosa = ((String)tblLista.getValueAt(tblLista.getSelectedRow(), 6)).trim();
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;
        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void verificaCheckJTable() {
        cargaVariablesInsertar();
        Boolean valor = (Boolean)(tblLista.getValueAt(tblLista.getSelectedRow(), 0));
        seleccionaDeseleccionaRegistro();
    }

    private void seleccionaDeseleccionaRegistro() {
        Boolean valorTmp = (Boolean)(tblLista.getValueAt(tblLista.getSelectedRow(), 0));
        FarmaUtility.setCheckValue(tblLista, false);
        Boolean valor = (Boolean)(tblLista.getValueAt(tblLista.getSelectedRow(), 0));
        operaRegistroSeleccionadoEnArrayList(valor);
    }

    private void operaRegistroSeleccionadoEnArrayList(Boolean valor) {
        ArrayList myArray = new ArrayList();
        myArray.add(VariablesCajaElectronica.vMontTotal);
        myArray.add(VariablesCajaElectronica.vNumerodeNota);
        myArray.add(VariablesCajaElectronica.vGlosa);
        FarmaUtility.operaListaProd(VariablesCajaElectronica.vArrayListInsertarCotizacion, myArray, valor, 1);
        log.debug("size : " + VariablesCajaElectronica.vArrayListInsertarCotizacion.size());
        log.debug("array : " + VariablesCajaElectronica.vArrayListInsertarCotizacion);
    }

    private void insercionMultiple() {
        boolean funcionAgrega = false;
        try {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "¿Esta seguro de agregar la(s) cotizaciones de competencia(s) ?")) {
                for (int i = 0; i < VariablesCajaElectronica.vArrayListInsertarCotizacion.size(); i++) {
                    VariablesCajaElectronica.vMontTotal =
                            (String)(((ArrayList)VariablesCajaElectronica.vArrayListInsertarCotizacion.get(i)).get(0));
                    VariablesCajaElectronica.vNumerodeNota =
                            (String)(((ArrayList)VariablesCajaElectronica.vArrayListInsertarCotizacion.get(i)).get(1));
                    VariablesCajaElectronica.vGlosa =
                            (String)(((ArrayList)VariablesCajaElectronica.vArrayListInsertarCotizacion.get(i)).get(2));
                    if (VariablesCajaElectronica.vTipCuadratura.equals("05")) { //ASOSA, 12.08.2010
                        insertaCuadraturaListaCTURNO();
                    } else {
                        insertaCuadraturaLista();
                    }
                    funcionAgrega = true;
                    if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_COTIZA_COMPETENCIA))
                        UtilityCajaElectronica.actualizaGuiaCotizacionCompetencia(this);
                }
                if (funcionAgrega) {
                    FarmaUtility.aceptarTransaccion();
                    if (VariablesCajaElectronica.vTipCuadratura.equals("05")) { //ASOSA, 12.08.2010
                        cargaLista_TURNO();
                    } else {
                        cargaLista();
                    }
                }
            }
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al grabar la informacion \n" +
                    sql.getMessage(), null);
        }
    }

    /**
     * Registrar la cotizacion de competencia en el cierre de turno
     * @author ASOSA
     * @since 12.08.2010
     * @throws SQLException
     */
    private void insertaCuadraturaListaCTURNO() throws SQLException {
        DBCajaElectronica.agregaCotizacionCompetenciaCTURNO(VariablesCajaElectronica.vCodCuadratura,
                                                            VariablesCajaElectronica.vNumerodeNota,
                                                            VariablesCajaElectronica.vMontTotal,
                                                            VariablesCajaElectronica.vGlosa,
                                                            VariablesCajaElectronica.vSecMovCaja);
    }

    /**
     * Lista pero para cotizacion de competencias de turno
     * @author ASOSA
     * @since 12.08.2010
     */
    private void cargaLista_TURNO() {
        try {
            DBCajaElectronica.listaCotizacionCompetenciaTURNO(tableModel, VariablesCajaElectronica.vTipCuadratura);
            VariablesCajaElectronica.vArrayListInsertarCotizacion.clear();
            if (tblLista.getRowCount() > 0) {
                FarmaUtility.ordenar(tblLista, tableModel, 3, FarmaConstants.ORDEN_DESCENDENTE);
                FarmaUtility.moveFocusJTable(tblLista);
            } else
                FarmaUtility.moveFocus(tblLista);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "", btnLista);
        }
    }

}
