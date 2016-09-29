CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_PED_ESPC" AS

  g_cNumPedRep PBL_NUMERA.COD_NUMERA%TYPE := '014';

  g_nCantDiasPeriodoMes NUMBER := '28';

  g_cTipoNotaEntrada LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '01';
  g_cTipoNotaSalida LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '02';
  g_cTipoNotaRecepcion LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '03';

  g_cCodMatriz PBL_LOCAL.COD_LOCAL%TYPE := '009';

  TYPE FarmaCursor IS REF CURSOR;

  g_nTipoFiltroPrincAct NUMBER(1) := 1;
  g_nTipoFiltroAccTerap NUMBER(1) := 2;
  g_nTipoFiltroLab NUMBER(1) := 3;


  --Descripcion: Se lista los pedidos especiales
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
    FUNCTION PED_ESPC_F_CUR_LISTA_PEDIDOS( cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cFecIni_in IN CHAR,
                                         cFecFin_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se lista los productos Especiales
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
  FUNCTION PED_ESPC_F_CUR_LISTA_PROD(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: Se guarda la cabecera del pedido especial
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
  PROCEDURE PED_ESPC_P_ING_CAB_PED_ESP(cCodGrupoCia_in    IN CHAR,
                                cCodLocal_in      IN CHAR,
                                cIdUsu_in         IN CHAR,
                                cNumPed_in        IN CHAR,
                                cCantItem_in      IN CHAR);

  --Descripcion: Se guarda el detalle del pedido especial
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
  PROCEDURE PED_ESPC_P_ING_DET_PED_ESP(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cIdUsu_in         IN CHAR,
                                   cNumPed_in        IN CHAR,
                                   cSec_in           IN CHAR,
                                   cCodProd_in       IN CHAR,
                                   cCantIngre_in     IN CHAR);

  --Descripcion: Se lista el detalle del pedido especial
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
  FUNCTION PED_ESPC_F_CUR_LISTA_PED_DET(cGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNumPed_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se actualiza detalle pedido especial
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
  PROCEDURE PED_ESPC_P_UPT_PROD_DET(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cIdUsu_in         IN CHAR,
                                   cNumPed_in        IN CHAR,
                                   cCodProd_in       IN CHAR,
                                   cCantIngre_in     IN CHAR,
                                   cEstProd_in       IN CHAR);

  --Descripcion: Se actualiza cabecera pedido especial
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
  PROCEDURE PED_ESPC_P_UPT_CAB(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cIdUsu_in         IN CHAR,
                             cNumPed_in        IN CHAR);

  --Descripcion: retorna el numero maximo de item por pedido especial
  --Fecha       Usuario   Comentario
  --22/09/2008  DUBILLUZ     Creación
  FUNCTION PED_ESPC_F_CHAR_MAX_ITEM_PED(cGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR)
  RETURN varCHAR2;


  --Descripcion: Se anula el pedido completo del local
  --Fecha       Usuario   Comentario
  --25/09/2008  JCORTEZ   Creación
  PROCEDURE PED_ESPC_ANULA(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cIdUsu_in         IN CHAR,
                             cNumPed_in        IN CHAR);

  --Descripcion: Cambia el estado del pedido a M
  --Fecha       Usuario   Comentario
  --18/10/2008  DVELIZ   Creación
  PROCEDURE PED_ESPC_P_MOD_EST_PEDIDO(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cIdUsu_in         IN CHAR,
                             cNumPed_in        IN CHAR,
                             cEstPedVta        IN CHAR,
                             cIpPC             IN CHAR);

  --Descripcion: Carga el detalle de un determinado pedido para modificarlo.
  --Fecha       Usuario   Comentario
  --18/10/2008  DVELIZ   Creación
  FUNCTION PED_ESPC_P_CUR_CARGA_DETALLE(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cNumPed_in        IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Limpia el detalle de cierto pedido para actualizarlo
  --Fecha       Usuario   Comentario
  --18/10/2008  DVELIZ   Creación
  PROCEDURE PED_ESPC_P_DEL_DET_PEDIDO(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cNumPed_in        IN CHAR);

  --Descripcion: Envia un mensaje de alerta con pedido en esta M
  --Fecha       Usuario   Comentario
  --18/10/2008  DVELIZ   Creación
  FUNCTION PED_ESPC_F_VAR_MENSAJE(cCodGrupoCia_in         IN CHAR,
                                   cCodLocal_in            IN CHAR,
                                   cFechaInicial_in        IN CHAR,
                                   cFechaFinal_in          IN CHAR)
  RETURN VARCHAR2;


  FUNCTION PED_ESPC_F_GET_EST_PEDIDO(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cNumPed_in        IN CHAR)
  RETURN CHAR;
end;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_PED_ESPC" AS


 /******************************************************************************/

  FUNCTION PED_ESPC_F_CUR_LISTA_PEDIDOS( cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cFecIni_in IN CHAR,
                                         cFecFin_in IN CHAR)
  RETURN FarmaCursor
  IS
    curPedRep FarmaCursor;
  BEGIN
    OPEN curPedRep FOR
    SELECT R.NUM_PED_ESP || 'Ã' ||
           TO_CHAR(R.FEC_PED_ESP ,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
           R.CANT_ITEM || 'Ã' ||
           DECODE(R.EST_PED_VTA,'P','PENDIENTE','C','CONFIRMADO','N','ANULADO', 'M','MODIFICADO')|| 'Ã' ||
           R.USU_CREA_PED_CAB|| 'Ã' ||
           decode(R.FECHA_PROCESO,null,' ',TO_CHAR( R.FECHA_PROCESO,'dd/MM/yyyy HH24:MI:SS')) || 'Ã' ||
           R.EST_PED_VTA
    FROM VTA_PED_ESP_CAB R, PBL_LOCAL L
    WHERE R.COD_GRUPO_CIA = cCodGrupoCia_in
          AND R.COD_LOCAL = cCodLocal_in
          AND R.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND R.COD_LOCAL = L.COD_LOCAL
          AND R.FEC_PED_ESP BETWEEN TO_DATE(cFecIni_in||' 00:00:00','dd/MM/yyyy HH24:MI:SS')  AND TO_DATE(cFecFin_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS');


       RETURN curPedRep;
  END;

   /******************************************************************************/

  FUNCTION PED_ESPC_F_CUR_LISTA_PROD(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curProductos FarmaCursor;
  BEGIN
    OPEN curProductos FOR
    SELECT P.COD_PROD || 'Ã' ||
          P.DESC_PROD || 'Ã' ||
          --DECODE(P.IND_PROD_FRACCIONABLE,'S',L.UNID_VTA,P.DESC_UNID_PRESENT) || 'Ã' ||
          P.DESC_UNID_PRESENT || 'Ã' ||
          B.NOM_LAB || 'Ã' ||
          TO_CHAR(L.STK_FISICO/l.val_frac_local,'999990') || 'Ã' ||
          ' '|| 'Ã' ||
          L.VAL_FRAC_LOCAL || 'Ã' ||
          TO_CHAR(L.VAL_PREC_VTA,'999990')
          --TO_CHAR(SYSDATE,'dd/MM/yyyy HH24:MI:SS')
    FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_LAB B
    WHERE P.COD_GRUPO_CIA = cGrupoCia_in
          AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND P.COD_PROD = L.COD_PROD
          AND L.COD_LOCAL = cCodLocal_in
          AND B.COD_LAB = P.COD_LAB;

    RETURN curProductos;

  END;

  /**************************************************************************************************/
    PROCEDURE PED_ESPC_P_ING_CAB_PED_ESP(cCodGrupoCia_in    IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cIdUsu_in         IN CHAR,
                                   cNumPed_in        IN CHAR,
                                   cCantItem_in      IN CHAR)

   IS
   vCount   NUMBER(1);

     BEGIN
         --MODIFICADO POR DVELIZ 18.10.08
         SELECT COUNT(*) INTO vCount
         FROM VTA_PED_ESP_CAB
         WHERE NUM_PED_ESP = cNumPed_in
           AND COD_LOCAL = cCodLocal_in
           AND COD_GRUPO_CIA = cCodGrupoCia_in;

         IF(vCount = 0) THEN
             INSERT INTO VTA_PED_ESP_CAB(COD_GRUPO_CIA
                                        ,COD_LOCAL
                                        ,NUM_PED_ESP
                                        ,FEC_PED_ESP
                                        ,CANT_ITEM
                                        ,USU_CREA_PED_CAB
                                        ,FEC_MOD_PED_CAB
                                        ,USU_MOD_PED_CAB)
             VALUES(cCodGrupoCia_in,cCodLocal_in,cNumPed_in,SYSDATE,TO_NUMBER(cCantItem_in),cIdUsu_in,NULL,NULL);
         ELSE
            UPDATE VTA_PED_ESP_CAB
            SET CANT_ITEM = cCantItem_in
            WHERE NUM_PED_ESP = cNumPed_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_GRUPO_CIA = cCodGrupoCia_in;
         END IF;
      END;

    /**********************************************************************************/
     PROCEDURE PED_ESPC_P_ING_DET_PED_ESP(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cIdUsu_in         IN CHAR,
                                   cNumPed_in        IN CHAR,
                                   cSec_in           IN CHAR,
                                   cCodProd_in       IN CHAR,
                                   cCantIngre_in     IN CHAR)

   IS
       BEGIN


      INSERT INTO VTA_PED_ESP_DET(COD_GRUPO_CIA
                                 ,COD_LOCAL
                                 ,NUM_PED_ESP
                                 ,SEC_PED_VTA_DET
                                 ,COD_PROD
                                 ,CANT_ATENDIDA
                                 ,USU_CREA_PED_DET
                                 ,FEC_MOD_PED_DET
                                 ,USU_MOD_PED_DET)
       VALUES(cCodGrupoCia_in,
              cCodLocal_in,
              cNumPed_in,
              TO_NUMBER(TRIM(cSec_in)),
              cCodProd_in,
              TO_NUMBER(TRIM(cCantIngre_in)),
              cIdUsu_in,NULL,NULL);
      COMMIT;

    EXCEPTION
    WHEN OTHERS THEN
      RAISE;

    END;

   /*****************************************************************************/
   FUNCTION PED_ESPC_F_CUR_LISTA_PED_DET(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumPed_in IN CHAR)
  RETURN FarmaCursor
  IS
    curProductos FarmaCursor;
  BEGIN
    OPEN curProductos FOR
    SELECT P.COD_PROD || 'Ã' ||
          P.DESC_PROD || 'Ã' ||
          DECODE(P.IND_PROD_FRACCIONABLE,'S',L.UNID_VTA,P.DESC_UNID_PRESENT) || 'Ã' ||
          B.NOM_LAB || 'Ã' ||
          A.CANT_ATENDIDA|| 'Ã' ||
          DECODE(A.EST_PED_VTA_DET,'P','PENDIENTE','C','CONFIRMADO','N','ANULADO')|| 'Ã' ||
          A.EST_PED_VTA_DET
    FROM VTA_PED_ESP_DET A,
         LGT_PROD P,
         LGT_PROD_LOCAL L,
         LGT_LAB B
    WHERE A.COD_GRUPO_CIA=P.COD_GRUPO_CIA
    AND A.COD_PROD=P.COD_PROD
    AND P.COD_GRUPO_CIA = cGrupoCia_in
    AND A.NUM_PED_ESP=cNumPed_in
          AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND P.COD_PROD = L.COD_PROD
          AND L.COD_LOCAL = cCodLocal_in
          AND B.COD_LAB = P.COD_LAB;

    RETURN curProductos;
  END;

  /**********************************************************************************/
   PROCEDURE PED_ESPC_P_UPT_PROD_DET(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cIdUsu_in         IN CHAR,
                                   cNumPed_in        IN CHAR,
                                   cCodProd_in       IN CHAR,
                                   cCantIngre_in     IN CHAR,
                                   cEstProd_in       IN CHAR)

   IS
     BEGIN
          UPDATE VTA_PED_ESP_DET X
          SET X.EST_PED_VTA_DET=cEstProd_in,
              X.CANT_ATENDIDA=TO_NUMBER(cCantIngre_in),
              X.USU_MOD_PED_DET=cIdUsu_in,
              X.FEC_MOD_PED_DET=SYSDATE
          WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
          AND X.COD_LOCAL=cCodLocal_in
          AND X.NUM_PED_ESP=cNumPed_in
          AND X.COD_PROD=cCodProd_in;
      END;
  /**********************************************************************************/
    PROCEDURE PED_ESPC_P_UPT_CAB(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in      IN CHAR,
                               cIdUsu_in         IN CHAR,
                               cNumPed_in        IN CHAR)

   IS
     BEGIN


          UPDATE VTA_PED_ESP_CAB X
          SET X.EST_PED_VTA='C',
              X.USU_MOD_PED_CAB=cIdUsu_in,
              X.FEC_MOD_PED_CAB=SYSDATE
          WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
          AND X.COD_LOCAL=cCodLocal_in
          AND X.NUM_PED_ESP=cNumPed_in;

          UPDATE VTA_PED_ESP_DET X
          SET X.EST_PED_VTA_DET='C',
              X.USU_MOD_PED_DET=cIdUsu_in,
              X.FEC_MOD_PED_DET=SYSDATE
          WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
          AND X.COD_LOCAL=cCodLocal_in
          AND X.NUM_PED_ESP=cNumPed_in
          AND X.EST_PED_VTA_DET='P'
          AND X.EST_PED_VTA_DET<>'N';
      END;

 /* ************************************************************************** */
  FUNCTION PED_ESPC_F_CHAR_MAX_ITEM_PED(cGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR)
  RETURN varCHAR2
  IS
  cItemMaximoPedEspc varCHAR2(2000);

  BEGIN
   BEGIN
      select nvl(R.MAX_ITEM_PED_ESPC,'0')
      into   cItemMaximoPedEspc
      from   LGT_PARAM_REP_LOC R
      WHERE  R.COD_GRUPO_CIA = cGrupoCia_in
      AND    R.COD_LOCAL     = cCodLocal_in;
   EXCEPTION
   WHEN NO_DATA_FOUND THEN
      cItemMaximoPedEspc := '0';
   END;

    return cItemMaximoPedEspc;

  END;

    /**********************************************************************************/
   PROCEDURE PED_ESPC_ANULA(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cIdUsu_in         IN CHAR,
                             cNumPed_in        IN CHAR)

   IS
     BEGIN

          UPDATE VTA_PED_ESP_CAB X
          SET X.EST_PED_VTA='N',
              X.USU_MOD_PED_CAB=cIdUsu_in,
              X.FEC_MOD_PED_CAB=SYSDATE
          WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
          AND X.COD_LOCAL=cCodLocal_in
          AND X.NUM_PED_ESP=cNumPed_in;

          UPDATE VTA_PED_ESP_DET Y
          SET Y.EST_PED_VTA_DET='N',
              Y.USU_MOD_PED_DET=cIdUsu_in,
              Y.FEC_MOD_PED_DET=SYSDATE
          WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
          AND Y.COD_LOCAL=cCodLocal_in
          AND Y.NUM_PED_ESP=cNumPed_in;
      END;

  /**************************************************************************/
  PROCEDURE PED_ESPC_P_MOD_EST_PEDIDO(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cIdUsu_in         IN CHAR,
                             cNumPed_in        IN CHAR,
                             cEstPedVta        IN CHAR,
                             cIpPC             IN CHAR)
  IS
  BEGIN
    UPDATE VTA_PED_ESP_CAB
    SET EST_PED_VTA = cEstPedVta,
    IPCOMPUTADOR = cIpPC,
    USU_MOD_PED_CAB = cIdUsu_in,
    FEC_MOD_PED_CAB = SYSDATE
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
      AND NUM_PED_ESP = cNumPed_in;
    COMMIT;
  EXCEPTION
  WHEN OTHERS THEN
    RAISE;
    ROLLBACK;
  END;

  /*************************************************************************/
  FUNCTION PED_ESPC_P_CUR_CARGA_DETALLE(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cNumPed_in        IN CHAR)
  RETURN FarmaCursor
  IS
  curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
        SELECT B.COD_PROD                                               || 'Ã' ||
           D.DESC_PROD                                                  || 'Ã' ||
           D.DESC_UNID_PRESENT                                          || 'Ã' ||
           E.NOM_LAB                                                    || 'Ã' ||
           TRIM(TO_CHAR(C.STK_FISICO/C.VAL_FRAC_LOCAL,'999990'))    || 'Ã' ||
           B.CANT_ATENDIDA                                              || 'Ã' ||
           C.VAL_FRAC_LOCAL                                             || 'Ã' ||
           TRIM(TO_CHAR(C.VAL_PREC_VTA,'999990'))
        FROM VTA_PED_ESP_CAB A,
             VTA_PED_ESP_DET B,
             LGT_PROD_LOCAL C,
             LGT_PROD D,
             LGT_LAB E
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
          AND A.COD_LOCAL = cCodLocal_in
          AND A.NUM_PED_ESP = B.NUM_PED_ESP
          AND A.EST_PED_VTA IN ('P', 'M')
          AND A.NUM_PED_ESP = cNumPed_in
          AND B.COD_PROD = D.COD_PROD
          AND D.COD_LAB = E.COD_LAB
          AND C.COD_PROD = B.COD_PROD;
    RETURN  curProd;
  END;

  /*************************************************************************/
  PROCEDURE PED_ESPC_P_DEL_DET_PEDIDO(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cNumPed_in        IN CHAR)
  IS
  BEGIN
        DELETE FROM VTA_PED_ESP_DET
        WHERE NUM_PED_ESP = cNumPed_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_GRUPO_CIA = cCodGrupoCia_in;
        COMMIT;
  EXCEPTION
  WHEN OTHERS THEN
    RAISE;
  END;

  /*************************************************************************/
  FUNCTION PED_ESPC_F_VAR_MENSAJE(cCodGrupoCia_in         IN CHAR,
                                   cCodLocal_in            IN CHAR,
                                   cFechaInicial_in        IN CHAR,
                                   cFechaFinal_in          IN CHAR)
  RETURN VARCHAR2
  IS
  vCount    NUMBER(1);
  vMensaje  VARCHAR(100):='';
  BEGIN
    SELECT COUNT(*) INTO vCount
    FROM VTA_PED_ESP_CAB
    WHERE FEC_PED_ESP BETWEEN TO_DATE(cFechaInicial_in,'dd/MM/yyyy') AND TO_DATE(cFechaFinal_in || ' 23:59:59', 'dd/MM/yyyy HH24:MI:SS')
      AND EST_PED_VTA = 'M'
      AND COD_LOCAL = cCodLocal_in
      AND COD_GRUPO_CIA = cCodGrupoCia_in;

    IF(vCount > 1)THEN
      vMensaje:= 'Alerta!!!. Tiene ' || vCount ||' pedidos sin haber concluido su modificación.';
    elsif vCount = 1 THEN
      vMensaje:= 'Alerta!!!. Tiene ' || vCount ||' pedido sin haber concluido su modificación.';
    END IF;

    RETURN vMensaje;
  END;
  /*************************************************************************/
  FUNCTION PED_ESPC_F_GET_EST_PEDIDO(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cNumPed_in        IN CHAR)
  RETURN CHAR
  IS
  vEstado char(1);
  BEGIN
  begin
  SELECT C.EST_PED_VTA
  INTo    vEstado
  FROM   VTA_PED_ESP_CAB C
  WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
  AND    C.COD_LOCAL = cCodLocal_in
  AND    C.NUM_PED_ESP = cNumPed_in;
  EXCEPTION
  WHEN OTHERS THEN
    vEstado := 'N';
    end;
    return vEstado;


  END;


END;
/

