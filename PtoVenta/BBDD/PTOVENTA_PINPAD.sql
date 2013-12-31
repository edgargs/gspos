--------------------------------------------------------
--  DDL for Package PTOVENTA_PINPAD
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_PINPAD" is

  TYPE FarmaCursor IS REF CURSOR;

	OPERADOR_VISA INTEGER := 1;
	OPERADOR_MC INTEGER := 2;
	
  -- Author  : LLEIVA
  -- Created : 07/08/2013 12:29:20 p.m.
  -- Purpose : Administrar las interacciones entre el sistema PTOVENTA y los diferentes pinpads
  
  --Descripcion: Guarda la información enviada por el pinpad de visa para una determinada transacción
  --Fecha        Usuario		    Comentario
  --08/07/2013   LLEIVA         Creación
  FUNCTION GRAB_TRAMA_PINPAD_VISA(cCodGrupoCia_in          IN CHAR,
                                  cCod_Cia_in              IN CHAR,
                                  cCod_Local_in            IN CHAR,
                                  cCodRespuesta_in         IN CHAR,
                                  cMsjeFinOperacion_in     IN VARCHAR2,
                                  cNomCliente_in           IN VARCHAR2,
                                  cNumAutorizacion_in      IN VARCHAR2,
                                  cNumReferencia_in        IN VARCHAR2,
                                  cNumTarjeta_in           IN VARCHAR2,
                                  cFecExpiracion_in        IN VARCHAR2,
                                  cFecTransaccion_in       IN VARCHAR2,
                                  cHoraTransaccion_in      IN VARCHAR2,
                                  cCodOperacion_in         IN VARCHAR2,
                                  cCortePapel_in           IN VARCHAR2,
                                  cMontoPropina_in         IN VARCHAR2,
                                  cNumMozo_in              IN VARCHAR2,
                                  cEmpresa_in              IN VARCHAR2,
                                  cNumTerminal_in          IN VARCHAR2,
                                  cCantCuotas_in           IN VARCHAR2,
                                  cPagoDiferido_in         IN VARCHAR2,
                                  cFlagPidePin_in          IN VARCHAR2,
                                  cIdUnico_in              IN VARCHAR2,
                                  cMontoCuota_in           IN VARCHAR2,
                                  cUsuario_in              IN VARCHAR2,
                                  cNumPedido_in            IN VARCHAR2,
                                  cTipoRegistro_in         IN VARCHAR2,
                                  cCodFormaPago            IN VARCHAR2)
  RETURN CHAR;

  --Descripcion: Consulta si el pedido ha sido pagado con una transacción de pinpad o POS
  --             y retorna la información de una transacción realizada con pinpad 
  --Fecha        Usuario		    Comentario
  --13/08/2013   LLEIVA         Creación
  FUNCTION CONS_INF_TRANS_PINPAD(cCodGrupoCia_in          IN CHAR,
                                  cCod_Cia_in              IN CHAR,
                                  cCod_Local_in            IN CHAR,
                                  cNumPedido_in            IN CHAR)
  RETURN CHAR;
  
  --Descripcion: Retorna el html para la impresión del voucher de una transacción VISA
  --Fecha        Usuario		    Comentario
  --13/08/2013   LLEIVA         Creación
  FUNCTION IMP_VOUCHER_TRANSAC_VISA(cCodGrupoCia_in IN CHAR,
                                    cCod_Local_in IN CHAR,
                                    cNum_Pedido_in IN CHAR,
                                    cTip_copia_in IN CHAR)
  RETURN CLOB;
  
  --Descripcion: Guarda la información enviada por el pinpad de Mastercard para una determinada transacción
  --Fecha        Usuario		    Comentario
  --26/08/2013   LLEIVA         Creación
