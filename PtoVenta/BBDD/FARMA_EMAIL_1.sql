--------------------------------------------------------
--  DDL for Package Body FARMA_EMAIL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."FARMA_EMAIL" is

  -- Private type declarations
procedure envia_correo(cSendorAddress_in in char,
                            cReceiverAddress_in in char,
                            cSubject_in in varchar2,
                            ctitulo_in in varchar2,
                            cmensaje_in in varchar2,
                            cCCReceiverAddress_in in char default null,
                            cip_servidor in char default '192.168.0.236',
                            cin_html boolean default false)
is
/*tomado internet y modificado por jluna*/
  EmailServer     varchar2(30) := FARMA_EMAIL.GET_EMAIL_SERVER ;
  Port number  := 25;
  conn UTL_SMTP.CONNECTION;
  crlf VARCHAR2( 2 ):= CHR( 13 ) || CHR( 10 );
  mesg VARCHAR2( 4000 );
  mesg_body varchar2(32767);
BEGIN
  if cin_html=true then
      mesg_body  := '    <html>
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
   else
      mesg_body:=ctitulo_in||crlf||cmensaje_in;
   end if;

  /*conn:= utl_smtp.open_connection( EmailServer, Port );
  utl_smtp.helo( conn, EmailServer );
  utl_smtp.mail( conn, cSendorAddress_in);
  utl_smtp.rcpt( conn, cReceiverAddress_in );
  if cCCReceiverAddress_in is not null then
    utl_smtp.rcpt( conn, cCCReceiverAddress_in );
  end if;
  mesg:=
         'Date: '||TO_CHAR( SYSDATE, 'dd Mon yy hh24:mi:ss' )|| crlf ||
         'From:'||cSendorAddress_in|| crlf ||
         'Subject: '||cSubject_in|| crlf ||
         'To: '||cReceiverAddress_in || crlf ||
         '' || crlf ||mesg_body||'';
  utl_smtp.data(conn, 'MIME-Version: 1.0' ||CHR(13)|| CHR(10)||'Content-type: text/html' || CHR(13)||CHR(10)||mesg);
  utl_smtp.quit( conn );*/
   --MODIFICADO  15/06/2006 ERIOS
   conn := ORACLE_MAIL.begin_mail(
        v_smtp_host => EmailServer,
        sender     => cSendorAddress_in,
        recipients => cReceiverAddress_in,
        CCrecipients => cCCReceiverAddress_in,
        subject    => cSubject_in,
        mime_type  => ORACLE_MAIL.MULTIPART_MIME_TYPE);

    ORACLE_MAIL.attach_text(
        conn      => conn,
        data      => mesg_body,
        mime_type => 'text/html');


      ORACLE_MAIL.end_mail( conn => conn );
      DBMS_OUTPUT.put_line('terminó procedimiento farma_email.envia_correo');
exception
 when others then
      DBMS_OUTPUT.put_line(sqlerrm);
  null;
END;

