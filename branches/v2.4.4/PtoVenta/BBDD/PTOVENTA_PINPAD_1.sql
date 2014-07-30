--------------------------------------------------------
--  DDL for Package Body PTOVENTA_PINPAD
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_PINPAD" is

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
  RETURN CHAR
  IS
  BEGIN
         INSERT INTO VTA_PROCESO_PINPAD_VISA (COD_PROCESO_PINPAD_VISA,
                                              COD_GRUPO_CIA,
                                              COD_CIA,
                                              COD_LOCAL,
                                              COD_RESPUESTA_TRAMA,
                                              DSC_MSJ_FIN_OPE_TRAMA,
                                              DSC_NOMBRE_CLIENTE_TRAMA,
                                              DSC_NUM_AUTORIZACION_TRAMA,
                                              DSC_NUM_REFERENCIA_TRAMA,
                                              DSC_NUM_TARJETA_TRAMA,
                                              DSC_FECHA_EXPIRACION_TRAMA,
                                              DSC_FECHA_TRANSACCION_TRAMA,
                                              DSC_HORA_TRANSACCION_TRAMA,
                                              DSC_COD_OPERACION_TRAMA,
                                              DSC_CORTE_PAPEL,
                                              DSC_MONTO_PROPINA,
                                              DSC_NUM_MOZO,
                                              DSC_EMPRESA,
                                              DSC_TERMINAL,
                                              DSC_CUOTAS,
                                              DSC_PAGO_DIFERIDO,
                                              DSC_FLAG_PIDE_PIN,
                                              DSC_ID_UNICO,
                                              DSC_MONTO_CUOTA,
                                              FEC_CREA,
                                              USU_CREA,
                                              NUM_PED_VTA,
                                              SEC_ORDEN,
                                              TIP_REGISTRO,
                                              COD_FORMA_PAGO)
         VALUES(SQ_VTA_PROCESO_PINPAD_VISA.NEXTVAL,
                cCodGrupoCia_in,
                cCod_Cia_in,
                cCod_Local_in,
                cCodRespuesta_in,
                cMsjeFinOperacion_in,
                cNomCliente_in,
                cNumAutorizacion_in,
                cNumReferencia_in,
                cNumTarjeta_in,
                cFecExpiracion_in,
                cFecTransaccion_in,
                cHoraTransaccion_in,
                cCodOperacion_in,
                cCortePapel_in,
                cMontoPropina_in,
                cNumMozo_in,
                cEmpresa_in,
                cNumTerminal_in,
                cCantCuotas_in,
                cPagoDiferido_in,
                cFlagPidePin_in,
                cIdUnico_in,
                cMontoCuota_in,
                SYSDATE,
                cUsuario_in,
                cNumPedido_in,
                (select COUNT(*)+1
                  from VTA_PROCESO_PINPAD_VISA
                  where NUM_PED_VTA = cNumPedido_in
                  and COD_GRUPO_CIA = cCodGrupoCia_in
                  and COD_CIA = cCod_Cia_in
                  and COD_LOCAL = cCod_Local_in),
                cTipoRegistro_in,
                cCodFormaPago
               );
         commit;
         RETURN 'TRUE';
  EXCEPTION
  WHEN OTHERS THEN
       DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
       RETURN 'FALSE';
  END;

  --Descripcion: Consulta si el pedido ha sido pagado con una transacción de pinpad o POS
  --             y retorna la información de una transacción realizada con pinpad
  --Fecha        Usuario		    Comentario
  --13/08/2013   LLEIVA         Creación
  FUNCTION CONS_INF_TRANS_PINPAD(cCodGrupoCia_in          IN CHAR,
                                  cCod_Cia_in              IN CHAR,
                                  cCod_Local_in            IN CHAR,
                                  cNumPedido_in            IN CHAR)
  RETURN CHAR
  IS
       CFP CHAR(5);
       RESULTADO VARCHAR(100);
  BEGIN
       select FP.COD_FORMA_PAGO
              INTO CFP
       from vta_forma_pago_pedido FPP
       inner join vta_forma_pago FP on FP.COD_FORMA_PAGO=FPP.COD_FORMA_PAGO
       where FPP.NUM_PED_VTA = cNumPedido_in AND
             FPP.COD_GRUPO_CIA = cCodGrupoCia_in AND
             FPP.COD_CIA = cCod_Cia_in AND
             FPP.COD_LOCAL = cCod_Local_in AND
             FP.IND_TARJ = 'S';

       --LLEIVA 10-Abr-2014 Se busca primero en tablas de pinpad VISA
       BEGIN
