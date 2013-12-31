--------------------------------------------------------
--  DDL for Package Body PTOVENTA_INT_REMITO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_INT_REMITO" is

  /****************************************************************************/

  PROCEDURE INT_GENERA_ARCHIVO(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in     IN CHAR,
                               cRemito_in       IN CHAR,
                               cIndEnviaAdjunto in char default 'N')
  AS
    CURSOR curResumenDia IS
    SELECT COD_LOCAL||
           TO_CHAR(FEC_OPERACION,'yyyyMMdd')||
           SEC_INT_CE||
           COD_CUADRATURA||
           CLASE_DOC||
           TO_CHAR(FEC_DOCUMENTO,'yyyyMMdd')||
           TO_CHAR(FEC_CONTABLE,'yyyyMMdd')||
           DESC_REFERENCIA||
           DESC_TEXTO_CAB||
           TIP_MONEDA||
           VAL_IMPORTE||
           DESC_CLAVE_CUENTA_1||
           DESC_CUENTA_1||
           CME||
           MARCA_IMPUESTO||
           DESC_ASIGNACION_1||
           DESC_TEXTO_DETALLE_1||
           DESC_CLAVE_CUENTA_2||
           DESC_CUENTA_2||
           DESC_CENTRO_COSTO||
           IND_IMPUESTO||
           DESC_TEXTO_DETALLE_2||
           COD_REMITO RESUMEN
    FROM   INT_CE_REMITO ICE
    WHERE  ICE.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    ICE.COD_LOCAL = cCodLocal_in
    AND    ICE.COD_REMITO = cRemito_in
    ORDER BY COD_GRUPO_CIA,COD_LOCAL,TO_CHAR(FEC_OPERACION,'yyyyMMdd'),COD_CUADRATURA,SEC_INT_CE;
    --INI - ASOSA, 01.10.2010
    /*SELECT COD_LOCAL ||
           CASE WHEN TO_CHAR(FEC_OPERACION,'MM') = TO_CHAR(

           (SELECT FEC_CREA_REMITO
      FROM ce_remito
      WHERE cod_remito = cRemito_in)

           ,'MM') THEN TO_CHAR(FEC_OPERACION,'yyyyMMdd')
                ELSE TO_CHAR(

                (SELECT FEC_CREA_REMITO
      FROM ce_remito
      WHERE cod_remito = cRemito_in)

                ,'yyyyMM')||'01' END ||
           SEC_INT_CE ||
           COD_CUADRATURA ||
           CLASE_DOC ||
           CASE WHEN TO_CHAR(FEC_DOCUMENTO,'MM') = TO_CHAR(

           (SELECT FEC_CREA_REMITO
      FROM ce_remito
      WHERE cod_remito = cRemito_in)

           ,'MM') THEN TO_CHAR(FEC_DOCUMENTO,'yyyyMMdd')
                ELSE TO_CHAR(

                (SELECT FEC_CREA_REMITO
      FROM ce_remito
      WHERE cod_remito = cRemito_in)

                ,'yyyyMM')||'01' END ||
           CASE WHEN TO_CHAR(FEC_CONTABLE,'MM') = TO_CHAR(

           (SELECT FEC_CREA_REMITO
      FROM ce_remito
      WHERE cod_remito = cRemito_in)

           ,'MM') THEN TO_CHAR(FEC_CONTABLE,'yyyyMMdd')
                ELSE TO_CHAR(

                (SELECT FEC_CREA_REMITO
      FROM ce_remito
      WHERE cod_remito = cRemito_in)

                ,'yyyyMM')||'01' END ||
           CASE WHEN SUBSTR(TRIM(DESC_REFERENCIA),10,2) = TO_CHAR(

           (SELECT FEC_CREA_REMITO
      FROM ce_remito
      WHERE cod_remito = cRemito_in)

           ,'MM') THEN DESC_REFERENCIA
                ELSE RPAD('RT-'||cod_local||'-'||'01'||TO_CHAR(

                (SELECT FEC_CREA_REMITO
      FROM ce_remito
      WHERE cod_remito = cRemito_in)

                ,'MMyyyy'),16,' ') END ||
           CASE WHEN SUBSTR(TRIM(DESC_TEXTO_CAB),10,2) = TO_CHAR(

           (SELECT FEC_CREA_REMITO
      FROM ce_remito
      WHERE cod_remito = cRemito_in)

           ,'MM') THEN DESC_TEXTO_CAB
                ELSE RPAD('RT-'||cod_local||'-'||'01'||TO_CHAR(

                (SELECT FEC_CREA_REMITO
      FROM ce_remito
      WHERE cod_remito = cRemito_in)

                ,'MMyyyy'),25,' ') END ||
           TIP_MONEDA ||
           VAL_IMPORTE ||
           DESC_CLAVE_CUENTA_1 ||
           DESC_CUENTA_1 ||
           CME ||
           MARCA_IMPUESTO ||
           DESC_ASIGNACION_1 ||
           DESC_TEXTO_DETALLE_1 ||
           DESC_CLAVE_CUENTA_2 ||
           DESC_CUENTA_2 ||
           DESC_CENTRO_COSTO ||
           IND_IMPUESTO ||
           DESC_TEXTO_DETALLE_2 ||
           COD_REMITO RESUMEN
    FROM   INT_CE_REMITO ICE
    WHERE  ICE.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    ICE.COD_LOCAL = cCodLocal_in
    AND    ICE.COD_REMITO = cRemito_in
    ORDER BY COD_GRUPO_CIA,COD_LOCAL,TO_CHAR(FEC_OPERACION,'yyyyMMdd'),COD_CUADRATURA,SEC_INT_CE;*/
    --FIN - ASOSA, 01.10.2010

    v_rCurResumenDia curResumenDia%ROWTYPE;
    v_cIndValidacion CHAR(1);
    v_vNombreArchivo VARCHAR2(100);
    v_eControlValidacion EXCEPTION;

    v_vDescLocal varchar2(200);

  BEGIN


      v_cIndValidacion := CE_VALIDA_DATA_INTERFACE(cCodGrupoCia_in,
                                                   cCodLocal_in,
                                                   cRemito_in);
      IF v_cIndValidacion = INDICADOR_ERROR THEN
         RAISE v_eControlValidacion;
      END IF;

      --DBMS_OUTPUT.PUT_LINE('vFecCierreDia_in : ' || vFecCierreDia_in);
      --DBMS_OUTPUT.PUT_LINE('TO_DATE(vFecCierreDia_in,dd/MM/yyyy) : ' || TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'));
      --DBMS_OUTPUT.PUT_LINE('TO_CHAR(TO_DATE(vFecCierreDia_in,dd/MM/yyyy),yyyyMMdd) : ' || TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'yyyyMMdd'));
      v_vNombreArchivo := 'RMT'||cCodLocal_in||cRemito_in||TO_CHAR(SYSDATE,'HH24MISS')||'.TXT';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));
      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'INICIO');
      FOR v_rCurResumenDia IN curResumenDia
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurResumenDia.RESUMEN);
      END LOOP;
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'FIN');
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
      DBMS_OUTPUT.PUT_LINE('SE GENERO EL ARCHIVO DE INTERFACE REMITO CORRECTAMENTE');
      --INICIO ADICION CORREO
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                   'EXITO AL GENERAR ARCHIVO DE REMITO PROSEGUR: ',
                                   'EXITO',
                                   'EXITO AL GENERAR ARCHIVO DE INTERFACE DE REMITO PROSEGUR NRO REMITO: '||cRemito_in);/*||'</B>'||
                                   '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR EN LA VALIDACION DE LA INFORMACION.'||'<B>');      */
      --FIN ADICION CORREO
      --ACTUALIZA LA FECHA DE CREACION DEL ARCHIVO
      CE_ACTUALIZA_FECHA_ARCHIVO(cCodGrupoCia_in,
                                 cCodLocal_in,
                                 cRemito_in,
                                 SYSDATE);

      COMMIT;

      if cIndEnviaAdjunto = 'S' then

      select l.desc_corta_local
      into   v_vDescLocal
      from   pbl_local l
      where  l.cod_grupo_cia = cCodGrupoCia_in
      and    l.cod_local = cCodLocal_in;

      RECEP_P_ENVIA_CORREO_ADJUNTO(
                            'REMITO ADJUNTO '||v_vNombreArchivo||' LOCAL: '||cCodLocal_in||'-'|| v_vDescLocal,
                            'ARCHIVO REMITO',
                            '<BR>' || 'REMITO GENERADO. ' || '<BR>'  ||'REMITO Nro. ' || v_vNombreArchivo || '<BR>',
                            v_vNombreArchivo);
      end if;

      --procedure que valida si se grabo bien los montos
      --11/12/2007 dubilluz creacion
