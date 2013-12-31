--------------------------------------------------------
--  DDL for Package Body PTOVENTA_VIAJERO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_VIAJERO" AS

  PROCEDURE VIAJ_PROCESAR_VIAJERO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cIndDelIvery_in IN CHAR DEFAULT NULL,vIdUsu_in IN VARCHAR2 DEFAULT 'VIAJERO')
  AS
    CURSOR curLocal IS
    SELECT DISTINCT COD_LOCAL
    FROM T_ADM_PROD_LOCAL;
    v_rCurLocal  curLocal%ROWTYPE;
    err_msg VARCHAR2(250);

    CURSOR curDeliv IS
    SELECT DISTINCT T.COD_LOCAL
    FROM T_ADM_PROD_LOCAL T, PBL_LOCAL L
    WHERE L.TIP_LOCAL = 'D'
    AND T.COD_GRUPO_CIA = L.COD_GRUPO_CIA
    AND T.COD_LOCAL = L.COD_LOCAL;
    v_rCurLocal  curDeliv%ROWTYPE;
  BEGIN

    v_gCantRegistrosActualizados := 0;
    v_gCantProdLocal := 0;
    g_vIdUsu := vIdUsu_in;
    g_dFechaInicio := SYSDATE;

    VIAJ_ACTUALIZA_LOCAL(cCodLocal_in);
    DBMS_OUTPUT.PUT_LINE('=> '||cCodLocal_in);
    VIAJ_ACTUALIZA_GRUPO_REP_LOCAL(cCodLocal_in);
    VIAJ_ACTUALIZA_PROV(cCodLocal_in);
    VIAJ_ACTUALIZA_LAB(cCodLocal_in);
    VIAJ_ACTUALIZA_GRUPO_QS(cCodLocal_in);
    VIAJ_ACTUALIZA_ACC_TERAP(cCodLocal_in);
    VIAJ_ACTUALIZA_PRINC_ACT(cCodLocal_in);

    --MODIFICADO 24/11/2006 ERIOS: YA NO SE REALIZA COPIA.
    --REALIZA BACKUP LGT_PROD_LOCAL
    --VIAJ_BCK_PROD_LOCAL;

    IF cIndDelIvery_in IS NULL OR cIndDelIvery_in = 'N' THEN
      VIAJ_ACTUALIZA_PROD(cCodGrupoCia_in,cCodLocal_in);
      VIAJ_ACTUALIZA_PROD_LOCAL(cCodGrupoCia_in,cCodLocal_in);
      --AGREGADO 08/09/2006 ERIOS
      --VIAJ_ACTUALIZA_PROD_LOCAL(cCodGrupoCia_in,'009','S');
    ELSE
      VIAJ_ACTUALIZA_PROD(cCodGrupoCia_in,cCodLocal_in,'S');--ASUME QUE ES DELIVERY
      FOR v_rCurLocal IN curLocal--LOCALES DE LA TABLA TEMPORAL
      LOOP
        VIAJ_ACTUALIZA_PROD_LOCAL(cCodGrupoCia_in,v_rCurLocal.COD_LOCAL,'S');
      END LOOP;
    END IF;

    VIAJ_ACTUALIZA_COD_BARRA(cCodLocal_in);
    VIAJ_ACTUALIZA_ACC_TERAP_PROD(cCodLocal_in);
    VIAJ_ACTUALIZA_PRINC_ACT_PROD(cCodLocal_in);
    VIAJ_ACTUALIZA_TIP_CAMBIO(cCodLocal_in);
    VIAJ_ACTUALIZA_CARGO(cCodLocal_in);
    VIAJ_ACTUALIZA_MAE_TRAB(cCodLocal_in);
    VIAJ_ACTUALIZA_CAMBIO_PROD(cCodGrupoCia_in,cCodLocal_in);

    --15/05/2007 ERIOS Tablas de Convenios
    VIAJ_ACTUALIZA_CAMPOS_FORM(cCodLocal_in);
    VIAJ_ACTUALIZA_CON_LISTA(cCodLocal_in);
    VIAJ_ACTUALIZA_MAE_EMPRESA(cCodLocal_in);
    VIAJ_ACTUALIZA_MAE_CONV(cCodLocal_in);
    VIAJ_ACTUALIZA_CAMPOS_CONV(cCodLocal_in);
    VIAJ_ACTUALIZA_LISTA_CONV(cCodLocal_in);
    VIAJ_ACTUALIZA_LAB_LISTA(cCodLocal_in);
    VIAJ_ACTUALIZA_PROD_LISTA(cCodLocal_in);
    VIAJ_ACTUALIZA_LOCAL_CONV(cCodLocal_in);
    VIAJ_ACTUALIZA_MAE_CLIENTE(cCodLocal_in);
    VIAJ_ACTUALIZA_CLI_CONV(cCodLocal_in);

    --10/09/2007 PAMEGHINO TABLAS DE CAJA ELECTRONICA
    VIAJ_ACTUALIZA_MAE_PROV_SAP(cCodLocal_in)  ;
    VIAJ_ACTUALIZA_MAE_SERVICIO(cCodLocal_in)  ;
    VIAJ_ACTUALIZA_MAE_PROVEEDORES(cCodLocal_in)  ;
    VIAJ_ACTUALIZA_REL_PROV_SERV(cCodLocal_in)  ;

    --12/12/2008 JCORTEZ TABLA TIPO CAMBIO SAP
    VIAJ_ACTUALIZA_TIP_CAMBIO_SAP(cCodLocal_in);

    --ENVIA_MAIL ALERTA
    IF (v_gCantRegistrosActualizados <> 0) OR
       (v_gCantProdLocal <> 0) THEN
      VIAJ_ENVIA_CORREO_ALERTA(cCodGrupoCia_in,cCodLocal_in);
    ELSE
      DBMS_OUTPUT.PUT_LINE('NO ACTUALIZA NADA');
    END IF;
    --ENVIA MAIL CAMBIOS

	-- 2011-02-25: JOLIVA	- SIEMPRE SE ENVIARÁ CAMBIOS DE PRECIOS
	VIAJ_ENVIA_CORREO_CAMBIOS(cCodGrupoCia_in,cCodLocal_in);
/*
    IF v_gCantProdLocal <> 0 THEN
      IF cIndDelIvery_in IS NULL OR cIndDelIvery_in = 'N' THEN
        VIAJ_ENVIA_CORREO_CAMBIOS(cCodGrupoCia_in,cCodLocal_in);
      ELSIF cCodLocal_in = '009' THEN
        VIAJ_ENVIA_CORREO_CAMBIOS(cCodGrupoCia_in,cCodLocal_in);
      ELSE
        FOR v_rCurDeliv IN curDeliv--LOCALES DE LA TABLA TEMPORAL
        LOOP
          VIAJ_ENVIA_CORREO_CAMBIOS(cCodGrupoCia_in,v_rCurDeliv.COD_LOCAL);
        END LOOP;
      END IF;
    ELSE
      DBMS_OUTPUT.PUT_LINE('NO ACTUALIZO PRECIOS');
    END IF;
*/

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('TERMINO EL VIAJERO. LOCAL:'||cCodLocal_in);
    --09/06/2008 ERIOS Se ejecuta el proceso de actualizacion ZAN_LOCAL
    --12/06/2008 ERIOS Resulta que ya no quiere que se calcule en el local,
    --                 sino que se actualiza en el maestro de productos
    /*IF (v_gCantProd + v_gCantProdLocal) > 0 AND
       (cIndDelIvery_in IS NULL OR cIndDelIvery_in = 'N') THEN
      PTOVENTA_TAREAS.UPDATE_PROD_ZAN_LOCAL(cCodGrupoCia_in,
                                        cCodLocal_in);
    END IF;*/
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO EN EL PROCESO.'||err_msg);
  END;

  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_PROV(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_LGT_PROV
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_PROV.COD_PROV%TYPE;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        INSERT INTO LGT_PROV(COD_PROV,
                            NOM_PROV,
                            EST_PROV,
                            DIRECC_PROV,
                            TELEF_PROV	,
                            RUC_PROV	,
                            USU_CREA_PROV)
        VALUES(v_rCur.COD_PROV,
                v_rCur.NOM_PROV,
                v_rCur.EST_PROV,
                v_rCur.DIRECC_PROV,
                v_rCur.TELEF_PROV	,
                v_rCur.RUC_PROV	,
                g_vIdUsu);
        -- ACTUALIZACION DE FECHA PROCESO
        UPDATE T_LGT_PROV SET FEC_PROCESO = SYSDATE
        WHERE COD_PROV = v_rCur.COD_PROV;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          SELECT COD_PROV INTO v_cCod
          FROM LGT_PROV
          WHERE COD_PROV = v_rCur.COD_PROV FOR UPDATE;

          UPDATE LGT_PROV
          SET NOM_PROV = v_rCur.NOM_PROV,
              EST_PROV = v_rCur.EST_PROV,
              DIRECC_PROV = v_rCur.DIRECC_PROV,
              TELEF_PROV = v_rCur.TELEF_PROV	,
              RUC_PROV = v_rCur.RUC_PROV	,
              FEC_MOD_PROV = SYSDATE,
              USU_MOD_PROV = g_vIdUsu
          WHERE COD_PROV = v_rCur.COD_PROV;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_PROV SET FEC_PROCESO = SYSDATE
          WHERE COD_PROV = v_rCur.COD_PROV;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROV,v_rCur.NOM_PROV||', proveedor no actualizado',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_PROV||' '||v_rCur.NOM_PROV||', proveedor no actualizado');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar proveedores. Registros:'||i);
    --AGREGADO 16/06/2006 ERIOS
    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROV,v_rCur.NOM_PROV||' ERROR NO CONTROLADO AL ACTUALIZAR LOS PROVEEDORES',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR LOS PROVEEDORES.');
  END;

  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_LAB(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_LGT_LAB
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_LAB.COD_LAB%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        INSERT INTO LGT_LAB(COD_LAB,
                            NOM_LAB,
                            DIRECC_LAB,
                            TELEF_LAB,
                            IND_LAB_PROPIO,
                            TIP_LAB	,
                            EST_LAB	,
                            USU_CREA_LAB,
                            --IND_LAB_PROPIO_GER,
                            --VAL_FACTOR_CRECIM,
                            --TIP_ABC_MONTO,
                            --TIP_ABC_UNID,
                            --IND_LAB_CONG,
                            IND_LAB_FARMA,
                            EKGRP)
        VALUES(v_rCur.COD_LAB,
                v_rCur.NOM_LAB,
                v_rCur.DIRECC_LAB,
                v_rCur.TELEF_LAB,
                v_rCur.IND_LAB_PROPIO,
                v_rCur.TIP_LAB,
                v_rCur.EST_LAB,
                g_vIdUsu,
                --v_rCur.IND_LAB_PROPIO_GER,
                --v_rCur.VAL_FACTOR_CRECIM,
                --v_rCur.TIP_ABC_MONTO,
                --v_rCur.TIP_ABC_UNID
                'N',
                '171'
                );

        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_LAB SET FEC_PROCESO = SYSDATE
          WHERE COD_LAB = v_rCur.COD_LAB;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          SELECT COD_LAB INTO v_cCod
          FROM LGT_LAB
          WHERE COD_LAB = v_rCur.COD_LAB FOR UPDATE;

          UPDATE LGT_LAB
          SET NOM_LAB = v_rCur.NOM_LAB,
              DIRECC_LAB = v_rCur.DIRECC_LAB,
              TELEF_LAB = v_rCur.TELEF_LAB,
              IND_LAB_PROPIO = v_rCur.IND_LAB_PROPIO,
              TIP_LAB = v_rCur.TIP_LAB,
              EST_LAB = v_rCur.EST_LAB,
              USU_MOD_LAB = g_vIdUsu,
              FEC_MOD_LAB = SYSDATE--,
              --IND_LAB_PROPIO_GER = v_rCur.IND_LAB_PROPIO_GER,
              --VAL_FACTOR_CRECIM = v_rCur.VAL_FACTOR_CRECIM,
              --TIP_ABC_MONTO = v_rCur.TIP_ABC_MONTO,
              --TIP_ABC_UNID = v_rCur.TIP_ABC_UNID
          WHERE COD_LAB = v_rCur.COD_LAB;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_LAB SET FEC_PROCESO = SYSDATE
          WHERE COD_LAB = v_rCur.COD_LAB;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_LAB,v_rCur.NOM_LAB||', laboratorio no actualizado',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_LAB||' '||v_rCur.NOM_LAB||', laboratorio no actualizado');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar laboratorios. Registros:'||i);
    --AGREGADO 16/06/2006 ERIOS
    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_LAB,v_rCur.NOM_LAB||' ERROR NO CONTROLADO AL ACTUALIZAR LOS LABORATORIOS',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR LOS LABORATORIOS.');
  END;

  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIndDelIvery_in IN CHAR DEFAULT NULL)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_LGT_PROD
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_PROD.COD_PROD%TYPE;

    --10/07/2007  ERIOS  Maestro de locales
    CURSOR curLocal IS
    SELECT COD_LOCAL
    FROM PBL_LOCAL
    ORDER BY 1;
    v_rCurLocal  curLocal%ROWTYPE;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --ERIOS 10/01/2007: SE HA DESHABILITADO EL CAMPO CANT_UNID_PRESENT TEMPORALEMTEN. CORREGIR!
        INSERT INTO LGT_PROD(COD_GRUPO_CIA,
                              COD_PROD,
                              DESC_PROD,
                              --CANT_UNID_PRESENT,
                              IND_PROD_FRACCIONABLE,
                              VAL_MAX_FRAC,
                              CANT_DOSIS,
                              CANT_DIAS_DOSIS,
                              IND_DESCONT,
                              VAL_PREC_PROV_VIG,
                              VAL_PREC_PROM	,
                              VAL_PREC_VTA_VIG	,
                              VAL_BONO_VIG	,
                              EST_PROD	,
                              DESC_UNID_PRESENT	,
                              COD_UNID_PRESENT	,
                              COD_UNID_MIN_FRAC	,
                              TIP_VTA	,
                              COD_LAB	,
                              COD_INSERTO,
                              COD_IGV	,
                              TIP_MONEDA,
                              USU_CREA_PROD	,
                              --FEC_MOD_PROD	,
                              --USU_MOD_PROD,
                              COD_GRUPO_REP	,
                              IND_PROD_FARMA	,
                              COD_GRUPO_QS	,
                              COD_FACT_PREC	,
                              IND_CONTROL_LOTE	,
                              IND_CONTROL_FEC_VENC,
                              DESC_PRODUCTO_SAP,
                              IND_PRECIO_CONTROL,
                              IND_PROD_REFRIG,
                              IND_TIPO_PROD, --33
                              --MAKTX2,
                              --MTART,
                              --MTPOS_MARA,
                              --EXTWG,
                              --HERKL,
                              --DESC_GEN,
                              --VAL_ANCHO,
                              --COD_UNID_ANCHO,
                              --VAL_ALTO,
                              --COD_UNID_ALTO,
                              --VAL_LARGO,
                              --COD_UNID_LARGO,
                              --VAL_PESO,
                              --COD_UNID_PESO,
                              TIP_ORIG_MAT,
                              --26/02/2008 JCORTEZ MODIFICACION
                              IND_PROD_PROPIO,
                              IND_PROD_PROPIO_GER,
                              --20/05/2008 JCORTEZ MODIFICACION
                              COD_CAT_IV,
                              COD_IMS_IV,
                              CLAS_PROD,
                              --04/06/2008 JCORTEZ MODIFICACION
                              VAL_FRAC_VTA_SUG,
                              COD_UNID_VTA_SUG,
                              DESC_UNID_VTA_SUG,
                              --03/11/2008 JCORTEZ MODIFICACION
                              IND_ZAN,
                              COD_GRUPO_REP_EDMUNDO,
                              VAL_FACT,
                              IND_CONSIGNACION,
                              VAL_ZAN_PROV,
                              --24/11/2008 ASOLIS MODIFICACION
                              --IND_PROD_CRONICO)
                              -- 2009-11-18 JOLIVA
                              IND_COD_BARRA,
                              MENSAJE_PROD,
                              IND_SOL_ID_USU,
                              PORC_ZAN,
                              -- 2010-03-08 JOLIVA: Campo para Equivalencia de productos similares
                              CANT_EQ_GRU_SIM
                              )
        VALUES(v_rCur.COD_GRUPO_CIA,
                v_rCur.COD_PROD,
                v_rCur.DESC_PROD,
                --v_rCur.CANT_UNID_PRESENT,
                v_rCur.IND_PROD_FRACCIONABLE,
                v_rCur.VAL_MAX_FRAC,
                v_rCur.CANT_DOSIS,
                v_rCur.CANT_DIAS_DOSIS,
                v_rCur.IND_DESCONT,
                v_rCur.VAL_PREC_PROV_VIG,
                v_rCur.VAL_PREC_PROM	,
                v_rCur.VAL_PREC_VTA_VIG	,
                v_rCur.VAL_BONO_VIG	,
                v_rCur.EST_PROD	,
                v_rCur.DESC_UNID_PRESENT	,
                v_rCur.COD_UNID_PRESENT	,
                v_rCur.COD_UNID_MIN_FRAC	,
                v_rCur.TIP_VTA	,
                v_rCur.COD_LAB	,
                v_rCur.COD_INSERTO,
                v_rCur.COD_IGV	,
                v_rCur.TIP_MONEDA,
                g_vIdUsu,
                --NVL2(v_rCur.FEC_MOD_PROD,SYSDATE,NULL),
                --v_rCur.USU_MOD_PROD,
                v_rCur.COD_GRUPO_REP	,
                v_rCur.IND_PROD_FARMA	,
                v_rCur.COD_GRUPO_QS	,
                v_rCur.COD_FACT_PREC	,
                v_rCur.IND_CONTROL_LOTE	,
                v_rCur.IND_CONTROL_FEC_VENC,
                v_rCur.DESC_PRODUCTO_SAP,
                v_rCur.IND_PRECIO_CONTROL,
                v_rCur.IND_PROD_REFRIG,
                v_rCur.IND_TIPO_PROD,
                --v_rCur.MAKTX2,
                --v_rCur.MTART,
                --v_rCur.MTPOS_MARA,
                --v_rCur.EXTWG,
                --v_rCur.HERKL,
                --v_rCur.DESC_GEN,
                --v_rCur.VAL_ANCHO,
                --v_rCur.COD_UNID_ANCHO,
                --v_rCur.VAL_ALTO,
                --v_rCur.COD_UNID_ALTO,
                --v_rCur.VAL_LARGO,
                --v_rCur.COD_UNID_LARGO,
                --v_rCur.VAL_PESO,
                --v_rCur.COD_UNID_PESO,
                '1',--v_rCur.TIP_ORIG_MAT
                --26/02/2008 JCORTEZ MODIFICACION
                v_rCur.IND_PROD_PROPIO,
                v_rCur.IND_PROD_PROPIO_GER,
                --20/05/2008 JCORTEZ MODIFICACION
                v_rCur.Cod_Cat_Iv,
                v_rCur.Cod_Ims_Iv,
                v_rCur.Clas_Prod,
                v_rCur.Val_Frac_Vta_Sug,
                v_rCur.Cod_Unid_Vta_Sug,
                v_rCur.Desc_Unid_Vta_Sug,
                --03/11/2008 JCORTEZ MODIFICACION
                v_rCur.IND_ZAN,
                v_rCur.COD_GRUPO_REP_EDMUNDO,
                v_rCur.VAL_FACT,
                v_rCur.IND_CONSIGNACION,
                v_rCur.VAL_ZAN_PROV,
                --24/11/2008 ASOLIS MODIFICACION
                --v_rCur.IND_PROD_CRONICO);
                -- 2009-11-18 JOLIVA
                v_rCur.IND_COD_BARRA,
                v_rCur.MENSAJE_PROD,
                v_rCur.IND_SOL_ID_USU,
                v_rCur.PORC_ZAN,
                -- 2010-03-08 JOLIVA:
                v_rCur.CANT_EQ_GRU_SIM
                );

        IF cIndDelIvery_in IS NULL OR cIndDelIvery_in = 'N' THEN
          AGREGA_PROD_LOCAL(v_rCur.COD_GRUPO_CIA,cCodLocal_in,v_rCur.COD_PROD,v_rCur.VAL_PREC_VTA_VIG,v_rCur.DESC_UNID_PRESENT);
        ELSE
          FOR v_rCurLocal IN curLocal--LOCALES
          LOOP
            AGREGA_PROD_LOCAL(v_rCur.COD_GRUPO_CIA,v_rCurLocal.COD_LOCAL,v_rCur.COD_PROD,v_rCur.VAL_PREC_VTA_VIG,v_rCur.DESC_UNID_PRESENT);
          END LOOP;
        END IF;

        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_PROD SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_PROD = v_rCur.COD_PROD;

        --ERIOS 12/06/2008 Se actualiza el precio de venta sugerido
        UPDATE LGT_PROD_LOCAL L
        SET (VAL_PREC_VTA_SUG,val_prec_lista_sug ) = (SELECT
                                  --(P.VAL_PREC_VTA_VIG*((100-L.PORC_DCTO_1)/100))/P.VAL_FRAC_VTA_SUG,
                                   (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)/P.VAL_FRAC_VTA_SUG, --JCORTEZ 23.12.09
                                   (P.VAL_PREC_VTA_VIG)/P.VAL_FRAC_VTA_SUG
                            FROM LGT_PROD P
                            WHERE P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
                                  AND P.COD_PROD = L.COD_PROD),
              L.FEC_MOD_PROD_LOCAL = SYSDATE,
              L.USU_MOD_PROD_LOCAL = g_vIdUsu
        WHERE L.COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND L.COD_LOCAL = cCodLocal_in
              AND L.COD_PROD = v_rCur.COD_PROD
              ;

        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          SELECT COD_PROD INTO v_cCod
          FROM LGT_PROD
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_PROD = v_rCur.COD_PROD FOR UPDATE;

          UPDATE LGT_PROD
          SET DESC_PROD = v_rCur.DESC_PROD,
              --CANT_UNID_PRESENT = v_rCur.CANT_UNID_PRESENT,
              IND_PROD_FRACCIONABLE = v_rCur.IND_PROD_FRACCIONABLE,
              VAL_MAX_FRAC = v_rCur.VAL_MAX_FRAC,
              CANT_DOSIS = v_rCur.CANT_DOSIS,
              CANT_DIAS_DOSIS = v_rCur.CANT_DIAS_DOSIS,
              IND_DESCONT = v_rCur.IND_DESCONT,
              VAL_PREC_PROV_VIG = v_rCur.VAL_PREC_PROV_VIG,
              VAL_PREC_PROM = v_rCur.VAL_PREC_PROM	,
              VAL_PREC_VTA_VIG = v_rCur.VAL_PREC_VTA_VIG	,
              VAL_BONO_VIG = v_rCur.VAL_BONO_VIG	,
              EST_PROD = v_rCur.EST_PROD	,
              DESC_UNID_PRESENT = v_rCur.DESC_UNID_PRESENT	,
              COD_UNID_PRESENT = v_rCur.COD_UNID_PRESENT	,
              COD_UNID_MIN_FRAC = v_rCur.COD_UNID_MIN_FRAC	,
              TIP_VTA = v_rCur.TIP_VTA	,
              COD_LAB = v_rCur.COD_LAB	,
              COD_INSERTO = v_rCur.COD_INSERTO,
              COD_IGV = v_rCur.COD_IGV	,
              TIP_MONEDA = v_rCur.TIP_MONEDA,
              FEC_MOD_PROD = SYSDATE,
              USU_MOD_PROD = g_vIdUsu ,
              COD_GRUPO_REP = v_rCur.COD_GRUPO_REP,
              IND_PROD_FARMA = v_rCur.IND_PROD_FARMA,
              COD_GRUPO_QS = v_rCur.COD_GRUPO_QS,
              COD_FACT_PREC = v_rCur.COD_FACT_PREC,
              IND_CONTROL_LOTE = v_rCur.IND_CONTROL_LOTE,
              IND_CONTROL_FEC_VENC = v_rCur.IND_CONTROL_FEC_VENC,
              DESC_PRODUCTO_SAP = v_rCur.DESC_PRODUCTO_SAP,
              IND_PRECIO_CONTROL = v_rCur.IND_PRECIO_CONTROL,
              IND_PROD_REFRIG = v_rCur.IND_PROD_REFRIG,
              IND_TIPO_PROD = v_rCur.IND_TIPO_PROD,
              --26/02/2008 JCORTEZ MODIFICACION
              IND_PROD_PROPIO =  v_rCur.IND_PROD_PROPIO,
              IND_PROD_PROPIO_GER =  v_rCur.IND_PROD_PROPIO_GER,
              --20/05/2008 JCORTEZ MODIFICACION
              COD_CAT_IV=v_rCur.Cod_Cat_Iv,
              COD_IMS_IV=v_rCur.Cod_Ims_Iv,
              CLAS_PROD=v_rCur.Clas_Prod,
              VAL_FRAC_VTA_SUG=v_rCur.Val_Frac_Vta_Sug,
              COD_UNID_VTA_SUG=v_rCur.Cod_Unid_Vta_Sug,
              DESC_UNID_VTA_SUG=v_rCur.Desc_Unid_Vta_Sug,
              --03/11/2008 JCORTEZ MODIFICACION
              IND_ZAN=v_rCur.IND_ZAN,
              COD_GRUPO_REP_EDMUNDO= v_rCur.COD_GRUPO_REP_EDMUNDO,
              VAL_FACT=v_rCur.VAL_FACT,
              IND_CONSIGNACION=v_rCur.IND_CONSIGNACION,
              VAL_ZAN_PROV=v_rCur.VAL_ZAN_PROV,
              --24/11/2008 ASOLIS MODIFICACION
              --IND_PROD_CRONICO=v_rCur.IND_PROD_CRONICO
              -- 2009-11-18 JOLIVA
              IND_COD_BARRA = v_rCur.IND_COD_BARRA,
              MENSAJE_PROD = v_rCur.MENSAJE_PROD,
              IND_SOL_ID_USU = v_rCur.IND_SOL_ID_USU,
              PORC_ZAN = v_rCur.PORC_ZAN,
              -- 2010-03-08 JOLIVA: Campo nuevo para productos similares
              CANT_EQ_GRU_SIM = v_rCur.CANT_EQ_GRU_SIM
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_PROD = v_rCur.COD_PROD;

          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_PROD SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_PROD = v_rCur.COD_PROD;

          --ERIOS 12/06/2008 Se actualiza el precio de venta sugerido
          UPDATE LGT_PROD_LOCAL L
          SET (VAL_PREC_VTA_SUG,val_prec_lista_sug ) = (SELECT
                                     --(P.VAL_PREC_VTA_VIG*((100-L.PORC_DCTO_1)/100))/P.VAL_FRAC_VTA_SUG,
                                     (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)/P.VAL_FRAC_VTA_SUG, --JCORTEZ 23.12.09
                                     (P.VAL_PREC_VTA_VIG)/P.VAL_FRAC_VTA_SUG
                              FROM LGT_PROD P
                              WHERE P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
                                    AND P.COD_PROD = L.COD_PROD),
                L.FEC_MOD_PROD_LOCAL = SYSDATE,
                L.USU_MOD_PROD_LOCAL = g_vIdUsu
          WHERE L.COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND L.COD_LOCAL = cCodLocal_in
                AND L.COD_PROD = v_rCur.COD_PROD
                ;

          COMMIT;

          --AGREGADO 16/06/2006 ERIOS
          -- MODIFICADO POR JOLIVA 22/06/06 (PRODUCTOS SE INACTIVAN AUNQUE  TENGAN STOCK)
