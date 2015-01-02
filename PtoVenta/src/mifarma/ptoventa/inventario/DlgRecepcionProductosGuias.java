package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
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

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.recepcionCiega.DlgListaTransportistas;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.reportes.DlgOrdenar;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRecepcionProductosGuias extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgRecepcionProductosGuias.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    private boolean todos = false;
    private final int CODIGO = 1;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JButtonLabel btnRelacionGuias = new JButtonLabel();
    private JTable tblListaGuias = new JTable();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JButtonLabel btnBuscar = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JLabelFunction lblF7 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgRecepcionProductosGuias() {
        this(null, "", false);
    }

    public DlgRecepcionProductosGuias(Frame parent, String title, boolean modal) {
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

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(526, 442));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Guias Pendientes");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 500, 50));
        pnlTitle1.setBounds(new Rectangle(10, 70, 500, 25));
        scrListaProductos.setBounds(new Rectangle(10, 95, 500, 240));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(425, 380, 85, 20));
        lblF2.setText("[ F2 ] Afecta Página");
        lblF2.setBounds(new Rectangle(125, 350, 125, 20));
        btnRelacionGuias.setText("Relacion de Guias Seleccionadas");
        btnRelacionGuias.setBounds(new Rectangle(5, 5, 215, 15));
        lblF1.setText("[ F1 ] Ver Detalle");
        lblF1.setBounds(new Rectangle(10, 350, 110, 20));
        lblF9.setText("[ F9 ] Ordenar");
        lblF9.setBounds(new Rectangle(330, 380, 90, 20));
        btnBuscar.setText("Buscar :");
        btnBuscar.setBounds(new Rectangle(5, 15, 55, 15));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtBuscar.setBounds(new Rectangle(60, 15, 330, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        lblF7.setText("[ F7 ] Seleccionar Todos");
        lblF7.setBounds(new Rectangle(165, 380, 160, 20));
        jContentPane.add(lblF7, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblEsc, null);
        scrListaProductos.getViewport().add(tblListaGuias, null);
        jContentPane.add(scrListaProductos, null);
        pnlTitle1.add(btnRelacionGuias, null);
        jContentPane.add(pnlTitle1, null);
        pnlHeader1.add(txtBuscar, null);
        pnlHeader1.add(btnBuscar, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        txtBuscar.setDocument(new FarmaLengthText(10));
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaGuiasRecepcion, ConstantsInventario.defaultValuesListaGuiasRecepcion,
                                    0);
        FarmaUtility.initSelectList(tblListaGuias, tableModel, ConstantsInventario.columnsListaGuiasRecepcion);
        tblListaGuias.getTableHeader().setReorderingAllowed(false);
        tblListaGuias.getTableHeader().setResizingAllowed(false);
        cargaListaGuias();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtBuscar);

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            int i = tblListaGuias.getSelectedRow();
            boolean check = ((Boolean)tblListaGuias.getValueAt(i, 0)).booleanValue();
            FarmaUtility.setearCheckInRow(tblListaGuias, new Boolean(check), true, false, "", 0);
            //seleccionarGuia(tblListaGuias.getSelectedRow());    //ASOSA, 08.02.2010

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            if (tieneRegistroSeleccionado(tblListaGuias)) {
                //{
                int row = tblListaGuias.getSelectedRow();
                cargarGuiaSeleccionada(tblListaGuias.getSelectedRow());
                DlgRecepcionProductosDetalleGuia dlgRecepcionProductosDetalleGuia =
                    new DlgRecepcionProductosDetalleGuia(myParentFrame, "", true);
                dlgRecepcionProductosDetalleGuia.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    cargaListaGuias();
                    tblListaGuias.repaint();
                }

                FarmaGridUtils.showCell(tblListaGuias, row, 0);
                //}
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) { // afectar pagina

            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtBuscar);
            else if (tieneRegistroSeleccionados(tblListaGuias))
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "¿Está seguro de afectar las Guias Seleccionadas?")) {

                    try {

                        if (VariablesPtoVenta.vIndRecepCiega.equalsIgnoreCase("N")) {
                            //ASOSA 08.02.10
                            //----------------------- JMIRANDA 09.02.10
                            String valorTipo = validaTiposEspeciales();
                            if (valorTipo.trim().equalsIgnoreCase("2")) {
                                //No puede mezclar ingreso tipo ZREG, ZTNL o UB con otras
                                //FarmaUtility.showMessage(this,"No puede Mezclar Entregas de tipo ZREG, ZTNL y/o UB. Con OTRAS." +
                                FarmaUtility.showMessage(this,
                                                         "No puede Mezclar Entregas de tipo ZREG, ZTNL y/o UB-L. Con UB." +
                                                         "\nAfecte estas guías por separado.", txtBuscar);
                                return;
                            }
                            if (valorTipo.trim().equalsIgnoreCase("3")) {

                                //                if (!ConstantsRecepCiega.error_du) {
                                if (!UtilityRecepCiega.indHabDatosTransp()) {
                                    //si puede afectar, pide cabecera
                                    ingresaCabeceraEntregas();
                                    cargaListaGuias();
                                    tblListaGuias.repaint();
                                } else {
                                    //JMIRANDA 18.03.2010 ASOCIAR ENTREGAS A CABECERA
                                    asociaCabecera();
                                }
                            }
                            if (valorTipo.trim().equalsIgnoreCase("1")) {
                                //no pida cabecera, Afecta tipo ZREG, UB, ZTNL
                                afectaEntregaTipoEspecial();
                                //cargaListaGuias();
                                //tblListaGuias.repaint();
                            }
                        } else {
                            // JMIRANDA 09.02.10 COMO ANTES
                            //-----------------------
                            String valorTipo = validaTiposEspeciales();
                            //-----------------------
                            for (int i = 0; i < tblListaGuias.getRowCount(); i++) {
                                boolean check = ((Boolean)tblListaGuias.getValueAt(i, 0)).booleanValue();
                                if (check) {
                                    cargarGuiaSeleccionada(i);
                                    log.debug("CARGA_GUIA,VariablesInventario.vNumEntAfectar: " +
                                              VariablesInventario.vNumEntAfectar);

                                    //JCHAVEZ 17122009 se valida que la recepcion ciega este activa
                                    if (VariablesPtoVenta.vIndRecepCiega.equalsIgnoreCase("S")) { // si esta activa la recepcion ciega
                                        if (validaTipoPedReg(VariablesInventario.vTipoPedRep.trim())) {
                                            log.debug("vTipoPedRep: " + VariablesInventario.vTipoPedRep);
                                            DBInventario.actualizaPagina();

                                        } else {
                                            FarmaUtility.showMessage(this,
                                                                     "UD. no puede afectar este tipo de guías solo podrá afectar de ZREG.",
                                                                     txtBuscar);
                                            return;
                                        }
                                    } /*else if (VariablesPtoVenta.vIndRecepCiega.equalsIgnoreCase("N")){
                     DBInventario.actualizaPagina();
                   --JMIRANDA 09.02.2010 Agregado en AfectaEntregaTipoEspecial
                 } */

                                }
                            }
                            FarmaUtility.aceptarTransaccion();
                            cargaListaGuias();
                            FarmaUtility.showMessage(this, "La página se afectó correctamente", txtBuscar);

                        }

                    } catch (SQLException sql) {
                        FarmaUtility.liberarTransaccion();
                        log.error("", sql);
                        FarmaUtility.showMessage(this, "Ocurrió un error al afectar la página : \n" +
                                sql.getMessage(), txtBuscar);

                    }

                    FarmaVariables.vAceptar = false;
                }

        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            ordenar();
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtBuscar);
            else {
                todos = !todos;
                //log.debug(todos);
                seleccionarTodos(todos);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaGuias() {
        try {
            DBInventario.getListaGuiasRecepPedido(tableModel);

            if (tblListaGuias.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaGuias, tableModel, 1, FarmaConstants.ORDEN_ASCENDENTE);
            else
                cerrarVentana(true);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener las guias del pedido : \n" +
                    sql.getMessage(), txtBuscar);
        }
    }


    /**
     * Remueve un elemento de la lista de guias
     * @author ASOSA
     * @since 08.02.2010
     * @param fila
     */
    private void removerGuia(int fila) {
        String cod = tblListaGuias.getValueAt(fila, CODIGO).toString();
        for (int c = 0; c < VariablesInventario.lista.size(); c++) {
            if (((ArrayList)(VariablesInventario.lista.get(c))).contains(cod)) {
                VariablesInventario.lista.remove(c);
                break;
            }
        }
    }

    /**
     * Agregar un elemento a la lista
     * @author ASOSA
     * @since 08.02.2010
     * @param pFila
     */
    private void agregaGuia(int pFila) {
        //cambio de posicion
        VariablesInventario.vNumGuia02 = tblListaGuias.getValueAt(pFila, 1).toString(); //chek
        VariablesInventario.vNumEntrega = tblListaGuias.getValueAt(pFila, 2).toString();
        VariablesInventario.vNumNotaEst = tblListaGuias.getValueAt(pFila, 4).toString();
        VariablesInventario.vSecGuia = tblListaGuias.getValueAt(pFila, 6).toString();

        VariablesInventario.cPos = pFila;
        ArrayList array = new ArrayList();
        array.add(VariablesInventario.vNumGuia02);
        array.add(VariablesInventario.vNumEntrega);
        array.add(VariablesInventario.vNumNotaEst);
        array.add(VariablesInventario.vSecGuia);
        array.add("A");
        VariablesInventario.lista.add(array);
        log.debug("GUIAS ASOCIADAS :" + VariablesInventario.lista);
    }

    /**
     * Selecciona o deselecciona guias
     * @autor ASOSA
     * @since 08.02.2010
     * @param fila
     */
    private void seleccionarGuia(int fila) {
        boolean selec = ((Boolean)tblListaGuias.getValueAt(fila, 0)).booleanValue();
        if (!selec) {
            removerGuia(fila);
            agregaGuia(fila);
            FarmaUtility.setCheckValue(tblListaGuias, false);
            tblListaGuias.setRowSelectionInterval(VariablesInventario.cPos, VariablesInventario.cPos);
        } else {
            removerGuia(fila);
            FarmaUtility.setCheckValue(tblListaGuias, true);
        }
    }

    private void cargarGuiaSeleccionada(int row) {
        VariablesInventario.vNumGuia = tblListaGuias.getValueAt(row, 1).toString();
        VariablesInventario.vNumPag = tblListaGuias.getValueAt(row, 2).toString();
        VariablesInventario.vCantItems = tblListaGuias.getValueAt(row, 4).toString();
        VariablesInventario.vNumEntAfectar = tblListaGuias.getValueAt(row, 5).toString(); //JMIRANDA 09.02.10
        VariablesInventario.vNumNotaEs =
                tblListaGuias.getValueAt(row, 8).toString().trim(); //ERA 7 SE AUMENTO TIPO_PED_REP
        VariablesInventario.vTipoPedRep = tblListaGuias.getValueAt(row, 7).toString().trim();
        VariablesInventario.vFecCreaNota = tblListaGuias.getValueAt(row, 6).toString().trim();

    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;
        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private boolean tieneRegistroSeleccionados(JTable pTabla) {
        boolean rpta = false;
        for (int i = 0; i < tblListaGuias.getRowCount(); i++) {
            boolean check = ((Boolean)tblListaGuias.getValueAt(i, 0)).booleanValue();
            if (check) {
                rpta = true;
                break;
            }
        }

        return rpta;
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void ordenar() {
        if (tblListaGuias.getRowCount() > 0) {
            //
            DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
            // los datos de abajo son variables y constantes y tienen q crearlos respectivamente
            VariablesReporte.vNombreInHashtable = ConstantsInventario.vNombreInHashtableGuiaRecep;
            FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable,
                                           ConstantsInventario.vCodCampoGuiaRecep,
                                           ConstantsInventario.vDescCampoGuiaRecep, true);
            dlgOrdenar.setVisible(true);
            //
            if (FarmaVariables.vAceptar) {
                FarmaUtility.ordenar(tblListaGuias, tableModel, VariablesReporte.vCampo, VariablesReporte.vOrden);
                tblListaGuias.repaint();
                FarmaVariables.vAceptar = false;
            }
        }
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaGuias, txtBuscar, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            //
            txtBuscar.setText(FarmaUtility.completeWithSymbol(txtBuscar.getText().trim(), 10, "0", "I"));
            //
            if (tblListaGuias.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaGuias, txtBuscar.getText().trim(), 5, 1))) {
                    FarmaUtility.showMessage(this, "Dato No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaGuias, txtBuscar, 1);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void seleccionarTodos(boolean valor) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(new Boolean(valor), i, 0);
        }
        tblListaGuias.repaint();
    }

    private boolean validaTipoPedReg(String pTipoPedReg) {
        boolean flag = false;
        if (pTipoPedReg.equalsIgnoreCase(ConstantsInventario.TIPO_PED_REG_ZREG)) {
            flag = true;
        }
        if (pTipoPedReg.equalsIgnoreCase(ConstantsInventario.TIPO_PED_REG_UB)) {
            //flag = true;
            flag = false;
        }
        if (pTipoPedReg.equalsIgnoreCase(ConstantsInventario.TIPO_PED_REG_ZTNL)) {
            flag = true;
        }
        if (pTipoPedReg.equalsIgnoreCase(ConstantsInventario.TIPO_PED_REG_UB_L)) {
            flag = true;
        }
        /*else {
          flag = false;
         /* FarmaUtility.showMessage(this,"Ud. no puede Afectar está guía. " +
                          "\nPara Afectarla utilice el módulo de Recepción Ciega.",txtBuscar);

      }*/
        return flag;
    }

    private String validaTiposEspeciales() {
        int cantTipoZREG = 0;
        int cantTipoUB = 0;
        int cantTipoZTNL = 0;
        int totalTipos = 0;
        int cantOtroTipo = 0;
        int cantTipoUBL = 0;
        String rpta = "";
        for (int i = 0; i < tblListaGuias.getRowCount(); i++) {
            boolean check = ((Boolean)tblListaGuias.getValueAt(i, 0)).booleanValue();
            if (check) {
                //        cargarGuiaSeleccionada(i);
                VariablesInventario.vTipoPedRep = tblListaGuias.getValueAt(i, 7).toString().trim();
                if (VariablesInventario.vTipoPedRep.trim().equalsIgnoreCase(ConstantsInventario.TIPO_PED_REG_ZREG)) {
                    cantTipoZREG += 1;
                }
                if (VariablesInventario.vTipoPedRep.trim().equalsIgnoreCase(ConstantsInventario.TIPO_PED_REG_UB)) {
                    cantTipoUB += 1;
                }
                if (VariablesInventario.vTipoPedRep.trim().equalsIgnoreCase(ConstantsInventario.TIPO_PED_REG_ZTNL)) {
                    cantTipoZTNL += 1;
                }
                if (VariablesInventario.vTipoPedRep.trim().equalsIgnoreCase(ConstantsInventario.TIPO_PED_REG_UB_L)) {
                    cantTipoUBL += 1;
                }
                if (VariablesInventario.vTipoPedRep.trim().equalsIgnoreCase("")) {
                    cantOtroTipo += 1;
                }
            }
        }
        totalTipos = cantTipoZREG + cantTipoUB + cantTipoZTNL;

        //if(cantOtroTipo == 0){
        if (cantOtroTipo == 0 && cantTipoUB == 0) {
            //if((cantTipoZREG > 0 || cantTipoUB > 0 || cantTipoZTNL > 0)){
            if ((cantTipoZREG > 0 || cantTipoZTNL > 0 || cantTipoUBL > 0)) {
                //no pida cabecera, Afecta tipo ZREG, UB, ZTNL
                log.debug("Caso 1");
                log.debug("AFECTA cantOtroTIpo:" + cantOtroTipo);
                log.debug("cantTipoZREG: " + cantTipoZREG);
                log.debug("cantTipoUB: " + cantTipoUB);
                log.debug("cantTipoUBL: " + cantTipoUBL);
                log.debug("cantTipoZTNL: " + cantTipoZTNL);
                rpta = "1";
            }

        }
        if (cantOtroTipo > 0 || cantTipoUB > 0) {
            //if((cantTipoZREG > 0 || cantTipoUB > 0 || cantTipoZTNL > 0)){
            if ((cantTipoZREG > 0 || cantTipoZTNL > 0 || cantTipoUBL > 0)) {
                //No puede afectar que escoja solo lo demás
                log.debug("Caso 2");
                log.debug("cantOtroTipo: " + cantOtroTipo);
                log.debug("cantTipoZREG: " + cantTipoZREG);
                log.debug("cantTipoUB: " + cantTipoUB);
                log.debug("cantTipoUBL: " + cantTipoUBL);
                log.debug("cantTipoZTNL: " + cantTipoZTNL);
                rpta = "2";


            }
            //if(cantTipoZREG == 0 && cantTipoUB == 0 && cantTipoZTNL == 0){
            if (cantTipoZREG == 0 && cantTipoZTNL == 0 && cantTipoUBL == 0) {
                //si puede afectar, pide cabecera
                log.debug("Sí puede afectar NO existe otros tipos");
                log.debug("Caso 3");
                log.debug("cantOtroTipo: " + cantOtroTipo);
                log.debug("cantTipoZREG: " + cantTipoZREG);
                log.debug("cantTipoUB: " + cantTipoUB);
                log.debug("cantTipoUBL: " + cantTipoUBL);
                log.debug("cantTipoZTNL: " + cantTipoZTNL);
                rpta = "3";
            }
        }
        return rpta;
    }
    /*
  private void ingresaCabeceraEntregas(){
      DlgDatosTransportistaInv objDTI=new DlgDatosTransportistaInv(myParentFrame,"",true);
      objDTI.setVisible(true);
        //GRABA DATOS CABECERA ENTREGA
        try {

            if (FarmaVariables.vAceptar) {
                VariablesRecepCiega.vNro_Recepcion =
                        DBInventario.creaRecepEntrega();
                int CantGuias = 0; //JMIRANDA 09.02.10
                for (int i = 0; i < tblListaGuias.getRowCount(); i++) {
                    boolean check =
                        ((Boolean)tblListaGuias.getValueAt(i, 0)).booleanValue();
                    if (check) {
                        cargarGuiaSeleccionada(i);
                        log.debug("CARGA_GUIA,VariablesInventario.vNumEntAfectar: " +
                                           VariablesInventario.vNumEntAfectar);

                        DBInventario.actualizaPagina();

                        //JMIRANDA 09.02.10
                        //Cambio para asociar las entregas en la tabla lgt_recep_entrega solo si no esta activado la RecepCiega
                        DBInventario.asociaRecepEntrega(VariablesRecepCiega.vNro_Recepcion);
                        CantGuias += 1;
                    }
                }
                //Actualiza Cantidad de Guías Afectadas por entrega
                log.debug("CantGuias: " + CantGuias);
                DBInventario.actualizaCantGuias(CantGuias,VariablesRecepCiega.vNro_Recepcion);

                FarmaUtility.aceptarTransaccion();
                cargaListaGuias();
                tblListaGuias.repaint();
                FarmaUtility.showMessage(this,
                                         "La página se afectó correctamente",
                                         txtBuscar);

            } else {
                //FarmaUtility.showMessage(this,"Ingrese Datos en la Cabecera.",txtBuscar);
                log.debug("Cerro ventana ");
             }
         }
         catch (SQLException e) {
             FarmaUtility.liberarTransaccion();
             log.error("",e);
                 FarmaUtility.showMessage(this,"Ocurrió un error al afectar la página : \n"+ e.getMessage(), txtBuscar);
         }
      }
    */
    //JQUISPE 15.04.2010 CAMBIOS SIN RECEPCION CIEGA

    private void ingresaCabeceraEntregas() {
        DlgDatosTransportistaInv objDTI = new DlgDatosTransportistaInv(myParentFrame, "", true);
        objDTI.setVisible(true);
        //GRABA DATOS CABECERA ENTREGA
        try {

            if (FarmaVariables.vAceptar) {
                VariablesRecepCiega.vNro_Recepcion = DBInventario.creaRecepEntrega();
                int CantGuias = 0; //JMIRANDA 09.02.10
                for (int i = 0; i < tblListaGuias.getRowCount(); i++) {
                    boolean check = ((Boolean)tblListaGuias.getValueAt(i, 0)).booleanValue();
                    if (check) {
                        cargarGuiaSeleccionada(i);
                        log.debug("CARGA_GUIA,VariablesInventario.vNumEntAfectar: " +
                                  VariablesInventario.vNumEntAfectar);

                        DBInventario.actualizaPagina();

                        //JMIRANDA 09.02.10
                        //Cambio para asociar las entregas en la tabla lgt_recep_entrega solo si no esta activado la RecepCiega
                        DBInventario.asociaRecepEntrega(VariablesRecepCiega.vNro_Recepcion);
                        CantGuias += 1;
                    }
                }
                //Actualiza Cantidad de Guías Afectadas por entrega
                log.debug("CantGuias: " + CantGuias);
                DBInventario.actualizaCantGuias(CantGuias, VariablesRecepCiega.vNro_Recepcion);

                FarmaUtility.aceptarTransaccion();
                cargaListaGuias();
                tblListaGuias.repaint();
                FarmaUtility.showMessage(this, "La página se afectó correctamente", txtBuscar);

            } else {
                //FarmaUtility.showMessage(this,"Ingrese Datos en la Cabecera.",txtBuscar);
                log.debug("Cerro ventana ");
            }
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            if (sql.getErrorCode() == 20010) {
                FarmaUtility.showMessage(this, sql.getMessage().substring(11, sql.getMessage().indexOf("ORA-06512")),
                                         txtBuscar);
            } else {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrió un error al afectar la página : \n" +
                        sql.getMessage(), txtBuscar);
            }

        }
    }

    private void afectaEntregaTipoEspecial() {
        try {
            for (int i = 0; i < tblListaGuias.getRowCount(); i++) {
                boolean check = ((Boolean)tblListaGuias.getValueAt(i, 0)).booleanValue();
                if (check) {
                    cargarGuiaSeleccionada(i);
                    DBInventario.actualizaPagina();
                }
            }
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "La página se afectó correctamente", txtBuscar);
            cargaListaGuias();
            tblListaGuias.repaint();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al Afectar Página. \n" +
                    sql.getMessage(), txtBuscar);
        }
    }
    /*
     private void asociaCabecera(){
         VariablesRecepCiega.vIndAsociaTransportista = true;
         DlgListaTransportistas dlgLista = new DlgListaTransportistas(myParentFrame,"",true);
         dlgLista.setVisible(true);
           //GRABA DATOS CABECERA ENTREGA
           try {

               if (FarmaVariables.vAceptar) {
                   //VariablesRecepCiega.vNro_Recepcion =  DBInventario.creaRecepEntrega();
                   //JMIRANDA 18.03.2010 comentado para utilizar la Recepción
                   int CantGuias = 0; //JMIRANDA 09.02.10
                   for (int i = 0; i < tblListaGuias.getRowCount(); i++) {
                       boolean check =
                           ((Boolean)tblListaGuias.getValueAt(i, 0)).booleanValue();
                       if (check) {
                           cargarGuiaSeleccionada(i);
                           log.debug("CARGA_GUIA,VariablesInventario.vNumEntAfectar: " +
                                              VariablesInventario.vNumEntAfectar);

                           DBInventario.actualizaPagina();

                           //JMIRANDA 09.02.10
                           //Cambio para asociar las entregas en la tabla lgt_recep_entrega solo si no esta activado la RecepCiega
                           DBInventario.asociaRecepEntrega(VariablesRecepCiega.vTran_NroRecepcion);
                           CantGuias += 1;
                       }
                   }
                   //Actualiza Cantidad de Guías Afectadas por entrega
                   log.debug("CantGuias: " + CantGuias);
                   int vCantGuias = DBRecepCiega.getCantGuias(VariablesRecepCiega.vTran_NroRecepcion);
                   DBInventario.actualizaCantGuias(vCantGuias+CantGuias,VariablesRecepCiega.vTran_NroRecepcion.trim());

                   FarmaUtility.aceptarTransaccion();
                   cargaListaGuias();
                   tblListaGuias.repaint();
                   FarmaUtility.showMessage(this,
                                            "La página se afectó correctamente",
                                            txtBuscar);

               } else {
                   //FarmaUtility.showMessage(this,"Ingrese Datos en la Cabecera.",txtBuscar);
                   log.debug("Cerro ventana ");
                }
            }
            catch (SQLException e) {
                FarmaUtility.liberarTransaccion();
                log.error("",e);
                    FarmaUtility.showMessage(this,"Ocurrió un error al afectar la página : \n"+ e.getMessage(), txtBuscar);
            }
     }

     */
    //JQUISPE 15.04.2010 CAMBIOS SIN RECECPION CIEGA

    private void asociaCabecera() {
        VariablesRecepCiega.vIndAsociaTransportista = true;
        DlgListaTransportistas dlgLista = new DlgListaTransportistas(myParentFrame, "", true);
        dlgLista.setVisible(true);
        //GRABA DATOS CABECERA ENTREGA
        try {

            if (FarmaVariables.vAceptar) {
                //VariablesRecepCiega.vNro_Recepcion =  DBInventario.creaRecepEntrega();
                //JMIRANDA 18.03.2010 comentado para utilizar la Recepción
                int CantGuias = 0; //JMIRANDA 09.02.10
                for (int i = 0; i < tblListaGuias.getRowCount(); i++) {
                    boolean check = ((Boolean)tblListaGuias.getValueAt(i, 0)).booleanValue();
                    if (check) {
                        cargarGuiaSeleccionada(i);
                        log.debug("CARGA_GUIA,VariablesInventario.vNumEntAfectar: " +
                                  VariablesInventario.vNumEntAfectar);

                        DBInventario.actualizaPagina();

                        //JMIRANDA 09.02.10
                        //Cambio para asociar las entregas en la tabla lgt_recep_entrega solo si no esta activado la RecepCiega
                        DBInventario.asociaRecepEntrega(VariablesRecepCiega.vTran_NroRecepcion);
                        CantGuias += 1;
                    }
                }
                //Actualiza Cantidad de Guías Afectadas por entrega
                log.debug("CantGuias: " + CantGuias);
                int vCantGuias = DBRecepCiega.getCantGuias(VariablesRecepCiega.vTran_NroRecepcion);
                DBInventario.actualizaCantGuias(vCantGuias + CantGuias, VariablesRecepCiega.vTran_NroRecepcion.trim());

                FarmaUtility.aceptarTransaccion();
                cargaListaGuias();
                tblListaGuias.repaint();
                FarmaUtility.showMessage(this, "La página se afectó correctamente", txtBuscar);

            } else {
                //FarmaUtility.showMessage(this,"Ingrese Datos en la Cabecera.",txtBuscar);
                log.debug("Cerro ventana ");
            }
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            if (sql.getErrorCode() == 20010) {
                FarmaUtility.showMessage(this, sql.getMessage().substring(11, sql.getMessage().indexOf("ORA-06512")),
                                         txtBuscar);
            } else {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrió un error al afectar la página : \n" +
                        sql.getMessage(), txtBuscar);
            }

        }
    }
}
