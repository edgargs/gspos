package com.gs.pos.configuracion.domain;

import org.apache.ibatis.jdbc.SQL;

public class SQLProviderConfiguracion {
    
    public String selectUsuario(){
        return new SQL()
            .SELECT("*")
            .FROM("CONFIGURACION.USUARIO")
            .WHERE("LOGIN = #{login}")
        .toString();
    } 
}