/*      INT_VALIDA_MONTO_INTERFACE(cCodGrupoCia_in,
                                 cCodLocal_in,
                                 vFecCierreDia_in,
                                 v_vNombreArchivo);*/

  EXCEPTION
    WHEN v_eControlValidacion THEN
         DBMS_OUTPUT.PUT_LINE('ERROR EN LA VALIDACION DE LA INFORMACION');
         INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                      'ERROR AL GENERAR INTERFACE REMITO PROSEGUR: ',
                                      'ALERTA',
                                      'ERROR AL GENERAR LA INTERFACE REMITO PROSEGUR NRO: '||cRemito_in||'</B>'||
                                      '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR EN LA VALIDACION DE LA INFORMACION.'||'<B>');
         ROLLBACK;
    WHEN OTHERS THEN
         DBMS_OUTPUT.PUT_LINE('ERROR AL GENERAR ARCHIVO DE INTERFACE RMT - ' || SQLERRM);
         INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                      'ERROR AL GENERAR INTERFACE REMITO PROSEGUR: ',
                                      'ALERTA',
                                      'ERROR AL GENERAR LA INTERFACE REMITO PROSEGUR NRO: '||cRemito_in||'</B>'||
                                      '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR AL GENERAR ARCHIVO DE INTERFACE DE RMT - ' || SQLERRM ||'<B>');

         ROLLBACK;
  END;

  PROCEDURE INT_EJECT_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cIndEnviaAdjunto in char default 'N')
  AS
    v_nCantDias INTEGER;

    v_cSecIntCe INT_CE_REMITO.SEC_INT_CE%TYPE;

    --remitos a generar el interfaz.
    CURSOR CurRemitosGenerarInt IS
    SELECT CR.COD_REMITO--, CR.COD_GRUPO_CIA, CR.COD_LOCAL
    FROM   CE_REMITO CR
    WHERE  CR.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CR.COD_LOCAL = cCodLocal_in
    AND    CR.FEC_PROCESO_INT_CE IS NULL
