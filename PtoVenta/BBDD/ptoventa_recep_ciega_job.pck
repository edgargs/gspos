CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_RECEP_CIEGA_JOB" is

  -- Author  : JCHAVEZ
  -- Created : 16/11/2009 06:29:33 p.m.
  -- Purpose :

  TYPE FarmaCursor IS REF CURSOR;
  V_CORREO_LOCAL VARCHAR2(300) := '';
  COD_NUMERA_SEC_KARDEX      PBL_NUMERA.COD_NUMERA%TYPE := '016';
  g_cMotKardexIngMatriz      LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '101';
  g_cTipDocKdxGuiaES         LGT_KARDEX.Tip_Comp_Pago%TYPE := '02';
  g_cTipCompNumEntrega       LGT_KARDEX.Tip_Comp_Pago%TYPE := '05';
  g_cTipoMotKardexAjusteGuia LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '008';
   v_gNombreDiretorioAlert VARCHAR2(50) := 'DIR_INTERFACES';--'DIR_REPORTES';
     g_cTipoOrigenLocal CHAR(2):= '01';
  g_cTipCompGuia LGT_KARDEX.Tip_Comp_Pago%TYPE := '03';
    g_cTipoOrigenMatriz CHAR(2):= '02';
      g_cTipoOrigenProveedor CHAR(2):= '03';
        g_cTipoOrigenCompetencia CHAR(2):= '04';
  C_INICIO_MSG VARCHAR2(20000) := '<html>'  ||
                                      '<head>'  ||
                                      '<style type="text/css">'  ||
                                      '.style3 {font-family: Arial, Helvetica, sans-serif}'  ||
                                      '.style8 {font-size: 24; }'  ||
                                      '.style9 {font-size: larger}'  ||
                                      '.style12 {'  ||
                                      'font-family: Arial, Helvetica, sans-serif;'  ||
                                      'font-size: larger;'  ||
                                      'font-weight: bold;'  ||
                                      '}'  ||
                                      '</style>'  ||
                                      '</head>'  ||
                                      '<body>'  ||
                                      '<table width="510"border="0">'  ||
                                      '<tr>'  ||
                                      '<td width="487" align="center" valign="top"><h1>DIFERENCIAS</h1></td>'  ||
                                      '</tr>'  ||
                                      '</table>'  ||
                                      '<table width="504" border="1">'  ||
                                      '<tr>'  ||
                                      '<td height="43" align="center" colspan="1"><h2>Producto </h2></td>'  ||
                                      '<td colspan="1" align="center" ><h2>Laboratorio</h2> </td>'  ||
                                      '</tr>';

  C_FIN_MSG VARCHAR2(2000) := '</td>' ||
                                  '</tr>' ||
                                  '</table>' ||
                                  '</body>' ||
                                  '</html>';
   ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;

  PROCEDURE ENVIA_CORREO_ATTACH3(cSendorAddress_in in char,
                                cReceiverAddress_in in char,
                                cSubject_in in varchar2,
                                ctitulo_in in varchar2,
                                cmensaje_in in varchar2,
                                pDirectorio IN VARCHAR2,
                                pfilename IN VARCHAR2,
                                cCCReceiverAddress_in in char,
                                cip_servidor in char);

PROCEDURE attach_report3(conn         IN OUT NOCOPY utl_smtp.connection,
			mime_type    IN VARCHAR2 DEFAULT 'text/plain',
			inline       IN BOOLEAN  DEFAULT TRUE,
      directory IN VARCHAR2 DEFAULT NULL,
			filename     IN VARCHAR2 DEFAULT NULL,
		        last         IN BOOLEAN  DEFAULT FALSE);

--JMIRANDA 02.02.10
  PROCEDURE RECEP_P_ENVIA_DIFE_TIEMPO(cCodGrupoCia_in IN CHAR);
      						  			   --cCod_Local_in   IN CHAR);

    PROCEDURE RECEP_P_ENVIA_CORREO_DIFE(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR);
  PROCEDURE RECEP_P_ENVIA_CORREO_ADJUNTO(vAsunto_in        IN CHAR,
                                     vTitulo_in        IN CHAR,
                                     vMensaje_in       IN CHAR,
                                     vInd_Archivo_in   IN CHAR DEFAULT 'N',
                                     vNombre_Archivo_in IN VARCHAR2 DEFAULT null
                                     );
----------------
FUNCTION RECEP_F_CHAR_IND_TIENE_DATA(cGrupoCia_in  CHAR,
                                     cCodLocal_in CHAR,
                                     cNroRecepcion CHAR)
                                     RETURN CHAR;

  --Descripcion: obtiene data de recpciones de locales
  --Fecha       Usuario	  Comentario
  --07/05/2010  JCORTEZ     Creación
  PROCEDURE RECEP_P_ENVIA_CORREO_DIFE_2(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR);

  --Descripcion: obtiene data de recpciones de locales
  --Fecha       Usuario	  Comentario
  --17/08/2010  JMIRANDA     Creación
  FUNCTION RECEP_F_EMAIL_LOCAL(cCod_Grupo_Cia_in IN CHAR, cCod_Local_in IN CHAR) RETURN VARCHAR2;

end;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_RECEP_CIEGA_JOB" is

          PROCEDURE ENVIA_CORREO_ATTACH3(cSendorAddress_in in char,
                                cReceiverAddress_in in char,
                                cSubject_in in varchar2,
                                ctitulo_in in varchar2,
                                cmensaje_in in varchar2,
                                pDirectorio IN VARCHAR2,
                                pfilename IN VARCHAR2,
                                cCCReceiverAddress_in in char,
                                cip_servidor in char)
  AS
    conn UTL_SMTP.CONNECTION;
    cmensaje VARCHAR2(1000);
    mesg_body varchar2(32767);
    crlf VARCHAR2( 2 ):= CHR( 13 ) || CHR( 10 );
  BEGIN



        conn := ORACLE_MAIL.begin_mail(
        v_smtp_host => cip_servidor,
        sender     => cSendorAddress_in,
        recipients => cReceiverAddress_in,
        CCrecipients => cCCReceiverAddress_in,
        subject    => cSubject_in,
        mime_type  => ORACLE_MAIL.MULTIPART_MIME_TYPE);

        mesg_body:= '<html>
                <head>
                <title>MIFARMA te entiende de cuida By SISTEMAS</title>
                </head>
                <body bgcolor="#FFFFFF" link="#000080">
                <table cellspacing="0" cellpadding="0" width="100%">
                <tr align="LEFT" valign="BASELINE">
                <td width="100%" valign="middle"><h1><font color="#00008B"><b>'||ctitulo_in||'</b></font></h1>
                </td>
             </table>
              <ul>
               '||cmensaje_in||'
               <l><b> </b> </l>
               <l><b></p></p> Mensaje originado automaticamente desde la Base de Datos,no contestar </b> </l>
                  </ul>
                 </body>
                 </html>';

        --ctitulo_in||crlf||cmensaje_in;

        ORACLE_MAIL.attach_text(
        conn      => conn,
        data      =>   mesg_body ,
        mime_type => 'text/html');



     /* IF i <> 0 THEN
        cmensaje := '<H2>SE HA REALIZADO CAMBIOS EN EL LOCAL.<BR><BR>¡VERIFIQUE LOS CAMBIOS ADJUNTOS!</H2>
                      <b><p> Mensaje originado automaticamente desde la Base de Datos,no contestar </b> </p>';
        ORACLE_MAIL.attach_text(
          conn      => conn,
          data      => cmensaje,
          mime_type => 'text/html');
*/
        attach_report3(
          conn      => conn,
          mime_type => 'text/html',
          inline    => FALSE,
          directory => pDirectorio,
          filename  => pfilename,
          last      => TRUE);
    /*  ELSE
        cmensaje := '<H2>NO SE HA REALIZADO CAMBIOS EN EL LOCAL</H2>
                    <b><p> Mensaje originado automaticamente desde la Base de Datos,no contestar </b> </p>';*/

      --END IF;

      ORACLE_MAIL.end_mail( conn => conn );
    /*exception
     when others then
      null;*/
  END;

  PROCEDURE attach_report3(conn         IN OUT NOCOPY utl_smtp.connection,
			mime_type    IN VARCHAR2 DEFAULT 'text/plain',
			inline       IN BOOLEAN  DEFAULT TRUE,
      directory IN VARCHAR2 DEFAULT NULL,
			filename     IN VARCHAR2 DEFAULT NULL,
		        last         IN BOOLEAN  DEFAULT FALSE)
  IS
    --v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
    vNewLine VARCHAR2(2000):='edgar';

    vInHandle utl_file.file_type;
  BEGIN
    ORACLE_MAIL.begin_attachment(conn, mime_type, inline, filename);
    DBMS_OUTPUT.PUT_LINE('ARCHIVO:'||filename);
    DBMS_OUTPUT.PUT_LINE('directory:'||directory);
    vInHandle := utl_file.fopen(directory, TRIM(filename), 'R');
    LOOP
      BEGIN
        /*IF vNewLine IS NULL THEN
            EXIT;
          END IF;*/
