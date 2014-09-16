--------------------------------------------------------
--  DDL for Package PTOVENTA_STOCK_NEG
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_STOCK_NEG" is

  -- Author  : LLEIVA
  -- Created : 26/12/2013 06:07:28 p.m.
  -- Purpose :

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Retorna listado de Solicitudes de Stock Negativo
  --Fecha       Usuario	Comentario
  --26/Dic/2013  LLEIVA  Creacion
  function LISTADO_SOL_STOCK_NEGATIVO(cNumSolic_in    IN CHAR,
                                      cEstado_in      IN CHAR,
                                      cSolicitante_in IN CHAR,
                                      cAprobador_in   IN CHAR,
                                      cFechaIni_in    IN CHAR,
                                      cFechaFin_in    IN CHAR) return FarmaCursor;

  --Descripcion: Retorna listado de detalles de Solicitudes de Stock Negativo
  --Fecha       Usuario	Comentario
  --27/Dic/2013  LLEIVA  Creacion
  function LISTADO_DET_STOCK_NEGATIVO(cNumSolic_in IN CHAR) return FarmaCursor;

  --Descripcion: Retorna listado de detalles de Solicitudes de Stock Negativo
  --Fecha       Usuario	Comentario
  --27/Dic/2013  LLEIVA  Creacion
  FUNCTION INV_REGULARIZAR_AJUSTE_KARDEX(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cCodProd_in      IN CHAR,
                                         cCodProdAnul_in  IN CHAR,
                                         cNeoCant_in      IN CHAR,
                                         cCodSol_in       IN CHAR,
                                         cUsu_in          IN CHAR) RETURN CHAR;

  --Descripcion: Envia un correo relativo a la regularizacion del stock negativo
  --Fecha       Usuario	Comentario
  --30/Dic/2013 LLEIVA  Creacion
  PROCEDURE ENVIAR_CORREO_REGULARIZACION(v_vDescLocal    IN CHAR,
                                         v_vUsuario      IN CHAR,
                                         v_vCodSol       IN CHAR,
                                         v_vCodProdReg   IN CHAR,
                                         v_vMovKardex    IN CHAR,
                                         v_vCantProd     IN CHAR);

  function LISTADO_VER_KARDEX(cCodProd    IN CHAR,
                              cCodLocal   IN CHAR) return FarmaCursor;

end PTOVENTA_STOCK_NEG;

/
