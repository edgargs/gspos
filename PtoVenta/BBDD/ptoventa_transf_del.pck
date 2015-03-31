CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_TRANSF_DEL" is

  -- Author  : ERIOS
  -- Created : 12/09/2006 10:09:58 a.m.
  -- Purpose : TRANSFERENCIAS AUTOMATICAS

  TYPE FarmaCursor IS REF CURSOR;

  g_cNumNotaEs PBL_NUMERA.COD_NUMERA%TYPE := '011';

  g_cTipCompGuia LGT_KARDEX.Tip_Comp_Pago%TYPE := '03';

  g_cTipoNotaSalida LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '02';

  g_cTipoOrigenLocal CHAR(2):= '01';
  g_cTipoOrigenMatriz CHAR(2):= '02';

  COD_NUMERA_SEC_KARDEX  PBL_NUMERA.COD_NUMERA%TYPE := '016';
  COD_MOTIVO_TRANS_DEL CHAR(2) := '99';

  --Descripcion: Obtiene la lista de pedidos de transferencia pendientes.
  --Fecha       Usuario	  Comentario
  --06/11/2008  JCALLO    Creación
  FUNCTION TRANF_F_CUR_DEL_PEND(cGrupoCia_in IN CHAR,
                                 cCodLocal_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: detalle de los productos y cantidad del pedido de transferencia.
  --Fecha       Usuario	  Comentario
  --06/11/2008  JCALLO    Creación
  FUNCTION TRANF_F_CUR_DET_PEDIDO(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in 	      IN CHAR,
                                  cNumPedTransf_in    IN CHAR,
                                  nSecGrupo_in        IN NUMBER,
                                  cCodLocalOrigen_in  IN CHAR,
                                  cCodLocalDest_in    IN CHAR,
                                  nSecTrans_in        IN NUMBER
                                  )
  RETURN FarmaCursor;

  --Descripcion: Encargado de actualizar el estado del pedido.
  --Fecha       Usuario	  Comentario
  --06/11/2008  JCALLO    Creación
  PROCEDURE TRANF_P_UPDATE_CAB_PEDIDO(cCodGrupoCia_in 	   IN CHAR,
                                      cCodLocal_in    	   IN CHAR,
                                      cNumPed_in           IN CHAR,
                                      nSecGrupo_in         IN NUMBER,
                                      cCodLocalOrigen_in   IN char,
                                      cCodLocalDest_in     IN CHAR,
                                      nSecTrans_in         IN NUMBER,
                                      cEstado_in           IN CHAR,
                                      cUsuId_in            IN CHAR);

 FUNCTION TRANSF_F_CHAR_AGREGA_CABECERA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        vTipDestino_in IN CHAR,
                                        cCodDestino_in IN VARCHAR2,
                                        cTipMotivo_in IN CHAR,
                                        vDesEmp_in IN VARCHAR2,
                                        vRucEmp_in IN VARCHAR2,
                                        vDirEmp_in IN VARCHAR2,
                                        vDesTran_in IN VARCHAR2,
                                        vRucTran_in IN VARCHAR2,
                                        vDirTran_in IN VARCHAR2,
                                        vPlacaTran_in IN VARCHAR2,
                                        nCantItems_in IN NUMBER,
                                        nValTotal_in IN NUMBER,
                                        vUsu_in IN VARCHAR2,
                                        cCodMotTransInterno_in CHAR,

                                        cCodLocalDel_in IN CHAR,
                                        cNumPed_in IN CHAR,
                                        nSecGrupo  IN NUMBER

                                        )
  RETURN CHAR;

  PROCEDURE TRANSF_P_GRABAR_KARDEX(cCodGrupoCia_in 	   IN CHAR,
                                   cCodLocal_in    	   IN CHAR,
                                   cCodProd_in		   IN CHAR,
							                     cCodMotKardex_in 	   IN CHAR,
						   	                   cTipDocKardex_in     IN CHAR,
						   	                   cNumTipDoc_in  	   IN CHAR,
							                     nStkAnteriorProd_in  IN NUMBER,
							                     nCantMovProd_in  	   IN NUMBER,
							                     nValFrac_in		   IN NUMBER,
							                     cDescUnidVta_in	   IN CHAR,
							                     cUsuCreaKardex_in	   IN CHAR,
							                     cCodNumera_in	   	   IN CHAR,
							                     cGlosa_in			   IN CHAR DEFAULT NULL,
                                   cTipDoc_in IN CHAR DEFAULT NULL,
                                   cNumDoc_in IN CHAR DEFAULT NULL);
/*
  PROCEDURE TRANSF_P_AGREGA_DETALLE(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    cNumNota_in      IN CHAR,
                                    cCodProd_in      IN CHAR,
                                    nValPrecUnit_in  IN NUMBER,
                                    nValPrecTotal_in IN NUMBER,
                                    nCantMov_in      IN NUMBER,
                                    vFecVecProd_in   IN VARCHAR2,
                                    vNumLote_in      IN VARCHAR2,
                                    cCodMotKardex_in IN CHAR,
                                    cTipDocKardex_in IN CHAR,
                                    vValFrac_in      IN NUMBER,
                                    vUsu_in          IN VARCHAR2,
                                    vTipDestino_in   IN CHAR,
                                    cCodDestino_in   IN CHAR);
*/
PROCEDURE TRANSF_P_AGREGA_DETALLE(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in     IN CHAR,
                                  cNumNota_in      IN CHAR,
                                  cCodProd_in      IN CHAR,
                                  nValPrecUnit_in  IN NUMBER,
                                  nValPrecTotal_in IN NUMBER,
                                  nCantMov_in      IN NUMBER,
                                  vFecVecProd_in   IN VARCHAR2,
                                  vNumLote_in      IN VARCHAR2,
                                  cCodMotKardex_in IN CHAR,
                                  cTipDocKardex_in IN CHAR,
                                  vValFrac_in      IN NUMBER,
                                  vUsu_in          IN VARCHAR2,
                                  vTipDestino_in   IN CHAR,
                                  cCodDestino_in   IN CHAR,
                                  vIndFrac_in      IN CHAR DEFAULT 'N');


  PROCEDURE TRANSF_P_CONFIRMAR(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumNotaEs_in IN CHAR, vIdUsu_in IN VARCHAR2);

  PROCEDURE TRANSF_P_GENERA_GUIA(cGrupoCia_in   IN CHAR,
  										           cCodLocal_in   IN CHAR,
										             cNumNota_in    IN CHAR,
                                 nCantMAxDet_in IN NUMBER,
										             nCantItems_in  IN NUMBER,
										             cIdUsu_in      IN CHAR);

  FUNCTION TRANF_F_CHAR_VAL_ESTADO_PED(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in 	      IN CHAR,
                                  cNumPedTransf_in    IN CHAR,
                                  nSecGrupo_in        IN NUMBER,
                                  cCodLocalOrigen_in  IN CHAR,
                                  cCodLocalDest_in    IN CHAR,
                                  nSecTrans_in        IN NUMBER
                                  )
  RETURN CHAR;

