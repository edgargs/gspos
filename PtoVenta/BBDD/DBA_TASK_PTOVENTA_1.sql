--------------------------------------------------------
--  DDL for Package Body DBA_TASK_PTOVENTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."DBA_TASK_PTOVENTA" IS
--autor jluna
--revisado corregido y aumentado 20070831

  PROCEDURE genera_bat_exporta(cCodLocal_in	   IN CHAR) IS

    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(200);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='_xcorre_exportacion.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');

    cline  :='call D:\EXPORTADOR\exporta_dia.bat '||TO_CHAR(TRUNC(SYSDATE-1),'yyyymmdd')||' '||cCodLocal_in||' & ';
    utl_file.put (output_file, cline);
--==========   20070720 POR SOLICITUD DE Rolando Castro
--    cline  :='envia_ftp.bat'||' & ';
--    utl_file.put (output_file, cline);
--    cline  :='del envia_ftp.bat'||' & ';
--    utl_file.put (output_file, cline);
--    cline  :='del comandos.ftp'||' & ';
--    utl_file.put (output_file, cline);
--    cline  :='del '||TO_CHAR(TRUNC(SYSDATE-1),'yyyymmdd')||'.'||cCodLocal_in;
--    utl_file.put (output_file, cline);
--========== 2007 generacion de _xcorre_envia.bat para que envie el archivo y lo trae a otra ruta
    utl_file.fclose(output_file);
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='_xcorre_envia.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='if exist '||'d:\exportador\'||to_char(sysdate-1,'yyyymmdd')||'.'||cCodLocal_in||' ';
    utl_file.put (output_file, cline);
    cline  :='call D:\EXPORTADOR\envia_ftp.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='call D:\EXPORTADOR\trae_ftp.bat'||'  ';
    utl_file.put_line (output_file, cline);
    cline  :='if exist '||'d:\'||to_char(sysdate-1,'yyyymmdd')||'.'||cCodLocal_in||' ';
    utl_file.put (output_file, cline);
    cline  :='del d:\exportador\'||TO_CHAR(TRUNC(SYSDATE-1),'yyyymmdd')||'.'||cCodLocal_in||' & ';
    utl_file.put (output_file, cline);
    cline  :='del d:\'||TO_CHAR(TRUNC(SYSDATE-1),'yyyymmdd')||'.'||cCodLocal_in||' &';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\envia_ftp.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\trae_ftp.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\comandos.ftp'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\comandos2.ftp'||' ';
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);


    genera_bat_ftp;
    genera_comandos_ftp(cCodLocal_in);

    cpath       :='DIR_EXPORTADOR';
    cfilename   :='ptoventa.par';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='tables=(';
    utl_file.put_line (output_file, cline);

    for c in (select decode(rownum, 1, ' ', ',') ||'PTOVENTA.'||table_name fila
              from user_tables
              where TABLESPACE_NAME IS NOT NULL
              order by table_name)
    loop
        cline  :=c.fila;
        utl_file.put_line (output_file, cline);
    end loop;
    cline  :=')';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);

    cpath       :='DIR_EXPORTADOR';
    cfilename   :='_backup_data_local.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='call sqlplus /nolog @D:\EXPORTADOR\ptoventa_tareas_madrugada.sql';
    utl_file.put_line (output_file, cline);
--    cline  :='CALL exp system/namego@xe file='||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-1),'yyyymmdd')||'.dmp '||''||'log='||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-1),'yyyymmdd')||'.log owner=ptoventa statistics=NONE';
    cline  :='CALL exp system/namego@xe file=D:\EXPORTADOR\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-1),'yyyymmdd')||'.dmp '||''||'log=D:\EXPORTADOR\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-1),'yyyymmdd')||'.log statistics=NONE parfile=D:\EXPORTADOR\ptoventa.par ';
    utl_file.put_line (output_file, cline);
    cline  := 'CALL move /y d:\exportador\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-1),'yyyymmdd')||'.dmp  D:\exportador\backup_data_local\';
    utl_file.put_line (output_file, cline);
    cline  := 'CALL move /y d:\exportador\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-1),'yyyymmdd')||'.log  D:\exportador\backup_data_local\';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-7),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-7),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-8),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-8),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-9),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-9),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-10),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-10),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-11),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-11),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-12),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-12),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-13),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-13),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-14),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del D:\exportador\backup_data_local\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-14),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-7),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-7),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-8),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-8),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-9),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-9),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-10),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-10),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-11),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-11),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-12),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-12),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-13),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-13),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-14),'yyyymmdd')||'.dmp';
    utl_file.put_line (output_file, cline);
    cline  := 'call del \\10.10.'||trim(to_char(to_number(cCodLocal_in)))||'.3\d$\backup\d_exportador\BACKUP_DATA_LOCAL\'||cCodLocal_in||''||TO_CHAR(TRUNC(SYSDATE-14),'yyyymmdd')||'.log';
    utl_file.put_line (output_file, cline);

    utl_file.fclose(output_file);

   END;

   PROCEDURE genera_bat_ftp IS
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='envia_ftp.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');

    cline  :='ftp -n -v -s:D:\EXPORTADOR\comandos.ftp';
    utl_file.put_line (output_file, cline);
    cline :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);
--==============modificacion 20070720 pedido de Rolando Castro que reintente
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='trae_ftp.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');

    cline  :='ftp -n -v -s:D:\EXPORTADOR\comandos2.ftp';
    utl_file.put_line (output_file, cline);
    cline :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);
  END;

  PROCEDURE genera_comandos_ftp(cCodLocal_in	   IN CHAR) IS
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');

--    cline  :='open 10.11.1.30';
--    cline  :='open 10.11.1.250';
      cline  :='open 10.85.8.46'; -- msaavedra
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='put D:\EXPORTADOR\20*.*';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);

    utl_file.fclose(output_file);
--==========modificacion 20070720 pedido de Rolando Castro que reintente
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos2.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');