--          DBMS_OUTPUT.PUT_LINE('INICIO,'||vNewLine);
        utl_file.get_line(vInHandle, vNewLine);
--         DBMS_OUTPUT.PUT_LINE('2,'||vNewLine);
        ORACLE_MAIL.write_text(conn, vNewLine || chr(10));
--        DBMS_OUTPUT.PUT_LINE('conn,'||vNewLine);
      EXCEPTION
        WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('error');
          EXIT;
      END;
    END LOOP;
    utl_file.fclose(vInHandle);
    DBMS_OUTPUT.PUT_LINE('close file');
    ORACLE_MAIL.end_attachment(conn, last);

  END;

  /*********************************************************************************************************/
  --PARA ENVIAR LAS DIFERENCIAS DESPUES DE PASADO EL TIEMPO PARA REALIZAR TRANSFERENCIAS
  PROCEDURE RECEP_P_ENVIA_DIFE_TIEMPO(cCodGrupoCia_in IN CHAR)
      						  			   --cCod_Local_in   IN CHAR)

  IS
  PRAGMA AUTONOMOUS_TRANSACTION;
    vHoras NUMBER(4);
    curRecep FarmaCursor;
    curRecep2 FarmaCursor;
    vCod_local CHAR(3);
    vCod_Grupo_cia CHAR(3);
    vNro_Recep CHAR(10);
    cCod_Local_in CHAR(3);
  BEGIN

  SELECT DISTINCT(COD_LOCAL)
  INTO   cCod_Local_in
  FROM   VTA_IMPR_LOCAL;

    SELECT to_number(trim(G.LLAVE_TAB_GRAL),'9999') INTO vHoras
      FROM PBL_TAB_GRAL G
     WHERE G.ID_TAB_GRAL = '333';

       BEGIN
         V_CORREO_LOCAL := RECEP_F_EMAIL_LOCAL(cCodGrupoCia_in,cCod_Local_in);
         EXCEPTION
         WHEN OTHERS THEN
         V_CORREO_LOCAL := '';
         DBMS_OUTPUT.PUT_LINE('ERROR V_CORREO_LOCAL '||V_CORREO_LOCAL||SQLERRM);
       END;
   OPEN curRecep FOR
    /* SELECT COD_GRUPO_CIA, COD_LOCAL, NRO_RECEP
       FROM LGT_RECEP_MERCADERIA RM
      WHERE RM.ESTADO = 'T'
        AND RM.IND_ENV_REPORTE = 'N'
        --AND RM.FEC_CREA_RECEP BETWEEN SYSDATE-(vHoras/24) AND SYSDATE +(vHoras)/24;
        AND SYSDATE > RM.FEC_CREA_RECEP +(vHoras/24);*/
        -- se valida que la fecha de envio sea mayor al limite para enviar reporte
     SELECT COD_GRUPO_CIA, COD_LOCAL, NRO_RECEP
       FROM LGT_RECEP_MERCADERIA RM
      WHERE RM.ESTADO = 'T'
        AND RM.IND_ENV_REPORTE = 'N'
        --AND RM.FEC_CREA_RECEP BETWEEN SYSDATE-(vHoras/24) AND SYSDATE +(vHoras)/24;
        --AND SYSDATE > RM.FEC_CREA_RECEP +(vHoras/24)
        AND RM.IND_AFEC_RECEP_CIEGA = 'S'
        --AND RM.NRO_RECEP IN ('0000000002','0000000005','0000000007')
        ORDER BY 2,3;

        LOOP
          FETCH curRecep
          INTO vCod_Grupo_cia, vCod_local, vNro_Recep;
          EXIT WHEN curRecep%NOTFOUND;
          RECEP_P_ENVIA_CORREO_DIFE(vCod_Grupo_cia,
                                           vCod_local,
                                           vNro_Recep);
          DBMS_OUTPUT.put_line('NRO RECEP: '|| vNro_Recep);

          --ACTUALIZA EL INDICADOR DE REPORTE LGT_RECEP_MERCADERIA
          UPDATE LGT_RECEP_MERCADERIA RM
          SET RM.IND_ENV_REPORTE = 'S',
              RM.FEC_ENV_REPORTE = SYSDATE  --GRABA FECHA ENVIA REPORTE
          WHERE RM.COD_GRUPO_CIA = vCod_Grupo_cia
          AND RM.COD_LOCAL = vCod_local
          AND RM.NRO_RECEP = vNro_Recep;

        COMMIT;

        END LOOP;

        --JCORTEZ    11/05/2010 carga de tablas resumem para
        OPEN curRecep2 FOR
          SELECT COD_GRUPO_CIA, COD_LOCAL, NRO_RECEP
          FROM LGT_RECEP_MERCADERIA RM
          WHERE RM.ESTADO = 'T'
          AND RM.COD_GRUPO_CIA=cCodGrupoCia_in
          AND RM.COD_LOCAL=cCod_Local_in
          AND RM.IND_ENV_REPORTE = 'S'
          AND RM.FEC_RECEP BETWEEN TRUNC(SYSDATE)-2 AND SYSDATE --rango de 2 dias
          AND RM.IND_AFEC_RECEP_CIEGA = 'S'
          ORDER BY 2,3;

        LOOP
        FETCH curRecep2
        INTO vCod_Grupo_cia, vCod_local, vNro_Recep;
        EXIT WHEN curRecep2%NOTFOUND;

          DELETE FROM RES_RECEP_MERCADERIA_DIFE A WHERE A.FEC_RECEP BETWEEN TRUNC(SYSDATE)-2 AND SYSDATE AND A.COD_LOCAL=vCod_local AND A.NRO_RECEP=vNro_Recep;
          DELETE FROM RES_RECEP_MERCA_POL_CANJ A WHERE A.FEC_RECEP BETWEEN TRUNC(SYSDATE)-2 AND SYSDATE AND A.COD_LOCAL=vCod_local AND A.NRO_RECEP=vNro_Recep;
          DELETE FROM RES_RECEP_MERCA_DETERIO A WHERE A.FEC_RECEP BETWEEN TRUNC(SYSDATE)-2 AND SYSDATE AND A.COD_LOCAL=vCod_local AND A.NRO_RECEP=vNro_Recep;

         COMMIT;

          DBMS_OUTPUT.put_line('curRecep2 NRO RECEP:--> '|| vNro_Recep);

          RECEP_P_ENVIA_CORREO_DIFE_2(vCod_Grupo_cia,vCod_local,vNro_Recep);
          COMMIT;

        END LOOP;

    DBMS_OUTPUT.put_line('NRO RECEP: '|| vNro_Recep);


    EXCEPTION
    WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.put_line('NRO RECEP: '|| vNro_Recep||' - '||SQLERRM);
  END;

