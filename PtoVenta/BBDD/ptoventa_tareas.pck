CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_TAREAS" is

  --Descripcion: Graba el stock actual de los productso en el local
  --Fecha       Usuario	  Comentario
  --24/05/2006  ERIOS     Creacion
  PROCEDURE INV_GRABA_STOCK_ACTUAL_PRODS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsu_in IN CHAR);

  --Descripcion: Obtiene el numero de dias maximo de reposicion de un producto en un local.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creacion
  --SE COPIO EL PROCEDIMEINTO DE PTOVENTA_INV.
  FUNCTION INV_GET_STK_TRANS_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN NUMBER;

  --Descripcion: Guarda los dias de stock de los productos.
  --Fecha       Usuario	  Comentario
  --27/04/2007  ERIOS     Creacion
  PROCEDURE INV_GRABA_DIAS_STOCK_PRODS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsu_in IN CHAR);

  --Descripcion: Se actualiza el precio venta del producto por convenio
  --Fecha       Usuario	  Comentario
  --27/04/2007  ERIOS     Creacion
  PROCEDURE UPDATE_PREC_VTA_LIST_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

  --Descripcion: Se actualiza la información de la tabla ZAN_LOCAL
  --Fecha       Usuario	  Comentario
  --09/06/2008  ERIOS     Creacion
  PROCEDURE UPDATE_PROD_ZAN_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

  --Descripcion: ACTUALIZACION DE LA TABLA rep_prod_sobrestock
  --Fecha       Usuario	  Comentario
  --16/07/2008  JLUNA     Creacion
  PROCEDURE UPDATE_rep_prod_sobrestock;

  PROCEDURE P_INACTIVA_CAMP_PRIMERA_COMPRA(
                                           cCodGrupoCia_in 	   IN CHAR,
                				    	             cCodLocal_in    	   IN CHAR,
                					                 cNumPedVta_in   	   IN CHAR
                                          );

  PROCEDURE UPDATE_PREC_VTA_LIST_PROD_2(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

    --Descripcion: INSERTA LA TABLA VTA_PED_DCTO_CLI_AUX EN VTA_PED_DCTO_CLI_BKP Y DEJA SOLO LO DE HACER 7 DIAS
  --Fecha       Usuario	  Comentario
  --16/12/2010  ASOSA     Creacion
  PROCEDURE PTOVTA_P_INS_DSCTO_AUX_BKP(cCodGrupoCia_in 	   IN CHAR);

  --jmiranda 12.07.2011
  --ACTUALIZA PRODUCTOS NUEVO CONVENIO LISTA 00019
  PROCEDURE UPDATE_PREC_VTA_LIST_PROD_19(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

    PROCEDURE UPDATE_PREC_VTA_LIST_PROD_21(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

    PROCEDURE UPDATE_PREC_VTA_LIST_PROD_22(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

-- 2014-09-23 JOLIVA: SE AGREGA CALCULO DE VENTA PERDIDA SOLICITADO POR FCHAU
    PROCEDURE CARGA_VTA_PERDIDA_LOCAL_DIA (cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, dFecVtaPerdida_in IN DATE DEFAULT TRUNC(SYSDATE-1));

end;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_TAREAS" is
  /******************************************************************************/
  PROCEDURE INV_GRABA_STOCK_ACTUAL_PRODS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsu_in IN CHAR)
  AS
  BEGIN
    BEGIN
      INSERT INTO LGT_HIST_STK_LOCAL(COD_GRUPO_CIA,
                                      COD_LOCAL,
                                      COD_PROD,
                                      FEC_STK,
                                      CANT_STK,
                                      VAL_FRAC_PROD_LOCAL,
                                      USU_CREA_HIST_STK_LOCAL,
                                      STK_TRANSITO)
      SELECT COD_GRUPO_CIA,COD_LOCAL,COD_PROD,Trunc(SYSDATE-1) AS FECHA, STK_FISICO,VAL_FRAC_LOCAL,cIdUsu_in,
            INV_GET_STK_TRANS_PROD(COD_GRUPO_CIA,COD_LOCAL,COD_PROD)
      FROM LGT_PROD_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND STK_FISICO > 0;
    EXCEPTION
      WHEN DUP_VAL_ON_INDEX THEN
        NULL;
    END;

    -- ******************************************************
    -- CALCULO ABC POR UNIDADES PARA LOS PRODUCTOS DEL LOCAL
    --TMP_REP_ERN.REP_DETERMINAR_TIPO_UND(cCodGrupoCia_in, cCodLocal_in, TO_DATE(TRUNC(SYSDATE-30),'dd/MM/yyyy'), TO_DATE(TRUNC(SYSDATE),'dd/MM/yyyy'));
    TMP_REP_ERN.REP_DETERMINAR_TIPO(cCodGrupoCia_in, cCodLocal_in, TO_DATE(TRUNC(SYSDATE-30),'dd/MM/yyyy'), TO_DATE(TRUNC(SYSDATE),'dd/MM/yyyy'));

    COMMIT;

    INV_GRABA_DIAS_STOCK_PRODS(cCodGrupoCia_in,cCodLocal_in,cIdUsu_in);
  END;
  /*****************************************************************************/
  FUNCTION INV_GET_STK_TRANS_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR)
  RETURN NUMBER
  IS
    v_nTrans LGT_PROD_LOCAL_REP.CANT_TRANSITO%TYPE;
  BEGIN
    SELECT NVL(SUM(CANT_ENVIADA_MATR),0) INTO v_nTrans
    FROM LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
          AND D.COD_PROD = cCodProd_in
          AND C.TIP_NOTA_ES = '03'--g_cTipoNotaRecepcion
          AND D.IND_PROD_AFEC = 'N';
    RETURN v_nTrans;
  END;
  /******************************************************************************/
  PROCEDURE INV_GRABA_DIAS_STOCK_PRODS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsu_in IN CHAR)
  AS
  BEGIN
    EXECUTE IMMEDIATE 'TRUNCATE TABLE LGT_DIAS_STK_PROD';

    INSERT INTO LGT_DIAS_STK_PROD(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,
                                  DIAS_CON_STK,PER_STK,
                                  USU_CREA_DIAS_STK)
    SELECT COD_GRUPO_CIA, COD_LOCAL, COD_PROD,
           (TRUNC(SYSDATE-1) - MAX(H.FEC_STK)) DIAS_CON_STK,
           CASE WHEN TRUNC((TRUNC(SYSDATE-1) - MAX(H.FEC_STK)) / 30) > 6 THEN 6 ELSE TRUNC((TRUNC(SYSDATE-1) - MAX(H.FEC_STK)) / 30) END PER_STK,
           cIdUsu_in
    FROM LGT_HIST_STK_LOCAL H
    WHERE H.COD_GRUPO_CIA = cCodGrupoCia_in
          AND H.COD_LOCAL = cCodLocal_in
          AND NOT EXISTS (SELECT 1 FROM LGT_HIST_STK_LOCAL B
                     WHERE B.COD_GRUPO_CIA = H.COD_GRUPO_CIA
                           AND B.COD_LOCAL = H.COD_LOCAL
                           AND B.COD_PROD = H.COD_PROD
                           AND B.FEC_STK = H.FEC_STK - 1)
    GROUP BY COD_GRUPO_CIA, COD_LOCAL, COD_PROD;
    COMMIT;
  END;
/******************************************************************************/
  PROCEDURE UPDATE_PREC_VTA_LIST_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  AS
      v_cCodLista CON_LISTA.COD_LISTA%TYPE := '00011';
      v_cIdUsu VARCHAR2(15) := 'JOB_ACT_PPLISTA';
      v_nValDscto NUMBER := 5;

      ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_VIAJERO;
      CCReceiverAddress VARCHAR2(120) := NULL;

      v_vDescLocal VARCHAR2(120);

-- 2010-06-23 JOLIVA: SE CREA NUEVA LISTA
      v_cCodLista_16 CON_LISTA.COD_LISTA%TYPE := '00016';
      v_cIdUsu_16 VARCHAR2(15) := 'JOB_ACT_LIST_16';

  BEGIN

     UPDATE_PREC_VTA_LIST_PROD_19(cCodGrupoCia_in,cCodLocal_in);
     UPDATE_PREC_VTA_LIST_PROD_21(cCodGrupoCia_in,cCodLocal_in);
     UPDATE_PREC_VTA_LIST_PROD_22(cCodGrupoCia_in,cCodLocal_in);

      DELETE CON_PROD_LISTA
      WHERE COD_LISTA = v_cCodLista;

      INSERT INTO CON_PROD_LISTA(COD_LISTA,COD_GRUPO_CIA,COD_PROD,USU_CREA_PROD_LISTA,PREC_VTA)
           SELECT v_cCodLista,P.COD_GRUPO_CIA,P.COD_PROD,v_cIdUsu,
           ROUND(CASE WHEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100)) >= (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL) --ASOSA, 15.12.2010-primer case
                      THEN L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL
                 WHEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100)) > (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)*(1-(v_nValDscto/100))
                      THEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100))
                ELSE (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)*(1-(v_nValDscto/100))
           END ,3)AS PREC
      FROM LGT_PROD P,
           LGT_PROD_LOCAL L,
           PBL_IGV I
      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
          AND L.COD_LOCAL = cCodLocal_in
          AND P.IND_PRECIO_CONTROL = 'N'
          AND P.EST_PROD = 'A'
          AND P.COD_IGV = I.COD_IGV
          AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND P.COD_PROD = L.COD_PROD
          AND P.COD_IMS_IV IN (SELECT C4.IMS_IV
                                  FROM CAT_PRODUCTO_IV C4,
                                       CAT_PRODUCTO_III C3,
                                       CAT_PRODUCTO_II C2,
                                       CAT_PRODUCTO_I C1
                                  WHERE C3.COD_CAT_III = C4.COD_CAT_III
                                    AND C2.COD_CAT_II = C3.COD_CAT_II
                                    AND C1.COD_CAT_I = C2.COD_CAT_I
                                    AND (C4.DES_CAT_IV LIKE '%LECHE%'  -- LECHES
                                        OR
                                        C4.DES_CAT_IV LIKE '%PAÑA%' -- PAÑALES
                                        OR
                                        C4.IND_FARMA = 'S'        -- FARMA
                                        ))--JCORTEZ 10/06/08
      ;

