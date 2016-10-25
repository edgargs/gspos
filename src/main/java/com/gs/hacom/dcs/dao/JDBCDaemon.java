package com.gs.hacom.dcs.dao;

import java.util.List;
import java.util.Properties;

import com.gs.hacom.dcs.Util;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;

public class JDBCDaemon implements DAODaemon {

	ConnectMSSQLServer connServer;
	
	public JDBCDaemon(Properties propDatabase) {
		Util.iniciaConfiguracion(propDatabase);
		String strUrl = "jdbc:sqlserver://" + Util.IPBD + ":"+Util.PuertoBD+";databaseName=" + Util.SID;
        // Cargamos conn a BBDD
		connServer = ConnectMSSQLServer.newInstance(strUrl, Util.UsuarioBD, Util.ClaveBD);
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub

	}

	@Override
	public void openConnection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void registraEvento(CalAmpEvent2 myEvent) throws Exception {
		String insertString = "INSERT INTO MTXEvent(accountID) VALUES(?)";
		Object[] parameters = new Object[] { myEvent.getCarrier() };
		connServer.executeSQL(insertString, parameters);
	}

	@Override
	public List<ConfigMessage> getConfigMessage(ConfigMessage configMessage) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