--          ACTIVAR_PRODUCTO(v_rCur.COD_GRUPO_CIA,v_rCur.COD_PROD,v_rCur.EST_PROD);

        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,v_rCur.DESC_PROD||', producto no actualizado',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error Producto/'||v_rCur.COD_PROD||' '||v_rCur.DESC_PROD||', producto no actualizado');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar productos. Registros:'||i);
    --AGREGADO 16/06/2006 ERIOS
    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
    v_gCantProd := v_gCantProd + i; --ERIOS 09/06/2008 Cuenta prods actualizados.
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,v_rCur.DESC_PROD||' ERROR NO CONTROLADO AL ACTUALIZAR LOS PRODUCTOS',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR LOS PRODUCTOS.');
  END;
  /******************************************************************************/
 PROCEDURE VIAJ_ACTUALIZA_PROD_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIndDelIvery_in IN CHAR DEFAULT NULL)
  AS
    CURSOR cur IS
      SELECT L.*
      FROM T_ADM_PROD_LOCAL L
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND L.PREC_VTA IS NOT NULL -- JCORTEZ 30.04.09
            AND L.FEC_PROCESO IS NULL
      ORDER BY FEC_MOD_PROD_LOCAL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_PROD.COD_PROD%TYPE;
    v_nStk LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nFrac LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    v_nComprometido LGT_PROD_LOCAL.STK_FISICO%TYPE := 0;
    v_cIndCong LGT_PROD_LOCAL.IND_PROD_CONG%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
    v_nComprometido:= 0;

    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN

        SELECT COD_PROD,STK_FISICO,VAL_FRAC_LOCAL,IND_PROD_CONG
          INTO v_cCod, v_nStk, v_nFrac,v_cIndCong
        FROM LGT_PROD_LOCAL
        WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND COD_LOCAL = v_rCur.COD_LOCAL
              AND COD_PROD = v_rCur.COD_PROD FOR UPDATE;

        IF cIndDelIvery_in = 'S' THEN

        --Se modifica precios
        UPDATE LGT_PROD_LOCAL
         SET PORC_DCTO_1	= v_rCur.PORC_DCTO_1,
              PORC_DCTO_2	= v_rCur.PORC_DCTO_2,
              PORC_DCTO_3	= v_rCur.PORC_DCTO_3,
             -- VAL_PREC_VTA = (v_rCur.VAL_PREC_VTA_VIG*((100-v_rCur.PORC_DCTO_1)/100))/v_rCur.VAL_FRAC_LOCAL,
              VAL_PREC_VTA=v_rCur.Prec_Vta/v_rCur.VAL_FRAC_LOCAL, --JCORTEZ 27.04.09 Se actualiza del precio tal y como se calculo en APPS
              VAL_PREC_LISTA = v_rCur.VAL_PREC_VTA_VIG/v_rCur.VAL_FRAC_LOCAL
        WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCur.COD_LOCAL
                AND COD_PROD = v_rCur.COD_PROD;

        COMMIT;

          UPDATE LGT_PROD_LOCAL L
          SET (VAL_PREC_VTA_SUG,val_prec_lista_sug ) = (SELECT
                                     --(P.VAL_PREC_VTA_VIG*((100-L.PORC_DCTO_1)/100))/P.VAL_FRAC_VTA_SUG,
                                     v_rCur.Prec_Vta/P.VAL_FRAC_VTA_SUG, --JCORTEZ 27.04.09
                                     (P.VAL_PREC_VTA_VIG)/P.VAL_FRAC_VTA_SUG
                              FROM LGT_PROD P
                              WHERE P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
                                    AND P.COD_PROD = L.COD_PROD),
                L.FEC_MOD_PROD_LOCAL = SYSDATE,
                L.USU_MOD_PROD_LOCAL = g_vIdUsu
          WHERE L.COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND L.COD_LOCAL = v_rCur.COD_LOCAL
                AND L.COD_PROD = v_rCur.COD_PROD;


          --Se modifica frac y demas
          UPDATE LGT_PROD_LOCAL
          SET STK_FISICO = (v_nStk*v_rCur.VAL_FRAC_LOCAL)/v_nFrac,
              UNID_VTA = DECODE(v_rCur.VAL_FRAC_LOCAL,1,' ',
              NVL(v_rCur.DESC_UNID_FRAC_LOCAL,v_rCur.DESC_UNID_PRESENT)),
              VAL_FRAC_LOCAL = v_rCur.VAL_FRAC_LOCAL,
              IND_PROD_FRACCIONADO = v_rCur.IND_PROD_FRACCIONADO,
              EST_PROD_LOC = v_rCur.EST_PROD_LOCAL,
              CANT_EXHIB = v_rCur.CANT_EXHIB,
              IND_REPONER	=  v_rCur.IND_REPONER,
              IND_PROD_HABIL_VTA = v_rCur.IND_PROD_HABIL_VTA,
              USU_MOD_PROD_LOCAL = g_vIdUsu,
              FEC_MOD_PROD_LOCAL = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCur.COD_LOCAL
                AND COD_PROD = v_rCur.COD_PROD;

          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_ADM_PROD_LOCAL SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCur.COD_LOCAL
                AND COD_PROD = v_rCur.COD_PROD;

          COMMIT;
        ELSIF v_rCur.VAL_FRAC_LOCAL = v_nFrac THEN

          --ACTUALIZAR PRECIOS
          UPDATE LGT_PROD_LOCAL
          SET PORC_DCTO_1	= v_rCur.PORC_DCTO_1,
              PORC_DCTO_2	= v_rCur.PORC_DCTO_2,
              PORC_DCTO_3	= v_rCur.PORC_DCTO_3,
