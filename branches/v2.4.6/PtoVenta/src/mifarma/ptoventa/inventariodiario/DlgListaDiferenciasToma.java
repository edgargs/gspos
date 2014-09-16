package mifarma.ptoventa.inventariodiario;

import com.gs.mifarma.componentes.*;

import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.*;
import mifarma.common.*;
import mifarma.ptoventa.*;
import mifarma.ptoventa.inventariodiario.reference.ConstantsInvDiario;
import mifarma.ptoventa.inventariodiario.reference.VariablesInvDiario;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.tomainventario.reference.*;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.inventariodiario.reference.DBInvDiario;
import mifarma.ptoventa.ventas.reference.VariablesVentas;
import mifarma.ptoventa.ventas.DlgResumenPedido;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgListaDiferenciasToma extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgResumenPedido.class);
    FarmaTableModel tableModelDiferenciasConsolidado;
    Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblRelacionDiferenciasProductos = new JTable();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JButtonLabel btnProductos = new JButtonLabel();
    private JTextFieldSanSerif txtProductos = new JTextFieldSanSerif();
    private JLabelFunction lblEscape = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF6 = new JLabelFunction();
    private JLabelWhite lblLab = new JLabelWhite();
    private JLabelFunction lblF7 = new JLabelFunction();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelWhite lbldiferencia = new JLabelWhite();
    private JButtonLabel lblTotal = new JButtonLabel();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private boolean todos=false;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaDiferenciasToma() {
        this(null, "", false);
    }

    public DlgListaDiferenciasToma(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }
    }
    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(776, 419));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Diferencias de Productos");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jPanelHeader1.setBounds(new Rectangle(15, 10, 745, 30));
        jPanelHeader1.setLayout(null);
        jPanelTitle1.setBounds(new Rectangle(15, 45, 745, 25));
        jPanelTitle1.setLayout(null);
        jScrollPane1.setBounds(new Rectangle(15, 70, 745, 260));
        tblRelacionDiferenciasProductos.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        tblRelacionDiferenciasProductos_keyReleased(e);
                    }
                });
        btnRelacionProductos.setText("Relacion de Diferencias de Productos");
        btnRelacionProductos.setBounds(new Rectangle(10, 0, 215, 25));
        btnRelacionProductos.setMnemonic('r');
        btnRelacionProductos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRelacionProductos_actionPerformed(e);
                    }
                });
        btnProductos.setText("Productos :");
        btnProductos.setMnemonic('p');
        btnProductos.setBounds(new Rectangle(30, 5, 65, 20));
        btnProductos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnProductos_actionPerformed(e);
                    }
                });
        txtProductos.setBounds(new Rectangle(115, 5, 305, 20));
        txtProductos.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtProductos_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtProductos_keyReleased(e);
                    }
                });
        lblEscape.setBounds(new Rectangle(650, 365, 110, 20));
        lblEscape.setText("[ ESCAPE ] Cerrar");        
        lblEnter.setBounds(new Rectangle(15, 365, 130, 20));
        lblEnter.setText("[ ENTER ] Seleccionar");        
        lblF5.setBounds(new Rectangle(150, 365, 80, 20));
        lblF5.setText("[ F5 ] Todos");
        lblF6.setBounds(new Rectangle(235, 365, 85, 20));
        lblF6.setText("[ F6 ] Ajustar");
        lblLab.setBounds(new Rectangle(435, 0, 305, 25));
        lblF7.setBounds(new Rectangle(325, 365, 95, 20));
        lblF7.setText("[ F7 ] Revertir");
        jPanelTitle2.setBounds(new Rectangle(15, 330, 745, 25));
        jPanelTitle2.setLayout(null);
        lbldiferencia.setBounds(new Rectangle(415, 0, 125, 25));
        lbldiferencia.setText("Total en Diferencia :");
        lblTotal.setBounds(new Rectangle(560, 0, 95, 25));
        lblTotal.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRelacionProductos_actionPerformed(e);
                    }
                });
        lblF8.setText("[ F8 ] Exportar a Excel");
        lblF8.setBounds(new Rectangle(405, 365, 170, 20));
        lblF8.setVisible(false);
        jLabelFunction1.setBounds(new Rectangle(555, 365, 90, 20));
        jLabelFunction1.setText("[ F12 ] Imprimir");
        jLabelFunction2.setBounds(new Rectangle(425, 365, 125, 20));
        jLabelFunction2.setText("[ F8 ] Generar Boleta");
        jScrollPane1.getViewport().add(tblRelacionDiferenciasProductos, null);
        jPanelTitle2.add(lbldiferencia, null);
        jPanelTitle2.add(lblTotal, null);
        jContentPane.add(jLabelFunction2, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(jPanelTitle2, null);
        jContentPane.add(lblF7, null);
        jContentPane.add(lblF6, null);
        jContentPane.add(jScrollPane1, null);
        jPanelTitle1.add(lblLab, null);
        jPanelTitle1.add(btnRelacionProductos, null);
        jContentPane.add(jPanelTitle1, null);
        jPanelHeader1.add(txtProductos, null);
        jPanelHeader1.add(btnProductos, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblEscape, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblF5, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) && 
            FarmaVariables.vEconoFar_Matriz)
            lblF8.setVisible(true);
        initTableListaDiferenciasConsolidado();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaDiferenciasConsolidado() {
        tableModelDiferenciasConsolidado = 
                new FarmaTableModel(ConstantsInvDiario.columnsListaDiferenciasConsolidado, 
                                    ConstantsInvDiario.defaultValuesListaDiferenciasConsolidado, 
                                    0);
        
        FarmaUtility.initSelectList(tblRelacionDiferenciasProductos, 
                                    tableModelDiferenciasConsolidado, 
                                    ConstantsInvDiario.columnsListaDiferenciasConsolidado);                
        
        cargaListaDiferenciasConsolidado();
    }

    // **************************************************************************
    // Metodos de eventos   
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProductos);
        VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas = new ArrayList();
    }

    private void txtProductos_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, 
                                              tblRelacionDiferenciasProductos, 
                                              txtProductos, 2);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            seleccionaProducto();
        }
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            limipiarVariables();
            this.setVisible(false);            
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            log.debug("TODOS 1: "+todos);
            todos=!todos;
            log.debug("TODOS 2: "+todos);
            seleccionarTodosCupones(todos);
            VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.clear();
            int row = 0;
            for (int i = 0; i < tblRelacionDiferenciasProductos.getRowCount(); i++)
            {
                Boolean valor = (Boolean)(tblRelacionDiferenciasProductos.getValueAt(i,0));
                ArrayList vProductoSeleccionado = new ArrayList();
                
                /*
                vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,1));//COD_PROD
                vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,9));//numero toma
                vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,10));
                vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,11));
                vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,12));
                vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,13));
                */

                vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,1));//COD_PROD
                vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,10));//numero toma
                vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,11));
                vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,12));
                vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,13));
                vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,14));

                /*row = tblRelacionDiferenciasProductos.getSelectedRow();
                if (row > -1) */
                vProductoSeleccionado.add(FarmaUtility.getValueFieldJTable(tblRelacionDiferenciasProductos,i,6));

                FarmaUtility.operaListaProd(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, vProductoSeleccionado, valor, 0);
                log.debug("Lista Dif Seleccionada: "+ VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas);
                                
            }
            
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            VariablesInvDiario.vAccion="A";
            
            if(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.size()>0){
              ajustarProd();
             
            }else{
              FarmaUtility.showMessage(this,"Debe selecionar productos para realizar el ajuste.!!!", txtProductos);
            }
             
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            //Funcion de Revertir
            VariablesInvDiario.vAccion="R";
            fReverir();
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            fGeneraPedidoDescuentoPersonal();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            if (tblRelacionDiferenciasProductos.getRowCount() > 0)
                imprimir();
            else
                FarmaUtility.showMessage(this, 
                                         "No existen registros para imprimir.", 
                                         txtProductos);
        }
    }

    private void btnProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProductos);
    }

    private void txtProductos_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblRelacionDiferenciasProductos, 
                                         txtProductos, 2);
        
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaDiferenciasConsolidado()
    {
        try
        {
            DBInvDiario.listaDiferenciasDiario(tableModelDiferenciasConsolidado);
            if (tblRelacionDiferenciasProductos.getRowCount() > 0)
                FarmaUtility.ordenar(tblRelacionDiferenciasProductos, 
                                     tableModelDiferenciasConsolidado, 2, 
                                     FarmaConstants.ORDEN_ASCENDENTE);
            lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRelacionDiferenciasProductos, 
                                                                                  6)));
        }
        catch(SQLException sql)
        {
            log.error("",sql);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener la lista de diferencias :\n" +
                    sql.getMessage(), txtProductos);
        }
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblRelacionDiferenciasProductos);
    }

    private void seleccionarTodosCupones(boolean valor)
    {
      for (int i = 0; i < tblRelacionDiferenciasProductos.getRowCount(); i++)
      {
        tblRelacionDiferenciasProductos.setValueAt(new Boolean(valor), i, 0);
      }
      tblRelacionDiferenciasProductos.repaint();
    }

    private void filtrarTablaProductos() {
        try {
            tableModelDiferenciasConsolidado.clearTable();
            if (VariablesPtoVenta.vTipoFiltro.equalsIgnoreCase(ConstantsTomaInv.TIPO_PRINCIPIO_ACTIVO)) {
                cargaListaDiferenciasConsolidado();
            } else if (VariablesPtoVenta.vTipoFiltro.equalsIgnoreCase(ConstantsTomaInv.TIPO_ACCION_TERAPEUTICA)) {
                cargaListaDiferenciasConsolidado();
            } else if (VariablesPtoVenta.vTipoFiltro.equalsIgnoreCase(ConstantsTomaInv.TIPO_LABORATORIO)) {
                DBTomaInv.listaDiferenciasConsolidadoFiltro(tableModelDiferenciasConsolidado, 
                                                            VariablesTomaInv.vSecToma);
                FarmaUtility.ordenar(tblRelacionDiferenciasProductos, 
                                     tableModelDiferenciasConsolidado, 1, 
                                     FarmaConstants.ORDEN_ASCENDENTE);
                lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRelacionDiferenciasProductos, 
                                                                                      5)));
            }
            if (tblRelacionDiferenciasProductos.getRowCount() <= 0) {
                log.debug(VariablesPtoVenta.vCodFiltro);
                if (VariablesPtoVenta.vTipoFiltro.equalsIgnoreCase(ConstantsTomaInv.TIPO_LABORATORIO)) {
                    lblLab.setText("");
                    FarmaUtility.showMessage(this, 
                                             "No se encontro informacion para el Filtro", 
                                             txtProductos);
                }
                /* if(VariablesPtoVenta.vTipoFiltro.equalsIgnoreCase(ConstantsTomaInv.TIPO_PRINCIPIO_ACTIVO)){
          lblLab.setText("");
          FarmaUtility.showMessage(this,"No se encontro informacion para el Filtro\nRecuerde el filtro es solo por laboratorios",txtProductos);
        } else if (VariablesPtoVenta.vTipoFiltro.equalsIgnoreCase(ConstantsTomaInv.TIPO_ACCION_TERAPEUTICA)){
          lblLab.setText("");
          FarmaUtility.showMessage(this,"No se encontro informacion para el Filtro\nRecuerde el filtro es solo por laboratorios",txtProductos);
        } else-*/
            }
        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this, 
                                     "Error al filtrar la lista de productos : \n" +
                    sql.getMessage(), txtProductos);
        }
    }

 /*   private void mostrarLaboratorio() {
        String laboratorio;
        int total = tblRelacionDiferenciasProductos.getRowCount();
        if (total > 0) {
            laboratorio = 
                    ((String)tblRelacionDiferenciasProductos.getValueAt(tblRelacionDiferenciasProductos.getSelectedRow(), 
                                                                        6)).trim();
            lblLab.setText(laboratorio);

        } else {
            laboratorio = "";
        }
    }*/

    private void tblRelacionDiferenciasProductos_keyReleased(KeyEvent e) {
    }

   
    private void imprimir() {
        log.debug("Imprimir");
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                            "¿Está seguro que desea imprimir?"))
            return;
        //String vImp = "\\\\10.11.1.53\\COMPROBANTES";
        String vImp = FarmaVariables.vImprReporte;
        FarmaPrintService vPrint = new FarmaPrintService(66, vImp, true);

        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, 
                                     "No se pudo inicializar el proceso de impresión", 
                                     txtProductos);
            return;
        }
        String fechaActual = "";
        try {
            fechaActual = 
                    FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        } catch (SQLException e) {
            log.debug("Error al obtener fecha");
        }
        vPrint.setStartHeader();
        vPrint.activateCondensed();
        vPrint.printBold(FarmaPRNUtility.llenarBlancos(49) + 
                         " REPORTE DE DIFERENCIAS DE PRODUCTOS ", true);
        vPrint.printBlankLine(1);
        vPrint.printBold(FarmaPRNUtility.alinearIzquierda("Compañia: ", 10) + 
                         FarmaPRNUtility.alinearIzquierda(FarmaVariables.vNomCia, 
                                                          30), true);
        vPrint.printBold(FarmaPRNUtility.alinearIzquierda("Local:   ", 10) + 
                         FarmaPRNUtility.alinearIzquierda(FarmaVariables.vCodLocal + 
                                                          " - " + 
                                                          FarmaVariables.vDescLocal, 
                                                          40), true);
        vPrint.printBold(FarmaPRNUtility.alinearIzquierda("Fecha: ", 10) + 
                         FarmaPRNUtility.alinearIzquierda(fechaActual, 10), 
                         true);
        vPrint.printBlankLine(1);

        /*CABECERA*/
        vPrint.printLine("=======================================================================================================================================", 
                         true);
        vPrint.printBold("Codigo  Descripción                              Unid. Presentación   Stock Actual    Diferencia          Precio  Laboratorio", 
                         true);
        vPrint.printLine("=======================================================================================================================================", 
                         true);
        vPrint.deactivateCondensed();
        vPrint.setEndHeader();

        /*DETALLE*/
        String vCodigo = "", vDesc = "", vUnidPres = "", vStkActual = 
            "", vDif = "", vPrecio = "", vLab = "";
        for (int i = 0; i < tblRelacionDiferenciasProductos.getRowCount(); 
             i++) {
            vCodigo = (String)tblRelacionDiferenciasProductos.getValueAt(i, 1);
            vDesc = (String)tblRelacionDiferenciasProductos.getValueAt(i, 2);
            vUnidPres = 
                    (String)tblRelacionDiferenciasProductos.getValueAt(i, 3);
            vStkActual = 
                    (String)tblRelacionDiferenciasProductos.getValueAt(i, 4);
            vDif = (String)tblRelacionDiferenciasProductos.getValueAt(i, 5);
            vPrecio = (String)tblRelacionDiferenciasProductos.getValueAt(i, 6);
            vLab = (String)tblRelacionDiferenciasProductos.getValueAt(i, 7);

            vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda(vCodigo, 
                                                                   7) + " " + 
                                  FarmaPRNUtility.alinearIzquierda(vDesc, 40) + 
                                  " " + 
                                  FarmaPRNUtility.alinearIzquierda(vUnidPres, 
                                                                   21) + " " + 
                                  FarmaPRNUtility.alinearDerecha(vStkActual.trim(), 
                                                                 13) + " " + 
                                  FarmaPRNUtility.alinearDerecha(vDif.trim(), 
                                                                 13) + "  " + 
                                  FarmaPRNUtility.alinearDerecha(vPrecio, 12) + 
                                  "  " + 
                                  FarmaPRNUtility.alinearIzquierda(vLab, 20), 
                                  true);

        }
        vPrint.activateCondensed();
        vPrint.printLine("=======================================================================================================================================", 
                         true);

        vPrint.printBold(FarmaPRNUtility.alinearDerecha("Total en Diferencia: ", 
                                                        101) + 
                         lblTotal.getText(), true);
        vPrint.printBold("Registros Impresos: " + 
                         FarmaUtility.formatNumber(tblRelacionDiferenciasProductos.getRowCount(), 
                                                   ",##0") + 
                         FarmaPRNUtility.llenarBlancos(11), false);
    }

    /**
     * Metodo para Revertir
     * @author DUBILLUZ
     * @since  10.06.2009
     */
    private void fReverir()
    {
        if (VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.size() > 
            0) {
            DlgListaMotivoRevertir dlgListaMotivo = 
                new DlgListaMotivoRevertir(myParentFrame, "", true);
            dlgListaMotivo.setVisible(true);
            if (FarmaVariables.vAceptar) {
                FarmaVariables.vAceptar = false;
                /*
                TILP.SEC_TOMA_INV  || 'Ã' ||  -- 9
                VK.SEC_KARDEX      || 'Ã' ||  -- 10
                VK.CANT_MOV_PROD   || 'Ã' ||  -- 11
                VK.VAL_FRACC_PROD    -- 12
                 * */
                String pCodigoProd = "",pCodigoToma = "",pSecKardex="",pCantMov="",pValFracProd="";
                try {
                    for (int i = 0; 
                         i < VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.size(); 
                         i++) {
                        pCodigoProd = 
                                FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, 
                                                                    i, 0);
                        pCodigoToma = 
                                FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, 
                                                                    i, 1);
                        pSecKardex = 
                                FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, 
                                                                    i, 2);
                        pCantMov = 
                                FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, 
                                                                    i, 3);
                        pValFracProd = 
                                FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, 
                                                                    i, 4);
                        
                        DBInvDiario.pRevertirProducto(pCodigoProd, pCodigoToma, 
                                                      VariablesInvDiario.vCodMotivoRevertir,
                                                      pSecKardex,pCantMov,pValFracProd);
                    }
                    VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas = new ArrayList();
                    
                    FarmaUtility.aceptarTransaccion();
                    cargaListaDiferenciasConsolidado();
                    FarmaUtility.showMessage(this, 
                                             "Se ha revertido correctamente.",
                                             txtProductos);
                }catch (SQLException sql) {
                    FarmaUtility.liberarTransaccion();
                    log.error("",sql);
                    FarmaUtility.showMessage(this, 
                                             "Error al revertir.\n" +
                                             sql.getMessage(),
                                             txtProductos);
                }
            }
        } else {
            FarmaUtility.showMessage(this, 
                                     "Debe de tener algun producto seleccionado.\nVerifique!!.",
                                     txtProductos);
        }
    }

    private void seleccionaProducto()
    {
        String codigo ="";
    for (int k = 0; k < tblRelacionDiferenciasProductos.getRowCount(); k++) {
      codigo = ((String)tblRelacionDiferenciasProductos.getValueAt(k,1)).trim();
      if (codigo.equalsIgnoreCase(txtProductos.getText())) {
        FarmaGridUtils.showCell(tblRelacionDiferenciasProductos,k,0);
          //vEjecutaAccionTeclaListado = false;
        break;
      }
    }  
        
      int vFila = tblRelacionDiferenciasProductos.getSelectedRow();
      FarmaUtility.setCheckValue(tblRelacionDiferenciasProductos,false);
      Boolean valor = (Boolean)(tblRelacionDiferenciasProductos.getValueAt(vFila,0));
      ArrayList vProductoSeleccionado = new ArrayList();
        /*
        TILP.SEC_TOMA_INV  || 'Ã' ||  -- 9
        VK.SEC_KARDEX      || 'Ã' ||  -- 10
        VK.CANT_MOV_PROD   || 'Ã' ||  -- 11
        VK.VAL_FRACC_PROD  || 'Ã' ||  -- 12
         VK.COD_MOT_KARDEX    -- 13
         * */
      /*
      vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,vFila,1));//COD_PROD
      vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,vFila,9));//numero toma
      vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,vFila,10));//numero toma
      vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,vFila,11));//numero toma
      vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,vFila,12));//numero toma
      vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,vFila,13));//numero toma
      */
      
      vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,vFila,1));//COD_PROD
      vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,vFila,10));//numero toma
      vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,vFila,11));//numero toma
      vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,vFila,12));//numero toma
      vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,vFila,13));//numero toma
      vProductoSeleccionado.add(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,vFila,14));//numero toma
      
       
        int row = tblRelacionDiferenciasProductos.getSelectedRow();
        if (row > -1) 
        vProductoSeleccionado.add(FarmaUtility.getValueFieldJTable(tblRelacionDiferenciasProductos,row,6));
        
      //log.debug("Lista Dif Seleccionada: "+ tableModelDiferenciasConsolidado.data);
        //JMIRANDA 25.09.09 Limpia el array VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas si
        //escapa de DlgIngresoTrabajadores
        if (!VariablesInvDiario.vIndSeleccionado){
            VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.clear();
            VariablesInvDiario.vIndSeleccionado = true;
        }
      FarmaUtility.operaListaProd(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, vProductoSeleccionado, valor, 0);
      log.debug("Lista Dif Seleccionada: "+ VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas);
    }
    
 
       