/* *************************************************************************** */
  PROCEDURE RECEP_P_ENVIA_CORREO_DIFE(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR)
  AS
     v_vHtmlCorreo       VARCHAR2(32767):='';
     v_vCorreoDiferenciasDest   VARCHAR2(3000):='';
     v_vNombreArchivo    VARCHAR2(2000):='';
     n_Contador          NUMBER:=0;
     v_vDescLocal        PBL_LOCAL.DESC_CORTA_LOCAL%TYPE;
     v_IndEnvia CHAR(1) := 'S';
     nDiferencia number;
     v_CodProd_Old CHAR(6) := '';

     v_Fecha_Generado  DATE; --JMIRANDA 01.02.2010
     CURSOR cursorDiferencias IS
     SELECT PRODUCTO,  DESC_PROD/*,COD_BARRA*/, UNIDAD, LAB,
         CANT_CONTADA,
              CANT_ENTREGADA
              /*,
             (SELECT CASE
                       WHEN CANT < 0 THEN
                        CANT * (-1)
                       ELSE
                        CANT
                     END
                FROM DUAL) DIFERENCIA*/
                ,NUM_ENTREGA --JMIRANDA 14.01.10
        FROM (SELECT Q1.PROD PRODUCTO,
                     (Q2.CANT2 - Q2.CANT_CONTADA) CANT,
                     --Q1.COD_BARRA,
                     Q3.DESC_PROD DESC_PROD,
                     Q3.DESC_UNID_PRESENT UNIDAD,
                     (SELECT L.NOM_LAB
                        FROM LGT_LAB L
                       WHERE L.COD_LAB = Q3.COD_LAB) LAB,
                     Q1.SEC_CONTEO,
                     Q1.CANT1 CANTTOTAL_CONTADA,
                     Q2.CANT2 CANT_ENTREGADA,
                     Q2.CANT_CONTADA CANT_CONTADA,
                     Q2.NUM_ENTREGA NUM_ENTREGA --JMIRANDA 14.01.10
                FROM (SELECT A.COD_PROD PROD,
                             NVL(A.CANT_SEG_CONTEO, A.CANTIDAD) CANT1,
                             --A.COD_BARRA COD_BARRA,
                             A.SEC_CONTEO SEC_CONTEO
                        FROM LGT_PROD_CONTEO A --, LGT_NOTA_ES_DET B
                       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
                         AND A.COD_LOCAL = cCodLocal_in
                         AND A.NRO_RECEP = cNroRecepcion) Q1,

                     (SELECT B.COD_PROD PROD,
                             SUM(B.CANT_ENVIADA_MATR) CANT2,
                             ' ' COD_BARRA,
                             SUM(B.CANT_CONTADA) CANT_CONTADA
                             ,B.NUM_ENTREGA NUM_ENTREGA--JMIRANDA 14.01.10
                        FROM LGT_NOTA_ES_DET B
                       WHERE B.COD_GRUPO_CIA = cGrupoCia_in
                         AND B.COD_LOCAL = cCodLocal_in
                         AND B.NUM_ENTREGA || B.NUM_NOTA_ES IN
                             (SELECT C.NUM_ENTREGA || C.NUM_NOTA_ES
                                FROM LGT_RECEP_ENTREGA C
                               WHERE C.COD_GRUPO_CIA = cGrupoCia_in
                                 AND C.COD_LOCAL = cCodLocal_in
                                 AND C.NRO_RECEP = cNroRecepcion)
                       --GROUP BY B.COD_PROD,B.CANT_CONTADA) Q2,
                       GROUP BY B.COD_PROD,B.CANT_CONTADA,B.NUM_ENTREGA) Q2,
                     LGT_PROD Q3

               WHERE Q1.PROD = Q2.PROD
                 AND Q3.COD_GRUPO_CIA = cGrupoCia_in
                 AND Q3.COD_PROD = Q1.PROD
              /*GROUP BY Q1.PROD
                       HAVING (Q2.CANT2 - Q1.CANT1) >0*/
              )
       WHERE CANT > 0
          OR CANT < 0

      UNION

      SELECT   E.COD_PROD ,
      H.DESC_PROD ,
            -- E.COD_BARRA ,
             H.DESC_UNID_PRESENT ,
             (SELECT L.NOM_LAB FROM LGT_LAB L WHERE L.COD_LAB = H.COD_LAB),
             NVL(E.CANT_SEG_CONTEO,E.CANTIDAD),
             0,
--             NVL(E.CANT_SEG_CONTEO, E.CANTIDAD)
             '' --JMIRANDA 14.01.10
        FROM LGT_PROD_CONTEO E, LGT_PROD H
       WHERE E.COD_GRUPO_CIA = cGrupoCia_in
         AND E.COD_LOCAL = cCodLocal_in
         AND E.NRO_RECEP = cNroRecepcion
         AND E.COD_PROD NOT IN
             (SELECT F.COD_PROD
                FROM LGT_NOTA_ES_DET F, LGT_RECEP_ENTREGA G
               WHERE F.COD_GRUPO_CIA = cGrupoCia_in
                 AND F.COD_LOCAL = cCodLocal_in
                 AND G.NRO_RECEP = cNroRecepcion
                -- AND F.EST_NOTA_ES_DET = 'A'
                 AND F.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                 AND F.COD_LOCAL = G.COD_LOCAL
                 AND F.NUM_NOTA_ES = G.NUM_NOTA_ES
                 AND F.NUM_ENTREGA = G.NUM_ENTREGA
                 AND F.SEC_GUIA_REM = G.SEC_GUIA_REM)
         AND E.COD_GRUPO_CIA = H.COD_GRUPO_CIA
         AND E.COD_PROD = H.COD_PROD
         AND NVL(E.CANT_SEG_CONTEO, E.CANTIDAD) > 0

         UNION

        SELECT K.COD_PROD ,K.DESC_PROD ,K.DESC_UNID_PRESENT, (SELECT L.NOM_LAB FROM LGT_LAB L WHERE L.COD_LAB = K.COD_LAB),0, I.CANT_ENVIADA_MATR
        , I.NUM_ENTREGA --JMIRANDA 14.01.10
        FROM LGT_NOTA_ES_DET I, LGT_PROD K
        WHERE I.COD_GRUPO_CIA = cGrupoCia_in
        AND I.COD_LOCAL       = cCodLocal_in
        AND I.NUM_ENTREGA IN (SELECT J.NUM_ENTREGA FROM  LGT_RECEP_ENTREGA J
                              WHERE J.COD_GRUPO_CIA = cGrupoCia_in
                              AND J.COD_LOCAL = cCodLocal_in
                              AND J.NRO_RECEP = cNroRecepcion
                             )
        AND I.COD_PROD NOT IN (
                                SELECT K.COD_PROD FROM LGT_PROD_CONTEO K
                                WHERE K.COD_GRUPO_CIA = cGrupoCia_in
                                AND K.COD_LOCAL       = cCodLocal_in
                                AND K.NRO_RECEP       = cNroRecepcion
                              )
        AND I.COD_GRUPO_CIA = K.COD_GRUPO_CIA
        AND I.COD_PROD      = K.COD_PROD;


--JMIRANDA 01.02.10
   --203 DETERIORADOS , 206 CANJES PROX VENCIMIENTO, POLITICA DE CANJE
-- CURSOR DETERIORADOS
 /* CURSOR curProdTransfDeter IS
  SELECT pf.cod_prod, p.desc_prod, pf.cant_mov, pf.num_lote_prod LOTE, pf.fec_vcto VENC, C.TIP_MOT_NOTA_ES MOTIVO
    FROM lgt_recep_prod_transf pf, lgt_nota_es_cab c, lgt_prod p
   WHERE pf.cod_grupo_cia = cGrupoCia_in
     AND pf.cod_local = cCodLocal_in
     AND PF.NRO_RECEP = cNroRecepcion
     AND pf.est_transf = 'A'
     AND pf.cod_grupo_cia = c.cod_grupo_cia
     AND pf.cod_local = c.cod_local
     AND pf.num_nota_es = c.num_nota_es
     AND PF.COD_GRUPO_CIA = P.COD_GRUPO_CIA
     AND PF.COD_PROD = P.COD_PROD
     AND C.TIP_MOT_NOTA_ES = '203';
     */
     CURSOR curProdTransfDeter IS
      SELECT Q1.COD_PROD, Q1.DESC_PROD, Q1.CANT_MOV, Q1.LOTE, Q1.VENC, Q1.MOTIVO, Q2.LOTE_SAP, Q2.VCTO_SAP
      FROM
      (  SELECT pf.cod_prod, p.desc_prod, pf.cant_mov, pf.num_lote_prod LOTE, pf.fec_vcto VENC, C.TIP_MOT_NOTA_ES MOTIVO
        FROM lgt_recep_prod_transf pf, lgt_nota_es_cab c, lgt_prod p
       WHERE pf.cod_grupo_cia = cGrupoCia_in
         AND pf.cod_local = cCodLocal_in
         AND PF.NRO_RECEP = cNroRecepcion
         AND pf.est_transf = 'A'
         AND pf.cod_grupo_cia = c.cod_grupo_cia
         AND pf.cod_local = c.cod_local
         AND pf.num_nota_es = c.num_nota_es
         AND PF.COD_GRUPO_CIA = P.COD_GRUPO_CIA
         AND PF.COD_PROD = P.COD_PROD
         AND C.TIP_MOT_NOTA_ES = '203'
      ) Q1,
      (SELECT D.COD_PROD, D.NUM_LOTE_PROD LOTE_SAP, D.FEC_VCTO_PROD VCTO_SAP FROM LGT_NOTA_ES_DET D
        WHERE D.COD_GRUPO_CIA = cGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_ENTREGA IN (
          SELECT e.num_entrega FROM lgt_recep_entrega e WHERE e.nro_recep = cNroRecepcion)
      ) Q2
      WHERE Q1.COD_PROD = Q2.COD_PROD(+)
      ORDER BY Q1.COD_PROD ASC;