--              VAL_PREC_VTA = (v_rCur.VAL_PREC_VTA_VIG*((100-v_rCur.PORC_DCTO_1)/100))/v_rCur.VAL_FRAC_LOCAL,
                VAL_PREC_VTA=v_rCur.Prec_Vta/v_rCur.VAL_FRAC_LOCAL, --JCORTEZ 27.04.09 Se actualiza del precio tal y como se calculo en APPS
              VAL_PREC_LISTA = v_rCur.VAL_PREC_VTA_VIG/v_rCur.VAL_FRAC_LOCAL
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCur.COD_LOCAL
                AND COD_PROD = v_rCur.COD_PROD;
           COMMIT;

            UPDATE LGT_PROD_LOCAL L
          SET (VAL_PREC_VTA_SUG,val_prec_lista_sug ) = (SELECT
                                     --(P.VAL_PREC_VTA_VIG*((100-L.PORC_DCTO_1)/100))/P.VAL_FRAC_VTA_SUG,
                                     v_rCur.Prec_Vta/P.VAL_FRAC_VTA_SUG, --JCORTEZ 27.04.09
                                     (P.VAL_PREC_VTA_VIG)/P.VAL_FRAC_VTA_SUG
                              FROM LGT_PROD P
                              WHERE P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
                                    AND P.COD_PROD = L.COD_PROD),
                L.FEC_MOD_PROD_LOCAL = SYSDATE,
                L.USU_MOD_PROD_LOCAL = g_vIdUsu
          WHERE L.COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND L.COD_LOCAL = v_rCur.COD_LOCAL
                AND L.COD_PROD = v_rCur.COD_PROD;

          COMMIT;


          UPDATE LGT_PROD_LOCAL
          SET EST_PROD_LOC = v_rCur.EST_PROD_LOCAL,
              UNID_VTA = DECODE(v_rCur.VAL_FRAC_LOCAL,1,' ',
              NVL(v_rCur.DESC_UNID_FRAC_LOCAL,v_rCur.DESC_UNID_PRESENT)),
              CANT_EXHIB = v_rCur.CANT_EXHIB,
              IND_REPONER	=  v_rCur.IND_REPONER,
              IND_PROD_HABIL_VTA = v_rCur.IND_PROD_HABIL_VTA,
              USU_MOD_PROD_LOCAL = g_vIdUsu,
              FEC_MOD_PROD_LOCAL = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCur.COD_LOCAL
                AND COD_PROD = v_rCur.COD_PROD;


          -- ACTUALIZACION DE FECHA PROCESO
            UPDATE T_ADM_PROD_LOCAL SET FEC_PROCESO = SYSDATE
            WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                  AND COD_LOCAL = v_rCur.COD_LOCAL
                  AND COD_PROD = v_rCur.COD_PROD;

            COMMIT;
            --ROLLBACK;
        ELSIF v_nComprometido = 0 THEN

          IF v_cIndCong = 'N' THEN

           --ACTUALIZAR PRECIOS
              UPDATE LGT_PROD_LOCAL
              SET   PORC_DCTO_1	= v_rCur.PORC_DCTO_1,
                      PORC_DCTO_2	= v_rCur.PORC_DCTO_2,
                      PORC_DCTO_3	= v_rCur.PORC_DCTO_3,
                      --VAL_PREC_VTA = (v_rCur.VAL_PREC_VTA_VIG*((100-v_rCur.PORC_DCTO_1)/100))/v_rCur.VAL_FRAC_LOCAL,
                      VAL_PREC_VTA=v_rCur.Prec_Vta/v_nFrac, --solo precio por que hasta este momento no se aplica fraccion local.
                      VAL_PREC_LISTA = v_rCur.VAL_PREC_VTA_VIG/v_nFrac
              WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                    AND COD_LOCAL = v_rCur.COD_LOCAL
                    AND COD_PROD = v_rCur.COD_PROD;

             /* DBMS_OUTPUT.put_line('CODIGO-->'||v_rCur.COD_PROD);
            DBMS_OUTPUT.put_line('SE GUARDA PRECIO PREC_VTA-->'||v_rCur.Prec_Vta);
            DBMS_OUTPUT.put_line('SE GUARDA PRECIO VAL_PREC_LISTA-->'||v_rCur.VAL_PREC_VTA_VIG);*/
            COMMIT;

            IF MOD(((v_nStk*v_rCur.VAL_FRAC_LOCAL)/v_nFrac),FLOOR((v_nStk*v_rCur.VAL_FRAC_LOCAL)/v_nFrac)) = 0  THEN

              --ACTUALIZAR PRECIOS
              UPDATE LGT_PROD_LOCAL
              SET   PORC_DCTO_1	= v_rCur.PORC_DCTO_1,
                      PORC_DCTO_2	= v_rCur.PORC_DCTO_2,
                      PORC_DCTO_3	= v_rCur.PORC_DCTO_3,
                      --VAL_PREC_VTA = (v_rCur.VAL_PREC_VTA_VIG*((100-v_rCur.PORC_DCTO_1)/100))/v_rCur.VAL_FRAC_LOCAL,
                      VAL_PREC_VTA=v_rCur.Prec_Vta/v_rCur.VAL_FRAC_LOCAL, --JCORTEZ 27.04.09 Se actualiza del precio tal y como se calculo en APPS
                      VAL_PREC_LISTA = v_rCur.VAL_PREC_VTA_VIG/v_rCur.VAL_FRAC_LOCAL
              WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                    AND COD_LOCAL = v_rCur.COD_LOCAL
                    AND COD_PROD = v_rCur.COD_PROD;

            /*DBMS_OUTPUT.put_line('SE GUARDA PRECIO PREC_VTA-->'||v_rCur.Prec_Vta/v_rCur.VAL_FRAC_LOCAL);
            DBMS_OUTPUT.put_line('SE GUARDA PRECIO VAL_PREC_LISTA-->'||v_rCur.VAL_PREC_VTA_VIG/v_rCur.VAL_FRAC_LOCAL);*/
            COMMIT;

              UPDATE LGT_PROD_LOCAL L
              SET (VAL_PREC_VTA_SUG,val_prec_lista_sug ) =
                   (SELECT --(P.VAL_PREC_VTA_VIG*((100-L.PORC_DCTO_1)/100))/P.VAL_FRAC_VTA_SUG,
                                        v_rCur.Prec_Vta/P.VAL_FRAC_VTA_SUG, --JCORTEZ 27.04.09
                                        (P.VAL_PREC_VTA_VIG)/P.VAL_FRAC_VTA_SUG
                                        FROM LGT_PROD P
                                        WHERE P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
                                        AND P.COD_PROD = L.COD_PROD),
                    L.FEC_MOD_PROD_LOCAL = SYSDATE,
                    L.USU_MOD_PROD_LOCAL = g_vIdUsu
              WHERE L.COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                    AND L.COD_LOCAL = v_rCur.COD_LOCAL
                    AND L.COD_PROD = v_rCur.COD_PROD;

              COMMIT;

              --ACTUALIZAR FRACCIONAMIENTO
              UPDATE LGT_PROD_LOCAL
              SET STK_FISICO = (v_nStk*v_rCur.VAL_FRAC_LOCAL)/v_nFrac,
                  UNID_VTA = DECODE(v_rCur.VAL_FRAC_LOCAL,1,' ',
                  NVL(v_rCur.DESC_UNID_FRAC_LOCAL,v_rCur.DESC_UNID_PRESENT)),
                  VAL_FRAC_LOCAL = v_rCur.VAL_FRAC_LOCAL,
                  IND_PROD_FRACCIONADO = v_rCur.IND_PROD_FRACCIONADO,
                  EST_PROD_LOC = v_rCur.EST_PROD_LOCAL,
                  CANT_EXHIB = v_rCur.CANT_EXHIB,
                  IND_REPONER	=  v_rCur.IND_REPONER,
                  IND_PROD_HABIL_VTA = v_rCur.IND_PROD_HABIL_VTA,
                  USU_MOD_PROD_LOCAL = g_vIdUsu,
                  FEC_MOD_PROD_LOCAL = SYSDATE
              WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                    AND COD_LOCAL = v_rCur.COD_LOCAL
                    AND COD_PROD = v_rCur.COD_PROD;

              COMMIT;
              --DBMS_OUTPUT.put_line('VAL_FRAC_LOCAL-->'||v_rCur.VAL_FRAC_LOCAL);


              IF cIndDelIvery_in IS NULL OR cIndDelIvery_in = 'N' THEN
                --KARDEX CAMBIO FRACCION
                IF v_rCur.VAL_FRAC_LOCAL <> v_nFrac THEN
                  INV_GRABAR_KARDEX_FRACCION(v_rCur.COD_GRUPO_CIA,
                                              v_rCur.COD_LOCAL,
                                              v_rCur.COD_PROD,
                                              v_nStk,
                                              (v_nStk*v_rCur.VAL_FRAC_LOCAL)/v_nFrac,
                                              v_rCur.VAL_FRAC_LOCAL,
                                              NVL(v_rCur.DESC_UNID_FRAC_LOCAL,v_rCur.DESC_UNID_PRESENT),
                                              NVL(g_vIdUsu,'SISTEMAS')
                                              );
                END IF;
              END IF;


              -- ACTUALIZACION DE FECHA PROCESO
              UPDATE T_ADM_PROD_LOCAL SET FEC_PROCESO = SYSDATE
              WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                    AND COD_LOCAL = v_rCur.COD_LOCAL
                    AND COD_PROD = v_rCur.COD_PROD;

              COMMIT;
              --ROLLBACK;

            ELSE
              ROLLBACK;
              GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,v_rCur.DESC_PROD||' no se puede fraccionar','Registro:'||i||'/STOCK ACTUAL='||v_nStk||',VAL_FRAC_LOCAL='||v_nFrac||',FRACCION_NUEVA='||v_rCur.VAL_FRAC_LOCAL);
              --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_PROD||' '||v_rCur.DESC_PROD||' no se puede fraccionar');
            END IF;
          ELSE
            ROLLBACK;
            GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,v_rCur.DESC_PROD||' el producto esta congelado','Registro:'||i||'/INDICADOR CONGELADO='||v_cIndCong);
            --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_PROD||' '||v_rCur.DESC_PROD||', el producto esta congelado');
          END IF;
        ELSE
          ROLLBACK;
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,v_rCur.DESC_PROD||' existe Stock Comprometido','Registro:'||i||'/STOCK COMPROMETIDO='||v_nComprometido);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_PROD||' '||v_rCur.DESC_PROD||' existe Stock Comprometido');
        END IF;
      EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,v_rCur.DESC_PROD||', producto-local no actualizado',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_PROD||' '||v_rCur.DESC_PROD||', producto-loal no actualizado');
          --RAISE;
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar productos local. Registros:'||i);
    --ACTUALIZADO 06/09/2006 ERIOS
    v_gCantProdLocal := v_gCantProdLocal+i;


      --JCORTEZ 20.08.09 Se borra temporal, haya o no aplicado cambios
      DELETE FROM T_ADM_PROD_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in;


  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,v_rCur.DESC_PROD||' ERROR NO CONTROLADO AL ACTUALIZAR LOS PRODUCTOS LOCAL',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR LOS PRODUCTOS LOCAL.');
      --RAISE;
  END;
  /****************************************************************************/
  PROCEDURE INV_GRABAR_KARDEX_FRACCION(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        cCodProd_in		   IN CHAR,
                                        nStkAnteriorProd_in  IN NUMBER,
                                        nStkFinalProd_in  IN NUMBER,
                                        nValFrac_in		   IN NUMBER,
                                        cDescUnidVta_in   IN CHAR,
                                        cUsuCreaKardex_in	   IN CHAR
                                        )
  IS
  v_cSecKardex LGT_KARDEX.SEC_KARDEX%TYPE;

  BEGIN
    v_cSecKardex :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_SEC_KARDEX),10,'0','I' );
    INSERT INTO LGT_KARDEX(COD_GRUPO_CIA, COD_LOCAL, SEC_KARDEX, COD_PROD, COD_MOT_KARDEX,
                                     TIP_DOC_KARDEX, NUM_TIP_DOC, STK_ANTERIOR_PROD, CANT_MOV_PROD,
                                     STK_FINAL_PROD, VAL_FRACC_PROD, DESC_UNID_VTA, USU_CREA_KARDEX,DESC_GLOSA_AJUSTE)
     VALUES (cCodGrupoCia_in,	cCodLocal_in, v_cSecKardex, cCodProd_in, g_vCodMotKardexFraccion,
                                     g_vTipDocKardexFraccion, g_vNumDocKardexFraccion, nStkAnteriorProd_in, 0,
                                     nStkFinalProd_in, nValFrac_in, cDescUnidVta_in, cUsuCreaKardex_in,'CAMBIO FRACCION');
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, COD_NUMERA_SEC_KARDEX, cUsuCreaKardex_in);
  END;

  /****************************************************************************/
  PROCEDURE AGREGA_PROD_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR,nValPrecVtaVig_in IN NUMBER,vDescUnid_in IN VARCHAR2)
  AS
  BEGIN
    INSERT INTO LGT_PROD_LOCAL(COD_GRUPO_CIA,
                                COD_LOCAL,
                                COD_PROD,
                                USU_CREA_PROD_LOCAL,
                                VAL_PREC_LISTA,
                                UNID_VTA)
    VALUES(cCodGrupoCia_in,
            cCodLocal_in,
            cCodProd_in,
            g_vIdUsu,
            nValPrecVtaVig_in,
            vDescUnid_in);

    INSERT INTO LGT_PROD_LOCAL_REP(COD_GRUPO_CIA,
                                  COD_LOCAL,
                                  COD_PROD,
                                  CANT_MIN_STK,
                                  CANT_MAX_STK,
                                  CANT_SUG,
                                  CANT_TRANSITO,
                                  NUM_DIAS    ,
                                  STK_FISICO  ,
                                  VAL_FRAC_LOCAL,
                                  CANT_EXHIB    ,
                                  USU_CREA_PROD_LOCAL_REP)
    VALUES(cCodGrupoCia_in,
            cCodLocal_in,
            cCodProd_in,
            0,0,0,0,0,0,1,0,
            g_vIdUsu);
  END;
  /****************************************************************************/
  PROCEDURE GRABA_LOG_VIAJERO(cCodLocal_in IN CHAR,cCodProd_in IN CHAR,vIdProceso_in IN VARCHAR2,vMensaje_in IN VARCHAR2)
  AS
  BEGIN
    INSERT INTO TMP_LOG_VIAJERO(COD_LOCAL,
                                COD_PROD,
                                ID_PROCESO_VIAJERO,
                                DESC_MENSAJE)
    VALUES(cCodLocal_in,cCodProd_in,vIdProceso_in,vMensaje_in);
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ENVIA_CORREO_ALERTA(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR)
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_VIAJERO;
    CCReceiverAddress VARCHAR2(120) := NULL;

    mesg_body VARCHAR2(32767);

    CURSOR curLog IS
    SELECT L.*,C.DESC_LOCAL
    FROM TMP_LOG_VIAJERO L, PBL_LOCAL C
    WHERE L.COD_LOCAL = C.COD_LOCAL
          AND FEC_ENVIO_MAIL IS NULL;

    v_rCurLog curLog%ROWTYPE;

    existeLog NUMBER(7):=0;
    v_vDescLocal VARCHAR2(120);
  BEGIN
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO v_vDescLocal
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

    --CREAR CUERPO MENSAJE;
    FOR v_rCurLog IN curLog
    LOOP
      existeLog := existeLog+1;

      mesg_body := mesg_body||'<LI> <B>' || 'LOCAL: '||v_rCurLog.COD_LOCAL	||CHR(9)||
                                       v_rCurLog.DESC_LOCAL ||CHR(9)||
                                          ' - PRODUCTO: '||v_rCurLog.COD_PROD	||CHR(9)||
                                          v_rCurLog.ID_PROCESO_VIAJERO	||CHR(9)||'</B><BR>'||
                                          '<I>ERROR: </I><BR>'||v_rCurLog.DESC_MENSAJE
                                          --||CHR(9)||v_rCurLog.FEC_CREA_LOG_VIAJERO	 ||
                                          ||'</LI>'  ;

    END LOOP;

    --ENVIA MAIL
    IF existeLog > 0 THEN

      UPDATE TMP_LOG_VIAJERO
      SET FEC_ENVIO_MAIL = SYSDATE
      WHERE FEC_ENVIO_MAIL IS NULL;

      FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                            ReceiverAddress,
                            'ERROR AL CARGAR VIAJERO: '||v_vDescLocal,
                            'ALERTA',
                            mesg_body,
                            CCReceiverAddress,
                            FARMA_EMAIL.GET_EMAIL_SERVER,
                            true);
    ELSE
      --MAIL PROCESO EXITOSO
      mesg_body := '<L><B>' || 'EL PROCESO VIAJERO, SE HA EJECUTADO CORRECTAMENTE EN EL LOCAL: '||
                            v_vDescLocal ||'</B></L>'  ;

      FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                            ReceiverAddress,
                            'VIAJERO EXITOSO: '||v_vDescLocal,
                            'EXITO',
                            mesg_body,
                            CCReceiverAddress,
                            FARMA_EMAIL.GET_EMAIL_SERVER,
                            true);
    END IF;

  END;

  /****************************************************************************/
  PROCEDURE VIAJ_BCK_PROD_LOCAL
  AS
    v_nCant NUMBER(7);
  BEGIN
    SELECT COUNT(*)
      INTO v_nCant
    FROM T_ADM_PROD_LOCAL
    WHERE FEC_PROCESO IS NOT NULL;

    /*IF v_nCant = 0 THEN
      DELETE FROM BK_LGT_PROD_LOCAL;
      --DBMS_OUTPUT.PUT_LINE('DELETE BK_LGT_PROD_LOCAL');
      INSERT INTO BK_LGT_PROD_LOCAL
      SELECT * FROM LGT_PROD_LOCAL;
      COMMIT;
    END IF;*/

  END;

  /****************************************************************************/
  PROCEDURE VIAJ_ENVIA_CORREO_CAMBIOS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  AS

      vFila_Msg_01       varchar2(2800):= '';

      C_INICIO_MSG  VARCHAR2(2000) := '<html>'||
                                      '<head>'||
                                      '</head>'||
                                      '<body>'||
                                      '<table width="100%" border="0">';

      C_FIN_MSG     VARCHAR2(2000) := '</table>'||
                                        '</body>'||
                                        '</html> ';
--CURSO CON CAMBIO EN PRECIO
--JMIRANDA 10.06.2011
    CURSOR curCambios IS
    SELECT B.COD_PROD,
           P.DESC_PROD,
           P.DESC_UNID_PRESENT,
           TO_CHAR(B.VAL_PREC_VTA_NVO,'999,999,990.000') "PRECIO_NUEVO",
           TO_CHAR(B.VAL_PREC_VTA_OLD,'999,999,990.000') "PRECIO_ANTIGUO",
           B.UNID_VTA_NVO "UNID_VTA_NUEVO",
           B.UNID_VTA_OLD "UNID_VTA_ANTIGUO"
      FROM --LGT_PROD_LOCAL_BK B,
          (SELECT COD_GRUPO_CIA, COD_LOCAL, COD_PROD, VAL_PREC_VTA_OLD, VAL_PREC_VTA_NVO, FEC_CREA,
                  UNID_VTA_NVO, UNID_VTA_OLD,
                  RANK() OVER (PARTITION BY COD_GRUPO_CIA,COD_LOCAL, COD_PROD ORDER BY FEC_CREA||ROWNUM DESC) ORDEN
             FROM LGT_PROD_LOCAL_BK WHERE FEC_CREA >= TRUNC(SYSDATE)) B,
           LGT_PROD P
     WHERE B.FEC_CREA >= TRUNC(SYSDATE)
       AND B.COD_GRUPO_CIA = P.COD_GRUPO_CIA
       AND B.COD_PROD = P.COD_PROD
       AND B.ORDEN = 1;