--       IF(CFP='00083') THEN
             --se verifica si se pago con pinpad Visa
             --OPEN curListado FOR
             select(CFP ||'Ã'||
                    TO_CHAR(FEC_CREA,'DDMMYYYY') ||'Ã'||
                    DSC_NUM_REFERENCIA_TRAMA ||'Ã'||
                    to_char(FPP.IM_TOTAL_PAGO,'9990.00'))
             INTO RESULTADO
             from vta_proceso_pinpad_visa PPV
             inner join vta_forma_pago_pedido FPP on FPP.NUM_PED_VTA = PPV.NUM_PED_VTA
             where PPV.NUM_PED_VTA = cNumPedido_in and
                   PPV.TIP_REGISTRO = TIP_COMPRA and
                   FPP.COD_FORMA_PAGO = CFP;
       --LLEIVA 10-Abr-2014 Si no devuelve registros, buscar en tablas de pinpad MC
       EXCEPTION
         WHEN NO_DATA_FOUND THEN
           BEGIN
       --ELSIF (CFP='00084' or CFP='00087' or CFP='00088') THEN
             --OPEN curListado FOR
             select(CFP ||'Ã'||
                    TO_CHAR(FEC_CREA,'DDMMYYYY') ||'Ã'||
                    RET_NUM_REFERENCIA_PINPAD_MC(COD_PROCESO_PINPAD_MC) ||'Ã'||
                    to_char(FPP.IM_TOTAL_PAGO,'9990.00')) 
             INTO RESULTADO
             from VTA_PROCESO_PINPAD_MC PPM
             inner join vta_forma_pago_pedido FPP on FPP.NUM_PED_VTA = PPM.NUM_PED_VTA
             where PPM.NUM_PED_VTA=cNumPedido_in and
                   FPP.COD_FORMA_PAGO = CFP;
