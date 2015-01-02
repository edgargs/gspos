--------------------------------------------------------
--  DDL for Package Body PTOVENTA_ENV_ALERTAS_AUX
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_ENV_ALERTAS_AUX" AS

  -------------------------------------------
  PROCEDURE ENVIA_CORREO(cCodGrupoCia_in       IN CHAR,
                         cCodLocal_in          IN CHAR,
                         vReceiverAddress_in   IN CHAR,
                         vAsunto_in            IN CHAR,
                         vTitulo_in            IN CHAR,
                         vMensaje_in           IN CHAR,
                         vCCReceiverAddress_in IN CHAR)
  AS
    mesg_body VARCHAR2(4000);

    v_vDescLocal VARCHAR2(120);
  BEGIN

    --DESCRIPCION DEL LOCAL
      SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO v_vDescLocal
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

    --CREAR CUERPO MENSAJE;
      mesg_body := '<LI> <B>' || 'LOCAL: '||v_vDescLocal	||CHR(9)||
                                       '</B><BR>'||
                                          '<I>MENSAJE: </I><BR>'||vMensaje_in	||
                                          '</LI>'  ;

    --ENVIA MAIL
      FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                               vReceiverAddress_in,
                               vAsunto_in||v_vDescLocal,
                               vTitulo_in,
                               mesg_body,
                               vCCReceiverAddress_in,
                               FARMA_EMAIL.GET_EMAIL_SERVER,
                               true);


  END;

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
    cmensaje VARCHAR2(400);
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

  /****************************************************************************/
  PROCEDURE attach_report3(conn IN OUT NOCOPY utl_smtp.connection,
			                     mime_type    IN VARCHAR2 DEFAULT 'text/plain',
			                     inline       IN BOOLEAN  DEFAULT TRUE,
                           directory IN VARCHAR2 DEFAULT NULL,
			                     filename     IN VARCHAR2 DEFAULT NULL,
		                       last         IN BOOLEAN  DEFAULT FALSE)
  IS
    --v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
    vNewLine VARCHAR2(1000):=' ';

    vInHandle utl_file.file_type;
  BEGIN
    ORACLE_MAIL.begin_attachment(conn, mime_type, inline, filename);
--    DBMS_OUTPUT.PUT_LINE('ARCHIVO:'||filename);
    vInHandle := utl_file.fopen(directory, TRIM(filename), 'R');
    LOOP
      BEGIN
        /*IF vNewLine IS NULL THEN
            EXIT;
          END IF;*/
        utl_file.get_line(vInHandle, vNewLine);
        ORACLE_MAIL.write_text(conn, vNewLine || chr(10));
        --DBMS_OUTPUT.PUT_LINE('conn,'||vNewLine);
      EXCEPTION
        WHEN OTHERS THEN
             DBMS_OUTPUT.PUT_LINE('err,'||SQLERRM);
          EXIT;
      END;
    END LOOP;
    utl_file.fclose(vInHandle);
    --DBMS_OUTPUT.PUT_LINE('close file');
    ORACLE_MAIL.end_attachment(conn, last);

  END;

