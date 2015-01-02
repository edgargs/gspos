CREATE OR REPLACE PACKAGE PTOVENTA."FARMA_ALERTUP" AS

  TYPE FarmaCursor IS REF CURSOR;

   FUNCTION F_CUR_ALERTA_MENSAJES(cCodGrupoCia   IN CHAR,
                                  cCodLocal       IN CHAR)
   RETURN FarmaCursor;

END FARMA_ALERTUP;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."FARMA_ALERTUP" IS

   FUNCTION F_CUR_ALERTA_MENSAJES(cCodGrupoCia   IN CHAR,
                                  cCodLocal       IN CHAR)
   RETURN FarmaCursor
   IS
     V_RCURFP FarmaCursor;
     nCtdPed_DLV number;
     nCtdPed_MAY number;

     nCtdPed_DLV_OLD number;
     nCtdPed_MAY_OLD number;

     nCtdTrans_Env number;
     nCtdTrans_Env_OLD number;

     nCtdTrans_Afectar number;
     nCtdTrans_Afectar_OLD number;

     pPermite number:=0;
     BEGIN

        SELECT COUNT(1)
        INTO   pPermite
        FROM   PBL_IP_LOCAL_ALERT_UP A
        WHERE  A.COD_GRUPO_CIA = cCodGrupoCia
        AND    A.COD_LOCAL = cCodLocal
        AND    A.IP = (SELECT SYS_CONTEXT('USERENV','IP_ADDRESS') FROM dual);

        if pPermite  > 0 then

        ---------------------------------------------------------------------------------------
        /* ********************************************************************************** */
        ---------------------------------------------------------------------------------------
        SELECT count(1)
         into  nCtdPed_DLV
          FROM TMP_VTA_PEDIDO_VTA_CAB TMP_CAB,
               PBL_LOCAL              LOC
         WHERE TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia
           AND TMP_CAB.Cod_Local_Procedencia = '999'
           AND TMP_CAB.COD_LOCAL_ATENCION = cCodLocal
           AND TMP_CAB.EST_PED_VTA = 'P'
           AND TMP_CAB.NUM_PED_VTA_ORIGEN IS NULL
           AND TMP_CAB.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
           AND TMP_CAB.COD_LOCAL = LOC.COD_LOCAL
           AND TMP_CAB.FEC_CREA_PED_VTA_CAB between sysdate-5/24/60 +1/24/60/60 and sysdate;

        SELECT count(1)
         into  nCtdPed_MAY
          FROM TMP_VTA_PEDIDO_VTA_CAB TMP_CAB,
               PBL_LOCAL              LOC
         WHERE TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia
           AND TMP_CAB.Cod_Local_Procedencia = '998'
           AND TMP_CAB.COD_LOCAL_ATENCION = cCodLocal
           AND TMP_CAB.EST_PED_VTA = 'P'
           AND TMP_CAB.NUM_PED_VTA_ORIGEN IS NULL
           AND TMP_CAB.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
           AND TMP_CAB.COD_LOCAL = LOC.COD_LOCAL
           AND TMP_CAB.FEC_CREA_PED_VTA_CAB between sysdate-5/24/60 +1/24/60/60 and sysdate;

        ---------------------------------------------------------------------------------------
        /* ********************************************************************************** */
        ---------------------------------------------------------------------------------------

        SELECT count(1)
         into  nCtdPed_DLV_OLD
          FROM TMP_VTA_PEDIDO_VTA_CAB TMP_CAB,
               PBL_LOCAL              LOC
         WHERE TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia
           AND TMP_CAB.Cod_Local_Procedencia = '999'
           AND TMP_CAB.COD_LOCAL_ATENCION = cCodLocal
           AND TMP_CAB.EST_PED_VTA = 'P'
           AND TMP_CAB.NUM_PED_VTA_ORIGEN IS NULL
           AND TMP_CAB.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
           AND TMP_CAB.COD_LOCAL = LOC.COD_LOCAL
           AND TMP_CAB.FEC_CREA_PED_VTA_CAB between sysdate-10 and sysdate -5/24/60 -0.5/24/60/60;

        SELECT count(1)
         into  nCtdPed_MAY_OLD
          FROM TMP_VTA_PEDIDO_VTA_CAB TMP_CAB,
               PBL_LOCAL              LOC
         WHERE TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia
           AND TMP_CAB.Cod_Local_Procedencia = '998'
           AND TMP_CAB.COD_LOCAL_ATENCION = cCodLocal
           AND TMP_CAB.EST_PED_VTA = 'P'
           AND TMP_CAB.NUM_PED_VTA_ORIGEN IS NULL
           AND TMP_CAB.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
           AND TMP_CAB.COD_LOCAL = LOC.COD_LOCAL
           AND TMP_CAB.FEC_CREA_PED_VTA_CAB between sysdate-10  and sysdate -5/24/60 -0.5/24/60/60;

        ---------------------------------------------------------------------------------------
        /* ********************************************************************************** */
        ---------------------------------------------------------------------------------------
        -- TRANSFERENCIAS
              SELECT count(1)
                INTO  nCtdTrans_Env
                FROM TMP_DEL_TRANS_CAB   C,
                     VTA_GRUPO_TRANS_PED G,
                     PBL_LOCAL           L,
                     pbl_local           lo
               WHERE C.COD_GRUPO_CIA = cCodGrupoCia
                 AND C.COD_LOCAL_ORIGEN = cCodLocal
                 AND C.FEC_CREA_TMP_TRANS_CAB between sysdate-5/24/60 +1/24/60/60 and sysdate
                 and c.cod_grupo_cia = lo.cod_grupo_cia
                 and c.cod_local = lo.cod_local
                 AND C.EST_TRANS = 'A'
                 AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                 AND C.COD_LOCAL = G.COD_LOCAL
                 AND C.NUM_PED_VTA = G.NUM_PED_VTA
                 AND C.SEC_GRUPO = G.SEC_GRUPO
                 AND G.EST_GRUPO_TRANS = 'A'
                 AND L.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND L.COD_LOCAL = C.COD_LOCAL_DESTINO;

              SELECT count(1)
                INTO  nCtdTrans_Env_OLD
                FROM TMP_DEL_TRANS_CAB   C,
                     VTA_GRUPO_TRANS_PED G,
                     PBL_LOCAL           L,
                     pbl_local           lo
               WHERE C.COD_GRUPO_CIA = cCodGrupoCia
                 AND C.COD_LOCAL_ORIGEN = cCodLocal
                 AND C.FEC_CREA_TMP_TRANS_CAB between sysdate-10 and sysdate -5/24/60 -0.5/24/60/60
                 and c.cod_grupo_cia = lo.cod_grupo_cia
                 and c.cod_local = lo.cod_local
                 AND C.EST_TRANS = 'A'
                 AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                 AND C.COD_LOCAL = G.COD_LOCAL
                 AND C.NUM_PED_VTA = G.NUM_PED_VTA
                 AND C.SEC_GRUPO = G.SEC_GRUPO
                 AND G.EST_GRUPO_TRANS = 'A'
                 AND L.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND L.COD_LOCAL = C.COD_LOCAL_DESTINO;
        ---------------------------------------------------------------------------------------
        /* ********************************************************************************** */
        ---------------------------------------------------------------------------------------

                SELECT count(distinct c.num_nota_es)
                INTO  nCtdTrans_Afectar
                    FROM T_LGT_NOTA_ES_CAB C, T_LGT_GUIA_REM g
                    WHERE C.COD_GRUPO_CIA = '001'
                          AND C.COD_DESTINO_NOTA_ES = '506'
                          AND C.EST_NOTA_ES_CAB = 'L'
                          AND C.Fec_Crea_Nota_Es_Cab between sysdate-5/24/60 +1/24/60/60 and sysdate
                          AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                          AND C.COD_LOCAL = G.COD_LOCAL
                          AND C.NUM_NOTA_ES = G.NUM_NOTA_ES;

                SELECT count(distinct c.num_nota_es)
                INTO  nCtdTrans_Afectar_old
                    FROM T_LGT_NOTA_ES_CAB C, T_LGT_GUIA_REM g
                    WHERE C.COD_GRUPO_CIA = '001'
                          AND C.COD_DESTINO_NOTA_ES = '506'
                          AND C.EST_NOTA_ES_CAB = 'L'
                          AND C.Fec_Crea_Nota_Es_Cab between sysdate-10 and sysdate -5/24/60 -0.5/24/60/60
                          AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                          AND C.COD_LOCAL = G.COD_LOCAL
                          AND C.NUM_NOTA_ES = G.NUM_NOTA_ES;

        ---------------------------------------------------------------------------------------
        /* ********************************************************************************** */
        ---------------------------------------------------------------------------------------
          nCtdPed_MAY := 0;
          nCtdPed_MAY_OLD := 0;

      else
           nCtdPed_DLV := 0;
           nCtdPed_MAY := 0;

           nCtdPed_DLV_OLD := 0;
           nCtdPed_MAY_OLD := 0;

           nCtdTrans_Env := 0;
           nCtdTrans_Env_OLD := 0;

           nCtdTrans_Afectar := 0;
           nCtdTrans_Afectar_OLD := 0;

      end if;

          -- || 'Ã' ||
          OPEN V_RCURFP
          FOR
          select TITULO || 'Ã' ||
                  MSJ   || 'Ã' ||
                  TIEMPO
          from  (
                         /*
                             nCtdPed_DLV number; 1
                             nCtdPed_MAY number;    2
                             nCtdTrans_Env number;    3
                             nCtdTrans_Afectar number;  4

                             nCtdPed_DLV_OLD number; 5
                             nCtdPed_MAY_OLD number;   6
                             nCtdTrans_Env_OLD number;  7
                             nCtdTrans_Afectar_OLD number; 8
                         */
                         select '¡Alerta Delivery!' TITULO,
                                 Case
                                 when nCtdPed_DLV = 0 then 'N'
                                 when nCtdPed_DLV = 1 then
                                   'Tiene 1 Pedido Delivery Pendiente@Por Favor de Atenderlo.'
                                 else
                                   'Tienes '||to_char(trim(nCtdPed_DLV),'999990') ||' Pedidos Delivery Pendientes@Por Favor de Atenderlos.'
                                 end MSJ ,
                                 '5' TIEMPO,
                                 1 PRIORIDAD
                         from  dual union
                         select '¡Alerta Venta Mayorista!' TITULO,
                                 Case
                                 when nCtdPed_MAY = 0 then 'N'
                                 when nCtdPed_MAY = 1 then
                                   'Tiene 1 Pedido Mayorista Pendiente@Por Favor de Atenderlo.'
                                 else
                                   'Tienes '||to_char(trim(nCtdPed_MAY),'999990') ||' Pedidos Mayorista Pendientes@Por Favor de Atenderlos.'
                                 end MSJ ,
                                 '5' TIEMPO,
                                 2 PRIORIDAD
                         from  dual union
                         select '¡Alerta Delivery!' TITULO,
                                 Case
                                 when nCtdTrans_Env = 0 then 'N'
                                 when nCtdTrans_Env = 1 then
                                   'Tiene 1 Transferencia Pendiente@Por Favor de Atenderlo.'
                                 else
                                   'Tienes '||to_char(trim(nCtdTrans_Env),'999990') ||' Transferencias Pendientes@Por Favor de Atenderlos.'
                                 end MSJ ,
                                 '5' TIEMPO,
                                 3 PRIORIDAD
                         from  dual union
        				 select '¡Alerta Transferencia!' TITULO,
                                 Case
                                 when nCtdTrans_Afectar = 0 then 'N'
                                 when nCtdTrans_Afectar = 1 then
                                   'Tiene 1 Transferencia Pendiente@Por Favor de Afectar.'
                                 else
                                   'Tienes '||to_char(trim(nCtdTrans_Afectar),'999990') ||' Transferencias Pendientes@Por Favor de Afectarlas.'
                                 end MSJ ,
                                 '5' TIEMPO,
                                 4 PRIORIDAD
                         from  dual union
        				 select '¡Alerta Delivery!' TITULO,
                                 Case
                                 when nCtdPed_DLV_OLD = 0 then 'N'
                                 when nCtdPed_DLV_OLD = 1 then
                                   'Tiene mas de 5 minutos,@1 Pedido Delivery Pendiente@Por Favor de Atenderlo.'
                                 else
                                   'Tienes mas de 5 minutos,@'||to_char(trim(nCtdPed_DLV_OLD),'999990') ||' Pedidos Delivery Pendientes@Por Favor de Atenderlos.'
                                 end MSJ ,
                                 '5' TIEMPO,
                                 5 PRIORIDAD
                         from  dual union
                         select '¡Alerta Venta Mayorista!' TITULO,
                                 Case
                                 when nCtdPed_MAY_OLD = 0 then  'N'
                                 when nCtdPed_MAY_OLD = 1 then
                                   'Tiene mas de 5 minutos,@1 Pedido Mayorista Pendiente@Por Favor de Atenderlo.'
                                 else
                                   'Tienes mas de 5 minutos,@'||to_char(trim(nCtdPed_MAY_OLD),'999990') ||' Pedidos Mayorista Pendientes@Por Favor de Atenderlos.'
                                 end MSJ ,
                                 '5' TIEMPO,
                                 6 PRIORIDAD
                         from  dual union
                         select '¡Alerta Delivery!' TITULO,
                                 Case
                                 when nCtdTrans_Env_OLD = 0 then 'N'
                                 when nCtdTrans_Env_OLD = 1 then
                                   'Tiene mas de 5 minutos,@1 Transferencia Pendiente@Por Favor de Atenderlo.'
                                 else
                                   'Tienes mas de 5 minutos,@'||to_char(trim(nCtdTrans_Env_OLD),'999990') ||' Transferencias Pendientes@Por Favor de Atenderlos.'
                                 end MSJ ,
                                 '5' TIEMPO,
                                 7 PRIORIDAD
                         from  dual union
        				 select '¡Alerta Transferencia!' TITULO,
                                 Case
                                 when nCtdTrans_Afectar_OLD = 0 then 'N'
                                 when nCtdTrans_Afectar_OLD = 1 then
                                   'Tiene mas de 5 minutos,@1 Transferencia Pendiente@Por Favor de Afectar.'
                                 else
                                   'Tienes mas de 5 minutos,@'||to_char(trim(nCtdTrans_Afectar_OLD),'999990') ||' Transferencias Pendientes@Por Favor de Afectarlas.'
                                 end MSJ ,
                                 '5' TIEMPO,
                                 8 PRIORIDAD
                         from  dual
                ) v
          order by  PRIORIDAD;

          RETURN V_RCURFP;
     END;


END FARMA_ALERTUP;
/