--    cline  :='open 10.11.1.30';
--    cline  :='open 10.11.1.250';
    cline  :='open 10.85.8.46'; -- msaavedra
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\';
    utl_file.put_line (output_file, cline);
    cline  :='get '||to_char(sysdate-1,'yyyymmdd')||'.'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);

    utl_file.fclose(output_file);
  END;

  PROCEDURE tarea_nocturna IS
	v_cod_grupo_cia CHAR(3);
  	v_cod_local CHAR(3);
  BEGIN
	   SELECT DISTINCT COD_GRUPO_CIA, COD_LOCAL
	   INTO v_cod_grupo_cia, v_cod_local
	   FROM VTA_CAJA_PAGO
	   ;
     declare
     begin
     ptoventa_tareas.INV_GRABA_STOCK_ACTUAL_PRODS(v_cod_grupo_cia,v_cod_local,'SISTEMAS');
     EXCEPTION
       WHEN OTHERS THEN
         farma_email.envia_correo(csendoraddress_in => 'jluna@mifarma.com.pe',creceiveraddress_in => 'operador@mifarma.com.pe',
         csubject_in => 'Tarea Nocturna Local: '||v_cod_local||' STOCK ACTUAL PRODS NO OK',ctitulo_in => 'Operacion Realizada con error .Coordinar con Jluna y Joliva',
         cmensaje_in => 'Fecha y Hora:'||to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'),cccreceiveraddress_in => 'jluna@mifarma.com.pe',
         cip_servidor => '10.11.1.252',cin_html => false);
     END;

     DECLARE
     BEGIN
 	     Ptoventa_REP.INV_CALCULA_RESUMEN_VENTAS(v_cod_grupo_cia, v_cod_local, TO_CHAR(SYSDATE-1,'dd/MM/yyyy'));
      /* farma_email.envia_correo(csendoraddress_in => 'jluna@mifarma.com.pe',creceiveraddress_in => 'operador@mifarma.com.pe',
       csubject_in => 'Tarea Nocturna Local: '||v_cod_local||' RESUMEN VENTAS OK',ctitulo_in => 'Operacion Realizada correctamente',
       cmensaje_in => 'Fecha y Hora:'||to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'),cccreceiveraddress_in => 'jluna@mifarma.com.pe',
       cip_servidor => '10.11.1.252',cin_html => false);*/
     EXCEPTION
       WHEN OTHERS THEN
         farma_email.envia_correo(csendoraddress_in => 'jluna@mifarma.com.pe',creceiveraddress_in => 'operador@mifarma.com.pe',
         csubject_in => 'Tarea Nocturna Local: '||v_cod_local||' RESUMEN VENTAS NO OK',ctitulo_in => 'Operacion Realizada con error .Coordinar con Jluna y Joliva',
         cmensaje_in => 'Fecha y Hora:'||to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'),cccreceiveraddress_in => 'jluna@mifarma.com.pe',
         cip_servidor => '10.11.1.252',cin_html => false);
     END;
     declare --añadido 20071121 12:12 jluna por correo pedido por Joliva
     begin
         Ptoventa_Reporte.ACT_RES_VENTAS_VENDEDOR (v_cod_grupo_cia, v_cod_local);
     exception
       WHEN OTHERS THEN
         farma_email.envia_correo(csendoraddress_in => 'jluna@mifarma.com.pe',creceiveraddress_in => 'operador@mifarma.com.pe',
         csubject_in => 'Tarea Nocturna Local: '||v_cod_local||' RESUMEN VENTAS VENDEDOR NO OK',ctitulo_in => 'Operacion Realizada con error .Coordinar con Jluna y Joliva',
         cmensaje_in => 'Fecha y Hora:'||to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'),cccreceiveraddress_in => 'jluna@mifarma.com.pe',
         cip_servidor => '10.11.1.252',cin_html => false);
     end;

     declare --añadido 20081218 16:02 JOLIVA
     begin
         PTOVENTA_REPORTE.ACT_RES_VENTAS_VENDEDOR_TIPO (v_cod_grupo_cia, v_cod_local, TO_CHAR(SYSDATE - 1,'dd/mm/yyyy'), '01');
         PTOVENTA_REPORTE.ACT_RES_VENTAS_VENDEDOR_TIPO (v_cod_grupo_cia, v_cod_local, TO_CHAR(SYSDATE - 1,'dd/mm/yyyy'), '02');
         PTOVENTA_REPORTE.ACT_RES_VENTAS_VENDEDOR_TIPO (v_cod_grupo_cia, v_cod_local, TO_CHAR(SYSDATE - 1,'dd/mm/yyyy'), '03');
     exception
       WHEN OTHERS THEN
         farma_email.envia_correo(csendoraddress_in => 'jluna@mifarma.com.pe',creceiveraddress_in => 'operador@mifarma.com.pe',
         csubject_in => 'Tarea Nocturna Local: '||v_cod_local||' RESUMEN VENTAS VENDEDOR - TIPO NO OK',ctitulo_in => 'Operacion Realizada con error .Coordinar con Jluna y Joliva',
         cmensaje_in => 'Fecha y Hora:'||to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'),cccreceiveraddress_in => 'jluna@mifarma.com.pe',
         cip_servidor => '10.11.1.252',cin_html => false);
     end;

     declare  --añadido 20080716 18:22 jluna por pedido de Rcastro , reporte sobrestock
     begin
       ptoventa_tareas.UPDATE_rep_prod_sobrestock;
     exception
       WHEN OTHERS THEN
         farma_email.envia_correo(csendoraddress_in => 'jluna@mifarma.com.pe',creceiveraddress_in => 'operador@mifarma.com.pe',
         csubject_in => 'Tarea Nocturna Local: '||v_cod_local||' reporte sobrestock NO OK',ctitulo_in => 'Operacion Realizada con error .Coordinar con Jluna y Joliva',
         cmensaje_in => 'Fecha y Hora:'||to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'),cccreceiveraddress_in => 'jluna@mifarma.com.pe',
         cip_servidor => '10.11.1.252',cin_html => false);
     end;

     declare  --añadido 20090225 jluna por pedido de Angelica Solis
     begin
       Ptoventa_Admin_Usu.USU_ALERTA_CARNE_P_VENCER_ADL('001','001',v_cod_local);
       Ptoventa_Admin_Usu.USU_ALERTA_CARNE_P_VENCER_MG('001','001',v_cod_local);
       Ptoventa_Admin_Usu.USU_ALERTA_TRAB_SIN_CARNE('001','001',v_cod_local);
     exception
       when others then
         farma_email.envia_correo(csendoraddress_in => 'jluna@mifarma.com.pe',creceiveraddress_in => 'operador@mifarma.com.pe',
         csubject_in => 'Tarea Nocturna Local: '||v_cod_local||' alertas carne NO OK',ctitulo_in => 'Operacion Realizada con error .Coordinar con Jluna y Joliva',
         cmensaje_in => 'Fecha y Hora:'||to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'),cccreceiveraddress_in => 'jluna@mifarma.com.pe',
         cip_servidor => '10.11.1.252',cin_html => false);
     end;

     declare  --añadido 20100521 JOLIVA -- Genera tablas de resumen de uso de campañas y packs
     begin
          PTOVENTA_RESUMEN.CARGA_RESUMENES ('001', v_cod_local, TO_CHAR(SYSDATE-1,'dd/MM/yyyy'));
     exception
       when others then
         farma_email.envia_correo(csendoraddress_in => 'jluna@mifarma.com.pe',creceiveraddress_in => 'operador@mifarma.com.pe',
         csubject_in => 'Tarea Nocturna Local: '||v_cod_local||' Resumen de uso de campañas y packs NO OK ',ctitulo_in => 'Operacion Realizada con error .Coordinar con Jluna y Joliva',
         cmensaje_in => 'Fecha y Hora:'||to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'),cccreceiveraddress_in => 'jluna@mifarma.com.pe',
         cip_servidor => '10.11.1.252',cin_html => false);
     end;
    actualiza_bines; --este ya tiene su propio exception
  END;
  --agregado 20060817

   --creado para enviar los archivos de la carpeta d:\interfaces
  PROCEDURE genera_bat_ftp_int
  --revisado y corregido 20070831
  --autor: jluna
  --genera programa que invoca a programas que envia,trae, comprueba, elimina los arhivos MC hacia Matriz
  --los archivos VTA no se comprueban antes de moverse a la carpeta old
  is
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='envia_ftp_int.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
--20070829 PARTE 1 DE EXPORTADOR MC VTA
    cline  :='call del D:\EXPORTADOR\listadoMC.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call del D:\EXPORTADOR\listadoVTA.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time /t >>D:\EXPORTADOR\listadoMC.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time /t >>D:\EXPORTADOR\listadoVTA.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\MC*.*   >>D:\EXPORTADOR\listadoMC.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\VTA*.*   >>D:\EXPORTADOR\listadoVTA.tot';
    utl_file.put_line (output_file, cline);
