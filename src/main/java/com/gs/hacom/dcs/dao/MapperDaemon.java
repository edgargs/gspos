package com.gs.hacom.dcs.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface MapperDaemon {

	@Insert(value = " {call dbo.MTX_sp_EventNewDCS( " +
            " #{eventID,jdbcType=INTEGER,mode=OUT}, " +
            " #{deviceID,jdbcType=VARCHAR,mode=IN}, " +
            " #{timestamp,jdbcType=BIGINT,mode=IN}, " +
            " #{eventCode,jdbcType=INTEGER,mode=IN}, " +
            " #{latitude,jdbcType=FLOAT,mode=IN}, " +
            " #{longitude,jdbcType=FLOAT,mode=IN}, " +
            " #{speedKPH,jdbcType=FLOAT,mode=IN}, " +
            " #{odometerKm,jdbcType=FLOAT,mode=IN}, " +
            " #{fuel,jdbcType=FLOAT,mode=IN}, " +
            " #{heading,jdbcType=FLOAT,mode=IN}, " +
            " #{HDOP,jdbcType=FLOAT,mode=IN}, " +
            " #{satelliteCount,jdbcType=INTEGER,mode=IN} " +
            " )} ")
	@Options(statementType = StatementType.CALLABLE)
	public void registraEvento(Map<String,Object> myEvent);

    @Select("SELECT * FROM MTXConfigDCS WHERE deviceID = #{deviceID}")
	public List<ConfigMessage> getConfigMessage(ConfigMessage configMessage);
}