-- 2010-06-23 JOLIVA: SE CARGA PRODUCTOS DE LA LISTA 16 (PRODUCTOS FARMA)
      DELETE CON_PROD_LISTA
      WHERE COD_LISTA = v_cCodLista_16;

      INSERT INTO CON_PROD_LISTA
      (COD_LISTA,COD_GRUPO_CIA,COD_PROD,USU_CREA_PROD_LISTA,PREC_VTA)
      SELECT v_cCodLista_16, P.COD_GRUPO_CIA, P.COD_PROD, v_cIdUsu_16,
            NULL PREC
      FROM LGT_PROD P
      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
          AND P.IND_PRECIO_CONTROL = 'N'
          AND P.EST_PROD = 'A'
          AND P.COD_GRUPO_REP_EDMUNDO = '001' -- SOLO MEDICAMENTOS
      ;

      COMMIT;

    EXCEPTION
      WHEN OTHERS THEN
      ROLLBACK;
        --DESCRIPCION DE LOCAL
        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
            INTO v_vDescLocal
          FROM PBL_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in;

        FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                              ReceiverAddress,
                              'ERROR EN EL CALCULO DE PRECIO VENTA POR CONVENIO MF',
                              'ERROR AL CALCULAR PRECIOS CONVENIO MF',
                              'ERROR EN EL PROCESO PARA LA FECHA: '||sysdate||'</B>'||
                                  '<BR> <I>ERROR: </I> <BR>'||SQLERRM||'<B>',
                              CCReceiverAddress,
                              FARMA_EMAIL.GET_EMAIL_SERVER,
                              true);
     ----para job de delivery arequipa 2
     update_prec_vta_list_prod_2(cCodGrupoCia_in,cCodLocal_in);

  END;
  /***************************************************************************/
  --12/06/2008  ERIOS     DEPRECATED
  PROCEDURE UPDATE_PROD_ZAN_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  AS
    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_VIAJERO;
    CCReceiverAddress VARCHAR2(120) := NULL;

    v_vDescLocal VARCHAR2(120);
    v_cValidacion  NUMBER;
  BEGIN
    SELECT to_number(DESC_CORTA,'0.999999999') INTO v_cValidacion
    FROM   PBL_TAB_GRAL
    WHERE  ID_TAB_GRAL = '209';
    DBMS_OUTPUT.PUT_LINE('PORC:'||v_cValidacion);

    --Se actualiza la tabla
    DELETE FROM LGT_PROD_ZAN_LOCAL;

    INSERT INTO LGT_PROD_ZAN_LOCAL(COD_GRUPO_CIA,
    COD_PROD,
    VAL_BONO_VIG,
    IND_PROD_PROPIO,
    VAL_PREC_VTA_S_IGV,
    VAL_PREC_PROM,
    IND_ZAN,
    USU_CREA)
    SELECT PROD.COD_GRUPO_CIA,
           PROD.COD_PROD,
           PROD.VAL_BONO_VIG,
           PROD.IND_PROD_PROPIO,
           (PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL) / (1 + I.PORC_IGV / 100),
           PROD.VAL_PREC_PROM,
           CASE WHEN PROD.VAL_BONO_VIG > 0 THEN 'GG'
                WHEN PROD.IND_PROD_PROPIO = 'S' THEN 'G'
                WHEN PROD_LOCAL.VAL_PREC_VTA > 0 AND
                         PROD.VAL_PREC_PROM > 0.01 AND
                         ((((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL) / (1 + I.PORC_IGV / 100)) - (PROD.VAL_PREC_PROM)) / ((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL) / (1 + I.PORC_IGV / 100))) > v_cValidacion
                         THEN 'G'
                ELSE ' '
           END AS IND_ZAN,
           'PCK_ZAN_LOCAL'
    FROM LGT_PROD PROD,
         LGT_PROD_LOCAL PROD_LOCAL,
         PBL_IGV I
    WHERE PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND PROD_LOCAL.COD_LOCAL = cCodLocal_in
          AND PROD.EST_PROD = 'A'
          AND PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
          AND PROD_LOCAL.COD_PROD = PROD.COD_PROD
          AND PROD.COD_IGV = I.COD_IGV
          ;

    --COMMIT;

    --Actualiza prod_local
    UPDATE LGT_PROD_LOCAL
    SET IND_ZAN = NULL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    UPDATE LGT_PROD_LOCAL PL
    SET IND_ZAN = (SELECT IND_ZAN FROM LGT_PROD_ZAN_LOCAL
                   WHERE COD_GRUPO_CIA = PL.COD_GRUPO_CIA
                         AND COD_PROD = PL.COD_PROD)
    WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND PL.COD_LOCAL = cCodLocal_in;

    --Se actualiza el historico
    DELETE FROM LGT_HIST_ZAN_LOCAL
    WHERE FEC_DIA_VTA = TRUNC(SYSDATE);

    INSERT INTO LGT_HIST_ZAN_LOCAL(COD_GRUPO_CIA,
    COD_PROD,
    FEC_DIA_VTA,
    VAL_BONO_VIG,
    IND_PROD_PROPIO,
    VAL_PREC_VTA_S_IGV,
    VAL_PREC_PROM,
    IND_ZAN,
    USU_CREA)
    SELECT COD_GRUPO_CIA,
    COD_PROD,
    TRUNC(SYSDATE),
    VAL_BONO_VIG,
    IND_PROD_PROPIO,
    VAL_PREC_VTA_S_IGV,
    VAL_PREC_PROM,
    IND_ZAN,
    'PCK_H_ZAN_LOCAL'
    FROM LGT_PROD_ZAN_LOCAL;

    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
    ROLLBACK;
      --DESCRIPCION DE LOCAL
      SELECT COD_LOCAL ||' - '|| DESC_LOCAL
          INTO v_vDescLocal
        FROM PBL_LOCAL
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in;

      FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                            ReceiverAddress,
                            'ERROR ACTUALIZAR LA INFORMACION ZAN_LOCAL',
                            'ERROR',
                            'ERROR EN EL PROCESO PARA LA FECHA: '||sysdate||'</B>'||
                                '<BR> <I>ERROR: </I> <BR>'||SQLERRM||'<B>',
                            CCReceiverAddress,
                            FARMA_EMAIL.GET_EMAIL_SERVER,
                            true);
  END;
  /***************************************************************************/
   PROCEDURE UPDATE_rep_prod_sobrestock IS
   BEGIN
    delete rep_prod_sobrestock;
    insert into rep_prod_sobrestock
    select  l.cod_local,l.cod_prod,p.desc_prod,p.desc_unid_present,lab.nom_lab,
            rep.desc_grupo_rep,NVL(grl.num_min_dias_rep,loc.num_min_dias_rep) min_dias_rep,
            trunc(l.cant_stk/l.val_frac_prod_local,3) stock,
            p.val_prec_prom ,
            trunc(l.cant_stk/l.val_frac_prod_local*p.val_prec_prom,3) valorizado,
            (select nvl(sum(vpl.cant_unid_vta),0)
             from   VTA_RES_VTA_PROD_LOCAL vpl
             where  vpl.cod_local=l.cod_local
             and    vpl.cod_prod =l.cod_prod
             and    trunc(vpl.fec_dia_vta,'MM')=trunc(add_months(sysdate,-3),'MM')) uni_vend_mes_3,
            (select nvl(sum(vpl.cant_unid_vta),0)
             from   VTA_RES_VTA_PROD_LOCAL vpl
             where  vpl.cod_local=l.cod_local
             and    vpl.cod_prod =l.cod_prod
             and    trunc(vpl.fec_dia_vta,'MM')=trunc(add_months(sysdate,-2),'MM')) uni_vend_mes_2,
            (select nvl(sum(vpl.cant_unid_vta),0)
             from   VTA_RES_VTA_PROD_LOCAL vpl
             where  vpl.cod_local=l.cod_local
             and    vpl.cod_prod =l.cod_prod
             and    trunc(vpl.fec_dia_vta,'MM')=trunc(add_months(sysdate,-1),'MM')) uni_vend_mes_1,
            trunc((select NVL(sum(vpl.cant_unid_vta),0)
             from   VTA_RES_VTA_PROD_LOCAL vpl
             where  vpl.cod_local=l.cod_local
             and    vpl.cod_prod =l.cod_prod
             and    trunc(vpl.fec_dia_vta,'MM')=trunc(add_months(sysdate,-3),'MM'))/nvl(rot_3.dias,1),4) rotacion_3m,
            trunc((select NVL(sum(vpl.cant_unid_vta),0)
             from   VTA_RES_VTA_PROD_LOCAL vpl
             where  vpl.cod_local=l.cod_local
             and    vpl.cod_prod =l.cod_prod
             and    trunc(vpl.fec_dia_vta,'MM')=trunc(add_months(sysdate,-2),'MM'))/nvl(rot_2.dias,1),4) rotacion_2m,
            trunc((select NVL(sum(vpl.cant_unid_vta),0)
             from   VTA_RES_VTA_PROD_LOCAL vpl
             where  vpl.cod_local=l.cod_local
             and    vpl.cod_prod =l.cod_prod
             and    trunc(vpl.fec_dia_vta,'MM')=trunc(add_months(sysdate,-1),'MM'))/nvl(rot_1.dias,1),4) rotacion_3m,
             trunc(30*(select ll.cant_stk/ll.val_frac_prod_local
              from lgt_hist_stk_local ll
              where ll.cod_local=l.cod_local
              and   ll.cod_prod =l.cod_prod
              and   ll.fec_stk=trunc(add_months(sysdate,-2),'MM')-1)/
              (select decode(NVL(sum(vpl.cant_unid_vta),0),0,-0000000.1,sum(vpl.cant_unid_vta))
             from   VTA_RES_VTA_PROD_LOCAL vpl
             where  vpl.cod_local=l.cod_local
             and    vpl.cod_prod =l.cod_prod
             and    trunc(vpl.fec_dia_vta,'MM')=trunc(add_months(sysdate,-3),'MM'))) dias_inv_3,
             trunc(30*(select ll.cant_stk/ll.val_frac_prod_local
              from lgt_hist_stk_local ll
              where ll.cod_local=l.cod_local
              and   ll.cod_prod =l.cod_prod
              and   ll.fec_stk=trunc(add_months(sysdate,-1),'MM')-1)/
              (select decode(NVL(sum(vpl.cant_unid_vta),0),0,-0000000.1,sum(vpl.cant_unid_vta))
             from   VTA_RES_VTA_PROD_LOCAL vpl
             where  vpl.cod_local=l.cod_local
             and    vpl.cod_prod =l.cod_prod
             and    trunc(vpl.fec_dia_vta,'MM')=trunc(add_months(sysdate,-2),'MM'))) dias_inv_2,
             trunc(30*(select ll.cant_stk/ll.val_frac_prod_local
              from lgt_hist_stk_local ll
              where ll.cod_local=l.cod_local
              and   ll.cod_prod =l.cod_prod
              and   ll.fec_stk=trunc(add_months(sysdate,-0),'MM')-1)/
              (select decode(NVL(sum(vpl.cant_unid_vta),0),0,-0000000.1,sum(vpl.cant_unid_vta))
             from   VTA_RES_VTA_PROD_LOCAL vpl
             where  vpl.cod_local=l.cod_local
             and    vpl.cod_prod =l.cod_prod
             and    trunc(vpl.fec_dia_vta,'MM')=trunc(add_months(sysdate,-1),'MM'))) dias_inv_1,
             sysdate fecha_act_vm
    from    lgt_hist_stk_local  l,
            lgt_prod            p,
            lgt_lab             lab,
            lgt_grupo_rep       rep,
            lgt_grupo_rep_local grl,
            pbl_local           loc,
            (select cod_prod,count(1) dias
              from
                  ( select  rpl.cod_prod,rpl.fec_dia_vta
                    from    VTA_RES_VTA_PROD_LOCAL rpl
                    where   trunc(rpl.fec_dia_vta,'MM') = trunc(add_months(sysdate,-3),'MM')
                    union
                    select  hl.cod_prod,hl.fec_stk
                    from    lgt_hist_stk_local hl
                    where   hl.cant_stk>0
                    and    trunc(hl.fec_stk,'MM')       = trunc(add_months(sysdate,-3),'MM')
              )
              group by cod_prod
              ) rot_3,
            (select cod_prod,count(1) dias
              from
                  ( select  rpl.cod_prod,rpl.fec_dia_vta
                    from    VTA_RES_VTA_PROD_LOCAL rpl
                    where   trunc(rpl.fec_dia_vta,'MM') = trunc(add_months(sysdate,-2),'MM')
                    union
                    select  hl.cod_prod,hl.fec_stk
                    from    lgt_hist_stk_local hl
                    where   hl.cant_stk>0
                    and    trunc(hl.fec_stk,'MM')       = trunc(add_months(sysdate,-2),'MM')
              )
              group by cod_prod
              ) rot_2,
            (select cod_prod,count(1) dias
              from
                  ( select  rpl.cod_prod,rpl.fec_dia_vta
                    from    VTA_RES_VTA_PROD_LOCAL rpl
                    where   trunc(rpl.fec_dia_vta,'MM') = trunc(add_months(sysdate,-1),'MM')
                    union
                    select  hl.cod_prod,hl.fec_stk
                    from    lgt_hist_stk_local hl
                    where   hl.cant_stk>0
                    and    trunc(hl.fec_stk,'MM')       = trunc(add_months(sysdate,-1),'MM')
              )
              group by cod_prod
              ) rot_1
    where   l.cod_prod=p.cod_prod
    and     l.fec_stk=trunc(sysdate-1)
    and     lab.cod_lab=p.cod_lab
    and     rep.cod_grupo_rep=p.cod_grupo_rep
    and     grl.cod_local=l.cod_local
    and     grl.cod_grupo_rep=p.cod_grupo_rep
    and     loc.cod_local=l.cod_local
    and     rot_3.cod_prod(+)=l.cod_prod
    and     rot_2.cod_prod(+)=l.cod_prod
    and     rot_1.cod_prod(+)=l.cod_prod;
    END;
  /***************************************************************************/
  PROCEDURE P_INACTIVA_CAMP_PRIMERA_COMPRA(
                                           cCodGrupoCia_in 	   IN CHAR,
                				    	             cCodLocal_in    	   IN CHAR,
                					                 cNumPedVta_in   	   IN CHAR
                                          )
  IS
  vDni VARCHAR2(10);
  vCodCampLimitar	CHAR(6):= 'A0006';
  nCantTarjetas number :=0;
  nUsoCampana number   :=0;
  mesg_body VARCHAR2(4000);
  BEGIN

     begin
     SELECT T.DNI_CLI
     into   vDni
     FROM   FID_TARJETA_PEDIDO T
     WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
     AND    T.COD_LOCAL = cCodLocal_in
     AND    T.NUM_PEDIDO = cNumPedVta_in;
     exception
     when no_data_found then
     vDni := 'N';
     end;


     if vDni != 'N' then
     dbms_output.put_line('hay DNI');
     SELECT COUNT(1)
     into   nCantTarjetas
     FROM   VTA_CAMP_X_TARJETA CT,
            FID_TARJETA T
     WHERE  T.DNI_CLI =  vDni
     AND    CT.COD_CAMP_CUPON = vCodCampLimitar
     AND    T.COD_TARJETA BETWEEN CT.TARJETA_INI AND  CT.TARJETA_FIN;

     if nCantTarjetas > 0 then
          dbms_output.put_line('tiene tarjetas');
         SELECT count(1)
         into   nUsoCampana
         FROM   VTA_PEDIDO_VTA_CAB C,
                VTA_PEDIDO_VTA_DET D,
                FID_TARJETA_PEDIDO T
         WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    C.COD_LOCAL     = cCodLocal_in
         AND    C.NUM_PED_VTA   = cNumPedVta_in
         --AND    C.EST_PED_VTA = 'C' Aun no se cobra
         AND    D.COD_CAMP_CUPON = vCodCampLimitar --CUIDADO CHAR(6)  CON CHAR(5)
         AND    C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
         AND    C.COD_LOCAL = D.COD_LOCAL
         AND    C.NUM_PED_VTA = D.NUM_PED_VTA
         AND    C.COD_GRUPO_CIA = T.COD_GRUPO_CIA
         AND    C.COD_LOCAL = T.COD_LOCAL
         AND    C.NUM_PED_VTA = T.NUM_PEDIDO;

           if nUsoCampana > 0 then
                     dbms_output.put_line('uso y elimina');
           delete VTA_CAMP_X_TARJETA q
           where  q.cod_grupo_cia  = '001'
           and    q.cod_camp_cupon =  vCodCampLimitar
           and    q.tarjeta_ini in (
                                    SELECT ct.tarjeta_ini
                                    FROM   VTA_CAMP_X_TARJETA CT,
                                           FID_TARJETA T
                                    WHERE  T.DNI_CLI =  vDni
                                    AND    CT.COD_CAMP_CUPON = vCodCampLimitar
                                    AND    T.COD_TARJETA BETWEEN CT.TARJETA_INI AND  CT.TARJETA_FIN
                                    );
           mesg_body := 'Exito inact. Gold ' || cNumPedVta_in || '<bt>DNI:'||vDni;
           FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                      cCodLocal_in,
                                      'dubilluz@mifarma.com.pe,joliva',
                                      'Exito inactivar GOld',
                                      'Exito',
                                      mesg_body,
                                      '');


          end if;
        end if;
     end if;
      EXCEPTION
    WHEN OTHERS THEN
     mesg_body := 'ERROR AL INACTIVAR CAMPANA GOLD:' || cNumPedVta_in || '. ' || SQLERRM;
     FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                cCodLocal_in,
                                'dubilluz@mifarma.com.pe,joliva',
                                'ERROR INACTIVAR GOLD',
                                'ERROR',
                                mesg_body,
                                '');
  END;

  /* ************************************************************************* */
