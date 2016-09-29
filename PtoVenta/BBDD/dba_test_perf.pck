CREATE OR REPLACE PACKAGE PTOVENTA."DBA_TEST_PERF" is

  -- Author  : JLUNA
  -- Created : 12/04/2010 05:55:40 p.m.
  -- Purpose : paquete utilitario del dba para los locales

  -- Public type declarations
  procedure revisa_prod;
  procedure revisa_varias_veces;
end dba_test_perf;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."DBA_TEST_PERF" is

  -- Private type declarations
  procedure revisa_prod is
  cCodGrupoCia_in char(3) :='001';
cCodProd_in char(6):='138939';
cCodLocal_in CHAR(3);
inD_ORIGEN_SUST NUMBER :=2;
NNumDia CHAR(1):='1';
cDni_in VARCHAR(8):='10289700';
v_vDescripcion varchar2(200);
xmensaje varchar2(2000);
nnn number;
xxx number;
nCant number;

begin
select distinct cod_local into cCodLocal_in from vta_impr_local;
nnn:=0;
for t in (
SELECT DISTINCT NVL(P.DESCRIPCION,'PRODUCTO EN PACK')
       FROM   VTA_PROMOCION P , VTA_PROD_PAQUETE C
       WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in   AND
       C.COD_PROD=cCodProd_in                       AND
       P.COD_GRUPO_CIA = C.COD_GRUPO_CIA         AND
       P.ESTADO  = 'A'                           AND
       ( P.COD_PAQUETE_1 = (C.COD_PAQUETE) OR
         P.COD_PAQUETE_2 = (C.COD_PAQUETE)))
loop
 nnn:=nnn+1;
end loop;

---
-- 07.01.2015 ERIOS Garantizado por local
SELECT (PROD_LOCAL.STK_FISICO) || 'Ã' ||
  			   PROD.DESC_UNID_PRESENT || 'Ã' ||
  			   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
  			   TO_CHAR(PTOVENTA_VTA.VTA_F_CHAR_PREC_REDONDEADO((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)),'999,990.000') || 'Ã' ||
  			   PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
           TO_CHAR( PTOVENTA_VTA.VTA_F_CHAR_PREC_REDONDEADO(VAL_PREC_VTA) ,'999,990.000') || 'Ã' ||
  			   NVL(DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA),' ')|| 'Ã' ||
           PROD_LOCAL.IND_PROD_HABIL_VTA || 'Ã' ||
           '0' || 'Ã' ||
  			   PROD.IND_TIPO_PROD || 'Ã' ||
  		     NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
           ' ' || 'Ã' ||
           ' '
      into xmensaje
  		FROM LGT_PROD PROD,
  			   LGT_PROD_LOCAL PROD_LOCAL
  		WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
  		AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
  		AND	   PROD_LOCAL.COD_PROD = cCodProd_in
  		AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
  		AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
      ;
