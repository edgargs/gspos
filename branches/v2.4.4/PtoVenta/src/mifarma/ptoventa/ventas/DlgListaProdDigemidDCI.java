package mifarma.ptoventa.ventas;

import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import javax.swing.JTable;

import javax.swing.JTextField;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ventas.reference.ConstantsVentas;

import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaProdDigemidDCI.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JMIRANDA       02.10.2010   Creacion <br>
 * <br>
 * @author John Miranda<br>
 * @version 1.0<br>
 * 
 */

public class DlgListaProdDigemidDCI extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgListaProdDigemidDCI.class);
    
    private Frame myParentFrame;

    private FarmaTableModel tableModelListaCDI;
    
    /* COLUMNAS LISTADO DE PRODUCTOS */
    private final int COL_DESC = 0;
    private final int COL_UND_VTA = 1;
    private final int COL_LAB = 2;
    private final int COL_PREC_NOR = 3;
    private final int COL_PREC_D = 4;
    private final int COL_PREC_50 = 5;
    private final int COL_COD = 6;
    private final int COL_MENS_ACT = 7;
    
    private boolean bIndFirstOpen = true;
    
    private JPanelWhite pnlContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelHeader pnlTitulo = new JPanelHeader();
    private JScrollPane scrListaPorDCI = new JScrollPane();
    private JTable tblListaDCI = new JTable();
    private JButtonLabel btnProducto = new JButtonLabel();
    private JTextField txtProducto = new JTextField();
    private JLabelFunction btnSalir = new JLabelFunction();
    private JLabelWhite lblMensActivo = new JLabelWhite();
    private JLabelWhite lblTitActivo = new JLabelWhite();

    public DlgListaProdDigemidDCI() {
        this(null, "", false);
    }

    public DlgListaProdDigemidDCI(Frame parent, String title, 
                                           boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(730, 434));        
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Consultas por DCI");
        this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
        this.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }

                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        pnlContentPane.setBounds(new Rectangle(0, 0, 530, 330));
        pnlContentPane.setLayout(null);
        pnlTitulo.setBounds(new Rectangle(10, 10, 705, 60));
        scrListaPorDCI.setBounds(new Rectangle(10, 75, 705, 305));

        btnProducto.setText("Descripción :");
        btnProducto.setMnemonic('d');
        btnProducto.setBounds(new Rectangle(10, 10, 75, 15));
        btnProducto.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnProducto_actionPerformed(e);
                    }
                });
        txtProducto.setBounds(new Rectangle(90, 5, 285, 20));
        txtProducto.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtProducto_keyPressed(e);
                    }
                });
        btnSalir.setBounds(new Rectangle(620, 385, 95, 20));
        btnSalir.setText("[ESC] Salir");
        lblMensActivo.setText("");
        lblMensActivo.setBounds(new Rectangle(135, 35, 540, 15));
        lblTitActivo.setText("Principio(s) Activo(s):");
        lblTitActivo.setBounds(new Rectangle(10, 35, 125, 15));
        scrListaPorDCI.getViewport().add(tblListaDCI, null);
        pnlContentPane.add(btnSalir, null);
        pnlContentPane.add(scrListaPorDCI, null);
        pnlTitulo.add(lblTitActivo, null);
        pnlTitulo.add(lblMensActivo, null);
        pnlTitulo.add(txtProducto, null);
        pnlTitulo.add(btnProducto, null);
        pnlContentPane.add(pnlTitulo, null);
        this.getContentPane().add(pnlContentPane, null);
    }
    
    private void initialize(){        
        initTableListaDCI();        
        FarmaVariables.vAceptar = false;
        
        //permite que no se muevan las columnas de Jtable
        tblListaDCI.getTableHeader().setReorderingAllowed(false);
        //permite que no se cambien el tamaño de la columna del Jtable
        tblListaDCI.getTableHeader().setResizingAllowed(false);
        
        //Verifica que exista Productos en la tabla
        if(tblListaDCI.getRowCount()>0){
            setJTable(tblListaDCI,txtProducto);
        }
        else{
            FarmaUtility.showMessage(this,"No existen productos similares.",txtProducto);
            cerrarVentana(false);
        }

    }
    
    private void initTableListaDCI()
    {

        tableModelListaCDI=new FarmaTableModel(ConstantsVentas.columnsListProdsCDI,
                                        ConstantsVentas.defaultValuesListProdsCDI,
                                        0);
        FarmaUtility.initSimpleList(tblListaDCI,
                                    tableModelListaCDI,
                                    ConstantsVentas.columnsListProdsCDI);
        
        cargalistaProductosDCI();
        if(tableModelListaCDI.getRowCount()>0){
            log.debug("entrar");
            mostrarPrincActivo(bIndFirstOpen);
        }
    }
    
    private void cargalistaProductosDCI() {
      try {
        DBVentas.cargaListaProductosDCI(tableModelListaCDI,VariablesVentas.vCodProdDCI);      
        //FarmaUtility.setearPrimerRegistro(tblListaDCI,txtProducto,COL_DESC);
      } catch (SQLException sql) {
        log.error("",sql);
        //tFarmaUtility.showMessage(this, "Error al Listar Productos Alternativos.\n" + sqlException, txtProducto);
      }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 txtProducto);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
       
        FarmaUtility.moveFocus(txtProducto);

    }
    
    private void chkKeyPressed(KeyEvent e)
    {       
          if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
          {            
            cerrarVentana(false);
          } else if(e.getKeyCode() == KeyEvent.VK_ENTER)
          {            
            buscarProducto();
          }
    }     
    
    private void cerrarVentana(boolean pAceptar)
    {
      limpiarVariables();
      FarmaVariables.vAceptar = pAceptar;     
      this.setVisible(false);
      this.dispose();
    }

    private void txtProducto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e,tblListaDCI,txtProducto,0);
        bIndFirstOpen = false;
        mostrarPrincActivo(bIndFirstOpen);
        chkKeyPressed(e);
    }
    
    private void buscarProducto(){
        
    }
    
    private void setJTable(JTable pJTable,JTextField pText) {
      if(pJTable.getRowCount() > 0){
        FarmaGridUtils.showCell(pJTable, 0, 0);
        FarmaUtility.setearActualRegistro(pJTable,txtProducto,0);
      }
      FarmaUtility.moveFocus(pText);
    }
    
    private void limpiarVariables() {
        bIndFirstOpen = false;
        VariablesVentas.vCodProdDCI = "";
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }
    
    private void mostrarPrincActivo(boolean pOpen){
        int pRow = tblListaDCI.getSelectedRow();
        String pMensaje = "";
        if(pOpen){
            pRow = 1;
            pMensaje = FarmaUtility.getValueFieldArrayList(tableModelListaCDI.data,pRow,COL_MENS_ACT);            
        }else{
            pMensaje = FarmaUtility.getValueFieldArrayList(tableModelListaCDI.data,pRow,COL_MENS_ACT);            
        }
        lblMensActivo.setText(pMensaje);
    }
}
