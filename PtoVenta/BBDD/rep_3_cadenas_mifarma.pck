CREATE OR REPLACE PACKAGE PTOVENTA."REP_3_CADENAS_MIFARMA" AS

  TYPE FarmaCursor IS REF CURSOR;

  C_TIP_LOCAL_PEQUENO     NUMBER := '1';
  C_TIP_LOCAL_PEQUENO_DOS NUMBER := '2';
  C_MOTIVO_SAL_INSUMO     CHAR(3):='522';
  /* ************************************************************* */
  PROCEDURE INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  vFecha_in IN VARCHAR2);
  PROCEDURE INV_CALC_VTA_SIN_PROM(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  vFecha_in IN VARCHAR2);
  PROCEDURE REP_MOVER_STK_CAMBIO_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProdAnt_in IN CHAR, cCodProdNue_in IN CHAR, dFecha_in IN DATE);
  PROCEDURE P_CREA_RES_VTA_PROD_LOCAL(cCodGrupoCia_in IN VARCHAR2,
                                      cCodLocal_in    IN VARCHAR2);

  /* ************************************************************* */
  PROCEDURE P_PROCESO_CALCULA_PICOS(cCodGrupoCia_in IN VARCHAR2,
                                    cCodLocal_in    IN VARCHAR2);

  /* ************************************************************* */
  PROCEDURE P_STK_LOCAL_AND_TRANSITO(cCodLocal_in IN VARCHAR2);

  /* ************************************************************* */
  function f_get_trans_prod(cCodGrupoCia_in in char,
                            cCodLocal_in    in char,
                            cCodProd_in     in char) return number;
  function f_get_pvm_prod(cCodLocal_in in char, cCodProd_in in char)
    return number;

 function f_get_stk_calculado
    (vTipStk varchar2,cCantUnidVta_S number,cCantUnidVta_M number,cFrec number,cCodLocal char,
    vCodProd_in varchar2
    ) return number;

  /* ************************************************************* */
  PROCEDURE P_VTA_MES_PROD_LOCAL(cCodLocal_in in char);
  procedure P_VTA_PVM_PROD_LOCAL(cCodLocal_in in char);
  /* ************************************************************* */

  PROCEDURE P_PROD_LOCAL_SEMANA(cCodLocal_in in char);
  /* ************************************************************* */
  PROCEDURE P_AUX_MEJOR_PROD_LOCAL(cCodLocal_in in char);
  /* ************************************************************* */
  PROCEDURE P_CALCULO_PED_REP_LOCAL(cCodLocal_in in char);
  /* *************************************************************** */
  PROCEDURE P_OPERA_ALGORITMO_MF(cCodLocal_in in char);
  /* *************************************************************** */
  procedure P_ACTUALIZA_PROD_LOCAL_REP(cCodLocal_in in char);

  procedure P_CALCULO_PROMO_COMPRA(cCodLocal_MF varchar2);

  /* *************************************************************** */
  PROCEDURE REP_ACTUALIZA_CANT_SUG(vCodGrupoCia IN CHAR, vCodLocal IN CHAR,
                                   vCodGrupo IN CHAR DEFAULT NULL);
 /* *************************************************************** */
  PROCEDURE VERIFICA_VENTAS_REP(vCodLocal IN CHAR);
 /* *************************************************************** */                                   

END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."REP_3_CADENAS_MIFARMA" AS

 PROCEDURE INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  vFecha_in IN VARCHAR2)
  AS
    v_dFecha DATE;
    CURSOR curCambio IS
    SELECT C.COD_PROD_ANT,C.COD_PROD_NUE
    FROM LGT_CAMBIO_PROD C, VTA_RES_HIST_PROD_LOCAL A
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.EST_CAMBIO_PROD = 'A'
          AND A.COD_LOCAL = cCodLocal_in
          AND A.FEC_DIA_VTA = v_dFecha
          AND C.COD_GRUPO_CIA = A.COD_GRUPO_CIA
          AND C.COD_PROD_ANT = A.COD_PROD
    ORDER BY C.FEC_INI_CAMBIO_PROD;
    --fec_ini
    rowCambio curCambio%ROWTYPE;

    CURSOR curHistorico IS
    SELECT C.COD_PROD_ANT, C.COD_PROD_NUE, A.FEC_DIA_VTA
      FROM VTA_RES_HIST_PROD_LOCAL A, LGT_CAMBIO_PROD C
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND C.EST_CAMBIO_PROD = 'A'
       AND A.CANT_UNID_VTA <> 0
--       AND A.FEC_DIA_VTA BETWEEN TRUNC(v_dFecha - 90) AND TRUNC(v_dFecha)
       AND A.FEC_DIA_VTA BETWEEN ADD_MONTHS(TRUNC(v_dFecha,'MM'),-3) AND TRUNC(v_dFecha)
       AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
       AND A.COD_PROD = C.COD_PROD_ANT
    ORDER BY C.FEC_INI_CAMBIO_PROD;
    rowHistorico curHistorico%ROWTYPE;

  BEGIN
    v_dFecha := TO_DATE(vFecha_in,'dd/MM/yyyy');

    --VTA_RES_VTA_PROD_LOCAL
    DELETE VTA_RES_VTA_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND FEC_DIA_VTA = v_dFecha AND COD_LOCAL = cCodLocal_in;
    --DBMS_OUTPUT.PUT_LINE(vFecha_in);

    INSERT INTO VTA_RES_VTA_PROD_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
    SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,v_dFecha,
           SUM(CANT_ATENDIDA/VAL_FRAC) AS CANT_ATENDIDA,
           SUM(VAL_PREC_TOTAL) AS VAL_PREC_TOTAL,
           SYSDATE
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.EST_PED_VTA = 'C'
          AND TRUNC(C.FEC_PED_VTA) = v_dFecha
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD
    HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0 AND SUM(VAL_PREC_TOTAL) != 0
    UNION--XXXX
    SELECT K.COD_GRUPO_CIA,
           k.cod_local,
           k.cod_prod,
           v_dFecha,
           sum(k.cant_mov_prod / k.val_fracc_prod) * (-1) CANT_ATENDIDA,
           to_number('0', 990) VAL_PREC_TOTAL,
           SYSDATE
      FROM LGT_KARDEX K, LGT_PROD l
     where k.cod_grupo_cia = l.cod_grupo_cia
       and k.cod_prod = l.cod_prod
       and k.cod_grupo_cia = cCodGrupoCia_in
       and k.cod_local = cCodLocal_in
       and l.cod_grupo_rep = '010'
--@@  INICIO
--@@  RHERRERA: Modificaci髇 de la condici髇.
--@@  Fecha   : 29.09.2014
--@@  Motivo  : Solo acpetara la condici髇 de motivo ='522' Salida de Insumos
--      and       ( k.cant_mov_prod    < 0
--              and       k.cod_mot_kardex in ('522','523'))--MOTIVO
        and       k.cod_mot_kardex = C_MOTIVO_SAL_INSUMO--MOTIVO
--       and   k.cod_mot_kardex = '522' -- unicamente motivo SALIDA DE INSUMOS 26.09.2014
--@@@ FIN
       and trunc(K.FEC_KARDEX) = v_dFecha --to_char(sysdate, 'DD/MM/YYYY') --
     group by k.cod_grupo_cia, k.cod_local, k.cod_prod
    having     sum(k.cant_mov_prod / k.val_fracc_prod) != 0;  --26.09.2014    

    --VTA_RES_VTA_REP_LOCAL
    DELETE VTA_RES_VTA_REP_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND FEC_DIA_VTA = v_dFecha AND COD_LOCAL = cCodLocal_in;
    --DBMS_OUTPUT.PUT_LINE(vFecha_in);

    INSERT INTO VTA_RES_VTA_REP_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
    SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,v_dFecha,
           SUM(CANT_ATENDIDA/VAL_FRAC) AS CANT_ATENDIDA,
           SUM(VAL_PREC_TOTAL) AS VAL_PREC_TOTAL,
           SYSDATE
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.EST_PED_VTA = 'C'
          AND C.TIP_PED_VTA IN ('01','02')
          AND TRUNC(C.FEC_PED_VTA) = v_dFecha
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD
    HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0
    UNION---XXX
    SELECT K.COD_GRUPO_CIA,
           k.cod_local,
           k.cod_prod,
           v_dFecha,
           sum(k.cant_mov_prod / k.val_fracc_prod) * (-1) CANT_ATENDIDA,
           to_number('0', 990) VAL_PREC_TOTAL,
           SYSDATE
      FROM LGT_KARDEX K, LGT_PROD l
     where k.cod_grupo_cia = l.cod_grupo_cia
       and k.cod_prod = l.cod_prod
       and k.cod_grupo_cia = cCodGrupoCia_in
       and k.cod_local = cCodLocal_in
       and l.cod_grupo_rep = '010'
--@@  INICIO
--@@  RHERRERA: Modificaci髇 de la condici髇.
--@@  Fecha   : 29.09.2014
--@@  Motivo  : Solo acpetara la condici髇 de motivo ='522' Salida de Insumos
--      and       ( k.cant_mov_prod    < 0
--              and       k.cod_mot_kardex in ('522','523'))--MOTIVO
        and       k.cod_mot_kardex = C_MOTIVO_SAL_INSUMO--MOTIVO
--       and   k.cod_mot_kardex = '522' -- unicamente motivo SALIDA DE INSUMOS 26.09.2014
--@@@ FIN
       and trunc(K.FEC_KARDEX) = v_dFecha --to_char(sysdate, 'DD/MM/YYYY') --
     group by k.cod_grupo_cia, k.cod_local, k.cod_prod
    having     sum(k.cant_mov_prod / k.val_fracc_prod) != 0;  --26.09.2014

    --VTA_RES_HIST_PROD_LOCAL
    DELETE VTA_RES_HIST_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND FEC_DIA_VTA = v_dFecha AND COD_LOCAL = cCodLocal_in;
    --DBMS_OUTPUT.PUT_LINE(vFecha_in);

    INSERT INTO VTA_RES_HIST_PROD_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,
    CANT_UNID_VTA,cant_unid_vta_TOTAL,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
    SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,v_dFecha,
           SUM(CANT_ATENDIDA/VAL_FRAC) AS CANT_ATENDIDA,
           SUM(CANT_ATENDIDA/VAL_FRAC) AS CANT_ATENDIDA,
           SUM(VAL_PREC_TOTAL) AS VAL_PREC_TOTAL,
           SYSDATE
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.EST_PED_VTA = 'C'
          AND C.TIP_PED_VTA IN ('01','02')
          AND D.IND_CALCULO_MAX_MIN = 'S'
          AND TRUNC(C.FEC_PED_VTA) = v_dFecha
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD
    HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0
    UNION----- rherrera 14.08.2014 XXX
    SELECT K.COD_GRUPO_CIA,
           k.cod_local,
           k.cod_prod,
           v_dFecha,
           sum(k.cant_mov_prod / k.val_fracc_prod) * (-1) CANT_ATENDIDA,
           sum(k.cant_mov_prod / k.val_fracc_prod) * (-1) CANT_ATENDIDA,
           to_number('0', 990) VAL_PREC_TOTAL,
           SYSDATE
      FROM LGT_KARDEX K, LGT_PROD l
     where k.cod_grupo_cia = l.cod_grupo_cia
       and k.cod_prod = l.cod_prod
       and k.cod_grupo_cia = cCodGrupoCia_in
       and k.cod_local     = cCodLocal_in
       and l.cod_grupo_rep = '010'
--@@  INICIO
--@@  RHERRERA: Modificaci髇 de la condici髇.
--@@  Fecha   : 29.09.2014
--@@  Motivo  : Solo acpetara la condici髇 de motivo ='522' Salida de Insumos
--      and       ( k.cant_mov_prod    < 0
--              and       k.cod_mot_kardex in ('522','523'))--MOTIVO
        and       k.cod_mot_kardex = C_MOTIVO_SAL_INSUMO--MOTIVO
--       and   k.cod_mot_kardex = '522' -- unicamente motivo SALIDA DE INSUMOS 26.09.2014
--@@@ FIN
       and trunc(K.FEC_KARDEX) = v_dFecha --to_char(sysdate, 'DD/MM/YYYY') --
     group by k.cod_grupo_cia, k.cod_local, k.cod_prod
    having     sum(k.cant_mov_prod / k.val_fracc_prod) != 0;  --26.09.2014

-- calcula venta del dia
    --VTA_RES_HIST_PROD_LOCAL
    DELETE VTA_RES_HIST_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND FEC_DIA_VTA = v_dFecha+1 AND COD_LOCAL = cCodLocal_in;
    --DBMS_OUTPUT.PUT_LINE(vFecha_in);

    INSERT INTO VTA_RES_HIST_PROD_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,
    CANT_UNID_VTA,cant_unid_vta_TOTAL,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
    SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,v_dFecha+1,
           SUM(CANT_ATENDIDA/VAL_FRAC) AS CANT_ATENDIDA,
           SUM(CANT_ATENDIDA/VAL_FRAC) AS CANT_ATENDIDA,
           SUM(VAL_PREC_TOTAL) AS VAL_PREC_TOTAL,
           SYSDATE
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.EST_PED_VTA = 'C'
          AND C.TIP_PED_VTA IN ('01','02')
          AND D.IND_CALCULO_MAX_MIN = 'S'
          AND TRUNC(C.FEC_PED_VTA) = v_dFecha+1
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD
    HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0
    UNION----- rherrera 14.08.2014 XXX
    SELECT K.COD_GRUPO_CIA,
           k.cod_local,
           k.cod_prod,
           v_dFecha+1,
           sum(k.cant_mov_prod / k.val_fracc_prod) * (-1) CANT_ATENDIDA,
           sum(k.cant_mov_prod / k.val_fracc_prod) * (-1) CANT_ATENDIDA,
           to_number('0', 990) VAL_PREC_TOTAL,
           SYSDATE
      FROM LGT_KARDEX K, LGT_PROD l
     where k.cod_grupo_cia = l.cod_grupo_cia
       and k.cod_prod = l.cod_prod
       and k.cod_grupo_cia = cCodGrupoCia_in
       and k.cod_local     = cCodLocal_in
       and l.cod_grupo_rep = '010'
--@@  INICIO
--@@  RHERRERA: Modificaci髇 de la condici髇.
--@@  Fecha   : 29.09.2014
--@@  Motivo  : Solo acpetara la condici髇 de motivo ='522' Salida de Insumos
--      and       ( k.cant_mov_prod    < 0
--              and       k.cod_mot_kardex in ('522','523'))--MOTIVO
        and       k.cod_mot_kardex = C_MOTIVO_SAL_INSUMO--MOTIVO
