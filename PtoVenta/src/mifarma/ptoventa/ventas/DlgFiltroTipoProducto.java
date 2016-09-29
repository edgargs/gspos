package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFiltro.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * KMONCADA      12.01.2015   Creación<br>
 * <br>
 * @author Kenny Moncada<br>
 * @version 1.0<br>
 *
 */

public class DlgFiltroTipoProducto extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgFiltroTipoProducto.class);

    /* ************************************************************************ */
    /*                         DECLARACION PROPIEDADES                          */
    /* ************************************************************************ */

    private Frame myParentFrame;
    ArrayList parametros;
    private FarmaTableModel tableModelListaFiltro;

    private JPanelWhite jContentPane = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnSelec = new JButtonLabel();
    private JScrollPane srcTipo = new JScrollPane();
    private JTable tblListaTipo = new JTable();
    
    private String tipoFiltroProducto = "";
    private String descTipoFiltroProducto = "";

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgFiltroTipoProducto() {
        this(null, "", false);
    }

    public DlgFiltroTipoProducto(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(225, 175));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Seleccione Tipo de Producto");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });


        lblEnter.setBounds(new Rectangle(5, 125, 110, 20));
        lblEnter.setText("[ ENTER ] Elegir");
        lblEsc.setBounds(new Rectangle(125, 125, 90, 20));
        lblEsc.setText("[ ESC ] Escape");
        pnlTitle1.setBounds(new Rectangle(5, 10, 205, 25));
        btnSelec.setText("Tipo de Producto");
        btnSelec.setBounds(new Rectangle(10, 5, 125, 15));
        btnSelec.setMnemonic('P');
        btnSelec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSelec_actionPerformed(e);
            }
        });
        srcTipo.setBounds(new Rectangle(5, 35, 205, 80));
        tblListaTipo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaTipo_keyPressed(e);
            }
        });


        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        srcTipo.getViewport().add(tblListaTipo, null);
        jContentPane.add(srcTipo, null);
        pnlTitle1.add(btnSelec, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, null);

    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        //cargar_cmbTipo();
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */


    private void initTable() {
        FarmaColumnData columnsListaTipoFiltroNuevo[] ={ 
                    new FarmaColumnData("Descripción", 150, JLabel.LEFT), 
                    new FarmaColumnData("", 0, JLabel.LEFT) };
        tableModelListaFiltro = new FarmaTableModel(columnsListaTipoFiltroNuevo, ConstantsVentas.defaultValuesListaTipoFiltroNuevo,0);
        FarmaUtility.initSimpleList(tblListaTipo, tableModelListaFiltro, columnsListaTipoFiltroNuevo);
        cargaLista();
    }

    /**
     * Carga los Items  al cmbTipo
     */
    /*private void cargar_cmbTipo(){
   //Ubicacion
   FarmaLoadCVL.loadCVLfromArrays(cmbTipo,ConstantsVentas.NOM_HASTABLE_CMBFILTROTIPO,
   ConstantsVentas.vCodColumna,ConstantsVentas.vDescColumna,true);
  }*/

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaTipo);
    }


    private void cmbTipo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            tipoFiltroProducto = tableModelListaFiltro.getValueAt(tblListaTipo.getSelectedRow(), 1).toString();
            descTipoFiltroProducto = tableModelListaFiltro.getValueAt(tblListaTipo.getSelectedRow(), 0).toString();
            log.info("Tipo de Filtro de Producto : "+tipoFiltroProducto+" - "+descTipoFiltroProducto);
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


    private void tblListaTipo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void cargaLista() {
        try {
            DBPtoVenta.listaTipoProducto(tableModelListaFiltro);
            FarmaUtility.moveFocusJTable(tblListaTipo);
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al obtener listado de Tipo de Producto.\n" +sql.getMessage(), tblListaTipo);
        }
    }

    private void btnSelec_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaTipo);
    }

    public String getTipoFiltroProducto(){
        return this.tipoFiltroProducto;
    }
    
    public String getDescTipoFiltroProducto(){
        return this.descTipoFiltroProducto;
    }
}