-- 2010-09-22 JOLIVA: SE IMPLEMENTA VB REMITO PARA QUE SE PROCESEN SOLO LOS APROBADOS POR CONTABILIDAD
    AND    CR.IND_VB ='S'
    AND    CR.FECH_VB IS NOT NULL;

    --remitos a generar el archivo interfaz
    CURSOR CurRemitoGenerarArchivo IS
    SELECT CR.COD_REMITO--, CR.COD_GRUPO_CIA, CR.COD_LOCAL
    FROM   CE_REMITO CR
    WHERE  CR.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CR.COD_LOCAL = cCodLocal_in
    AND    CR.FEC_PROCESO_INT_CE IS NOT NULL
    AND    CR.FEC_PROCESO_ARCHIVO IS NULL;
    /*
    --
    CURSOR CurDias(p_Remito CHAR) IS
    SELECT DISTINCT DR.FEC_DIA_VTA--, DR.COD_GRUPO_CIA, DR.COD_LOCAL
    FROM   CE_DIA_REMITO DR
    WHERE  DR.COD_GRUPO_CIA  = cCodGrupoCia_in
    AND    DR.COD_LOCAL      = cCodLocal_in
    AND    DR.COD_REMITO     = p_Remito;

    CURSOR CurSobres(p_diaRemito CHAR) IS
    SELECT DR.COD_GRUPO_CIA COD_GRUPO_CIA,
           DR.COD_LOCAL COD_LOCAL,
           TO_CHAR(DR.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           C.COD_CUADRATURA TIPO_OPERACION,--C_C_DEPOSITO_VENTA
           C.COD_CLASE_DOCUMENTO CLASE_DOC,--CLASE_DOC_DA
           TO_CHAR(DR.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(DR.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           'RT'|| '-' || DR.COD_LOCAL || '-' || TO_CHAR(DR.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           'RT'|| '-' || DR.COD_LOCAL || '-' || TO_CHAR(DR.FEC_DIA_VTA,'DDMMYYYY') TEX_CAB,
           DECODE(FPE.TIP_MONEDA,'01','PEN','USD') MONEDA,
           TO_CHAR(FPE.MON_ENTREGA,'999999990.00') IMPORTE,
           C.DESC_CLAVE_CUENTA_1 CLA_CUE_1,--DESC_CLAVE_CUENTA_1
           C.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           S.COD_LOCAL||'-'||trim(S.COD_SOBRE) ASIG_1,
           'RT'|| '-' ||S.COD_LOCAL || '-'|| TRIM(S.COD_SOBRE)||'-'||
           NVL( (SELECT US.COD_TRAB_RRHH FROM PBL_USU_LOCAL US WHERE US.COD_GRUPO_CIA = DR.COD_GRUPO_CIA AND US.COD_LOCAL = DR.COD_LOCAL AND US.LOGIN_USU = S.USU_CREA_SOBRE),' ') TEX_DET_1,
           C.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           C.Desc_Cuenta_2  CUE_2,--CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           'RT'|| '-' || DR.COD_LOCAL || '-' || TO_CHAR(DR.FEC_DIA_VTA,'DDMMYYYY') TEX_DET_2

    FROM  ce_forma_pago_entrega fpe,
          ce_dia_remito DR,
          ce_sobre s,
          CE_CUADRATURA C
    WHERE fpe.cod_grupo_cia = s.cod_grupo_cia
    AND   fpe.cod_local = s.cod_local
    AND   fpe.sec_mov_caja = s.sec_mov_caja
    AND   fpe.sec_forma_pago_entrega = s.sec_forma_pago_entrega
    AND   DR.fec_dia_vta = s.fec_dia_vta
    AND   DR.cod_grupo_cia = s.cod_grupo_cia
    AND   DR.cod_local = s.cod_local
    AND   fpe.est_forma_pago_ent = 'A'
    AND  C.COD_GRUPO_CIA  = S.COD_GRUPO_CIA
    AND  C.COD_CUADRATURA = '031'
    AND  DR.COD_GRUPO_CIA = cCodGrupoCia_in
    AND  DR.COD_LOCAL   = cCodLocal_in
    AND  DR.fec_dia_vta = p_diaRemito;
    */
