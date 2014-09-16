--------------------------------------------------------
--  DDL for Package PTOVENTA_ADMIN_MANT
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_ADMIN_MANT" is

  -- Author  : LMESIA
  -- Created : 19/07/2006 09:41:02 a.m.
  -- Purpose :
  POS_INICIO		      CHAR(1):='I';
  -- Public type declarations
  TYPE FarmaCursor IS REF CURSOR;

   C_C_RETORNO_EXITO   CHAR(1) := '1';
   C_C_RETORNO_ERROR_1 CHAR(1) := '2';
   C_C_RETORNO_ERROR_2 CHAR(1) := '3';
   --C_C_RETORNO_ERROR_3 CHAR(1) := '4';


  -- Public function and procedure declarations

  --Descripcion: Obtiene las series asignadas al local.
  --Fecha       Usuario		Comentario
  --12/07/2006  ERIOS    	Creación
  --05/07/2008  ASOLIS    MODIFICACION
  FUNCTION GET_PARAMETROS_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Actualiza los parametros del local.
  --Fecha       Usuario		Comentario
  --12/07/2006  ERIOS    	Creación
  --05/09/2007  DUBILLUZ  Modificacion
  --05/07/2008  ASOLIS    MODIFICACION
  PROCEDURE ACTUALIZAR_PARAMETROS_LOCAL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        vRutaImpReporte_in IN VARCHAR2,
                                        nMinPedPendientes_in IN NUMBER,
                                        vIndCambioPrecio_in IN CHAR,
                                       vIndCambioModeloImpresora_in IN CHAR,
                                        vIdUsu_in IN VARCHAR2);


  --Descripcion: LISTA LOS INDICARES DE HORARIO
  --Fecha       Usuario		Comentario
  --23/04/2007  PAULO    	Creación
  FUNCTION LISTA_INDICADORES_HORARIO
  RETURN FarmaCursor;

  --Descripcion: obtiene el secuencial por usuario
  --Fecha       Usuario		Comentario
  --23/04/2007  PAULO    	Creación
 /* FUNCTION GET_SECUENCIAL_HORARIOS(cCodGrupoCia_in      IN CHAR,
                                   cCodLocal_in         IN CHAR,
                                   cSecUsulocal_in      IN CHAR)
  RETURN CHAR;*/

  --Descripcion: LISTA LAS HORAS DEL CONTROL DE USUARUOS
  --Fecha       Usuario		Comentario
  --23/04/2007  PAULO    	Creación
 /* FUNCTION LISTA_CONTROL_HORAS_USU(cCodGrupoCia_in      IN CHAR,
                                  cCodLocal_in          IN CHAR,
                                  cSecUsulocal_in       IN CHAR)
  RETURN FarmaCursor;   */

 /* PROCEDURE GRABA_CONTROL_HORAS (cCodGrupoCia_in      IN CHAR,
                                 cCodLocal_in         IN CHAR,
                                 cSecUsulocal_in      IN CHAR,
                                 cIndControl_in       IN CHAR)  ;    */

  --Descripcion: Obtiene los motivos del control de horas
  --Fecha       Usuario		Comentario
  --27/04/2007  LREQUE    Creación
   FUNCTION ADMMANT_OBTIENE_MOTIVOS_CTRL
   RETURN FarmaCursor;

  --Descripcion: Verifica si el motivo ingresado es correcto
  --Fecha       Usuario		Comentario
  --27/04/2007  LREQUE    Creación
   FUNCTION ADMMANT_VERIFICA_INGRESO_CTRL(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCodMotivo_in   IN CHAR,
                                          cSecUsu_in      IN CHAR)
   RETURN CHAR;

  --Descripcion: Registra en el control de horas
  --Fecha       Usuario		Comentario
  --27/04/2007  LREQUE    Creación
   PROCEDURE ADMMANT_INGRESA_CONTROL(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecUsu_in      IN CHAR,
                                     cCodMotES_in    IN CHAR,
                                     cObservac_in    IN CHAR);

  --Descripcion: Obtiene el listado de control de horas
  --Fecha       Usuario		Comentario
  --27/04/2007  LREQUE    Creación
   FUNCTION ADMMANT_OBTIENE_CONTROL_HORAS(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cSecUsu_ini     IN CHAR)
   RETURN FarmaCursor;

  --Descripcion: Actualiza la ruta de Impresora en Delivery
  --Fecha       Usuario		Comentario
  --05/09/2007  DUBILLUZ  Creación
  PROCEDURE ACTUALIZA_IMP_DELIVERY_LOCAL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        vRutaImpReporte_in IN VARCHAR2,
                                        vIdUsu_in IN VARCHAR2);

  --Fecha       Usuario		Comentario
  --05/09/2007  dubilluz   creacion
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2);



end PTOVENTA_ADMIN_MANT;

/
