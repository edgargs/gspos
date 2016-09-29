package mifarma.ptoventa.caja.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mifarma.ptoventa.reference.BeanImpresion;
import mifarma.ptoventa.reference.DAOTransaccion;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DAOCaja.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      16.07.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public interface DAOCaja extends DAOTransaccion {

    /**
     * REGISTRAR UNA VENTA CON TARJETA CMR
     * @author GFonseca
     * @since 16.08.2013
     */
    public Long registrarTrsscVentaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                       String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                       String strTipoRcd, String strNroTarjeta, String strMonto, String strTerminal,
                                       String strComercio, String strUbicacion, String strNroCuotas,
                                       String strIdCajero, String strNroDoc, String strUsuario) throws SQLException;

    /**
     * OBTIENE LAS OPCIONES BLOQUEADAS DEL SISTEMA
     * @author CVILCA
     * @since 18.10.2013
     */
    public ArrayList<ArrayList<String>> obtenerOpcionesBloqueadas() throws Exception;

    /**
     * Obtener las formas de pago de un pedido, para abrir la gabeta en caso sea efectivo.
     * @author GFonseca
     * @since 27.Dic.2013
     */
    public ArrayList<ArrayList<String>> getFormasPagoPedido(String strNumPedido) throws Exception;

    /**
     * Proceso cobro.
     * @author RHERRERA
     * @since 04.Abr.2014
     */
    public Map grabarNuevoCobro(String pNumPedVta, ArrayList a_CodFormaPago, ArrayList a_monto, ArrayList a_CodMoneda,
                                ArrayList a_XXX, ArrayList a_ImpTotal, ArrayList a_NumTarjeta,
                                ArrayList a_FecVecTarjeta,
        //--vuelto---//falta
        ArrayList a_NomCliTarjeta, ArrayList a_CantCupon, ArrayList a_DniTarjeta,
        //-- noc q es //
        ArrayList a_CodBouch, ArrayList a_CodLote) throws SQLException;


    public void setVersion() throws Exception;
    
    public List<BeanImpresion> getEncabezadoCupon(String pCodGrupoCia, String pCodCia, String pCodLocal, String pNumPedVta) throws Exception;
}
