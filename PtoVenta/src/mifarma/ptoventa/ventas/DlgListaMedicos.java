package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaMedicos extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaMedicos.class);

    private Frame myParentFrame;
    FarmaTableModel tableModelListaMedicos;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JTextFieldSanSerif txtNombreMedico = new JTextFieldSanSerif();
    private JButtonLabel btnNombre = new JButtonLabel();
    private JPanelTitle pnlRelacionCliente = new JPanelTitle();
    private JButtonLabel btnRelacion = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblMedicos = new JTable();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();

    public DlgListaMedicos() {
        this(null, "", false);
    }

    public DlgListaMedicos(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(466, 373));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Lista de Medicos");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlCliente.setBounds(new Rectangle(10, 10, 440, 40));
        txtNombreMedico.setBounds(new Rectangle(95, 10, 295, 20));
        txtNombreMedico.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNombreMedico_keyPressed(e);
                // txtClienteJuridico_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtNombreMedico_keyReleased(e);
                //txtClienteJuridico_keyReleased(e);
            }
        });
        btnNombre.setText("Nombre :");
        btnNombre.setBounds(new Rectangle(20, 10, 65, 20));
        btnNombre.setMnemonic('n');
        btnNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // btnClienteJuridico_actionPerformed(e);
            }
        });
        pnlRelacionCliente.setBounds(new Rectangle(10, 60, 440, 25));
        btnRelacion.setText("Relacion de Medicos ");
        btnRelacion.setBounds(new Rectangle(10, 5, 150, 15));
        btnRelacion.setMnemonic('r');
        btnRelacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // btnRelacion_actionPerformed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(10, 85, 440, 225));
        jLabelFunction1.setBounds(new Rectangle(180, 315, 140, 20));
        jLabelFunction1.setText("[ ENTER ] Seleccionar");
        jLabelFunction2.setBounds(new Rectangle(335, 315, 115, 20));
        jLabelFunction2.setText("[ ESC ] Cerrar");
        pnlCliente.add(txtNombreMedico, null);
        pnlCliente.add(btnNombre, null);
        pnlRelacionCliente.add(btnRelacion, null);
        jScrollPane1.getViewport().add(tblMedicos, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(jScrollPane1, null);
        jPanelWhite1.add(pnlRelacionCliente, null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        initTableListaMedicos();
        VariablesVentas.vSeleccionaMedico = false;
        FarmaVariables.vAceptar = false;
    }

    private void initTableListaMedicos() {
        tableModelListaMedicos =
                new FarmaTableModel(ConstantsVentas.columnsListaMedicos, ConstantsVentas.defaultValuesListaMedicos, 0);
        FarmaUtility.initSimpleList(tblMedicos, tableModelListaMedicos, ConstantsVentas.columnsListaMedicos);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cargaListaMedicos();
        FarmaUtility.moveFocus(txtNombreMedico);
    }

    private void cargaListaMedicos() {
        try {
            DBVentas.listaBusquedaMedicos(tableModelListaMedicos, VariablesVentas.vCodMed,
                                          VariablesVentas.vMatriculaApe, VariablesVentas.vTipoBusqueda);
            FarmaUtility.ordenar(tblMedicos, tableModelListaMedicos, 2, FarmaConstants.ORDEN_ASCENDENTE);
            if (tblMedicos.getRowCount() <= 0) {
                VariablesVentas.vSeleccionaMedico = false;
                FarmaUtility.showMessage(this, "No se encontró ningún médico para esta búsqueda.", txtNombreMedico);
                cerrarVentana(false);
            } else {
                FarmaGridUtils.showCell(tblMedicos, 0, 0);
                FarmaUtility.setearActualRegistro(tblMedicos, txtNombreMedico, 2);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurio un error al listar los medicos \n " + sql.getMessage(),
                                     txtNombreMedico);
        }
    }

    private void txtNombreMedico_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblMedicos, txtNombreMedico, 2);
    }

    private void guardaRegistroMedico() {
        if (tblMedicos.getRowCount() > 0) {
            VariablesVentas.vArrayList_Medicos.clear();
            VariablesVentas.vArrayList_Medicos.add(tableModelListaMedicos.data.get(tblMedicos.getSelectedRow()));
            log.debug("VariablesVentas.vArrayList_Medicos : " + VariablesVentas.vArrayList_Medicos);
        }
    }

    private void obtieneDatosMedico() {
        if (VariablesVentas.vArrayList_Medicos.size() == 1) {
            VariablesVentas.vSeleccionaMedico = true;
            VariablesVentas.vCodListaMed =
                    ((String)((ArrayList)VariablesVentas.vArrayList_Medicos.get(0)).get(0)).trim();
            VariablesVentas.vMatriListaMed =
                    ((String)((ArrayList)VariablesVentas.vArrayList_Medicos.get(0)).get(1)).trim();
            VariablesVentas.vNombreListaMed =
                    ((String)((ArrayList)VariablesVentas.vArrayList_Medicos.get(0)).get(2)).trim();
            log.debug("VariablesVentas.vCodListaMed : " + VariablesVentas.vCodListaMed);
            log.debug("VariablesVentas.vMatriListaMed : " + VariablesVentas.vMatriculaApe);
            log.debug("VariablesVentas.vNombreListaMed : " + VariablesVentas.vNombreListaMed);
        }
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            guardaRegistroMedico();
            obtieneDatosMedico();
            cerrarVentana(true);
            //muestraResumenReceta();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtNombreMedico_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblMedicos, txtNombreMedico, 2);
        chkKeyPressed(e);
    }

    /**
     * Se muestra el dialogo de resumen de receta.
     * @author Edgar Rios Navarro
     * @since 06.12.2006
     */
    private void muestraResumenReceta() {
        DlgResumenReceta dlgResumenReceta = new DlgResumenReceta(myParentFrame, "", true);
        dlgResumenReceta.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cerrarVentana(true);
        }
    }
}