-- CURSOS POLITICA CANJE
/*
  CURSOR curProdTransfCanje IS
  SELECT pf.cod_prod, p.desc_prod, pf.cant_mov, pf.num_lote_prod LOTE, pf.fec_vcto VENC, C.TIP_MOT_NOTA_ES MOTIVO
    FROM lgt_recep_prod_transf pf, lgt_nota_es_cab c, lgt_prod p
   WHERE pf.cod_grupo_cia = cGrupoCia_in
     AND pf.cod_local = cCodLocal_in
     AND PF.NRO_RECEP = cNroRecepcion
     AND pf.est_transf = 'A'
     AND pf.cod_grupo_cia = c.cod_grupo_cia
     AND pf.cod_local = c.cod_local
     AND pf.num_nota_es = c.num_nota_es
     AND PF.COD_GRUPO_CIA = P.COD_GRUPO_CIA
     AND PF.COD_PROD = P.COD_PROD
     AND C.TIP_MOT_NOTA_ES = '206';
*/
    CURSOR curProdTransfCanje IS
    SELECT Q1.COD_PROD, Q1.DESC_PROD, Q1.CANT_MOV, Q1.LOTE, Q1.VENC, Q1.MOTIVO, Q2.LOTE_SAP, Q2.VCTO_SAP
    FROM
    (
      SELECT pf.cod_prod, p.desc_prod, pf.cant_mov, pf.num_lote_prod LOTE, pf.fec_vcto VENC, C.TIP_MOT_NOTA_ES MOTIVO
        FROM lgt_recep_prod_transf pf, lgt_nota_es_cab c, lgt_prod p
       WHERE pf.cod_grupo_cia = cGrupoCia_in
         AND pf.cod_local = cCodLocal_in
         AND PF.NRO_RECEP = cNroRecepcion
         AND pf.est_transf = 'A'
         AND pf.cod_grupo_cia = c.cod_grupo_cia
         AND pf.cod_local = c.cod_local
         AND pf.num_nota_es = c.num_nota_es
         AND PF.COD_GRUPO_CIA = P.COD_GRUPO_CIA
         AND PF.COD_PROD = P.COD_PROD
         AND C.TIP_MOT_NOTA_ES = '206'
    ) Q1,
    (SELECT D.COD_PROD, D.NUM_LOTE_PROD LOTE_SAP, D.FEC_VCTO_PROD VCTO_SAP FROM LGT_NOTA_ES_DET D
    WHERE D.COD_GRUPO_CIA = cGrupoCia_in
      AND D.COD_LOCAL = cCodLocal_in
      AND D.NUM_ENTREGA IN (
        SELECT e.num_entrega FROM lgt_recep_entrega e WHERE e.nro_recep = cNroRecepcion)
      ) Q2
    WHERE Q1.COD_PROD = Q2.COD_PROD (+)
    ORDER BY Q1.COD_PROD ASC;

  BEGIN
       DBMS_OUTPUT.put_line('ENVIA CORREO');
       SELECT TRIM(X.LLAVE_TAB_GRAL) INTO v_vCorreoDiferenciasDest
      FROM PBL_TAB_GRAL X
      WHERE X.ID_TAB_GRAL='321';
      --JMIRANDA 01.02.2010
      SELECT TRIM(R.HORA_LLEGADA) INTO v_Fecha_Generado
        FROM LGT_RECEP_MERCADERIA R
        WHERE R.COD_GRUPO_CIA = cGrupoCia_in
          AND R.COD_LOCAL = cCodLocal_in
          AND R.NRO_RECEP = cNroRecepcion;

     SELECT A.DESC_CORTA_LOCAL INTO v_vDescLocal FROM PBL_LOCAL A WHERE A.COD_GRUPO_CIA =cGrupoCia_in  AND A.COD_LOCAL =cCodLocal_in;
       --v_vNombreArchivo := 'ALERTA_DIFERENCIAS_CONEO_'||to_char(sysdate,'ddmmyyyy') ||'_'||cNroRecepcion ||'.xls';
       --v_vNombreArchivo := 'DIF_'||to_char(sysdate,'ddmmyyyy') ||'_'||cNroRecepcion ||'.xls';
       --JMIRANDA 01.02.2010
--JMIRANDA 10.03.2010 VERIFICA SI NO TIENE DATOS
    v_IndEnvia := RECEP_F_CHAR_IND_TIENE_DATA(cGrupoCia_in,cCodLocal_in, cNroRecepcion);
    IF (v_IndEnvia = 'S') THEN
       v_vNombreArchivo := 'DIF_'||to_char(v_Fecha_Generado,'ddmmyyyy') ||'_'||cCodLocal_in||cNroRecepcion ||'.xls';
       ARCHIVO_TEXTO := UTL_FILE.FOPEN(v_gNombreDiretorioAlert,TRIM(v_vNombreArchivo),'W');

        v_vHtmlCorreo:='<html>'||
                       '<head>'||
                        '  <meta http-equiv="Content-Type" content="application/vnd.ms-excel">'||
                        '  <title>DIFERENCIAS EN CONTEO</title>'||
                        '</head>'||
                        '<body>'||
                        --JMIRANDA 01.02.10
                        '<h3 align="center">PRODUCTOS CON DIFERENCIAS LOCAL '||cCodLocal_in||'-'||v_vDescLocal||--'</h3>'||
                        '<br>RECEPCIÓN NRO: '||cNroRecepcion||--||'</h3>'||
--                        '<h3 align="center">Fecha : '||to_char(sysdate,'dd/mm/yyyy')||' </h3>'||
                        --'<h3 align="center">Fecha : '||to_char(v_Fecha_Generado,'dd/mm/yyyy')||' </h3>'||
                        '<br>Fecha : '||to_char(v_Fecha_Generado,'dd/mm/yyyy')||' </h3>'||
                        '<table style="text-align: left; width: 100%;" border="1"'||
                        ' cellpadding="2" cellspacing="1">'||
                        '    <tr>'||
                        '      <th><small>CODIGO</small></th>'||
                        '      <th><small>DESCRIPCION DEL PRODUCTO</small></th>'||
                      --  '      <th><small>CODIGO DE BARRA</small></th>'||
                        '      <th><small>UNIDAD DE PRESENTACION</small></th>'||
                        '      <th><small>LABORATORIO</small></th>'||
                        '      <th><small>CANTIDAD CONTADA</small></th>'||
                        '      <th><small>CANTIDAD ENTREGADA</small></th>'||
                        '      <th><small>NUM. ENTREGA</small></th>'||
                        '      <th><small>TIPO</small></th>'||
                        '    </tr>';

       UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);
            DBMS_OUTPUT.put_line('v_vHtmlCorreo: ' || v_vHtmlCorreo);
       FOR diferencias IN cursorDiferencias
       LOOP
              n_Contador:=n_Contador+1;
              v_vHtmlCorreo:='<tr>'||
                             '    <td align="right">'||diferencias.PRODUCTO||'</td>'||
                             '    <td align="left">'||diferencias.Desc_prod||'</td>'||
                           --  '    <td align="left">'||diferencias.COD_BARRA||'</td>'||
                             '    <td align="left">'||diferencias.UNIDAD||'</td>'||
                             '    <td align="left">'||diferencias.LAB||'</td>'||
                             '    <td align="left">'||diferencias.CANT_CONTADA||'</td>'||
                             '    <td align="left">'||diferencias.CANT_ENTREGADA||'</td>'||
                             '    <td align="left">'||diferencias.NUM_ENTREGA||'</td>';

                             nDiferencia  :=  diferencias.CANT_ENTREGADA - diferencias.CANT_CONTADA;

                             if nDiferencia < 0 then
                             v_vHtmlCorreo := v_vHtmlCorreo  || '    <td align="left">'||'Sobrante'||'</td>';
                             else
                             v_vHtmlCorreo := v_vHtmlCorreo  || '    <td align="left">'||'Faltante'||'</td>';
                             end if;
                         --    '    <td align="left">'||diferencias.DIFERENCIA||'</td>'||

                             v_vHtmlCorreo := v_vHtmlCorreo  || '</tr>';
               UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);
       END LOOP;

        DBMS_OUTPUT.put_line('v_vHtmlCorreo: ' || v_vHtmlCorreo);
       DBMS_OUTPUT.put_line('n_Contador: ' || n_Contador);
          v_vHtmlCorreo:=' </table>';
          UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);  --TERMINA LA PRIMERA TABLA

       --JMIRANDA 01.02.10 AGREGA PROD TRANSF POLITICA CANJE Y DETERIORADOS
          v_vHtmlCorreo:='<h3 align="left">DEVOLUCIÓN POR FUERA DE POLÍTICA DE CANJE</h3>'||
                        '<table style="text-align: left; width: 100%;" border="1"'||
                        ' cellpadding="2" cellspacing="1">'||
                        '    <tr>'||
                        '      <th><small>CODIGO</small></th>'||
                        '      <th><small>DESCRIPCION DEL PRODUCTO</small></th>'||
                        '      <th><small>CANTIDAD MOVIMIENTO</small></th>'||
                        '      <th><small>LOTE</small></th>'||
                        '      <th><small>FEC. VENCIMIENTO</small></th>'||
                        '      <th><small>LOTE SAP</small></th>'||
                        '      <th><small>FEC. VENC. SAP</small></th>'||
                        --'      <th><small>MOTIVO</small></th>'||
                        '    </tr>';
       UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);

        v_CodProd_Old := '';
       FOR poliCanje IN curProdTransfCanje
       LOOP
         IF v_CodProd_Old = poliCanje.Cod_Prod THEN
           v_vHtmlCorreo:='<tr>'||
                          '    <td align="left">&nbsp;</td>'||
                          '    <td align="left">&nbsp;</td>'||
                          '    <td align="left">&nbsp;</td>'||
                          '    <td align="left">&nbsp;</td>'||
                          '    <td align="left">&nbsp;</td>'||
                          '    <td align="left">'||poliCanje.Lote_Sap||'</td>'||
                          '    <td align="left">'||poliCanje.Vcto_Sap||'</td>'
                          ;
         ELSE
           v_vHtmlCorreo:='<tr>'||
                          '    <td align="left">'||poliCanje.Cod_Prod||'</td>'||
                          '    <td align="left">'||poliCanje.Desc_Prod||'</td>'||
                          '    <td align="left">'||poliCanje.Cant_Mov||'</td>'||
                          '    <td align="left">'||poliCanje.Lote||'</td>'||
                          '    <td align="left">'||poliCanje.Venc||'</td>'||
                          '    <td align="left">'||poliCanje.Lote_Sap||'</td>'||
                          '    <td align="left">'||poliCanje.Vcto_Sap||'</td>'
                          ;
         END IF;
         v_CodProd_Old := poliCanje.Cod_Prod;
