--------------------------------------------------------
--  DDL for Package Body PTOVENTA_PROC_PACKS_CAMPS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_PROC_PACKS_CAMPS" is


  -- Function and procedure implementations
  /*------------------------------------------------------------------------------------------------------------------------------------
  GOAL : PROCESAR LOS PACKS RECIBIDOS DESDE APPS
  Ammedments:
  22-OCT-12       TCT       Create
  11-DIC-13       TCT       Add Fields para precio fijo

  --------------------------------------------------------------------------------------------------------------------------------------*/
  PROCEDURE SP_PROCESA_PACKS
  AS
   --vcCodLocAct CHAR(3);
  BEGIN

   -- 10.- SI VTA_PAQUETE NO EXISTE, INSERT
   -- 20.- SI SI VTA_PAQUETE EXISTE, UPDATE
   BEGIN
     MERGE INTO vta_paquete vpq
      USING T_vta_paquete TPQ
      ON (vpq.cod_paquete=TPQ.cod_paquete AND VPQ.COD_GRUPO_CIA=TPQ.COD_GRUPO_CIA)
      WHEN MATCHED THEN
         UPDATE SET vpq.usu_crea_paquete_promocion = tpq.usu_crea_paquete_promocion
               ,vpq.fec_crea_paquete_promocion     = tpq.fec_crea_paquete_promocion
               ,vpq.usu_mod_paquete_promocion      = tpq.usu_mod_paquete_promocion
               ,vpq.fec_mod_paquete_promocion      = tpq.fec_mod_paquete_promocion
      WHEN NOT MATCHED THEN
         INSERT VALUES(tpq.cod_grupo_cia,
                       tpq.cod_paquete,
                       tpq.usu_crea_paquete_promocion,
                       tpq.fec_crea_paquete_promocion,
                       tpq.usu_mod_paquete_promocion,
                       tpq.fec_mod_paquete_promocion);


   EXCEPTION
    WHEN OTHERS THEN
     RAISE_APPLICATION_ERROR(-20001,'ERROR,AL INSERTAR/ACTUALIZAR TABLA: VTA_PAQUETE...');
   END;

   -- 30.- SI vta_PROD_paquete NO EXISTE, INSERT
   -- 40.-  SI vta_PROD_paquete EXISTE, UPDATE
   BEGIN
     MERGE INTO vta_PROD_paquete PP
      USING T_vta_PROD_paquete TPP
      ON (pp.cod_paquete=TPP.cod_paquete AND PP.COD_GRUPO_CIA=TPP.COD_GRUPO_CIA AND PP.COD_PROD=TPP.COD_PROD)
      WHEN MATCHED THEN

         UPDATE SET PP.CANTIDAD              = TPP.CANTIDAD,
                    PP.PORC_DCTO             = TPP.PORC_DCTO,
                    PP.USU_CREA_PROD_PAQUETE = TPP.USU_CREA_PROD_PAQUETE,
                    PP.FEC_CREA_PROD_PAQUETE = TPP.FEC_CREA_PROD_PAQUETE,
                    PP.USU_MOD_PROD_PAQUETE  = TPP.USU_MOD_PROD_PAQUETE,
                    PP.FEC_MOD_PROD_PAQUETE  = TPP.FEC_MOD_PROD_PAQUETE,
                    PP.VAL_FRAC              = TPP.VAL_FRAC,
                    PP.prec_fijo             = TPP.prec_fijo,
                    PP.flg_modo              = TPP.flg_modo,
                    PP.imp_valor             = TPP.imp_valor,
                    PP.flg_tipo_valor        = TPP.flg_tipo_valor
      WHEN NOT MATCHED THEN
         INSERT (
                cod_grupo_cia,
                cod_paquete,
                cod_prod,
                cantidad,
                porc_dcto,
                usu_crea_prod_paquete,
                fec_crea_prod_paquete,
                usu_mod_prod_paquete,
                fec_mod_prod_paquete,
                val_frac,
                prec_fijo,
                flg_modo,
                imp_valor,
                flg_tipo_valor
                )
         VALUES(TPP.COD_GRUPO_CIA,
                       TPP.COD_PAQUETE,
                       TPP.COD_PROD,
                       TPP.CANTIDAD,
                       TPP.PORC_DCTO,
                       TPP.USU_CREA_PROD_PAQUETE,
                       TPP.FEC_CREA_PROD_PAQUETE,
                       TPP.USU_MOD_PROD_PAQUETE,
                       TPP.FEC_MOD_PROD_PAQUETE,
                       TPP.VAL_FRAC,
                       TPP.prec_fijo,
                       TPP.flg_modo,
                       TPP.imp_valor,
                       TPP.flg_tipo_valor
                       );


   EXCEPTION
    WHEN OTHERS THEN
     RAISE_APPLICATION_ERROR(-20001,'ERROR,AL INSERTAR/ACTUALIZAR TABLA: VTA_PROD_PAQUETE...');
   END;

   -- 50.- SI vta_PROMOCION NO EXISTE, INSERT
   -- 60.-  SI vta_PROMOCION EXISTE, UPDATE
   BEGIN
     MERGE INTO vta_PROMOCION VP
      USING T_vta_PROMOCION TVP
      ON (VP.COD_PROM=TVP.COD_PROM AND VP.COD_GRUPO_CIA=TVP.COD_GRUPO_CIA )
      WHEN MATCHED THEN
         UPDATE SET VP.DESC_CORTA_PROM      = TVP.DESC_CORTA_PROM,
                    VP.DESC_LARGA_PROM      = TVP.DESC_LARGA_PROM,
                    VP.COD_PAQUETE_1        = TVP.COD_PAQUETE_1,
                    VP.COD_PAQUETE_2        = TVP.COD_PAQUETE_2,
                    VP.ESTADO               = TVP.ESTADO,
                    VP.USU_CREA_PROMOCION   = TVP.USU_CREA_PROMOCION,
                    VP.FEC_CREA_PROMOCION   = TVP.FEC_CREA_PROMOCION,
                    VP.USU_MOD_PROMOCION    = TVP.USU_MOD_PROMOCION,
                    VP.FEC_MOD_PROMOCION    = TVP.FEC_MOD_PROMOCION,
                    VP.CANT_MAX_VTA         = TVP.CANT_MAX_VTA,
                    VP.DESC_LARGA_PROM_1    = TVP.DESC_LARGA_PROM_1,
                    VP.FEC_PROMOCION_INICIO = TVP.FEC_PROMOCION_INICIO,
                    VP.FEC_PROMOCION_FIN    = TVP.FEC_PROMOCION_FIN,
                    VP.DESCRIPCION          = TVP.DESCRIPCION,
                    VP.IND_DELIVERY         = TVP.IND_DELIVERY,
                    VP.cod_prom_btl         = TVP.cod_prom_btl

      WHEN NOT MATCHED THEN
         INSERT(
                cod_grupo_cia,
                cod_prom,
                desc_corta_prom,
                desc_larga_prom,
                cod_paquete_1,
                cod_paquete_2,
                estado,
                usu_crea_promocion,
                fec_crea_promocion,
                usu_mod_promocion,
                fec_mod_promocion,
                cant_max_vta,
                desc_larga_prom_1,
                fec_promocion_inicio,
                fec_promocion_fin,
                descripcion,
                ind_delivery,
                cod_prom_btl
               )
           VALUES(TVP.COD_GRUPO_CIA,
                       TVP.COD_PROM,
                       TVP.DESC_CORTA_PROM,
                       TVP.DESC_LARGA_PROM,
                       TVP.COD_PAQUETE_1,
                       TVP.COD_PAQUETE_2,
                       TVP.ESTADO,
                       TVP.USU_CREA_PROMOCION,
                       TVP.FEC_CREA_PROMOCION,
                       TVP.USU_MOD_PROMOCION,
                       TVP.FEC_MOD_PROMOCION,
                       TVP.CANT_MAX_VTA,
                       TVP.DESC_LARGA_PROM_1,
                       TVP.FEC_PROMOCION_INICIO,
                       TVP.FEC_PROMOCION_FIN,
                       TVP.DESCRIPCION,
                       TVP.IND_DELIVERY,
                       TVP.cod_prom_btl
                       );


   EXCEPTION
    WHEN OTHERS THEN
     RAISE_APPLICATION_ERROR(-20001,'ERROR,AL INSERTAR/ACTUALIZAR TABLA: VTA_PROMOCION...');
   END;

   -- 70.-  SI VTA_PROM_X_LOCAL NO EXISTE, INSERT
   -- 80.-  SI VTA_PROM_X_LOCAL EXISTE, UPDATE
   BEGIN
     MERGE INTO VTA_PROM_X_LOCAL PL
      USING T_VTA_PROM_X_LOCAL TPL
      ON (PL.COD_PROM=TPL.COD_PROM AND PL.COD_GRUPO_CIA=TPL.COD_GRUPO_CIA AND PL.COD_LOCAL=TPL.COD_LOCAL)
      WHEN MATCHED THEN
         UPDATE SET PL.ESTADO              = TPL.ESTADO,
                    PL.USU_CREA_PROM_LOCAL = TPL.USU_CREA_PROM_LOCAL,
                    PL.FEC_CREA_PROM_LOCAL = TPL.FEC_CREA_PROM_LOCAL,
                    PL.USU_MOD_PROM_LOCAL  = TPL.USU_MOD_PROM_LOCAL,
                    PL.FEC_MOD_PROM_LOCAL  = TPL.FEC_MOD_PROM_LOCAL,
                    PL.cod_cia             = TPL.cod_cia,
                    PL.cod_prom_btl        = TPL.cod_prom_btl

      WHEN NOT MATCHED THEN
         INSERT (
                  cod_grupo_cia,
                  cod_prom,
                  cod_local,
                  estado,
                  usu_crea_prom_local,
                  fec_crea_prom_local,
                  usu_mod_prom_local,
                  fec_mod_prom_local,
                  cod_cia,
                  cod_prom_btl
                )
         VALUES(TPL.cod_grupo_cia,
                        TPL.cod_prom,
                        TPL.cod_local,
                        TPL.estado,
                        TPL.usu_crea_prom_local,
                        TPL.fec_crea_prom_local,
                        TPL.usu_mod_prom_local,
                        TPL.fec_mod_prom_local,
                        TPL.cod_cia,
                        TPL.cod_prom_btl
                        );


   EXCEPTION
    WHEN OTHERS THEN
     RAISE_APPLICATION_ERROR(-20001,'ERROR,AL INSERTAR/ACTUALIZAR TABLA: VTA_PROM_X_LOCAL...');
   END;

   -- 300.- Elimina Productos de Pack, si es aplicable, sino no hace nada
   ptoventa_proc_packs_camps.sp_del_prods_pack;
   -- 400.- Asignar estado Actual a Promocion, si no esta en local tambien se desactiva
   ptoventa_proc_packs_camps.sp_asig_estado_prom;

   COMMIT;
   dbms_output.put_line('OK,al Cargar Promociones en Local');
   -- 410.- Grabar Log. de Exito
   ptoventa_proc_packs_camps.sp_graba_log('OK,al Cargar Promociones en Local','OK','PAQ');
  EXCEPTION
   WHEN OTHERS THEN
   ROLLBACK;
   dbms_output.put_line('ERROR,al Cargar Promociones en Local:'||SQLERRM);
   -- 410.- Grabar Log. de Error
   ptoventa_proc_packs_camps.sp_graba_log('ERROR,al Cargar Promociones en Local:'||substr(SQLERRM,1,150),'ERR','PAQ');
   RAISE;

  END;