public void fGeneraPedidoDescuentoPersonal(){
        // Genera el pedido para descuento a Personal.
        if(validacionBoleta()) {
            if(generaPedidoTemporal()) {
                DlgPedidoDiario dlgPedido = 
                    new DlgPedidoDiario(myParentFrame, "", true);
                dlgPedido.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas  = new ArrayList();
                    FarmaVariables.vAceptar = false;
                    cargaListaDiferenciasConsolidado();
                    
                    log.info("Pedido Generado: " + VariablesInvDiario.vSecPedidoTemporal);
                    FarmaUtility.showMessage(this,"Se generó el pedido con éxito.\nVerificar que existe papel en la impresora.!!!", txtProductos);
                    try{
                        imprimirBoleteo(VariablesInvDiario.vSecPedidoTemporal);
                        limipiarVariables();
                    }catch(Exception e){
                       log.info("ERROR IMPRESION:::");
                     } 
                    
                    VariablesInvDiario.vSecPedidoTemporal= "";
                }
            }
        }
    }
    
    public boolean generaPedidoTemporal(){
        boolean pValidacion = true;
        try {
            String pNumPedido = DBInvDiario.getGeneraTemporalPedidoInvDiario();
            String pSecDetalle = "", pCodProd = "", pCantMov = "", pValFrac = 
                "",pSecToma="",pSecKardex="",pCodMotivo="";
            for (int i = 0; 
                 i < VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.size(); 
                 i++) {
                pSecDetalle = "" + (i+1);
                pCodProd = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas,i,0);
                pSecToma = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas,i,1);
                pSecKardex = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas,i,2);
                pCantMov = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas,i,3);
                pValFrac = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas,i,4);
                pCodMotivo = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas,i,5);

                DBInvDiario.agregaProdDetalleTemporal(pNumPedido, pSecDetalle, 
                                                      pCodProd, pCantMov, 
                                                      pValFrac,
                                                      pSecToma,pSecKardex,pCodMotivo);
                
            }
            
            VariablesInvDiario.vSecPedidoTemporal = pNumPedido.trim();
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
                FarmaUtility.liberarTransaccion();
                log.error("",e);
                FarmaUtility.showMessage(this,"Error al generar el temporal del pedido.\n"+
                                              e.getMessage(),txtProductos);
        }

        return pValidacion;
    }
    
    /**
     * Metodo que muestra solo los seleccionados.
     * @author dubilluz
     * @since  18.06.2009
     */
    public void verSeleccionados(){
      /* if(VariablesInvDiario.vListadoProductosDiferenciaOriginal.)
       VariablesInvDiario.vListadoProductosDiferenciaOriginal = (ArrayList)tableModelDiferenciasConsolidado.data.clone();
       boolean pSeleccion = false;
       for(int i=0;i<tableModelDiferenciasConsolidado.data.size();i++){
           pSeleccion = (Boolean)FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,0);
           if(!pSeleccion){
               tableModelDiferenciasConsolidado.data.remove(i);
           }
       }
        tblRelacionDiferenciasProductos.repaint();
        if (tableModelDiferenciasConsolidado.getRowCount() > 0) {
          FarmaGridUtils.showCell(tblRelacionDiferenciasProductos, 0,0);
        }
    */
    }
    
    public boolean validacionBoleta(){
        boolean pValidacion = true;
        double pCant = 0;
        String pCodProd = "";
        
        if(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.size()>0){            
            //Se valida que los montos solo sean Negativos
            for(int i=0;i<VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.size();i++){
                pCant = FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas,i,3));
                if(pCant>0){
                    pCodProd = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas,i,0);
                    pValidacion = false;
                    break;
                }   
            }
            
            if(!pValidacion){
                for(int i=0;i<tableModelDiferenciasConsolidado.data.size();i++){
                    if(pCodProd.trim().equalsIgnoreCase(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasConsolidado.data,i,1).trim())){
                        FarmaGridUtils.showCell(tblRelacionDiferenciasProductos, i, 0);
                        break;
                    }
                        
                }
                FarmaUtility.showMessage(this,"Debe de seleccionar solo productos \ncon diferencias negativas.",txtProductos);
            }
        }
        else
            pValidacion = false;
        
        return pValidacion;
    }
    
    
    /**
     * Ajuste de productos
     * @author JCORTEZ
     * @since  15.06.09
     */
    private void ajustarProd()
    {
        if (VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.size() > 0) {
        
            log.debug("VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas -->"+
                                            VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas);
            obetenerTotal();
            DlgListaMotivoRevertir dlgListaMotivo = new DlgListaMotivoRevertir(myParentFrame, "", true);
            dlgListaMotivo.setVisible(true);
            
            if (FarmaVariables.vAceptar) {
            
                //si el monto de los productos es 0  no existe descuento a trabajadores.
                if(VariablesInvDiario.dTotalProds<0){
                    DlgIngresoTrabajadores dlgIngresoTrab = new DlgIngresoTrabajadores(myParentFrame, "", true);
                    dlgIngresoTrab.setVisible(true);
                    
                    if (FarmaVariables.vAceptar) {
                        log.info("Trabajadores a Descontar: " +VariablesInvDiario.vListaTrabParaDescuento);
                        //crear ajuste 
                        if(agregarAjuste()){
                          realizarAjuste();
                          //verificar hoja em impresora
                            FarmaUtility.showMessage(this,"Verificar que existe papel en la impresora.!!!", txtProductos);
                            try{
                              imprimirAjuste();
                                limipiarVariables();
                            }catch(Exception e){
                                log.info("ERROR IMPRESION:::");
                            } 
                        }else{
                            log.info("AJUSTE ERROR:::::");
                        }
                    }else {
                        for (int i = 0; i < tableModelDiferenciasConsolidado.getRowCount(); i++)
                        {
                          tableModelDiferenciasConsolidado.setValueAt(new Boolean(false), i, 0);
                        }
                        tblRelacionDiferenciasProductos.repaint();
                    }
                }else{
                    realizarAjuste();//solo se realiza el ajuste
                    limipiarVariables();
                }
                //cargaListaDiferenciasConsolidado();
            }
        } else {
            FarmaUtility.showMessage(this,"Debe de tener algun producto seleccionado.\nVerifique!!.",txtProductos);
        }
    }
    
    
    /**
     * Se realiza proceso de ajuste, asi se ingrese o no montos por trabajador
     * @AUTHOR JCORTEZ
     * @SINCE 18.06.09
     * */
    private void realizarAjuste(){
    
        FarmaVariables.vAceptar = false;
        
            /*
            TILP.SEC_TOMA_INV  || 'Ã' ||  -- 9
            VK.SEC_KARDEX      || 'Ã' ||  -- 10
            VK.CANT_MOV_PROD   || 'Ã' ||  -- 11
            VK.VAL_FRACC_PROD   || 'Ã' ||  -- 12
            VK.COD_MOT_KARDEX    -- 13
             * */
        String pCodigoProd = "",pCodigoToma = "",pSecKardex="",pCantMov="",pValFracProd="",pCodMotKardex="";
        log.debug("VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas -->"+
                                        VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas);
        try {
            for (int i = 0; i < VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.size();i++) {
                pCodigoProd = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, i, 0);
                pCodigoToma = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, i, 1);
                pSecKardex = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, i, 2);
                pCantMov = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, i, 3);
                pValFracProd = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, i, 4);
                pCodMotKardex = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, i, 5);
                
                DBInvDiario.ajustarProd(pCodigoProd, pCodigoToma,VariablesInvDiario.vCodMotivoAjuste,
                                              pSecKardex,pCantMov,pValFracProd,pCodMotKardex,VariablesInvDiario.vCodAjuste);
            }
            VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas = new ArrayList();
            
            FarmaSearch.updateNumera("072");
            FarmaUtility.aceptarTransaccion();
            cargaListaDiferenciasConsolidado();
            VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas = new ArrayList();  
            FarmaUtility.showMessage(this, "Se ha realizado el ajuste correctamente.",txtProductos);
            //limipiarVariables();
        }catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas = new ArrayList();  
            log.error("",sql);
            FarmaUtility.showMessage(this, "Error al revertir.\n" +sql.getMessage(),txtProductos);
        }
        
        log.info("AJUSTE EXITOSO");
    
    
    }
    
    /**
     * Se muestra ingreso de trabajadores
     * */
    private void agregarTrabMonto(){
    
       /* DlgIngresoTrabajadores dlgIngresoTrab = new DlgIngresoTrabajadores(myParentFrame, "", true);
        dlgIngresoTrab.setVisible(true);
        
        if (FarmaVariables.vAceptar) {
            log.info("Trabajadores a Descontar: " +VariablesInvDiario.vListaTrabParaDescuento);
            if(VariablesInvDiario.vListaTrabParaDescuento.size()>0){
            agregarAjuste();
            //ajustarProd();
            }else
                FarmaUtility.showMessage(this,"No se realizo el ajuste",txtProductos);
        }*/
    
    }
    
    /**
     * Se guarda LGT_AJUSTE_TOMA_INV_DIA_LOC/ LGT_AJUSTE_PROD /LGT_AJUSTE_TRAB
     * @author JCORTEZ
     * @since 16.06.09
     * */
    private boolean agregarAjuste(){
        
        String CodAjust="",pCodigoToma="",pCodigoProd="",codTrabRRHH="",codTrab="",Monto="";
        try
           {
               CodAjust=FarmaSearch.getNuSecNumeracion("072", 10);
               VariablesInvDiario.vCodAjuste=CodAjust;
               
            DBInvDiario.grabarAjuste(VariablesInvDiario.vCodMotivoAjuste,CodAjust); //genera ajuste
             
             for (int i = 0; i < VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.size();i++) {
                 pCodigoToma = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas,i,1);
                 pCodigoProd = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas, i, 0);
                 DBInvDiario.grabarAjusteProd(CodAjust,pCodigoToma,pCodigoProd); //ajuste por producto
             }
                  
               for (int a = 0; a < VariablesInvDiario.vListaTrabParaDescuento.size();a++) {
                   codTrabRRHH = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListaTrabParaDescuento,a,1);
                   Monto = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListaTrabParaDescuento, a, 3);
                   codTrab = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListaTrabParaDescuento, a, 4);
                   DBInvDiario.grabarAjusteTrab(CodAjust,codTrabRRHH,codTrab,Monto); //ajuste por trabajador
               }
                
            log.debug("SE GRABO NUEVO AJUSTE : "+CodAjust);
             // FarmaSearch.updateNumera("072");
             // FarmaUtility.aceptarTransaccion();
               return true;
           }catch (SQLException sql)
           {
             FarmaUtility.liberarTransaccion();
             log.error("",sql);
             FarmaUtility.showMessage(this,"Ocurrio un error al generar ajuste.\n"+sql.getMessage(),txtProductos);
               return false;
           }
    
    }
    
    /**
     * Obtien monto de productos con diferencia
     * @AUTHOR JCORTEZ
     * @SINCE 17.06.09
     * */
    private void obetenerTotal(){
    
        double total=0.00;
        double montoprod=0.00;
        if(VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.size()>0){
            
            for (int a = 0; a < VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas.size();a++) {
              montoprod=FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(
                            VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas,a,6));
                total=total+montoprod;
            }
            
           
          VariablesInvDiario.dTotalProds=total;
            log.debug("Monto total pre : "+VariablesInvDiario.dTotalProds);
          VariablesInvDiario.dTotalProds = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesInvDiario.dTotalProds,2));
          log.debug("Monto total de productos : "+VariablesInvDiario.dTotalProds);
            total=0.00;
        }
        
    }
    
    
    /***
     * Se imprime productos con diferencia
     * */
    private  void imprimirAjuste() throws Exception{
        log.debug("Imprimir resultado ajuste");
        
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro que desea imprimir?"))
            return;
            
        //String vImp = "\\\\10.11.1.53\\COMPROBANTES";
        String vImp = FarmaVariables.vImprReporte;
        FarmaPrintService vPrint = new FarmaPrintService(24, vImp, false);
        
        String fechaActual = "";
        try {
            fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        } catch (SQLException e) {
            log.debug("Error al obtener fecha");
        }
        
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión",txtProductos);
        
        }else{
        
        
        try{
        
        log.debug("Inicio Impresion....");
        //vPrint.setStartHeader();
        vPrint.activateCondensed();
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + " REPORTE DE DIFERENCIAS DE PRODUCTOS ", true);
        vPrint.printLine(" ",true);
        //vPrint.printBlankLine(1);
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda("Compañia: ", 10) + FarmaPRNUtility.alinearIzquierda(FarmaVariables.vNomCia, 
                                                          30), true);
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda("Local:   ", 10) + FarmaPRNUtility.alinearIzquierda(FarmaVariables.vCodLocal +
                                " - " + FarmaVariables.vDescLocal, 40), true);
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda("Fecha: ", 10) + FarmaPRNUtility.alinearIzquierda(fechaActual, 10)
                   +"       "+ "  Motivo : " +VariablesInvDiario.vDescMotivoAjuste.trim()
                +"      "+" Importe : "+ VariablesInvDiario.dTotalProds, true);
        //vPrint.printBlankLine(1);
         vPrint.printLine(" ",true);
        
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda("El trabajador, declara conocer y aceptar el descuento respectivo que se efectura en su renumeracion",100), true);
        //vPrint.printBlankLine(1);
         vPrint.printLine(" ",true);

        /*CABECERA*/
        vPrint.printLine("========================================================================================================================",true);
        vPrint.printLine("CodTrab   Nombres y Apellidos                               Importe             Firma           Huella Digital", true);
        vPrint.printLine("========================================================================================================================",true);
        //vPrint.deactivateCondensed();
        //vPrint.setEndHeader();

        /*DETALLE*/
        String CodTrab = "",DescTrab="",Importe="";
        for (int i = 0; i < VariablesInvDiario.vListaTrabParaDescuento.size(); i++) {
        
            CodTrab = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListaTrabParaDescuento, i, 1);
            DescTrab = FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListaTrabParaDescuento, i, 2);
            Importe =FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListaTrabParaDescuento, i, 3);
            vPrint.printLine(FarmaPRNUtility.alinearIzquierda(CodTrab.trim(),9) + " " + 
                                  FarmaPRNUtility.alinearIzquierda(DescTrab.trim(), 50) + " " + 
                                  FarmaPRNUtility.alinearIzquierda(Importe.trim(),15) + " " + 
                                  FarmaPRNUtility.alinearDerecha("           ", 15) + " " + 
                                  FarmaPRNUtility.alinearDerecha("           ", 15),true);
            vPrint.printLine(" ",true);
            vPrint.printLine(" ",true);
            vPrint.printLine(" ",true);
            vPrint.printLine(" ",true);
            vPrint.printLine("----------------------------------------------------------------------------------------------------------------------", true);

        }
        //vPrint.activateCondensed();
        vPrint.printLine("========================================================================================================================", true);
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.llenarBlancos(51)+" Total:  "+ VariablesInvDiario.dTotalProds, 100), true);
        vPrint.printLine("========================================================================================================================", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda("--------------------------"+"                                                "+"-----------------------------------", 160), true);
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda("      Firma Auditor "+"                                                "+"        Firma Sello del Jefe Local", 160), true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.deactivateCondensed();
        vPrint.endPrintService();
        
        /*vPrint.printBold(FarmaPRNUtility.alinearDerecha(FarmaPRNUtility.llenarBlancos(50)+"--------------------------", 150), true);
        vPrint.printBold(FarmaPRNUtility.alinearDerecha(FarmaPRNUtility.llenarBlancos(50)+"  Firma Sello del Jefe Local ", 150), true);
        
        vPrint.printBold("Registros Impresos: " + FarmaUtility.formatNumber(tblRelacionDiferenciasProductos.getRowCount(),",##0") + 
                         FarmaPRNUtility.llenarBlancos(11), false);*/
     } catch(Exception e){
                log.debug(":::::::::ERROR DE IMPRESION REPORTE::::::::::::.");
            } 
    }
  }
    public void limipiarVariables(){
        VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas = new ArrayList();
        VariablesInvDiario.vCodMotivoRevertir = "";
        VariablesInvDiario.vDNI = "";
        VariablesInvDiario.vCodigoTrab = "";
        VariablesInvDiario.vCodRRHH = "";
        VariablesInvDiario.vNombreCompleto = "";
        VariablesInvDiario.vMontoIngresado = "";
        VariablesInvDiario.vListaTrabSeleccionados = new ArrayList();
        VariablesInvDiario.vListaTrabParaDescuento = new ArrayList();
        VariablesInvDiario.vSecPedidoTemporal = "";
        VariablesInvDiario.vCodMotivoAjuste = "";
        VariablesInvDiario.vAccion = "";        
    }
    

    
    /***
     * Se imprime productos con diferencia
     * */
    private  void imprimirBoleteo(String pSecPedidoTemporal) throws Exception{

        log.debug("Imprimir pedido generado en boleteo");
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                            "¿Está seguro que desea imprimir?"))
            return;
        String vImp = FarmaVariables.vImprReporte;
        FarmaPrintService vPrint = new FarmaPrintService(24, vImp, false);
        String fechaActual = "", pDescMotivo = "FALTANTE", totalPedido = "";
        ArrayList vListaTrabDcto = new ArrayList();

        try {
            fechaActual = 
                    FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            //pDescMotivo = 
            //totalPedido = Neto del Pedido
            //vListaTrabDcto Obtiene Datos de Trabajadores
            totalPedido =     DBInvDiario.obtieneNetoPedido(pSecPedidoTemporal);
            DBInvDiario.obtieneTrabDescuento(vListaTrabDcto,pSecPedidoTemporal);            
        } catch (SQLException e) {
            log.debug("Error al obtener fecha");
        }

        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, 
                                     "No se pudo inicializar el proceso de impresión", 
                                     txtProductos);
        } else {

            try {

                log.debug("Inicio Impresion....");
                vPrint.activateCondensed();
                vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + 
                                 " REPORTE DE DIFERENCIAS DE PRODUCTOS ", 
                                 true);
                vPrint.printLine(" ", true);
                //vPrint.printBlankLine(1);
                vPrint.printLine(FarmaPRNUtility.alinearIzquierda("Compañia: ", 
                                                                  10) + 
                                 FarmaPRNUtility.alinearIzquierda(FarmaVariables.vNomCia, 
                                                                  30), true);
                vPrint.printLine(FarmaPRNUtility.alinearIzquierda("Local:   ", 
                                                                  10) + 
                                 FarmaPRNUtility.alinearIzquierda(FarmaVariables.vCodLocal + 
                                                                  " - " + 
                                                                  FarmaVariables.vDescLocal, 
                                                                  40), true);
                vPrint.printLine(FarmaPRNUtility.alinearIzquierda("Fecha: ", 
                                                                  10) + 
                                 FarmaPRNUtility.alinearIzquierda(fechaActual, 
                                                                  10) + 
                                 "       " + "  Motivo : " + 
                                 pDescMotivo.trim() + "      " + 
                                 " Importe : " + totalPedido, true);
                //vPrint.printBlankLine(1);
                vPrint.printLine(" ", true);

                vPrint.printLine(FarmaPRNUtility.alinearIzquierda("El trabajador, declara conocer y aceptar el descuento respectivo que se efectura en su renumeracion", 
                                                                  100), true);
                //vPrint.printBlankLine(1);
                vPrint.printLine(" ", true);

                /*CABECERA*/
                vPrint.printLine("========================================================================================================================", 
                                 true);
                vPrint.printLine("CodTrab   Nombres y Apellidos                               Importe             Firma           Huella Digital", 
                                 true);
                vPrint.printLine("========================================================================================================================", 
                                 true);
                //vPrint.deactivateCondensed();
                //vPrint.setEndHeader();

                /*DETALLE*/
                String CodTrab = "", DescTrab = "", Importe = "";
                for (int i = 0; i < vListaTrabDcto.size(); i++) {

                    CodTrab = 
                            FarmaUtility.getValueFieldArrayList(vListaTrabDcto, 
                                                                i, 0);
                    DescTrab = 
                            FarmaUtility.getValueFieldArrayList(vListaTrabDcto, 
                                                                i, 1);
                    Importe = 
                            FarmaUtility.getValueFieldArrayList(vListaTrabDcto, 
                                                                i, 2);
                    vPrint.printLine(FarmaPRNUtility.alinearIzquierda(CodTrab.trim(), 
                                                                      9) + 
                                     " " + 
                                     FarmaPRNUtility.alinearIzquierda(DescTrab.trim(), 
                                                                      50) + 
                                     " " + 
                                     FarmaPRNUtility.alinearIzquierda(Importe.trim(), 
                                                                      15) + 
                                     " " + 
                                     FarmaPRNUtility.alinearDerecha("           ", 
                                                                    15) + " " + 
                                     FarmaPRNUtility.alinearDerecha("           ", 
                                                                    15), true);
                    vPrint.printLine(" ", true);
                    vPrint.printLine(" ", true);
                    vPrint.printLine(" ", true);
                    vPrint.printLine(" ", true);
                    vPrint.printLine("----------------------------------------------------------------------------------------------------------------------", true);
                }
                //vPrint.activateCondensed();
                vPrint.printLine("========================================================================================================================", 
                                 true);
                vPrint.printLine(FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.llenarBlancos(51) + 
                                                                  " Total:  " + 
                                                                  totalPedido, 
                                                                  100), true);
                vPrint.printLine("========================================================================================================================", 
                                 true);
                vPrint.printLine(" ", true);
                vPrint.printLine(" ", true);
                vPrint.printLine(" ", true);
                vPrint.printLine(" ", true);
                vPrint.printLine(FarmaPRNUtility.alinearIzquierda("--------------------------" + 
                                                                  "                                                " + 
                                                                  "-----------------------------------", 
                                                                  160), true);
                vPrint.printLine(FarmaPRNUtility.alinearIzquierda("      Firma Auditor " + 
                                                                  "                                                " + 
                                                                  "        Firma Sello del Jefe Local", 
                                                                  160), true);
                vPrint.printLine(" ", true);
                vPrint.printLine(" ", true);
                vPrint.printLine(" ", true);
                vPrint.printLine(" ", true);
                vPrint.deactivateCondensed();
                vPrint.endPrintService();

            } catch (Exception e) {
                log.debug("Error Prueba Impresion ");
            }
        }

    }
    
    
}