--  envia los arhivos MC y VTA
    cline  :='CALL ftp -n -v -s:D:\EXPORTADOR\comandos_int.ftp';
    utl_file.put_line (output_file, cline);
--  trae los arhivos MC y VTA para su comprobacion
    cline  :='CALL ftp -n -v -s:D:\EXPORTADOR\comandos_int_MC_got.ftp';
    utl_file.put_line (output_file, cline);
    cline  :='CALL ftp -n -v -s:D:\EXPORTADOR\comandos_int_VTA_got.ftp';
    utl_file.put_line (output_file, cline);
--    se comento por que ahora sera evaluado esto
--    cline  :='CALL move /y d:\INTERFACES\VTA*.* d:\INTERFACES\old\';
--    utl_file.put_line (output_file, cline);
--20070829 PARTE 2 DE EXPORTADOR MC VTA
    cline  :='call del D:\EXPORTADOR\listadoMC2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call del D:\EXPORTADOR\listadoVTA2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time/t >>D:\EXPORTADOR\listadoMC2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time/t >>D:\EXPORTADOR\listadoVTA2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\MCTMP\MC*.*  >>D:\EXPORTADOR\listadoMC2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\VTATMP\VTA*.*  >>D:\EXPORTADOR\listadoVTA2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call sqlplus ptoventa/caba123lleria@xe @D:\EXPORTADOR\evalua_MC.sql';
    utl_file.put_line (output_file, cline);
    cline  :='call sqlplus ptoventa/caba123lleria@xe @D:\EXPORTADOR\evalua_VTA.sql';
    utl_file.put_line (output_file, cline);
    cline  :='CALL D:\EXPORTADOR\elimina_mueve_MC.bat';
    utl_file.put_line (output_file, cline);
    cline  :='CALL D:\EXPORTADOR\elimina_mueve_VTA.bat';
    utl_file.put_line (output_file, cline);
--    cline  :='CALL move /y d:\INTERFACES\MC*.* d:\INTERFACES\old\';
--    utl_file.put_line (output_file, cline);
    cline :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);
  end;

  PROCEDURE genera_bat_ftp_int_3v
  --revisado y corregido 20070831
  --autor: jluna
  --genera programa que invoca a programas que envia,trae, comprueba, elimina los arhivos CC y CI hacia Matriz
  is
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='envia_ftp_int_3v.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
--20070829 PARTE 1 DE EXPORTADOR CI CC
    cline  :='call del D:\EXPORTADOR\listadoCC.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time /t >>D:\EXPORTADOR\listadoCC.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\CC*.*   >>D:\EXPORTADOR\listadoCC.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call del D:\EXPORTADOR\listadoCI.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time /t >>D:\EXPORTADOR\listadoCI.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\CI*.*   >>D:\EXPORTADOR\listadoCI.tot';
    utl_file.put_line (output_file, cline);
--  envia los archivos CC y CI
    cline  :='CALL ftp -n -v -s:D:\EXPORTADOR\comandos_int_3v.ftp';
    utl_file.put_line (output_file, cline);
--  trae  los archivos CC y CI
    cline  :='CALL ftp -n -v -s:D:\EXPORTADOR\comandos_int_3v_got.ftp';
    utl_file.put_line (output_file, cline);
--20070829 PARTE 2 DE EXPORTADOR CI CC
    cline  :='call del D:\EXPORTADOR\listadoCC2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time/t >>D:\EXPORTADOR\listadoCC2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\CCTMP\CC*.*  >>D:\EXPORTADOR\listadoCC2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call sqlplus ptoventa/caba123lleria@xe @D:\EXPORTADOR\evalua_CC.sql';
    utl_file.put_line (output_file, cline);
    cline  :='CALL D:\EXPORTADOR\elimina_mueve_CC.bat';
    utl_file.put_line (output_file, cline);
    --
    cline  :='call del D:\EXPORTADOR\listadoCI2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time/t >>D:\EXPORTADOR\listadoCI2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\CITMP\CI*.*  >>D:\EXPORTADOR\listadoCI2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call sqlplus ptoventa/caba123lleria@xe @D:\exportador\evalua_CI.sql';
    utl_file.put_line (output_file, cline);
    cline  :='CALL D:\EXPORTADOR\elimina_mueve_CI.bat';
    utl_file.put_line (output_file, cline);
--
--    cline  :='CALL move /y d:\INTERFACES\CC*.* d:\INTERFACES\old\';
--    utl_file.put_line (output_file, cline);
--    cline  :='CALL move /y d:\INTERFACES\CI*.* d:\INTERFACES\old\';
--    utl_file.put_line (output_file, cline);
    cline :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);
  end;
---
 PROCEDURE genera_bat_ftp_int_4v
  --genera programa que invoca a programas que envia,trae, comprueba, elimina los arhivos OC,DP,GCONF hacia Matriz
  is
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='envia_ftp_int_4v.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
--20131104 PARTE 1 DE EXPORTADOR OC,DP,GCONF
    cline  :='call del D:\EXPORTADOR\listadoOC.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time /t >>D:\EXPORTADOR\listadoOC.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\OC*.*   >>D:\EXPORTADOR\listadoOC.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call del D:\EXPORTADOR\listadoDP.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time /t >>D:\EXPORTADOR\listadoDP.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\DP*.*   >>D:\EXPORTADOR\listadoDP.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call del D:\EXPORTADOR\listadoGCONF.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time /t >>D:\EXPORTADOR\listadoGCONF.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\GCONF*.*   >>D:\EXPORTADOR\listadoGCONF.tot';
    utl_file.put_line (output_file, cline);
--  envia los archivos OC,DP,GCONF
    cline  :='CALL ftp -n -v -s:D:\EXPORTADOR\comandos_int_4v.ftp';
    utl_file.put_line (output_file, cline);
--  trae  los archivos CC y CI
    cline  :='CALL ftp -n -v -s:D:\EXPORTADOR\comandos_int_4v_got.ftp';
    utl_file.put_line (output_file, cline);
--20070829 PARTE 2 DE EXPORTADOR CI CC
    cline  :='call del D:\EXPORTADOR\listadoOC2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time/t >>D:\EXPORTADOR\listadoOC2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\OCTMP\OC*.*  >>D:\EXPORTADOR\listadoOC2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call sqlplus ptoventa/caba123lleria@xe @D:\EXPORTADOR\evalua_OC.sql';
    utl_file.put_line (output_file, cline);
    cline  :='CALL D:\EXPORTADOR\elimina_mueve_OC.bat';
    utl_file.put_line (output_file, cline);
    --
    cline  :='call del D:\EXPORTADOR\listadoDP2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time/t >>D:\EXPORTADOR\listadoDP2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\DPTMP\DP*.*  >>D:\EXPORTADOR\listadoDP2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call sqlplus ptoventa/caba123lleria@xe @D:\exportador\evalua_DP.sql';
    utl_file.put_line (output_file, cline);
    cline  :='CALL D:\EXPORTADOR\elimina_mueve_DP.bat';
    utl_file.put_line (output_file, cline);
