CREATE OR REPLACE PACKAGE PTOVENTA.UTIL_EMAIL_ORACLE AS

  /* ********************************************************************* */
  PROCEDURE SENT_EMAIL(p_servidor  varchar2,
                       p_desde     varchar2,
                       p_para      varchar2,
                       p_CC        varchar2,
                       p_CO        varchar2,
                       p_asunto    varchar2,
                       p_titulo    varchar2,
                       p_mensaje   varchar2,
                       p_fichero   VARCHAR2,
                       pDirectorio varchar2);
  /* ********************************************************************* */
  function F_GET_EXTENSION_FILE(vNombreArchivo in varchar2) return varchar2;
  /* ********************************************************************* */
  FUNCTION GET_EMAIL_SERVER(cCodGrupoCia_in IN CHAR default '001')
    RETURN VARCHAR2;

  /* ********************************************************************* */
  PROCEDURE ENVIA_CORREO_ORACLE(p_para      varchar2,
                                p_CC        varchar2,
                                p_CO        varchar2,
                                p_asunto    varchar2,
                                p_titulo    varchar2,
                                p_mensaje   varchar2,
                                p_fichero   VARCHAR2,
                                pDirectorio varchar2);

  /* ********************************************************************* */
  function OUT_ZIP_FILE(A_NOM_ARCHIVO    IN VARCHAR2,
                        A_DES_DIRECTORIO IN VARCHAR2) return varchar2;
