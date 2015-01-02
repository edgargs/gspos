package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgTransferenciasPorConfirmar.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      17.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgTransferenciasPorConfirmar extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgTransferenciasPorConfirmar.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnRelacionTransferencias = new JButtonLabel();
    private JScrollPane scrListaTransferencias = new JScrollPane();
    private JTable tblListaTransferencias = new JTable();
    private JLabelFunction lblF2 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgTransferenciasPorConfirmar() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgTransferenciasPorConfirmar(Frame parent, String title, boolean modal) {
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
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Transferencias Por Confirmar");
        this.setSize(new Dimension(525, 378));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(415, 310, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF9.setBounds(new Rectangle(170, 310, 105, 20));
        lblF9.setText("[ F9 ] Confirmar");
        pnlTitle1.setBounds(new Rectangle(10, 10, 500, 25));
        btnRelacionTransferencias.setText("Relacion de Transferencias");
        btnRelacionTransferencias.setBounds(new Rectangle(5, 5, 165, 15));
        btnRelacionTransferencias.setMnemonic('R');
        btnRelacionTransferencias.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionTransferencias_keyPressed(e);
            }
        });
        scrListaTransferencias.setBounds(new Rectangle(10, 35, 500, 260));
        lblF2.setBounds(new Rectangle(15, 310, 145, 20));
        lblF2.setText("[ F2 ] Ver Transferencia");
        pnlTitle1.add(btnRelacionTransferencias, null);
        scrListaTransferencias.getViewport();
        scrListaTransferencias.getViewport().add(tblListaTransferencias, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(scrListaTransferencias, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaTransfPorConfirmar, ConstantsInventario.defaultValuesListaTransfPorConfirmar,
                                    0);
        FarmaUtility.initSimpleList(tblListaTransferencias, tableModel,
                                    ConstantsInventario.columnsListaTransfPorConfirmar);
        cargaListaTransferencias();
    }

    private void cargaListaTransferencias() {
        try {
            DBInventario.cargaListaTransfPorConfirmar(tableModel);
            FarmaUtility.ordenar(tblListaTransferencias, tableModel, 0, FarmaConstants.ORDEN_DESCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al listar las transferencias: \n" +
                    sql.getMessage(), btnRelacionTransferencias);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(btnRelacionTransferencias);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnRelacionTransferencias_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaTransferencias, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            funcionF2();
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                funcionF9();
            } else {
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción", null);
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

    private void funcionF2() {
        int fila = tblListaTransferencias.getSelectedRow();
        if (fila > -1) {
            VariablesInventario.vNumNota = tblListaTransferencias.getValueAt(fila, 0).toString();
            VariablesInventario.vEstadoNota = tblListaTransferencias.getValueAt(fila, 4).toString();
            DlgTransferenciasVer dlgTransferenciasVer = new DlgTransferenciasVer(myParentFrame, "", true);
            dlgTransferenciasVer.setVisible(true);
            if (FarmaVariables.vAceptar) {
                cargaListaTransferencias();
                FarmaVariables.vAceptar = false;
            }
        }
    }

    private void funcionF9() {
        if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
            int fila = tblListaTransferencias.getSelectedRow();
            if (fila > -1) {
                try {
                    //actualizar a estado confirmado el pedido de transferencia
                    String numTransf = tblListaTransferencias.getModel().getValueAt(fila, 0).toString();
                    String codLocalDestino = tblListaTransferencias.getModel().getValueAt(fila, 7).toString().trim();
                    String tipoOrigenTransf = tblListaTransferencias.getModel().getValueAt(fila, 6).toString().trim();
                    log.debug("tipoOrigenTransf:" + tipoOrigenTransf + "***");
                    log.debug("codLocalDestino:" + codLocalDestino + "***");
                    log.debug("numTransf:" + numTransf + "***");

                    DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf, "I",
                                                                         "N"); //JCHAVEZ 10122009 graba inicio de confimacion de transferencia en local origen
                    DBInventario.confirmarTransferencia(numTransf);
                    DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf, "F",
                                                                         "N"); //JCHAVEZ 10122009 graba fin de confimacion de transferencia en local origen

                    FarmaUtility.aceptarTransaccion();

                    if (tipoOrigenTransf.equals("01")) { //si es TIPO LOCAL
                        log.debug("jcallo: verificando si hay linea con matriz");
                        VariablesInventario.vIndLineaMatriz =
                                FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                               FarmaConstants.INDICADOR_S);
                        log.debug("jcallo: VariablesInventario.vIndLineaMatriz:" +
                                  VariablesInventario.vIndLineaMatriz);
                        //si hay linea con matriz, se intentara realizar la trasnferencia a matriz y local destino.
                        if (VariablesInventario.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)) {
                            log.debug("jcallo: tratando de realizar la transferencia a local destino y matriz con estado L");
                            DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf, "I",
                                                                                 "S"); //JCHAVEZ 10122009 graba inicio de confimacion remota de transferencia de local origen a matriz

                            //kmoncada 09.06.2014 try-catch para evitar bloqueo de la tablas de transferencia
                            //si hay linea con matriz, se intentara realizar la trasnferencia a matriz y local destino.
                            //si ocurriera algun error, se realizara solo la confirmacion en local origen
                            String resultado = "N";
                            try {
                                resultado =
                                        DBInventario.realizarTransfDestino(numTransf, codLocalDestino, FarmaConstants.INDICADOR_N).trim();
                                if (resultado.equals(FarmaConstants.INDICADOR_S)) {
                                    //si la respuesta es afirmativa S, realiza el commit remoto
                                    FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                          FarmaConstants.INDICADOR_S);
                                    log.debug("actualizando el estado de la transferencia en local");
                                    DBInventario.actualizarEstadoTransf(numTransf, "L");
                                }
                            } catch (Exception e) {
                                log.error("", e);
                                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                      FarmaConstants.INDICADOR_S);
                            } finally {
                                log.debug("cerrando toda conexion remota");
                                FarmaConnectionRemoto.closeConnection();
                            }
                            //JCHAVEZ 10122009 graba fin de confimacion remota de transferencia de local origen a matriz
                            DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf, "F", "S");
                            FarmaUtility.aceptarTransaccion();

                            /*

                            String resultado = DBInventario.realizarTransfDestino(  numTransf,
                                                                                                                                            codLocalDestino,
                                                                                                                                            FarmaConstants.INDICADOR_N).trim();

                            if(resultado.equals(FarmaConstants.INDICADOR_S)){

                                    FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                                                              FarmaConstants.INDICADOR_S);
                                    log.debug("actualizando el estado de la transferencia en local");
                                    DBInventario.actualizarEstadoTransf(numTransf, "L");
                                    FarmaUtility.aceptarTransaccion();
                            }else{
                                    FarmaUtility.liberarTransaccionRemota(  FarmaConstants.CONECTION_MATRIZ,
                                                                                                                    FarmaConstants.INDICADOR_S);
                            }
                            log.debug("cerrando toda conexion remota");
                            FarmaConnectionRemoto.closeConnection();

                            DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf,"F","S");//JCHAVEZ 10122009 graba fin de confimacion remota de transferencia de local origen a matriz
                            log.debug("despues de invocar a matriz RESPUESTA:"+resultado);
                            */
                        }
                    }
                    FarmaUtility.showMessage(this, "Transferencia Confirmada.", btnRelacionTransferencias);
                    cerrarVentana(true);
                } catch (SQLException e) {
                    FarmaUtility.liberarTransaccion();
                    log.error("", e);
                    FarmaUtility.showMessage(this, "Ha ocurrido un error al confirmar.\n" +
                            e, btnRelacionTransferencias);
                }
            }
        } else {
            FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción", null);
        }
    }
}