--
    cline  :='call del D:\EXPORTADOR\listadoGCONF2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time/t >>D:\EXPORTADOR\listadoGCONF2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\GCONFTMP\GCONF*.*  >>D:\EXPORTADOR\listadoGCONF2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call sqlplus ptoventa/caba123lleria@xe @D:\exportador\evalua_GCONF.sql';
    utl_file.put_line (output_file, cline);
    cline  :='CALL D:\EXPORTADOR\elimina_mueve_GCONF.bat';
    utl_file.put_line (output_file, cline);
    cline :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);
  end;
------
  PROCEDURE genera_bat_ftp_int_CE
  --revisado y corregido 20070831
  is
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='envia_ftp_int_CE.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
--20070829 PARTE 1 DE EXPORTADOR CJE
    cline  :='call del D:\EXPORTADOR\listadoCJE.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time /t >>D:\EXPORTADOR\listadoCJE.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\CJE*.*   >>D:\EXPORTADOR\listadoCJE.tot';
    utl_file.put_line (output_file, cline);
-- envia los archivos
    cline  :='CALL ftp -n -v -s:D:\EXPORTADOR\comandos_int_CE.ftp';
    utl_file.put_line (output_file, cline);
-- trae los archivos para comprobar
    cline  :='CALL ftp -n -v -s:D:\EXPORTADOR\comandos_int_CE_got.ftp';
    utl_file.put_line (output_file, cline);
--20070829 PARTE 2 DE EXPORTADOR CJE
    cline  :='call del D:\EXPORTADOR\listadoCJE2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time/t >>D:\EXPORTADOR\listadoCJE2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\CJETMP\CJE*.*  >>D:\EXPORTADOR\listadoCJE2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call sqlplus ptoventa/caba123lleria@xe @D:\EXPORTADOR\evalua_CJE.sql';
    utl_file.put_line (output_file, cline);
    cline  :='CALL D:\EXPORTADOR\elimina_mueve_CJE.bat';
    utl_file.put_line (output_file, cline);
--    cline  :='CALL move /y d:\INTERFACES\CJE*.* d:\INTERFACES\old\';
--    utl_file.put_line (output_file, cline);
    cline :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);
  end;
---
PROCEDURE genera_bat_ftp_int_RMT
  --revisado y corregido 20090421
  is
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='envia_ftp_int_RMT.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
--20070829 PARTE 1 DE EXPORTADOR RMT
    cline  :='call del D:\EXPORTADOR\listadoRMT.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time /t >>D:\EXPORTADOR\listadoRMT.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\RMT*.*   >>D:\EXPORTADOR\listadoRMT.tot';
    utl_file.put_line (output_file, cline);
-- envia los archivos
    cline  :='CALL ftp -n -v -s:D:\EXPORTADOR\comandos_int_RMT.ftp';
    utl_file.put_line (output_file, cline);
-- trae los archivos para comprobar
    cline  :='CALL ftp -n -v -s:D:\EXPORTADOR\comandos_int_RMT_got.ftp';
    utl_file.put_line (output_file, cline);
--20070829 PARTE 2 DE EXPORTADOR RMT
    cline  :='call del D:\EXPORTADOR\listadoRMT2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call time/t >>D:\EXPORTADOR\listadoRMT2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call dir /b D:\INTERFACES\RMTTMP\RMT*.TXT  >>D:\EXPORTADOR\listadoRMT2.tot';
    utl_file.put_line (output_file, cline);
    cline  :='call sqlplus ptoventa/caba123lleria@xe @D:\EXPORTADOR\evalua_RMT.sql';
    utl_file.put_line (output_file, cline);
    cline  :='CALL D:\EXPORTADOR\elimina_mueve_RMT.bat';
    utl_file.put_line (output_file, cline);
--    cline  :='CALL move /y d:\INTERFACES\RMT*.* d:\INTERFACES\old\';
--    utl_file.put_line (output_file, cline);
    cline :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);
  end;
---
  PROCEDURE genera_comandos_ftp_int(cCodLocal_in	   IN CHAR) IS
  --modificacion 20070829 jluna creacion de comandos_int_got.ftp
  --envio arhivos VTA MC
  --comprobacion de archivos MC los VTA no se comprueban
  --revisado 20070831
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos_int.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='open 10.85.8.46';
--    cline  :='open 10.11.1.250';
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces';
    utl_file.put_line (output_file, cline);
--    cline  :='cd /interfaces/'||cCodLocal_in;
--    utl_file.put_line (output_file, cline);
    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='prompt';
    utl_file.put_line (output_file, cline);
--    cline  :='mput D:\INTERFACES\VTATMP\VTA*.*';
    cline  :='mput D:\INTERFACES\VTA*.*';
    utl_file.put_line (output_file, cline);
--    cline  :='mput D:\INTERFACES\MCTMP\MC*.*';
    cline  :='mput D:\INTERFACES\MC*.*';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);

    --archivo de comprobacion comandos_int_got.ftp
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos_int_MC_got.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='open 10.85.8.46';
--    cline  :='open 10.11.1.250';
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
--    utl_file.put_line (output_file, cline);
--    cline  :='cd /interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces\MCTMP';
    utl_file.put_line (output_file, cline);
    cline  :='prompt';
    utl_file.put_line (output_file, cline);
--    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/MC*.*';
    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/MC*.*';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);

  END;
  --
  PROCEDURE genera_comandos_ftp_int_3v(cCodLocal_in	   IN CHAR) IS
  --modificacion 20070829 jluna creacion de comandos_int_3v_got.ftp
  --movimiento de arhivos CC y CI
  --coregido 20070831
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos_int_3v.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='open 10.85.8.46';
--    cline  :='open 10.11.1.250';
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces';
--    utl_file.put_line (output_file, cline);
--    cline  :='cd /interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='prompt';
    utl_file.put_line (output_file, cline);
--    cline  :='mput D:\INTERFACES\CCTMP\CC*.*';
    cline  :='mput D:\INTERFACES\CC*.*';
    utl_file.put_line (output_file, cline);
--    cline  :='mput D:\INTERFACES\CITMP\CI*.*';
    cline  :='mput D:\INTERFACES\CI*.*';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);

    utl_file.fclose(output_file);
    --archivo de comprobacion comandos_int_3v_got.ftp
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos_int_3v_got.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='open 10.85.8.46';
--    cline  :='open 10.11.1.250';
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
--    utl_file.put_line (output_file, cline);
--    cline  :='cd /interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces\CCTMP';
    utl_file.put_line (output_file, cline);
    cline  :='prompt';
    utl_file.put_line (output_file, cline);
--    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/CC*.*';
    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/CC*.*';
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces\CITMP';
    utl_file.put_line (output_file, cline);