FUNCTION GRAB_TRAMA_PINPAD_MC(cCodGrupoCia_in          IN CHAR,
                                cCodCia_in               IN CHAR,
                                cCodLocal_in             IN CHAR,
                                cDscResponseCode_in      IN CHAR,
                                cDscAmount_in            IN CHAR,
                                cDscApprovalCode_in      IN CHAR,
                                cDscMessage_in           IN CHAR,
                                cDscCard_in              IN CHAR,
                                cDscCardId_in            IN CHAR,
                                cDscMonth_in             IN CHAR,
                                cDscAmountQuota_in       IN CHAR,
                                cDscCreditType_in        IN CHAR,
                                cDscClientName_in        IN CHAR,
                                cDscEcrCurrencyCode_in   IN CHAR,
                                cDscEcrAplicacion_in     IN CHAR,
                                cDscEcrTransaccion_in    IN CHAR,
                                cDscMerchantId_in        IN CHAR,
                                cDscPrintData_in         IN CHAR,
                                cNumPedVta_in            IN CHAR,
                                cUsuario_in              IN CHAR,
                                cCodFormaPago            IN CHAR)
  RETURN CHAR;
  
  --Descripcion: Retorna el numero de referencia de una transacción del pinpad Mastercard
  --Fecha        Usuario		    Comentario
  --03/09/2013   LLEIVA         Creación
  FUNCTION RET_NUM_REFERENCIA_PINPAD_MC(cCodProcesoPinpadMc_in IN CHAR)
  RETURN CHAR;

  --Descripcion: Guarda la información enviada por el pinpad de Mastercard para una determinada anulacion
  --Fecha        Usuario		    Comentario
  --04/09/2013   LLEIVA         Creación
  FUNCTION GRAB_TRAMA_ANUL_PINPAD_MC(cCodGrupoCia_in          IN CHAR,
                                cCodCia_in               IN CHAR,
                                cCodLocal_in             IN CHAR,
                                cDscResponseCode_in      IN CHAR,
                                cDscAmount_in            IN CHAR,
                                cDscApprovalCode_in      IN CHAR,
                                cDscMessage_in           IN CHAR,
                                cDscCard_in              IN CHAR,
                                cDscMonth_in             IN CHAR,
                                cDscAmountQuota_in       IN CHAR,
                                cDscCreditType_in        IN CHAR,
                                cDscClientName_in        IN CHAR,
                                cDscEcrCurrencyCode_in   IN CHAR,
                                cDscEcrAplicacion_in     IN CHAR,
                                cDscEcrTransaccion_in    IN CHAR,
                                cDscMerchantId_in        IN CHAR,
                                cDscPrintData_in         IN CHAR,
                                cNumPedVta_in            IN CHAR,
                                cUsuario_in              IN CHAR)
  RETURN CHAR;
  
  --Descripcion: Guarda la información enviada por el pinpad de Mastercard para una determinada reimpresión
  --Fecha        Usuario		    Comentario
  --04/09/2013   LLEIVA         Creación
  FUNCTION GRAB_TRAMA_REIMP_PINPAD_MC(cCodGrupoCia_in          IN CHAR,
                                cCodCia_in               IN CHAR,
                                cCodLocal_in             IN CHAR,
                                cDscResponseCode_in      IN CHAR,
                                cDscMessage_in           IN CHAR,
                                cDscEcrCurrencyCode_in   IN CHAR,
                                cDscEcrAplicacion_in     IN CHAR,
                                cDscEcrTransaccion_in    IN CHAR,
                                cDscMerchantId_in        IN CHAR,
                                cDscPrintData_in         IN CHAR,
                                cNumPedVta_in            IN CHAR,
                                cUsuario_in              IN CHAR)
  RETURN CHAR;

  --Descripcion: Guarda la información enviada por el pinpad de Mastercard para un determinado reporte detallado
  --Fecha        Usuario		    Comentario
  --04/09/2013   LLEIVA         Creación
  FUNCTION GRAB_TRAMA_REPD_PINPAD_MC(cCodGrupoCia_in          IN CHAR,
                                cCodCia_in               IN CHAR,
                                cCodLocal_in             IN CHAR,
                                cDscResponseCode_in      IN CHAR,
                                cDscMessage_in           IN CHAR,
                                cDscEcrCurrencyCode_in   IN CHAR,
                                cDscEcrAplicacion_in     IN CHAR,
                                cDscEcrTransaccion_in    IN CHAR,
                                cDscMerchantId_in        IN CHAR,
                                cDscPrintData_in         IN CHAR,
                                cNumPedVta_in            IN CHAR,
                                cUsuario_in              IN CHAR)
  RETURN CHAR;
  
  --Descripcion: Guarda la información enviada por el pinpad de Mastercard para un determinado reporte totales
  --Fecha        Usuario		    Comentario
  --04/09/2013   LLEIVA         Creación
  FUNCTION GRAB_TRAMA_REPT_PINPAD_MC(cCodGrupoCia_in          IN CHAR,
                                cCodCia_in               IN CHAR,
                                cCodLocal_in             IN CHAR,
                                cDscResponseCode_in      IN CHAR,
                                cDscMessage_in           IN CHAR,
                                cDscEcrCurrencyCode_in   IN CHAR,
                                cDscEcrAplicacion_in     IN CHAR,
                                cDscEcrTransaccion_in    IN CHAR,
                                cDscMerchantId_in        IN CHAR,
                                cDscPrintData_in         IN CHAR,
                                cNumPedVta_in            IN CHAR,
                                cUsuario_in              IN CHAR)
  RETURN CHAR;
  
  --Descripcion: Guarda la información enviada por el pinpad de Mastercard para un determinado cierre
  --Fecha        Usuario		    Comentario
  --04/09/2013   LLEIVA         Creación
  FUNCTION GRAB_TRAMA_CIER_PINPAD_MC(cCodGrupoCia_in          IN CHAR,
                                cCodCia_in               IN CHAR,
                                cCodLocal_in             IN CHAR,
                                cDscResponseCode_in      IN CHAR,
                                cDscMessage_in           IN CHAR,
                                cDscEcrCurrencyCode_in   IN CHAR,
                                cDscEcrAplicacion_in     IN CHAR,
                                cDscEcrTransaccion_in    IN CHAR,
                                cDscMerchantId_in        IN CHAR,
                                cDscPrintData_in         IN CHAR,
                                cNumPedVta_in            IN CHAR,
                                cUsuario_in              IN CHAR)
  RETURN CHAR;

  --Descripcion: Consulta si una transaccion existe, de acuerdo al tipo de tarjeta, num de referencia, autorización y monto
  --Fecha        Usuario		    Comentario
  --09/09/2013   LLEIVA         Creación
  FUNCTION CONS_TRANSACCION_PINPAD(cCodGrupoCia_in          IN CHAR,
                                   cCodCia_in               IN CHAR,
                                   cCodLocal_in             IN CHAR,
                                   cTipTarjeta_in           IN CHAR,
                                   cNumReferencia_in        IN CHAR,
                                   cNumAutorizacion_in      IN CHAR,
                                   cFecha_in                IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene cabecera de voucer
  --Fecha        Usuario		    Comentario
  --13/08/2013   LLEIVA         	Creacion  
  --17/12/2013   ERIOS         	    Se agrega el parametro nTipoOperador_in
  FUNCTION OBTENER_CABECERA_VOUCHERS(cCodGrupoCia_in       IN CHAR,
                                     cCod_Local_in         IN CHAR,
									 nTipoOperador_in      IN NUMBER)
  RETURN CLOB;
  
    --Descripcion: Consulta si el pedido ha sido pagado con una transacción de pinpad o POS
  --             y retorna la información de una transacción realizada con pinpad
  --Fecha        Usuario		    Comentario
  --13/08/2013   LLEIVA         Creación
  FUNCTION CONS_INF_TRANS_PINPAD2(cCodGrupoCia_in          IN CHAR,
                                  cCod_Cia_in              IN CHAR,
                                  cCod_Local_in            IN CHAR,
                                  cNumPedido_in            IN CHAR)
  RETURN CHAR;
  
  FUNCTION GRAB_IND_ANUL_TRANS_CERR(cCodGrupoCia_in          IN CHAR,
                                    cCodCia_in               IN CHAR,
                                    cCodLocal_in             IN CHAR,
                                    cNumPedVta_in            IN CHAR,
                                    cCodOperTarj             IN CHAR)
  RETURN CHAR;
                                    
end PTOVENTA_PINPAD;

/
