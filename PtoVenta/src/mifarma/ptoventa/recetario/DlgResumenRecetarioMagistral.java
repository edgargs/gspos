package mifarma.ptoventa.recetario;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recetario.reference.ConstantsRecetario;
import mifarma.ptoventa.recetario.reference.FacadeRecetario;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgResumenRecetarioMagistral.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      12.04.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgResumenRecetarioMagistral extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgResumenRecetarioMagistral.class);
    private FacadeRecetario facadeRecetario = new FacadeRecetario();

    private Frame myParentFrame;
    private FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel pnlTotalesD = new JPanel();
    private XYLayout xYLayout6 = new XYLayout();
    private JLabel lblTotalD = new JLabel();
    private JLabel lblTotalS = new JLabel();
    private JLabel lblTotalDT = new JLabel();
    private JLabel lblTotalST = new JLabel();
    private JPanel pnlTotalesT = new JPanel();
    private XYLayout xYLayout5 = new XYLayout();

    private JLabel lblPrepadadosT = new JLabel();
    private JLabel lblPrepadados = new JLabel();
    private JLabel lblContenidoT = new JLabel();
    private JLabel lblContenido = new JLabel();
    private JLabel lblFormaFarmaT = new JLabel();
    private JLabel lblFormaFarma = new JLabel();
    private JLabel lblEsterilT = new JLabel();
    private JLabel lblEsteril = new JLabel();

    private JScrollPane scrProductos = new JScrollPane();
    private JPanel pnlProductos = new JPanel();
    private XYLayout xYLayout2 = new XYLayout();
    private JButton btnRelacionProductos = new JButton();
    private JLabel lblItemsT = new JLabel();
    private JLabel lblItems = new JLabel();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF = new JLabelFunction();
    private JTable tblResumenInsumos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelWhite lblCliente_T = new JLabelWhite();
    private JLabelWhite lblCliente = new JLabelWhite();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();

    private JPanel pnlAtencion1 = new JPanel();
    private XYLayout xYLayout7 = new XYLayout();

    private JLabel lblOpcionesT = new JLabel();
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelWhite lblMedicoT = new JLabelWhite();
    private JLabelWhite lblMedico = new JLabelWhite();

    String strCodigoRecetario = "1";
    private Color clr_original;
    private Color clr_warning = Color.GRAY;

    private String costoFormFarm = "";
    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    /**
     * Constructor
     */
    public DlgResumenRecetarioMagistral() {
        this(null, "", false);
    }

    /**
     * Constructor
     * @param parent Objeto Frame de la Aplicación.
     * @param title Título de la Ventana.
     * @param modal Tipo de Ventana.
     */
    public DlgResumenRecetarioMagistral(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initializeTable();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    /**
     * Implementa la Ventana con todos sus Objetos
     */
    private void jbInit() throws Exception {
        this.setSize(new Dimension(783, 472));
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setTitle("Resumen de Recetario Magistral");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.setBackground(Color.white);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        jContentPane.setLayout(null);
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(742, 423));
        jContentPane.setForeground(Color.white);
        pnlTotalesD.setFont(new Font("SansSerif", 0, 12));
        pnlTotalesD.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTotalesD.setLayout(xYLayout6);
        pnlTotalesD.setBounds(new Rectangle(10, 310, 760, 35));
        pnlTotalesD.setBackground(new Color(43, 141, 39));
        lblTotalD.setText("0.00");
        lblTotalD.setFont(new Font("SansSerif", 1, 14));
        lblTotalD.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalD.setForeground(Color.white);
        lblTotalD.setVisible(true);
        lblTotalS.setFont(new Font("SansSerif", 1, 14));
        lblTotalS.setForeground(Color.white);
        lblTotalS.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalS.setText("0.00");
        lblTotalDT.setText("US$");
        lblTotalDT.setFont(new Font("SansSerif", 1, 14));
        lblTotalDT.setForeground(Color.white);
        lblTotalST.setText("TOTAL: S/.");
        lblTotalST.setFont(new Font("SansSerif", 1, 14));
        lblTotalST.setForeground(Color.white);
        pnlTotalesT.setFont(new Font("SansSerif", 0, 12));
        pnlTotalesT.setBackground(new Color(255, 130, 14));
        pnlTotalesT.setLayout(xYLayout5);
        pnlTotalesT.setSize(new Dimension(560, 25));
        pnlTotalesT.setBounds(new Rectangle(10, 285, 760, 25));

        lblPrepadadosT.setText("Preparados :");
        lblPrepadadosT.setFont(new Font("SansSerif", 1, 12));
        lblPrepadadosT.setForeground(Color.white);
        lblPrepadados.setText("0");
        lblPrepadados.setFont(new Font("SansSerif", 1, 12));
        lblPrepadados.setForeground(Color.white);
        lblPrepadados.setHorizontalAlignment(SwingConstants.LEFT);
        lblContenidoT.setText("Contenido :");
        lblContenidoT.setFont(new Font("SansSerif", 1, 12));
        lblContenidoT.setForeground(Color.white);
        lblContenido.setText("0");
        lblContenido.setFont(new Font("SansSerif", 1, 12));
        lblContenido.setForeground(Color.white);
        lblContenido.setHorizontalAlignment(SwingConstants.LEFT);
        lblFormaFarmaT.setText("Forma Farmacéutica :");
        lblFormaFarmaT.setFont(new Font("SansSerif", 1, 12));
        lblFormaFarmaT.setForeground(Color.white);
        lblFormaFarma.setText("");
        lblFormaFarma.setFont(new Font("SansSerif", 1, 12));
        lblFormaFarma.setForeground(Color.white);
        lblFormaFarma.setHorizontalAlignment(SwingConstants.LEFT);
        lblEsterilT.setText("Esteríl :");
        lblEsterilT.setFont(new Font("SansSerif", 1, 12));
        lblEsterilT.setForeground(Color.white);
        lblEsteril.setText("");
        lblEsteril.setFont(new Font("SansSerif", 1, 12));
        lblEsteril.setForeground(Color.white);
        lblEsteril.setHorizontalAlignment(SwingConstants.LEFT);

        scrProductos.setFont(new Font("SansSerif", 0, 12));
        scrProductos.setBounds(new Rectangle(10, 75, 760, 210));
        scrProductos.setBackground(new Color(255, 130, 14));
        pnlProductos.setFont(new Font("SansSerif", 0, 12));
        pnlProductos.setLayout(xYLayout2);
        pnlProductos.setBackground(new Color(255, 130, 14));
        pnlProductos.setBounds(new Rectangle(10, 50, 760, 25));
        btnRelacionProductos.setText("Relacion de Insumos :");
        btnRelacionProductos.setFont(new Font("SansSerif", 1, 11));
        btnRelacionProductos.setForeground(Color.white);
        btnRelacionProductos.setBackground(new Color(255, 130, 14));
        btnRelacionProductos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnRelacionProductos.setHorizontalAlignment(SwingConstants.LEFT);
        btnRelacionProductos.setRequestFocusEnabled(false);
        btnRelacionProductos.setMnemonic('r');
        btnRelacionProductos.setBorderPainted(false);
        btnRelacionProductos.setContentAreaFilled(false);
        btnRelacionProductos.setDefaultCapable(false);
        btnRelacionProductos.setFocusPainted(false);
        btnRelacionProductos.setActionCommand("Relacion de Insumos :");
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
        lblItemsT.setText("items");
        lblItemsT.setFont(new Font("SansSerif", 1, 11));
        lblItemsT.setForeground(Color.white);
        lblItems.setText("0");
        lblItems.setFont(new Font("SansSerif", 1, 11));
        lblItems.setForeground(Color.white);
        lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
        lblF3.setText("[ F3 ]  Agregar Insumo");
        lblF3.setBounds(new Rectangle(10, 380, 165, 20));
        lblF11.setText("[ F11 ]  Grabar");
        lblF11.setBounds(new Rectangle(640, 380, 130, 20));
        lblF.setText("[ F4 ]  Forma Farmacéutica");
        lblF.setBounds(new Rectangle(180, 380, 160, 20));
        tblResumenInsumos.setFont(new Font("SansSerif", 0, 12));
        tblResumenInsumos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblResumenInsumos_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(640, 410, 130, 20));
        pnlTitle1.setBounds(new Rectangle(10, 5, 760, 30));
        lblCliente_T.setText("Cliente:");
        lblCliente_T.setBounds(new Rectangle(15, 5, 55, 20));
        lblCliente_T.setFont(new Font("SansSerif", 1, 14));
        lblCliente.setBounds(new Rectangle(75, 5, 285, 20));
        lblCliente.setFont(new Font("SansSerif", 1, 14));
        lblF9.setBounds(new Rectangle(495, 380, 140, 20));
        lblF9.setText("[ F9 ] Ingreso Datos");
        lblEnter.setBounds(new Rectangle(10, 410, 165, 20));
        lblEnter.setText("[ ENTER ] Cambiar Cantidad");
        pnlAtencion1.setFont(new Font("SansSerif", 0, 11));
        pnlAtencion1.setLayout(xYLayout7);
        pnlAtencion1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlAtencion1.setBackground(new Color(43, 141, 39));

        lblOpcionesT.setText("Opciones");
        lblOpcionesT.setBounds(new Rectangle(15, 355, 70, 20));
        lblOpcionesT.setFont(new Font("SansSerif", 1, 11));

        pnlCliente.setBounds(new Rectangle(0, 0, 365, 30));
        lblF5.setText("[ F5 ]  Borrar Insumo");
        lblF5.setBounds(new Rectangle(345, 380, 145, 20));
        lblMedicoT.setText("Medico:");
        lblMedicoT.setBounds(new Rectangle(375, 5, 65, 20));
        lblMedicoT.setFont(new Font("SansSerif", 1, 14));
        lblMedico.setFont(new Font("SansSerif", 1, 14));
        lblMedico.setBounds(new Rectangle(435, 5, 310, 20));

        lblMedico.setSize(new Dimension(350, 20));
        pnlTotalesD.add(lblTotalD, new XYConstraints(634, 9, 105, 20));
        pnlTotalesD.add(lblTotalS, new XYConstraints(444, 9, 115, 20));
        pnlTotalesD.add(lblTotalDT, new XYConstraints(589, 9, 35, 20));
        pnlTotalesD.add(lblTotalST, new XYConstraints(354, 9, 80, 20));

        pnlTotalesT.add(lblPrepadadosT, new XYConstraints(630, 5, 80, 20));
        pnlTotalesT.add(lblPrepadados, new XYConstraints(710, 5, 40, 20));
        pnlTotalesT.add(lblContenidoT, new XYConstraints(465, 5, 70, 20));
        pnlTotalesT.add(lblContenido, new XYConstraints(535, 5, 40, 20));
        pnlTotalesT.add(lblFormaFarmaT, new XYConstraints(130, 5, 130, 20));
        pnlTotalesT.add(lblFormaFarma, new XYConstraints(260, 5, 185, 20));
        pnlTotalesT.add(lblEsterilT, new XYConstraints(10, 5, 45, 20));
        pnlTotalesT.add(lblEsteril, new XYConstraints(60, 5, 55, 20));
        scrProductos.getViewport();
        pnlProductos.add(pnlAtencion1, new XYConstraints(10, 45, 715, 35));
        pnlProductos.add(btnRelacionProductos, new XYConstraints(10, 5, 145, 15));
        pnlProductos.add(lblItemsT, new XYConstraints(185, 5, 40, 15));
        pnlProductos.add(lblItems, new XYConstraints(150, 5, 30, 15));
        pnlCliente.add(lblCliente_T, null);
        pnlCliente.add(lblCliente, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(pnlTotalesT, null);
        jContentPane.add(lblOpcionesT, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTotalesD, null);
        scrProductos.getViewport().add(tblResumenInsumos, null);
        jContentPane.add(scrProductos, null);
        jContentPane.add(pnlProductos, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblF, null);
        pnlTitle1.add(lblMedicoT, null);
        pnlTitle1.add(pnlCliente, null);
        pnlTitle1.add(lblMedico, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

        clr_original = lblF.getBackground();
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initializeTable() {
        tableModel =
                new FarmaTableModel(ConstantsRecetario.columnsListaRecetariosMagistral, ConstantsRecetario.defaultListaRecetariosMagistral,
                                    0);
        FarmaUtility.initSimpleList(tblResumenInsumos, tableModel, ConstantsRecetario.columnsListaRecetariosMagistral);
        chkKeyPressed(null);
        VariablesRecetario.strCodigoRecetario = "";
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void llenarTablaInsumos() {
        ArrayList<String> tmpArrayRow = null;
        tableModel.data = new ArrayList<Object>();
        for (int i = 0; VariablesRecetario.vArrayInsumosSeleccionados.size() > i; i++) {
            tmpArrayRow = new ArrayList<String>();
            String tmpCodigoInsu = (VariablesRecetario.vArrayInsumosSeleccionados.get(i)).get(1).toString().trim();
            String tmpDescInsu = (VariablesRecetario.vArrayInsumosSeleccionados.get(i)).get(2).toString().trim();
            String tmpUnidVentaInsu = (VariablesRecetario.vArrayInsumosSeleccionados.get(i)).get(3).toString().trim();
            String tmpPrecioInsu = (VariablesRecetario.vArrayInsumosSeleccionados.get(i)).get(4).toString().trim();
            String tmpCantidadInsu = (VariablesRecetario.vArrayInsumosSeleccionados.get(i)).get(5).toString().trim();
            String tmpCantidadInsuOrig =
                (VariablesRecetario.vArrayInsumosSeleccionados.get(i)).get(6).toString().trim();
            String tmpPorcentaje = (VariablesRecetario.vArrayInsumosSeleccionados.get(i)).get(7).toString().trim();
            String tmpCodUnidVentaInsu =
                (VariablesRecetario.vArrayInsumosSeleccionados.get(i)).get(8).toString().trim();
            String tmpDescUnidVentaInsu =
                (VariablesRecetario.vArrayInsumosSeleccionados.get(i)).get(9).toString().trim();
            double tmpFactorConversion = facadeRecetario.getFactorConversion(tmpCodigoInsu, tmpCodUnidVentaInsu);

            double tmpSubTotal = Double.parseDouble(tmpPrecioInsu) * Double.parseDouble(tmpCantidadInsuOrig);
            tmpSubTotal = tmpSubTotal * tmpFactorConversion;

            if (tmpPorcentaje != null && !tmpPorcentaje.equals(""))
                tmpSubTotal = tmpSubTotal * (Double.parseDouble(tmpPorcentaje) / 100);

            tmpArrayRow.add(tmpCodigoInsu);
            tmpArrayRow.add(tmpDescInsu);
            tmpArrayRow.add(tmpUnidVentaInsu);
            tmpArrayRow.add(tmpPrecioInsu);
            tmpArrayRow.add(tmpCantidadInsu);
            tmpArrayRow.add(tmpPorcentaje);
            tmpArrayRow.add(tmpCantidadInsuOrig);
            tmpArrayRow.add(FarmaUtility.formatNumber(tmpSubTotal, 6));
            tmpArrayRow.add(tmpCodUnidVentaInsu);
            tmpArrayRow.add(tmpDescUnidVentaInsu);
            tmpArrayRow.add(tmpCodUnidVentaInsu);
            tmpArrayRow.add(tmpDescUnidVentaInsu);

            tableModel.insertRow(tmpArrayRow);
        }
        //se calcula el total de acuerdo a la suma
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void tblResumenInsumos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnRelacionProductos_keyPressed(KeyEvent e) {
        FarmaUtility.moveFocusJTable(tblResumenInsumos);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblResumenInsumos);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocusJTable(tblResumenInsumos);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e != null) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                cambiarCantidadInsumo();
            } else if (e.getKeyCode() == KeyEvent.VK_F3) {
                mostrarListaInsumos();
            } else if (e.getKeyCode() == KeyEvent.VK_F4) {
                mostrarFormaFarmac();
            } else if (e.getKeyCode() == KeyEvent.VK_F5) {
                eliminarRegistroTable();
            } else if (e.getKeyCode() == KeyEvent.VK_F9) {
                mostrarDatosPacienMed();
            } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e) && validarBotonGrabar()) {
                grabarRecetarioMedico();
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarVentana(false);
            }
        }
        lblF11.setEnabled(validarBotonGrabar());
    }

    private void eliminarRegistroTable() {
        int tmpRowSelected = tblResumenInsumos.getSelectedRow();
        if (tmpRowSelected >= 0) {
            String codTemp = tblResumenInsumos.getValueAt(tmpRowSelected, 0).toString();
            tableModel.deleteRow(tmpRowSelected);
            lblItems.setText(tblResumenInsumos.getRowCount() + "");
            for (int i = 0; i < VariablesRecetario.vArrayInsumosSeleccionados.size(); i++) {
                ArrayList<Object> temp = VariablesRecetario.vArrayInsumosSeleccionados.get(i);
                if (temp.get(1).toString().equals(codTemp)) {
                    VariablesRecetario.vArrayInsumosSeleccionados.remove(i);
                }
            }
        } else {
            FarmaUtility.showMessage(this, "Debe seleccionar un registro.", null);
        }
        calcularTotal();
    }

    private void cambiarCantidadInsumo() {
        if (tblResumenInsumos.getRowCount() > 0) {
            int tmpRowSelec = tblResumenInsumos.getSelectedRow();
            String tmpCodInsumo = FarmaUtility.getValueFieldJTable(tblResumenInsumos, tmpRowSelec, 0);
            double tmPrecioInsumo =
                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblResumenInsumos, tmpRowSelec, 3));
            double tmpNewPrecioTotal = 0.0;
            DlgIngresoCantidadInsumo dlgIngresoCantidadInsumo = new DlgIngresoCantidadInsumo(myParentFrame, "", true);
            dlgIngresoCantidadInsumo.setFacade(facadeRecetario);
            dlgIngresoCantidadInsumo.setCodInsumo(tmpCodInsumo);
            dlgIngresoCantidadInsumo.cargarDatosInsumo(FarmaUtility.getValueFieldJTable(tblResumenInsumos, tmpRowSelec,
                                                                                        6), //cant
                    FarmaUtility.getValueFieldJTable(tblResumenInsumos, tmpRowSelec, 9), //DescUnidMedida
                    FarmaUtility.getValueFieldJTable(tblResumenInsumos, tmpRowSelec, 5)); //porc);
            dlgIngresoCantidadInsumo.setVisible(true);
            if (FarmaVariables.vAceptar) { //tmpNewPrecioTotal = tmPrecioInsumo * FarmaUtility.getDecimalNumber(VariablesRecetario.vCantInsumosCodSelec);
                //tblResumenInsumos.setValueAt(VariablesRecetario.vCantInsumosCodSelec, tmpRowSelec, 4);
                //tblResumenInsumos.setValueAt(FarmaUtility.formatNumber(tmpNewPrecioTotal), tmpRowSelec, 5);
                if (VariablesRecetario.vArrayInsumosSeleccionados.size() > 0) { //llenarTablaInsumos();
                    actualizar_fila(tmpRowSelec, dlgIngresoCantidadInsumo);
                    FarmaUtility.moveFocus(tblResumenInsumos);
                }
            }
            llenarTablaInsumos();
            calcularTotal();
            tblResumenInsumos.updateUI();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        VariablesRecetario.vArrayInsumosSeleccionados = null;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void mostrarListaInsumos() {
        DlgListaInsumos dlgListaInsumos = new DlgListaInsumos(myParentFrame, "", true);
        dlgListaInsumos.setFacade(facadeRecetario);
        dlgListaInsumos.setVisible(true);
        if (FarmaVariables.vAceptar) { //if( VariablesRecetario.vArrayInsumosSeleccionados.size() > 0 )
            //{
            llenarTablaInsumos();
            FarmaUtility.moveFocus(tblResumenInsumos);
            //}
        }
        tblResumenInsumos.repaint();
        //lblItems.setText(tblResumenInsumos.getRowCount()+"");
        calcularTotal();
    }

    private void mostrarFormaFarmac() {
        DlgFormaFarmaceutica dlgFormaFarmac = new DlgFormaFarmaceutica(myParentFrame, "", true);
        dlgFormaFarmac.setFacade(facadeRecetario);
        dlgFormaFarmac.setVisible(true);
        if (FarmaVariables.vAceptar) {
            ArrayList<Object> tmpArrayEsteril;
            tmpArrayEsteril = VariablesRecetario.vArrayEsteril;
            lblEsteril.setText(tmpArrayEsteril.get(0).toString());
            lblFormaFarma.setText(tmpArrayEsteril.get(1).toString());
            lblContenido.setText(tmpArrayEsteril.get(2).toString());
            lblPrepadados.setText(tmpArrayEsteril.get(3).toString());
            this.costoFormFarm = tmpArrayEsteril.get(8).toString();
        }
        calcularTotal();
    }

    private void mostrarDatosPacienMed() {
        DlgDatosPacienteMedico dlgindpacmed = new DlgDatosPacienteMedico(myParentFrame, "", true);
        dlgindpacmed.setVisible(true);
        if (FarmaVariables.vAceptar) {
            Map<String, String> tmpMapDatos = new HashMap<String, String>();
            tmpMapDatos = VariablesRecetario.vMapDatosPacienteMedico;
            lblCliente.setText(tmpMapDatos.get("PACIENTE"));
            lblMedico.setText(tmpMapDatos.get("MEDICO"));
        }
    }

    private void calcularTotal() {
        double tmpMontoTotal = 0.0;
        double tmpMontoDol = 0.0;

        tmpMontoTotal = facadeRecetario.calcularTotales(tableModel.data, lblPrepadados.getText(), this.costoFormFarm);
        tmpMontoDol = tmpMontoTotal / FarmaVariables.vTipCambio;

        lblTotalS.setText(FarmaUtility.formatNumber(tmpMontoTotal));
        lblTotalD.setText(FarmaUtility.formatNumber(tmpMontoDol));
        lblItems.setText(tblResumenInsumos.getRowCount() + "");
    }

    private void grabarRecetarioMedico() {
        if (((VariablesRecetario.vMapDatosPacienteMedico).size() > 0) &
            ((VariablesRecetario.vArrayEsteril).size() > 0) &
            ((VariablesRecetario.vArrayInsumosSeleccionados).size() > 0)) {
            //log.debug("vArrayDatosPacienteMedico");
            //log.debug(VariablesRecetario.vArrayDatosPacienteMedico);
            //log.debug("vArrayEsteril");
            //log.debug(VariablesRecetario.vArrayEsteril);
            //log.debug("vArrayInsumosSeleccionados");
            //log.debug(VariablesRecetario.vArrayInsumosSeleccionados);
            if (FarmaUtility.getDecimalNumber(VariablesRecetario.vValVenta) > 0)
                grabarCabeceraRM();
            else
                FarmaUtility.showMessage(this, "El total no puede ser 0.00", null);
        } else {
            FarmaUtility.showMessage(this, "Falta ingresar datos.", null);
        }
    }

    /**
     * Guarda los datos completos del recetario magistral
     * @author LLEIVA
     * @since 28.05.2013
     */
    private boolean validarBotonGrabar() {
        boolean flag_ff = true;
        boolean flag_id = true;
        boolean flag_ai = true;
        //se valida datos de boton "Forma Farmaceutica"
        if (VariablesRecetario.vArrayEsteril == null) {
            lblF.setBackground(clr_warning);
            flag_ff = false;
        } else
            lblF.setBackground(clr_original);
        //se valida datos de boton "Ingreso datos"
        if (VariablesRecetario.vMapDatosPacienteMedico == null) {
            lblF9.setBackground(clr_warning);
            flag_id = false;
        } else
            lblF9.setBackground(clr_original);
        //se valida datos de boton "Agregar Insumo"
        if (tableModel == null || tableModel.data == null || (tableModel.data.size() == 0)) {
            lblF3.setBackground(clr_warning);
            flag_ai = false;
        } else
            lblF3.setBackground(clr_original);
        return (flag_ff && flag_id && flag_ai);
    }

    /**
     * Guarda los datos completos del recetario magistral
     * @author LLEIVA
     * @since 28.05.2013
     */
    private void grabarCabeceraRM() { //se llenan los datos de la cabecera
        ArrayList<String> tmpArrayCab = new ArrayList<String>();
        tmpArrayCab.add(FarmaVariables.vCodGrupoCia);
        tmpArrayCab.add(FarmaVariables.vCodCia);
        tmpArrayCab.add(FarmaVariables.vCodLocal);
        tmpArrayCab.add((VariablesRecetario.vArrayEsteril).get(4).toString());
        tmpArrayCab.add((VariablesRecetario.vArrayEsteril).get(5).toString());
        tmpArrayCab.add((VariablesRecetario.vArrayEsteril).get(2).toString());
        tmpArrayCab.add((VariablesRecetario.vArrayEsteril).get(3).toString());
        tmpArrayCab.add(facadeRecetario.getDate());
        tmpArrayCab.add(VariablesRecetario.vMapDatosPacienteMedico.get("DNI"));
        tmpArrayCab.add(VariablesRecetario.vMapDatosPacienteMedico.get("CMP"));
        tmpArrayCab.add(VariablesRecetario.vMapDatosPacienteMedico.get("FECHA"));
        tmpArrayCab.add(VariablesRecetario.vMapDatosPacienteMedico.get("HORA"));
        tmpArrayCab.add(VariablesRecetario.vMapDatosPacienteMedico.get("LOCAL"));
        tmpArrayCab.add(VariablesRecetario.vMapDatosPacienteMedico.get("OBSERVACION"));

        Double temp =
            FarmaUtility.getDecimalNumber(VariablesRecetario.vValVenta) - FarmaUtility.getDecimalNumber(VariablesRecetario.vValIgv);
        tmpArrayCab.add(temp.toString());
        tmpArrayCab.add("A");
        tmpArrayCab.add(FarmaVariables.vIdUsu);
        tmpArrayCab.add(facadeRecetario.getDate());
        tmpArrayCab.add(VariablesRecetario.vMapDatosPacienteMedico.get("TELEFONO"));
        //guardar num guia
        tmpArrayCab.add("");
        tmpArrayCab.add(VariablesRecetario.vValIgv);
        tmpArrayCab.add(VariablesRecetario.vValVenta);
        tmpArrayCab.add(VariablesRecetario.vValVentaSinRed);

        //se llenan los datos del detalle
        ArrayList<ArrayList<String>> tmpArrayDetalle = new ArrayList<ArrayList<String>>();
        ArrayList<String> tmpArrayRow = null;
        for (int i = 0; i < tableModel.data.size(); i++) {
            tmpArrayRow = new ArrayList<String>();
            tmpArrayRow.add(FarmaVariables.vCodGrupoCia);
            tmpArrayRow.add(FarmaVariables.vCodCia);
            tmpArrayRow.add(FarmaVariables.vCodLocal);
            //tmpArrayRow.add(strCodigoRecetario);
            tmpArrayRow.add(((ArrayList)(tableModel.data).get(i)).get(0).toString().trim());
            tmpArrayRow.add(((ArrayList)(tableModel.data).get(i)).get(4).toString().trim());
            tmpArrayRow.add(((ArrayList)(tableModel.data).get(i)).get(3).toString().trim());
            tmpArrayRow.add(((ArrayList)(tableModel.data).get(i)).get(7).toString().trim());
            tmpArrayRow.add(FarmaVariables.vIdUsu);
            tmpArrayRow.add(facadeRecetario.getDate());
            tmpArrayRow.add(((ArrayList)(tableModel.data).get(i)).get(8).toString().trim());
            tmpArrayRow.add(((ArrayList)(tableModel.data).get(i)).get(5).toString().trim());
            tmpArrayDetalle.add(tmpArrayRow);
        }

        strCodigoRecetario = facadeRecetario.grabarCabeceraRM(tmpArrayCab, tmpArrayDetalle);
        log.debug("=> " + strCodigoRecetario);

        if (strCodigoRecetario != null) {
            FarmaUtility.showMessage(this, "Se grabo correctamente el recetario magistral", null);
            facadeRecetario.resetearVariablesRecetario();
            VariablesRecetario.strCodigoRecetario = strCodigoRecetario;
            VariablesRecetario.strPrecioTotal = lblTotalS.getText().trim();
            VariablesRecetario.strCant_Recetario = "1";
            VariablesRecetario.strAccion = "C";
            cerrarVentana(true);
        } else
            FarmaUtility.showMessage(this, "ERROR: No se pudo grabar el recetario magistral", null);
    }

    /**
     * Actualiza el registro modificado en el recetario magistral
     * @author LLEIVA
     * @since 10.06.2013
     */
    private void actualizar_fila(Integer fila, DlgIngresoCantidadInsumo dici) {
        ArrayList<Object> temp = VariablesRecetario.vArrayInsumosSeleccionados.get(fila);

        temp.set(5, VariablesRecetario.vCantInsumosCodSelec);
        temp.set(6, dici.getCantidadIngresada());
        temp.set(7, dici.getPorcentaje());
        temp.set(8, dici.getCodUnidMedidaElegida());
        temp.set(9, dici.getDescUnidMedidaElegida());

        VariablesRecetario.vArrayInsumosSeleccionados.set(fila, temp);
    }
}