--    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/CI*.*';
    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/CI*.*';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);

    utl_file.fclose(output_file);

   --archivo de comprobacion comandos_int_VTA_got.ftp
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos_int_VTA_got.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='open 10.85.8.46';
--    cline  :='open 10.11.1.250';
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
--    utl_file.put_line (output_file, cline);
--    cline  :='cd /interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces\VTATMP';
    utl_file.put_line (output_file, cline);
    cline  :='prompt';
    utl_file.put_line (output_file, cline);
    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/VTA*.*';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);

    utl_file.fclose(output_file);

    --archivo de comprobacion comandos_int_RMT_got.ftp
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos_int_RMT_got.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='open 10.85.8.46';
--    cline  :='open 10.11.1.250';
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
--    utl_file.put_line (output_file, cline);
--    cline  :='cd /interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces\RMTTMP';
    utl_file.put_line (output_file, cline);
    cline  :='prompt';
    utl_file.put_line (output_file, cline);
    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/RMT*.*';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);

    utl_file.fclose(output_file);

  END;
---
 PROCEDURE genera_comandos_ftp_int_4v(cCodLocal_in	   IN CHAR) IS
  --movimiento de arhivos OC,DP,GCONF
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos_int_4v.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='open 10.85.8.46';
--    cline  :='open 10.11.1.250';
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces';
--    utl_file.put_line (output_file, cline);
--    cline  :='cd /interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='prompt';
    utl_file.put_line (output_file, cline);
--    cline  :='mput D:\INTERFACES\CCTMP\CC*.*';
    cline  :='mput D:\INTERFACES\OC*.*';
    utl_file.put_line (output_file, cline);
--    cline  :='mput D:\INTERFACES\CITMP\CI*.*';
    cline  :='mput D:\INTERFACES\DP*.*';
    utl_file.put_line (output_file, cline);
    cline  :='mput D:\INTERFACES\GCONF*.*';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);

    utl_file.fclose(output_file);
    --archivo de comprobacion comandos_int_4v_got.ftp
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos_int_4v_got.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='open 10.85.8.46';
--    cline  :='open 10.11.1.250';
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
--    cline  :='lcd d:\interfaces\OCTMP';
    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
--    utl_file.put_line (output_file, cline);
--    cline  :='cd /interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
--    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
    cline  :='lcd d:\interfaces\OCTMP';
    utl_file.put_line (output_file, cline);
    cline  :='prompt';
    utl_file.put_line (output_file, cline);
--    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/CC*.*';
--    cline  :='mget d:\interfaces\OCTMP\OC*.*';
    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/OC*.*';
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces\DPTMP';
    utl_file.put_line (output_file, cline);
--    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/CI*.*';
--    cline  :='mget d:\interfaces\DPTMP\DP*.*';
    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/DP*.*';
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces\GCONFTMP';
    utl_file.put_line (output_file, cline);
--    cline  :='mget d:\interfaces\GCONFTMP\GCONF*.*';
    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/GCONF*.*';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);

    utl_file.fclose(output_file);
  END;
---
  PROCEDURE genera_comandos_ftp_int_CE(cCodLocal_in	   IN CHAR) IS
  --modificacion 20070829 jluna creacion de comandos_int_CE_got.ftp
  --revisado     20070831 jluna
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos_int_CE.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='open 10.85.8.46';
--    cline  :='open 10.11.1.250';
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces';
--    utl_file.put_line (output_file, cline);
--    cline  :='cd /interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='prompt';
    utl_file.put_line (output_file, cline);
--    cline  :='mput D:\INTERFACES\CJETMP\CJE*.*';
    cline  :='mput D:\INTERFACES\CJE*.*';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);
    --archivo de comprobacion comandos_int_CE_got.ftp
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos_int_CE_got.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='open 10.85.8.46';
--    cline  :='open 10.11.1.250';
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
--    utl_file.put_line (output_file, cline);
--    cline  :='cd /interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces\CJETMP';
    utl_file.put_line (output_file, cline);
    cline  :='prompt';
    utl_file.put_line (output_file, cline);
--    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/CJE*.*';
    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/CJE*.*';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);
  END;
----
---
  PROCEDURE genera_comandos_ftp_int_RMT(cCodLocal_in	   IN CHAR) IS
  --modificacion 20070829 jluna creacion de comandos_int_RMT_got.ftp
  --revisado     20070831 jluna
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos_int_RMT.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
--    cline  :='open 10.11.1.250';
    cline  :='open 10.85.8.46';
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces';
    utl_file.put_line (output_file, cline);
--    cline  :='cd /interfaces/'||cCodLocal_in;
--    utl_file.put_line (output_file, cline);
    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='prompt';
    utl_file.put_line (output_file, cline);
--    cline  :='mput D:\INTERFACES\RMTTMP\RMT*.TXT';
    cline  :='mput D:\INTERFACES\RMT*.TXT';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);
    --archivo de comprobacion comandos_int_RMT_got.ftp
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='comandos_int_RMT_got.ftp';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
--    cline  :='open 10.11.1.250';
    cline  :='open 10.85.8.46';
    utl_file.put_line (output_file, cline);
    cline  :='user mfftp001 mamao456';
    utl_file.put_line (output_file, cline);
    cline  :='binary';
    utl_file.put_line (output_file, cline);
    cline  :='cd /usr/mifarma/ftproot/interfaces/'||cCodLocal_in;
--    utl_file.put_line (output_file, cline);
--    cline  :='cd /interfaces/'||cCodLocal_in;
    utl_file.put_line (output_file, cline);
    cline  :='lcd d:\interfaces\RMTTMP';
    utl_file.put_line (output_file, cline);
    cline  :='prompt';
    utl_file.put_line (output_file, cline);
--    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/RMT*.*';
    cline  :='mget /usr/mifarma/ftproot/interfaces/'||cCodLocal_in||'/RMT*.*';
    utl_file.put_line (output_file, cline);
    cline  :='bye';
    utl_file.put_line (output_file, cline);
    cline  :=' ';
    utl_file.put_line (output_file, cline);
    utl_file.fclose(output_file);
  END;
----
  PROCEDURE genera_bat_exporta_int(cCodLocal_in	   IN CHAR) IS
  -- genera el bat que se ejecuta con la tarea del sistema operativo
  -- para el envio de archivos MC y de VTA hacia matriz
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='_xcorre_exportacion_int.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');

    cline  :='D:\EXPORTADOR\envia_ftp_int.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\envia_ftp_int.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\comandos_int.ftp'||' & ';
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);
    genera_bat_ftp_int;
    genera_comandos_ftp_int(cCodLocal_in);
   END;

  PROCEDURE genera_bat_exporta_int_3v(cCodLocal_in	   IN CHAR) IS
  -- genera el bat que se ejecuta con la tarea del sistema operativo
  -- para el envio de archivos CC y de CI hacia matriz
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='_xcorre_exportacion_int_3v.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');

    cline  :='D:\EXPORTADOR\envia_ftp_int_3v.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\envia_ftp_int_3v.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\comandos_int_3v.ftp'||' & ';
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);
    genera_bat_ftp_int_3v;
    genera_comandos_ftp_int_3v(cCodLocal_in);
   END;
