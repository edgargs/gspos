CREATE OR REPLACE PACKAGE PTOVENTA.DBA_ALERTAS is

  PROCEDURE ALERTA_INDICES ;

  PROCEDURE ALERTA_IND_UNUSABLE (instancia varchar2);

  PROCEDURE ALERTA_JOB;

  PROCEDURE ALERTA_JOBS_INVALIDO (instancia varchar2);

  PROCEDURE ALERTA_OBJETOS(recursivo boolean default false);

--  PROCEDURE ALERTA_OBJ_INVALIDOS (instancia varchar2);

  PROCEDURE ALERTA_ESPACIO_TS(recursivo boolean default false);

  PROCEDURE ALERTA_ESPACIO_FS;

  PROCEDURE ALERTA_ESTADISTICAS;

  PROCEDURE REDUCE_LGT_PED_REP_DET;

END DBA_ALERTAS;
/

create or replace package body ptoventa.DBA_ALERTAS is

  PROCEDURE ALERTA_INDICES  IS
      TYPE CUR_TYP IS REF CURSOR;
      c_estado_dblink    CUR_TYP;
      v_query_inst       varchar2(500);
      c_instancia        varchar2(20);

  BEGIN

      v_query_inst := 'select distinct cod_local cod_local from ptoventa.vta_caja_impr';
      OPEN c_estado_dblink FOR v_query_inst;

      FETCH c_estado_dblink INTO c_instancia;
      WHILE c_estado_dblink%found
      LOOP

          ALERTA_IND_UNUSABLE(c_instancia);
          FETCH c_estado_dblink INTO c_instancia;
          commit;
      END LOOP;
      CLOSE c_estado_dblink;

  END ALERTA_INDICES;

  PROCEDURE ALERTA_IND_UNUSABLE (instancia varchar2) AS  ---falta agregar variables de dbname o descripcion
      TYPE CUR_TYP IS REF CURSOR;
      c_objinvalidos     CUR_TYP;
      v_query            VARCHAR2(1250);

      c_OWNER            VARCHAR2(20);
      c_TABLE            VARCHAR2(30);
      c_IDX_NAME         VARCHAR2(30);
      c_IDX_TYPE         VARCHAR2(20);
      c_LAST_ANALYZED    DATE;
      c_REBUILD          VARCHAR2(80);

      c_cadena varchar2(32000);
      cont number(2);
  BEGIN
      v_query := 'SELECT OWNER,TABLE_NAME,INDEX_NAME,INDEX_TYPE,LAST_ANALYZED, ''ALTER INDEX ''||OWNER||''.''||INDEX_NAME||'' REBUILD''
                 FROM DBA_INDEXES WHERE OWNER=''PTOVENTA'' AND STATUS=''UNUSABLE''';

      OPEN c_objinvalidos FOR v_query;
      FETCH c_objinvalidos INTO C_OWNER, c_TABLE, c_IDX_NAME, c_IDX_TYPE, c_LAST_ANALYZED, c_REBUILD;

         c_cadena :='<BR><TABLE STYLE="TEXT-ALIGN: LEFT; WIDTH: 85%;" BORDER="1" CELLPADDING="2" CELLSPACING="1">
         <TBODY><TR>
         <TH COLSPAN="4" ALIGN="CENTER" BGCOLOR="#0066CC"><FONT COLOR="#FFFFFF" FACE="COURIER NEW" SIZE=2>REPORTE INDEXES UNUSUABLE : '|| TO_CHAR(SYSDATE,'DD/MM/YYYY')||'  </TH>
         <TR STYLE="BACKGROUND-COLOR: #FFFFCC ; COLOR: #FFFFF"><FONT FACE="COURIER NEW" SIZE=2>
         <th>Esquema</th>
         <th>Tabla</th>
         <th>Indice</th>
         <th>Tipo</th>;
         </tr>';

      cont:=0;

      WHILE c_objinvalidos%found
      LOOP
          c_cadena := c_cadena || '<tr>
          <td>'||c_OWNER||'</td>
          <td>'||c_TABLE||'</td>
          <td>'||c_IDX_NAME||'</td>
          <th>'||c_IDX_TYPE||'</td>
          </tr>';
          cont:=cont+1;
          BEGIN
                EXECUTE IMMEDIATE c_REBUILD;
          EXCEPTION
             WHEN OTHERS THEN
             dbms_output.put_line('Error.'||' => '||Sqlerrm);
          END;

          FETCH c_objinvalidos INTO C_OWNER, c_TABLE, c_IDX_NAME, c_IDX_TYPE, c_LAST_ANALYZED, c_REBUILD;
          commit;
      END LOOP;
      CLOSE c_objinvalidos;

      c_cadena := c_cadena || '</TBODY></TABLE><TR><TR>';

      IF cont <> 0 THEN
          farma_email.envia_correo(cSendorAddress_in => 'oracle@mifarma.com.pe',
                                     cReceiverAddress_in => ' ccasas',
                                     cSubject_in => 'ALERTA BD INDEXES UNSUABLES LOCAL ' || UPPER(instancia),
                                     ctitulo_in =>  '<font size="4">ALERTA BD INDEXES UNSUABLES LOCAL ' || UPPER(instancia),
                                     cmensaje_in => c_cadena,
                                     cCCReceiverAddress_in => ' soportedba4,soportedba3,soportedba2,evaldez',
                                     cip_servidor => '10.18.0.17',
                                     cin_html => true);
       END IF;
  END ALERTA_IND_UNUSABLE;

  PROCEDURE ALERTA_JOB  IS
      TYPE CUR_TYP IS REF CURSOR;
      c_estado_dblink    CUR_TYP;
      v_query_inst       varchar2(500);
      c_instancia        varchar2(20);

  BEGIN

      v_query_inst := 'select distinct cod_local cod_local from ptoventa.vta_caja_impr';
      OPEN c_estado_dblink FOR v_query_inst;

      FETCH c_estado_dblink INTO c_instancia;
      WHILE c_estado_dblink%found
      LOOP

          ALERTA_JOBS_INVALIDO (c_instancia);
          FETCH c_estado_dblink INTO c_instancia;
          COMMIT;
      END LOOP;
      CLOSE c_estado_dblink;

  END ALERTA_JOB;

  PROCEDURE ALERTA_JOBS_INVALIDO (instancia varchar2) AS

      MESG_BODY VARCHAR2(32000);
      I         NUMBER;

  BEGIN
       I:=0;
       MESG_BODY := MESG_BODY||'    <BR>';
       MESG_BODY := MESG_BODY||'<TABLE STYLE="TEXT-ALIGN: LEFT; WIDTH: 85%;" BORDER="1"';
       MESG_BODY := MESG_BODY||' CELLPADDING="2" CELLSPACING="1">';
       MESG_BODY := MESG_BODY||'  <TBODY>';
       MESG_BODY := MESG_BODY||'     <TR>';
       MESG_BODY := MESG_BODY||'    <TH COLSPAN="6" ALIGN="CENTER" BGCOLOR="#0066CC"><FONT COLOR="#FFFFFF" FACE="COURIER NEW" SIZE=2>REPORTE JOBS INVALIDOS : '|| TO_CHAR(SYSDATE,'DD/MM/YYYY')||'  </TH>';
       MESG_BODY := MESG_BODY||'     <TR STYLE="BACKGROUND-COLOR: #FFFFCC ; COLOR: #FFFFF"><FONT FACE="COURIER NEW" SIZE=2>';
       MESG_BODY := MESG_BODY||'      <TH>JOB</TH>';
       MESG_BODY := MESG_BODY||'      <TH>LAST_DATE</TH>';
       MESG_BODY := MESG_BODY||'      <TH>NEXT_DATE</TH>';
       MESG_BODY := MESG_BODY||'      <TH>BROKEN</TH>';
       MESG_BODY := MESG_BODY||'      <TH>FAILURES</TH>';
       MESG_BODY := MESG_BODY||'      <TH>WHAT</TH>';
       MESG_BODY := MESG_BODY||'    </TR>';

        FOR X IN (

  	      SELECT * FROM
              (SELECT X.JOB,TO_CHAR(X.LAST_DATE,'DD-MM-YY HH12:MI:SS AM') LAST_DATE,TO_CHAR(X.NEXT_DATE,'DD-MM-YY HH12:MI:SS AM') NEXT_DATE,X.BROKEN,X.FAILURES,X.WHAT
              FROM DBA_JOBS X
              WHERE BROKEN='Y' OR LAST_DATE < SYSDATE-2 OR NEXT_DATE > SYSDATE + 1)
          WHERE what NOT LIKE '%begin ptoventa.pkg_reportes.ENVIA_LOCAL_KARDEX_EMAIL(); end;%'
          ORDER BY JOB ASC

    ) LOOP

          MESG_BODY := MESG_BODY||'   <TR>'||
                                  '<TD>'|| X.JOB ||'</TD>'||
                                  '<TD>'|| X.LAST_DATE ||'</TD>'||
                                  '<TD>'|| X.NEXT_DATE ||'</TD>'||
                                  '<TD>'|| X.BROKEN ||'</TD>'||
                                  '<TD>'|| X.FAILURES ||'</TD>'||
                                  '<TD>'|| X.WHAT ||'</TD>'||
                                  '   </TR>';
        I:=I+1;
        END LOOP ;

      MESG_BODY := MESG_BODY||'</TBODY>';
      MESG_BODY := MESG_BODY||'</TABLE>';
      MESG_BODY := MESG_BODY||'<BR>';
      MESG_BODY := MESG_BODY||'<BR>';
     -- ****************************************************************************************
       IF (I>0 )THEN
        farma_email.envia_correo(cSendorAddress_in => 'oracle@mifarma.com.pe',
                                     cReceiverAddress_in => ' ccasas',
                                     cSubject_in => 'ALERTA BD JOB LOCAL ' || UPPER(instancia) ,
                                     ctitulo_in =>  '<font size="4">ALERTA BD JOB LOCAL ' || UPPER(instancia) ||'  </font>',
                                     cmensaje_in => MESG_BODY,
                                     cCCReceiverAddress_in => ' soportedba4,soportedba3,soportedba2',
                                     cip_servidor => '10.18.0.17',
                                     cin_html => true);
        END IF;

  END ALERTA_JOBS_INVALIDO;

/*  PROCEDURE ALERTA_OBJETOS  IS
      TYPE CUR_TYP IS REF CURSOR;
      c_estado_dblink    CUR_TYP;
      v_query_inst       VARCHAR2(500);
      c_instancia        VARCHAR2(20);

  BEGIN

      v_query_inst := 'select distinct cod_local cod_local from ptoventa.vta_caja_impr';
      OPEN c_estado_dblink FOR v_query_inst;

      FETCH c_estado_dblink INTO c_instancia;
      WHILE c_estado_dblink%found
      LOOP

          alerta_obj_invalidos(c_instancia);
          FETCH c_estado_dblink INTO c_instancia;
          commit;
      END LOOP;
      CLOSE c_estado_dblink;

  END ALERTA_OBJETOS;*/

  PROCEDURE ALERTA_OBJETOS(recursivo boolean default false)  AS
    TYPE CUR_TYP IS REF CURSOR;
    c_objinvalidos     CUR_TYP;
    c_estado_dblink    CUR_TYP;
    v_query            VARCHAR2(1250);
    v_query_inst       VARCHAR2(400);
    c_instancia        varchar2(20);
-- DEFINIMOS LAS VARIABLES PARA ALMACENAR EL CONTENIDO DEL CURSOR
    c_owner            VARCHAR2(20);
    c_obj_nombre       VARCHAR2(30);
    c_obj_tipo         VARCHAR2(20);
    c_last_DDL         DATE;
    c_alter            VARCHAR2(80);
    c_sort_owner       NUMBER;
    c_sort_type        NUMBER;

    c_cadena VARCHAR2(32000);
    cont number(2);
  BEGIN
    v_query_inst := 'select distinct cod_local cod_local from ptoventa.vta_caja_impr';
    OPEN c_estado_dblink FOR v_query_inst;

    FETCH c_estado_dblink INTO c_instancia;
    WHILE c_estado_dblink%found
    LOOP
      v_query := 'select owner, object_name, object_type, last_ddl_time, ''Alter '' || decode(object_type,''PACKAGE BODY'',''PACKAGE'',''TYPE BODY'',''TYPE'',''UNDEFINED'',''SNAPSHOT'',object_type)
       || '' '' || owner || ''.'' || object_name || '' Compile '' || decode(object_type,''PACKAGE'',''SPECIFICATION'',''PACKAGE BODY'',''BODY'',''TYPE BODY'',''BODY'','' ''),
      decode(owner, ''SYS'', 1, ''SYSTEM'', 2, 3) SORT_OWNER, decode(object_type, ''VIEW'', 1, ''PACKAGE'', 2, ''TRIGGER'', 9, 3) SORT_TYPE
      from dba_objects where status <> ''VALID'' and object_type not like ''SYNONYM''';

      OPEN c_objinvalidos FOR v_query;
      FETCH c_objinvalidos INTO c_owner, c_obj_nombre, c_obj_tipo, c_last_DDL, c_alter, c_sort_owner, c_sort_type;

    	c_cadena :='<BR><TABLE STYLE="TEXT-ALIGN: LEFT; WIDTH: 85%;" BORDER="1" CELLPADDING="2" CELLSPACING="1">
         <TBODY>
         <TR>
         <TH COLSPAN="3" ALIGN="CENTER" BGCOLOR="#0066CC"><FONT COLOR="#FFFFFF" FACE="COURIER NEW" SIZE=2>REPORTE OBJETOS INVALIDOS : '|| TO_CHAR(SYSDATE,'DD/MM/YYYY')||'  </TH>
         <TR STYLE="BACKGROUND-COLOR: #FFFFCC ; COLOR: #FFFFF"><FONT FACE="COURIER NEW" SIZE=2>
         <th>Esquema</th>
         <th>Objeto</th>
         <th>Tipo</th>
         </tr>';

      cont:=0;

      WHILE c_objinvalidos%found
      LOOP
          IF recursivo = false THEN