end PTOVENTA_TRANSF_DEL;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_TRANSF_DEL" is

  /****************************************************************************/
  FUNCTION TRANF_F_CUR_DEL_PEND(cGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curTransf FarmaCursor;
  BEGIN
    OPEN curTransf FOR
    SELECT C.NUM_PED_VTA                                             || 'Ã' ||
           C.COD_LOCAL_DESTINO||'-'||L.DESC_CORTA_LOCAL              || 'Ã' ||
           C.CANT_ITEMS_PED_VTA                                      || 'Ã' ||
           TO_CHAR(C.FEC_CREA_TMP_TRANS_CAB,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
           C.COD_LOCAL                                               || 'Ã' ||
           C.SEC_GRUPO                                               || 'Ã' ||
           C.COD_LOCAL_ORIGEN                                        || 'Ã' ||
           C.COD_LOCAL_DESTINO                                       || 'Ã' ||
           C.SEC_TRANS
    FROM TMP_DEL_TRANS_CAB C,
         VTA_GRUPO_TRANS_PED G,
         --TMP_DEL_TRANS_DET D ,
         PBL_LOCAL L
    WHERE C.COD_GRUPO_CIA = cGrupoCia_in
      AND C.COD_LOCAL_ORIGEN = cCodLocal_in
      AND C.EST_TRANS = 'A'

      AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
      AND C.COD_LOCAL = G.COD_LOCAL
      AND C.NUM_PED_VTA = G.NUM_PED_VTA
      AND C.SEC_GRUPO = G.SEC_GRUPO
      AND G.EST_GRUPO_TRANS = 'A'

      /*AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
      AND C.COD_LOCAL     = D.COD_LOCAL
      AND C.NUM_PED_VTA   = D.NUM_PED_VTA
      AND C.SEC_GRUPO     = D.SEC_GRUPO*/

      AND L.COD_GRUPO_CIA = C.COD_GRUPO_CIA
      AND L.COD_LOCAL     = C.COD_LOCAL_DESTINO;

    RETURN curTransf;
  END;

  /**************************************************************/

  FUNCTION TRANF_F_CUR_DET_PEDIDO(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in 	      IN CHAR,
                                  cNumPedTransf_in    IN CHAR,
                                  nSecGrupo_in        IN NUMBER,
                                  cCodLocalOrigen_in  IN CHAR,
                                  cCodLocalDest_in    IN CHAR,
                                  nSecTrans_in        IN NUMBER
                                  )
  RETURN FarmaCursor
  IS
  	curDet FarmaCursor;
    nCont number:=0;
    v_cEmailErrorPtoVenta PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='joliva';
    v_vDescLocalOrigen VARCHAR2(120);
    v_vDescLocalDestino VARCHAR2(120);
  BEGIN

      SELECT count(1) into nCont
      FROM TMP_DEL_TRANS_DET D, LGT_PROD P, LGT_PROD_LOCAL L, LGT_LAB B
      WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND D.COD_LOCAL     = cCodLocal_in
        AND D.NUM_PED_VTA = cNumPedTransf_in
        AND D.SEC_GRUPO = nSecGrupo_in
        AND D.COD_LOCAL_ORIGEN = cCodLocalOrigen_in
        AND D.COD_LOCAL_DESTINO = cCodLocalDest_in
        AND D.SEC_TRANS = nSecTrans_in

        AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
        AND D.COD_LOCAL_ORIGEN = L.COD_LOCAL
        AND D.COD_PROD = L.COD_PROD
        AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND L.COD_PROD = P.COD_PROD
        AND P.COD_LAB = B.COD_LAB
        AND L.VAL_FRAC_LOCAL != D.VAL_FRAC_VTA;

      IF nCont > 0 then

        SELECT G.LLAVE_TAB_GRAL INTO v_cEmailErrorPtoVenta
        FROM PBL_TAB_GRAL  G
        WHERE G.ID_TAB_GRAL = 242;

        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
          INTO   v_vDescLocalOrigen
          FROM   PBL_LOCAL
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND    COD_LOCAL = cCodLocalOrigen_in;

          SELECT COD_LOCAL ||' - '|| DESC_LOCAL
          INTO   v_vDescLocalDestino
          FROM   PBL_LOCAL
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND    COD_LOCAL = cCodLocalDest_in;


         FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                           v_cEmailErrorPtoVenta,
                           'ERROR AL GENERAR TRANSFERENCIA DELIVERY EN LOCAL : '||v_vDescLocalOrigen,
                           'ALERTA',
                           '<H1>ERROR AL GENERAR TRANSFERENCIA EN LOCAL '||v_vDescLocalOrigen||'</H1><BR>'||
                           '<i>Valor fracciòn de algun(os) de los productos de local de la transferencia es diferente a la de delivery</i><BR>'||
                           '<br>NRO PEDIDO TRANSFERENCIA : <b>'||cNumPedTransf_in||'</b>'||
                           '<br>SECUENCIAL DE GRUPO      : <b>'||nSecGrupo_in||'</b>'||
                           '<br>LOCAL ORIGEN             : <b>'||v_vDescLocalOrigen||'</b>'||
                           '<br>LOCAL DESTINO            : <b>'||v_vDescLocalDestino||'</b>'||
                           '<BR><BR> FECHA : <B>'||to_char(SYSDATE,'dd/MM/yyyy HH24:MI:SS')||'</B>',
                           'jcallo',
                           FARMA_EMAIL.GET_EMAIL_SERVER,
                           TRUE);
            --generando el error en el aplicativo

      end if;

  	OPEN curDet FOR
    	SELECT D.COD_PROD                                            || 'Ã' ||--0
    	       P.DESC_PROD                                           || 'Ã' ||--1
  		       --NVL(L.UNID_VTA,' ')	                                 || 'Ã' ||--2
             case
               when d.val_frac_vta = 1 then nvl(p.desc_unid_present,' ')
                 else nvl(l.unid_vta,' ')
             end                                                   || 'Ã' ||--2


    	       B.NOM_LAB                                             || 'Ã' ||--3
    	       D.CANTIDAD                                            || 'Ã' ||--4
             ' '                                                   || 'Ã' ||--5
             ' '                                                   || 'Ã' ||--6
             (l.stk_fisico)                     || 'Ã' ||--7
    	       --L.UNID_VTA                                            || 'Ã' ||--
             case
               when d.val_frac_vta = 1 then nvl(p.desc_unid_present,' ')
                 else nvl(l.unid_vta,' ')
             end                                                   || 'Ã' ||--2
             to_char(sysdate,'dd/MM/yyyy HH24:MI:SS')              || 'Ã' ||
             L.VAL_FRAC_LOCAL                                      || 'Ã' ||
             D.Val_Frac_Vta                                        || 'Ã' ||
             DECODE((L.VAL_FRAC_LOCAL - D.Val_Frac_Vta),0,'S','N') || 'Ã' ||--validar si al menos un producto no tiene el mismo valor fraccion
             to_char(l.val_prec_vta,'999999990.00')                  || 'Ã' ||
             to_char(d.cantidad * l.val_prec_vta,'999999990.00')     || 'Ã' ||
             CASE WHEN ( (L.STK_FISICO) - D.CANTIDAD ) >= 0 THEN 'S' ELSE 'N' END  -- VALIDAR QUE haya stock disponible para los productos del pedido
      FROM TMP_DEL_TRANS_DET D, LGT_PROD P, LGT_PROD_LOCAL L, LGT_LAB B
    	WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND D.COD_LOCAL     = cCodLocal_in
        AND D.NUM_PED_VTA = cNumPedTransf_in
        AND D.SEC_GRUPO = nSecGrupo_in
        AND D.COD_LOCAL_ORIGEN = cCodLocalOrigen_in
        AND D.COD_LOCAL_DESTINO = cCodLocalDest_in
        AND D.SEC_TRANS = nSecTrans_in

        AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
        AND D.COD_LOCAL_ORIGEN = L.COD_LOCAL
        AND D.COD_PROD = L.COD_PROD
        AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND L.COD_PROD = P.COD_PROD
        AND P.COD_LAB = B.COD_LAB;

  	RETURN curDet;
  END;

  /*******************************************************************/
  PROCEDURE TRANF_P_UPDATE_CAB_PEDIDO(cCodGrupoCia_in 	   IN CHAR,
                                      cCodLocal_in    	   IN CHAR,
                                      cNumPed_in           IN CHAR,
                                      nSecGrupo_in         IN NUMBER,
                                      cCodLocalOrigen_in   IN char,
                                      cCodLocalDest_in     IN CHAR,
                                      nSecTrans_in         IN NUMBER,
                                      cEstado_in           IN CHAR,
                                      cUsuId_in            IN CHAR)
  AS

  BEGIN

    UPDATE TMP_DEL_TRANS_CAB C SET C.EST_TRANS = cEstado_in,
                                   C.USU_MOD_TMP_TRANS_CAB = cUsuId_in,
                                   C.FEC_MOD_TMP_TRANS_CAB = SYSDATE
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   C.COD_LOCAL     = cCodLocal_in
    AND   C.NUM_PED_VTA   = cNumPed_in
    AND   C.SEC_GRUPO     = nSecGrupo_in
    AND   C.COD_LOCAL_ORIGEN = cCodLocalOrigen_in
    AND   C.COD_LOCAL_DESTINO = cCodLocalDest_in
    AND   C.SEC_TRANS = nSecTrans_in;

    EXCEPTION
    WHEN OTHERS THEN
         RAISE_APPLICATION_ERROR(-20081,'NO SE PUDO ACTUALIZAR EL ESTADO DEL PEDIDO.');


  END;







  FUNCTION TRANSF_F_CHAR_AGREGA_CABECERA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        vTipDestino_in IN CHAR,
                                        cCodDestino_in IN VARCHAR2,
                                        cTipMotivo_in IN CHAR,
                                        vDesEmp_in IN VARCHAR2,
                                        vRucEmp_in IN VARCHAR2,
                                        vDirEmp_in IN VARCHAR2,
                                        vDesTran_in IN VARCHAR2,
                                        vRucTran_in IN VARCHAR2,
                                        vDirTran_in IN VARCHAR2,
                                        vPlacaTran_in IN VARCHAR2,
                                        nCantItems_in IN NUMBER,
                                        nValTotal_in IN NUMBER,
                                        vUsu_in IN VARCHAR2,
                                        cCodMotTransInterno_in CHAR,

                                        cCodLocalDel_in IN CHAR,
                                        cNumPed_in IN CHAR,
                                        nSecGrupo  IN NUMBER

                                        )
  RETURN CHAR
  IS
  	v_nNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;

    v_cEmailErrorPtoVenta PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='joliva';
    v_vDescLocalOrigen VARCHAR2(120);
    v_vDescLocalDestino VARCHAR2(120);
