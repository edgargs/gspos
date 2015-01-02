package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.DlgListaCompDesfasados;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgGuiasRemision.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.06.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */

public class DlgGuiasRemision extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgGuiasRemision.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JScrollPane scrLista = new JScrollPane();
    private JPanel pnlHeaderLista = new JPanel();
    private JButton btnListaGuias = new JButton();
    private JPanel pnlDatosUser = new JPanel();
    private JTable tblListaComprobantes = new JTable();
    private JLabelFunction lblCerrar = new JLabelFunction();
    private JButtonLabel btnRangoFec = new JButtonLabel();
    private JTextFieldSanSerif txtFechaInicial = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFinal = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JLabelFunction lblCorrComp = new JLabelFunction();
    private JLabelFunction lblVercompDesf = new JLabelFunction();
    private JLabelFunction lblOrdenar = new JLabelFunction();
    private JLabelFunction lblF7 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgGuiasRemision() {
        this(null, "", false);
    }

    public DlgGuiasRemision(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(576, 427));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Relacion Guías Remisión");
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
        jContentPane.setSize(new Dimension(494, 346));
        scrLista.setBounds(new Rectangle(10, 90, 550, 240));
        scrLista.setBackground(new Color(255, 130, 14));
        pnlHeaderLista.setBounds(new Rectangle(10, 65, 550, 25));
        pnlHeaderLista.setBackground(new Color(255, 130, 14));
        pnlHeaderLista.setLayout(null);
        btnListaGuias.setText("Lista Guías Remisión");
        btnListaGuias.setBounds(new Rectangle(10, 0, 180, 25));
        btnListaGuias.setBackground(new Color(255, 130, 14));
        btnListaGuias.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnListaGuias.setBorderPainted(false);
        btnListaGuias.setContentAreaFilled(false);
        btnListaGuias.setFocusPainted(false);
        btnListaGuias.setDefaultCapable(false);
        btnListaGuias.setFont(new Font("SansSerif", 1, 11));
        btnListaGuias.setForeground(Color.white);
        btnListaGuias.setHorizontalAlignment(SwingConstants.LEFT);
        btnListaGuias.setMnemonic('l');
        btnListaGuias.setRequestFocusEnabled(false);
        btnListaGuias.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPedComp_actionPerformed(e);
            }
        });
        btnListaGuias.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButton1_keyPressed(e);
            }
        });
        pnlDatosUser.setBounds(new Rectangle(10, 10, 550, 45));
        pnlDatosUser.setBorder(BorderFactory.createTitledBorder(""));
        pnlDatosUser.setBackground(new Color(43, 141, 39));
        pnlDatosUser.setLayout(null);
        lblCerrar.setText("[ ESC ] Cerrar");
        lblCerrar.setBounds(new Rectangle(445, 370, 115, 20));
        btnRangoFec.setText("Rango de fechas :");
        btnRangoFec.setBounds(new Rectangle(15, 10, 110, 20));
        btnRangoFec.setMnemonic('f');
        btnRangoFec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRangoFec_actionPerformed(e);
            }
        });
        txtFechaInicial.setBounds(new Rectangle(125, 10, 90, 20));
        txtFechaInicial.setDocument(new FarmaLengthText(10));
        txtFechaInicial.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaInicial_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaInicial_keyReleased(e);
            }
        });
        txtFechaFinal.setBounds(new Rectangle(230, 10, 90, 20));
        txtFechaFinal.setDocument(new FarmaLengthText(10));
        txtFechaFinal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFinal_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFinal_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(335, 10, 75, 20));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        lblCorrComp.setText("[F4] Corrección de comprobantes");
        lblCorrComp.setBounds(new Rectangle(10, 340, 205, 20));
        lblVercompDesf.setText("[F5] Ver comprobantes desfasados");
        lblVercompDesf.setBounds(new Rectangle(225, 340, 205, 20));
        lblOrdenar.setText("[F6] Ordenar");
        lblOrdenar.setBounds(new Rectangle(105, 370, 150, 20));
        lblF7.setText("[F7] Corregir Guía");
        lblF7.setBounds(new Rectangle(265, 370, 150, 20));
        scrLista.getViewport();
        pnlDatosUser.add(btnBuscar, null);
        pnlDatosUser.add(txtFechaFinal, null);
        pnlDatosUser.add(txtFechaInicial, null);
        pnlDatosUser.add(btnRangoFec, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblF7, null);
        jContentPane.add(lblOrdenar, null);
        jContentPane.add(lblVercompDesf, null);
        jContentPane.add(lblCorrComp, null);
        jContentPane.add(lblCerrar, null);
        scrLista.getViewport().add(tblListaComprobantes, null);
        jContentPane.add(scrLista, null);
        pnlHeaderLista.add(btnListaGuias, null);
        jContentPane.add(pnlHeaderLista, null);
        jContentPane.add(pnlDatosUser, null);
        // this.getContentPane().add(jContentPane, null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        iniciarValoresFecha();
        initTableListaGuias();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void iniciarValoresFecha() {
        String fecActual = "";
        try {
            fecActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        } catch (Exception ex) {
            log.error("", ex);
            Date fec = new Date();
            fecActual = fec.toString();
            FarmaUtility.showMessage(this, "Error al obtener la fecha actual. \n " + ex.getMessage(),
                                     tblListaComprobantes);
        }
        VariablesCaja.vFecIniVerComp = fecActual;
        VariablesCaja.vFecFinVerComp = fecActual;
        txtFechaInicial.setText(fecActual);
        txtFechaFinal.setText(fecActual);
    }

    private void initTableListaGuias() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaGuiasRemision, ConstantsInventario.defaultListaGuiasRemision,
                                    0);
        FarmaUtility.initSimpleList(tblListaComprobantes, tableModel, ConstantsInventario.columnsListaGuiasRemision);
        cargaListaComprobantes();
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaInicial);
    }

    private void jButton1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnPedComp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnListaGuias);
    }

    private void btnRangoFec_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaInicial);
    }

    private void txtFechaInicial_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFechaFinal);
        }
        chkKeyPressed(e);
    }

    private void txtFechaFinal_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        }
        chkKeyPressed(e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (datosValidados()) {
            cargarValoresFec();
            cargaListaComprobantes();

            if (tblListaComprobantes.getRowCount() == 0)
                FarmaUtility.showMessage(this, "La búsqueda no arrojó resultados.", txtFechaInicial);
            else
                FarmaUtility.moveFocus(txtFechaInicial);

        }
    }

    private void txtFechaInicial_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaInicial, e);
    }

    private void txtFechaFinal_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFinal, e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaComprobantes, null, 0);

        if (e.getKeyCode() == KeyEvent.VK_F4) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtFechaInicial);
            else if (tieneRegistroSeleccionado(tblListaComprobantes)) {
                cargarRegistroSeleccionado();
                DlgIngresoSecuenciasGuias dlgIngresoSecuenciasGuias =
                    new DlgIngresoSecuenciasGuias(this.myParentFrame, "", true);
                dlgIngresoSecuenciasGuias.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    cargaListaComprobantes();
                    FarmaVariables.vAceptar = false;
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (tblListaComprobantes.getRowCount() > 1) {
                FarmaUtility.ordenar(tblListaComprobantes, tableModel, 6, FarmaConstants.ORDEN_ASCENDENTE);
                tblListaComprobantes.repaint();
                if (cargarCompDesfasados()) {
                    DlgListaCompDesfasados dlgListaCompDesfasados =
                        new DlgListaCompDesfasados(this.myParentFrame, "", true);
                    dlgListaCompDesfasados.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        FarmaUtility.findTextInJTable(tblListaComprobantes, VariablesCaja.vNumCompDesf.trim(), 3, 3);
                        FarmaVariables.vAceptar = false;
                    }
                } else {
                    FarmaUtility.showMessage(this, "No se encontraron comprobantes desfasados", txtFechaInicial);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            if (tblListaComprobantes.getRowCount() > 0) {
                muestraComprobantesOrd();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtFechaInicial);
            else if (tieneRegistroSeleccionado(tblListaComprobantes)) {
                cargarRegistroSeleccionado();
                correguirGuia();
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

    private void cargaListaComprobantes() {
        try {
            DBInventario.getListaGuiasRangos(tableModel);

            if (tblListaComprobantes.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaComprobantes, tableModel, 6, FarmaConstants.ORDEN_ASCENDENTE);
            log.debug("se cargo la lista de guias remision");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener lista de comprobantes. \n " + e.getMessage(),
                                     tblListaComprobantes);
        }
    }

    private boolean datosValidados() {

        if (!FarmaUtility.validaFecha(txtFechaInicial.getText(), "dd/MM/yyyy") ||
            txtFechaInicial.getText().length() != 10) {
            FarmaUtility.showMessage(this, "Ingrese la Fecha Inicial correctamente", txtFechaInicial);
            return false;
        }
        if (!FarmaUtility.validaFecha(txtFechaFinal.getText(), "dd/MM/yyyy") ||
            txtFechaFinal.getText().length() != 10) {
            FarmaUtility.showMessage(this, "Ingrese la Fecha Final correctamente", txtFechaFinal);
            return false;
        }
        Date fecIni = FarmaUtility.getStringToDate(txtFechaInicial.getText().trim(), "dd/MM/yyyy");
        Date fecFin = FarmaUtility.getStringToDate(txtFechaFinal.getText().trim(), "dd/MM/yyyy");

        if (fecIni.after(fecFin)) {
            FarmaUtility.showMessage(this, "La Fecha Inicial no puede ser posterior a la Fecha Final",
                                     txtFechaInicial);
            return false;
        }
        return true;
    }

    private void cargarValoresFec() {
        VariablesCaja.vFecIniVerComp = txtFechaInicial.getText();
        VariablesCaja.vFecFinVerComp = txtFechaFinal.getText();
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;

        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void cargarRegistroSeleccionado() {
        VariablesInventario.vNumNotaEs =
                tblListaComprobantes.getValueAt(tblListaComprobantes.getSelectedRow(), 0).toString().trim();
        VariablesCaja.vSecComprobante =
                tblListaComprobantes.getValueAt(tblListaComprobantes.getSelectedRow(), 1).toString().trim();
        VariablesCaja.vDesComp =
                tblListaComprobantes.getValueAt(tblListaComprobantes.getSelectedRow(), 2).toString().trim();
        VariablesCaja.vTipComp =
                tblListaComprobantes.getValueAt(tblListaComprobantes.getSelectedRow(), 7).toString().trim();
        VariablesCaja.vNumComp =
                tblListaComprobantes.getValueAt(tblListaComprobantes.getSelectedRow(), 3).toString().trim();
    }

    private boolean cargarCompDesfasados() {
        boolean rpta = true;
        double valorAct = 0;
        double valorSig = 0;
        String tipAct = "";
        String tipSig = "";
        int indexBusqueda = 3;

        /*if( VariablesCaja.vTipOrdComprobantes.trim().equals(ConstantsCaja.TIP_ORD_CORRELATIVO)){
        indexBusqueda=11;
    }*/

        ArrayList elementoLista;
        VariablesCaja.listaCompsDesfasados = new ArrayList();

        for (int i = 0; i < tblListaComprobantes.getRowCount() - 1; i++) {
            valorAct = Double.parseDouble(tblListaComprobantes.getValueAt(i, indexBusqueda).toString());
            valorSig = Double.parseDouble(tblListaComprobantes.getValueAt(i + 1, indexBusqueda).toString());
            tipAct = tblListaComprobantes.getValueAt(i, 7).toString();
            tipSig = tblListaComprobantes.getValueAt(i + 1, 7).toString();
            if (valorAct + 1 != valorSig && tipAct.equals(tipSig)) {
                // si no son correlativos
                elementoLista = new ArrayList();
                elementoLista.add(tblListaComprobantes.getValueAt(i, 2).toString().trim());
                elementoLista.add(tblListaComprobantes.getValueAt(i, 3).toString().trim());
                elementoLista.add(tblListaComprobantes.getValueAt(i, 0).toString().trim());
                elementoLista.add(tblListaComprobantes.getValueAt(i, 5).toString().trim());
                elementoLista.add(tblListaComprobantes.getValueAt(i, 3).toString().trim());
                log.debug("Adding:" + tblListaComprobantes.getValueAt(i, 3).toString().trim());
                VariablesCaja.listaCompsDesfasados.add(elementoLista);
            }
        }
        log.debug("listaCompsDesfasados.size()=" + VariablesCaja.listaCompsDesfasados.size());
        if (VariablesCaja.listaCompsDesfasados.size() == 0) {
            rpta = false;
        }
        return rpta;
    }

    private void muestraComprobantesOrd() {
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);

        VariablesReporte.vNombreInHashtable = ConstantsInventario.vNombreInHashtableGuiaRem;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable,
                                       ConstantsInventario.vCodCampoGuiaRem, ConstantsInventario.vDescCampoGuiaRem,
                                       true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            //log.debug("Campo " + VariablesReporte.vCampo );
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblListaComprobantes, tableModel, VariablesReporte.vCampo, VariablesReporte.vOrden);
            tblListaComprobantes.repaint();
        }
    }

    private void correguirGuia() {
        if (validaTipoNota()) {
            DlgCorregirGuia dlgCorregirGuia = new DlgCorregirGuia(myParentFrame, "", true);
            dlgCorregirGuia.setVisible(true);
            if (FarmaVariables.vAceptar) {
                cargaListaComprobantes();
                FarmaVariables.vAceptar = false;
            }
        }
    }

    private boolean validaTipoNota() {
        boolean retorno;
        if (VariablesCaja.vTipComp.equals(ConstantsPtoVenta.TIP_NOTA_INGRESO))
            retorno = true;
        else {
            retorno = false;
            FarmaUtility.showMessage(this, "¡Solo puede corregir las Guías de Ingreso!", txtFechaInicial);
        }
        return retorno;
    }
}