/*-----------------------------------------------------------------------------------------------------------------------------------------
GOAL : Eliminar Productos (Emite,Regalo) que ya no figuran en el Paquete
DATE : 23-OCT-12
-------------------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_DEL_PRODS_PACK
AS
----------------------------------------- CURSOR CON PAQUETES Y PRODUCTOS A PROCESAR
 CURSOR crProdsPack IS
  SELECT pp.cod_grupo_cia,pp.cod_paquete,pp.cod_prod,pp.cantidad,pp.porc_dcto
  FROM T_vta_prod_paquete pp;

BEGIN

  ---- Productos a Eliminar, SE ASUME QUE LOS NUEVOS YA ESTAN INSERTTADOS Y LOS EXISTENTES ACTUALIZADOS

      FOR regProdPack IN crProdsPack LOOP
        BEGIN
          DELETE FROM  vta_prod_paquete vp
          WHERE vp.cod_prod IN (
                                 SELECT V1.cod_prod FROM(
                                     SELECT pp.cod_grupo_cia,pp.cod_paquete,pp.cod_prod
                                      FROM vta_prod_paquete pp
                                      WHERE pp.cod_paquete=regProdPack.Cod_Paquete
                                      MINUS
                                      SELECT tpp.cod_grupo_cia,tpp.cod_paquete,tpp.cod_prod
                                      FROM T_vta_prod_paquete tpp
                                      WHERE tpp.cod_paquete=regProdPack.Cod_Paquete)V1
                                  );
        EXCEPTION
        WHEN OTHERS THEN
         RAISE_APPLICATION_ERROR(-20001,'ERROR, AL ELIMINAR PRODUCTOS DE PACK:'||regProdPack.Cod_Paquete||', '||SQLERRM);
        END;
      END LOOP;


  COMMIT;
   DBMS_OUTPUT.put_line('OK, AL ELIMINAR PRODUCTOS DE PACKS');
  EXCEPTION
   WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.put_line('ERROR, AL ELIMINAR PRODUCTOS DE PACKS:'||SQLERRM);
    RAISE;

END;
/*-----------------------------------------------------------------------------------------------------------------------------------------
GOAL : ASIGNAR EL ESTADO DE LA PROMOCION
DATE : 23-OCT-12
------------------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_ASIG_ESTADO_PROM
AS
    ------------------------------ CURSOR CON PROMOCIONES A PROCESAR,se asume que la tabla:T_VTA_PROMOCION ya fue procesada
    CURSOR crProms IS
      SELECT tvp.cod_prom, tvp.estado
      FROM T_VTA_PROMOCION tvp;
    ------------------------------- Variables
    vcCodLocAct CHAR(3);
    vnCant NUMBER;
    vcEstProm CHAR(1):='I';         -- asume inactiva
BEGIN
  -- 10.- Determinar Codigo de Local Actual
  SELECT DISTINCT(I.Cod_Local) INTO vcCodLocAct
  FROM vta_impr_local I;

  -- 20.- Procesar cada Promocion
  FOR regProm IN crProms LOOP
   BEGIN
       -- 25.- SI TABLA:VTA_PROM_X_LOCAL NO CONTIENE NINGUN REGISTRO PARA LA PROMOCION,
       --      SIGNIFICA QUE SE ACTIVA EN TODA LA CADENA, SI NO ACTIVAR SOLO EN LOCALES QUE INDIQUE
       SELECT COUNT(*) INTO vnCant
       FROM VTA_PROM_X_LOCAL PL
       WHERE PL.COD_GRUPO_CIA='001'
         AND PL.COD_PROM= regProm.Cod_Prom;

       -- 30.- determinar si promocion existe en local
       IF vnCant>0 AND trim(regProm.Estado)='A' THEN
           vcEstProm:='I';
           IF ptoventa_proc_packs_camps.fn_activar_prom_local(regProm.Cod_Prom,vcCodLocAct,CC_PROM_PACK) THEN
             vcEstProm:='A';
           END IF;
           -- 40.- Asignar estado a Promocion
           UPDATE vta_promocion vp
           SET vp.estado=vcEstProm
           WHERE vp.cod_prom=regProm.Cod_Prom;
      END IF;

   EXCEPTION
    WHEN OTHERS THEN
     RAISE_APPLICATION_ERROR(-20001,'ERROR, AL ACTUALIZAR ESTADO DE PROM:'||regProm.Cod_Prom);
   END;
  END LOOP;

  COMMIT;
  DBMS_OUTPUT.put_line('OK, AL ACTUALIZAR ESTADO DE PROMOCIONES...');
EXCEPTION
 WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.put_line('ERROR, AL ACTUALIZAR ESTADO DE PROMOCIONES...'||SQLERRM);
  RAISE;


END;
/*-----------------------------------------------------------------------------------------------------------------------------------------
GOAL : DETERMINAR SI UNA PROMOCION o Campaña DEBE ESTAR ACTIVA EN UN LOCAL
DATE : 23-OCT-12
-------------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_ACTIVAR_PROM_LOCAL(cCodPromMF_in CHAR, cCodLocMF_in CHAR,cCodTipProm_in CHAR) RETURN BOOLEAN
IS
   vbRet BOOLEAN:=FALSE;
   vnCant NUMBER;
BEGIN
   vnCant:=0;
   IF  TRIM(upper(cCodTipProm_in))=CC_PROM_PACK   THEN
       SELECT count(*) INTO vnCant
       FROM VTA_PROM_X_LOCAL PL
       WHERE pl.cod_grupo_cia='001'
         AND pl.cod_prom=cCodPromMF_in
         AND pl.cod_local=cCodLocMF_in;
   ELSIF TRIM(upper(cCodTipProm_in))=CC_CAMP_AUT THEN
       SELECT count(*) INTO vnCant
       FROM VTA_CAMP_X_LOCAL CL
       WHERE CL.cod_grupo_cia='001'
         AND CL.COD_CAMP_CUPON=cCodPromMF_in
         AND CL.cod_local=cCodLocMF_in;
   END IF;

   IF  vnCant>=1 THEN
    vbRet:=TRUE;
   END IF;
   RETURN vbRet;
EXCEPTION
  WHEN OTHERS THEN
   RETURN FALSE;
   RAISE;

END;
 /*--------------------------------------------------------------------------------------------------------------------
 GOAL : Grabar Log de Proceso de Campañas y Promociones(Packs)
 DATE : 23-OCT-12
 AUTH :
 ----------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_GRABA_LOG(pvc_DescProc VARCHAR2,pvc_ResulProc varchar2,pcTipProm CHAR)
AS
  PRAGMA AUTONOMOUS_TRANSACTION;

  ------------------------------ CURSOR CON PROMOCIONES A PROCESAR,se asume que la tabla:T_VTA_PROMOCION ya fue procesada
  CURSOR crProms IS
      SELECT tvp.cod_prom, tvp.estado
      FROM T_VTA_PROMOCION tvp
      WHERE trunc(TVP.FEC_CREA_PROMOCION) BETWEEN trunc(SYSDATE-1) AND trunc(SYSDATE)
      OR trunc(TVP.FEC_MOD_PROMOCION) BETWEEN trunc(SYSDATE-1) AND trunc(SYSDATE);

   ------------------------------ CURSOR CON CAMPAÑAS A PROCESAR,se asume que la tabla:T_VTA_PROMOCION ya fue procesada
  CURSOR crCamps IS
      SELECT TCC.COD_CAMP_CUPON,TCC.ESTADO
      FROM T_VTA_CAMPANA_CUPON tCC
      WHERE trunc(TCC.FEC_CREA_CAMP_CUPON) BETWEEN trunc(SYSDATE-1) AND trunc(SYSDATE)
      OR trunc(TCC.FEC_MOD_CAMP_CUPON) BETWEEN trunc(SYSDATE-1) AND trunc(SYSDATE);
  ------------------------------- Variables
  vcCodLocAct CHAR(3);

  vnSec NUMBER;
BEGIN

    -- 10.- Determinar Codigo de Local Actual
    SELECT DISTINCT(I.Cod_Local) INTO vcCodLocAct
    FROM vta_impr_local I;

   IF trim(upper(pcTipProm))='PAQ' THEN
    -- 20 .- Promociones
    FOR regProm IN crProms LOOP

          --- determinar numero de secuencia
          SELECT NVL(MAX(L.Sec),0)+1 INTO vnSec
          FROM LOG_PROC_CAMPS_PROMS L;

           INSERT INTO LOG_PROC_CAMPS_PROMS(SEC,                          COD_LOCAL,
                                            COD_PROM,                     FEC_PROC,
                                            USU_PROC,                     DESC_PROC,
                                            RESUL_PROC)
                                     VALUES(vnSec,                        vcCodLocAct,
                                            regProm.Cod_Prom,             SYSDATE,
                                            vcUserLoadProm,                pvc_DescProc,
                                            pvc_ResulProc
                                     );
     END LOOP;
     ELSIF trim(upper(pcTipProm))='CAU' THEN
        -- 20 .- CAMPAÑAS
        FOR regCamp IN crCamps LOOP

              --- determinar numero de secuencia
              SELECT NVL(MAX(L.Sec),0)+1 INTO vnSec
              FROM LOG_PROC_CAMPS_PROMS L;

               INSERT INTO LOG_PROC_CAMPS_PROMS(SEC,                          COD_LOCAL,
                                                COD_PROM,                     FEC_PROC,
                                                USU_PROC,                     DESC_PROC,
                                                RESUL_PROC)
                                         VALUES(vnSec,                        vcCodLocAct,
                                                regCamp.Cod_Camp_Cupon,       SYSDATE,
                                                vcUserLoadProm,                pvc_DescProc,
                                                pvc_ResulProc
                                         );
         END LOOP;

     END IF;

     COMMIT;
END;
/*------------------------------------------------------------------------------------------------------------------------------------
  GOAL : PROCESAR LAS CAMPAÑAS RECIBIDOS DESDE APPS
  Ammedments:
  22-OCT-12       TCT          Create
  12-AGO-13       TCT          Elimina productos de campaña prod uso
  11-DIC-13       TCT          Add fields para camp. precio fijo
--------------------------------------------------------------------------------------------------------------------------------------*/
  PROCEDURE SP_PROCESA_CAMPS
  AS
   --vcCodLocAct CHAR(3);
  BEGIN

   -- 10.- SI VTA_CAMPANA_CUPON EXISTE THEN UPDATE , ELSE INSERT

   BEGIN
     MERGE INTO VTA_CAMPANA_CUPON CC
      USING T_VTA_CAMPANA_CUPON TCC
      ON (CC.COD_CAMP_CUPON=TCC.COD_CAMP_CUPON AND CC.COD_GRUPO_CIA=TCC.COD_GRUPO_CIA)
      WHEN MATCHED THEN
         UPDATE SET CC.FECH_INICIO         = TCC.FECH_INICIO
                   ,CC.FECH_FIN            = TCC.FECH_FIN
                   ,CC.MONT_MIN            = TCC.MONT_MIN
                   ,CC.DESC_CUPON          = TCC.DESC_CUPON
                   ,CC.ESTADO              = TCC.ESTADO
                   ,CC.USU_CREA_CAMP_CUPON = TCC.USU_CREA_CAMP_CUPON
                   ,CC.FEC_CREA_CAMP_CUPON = TCC.FEC_CREA_CAMP_CUPON
                   ,CC.USU_MOD_CAMP_CUPON  = TCC.USU_MOD_CAMP_CUPON
                   ,CC.FEC_MOD_CAMP_CUPON  = TCC.FEC_MOD_CAMP_CUPON
                   ,CC.TIP_CAMPANA         = TCC.TIP_CAMPANA
                   ,CC.IND_MENSAJE         = TCC.IND_MENSAJE
                   ,CC.PRIORIDAD           = TCC.PRIORIDAD
                   ,CC.MAX_CUPONES         = TCC.MAX_CUPONES
                   ,CC.NUM_CUPON           = TCC.NUM_CUPON
                   ,CC.IND_MULTIPLO_MONT   = TCC.IND_MULTIPLO_MONT
                   ,CC.IND_MULTIPLO_UNIDAD = TCC.IND_MULTIPLO_UNIDAD
                   ,CC.RANKING             = TCC.RANKING
                   ,CC.FECH_INICIO_USO     = TCC.FECH_INICIO_USO
                   ,CC.FECH_FIN_USO        = TCC.FECH_FIN_USO
                   ,CC.MONT_MIN_USO        = TCC.MONT_MIN_USO
                   ,CC.UNID_MIN_USO        = TCC.UNID_MIN_USO
                   ,CC.TIP_CUPON           = TCC.TIP_CUPON
                   ,CC.UNID_MAX_PROD       = TCC.UNID_MAX_PROD
                   ,CC.MONTO_MAX_DESCT     = TCC.MONTO_MAX_DESCT
                   ,CC.VALOR_CUPON         = TCC.VALOR_CUPON
                   ,CC.COD_FORMA_PAGO      = TCC.COD_FORMA_PAGO
                   ,CC.UNID_MIN            = TCC.UNID_MIN
                   ,CC.DIA_SEMANA          = TCC.DIA_SEMANA
                   ,CC.IND_VAUCHER         = TCC.IND_VAUCHER
                   ,CC.NUM_DIAS_INI        = TCC.NUM_DIAS_INI
                   ,CC.NUM_DIAS_VIG        = TCC.NUM_DIAS_VIG
                   ,CC.MENSAJE_CAMP        = TCC.MENSAJE_CAMP
                   ,CC.IND_MULTIUSO        = TCC.IND_MULTIUSO
                   ,CC.IND_FID             = TCC.IND_FID
                   ,CC.TIPO_SEXO_E         = TCC.TIPO_SEXO_E
                   ,CC.FEC_NAC_INICIO_E    = TCC.FEC_NAC_INICIO_E
                   ,CC.FEC_NAC_FIN_E       = TCC.FEC_NAC_FIN_E
                   ,CC.TIPO_SEXO_U         = TCC.TIPO_SEXO_U
                   ,CC.FEC_NAC_INICIO_U    = TCC.FEC_NAC_INICIO_U
                   ,CC.FEC_NAC_FIN_U       = TCC.FEC_NAC_FIN_U
                   ,CC.CA_MENSAJE_CAMP     = TCC.CA_MENSAJE_CAMP
                   ,CC.CA_COD_BENEFICIO    = TCC.CA_COD_BENEFICIO
                   ,CC.CA_COD_PROD         = TCC.CA_COD_PROD
                   ,CC.CA_CANT_PROD	       =	TCC.CA_CANT_PROD
                   ,CC.CA_VAL_FRAC	       =	TCC.CA_VAL_FRAC
                   ,CC.CA_MAX_CANJE	       =	TCC.CA_MAX_CANJE
                   ,CC.CA_PER_MAX_CANJE	   =	TCC.CA_PER_MAX_CANJE
                   ,CC.CA_NUM_CANJE_X_PER	 =	TCC.CA_NUM_CANJE_X_PER
                   ,CC.CA_UNID_MIN	       =	TCC.CA_UNID_MIN
                   ,CC.CA_CANT_CANJE	     =	TCC.CA_CANT_CANJE
                   ,CC.CA_VAL_FRAC_CANJE	 =	TCC.CA_VAL_FRAC_CANJE
                   ,CC.CA_UNIDAD_CANJ	     =	TCC.CA_UNIDAD_CANJ
                   ,CC.CL_MAX_USOS	       =	TCC.CL_MAX_USOS
                   ,CC.IND_VAL_COSTO_PROM	 =	TCC.IND_VAL_COSTO_PROM
                   ,CC.TEXTO_LARGO	       =	TCC.TEXTO_LARGO
                   ,CC.IND_CADENA	         =	TCC.IND_CADENA
                   ,CC.COD_CAMP_CUPON_PROD_USO	=	TCC.COD_CAMP_CUPON_PROD_USO
                   ,CC.IND_FID_EMI	       =	TCC.IND_FID_EMI
                   ,CC.COD_CAMP_CUPON_PROD =	TCC.COD_CAMP_CUPON_PROD
                   ,CC.DESC_CORTA_CUPON	   =	TCC.DESC_CORTA_CUPON
                   ,CC.IND_MEN_CAMP_PROD	 =	TCC.IND_MEN_CAMP_PROD
                   ,CC.IND_VALIDA_MATRIZ	 =	TCC.IND_VALIDA_MATRIZ
                   ,CC.flg_camp_prec       =  TCC.flg_camp_prec
                   ,CC.cod_prom_btl        =  CC.cod_prom_btl
                   --,CC.IND_TIPO_COLEGIO	   =	TCC.IND_TIPO_COLEGIO
                   --,CC.DCTO_UNICO_X_TARJ   =  TCC.DCTO_UNICO_X_TARJ

      WHEN NOT MATCHED THEN
         INSERT ( CC.COD_GRUPO_CIA                	  , CC.COD_CAMP_CUPON                	  , CC.FECH_INICIO
	              , CC.FECH_FIN 	                      , CC.MONT_MIN                         , CC.DESC_CUPON
	              , CC.ESTADO                           , CC.USU_CREA_CAMP_CUPON              , CC.FEC_CREA_CAMP_CUPON

	              , CC.TIP_CAMPANA                      , CC.IND_MENSAJE                      , CC.PRIORIDAD
	              , CC.MAX_CUPONES                      , CC.NUM_CUPON                        , CC.IND_MULTIPLO_MONT
	              , CC.IND_MULTIPLO_UNIDAD              , CC.RANKING                          , CC.FECH_INICIO_USO

	              , CC.FECH_FIN_USO                     , CC.MONT_MIN_USO                     , CC.UNID_MIN_USO
	              , CC.TIP_CUPON                        , CC.UNID_MAX_PROD                    , CC.MONTO_MAX_DESCT
	              , CC.VALOR_CUPON                      , CC.DIA_SEMANA                       , CC.IND_VAUCHER

	              , CC.IND_MULTIUSO                     , CC.IND_FID                          , CC.IND_VAL_COSTO_PROM
	              , CC.ind_cadena                       , CC.ind_fid_emi                      , CC.flg_camp_prec
                , CC.cod_prom_btl
         )
         VALUES(
                  TCC.COD_GRUPO_CIA                   ,TCC.COD_CAMP_CUPON                    ,TCC.FECH_INICIO
                  ,TCC.FECH_FIN                       ,TCC.MONT_MIN                          ,TCC.DESC_CUPON
                  ,TCC.ESTADO                         ,TCC.USU_CREA_CAMP_CUPON               ,TCC.FEC_CREA_CAMP_CUPON

                , TCC.TIP_CAMPANA                      , TCC.IND_MENSAJE                      , TCC.PRIORIDAD
	              , TCC.MAX_CUPONES                      , TCC.NUM_CUPON                        , TCC.IND_MULTIPLO_MONT
	              , TCC.IND_MULTIPLO_UNIDAD              , TCC.RANKING                          , TCC.FECH_INICIO_USO

	              , TCC.FECH_FIN_USO                     , TCC.MONT_MIN_USO                     , TCC.UNID_MIN_USO
	              , TCC.TIP_CUPON                        , TCC.UNID_MAX_PROD                    , TCC.MONTO_MAX_DESCT
	              , TCC.VALOR_CUPON                      , TCC.DIA_SEMANA                       , TCC.IND_VAUCHER

	              , TCC.IND_MULTIUSO                     , TCC.IND_FID                          , TCC.IND_VAL_COSTO_PROM
	              , TCC.ind_cadena                       , TCC.ind_fid_emi                      , TCC.flg_camp_prec
                , TCC.cod_prom_btl
               );


   EXCEPTION
    WHEN OTHERS THEN
     RAISE_APPLICATION_ERROR(-20001,'ERROR,AL INSERTAR/ACTUALIZAR TABLA: VTA_CAMPANA_CUPON...');
   END;

  -- 25 .- Eliminar Productos Uso para Recrearlos
   DELETE FROM VTA_CAMPANA_PROD_USO pu
   WHERE EXISTS (
                 SELECT 1
                 FROM T_VTA_CAMPANA_PROD_USO TPU
                 WHERE tpu.cod_grupo_cia = pu.cod_grupo_cia
                   AND tpu.cod_camp_cupon = pu.cod_camp_cupon
                );

  -- 30.- SI VTA_CAMPANA_PROD_USO EXISTE THEN UPDATE ELSE INSERT

   BEGIN

     MERGE INTO VTA_CAMPANA_PROD_USO PU
      USING (
              SELECT t.*
                FROM t_VTA_CAMPANA_PROD_USO t
                WHERE EXISTS(
                              SELECT 1
                              FROM lgt_prod p
                              WHERE p.cod_grupo_cia = '001'
                                AND p.cod_prod      = t.cod_prod
            )
            )TPU
      ON (PU.COD_CAMP_CUPON = TPU.COD_CAMP_CUPON AND PU.COD_GRUPO_CIA = TPU.COD_GRUPO_CIA AND PU.COD_PROD	=	TPU.COD_PROD)
      WHEN MATCHED THEN

         UPDATE SET  PU.ESTADO	          =	TPU.ESTADO
                    ,PU.USU_CREA_CUP_DET	=	TPU.USU_CREA_CUP_DET
                    ,PU.FEC_CREA_CUP_DET	=	TPU.FEC_CREA_CUP_DET
                    ,PU.USU_MOD_CUP_DET	  =	TPU.USU_MOD_CUP_DET
                    ,PU.FEC_MOD_CUP_DET	  =	TPU.FEC_MOD_CUP_DET
                    ,PU.MAX_UNID_DCTO	    =	TPU.MAX_UNID_DCTO
                    ,PU.VALOR_CUPON_PROD	=	TPU.VALOR_CUPON_PROD
                    ,PU.ind_excluye_acum_ahorro = TPU.ind_excluye_acum_ahorro
                    ,PU.ind_suma_uso            = TPU.ind_suma_uso
                    ,PU.ind_dcto_uso            = TPU.ind_dcto_uso
                    ,PU.val_prec_prom           = TPU.val_prec_prom
                    ,PU.flg_modo                = TPU.flg_modo
                    ,PU.flg_tipo_valor          = TPU.flg_tipo_valor
                    ,PU.imp_valor               = TPU.imp_valor

      WHEN NOT MATCHED THEN
         INSERT (
                  cod_grupo_cia,
                  cod_camp_cupon,
                  cod_prod,
                  estado,
                  usu_crea_cup_det,
                  fec_crea_cup_det,
                  usu_mod_cup_det,
                  fec_mod_cup_det,
                  max_unid_dcto,
                  valor_cupon_prod,
                  ind_excluye_acum_ahorro,
                  ind_suma_uso,
                  ind_dcto_uso,
                  val_prec_prom,
                  flg_modo,
                  flg_tipo_valor,
                  imp_valor
                )
         VALUES(TPU.COD_GRUPO_CIA
                       ,TPU.COD_CAMP_CUPON
                       ,TPU.COD_PROD
                       ,TPU.ESTADO
                       ,TPU.USU_CREA_CUP_DET
                       ,TPU.FEC_CREA_CUP_DET
                       ,TPU.USU_MOD_CUP_DET
                       ,TPU.FEC_MOD_CUP_DET
                       ,TPU.MAX_UNID_DCTO
                       ,TPU.VALOR_CUPON_PROD
                       ,TPU.IND_EXCLUYE_ACUM_AHORRO
                       ,TPU.ind_suma_uso
                       ,TPU.ind_dcto_uso
                       ,TPU.val_prec_prom
                       ,TPU.flg_modo
                       ,TPU.flg_tipo_valor
                       ,TPU. imp_valor
                       );


   EXCEPTION
    WHEN OTHERS THEN
     RAISE_APPLICATION_ERROR(-20001,'ERROR,AL INSERTAR/ACTUALIZAR TABLA: VTA_CAMPANA_PROD_USO...');
   END;


   -- 70.-  SI VTA_CAMP_X_LOCAL EXISTE THEN UPDATE, ELSE INSERT

   BEGIN
     MERGE INTO VTA_CAMP_X_LOCAL CL
      USING (
             SELECT t.*
             FROM t_VTA_CAMP_X_LOCAL T
             WHERE EXISTS (
                    SELECT 1
                    FROM pbl_local l
                    WHERE l.cod_grupo_cia = '001'
                      AND l.cod_local     = t.cod_local
                   )
             ) TCL
      ON (CL.COD_GRUPO_CIA=TCL.COD_GRUPO_CIA AND CL.COD_CAMP_CUPON=TCL.COD_CAMP_CUPON AND CL.COD_LOCAL=TCL.COD_LOCAL)
      WHEN MATCHED THEN
         UPDATE SET  CL.ESTADO	              =	TCL.ESTADO
                     ,CL.USU_CREA_CAMP_LOCAL	=	TCL.USU_CREA_CAMP_LOCAL
                     ,CL.FEC_CREA_CAMP_LOCAL	=	TCL.FEC_CREA_CAMP_LOCAL
                     ,CL.USU_MOD_CAMP_LOCAL	  =	TCL.USU_MOD_CAMP_LOCAL
                     ,CL.FEC_MOD_CAMP_LOCAL	  =	TCL.FEC_MOD_CAMP_LOCAL
                     ,CL.cod_cia              = TCL.cod_cia
                     ,CL.cod_prom_btl         = TCL.cod_prom_btl


      WHEN NOT MATCHED THEN
         INSERT (
                  cod_grupo_cia,
                  cod_camp_cupon,
                  cod_local,
                  estado,
                  usu_crea_camp_local,
                  fec_crea_camp_local,
                  usu_mod_camp_local,
                  fec_mod_camp_local,
                  cod_cia,
                  cod_prom_btl
                )
         VALUES(TCL.COD_GRUPO_CIA
                       ,TCL.COD_CAMP_CUPON
                       ,TCL.COD_LOCAL
                       ,TCL.ESTADO
                       ,TCL.USU_CREA_CAMP_LOCAL
                       ,TCL.FEC_CREA_CAMP_LOCAL
                       ,TCL.USU_MOD_CAMP_LOCAL
                       ,TCL.FEC_MOD_CAMP_LOCAL
                       ,TCL.cod_cia
                       ,TCL.cod_prom_btl
                       );


   EXCEPTION
    WHEN OTHERS THEN
     RAISE_APPLICATION_ERROR(-20001,'ERROR,AL INSERTAR/ACTUALIZAR TABLA: VTA_CAMP_X_LOCAL ...');
   END;

   -- 300.- DESACTIVA PRODS. DE CAMPAÑA SI APLICA, SI NO NOTHING
   ptoventa_proc_packs_camps.sp_DISABLE_PRODS_CAMPS;
   -- 400.- Asignar estado Actual a CAMPAÑA, si no esta en local tambien se desactiva
   ptoventa_proc_packs_camps.sp_asig_estado_CAMP;
   COMMIT;
   dbms_output.put_line('OK,al Cargar Campañas en Local');
   -- 410.- Grabar Log. de Exito
   ptoventa_proc_packs_camps.sp_graba_log('OK,al Cargar Campañas en Local','OK','CAU');
  EXCEPTION
   WHEN OTHERS THEN
   ROLLBACK;
   dbms_output.put_line('ERROR,al Cargar Campañas en Local:'||SQLERRM);
   -- 410.- Grabar Log. de Error
   ptoventa_proc_packs_camps.sp_graba_log('ERROR,al Cargar Campañas en Local'||substr(SQLERRM,1,150),'ERR','CAU');
   RAISE;

  END;

 /*-----------------------------------------------------------------------------------------------------------------------------------------
GOAL : DISABLE PRODUCTOS QUE YA NO FIGURAN EN CAMPAÑA
DATE : 23-OCT-12
-------------------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_DISABLE_PRODS_CAMPS
AS
 ------------------------------------ CURSOR CON PRODUCTOS A DESHABILITAR (PREFERIBLE NO ELIMINAR)
 CURSOR crProdsDisable IS
      SELECT PU.COD_GRUPO_CIA,PU.COD_CAMP_CUPON,PU.COD_PROD
      FROM VTA_CAMPANA_PROD_USO PU
      WHERE EXISTS (SELECT 1
                    FROM T_VTA_CAMPANA_CUPON TCC
                    WHERE TCC.COD_CAMP_CUPON = PU.COD_CAMP_CUPON
                      AND TCC.COD_GRUPO_CIA  = PU.COD_GRUPO_CIA)
      MINUS
      SELECT T.COD_GRUPO_CIA,T.COD_CAMP_CUPON,T.COD_PROD
      FROM T_VTA_CAMPANA_PROD_USO T;
/* ------------------------------------------------------------------------ PRODUCTOS A ELIMINAR
SELECT pu.cod_camp_cupon,pu.cod_prod,pu.estado AS est FROM VTA_CAMPANA_PROD_USO PU
WHERE EXISTS (SELECT 1
              FROM T_VTA_CAMPANA_CUPON TCC
              WHERE TCC.COD_CAMP_CUPON = PU.COD_CAMP_CUPON
                AND TCC.COD_GRUPO_CIA  = PU.COD_GRUPO_CIA
                )
  AND NOT EXISTS(SELECT 1
                 FROM  T_VTA_CAMPANA_PROD_USO T
                 WHERE T.COD_CAMP_CUPON = PU.COD_CAMP_CUPON
                   AND T.COD_GRUPO_CIA  = PU.COD_GRUPO_CIA
                   AND T.COD_PROD       = PU.COD_PROD)     */


