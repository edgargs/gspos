package mifarma.ptoventa.inventariodiario;

import com.gs.mifarma.componentes.*;

import java.awt.*;
import java.awt.event.*;

import java.sql.SQLException;

import javax.swing.*;

import mifarma.common.*;

import mifarma.ptoventa.*;
import mifarma.ptoventa.inventariodiario.reference.DBInvDiario;
import mifarma.ptoventa.inventariodiario.reference.VariablesInvDiario;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.reportes.*;
import mifarma.ptoventa.reportes.reference.*;
import mifarma.ptoventa.tomainventario.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgListaDiferenciasVer extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaDiferenciasVer.class); 

    FarmaTableModel tableModelDiferenciasConsolidado;
    Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblRelacionDiferenciasProductos = new JTable();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JButtonLabel btnProductos = new JButtonLabel();
    private JTextFieldSanSerif txtProductos = new JTextFieldSanSerif();
    private JLabelFunction lblEscape = new JLabelFunction();
    //private JLabelFunction lblF5 = new JLabelFunction();
    //private JLabelFunction lblF6 = new JLabelFunction();
    private JLabelWhite lblLab = new JLabelWhite();
    private JLabelFunction lblF7 = new JLabelFunction();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelWhite lbldiferencia = new JLabelWhite();
    private JButtonLabel lblTotal = new JButtonLabel();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaDiferenciasVer() {
        this(null, "", false);
    }

    public DlgListaDiferenciasVer(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(821, 379));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Diferencias de Productos");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                    public void windowClosing(WindowEvent e)
                    {
                      this_windowClosing(e);
                    }
                });
        jPanelHeader1.setBounds(new Rectangle(15, 10, 785, 30));
        jPanelHeader1.setLayout(null);
        jPanelTitle1.setBounds(new Rectangle(15, 45, 785, 25));
        jPanelTitle1.setLayout(null);
        jScrollPane1.setBounds(new Rectangle(15, 70, 785, 210));
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
        lblEscape.setBounds(new Rectangle(690, 325, 110, 20));
        lblEscape.setText("[ ESCAPE ] Cerrar");
        lblF3.setBounds(new Rectangle(250, 325, 100, 20));
        lblF3.setText("[ F3 ] Cruces");
        
        //lblF5.setBounds(new Rectangle(15, 365, 110, 20));
        //lblF5.setText("[ F5 ] Filtrar");
        //lblF6.setBounds(new Rectangle(135, 365, 110, 20));
        //lblF6.setText("[ F6 ] Quitar Filtro");
        lblLab.setBounds(new Rectangle(435, 0, 305, 25));
        lblF7.setBounds(new Rectangle(15, 325, 110, 20));
        lblF7.setText("[ F7 ] Ordenar");
        jPanelTitle2.setBounds(new Rectangle(15, 280, 785, 35));
        jPanelTitle2.setLayout(null);
        lbldiferencia.setBounds(new Rectangle(505, 5, 125, 20));
        lbldiferencia.setText("Total en Diferencia :");


        lblTotal.setBounds(new Rectangle(635, 5, 95, 25));
        lblTotal.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRelacionProductos_actionPerformed(e);
                    }
                });
        lblF8.setText("[ F8 ] Exportar a Excel");
        lblF8.setBounds(new Rectangle(405, 365, 170, 20));
        lblF8.setVisible(false);
        jLabelFunction1.setBounds(new Rectangle(130, 325, 115, 20));
        jLabelFunction1.setText("[ F12 ] Imprimir");
        jScrollPane1.getViewport().add(tblRelacionDiferenciasProductos, null);
        jPanelTitle2.add(lblTotal, null);
        jPanelTitle2.add(lbldiferencia, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(jPanelTitle2, null);
        //jContentPane.add(lblF6, null);
        //jContentPane.add(lblF5, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF7, null);
        jContentPane.add(jScrollPane1, null);
        jPanelTitle1.add(lblLab, null);
        jPanelTitle1.add(btnRelacionProductos, null);
        jContentPane.add(jPanelTitle1, null);
        jPanelHeader1.add(txtProductos, null);
        jPanelHeader1.add(btnProductos, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblEscape, null);
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
                new FarmaTableModel(ConstantsTomaInv.columnsListaDiferenciasConsolidadoDiario, 
                                    ConstantsTomaInv.defaultValuesListaDiferenciasConsolidadoDiario, 
                                    0);
        FarmaUtility.initSimpleList(tblRelacionDiferenciasProductos, 
                                    tableModelDiferenciasConsolidado, 
                                    ConstantsTomaInv.columnsListaDiferenciasConsolidadoDiario);
        cargaListaDiferenciasConsolidado();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProductos);
    }

    private void txtProductos_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, 
                                              tblRelacionDiferenciasProductos, 
                                              txtProductos, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblRelacionDiferenciasProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblRelacionDiferenciasProductos, 
                                                    txtProductos.getText().trim(), 
                                                    0, 1))) {
                    FarmaUtility.showMessage(this, 
                                             "Producto No Encontrado según Criterio de Búsqueda !!!", 
                                             txtProductos);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) 
    {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
        {
            VariablesInvDiario.vFlagTimer=true;
            //this.setVisible(false);        
            //JMIRANDA 03/08/09
            boolean rptaCerrar;
            rptaCerrar =  JConfirmDialog.rptaConfirmDialogDefaultNo(this,"Después de Cerrar la Ventana, NO PODRA "+
                          "REALIZAR MODIFICACIONES!\n" +
                          "¿Desea Cerrar de Todas Maneras?");
            log.debug("RptaCerrar: "+rptaCerrar);
            if (rptaCerrar == false)
               return;
            else{
                 cerrarVentana(rptaCerrar);
                }
        }
        else if (e.getKeyCode() == KeyEvent.VK_F3) 
        {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, 
                                         ConstantsPtoVenta.MENSAJE_MATRIZ, 
                                         txtProductos);
            else
                insertar(); 
        }
        else if (e.getKeyCode() == KeyEvent.VK_F5) 
        {
            cargaListaFiltro();
        } 
        else if (e.getKeyCode() == KeyEvent.VK_F6) 
        {
            if (VariablesPtoVenta.vInd_Filtro.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_N;
                tableModelDiferenciasConsolidado.clearTable();
                cargaListaDiferenciasConsolidado();
                FarmaUtility.moveFocus(txtProductos);
            }
        } 
        else if (e.getKeyCode() == KeyEvent.VK_F7) 
        {
            ordenarFiltro();
        }
        else if (e.getKeyCode() == KeyEvent.VK_F8) 
        {
            if (lblF8.isVisible()) {
                int[] ancho = { 30, 30, 30, 30, 30, 30, 30 };
                FarmaUtility.saveFile(myParentFrame, 
                                      ConstantsTomaInv.columnsListaDiferenciasConsolidadoDiario, 
                                      tblRelacionDiferenciasProductos, ancho);
            }
        } 
        else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) 
        {
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
                                         txtProductos, 1);
        mostrarLaboratorio();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    /**
     * Inserta nuevos productos a la toma.
     */
    private void insertar() {
        cargarRegistroSeleccionado(); 
        DlgProductosInvDiario dlgProductosInvDiario = new DlgProductosInvDiario(myParentFrame, "", true);
        dlgProductosInvDiario.setVisible(true);
        //if (FarmaVariables.vAceptar) {
        //    cargaListaDiferenciasConsolidado();
        //    FarmaVariables.vAceptar = false;
        //}
    }
    
    private void cargarRegistroSeleccionado() {
        
                VariablesInvDiario.vCodProd = tblRelacionDiferenciasProductos.getValueAt(tblRelacionDiferenciasProductos.getSelectedRow(), 0).toString().trim();
                VariablesInvDiario.vDesProd = tblRelacionDiferenciasProductos.getValueAt(tblRelacionDiferenciasProductos.getSelectedRow(), 1).toString().trim();
                VariablesInvDiario.vUnidPresentacion = tblRelacionDiferenciasProductos.getValueAt(tblRelacionDiferenciasProductos.getSelectedRow(), 2).toString().trim();                
                
                VariablesInvDiario.vStockProd = tblRelacionDiferenciasProductos.getValueAt(tblRelacionDiferenciasProductos.getSelectedRow(), 3).toString().trim();                
                log.debug(" stock de producto a cargar : " + VariablesInvDiario.vStockProd);
        
                VariablesInvDiario.vNomLab = tblRelacionDiferenciasProductos.getValueAt(tblRelacionDiferenciasProductos.getSelectedRow(), 6).toString().trim();                
                VariablesInvDiario.vValFrac =tblRelacionDiferenciasProductos.getValueAt(tblRelacionDiferenciasProductos.getSelectedRow(), 7).toString().trim();                
                            
    }
    
    private void cargaListaDiferenciasConsolidado() {
        try {
            DBTomaInv.listaDiferenciasConsolidadoDiario(tableModelDiferenciasConsolidado,VariablesInvDiario.vSecToma);
            if (tblRelacionDiferenciasProductos.getRowCount() > 0)
                FarmaUtility.ordenar(tblRelacionDiferenciasProductos, 
                                     tableModelDiferenciasConsolidado, 1, 
                                     FarmaConstants.ORDEN_ASCENDENTE);
            lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRelacionDiferenciasProductos,5)));
            
            //GUARDAR DATOS PARA LA CARGA DE PRODUCTOS QUE AUN TIENEN DIFERENCIA
             for (int p = 0; p < tblRelacionDiferenciasProductos.getRowCount(); p++) {                 
                
                 String prod = ((String)tblRelacionDiferenciasProductos.getValueAt(p, 0)).trim();
                 log.debug("producto a cargar : " + p + "  " + prod);
                 DBInvDiario.actualizaFlagCargaProducto(prod);
                 FarmaUtility.aceptarTransaccion();
             }            
            
        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener la lista de diferencias :\n" +
                    sql.getMessage(), txtProductos);
        }
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblRelacionDiferenciasProductos);
    }

    private void cargaListaFiltro() {
        DlgFiltroProductos dlgFiltroProductos = 
            new DlgFiltroProductos(myParentFrame, "", true);
        dlgFiltroProductos.setVisible(true);
        if (FarmaVariables.vAceptar) {
            tableModelDiferenciasConsolidado.clearTable();
            filtrarTablaProductos();
            FarmaVariables.vAceptar = false;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
        }
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

    private void mostrarLaboratorio() {
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
    }

    private void tblRelacionDiferenciasProductos_keyReleased(KeyEvent e) {
    }

    private void ordenarFiltro() {
        if (tblRelacionDiferenciasProductos.getRowCount() > 0) {
            DlgOrdenar dlgOrdenar = 
                new DlgOrdenar(myParentFrame, "Ordenar", true);
            // los datos de abajo son variables y constantes y tienen q crearlos respectivamente
            VariablesReporte.vNombreInHashtable = 
                    ConstantsTomaInv.vNombreInHashtableDiferencias;
            FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), 
                                           VariablesReporte.vNombreInHashtable, 
                                           ConstantsTomaInv.vCodDiferencia, 
                                           ConstantsTomaInv.vDescCampoDiferencia, 
                                           true);
            dlgOrdenar.setVisible(true);
            if (FarmaVariables.vAceptar) {
                FarmaUtility.ordenar(tblRelacionDiferenciasProductos, 
                                     tableModelDiferenciasConsolidado, 
                                     VariablesReporte.vCampo, 
                                     VariablesReporte.vOrden);
                tblRelacionDiferenciasProductos.repaint();
                FarmaVariables.vAceptar = false;
            }
        }
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
            vCodigo = (String)tblRelacionDiferenciasProductos.getValueAt(i, 0);
            vDesc = (String)tblRelacionDiferenciasProductos.getValueAt(i, 1);
            vUnidPres = 
                    (String)tblRelacionDiferenciasProductos.getValueAt(i, 2);
            vStkActual = 
                    (String)tblRelacionDiferenciasProductos.getValueAt(i, 3);
            vDif = (String)tblRelacionDiferenciasProductos.getValueAt(i, 4);
            vPrecio = (String)tblRelacionDiferenciasProductos.getValueAt(i, 5);
            vLab = (String)tblRelacionDiferenciasProductos.getValueAt(i, 6);

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

    private void this_windowClosing(WindowEvent e)
    {          
          boolean rptaCerrar;
          rptaCerrar =  JConfirmDialog.rptaConfirmDialogDefaultNo(this,"Después de Cerrar la Ventana, NO PODRA "+
                      "REALIZAR MODIFICACIONES!\n" +
                      "¿Desea Cerrar de Todas Maneras?");
          log.debug("RptaCerrar: "+rptaCerrar);
          if (rptaCerrar == false)
              return;
          else{
                  cerrarVentana(rptaCerrar);
          }        
    }
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }

}
