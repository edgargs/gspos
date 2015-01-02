--------------------------------------------------------
--  DDL for Package DBA_SESSION
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."DBA_SESSION" IS
FUNCTION FP_COUNT_SESSION(pnom_machine CHAR,pnom_usuario CHAR) return number ;
FUNCTION FP_last_activity(pnom_machine CHAR,pnom_usuario CHAR) return date ;
procedure sp_kill_session(pnom_machine CHAR) ;
end;

/
