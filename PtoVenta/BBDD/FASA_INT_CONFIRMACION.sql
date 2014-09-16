--------------------------------------------------------
--  DDL for Package FASA_INT_CONFIRMACION
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."FASA_INT_CONFIRMACION" is

  --  g_cNumProdQuiebre INTEGER := 750;

  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;

  C_CANTIDAD_ARCHIVOS INTEGER := 5;

  PROCEDURE P_OPERA_DIF_RECEP_FASA(cFechaProceso    in char,
                                   cFechaProcesoFin in char);
  PROCEDURE P_CREA_AUX_DIF_RECEP(CodiLoca_in   IN varchar2,
                                 Id_Entrega_in IN varchar2);

  PROCEDURE P_CREA_INT_CONFIRMACION(CodiLoca_in   IN varchar2,
                                    Id_Entrega_in IN varchar2,
                                    fchRecepcion  in date);

  PROCEDURE INT_TXT_CONFIRMACION;
  --Fecha       Usuario   Comentario
  --10/07/2006  ERIOS     Creacion
  procedure P_ACTUALIZA_LOTE;

  PROCEDURE P_COMPLETA_SOBRANTE_ADICIONAL(CodiLoca_in   IN varchar2,
                                          Id_Entrega_in IN varchar2);

end;

/