/*
    CURSOR curCambios IS
    SELECT a.cod_prod,
            C.DESC_PROD,
            C.DESC_UNID_PRESENT,
            TO_CHAR(precio1,'999,990.000') AS PRECIO_NUEVO,
            TO_CHAR(precio2,'999,990.000') AS PRECIO_ANTIGUO,
            a.UNID_VTA AS UNID_VTA_NUEVO,
            --b.UNID_VTA AS UNID_VTA_ANTIGUO
            b.UNID_VTA_OLD AS UNID_VTA_ANTIGUO               --JSANTIVANEZ 07.06.2011
    FROM
          (
            / *SELECT 1, cod_local, COD_PROD, UNID_VTA, ROUND(VAL_PREC_VTA * VAL_FRAC_LOCAL,2) precio1
            FROM LGT_PROD_LOCAL
            MINUS
            SELECT 1, cod_local, COD_PROD, UNID_VTA, ROUND(VAL_PREC_VTA * VAL_FRAC_LOCAL,2)
            FROM LGT_PROD_LOCAL
            AS OF TIMESTAMP (g_dFechaInicio-0.00004)
            --FROM BK_LGT_PROD_LOCAL* /

            --JSANTIVANEZ 07.06.2011
            SELECT 1, cod_local, COD_PROD, UNID_VTA, ROUND(VAL_PREC_VTA * VAL_FRAC_LOCAL,2) precio1
            FROM LGT_PROD_LOCAL
            MINUS
            select 1, cod_local, cod_prod, UNID_VTA_OLD,ROUND(VAL_PREC_VTA_OLD*val_frac_local_old,2)
            from   (
            select fec_crea,cod_prod,UNID_VTA_OLD,cod_local,val_frac_local_old,val_prec_vta_old,
               rank() OVER (PARTITION BY cod_prod,cod_local ORDER BY fec_crea desc) orden
               from   lgt_prod_local_BK
               )
            where   orden = 1

          ) a,
          (
            / *SELECT 2, cod_local, COD_PROD, UNID_VTA, ROUND(VAL_PREC_VTA * VAL_FRAC_LOCAL,2) precio2
            FROM LGT_PROD_LOCAL
            AS OF TIMESTAMP (g_dFechaInicio-0.00004)
            MINUS
            SELECT 2, cod_local, COD_PROD, UNID_VTA, ROUND(VAL_PREC_VTA * VAL_FRAC_LOCAL,2)
            FROM LGT_PROD_LOCAL* /

            --JSANTIVANEZ 07.06.2011
            select 2, cod_local, cod_prod, UNID_VTA_OLD,ROUND(VAL_PREC_VTA_OLD*val_frac_local_old,2) precio2
            from   (
            select fec_crea,cod_prod,UNID_VTA_OLD,cod_local,val_frac_local_old,val_prec_vta_old,
               rank() OVER (PARTITION BY cod_prod,cod_local ORDER BY fec_crea desc) orden
               from   lgt_prod_local_BK
               )
            where   orden = 1
            MINUS
            SELECT 2, cod_local, COD_PROD, UNID_VTA, ROUND(VAL_PREC_VTA * VAL_FRAC_LOCAL,2)
            FROM LGT_PROD_LOCAL
          ) b,
          LGT_PROD C
    WHERE a.cod_local = cCodLocal_in
          AND c.cod_prod = a.cod_prod
    	  AND a.cod_prod=b.cod_prod
        AND a.cod_local = b.cod_local
        AND C.COD_GRUPO_REP_EDMUNDO = '003' -- SOLO PRODUCTOS DE TIPO "NO FARMA"
    ORDER BY C.DESC_PROD, C.DESC_UNID_PRESENT
--    ORDER BY ABS(precio1-precio2) DESC
--    ORDER BY 1--ABS(precio1-precio2) DESC
    ;
    */
    v_rCurCambio curCambios%ROWTYPE;

--CURSO CON CAMBIO FRACCION
--JMIRANDA 10.06.2011
    CURSOR curCambiosFrac IS
    SELECT B.COD_PROD,
           P.DESC_PROD,
           P.DESC_UNID_PRESENT,
           B.VAL_FRAC_LOCAL_NVO "FRACCION_NUEVO",
           B.VAL_FRAC_LOCAL_OLD "FRACCION_ANTIGUO",
           B.UNID_VTA_NVO "UNID_VTA_NUEVO",
           B.UNID_VTA_OLD "UNID_VTA_ANTIGUO"
      FROM --LGT_PROD_LOCAL_BK B,
          (SELECT COD_GRUPO_CIA, COD_LOCAL, COD_PROD, UNID_VTA_OLD, UNID_VTA_NVO, FEC_CREA,
                  VAL_FRAC_LOCAL_NVO, VAL_FRAC_LOCAL_OLD,
                  RANK() OVER (PARTITION BY COD_GRUPO_CIA,COD_LOCAL, COD_PROD ORDER BY FEC_CREA||ROWNUM DESC) ORDEN
             FROM LGT_PROD_LOCAL_BK WHERE FEC_CREA >= TRUNC(SYSDATE)) B,
           LGT_PROD P
     WHERE B.COD_GRUPO_CIA = P.COD_GRUPO_CIA
       AND B.COD_PROD = P.COD_PROD
       AND B.ORDEN = 1;
/*    CURSOR curCambiosFrac IS
    SELECT a.cod_prod,
            C.DESC_PROD,
            C.DESC_UNID_PRESENT,
            frac1 AS FRACCION_NUEVO,
            frac2 AS FRACCION_ANTIGUO,
            a.UNID_VTA AS UNID_VTA_NUEVO,
            --b.UNID_VTA AS UNID_VTA_ANTIGUO         --JSANTIVANEZ 07.06.2011
            b.UNID_VTA_OLD AS UNID_VTA_ANTIGUO
    FROM
          (
            / *SELECT 1, cod_local, COD_PROD, UNID_VTA, VAL_FRAC_LOCAL frac1
            FROM LGT_PROD_LOCAL
            MINUS
            SELECT 1, cod_local, COD_PROD, UNID_VTA, VAL_FRAC_LOCAL
            FROM LGT_PROD_LOCAL
            AS OF TIMESTAMP (g_dFechaInicio-0.00004)* /
            --FROM BK_LGT_PROD_LOCAL* /

            --JSANTIVANEZ 07.06.2011
            SELECT 1, cod_local, COD_PROD, UNID_VTA, VAL_FRAC_LOCAL frac1
            FROM LGT_PROD_LOCAL
            MINUS
            select 1, cod_local, cod_prod, UNID_VTA_OLD,val_frac_local_old
            from   (
            select fec_crea,cod_prod,UNID_VTA_OLD,cod_local,val_frac_local_old,val_prec_vta_old,
               rank() OVER (PARTITION BY cod_prod,cod_local ORDER BY fec_crea desc) orden
               from   lgt_prod_local_BK
               )
            where   orden = 1

          ) a,
          (
            / *SELECT 2, cod_local, COD_PROD, UNID_VTA, VAL_FRAC_LOCAL frac2
            FROM LGT_PROD_LOCAL
            AS OF TIMESTAMP (g_dFechaInicio-0.00004)
            MINUS
            SELECT 2, cod_local, COD_PROD, UNID_VTA, VAL_FRAC_LOCAL
            FROM LGT_PROD_LOCAL* /

            --JSANTIVANEZ 07.06.2011
             select 2, cod_local, cod_prod, UNID_VTA_OLD,val_frac_local_old frac2
            from   (
            select fec_crea,cod_prod,UNID_VTA_OLD,cod_local,val_frac_local_old,val_prec_vta_old,
               rank() OVER (PARTITION BY cod_prod,cod_local ORDER BY fec_crea desc) orden
               from   lgt_prod_local_BK
               )
            where   orden = 1
            MINUS
            SELECT 2, cod_local, COD_PROD, UNID_VTA, VAL_FRAC_LOCAL
            FROM LGT_PROD_LOCAL

          ) b,
          LGT_PROD C
    WHERE a.cod_local = cCodLocal_in
          AND c.cod_prod = a.cod_prod
    	  AND a.cod_prod=b.cod_prod
        AND a.cod_local = b.cod_local
    ORDER BY C.DESC_PROD, C.DESC_UNID_PRESENT
    ;
    */
    v_rCurCambioFrac curCambiosFrac%ROWTYPE;

    v_vDescLocal VARCHAR2(120);

    i NUMBER(10) := 0; -- cambios en precios
    j NUMBER(10) := 0; -- cambios en fraccionamientos
    k NUMBER(10) := 0; -- cambio en estado de productos
    v_vNombreArchivo VARCHAR2(100);

    ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_CAMBIOS;
    CCReceiverAddress VARCHAR2(120) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_LOCAL(cCodLocal_in);
    mesg_body VARCHAR2(4000);

    CURSOR curCambiosEst IS
    SELECT T1.COD_PROD,
          T1.DESC_PROD,
          T1.DESC_UNID_PRESENT,
          T1.EST_PROD AS EST_NUEVO,
          T2.EST_PROD AS EST_ANTIGUO
    FROM
    (
    SELECT *
    FROM LGT_PROD
    MINUS SELECT *
    FROM LGT_PROD
    AS OF TIMESTAMP (g_dFechaInicio-0.00001)
    ) T1,
    (
    SELECT *
    FROM LGT_PROD
    AS OF TIMESTAMP (g_dFechaInicio-0.00001)
    MINUS SELECT *
    FROM LGT_PROD
    ) T2
    WHERE T1.COD_GRUPO_CIA = T2.COD_GRUPO_CIA
          AND T1.COD_PROD = T2.COD_PROD
          AND T1.EST_PROD != T2.EST_PROD;
    v_rCurCambioEst curCambiosEst%ROWTYPE;
  BEGIN

  g_dFechaInicio:=sysdate;
  Dbms_Output.put_line('g_dFechaInicio'||g_dFechaInicio);

    --DESC LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
      INTO v_vDescLocal
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    --NOM ARCHIVO
    v_vNombreArchivo := 'CambioDePrecios_'||cCodLocal_in||TO_CHAR(SYSDATE,'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MMSS')||'.htm';
    --INICIO ARCHIVO
    ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
    --INICIO HTML
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<html>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<head>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <meta content="text/html; charset=ISO-8859-1"');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,' http-equiv="content-type">');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <title>CAMBIO DE PRECIOS</title>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'</head>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<body>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<span style="font-weight: bold; font-style: italic;">LOCAL: '||v_vDescLocal||'</span><br>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<br>');

    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<table style="text-align: left; width: 100%;" border="1"');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,' cellpadding="2" cellspacing="1">');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<caption><big>CAMBIO DE PRECIOS</big></caption>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <tbody>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'    <tr>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>#</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>COD_PROD</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>DESC_PROD</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>UNID_PRESENT</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>PRECIO NUEVO</small></th>');
-- 2010-02-10 JOLIVA: Se copia al local el listado de cambios realizados
/*
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>PRECIO ANTIGUO</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>UNID_VTA NUEVO</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>UNID_VTA ANTIGUO</small></th>');
*/
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'    </tr>');
    FOR v_rCurCambio IN curCambios
    LOOP
      i := i+1;
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'   <tr>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||i||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td style="font-weight: bold;"><small>'||v_rCurCambio.cod_prod||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||v_rCurCambio.DESC_PROD||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||v_rCurCambio.DESC_UNID_PRESENT||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td style="color: rgb(255, 0, 0);"><small>'||v_rCurCambio.PRECIO_NUEVO||'</small></td>');
-- 2010-02-10 JOLIVA: Se copia al local el listado de cambios realizados
/*
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td style="color: rgb(0, 0, 255);"><small>'||v_rCurCambio.PRECIO_ANTIGUO||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||NVL(v_rCurCambio.UNID_VTA_NUEVO,'&nbsp;')||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||NVL(v_rCurCambio.UNID_VTA_ANTIGUO,'&nbsp;')||'</small></td>');
*/
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'   </tr>');
    END LOOP;
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  </tbody>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'</table>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<br><br>');

    Dbms_Output.put_line('hola');
    --JMIRANDA 08.06.2011 COMENTADO PUES NO SE UTILIZA

    INSERT INTO AUX_CAMBIO_PRECIO(COD_GRUPO_CIA,COD_PROD,DESCR_PROD,COD_LOCAL,PRECIOS,IND_PROD_FRACCIONADO,VAL_FRAC_LOCAL,IND_IMPRESORA,FECHA_CAMBIO,CONT_IMPRESION)
    SELECT
            D.COD_GRUPO_CIA,
            D.Cod_Prod,
            C.Desc_Prod,
            D.cod_local,
            precio1,
            D.Ind_Prod_Fraccionado,
            D.VAL_PREC_VTA,
            'N',
            --TO_CHAR(SYSDATE,'dd/MM/yyyy hh24:mi:ss')
            SYSDATE,
            0
    FROM
          (
            SELECT 1,cod_grupo_cia ,cod_local, COD_PROD, UNID_VTA, ROUND(VAL_PREC_VTA * VAL_FRAC_LOCAL,2) precio1
            FROM LGT_PROD_LOCAL
            MINUS
            SELECT 1,cod_grupo_cia, cod_local, COD_PROD, UNID_VTA, ROUND(VAL_PREC_VTA * VAL_FRAC_LOCAL,2)
            FROM LGT_PROD_LOCAL
            AS OF TIMESTAMP (g_dFechaInicio-0.00001)
          ) a,
          (
            SELECT 2,cod_grupo_cia, cod_local, COD_PROD, UNID_VTA, ROUND(VAL_PREC_VTA * VAL_FRAC_LOCAL,2) precio2
            FROM LGT_PROD_LOCAL
            AS OF TIMESTAMP (g_dFechaInicio-0.00001)
            MINUS
            SELECT 2,cod_grupo_cia, cod_local, COD_PROD, UNID_VTA, ROUND(VAL_PREC_VTA * VAL_FRAC_LOCAL,2)
            FROM LGT_PROD_LOCAL
          ) b,
          LGT_PROD C,
          LGT_PROD_LOCAL D
    WHERE a.cod_local = cCodLocal_in
          AND c.cod_prod = a.cod_prod
    	  AND a.cod_prod=b.cod_prod
        AND a.cod_local = b.cod_local
        AND a.COD_GRUPO_CIA=D.COD_GRUPO_CIA
        AND a.COD_PROD=D.COD_PROD
        aND a.cod_local=D.COD_LOCAL
    ORDER BY C.DESC_PROD, C.DESC_UNID_PRESENT;
    COMMIT;

/*

    --FRACCION
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<table style="text-align: left; width: 100%;" border="1"');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,' cellpadding="2" cellspacing="1">');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<caption><big>CAMBIO FRACCION</big></caption>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <tbody>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'    <tr>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>#</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>COD_PROD</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>DESC_PROD</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>UNID_PRESENT</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>FRACCION NUEVO</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>FRACCION ANTIGUO</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>UNID_VTA NUEVO</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>UNID_VTA ANTIGUO</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'    </tr>');
    FOR v_rCurCambioFrac IN curCambiosFrac
    LOOP
      j := j+1;
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'   <tr>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||j||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td style="font-weight: bold;"><small>'||v_rCurCambioFrac.cod_prod||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||v_rCurCambioFrac.DESC_PROD||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||v_rCurCambioFrac.DESC_UNID_PRESENT||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td style="color: rgb(255, 0, 0);"><small>'||v_rCurCambioFrac.FRACCION_NUEVO||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td style="color: rgb(0, 0, 255);"><small>'||v_rCurCambioFrac.FRACCION_ANTIGUO||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||NVL(v_rCurCambioFrac.UNID_VTA_NUEVO,'&nbsp;')||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||NVL(v_rCurCambioFrac.UNID_VTA_ANTIGUO,'&nbsp;')||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'   </tr>');
    END LOOP;
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  </tbody>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'</table>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<br><br>');


    --PRODUCTOS
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<table style="text-align: left; width: 100%;" border="1"');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,' cellpadding="2" cellspacing="1">');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<caption><big>CAMBIO ESTADO PRODUCTOS</big></caption>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <tbody>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'    <tr>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>#</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>COD_PROD</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>DESC_PROD</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>UNID_PRESENT</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>ESTADO NUEVO</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>ESTADO ANTIGUO</small></th>');
    --UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>UNID_VTA NUEVO</small></th>');
    --UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <th><small>UNID_VTA ANTIGUO</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'    </tr>');
    FOR v_rCurCambioEst IN curCambiosEst
    LOOP
      k := k+1;
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'   <tr>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||k||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td style="font-weight: bold;"><small>'||v_rCurCambioEst.cod_prod||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||v_rCurCambioEst.DESC_PROD||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||v_rCurCambioEst.DESC_UNID_PRESENT||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td style="color: rgb(255, 0, 0);"><small>'||v_rCurCambioEst.EST_NUEVO||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td style="color: rgb(0, 0, 255);"><small>'||v_rCurCambioEst.EST_ANTIGUO||'</small></td>');
      --UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||NVL(v_rCurCambioFrac.UNID_VTA_NUEVO,' ')||'</small></td>');
      --UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <td><small>'||NVL(v_rCurCambioFrac.UNID_VTA_ANTIGUO,' ')||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'   </tr>');
    END LOOP;
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  </tbody>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'</table>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<br>');

*/

    --FIN HTML
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'</body>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'</html>');

    --FIN ARCHIVO
    UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
    DBMS_OUTPUT.PUT_LINE('GRABO ARCHIVO DE CAMBIOS');

--       vFila_Msg_01:='<tr><font sice="-2" face="Arial" >En cumplimiento al <b>DS  No 006-2009-PCM - TEXTO &Uacute;NICO ORDENADO DE LA LEY DE PROTECCI&Oacute;N AL CONSUMIDOR, Art&iacute;culo 17</b> hemos puesto a disposici&oacute;n de cualquier consumidor que lo solicite la lista de precios de Mifarma en este local. <br>Teniendo esto en cuenta, favor de revisar el archivo adjunto y actualizar (de ser necesario) los precios de los productos que aparecen en vitrina.</font></tr>';
       vFila_Msg_01:='Estimado Jefe de Local,<BR><BR>Con el fin de mantener actualizado los precios de venta que tiene en las vitrinas de vuestros locales, se le adjunta los productos que han sufrido variaci&oacute;n en el precio para su revisi&oacute;n y proceder a actualizarlo de ser el caso.<BR><BR>Saludos,';

       mesg_body:=C_INICIO_MSG||vFila_Msg_01||C_FIN_MSG;

         FARMA_EMAIL.ENVIA_CORREO_ATTACH(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                            ReceiverAddress||',jmiranda',--ReceiverAddress, --DESCOMENTAR
--                            'jsantivanez,jmiranda',--ReceiverAddress, --COMENTAR
                            'Cambio de precios en el local '|| v_vDescLocal,
                            'Cambio de precios en el local '|| v_vDescLocal,
                            CASE WHEN (i+j+k) > 0 THEN mesg_body ELSE NULL END,
                            TRIM(v_vNombreArchivo),
                            (i+j+k),
-- 2010-02-10 JOLIVA: Se copia al local el listado de cambios realizados
--                            CCReceiverAddress,
                            'operador ' || CASE WHEN (i+j+k) > 0 THEN ', ' || CCReceiverAddress/*'dubilluz;asosa'*/ ELSE '' END,
--                            'operador ' || CASE WHEN (i+j+k) > 0 THEN ', ' || 'JMIRANDA'/*'dubilluz;asosa'*/ ELSE '' END,
--                            'operador ' || CASE WHEN (i+j+k) > 0 THEN ', ' || 'joliva' ELSE '' END,
                            FARMA_EMAIL.GET_EMAIL_SERVER);

  /*EXCEPTION
    WHEN OTHERS THEN
      --NULL;
     -- DBMS_OUTPUT.PUT_LINE('ERROR:'||SUBSTR(SQLERRM, 1, 240));
     DBMS_OUTPUT.PUT_LINE(SQLERRM);*/
  END;
