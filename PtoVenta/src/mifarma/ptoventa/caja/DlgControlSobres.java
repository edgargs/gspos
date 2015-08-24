package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
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

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.ConstantsSobres;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.caja.reference.UtilitySobres;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresoSobre.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ     03.11.2009   Creación<br>
 * <br>
 * @AUTHOR JORGE LUIS CORTEZ ALVAREZ<br>
 * @VERSION 1.0<br>
 *
 */
public class DlgControlSobres extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgControlSobres.class);

    private FarmaTableModel tableModelLista;
    private Frame myParentFrame;

    private final int COL_FEC_REM = 0;
    private final int COL_NUM_REM = 1;

    private final int COL_ORDEN = 11;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblSobres = new JTable();
    private JButtonLabel lblFecinicio = new JButtonLabel();
    private JTextFieldSanSerif txtFecInicio = new JTextFieldSanSerif();
    private JButtonLabel btnListado = new JButtonLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblRegistros = new JLabel();


    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF10 = new JLabelFunction();
    private JLabel lblRegistros1 = new JLabel();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblTotal = new JLabelWhite();
    private JButtonFunction btnBuscar = new JButtonFunction();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JTextFieldSanSerif txtFecFin = new JTextFieldSanSerif();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JPanelHeader jPanelHeader2 = new JPanelHeader();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    private JLabelFunction lblAgregar = new JLabelFunction();
    private JLabelFunction lblEliminar = new JLabelFunction();
    private JLabelFunction btnReimp = new JLabelFunction();

    public DlgControlSobres() {
        this(null, "", false);
    }

    public DlgControlSobres(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(749, 389));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Control Sobres Parciales");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlCriterioBusqueda.setBounds(new Rectangle(5, 385, 735, 40));
        pnlTitulo.setBounds(new Rectangle(5, 5, 735, 25));
        pnlResultados.setBounds(new Rectangle(5, 310, 735, 20));
        jScrollPane1.setBounds(new Rectangle(5, 30, 735, 280));
        tblSobres.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblVentasVendedor_keyPressed(e);
            }
        });
        lblFecinicio.setText("Desde :");
        lblFecinicio.setBounds(new Rectangle(75, 10, 80, 20));
        lblFecinicio.setMnemonic('d');
        lblFecinicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblFecinicio_actionPerformed(e);
            }
        });
        txtFecInicio.setBounds(new Rectangle(155, 10, 110, 20));
        txtFecInicio.setLengthText(10);
        txtFecInicio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecInicio_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecInicio_keyReleased(e);
            }
        });
        btnListado.setText("Listado de Sobres :");
        btnListado.setBounds(new Rectangle(10, 0, 345, 20));
        btnListado.setMnemonic('l');
        btnListado.setToolTipText("null");
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(5, 0, 70, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        lblRegistros.setText("0");
        lblRegistros.setBounds(new Rectangle(65, 0, 40, 20));
        lblRegistros.setFont(new Font("SansSerif", 1, 11));
        lblRegistros.setForeground(Color.white);
        lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);


        lblF2.setBounds(new Rectangle(5, 335, 105, 20));
        lblF2.setText("[ F1 ] Aprobar");
        lblEsc.setBounds(new Rectangle(655, 335, 85, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        //lblF10.setBounds(new Rectangle(150, 370, 135, 20));
        //lblF10.setText("[ F8 ]Guardar Archivo");
        lblRegistros1.setText("0");
        lblRegistros1.setBounds(new Rectangle(-100, 0, 40, 20));
        lblRegistros1.setFont(new Font("SansSerif", 1, 11));
        lblRegistros1.setForeground(Color.white);
        lblRegistros1.setHorizontalAlignment(SwingConstants.RIGHT);
        //jContentPane.add(lblF10, null);
        jLabelWhite1.setText("Total Ingreso :  S/.");
        jLabelWhite1.setBounds(new Rectangle(535, 0, 105, 20));
        lblTotal.setText("0");
        lblTotal.setBounds(new Rectangle(645, 0, 85, 20));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(600, 10, 90, 20));
        btnBuscar.setFont(new Font("Arial Black", 0, 11));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        jButtonLabel1.setText("Hasta :");
        jButtonLabel1.setBounds(new Rectangle(285, 10, 60, 20));
        jButtonLabel1.setMnemonic('h');
        jButtonLabel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        txtFecFin.setBounds(new Rectangle(350, 10, 115, 20));
        txtFecFin.setLengthText(10);
        txtFecFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecFin_keyReleased(e);
            }
        });
        jPanelHeader2.setBounds(new Rectangle(290, 0, 445, 25));
        jPanelHeader2.setBackground(Color.white);
        jLabelOrange1.setText("LOS SOBRES PENDIENTES SE MUESTRAN DE COLOR ROJO");
        jLabelOrange1.setBounds(new Rectangle(5, 0, 435, 25));
        jLabelOrange1.setForeground(Color.red);
        jLabelOrange1.setFont(new Font("DialogInput", 1, 15));
        lblAgregar.setBounds(new Rectangle(120, 335, 95, 20));
        lblAgregar.setText("[ F2 ] Agregar");
        lblEliminar.setBounds(new Rectangle(220, 335, 100, 20));
        lblEliminar.setText("[ F3 ] Eliminar");
        btnReimp.setBounds(new Rectangle(325, 335, 117, 19));
        btnReimp.setText("[ F5 ] Reimprimir");
        jContentPane.add(btnReimp, null);
        jContentPane.add(lblEliminar, null);
        jContentPane.add(lblAgregar, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF2, null);
        jScrollPane1.getViewport().add(tblSobres, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlResultados, null);
        jContentPane.add(pnlTitulo, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        jPanelHeader2.add(jLabelOrange1, null);
        pnlTitulo.add(jPanelHeader2, null);
        pnlTitulo.add(btnListado, null);
        pnlResultados.add(lblTotal, null);
        pnlResultados.add(jLabelWhite1, null);
        pnlResultados.add(lblRegistros1, null);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlCriterioBusqueda.add(txtFecFin, null);
        pnlCriterioBusqueda.add(jButtonLabel1, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFecInicio, null);
        pnlCriterioBusqueda.add(lblFecinicio, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initTableListaVentasVendedor();
    }

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaVentasVendedor() {
        tblSobres.getTableHeader().setReorderingAllowed(false);
        tblSobres.getTableHeader().setResizingAllowed(false);

        tableModelLista =
                new FarmaTableModel(ConstantsCaja.columnsListaSobresControl_02, ConstantsCaja.defaultValuesListaSobresControl_02,
                                    0);
        FarmaUtility.initSimpleList(tblSobres, tableModelLista, ConstantsCaja.columnsListaSobresControl_02);

        //lista sobres existentes
        listarSobres();

        if (tblSobres.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblSobres);
            //tblSobres.setSelectionMode(0);
            FarmaGridUtils.showCell(tblSobres, 0, 0);
        }
    }


    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */

    private void txtFechaFin_keyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e, tblSobres,null, 2);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            /* if(txtFechaFin.getText().trim().length()>1){
          //FarmaUtility.moveFocus(btnBuscar);
          btnBuscar.doClick();
         }*/
        }
        chkKeyPressed(e);
    }

    private void tblVentasVendedor_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }


    private void btnPeriodo_actionPerformed(ActionEvent e) {
        //FarmaUtility.moveFocus(txtFecInicio); ASOSA, 07.06.2010
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblSobres.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblSobres);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }


    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            /*
     if(lblF2.isVisible())
       accionFunciones(ConstantsSobres.ACC_APRUEBA);
    */

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            /*if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Esta seguro de eliminar el sobre creado?")){
        borrarSobre();
        lblRegistros.setText(tblSobres.getRowCount()+"");
      }*/
            /*
        if(tblSobres.getSelectedRow()>-1){
         imprimeSobre(this);
        }
        else
        FarmaUtility.showMessage(this, "Debe seleccionar un sobre.",tblSobres);
        */

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {

            /*  int cant=0;
       if(tblSobres.getRowCount()>0){
            for(int i=0;i<tblSobres.getRowCount(); i++){
                 if(((String)tblSobres.getValueAt(i,5)).trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                 {
                   cant++;
                 }
                }
            if(cant>0){
                if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Esta seguro de registrar los sobres ingresados?")){
                    guardarCreados();
                }
            }else
                FarmaUtility.showMessage(this, "No existen sobres sin codigo",cmbMoneda);

        }else{
            FarmaUtility.showMessage(this, "Debe ingresar sobres.",cmbMoneda);
        }
          */
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            if (lblAgregar.isVisible())
                accionFunciones(ConstantsSobres.ACC_INGRESO);

        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (lblEliminar.isVisible())
                accionFunciones(ConstantsSobres.ACC_ELIMINA);

        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (btnReimp.isVisible())
                reimprimir();
        }
    }


    /**
     * imprime sobres
     * @AUTHOR JCORTEZ
     * @SINCE 08.04.2010
     */
    private void imprimeSobre(JDialog pDialogo) {
        String pIndProsegur = FarmaConstants.INDICADOR_N;
        int row = tblSobres.getSelectedRow();
        /*String pSecMovCaja=FarmaUtility.getValueFieldArrayList(tableModelLista.data,row,9);
         String CodSobre=FarmaUtility.getValueFieldArrayList(tableModelLista.data,row,3);*/
        String pSecMovCaja = FarmaUtility.getValueFieldArrayList(tableModelLista.data, row, 10);
        String CodSobre = FarmaUtility.getValueFieldArrayList(tableModelLista.data, row, 3);
        boolean indImp = false;
        try {
            //pIndProsegur = DBCajaElectronica.getIndProsegur().trim();
            pIndProsegur = "S";
            if (pIndProsegur.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
                String pCodSobre = "";

                String vIndImpre = DBCaja.obtieneIndImpresion();
                log.debug("vIndImpre :" + vIndImpre);
                if (!vIndImpre.equals("N")) {
                    ArrayList pLista = new ArrayList();
                    DBCajaElectronica.getSobreDeclarados(pSecMovCaja, pLista);

                    //for(int f=0;f<pLista.size();f++){
                    String html = DBCajaElectronica.getHtmlSobreDeclarados(pSecMovCaja, CodSobre);
                    log.debug(":::::::::.html :" + html);
                    PrintConsejo.imprimirHtml(html.trim(), VariablesPtoVenta.vImpresoraActual,
                                              VariablesPtoVenta.vTipoImpTermicaxIp);
                    PrintConsejo.imprimirHtml(html.trim(), VariablesPtoVenta.vImpresoraActual,
                                              VariablesPtoVenta.vTipoImpTermicaxIp);

                    indImp = true;
                    // }
                    if (indImp)
                        FarmaUtility.showMessage(pDialogo, "Recoger Voucher de sobres", null);
                }

            }

        } catch (SQLException sqlException) {
            log.error("", sqlException);
            //log.error(null,sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener los datos del sobre a imprimir.", null);

        }

    }

    private void aprobarSobre() {

        if (tblSobres.getRowCount() > 0) {

            if (tblSobres.getSelectedRow() < 0) {
                FarmaUtility.showMessage(this, "Debe seleccionar un sobre", tblSobres);
                return;
            }

            String estado =
                FarmaUtility.getValueFieldArrayList(tableModelLista.data, tblSobres.getSelectedRow(), 11).trim();
            String CodSobre = tblSobres.getValueAt(tblSobres.getSelectedRow(), 3).toString().trim();
            String Fecha = tblSobres.getValueAt(tblSobres.getSelectedRow(), 4).toString().trim();

            log.debug("ESTADO :" + estado);

            if (!estado.equalsIgnoreCase("")) {
                if (estado.equalsIgnoreCase("A")) {
                    FarmaUtility.showMessage(this, "Este ya ha sido aprobado.", tblSobres);
                } else if (estado.equalsIgnoreCase("I")) {
                    FarmaUtility.showMessage(this, "Este sobre ya ha sido rechazado.", tblSobres);
                } else if (estado.equalsIgnoreCase("C")) {
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "¿Esta seguro de aprobar el sobre?")) {

                        if (validarEstSobre(Fecha, CodSobre)) {
                            int row = tblSobres.getSelectedRow();
                            if (row > -1) {
                                aprobarSobre(row);
                                listarSobres();
                            }
                        }
                    }
                }
            }
        } else {
            FarmaUtility.showMessage(this, "No existen sobres para aprobar.", txtFecInicio);
        }
    }


    /**
     * Se aprueba el sobre
     */
    private void aprobarSobre(int row) {

        String CodSobre = tblSobres.getValueAt(tblSobres.getSelectedRow(), 3).toString().trim();
        String FecVta = tblSobres.getValueAt(tblSobres.getSelectedRow(), 4).toString().trim();
        try {
            DBCaja.aprobarSobre(FecVta, CodSobre);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se aprobó el sobre exitosamente", tblSobres);

        } catch (SQLException sql) {
            if (sql.getErrorCode() == 20001)
                FarmaUtility.showMessage(this, "El sobre ya ha sido aprobado!!!", null);
            else if (sql.getErrorCode() == 20002)
                FarmaUtility.showMessage(this, "El sobre ya ha sido rechazado!!!", null);
            else if (sql.getErrorCode() == 20003)
                FarmaUtility.showMessage(this, "El sobre no existe!!!", null);
            else {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrió un error al aprobar sobre.\n" +
                        sql.getMessage(), tblSobres);
            }
        }

    }

    /**
     * Se valida el estado del sobre al aceptarlo
     */
    private boolean validarEstSobre(String FecVta, String CodSobre) {

        boolean estado = true;
        try {
            DBCaja.validaEstadosobre(FecVta, CodSobre);
        } catch (SQLException e) {
            estado = false;
            if (e.getErrorCode() == 20001) {
                FarmaUtility.showMessage(this, "El sobre ya ha sido aprobado", tblSobres);
            } else if (e.getErrorCode() == 20002) {
                FarmaUtility.showMessage(this, "El sobre ya ha sido rechazado", tblSobres);
            } else {
                log.error("", e);
                FarmaUtility.showMessage(this, "Ocurrió un error al validar estado sobre" + e.getMessage(), tblSobres);
            }
        }
        return estado;
    }


    /**
     * Se ordena el listado de remitos
     * */
    private void ordenar() {

        DlgOrdenar dlgordenar = new DlgOrdenar(myParentFrame, "", true);
        dlgordenar.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaUtility.ordenar(tblSobres, tableModelLista, Integer.parseInt(VariablesCaja.vColumna),
                                 VariablesCaja.vOrden);
            tblSobres.repaint();
            //FarmaUtility.setearPrimerRegistro(tblRemitos,txtFecha,2);
        }
    }

    private void listarSobres() {

        String fechIni = txtFecInicio.getText().trim();
        String fecFin = txtFecFin.getText().trim();

        try {
            //DBCaja.getListaSobresPorAprobar(tableModelLista,fechIni,fecFin); antes
            DBCajaElectronica.listarSobresParcialesXAprobar(tableModelLista); //ASOSA, 07.06.2010
            if (tblSobres.getRowCount() > 0) {
                //FarmaUtility.ordenar(tblSobres, tableModelLista, COL_ORDEN, FarmaConstants.ORDEN_DESCENDENTE); ASOSA, 07.06.2010
                //FarmaUtility.moveFocus(txtFecInicio); ASOSA, 07.06.2010
            }
            lblRegistros.setText(tblSobres.getRowCount() + "");

            ArrayList aux = new ArrayList();
            String isAprobado = "", codSobre = "";

            if (tblSobres.getRowCount() > 0) {
                for (int i = 0; i < tableModelLista.data.size(); i++) {
                    //valida sobre
                    //log.debug("obtiene estado->"+FarmaUtility.getValueFieldArrayList(tableModelLista.data,tblSobres.getSelectedRow(),8).trim());
                    //if ( ((ArrayList)tableModelLista.data.get(i)).get(8).toString().trim().equalsIgnoreCase("A") )
                    if (FarmaUtility.getValueFieldArrayList(tableModelLista.data, i,
                                                            11).trim().equalsIgnoreCase("P")) {
                        codSobre = ((ArrayList)tableModelLista.data.get(i)).get(3).toString().trim();
                        isAprobado = ((ArrayList)tableModelLista.data.get(i)).get(11).toString().trim();
                        //log.debug("obtiene estado-> "+isAprobado+" - "+codSobre);
                        //isAprobado = validarEstadoSobre(codSobre,codFormaPago);
                        //log.debug("obtiene estado-> "+isAprobado);
                        // if(isAprobado.trim().equalsIgnoreCase("A"))//Aprobado
                        aux.add(String.valueOf(i));
                    }
                }
                FarmaUtility.initSimpleListCleanColumns(tblSobres, tableModelLista,
                                                        ConstantsCaja.columnsListaSobresControl_02, aux, Color.white,
                                                        Color.red, false); //ASOSA, 07.06.2010
            }
            lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSobres, 8))); //ASOSA, 07.06.2010

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar sobres", txtFecInicio);
        }
    }

    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtFecInicio, e);

    }


    private boolean validaMontoIngresado() {
        log.debug("VariablesCaja.vIndTarjetaSeleccionada valida monto : " + VariablesCaja.vIndTarjetaSeleccionada);
        String monto = txtFecInicio.getText().trim();

        if (monto.equalsIgnoreCase("") || monto.length() <= 0) {
            FarmaUtility.showMessage(this, "Ingrese monto a pagar.", monto);
            return false;
        }
        if (!FarmaUtility.isDouble(monto)) {
            FarmaUtility.showMessage(this, "Ingrese monto a pagar valido.", monto);
            return false;
        }
        if (FarmaUtility.getDecimalNumber(monto) == 0) {
            FarmaUtility.showMessage(this, "Ingrese monto a pagar mayor a 0.", monto);
            return false;
        }
        if (FarmaUtility.getDecimalNumber(monto) < 0) {
            FarmaUtility.showMessage(this, "El monto ingresado es negativo.", monto);
            return false;
        }

        return true;
    }


    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;
        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void txtFecInicio_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFecInicio.getText().trim().length() > 1) {
                FarmaUtility.moveFocus(txtFecFin);
            }
        }
        chkKeyPressed(e);
    }

    private void txtFecInicio_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecInicio, e);
    }

    private void txtFecFin_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFecFin.getText().trim().length() > 1) {
                btnBuscar.doClick();
                //FarmaUtility.moveFocus(txtFecInicio); ASOSA, 07.06.2010
            }
        }
        chkKeyPressed(e);
    }

    private void txtFecFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecFin, e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (validarCampos()) {
            listarSobres();
            lblRegistros.setText(tblSobres.getRowCount() + "");
        }
        FarmaGridUtils.moveRowSelection(tblSobres, 0);
        //FarmaUtility.moveFocus(tblSobres);
    }

    /**
     *  Valida los campos ingresados()Cod,fecini,fecfin)
     */
    private boolean validarCampos() {

        boolean retorno = true;
        String fechaini = txtFecInicio.getText().trim();
        String fechafin = txtFecFin.getText().trim();
        if (txtFecInicio.getText().length() < 1 || txtFecInicio.getText().length() < 10) {
            retorno = false;
            FarmaUtility.showMessage(this, "Formato de fecha inicio inválido", txtFecInicio);
        } else if (txtFecFin.getText().length() < 1 || txtFecFin.getText().length() < 10) {
            retorno = false;
            FarmaUtility.showMessage(this, "Formato de fecha fin  inválido", txtFecFin);
        } else if (!FarmaUtility.validaFecha(fechaini.trim(), "dd/MM/yyyy")) { //ASOSA, 01.06.2010
            retorno = false;
            FarmaUtility.showMessage(this, "Fecha de inicio inválida", txtFecInicio);
        } else if (!FarmaUtility.validaFecha(fechafin.trim(), "dd/MM/yyyy")) { //ASOSA, 01.06.2010
            retorno = false;
            FarmaUtility.showMessage(this, "Fecha de fin inválida", txtFecFin);
            FarmaUtility.moveFocus(txtFecFin);
        } else if (!FarmaUtility.validarRangoFechas(this, txtFecInicio, txtFecFin, false, true, true, true)) {
            retorno = false;
            FarmaUtility.moveFocus(txtFecInicio);
        }
        return retorno;
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        //FarmaUtility.moveFocus(txtFecInicio);
        FarmaUtility.moveFocus(tblSobres);
        activarSegunRol();
        //YA NO SE VA APRUEBA
        lblF2.setVisible(false);
    }

    private void lblFecinicio_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecInicio);
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecFin);
    }

    /**
     * ACtivar segun rol
     * @author ASOSA
     * @since 07.06.2010
     */
    private void activarSegunRol() {
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_CAJERO) &&
            FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) {
            lblF2.setVisible(true);
            lblAgregar.setVisible(true);
            lblEliminar.setVisible(true);
            //lblModificar.setVisible(true);
        } else if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_CAJERO)) {
            lblF2.setVisible(false);
            lblAgregar.setVisible(true);
            lblEliminar.setVisible(true);
            //lblModificar.setVisible(true);
        } else if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) {
            lblF2.setVisible(true);
            lblAgregar.setVisible(false);
            lblEliminar.setVisible(false);
            //lblModificar.setVisible(false);
        } else if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS)) {
            lblF2.setVisible(false);
            lblAgregar.setVisible(false);
            lblEliminar.setVisible(false);
            //lblModificar.setVisible(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void accionFunciones(String pTipoAccion) {

        //String pTipoAccion,
        String pSec = " ";
        String pCodigoSobre = " ";
        String pSecMovCaja = " ";
        //JTextFieldSanSerif jfoco,
        //JDialog pDialogo,
        //Frame parent
        int pPos = tblSobres.getSelectedRow();

        //if(pPos>0) {
        if (pPos >= 0 ||
            pTipoAccion.toUpperCase().trim().equalsIgnoreCase(ConstantsSobres.ACC_INGRESO)) { //ASOSA, 10.06.2010
            if (pTipoAccion.toUpperCase().trim().equalsIgnoreCase(ConstantsSobres.ACC_MODIFICA) ||
                pTipoAccion.toUpperCase().trim().equalsIgnoreCase(ConstantsSobres.ACC_ELIMINA) ||
                pTipoAccion.toUpperCase().trim().equalsIgnoreCase(ConstantsSobres.ACC_APRUEBA)) {

                pSec = FarmaUtility.getValueFieldArrayList(tableModelLista.data, pPos, 9);
                pCodigoSobre = FarmaUtility.getValueFieldArrayList(tableModelLista.data, pPos, 3);
                pSecMovCaja = FarmaUtility.getValueFieldArrayList(tableModelLista.data, pPos, 10);
                log.info("pSec:" + pSec);
                log.info("pCodigoSobre:" + pCodigoSobre);
                log.info("pSecMovCaja:" + pSecMovCaja);
            }
            UtilitySobres.accionSobre(pTipoAccion, pSec, pCodigoSobre, pSecMovCaja, tblSobres, this, myParentFrame);
        } else {
            FarmaUtility.showMessage(this, "Debe de seleccionar una fila de la tabla.", tblSobres);
        }
        listarSobres(); //ASOSA, 10.06.2010
        if (tblSobres.getRowCount() > 0)
            FarmaGridUtils.moveRowSelection(tblSobres, 0);
    }

    /**
     * Reimprime un sobre
     * @author ASOSA
     * @since 04.08.2010
     */
    private void reimprimir() {
        String secSobre =
            FarmaUtility.getValueFieldArrayList(tableModelLista.data, tblSobres.getSelectedRow(), 3).trim();
        String pSecMovCaja =
            FarmaUtility.getValueFieldArrayList(tableModelLista.data, tblSobres.getSelectedRow(), 10).trim();
        log.debug("pSecMovCaja: " + pSecMovCaja);
        log.debug("SecSobre: " + secSobre);
        UtilitySobres.reimprimirSobre(null, pSecMovCaja, secSobre);
    }

}
