package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.ColumnGroup;
import com.gs.mifarma.componentes.GroupableTableHeader;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JMessageAlert;
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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgVerificacionConteo extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgVerificacionConteo.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelHeader pnlObservacion = new JPanelHeader();
    private JLabelWhite lblObservacion = new JLabelWhite();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JButtonLabel btnBuscar = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JLabel lblMensaje = new JLabel();

    private int COL_CODIGO = 0;
    private int COL_DESC_PROD = 1;
    private int COL_UNIDAD = 2;

    private int COL_LOTE = 4;
    private int COL_GUIA = 4;
    private int COL_RCP = 5;
    private int COL_CANT = 6;

    private int COL_CODIGO_2 = 8;
    private int COL_SEC_CONTEO = 9;    
    private int COL_FECHA_VENCIMIENTO = 11;
    private int COL_IND_LOTE_MAYORISTA = 12;
    
    private JScrollPane srcBultos = new JScrollPane();
    private JTable tblBultos = new JTable();
    private FarmaTableModel tableModelBultos;
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JLabelWhite lblDescProducto = new JLabelWhite();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgVerificacionConteo() {
        super();
        try {
            jbInit();
            initTable();
            initTableBultos();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgVerificacionConteo(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(800, 600));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Verificación de Conteo");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle.setBounds(new Rectangle(5, 60, 785, 20));
        btnRelacionProductos.setText("Relación de Códigos de Productos");
        btnRelacionProductos.setBounds(new Rectangle(10, 0, 335, 15));
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });
        btnRelacionProductos.setMnemonic('R');
        scrListaProductos.setBounds(new Rectangle(5, 80, 785, 345));
        tblListaProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductos_keyPressed(e);
            }
        });
        lblEnter.setBounds(new Rectangle(30, 525, 185, 20));
        lblEnter.setText("[ ENTER ] Ingreso Cantidad");
        lblF11.setBounds(new Rectangle(450, 525, 195, 20));
        lblF11.setText("[ F11 ] Finalizar Verificación");
        lblEsc.setBounds(new Rectangle(660, 525, 115, 20));
        lblEsc.setText("[ ESC ] Salir ");
        pnlObservacion.setBounds(new Rectangle(5, 5, 785, 55));
        lblObservacion.setText("Sr(a). Jefe de Local, Verifique la cantidad recibida de los siguientes productos");
        lblObservacion.setBounds(new Rectangle(10, 5, 490, 15));
        lblF5.setBounds(new Rectangle(235, 525, 115, 20));
        lblF5.setText("[ F5 ] Imprimir");
        lblF1.setBounds(new Rectangle(140, 330, 180, 20));
        lblF1.setText("[ F1 ] Completar con ceros");
        lblF1.setVisible(false);
        btnBuscar.setText("Buscar: ");
        btnBuscar.setMnemonic('B');
        btnBuscar.setBounds(new Rectangle(10, 30, 50, 15));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtBuscar.setBounds(new Rectangle(60, 25, 245, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        lblMensaje.setText("Las diferencias que se reportarán, forman parte de la pol\u00edtica de recepci\u00f3n de mercader\u00eda en locales.");
        lblMensaje.setBounds(new Rectangle(15, 500, 730, 20));
        lblMensaje.setFont(new Font("SansSerif", 1, 12));
        lblMensaje.setForeground(Color.red);
        srcBultos.setBounds(new Rectangle(5, 445, 785, 55));
        jPanelTitle1.setBounds(new Rectangle(5, 425, 785, 20));
        jButtonLabel1.setText("Bultos del producto:");
        jButtonLabel1.setBounds(new Rectangle(5, 0, 120, 20));
        lblDescProducto.setText("[PRODUCTO]");
        lblDescProducto.setBounds(new Rectangle(130, 0, 655, 20));
        srcBultos.getViewport().add(tblBultos, null);
        jPanelTitle1.add(lblDescProducto, null);
        jPanelTitle1.add(jButtonLabel1, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(srcBultos, null);
        jContentPane.add(lblMensaje, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF5, null);
        pnlObservacion.add(txtBuscar, null);
        pnlObservacion.add(btnBuscar, null);
        pnlObservacion.add(lblObservacion, null);
        jContentPane.add(pnlObservacion, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEnter, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        pnlTitle.add(btnRelacionProductos, null);
        jContentPane.add(pnlTitle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        FarmaUtility.centrarVentana(this);
        
        initTable();
        initTableBultos();
        setJTable(tblListaProductos, txtBuscar);
        
        cargaListaProductos();
        cargaListaBultos();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA)){
        if (UtilityPtoVenta.isLocalVentaMayorista()){
            tableModel =
                    new FarmaTableModel(ConstantsRecepCiega.columnsListaProductosSegundoConteoM, ConstantsRecepCiega.defaultcolumnsListaProductosSegundoConteoM,1);
            
            FarmaUtility.initSimpleList(tblListaProductos, tableModel,
                                        ConstantsRecepCiega.columnsListaProductosSegundoConteoM);
            COL_GUIA = COL_GUIA + 1;
            COL_RCP = COL_RCP + 1;
            COL_CANT = COL_CANT + 1;
            COL_CODIGO_2 = COL_CODIGO_2 + 1;
            COL_SEC_CONTEO = COL_SEC_CONTEO + 1;
            
        }else{
            tableModel =
                    new FarmaTableModel(ConstantsRecepCiega.columnsListaProductosSegundoConteo, ConstantsRecepCiega.defaultcolumnsListaProductosSegundoConteo,1);
            
            FarmaUtility.initSimpleList(tblListaProductos, tableModel,
                                        ConstantsRecepCiega.columnsListaProductosSegundoConteo);
        }
                                    
        
        
        //GroupColumn        
        TableColumnModel cm = tblListaProductos.getColumnModel();
        ColumnGroup g_other = new ColumnGroup("CANTIDAD");        
        g_other.add(cm.getColumn(COL_GUIA));
        g_other.add(cm.getColumn(COL_RCP));
        g_other.add(cm.getColumn(COL_CANT));
        
        GroupableTableHeader header = new GroupableTableHeader(cm);
        header.addColumnGroup(g_other);
        
        tblListaProductos.setTableHeader(header);        
        
    }
    
    private void initTableBultos(){
        tableModelBultos = new FarmaTableModel(ConstantsRecepCiega.columnsListaProductosSegundoConteoBultos, ConstantsRecepCiega.defaultcolumnsListaProductosSegundoConteoBultos,
                                    3);
        FarmaUtility.initSimpleList(tblBultos, tableModelBultos,
                                    ConstantsRecepCiega.columnsListaProductosSegundoConteoBultos);
        tblBultos.setTableHeader(null);
        tblBultos.repaint();
        lblDescProducto.setText("");
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtBuscar);
        boolean vRpta = false;
        if (alertaMaxDiferencias()) {
            vRpta = JConfirmDialog.rptaConfirmDialogDefaultNo(this, "La Recepción Actual tiene muchas diferencias.\n" +
                        "Verifique el Detalle de las Entregas por si faltan Asociar o si requiere Desasociar.\n" +
                        "Se Recomienda que verifique antes de ingresar las cantidades.\n" +
                        "\n" +
                        "¿Desea continuar con la verificación de todas maneras?");
            if (!vRpta) {
                if (UtilityRecepCiega.updateEstadoRecep(ConstantsRecepCiega.EstadoRevisado,
                                                        VariablesRecepCiega.vNro_Recepcion.trim(), this, txtBuscar)) {
                    FarmaUtility.aceptarTransaccion();
                    cerrarVentana(false);
                } else {
                    FarmaUtility.showMessage(this, "No se pudo modificar el estado en la Recepción.\n" +
                            "Vuelva a Intentarlo.\n", txtBuscar);
                }
            }
        }

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtBuscar);
        log.debug("cerra ventana");
    }

    private void tblListaProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (tblListaProductos.getRowCount() > 0) {

                int pFila = tblListaProductos.getSelectedRow();

                VariablesRecepCiega.vDesc_Producto =
                        FarmaUtility.getValueFieldArrayList(tableModel.data, pFila, COL_DESC_PROD).trim();
                VariablesRecepCiega.vUnidad =
                        FarmaUtility.getValueFieldArrayList(tableModel.data, pFila, COL_UNIDAD).trim();
                VariablesRecepCiega.vCodProd =
                        FarmaUtility.getValueFieldArrayList(tableModel.data, pFila, COL_CODIGO).trim();
                VariablesRecepCiega.vSecConteo =
                        FarmaUtility.getValueFieldArrayList(tableModel.data, pFila, COL_SEC_CONTEO).trim();
                //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
                //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA)){
                if (UtilityPtoVenta.isLocalVentaMayorista()){
                    VariablesRecepCiega.vLote = 
                            FarmaUtility.getValueFieldArrayList(tableModel.data, pFila, COL_LOTE).trim();
                    VariablesRecepCiega.vFechaVcto = 
                            FarmaUtility.getValueFieldArrayList(tableModel.data, pFila, COL_FECHA_VENCIMIENTO).trim();
                    VariablesRecepCiega.vLastIndLoteMayorista = 
                            FarmaUtility.getValueFieldArrayList(tableModel.data, pFila, COL_IND_LOTE_MAYORISTA).trim();
                }
                //ERIOS 2.3.3 Verifica sobrante
                verificaSobrante(pFila);

                DlgIngresoCantVerificaConteo vIngresoCantidad =
                    new DlgIngresoCantVerificaConteo(myParentFrame, "", true);
                vIngresoCantidad.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    tableModel.setValueAt(VariablesRecepCiega.vCantidadVerificaConteo, pFila, COL_CANT);
                    cargaListaProductos();
                    FarmaGridUtils.showCell(tblListaProductos, pFila, COL_DESC_PROD);
                    FarmaVariables.vAceptar = false;
                }
            }

        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            funcionF11();

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //COLOCAR IND_SEGUNDO_CONTEO N
            if (UtilityRecepCiega.updateEstadoRecep(ConstantsRecepCiega.EstadoRevisado,
                                                    VariablesRecepCiega.vNro_Recepcion.trim(), this, txtBuscar)) {
                FarmaUtility.aceptarTransaccion();
                log.info("Dio escape actualiza estado a Revisado");
                cerrarVentana(false);
            } else {
                FarmaUtility.showMessage(this, "No se pudo modificar el estado en la Recepción.\n" +
                        "Vuelva a Intentarlo.\n", txtBuscar);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (this.tableModel.getRowCount() > 0) {
                UtilityCaja.imprimeVoucherDiferencias(this);
                log.debug("Imprimio vouvher");
            }
        } /*else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            if (tableModel.getRowCount() > 0){
                log.debug("Completa con ceros");
                if (existenProductosACompletarConCeros() ){
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro de completar los registros no contados con ceros?"))
                    {
                        rellenarConCeros();

                    }
                }

            }

        }*/ //se comento a petición de Pedro Yovera
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private boolean existenProductosACompletarConCeros() {
        int numElementos = 1;
        int numBlancos = 0;
        boolean encontrado = false;

        while (numElementos <= tableModel.getRowCount() && !encontrado) {
            if (FarmaUtility.getValueFieldArrayList(tableModel.data, numElementos - 1,
                                                    COL_CANT).trim().equalsIgnoreCase("")) {
                numBlancos++;
                encontrado = true;
            }
            numElementos++;
        }
        if (numBlancos > 0) {
            return true;
        }
        return false;
    }

    private void rellenarConCeros() {
        try {
            for (int i = 0; i < this.tableModel.getRowCount(); i++) {
                String codProducto = FarmaUtility.getValueFieldArrayList(tableModel.data, i, COL_CODIGO_2).trim();
                String secProducto = FarmaUtility.getValueFieldArrayList(tableModel.data, i, COL_SEC_CONTEO).trim();
                DBRecepCiega.rellenaConCerosCantNoIngresada(codProducto, secProducto);
            }
            FarmaUtility.aceptarTransaccion();
            cargaListaProductos();
            FarmaUtility.showMessage(this, "La operación se realizó correctamente", txtBuscar);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al completar con ceros: \n" +
                    sql.getMessage(), txtBuscar);
        }
    }

    private void cargaListaProductos() {
        try {           
            DBRecepCiega.getListaProdVerificacionConteo(tableModel, VariablesRecepCiega.vNro_Recepcion);        
            
            if (tblListaProductos.getRowCount() > 0) {                
                FarmaUtility.ordenar(tblListaProductos, tableModel, COL_DESC_PROD, FarmaConstants.ORDEN_ASCENDENTE);
                tblListaProductos.repaint();
            }
            FarmaUtility.moveFocus(txtBuscar);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n", txtBuscar);
        }
      }

    private void mostrarListaGuiasPendientes() {
        DlgListaGuiasPendientes vListaGuiasPendientes = new DlgListaGuiasPendientes(myParentFrame, "", true);
        vListaGuiasPendientes.setVisible(true);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean alertaMaxDiferencias() {
        boolean flag = false;
        try {
            int maxProd = 0;
            maxProd = DBRecepCiega.getMaxProdVerificacion();
            log.debug("maxProd: " + maxProd);
            log.debug("tblListaProductos.getRowCount(): " + tblListaProductos.getRowCount());
            if (maxProd < tblListaProductos.getRowCount()) {
                flag = true;
            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al obetener indicador para Alerta de máximos Productos.", txtBuscar);
        }
        return flag;
    }

    private void funcionF11() {
        log.debug("Guarda cambios en segundo conteo");
        if (existenProductosACompletarConCeros()) {
            FarmaUtility.showMessage(this,
                                     "Existen productos que no han sido contados,\n para finalizar deberá completar con ceros los \n productos no contados\n",
                                     txtBuscar);
        } else {
            if (JConfirmDialog.rptaConfirmDialog(this,
                                                 "¿Está seguro de confirmar la cantidad ingresada " + "para cada producto?\n Si continúa ya no podrá modificar las cantidades." +
                                                 "\n\n Desea continuar con el proceso?")) {
                accionConfirmaVerificacion();
            }
        }
    }

    public void accionConfirmaVerificacion() {
        boolean verificaExisteGuiasPendientes = false;
        //de acuerdo a la cantidad contada de cada producto, se actualiza en la tabla LGT_NOTA_DET el campo CANT_RECEPCIONADA 
        //segun el algoritmo (tiene mas prioridad el que tiene menor cantidad en el campo CANT_ENVIADIA_MAtRIZ)
        try {

            //ERIOS 03.05.2016 Salida a Cuarentena
            if(UtilityPtoVenta.isLocalVentaMayorista()){
                DBRecepCiega.salidaCuarentena(VariablesRecepCiega.vNro_Recepcion);
            }
            
            DBRecepCiega.actualizaCantidadRecepPorEntrega();
            verificaExisteGuiasPendientes = DBRecepCiega.verificaExistenGuiasPendientes();

            VariablesRecepCiega.vAfectaSobranteNuevo = DBRecepCiega.getIndAfectaSobrantesFaltantesNuevo();

            if (verificaExisteGuiasPendientes) { // en cada existan guias que sus productos no han sido contado, o simplemente la cantidad contada no cubre algunas entregas

                if (VariablesRecepCiega.vAfectaSobranteNuevo.equalsIgnoreCase("S")) {
                    afectarEntregasPendientes();
                } else {
                    log.debug("Existen guias pendientes");
                    mostrarListaGuiasPendientes();
                    if (FarmaVariables.vAceptar) {
                        DBRecepCiega.eliminaGuiasPendienteDeRecepcion();
                        log.debug("entro a desasociar!");
                        FarmaVariables.vAceptar = false;
                    }
                }

            }

            if (VariablesRecepCiega.vAfectaSobranteNuevo.equalsIgnoreCase("S")) {
                afectarEntregasSobrantes();
            }

            log.debug("Afectar guias");
            DBRecepCiega.afectaProductosDeEntregas();

            DBRecepCiega.actualizaIndSegundoConteo();
            FarmaUtility.aceptarTransaccion();

            cerrarVentana(true);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al actualizar la cantidad recepcionada por entrega: \n",
                                     txtBuscar);
        }
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, COL_DESC_PROD);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        }
        chkKeyPressed(e);
    }

    private void setJTable(JTable pJTable, JTextFieldSanSerif pText) {
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, COL_CODIGO);
            FarmaUtility.setearActualRegistro(pJTable, pText, COL_DESC_PROD);
        }
        FarmaUtility.moveFocus(pText);
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        if(FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, COL_DESC_PROD) ||
                (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_PAGE_UP) ||
                (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN)){
            cargaListaBultos();        
        }
    }

    private void afectarEntregasPendientes() throws SQLException {
        DBRecepCiega.afectarEntregasPendientesBD();
    }

    private void afectarEntregasSobrantes() throws SQLException {
        DBRecepCiega.afectarEntregasSobrantesBD();
    }

    /**
     * Verifica cantidad sobrante
     * @author ERIOS
     * @since 2.3.3
     * @param i
     */
    private void verificaSobrante(int i) {
        String cantGuia = FarmaUtility.getValueFieldJTable(tblListaProductos, i, COL_GUIA);
        String cantRecep = FarmaUtility.getValueFieldJTable(tblListaProductos, i, COL_RCP);
        double dCantGuia = FarmaUtility.getDecimalNumber(cantGuia);
        double dCantRecep = FarmaUtility.getDecimalNumber(cantRecep);
        if (dCantRecep > dCantGuia) {
            JMessageAlert.showMessage(myParentFrame, "Recepcion Ciega", "¡¡¡ ALERTA !!!",
                    "\nUSTED PUEDE SER PARTE DE LA PRUEBA DE RECEPCION DE PRODUCTOS.\n" +
                    "\n" +
                    "RECUERDE: EL INCUMPLIMIENTO DE ESTA PRUEBA PUEDE TRAER SANCIONES,\n" +
                    "LAS CUALES UD. ASUMIRÁ!!!\n" +
                    "\n" +
                    "POR FAVOR VERIFIQUE FÍSICAMENTE LA CANTIDAD DE PRODUCTOS RECIBIDOS Y \n" +
                    "REGÍSTRELA CORRECTAMENTE EN EL SISTEMA.\n", "", false);
        }
    }
    
    public static void main(String[] args){
        DlgVerificacionConteo dlgVerificacionConteo = new DlgVerificacionConteo();
        dlgVerificacionConteo.setVisible(true);
    }

    /**
     * Muestra lista de bultos
     * @author ERIOS
     * @since 09.06.2015
     */
    private void cargaListaBultos() {
        lblDescProducto.setText("");
        tableModelBultos.clearTable();
        try {
            int pFila = tblListaProductos.getSelectedRow();
            String vCodProd =FarmaUtility.getValueFieldArrayList(tableModel.data, pFila, COL_CODIGO).trim();
            String vDescProducto =FarmaUtility.getValueFieldArrayList(tableModel.data, pFila, COL_DESC_PROD).trim();
            String vUnidad =FarmaUtility.getValueFieldArrayList(tableModel.data, pFila, COL_UNIDAD).trim();
            
            DBRecepCiega.getListaBultosProd(tableModelBultos, VariablesRecepCiega.vNro_Recepcion,vCodProd);
            lblDescProducto.setText(vCodProd+"-"+vDescProducto+" "+vUnidad);
        } catch (SQLException e) {
            log.error("",e);
        }
    }
}