END;
/
create or replace package body ptoventa.UTIL_EMAIL_ORACLE is
  /* ********************************************************************* */
  PROCEDURE SENT_EMAIL(p_servidor  varchar2,
                       p_desde     varchar2,
                       p_para      varchar2,
                       p_CC        varchar2,
                       p_CO        varchar2,
                       p_asunto    varchar2,
                       p_titulo    varchar2,
                       p_mensaje   varchar2,
                       p_fichero   VARCHAR2,
                       pDirectorio varchar2) as
  

    CRLF                 CONSTANT varchar2(10) := utl_tcp.CRLF;
    BOUNDARY            CONSTANT VARCHAR2(256) := '=_mixed 007762120525773C_=';--'-----7D81B75CCC90D2974F7A1CBD';
    FIRST_BOUNDARY       CONSTANT varchar2(256) := '--'||BOUNDARY||CRLF;
    MULTIPART_MIME_TYPE  CONSTANT varchar2(256) := 'multipart/mixed; boundary="'||BOUNDARY||'"';
        
        
            
    v_file_handle        utl_file.file_type;
    v_line               varchar2(1000);
    conn                 utl_smtp.connection;
    from_address         varchar2(255) := p_desde;
    to_address           varchar2(255) := p_para;
    subject              varchar2(1000) := p_asunto;
    mime_type            varchar2(255) := 'text/html';
    attachment_file_name varchar2(255) := p_fichero;
    mailhost             varchar2(255) := p_servidor;
    v_to_address         VARCHAR2(255);
    mesg_body            varchar2(32767);
    /* ************************ variables para lectura de archivo adjuntar ************************ */
    CONS_SERVER_RUTA varchar2(2000);
    file_type        VARCHAR2(2000);
    bfile_handle     bfile;
    bfile_len        number;
    pos              number;
    file_handle      utl_file.file_type;
    read_bytes       number;
    data             raw(200);
    crlf_a           varchar2(2) := chr(13) || chr(10);
    /* ************************ variables para lectura de archivo adjuntar ************************ */
  
    PROCEDURE send_header(name IN VARCHAR2, header IN VARCHAR2) AS
        BEGIN
            If (name = 'Subject') then
       	        utl_smtp.write_data(conn, name || ': =?iso-8859-1?Q?' || utl_raw.cast_to_varchar2(  utl_encode.quoted_printable_encode(utl_raw.cast_to_raw(header))) || '?=' || utl_tcp.crlf);
            else
                utl_smtp.write_data(conn, name || ': ' || header || utl_tcp.crlf);
            end if;
        END;
    /* ******************************************************************************************* */
  
  BEGIN
    CONS_SERVER_RUTA := trim(pDirectorio);
    conn             := utl_smtp.open_connection(mailhost);
    utl_smtp.ehlo(conn, mailhost);
  
    utl_smtp.mail(conn, '< ' || from_address || ' >');
    ------------------------------------------------------------------------
    v_to_address := p_para;
    LOOP
      IF TRIM(v_to_address) IS NULL THEN
        EXIT;
      END IF;
      IF instr(v_to_address, ',') = 0 THEN
        to_address := v_to_address;
        utl_smtp.rcpt(conn, '< ' || to_address || ' >');
        EXIT;
      ELSE
        to_address := substr(v_to_address, 1, instr(v_to_address, ',') - 1);
        utl_smtp.rcpt(conn, '< ' || to_address || ' >');
        v_to_address := substr(v_to_address, instr(v_to_address, ',') + 1);
      END IF;
    END LOOP;
    ------------------------------------------------------------------------
    v_to_address := p_cc;
    LOOP
      IF TRIM(v_to_address) IS NULL THEN
        EXIT;
      END IF;
      IF instr(v_to_address, ',') = 0 THEN
        to_address := v_to_address;
        utl_smtp.rcpt(conn, '< ' || to_address || ' >');
        EXIT;
      ELSE
        to_address := substr(v_to_address, 1, instr(v_to_address, ',') - 1);
        utl_smtp.rcpt(conn, '< ' || to_address || ' >');
        v_to_address := substr(v_to_address, instr(v_to_address, ',') + 1);
      END IF;
    END LOOP;
    ------------------------------------------------------------------------
    v_to_address := p_co;
    LOOP
      IF TRIM(v_to_address) IS NULL THEN
        EXIT;
      END IF;
      IF instr(v_to_address, ',') = 0 THEN
        to_address := v_to_address;
        utl_smtp.rcpt(conn, '< ' || to_address || ' >');
        EXIT;
      ELSE
        to_address := substr(v_to_address, 1, instr(v_to_address, ',') - 1);
        utl_smtp.rcpt(conn, '< ' || to_address || ' >');
        v_to_address := substr(v_to_address, instr(v_to_address, ',') + 1);
      END IF;
    END LOOP;
    ------------------------------------------------------------------------        
    utl_smtp.open_data(conn);
    send_header('From', '' || p_desde || '');
    send_header('To', '' || p_para || '');
    send_header('Cc', '' || p_cc || '');
    send_header('bbc', '' || p_cO || '');
    send_header('Date',
                to_char(sysdate, 'dd Mon yy hh24:mi:ss'));
    send_header('Subject', subject);
    send_header('Content-Type', MULTIPART_MIME_TYPE);
  
    utl_smtp.write_data(conn, CRLF);
  
    utl_smtp.write_data(conn, FIRST_BOUNDARY);
    send_header('Content-Type', mime_type);
    utl_smtp.write_data(conn, CRLF);
  
    mesg_body := '<html>
                        <head>
                        <title>MIFARMA te entiende de cuida By SISTEMAS</title>
                        </head>
                        <body bgcolor="#FFFFFF" link="#000080">
                        <table cellspacing="0" cellpadding="0" width="100%">
                        <tr  valign="BASELINE">
                        <td width="100%" valign="middle"><h1><font color="#00008B"><b>' ||
                 p_titulo || '</b></font></h1>
                        </td>
                     </table>
                      <ul>
                       ' || p_mensaje || '
                       <l><b> </b> </l>
                       <l><b></p></p> Mensaje originado automaticamente desde la Base de Datos,no contestar </b> </l>
                          </ul>
                         </body>
                         </html>';
                         
    --mesg_body := p_titulo|| crlf_a|| p_mensaje;
    utl_smtp.write_raw_data(conn,
                            utl_raw.cast_to_raw(mesg_body || utl_tcp.CRLF));
    utl_smtp.write_data(conn, CRLF);
  
    if length(trim(p_fichero)) is not null and length(trim(p_fichero)) > 0 then
      /* ******************************************************************************** */
      /* **********************         ADJUNTA ARCHIVOS         ************************ */
      /* ******************************************************************************** */
      utl_smtp.write_data(conn, FIRST_BOUNDARY);
      send_header('Content-Type', mime_type);
    
      file_type := F_GET_EXTENSION_FILE(p_fichero);
      if trim(file_type) != 'TXT' then
        send_header('Content-Disposition',
                    'attachment; filename= "' || attachment_file_name || '"' ||
                    crlf_a || 'Content-Transfer-Encoding: base64 ' ||
                    crlf_a || crlf_a);
      else
        send_header('Content-Disposition',
                    'attachment; filename= "' || attachment_file_name || '"' ||
                    crlf_a || 'Content-Transfer-Encoding: 7bit ' || crlf_a ||
                    crlf_a);
      end if;
    
      if trim(file_type) != 'TXT' then
        bfile_handle := bfilename(CONS_SERVER_RUTA, p_fichero);
        bfile_len    := dbms_lob.getlength(bfile_handle);
        pos          := 1;
        dbms_lob.open(bfile_handle, dbms_lob.lob_readonly);
      else
        file_handle := utl_file.fopen(CONS_SERVER_RUTA, p_fichero, 'r');
      end if;
      -- Append the file contents to the end of the message
      loop
        -- If it is a binary file, process it 57 bytes at a time,
        -- reading them in with a LOB read, encoding them in BASE64,
        -- and writing out the encoded binary string as raw data
        if trim(file_type) != 'TXT' then
          if pos + 57 - 1 > bfile_len then
            read_bytes := bfile_len - pos + 1;
          else
            read_bytes := 57;
          end if;
          dbms_lob.read(bfile_handle, read_bytes, pos, data);
          utl_smtp.write_raw_data(conn, utl_encode.base64_encode(data));
          pos := pos + 57;
          if pos > bfile_len then
            exit;
          end if;
          -- If it is a text file, get the next line of text, append a
          -- carriage return / line feed to it, and write it out         
        else
          --utl_file.get_line(file_handle,line);
          utl_file.get_line(v_file_handle, v_line);
          utl_smtp.write_data(conn, v_line || crlf);
        end if;
      end loop;
      -- Close the file (binary or text)
      if trim(file_type) != 'TXT' then
        dbms_lob.close(bfile_handle);
      else
        utl_file.fclose(file_handle);
      end if;
    
      utl_smtp.write_data(conn, chr(13) || chr(10)); -- LAST_BOUNDARY);             
    
      /* ******************************************************************************** */
      /* ******************************************************************************** */
      /* ******************************************************************************** */
    end if;
  
    utl_smtp.write_data(conn,chr(13) || chr(10));-- LAST_BOUNDARY);    
    utl_smtp.close_data(conn);
    utl_smtp.quit(conn);
    UTL_FILE.FCLOSE(v_file_handle);
    conn:=null;
  end;
  --------------------------------------------------------------------------------------------------------------
  function F_GET_EXTENSION_FILE(vNombreArchivo in varchar2) return varchar2 IS
    vExt varchar2(100);
  BEGIN
    select valor
      INTO vExt
      from (SELECT V.VALOR, rank() OVER(ORDER BY ORDEN desc) puesto
              FROM (SELECT EXTRACTVALUE(xt.column_value, 'e') VALOR,
                           ROWNUM ORDEN
                      FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                                             REPLACE(vNombreArchivo,
                                                                     '.',
                                                                     '</e><e>') ||
                                                             '</e></coll>'),
                                                     '/coll/e'))) xt) V)
     where puesto = 1;
  
    return upper(trim(vExt));
  END;
  /* ********************************************************************************************* */
  FUNCTION GET_EMAIL_SERVER(cCodGrupoCia_in IN CHAR default '001')
    RETURN VARCHAR2 IS
    v_vServer PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    SELECT DESC_CORTA
      INTO v_vServer
      FROM PBL_TAB_GRAL
     WHERE ID_TAB_GRAL = 38
       AND COD_APL = 'PTO_VENTA'
       AND COD_TAB_GRAL = 'FARMA_EMAIL'
       AND LLAVE_TAB_GRAL = '02';
      -- AND COD_GRUPO_CIA = cCodGrupoCia_in;
    RETURN v_vServer;
  END;

  /* ****************************************************************************************** */
  PROCEDURE ENVIA_CORREO_ORACLE(p_para      varchar2,
                                p_CC        varchar2,
                                p_CO        varchar2,
                                p_asunto    varchar2,
                                p_titulo    varchar2,
                                p_mensaje   varchar2,
                                p_fichero   VARCHAR2,
                                pDirectorio varchar2)
  
   IS
    p_desde varchar2(200) := 'oracle@mifarma.com.pe';
  BEGIN
    /*
        begin
          -- Call the procedure
          util_email_oracle.SENT_EMAIL(
                                        p_servidor => '10.11.1.252',
                                        p_desde => 'oracle@mifarma.com.pe',
                                        p_para => 'dubilluz,jquispe',
                                        p_cc => 'joliva@mifarma.com.pe',
                                        p_co => 'jquispe',
                                        p_asunto => 'MSG PRUEBA',
                                        p_titulo => 'titulooo',
                                        p_mensaje => 'HOLA COMO ESTAS!!!!',
                                        p_fichero => 'Funciones Analiticas con Oracle.pdf',
                                        pdirectorio => 'DIR_INTERFACES');
        end;  
    */
    -- Call the procedure
    util_email_oracle.sent_email(p_servidor  => util_email_oracle.GET_EMAIL_SERVER,
                                 p_desde     => p_desde,
                                 p_para      => p_para,
                                 p_cc        => p_cc,
                                 p_co        => p_co,
                                 p_asunto    => p_asunto,
                                 p_titulo    => p_titulo,
                                 p_mensaje   => p_mensaje,
                                 p_fichero   => p_fichero,
                                 pdirectorio => pdirectorio);
  
  END;
  /* ************************************************************************************** */
  function OUT_ZIP_FILE(A_NOM_ARCHIVO    IN VARCHAR2,
                        A_DES_DIRECTORIO IN VARCHAR2) return varchar2 as
    C_BFILE             BFILE;
    L_ORIGINAL_BLOB     BLOB;
    L_COMPRESSED_BLOB   BLOB;
    L_UNCOMPRESSED_BLOB BLOB;
  
    C_DEST    FLOAT := 1;
    C_SRC     FLOAT := 1;
    C_BANDERA VARCHAR2(20) := '0';
    VSTART    NUMBER := 1;
    BYTELEN   NUMBER := 32000;
    LEN       NUMBER;
    MY_VR     RAW(32760);
    X         NUMBER;
  
    L_OUTPUT      UTL_FILE.FILE_TYPE;
    vnameFile_out varchar2(1000);
  BEGIN
  
    IF A_NOM_ARCHIVO IS NULL THEN
      RAISE_APPLICATION_ERROR(-20000,
                              'El nombre del archivo esta en nulo ' ||
                              CHR(13) || SQLERRM);
    END IF;
    ---abrimos el file que sera el file zipeado
    L_OUTPUT := UTL_FILE.FOPEN(A_DES_DIRECTORIO,
                               A_NOM_ARCHIVO || '.zip',
                               'wb',
                               32760);
  
    C_BANDERA := '1';
    ---declaramos el archivo a zipear y lo cargamos en un bfile
    C_BFILE   := BFILENAME(A_DES_DIRECTORIO, A_NOM_ARCHIVO);
    C_BANDERA := '2';
    ---si el archivo existe realizamos las operaciones
    IF DBMS_LOB.FILEEXISTS(C_BFILE) <> 0 THEN
    
      --abrimos el archivo
      DBMS_LOB.FILEOPEN(C_BFILE);
    
      --inicializamos los blobs
      L_ORIGINAL_BLOB     := TO_BLOB('1');
      L_COMPRESSED_BLOB   := TO_BLOB('1');
      L_UNCOMPRESSED_BLOB := TO_BLOB('1');
      --cargamos el archivo a un tipo blob
      C_BANDERA := '*' || DBMS_LOB.GETLENGTH(C_BFILE) || '*';
    
      IF DBMS_LOB.GETLENGTH(C_BFILE) <> 0 THEN
      
        DBMS_LOB.LOADBLOBFROMFILE(L_ORIGINAL_BLOB,
                                  C_BFILE,
                                  DBMS_LOB.GETLENGTH(C_BFILE),
                                  C_DEST,
                                  C_SRC);
      
        --         DBMS_LOB.createtemporary (L_ORIGINAL_BLOB, CACHE => TRUE,DBMS_LOB.SESSION);
      ELSE
        NULL;
      END IF;
    
      C_BANDERA := '3';
      --comprimos el blob que contiene el archivo y este nos devuelve un blob-l_compressed_blob
      UTL_COMPRESS.LZ_COMPRESS(L_ORIGINAL_BLOB, L_COMPRESSED_BLOB, 9);
    
      ---ahora tenemos que pasar el blob comprimido a un archivo del so
      VSTART    := 1;
      BYTELEN   := 32000;
      C_BANDERA := '4';
      X         := DBMS_LOB.GETLENGTH(L_COMPRESSED_BLOB);
      LEN       := X;
      --dbms_output.put_line(x);
    
      IF LEN < 32000 THEN
        UTL_FILE.PUT_RAW(L_OUTPUT, L_COMPRESSED_BLOB);
        UTL_FILE.FFLUSH(L_OUTPUT);
      ELSE
        -- se escribe en partes
        VSTART    := 1;
        C_BANDERA := '5';
        WHILE VSTART < LEN AND BYTELEN > 0 LOOP
        
          DBMS_LOB.READ(L_COMPRESSED_BLOB, BYTELEN, VSTART, MY_VR);
          C_BANDERA := '6';
          UTL_FILE.PUT_RAW(L_OUTPUT, MY_VR);
          C_BANDERA := '7';
          UTL_FILE.FFLUSH(L_OUTPUT);
        
          -- set the start position for the next cut
          VSTART := VSTART + BYTELEN;
        
          -- set the end position if less than 32000 bytes
          X := X - BYTELEN;
          IF X < 32000 THEN
            BYTELEN := X;
          END IF;
        
        END LOOP;
        C_BANDERA := '8';
        UTL_FILE.FCLOSE(L_OUTPUT);
      END IF;
      C_BANDERA := '9';
      DBMS_LOB.FREETEMPORARY(L_ORIGINAL_BLOB);
      C_BANDERA := '10';
      DBMS_LOB.FREETEMPORARY(L_COMPRESSED_BLOB);
      C_BANDERA := '11';
      DBMS_LOB.FREETEMPORARY(L_UNCOMPRESSED_BLOB);
      C_BANDERA := '12';
      DBMS_LOB.FILECLOSE(C_BFILE);
      C_BANDERA := '13';
    
      DBMS_LOB.filecloseall;
    
      --    RAISE_APPLICATION_ERROR(-20000,'jeje '||A_NOM_ARCHIVO);
      vnameFile_out := A_NOM_ARCHIVO || '.zip';
      return vnameFile_out;
    ELSE
      RAISE_APPLICATION_ERROR(-20000,
                              'NO EXISTE EL ARCHIVO ' || A_NOM_ARCHIVO);
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20000,
                              C_BANDERA || CHR(13) ||
                              'QUE PASO CON EL ARCHIVO ' || A_NOM_ARCHIVO ||
                              CHR(13) || SQLERRM);
  end;

END;
/
