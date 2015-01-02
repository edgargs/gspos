package mifarma.ptoventa.delivery;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.delivery.reference.ConstantsMotorizados;
import mifarma.ptoventa.delivery.reference.DBMotorizados;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.delivery.reference.VariablesMotorizados;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaMotorizados.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LREQUE      21.12.2006   Creación
 * <br>
 * @author Luis Reque Orellana<br>
 * @version 1.0<br>
 */

public class DlgListaMotorizados extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaMotorizados.class);

    Frame myParentFrame;
    FarmaTableModel tableModelListaMot;
    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JTextFieldSanSerif txtApePatMot = new JTextFieldSanSerif();
    private JButtonLabel btnApePaterno = new JButtonLabel();
    private JLabelWhite lblLocal = new JLabelWhite();
    private JLabelWhite lblLocal_T = new JLabelWhite();
    private JPanelTitle pnlHeaderUsuarios = new JPanelTitle();
    private JButtonLabel btnRelacion = new JButtonLabel();
    private JScrollPane scrListaMot = new JScrollPane();
    private JTable tblListaMot = new JTable();
    private JPanelTitle pnlHeaderUsuarios1 = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabel lblRegistros = new JLabel();
    private JLabelFunction lblCrear = new JLabelFunction();
    private JLabelFunction lblModificar = new JLabelFunction();
    private JLabelFunction lblActivDesactiv = new JLabelFunction();
    private JLabelFunction lblSeleccionar = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelFunction lblF6 = new JLabelFunction();

    private String vEstado = "A";

    public DlgListaMotorizados(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(646, 375));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Listado de Motorizados");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        txtApePatMot.setBounds(new Rectangle(425, 25, 175, 20));
        txtApePatMot.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtApePaterno_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtApePaterno_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtApePatMot_keyTyped(e);
            }
        });
        btnApePaterno.setText("APELLIDO PATERNO :");
        btnApePaterno.setBounds(new Rectangle(305, 25, 115, 20));
        btnApePaterno.setMnemonic('a');
        btnApePaterno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnApePaterno_actionPerformed(e);
            }
        });
        lblLocal.setText(FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal);
        lblLocal.setBounds(new Rectangle(65, 5, 155, 20));
        lblLocal_T.setText("LOCAL:");
        lblLocal_T.setBounds(new Rectangle(5, 5, 55, 20));
        pnlHeaderUsuarios.setBounds(new Rectangle(5, 75, 625, 25));
        btnRelacion.setText("Relación de Motorizados :");
        btnRelacion.setBounds(new Rectangle(15, 5, 160, 15));
        btnRelacion.setMnemonic('r');
        btnRelacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacion_actionPerformed(e);
            }
        });
        scrListaMot.setBounds(new Rectangle(5, 100, 625, 180));
        tblListaMot.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaMot_keyPressed(e);
            }
        });

        pnlHeaderUsuarios1.setBounds(new Rectangle(5, 280, 625, 25));
        jLabelWhite1.setText("Registros:");
        jLabelWhite1.setBounds(new Rectangle(15, 0, 75, 25));
        lblRegistros.setText("0");
        lblRegistros.setBounds(new Rectangle(85, 0, 55, 25));
        lblRegistros.setFont(new Font("SansSerif", 1, 11));
        lblRegistros.setForeground(Color.white);
        lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCrear.setBounds(new Rectangle(10, 315, 75, 20));
        lblCrear.setText("[F2] Crear");
        lblModificar.setBounds(new Rectangle(90, 315, 90, 20));
        lblModificar.setText("[F3] Modificar");
        lblActivDesactiv.setBounds(new Rectangle(185, 315, 145, 20));
        lblActivDesactiv.setText("[F4] Activar/Desactivar");
        lblSeleccionar.setBounds(new Rectangle(440, 315, 110, 20));
        lblSeleccionar.setText("[F11] Seleccionar");
        lblSalir.setBounds(new Rectangle(555, 315, 75, 20));
        lblSalir.setText("[ESC] Cerrar");
        jPanelHeader1.setBounds(new Rectangle(5, 10, 625, 55));
        lblF6.setBounds(new Rectangle(335, 315, 100, 20));
        lblF6.setText("[ F6 ] Ver Todos");
        pnlHeaderUsuarios1.add(lblRegistros, null);
        pnlHeaderUsuarios1.add(jLabelWhite1, null);
        jPanelHeader1.add(lblLocal, null);
        jPanelHeader1.add(lblLocal_T, null);
        jPanelHeader1.add(txtApePatMot, null);
        jPanelHeader1.add(btnApePaterno, null);
        jPanelWhite1.add(lblF6, null);
        jPanelWhite1.add(jPanelHeader1, null);
        jPanelWhite1.add(lblSalir, null);
        jPanelWhite1.add(lblActivDesactiv, null);
        jPanelWhite1.add(lblSeleccionar, null);
        jPanelWhite1.add(lblModificar, null);
        jPanelWhite1.add(lblCrear, null);
        jPanelWhite1.add(pnlHeaderUsuarios1, null);
        scrListaMot.getViewport().add(tblListaMot, null);
        jPanelWhite1.add(scrListaMot, null);
        pnlHeaderUsuarios.add(btnRelacion, null);
        jPanelWhite1.add(pnlHeaderUsuarios, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
    }

    private void initialize() {
        initTable();
        cargaRegistrosMot(vEstado);
    }

    private void initTable() {
        tableModelListaMot =
                new FarmaTableModel(ConstantsMotorizados.columnsListaMotorizados, ConstantsMotorizados.defaultValuesListaMotorizados,
                                    0);
        FarmaUtility.initSimpleList(tblListaMot, tableModelListaMot, ConstantsMotorizados.columnsListaMotorizados);
    }

    private void cargaRegistrosMot(String pEstado) {
        try {
            DBMotorizados.getListaMotorizados(tableModelListaMot, pEstado);
            lblRegistros.setText("" + tblListaMot.getRowCount());

            if (tblListaMot.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaMot, tableModelListaMot, 1, "asc");
            }

            if (pEstado.equals("A")) {
                this.setTitle("Listado de Motorizados Activos");
                lblF6.setText("[F6] Ver Todos");
            } else {
                this.setTitle("Listado de Todos los Motorizados");
                lblF6.setText("[F6] Ver Activos");
            }

        } catch (SQLException sql) {
            log.error("Error al cargar motorizados", sql);
            FarmaUtility.moveFocus(txtApePatMot);
        }
    }

    /*METODOS DE EVENTOS*/

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtApePatMot);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtApePatMot);
    }

    private void txtApePaterno_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaMot, txtApePatMot, 1);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            e.consume();
            if (tblListaMot.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaMot, txtApePatMot.getText().trim(), 0, 1))) {
                    FarmaUtility.showMessage(this, "Motorizado no encontrado según el criterio de búsqueda.",
                                             txtApePatMot);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }


    private void btnApePaterno_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtApePatMot);
    }

    private void btnRelacion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblListaMot);
    }

    private void txtApePatMot_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtApePatMot, e);
    }

    private void txtApePaterno_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaMot, txtApePatMot, 1);
    }

    private void tblListaMot_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        else if (e.getKeyCode() == KeyEvent.VK_F2) {
            VariablesMotorizados.vAccion = ConstantsMotorizados.ACCION_INSERTAR;
            limpiaVariables();
            DlgMantMotorizados dlgMantMot =
                new DlgMantMotorizados(myParentFrame, "Mantenimiento de Motorizados", true);
            dlgMantMot.setVisible(true);

            if (FarmaVariables.vAceptar) {
                vEstado = "A";
                cargaRegistrosMot(vEstado);
                //Adicional
                FarmaUtility.findTextInJTable(tblListaMot, VariablesMotorizados.vCodMot, 0, 0);
                VariablesMotorizados.vCodMot = "";
                //
                FarmaVariables.vAceptar = false;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            VariablesMotorizados.vAccion = ConstantsMotorizados.ACCION_MODIFICAR;
            cargarDatosMot();
            DlgMantMotorizados dlgMantMot =
                new DlgMantMotorizados(myParentFrame, "Mantenimiento de Motorizados", true);
            dlgMantMot.setVisible(true);

            if (FarmaVariables.vAceptar) {
                cargaRegistrosMot(vEstado);
                FarmaVariables.vAceptar = false;
            }

        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro que desea cambiar el estado del motorizado?")) {
                int vFilaSel = tblListaMot.getSelectedRow();
                cambiarEstado();
                cargaRegistrosMot(vEstado);
                if (tblListaMot.getRowCount() > 0) {
                    FarmaUtility.findTextInJTable(tblListaMot, VariablesMotorizados.vCodMot, 0, 0);
                }
                VariablesMotorizados.vCodMot = "";
            } else
                FarmaUtility.showMessage(this, "Se canceló la operación.", txtApePatMot);
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            if (vEstado.equals("A")) {
                vEstado = "%";
            } else {
                vEstado = "A";
            }
            cargaRegistrosMot(vEstado);
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            int sel = tblListaMot.getSelectedRow();
            if (tblListaMot.getRowCount() > 0 && sel >= 0) {
                String estado = FarmaUtility.getValueFieldArrayList(tableModelListaMot.data, sel, 5).trim();
                if (estado.equals("INACTIVO")) {
                    FarmaUtility.showMessage(this, "No puede seleccionar un Motorizado inactivo.", txtApePatMot);
                    return;
                }
                if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro que desea Seleccionar al Motorizado?")) { //
                    try {
                        String pDni = FarmaUtility.getValueFieldArrayList(tableModelListaMot.data, sel, 4).trim();
                        String pCod = FarmaUtility.getValueFieldArrayList(tableModelListaMot.data, sel, 0).trim();
                        String pNam =
                            FarmaUtility.getValueFieldArrayList(tableModelListaMot.data, sel, 3).trim() + " " +
                            FarmaUtility.getValueFieldArrayList(tableModelListaMot.data, sel, 1).trim() + " " +
                            FarmaUtility.getValueFieldArrayList(tableModelListaMot.data, sel, 2).trim() + " ";

                        DBCaja.updMotorizadoDLV(VariablesDelivery.vNumeroPedido, pCod, pNam, "", "", "", "", "");
                        FarmaUtility.showMessage(this, "Se Ingreso el Motorizado Indicado.", txtApePatMot);
                        cerrarVentana(true);
                    } catch (Exception sqle) {
                        log.error("", sqle);
                        FarmaUtility.showMessage(this, "Error al ingresar Motorizado.\n" +
                                sqle.getMessage(), txtApePatMot);
                    }
                }
            }
        }
    }

    /*METODOS DE LOGICA DE NEGOCIO*/

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void cargarDatosMot() {
        try {
            int filaSelect = tblListaMot.getSelectedRow();

            ArrayList myArray = new ArrayList();

            VariablesMotorizados.vCodMot = (String)tblListaMot.getValueAt(filaSelect, 0);
            DBMotorizados.obtieneInfoMotorizado(myArray);


            VariablesMotorizados.vCodMot = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesMotorizados.vNomMot = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            VariablesMotorizados.vApePatMot = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            VariablesMotorizados.vApeMatMot = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
            VariablesMotorizados.vDNI = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            VariablesMotorizados.vPlaca = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            VariablesMotorizados.vNumNextel = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
            VariablesMotorizados.vFecNac = ((String)((ArrayList)myArray.get(0)).get(7)).trim();
            VariablesMotorizados.vDireccion = ((String)((ArrayList)myArray.get(0)).get(8)).trim();

            /**
       * PARA EL ALIAS Y COD_LOCAL_REFERENCIA
       * @author : dubilluz
       * @since  : 09.08.2007
       */
            VariablesMotorizados.vAlias = (String)tblListaMot.getValueAt(filaSelect, 6);
            VariablesMotorizados.vCodLocalAtencion = (String)tblListaMot.getValueAt(filaSelect, 7);
            VariablesMotorizados.vDescLocalAtencion = (String)tblListaMot.getValueAt(filaSelect, 8);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "No se pudo obtener la información del motorizado.", txtApePatMot);
        }

    }

    private void limpiaVariables() {
        VariablesMotorizados.vCodMot = "";
        VariablesMotorizados.vApePatMot = "";
        VariablesMotorizados.vApeMatMot = "";
        VariablesMotorizados.vNomMot = "";
        VariablesMotorizados.vDNI = "";
        VariablesMotorizados.vPlaca = "";
        VariablesMotorizados.vNumNextel = "";
        VariablesMotorizados.vFecNac = "";
        VariablesMotorizados.vDireccion = "";
    }

    private void cambiarEstado() {
        try {
            int filaSelect = tblListaMot.getSelectedRow();
            VariablesMotorizados.vCodMot = (String)tblListaMot.getValueAt(filaSelect, 0);
            String vEstado = (String)tblListaMot.getValueAt(filaSelect, 5);
            DBMotorizados.actualizaEstadoMot(VariablesMotorizados.vCodMot, vEstado);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se actualizó el estado del motorizado.", txtApePatMot);

        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "No se pudo cambiar el estado del motorizado.", txtApePatMot);
        }
    }
}
