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
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.dto.NotaEsCabDTO;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDevolucionesLista.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.06.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDevolucionesLista extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgDevolucionesLista.class);

    private Frame myParentFrame;
    private FarmaTableModel tableDevolucion;
    private JTable tblListaDevoluciones = new JTable();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JScrollPane scrListaDevoluciones = new JScrollPane();
    private JButtonLabel btnRelacionDevoluciones = new JButtonLabel();
    private JButtonLabel lblCantDevoluciones = new JButtonLabel();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF6 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();

    //Variables
    transient FacadeInventario facadeInventario = new FacadeInventario();

    private final int COL_COD_ESTADO = 8;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDevolucionesLista() {
        this(null, "", false);
    }

    public DlgDevolucionesLista(Frame parent, String title, boolean modal) {
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
        this.setTitle("Relacion de Devoluciones");
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

        btnRelacionDevoluciones.setText("Relacion de Devoluciones");
        btnRelacionDevoluciones.setBounds(new Rectangle(5, 5, 165, 15));
        btnRelacionDevoluciones.setMnemonic('R');
        btnRelacionDevoluciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionDevoluciones_actionPerformed(e);
            }
        });

        lblCantDevoluciones.setText("Cantidad de Transferencias");
        lblCantDevoluciones.setBounds(new Rectangle(545, 5, 160, 15));

        pnlTitle1.add(tblListaDevoluciones, null);
        pnlTitle1.setBounds(new Rectangle(10, 10, 770, 25));
        scrListaDevoluciones.setBounds(new Rectangle(10, 35, 770, 260));
        scrListaDevoluciones.getViewport().add(tblListaDevoluciones, null);
        tblListaDevoluciones.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaDevoluciones_keyPressed(e);
            }
        });

        lblF1.setText("[ F1 ] Nueva Devolucion");
        lblF1.setBounds(new Rectangle(15, 315, 160, 20));
        lblF2.setText("[ F2 ] Ver Devolucion");
        lblF2.setBounds(new Rectangle(185, 315, 155, 20));
        lblF6.setBounds(new Rectangle(350, 315, 117, 19));
        lblF6.setText("[ F6 ] Filtrar");
        lblF6.setSize(new Dimension(117, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(690, 315, 90, 20));

        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrListaDevoluciones, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF6, null);
        jContentPane.add(lblEsc, null);
        pnlTitle1.add(btnRelacionDevoluciones, null);
        pnlTitle1.add(lblCantDevoluciones, null);
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
        tblListaDevoluciones.getTableHeader().setReorderingAllowed(false);
        tblListaDevoluciones.getTableHeader().setResizingAllowed(false);
        tableDevolucion =
                new FarmaTableModel(ConstantsInventario.columnsListaDevolucionesRealizadas, ConstantsInventario.defaultValuesListaDevolucionesRealizadas,
                                    0);
        FarmaUtility.initSimpleList(tblListaDevoluciones, tableDevolucion,
                                    ConstantsInventario.columnsListaDevolucionesRealizadas);
        NotaEsCabDTO notaEsCabDTO = new NotaEsCabDTO();
        cargaListaDevoluciones(notaEsCabDTO);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaDevoluciones);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnRelacionDevoluciones_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaDevoluciones);
    }

    private void tblListaDevoluciones_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {

        if (UtilityPtoVenta.verificaVK_F1(e)) {
            nuevaDevolucion();
        } else if (UtilityPtoVenta.verificaVK_F2(e)) {
            cargarDatos();
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            filtrar();
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

    private void cargarDatos() {
        int vPos = tblListaDevoluciones.getSelectedRow();
        NotaEsCabDTO notaEsCabDTO = new NotaEsCabDTO();
        notaEsCabDTO.setNumNotaEs(tblListaDevoluciones.getValueAt(vPos, 0).toString());
        notaEsCabDTO.setEstaNotaEsCab(tblListaDevoluciones.getValueAt(vPos, 4).toString());
        notaEsCabDTO.setTipoMotiNotaEs(tblListaDevoluciones.getValueAt(vPos, 7).toString());
        notaEsCabDTO.setFecEmisiForm(tblListaDevoluciones.getValueAt(vPos, 2).toString());
        notaEsCabDTO.setCodEstadoNota(tblListaDevoluciones.getValueAt(vPos, COL_COD_ESTADO).toString());

        DlgDevolucionVerDetalle dlgDetalleDevolucionVer = new DlgDevolucionVerDetalle(myParentFrame, "", true);
        dlgDetalleDevolucionVer.setFacade(facadeInventario);
        dlgDetalleDevolucionVer.setNotaEsCabDTO(notaEsCabDTO);
        dlgDetalleDevolucionVer.setVisible(true);

        if (FarmaVariables.vAceptar) {
            NotaEsCabDTO notaEsCabDTO2 = new NotaEsCabDTO();
            cargaListaDevoluciones(notaEsCabDTO2);
        }
    }

    private void filtrar() {
        if (tblListaDevoluciones.getRowCount() > 0) {
            NotaEsCabDTO notaEsCabDTO = new NotaEsCabDTO();
            VariablesInventario.vTipoNota = ConstantsPtoVenta.TIP_NOTA_SALIDA;
            VariablesInventario.vNomInHashtableGuias = ConstantsInventario.NOM_HASTABLE_CMBFILTRO_TRANSF;
            DlgFiltroGuias dlgFiltroGuias = new DlgFiltroGuias(myParentFrame, "", true);
            dlgFiltroGuias.setVisible(true);
            if (FarmaVariables.vAceptar) {
                notaEsCabDTO.setFiltro(VariablesInventario.vCodFiltro);
                cargaListaDevoluciones(notaEsCabDTO);
                FarmaVariables.vAceptar = false;
            }
        }
    }

    private void cargaListaDevoluciones(NotaEsCabDTO notaEsCabDTO) {

        ArrayList listaDevoluciones = new ArrayList();
        tableDevolucion.clearTable();
        try {
            listaDevoluciones = facadeInventario.listarDevoluciones(notaEsCabDTO);
            if (listaDevoluciones != null) {
                tableDevolucion.data = listaDevoluciones;
                if (tableDevolucion.data.size() > 0) {
                    FarmaUtility.ordenar(tblListaDevoluciones, tableDevolucion, 0, FarmaConstants.ORDEN_DESCENDENTE);
                }
            }
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al listar las devoluciones: \n" +
                    sql.getMessage(), btnRelacionDevoluciones);
        }
    }

    private void nuevaDevolucion() {
        DlgDevolucionProductos dlgDevolucionProductos = new DlgDevolucionProductos(myParentFrame, "", true);
        dlgDevolucionProductos.setFacadeInventario(facadeInventario);
        dlgDevolucionProductos.setVisible(true);
        if (FarmaVariables.vAceptar) {
            NotaEsCabDTO notaEsCabDTO = new NotaEsCabDTO();
            cargaListaDevoluciones(notaEsCabDTO);
        }
    }

}