--       and   k.cod_mot_kardex = '522' -- unicamente motivo SALIDA DE INSUMOS 26.09.2014
--@@@ FIN
       and trunc(K.FEC_KARDEX) = v_dFecha+1 --to_char(sysdate, 'DD/MM/YYYY') --
     group by k.cod_grupo_cia, k.cod_local, k.cod_prod
    having     sum(k.cant_mov_prod / k.val_fracc_prod) != 0;  --26.09.2014


    -- CALCULA VTA SIN PROMOCION
    -- DUBILLUZ 16.08.2013
    INV_CALC_VTA_SIN_PROM(cCodGrupoCia_in,cCodLocal_in,vFecha_in);

    ---JQUISPE 23/03/2012 , SE PIDIO QUE NO TOME EN CUENTA LOS PRODUCTOS QUE ESTEN EN CAMPA每`A IMBATIBLES MARZO
/*    DELETE FROM VTA_RES_VTA_PROD_LOCAL V WHERE V.COD_PROD IN (SELECT T.COD_PROD FROM TMP_PROD_EXCLUIR_VTA T)   AND TRUNC(V.FEC_DIA_VTA)  BETWEEN TO_DATE('08/03/2012','DD/MM/YYYY') AND TO_DATE('22/03/2012','DD/MM/YYYY');
    DELETE FROM VTA_RES_VTA_REP_LOCAL  V1 WHERE V1.COD_PROD IN (SELECT T.COD_PROD FROM TMP_PROD_EXCLUIR_VTA T) AND TRUNC(V1.FEC_DIA_VTA) BETWEEN TO_DATE('08/03/2012','DD/MM/YYYY') AND TO_DATE('22/03/2012','DD/MM/YYYY');
    DELETE FROM VTA_RES_HIST_PROD_LOCAL V2 WHERE V2.COD_PROD IN (SELECT T.COD_PROD FROM TMP_PROD_EXCLUIR_VTA T) AND TRUNC(V2.FEC_DIA_VTA) BETWEEN TO_DATE('08/03/2012','DD/MM/YYYY') AND TO_DATE('22/03/2012','DD/MM/YYYY');*/
    DELETE FROM VTA_RES_VTA_PROD_LOCAL V
    where  exists (
                   SELECT 1
                   FROM   TMP_PROD_EXCLUIR_VTA T
                   where  t.cod_prod = v.cod_prod
                   and    v.fec_dia_vta between trunc(t.fech_inicio) and trunc(t.fech_fin) + 1 -1/24/60/60
                   );

    DELETE FROM VTA_RES_VTA_REP_LOCAL V1
    where  exists (
                   SELECT 1
                   FROM   TMP_PROD_EXCLUIR_VTA T
                   where  t.cod_prod = v1.cod_prod
                   and    v1.fec_dia_vta between trunc(t.fech_inicio) and trunc(t.fech_fin) + 1 -1/24/60/60
                   );

    DELETE FROM VTA_RES_HIST_PROD_LOCAL V2
    where  exists (
                   SELECT 1
                   FROM   TMP_PROD_EXCLUIR_VTA T
                   where  t.cod_prod = v2.cod_prod
                   and    v2.fec_dia_vta between trunc(t.fech_inicio) and trunc(t.fech_fin) + 1 -1/24/60/60
                   );

    --MUEVE HISTORICO
    FOR rowHistorico IN curHistorico
    LOOP
      REP_MOVER_STK_CAMBIO_PROD(cCodGrupoCia_in, cCodLocal_in,
      rowHistorico.COD_PROD_ANT, rowHistorico.COD_PROD_NUE, rowHistorico.FEC_DIA_VTA);
    END LOOP;
    --MUEVE VENTAS DEL DIA
    FOR rowCambio IN curCambio
    LOOP
      REP_MOVER_STK_CAMBIO_PROD(cCodGrupoCia_in, cCodLocal_in,
      rowCambio.COD_PROD_ANT, rowCambio.COD_PROD_NUE, v_dFecha);
    END LOOP;

    COMMIT;
  END;
  ---------------------------------------------------------------------------------------------
/* ****************************************************************** */
PROCEDURE INV_CALC_VTA_SIN_PROM(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  vFecha_in IN VARCHAR2)
  as
  nCantSinPromo VTA_RES_HIST_PROD_LOCAL.CANT_UNID_VTA_SIN_PROMO%type;
  nUnidVendidas number;
  nCantDias     number;
  nExisteProm number;
  nFechIni date;
begin
  -- NULL;
  for lista in (
                select t.cod_prod,t.cant_unid_vta
                from   VTA_RES_HIST_PROD_LOCAL t
                where  t.cod_grupo_cia = cCodGrupoCia_in
                and    t.cod_local = cCodLocal_in
                and    t.fec_dia_vta = to_date(vFecha_in,'dd/mm/yyyy')
               ) loop

       nUnidVendidas := lista.cant_unid_vta;

       Select count(1)
         Into nExisteProm
         From CodoProm
        Where tcodimifa = lista.cod_prod
          And to_date(vFecha_in,'dd/mm/yyyy') between TFechInic and TFechFin;

      if nExisteProm > 0 then

       Select v.tfechinic
         Into nFechIni
         From CodoProm v
        Where tcodimifa = lista.cod_prod
          And to_date(vFecha_in,'dd/mm/yyyy') between TFechInic and TFechFin
          and rownum = 1 ;

        select sum(CANT_UNID_VTA_TOTAL),
               sum(
               case
                 when CANT_UNID_VTA_TOTAL > 0 then 1 else 0
               end )
        into   nUnidVendidas,nCantDias
        from   VTA_RES_HIST_PROD_LOCAL t
        where  t.cod_grupo_cia = cCodGrupoCia_in
        and    t.cod_local = cCodLocal_in
        and    t.cod_prod = lista.cod_prod
        and    t.fec_dia_vta <= nFechIni
        and    t.fec_dia_vta >= nFechIni - 30;

        nCantSinPromo := round((nUnidVendidas / nCantDias),2);

      else

        nCantSinPromo  := nUnidVendidas;

      end if;

        update vta_res_hist_prod_local lo
        set    lo.cant_unid_vta_sin_promo = nCantSinPromo
        where  lo.cod_grupo_cia = cCodGrupoCia_in
        and    lo.cod_local = cCodLocal_in
        and    lo.cod_prod = lista.cod_prod
        and    lo.fec_dia_vta = to_date(vFecha_in,'dd/mm/yyyy');

  end loop;

end;
/* ****************************************************************** */
PROCEDURE REP_MOVER_STK_CAMBIO_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProdAnt_in IN CHAR, cCodProdNue_in IN CHAR, dFecha_in IN DATE)
  AS
    v_nCant VTA_RES_VTA_REP_LOCAL.CANT_UNID_VTA%TYPE;
    v_nMonTot VTA_RES_VTA_REP_LOCAL.MON_TOT_VTA%TYPE;

  BEGIN
      SELECT CANT_UNID_VTA,MON_TOT_VTA
        INTO v_nCant,v_nMonTot
      FROM VTA_RES_HIST_PROD_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = cCodProdAnt_in
            AND FEC_DIA_VTA = dFecha_in FOR UPDATE;
      BEGIN
        INSERT INTO VTA_RES_HIST_PROD_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,
        CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
        VALUES(cCodGrupoCia_in,cCodLocal_in,cCodProdNue_in,dFecha_in,
        v_nCant,v_nMonTot,SYSDATE);
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE VTA_RES_HIST_PROD_LOCAL
          SET CANT_UNID_VTA = CANT_UNID_VTA + v_nCant,
              MON_TOT_VTA = MON_TOT_VTA + v_nMonTot,
              FEC_CREA_VTA_PROD_LOCAL = SYSDATE
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND COD_PROD = cCodProdNue_in
                AND FEC_DIA_VTA = dFecha_in;
      END;

      UPDATE VTA_RES_HIST_PROD_LOCAL
      SET CANT_UNID_VTA = 0,
          MON_TOT_VTA = 0,
          FEC_CREA_VTA_PROD_LOCAL = SYSDATE
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = cCodProdAnt_in
            AND FEC_DIA_VTA = dFecha_in;
  END;
/*****************************************************************************/

    PROCEDURE P_CREA_RES_VTA_PROD_LOCAL(cCodGrupoCia_in IN VARCHAR2,
                                      cCodLocal_in    IN VARCHAR2) IS

  BEGIN
     -- este proceso se copio del REP ACTUAL
     -- debido que el proceso de cargar VENTAS
     -- mueve las ventas de
        -- x Cambio de Codigo
        -- x Exclucion de Venta promocion.
        -- x exclucion de Prod.
     INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE-1,'dd/MM/yyyy'));
     COMMIT;
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_VTA_RES_VTA_PROD_LOCAL DROP STORAGE';

    INSERT INTO MF_VTA_RES_VTA_PROD_LOCAL NOLOGGING
      (COD_LOCAL_MF,
       COD_PROD_MF,
       FEC_DIA_VTA,
       CANT_UNID_VTA,
       MON_TOT_VTA,
       COS_TOT_VTA,
       CANT_UNID_VTA_SIN_PROM,
       CANT_UNID_VTA_TOT,
       MES
       )
      SELECT C.COD_LOCAL,
             COD_PROD,
             TRUNC(C.FEC_DIA_VTA),
             0, -- EMG
             SUM(MON_TOT_VTA),
             0,
             SUM(CANT_UNID_VTA),
             SUM(CANT_UNID_VTA_SIN_PROMO),
             TO_NUMBER(TO_CHAR(C.FEC_DIA_VTA,'YYYYMM'))
        FROM VTA_RES_HIST_PROD_LOCAL C
        where FEC_DIA_VTA between Add_months(trunc(sysdate,'MM'), -4) and sysdate
        GROUP BY C.COD_LOCAL,
             COD_PROD,
             TRUNC(C.FEC_DIA_VTA),
             TO_NUMBER(TO_CHAR(C.FEC_DIA_VTA,'YYYYMM'));

    COMMIT;
  END;
  /* *********************************************************************************** */
  PROCEDURE P_PROCESO_CALCULA_PICOS(cCodGrupoCia_in IN VARCHAR2,
                                    cCodLocal_in    IN VARCHAR2) IS

       D_FECHA_PROCESO DATE := NULL; --
       D_DIAS_TOPE_MES INTEGER := 5;

         -- PARA ACTUALIZAR DATOS DEL MES CERRADO
       CURSOR CUR1 (C_COD_LOCAL_MF_IN IN CHAR, C_FECHA_IN IN DATE) IS
            SELECT /*+ INDEX (MF_VTA_RES_VTA_PROD_LOCAL IX_MF_VTA_RES_VTA_PROD_LOCAL2_02) */
                  COD_LOCAL_MF,
                  COD_PROD_MF,
                  FEC_DIA_VTA,
                  MES
            FROM MF_VTA_RES_VTA_PROD_LOCAL
            WHERE MES = TO_NUMBER(TO_CHAR(C_FECHA_IN - D_DIAS_TOPE_MES,'YYYYMM'))
              AND COD_LOCAL_MF = C_COD_LOCAL_MF_IN
            ORDER BY
                  COD_LOCAL_MF,
                  COD_PROD_MF,
                  FEC_DIA_VTA;


         -- PARA ACTUALIZAR DATOS DEL MES VIGENTE
       CURSOR CUR2 (C_COD_LOCAL_MF_IN IN CHAR, C_FECHA_IN IN DATE) IS
            SELECT /*+ INDEX (MF_VTA_RES_VTA_PROD_LOCAL IX_MF_VTA_RES_VTA_PROD_LOCAL2_02) */
                  COD_LOCAL_MF,
                  COD_PROD_MF,
                  FEC_DIA_VTA
            FROM MF_VTA_RES_VTA_PROD_LOCAL
            WHERE MES = TO_NUMBER(TO_CHAR(C_FECHA_IN,'YYYYMM'))
              AND COD_LOCAL_MF = C_COD_LOCAL_MF_IN
            ORDER BY
                  COD_LOCAL_MF,
                  COD_PROD_MF,
                  FEC_DIA_VTA;

         -- OBTIENE LOCALES POR ACTUALIZAR
       CURSOR CUR3 (C_FECHA_IN IN DATE) IS
            SELECT DISTINCT COD_LOCAL_MF
            FROM MF_VTA_RES_VTA_PROD_LOCAL
            WHERE MES BETWEEN TO_NUMBER(TO_CHAR(C_FECHA_IN - D_DIAS_TOPE_MES,'YYYYMM')) AND TO_NUMBER(TO_CHAR(C_FECHA_IN,'YYYYMM'))
              AND COD_LOCAL_MF = cCodLocal_in
            ORDER BY COD_LOCAL_MF;

       CURSOR CUR4 IS
            SELECT DISTINCT TO_DATE(MES,'YYYYMM') MES
            FROM MF_VTA_RES_VTA_PROD_LOCAL
            WHERE COD_LOCAL_MF = cCodLocal_in
            ORDER BY 1;

         N_REEMPLAZO            NUMBER;
         N_PROM_DIARIO_MES      NUMBER;
  BEGIN

      EXECUTE IMMEDIATE 'ALTER INDEX PK_MF_VTA_RES_VTA_PROD_LOCAL REBUILD';
      EXECUTE IMMEDIATE 'ALTER INDEX IX_VTA_RES_VTA_PROD_LOCAL_01 REBUILD';
      EXECUTE IMMEDIATE 'ALTER INDEX IX_VTA_RES_VTA_PROD_LOCAL_02 REBUILD';
      EXECUTE IMMEDIATE 'ALTER INDEX IX_VTA_RES_VTA_PROD_LOCAL_03 REBUILD';

       FOR LOC4 IN CUR4
       LOOP

           D_FECHA_PROCESO := LOC4.MES;

             FOR LOC3 IN CUR3 (D_FECHA_PROCESO)
             LOOP
                       -- ACTUALIZA DATA DEL MES ANTERIOR
                       IF TO_NUMBER(TO_CHAR(D_FECHA_PROCESO,'DD')) <= D_DIAS_TOPE_MES THEN
                           FOR LOC1 IN CUR1 (LOC3.COD_LOCAL_MF, D_FECHA_PROCESO)
                           LOOP
                               BEGIN

                                        SELECT /*+ INDEX (MF_VTA_RES_VTA_PROD_LOCAL IX_MF_VTA_RES_VTA_PROD_LOCAL2_03) */
                                              NVL(CASE WHEN COUNT(*) = 0 THEN 0 ELSE SUM(CASE WHEN CANT_UNID_VTA_SIN_PROM <= 0 THEN 0 ELSE CANT_UNID_VTA_SIN_PROM END) / COUNT(*) END,0) "PROM_DIARIO_MES"
                                        INTO N_PROM_DIARIO_MES
                                        FROM MF_VTA_RES_VTA_PROD_LOCAL R
                                        WHERE 1 = 1
                                          AND R.MES = LOC1.MES
                                          AND R.COD_LOCAL_MF = LOC1.COD_LOCAL_MF
                                          AND R.COD_PROD_MF = LOC1.COD_PROD_MF;

                                    UPDATE MF_VTA_RES_VTA_PROD_LOCAL T
                                    SET FEC_PROC_PICKS = SYSDATE,
                                        CANT_UNID_VTA =
                                              CASE
                                                   WHEN CANT_UNID_VTA_SIN_PROM < 0                   THEN CANT_UNID_VTA_SIN_PROM
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) > 20             THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 1.5 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) BETWEEN 6 AND 20 THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 2.0 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) = 5              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 2.5 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) = 4              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 3.0 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) = 3              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 3.5 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) = 2              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 4.0 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) = 1              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 5.0 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) >= 0             THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > 5.0                        THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                              END
                                    WHERE 1 = 1
                                      AND T.MES = LOC1.MES
                                      AND T.COD_LOCAL_MF = LOC1.COD_LOCAL_MF
                                      AND T.COD_PROD_MF = LOC1.COD_PROD_MF
                                      AND T.FEC_DIA_VTA = LOC1.FEC_DIA_VTA;

                                 COMMIT;

                                 -- DBMS_OUTPUT.put_line(LOC1.COD_LOCAL_MF ||';'|| LOC1.COD_PROD_MF ||';'|| TO_CHAR(LOC1.MES_INI,'YYYY-MM-DD') ||':'|| SQLERRM);

                               EXCEPTION
                                        WHEN OTHERS THEN
                                             DBMS_OUTPUT.put_line('ERR=' || LOC1.COD_LOCAL_MF ||';'|| LOC1.COD_PROD_MF ||';'|| LOC1.MES ||':'|| SQLERRM);
                               END;

                           END LOOP;
                       END IF;

                       -- ESTOY PROCESANDO UNA FECHA DEL MES ACTUAL
                       IF TRUNC(D_FECHA_PROCESO,'MM') = TRUNC(SYSDATE,'MM') THEN

                               -- ACTUALIZA DATA DEL MES ACTUAL
                               FOR LOC2 IN CUR2 (LOC3.COD_LOCAL_MF, D_FECHA_PROCESO)
                               LOOP
                                   BEGIN
                                        N_REEMPLAZO := 0;

                                        SELECT
                                              NVL(CASE WHEN SUM(CASE WHEN CANT_UNID_VTA_SIN_PROM <= 0 THEN 0 ELSE 1 END) = 0 THEN 0 ELSE SUM(CANT_UNID_VTA_SIN_PROM) / SUM(CASE WHEN CANT_UNID_VTA_SIN_PROM <= 0 THEN 0 ELSE 1 END) END,0) "CANT_UNID_VTA_SIN_PROM_NVO"
                                        INTO N_REEMPLAZO
                                        FROM
                                            (
                                              SELECT
                                                    COD_LOCAL_MF,
                                                    COD_PROD_MF,
                                                    FEC_DIA_VTA,
                                                    CANT_UNID_VTA_SIN_PROM,
                                                    RANK() OVER(PARTITION BY COD_LOCAL_MF, COD_PROD_MF ORDER BY FEC_DIA_VTA DESC) "PUESTO"
                                              FROM MF_VTA_RES_VTA_PROD_LOCAL R
                                              WHERE 1 = 1
                                                AND R.COD_LOCAL_MF = LOC2.COD_LOCAL_MF
                                                AND R.COD_PROD_MF = LOC2.COD_PROD_MF
                                                AND R.FEC_DIA_VTA < LOC2.FEC_DIA_VTA
                                            )
                                        WHERE PUESTO <= 7;

                                        SELECT /*+ INDEX (MF_VTA_RES_VTA_PROD_LOCAL IX_MF_VTA_RES_VTA_PROD_LOCAL2_03) */
                                              NVL(CASE WHEN COUNT(*) = 0 THEN 0 ELSE SUM(CASE WHEN CANT_UNID_VTA_SIN_PROM <= 0 THEN 0 ELSE CANT_UNID_VTA_SIN_PROM END) / COUNT(*) END,0) "PROM_DIARIO_MES"
                                        INTO N_PROM_DIARIO_MES
                                        FROM MF_VTA_RES_VTA_PROD_LOCAL R
                                        WHERE 1 = 1
                                          AND R.MES = TO_NUMBER(TO_CHAR(SYSDATE,'YYYYMM'))
                                          AND R.COD_LOCAL_MF = LOC2.COD_LOCAL_MF
                                          AND R.COD_PROD_MF = LOC2.COD_PROD_MF;

                                        -- DBMS_OUTPUT.put_line(LOC2.COD_LOCAL_MF ||';'|| LOC2.COD_PROD_MF ||';'|| LOC2.FEC_DIA_VTA ||';'|| N_REEMPLAZO);

                                        UPDATE MF_VTA_RES_VTA_PROD_LOCAL T
                                        SET FEC_PROC_PICKS = SYSDATE,
                                            CANT_UNID_VTA =
                                                CASE
                                                     WHEN CANT_UNID_VTA_SIN_PROM < 0                            THEN CANT_UNID_VTA_SIN_PROM
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) > 20             THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 1.5 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) BETWEEN 6 AND 20 THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 2.0 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) = 5              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 2.5 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) = 4              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 3.0 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) = 3              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 3.5 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) = 2              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 4.0 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) = 1              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 5.0 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) > 0              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > 5.0                     THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     ELSE CANT_UNID_VTA_SIN_PROM
                                                END
                                        WHERE T.COD_LOCAL_MF = LOC2.COD_LOCAL_MF
                                          AND T.COD_PROD_MF = LOC2.COD_PROD_MF
                                          AND T.FEC_DIA_VTA = LOC2.FEC_DIA_VTA;


                                   END;
                               END LOOP;

                               COMMIT;

                         END IF;

             END LOOP;

       END LOOP;


  END;
  /* *********************************************************************************** */
  PROCEDURE P_STK_LOCAL_AND_TRANSITO(cCodLocal_in IN VARCHAR2) IS
  BEGIN
    null;
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_VTA_STK_PROD_LOCAL DROP STORAGE';

    INSERT INTO MF_VTA_STK_PROD_LOCAL NOLOGGING
      (COD_PROD_MF, COD_LOCAL_MF, STK)
      SELECT S.COD_PROD COD_PROD_MF, S.COD_LOCAL COD_LOCAL_MF, S.STK_FISICO / S.VAL_FRAC_LOCAL
        FROM LGT_PROD_LOCAL S
       WHERE COD_LOCAL = cCodLocal_in;

    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_STK_TRAN_PROD_LOCAL DROP STORAGE';

    INSERT INTO MF_STK_TRAN_PROD_LOCAL NOLOGGING
      (COD_PROD_MF, COD_LOCAL_MF, STK)
      SELECT S.COD_PROD COD_PROD_MF,
             S.COD_LOCAL COD_LOCAL_MF,
             F_GET_TRANS_PROD('001', S.COD_LOCAL, S.COD_PROD)
        FROM LGT_PROD_LOCAL S
       WHERE COD_LOCAL = cCodLocal_in;

    COMMIT;

  END;
  /* *********************************************************************************** */
  function f_get_trans_prod(cCodGrupoCia_in in char,
                            cCodLocal_in    in char,
                            cCodProd_in     in char) return number is
    g_cTipoNotaRecepcion LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '03';
    v_nTrans             number;
  begin
    SELECT NVL(SUM(CANT_ENVIADA_MATR), 0)
      INTO v_nTrans
      FROM LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCodLocal_in
       AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
       AND C.COD_LOCAL = D.COD_LOCAL
       AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
       AND D.COD_PROD = cCodProd_in
       AND C.TIP_NOTA_ES = g_cTipoNotaRecepcion
       AND D.IND_PROD_AFEC = 'N';
    return v_nTrans;
  end;
  /* *********************************************************************************** */
  function f_get_pvm_prod(cCodLocal_in in char, cCodProd_in in char)
    return number is
    vValorAutorizado number;
  begin
    BEGIN
      SELECT CASE
               WHEN IND_AUTORIZADO = 'S' AND IND_ACTIVO > SYSDATE THEN
                TOT_UNID_VTA_RDM
               ELSE
                0
             END
        INTO vValorAutorizado
        FROM LGT_PARAM_PROD_LOCAL
       WHERE COD_LOCAL = cCodLocal_in
         AND COD_PROD = cCodProd_in;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        vValorAutorizado := NULL;
    END;
    return vValorAutorizado;
  end;
  /* ********************************************************************************** */
  function f_get_stk_calculado
    (vTipStk varchar2,cCantUnidVta_S number,cCantUnidVta_M number,cFrec number,cCodLocal char,
     vCodProd_in varchar2
    ) return number is
    vStokCalculado number;
    indLocalProv char(1);
    indLocalPequeno char(1);

    indUsoRotacion char(1);
    indTipRot char(1);
    vMinLima number;vMinProv number;
    vMaxLima number;vMaxProv number;
    vMinLimaChico number;vMaxLimaChico number;
    vMinLimaChicoDOS number;vMaxLimaChicoDOS number;

-- 2014-04-11 JOLIVA: SE AGREGA LOGICA TOMANDO COMO BASE ROTACION MENSUAL = 10
    vMinLima10 number; vMinProv10 number;
    vMaxLima10 number; vMaxProv10 number;
    vMinLimaChico10 number; vMaxLimaChico10 number;
    vMinLimaChicoDOS10 number; vMaxLimaChicoDOS10 number;
    vStokCalculado10 number;

    cCantUnidVtaCalculado number;
    vFactMes number;
    -- dubilluz 14.11.2013
    nCantExhiFijoProd number;

  begin
   -- dbms_output.put_line('>>> ><<<< ');
   --dbms_output.put_line('vCodProd_in >>@'||vCodProd_in||'@');
   begin
    if cCantUnidVta_M <= 0 then
        vStokCalculado := 0;

    else
-- 2012-09-11 JOLIVA: Se implementa temporalmente indicador de local chico
--       select l.ind_local_prov,'X' ind_local_pequeno
       SELECT L.IND_LOCAL_PROV,
/*              CASE WHEN cCodLocal IN ('170', '137', '159', '089', '122', '114', '018', '011', '042', '071', '079', '088', '099', '100', '101', '107', '109', '115', '119', '120', '128', '129', '148', '155', '164', '165', '172',  '037', '031', '118') THEN 'S'
                   ELSE 'X'
              END ind_local_pequeno*/
               nvl((
              select trim(to_char(a.tip_local,'90'))
              from  PBL_LOCAL_TIPO_REP a
              where  a.cod_grupo_cia = l.cod_grupo_cia
              and    a.cod_local = l.cod_local
              ),'X') ind_local_pequeno
       into   indLocalProv,indLocalPequeno
       from   pbl_local l
       where  l.cod_grupo_cia = '001'
       and    l.cod_local = cCodLocal;

    select m.ind_uso_rot,
           m.tip_rotacion,
           m.min_lima,
           m.min_prov,
           m.max_lima,
           m.max_prov,
           m.min_lima_chico,
           m.max_lima_chico,
           m.min_lima_chico_2,
           m.max_lima_chico_2,
           m.FACT_MES
      into indUsoRotacion,
           indTipRot,
           vMinLima,
           vMinProv,
           vMaxLima,
           vMaxProv,
           vMinLimaChico,
           vMaxLimaChico,
           vMinLimaChicoDOS,
           vMaxLimaChicoDOS,
           vFactMes
      from MF_PARAM_REP_LOCAL m
     where cCantUnidVta_M between m.vta_mes_i and nvl(m.vta_mes_f,999999999)
       and cFrec between nvl(m.frec_i, 0) and nvl(m.frec_f, 999999999);

    if vFactMes is not null and vFactMes > 0 then
    ----------------------------------------------------
    select  LEAST(cCantUnidVta_S, cCantUnidVta_M*vFactMes)
    into    cCantUnidVtaCalculado
    from    dual;
    else
        cCantUnidVtaCalculado := cCantUnidVta_M;
    end if;
    ----------------------------------------------------

     if vTipStk = 'MIN' then
        if indLocalProv = 'S' then
          if indUsoRotacion = 'N' then
           vStokCalculado := vMinProv;
          else -- ROTACION SEMANAL es la UNICA Q SE USA
            vStokCalculado := ceil(cCantUnidVtaCalculado*vMinProv);
          end if;
        else
          if indLocalPequeno = 'X' then
             -- esto xq aun no existe lo q es local peqno
            if indUsoRotacion = 'N' then
               vStokCalculado := vMinLima;
            else -- ROTACION SEMANAL es la UNICA Q SE USA
               vStokCalculado := ceil(cCantUnidVtaCalculado*vMinLima);
            end if;
          else
              --if indLocalPequeno = 'S' then --  ES LOCAL PEQUENO
              if indLocalPequeno = C_TIP_LOCAL_PEQUENO then --  ES LOCAL PEQUENO
                 if indUsoRotacion = 'N' then
                   vStokCalculado := vMinLimaChico;
                 else -- ROTACION SEMANAL es la UNICA Q SE USA
                   vStokCalculado := ceil(cCantUnidVtaCalculado*vMinLimaChico);
                 end if;
              else
                if indLocalPequeno = C_TIP_LOCAL_PEQUENO_DOS then --  ES LOCAL PEQUENO
                   if indUsoRotacion = 'N' then
                      vStokCalculado := vMinLimaChicoDOS;
                   else -- ROTACION SEMANAL es la UNICA Q SE USA
                     vStokCalculado := ceil(cCantUnidVtaCalculado*vMinLimaChicoDOS);
                   end if;
                else    --  NO >> ES LOCAL PEQUENO
                 if indUsoRotacion = 'N' then
                   vStokCalculado := vMinLima;
                 else -- ROTACION SEMANAL es la UNICA Q SE USA
                   vStokCalculado := ceil(cCantUnidVtaCalculado*vMinLima);
                 end if;
               end if;
              end if;
          end if;
        end if;
     else
        if vTipStk = 'MAX' then
            if indLocalProv = 'S' then
              if indUsoRotacion = 'N' then
               vStokCalculado := vMaxProv;
              else -- ROTACION SEMANAL es la UNICA Q SE USA
                vStokCalculado := ceil(cCantUnidVtaCalculado*vMaxProv);

              end if;

            else
              if indLocalPequeno = 'X' then
                 -- esto xq aun no existe lo q es local peqno
                if indUsoRotacion = 'N' then
                   vStokCalculado := vMaxLima;
                else -- ROTACION SEMANAL es la UNICA Q SE USA
                   vStokCalculado := ceil(cCantUnidVtaCalculado*vMaxLima);

                end if;
              else
                  if indLocalPequeno = C_TIP_LOCAL_PEQUENO then --  ES LOCAL PEQUENO
                     if indUsoRotacion = 'N' then
                       vStokCalculado := vMaxLimaChico;
                     else -- ROTACION SEMANAL es la UNICA Q SE USA
                       vStokCalculado := ceil(cCantUnidVtaCalculado*vMaxLimaChico);

                     end if;

                  else
                      if indLocalPequeno = C_TIP_LOCAL_PEQUENO_DOS then --  ES LOCAL PEQUENO
                         if indUsoRotacion = 'N' then
                           vStokCalculado := vMaxLimaChicoDOS;
                         else -- ROTACION SEMANAL es la UNICA Q SE USA
                           vStokCalculado := ceil(cCantUnidVtaCalculado*vMaxLimaChicoDOS);

                         end if;
                      else   --  NO >> ES LOCAL PEQUENO

                       if indUsoRotacion = 'N' then
                         vStokCalculado := vMaxLima;
                       else -- ROTACION SEMANAL es la UNICA Q SE USA
                         vStokCalculado := ceil(cCantUnidVtaCalculado*vMaxLima);

                       end if;
                     end if;
                  end if;

              end if;

            end if;
        else
          vStokCalculado := 0;
        end if;
     end if;
    end if;

    exception
    when others then
      --dbms_output.put_line('salee >>@'||sqlerrm||'@');
        vStokCalculado := 0;
   end;


-- 2014-04-11 JOLIVA: SE AGREGA LOGICA TOMANDO COMO BASE ROTACION MENSUAL = 10
    vStokCalculado10 := 0;

    IF cCantUnidVta_M BETWEEN 11 AND 35 THEN
    BEGIN
              select
                     m.min_lima,
                     m.min_prov,
                     m.max_lima,
                     m.max_prov,
                     m.min_lima_chico,
                     m.max_lima_chico,
                     m.min_lima_chico_2,
                     m.max_lima_chico_2
                into
                     vMinLima10,
                     vMinProv10,
                     vMaxLima10,
                     vMaxProv10,
                     vMinLimaChico10,
                     vMaxLimaChico10,
                     vMinLimaChicoDOS10,
                     vMaxLimaChicoDOS10
                from MF_PARAM_REP_LOCAL m
               where 10 between m.vta_mes_i and nvl(m.vta_mes_f,999999999);

               if vTipStk = 'MIN' then
                  if indLocalProv = 'S' then
                    vStokCalculado10 := vMinProv10;
                  else
                    if indLocalPequeno = 'X' then
                       -- esto xq aun no existe lo q es local peqno
                      vStokCalculado10 := vMinLima10;
                    else
                        --if indLocalPequeno = 'S' then --  ES LOCAL PEQUENO
                        if indLocalPequeno = C_TIP_LOCAL_PEQUENO then --  ES LOCAL PEQUENO
                           vStokCalculado10 := vMinLimaChico10;
                        else
                          if indLocalPequeno = C_TIP_LOCAL_PEQUENO_DOS then --  ES LOCAL PEQUENO
                             vStokCalculado10 := vMinLimaChicoDOS10;
                          else    --  NO >> ES LOCAL PEQUENO
                           vStokCalculado10 := vMinLima10;
                          end if;
                        end if;
                    end if;
                  end if;
               else
                  if vTipStk = 'MAX' then
                      if indLocalProv = 'S' then
                        vStokCalculado10 := vMaxProv10;
                      else
                        if indLocalPequeno = 'X' then
                          vStokCalculado10 := vMaxLima10;
                        else
                            if indLocalPequeno = C_TIP_LOCAL_PEQUENO then --  ES LOCAL PEQUENO
                               vStokCalculado10 := vMaxLimaChico10;
                            else
                                if indLocalPequeno = C_TIP_LOCAL_PEQUENO_DOS then --  ES LOCAL PEQUENO
                                   vStokCalculado10 := vMaxLimaChicoDOS10;
                                else   --  NO >> ES LOCAL PEQUENO
                                 vStokCalculado10 := vMaxLima10;
                               end if;
                            end if;

                        end if;

                      end if;
                  else
                    vStokCalculado10 := 0;
                  end if;
               end if;

              vStokCalculado := GREATEST(vStokCalculado, vStokCalculado10);

    END;
    END IF;



    /*CASE WHEN
             M.CANT_UNID_VTA <= 0 THEN   0
         ELSE
                GREATEST
                        (
                            CASE
                                 WHEN M.CANT_UNID_VTA <= 0                  THEN   0
                                 WHEN M.CANT_UNID_VTA <= 1                  THEN   0.5
                                 WHEN M.CANT_UNID_VTA <= 2                  THEN   0.5
                                 WHEN M.CANT_UNID_VTA <= 3                  THEN   1.5
                                 WHEN M.CANT_UNID_VTA <= 4                  THEN   1.5
                                 WHEN M.CANT_UNID_VTA <= 5                  THEN   2.5
                                 WHEN M.CANT_UNID_VTA <= 6                  THEN   2.5
                                 WHEN M.CANT_UNID_VTA <= 7                  THEN   3.5
                                 WHEN M.CANT_UNID_VTA <= 8                  THEN   3.5
                                 WHEN M.CANT_UNID_VTA <= 9                  THEN   4.5
                                 WHEN M.CANT_UNID_VTA <= 10                 THEN   4.5
                                 WHEN M.CANT_UNID_VTA <= 35 AND S.FREC <= 3 THEN   CEIL(LEAST(S.CANT_UNID_VTA, M.CANT_UNID_VTA/3) * 6/7)
                                 WHEN M.CANT_UNID_VTA <= 35 AND S.FREC <= 7 THEN   CEIL(LEAST(S.CANT_UNID_VTA, M.CANT_UNID_VTA/3) * 6/7)
                                 WHEN M.CANT_UNID_VTA >  35 AND S.FREC <= 3 THEN   CEIL(LEAST(S.CANT_UNID_VTA, M.CANT_UNID_VTA/4) * 5/7)
                                 WHEN M.CANT_UNID_VTA >  35 AND S.FREC <= 7 THEN   CEIL(LEAST(S.CANT_UNID_VTA, M.CANT_UNID_VTA/4) * 5/7)
                            END,
                            NVL(0,0) + NVL(0,0)
         )
   END "STK_MINIMO",*/

   -- consultar mini exhIBICION fijo
   -- eNVIAR EL MAYOR DE AMBIOS VALORES
   -- vStokCalculado Y NVL(MEPL.CANT_EXHIB,0)
   -- DUBILLUZ 15.11.2013
   begin
   select a.cant_exhib
   into   nCantExhiFijoProd
   from   VTA_MIN_EXHIB_PROD_LOCAL a
   WHERE  A.COD_LOCAL_SAP = cCodLocal
   AND    A.COD_PROD_SAP = trim(vCodProd_in);
   exception
     when others then
       nCantExhiFijoProd := 0;
   end;

   -- NO ESTA ACTIVO
   --nCantExhiFijoProd := 0;

   if nCantExhiFijoProd > 0 then
     select GREATEST(nCantExhiFijoProd,vStokCalculado)
     into   vStokCalculado
     from   dual;
   end if;
   -- DUBILLUZ 15.11.2013

    return vStokCalculado;
  end;

  /* *********************************************************************************** */
  PROCEDURE P_VTA_MES_PROD_LOCAL(cCodLocal_in in char) is
  begin
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_VTA_RES_VTA_MES_PROD_LOCAL DROP STORAGE';

    INSERT INTO MF_VTA_RES_VTA_MES_PROD_LOCAL NOLOGGING
      (COD_LOCAL_MF,
       COD_PROD_MF,
       MES,
       CANT_UNID_VTA,
       MON_TOT_VTA,
       FRECUENCIA,
       CANT_UNID_VTA_TOT,
       CANT_UNID_VTA_SIN_PROM,
       COS_TOT_VTA)
      SELECT COD_LOCAL_MF,
             COD_PROD_MF,
             TRUNC(FEC_DIA_VTA, 'MM') MES,
             SUM(CANT_UNID_VTA) CANT_UNID_VTA,
             SUM(MON_TOT_VTA) MON_TOT_VTA,
             SUM(CASE
                   WHEN R.CANT_UNID_VTA < 0 THEN
                    0
                   ELSE
                    1
                 END) FRECUENCIA,
             SUM(CANT_UNID_VTA_TOT),
             SUM(CANT_UNID_VTA_SIN_PROM),
             SUM(COS_TOT_VTA)
        FROM MF_VTA_RES_VTA_PROD_LOCAL r
       WHERE R.COD_LOCAL_MF = cCodLocal_in
       AND   R.CANT_UNID_VTA IS NOT NULL
       GROUP BY COD_LOCAL_MF, COD_PROD_MF, TRUNC(FEC_DIA_VTA, 'MM');

  end;
  /* ******************************************************************************** */
 procedure P_VTA_PVM_PROD_LOCAL(cCodLocal_in in char) is

BEGIN
         BEGIN

                  INSERT INTO MF_AUX_MEJOR_VTA_SEM_PROD_LOC NOLOGGING
                   (COD_LOCAL_MF, COD_PROD_MF, CANT_UNID_VTA, FREC, SEMANA, T_S_IGV)
                  SELECT /*+rule*/
                         R.COD_LOCAL,
                         COD_PROD COD_PROD_MF,
                         NVL(TOT_UNID_VTA_RDM, 0) / 4 CTD_PVM,
                         7            FRECUENCIA,
                         TO_CHAR(SYSDATE,'WW') SEMANA,
                         0                                           T_S_IGV
                  FROM LGT_PARAM_PROD_LOCAL R
                  WHERE r.Cod_Local = cCodLocal_in
                    AND R.IND_AUTORIZADO = 'S'
                    AND R.IND_ACTIVO > SYSDATE
                    AND NVL(R.TOT_UNID_VTA_RDM,0) > 0
                    AND NOT EXISTS
                                  (
                                   SELECT 1
                                   FROM MF_AUX_MEJOR_VTA_SEM_PROD_LOC A
                                   WHERE A.COD_LOCAL_MF = cCodLocal_in
                                     AND A.COD_PROD_MF = R.COD_PROD
                                  );

                  INSERT INTO MF_AUX_MEJOR_VTA_MES_PROD_LOC NOLOGGING (
                  COD_LOCAL_MF, COD_PROD_MF, CANT_UNID_VTA, FRECUENCIA, MES_ID, T_S_IGV, PVM_AUTORIZADO, OLD_CANT_UNID_VTA)
                  SELECT /*+rule*/
                         COD_LOCAL COD_LOCAL_MIFARMA,
                         COD_PROD COD_PROD_MIFARMA,
                         NVL(TOT_UNID_VTA_RDM, 0) CTD_PVM,
                         30            FRECUENCIA,
                         TO_NUMBER(TO_CHAR(SYSDATE,'YYYYMM'))        MES_ID,
                         30                                           T_S_IGV,
                         NVL(TOT_UNID_VTA_RDM, 0) CTD_PVM,
                         NULL         OLD_CANT_UNID_VTA
                --  SELECT COD_PRODUCTO
                  FROM LGT_PARAM_PROD_LOCAL R
                  WHERE r.Cod_Local = cCodLocal_in
                    AND R.IND_AUTORIZADO = 'S'
                    AND R.IND_ACTIVO > SYSDATE
                    AND NVL(R.TOT_UNID_VTA_RDM,0) > 0
                    AND NOT EXISTS
                    (
                     SELECT 1
                     FROM MF_AUX_MEJOR_VTA_MES_PROD_LOC A
                     WHERE A.COD_LOCAL_MF = cCodLocal_in
                       AND A.COD_PROD_MF = R.COD_PROD
                    );

             COMMIT;

         EXCEPTION
                  WHEN OTHERS THEN
                       DBMS_OUTPUT.put_line('ERR=' || cCodLocal_in || ':' || SQLERRM);
         END;

    -- END LOOP;
END;
  /* *********************************************************************************** */
  PROCEDURE P_PROD_LOCAL_SEMANA(cCodLocal_in in char) is
  begin
    execute immediate 'TRUNCATE TABLE MF_AUX_RES_VTA_PROD_LOCAL_SEM DROP STORAGE';

    INSERT INTO MF_AUX_RES_VTA_PROD_LOCAL_SEM NOLOGGING
      (COD_LOCAL_MF, COD_PROD_MF, SEMANA, FREC, CANT_UNID_VTA, T_S_IGV)
      SELECT R.COD_LOCAL_MF,
             R.COD_PROD_MF,
             TO_CHAR(R.FEC_DIA_VTA, 'WW') SEMANA,
             SUM(CASE
                   WHEN R.CANT_UNID_VTA > 0 THEN
                    1
                   ELSE
                    0
                 END) "FREC",
             CEIL(SUM(R.CANT_UNID_VTA)) CANT_UNID_VTA,
             SUM(R.MON_TOT_VTA) T_S_IGV
        FROM MF_VTA_RES_VTA_PROD_LOCAL R
       WHERE 1 = 1
         AND R.FEC_DIA_VTA BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -3) AND
             SYSDATE
         AND R.COD_LOCAL_MF = cCodLocal_in
       GROUP BY R.COD_LOCAL_MF, R.COD_PROD_MF, TO_CHAR(R.FEC_DIA_VTA, 'WW');

    COMMIT;

  end;
  /* *********************************************************************************** */
  PROCEDURE P_AUX_MEJOR_PROD_LOCAL(cCodLocal_in in char) IS
  BEGIN

    execute immediate 'TRUNCATE TABLE MF_AUX_MEJOR_VTA_SEM_PROD_LOC DROP STORAGE';

    INSERT INTO MF_AUX_MEJOR_VTA_SEM_PROD_LOC NOLOGGING
      (COD_LOCAL_MF, COD_PROD_MF, CANT_UNID_VTA, FREC, SEMANA, T_S_IGV, PVM_AUTORIZADO, OLD_CANT_UNID_VTA)
      SELECT COD_LOCAL_MF,
             COD_PROD_MF,
             CANT_UNID_VTA,
             FREC,
             SEMANA,
             T_S_IGV,
             f_get_pvm_prod(COD_LOCAL_MF, COD_PROD_MF),
             CANT_UNID_VTA
        FROM (SELECT COD_LOCAL_MF,
                     COD_PROD_MF,
                     SEMANA,
                     CANT_UNID_VTA,
                     FREC,
                     T_S_IGV,
                     RANK() OVER(PARTITION BY COD_LOCAL_MF, COD_PROD_MF ORDER BY CANT_UNID_VTA DESC, SEMANA DESC) "PUESTO"
                FROM (SELECT COD_LOCAL_MF,
                             COD_PROD_MF,
                             SEMANA,
                             CANT_UNID_VTA,
                             FREC,
                             T_S_IGV
                        FROM MF_AUX_RES_VTA_PROD_LOCAL_SEM)) V
       WHERE PUESTO = 1
         AND COD_LOCAL_MF = cCodLocal_in;

    -- actualiza el valor si es no nulo
    update MF_AUX_MEJOR_VTA_SEM_PROD_LOC s
       set s.cant_unid_vta = s.pvm_autorizado / 4,
           s.frec = 7
     where s.pvm_autorizado is not null
     and s.pvm_autorizado > 0 ;


    COMMIT;

    -- **********************************************************************

    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_AUX_MEJOR_VTA_MES_PROD_LOC DROP STORAGE';

    INSERT INTO MF_AUX_MEJOR_VTA_MES_PROD_LOC NOLOGGING
      (COD_LOCAL_MF,
       COD_PROD_MF,
       CANT_UNID_VTA,
       FRECUENCIA,
       MES_ID,
       T_S_IGV,
       PVM_AUTORIZADO,
       OLD_CANT_UNID_VTA)
      SELECT COD_LOCAL_MF,
             COD_PROD_MF,
             CANT_UNID_VTA,
             FRECUENCIA,
             MES_ID,
             T_S_IGV,
             f_get_pvm_prod(COD_LOCAL_MF, COD_PROD_MF),
             CANT_UNID_VTA
        FROM (SELECT COD_LOCAL_MF,
                     COD_PROD_MF,
                     MES_ID,
                     CANT_UNID_VTA,
                     FRECUENCIA,
                     T_S_IGV,
                     RANK() OVER(PARTITION BY COD_LOCAL_MF, COD_PROD_MF ORDER BY CANT_UNID_VTA DESC, MES_ID DESC) "PUESTO"
                FROM (SELECT R.COD_LOCAL_MF,
                             R.COD_PROD_MF,
                             TO_NUMBER(TO_CHAR(R.MES, 'YYYYMM')) MES_ID,
                             CEIL(R.CANT_UNID_VTA) CANT_UNID_VTA,
                             R.FRECUENCIA FRECUENCIA,
                             R.MON_TOT_VTA T_S_IGV
                        FROM MF_VTA_RES_VTA_MES_PROD_LOCAL R
                       WHERE 1 = 1
                         AND R.MES BETWEEN
                             --ADD_MONTHS(TRUNC(SYSDATE - 10, 'MM'), -3) AND
                             ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -3) AND
                             SYSDATE)) V
       WHERE PUESTO = 1
         AND COD_LOCAL_MF = cCodLocal_in;

    -- actualiza el valor si es no nulo
    update MF_AUX_MEJOR_VTA_MES_PROD_LOC s
       set s.cant_unid_vta = s.pvm_autorizado
     where s.pvm_autorizado is not null
     and s.pvm_autorizado > 0 ;

    COMMIT;

  END;

  /* *********************************************************************************** */
  PROCEDURE P_CALCULO_PED_REP_LOCAL(cCodLocal_in in char) IS
    vActivAlgProm CHAR(1);
  BEGIN

    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_LGT_PROD_LOCAL_REP DROP STORAGE';

    INSERT INTO MF_LGT_PROD_LOCAL_REP NOLOGGING
      (COD_PROD_MF,
       COD_LOCAL_MF,
       GRUPO,
       COSTO_PROMEDIO,
       CANT_SEM,
       T_S_IGV_SEM,
       FREC_SEM,
       CANT_MES,
       T_S_IGV_MES,
       FREC_MES,
       STK_IDEAL,
       VAL_STK_IDEAL,
       STK_TRANSITO,
       STK_LOC,
       VAL_STK_LOC,
       STK_CD,
       X_REPONER,
       X_REPONER_FINAL,
       VAL_X_REPONER_FASA,
       USU_CREA,
       Cod_Prod_Sap,
       STK_MINIMO,
       STK_MAXIMO,
       X_REPONER_NUEVO,
       MIN_EXHIB_FIJO,
       MIN_EXHIB_PROM,
       PVM_AUTORIZADO,OLD_CANT_UNID_VTA,
       CANT_MAX_STK,cant_sug,
       UND_VTA_MES_0,UND_VTA_MES_1,UND_VTA_MES_2,UND_VTA_MES_3,UND_VTA_MES_4
     )
      SELECT COD_PROD_MF,
             COD_LOCAL_MF,
             GRUPO,
             COSTO_PROMEDIO,
             CANT_SEM,
             T_S_IGV_SEM,
             FREC_SEM,
             CANT_MES,
             T_S_IGV_MES,
             FREC_MES,
             STK_IDEAL,
             VAL_STK_IDEAL,
             STK_TRANSITO,
             STK_LOC,
             VAL_STK_LOC,
             STK_CD,
             X_REPONER,
             X_REPONER X_REPONER_FINAL,
             VAL_X_REPONER_MF,
             'PR_REPOSICION',
             COD_PROD_SAP,
             STK_MINIMO,
             STK_MAXIMO,
             X_REPONER_NUEVO,
             MIN_EXHIB_FIJO,
             MIN_EXHIB_PROM,
             PVM_AUTORIZADO,OLD_CANT_UNID_VTA,
             (
              SELECT s.cant_max_stk
              FROM  LGT_PROD_LOCAL_REP s
              WHERE S.COD_LOCAL = cCodLocal_in
              and   S.COD_PROD = cod_prod_mf
             ) ,
             (
              SELECT s.cant_sug
              FROM  LGT_PROD_LOCAL_REP s
              WHERE S.COD_LOCAL = cCodLocal_in
              and   S.COD_PROD = cod_prod_mf
             ) ,
             UNID_VTA_M_0,
            UNID_VTA_M_1,
            UNID_VTA_M_2,
            UNID_VTA_M_3,
            UNID_VTA_M_4
        FROM V_MF_DET_SIM_REP_LOC
       WHERE COD_LOCAL_MF = cCodLocal_in;
    COMMIT;

    SELECT A.IND_ACTIVO_FUNCION
    INTO   vActivAlgProm
    FROM   vta_param_rep_promo A;

    if vActivAlgProm = 'S' then
         P_CALCULO_PROMO_COMPRA(cCodLocal_in);
        commit;

    end if;


  END;
  /* *********************************************************************************** */
  PROCEDURE P_OPERA_ALGORITMO_MF(cCodLocal_in in char) is
  begin
    P_CREA_RES_VTA_PROD_LOCAL('001', cCodLocal_in);
    VERIFICA_VENTAS_REP(cCodLocal_in);
    P_PROCESO_CALCULA_PICOS('001', cCodLocal_in);
    P_STK_LOCAL_AND_TRANSITO(cCodLocal_in);
    P_VTA_MES_PROD_LOCAL(cCodLocal_in);
    P_PROD_LOCAL_SEMANA(cCodLocal_in);
    P_AUX_MEJOR_PROD_LOCAL(cCodLocal_in);
    P_VTA_PVM_PROD_LOCAL(cCodLocal_in);

    ------------------------------------------

    P_CALCULO_PED_REP_LOCAL(cCodLocal_in);

    P_ACTUALIZA_PROD_LOCAL_REP(cCodLocal_in);

    COMMIT;

    -- GUARDA ANALISIS DE PEDIDO REPO LOCAL MF 2.0
    DELETE HIST_ANALISIS_PED_REP_MF
    WHERE  FECHA_HIST <= TRUNC(SYSDATE - 3);

    insert into HIST_ANALISIS_PED_REP_MF
    (tot_unid_vta_rdm, grupo, cod_prod_mf, cod_local_mf, costo_promedio, cant_sem,
      t_s_igv_sem, frec_sem, cant_mes, t_s_igv_mes, frec_mes, stk_ideal,
      stk_minimo, stk_maximo, val_stk_ideal, stk_transito, stk_loc,
      val_stk_loc, stk_cd, min_exhib_fijo, min_exhib_prom, x_reponer,
      x_reponer_nuevo, val_x_reponer_mf, cod_prod_sap, unid_vta_m_0, unid_vta_m_1,
      unid_vta_m_2, unid_vta_m_3, unid_vta_m_4, cant_ped_esp,
      fec_crea, fec_mod, usu_mod, cod_local,
      pvm_autorizado, old_cant_unid_vta,FECHA_HIST)
    SELECT p.tot_unid_vta_rdm,a.grupo, cod_prod_mf, cod_local_mf, costo_promedio, cant_sem,
    t_s_igv_sem, frec_sem, cant_mes, t_s_igv_mes, frec_mes, stk_ideal, stk_minimo,
    stk_maximo, val_stk_ideal, stk_transito, stk_loc, val_stk_loc, stk_cd, min_exhib_fijo,
    min_exhib_prom, x_reponer, x_reponer_nuevo, val_x_reponer_mf, cod_prod_sap, unid_vta_m_0,
    unid_vta_m_1, unid_vta_m_2, unid_vta_m_3, unid_vta_m_4,cant_ped_esp,
    fec_crea, SYSDATE fec_mod,usu_mod,a.cod_local, pvm_autorizado, old_cant_unid_vta,sysdate
    FROM   V_MF_DET_SIM_REP_LOC a,
           lgt_param_prod_local p
    where  a.COD_PROD_MF = p.cod_prod(+);
    COMMIT;

    -- BORRA TABLAS CALCULADAS X ESPACIO EN EL LOCAL
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_VTA_RES_VTA_PROD_LOCAL DROP STORAGE';
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_VTA_STK_PROD_LOCAL DROP STORAGE';
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_STK_TRAN_PROD_LOCAL DROP STORAGE';
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_VTA_RES_VTA_MES_PROD_LOCAL DROP STORAGE';
    execute immediate 'TRUNCATE TABLE MF_AUX_RES_VTA_PROD_LOCAL_SEM DROP STORAGE';
    execute immediate 'TRUNCATE TABLE MF_AUX_MEJOR_VTA_SEM_PROD_LOC DROP STORAGE';
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_AUX_MEJOR_VTA_MES_PROD_LOC DROP STORAGE';

    BEGIN
      REP_ACTUALIZA_CANT_SUG('001', cCodLocal_in);
      DBMS_OUTPUT.PUT_LINE('LA ACTUALIZACION DE CANT SUG SE EJECUTO CORRECTAMENTE');
/*    EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('SE PRODUJO UN ERROR AL EJECUTAR ACTUALIZA CANT_SUG');
      RAISE;
*/    END;

      UPDATE LGT_PROD_LOCAL_REP A
      SET A.CANT_ROT=0
      WHERE A.CANT_ROT IS NULL;

      COMMIT;

      UPDATE LGT_PROD_LOCAL_REP A
      SET A.Val_Frac_Local=1
      WHERE A.Val_Frac_Local=0;

      COMMIT;
    --- cambia repone a recargas y la carga de historico
    -- LAS RECARGAS NO SE DEBEN INTENTAR REPONER
    UPDATE LGT_PROD_LOCAL_REP
    SET CANT_SOL = 0, CANT_SUG = 0
    WHERE COD_PROD IN ('510558', '510559');

    DELETE FROM LGT_PROD_LOCAL_REP_HIST WHERE FEC_CREA < TRUNC(SYSDATE - 10);
   --agregado por jluna 20141006 siguiente linea
    DELETE FROM LGT_PROD_LOCAL_REP_HIST WHERE FEC_CREA >= TRUNC(SYSDATE );


    INSERT INTO LGT_PROD_LOCAL_REP_HIST
    (
    COD_GRUPO_CIA, COD_LOCAL, COD_PROD, CANT_MIN_STK, CANT_MAX_STK, CANT_SUG, CANT_SOL, CANT_ROT, CANT_TRANSITO,
    CANT_DIA_ROT, NUM_DIAS, STK_FISICO, VAL_FRAC_LOCAL, CANT_EXHIB, USU_CREA_PROD_LOCAL_REP, FEC_CREA_PROD_LOCAL_REP,
    USU_MOD_PROD_LOCAL_REP, FEC_MOD_PROD_LOCAL_REP, TIPO, CANT_DIAS_VTA, CANT_VTA_PER_0, CANT_VTA_PER_1,
    CANT_VTA_PER_2, CANT_VTA_PER_3, CANT_UNID_VTA, CANT_MAX_ADIC, TIP_ABC_UNID_VTA, CANT_ADIC, IND_STK_LOCALES,
    IND_STK_ALMACEN, STK_ALMACEN, UNID_MINIMA, CANT_SUG_BK, UNID_MIN_SUG, STK_UNID_MIN, VAL_FACT_CONV,
    CANT_ADICIONAL,
    -- 2009-11-24 JOLIVA: SE AGREGAN CAMPOS FALTANTES
    PVM_AUTORIZADO, UNID_STK_IDEAL_REAL, MES_STK_IDEAL_REAL, DIA_EFEC_STK_IDEAL_REAL,
    MAX_STK_IDEAL_REAL, MAX_STK_IDEAL_EFECT,
    UN_VTA_M0, UN_VTA_M1, UN_VTA_M2, UN_VTA_M3, UN_VTA_M4,
    DIA_EF_M0, DIA_EF_M1, DIA_EF_M2, DIA_EF_M3, DIA_EF_M4,
    -- 2010-01-06 JOLIVA: SE AGREGAN CAMPOS NUEVOS
    FACTOR_AUMENTO_STK_EFECT, STK_IDEAL_REAL, STK_IDEAL_EFECT, STK_IDEAL_EFICIENTE
    )
    SELECT
    COD_GRUPO_CIA, COD_LOCAL, COD_PROD, CANT_MIN_STK, CANT_MAX_STK, CANT_SUG, CANT_SOL, CANT_ROT, CANT_TRANSITO,
    CANT_DIA_ROT, NUM_DIAS, STK_FISICO, VAL_FRAC_LOCAL, CANT_EXHIB, USU_CREA_PROD_LOCAL_REP, FEC_CREA_PROD_LOCAL_REP,
    USU_MOD_PROD_LOCAL_REP, FEC_MOD_PROD_LOCAL_REP, TIPO, CANT_DIAS_VTA, CANT_VTA_PER_0, CANT_VTA_PER_1,
    CANT_VTA_PER_2, CANT_VTA_PER_3, CANT_UNID_VTA, CANT_MAX_ADIC, TIP_ABC_UNID_VTA, CANT_ADIC, IND_STK_LOCALES,
    IND_STK_ALMACEN, STK_ALMACEN, UNID_MINIMA, CANT_SUG_BK, UNID_MIN_SUG, STK_UNID_MIN, VAL_FACT_CONV,
    CANT_ADICIONAL,
    -- 2009-11-24 JOLIVA: SE AGREGAN CAMPOS FALTANTES
    PVM_AUTORIZADO, UNID_STK_IDEAL_REAL, MES_STK_IDEAL_REAL, DIA_EFEC_STK_IDEAL_REAL,
    MAX_STK_IDEAL_REAL, MAX_STK_IDEAL_EFECT,
    UN_VTA_M0, UN_VTA_M1, UN_VTA_M2, UN_VTA_M3, UN_VTA_M4,
    DIA_EF_M0, DIA_EF_M1, DIA_EF_M2, DIA_EF_M3, DIA_EF_M4,
    -- 2010-01-06 JOLIVA: SE AGREGAN CAMPOS NUEVOS
    FACTOR_AUMENTO_STK_EFECT, STK_IDEAL_REAL, STK_IDEAL_EFECT, STK_IDEAL_EFICIENTE
    FROM LGT_PROD_LOCAL_REP;

    COMMIT;


    --2014-09-25 JOLIVA: SE CALCULA LA VENTA PERDIDA DEL D虯 DE AYER AL TERMINAR LA REPOSICION DE LOCALES
    PTOVENTA_TAREAS.CARGA_VTA_PERDIDA_LOCAL_DIA('001', cCodLocal_in);


  end;
  /* *********************************************************************************** */
  procedure P_ACTUALIZA_PROD_LOCAL_REP(cCodLocal_in in char) is
    begin
        update  lgt_prod_local_rep NOLOGGING
        set     cant_sug = 0,
                cant_sol = 0,
                CANT_MIN_STK = 0,
                CANT_MAX_STK = 0;
        commit;

        update  lgt_prod_local_rep l
        set     (l.cant_sug,l.cant_sol,CANT_MIN_STK,CANT_MAX_STK,
        CANT_VTA_PER_0,CANT_VTA_PER_1,CANT_VTA_PER_2,CANT_VTA_PER_3,
        -- Author    : JOLIVA
        -- Fecha     : 2014-07-18
        -- Prop贸sito : Se usa la columna UN_VTA_M5 para almanenar la m谩xima venta por mes que se usar谩 en el calculo del masterpack
        UN_VTA_M5)
                                         = (
                                           select t.x_reponer_nuevo,t.x_reponer_nuevo,t.stk_minimo,t.stk_maximo,
                                           UND_VTA_MES_0, UND_VTA_MES_1, UND_VTA_MES_2, UND_VTA_MES_3,
        -- Author    : JOLIVA
        -- Fecha     : 2014-07-18
        -- Prop贸sito : Se usa la columna UN_VTA_M5 para almanenar la m谩xima venta por mes que se usar谩 en el calculo del masterpack
                                           CANT_MES
                                           from MF_LGT_PROD_LOCAL_REP t
                                           where T.cod_local_MF = l.cod_local
                                           and   T.cod_prod_MF = l.cod_prod
                                           AND   x_reponer_nuevo > 0
                                           )
        where   l.cod_grupo_cia = '001'
        and     l.cod_local = cCodLocal_in
        and     exists (
                       select 1
                       from MF_LGT_PROD_LOCAL_REP t
                       where T.cod_local_MF = l.cod_local
                       and   T.cod_prod_MF = l.cod_prod
                       AND   x_reponer_nuevo > 0
                       );

      update pbl_local s
      set    s.fec_genera_max_min = sysdate
      where  s.cod_grupo_cia = '001'
      and    s.cod_local = cCodLocal_in;

  end;
  /* ********************************************************************************** */
procedure P_CALCULO_PROMO_COMPRA(cCodLocal_MF varchar2) IS

     vDiaSemanaRep varchar2(10);
     ------------------------------
 CURSOR curPromAnalisis (cDiaSemanaIn IN CHAR, C_FECHA_IN IN DATE) IS
 select COD_GRUPO_CIA,SEC_PROM_COMPRA,FECH_INI,FECH_FIN,FECHA,DIA,POS,FACTOR,TIPO
 from   (
            -- 1 Universo de listado de Prom antes de Iniciar para reponer el Max Unid Prom x FACTOR
            select vPA.COD_GRUPO_CIA,VPA.SEC_PROM_COMPRA,VPA.FECH_INI,VPA.FECH_FIN,VPA.FECHA,VPA.DIA,VPA.POS,
                   RANK() OVER (PARTITION BY vPA.COD_GRUPO_CIA,VPA.SEC_PROM_COMPRA,VPA.FECH_INI,VPA.FECH_FIN ORDER BY VPA.FECHA ASC) "FACTOR",
                   0 as "TIPO"
            from   (
                    SELECT A.COD_GRUPO_CIA,A.SEC_PROM_COMPRA,A.FECH_INI,A.FECH_FIN,c.fecha,c.dia,
                           RANK() OVER (PARTITION BY A.COD_GRUPO_CIA,A.SEC_PROM_COMPRA,A.FECH_INI,A.FECH_FIN ORDER BY c.fecha desc) "POS"
                    FROM   MAE_PROM_X_COMPRA A,
                           PBL_DIA_CALENDARIO c
                    WHERE  C_FECHA_IN < A.FECH_INI
          AND  A.ESTADO = 'A'
                    --AND    a.sec_prom_compra in ('0000000107','0000000008','0000000116','0000000009','0000000048','0000000046')
                    and    DECODE(cDiaSemanaIn,NULL,'S',DECODE(cDiaSemanaIn,REGEXP_REPLACE(cDiaSemanaIn,c.dia, 'S'),'N','S')) = 'S'
                    and    c.fecha < a.fech_ini
                    and    c.fecha > trunc(a.fech_ini-31)
                   ) vPA
            where  vPA.pos <= 3
            AND    VPA.FECHA = TRUNC(C_FECHA_IN)
            union
            -- 2 Universo de listado de Prom que esta DURANTE la PROMO
            --   REPONE desde la ultima repo hasta la fecha de generacion
            select vPA.COD_GRUPO_CIA,VPA.SEC_PROM_COMPRA,VPA.FECH_INI,VPA.FECH_FIN,VPA.FECHA,VPA.DIA,VPA.POS,0 FACTOR ,1 as "TIPO"
            from   (
                    SELECT A.COD_GRUPO_CIA,A.SEC_PROM_COMPRA,A.FECH_INI,A.FECH_FIN,c.fecha,c.dia,
                           RANK() OVER (PARTITION BY A.COD_GRUPO_CIA,A.SEC_PROM_COMPRA,A.FECH_INI,A.FECH_FIN ORDER BY c.fecha desc) "POS"
                    FROM   MAE_PROM_X_COMPRA A,
                           PBL_DIA_CALENDARIO c
                    WHERE  C_FECHA_IN >= A.FECH_INI
          AND  A.ESTADO = 'A'
                   -- AND    a.sec_prom_compra in ('0000000107','0000000008','0000000116','0000000009','0000000048','0000000046')
                    and    DECODE(cDiaSemanaIn,NULL,'S',DECODE(cDiaSemanaIn,REGEXP_REPLACE(cDiaSemanaIn,c.dia, 'S'),'N','S')) = 'S'
                    and    c.fecha >= a.fech_ini
                    and    c.fecha <= a.fech_fin
                   ) vPA
            where  vPA.pos > 2
            AND    VPA.FECHA = TRUNC(C_FECHA_IN)
            union
            -- 3 repone a las 2 ultimas reposiciones antes de terminar las promo
            --   El factor debe ser MAYOR q CERO esto es para el caso q coincide la fecha de Repo con el FIN de PROMO
            select vPA.COD_GRUPO_CIA,VPA.SEC_PROM_COMPRA,VPA.FECH_INI,VPA.FECH_FIN,VPA.FECHA,VPA.DIA,VPA.POS,
                   VPA.FECH_FIN - VPA.FECHA "FACTOR",2 as "TIPO"
            from   (
                    SELECT A.COD_GRUPO_CIA,A.SEC_PROM_COMPRA,A.FECH_INI,A.FECH_FIN,c.fecha,c.dia,
                           RANK() OVER (PARTITION BY A.COD_GRUPO_CIA,A.SEC_PROM_COMPRA,A.FECH_INI,A.FECH_FIN ORDER BY c.fecha desc) "POS"
                    FROM   MAE_PROM_X_COMPRA A,
                           PBL_DIA_CALENDARIO c
                    WHERE  C_FECHA_IN >= A.FECH_INI
          AND  A.ESTADO = 'A'
                   -- AND    a.sec_prom_compra in ('0000000107','0000000008','0000000116','0000000009','0000000048','0000000046')
                    and    DECODE(cDiaSemanaIn,NULL,'S',DECODE(cDiaSemanaIn,REGEXP_REPLACE(cDiaSemanaIn,c.dia, 'S'),'N','S')) = 'S'
                    and    c.fecha >= a.fech_ini
                    and    c.fecha <= a.fech_fin
                   ) vPA
            where  vPA.pos <= 2
            AND    VPA.FECHA = TRUNC(C_FECHA_IN)
            and    (VPA.FECH_FIN - VPA.FECHA) > 0
    ) vPromo
    order by vPromo.tipo asc,vPromo.sec_prom_compra;

    vCodProd   varchar2(20);
    vStkMaximo MF_LGT_PROD_LOCAL_REP.STK_MAXIMO%type;
    vStkIdealProm number;
    vStkIdelCalulado number;
    vStkLocal  number;
    vStkTra    number;
    vDiasProm  number;
    vFactor    number;
    vReponeNuevo number;
    dFechaMaxPedido date;
    existe_prod_Rep number;
    cod_prod_Fasa_in varchar2(20);
   begin
      SELECT CASE WHEN LUNES  = 'X' THEN '7' ELSE '' END ||
             CASE WHEN MARTES = 'X' THEN '1' ELSE '' END ||
             CASE WHEN MIERCOLES = 'X' THEN '2' ELSE ''   END ||
             CASE WHEN JUEVES   = 'X' THEN '3' ELSE '' END ||
             CASE WHEN VIERNES  = 'X' THEN '4' ELSE '' END ||
             CASE WHEN SABADO   = 'X' THEN '5' ELSE '' END DIA_SEMANA
      into   vDiaSemanaRep
      FROM   AUX_CRON_REP_3_MARCAS a
      where  a.cod_local = cCodLocal_MF
      AND    A.MARCA = 'MF';
      dbms_output.put_line(vDiaSemanaRep);
       -- corre cada promocion
       FOR curPromo IN curPromAnalisis(vDiaSemanaRep,trunc(sysdate))
       LOOP
         --COD_GRUPO_CIA,SEC_PROM_COMPRA,FECH_INI,FECH_FIN,FECHA,DIA,POS,FACTOR,TIPO
         dbms_output.put_line(curPromo.SEC_PROM_COMPRA);

/*        vCodProd   varchar2(20);
          vStkMaximo LGT_PROD_LOCAL_REP.STK_MAXIMO%type;
          vStkIdealProm number;
          vStkIdelCalulado number;

          vStkLocal  number;
          vStkTra    number;
          vDiasProm  number;     */

          --- dia de promocion
          select M.FECH_FIN - M.FECH_INI,m.cod_prod_prom
          INTO   vDiasProm,vCodProd
          from   MAE_PROM_X_COMPRA M
          where  m.cod_grupo_cia = curPromo.cod_grupo_cia
          and    m.sec_prom_compra = curPromo.SEC_PROM_COMPRA;

          begin
               select l.cod_prod_mf,l.vta_estimada/vDiasProm
               into   vCodProd,vStkIdealProm
               from   LGT_PROM_X_LOCAL l
               where  l.cod_grupo_cia = curPromo.cod_grupo_cia
               AND    L.COD_LOCAL = cCodLocal_MF
               and    l.sec_prom_compra = curPromo.SEC_PROM_COMPRA;
          exception
          when others then
            vStkIdealProm := 0;
          end;


         begin
             select r.stk_maximo
             into   vStkMaximo
             from   MF_LGT_PROD_LOCAL_REP r
             where  r.cod_local_mf = cCodLocal_MF
             and    r.Cod_Prod_MF =  vCodProd;
          exception
          when no_data_found then
            vStkMaximo := 0;
          end;

          begin
            select   s.Stk_Fisico/S.VAL_FRAC_LOCAL
            into     vStkLocal
            from     LGT_PROD_LOCAL s
            where    s.Cod_Prod = vCodProd
            and      s.cod_locaL = cCodLocal_mF;
          exception
          when no_data_found then
            vStkLocal := 0;
          end;
          ---------------------
          begin
            select t.stk
            into   vStkTra
            from   MF_STK_TRAN_PROD_LOCAL t
            where  t.cod_prod_mf = vCodProd
            and    t.cod_local_mf = cCodLocal_MF;
          exception
          when no_data_found then
            vStkTra := 0;
          end;

          /*BEGIN
            select h.Cod_Producto
            into   cod_prod_Fasa_in
            from   CMR.AUX_MAE_PRODUCTO_COM h
            where  h.cod_codigo_sap =  vCodProd;
          EXCEPTION
          WHEN NO_DATA_FOUND THEN
             cod_prod_Fasa_in := '0';
          END;  */

          cod_prod_Fasa_in := vCodProd;
       --- obtiene el Mayor
       select Greatest(vStkMaximo,vStkIdealProm)
       into   vStkIdelCalulado
       from   dual;

       vFactor := curPromo.FACTOR;

      -- dbms_output.put_line('<>vStkIdelCalulado:'||vStkIdelCalulado ||'   vFactor> ' ||vFactor);

         if curPromo.TIPO = 0 then -- Repone antes de inicio la promo
        --   dbms_output.put_line('<>Repone antes de inicio la promo ');
        ----   dbms_output.put_line('<>vStkLocal '||vStkLocal);
        --   dbms_output.put_line('<>vStkTra '||vStkTra);
           vReponeNuevo := vStkIdelCalulado*vFactor - vStkLocal - vStkTra;
           if vReponeNuevo < 0  then vReponeNuevo := 0; end if;
         else
           if curPromo.TIPO = 1 then -- repone durante la promo antes de los dias q acabe
           --   dbms_output.put_line('**********<>repone durante la promo antes de los dias q acabe********');
              ----
              /*select max(i.fec_pedido)
              into   dFechaMaxPedido
              from   int_pedido_rep i
              where  i.cod_local_fasa = cCodLocal_Fasa
              and    i.cod_prod_fasa = cod_prod_Fasa_in
              and    i.fec_pedido <= trunc(sysdate-1);*/

              select max(c.fecha)
              into   dFechaMaxPedido
              from   PBL_DIA_CALENDARIO c
              WHERE  DECODE(vDiaSemanaRep,NULL,'S',
                            DECODE(vDiaSemanaRep,
                                   REGEXP_REPLACE(vDiaSemanaRep,c.dia, 'S'),'N','S')) = 'S'
              and    c.fecha <= trunc(sysdate-1);

             --  dbms_output.put_line('<>cCodLocal_Fasa'||cCodLocal_Fasa);
             --  dbms_output.put_line('<>cod_prod_Fasa_in'||cod_prod_Fasa_in);
              ----
              select sum(v.cant_unid_vta)
              into   vReponeNuevo
              from   vta_res_vta_prod_local v
              where  V.COD_GRUPO_CIA = '001'
              AND    v.cod_local = cCodLocal_MF
              and    v.cod_prod = cod_prod_Fasa_in
              and    v.fec_dia_vta between trunc(dFechaMaxPedido) + 1 - 1/24/60/60 and sysdate;

            --  dbms_output.put_line('<>repone venta de '||dFechaMaxPedido||' a '||
             --                      trunc(sysdate) || ' vReponeNuevo '||vReponeNuevo);
              if vReponeNuevo < 0  then vReponeNuevo := 0; end if;
           else
               if curPromo.TIPO = 2 then -- repone dias antes q acabe la promo
              ---   dbms_output.put_line('<>repone dias antes q acabe la promo');
                 vReponeNuevo := vStkIdelCalulado*vFactor - vStkLocal - vStkTra;
             --    dbms_output.put_line('<>vStkLocal '||vStkLocal);
             --    dbms_output.put_line('<>vStkTra '||vStkTra);
                 if vReponeNuevo < 0  then vReponeNuevo := 0; end if;
               else
                 vReponeNuevo := 0;
               --  dbms_output.put_line('<>error no es ningun tipo');
               end if;
           end if;
         end if;

         -- se ve la cantidad y ve si se reemplazaraa
         if vReponeNuevo > 0 then

           vReponeNuevo := ceil(vReponeNuevo);

           --dbms_output.put_line('>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<>00000');
            select count(1)
              into existe_prod_Rep
              from MF_LGT_PROD_LOCAL_REP rep
             where REP.COD_LOCAL_MF = cCodLocal_MF
               and rep.Cod_Prod_MF = cod_prod_Fasa_in;

             if existe_prod_Rep = 0 then
                insert into MF_LGT_PROD_LOCAL_REP NOLOGGING
                                (COD_PROD_MF,
                                 COD_LOCAL_mf,
                                 X_REPONER_FINAL,
                                 X_REPONER_nuevo,
                                 X_LA_PROM,
                                 SEC_PROM_COMPRA,P_STKMAXIMO,P_STKIDEALPROM,
                                 P_STKIDELCALULADO,P_STKLOCAL,P_STKTRA,
                                 P_DIASPROM,P_FACTOR,P_REPONENUEVO,
                                 P_FECHAMAXPEDIDO,P_EXISTE_PROD_REP,P_CASO_PROM)
                              values
                                (cod_prod_Fasa_in,
                                 cCodLocal_MF,
                                 vReponeNuevo,
                                 vReponeNuevo,
                                 vReponeNuevo,
                                 curPromo.SEC_PROM_COMPRA,vStkMaximo,vStkIdealProm,
                                 vStkIdelCalulado,vStkLocal,vStkTra,
                                 vDiasProm,vFactor,vReponeNuevo,
                                 dFechaMaxPedido,existe_prod_Rep,curPromo.TIPO
                                 );
            else
              UPDATE MF_LGT_PROD_LOCAL_REP NOLOGGING
                 SET X_REPONER_FINAL = vReponeNuevo,
                     X_REPONER_nuevo = vReponeNuevo,
                     X_LA_PROM    = vReponeNuevo,
                     SEC_PROM_COMPRA =curPromo.SEC_PROM_COMPRA,
                     P_STKMAXIMO = vStkMaximo,
                     P_STKIDEALPROM = vStkIdealProm,
                     P_STKIDELCALULADO = vStkIdelCalulado,
                     P_STKLOCAL = vStkLocal,
                     P_STKTRA = vStkTra,
                     P_DIASPROM =vDiasProm,
                     P_FACTOR = vFactor,
                     P_REPONENUEVO = vReponeNuevo,
                     P_FECHAMAXPEDIDO =dFechaMaxPedido,
                     P_EXISTE_PROD_REP = existe_prod_Rep,
                     P_CASO_PROM = curPromo.TIPO
               where COD_LOCAL_mf = cCodLocal_mf
                 and COD_PROD_mf = cod_prod_Fasa_in;
             end if;


         end if;

       end loop;



   end;
/* **************************************************************************************** */
PROCEDURE REP_ACTUALIZA_CANT_SUG(vCodGrupoCia IN CHAR, vCodLocal IN CHAR, vCodGrupo IN CHAR DEFAULT NULL) AS
--DECLARE
  --Obtengo en este cursor los codigos de los grupos de productos activos.
  CURSOR curGrupos IS
    SELECT DISTINCT G.COD_GRUPO
      FROM LGT_GRUPO_SIMILAR G,
           LGT_PROD_GRUPO_SIMILAR GP,
           LGT_PROD_LOCAL_REP PLR
     WHERE EST_GRUPO_SIMILAR = 'A'
       AND GP.EST_PROD_GRUPO_SIMILAR = 'A'
       AND GP.COD_GRUPO = G.COD_GRUPO
       AND PLR.COD_GRUPO_CIA = GP.COD_GRUPO_CIA
       AND PLR.COD_PROD = GP.COD_PROD
       AND PLR.CANT_SUG > 0
       AND G.COD_GRUPO = NVL(vCodGrupo, G.COD_GRUPO)
     ORDER BY COD_GRUPO;

/*
  -- 2009-05-20 DOCUMENTADO POR JOLIVA
  -- Obtengo en este cursor los productos:
  1. Pertenecientes al grupo indicado
  2. Que solicitan reponer mercaderia
  3. Que no tienen stock en almacen
*/
  CURSOR curProdGrupo(cCodGrupo CHAR) IS

        SELECT T.COD_PROD,
               T.CANT_SUG,
               T.CANT_SUG_BK,
               P.VAL_MAX_FRAC,
               T.UNID_MIN_SUG,
               T.STK_ALMACEN,
               P.IND_ZAN
        FROM LGT_PROD P,
             LGT_PROD_GRUPO_SIMILAR D,
             LGT_PROD_LOCAL_REP    T
       WHERE P.COD_GRUPO_CIA = vCodGrupoCia
         AND D.EST_PROD_GRUPO_SIMILAR = 'A'
         AND D.COD_GRUPO_CIA = P.COD_GRUPO_CIA
         AND D.COD_GRUPO = cCodGrupo
         AND D.COD_PROD = P.COD_PROD
         AND T.COD_GRUPO_CIA = D.COD_GRUPO_CIA
         AND T.COD_PROD = D.COD_PROD
         AND T.CANT_SUG > 0
--         AND D.COD_GRUPO = '001040'
         AND T.IND_STK_ALMACEN = 'N';

  vStockIdealEfectivo NUMBER;
  vStockIdealReal     NUMBER;
  vStockMaximoGrupo   NUMBER;
  vCodProdTemp        CHAR(6);

  -- 2009-06-03 JOLIVA
  vFactorConversion NUMBER(4);
  vStockTotalGrupoUnidMinimas NUMBER(9);
  vStockAdicionalProdSugerido NUMBER(9,2);
  vSugeridoUnidMinimaProdTmp  LGT_PROD_LOCAL_REP.UNID_MIN_SUG%TYPE;

  --dubilluz 19.03.2012
  vStkFisico_Actual number;
  vStkTransito number;
  vSugeridoActual number;
  v_nCantSugeridaRepCorrecto number;

BEGIN

  -- 2009-05-20 DOCUMENTADO POR JOLIVA
  -- Saca backup a la 每潞ltima cantidad SUGERIDA y obtiene el sugerido convertido a la unidad m每颅nima del producto
   UPDATE LGT_PROD_LOCAL_REP lplrp
         SET lplrp.UNID_MIN_SUG = CANT_SUG * (select CANT_EQ_GRU_SIM from lgt_prod p where p.cod_grupo_cia=lplrp.cod_grupo_cia and p.cod_prod=lplrp.cod_prod),
             lplrp.STK_UNID_MIN = lplrp.STK_FISICO * (select CANT_EQ_GRU_SIM from lgt_prod p where p.cod_grupo_cia=lplrp.cod_grupo_cia and p.cod_prod=lplrp.cod_prod) / lplrp.VAL_FRAC_LOCAL,
             lplrp.CANT_SUG_BK = CANT_SUG,
             lplrp.IND_STK_ALMACEN = (SELECT IND_STK_ALMACEN
                                    FROM TMP_PBL_INDICADORES_STK t
                                   WHERE t.COD_PROD       = lplrp.cod_prod
                                     AND t.COD_GRUPO_CIA  = lplrp.cod_grupo_cia
                                     AND t.COD_LOCAL      = lplrp.cod_local )
       WHERE exists (
                     select 1
                     from   LGT_PROD_LOCAL_REP    T,
                            LGT_PROD_GRUPO_SIMILAR D
                      WHERE T.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                        AND T.COD_PROD      = D.COD_PROD
                    );


  FOR v_cur_Grupos IN curGrupos LOOP

    BEGIN
         IF vCodGrupo IS NOT NULL THEN
            DBMS_OUTPUT.put_line('v_cur_Grupos.COD_GRUPO=' || v_cur_Grupos.COD_GRUPO);
         END IF;

  -- Se obtiene el stock efectivo ideal por grupo de productos.
/*
      SELECT MAX(SUM(A.CANT_UNID_VTA * B.CANT_EQ_GRU_SIM * 30 /
                     A.NUM_DIA_EFECTIVO))
*/

  -- 2009-11-24 JOLIVA: SE MODIFICA PARA QUE S每驴LO TRABAJE CON ENTEROS
        SELECT CEIL(NVL(MAX(
                       SUM(
                   A.cant_unid_vta * CASE WHEN A.fecha_mes = TRUNC(SYSDATE,'MM') THEN 1
                                          WHEN A.num_dia_efectivo < 15 THEN 1
                                          ELSE 30 / DECODE(A.num_dia_efectivo,0,1,A.num_dia_efectivo)
                                     END
                       )
               ),0))
        INTO vStockIdealEfectivo
        FROM LGT_PROD_GRUPO_SIMILAR C,
             VTA_RES_VTA_EFECTIVA_LOCAL   A,
             LGT_PROD                     B
       WHERE C.COD_GRUPO_CIA = vCodGrupoCia
         AND C.EST_PROD_GRUPO_SIMILAR = 'A'
         AND C.COD_GRUPO = v_cur_Grupos.COD_GRUPO
         AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND A.FECHA_MES BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -3) AND TRUNC(SYSDATE)
         AND A.COD_PROD = C.COD_PROD
         AND B.COD_PROD = C.COD_PROD
       GROUP BY C.COD_GRUPO, A.FECHA_MES;


-- 2009-06-26 FALTA AGREGAR ESTA LOGICA PARA LA ROTACION REAL
-- 2010-06-24 JOLIVA: SE AGREGA NUEVO RANGO DE FILTRO: SI VTA > 120 --> VTA / 5
        select CASE
                    WHEN vStockIdealEfectivo > 120  THEN vStockIdealEfectivo/5
                    WHEN vStockIdealEfectivo > 30  THEN vStockIdealEfectivo/4
                    WHEN vStockIdealEfectivo >15   THEN vStockIdealEfectivo/3
                    WHEN vStockIdealEfectivo >1     THEN vStockIdealEfectivo/2
                    WHEN vStockIdealEfectivo <=1   THEN 1
                    WHEN vStockIdealEfectivo =0     THEN 0
                END
        into   vStockIdealEfectivo
        from   dual;
/*
        select CASE WHEN v_nMaxStockIdelEfecivo > v_max_stk_g1  THEN v_nMaxStockIdelEfecivo/ v_fac_g1
                    WHEN v_nMaxStockIdelEfecivo > v_max_stk_g2   THEN v_nMaxStockIdelEfecivo/ v_fac_g2
                    WHEN v_nMaxStockIdelEfecivo > v_max_stk_g3     THEN v_nMaxStockIdelEfecivo/ v_fac_g3
                    WHEN v_nMaxStockIdelEfecivo <= v_max_stk_g4   THEN v_fac_g4
                    WHEN v_nMaxStockIdelEfecivo =0     THEN 0
                END
        into   v_nStockIdelEfectivo
        from   dual;
*/

      --Se obtiene el stock real ideal por grupo de productos.
      SELECT MAX(SUM(A.CANT_UNID_VTA * B.CANT_EQ_GRU_SIM))
        INTO vStockIdealReal
        FROM LGT_PROD_GRUPO_SIMILAR C,
             VTA_RES_VTA_EFECTIVA_LOCAL   A,
             LGT_PROD                     B
       WHERE C.COD_GRUPO_CIA = vCodGrupoCia
         AND C.EST_PROD_GRUPO_SIMILAR = 'A'
         AND C.COD_GRUPO = v_cur_Grupos.COD_GRUPO
         AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND A.FECHA_MES BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -3) AND TRUNC(SYSDATE)
         AND A.COD_PROD = C.COD_PROD
         AND B.COD_PROD = C.COD_PROD
       GROUP BY C.COD_GRUPO, A.FECHA_MES;


-- 2009-06-26 FALTA AGREGAR ESTA LOGICA PARA LA ROTACION REAL
        select CASE WHEN vStockIdealReal > 1   THEN vStockIdealReal/2
                    WHEN vStockIdealReal <= 1 and
                         vStockIdealReal >0  THEN 1
                    WHEN vStockIdealReal = 0   THEN 0
               END
        into   vStockIdealReal
        from   dual;


      --Se obtiene el promedio del stock efectivo y el real para obtener el
      --stock maximo por grupos.
      vStockMaximoGrupo := FLOOR((vStockIdealEfectivo + vStockIdealReal) / 2);

      --Se actualiza el campo MAX_GRUPO de la tabla LGT_GRUPO_PROD_LOCAL_REP_CAB
      --con el promedio obtenido en el paso anterior.
      UPDATE LGT_GRUPO_SIMILAR_LOCAL
         SET MAX_GRUPO = vStockMaximoGrupo
       WHERE COD_GRUPO_CIA = vCodGrupoCia
         AND COD_LOCAL = vCodLocal
         AND COD_GRUPO = v_cur_Grupos.COD_GRUPO;

      COMMIT;

         IF vCodGrupo IS NOT NULL THEN
            DBMS_OUTPUT.PUT_LINE('MAXIMO DE GRUPO:' || vStockMaximoGrupo);
         END IF;
    END;

    -- 2009-05-20 JOLIVA: Obtener el c每鲁digo de producto que se usar每隆 para reemplazar a los SOLICITADOS que no tengan stock en almacen
      BEGIN
          SELECT COD_PROD, CANT_EQ_GRU_SIM -- , UNID_MIN_SUG
          INTO vCodProdTemp, vFactorConversion --, vSugeridoUnidMinimaProdTmp
          FROM
               (
                  SELECT
                         P.COD_PROD, P.DESC_PROD, P.DESC_UNID_PRESENT,
                         P.CANT_EQ_GRU_SIM,
                         PLR.UNID_MIN_SUG,
                         TRIM(P.IND_ZAN),
                         PLR.IND_STK_ALMACEN,
                         RANK() OVER(ORDER BY NVL(TRIM(P.IND_ZAN),' ') DESC, P.COD_PROD DESC) POS
                  FROM LGT_PROD P,
                       LGT_PROD_GRUPO_SIMILAR GP,
                       LGT_PROD_LOCAL_REP PLR
                  WHERE GP.COD_GRUPO = v_cur_Grupos.COD_GRUPO
                    AND GP.EST_PROD_GRUPO_SIMILAR = 'A'
                    AND PLR.COD_GRUPO_CIA = vCodGrupoCia
                    AND PLR.COD_LOCAL = vCodLocal
                    AND PLR.COD_PROD = GP.COD_PROD
                    AND P.COD_GRUPO_CIA = PLR.COD_GRUPO_CIA
                    AND P.COD_PROD = PLR.COD_PROD
                    AND PLR.IND_STK_ALMACEN = 'S'
               )
          WHERE POS = 1;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          vCodProdTemp := NULL;
      END;


    -- 2009-06-29 JOLIVA: Voy a asumir que todo lo que se va a pedir de los productos que tienen stock en almacen ser每隆 atendido
    --                    Y ya lo considerar每漏 como parte del stock del grupo
      BEGIN
            SELECT
                   SUM(PLR.UNID_MIN_SUG)
            INTO vSugeridoUnidMinimaProdTmp
            FROM LGT_PROD P,
                 LGT_PROD_GRUPO_SIMILAR GP,
                 LGT_PROD_LOCAL_REP PLR
            WHERE GP.COD_GRUPO = v_cur_Grupos.COD_GRUPO
              AND GP.EST_PROD_GRUPO_SIMILAR = 'A'
              AND PLR.COD_GRUPO_CIA = vCodGrupoCia
              AND PLR.COD_LOCAL = vCodLocal
              AND PLR.COD_PROD = GP.COD_PROD
              AND P.COD_GRUPO_CIA = PLR.COD_GRUPO_CIA
              AND P.COD_PROD = PLR.COD_PROD
              AND PLR.IND_STK_ALMACEN = 'S';
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          vSugeridoUnidMinimaProdTmp := 0;
      END;

         IF vCodGrupo IS NOT NULL THEN
            DBMS_OUTPUT.PUT_LINE('vCodProdTemp=' || vCodProdTemp ||', vFactorConversion=' || vFactorConversion);
         END IF;


--        SELECT SUM(T.UNID_MIN_SUG)
        SELECT SUM(T.STK_UNID_MIN)
          INTO vStockTotalGrupoUnidMinimas
          FROM LGT_PROD_LOCAL_REP    T,
               LGT_PROD_GRUPO_SIMILAR D
         WHERE T.COD_GRUPO_CIA = D.COD_GRUPO_CIA
           AND D.EST_PROD_GRUPO_SIMILAR = 'A'
           AND T.COD_PROD = D.COD_PROD
           AND D.COD_GRUPO = v_cur_Grupos.COD_GRUPO;

         IF vCodGrupo IS NOT NULL THEN
            DBMS_OUTPUT.PUT_LINE('vStockTotalGrupoUnidMinimas=' || vStockTotalGrupoUnidMinimas);
            DBMS_OUTPUT.PUT_LINE('vStockMaximoGrupo=' || vStockMaximoGrupo);
         END IF;

    -- Si no se encuentra ningun producto en algun grupo con stock disponible
    -- no ingresa al proceso; esto es validado por el vFlag.
    IF (vCodProdTemp IS NOT NULL) THEN

    -- Empiezo con adicional igual a cero
      vStockAdicionalProdSugerido := 0;

    -- Asumo que
    -- A partir de aqui no se debe superar el m每隆ximo del grupo
      vStockTotalGrupoUnidMinimas :=  vStockTotalGrupoUnidMinimas + vSugeridoUnidMinimaProdTmp;
         IF vCodGrupo IS NOT NULL THEN
            DBMS_OUTPUT.PUT_LINE('Empieza vStockTotalGrupoUnidMinimas=' || vStockTotalGrupoUnidMinimas);
         END IF;
      FOR v_cur_ProdGrupo IN curProdGrupo(v_cur_Grupos.COD_GRUPO) LOOP

         IF vCodGrupo IS NOT NULL THEN
            DBMS_OUTPUT.PUT_LINE('v_cur_ProdGrupo.COD_PROD=' || v_cur_ProdGrupo.COD_PROD ||', v_cur_ProdGrupo.UNID_MIN_SUG=' || v_cur_ProdGrupo.UNID_MIN_SUG);
            DBMS_OUTPUT.PUT_LINE('vStockAdicionalProdSugerido=' || vStockAdicionalProdSugerido);
            DBMS_OUTPUT.PUT_LINE('vStockTotalGrupoUnidMinimas=' || vStockTotalGrupoUnidMinimas);
            DBMS_OUTPUT.PUT_LINE('vStockMaximoGrupo=' || vStockMaximoGrupo);
         END IF;


          IF ( ( vStockAdicionalProdSugerido + vStockTotalGrupoUnidMinimas + v_cur_ProdGrupo.UNID_MIN_SUG ) <= vStockMaximoGrupo) THEN
             vStockAdicionalProdSugerido :=  vStockAdicionalProdSugerido + (1.0 * v_cur_ProdGrupo.UNID_MIN_SUG / vFactorConversion);
             IF vCodGrupo IS NOT NULL THEN
                DBMS_OUTPUT.PUT_LINE('1. vStockAdicionalProdSugerido=' || vStockAdicionalProdSugerido);
             END IF;
          ELSE
              IF (vStockAdicionalProdSugerido + vStockTotalGrupoUnidMinimas < vStockMaximoGrupo ) THEN
                 vStockAdicionalProdSugerido :=  vStockAdicionalProdSugerido + ( ( vStockMaximoGrupo - (vStockAdicionalProdSugerido + vStockTotalGrupoUnidMinimas)) / vFactorConversion);
                 IF vCodGrupo IS NOT NULL THEN
                    DBMS_OUTPUT.PUT_LINE('2. vStockAdicionalProdSugerido=' || vStockAdicionalProdSugerido);
                 END IF;
              ELSE
                 IF vCodGrupo IS NOT NULL THEN
                    DBMS_OUTPUT.PUT_LINE('Ya se super每鲁 el stock m每隆ximo del grupo=' || (vStockAdicionalProdSugerido + vStockTotalGrupoUnidMinimas));
                 END IF;
              END IF;
          END IF;

      END LOOP;

      --Se actualiza el campo CANT_SUG de la tabla LGT_PROD_LOCAL_REP
      --con la nueva cantidad sugerida del producto que reemplazara
      --a los que no tienen stock.
      --Modificado por dveliz 15.01.2009

      DBMS_OUTPUT.PUT_LINE('a silicitar CEIL(vStockAdicionalProdSugerido):'||CEIL(vStockAdicionalProdSugerido));

      -- v_nCantSugeridaReponer := FLOOR((v_nCantMax)-(v_rProd.STK_FISICO/v_rProd.VAL_FRAC_LOCAL)-v_rProd.STK_TRANSITO);
  --dubilluz 19.03.2012
  --vStkFisico_Actual number;
  --vStkTransito number;

     select l.stk_fisico/l.val_frac_local
     into   vStkFisico_Actual
     from   lgt_prod_local l
      WHERE COD_GRUPO_CIA = vCodGrupoCia
      AND COD_LOCAL = vCodLocal
      AND COD_PROD = vCodProdTemp;

      begin
      select nvl(a.stk_transito,0)
      into   vStkTransito
      from   AUX_STK_TRANSITO a
      where  a.cod_grupo_cia = vCodGrupoCia
      and    a.cod_local = vCodLocal
      and    a.cod_prod = vCodProdTemp;
      exception
      when no_data_found then
        vStkTransito := 0;
      end;

      begin
      select nvl(CANT_SUG,0)
      into   vSugeridoActual
      from   lgt_prod_local_rep
      WHERE COD_GRUPO_CIA = vCodGrupoCia
         AND COD_LOCAL = vCodLocal
         AND COD_PROD = vCodProdTemp;
           exception
      when no_data_found then
        vSugeridoActual := 0;
      end;

     select
           case
           when FLOOR((vSugeridoActual+CEIL(vStockAdicionalProdSugerido))- (vStkFisico_Actual)-vStkTransito) <= 0 then 0
           else
               FLOOR((vSugeridoActual+CEIL(vStockAdicionalProdSugerido))- (vStkFisico_Actual)-vStkTransito)
           end
     into v_nCantSugeridaRepCorrecto
     from dual;

     DBMS_OUTPUT.PUT_LINE('vStkFisico_Actual:'||vStkFisico_Actual);
     DBMS_OUTPUT.PUT_LINE('vStkTransito:'||vStkTransito);
     DBMS_OUTPUT.PUT_LINE('vSugeridoActual:'||vSugeridoActual);

     DBMS_OUTPUT.PUT_LINE('--->>>> deberia reponer ..>>> '||v_nCantSugeridaRepCorrecto);

     if v_nCantSugeridaRepCorrecto > 0 then
     /*
     UPDATE LGT_PROD_LOCAL_REP
         --SET CANT_SUG = vNuevoSugerido
         SET CANT_SUG = CANT_SUG + CEIL(vStockAdicionalProdSugerido),
             CANT_ADICIONAL = CEIL(vStockAdicionalProdSugerido),
             FEC_MOD_PROD_LOCAL_REP = SYSDATE
      WHERE COD_GRUPO_CIA = vCodGrupoCia
         AND COD_LOCAL = vCodLocal
         AND COD_PROD = vCodProdTemp;
      */
     UPDATE LGT_PROD_LOCAL_REP
         --SET CANT_SUG = vNuevoSugerido
         SET CANT_SUG = CEIL(v_nCantSugeridaRepCorrecto),
             CANT_SOL = CEIL(v_nCantSugeridaRepCorrecto),
             CANT_ADICIONAL = CEIL(v_nCantSugeridaRepCorrecto),
             FEC_MOD_PROD_LOCAL_REP = SYSDATE
      WHERE COD_GRUPO_CIA = vCodGrupoCia
         AND COD_LOCAL = vCodLocal
         AND COD_PROD = vCodProdTemp;

      end if;
      COMMIT;
    ELSE
        NULL;
    END IF;
  END LOOP;
END REP_ACTUALIZA_CANT_SUG;
/* **************************************************************************************** */
PROCEDURE VERIFICA_VENTAS_REP(vCodLocal IN CHAR) AS
v_local_1 char(3):='999';
v_local_2 char(3):='999';
v_venta_real_0 float:=0;
v_venta_real_1 float:=0;
v_venta_real_2 float:=0;
v_venta_real_3 float:=0;
v_venta_rep_0 float:=0;
v_venta_rep_1 float:=0;
v_venta_rep_2 float:=0;
v_venta_rep_3 float:=0;

BEGIN
-- VENTA PARA LA REPOSICION DE LOS ULTIMOS 4 MESES CONTANDO EL ACTUAL

SELECT COD_LOCAL_MF,
       ROUND(sum(case
           when a.MES between
                TO_NUMBER(TO_CHAR(Add_months(trunc(sysdate, 'MM'), -3),'YYYYMM')) and
                TO_NUMBER(TO_CHAR(Add_months(trunc(sysdate, 'MM'), -2) - 1 / 24 / 60 / 60,'YYYYMM')) then
            a.CANT_UNID_VTA_SIN_PROM
           else
            0
         end),3) MES_3,
       ROUND(sum(case
           when a.MES between
                TO_NUMBER(TO_CHAR(Add_months(trunc(sysdate, 'MM'), -2),'YYYYMM')) and
                TO_NUMBER(TO_CHAR(Add_months(trunc(sysdate, 'MM'), -1) - 1 / 24 / 60 / 60,'YYYYMM')) then
            a.CANT_UNID_VTA_SIN_PROM
           else
            0
         end),3) MES_2,
       ROUND(sum(case
           when a.MES between
                TO_NUMBER(TO_CHAR(Add_months(trunc(sysdate, 'MM'), -1),'YYYYMM')) and
                TO_NUMBER(TO_CHAR(Add_months(trunc(sysdate, 'MM'), -0) - 1 / 24 / 60 / 60,'YYYYMM')) then
            a.CANT_UNID_VTA_SIN_PROM
           else
            0
         end),3) MES_1,
       ROUND(sum(case
           when TRUNC(a.Fec_Dia_Vta) between
                trunc(sysdate, 'MM') and TRUNC(sysdate-1) then
            a.CANT_UNID_VTA_SIN_PROM
           else
            0
         end),3) MES_0 into v_local_1, v_venta_rep_3, v_venta_rep_2, v_venta_rep_1, v_venta_rep_0
FROM   MF_VTA_RES_VTA_PROD_LOCAL A
WHERE A.COD_LOCAL_MF=vCodLocal
GROUP BY COD_LOCAL_MF;

-- VENTAS REALES DEL LOCAL DE LOS ULTIMOS 4 MESES CONTANDO EL ACTUAL
 SELECT C.COD_LOCAL,
       ROUND(sum(case
           when C.FEC_PED_VTA between
                Add_months(trunc(sysdate, 'MM'), -3) and
                Add_months(trunc(sysdate, 'MM'), -2) - 1 / 24 / 60 / 60 then
            (CANT_ATENDIDA/VAL_FRAC)
           else
            0
         end),3) MES_3,
       ROUND(sum(case
           when C.FEC_PED_VTA between
                Add_months(trunc(sysdate, 'MM'), -2) and
                Add_months(trunc(sysdate, 'MM'), -1) - 1 / 24 / 60 / 60 then
            (CANT_ATENDIDA/VAL_FRAC)
           else
            0
         end),3) MES_2,
       ROUND(sum(case
           when C.FEC_PED_VTA between
                Add_months(trunc(sysdate, 'MM'), -1) and
                Add_months(trunc(sysdate, 'MM'), -0) - 1 / 24 / 60 / 60 then
            (CANT_ATENDIDA/VAL_FRAC)
           else
            0
         end),3) MES_1,
       ROUND(sum(case
           when trunc(C.FEC_PED_VTA) between
                trunc(sysdate, 'MM') and TRUNC(sysdate-1) then
            (CANT_ATENDIDA/VAL_FRAC)
           else
            0
         end),3) MES_0 into v_local_2, v_venta_real_3, v_venta_real_2, v_venta_real_1, v_venta_real_0
FROM VTA_PEDIDO_VTA_DET D,
     VTA_PEDIDO_VTA_CAB C
WHERE C.COD_GRUPO_CIA = '001'
      AND C.COD_LOCAL = vCodLocal
      AND C.EST_PED_VTA = 'C'
      AND C.TIP_PED_VTA IN ('01','02')
      AND D.IND_CALCULO_MAX_MIN = 'S'
      AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
      AND C.COD_LOCAL = D.COD_LOCAL
      AND C.NUM_PED_VTA = D.NUM_PED_VTA
    --  AND COD_PROD='572010'--, '137903'
GROUP BY C.COD_LOCAL
HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0   ;

if ((v_venta_real_3 - v_venta_rep_3<20) and (v_venta_real_3 - v_venta_rep_3>-20)) and
   ((v_venta_real_2 - v_venta_rep_2<20) and (v_venta_real_2 - v_venta_rep_2>-20)) and
   ((v_venta_real_1 - v_venta_rep_1<20) and (v_venta_real_1 - v_venta_rep_1>-20)) and
   ((v_venta_real_0 - v_venta_rep_0<20) and (v_venta_real_0 - v_venta_rep_0>-20)) then

    DBMS_OUTPUT.PUT_LINE('Ventas Cargadas Correctamente...');

else
    begin
    DELETE FROM TMP_VENTAS_REPO_LOCALES A WHERE A.FECH_CREA<SYSDATE-31;
    INSERT INTO TMP_VENTAS_REPO_LOCALES
    SELECT v_local_1, v_venta_rep_3, v_venta_rep_2, v_venta_rep_1, v_venta_rep_0, v_venta_real_3, v_venta_real_2, v_venta_real_1, v_venta_real_0, SYSDATE
    FROM DUAL;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Hay diferencia en las ventas. REVISAR!!!');
exception
when others then
    DBMS_OUTPUT.PUT_LINE('ERROR AL GRABAR DIFERENCIA!!!'||SQLERRM);
    end;
end if;
END;
/* **************************************************************************************** */
END;
/

