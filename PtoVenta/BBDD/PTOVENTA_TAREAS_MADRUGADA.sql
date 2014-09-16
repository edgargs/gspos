--------------------------------------------------------
--  DDL for Package PTOVENTA_TAREAS_MADRUGADA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_TAREAS_MADRUGADA" is

  --JOLIVA 2012-02-23
  -- ENVIA ALERTA CON ENTREGAS SIN AFECTAR
  PROCEDURE P_ENVIA_ENTREGAS_SIN_AFECTAR;

  PROCEDURE P_PROCESA_TAREAS_MADRUGADA;

end;

/
