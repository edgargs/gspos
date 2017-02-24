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
import java.util.Properties;

import com.gs.hacom.dcs.Util;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;

/**
 * Interacciones JDBC con base de datos.
 * 
 * @version 1.0
 * @since 2017/01/01
 */
public class JDBCDaemon implements DAODaemon {

	ConnectMSSQLServer connServer;
	
	/**
	 * Constructor.
	 * @param propDatabase Propiedades de base de datos.
	 */
	public JDBCDaemon(Properties propDatabase) {
		Util.iniciaConfiguracion(propDatabase);
		String strUrl = "jdbc:sqlserver://" + Util.IPBD + ":"+Util.PuertoBD+";databaseName=" + Util.SID;
        // Cargamos conn a BBDD
		connServer = ConnectMSSQLServer.newInstance(strUrl, Util.UsuarioBD, Util.ClaveBD);
	}

	/**
	 * Confirma la transaccion.
	 */
	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	/**
     * Deshace la transaccion.
     */
	@Override
	public void rollback() {
		// TODO Auto-generated method stub

	}

	/**
     * Inicia comunicacion.
     */
	@Override
	public void openConnection() {
		// TODO Auto-generated method stub

	}

	/**
	 * Registra el evento en base de datos.
	 * @param myEvent Evento del sistema.
	 * @throws Exception Error de base de datos.
	 */
	@Override
	public void registraEvento(CalAmpEvent2 myEvent) throws Exception {
		String insertString = "INSERT INTO MTXEvent(accountID) VALUES(?)";
		Object[] parameters = new Object[] { myEvent.getCarrier() };
		connServer.executeSQL(insertString, parameters);
	}

	/**
	 * Obtiene la configuracion de la trama.
	 * @param configMessage Filtro para la consulta.
	 * @return Configuracion de la trama.
	 * @throws Exception Error de base de datos.
	 */
	@Override
	public List<ConfigMessage> getConfigMessage(ConfigMessage configMessage) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
