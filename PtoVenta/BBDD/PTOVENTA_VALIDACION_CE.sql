--------------------------------------------------------
--  DDL for Package PTOVENTA_VALIDACION_CE
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_VALIDACION_CE" AS

	TYPE FarmaCursor IS REF CURSOR;

	/*************************************************************/
  --Descripcion: ENVIA INFORMACION POR CORREO
  --Fecha       Usuario		Comentario
  --16/01/2007  PAULO     Creación
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR);

	/*************************************************************/
  --Descripcion: OBTIENE LOS USUARIOS SIN CODIGO DE TRABAJADOR
  --Fecha       Usuario		Comentario
  --16/01/2007  PAULO     Creación
    PROCEDURE USU_OBTIENE_USUARIOS_MAIL;

	/*************************************************************/
  --Descripcion: ENVIA INFORMACION POR CORREO AL OPERADOR
  --Fecha       Usuario		Comentario
  --16/01/2007  PAULO     Creación
   PROCEDURE INT_ENVIA_CORREO_INFO_OPER(vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR);




END;

/