----
 PROCEDURE genera_bat_exporta_int_4v(cCodLocal_in	   IN CHAR) IS
  -- genera el bat que se ejecuta con la tarea del sistema operativo
  -- para el envio de archivos CC y de CI hacia matriz
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='_xcorre_exportacion_int_4v.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');

    cline  :='D:\EXPORTADOR\envia_ftp_int_4v.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\envia_ftp_int_4v.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\comandos_int_4v.ftp'||' & ';
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);
    genera_bat_ftp_int_4v;
    genera_comandos_ftp_int_4v(cCodLocal_in);
   END;
----
  PROCEDURE genera_bat_exporta_int_CE(cCodLocal_in	   IN CHAR) IS
  -- genera el bat que se ejecuta con la tarea del sistema operativo
  -- para el envio de caja electronica hacia matriz
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='_xcorre_exportacion_int_CE.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');

    cline  :='D:\EXPORTADOR\envia_ftp_int_CE.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\envia_ftp_int_CE.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\comandos_int_CE.ftp'||' & ';
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);
    genera_bat_ftp_int_CE;
    genera_comandos_ftp_int_CE(cCodLocal_in);
   END;
---------
  PROCEDURE genera_bat_exporta_int_RMT(cCodLocal_in	   IN CHAR) IS
  -- genera el bat que se ejecuta con la tarea del sistema operativo
  -- para el envio de caja electronica hacia matriz
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='_xcorre_exportacion_int_RMT.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');

    cline  :='D:\EXPORTADOR\envia_ftp_int_RMT.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\envia_ftp_int_RMT.bat'||' & ';
    utl_file.put (output_file, cline);
    cline  :='del D:\EXPORTADOR\comandos_int_RMT.ftp'||' & ';
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);
    genera_bat_ftp_int_RMT;
    genera_comandos_ftp_int_RMT(cCodLocal_in);
   END;
---------
PROCEDURE genera_new_importa_bat IS
    --20070212 creado para que funcione solo en matriz,
    --crea los programas importador y el programa que reintenta la importacion
    --en el directorio importador
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(80);
    output_file  utl_file.file_type;
  BEGIN
    cpath       :='DIR_IMPORTADOR';
    --
    --cd importador
    cfilename   :='cd /usr/mifarma/importador/new_importador.sh';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='sh /usr/mifarma/importador/new_importador_.sh '||to_char(sysdate-1,'YYYYMMDD');
    utl_file.put (output_file, cline);
    cline  :=' ';
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);

    --20070720 genera reintentador importacion
    cfilename   :='new_reintenta.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='call D:\EXPORTADOR\new_reintenta_.bat '||to_char(sysdate-1,'YYYYMMDD');
    utl_file.put (output_file, cline);
    cline  :=' ';
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);
 /*   cfilename   :='new_importador01.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='call new_importador_01.bat '||to_char(sysdate-1,'YYYYMMDD');
    utl_file.put (output_file, cline);
    cline  :=' ';
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);
    --
    cfilename   :='new_importador02.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='call new_importador_02.bat '||to_char(sysdate-1,'YYYYMMDD');
    utl_file.put (output_file, cline);
    cline  :=' ';
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);
    --
    cfilename   :='new_importador03.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='call new_importador_03.bat '||to_char(sysdate-1,'YYYYMMDD');
    utl_file.put (output_file, cline);
    cline  :=' ';
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);
    --
    cfilename   :='new_importador04.bat';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='call new_importador_04.bat '||to_char(sysdate-1,'YYYYMMDD');
    utl_file.put (output_file, cline);
    cline  :=' ';
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);*/
  END;

/*-----------------------------------------------------------------------------------------------------------------
GOAL : Generar Interfaces Diarias
Ammedments:
18-OCT-13      TCT         Lectura de cod cia para enviar ejecucion de intefaces con estructura Fasa
-------------------------------------------------------------------------------------------------------------------*/
PROCEDURE GENERA_INT_DIARIAS(ndias in number default 2) is
  /*antes era un script que se llamaba en matriz local por local Jluna 20070214
    20070502 se cambio default de 3 a 2
  */
  CODIGOCOMPANIA_IN CHAR(3);
  CCODLOCAL_IN      CHAR(3);
  FECHAINICIO_IN    CHAR(10);
  FECHAFIN_IN       CHAR(10);
  FECHAINICIO_VTA_IN    CHAR(10);
  FECHAFIN_VTA_IN       CHAR(10);
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(180);
    output_file  utl_file.file_type;
  -- 18-OCT-13, TCT, Lectura de Cod Cia
  vc_CodCia CHAR(3):=NULL;

begin
  CODIGOCOMPANIA_IN := '001';

  select distinct a.cod_local
  into   CCODLOCAL_IN
  from   vta_impr_local a;

  -- 20.- Lectura de Cod Cia
  select distinct a.cod_cia
  into   vc_CodCia
  from   vta_impr_local a;

--  select distinct a.cod_local
--  into   CCODLOCAL_IN
--  from   ce_cierre_dia_venta a
--  where  a.fec_cierre_dia_vta>sysdate-30;

  FECHAINICIO_IN := TO_CHAR(TRUNC(SYSDATE)-NDIAS,'dd/MM/yyyy');
  FECHAFIN_IN    := TO_CHAR(TRUNC(SYSDATE)-NDIAS,'dd/MM/yyyy');

  FECHAINICIO_VTA_IN := TO_CHAR(TRUNC(SYSDATE) -(NDIAS+2),'dd/MM/yyyy');
  FECHAFIN_VTA_IN    := TO_CHAR(TRUNC(SYSDATE) -(NDIAS+2),'dd/MM/yyyy');

  /*PTOVENTA.PTOVENTA_INT.INT_EJECT_RESUMEN_DIA_RANGO(
    CODIGOCOMPANIA_IN => CODIGOCOMPANIA_IN,
    CCODLOCAL_IN      => CCODLOCAL_IN,
    FECHAINICIO_IN    => FECHAINICIO_IN,
    FECHAFIN_IN       => FECHAFIN_IN
  );*/

   IF trim(vc_CodCia) = '002' THEN
      Ptoventa.mf_int_vta.int_eject_resumen_rango_dia('001',FECHAINICIO_VTA_IN,FECHAFIN_VTA_IN);
   END IF;


   IF trim(vc_CodCia) = '001' THEN
      -- 2009-03-09 JOLIVA: SE CONGELA LA GENERACIÓN DE AJUSTES
      -- 2009-03-24 JOLIVA: SE LIBERA LA GENERACIÓN DE AJUSTES
      Ptoventa.Ptoventa_Int.INT_RESUMEN_CC(CODIGOCOMPANIA_IN, CCODLOCAL_IN);
      --17/01/2008 ERIOS Se agrega la generacion de RD.
      PTOVENTA.PTOVENTA_INT.INT_GENERA_GUIA_PROV(CODIGOCOMPANIA_IN,CCODLOCAL_IN,0);
      -- 2009-03-09 JOLIVA: SE CONGELA LA GENERACIÓN DE AJUSTES
      -- 2009-03-24 JOLIVA: SE LIBERA LA GENERACIÓN DE AJUSTES
      PTOVENTA.PTOVENTA_INT.INT_RESUMEN_RD(CODIGOCOMPANIA_IN,CCODLOCAL_IN, -1);
   ELSE
       --GENERA KARDEX PARA AJUSTES RD
       PTOVENTA.PTOVENTA_INT.INT_GENERA_GUIA_PROV(CODIGOCOMPANIA_IN,CCODLOCAL_IN,0);
       --AJUSTES RD DE LOCALES FASA CON FARMAVENTA
      Ptoventa.pkg_fasa_interf.SP_RVIRTUAL_INT_FASA(CODIGOCOMPANIA_IN, CCODLOCAL_IN);     
   END IF;


  EXCEPTION
  WHEN OTHERS THEN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='GENERA_INT_DIARIAS.LOG';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='fallo en procedimiento GENERA_INT_DIARIAS de dba_task_ptoventa, avisar al dba'||to_char(sysdate,'yyyy/mm//dd hh24:mi:ss');
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);

