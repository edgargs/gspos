/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizacion de Transporte Urbano.
  =======================================================================================
	Change History:
	2017/02/27 Edgar Rios
	  Se agrega el parametro creationTime.
  =======================================================================================
*/
package com.gs.hacom.dcs.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import hacom.pe.datos.entidades.Event;

/**
 * Comunicacion con la base de datos.
 * 
 * @version 1.0
 * @since 2017/01/01
 */
public interface MapperDaemon {

	/**
	 * Registra el evento en base de datos.
	 * @param myEvent Evento del sistema.
	 */
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
            " #{satelliteCount,jdbcType=INTEGER,mode=IN}, " +
            " #{creationTime,jdbcType=BIGINT,mode=IN}" +
            " )} ")
	@Options(statementType = StatementType.CALLABLE)
	public void registraCalAmpEvent(Map<String,Object> myEvent) throws Exception;

	/**
	 * Obtiene la configuracion de la trama.
	 * @param configMessage Filtro para la consulta.
	 * @return Configuracion de la trama.
	 */
    @Select("SELECT * FROM MTXConfigDCS WHERE deviceID = #{deviceID}")
	public List<ConfigMessage> getConfigMessage(ConfigMessage configMessage) throws Exception;

    /**
	 * Registra el evento en base de datos.
	 * @param myEvent Evento del sistema.
	 */
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
            " #{satelliteCount,jdbcType=INTEGER,mode=IN}, " +
            " #{creationTime,jdbcType=BIGINT,mode=IN},"
            + "#{typeProvider,jdbcType=VARCHAR,mode=IN}," 
            + "#{eventStatusID,jdbcType=INTEGER,mode=IN}" +
            " )} ")
	@Options(statementType = StatementType.CALLABLE)
	public void registraEvento(Event myEvent) throws Exception;
}