-- *****************************************************************************************

  PROCEDURE ALERTA_PORC_VTA_FID_VEND
  IS
     v_vHtmlCorreo VARCHAR2(32767):='';
     v_vNombreArchivo    varchar(100):='';
     n_Contador          number:=0;
     v_Cod_Local         PBL_LOCAL.COD_LOCAL%TYPE;
     v_Desc_Local         PBL_LOCAL.DESC_CORTA_LOCAL%TYPE;

     v_Mail_Local        PBL_LOCAL.MAIL_LOCAL%TYPE;
     v_Mail_JefeZona     VTA_ZONA_VTA.EMAIL_JEFE_ZONA%TYPE;

     v_Mensaje           VARCHAR2(500);

     CURSOR curVta (C_COD_LOCAL IN CHAR) IS
         SELECT
              DESC_CARGO                                               ,
              COD_TRAB_RRHH                                            ,
              APE_PAT_TRAB                                             ,
              APE_MAT_TRAB                                             ,
              NOM_TRAB                                                 ,
              TO_CHAR(SUM(VAL_NETO_PED_VTA),'999999.00')               VTA_S_IGV,
              SUM(CASE WHEN EMIT IS NOT NULL THEN 1 ELSE 0 END)        PED_EMIT,
              SUM(CASE WHEN FID IS NOT NULL THEN 1 ELSE 0 END)         PED_FID,
              TO_CHAR(CASE WHEN SUM(CASE WHEN EMIT IS NOT NULL THEN 1 ELSE 0 END) = 0 THEN 0 ELSE (SUM(CASE WHEN FID IS NOT NULL THEN 1 ELSE 0 END) / SUM(CASE WHEN EMIT IS NOT NULL THEN 1 ELSE 0 END)) * 100 END,'90.00') || '%' PORC_FID,
              CASE WHEN SUM(CASE WHEN EMIT IS NOT NULL THEN 1 ELSE 0 END) = 0 THEN 0 ELSE (SUM(CASE WHEN FID IS NOT NULL THEN 1 ELSE 0 END) / SUM(CASE WHEN EMIT IS NOT NULL THEN 1 ELSE 0 END)) * 100 END PORC_FID_NUM
         FROM
             (
               SELECT DISTINCT
                      CARGO.DESC_CARGO,
                      Z.EMAIL_JEFE_ZONA,
                      (L.COD_LOCAL ||'-'|| L.DESC_CORTA_LOCAL) LOCAL,
                      T.COD_TRAB_RRHH, T.APE_PAT_TRAB, T.APE_MAT_TRAB, T.NOM_TRAB,
                      CAB.VAL_NETO_PED_VTA,
                      CAB.NUM_PED_VTA,
                      CAB.FID,
                      CAB.EMIT,
                      CAB.EC,
                      CAB.PEC
               FROM
                   (
                      SELECT DISTINCT
                             C.COD_LOCAL,
                             C.NUM_PED_VTA NUM_PED_VTA,
                             (CASE WHEN C.NUM_PED_VTA_ORIGEN IS NULL AND C.IND_FID = 'S' THEN C.NUM_PED_VTA ELSE NULL END) FID,
                             (CASE WHEN C.NUM_PED_VTA_ORIGEN IS NULL THEN C.NUM_PED_VTA ELSE NULL END) EMIT,
                             C.VAL_NETO_PED_VTA,
                             EC.NUM_PED_VTA EC,
                             PEC.NUM_PED_VTA PEC
                      FROM VTA_PEDIDO_VTA_CAB C,
                           AUX_PED_EMIT_CUPON EC,
                           AUX_PED_PUDIERON_EMIT_CUPON PEC,
                           VTA_PEDIDO_VTA_DET D
                      WHERE C.COD_GRUPO_CIA = '001'
                        AND C.COD_LOCAL = C_COD_LOCAL
                        AND C.FEC_PED_VTA BETWEEN TRUNC(SYSDATE-1,'MM') AND TRUNC(SYSDATE)-1/24/60/60
                        AND C.EST_PED_VTA = 'C'
                        AND C.TIP_PED_VTA = '01'
                        AND EC.COD_GRUPO_CIA(+) = C.COD_GRUPO_CIA
                        AND EC.COD_LOCAL(+) = C.COD_LOCAL
                        AND EC.NUM_PED_VTA(+) = C.NUM_PED_VTA
                        AND PEC.COD_GRUPO_CIA(+) = C.COD_GRUPO_CIA
                        AND PEC.COD_LOCAL(+) = C.COD_LOCAL
                        AND PEC.NUM_PED_VTA(+) = C.NUM_PED_VTA
                        AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                        AND D.COD_LOCAL = C.COD_LOCAL
                        AND D.NUM_PED_VTA = C.NUM_PED_VTA
                   ) CAB,
                   VTA_PEDIDO_VTA_DET D,
                   PBL_USU_LOCAL U,
                   CE_MAE_TRAB T,
                   PBL_LOCAL L,
                   VTA_LOCAL_X_ZONA LXZ,
                   VTA_ZONA_VTA Z,
                   CE_CARGO CARGO
               WHERE D.NUM_PED_VTA = CAB.NUM_PED_VTA
                 AND U.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                 AND U.COD_LOCAL = D.COD_LOCAL
                 AND U.SEC_USU_LOCAL = D.SEC_USU_LOCAL
                 AND T.COD_TRAB = U.COD_TRAB
                 AND L.COD_LOCAL = CAB.COD_LOCAL
                 AND LXZ.COD_LOCAL = L.COD_LOCAL
                 AND Z.COD_ZONA_VTA = LXZ.COD_ZONA_VTA
                 AND CARGO.COD_CARGO = T.COD_CARGO
             )
         GROUP BY
              DESC_CARGO,
              EMAIL_JEFE_ZONA,
              LOCAL,
              COD_TRAB_RRHH, APE_PAT_TRAB, APE_MAT_TRAB, NOM_TRAB
         ORDER BY PORC_FID DESC;

     v_rCurVta curVta%ROWTYPE;

  BEGIN

  /*.xl28
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:right;
	border:.5pt solid black;
	white-space:normal;}**/

        v_vHtmlCorreo:='<html>'||
                        '<body>'||
                        '<table style="text-align: left; width: 90%;" border="1"'||
                        ' cellpadding="2" cellspacing="1">'||
                        '    <tr>'||
                        '      <th><small> # </small></th>'||
                        '      <th><small> CARGO </small></th>'||
                        '      <th><small> CODIGO </small></th>'||
                        '      <th><small> APE PAT </small></th>'||
                        '      <th><small> APE MAT </small></th>'||
                        '      <th><small> NOMBRE </small></th>'||
