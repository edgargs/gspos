package mifarma.ptoventa.administracion.impresoras;

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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.impresoras.reference.*;

/**
* Copyright (c) 2006 MIFARMA S.A.C.<br>
* <br>
* Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
* Nombre de la Aplicación : DlgDatosLaboratorio.java<br>
* <br>
* Histórico de Creación/Modificación<br>
*        25.06.2009   Creación <br>
* <br>
* @author Jenny Chavez<br>
* @version 1.0<br>
*
*/
public class DlgListaImpresoraTermica  extends JDialog{
    
    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlListaImpresoraT = new JPanelTitle();
    private JButtonLabel btnListaImpresoraT = new JButtonLabel();
    private JScrollPane srcListaImpresorasT = new JScrollPane();
    private JTable tblListaImpresorasT = new JTable();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();


    // **************************************************************************
    // Constructores
    // **************************************************************************
    
    public DlgListaImpresoraTermica() {
        this(null, "", false);
    }
    
    public DlgListaImpresoraTermica(Frame parent, String title, boolean modal) {
        
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
     
        this.setSize(new Dimension(350, 267));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Impresoras Térmicas");
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });

        pnlListaImpresoraT.setBounds(new Rectangle(5, 10, 335, 25));

        btnListaImpresoraT.setText("Lista Impresoras Térmicas:");
        btnListaImpresoraT.setBounds(new Rectangle(5, 0, 165, 25));
        btnListaImpresoraT.setMnemonic('l');

        btnListaImpresoraT.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnListaImpresoraT_actionPerformed(e);
                    }
                });

        srcListaImpresorasT.setBounds(new Rectangle(5, 35, 335, 175));

        tblListaImpresorasT.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblListaImpresorasT_keyPressed(e);
                    }
                });

        lblF11.setBounds(new Rectangle(140, 215, 90, 20));
        lblF11.setText("[F11] Aceptar");
        lblEsc.setBounds(new Rectangle(240, 215, 75, 20));
        lblEsc.setText("[Esc] Salir");
        
        pnlListaImpresoraT.add(btnListaImpresoraT, null);
        srcListaImpresorasT.getViewport().add(tblListaImpresorasT, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(srcListaImpresorasT, null);
        jContentPane.add(pnlListaImpresoraT, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
    
    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize(){
        FarmaVariables.vAceptar = false;
        initTable();
    }
    
    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************
    private void initTable() {
        tableModel = new FarmaTableModel(ConstantsImpresoras.columnsListaImpresoraTermica, ConstantsImpresoras.defaultValuesListaImpresoraTermica, 0);
        FarmaUtility.initSimpleList(this.tblListaImpresorasT, tableModel,ConstantsImpresoras.columnsListaImpresoraTermica);
        cargaLista();
    }
    
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(this.tblListaImpresorasT);
    }

    private void btnListaImpresoraT_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(this.tblListaImpresorasT);
    }

    private void tblListaImpresorasT_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************
   
    private void chkKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_F1) // Reservado para ayuda
        {
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {

            if (FarmaUtility.rptaConfirmDialog(this, 
                                               "¿Esta seguro de asignar la IP a la impresora seleccionada?")) {

                actualizarRelacionIPImprTermica();
            }

            cerrarVentana(true);

        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            FarmaVariables.vAceptar = false;
            this.setVisible(false);
            this.dispose();
        }

    }
    
    private void actualizarRelacionIPImprTermica() {
        
        int vFila=this.tblListaImpresorasT.getSelectedRow();  
        
        VariablesImpresoras.vSecIPImprTerm =FarmaUtility.getValueFieldArrayList(this.tableModel.data,vFila,0).trim();        
        
          try{
            DBImpresoras.actualizaRelacionIPImprTermica(VariablesImpresoras.vSecIP, VariablesImpresoras.vSecIPImprTerm);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this,"Asignacion Exitosa.",this.tblListaImpresorasT);
            cerrarVentana(true);
            }catch (SQLException sql)
              {
                sql.printStackTrace();
                FarmaUtility.showMessage(this,"Ocurrio un error guardar la IP.\n"+sql.getMessage(),this.tblListaImpresorasT);
              }
    }
        
   // **************************************************************************
   // Metodos de lógica de negocio
   // **************************************************************************

   private void cerrarVentana(boolean pAceptar) {
       FarmaVariables.vAceptar = pAceptar;
       this.setVisible(false);
       this.dispose();
   }
    private void cargaLista() {
        try {
            DBImpresoras.getListaImpresoraTermica(tableModel,VariablesImpresoras.vSecIP);
            if (tblListaImpresorasT.getRowCount() > 0){
                FarmaUtility.ordenar(this.tblListaImpresorasT, tableModel, 0,FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.moveFocus(tblListaImpresorasT);
            }
            System.out.println("se cargo la lista");
        } catch (SQLException ex) {
            ex.printStackTrace();
            FarmaUtility.showMessage(this,"Error al listar. \n " + ex.getMessage(),this.tblListaImpresorasT);
        }
    }
}
