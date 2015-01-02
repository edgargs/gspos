package mifarma.ptoventa.recetario;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recetario.reference.ConstantsRecetario;
import mifarma.ptoventa.recetario.reference.FacadeRecetario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDetalleRecetario extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgDetalleRecetario.class);

    private FacadeRecetario facadeRecetario = new FacadeRecetario();

    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JPanel pnlRecetarioCab = new JPanel();
    private JLabelWhite lblTitle = new JLabelWhite();
    private JLabelWhite lblTitleDetalle = new JLabelWhite();
    private JPanelTitle pnlTitleDetalle = new JPanelTitle();
    private JScrollPane sclDetalle = new JScrollPane();
    private JLabelFunction lblEsc = new JLabelFunction();

    private FarmaTableModel tableModel;
    private JTable tblDetalleRecetarios = new JTable();

    private JLabelOrange lblNumRec = new JLabelOrange();
    private JLabelOrange lblIndEsteril = new JLabelOrange();
    private JLabelOrange lblFormFarm = new JLabelOrange();
    private JLabelOrange lblcantCont = new JLabelOrange();
    private JLabelOrange lblCantPrep = new JLabelOrange();
    private JLabelOrange lblPaciente = new JLabelOrange();
    private JLabelOrange lblNumTelef = new JLabelOrange();
    private JLabelOrange lblMedico = new JLabelOrange();
    private JLabelOrange lblFecha = new JLabelOrange();
    private JLabelOrange lblValBruto = new JLabelOrange();
    private JLabelOrange lblFecHoraEntrega = new JLabelOrange();
    private JLabelOrange lblLocalEntrega = new JLabelOrange();
    private JLabelOrange lblEstado = new JLabelOrange();
    private JLabelOrange lblOrdPrep = new JLabelOrange();
    private JLabelOrange lblNumPedido = new JLabelOrange();

    private JLabel lblDatoNumRec = new JLabel();
    private JLabel lblDatoIndEsteril = new JLabel();
    private JLabel lblDatoFormFarm = new JLabel();
    private JLabel lblDatoCantCont = new JLabel();
    private JLabel lblDatoFecha = new JLabel();
    private JLabel lblDatoValBruto = new JLabel();
    private JLabel lblDatoOrdPrep = new JLabel();
    private JLabel lblDatoPaciente = new JLabel();
    private JLabel lblDatoNumTelef = new JLabel();
    private JLabel lblDatoMedico = new JLabel();
    private JLabel lblDatoFecHoraEntrega = new JLabel();
    private JLabel lblDatoLocalEntrega = new JLabel();
    private JLabel lblDatoEstado = new JLabel();
    private JLabel lblDatoNumPedido = new JLabel();
    private JLabel lblDatoCantPrep = new JLabel();

    private String num_recetario = "";

    public DlgDetalleRecetario() {
        this(null, "", false);
    }

    public DlgDetalleRecetario(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            initTable();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(658, 497));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Detalle de Recetario Magistral");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlFondo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlFondo_keyPressed(e);
            }
        });
        pnlTitle.setBounds(new Rectangle(10, 10, 635, 20));
        pnlTitle.setFocusable(false);
        pnlRecetarioCab.setBounds(new Rectangle(10, 30, 635, 220));
        pnlRecetarioCab.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        pnlRecetarioCab.setLayout(null);
        pnlRecetarioCab.setBackground(Color.white);
        pnlRecetarioCab.setFocusable(false);
        lblTitle.setText("Datos de Recetario Magistral");
        lblTitle.setBounds(new Rectangle(20, 0, 220, 20));
        lblTitle.setFocusable(false);
        pnlTitleDetalle.setBounds(new Rectangle(10, 260, 635, 20));
        pnlTitleDetalle.setFocusable(false);
        sclDetalle.setBounds(new Rectangle(10, 280, 635, 150));
        sclDetalle.setBackground(Color.white);
        sclDetalle.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(530, 435, 115, 25));
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        lblNumRec.setText("Num. Recetario:");
        lblNumRec.setBounds(new Rectangle(25, 15, 90, 15));
        lblNumRec.setSize(new Dimension(110, 15));
        lblNumRec.setFocusable(false);
        lblIndEsteril.setText("Ind. Esteril");
        lblIndEsteril.setBounds(new Rectangle(25, 40, 70, 15));
        lblIndEsteril.setSize(new Dimension(110, 15));
        lblIndEsteril.setFocusable(false);
        lblFormFarm.setText("Forma Farmaceut.:");
        lblFormFarm.setBounds(new Rectangle(25, 65, 110, 15));
        lblFormFarm.setFocusable(false);
        lblcantCont.setText("Cant. Contenido:");
        lblcantCont.setBounds(new Rectangle(25, 90, 100, 15));
        lblcantCont.setSize(new Dimension(110, 15));
        lblcantCont.setFocusable(false);
        lblPaciente.setText("Paciente:");
        lblPaciente.setBounds(new Rectangle(310, 15, 115, 15));
        lblPaciente.setSize(new Dimension(115, 15));
        lblPaciente.setFocusable(false);
        lblNumTelef.setText("Num. Telefono:");
        lblNumTelef.setBounds(new Rectangle(310, 40, 115, 15));
        lblNumTelef.setSize(new Dimension(115, 15));
        lblNumTelef.setFocusable(false);
        lblMedico.setText("Medico:");
        lblMedico.setBounds(new Rectangle(310, 65, 115, 15));
        lblMedico.setSize(new Dimension(115, 15));
        lblMedico.setFocusable(false);
        lblFecha.setText("Fecha:");
        lblFecha.setBounds(new Rectangle(25, 140, 110, 15));
        lblFecha.setSize(new Dimension(110, 15));
        lblFecha.setFocusable(false);
        lblValBruto.setText("Valor Bruto:");
        lblValBruto.setBounds(new Rectangle(25, 165, 110, 15));
        lblValBruto.setSize(new Dimension(110, 15));
        lblValBruto.setFocusable(false);
        lblFecHoraEntrega.setText("Fecha-Hora Entrega:");
        lblFecHoraEntrega.setBounds(new Rectangle(310, 90, 115, 15));
        lblFecHoraEntrega.setFocusable(false);
        lblLocalEntrega.setText("Local Entrega:");
        lblLocalEntrega.setBounds(new Rectangle(310, 115, 115, 15));
        lblLocalEntrega.setSize(new Dimension(115, 15));
        lblLocalEntrega.setFocusable(false);
        lblEstado.setText("Estado:");
        lblEstado.setBounds(new Rectangle(310, 140, 115, 15));
        lblEstado.setSize(new Dimension(115, 15));
        lblEstado.setFocusable(false);
        lblOrdPrep.setText("Orden Prep:");
        lblOrdPrep.setBounds(new Rectangle(25, 190, 110, 15));
        lblOrdPrep.setSize(new Dimension(110, 15));
        lblOrdPrep.setFocusable(false);
        lblNumPedido.setText("Num. Pedido:");
        lblNumPedido.setBounds(new Rectangle(310, 165, 115, 15));
        lblNumPedido.setSize(new Dimension(115, 15));
        lblNumPedido.setFocusable(false);
        lblTitleDetalle.setText("Detalle Recetario Magistral");
        lblTitleDetalle.setBounds(new Rectangle(15, 0, 275, 20));
        lblTitleDetalle.setFocusable(false);
        lblDatoNumRec.setBounds(new Rectangle(135, 15, 160, 15));
        lblDatoNumRec.setSize(new Dimension(160, 15));
        lblDatoNumRec.setFocusable(false);
        lblDatoIndEsteril.setBounds(new Rectangle(135, 40, 34, 14));
        lblDatoIndEsteril.setSize(new Dimension(160, 15));
        lblDatoIndEsteril.setFocusable(false);
        lblDatoFormFarm.setBounds(new Rectangle(135, 65, 34, 14));
        lblDatoFormFarm.setSize(new Dimension(160, 15));
        lblDatoFormFarm.setFocusable(false);
        lblDatoCantCont.setBounds(new Rectangle(135, 90, 34, 14));
        lblDatoCantCont.setSize(new Dimension(160, 15));
        lblDatoCantCont.setFocusable(false);
        lblDatoFecha.setBounds(new Rectangle(135, 140, 34, 14));
        lblDatoFecha.setSize(new Dimension(160, 15));
        lblDatoFecha.setFocusable(false);
        lblDatoValBruto.setBounds(new Rectangle(135, 165, 34, 14));
        lblDatoValBruto.setSize(new Dimension(160, 15));
        lblDatoValBruto.setFocusable(false);
        lblDatoOrdPrep.setBounds(new Rectangle(135, 190, 35, 15));
        lblDatoOrdPrep.setSize(new Dimension(160, 15));
        lblDatoOrdPrep.setFocusable(false);
        lblDatoPaciente.setBounds(new Rectangle(430, 15, 55, 15));
        lblDatoPaciente.setSize(new Dimension(185, 15));
        lblDatoPaciente.setFocusable(false);
        lblDatoNumTelef.setBounds(new Rectangle(430, 40, 34, 14));
        lblDatoNumTelef.setSize(new Dimension(185, 15));
        lblDatoNumTelef.setFocusable(false);
        lblDatoMedico.setBounds(new Rectangle(430, 65, 40, 14));
        lblDatoMedico.setSize(new Dimension(185, 15));
        lblDatoMedico.setFocusable(false);
        lblDatoFecHoraEntrega.setBounds(new Rectangle(430, 90, 185, 15));
        lblDatoFecHoraEntrega.setSize(new Dimension(185, 15));
        lblDatoFecHoraEntrega.setFocusable(false);
        lblDatoLocalEntrega.setBounds(new Rectangle(430, 115, 40, 14));
        lblDatoLocalEntrega.setSize(new Dimension(185, 15));
        lblDatoLocalEntrega.setFocusable(false);
        lblDatoEstado.setBounds(new Rectangle(430, 140, 40, 14));
        lblDatoEstado.setSize(new Dimension(185, 15));
        lblDatoEstado.setFocusable(false);
        lblDatoNumPedido.setBounds(new Rectangle(430, 165, 40, 14));
        lblDatoNumPedido.setSize(new Dimension(185, 15));
        lblDatoNumPedido.setFocusable(false);
        lblDatoCantPrep.setBounds(new Rectangle(135, 115, 34, 14));
        lblDatoCantPrep.setSize(new Dimension(160, 15));
        lblDatoCantPrep.setFocusable(false);
        lblCantPrep.setText("Cant. Preparados:");
        lblCantPrep.setBounds(new Rectangle(25, 115, 110, 15));
        lblCantPrep.setSize(new Dimension(110, 15));
        lblCantPrep.setFocusable(false);
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(sclDetalle, null);
        pnlTitleDetalle.add(lblTitleDetalle, null);
        pnlFondo.add(pnlTitleDetalle, null);
        pnlRecetarioCab.add(lblDatoCantPrep, null);
        pnlRecetarioCab.add(lblCantPrep, null);
        pnlRecetarioCab.add(lblDatoNumPedido, null);
        pnlRecetarioCab.add(lblDatoEstado, null);
        pnlRecetarioCab.add(lblDatoLocalEntrega, null);
        pnlRecetarioCab.add(lblDatoFecHoraEntrega, null);
        pnlRecetarioCab.add(lblDatoMedico, null);
        pnlRecetarioCab.add(lblDatoNumTelef, null);
        pnlRecetarioCab.add(lblDatoPaciente, null);
        pnlRecetarioCab.add(lblDatoOrdPrep, null);
        pnlRecetarioCab.add(lblDatoValBruto, null);
        pnlRecetarioCab.add(lblDatoFecha, null);
        pnlRecetarioCab.add(lblDatoCantCont, null);
        pnlRecetarioCab.add(lblDatoFormFarm, null);
        pnlRecetarioCab.add(lblDatoIndEsteril, null);
        pnlRecetarioCab.add(lblDatoNumRec, null);
        pnlRecetarioCab.add(lblNumPedido, null);
        pnlRecetarioCab.add(lblOrdPrep, null);
        pnlRecetarioCab.add(lblEstado, null);
        pnlRecetarioCab.add(lblLocalEntrega, null);
        pnlRecetarioCab.add(lblFecHoraEntrega, null);
        pnlRecetarioCab.add(lblValBruto, null);
        pnlRecetarioCab.add(lblFecha, null);
        pnlRecetarioCab.add(lblMedico, null);
        pnlRecetarioCab.add(lblNumTelef, null);
        pnlRecetarioCab.add(lblPaciente, null);
        pnlRecetarioCab.add(lblcantCont, null);
        pnlRecetarioCab.add(lblFormFarm, null);
        pnlRecetarioCab.add(lblIndEsteril, null);
        pnlRecetarioCab.add(lblNumRec, null);
        pnlFondo.add(pnlRecetarioCab, null);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(pnlTitle, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        sclDetalle.getViewport().add(tblDetalleRecetarios, null);
        FarmaUtility.centrarVentana(this);
    }


    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsRecetario.columnsListaDetalleRecetarios, ConstantsRecetario.defaultListaDetalleRecetarios,
                                    0);
        FarmaUtility.initSimpleList(tblDetalleRecetarios, tableModel,
                                    ConstantsRecetario.columnsListaDetalleRecetarios);
    }

    private void pnlFondo_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblDetalleRecetarios, null, 0);

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    public void setNumRecetario(String numRec) {
        this.num_recetario = numRec;
        cargarDetalle();
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void cargarDetalle() {
        //cargar detalle recetario magistral
        ArrayList<String> tmpArrayRow = null;
        ArrayList<ArrayList<String>> resultado = facadeRecetario.detalleReporteRecetario(this.num_recetario);
        for (int i = 0; resultado.size() > i; i++) {
            tmpArrayRow = new ArrayList<String>();

            lblDatoNumRec.setText(((ArrayList)resultado.get(i)).get(0).toString().trim());
            lblDatoIndEsteril.setText(((ArrayList)resultado.get(i)).get(1).toString().trim());
            lblDatoFormFarm.setText(((ArrayList)resultado.get(i)).get(2).toString().trim());
            lblDatoCantCont.setText(((ArrayList)resultado.get(i)).get(3).toString().trim());
            lblDatoCantPrep.setText(((ArrayList)resultado.get(i)).get(4).toString().trim());
            lblDatoFecha.setText(((ArrayList)resultado.get(i)).get(5).toString().trim());
            lblDatoPaciente.setText(((ArrayList)resultado.get(i)).get(6).toString().trim());
            lblDatoMedico.setText(((ArrayList)resultado.get(i)).get(7).toString().trim());
            lblDatoValBruto.setText(((ArrayList)resultado.get(i)).get(8).toString().trim());
            lblDatoOrdPrep.setText(((ArrayList)resultado.get(i)).get(9).toString().trim());
            lblDatoNumTelef.setText(((ArrayList)resultado.get(i)).get(10).toString().trim());
            lblDatoFecHoraEntrega.setText(((ArrayList)resultado.get(i)).get(11).toString().trim());
            lblDatoLocalEntrega.setText(((ArrayList)resultado.get(i)).get(12).toString().trim());
            lblDatoEstado.setText(((ArrayList)resultado.get(i)).get(13).toString().trim());
            lblDatoNumPedido.setText(((ArrayList)resultado.get(i)).get(14).toString().trim());
        }

        //cargar listado de detalle de recetario magistral
        tmpArrayRow = null;
        resultado = facadeRecetario.listadoDetalleRecetario(this.num_recetario);
        for (int i = 0; resultado.size() > i; i++) {
            tmpArrayRow = new ArrayList<String>();

            String tmpInsumo = ((ArrayList)resultado.get(i)).get(0).toString().trim();
            String tmpCantAten = ((ArrayList)resultado.get(i)).get(1).toString().trim();
            String tmpUnidad = ((ArrayList)resultado.get(i)).get(2).toString().trim();
            String tmpPrecUnit = ((ArrayList)resultado.get(i)).get(3).toString().trim();
            String tmpPrec = ((ArrayList)resultado.get(i)).get(4).toString().trim();

            tmpArrayRow.add(tmpInsumo == null ? " " : tmpInsumo);
            tmpArrayRow.add(tmpCantAten == null ? " " : tmpCantAten);
            tmpArrayRow.add(tmpUnidad == null ? " " : tmpUnidad);
            tmpArrayRow.add(tmpPrecUnit == null ? " " : tmpPrecUnit);
            tmpArrayRow.add(tmpPrec == null ? " " : tmpPrec);
            tableModel.insertRow(tmpArrayRow);
        }
        //Cuando se realiza una busqueda, si existe algun registro, se selecciona la primera fila
        if (tblDetalleRecetarios.getRowCount() > 0 && tblDetalleRecetarios.getSelectedRow() == -1)
            tblDetalleRecetarios.setRowSelectionInterval(0, 0);
    }

    private void this_windowOpened(WindowEvent e) {
        pnlFondo.grabFocus();
    }
}
