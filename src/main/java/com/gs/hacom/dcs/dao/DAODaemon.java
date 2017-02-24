/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizacion de Transporte Urbano.
  =======================================================================================
	Change History:
  =======================================================================================
*/
package com.gs.hacom.dcs.dao;

import java.util.List;

import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;

/**
 * Transacciones del servidor DCS.
 * 
 * @version 1.0
 * @since 2017/01/01
 * @see com.gs.hacom.dcs.dao.ConfigMessage
 */
public interface DAODaemon extends DAOTransaccion{

	/**
	 * Registra el evento en base de datos.
	 * @param myEvent Evento del sistema.
	 * @throws Exception Error de base de datos.
	 */
	public void registraEvento(CalAmpEvent2 myEvent) throws Exception;

	/**
	 * Obtiene la configuracion de la trama.
	 * @param configMessage Filtro para la consulta.
	 * @return Configuracion de la trama.
	 * @throws Exception Error de base de datos.
	 */
	public List<ConfigMessage> getConfigMessage(ConfigMessage configMessage) throws Exception;

}
