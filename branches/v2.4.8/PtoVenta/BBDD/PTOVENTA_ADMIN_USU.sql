--------------------------------------------------------
--  DDL for Package PTOVENTA_ADMIN_USU
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_ADMIN_USU" AS

	TYPE FarmaCursor IS REF CURSOR;
/*************************************************************/
	C_C_USU_OPERA 	      PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE := '000';
	COD_NUMERA_SEC_USU    PBL_NUMERA.COD_NUMERA%TYPE := '002';
	ROLES_PTOVENTA   	  PBL_ROL.TIP_ROL%TYPE := '01';
	ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
	POS_INICIO		      CHAR(1):='I';
  TIPO_ROL_CAJERO     CHAR(3):='009';
  --21/01/2008 DUBILLUZ CREACION
  TIPO_ROL_AUDITOR    CHAR(3):='012';
/*************************************************************/
  --Descripcion: Obtiene el listado de los usuarios del local
  --Fecha       Usuario		Comentario
  --02/02/2006  MHUAYTA     Creación
  FUNCTION USU_LISTA_USUARIOS_LOCAL(cCodGrupoCia_in    IN CHAR,
                                    cCodLocal_in       IN CHAR,
                                     cEstadoActivo_in   IN CHAR)
  RETURN FarmaCursor;

/*************************************************************/
--Descripcion: Obtiene un nuevo secuencial para un usuario
  --Fecha       Usuario		Comentario
  --03/02/2006  MHUAYTA     Creación
FUNCTION USU_NUEVO_SECUENCIA_USU(cCodLocal_in 		IN CHAR,
		 					     cCodGrupoCia_in 	IN CHAR)
RETURN NUMBER;


/*************************************************************/
 --Descripcion: actualiza un usuario
  --Fecha       Usuario		Comentario
  --03/02/2006  MHUAYTA     Creación
  --13/07/2007  JORGE     MODIFICACION
PROCEDURE USU_MODIFICA_USUARIO(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
            						   	   cSecUsuLocal_in IN CHAR,
                               cCodTrab_in     IN CHAR,
            						   	   cNomUsu_in 	   IN CHAR,
            						   	   cApePat_in 	   IN CHAR,
            						   	   cApeMat_in 	   IN CHAR,
            						   	   cLoginUsu_in    IN CHAR,
            						   	   cClaveUsu_in    IN CHAR,
            						   	   cTelefUsu_in    IN CHAR,
            						   	   cDireccUsu_in   IN CHAR,
            						   	   cFecNac_in 	   IN CHAR,
            						   	   cCodUsu_in 	   IN CHAR,
                               cDni_in         IN CHAR);
/*************************************************************/
--Descripcion: cambia el estado a un usuario
  --Fecha       Usuario		Comentario
  --03/02/2006  MHUAYTA     Creación
PROCEDURE USU_CAMBIA_ESTADO_USU(cCodGrupoCia_in IN CHAR,
		  					    cCodLocal_in    IN CHAR,
							    cSecUsuLocal_in IN CHAR,
								cCodUsu_in      IN CHAR);
/*************************************************************/
--Descripcion: muestra la relacion de roles asignados a un usuario
  --Fecha       Usuario		Comentario
  --03/02/2006  MHUAYTA     Creación
FUNCTION USU_LISTA_ROLES_USUARIO(cCodGrupoCia_in  IN CHAR,
		 					     cCodLocal_in 	  IN CHAR,
							  	 cSecUsuLocal_in  IN CHAR)
RETURN FarmaCursor;
/*************************************************************/
--Descripcion: muestra la relacion de roles disponibles
  --Fecha       Usuario		Comentario
  --03/02/2006  MHUAYTA     Creación
FUNCTION USU_LISTA_ROLES
RETURN FarmaCursor;
/*************************************************************/
--Descripcion: limpia la asignacion de roles para un usuario
  --Fecha       Usuario		Comentario
  --03/02/2006  MHUAYTA     Creación
  --23/01/2008  dubilluz   modificacion
PROCEDURE USU_LIMPIA_ROLES_USUARIO(cCodGrupoCia_in  IN CHAR,
		  						   cCodLocal_in 	IN CHAR,
								   cSecUsuLocal_in  IN CHAR);
