--------------------------------------------------------
--  DDL for Package PTOVENTA_TRANSF
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_TRANSF" is

  -- Author  : ERIOS
  -- Created : 12/09/2006 10:09:58 a.m.
  -- Purpose : TRANSFERENCIAS AUTOMATICAS

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Ejecuta el procedimiento de transferencias entre locales.
  --Fecha       Usuario	  Comentario
  --24/07/2006  ERIOS     Creaci贸n
  PROCEDURE EJECT_TRANSFERENCIAS(cCodGrupoCia_in IN CHAR,ctipo in char default 'N',ccod_local_ini_trans in char default '001' ,ccod_local_fin_trans in char default '999');

  --Descripcion: Obtiene las transferencias de los locales.
  --Fecha       Usuario	  Comentario
  --24/07/2006  ERIOS     Creaci贸n
  PROCEDURE GET_ORIGEN_TRANSFERENCIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Borra una transferencia de la tabla temporal.
  --Fecha       Usuario	  Comentario
  --24/07/2006  ERIOS     Creaci贸n
  PROCEDURE BORRA_TRANSF_TEMPORAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR);

  --Descripcion: Actualiza estado de las transferencias de origen.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     Creaci贸n
  PROCEDURE ACTUALIZA_EST_TRANSF_ORIGEN(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Envia las transferencias a los locales.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     Creaci贸n
  PROCEDURE SET_DESTINO_TRANSFERENCIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cCodLocal_origen IN CHAR);

  --Descripcion: Actualiza la transferencia original en el local de origen.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     Creaci贸n
  PROCEDURE ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,cEstNotaEs_in IN CHAR);

  --Descripcion: Obtiene las transferencias recibidas.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     Creaci贸n
  FUNCTION LISTA_TRANSF_LOCAL(cGrupoCia_in IN CHAR, cCia_in IN CHAR, cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene la cabacera de una transferencia recibida.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     Creaci贸n
  FUNCTION GET_CAB_TRANSF_LOCAL(cCodGrupoCia_in IN CHAR,cCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cCodLocalOrigen_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle de una transferencia recibida.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     Creaci贸n
  FUNCTION GET_DET_TRANSF_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cCodLocalOrigen_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Genera guia de ingreso en el local.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     Creaci贸n
  PROCEDURE GENERAR_GUIA_INGRESO_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                cCodLocalOrigen_in IN CHAR,cNumNota_in IN CHAR,
                                vIdUsu_in IN CHAR);

  --Descripcion: Envia correo de alerta.
  --Fecha       Usuario	  Comentario
  --26/07/2006  ERIOS     Creaci髇
  PROCEDURE INT_ENVIA_CORREO_ERROR(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2);

  --Descripcion: Obtiene la fracci髇 de un producto para un local.
  --Fecha       Usuario	  Comentario
  --07/09/2006  ERIOS     Creaci髇
  PROCEDURE GET_FRACCION_LOCAL(cCodGrupoCia_in 	   IN CHAR,
                              cCodLocal_in    	   IN CHAR,
                              cCodProd_in   IN CHAR,
                              nCantMov_in IN NUMBER,
                              nValFrac_in IN NUMBER,
                              cInd_out IN OUT CHAR);


end PTOVENTA_TRANSF;



/
