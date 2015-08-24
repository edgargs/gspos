CREATE OR REPLACE package          PKG_GENE_RESU_VTAS is

  -- Author  : TCANCHES
  -- Created : 16/03/2015 01:18:05 p.m.
  -- Purpose : Generar resumenes de ventas en locales

  -- Public type declarations
  /*****************************************************************************************************************************/
/*--------------------------------------------------------------------------------------------------------------------------
GOAL : Cargar las Tablas de Resumen de Ventas en Local
Hist : 09-MAR-15  TCT  Create
*---------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_CARGA_VENTAS_LOCAL(ac_Anio_Mes IN CHAR DEFAULT NULL);
/*-------------------------------------------------------------------------------------------------------------------------
GOAL : Cargar Tabla Auxiliar para Validar Resumen de Ventas
Hist : 1) 12-MAR-15   TCT   Create
--------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_LOAD_AUX_VENTAS(ac_Fec_Ini IN CHAR, ac_Fec_Fin IN CHAR);
/*******************************************************************************************************************************/

end PKG_GENE_RESU_VTAS;
/


CREATE OR REPLACE package body          PKG_GENE_RESU_VTAS is
/*****************************************************************************************************************************/
/*--------------------------------------------------------------------------------------------------------------------------
GOAL : Cargar las Tablas de Resumen de Ventas en Local
Hist : 09-MAR-15  TCT  Create
*---------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_CARGA_VENTAS_LOCAL(ac_Anio_Mes IN CHAR DEFAULT NULL)
AS
 vc_Fec_Ini CHAR(10):= NULL;
 vc_Fec_Fin VARCHAR2(50):= NULL;
 vc_Cod_Local CHAR(3);
BEGIN
  --- 10.- Determinar el Rango de Fechas a Procesar
  IF length(ac_Anio_Mes)!= 6 AND ac_Anio_Mes IS NOT NULL THEN
   Raise_Application_Error(-20010,'Error,El Par�metro A�o Mes Ingresado es Incorrecto, debe usar Formato YYYYMM !!!!');
  END IF;


  IF ac_Anio_Mes IS  NULL THEN

   SELECT to_char(add_months(trunc(SYSDATE,'MM'),-1),'dd/MM/yyyy'),
          to_char(last_day(add_months(trunc(SYSDATE,'MM'),-1)),'dd/MM/yyyy')
   INTO vc_Fec_Ini, vc_Fec_Fin
   FROM dual;

  END IF;

  IF vc_Fec_Ini IS NULL AND ac_Anio_Mes IS NOT NULL THEN
    SELECT to_char(to_date(ac_Anio_Mes,'YYYYMM'),'dd/MM/yyyy'),
           to_char(last_day(to_date(ac_Anio_Mes,'YYYYMM')),'dd/MM/yyyy')
    INTO vc_Fec_Ini, vc_Fec_Fin
    FROM dual;
  END IF;

 /* vc_Fec_Ini := vc_Fec_Ini ||' 00:00:00';
  vc_Fec_Fin := vc_Fec_Fin ||' 23:59:59';*/

  --- 20.- Get Codigo Local
  BEGIN
    SELECT DISTINCT i.cod_local
    INTO vc_Cod_Local
    FROM vta_impr_local i;
  EXCEPTION
   WHEN OTHERS THEN
    Raise_Application_Error(-2010,'Error, No se puede Determinar el C�digo del Local !!!');
  END;
  ---
  --dbms_output.put_line('Fec Ini, vc_Fec_Fin,vc_Cod_Local => '||vc_Fec_Ini||' , '||vc_Fec_Fin||' , '||vc_Cod_Local);
  --RETURN;

  --- 30.- Carga Temporales de Ventas
  DELETE FROM VTA_RES_VTA_VEND_LOCAL
	WHERE COD_GRUPO_CIA = '001'
    AND COD_LOCAL     = vc_Cod_Local--'&1'
	  AND FEC_DIA_VENTA BETWEEN to_date(vc_Fec_Ini || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                          AND to_date(vc_Fec_Fin || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');

  PTOVENTA_REPORTE.SP_LOAD_VENTAS_VEN_LOC(cCodGrupoCia_in => '001', cCodLocal_in => vc_Cod_Local,
  cFecha_Ini_in => vc_Fec_Ini, cFecha_Fin_in =>vc_Fec_Fin);

  --- 50.- Carga Tablas Final Vta Vend Local
  INSERT INTO VTA_RES_VTA_VEND_LOCAL(
  COD_GRUPO_CIA,                    COD_LOCAL,                     SEC_USU_LOCAL,
  FEC_DIA_VENTA,                    MON_TOT_VTA,                   MON_TOT_BONO,
  MON_TOT_GRUPO_A,                  MON_TOT_PP,                    CANT_PED,
  CANT_PED_ANUL,                    MON_TOT_FARMA,                 MON_TOT_NO_FARMA,
  MON_TOT_NO_A,                     MON_TOT_SERV,                  MON_TOT_OTROS,
  MON_TOT_G,                        MON_TOT_GG,                    MON_TOT_S_IGV,
  MON_TOT_C_IGV,                    FEC_CREA,                      TOT_COM,
  MON_TOT_GP,                       PORC_AT_CLIENTE,               TOT_COM_FIN
  )
	SELECT
	COD_GRUPO_CIA,                    COD_LOCAL,                     SEC_USU_LOCAL,
  FEC_DIA_VENTA,                    MON_TOT_VTA,                   MON_TOT_BONO,
  MON_TOT_GRUPO_A,                  MON_TOT_PP,                    CANT_PED,
  CANT_PED_ANUL,                    MON_TOT_FARMA,                 MON_TOT_NO_FARMA,
  MON_TOT_NO_A,                     MON_TOT_SERV,                  MON_TOT_OTROS,
  MON_TOT_G,                        MON_TOT_GG,                    MON_TOT_S_IGV,
  MON_TOT_C_IGV,                    FECH_LOAD,                     TOT_COM,
  MON_TOT_GP,                       PORC_AT_CLIENTE,               TOT_COM_FIN
	FROM TMP_VTA_VEND_LOCAL;


  --- 60.- Carga Venta Mezon
  DELETE FROM PTOVENTA.VTA_RES_VTA_VEND_LOCAL_MEZON R
  WHERE  R.COD_GRUPO_CIA = '001'
  AND    R.COD_LOCAL     = vc_Cod_Local--'&1'
  AND    R.FEC_DIA_VENTA BETWEEN to_date(vc_Fec_Ini || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                             AND to_date(vc_Fec_Fin || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');

  PTOVENTA_REPORTE.ACT_RES_VENTAS_VENDEDOR_TIPO('001', vc_Cod_Local, vc_Fec_Ini, vc_Fec_Fin, '01');

  INSERT INTO VTA_RES_VTA_VEND_LOCAL_MEZON(
  COD_GRUPO_CIA,                           COD_LOCAL,                      SEC_USU_LOCAL,
  FEC_DIA_VENTA,                           MON_TOT_VTA,                    MON_TOT_BONO,
  MON_TOT_GRUPO_A,                         MON_TOT_PP,                     CANT_PED,
  CANT_PED_ANUL,                           MON_TOT_FARMA,                  MON_TOT_NO_FARMA,
  MON_TOT_NO_A,                            MON_TOT_SERV,                   MON_TOT_OTROS,
  MON_TOT_G,                               MON_TOT_GG,                     MON_TOT_S_IGV,
  MON_TOT_C_IGV,                           FEC_CREA,                       TOT_COM,
  MON_TOT_GP
  )
  SELECT
  COD_GRUPO_CIA,                           COD_LOCAL,                      SEC_USU_LOCAL,
  FEC_DIA_VENTA,                           MON_TOT_VTA,                    MON_TOT_BONO,
  MON_TOT_GRUPO_A,                         MON_TOT_PP,                     CANT_PED,
  CANT_PED_ANUL,                           MON_TOT_FARMA,                  MON_TOT_NO_FARMA,
  MON_TOT_NO_A,                            MON_TOT_SERV,                   MON_TOT_OTROS,
  MON_TOT_G,                               MON_TOT_GG,                     MON_TOT_S_IGV,
  MON_TOT_C_IGV,                           FEC_CREA,                       TOT_COM,
  MON_TOT_GP
  FROM TMP_RES_VTA_VEND_LOCAL_MEZON R
  WHERE  R.COD_GRUPO_CIA = '001'
  AND    R.COD_LOCAL     = vc_Cod_Local--'&1'
  AND    R.FEC_DIA_VENTA BETWEEN to_date(vc_Fec_Ini || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                             AND to_date(vc_Fec_Fin || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');

  --- 70.- Carga Auxiliar con datos sumarizados para validacion
  PKG_GENE_RESU_VTAS.sp_load_aux_ventas(ac_fec_ini => vc_Fec_Ini,
                                      ac_fec_fin => vc_Fec_Fin);


  COMMIT;
  dbms_output.put_line('Ok, al cargar tablas de resumen de ventas Loc = '||vc_Cod_Local);
 EXCEPTION
  WHEN OTHERS THEN
   ROLLBACK;
   dbms_output.put_line('Error, al cargar tablas de resumen de ventas Loc = '||vc_Cod_Local||' => '||Sqlerrm);

END;
/*----------------------------------------------------------------------------------------------------------------
GOAL : Cargar Tabla Auxiliar para Validar Resumen de Ventas
Hist : 1) 12-MAR-15   TCT   Create
------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_LOAD_AUX_VENTAS(ac_Fec_Ini IN CHAR, ac_Fec_Fin IN CHAR)
IS
BEGIN
  --- 10.- Elimmina datos antiguos
  DELETE FROM  AUX_VTA_MES_LOCAL_VALID;

  --- 20.- Carga nuevos datos
  INSERT INTO AUX_VTA_MES_LOCAL_VALID (
        cod_local,
        mes,
        t_s_igv,
        t_s_igv_meson,
        t_s_igv_g_meson,
        fec_crea
        )
  SELECT
        C.COD_LOCAL,
        TO_CHAR(C.FEC_PED_VTA,'YYYYMM')             MES,
        SUM(D.VAL_PREC_TOTAL / (1 + D.VAL_IGV/100)) T_S_IGV,
        SUM(
            CASE
            WHEN C.TIP_PED_VTA = '01' THEN (D.VAL_PREC_TOTAL / (1 + D.VAL_IGV/100))
            END
            )                                       T_S_IGV_MESON,
        SUM(
             CASE
             WHEN C.TIP_PED_VTA = '01'
              AND TRIM(D.IND_ZAN) IS NOT NULL THEN (D.VAL_PREC_TOTAL / (1 + D.VAL_IGV/100))
             END
            )                                        T_S_IGV_G_MESON,
        SYSDATE                                      FEC_CREA
  FROM VTA_PEDIDO_VTA_CAB C,
       VTA_PEDIDO_VTA_DET D
  WHERE C.COD_GRUPO_CIA = '001'
    AND C.COD_LOCAL     = (
                           SELECT DISTINCT i.cod_local
                           FROM vta_impr_local i
                          )

    AND C.FEC_PED_VTA BETWEEN TO_DATE(ac_Fec_Ini||' 00:00:00','dd/MM/yyyy hh24:mi:ss')
                          AND to_date(ac_Fec_Fin||' 23:59:59','dd/MM/yyyy hh24:mi:ss')
    AND C.EST_PED_VTA   = 'C'
	  AND C.TIP_PED_VTA   != '03'
    AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
    AND D.COD_LOCAL     = C.COD_LOCAL
    AND D.NUM_PED_VTA   = C.NUM_PED_VTA
  GROUP BY
        C.COD_LOCAL,
        TO_CHAR(C.FEC_PED_VTA,'YYYYMM');
 COMMIT;

END;

/*******************************************************************************************************************************/
end PKG_GENE_RESU_VTAS;
/