/******************************************/
  FUNCTION VIAJ_LISTAR_PRECIOS_CAMBIADOS(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
    curTrab FarmaCursor;
  BEGIN




    OPEN curTrab FOR
         SELECT
                nvl(m.cod_local,' ') || 'Ã' ||
                nvl(m.cod_prod,' ')       || 'Ã' ||
                nvl(m.descr_prod,' ')       || 'Ã' ||
                NVL(m.precios,0)|| 'Ã' ||
                nvl(m.ind_prod_fraccionado,' ')             || 'Ã' ||
                NVL(m.val_frac_local,0)|| 'Ã' ||
                NVL(TO_CHAR(m.fecha_cambio,'dd/MM/yyyy HH24:MI:SS'),' ')      || 'Ã' ||
                nvl(m.ind_impresora,' ' )    || 'Ã' ||
                NVL(TO_CHAR(m.fecha_impresion,'dd/MM/yyyy HH24:MI:SS'),' ')

          FROM  AUX_CAMBIO_PRECIO m
          WHERE m.cod_grupo_cia=cCodGrupoCia_in
          AND   m.ind_impresora='N';
    RETURN curTrab;
  END;

FUNCTION V_LIST_PREC_CAMB_X_FECHA_PROD(cCodGrupoCia_in IN CHAR,
                                                    cCodLocal_in	  	IN CHAR,
                                                    cFechaInicio      IN CHAR,
                                                    cFechaFin         IN CHAR,
                                                    cDescProd         IN CHAR)
  RETURN FarmaCursor
  IS
    curTrab FarmaCursor;
  BEGIN




    OPEN curTrab FOR
         SELECT
                nvl(m.cod_local,' ') || 'Ã' ||
                nvl(m.cod_prod,' ')       || 'Ã' ||
                nvl(m.descr_prod,' ')       || 'Ã' ||
                NVL(m.precios,0)|| 'Ã' ||
                nvl(m.ind_prod_fraccionado,' ')             || 'Ã' ||
                NVL(m.val_frac_local,0)|| 'Ã' ||
                NVL(TO_CHAR(m.fecha_cambio,'dd/MM/yyyy HH24:MI:SS'),' ')       || 'Ã' ||
                nvl(m.ind_impresora,' ' )    || 'Ã' ||
                NVL(TO_CHAR(m.fecha_impresion,'dd/MM/yyyy HH24:MI:SS'),' ')

          FROM  AUX_CAMBIO_PRECIO m
          WHERE m.cod_grupo_cia=cCodGrupoCia_in
          AND   m.cod_local=cCodLocal_in
          AND   m.descr_prod like '%'||cDescProd||'%'
          AND   m.fecha_cambio BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
          AND     TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
          --AND     m.ind_impresora='N'
          ;
    RETURN curTrab;
  END;


  FUNCTION V_LIST_PREC_CAMB_FALTANTE(cCodGrupoCia_in IN CHAR, cCodLocal_in	  	IN CHAR)
  RETURN FarmaCursor
  IS
    curTrab FarmaCursor;
  BEGIN




    OPEN curTrab FOR
         SELECT
                nvl(m.cod_local,' ') || 'Ã' ||
                nvl(m.cod_prod,' ')       || 'Ã' ||
                nvl(m.descr_prod,' ')       || 'Ã' ||
                NVL(m.precios,0)|| 'Ã' ||
                nvl(m.ind_prod_fraccionado,' ')             || 'Ã' ||
                NVL(m.val_frac_local,0)|| 'Ã' ||
                NVL(TO_CHAR(m.fecha_cambio,'dd/MM/yyyy HH24:MI:SS'),' ')      || 'Ã' ||
                nvl(m.ind_impresora,' ' )    || 'Ã' ||
                NVL(TO_CHAR(m.fecha_impresion,'dd/MM/yyyy HH24:MI:SS'),' ')

          FROM  AUX_CAMBIO_PRECIO m
          WHERE m.cod_grupo_cia=cCodGrupoCia_in
          AND   m.Ind_Impresora='N';
    RETURN curTrab;
  END;

  /****************************************************************************/
  PROCEDURE ACTIVAR_PRODUCTO(cCodGrupoCia_in IN CHAR,cCodProd_in IN CHAR,cIndEstProd_in IN CHAR)
  AS
    v_nCant LGT_PROD_LOCAL.STK_FISICO%TYPE;
  BEGIN
    IF cIndEstProd_in = 'I' THEN
      BEGIN
        SELECT SUM(STK_FISICO)
          INTO v_nCant
        FROM LGT_PROD_LOCAL
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_PROD = cCodProd_in;

        IF v_nCant > 0 THEN
          UPDATE LGT_PROD SET FEC_MOD_PROD = SYSDATE,USU_MOD_PROD = g_vIdUsu,
                EST_PROD = 'A'
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_PROD = cCodProd_in;

          COMMIT;
        END IF;

      EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK;
      END;
    END IF;
  END;

  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_GRUPO_QS(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_LGT_GRUPO_QS
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_GRUPO_QS.COD_GRUPO_QS%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        INSERT INTO LGT_GRUPO_QS(COD_GRUPO_QS,
                            DESC_GRUPO_QS,
                            IND_GRUPO_MIFARMA,
                            USU_CREA_GRUPO_QS)
        VALUES(v_rCur.COD_GRUPO_QS,
                v_rCur.DESC_GRUPO_QS,
                v_rCur.IND_GRUPO_MIFARMA,
                g_vIdUsu);

        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_GRUPO_QS SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_QS = v_rCur.COD_GRUPO_QS;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          SELECT COD_GRUPO_QS INTO v_cCod
          FROM LGT_GRUPO_QS
          WHERE COD_GRUPO_QS = v_rCur.COD_GRUPO_QS FOR UPDATE;

          UPDATE LGT_GRUPO_QS
          SET DESC_GRUPO_QS = v_rCur.DESC_GRUPO_QS,
              IND_GRUPO_MIFARMA = v_rCur.IND_GRUPO_MIFARMA,
              USU_MOD_GRUPO_QS = g_vIdUsu,
              FEC_MOD_GRUPO_QS = SYSDATE
          WHERE COD_GRUPO_QS = v_rCur.COD_GRUPO_QS;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_GRUPO_QS SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_QS = v_rCur.COD_GRUPO_QS;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_GRUPO_QS,v_rCur.DESC_GRUPO_QS||', Grupo QS no actualizado',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_GRUPO_QS||' '||v_rCur.DESC_GRUPO_QS||', Grupo QS no actualizado');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Grupo QS. Registros:'||i);

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_GRUPO_QS,v_rCur.DESC_GRUPO_QS||' ERROR NO CONTROLADO AL ACTUALIZAR GRUPO_QS',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR GRUPO_QS.');
  END;

  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_COD_BARRA(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_LGT_COD_BARRA
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_COD_BARRA.COD_BARRA%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
    delete t_LGT_COD_BARRA
    where (cod_barra, tip_origen,cod_prod,cod_grupo_cia,nvl(tipo_ean,' ')   )      in (
    select cod_barra, tip_origen,cod_prod,cod_grupo_cia,nvl(tipo_ean,' ')
    from t_LGT_COD_BARRA
    intersect
    select cod_barra, tip_origen,cod_prod,cod_grupo_cia,nvl(tipo_ean,' ')
    from LGT_COD_BARRA);
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        INSERT INTO LGT_COD_BARRA(COD_BARRA	,
                            TIP_ORIGEN	,
                            COD_PROD	,
                            COD_GRUPO_CIA,
                            USU_CREA_COD_BARRA,
                            TIPO_EAN)--20/05/2008 JCORTEZ MODIFICACION	)
        VALUES(v_rCur.COD_BARRA,
                v_rCur.TIP_ORIGEN,
                v_rCur.COD_PROD,
                v_rCur.COD_GRUPO_CIA,
                g_vIdUsu,
                v_rCur.TIPO_EAN);--20/05/2008 JCORTEZ MODIFICACION

        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_COD_BARRA SET FEC_PROCESO = SYSDATE
          WHERE COD_BARRA = v_rCur.COD_BARRA;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          SELECT COD_BARRA INTO v_cCod
          FROM LGT_COD_BARRA
          WHERE COD_BARRA = v_rCur.COD_BARRA FOR UPDATE;

          UPDATE LGT_COD_BARRA
          SET TIP_ORIGEN = v_rCur.TIP_ORIGEN,
              COD_PROD = v_rCur.COD_PROD,
              COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA,
              USU_MOD_COD_BARRA	= g_vIdUsu,
              FEC_MOD_COD_BARRA	= SYSDATE,
              TIPO_EAN=v_rCur.TIPO_EAN --20/05/2008 JCORTEZ MODIFICACION
          WHERE COD_BARRA = v_rCur.COD_BARRA;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_COD_BARRA SET FEC_PROCESO = SYSDATE
          WHERE COD_BARRA = v_rCur.COD_BARRA;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,v_rCur.COD_BARRA||', Codigo Barra no actualizado',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_PROD||' '||v_rCur.COD_BARRA||', Codigo Barra no actualizado');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Cod_Barra. Registros:'||i);

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,v_rCur.COD_BARRA||' ERROR NO CONTROLADO AL ACTUALIZAR COD_BARRA',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR COD_BARRA.');
  END;

  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_LOCAL(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_PBL_LOCAL
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod PBL_LOCAL.COD_LOCAL%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO PBL_LOCAL(COD_GRUPO_CIA	,
                            COD_LOCAL	,
                            COD_CIA	,
                            DESC_CORTA_LOCAL	,
                            DESC_LOCAL	,
                            TIP_LOCAL	,
                            TIP_CAJA	,
                            COD_LOCAL_DELIV	,
                            COD_CENTRO_COSTO	,
                            NUM_MIN_DIAS_REP	,
                            NUM_MAX_DIAS_REP	,
                            NUM_DIAS_ROT	,
                            EST_LOCAL	,
                            DIREC_LOCAL	,
                            COD_TRANSPORTISTA	,
                            IND_PED_REP	,
                            FAC_BAJA_ROT	,
                            CANT_MAX_MIN_PED_PENDIENTE	,
                            IP_CONFIG_COMP_1	,
                            IP_CONFIG_COMP_2	,
                            RUTA_IMPR_REPORTE	,
                            IP_SERVIDOR_LOCAL	,
                            USU_CREA_LOCAL	,
                            MAIL_LOCAL	,
                            FEC_GENERA_MAX_MIN	,
                            PORC_ADIC_REP	,
                            CANT_UNIDAD_MAX	,
                            CANT_MAX_CONFIG,
                            DESC_ABREV,
                            IND_EN_LINEA,
                            EST_CONFIG_LOCAL,
                            --IND_LOCAL_PROV,
                            --IND_VTA_INST,
                            IND_HABILITADO
                            )
        SELECT COD_GRUPO_CIA	,
                            COD_LOCAL	,
                            COD_CIA	,
                            DESC_CORTA_LOCAL	,
                            DESC_LOCAL	,
                            TIP_LOCAL	,
                            TIP_CAJA	,
                            COD_LOCAL_DELIV	,
                            COD_CENTRO_COSTO	,
                            NUM_MIN_DIAS_REP	,
                            NUM_MAX_DIAS_REP	,
                            NUM_DIAS_ROT	,
                            EST_LOCAL	,
                            DIREC_LOCAL	,
                            COD_TRANSPORTISTA	,
                            IND_PED_REP	,
                            FAC_BAJA_ROT	,
                            CANT_MAX_MIN_PED_PENDIENTE	,
                            IP_CONFIG_COMP_1	,
                            IP_CONFIG_COMP_2	,
                            RUTA_IMPR_REPORTE	,
                            IP_SERVIDOR_LOCAL	,
                            g_vIdUsu,
                            MAIL_LOCAL	,
                            FEC_GENERA_MAX_MIN,
                            PORC_ADIC_REP	,
                            CANT_UNIDAD_MAX	,
                            CANT_MAX_CONFIG,
                            DESC_ABREV,
                            IND_EN_LINEA,
                            EST_CONFIG_LOCAL,
                            --IND_LOCAL_PROV,
                            --IND_VTA_INST,
                            IND_HABILITADO
        FROM T_PBL_LOCAL
        WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCur.COD_LOCAL;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_PBL_LOCAL SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCur.COD_LOCAL;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          --UPDATE
          UPDATE PBL_LOCAL
          SET COD_CIA	 = v_rCur.COD_CIA,
              DESC_CORTA_LOCAL	 = v_rCur.DESC_CORTA_LOCAL,
              DESC_LOCAL = v_rCur.DESC_LOCAL,
              TIP_LOCAL = v_rCur.TIP_LOCAL,
              TIP_CAJA = v_rCur.TIP_CAJA,
              COD_LOCAL_DELIV = v_rCur.COD_LOCAL_DELIV,
              COD_CENTRO_COSTO = v_rCur.COD_CENTRO_COSTO,
              NUM_MIN_DIAS_REP = v_rCur.NUM_MIN_DIAS_REP,
              NUM_MAX_DIAS_REP = v_rCur.NUM_MAX_DIAS_REP,
              NUM_DIAS_ROT = v_rCur.NUM_DIAS_ROT,
              EST_LOCAL = v_rCur.EST_LOCAL,
              DIREC_LOCAL = v_rCur.DIREC_LOCAL,
              COD_TRANSPORTISTA = v_rCur.COD_TRANSPORTISTA,
              --IND_PED_REP = v_rCur.IND_PED_REP,
              FAC_BAJA_ROT = v_rCur.FAC_BAJA_ROT,
              CANT_MAX_MIN_PED_PENDIENTE = v_rCur.CANT_MAX_MIN_PED_PENDIENTE,
              --IP_CONFIG_COMP_1 = v_rCur.IP_CONFIG_COMP_1,
              --IP_CONFIG_COMP_2 = v_rCur.IP_CONFIG_COMP_2,
              --RUTA_IMPR_REPORTE = v_rCur.RUTA_IMPR_REPORTE,
              --IP_SERVIDOR_LOCAL = v_rCur.IP_SERVIDOR_LOCAL,
              USU_MOD_LOCAL = g_vIdUsu,
              FEC_MOD_LOCAL = SYSDATE,
              MAIL_LOCAL = v_rCur.MAIL_LOCAL,
              PORC_ADIC_REP = v_rCur.PORC_ADIC_REP	,
              CANT_UNIDAD_MAX = v_rCur.CANT_UNIDAD_MAX,
              CANT_MAX_CONFIG = v_rCur.CANT_MAX_CONFIG,
              DESC_ABREV = v_rCur.DESC_ABREV,
              IND_EN_LINEA = v_rCur.IND_EN_LINEA,
              EST_CONFIG_LOCAL = v_rCur.EST_CONFIG_LOCAL--,
              --IND_LOCAL_PROV = v_rCur.IND_LOCAL_PROV,
              --IND_VTA_INST = v_rCur.IND_VTA_INST
              --IND_HABILITADO = v_rCur.IND_HABILITADO
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCur.COD_LOCAL;
          -- ACTUALIZACION DE FECHA PROCESO
            UPDATE T_PBL_LOCAL SET FEC_PROCESO = SYSDATE
            WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                  AND COD_LOCAL = v_rCur.COD_LOCAL;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_LOCAL,v_rCur.DESC_LOCAL||', local no actualizado.',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_LOCAL||' '||v_rCur.DESC_LOCAL||', local no actualizado.');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Local. Registros:'||i);

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_LOCAL,v_rCur.DESC_LOCAL||' ERROR NO CONTROLADO AL ACTUALIZAR LOCALES',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR LOCALES.');
  END;

  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_GRUPO_REP_LOCAL(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_LGT_GRUPO_REP_LOCAL
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_GRUPO_REP_LOCAL.COD_GRUPO_REP%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO LGT_GRUPO_REP_LOCAL(COD_GRUPO_CIA,
                                        COD_LOCAL,
                                        COD_GRUPO_REP	,
                                        NUM_MIN_DIAS_REP,
                                        NUM_MAX_DIAS_REP,
                                        NUM_DIAS_ROT	,
                                        EST_GRUPO_REP_LOCAL	,
                                        USU_CREA_GRUPO_REP_LOCAL
                                        )
        SELECT COD_GRUPO_CIA,
                                COD_LOCAL,
                                COD_GRUPO_REP	,
                                NUM_MIN_DIAS_REP,
                                NUM_MAX_DIAS_REP,
                                NUM_DIAS_ROT	,
                                EST_GRUPO_REP_LOCAL	,
                                g_vIdUsu
        FROM T_LGT_GRUPO_REP_LOCAL
        WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCur.COD_LOCAL
                AND COD_GRUPO_REP = v_rCur.COD_GRUPO_REP;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_GRUPO_REP_LOCAL SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCur.COD_LOCAL
                AND COD_GRUPO_REP = v_rCur.COD_GRUPO_REP;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE LGT_GRUPO_REP_LOCAL
          SET NUM_MIN_DIAS_REP = NUM_MIN_DIAS_REP,
              NUM_MAX_DIAS_REP = NUM_MAX_DIAS_REP,
              NUM_DIAS_ROT = NUM_DIAS_ROT,
              EST_GRUPO_REP_LOCAL = EST_GRUPO_REP_LOCAL,
              USU_MOD_GRUPO_REP_LOCAL = g_vIdUsu,
              FEC_MOD_GRUPO_REP_LOCAL = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCur.COD_LOCAL
                AND COD_GRUPO_REP = v_rCur.COD_GRUPO_REP;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_GRUPO_REP_LOCAL SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCur.COD_LOCAL
                AND COD_GRUPO_REP = v_rCur.COD_GRUPO_REP;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_GRUPO_REP,v_rCur.COD_GRUPO_REP||', Grupo Reposicion no actualizado.',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_GRUPO_REP||' '||'Grupo Reposicion no actualizado.');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Grupo Reposicion Local. Registros:'||i);

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_GRUPO_REP,v_rCur.COD_GRUPO_REP||' ERROR NO CONTROLADO AL ACTUALIZAR GRUPO REPOSICION LOCAL',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR GRUPO REPOSICION LOCAL.');
  END;

  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_ACC_TERAP_PROD(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT DISTINCT COD_GRUPO_CIA,COD_PROD
      FROM T_LGT_ACC_TERAP_PROD
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_ACC_TERAP_PROD.COD_PROD%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
  delete t_LGT_ACC_TERAP_PROD
  where (COD_GRUPO_CIA,COD_PROD	,COD_ACC_TERAP)
  in
  (select COD_GRUPO_CIA,COD_PROD	,COD_ACC_TERAP
  from t_LGT_ACC_TERAP_PROD
  intersect
  select COD_GRUPO_CIA,COD_PROD	,COD_ACC_TERAP
  from LGT_ACC_TERAP_PROD
  );
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --BORRA DATOS EN LOCAL
        DELETE FROM LGT_ACC_TERAP_PROD
        WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND COD_PROD = v_rCur.COD_PROD;
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO LGT_ACC_TERAP_PROD(COD_GRUPO_CIA	,
                                  COD_PROD	,
                                  COD_ACC_TERAP	,
                                  USU_CREA_ACC_TERAP	)
        SELECT DISTINCT COD_GRUPO_CIA,COD_PROD,COD_ACC_TERAP, g_vIdUsu
        FROM T_LGT_ACC_TERAP_PROD
        WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_PROD = v_rCur.COD_PROD
                AND COD_PROD IN (SELECT COD_PROD FROM LGT_PROD);
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_ACC_TERAP_PROD SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_PROD = v_rCur.COD_PROD;
        COMMIT;
      EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,'Acciones Terapeuticas Producto no actualizados.',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_PROD||' '||'Acciones Terapeuticas Producto no actualizados.');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Acc Terap Prod. Registros:'||i);

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,v_rCur.COD_PROD||' ERROR NO CONTROLADO AL ACTUALIZAR ACCIONES TERAPEUTICAS PROD',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR ACCIONES TERAPEUTICAS PROD.');
  END;

  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_PRINC_ACT_PROD(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT DISTINCT COD_GRUPO_CIA,COD_PROD
      FROM T_LGT_PRINC_ACT_PROD
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_PRINC_ACT_PROD.COD_PROD%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
  delete t_LGT_PRINC_ACT_PROD
  where (COD_GRUPO_CIA,COD_PROD	,COD_PRINC_ACT	,NVL(DESC_CONCENTRACION,' '))
  in
  (
  select COD_GRUPO_CIA,COD_PROD	,COD_PRINC_ACT	,NVL(DESC_CONCENTRACION,' ')
  from t_LGT_PRINC_ACT_PROD
  intersect
  select COD_GRUPO_CIA,COD_PROD	,COD_PRINC_ACT	,NVL(DESC_CONCENTRACION,' ')
  from LGT_PRINC_ACT_PROD
  );
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --BORRA DATOS EN LOCAL
        DELETE FROM LGT_PRINC_ACT_PROD
        WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND COD_PROD = v_rCur.COD_PROD;
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO LGT_PRINC_ACT_PROD(COD_GRUPO_CIA	,
                                  COD_PROD	,
                                  COD_PRINC_ACT	,
                                  USU_CREA_PRINC_ACT_PROD,
                                  DESC_CONCENTRACION)--JCORTEZ 20/05/2008 MODIFICACION
        SELECT DISTINCT COD_GRUPO_CIA,COD_PROD,COD_PRINC_ACT, g_vIdUsu,DESC_CONCENTRACION
        FROM T_LGT_PRINC_ACT_PROD
        WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_PROD = v_rCur.COD_PROD
                AND COD_PROD IN (SELECT COD_PROD FROM LGT_PROD);
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_PRINC_ACT_PROD SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND COD_PROD = v_rCur.COD_PROD;
        COMMIT;
      EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,'Principios Activos Producto no actualizados.',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_PROD||' '||'Principios Activos Producto no actualizados.');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Princ Act Prod. Registros:'||i);

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD,v_rCur.COD_PROD||' ERROR NO CONTROLADO AL ACTUALIZAR PRINCIPIOS ACTIVOS PROD',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR PRINCIPIOS ACTIVOS PROD.');
  END;

  PROCEDURE VIAJ_ACTUALIZA_TIP_CAMBIO(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CE_TIP_CAMBIO
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CE_TIP_CAMBIO(COD_GRUPO_CIA	,
                                  SEC_TIPO_CAMBIO,
                                  VAL_TIPO_CAMBIO,
                                  FEC_INI_VIG	,
                                  FEC_FIN_VIG	,
                                  EST_TIPO_CAMBIO,
                                  USU_CREA_TIPO_CAMBIO,
								  COD_CIA,
								  VAL_TIPO_CAMBIO_COMPRA
                                        )
        SELECT COD_GRUPO_CIA	,
                                  SEC_TIPO_CAMBIO,
                                  VAL_TIPO_CAMBIO,
                                  FEC_INI_VIG	,
                                  FEC_FIN_VIG	,
                                  EST_TIPO_CAMBIO,
                                  g_vIdUsu,
								  COD_CIA,
								  VAL_TIPO_CAMBIO_COMPRA
        FROM T_CE_TIP_CAMBIO
        WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND SEC_TIPO_CAMBIO = v_rCur.SEC_TIPO_CAMBIO;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CE_TIP_CAMBIO SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND SEC_TIPO_CAMBIO = v_rCur.SEC_TIPO_CAMBIO;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CE_TIP_CAMBIO
          SET VAL_TIPO_CAMBIO = v_rCur.VAL_TIPO_CAMBIO,
              FEC_INI_VIG = v_rCur.FEC_INI_VIG,
              FEC_FIN_VIG = v_rCur.FEC_FIN_VIG,
              EST_TIPO_CAMBIO = v_rCur.EST_TIPO_CAMBIO,
              USU_MOD_TIPO_CAMBIO = g_vIdUsu,
              FEC_MOD_TIPO_CAMBIO = SYSDATE,
			  COD_CIA = v_rCur.COD_CIA,
			  VAL_TIPO_CAMBIO_COMPRA = v_rCur.VAL_TIPO_CAMBIO_COMPRA
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND SEC_TIPO_CAMBIO = v_rCur.SEC_TIPO_CAMBIO;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CE_TIP_CAMBIO SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND SEC_TIPO_CAMBIO = v_rCur.SEC_TIPO_CAMBIO;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.FEC_INI_VIG||','||v_rCur.VAL_TIPO_CAMBIO||', Tipo Cambio no actualizado.',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.VAL_TIPO_CAMBIO||' '||', Tipo Cambio no actualizado.');
      END;	  
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Tipo Cambio. Registros:'||i);
	IF i > 0 THEN
		ENVIA_CORREO_TIPOCAMBIO('001',cCodLocal_in);
	END IF;
    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.FEC_INI_VIG||','||v_rCur.VAL_TIPO_CAMBIO||' ERROR NO CONTROLADO AL ACTUALIZAR TIPO CAMBIO',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR TIPO CAMBIO.');
  END;

 /**************************************************************************************************/
 PROCEDURE VIAJ_ACTUALIZA_TIP_CAMBIO_SAP(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CE_TIP_CAMBIO_SAP
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CE_TIP_CAMBIO_SAP(COD_GRUPO_CIA,
                                      SEC_TIPO_CAMBIO,
                                      VAL_TIPO_CAMBIO,
                                      FEC_TIPO_CAMBIO,
                                      EST_TIPO_CAMBIO,
                                      USU_CREA_TIPO_CAMBIO
                                        )
        SELECT COD_GRUPO_CIA,
                                      SEC_TIPO_CAMBIO,
                                      VAL_TIPO_CAMBIO,
                                      FEC_TIPO_CAMBIO,
                                      EST_TIPO_CAMBIO,
                                g_vIdUsu
        FROM T_CE_TIP_CAMBIO_SAP
        WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND SEC_TIPO_CAMBIO = v_rCur.SEC_TIPO_CAMBIO;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CE_TIP_CAMBIO_SAP SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND SEC_TIPO_CAMBIO = v_rCur.SEC_TIPO_CAMBIO;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CE_TIP_CAMBIO_SAP
          SET VAL_TIPO_CAMBIO = v_rCur.VAL_TIPO_CAMBIO,
              FEC_TIPO_CAMBIO = v_rCur.FEC_TIPO_CAMBIO,
              EST_TIPO_CAMBIO = v_rCur.EST_TIPO_CAMBIO,
              USU_MOD_TIPO_CAMBIO = g_vIdUsu,
              FEC_MOD_TIPO_CAMBIO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND SEC_TIPO_CAMBIO = v_rCur.SEC_TIPO_CAMBIO;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CE_TIP_CAMBIO_SAP SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
                AND SEC_TIPO_CAMBIO = v_rCur.SEC_TIPO_CAMBIO;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.FEC_TIPO_CAMBIO||','||v_rCur.VAL_TIPO_CAMBIO||', Tipo Cambio Sap no actualizado.',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.VAL_TIPO_CAMBIO||' '||', Tipo Cambio no actualizado.');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Tipo Cambio. Registros:'||i);

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.FEC_TIPO_CAMBIO||','||v_rCur.VAL_TIPO_CAMBIO||' ERROR NO CONTROLADO AL ACTUALIZAR TIPO CAMBIO SAP',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR TIPO CAMBIO.');
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_ACC_TERAP(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_LGT_ACC_TERAP
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_ACC_TERAP.COD_ACC_TERAP%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO LGT_ACC_TERAP(COD_ACC_TERAP	,
                                  DESC_ACC_TERAP,
                                  IND_ACC_TERAP_FARMA	,
                                  USU_CREA_ACC_TERAP	)
        VALUES(v_rCur.COD_ACC_TERAP	,
                v_rCur.DESC_ACC_TERAP,
                v_rCur.IND_ACC_TERAP_FARMA,
                g_vIdUsu);
        -- ACTUALIZACION DE FECHA PROCESO
        UPDATE T_LGT_ACC_TERAP SET FEC_PROCESO = SYSDATE
        WHERE COD_ACC_TERAP = v_rCur.COD_ACC_TERAP;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE LGT_ACC_TERAP
          SET DESC_ACC_TERAP = v_rCur.DESC_ACC_TERAP,
              IND_ACC_TERAP_FARMA = v_rCur.IND_ACC_TERAP_FARMA,
              USU_MOD_ACC_TERAP = g_vIdUsu,
              FEC_MOD_ACC_TERAP = SYSDATE
          WHERE COD_ACC_TERAP = v_rCur.COD_ACC_TERAP;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_ACC_TERAP SET FEC_PROCESO = SYSDATE
          WHERE COD_ACC_TERAP = v_rCur.COD_ACC_TERAP;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_ACC_TERAP,'Maestro Acciones Terapeuticas no actualizados.',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_ACC_TERAP||' '||'Maestro Acciones Terapeuticas no actualizados.');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Maestro Acc Terap. Registros:'||i);

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_ACC_TERAP,v_rCur.DESC_ACC_TERAP||' ERROR NO CONTROLADO AL ACTUALIZAR MAESTRO ACCIONES TERAPEUTICAS',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR MAESTRO ACCIONES TERAPEUTICAS.');
  END;

  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_PRINC_ACT(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_LGT_PRINC_ACT
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_PRINC_ACT.COD_PRINC_ACT%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO LGT_PRINC_ACT(COD_PRINC_ACT	,
                                    DESC_PRINC_ACT	,
                                    EST_PRINC_ACT_PROD	,
                                    IND_PRINC_ACT_FARMA	,
                                    USU_CREA_PRINC_ACT	)
        VALUES(v_rCur.COD_PRINC_ACT	,
                v_rCur.DESC_PRINC_ACT,
                v_rCur.EST_PRINC_ACT_PROD,
                v_rCur.IND_PRINC_ACT_FARMA,
                g_vIdUsu);
        -- ACTUALIZACION DE FECHA PROCESO
        UPDATE T_LGT_PRINC_ACT SET FEC_PROCESO = SYSDATE
        WHERE COD_PRINC_ACT = v_rCur.COD_PRINC_ACT;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE LGT_PRINC_ACT
          SET DESC_PRINC_ACT = v_rCur.DESC_PRINC_ACT,
              EST_PRINC_ACT_PROD = v_rCur.EST_PRINC_ACT_PROD,
              IND_PRINC_ACT_FARMA = v_rCur.IND_PRINC_ACT_FARMA,
              USU_MOD_PRINC_ACT = g_vIdUsu,
              FEC_MOD_PRINC_ACT = SYSDATE
          WHERE COD_PRINC_ACT = v_rCur.COD_PRINC_ACT;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_PRINC_ACT SET FEC_PROCESO = SYSDATE
          WHERE COD_PRINC_ACT = v_rCur.COD_PRINC_ACT;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PRINC_ACT,'Maestro Principios Activos no actualizados.',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_PRINC_ACT||' '||'Maestro Principios Activos no actualizados.');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Maestro Principios Activos. Registros:'||i);

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PRINC_ACT,v_rCur.DESC_PRINC_ACT||' ERROR NO CONTROLADO AL ACTUALIZAR MAESTRO PRINCIPIOS ACTIVOS',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR MAESTRO PRINCIPIOS ACTIVOS.');
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_CARGO(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CE_CARGO
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CE_CARGO(COD_CARGO	,
                              DESC_CARGO,
                              USU_CREA_CARGO	)
        SELECT COD_CARGO,
                                DESC_CARGO	,
                                g_vIdUsu
        FROM T_CE_CARGO
        WHERE COD_CARGO = v_rCur.COD_CARGO;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CE_CARGO SET FEC_PROCESO = SYSDATE
          WHERE COD_CARGO = v_rCur.COD_CARGO;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CE_CARGO
          SET DESC_CARGO = v_rCur.DESC_CARGO,
              USU_MOD_CARGO = g_vIdUsu,
              FEC_MOD_CARGO = SYSDATE
          WHERE COD_CARGO = v_rCur.COD_CARGO;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CE_CARGO SET FEC_PROCESO = SYSDATE
          WHERE COD_CARGO = v_rCur.COD_CARGO;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_CARGO,v_rCur.DESC_CARGO||', Cargo no actualizado.',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.DESC_CARGO||', Cargo no actualizado.');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Cargos. Registros:'||i);

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_CARGO,v_rCur.DESC_CARGO||' ERROR NO CONTROLADO AL ACTUALIZAR CARGOS',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR CARGOS.');
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_MAE_TRAB(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CE_MAE_TRAB
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CE_MAE_TRAB(COD_CIA	,
                            COD_TRAB	,
                            APE_PAT_TRAB	,
                            APE_MAT_TRAB	,
                            NOM_TRAB	,
                            TELEF_TRAB	,
                            DIRECC_TRAB	,
                            FEC_NAC_TRAB	,
                            EST_TRAB	,
                            TIP_DOC_IDENT	,
                            NUM_DOC_IDEN	,
                            FEC_INGRESO	,
                            COD_CARGO		,
                            COD_SAP,
                            USU_CREA_TRAB,
                            COD_TRAB_RRHH,
                            DESC_GERENCIA,
                            IND_FISCALIZADO,
                            FEC_CESE_TRAB
                            )
        SELECT COD_CIA	,
                            COD_TRAB	,
                            APE_PAT_TRAB	,
                            APE_MAT_TRAB	,
                            NOM_TRAB	,
                            TELEF_TRAB	,
                            DIRECC_TRAB	,
                            FEC_NAC_TRAB	,
                            EST_TRAB	,
                            TIP_DOC_IDENT	,
                            NUM_DOC_IDEN	,
                            FEC_INGRESO	,
                            COD_CARGO		,
                            COD_SAP,
                            g_vIdUsu,
                            COD_TRAB_RRHH,
                            DESC_GERENCIA,
                            IND_FISCALIZADO,
                            FEC_CESE_TRAB
        FROM T_CE_MAE_TRAB
        WHERE COD_CIA = g_cCodCia
              AND COD_TRAB = v_rCur.COD_TRAB;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CE_MAE_TRAB SET FEC_PROCESO = SYSDATE
          WHERE COD_CIA = g_cCodCia
                AND COD_TRAB = v_rCur.COD_TRAB;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CE_MAE_TRAB
          SET APE_PAT_TRAB = v_rCur.APE_PAT_TRAB,
              APE_MAT_TRAB = v_rCur.APE_MAT_TRAB,
              NOM_TRAB = v_rCur.NOM_TRAB,
              TELEF_TRAB = v_rCur.TELEF_TRAB,
              DIRECC_TRAB = v_rCur.DIRECC_TRAB,
              FEC_NAC_TRAB = v_rCur.FEC_NAC_TRAB,
              EST_TRAB = v_rCur.EST_TRAB,
              TIP_DOC_IDENT = v_rCur.TIP_DOC_IDENT,
              NUM_DOC_IDEN = v_rCur.NUM_DOC_IDEN,
              FEC_INGRESO = v_rCur.FEC_INGRESO,
              COD_CARGO = v_rCur.COD_CARGO,
              COD_SAP = v_rCur.COD_SAP,
              FEC_MOD_TRAB = SYSDATE,
              USU_MOD_TRAB = g_vIdUsu,
              COD_TRAB_RRHH = v_rCur.COD_TRAB_RRHH,
              DESC_GERENCIA = v_rCur.DESC_GERENCIA,
              IND_FISCALIZADO = v_rCur.IND_FISCALIZADO,
              FEC_CESE_TRAB = v_rCur.FEC_CESE_TRAB
          WHERE COD_CIA = g_cCodCia
                AND COD_TRAB = v_rCur.COD_TRAB;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CE_MAE_TRAB SET FEC_PROCESO = SYSDATE
          WHERE COD_CIA = g_cCodCia
                AND COD_TRAB = v_rCur.COD_TRAB;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_TRAB,v_rCur.APE_PAT_TRAB||', Trabajador no actualizado.',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.APE_PAT_TRAB||', Trabajador no actualizado.');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Trabajadores. Registros:'||i);

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_TRAB,v_rCur.APE_PAT_TRAB||' ERROR NO CONTROLADO AL ACTUALIZAR TRABAJADORES',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR TRABAJADORES.');
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_CAMBIO_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_LGT_CAMBIO_PROD
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO LGT_CAMBIO_PROD(COD_GRUPO_CIA,
                                    COD_PROD_ANT,
                                    COD_PROD_NUE,
                                    EST_CAMBIO_PROD,
                                    FEC_INI_CAMBIO_PROD,
                                    FEC_FIN_CAMBIO_PROD,
                                    USU_CREA_CAMBIO_PROD	)
        SELECT COD_GRUPO_CIA,
              COD_PROD_ANT,
              COD_PROD_NUE,
              EST_CAMBIO_PROD,
              FEC_INI_CAMBIO_PROD,
              FEC_FIN_CAMBIO_PROD,
               g_vIdUsu
        FROM T_LGT_CAMBIO_PROD
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_PROD_ANT = v_rCur.COD_PROD_ANT
              AND FEC_INI_CAMBIO_PROD = v_rCur.FEC_INI_CAMBIO_PROD;
        -- ACTUALIZACION DE FECHA PROCESO
        UPDATE T_LGT_CAMBIO_PROD SET FEC_PROCESO = SYSDATE
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_PROD_ANT = v_rCur.COD_PROD_ANT
          AND FEC_INI_CAMBIO_PROD = v_rCur.FEC_INI_CAMBIO_PROD;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE LGT_CAMBIO_PROD
          SET COD_PROD_NUE = v_rCur.COD_PROD_NUE,
              EST_CAMBIO_PROD = v_rCur.EST_CAMBIO_PROD,
              FEC_FIN_CAMBIO_PROD = v_rCur.FEC_FIN_CAMBIO_PROD,
              FEC_MOD_CAMBIO_PROD = SYSDATE,
              USU_MOD_CAMBIO_PROD = g_vIdUsu
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_PROD_ANT = v_rCur.COD_PROD_ANT
                AND FEC_INI_CAMBIO_PROD = v_rCur.FEC_INI_CAMBIO_PROD;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_CAMBIO_PROD SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_PROD_ANT = v_rCur.COD_PROD_ANT
                AND FEC_INI_CAMBIO_PROD = v_rCur.FEC_INI_CAMBIO_PROD;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD_ANT,v_rCur.COD_PROD_ANT||'-->>'||v_rCur.COD_PROD_NUE||', Cambio Producto no actualizado.',err_msg);
          --DBMS_OUTPUT.PUT_LINE('Registro:'||i||'/Error/'||v_rCur.COD_PROD_ANT||'-->>'||v_rCur.COD_PROD_NUE||', Cambio Producto no actualizado.');
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Finalizo el proceso de actualizar Cambio Productos. Registros:'||i);

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROD_ANT,v_rCur.COD_PROD_ANT||'-->>'||v_rCur.COD_PROD_NUE||' ERROR NO CONTROLADO AL ACTUALIZAR CAMBIO PROD',err_msg);
      --DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR NO CONTROLADO AL ACTUALIZAR CAMBIO PROD.');
  END;
  /****************************************************************************/
  /*PROCEDURE VIAJ_ACTUALIZA_CAMPOS_FORM(cCodLocal_in IN CHAR)
  AS
   err_msg VARCHAR2(250);

    BEGIN
         MERGE INTO CON_CAMPOS_FORMULARIO A
         USING T_CON_CAMPOS_FORMULARIO B
         ON(A.COD_CAMPO = B.COD_CAMPO)
         when matched then update set
         A.NOM_CAMPO = B.NOM_CAMPO,
         A.EST_CAMPO = B.EST_CAMPO,
         A.FEC_MOD_CAMPOS_FORMULARIO = SYSDATE,
         A.USU_MOD_CAMPOS_FORMULARIO = B.USU_MOD_CAMPOS_FORMULARIO
         when not matched then insert(
         COD_CAMPO,
         NOM_CAMPO,
         EST_CAMPO,
         USU_CREA_CAMPOS_FORMULARIO) VALUES(
         B.COD_CAMPO,
         B.NOM_CAMPO,
         B.EST_CAMPO,
         B.USU_CREA_CAMPOS_FORMULARIO);

         UPDATE T_CON_CAMPOS_FORMULARIO B
         SET    B.FEC_PROCESO = SYSDATE
         WHERE  B.FEC_PROCESO IS NULL
         AND    EXISTS(SELECT 1
                       FROM   CON_CAMPOS_FORMULARIO A
                       WHERE  A.COD_CAMPO = B.COD_CAMPO);

         dbms_output.put_line('Terminado: '||SQL%ROWCOUNT||' filas afectadas.');
         COMMIT ;
    EXCEPTION
    WHEN OTHERS THEN
         ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,NULL,'CAMPO DE FORMULARIO NO ACTUALIZADO',err_msg);
    END;
  */
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_CAMPOS_FORM(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CON_CAMPOS_FORMULARIO
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CON_CAMPOS_FORMULARIO(COD_CAMPO,
                    NOM_CAMPO,
                    EST_CAMPO,
                    USU_CREA_CAMPOS_FORMULARIO)
        SELECT COD_CAMPO,
                    NOM_CAMPO,
                    EST_CAMPO,
                    g_vIdUsu
        FROM T_CON_CAMPOS_FORMULARIO
        WHERE COD_CAMPO = v_rCur.COD_CAMPO;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_CAMPOS_FORMULARIO SET FEC_PROCESO = SYSDATE
          WHERE COD_CAMPO = v_rCur.COD_CAMPO;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CON_CAMPOS_FORMULARIO
          SET NOM_CAMPO = v_rCur.NOM_CAMPO,
              EST_CAMPO = v_rCur.EST_CAMPO,
              FEC_MOD_CAMPOS_FORMULARIO = SYSDATE,
              USU_MOD_CAMPOS_FORMULARIO = g_vIdUsu
          WHERE COD_CAMPO = v_rCur.COD_CAMPO;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_CAMPOS_FORMULARIO SET FEC_PROCESO = SYSDATE
          WHERE COD_CAMPO = v_rCur.COD_CAMPO;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_CAMPO,v_rCur.NOM_CAMPO||', Campo no actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_CAMPO,v_rCur.NOM_CAMPO||' ERROR NO CONTROLADO AL ACTUALIZAR CAMPOS_FORM',err_msg);
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_CON_LISTA(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CON_LISTA
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CON_LISTA(COD_LISTA,
                    DESC_CORTA_LISTA,
                    DESC_LISTA,
                    EST_LISTA,
                    IND_TIPO_LISTA,
                    USU_CREA_LISTA)
        SELECT COD_LISTA,
                    DESC_CORTA_LISTA,
                    DESC_LISTA,
                    EST_LISTA,
                    IND_TIPO_LISTA,
                    g_vIdUsu
        FROM T_CON_LISTA
        WHERE COD_LISTA = v_rCur.COD_LISTA;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_LISTA SET FEC_PROCESO = SYSDATE
          WHERE COD_LISTA = v_rCur.COD_LISTA;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CON_LISTA
          SET DESC_CORTA_LISTA = v_rCur.DESC_CORTA_LISTA,
              DESC_LISTA = v_rCur.DESC_LISTA,
              EST_LISTA = v_rCur.EST_LISTA,
              IND_TIPO_LISTA = v_rCur.IND_TIPO_LISTA,
              FEC_MOD_LISTA = SYSDATE,
              USU_MOD_LISTA = g_vIdUsu
          WHERE COD_LISTA = v_rCur.COD_LISTA;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_LISTA SET FEC_PROCESO = SYSDATE
          WHERE COD_LISTA = v_rCur.COD_LISTA;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_LISTA,v_rCur.DESC_LISTA||', Lista no actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_LISTA,v_rCur.DESC_LISTA||' ERROR NO CONTROLADO AL ACTUALIZAR LISTAS',err_msg);
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_MAE_EMPRESA(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CON_MAE_EMPRESA
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CON_MAE_EMPRESA(COD_EMPRESA,
                    DESC_RAZON_SOCIAL,
                    NUM_RUC,
                    USU_CREA_MAE_EMPRESA	)
        SELECT COD_EMPRESA,
                    DESC_RAZON_SOCIAL,
                    NUM_RUC,
                    g_vIdUsu
        FROM T_CON_MAE_EMPRESA
        WHERE COD_EMPRESA = v_rCur.COD_EMPRESA;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_MAE_EMPRESA SET FEC_PROCESO = SYSDATE
          WHERE COD_EMPRESA = v_rCur.COD_EMPRESA;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CON_MAE_EMPRESA
          SET COD_EMPRESA = v_rCur.COD_EMPRESA,
              DESC_RAZON_SOCIAL = v_rCur.DESC_RAZON_SOCIAL,
              NUM_RUC = v_rCur.NUM_RUC,
              FEC_MOD_MAE_EMPRESA = SYSDATE,
              USU_MOD_MAE_EMPRESA = g_vIdUsu
          WHERE COD_EMPRESA = v_rCur.COD_EMPRESA;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_MAE_EMPRESA SET FEC_PROCESO = SYSDATE
          WHERE COD_EMPRESA = v_rCur.COD_EMPRESA;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.DESC_RAZON_SOCIAL||', Empresa no actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.DESC_RAZON_SOCIAL||' ERROR NO CONTROLADO AL ACTUALIZAR EMPRESAS',err_msg);
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_MAE_CONV(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CON_MAE_CONVENIO
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CON_MAE_CONVENIO(COD_CONVENIO,
                    DESC_CORTA_CONV,
                    DESC_LARGA_CONV,
                    PORC_DCTO_CONV,
                    PORC_COPAGO_CONV,
                    COD_EMPRESA,
                    NUM_DIA_INI_FACT,
                    EST_CONVENIO,
                    IND_MAE_PROD,
                    IND_LOCAL_LIMA,
                    IND_LOCAL_PROV,
                    --EST_MANUAL,
                    --IND_MULTIPLE_CONV,
                    USU_CREA_MAE_CONVENIO)
        SELECT COD_CONVENIO,
                    DESC_CORTA_CONV,
                    DESC_LARGA_CONV,
                    PORC_DCTO_CONV,
                    PORC_COPAGO_CONV,
                    COD_EMPRESA,
                    NUM_DIA_INI_FACT,
                    EST_CONVENIO,
                    IND_MAE_PROD,
                    IND_LOCAL_LIMA,
                    IND_LOCAL_PROV,
                    --EST_MANUAL,
                    --IND_MULTIPLE_CONV,
                    g_vIdUsu
        FROM T_CON_MAE_CONVENIO
        WHERE COD_CONVENIO = v_rCur.COD_CONVENIO;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_MAE_CONVENIO SET FEC_PROCESO = SYSDATE
          WHERE COD_CONVENIO = v_rCur.COD_CONVENIO;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CON_MAE_CONVENIO
          SET DESC_CORTA_CONV = v_rCur.DESC_CORTA_CONV,
              DESC_LARGA_CONV = v_rCur.DESC_LARGA_CONV,
              PORC_DCTO_CONV = v_rCur.PORC_DCTO_CONV,
              PORC_COPAGO_CONV = v_rCur.PORC_COPAGO_CONV,
              COD_EMPRESA = v_rCur.COD_EMPRESA,
              NUM_DIA_INI_FACT = v_rCur.NUM_DIA_INI_FACT,
              EST_CONVENIO = v_rCur.EST_CONVENIO,
              IND_MAE_PROD = v_rCur.IND_MAE_PROD,
              IND_LOCAL_LIMA = v_rCur.IND_LOCAL_LIMA,
              IND_LOCAL_PROV = v_rCur.IND_LOCAL_PROV,
              --EST_MANUAL = v_rCur.EST_MANUAL,
              --IND_MULTIPLE_CONV = v_rCur.IND_MULTIPLE_CONV,
              FEC_MOD_MAE_CONVENIO = SYSDATE,
              USU_MOD_MAE_CONVENIO = g_vIdUsu
          WHERE COD_CONVENIO = v_rCur.COD_CONVENIO;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_MAE_CONVENIO SET FEC_PROCESO = SYSDATE
          WHERE COD_CONVENIO = v_rCur.COD_CONVENIO;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.DESC_CORTA_CONV||', Convenio no actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.DESC_CORTA_CONV||' ERROR NO CONTROLADO AL ACTUALIZAR CONVENIOS',err_msg);
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_CAMPOS_CONV(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CON_CAMPOS_CONVENIO
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CON_CAMPOS_CONVENIO(COD_CONVENIO,
                    COD_CAMPO,
                    IND_TIP_DATO,
                    IND_SOLO_LECTURA,
                    IND_OBLIGATORIO,
                    USU_CREA_CAMPOS_CONVENIO
                    )
        SELECT COD_CONVENIO,
                    COD_CAMPO,
                    IND_TIP_DATO,
                    IND_SOLO_LECTURA,
                    IND_OBLIGATORIO,
                    g_vIdUsu
        FROM T_CON_CAMPOS_CONVENIO
        WHERE COD_CONVENIO = v_rCur.COD_CONVENIO
              AND COD_CAMPO = v_rCur.COD_CAMPO;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_CAMPOS_CONVENIO SET FEC_PROCESO = SYSDATE
          WHERE COD_CONVENIO = v_rCur.COD_CONVENIO
              AND COD_CAMPO = v_rCur.COD_CAMPO;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CON_CAMPOS_CONVENIO
          SET IND_TIP_DATO = v_rCur.IND_TIP_DATO,
              IND_SOLO_LECTURA = v_rCur.IND_SOLO_LECTURA,
              IND_OBLIGATORIO = v_rCur.IND_OBLIGATORIO,
              FEC_MOD_CAMPOS_CONVENIO = SYSDATE,
              USU_MOD_CAMPOS_CONVENIO = g_vIdUsu
          WHERE COD_CONVENIO = v_rCur.COD_CONVENIO
              AND COD_CAMPO = v_rCur.COD_CAMPO;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_CAMPOS_CONVENIO SET FEC_PROCESO = SYSDATE
          WHERE COD_CONVENIO = v_rCur.COD_CONVENIO
              AND COD_CAMPO = v_rCur.COD_CAMPO;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.COD_CAMPO||', Campo por Comvenio no actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.COD_CAMPO||' ERROR NO CONTROLADO AL ACTUALIZAR CAMPOS POR CONVENIOS',err_msg);
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_LISTA_CONV(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CON_LISTA_CONVENIO
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CON_LISTA_CONVENIO(COD_CONVENIO,
                    COD_LISTA,
                    SEC_LISTA_CONV,
                    EST_LISTA_CONVENIO,
                    IND_DCTO_PORC,
                    VAL_DCTO_LIST_CONV,
                    USU_CREA_LISTA_CONVENIO)
        SELECT COD_CONVENIO,
                    COD_LISTA,
                    SEC_LISTA_CONV,
                    EST_LISTA_CONVENIO,
                    IND_DCTO_PORC,
                    VAL_DCTO_LIST_CONV,
                    g_vIdUsu
        FROM T_CON_LISTA_CONVENIO
        WHERE COD_CONVENIO = v_rCur.COD_CONVENIO
              AND COD_LISTA = v_rCur.COD_LISTA;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_LISTA_CONVENIO SET FEC_PROCESO = SYSDATE
          WHERE COD_CONVENIO = v_rCur.COD_CONVENIO
              AND COD_LISTA = v_rCur.COD_LISTA;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CON_LISTA_CONVENIO
          SET SEC_LISTA_CONV = v_rCur.SEC_LISTA_CONV,
              EST_LISTA_CONVENIO = v_rCur.EST_LISTA_CONVENIO,
              IND_DCTO_PORC = v_rCur.IND_DCTO_PORC,
              VAL_DCTO_LIST_CONV = v_rCur.VAL_DCTO_LIST_CONV,
              FEC_MOD_LISTA_CONVENIO = SYSDATE,
              USU_MOD_LISTA_CONVENIO = g_vIdUsu
          WHERE COD_CONVENIO = v_rCur.COD_CONVENIO
              AND COD_LISTA = v_rCur.COD_LISTA;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_LISTA_CONVENIO SET FEC_PROCESO = SYSDATE
          WHERE COD_CONVENIO = v_rCur.COD_CONVENIO
              AND COD_LISTA = v_rCur.COD_LISTA;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.COD_LISTA||', Lista por Convenio no actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.COD_LISTA||' ERROR NO CONTROLADO AL ACTUALIZAR LISTAS POR CONVENIOS',err_msg);
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_LAB_LISTA(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CON_LAB_LISTA
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CON_LAB_LISTA(COD_LISTA,
                    COD_LAB,
                    EST_LAB_LISTA,
                    USU_CREA_LAB_LISTA)
        SELECT COD_LISTA,
                    COD_LAB,
                    EST_LAB_LISTA,
                    g_vIdUsu
        FROM T_CON_LAB_LISTA
        WHERE COD_LISTA = v_rCur.COD_LISTA
              AND COD_LAB = v_rCur.COD_LAB;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_LAB_LISTA SET FEC_PROCESO = SYSDATE
          WHERE COD_LISTA = v_rCur.COD_LISTA
              AND COD_LAB = v_rCur.COD_LAB;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CON_LAB_LISTA
          SET EST_LAB_LISTA = v_rCur.EST_LAB_LISTA,
              FEC_MOD_LAB_LISTA = SYSDATE,
              USU_MOD_LAB_LISTA = g_vIdUsu
          WHERE COD_LISTA = v_rCur.COD_LISTA
              AND COD_LAB = v_rCur.COD_LAB;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_LAB_LISTA SET FEC_PROCESO = SYSDATE
          WHERE COD_LISTA = v_rCur.COD_LISTA
              AND COD_LAB = v_rCur.COD_LAB;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_LISTA,v_rCur.COD_LAB||', Laboratorio por Lista no actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_LISTA,v_rCur.COD_LAB||' ERROR NO CONTROLADO AL ACTUALIZAR LABORATORIOS POR LISTAS',err_msg);
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_PROD_LISTA(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CON_PROD_LISTA
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CON_PROD_LISTA(COD_LISTA,
                    COD_GRUPO_CIA,
                    COD_PROD,
                    EST_PROD_LISTA,
                    USU_CREA_PROD_LISTA,
                    PREC_VTA)--23/05/08 JCORTEZ
        SELECT COD_LISTA,
                    COD_GRUPO_CIA,
                    COD_PROD,
                    EST_PROD_LISTA,
                    g_vIdUsu,
                    PREC_VTA --23/05/08 JCORTEZ
        FROM T_CON_PROD_LISTA
        WHERE COD_LISTA = v_rCur.COD_LISTA
              AND COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND COD_PROD = v_rCur.COD_PROD;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_PROD_LISTA SET FEC_PROCESO = SYSDATE
          WHERE COD_LISTA = v_rCur.COD_LISTA
              AND COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND COD_PROD = v_rCur.COD_PROD;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CON_PROD_LISTA
          SET EST_PROD_LISTA = v_rCur.EST_PROD_LISTA,
              FEC_MOD_PROD_LISTA = SYSDATE,
              USU_MOD_PROD_LISTA = g_vIdUsu,
              PREC_VTA=v_rCuR.Prec_Vta--23/05/08 JCORTEZ
          WHERE COD_LISTA = v_rCur.COD_LISTA
              AND COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND COD_PROD = v_rCur.COD_PROD;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_PROD_LISTA SET FEC_PROCESO = SYSDATE
          WHERE COD_LISTA = v_rCur.COD_LISTA
              AND COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND COD_PROD = v_rCur.COD_PROD;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_LISTA,v_rCur.COD_PROD||', Producto por Lista no actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_LISTA,v_rCur.COD_PROD||' ERROR NO CONTROLADO AL ACTUALIZAR PRODUCTOS POR LISTAS',err_msg);
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_LOCAL_CONV(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CON_LOCAL_X_CONV
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CON_LOCAL_X_CONV(COD_GRUPO_CIA,
                    COD_LOCAL,
                    COD_CONVENIO,
                    EST_LOCAL_X_CONV,
                    USU_CREA_LOCAL_X_CONV	)
        SELECT COD_GRUPO_CIA,
                    COD_LOCAL,
                    COD_CONVENIO,
                    EST_LOCAL_X_CONV,
                    g_vIdUsu
        FROM T_CON_LOCAL_X_CONV
        WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND COD_LOCAL = v_rCur.COD_LOCAL
              AND COD_CONVENIO = v_rCur.COD_CONVENIO;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_LOCAL_X_CONV SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND COD_LOCAL = v_rCur.COD_LOCAL
              AND COD_CONVENIO = v_rCur.COD_CONVENIO;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CON_LOCAL_X_CONV
          SET EST_LOCAL_X_CONV = v_rCur.EST_LOCAL_X_CONV,
              FEC_MOD_LOCAL_X_CONV = SYSDATE,
              USU_MOD_LOCAL_X_CONV = g_vIdUsu
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND COD_LOCAL = v_rCur.COD_LOCAL
              AND COD_CONVENIO = v_rCur.COD_CONVENIO;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_LOCAL_X_CONV SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
              AND COD_LOCAL = v_rCur.COD_LOCAL
              AND COD_CONVENIO = v_rCur.COD_CONVENIO;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_LOCAL,v_rCur.COD_CONVENIO||', Convenio por Local no actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_LOCAL,v_rCur.COD_CONVENIO||' ERROR NO CONTROLADO AL ACTUALIZAR CONVENIOS POR LOCALES',err_msg);
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_MAE_CLIENTE(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CON_MAE_CLIENTE
      WHERE FEC_PROCESO IS NULL
            /*AND COD_CLI IN (SELECT C.COD_CLI
                           FROM T_CON_CLI_CONV C,T_CON_LOCAL_X_CONV L
                            WHERE C.COD_CONVENIO = L.COD_CONVENIO
                                  AND L.COD_LOCAL = cCodLocal_in
                            UNION
                            SELECT C.COD_CLI
                           FROM CON_CLI_CONV C,CON_LOCAL_X_CONV L
                            WHERE C.COD_CONVENIO = L.COD_CONVENIO
                                  AND L.COD_LOCAL = cCodLocal_in)*/;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CON_MAE_CLIENTE(COD_CLI,
                    NOM_CLI,
                    APE_PAT_CLI,
                    APE_MAT_CLI,
                    TIP_DOC_CLI,
                    NUM_DOC_CLI,
                    NOM_COMPLETO,
                    USU_CREA_MAE_CLIENTE)
        SELECT COD_CLI,
                    NOM_CLI,
                    APE_PAT_CLI,
                    APE_MAT_CLI,
                    TIP_DOC_CLI,
                    NUM_DOC_CLI,
                    NOM_COMPLETO,
                    g_vIdUsu
        FROM T_CON_MAE_CLIENTE
        WHERE COD_CLI = v_rCur.COD_CLI;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_MAE_CLIENTE SET FEC_PROCESO = SYSDATE
          WHERE COD_CLI = v_rCur.COD_CLI;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CON_MAE_CLIENTE
          SET NOM_CLI = v_rCur.NOM_CLI,
              APE_PAT_CLI = v_rCur.APE_PAT_CLI,
              APE_MAT_CLI = v_rCur.APE_MAT_CLI,
              TIP_DOC_CLI = v_rCur.TIP_DOC_CLI,
              NUM_DOC_CLI = v_rCur.NUM_DOC_CLI,
              NOM_COMPLETO = v_rCur.NOM_COMPLETO,
              FEC_MOD_MAE_CLIENTE = SYSDATE,
              USU_MOD_MAE_CLIENTE = g_vIdUsu
          WHERE COD_CLI = v_rCur.COD_CLI;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_MAE_CLIENTE SET FEC_PROCESO = SYSDATE
          WHERE COD_CLI = v_rCur.COD_CLI;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.NUM_DOC_CLI||', Cliente no actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.NUM_DOC_CLI||' ERROR NO CONTROLADO AL ACTUALIZAR CLIENTES',err_msg);
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_CLI_CONV(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CON_CLI_CONV
      WHERE FEC_PROCESO IS NULL
            /*AND COD_CONVENIO IN (SELECT L.COD_CONVENIO
                           FROM T_CON_LOCAL_X_CONV L
                            WHERE L.COD_LOCAL = cCodLocal_in)*/;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DEL VIAJERO
        INSERT INTO CON_CLI_CONV(VAL_CREDITO_UTIL,
                    COD_CONVENIO,
                    COD_CLI,
                    VAL_CREDITO_MAX,
                    COD_TRAB_CONV,
                    EST_CONV_CLI,
                    USU_CREA_CLI_CONV)
        SELECT VAL_CREDITO_UTIL,
                    COD_CONVENIO,
                    COD_CLI,
                    VAL_CREDITO_MAX,
                    COD_TRAB_CONV,
                    EST_CONV_CLI,
                    g_vIdUsu
        FROM T_CON_CLI_CONV
        WHERE COD_CONVENIO = v_rCur.COD_CONVENIO
              AND COD_CLI = v_rCur.COD_CLI;
        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_CLI_CONV SET FEC_PROCESO = SYSDATE
          WHERE COD_CONVENIO = v_rCur.COD_CONVENIO
              AND COD_CLI = v_rCur.COD_CLI;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CON_CLI_CONV
          SET --VAL_CREDITO_UTIL = v_rCur.VAL_CREDITO_UTIL,
              VAL_CREDITO_MAX = v_rCur.VAL_CREDITO_MAX,
              COD_TRAB_CONV = v_rCur.COD_TRAB_CONV,
              EST_CONV_CLI = v_rCur.EST_CONV_CLI,
              FEC_MOD_CLI_CONV = SYSDATE,
              USU_MOD_CLI_CONV = g_vIdUsu
          WHERE COD_CONVENIO = v_rCur.COD_CONVENIO
              AND COD_CLI = v_rCur.COD_CLI;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_CON_CLI_CONV SET FEC_PROCESO = SYSDATE
          WHERE COD_CONVENIO = v_rCur.COD_CONVENIO
              AND COD_CLI = v_rCur.COD_CLI;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.COD_CLI||', Cliente por Convenio no actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.COD_CLI||' ERROR NO CONTROLADO AL ACTUALIZAR CLIENTES POR CONVENIOS',err_msg);
  END;
  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_MAE_PROV_SAP(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CE_MAE_PROV
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DE LOS PROVEEDORES DE QA
      INSERT INTO CE_MAE_PROV  (COD_SAP,
                                DESC_RAZON_SOCIAL,
                                DESC_RUC,
                                USU_CREA_MAE_PROV
                                )
        SELECT COD_SAP,
               DESC_RAZON_SOCIAL,
               DESC_RUC,
               g_vIdUsu
        FROM T_CE_MAE_PROV
        WHERE COD_SAP = v_rCur.COD_SAP
        AND   DESC_RUC = v_rCur.DESC_RUC;
        -- ACTUALIZACION DE FECHA PROCESO
        UPDATE T_CE_MAE_PROV SET FEC_PROCESO = SYSDATE
        WHERE COD_SAP = v_rCur.COD_SAP
        AND   DESC_RUC = v_rCur.DESC_RUC;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CE_MAE_PROV
          SET COD_SAP = v_rCur.COD_SAP,
              DESC_RAZON_SOCIAL = v_rCur.DESC_RAZON_SOCIAL,
              DESC_RUC = v_rCur.DESC_RUC,
              FEC_MOD_MAE_PROV = SYSDATE,
              USU_MOD_MAE_PRO = g_vIdUsu
        WHERE COD_SAP = v_rCur.COD_SAP
        AND   DESC_RUC = v_rCur.DESC_RUC;
          -- ACTUALIZACION DE FECHA PROCESO
        UPDATE T_CE_MAE_PROV SET FEC_PROCESO = SYSDATE
        WHERE COD_SAP = v_rCur.COD_SAP
        AND   DESC_RUC = v_rCur.DESC_RUC;
        COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.COD_SAP||', Codigo SAP de Proveedor no Actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,'',v_rCur.COD_SAP||' ERROR NO CONTROLADO AL ACTUALIZAR CODIGOS SAP DE PROVEEDORES',err_msg);
  END;

  /****************************************************************************/

  PROCEDURE VIAJ_ACTUALIZA_MAE_SERVICIO(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CE_SERVICIO
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DE LOS SERVICIOS
      INSERT INTO CE_SERVICIO  (COD_SERVICIO,
                                DESC_SERVICIO,
                                EST_SERVICIO,
                                TIP_SERVICIO,
                                DESC_CUENTA_2,
                                USU_CREA_MAE_SERV)
        SELECT COD_SERVICIO,
               DESC_SERVICIO,
               EST_SERVICIO,
               TIP_SERVICIO,
               DESC_CUENTA_2,
               g_vIdUsu
        FROM   T_CE_SERVICIO
        WHERE  COD_SERVICIO = v_rCur.COD_SERVICIO;
        -- ACTUALIZACION DE FECHA PROCESO
        UPDATE T_CE_SERVICIO SET FEC_PROCESO = SYSDATE
        WHERE COD_SERVICIO = v_rCur.COD_SERVICIO;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CE_SERVICIO
          SET COD_SERVICIO = v_rCur.COD_SERVICIO,
              DESC_SERVICIO = v_rCur.DESC_SERVICIO,
              EST_SERVICIO = v_rCur.EST_SERVICIO,
              TIP_SERVICIO = v_rCur.TIP_SERVICIO,
              DESC_CUENTA_2 = v_rCur.DESC_CUENTA_2,
              USU_MOD_MAE_SERV  = g_vIdUsu,
              FEC_MOD_MAE_SERV = SYSDATE
        WHERE COD_SERVICIO = v_rCur.COD_SERVICIO;
          -- ACTUALIZACION DE FECHA PROCESO
        UPDATE T_CE_SERVICIO SET FEC_PROCESO = SYSDATE
        WHERE COD_SERVICIO = v_rCur.COD_SERVICIO;
        COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_SERVICIO,v_rCur.COD_SERVICIO||', Codigo SERVICIO no Actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_SERVICIO,v_rCur.COD_SERVICIO||' ERROR NO CONTROLADO AL ACTUALIZAR CODIGO DE SERVICIO',err_msg);
  END;

  /****************************************************************************/

  PROCEDURE VIAJ_ACTUALIZA_MAE_PROVEEDORES(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CE_PROVEEDOR
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DE LOS PROVEEDORES
      INSERT INTO CE_PROVEEDOR  (COD_PROVEEDOR,
                                 DESC_CORTA_PROVEEDOR,
                                 DESC_PROVEEDOR,
                                 RUC_PROVEEDOR,
                                 EST_PROVEEDOR,
                                 USU_CREA_CE_PROVEEDOR)
        SELECT COD_PROVEEDOR,
               DESC_CORTA_PROVEEDOR,
               DESC_PROVEEDOR,
               RUC_PROVEEDOR,
               EST_PROVEEDOR,
               g_vIdUsu
        FROM   T_CE_PROVEEDOR
        WHERE  COD_PROVEEDOR = v_rCur.COD_PROVEEDOR;
        -- ACTUALIZACION DE FECHA PROCESO
        UPDATE T_CE_PROVEEDOR SET FEC_PROCESO = SYSDATE
        WHERE COD_PROVEEDOR = v_rCur.COD_PROVEEDOR;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CE_PROVEEDOR
          SET COD_PROVEEDOR = v_rCur.COD_PROVEEDOR,
              DESC_CORTA_PROVEEDOR = v_rCur.DESC_CORTA_PROVEEDOR,
              DESC_PROVEEDOR = v_rCur.DESC_PROVEEDOR,
              RUC_PROVEEDOR = v_rCur.RUC_PROVEEDOR,
              EST_PROVEEDOR = v_rCur.EST_PROVEEDOR,
              USU_MOD_CE_PROVEEDOR  = g_vIdUsu,
              FEC_MOD_CE_PROVEEDOR = SYSDATE
        WHERE COD_PROVEEDOR = v_rCur.COD_PROVEEDOR;
          -- ACTUALIZACION DE FECHA PROCESO
        UPDATE T_CE_PROVEEDOR SET FEC_PROCESO = SYSDATE
        WHERE COD_PROVEEDOR = v_rCur.COD_PROVEEDOR;
        COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROVEEDOR,v_rCur.COD_PROVEEDOR||', Codigo PROVEEDOR no Actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_PROVEEDOR,v_rCur.COD_PROVEEDOR||' ERROR NO CONTROLADO AL ACTUALIZAR CODIGO DE PROVEEDOR',err_msg);
  END;

  /****************************************************************************/
  PROCEDURE VIAJ_ACTUALIZA_REL_PROV_SERV(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_CE_SERVICIO_PROVEEDOR
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      BEGIN
        --INSERTA DATOS DE LOS PROVEEDORES
      INSERT INTO CE_SERVICIO_PROVEEDOR  (COD_SERVICIO,
                                          COD_PROVEEDOR,
                                          EST_SERVICIO_PROVEEDOR,
                                          USU_CREA_SERVICIO_PROVEEDOR)
        SELECT COD_SERVICIO,
               COD_PROVEEDOR,
               EST_SERVICIO_PROVEEDOR,
               g_vIdUsu
        FROM   T_CE_SERVICIO_PROVEEDOR
        WHERE  COD_PROVEEDOR = v_rCur.COD_PROVEEDOR
        AND    COD_SERVICIO = v_rCur.COD_SERVICIO;
        -- ACTUALIZACION DE FECHA PROCESO
        UPDATE T_CE_SERVICIO_PROVEEDOR SET FEC_PROCESO = SYSDATE
        WHERE COD_PROVEEDOR = v_rCur.COD_PROVEEDOR
        AND   COD_SERVICIO = v_rCur.COD_SERVICIO;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE CE_SERVICIO_PROVEEDOR
          SET COD_PROVEEDOR = v_rCur.COD_PROVEEDOR,
              COD_SERVICIO = v_rCur.COD_SERVICIO,
              USU_MOD_SERVICIO_PROVEEDOR  = g_vIdUsu,
              FEC_MOD_SERVICIO_PROVEEDOR = SYSDATE
        WHERE COD_PROVEEDOR = v_rCur.COD_PROVEEDOR
        AND   COD_SERVICIO = v_rCur.COD_SERVICIO;
          -- ACTUALIZACION DE FECHA PROCESO
        UPDATE T_CE_SERVICIO_PROVEEDOR SET FEC_PROCESO = SYSDATE
        WHERE COD_PROVEEDOR = v_rCur.COD_PROVEEDOR
        AND   COD_SERVICIO = v_rCur.COD_SERVICIO;
        COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_SERVICIO,v_rCur.COD_PROVEEDOR||', Codigo PROVEEDOR - SERVICIO no Actualizado.',err_msg);
      END;
    END LOOP;

    v_gCantRegistrosActualizados := v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_SERVICIO,v_rCur.COD_PROVEEDOR||' ERROR NO CONTROLADO AL ACTUALIZAR CODIGO DE PROVEEDOR - SERVICIO',err_msg);
  END;

  /****************************************************************************/
  /****************************************************************************/
  /****************************************************************************/
  /****************************************************************************/
  /****************************************************************************/

  /****************************************************************************/
  PROCEDURE VIAJ_TRUNC_TABLAS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR DEFAULT NULL)
  AS
  BEGIN

      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_LGT_LAB';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_LGT_ACC_TERAP';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_LGT_PRINC_ACT';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_LGT_PROD';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_LGT_PROV';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_LGT_GRUPO_QS';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_LGT_COD_BARRA';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_LGT_ACC_TERAP_PROD';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_LGT_PRINC_ACT_PROD';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_CE_TIP_CAMBIO';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_PBL_LOCAL';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_LGT_GRUPO_REP_LOCAL';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_CE_CARGO';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_CE_MAE_TRAB';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_ADM_PROD_LOCAL';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_LGT_CAMBIO_PROD';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_CON_MAE_CLIENTE';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_CON_CLI_CONV';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_LGT_AGRUPA_PRODUCTO';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_VTA_CONSEJO';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_VTA_LOAD_CONSEJO';
      EXECUTE IMMEDIATE 'TRUNCATE TABLE T_CE_TIP_CAMBIO_SAP';

    COMMIT;
    --ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('PROCESO EXITOSO EN EL LOCAL: '||cCodLocal_in);
  EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('An error was encountered - '||cCodLocal_in||' '||SQLCODE||' -ERROR- '||SQLERRM);
  END;
  /****************************************************************************/
  PROCEDURE ENVIA_CORREO_TIPOCAMBIO(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR)
  AS

    ReceiverAddress VARCHAR2(30);
    CCReceiverAddress VARCHAR2(120) := NULL;

    mesg_body VARCHAR2(32767);

    v_vDescLocal VARCHAR2(120);
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL,
			MAIL_LOCAL,
			COD_CIA
        INTO v_vDescLocal,
			 ReceiverAddress,
			 vCodCia
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

    --CREAR CUERPO MENSAJE;
      mesg_body := mesg_body||'<LI><B>' || 'VENTA: '|| FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,null,'V') ||'</B></LI>'  ;
      mesg_body := mesg_body||'<LI><B>' || 'COMPRA: '|| FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,null,'C')  ||'</B></LI>'  ;

    --ENVIA MAIL
      FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                            ReceiverAddress,
                            'TIPO DE CAMBIO VIGENTE: '||v_vDescLocal,
                            'T.C.',
                            mesg_body,
                            CCReceiverAddress,
                            FARMA_EMAIL.GET_EMAIL_SERVER,
                            true);

  END;
  /****************************************************************************/  
END;

/
