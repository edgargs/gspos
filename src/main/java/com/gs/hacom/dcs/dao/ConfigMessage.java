/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizacion de Transporte Urbano.
  =======================================================================================
	Change History:
  =======================================================================================
*/
package com.gs.hacom.dcs.dao;

import lombok.Data;

/**
 * Configuracion de tipo de dispositivo.
 * 
 * @version 1.0
 * @since 2017/01/01
 */
@Data
public class ConfigMessage {

	private int deviceID;
	private String nameField;
	private int orderField;
	private int lengthField;
	
}