--             insert into OPERADOR_DBA.MFB_RPT_OBJINV_PTOVENTA@INTEGRADOR (COD_LOCAL,OWNER,OBJECT_NAME,OBJECT_TYPE,LAST_DDL_TIME,TIMESTAMP,STATUS)
--             values (c_instancia, c_owner, c_obj_nombre, c_obj_tipo, c_last_DDL,sysdate,'INVALID');
             DELETE FROM PTOVENTA.TMP_DBA_OBJINV where trunc(FECHA) <= trunc(sysdate-3);
             COMMIT;
             INSERT INTO PTOVENTA.TMP_DBA_OBJINV (COD_LOCAL,OWNER,OBJECT_NAME,OBJECT_TYPE,LAST_DDL_TIME,FECHA)
             values (c_instancia, c_owner, c_obj_nombre, c_obj_tipo, c_last_DDL,sysdate);
             COMMIT;
          END IF;
          c_cadena := c_cadena || '<tr>
          <td>'||c_owner||'</td>
          <td>'||c_obj_nombre||'</td>
          <td>'||c_obj_tipo||'</td>
          </tr>';
          cont:=cont+1;
          BEGIN
                EXECUTE IMMEDIATE c_alter;
          EXCEPTION
                WHEN OTHERS THEN
                dbms_output.put_line('Error.'||' => '||Sqlerrm);
          END;

          FETCH c_objinvalidos INTO c_owner, c_obj_nombre, c_obj_tipo, c_last_DDL, c_alter, c_sort_owner, c_sort_type;
          COMMIT;
      END LOOP;
      CLOSE c_objinvalidos;

      c_cadena := c_cadena || '</TBODY></TABLE><TR><TR>';

      IF recursivo = false and cont <> 0 THEN
         ALERTA_OBJETOS(true);
      END IF;
      FETCH c_estado_dblink INTO c_instancia;
    END LOOP;
    IF cont <> 0 and recursivo = true THEN
         farma_email.envia_correo(cSendorAddress_in => 'oracle@mifarma.com.pe',
                                     cReceiverAddress_in => ' ccasas',
                                     cSubject_in => 'ALERTA BD OBJETOS INVALIDOS LOCAL ' || UPPER(c_instancia),
                                     ctitulo_in =>  '<font size="4">ALERTA BD OBJETOS INVALIDOS LOCAL ' || UPPER(c_instancia),
                                     cmensaje_in => c_cadena,
                                     cCCReceiverAddress_in => ' soportedba4,soportedba3,soportedba2,evaldez',
                                     cip_servidor => '10.18.0.17',
                                     cin_html => true);
    END IF;
  END ALERTA_OBJETOS;

  PROCEDURE ALERTA_ESPACIO_TS(recursivo boolean default false) as
    TYPE CUR_TYP IS REF CURSOR;
    c_espacio_libre    CUR_TYP;

    c_cod_local        CHAR(3);
    c_version_oracle   CHAR(3);

    v_query            VARCHAR2(1400);

    c_limite           NUMBER;

    c_nombre           VARCHAR2(20);
    c_libre            NUMBER(10);
    c_libre_8mb        NUMBER(10);
    c_libre_16mb       NUMBER(10);
    c_libre_64mb       NUMBER(10);
    c_total            NUMBER(10);
    c_auto             NUMBER(2);
    c_libre_auto       NUMBER(10);

    c_cadena           VARCHAR2(32000);
    cont               NUMBER(2);

  BEGIN
    c_limite := 3;

    select substr(banner,17,3) into c_version_oracle from v$version where rownum = 1;
    select distinct cod_local into c_cod_local from ptoventa.vta_caja_impr;

    v_query := 'select a.tablespace_name Nombre, round(a.Totalasig/1024/1024,0) Tamano, nvl(round(b.Libre/1024/1024,0),0) Libre, nvl(round(b.bytes_8MB/1024/1024,0),0) libre_8MB, nvl(round(b.bytes_16MB/1024/1024,0),0) libre_16MB,
      nvl(round(b.bytes_64MB/1024/1024,0),0) libre_64MB, (select count(1) from dba_data_files d where d.tablespace_name=a.tablespace_name and d.autoextensible=''YES'') AutoExt,
      nvl(c.MB_max_libre_extensible,0) Libre_Ext from
      (select tablespace_name, sum(bytes) Totalasig from dba_data_files where upper(tablespace_name) not like ''%UNDO%'' group by tablespace_name) a
      left outer join
     (select tablespace_name, sum(round(((CASE WHEN tablespace_name=''SYSTEM'' THEN maxbytes ELSE user_bytes + 1024*1024 END) - bytes)/(1024*1024),0)) MB_max_libre_extensible from dba_data_files where autoextensible = ''YES''
      group by tablespace_name, autoextensible) c
      on a.tablespace_name = c.tablespace_name
      left outer join
      (select tablespace_name, sum(bytes) Libre, sum(CASE WHEN bytes>=1024*1024*8 THEN BYTES ELSE 0 END) bytes_8MB,
      sum(CASE WHEN bytes>=1024*1024*16 THEN BYTES ELSE 0 END) bytes_16MB, sum(CASE WHEN bytes>=1024*1024*64 THEN BYTES ELSE 0 END) bytes_64MB from dba_free_space group by tablespace_name) b
      on a.tablespace_name = b.tablespace_name';

    OPEN c_espacio_libre FOR v_query;

    FETCH c_espacio_libre INTO c_nombre,c_total,c_libre,c_libre_8mb,c_libre_16mb,c_libre_64mb,c_auto,c_libre_auto;

    c_cadena :='<BR><TABLE STYLE="TEXT-ALIGN: LEFT; WIDTH: 85%;" BORDER="1" CELLPADDING="2" CELLSPACING="1">
     <TBODY><TR>
     <TH COLSPAN="7" ALIGN="CENTER" BGCOLOR="#0066CC"><FONT COLOR="#FFFFFF" FACE="COURIER NEW" SIZE=2>ALERTA DE ESPACIO EN TABLESPACES : '|| TO_CHAR(SYSDATE,'DD/MM/YYYY')||'  </TH>
     <TR STYLE="BACKGROUND-COLOR: #FFFFCC ; COLOR: #FFFFF"><FONT FACE="COURIER NEW" SIZE=2>
         <th>Tablespace</th>
         <th>Total (MB)</th>
         <th>Libre (MB)</th>
         <th>Usado (%)</th>
         <th>Libre Mayor a 8MB</th>
         <th>Libre Mayor a 16MB</th>
         <th>Version_BD</th>
         </tr>';

    cont:=0;

    WHILE c_espacio_libre%found
    LOOP
