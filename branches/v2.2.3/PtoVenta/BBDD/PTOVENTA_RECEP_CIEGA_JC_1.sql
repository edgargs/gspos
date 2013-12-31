--------------------------------------------------------
--  DDL for Package Body PTOVENTA_RECEP_CIEGA_JC
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_RECEP_CIEGA_JC" is

   /*******************************************************************************/
   FUNCTION RECEP_F_LISTA_MERCADERIA_RANGO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in IN CHAR,
                                    cFecIni_in  IN CHAR,
                                    cFecFin_in  IN CHAR)
    RETURN FarmaCursor
    IS
      curDet FarmaCursor;
      BEGIN
        OPEN curDet FOR
        SELECT A.NRO_RECEP || 'Ã' ||
               TO_CHAR(A.FEC_RECEP, 'DD/MM/YYYY HH24:MI:SS') || 'Ã' ||
               NVL(A.USU_CREA_RECEP, ' ') || 'Ã' || NVL(A.CANT_GUIAS,0) || 'Ã' ||
               DECODE(A.ESTADO,
                      'E',
                      'EMITIDO',
                      'C',
                      'CONTEO',
                      'P',
                      'ESPERA',
                      'R',
                      'REVISADO',
                      'L',
                      'AFECT.PARCIAL',
                      'T',
                      'AFECT.TOTAL',
                      'V',
                      'VERIFICACION',
                      A.ESTADO) || 'Ã' ||
                      NVL(A.ESTADO, ' ') || 'Ã' ||
               --NVL(A.NRO_RECEP, ' ') || 'Ã' || --ordenar
               --jmiranda 19.03.2010 ordenar
               TO_CHAR(CASE NVL(a.CANT_GUIAS,0) WHEN 0 THEN 1 ELSE 0 END,'9000000')||
                      to_char(a.fec_crea_recep,'YYYYmmddHH24Miss') || 'Ã' || --ordena
               A.CANT_BULTOS || 'Ã' ||
               A.CANT_PRECINTOS
          FROM LGT_RECEP_MERCADERIA A
         WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
           AND A.COD_LOCAL = cCodLocal_in
           AND A.FEC_RECEP BETWEEN
               TO_DATE(cFecIni_in || ' 00:00:00', 'dd/MM/yyyy HH24:mi:ss') AND
               TO_DATE(cFecFin_in || ' 23:59:59', 'dd/MM/yyyy HH24:mi:ss')
           --JMIRANDA 09.02.2010
           AND (A.IND_AFEC_RECEP_CIEGA IN ('S')
           OR A.IND_AFEC_RECEP_CIEGA IS NULL)
         ORDER BY A.NRO_RECEP DESC;
     RETURN curDet ;
      END;

         /*******************************************************************************/
   FUNCTION RECEP_F_LISTA_MERCADERIA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in IN CHAR)
    RETURN FarmaCursor
    IS
      curDet FarmaCursor;
      NRO_DIAS VARCHAR2(1000);
      BEGIN
      --JMIRANDA 10.12.09  OBTENGO N DIAS PARA PODER LISTAR
      SELECT NVL(TRIM(LLAVE_TAB_GRAL),'0') INTO NRO_DIAS
        FROM PBL_TAB_GRAL
       WHERE ID_TAB_GRAL = '324';

        OPEN curDet FOR
        SELECT A.NRO_RECEP || 'Ã' ||
               TO_CHAR(A.FEC_RECEP, 'DD/MM/YYYY HH24:MI:SS') || 'Ã' ||
               NVL(A.USU_CREA_RECEP, ' ') || 'Ã' || NVL(A.CANT_GUIAS,0) || 'Ã' ||
               DECODE(A.ESTADO,
                      'E',
                      'EMITIDO',
                      'C',
                      'CONTEO',
                      'P',
                      'ESPERA',
                      'R',
                      'REVISADO',
                      'L',
                      'AFECT.PARCIAL',
                      'T',
                      'AFECT.TOTAL',
                      'V',
                      'VERIFICACION',
                      A.ESTADO) || 'Ã' ||
                      NVL(A.ESTADO, ' ') || 'Ã' ||
               --NVL(A.NRO_RECEP, ' ') || 'Ã' || --ordenar
               --jmiranda 19.03.2010 ordenar
               TO_CHAR(CASE NVL(a.CANT_GUIAS,0) WHEN 0 THEN 1 ELSE 0 END,'9000000')||
                      to_char(a.fec_crea_recep,'YYYYmmddHH24Miss') || 'Ã' || --ordena
               A.CANT_BULTOS || 'Ã' ||
               A.CANT_PRECINTOS
          FROM LGT_RECEP_MERCADERIA A
         WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
           AND A.COD_LOCAL = cCodLocal_in
           --AND TRUNC(A.FEC_RECEP) = TRUNC(SYSDATE)
           --JMIRANDA 10.12.2009
           AND TRUNC(A.FEC_RECEP) BETWEEN TRUNC(SYSDATE-NRO_DIAS) AND TRUNC(SYSDATE)
           --JMIRANDA 09.02.2010
           AND (A.IND_AFEC_RECEP_CIEGA IN ('S')
           OR A.IND_AFEC_RECEP_CIEGA IS NULL)
         ORDER BY A.NRO_RECEP DESC;
     RETURN curDet ;
      END;


  /* ************************************************************************* */
  FUNCTION RECEP_F_OBTIENE_GUIAS_PEND(cCodGrupoCia_in IN CHAR,
								 	               cCodLocal_in	  IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
     SELECT C.NUM_GUIA_REM|| 'Ã' ||
            C.NUM_ENTREGA|| 'Ã' ||
            TO_CHAR(A.FEC_NOTA_ES_CAB,' DD/MM/YYYY HH24:MI:SS')|| 'Ã' ||
            A.NUM_NOTA_ES|| 'Ã' ||
            DECODE(C.EST_GUIA_REM,'A','ACTIVO','N','ANULADO','P','PENDIENTE',C.EST_GUIA_REM)|| 'Ã' ||
            C.SEC_GUIA_REM|| 'Ã' ||
            COUNT(B.COD_PROD)|| 'Ã' ||
            SUM(B.CANT_MOV)
     FROM LGT_NOTA_ES_CAB A,
          LGT_NOTA_ES_DET B,
          LGT_GUIA_REM C
     WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
     AND A.COD_LOCAL=cCodLocal_in
     --AND A.FEC_NOTA_ES_CAB > SYSDATE -3
     AND C.NUM_GUIA_REM NOT IN (SELECT D.NUM_GUIA_REM
                               FROM LGT_RECEP_ENTREGA D
                               WHERE D.COD_GRUPO_CIA=A.COD_GRUPO_CIA
                               AND D.COD_LOCAL=A.COD_LOCAL)--sin contar los ya relacionados
     AND A.TIP_NOTA_ES='03'--GUIAS QUE VIENEN DE ALMACEN
     AND C.IND_GUIA_CERRADA='N'
     AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
     AND A.COD_LOCAL=B.COD_LOCAL
     AND A.NUM_NOTA_ES=B.NUM_NOTA_ES
     AND A.NUM_NOTA_ES=C.NUM_NOTA_ES
     AND B.COD_GRUPO_CIA=C.COD_GRUPO_CIA
     AND B.COD_LOCAL=C.COD_LOCAL
     AND B.NUM_ENTREGA=C.NUM_ENTREGA
     AND A.COD_LOCAL=C.COD_LOCAL
     AND C.TIPO_PED_REP NOT IN ('ZREG') --JMIRANDA 14.12.09
     /*AND (C.COD_GRUPO_CIA,C.COD_LOCAL,C.NUM_NOTA_ES)
          NOT IN
          (
          SELECT GR.COD_GRUPO_CIA,GR.COD_LOCAL,GR.NUM_NOTA_ES
          FROM   LGT_GUIA_REM GR
          WHERE  GR.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    GR.COD_LOCAL = cCodLocal_in
          AND    GR.TIPO_PED_REP = 'ZREG'
          )*/
     /* AND (C.COD_GRUPO_CIA,C.COD_LOCAL,C.Num_Entrega)
          NOT IN
          (
          SELECT GR.COD_GRUPO_CIA,GR.COD_LOCAL,GR.Num_Entrega
          FROM   LGT_GUIA_REM GR
          WHERE  GR.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    GR.COD_LOCAL = cCodLocal_in
          AND    GR.TIPO_PED_REP = 'ZREG'
          )*/
     GROUP BY  C.NUM_GUIA_REM, C.SEC_GUIA_REM,A.CANT_ITEMS, C.NUM_ENTREGA,A.FEC_NOTA_ES_CAB,A.NUM_NOTA_ES,C.EST_GUIA_REM
     ORDER BY A.FEC_NOTA_ES_CAB DESC;


    RETURN curVta;
  END;

    /* ************************************************************************* */
  FUNCTION RECEP_F_OBTIENE_GUIAS_ASOC(cCodGrupoCia_in IN CHAR,
								 	               cCodLocal_in	        IN CHAR,
                                 cNumIngreso_in       IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
     SELECT C.NUM_GUIA_REM|| 'Ã' ||
            C.NUM_ENTREGA|| 'Ã' ||
            A.FEC_NOTA_ES_CAB|| 'Ã' ||
            A.NUM_NOTA_ES|| 'Ã' ||
            DECODE(C.EST_GUIA_REM,'A','ACTIVO','N','ANULADO','P','PENDIENTE',C.EST_GUIA_REM)|| 'Ã' ||
            C.SEC_GUIA_REM|| 'Ã' ||
            COUNT(B.COD_PROD)|| 'Ã' ||
            SUM(B.CANT_MOV)
     FROM LGT_NOTA_ES_CAB A,
          LGT_NOTA_ES_DET B,
          LGT_GUIA_REM C,
          LGT_RECEP_ENTREGA D
     WHERE D.NRO_RECEP=cNumIngreso_in
     AND D.COD_GRUPO_CIA=A.COD_GRUPO_CIA
     AND D.COD_LOCAL=A.COD_LOCAL
     AND D.NUM_NOTA_ES=C.NUM_NOTA_ES
     AND D.SEC_GUIA_REM=C.SEC_GUIA_REM
     AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
     AND A.COD_LOCAL=B.COD_LOCAL
     AND A.NUM_NOTA_ES=B.NUM_NOTA_ES
     AND A.NUM_NOTA_ES=C.NUM_NOTA_ES
     AND B.NUM_ENTREGA=C.NUM_ENTREGA
     AND A.COD_LOCAL=C.COD_LOCAL
     GROUP BY  C.NUM_GUIA_REM,C.NUM_ENTREGA,A.FEC_NOTA_ES_CAB,A.NUM_NOTA_ES,C.EST_GUIA_REM, C.SEC_GUIA_REM
     ORDER BY A.FEC_NOTA_ES_CAB ASC;

    RETURN curVta;
  END;

   /****************************************************************************************************/

  FUNCTION RECEP_F_LISTA_DET_GUIA (cGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  cNumNota_in  IN CHAR,
                                  cNumGuia_in IN CHAR)
  RETURN FarmaCursor
  IS
  curDet FarmaCursor;
  BEGIN
  OPEN curDet FOR
  SELECT NVL(TO_CHAR(DET.COD_PROD),' ')	        	|| 'Ã' ||
        NVL(TO_CHAR(PROD.DESC_PROD),' ')			   	    || 'Ã' ||
        NVL(TO_CHAR(DESC_UNID_VTA),' ')						|| 'Ã' ||
        NVL(TO_CHAR(LAB.NOM_LAB),' ')						|| 'Ã' ||
        CANT_ENVIADA_MATR                       /*|| 'Ã' ||
        DECODE(FEC_MOD_NOTA_ES_DET,NULL,' ',TO_CHAR(CANT_MOV))                                || 'Ã' ||
        DECODE(FEC_MOD_NOTA_ES_DET,NULL,' ',TO_CHAR(CANT_ENVIADA_MATR - CANT_MOV))	|| 'Ã' ||
        NVL(TO_CHAR(IND_PROD_AFEC),' ')						|| 'Ã' ||
        (SELECT NUM_GUIA_REM
            FROM LGT_GUIA_REM
            WHERE COD_GRUPO_CIA = DET.COD_GRUPO_CIA
            AND COD_LOCAL = DET.COD_LOCAL
            AND NUM_NOTA_ES = DET.NUM_NOTA_ES
            AND SEC_GUIA_REM = DET.SEC_GUIA_REM)|| 'Ã' ||
        NVL(TO_CHAR(VAL_FRAC),' ')							|| 'Ã' ||
        NVL(TO_CHAR(STK_FISICO),' ')						|| 'Ã' ||
        NVL(TO_CHAR(SEC_DET_NOTA_ES),' ')				|| 'Ã' ||
        TO_CHAR( TO_CHAR(NVL(NUM_PAG_RECEP,0),g_cCodMatriz) || NVL(PROD.DESC_PROD,' ') )|| 'Ã' ||
        NVL(TO_CHAR(NUM_PAG_RECEP),' ')*/
    FROM  LGT_NOTA_ES_DET DET,
          LGT_LAB LAB,
          LGT_PROD_LOCAL PRODLOC,
          LGT_PROD PROD,
          LGT_GUIA_REM G
    WHERE DET.COD_GRUPO_CIA=cGrupoCia_in
    AND  DET.COD_LOCAL=cCodLocal_in
    AND  DET.NUM_NOTA_ES=cNumNota_in
    AND G.NUM_GUIA_REM = cNumGuia_in
    AND DET.COD_GRUPO_CIA=PRODLOC.COD_GRUPO_CIA
    AND DET.COD_LOCAL=PRODLOC.COD_LOCAL
    AND DET.COD_PROD=PRODLOC.COD_PROD
    AND PRODLOC.COD_GRUPO_CIA=PROD.COD_GRUPO_CIA
    AND PRODLOC.COD_PROD=PROD.COD_PROD
    AND PROD.COD_LAB=LAB.COD_LAB
    AND DET.COD_GRUPO_CIA = G.COD_GRUPO_CIA
    AND DET.COD_LOCAL = G.COD_LOCAL
    AND DET.NUM_NOTA_ES = G.NUM_NOTA_ES
    AND DET.SEC_GUIA_REM = G.SEC_GUIA_REM;

/*ORDER BY NVL(TO_CHAR(NUM_PAG_RECEP),' '),
	  	 NVL(TO_CHAR(PROD.DESC_PROD),' ');*/

  RETURN curDet;
  END;

   /****************************************************************************/
   FUNCTION RECEP_P_NEW_RECEPCION(cCodGrupoCia_in IN CHAR,
                                    cCodLocal      IN CHAR,
                                    cIdUsu_in      IN CHAR,
                                    cCantGuias     IN NUMBER,
                                    cNombTransp    IN CHAR,
                                    cHoraTransp    IN CHAR,
                                    cPlaca         IN CHAR,
                                    nCantBultos    IN NUMBER,
                                    nCantPrecintos IN NUMBER,
                                    cGlosa IN VARCHAR2 DEFAULT '')
  RETURN VARCHAR2
  IS
  v_nCant	NUMBER;
  nroRecep NUMBER;
  vRecep   VARCHAR2(15);
  v_ip VARCHAR2(20);
  HoraLlegada DATE;
  BEGIN

  --HoraLlegada:=TO_CHAR(SYSDATE,'DD/MM/YYYY')||' '||cHoraTransp;
  HoraLlegada:=TO_DATE(TRIM(cHoraTransp),'dd/MM/yyyy HH24:mi:ss');

          SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip
        FROM DUAL;

     nroRecep := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal, '074');
     vRecep :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(nroRecep,10,0,'I');

    INSERT INTO LGT_RECEP_MERCADERIA(
             COD_GRUPO_CIA,
             COD_LOCAL,
             NRO_RECEP,
             FEC_RECEP,
             CANT_GUIAS,
             ESTADO,
             USU_CREA_RECEP,
             FEC_CREA_RECEP,
             USU_MOD_RECEP,
             FEC_MOD_RECEP,
             IP_CREA_RECEP,
             IP_MOD_RECEP,
             NOMBRE_TRANS,
             HORA_LLEGADA,
             PLACA_UND,
             CANT_BULTOS,
             CANT_PRECINTOS,
             GLOSA) --JMIRANDA 05.03.2010 GLOSA
      VALUES (cCodGrupoCia_in,
              cCodLocal,
              vRecep,
              --SYSDATE,
              HoraLlegada,
              cCantGuias,
              'E',--EMITIDO
              cIdUsu_in,
              SYSDATE,
              NULL,NULL,
              v_ip,NULL,
              cNombTransp,HoraLlegada,cPlaca,nCantBultos,nCantPrecintos,
              cGlosa);

            Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal,'074',cIdUsu_in);

       RETURN vRecep;
  EXCEPTION
     WHEN DUP_VAL_ON_INDEX THEN
        RAISE_APPLICATION_ERROR(-20002,'EL NUMERO DE INGRESO YA EXISTE. VERIFIQUE!!!');

  END;


   /****************************************************************************/
