package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JLabelFunction;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFiltroProductosNuevo.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * KMONCADA             09.01.2015   Creación<br>
 * <br>
 * @author Kenny Moncada<br>
 * @version 1.0<br>
 *
 */

public class DlgFiltroProductosNuevo extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgFiltroProductosNuevo.class);
    private String codigoFiltroProducto; // 0: sintomas 1: principio activo 2:accion terapeutica 3: laboratorio 
    private Frame myParentFrame;
    private FarmaTableModel tableModelListaFiltro;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel pnlRelacionFiltros = new JPanel();
    private JLabel lblRelacionFiltros = new JLabel();
    private JPanel pnlIngresarProductos = new JPanel();
    private JButton btnCategoria = new JButton();
    private JTextFieldSanSerif txtProducto = new JTextFieldSanSerif();
    private JButton btnProducto = new JButton();
    private JTable tblFiltro = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JTextField txtCategoria = new JTextField();
    
    private String tipoProducto;
    
    private final String NOM_CAT_SINTOMAS = "SINTOMAS";
    private final String NOM_CAT_PRINCIPIO_ACTIVO = "PRINCIPIO ACTIVO";
    private final String NOM_CAT_ACCION_TERAPEUTICA = "ACCION TERAPEUTICA";
    private final String NOM_CAT_LABORATORIO = "LABORATORIO";
    
    private final String CAT_SINTOMAS = "0";
    private final String CAT_PRINCIPIO_ACTIVO = "1";
    private final String CAT_ACCION_TERAPEUTICA = "2";
    private final String CAT_LABORATORIO = "3";
    private final String PROD_FARMA = "S";
    private final String PROD_NO_FARMA = "N";
    private final String ORDEN_COMERCIAL = "C";
    private final String ORDEN_ALFABETICO = "A";
    
    private TableRowSorter<FarmaTableModel> sorter;
    private String tipoOrden;
    
    private ArrayList lstNombresFiltro = new ArrayList();
    private ArrayList lstCodigoFiltro = new ArrayList();
    private ArrayList lstOrdenFiltro = new ArrayList();
    private ArrayList lstIndFiltroTipo = new ArrayList();
  
    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgFiltroProductosNuevo() {
        this(null, "", false);
    }

    public DlgFiltroProductosNuevo(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(526, 413));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Filtro de Productos");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(623, 335));
        jContentPane.setForeground(Color.white);
        lblEnter.setText("<html><center>[ ENTER ]<br>Seleccionar");
        lblEnter.setBounds(new Rectangle(260, 350, 120, 30));
        
        lblF1.setText("<html><center>[ F1 ]<br>S\u00edntomas");
        lblF1.setBounds(new Rectangle(10, 310, 120, 30));

        lblF1.setHorizontalTextPosition(SwingConstants.CENTER);
        lblF2.setText("<html><center>[ F2 ]<br>Principios Activos");
        lblF2.setBounds(new Rectangle(135, 310, 120, 30));
        
        lblF3.setText("<html><center>[ F3 ]<br>Acción Terapéutica");
        lblF3.setBounds(new Rectangle(260, 310, 120, 30));
        
        lblF4.setText("<html><center>[ F4 ]<br>Laboratorios");
        lblF4.setBounds(new Rectangle(385, 310, 120, 30));
        
        
        txtCategoria.setBounds(new Rectangle(95, 10, 330, 20));
        txtCategoria.setFont(new Font("SansSerif", 1, 11));
        txtCategoria.setForeground(new Color(32, 105, 29));
        txtCategoria.setEditable(false);
        
        jScrollPane1.setBounds(new Rectangle(15, 120, 480, 185));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        pnlRelacionFiltros.setBounds(new Rectangle(15, 100, 480, 20));
        pnlRelacionFiltros.setBackground(new Color(255, 130, 14));
        pnlRelacionFiltros.setLayout(null);
        lblRelacionFiltros.setText("Relacion de Filtros");
        lblRelacionFiltros.setBounds(new Rectangle(5, 0, 145, 20));
        lblRelacionFiltros.setFont(new Font("SansSerif", 1, 11));
        lblRelacionFiltros.setForeground(Color.white);
        pnlIngresarProductos.setBounds(new Rectangle(20, 10, 465, 75));
        pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 14), 1));
        pnlIngresarProductos.setBackground(Color.white);
        pnlIngresarProductos.setLayout(null);
        pnlIngresarProductos.setForeground(Color.orange);
        btnCategoria.setText("Categoria :");
        btnCategoria.setBounds(new Rectangle(10, 10, 75, 20));
        btnCategoria.setMnemonic('c');
        btnCategoria.setRequestFocusEnabled(false);
        btnCategoria.setFocusPainted(false);
        btnCategoria.setDefaultCapable(false);
        btnCategoria.setBorderPainted(false);
        btnCategoria.setBackground(new Color(43, 141, 39));
        btnCategoria.setHorizontalAlignment(SwingConstants.LEFT);
        btnCategoria.setFont(new Font("SansSerif", 1, 11));
        btnCategoria.setForeground(new Color(255, 140, 14));
        btnCategoria.setHorizontalTextPosition(SwingConstants.LEADING);
        btnCategoria.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnCategoria.setContentAreaFilled(false);
        txtProducto.setBounds(new Rectangle(95, 40, 330, 20));
        //txtProducto.setForeground(new Color(32, 105, 29));
        txtProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }
        });
        btnProducto.setText("Filtro :");
        btnProducto.setBounds(new Rectangle(10, 40, 60, 25));
        btnProducto.setMnemonic('f');
        btnProducto.setFont(new Font("SansSerif", 1, 11));
        btnProducto.setDefaultCapable(false);
        btnProducto.setRequestFocusEnabled(false);
        btnProducto.setBackground(new Color(50, 162, 65));
        btnProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnProducto.setFocusPainted(false);
        btnProducto.setHorizontalAlignment(SwingConstants.LEFT);
        btnProducto.setContentAreaFilled(false);
        btnProducto.setBorderPainted(false);
        btnProducto.setForeground(new Color(255, 140, 14));
        btnProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProducto_actionPerformed(e);
            }
        });
        tblFiltro.setFont(new Font("SansSerif", 0, 12));
        tblFiltro.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblFiltro_keyPressed(e);
            }
        });
        lblEsc.setText("<html><center>[ ESC ]<br>Cerrar");
        lblEsc.setBounds(new Rectangle(385, 350, 120, 30));
        jScrollPane1.getViewport();
        pnlIngresarProductos.add(txtCategoria, null);
        pnlIngresarProductos.add(btnCategoria, null);
        pnlIngresarProductos.add(txtProducto, null);
        pnlIngresarProductos.add(btnProducto, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF4, null);
        jScrollPane1.getViewport().add(tblFiltro, null);
        jContentPane.add(jScrollPane1, null);
        pnlRelacionFiltros.add(lblRelacionFiltros, null);
        jContentPane.add(pnlRelacionFiltros, null);
        jContentPane.add(pnlIngresarProductos, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        try{
            initTable();
            // SE CARGA TIPO SINTOMAS POR DEFECTO
            cargarDatosFiltro();
            funcionFiltro(0);
            cargaListaFiltros();
            FarmaVariables.vAceptar = false;
        }catch(Exception ex){
            log.error("", ex);
            FarmaUtility.showMessage(this, "Error en Filtro: "+ex.getMessage(), null);
        }
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModelListaFiltro = new FarmaTableModel(ConstantsPtoVenta.columnsListaFiltroNuevo, ConstantsPtoVenta.defaultValuesListaFiltroNuevo, 0);
        FarmaUtility.initSimpleList(tblFiltro, tableModelListaFiltro, ConstantsPtoVenta.columnsListaFiltroNuevo);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void tblFiltro_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProducto);
    }

    private void txtProducto_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblFiltro.getSelectedRow() >= 0) {
                /*if (!(FarmaUtility.findTextInJTable(tblFiltro, txtProducto.getText().trim(), 0, 1))) {
                    FarmaUtility.showMessage(this, "Filtro No Encontrado según Criterio de Búsqueda !!!", txtProducto);
                    return;
                } else {*/
                    guardaValoresFiltro();
                    cerrarVentana(true);
                //}
            }
        }
        chkKeyPressed(e);
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        //FarmaGridUtils.buscarDescripcion(e, tblFiltro, null, 1);
        FarmaGridUtils.aceptarTeclaPresionada(e, tblFiltro, null, 1);
        txtProducto.setText(txtProducto.getText().toUpperCase());
        String expr = txtProducto.getText();//.toUpperCase();
        if (expr.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            //expr = expr.toUpperCase();
            sorter.setRowFilter(RowFilter.regexFilter(""+expr));
        }
        sorter.setSortKeys(null); 
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if(UtilityPtoVenta.verificaVK_F1(e)){
            //funcionF1(); 
            funcionFiltro(0);
        }
        if(UtilityPtoVenta.verificaVK_F2(e)){
            //funcionF2(); 
            funcionFiltro(1);
        }
        if(e.getKeyCode()==KeyEvent.VK_F3){
            //funcionF3(); 
            funcionFiltro(2);
        }
        if(e.getKeyCode()==KeyEvent.VK_F4){
            //funcionF4(); 
            funcionFiltro(3);
        }
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            cerrarVentana(false);
        }
        if(UtilityPtoVenta.verificaVK_F11(e)){
            guardaValoresFiltro();
            cerrarVentana(true); 
        }
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaFiltros() {
        try {
            tblFiltro.setRowSorter(null);
            DBPtoVenta.cargaListaFiltroNuevo(tableModelListaFiltro, codigoFiltroProducto, tipoProducto);
            FarmaUtility.ordenar(tblFiltro, tableModelListaFiltro, 1, FarmaConstants.ORDEN_ASCENDENTE);
            
            sorter = new TableRowSorter<FarmaTableModel>(tableModelListaFiltro);
            tblFiltro.setRowSorter(sorter);
        } catch (SQLException e) {
            log.error("", e);
        }
    }

    private void guardaValoresFiltro() {
        VariablesPtoVenta.vCodFiltro = ((String)tblFiltro.getValueAt(tblFiltro.getSelectedRow(), 0)).trim();
        VariablesPtoVenta.vDescFiltro = ((String)tblFiltro.getValueAt(tblFiltro.getSelectedRow(), 1)).trim();
        VariablesPtoVenta.vTipoFiltro = codigoFiltroProducto;
        VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
        VariablesPtoVenta.vDesc_Cat_Filtro = txtCategoria.getText();
    }
    
    private void funcionFiltro(int indice){
        String indFiltroTipoProducto = (String)lstIndFiltroTipo.get(indice); 
        if("S".equalsIgnoreCase(indFiltroTipoProducto)){
            DlgFiltroTipoProducto dlgFiltro = new DlgFiltroTipoProducto(myParentFrame, "Tipo de Producto", true);
            dlgFiltro.setVisible(true);
            if(dlgFiltro.getTipoFiltroProducto().length()!= 0){
                txtCategoria.setText((String)lstNombresFiltro.get(indice));
                codigoFiltroProducto  = (String)lstCodigoFiltro.get(indice); 
                tipoOrden = (String)lstOrdenFiltro.get(indice); 
                tipoProducto  = dlgFiltro.getTipoFiltroProducto();
                txtCategoria.setText(txtCategoria.getText()+" - "+dlgFiltro.getDescTipoFiltroProducto());
            }else{
                FarmaUtility.showMessage(this, "Tiene que seleccionar un tipo de Producto", txtProducto);
            }
        }else{
            txtCategoria.setText((String)lstNombresFiltro.get(indice));
            codigoFiltroProducto  = (String)lstCodigoFiltro.get(indice); 
            tipoOrden = (String)lstOrdenFiltro.get(indice); 
            tipoProducto  = "S";
        }
        cargaListaFiltros();
    }
    
    public String getTipoOrden(){
        return this.tipoOrden;
    }
    
    private void cargarDatosFiltro() throws Exception{
        List lstFiltro = DBPtoVenta.getFiltroProductos();
        if(lstFiltro.size()>0){
            for(int i=0;i<lstFiltro.size();i++){
                Map filtro = (HashMap)lstFiltro.get(i);
                lstCodigoFiltro.add(((String)filtro.get("CODIGO")).trim());
                lstNombresFiltro.add(((String)filtro.get("DESCRIPCION")).trim());
                lstOrdenFiltro.add(((String)filtro.get("ORDEN")).trim());
                lstIndFiltroTipo.add(((String)filtro.get("IND_TIPO")).trim());
            }
        }

    }
}
