package mifarma.ptoventa.recepcionCiega;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import javax.swing.JScrollPane;

import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;


public class DlgListaGuiasPendientes extends JDialog {
    
    Frame myParentFrame;

    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel btnRelacionGuiasPendientes = new JButtonLabel();
    private JPanelHeader pnlObservacion = new JPanelHeader();
    private JLabelWhite lblObservacion1 = new JLabelWhite();
    private JLabelWhite lblObservacion2 = new JLabelWhite();
    private JScrollPane srcListaGuias = new JScrollPane();
    private JTable tblListaGuias = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();


    // **************************************************************************
    // Constructores
    // **************************************************************************
    public DlgListaGuiasPendientes() {
        this(null, "", false);     
    }

    public DlgListaGuiasPendientes(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
               jbInit();
               initialize();
               FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
               e.printStackTrace();
        }

    }
  
    private void jbInit() throws Exception {
        
        this.setSize(new Dimension(442, 300));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Guias  Pendientes");
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                }

                public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                }
        });
        
        pnlTitle.setBounds(new Rectangle(10, 50, 415, 25));
        btnRelacionGuiasPendientes.setText("Relacion de Guias Pendientes");
        btnRelacionGuiasPendientes.setBounds(new Rectangle(10, 5, 285, 15));
        btnRelacionGuiasPendientes.setMnemonic('R');
        btnRelacionGuiasPendientes.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRelacionGuiasPendientes_actionPerformed(e);
                    }
                });
        pnlObservacion.setBounds(new Rectangle(10, 10, 415, 40));
        lblObservacion1.setText("Los productos de las siguientes gu�as no han si contadas, por lo tanto");
        lblObservacion1.setBounds(new Rectangle(15, 5, 395, 15));
        lblObservacion2.setText("ser�n eliminadas de la recepci�n");
        lblObservacion2.setBounds(new Rectangle(15, 20, 325, 15));
        srcListaGuias.setBounds(new Rectangle(10, 75, 415, 160));
        tblListaGuias.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblListaGuias_keyPressed(e);
                    }
                });
        lblEsc.setBounds(new Rectangle(310, 245, 117, 19));
        lblEsc.setText("[ Esc ] Salir ");
        pnlObservacion.add(lblObservacion2, null);
        pnlObservacion.add(lblObservacion1, null);
        srcListaGuias.getViewport().add(tblListaGuias, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(srcListaGuias, null);
        jContentPane.add(pnlObservacion, null);
        pnlTitle.add(btnRelacionGuiasPendientes, null);
        jContentPane.add(pnlTitle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
    
    // **************************************************************************
    // M�todo "initialize()"
    // **************************************************************************
    private void initialize() {
        FarmaVariables.vAceptar = false;
            initTable();
    }
    
    // **************************************************************************
    // M�todos de inicializaci�n
    // **************************************************************************
    private void initTable() {
            tableModel = new FarmaTableModel(
                            ConstantsRecepCiega.columnsListaGuiasPendientes,
                            ConstantsRecepCiega.defaultcolumnsListaGuiasPendientes, 0);
            FarmaUtility.initSimpleList(tblListaGuias, tableModel,
                            ConstantsRecepCiega.columnsListaGuiasPendientes);
            cargaListaGuias();
    }
    
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************
    private void this_windowOpened(WindowEvent e) {
            FarmaUtility.centrarVentana(this);
            FarmaUtility.moveFocus(this.tblListaGuias);
    }
    private void this_windowClosing(WindowEvent e) {
            FarmaUtility.showMessage(this,
                            "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    private void tblListaGuias_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
       
    private void chkKeyPressed(KeyEvent e) {    
         if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }
    
    private void btnRelacionGuiasPendientes_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(this.tblListaGuias);
    }
    
    // **************************************************************************
    // Metodos de l�gica de negocio
    // **************************************************************************
    public void cargaListaGuias(){
        try {
                DBRecepCiega.getListaGuiasPendientes(tableModel);
                if (tblListaGuias.getRowCount() > 0)
                {
                    FarmaUtility.ordenar(tblListaGuias, tableModel, 1,FarmaConstants.ORDEN_ASCENDENTE);
       
                }
        } catch (SQLException sql) {
            sql.printStackTrace();
                FarmaUtility.showMessage(this,"Ocurri� un error al cargar la lista de gu�as : \n",null);   
        }
    }

    private void cerrarVentana(boolean pAceptar) {
            FarmaVariables.vAceptar = pAceptar;
            this.setVisible(false);
            this.dispose();
    }


}