--        insert into operador_dba.check_tablespace@INTEGRADOR (nombre_ts, total_sz, libre_sz, num_auto_ext, libre_auto_sz, nombre_dblink, fecha, libre_8mb, libre_64mb, libre_16mb)
--        values (c_nombre,c_total,c_libre,c_auto,c_libre_auto,'XE_'||c_cod_local,sysdate,c_libre_8mb,c_libre_64mb,c_libre_16mb);
        delete from ptoventa.tmp_dba_tablespace where trunc(fecha) <= trunc(sysdate-3);
        COMMIT;
        insert into ptoventa.tmp_dba_tablespace (nombre_ts, total_sz, libre_sz, num_auto_ext, libre_auto_sz, nombre_dblink, fecha, libre_8mb, libre_64mb, libre_16mb)
        values (c_nombre,c_total,c_libre,c_auto,c_libre_auto,'XE_'||c_cod_local,sysdate,c_libre_8mb,c_libre_64mb,c_libre_16mb);
        COMMIT;
        IF (c_libre * 100) / c_total < c_limite and ((c_auto <> 0 and c_libre_auto <= 0) or c_auto = 0) or (((c_libre_8mb) * 100) / c_total < c_limite*0.6 and ((c_auto <> 0 and c_libre_auto <= 0) or c_auto = 0)) THEN
              c_cadena := c_cadena || '<tr>
              <td>'||c_nombre||'</td>
              <td>'||c_total|| '</td>
              <td>'||c_libre|| '</td>
              <td><b>'||to_char(100-round(c_libre/c_total*100,1))|| '</b></td>
              <td>'||c_libre_8mb|| '</td>
              <td>'||c_libre_16mb|| '</td>
              <td>'||c_version_oracle|| '</td>
              </tr>';
              cont:=cont+1;
        END IF;
        FETCH c_espacio_libre INTO c_nombre,c_total,c_libre,c_libre_8mb,c_libre_16mb,c_libre_64mb,c_auto,c_libre_auto;
        COMMIT;
    END LOOP;
    CLOSE c_espacio_libre;

    c_cadena := c_cadena || '</TBODY></TABLE><TR><TR>';

    IF cont <> 0 THEN
       IF (c_nombre = 'TS_PTOVENTA_DATA' or c_nombre = 'TS_PTOVENTA_IDX') and recursivo = false THEN
          REDUCE_LGT_PED_REP_DET;
          ALERTA_ESPACIO_TS(true);
       END IF;
       IF recursivo = true THEN
          farma_email.envia_correo(cSendorAddress_in => 'oracle@mifarma.com.pe',
                     cReceiverAddress_in => ' ccasas',
                     cSubject_in => 'ALERTA BD TABLESPACE LOCAL ' || UPPER(c_cod_local),
                     ctitulo_in =>  'ALERTA BD TABLESPACE LOCAL ' || UPPER(c_cod_local),
                     cmensaje_in => c_cadena,
                     cCCReceiverAddress_in => ' soportedba4; soportedba3; soportedba2; evaldez',
                     cip_servidor => '10.18.0.17',
                     cin_html => true);
       END IF;
    END IF;
    COMMIT;
  END ALERTA_ESPACIO_TS;

  ---******************************---
  PROCEDURE ALERTA_ESPACIO_FS AS
    TYPE CUR_TYP IS REF CURSOR;
    c_espacio_fs       CUR_TYP;

    c_cod_local        CHAR(3);
    c_ipaddr           VARCHAR2(15);

    c_esp_total        NUMBER;
    c_esp_disp         NUMBER;
    c_limite           NUMBER;
    c_file_system      VARCHAR2(15);
    c_tipo             VARCHAR2(4);
    v_query            VARCHAR2(500);

    c_cadena           VARCHAR2(32000);
    cont               NUMBER(2);
    aux1               VARCHAR2(15);

    BEGIN
        select distinct cod_local into c_cod_local from ptoventa.vta_caja_impr;
        select SYS_CONTEXT('USERENV', 'IP_ADDRESS', 15) into c_ipaddr from dual;

        v_query := 'select filesystem, tam_total, tam_avail, limite, tipo from ptoventa.et_espaciodisco';

        OPEN c_espacio_fs FOR v_query;
        FETCH c_espacio_fs INTO c_file_system, c_esp_total, c_esp_disp, c_limite, c_tipo;

        c_cadena :='<BR><TABLE STYLE="TEXT-ALIGN: LEFT; WIDTH: 85%;" BORDER="1" CELLPADDING="2" CELLSPACING="1">
          <TBODY><TR>
          <TH COLSPAN="6" ALIGN="CENTER" BGCOLOR="#0066CC"><FONT COLOR="#FFFFFF" FACE="COURIER NEW" SIZE=2>ALERTA DE ESPACIO EN FILESYSTEM : '|| TO_CHAR(SYSDATE,'DD/MM/YYYY')||'  </TH>
          <TR STYLE="BACKGROUND-COLOR: #FFFFCC ; COLOR: #FFFFF"><FONT FACE="COURIER NEW" SIZE=2>
          <th>IP</th>
          <th>Tipo</th>
          <th>File System</th>
          <th>Usado (%)</th>
          <th>Libre (MB)</th>
          <th>Total (MB)</th>
          </tr>';

        cont:=0;

        WHILE c_espacio_fs%found
        LOOP
            IF c_tipo <> 'SRV' then
               aux1 := 'SERVIDOR';
            END IF;
            IF c_tipo <> 'STB' then
               aux1 := 'STANDBY';
            END IF;