-- kmoncada 26.08.2014 cambios para grabar proforma de transferencia delivery
    v_traCia VTA_GRUPO_TRANS_PED.TRA_CIA%TYPE;
    v_traCodLocal VTA_GRUPO_TRANS_PED.TRA_COD_LOCAL%TYPE;
    v_traNumPed VTA_GRUPO_TRANS_PED.TRA_NUM_PROFORMA%TYPE;

  BEGIN
    	v_nNumNota := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumNotaEs),10,'0','I' );

      -- kmoncada 26.08.2014 cambios para grabar proforma de transferencia delivery
      BEGIN
        SELECT A.TRA_CIA, A.TRA_COD_LOCAL, A.TRA_NUM_PROFORMA
        INTO   v_traCia, v_traCodLocal, v_traNumPed
        FROM VTA_GRUPO_TRANS_PED A
        WHERE A.COD_LOCAL = cCodLocalDel_in
        AND A.NUM_PED_VTA = cNumPed_in;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          v_traCia := null;
          v_traCodLocal := null;
          v_traNumPed := null;
      END;

  	  INSERT INTO LGT_NOTA_ES_CAB(COD_GRUPO_CIA, COD_LOCAL, NUM_NOTA_ES, FEC_NOTA_ES_CAB, EST_NOTA_ES_CAB,
                                  COD_ORIGEN_NOTA_ES, TIP_ORIGEN_NOTA_ES, COD_DESTINO_NOTA_ES,
                                  TIP_MOT_NOTA_ES, DESC_EMPRESA, RUC_EMPRESA, DIR_EMPRESA,
                                  DESC_TRANS, RUC_TRANS, DIR_TRANS, PLACA_TRANS, CANT_ITEMS,
                                  VAL_TOTAL_NOTA_ES_CAB, TIP_NOTA_ES, USU_CREA_NOTA_ES_CAB,
                                  TIP_DOC,COD_MOTIVO_INTERNO_TRANS,
                                  COD_GRUPO_CIA_DEL, COD_LOCAL_DEL, NUM_PED_VTA_DEL, SEC_GRUPO_DEL,
       -- kmoncada 26.08.2014 cambios para grabar proforma de transferencia delivery
                                  TRA_CIA, TRA_COD_LOCAL, TRA_NUM_PROFORMA)
  	 -- VALUES(cCodGrupoCia_in,cCodLocal_in,v_nNumNota, SYSDATE,'C',
       VALUES(cCodGrupoCia_in,cCodLocal_in,v_nNumNota, SYSDATE,'P',
             cCodLocal_in,vTipDestino_in,cCodDestino_in,
             cTipMotivo_in,vDesEmp_in, vRucEmp_in , vDirEmp_in,
             vDesTran_in, vRucTran_in, vDirTran_in,vPlacaTran_in, nCantItems_in,
             nValTotal_in, g_cTipoNotaSalida,vUsu_in,
             g_cTipCompGuia,
                            --cCodMotTransInterno_in
                            COD_MOTIVO_TRANS_DEL
                            ,

             cCodGrupoCia_in, cCodLocalDel_in, cNumPed_in, nSecGrupo,
             v_traCia, v_traCodLocal, v_traNumPed);

    	Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumNotaEs, vUsu_in);


      RETURN v_nNumNota;--retorna valor si no hay errorres

    EXCEPTION
    WHEN OTHERS THEN--SI OCURRE ALGUN ERROR--
        --DBMS_OUTPUT.PUT_LINE('ERROR :'||cCodLocal_in);
         --SE ENVIARA CORREO
        SELECT G.LLAVE_TAB_GRAL INTO v_cEmailErrorPtoVenta
        FROM PBL_TAB_GRAL  G
        WHERE G.ID_TAB_GRAL = 242;

        --DBMS_OUTPUT.PUT_LINE('v_cEmailErrorPtoVenta : '||v_cEmailErrorPtoVenta);

        SELECT COD_LOCAL ||' - '|| DESC_LOCAL INTO   v_vDescLocalOrigen
        FROM   PBL_LOCAL
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL = cCodLocal_in;

        --DBMS_OUTPUT.PUT_LINE('v_vDescLocalOrigen : '||v_vDescLocalOrigen);
        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO   v_vDescLocalDestino
        FROM   PBL_LOCAL
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL = cCodDestino_in;

  --      DBMS_OUTPUT.PUT_LINE('v_vDescLocalDestino : '||v_vDescLocalDestino);

         FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                           v_cEmailErrorPtoVenta,
                           'ERROR AL GENERAR TRANSFERENCIA DELIVERY EN LOCAL : '||v_vDescLocalOrigen,
                           'ALERTA',
                           '<H1>ERROR AL GENERAR TRANSFERENCIA EN LOCAL '||v_vDescLocalOrigen||'</H1><BR>'||
                           '<i>Error al grabar la cabecera de la transferencia a generar</i><BR>'||
                           '<br>NRO PEDIDO TRANSFERENCIA : <b>'||cNumPed_in||'</b>'||
                           '<br>SECUENCIAL DE GRUPO      : <b>'||nSecGrupo||'</b>'||
                           '<br>LOCAL ORIGEN             : <b>'||v_vDescLocalOrigen||'</b>'||
                           '<br>LOCAL DESTINO            : <b>'||v_vDescLocalDestino||'</b>'||
                           '<BR><BR> FECHA : <B>'||to_char(SYSDATE,'dd/MM/yyyy HH24:MI:SS')||'</B>',
                           'jcallo',
                           FARMA_EMAIL.GET_EMAIL_SERVER,
                           TRUE);

           --DBMS_OUTPUT.PUT_LINE('ERRORR : DESPUES DE ENVIAR CORREO');
           RAISE_APPLICATION_ERROR(-20001,'ERROR AL GRABAR LA CABECERA DE LA TRANSFERENCIA A GENERAR');

          -- DBMS_OUTPUT.PUT_LINE('ANTES DE PONER RETURN ');
           RETURN '';--retorna valor si no hay errorres



  END;