/*   PROCEDURE RECEP_P_AGREGA_GUIAS_RECEPCION(cCodGrupoCia_in IN CHAR,
                                            cCodLocal       IN CHAR,
                                            cIdUsu_in       IN CHAR,
                                            cNumRecep_in    IN CHAR,
                                            cNumNotaEs_in   IN CHAR,
                                            cNumGuiaRem_in  IN CHAR,
                                            cNumEntrega_in  IN CHAR,
                                            cSecGuia        IN NUMBER)

  IS
  CANT NUMBER;
  cod_local CHAR(3);
  BEGIN

 \* SELECT A.COD_LOCAL INTO cod_local
  FROM LGT_RECEP_ENTREGA A
  WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
  AND A.COD_LOCAL=cCodLocal FOR UPDATE;*\

  SELECT COUNT(*) INTO CANT
  FROM LGT_RECEP_ENTREGA B
  WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
  AND B.COD_LOCAL=cod_local
  AND B.NUM_GUIA_REM=cNumGuiaRem_in;

 -- IF(CANT=0) THEN

    INSERT INTO LGT_RECEP_ENTREGA (COD_GRUPO_CIA
                              ,COD_LOCAL
                              ,NRO_RECEP
                              ,NUM_NOTA_ES
                              ,NUM_GUIA_REM
                              ,NUM_ENTREGA
                              ,SEC_GUIA_REM
                              ,USU_CREA_REC_ENT
                              ,FEC_CREA_REC_ENT
                              ,USU_MOD_REC_ENT
                              ,FEC_MOD_REC_ENT)
      VALUES (cCodGrupoCia_in,
              cCodLocal,
              cNumRecep_in,
              cNumNotaEs_in,
              cNumGuiaRem_in,
              cNumEntrega_in,
              cSecGuia,
              cIdUsu_in,
              SYSDATE,
              NULL,NULL);
   \*ELSE
    RAISE_APPLICATION_ERROR(-20004,'LA GUIA YA ESTA ASOCIADA A UNA RECEPCION. VERIFIQUE!!! -->'||cNumGuiaRem_in);
   END IF;*\


  EXCEPTION
     WHEN DUP_VAL_ON_INDEX THEN
        RAISE_APPLICATION_ERROR(-20004,'EXISTEN GUIAS YA ASOCIADAS. VERIFIQUE!!!');

  END;
  */
   PROCEDURE RECEP_P_AGREGA_GUIAS_RECEPCION(cCodGrupoCia_in IN CHAR,
                                            cCodLocal       IN CHAR,
                                            cIdUsu_in       IN CHAR,
                                            cNumRecep_in    IN CHAR,
                                            cNumNotaEs_in   IN CHAR,
                                            cNumGuiaRem_in  IN CHAR,
                                            cNumEntrega_in  IN CHAR,
                                            cSecGuia        IN NUMBER)

  IS
  CANT NUMBER;
  cod_local CHAR(3);
  cFechaRecepcion date;--fecha recepcion
  cFechaCreaEntrega date;--fecha creacion entrega
  BEGIN