end;
/*--------------------------------------------------------------------------------------------------------------------
GOAL : Generar Interfaces 3V
Ammedments:
18-OCT-13    TCT          Lectura de Cod_Cia para Formato de Interface a Generar
----------------------------------------------------------------------------------------------------------------------*/
PROCEDURE GENERA_INT_DIARIAS_3V is
  /*antes era un script que se llamaba en matriz local por local
   Jluna 20070214
  */
  CODIGOCOMPANIA_IN CHAR(3);
  CCODLOCAL_IN      CHAR(3);
--  FECHAINICIO_IN    CHAR(10);
--  FECHAFIN_IN       CHAR(10);
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(180);
    output_file  utl_file.file_type;

     -- 18-OCT-13, TCT, Lectura de Cod Cia
     vc_CodCia CHAR(3):=NULL;
BEGIN
  CODIGOCOMPANIA_IN := '001';
  select distinct a.cod_local
  into   CCODLOCAL_IN
  from   vta_impr_local a;

  -- 20.- Lectura de Cod Cia
  select distinct a.cod_cia
  into   vc_CodCia
  from   vta_impr_local a;

-- select distinct a.cod_local
--  into   CCODLOCAL_IN
--  from   ce_cierre_dia_venta a
--  where  a.fec_cierre_dia_vta>sysdate-30;
 --ccasas-desactivado por dubilluz -20131014
 -- Ptoventa.Ptoventa_Int.INT_RESUMEN_REC_PROD(CODIGOCOMPANIA_IN, CCODLOCAL_IN);
 --ccasas-activado por dubilluz -20131014



  IF trim(vc_CodCia) = '001' THEN
    --CONFIRMACIONES DE LOCALES MIFARMA
    Ptoventa.Ptoventa_Int.INT_RESUMEN_REC_PROD(CODIGOCOMPANIA_IN, CCODLOCAL_IN);

    -- MFA
    Ptoventa.Ptoventa_Int.INT_RESUMEN_MOV(CODIGOCOMPANIA_IN,CCODLOCAL_IN);
    -- 2009-03-09 JOLIVA: SE CONGELA LA GENERACIÓN DE AJUSTES
    -- 2009-03-24 JOLIVA: SE LIBERA LA GENERACIÓN DE AJUSTES
    -- 2009-04-13 JOLIVA: SE CONGELA LA GENERACIÓN DE AJUSTES
    -- 2009-09-23 JOLIVA: SE LIBERA LA GENERACIÓN DE AJUSTES
  Ptoventa.Ptoventa_Int.INT_RESUMEN_MOV2(CODIGOCOMPANIA_IN, CCODLOCAL_IN);
  ELSE
      begin
      --CONFIRMACIONES DE LOCALES FASA CON FARMAVENTA
      fasa_int_confirmacion.p_opera_dif_recep_fasa(to_char(trunc(sysdate, 'MM') - 10,
                                                  'dd/mm/yyyy'),
                                                  to_char(TRUNC(sysdate),
                                                  'dd/mm/yyyy'));
      end;

      --AJUSTES AJ DE LOCALES FASA CON FARMAVENTA
      Ptoventa.pkg_fasa_interf.SP_MOV2_INT_FASA(CODIGOCOMPANIA_IN,CCODLOCAL_IN);     
  END IF;


EXCEPTION
  WHEN OTHERS THEN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='GENERA_INT_DIARIAS_3V.LOG';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='fallo en procedimiento GENERA_INT_DIARIAS de dba_task_ptoventa, avisar al dba'||to_char(sysdate,'yyyy/mm//dd hh24:mi:ss');
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);

END;
/*INICIA INTERFACE OC*/
PROCEDURE GENERA_INT_DIARIAS_4V(nDiasAtras_in in integer default 2) is

   CCODGRUPOCIA_IN CHAR(3);
   CCODLOCAL_IN      CHAR(3);
   cpath       VARCHAR2(60);
   cfilename   VARCHAR2(80);
   cline       VARCHAR2(180);
   output_file  utl_file.file_type;
   vc_CodCia CHAR(3):=NULL;

begin
  CCODGRUPOCIA_IN := '001';
-- LECTURA CODIGO_LOCAL
  select distinct a.cod_local
  into   CCODLOCAL_IN
  from   vta_impr_local a;

-- LECTURA COD_CIA
  select distinct a.cod_cia
  into   vc_CodCia
  from   vta_impr_local a;

--IF trim(vc_CodCia) = '001' THEN
--NULL;
--ELSE
  IF trim(vc_CodCia) = '002' THEN
-- Call the procedure OC
    ptoventa.pkg_fasa_interf.SP_OCDIR_INT_FASA(CCODGRUPOCIA_IN, CCODLOCAL_IN,nDiasAtras_in);
-- Call the procedure DP
    ptoventa.pkg_fasa_interf.sp_devpro_int_fasa(CCODGRUPOCIA_IN, CCODLOCAL_IN,nDiasAtras_in);
-- Call the procedure GCONF
  end if;
-- ptoventa.fasa_int_confirmacion.P_OPERA_DIF_RECEP_FASA(to_char(trunc(sysdate, 'MM') - 10,'dd/mm/yyyy'),to_char(TRUNC(sysdate),'dd/mm/yyyy'));


  EXCEPTION
  WHEN OTHERS THEN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='GENERA_INT_DIARIAS_4V.LOG';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='fallo en procedimiento GENERA_INT_DIARIAS_4V de dba_task_ptoventa, avisar al dba '||to_char(sysdate,'yyyy/mm//dd hh24:mi:ss');
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);
end;
/*FINALIZA INTERFACE OC,DP,GCONF*/
PROCEDURE GENERA_INT_DIARIAS_RMT is
  /*antes era un script que se llamaba en matriz local por local
   Jluna 20090421
  */
  CODIGOCOMPANIA_IN CHAR(3);
  CCODLOCAL_IN      CHAR(3);
    cpath       VARCHAR2(60);
    cfilename   VARCHAR2(80);
    cline       VARCHAR2(180);
    output_file  utl_file.file_type;
BEGIN
  CODIGOCOMPANIA_IN := '001';
  select distinct a.cod_local
  into   CCODLOCAL_IN
  from   vta_impr_local a;
--  ptoventa_int_remito.int_eject_cierre_dia('001',cCodLocal_in);