PROCEDURE TRANSF_P_AGREGA_DETALLE(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in     IN CHAR,
                                  cNumNota_in      IN CHAR,
                                  cCodProd_in      IN CHAR,
                                  nValPrecUnit_in  IN NUMBER,
                                  nValPrecTotal_in IN NUMBER,

                                  nCantMov_in      IN NUMBER,
                                  vFecVecProd_in   IN VARCHAR2,
                                  vNumLote_in      IN VARCHAR2,
                                  cCodMotKardex_in IN CHAR,
                                  cTipDocKardex_in IN CHAR,
                                  vValFrac_in      IN NUMBER,
                                  vUsu_in          IN VARCHAR2,
                                  vTipDestino_in   IN CHAR,
                                  cCodDestino_in   IN CHAR,
                                  vIndFrac_in      IN CHAR DEFAULT 'N')
  AS
    v_dFechaNota DATE;
    nSec LGT_NOTA_ES_DET.SEC_DET_NOTA_ES%TYPE;
  	v_nValFrac LGT_NOTA_ES_DET.VAL_FRAC%TYPE;
  	v_vDescUnidVta LGT_NOTA_ES_DET.DESC_UNID_VTA%TYPE;
        v_nStkFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_cInd CHAR(1);

    v_cEmailErrorPtoVenta PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='joliva';
    v_vDescLocalOrigen VARCHAR2(120);
    v_vDescLocalDestino VARCHAR2(120);
  BEGIN
      SELECT FEC_NOTA_ES_CAB INTO v_dFechaNota
      FROM LGT_NOTA_ES_CAB
      WHERE COD_GRUPO_CIA =  cCodGrupoCia_in
            AND COD_LOCAL =  cCodLocal_in
            AND NUM_NOTA_ES = cNumNota_in;

      SELECT COUNT(SEC_DET_NOTA_ES)+1 INTO nSec
    	FROM LGT_NOTA_ES_DET
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND NUM_NOTA_ES = cNumNota_in;

      SELECT VAL_FRAC_LOCAL,NVL(TRIM(UNID_VTA),(SELECT DESC_UNID_PRESENT
                                                FROM LGT_PROD
                                                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                                AND COD_PROD = cCodProd_in )),STK_FISICO INTO