CURSOR CurSobres_du(p_codRemito CHAR,nCambiarFecha_in char) IS
    SELECT DR.COD_GRUPO_CIA COD_GRUPO_CIA,
           DR.COD_LOCAL COD_LOCAL,
           TO_CHAR(decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM')),'DD/MM/YYYY') FECHA,
           C.COD_CUADRATURA TIPO_OPERACION,--C_C_DEPOSITO_VENTA
           C.COD_CLASE_DOCUMENTO CLASE_DOC,--CLASE_DOC_DA
           TO_CHAR(decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM')),'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM')),'DD/MM/YYYY') FECHA_CON,
           'RT'|| '-' || DR.COD_LOCAL || '-' || TO_CHAR(decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM')),'DDMMYYYY') REFERENCIA,
           'RT'|| '-' || DR.COD_LOCAL || '-' || TO_CHAR(decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM')),'DDMMYYYY') TEX_CAB,
           DECODE(FPE.TIP_MONEDA,/*TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP*/'01','PEN','USD') MONEDA,
           TO_CHAR(FPE.MON_ENTREGA,'999999990.00') IMPORTE,
           C.DESC_CLAVE_CUENTA_1 CLA_CUE_1,--DESC_CLAVE_CUENTA_1
           C.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           S.COD_LOCAL||'-'||trim(S.COD_SOBRE) ASIG_1,
           'RT'|| '-' ||S.COD_LOCAL || '-'|| TRIM(S.COD_SOBRE)||'-'||
           NVL( (SELECT US.COD_TRAB_RRHH FROM PBL_USU_LOCAL US WHERE US.COD_GRUPO_CIA = DR.COD_GRUPO_CIA AND US.COD_LOCAL = DR.COD_LOCAL AND US.LOGIN_USU = S.USU_CREA_SOBRE),' ') TEX_DET_1,
           C.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           C.Desc_Cuenta_2  CUE_2,--CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           'RT'|| '-' || DR.COD_LOCAL || '-' || TO_CHAR(decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM')),'DDMMYYYY') TEX_DET_2,
           decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM'))  FEC_DIA_VTA_2

    FROM  ce_forma_pago_entrega fpe,
          ce_dia_remito DR,
          ce_sobre s,
          CE_CUADRATURA C
    WHERE fpe.cod_grupo_cia = s.cod_grupo_cia
    AND   fpe.cod_local = s.cod_local
    AND   fpe.sec_mov_caja = s.sec_mov_caja
    AND   fpe.sec_forma_pago_entrega = s.sec_forma_pago_entrega
    AND   DR.fec_dia_vta = s.fec_dia_vta
    AND   DR.cod_grupo_cia = s.cod_grupo_cia
    AND   DR.cod_local = s.cod_local
    AND   fpe.est_forma_pago_ent = 'A'
    AND  C.COD_GRUPO_CIA  = S.COD_GRUPO_CIA
    AND  C.COD_CUADRATURA = '031'
    AND  DR.COD_GRUPO_CIA = cCodGrupoCia_in
    AND  DR.COD_LOCAL   = cCodLocal_in
    AND  s.cod_remito   = p_codRemito
    union
    SELECT DR.COD_GRUPO_CIA COD_GRUPO_CIA,
           DR.COD_LOCAL COD_LOCAL,
           TO_CHAR(decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM')),'DD/MM/YYYY') FECHA,
           C.COD_CUADRATURA TIPO_OPERACION,--C_C_DEPOSITO_VENTA
           C.COD_CLASE_DOCUMENTO CLASE_DOC,--CLASE_DOC_DA
           TO_CHAR(decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM')),'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM')),'DD/MM/YYYY') FECHA_CON,
           'RT'|| '-' || DR.COD_LOCAL || '-' || TO_CHAR(decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM')),'DDMMYYYY') REFERENCIA,
           'RT'|| '-' || DR.COD_LOCAL || '-' || TO_CHAR(decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM')),'DDMMYYYY') TEX_CAB,
           DECODE(s.TIP_MONEDA,/*TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP*/'01','PEN','USD') MONEDA,
           TO_CHAR(s.MON_ENTREGA,'999999990.00') IMPORTE,
           C.DESC_CLAVE_CUENTA_1 CLA_CUE_1,--DESC_CLAVE_CUENTA_1
           C.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           S.COD_LOCAL||'-'||trim(S.COD_SOBRE) ASIG_1,
           'RT'|| '-' ||S.COD_LOCAL || '-'|| TRIM(S.COD_SOBRE)||'-'||
           NVL( (SELECT US.COD_TRAB_RRHH FROM PBL_USU_LOCAL US WHERE US.COD_GRUPO_CIA = DR.COD_GRUPO_CIA AND US.COD_LOCAL = DR.COD_LOCAL AND US.LOGIN_USU = S.USU_CREA_SOBRE),' ') TEX_DET_1,
           C.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           C.Desc_Cuenta_2  CUE_2,--CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           'RT'|| '-' || DR.COD_LOCAL || '-' || TO_CHAR(decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM')),'DDMMYYYY') TEX_DET_2,
           decode(nCambiarFecha_in,'N',DR.FEC_DIA_VTA,trunc(sysdate,'MM'))  FEC_DIA_VTA_2
    FROM  ce_dia_remito DR,
          ce_sobre_tmp s,
          (select * from CE_CUADRATURA u where  u.cod_cuadratura = '031') C
    WHERE DR.fec_dia_vta = s.fec_dia_vta
    AND   DR.cod_grupo_cia = s.cod_grupo_cia
    AND   DR.cod_local = s.cod_local
    AND  C.COD_GRUPO_CIA  = S.COD_GRUPO_CIA
    AND  C.COD_CUADRATURA = '031'
    AND  DR.COD_GRUPO_CIA = cCodGrupoCia_in
    AND  DR.COD_LOCAL   = cCodLocal_in
    AND  s.cod_remito   = p_codRemito
    and  not exists
                   (
                   select 1
                   from   ce_sobre e
                   where  e.cod_grupo_cia = s.cod_grupo_cia
                   and    e.cod_local = s.cod_local
                   and    e.cod_sobre = s.cod_sobre
                   );

  nCambiar_Fecha char(1);

  BEGIN
       --recorriendo por remito
       FOR remito_rec IN CurRemitosGenerarInt
	     LOOP
           BEGIN
                v_nCantDias:=0;
                /*
                FOR dia_rec in CurDias(remito_rec.Cod_Remito)
                LOOP

                    BEGIN
                         v_nCantDias:= v_nCantDias +1;
                    */
--                    OPEN CurSobres(dia_rec.fec_dia_vta);
                    --FOR sobre_rec in CurSobres(dia_rec.fec_dia_vta)

                    select decode(count(1),0,'N','S')
                    into   nCambiar_Fecha
                    from   borra_remitos_error br
                    where  br.cod_remito = remito_rec.Cod_Remito;

                    FOR sobre_rec in CurSobres_du(remito_rec.Cod_Remito,nCambiar_Fecha)
                    LOOP

                       v_nCantDias:= v_nCantDias +1;

                        DBMS_OUTPUT.put_line('SOBRE:'||sobre_rec.referencia||'-'||sobre_rec.moneda||'-'||sobre_rec.importe||'-'||sobre_rec.cue_2);
                        -- v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, dia_rec.fec_dia_vta);
                        DBMS_OUTPUT.put_line('sobre_rec.fec_dia_vta:'||sobre_rec.FEC_DIA_VTA_2);
                        v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, sobre_rec.FEC_DIA_VTA_2);
                        DBMS_OUTPUT.put_line('sobre_rec.FECHA:'||sobre_rec.FECHA);
                        DBMS_OUTPUT.put_line('sobre_rec.TIPO_OPERACION:'||sobre_rec.TIPO_OPERACION);
                        DBMS_OUTPUT.put_line('v_cSecIntCe:'||v_cSecIntCe);
                         INT_GRABA_REMITO(sobre_rec.COD_GRUPO_CIA,
                                                sobre_rec.COD_LOCAL,
                                                remito_rec.cod_remito,
                                                sobre_rec.FECHA,
                                                sobre_rec.TIPO_OPERACION,
                                                v_cSecIntCe,
                                                sobre_rec.CLASE_DOC,
                                                sobre_rec.FECHA_DOC,
                                                sobre_rec.FECHA_CON,
                                                sobre_rec.REFERENCIA,
                                                sobre_rec.TEX_CAB,
                                                sobre_rec.MONEDA,
                                                sobre_rec.CLA_CUE_1,
                                                sobre_rec.CUE_1,
                                                sobre_rec.IMPUESTO,
                                                sobre_rec.IMPORTE,
                                                sobre_rec.ASIG_1,
                                                sobre_rec.TEX_DET_1,
                                                sobre_rec.CLA_CUE_2,
                                                sobre_rec.CUE_2,
                                                sobre_rec.CEN_COSTO,
                                                sobre_rec.IND_IMP,
                                                sobre_rec.TEX_DET_2||'-'||remito_rec.cod_remito,
                                                C_C_USU_CREA_INT_CE,
                                                sobre_rec.CME);
                    END LOOP;
                    /*
                    EXCEPTION
                        WHEN OTHERS THEN
                             DBMS_OUTPUT.put_line('entro a exception'||SQLERRM);
                    END;

                END LOOP;
                */
               --ACTUALIZA FECHA DE PROCESO DE INFORMACION
               CE_ACTUALIZA_FECHA_PROCESO(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          remito_rec.cod_remito,
                                          SYSDATE);

                  COMMIT;
               IF v_nCantDias > 0 THEN
                  INT_GENERA_ARCHIVO(cCodGrupoCia_in,cCodLocal_in,remito_rec.cod_remito,cIndEnviaAdjunto);
               END IF;
           EXCEPTION
              WHEN OTHERS THEN
                   ROLLBACK;
                   DBMS_OUTPUT.PUT_LINE('ERROR AL GENERAR DATA EN TABLA DE INTERFACE - ' || SQLERRM);
                   INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                                'ERROR AL GENERAR INTERFACE CAJA ELECTRONICA: ',
                                                'ALERTA',
                                    --            'ERROR AL GENERAR LA INTERFACE DE CAJA ELECTRONICA PARA LA FECHA: '||fechaCierreDia_rec.FEC_CIERRE_DIA_VTA ||'</B>'||
                                    '-',
                                                '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR AL GENERAR DATA EN TABLA DE INTERFACE- ' || SQLERRM ||'<B>');
           END;
	     END LOOP;

      -- Se separo el proceso de generacion de archivo dependiendo de las fechas de gneraciion de proceso.
/*       FOR fechaCierreDiaArchivo_rec IN fechaCierreDiaArchivo
	     LOOP
           INT_GENERA_ARCHIVO(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(fechaCierreDiaArchivo_rec.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY'));
       END LOOP;*/

    --GENERA ARCHIVO
    --INT_GET_RESUMEN_DIA(cCodGrupoCia_in, cCodLocal_in,vFecProceso_in);
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('ERROR GENERAL AL GENERAR DATA EN TABLA DE INTERFACE - ' || SQLERRM);
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                   'ERROR AL GENERAR INTERFACE CAJA ELECTRONICA: ',
                                   'ALERTA',
                                   'ERROR AL GENERAR LA INTERFACE DE CAJA ELECTRONICA'||'</B>'||
                                   '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR GENERAL AL GENERAR DATA EN TABLA DE INTERFACE - ' || SQLERRM||'<B>');
      ROLLBACK;
  END;

  /****************************************************************************/


  PROCEDURE INT_GRABA_REMITO(cCodGrupoCia_in    IN CHAR,
                                   cCodLocal_in       IN CHAR,
                                   vCodRemito_in      IN CHAR,
                                   vFecOperacion_in   IN CHAR,
                                   cCodCuadratura_in  IN CHAR,
                                   cSecIntCe_in       IN CHAR,
                                   vClaseDoc_in       IN CHAR,
                                   cFecDocumento_in   IN CHAR,
                                   cFecContable_in    IN CHAR,
                                   vDescReferencia_in IN CHAR,
                                   cDescTextCab_in    IN CHAR,
                                   cTipMoneda_in      IN CHAR,
                                   vClaveCue1_in      IN CHAR,
                                   vCuenta1_in        IN CHAR,
                                   cMarcaImp_in       IN CHAR,
                                   cValImporte_in     IN CHAR,
                                   vDescAsig1_in      IN CHAR,
                                   cDescTextDet1_in   IN CHAR,
                                   cClaveCue2_in      IN CHAR,
                                   vCuenta2_in        IN CHAR,
                                   vCentroCosto_in    IN CHAR,
                                   cIndImp_in         IN CHAR,
                                   cDescTextDet2_in   IN CHAR,
                                   cUsuCreaIntCe_in   IN CHAR,
                                   CME_in             IN CHAR)
    IS
  v_Desc_Texto_Cabecera Int_Caja_Electronica.Desc_Texto_Cab%TYPE ;
  v_Desc_Texto_Detalle1 Int_Caja_Electronica.Desc_Texto_Detalle_1%TYPE ;
  v_Desc_Texto_Detalle2 Int_Caja_Electronica.Desc_Texto_Detalle_2%TYPE ;

  BEGIN

  v_Desc_Texto_Cabecera := REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(cDescTextCab_in,'�','N'),'�','n'),'�','a'),'�','e'),'�','i'),'�','o'),'�','u'),'�','A'),'�','E'),'�','I'),'�','O'),'�','U'),'�','U'),'�','u');
  v_Desc_Texto_Detalle1 := REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(cDescTextDet1_in,'�','N'),'�','n'),'�','a'),'�','e'),'�','i'),'�','o'),'�','u'),'�','A'),'�','E'),'�','I'),'�','O'),'�','U'),'�','U'),'�','u');
  v_Desc_Texto_Detalle2 := REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(cDescTextDet2_in,'�','N'),'�','n'),'�','a'),'�','e'),'�','i'),'�','o'),'�','u'),'�','A'),'�','E'),'�','I'),'�','O'),'�','U'),'�','U'),'�','u');

  /*dbms_output.put_line('cCodGrupoCia_in:'||cCodGrupoCia_in);
  dbms_output.put_line('cCodLocal_in:'||cCodLocal_in);
  dbms_output.put_line('vCodRemito_in:'||vCodRemito_in);
  dbms_output.put_line('vFecOperacion_in:'||vFecOperacion_in);
  dbms_output.put_line('cCodCuadratura_in:'||cCodCuadratura_in);
  dbms_output.put_line('cSecIntCe_in:'||cSecIntCe_in);
  dbms_output.put_line('vClaseDoc_in:'||vClaseDoc_in);
  dbms_output.put_line('cFecDocumento_in:'||cFecDocumento_in);
  dbms_output.put_line('cFecContable_in:'||cFecContable_in);

  dbms_output.put_line('vDescReferencia_in:'||vDescReferencia_in);
  dbms_output.put_line('v_Desc_Texto_Cabecera:'||v_Desc_Texto_Cabecera);
  dbms_output.put_line('cTipMoneda_in:'||cTipMoneda_in);
  dbms_output.put_line('vClaveCue1_in:'||vClaveCue1_in);
  dbms_output.put_line('vCuenta1_in:'||vCuenta1_in);
  dbms_output.put_line('cMarcaImp_in:'||cMarcaImp_in);
  dbms_output.put_line('cValImporte_in:'||cValImporte_in);
  dbms_output.put_line('vDescAsig1_in:'||vDescAsig1_in);
  dbms_output.put_line('v_Desc_Texto_Detalle1:'||v_Desc_Texto_Detalle1);
  dbms_output.put_line('cClaveCue2_in:'||cClaveCue2_in);
  dbms_output.put_line('vCentroCosto_in:'||vCentroCosto_in);
  dbms_output.put_line('cIndImp_in:'||cIndImp_in);
  dbms_output.put_line('v_Desc_Texto_Detalle2:'||v_Desc_Texto_Detalle2);
  dbms_output.put_line('cUsuCreaIntCe_in:'||cUsuCreaIntCe_in);
  dbms_output.put_line('CME_in:'||CME_in);*/

      INSERT INTO INT_CE_REMITO(COD_GRUPO_CIA,
                                       COD_LOCAL,
                                       COD_REMITO,
                                       FEC_OPERACION,
                                       COD_CUADRATURA,
                                       SEC_INT_CE,
                                       CLASE_DOC,
                                       FEC_DOCUMENTO,
                                       FEC_CONTABLE,
                                       DESC_REFERENCIA,
                                       DESC_TEXTO_CAB,
                                       TIP_MONEDA,
                                       DESC_CLAVE_CUENTA_1,
                                       DESC_CUENTA_1,
                                       MARCA_IMPUESTO,
                                       VAL_IMPORTE,
                                       DESC_ASIGNACION_1,
                                       DESC_TEXTO_DETALLE_1,
                                       DESC_CLAVE_CUENTA_2,
                                       DESC_CUENTA_2,
                                       DESC_CENTRO_COSTO,
                                       IND_IMPUESTO,
                                       DESC_TEXTO_DETALLE_2,
                                       USU_CREA_INT_CE,
                                       CME)
                                VALUES(cCodGrupoCia_in,
                                       cCodLocal_in,
                                       vCodRemito_in,
                                       vFecOperacion_in,
                                       cCodCuadratura_in,
                                       cSecIntCe_in,
                                       vClaseDoc_in,
                                       cFecDocumento_in,
                                       cFecContable_in,
                                       vDescReferencia_in,
                                       v_Desc_Texto_Cabecera,
                                       cTipMoneda_in,
                                       vClaveCue1_in,
                                       vCuenta1_in,
                                       cMarcaImp_in,
                                       cValImporte_in,
                                       vDescAsig1_in,
                                       v_Desc_Texto_Detalle1,
                                       cClaveCue2_in,
                                       vCuenta2_in,
                                       vCentroCosto_in,
                                       cIndImp_in,
                                       v_Desc_Texto_Detalle2,
                                       cUsuCreaIntCe_in,
                                       CME_in);

  END;

  /****************************************************************************/

  FUNCTION CE_GET_SECUENCIAL_INT(cCodGrupoCia_in      IN CHAR,
                                 cCodLocal_in         IN CHAR,
                                 vFecCierreDia_in     IN CHAR)
    RETURN CHAR
  IS
    v_cSecIntCe INT_CE_REMITO.SEC_INT_CE%TYPE;
  BEGIN
       SELECT FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(COUNT(ICE.SEC_INT_CE) + 1, 3, '0', POS_INICIO)
       INTO   v_cSecIntCe
       FROM   INT_CE_REMITO ICE
       WHERE  ICE.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    ICE.COD_LOCAL = cCodLocal_in
       AND    ICE.FEC_OPERACION = vFecCierreDia_in;
    RETURN v_cSecIntCe;
  END;

  /****************************************************************************/

  FUNCTION CE_VALIDA_DATA_INTERFACE(cCodRemito       IN CHAR,
                                    cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR)
    RETURN CHAR
  IS
    v_cIndResult    CHAR(1);
    v_nCanFechaArchivo NUMBER;
  BEGIN
       v_cIndResult := INDICADOR_CORRECTO;--indica que no hay error

       --VALIDANDO QUE NO EXISTA ARCHIVO CREADO PARA LA FECHA DADA
       SELECT COUNT(*)
       INTO   v_nCanFechaArchivo
       FROM   CE_REMITO CR
       WHERE  CR.COD_REMITO    = cCodRemito
       AND    CR.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CR.COD_LOCAL = cCodLocal_in
       AND    CR.FEC_PROCESO_ARCHIVO IS NOT NULL;

       IF v_nCanFechaArchivo > 0 THEN
          DBMS_OUTPUT.PUT_LINE('NO PAS� LA VALIDACION DE LA FECHA DE ARCHIVO');
          v_cIndResult := INDICADOR_ERROR;
          RETURN v_cIndResult;
       END IF;


    RETURN v_cIndResult;
  END;

  /****************************************************************************/

  PROCEDURE CE_ACTUALIZA_FECHA_PROCESO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cCodRemito_in    IN CHAR,
                                       vFecProceso_in   IN DATE)
  IS
  BEGIN
       UPDATE CE_REMITO CR
       SET    CR.FEC_PROCESO_INT_CE = vFecProceso_in
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    CR.COD_REMITO = cCodRemito_in;
  END;

  /****************************************************************************/

  PROCEDURE CE_ACTUALIZA_FECHA_ARCHIVO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cCodRemito_in    IN CHAR,
                                       vFecArchivo_in   IN DATE)
  IS
  BEGIN
       UPDATE CE_REMITO CR
       SET    CR.FEC_PROCESO_ARCHIVO = vFecArchivo_in
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL     = cCodLocal_in
       AND    CR.COD_REMITO = cCodRemito_in;
  END;

  /****************************************************************************/

  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        vEnviarOper_in   IN CHAR DEFAULT 'N')
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_INTER_CE;
    CCReceiverAddress VARCHAR2(120) := NULL;
    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN
     --SE VALIDARA A DONDE ENVIARA SI EXISTA UN ERROR EN LOS MONTOS
     IF vEnviarOper_in = INDICADOR_SI THEN
        ReceiverAddress:= '';
        ReceiverAddress := FARMA_EMAIL.GET_RECEIVER_ADDRESS_VL_INT_CE;
     END IF;

    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,--'VIAJERO EXITOSO: '||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             CCReceiverAddress,
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;

  /****************************************************************************/