BEGIN
 FOR regProdDisa IN crProdsDisable LOOP
  BEGIN
     UPDATE VTA_CAMPANA_PROD_USO PU
     SET PU.ESTADO='I'
         ,PU.USU_MOD_CUP_DET='USR_DIS_CAMP'
         ,PU.FEC_MOD_CUP_DET=SYSDATE

     WHERE PU.COD_GRUPO_CIA=REGPRODDISA.COD_GRUPO_CIA
       AND PU.COD_CAMP_CUPON=REGPRODDISA.COD_CAMP_CUPON
       AND PU.COD_PROD=REGPRODDISA.COD_PROD;
   EXCEPTION
    WHEN OTHERS THEN
     RAISE_APPLICATION_ERROR(-20001,'ERROR, AL INACTIVAR PROD. DE CAMP. '||REGPRODDISA.COD_PROD||
     ', '||REGPRODDISA.COD_CAMP_CUPON||SQLERRM);
   END;

 END LOOP;

 COMMIT;
 DBMS_OUTPUT.put_line('OK,AL INACTIVAR PRODS. DE CAMPAÑA');
 EXCEPTION
  WHEN OTHERS THEN
   ROLLBACK;
   DBMS_OUTPUT.put_line('ERROR,AL INACTIVAR PRODS. DE CAMPAÑA:'||SQLERRM);
   RAISE;