--FECHA_RECEPCION
  select M.FEC_RECEP
  into cFechaRecepcion
  from LGT_RECEP_MERCADERIA M
  where M.NRO_RECEP=cNumRecep_in
  and M.COD_LOCAL=cCodLocal
  and M.COD_GRUPO_CIA=cCodGrupoCia_in;

  --FCHA CREACION ENTREGA
  select G.FEC_CREA_GUIA_REM
  into cFechaCreaEntrega
  from LGT_GUIA_REM G
  where G.NUM_GUIA_REM=cNumGuiaRem_in
  and G.COD_LOCAL=cCodLocal
  and G.COD_GRUPO_CIA=cCodGrupoCia_in;

  IF(cFechaRecepcion >= cFechaCreaEntrega) THEN
  --   IF TRUE THEN
    INSERT INTO LGT_RECEP_ENTREGA (COD_GRUPO_CIA
                              ,COD_LOCAL
                              ,NRO_RECEP
                              ,NUM_NOTA_ES
                              ,NUM_GUIA_REM
                              ,NUM_ENTREGA
                              ,SEC_GUIA_REM
                              ,USU_CREA_REC_ENT
                              ,FEC_CREA_REC_ENT
                              ,USU_MOD_REC_ENT
                              ,FEC_MOD_REC_ENT)
      VALUES (cCodGrupoCia_in,
              cCodLocal,
              cNumRecep_in,
              cNumNotaEs_in,
              cNumGuiaRem_in,
              cNumEntrega_in,
              cSecGuia,
              cIdUsu_in,
              SYSDATE,
              NULL,NULL);
   ELSE
   RAISE_APPLICATION_ERROR(-20010,'NO SE PUEDE ASOCIAR LA ENTREGA '|| cNumEntrega_in ||' PORQUE ' ||chr(10)||'LA FECHA DE RECEPCION :    '||to_char(cFechaRecepcion,'dd/mm/yyyy hh24:mi:ss')||'     ES MENOR A '||chr(10)||'LA FECHA DE ENTREGA     :    '||to_char(cFechaCreaEntrega,'dd/mm/yyyy hh24:mi:ss'));
   END IF;




  END;

  /*PROCEDURE RECEP_P_AGREGA_GUIAS_RECEPCION(cCodGrupoCia_in IN CHAR,
                                            cCodLocal       IN CHAR,
                                            cIdUsu_in       IN CHAR,
                                            cNumRecep_in    IN CHAR,
                                            cNumNotaEs_in   IN CHAR,
                                            cNumGuiaRem_in  IN CHAR,
                                            cNumEntrega_in  IN CHAR,
                                            cSecGuia        IN NUMBER)

  IS
  cod_local CHAR(3);
  cFechaRecepcion date;--fecha recepcion
  cFechaCreaEntrega date;--fecha creacion entrega
  BEGIN

  --FECHA_RECEPCION
  select M.FEC_RECEP
  into cFechaRecepcion
  from LGT_RECEP_MERCADERIA M
  where M.NRO_RECEP=cNumRecep_in
  and M.COD_LOCAL=cCodLocal
  and M.COD_GRUPO_CIA=cCodGrupoCia_in;

  --FCHA CREACION ENTREGA
  select G.FEC_CREA_GUIA_REM
  into cFechaCreaEntrega
  from LGT_GUIA_REM G
  where G.NUM_GUIA_REM=cNumGuiaRem_in
  and G.COD_LOCAL=cCodLocal
  and G.COD_GRUPO_CIA=cCodGrupoCia_in;

   IF(cFechaRecepcion => cFechaCreaEntrega) THEN

    INSERT INTO LGT_RECEP_ENTREGA (COD_GRUPO_CIA
                              ,COD_LOCAL
                              ,NRO_RECEP
                              ,NUM_NOTA_ES
                              ,NUM_GUIA_REM
                              ,NUM_ENTREGA
                              ,SEC_GUIA_REM
                              ,USU_CREA_REC_ENT
                              ,FEC_CREA_REC_ENT
                              ,USU_MOD_REC_ENT
                              ,FEC_MOD_REC_ENT)
      VALUES (cCodGrupoCia_in,
              cCodLocal,
              cNumRecep_in,
              cNumNotaEs_in,
              cNumGuiaRem_in,
              cNumEntrega_in,
              cSecGuia,
              cIdUsu_in,
              SYSDATE,
              NULL,NULL);
   ELSE
    RAISE_APPLICATION_ERROR(-20010,'LA ENTREGA '|| cNumEntrega_in ||'NO SE PUEDE ASOCIAR PORQUE LA FECHA DE RECEPCION'||cFechaRecepcion||' ES MENOR A LA FECHA DE ENTREGA '||cFechaCreaEntrega);
   END IF;

  END;
*/
    /* ************************************************************************* */
  FUNCTION RECEP_F_OBTIENE_GUIAS_RECEP(cCodGrupoCia_in IN CHAR,
								 	                       cCodLocal_in	  IN CHAR,
                                         cNumRecep_in IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
     SELECT C.NUM_GUIA_REM|| 'Ã' ||
            C.NUM_ENTREGA|| 'Ã' ||
            TO_CHAR(A.FEC_NOTA_ES_CAB,'DD/MM/YYYY')|| 'Ã' ||
            A.NUM_NOTA_ES|| 'Ã' ||
            C.SEC_GUIA_REM|| 'Ã' ||
            COUNT(B.COD_PROD)|| 'Ã' ||
            SUM(B.CANT_MOV)|| 'Ã' ||
            DECODE(C.IND_GUIA_CERRADA,'S','AFECTADO','N','PENDIENTE',C.IND_GUIA_CERRADA)
     FROM LGT_NOTA_ES_CAB A,
          LGT_NOTA_ES_DET B,
          LGT_GUIA_REM C,
          LGT_RECEP_ENTREGA D
     WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
     AND A.COD_LOCAL=cCodLocal_in
     AND D.NRO_RECEP=cNumRecep_in
     AND D.NUM_NOTA_ES=A.NUM_NOTA_ES
     AND D.NUM_GUIA_REM=C.NUM_GUIA_REM
     AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
     AND A.COD_LOCAL=B.COD_LOCAL
     AND A.NUM_NOTA_ES=B.NUM_NOTA_ES
     AND A.NUM_NOTA_ES=C.NUM_NOTA_ES
     AND B.NUM_ENTREGA=C.NUM_ENTREGA
     AND A.COD_LOCAL=C.COD_LOCAL
     GROUP BY  C.NUM_GUIA_REM, C.SEC_GUIA_REM,A.CANT_ITEMS, C.NUM_ENTREGA,A.FEC_NOTA_ES_CAB,A.NUM_NOTA_ES,C.IND_GUIA_CERRADA
     ORDER BY A.FEC_NOTA_ES_CAB ASC;

    RETURN curVta;
  END;


  /*****************************************************************************/
  FUNCTION REPCEP_VALIDA_IP(cCodGrupoCia_in    IN CHAR,
                      		  cCod_Local_in      IN CHAR)
   RETURN CHAR
   IS
     v_ip VARCHAR2(20);
     v_ip2 VARCHAR2(20);
     ind CHAR(1);
   BEGIN


        SELECT NVL(A.IP_RECEPCION,'N') INTO v_ip
        FROM PBL_LOCAL A
        WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
        AND A.COD_LOCAL=cCod_Local_in;

       SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip2
        FROM DUAL;

    IF(v_ip=v_ip2 or v_ip='N')THEN
         ind:='S';
    ELSE
         ind:='N';
    END IF;

     RETURN ind;

   EXCEPTION
     WHEN NO_DATA_FOUND THEN
         ind:='N';
         RETURN  ind;
   END;

   /***************************************************************************/
   FUNCTION RECEP_VERIFICA_ROL_USU(cCodGrupoCia_in  IN CHAR,
                             cCodLocal_in     IN CHAR,
                             vSecUsu_in       IN CHAR,
                             cCodRol_in       IN CHAR)
    RETURN CHAR
    IS
    vresultado  CHAR(1);
    vcontador   NUMBER;
    BEGIN

    BEGIN
    SELECT COUNT(*) INTO vcontador
    FROM  PBL_ROL_USU X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.COD_LOCAL=cCodLocal_in
    AND X.SEC_USU_LOCAL=vSecUsu_in
    AND X.COD_ROL=cCodRol_in;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
           vcontador :=0;
    END;

    IF vcontador > 0 THEN
      vresultado := 'S';
    ELSE
      vresultado := 'N';
    END IF;
    RETURN vresultado;

    END;

   /****************************************************************************/
   FUNCTION RECEP_F_INS_TRANSPORTISTA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal      IN CHAR,
                                      cCantGuias     IN NUMBER,
                                      cIdUsu_in      IN CHAR,
                                      cNombTransp    IN CHAR,
                                      cPlaca         IN CHAR,
                                      nCantBultos    IN NUMBER,
                                      nCantPrecintos IN NUMBER,
                                      cGlosa IN VARCHAR2 DEFAULT '',
                                      cSecUsu_in     IN CHAR)
                                      RETURN VARCHAR2
  IS
  v_nCant	NUMBER;
  nroRecep NUMBER;
  vRecep   VARCHAR2(15);
  v_ip VARCHAR2(20);
  --vIndRecepCiega PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE := 'S';
   vIndRecepCiega CHAR(1) := 'S';
  BEGIN

  SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip
    FROM DUAL;

  SELECT (L.LLAVE_TAB_GRAL)
    INTO vIndRecepCiega
    FROM PBL_TAB_GRAL L
   WHERE L.ID_TAB_GRAL = 326;
    DBMS_OUTPUT.put_line('vIndRecepCiega: '||vIndRecepCiega);
    nroRecep := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal, '074');
     vRecep :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(nroRecep,10,0,'I');

    INSERT INTO LGT_RECEP_MERCADERIA(
             COD_GRUPO_CIA,
             COD_LOCAL,
             NRO_RECEP,
             FEC_RECEP,
             CANT_GUIAS,
             ESTADO,
             USU_CREA_RECEP,
             FEC_CREA_RECEP,
             IP_CREA_RECEP,
             NOMBRE_TRANS,
             HORA_LLEGADA,
             PLACA_UND,
             CANT_BULTOS,
             CANT_PRECINTOS,
             IND_AFEC_RECEP_CIEGA,
             GLOSA,
             sec_usu_crea ) --JMIRANDA 05.03.2010 GLOSA
      VALUES (cCodGrupoCia_in,
              cCodLocal,
              vRecep,
              --trunc(SYSDATE,'MI'),
              SYSDATE,
              cCantGuias,
              'E',--EMITIDO
              cIdUsu_in,
              SYSDATE,
              v_ip,
              cNombTransp,SYSDATE,cPlaca,nCantBultos,nCantPrecintos,
              vIndRecepCiega, --'S' RECEP_CIEGA, 'N' ANTIGUA
              cGlosa,
              cSecUsu_in);

            Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal,'074',cIdUsu_in);

  RETURN vRecep;

  EXCEPTION
     WHEN DUP_VAL_ON_INDEX THEN
        RAISE_APPLICATION_ERROR(-20002,'EL NUMERO DE INGRESO YA EXISTE. VERIFIQUE!!!');

  END;

  /* ****************************************************************/
    FUNCTION RECEP_F_VAR2_IMP_VOUCHER(cGrupoCia_in  IN CHAR,
                                              cCodLocal_in  IN CHAR,
                                              cNroRecep_in IN VARCHAR2) RETURN VARCHAR2
    IS
    vMsg_out VARCHAR2(32767) := '';
    vMsg_1 VARCHAR2(32767) := '';
    vCab_TIPO VARCHAR2(10000) := '';


    vTransp VARCHAR(500);
    vTipo CHAR(1);
    vEstado CHAR(1);
    vCantBultos LGT_RECEP_MERCADERIA.CANT_BULTOS%TYPE;
    vCantPrecintos LGT_RECEP_MERCADERIA.CANT_PRECINTOS%TYPE;
    vFechaCrea LGT_RECEP_MERCADERIA.FEC_CREA_RECEP%TYPE ;

    vLocal VARCHAR2(300) := '';
    vUsu_Crea VARCHAR2(500) := '';
    vSec_Usu_crea lgt_recep_mercaderia.sec_usu_crea%TYPE;
    vLogin VARCHAR2(30) := '';
    vPlaca VARCHAR2(30) := '';
    vGlosa LGT_RECEP_MERCADERIA.GLOSA%TYPE;
   BEGIN

   SELECT O.COD_LOCAL||' '||O.DESC_CORTA_LOCAL
     INTO vLocal
     FROM PBL_LOCAL O
    WHERE O.COD_GRUPO_CIA = cGrupoCia_in
      AND O.COD_LOCAL = cCodLocal_in;

   SELECT M.FEC_CREA_RECEP, M.NOMBRE_TRANS, M.CANT_BULTOS, M.CANT_PRECINTOS, M.USU_CREA_RECEP,
          M.SEC_USU_CREA, M.PLACA_UND,M.GLOSA
     INTO vFechaCrea, vTransp, vCantBultos, vCantPrecintos, vLogin, vSec_Usu_crea, vPlaca,vGlosa
     FROM LGT_RECEP_MERCADERIA M
    WHERE M.COD_GRUPO_CIA = cGrupoCia_in
      AND M.COD_LOCAL = cCodLocal_in
      AND M.NRO_RECEP = cNroRecep_in;

   IF trim(vSec_Usu_crea) IS NULL THEN
        vUsu_Crea := vLogin;
   ELSE
     SELECT L.NOM_USU||' '||L.APE_PAT||' '||L.APE_MAT
       INTO vUsu_Crea
       FROM PBL_USU_LOCAL L
      WHERE L.COD_GRUPO_CIA = cGrupoCia_in
        AND L.COD_LOCAL = cCodLocal_in
        AND L.SEC_USU_LOCAL = vSec_Usu_crea;
   END IF;



       vCab_Tipo := '<tr>'||
                    '<td align="center"><h2>'||vLocal||'</h2>'||
                    '</td></tr>';