/*************************************************************/
--Descripcion: Obtiene los datos de un trabajador del maestro de trabajadores
  --Fecha       Usuario		Comentario
  --03/02/2006  MHUAYTA     Creación
  --27/11/2007  DUBILLUZ  MODIFICACION
FUNCTION USU_OBTIENE_DATA_TRABAJADOR(cCodCia_in  IN CHAR,
		 					         cCodTrab_in IN CHAR)
  RETURN FarmaCursor;
 /*************************************************************/
  --Descripcion: Determina si un usuario ya existe en la tabla de PBL_USU_LOCAL ( mediante cod de trabajador)
  --Fecha       Usuario		Comentario
  --03/02/2006  MHUAYTA     Creación
  --27/11/2007  DUBILLUZ   MODIFICACION
   FUNCTION USU_EXISTE_DUPLICADO(cCodGrupoCia_in  IN CHAR,
			 					 cCodLocal_in 	  IN CHAR,
			 					 cCodTrab_in 	  IN CHAR)
    RETURN VARCHAR2;
	/*************************************************************/
	 --Descripcion: Determina si un usuario ya existe en la tabla de PBL_USU_LOCAL (mediante login de usuario)
  --Fecha       Usuario		Comentario
  --03/02/2006  MHUAYTA     Creación
	FUNCTION USU_EXISTE_LOGIN_DUPLICADO(cCodGrupoCia_in  IN CHAR,
			 							cCodLocal_in 	 IN CHAR,
										cLogin_in 		 IN CHAR)
	RETURN VARCHAR2;
	/*************************************************************/
  --Descripcion: Asigna un rol especifico a un usuario
  --Fecha       Usuario		Comentario
  --03/02/2006  MHUAYTA     Creación
	PROCEDURE USU_AGREGA_ROL_USUARIO(cCodGrupoCia_in IN CHAR,
			  				         cCodLocal_in 	 IN CHAR,
							 		 cSecUsuLocal_in IN CHAR,
							 		 cCodRol_in 	 IN CHAR,
							 		 cUsuCreaRol_in  IN CHAR);

  --Descripcion: Obtiene la información de los trabajadores
  --Fecha       Usuario		Comentario
  --11/12/2006  LREQUE     Creación
  --27/11/2007 DUBILLUZ MODIFICACION
  FUNCTION USU_LISTA_TRABAJADORES(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor;


 --Descripcion: Ingresa un nuevo usuario
  --Fecha       Usuario		Comentario
  --03/02/2006  MHUAYTA     Creación
  --13/12/2006  LREQUE      Modificación CAMPO cCodCia_in
  --27/11/2007  DUBILLUZ    MODIFICACION
PROCEDURE USU_INGRESA_USUARIO(cCodGrupoCia_in IN CHAR,
                              cCodCia_in      IN CHAR,
		  				                cCodLocal_in 	  IN CHAR,
            						  	  cCodTrab_in 	  IN CHAR,
            						  	  cNomUsu_in 	    IN CHAR,
            						  	  cApePat_in 	    IN CHAR,
            						  	  cApeMat_in 	    IN CHAR,
            						  	  cLoginUsu_in 	  IN CHAR,
            						  	  cClaveUsu_in 	  IN CHAR,
            						  	  cTelefUsu_in 	  IN CHAR,
            						  	  cDireccUsu_in   IN CHAR,
            						  	  cFecNac_in 	    IN CHAR,
            						  	  cCodUsu_in 	    IN CHAR,
                              cDni_in         IN CHAR,
                              cCodTrabRRHH    IN CHAR
                              );


  /********************************************************************************/
  -- Busca si existe usuarios sin asignar caja
  --Fecha       Usuario		Comentario
  --12/07/2007  DUBILLUZ    Creación
  FUNCTION USU_EXISTE_USU_SIN_CAJA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in  IN CHAR)

  RETURN VARCHAR2;

  --Valida Rango de dias al modificar el codigo de trabajador para el usuario
  --Fecha       Usuario		Comentario
  --06/07/2007  JCORTEZ    Creación
  FUNCTION USU_VALIDA_ACTULIZACION(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                    vLogin IN VARCHAR2)

  RETURN VARCHAR2;

  --INDICADOR QUE VALIDARA SI ES QUE OBLIGA QUE EXISTA EN LA TABLA DE MAESTRO DE USUARIOS
  --Fecha         Usuario    Comentario
  --27/11/2007   DUBILLUZ    CREACION
  --28/11/2007   DUBILLUZ    MODIFICACION
  FUNCTION USU_GET_IND_VALIDA(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR )
  RETURN CHAR;

  --BUSCA SI EXISTE EL USUARIO
  --FECHA       USUARIO  COMENTARIO
  --27/11/2007  DUBILLUZ  CREACION
  FUNCTION USU_EXISTE_USUARIO(cCodGrupoCia_in   IN CHAR,
                              cCodTrabRR_HH_in  IN VARCHAR2)
  RETURN CHAR;

  /********************************************************************************/
  -- Busca si el usuario tiene aluna caja asignada
  --Fecha       Usuario		Comentario
  --11/07/2007  DUBILLUZ    Creación
  FUNCTION USU_EXISTE_CAJA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                    cSec_usu_local_in IN VARCHAR2)

  RETURN VARCHAR2;

  --Retorna el mensaje para el usuario.
  --Fecha       Usuario		Comentario
  --17/07/2008  ERIOS     Creacion
  FUNCTION GET_MENSAJE_USU(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cSecUsu_in IN CHAR)
  RETURN FarmaCursor;

  --Actualiza el contador de veces.
  --Fecha       Usuario		Comentario
  --17/07/2008  ERIOS     Creacion
  PROCEDURE ACT_MENSAJE_USU(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cSecUsu_in IN CHAR,vIdUsu_in IN VARCHAR2);

  /*************************************************************/
  --Obtiene la clave de un usuario para modificarlo
  --Fecha       Usuario		Comentario
  --02/09/08    DVELIZ    Creación

   FUNCTION OBTENER_CLAVE(cCodGrupoCia_in  IN CHAR,
                          cCodLocal_in 	   IN CHAR,
                          cSecUsu_in 	     IN CHAR)
    RETURN VARCHAR2;

    /******************************************************/
     --Descripcion: Obtiene el listado de los trabajadores del local
  --Fecha       Usuario		Comentario
  --16/02/2009  ASOLIS     Creación
  FUNCTION USU_LISTA_TRABAJADORES_LOCAL(cCodGrupoCia_in    IN CHAR,
                                    cCodLocal_in       IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: Obtiene los datos de un trabajador del local
  --Fecha       Usuario		Comentario
  --16/02/2009  asolis     Creación

FUNCTION USU_OBTIENE_DATA_TRAB_LOCAL(cCodCia_in  IN CHAR,
                                    cCodGrupoCia_in    IN CHAR,
                                    cCodLocal_in  IN CHAR,
		 					                       cCodTrab_in IN CHAR)
  RETURN FarmaCursor;


    --BUSCA SI EL USUARIO CUENTA CON CARNE DE SANIDAD
  --FECHA       USUARIO  COMENTARIO
  --16/02/2008  ASOLIS  CREACION
  FUNCTION USU_EXISTE_USUARIO_CARNE(cCodGrupoCia_in  IN CHAR,
			 					  cCodLocal_in     IN CHAR,
			 					  cCodTrab_in 	   IN CHAR)
     RETURN VARCHAR2;



  --Descripcion: Ingresa un carne de sanidad
  --Fecha       Usuario		Comentario
  --17/02/2009  ASOLIS     Creación

PROCEDURE USU_INGRESA_CARNE_USUARIO(cCodGrupoCia_in IN CHAR,
                              cCodCia_in         IN CHAR,
		  				                cCodLocal_in 	     IN CHAR,
            						  	  cCodTrab_in 	     IN CHAR,
                              cCodTrabRRHH_in    IN CHAR,
                              cNumCarne_in       IN CHAR,
                              cFechaExpe_in      IN CHAR,
                              cFechaVenc_in      IN CHAR

                              );

  --Descripcion: Envia Alerta de Registro de carne de sanidad
  --Fecha       Usuario		Comentario
  --17/02/2009  ASOLIS     Creación

PROCEDURE USU_ALERTA_INSR_CARNE_USUARIO(cCodGrupoCia_in IN CHAR,
                              cCodCia_in         IN CHAR,
		  				                cCodLocal_in 	     IN CHAR,
            						  	  cCodTrab_in 	     IN CHAR,
                              cCodTrabRRHH_in    IN CHAR,
                              cNumCarne_in       IN CHAR,
                              cFechaExpe_in      IN CHAR,
                              cFechaVenc_in      IN CHAR


                              );





  ---usado en job
  --Descripcion : Envía alerta (1 mes de anticipación) para informar la proximidad del vencimiento
               -- del carné de sanidad del Trabajador  al Administrador del local.
   --Fecha       Usuario		Comentario
   --24/02/2009  asolis     Creación
  PROCEDURE USU_ALERTA_CARNE_P_VENCER_ADL(cCodCia_in  IN CHAR,
                                   cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR);


   ---usado en job
  --Descripcion : Envía alerta (1 mes de anticipación y 10 dias de anticipación) para informar la proximidad del vencimiento
  --              del carné de sanidad del Trabajador  a la Dra Myriam Gallo
  --Fecha       Usuario		Comentario
  --24/02/2009  asolis     Creación
  PROCEDURE USU_ALERTA_CARNE_P_VENCER_MG(cCodCia_in  IN CHAR,
                                   cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR);

  ---usado en job
  --Descripcion: Obtiene datos de los Trabajadores de Local que no tienen Carné  y envia correo de alerta
  --Fecha       Usuario		Comentario
  --19/02/2009  asolis     Creación

  PROCEDURE USU_ALERTA_TRAB_SIN_CARNE(cCodCia_in    IN CHAR,
                                   cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR);


--Descripcion : Envia correo
--Fecha         Usuario Comentario
--19/02/2009    ASOLIS   Creacion

  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL);