--            insert into operador_dba.check_filesystem@INTEGRADOR (nombre_dblink,filesystem,espacio_total,espacio_libre,limite,fecha)
--            values ('XE_'||c_instancia,c_file_system,c_esp_total,c_esp_disp,c_limite,sysdate);
            DELETE FROM PTOVENTA.TMP_DBA_FILESYSTEM WHERE trunc(fecha) <= trunc(sysdate-3);
            COMMIT;
            INSERT INTO PTOVENTA.TMP_DBA_FILESYSTEM (nombre_dblink,filesystem,espacio_total,espacio_libre,limite,fecha,tipo)
            VALUES ('XE_'||c_cod_local,c_file_system,c_esp_total,c_esp_disp,c_limite,sysdate,c_tipo);
            IF c_limite > (c_esp_disp/c_esp_total*100) THEN
                  c_cadena := c_cadena || '<tr>
                  <td>'||c_ipaddr||  '</td>
                  <td>'||aux1||'</td>
                  <td>'||c_file_system||'</td>
                  <td>'||to_char(round(100-((c_esp_disp/c_esp_total)*100),1))|| '</td>
                  <td>'||to_char(round(c_esp_disp/1024/1024,1))|| '</td>
                  <td>'||to_char(round(c_esp_total/1024/1024,1))|| '</td>
                  </tr>';
                  cont:=cont+1;
            END IF;
            FETCH c_espacio_fs INTO c_file_system, c_esp_total, c_esp_disp, c_limite, c_tipo;
            COMMIT;
        END LOOP;
        CLOSE c_espacio_fs;

        c_cadena := c_cadena || '</TBODY></TABLE><TR><TR>';

        IF cont <> 0 THEN
             farma_email.envia_correo(cSendorAddress_in => 'oracle@mifarma.com.pe',
                         cReceiverAddress_in => ' ccasas',
                         cSubject_in => 'ALERTA SO FILESYSTEM LOCAL ' || UPPER(c_cod_local),
                         ctitulo_in =>  'ALERTA SO FILESYSTEM LOCAL ' || UPPER(c_cod_local),
                         cmensaje_in => c_cadena,
                         cCCReceiverAddress_in => ' soportedba4; soportedba3; soportedba2; evaldez; operador',
                         cip_servidor => '10.18.0.17',
                         cin_html => true);
        END IF;
        COMMIT;
  END ALERTA_ESPACIO_FS;

  PROCEDURE ALERTA_ESTADISTICAS IS
    TYPE CUR_TYP IS REF CURSOR;
    c_reporte_stat     CUR_TYP;
    c_nombre_inst      CUR_TYP;
    v_query            VARCHAR2(1050);
    v_query_inst       VARCHAR2(450);

    c_cadena           varchar2(32000);
    cont               number(3);

    c_instancia        varchar2(15);
    c_total_tab        NUMBER;
    c_no_analizados    NUMBER;

  BEGIN
       v_query_inst := 'select distinct cod_local cod_local from ptoventa.vta_caja_impr';