/******************************************************************************/
  PROCEDURE UPDATE_PREC_VTA_LIST_PROD_2(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  AS
      v_cCodLista CON_LISTA.COD_LISTA%TYPE := '00015';
      v_cIdUsu VARCHAR2(15) := 'JOB_ACT_PPLISTA';
      v_nValDscto NUMBER := 10;

      ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_VIAJERO;
      CCReceiverAddress VARCHAR2(120) := NULL;

      v_vDescLocal VARCHAR2(120);
  BEGIN

      DELETE CON_PROD_LISTA
      WHERE COD_LISTA = v_cCodLista;

      INSERT INTO CON_PROD_LISTA(COD_LISTA,COD_GRUPO_CIA,COD_PROD,USU_CREA_PROD_LISTA,PREC_VTA)
           SELECT v_cCodLista,P.COD_GRUPO_CIA,P.COD_PROD,v_cIdUsu,
           ROUND(CASE WHEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100)) > (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)*(1-(v_nValDscto/100))
                      THEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100))
                ELSE (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)*(1-(v_nValDscto/100))
           END ,3)AS PREC
      FROM LGT_PROD P,
           LGT_PROD_LOCAL L,
           PBL_IGV I
      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
          AND L.COD_LOCAL = cCodLocal_in
          AND P.IND_PRECIO_CONTROL = 'N'
          AND P.EST_PROD = 'A'
          AND P.COD_IGV = I.COD_IGV
          AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND P.COD_PROD = L.COD_PROD
          AND P.COD_IMS_IV IN (SELECT C4.IMS_IV
                                  FROM CAT_PRODUCTO_IV C4,
                                       CAT_PRODUCTO_III C3,
                                       CAT_PRODUCTO_II C2,
                                       CAT_PRODUCTO_I C1
                                  WHERE C3.COD_CAT_III = C4.COD_CAT_III
                                    AND C2.COD_CAT_II = C3.COD_CAT_II
                                    AND C1.COD_CAT_I = C2.COD_CAT_I
                                    AND (
/*
                                        C4.DES_CAT_IV LIKE '%LECHE%'  -- LECHES
                                        OR
                                        C4.DES_CAT_IV LIKE '%PAÑA%' -- PAÑALES
                                        OR
*/
                                        C4.IND_FARMA = 'S'        -- FARMA
                                        ))--JCORTEZ 10/06/08
      ;

      COMMIT;

    EXCEPTION
      WHEN OTHERS THEN
      ROLLBACK;
        --DESCRIPCION DE LOCAL
        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
            INTO v_vDescLocal
          FROM PBL_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in;

        FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                              ReceiverAddress,
                              'ERROR EN EL CALCULO DE PRECIO VENTA POR CONVENIO DELIVERY 10%',
                              'ERROR AL CALCULAR PRECIOS CONVENIO MF',
                              'ERROR EN EL PROCESO PARA LA FECHA: '||sysdate||'</B>'||
                                  '<BR> <I>ERROR: </I> <BR>'||SQLERRM||'<B>',
                              CCReceiverAddress,
                              FARMA_EMAIL.GET_EMAIL_SERVER,
                              true);
  END;
  /***************************************************************************/

    PROCEDURE PTOVTA_P_INS_DSCTO_AUX_BKP(cCodGrupoCia_in 	   IN CHAR)
  AS
  BEGIN
     INSERT INTO VTA_PED_DCTO_CLI_BKP(COD_GRUPO_CIA,
                                      COD_LOCAL,
                                      NUM_PED_VTA,
                                      VAL_DCTO_VTA,
                                      DNI_CLIENTE,
                                      FEC_CREA_PED_VTA_CAB)
     SELECT COD_GRUPO_CIA,
            COD_LOCAL,
            NUM_PED_VTA,
            VAL_DCTO_VTA,
            DNI_CLIENTE,
            FEC_CREA_PED_VTA_CAB FROM VTA_PED_DCTO_CLI_AUX;

     DELETE FROM VTA_PED_DCTO_CLI_BKP A
     WHERE A.FEC_CREA_PED_VTA_CAB < (SYSDATE - 7);
     COMMIT;
  END;