--                        '    <td align="left">'||poliCanje.Motivo||'</td>';
             v_vHtmlCorreo := v_vHtmlCorreo  || '</tr>';
               UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);
       END LOOP;
                v_vHtmlCorreo:=' </table> ';
          UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);         --TERMINA LA SEGUNDA TABLA

          v_vHtmlCorreo:='<h3 align="left">DEVOLUCIÓN POR DETERIORADOS</h3>'||
                        '<table style="text-align: left; width: 100%;" border="1"'||
                        ' cellpadding="2" cellspacing="1">'||
                        '    <tr>'||
                        '      <th><small>CODIGO</small></th>'||
                        '      <th><small>DESCRIPCION DEL PRODUCTO</small></th>'||
                        '      <th><small>CANTIDAD MOVIMIENTO</small></th>'||
                        '      <th><small>LOTE</small></th>'||
                        '      <th><small>FEC. VENCIMIENTO</small></th>'||
                        '      <th><small>LOTE SAP</small></th>'||
                        '      <th><small>FEC. VENC. SAP</small></th>'||
                        --'      <th><small>MOTIVO</small></th>'||
                        '    </tr>';
       UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);

       v_CodProd_Old := '';
       FOR deteriorados IN curProdTransfDeter
       LOOP
         IF v_CodProd_Old = deteriorados.cod_prod THEN
           v_vHtmlCorreo:='<tr>'||
                          '    <td align="left">&nbsp;</td>'||
                          '    <td align="left">&nbsp;</td>'||
                          '    <td align="left">&nbsp;</td>'||
                          '    <td align="left">&nbsp;</td>'||
                          '    <td align="left">&nbsp;</td>'||
                          '    <td align="left">'||deteriorados.Lote_Sap||'</td>'||
                          '    <td align="left">'||deteriorados.Vcto_Sap||'</td>';
         ELSE
         v_vHtmlCorreo:='<tr>'||
                        '    <td align="left">'||deteriorados.cod_prod||'</td>'||
                        '    <td align="left">'||deteriorados.desc_prod||'</td>'||
                        '    <td align="left">'||deteriorados.cant_mov||'</td>'||
                        '    <td align="left">'||deteriorados.Lote||'</td>'||
                        '    <td align="left">'||deteriorados.Venc||'</td>'||
                        '    <td align="left">'||deteriorados.Lote_Sap||'</td>'||
                        '    <td align="left">'||deteriorados.Vcto_Sap||'</td>'
                        ;
         END IF;
        v_CodProd_Old := deteriorados.cod_prod;
--                        '    <td align="left">'||deteriorados.Motivo||'</td>';
             v_vHtmlCorreo := v_vHtmlCorreo  || '</tr>';
               UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);
       END LOOP;
                v_vHtmlCorreo:=' </table> ';
          UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);         --TERMINA LA TERCERA TABLA


-- CIERRA
     --   v_vHtmlCorreo:=' </table> '||
          v_vHtmlCorreo:=' </body> '||
                       ' </html> ';

       UTL_FILE.put_line(ARCHIVO_TEXTO,v_vHtmlCorreo);
       UTL_FILE.FCLOSE(ARCHIVO_TEXTO);

--      SELECT A.DESC_CORTA_LOCAL INTO v_vDescLocal FROM PBL_LOCAL A WHERE A.COD_GRUPO_CIA =cGrupoCia_in  AND A.COD_LOCAL =cCodLocal_in;
      RECEP_P_ENVIA_CORREO_ADJUNTO(
                            --'PRODUCTOS CON DIFERENCIAS EN RECEPCIÓN '||TO_CHAR(SYSDATE,'DD/MM/YYYY') || '-'|| v_vDescLocal,
                            --'<BR>' || 'PRODUCTOS CON DIFERENCIAS EN CONTEO ' || '<BR>'  ||'RECEPCIÓN Nro. ' || cNroRecepcion || '<BR>' || 'DIA: '||TO_CHAR(SYSDATE,'DD/MM/YYYY HH24:mi:ss') || '<BR>' || 'LOCAL: ' ||v_vDescLocal,
                            --'lista de productos que presentan diferencias en el conteo '||TO_CHAR(SYSDATE,'DD/MM/YYYY'),
                            --JMIRANDA 01.02.10
                            'PRODUCTOS CON DIFERENCIAS EN RECEPCIÓN '||TO_CHAR(v_Fecha_Generado,'DD/MM/YYYY') || ' LOCAL: '||cCodLocal_in||'-'|| v_vDescLocal,
                            --'<BR>' || 'PRODUCTOS CON DIFERENCIAS EN CONTEO ' || '<BR>'  ||'RECEPCIÓN Nro. ' || cNroRecepcion || '<BR>' || 'DIA: '||TO_CHAR(SYSDATE,'DD/MM/YYYY HH24:mi:ss') || '<BR>' || 'LOCAL: '||cCodLocal_in||'-'|| v_vDescLocal,
                            '<BR>' || 'PRODUCTOS CON DIFERENCIAS EN CONTEO ' || '<BR>'  ||'RECEPCIÓN Nro. ' || cNroRecepcion || '<BR>' || 'DIA: '||TO_CHAR(v_Fecha_Generado,'DD/MM/YYYY HH24:mi:ss') || '<BR>' || 'LOCAL: '||cCodLocal_in||'-'|| v_vDescLocal,
                            'Lista de productos que presentan diferencias en el conteo '||TO_CHAR(v_Fecha_Generado,'DD/MM/YYYY'),
                            'S',v_vNombreArchivo);

      ELSE
       --NO TIENE DATOS ENVIA A DU
       RECEP_P_ENVIA_CORREO_ADJUNTO(
                            'PRODUCTOS SIN DIFERENCIAS EN RECEPCIÓN '||TO_CHAR(v_Fecha_Generado,'DD/MM/YYYY') || ' LOCAL: '||cCodLocal_in||'-'|| v_vDescLocal,
                            '<BR>' || 'PRODUCTOS SIN DIFERENCIAS EN CONTEO ' || '<BR>'  ||'RECEPCIÓN Nro. ' || cNroRecepcion || '<BR>' || 'DIA: '||TO_CHAR(v_Fecha_Generado,'DD/MM/YYYY HH24:mi:ss') || '<BR>' || 'LOCAL: '||cCodLocal_in||'-'|| v_vDescLocal,
                            'Está recepción no presentan Diferencias. '||TO_CHAR(v_Fecha_Generado,'DD/MM/YYYY'),
                            'N',NULL);

      END IF;
  END;
/* *********************************************************************** */
  PROCEDURE RECEP_P_ENVIA_CORREO_ADJUNTO(vAsunto_in        IN CHAR,
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

      SELECT TRIM(LLAVE_TAB_GRAL)  INTO  ReceiverAddress
      FROM PBL_TAB_GRAL
      WHERE ID_TAB_GRAL='321';

      --DBMS_OUTPUT.put_line('correo ' ||ReceiverAddress);
      IF vInd_Archivo_in = 'N' THEN
        SELECT TRIM(l.llave_tab_gral) INTO ReceiverAddress
          FROM pbl_tab_gral l
         WHERE l.Id_Tab_Gral = '343';

         ReceiverAddress := ReceiverAddress||';'||V_CORREO_LOCAL;
        FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                 ReceiverAddress,
                                 vAsunto_in,
                                 vTitulo_in,
                                 mesg_body,
                                 CCReceiverAddress,
                                 FARMA_EMAIL.GET_EMAIL_SERVER,
                                 true);
      ELSE
        ReceiverAddress := ReceiverAddress||';'||V_CORREO_LOCAL;
        DBMS_OUTPUT.put_line('vNombre_Archivo_in '|| vNombre_Archivo_in);
      ENVIA_CORREO_ATTACH3(
                               FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                               ReceiverAddress,
                               --'DUBILLUZ@MIFARMA.COM.PE',
                               vAsunto_in,
                               vTitulo_in,
                               mesg_body,
                               v_gNombreDiretorioAlert,--
                               vNombre_Archivo_in,
                               CCReceiverAddress,
                               --'JMELGAR@MIFARMA.COM.PE',
                               FARMA_EMAIL.GET_EMAIL_SERVER);
     DBMS_OUTPUT.put_line('Envio archivo');
      END IF;

    END;
