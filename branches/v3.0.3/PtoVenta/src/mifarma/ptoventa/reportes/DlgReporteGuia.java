package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgReporteGuia extends JDialog {
    private Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgReporteGuia.class);

    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JScrollPane jScrollPane1 = new JScrollPane();

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();

    private JPanelTitle pnlTitle = new JPanelTitle();

    private JLabelWhite lblTipoGuia = new JLabelWhite();
    private JLabelWhite lblTitle = new JLabelWhite();
    private JLabelWhite lblNumGuia = new JLabelWhite();
    private JLabelWhite lblCant = new JLabelWhite();
    private JLabelWhite lblFecha2 = new JLabelWhite();
    private JLabelWhite lblCantT = new JLabelWhite();

    private JTextFieldSanSerif txtFechaInicial = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFinal = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNumGuia = new JTextFieldSanSerif();

    private FarmaTableModel ftm_guia;
    private JTable tbl_guia = new JTable();


    public DlgReporteGuia() {
        this(null, "", false);
    }

    public DlgReporteGuia(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(657, 490));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Reporte de Guías");
        pnlFondo.setFocusable(false);
        pnlHeader.setBounds(new Rectangle(10, 15, 630, 85));
        pnlHeader.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 135, 630, 295));
        jScrollPane1.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(520, 435, 120, 25));
        lblEsc.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(10, 110, 630, 25));
        pnlTitle.setFocusable(false);
        lblTipoGuia.setText("Fecha:");
        lblTipoGuia.setBounds(new Rectangle(40, 15, 65, 20));
        lblTipoGuia.setFocusable(false);
        lblTitle.setText("Lista de Guias");
        lblTitle.setBounds(new Rectangle(25, 0, 240, 25));
        lblTitle.setFocusable(false);
        lblNumGuia.setText("Num. Guía:");
        lblNumGuia.setBounds(new Rectangle(40, 50, 65, 20));
        lblNumGuia.setFocusable(false);
        txtFechaInicial.setBounds(new Rectangle(130, 15, 90, 20));
        txtFechaInicial.setLengthText(10);
        txtFechaInicial.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaInicial_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaInicial_keyReleased(e);
            }
        });
        txtFechaFinal.setBounds(new Rectangle(255, 15, 90, 20));
        txtFechaFinal.setLengthText(10);
        txtFechaFinal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFinal_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFinal_keyReleased(e);
            }
        });
        txtNumGuia.setBounds(new Rectangle(130, 50, 220, 20));
        txtNumGuia.setLengthText(10);
        txtNumGuia.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumGuia_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtNumGuia_keyReleased(e);
            }
        });
        lblFecha2.setText("a");
        lblFecha2.setBounds(new Rectangle(235, 15, 20, 20));
        lblFecha2.setFocusable(false);
        lblF11.setText("[ F11 ] Buscar");
        lblF11.setBounds(new Rectangle(10, 435, 130, 25));
        lblF11.setFocusable(false);
        tbl_guia.setFocusable(false);
        lblCantT.setText("0");
        lblCantT.setBounds(new Rectangle(535, 0, 65, 25));
        lblCant.setText("Cant:");
        lblCant.setBounds(new Rectangle(485, 0, 45, 25));
        lblF5.setText("[ F5 ] Limpiar");
        lblF5.setBounds(new Rectangle(150, 435, 130, 25));
        jScrollPane1.getViewport().add(tbl_guia, null);
        pnlTitle.add(lblCantT, null);
        pnlTitle.add(lblCant, null);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(lblF5, null);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(pnlTitle, null);
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(jScrollPane1, null);
        pnlFondo.add(pnlHeader, null);
        pnlHeader.add(lblFecha2, null);
        pnlHeader.add(txtNumGuia, null);
        pnlHeader.add(txtFechaFinal, null);
        pnlHeader.add(txtFechaInicial, null);
        pnlHeader.add(lblNumGuia, null);
        pnlHeader.add(lblTipoGuia, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
    }

    private void initialize() {
        ftm_guia =
                new FarmaTableModel(ConstantsReporte.columnsListaReporteGuia, ConstantsReporte.defaultValuesListaReporteGuia,
                                    0);
        FarmaUtility.initSimpleList(tbl_guia, ftm_guia, ConstantsReporte.columnsListaReporteGuia);
        //llenarTablaGuia();
        txtFechaInicial.grabFocus();
    }

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tbl_guia, null, 0);

        if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (validar())
                buscar();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            limpiar();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            ((JComponent)e.getSource()).transferFocus();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void limpiar() {
        txtFechaInicial.setText("");
        txtFechaFinal.setText("");
        txtNumGuia.setText("");
    }

    private boolean validar() {
        boolean flag = true;

        if (!txtFechaInicial.getText().trim().equals("") && !txtFechaFinal.getText().trim().equals("")) {
            Calendar fecha_inicial = Calendar.getInstance();
            Calendar fecha_final = (Calendar)fecha_inicial.clone();
            try {
                fecha_inicial.setTime(FarmaUtility.getStringToDate(txtFechaInicial.getText().trim(), "dd/MM/yyyy"));
            } catch (Exception ex) { //si no se puede obtener un Date, la fecha es incorrecta
                FarmaUtility.showMessage(this, "ERROR: La fecha inicial ingresada no es valida", null);
                flag = false;
            }

            if (flag) {
                try {
                    fecha_final.setTime(FarmaUtility.getStringToDate(txtFechaFinal.getText().trim(), "dd/MM/yyyy"));
                } catch (Exception ex) { //si no se puede obtener un Date, la fecha es incorrecta
                    FarmaUtility.showMessage(this, "ERROR: La fecha final ingresada no es valida", null);
                    flag = false;
                }
            }

            if (flag) {
                if (FarmaUtility.diferenciaEnDias(fecha_inicial, fecha_final) > 0) {
                    flag = false;
                    FarmaUtility.showMessage(this, "ERROR: La fecha final debe ser mayor a la fecha inicial", null);
                }
            }
        } else if (txtFechaInicial.getText().trim().equals("") && txtFechaFinal.getText().trim().equals("")) {
            flag = true;
        } else {
            flag = false;
            FarmaUtility.showMessage(this, "Alguno de los campos se encuentra vacio", null);
        }
        return flag;
    }

    private void buscar() {
        //busca el listado de guias
        ftm_guia.clearTable();
        try {
            DBReportes.listaReporteGuias(ftm_guia, txtFechaInicial.getText(), txtFechaFinal.getText(),
                                         txtNumGuia.getText());
            lblCantT.setText("" + ftm_guia.getRowCount());
        } catch (Exception e) {
            log.info("", e);
            ftm_guia.clearTable();
            lblCantT.setText("0");
            FarmaUtility.showMessage(this, "ATENCIÓN: Existio un problema al consulta los datos. Reintente nuevamente",
                                     null);
        }

        if (ftm_guia.getRowCount() == 0) {
            FarmaUtility.showMessage(this, "No se encontraron registros para los parametros de busqueda", null);
        }
    }

    private void txtFechaInicial_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtFechaFinal_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtNumGuia_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtFechaInicial_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaInicial, e);
    }

    private void txtFechaFinal_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFinal, e);
    }

    private void txtNumGuia_keyReleased(KeyEvent e) {
        FarmaUtility.admitirSoloDigitos(e, txtNumGuia, txtNumGuia.getText().length(), this);
    }
}