/******************************************************************************/
  PROCEDURE UPDATE_PREC_VTA_LIST_PROD_19(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  AS
      v_cCodLista CON_LISTA.COD_LISTA%TYPE := '00019';
      v_cIdUsu VARCHAR2(15) := 'JOB_ACT_PPLISTA';
      v_nValDscto NUMBER := 10;
      ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_VIAJERO;
      CCReceiverAddress VARCHAR2(120) := NULL;

      v_vDescLocal VARCHAR2(120);
      v_cIdUsu_16 VARCHAR2(15) := 'JOB_ACT_LIST_16';

  BEGIN

      DELETE CON_PROD_LISTA
      WHERE COD_LISTA = v_cCodLista;

     INSERT INTO CON_PROD_LISTA(COD_LISTA,COD_GRUPO_CIA,COD_PROD,USU_CREA_PROD_LISTA,PREC_VTA)
           SELECT v_cCodLista,P.COD_GRUPO_CIA,P.COD_PROD,v_cIdUsu,
           ROUND(CASE WHEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100)) >= (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL) --ASOSA, 15.12.2010-primer case
                      THEN L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL
                 WHEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100)) > (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)*(1-(v_nValDscto/100))
                      THEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100))
                ELSE (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)*(1-(v_nValDscto/100))
           END ,3)AS PREC
      FROM LGT_PROD P,
           LGT_PROD_LOCAL L,
           PBL_IGV I
      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
          --JQUISPE 16.08.2010 CAMBIO COD GRUPO CIA
          --AND I.COD_GRUPO_CIA=P.COD_GRUPO_CIA
          AND L.COD_LOCAL = (SELECT DISTINCT(COD_LOCAL) FROM VTA_IMPR_IP)
          AND P.IND_PRECIO_CONTROL = 'N'
          AND P.EST_PROD = 'A'
          AND P.COD_IGV = I.COD_IGV
          AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND P.COD_PROD = L.COD_PROD
          AND p.cod_grupo_rep_edmundo NOT IN ('002','004','005')
          AND p.est_prod = 'A'
      ;

      COMMIT;

    EXCEPTION
      WHEN OTHERS THEN
      ROLLBACK;
        --DESCRIPCION DE LOCAL
        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
            INTO v_vDescLocal
          FROM PBL_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in;

        FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                              ReceiverAddress,
                              'ERROR EN EL CALCULO DE PRECIO VENTA POR NUEVO CONVENIO MF',
                              'ERROR AL CALCULAR PRECIOS NUEVO CONVENIO MF',
                              'ERROR EN EL PROCESO PARA LA FECHA: '||sysdate||'</B>'||
                                  '<BR> <I>ERROR: </I> <BR>'||SQLERRM||'<B>',
                              CCReceiverAddress,
                              FARMA_EMAIL.GET_EMAIL_SERVER,
                              TRUE);


  END;

  PROCEDURE UPDATE_PREC_VTA_LIST_PROD_21(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  AS
      v_cCodLista CON_LISTA.COD_LISTA%TYPE := '00021';
      v_cIdUsu VARCHAR2(15) := 'JOB_ACT_LIST_21';
      v_nValDscto NUMBER := 10;
      ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_VIAJERO;
      CCReceiverAddress VARCHAR2(120) := NULL;

      v_vDescLocal VARCHAR2(120);
      v_cIdUsu_16 VARCHAR2(15) := 'JOB_ACT_LIST_16';

  BEGIN

      DELETE CON_PROD_LISTA
      WHERE COD_LISTA = v_cCodLista;

     INSERT INTO CON_PROD_LISTA(COD_LISTA,COD_GRUPO_CIA,COD_PROD,USU_CREA_PROD_LISTA,PREC_VTA)
           SELECT v_cCodLista,P.COD_GRUPO_CIA,P.COD_PROD,v_cIdUsu,
           ROUND(CASE WHEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100)) >= (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL) --ASOSA, 15.12.2010-primer case
                      THEN L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL
                 WHEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100)) > (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)*(1-(v_nValDscto/100))
                      THEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100))
                ELSE (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)*(1-(v_nValDscto/100))
           END ,3)AS PREC
      FROM LGT_PROD P,
           LGT_PROD_LOCAL L,
           PBL_IGV I
      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in

          --AND I.COD_GRUPO_CIA=P.COD_GRUPO_CIA
          AND L.COD_LOCAL = (SELECT DISTINCT(COD_LOCAL) FROM VTA_IMPR_IP)
          AND P.IND_PRECIO_CONTROL = 'N'
          AND P.EST_PROD = 'A'
          AND P.COD_IGV = I.COD_IGV
          AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND P.COD_PROD = L.COD_PROD
          AND p.cod_grupo_rep_edmundo  IN ('001')
          AND p.est_prod = 'A'
      ;

      COMMIT;

    EXCEPTION
      WHEN OTHERS THEN
      ROLLBACK;
        --DESCRIPCION DE LOCAL
        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
            INTO v_vDescLocal
          FROM PBL_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in;

        FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                              ReceiverAddress,
                              'ERROR EN EL CALCULO DE PRECIO VENTA POR NUEVO CONVENIO MF',
                              'ERROR AL CALCULAR PRECIOS NUEVO CONVENIO MF',
                              'ERROR EN EL PROCESO PARA LA FECHA: '||sysdate||'</B>'||
                                  '<BR> <I>ERROR: </I> <BR>'||SQLERRM||'<B>',
                              CCReceiverAddress,
                              FARMA_EMAIL.GET_EMAIL_SERVER,
                              TRUE);


  END;


  PROCEDURE UPDATE_PREC_VTA_LIST_PROD_22(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  AS
      v_cCodLista CON_LISTA.COD_LISTA%TYPE := '00022';
      v_cIdUsu VARCHAR2(15) := 'JOB_ACT_LIST_22';
      v_nValDscto NUMBER := 20;
      ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_VIAJERO;
      CCReceiverAddress VARCHAR2(120) := NULL;

      v_vDescLocal VARCHAR2(120);
      v_cIdUsu_16 VARCHAR2(15) := 'JOB_ACT_LIST_16';

  BEGIN

      DELETE CON_PROD_LISTA
      WHERE COD_LISTA = v_cCodLista;

     INSERT INTO CON_PROD_LISTA(COD_LISTA,COD_GRUPO_CIA,COD_PROD,USU_CREA_PROD_LISTA,PREC_VTA)
           SELECT v_cCodLista,P.COD_GRUPO_CIA,P.COD_PROD,v_cIdUsu,
-- 2012-12-04 JOLIVA: a solicitud de Patricia Salazar, para Vitaminas el descuento es del 20% fijo
/*
           ROUND(CASE WHEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100)) >= (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL) --ASOSA, 15.12.2010-primer case
                      THEN L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL
                 WHEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100)) > (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)*(1-(v_nValDscto/100))
                      THEN P.VAL_PREC_PROM*DECODE(TRIM(P.COD_IGV),'00',1,(1+I.PORC_IGV/100))
                ELSE (L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)*(1-(v_nValDscto/100))
           END ,3)AS PREC
*/
           ROUND((L.VAL_PREC_VTA*L.VAL_FRAC_LOCAL)*(1-(v_nValDscto/100)),3) AS PREC

      FROM LGT_PROD P,
           LGT_PROD_LOCAL L,
           PBL_IGV I
      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in

          --AND I.COD_GRUPO_CIA=P.COD_GRUPO_CIA
          AND L.COD_LOCAL = (SELECT DISTINCT(COD_LOCAL) FROM VTA_IMPR_IP)
          AND P.IND_PRECIO_CONTROL = 'N'
          AND P.EST_PROD = 'A'
          AND P.COD_IGV = I.COD_IGV
          AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND P.COD_PROD = L.COD_PROD
          AND p.cod_lab  IN ('173','00706','135','480','00733')
          AND p.est_prod = 'A'
      ;

      COMMIT;

    EXCEPTION
      WHEN OTHERS THEN
      ROLLBACK;
        --DESCRIPCION DE LOCAL
        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
            INTO v_vDescLocal
          FROM PBL_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in;

        FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                              ReceiverAddress,
                              'ERROR EN EL CALCULO DE PRECIO VENTA POR NUEVO CONVENIO MF',
                              'ERROR AL CALCULAR PRECIOS NUEVO CONVENIO MF',
                              'ERROR EN EL PROCESO PARA LA FECHA: '||sysdate||'</B>'||
                                  '<BR> <I>ERROR: </I> <BR>'||SQLERRM||'<B>',
                              CCReceiverAddress,
                              FARMA_EMAIL.GET_EMAIL_SERVER,
                              TRUE);


  END;

