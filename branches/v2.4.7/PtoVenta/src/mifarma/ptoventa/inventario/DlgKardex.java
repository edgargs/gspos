package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
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

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgFiltroProductos;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.DlgStockLocales;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgKardex extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgKardex.class);

    Frame myParentFrame;    
    FarmaTableModel tableModel;    
    private BorderLayout borderLayout1 = new BorderLayout();    
    private JPanelWhite jContentPane = new JPanelWhite();    
    private JPanelHeader pnlHeader1 = new JPanelHeader();    
    private JPanelTitle pnlTitle1 = new JPanelTitle();    
    private JButtonLabel btnBuscar = new JButtonLabel();    
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();    
    private JLabelWhite lblLocal_T = new JLabelWhite();    
    private JLabelWhite lblLocal = new JLabelWhite();    
    private JScrollPane scrListaProductos = new JScrollPane();    
    private JTable tblListaProductos = new JTable();    
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    
    private JLabelFunction lblEsc = new JLabelFunction();    
    private JLabelFunction lblF7 = new JLabelFunction();    
    private JLabelFunction lblF6 = new JLabelFunction();    
    private JLabelFunction lblF1 = new JLabelFunction();    
    private JLabelFunction jLabelFunction5 = new JLabelFunction();    
    private JLabelFunction jLabelFunction6 = new JLabelFunction();    
    private JLabelFunction lblF2 = new JLabelFunction();    
    private JLabelFunction jLabelFunction8 = new JLabelFunction();
    
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    
    private JLabelWhite lblFiltro = new JLabelWhite();
    
    private JLabelWhite lblTotalRegistros_T = new JLabelWhite();
    
    private JLabelWhite lblTotalRegistros = new JLabelWhite();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    
    private String tipo_reposicion;
    // **************************************************************************
    // Constructores
    // **************************************************************************
    
    public DlgKardex() {
        this(null, "", false);
    }
    
    public DlgKardex(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("",e);
        }
    
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(800, 456));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Kardex - Ajuste");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
            }
        
        public void windowClosing(WindowEvent e)
        {
        this_windowClosing(e);
        }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 775, 35));
        pnlTitle1.setBounds(new Rectangle(10, 50, 775, 30));
        btnBuscar.setText("Buscar por:");
        btnBuscar.setBounds(new Rectangle(5, 5, 70, 20));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    btnBuscar_actionPerformed(e);
            }
        });
        txtBuscar.setBounds(new Rectangle(75, 5, 250, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
        txtBuscar_keyPressed(e);
            }
        
            public void keyReleased(KeyEvent e) {
        txtBuscar_keyReleased(e);
            }
        });
        lblLocal_T.setText("Local:");
        lblLocal_T.setBounds(new Rectangle(395, 0, 40, 30));
        lblLocal.setText("XXXXX");
        lblLocal.setBounds(new Rectangle(440, 5, 225, 20));
        scrListaProductos.setBounds(new Rectangle(10, 80, 775, 240));
        tblListaProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    tblListaProductos_keyPressed(e);
            }
        });
        pnlTitle2.setBounds(new Rectangle(10, 320, 775, 30));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(685, 390, 90, 20));
        lblF7.setText("[ F6 ] Eliminar Filtro");
        lblF7.setBounds(new Rectangle(490, 360, 120, 20));
        lblF6.setText("[ F5 ] Filtrar Productos");
        lblF6.setBounds(new Rectangle(345, 360, 140, 20));
        lblF1.setText("[ F2 ] Kardex");
        lblF1.setBounds(new Rectangle(15, 360, 75, 20));
        jLabelFunction5.setText("jLabelFunction1");
        jLabelFunction5.setBounds(new Rectangle(20, 325, 100, 20));
        jLabelFunction6.setText("jLabelFunction1");
        jLabelFunction6.setBounds(new Rectangle(110, 375, 100, 20));
        lblF2.setText("[ F3 ] Ajuste");
        lblF2.setBounds(new Rectangle(100, 360, 90, 20));
        jLabelFunction8.setText("jLabelFunction1");
        jLabelFunction8.setBounds(new Rectangle(110, 375, 100, 20));
        btnRelacionProductos.setText("Relación de Productos");
        btnRelacionProductos.setBounds(new Rectangle(5, 10, 140, 15));
        btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    btnRelacionProductos_actionPerformed(e);
            }
        });
        lblFiltro.setText("Todos los Laboratorios");
        lblFiltro.setBounds(new Rectangle(155, 10, 150, 15));
        lblTotalRegistros_T.setText("Total de Registros:");
        lblTotalRegistros_T.setBounds(new Rectangle(15, 5, 115, 20));
        lblTotalRegistros_T.setVisible(false);
        lblTotalRegistros.setText("100");
        lblTotalRegistros.setBounds(new Rectangle(150, 5, 70, 20));
        lblTotalRegistros.setVisible(false);
        lblF8.setText("[ F8 ] Excedente");
        lblF8.setBounds(new Rectangle(450, 390, 105, 20));
        lblF9.setText("[ F9 ] Excesos");
        lblF9.setBounds(new Rectangle(570, 390, 100, 20));
        lblF4.setBounds(new Rectangle(195, 360, 145, 20));
        lblF4.setText("[ F4 ] Ajuste Diferencias");
        lblF12.setText("[ F12 ]  Stock Locales");
        lblF12.setBounds(new Rectangle(295, 390, 135, 20));
        jPanelHeader1.setBounds(new Rectangle(0, 0, 465, 30));
        jPanelHeader1.setBackground(Color.white);
        jLabelOrange1.setText("LOS PRODUCTOS INACTIVOS SE MUESTRAN EN COLOR ROJO");
        jLabelOrange1.setBounds(new Rectangle(10, 0, 450, 30));
        jLabelOrange1.setForeground(Color.red);
        jLabelOrange1.setFont(new Font("DialogInput", 1, 15));
        scrListaProductos.getViewport().add(tblListaProductos, null);
        lblF2.add(jLabelFunction8, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblF2, null);
        lblF1.add(jLabelFunction6, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF6, null);
        jContentPane.add(lblF7, null);
        lblEsc.add(jLabelFunction5, null);
        jContentPane.add(lblEsc, null);
        pnlTitle2.add(lblTotalRegistros, null);
        pnlTitle2.add(lblTotalRegistros_T, null);
        jPanelHeader1.add(jLabelOrange1, null);
        pnlTitle2.add(jPanelHeader1, null);
        jContentPane.add(pnlTitle2, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle1.add(lblFiltro, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(pnlHeader1, null);
        pnlHeader1.add(lblLocal, null);
        pnlHeader1.add(lblLocal_T, null);
        pnlHeader1.add(txtBuscar, null);
        pnlHeader1.add(btnBuscar, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************
    
    private void initialize() {
            initTable();
    };
    
    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
            tableModel = new FarmaTableModel(
                            ConstantsInventario.columnsListaProductosAK,
                            ConstantsInventario.defaultValuesListaProductosAK, 0);
            FarmaUtility.initSimpleList(tblListaProductos, tableModel, ConstantsInventario.columnsListaProductosAK);
            cargaListaProductos(); 
    }
    
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************
    private void tblListaProductos_keyPressed(KeyEvent e) {
            chkKeyPressed(e);
    }
    
    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
            FarmaUtility.moveFocus(tblListaProductos);
    }
    
    private void this_windowOpened(WindowEvent e) {
            FarmaUtility.centrarVentana(this);
            FarmaUtility.moveFocus(txtBuscar);
            lblLocal.setText(FarmaVariables.vDescCortaLocal);
            log.debug("ROL USUARIO: "+FarmaVariables.vNuSecUsu);
            if(validarAsistAudit()){ //ASOSA 18.01.2010
                lblF2.setVisible(false);
                lblF4.setVisible(false);
            }
    }
    
    private void btnBuscar_actionPerformed(ActionEvent e) {
            FarmaUtility.moveFocus(txtBuscar);
    }
    
    private void txtBuscar_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar,
                        1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblListaProductos.getSelectedRow() >= 0) {
                String pCodBusque=txtBuscar.getText().trim();
                if (FarmaUtility.findTextInJTable(tblListaProductos,
                        pCodBusque, 0, 1)) {
                      //realiza busqueda por codigo
                } else if(!"000000".equalsIgnoreCase(codBarra())){
                    String temp = codBarra();
                    FarmaUtility.findTextInJTable(tblListaProductos,temp,0,1);//realiza busqueda por codigo de barra
                    txtBuscar.setText("");
                }else
                {   FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
                }
            }
        }
        chkKeyPressed(e);
    }
   private void this_windowClosing(WindowEvent e){
       FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
   }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 1);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************
    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            if (tieneRegistroSeleccionado(tblListaProductos)) {
                cargarRegistroSeleccionado();
                DlgMovimientoKardex dlgMovimientoKardex = new DlgMovimientoKardex(
                                myParentFrame, "", true);
                dlgMovimientoKardex.setVisible(true);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            if(!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ) {
                if(FarmaVariables.vEconoFar_Matriz)
                    FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtBuscar);
                else if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) || FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS )){
                    if (tieneRegistroSeleccionado(tblListaProductos)) {
                        cargarRegistroSeleccionado();
                        if(VariablesInventario.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                            FarmaUtility.showMessage(this,"No se puede realizar un ajuste al tipo de producto virtual", txtBuscar);
                        } else {
                            if(!validarAsistAudit()){//ASOSA 20.01.2010
                                VariablesInventario.vCFraccion=tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),7).toString();
                                DlgAjusteKardex dlgAjusteKardex = new DlgAjusteKardex(myParentFrame, "", true);
                                dlgAjusteKardex.setVisible(true);
                                
                                /* if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) ||  //06.08.2014
                                                         FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS ))      //
                                { dlgAjusteKardex.setTipo_rep("AAA");//
                                    log.info("parametro: "+"no envia");}
                                else{//
                                     dlgAjusteKardex.setTipo_rep(tipo_reposicion);//
                                     log.info("parametro: "+tipo_reposicion);}
                                dlgAjusteKardex.initCmbMotivoAjuste();  //06.08.2014
                                dlgAjusteKardex.setVisible(true); */
                                if (FarmaVariables.vAceptar) {
                                    /*if (VariablesPtoVenta.vInd_Filtro
                                                    .equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                                            filtrarTablaProductos();
                                    } else {
                                            cargaListaProductos();
                                    }*/
                                    tblListaProductos.setValueAt(VariablesInventario.vStk_ModEntero,tblListaProductos.getSelectedRow(),5);
                                    tblListaProductos.setValueAt(VariablesInventario.vStk_ModFrac,tblListaProductos.getSelectedRow(),6);
                                    tblListaProductos.repaint();
                                } 
                                
                            }
                        }
                    }
                } else 
                    FarmaUtility.showMessage(this,"Solo un usuario con rol Auditoria u Operador podra ingresar a esta opcion",txtBuscar);
                
                
            }else {
                FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opción",null);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) || FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS )){
                if(!validarAsistAudit()) ajusteDiferencias(); //ASOSA 20.01.2010               
            } else 
                FarmaUtility.showMessage(this,"Solo un usuario con rol Auditoria u Operador podra ingresar a esta opcion",txtBuscar);
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            cargaListaFiltro();
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            if (VariablesPtoVenta.vInd_Filtro
                            .equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_N;
                    tableModel.clearTable();
                    cargaListaProductos();
                    FarmaUtility.moveFocus(txtBuscar);
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            /*cargarRegistroSeleccionado();
            cargaStockLocales();*/
            funcion_F12();
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ) { 
                if(FarmaVariables.vEconoFar_Matriz)
                    FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtBuscar);
                else if(tieneRegistroSeleccionado(tblListaProductos)) {
                    cargarRegistroSeleccionado();
                    DlgExcesoProducto dlgExcesoProducto = new DlgExcesoProducto(myParentFrame, "", true);
                    dlgExcesoProducto.setVisible(true);
                }
            } else {
                FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opción",null);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if(!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ) { 
                if(FarmaVariables.vEconoFar_Matriz)
                    FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtBuscar);
                else {
                    DlgExcesoListado dlgExcesoListado = new DlgExcesoListado(myParentFrame,"",true);
                    dlgExcesoListado.setVisible(true);
                }
            } else {
                FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opción",null);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                this.setVisible(false);
        }
    }
    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************
    
    //private void cargaListaProductos() {
    public void cargaListaProductos() {
        try {
            //DBInventario.getListaProdsAK(tableModel);
            DBInventario.getListaProdsAK(tableModel);
            if (tblListaProductos.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaProductos, tableModel, 1,
                                FarmaConstants.ORDEN_ASCENDENTE);
            
          ArrayList rowsWithOtherColor = new ArrayList();
        for(int i = 0; i < tableModel.data.size(); i++){
            if ( ((ArrayList)tableModel.data.get(i)).get(9).toString().trim().equalsIgnoreCase("I") )
            { //cantguias 8 es 0
              rowsWithOtherColor.add(String.valueOf(i));
            }
        }

        FarmaUtility.initSimpleListCleanColumns(tblListaProductos, tableModel,
             ConstantsInventario.columnsListaProductosAK,rowsWithOtherColor,Color.white,Color.red,false);

        tblListaProductos.getTableHeader().setReorderingAllowed(false);
        tblListaProductos.getTableHeader().setResizingAllowed(false);


            log.debug("se cargo la lista de prods");
        } catch (SQLException sql) {
            FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de productos : \n" + sql.getMessage(),txtBuscar);
        }
        lblTotalRegistros.setText("" + tblListaProductos.getRowCount());
        lblFiltro.setText("Todos los productos");
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;
        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void cargarRegistroSeleccionado() {
        VariablesInventario.vCodProd = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 0).toString().trim();
        VariablesInventario.vDescProd = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 1).toString().trim();
        VariablesInventario.vDescUnidPresent = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 2).toString().trim();
        VariablesInventario.vNomLab = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 3).toString().trim();
        VariablesInventario.vDescUnidFrac = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 4).toString().trim();
        VariablesInventario.vCant = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 5).toString().trim();
        VariablesInventario.vCantFrac = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 6).toString().trim();
        VariablesInventario.vFrac = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 7).toString().trim();
        VariablesInventario.vIndProdVirtual = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),8).toString().trim();
        log.debug("VariablesInventario.vFrac : " + VariablesInventario.vFrac);
        VariablesInventario.vStock = (Integer.parseInt(VariablesInventario.vCant) * Integer.parseInt(VariablesInventario.vFrac))+ Integer.parseInt(VariablesInventario.vCantFrac) ;
        //VariablesVentas.vCod_Prod
    }

    private void cargaListaFiltro() {
        DlgFiltroProductos dlgFiltroProductos = new DlgFiltroProductos(
            myParentFrame, "", true);
        dlgFiltroProductos.setVisible(true);
        if (FarmaVariables.vAceptar) {
            tableModel.clearTable();
            txtBuscar.setText("");
            filtrarTablaProductos();
            FarmaVariables.vAceptar = false;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
        }
    }

    private void filtrarTablaProductos() {
        try {
            tableModel.clearTable();
            DBInventario.cargaListaProductosKardex_Filtro(tableModel);
            FarmaUtility.ordenar(tblListaProductos, tableModel, 2,
                            FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this,"Error al filtrar la lista de productos : \n" + sql.getMessage(),txtBuscar);
        }
        lblTotalRegistros.setText("" + tblListaProductos.getRowCount());
        mostrarDatosFiltro();
    }

    private void mostrarDatosFiltro() {
        lblFiltro.setText("Fitro: " + VariablesPtoVenta.vDesc_Cat_Filtro
                        + " : " + VariablesPtoVenta.vDescFiltro);
    }

    private void ajusteDiferencias() {
        if (FarmaVariables.vEconoFar_Matriz)
            FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtBuscar);
        else if (tieneRegistroSeleccionado(tblListaProductos)) {
            cargarRegistroSeleccionado();
            if(VariablesInventario.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                FarmaUtility.showMessage(this,"No se puede realizar un ajuste de diferencias al tipo de producto virtual", txtBuscar);
            } else {
                DlgAjusteDiferencias dlgAjusteDiferencias= new DlgAjusteDiferencias(myParentFrame, "", true);
                dlgAjusteDiferencias.setVisible(true);
            }      
        }
        if (FarmaVariables.vAceptar) {
          /*if (VariablesPtoVenta.vInd_Filtro.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
          {
            filtrarTablaProductos();
          }
          else
          {
            cargaListaProductos();
          }*/
          tblListaProductos.setValueAt(VariablesInventario.vStk_ModEntero, tblListaProductos.getSelectedRow(), 5);
          /*tblListaProductos.setValueAt(VariablesInventario.vStk_ModFrac, 
                                       tblListaProductos.getSelectedRow(), 6);*/
          tblListaProductos.repaint();
        
        }
    }

    private void cargaStockLocales() {
        DlgStockLocales dlgStockLocales = new DlgStockLocales(myParentFrame,"",true);
        dlgStockLocales.setVisible(true);    
    }
  
    /**
    * Se da inicia el proceso de ver Stock Locales
    * @author dubilluz
    * @since  05.11.2007
    */
    private void funcion_F12()
    {
    if(cargaLogin()){
        log.debug("Se mostrara el Listado de Stock en Locales");
        VariablesPtoVenta.vRevisarIndStockLocales = FarmaConstants.INDICADOR_N;
        log.debug("Se cambia la variable VariablesPtoVenta.vRevisarIndStockLocales :" +
                          VariablesPtoVenta.vRevisarIndStockLocales);
     
        VariablesVentas.vCod_Prod  = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 0).toString().trim();
        VariablesVentas.vDesc_Prod = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 1).toString().trim();
        VariablesVentas.vUnid_Vta  = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 2).toString().trim();
        VariablesVentas.vNom_Lab   = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 3).toString().trim();
      
        DlgStockLocales dlgStockLocales = new DlgStockLocales(myParentFrame,"",true);
        dlgStockLocales.setVisible(true);        
        VariablesPtoVenta.vRevisarIndStockLocales = "";
    }
    }
    /**
    * Se muestra el loguin 
    * @author dubilluz
    * @since  05.11.2007
    */  
    private boolean cargaLogin() {
        String numsec = FarmaVariables.vNuSecUsu ;
        String idusu = FarmaVariables.vIdUsu ;
        String nomusu = FarmaVariables.vNomUsu ;
        String apepatusu = FarmaVariables.vPatUsu ;
        String apematusu = FarmaVariables.vMatUsu ;
    
        try{
            DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
            //se validara que sea un jefe de zona -- SUPERVISOR DE VENTAS
            dlgLogin.setRolUsuario(FarmaConstants.ROL_SUPERVISOR_VENTAS);
            dlgLogin.setVisible(true);
            FarmaVariables.vNuSecUsu  = numsec ;
            FarmaVariables.vIdUsu  = idusu ;
            FarmaVariables.vNomUsu  = nomusu ;
            FarmaVariables.vPatUsu  = apepatusu ;
            FarmaVariables.vMatUsu  = apematusu ;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu  = numsec ;
            FarmaVariables.vIdUsu  = idusu ;
            FarmaVariables.vNomUsu  = nomusu ;
            FarmaVariables.vPatUsu  = apepatusu ;
            FarmaVariables.vMatUsu  = apematusu ;
            FarmaVariables.vAceptar = false;
            log.error("",e);
            FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
        }
        return FarmaVariables.vAceptar;
    }
    
    /**
     * devuelve true si se trata de un asistente de auditoria
     * @author ASOSA
     * @since  18.01.2010
     * @return
     */
    private boolean validarAsistAudit(){
        boolean flag=false;
        String ind="";
        try{
            ind=DBInventario.validarAsistenteAuditoria(FarmaVariables.vCodCia,FarmaVariables.vCodLocal,FarmaVariables.vNuSecUsu);
            if(ind.equalsIgnoreCase("S"))flag=true;
        }catch(SQLException sql){
            log.error("",sql);
            FarmaUtility.showMessage(this,"ERROR en validarAsistAudit \n : " + sql.getMessage(),null);
        }
        return flag;
    }
    
    private String codBarra(){
        String cadena=txtBuscar.getText().trim();
        cadena=UtilityPtoVenta.getCadenaAlfanumerica(cadena);
        cadena=UtilityPtoVenta.getCodBarraSinCarControl(cadena);
        String codBarra="";
        try{
            codBarra=DBVentas.obtieneCodigoProductoBarra(cadena);
            return codBarra;
                
        }catch(SQLException e){
            log.error("",e);
            return "000000";
        }
        
    }
   
    /**
         * Para indicar el tipo de reposicion
         * @author rherrera  05.08.2014
         * @param tipo_reposicion tipo de reposicion
         */
        public void setTipo_reposicion(String tipo_reposicion){
            this.tipo_reposicion = tipo_reposicion;    
        }
        
       /* public void cargarNombre(){
            if (tipo_reposicion.equals(ConstantsCaja.TIPO_REPOSICION_INSUMOS)) this.setTitle("Kardex - Ajuste Productos de Insumos");
                else
            this.setTitle("Kardex - Ajustes Productos para Ventas");
        }*/
        
}