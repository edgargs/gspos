package mifarma.electronico.impresion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.electronico.impresion.dao.DAOComprobanteElectronico;
import mifarma.electronico.impresion.dao.FactoryDocumentoElectronico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : FacadeComprobanteElectronico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES     01.09.2014  Creación<br>
 * <br>
 * @author Cesar Alfredo Huanes Bautista<br>
 * @version 1.0<br>
 *
 */
public class FacadeComprobanteElectronico {
    private static final Logger log = LoggerFactory.getLogger(FacadeComprobanteElectronico.class);
    private DAOComprobanteElectronico daoDocElectronico;

    public FacadeComprobanteElectronico() {
        super();
        daoDocElectronico =
                FactoryDocumentoElectronico.getDAOComprobanteElectronico(mifarma.electronico.impresion.dao.FactoryDocumentoElectronico.Tipo.MYBATIS);
    }

    public ArrayList<ArrayList<String>> imprimeCabecera(String NumPedidoVta, String SecCompPago,
                                                        String TipoDocumento) throws Exception {
        ArrayList<ArrayList<String>> lstListado = null;
        //try{
        lstListado = daoDocElectronico.listaDatosCabecera(NumPedidoVta, SecCompPago, TipoDocumento);
        /*}catch(Exception ex){
                 log.error("Ocurrio un Error en imprimeCabecera: ",ex);
             }*/
        return lstListado;
    }

    public ArrayList<ArrayList<String>> imprimeDetalle(String NumPedidoVta, String SecCompPago,
                                                       String TipoClienteConvenio) {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            lstListado = daoDocElectronico.listaDetalle(NumPedidoVta, SecCompPago, TipoClienteConvenio);
        } catch (Exception ex) {
            log.error("Ocurrio un Error en imprimeDetalle:", ex);
        }
        return lstListado;
    }

    public ArrayList<ArrayList<String>> imprimeMontos(String NumPedidoVta, String SecCompPago) throws Exception {
        ArrayList<ArrayList<String>> lstListado = null;
        //try{
        lstListado = daoDocElectronico.listaMontos(NumPedidoVta, SecCompPago);
        /*}catch(Exception ex){
                 log.error("Ocurrio un Error en imprimeMontos: ",ex);
             }*/
        return lstListado;
    }

    public String imprimeDatosConvenio(String NumPedidoVta, String SecCompPago, String TipoDocumento,
                                       String TipoClienteConvenio) {

        String datos = null;
        try {
            datos = daoDocElectronico.getDatosConvenio(NumPedidoVta, SecCompPago, TipoDocumento, TipoClienteConvenio);
        } catch (Exception ex) {
            log.error("Ocurrio un Error en Convenio: ", ex);
        }
        return datos;
    }

    public ArrayList<ArrayList<String>> imprimeNotas(String NumPedidoVta, String SecCompPago,
                                                     String TipoDocumento) throws Exception {
        ArrayList<ArrayList<String>> lstListado = null;
        //try{
        lstListado = daoDocElectronico.listaNotas(NumPedidoVta, SecCompPago, TipoDocumento);
        /*}catch(Exception ex){
                log.error("Ocurrio un Error en imprimeNotas: ",ex);
             }*/
        return lstListado;
    }

    public ArrayList imprimePiePag() {

        String datoPiePagina = null;
        ArrayList lstPagina = null;
        try {
            datoPiePagina = daoDocElectronico.listaDatosPiePagina();
            List<String> items = Arrays.asList(datoPiePagina.split("Ã"));
            lstPagina = new ArrayList(items);
        } catch (Exception ex) {
            log.error("Ocurrio un Error en imprimeNotas: ", ex);
        }
        return lstPagina;
    }

    public ArrayList<ArrayList<String>> imprimeFormaPago(String NumPedidoVta) throws Exception {
        ArrayList<ArrayList<String>> lstListado = null;
        //try{
        lstListado = daoDocElectronico.listaFormasPago(NumPedidoVta);
        /*}catch(Exception ex){
            log.error("Ocurrio un Error en Formas de Pago: ",ex);
         }*/
        return lstListado;
    }

    public ArrayList<ArrayList<String>> imprimeDatosMayorista(String NumPedidoVta) {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            lstListado = daoDocElectronico.listaDatosEmpresa(NumPedidoVta);
        } catch (Exception ex) {
            log.error("Ocurrio un Error en Formas de Pago: ", ex);
        }
        return lstListado;
    }

    public ArrayList<ArrayList<String>> imprimeDatosDelivery(String NumPedidoVta) throws Exception {
        ArrayList<ArrayList<String>> lstListado = null;
        //try{
        lstListado = daoDocElectronico.listaDatosDelvery(NumPedidoVta);
        /*}catch(Exception ex){
            log.error("Ocurrio un Error en Formas de Pago: ",ex);
         }*/
        return lstListado;
    }

    public Map<String, Object> obtieneDatosImpresion(String pNumPedidoVta, String pSecCompPago, String pTipoDocumento,
                                                     String pTipoClienteConvenio, ArrayList<String> lstPiePag,
                                                     ArrayList<ArrayList<String>> lstCabecera,
                                                     ArrayList<ArrayList<String>> lstDetalle,
                                                     ArrayList<ArrayList<String>> lstMontos,
                                                     ArrayList<ArrayList<String>> lstNotas,
                                                     ArrayList<ArrayList<String>> lstFormaPago,
                                                     ArrayList<ArrayList<String>> lstDelivery,
                                                     ArrayList<ArrayList<String>> lstEmpresa) {
        Map<String, Object> a = new HashMap<String, Object>();
        try {
            daoDocElectronico.openConnection();
            lstPiePag = imprimePiePag();
            lstCabecera = imprimeCabecera(pNumPedidoVta, pSecCompPago, pTipoDocumento);
            lstDetalle = imprimeDetalle(pNumPedidoVta, pSecCompPago, pTipoClienteConvenio);
            lstMontos = imprimeMontos(pNumPedidoVta, pSecCompPago);
            lstNotas = imprimeNotas(pNumPedidoVta, pSecCompPago, pTipoDocumento);
            lstFormaPago = imprimeFormaPago(pNumPedidoVta);
            lstDelivery = imprimeDatosDelivery(pNumPedidoVta);
            lstEmpresa = imprimeDatosMayorista(pNumPedidoVta);
            daoDocElectronico.commit();

            a.put("1", lstPiePag);
            a.put("2", lstCabecera);
            a.put("3", lstDetalle);
            a.put("4", lstMontos);
            a.put("5", lstNotas);
            a.put("6", lstFormaPago);
            a.put("7", lstDelivery);
            a.put("8", lstEmpresa);

        } catch (Exception e) {
            daoDocElectronico.rollback();
            log.error("", e);
        } finally {
            return a;
        }
    }
}