EXCEPTION
  WHEN OTHERS THEN
    cpath       :='DIR_EXPORTADOR';
    cfilename   :='GENERA_INT_DIARIAS_RTM.LOG';
    output_file := utl_file.fopen (cpath,cfilename, 'W');
    cline  :='fallo en procedimiento GENERA_INT_DIARIAS_RMT de dba_task_ptoventa, avisar al dba'||to_char(sysdate,'yyyy/mm//dd hh24:mi:ss');
    utl_file.put (output_file, cline);
    utl_file.fclose(output_file);
END;
function  get_semana(pfecha date) return varchar2
is
 csemana varchar2(3);
begin
 csemana:=lpad(to_char(1+trunc((pfecha-to_date('20060424','yyyymmdd'))/7)),3,' ');
 return csemana;
end;
function  get_zona return varchar2
--JLUNA 20071023

is
CCOD_LOCAL VARCHAR2(3);
begin
 /* SELECT Z.COD_ZONA_VTA
 INTO   CCOD_LOCAL
 FROM   VTA_ZONA_VTA Z
 WHERE  Z.DESC_PC_JEFE_ZONA=sys_context('USERENV','TERMINAL');
 return CCOD_LOCAL;*/
 RETURN CASE WHEN sys_context('USERENV','TERMINAL') LIKE '%MF034AV%' THEN '001'
        WHEN sys_context('USERENV','TERMINAL') LIKE '%SONY%' THEN '002'
        WHEN sys_context('USERENV','TERMINAL') LIKE '%MF015DGL%' THEN '005'
        WHEN sys_context('USERENV','TERMINAL') LIKE '%MF048DMG%' THEN '004'
        WHEN sys_context('USERENV','TERMINAL') LIKE '%MF017DVP%' THEN '003'
        WHEN sys_context('USERENV','TERMINAL') LIKE '%MF001DMM%' THEN '006'
--        WHEN sys_context('USERENV','TERMINAL') LIKE '%MF001DMM%' THEN '%'
        WHEN sys_context('USERENV','TERMINAL') LIKE '%MF056DCH%' THEN '007'
        WHEN sys_context('USERENV','TERMINAL') LIKE '%MF044DVC%' THEN '008'
        WHEN sys_context('USERENV','TERMINAL') LIKE '%MF046DWM%' THEN '009'
--        WHEN sys_context('USERENV','TERMINAL') LIKE '%MFLIMITJL%' THEN '001'
--        WHEN sys_context('USERENV','TERMINAL') LIKE '%MFLIMITJO%' THEN '002'
        ELSE '%'
        END;
 /* exception
 when others then
  return '%';*/
end;
PROCEDURE actualiza_bines
--autor jluna 20131017 elaborado en funcion a requerimiento de AESCATE
is
CCODLOCAL_IN pbl_local.cod_local%type;

begin
  select distinct a.cod_local
  into   CCODLOCAL_IN
  from   vta_impr_local a;
insert into vta_fpago_tarj (bin, desc_prod, cod_grupo_cia, cod_forma_pago, tip_origen_pago)
SELECT BIN,DESC_PROD,COD_GRUPO_CIA,cod_forma_pago,TIP_ORIGEN_PAGO
FROM
(
(select
trim(nvl(dbms_lob.substr(substr(
             replace(replace(replace(replace(replace(replace(replace(replace(' '||cod_bin_tarjeta||' ',chr(9),' '),chr(10),' '),chr(11),' '),chr(12),' '),chr(13),' '),'  ',' '),'  ',' '),'  ',' '),
       instr(replace(replace(replace(replace(replace(replace(replace(replace(' '||cod_bin_tarjeta||' ',chr(9),' '),chr(10),' '),chr(11),' '),chr(12),' '),chr(13),' '),'  ',' '),'  ',' '),'  ',' '),' ',1,numero)+1,
       instr(replace(replace(replace(replace(replace(replace(replace(replace(' '||cod_bin_tarjeta||' ',chr(9),' '),chr(10),' '),chr(11),' '),chr(12),' '),chr(13),' '),'  ',' '),'  ',' '),'  ',' '),' ',1,numero+1)-
       instr(replace(replace(replace(replace(replace(replace(replace(replace(' '||cod_bin_tarjeta||' ' ,chr(9),' '),chr(10),' '),chr(11),' '),chr(12),' '),chr(13),' '),'  ',' '),'  ',' '),'  ',' '),' ',1,numero)-1
       )),'0')) BIN,
des_hijo DESC_PROD,
'001' COD_GRUPO_CIA,
decode(cod_emp_pag_tarjeta,'001','00084','002','00083') cod_forma_pago,
'PIN' TIP_ORIGEN_PAGO
from ptoventa.aux_forma_pago a,
     (select rownum numero from ptoventa.lgt_prod where rownum<5000) p
where cod_emp_pag_tarjeta in ('001','002')
and   cod_bin_tarjeta is not null
and   p.numero <= round( (length(cod_bin_tarjeta)+100) /7)
and   substr(replace(replace(replace(replace(replace(replace(replace(replace(' '||cod_bin_tarjeta||' ',chr(9),' '),chr(10),' '),chr(11),' '),chr(12),' '),chr(13),' '),'  ',' '),'  ',' '),'  ',' '),
       instr(replace(replace(replace(replace(replace(replace(replace(replace(' '||cod_bin_tarjeta||' ',chr(9),' '),chr(10),' '),chr(11),' '),chr(12),' '),chr(13),' '),'  ',' '),'  ',' '),'  ',' '),' ',1,numero)+1,
       instr(replace(replace(replace(replace(replace(replace(replace(replace(' '||cod_bin_tarjeta||' ',chr(9),' '),chr(10),' '),chr(11),' '),chr(12),' '),chr(13),' '),'  ',' '),'  ',' '),'  ',' '),' ',1,numero+1)-
       instr(replace(replace(replace(replace(replace(replace(replace(replace(' '||cod_bin_tarjeta||' ' ,chr(9),' '),chr(10),' '),chr(11),' '),chr(12),' '),chr(13),' '),'  ',' '),'  ',' '),'  ',' '),' ',1,numero)-1
       ) is not null
)
minus
select bin, desc_prod, cod_grupo_cia, cod_forma_pago, tip_origen_pago from vta_fpago_tarj where tip_origen_pago='PIN' AND COD_FORMA_PAGO IN ('00084','00083')
) AA
WHERE NOT EXISTS (SELECT 1
                  FROM vta_fpago_tarj BB
                  WHERE BB.bin=AA.BIN
                  AND   BB.cod_grupo_cia=AA.cod_grupo_cia
                  AND   BB.tip_origen_pago =AA.tip_origen_pago );
commit;
exception
when others then
 farma_email.envia_correo(cSendorAddress_in => 'oracle@mifarma.com.pe',
                          cReceiverAddress_in => 'aescate,jluna,ccasas',
                          cSubject_in => 'Error actualiza bines local '||CCODLOCAL_IN,
                          ctitulo_in => 'Error actualiza bines local '||CCODLOCAL_IN,
                          cmensaje_in => 'revisar el procedure actualiza_bines del paquete Dba_Task_Ptoventa ',
                          cip_servidor => farma_email.GET_EMAIL_SERVER,cin_html => true);

end;

END Dba_Task_Ptoventa;

/
