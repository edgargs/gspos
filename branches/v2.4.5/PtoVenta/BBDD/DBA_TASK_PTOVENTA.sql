--------------------------------------------------------
--  DDL for Package DBA_TASK_PTOVENTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."DBA_TASK_PTOVENTA" IS

  -- Author  : JOSE LUNA
  -- Created : 08/05/2006 05:31:03 p.m.
  -- Purpose : Tareas administrativas remotas del dba y operador
  -- modificacion generacion de bats para interfaces 20060817
  -- modificacion proceso contempla la verificacion de lo enviado a matriz para poder moverlo a old  20070831

  PROCEDURE genera_bat_exporta(cCodLocal_in	   IN CHAR);
  PROCEDURE genera_bat_ftp;
  PROCEDURE genera_comandos_ftp(cCodLocal_in	   IN CHAR);
  PROCEDURE tarea_nocturna;
  PROCEDURE genera_bat_ftp_int;
  PROCEDURE genera_bat_ftp_int_3v;
  PROCEDURE genera_bat_ftp_int_4v;
  PROCEDURE genera_bat_ftp_int_CE;
  PROCEDURE genera_bat_ftp_int_RMT;
  PROCEDURE genera_comandos_ftp_int(cCodLocal_in	   IN CHAR);
  PROCEDURE genera_comandos_ftp_int_3v(cCodLocal_in	   IN CHAR);
  PROCEDURE genera_comandos_ftp_int_4v(cCodLocal_in	   IN CHAR);
  PROCEDURE genera_comandos_ftp_int_CE(cCodLocal_in	   IN CHAR);
  PROCEDURE genera_comandos_ftp_int_RMT(cCodLocal_in	   IN CHAR);
  PROCEDURE genera_bat_exporta_int(cCodLocal_in	   IN CHAR);
  PROCEDURE genera_bat_exporta_int_3v(cCodLocal_in	   IN CHAR);
  PROCEDURE genera_bat_exporta_int_4v(cCodLocal_in	   IN CHAR);
  PROCEDURE genera_bat_exporta_int_CE(cCodLocal_in	   IN CHAR);
  PROCEDURE genera_bat_exporta_int_RMT(cCodLocal_in	   IN CHAR);
  PROCEDURE genera_new_importa_bat;
  PROCEDURE GENERA_INT_DIARIAS(ndias in number default 2);
  PROCEDURE GENERA_INT_DIARIAS_3V;
  PROCEDURE GENERA_INT_DIARIAS_4V(nDiasAtras_in in integer default 2);
  PROCEDURE GENERA_INT_DIARIAS_RMT;
  function  get_semana(pfecha date) return varchar2;
  function  get_zona return varchar2;
  --PROCEDURE actualiza_bines;
END Dba_Task_Ptoventa;

/