--       ELSE
           EXCEPTION
             WHEN NO_DATA_FOUND THEN
               
                select NULL INTO RESULTADO FROM DUAL;
           END;
       END;
       --END IF;
       return RESULTADO;
  EXCEPTION
  WHEN NO_DATA_FOUND THEN
       --DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
       --OPEN curListado FOR
       select NULL INTO RESULTADO FROM DUAL;
       RETURN RESULTADO;
  END;

  --Descripcion: Retorna el html para la impresión del voucher de una transacción VISA
  --Fecha        Usuario		    Comentario
  --13/08/2013   LLEIVA         Creación
  FUNCTION IMP_VOUCHER_TRANSAC(cCodGrupoCia_in IN CHAR,
                               cCod_Local_in   IN CHAR,
                               cOperador_in    IN CHAR,
                               cNum_Pedido_in  IN CHAR,
                               cTip_copia_in   IN CHAR)
  RETURN CLOB
  IS
      impr1 VARCHAR(1000);
      impr2 CLOB;

      msjVoucher CLOB;

      nomMarca varchar2(100);
      razonSocial varchar2(100);
      numRuc varchar2(16);
      direccion varchar2(200);
  BEGIN
      IF (cOperador_in ='M')
      THEN
          select DSC_PRINT_DATA
                 INTO msjVoucher
          from VTA_PROCESO_PINPAD_MC
          where NUM_PED_VTA = cNum_Pedido_in;

          return msjVoucher;
      ELSIF (cOperador_in ='V')
      THEN
          --si el registro existe devolver el texto en HTML para la impresión
          IF (cTip_copia_in = 'C') THEN
              --se consulta la cabecera
              SELECT MG.NOM_MARCA,
                     PC.RAZ_SOC_CIA,
                     PC.NUM_RUC_CIA,
                     REPLACE(PC.DIR_CIA,' ','{ESP}')
                     INTO nomMarca, razonSocial, numRuc, direccion
              FROM PBL_LOCAL PL
              JOIN PBL_MARCA_CIA MC ON (PL.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                        PL.COD_MARCA = MC.COD_MARCA AND
                                        PL.COD_CIA = MC.COD_CIA)
              JOIN PBL_MARCA_GRUPO_CIA MG ON (MG.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                              MG.COD_MARCA = MC.COD_MARCA)
              JOIN PBL_CIA PC ON (PC.COD_CIA = PL.COD_CIA)
              WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND PL.COD_LOCAL = cCod_Local_in;


              --se consulta el mensaje del cuerpo del voucher para la impresión
              select DSC_MSJ_FIN_OPE_TRAMA
                     INTO msjVoucher
              from vta_proceso_pinpad_visa
              where NUM_PED_VTA = cNum_Pedido_in
                AND TIP_REGISTRO = 'I'
                AND SEC_ORDEN = 1
              order by 1 desc;

              --reemplazamos saltos de linea por <br/>
              select REPLACE (msjVoucher, CHR(10), '<br/>') into msjVoucher from dual;

              --se arma la cabecera del voucher para la impresión
              impr2:= OBTENER_CABECERA_VOUCHERS(cCodGrupoCia_in,cCod_Local_in,OPERADOR_VISA);

              impr2:=impr2 || msjVoucher;

          ELSE

              --se consulta el mensaje del cuerpo del voucher para la impresión
              select DSC_MSJ_FIN_OPE_TRAMA
                     INTO msjVoucher
              from vta_proceso_pinpad_visa
              where NUM_PED_VTA = cNum_Pedido_in
                AND TIP_REGISTRO = 'I'
                AND SEC_ORDEN = 2
              order by 1 desc;

              --reemplazamos saltos de linea por <br/>
              select REPLACE (msjVoucher, CHR(10), '<br/>') into msjVoucher from dual;

              --se arma el cuerpo del voucher para la impresión
              impr2:= msjVoucher;
          END IF;

          --LLEIVA 11-Feb-2014 El pie se incluira en Java
          --impr2:='<br/><br/><br/>' ||impr2|| '<p>.</p></div></body></html>';

 	        --reemplazamos los valores en blanco por la etiqueta &nbsp;
          select REPLACE (impr2, ' ', '&nbsp;') into impr2 from dual;

          --incluye espacios en blanco necesarios
          select REPLACE (impr2, '{ESP}', ' ') into impr2 from dual;

          return impr1||impr2;
      END IF;
  EXCEPTION
  when others then
       return null;
  END;

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
  RETURN CHAR
  IS
  BEGIN
       INSERT INTO VTA_PROCESO_PINPAD_MC (COD_PROCESO_PINPAD_MC,
                                          COD_GRUPO_CIA,
                                          COD_CIA,
                                          COD_LOCAL,
                                          DSC_RESPONSE_CODE,
                                          DSC_AMOUNT,
                                          DSC_APPROVAL_CODE,
                                          DSC_MESSAGE,
                                          DSC_CARD,
                                          DSC_CARD_ID,
                                          DSC_MONTH,
                                          DSC_AMOUNT_QUOTA,
                                          DSC_CREDIT_TYPE,
                                          DSC_CLIENT_NAME,
                                          DSC_ECR_CURRENCY_CODE,
                                          DSC_ECR_APLICACION,
                                          DSC_ECR_TRANSACCION,
                                          DSC_MERCHANT_ID,
                                          DSC_PRINT_DATA,
                                          NUM_PED_VTA,
                                          FEC_CREA,
                                          USU_CREA,
                                          TIP_REGISTRO,
                                          COD_FORMA_PAGO)
         VALUES(SQ_VTA_PROCESO_PINPAD_MC.NEXTVAL,
                cCodGrupoCia_in,
                cCodCia_in,
                cCodLocal_in,
                cDscResponseCode_in,
                cDscAmount_in,
                cDscApprovalCode_in,
                cDscMessage_in,
                cDscCard_in,
                cDscCardId_in,
                cDscMonth_in,
                cDscAmountQuota_in,
                cDscCreditType_in,
                cDscClientName_in,
                cDscEcrCurrencyCode_in,
                cDscEcrAplicacion_in,
                cDscEcrTransaccion_in,
                cDscMerchantId_in,
                cDscPrintData_in,
                cNumPedVta_in,
                SYSDATE,
                cUsuario_in,
                TIP_COMPRA,
                cCodFormaPago);
         commit;
         RETURN 'TRUE';
  EXCEPTION
  WHEN OTHERS THEN
       rollback;
       RETURN 'FALSE';
  END;

  --Descripcion: Retorna el numero de referencia de una transacción del pinpad Mastercard
  --Fecha        Usuario		    Comentario
  --03/09/2013   LLEIVA         Creación
  FUNCTION RET_NUM_REFERENCIA_PINPAD_MC(cCodProcesoPinpadMc_in IN CHAR)
  RETURN CHAR
  IS
    num_ref VARCHAR(4);
  BEGIN
       select SUBSTR(DSC_MESSAGE,
              INSTRC(DSC_MESSAGE, 'REF', 1, 1)+3,
              4) into num_ref
       from VTA_PROCESO_PINPAD_MC
       where COD_PROCESO_PINPAD_MC=cCodProcesoPinpadMc_in;
       return num_ref;
  EXCEPTION
  WHEN OTHERS THEN
       return '';
  END;

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
                                cUsuario_in              IN CHAR,
								cCodFormaPago            IN VARCHAR2)
  RETURN CHAR
  IS
  BEGIN
         INSERT INTO VTA_PROCESO_PINPAD_MC (COD_PROCESO_PINPAD_MC,
                                          COD_GRUPO_CIA,
                                          COD_CIA,
                                          COD_LOCAL,
                                          DSC_RESPONSE_CODE,
                                          DSC_AMOUNT,
                                          DSC_APPROVAL_CODE,
                                          DSC_MESSAGE,
                                          DSC_CARD,
                                          DSC_MONTH,
                                          DSC_AMOUNT_QUOTA,
                                          DSC_CREDIT_TYPE,
                                          DSC_CLIENT_NAME,
                                          DSC_ECR_CURRENCY_CODE,
                                          DSC_ECR_APLICACION,
                                          DSC_ECR_TRANSACCION,
                                          DSC_MERCHANT_ID,
                                          DSC_PRINT_DATA,
                                          NUM_PED_VTA,
                                          FEC_CREA,
                                          USU_CREA,
                                          TIP_REGISTRO,
										  COD_FORMA_PAGO)
         VALUES(SQ_VTA_PROCESO_PINPAD_MC.NEXTVAL,
                cCodGrupoCia_in,
                cCodCia_in,
                cCodLocal_in,
                cDscResponseCode_in,
                cDscAmount_in,
                cDscApprovalCode_in,
                cDscMessage_in,
                cDscCard_in,
                cDscMonth_in,
                cDscAmountQuota_in,
                cDscCreditType_in,
                cDscClientName_in,
                cDscEcrCurrencyCode_in,
                cDscEcrAplicacion_in,
                cDscEcrTransaccion_in,
                cDscMerchantId_in,
                cDscPrintData_in,
                cNumPedVta_in,
                SYSDATE,
                cUsuario_in,
                TIP_ANULACION,
				cCodFormaPago);
         commit;
         RETURN 'TRUE';
  EXCEPTION
  WHEN OTHERS THEN
       rollback;
       RETURN 'FALSE';
  END;

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
  RETURN CHAR
  IS
  BEGIN
         INSERT INTO VTA_PROCESO_PINPAD_MC (COD_PROCESO_PINPAD_MC,
                                          COD_GRUPO_CIA,
                                          COD_CIA,
                                          COD_LOCAL,
                                          DSC_RESPONSE_CODE,
                                          DSC_MESSAGE,
                                          DSC_ECR_CURRENCY_CODE,
                                          DSC_ECR_APLICACION,
                                          DSC_ECR_TRANSACCION,
                                          DSC_MERCHANT_ID,
                                          DSC_PRINT_DATA,
                                          NUM_PED_VTA,
                                          FEC_CREA,
                                          USU_CREA,
                                          TIP_REGISTRO)
         VALUES(SQ_VTA_PROCESO_PINPAD_MC.NEXTVAL,
                cCodGrupoCia_in,
                cCodCia_in,
                cCodLocal_in,
                cDscResponseCode_in,
                cDscMessage_in,
                cDscEcrCurrencyCode_in,
                cDscEcrAplicacion_in,
                cDscEcrTransaccion_in,
                cDscMerchantId_in,
                cDscPrintData_in,
                cNumPedVta_in,
                SYSDATE,
                cUsuario_in,
                'REI');
         commit;
         RETURN 'TRUE';
  EXCEPTION
  WHEN OTHERS THEN
       rollback;
       RETURN 'FALSE';
  END;

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
  RETURN CHAR
  IS
  BEGIN
         INSERT INTO VTA_PROCESO_PINPAD_MC (COD_PROCESO_PINPAD_MC,
                                          COD_GRUPO_CIA,
                                          COD_CIA,
                                          COD_LOCAL,
                                          DSC_RESPONSE_CODE,
                                          DSC_MESSAGE,
                                          DSC_ECR_CURRENCY_CODE,
                                          DSC_ECR_APLICACION,
                                          DSC_ECR_TRANSACCION,
                                          DSC_MERCHANT_ID,
                                          DSC_PRINT_DATA,
                                          NUM_PED_VTA,
                                          FEC_CREA,
                                          USU_CREA,
                                          TIP_REGISTRO)
         VALUES(SQ_VTA_PROCESO_PINPAD_MC.NEXTVAL,
                cCodGrupoCia_in,
                cCodCia_in,
                cCodLocal_in,
                cDscResponseCode_in,
                cDscMessage_in,
                cDscEcrCurrencyCode_in,
                cDscEcrAplicacion_in,
                cDscEcrTransaccion_in,
                cDscMerchantId_in,
                cDscPrintData_in,
                cNumPedVta_in,
                SYSDATE,
                cUsuario_in,
                'RDE');
         commit;
         RETURN 'TRUE';
  EXCEPTION
  WHEN OTHERS THEN
       rollback;
       RETURN 'FALSE';
  END;

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
  RETURN CHAR
  IS
  BEGIN
         INSERT INTO VTA_PROCESO_PINPAD_MC (COD_PROCESO_PINPAD_MC,
                                          COD_GRUPO_CIA,
                                          COD_CIA,
                                          COD_LOCAL,
                                          DSC_RESPONSE_CODE,
                                          DSC_MESSAGE,
                                          DSC_ECR_CURRENCY_CODE,
                                          DSC_ECR_APLICACION,
                                          DSC_ECR_TRANSACCION,
                                          DSC_MERCHANT_ID,
                                          DSC_PRINT_DATA,
                                          NUM_PED_VTA,
                                          FEC_CREA,
                                          USU_CREA,
                                          TIP_REGISTRO)
         VALUES(SQ_VTA_PROCESO_PINPAD_MC.NEXTVAL,
                cCodGrupoCia_in,
                cCodCia_in,
                cCodLocal_in,
                cDscResponseCode_in,
                cDscMessage_in,
                cDscEcrCurrencyCode_in,
                cDscEcrAplicacion_in,
                cDscEcrTransaccion_in,
                cDscMerchantId_in,
                cDscPrintData_in,
                cNumPedVta_in,
                SYSDATE,
                cUsuario_in,
                'RTO');
         commit;
         RETURN 'TRUE';
  EXCEPTION
  WHEN OTHERS THEN
       rollback;
       RETURN 'FALSE';
  END;

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
  RETURN CHAR
  IS
  BEGIN
         INSERT INTO VTA_PROCESO_PINPAD_MC (COD_PROCESO_PINPAD_MC,
                                          COD_GRUPO_CIA,
                                          COD_CIA,
                                          COD_LOCAL,
                                          DSC_RESPONSE_CODE,
                                          DSC_MESSAGE,
                                          DSC_ECR_CURRENCY_CODE,
                                          DSC_ECR_APLICACION,
                                          DSC_ECR_TRANSACCION,
                                          DSC_MERCHANT_ID,
                                          DSC_PRINT_DATA,
                                          NUM_PED_VTA,
                                          FEC_CREA,
                                          USU_CREA,
                                          TIP_REGISTRO)
         VALUES(SQ_VTA_PROCESO_PINPAD_MC.NEXTVAL,
                cCodGrupoCia_in,
                cCodCia_in,
                cCodLocal_in,
                cDscResponseCode_in,
                cDscMessage_in,
                cDscEcrCurrencyCode_in,
                cDscEcrAplicacion_in,
                cDscEcrTransaccion_in,
                cDscMerchantId_in,
                cDscPrintData_in,
                cNumPedVta_in,
                SYSDATE,
                cUsuario_in,
                'CIE');
         commit;
         RETURN 'TRUE';
  --EXCEPTION
  --WHEN OTHERS THEN
  --     rollback;
  --     RETURN 'FALSE';
  END;

  --Descripcion: Consulta si una transaccion existe, de acuerdo al tipo de tarjeta, num de referencia, autorización
  --Fecha        Usuario		    Comentario
  --09/09/2013   LLEIVA         Creación
  FUNCTION CONS_TRANSACCION_PINPAD(cCodGrupoCia_in          IN CHAR,
                                   cCodCia_in               IN CHAR,
                                   cCodLocal_in             IN CHAR,
                                   cTipTarjeta_in           IN CHAR,
                                   cNumReferencia_in        IN CHAR,
                                   cNumAutorizacion_in      IN CHAR,
                                   cFecha_in                IN CHAR)
  RETURN VARCHAR2
  IS
     MONTO VARCHAR2(50);
  BEGIN
	--ERIOS 2.3.4 Se permite la anulacion de voucher de los pedidos anulados (RCASTRO)
       IF (cTipTarjeta_in = 'M') THEN
            select trim(to_char((TO_NUMBER(MC.DSC_AMOUNT)/100),'9990.00')) ||'Ã'||
                   (select EST_PED_VTA||decode(ind_pedido_anul,'N','',ind_pedido_anul)
                    from VTA_PEDIDO_VTA_CAB
                    where NUM_PED_VTA = MC.NUM_PED_VTA) ||'Ã'||
					MC.NUM_PED_VTA ||'Ã'||
					MC.COD_FORMA_PAGO
					into MONTO
            from vta_proceso_pinpad_mc MC
            where MC.COD_GRUPO_CIA=cCodGrupoCia_in
              and MC.COD_CIA = cCodCia_in
              and MC.COD_LOCAL = cCodLocal_in
              and MC.DSC_APPROVAL_CODE = cNumAutorizacion_in
              and TO_CHAR(MC.FEC_CREA,'DD/MM/YYYY')=cFecha_in
              and MC.TIP_REGISTRO = TIP_COMPRA
              and PTOVENTA_PINPAD.RET_NUM_REFERENCIA_PINPAD_MC(MC.COD_PROCESO_PINPAD_MC)=cNumReferencia_in;
       ELSIF (cTipTarjeta_in = 'V') THEN
            select ' ' ||'Ã'||
                   (select EST_PED_VTA||decode(ind_pedido_anul,'N','',ind_pedido_anul)
                    from VTA_PEDIDO_VTA_CAB
                    where NUM_PED_VTA = VI.NUM_PED_VTA) ||'Ã'||
					VI.NUM_PED_VTA ||'Ã'||
					VI.COD_FORMA_PAGO
					into MONTO
            from vta_proceso_pinpad_visa VI
            where VI.COD_GRUPO_CIA = cCodGrupoCia_in and
                  VI.COD_CIA = cCodCia_in and
                  VI.COD_LOCAL = cCodLocal_in and
                  VI.DSC_NUM_AUTORIZACION_TRAMA = cNumAutorizacion_in and
                  VI.DSC_NUM_REFERENCIA_TRAMA = cNumReferencia_in and
                  VI.TIP_REGISTRO = TIP_COMPRA and
                  TO_CHAR(VI.FEC_CREA,'DD/MM/YYYY')=cFecha_in;
       END IF;
       return MONTO;
  EXCEPTION
  WHEN OTHERS THEN
       DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
       RETURN 'FALSE';
  END;

  FUNCTION OBTENER_CABECERA_VOUCHERS(cCodGrupoCia_in       IN CHAR,
                                     cCod_Local_in         IN CHAR,
									 nTipoOperador_in      IN NUMBER)
  RETURN CLOB
  IS
      impr2 CLOB;

      nomMarca varchar2(100);
      razonSocial varchar2(100);
      numRuc varchar2(16);
      direccion varchar2(200);
      long_direccion integer;
	    vTelefCia PBL_CIA.TELF_CIA%TYPE;
	    vDirecLocal varchar2(200);
      info_local varchar2(200);
      long_info_local integer;
  BEGIN
       --se consulta la cabecera
        SELECT MG.NOM_MARCA,
               PC.RAZ_SOC_CIA,
               PC.NUM_RUC_CIA,
               REPLACE(PC.DIR_CIA,' ','{ESP}'),
			         PC.TELF_CIA,
			         --REPLACE(PL.DIREC_LOCAL_CORTA,' ','{ESP}')
               PL.DIREC_LOCAL_CORTA
               INTO nomMarca, razonSocial, numRuc, direccion, vTelefCia,vDirecLocal
        FROM PBL_LOCAL PL
        JOIN PBL_MARCA_CIA MC ON (PL.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                  PL.COD_MARCA = MC.COD_MARCA AND
                                  PL.COD_CIA = MC.COD_CIA)
        JOIN PBL_MARCA_GRUPO_CIA MG ON (MG.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                        MG.COD_MARCA = MC.COD_MARCA)
        JOIN PBL_CIA PC ON (PC.COD_CIA = PL.COD_CIA)
        WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
              AND PL.COD_LOCAL = cCod_Local_in;

        --se verifica longitud para que no se descuadre el ticket
        select LENGTH(direccion) into long_direccion from dual;

        IF (long_direccion>50) THEN
           select SUBSTR(direccion,0,50)||'<br>'||SUBSTR(direccion,51,long_direccion)
           into direccion
           from dual;
        END IF;

        info_local := cCod_Local_in || '{ESP}' || vDirecLocal;
        select LENGTH(info_local) into long_info_local from dual;

        IF (long_info_local>42) THEN
           select SUBSTR(info_local,0,42)||'<br>'||SUBSTR(info_local,43,long_info_local)
           into info_local
           from dual;
        END IF;
        
        --LLEIVA 11-Abr-2014
        select REPLACE(vDirecLocal,' ','{ESP}') into vDirecLocal from dual;

        --se arma la cabecera del voucher para la impresión
        impr2:='.<div{ESP}align="center"{ESP}class="tipoLetraA">{OPERADOR}</div>
