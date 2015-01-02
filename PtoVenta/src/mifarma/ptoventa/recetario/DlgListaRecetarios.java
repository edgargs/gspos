package mifarma.ptoventa.recetario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recetario.reference.ConstantsRecetario;
import mifarma.ptoventa.recetario.reference.FacadeRecetario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgListaRecetarios.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      12.04.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgListaRecetarios extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgListaRecetarios.class);

    private Frame myParentFrame;

    private FacadeRecetario facadeRecetario = new FacadeRecetario();
    private FarmaTableModel tableModel;
    private JPanel pnlFondo = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JScrollPane scrListaLocal = new JScrollPane();
    private JPanel pnlHeader = new JPanel();
    private JPanelHeader pnlBusqueda = new JPanelHeader();
    private JPanelTitle pnlTitleBusqueda = new JPanelTitle();
    private JTable tblListaRecetarios = new JTable();
    private JTextFieldSanSerif txtFechaInicial = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFinal = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtPaciente = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNumRecetario = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNumPedido = new JTextFieldSanSerif();
    private JLabelWhite lblTitleBusqueda = new JLabelWhite();
    private JButtonLabel lblFechaInicial = new JButtonLabel();
    private JButtonLabel lblFechaFinal = new JButtonLabel();
    private JButtonLabel lblPaciente = new JButtonLabel();
    private JButtonLabel lblHoraFinal = new JButtonLabel();
    private JButtonLabel lblNumPedido = new JButtonLabel();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButton btnRelacionTransferencia = new JButton();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaRecetarios() {
        this(null, "", false);
    }

    public DlgListaRecetarios(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
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

        this.setSize(new Dimension(651, 497));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setForeground(Color.white);
        this.setTitle("Lista de Recetarios");

        pnlFondo.setLayout(null);
        pnlFondo.setBackground(Color.white);
        //pnlFondo.setSize(new Dimension(594, 405));
        pnlFondo.setForeground(Color.white);
        //pnlFondo.setFocusable(false);

        scrListaLocal.setBounds(new Rectangle(5, 175, 630, 255));
        scrListaLocal.setBackground(new Color(255, 130, 14));
        scrListaLocal.setFocusable(false);
        pnlHeader.setBounds(new Rectangle(5, 150, 630, 25));
        pnlHeader.setBackground(new Color(255, 130, 14));
        pnlHeader.setLayout(null);
        pnlHeader.setForeground(new Color(255, 130, 14));

        pnlHeader.setFocusable(false);
        btnRelacionTransferencia.setText("Relacion de Recetarios");
        btnRelacionTransferencia.setBounds(new Rectangle(15, 0, 235, 25));
        btnRelacionTransferencia.setBackground(new Color(255, 130, 14));
        btnRelacionTransferencia.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnRelacionTransferencia.setBorderPainted(false);
        btnRelacionTransferencia.setContentAreaFilled(false);
        btnRelacionTransferencia.setDefaultCapable(false);
        btnRelacionTransferencia.setFocusPainted(false);
        btnRelacionTransferencia.setMnemonic('R');
        btnRelacionTransferencia.setFont(new Font("SansSerif", 1, 11));
        btnRelacionTransferencia.setForeground(Color.white);
        btnRelacionTransferencia.setHorizontalAlignment(SwingConstants.LEFT);
        btnRelacionTransferencia.setRequestFocusEnabled(false);

        btnRelacionTransferencia.setFocusable(false);
        lblF5.setText("[ F5 ] Ver Detalle");
        lblF5.setBounds(new Rectangle(145, 435, 135, 25));
        lblF5.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(525, 435, 110, 25));

        lblEsc.setFocusable(false);
        lblNumPedido.setText("Num. Pedido:");
        lblNumPedido.setBounds(new Rectangle(315, 45, 100, 20));
        //CVILCA 17.10.2013
        lblNumPedido.setMnemonic('p');
        lblNumPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNumPedido_actionPerformed(e);
            }
        });

        txtNumPedido.setBounds(new Rectangle(425, 45, 120, 20));
        txtNumPedido.setLengthText(10);
        //CVILCA 11.10.2013
        txtNumPedido.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtFechaInicial_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtFechaInicial_keyPressed(e);
            }
        });
        pnlBusqueda.setBounds(new Rectangle(5, 35, 630, 110));
        pnlBusqueda.setFocusable(false);
        pnlTitleBusqueda.setBounds(new Rectangle(5, 10, 630, 25));
        pnlTitleBusqueda.setFocusable(false);
        lblTitleBusqueda.setText("Busqueda de Recetarios");
        lblTitleBusqueda.setBounds(new Rectangle(15, 0, 385, 25));
        lblTitleBusqueda.setFocusable(false);
        txtFechaInicial.setBounds(new Rectangle(130, 15, 125, 20));
        txtFechaInicial.setLengthText(10);
        txtFechaInicial.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtFechaInicial_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtFechaInicial_keyPressed(e);
            }
        });
        lblFechaInicial.setText("Fecha Inicial:");
        lblFechaInicial.setBounds(new Rectangle(35, 15, 90, 20));
        lblFechaInicial.setFocusable(false);
        //CVILCA 17.10.2013
        lblFechaInicial.setMnemonic('i');
        lblFechaInicial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblFechaInicial_actionPerformed(e);
            }
        });

        lblFechaFinal.setText("Fecha Final:");
        lblFechaFinal.setBounds(new Rectangle(35, 45, 95, 20));
        lblFechaFinal.setFocusable(false);
        //CVILCA 17.10.2013
        lblFechaFinal.setMnemonic('f');
        lblFechaFinal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblFechaFinal_actionPerformed(e);
            }
        });

        txtFechaFinal.setBounds(new Rectangle(130, 45, 125, 20));
        txtFechaFinal.setLengthText(10);
        txtFechaFinal.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtFechaFinal_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtFechaFinal_keyPressed(e);
            }
        });
        txtPaciente.setBounds(new Rectangle(130, 75, 415, 20));
        txtPaciente.setLengthText(100);
        //CVILCA 11.10.2013
        txtPaciente.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtFechaInicial_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtFechaInicial_keyPressed(e);
            }
        });
        lblPaciente.setText("Paciente:");
        lblPaciente.setBounds(new Rectangle(35, 75, 85, 20));
        lblPaciente.setFocusable(false);
        //CVILCA 17.10.2013
        lblPaciente.setMnemonic('a');
        lblPaciente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblPaciente_actionPerformed(e);
            }
        });

        txtNumRecetario.setBounds(new Rectangle(425, 15, 120, 20));
        txtNumRecetario.setLengthText(10);
        //CVILCA 11.10.2013
        txtNumRecetario.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtFechaInicial_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtFechaInicial_keyPressed(e);
            }
        });
        lblHoraFinal.setText("Num. Recetario:");
        lblHoraFinal.setBounds(new Rectangle(305, 15, 105, 20));
        lblHoraFinal.setHorizontalAlignment(SwingConstants.CENTER);
        lblHoraFinal.setFocusable(false);
        //CVILCA 17.10.2013
        lblHoraFinal.setMnemonic('c');
        lblHoraFinal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblHoraFinal_actionPerformed(e);
            }
        });

        lblF11.setText("[ F11 ] Buscar");
        lblF11.setBounds(new Rectangle(5, 435, 135, 25));
        lblF11.setFocusable(false);
        scrListaLocal.getViewport();

        pnlTitleBusqueda.add(lblTitleBusqueda, null);

        pnlHeader.add(btnRelacionTransferencia, null);
        tblListaRecetarios.setFocusable(false);
        scrListaLocal.getViewport().add(tblListaRecetarios, null);

        pnlBusqueda.add(txtNumPedido, null);
        pnlBusqueda.add(lblNumPedido, null);
        pnlBusqueda.add(lblHoraFinal, null);
        pnlBusqueda.add(txtNumRecetario, null);
        pnlBusqueda.add(lblPaciente, null);
        pnlBusqueda.add(txtPaciente, null);
        pnlBusqueda.add(txtFechaFinal, null);
        pnlBusqueda.add(lblFechaFinal, null);
        pnlBusqueda.add(lblFechaInicial, null);
        pnlBusqueda.add(txtFechaInicial, null);

        pnlFondo.add(pnlTitleBusqueda, null);
        pnlFondo.add(pnlBusqueda, null);
        pnlFondo.add(pnlHeader, null);
        pnlFondo.add(scrListaLocal, null);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(lblF5, null);

        this.getContentPane().add(pnlFondo, BorderLayout.CENTER);
        FarmaUtility.centrarVentana(this);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

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
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            detalle();
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

    private void lblFechaInicial_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaInicial);
    }

    private void lblFechaFinal_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaFinal);
    }

    private void lblNumPedido_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNumPedido);
    }

    private void lblHoraFinal_actionPerformed(ActionEvent e) {
        log.info("colocando el foco en recetario....");
        FarmaUtility.moveFocus(txtNumRecetario);
        log.info("foco en recetario colocado....");
    }

    private void lblPaciente_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtPaciente);
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
        tableModel.clearTable();
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

    private void detalle() {
        int temp = tblListaRecetarios.getSelectedRow();
        if (temp >= 0) {
            DlgDetalleRecetario dlgDetalleRecetario = new DlgDetalleRecetario(this.myParentFrame, "", true);
            dlgDetalleRecetario.setNumRecetario(tblListaRecetarios.getValueAt(temp, 2).toString());
            dlgDetalleRecetario.setVisible(true);
        }
    }
}
