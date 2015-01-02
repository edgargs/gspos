package mifarma.ptoventa.administracion.otros;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.otros.reference.ConstantsProbisa;
import mifarma.ptoventa.administracion.otros.reference.DBProbisa;
import mifarma.ptoventa.administracion.otros.reference.VariablesProbisa;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaProbisa.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      23.04.2007   Creación<br>
 * <br>
 * @author  Luis Reque Orellana<br>
 * @version 1.0<br>
 *
 */

public class DlgListaProbisa extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaProbisa.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelControles;
    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite pnlBlanco = new JPanelWhite();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JButtonLabel btnRango = new JButtonLabel();
    private JTextFieldSanSerif txtFecIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFecFin = new JTextFieldSanSerif();
    private JLabelWhite lblAl = new JLabelWhite();
    private JButton btnBuscar = new JButton();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnListado = new JButtonLabel();
    private JScrollPane scrListado = new JScrollPane();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JLabelWhite lblCantReg = new JLabelWhite();
    private JLabelFunction lblFCrear = new JLabelFunction();
    private JLabelFunction lnlFModificar = new JLabelFunction();
    private JLabelFunction lblFCerrar = new JLabelFunction();
    private JTable tblListaControles = new JTable();

    public DlgListaProbisa(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;

        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(540, 354));
        this.setTitle("Listado de Registros");
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader.setBounds(new Rectangle(10, 10, 515, 50));
        btnRango.setText("Rango de Fechas:");
        btnRango.setBounds(new Rectangle(20, 15, 110, 20));
        btnRango.setMnemonic('r');
        btnRango.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRango_actionPerformed(e);
            }
        });
        txtFecIni.setBounds(new Rectangle(130, 15, 100, 20));
        txtFecIni.setLengthText(10);
        txtFecIni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecIni_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFecIni_keyTyped(e);
            }
        });
        txtFecFin.setBounds(new Rectangle(260, 15, 100, 20));
        txtFecFin.setLengthText(10);
        txtFecFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecFin_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFecFin_keyTyped(e);
            }
        });
        lblAl.setText("al");
        lblAl.setBounds(new Rectangle(240, 15, 25, 20));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(395, 15, 90, 20));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 70, 515, 20));
        btnListado.setText("Listado");
        btnListado.setBounds(new Rectangle(15, 0, 80, 20));
        btnListado.setMnemonic('l');
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        scrListado.setBounds(new Rectangle(10, 90, 515, 175));
        scrListado.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle2.setBounds(new Rectangle(10, 265, 515, 20));
        lblCantReg.setText("0 registros.");
        lblCantReg.setHorizontalAlignment(JLabelWhite.RIGHT);
        lblCantReg.setBounds(new Rectangle(395, 265, 110, 20));
        lblFCrear.setBounds(new Rectangle(15, 295, 90, 20));
        lblFCrear.setText("[ F2 ] Crear");
        lnlFModificar.setBounds(new Rectangle(110, 295, 100, 20));
        lnlFModificar.setText("[ F3 ] Modificar");
        lblFCerrar.setBounds(new Rectangle(425, 295, 100, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        pnlHeader.add(btnBuscar, null);
        pnlHeader.add(lblAl, null);
        pnlHeader.add(txtFecFin, null);
        pnlHeader.add(txtFecIni, null);
        pnlHeader.add(btnRango, null);
        pnlBlanco.add(lblFCerrar, null);
        pnlBlanco.add(lnlFModificar, null);
        pnlBlanco.add(lblFCrear, null);
        pnlBlanco.add(lblCantReg, null);
        pnlBlanco.add(pnlTitle2, null);
        scrListado.getViewport().add(tblListaControles, null);
        tblListaControles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaControles_keyPressed(e);
            }
        });
        pnlBlanco.add(scrListado, null);
        pnlTitle1.add(btnListado, null);
        pnlBlanco.add(pnlTitle1, null);
        pnlBlanco.add(pnlHeader, null);
        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTableListaControles();
        //cargaListaControles();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaControles() {
        tableModelControles =
                new FarmaTableModel(ConstantsProbisa.columnsListaControlesProbisa, ConstantsProbisa.defaultValuesListaControlesProbisa,
                                    0);
        FarmaUtility.initSimpleList(tblListaControles, tableModelControles,
                                    ConstantsProbisa.columnsListaControlesProbisa);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFecIni);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (txtFecIni.getText().trim().equalsIgnoreCase("") || txtFecFin.getText().trim().equalsIgnoreCase(""))
            FarmaUtility.showMessage(this, "Debe ingresar las fechas respectivas.", txtFecIni);
        else if (FarmaUtility.validarRangoFechas(this, txtFecIni, txtFecFin, false, true, true, true))
            cargaListaControles();
    }

    private void btnRango_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecIni);
    }

    private void txtFecIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFecFin);
        else
            chkKeyPressed(e);
    }

    private void txtFecFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            btnBuscar.doClick();
        else
            chkKeyPressed(e);
    }

    private void txtFecIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecIni, e);
    }

    private void txtFecFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecFin, e);
    }

    private void txtFecIni_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFecIni, e);
    }

    private void txtFecFin_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFecFin, e);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblListaControles);
    }

    private void tblListaControles_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtFecIni);
            else {
                /*if(verificaFechaMax())
        FarmaUtility.showMessage(this,"Ya se ha registro el control para el día de hoy.",txtFecIni);
      else
      {*/
                VariablesProbisa.vAccion = FarmaConstants.ACCION_INSERT;
                DlgMantProbisa dlgMantProbisa = new DlgMantProbisa(myParentFrame, "", true);
                dlgMantProbisa.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    txtFecFin.setText(getFechaActual());
                    txtFecIni.setText(VariablesProbisa.vFechaRegistro);
                    cargaListaControles();
                }
                //}
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtFecIni);
            else {
                if (tblListaControles.getRowCount() > 0) {
                    cargarVariables();
                    VariablesProbisa.vAccion = FarmaConstants.ACCION_UPDATE;
                    DlgMantProbisa dlgMantProbisa = new DlgMantProbisa(myParentFrame, "", true);
                    dlgMantProbisa.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        cargaListaControles();
                        if (tblListaControles.getRowCount() > 0)
                            FarmaUtility.findTextInJTable(tblListaControles, VariablesProbisa.vFecha, 0, 0);
                    }
                } else
                    FarmaUtility.showMessage(this, "Debe de seleccionar un registro.", txtFecIni);
            }
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaControles() {
        try {
            DBProbisa.listaControlProbisa(tableModelControles, txtFecIni.getText().trim(), txtFecFin.getText().trim());
            if (tblListaControles.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaControles, tableModelControles, 0, FarmaConstants.ORDEN_DESCENDENTE);
                FarmaUtility.moveFocusJTable(tblListaControles);
            }
            lblCantReg.setText(tblListaControles.getRowCount() + " registros.");
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar controles: " + sql.getMessage(), txtFecIni);
        }
    }

    private void cargarVariables() {
        int filaSelect = tblListaControles.getSelectedRow();

        VariablesProbisa.vFecha = "" + tblListaControles.getValueAt(filaSelect, 0);
        VariablesProbisa.vUsuario = "" + tblListaControles.getValueAt(filaSelect, 1);
        VariablesProbisa.vCantTint = "" + tblListaControles.getValueAt(filaSelect, 2);
        VariablesProbisa.vCantRec = "" + tblListaControles.getValueAt(filaSelect, 3);
        VariablesProbisa.vCantAten = "" + tblListaControles.getValueAt(filaSelect, 4);

    }

    private String getFechaActual() {
        String fecha = "";
        try {
            fecha = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error en BD: " + sql.getMessage(), txtFecIni);
        }
        return fecha;
    }

    private boolean verificaFechaMax() {
        boolean retorno = false;
        try {
            String vResultado = DBProbisa.verificaFecMax();

            if (vResultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                retorno = true;
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al verificar fecha maxima: " + sql.getMessage(), txtFecIni);
        }
        return retorno;
    }
}