--       v_query := 'SELECT a.total, b.no_analizados FROM ( SELECT COUNT(*) total FROM dba_tables WHERE owner = ''PTOVENTA'' ) a,
--                  ( SELECT COUNT(*) no_analizados FROM dba_tables WHERE trunc(last_analyzed) < trunc(sysdate) AND owner = ''PTOVENTA'' ) b';
       v_query := 'SELECT a.total, b.no_analizados FROM ( SELECT COUNT(*) total FROM dba_tables WHERE owner = ''PTOVENTA'' AND table_name NOT IN (SELECT table_name FROM dba_external_tables WHERE owner = ''PTOVENTA'') ) a,
                  ( SELECT COUNT(*) no_analizados FROM dba_tables WHERE trunc(last_analyzed) < trunc(sysdate) AND owner = ''PTOVENTA'' AND table_name NOT IN (SELECT table_name FROM dba_external_tables WHERE owner = ''PTOVENTA'') ) b';

       c_cadena :='<BR><TABLE STYLE="TEXT-ALIGN: LEFT; WIDTH: 85%;" BORDER="1" CELLPADDING="2" CELLSPACING="1">
         <TBODY><TR>
         <TH COLSPAN="4" ALIGN="CENTER" BGCOLOR="#0066CC"><FONT COLOR="#FFFFFF" FACE="COURIER NEW" SIZE=2>ALERTA DE ACTUALIZACION DE ESTADISTICAS : '|| TO_CHAR(SYSDATE,'DD/MM/YYYY')||'  </TH>
         <TR STYLE="BACKGROUND-COLOR: #FFFFCC ; COLOR: #FFFFF"><FONT FACE="COURIER NEW" SIZE=2>
             <th>Instancia</th>
             <th>Total Tablas</th>
             <th>Total NO Analizadas</th>
             <th>% NO Analizadas</th>
             </tr>';
       cont:=0;

       OPEN c_nombre_inst FOR v_query_inst;
       FETCH c_nombre_inst INTO c_instancia;
	     IF c_nombre_inst%notfound THEN
		      c_instancia := 'ERROR';
	     END IF;
	     COMMIT;
	     CLOSE c_nombre_inst;

       OPEN c_reporte_stat FOR v_query;
       FETCH c_reporte_stat INTO c_total_tab, c_no_analizados;

       WHILE c_reporte_stat%found
       LOOP