--Descripcion : Envia correo al Administrador del local.
--Fecha         Usuario Comentario
--19/02/2009    ASOLIS   Creacion

  PROCEDURE ENVIA_CORREO_INF_MAIL_LOCAL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL);

 --Descripcion : Envia correo a la Dra Myriam Gallo
--Fecha         Usuario Comentario
--19/02/2009    ASOLIS   Creacion

  PROCEDURE ENVIA_CORREO_INF_MG(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL);



  --Obtener la fecha de Vencimiento proximo
   --FECHA       USUARIO  COMENTARIO
  --23/02/2008  ASOLIS  CREACION

     FUNCTION USU_OBTIENE_FECVENC_PROX(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cCodTrab_in     IN CHAR)--cCodTrabRR_HH_in IN CHAR)
    RETURN char;


 ---Descripcion: Determina si existe  carne de sanidad usado en otro usuario
  --Fecha       Usuario		Comentario
  --23/02/2009  ASOLIS     Creación

   FUNCTION USU_EXISTE_DUPLICADO_CARNE(cCodGrupoCia_in  IN CHAR,
			 					                       cCodLocal_in 	  IN CHAR,
			 					                       COD_TRAB_RRHH_IN	  IN CHAR,
                                       cNumCarne_in     IN CHAR)
    RETURN VARCHAR2;


    --Obtener la fecha de Vencimiento Carne
   --FECHA       USUARIO  COMENTARIO
  --23/02/2008  ASOLIS  CREACION

     FUNCTION USU_OBTIENE_FECVENC_PROX_CARNE(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cCodTrab_in IN CHAR)
    RETURN char;

    --Verifica TrabLocal tiene Carne Sanidad
    --FECHA     USUARIO COMENTARIO
    --26/02/09  ASOLIS  CREACION

    FUNCTION USU_VERIFICA_EXISTENCIA_CARNE(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in   IN CHAR,
                                           cCodTrab_in    IN CHAR)

    RETURN CHAR;

  --Descripcion: Obtiene datos deL Trabajador de Local que no tiene Carné
  --al marcar su ingreso y envia correo de alerta
  --Fecha       Usuario		Comentario
  --19/02/2009  asolis     Creación

  PROCEDURE USU_ALERTA_TRAB_S_CARNE_M_ING(cCodGrupoCia_in    IN CHAR,
                                   cCodLocal_in  IN CHAR,
                                   cCodTrab_in     IN CHAR);


END;

/