---------------------------------------------------------------------------------------
FUNCTION RECEP_F_CHAR_IND_TIENE_DATA(cGrupoCia_in  CHAR,
                                     cCodLocal_in CHAR,
                                     cNroRecepcion CHAR)
RETURN CHAR
IS
 vNumDife NUMBER := 0;
 vNumDete NUMBER := 0;
 vNumPoli NUMBER := 0;
 vRpta CHAR(1) := 'N';
BEGIN


  BEGIN
    SELECT COUNT(1) INTO vNumDife
    FROM
     (SELECT PRODUCTO,  DESC_PROD/*,COD_BARRA*/, UNIDAD, LAB,
         CANT_CONTADA,
              CANT_ENTREGADA
              /*,
             (SELECT CASE
                       WHEN CANT < 0 THEN
                        CANT * (-1)
                       ELSE
                        CANT
                     END
                FROM DUAL) DIFERENCIA*/
                ,NUM_ENTREGA --JMIRANDA 14.01.10
        FROM (SELECT Q1.PROD PRODUCTO,
                     (Q2.CANT2 - Q2.CANT_CONTADA) CANT,
                     --Q1.COD_BARRA,
                     Q3.DESC_PROD DESC_PROD,
                     Q3.DESC_UNID_PRESENT UNIDAD,
                     (SELECT L.NOM_LAB
                        FROM LGT_LAB L
                       WHERE L.COD_LAB = Q3.COD_LAB) LAB,
                     Q1.SEC_CONTEO,
                     Q1.CANT1 CANTTOTAL_CONTADA,
                     Q2.CANT2 CANT_ENTREGADA,
                     Q2.CANT_CONTADA CANT_CONTADA,
                     Q2.NUM_ENTREGA NUM_ENTREGA --JMIRANDA 14.01.10
                FROM (SELECT A.COD_PROD PROD,
                             NVL(A.CANT_SEG_CONTEO, A.CANTIDAD) CANT1,
                             --A.COD_BARRA COD_BARRA,
                             A.SEC_CONTEO SEC_CONTEO
                        FROM LGT_PROD_CONTEO A --, LGT_NOTA_ES_DET B
                       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
                         AND A.COD_LOCAL = cCodLocal_in
                         AND A.NRO_RECEP = cNroRecepcion) Q1,

                     (SELECT B.COD_PROD PROD,
                             SUM(B.CANT_ENVIADA_MATR) CANT2,
                             ' ' COD_BARRA,
                             SUM(B.CANT_CONTADA) CANT_CONTADA
                             ,B.NUM_ENTREGA NUM_ENTREGA--JMIRANDA 14.01.10
                        FROM LGT_NOTA_ES_DET B
                       WHERE B.COD_GRUPO_CIA = cGrupoCia_in
                         AND B.COD_LOCAL = cCodLocal_in
                         AND B.NUM_ENTREGA || B.NUM_NOTA_ES IN
                             (SELECT C.NUM_ENTREGA || C.NUM_NOTA_ES
                                FROM LGT_RECEP_ENTREGA C
                               WHERE C.COD_GRUPO_CIA = cGrupoCia_in
                                 AND C.COD_LOCAL = cCodLocal_in
                                 AND C.NRO_RECEP = cNroRecepcion)
                       --GROUP BY B.COD_PROD,B.CANT_CONTADA) Q2,
                       GROUP BY B.COD_PROD,B.CANT_CONTADA,B.NUM_ENTREGA) Q2,
                     LGT_PROD Q3

               WHERE Q1.PROD = Q2.PROD
                 AND Q3.COD_GRUPO_CIA = cGrupoCia_in
                 AND Q3.COD_PROD = Q1.PROD
              /*GROUP BY Q1.PROD
                       HAVING (Q2.CANT2 - Q1.CANT1) >0*/
              )
       WHERE CANT > 0
          OR CANT < 0

      UNION

      SELECT   E.COD_PROD ,
      H.DESC_PROD ,
            -- E.COD_BARRA ,
             H.DESC_UNID_PRESENT ,
             (SELECT L.NOM_LAB FROM LGT_LAB L WHERE L.COD_LAB = H.COD_LAB),
             NVL(E.CANT_SEG_CONTEO,E.CANTIDAD),
             0,
--             NVL(E.CANT_SEG_CONTEO, E.CANTIDAD)
             '' --JMIRANDA 14.01.10
        FROM LGT_PROD_CONTEO E, LGT_PROD H
       WHERE E.COD_GRUPO_CIA = cGrupoCia_in
         AND E.COD_LOCAL = cCodLocal_in
         AND E.NRO_RECEP = cNroRecepcion
         AND E.COD_PROD NOT IN
             (SELECT F.COD_PROD
                FROM LGT_NOTA_ES_DET F, LGT_RECEP_ENTREGA G
               WHERE F.COD_GRUPO_CIA = cGrupoCia_in
                 AND F.COD_LOCAL = cCodLocal_in
                 AND G.NRO_RECEP = cNroRecepcion
                -- AND F.EST_NOTA_ES_DET = 'A'
                 AND F.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                 AND F.COD_LOCAL = G.COD_LOCAL
                 AND F.NUM_NOTA_ES = G.NUM_NOTA_ES
                 AND F.NUM_ENTREGA = G.NUM_ENTREGA
                 AND F.SEC_GUIA_REM = G.SEC_GUIA_REM)
         AND E.COD_GRUPO_CIA = H.COD_GRUPO_CIA
         AND E.COD_PROD = H.COD_PROD
         AND NVL(E.CANT_SEG_CONTEO, E.CANTIDAD) > 0

         UNION

        SELECT K.COD_PROD ,K.DESC_PROD ,K.DESC_UNID_PRESENT, (SELECT L.NOM_LAB FROM LGT_LAB L WHERE L.COD_LAB = K.COD_LAB),0, I.CANT_ENVIADA_MATR
        , I.NUM_ENTREGA --JMIRANDA 14.01.10
        FROM LGT_NOTA_ES_DET I, LGT_PROD K
        WHERE I.COD_GRUPO_CIA = cGrupoCia_in
        AND I.COD_LOCAL       = cCodLocal_in
        AND I.NUM_ENTREGA IN (SELECT J.NUM_ENTREGA FROM  LGT_RECEP_ENTREGA J
                              WHERE J.COD_GRUPO_CIA = cGrupoCia_in
                              AND J.COD_LOCAL = cCodLocal_in
                              AND J.NRO_RECEP = cNroRecepcion
                             )
        AND I.COD_PROD NOT IN (
                                SELECT K.COD_PROD FROM LGT_PROD_CONTEO K
                                WHERE K.COD_GRUPO_CIA = cGrupoCia_in
                                AND K.COD_LOCAL       = cCodLocal_in
                                AND K.NRO_RECEP       = cNroRecepcion
                              )
        AND I.COD_GRUPO_CIA = K.COD_GRUPO_CIA
        AND I.COD_PROD      = K.COD_PROD
      );
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
     vNumDife := 0;
  END;

--JMIRANDA 01.02.10
   --203 DETERIORADOS , 206 CANJES PROX VENCIMIENTO, POLITICA DE CANJE
--DETERIORADOS
 BEGIN
  SELECT COUNT(1) INTO vNumDete
  --pf.cod_prod, p.desc_prod, pf.cant_mov, pf.num_lote_prod LOTE, pf.fec_vcto VENC, C.TIP_MOT_NOTA_ES MOTIVO
    FROM lgt_recep_prod_transf pf, lgt_nota_es_cab c, lgt_prod p
   WHERE pf.cod_grupo_cia = cGrupoCia_in
     AND pf.cod_local = cCodLocal_in
     AND PF.NRO_RECEP = cNroRecepcion
     AND pf.est_transf = 'A'
     AND pf.cod_grupo_cia = c.cod_grupo_cia
     AND pf.cod_local = c.cod_local
     AND pf.num_nota_es = c.num_nota_es
     AND PF.COD_GRUPO_CIA = P.COD_GRUPO_CIA
     AND PF.COD_PROD = P.COD_PROD
     AND C.TIP_MOT_NOTA_ES = '203';
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
     vNumDete := 0;
  END;