v_nValFrac,v_vDescUnidVta,v_nStkFisico
    	FROM LGT_PROD_LOCAL
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND COD_PROD = cCodProd_in;

      IF v_nValFrac <> vValFrac_in THEN
         RAISE_APPLICATION_ERROR(-20083,'LA FRACCION HA CAMBIADO PARA EL PRODUCTO:'||cCodProd_in||'. VUELVA A INGRESAR EL

PRODUCTO.');
      END IF;
      --AGREGADO 07/09/2006 ERIOS
      --VALIDA QUE LA FRACCION DEL LOCAL DESTINO, PERMITA ACEPTAR LA TRANSFERENCIA
      --SI HAY CONEXION CON MATRIZ
      IF vTipDestino_in = g_cTipoOrigenLocal THEN
      /*  EXECUTE IMMEDIATE 'BEGIN  PTOVENTA_TRANSF.GET_FRACCION_LOCAL@XE_000(:1,:2,:3,:4,:5,:6); END;'
                          USING cCodGrupoCia_in,cCodDestino_in,cCodProd_in,nCantMov_in,v_nValFrac,IN OUT v_cInd;
        --v_cInd := PTOVENTA_TRANSF.GET_FRACCION_LOCAL(cCodGrupoCia_in,cCodDestino_in,cCodProd_in,nCantMov_in,v_nValFrac);
       */
        IF vIndFrac_in != 'V' THEN
          RAISE_APPLICATION_ERROR(-20081,'ALGUNOS PRODUCTOS NO PUEDEN SER TRANSFERIDOS, DEBIDO A LA FRACCION ACTUAL DEL LOCAL

DESTINO.');
        END IF;
      END IF;

    	INSERT INTO LGT_NOTA_ES_DET(COD_GRUPO_CIA,COD_LOCAl,NUM_NOTA_ES,SEC_DET_NOTA_ES,

COD_PROD,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,FEC_NOTA_ES_DET,FEC_VCTO_PROD,NUM_LOTE_PROD, USU_CREA_NOTA_ES_DET,

VAL_FRAC,DESC_UNID_VTA)
    	VALUES(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,nSec,

cCodProd_in,nValPrecUnit_in,nValPrecTotal_in,nCantMov_in,v_dFechaNota,TO_DATE(vFecVecProd_in,'dd/MM/yyyy'),vNumLote_in,

vUsu_in, v_nValFrac,v_vDescUnidVta);

      --INSERTAR KARDEX
      PTOVENTA_TRANSF_DEL.TRANSF_P_GRABAR_KARDEX(cCodGrupoCia_in ,
                                      cCodLocal_in,
                                      cCodProd_in,
                                      cCodMotKardex_in,
                                      cTipDocKardex_in,
                                      cNumNota_in,
                                      v_nStkFisico,
                                      nCantMov_in*-1,
                                      v_nValFrac,
                                      v_vDescUnidVta,
                                      vUsu_in,
                                      COD_NUMERA_SEC_KARDEX,'');

      --DESCONTAR STOCK FISICO,STOCK COMPROMETIDO
      UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = vUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
                STK_FISICO = STK_FISICO - nCantMov_in
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND COD_PROD = cCodProd_in;
      --BORRAR REGISTRO RESPALDO




    EXCEPTION
    WHEN OTHERS THEN--SI OCURRE ALGUN ERROR--
        --DBMS_OUTPUT.PUT_LINE('ERROR :'||cCodLocal_in);
         --SE ENVIARA CORREO
        SELECT G.LLAVE_TAB_GRAL INTO v_cEmailErrorPtoVenta
        FROM PBL_TAB_GRAL  G
        WHERE G.ID_TAB_GRAL = 242;

        --DBMS_OUTPUT.PUT_LINE('v_cEmailErrorPtoVenta : '||v_cEmailErrorPtoVenta);

        SELECT COD_LOCAL ||' - '|| DESC_LOCAL INTO   v_vDescLocalOrigen
        FROM   PBL_LOCAL
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL = cCodLocal_in;

        --DBMS_OUTPUT.PUT_LINE('v_vDescLocalOrigen : '||v_vDescLocalOrigen);
        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO   v_vDescLocalDestino
        FROM   PBL_LOCAL
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL = cCodDestino_in;

  --      DBMS_OUTPUT.PUT_LINE('v_vDescLocalDestino : '||v_vDescLocalDestino);

         FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                           v_cEmailErrorPtoVenta,
                           'ERROR AL GENERAR TRANSFERENCIA DELIVERY EN LOCAL : '||v_vDescLocalOrigen,
                           'ALERTA',
                           '<H1>ERROR AL GENERAR TRANSFERENCIA EN LOCAL '||v_vDescLocalOrigen||'</H1><BR>'||
                           '<i>Error al grabar detalle de transferencia</i><BR>'||SQLERRM||
                           '<br>NUMERO DE NOTA           : <b>'||cNumNota_in||'</b>'||
                           '<br>LOCAL ORIGEN             : <b>'||v_vDescLocalOrigen||'</b>'||
                           '<br>LOCAL DESTINO            : <b>'||v_vDescLocalDestino||'</b>'||
                           '<BR><BR> FECHA : <B>'||to_char(SYSDATE,'dd/MM/yyyy HH24:MI:SS')||'</B>',
                           'DUBILLUZ',
                           FARMA_EMAIL.GET_EMAIL_SERVER,
                           TRUE);

           --DBMS_OUTPUT.PUT_LINE('ERRORR : DESPUES DE ENVIAR CORREO');
           RAISE_APPLICATION_ERROR(-20100,'ERROR AL GRABAR EL DETALLE DE LA TRANSFERENCIA S');

  END;

  PROCEDURE TRANSF_P_GRABAR_KARDEX(cCodGrupoCia_in 	   IN CHAR,
                                   cCodLocal_in    	   IN CHAR,
                                   cCodProd_in		   IN CHAR,
							                     cCodMotKardex_in 	   IN CHAR,
						   	                   cTipDocKardex_in     IN CHAR,
						   	                   cNumTipDoc_in  	   IN CHAR,
							                     nStkAnteriorProd_in  IN NUMBER,
							                     nCantMovProd_in  	   IN NUMBER,
							                     nValFrac_in		   IN NUMBER,
							                     cDescUnidVta_in	   IN CHAR,
							                     cUsuCreaKardex_in	   IN CHAR,
							                     cCodNumera_in	   	   IN CHAR,
							                     cGlosa_in			   IN CHAR DEFAULT NULL,
                                   cTipDoc_in IN CHAR DEFAULT NULL,
                                   cNumDoc_in IN CHAR DEFAULT NULL)
  IS
  v_cSecKardex LGT_KARDEX.SEC_KARDEX%TYPE;
  BEGIN
      v_cSecKardex :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,cCodNumera_in),10,'0','I' );
      INSERT INTO LGT_KARDEX(COD_GRUPO_CIA, COD_LOCAL, SEC_KARDEX, COD_PROD, COD_MOT_KARDEX,
                                     TIP_DOC_KARDEX, NUM_TIP_DOC, STK_ANTERIOR_PROD, CANT_MOV_PROD,
                                     STK_FINAL_PROD, VAL_FRACC_PROD, DESC_UNID_VTA, USU_CREA_KARDEX,DESC_GLOSA_AJUSTE,
                                     TIP_COMP_PAGO,NUM_COMP_PAGO)
      VALUES (cCodGrupoCia_in,	cCodLocal_in, v_cSecKardex, cCodProd_in, cCodMotKardex_in,
                                     cTipDocKardex_in, cNumTipDoc_in, nStkAnteriorProd_in, nCantMovProd_in,
                                     (nStkAnteriorProd_in + nCantMovProd_in), nValFrac_in, cDescUnidVta_in, cUsuCreaKardex_in,cGlosa_in,
                                     cTipDoc_in,cNumDoc_in);
	    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in, cUsuCreaKardex_in);
  END;





  /****************************************************************************/
  PROCEDURE TRANSF_P_CONFIRMAR(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumNotaEs_in IN CHAR, vIdUsu_in IN VARCHAR2)
  AS
    v_cTipOrigen LGT_NOTA_ES_CAB.TIP_ORIGEN_NOTA_ES%TYPE;

    v_cEmailErrorPtoVenta PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='joliva';
    v_vDescLocalOrigen VARCHAR2(120);
    v_vDescLocalDestino VARCHAR2(120);

    cCant number;
    cEstado varchar2(100);
  BEGIN
    SELECT TIP_ORIGEN_NOTA_ES
      INTO v_cTipOrigen
    FROM LGT_NOTA_ES_CAB
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND TIP_NOTA_ES = g_cTipoNotaSalida
          AND COD_ORIGEN_NOTA_ES = cCodLocal_in
          AND COD_DESTINO_NOTA_ES <> cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in
          ;

    IF v_cTipOrigen = g_cTipoOrigenMatriz THEN
      UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = vIdUsu_in, FEC_MOD_NOTA_ES_CAB = SYSDATE,
            EST_NOTA_ES_CAB = 'A'
      WHERE COD_GRUPO_CIA = cGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND TIP_NOTA_ES = g_cTipoNotaSalida
            AND EST_NOTA_ES_CAB = 'P'
            AND IND_NOTA_IMPRESA = 'S'
            AND NUM_NOTA_ES = cNumNotaEs_in;
    ELSIF v_cTipOrigen = g_cTipoOrigenLocal THEN


