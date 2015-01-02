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
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDevolucionesLista_02.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      18/08/2014   Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */
public class DlgDevolucionesLista_02 extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgDevolucionesLista_02.class);

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
    private JLabelFunction lblEsc = new JLabelFunction();

    //Variables
    transient FacadeInventario facadeInventario = new FacadeInventario();

    private final int COL_COD_ESTADO = 8;
    private JLabelFunction jLabelFunction1 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDevolucionesLista_02() {
        this(null, "", false);
    }

    public DlgDevolucionesLista_02(Frame parent, String title, boolean modal) {
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
        this.setTitle("Relaci\u00f3n de Devoluciones X Sobrantes");
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

        btnRelacionDevoluciones.setText("Relaci\u00f3n de Devoluciones X Sobrantes");
        btnRelacionDevoluciones.setBounds(new Rectangle(5, 5, 230, 15));
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
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(690, 315, 90, 20));

        jLabelFunction1.setBounds(new Rectangle(360, 315, 250, 20));
        jLabelFunction1.setText("[ F3 ] Ver Ordenes Pendientes Regularizar");
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrListaDevoluciones, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF2, null);
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
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            cargarOrdsPendRegularizar();
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

    private void cargarOrdsPendRegularizar() {
        DlgOrdCompExceso dlgOrdCompExceso = new DlgOrdCompExceso(myParentFrame, "", true);
        dlgOrdCompExceso.setVisible(true);
    }

    /*
    private void filtrar(){
      if(tblListaDevoluciones.getRowCount()>0){
        NotaEsCabDTO notaEsCabDTO = new NotaEsCabDTO();
        VariablesInventario.vTipoNota = ConstantsPtoVenta.TIP_NOTA_SALIDA;
        VariablesInventario.vNomInHashtableGuias = ConstantsInventario.NOM_HASTABLE_CMBFILTRO_TRANSF;
        DlgFiltroGuias dlgFiltroGuias = new DlgFiltroGuias(myParentFrame,"", true);
        dlgFiltroGuias.setVisible(true);
        if(FarmaVariables.vAceptar)
        {
          notaEsCabDTO.setFiltro(VariablesInventario.vCodFiltro);
          cargaListaDevoluciones(notaEsCabDTO);
          FarmaVariables.vAceptar = false;
        }
      }
    }
*/

    private void cargaListaDevoluciones(NotaEsCabDTO notaEsCabDTO) {

        ArrayList listaDevoluciones = new ArrayList();
        tableDevolucion.clearTable();
        notaEsCabDTO.setFiltro(ConstantsInventario.COD_MOT_KDX_SOBRANTE);
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

        DlgMercaderiaDirectaProducto_02 dlgMercaderiaDirectaProducto_02 =
            new DlgMercaderiaDirectaProducto_02(myParentFrame, "", true);
        dlgMercaderiaDirectaProducto_02.setFacade(facadeInventario);
        dlgMercaderiaDirectaProducto_02.setVisible(true);

        if (FarmaVariables.vAceptar) {
            NotaEsCabDTO notaEsCabDTO = new NotaEsCabDTO();
            cargaListaDevoluciones(notaEsCabDTO);
        }
    }

}
