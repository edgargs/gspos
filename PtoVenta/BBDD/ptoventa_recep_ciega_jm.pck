CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_RECEP_CIEGA_JM" IS

 TYPE FarmaCursor IS REF CURSOR;
  g_nCodNumeraAuxConteoProd       PBL_NUMERA.COD_NUMERA%TYPE := '075';
  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;
  v_gNombreDiretorioAlert VARCHAR2(50) := 'DIR_INTERFACES';
/****************************************************************************/

  --Descripcion:
  --Fecha       Usuario	  	Comentario
  --16/11/2009  jmiranda    Creación
 PROCEDURE RECEP_P_INS_AUX_CONTEO (cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNroRecep_in IN CHAR,
                                      nSecAuxConteo_in IN NUMBER,
                                      cCodBarra_in IN VARCHAR2,
                                      nCantidad_in IN NUMBER,
                                      cUsuConteo_in IN CHAR,
                                      vIp_in IN VARCHAR,
                                      vNroBloque_in IN NUMBER,
                                      vIndDeteriorado_in IN CHAR DEFAULT 'N',
                                      vIndFueraLote_in IN CHAR DEFAULT 'N',
                                      vIndNoFound_in IN CHAR DEFAULT 'N');


/****************************************************************************/
 FUNCTION RECEP_F_DATOS_PROD(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNroRecep_in IN CHAR,
                             cNroBloque_in IN CHAR,
                             cAuxSecProd_in IN CHAR)

  RETURN FarmaCursor;

/****************************************************************************/
  FUNCTION RECEP_F_CUR_LIS_PRIMER_CONTEO(cCodGrupoCia_in IN CHAR,
                          		   			  cCodLocal_in	 IN CHAR,
                                        cNroRecep_in IN CHAR,
                                        cNroBloque_in IN CHAR)
  RETURN FarmaCursor;

/****************************************************************************/
  FUNCTION RECEP_F_NRO_BLOQUE_CONTEO(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cNroRecep_in IN CHAR,
                                     vIp_in IN VARCHAR)
  RETURN NUMBER;


/****************************************************************************/

  PROCEDURE RECEP_P_INS_PROD_CONTEO(   cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNroRecep_in IN CHAR,
                                      cUsuConteo_in IN CHAR,
                                      vIp_in IN VARCHAR);

/****************************************************************************/
  PROCEDURE RECEP_P_UPD_CAB (cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNroRecep_in IN CHAR,
                             cEstado_in IN CHAR,
                             cUsuCuenta_in IN CHAR,
                             cIpModRecep_in IN VARCHAR2);

/****************************************************************************/

 FUNCTION RECEP_P_VER_ESTADO (cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNroRecep_in IN CHAR)
 RETURN CHAR;

/****************************************************************************/
  PROCEDURE RECEP_P_ELIMINA_AUX (cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cNroRecep_in IN CHAR,
                                cNroBloque_in IN CHAR,
                                cSecAuxConteo_in IN NUMBER);

/****************************************************************************/
    PROCEDURE RECEP_P_UPD_AUX_CONTEO (cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNroRecep_in IN CHAR,
                             cNroBloque_in IN CHAR,
                             cSecAuxConteo_in IN NUMBER,
                             cCantidad_in IN NUMBER,
                             cUsuCuenta_in IN CHAR,
                             cIpModRecep_in IN VARCHAR2);
/****************************************************************************/
   PROCEDURE RECEP_P_ENVIA_CORREO_CONTEO(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR,
                                         cNroBloque IN CHAR);

/****************************************************************************/
  PROCEDURE RECEP_P_CORREO_ADJUNTO_CONTEO(vAsunto_in        IN CHAR,
                                     vTitulo_in        IN CHAR,
                                     vMensaje_in       IN CHAR,
                                     vInd_Archivo_in   IN CHAR DEFAULT 'N',
                                     vNombre_Archivo_in IN VARCHAR2 DEFAULT null
                                     );
