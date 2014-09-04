package com.gs.pos.configuracion.domain;

import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

public interface MapperConfiguracion {
    
    @SelectProvider(type = SQLProviderConfiguracion.class, method = "selectUsuario")
    Usuario validarLogin(Map<String,Object> mapParametros);
}