-- CURSOS POLITICA CANJE
  BEGIN
  SELECT COUNT(1) INTO vNumPoli
  --pf.cod_prod, p.desc_prod, pf.cant_mov, pf.num_lote_prod LOTE, pf.fec_vcto VENC, C.TIP_MOT_NOTA_ES MOTIVO
    FROM lgt_recep_prod_transf pf, lgt_nota_es_cab c, lgt_prod p
   WHERE pf.cod_grupo_cia = cGrupoCia_in
     AND pf.cod_local = cCodLocal_in
     AND PF.NRO_RECEP = cNroRecepcion
     AND pf.est_transf = 'A'
     AND pf.cod_grupo_cia = c.cod_grupo_cia
     AND pf.cod_local = c.cod_local
     AND pf.num_nota_es = c.num_nota_es
     AND PF.COD_GRUPO_CIA = P.COD_GRUPO_CIA
     AND PF.COD_PROD = P.COD_PROD
     AND C.TIP_MOT_NOTA_ES = '206';

   EXCEPTION
   WHEN NO_DATA_FOUND THEN
    vNumPoli := 0;
  END;
     IF(vNumPoli = 0 AND (vNumDete = 0 AND vNumDife = 0)) THEN
      vRpta := 'N';
     ELSE
      vRpta := 'S';
     END IF;

   RETURN vRpta;
END;