PROCEDURE ENVIA_CORREO_ATTACH3(cSendorAddress_in in char,
                                cReceiverAddress_in in char,
                                cSubject_in in varchar2,
                                ctitulo_in in varchar2,
                                cmensaje_in in varchar2,
                                pDirectorio IN VARCHAR2,
                                pfilename IN VARCHAR2,
                                cCCReceiverAddress_in in char,
                                cip_servidor in char)
  AS
    conn UTL_SMTP.CONNECTION;
    cmensaje VARCHAR2(1000);
    mesg_body varchar2(32767);
    crlf VARCHAR2( 2 ):= CHR( 13 ) || CHR( 10 );
  BEGIN



        conn := ORACLE_MAIL.begin_mail(
        v_smtp_host => cip_servidor,
        sender     => cSendorAddress_in,
        recipients => cReceiverAddress_in,
        CCrecipients => cCCReceiverAddress_in,
        subject    => cSubject_in,
        mime_type  => ORACLE_MAIL.MULTIPART_MIME_TYPE);

        mesg_body:= '<html>
                <head>
                <title>MIFARMA te entiende de cuida By SISTEMAS</title>
                </head>
                <body bgcolor="#FFFFFF" link="#000080">
                <table cellspacing="0" cellpadding="0" width="100%">
                <tr align="LEFT" valign="BASELINE">
                <td width="100%" valign="middle"><h1><font color="#00008B"><b>'||ctitulo_in||'</b></font></h1>
                </td>
             </table>
              <ul>
               '||cmensaje_in||'
               <l><b> </b> </l>
               <l><b></p></p> Mensaje originado automaticamente desde la Base de Datos,no contestar </b> </l>
                  </ul>
                 </body>
                 </html>';

        --ctitulo_in||crlf||cmensaje_in;

        ORACLE_MAIL.attach_text(
        conn      => conn,
        data      =>   mesg_body ,
        mime_type => 'text/html');



     /* IF i <> 0 THEN
        cmensaje := '<H2>SE HA REALIZADO CAMBIOS EN EL LOCAL.<BR><BR>�VERIFIQUE LOS CAMBIOS ADJUNTOS!</H2>
                      <b><p> Mensaje originado automaticamente desde la Base de Datos,no contestar </b> </p>';
        ORACLE_MAIL.attach_text(
          conn      => conn,
          data      => cmensaje,
          mime_type => 'text/html');
*/
        attach_report3(
          conn      => conn,
          mime_type => 'text/html',
          inline    => FALSE,
          directory => pDirectorio,
          filename  => pfilename,
          last      => TRUE);
    /*  ELSE
        cmensaje := '<H2>NO SE HA REALIZADO CAMBIOS EN EL LOCAL</H2>
                    <b><p> Mensaje originado automaticamente desde la Base de Datos,no contestar </b> </p>';*/

      --END IF;

      ORACLE_MAIL.end_mail( conn => conn );
    /*exception
     when others then
      null;*/
  END;

  PROCEDURE attach_report3(conn         IN OUT NOCOPY utl_smtp.connection,
			mime_type    IN VARCHAR2 DEFAULT 'text/plain',
			inline       IN BOOLEAN  DEFAULT TRUE,
      directory IN VARCHAR2 DEFAULT NULL,
			filename     IN VARCHAR2 DEFAULT NULL,
		        last         IN BOOLEAN  DEFAULT FALSE)
  IS
    --v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
    vNewLine VARCHAR2(2000):='edgar';

    vInHandle utl_file.file_type;
  BEGIN
    ORACLE_MAIL.begin_attachment(conn, mime_type, inline, filename);
    DBMS_OUTPUT.PUT_LINE('ARCHIVO:'||filename);
    DBMS_OUTPUT.PUT_LINE('directory:'||directory);
    vInHandle := utl_file.fopen(directory, TRIM(filename), 'R');
    LOOP
      BEGIN
        /*IF vNewLine IS NULL THEN
            EXIT;
          END IF;*/
