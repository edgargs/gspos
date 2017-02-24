/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizacion de Transporte Urbano.
  =======================================================================================
	Change History:
  =======================================================================================
*/
package com.gs.hacom.dcs.dao;

/**
 * Plantilla de transacciones.
 * 
 * @version 1.0
 * @since 2017/01/01
 */
public interface DAOTransaccion {
    
	/**
	 * Confirma la transaccion.
	 */
    public void commit();
    
    /**
     * Deshace la transaccion.
     */
    public void rollback();
    
    /**
     * Inicia comunicacion.
     */
    public void openConnection();
}
