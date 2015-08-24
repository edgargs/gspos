package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;

import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaConveniosBTLMF.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * FRAMIREZ      12.11.2011  Creación<br>
 * <br>
 * @author Fredy Ramirez Calderon<br>
 * @version 1.0<br>
 *
 */

public class DlgBusquedaConveniosBTLMF extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgBusquedaConveniosBTLMF.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelListaconvenios;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout(); //  @jve:decl-index=0:
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JTextField txtNombreConvenio = new JTextField();
    private JButtonLabel btnNombre = new JButtonLabel();
    private JPanelTitle pnlRelacionCliente = new JPanelTitle();
    private JPanelTitle pnlMensaje = new JPanelTitle();
    private JButtonLabel btnRelacion = new JButtonLabel();
    private JButtonLabel btnMensaje = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblConvenios = new JTable();

    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction jLabelFunction3 = new JLabelFunction();
    private JLabelFunction jLabelFunction4 = new JLabelFunction();

    private ArrayList listaTodo = new ArrayList();
    private ArrayList codConvSelecc = new ArrayList();
    private boolean marcaTodos = true;

    public DlgBusquedaConveniosBTLMF() {
        this(null, "", false);
    }

    public DlgBusquedaConveniosBTLMF(Frame parent) {
        this(null, "", false);
        myParentFrame = parent;
    }

    public DlgBusquedaConveniosBTLMF(Frame parent, String title, boolean modal) {
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

        this.getContentPane().removeAll();
        this.getContentPane().setLayout(gridLayout1);

        jPanelWhite1.setBounds(new Rectangle(0, 0, 617, 373));
        tblConvenios.setBounds(new Rectangle(0, 0, 455, 80));

        this.setTitle("Lista de Convenios");
        this.setBounds(new Rectangle(0, 0, 620, 373));
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlCliente.setBounds(new Rectangle(10, 10, 600, 40));
        txtNombreConvenio.setBounds(new Rectangle(128, 10, 295, 20));
        txtNombreConvenio.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {

                txtNombreConvenio_keyPressed(e);

            }

            public void keyReleased(KeyEvent e) {
                txtNombreConvenio_keyReleased(e);
            }

        });
        btnNombre.setText("Código/Nombre :");
        btnNombre.setBounds(new Rectangle(20, 10, 102, 20));
        btnNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // btnClienteJuridico_actionPerformed(e);
            }
        });
        pnlRelacionCliente.setBounds(new Rectangle(10, 60, 600, 25));
        pnlMensaje.setBounds(new Rectangle(10, 310, 125, 20));
        btnRelacion.setText("Relacion de Convenios");
        btnRelacion.setBounds(new Rectangle(10, 5, 150, 15));
        btnRelacion.setMnemonic('r');
        btnRelacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // btnRelacion_actionPerformed(e);
            }
        });
        btnMensaje.setText("0 Seleccionados");
        btnMensaje.setBounds(new Rectangle(15, 2, 105, 15));
        btnMensaje.setMnemonic('r');

        jScrollPane1.setBounds(new Rectangle(10, 85, 600, 225));
        jLabelFunction1.setBounds(new Rectangle(360, 312, 140, 20));
        jLabelFunction1.setText("[ ENTER ] Seleccionar");
        jLabelFunction2.setBounds(new Rectangle(505, 312, 105, 20));
        jLabelFunction2.setText("[ ESC ] Cerrar");
        jLabelFunction3.setBounds(new Rectangle(255, 312, 100, 20));
        jLabelFunction3.setText("[ F11 ] Aceptar");
        jLabelFunction4.setBounds(new Rectangle(140, 312, 110, 20));
        jLabelFunction4.setText("[ F9 ] Sel.Todos");
        pnlCliente.add(txtNombreConvenio, null);
        pnlCliente.add(btnNombre, null);
        pnlRelacionCliente.add(btnRelacion, null);
        jScrollPane1.getViewport().add(tblConvenios, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jLabelFunction3, null);
        jPanelWhite1.add(jLabelFunction4, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(jScrollPane1, null);
        jPanelWhite1.add(pnlRelacionCliente, null);
        pnlMensaje.add(btnMensaje, null);
        jPanelWhite1.add(pnlMensaje, null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        String res = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_RAC, FarmaConstants.INDICADOR_S);
        log.debug("resCOnexionRac=" + res);
        initTableListaConvenios();
        FarmaVariables.vAceptar = false;
    }

    private void initTableListaConvenios() {
        FarmaColumnData columnsListaConvenios[] =
        { new FarmaColumnData("Sel", 20, JLabel.CENTER), new FarmaColumnData("Código", 100, JLabel.CENTER),
          new FarmaColumnData("Descripción", 461, JLabel.LEFT) };
        Object[] defaultValuesListaConvenios = { " ", " ", " ", " " };

        tableModelListaconvenios = new FarmaTableModel(columnsListaConvenios, defaultValuesListaConvenios, 0);
        FarmaUtility.initSelectList(tblConvenios, tableModelListaconvenios, columnsListaConvenios);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cargaListaConvenios();
        FarmaUtility.moveFocus(txtNombreConvenio);
    }

    private void chkKeyPressed(KeyEvent e) {

        String descripcionTemp = "";

        int vFila = tblConvenios.getSelectedRow();
        if (vFila > -1) {
            VariablesConvenioBTLMF.listaDatos.clear();
            VariablesConvenioBTLMF.listaDatos.add(tableModelListaconvenios.data.get(vFila));
            descripcionTemp = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos, 0, 2);
        }
        //if (e.getKeyCode() == KeyEvent.VK_F11) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            cerrarVentana(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_F9) {
            marcarTodos(marcaTodos);
            marcaTodos = !marcaTodos;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //limpiarVariablesGlobales();
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && txtNombreConvenio.getSelectedText() != null &&
                   txtNombreConvenio.getSelectedText().trim().equalsIgnoreCase(descripcionTemp.trim())) {
            //iniciarVariablesGlobales();
            //guardaRegistroConvenio();
            marcarFila();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            busquaConvenio(e);
        } else {
            if (e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_LEFT &&
                e.getKeyCode() != KeyEvent.VK_BACK_SPACE && e.getKeyCode() != KeyEvent.VK_CLEAR) {

                FarmaGridUtils.aceptarTeclaPresionada(e, tblConvenios, txtNombreConvenio, 2);
            }
        }
    }

    private void marcarTodos(boolean estado) {
        txtNombreConvenio.setText("");
        tableModelListaconvenios.clearTable();

        for (int i = 0; i < listaTodo.size(); i++) {
            Object[] aux = (Object[])listaTodo.get(i);
            actualizart((String)aux[1], estado);

            ArrayList myArray = new ArrayList();
            for (int y = 0; y < aux.length; y++) {
                myArray.add(aux[y]);
            }
            tableModelListaconvenios.insertRow(myArray);
        }

        tblConvenios.repaint();
    }

    private void marcarFila() {
        int vFila = tblConvenios.getSelectedRow();
        if (vFila > -1) {
            VariablesConvenioBTLMF.vArrayList_ListaConvenio = new ArrayList();
            VariablesConvenioBTLMF.vArrayList_ListaConvenio.add(tableModelListaconvenios.data.get(vFila));
            if (VariablesConvenioBTLMF.vArrayList_ListaConvenio.size() == 1) {
                boolean aux = (Boolean)tableModelListaconvenios.getValueAt(vFila, 0);
                tableModelListaconvenios.setValueAt(!aux, vFila, 0);
                String codConv = (String)tableModelListaconvenios.getValueAt(vFila, 1);
                tblConvenios.repaint();
                actualizart(codConv, !aux);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        txtNombreConvenio_keyReleased(e);
    }

    public void busquaConvenio(KeyEvent g) {
        if (esCodigo(txtNombreConvenio.getText().trim())) {
            buscaPorCodigo(g);
        } else {
            buscaPorDescripcion(g);
        }

    }

    public void buscaPorCodigo(KeyEvent evento) {

        txtNombreConvenio.setText(completarCerosCodigo(txtNombreConvenio.getText()));
        FarmaGridUtils.aceptarTeclaPresionada(evento, tblConvenios, txtNombreConvenio, 2);
        jScrollPane1.getViewport().removeAll();

        UtilityConvenioBTLMF.filtraCampoString(evento, tableModelListaconvenios, listaTodo, txtNombreConvenio, 1);

        //Compiamos en la tabla de la pantalla.
        FarmaUtility.ordenar(tblConvenios, tableModelListaconvenios, 2, FarmaConstants.ORDEN_ASCENDENTE);
        jScrollPane1.getViewport().add(tblConvenios, null);
        FarmaUtility.moveFocus(txtNombreConvenio);

        log.debug("tblConvenios :" + tblConvenios.getSelectedRowCount());

        if (tblConvenios.getSelectedRowCount() <= 0) {
            FarmaUtility.showMessage(this, "No se encontro ningun Convenio ", txtNombreConvenio);
        } else {
            FarmaGridUtils.showCell(tblConvenios, 0, 0);
            FarmaUtility.setearActualRegistro(tblConvenios, txtNombreConvenio, 2);
        }
    }

    public void buscaPorDescripcion(KeyEvent evento) {
        FarmaGridUtils.aceptarTeclaPresionada(evento, tblConvenios, txtNombreConvenio, 1);
        jScrollPane1.getViewport().removeAll();

        UtilityConvenioBTLMF.filtraCampoString(evento, tableModelListaconvenios, listaTodo, txtNombreConvenio, 2);

        FarmaUtility.ordenar(tblConvenios, tableModelListaconvenios, 2, FarmaConstants.ORDEN_ASCENDENTE);
        jScrollPane1.getViewport().add(tblConvenios, null);
        FarmaUtility.moveFocus(txtNombreConvenio);

        log.debug("tblConvenios :" + tblConvenios.getSelectedRowCount());

        if (tblConvenios.getSelectedRowCount() <= 0) {
            FarmaUtility.showMessage(this, "No se encontro ningun Convenio ", txtNombreConvenio);
        } else {
            FarmaGridUtils.showCell(tblConvenios, 0, 0);
            FarmaUtility.setearActualRegistro(tblConvenios, txtNombreConvenio, 2);
        }
    }


    public String completarCerosCodigo(String dato) {
        String codigo = "";
        String cero = "";
        if (dato != null) {
            int cantCero = 10 - dato.trim().length();
            for (int i = 0; i < cantCero; i++) {
                cero = cero + "0";
            }
        }
        codigo = cero + dato;
        return codigo;
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        VariablesConvenioBTLMF.vAceptar = pAceptar;

        this.setVisible(false);
        this.dispose();
    }

    private void txtNombreConvenio_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtNombreConvenio_keyReleased(KeyEvent e) {

        if (e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_LEFT &&
            e.getKeyCode() != KeyEvent.VK_BACK_SPACE && e.getKeyCode() != KeyEvent.VK_CLEAR) {

            String descripcion = "";
            int vFila = -1;
            if (FarmaGridUtils.buscarDescripcion(e, tblConvenios, txtNombreConvenio, 2)) {
                vFila = tblConvenios.getSelectedRow();
                if (vFila > -1) {
                    VariablesConvenioBTLMF.listaDatos.clear();
                    VariablesConvenioBTLMF.listaDatos.add(tableModelListaconvenios.data.get(vFila));
                    descripcion = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos, 0, 2);
                }
            }
        }

    }

    private void cargaListaConvenios() {
        try {
            DBConvenioBTLMF.listaConveniosBusqueda(tableModelListaconvenios);

            listaTodo = listaTodo(tableModelListaconvenios);
            FarmaUtility.ordenar(tblConvenios, tableModelListaconvenios, 2, FarmaConstants.ORDEN_ASCENDENTE);

            if (tblConvenios.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro ningun Convenio ", txtNombreConvenio);
                cerrarVentana(false);
            } else {
                FarmaGridUtils.showCell(tblConvenios, 0, 0);
                FarmaUtility.setearActualRegistro(tblConvenios, txtNombreConvenio, 2);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurio un error al listar los medicos \n " + sql.getMessage(),
                                     txtNombreConvenio);
        }
    }

    private boolean esCodigo(String dato) {
        boolean retorno = false;
        try {
            Integer.parseInt(dato);
            retorno = true;

        } catch (Exception e) {
            retorno = false;
        }
        return retorno;
    }

    private void actualizart(String codConv, boolean estado) {
        ArrayList lista = new ArrayList();
        for (int i = 0; i < listaTodo.size(); i++) {
            Object[] aux = (Object[])listaTodo.get(i);
            if (codConv.equalsIgnoreCase((String)aux[1])) {
                aux[0] = estado;
                if (estado) {
                    codConvSelecc.add(codConv);
                } else {
                    codConvSelecc.remove(codConv);
                }
            }
            lista.add(aux);
        }
        log.info(" --> " + codConvSelecc);
        btnMensaje.setText(codConvSelecc.size() + " Seleccionados");
    }


    private ArrayList listaTodo(FarmaTableModel tableModelListaconvenios) {
        log.debug("<<<<<<<<<<<<Metodo: listaTodo  >>>>>>>>>>>>>>>");
        ArrayList lista = new ArrayList();

        boolean vEstado = false;
        String vCodigo = "";
        String vDescrip = "";
        String vConvRelacionado = "";
        String vFlagCreaCliente = "";


        for (int k = 0; k < tableModelListaconvenios.getRowCount(); k++) {
            ArrayList listaTemp = new ArrayList();
            listaTemp.add(tableModelListaconvenios.data.get(k));
            log.info(listaTemp.get(0).getClass().toString());
            ArrayList listaTemp2 = (ArrayList)listaTemp.get(0);
            vEstado = (Boolean)listaTemp2.get(0);
            vCodigo = FarmaUtility.getValueFieldArrayList(listaTemp, 0, 1);
            vDescrip = FarmaUtility.getValueFieldArrayList(listaTemp, 0, 2);
            vConvRelacionado = FarmaUtility.getValueFieldArrayList(listaTemp, 0, 3);
            vFlagCreaCliente = FarmaUtility.getValueFieldArrayList(listaTemp, 0, 4);

            Object dato[] = new Object[50];
            dato[0] = vEstado;
            dato[1] = vCodigo;
            dato[2] = vDescrip;
            dato[3] = vConvRelacionado;
            dato[4] = vFlagCreaCliente;
            lista.add(dato);

        }

        return lista;

    }


    public ArrayList getCodConvSelec() {
        return this.codConvSelecc;
    }
}