/***********************************************************************************************************************/
  FUNCTION RECEP_F_EMAIL_CB_NO_HALLADO
  RETURN VARCHAR2;

/***********************************************************************************************************************/
  FUNCTION RECEP_F_VERIF_AUX_PROD(cGrupoCia_in  IN CHAR,
                                      cCodLocal_in  IN CHAR,
                                      cNroRecepcion IN CHAR)
  RETURN CHAR;
/************************************************************************************/
    /*
    FUNCTION RECEP_F_VAR2_IP_CONTEO(cGrupoCia_in  IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                    vIp_in        IN VARCHAR2)
    RETURN VARCHAR2;
    */

/* ********************************************************************* */
  PROCEDURE RECEP_P_BLOQUEO_RECEPCION(cGrupoCia_in  IN CHAR,
                                      cCodLocal_in  IN CHAR,
                                      cNroRecepcion IN CHAR);
/* ********************************************************************* */

  FUNCTION RECEP_F_GET_MSG_PEND
  RETURN VARCHAR2;

/***********************************************************************************************************************/
  --Descripcion: Destinatario ingreso transportista.
  --Fecha       Usuario   Comentario
  --16/04/2014  ERIOS     Creacion
  FUNCTION RECEP_F_EMAIL_ING_TRANSP
  RETURN VARCHAR2;

