package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
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

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.inventario.dto.NotaEsCabDTO;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDevolucionVerDetalle.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.06.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDevolucionVerDetalle extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgDevolucionVerDetalle.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModel;
    private JTable tblListaProductos = new JTable();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelHeader pnllHeader1 = new JPanelHeader();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelWhite lblNrDevolucion = new JLabelWhite();
    private JLabelWhite lblNrDevolucionD = new JLabelWhite();
    private JLabelWhite lblFechaEmision = new JLabelWhite();
    private JLabelWhite lblFechaEmisionD = new JLabelWhite();
    private JLabelWhite lblMotivo = new JLabelWhite();
    private JLabelWhite lblMotivoD = new JLabelWhite();
    private JLabelWhite lblEstado = new JLabelWhite();
    private JLabelWhite lblEstadoD = new JLabelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JButtonLabel lblCantProductos = new JButtonLabel();
    private JButtonLabel lblCantProductosD = new JButtonLabel();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JLabelFunction lblEsc = new JLabelFunction();
    private NotaEsCabDTO notaEsCabDTO = null;
    private FacadeInventario facadeInventario = null;
    private JLabelFunction lblF10 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDevolucionVerDetalle() {
        this(null, "", false);
    }

    public DlgDevolucionVerDetalle(Frame parent, String title, boolean modal) {

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


        this.setSize(new Dimension(705, 422));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ver Devolucion de Productos");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        pnllHeader1.setBounds(new Rectangle(10, 10, 680, 80));
        pnlTitle1.setBounds(new Rectangle(10, 100, 680, 25));

        lblNrDevolucion.setText("No. Devolucion:");
        lblNrDevolucion.setBounds(new Rectangle(15, 15, 105, 15));
        lblNrDevolucionD.setFont(new Font("SansSerif", 0, 11));
        lblNrDevolucionD.setBounds(new Rectangle(110, 15, 110, 15));

        lblFechaEmision.setText("F. Emisión:");
        lblFechaEmision.setBounds(new Rectangle(345, 15, 70, 15));
        lblFechaEmisionD.setFont(new Font("SansSerif", 0, 11));
        lblFechaEmisionD.setBounds(new Rectangle(415, 15, 110, 15));

        lblMotivo.setText("Motivo:");
        lblMotivo.setBounds(new Rectangle(15, 50, 50, 15));
        lblMotivoD.setFont(new Font("SansSerif", 0, 11));
        lblMotivoD.setBounds(new Rectangle(66, 50, 200, 15));

        lblEstado.setText("Estado:");
        lblEstado.setBounds(new Rectangle(345, 50, 45, 15));
        lblEstadoD.setFont(new Font("SansSerif", 0, 11));
        lblEstadoD.setBounds(new Rectangle(415, 50, 110, 15));

        btnRelacionProductos.setText("Relacion de Productos Devueltos");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 205, 15));
        btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionProductos_keyPressed(e);
            }
        });
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });

        lblCantProductos.setText("Cantidad de Productos : ");
        lblCantProductos.setBounds(new Rectangle(475, 5, 135, 15));
        lblCantProductosD.setFont(new Font("SansSerif", 0, 11));
        lblCantProductosD.setBounds(new Rectangle(610, 5, 65, 15));

        scrListaProductos.setBounds(new Rectangle(10, 125, 680, 220));
        scrListaProductos.getViewport().add(tblListaProductos, null);

        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(600, 355, 85, 20));

        lblF10.setText("[ F10 ] Reimprimir");
        lblF10.setBounds(new Rectangle(95, 355, 125, 20));
        pnllHeader1.add(lblNrDevolucion, null);
        pnllHeader1.add(lblNrDevolucionD, null);
        pnllHeader1.add(lblFechaEmision, null);
        pnllHeader1.add(lblFechaEmisionD, null);
        pnllHeader1.add(lblMotivo, null);
        pnllHeader1.add(lblMotivoD, null);
        pnllHeader1.add(lblEstado, null);
        pnllHeader1.add(lblEstadoD, null);
        pnlTitle1.add(btnRelacionProductos, null);
        pnlTitle1.add(lblCantProductos, null);
        pnlTitle1.add(lblCantProductosD, null);
        jContentPane.add(lblF10, null);
        jContentPane.add(pnllHeader1, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        //cargarDetalle();
        //mostrarF2();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaProductosDevolucionVer, ConstantsInventario.defaultValuesListaProductosDevolucionVer,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductos, tableModel,
                                    ConstantsInventario.columnsListaProductosDevolucionVer);

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cargaListaProductos();
        lblCantProductosD.setText("" + tblListaProductos.getRowCount());
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnRelacionProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnRelacionProductos);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F10(e)) { //CHUANES 14.03.2014
            //Verificamos la ruta y el acceso ala impresora correspondiente a imprimir
            if (!UtilityCaja.verificaImpresora(this, null, ConstantsPtoVenta.TIP_COMP_GUIA)) {
                return;
            }

            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, btnRelacionProductos);
            else if (UtilityInventario.validaGuias(this, notaEsCabDTO.getCodEstadoNota(), btnRelacionProductos,
                                                   notaEsCabDTO.getNumNotaEs())) {
                if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro de reimprimir?")) {
                    FarmaVariables.vAceptar = false;
                    DlgListaImpresorasInv dlgListaImpresorasInv = new DlgListaImpresorasInv(myParentFrame, "", true);
                    dlgListaImpresorasInv.setVisible(true);

                    if (!FarmaVariables.vAceptar) {
                        return;
                    }
                    if (UtilityInventario.reimprimir(this, tblListaProductos, btnRelacionProductos,
                                                     notaEsCabDTO.getNumNotaEs())) {
                        //FarmaUtility.showMessage(this, "Guías impresas satisfactoriamente!", btnRelacionProductos);
                        try {
                            facadeInventario.confirmarDevolucion(notaEsCabDTO.getNumNotaEs());
                        } catch (Exception f) {
                            log.error("", f);
                        }
                        cerrarVentana(true);
                    }
                }
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

    private void cargaListaProductos() {
        cargarCabecera();
        tableModel.data = facadeInventario.cargaDetalleTransferencia(notaEsCabDTO.getNumNotaEs());
    }

    public void cargarCabecera() {
        lblNrDevolucionD.setText(notaEsCabDTO.getNumNotaEs());
        lblFechaEmisionD.setText(notaEsCabDTO.getFecEmisiForm());
        lblMotivoD.setText(notaEsCabDTO.getTipoMotiNotaEs());
        lblEstadoD.setText(notaEsCabDTO.getEstaNotaEsCab());
    }

    void setNotaEsCabDTO(NotaEsCabDTO notaEsCabDTO) {
        this.notaEsCabDTO = notaEsCabDTO;
    }

    void setFacade(FacadeInventario facadeInventario) {
        this.facadeInventario = facadeInventario;
    }
}
