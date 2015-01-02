package mifarma.ptoventa.inventario;


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

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.dto.NotaEsCabDTO;
import mifarma.ptoventa.inventario.dto.OrdenCompraCabDTO;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDevolucionesListaOC.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.06.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDevolucionesListaOC extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgDevolucionesListaOC.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModel;
    private JTable tblListaOrdenCompra = new JTable();

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JScrollPane scrListaOrdenCompra = new JScrollPane();
    private JButtonLabel btnRelacionOrdeCompra = new JButtonLabel();
    private JLabelFunction lblSeleccionar = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();

    private FacadeInventario facadeInventario;
    ArrayList listaOrdenesCompra = new ArrayList();
    private NotaEsCabDTO notaEsCabDTO = null;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDevolucionesListaOC() {
        this(null, "", false);
    }

    public DlgDevolucionesListaOC(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {

        this.setSize(new Dimension(790, 378));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Relacion de Ordenes de Compra");
        this.setDefaultCloseOperation(0);
        this.setBounds(new Rectangle(10, 10, 790, 394));
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        btnRelacionOrdeCompra.setText("Relacion");
        btnRelacionOrdeCompra.setBounds(new Rectangle(5, 5, 165, 15));
        btnRelacionOrdeCompra.setMnemonic('R');
        btnRelacionOrdeCompra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionOrdeCompra_actionPerformed(e);
            }
        });

        pnlTitle1.add(tblListaOrdenCompra, null);
        pnlTitle1.setBounds(new Rectangle(10, 10, 770, 25));
        scrListaOrdenCompra.setBounds(new Rectangle(10, 35, 770, 260));
        scrListaOrdenCompra.getViewport().add(tblListaOrdenCompra, null);
        tblListaOrdenCompra.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaOrdenCompra_keyPressed(e);
            }
        });

        lblSeleccionar.setText("[ ENTER ] Seleccionar");
        lblSeleccionar.setBounds(new Rectangle(10, 315, 160, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(690, 315, 90, 20));

        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrListaOrdenCompra, null);
        jContentPane.add(lblSeleccionar, null);
        jContentPane.add(lblEsc, null);
        pnlTitle1.add(btnRelacionOrdeCompra, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        /*tblListaOrdenCompra.getTableHeader().setReorderingAllowed(false);
      tblListaOrdenCompra.getTableHeader().setResizingAllowed(false);*/
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaOrdenesCompra, ConstantsInventario.defaultValuesListaOrdenesCompra,
                                    0);
        FarmaUtility.initSimpleList(tblListaOrdenCompra, tableModel, ConstantsInventario.columnsListaOrdenesCompra);
        if (tableModel.data.size() > 0) {
            FarmaGridUtils.showCell(tblListaOrdenCompra, 0, 0);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaOrdenCompra);

        OrdenCompraCabDTO ordenCompraCabDTO = new OrdenCompraCabDTO();
        cargaListaOrdenesCompra(ordenCompraCabDTO);

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnRelacionOrdeCompra_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaOrdenCompra);
    }

    private void tblListaOrdenCompra_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (listaOrdenesCompra.size() > 0) {
                int filaSelec = tblListaOrdenCompra.getSelectedRow();
                notaEsCabDTO.setCodOrdenCompra(tblListaOrdenCompra.getValueAt(filaSelec, 0).toString());
                cerrarVentana(true);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void cargaListaOrdenesCompra(OrdenCompraCabDTO ordenCompraCabDTO) {

        try {
            listaOrdenesCompra = facadeInventario.listarOrdenesCompra(ordenCompraCabDTO);
            tableModel.data = listaOrdenesCompra;
            FarmaUtility.ordenar(tblListaOrdenCompra, tableModel, 0, FarmaConstants.ORDEN_DESCENDENTE);

            if (listaOrdenesCompra.size() == 0) {
                cerrarVentana(true);
                FarmaUtility.showMessage(this, "No se encuentra Guias de ingreso de este Proveedor.", null);
            }

        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al listar las devoluciones: \n" +
                    sql.getMessage(), btnRelacionOrdeCompra);
        }
    }

    public void setNotaEsCabDTO(NotaEsCabDTO notaEsCabDTO) {
        this.notaEsCabDTO = notaEsCabDTO;
    }

    public void setFacadeInventario(FacadeInventario facadeInventario) {
        this.facadeInventario = facadeInventario;
    }
}
