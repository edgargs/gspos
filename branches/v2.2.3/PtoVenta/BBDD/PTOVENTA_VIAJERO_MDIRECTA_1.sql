--------------------------------------------------------
--  DDL for Package Body PTOVENTA_VIAJERO_MDIRECTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_VIAJERO_MDIRECTA" AS

  PROCEDURE VIAJ_ACTUALIZA_ORDEN_COMPRA(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_LGT_OC_CAB
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_OC_CAB.COD_OC%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      DBMS_OUTPUT.put_line(i);
      BEGIN
        INSERT INTO LGT_OC_CAB(
          COD_GRUPO_CIA,
          COD_CIA,
          COD_LOCAL,
          COD_OC,
          COD_PROV,
          COD_FORMA_PAGO,
          DESC_FORMA_PAGO,
          FEC_VENC_OC,
          CANT_DIAS,
          FEC_PROG_ENTREGA,
          FEC_RECEPCION,
          TIP_MONEDA,
          CANT_ITEMS,
          PORC_IGV_COMP_PAGO,
          EST_OC_CAB,
          VAL_TOTAL_OC_CAB,
          TIP_ORIGEN_OC,
          FEC_CREA_OC,
          FEC_ANU_OC,
          IMP_AFECT,
          IMP_INAFECT,
          FEC_INI,
          PROC_OC_CAB,
          TIP_COMP_CAB,
          NUM_DOC_CAB,
          SER_DOC_CAB,
          VAL_PAR_TOTAL_OC_CAB,
          VAL_DIF_TOTAL,
          USU_CREA,
          FEC_CREA)
        VALUES(v_rCur.COD_GRUPO_CIA,
              v_rCur.COD_CIA,
              v_rCur.COD_LOCAL,
              v_rCur.COD_OC,
              v_rCur.COD_PROV,
              v_rCur.COD_FORMA_PAGO,
              v_rCur.DESC_FORMA_PAGO,
              v_rCur.FEC_VENC_OC,
              v_rCur.CANT_DIAS,
              v_rCur.FEC_PROG_ENTREGA,
              v_rCur.FEC_RECEPCION,
              v_rCur.TIP_MONEDA,
              v_rCur.CANT_ITEMS,
              v_rCur.PORC_IGV_COMP_PAGO,
              v_rCur.EST_OC_CAB,
              v_rCur.VAL_TOTAL_OC_CAB,
              v_rCur.TIP_ORIGEN_OC,
              v_rCur.FEC_CREA_OC,
              v_rCur.FEC_ANU_OC,
              v_rCur.IMP_AFECT,
              v_rCur.IMP_INAFECT,
              v_rCur.FEC_INI,
              v_rCur.PROC_OC_CAB,
              v_rCur.TIP_COMP_CAB,
              v_rCur.NUM_DOC_CAB,
              v_rCur.SER_DOC_CAB,
              v_rCur.VAL_PAR_TOTAL_OC_CAB,
              v_rCur.VAL_DIF_TOTAL,
                g_vIdUsu,
                SYSDATE
                );

        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_OC_CAB SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
            AND COD_CIA = v_rCur.COD_CIA
            AND COD_LOCAL = v_rCur.COD_LOCAL
            AND COD_OC = v_rCur.COD_OC;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          SELECT COD_OC INTO v_cCod
          FROM LGT_OC_CAB
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
            AND COD_CIA = v_rCur.COD_CIA
            AND COD_LOCAL = v_rCur.COD_LOCAL
            AND COD_OC = v_rCur.COD_OC FOR UPDATE;

          UPDATE LGT_OC_CAB
          SET COD_PROV = v_rCur.COD_PROV,
              COD_FORMA_PAGO = v_rCur.COD_FORMA_PAGO,
              DESC_FORMA_PAGO = v_rCur.DESC_FORMA_PAGO,
              FEC_VENC_OC = v_rCur.FEC_VENC_OC,
              CANT_DIAS = v_rCur.CANT_DIAS,
              FEC_PROG_ENTREGA = v_rCur.FEC_PROG_ENTREGA,
              FEC_RECEPCION = v_rCur.FEC_RECEPCION,
              TIP_MONEDA = v_rCur.TIP_MONEDA,
              CANT_ITEMS = v_rCur.CANT_ITEMS,
              PORC_IGV_COMP_PAGO = v_rCur.PORC_IGV_COMP_PAGO,
              EST_OC_CAB = v_rCur.EST_OC_CAB,
              VAL_TOTAL_OC_CAB = v_rCur.VAL_TOTAL_OC_CAB,
              TIP_ORIGEN_OC = v_rCur.TIP_ORIGEN_OC,
              FEC_CREA_OC = v_rCur.FEC_CREA_OC,
              FEC_ANU_OC = v_rCur.FEC_ANU_OC,
              IMP_AFECT = v_rCur.IMP_AFECT,
              IMP_INAFECT = v_rCur.IMP_INAFECT,
              FEC_INI = v_rCur.FEC_INI,
              PROC_OC_CAB = v_rCur.PROC_OC_CAB,
              TIP_COMP_CAB = v_rCur.TIP_COMP_CAB,
              NUM_DOC_CAB = v_rCur.NUM_DOC_CAB,
              SER_DOC_CAB = v_rCur.SER_DOC_CAB,
              VAL_PAR_TOTAL_OC_CAB = v_rCur.VAL_PAR_TOTAL_OC_CAB,
              VAL_DIF_TOTAL = v_rCur.VAL_DIF_TOTAL,
              USU_MOD = g_vIdUsu,
              FEC_MOD = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
            AND COD_CIA = v_rCur.COD_CIA
            AND COD_LOCAL = v_rCur.COD_LOCAL
            AND COD_OC = v_rCur.COD_OC;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_OC_CAB SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
            AND COD_CIA = v_rCur.COD_CIA
            AND COD_LOCAL = v_rCur.COD_LOCAL
            AND COD_OC = v_rCur.COD_OC;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_OC,v_rCur.COD_OC||', Orden Compra no actualizado',err_msg);
          
      END;
    END LOOP;
    PTOVENTA_VIAJERO.v_gCantRegistrosActualizados := PTOVENTA_VIAJERO.v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_OC,v_rCur.COD_OC||' ERROR NO CONTROLADO AL ACTUALIZAR ORDEN_COMPRA',err_msg);
  END;
  
  PROCEDURE VIAJ_ACTUALIZA_ORDEN_DET(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_LGT_OC_DET
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod LGT_OC_DET.COD_OC%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      DBMS_OUTPUT.put_line(i);
      BEGIN
        INSERT INTO LGT_OC_DET(
          COD_GRUPO_CIA,
          COD_CIA,
          COD_LOCAL,
          COD_OC,
          SEC_DET_NOTA_ES,
          COD_PROD,
          CANT_RECEP,
          CANT_PED,
          FEC_PROCESO_SAP,
          FEC_PROCESO_CE,
          PREC_TOTAL,
          PREC_PROD,
          DET_IGV_PROD,
          USU_CREA_OC_DET,
          FEC_CREA_OC_DET
          )
        VALUES(v_rCur.COD_GRUPO_CIA,
                v_rCur.COD_CIA,
                v_rCur.COD_LOCAL,
                v_rCur.COD_OC,
                v_rCur.SEC_DET_NOTA_ES,
                v_rCur.COD_PROD,
                v_rCur.CANT_RECEP,
                v_rCur.CANT_PED,
                v_rCur.FEC_PROCESO_SAP,
                v_rCur.FEC_PROCESO_CE,
                v_rCur.PREC_TOTAL,
                v_rCur.PREC_PROD,
                v_rCur.DET_IGV_PROD,
                g_vIdUsu,
                SYSDATE
                );

        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_OC_DET SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
            AND COD_CIA = v_rCur.COD_CIA
            AND COD_LOCAL = v_rCur.COD_LOCAL
            AND COD_OC = v_rCur.COD_OC
            AND SEC_DET_NOTA_ES = v_rCur.SEC_DET_NOTA_ES;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          SELECT COD_OC INTO v_cCod
          FROM LGT_OC_DET
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
            AND COD_CIA = v_rCur.COD_CIA
            AND COD_LOCAL = v_rCur.COD_LOCAL
            AND COD_OC = v_rCur.COD_OC 
            AND SEC_DET_NOTA_ES = v_rCur.SEC_DET_NOTA_ES FOR UPDATE;

          UPDATE LGT_OC_DET
          SET COD_PROD = v_rCur.COD_PROD,
              CANT_RECEP = v_rCur.CANT_RECEP,
              CANT_PED = v_rCur.CANT_PED,
              FEC_PROCESO_SAP = v_rCur.FEC_PROCESO_SAP,
              FEC_PROCESO_CE = v_rCur.FEC_PROCESO_CE,
              PREC_TOTAL = v_rCur.PREC_TOTAL,
              PREC_PROD = v_rCur.PREC_PROD,
              DET_IGV_PROD = v_rCur.DET_IGV_PROD,
              USU_MOD_OC_DET = g_vIdUsu,
              FEC_MOD_OC_DET = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
            AND COD_CIA = v_rCur.COD_CIA
            AND COD_LOCAL = v_rCur.COD_LOCAL
            AND COD_OC = v_rCur.COD_OC
            AND SEC_DET_NOTA_ES = v_rCur.SEC_DET_NOTA_ES;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_LGT_OC_DET SET FEC_PROCESO = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCur.COD_GRUPO_CIA
            AND COD_CIA = v_rCur.COD_CIA
            AND COD_LOCAL = v_rCur.COD_LOCAL
            AND COD_OC = v_rCur.COD_OC
            AND SEC_DET_NOTA_ES = v_rCur.SEC_DET_NOTA_ES;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_OC,v_rCur.SEC_DET_NOTA_ES||', Orden Compra-Detalle no actualizado',err_msg);
          
      END;
    END LOOP;
    PTOVENTA_VIAJERO.v_gCantRegistrosActualizados := PTOVENTA_VIAJERO.v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_OC,v_rCur.SEC_DET_NOTA_ES||' ERROR NO CONTROLADO AL ACTUALIZAR ORDEN_COMPRA_DET',err_msg);
  END;  
END PTOVENTA_VIAJERO_MDIRECTA;

/