/*      select 'ind_impreso:'||IND_NOTA_IMPRESA||'-EST_NOTA_ES_CAB:'||EST_NOTA_ES_CAB
        into cEstado
        FROM LGT_NOTA_ES_CAB
       WHERE COD_GRUPO_CIA = cGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND TIP_NOTA_ES = g_cTipoNotaSalida
         AND NUM_NOTA_ES = cNumNotaEs_in;
*/

      --ACTUALIZA CABECERA
      UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = vIdUsu_in, FEC_MOD_NOTA_ES_CAB = SYSDATE,
            EST_NOTA_ES_CAB = 'C'
      WHERE COD_GRUPO_CIA = cGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND TIP_NOTA_ES = g_cTipoNotaSalida
            AND EST_NOTA_ES_CAB = 'P'
            AND IND_NOTA_IMPRESA = 'S'
            AND NUM_NOTA_ES = cNumNotaEs_in;

      --CREAR TEMPORALES
/*
      select count(1)
        into cCant
        FROM LGT_NOTA_ES_CAB
       WHERE COD_GRUPO_CIA = cGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND TIP_NOTA_ES = g_cTipoNotaSalida
         AND EST_NOTA_ES_CAB = 'C'
            --  AND IND_NOTA_IMPRESA = 'S'
         AND NUM_NOTA_ES = cNumNotaEs_in;

     -- cEstado

      select 'ind_impreso:'||IND_NOTA_IMPRESA||'-EST_NOTA_ES_CAB:'||EST_NOTA_ES_CAB
        into cEstado
        FROM LGT_NOTA_ES_CAB
       WHERE COD_GRUPO_CIA = cGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND TIP_NOTA_ES = g_cTipoNotaSalida
         AND NUM_NOTA_ES = cNumNotaEs_in;
*/

      INSERT INTO T_LGT_NOTA_ES_CAB(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,
      FEC_NOTA_ES_CAB,EST_NOTA_ES_CAB,TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES,
      CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA,
      RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS,
      TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION,
      USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB	,
-- kmoncada 26.08.2014 cambios para grabar proforma de transferencia delivery
      IND_NOTA_IMPRESA,FEC_PROCESO_SAP, TRA_CIA, TRA_COD_LOCAL, TRA_NUM_PROFORMA)
      SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,
      FEC_NOTA_ES_CAB,EST_NOTA_ES_CAB,TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES,
      CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA,
      RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS,
      TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION,
      USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB	,