--           INSERT INTO operador_dba.check_estadistica@INTEGRADOR (nombre_dblink, total_tablas, total_no_stad, fecha)
--           VALUES ('XE_'||c_instancia, c_total_tab, c_no_analizados, sysdate);
           DELETE FROM PTOVENTA.TMP_DBA_ESTADISTICA WHERE trunc(fecha) <= trunc(sysdate-3);
           COMMIT;
           INSERT INTO PTOVENTA.TMP_DBA_ESTADISTICA (nombre_dblink, total_tablas, total_no_stad, fecha)
           VALUES ('XE_'||c_instancia, c_total_tab, c_no_analizados, sysdate);
           IF (c_no_analizados/c_total_tab)*100 > 60 THEN
              c_cadena := c_cadena || '<tr>
              <td>'||c_instancia|| '</td>
              <td>'||c_total_tab|| '</td>
              <td>'||c_no_analizados|| '</td>
              <td>'||to_char(round((c_no_analizados/c_total_tab)*100,1))|| '</td>
              </tr>';
              cont:=cont+1;
           END IF;
           FETCH c_reporte_stat INTO c_total_tab, c_no_analizados;
           COMMIT;
       END LOOP;
       CLOSE c_reporte_stat;

       c_cadena := c_cadena || '</TBODY>
              </TABLE><TR><TR>';

       IF cont <> 0 THEN
           farma_email.envia_correo(cSendorAddress_in => 'oracle@mifarma.com.pe',
               cReceiverAddress_in => ' ccasas',
               cSubject_in => 'ALERTA BD ESTADISTICAS LOCAL ' || UPPER(c_instancia),
               ctitulo_in =>  'ALERTA BD ESTADISTICAS LOCAL' || UPPER(c_instancia),
               cmensaje_in => c_cadena,
               cCCReceiverAddress_in => ' soportedba4; soportedba3; soportedba2; evaldez',
               cip_servidor => '10.18.0.17',
               cin_html => true);
       END IF;
       COMMIT;
  END ALERTA_ESTADISTICAS;

  PROCEDURE REDUCE_LGT_PED_REP_DET AS
    cuenta NUMBER := 0;
    total  NUMBER := 0;
    TYPE CUR_TYP IS REF CURSOR;
    c_rebuild_indices  CUR_TYP;
    v_query            VARCHAR2(1250);
    c_alter            VARCHAR2(120);

    CURSOR del_record_cur IS
    SELECT rowid FROM PTOVENTA.LGT_PED_REP_DET WHERE FEC_PED_REP_DET< SYSDATE-10;

  BEGIN
    v_query := 'select ''ALTER INDEX ''||i.owner||''.''||i.index_name||'' REBUILD '' from dba_indexes i
            where i.table_name=''LGT_PED_REP_DET'' and i.owner=''PTOVENTA''';
    FOR rec IN del_record_cur LOOP

      DELETE FROM PTOVENTA.LGT_PED_REP_DET
      WHERE rowid = rec.rowid;

      total := total + 1;
      cuenta := cuenta + 1;

      IF (cuenta >= 40000) THEN
          COMMIT;
          cuenta := 0;
          dbms_lock.sleep(3);
      END IF;

    END LOOP;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Deleted ' || total || ' records from PTOVENTA.LGT_PED_REP_DET.');

    BEGIN
        EXECUTE IMMEDIATE 'alter table PTOVENTA.LGT_PED_REP_DET enable row movement';
        EXECUTE IMMEDIATE 'alter table PTOVENTA.LGT_PED_REP_DET shrink space compact';
        EXECUTE IMMEDIATE 'alter table PTOVENTA.LGT_PED_REP_DET shrink space';
        EXECUTE IMMEDIATE 'alter table PTOVENTA.LGT_PED_REP_DET disable row movement';

/*      OPEN c_rebuild_indices FOR v_query;
        FETCH c_rebuild_indices INTO c_alter;

        WHILE c_rebuild_indices%found
        LOOP
            BEGIN
                  EXECUTE IMMEDIATE c_alter;
            END;
            FETCH c_rebuild_indices INTO c_alter;
            COMMIT;
        END LOOP;
        CLOSE c_rebuild_indices;
*/
        EXECUTE IMMEDIATE 'ALTER INDEX PTOVENTA.PK_LGT_PED_REP_DET REBUILD TABLESPACE TS_PTOVENTA_DATA';
        EXECUTE IMMEDIATE 'ALTER TABLE PTOVENTA.LGT_PED_REP_DET MOVE TABLESPACE TS_PTOVENTA_IDX';
        EXECUTE IMMEDIATE 'ALTER TABLE PTOVENTA.LGT_PED_REP_DET MOVE TABLESPACE TS_PTOVENTA_DATA';
        EXECUTE IMMEDIATE 'ALTER INDEX PTOVENTA.PK_LGT_PED_REP_DET REBUILD TABLESPACE TS_PTOVENTA_IDX';

        dbms_utility.compile_schema('PTOVENTA',false);
    END;

  END REDUCE_LGT_PED_REP_DET;

END DBA_ALERTAS;
/