/*                    '<tr>'||
                    '<td align="center"><h2>ASIGNACI&Oacute;N</h2>'||
                    '</td></tr>';*/
          vMsg_1 := '<tr><td class="style9">'||
                    'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
                    'Yo, '||vUsu_Crea||', Adm. Local&nbsp <b>CONFIRMO LA RECEPCI&Oacute;N DE LA MERCADER&Iacute;A </b> '||
                    'entregada por el Sr. Transportista, '||vTransp||', en la unidad con Placa: '||vPlaca||'.<br>'||
                    'La recepci&oacute;n consta de: '||vCantBultos||' Bulto(s)'||
                    ' y '||vCantPrecintos||' Precinto(s).<br>'||
                    'Glosa: '||vGlosa||'<br>'||
                    'Firma Transportista:&nbsp; ____________________<br><br>'||
                    'Firma Adm. Local:&nbsp;&nbsp;&nbsp;&nbsp;____________________<br><br>'||
--                    '_________________________________________________'||
                    '_'||
                    '</td></tr>';

  /*  IF vTipo = 'A' THEN
       vCab_Tipo := '<tr>'||
                    '<td align="center"><h2>'||vLocal||'</h2>'||
                    '</td></tr>'||
                    '<tr>'||
                    '<td align="center"><h2>ASIGNACI&Oacute;N</h2>'||
                    '</td></tr>';
       IF vEstado = 'A' THEN
          vMsg_1 := '<tr><td class="style9">'||
                    'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
                    'Yo, '||vUsuOriLargo||', Adm. Local,&nbsp <b>ASIGNO</b> '||
--                    '<b>ASIGNO.</b><br>'||
                    'Al Técnico/Cajero, '||vUsuDestLargo||'.<br>'||
                    'El Importe de: S/. '|| vMonto||'  Nuevos Soles.<br><br>'||
                    'Firma Téc./Cajero:&nbsp; ____________________<br><br>'||
                    'Firma Adm. Local:&nbsp;&nbsp;____________________<br><br>'||
--                    '_________________________________________________'||
                    '&nbsp'||
                    '</td></tr>';
       ELSIF vEstado = 'E' THEN
          vMsg_1 := '<tr><td class="style9">'||
                    'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
                    'Yo, '||vUsuOriLargo||', Adm. Local,&nbsp <b>ASIGNO</b> '||
--                    '<b>ASIGNO.</b><br>'||
                    'Al Técnico/Cajero, '||vUsuDestLargo||'.<br>'||
                    'El Importe de: S/. '|| vMonto||'  Nuevos Soles.<br><br>'||
                    'Firma Téc./Cajero:&nbsp; ____________________<br><br>'||
                    'Firma Adm. Local:&nbsp;&nbsp;____________________<br><br>'||
--                    '_________________________________________________'||
                    '&nbsp'||
                    '</td></tr>';
       END IF;
    ELSIF vTipo = 'D' THEN
       vCab_Tipo := '<tr>'||
                    '<td align="center"><h2>'||vLocal||'</h2>'||
                    '</td></tr>'||
                    '<tr>'||
                    '<td align="center"><h2>DEVOLUCI&Oacute;N</h2>'||
                    '</td>'||
                    '</tr>';
       IF vEstado = 'A' THEN
          vMsg_1 := '<tr><td class="style9">'||
                    'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
                    'Yo, '||vUsuOriLargo||', Técnico/Cajero,&nbsp <b>DEVUELVO</b> '||
--                    '<b>DEVUELVO.</b><br>'||
                    'Al Adm. Local, '||vUsuDestLargo||'.<br>'||
                    'El Importe de: S/. '|| vMonto||'  Nuevos Soles.<br><br>'||
                    'Firma Téc./Cajero:&nbsp; ____________________<br><br>'||
                    'Firma Adm. Local:&nbsp;&nbsp;____________________<br><br>'||
                    '&nbsp'||
                    '</td></tr>';
       ELSIF vEstado = 'E' THEN
          vMsg_1 := '<tr><td class="style9">'||
                    'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
                    'Yo, '||vUsuOriLargo||', Técnico/Cajero,&nbsp <b>DEVUELVO</b> '||
--                    '<b>DEVUELVO.</b><br>'||
                    'Al Adm. Local, '||vUsuDestLargo||'.<br>'||
                    'El Importe de S/. '|| vMonto||'  Nuevos Soles.<br><br>'||
                    'Firma Téc./Cajero:&nbsp; ____________________<br><br>'||
                    'Firma Adm. Local:&nbsp;&nbsp;____________________<br><br>'||
                    --'_________________________________________________'||
                    '&nbsp'||
                    '</td></tr>';
       END IF;

    END IF;
*/
    vMsg_out := C_INICIO_MSG || vCab_Tipo || vMsg_1 ||
              C_FIN_MSG;

    RETURN vMsg_out;

    END;

         /*******************************************************************************/
   FUNCTION RECEP_F_LISTA_TRANSP(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in IN CHAR)
    RETURN FarmaCursor
    IS
      curDet FarmaCursor;
      NRO_DIAS VARCHAR2(1000);
      vTipoLocal PBL_LOCAL.IND_LOCAL_PROV%TYPE := 'N';
      BEGIN

        SELECT L.IND_LOCAL_PROV INTO  vTipoLocal
          FROM PBL_LOCAL L
         WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
           AND L.COD_LOCAL = cCodLocal_in;

         --JMIRANDA 10.12.09  OBTENGO N DIAS PARA PODER LISTAR
      SELECT NVL(TRIM(LLAVE_TAB_GRAL),'0') INTO NRO_DIAS
        FROM PBL_TAB_GRAL
       WHERE ID_TAB_GRAL = '324';

        IF vTipoLocal = 'N' THEN
         SELECT NVL(TRIM(LLAVE_TAB_GRAL), '0')
           INTO NRO_DIAS
           FROM PBL_TAB_GRAL
          WHERE ID_TAB_GRAL = '344';
        ELSE
         SELECT NVL(TRIM(LLAVE_TAB_GRAL), '0')
           INTO NRO_DIAS
           FROM PBL_TAB_GRAL
          WHERE ID_TAB_GRAL = '345';
        END IF;

        OPEN curDet FOR
          SELECT m.nro_recep || 'Ã' ||
                 TO_CHAR(M.FEC_RECEP, 'DD/MM/YYYY HH24:MI:SS') || 'Ã' || --FECHA
                 m.usu_crea_recep  || 'Ã' ||  --USUARIO
                 m.Nombre_Trans  || 'Ã' || --TRANSPORTISTA
                 m.placa_und  || 'Ã' ||
                 DECODE(m.ESTADO,
                                'E',
                                'EMITIDO',
                                'C',
                                'CONTEO',
                                'P',
                                'ESPERA',
                                'R',
                                'REVISADO',
                                'T',
                                'AFECT.TOTAL',
                                'V',
                                'VERIFICACION',
                                m.ESTADO) || 'Ã' || --ESTADO
                                NVL(M.CANT_GUIAS,0) || 'Ã' ||
                 m.estado || 'Ã' || --CODIGO_ESTADO
                 --TRIM(TO_CHAR(m.NRO_RECEP,'9999999999')) --ordena
                 TO_CHAR(CASE NVL(M.CANT_GUIAS,0) WHEN 0 THEN 1 ELSE 0 END,'9000000')||
                      to_char(m.fec_crea_recep,'YYYYmmddHH24Miss')   --ordena
            FROM lgt_recep_mercaderia m
           WHERE m.cod_grupo_cia = cCodGrupoCia_in
             AND m.cod_local = cCodLocal_in
             AND m.fec_crea_recep  BETWEEN Trunc(SYSDATE)-NRO_DIAS AND SYSDATE;
