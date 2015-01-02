package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMedicoCampana extends JDialog {

    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */
    private static final Logger log = LoggerFactory.getLogger(DlgMedicoCampana.class);

    Frame myParentFrame;
    private FarmaTableModel tableModelListaMedico;
    private final int COL_COD = 0;
    private final int COL_DESC = 1;
    private final int COL_PREC_PACK = 2;
    private final int COL_STK_PACK = 3;
    private final int COL_IND_PERMITIDO = 5;
    private final int COL_DESL = 6;
    private final int COL_CODP1 = 7;
    private final int COL_CODP2 = 8;
    private final int COL_DESC_LARGA = 9;

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelHeader pnlCliente1 = new JPanelHeader();
    private JTextFieldSanSerif txtMedico = new JTextFieldSanSerif();
    private JButtonLabel btnMedico = new JButtonLabel();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelFunction lblAceptar = new JLabelFunction();
    private JButtonLabel btnListado = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblMedico = new JTable();
    private JLabelFunction lblAceptar1 = new JLabelFunction();

    private ArrayList listMedNuevo = new ArrayList();
    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */

    public DlgMedicoCampana(ArrayList listaMedico) {
        this(null, "", false, listaMedico);
    }

    public DlgMedicoCampana(Frame parent, String title, boolean modal, ArrayList listaMedico) {
        super(parent, title, modal);
        myParentFrame = parent;
        listMedNuevo = (ArrayList)(listaMedico.clone());
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
        this.setSize(new Dimension(628, 317));
        this.setTitle("Búsqueda Médico");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlCliente1.setBounds(new Rectangle(10, 10, 600, 40));
        txtMedico.setBounds(new Rectangle(55, 10, 405, 20));
        txtMedico.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtPromocion_keyReleased(e);

            }

            public void keyPressed(KeyEvent e) {
                txtPromocion_keyPressed(e);
            }
        });
        tblMedico.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblPromociones_keyPressed(e);
            }
        });
        lblAceptar1.setBounds(new Rectangle(515, 265, 95, 20));
        lblAceptar1.setText("[ESC] Cerrar ");

        btnMedico.setText("Médico");
        btnMedico.setBounds(new Rectangle(20, 20, 65, 20));
        btnMedico.setMnemonic('P');
        btnMedico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPromocion_actionPerformed(e);
                // btnEmpresa_actionPerformed(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 60, 600, 25));
        jPanelTitle1.setLayout(null);
        lblAceptar.setBounds(new Rectangle(395, 265, 110, 20));
        lblAceptar.setText("[ENTER] Aceptar");
        btnListado.setText("Búsqueda de Médico");
        btnListado.setBounds(new Rectangle(5, 0, 145, 25));
        btnListado.setMnemonic('L');
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(10, 85, 600, 170));
        jScrollPane1.getViewport().add(tblMedico, null);
        jPanelWhite1.add(lblAceptar1, null);
        jPanelWhite1.add(jScrollPane1, null);
        jPanelWhite1.add(lblAceptar, null);
        jPanelTitle1.add(btnListado, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelWhite1.add(btnMedico, null);
        pnlCliente1.add(txtMedico, null);
        jPanelWhite1.add(pnlCliente1, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        VariablesVentas.ACCION = VariablesVentas.ACCION_CREAR;
        FarmaVariables.vAceptar = false;
        initTableListaBusquedaMedico();

    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaBusquedaMedico() {
        tableModelListaMedico =
                new FarmaTableModel(ConstantsVentas.columnsListaBusquedaMedico, ConstantsVentas.defaultValuesListaBusquedaMedico,
                                    COL_COD);
        FarmaUtility.initSimpleList(tblMedico, tableModelListaMedico, ConstantsVentas.columnsListaBusquedaMedico);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtMedico);
        ConstantsVentas.ESTADO_LISTADO = "P";
        listarBusquedaMedico(listMedNuevo);
        FarmaUtility.setearPrimerRegistro(tblMedico, txtMedico, COL_DESC);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtMedico);
        ConstantsVentas.ESTADO_LISTADO = "P";
    }


    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            ConstantsVentas.ESTADO_LISTADO = "P";
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER)
            cargaMedico();

    }

    private void txtPromocion_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblMedico, txtMedico, COL_DESC);
        chkKeyPressed(e);
    }

    private void txtPromocion_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblMedico, txtMedico, 1);
    }

    private void btnPromocion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMedico);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblMedico);
    }

    private void tblPromociones_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    /**
     * Se lista todas las Promociones en las que participa es un producto
     */
    private void listarBusquedaMedico(ArrayList lista) {
        tableModelListaMedico.clearTable();
        Map map = new HashMap();
        /*
        NUM_CMP,
        NOMBRE,
        DESC_TIP_COLEGIO,
        TIPO_COLEGIO,
        COD_MEDICO
        * */
        String pNUM_CMP = "";
        String pNOMBRE = "";
        String pDESC_TIP_COLEGIO = "";
        String pTIPO_COLEGIO = "";
        String pCOD_MEDICO = "";

        for (int i = 0; i < lista.size(); i++) {
            ArrayList fila = new ArrayList();
            map = (HashMap)lista.get(i);
            pNUM_CMP = ((String)map.get("NUM_CMP")).trim();
            pNOMBRE = ((String)map.get("NOMBRE")).trim();
            pDESC_TIP_COLEGIO = ((String)map.get("DESC_TIP_COLEGIO")).trim();
            pTIPO_COLEGIO = ((String)map.get("TIPO_COLEGIO")).trim();
            pCOD_MEDICO = ((String)map.get("COD_MEDICO")).trim();
            fila.add(pNUM_CMP);
            fila.add(pNOMBRE);
            fila.add(pDESC_TIP_COLEGIO);
            fila.add(pTIPO_COLEGIO);
            fila.add(pCOD_MEDICO);
            tableModelListaMedico.insertRow(fila);
        }
        tblMedico.repaint();
    }

    private void cargaMedico() {
        int i = tblMedico.getSelectedRow();

        if (i >= 0) {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "¿Está seguro de seleccionar al médico?")) {
                ///////////////////////////////////////////////
                VariablesFidelizacion.V_NUM_CMP = tableModelListaMedico.getValueAt(i, 0).toString();
                VariablesFidelizacion.V_NOMBRE = tableModelListaMedico.getValueAt(i, 1).toString();
                VariablesFidelizacion.V_DESC_TIP_COLEGIO = tableModelListaMedico.getValueAt(i, 2).toString();
                VariablesFidelizacion.V_OLD_TIPO_COLEGIO = VariablesFidelizacion.V_TIPO_COLEGIO;
                VariablesFidelizacion.V_TIPO_COLEGIO = tableModelListaMedico.getValueAt(i, 3).toString();
                VariablesFidelizacion.V_COD_MEDICO = tableModelListaMedico.getValueAt(i, 4).toString();
                cerrarVentana(true);
                ///////////////////////////////////////////////
            }
        } else {
            FarmaUtility.showMessage(this, "Debe de seleccionar un medico de la lista", txtMedico);
        }
    }
}
