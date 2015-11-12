package mifarma.ptoventa.administracion.producto.dao;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.ptoventa.reference.DAOTransaccion;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DAOProducto.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CVILCA      01.10.2013   Creación<br>
 * <br>
 * @author Christian Vilca Quiros<br>
 * @version 1.0<br>
 *
 */
public interface DAOProducto extends DAOTransaccion {

    /**
     * Obtiene los datos básicos de los productos por descripción.
     * @author CVILCA
     * @since 01.10.2013
     */
    public abstract ArrayList obtenerProductosPorDescripcion(String strDescripcion) throws SQLException;

    /**
     * Obtiene el código EPL para la impresión del sticker de un producto.
     * @author CVILCA
     * @since 02.10.2013
     */
    public abstract String obtenerCodigoEPLPorProducto(String strCodigo) throws Exception;


    /**
     * Obtiene el código de barra de un producto
     * @author CHUANES
     * @since 22.11.2013
     */
    public abstract ArrayList obtenerProductoCodigoBarra(String pCodBarra) throws SQLException;
    

    /**
     * Obtiene la cantidad de impresiones a realizar antes de mostrar mensaje de Confirmacion
     * @return String
     * @autor Desarrollo5
     * @since 22.09.2015
     */
    public abstract String obtenerCantidadImpresiones() throws SQLException;

}