-- ***********************************************************************************
-- 2014-09-23 JOLIVA: SE AGREGA CALCULO DE VENTA PERDIDA SOLICITADO POR FCHAU

    PROCEDURE CARGA_VTA_PERDIDA_LOCAL_DIA (cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, dFecVtaPerdida_in IN DATE DEFAULT TRUNC(SYSDATE-1)) IS
    BEGIN

                    DELETE FROM VTA_PERDIDA_LOCAL_DIA WHERE COD_LOCAL = cCodLocal_in AND FCH_VTA_PERDIDA = dFecVtaPerdida_in;

                    INSERT INTO VTA_PERDIDA_LOCAL_DIA (COD_LOCAL, COD_PROD, FCH_VTA_PERDIDA, VAL_PREC, IND_REPONER, PRODUCTO, PRESENT, ESTADO, G_R, G_R_EY, VTA_PERDIDA, PVM, VTA_PERDIDA_SP, PVM_SP, FCH_ACTUALIZADO)
                    SELECT
                           M.COD_LOCAL,
                           M.COD_PROD,
                           dFecVtaPerdida_in FCH_VTA_PERDIDA,
                           N.VAL_FRAC_LOCAL * N.VAL_PREC_VTA VAL_PREC ,
                           N.IND_REPONER,
                           O.DESC_PROD PRODUCTO,
                           O.DESC_UNID_PRESENT PRESENT,
                           O.IND_TIPO_PROD ESTADO,
                           P.DESC_GRUPO_REP G_R,
                           Q.DESC_GRUPO_REP_EDMUNDO G_R_EY,
                           M.VTA_DIA * N.VAL_FRAC_LOCAL * N.VAL_PREC_VTA VTA_PERDIDA,
                           M.PVM,
                           M.VTA_SP_DIA * N.VAL_FRAC_LOCAL * N.VAL_PREC_VTA VTA_PERDIDA_SP,
                           PVM_SP,
                           SYSDATE FCH_ACTUALIZADO
                      FROM
                           (SELECT C.COD_LOCAL,
                                   C.COD_PROD,
                                   SUM(C.VTA) / (4 * 30) VTA_DIA,
                                   MAX(C.VTA) PVM,
                                   SUM(C.VTA_SP) / (4 * 30) VTA_SP_DIA,
                                   MAX(C.VTA_SP) PVM_SP
                              FROM
                                   (
                                   SELECT COD_LOCAL, COD_PROD, MES, VTA, VTA_SP
                                      FROM (
                                           SELECT COD_LOCAL,
                                                   COD_PROD,
                                                   TRUNC(FEC_DIA_VTA, 'MM') MES,
                                                   SUM(CANT_UNID_VTA) VTA,
                                                   SUM(CANT_UNID_VTA_SIN_PROMO) VTA_SP
                                              FROM PTOVENTA.VTA_RES_HIST_PROD_LOCAL  A
                                             WHERE 1 = 1
                                               AND COD_GRUPO_CIA = cCodGrupoCia_in
                                               AND A.COD_LOCAL = cCodLocal_in
                                               AND FEC_DIA_VTA BETWEEN
                                                   ADD_MONTHS(TRUNC(dFecVtaPerdida_in, 'MM'), -3) AND
                                                   ADD_MONTHS(TRUNC(dFecVtaPerdida_in, 'MM'), 0)
                                               AND FEC_DIA_VTA < dFecVtaPerdida_in
                                               AND NOT EXISTS
                                                   (SELECT 1
                                                            FROM PTOVENTA.LGT_HIST_STK_LOCAL  B
                                                           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                                             AND COD_LOCAL = cCodLocal_in
                                                             AND B.COD_PROD = A.COD_PROD
                                                             AND FEC_STK = dFecVtaPerdida_in
                                                   )
                                             GROUP BY COD_LOCAL,
                                                      COD_PROD,
                                                      TRUNC(FEC_DIA_VTA, 'MM')
                                          )
                                   ) C
                             GROUP BY C.COD_LOCAL, C.COD_PROD
                           ) M,
                           PTOVENTA.LGT_PROD_LOCAL  N,
                           PTOVENTA.LGT_PROD  O,
                           PTOVENTA.LGT_GRUPO_REP P,
                           PTOVENTA.LGT_GRUPO_REP_EDMUNDO Q
                     WHERE N.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND M.COD_LOCAL = N.COD_LOCAL
                       AND M.COD_PROD = N.COD_PROD
                       AND O.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND M.COD_PROD = O.COD_PROD
                       AND O.COD_GRUPO_REP = P.COD_GRUPO_REP
                       AND O.COD_GRUPO_REP_EDMUNDO = Q.COD_GRUPO_REP_EDMUNDO
                     ORDER BY M.COD_PROD;

                    COMMIT;

    END;


end;
/