/* *************************************************************************** */
  PROCEDURE RECEP_P_ENVIA_CORREO_DIFE_2(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR)
  AS
     v_vHtmlCorreo       VARCHAR2(32767):='';
     v_vCorreoDiferenciasDest   VARCHAR2(100):='';
     v_vNombreArchivo    VARCHAR2(100):='';
     n_Contador          NUMBER:=0;
     v_vDescLocal        PBL_LOCAL.DESC_CORTA_LOCAL%TYPE;
     v_IndEnvia CHAR(1) := 'S';
     nDiferencia number;
     v_CodProd_Old CHAR(6) := '';

     v_Fecha_Generado  DATE;

     Tipo VARCHAR2(10);
     Usuario VARCHAR2(20):='PTOVENTA_CIEGA_JOB';

     CURSOR cursorDiferencias IS
     SELECT PRODUCTO,  DESC_PROD, UNIDAD, LAB,
         CANT_CONTADA,
              CANT_ENTREGADA
                ,NUM_ENTREGA
        FROM (SELECT Q1.PROD PRODUCTO,
                     (Q2.CANT2 - Q2.CANT_CONTADA) CANT,
                     Q3.DESC_PROD DESC_PROD,
                     Q3.DESC_UNID_PRESENT UNIDAD,
                     (SELECT L.NOM_LAB
                        FROM LGT_LAB L
                       WHERE L.COD_LAB = Q3.COD_LAB) LAB,
                     Q1.SEC_CONTEO,
                     Q1.CANT1 CANTTOTAL_CONTADA,
                     Q2.CANT2 CANT_ENTREGADA,
                     Q2.CANT_CONTADA CANT_CONTADA,
                     Q2.NUM_ENTREGA NUM_ENTREGA
                FROM (SELECT A.COD_PROD PROD,
                             NVL(A.CANT_SEG_CONTEO, A.CANTIDAD) CANT1,
                             A.SEC_CONTEO SEC_CONTEO
                        FROM LGT_PROD_CONTEO A
                       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
                         AND A.COD_LOCAL = cCodLocal_in
                         AND A.NRO_RECEP = cNroRecepcion) Q1,
                     (SELECT B.COD_PROD PROD,
                             SUM(B.CANT_ENVIADA_MATR) CANT2,
                             ' ' COD_BARRA,
                             SUM(B.CANT_CONTADA) CANT_CONTADA
                             ,B.NUM_ENTREGA NUM_ENTREGA
                        FROM LGT_NOTA_ES_DET B
                       WHERE B.COD_GRUPO_CIA = cGrupoCia_in
                         AND B.COD_LOCAL = cCodLocal_in
                         AND B.NUM_ENTREGA || B.NUM_NOTA_ES IN
                             (SELECT C.NUM_ENTREGA || C.NUM_NOTA_ES
                                FROM LGT_RECEP_ENTREGA C
                               WHERE C.COD_GRUPO_CIA = cGrupoCia_in
                                 AND C.COD_LOCAL = cCodLocal_in
                                 AND C.NRO_RECEP = cNroRecepcion)
                       GROUP BY B.COD_PROD,B.CANT_CONTADA,B.NUM_ENTREGA) Q2,
                     LGT_PROD Q3

               WHERE Q1.PROD = Q2.PROD
                 AND Q3.COD_GRUPO_CIA = cGrupoCia_in
                 AND Q3.COD_PROD = Q1.PROD
              )
       WHERE CANT > 0
          OR CANT < 0

      UNION

      SELECT   E.COD_PROD ,
      H.DESC_PROD ,
             H.DESC_UNID_PRESENT ,
             (SELECT L.NOM_LAB FROM LGT_LAB L WHERE L.COD_LAB = H.COD_LAB),
             NVL(E.CANT_SEG_CONTEO,E.CANTIDAD),
             0,
             ''
        FROM LGT_PROD_CONTEO E, LGT_PROD H
       WHERE E.COD_GRUPO_CIA = cGrupoCia_in
         AND E.COD_LOCAL = cCodLocal_in
         AND E.NRO_RECEP = cNroRecepcion
         AND E.COD_PROD NOT IN
             (SELECT F.COD_PROD
                FROM LGT_NOTA_ES_DET F, LGT_RECEP_ENTREGA G
               WHERE F.COD_GRUPO_CIA = cGrupoCia_in
                 AND F.COD_LOCAL = cCodLocal_in
                 AND G.NRO_RECEP = cNroRecepcion
                 AND F.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                 AND F.COD_LOCAL = G.COD_LOCAL
                 AND F.NUM_NOTA_ES = G.NUM_NOTA_ES
                 AND F.NUM_ENTREGA = G.NUM_ENTREGA
                 AND F.SEC_GUIA_REM = G.SEC_GUIA_REM)
         AND E.COD_GRUPO_CIA = H.COD_GRUPO_CIA
         AND E.COD_PROD = H.COD_PROD
         AND NVL(E.CANT_SEG_CONTEO, E.CANTIDAD) > 0

         UNION

        SELECT K.COD_PROD ,K.DESC_PROD ,K.DESC_UNID_PRESENT, (SELECT L.NOM_LAB FROM LGT_LAB L WHERE L.COD_LAB = K.COD_LAB),0, I.CANT_ENVIADA_MATR
        , I.NUM_ENTREGA
        FROM LGT_NOTA_ES_DET I, LGT_PROD K
        WHERE I.COD_GRUPO_CIA = cGrupoCia_in
        AND I.COD_LOCAL       = cCodLocal_in
        AND I.NUM_ENTREGA IN (SELECT J.NUM_ENTREGA FROM  LGT_RECEP_ENTREGA J
                              WHERE J.COD_GRUPO_CIA = cGrupoCia_in
                              AND J.COD_LOCAL = cCodLocal_in
                              AND J.NRO_RECEP = cNroRecepcion
                             )
        AND I.COD_PROD NOT IN (
                                SELECT K.COD_PROD FROM LGT_PROD_CONTEO K
                                WHERE K.COD_GRUPO_CIA = cGrupoCia_in
                                AND K.COD_LOCAL       = cCodLocal_in
                                AND K.NRO_RECEP       = cNroRecepcion
                              )
        AND I.COD_GRUPO_CIA = K.COD_GRUPO_CIA
        AND I.COD_PROD      = K.COD_PROD;


    --203 DETERIORADOS , 206 CANJES PROX VENCIMIENTO, POLITICA DE CANJE
    -- CURSOR DETERIORADOS

     CURSOR curProdTransfDeter IS
      SELECT Q1.COD_PROD, Q1.DESC_PROD, Q1.CANT_MOV, Q1.LOTE, Q1.VENC, Q1.MOTIVO, Q2.LOTE_SAP, Q2.VCTO_SAP
      FROM
      (  SELECT pf.cod_prod, p.desc_prod, pf.cant_mov, pf.num_lote_prod LOTE, pf.fec_vcto VENC, C.TIP_MOT_NOTA_ES MOTIVO
        FROM lgt_recep_prod_transf pf, lgt_nota_es_cab c, lgt_prod p
       WHERE pf.cod_grupo_cia = cGrupoCia_in
         AND pf.cod_local = cCodLocal_in
         AND PF.NRO_RECEP = cNroRecepcion
         AND pf.est_transf = 'A'
         AND pf.cod_grupo_cia = c.cod_grupo_cia
         AND pf.cod_local = c.cod_local
         AND pf.num_nota_es = c.num_nota_es
         AND PF.COD_GRUPO_CIA = P.COD_GRUPO_CIA
         AND PF.COD_PROD = P.COD_PROD
         AND C.TIP_MOT_NOTA_ES = '203'
      ) Q1,
      (SELECT D.COD_PROD, D.NUM_LOTE_PROD LOTE_SAP, D.FEC_VCTO_PROD VCTO_SAP FROM LGT_NOTA_ES_DET D
        WHERE D.COD_GRUPO_CIA = cGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_ENTREGA IN (
          SELECT e.num_entrega FROM lgt_recep_entrega e WHERE e.nro_recep = cNroRecepcion)
      ) Q2
      WHERE Q1.COD_PROD = Q2.COD_PROD(+)
      ORDER BY Q1.COD_PROD ASC;

    -- CURSOS POLITICA CANJE
    CURSOR curProdTransfCanje IS
    SELECT Q1.COD_PROD, Q1.DESC_PROD, Q1.CANT_MOV, Q1.LOTE, Q1.VENC, Q1.MOTIVO, Q2.LOTE_SAP, Q2.VCTO_SAP
    FROM
    (
      SELECT pf.cod_prod, p.desc_prod, pf.cant_mov, pf.num_lote_prod LOTE, pf.fec_vcto VENC, C.TIP_MOT_NOTA_ES MOTIVO
        FROM lgt_recep_prod_transf pf, lgt_nota_es_cab c, lgt_prod p
       WHERE pf.cod_grupo_cia = cGrupoCia_in
         AND pf.cod_local = cCodLocal_in
         AND PF.NRO_RECEP = cNroRecepcion
         AND pf.est_transf = 'A'
         AND pf.cod_grupo_cia = c.cod_grupo_cia
         AND pf.cod_local = c.cod_local
         AND pf.num_nota_es = c.num_nota_es
         AND PF.COD_GRUPO_CIA = P.COD_GRUPO_CIA
         AND PF.COD_PROD = P.COD_PROD
         AND C.TIP_MOT_NOTA_ES = '206'
    ) Q1,
    (SELECT D.COD_PROD, D.NUM_LOTE_PROD LOTE_SAP, D.FEC_VCTO_PROD VCTO_SAP FROM LGT_NOTA_ES_DET D
    WHERE D.COD_GRUPO_CIA = cGrupoCia_in
      AND D.COD_LOCAL = cCodLocal_in
      AND D.NUM_ENTREGA IN (
        SELECT e.num_entrega FROM lgt_recep_entrega e WHERE e.nro_recep = cNroRecepcion)
      ) Q2
    WHERE Q1.COD_PROD = Q2.COD_PROD (+)
    ORDER BY Q1.COD_PROD ASC;

  BEGIN

        SELECT R.HORA_LLEGADA INTO v_Fecha_Generado
        FROM LGT_RECEP_MERCADERIA R
        WHERE R.COD_GRUPO_CIA = cGrupoCia_in
        AND R.COD_LOCAL = cCodLocal_in
        AND R.NRO_RECEP = cNroRecepcion;


     SELECT A.DESC_CORTA_LOCAL INTO v_vDescLocal
      FROM PBL_LOCAL A
      WHERE A.COD_GRUPO_CIA =cGrupoCia_in
      AND A.COD_LOCAL =cCodLocal_in;


      v_IndEnvia := RECEP_F_CHAR_IND_TIENE_DATA(cGrupoCia_in,cCodLocal_in, cNroRecepcion);

     DBMS_OUTPUT.put_line('CARGA JCORTEZ');
     DBMS_OUTPUT.PUT_LINE('v_IndEnvia'||v_IndEnvia);

    IF (v_IndEnvia = 'S') THEN

     DBMS_OUTPUT.put_line('ENTRO');

       FOR diferencias IN cursorDiferencias
       LOOP
         DBMS_OUTPUT.put_line('ENTRO');

         nDiferencia  :=  diferencias.CANT_ENTREGADA - diferencias.CANT_CONTADA;

                             if nDiferencia < 0 then
                             Tipo:='Sobrante';
                             else
                             Tipo:='Faltante';
                             end if;
           DBMS_OUTPUT.put_line('PRODUCTOS-->'||diferencias.PRODUCTO);

         INSERT INTO RES_RECEP_MERCADERIA_DIFE (COD_PROD,DESC_PROD,DESC_UNID_PRESENT,LABORATORIO,CANT_CONTADA,
         CANT_ENTREGADA,NUM_ENTREGA,TIPO,USU_CREA,FEC_RECEP,FEC_MOD,USU_MOD,COD_LOCAL,NRO_RECEP)
         VALUES (diferencias.PRODUCTO,diferencias.Desc_prod,diferencias.UNIDAD,diferencias.LAB,diferencias.CANT_CONTADA,diferencias.CANT_ENTREGADA,
                diferencias.NUM_ENTREGA,Tipo,Usuario,v_Fecha_Generado,NULL,NULL,cCodLocal_in,cNroRecepcion);

       END LOOP;


        v_CodProd_Old := '';

       FOR poliCanje IN curProdTransfCanje
       LOOP
         IF v_CodProd_Old = poliCanje.Cod_Prod THEN


        INSERT INTO  RES_RECEP_MERCA_POL_CANJ(COD_PROD,DESC_PROD,CANT_MOV,LOTE,FEC_VENC,LOTE_SAP,FEC_VENC_SAP,FEC_RECEP,USU_CREA,FEC_MOD,USU_MOD,COD_LOCAL,NRO_RECEP)
        VALUES(NULL,NULL,NULL,NULL,NULL,poliCanje.Lote_Sap,poliCanje.Vcto_Sap,v_Fecha_Generado,Usuario,NULL,NULL,cCodLocal_in,cNroRecepcion);

         ELSE

        INSERT INTO  RES_RECEP_MERCA_POL_CANJ(COD_PROD,DESC_PROD,CANT_MOV,LOTE,FEC_VENC,LOTE_SAP,FEC_VENC_SAP,FEC_RECEP,USU_CREA,FEC_MOD,USU_MOD,COD_LOCAL,NRO_RECEP)
        VALUES(poliCanje.Cod_Prod,poliCanje.Desc_Prod,poliCanje.Cant_Mov,poliCanje.Lote,poliCanje.Venc,poliCanje.Lote_Sap,poliCanje.Vcto_Sap,
        v_Fecha_Generado,Usuario,NULL,NULL,cCodLocal_in,cNroRecepcion);

         END IF;

         v_CodProd_Old := poliCanje.Cod_Prod;


       END LOOP;


       v_CodProd_Old := '';
       FOR deteriorados IN curProdTransfDeter
       LOOP
         IF v_CodProd_Old = deteriorados.cod_prod THEN


             INSERT INTO RES_RECEP_MERCA_DETERIO(COD_PROD,DESC_PROD,CANT_MOV,LOTE,FEC_VENC,LOTE_SAP,FEC_VENC_SAP,FEC_RECEP,USU_CREA,FEC_MOD,USU_MOD,COD_LOCAL,NRO_RECEP)
             VALUES(NULL,NULL,NULL,NULL,NULL,NULL,NULL,v_Fecha_Generado,Usuario,NULL,NULL,cCodLocal_in,cNroRecepcion);

         ELSE

             INSERT INTO RES_RECEP_MERCA_DETERIO(COD_PROD,DESC_PROD,CANT_MOV,LOTE,FEC_VENC,LOTE_SAP,FEC_VENC_SAP,FEC_RECEP,USU_CREA,FEC_MOD,USU_MOD,COD_LOCAL,NRO_RECEP)
            VALUES(deteriorados.cod_prod,deteriorados.desc_prod,deteriorados.cant_mov,deteriorados.Lote,deteriorados.Venc,deteriorados.Lote_Sap,
            deteriorados.Vcto_Sap,v_Fecha_Generado,Usuario,NULL,NULL,cCodLocal_in,cNroRecepcion);

         END IF;

        v_CodProd_Old := deteriorados.cod_prod;

       END LOOP;
      END IF;
  END;
  /**************************************** */
  FUNCTION RECEP_F_EMAIL_LOCAL(cCod_Grupo_Cia_in IN CHAR, cCod_Local_in IN CHAR)
  RETURN VARCHAR2
  IS
   V_EMAIL_LOCAL VARCHAR2(300);
  BEGIN

  SELECT NVL(TRIM(L.MAIL_LOCAL),'') INTO V_EMAIL_LOCAL
    FROM PBL_LOCAL L
   WHERE L.COD_GRUPO_CIA = cCod_Grupo_Cia_in
     AND L.COD_LOCAL = cCod_Local_in;

  RETURN V_EMAIL_LOCAL;
  END;

end PTOVENTA_RECEP_CIEGA_JOB;
/