--             ORDER BY TO_CHAR(m.NRO_RECEP,'9999999999')  DESC;
     RETURN curDet ;
      END;

         /*******************************************************************************/
   FUNCTION RECEP_F_LISTA_TRANSP_RANGO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in IN CHAR,
                                    cFecIni_in  IN CHAR,
                                    cFecFin_in  IN CHAR)
    RETURN FarmaCursor
    IS
      curDet FarmaCursor;

      BEGIN

        OPEN curDet FOR
          SELECT m.nro_recep || 'Ã' ||
                 TO_CHAR(M.FEC_RECEP, 'DD/MM/YYYY HH24:MI:SS') || 'Ã' || --FECHA
                 m.usu_crea_recep  || 'Ã' ||  --USUARIO
                 m.Nombre_Trans  || 'Ã' || --TRANSPORTISTA
                 m.placa_und  || 'Ã' ||
                 DECODE(m.ESTADO,
                                'E',
                                'EMITIDO',
                                'C',
                                'CONTEO',
                                'P',
                                'ESPERA',
                                'R',
                                'REVISADO',
                                'T',
                                'AFECT.TOTAL',
                                'V',
                                'VERIFICACION',
                                m.ESTADO) || 'Ã' || --ESTADO
                                NVL(M.CANT_GUIAS,0) || 'Ã' ||
                 m.estado || 'Ã' || --CODIGO_ESTADO
                 TO_CHAR(CASE NVL(M.CANT_GUIAS,0) WHEN 0 THEN 1 ELSE 0 END,'9000000')||
                      to_char(m.fec_crea_recep,'YYYYmmddHH24Miss')  --ordena

            FROM lgt_recep_mercaderia m
           WHERE m.cod_grupo_cia = cCodGrupoCia_in
             AND m.cod_local = cCodLocal_in
             AND m.fec_crea_recep  BETWEEN TO_DATE(cFecIni_in||' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                                   AND TO_DATE(cFecFin_in||' 23:59:59','DD/MM/YYYY HH24:MI:SS')
             -- ORDER BY TO_CHAR(m.NRO_RECEP,'9999999999')  DESC
             ;
     RETURN curDet ;
      END;

    /*******************************************************************************/
    FUNCTION RECEP_F_CANT_GUIAS (cCod_Grupo_cia_in CHAR,
                                 cCod_Local_in CHAR,
                                 cNro_Recepcion CHAR)
                                 RETURN NUMBER
    IS
      vCantGuias LGT_RECEP_MERCADERIA.CANT_GUIAS%TYPE := 0;
    BEGIN
      BEGIN
      SELECT M.CANT_GUIAS
        INTO vCantGuias
        FROM LGT_RECEP_MERCADERIA M
       WHERE M.COD_GRUPO_CIA = cCod_Grupo_cia_in
         AND M.COD_LOCAL = cCod_Local_in
         AND M.NRO_RECEP = cNro_Recepcion;
      EXCEPTION
       WHEN NO_DATA_FOUND THEN
        vCantGuias := 0;
      END;
      RETURN vCantGuias;
    END;

    /*******************************************************************************/
    FUNCTION RECEP_F_DESASOCIA_ENTREGA(cCod_Grupo_cia_in CHAR,
                                     cCod_Local_in CHAR,
                                     cNro_Recepcion_in CHAR,
                                     cNro_Entrega_in CHAR)
                                     RETURN CHAR

    IS
    vRpta CHAR(1) := 'S';
    BEGIN
      BEGIN
        DELETE LGT_RECEP_ENTREGA A
         WHERE A.COD_GRUPO_CIA = cCod_Grupo_cia_in
           AND A.COD_LOCAL = cCod_Local_in
           AND A.NRO_RECEP = cNro_Recepcion_in
           AND A.NUM_ENTREGA = cNro_Entrega_in;
      -- JMIRANDA 01.02.10 ACTUALIZA LA CANT DE GUIAS
        UPDATE LGT_RECEP_MERCADERIA M
           SET M.CANT_GUIAS = (SELECT COUNT(*) FROM LGT_RECEP_ENTREGA WHERE COD_GRUPO_CIA = cCod_Grupo_cia_in
                                                                 AND COD_LOCAL = cCod_Local_in
                                                                 AND NRO_RECEP = cNro_Recepcion_in)
         WHERE M.COD_GRUPO_CIA = cCod_Grupo_cia_in
           AND M.COD_LOCAL = cCod_Local_in
           AND M.NRO_RECEP = cNro_Recepcion_in;

         vRpta := 'S';

      EXCEPTION
      WHEN OTHERS THEN
       vRpta := 'N';
      END;
      RETURN vRpta;
    END;

    /*******************************************************************************/
    FUNCTION RECEP_F_MAX_PROD_VERIF
                                     RETURN NUMBER
    IS
     vRpta NUMBER(3) := 0;
    BEGIN
      BEGIN
      SELECT NVL(TRIM(LLAVE_TAB_GRAL), '0')
             INTO vRpta
             FROM PBL_TAB_GRAL
            WHERE ID_TAB_GRAL = '346';
      EXCEPTION
      WHEN OTHERS THEN
          vRpta := 10;    --VALOR POR DEFECTO 10
      END;

    RETURN vRpta;
    END;

    /*******************************************************************************/
    FUNCTION RECEP_F_TIENE_LOTE_SAP (cCod_GrupoCia_in IN CHAR,
                                     cCod_Local_in IN CHAR,
                                     cNro_Recepcion IN CHAR,
                                     cCod_Prod_in IN CHAR,
                                     cLote_in IN CHAR)
                                     RETURN VARCHAR2
    IS
     vRpta VARCHAR2(5) := 'N';
     vCant NUMBER(5) := 0;
     vNumEntrega lgt_nota_es_det.num_entrega%TYPE;
     vTotal NUMBER(10) := 0;

     CURSOR curEntregas IS
     SELECT e.num_entrega
       FROM lgt_recep_entrega e
      WHERE e.cod_grupo_cia = cCod_GrupoCia_in
      AND e.cod_local = cCod_Local_in
      AND e.nro_recep = cNro_Recepcion;

    BEGIN

    FOR v_curEntregas IN curEntregas LOOP
      BEGIN
        SELECT COUNT(1)
               INTO vCant
          FROM lgt_nota_es_det d
         WHERE d.cod_grupo_cia = cCod_GrupoCia_in
           AND d.cod_local = cCod_Local_in
           AND d.num_entrega = v_curEntregas.Num_Entrega
           AND d.cod_prod = cCod_Prod_in
           AND upper(trim(d.num_lote_prod)) = cLote_in;
      EXCEPTION
      WHEN no_data_found THEN
       vRpta := 'N';
      END;
      vTotal := vTotal + vCant;
    END LOOP;
      IF (vTotal > 0) THEN
       vRpta := 'S';
      END IF;
      RETURN vRpta;
    END;

    /*******************************************************************************/

    FUNCTION RECEP_F_IND_HAB_TRANSP
    RETURN CHAR
    IS
     vRpta char(1) := 'N';
    BEGIN

     BEGIN
       SELECT L.LLAVE_TAB_GRAL INTO vRpta
       FROM PBL_TAB_GRAL L
       WHERE L.ID_TAB_GRAL = 347;

       EXCEPTION
       WHEN no_data_found THEN
       vRpta := 'N';
     END;
    RETURN vRpta;
    END;

END;

/