--                        '      <th><small> TOT S IGV </small></th>'||
                        '      <th><small> PED EMIT </small></th>'||
                        '      <th><small> PED FID </small></th>'||
                        '      <th><small> % </small></th>'||
                        '    </tr>';


       SELECT DISTINCT L.COD_LOCAL, TRIM(L.DESC_CORTA_LOCAL)
       INTO V_COD_LOCAL, v_Desc_Local
       FROM VTA_IMPR_LOCAL I,
            PBL_LOCAL L
       WHERE I.COD_GRUPO_CIA = '001'
         AND I.COD_LOCAL = L.COD_LOCAL;

       SELECT TRIM(MAIL_LOCAL)
       INTO v_Mail_Local
       FROM PBL_LOCAL
       WHERE COD_GRUPO_CIA = '001'
         AND COD_LOCAL = V_COD_LOCAL;


       SELECT TRIM(EMAIL_JEFE_ZONA)
       INTO v_Mail_JefeZona
       FROM VTA_ZONA_VTA Z,
            VTA_LOCAL_X_ZONA ZL
       WHERE ZL.COD_GRUPO_CIA = '001'
         AND ZL.COD_LOCAL = V_COD_LOCAL
         AND ZL.COD_ZONA_VTA = Z.COD_ZONA_VTA;


      DELETE FROM AUX_PED_EMIT_CUPON;

      INSERT INTO AUX_PED_EMIT_CUPON (COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA)
      SELECT DISTINCT C.COD_GRUPO_CIA, C.COD_LOCAL, C.NUM_PED_VTA
      FROM VTA_PEDIDO_VTA_CAB C,
           VTA_CAMP_PEDIDO_CUPON CPC
      WHERE C.COD_GRUPO_CIA = '001'
        AND C.COD_LOCAL = V_COD_LOCAL
        AND C.FEC_PED_VTA BETWEEN TRUNC(SYSDATE-1,'MM') AND TRUNC(SYSDATE)-1/24/60/60
        AND C.EST_PED_VTA = 'C'
        AND C.TIP_PED_VTA = '01'
        AND C.NUM_PED_VTA_ORIGEN IS NULL
        AND CPC.COD_GRUPO_CIA = C.COD_GRUPO_CIA
        AND CPC.COD_LOCAL = C.COD_LOCAL
        AND CPC.NUM_PED_VTA = C.NUM_PED_VTA
        AND ESTADO = 'E' AND IND_IMPR = 'S';

      DELETE FROM AUX_PED_PUDIERON_EMIT_CUPON;

      INSERT INTO AUX_PED_PUDIERON_EMIT_CUPON (COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA)
      SELECT DISTINCT COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA
      FROM
          (
            -- PEDIDOS QUE PUDIERON EMITIR CUPON
            SELECT C.COD_GRUPO_CIA, C.COD_LOCAL, C.NUM_PED_VTA
            FROM VTA_PEDIDO_VTA_CAB C,
                 VTA_PEDIDO_VTA_DET D,
                 VTA_CAMPANA_PROD CP,
                 VTA_CAMPANA_CUPON CC
            WHERE C.COD_GRUPO_CIA = '001'
              AND C.COD_LOCAL = V_COD_LOCAL
              AND C.FEC_PED_VTA BETWEEN TRUNC(SYSDATE-1,'MM') AND TRUNC(SYSDATE)-1/24/60/60
              AND C.EST_PED_VTA = 'C'
              AND C.TIP_PED_VTA = '01'
              AND C.NUM_PED_VTA_ORIGEN IS NULL
              AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
              AND D.COD_LOCAL = C.COD_LOCAL
              AND D.NUM_PED_VTA = C.NUM_PED_VTA
              AND CP.COD_PROD = D.COD_PROD
              AND CC.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
              AND CC.COD_CAMP_CUPON = CP.COD_CAMP_CUPON
              AND C.FEC_PED_VTA BETWEEN CC.FECH_INICIO AND CC.FECH_FIN + 1
            MINUS
            -- PEDIDOS QUE EMITIERON CUPON
            SELECT C.COD_GRUPO_CIA, C.COD_LOCAL, C.NUM_PED_VTA
            FROM VTA_PEDIDO_VTA_CAB C,
                 VTA_CAMP_PEDIDO_CUPON CPC
            WHERE C.COD_GRUPO_CIA = '001'
              AND C.COD_LOCAL = V_COD_LOCAL
              AND C.FEC_PED_VTA BETWEEN TRUNC(SYSDATE-1,'MM') AND TRUNC(SYSDATE)-1/24/60/60
              AND C.EST_PED_VTA = 'C'
              AND C.TIP_PED_VTA = '01'
              AND C.NUM_PED_VTA_ORIGEN IS NULL
              AND CPC.COD_GRUPO_CIA = C.COD_GRUPO_CIA
              AND CPC.COD_LOCAL = C.COD_LOCAL
              AND CPC.NUM_PED_VTA = C.NUM_PED_VTA
              AND ESTADO = 'E' AND IND_IMPR = 'S'
          );

      COMMIT;

       FOR v_rCurVta IN curVta (v_Cod_Local)
       LOOP
              n_Contador:=n_Contador+1;

              v_vHtmlCorreo:= v_vHtmlCorreo || '<tr>'||
                             '    <td align="right">'||n_Contador||'</td>'||
                             '    <td align="left">'||v_rCurVta.DESC_CARGO||'</td>'||
                             '    <td align="left">'||v_rCurVta.COD_TRAB_RRHH||'</td>'||
                             '    <td align="left">'||v_rCurVta.APE_PAT_TRAB||'</td>'||
                             '    <td align="left">'||v_rCurVta.APE_MAT_TRAB||'</td>'||
                             '    <td align="left">'||v_rCurVta.NOM_TRAB||'</td>'||
                             '    <td align="right">'||v_rCurVta.PED_EMIT||'</td>'||
                             '    <td align="right">'||v_rCurVta.PED_FID||'</td>';
              IF v_rCurVta.PORC_FID_NUM < 50 THEN
                  v_vHtmlCorreo:= v_vHtmlCorreo ||
                                 '    <td align="right">'||v_rCurVta.PORC_FID||'</td>';