--          DBMS_OUTPUT.PUT_LINE('INICIO,'||vNewLine);
        utl_file.get_line(vInHandle, vNewLine);
--         DBMS_OUTPUT.PUT_LINE('2,'||vNewLine);
        ORACLE_MAIL.write_text(conn, vNewLine || chr(10));
--        DBMS_OUTPUT.PUT_LINE('conn,'||vNewLine);
      EXCEPTION
        WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('error');
          EXIT;
      END;
    END LOOP;
    utl_file.fclose(vInHandle);
    DBMS_OUTPUT.PUT_LINE('close file');
    ORACLE_MAIL.end_attachment(conn, last);

  END;
/* *************************************************************** */
PROCEDURE RECEP_P_ENVIA_CORREO_ADJUNTO(vAsunto_in        IN CHAR,
                                       vTitulo_in        IN CHAR,
                                       vMensaje_in       IN CHAR,
                                       vNombre_Archivo_in IN VARCHAR2 DEFAULT null
                                      )
    AS

      ReceiverAddress VARCHAR2(3000) ;
      CCReceiverAddress VARCHAR2(120) := NULL;
      mesg_body VARCHAR2(32767);

      v_gNombreDiretorioAlert VARCHAR2(50) := 'DIR_INTERFACES';

    BEGIN
      DBMS_OUTPUT.put_line('vNombre_Archivo_in INICIO '|| vNombre_Archivo_in);

      mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

      ReceiverAddress := 'dubilluz;pmiguel;operador';

      DBMS_OUTPUT.put_line('vNombre_Archivo_in '|| vNombre_Archivo_in);

      ENVIA_CORREO_ATTACH3(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                           ReceiverAddress,
                           vAsunto_in,
                           vTitulo_in,
                           mesg_body,
                           v_gNombreDiretorioAlert,
                           vNombre_Archivo_in,
                           CCReceiverAddress,
                           FARMA_EMAIL.GET_EMAIL_SERVER);

     DBMS_OUTPUT.put_line('Envio archivo');

 END;
end PTOVENTA_INT_REMITO;

/