---------
nnn:=0;
for c in (SELECT
      CASE WHEN PROD_LOCAL.IND_PROD_FRACCIONADO = 'N'  THEN

                 PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
          		   DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR(  ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
          		   NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 inD_ORIGEN_SUST --ERIOS 11/04/2008 Origen

         WHEN PROD.VAL_FRAC_VTA_SUG IS NOT NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S'
              AND PROD.VAL_FRAC_VTA_SUG < PROD_LOCAL.VAL_FRAC_LOCAL     THEN

                 PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',' ',TRIM(PROD.desc_unid_vta_sug)) || 'Ã' ||

          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
          		   NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 inD_ORIGEN_SUST --ERIOS 11/04/2008 Origen


             WHEN PROD.VAL_FRAC_VTA_SUG IS NOT NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S'
             AND PROD.VAL_FRAC_VTA_SUG =  PROD_LOCAL.VAL_FRAC_LOCAL  THEN

                 PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',' ',TRIM(PROD_LOCAL.UNID_VTA)) || 'Ã' ||

          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
          		   NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 inD_ORIGEN_SUST --ERIOS 11/04/2008 Origen

            WHEN PROD.VAL_FRAC_VTA_SUG IS NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S' THEN

                  PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||

                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,TRIM(PROD_LOCAL.UNID_VTA)) || 'Ã' ||


          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
          		   NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 inD_ORIGEN_SUST --ERIOS 11/04/2008 Origen

         ELSE
                  PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||

                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,TRIM(PROD_LOCAL.UNID_VTA)) || 'Ã' ||

          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
          		   NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 inD_ORIGEN_SUST --ERIOS 11/04/2008 Origen

                 END



          FROM   LGT_PROD PROD,
          		   LGT_PROD_LOCAL PROD_LOCAL,
          		   LGT_LAB LAB,
          		   PBL_IGV IGV,
                 LGT_PROD_VIRTUAL PR_VRT,
                 --21/11/2007 DUBILLUZ MODIFICADO
                 (SELECT DISTINCT(V1.COD_PROD) COD
                  FROM  (SELECT COD_PAQUETE,COD_PROD
                         FROM   VTA_PROD_PAQUETE
                         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                         ) V1,
                         VTA_PROMOCION    P
                  WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                  AND    P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                  AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    P.ESTADO  = 'A'
                  AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                          OR
                           P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
                ) Z,
                (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                        PROD_ENCARTE.COD_ENCARTE,
                        PROD_ENCARTE.COD_PROD
                 FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                        VTA_ENCARTE ENCARTE
                 WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
                 AND    ENCARTE.ESTADO = 'A'
                 AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
                 AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
                 )   V2
          WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
          AND    (PROD_LOCAL.STK_FISICO) > 0
          AND	   PROD_LOCAL.COD_PROD IN ((
                                        -- 1. obtener los productos con mismos principios activos
                                         select cod_prod
                                         from   lgt_prod pp
                                         where  COD_GRUPO_CIA = cCodGrupoCia_in
                                         AND    cod_prod in (select cod_prod
                                                             from   lgt_princ_act_prod
                                                             where  COD_GRUPO_CIA = cCodGrupoCia_in

                                                             AND    (cod_prod > cCodProd_in or cod_prod < cCodProd_in)
                                                             and    cod_princ_act in (select cod_princ_act
                                                                                      from   lgt_princ_act_prod
                                                                                      where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                                                      AND    cod_prod      = cCodProd_in)
                                                             group by cod_prod
                                                             having count(*) = (select count(*)
                                                                                from   lgt_princ_act_prod
                                                                                where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                                                AND    cod_prod      =cCodProd_in))
                                         and cod_prod IN (select cod_prod
                                                          from   lgt_princ_act_prod
                                                          where  COD_GRUPO_CIA =cCodGrupoCia_in
--
                                                          AND    (cod_prod > cCodProd_in or cod_prod < cCodProd_in)
                                                          group by cod_prod
                                                          having count(*) = (select count(*)
                                                                             from   lgt_princ_act_prod
                                                                             where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                                             AND    cod_prod = cCodProd_in)))
                                             UNION
                                             --asolis
                                              -- 2. obtener los productos que pertenecen al mismo grupo lgt_cab_sust_cab y lgt_cab_sust_det
                                             (
                                             select cod_prod
                                             from   lgt_prod PP
                                             where  COD_GRUPO_CIA =cCodGrupoCia_in
                                             AND    EXISTS       (SELECT 1
                                                                  FROM  LGT_CAT_SUST_CAB C,
                                                                        LGT_CAT_SUST_DET D
                                                                  WHERE C.COD_CAT_SUST = D.COD_CAT_SUST
                                                                  AND   D.cod_ind_cat  = PP.cod_prod
--
                                                                  AND  (D.cod_ind_cat > cCodProd_in or D.cod_ind_cat < cCodProd_in )
                                                                  AND   C.IND_CAT = 'PROD'
                                                                  AND   C.EST_CAT = 'A'
                                                                  and   EXISTS            (SELECT 1
                                                                                           FROM   LGT_CAT_SUST_CAB C1,
                                                                                                  LGT_CAT_SUST_DET D
                                                                                           WHERE  C1.COD_CAT_SUST = D.COD_CAT_SUST
                                                                                           AND    C1.COD_CAT_SUST = C.COD_CAT_SUST
                                                                                           AND    C1.IND_CAT      = 'PROD'
                                                                                           AND    C1.EST_CAT      = 'A'
                                                                                           AND    D.COD_IND_CAT   = cCodProd_in))
                                            )

                                           --asolis
                                           -- 3. obtener los productos que pertenecen al mismo grupo lgt_grupo_similar y lgt_prod_grupo_similar

                                               UNION
                                                (select cod_prod
                                                 from   lgt_prod pp
                                                 where  COD_GRUPO_CIA = cCodGrupoCia_in

                                                 and  exists (select  1
                                                                FROM  lgt_prod_grupo_similar p,lgt_grupo_similar g
                                                                where p.cod_grupo     = g.cod_grupo
                                                                and   p.cod_grupo_cia = cCodGrupoCia_in

                                                                and   (p.cod_prod     > cCodProd_in or p.cod_prod     < cCodProd_in)
                                                                and   p.est_prod_grupo_similar='A'
                                                                and   g.est_grupo_similar='A'
                                                                and   p.cod_prod=pp.cod_prod
                                                                and   p.cod_grupo IN (SELECT cod_grupo
                                                                                   FROM lgt_prod_grupo_similar
                                                                                   WHERE cod_prod = cCodProd_in)))

                           )


          AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
          AND	   PROD.COD_LAB = LAB.COD_LAB
          --AND	   LAB.IND_LAB_PROPIO = 'S' --LABORATORIO PROPIO
          AND	   PROD.COD_IGV = IGV.COD_IGV
          AND	   PROD.EST_PROD = 'A'
          AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
          AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
          AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
          AND    PROD.COD_PROD = Z.COD (+)
          AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
          AND    PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
          AND   exists( SELECT 1
                                      FROM LGT_PROD     PADRE,
                                           LGT_REL_UNID UPADRE,
                                           LGT_REL_UNID UHIJO,
                                           LGT_PROD     HIJO
                                      WHERE HIJO.EST_PROD ='A'
                                            AND PADRE.COD_PROD = cCodProd_in
                                            and HIJO.cod_prod=PROD.COD_PROD
                                            and HIJO.COD_GRUPO_CIA=cCodGrupoCia_in
                                            and PADRE.COD_GRUPO_CIA=cCodGrupoCia_in
                                            AND UPADRE.COD_UNID_MEDIDA = PADRE.COD_UNID_MIN_FRAC
                                            AND UHIJO.COD_REL = UPADRE.COD_REL
                                            AND HIJO.COD_UNID_MIN_FRAC = UHIJO.COD_UNID_MEDIDA
                                    ) --JCORTEZ 18.04.08 productos con misma unidad de medida
                                    --JLUNA USO DE EXISTS EN LUGAR DE IN
         -- ORDER BY NVL(to_char(prod.val_bono_vig/PROD_LOCAL.VAL_FRAC_LOCAL,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ') DESC
         --asolis
          ORDER BY NVL(PROD_LOCAL.IND_ZAN || TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ') DESC)
loop
  nnn:=nnn+1;
end loop;


---------------
xxx:=0;
SELECT count(1)
    into   xxx
    FROM   VTA_CAMPANA_CUPON  C
    WHERE  TRUNC(SYSDATE) BETWEEN C.FECH_INICIO AND  C.FECH_FIN
           AND    C.TIP_CAMPANA = 'A'--TIPO CAMPAÑA DE ACUMULACION
           AND    C.ESTADO      = 'A'--ESTADO ACTIVO
           AND    C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    EXISTS (
                          SELECT 1 FROM VTA_CAMPANA_PROD VCP
                          WHERE  C.COD_GRUPO_CIA  = VCP.COD_GRUPO_CIA
                          AND    C.COD_CAMP_CUPON = VCP.COD_CAMP_CUPON
                          AND    ( cCodProd_in = 'T' OR VCP.COD_PROD = cCodProd_in)
                          )--cCodProd_in = 'T' pàra mostrar todos

           AND    C.COD_CAMP_CUPON IN (
                                       --JCORTEZ 19.10.09 cambio de logica
                                      SELECT *
                                        FROM   (
                                               SELECT X.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON X
                                               WHERE X.COD_GRUPO_CIA='001'
                                               AND X.TIP_CAMPANA='A'
                                               AND X.ESTADO='A'
                                               AND X.IND_CADENA='S'
                                               UNION
                                               SELECT Y.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON Y
                                               WHERE Y.COD_GRUPO_CIA='001'
                                               AND Y.TIP_CAMPANA='A'
                                               AND Y.ESTADO='A'
                                               AND Y.IND_CADENA='N'
                                               AND Y.COD_CAMP_CUPON IN (SELECT COD_CAMP_CUPON
                                                                        FROM   VTA_CAMP_X_LOCAL Z
                                                                        WHERE  Z.COD_GRUPO_CIA =cCodGrupoCia_in
                                                                        AND    Z.COD_LOCAL = cCodLocal_in
                                                                        AND    Z.ESTADO = 'A')

                                               )
                                      )
           AND  C.COD_CAMP_CUPON IN (
                                    SELECT *
                                    FROM
                                        (
                                          SELECT *
                                          FROM
                                          (
                                            SELECT COD_CAMP_CUPON
                                            FROM   VTA_CAMPANA_CUPON
                                            MINUS
                                            SELECT H.COD_CAMP_CUPON
                                            FROM   VTA_CAMP_HORA H
                                          )
                                          UNION
                                          SELECT H.COD_CAMP_CUPON
                                          FROM   VTA_CAMP_HORA H
                                          WHERE  TRIM(TO_CHAR(SYSDATE,'HH24')) BETWEEN H.HORA_INICIO  AND H.HORA_FIN
                                        ))
           AND  DECODE(C.DIA_SEMANA,NULL,'S',
                        DECODE(C.DIA_SEMANA,REGEXP_REPLACE(C.DIA_SEMANA,NNumDia,'S'),'N','S')
                        ) = 'S';
----
select count(1)
         into   nCant
         from   ca_cli_camp c,
                vta_campana_cupon ca,
                vta_campana_prod cp
         where  c.dni_cli = cDni_in
         and    ca.estado = 'A'
         and    ca.tip_campana = 'A'
         and    c.cod_grupo_cia = ca.cod_grupo_cia
         and    c.cod_camp_cupon = ca.cod_camp_cupon
         and    ca.cod_grupo_cia = cp.cod_grupo_cia
         and    ca.cod_camp_cupon = cp.cod_camp_cupon
         and    cp.cod_prod  = cCodProd_in;
end;
  procedure revisa_varias_veces is
  fecha1 timestamp;
  fecha2 timestamp;
  xdiferencia_segs number;
  cCodLocal_in  char(3);
  begin
  dbms_output.enable;
  select distinct cod_local into cCodLocal_in from vta_impr_local;
  select SYSTIMESTAMP  into fecha1 from dual;
  revisa_prod;
  revisa_prod;
  revisa_prod;
  revisa_prod;
  revisa_prod;
  revisa_prod;
  revisa_prod;
  revisa_prod;
  revisa_prod;
  revisa_prod;
  select SYSTIMESTAMP  into fecha2 from dual;

--  select (fecha2-fecha1)*24*60*60 into xdiferencia_segs from dual;
--  dbms_output.put_line(cCodLocal_in||to_char(xdiferencia_segs )) ;
--  dbms_output.put_line(cCodLocal_in||'--'||to_char(trunc(EXTRACT(second FROM(fecha2-fecha1))) ));
dbms_output.put_line(cCodLocal_in||'--'||to_char(((extract(hour from fecha2)-extract(hour from fecha1))*3600+
 (extract(minute from fecha2)-extract(minute from fecha1))*60+
 extract(second from fecha2)-extract(second from fecha1))*1000));
  end;


end dba_test_perf;
/

