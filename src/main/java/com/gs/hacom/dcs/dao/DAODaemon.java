package com.gs.hacom.dcs.dao;

import java.util.List;

import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;

public interface DAODaemon extends DAOTransaccion{

	public void registraEvento(CalAmpEvent2 myEvent) throws Exception;

	public List<ConfigMessage> getConfigMessage(ConfigMessage configMessage) throws Exception;

}
