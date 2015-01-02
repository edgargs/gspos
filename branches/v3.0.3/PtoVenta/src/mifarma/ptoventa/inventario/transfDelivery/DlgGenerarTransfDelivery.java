package mifarma.ptoventa.inventario.transfDelivery;


import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JTable;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.inventario.transfDelivery.reference.ConstantsTranfDelivery;
import mifarma.ptoventa.inventario.transfDelivery.reference.DBTranfDelivery;
import mifarma.ptoventa.inventario.transfDelivery.reference.VariablesTranfDelivery;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgSelecLocal.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      20.10.2008   Creación<br>
 * <br>
 * @author JCALLO<br>
 * @version 1.0<br>
 *
 */
public class DlgGenerarTransfDelivery extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgGenerarTransfDelivery.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    ArrayList tableModel_02; //ASOSA, 31.08.2010

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelWhite lblCodigo_T = new JLabelWhite();
    private JLabelWhite lblUnidad_T = new JLabelWhite();

    private JTable tblProdTransf = new JTable();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgGenerarTransfDelivery() {
        //this(null, "", false,null); antes
        this(null, "", false, null, null); //ASOSA, 31.08.2010
    }

    public DlgGenerarTransfDelivery(Frame parent, String title, boolean modal, JTable tblProdTransf,
                                    ArrayList tableModel02) { //ASOSA, 31.08.2010
        super(parent, title, modal);
        myParentFrame = parent;
        this.tblProdTransf = tblProdTransf;
        this.tableModel = (FarmaTableModel)tblProdTransf.getModel();
        this.tableModel_02 = tableModel02; //ASOSA, 31.08.2010
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        jContentPane.setSize(new Dimension(809, 124));
        this.setSize(new Dimension(354, 112));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Procesando...");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 5, 335, 70));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblCodigo_T.setText("Generando Transferencia ...");
        lblCodigo_T.setBounds(new Rectangle(10, 15, 315, 35));
        lblCodigo_T.setForeground(new Color(255, 130, 14));


        lblCodigo_T.setFont(new Font("SansSerif", 1, 18));
        lblUnidad_T.setText("");
        lblUnidad_T.setBounds(new Rectangle(374, 17, 13, 15));
        lblUnidad_T.setForeground(new Color(255, 130, 14));


        jContentPane.add(pnlTitle1, null);
        pnlTitle1.add(lblUnidad_T, null);


        pnlTitle1.add(lblCodigo_T, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //this.setContentPane(jContentPane);
        //

    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initCabecera();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCabecera() {
        /*lblAlias.setText(VariablesCtrlMotorizado.vAlias_Motorizado);
        lblDescripcion.setText(VariablesCtrlMotorizado.vNombre_Motorizado);*/
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        lblCodigo_T.repaint();
        if (procesarTransferencia()) {
            cerrarVentana(true);
        } else {
            cerrarVentana(false);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Espere que termine el proceso de generacion de transferencia", null);
    }


    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */


    /**
     * Metodo encargado de validar formato de Fecha formato dd/MM/yyyy.
     *@autor jcallo
     *@since 20.10.2008
     */
    public boolean validarFecha(String fecha, String formato) {
        boolean flag = false;
        Date fec_valido = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            fec_valido = sdf.parse(fecha);

            if (fec_valido != null) {
                flag = true;
            } else {
                flag = false;
            }

        } catch (Exception e) {
            flag = false;
            log.debug("ERROR : " + e);
        }
        return flag;
    }

    /**
     * Metodo encargado de validar formato de correo.
     *@autor jcallo
     *@since 17.10.2008
     */
    public boolean validarHora(String hora) {

        //boolean b = Pattern.matches("^(0[0-2]|1[0-9]|2[0-3]):(0[0-9]|[1-5][0-9]):(0[0-9]|[1-5][0-9])$", hora);//HH:MM:SS
        boolean b = Pattern.matches("^(0[0-9]|1[0-9]|2[0-3]):(0[0-9]|[1-5][0-9])$", hora); //HH:MM

        return b;
    }

    private boolean procesarTransferencia() {
        boolean retorno = false;
        int valFracProd; //JCHAVEZ 25082009.n
        String indFraccionamiento = "";
        try {
            double sumaTotal = 0.0;

            for (int i = 0; i < tblProdTransf.getRowCount(); i++) {
                log.info(" suma total : " + sumaTotal);
                sumaTotal += FarmaUtility.getDecimalNumber(tableModel.getValueAt(i, 14).toString().trim());
            }


            String numera =
                DBTranfDelivery.agregarCabeceraTransferencia(VariablesTranfDelivery.vTipoDestino_Transf, VariablesTranfDelivery.vCodLocalDestino,
                                                             VariablesTranfDelivery.vMotivo_Transf,
                                                             VariablesTranfDelivery.

                    vDestino_Transf, VariablesTranfDelivery.vRucDestino_Transf,
                    VariablesTranfDelivery.vDirecDestino_Transf, VariablesTranfDelivery.

                    vTransportista_Transf, VariablesTranfDelivery.vRucTransportista_Transf,
                    VariablesTranfDelivery.vDirecTransportista_Transf,
                    VariablesTranfDelivery.vPlacaTransportista_Transf, tblProdTransf.getRowCount() + "",
                    sumaTotal + "", VariablesTranfDelivery.

                    vCodDefinicion,
                    //4 campos adicionales agregados
                    VariablesTranfDelivery.vCodLocalDel, VariablesTranfDelivery.vNumPedidoTransf,
                    VariablesTranfDelivery.vSecGrupo);
            //log.debug(numera);
            //ArrayList pArrayList = new ArrayList();

            for (int i = 0; i < tableModel.getRowCount(); i++) {

                //DBInventario.obtieneStockProducto_ForUpdate(pArrayList, tableModel.getValueAt(i,0).toString());
                //if(((String)((ArrayList)pArrayList.get(0)).get(0)).trim())

                //JCHAVEZG 25082009.sn
                if (VariablesInventario.vTipoDestino_Transf.equals("01")) { //si la transferencia es local

                    valFracProd = DBTranfDelivery.getValFracProducto(tableModel.getValueAt(i, 0).toString());
                    indFraccionamiento =
                            DBTranfDelivery.obtieneIndFraccLocalDestino(VariablesTranfDelivery.vCodLocalDestino,
                                                                        tableModel.getValueAt(i, 0).toString(),
                                                                        tableModel.getValueAt(i, 4).toString(),
                                                                        valFracProd, FarmaConstants.INDICADOR_N);
                    if (indFraccionamiento.equals("V")) {
                        FarmaUtility.liberarTransaccion();

                        FarmaUtility.showMessage(this,
                                                 "Ha ocurrido un error, algunos productos no pueden ser transferidos debido a la fraccion actual del local destino.\n",
                                                 null);
                        retorno = false;
                        return retorno;
                    }
                }
                //JCHAVEZG 25082009.en

                /*DBTranfDelivery.agregarDetalleTransferencia(numera,tableModel.getValueAt(i,0).toString(),     antes
						tableModel.getValueAt(i,13).toString(), tableModel.getValueAt(i,14).toString(),
						tableModel.getValueAt(i,4).toString(), tableModel.getValueAt(i,6).toString(),
						tableModel.getValueAt(i,5).toString(),VariablesTranfDelivery.vTipoDestino_Transf,
						VariablesTranfDelivery.vCodLocalDestino,tableModel.getValueAt(i,10).toString(),indFraccionamiento);*/
                //INI ASOSA, 31.08.2010
                ArrayList listaxx = (ArrayList)tableModel_02.get(i);
                String secResp = listaxx.get(16).toString();
                DBTranfDelivery.agregarDetalleTransferencia_02(numera, tableModel.getValueAt(i, 0).toString(),
                                                               //INI ASOSA, 31.08.2010
                        tableModel.getValueAt(i, 13).toString(), tableModel.getValueAt(i, 14).toString(),
                        tableModel.getValueAt(i, 4).toString(), tableModel.getValueAt(i, 6).toString(),
                        tableModel.getValueAt(i, 5).toString(), VariablesTranfDelivery.vTipoDestino_Transf,
                        VariablesTranfDelivery.vCodLocalDestino, tableModel.getValueAt(i, 10).toString(),
                        indFraccionamiento, secResp) //secRespaldo
                    ;
                //FIN ASOSA, 31.08.2010
                //actualizaStkComprometidoProd_sinCommit(tblListaProductos.getValueAt(i,0).toString(),Integer.parseInt(tblListaProductos.getValueAt(i,4).toString()),ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(tblListaProductos.getValueAt(i,4).toString()));
            }

            DBTranfDelivery.generarGuiasTransferencia(numera, FarmaConstants.ITEMS_POR_GUIA,
                                                      tableModel.getRowCount() + "");

            /*** validando si por x motivos se ha cambiado el estado del pedido de transferencia****/
            String indEstado =
                DBTranfDelivery.validarEstadoPedido(VariablesTranfDelivery.vCodLocalDel, VariablesTranfDelivery.vNumPedidoTransf,
                                                    VariablesTranfDelivery.vSecGrupo,
                                                    VariablesTranfDelivery.vCodLocalDestino,
                                                    VariablesTranfDelivery.vSecTrans);
            /***fin validacion***/

            FarmaUtility.aceptarTransaccion(); //commit generacion de transferencia

            /*** proceso de impresion **/
            log.info("numera : " + numera);
            VariablesInventario.vNumNotaEs = numera;
            VariablesInventario.vDescMotivo_Transf = VariablesTranfDelivery.vDescMotivo_Transf;
            VariablesInventario.vDestino_Transf = VariablesTranfDelivery.vDestino_Transf;
            VariablesInventario.vRucDestino_Transf = VariablesTranfDelivery.vRucDestino_Transf;
            VariablesInventario.vDirecDestino_Transf = VariablesTranfDelivery.vDirecDestino_Transf;
            VariablesInventario.vTransportista_Transf = VariablesTranfDelivery.vTransportista_Transf;
            VariablesInventario.vTipoDestino_Transf = VariablesTranfDelivery.vTipoDestino_Transf;
            VariablesInventario.vCodDestino_Transf = VariablesTranfDelivery.vCodLocalDestino;
            VariablesInventario.vRucTransportista_Transf = VariablesTranfDelivery.vRucTransportista_Transf;

            log.info("VariablesCaja.vSecImprLocalGuia : " + VariablesCaja.vSecImprLocalGuia);

            // dubilluz 16.10.2014
            boolean pResultado = UtilityInventario.procesoImpresionComprobante(this, tblProdTransf);
            /**fin proceso de impresion**/
            if (pResultado) {
                /**validando si por x motivos se cambio el estado a inactivo del pedido de transferencia delivery**/
                indEstado =
                        DBTranfDelivery.validarEstadoPedido(VariablesTranfDelivery.vCodLocalDel, VariablesTranfDelivery.vNumPedidoTransf,
                                                            VariablesTranfDelivery.vSecGrupo,
                                                            VariablesTranfDelivery.vCodLocalDestino,
                                                            VariablesTranfDelivery.vSecTrans);
                log.info(" indEstado : " + indEstado);
                /** fin validacion **/
                DBTranfDelivery.actualizarPedidoTransfDelivery(VariablesTranfDelivery.vCodLocalDel,
                                                               VariablesTranfDelivery.vNumPedidoTransf,
                                                               VariablesTranfDelivery.vSecGrupo,
                                                               VariablesTranfDelivery.vCodLocalDestino,
                                                               ConstantsTranfDelivery.ESTADO_GENERADO,
                                                               VariablesTranfDelivery.vSecTrans);
                FarmaUtility.aceptarTransaccion();
                try {
                    DBTranfDelivery.confirmarTransferencia(numera);
                } catch (Exception sqle) {
                    log.error("", sqle);
                }
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Guia Generada e Impresa con Exito!", null);
            } else {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Error al Momento de Generar las Guias e Imprimir!", null);
            }
            /** proceso de confirmacion de transferencia**/
            // dubilluz 16.10.2014


            log.debug("tratando de enviar el pedido de transferencia a local destino");
            log.debug("jcallo: verificando si hay linea con matriz");
            VariablesInventario.vIndLineaMatriz =
                    FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            log.debug("jcallo: VariablesInventario.vIndLineaMatriz:" + VariablesInventario.vIndLineaMatriz);
            //si hay linea con matriz, se intentara realizar la trasnferencia a matriz y local destino.
            //si ocurriera algun error, se realizara solo la confirmacion en local origen
            if (VariablesInventario.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)) {

                //kmoncada 09.06.2014 try-catch para evitar bloqueo de la tablas de transferencia
                //si hay linea con matriz, se intentara realizar la trasnferencia a matriz y local destino.
                //si ocurriera algun error, se realizara solo la confirmacion en local origen
                String resultado = "N";
                try {
                    resultado =
                            DBInventario.realizarTransfDestino(numera, VariablesTranfDelivery.vCodLocalDestino, FarmaConstants.INDICADOR_N).trim();
                    if (resultado.equals(FarmaConstants.INDICADOR_S)) {
                        //si la respuesta es afirmativa S, realiza el commit remoto
                        FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                              FarmaConstants.INDICADOR_S);
                        log.debug("actualizando el estado de la transferencia en local");
                        DBInventario.actualizarEstadoTransf(numera, "L");
                        FarmaUtility.aceptarTransaccion();
                    }
                } catch (Exception e) {
                    log.error("", e);
                    FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
                } finally {
                    log.debug("cerrando toda conexion remota");
                    FarmaConnectionRemoto.closeConnection();
                }


                /*
				log.debug("jcallo: tratando de realizar la transferencia a local destino y matriz con estado L");						
				String resultado = DBInventario.realizarTransfDestino(	numera,
																		VariablesTranfDelivery.vCodLocalDestino,
																		FarmaConstants.INDICADOR_N).trim();
				log.debug("despues de invocar a matriz RESPUESTA:"+resultado);
				if(resultado.equals(FarmaConstants.INDICADOR_S)){							
					FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
														  FarmaConstants.INDICADOR_S);
					log.debug("actualizando el estado de la transferencia en local");
					DBInventario.actualizarEstadoTransf(numera, "L");
					FarmaUtility.aceptarTransaccion();
				}else{
					FarmaUtility.liberarTransaccionRemota(  FarmaConstants.CONECTION_MATRIZ,
							  								FarmaConstants.INDICADOR_S);
				}
				log.debug("cerrando toda conexion remota");
				FarmaConnectionRemoto.closeConnection();
                            */
            }


            //FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Transferencia generada!", null);
            //Imprimir Comprobantes


            retorno = true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            if (sql.getErrorCode() == 20666) {
                FarmaUtility.showMessage(this,
                                         "Ha ocurrido un error al Generar la transferencia.\nEl pedido de Transferencia no se encuentra Activo !",
                                         null);

            } else {
                FarmaUtility.showMessage(this, "Ha ocurrido un error al grabar la transferencia.\n" +
                        sql.getMessage(), null);
            }

            retorno = false;
        } catch (Exception exc) {
            FarmaUtility.liberarTransaccion();
            log.error("", exc);
            FarmaUtility.showMessage(this, "Error en la aplicacion al grabar la transferencia.\n" +
                    exc.getMessage(), null);
            retorno = false;
        }

        return retorno;
    }

    private boolean separaStockProducto() {
        boolean valor = true;
        try {
            log.info("" + VariablesTranfDelivery.vCodProducto + "***" +
                     Integer.parseInt(VariablesTranfDelivery.vCantProductoTransf) + "***" +
                     ConstantsInventario.INDICADOR_A + "***" + ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR +
                     "***" + Integer.parseInt(VariablesTranfDelivery.vCantProductoTransf));
            actualizaStkComprometidoProd(VariablesTranfDelivery.vCodProducto,
                                         Integer.parseInt(VariablesTranfDelivery.vCantProductoTransf),
                                         ConstantsInventario.INDICADOR_A,
                                         ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR,
                                         Integer.parseInt(VariablesTranfDelivery.vCantProductoTransf));
        } catch (Exception e) {
            log.info("ERROR : " + e);
            valor = false;
        }
        return valor;
    }

    private void actualizaStkComprometidoProd(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido,
                                              String pTipoRespaldoStock, int pCantidadRespaldo) {
        try {
            //log.info("123456");
            DBInventario.actualizaStkComprometidoProd(pCodigoProducto, pCantidadStk, pTipoStkComprometido);
            //log.info("23456");
            DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto, "", pTipoRespaldoStock, pCantidadRespaldo,
                                            Integer.parseInt(VariablesTranfDelivery.vValFracProducto),
                                            ConstantsPtoVenta.MODULO_TRANSFERENCIA);
            //log.info("3456");
            //FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Error al Actualizar Stock del Producto.\n Ponganse en contacto con el area de Sistemas\n" +
                    sql.getMessage(), null);
        }
    }
}