END;
/*-----------------------------------------------------------------------------------------------------------------------------------------
GOAL : ASIGNAR EL ESTADO DE LAS CAMPAÑAS
DATE : 25-OCT-12
------------------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_ASIG_ESTADO_CAMP
AS
    ------------------------------ CURSOR CON PROMOCIONES A PROCESAR,se asume que la tabla:T_VTA_PROMOCION ya fue procesada
    CURSOR crCamps IS
      SELECT TCC.COD_CAMP_CUPON,TCC.ESTADO
      FROM T_VTA_CAMPANA_CUPON TCC;
    ------------------------------- Variables
    vcCodLocAct CHAR(3);
    vnCant NUMBER;
    vcEstCamp CHAR(1):='I';         -- asume inactiva
BEGIN
  -- 10.- Determinar Codigo de Local Actual
  SELECT DISTINCT(I.Cod_Local) INTO vcCodLocAct
  FROM vta_impr_local I;

  -- 20.- Procesar cada Promocion
  FOR regCamp IN crCamps LOOP
   BEGIN
       -- 25.- SI TABLA:VTA_CAMP_X_LOCAL NO CONTIENE NINGUN REGISTRO PARA LA CAMPAÑA,
       --      SIGNIFICA QUE SE ACTIVA EN TODA LA CADENA, SI NO ACTIVAR SOLO EN LOCALES QUE INDIQUE
       SELECT COUNT(*) INTO vnCant
       FROM VTA_CAMP_X_LOCAL CL
       WHERE CL.COD_GRUPO_CIA='001'
         AND CL.COD_CAMP_CUPON= regCamp.Cod_Camp_Cupon;

       IF vnCant>0 AND trim(regCamp.Estado)='A' THEN
          -- 30.- determinar si promocion existe en local
          vcEstCamp:='I';
           IF ptoventa_proc_packs_camps.fn_activar_prom_local(regCamp.Cod_Camp_Cupon,vcCodLocAct,CC_CAMP_AUT) THEN
             vcEstCamp:='A';
           END IF;
           -- 40.- Asignar estado a Promocion
           UPDATE VTA_CAMPANA_CUPON CC
           SET CC.ESTADO=vcEstCamp
           WHERE CC.COD_GRUPO_CIA='001'
             AND CC.COD_CAMP_CUPON=regCamp.Cod_Camp_Cupon;

       END IF;

   EXCEPTION
    WHEN OTHERS THEN
     RAISE_APPLICATION_ERROR(-20001,'ERROR, AL ACTUALIZAR ESTADO DE CAMP:'||regCamp.Cod_Camp_Cupon);
   END;
  END LOOP;

  COMMIT;
  DBMS_OUTPUT.put_line('OK, AL ACTUALIZAR ESTADO DE CAMPAÑAS...');
EXCEPTION
 WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.put_line('ERROR, AL ACTUALIZAR ESTADO DE CAMPAÑAS...'||SQLERRM);
  RAISE;


END;
/*-------------------------------------------------------------------------------------------------------------------------
GOAL : Insert, Update, Delete las Tablas de: Campañas y Promociones(Packs) de Local,
       Usando las Tablas Temporales Recibidas desde APPS
DATE: 25-OCT-12
--------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_PROCESA_PACKS_CAMPS
AS
 vnCant NUMBER;
BEGIN

  BEGIN
   -- 10.- Determinar si hay Promociones(Packs) a Procesar, solo de AYER y HOY
   SELECT COUNT(*) INTO vnCant
   FROM T_VTA_PROMOCION TVP
   WHERE trunc(TVP.FEC_CREA_PROMOCION) BETWEEN trunc(SYSDATE-1) AND trunc(SYSDATE)
     OR trunc(TVP.FEC_MOD_PROMOCION) BETWEEN trunc(SYSDATE-1) AND trunc(SYSDATE);

   IF vnCant>0 THEN
      ptoventa_proc_packs_camps.sp_procesa_packs;
   END IF;

   -- 20.- Determinar si hay Campañas a Procesar, solo de AYER y HOY
   SELECT COUNT(*) INTO vnCant
   FROM T_VTA_CAMPANA_CUPON TCC
   WHERE trunc(TCC.FEC_CREA_CAMP_CUPON) BETWEEN trunc(SYSDATE-1) AND trunc(SYSDATE)
     OR trunc(TCC.FEC_MOD_CAMP_CUPON) BETWEEN trunc(SYSDATE-1) AND trunc(SYSDATE);

   IF vnCant>0 THEN
      ptoventa_proc_packs_camps.sp_procesa_camps;
   END IF;
  EXCEPTION
   WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20001,'ERROR GRAL, AL PROCESAR CAMPAÑAS/PROMOCIONES:'||SQLERRM);
  END;
  DBMS_OUTPUT.put_line('OK GRAL, AL PROCESAR CAMPAÑAS/PROMOCIONES');
 EXCEPTION
  WHEN OTHERS THEN
   DBMS_OUTPUT.put_line('ERROR GRAL, AL PROCESAR CAMPAÑAS/PROMOCIONES:'||SQLERRM);
   RAISE;

END;
/*-----------------------------------------------------------------------------------------------------------------------------------
GOAL : DETERMINAR LAS DIFERENCIAS DE CAMPAÑAS ACTIVAS DE APPS CON LOCAL ( APPS - LOCAL)
Ammedments:
09-ENE-14  TCT    Create
-----------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_GENE_DIFE_PROMS_ADM_LOC
AS
BEGIN
-- 10.- ELIMINAR DATOS ANTIGUOS
DELETE FROM AUX_DIFE_PROMS_ADM_LOCS;

-- 20.- CARGA CAMPAÑAS
INSERT INTO AUX_DIFE_PROMS_ADM_LOCS(
cod_local,
cod_camp_cupon,
desc_cupon,
tip_campana,
fech_inicio_uso,
fech_fin_uso,
ind_cadena,
unid_min_uso,
unid_max_prod,
monto_max_desct,
valor_cupon,
dia_semana,
flg_camp_prec,
estado,
tip_promocion
)

SELECT (
        SELECT DISTINCT (l.cod_local)
        FROM vta_impr_local l
        WHERE l.cod_grupo_cia = '001'
       )cod_local,
       cc.cod_camp_cupon,cc.desc_cupon,cc.tip_campana,cc.fech_inicio_uso,cc.fech_fin_uso,cc.ind_cadena,
       cc.unid_min_uso,cc.unid_max_prod,cc.monto_max_desct,cc.valor_cupon,cc.dia_semana,cc.flg_camp_prec,
       cc.estado,c.tip_promocion
FROM apps.vta_campana_cupon@XE_DEL_999 cc
LEFT OUTER JOIN apps.cab_promoc_mfarma_btl@XE_DEL_999 c ON ('C'||cc.cod_camp_cupon = c.cod_prom_mf)
WHERE cc.cod_grupo_cia = '001'
  AND cc.estado        = 'A'
  AND trunc(SYSDATE) BETWEEN cc.fech_inicio_uso
                         AND cc.fech_fin_uso
  --- cabecera de promocion BTL
  AND c.tip_promocion  = 'CAU'
MINUS
  -- Campañas Activas en Local
SELECT (
        SELECT DISTINCT (l.cod_local)
        FROM vta_impr_local l
        WHERE l.cod_grupo_cia = '001'
       )cod_local,
       cc.cod_camp_cupon,cc.desc_cupon,cc.tip_campana,cc.fech_inicio_uso,cc.fech_fin_uso,cc.ind_cadena,
       cc.unid_min_uso,cc.unid_max_prod,cc.monto_max_desct,cc.valor_cupon,cc.dia_semana,cc.flg_camp_prec,
       cc.estado,'CAU' tip_promocion

FROM ptoventa.vta_campana_cupon cc
WHERE cc.cod_grupo_cia = '001'
  AND cc.estado        = 'A'
  AND trunc(SYSDATE) BETWEEN cc.fech_inicio_uso
                         AND cc.fech_fin_uso;

-- 30.- CARGA PROMOCIONES (PACKS)
INSERT INTO AUX_DIFE_PROMS_ADM_LOCS(
cod_local,
cod_camp_cupon,
desc_cupon,
tip_campana,
fech_inicio_uso,
fech_fin_uso,
ind_cadena,
unid_min_uso,
unid_max_prod,
monto_max_desct,
valor_cupon,
dia_semana,
flg_camp_prec,
estado,
tip_promocion
)

 SELECT (
        SELECT DISTINCT (l.cod_local)
        FROM vta_impr_local l
        WHERE l.cod_grupo_cia = '001'
       )cod_local,
       cc.cod_prom cod_camp_cupon ,cc.desc_corta_prom desc_cupon ,NULL tip_campana,cc.fec_promocion_inicio ,cc.fec_promocion_fin ,NULL ind_cadena,
       NULL unid_min_uso,NULL unid_max_prod,NULL monto_max_desct,NULL valor_cupon,NULL dia_semana,NULL flg_camp_prec,
       cc.estado,C.TIP_PROMOCION
FROM apps.vta_PROMOCION@XE_DEL_999 cc
LEFT OUTER JOIN apps.cab_promoc_mfarma_btl@XE_DEL_999 c ON (CC.COD_PROM = c.cod_prom_mf)
WHERE cc.cod_grupo_cia = '001'
  AND cc.estado        = 'A'
  AND trunc(SYSDATE) BETWEEN cc.fec_promocion_inicio
                         AND cc.fec_promocion_fin
  --- cabecera de promocion BTL
  AND c.tip_promocion  = 'PAQ'
MINUS
  -- PROMOS Activas en Local
SELECT (
        SELECT DISTINCT (l.cod_local)
        FROM vta_impr_local l
        WHERE l.cod_grupo_cia = '001'
       )cod_local,
       cc.cod_prom cod_camp_cupon ,cc.desc_corta_prom desc_cupon ,NULL tip_campana,cc.fec_promocion_inicio ,cc.fec_promocion_fin ,NULL ind_cadena,
       NULL unid_min_uso,NULL unid_max_prod,NULL monto_max_desct,NULL valor_cupon,NULL dia_semana,NULL flg_camp_prec,
       cc.estado,'PAQ' tip_promocion
-- SELECT *
FROM ptoventa.vta_PROMOCION cc
WHERE cc.cod_grupo_cia = '001'
  AND cc.estado        = 'A'
  AND trunc(SYSDATE) BETWEEN cc.fec_promocion_inicio
                         AND cc.fec_promocion_fin;

 COMMIT;
  DBMS_OUTPUT.put_line('OK, Al Grabar Tabla : AUX_DIFE_PROMS_ADM_LOCS con diferencias de Promos.');
 EXCEPTION
   WHEN OTHERS THEN
   ROLLBACK;
   DBMS_OUTPUT.put_line('ERROR, Al Grabar Tabla : AUX_DIFE_PROMS_ADM_LOCS con diferencias de Promos:'||Sqlerrm);
   ptoventa_proc_packs_camps.sp_graba_log_proce_proms(ac_resu_descrip => 'Gene. Dife. '||SUBSTR(SQLERRM,1,300));

END;
/*--------------------------------------------------------------------------------------------------------------------
 GOAL : GRABAR LOG DE ENVIO DE PROMOCIONES DE BTL A MFA
 Ammedments:
 09-ENE-14  TCT    Create
----------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_GRABA_LOG_PROCE_PROMS(
                                   ac_Resu_Descrip VARCHAR2
                                  )
AS PRAGMA AUTONOMOUS_TRANSACTION;
vn_Secu NUMBER;

BEGIN
     -- 10.- Determmina Numero de Secuencia
     SELECT nvl(MAX(l.secu),1)+1
     INTO vn_Secu
     FROM PTOVENTA.LOG_PROCESA_PROMS L;

     -- 20.- Carga Datos en Tabla
     INSERT INTO PTOVENTA.LOG_PROCESA_PROMS (
     secu,
     resu_descrip,
     fech_load
     )
     VALUES(vn_Secu,ac_Resu_Descrip,SYSDATE);
     COMMIT;
END;
/*--------------------------------------------------------------------------------------------------------------------
 GOAL : ENVIAR DIFERENCIA DE PROMOCIONES = CAMPA + PACKS A ADM
 Ammedments:
 09-ENE-14  TCT    Create
----------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_SEND_DIFE_PROMS_A_ADM
AS
 vn_Cuenta NUMBER;
BEGIN
 -- 10.- GENERA DIFERENCIA DE PROMOCIONES
 ptoventa_proc_packs_camps.sp_gene_dife_proms_adm_loc;
 -- 20.- VALIDAR SI HAY DATOS A ENVIAR
 SELECT COUNT(*)
 INTO vn_Cuenta
 FROM AUX_DIFE_PROMS_ADM_LOCS A
 WHERE A.FECH_CARGA_ADM IS NULL; -- NO ENVIADOS A ADM

 -- 30.- ENVIO A ADM
 IF (vn_Cuenta > 0) THEN
  -- 30.10.- Eliminar Datos antiguos de Local
  DELETE FROM APPS.AUX_DIFE_PROMS_ADM_LOCS@XE_DEL_999 T
  WHERE trim(T.cod_local)= (
                            select distinct(a.cod_local)
                            from aux_dife_proms_adm_locs a
                           );

  INSERT INTO APPS.AUX_DIFE_PROMS_ADM_LOCS@XE_DEL_999(
                                      cod_local,
                                      cod_camp_cupon,
                                      desc_cupon,
                                      tip_campana,
                                      fech_inicio_uso,
                                      fech_fin_uso,
                                      ind_cadena,
                                      unid_min_uso,
                                      unid_max_prod,
                                      monto_max_desct,
                                      valor_cupon,
                                      dia_semana,
                                      flg_camp_prec,
                                      estado,
                                      tip_promocion,
                                      fech_carga_adm
                                      )
 SELECT
    A.cod_local,
    A.cod_camp_cupon,
    A.desc_cupon,
    A.tip_campana,
    A.fech_inicio_uso,
    A.fech_fin_uso,
    A.ind_cadena,
    A.unid_min_uso,
    A.unid_max_prod,
    A.monto_max_desct,
    A.valor_cupon,
    A.dia_semana,
    A.flg_camp_prec,
    A.estado,
    A.tip_promocion,
    SYSDATE
 FROM PTOVENTA.AUX_DIFE_PROMS_ADM_LOCS A
 WHERE A.FECH_CARGA_ADM IS NULL; -- NO ENVIADOS A ADM
 -- ACTUALIZA FECHA DE ENVIO
 UPDATE PTOVENTA.AUX_DIFE_PROMS_ADM_LOCS A
 SET A.FECH_CARGA_ADM = SYSDATE
 WHERE A.FECH_CARGA_ADM IS NULL;

 END IF;
 COMMIT;
  DBMS_OUTPUT.put_line('OK, Al Grabar Tabla : AUX_DIFE_PROMS_ADM_LOCS en ADM.');
 EXCEPTION
   WHEN OTHERS THEN
   ROLLBACK;
   DBMS_OUTPUT.put_line('ERROR, Al Grabar Tabla : AUX_DIFE_PROMS_ADM_LOCS en ADM:'||Sqlerrm);
   ptoventa_proc_packs_camps.sp_graba_log_proce_proms(ac_resu_descrip => 'Envia Dife.a ADM '||SUBSTR(SQLERRM,1,300));

END;

/******************************************************************************************************************************************/
end PTOVENTA_PROC_PACKS_CAMPS;

/
