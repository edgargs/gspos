package mifarma.ptoventa.ventas.reference;


import com.gs.mifarma.componentes.JConfirmDialog;

import farmapuntos.bean.TarjetaBean;

import farmapuntos.util.FarmaPuntosConstante;

import java.awt.Frame;

import java.io.IOException;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.campana.reference.VariablesCampana;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.encuesta.reference.FacadeEncuesta;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.lealtad.DlgListaAcumulaX1;
import mifarma.ptoventa.lealtad.reference.FacadeLealtad;
import mifarma.ptoventa.puntos.reference.DBPuntos;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.DlgDetalleProducto;
import mifarma.ptoventa.ventas.DlgIngCodBarraNegativa;
import mifarma.ptoventa.ventas.DlgIngreseCodBarra;
import mifarma.ptoventa.ventas.DlgLoginVtaNegativa;
import mifarma.ptoventa.ventas.DlgRegistroPsicotropico;
import mifarma.ptoventa.ventas.DlgTratamiento;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilityVentas.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      26.03.2005   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class UtilityVentas {

    private static final Logger log = LoggerFactory.getLogger(UtilityVentas.class);

    /**
     * Constructor
     */
    public UtilityVentas() {
    }

    public static boolean evaluaPedidoDelivery(JDialog pJDialog, Object pObjectFocus, ArrayList pArrayListVenta) {
        if (!VariablesVentas.vEsPedidoDelivery) {
            if (pArrayListVenta.size() > 0) {
                FarmaUtility.showMessage(pJDialog,
                                         "Existen Productos Seleccionados. Para realizar un Pedido Delivery\n" +
                        "no deben haber productos seleccionados. Verifique!!!", pObjectFocus);
                return false;
            }
            VariablesVentas.vEsPedidoDelivery = true;
        } else {
            if (pArrayListVenta.size() > 0) {
                FarmaUtility.showMessage(pJDialog,
                                         "Existen Productos Seleccionados. Para realizar un Pedido Mostrador\n" +
                        "no deben haber productos seleccionados. Verifique!!!", pObjectFocus);
                return false;
            }
            VariablesVentas.vEsPedidoDelivery = false;
        }
        //evaluaTitulo(pJDialog);
        VariablesVentas.vArrayList_ResumenPedido.clear();
        VariablesVentas.vArrayList_PedidoVenta.clear();
        return true;
    }

    public static boolean evaluaPedidoConvenio(JDialog pJDialog, Object pObjectFocus, ArrayList pArrayListVenta) {
        if (!VariablesVentas.vEsPedidoConvenio) {
            if (pArrayListVenta.size() > 0 ||
                // DUBILLUZ 30.04.2015
                VariablesVentas.vArrayList_ResumenPedido.size() > 0 
            ) {
                FarmaUtility.showMessage(pJDialog,
                                         "Existen Productos Seleccionados. Para realizar un Pedido por Convenio\n" +
                        "no deben haber productos seleccionados. Verifique!!!", pObjectFocus);
                return false;
            }

            /*if(VariablesVentas.vArrayList_Cupones.size() > 0)
      {
        FarmaUtility.showMessage(pJDialog, "Existen Cupones Ingresados. Para realizar un Pedido por Convenio\n" +
          "no deben tener cupones agregados. Verifique!!!", pObjectFocus);
        return false;
      }*/

            VariablesVentas.vEsPedidoConvenio = true;
        } else {
            if (pArrayListVenta.size() > 0) {
                FarmaUtility.showMessage(pJDialog,
                                         "Existen Productos Seleccionados. Para realizar un Pedido Mostrador\n" +
                        "no deben haber productos seleccionados. Verifique!!!", pObjectFocus);
                return false;
            }
            VariablesVentas.vEsPedidoConvenio = false;
        }
        //evaluaTitulo(pJDialog);
        VariablesVentas.vArrayList_ResumenPedido.clear();
        VariablesVentas.vArrayList_PedidoVenta.clear();
        return true;
    }


    public static boolean evaluaPedidoInstitucional(JDialog pJDialog, Object pObjectFocus, ArrayList pArrayListVenta) {
        if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) {
            FarmaUtility.showMessage(pJDialog, "No es posible realizar esta operación. Solo un usuario con Rol\n" +
                    "Administrador Local puede realizar una venta institucional.", pObjectFocus);
            return false;
        }
        if (!VariablesVentas.vEsPedidoInstitucional) {
            if (pArrayListVenta.size() > 0) {
                FarmaUtility.showMessage(pJDialog,
                                         "Existen Productos Seleccionados. Para realizar un Pedido Institucional\n" +
                        "no deben haber productos seleccionados. Verifique!!!", pObjectFocus);
                return false;
            }
            VariablesVentas.vEsPedidoInstitucional = true;
        } else {
            if (pArrayListVenta.size() > 0) {
                FarmaUtility.showMessage(pJDialog,
                                         "Existen Productos Seleccionados. Para realizar un Pedido Mostrador\n" +
                        "no deben haber productos seleccionados. Verifique!!!", pObjectFocus);
                return false;
            }
            VariablesVentas.vEsPedidoInstitucional = false;
        }
        //evaluaTitulo(pJDialog);
        VariablesVentas.vArrayList_ResumenPedido.clear();
        VariablesVentas.vArrayList_PedidoVenta.clear();
        return true;
    }

    /**
     * Comprometer stock para ventas.
     * @param pCodigoProducto
     * @param pCantidadStk
     * @param pTipoStkComprometido
     * @param pTipoRespaldoStock
     * @param pCantidadRespaldo
     * @param pEjecutaCommit
     * @param pDialogo
     * @param pObjectFocus
     * @return
     * @author Edgar Rios Navarro
     * @since 29.05.2008
     */
    public static boolean actualizaStkComprometidoProd(String pCodigoProducto, int pCantidadStk,
                                                       String pTipoStkComprometido, String pTipoRespaldoStock,
                                                       int pCantidadRespaldo, boolean pEjecutaCommit, JDialog pDialogo,
                                                       Object pObjectFocus) {
        try {
            log.debug("$$$$$$$$$$$$$$$$$$$$$ JUSTO ANTES DE actualizaStkComprometidoProd");
            DBVentas.actualizaStkComprometidoProd(pCodigoProducto, pCantidadStk, pTipoStkComprometido);

            log.debug("$$$$$$$$$$$$$$$$$$$$$ JUSTO ANTES DE ejecutaRespaldoStock");
            DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto, "", pTipoRespaldoStock, pCantidadRespaldo,
                                            Integer.parseInt(VariablesVentas.vVal_Frac),
                                            ConstantsPtoVenta.MODULO_VENTAS);

            if (pEjecutaCommit) {
                FarmaUtility.aceptarTransaccion();
            }

            return true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            //log.error("",sql);
            log.error(null, sql);
            FarmaUtility.showMessage(pDialogo, "Error al Actualizar Stock del Producto.\n" +
                    "Ponganse en contacto con el area de Sistemas.\n" +
                    "Error - " + sql.getMessage(), pObjectFocus);
            return false;
        }
    }

    /**
     * Se valida los datos del cupon.
     * en local, ya no se valida en matriz
     * @param cadena
     * @return
     * @author javier Callo Quispe
     * @since 04.03.2009
     */
    public static boolean validarCuponEnBD(String nroCupon, JDialog pDialogo, JTextField pProducto, String indMultiUso,
                                           String dniCliente) {
        // Se modifico la logica de validacion Cupon
        boolean retorno = true;

        try {
            //Se valida el Cupon en el local
            DBVentas.validarCuponEnBD(nroCupon, indMultiUso, dniCliente);
            /**se quito la validacion de indicador de linea con matriz y el hecho validar en matriz el cupon**/

        } catch (SQLException e) {
            retorno = false;
            log.error("", e);
            pProducto.setText("");
            switch (e.getErrorCode()) {
            case 20003:
                FarmaUtility.showMessage(pDialogo, "La campaña no es valida.", pProducto);
                break;
            case 20004:
                FarmaUtility.showMessage(pDialogo, "Local no valido para el uso del cupon.", pProducto);
                break;
            case 20005:
                FarmaUtility.showMessage(pDialogo, "Local de emisión no valido.", pProducto);
                break;
            case 20006:
                FarmaUtility.showMessage(pDialogo, "Local de emisión no es local de venta.", pProducto);
                break;
            case 20007:
                FarmaUtility.showMessage(pDialogo, "Cupón ya fue usado.", pProducto);
                break;
            case 20008:
                FarmaUtility.showMessage(pDialogo, "Cupón esta anulado.", pProducto);
                break;
            case 20009:
                FarmaUtility.showMessage(pDialogo, "Campaña no valido.", pProducto);
                break;
            case 20010:
                FarmaUtility.showMessage(pDialogo, "Cupon solo de uso para Fidelizados.", pProducto);
                break;
            case 20011:
                FarmaUtility.showMessage(pDialogo, "Cupon no esta vigente .", pProducto);
                break;
            default:
                FarmaUtility.showMessage(pDialogo, "Error al validar el cupon.\n" +
                        e.getMessage(), pProducto);
                break;
            }
        }
        log.debug("Retorno :" + retorno);

        return retorno;
    }

    /**
     * Se valida los datos del cupon.
     * @param cadena
     * @return
     * @author Edgar Rios Navarro
     * @since 03.07.2008
     * @deprecated
     */
    public static boolean validaDatoCupon(String cadena, JDialog pDialogo, JTextField pProducto,
                                          String indMultiUso) { // Se modifico la logica de valicacion Cupon
        boolean retorno;
        ArrayList arreglo = new ArrayList();
        ArrayList cupon = new ArrayList();
        ArrayList auxcamp = new ArrayList();
        String valida, descCamp, codCupon;
        String vIndLinea = "";
        log.debug("***validaDatoCupon***");
        try {
            //Se valida el Cupon en el local
            //Modificado por DVELIZ 04.10.08
            DBVentas.verificaCupon(cadena, arreglo, indMultiUso, VariablesFidelizacion.vDniCliente, "");
            valida = FarmaConstants.INDICADOR_S;
            //Se verifica si hay linea para validar el cupon en Matriz
            vIndLinea = FarmaConstants.INDICADOR_N;
            /*
                     * FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                   FarmaConstants.INDICADOR_N);
                    */

            log.debug("vIndLinea " + vIndLinea);
            /*
        if(vIndLinea.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
           valida = DBVentas.verificaCuponMatriz(cadena,indMultiUso,FarmaConstants.INDICADOR_S);
        */
            //--Fin de validacion en Matriz

            if (valida.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                VariablesVentas.vArrayList_Cupones.add(arreglo.get(0));
                codCupon = ((String)((ArrayList)arreglo.get(0)).get(0)).trim();
                DBVentas.obtieneInfoCamp(auxcamp, ((String)((ArrayList)(arreglo.get(0))).get(1)).trim());
                if (auxcamp.size() > 0) {
                    descCamp = ((String)((ArrayList)auxcamp.get(0)).get(1)).trim();
                    VariablesVentas.vMensCuponIngre =
                            "Se ha agregado el cupón " + codCupon + " de la Campaña " + descCamp + ".";
                    VariablesCampana.vDescCamp = descCamp;
                    FarmaUtility.showMessage(pDialogo, VariablesVentas.vMensCuponIngre, pProducto);
                }

                FarmaUtility.ordenar(VariablesVentas.vArrayList_Cupones, 9, FarmaConstants.ORDEN_ASCENDENTE);

                retorno = true;
            } else if (valida.trim().equalsIgnoreCase("B")) {
                retorno = false;
            } else {
                retorno = false;
                pProducto.setText("");
                FarmaUtility.showMessage(pDialogo, valida.trim(), pProducto);
            }

        } catch (SQLException e) {
            retorno = false;
            log.error(null, e);
            pProducto.setText("");
            log.error(null, e);
            switch (e.getErrorCode()) {
            case 20003:
                FarmaUtility.showMessage(pDialogo, "La campaña no es valida.", pProducto);
                break;
            case 20004:
                FarmaUtility.showMessage(pDialogo, "Local no valido para el uso del cupon.", pProducto);
                break;
            case 20005:
                FarmaUtility.showMessage(pDialogo, "Local de emisión no valido.", pProducto);
                break;
            case 20006:
                FarmaUtility.showMessage(pDialogo, "Local de emisión no es local de venta.", pProducto);
                break;
            case 20007:
                FarmaUtility.showMessage(pDialogo, "Cupón ya fue usado.", pProducto);
                break;
            case 20008:
                FarmaUtility.showMessage(pDialogo, "Cupón esta anulado.", pProducto);
                break;
            case 20009:
                FarmaUtility.showMessage(pDialogo, "Campaña no valido.", pProducto);
                break;

                //Agregado por DVELIZ 04.10.08
            case 20010:
                FarmaUtility.showMessage(pDialogo, "Cupon solo de uso para Fidelizados.", pProducto);
                break;

            case 20011:
                FarmaUtility.showMessage(pDialogo, "Cupon no esta vigente .", pProducto);
                break;
            default:
                FarmaUtility.showMessage(pDialogo, "Error al validar el cupon.\n" +
                        e.getMessage(), pProducto);
                break;
            }
        }
        log.debug("lista CUPONES...");
        //
        log.debug("...LisCupones:" + VariablesVentas.vArrayList_Cupones);
        return retorno;
    }


    /**
     *
     * @param cadena
     * @return
     * @author Edgar Rios Navarro
     * @since 10.07.2008
     */
    public static boolean validaCampanaCupon(String cadena, JDialog pDialogo, JTextField pProducto, String indMultiUso,
                                             String codCamp) {
        boolean retorno = false;
        String vCodCamp = "";
        if (indMultiUso.equalsIgnoreCase("N"))
            vCodCamp = cadena.substring(0, 5);
        else
            vCodCamp = codCamp;

        for (int i = 0; i < VariablesVentas.vArrayList_Cupones.size(); i++) {
            String vAuxCamp = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Cupones, i, 1);
            String vTipCupon = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Cupones, i, 2);

            if (vAuxCamp.equals(vCodCamp) && vTipCupon.equalsIgnoreCase("P")) {
                retorno = true;
                pProducto.setText("");
                FarmaUtility.showMessage(pDialogo, "Esta campaña ya fue agregado al pedido.", pProducto);
                break;
            }
        }
        VariablesCampana.vCodCampana = vCodCamp;
        return retorno;
    }

    /**
     * Se verifica que el cupon no exista en el arreglo de cupones.
     * @param cadena
     * @return
     * @author Edgar Rios Navarro
     * @since 04.07.2008
     */
    public static boolean validaCupones(String cadena, JDialog pDialogo, JTextField pProducto) {
        boolean retorno = false;

        for (int i = 0; i < VariablesVentas.vArrayList_Cupones.size(); i++) {
            ArrayList aux = (ArrayList)VariablesVentas.vArrayList_Cupones.get(i);
            if (aux.contains(cadena)) {
                retorno = true;
                pProducto.setText("");
                FarmaUtility.showMessage(pDialogo, "El cupon ya fue agregado al pedido.", pProducto);
                break;
            }
        }

        return retorno;
    }

    /**
     * Se verifica que la cadena ingresada corresponda a un cupon.
     * @param pCadena
     * @return
     * @author Edgar Rios Navarro
     * @since 02.07.2008
     */
    public static boolean esCupon(String pCadena, JDialog pDialogo, JTextField pProducto) {
        log.info("Valida esCupon");
        boolean retorno = false;
        boolean isCupon = false;
        int pTamano = pCadena.length();

        //obtiene indicador de multiuso de la campaña
        String ind_multiuso = "";
        ArrayList aux = new ArrayList();
        try {
            DBVentas.obtieneIndMultiuso(aux, pCadena);
            log.debug("", aux);
            log.info("aux:" + aux);
            if (aux.size() > 0) {
                ind_multiuso = (String)((ArrayList)aux.get(0)).get(1);
                isCupon = true;
            }
        } catch (SQLException sql) {
            log.error("", sql);
        }

        if (pTamano > 1) {
            log.info("if(pTamano > 1)");
            //se valida el codigo de barra a nivel de local
            if (isCupon && // si es CUPON
                isNumerico(pCadena) && // si es NUMERO
                pTamano == 13 && // si es un posible EAN 13
                validaCodBarraLocal(pCadena, pDialogo, pProducto)) // si NO ES CODIGO DE BARRA
            {
                log.info(" retorno true");
                retorno = true;

            }
        }
        log.info("retorno :" + retorno);
        return retorno;
    }

    /**
     * Se valida el codigo de barra a nivel de local
     * @since 28.08.08
     * @author JCORTEZ
     * */
    private static boolean validaCodBarraLocal(String cadena, JDialog pDialogo, JTextField pProducto) {

        boolean retorno = true;
        String valida = "";

        try {
            valida = DBVentas.verificaCodBarraLocal(cadena);
            if (valida.equalsIgnoreCase("S")) {
                retorno = false;
                log.debug("El codigo de barra " + cadena + " existe en local");
            } else {
                retorno = true;
                log.debug("El codigo de barra " + cadena + " No existe en local");
            }
        } catch (SQLException e) {
            log.error("", e);
        }

        log.info("validaCodBarraLocal:" + retorno);
        return retorno;
    }

    public static boolean isNumerico(String pcadena) {
        char vCaracter;
        if (pcadena.length() == 0)
            return false;
        for (int i = 0; i < pcadena.length(); i++) {
            vCaracter = pcadena.charAt(i);
            if (Character.isLetter(vCaracter))
                return false;
        }
        return true;
    }


    public static void eliminaImagenesCodBarra() {
        try {
            //ERIOS 25.06.2013 Utilizamos el paquete java.nio

            String carpetaRaiz = DBPtoVenta.getDirectorioRaiz();
            String carpeta = DBPtoVenta.getDirectorioImagenes();

            Path dir = Paths.get(carpetaRaiz, carpeta);
            if (Files.exists(dir)) {
                try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir, "1*.jpg")) {
                    for (Path p : ds) {
                        Files.delete(p);
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }

            //ERIOS 2.4.4 Se elimina archivos antiguos
            // Creating the filter
            DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {

                int ndias = Integer.parseInt(DBCaja.ObtieneNroDiasEliminar());

                @Override
                public boolean accept(Path entry) throws IOException {
                    BasicFileAttributes basicAttr = Files.readAttributes(entry, BasicFileAttributes.class);
                    //FileTime creationTime = basicAttr.creationTime();
                    FileTime creationTime = basicAttr.lastModifiedTime();
                    long millis = creationTime.to(TimeUnit.MILLISECONDS);

                    Calendar today = Calendar.getInstance();

                    // conseguir la representacion de la fecha en milisegundos
                    long milis1 = millis;
                    long milis2 = today.getTimeInMillis();

                    // calcular la diferencia en milisengundos
                    long diff = milis2 - milis1;

                    // calcular la diferencia en dias
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    return diffDays > ndias;
                }
            };

            if (Files.exists(dir)) {
                try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir, filter)) {
                    for (Path p : ds) {
                        Files.delete(p);
                        //log.debug("Elimina "+p.getFileName());
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * Elimina los Archivos de Texto con antiguedad mayor a 2 dias
     * @return
     * @author DUBILLUZ
     * @since 08.07.09
     * @throws Exception
     */
    public static void eliminaArchivoTxt() throws Exception {
        //ERIOS 25.06.2013 Utilizamos el paquete java.nio

        String carpetaRaiz = DBPtoVenta.getDirectorioRaiz();
        String carpetaComprobantes = DBPtoVenta.getDirectorioComprobantes();

        Path dir = Paths.get(carpetaRaiz, carpetaComprobantes);

        // Creating the filter
        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {

            String pListaFecha = "";
            ArrayList<String> pLista = new ArrayList<String>();
            Calendar cFecha = Calendar.getInstance();
            String DATE_FORMAT = "yyyyMMdd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            int ndias = Integer.parseInt(DBCaja.ObtieneNroDiasEliminar());

            {
                for (int i = ndias * -1; i < 0; i++) {
                    cFecha = Calendar.getInstance();
                    cFecha.add(Calendar.DATE, i + 1);

                    pListaFecha = sdf.format(cFecha.getTime());
                    pLista.add(pListaFecha);
                }
            }

            public boolean accept(Path entry) throws IOException {
                boolean vRetorno = true;
                String fileName = entry.getFileName().toString();
                for (String fecha : pLista) {
                    if (fileName.toLowerCase().endsWith("txt") && fileName.startsWith(fecha)) {
                        vRetorno = false;
                        break;
                    }
                }
                return vRetorno;
            }
        };

        if (Files.exists(dir)) {
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir, filter)) {
                for (Path p : ds) {
                    Files.delete(p);
                    //log.debug("Elimina "+p.getFileName());
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }


    /**
     * este metodo obtiene los descuentos para actualizacion de pedido vta detalle
     * @author dveliz
     * @since 09.10.08
     * @param codProd
     * @param porcDcto1
     * @param codCampCupon
     * @param ahorro
     * @param porcDctoCalc
     */
    public static void obtieneDctosActualizaPedidoDetalle(String codProd, String porcDcto1, String codCampCupon,
                                                          String ahorro, String porcDctoCalc) {
        /*
        VariablesVentas.vActDctoDetPedVta = new ArrayList();
        VariablesVentas.vActDctoDetPedVta.add(codProd);
        VariablesVentas.vActDctoDetPedVta.add(porcDcto1);
        VariablesVentas.vActDctoDetPedVta.add(codCampCupon);
        VariablesVentas.vActDctoDetPedVta.add(ahorro);
        VariablesVentas.vActDctoDetPedVta.add(porcDctoCalc);
        */
        // 19.02.2009 DUBILLUZ

        log.debug("diego 11 ");
        ArrayList vActDctoDetPedVta = new ArrayList();
        log.debug("diego 22 vActDctoDetPedVta" + vActDctoDetPedVta);
        vActDctoDetPedVta.add(codProd);
        log.debug("diego 33 vActDctoDetPedVta" + vActDctoDetPedVta);
        vActDctoDetPedVta.add(porcDcto1);
        log.debug("diego 44 vActDctoDetPedVta" + vActDctoDetPedVta);
        vActDctoDetPedVta.add(codCampCupon);
        log.debug("diego 55 vActDctoDetPedVta" + vActDctoDetPedVta);
        vActDctoDetPedVta.add(ahorro);
        log.debug("diego 66 vActDctoDetPedVta" + vActDctoDetPedVta);
        vActDctoDetPedVta.add(porcDctoCalc);
        log.debug("obtieneDctosActualizaPedidoDetalle " + vActDctoDetPedVta);
        log.debug("VariablesVentas.vResumenActDctoDetPedVta " + VariablesVentas.vResumenActDctoDetPedVta);
        VariablesVentas.vResumenActDctoDetPedVta.add(vActDctoDetPedVta);
        log.debug("VariablesVentas.vResumenActDctoDetPedVta " + VariablesVentas.vResumenActDctoDetPedVta);
    }

    /**
     * Retorna true si el producto si aplico el precio de la campana cupon
     * Esto incluye para fidelizacion y campañas automaticas.
     * @param pCodProd
     * @return
     */
    public static boolean isAplicoPrecioCampanaCupon(String pCodProd, String pIndProdCamp) {

        String pCodAux = "";
        log.debug("jcallo: metodo isAplicoPrecioCampanaCupon() VariablesVentas.vListaProdAplicoPrecioDescuento : " +
                  VariablesVentas.vListaProdAplicoPrecioDescuento);
        if (VariablesVentas.vListaProdAplicoPrecioDescuento.size() > 0) {
            for (int i = 0; i < VariablesVentas.vListaProdAplicoPrecioDescuento.size(); i++) {
                pCodAux = (String)VariablesVentas.vListaProdAplicoPrecioDescuento.get(i);
                if (pCodAux.trim().equalsIgnoreCase(pCodProd.trim())) {
                    return true;
                }
            }
        }


        if (pIndProdCamp.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;

        log.debug("RETORNA FALSE el metodo isAplicoPrecioCampanaCupon");

        return false;
    }

    /**
     * Se verifica si el cupon ya fue agregado
     * tambien verifica si ya existe la campaña
     * @param nroCupon
     * @author Javier Callo Quispe
     * @since  04.03.2009
     */
    public static boolean existeCuponCampana(String nroCupon, JDialog pDialogo, JTextField pProducto) {
        boolean retorno = false;
        Map mapCupon;
        log.debug("nroCupon:" + nroCupon);
        String codCampCupon = nroCupon.substring(0, 5);
        log.debug("codCampCupon:" + codCampCupon);
        String auxCodCupon = "";
        String auxCodCampCupon = "";
        for (int i = 0; i < VariablesVentas.vArrayList_Cupones.size(); i++) {
            mapCupon = (Map)VariablesVentas.vArrayList_Cupones.get(i);
            auxCodCupon = (String)mapCupon.get("COD_CUPON");
            auxCodCampCupon = (String)mapCupon.get("COD_CAMP_CUPON");

            if (nroCupon.equalsIgnoreCase(auxCodCupon)) {
                retorno = true;
                pProducto.setText("");
                FarmaUtility.showMessage(pDialogo, "El cupon ya fue agregado al pedido.", pProducto);
                break;
            }

            if (codCampCupon.equalsIgnoreCase(auxCodCampCupon)) {
                retorno = true;
                pProducto.setText("");
                FarmaUtility.showMessage(pDialogo,
                                         "Esta campaña ya fue agregado al pedido." + VariablesVentas.vArrayList_Cupones.size(),
                                         pProducto);
                break;
            }

        }

        return retorno;
    }

    /**
     * productos con campanias aplicables
     * **/
    public static List prodsCampaniasAplicables(List listProds, List listCamps, ArrayList pListaProd) {
        List listProdCamps = new ArrayList();
        try {
            listProdCamps = DBVentas.prodsCampaniasAplicables(listProds, listCamps);

        } catch (SQLException se) {
            log.error("ERRORSQL AL OBTENER EL LISTADO de productos campañas aplicables:" + se);
        } catch (Exception e) {
            log.error("ERROR AL OBTENER EL LISTADO de productos campañas aplicables:" + e);
        }

        return listProdCamps;

    }

    /**
     *
     * metodo encargado de redondear un double nD, a n decimales nDEC
     * @param nD valor a redondear
     * @param nDec cantidad decimales a redondear
     *
     * */
    public static double Redondear(double nD, int nDec) {
        return Math.round(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);
    }

    /**
     *
     * metodo encargado de truncar un double nD, a n decimales nDEC
     * @param nD valor a truncar
     * @param nDec cantidad decimales a truncar
     *
     * */
    public static double Truncar(double nD, int nDec) {
        if (nD > 0)
            nD = Math.floor(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);
        else
            nD = Math.ceil(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);
        return nD;
    }

    /**
     * metodo encargado de ajustar redondear siempre abajo
     * que termine en 0 ó 5
     *
     * @param nD valor a ajustar el monto que termine en 0 ó 5  la ultima cifra
     * @param nDec cantidad decimales a ajustar
     *
     * */
    /*
        public static double ajustarMonto(double nD, int nDec)
	{
		
		long aux = 0L;
		long resto = 0L;
		
		if(nD > 0){
			//nD = Math.floor(nD * Math.pow(10,nDec))/Math.pow(10,nDec);
			aux  = (long)Math.floor(nD * Math.pow(10,nDec));			
			resto = aux%10;
			aux = aux/10;
			if(resto < 5)
				resto = 0;
			else
				resto = 5;
			nD = ((double)(aux*10+resto))/Math.pow(10,nDec);
		} else {
			//nD = Math.ceil(nD * Math.pow(10,nDec))/Math.pow(10,nDec);
			aux  = (long)Math.ceil(nD * Math.pow(10,nDec));
			aux  = (long)Math.floor(nD * Math.pow(10,nDec));			
			resto = aux%10;
			aux = aux/10;
			if(resto < 5)
				resto = 5;
			else
				resto = 0;
			nD = ((double)(aux*10+resto))/Math.pow(10,nDec);
		}
		return nD;
	}

    */
    //14.10.2009 jcortez
    /*reemplazo de
     * aux  = (long)Math.floor(nD * Math.pow(10,nDec));
     * por
     *                     aux  = (long) FarmaUtility.getDecimalNumber(
                                        FarmaUtility.formatNumber(
                                            Math.pow(10,nDec) * nD,
                                            nDec
                                            )
                                        );
     * */

    /**
     * @author joliva
     * @since  14.10.2009
     * @param nD
     * @param nDec
     * @return
     */
    public static double ajustarMonto(double nD, int nDec) {
        //log.debug("nD old:"+nD);

        long aux = 0L;
        long resto = 0L;

        int signo = 1;
        if (nD < 0)
            signo = -1;

        //log.debug("signo:"+signo);
        nD = nD * signo;
        //log.debug("nD pw:"+nD);
        aux = (long)FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(Math.pow(10, nDec) * nD, nDec));

        //log.debug("1) aux:"+aux);

        resto = aux % 10;
        //log.debug("2) resto:"+resto);
        aux = aux / 10;

        //log.debug("3) aux:"+aux);

        if (resto < 5)
            resto = 0;
        else
            resto = 5;

        //log.debug("3) aux:"+aux);

        nD = ((double)(aux * 10 + resto)) / Math.pow(10, nDec);

        //log.debug("4) nD:"+nD);
        nD = nD * signo;
        //log.debug("nD Nuevo:"+nD);
        return nD;
    }

    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09

    public static void carga_impresoras(JFrame myParentFrame) {
        PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null, null);
        boolean pEncontroImp = false;
        if (servicio != null) {
            try {
                String pNombreImpresora = "";
                //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
                VariablesPtoVenta.vIndExisteImpresoraConsejo = DBCaja.obtieneNameImpConsejos();
                VariablesPtoVenta.vTipoImpTermicaxIp =
                        DBCaja.obtieneTipoImprConsejoXIp(); //JCHAVEZ 03.07.2009 obtiene tipo de imopresora por IP
                log.debug("Tipo Impresora :" + VariablesPtoVenta.vTipoImpTermicaxIp);
                log.debug("Buscando impresora :" + VariablesPtoVenta.vIndExisteImpresoraConsejo);
                log.debug("impresoras..encontradas...");
                for (int i = 0; i < servicio.length; i++) {
                    PrintService impresora = servicio[i];
                    String pNameImp = impresora.getName().toString().trim();
                    pNombreImpresora = retornaUltimaPalabra(pNameImp, "\\").trim();
                    //if (pNameImp.toUpperCase().indexOf(VariablesPtoVenta.vIndExisteImpresoraConsejo.toUpperCase()) != -1)
                    //Buscara el nombre.
                    log.debug(i + ") pNameImp:" + pNameImp);
                    log.debug(i + ") pNombreImpresora:" + pNombreImpresora);
                    log.debug("**************************************");
                    if (pNombreImpresora.trim().toUpperCase().equalsIgnoreCase(VariablesPtoVenta.vIndExisteImpresoraConsejo.toUpperCase())) {
                        log.info("Encotró impresora térmica");
                        pEncontroImp = true;
                        VariablesPtoVenta.vImpresoraActual = impresora;
                        break;
                    }
                }

                /**0
                      * 03/07/2009
                      * dubilluz
                      * se genero error en produccion
                      * String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();

                     for (int i = 0; i < servicio.length; i++)
                     {
                       PrintService impresora = servicio[i];
                       String pNameImp = impresora.getName().toString().trim();

                       if (pNameImp.indexOf(vIndExisteImpresora) != -1)
                       {
                         VariablesPtoVenta.vImpresoraActual =  impresora;
                         break;
                       }
                     } */

            } catch (SQLException sqlException) {
                log.error(null, sqlException);
            }
        }
        if (!pEncontroImp) {
            FarmaUtility.showMessage(myParentFrame,
                                     "No se encontró la impresora de térmica :" + VariablesPtoVenta.vIndExisteImpresoraConsejo +
                                     "\nVerifique que tenga instalada la impresora.", null);
        }
    }

    public static String retornaUltimaPalabra(String pCadena, String pSeparador) {
        //log.debug(pCadena);
        //log.debug(pSeparador);
        String pLetra = "";
        String pPalabraOut = "";
        for (int i = pCadena.length() - 1; i >= 0; i--) {
            pLetra = pCadena.charAt(i) + "";
            if (pLetra.trim().equalsIgnoreCase(pSeparador.trim())) {
                break;
            } else {
                pPalabraOut = pLetra + pPalabraOut;
            }
        }
        return pPalabraOut.trim();
    }

    //JMIRANDA 23.09.09

    public static boolean validaCodBarraLocal(String cadena) {

        boolean retorno = true;
        String valida = "";

        try {
            valida = DBVentas.verificaCodBarraLocal(cadena);
            if (valida.equalsIgnoreCase("S")) {
                retorno = false;
                log.debug("2. El codigo de barra " + cadena + " existe en local");
            } else {
                log.debug("2. El codigo de barra " + cadena + " No existe en local");
            }
        } catch (SQLException e) {
            log.error("", e);
        }
        return retorno;
    }

    /**
     * Genera Salto de Linea al traer Mensaje de base de Datos con limitador definido en tab_gral
     * @author JMIRANDA
     * @since  29.09.2009
     * @return sMensaje  Mensaje Editado
     * @param pMensaje   Mensaje al que se le va realizar el salto de linea
     * */
    public static String saltoLineaConLimitador(String pMensaje) {
        String sMensaje = "";
        try {
            String[] temp;
            //String delimiter = "_";
            String delimiter = DBVentas.getDelimitadorMensaje();
            temp = pMensaje.split(delimiter);

            for (int i = 0; i < temp.length; i++) {
                sMensaje += temp[i] + "\n";
            }

        } catch (SQLException sql) {
            log.debug("Error al obtener limitador del Mensaje. " + sql);
        }
        return sMensaje;
    }

    /**
     * Opera el stock comprometido, copia modificada de otro metodo
     * @author ASOSA
     * @since 01.07.2010
     * @param pCodigoProducto
     * @param pCantidadStk
     * @param pTipoStkComprometido
     * @param pTipoRespaldoStock
     * @param pCantidadRespaldo
     * @param pEjecutaCommit
     * @param pDialogo
     * @param pObjectFocus
     * @return
     */
    public static boolean operaStkCompProdResp(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido,
                                               String pTipoRespaldoStock, int pCantidadRespaldo,
                                               boolean pEjecutaCommit, JDialog pDialogo, Object pObjectFocus,
                                               String secRespaldo) {
        /*try
      {*/
        /*
        log.debug("$$$$$$$$$$$$$$$$$$$$$ JUSTO ANTES DE actualizaStkComprometidoProd");
        DBVentas.actualizaStkComprometidoProd(pCodigoProducto, pCantidadStk,
                                              pTipoStkComprometido);
        log.debug("$$$$$$$$$$$$$$$$$$$$$ JUSTO ANTES DE ejecutaRespaldoStock");
        DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto, "",
                                        pTipoRespaldoStock,
                                        pCantidadRespaldo,
                                        Integer.parseInt(VariablesVentas.vVal_Frac),
                                        ConstantsPtoVenta.MODULO_VENTAS);
        */
        /*
         VariablesVentas.secRespStk=DBVentas.operarResStkAntesDeCobrar(pCodigoProducto,
                                                                       String.valueOf(pCantidadStk),
                                                                       VariablesVentas.vVal_Frac,
                                                                       secRespaldo,
                                                                       ConstantsPtoVenta.MODULO_VENTAS);
         */
        VariablesVentas.secRespStk = "0";
        boolean flag = true;
        /*boolean flag=true;
          if(VariablesVentas.secRespStk.trim().equalsIgnoreCase("N")){
            FarmaUtility.liberarTransaccion();
            flag=false;
        }else{
            FarmaUtility.aceptarTransaccion();
            flag=true;
        }*/

        return flag;
        /*
      }
      catch (SQLException sql)
      {
        FarmaUtility.liberarTransaccion();
        //log.error("",sql);
        log.error(null,sql);
        FarmaUtility.showMessage(pDialogo,
                                 "Error al Actualizar Stock del Producto.\n" +
                                 "Ponganse en contacto con el area de Sistemas.\n" +
                                 "Error - " +
                                 sql.getMessage(), pObjectFocus);
        return false;
      }
        */
    }

    public static void calculaPrecioVenta(){
        double auxPrecVta = FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta);
        double auxCantIngr = FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada);

        //ERIOS 03.06.2008 Cuando se ingresa por tratamiento, el total es el calculado
        //y el precio de venta unitario se recalcula.
        if (VariablesVentas.vIndTratamiento.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            VariablesVentas.vTotalPrecVtaProd = VariablesVentas.vTotalPrecVtaTra;
            VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd / auxCantIngr, 3);
        } else if (
                   !VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)) //ERIOS 18.06.2008 Se redondea el total de venta por producto
        {
            //JCHAVEZ 29102009 redondeo inicio
            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                try {
                    VariablesVentas.vIndAplicaRedondeo = DBVentas.getIndicadorAplicaRedondedo();

                } catch (SQLException ex) {
                    log.error("", ex);
                }
            }

            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
                log.debug("vIndAplicaRedondeo: " + VariablesVentas.vIndAplicaRedondeo);
                VariablesVentas.vTotalPrecVtaProd = (auxCantIngr * auxPrecVta);
                VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd / auxCantIngr, 3);
                log.debug("VariablesVentas.vTotalPrecVtaProd : " + VariablesVentas.vTotalPrecVtaProd);
                log.debug("VariablesVentas.vVal_Prec_Vta : " + VariablesVentas.vVal_Prec_Vta);
            }
            //JCHAVEZ 29102009 redondeo fin
            else {
                VariablesVentas.vTotalPrecVtaProd = (auxCantIngr * auxPrecVta);
                //El redondeo se ha dos digitos hacia arriba ha 0.05.
                /*TO_CHAR( CEIL(VAL_PREC_VTA*100)/100 +
                                 CASE WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) = 0.0 THEN 0.0
                                      WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) <= 0.5 THEN
                                           (0.5 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10
                                      ELSE (1.0 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10 END ,'999,990.000') || 'Ã' ||*/

                double valVtaProd = VariablesVentas.vTotalPrecVtaProd * 100;
                valVtaProd = FarmaUtility.getDecimalNumber((FarmaUtility.formatNumber(valVtaProd, 4)).trim());
                valVtaProd = Math.ceil(valVtaProd);
                double aux1 = valVtaProd / 100; ///Math.ceil(VariablesVentas.vTotalPrecVtaProd*100)/100;
                double aux2 = valVtaProd / 10; ///Math.ceil(VariablesVentas.vTotalPrecVtaProd*100)/10;

                int aux21 = (int)(aux2 * 10);
                int aux3 = FarmaUtility.trunc(aux2) * 10;
                int aux4 = 0;
                double aux5;

                // --inicio añadido error producto 510991 25.06.2008
                if (aux3 == 0)
                    aux4 = 0;
                else
                    aux4 = aux21 % aux3;

                if (aux4 == 0) {
                    aux5 = 0;
                } else if (aux4 <= 5) {
                    aux5 = (5.0 - aux4) / 100;
                } else {
                    aux5 = (10.0 - aux4) / 100;
                }

                VariablesVentas.vTotalPrecVtaProd = aux1 + aux5;
                VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd / auxCantIngr, 3);
            }
        }
    }

    public static void operaProductoSeleccionadoEnArrayList_02(Boolean valor, String secRespStk) {
        double auxPrecVta = FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta);
        double auxPorcIgv = FarmaUtility.getDecimalNumber(VariablesVentas.vPorc_Igv_Prod);
        double auxCantIngr = FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada);
        String valIgv =
            FarmaUtility.formatNumber((auxPrecVta - (auxPrecVta / (1 + (auxPorcIgv / 100)))) * auxCantIngr);
        VariablesVentas.vVal_Igv_Prod = valIgv;

        calculaPrecioVenta();

        ArrayList myArray = new ArrayList();
        myArray.add(VariablesVentas.vCod_Prod); //0
        myArray.add(VariablesVentas.vDesc_Prod);
        myArray.add(VariablesVentas.vUnid_Vta);
        log.debug("VariablesVentas.vVal_Prec_Vta 3: " + VariablesVentas.vVal_Prec_Vta);

        //myArray.add(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta),3));
        myArray.add(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta) /
                                              FarmaUtility.getDecimalNumber(VariablesVentas.vValorMultiplicacion),
                                              3)); //ASOSA - 12/08/2014

        //myArray.add(VariablesVentas.vCant_Ingresada);
        Integer cantidad =
            Integer.parseInt(VariablesVentas.vCant_Ingresada) * Integer.parseInt(VariablesVentas.vValorMultiplicacion);
        myArray.add(cantidad.toString()); //ASOSA - 12/08/2014


        myArray.add(""); //myArray.add(VariablesVentas.vPorc_Dcto_1);se supone que este descuento ya no se aplica
        log.debug("VariablesVentas.vVal_Prec_Lista: " + VariablesVentas.vVal_Prec_Lista);
        myArray.add(VariablesVentas.vVal_Prec_Lista);
        myArray.add(FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd, 2));
        myArray.add(VariablesVentas.vVal_Bono);
        myArray.add(VariablesVentas.vNom_Lab); //9
        myArray.add(VariablesVentas.vVal_Frac);
        myArray.add(VariablesVentas.vPorc_Igv_Prod);
        myArray.add(VariablesVentas.vVal_Igv_Prod);
        myArray.add(VariablesVentas.vNumeroARecargar); //NUMERO TELEFONICO SI ES RECARGA AUTOMATICA
        myArray.add(VariablesVentas.vIndProdVirtual); //INDICADOR DE PRODUCTO VIRTUAL
        myArray.add(VariablesVentas.vTipoProductoVirtual); //TIPO DE PRODUCTO VIRTUAL
        myArray.add(VariablesVentas.vIndProdControlStock ? FarmaConstants.INDICADOR_S :
                    FarmaConstants.INDICADOR_N); //INDICADOR PROD CONTROLA STOCK
        myArray.add(VariablesVentas.vVal_Prec_Lista_Tmp); //PRECIO DE LISTA ORIGINAL SI ES QUE SE MODIFICO
        myArray.add(VariablesVentas.vVal_Prec_Pub);
        myArray.add(VariablesVentas.vIndOrigenProdVta); //19
        myArray.add(FarmaConstants.INDICADOR_N); //20 Indicador Promocion
        myArray.add(VariablesVentas.vPorc_Dcto_2); //21
        myArray.add(VariablesVentas.vIndTratamiento); //22
        myArray.add(VariablesVentas.vCantxDia); //23
        myArray.add(VariablesVentas.vCantxDias); //24
        myArray.add(""); //25
        myArray.add(secRespStk); //ASOSA, 01.07.2010
        log.info("<<TCT 1>>Producto agregado al pedidoVenta: " + myArray);

        myArray.add(VariablesVentas.vValorMultiplicacion); //27 ASOSA - 11/08/2014
        myArray.add(VariablesVentas.tipoProducto); //28 ASOSA - 09/10/2014 - PANHD

        FarmaUtility.operaListaProd(VariablesVentas.vArrayList_PedidoVenta, myArray, valor, 0);
        //log.debug("size : " + VariablesVentas.vArrayList_PedidoVenta.size());
        //log.debug("array : " + VariablesVentas.vArrayList_PedidoVenta);
    }

    public static boolean getIndProdFarma(String pCodProd, Object pObj, JDialog pDia) {
        boolean flag = false;
        try {
            String rpta = DBVentas.getIndProdFarma(pCodProd);
            if (rpta.equalsIgnoreCase("S"))
                flag = true;
        } catch (SQLException sql) {
            flag = false;
            //FarmaUtility.showMessage(pDia,sql.getMessage(),pObj);
            if (sql.getErrorCode() > 20000) {
                FarmaUtility.showMessage(pDia, sql.getMessage().substring(10, sql.getMessage().indexOf("ORA-06512")),
                                         pObj);
            } else {
                FarmaUtility.showMessage(pDia, "Ocurrió un error al validar el convenio.\n" +
                        sql.getMessage(), pObj);
            }
        }
        return flag;
    }

    public static boolean getIndImprimeCorrelativo() {
        boolean flag = true;
        String vInd = "S";
        try {
            vInd = DBVentas.getIndImprimirCorrelativo();
            if (vInd.trim().equalsIgnoreCase("N")) {
                flag = false;
            }
        } catch (SQLException sql) {
            flag = false;
            sql.getMessage();
        }
        return flag;
    }

    public static String getProdVendidos() {
        String pCadenaProductosVendidos = "";
        ArrayList array = new ArrayList();
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
            array = (ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i);
            pCadenaProductosVendidos += ((String)(array.get(0))).trim() + "@";
        }

        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones.size(); i++) {
            //array = ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i));
            array = ((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i));
            pCadenaProductosVendidos += ((String)(array.get(0))).trim() + "@";
        }

        return pCadenaProductosVendidos.trim();
    }


    /**
     * Abre el formulario de Registro Psicotropico
     * @author wvillagomez
     * @since 03.09.2013
     * @param myParentFrame - tipo Frame
     * @return boolean
     */
    public static boolean registroDatosRestringidos(Frame myParentFrame) {
        DlgRegistroPsicotropico dlgindpacmed = new DlgRegistroPsicotropico(myParentFrame, "", true);
        dlgindpacmed.setVisible(true);
        boolean vRetorno = FarmaVariables.vAceptar;
        FarmaVariables.vAceptar = false;
        return vRetorno;
    }

    /**
     * Verifica si el pedido tiene productos con venta restringida
     * @author wvillagomez
     * @since 03.09.2013
     * @param pNumPedVta - tipo String
     * @return boolean
     */
    public static boolean getVentaRestringida(String pNumPedVta) throws SQLException {
        boolean vRetorno = false;

        String vtaRestringida = DBVentas.getVentaRestringida(pNumPedVta);
        if (vtaRestringida.equals("S")) {
            vRetorno = true;
        }
        return vRetorno;
    }

    public static boolean getIndVtaNegativa() {
        String pValor = "";
        boolean pResultado = false;
        try {
            pValor = DBVentas.getIndPermiteVtaNegativa();
        } catch (SQLException sqle) {
            log.error("", sqle);
        }

        if (pValor.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            pResultado = true;

        return pResultado;
    }

    public static boolean permiteVtaNegativa(Frame myParentFrame, JDialog pJDialog, String pCodProd, String pCantidad,
                                             String pValFrac) {
        boolean pResultado = false;
        // Consultara si Permite Venta Negativa en la configuracion del local.
        //VariablesVentas.vCod_Prod,VariablesVentas.vVal_Frac
        String pConsulta = "";


        if (VariablesVentas.vEsPedidoDelivery) {

            try {
                pConsulta = DBVentas.getPermiteVtaNegativa(pCodProd, pCantidad, pValFrac);
                FarmaUtility.liberarTransaccion();
            } catch (Exception nfe) {
                log.error("", nfe);
            }
            log.debug(">>><<< " + pConsulta);
            if (pConsulta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                pResultado = false;
            else {
                String[] pListaDatos = pConsulta.trim().split("@");
                //Aqui se colocara lo programado y solicitado     >>
                //47 STREET AT SPRAY FEELING@                     >> 1
                //Usted, desea vender 80 unidades y su stock @    >> 2
                //En Sistema es de 76@                            >> 3
                //¿Desea Vender en Negativo?@                     >> 4
                //Consulte a su Jefe de Local@                    >> 5
                //4                                               >> 6
                String pLineaUno = pListaDatos[0].toString().trim();
                String pLineaDos = pListaDatos[1].toString().trim();
                String pLineaTres = pListaDatos[2].toString().trim();
                String pLineaCuatro = pListaDatos[3].toString().trim();
                String pLineaCinco = pListaDatos[4].toString().trim();
                String pLineaSeis = pListaDatos[5].toString().trim();

                boolean pRespuesta = JConfirmDialog.rptaConfirmDialogDefaultNo(pJDialog, pLineaUno + "\n" +
                        "\n" +
                        pLineaDos + "\n" +
                        pLineaTres);

                if (pRespuesta) {
                    //pResultado = true;
                    // Ingresara los codigo de barra de los tdos los productos
                    // las N veces Posible
                    if (!VariablesVentas.vEsPedidoDelivery) {
                        DlgIngCodBarraNegativa dlgIngresoCantidad =
                            new DlgIngCodBarraNegativa(myParentFrame, "", true, pCodProd, pLineaUno,
                                                       Integer.parseInt(pCantidad));

                        dlgIngresoCantidad.setVisible(true);
                        if (FarmaVariables.vAceptar) {
                            //CLAVE DE QF
                            if (cargaValidaLogin(myParentFrame, pJDialog, pCodProd, pLineaUno,
                                                 Integer.parseInt(pLineaSeis.trim()))) {
                                log.debug("GRABA SOLICITUD");
                                // ingresa solicitud de VENTA NEGATIVA
                                grabaSolicitud(pCodProd, pCantidad, pValFrac);
                                pResultado = true;
                            }
                        }
                    } else {
                        pResultado = true;
                    }
                } else {
                    pResultado = false;
                }


            }
        }
        
        //INI ASOSA - 23/01/2015 - RIMAC
        if (UtilityInventario.isExistProdRimac(VariablesVentas.vCod_Prod)) {
            pResultado = true;
        }
        //FIN ASOSA - 23/01/2015 - RIMAC

        return pResultado;
    }

    private static boolean cargaValidaLogin(Frame myParentFrame, JDialog pJDialog, String pCodProd,
                                            String pDescripcionProd, int pCantVeces) {

        VariablesVentas.vQFApruebaVTANEGATIVA = "";

        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLoginVtaNegativa dlgLogin =
                new DlgLoginVtaNegativa(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true, pCodProd,
                                        pDescripcionProd, pCantVeces);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);

            if (FarmaVariables.vAceptar) {
                // GUARDA EL USUARIO DEL QF
                VariablesVentas.vQFApruebaVTANEGATIVA = FarmaVariables.vIdUsu;
            }

            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("", e);
            FarmaUtility.showMessage(pJDialog, "Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),
                                     null);
        }
        return FarmaVariables.vAceptar;
    }

    public static void grabaSolicitud(String pCodProd, String pCantidad, String pValFrac) {
        //VariablesVentas.vCodSolicitudVtaNegativa = "";

        try {
            VariablesVentas.vCodSolicitudVtaNegativa =
                    DBVentas.getGrabaSolcitudVtaNegativa(VariablesVentas.vCodSolicitudVtaNegativa,
                                                         VariablesVentas.vQFApruebaVTANEGATIVA, pCodProd, pCantidad,
                                                         pValFrac);
            FarmaUtility.aceptarTransaccion();
        } catch (Exception nfe) {
            FarmaUtility.liberarTransaccion();
            log.error("", nfe);
        }

    }

    /**
     * Utilitario con el que obtengo un array de valores desde base de datos.
     * @author ASOSA
     * @since 06/08/2014
     * @param tipo
     * @return
     */
    public static String[] obtenerArrayValoresBd(String tipo) {
        String[] list = null;
        try {
            if (tipo.equals(ConstantsVentas.TIPO_VAL_ADIC_COD_BARRA)) {
                list = DBVentas.obtenerValoresAdicionalesBarra().split("Ã");
            }
        } catch (Exception e) {
            log.error("",e);
        }
        return list;
    }

    /**
     * Metodo que me determina que un producto final sea viable para vender
     * @author ASOSA
     * @since 02/10/2014
     * @param codProd
     * @return
     */
    public static String[] obtenerInfoRecetaProdFinal(String codProd) {
        String[] list = null;
        try {

            list = DBVentas.obtenerInfoRecetaProdFinal(codProd).split("Ã");

        } catch (Exception e) {
            log.error("",e);
        }
        return list;
    }

    /**
     * Metodo que me determina si los componentes de un producto final tienen el stock necesario.
     * @author ASOSA
     * @since 02/10/2014
     * @param codProd
     * @param cantPedido
     * @return
     */
    public static boolean existsStockComp(String codProd, int cantPedido) {
        String ind = "N";
        boolean flag = false;
        try {
            ind = DBVentas.obtenerIndValidaStkComp(codProd, cantPedido);
            if (ind.trim().equals("S")) {
                flag = true;
            }
        } catch (Exception e) {
            log.error("",e);
        }
        return flag;
    }

    /**
     * Determinar si un producto es parte de algun pack
     * @author ASOSA
     * @since 23/10/2014
     * @param codProd
     * @return
     */
    public static boolean obtenerIndProdInPack(String codProd) {
        String ind = "N";
        boolean flag = false;
        try {
            ind = DBVentas.obtenerIndProdInPack(codProd);
            if (ind.trim().equals("S")) {
                flag = true;
            }
        } catch (Exception e) {
            log.error("",e);
        }
        return flag;
    }

    /**
     * 
     * @author ERIOS
     * @since 08.01.2015
     * @param myParentFrame
     * @return
     */
    public static boolean validaLoginQF(JDialog pJDialog,Frame myParentFrame) {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("", e);
            FarmaUtility.showMessage(pJDialog, "Ocurrio un error al validar rol de usuario \n : " + e.getMessage(),
                                     null);
        } finally {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        }
        return FarmaVariables.vAceptar;
    }
    
    /**
     *
     * @author ERIOS
     * @since 08.01.2015
     * @param pJDialog
     * @param myParentFrame
     * @param tblProductos
     * @param txtProducto
     * @return
     */
    public static boolean validaVentaConMenos(JDialog pJDialog, Frame myParentFrame, JTable tblProductos, JTextField txtProducto) {

        if (tblProductos.getRowCount() == 0)
            return false;

        int vFila = tblProductos.getSelectedRow();
        Boolean valor = (Boolean)tblProductos.getValueAt(vFila, 0);

        if (valor.booleanValue()) {
            FarmaUtility.showMessage(pJDialog,
                                     "Para modificar la venta por tratamiento, debe deseleccionarlo primero.",
                                     txtProducto);
            return false;
        }
        
        FarmaVariables.vAceptar = true;
        boolean flagContinua = true;
        
        try {
            
            if(flagContinua){
                flagContinua = UtilityVentas.verificaCobertura(pJDialog);                
            }
            
            // Verifica si es obligatorio ingresar codigo de barra
            if(flagContinua){
                flagContinua = verificaIngresoCodigoBarra(myParentFrame,txtProducto);
            }
            
            // Verifica si posee Mensaje de Producto
            if(flagContinua){
                mensajeProducto(pJDialog,txtProducto);
            }
            
            // Valida ID Usuario
            if(flagContinua){
                flagContinua = validaIdUsuario(pJDialog,myParentFrame,txtProducto);
            }
            
            //ERIOS 18.11.2014 Verifica si tiene encuesta a realizar.
            if(flagContinua){
                mostrarEncuestas(myParentFrame,VariablesVentas.vCod_Prod);
            }
            
            //ERIOS 02.02.2015 Valida X+1
            if(flagContinua){
                verificaInscripcionX1(pJDialog,myParentFrame,VariablesVentas.vCod_Prod, null);
            }

        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog, "Error en Validar Producto: " + sql, txtProducto);            
        }

        //if(flagContinua)
        //Continua con el proceso

        return flagContinua;
    }
    
    /**
     * 
     * @author ERIOS
     * @since 08.01.2015
     * @param pJDialog
     * @param myParentFrame
     * @param pObjectFocus
     * @return
     */
    public static boolean validaLoginVendedor(JDialog pJDialog, Frame myParentFrame, Object pObjectFocus) {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;
        boolean flag = false;
        log.debug("numsec: " + numsec);
        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            //dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
            log.debug("FarnaVariables.NumSec: " + FarmaVariables.vNuSecUsu);
            if (FarmaVariables.vAceptar) {
                if (numsec.equalsIgnoreCase(FarmaVariables.vNuSecUsu)) {
                    log.debug("numsec 2: " + numsec);
                    flag = true;
                } else {
                    FarmaUtility.showMessage(pJDialog,
                                             "Ud. ha ingresado un usuario diferente al que inicio la Venta." + "\nIngrese Usuario que Inicio venta o vuelva a ingresar otro Usuario.",
                                             pObjectFocus);
                    flag = false;
                }
            } else {
                flag = false;
            }
        } catch (Exception e) {
            FarmaVariables.vAceptar = false;
            log.error("", e);
            FarmaUtility.showMessage(pJDialog, "Ocurrio un error al validar rol de usuario \n : " + e.getMessage(), null);
            flag = false;
        } finally {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        }
        return flag;
    }
    
    /**
     * 
     * @author ERIOS
     * @since 08.01.2015
     * @param pJDialog
     * @param pObjectFocus
     */
    public static void guardaInfoProdVariables(JDialog pJDialog, Object pObjectFocus) {
        log.debug("*************************************************");
        if (!UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null)) {
            log.debug("*************************************************");
            if (VariablesVentas.vEsPedidoConvenio &&
                !VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)) //Se ha seleccionado un convenio
            {
                //String indControlPrecio = "";
                String mensaje = "";
                try {
                    //mensaje = "Error al obtener el indicador de control de precio del producto.\n";
                    //indControlPrecio = DBConvenio.obtieneIndPrecioControl(VariablesVentas.vCod_Prod);

                    VariablesConvenio.vVal_Prec_Vta_Local = VariablesVentas.vVal_Prec_Pub;

                    /* 23.01.2007 ERIOS La validacion de realiza por las listas de exclusion */
                    //if(indControlPrecio.equals(FarmaConstants.INDICADOR_N))
                    if (true) {
                        mensaje = "Error al obtener el nuevo precio del producto.\n";
                        VariablesConvenio.vVal_Prec_Vta_Conv = DBConvenio.obtieneNvoPrecioConvenio(VariablesConvenio.vCodConvenio,
                                                                    VariablesVentas.vCod_Prod,
                                                                    VariablesVentas.vVal_Prec_Pub);
                    } else {

                        VariablesConvenio.vVal_Prec_Vta_Conv = VariablesVentas.vVal_Prec_Pub;
                    }
                } catch (SQLException sql) {
                    //log.error("",sql);
                    log.error("", sql);
                    FarmaUtility.showMessage(pJDialog, mensaje + sql.getMessage(), null);
                    FarmaUtility.moveFocus(pObjectFocus);
                }
            }
        }
    }
    
    /**
     * Se muestra el tratemiento del producto
     * @author JCORTEZ
     * @since 29.05.2008
     */
    public static void mostrarTratamiento(JDialog pJDialog, Frame myParentFrame, JTable tblProductos, int pColCod, int pColOrigProd) {
    
        if (tblProductos.getRowCount() == 0)
            return;

        int vFila = tblProductos.getSelectedRow();

        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColCod);
        VariablesVentas.vDesc_Prod = ((String)(tblProductos.getValueAt(vFila, 2))).trim();
        VariablesVentas.vNom_Lab = ((String)(tblProductos.getValueAt(vFila, 4))).trim();
        VariablesVentas.vPorc_Igv_Prod = ((String)(tblProductos.getValueAt(vFila, 11))).trim();
        VariablesVentas.vCant_Ingresada_Temp = "0";
        VariablesVentas.vNumeroARecargar = "";
        VariablesVentas.vVal_Prec_Lista_Tmp = "";
        VariablesVentas.vIndProdVirtual = FarmaConstants.INDICADOR_N;
        VariablesVentas.vTipoProductoVirtual = "";
        //VariablesVentas.vVal_Prec_Pub = ((String)(myJTable.getValueAt(vFila,6))).trim();
        VariablesVentas.vIndOrigenProdVta = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColOrigProd);
        VariablesVentas.vPorc_Dcto_2 = "0";
        VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_S;
        VariablesVentas.vCantxDia = "";
        VariablesVentas.vCantxDias = "";
        VariablesVentas.vIndModificacion = FarmaConstants.INDICADOR_N;
        //guardaInfoProdVariables();

        DlgTratamiento dlgtratemiento = new DlgTratamiento(myParentFrame, "", true);
        VariablesVentas.vIngresaCant_ResumenPed = false;
        dlgtratemiento.setVisible(true);

    }
    
    public static void seleccionaProducto(JDialog pJDialog, JTable tblProductos, Object pObjectFocus) {
        int vFila = tblProductos.getSelectedRow();
        
        double auxCantIng = FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada);
        int aux2CantIng = (int)auxCantIng;
        double auxPrecVta = FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta);
        VariablesVentas.vTotalPrecVtaProd = (auxCantIng * auxPrecVta);
        VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
        if (VariablesVentas.vIndProdControlStock &&            
            !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod,
                aux2CantIng, ConstantsVentas.INDICADOR_A, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, aux2CantIng,
                true, pJDialog, pObjectFocus, ""))
            return;
        FarmaUtility.setCheckValue(tblProductos, false);
        Boolean valor = (Boolean)(tblProductos.getValueAt(vFila, 0));
        UtilityVentas.operaProductoSeleccionadoEnArrayList_02(valor, VariablesVentas.secRespStk); //ASOSA, 22.07.2010
    }
    
    /**
     * Muestra si es producto de Encarte
     * @author JCORTEZ
     * @since  08.04.2008
     */
    public static void muestraProductoEncarte(JTable tblProductos, int pColumna, JLabel pLabel) {
        if (tblProductos.getRowCount() > 0) {
            int vFila = tblProductos.getSelectedRow();
            String indProdEncarte = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColumna);
            if (indProdEncarte.length() > 0) {
                pLabel.setVisible(true);
            } else {
                pLabel.setVisible(false);
            }
        }
    }
    
    public static void muestraProductoCongelado(JTable tblProductos, String indProdCong, JLabel pLabel) {
        if (tblProductos.getRowCount() > 0) {
            //String indProdCong = ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(),pColumna))).trim();
            if (indProdCong.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }
    
    public static void muestraIndTipoProd(JTable myJTable, int pColumna, JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            int vFila = myJTable.getSelectedRow();
            String tipoProd = FarmaUtility.getValueFieldJTable(myJTable, vFila, pColumna);
            
            muestraIndTipoProd(myJTable, pLabel, tipoProd);
        }
    }
    
    public static void muestraIndTipoProd(JTable myJTable, JLabel pLabel, String tipoProd) {
        if (myJTable.getRowCount() > 0) {            
            if (tipoProd.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[0]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[0]);
            else if (tipoProd.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[1]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[1]);
            else if (tipoProd.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[2]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[2]);
            else if (tipoProd.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[3]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[3]);
            else if (tipoProd.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[4]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[4]);
        }
    }
    
    public static void muestraProductoRefrigerado(JTable tblProductos, int pColumna, JLabel pLabel) {
        if (tblProductos.getRowCount() > 0) {
            int vFila = tblProductos.getSelectedRow();
            String indProdRef = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColumna);
            if (indProdRef.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }
    
    public static void muestraProductoPromocion(JTable tblProductos, int pColumna, JLabel pLabel, int mostrarDesc) {          
        if (tblProductos.getRowCount() > 0) {
            int vFila = tblProductos.getSelectedRow();
            String indProdProm = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColumna);
            if (indProdProm.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                if(mostrarDesc == 1){
                    String descripcion = " ";
                    if (tblProductos.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS)) {
                        int COL_COD = 1; //Posicion por defecto de la columna Codigo de Producto, en el listado.
                        VariablesVentas.vCodProdFiltro = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_COD);
                        try {
                            descripcion = DBVentas.getDescPaquete(VariablesVentas.vCodProdFiltro);
                            log.debug("descripcion paquete: " + descripcion);
                        } catch (SQLException sqlException) {
                            log.error("", sqlException);
                        }
                    }
                    pLabel.setText(descripcion);
                }
                pLabel.setVisible(true);
            }else{
                pLabel.setVisible(false);
            }
        }
    }
    
    public static void muestraProductoInafectoIgv(JTable tblProductos, int pColumna, JLabel pLabel) {
        if (tblProductos.getRowCount() > 0) {
            int vFila = tblProductos.getSelectedRow();
            String inafectoIgv = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColumna);
            if (FarmaUtility.getDecimalNumber(inafectoIgv) == 0.00)
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }
    
    public static void muestraNombreLab(JTable tblProductos, int pColumna, JLabel pLabel) {
        if (tblProductos.getRowCount() > 0) {
            int vFila = tblProductos.getSelectedRow();
            String nombreLab = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColumna);
            pLabel.setText(nombreLab);
        }
    }
    
    public static boolean buscar(ArrayList array, String codigo) {
        String codtemp;
        for (int i = 0; i < array.size(); i++) {
            codtemp = ((String)(((ArrayList)array.get(i)).get(0))).trim();
            if (codtemp.equalsIgnoreCase(codigo))
                return true;
        }
        return false;
    }
    
    public static boolean validaStockDisponible(String stkProd) {
        if (FarmaUtility.isInteger(stkProd) && Integer.parseInt(stkProd) > 0)
            return true;
        else {
            if (FarmaUtility.isInteger(stkProd) && Integer.parseInt(stkProd) == 0 &&
                (VariablesVentas.vEsPedidoDelivery && UtilityVentas.getIndVtaNegativa()))
                return true;
            else
                return false;
        }
    }
    
    public static boolean validaProductoHabilVenta() {
        if (VariablesVentas.vInd_Prod_Habil_Vta.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }
    
    public static boolean validaProductoTomaInventario(String indProdCong) {
        if (indProdCong.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return true;
        else
            return false;
    }
    
    public static void deseleccionaProducto(JDialog pJDialog, JTable tblProductos, int pColCod) {
        int vFila = tblProductos.getSelectedRow();
        String cantidad = "";
        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColCod);
        String indicadorControlStock = FarmaConstants.INDICADOR_S;
        String codigoTmp = "";
        String secRespaldo = ""; //ASOSA, 22.07.2010
        for (int i = 0; i < VariablesVentas.vArrayList_PedidoVenta.size(); i++) {
            codigoTmp = (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(0);
            if (VariablesVentas.vCod_Prod.equalsIgnoreCase(codigoTmp)) {
                indicadorControlStock =
                        FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_PedidoVenta, i, 16);
                cantidad = (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(4);
                VariablesVentas.vVal_Frac =
                        FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_PedidoVenta, i, 10);
                secRespaldo =
                        FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_PedidoVenta, i, 26).trim(); //ASOSA, 22.07.2010
                break;
            }
        }
        
        VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
        if (indicadorControlStock.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
           
            !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod, //ASOSA, 22.07.2010
                Integer.parseInt(cantidad), ConstantsVentas.INDICADOR_D, 
                ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad), true, pJDialog,
                tblProductos, secRespaldo)) {
            return;
        }
        FarmaUtility.setCheckValue(tblProductos, false);
        Boolean valor = (Boolean)(tblProductos.getValueAt(vFila, 0));
        UtilityVentas.operaProductoSeleccionadoEnArrayList_02(valor, VariablesVentas.secRespStk); //ASOSA, 22.07.2010
        if (VariablesVentas.vArrayList_PedidoVenta.size() == 0)
            VariablesVentas.vIndPedConProdVirtual = false;
        //indicadorItems = FarmaConstants.INDICADOR_N;
    }
    
    public static void muestraDetalleProducto(Frame myParentFrame, JTable tblProductos, int pColdCod, int pColDesc, int pColLab) {
        if (tblProductos.getRowCount() == 0)
            return;
        int vFila = tblProductos.getSelectedRow();
        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColdCod);
        VariablesVentas.vDesc_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColDesc);
        VariablesVentas.vNom_Lab = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColLab);
        DlgDetalleProducto dlgDetalleProducto = new DlgDetalleProducto(myParentFrame, "", true);
        dlgDetalleProducto.setVisible(true);
    }
    
    public static void obtieneInfoProdEnArrayList(JDialog pJDialog, JTable myJTable, ArrayList pArrayList, Object pObjectFocus, int pColCod, boolean vEsProdOferta) {
        int vFila = myJTable.getSelectedRow();
        String codProd = FarmaUtility.getValueFieldJTable(myJTable, vFila, pColCod);
        //JMIRANDA 22/09/2009 lleno la variable vCod_Prod
        VariablesVentas.vCod_Prod = codProd;
        
        try {
            if (!vEsProdOferta) {
                log.debug("BUSCA PRODUCTOS INFO");
                //ERIOS 06.06.2008 Solución temporal para evitar la venta sugerida por convenio
                if (VariablesVentas.vEsPedidoConvenio) {
                    DBVentas.obtieneInfoProducto(pArrayList, codProd);
                } else {
                    DBVentas.obtieneInfoProductoVta(pArrayList, codProd);
                }
            } else {
                log.debug("BUSCA PRODUCTOS INFOR OFERTA ");
                DBVentas.obtieneInfoOfertProducto(pArrayList, codProd);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog, "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), pObjectFocus);
        }
    }

    private static boolean verificaIngresoCodigoBarra(Frame myParentFrame, JTextField txtProducto) throws SQLException {
        boolean flagContinua = true;
        if (DBVentas.getIndCodBarra(VariablesVentas.vCod_Prod).equalsIgnoreCase("S") && FarmaVariables.vAceptar &&
            VariablesVentas.vIndEsCodBarra) {

            //valida si se ha insertado cod Barra
            if (UtilityVentas.validaCodBarraLocal(txtProducto.getText().trim())) {
                DlgIngreseCodBarra dlgIngCodBarra = new DlgIngreseCodBarra(myParentFrame, "", true);
                dlgIngCodBarra.setVisible(true);
                flagContinua = VariablesVentas.bIndCodBarra;
            }
            if (VariablesVentas.vCodigoBarra.equalsIgnoreCase(txtProducto.getText().trim())) {
                flagContinua = true;
            }
            //flagContinua = VariablesVentas.bIndCodBarra;
            FarmaVariables.vAceptar = flagContinua;
        }
        return flagContinua;
    }

    private static void mensajeProducto(JDialog pJDialog, Object pObjectFocus) throws SQLException {
        String vMensajeProd = DBVentas.getMensajeProd(VariablesVentas.vCod_Prod);
        if (!vMensajeProd.equalsIgnoreCase("N") && FarmaVariables.vAceptar) {

            String sMensaje = "";
            sMensaje = UtilityVentas.saltoLineaConLimitador(vMensajeProd);
            //ENVIO vMensajeProd LLAMAR METODO RETORNAR SMENSAJE CON SALTO DE LINEA
            log.debug(sMensaje);
            FarmaUtility.showMessage(pJDialog, sMensaje, pObjectFocus);
        }
    }

    private static boolean validaIdUsuario(JDialog pJDialog, Frame myParentFrame, Object pObjectFocus) throws SQLException {
        boolean flagContinua = true;
        String pInd = DBVentas.getIndSolIdUsu(VariablesVentas.vCod_Prod).trim().toUpperCase();
        if (pInd.equalsIgnoreCase("S") && FarmaVariables.vAceptar) {
            //llamar a Usuario para visualizar
            flagContinua = validaLoginVendedor(pJDialog,myParentFrame,pObjectFocus);
            FarmaVariables.vAceptar = flagContinua;
            log.debug("SolId. flagContinua: " + flagContinua);
        } else {
            if (pInd.equalsIgnoreCase("J") && FarmaVariables.vAceptar) {
                log.debug("*** Valida Producto Venta Cero");
                //llamar a Usuario para visualizar
                flagContinua = UtilityVentas.validaLoginQF(pJDialog,myParentFrame);
                FarmaVariables.vAceptar = flagContinua;
                log.debug("SolId. flagContinua : " + flagContinua);
            }
        }
        return flagContinua;
    }
    
    public static boolean verificaCobertura(JDialog pJDialog) {
        boolean flagContinua = true;
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null) &&
            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF &&
            VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0 &&
            VariablesVentas.vCod_Prod.trim().length() == 6) {
            
            UtilityConvenioBTLMF.Busca_Estado_ProdConv(pJDialog);
            
            if (!VariablesVentas.vEstadoProdConvenio.equalsIgnoreCase("I")) {
                FarmaUtility.showMessage(pJDialog, "Producto no esta en cobertura del Convenio.", null);
                flagContinua = false;
            }
        }
        return flagContinua;
    }    
    

    private static void mostrarEncuestas(Frame myParentFrame, String vCod_Prod) {
        FacadeEncuesta facadeEncuesta = new FacadeEncuesta(myParentFrame); //TODO pensar en singleton
        facadeEncuesta.verificaEncuesta(vCod_Prod);
    }

    public static void verificaInscripcionX1(JDialog pJDialog, Frame myParentFrame, String pCodProd, String descProd) {

        if(UtilityPuntos.isActivoFuncionalidad()){
            if(permiteInscripcionProdsXMasUno()){   //ASOSA - 16/04/2015 - PTOSYAYAYAYA
                String mensajeBox = "";
                FacadeLealtad facade = new FacadeLealtad();
                //1. Verifica si el producto participa de X+1
                int cantidad = facade.verificaAcumulaX1(pCodProd);
                if(cantidad > 0){
                    //2. Verifica si el cliente ha sido afiliado
                    TarjetaBean tarjetaCliente = VariablesPuntos.frmPuntos.getTarjetaBean();
                    
                    if(tarjetaCliente==null){
                        if(descProd!=null){
                            mensajeBox = descProd+"\n";
                        }
                        FarmaUtility.showMessage(pJDialog, mensajeBox+"Este producto participa de X+1. Debe afiliarse para poder participar.", null);
                        return;
                    }
                    
                    if(tarjetaCliente!=null && tarjetaCliente.getEstadoOperacion().equals(FarmaPuntosConstante.EXITO) && 
                       (tarjetaCliente.getEstadoTarjeta().equals(FarmaPuntosConstante.EstadoTarjeta.ACTIVA) || 
                        tarjetaCliente.getEstadoTarjeta().equals(FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA_REDIMIR))){
                        //3. Verifica si el cliente ya se encuentra inscrito                    
                        if(facade.verificaInscripcionProducto(tarjetaCliente.getListaInscritos(),pCodProd).equals("")){
                            
                                if(descProd!=null){
                                    mensajeBox = descProd+"\n";
                                }
                                //4. Pregunta de inscripcion
                                if(JConfirmDialog.rptaConfirmDialog(pJDialog, mensajeBox+"Este producto participa de X+1. ¿Desea inscribirse a esta campaña?")){
                                    //5. Determina lista que participa
                                    if(cantidad > 1){
                                        //6. Mostrar pantalla
                                        DlgListaAcumulaX1 dlgListaAcumulaX1 = new DlgListaAcumulaX1(myParentFrame,"",true);
                                        dlgListaAcumulaX1.setpCodProd(pCodProd);
                                        dlgListaAcumulaX1.setFacadeLealtad(facade);
                                        dlgListaAcumulaX1.setVisible(true);
                                    }else{
                                        //7. Inscripcion automatica
                                        ArrayList<ArrayList<String>> lista = facade.listaAcumulaX1(pCodProd);
                                        String codCamp = lista.get(0).get(0);
                                        String pCodEqui = lista.get(0).get(2);
                                        String codMatrizAcu = lista.get(0).get(3);
                                        facade.inscribeAcumulaX1(pJDialog,pCodProd,codCamp,pCodEqui,codMatrizAcu,tarjetaCliente);
                                    }
                                }                                               
                        }
                        
                    }
                }
            }            
        }
    }
    
    /**
     * permiteInscripcionProdsXMasUno
     * @author ASOSA
     * @since 16/04/2015
     * @type PTOSYAYAYAYA
     * @return
     */
    private static boolean permiteInscripcionProdsXMasUno() {
        boolean flag = false;
        String ind = "N";
        try {
            /*
            ind = UtilityInventario.obtenerParametroString(ConstantsVentas.IND_INSCRIP_PROD_XMAS1);
            if (ind.equals("S")) {
                flag = true;
            }
            */
            flag = DBPuntos.indicadorMuestrPantallaBonifica();
        } catch (Exception e) {
            log.error("",e);
        }
        return flag;
    }
    
    /**
     * Obtiene valor igv para el local
     * @AUTHOR ASOSA
     * @SINCE 25/06/2015
     * @KIND IGVAZONIA
     * @return
     * @throws SQLException
     */
    public static String obtenerIgvLocal() {
        String valor = "";
        try {
            valor = DBVentas.obtenerIgvLocal();
        }catch (Exception e){
            log.info("" + e.getMessage());
        }
        return valor;
    }
    
    /**
     * Obtiene un igv especifico a demanda
     * @AUTHOR ASOSA
     * @SINCE 25/06/2015
     * @KIND IGVAZONIA
     * @param codIgv
     * @return
     * @throws SQLException
     */
    public static String obtenerIgvEspecificoLocal(String codIgv) {
        String valor = "";
        try {
            valor = DBVentas.obtenerIgvEspecificoLocal(codIgv);
        } catch (Exception e) {
            log.info("" + e.getMessage());
        }
        return valor;
    }
    
}