/*begin
  -- Initialization
  null;*/

  /****************************************************************************/
  PROCEDURE ENVIA_CORREO_ATTACH(cSendorAddress_in in char,
                                cReceiverAddress_in in char,
                                cSubject_in in varchar2,
                                ctitulo_in in varchar2,
                                cmensaje_in in varchar2,
                                pfilename IN VARCHAR2,
                                i IN NUMBER,
                                cCCReceiverAddress_in in char,
                                cip_servidor in char default '192.168.0.236')
  AS
    conn UTL_SMTP.CONNECTION;
    cmensaje VARCHAR2(400);
    EmailServer     varchar2(30) := FARMA_EMAIL.GET_EMAIL_SERVER ;
  BEGIN
      conn := ORACLE_MAIL.begin_mail(
        v_smtp_host => EmailServer,
        sender     => cSendorAddress_in,
        recipients => cReceiverAddress_in,
        CCrecipients => cCCReceiverAddress_in,
        subject    => cSubject_in,
        mime_type  => ORACLE_MAIL.MULTIPART_MIME_TYPE);

      IF i <> 0 THEN
        cmensaje := '<H2>SE HA REALIZADO CAMBIOS EN EL LOCAL.<BR><BR>¡VERIFIQUE LOS CAMBIOS ADJUNTOS!</H2>
                      <b><p> Mensaje originado automaticamente desde la Base de Datos,no contestar </b> </p>';
        ORACLE_MAIL.attach_text(
          conn      => conn,
          data      => NVL(cmensaje_in, cmensaje),
          mime_type => 'text/html');

        attach_report(
          conn      => conn,
          mime_type => 'text/html',
          inline    => FALSE,
          filename  => pfilename,
          last      => TRUE);
      ELSE
        cmensaje := '<H2>NO SE HA REALIZADO CAMBIOS EN EL LOCAL</H2>
                    <b><p> Mensaje originado automaticamente desde la Base de Datos,no contestar </b> </p>';
        ORACLE_MAIL.attach_text(
        conn      => conn,
        data      => NVL(cmensaje_in, cmensaje),
        mime_type => 'text/html');
      END IF;

      ORACLE_MAIL.end_mail( conn => conn );
    /*exception
     when others then
      null;*/
  END;

  /****************************************************************************/
  PROCEDURE attach_report(conn         IN OUT NOCOPY utl_smtp.connection,
			mime_type    IN VARCHAR2 DEFAULT 'text/plain',
			inline       IN BOOLEAN  DEFAULT TRUE,
			filename     IN VARCHAR2 DEFAULT NULL,
		        last         IN BOOLEAN  DEFAULT FALSE)
  IS
    v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
    vNewLine VARCHAR2(200):='edgar';

    vInHandle utl_file.file_type;
  BEGIN
    ORACLE_MAIL.begin_attachment(conn, mime_type, inline, filename);
    DBMS_OUTPUT.PUT_LINE('ARCHIVO:'||filename);
    vInHandle := utl_file.fopen(v_gNombreDiretorio, TRIM(filename), 'R');
    LOOP
      BEGIN
        /*IF vNewLine IS NULL THEN
            EXIT;
          END IF;*/
        utl_file.get_line(vInHandle, vNewLine);
        ORACLE_MAIL.write_text(conn, vNewLine);
        --DBMS_OUTPUT.PUT_LINE('conn,'||vNewLine);
      EXCEPTION
        WHEN OTHERS THEN
          EXIT;
      END;
    END LOOP;
    utl_file.fclose(vInHandle);
    --DBMS_OUTPUT.PUT_LINE('close file');
    ORACLE_MAIL.end_attachment(conn, last);

  END;

  /****************************************************************************/
  FUNCTION GET_SENDDOR_ADDRESS
  RETURN VARCHAR2
  IS
    v_vSenddor PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    SELECT DESC_CORTA
      INTO v_vSenddor
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL = 37
          AND COD_APL = 'PTO_VENTA'
          AND COD_TAB_GRAL = 'FARMA_EMAIL'
          AND LLAVE_TAB_GRAL = '01'
    ;
    RETURN v_vSenddor;
  END;

  /****************************************************************************/
  FUNCTION GET_EMAIL_SERVER
  RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    SELECT DESC_CORTA
      INTO v_vServer
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL = 38
          AND COD_APL = 'PTO_VENTA'
          AND COD_TAB_GRAL = 'FARMA_EMAIL'
          AND LLAVE_TAB_GRAL = '02'
    ;
    RETURN v_vServer;
  END;

  /****************************************************************************/
  FUNCTION GET_RECEIVER_ADDRESS_VERIFICA
  RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    --'jccardenas@mifarma.com.pe, joliva@mifarma.com.pe'
    SELECT DESC_CORTA
      INTO v_vServer
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL = 39
          AND COD_APL = 'PTO_VENTA'
          AND COD_TAB_GRAL = 'FARMA_EMAIL'
          AND LLAVE_TAB_GRAL = '03'
    ;
    RETURN v_vServer;
  END;

  /****************************************************************************/
  FUNCTION GET_RECEIVER_ADDRESS_INTERFACE
  RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    --'operador@mifarma.com.pe, joliva@mifarma.com.pe, erios@mifarma.com.pe'
    SELECT DESC_CORTA
      INTO v_vServer
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL = 40
          AND COD_APL = 'PTO_VENTA'
          AND COD_TAB_GRAL = 'FARMA_EMAIL'
          AND LLAVE_TAB_GRAL = '04'
    ;
    RETURN v_vServer;
  END;

  /****************************************************************************/
  FUNCTION GET_RECEIVER_ADDRESS_VIAJERO
  RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    --'operador@mifarma.com.pe, joliva@mifarma.com.pe, erios@mifarma.com.pe'
    SELECT DESC_CORTA
      INTO v_vServer
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL = 41
          AND COD_APL = 'PTO_VENTA'
          AND COD_TAB_GRAL = 'FARMA_EMAIL'
          AND LLAVE_TAB_GRAL = '05'
    ;
    RETURN v_vServer;
  END;

  /****************************************************************************/
  FUNCTION GET_RECEIVER_ADDRESS_CAMBIOS
  RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    --'mtorres@mifarma.com.pe, vmonteza@mifarma.com.pe, joliva@mifarma.com.pe, maguilar@mifarma.com.pe'
    SELECT DESC_CORTA
      INTO v_vServer
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL = 42
          AND COD_APL = 'PTO_VENTA'
          AND COD_TAB_GRAL = 'FARMA_EMAIL'
          AND LLAVE_TAB_GRAL = '06'
    ;
    RETURN v_vServer;
  END;

  /****************************************************************************/
  FUNCTION GET_RECEIVER_ADDRESS_AJUSTES
  RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    --'pyovera'
    SELECT DESC_CORTA
      INTO v_vServer
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL = 43
          AND COD_APL = 'PTO_VENTA'
          AND COD_TAB_GRAL = 'FARMA_EMAIL'
          AND LLAVE_TAB_GRAL = '07'
    ;
    RETURN v_vServer;
  END;
  /****************************************************************************/
  FUNCTION GET_RECEIVER_ADDRESS_LOCAL(cCodLocal_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vServer PBL_LOCAL.MAIL_LOCAL %TYPE;
  BEGIN
    SELECT TRIM(MAIL_LOCAL)
      INTO v_vServer
    FROM PBL_LOCAL
    WHERE COD_LOCAL = cCodLocal_in
    ;
    RETURN v_vServer;
  END;
  /****************************************************************************/
  FUNCTION GET_RECEIVER_ADDRESS_TRANSF
  RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    --'pyovera'
    SELECT DESC_CORTA
      INTO v_vServer
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL = 47
          AND COD_APL = 'PTO_VENTA'
          AND COD_TAB_GRAL = 'FARMA_EMAIL'
          AND LLAVE_TAB_GRAL = '08'
    ;
    RETURN v_vServer;
  END;

  /****************************************************************************/

  FUNCTION GET_RECEIVER_ADDRESS_INTER_CE
    RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
       --'lmesia@mifarma.com.pe, joliva@mifarma.com.pe'
       SELECT DESC_CORTA
       INTO   v_vServer
       FROM   PBL_TAB_GRAL
       WHERE  ID_TAB_GRAL = 49
       AND    COD_APL = 'PTO_VENTA'
       AND    COD_TAB_GRAL = 'FARMA_EMAIL'
       AND    LLAVE_TAB_GRAL = '09';
    RETURN v_vServer;
  END;

  /****************************************************************************/
  FUNCTION GET_RECEIVER_ADDRESS_ADMIN_USU
    RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
       --'lmesia@mifarma.com.pe, joliva@mifarma.com.pe'
       SELECT DESC_CORTA
       INTO   v_vServer
       FROM   PBL_TAB_GRAL
       WHERE  ID_TAB_GRAL = 54
       AND    COD_APL = 'PTO_VENTA'
       AND    COD_TAB_GRAL = 'FARMA_EMAIL'
       AND    LLAVE_TAB_GRAL = '10';
    RETURN v_vServer;
  END;

  /****************************************************************************/
  FUNCTION GET_RECEIVER_ADDRESS_INT_CONV
    RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
       --'lmesia@mifarma.com.pe, joliva@mifarma.com.pe'
       SELECT DESC_CORTA
       INTO   v_vServer
       FROM   PBL_TAB_GRAL
       WHERE  ID_TAB_GRAL = 58
       AND    COD_APL = 'PTO_VENTA'
       AND    COD_TAB_GRAL = 'FARMA_EMAIL'
       AND    LLAVE_TAB_GRAL = '11';
    RETURN v_vServer;
  END;
  /****************************************************************************/
  PROCEDURE attach_report2(conn         IN OUT NOCOPY utl_smtp.connection,
			mime_type    IN VARCHAR2 DEFAULT 'text/plain',
			inline       IN BOOLEAN  DEFAULT TRUE,
      directory IN VARCHAR2 DEFAULT NULL,
			filename     IN VARCHAR2 DEFAULT NULL,
		        last         IN BOOLEAN  DEFAULT FALSE)
  IS
    --v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
    vNewLine VARCHAR2(200):='edgar';

    vInHandle utl_file.file_type;
  BEGIN
    ORACLE_MAIL.begin_attachment(conn, mime_type, inline, filename);
    DBMS_OUTPUT.PUT_LINE('ARCHIVO:'||filename);
    vInHandle := utl_file.fopen(directory, TRIM(filename), 'R');
    LOOP
      BEGIN
        /*IF vNewLine IS NULL THEN
            EXIT;
          END IF;*/
        utl_file.get_line(vInHandle, vNewLine);
        ORACLE_MAIL.write_text(conn, vNewLine);
        --DBMS_OUTPUT.PUT_LINE('conn,'||vNewLine);
      EXCEPTION
        WHEN OTHERS THEN
          EXIT;
      END;
    END LOOP;
    utl_file.fclose(vInHandle);
    --DBMS_OUTPUT.PUT_LINE('close file');
    ORACLE_MAIL.end_attachment(conn, last);

  END;

  /****************************************************************************/
  PROCEDURE attach_report3(conn         IN OUT NOCOPY utl_smtp.connection,
			mime_type    IN VARCHAR2 DEFAULT 'text/plain',
			inline       IN BOOLEAN  DEFAULT TRUE,
      directory IN VARCHAR2 DEFAULT NULL,
			filename     IN VARCHAR2 DEFAULT NULL,
		        last         IN BOOLEAN  DEFAULT FALSE)
  IS
    --v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
    vNewLine VARCHAR2(200):='edgar';

    vInHandle utl_file.file_type;
  BEGIN
    ORACLE_MAIL.begin_attachment(conn, mime_type, inline, filename);
    DBMS_OUTPUT.PUT_LINE('ARCHIVO:'||filename);
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
          EXIT;
      END;
    END LOOP;
    utl_file.fclose(vInHandle);
    --DBMS_OUTPUT.PUT_LINE('close file');
    ORACLE_MAIL.end_attachment(conn, last);

  END;

  /****************************************************************************/
  PROCEDURE ENVIA_CORREO_ATTACH2(cSendorAddress_in in char,
                                cReceiverAddress_in in char,
                                cSubject_in in varchar2,
                                ctitulo_in in varchar2,
                                cmensaje_in in varchar2,
                                pDirectorio IN VARCHAR2,
                                pfilename IN VARCHAR2,
                                i IN NUMBER,
                                cCCReceiverAddress_in in char,
                                cip_servidor in char)
  AS
    conn UTL_SMTP.CONNECTION;
    cmensaje VARCHAR2(400);
    EmailServer     varchar2(30) := FARMA_EMAIL.GET_EMAIL_SERVER ;
  BEGIN
      conn := ORACLE_MAIL.begin_mail(
        v_smtp_host => EmailServer,
        sender     => cSendorAddress_in,
        recipients => cReceiverAddress_in,
        CCrecipients => cCCReceiverAddress_in,
        subject    => cSubject_in,
        mime_type  => ORACLE_MAIL.MULTIPART_MIME_TYPE);

      IF i <> 0 THEN
        cmensaje := '<H2>SE HA REALIZADO CAMBIOS EN EL LOCAL.<BR><BR>¡VERIFIQUE LOS CAMBIOS ADJUNTOS!</H2>
                      <b><p> Mensaje originado automaticamente desde la Base de Datos,no contestar </b> </p>';
        ORACLE_MAIL.attach_text(
          conn      => conn,
          data      => cmensaje,
          mime_type => 'text/html');

        attach_report2(
          conn      => conn,
          mime_type => 'text/html',
          inline    => FALSE,
          directory => pDirectorio,
          filename  => pfilename,
          last      => TRUE);
      ELSE
        cmensaje := '<H2>NO SE HA REALIZADO CAMBIOS EN EL LOCAL</H2>
                    <b><p> Mensaje originado automaticamente desde la Base de Datos,no contestar </b> </p>';
        ORACLE_MAIL.attach_text(
        conn      => conn,
        data      => cmensaje,
        mime_type => 'text/html');
      END IF;

      ORACLE_MAIL.end_mail( conn => conn );
    /*exception
     when others then
      null;*/
  END;
  /****************************************************************************/
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
    EmailServer     varchar2(30) := FARMA_EMAIL.GET_EMAIL_SERVER ;
  BEGIN



        conn := ORACLE_MAIL.begin_mail(
        v_smtp_host => EmailServer,
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
  --04/10/2007 DUBILLUZ CREACION
  FUNCTION GET_RECEIVER_ADDRESS_IND_LINEA
    RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
        SELECT DESC_CORTA
        INTO v_vServer
        FROM PBL_TAB_GRAL
        WHERE ID_TAB_GRAL = 128
              AND COD_APL = 'PTO_VENTA'
              AND COD_TAB_GRAL = 'FARMA_EMAIL'
              AND LLAVE_TAB_GRAL = '15';
    RETURN v_vServer;
  END;

  FUNCTION GET_RECEIVER_ADDRESS_IMP_LOCAL
    RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
        SELECT DESC_CORTA
        INTO v_vServer
        FROM PBL_TAB_GRAL
        WHERE ID_TAB_GRAL = 127
              AND COD_APL = 'PTO_VENTA'
              AND COD_TAB_GRAL = 'FARMA_EMAIL'
              AND LLAVE_TAB_GRAL = '16';
    RETURN v_vServer;
  END;

  /***************************************************************************/
  FUNCTION GET_RECEIVER_ADDRESS_GER_COMER
    RETURN VARCHAR2
  IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
       SELECT DESC_CORTA
       INTO   v_vServer
       FROM   PBL_TAB_GRAL
       WHERE  /*ID_TAB_GRAL = 168
       AND    */COD_APL = 'ADM_CENTRAL'
       AND    COD_TAB_GRAL = 'FARMA_EMAIL'
       AND    LLAVE_TAB_GRAL = '17';
    RETURN v_vServer;
  END;
  /***************************************************************************/
  FUNCTION GET_RECEIVER_ADDRESS_VL_INT_CE
  RETURN VARCHAR2
  IS
     v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
        SELECT DESC_CORTA
        INTO v_vServer
        FROM PBL_TAB_GRAL
        WHERE ID_TAB_GRAL = 169
              AND COD_APL = 'PTO_VENTA'
              AND COD_TAB_GRAL = 'FARMA_EMAL'
              AND LLAVE_TAB_GRAL = '18';

    RETURN v_vServer;
  END;


end farma_email;

/
