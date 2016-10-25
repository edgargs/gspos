package com.gs.hacom.dcs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;

public interface MapperDaemon {

    /*@Select(value="{call 
            "PTOVENTA.PKG_DAEMON_TRANSF.GET_LOCALES_ENVIO(" +
                "#{carrier}," +
                "#{cCodCia_in}" +
            ")}")
	@Options(statementType = StatementType.CALLABLE)
	public void getLocalesEnvio(Map<String,Object>  mapParametros);*/

    @Insert("INSERT INTO MTXEvent(accountID) VALUES(#{carrier})")
	public void registraEvento(CalAmpEvent2 myEvent);

    @Select("SELECT * FROM MTXConfigDCS WHERE deviceID = #{deviceID}")
	public List<ConfigMessage> getConfigMessage(ConfigMessage configMessage);
}
