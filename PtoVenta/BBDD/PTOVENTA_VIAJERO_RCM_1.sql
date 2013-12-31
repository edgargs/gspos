--------------------------------------------------------
--  DDL for Package Body PTOVENTA_VIAJERO_RCM
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_VIAJERO_RCM" AS

  PROCEDURE VIAJ_ACTUALIZA_UNIDAD_MEDIDA(cCodLocal_in IN CHAR)
  AS
    CURSOR cur IS
      SELECT *
      FROM T_RCM_UNIDAD_MEDIDA
      WHERE FEC_PROCESO IS NULL;
    v_rCur cur%ROWTYPE;

    i NUMBER(7):=0;

    v_cCod RCM_UNIDAD_MEDIDA.COD_UNIDAD_MEDIDA%TYPE;
    err_msg VARCHAR2(250);
  BEGIN
    FOR v_rCur IN cur
    LOOP
      i:=i+1;
      DBMS_OUTPUT.put_line(i);
      BEGIN
        INSERT INTO RCM_UNIDAD_MEDIDA(COD_UNIDAD_MEDIDA,
            DESC_UNIDAD_MEDIDA,
            ABREV_UNIDAD_MEDIDA,
            EST_UNIDAD_MEDIDA,
            USU_CREA,
            FEC_CREA)
        VALUES(v_rCur.COD_UNIDAD_MEDIDA,
                v_rCur.DESC_UNIDAD_MEDIDA,
                v_rCur.ABREV_UNIDAD_MEDIDA,
                v_rCur.EST_UNIDAD_MEDIDA,
                g_vIdUsu,
                SYSDATE
                );

        -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_RCM_UNIDAD_MEDIDA SET FEC_PROCESO = SYSDATE
          WHERE COD_UNIDAD_MEDIDA = v_rCur.COD_UNIDAD_MEDIDA;
        COMMIT;
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          SELECT COD_UNIDAD_MEDIDA INTO v_cCod
          FROM RCM_UNIDAD_MEDIDA
          WHERE COD_UNIDAD_MEDIDA = v_rCur.COD_UNIDAD_MEDIDA FOR UPDATE;

          UPDATE RCM_UNIDAD_MEDIDA
          SET DESC_UNIDAD_MEDIDA = v_rCur.DESC_UNIDAD_MEDIDA,
              ABREV_UNIDAD_MEDIDA = v_rCur.ABREV_UNIDAD_MEDIDA,
              EST_UNIDAD_MEDIDA = v_rCur.EST_UNIDAD_MEDIDA,
              USU_MOD = g_vIdUsu,
              FEC_MOD = SYSDATE
          WHERE COD_UNIDAD_MEDIDA = v_rCur.COD_UNIDAD_MEDIDA;
          -- ACTUALIZACION DE FECHA PROCESO
          UPDATE T_RCM_UNIDAD_MEDIDA SET FEC_PROCESO = SYSDATE
          WHERE COD_UNIDAD_MEDIDA = v_rCur.COD_UNIDAD_MEDIDA;
          COMMIT;
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_UNIDAD_MEDIDA,v_rCur.DESC_UNIDAD_MEDIDA||', Unidad Medida no actualizado',err_msg);
          
      END;
    END LOOP;
    PTOVENTA_VIAJERO.v_gCantRegistrosActualizados := PTOVENTA_VIAJERO.v_gCantRegistrosActualizados + i;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 240);
      PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_UNIDAD_MEDIDA,v_rCur.DESC_UNIDAD_MEDIDA||' ERROR NO CONTROLADO AL ACTUALIZAR UNIDAD_MEDIDA',err_msg);
  END;
  
    PROCEDURE VIAJ_ACTUALIZA_FORMA_FARMAC(cCodLocal_in IN CHAR)
    IS
        CURSOR cur IS
          SELECT *
          FROM T_RCM_FORMA_FARMAC
          WHERE FEC_PROCESO IS NULL;
        v_rCur cur%ROWTYPE;
    
        i NUMBER(7):=0;
    
        v_cCod RCM_FORMA_FARMAC.COD_FORMA_FARMAC%TYPE;
        err_msg VARCHAR2(250);    
    BEGIN
        FOR v_rCur IN cur
        LOOP
          i:=i+1;
          BEGIN
            INSERT INTO RCM_FORMA_FARMAC(COD_FORMA_FARMAC,
                DESC_FORMA_FARMAC,
                EST_FORMA,
                USU_CREA,
                FEC_CREA,
                COD_EQUI,
                IND_MULT,
                VAL_PREC,
                COD_UNIDAD_MEDIDA)
            VALUES(v_rCur.COD_FORMA_FARMAC,
                    v_rCur.DESC_FORMA_FARMAC,
                    v_rCur.EST_FORMA,
                    g_vIdUsu,
                    SYSDATE,
                    v_rCur.COD_EQUI,
                    v_rCur.IND_MULT,
                    v_rCur.VAL_PREC,
                    v_rCur.COD_UNIDAD_MEDIDA
                    );
    
            -- ACTUALIZACION DE FECHA PROCESO
              UPDATE T_RCM_FORMA_FARMAC SET FEC_PROCESO = SYSDATE
              WHERE COD_FORMA_FARMAC = v_rCur.COD_FORMA_FARMAC;
            COMMIT;
          EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN
              SELECT COD_FORMA_FARMAC INTO v_cCod
              FROM RCM_FORMA_FARMAC
              WHERE COD_FORMA_FARMAC = v_rCur.COD_FORMA_FARMAC FOR UPDATE;
    
              UPDATE RCM_FORMA_FARMAC
              SET DESC_FORMA_FARMAC = v_rCur.DESC_FORMA_FARMAC,
                  EST_FORMA = v_rCur.EST_FORMA,
                  COD_EQUI = v_rCur.COD_EQUI,
                  IND_MULT = v_rCur.IND_MULT,
                  VAL_PREC = v_rCur.VAL_PREC,
                  COD_UNIDAD_MEDIDA = v_rCur.COD_UNIDAD_MEDIDA,
                  USU_MOD = g_vIdUsu,
                  FEC_MOD = SYSDATE
              WHERE COD_FORMA_FARMAC = v_rCur.COD_FORMA_FARMAC;
              -- ACTUALIZACION DE FECHA PROCESO
              UPDATE T_RCM_FORMA_FARMAC SET FEC_PROCESO = SYSDATE
              WHERE COD_FORMA_FARMAC = v_rCur.COD_FORMA_FARMAC;
              COMMIT;
            WHEN OTHERS THEN
              ROLLBACK;
              err_msg := SUBSTR(SQLERRM, 1, 240);
              PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_FORMA_FARMAC,v_rCur.DESC_FORMA_FARMAC||', Forma Farmaceutica no actualizado',err_msg);
              
          END;
        END LOOP;
        PTOVENTA_VIAJERO.v_gCantRegistrosActualizados := PTOVENTA_VIAJERO.v_gCantRegistrosActualizados + i;
      EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_FORMA_FARMAC,v_rCur.DESC_FORMA_FARMAC||' ERROR NO CONTROLADO AL ACTUALIZAR FORMA FARMACEUTICA',err_msg);
    END;  
    
    PROCEDURE VIAJ_ACTUALIZA_INSUMO(cCodLocal_in IN CHAR)
    IS
        CURSOR cur IS
          SELECT *
          FROM T_RCM_INSUMO
          WHERE FEC_PROCESO IS NULL;
        v_rCur cur%ROWTYPE;
    
        i NUMBER(7):=0;
    
        v_cCod RCM_INSUMO.COD_INSUMO%TYPE;
        err_msg VARCHAR2(250);    
    BEGIN
        FOR v_rCur IN cur
        LOOP
          i:=i+1;
          BEGIN
            INSERT INTO RCM_INSUMO(COD_INSUMO,
                DESC_INSUMO,
                COD_UNIDAD_MEDIDA,
                VAL_PREC_VIG,
                EST_INSUMO,
                COD_EQUI,
                USU_CREA,
                FEC_CREA)
            VALUES(v_rCur.COD_INSUMO,
                    v_rCur.DESC_INSUMO,
                    v_rCur.COD_UNIDAD_MEDIDA,
                    v_rCur.VAL_PREC_VIG,
                    v_rCur.EST_INSUMO,
                    v_rCur.COD_EQUI,
                    g_vIdUsu,
                    SYSDATE
                    );
    
            -- ACTUALIZACION DE FECHA PROCESO
              UPDATE T_RCM_INSUMO SET FEC_PROCESO = SYSDATE
              WHERE COD_INSUMO = v_rCur.COD_INSUMO;
            COMMIT;
          EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN
              SELECT COD_INSUMO INTO v_cCod
              FROM RCM_INSUMO
              WHERE COD_INSUMO = v_rCur.COD_INSUMO FOR UPDATE;
    
              UPDATE RCM_INSUMO
              SET DESC_INSUMO = v_rCur.DESC_INSUMO,
                  COD_UNIDAD_MEDIDA = v_rCur.COD_UNIDAD_MEDIDA,
                  VAL_PREC_VIG = v_rCur.VAL_PREC_VIG,
                  EST_INSUMO = v_rCur.EST_INSUMO,
                  COD_EQUI = v_rCur.COD_EQUI,
                  USU_MOD = g_vIdUsu,
                  FEC_MOD = SYSDATE
              WHERE COD_INSUMO = v_rCur.COD_INSUMO;
              -- ACTUALIZACION DE FECHA PROCESO
              UPDATE T_RCM_INSUMO SET FEC_PROCESO = SYSDATE
              WHERE COD_INSUMO = v_rCur.COD_INSUMO;
              COMMIT;
            WHEN OTHERS THEN
              ROLLBACK;
              err_msg := SUBSTR(SQLERRM, 1, 240);
              PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_INSUMO,v_rCur.DESC_INSUMO||', Insumo no actualizado',err_msg);
              
          END;
        END LOOP;
        PTOVENTA_VIAJERO.v_gCantRegistrosActualizados := PTOVENTA_VIAJERO.v_gCantRegistrosActualizados + i;
      EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_INSUMO,v_rCur.DESC_INSUMO||' ERROR NO CONTROLADO AL ACTUALIZAR INSUMO',err_msg);
    END;
    
    PROCEDURE VIAJ_ACTUALIZA_CONVERSION_UNID(cCodLocal_in IN CHAR)
    IS
        CURSOR cur IS
          SELECT *
          FROM T_RCM_CONVERSION_UNIDADES
          WHERE FEC_PROCESO IS NULL;
        v_rCur cur%ROWTYPE;
    
        i NUMBER(7):=0;
    
        v_cCod RCM_INSUMO.COD_INSUMO%TYPE;
        err_msg VARCHAR2(250);    
    BEGIN
        FOR v_rCur IN cur
        LOOP
          i:=i+1;
          BEGIN
            INSERT INTO RCM_CONVERSION_UNIDADES(cod_conversion,
                cod_unidad_orig,
                cod_unidad_conv,
                fac_conversion,
                est_conversion,
                desc_observacion, 
                USU_CREA,
                FEC_CREA)
            VALUES(v_rCur.cod_conversion,
                    v_rCur.cod_unidad_orig,
                    v_rCur.cod_unidad_conv,
                    v_rCur.fac_conversion,
                    v_rCur.est_conversion,
                    v_rCur.desc_observacion,
                    g_vIdUsu,
                    SYSDATE
                    );
    
            -- ACTUALIZACION DE FECHA PROCESO
              UPDATE T_RCM_CONVERSION_UNIDADES SET FEC_PROCESO = SYSDATE
              WHERE cod_conversion = v_rCur.cod_conversion;
            COMMIT;
          EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN
              --SELECT cod_conversion --INTO v_ccod_conversion
              --FROM RCM_CONVERSION_UNIDADES
              --WHERE cod_conversion = v_rCur.cod_conversion FOR UPDATE;
    
              UPDATE RCM_CONVERSION_UNIDADES
              SET cod_unidad_orig = v_rCur.cod_unidad_orig,
                  cod_unidad_conv = v_rCur.cod_unidad_conv,
                  fac_conversion = v_rCur.fac_conversion,
                  est_conversion = v_rCur.est_conversion,
                  desc_observacion = v_rCur.desc_observacion,
                  USU_MOD = g_vIdUsu,
                  FEC_MOD = SYSDATE
              WHERE cod_conversion = v_rCur.cod_conversion;
              
              -- ACTUALIZACION DE FECHA PROCESO
              UPDATE T_RCM_CONVERSION_UNIDADES SET FEC_PROCESO = SYSDATE
              WHERE cod_conversion = v_rCur.cod_conversion;
              COMMIT;
            WHEN OTHERS THEN
              ROLLBACK;
              err_msg := SUBSTR(SQLERRM, 1, 240);
              --PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_INSUMO,v_rCur.DESC_INSUMO||', Insumo no actualizado',err_msg);
              
          END;
        END LOOP;
        --PTOVENTA_VIAJERO.v_gCantRegistrosActualizados := PTOVENTA_VIAJERO.v_gCantRegistrosActualizados + i;
      EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          --PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_INSUMO,v_rCur.DESC_INSUMO||' ERROR NO CONTROLADO AL ACTUALIZAR INSUMO',err_msg);
    END;
    
    PROCEDURE VIAJ_ACTUALIZA_ORDEN_PREPARADO(cCodLocal_in IN CHAR)
    IS
        CURSOR cur IS
          SELECT *
          FROM T_RCM_ORDEN_PREPARADO
          WHERE FEC_PROCESO IS NULL;
        v_rCur cur%ROWTYPE;
    
        i NUMBER(7):=0;
    
        v_cCod RCM_INSUMO.COD_INSUMO%TYPE;
        err_msg VARCHAR2(250);    
    BEGIN
        FOR v_rCur IN cur
        LOOP
          i:=i+1;
          BEGIN
            INSERT INTO RCM_ORDEN_PREPARADO(tnumeorpr, 
                                          tfechorpr,         tcoditipoorpr, 
                                          tcodiestaorpr,     tfechesta, 
                                          tcodiloca,         tcodilocadest, 
                                          tcodiafil,         tcodiclie, 
                                          tobseorpr,         timpoafec, 
                                          timpoinaf,         timpoimpu, 
                                          timpotota,         tindiurge, 
                                          tcodiprod,         tcantprod, 
                                          tcorrrect,         tcorrprep, 
                                          ttipocamb,         tcodilocacmpr, 
                                          tcoditipocmpr,     tnumecmpr, 
                                          tfechcmpr,         tcodilocaguia, 
                                          tnumeguiavent,     tcodilocatran, 
                                          tnumeguiatran,     tcodipers, 
                                          tpersoper,         tperssupe, 
                                          tpersasig,         tpersejec, 
                                          tglosceme,         tnombclie, 
                                          tcodicia,          
                                          --tusuacrea, 
                                          --tfechcrea,         
                                          --tusuamodi, 
                                          --tfechmodi,         
                                          tindistdr, 
                                          tnumecole,         tcodidocuiden, 
                                          tnumedocuiden,     tnumetel, 
                                          tobsedomi,         trefedomi, 
                                          tidcodafil,        tidcodclie, 
                                          tidcodprod,        tcodiformpago, 
                                          tnumedias,         timpodscg, 
                                          timpocost,         tnumereceloca, 
                                          tnumepreploca,     tcodilocacmprorig, 
                                          tcoditipocmprorig, tnumecmprorig, 
                                          tfechcmprorig,     tcodilocaguiaorig, 
                                          tnumeguiaventorig, tnumeterm, 
                                          tformfarm,         tcontenva, 
                                          tcantenva,         tindregi, 
                                          tindimues,         tindicnvr, 
                                          tindirece,         tcoditioporig, 
                                          tindiimpr,         tindicopi, 
                                          timpocostdscg,     tindiformpago, 
                                          tindiproc,         tfechprep, 
                                          tfechanul,         --timagrece, 
                                          fec_proceso_sap,   tcodimifa,
                                          tusuacrea, 
                                          tfechcrea)
            VALUES(v_rCur.tnumeorpr, 
                    v_rCur.tfechorpr,         v_rCur.tcoditipoorpr, 
                    v_rCur.tcodiestaorpr,     v_rCur.tfechesta, 
                    v_rCur.tcodiloca,         v_rCur.tcodilocadest, 
                    v_rCur.tcodiafil,         v_rCur.tcodiclie, 
                    v_rCur.tobseorpr,         v_rCur.timpoafec, 
                    v_rCur.timpoinaf,         v_rCur.timpoimpu, 
                    v_rCur.timpotota,         v_rCur.tindiurge, 
                    v_rCur.tcodiprod,         v_rCur.tcantprod, 
                    v_rCur.tcorrrect,         v_rCur.tcorrprep, 
                    v_rCur.ttipocamb,         v_rCur.tcodilocacmpr, 
                    v_rCur.tcoditipocmpr,     v_rCur.tnumecmpr, 
                    v_rCur.tfechcmpr,         v_rCur.tcodilocaguia, 
                    v_rCur.tnumeguiavent,     v_rCur.tcodilocatran, 
                    v_rCur.tnumeguiatran,     v_rCur.tcodipers, 
                    v_rCur.tpersoper,         v_rCur.tperssupe, 
                    v_rCur.tpersasig,         v_rCur.tpersejec, 
                    v_rCur.tglosceme,         v_rCur.tnombclie, 
                    v_rCur.tcodicia,          
                    --v_rCur.tusuacrea, 
                    --v_rCur.tfechcrea,         
                    --v_rCur.tusuamodi, 
                    --v_rCur.tfechmodi,         
                    v_rCur.tindistdr, 
                    v_rCur.tnumecole,         v_rCur.tcodidocuiden, 
                    v_rCur.tnumedocuiden,     v_rCur.tnumetel, 
                    v_rCur.tobsedomi,         v_rCur.trefedomi, 
                    v_rCur.tidcodafil,        v_rCur.tidcodclie, 
                    v_rCur.tidcodprod,        v_rCur.tcodiformpago, 
                    v_rCur.tnumedias,         v_rCur.timpodscg, 
                    v_rCur.timpocost,         v_rCur.tnumereceloca, 
                    v_rCur.tnumepreploca,     v_rCur.tcodilocacmprorig, 
                    v_rCur.tcoditipocmprorig, v_rCur.tnumecmprorig, 
                    v_rCur.tfechcmprorig,     v_rCur.tcodilocaguiaorig, 
                    v_rCur.tnumeguiaventorig, v_rCur.tnumeterm, 
                    v_rCur.tformfarm,         v_rCur.tcontenva, 
                    v_rCur.tcantenva,         v_rCur.tindregi, 
                    v_rCur.tindimues,         v_rCur.tindicnvr, 
                    v_rCur.tindirece,         v_rCur.tcoditioporig, 
                    v_rCur.tindiimpr,         v_rCur.tindicopi, 
                    v_rCur.timpocostdscg,     v_rCur.tindiformpago, 
                    v_rCur.tindiproc,         v_rCur.tfechprep, 
                    v_rCur.tfechanul,         --timagrece, 
                    v_rCur.fec_proceso_sap,   v_rCur.tcodimifa,
                    v_rCur.tusuacrea,
                    v_rCur.tfechcrea
                    );
    
            -- ACTUALIZACION DE FECHA PROCESO
              UPDATE T_RCM_ORDEN_PREPARADO SET FEC_PROCESO = SYSDATE
              WHERE tnumeorpr = v_rCur.tnumeorpr;
            COMMIT;
          EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN
              --SELECT tnumeorpr INTO v_ctnumeorpr
              --FROM RCM_ORDEN_PREPARADO
              --WHERE tnumeorpr = v_rCur.tnumeorpr FOR UPDATE;
    
              UPDATE RCM_ORDEN_PREPARADO
              SET tfechorpr = v_rCur.tfechorpr, 
                  tcoditipoorpr = v_rCur.tcoditipoorpr, 
                  tcodiestaorpr = v_rCur.tcodiestaorpr, 
                  tfechesta = v_rCur.tfechesta, 
                  tcodiloca = v_rCur.tcodiloca, 
                  tcodilocadest = v_rCur.tcodilocadest, 
                  tcodiafil = v_rCur.tcodiafil, 
                  tcodiclie = v_rCur.tcodiclie, 
                  tobseorpr = v_rCur.tobseorpr, 
                  timpoafec = v_rCur.timpoafec, 
                  timpoinaf = v_rCur.timpoinaf, 
                  timpoimpu = v_rCur.timpoimpu, 
                  timpotota = v_rCur.timpotota, 
                  tindiurge = v_rCur.tindiurge, 
                  tcodiprod = v_rCur.tcodiprod, 
                  tcantprod = v_rCur.tcantprod, 
                  tcorrrect = v_rCur.tcorrrect, 
                  tcorrprep = v_rCur.tcorrprep, 
                  ttipocamb = v_rCur.ttipocamb, 
                  tcodilocacmpr = v_rCur.tcodilocacmpr, 
                  tcoditipocmpr = v_rCur.tcoditipocmpr, 
                  tnumecmpr = v_rCur.tnumecmpr, 
                  tfechcmpr = v_rCur.tfechcmpr, 
                  tcodilocaguia = v_rCur.tcodilocaguia, 
                  tnumeguiavent = v_rCur.tnumeguiavent, 
                  tcodilocatran = v_rCur.tcodilocatran, 
                  tnumeguiatran = v_rCur.tnumeguiatran, 
                  tcodipers = v_rCur.tcodipers, 
                  tpersoper = v_rCur.tpersoper, 
                  tperssupe = v_rCur.tperssupe, 
                  tpersasig = v_rCur.tpersasig, 
                  tpersejec = v_rCur.tpersejec, 
                  tglosceme = v_rCur.tglosceme, 
                  tnombclie = v_rCur.tnombclie, 
                  tcodicia = v_rCur.tcodicia, 
                  --tusuacrea = FI.tusuacrea, 
                  --tfechcrea = FI.tfechcrea, 
                  --tusuamodi = FI.tusuamodi, 
                  --tfechmodi = FI.tfechmodi, 
                  tindistdr = v_rCur.tindistdr, 
                  tnumecole = v_rCur.tnumecole, 
                  tcodidocuiden = v_rCur.tcodidocuiden, 
                  tnumedocuiden = v_rCur.tnumedocuiden, 
                  tnumetel = v_rCur.tnumetel, 
                  tobsedomi = v_rCur.tobsedomi, 
                  trefedomi = v_rCur.trefedomi, 
                  tidcodafil = v_rCur.tidcodafil, 
                  tidcodclie = v_rCur.tidcodclie, 
                  tidcodprod = v_rCur.tidcodprod, 
                  tcodiformpago = v_rCur.tcodiformpago, 
                  tnumedias = v_rCur.tnumedias, 
                  timpodscg = v_rCur.timpodscg, 
                  timpocost = v_rCur.timpocost, 
                  tnumereceloca = v_rCur.tnumereceloca, 
                  tnumepreploca = v_rCur.tnumepreploca, 
                  tcodilocacmprorig = v_rCur.tcodilocacmprorig, 
                  tcoditipocmprorig = v_rCur.tcoditipocmprorig, 
                  tnumecmprorig = v_rCur.tnumecmprorig, 
                  tfechcmprorig = v_rCur.tfechcmprorig, 
                  tcodilocaguiaorig = v_rCur.tcodilocaguiaorig, 
                  tnumeguiaventorig = v_rCur.tnumeguiaventorig, 
                  tnumeterm = v_rCur.tnumeterm, 
                  tformfarm = v_rCur.tformfarm, 
                  tcontenva = v_rCur.tcontenva, 
                  tcantenva = v_rCur.tcantenva, 
                  tindregi = v_rCur.tindregi, 
                  tindimues = v_rCur.tindimues, 
                  tindicnvr = v_rCur.tindicnvr, 
                  tindirece = v_rCur.tindirece, 
                  tcoditioporig = v_rCur.tcoditioporig, 
                  tindiimpr = v_rCur.tindiimpr, 
                  tindicopi = v_rCur.tindicopi, 
                  timpocostdscg = v_rCur.timpocostdscg, 
                  tindiformpago = v_rCur.tindiformpago, 
                  tindiproc = v_rCur.tindiproc, 
                  tfechprep = v_rCur.tfechprep, 
                  tfechanul = v_rCur.tfechanul, 
                  --RI.timagrece = FI.timagrece, 
                  fec_proceso_sap = v_rCur.fec_proceso_sap,  
                  tcodimifa = v_rCur.tcodimifa,
                  tusuamodi = 'AUX_RCM', 
                  tfechmodi = SYSDATE
              WHERE tnumeorpr = v_rCur.tnumeorpr;
              
              -- ACTUALIZACION DE FECHA PROCESO
              UPDATE T_RCM_ORDEN_PREPARADO SET FEC_PROCESO = SYSDATE
              WHERE tnumeorpr = v_rCur.tnumeorpr;
              COMMIT;
            WHEN OTHERS THEN
              ROLLBACK;
              err_msg := SUBSTR(SQLERRM, 1, 240);
              --PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_INSUMO,v_rCur.DESC_INSUMO||', Insumo no actualizado',err_msg);
              
          END;
        END LOOP;
        --PTOVENTA_VIAJERO.v_gCantRegistrosActualizados := PTOVENTA_VIAJERO.v_gCantRegistrosActualizados + i;
      EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK;
          err_msg := SUBSTR(SQLERRM, 1, 240);
          --PTOVENTA_VIAJERO.GRABA_LOG_VIAJERO(cCodLocal_in,v_rCur.COD_INSUMO,v_rCur.DESC_INSUMO||' ERROR NO CONTROLADO AL ACTUALIZAR INSUMO',err_msg);
    END;
    
END PTOVENTA_VIAJERO_RCM;

/