<br/>
<div{ESP}align="center"{ESP}class="tipoLetraA">BOTICAS {MARCA}</div>
<div{ESP}class="tipoLetraA">{RAZON_SOCIAL} RUC:{NUM_RUC}</div>
<div{ESP}align="left"{ESP}class="tipoLetraA">Dom Fiscal:{DIRECCION}</div>
<div{ESP}align="left"{ESP}class="tipoLetraA">Telf:{ESP}{TELEF_CIA}</div>
<div{ESP}align="left"{ESP}class="tipoLetraA">T{INFO_LOCAL}</div>
<hr/>';
    select REPLACE (impr2, '{OPERADOR}', DECODE(nTipoOperador_in,OPERADOR_VISA,'VISANET',
																	   OPERADOR_MC,'PROCESOS DE MEDIOS DE PAGO S.A.')) into impr2 from dual;
		select REPLACE (impr2, '{MARCA}', nomMarca) into impr2 from dual;
    select REPLACE (impr2, '{RAZON_SOCIAL}', razonSocial) into impr2 from dual;
    select REPLACE (impr2, '{NUM_RUC}', numRuc) into impr2 from dual;
    select REPLACE (impr2, '{DIRECCION}', direccion) into impr2 from dual;
		--select REPLACE (impr2, '{LOCAL}', cCod_Local_in) into impr2 from dual;
		select REPLACE (impr2, '{TELEF_CIA}', vTelefCia) into impr2 from dual;
		--select REPLACE (impr2, '{DIREC_LOCAL}', vDirecLocal) into impr2 from dual;
 		select REPLACE (impr2, '{INFO_LOCAL}', info_local) into impr2 from dual;

        --incluye espacios en blanco necesarios
        select REPLACE (impr2, '{ESP}', ' ') into impr2 from dual;
        return impr2;
  EXCEPTION
  WHEN OTHERS THEN
       DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
       RETURN '';
  END;

  --Descripcion: Consulta si el pedido ha sido pagado con una transacción de pinpad o POS
  --             y retorna la información de una transacción realizada con pinpad
  --Fecha        Usuario		    Comentario
  --13/08/2013   LLEIVA         Creación
  FUNCTION CONS_INF_TRANS_PINPAD2(cCodGrupoCia_in          IN CHAR,
                                  cCod_Cia_in              IN CHAR,
                                  cCod_Local_in            IN CHAR,
                                  cNumPedido_in            IN CHAR)
  RETURN CHAR
  IS
       RESULTADO VARCHAR(100);
  BEGIN

       BEGIN
             --se verifica si se pago con pinpad VISA
             --OPEN curListado FOR
             select('00083' ||'Ã'||
                    TO_CHAR(FEC_CREA,'DDMMYYYY') ||'Ã'||
                    DSC_NUM_REFERENCIA_TRAMA) INTO RESULTADO
             from vta_proceso_pinpad_visa
             where NUM_PED_VTA = cNumPedido_in and
                   TIP_REGISTRO = TIP_COMPRA;
       EXCEPTION
       WHEN NO_DATA_FOUND THEN
            --se verifica si se pago con MC
             --OPEN curListado FOR
             select('00084' ||'Ã'||
                    TO_CHAR(FEC_CREA,'DDMMYYYY') ||'Ã'||
                    RET_NUM_REFERENCIA_PINPAD_MC(COD_PROCESO_PINPAD_MC)) INTO RESULTADO
             from VTA_PROCESO_PINPAD_MC
             where NUM_PED_VTA=cNumPedido_in;
       END;
       return RESULTADO;
  EXCEPTION
  WHEN NO_DATA_FOUND THEN
       --DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
       --OPEN curListado FOR
       select(' '||'Ã'||
              ' '||'Ã'||
              ' ') INTO RESULTADO FROM DUAL;
       RETURN RESULTADO;
  END;

  --Descripcion: Actualiza el flag de la transacción realizada para indicar si no se pudo anular por que el lote se encontraba cerrado
  --Fecha        Usuario		    Comentario
  --11/Dic/2013  LLEIVA         Creación
  FUNCTION GRAB_IND_ANUL_TRANS_CERR(cCodGrupoCia_in          IN CHAR,
                                    cCodCia_in               IN CHAR,
                                    cCodLocal_in             IN CHAR,
                                    cNumPedVta_in            IN CHAR,
                                    cCodOperTarj             IN CHAR)
  RETURN CHAR
  IS
  BEGIN
       IF (cCodOperTarj = 'MC') THEN
            UPDATE VTA_PROCESO_PINPAD_MC
            SET IND_ANUL_TRANS_CERR = 'S'
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
                  COD_CIA = cCodCia_in AND
                  COD_LOCAL = cCodLocal_in AND
                  NUM_PED_VTA = cNumPedVta_in AND
                  TIP_REGISTRO = TIP_COMPRA;
            commit;
            RETURN 'TRUE';
       END IF;
       IF(cCodOperTarj = 'VI') THEN

            UPDATE VTA_PROCESO_PINPAD_VISA
            SET IND_ANUL_TRANS_CERR = 'S'
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
                  COD_CIA = cCodCia_in AND
                  COD_LOCAL = cCodLocal_in AND
                  NUM_PED_VTA = cNumPedVta_in AND
                  TIP_REGISTRO = TIP_COMPRA;
            commit;
            RETURN 'TRUE';
       END IF;
  EXCEPTION
  WHEN OTHERS THEN
       rollback;
       RETURN 'FALSE';
  END;

  FUNCTION LIST_PEDIDOS_PINPAD(cCodGrupoCia_in          IN CHAR,
                               cCodCia_in               IN CHAR,
                               cCodLocal_in             IN CHAR,
                               cFechaInicial_in         IN CHAR,
                               cFechaFinal_in           IN CHAR)
  RETURN FarmaCursor
  IS
      curRep FarmaCursor;
      QUERY VARCHAR2(3000);
  BEGIN
      QUERY := 'select T.NUM_PED_VTA ||''Ã''||'         ||
               '       TO_CHAR(T.FEC_PED_VTA,''DD/MM/YYYY'') ||''Ã''||'         ||
               '       TO_CHAR(T.VAL_BRUTO_PED_VTA,''FM9990.00'') ||''Ã''||'   ||
               '       TO_CHAR(T.IM_PAGO_PINPAD,''FM9990.00'') ||''Ã''||'      ||
               '       DECODE(T.EST_PED_VTA,''C'',''COBRADO'',''N'',''ANULADO'',T.EST_PED_VTA)  ||''Ã''||'         ||
               '       T.DESC_COMP ||''Ã''||'       ||
               '       T.USU_CREA_PED_VTA_CAB ||''Ã''||'||
               '       T.OPERADOR ||''Ã''||'            ||
               '       T.COD_PROCESO '   ||
               'from ( '                                ||
               '      select PED.NUM_PED_VTA,    PED.FEC_PED_VTA, '            ||
               '      PED.VAL_BRUTO_PED_VTA,     FPP.IM_TOTAL_PAGO AS IM_PAGO_PINPAD, ' ||
               '      PED.EST_PED_VTA, '                                       ||
               '      TP.DESC_COMP,              PED.USU_CREA_PED_VTA_CAB, '   ||
               '      ''MC'' AS OPERADOR,        MC.COD_PROCESO_PINPAD_MC as COD_PROCESO '   ||
               '      from VTA_PEDIDO_VTA_CAB PED '                            ||
               '      inner join VTA_PROCESO_PINPAD_MC MC ON MC.NUM_PED_VTA=PED.Num_Ped_Vta '||
               '      inner join VTA_FORMA_PAGO_PEDIDO FPP ON FPP.NUM_PED_VTA=PED.NUM_PED_VTA and ' ||
               '                                              FPP.COD_FORMA_PAGO = MC.COD_FORMA_PAGO ' ||
               '      left join VTA_TIP_COMP TP on TP.TIP_COMP = PED.TIP_COMP_PAGO ' ||
               '      where PED.COD_GRUPO_CIA = ''' || cCodGrupoCia_in || ''' '||
               '      and PED.COD_CIA = ''' || cCodCia_in || ''' '             ||
               '      and PED.COD_LOCAL = ''' || cCodLocal_in || ''' ';

      IF (cFechaInicial_in IS NOT NULL and cFechaFinal_in IS NOT NULL) THEN
          QUERY := QUERY ||
                   '      and PED.FEC_PED_VTA '        ||
                   '      BETWEEN TO_DATE(''' || cFechaInicial_in || ' 00:00:00'',''DD/MM/YYYY HH24:MI:SS'') and ' ||
                   '              TO_DATE(''' || cFechaFinal_in || ' 23:59:59'',''DD/MM/YYYY HH24:MI:SS'') ';
      END IF;
      QUERY := QUERY ||
               '      UNION ' ||
               ' select PED.NUM_PED_VTA,           PED.FEC_PED_VTA, '          ||
               '        PED.VAL_BRUTO_PED_VTA,     FPP.IM_TOTAL_PAGO AS IM_PAGO_PINPAD, ' ||
               '        PED.EST_PED_VTA, '          ||
               '        TP.DESC_COMP,               PED.USU_CREA_PED_VTA_CAB, ' ||
               '        ''VISA'' AS OPERADOR,      VISA.COD_PROCESO_PINPAD_VISA as COD_PROCESO ' ||
               ' from VTA_PEDIDO_VTA_CAB PED '                                  ||
               ' inner join VTA_PROCESO_PINPAD_VISA VISA on VISA.NUM_PED_VTA=PED.NUM_PED_VTA and ' ||
               '                                            VISA.TIP_REGISTRO = '''||TIP_COMPRA||''' ' ||
               '      inner join VTA_FORMA_PAGO_PEDIDO FPP ON FPP.NUM_PED_VTA=PED.NUM_PED_VTA and ' ||
               '                                              FPP.COD_FORMA_PAGO = VISA.COD_FORMA_PAGO ' ||
               ' left join VTA_TIP_COMP TP on TP.TIP_COMP = PED.TIP_COMP_PAGO ' ||
               ' where PED.COD_GRUPO_CIA = '''|| cCodGrupoCia_in || ''' ' ||
               ' and PED.COD_CIA = ''' || cCodCia_in || ''' ' ||
               ' and PED.COD_LOCAL = ''' || cCodLocal_in  || ''' ';
      IF(cFechaInicial_in IS NOT null and cFechaFinal_in IS NOT NULL)
      THEN
          QUERY := QUERY ||
                   '      and PED.FEC_PED_VTA '        ||
                   '      BETWEEN TO_DATE(''' || cFechaInicial_in || ' 00:00:00'',''DD/MM/YYYY HH24:MI:SS'') and ' ||
                   '              TO_DATE(''' || cFechaFinal_in || ' 23:59:59'',''DD/MM/YYYY HH24:MI:SS'') ';
      END IF;
      QUERY := QUERY || ' ) T ORDER BY T.FEC_PED_VTA';

      DBMS_OUTPUT.put_line(QUERY);
      OPEN curRep FOR QUERY;
      RETURN curRep;
  END;


end PTOVENTA_PINPAD;

/