--                                 '    <td align="right" style="mso-number-format:''\@'';"><FONT COLOR="RED">'|| v_rCurVta.PORC_FID ||'</FONT></td>';
              ELSE
                  v_vHtmlCorreo:= v_vHtmlCorreo ||
                                 '    <td align="right">'||v_rCurVta.PORC_FID||'</td>';
              END IF;
                  v_vHtmlCorreo:= v_vHtmlCorreo ||
                             '</tr>';
       END LOOP;

        DBMS_OUTPUT.put_line('n_Contador=' || n_Contador);

        v_vHtmlCorreo:= v_vHtmlCorreo || ' </table> '||
                       ' </body> '||
                       ' </html> ';

       v_Mensaje := ' Estimado Jefe de Local, <BR><BR>';
       v_Mensaje := v_Mensaje || ' Adjunto un reporte con el n&uacute;mero de pedidos (Mes&oacute;n) realizados del 01 al ' || TO_CHAR(SYSDATE-1,'DD') || ' de ' || TO_CHAR(SYSDATE-1,'MONTH') || ' y el porcentaje (%) de venta fidelizada.<BR><BR>';

      FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                            v_Mail_Local,
                            V_COD_LOCAL ||'-'|| v_Desc_Local || ' Avance de venta fidelizada',
                            ' ',
                            v_Mensaje || v_vHtmlCorreo,
                            v_Mail_JefeZona || ', joliva, rcastro',
                            FARMA_EMAIL.GET_EMAIL_SERVER,
                            true);

  END;

END;

/