END PTOVENTA_RECEP_CIEGA_JM;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_RECEP_CIEGA_JM" IS

 /****************************************************************************/
 PROCEDURE RECEP_P_INS_AUX_CONTEO (cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNroRecep_in IN CHAR,
                                      nSecAuxConteo_in IN NUMBER,
                                      cCodBarra_in IN VARCHAR2,
                                      nCantidad_in IN NUMBER,
                                      cUsuConteo_in IN CHAR,
                                      vIp_in IN VARCHAR,
                                      vNroBloque_in IN NUMBER,
                                      vIndDeteriorado_in IN CHAR DEFAULT 'N',
                                      vIndFueraLote_in IN CHAR DEFAULT 'N',
                                      vIndNoFound_in IN CHAR DEFAULT 'N')

   IS
    Cod_Prod CHAR(6);
    Nro_bloque_act NUMBER;
    --vSec_conteo NUMBER;
    sec_conteo_aux NUMBER;
   BEGIN
   Cod_Prod := PTOVENTA_VTA.VTA_REL_COD_BARRA_COD_PROD(cCodGrupoCia_in, cCodBarra_in);
   /*
   SELECT VAL_NUMERA INTO vsec_conteo FROM PBL_NUMERA N
   WHERE N.COD_GRUPO_CIA = cCodGrupoCia_in AND N.COD_LOCAL = cCodLocal_in AND N.COD_NUMERA =  g_nCodNumeraAuxConteoProd;
   */
   INSERT INTO AUX_LGT_PROD_CONTEO
  -- (COD_GRUPO_CIA, COD_LOCAL, NRO_RECEP, SEC_CONTEO, COD_PROD, COD_BARRA, CANTIDAD,
   (COD_GRUPO_CIA, COD_LOCAL, NRO_RECEP, COD_PROD, COD_BARRA, CANTIDAD,
   USU_INS_CONTEO, FEC_INS_CONTEO, IP_INS_CONTEO, NRO_BLOQUE, IND_DETERIORADO, IND_FUERA_LOTE,
   IND_NO_FOUND, SEC_AUX_CONTEO)
   VALUES
--   (cCodGrupoCia_in, cCodLocal_in, cNroRecep_in, vSec_conteo , Cod_Prod, cCodBarra_in, nCantidad_in,
   (cCodGrupoCia_in, cCodLocal_in, cNroRecep_in, Cod_Prod, cCodBarra_in, nCantidad_in,
   cUsuConteo_in, SYSDATE, vIp_in, vNroBloque_in, vIndDeteriorado_in,  vIndFueraLote_in,
   vIndNoFound_in, nSecAuxConteo_in );

   END;

 /****************************************************************************/

 FUNCTION RECEP_F_DATOS_PROD(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNroRecep_in IN CHAR,
                             cNroBloque_in IN CHAR,
                             cAuxSecProd_in IN CHAR)

   RETURN FarmaCursor
  IS
    curDetalleProd FarmaCursor;
  BEGIN

    --SELECT * FROM AUX_LGT_PROD_CONTEO ;

    OPEN curDetalleProd FOR
      SELECT COD_PROD || 'Ã' || DESC_PROD || 'Ã' ||
             CANT_UNID_PRESENT || 'Ã' || DESC_UNID_PRESENT
       FROM LGT_PROD P
      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in;
        --AND P.COD_PROD = cCodProd_in;

    RETURN curDetalleProd;
  END;

  /****************************************************************************/
  FUNCTION RECEP_F_CUR_LIS_PRIMER_CONTEO(cCodGrupoCia_in IN CHAR,
                          		   			  cCodLocal_in	 IN CHAR,
                                        cNroRecep_in IN CHAR,
                                        cNroBloque_in IN CHAR)
  RETURN FarmaCursor
  IS
    curPrimerConteo FarmaCursor;
    vIP_pc VARCHAR2(15) := '';
  BEGIN

  SELECT SYS_CONTEXT ('USERENV', 'IP_ADDRESS') INTO vIP_pc  FROM dual;
    OPEN curPrimerConteo FOR
--------------------------------
--------------------------------
--JMIRANDA 24.08.2010
SELECT NVL(A1.DESC_PROD,' ') || 'Ã' ||
       NVL(A1.UNIDAD,' ') || 'Ã' ||
       TO_CHAR(A1.CANTIDAD,'999999') || 'Ã' ||
       ' ' || 'Ã' ||
       ' ' || 'Ã' ||
       ' ' || 'Ã' ||
       ' ' || 'Ã' ||
       TO_CHAR(A1.SEC_AUX_CONTEO,'99999999') || 'Ã' ||
       A1.COD_BARRA || 'Ã' ||
       TO_CHAR(A1.NRO_BLOQUE,'9999999999')
FROM
(
--LISTADO DE PRODUCTOS AUXILIARES
SELECT AU.COD_GRUPO_CIA,
       AU.COD_LOCAL,
       AU.NRO_RECEP,
       AU.COD_PROD,
       P.DESC_PROD,
       P.DESC_UNID_PRESENT UNIDAD,
       AU.CANTIDAD,
       --'LOTE1',     --'FEC1',       --'LOTE2',       --'FEC2',
       AU.SEC_AUX_CONTEO,
       AU.COD_BARRA,
       AU.NRO_BLOQUE
  FROM AUX_LGT_PROD_CONTEO AU, LGT_PROD P, LGT_COD_BARRA CB
 WHERE AU.COD_GRUPO_CIA = P.COD_GRUPO_CIA(+)
   AND AU.COD_PROD = P.COD_PROD(+)
   AND AU.COD_GRUPO_CIA = CB.COD_GRUPO_CIA(+)
   AND AU.COD_BARRA = CB.COD_BARRA(+)
   AND AU.COD_PROD = CB.COD_BARRA(+)
   /* -correccion ERROR - dubilluz*/
   AND AU.COD_GRUPO_CIA = cCodGrupoCia_in
   AND AU.COD_LOCAL = cCodLocal_in
   /**/
   AND AU.NRO_RECEP = cNroRecep_in
   AND AU.NRO_BLOQUE = cNroBloque_in
   AND AU.IP_INS_CONTEO = vIP_pc
 --LISTADO DE PRODUCTOS AUXILIARES //
) A1
--mezclo
WHERE A1.COD_GRUPO_CIA = cCodGrupoCia_in
  AND A1.COD_LOCAL = cCodLocal_in
  AND A1.NRO_RECEP = cNroRecep_in
  AND A1.COD_PROD NOT IN ('000000')
ORDER BY A1.SEC_AUX_CONTEO DESC;

--------------------------------

    RETURN curPrimerConteo;
  END;

  /****************************************************************************/
  FUNCTION RECEP_F_NRO_BLOQUE_CONTEO(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cNroRecep_in IN CHAR,
                                     vIp_in IN VARCHAR)
  RETURN NUMBER
  IS
    Nro_bloque_act NUMBER;
    BEGIN

    --SELECT NVL(MAX(TO_NUMBER(pc.nro_bloque),0) INTO Nro_bloque_act FROM aux_lgt_prod_conteo pc
    SELECT NVL(MAX(TO_NUMBER(pc.nro_bloque)), 0)
      INTO Nro_bloque_act
      FROM aux_lgt_prod_conteo pc
     WHERE pc.cod_grupo_cia = cCodGrupoCia_in
       AND pc.cod_local = cCodLocal_in
       AND pc.nro_recep = cNroRecep_in
       ANd pc.ip_ins_conteo = vIp_in;--DUBILLUZ - 07.12.2009

     IF Nro_bloque_act = 0 THEN
        Nro_bloque_act := 1;
     ELSE
        Nro_bloque_act := Nro_bloque_act+1;
     END IF;

      RETURN Nro_bloque_act;
     EXCEPTION
      WHEN NO_DATA_FOUND THEN
       Nro_bloque_act := 1;

  END;
  /****************************************************************************/

  PROCEDURE RECEP_P_INS_PROD_CONTEO(   cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNroRecep_in IN CHAR,
                                      cUsuConteo_in IN CHAR,
                                      vIp_in IN VARCHAR)
  IS
  BEGIN
   --TENER EN CUENTA FINALIZAR LA TOMA

   INSERT INTO LGT_PROD_CONTEO
--   (COD_GRUPO_CIA, COD_LOCAL, NRO_RECEP, SEC_CONTEO, COD_PROD, COD_BARRA, CANTIDAD,
   (COD_GRUPO_CIA, COD_LOCAL, NRO_RECEP, SEC_CONTEO, COD_PROD, CANTIDAD,
    USU_INS_CONTEO, FEC_INS_CONTEO, IP_INS_CONTEO)
SELECT cCodGrupoCia_in,
       cCodLocal_in,
       cNroRecep_in,
       ROWNUM SEC_CONTEO,
       V1.COD_PROD,
       V1.CANT,
       cUsuConteo_in,
       SYSDATE,
       vIp_in
  FROM (SELECT COD_PROD, SUM(CANTIDAD) CANT
          FROM AUX_LGT_PROD_CONTEO AP
         WHERE AP.COD_GRUPO_CIA = cCodGrupoCia_in
           AND AP.COD_LOCAL = cCodLocal_in
           AND AP.NRO_RECEP = cNroRecep_in
           AND AP.COD_PROD NOT IN ('000000')--no incluye los que no tienen codigo de barra
         GROUP BY COD_PROD) V1
 ORDER BY ROWNUM ASC;

 /*  SELECT COD_GRUPO_CIA, COD_LOCAL, NRO_RECEP, ROWNUM SEC_CONTEO, COD_PROD, COD_BARRA, SUM(CANTIDAD),
    cUsuConteo_in, SYSDATE, vIp_in
     FROM AUX_LGT_PROD_CONTEO AP
    WHERE AP.COD_GRUPO_CIA = cCodGrupoCia_in
      AND AP.COD_LOCAL = cCodLocal_in
      AND AP.NRO_RECEP = cNroRecep_in
      AND AP.COD_PROD NOT IN ('000000')
    GROUP BY COD_GRUPO_CIA, COD_LOCAL, NRO_RECEP, ROWNUM, COD_PROD, COD_BARRA
    ORDER BY SEC_CONTEO ASC;
   */
  END;

  /****************************************************************************/
  PROCEDURE RECEP_P_UPD_CAB (cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNroRecep_in IN CHAR,
                             cEstado_in IN CHAR,
                             cUsuCuenta_in IN CHAR,
                             cIpModRecep_in IN VARCHAR2)
  IS

  BEGIN
    --ESTADO: C: CONTEO, E: ESPERA, R: REVISADO
    UPDATE LGT_RECEP_MERCADERIA RM
    SET RM.ESTADO = cEstado_in,
        RM.USU_MOD_RECEP = cUsuCuenta_in,
        RM.FEC_MOD_RECEP = SYSDATE,
        RM.IP_MOD_RECEP = cIpModRecep_in
    WHERE RM.COD_GRUPO_CIA = cCodGrupoCia_in
    AND RM.COD_LOCAL = cCodLocal_in
    AND RM.NRO_RECEP = cNroRecep_in;

  END;

  /****************************************************************************/
  FUNCTION RECEP_P_VER_ESTADO (cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNroRecep_in IN CHAR)
   RETURN CHAR
  IS
    cEstado CHAR(1);
  BEGIN
    SELECT RM.ESTADO INTO cEstado FROM LGT_RECEP_MERCADERIA RM
     WHERE RM.COD_GRUPO_CIA = cCodGrupoCia_in
       AND RM.COD_LOCAL = cCodLocal_in
       AND RM.NRO_RECEP = cNroRecep_in;

    RETURN cEstado;

    --EXCEPTION
     --WHEN NO_DATA_FOUND THEN
    --  RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_ENTREGAS_RENDIR||' - ENTREGAS A RENDIR');
  END;

  /****************************************************************************/
  PROCEDURE RECEP_P_ELIMINA_AUX (cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cNroRecep_in IN CHAR,
                                cNroBloque_in IN CHAR,
                                cSecAuxConteo_in IN NUMBER)

   IS

  BEGIN

  --GUARDAR EN TABLA AUX_LGT_PROD_CONTEO_BORRADOS
  INSERT INTO AUX_LGT_PROD_CONTEO_BORRADOS
  (SELECT * FROM AUX_LGT_PROD_CONTEO A
   WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
     AND A.COD_LOCAL = cCodLocal_in
     AND A.NRO_RECEP = cNroRecep_in
     AND A.NRO_BLOQUE = cNroBloque_in
     AND A.SEC_AUX_CONTEO = cSecAuxConteo_in);


   DELETE AUX_LGT_PROD_CONTEO PC
    WHERE PC.COD_GRUPO_CIA = cCodGrupoCia_in
      AND PC.COD_LOCAL = cCodLocal_in
      AND PC.NRO_RECEP = cNroRecep_in
      AND PC.NRO_BLOQUE = cNroBloque_in
      AND PC.SEC_AUX_CONTEO = cSecAuxConteo_in;

  END;

  /****************************************************************************/
    PROCEDURE RECEP_P_UPD_AUX_CONTEO (cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNroRecep_in IN CHAR,
                             cNroBloque_in IN CHAR,
                             cSecAuxConteo_in IN NUMBER,
                             cCantidad_in IN NUMBER,
                             cUsuCuenta_in IN CHAR,
                             cIpModRecep_in IN VARCHAR2)
  IS

  BEGIN
    --ESTADO: C: CONTEO, E: ESPERA, R: REVISADO
    UPDATE AUX_LGT_PROD_CONTEO A
    SET A.CANTIDAD = cCantidad_in,
        A.USU_MOD_CONTEO = cUsuCuenta_in,
        A.FEC_MOD_CONTEO = SYSDATE,
        A.IP_MOD_CONTEO = cIpModRecep_in
    WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND A.COD_LOCAL = cCodLocal_in
    AND A.NRO_RECEP = cNroRecep_in
    AND A.NRO_BLOQUE = cNroBloque_in
    AND A.SEC_AUX_CONTEO = cSecAuxConteo_in;

  END;

  /****************************************************************************/
  PROCEDURE RECEP_P_ENVIA_CORREO_CONTEO(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR,
                                         cNroBloque IN CHAR)

   IS
     v_vHtmlCorreo       VARCHAR2(32767):='';
     v_vCorreoDiferenciasDest   VARCHAR2(100):='';
     v_vNombreArchivo    VARCHAR2(100):='';
     n_Contador          NUMBER:= 0;
     v_vDescLocal        PBL_LOCAL.DESC_CORTA_LOCAL%TYPE;

     CURSOR cursorCodBarraNoFound IS
      SELECT PC.COD_BARRA,
             PC.CANTIDAD,
             PC.USU_INS_CONTEO,
             TO_CHAR(PC.FEC_INS_CONTEO, 'DD/MM/YYYY HH24:MI') fecha,
             PC.IP_INS_CONTEO
        FROM AUX_LGT_PROD_CONTEO PC
       WHERE PC.NRO_RECEP = cNroRecepcion
         AND PC.NRO_BLOQUE = cNroBloque
         AND PC.COD_PROD NOT IN ('000000');
       --AND PC.IND_NO_FOUND = 'S'

  BEGIN
      DBMS_OUTPUT.put_line('ENVIA CORREO CONTEO');

      SELECT TRIM(TB.LLAVE_TAB_GRAL) INTO v_vCorreoDiferenciasDest
      FROM PBL_TAB_GRAL TB
      WHERE TB.ID_TAB_GRAL='322';

       v_vNombreArchivo := 'ALERTA_COD_BARRA_NO_FOUND_CONTEO.xls';--_'||to_char(sysdate,'dd/mm/yyyy') ||'-RECEPCION:'||cNroRecepcion ||'.xls';

       ARCHIVO_TEXTO := UTL_FILE.FOPEN(v_gNombreDiretorioAlert,TRIM(v_vNombreArchivo),'W');

        v_vHtmlCorreo:='<html>'||
                       '<head>'||
                        '  <meta http-equiv="Content-Type" content="application/vnd.ms-excel">'||
                        '  <title>PRIMER CONTEO RECEPCION CIEGA</title>'||
                        '</head>'||
                        '<body>'||
                        '<h3 align="center">PRODUCTOS CON CODIGO DE BARRA NO HALLADOS  </h3>'||
                        '<h3 align="center">Fecha : '||to_char(sysdate,'dd/mm/yyyy HH24:MI')||' </h3>'||
                        '<table style="text-align: left; width: 100%;" border="1"'||
                        ' cellpadding="2" cellspacing="1">'||
                        '    <tr>'||
                        '      <th><small>CODIGO BARRA</small></th>'||
                        '      <th><small>CANTIDAD</small></th>'||
                      --  '      <th><small>CODIGO DE BARRA</small></th>'||
                        '      <th><small>USUARIO</small></th>'||
                        '      <th><small>FECHA</small></th>'||
                        '      <th><small>IP</small></th>'||
--                        '      <th><small>CANTIDAD ENTREGADA</small></th>'||
                        '    </tr>';

       UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);
            DBMS_OUTPUT.put_line('v_vHtmlCorreo: ' || v_vHtmlCorreo);
       FOR codBarra IN cursorCodBarraNoFound
       LOOP
              n_Contador:=n_Contador+1;
              v_vHtmlCorreo:='<tr>'||
--                             '    <td align="right">'||codBarra.Cod_Barra||'</td>'||
                             '    <td align="left">'||codBarra.Cod_Barra||'</td>'||
                             '    <td align="left">'||codBarra.Cantidad||'</td>'||
                             '    <td align="left">'||codBarra.Usu_Ins_Conteo||'</td>'||
                             '    <td align="left">'||codBarra.Fecha||'</td>'||
                             '    <td align="left">'||codBarra.Ip_Ins_Conteo||'</td>'||
                             '</tr>';
               UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);
       END LOOP;
        DBMS_OUTPUT.put_line('v_vHtmlCorreo: ' || v_vHtmlCorreo);
       DBMS_OUTPUT.put_line('n_Contador: ' || n_Contador);

        v_vHtmlCorreo:=' </table> '||
                       ' </body> '||
                       ' </html> ';

       UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);
       UTL_FILE.FCLOSE(ARCHIVO_TEXTO);


      SELECT A.DESC_CORTA_LOCAL INTO v_vDescLocal FROM PBL_LOCAL A WHERE A.COD_GRUPO_CIA =cGrupoCia_in  AND A.COD_LOCAL =cCodLocal_in;
      RECEP_P_CORREO_ADJUNTO_CONTEO(
                            'PRODUCTOS CON CÓDIGO DE BARRA NO ENCONTRADOS '||TO_CHAR(SYSDATE,'DD/MM/YYYY') || '-'|| v_vDescLocal,
                            '<BR>' || 'PRODUCTOS CON CÓDIGO DE BARRA NO ENCONTRADOS ' || '<BR>'  ||'RECEPCIÓN Nro. ' || cNroRecepcion || '<BR>' || 'DIA: '||TO_CHAR(SYSDATE,'DD/MM/YYYY HH24:mi:ss') || '<BR>' || 'LOCAL: ' ||v_vDescLocal,
                            'Lista de códigos de barras no encontrados en conteo Fecha: '||TO_CHAR(SYSDATE,'DD/MM/YYYY'),
                            'S',v_vNombreArchivo);

  END;

  /***********************************************************************************************************************/
  PROCEDURE RECEP_P_CORREO_ADJUNTO_CONTEO(vAsunto_in        IN CHAR,
                                     vTitulo_in        IN CHAR,
                                     vMensaje_in       IN CHAR,
                                     vInd_Archivo_in   IN CHAR DEFAULT 'N',
                                     vNombre_Archivo_in IN VARCHAR2 DEFAULT null
                                     )
    AS

      ReceiverAddress VARCHAR2(3000) ;
      CCReceiverAddress VARCHAR2(120) := NULL;
      mesg_body VARCHAR2(32767);
    BEGIN

    DBMS_OUTPUT.put_line('vNombre_Archivo_in INICIO '|| vNombre_Archivo_in);
      mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;


      SELECT TRIM(A.LLAVE_TAB_GRAL)  INTO  ReceiverAddress
      FROM PBL_TAB_GRAL A
      WHERE A.ID_TAB_GRAL='322';

      DBMS_OUTPUT.put_line('correo ' ||ReceiverAddress);

      IF vInd_Archivo_in = 'N' THEN
        FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                 ReceiverAddress,
                                 vAsunto_in,
                                 vTitulo_in,
                                 mesg_body,
                                 CCReceiverAddress,
                                 FARMA_EMAIL.GET_EMAIL_SERVER,
                                 true);
      ELSE
        DBMS_OUTPUT.put_line('vNombre_Archivo_in '|| vNombre_Archivo_in);
      FARMA_EMAIL.ENVIA_CORREO_ATTACH3(
                               FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                               ReceiverAddress,
                               vAsunto_in,
                               vTitulo_in,
                               mesg_body,
                               v_gNombreDiretorioAlert,--
                               vNombre_Archivo_in,
                               CCReceiverAddress,
                               FARMA_EMAIL.GET_EMAIL_SERVER);
     DBMS_OUTPUT.put_line('Envio archivo Conteo');
      END IF;

    END;

  /***********************************************************************************************************************/
    FUNCTION RECEP_F_EMAIL_CB_NO_HALLADO
    RETURN VARCHAR2
    IS
     v_Direccion VARCHAR2(250);
    BEGIN
      SELECT TO_CHAR(NVL(TG.LLAVE_TAB_GRAL,'JMIRANDA')) INTO v_Direccion
      FROM PBL_TAB_GRAL TG WHERE TG.ID_TAB_GRAL = '322';
      RETURN v_Direccion;
    END;
  /***********************************************************************************************************************/

    FUNCTION RECEP_F_VERIF_AUX_PROD(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR)
    RETURN CHAR
     IS
       v_CantAuxProd NUMBER;
       v_IndTieneDatos CHAR;
    BEGIN
       SELECT COUNT(*) INTO v_CantAuxProd
         FROM AUX_LGT_PROD_CONTEO
        WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NRO_RECEP = cNroRecepcion
          AND COD_PROD NOT IN ('000000');

        IF v_CantAuxProd = 0 THEN
           v_IndTieneDatos := 'N';
        ELSIF v_CantAuxProd > 0 THEN
           v_IndTieneDatos := 'S';
        END IF;

        RETURN v_IndTieneDatos;
    END;
  /* ******************************************************************************* */
   /* FUNCTION RECEP_F_VAR2_IP_CONTEO(cGrupoCia_in  IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                    vIp_in        IN VARCHAR2)
    RETURN VARCHAR2
    IS
     v_ipConteo_in VARCHAR2(20);
     vResultado varchar2(2);
    BEGIN

      begin

      SELECT nvl(l.ip_conteo_ciega,'T')
      INTO   v_ipConteo_in
      FROM   pbl_local l
      where  l.cod_grupo_cia = cGrupoCia_in
      and    l.cod_local = cCodLocal_in;

      exception
      when others then
           v_ipConteo_in := 'T';
      end;

      if v_ipConteo_in = 'T'  then
          vResultado := 'S';
      else
         if  v_ipConteo_in = vIp_in  then
             vResultado := 'S';
         else
             vResultado := 'N';
         end if;

      end if;
      RETURN vResultado;
    END;
    */

  /* ************************************************************************************* */
  PROCEDURE RECEP_P_BLOQUEO_RECEPCION(cGrupoCia_in  IN CHAR,
                                      cCodLocal_in  IN CHAR,
                                      cNroRecepcion IN CHAR)
    AS
    vConteo LGT_RECEP_MERCADERIA.Estado%type;
    vIndSegundoConteo LGT_RECEP_MERCADERIA.Ind_Seg_Conteo%type;
    BEGIN
         SELECT R.ESTADO,r.ind_seg_conteo
         INTO   vConteo,vIndSegundoConteo
         FROM   LGT_RECEP_MERCADERIA R
         WHERE  R.COD_GRUPO_CIA = cGrupoCia_in
         AND    R.COD_LOCAL = cCodLocal_in
         AND    R.NRO_RECEP = cNroRecepcion  FOR UPDATE;
    EXCEPTION
    WHEN OTHERS THEN
         ROLLBACK;
    END;
  /* ************************************************************************************* */
    FUNCTION RECEP_F_GET_MSG_PEND
    RETURN VARCHAR2
    IS
     vMensaje VARCHAR2(500) := '';
    BEGIN
     BEGIN
          SELECT LLAVE_TAB_GRAL INTO vMensaje
            FROM PBL_TAB_GRAL
           WHERE ID_TAB_GRAL = 328;

         EXCEPTION
          WHEN NO_DATA_FOUND THEN
           vMensaje := ' ';
     END;
      RETURN vMensaje;
    END;

  --Descripcion: Destinatario ingreso transportista.
  --Fecha       Usuario   Comentario
  --16/04/2014  ERIOS     Creacion
  FUNCTION RECEP_F_EMAIL_ING_TRANSP
  RETURN VARCHAR2
    IS
   v_Direccion VARCHAR2(250);
  BEGIN
    SELECT DESC_CORTA INTO v_Direccion
    FROM PBL_TAB_GRAL TG WHERE --TG.ID_TAB_GRAL = '322'
    COD_APL = 'PTO_VENTA'
    and COD_TAB_GRAL = 'FARMA_EMAIL'
    and LLAVE_TAB_GRAL = '14'
    ;
    RETURN v_Direccion;
  END;

 END PTOVENTA_RECEP_CIEGA_JM;
/

