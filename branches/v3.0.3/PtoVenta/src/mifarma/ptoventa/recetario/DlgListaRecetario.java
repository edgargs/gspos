package mifarma.ptoventa.recetario;


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

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recetario.reference.ConstantsRecetario;
import mifarma.ptoventa.recetario.reference.FacadeRecetario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaRecetario extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaRecetario.class);

    private Frame myParentFrame;

    private FarmaTableModel tableModel;
    private JTable tblListaRecetarios = new JTable();

    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout2 = new CardLayout();
    private JPanelHeader pnlBusqueda = new JPanelHeader();
    private JScrollPane scrListaRecetarios = new JScrollPane();
    private JPanelTitle pnlBusquedaTitle = new JPanelTitle();
    private JLabelWhite lblTitleBusqueda = new JLabelWhite();
    private JLabelWhite lblFechaBusq = new JLabelWhite();
    private JTextFieldSanSerif txtFechaInicial = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFinal = new JTextFieldSanSerif();
    private JPanelTitle pnlTitleResult = new JPanelTitle();
    private JLabelWhite lblResultTitle = new JLabelWhite();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();


    private JTextFieldSanSerif txtPaciente = new JTextFieldSanSerif();
    private JLabelWhite lblPaciente = new JLabelWhite();
    private JTextFieldSanSerif txtNumRecetario = new JTextFieldSanSerif();
    private JLabelWhite lblNumRecet = new JLabelWhite();
    private JTextFieldSanSerif txtNumPedido = new JTextFieldSanSerif();
    private JLabelWhite lblNumPedido = new JLabelWhite();
    private JLabelWhite lblFechaFin = new JLabelWhite();

    private FacadeRecetario facadeRecetario = new FacadeRecetario();

    public DlgListaRecetario() {
        this(null, "", false);
    }

    public DlgListaRecetario(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(734, 481));
        this.getContentPane().setLayout(cardLayout2);
        pnlBusqueda.setBounds(new Rectangle(10, 35, 705, 110));
        scrListaRecetarios.setBounds(new Rectangle(10, 175, 705, 240));
        pnlBusquedaTitle.setBounds(new Rectangle(10, 10, 705, 25));
        lblTitleBusqueda.setText("Campos de Busqueda");
        lblTitleBusqueda.setBounds(new Rectangle(20, 0, 340, 25));
        txtFechaInicial.setBounds(new Rectangle(175, 15, 120, 20));
        txtFechaInicial.setLengthText(10);
        txtFechaInicial.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaInicial_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaInicial_keyReleased(e);
            }
        });
        lblFechaBusq.setText("Fecha:");
        lblFechaBusq.setBounds(new Rectangle(70, 15, 100, 20));
        txtFechaFinal.setBounds(new Rectangle(425, 15, 145, 20));
        txtFechaFinal.setLengthText(10);
        txtFechaFinal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFinal_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFinal_keyReleased(e);
            }
        });
        pnlTitleResult.setBounds(new Rectangle(10, 150, 705, 25));
        lblResultTitle.setText("Resultado de Busqueda");
        lblResultTitle.setBounds(new Rectangle(20, 0, 450, 25));
        lblF11.setText("[ F11 ] Buscar");
        lblF11.setBounds(new Rectangle(10, 420, 120, 30));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(595, 420, 120, 30));

        txtPaciente.setBounds(new Rectangle(175, 45, 395, 20));
        txtPaciente.setLengthText(100);
        txtPaciente.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtPaciente_keyPressed(e);
            }
        });
        lblPaciente.setText("Paciente:");
        lblPaciente.setBounds(new Rectangle(70, 45, 100, 20));
        txtNumRecetario.setBounds(new Rectangle(175, 75, 120, 20));
        txtNumRecetario.setLengthText(10);
        txtNumRecetario.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumRecetario_keyPressed(e);
            }
        });
        lblNumRecet.setText("Num. Recetario:");
        lblNumRecet.setBounds(new Rectangle(70, 75, 100, 20));
        txtNumPedido.setBounds(new Rectangle(425, 75, 145, 20));
        txtNumPedido.setLengthText(10);
        txtNumPedido.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumPedido_keyPressed(e);
            }
        });
        lblNumPedido.setText("Num. Pedido:");
        lblNumPedido.setBounds(new Rectangle(340, 80, 80, 15));
        lblFechaFin.setText("Fecha Final:");
        lblFechaFin.setBounds(new Rectangle(340, 15, 80, 20));
        tblListaRecetarios.setFocusable(false);
        scrListaRecetarios.getViewport().add(tblListaRecetarios, null);

        pnlBusquedaTitle.add(lblTitleBusqueda, null);
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(lblF11, null);
        pnlTitleResult.add(lblResultTitle, null);
        pnlFondo.add(pnlTitleResult, null);
        pnlFondo.add(pnlBusquedaTitle, null);
        pnlFondo.add(scrListaRecetarios, null);
        pnlBusqueda.add(lblFechaFin, null);
        pnlBusqueda.add(lblNumPedido, null);
        pnlBusqueda.add(txtNumPedido, null);
        pnlBusqueda.add(lblNumRecet, null);
        pnlBusqueda.add(txtNumRecetario, null);
        pnlBusqueda.add(lblPaciente, null);
        pnlBusqueda.add(txtPaciente, null);
        pnlBusqueda.add(txtFechaFinal, null);
        pnlBusqueda.add(lblFechaBusq, null);
        pnlBusqueda.add(txtFechaInicial, null);
        pnlFondo.add(pnlBusqueda, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
    }

    private void initialize() {
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsRecetario.columnsListaRecetarios, ConstantsRecetario.defaultListaRecetarios,
                                    0);
        FarmaUtility.initSimpleList(tblListaRecetarios, tableModel, ConstantsRecetario.columnsListaRecetarios);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    //    private void this_windowOpened(WindowEvent e)
    //    {   txtFechaInicial.grabFocus();
    //    }
    //
    //    private void this_windowClosing(WindowEvent e)
    //    {   FarmaUtility.showMessage(this,
    //                                 "Debe presionar la tecla ESC para cerrar la ventana.",
    //                                 null);
    //    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaRecetarios, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (validar())
                busqueda();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) { //detalle();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            ((JComponent)e.getSource()).transferFocus();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        //this.dispose();
    }

    private void txtFechaInicial_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaInicial, e);
    }

    private void txtFechaFinal_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFinal, e);
    }

    private void txtFechaInicial_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtFechaFinal_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private boolean validar() {
        boolean flag = true;

        Calendar fecha_inicial = Calendar.getInstance();
        Calendar fecha_final = (Calendar)fecha_inicial.clone();
        if (txtFechaInicial.getText() != null && !txtFechaInicial.getText().trim().equals("")) {
            try {
                fecha_inicial.setTime(FarmaUtility.getStringToDate(txtFechaInicial.getText().trim(), "dd/MM/yyyy"));
            } catch (Exception ex) { //si no se puede obtener un Date, la fecha es incorrecta
                FarmaUtility.showMessage(this, "ERROR: La fecha inicial no es valida", null);
                flag = false;
            }
        }

        if (txtFechaFinal.getText() != null && !txtFechaFinal.getText().trim().equals("")) {
            try {
                fecha_final.setTime(FarmaUtility.getStringToDate(txtFechaFinal.getText().trim(), "dd/MM/yyyy"));
            } catch (Exception ex) { //si no se puede obtener un Date, la fecha es incorrecta
                FarmaUtility.showMessage(this, "ERROR: La fecha final no es valida", null);
                flag = false;
            }
        }

        if (flag) {
            if (FarmaUtility.diferenciaEnDias(fecha_inicial, fecha_final) > 0) {
                flag = false;
                FarmaUtility.showMessage(this, "ERROR: La fecha inicial no debe ser mayor a la fecha final", null);
            }
        }
        return flag;
    }

    private void busqueda() {
        ArrayList<String> tmpArrayRow = null;
        ArrayList<ArrayList<String>> resultado =
            facadeRecetario.reporteRecetario(txtFechaInicial.getText().trim(), txtFechaFinal.getText().trim(),
                                             txtPaciente.getText().trim(), txtNumRecetario.getText().trim(),
                                             txtNumPedido.getText().trim());
        for (int i = 0; resultado.size() > i; i++) {
            tmpArrayRow = new ArrayList<String>();

            String tmpCodOP = ((ArrayList)resultado.get(i)).get(0).toString().trim();
            String tmpFechaRec = ((ArrayList)resultado.get(i)).get(1).toString().trim();
            String tmpNumRec = ((ArrayList)resultado.get(i)).get(2).toString().trim();
            String tmpCliente = ((ArrayList)resultado.get(i)).get(3).toString().trim();
            String tmpNumPed = ((ArrayList)resultado.get(i)).get(4).toString().trim();
            String tmpEstPed = ((ArrayList)resultado.get(i)).get(5).toString().trim();

            tmpArrayRow.add(tmpCodOP == null ? " " : tmpCodOP);
            tmpArrayRow.add(tmpFechaRec == null ? " " : tmpFechaRec);
            tmpArrayRow.add(tmpNumRec == null ? " " : tmpNumRec);
            tmpArrayRow.add(tmpCliente == null ? " " : tmpCliente);
            tmpArrayRow.add(tmpNumPed == null ? " " : tmpNumPed);
            tmpArrayRow.add(tmpEstPed == null ? " " : tmpEstPed);
            tableModel.insertRow(tmpArrayRow);
        }
        //Cuando se realiza una busqueda, si existe algun registro, se selecciona la primera fila
        if (tblListaRecetarios.getRowCount() > 0 && tblListaRecetarios.getSelectedRow() == -1)
            tblListaRecetarios.setRowSelectionInterval(0, 0);
    }

    private void txtPaciente_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtNumRecetario_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtNumPedido_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
}
