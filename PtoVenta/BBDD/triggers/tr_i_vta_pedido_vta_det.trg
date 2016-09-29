CREATE OR REPLACE TRIGGER PTOVENTA."TR_I_VTA_PEDIDO_VTA_DET"
BEFORE INSERT ON VTA_PEDIDO_VTA_DET
FOR EACH ROW
DECLARE
		v_cIndZan LGT_PROD_LOCAL.IND_ZAN%TYPE;
    v_nPrecProm LGT_PROD.VAL_PREC_PROM%TYPE;

    valor_fraccion NUMBER;
    valor_fraccion_sug NUMBER;

    valor_prec_vta LGT_PROD_LOCAL.VAL_PREC_VTA%TYPE;
    tip_ped_vta VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE;
    indValida CHAR(1);

-- 2009-11-11 JOLIVA
    v_nPorcZan LGT_PROD_LOCAL.PORC_ZAN%TYPE;
-- 14-ENE-14, TCT
    vc_Cod_Cia CHAR(3);
    vCodGrupoRepEdmundo LGT_PROD.COD_GRUPO_REP_EDMUNDO%TYPE;

   nAtencionCliente PRESUPUESTO.TOTAL%TYPE;
   nAnio_v integer;
   nMes_v  integer;
BEGIN
   -- 07.01.2015 ERIOS Garantizado por local
   SELECT PROD_LOCAL.VAL_FRAC_LOCAL,
          NVL(PROD.VAL_FRAC_VTA_SUG,PROD_LOCAL.VAL_FRAC_LOCAL),
          PROD_LOCAL.VAL_PREC_VTA,
          PROD_LOCAL.IND_ZAN,
          PROD_LOCAL.PORC_ZAN,
          PROD.COD_GRUPO_REP_EDMUNDO
	        INTO	valor_fraccion,valor_fraccion_sug,
          valor_prec_vta,
          v_cIndZan,
          v_nPorcZan,
          vCodGrupoRepEdmundo
	 FROM   LGT_PROD_LOCAL PROD_LOCAL,
          LGT_PROD PROD
	 WHERE  PROD_LOCAL.COD_GRUPO_CIA = :NEW.COD_GRUPO_CIA
	        AND	PROD_LOCAL.COD_LOCAL = :NEW.COD_LOCAL
	        AND	PROD_LOCAL.COD_PROD = :NEW.COD_PROD
          AND PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
          AND PROD_LOCAL.COD_PROD = PROD.COD_PROD

   ;
	 IF (valor_fraccion <> :NEW.VAL_FRAC) AND
      (valor_fraccion_sug <> :NEW.VAL_FRAC) THEN
		RAISE_APPLICATION_ERROR(-20058,'Error - Valor de Fraccion Diferente');
	 END IF;

   --ERIOS 05/08/2008 Se valida el precio

   SELECT TRIM(T.LLAVE_TAB_GRAL)
   INTO   tip_ped_vta
   FROM   PBL_TAB_GRAL T
   WHERE  T.ID_TAB_GRAL = 212;
   --Solo si se habilita la opcion
   IF indValida = 'S' THEN

     SELECT C.TIP_PED_VTA
       INTO tip_ped_vta
     FROM VTA_PEDIDO_VTA_CAB C
     WHERE COD_GRUPO_CIA = :NEW.COD_GRUPO_CIA
           AND COD_LOCAL = :NEW.COD_LOCAL
           AND NUM_PED_VTA = :NEW.NUM_PED_VTA;
     --Si es pedido meson
     IF tip_ped_vta = '01' THEN

       IF :NEW.VAL_PREC_TOTAL != 0 THEN
         IF ABS(valor_prec_vta*:NEW.CANT_ATENDIDA* valor_fraccion / :NEW.VAL_FRAC - :NEW.VAL_PREC_TOTAL) >= 0.01 THEN
           IF
              (CEIL(valor_prec_vta*:NEW.CANT_ATENDIDA* valor_fraccion / :NEW.VAL_FRAC * 10) / 10) / :NEW.VAL_PREC_TOTAL >= 2
              OR
              :NEW.VAL_PREC_TOTAL / (CEIL(valor_prec_vta*:NEW.CANT_ATENDIDA* valor_fraccion / :NEW.VAL_FRAC * 10) / 10) >= 2
             THEN
               RAISE_APPLICATION_ERROR(-20059,'Error - Precio errado');
           END IF;
         END IF;
       END IF;

     END IF;
   END IF;

   --- 14-ENE-14, TCT, LEER CODIGO CIA

  SELECT LC.COD_CIA
  INTO vc_cod_cia
  FROM PBL_LOCAL LC
  WHERE LC.COD_GRUPO_CIA = :NEW.COD_GRUPO_CIA
    AND LC.COD_LOCAL     = :NEW.COD_LOCAL;
  -----

	 SELECT P.VAL_PREC_PROM
	 INTO	v_nPrecProm
	 FROM   LGT_PROD P
	 WHERE  P.COD_GRUPO_CIA = :NEW.COD_GRUPO_CIA
      	 AND	P.COD_PROD = :NEW.COD_PROD;

   :NEW.IND_ZAN := v_cIndZan;
   :NEW.VAL_PREC_PROM := v_nPrecProm;
   :NEW.PORC_ZAN := v_nPorcZan;
   -- KMONCADA ACTUALIZANDO EL CODIGO DE REPOSICION EDMUNDO
   :NEW.COD_GRUPO_REP_EDMUNDO := vCodGrupoRepEdmundo;

   --ERIOS 01.06.2015 Graba el porcentaje de comision Atencion al Cliente
   select to_number(to_char(SYSDATE,'yyyy')),
          to_number(to_char(SYSDATE,'mm'))
          into   nAnio_v,nMes_v
   from   dual;
   nAtencionCliente := PTOVENTA.fn_dev_meta('018',nAnio_v,nMes_v,:NEW.COD_LOCAL);
   :NEW.PORC_COM_ATE_CLI := nAtencionCliente;
   
   --ERIOS 18.04.2016 Se graba puntos rentables
   :NEW.PTS_PROG := PTOVENTA.PTOVENTA_RENTABLES.GET_PUNTOS(:NEW.COD_GRUPO_CIA,:NEW.COD_PROD);
END;
/