-- kmoncada 26.08.2014 cambios para grabar proforma de transferencia delivery
      IND_NOTA_IMPRESA,FEC_PROCESO_SAP, TRA_CIA, TRA_COD_LOCAL, TRA_NUM_PROFORMA
      FROM LGT_NOTA_ES_CAB
      WHERE COD_GRUPO_CIA = cGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND TIP_NOTA_ES = g_cTipoNotaSalida
            AND EST_NOTA_ES_CAB = 'C'
            AND IND_NOTA_IMPRESA = 'S'
            AND NUM_NOTA_ES = cNumNotaEs_in;

      INSERT INTO T_LGT_GUIA_REM(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM,
      NUM_GUIA_REM,FEC_CREA_GUIA_REM,USU_CREA_GUIA_REM,FEC_MOD_GUIA_REM,USU_MOD_GUIA_REM,
      EST_GUIA_REM,NUM_ENTREGA,IND_GUIA_CERRADA,IND_GUIA_IMPRESA)
      SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM,
      NUM_GUIA_REM,FEC_CREA_GUIA_REM,USU_CREA_GUIA_REM,FEC_MOD_GUIA_REM,USU_MOD_GUIA_REM,
      EST_GUIA_REM,NUM_ENTREGA,IND_GUIA_CERRADA,IND_GUIA_IMPRESA
      FROM LGT_GUIA_REM
      WHERE COD_GRUPO_CIA = cGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_NOTA_ES = cNumNotaEs_in;


      INSERT INTO T_LGT_NOTA_ES_DET(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES,
      COD_PROD,SEC_GUIA_REM,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,VAL_FRAC,EST_NOTA_ES_DET,
      FEC_NOTA_ES_DET,DESC_UNID_VTA,FEC_VCTO_PROD,NUM_LOTE_PROD,CANT_ENVIADA_MATR,
      NUM_PAG_RECEP,IND_PROD_AFEC,USU_CREA_NOTA_ES_DET,FEC_CREA_NOTA_ES_DET,USU_MOD_NOTA_ES_DET,
      FEC_MOD_NOTA_ES_DET,FEC_PROCESO_SAP,POSICION,NUM_ENTREGA)
      SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES,
      COD_PROD,SEC_GUIA_REM,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,VAL_FRAC,EST_NOTA_ES_DET,
      FEC_NOTA_ES_DET,DESC_UNID_VTA,FEC_VCTO_PROD,NUM_LOTE_PROD,CANT_ENVIADA_MATR,
      NUM_PAG_RECEP,IND_PROD_AFEC,USU_CREA_NOTA_ES_DET,FEC_CREA_NOTA_ES_DET,USU_MOD_NOTA_ES_DET,
      FEC_MOD_NOTA_ES_DET,FEC_PROCESO_SAP,POSICION,NUM_ENTREGA
      FROM LGT_NOTA_ES_DET
      WHERE COD_GRUPO_CIA = cGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_NOTA_ES = cNumNotaEs_in;

       --RAISE_APPLICATION_ERROR(-20088,'creando temporales..'||cCant||'-'||cCodLocal_in||'-'||g_cTipoNotaSalida||'-'||cNumNotaEs_in||'--'||cEstado);

    END IF;


    EXCEPTION
    WHEN OTHERS THEN--SI OCURRE ALGUN ERROR--
        --DBMS_OUTPUT.PUT_LINE('ERROR :'||cCodLocal_in);
         --SE ENVIARA CORREO
        SELECT G.LLAVE_TAB_GRAL INTO v_cEmailErrorPtoVenta
        FROM PBL_TAB_GRAL  G
        WHERE G.ID_TAB_GRAL = 242;

        --DBMS_OUTPUT.PUT_LINE('v_cEmailErrorPtoVenta : '||v_cEmailErrorPtoVenta);

        SELECT COD_LOCAL ||' - '|| DESC_LOCAL INTO   v_vDescLocalOrigen
        FROM   PBL_LOCAL
        WHERE  COD_GRUPO_CIA = cGrupoCia_in
        AND    COD_LOCAL = cCodLocal_in;


         FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                           v_cEmailErrorPtoVenta,
                           'ERROR AL GENERAR TRANSFERENCIA DELIVERY EN LOCAL : '||v_vDescLocalOrigen,
                           'ALERTA',
                           '<H1>ERROR AL GENERAR TRANSFERENCIA EN LOCAL '||v_vDescLocalOrigen||'</H1><BR>'||
                           '<i>Error al confirmar Transferencia</i><BR>'||
                           '<br>NUMERO DE NOTA           : <b>'||cNumNotaEs_in||'</b>'||
                           '<br>LOCAL ORIGEN             : <b>'||v_vDescLocalOrigen||'</b>'||
                           '<BR><BR> FECHA : <B>'||to_char(SYSDATE,'dd/MM/yyyy HH24:MI:SS')||'</B>',
                           'jcallo',
                           FARMA_EMAIL.GET_EMAIL_SERVER,
                           TRUE);

           --DBMS_OUTPUT.PUT_LINE('ERRORR : DESPUES DE ENVIAR CORREO');
           RAISE_APPLICATION_ERROR(-20100,'ERROR AL CONFIRMAR TRANSFERENCIA!');

  END;




  PROCEDURE TRANSF_P_GENERA_GUIA(cGrupoCia_in   IN CHAR,
  										           cCodLocal_in   IN CHAR,
										             cNumNota_in    IN CHAR,
                                 nCantMAxDet_in IN NUMBER,
										             nCantItems_in  IN NUMBER,
										             cIdUsu_in      IN CHAR)
  AS
    v_nSecDet LGT_NOTA_ES_DET.SEC_DET_NOTA_ES%TYPE;
    j INTEGER:=1;
    CURSOR curDet IS
    SELECT D.SEC_DET_NOTA_ES--,L.NOM_LAB,P.DESC_PROD,P.DESC_UNID_PRESENT
    FROM LGT_NOTA_ES_DET D, LGT_PROD P, LGT_LAB L
    WHERE D.COD_GRUPO_CIA = cGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_NOTA_ES = cNumNota_in
          AND D.SEC_GUIA_REM IS NULL
          AND D.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND D.COD_PROD = P.COD_PROD
          AND P.COD_LAB = L.COD_LAB
    ORDER BY L.NOM_LAB,P.DESC_PROD,P.DESC_UNID_PRESENT;
  BEGIN
    FOR i IN 1..CEIL(nCantItems_in/nCantMAxDet_in)
    LOOP
      INSERT INTO LGT_GUIA_REM(COD_GRUPO_CIA,
              		 	  			   COD_LOCAL,
						                   NUM_NOTA_ES,
							                 SEC_GUIA_REM,
                               NUM_GUIA_REM,
							                 USU_CREA_GUIA_REM,
							                 FEC_CREA_GUIA_REM)
      VALUES (cGrupoCia_in,cCodLocal_in,cNumNota_in,i,SUBSTR(cNumNota_in,-5)||'-'||i,cIdUsu_in,SYSDATE);
      Dbms_Output.PUT_LINE('CABECERA' || i );
      /*WHILE j <= nCantMAxDet_in*i  AND j <= nCantItems_in
      LOOP
        UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET = cIdUsu_in,FEC_MOD_NOTA_ES_DET = SYSDATE,
              SEC_GUIA_REM = i
        WHERE COD_GRUPO_CIA = cGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND NUM_NOTA_ES = cNumNota_in
              AND SEC_DET_NOTA_ES = j;
        Dbms_Output.PUT_LINE('CABECERA' || i || ' DET' || j);
        j:=j+1;
      END LOOP;*/
      OPEN curDet;
      LOOP
        FETCH curDet INTO v_nSecDet;
        EXIT WHEN curDet%NOTFOUND;
        UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET = cIdUsu_in,FEC_MOD_NOTA_ES_DET = SYSDATE,
              SEC_GUIA_REM = i
        WHERE COD_GRUPO_CIA = cGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND NUM_NOTA_ES = cNumNota_in
              AND SEC_DET_NOTA_ES = v_nSecDet;
        j:=j+1;
        EXIT WHEN j > nCantMAxDet_in*i;
      END LOOP;
      CLOSE curDet;
    END LOOP;
  END;


  FUNCTION TRANF_F_CHAR_VAL_ESTADO_PED(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in 	      IN CHAR,
                                  cNumPedTransf_in    IN CHAR,
                                  nSecGrupo_in        IN NUMBER,
                                  cCodLocalOrigen_in  IN CHAR,
                                  cCodLocalDest_in    IN CHAR,
                                  nSecTrans_in        IN NUMBER
                                  )
  RETURN CHAR
  AS
  cCorrecto CHAR(1):='N';
  nCantidad NUMBER:=0;
  BEGIN

        SELECT C.EST_TRANS INTO cCorrecto
        FROM TMP_DEL_TRANS_CAB C,
            VTA_GRUPO_TRANS_PED G,
            PBL_LOCAL L
        WHERE C.COD_GRUPO_CIA     = cCodGrupoCia_in
          AND C.COD_LOCAL         = cCodLocal_in
          AND C.NUM_PED_VTA       = cNumPedTransf_in
          AND C.SEC_GRUPO         = nSecGrupo_in
          AND C.COD_LOCAL_ORIGEN  = cCodLocalOrigen_in
          AND C.COD_LOCAL_DESTINO = cCodLocalDest_in
          AND C.SEC_TRANS         = nSecTrans_in
          AND C.EST_TRANS = 'A'

          AND C.COD_GRUPO_CIA     = G.COD_GRUPO_CIA
          AND C.COD_LOCAL         = G.COD_LOCAL
          AND C.NUM_PED_VTA       = G.NUM_PED_VTA
          AND C.SEC_GRUPO         = G.SEC_GRUPO
          AND G.EST_GRUPO_TRANS   = 'A'

          AND L.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND L.COD_LOCAL     = C.COD_LOCAL_DESTINO;

          RETURN cCorrecto;

      EXCEPTION
          WHEN OTHERS THEN
               RAISE_APPLICATION_ERROR(-20666,'ERROR : EL PEDIDO NO SE ENCUENTRA ACTIVO!');

       RETURN cCorrecto;
  END;

end PTOVENTA_TRANSF_DEL;
/

