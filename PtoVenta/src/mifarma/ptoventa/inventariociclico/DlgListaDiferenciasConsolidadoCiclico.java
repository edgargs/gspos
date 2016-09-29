package mifarma.ptoventa.inventariociclico;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventariociclico.reference.ConstantsInvCiclico;
import mifarma.ptoventa.inventariociclico.reference.DBInvCiclico;
import mifarma.ptoventa.inventariociclico.reference.VariablesInvCiclico;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.reportes.DlgOrdenar;
import mifarma.ptoventa.reportes.reference.VariablesReporte;
import mifarma.ptoventa.ventas.DlgFiltroProductos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaDiferenciasConsolidadoCiclico extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaDiferenciasConsolidadoCiclico.class);

    FarmaTableModel tableModelDiferenciasConsolidadoCiclico;
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
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF6 = new JLabelFunction();
    private JLabelWhite lblLab = new JLabelWhite();
    private JLabelFunction lblF7 = new JLabelFunction();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelWhite lbldiferencia = new JLabelWhite();
    private JButtonLabel lblTotal = new JButtonLabel();
    private JLabelFunction lblF8 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaDiferenciasConsolidadoCiclico() {
        this(null, "", false);
    }

    public DlgListaDiferenciasConsolidadoCiclico(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(787, 428));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Diferencias de Productos");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanelHeader1.setBounds(new Rectangle(10, 10, 765, 30));
        jPanelHeader1.setLayout(null);
        jPanelTitle1.setBounds(new Rectangle(10, 45, 765, 25));
        jPanelTitle1.setLayout(null);
        jScrollPane1.setBounds(new Rectangle(10, 70, 765, 260));
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        tblRelacionDiferenciasProductos.getTableHeader().setReorderingAllowed(false);
        tblRelacionDiferenciasProductos.getTableHeader().setResizingAllowed(false);        
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
        txtProductos.addFocusListener(new FocusAdapter() {
        public void focusLost(FocusEvent e) {
                txtProductos_focusLost(e);
            }
        });
        lblEscape.setBounds(new Rectangle(665, 365, 110, 20));
        lblEscape.setText("[ ESCAPE ] Cerrar");
        lblF5.setBounds(new Rectangle(0, 365, 110, 20));
        lblF5.setText("[ F5 ] Filtrar");
        lblF6.setBounds(new Rectangle(125, 365, 110, 20));
        lblF6.setText("[ F6 ] Quitar Filtro");
        lblLab.setBounds(new Rectangle(435, 0, 305, 25));
        lblF7.setBounds(new Rectangle(245, 365, 110, 20));
        lblF7.setText("[ F7 ] Ordenar");
        jPanelTitle2.setBounds(new Rectangle(10, 330, 765, 25));
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
        jPanelTitle2.add(lbldiferencia, null);
        jPanelTitle2.add(lblTotal, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(jPanelTitle2, null);
        jContentPane.add(lblF7, null);
        jContentPane.add(lblF6, null);
        jContentPane.add(lblF5, null);
        jScrollPane1.getViewport().add(tblRelacionDiferenciasProductos, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblEscape, null);
        jPanelTitle1.add(lblLab, null);
        jPanelTitle1.add(btnRelacionProductos, null);
        jPanelHeader1.add(txtProductos, null);
        jPanelHeader1.add(btnProductos, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        /*if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) && FarmaVariables.vEconoFar_Matriz )
     lblF8.setVisible(true);*/
        initTableListaDiferenciasConsolidado();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaDiferenciasConsolidado() {
        
        if(UtilityPtoVenta.isLocalVentaMayorista()){
            tableModelDiferenciasConsolidadoCiclico =
                    new FarmaTableModel(ConstantsInvCiclico.columnsListaDiferenciasConsolidadoLote,
                                        ConstantsInvCiclico.defaultValuesListaDiferenciasConsolidadoLote, 0);
            FarmaUtility.initSimpleList(tblRelacionDiferenciasProductos, tableModelDiferenciasConsolidadoCiclico,
                                        ConstantsInvCiclico.columnsListaDiferenciasConsolidadoLote);
            cargaListaDiferenciasConsolidadoLote();
        }else{
            tableModelDiferenciasConsolidadoCiclico =
                    new FarmaTableModel(ConstantsInvCiclico.columnsListaDiferenciasConsolidado,
                                        ConstantsInvCiclico.defaultValuesListaDiferenciasConsolidado, 0);
            FarmaUtility.initSimpleList(tblRelacionDiferenciasProductos, tableModelDiferenciasConsolidadoCiclico,
                                        ConstantsInvCiclico.columnsListaDiferenciasConsolidado);
            cargaListaDiferenciasConsolidado();  
        }
        
        
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProductos);
    }

    private void txtProductos_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionDiferenciasProductos, txtProductos, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblRelacionDiferenciasProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblRelacionDiferenciasProductos, txtProductos.getText().trim(), 0,
                                                    1))) {
                    FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                             txtProductos);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            cargaListaFiltro();
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            if (VariablesPtoVenta.vInd_Filtro.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_N;
                tableModelDiferenciasConsolidadoCiclico.clearTable();
                cargaListaDiferenciasConsolidado();
                FarmaUtility.moveFocus(txtProductos);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            ordenarFiltro();
        } /*else if(e.getKeyCode() == KeyEvent.VK_F8)
    {
      if(lblF8.isVisible())
      {
        int[] ancho = { 30,30,30,30,30,30,30 };
        FarmaUtility.saveFile(myParentFrame, ConstantsInvCiclico.columnsListaDiferenciasConsolidado, tblRelacionDiferenciasProductos, ancho);
      }
    }*/
    }

    private void btnProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProductos);
    }

    private void txtProductos_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblRelacionDiferenciasProductos, txtProductos, 1);
        mostrarLaboratorio();
    }
    
    private void txtProductos_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtProductos);
    }    
    

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaDiferenciasConsolidado() {
        try {
            if(UtilityPtoVenta.isLocalVentaMayorista()){
                DBInvCiclico.listaDiferenciasConsolidadoLote(tableModelDiferenciasConsolidadoCiclico,
                                                         VariablesInvCiclico.vSecToma);
                if (tblRelacionDiferenciasProductos.getRowCount() > 0)
                    FarmaUtility.ordenar(tblRelacionDiferenciasProductos, tableModelDiferenciasConsolidadoCiclico, 1,
                                         FarmaConstants.ORDEN_ASCENDENTE);
                lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRelacionDiferenciasProductos,
                                                                                      6)));            
            }else{
                DBInvCiclico.listaDiferenciasConsolidado(tableModelDiferenciasConsolidadoCiclico,
                                                         VariablesInvCiclico.vSecToma);
                if (tblRelacionDiferenciasProductos.getRowCount() > 0)
                    FarmaUtility.ordenar(tblRelacionDiferenciasProductos, tableModelDiferenciasConsolidadoCiclico, 1,
                                         FarmaConstants.ORDEN_ASCENDENTE);
                lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRelacionDiferenciasProductos,
                                                                                      5)));            

            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener la lista de diferencias :\n" +
                    sql.getMessage(), txtProductos);
        }
    }

    private void cargaListaDiferenciasConsolidadoLote() {
        try {
            DBInvCiclico.listaDiferenciasConsolidadoLote(tableModelDiferenciasConsolidadoCiclico,
                                                     VariablesInvCiclico.vSecToma);
            if (tblRelacionDiferenciasProductos.getRowCount() > 0)
                FarmaUtility.ordenar(tblRelacionDiferenciasProductos, tableModelDiferenciasConsolidadoCiclico, 1,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRelacionDiferenciasProductos,
                                                                                  6)));
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener la lista de diferencias :\n" +
                    sql.getMessage(), txtProductos);
        }
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblRelacionDiferenciasProductos);
    }

    private void cargaListaFiltro() {
        DlgFiltroProductos dlgFiltroProductos = new DlgFiltroProductos(myParentFrame, "", true);
        dlgFiltroProductos.setVisible(true);
        if (FarmaVariables.vAceptar) {
            tableModelDiferenciasConsolidadoCiclico.clearTable();
            filtrarTablaProductos();
            FarmaVariables.vAceptar = false;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
        }
    }

    private void filtrarTablaProductos() {
        try {
            tableModelDiferenciasConsolidadoCiclico.clearTable();
            if (VariablesPtoVenta.vTipoFiltro.equalsIgnoreCase(ConstantsInvCiclico.TIPO_PRINCIPIO_ACTIVO)) {
                cargaListaDiferenciasConsolidado();
            } else if (VariablesPtoVenta.vTipoFiltro.equalsIgnoreCase(ConstantsInvCiclico.TIPO_ACCION_TERAPEUTICA)) {
                cargaListaDiferenciasConsolidado();
            } else if (VariablesPtoVenta.vTipoFiltro.equalsIgnoreCase(ConstantsInvCiclico.TIPO_LABORATORIO)) {
                if(UtilityPtoVenta.isLocalVentaMayorista()){
                    DBInvCiclico.listaDiferenciasConsolidadoFiltroLote(tableModelDiferenciasConsolidadoCiclico,
                                                                   VariablesInvCiclico.vSecToma);
                    FarmaUtility.ordenar(tblRelacionDiferenciasProductos, tableModelDiferenciasConsolidadoCiclico, 1,
                                         FarmaConstants.ORDEN_ASCENDENTE);
                    lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRelacionDiferenciasProductos,
                                                                                          6)));
                
                }else{
                    DBInvCiclico.listaDiferenciasConsolidadoFiltro(tableModelDiferenciasConsolidadoCiclico,
                                                                   VariablesInvCiclico.vSecToma);
                    FarmaUtility.ordenar(tblRelacionDiferenciasProductos, tableModelDiferenciasConsolidadoCiclico, 1,
                                         FarmaConstants.ORDEN_ASCENDENTE);
                    lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRelacionDiferenciasProductos,
                                                                                          5)));
                }
               
            }
            if (tblRelacionDiferenciasProductos.getRowCount() <= 0) {
                log.debug(VariablesPtoVenta.vCodFiltro);
                if (VariablesPtoVenta.vTipoFiltro.equalsIgnoreCase(ConstantsInvCiclico.TIPO_LABORATORIO)) {
                    lblLab.setText("");
                    FarmaUtility.showMessage(this, "No se encontro informacion para el Filtro", txtProductos);
                }
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al filtrar la lista de productos : \n" +
                    sql.getMessage(), txtProductos);
        }
    }

    private void mostrarLaboratorio() {
        String laboratorio;
        int total = tblRelacionDiferenciasProductos.getRowCount();
        if (total > 0) {            
                        if(UtilityPtoVenta.isLocalVentaMayorista()){ 
                            laboratorio =
                                    ((String)tblRelacionDiferenciasProductos.getValueAt(tblRelacionDiferenciasProductos.getSelectedRow(),
                                                                                        7)).trim();
                        }else{
                            laboratorio =
                                    ((String)tblRelacionDiferenciasProductos.getValueAt(tblRelacionDiferenciasProductos.getSelectedRow(),
                                                                                        6)).trim();
                        }                   
            lblLab.setText(laboratorio);

        } else {
            laboratorio = "";
        }
    }

    private void tblRelacionDiferenciasProductos_keyReleased(KeyEvent e) {
    }

    private void ordenarFiltro() {
        if (tblRelacionDiferenciasProductos.getRowCount() > 0) {
            DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
            // los datos de abajo son variables y constantes y tienen q crearlos respectivamente
            VariablesReporte.vNombreInHashtable = ConstantsInvCiclico.vNombreInHashtableDiferencias;
            if(UtilityPtoVenta.isLocalVentaMayorista()){
                FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable,
                                               ConstantsInvCiclico.vCodDiferenciaLote,
                                               ConstantsInvCiclico.vDescCampoDiferenciaLote, true); 
            }else{
                FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable,
                                               ConstantsInvCiclico.vCodDiferencia,
                                               ConstantsInvCiclico.vDescCampoDiferencia, true);            
            
            }
            
            
            dlgOrdenar.setVisible(true);
            if (FarmaVariables.vAceptar) {
                FarmaUtility.ordenar(tblRelacionDiferenciasProductos, tableModelDiferenciasConsolidadoCiclico,
                                     VariablesReporte.vCampo, VariablesReporte.vOrden);
                tblRelacionDiferenciasProductos.repaint();
                FarmaVariables.vAceptar = false;
            }
        }
    }

}
