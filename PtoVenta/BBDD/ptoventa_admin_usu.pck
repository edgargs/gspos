CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_ADMIN_USU" AS

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
                              cFechaVenc_in      IN CHAR,
                              cUsuMod             IN CHAR DEFAULT NULL
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
                                   
                                   
 --Descripcion: Lista los registros de la asignacion de roles temporales como administradores
  -- a los cajeros 
  --Fecha       Usuario		Comentario
  --12/02/2015  chuanes     Creación                                   
  FUNCTION LISTA_ROLES_TEMP(cCodGrupoCia_in IN CHAR, cCodLocal_in    IN CHAR)
                   
                          
    RETURN FarmaCursor ; 
  --Descripcion: Busca Datos del Usuario por Documento de Identidad
  --Fecha       Usuario		Comentario
  --12/02/2015  chuanes     Creación  
       
  FUNCTION BUSCA_DATOS_USUARIO(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR,cDni_in IN CHAR
                   )
  RETURN FarmaCursor ;
  --Descripcion: Registra Rol de forma Temporal
  --Fecha       Usuario		Comentario
  --12/02/2015  chuanes     Creación 
 FUNCTION REGISTRA_ROL_TMP (cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cSec_Usu IN CHAR,cFec_Inicio IN CHAR,
                           cFec_Fin IN CHAR ,cUsuario IN CHAR )
                           
   RETURN CHAR;
  --Descripcion: Verifica la vigencia del rol tempral 
  --Fecha       Usuario		Comentario
  --13/02/2015  chuanes     Creación   
 FUNCTION VERIFICA_VIGENCIA_ROL_TMP(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR, cFecInico IN CHAR,cFecFin IN CHAR) 
  RETURN CHAR;
  --Descripcion: Verifica la vigencia a Futuro del Rol
  --Fecha       Usuario		Comentario
  --13/02/2015  chuanes     Creación   
  
    FUNCTION VERIFICA_VIGENCIA_FUTURO(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR, cFecInicio IN CHAR)  
  RETURN CHAR ;
  --Descripcion: Actualiza los datos del rol.
  --Fecha       Usuario		Comentario
  --13/02/2015  chuanes     Creación   
  
  FUNCTION ACTUALIZA_DATOS_ROL_TMP(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR, cFecInico IN CHAR,cFecFin IN CHAR ,cUsuario IN CHAR) 
  RETURN CHAR;
   --Descripcion: Elimina registro del rol temporal
  --Fecha       Usuario		Comentario
  --13/02/2015  chuanes     Creación 
  FUNCTION ELIMINA_DATOS_ROL_TMP(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR, cFecInico IN CHAR) 
  RETURN CHAR; 
  --Descripcion: Verifica  la no duplicidad del registro
  --Fecha       Usuario		Comentario
  --13/02/2015  chuanes     Creación 
     
  FUNCTION VERIFICA_NO_DUPLICIDAD(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR, cFecInicio IN CHAR) 
  RETURN CHAR;
  
  --Descripcion: Verifica  si hay algun usuario que esta vigente
  --Fecha       Usuario		Comentario
  --13/02/2015  chuanes     Creación 
 
      
  FUNCTION VERIFICA_CRUCE_FECHA(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR,cFecInicio IN CHAR) 
  RETURN CHAR;
   --Descripcion: Verifica fecha de inicio mayoy a hoy
  --Fecha       Usuario		Comentario
  --13/02/2015  chuanes     Creación 
   FUNCTION VERIFICA_FECHA_INICIO(cFecInicio IN CHAR) 
  RETURN CHAR;
   --Descripcion: Verifica cruce de fechas de la fecha fin cuando hay una actualizacion
  --Fecha       Usuario		Comentario
  --13/02/2015  chuanes     Creación 
  FUNCTION VERIFICA_CRUCE_FECHA_UPDATE(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR,cFecInicio IN CHAR,cFecFin IN CHAR) 
  RETURN CHAR;
   --Descripcion: Verfica codigo de Trabajador
  --Fecha       Usuario		Comentario
  --13/02/2015  chuanes     Creación 
  FUNCTION VERIFICA_COD_TRAB(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR) 
  RETURN CHAR;
   --Descripcion: Listado de roles temporales por rango de fecha
  --Fecha       Usuario		Comentario
  --05/03/2015  chuanes     Creación 
  
  FUNCTION LISTA_ROLES_RANGOFECHA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cFecInicio CHAR,cFecFin CHAR)                                     
  RETURN FarmaCursor;
  --Descripcion: Verifica que el rango entre fechas sea mejor a  30
  --Fecha       Usuario		Comentario
  --11.03.2015 chuanes     Creación   
   FUNCTION VERIFICA_RANGO_ENTRE_FECHAS(cFecInicio CHAR,cFecFin CHAR)
   RETURN CHAR; 
 --Descripcion: Maximo Rango Entre Fechas
  --Fecha       Usuario		Comentario
  --12.03.2015 chuanes     Creación     
   FUNCTION MAXIMO_RANGO_FECHAS
                           
    RETURN CHAR ; 
--Descripcion: Maximo Ultimas Claves al momento de Cambiar
  --Fecha       Usuario		Comentario
  --12.03.2015 chuanes     Creación         
    
  FUNCTION MAXIMO_ULTIMAS_CLAVES
                           
  RETURN CHAR;    

/*************************************************************************************/
--Descripcion: INDICADOR DE LAS SOLICITUDES DE HUELLA DACTILAR
--Fecha       Usuario		     Comentario
--04.12.2015  KMONCADA       Creación  
  FUNCTION F_VAR_SOLICITUD_HUELLAS 
    RETURN VARCHAR2;
    
/*************************************************************************************/
--Descripcion: obtiene datos de usuarios activos del FV
--Fecha       Usuario		     Comentario
--01.12.2015  KMONCADA       Creación    
  FUNCTION F_CUR_USUARIO_FV(pCodGrupoCia_in IN PBL_USU_LOCAL.COD_GRUPO_CIA%TYPE,
                            pCodLocal_in    IN PBL_USU_LOCAL.COD_LOCAL%TYPE,
                            pSecUsu_in      IN PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE)
    RETURN FARMACURSOR;

/*************************************************************************************/
--Descripcion: REGISTRA HUELLA DACTILAR DE LOS USUARIOS
--Fecha       Usuario		     Comentario
--01.12.2015  KMONCADA       Creación      
  FUNCTION F_REGISTRAR_HUELLA(pCodGrupoCia_in    IN PBL_USU_LOCAL.COD_GRUPO_CIA%TYPE,
                              pCodLocal_in       IN PBL_USU_LOCAL.COD_LOCAL%TYPE,
                              pSecUsu_in         IN PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE,
                              pHuella_in         IN PTOVENTA.CE_MAE_TRAB.HUELLA_MENIQUE_DER%TYPE,
                              pUsuMod_in         IN PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE,
                              pPosicionHuella_in IN NUMBER) 
    RETURN CHAR;
  
  FUNCTION F_NUM_REPITE_HUELLA
    RETURN NUMBER;
END;
/
CREATE OR REPLACE PACKAGE BODY "PTOVENTA_ADMIN_USU" AS
  /* ******************************************************************************************************* */
  FUNCTION USU_LISTA_USUARIOS_LOCAL(cCodGrupoCia_in    IN CHAR,
                                  cCodLocal_in       IN CHAR,
                                  cEstadoActivo_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curLab FarmaCursor;
  BEGIN
    OPEN curLab FOR
      SELECT SEC_USU_LOCAL || 'Ã' ||
             APE_PAT || 'Ã' ||
             NVL(APE_MAT,' ') || 'Ã' ||
             NOM_USU || 'Ã' ||
             LOGIN_USU || 'Ã' ||
             CASE  WHEN EST_USU = ESTADO_ACTIVO
              THEN 'Activo'
             WHEN EST_USU = ESTADO_INACTIVO
             THEN 'Inactivo'
             ELSE EST_USU
             END  || 'Ã' ||
             --CLAVE_USU || 'Ã' ||--modificado dveliz 02.09.08
             NVL(DIRECC_USU,' ') || 'Ã' ||
             TELEF_USU || 'Ã' ||
             NVL(TO_CHAR(FEC_NAC,'dd/MM/yyyy'),' ')  || 'Ã' ||
             COD_GRUPO_CIA || 'Ã' ||
             COD_LOCAL     || 'Ã' ||
             NVL(COD_TRAB,' ') || 'Ã' ||
             nvl(DNI_USU,' ')  || 'Ã' ||
             --SE MODIFICO
             NVL(COD_TRAB_RRHH,' ')
      FROM   PBL_USU_LOCAL
      WHERE  COD_LOCAL     = cCodLocal_in
      AND    COD_GRUPO_CIA = cCodGrupoCia_in
      AND    EST_USU LIKE '%'||cEstadoActivo_in||'%'
      AND    SEC_USU_LOCAL BETWEEN '001' AND '899'--ERIOS 25/10/20006: RANGO VALIDO
      ;
    RETURN curLab;
  END;
  /* ******************************************************************************************************* */

FUNCTION USU_NUEVO_SECUENCIA_USU(cCodLocal_in    IN CHAR,
                                 cCodGrupoCia_in IN CHAR)
RETURN NUMBER
IS
 v_secGenerado NUMBER;
BEGIN
   SELECT NVL(TO_NUMBER(MAX(SEC_USU_LOCAL))+1,1)
   INTO   v_secGenerado
   FROM   PBL_USU_LOCAL
   WHERE  COD_LOCAL     = cCodLocal_in
   AND    COD_GRUPO_CIA = cCodGrupoCia_in;
RETURN v_secGenerado;
END;

 /* ******************************************************************************************************* */

PROCEDURE USU_INGRESA_USUARIO(cCodGrupoCia_in IN CHAR,
                              cCodCia_in      IN CHAR,
                              cCodLocal_in     IN CHAR,
                              cCodTrab_in     IN CHAR,
                              cNomUsu_in       IN CHAR,
                              cApePat_in       IN CHAR,
                              cApeMat_in       IN CHAR,
                              cLoginUsu_in     IN CHAR,
                              cClaveUsu_in     IN CHAR,
                              cTelefUsu_in     IN CHAR,
                              cDireccUsu_in   IN CHAR,
                              cFecNac_in       IN CHAR,
                              cCodUsu_in       IN CHAR,
                              cDni_in         IN CHAR,
                              cCodTrabRRHH    IN CHAR
                              )
IS
  v_cNeoCod  CHAR(3);
  v_nNeoCod  NUMBER;
  vCantUsersLogin NUMBER;
BEGIN
   SELECT TO_NUMBER(COUNT(*)) INTO vCantUsersLogin
   FROM PBL_USU_LOCAL
   WHERE LOGIN_USU     = cLoginUsu_in    AND
        COD_LOCAL     = cCodLocal_in    AND
       COD_GRUPO_CIA = cCodGrupoCia_in ;

   IF vCantUsersLogin>0 THEN
     RAISE_APPLICATION_ERROR(-20014,'El Login especificado ya existe');
   END IF;

    v_nNeoCod:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_SEC_USU);
  v_cNeoCod:= Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNeoCod , 3, '0', POS_INICIO);

                INSERT INTO PBL_USU_LOCAL (COD_GRUPO_CIA,
                                           COD_LOCAL,
                               SEC_USU_LOCAL,
                               COD_TRAB,
                               COD_CIA,
                               NOM_USU,
                               APE_PAT,
                               APE_MAT,
                               LOGIN_USU,
                               CLAVE_USU,
                               TELEF_USU,
                               DIRECC_USU,
                               FEC_NAC,
                               EST_USU,
                               FEC_CREA_USU_LOCAL,
                               USU_CREA_USU_LOCAL,
                               FEC_MOD_USU_LOCAL,
                               USU_MOD_USU_LOCAL,
                               DNI_USU,                               --AÑADIDO
                               COD_TRAB_RRHH)
                VALUES(cCodGrupoCia_in,
                       cCodLocal_in,
                     v_cNeoCod ,
                     cCodTrab_in,
                     cCodCia_in,
                     cNomUsu_in,
                     cApePat_in,
                     cApeMat_in,
                     cLoginUsu_in,
                     cClaveUsu_in,
                     cTelefUsu_in,
                     cDireccUsu_in,
                     TO_DATE(cFecNac_in,'dd/MM/yyyy'),
                     ESTADO_ACTIVO,
                      SYSDATE,
                     cCodUsu_in,
                     NULL,
                     NULL,
                     CDNI_IN,
                     cCodTrabRRHH);

END;

 /* ******************************************************************************************************* */

PROCEDURE USU_MODIFICA_USUARIO(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                                cSecUsuLocal_in IN CHAR,
                               cCodTrab_in     IN CHAR,
                                cNomUsu_in      IN CHAR,
                                cApePat_in      IN CHAR,
                                cApeMat_in      IN CHAR,
                                cLoginUsu_in    IN CHAR,
                                cClaveUsu_in    IN CHAR,
                                cTelefUsu_in    IN CHAR,
                                cDireccUsu_in   IN CHAR,
                                cFecNac_in      IN CHAR,
                                cCodUsu_in      IN CHAR,
                               cDni_in         IN CHAR)
IS
BEGIN

               UPDATE PBL_USU_LOCAL SET FEC_MOD_USU_LOCAL = SYSDATE, USU_MOD_USU_LOCAL = cCodUsu_in,
                      COD_TRAB =  cCodTrab_in,
                      NOM_USU    = cNomUsu_in,
                      APE_PAT    = cApePat_in,
                      APE_MAT    = cApeMat_in,
                      LOGIN_USU  = cLoginUsu_in,
                      CLAVE_USU  = cClaveUsu_in,
                      TELEF_USU  = cTelefUsu_in,
                      DIRECC_USU = cDireccUsu_in,
                      FEC_NAC    = TO_DATE(cFecNac_in,'dd/MM/yyyy'),
                      DNI_USU    = CDNI_IN
               WHERE
                     COD_GRUPO_CIA = cCodGrupoCia_in AND
                    COD_LOCAL     = cCodLocal_in    AND
                    SEC_USU_LOCAL = cSecUsuLocal_in;
              END;


  /* ******************************************************************************************************* */

/*PROCEDURE USU_CAMBIA_ESTADO_USU(cCodGrupoCia_in IN CHAR,
                    cCodLocal_in    IN CHAR,
                  cSecUsuLocal_in IN CHAR,
                cCodUsu_in      IN CHAR)
IS
v_est CHAR(1);
vCantCajasAsig NUMBER;
BEGIN
      SELECT EST_USU
    INTO   v_est
    FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
             COD_LOCAL     = cCodLocal_in    AND
             SEC_USU_LOCAL = cSecUsuLocal_in;

       IF   v_est = ESTADO_ACTIVO THEN
            v_est:= ESTADO_INACTIVO;
       ELSE v_est:= ESTADO_ACTIVO;
       END IF;

     IF v_est=ESTADO_INACTIVO THEN
      BEGIN
      SELECT TO_NUMBER(COUNT(*)) INTO vCantCajasAsig
      FROM VTA_CAJA_PAGO
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
                COD_LOCAL     = cCodLocal_in    AND
        SEC_USU_LOCAL = cSecUsuLocal_in;

        IF vCantCajasAsig>0 THEN
           RAISE_APPLICATION_ERROR(-20015,'No se puede inactivar a un usuario que este asignado a una caja.');
        END IF;
    END;
     END IF;

         UPDATE PBL_USU_LOCAL SET    FEC_MOD_USU_LOCAL = SYSDATE,USU_MOD_USU_LOCAL = cCodUsu_in,
         EST_USU = v_est
         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
                COD_LOCAL     = cCodLocal_in    AND
                SEC_USU_LOCAL = cSecUsuLocal_in;
END;*/

PROCEDURE USU_CAMBIA_ESTADO_USU(cCodGrupoCia_in IN CHAR,
                    cCodLocal_in    IN CHAR,
                    cSecUsuLocal_in IN CHAR,
                    cCodUsu_in      IN CHAR)
IS
v_est CHAR(1);
vCantCajasAsig NUMBER;
BEGIN
      SELECT EST_USU
    INTO   v_est
    FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
             COD_LOCAL     = cCodLocal_in    AND
             SEC_USU_LOCAL = cSecUsuLocal_in;

       IF   v_est = ESTADO_ACTIVO THEN
            v_est:= ESTADO_INACTIVO;
       ELSE v_est:= ESTADO_ACTIVO;
       END IF;

     IF v_est=ESTADO_INACTIVO THEN
      BEGIN
      SELECT TO_NUMBER(COUNT(*)) INTO vCantCajasAsig
      FROM VTA_CAJA_PAGO
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
            COD_LOCAL     = cCodLocal_in    AND
        SEC_USU_LOCAL = cSecUsuLocal_in     AND
        Est_Caja_Pago =ESTADO_ACTIVO ;  --Añadido  --dubilluz 12.07.2007


        IF vCantCajasAsig>0 THEN
           RAISE_APPLICATION_ERROR(-20015,'No se puede inactivar a un usuario que este asignado a una caja.');
        END IF;
    END;
     END IF;

         UPDATE PBL_USU_LOCAL SET    FEC_MOD_USU_LOCAL = SYSDATE,USU_MOD_USU_LOCAL = cCodUsu_in,
         EST_USU = v_est
         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
                COD_LOCAL     = cCodLocal_in    AND
                SEC_USU_LOCAL = cSecUsuLocal_in;
END;

   /* ******************************************************************************************************* */

  FUNCTION USU_LISTA_ROLES_USUARIO(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR,
                   cSecUsuLocal_in IN CHAR)
RETURN FarmaCursor
IS
    mifcur FarmaCursor;
  BEGIN
    OPEN mifcur FOR
  SELECT PBL_ROL.COD_ROL  || 'Ã' ||
         PBL_ROL.DESC_ROL
    FROM   PBL_ROL,
       PBL_ROL_USU
    WHERE
       PBL_ROL_USU.COD_GRUPO_CIA = cCodGrupoCia_in     AND
         PBL_ROL_USU.COD_LOCAL     = cCodLocal_in        AND
         PBL_ROL_USU.SEC_USU_LOCAL = cSecUsuLocal_in      AND
       PBL_ROL.COD_ROL           = PBL_ROL_USU.COD_ROL ;
    RETURN mifcur;
  END;

   /* ******************************************************************************************************* */

  FUNCTION USU_LISTA_ROLES
  RETURN FarmaCursor
  IS
    curLab FarmaCursor;
  BEGIN
    OPEN curLab FOR
      SELECT COD_ROL   || 'Ã' ||
         DESC_ROL
      FROM   PBL_ROL
      WHERE  EST_ROL = ESTADO_ACTIVO   AND
         TIP_ROL = ROLES_PTOVENTA;
    RETURN curLab;
  END;

  /* ******************************************************************************************************* */

  PROCEDURE USU_LIMPIA_ROLES_USUARIO(cCodGrupoCia_in IN CHAR,
                       cCodLocal_in    IN CHAR,
                     cSecUsuLocal_in IN CHAR)
  IS
     BEGIN
     -- 2015-02-24 JOLIVA: SE USA AHORA NUEVA TABLA DE MANTENIMIENTO
      DELETE FROM AUX_PBL_ROL_USU
           WHERE  COD_GRUPO_CIA = cCodGrupoCia_in   AND
                  COD_LOCAL     = cCodLocal_in      AND
                   SEC_USU_LOCAL = cSecUsuLocal_in   AND
                  COD_ROL  <> TIPO_ROL_AUDITOR;

END;

 /* ******************************************************************************************************* */

PROCEDURE USU_AGREGA_ROL_USUARIO(cCodGrupoCia_in IN CHAR,
                       cCodLocal_in    IN CHAR,
                  cSecUsuLocal_in IN CHAR,
                  cCodRol_in      IN CHAR,
                  cUsuCreaRol_in  IN CHAR)
IS
 BEGIN
     -- 2015-02-24 JOLIVA: SE USA AHORA NUEVA TABLA DE MANTENIMIENTO 
    INSERT INTO AUX_PBL_ROL_USU (COD_ROL,
                  COD_GRUPO_CIA,
               COD_LOCAL,
               SEC_USU_LOCAL,
               EST_ROL_USU,
               FEC_CREA_ROL_USU,
               USU_CREA_ROL_USU,
               FEC_MOD_ROL_USU,
               USU_MOD_ROL_USU)
       VALUES(       cCodRol_in,
                   cCodGrupoCia_in,
                 cCodLocal_in,
                  cSecUsuLocal_in,
                  ESTADO_ACTIVO,
                  SYSDATE,
                  cUsuCreaRol_in,
                  NULL,
                  NULL);
 END;

  /* ******************************************************************************************************* */
  FUNCTION USU_OBTIENE_DATA_TRABAJADOR(cCodCia_in  IN CHAR,
                          cCodTrab_in IN CHAR)
  RETURN FarmaCursor
  IS
    curLab     FarmaCursor;
    v_cNeoCod  CHAR(6);
    v_nNeoCod  NUMBER;
  BEGIN
    v_nNeoCod:= TO_NUMBER(cCodTrab_in);
    v_cNeoCod:= Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNeoCod , 6, '0', POS_INICIO);
    --29/11/2007  DUBILLUZ SE AGREGO EL DNI
    OPEN curLab FOR
    SELECT COD_TRAB          || 'Ã' ||
           NVL(APE_PAT_TRAB,' ') || 'Ã' ||
           NVL(APE_MAT_TRAB,' ') || 'Ã' ||
           NVL(NOM_TRAB,' ')     || 'Ã' ||
           NVL(TELEF_TRAB,' ')   || 'Ã' ||
           NVL(DIRECC_TRAB,' ')  || 'Ã' ||
           NVL(TO_CHAR(FEC_NAC_TRAB,'dd/MM/yyyy'),' ')|| 'Ã' ||
           NVL(C.NUM_DOC_IDEN,' ')
    FROM CE_MAE_TRAB C
    WHERE  /*COD_CIA  = cCodCia_in
           --AND COD_TRAB = v_cNeoCod
           AND*/ C.COD_TRAB_RRHH = v_cNeoCod
    ;

    RETURN curLab;
  END;
  /* ******************************************************************************************************* */

    FUNCTION USU_EXISTE_DUPLICADO(cCodGrupoCia_in  IN CHAR,
                   cCodLocal_in     IN CHAR,
                   cCodTrab_in      IN CHAR)
    RETURN VARCHAR2
    IS
        v_cNeoCod  CHAR(6);
        v_nNeoCod  NUMBER;
        v_rpta     VARCHAR(1);
        v_cant     NUMBER;
    BEGIN
      v_nNeoCod:= TO_NUMBER(cCodTrab_in);
      v_cNeoCod:= Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNeoCod , 6, '0', POS_INICIO);
      v_rpta   := '0';

      SELECT COUNT(*)
       INTO   v_cant
      FROM   PBL_USU_LOCAL C
      WHERE  --COD_TRAB       = v_cNeoCod
             COD_TRAB_RRHH = v_cNeoCod
      AND    COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in;

          IF v_cant = 0 THEN
             v_rpta :='0';
          ELSE
             v_rpta :='1';
          END IF;
       RETURN v_rpta;
    END;

    /* ******************************************************************************************************* */
    FUNCTION USU_EXISTE_LOGIN_DUPLICADO(cCodGrupoCia_in  IN CHAR,
                     cCodLocal_in    IN CHAR,
                           cLogin_in      IN CHAR)
RETURN VARCHAR2
IS
    v_rpta     VARCHAR(1);
    v_cant     NUMBER;
  BEGIN

    v_rpta   := '0';

    SELECT COUNT(*)
  INTO   v_cant
  FROM   PBL_USU_LOCAL
  WHERE  LOGIN_USU    = cLogin_in       AND
       COD_GRUPO_CIA = cCodGrupoCia_in AND
       COD_LOCAL   = cCodLocal_in;

        IF v_cant = 0 THEN
           v_rpta :='0';
        ELSE
           v_rpta :='1';
        END IF;
     RETURN v_rpta;
  END;

 /* ******************************************************************************************************* */


  --SE AGREGO LA COLUMNA DE CODIGO DE RECURSOS HUMANOS
  --27/11/2007 DUBILLUZ MODIFICACION
  FUNCTION USU_LISTA_TRABAJADORES(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
    curTrab FarmaCursor;
  BEGIN

    OPEN curTrab FOR
         SELECT COD_TRAB             || 'Ã' ||
                nvl(Cod_Trab_Rrhh,' ') || 'Ã' ||
                APE_PAT_TRAB         || 'Ã' ||
                NVL(APE_MAT_TRAB,' ')|| 'Ã' ||
                NOM_TRAB             || 'Ã' ||
                NVL(NUM_DOC_IDEN,' ')|| 'Ã' ||
                EST_TRAB             || 'Ã' ||
                DIRECC_TRAB          || 'Ã' ||
                TELEF_TRAB           || 'Ã' ||
                NVL(TO_CHAR(FEC_NAC_TRAB,'dd/mm/YYYY'),' ')         || 'Ã' ||
                COD_CIA
          FROM  CE_MAE_TRAB m
          WHERE COD_CIA=cCodGrupoCia_in -- KMONCADA 19.09.2014 SOLO TRABAJADORES SEGUN CIA
      ;
    RETURN curTrab;
  END;

  /********************************************************************************/
  -- Busca si el usuario tiene aluna caja asignada
  --Fecha       Usuario    Comentario
  --11/07/2007  DUBILLUZ    Creación
  FUNCTION USU_EXISTE_CAJA(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,
                           cSec_usu_local_in IN VARCHAR2)
  RETURN VARCHAR2
  IS
  cCant NUMBER;
  vNumCaja VARCHAR2(5);
  BEGIN
   SELECT count(*) INTO cCant
   FROM   VTA_CAJA_PAGO
   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in            AND
          COD_LOCAL     = cCodLocal_in               AND
          SEC_USU_LOCAL = NVL(cSec_usu_local_in,'_');

  if cCant > 0 then
   SELECT NUM_CAJA_PAGO INTO vNumCaja
   FROM   VTA_CAJA_PAGO
   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in            AND
          COD_LOCAL     = cCodLocal_in               AND
          SEC_USU_LOCAL = NVL(cSec_usu_local_in,'_');
   return vNumCaja ;
   else
         return 'N';
   end if;

  END USU_EXISTE_CAJA;

  /********************************************************************************/
  -- Busca si existe usuarios sin asignar caja
  --Fecha       Usuario    Comentario
  --12/07/2007  DUBILLUZ    Creación
  FUNCTION USU_EXISTE_USU_SIN_CAJA(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR)
  RETURN VARCHAR2
  IS
  cCant NUMBER;
  vRetorno VARCHAR2(5);

  BEGIN
    SELECT COUNT(*)
    INTO   cCant
    FROM   PBL_USU_LOCAL
    WHERE
        COD_GRUPO_CIA = cCodGrupoCia_in  AND
        COD_LOCAL     = cCodLocal_in     AND
        EST_USU       = ESTADO_ACTIVO    AND
        COD_GRUPO_CIA || COD_LOCAL ||
        SEC_USU_LOCAL IN (
                          SELECT COD_GRUPO_CIA||COD_LOCAL||SEC_USU_LOCAL
                           FROM   PBL_ROL_USU
                          WHERE  COD_ROL = TIPO_ROL_CAJERO ) AND
        COD_GRUPO_CIA || COD_LOCAL ||
        SEC_USU_LOCAL NOT IN (
                              SELECT COD_GRUPO_CIA || COD_LOCAL ||  SEC_USU_LOCAL
                              FROM   VTA_CAJA_PAGO );

    if (cCant > 0) then
      vRetorno:='TRUE';
    else
      vRetorno:='FALSE';
    end if;

   return vRetorno;

  END USU_EXISTE_USU_SIN_CAJA;

--Valida Rango de dias al modificar el codigo de trabajador para el usuario
  --Fecha       Usuario    Comentario
  --06/07/2007  JCORTEZ    Creación
  FUNCTION USU_VALIDA_ACTULIZACION(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                    vLogin IN VARCHAR2)

  RETURN VARCHAR2
  IS
  cCantDias NUMBER;
  cMaxDias NUMBER;
  vRetorno VARCHAR2(5);

  BEGIN
    --SELECT TO_DATE(SYSDATE,'dd/MM/YYYY')-TO_DATE(A.FEC_CREA_USU_LOCAL,'dd/MM/YYYY') INTO cCantDias
   --FECMOD:16/04/2015-CHUANES
   SELECT TRUNC(SYSDATE)-TRUNC(A.FEC_CREA_USU_LOCAL) INTO cCantDias

    FROM   PBL_USU_LOCAL A
    WHERE a.login_usu=vLogin
    AND   A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   A.COD_LOCAL     = cCodLocal_in;

    SELECT TO_NUMBER(B.LLAVE_TAB_GRAL) INTO cMaxDias
    FROM PBL_TAB_GRAL B WHERE B.ID_TAB_GRAL='75';

    if(cCantDias>cMaxDias) then
    vRetorno:='FALSE';
    else
    vRetorno:='TRUE';
    end if;
    return vRetorno;

  END USU_VALIDA_ACTULIZACION;

  /********************************************************************************/
  FUNCTION USU_GET_IND_VALIDA(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR )
  RETURN CHAR
  IS
    v_indicador CHAR(1);
  BEGIN

    SELECT IND_VALIDA_MANT_USU
      INTO   v_indicador
    FROM PBL_LOCAL L
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND L.COD_LOCAL = cCodLocal_in;

    RETURN v_indicador;
  END;
  /********************************************************************************/
  FUNCTION USU_EXISTE_USUARIO(cCodGrupoCia_in   IN CHAR,
                              cCodTrabRR_HH_in IN VARCHAR2)
  RETURN CHAR
  IS
  v_valor char(1):= 'N';
  nCant   number;
  BEGIN

  SELECT COUNT(*)
  INTO   nCant
  FROM   CE_MAE_TRAB L
  WHERE  /*L.COD_CIA       = cCodGrupoCia_in
  AND    */L.COD_TRAB_RRHH = cCodTrabRR_HH_in;

  IF nCant > 0 THEN
     v_valor := 'S';
  END IF;

  return v_valor;

  END;
  /***************************************************************************/
  FUNCTION GET_MENSAJE_USU(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cSecUsu_in IN CHAR)
  RETURN FarmaCursor
  IS
    curMensaje FarmaCursor;
  BEGIN
    OPEN curMensaje FOR
    SELECT M.MENSAJE
    FROM PBL_USU_LOCAL U,
         PBL_USU_MENSAJE M
    WHERE U.COD_GRUPO_CIA = cCodGrupoCia_in
          AND U.COD_LOCAL = cCodLocal_in
          AND U.SEC_USU_LOCAL = cSecUsu_in
          AND U.COD_TRAB_RRHH = M.COD_TRAB_RRHH
          AND TRUNC(SYSDATE) BETWEEN M.FEC_INI AND M.FEC_FIN
          AND M.REPITE > M.CANT_VECES;
    RETURN curMensaje;
  END;
  /***************************************************************************/
  PROCEDURE ACT_MENSAJE_USU(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cSecUsu_in IN CHAR,vIdUsu_in IN VARCHAR2)
  IS
    PRAGMA AUTONOMOUS_TRANSACTION;
  BEGIN
    UPDATE PBL_USU_MENSAJE
    SET CANT_VECES = CANT_VECES+1,
        USU_MOD = vIdUsu_in,
        FEC_MOD = SYSDATE
    WHERE COD_TRAB_RRHH = (SELECT COD_TRAB_RRHH FROM PBL_USU_LOCAL
                              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND COD_LOCAL = cCodLocal_in
                                    AND SEC_USU_LOCAL = cSecUsu_in);
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
  END;
  /***************************************************************************/

  FUNCTION OBTENER_CLAVE(cCodGrupoCia_in  IN CHAR,
                          cCodLocal_in      IN CHAR,
                          cSecUsu_in        IN CHAR)
    RETURN VARCHAR2
    IS
    vClave   VARCHAR2(20);
    BEGIN
    SELECT   CLAVE_USU INTO vclave
      FROM   PBL_USU_LOCAL
      WHERE  COD_LOCAL     = cCodLocal_in
      AND    COD_GRUPO_CIA = cCodGrupoCia_in
      AND    SEC_USU_LOCAL = cSecUsu_in;

    RETURN vclave;
    END;

  /************************asolis*****************************************************/
  FUNCTION USU_LISTA_TRABAJADORES_LOCAL(cCodGrupoCia_in    IN CHAR,
                                        cCodLocal_in       IN CHAR)

   RETURN FarmaCursor
  IS
    curLab FarmaCursor;
  BEGIN
    OPEN curLab FOR
    SELECT  X.SEC_USU_LOCAL || 'Ã' ||
            nvl(X.Cod_Trab_Rrhh,' ') || 'Ã' ||
            X.APE_PAT || 'Ã' ||
            NVL(X.APE_MAT,' ') || 'Ã' ||
            X.NOM_USU || 'Ã' ||
            nvl(DNI_USU,' ')  || 'Ã' ||
            X.EST_USU  || 'Ã' ||
            NVL(X.CARNE_SANIDAD,' ') || 'Ã' ||
            NVL(TO_CHAR(X.FEC_VENCIMIENTO,'dd/mm/YYYY'),' ') || 'Ã' ||
            TELEF_USU || 'Ã' ||
            COD_GRUPO_CIA || 'Ã' ||
            COD_LOCAL
    FROM   PBL_USU_LOCAL X
    WHERE  X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND    X.COD_LOCAL=cCodLocal_in
    AND    X.EST_USU ='A'
    AND    X.SEC_USU_LOCAL BETWEEN '001' AND '899';
    RETURN curLab;

  END;
  /****************************asolis************************************************/

FUNCTION USU_OBTIENE_DATA_TRAB_LOCAL(cCodCia_in  IN CHAR,
                                    cCodGrupoCia_in    IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                      cCodTrab_in IN CHAR)
    RETURN FarmaCursor
  IS
    curLab     FarmaCursor;
    v_cNeoCod  CHAR(6);
    v_nNeoCod  NUMBER;
  BEGIN
    v_nNeoCod:= TO_NUMBER(cCodTrab_in);
    v_cNeoCod:= Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNeoCod , 6, '0', POS_INICIO);

    OPEN curLab FOR

      SELECT nvl(Cod_Trab_Rrhh,' ') || 'Ã' ||
             NVL(APE_PAT,' ') || 'Ã' ||
             NVL(APE_MAT,' ') || 'Ã' ||
             NVL(NOM_USU,' ') || 'Ã' ||
             NVL(TELEF_USU,' ') || 'Ã' ||
             NVL(DIRECC_USU,' ') || 'Ã' ||
             NVL(TO_CHAR(FEC_NAC,'dd/MM/yyyy'),' ')  || 'Ã' ||
             nvl(DNI_USU,' ')  || 'Ã' ||
             NVL(CARNE_SANIDAD,' ') || 'Ã' ||
             FEC_EXPEDICION  || 'Ã' ||
             FEC_VENCIMIENTO
      FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in
      AND    Cod_Trab_Rrhh = v_cNeoCod;

    RETURN curLab;

  END;

/***************************asolis*******************************************************/

FUNCTION USU_EXISTE_USUARIO_CARNE(cCodGrupoCia_in  IN CHAR,
                   cCodLocal_in     IN CHAR,
                   cCodTrab_in      IN CHAR)
    RETURN VARCHAR2
    IS
        v_cNeoCod  CHAR(6);
        v_nNeoCod  NUMBER;
        v_rpta     VARCHAR(1);
        v_cant     NUMBER;
    BEGIN
      v_nNeoCod:= TO_NUMBER(cCodTrab_in);
      v_cNeoCod:= Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNeoCod , 6, '0', POS_INICIO);
      v_rpta   := '0';

      SELECT COUNT(*)
       INTO   v_cant
      FROM   PBL_USU_LOCAL C
      WHERE  --COD_TRAB       = v_cNeoCod
             COD_TRAB_RRHH = v_cNeoCod
      AND    COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in
      AND    CARNE_SANIDAD IS NOT NULL;

          IF v_cant = 0 THEN
             v_rpta :='0';
          ELSE
             v_rpta :='1';
          END IF;
       RETURN v_rpta;
    END;

/******************************asolis********************************************************/


PROCEDURE USU_INGRESA_CARNE_USUARIO(cCodGrupoCia_in IN CHAR,
                              cCodCia_in         IN CHAR,
                              cCodLocal_in        IN CHAR,
                              cCodTrab_in        IN CHAR,
                              cCodTrabRRHH_in    IN CHAR,
                              cNumCarne_in       IN CHAR,
                              cFechaExpe_in      IN CHAR,
                              cFechaVenc_in      IN CHAR,
                              cUsuMod             IN CHAR DEFAULT NULL
                               )

IS

 vFechaExp DATE  ;
 vFechaVenc DATE ;
 bad_date EXCEPTION;



 PRAGMA EXCEPTION_INIT (bad_date, -01843);


 BEGIN

   vFechaExp := TO_DATE(cFechaExpe_in,'dd/MM/yyyy');
   vFechaVenc := TO_DATE(cFechaVenc_in,'dd/MM/yyyy');

  IF vFechaExp>TRUNC(SYSDATE) THEN
      RAISE bad_date;
   END IF;

  UPDATE  PBL_USU_LOCAL
  SET CARNE_SANIDAD = cNumCarne_in,
      FEC_EXPEDICION =   TO_DATE(cFechaExpe_in,'dd/MM/yyyy'),
      FEC_VENCIMIENTO =  TO_DATE(cFechaVenc_in,'dd/MM/yyyy'),
      FEC_MOD_USU_LOCAL = SYSDATE,--FECMOD:16/04/2015--CHUANES
      USU_MOD_USU_LOCAL=cUsuMod
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_LOCAL = cCodLocal_in
    AND sec_usu_local  = cCodTrab_in
    AND COD_TRAB_RRHH = cCodTrabRRHH_in ;

     /* commit;*/

 EXCEPTION
   WHEN bad_date THEN
  RAISE_APPLICATION_ERROR(-20001,'FECHA EXPEDICION INVALIDA');
 /*  rollback;*/


 END;

 /***************************asolis***********************************************************/


PROCEDURE USU_ALERTA_INSR_CARNE_USUARIO(cCodGrupoCia_in IN CHAR,
                              cCodCia_in         IN CHAR,
                              cCodLocal_in        IN CHAR,
                              cCodTrab_in        IN CHAR,
                              cCodTrabRRHH_in    IN CHAR,
                              cNumCarne_in       IN CHAR,
                              cFechaExpe_in      IN CHAR,
                              cFechaVenc_in      IN CHAR

                              )

IS


  vNOM_USU VARCHAR2(50);
  vDNI_USU  CHAR(8);





  vMensajeTitulo  varchar (500);
  vMensajeInfoUsuario   varchar2 (2000);
  vMensajeFila   varchar2 (5200);
  vMensajeFinal  varchar2 (8000);
  cFechaMensaje  varchar (150);
   v_vDescLocal varchar (150);

BEGIN

    ---OBTENER LOS DATOS ACTUALIZADOS DEL TRABAJADOR

  SELECT  NOM_USU || ' ' || APE_PAT  || ' ' ||  APE_MAT , DNI_USU
          INTO  vNOM_USU , vDNI_USU

      FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA =  cCodGrupoCia_in
      AND    COD_LOCAL  = cCodLocal_in
      AND    COD_TRAB_RRHH = cCodTrabRRHH_in

      AND    EST_USU ='A'
      AND    CARNE_SANIDAD = cNumCarne_in
      AND    SEC_USU_LOCAL  = cCodTrab_in ;

      ---envia correo alerta cuando ingresa el nro carné sanidad del trabajador.

            --DESCRIPCION DE LOCAL
                        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
                        INTO   v_vDescLocal
                        FROM   PBL_LOCAL
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL = cCodLocal_in;


             vMensajeInfoUsuario:= '<TR><TD align="center">' || cCodTrabRRHH_in
                           || '</TD>' ||'<TD align="center">' || vNOM_USU || '</TD>'
                           || '<TD align="center">' ||  vDNI_USU  || '</TD>'
                           || '<TD align="center">' ||  cNumCarne_in  || '</TD>'
                           || '<TD align="center">' ||  cFechaExpe_in  || '</TD>'
                           || '<TD align="center">' ||  cFechaVenc_in  || '</TD></TR>';

             vMensajeFila :=   vMensajeInfoUsuario;

              vMensajeTitulo :='<BR>  <BR>' || '<TABLE  BORDER=1 align="center">' ||
                                              '<TR><TD align="center">' || 'Cod Trab Rrhh' || '</TD>'||
                                              '<TD align="center">'||' Nombres' || '</TD>' ||
                                              '<TD align="center">' ||'Dni' || '</TD>' ||
                                              '<TD align="center">' ||'Nro. Carné Sanidad' || '</TD>'||
                                              '<TD align="center">' ||'Fecha Expedición' || '</TD>'||
                                              '<TD align="center">' ||'Fecha Vencimiento' || '</TD></TR>';
              vMensajeFinal := vMensajeTitulo || vMensajeFila  ||'</TABLE>' ;
              cFechaMensaje := TO_CHAR (TRUNC (SYSDATE), 'DD/MM/YYYY ');

                    ENVIA_CORREO_INF_MG(cCodGrupoCia_in,cCodLocal_in,
                                                'ALERTA REGISTRO DE CARNÉ SANIDAD DEL TRABAJADOR:',
                                                'ALERTA',
                                                ' SE REGISTRÓ EL CARNÉ SANIDAD  EN LOCAL:'|| '   '|| v_vDescLocal || '  ' || '<B> Fecha :</B>' || cFechaMensaje ||'<BR>' ||
                                                '<BR>DATOS DEL TRABAJADOR<BR>' ||
                                                '</B>'||
                                                '<BR> <I>VERIFIQUE:</I> <BR>'|| vMensajeFinal || '<B>');


END;




/********************************asolis********************************************************/


  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL)
  AS


   --Corregido por Arturo Escate 01/08/2012
   --ReceiverAddress      VARCHAR2(30):='';
   ReceiverAddress        pbl_tab_gral.llave_tab_gral%type;

   --CCReceiverAddress VARCHAR2(120);
   CCReceiverAddress      pbl_local.mail_local%type;


    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN

     select  llave_tab_gral
     into    ReceiverAddress
     from  pbl_tab_gral
     where id_tab_gral=265;

----------------
   select mail_local
   into   CCReceiverAddress
   from   pbl_local
   where  cod_grupo_cia = cCodGrupoCia_in
   and    cod_local = cCodLocal_in;
-----------------------------------------------------
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             NVL(cCopiaCorreo,CCReceiverAddress),
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;

/************************************asolis***********************************************/



  PROCEDURE ENVIA_CORREO_INF_MAIL_LOCAL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL)
  AS

   --Corregido por Arturo Escate 01/08/212
   --ReceiverAddress VARCHAR2(30):='';
   ReceiverAddress     pbl_local.mail_local%type;
   CCReceiverAddress   VARCHAR2(120);

    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN

-----BORRAR CUANDO SE USE EL JOB------
 /*    select  llave_tab_gral
     into   ReceiverAddress
     from  pbl_tab_gral
     where id_tab_gral=253;--asolis
*/
----------------------------------------

   select mail_local
   into   ReceiverAddress
   from   pbl_local
   where  cod_grupo_cia = cCodGrupoCia_in
   and    cod_local = cCodLocal_in;
----------------------------------------------------------------
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             NVL(cCopiaCorreo,CCReceiverAddress),
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;

/**********************************asolis*************************************************/



  PROCEDURE ENVIA_CORREO_INF_MG(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL)
  AS

   --Cambiado por Arturo Escate 01/08/2012
   --ReceiverAddress VARCHAR2(30);
   ReceiverAddress pbl_tab_gral.llave_tab_gral%type;

   CCReceiverAddress VARCHAR2(120);

    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN


     select  llave_tab_gral
     into   ReceiverAddress
     from  pbl_tab_gral
     where id_tab_gral=265;


    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             NVL(cCopiaCorreo,CCReceiverAddress),
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;
 /***********************************asolis**********************************************/
 --Usado en Job
 PROCEDURE USU_ALERTA_TRAB_SIN_CARNE(cCodCia_in       IN CHAR,
                                    cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR)

  IS

  vMensajeTitulo  varchar (500);
  vMensajeInfoUsuario   varchar2 (900);
  vMensajeFila   varchar2 (5200);
  vMensajeFinal  varchar2 (8000);
  vResult_EXISTE_USU_SIN_CARNE char(1);
  cFechaMensaje  varchar (150);
   v_vDescLocal varchar (150);

  CURSOR  curValUsu  IS

     SELECT SEC_USU_LOCAL ,
             nvl(Cod_Trab_Rrhh,' ') Cod_Trab_Rrhh,
             APE_PAT ,
             NVL(APE_MAT,' ') APE_MAT,
             NOM_USU ,
             nvl(DNI_USU,' ') DNI_USU ,
             COD_GRUPO_CIA ,
             COD_LOCAL
      FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in
      AND    EST_USU ='A'
      AND    CARNE_SANIDAD IS  NULL
      AND    SEC_USU_LOCAL BETWEEN '001' AND '899'
      order by Cod_Trab_Rrhh;

      v_rcurValUsu curValUsu%ROWTYPE;

      BEGIN

       FOR v_rcurValUsu IN curValUsu
         LOOP
         EXIT WHEN curValUsu%NOTFOUND;
             IF  v_rcurValUsu.Sec_Usu_Local IS NOT NULL THEN
                 DBMS_OUTPUT.PUT_LINE('si hay datos');

                 vMensajeInfoUsuario:=  '<TR><TD align="center">' || v_rcurValUsu.Cod_Trab_Rrhh || '</TD>' ||'<TD align="center">' || v_rcurValUsu.Nom_Usu || ' ' ||  v_rcurValUsu.Ape_Pat || ' ' || v_rcurValUsu.Ape_Mat || '</TD>' || '<TD align="center">' ||  v_rcurValUsu.Dni_Usu  || '</TD></TR>';
                 vMensajeFila := vMensajeFila || vMensajeInfoUsuario;

             END IF;

             vResult_EXISTE_USU_SIN_CARNE :='S';

         END LOOP;


             IF (vResult_EXISTE_USU_SIN_CARNE = 'S') then

             --DESCRIPCION DE LOCAL
                        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
                        INTO   v_vDescLocal
                        FROM   PBL_LOCAL
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL = cCodLocal_in;

              vMensajeTitulo :='<BR>  <BR>' || '<TABLE  BORDER=1 align="center">' || '<TR><TD  >' || 'Cod Trab Rrhh' || '</TD>' || '<TD align="center">' ||' Nombres' || '</TD>' || '<TD align="center">' ||'Dni' || '</TD></TR>';
              vMensajeFinal := vMensajeTitulo || vMensajeFila  ||'</TABLE>' ;
              cFechaMensaje := TO_CHAR (TRUNC (SYSDATE), 'DD/MM/YYYY ');

                    INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                                'ALERTA USUARIO(S) SIN CARNÉ SANIDAD:',
                                                'ALERTA',
                                                ' EXISTE(N) USUARIO(S) SIN CARNÉ SANIDAD EN LOCAL:'|| '   '|| v_vDescLocal || '       ' || '<B> Fecha :</B>' || cFechaMensaje ||'<BR>' ||
                                                '<BR>Datos del Trabajador<BR>' ||
                                                '</B>'||
                                                '<BR> <I>Verifique:</I> <BR>'|| vMensajeFinal || '<B>');

             END IF;

       END;

 /****************************************asolis*****************************************/
 --Usado en Job
 PROCEDURE USU_ALERTA_CARNE_P_VENCER_ADL(cCodCia_in    IN CHAR,
                                    cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR)

  IS

  vMensajeTitulo  varchar (500);
  vMensajeInfoUsuario   varchar2 (900);
  vMensajeFila   varchar2 (5200);
  vMensajeFinal  varchar2 (8000);
  vResult_EXISTE_USU_SIN_CARNE char(1):='N';
  cFechaMensaje  varchar (150);
   v_vDescLocal varchar (150);

  CURSOR  curValUsu  IS

     SELECT SEC_USU_LOCAL ,
             nvl(Cod_Trab_Rrhh,' ') Cod_Trab_Rrhh,
             APE_PAT ,
             NVL(APE_MAT,' ') APE_MAT,
             NOM_USU ,
             nvl(DNI_USU,' ') DNI_USU ,
             CARNE_SANIDAD,
             FEC_EXPEDICION,
             FEC_VENCIMIENTO,
             COD_GRUPO_CIA ,
             COD_LOCAL
      FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in
      AND    EST_USU ='A'
      AND    CARNE_SANIDAD IS NOT  NULL
      AND    ((TRUNC((FEC_VENCIMIENTO)-30)<=TRUNC(SYSDATE) AND TRUNC(FEC_VENCIMIENTO)>=TRUNC(SYSDATE))--POR VENCER
               OR TRUNC(FEC_VENCIMIENTO)<TRUNC(SYSDATE))--VENCIDAS

      AND    SEC_USU_LOCAL BETWEEN '001' AND '899'
      order by Cod_Trab_Rrhh;

      v_rcurValUsu curValUsu%ROWTYPE;

      BEGIN

       FOR v_rcurValUsu IN curValUsu
         LOOP
         EXIT WHEN curValUsu%NOTFOUND;

             IF  v_rcurValUsu.Sec_Usu_Local IS NOT NULL THEN
                    DBMS_OUTPUT.PUT_LINE('si hay datos');

                  IF TRUNC(v_rcurValUsu.Fec_Vencimiento)<=TRUNC(SYSDATE) THEN
                     DBMS_OUTPUT.PUT_LINE('VENCIDO');
                     --mostrará la fecha de vencimiento en rojo
                 vMensajeInfoUsuario:=
                 '<TR><TD align="center">' ||v_rcurValUsu.Cod_Trab_Rrhh || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Nom_Usu || ' ' ||  v_rcurValUsu.Ape_Pat || ' ' || v_rcurValUsu.Ape_Mat || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Dni_Usu  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Carne_Sanidad  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Fec_Expedicion  || '</TD>' ||
                     '<TD align="center"><font color="red"><b>' ||v_rcurValUsu.Fec_Vencimiento  || '</b></font></TD></TR>';
                 vMensajeFila := vMensajeFila || vMensajeInfoUsuario;


                  ELSE

                 vMensajeInfoUsuario:=
                 '<TR><TD align="center">' ||v_rcurValUsu.Cod_Trab_Rrhh || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Nom_Usu || ' ' ||  v_rcurValUsu.Ape_Pat || ' ' || v_rcurValUsu.Ape_Mat || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Dni_Usu  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Carne_Sanidad  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Fec_Expedicion  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Fec_Vencimiento  || '</TD></TR>';
                 vMensajeFila := vMensajeFila || vMensajeInfoUsuario;


                  END IF;

             END IF;

             vResult_EXISTE_USU_SIN_CARNE :='S';

         END LOOP;


             IF (vResult_EXISTE_USU_SIN_CARNE = 'S') then

             --DESCRIPCION DE LOCAL
                        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
                        INTO   v_vDescLocal
                        FROM   PBL_LOCAL
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL = cCodLocal_in;

              vMensajeTitulo :='<BR>  <BR>' || '<TABLE  BORDER=1 align="center">' || '<TR><TD align="center">' || 'Cod Trab Rrhh' || '</TD>' || '<TD align="center">' ||' Nombres' || '</TD>'
                                                                                  || '<TD align="center">' ||'Dni' || '</TD>'
                                                                                  || '<TD align="center">' ||'Nro Carné' || '</TD>'
                                                                                  || '<TD align="center">' ||'Fecha Expedición' || '</TD>'
                                                                                  || '<TD align="center">' ||'Fecha Vencimiento' || '</TD>'
                                                                                  || '</TR>';
              vMensajeFinal := vMensajeTitulo || vMensajeFila  ||'</TABLE>' ;
              cFechaMensaje := TO_CHAR (TRUNC (SYSDATE), 'DD/MM/YYYY ');
              ---INT_ENVIA_CORREO_INFORMACION
                    ENVIA_CORREO_INF_MAIL_LOCAL(cCodGrupoCia_in,cCodLocal_in,
                                                'ALERTA VENCIMIENTO DE CARNÉ SANIDAD:',
                                                'ALERTA',
                                                ' EXISTE(N) CARNÉ(S) DE SANIDAD VENCIDO(S) O POR VENCER  EN EL LOCAL:'|| '   '|| v_vDescLocal || '  ' || '<B> Fecha :</B>' || cFechaMensaje ||'<BR>' ||
                                                '<BR><BR>' ||
                                                '</B>'||
                                                 '<BR>Datos del Trabajador<BR>' ||
                                                '<BR><I>Verifique Fecha de Vencimiento del Carné</I> <BR>'|| vMensajeFinal || '<B>');
             END IF;

       END;




 /*********************************asolis************************************************/
 --Usado en Job
 PROCEDURE USU_ALERTA_CARNE_P_VENCER_MG(cCodCia_in    IN CHAR,
                                    cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR)

  IS

  vMensajeTitulo  varchar (500);
  vMensajeInfoUsuario   varchar2 (900);
  vMensajeFila   varchar2 (5200);
  vMensajeFinal  varchar2 (8000);
  vResult_EXISTE_USU_SIN_CARNE char(1);
  cFechaMensaje  varchar (150);
   v_vDescLocal varchar (150);

  CURSOR  curValUsu  IS

     SELECT SEC_USU_LOCAL ,
             nvl(Cod_Trab_Rrhh,' ') Cod_Trab_Rrhh,
             APE_PAT ,
             NVL(APE_MAT,' ') APE_MAT,
             NOM_USU ,
             nvl(DNI_USU,' ') DNI_USU ,
             CARNE_SANIDAD,
             FEC_EXPEDICION,
             FEC_VENCIMIENTO,
             COD_GRUPO_CIA ,
             COD_LOCAL
      FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND   COD_LOCAL     = cCodLocal_in
      AND   EST_USU ='A'
      AND   CARNE_SANIDAD IS NOT  NULL
      AND   (TRUNC((FEC_VENCIMIENTO)-30)=TRUNC(SYSDATE) OR TRUNC((FEC_VENCIMIENTO)-10)=TRUNC(SYSDATE) --POR VENCER
                  OR TRUNC(FEC_VENCIMIENTO)<TRUNC(SYSDATE)) --vencidas


      AND    SEC_USU_LOCAL BETWEEN '001' AND '899'
      order by Cod_Trab_Rrhh;

      v_rcurValUsu curValUsu%ROWTYPE;

      BEGIN

       FOR v_rcurValUsu IN curValUsu
         LOOP
           EXIT WHEN curValUsu%NOTFOUND;
             IF  v_rcurValUsu.Sec_Usu_Local IS NOT NULL THEN
                 DBMS_OUTPUT.PUT_LINE('si hay datos');

                  IF TRUNC(v_rcurValUsu.Fec_Vencimiento)<=TRUNC(SYSDATE) THEN
                     DBMS_OUTPUT.PUT_LINE('VENCIDO');
                     --mostrará la fecha de vencimiento en rojo
                 vMensajeInfoUsuario:=
                 '<TR><TD align="center">' ||v_rcurValUsu.Cod_Trab_Rrhh || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Nom_Usu || ' ' ||  v_rcurValUsu.Ape_Pat || ' ' || v_rcurValUsu.Ape_Mat || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Dni_Usu  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Carne_Sanidad  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Fec_Expedicion  || '</TD>' ||
                     '<TD align="center"><font color="red"><b>' ||v_rcurValUsu.Fec_Vencimiento  || '</b></font></TD></TR>';
                 vMensajeFila := vMensajeFila || vMensajeInfoUsuario;


                  ELSE
                 DBMS_OUTPUT.PUT_LINE('NO VENCIDO');

                 vMensajeInfoUsuario:=
                 '<TR><TD align="center">' ||v_rcurValUsu.Cod_Trab_Rrhh || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Nom_Usu || ' ' ||  v_rcurValUsu.Ape_Pat || ' ' || v_rcurValUsu.Ape_Mat || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Dni_Usu  || '</TD>' ||
                      '<TD align="center">' ||v_rcurValUsu.Carne_Sanidad  || '</TD>' ||
                       '<TD align="center">' ||v_rcurValUsu.Fec_Expedicion  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Fec_Vencimiento  || '</TD></TR>';
                 vMensajeFila := vMensajeFila || vMensajeInfoUsuario;

                 end if;

             END IF;

             vResult_EXISTE_USU_SIN_CARNE :='S';

         END LOOP;


             IF (vResult_EXISTE_USU_SIN_CARNE = 'S') then

             --DESCRIPCION DE LOCAL
                        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
                        INTO   v_vDescLocal
                        FROM   PBL_LOCAL
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL = cCodLocal_in;

              vMensajeTitulo :='<BR>  <BR>' || '<TABLE  BORDER=1 align="center">' || '<TR><TD align="center">' || 'Cod Trab Rrhh' || '</TD>' || '<TD align="center">' ||' Nombres' || '</TD>'
                                                                                  || '<TD align="center">' ||'Dni' || '</TD>'
                                                                                  || '<TD align="center">' ||'Nro Carné' || '</TD>'
                                                                                  || '<TD align="center">' ||'Fecha Expedición' || '</TD>'
                                                                                  || '<TD align="center">' ||'Fecha Vencimiento' || '</TD>'
                                                                                  || '</TR>';
              vMensajeFinal := vMensajeTitulo || vMensajeFila  ||'</TABLE>' ;
              cFechaMensaje := TO_CHAR (TRUNC (SYSDATE), 'DD/MM/YYYY ');

                    --INT_ENVIA_CORREO_INFORMACION
                    ENVIA_CORREO_INF_MG(cCodGrupoCia_in,cCodLocal_in,
                                                'ALERTA VENCIMIENTO DE CARNÉ SANIDAD:',
                                                'ALERTA',
                                              ' EXISTE(N) CARNÉ(S) DE SANIDAD VENCIDO(S) O POR VENCER  EN EL LOCAL:'|| '   '|| v_vDescLocal || '  ' || '<B> Fecha :</B>' || cFechaMensaje ||'<BR>' ||
                                                '<BR><BR>' ||
                                                '</B>'||
                                                 '<BR>Datos del Trabajador<BR>' ||
                                                '<BR><I>Verifique Fecha de Vencimiento del Carné</I> <BR>'|| vMensajeFinal || '<B>');

             END IF;

       END;
/*-----------------------------------------asolis---------------------------------------------------------------------------*/

  FUNCTION USU_OBTIENE_FECVENC_PROX (cCodGrupoCia_in   IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    cCodTrab_in IN CHAR)
  RETURN CHAR

  IS
   v_Fecha  CHAR(12):='';

  BEGIN

 SELECT
   CASE
   WHEN TRUNC((FEC_VENCIMIENTO)-30)<=TRUNC(SYSDATE) AND TRUNC(FEC_VENCIMIENTO)>=TRUNC(SYSDATE) THEN
       NVL(TO_CHAR(FEC_VENCIMIENTO,'dd/MM/yyyy'),' ')   --POR VENCER


   WHEN TRUNC(FEC_VENCIMIENTO)<TRUNC(SYSDATE) THEN
       NVL(TO_CHAR(FEC_VENCIMIENTO,'dd/MM/yyyy'),' ')  --VENCIDOS
   END

   INTO v_Fecha

   FROM   PBL_USU_LOCAL
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND   COD_LOCAL = cCodLocal_in
          --AND    Cod_Trab_Rrhh = cCodTrabRR_HH_in;
          AND    SEC_USU_LOCAL = cCodTrab_in;

      BEGIN

      IF v_Fecha IS NULL THEN
          v_Fecha :='NV'; --No se ha vencido el nro carné
          DBMS_OUTPUT.put_line('v_Fecha:'||v_Fecha);

      ELSIF TO_DATE(v_Fecha)<TRUNC(sysdate) THEN
          v_Fecha :='V'; --Carné Vencido
           DBMS_OUTPUT.put_line('v_Fechav:'||v_Fecha);

      ELSE
           v_Fecha :='P'; --Carné próximo a Vencer
            DBMS_OUTPUT.put_line('v_Fechap:'||v_Fecha);

      END IF;

      END;

  return v_Fecha;

  END;

/***********************************asolis************************************************/


  FUNCTION USU_EXISTE_DUPLICADO_CARNE(cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        COD_TRAB_RRHH_IN IN CHAR,
                                       cNumCarne_in     IN CHAR)
    RETURN VARCHAR2

    IS
    v_rpta CHAR(1);
    v_cant CHAR(3);

    BEGIN

     SELECT COUNT(*)
       INTO   v_cant
      FROM   PBL_USU_LOCAL C
      WHERE
             COD_TRAB_RRHH != COD_TRAB_RRHH_IN
      AND    COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in
      AND    CARNE_SANIDAD  = cNumCarne_in;

          IF v_cant = 0 THEN
             v_rpta :='0';
          ELSE
             v_rpta :='1';
          END IF;
       RETURN v_rpta;

    END;


/**************************asolis***************************/

   FUNCTION USU_OBTIENE_FECVENC_PROX_CARNE(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cCodTrab_in IN CHAR)
    RETURN char
  IS
   v_Fecha  CHAR(12);

  BEGIN

 SELECT
   CASE
   WHEN TRUNC((FEC_VENCIMIENTO)-30)<=TRUNC(SYSDATE) AND TRUNC(FEC_VENCIMIENTO)>=TRUNC(SYSDATE) THEN
       NVL(TO_CHAR(FEC_VENCIMIENTO,'dd/MM/yyyy'),' ')   --POR VENCER


   WHEN TRUNC(FEC_VENCIMIENTO)<TRUNC(SYSDATE) THEN
       NVL(TO_CHAR(FEC_VENCIMIENTO,'dd/MM/yyyy'),' ')  --VENCIDOS
   END

   INTO v_Fecha

   FROM   PBL_USU_LOCAL
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND   COD_LOCAL = cCodLocal_in
          AND    SEC_USU_LOCAL = cCodTrab_in;


      BEGIN

      IF v_Fecha IS NULL THEN
          v_Fecha :='NV'; --No se ha vencido el nro carné
          DBMS_OUTPUT.put_line('v_Fecha:'||v_Fecha);



      END IF;

      END;

  return v_Fecha;

  END;

 /*************************asolis********************************/

  FUNCTION USU_VERIFICA_EXISTENCIA_CARNE(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in   IN CHAR,
                                           cCodTrab_in    IN CHAR)

    RETURN char

    IS
    vCarne char(20);

 BEGIN

      SELECT CARNE_SANIDAD INTO vCarne FROM  PBL_USU_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND   COD_LOCAL = cCodLocal_in
          AND   SEC_USU_LOCAL = cCodTrab_in
          AND   EST_USU ='A';


         BEGIN
         IF  vCarne is null THEN
             vCarne := '0';
         END IF;
         END;


     RETURN vCarne;

 END;
/***********************************************************/


  PROCEDURE USU_ALERTA_TRAB_S_CARNE_M_ING(cCodGrupoCia_in    IN CHAR,
                                          cCodLocal_in       IN CHAR,
                                          cCodTrab_in        IN CHAR)


   IS

  vMensajeTitulo  varchar (500);
  vMensajeInfoUsuario   varchar2 (900);
  vMensajeFila   varchar2 (5200);
  vMensajeFinal  varchar2 (8000);
  vResult_EXISTE_USU_SIN_CARNE char(1);
  cFechaMensaje  varchar (150);
  v_vDescLocal varchar (150);

  v_vSEC_USU_LOCAL  PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE;
  v_vCod_Trab_Rrhh  PBL_USU_LOCAL.Cod_Trab_Rrhh%TYPE;
  v_vAPE_PAT  PBL_USU_LOCAL.APE_PAT%TYPE;
  v_vAPE_MAT  PBL_USU_LOCAL.APE_MAT%TYPE;
  v_vNOM_USU  PBL_USU_LOCAL.NOM_USU%TYPE;
  v_vDNI_USU  PBL_USU_LOCAL.DNI_USU%TYPE;
  v_CARN_SANIDAD  PBL_USU_LOCAL.CARNE_SANIDAD%TYPE;

      BEGIN
      SELECT A.SEC_USU_LOCAL ,
             nvl(A.COD_TRAB_RRHH,' ') Cod_Trab_Rrhh,
             A.APE_PAT ,
             NVL(A.APE_MAT,' ') APE_MAT,
             A.NOM_USU ,
             nvl(A.DNI_USU,' ') DNI_USU,
             A.CARNE_SANIDAD
      INTO   v_vSEC_USU_LOCAL,
             v_vCod_Trab_Rrhh,
             v_vAPE_PAT,
             v_vAPE_MAT,
             v_vNOM_USU,
             v_vDNI_USU,
             v_CARN_SANIDAD
      FROM   PBL_USU_LOCAL A
      WHERE  A.COD_GRUPO_CIA    = cCodGrupoCia_in
      AND    A.COD_LOCAL  = cCodLocal_in
      AND    A.EST_USU ='A'
      AND    A.SEC_USU_LOCAL = cCodTrab_in;

             IF  v_vSEC_USU_LOCAL IS NOT NULL THEN
                 DBMS_OUTPUT.PUT_LINE('si hay datos');
                 vMensajeInfoUsuario:=  '<TR><TD align="center">' || v_vCod_Trab_Rrhh || '</TD>' ||'<TD align="center">' || v_vNOM_USU || ' ' ||  v_vAPE_PAT || ' ' || v_vAPE_MAT || '</TD>' || '<TD align="center">' ||  v_vDNI_USU  || '</TD></TR>';
                 vMensajeFila := vMensajeFila || vMensajeInfoUsuario;
             END IF;

             --JCORTEZ 09/03/2009  solo si no tiene carne de sanidad
             IF(v_CARN_SANIDAD IS NULL)THEN
               vResult_EXISTE_USU_SIN_CARNE :='S';
             ELSE
               vResult_EXISTE_USU_SIN_CARNE :='N';
             END IF;


             IF (vResult_EXISTE_USU_SIN_CARNE = 'S') then

             --DESCRIPCION DE LOCAL
                        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
                        INTO   v_vDescLocal
                        FROM   PBL_LOCAL
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL = cCodLocal_in;

              vMensajeTitulo :='<BR>  <BR>' || '<TABLE  BORDER=1 align="center">' || '<TR><TD  >' || 'Cod Trab Rrhh' || '</TD>' || '<TD align="center">' ||' Nombres' || '</TD>' || '<TD align="center">' ||'Dni' || '</TD></TR>';
              vMensajeFinal := vMensajeTitulo || vMensajeFila  ||'</TABLE>' ;
              cFechaMensaje := TO_CHAR (TRUNC (SYSDATE), 'DD/MM/YYYY ');

                    ENVIA_CORREO_INF_MAIL_LOCAL(cCodGrupoCia_in,cCodLocal_in,
                                                'ALERTA USUARIO SIN CARNÉ SANIDAD:',
                                                'ALERTA',
                                                ' EXISTE USUARIO SIN CARNÉ SANIDAD EN LOCAL:'|| '   '|| v_vDescLocal || '       ' || '<B> Fecha :</B>' || cFechaMensaje ||'<BR>' ||
                                                '<BR>Datos del Trabajador<BR>' ||
                                                '</B>'||
                                                '<BR> <I>Verifique:</I> <BR>'|| vMensajeFinal || '<B>');

             END IF;

       END;

/************************************************************/

 FUNCTION LISTA_ROLES_TEMP(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR
                   )
RETURN FarmaCursor
IS
    mifcur FarmaCursor;
  BEGIN
    OPEN mifcur FOR
    SELECT USU.DNI_USU       || 'Ã' ||
         USU.NOM_USU ||' '|| USU.APE_PAT || 'Ã' ||
         TO_CHAR(TMP.FEC_INICIO ,'dd/mm/yyyy HH24:MI:SS')|| 'Ã' ||
         TO_CHAR(TMP.FEC_VENCIMIENTO ,'dd/mm/yyyy')|| 'Ã' ||
         TMP.USU_CREA_EXE     || 'Ã' ||
         TO_CHAR(TMP.FEC_CREA_USU_EXE,'dd/mm/yyyy HH24:MI:SS')
         
     FROM  PBL_ADMIN_TEMP  TMP ,PBL_USU_LOCAL USU
     WHERE USU.COD_GRUPO_CIA=TMP.COD_GRUPO_CIA
     AND USU.COD_LOCAL=TMP.COD_LOCAL
     AND USU.SEC_USU_LOCAL=TMP.SEC_USU_LOCAL 
     AND TMP.COD_GRUPO_CIA=cCodGrupoCia_in
     AND TMP.COD_LOCAL=cCodLocal_in 
     --FECMOD:16/04/2015 CHUANES
     --AND to_date(TMP.FEC_CREA_USU_EXE,'dd/mm/yyyy') <= to_date(SYSDATE,'dd/mm/yyyy') AND  to_date(TMP.FEC_CREA_USU_EXE,'dd/mm/yyyy')>(to_date(SYSDATE,'dd/mm/yyyy') -30)
     AND TMP.FEC_CREA_USU_EXE <= SYSDATE AND  TMP.FEC_CREA_USU_EXE>(SYSDATE -30)

     ORDER BY FEC_CREA_USU_EXE DESC;
        RETURN mifcur;
  END;
  /****************************************************/
 FUNCTION BUSCA_DATOS_USUARIO(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR,cDni_in IN CHAR
                   )
RETURN FarmaCursor
IS
    mifcur FarmaCursor;
  BEGIN
    OPEN mifcur FOR
    
    SELECT USU.SEC_USU_LOCAL || 'Ã' ||
    USU.LOGIN_USU FROM PBL_USU_LOCAL  USU
    WHERE USU.COD_GRUPO_CIA=cCodGrupoCia_in
    AND USU.COD_LOCAL=cCodLocal_in
    AND USU.DNI_USU=cDni_in
    AND USU.EST_USU='A';
            
            
            RETURN mifcur;
  END;
  /*****************************************************/
  
  FUNCTION REGISTRA_ROL_TMP (cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cSec_Usu IN CHAR,cFec_Inicio IN CHAR,
                           cFec_Fin IN CHAR ,cUsuario IN CHAR )
                  
                           
  RETURN CHAR
  IS
 
  flag CHAR(10);
  vFecInicio VARCHAR2(30);
  vFecPrueba VARCHAR2(30); 
  BEGIN
  flag:='FALSE';
  vFecPrueba:=TO_CHAR(SYSDATE,'dd/mm/yyyy');
  IF(cFec_Inicio=vFecPrueba)THEN 
  vFecInicio:=TO_CHAR(SYSDATE,'dd/mm/yyyy HH24:MI:SS');
   INSERT INTO  PBL_ADMIN_TEMP(cod_grupo_cia,cod_local,sec_usu_local,fec_inicio,fec_vencimiento,usu_crea_exe) VALUES(cCodGrupoCia_in,cCodLocal_in,cSec_Usu,TO_DATE(vFecInicio,'dd/mm/yyyy HH24:MI:SS'),TO_DATE(cFec_Fin,'dd/mm/yyyy'),cUsuario );
  flag:='TRUE';
  
  ELSE
  
  vFecInicio:=cFec_Inicio||' '||'00:00:00';
   INSERT INTO  PBL_ADMIN_TEMP(cod_grupo_cia,cod_local,sec_usu_local,fec_inicio,fec_vencimiento,usu_crea_exe) VALUES(cCodGrupoCia_in,cCodLocal_in,cSec_Usu,TO_DATE(vFecInicio,'dd/mm/yyyy HH24:MI:SS'),TO_DATE(cFec_Fin,'dd/mm/yyyy'),cUsuario );
  flag:='TRUE';
  
  END IF;
 
 
  RETURN flag;
  
  EXCEPTION 
  WHEN OTHERS THEN

              DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
     RETURN flag; 
 END; 
 /*************************************************/               
  FUNCTION VERIFICA_VIGENCIA_ROL_TMP(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR, cFecInico IN CHAR,cFecFin IN CHAR) 
  RETURN CHAR
  IS
  flag CHAR(10);
  vCantidad NUMBER;
  BEGIN
  flag:='FALSE';
    IF  TO_DATE(cFecInico,'dd/mm/yyyy HH24:MI:SS')<SYSDATE THEN --EVALUAMOS QUE SEA ANTES DE HOY
    --CAMBIO
   SELECT  COUNT(*) INTO  vCantidad
   FROM  PBL_ADMIN_TEMP TMP
   WHERE 
   TMP.COD_GRUPO_CIA=cCodGrupoCia_in
   AND  TMP.COD_LOCAL=cCodLocal_in
   AND  TMP.SEC_USU_LOCAL=(SELECT USU.SEC_USU_LOCAL FROM PBL_USU_LOCAL  USU WHERE USU.DNI_USU=cDni)
   AND  TMP.FEC_INICIO=TO_DATE(cFecInico,'dd/mm/yyyy HH24:MI:SS')--CAMBIO
   AND  SYSDATE BETWEEN TO_DATE(cFecInico,'dd/mm/yyyy HH24:MI:SS') AND TO_DATE(cFecFin ||'23:59:59','dd/mm/yyyy HH24:MI:SS');
   IF vCantidad=0 THEN--NO ESTA VIGENTE
      flag:='TRUE';
      
    END IF;
    
  END IF;  
    
    RETURN flag;
  
    EXCEPTION 
    WHEN OTHERS THEN

              DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
     RETURN flag; 
     
     END;
   /*********************************************************/  
   FUNCTION VERIFICA_VIGENCIA_FUTURO(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR, cFecInicio IN CHAR) 
  RETURN CHAR
  IS
  flag CHAR(10);
  vCantidad NUMBER;
  BEGIN
  flag:='FALSE';
  
   SELECT  COUNT(*) INTO  vCantidad
   FROM  PBL_ADMIN_TEMP TMP
   WHERE 
    TMP.COD_GRUPO_CIA=cCodGrupoCia_in
   AND  TMP.COD_LOCAL=cCodLocal_in
   AND  TMP.SEC_USU_LOCAL=(SELECT USU.SEC_USU_LOCAL FROM PBL_USU_LOCAL  USU WHERE USU.DNI_USU=cDni)
   AND TMP.FEC_INICIO=TO_DATE(cFecInicio,'dd/mm/yyyy HH24:MI:SS')--CAMBIO
   AND  TO_DATE(cFecInicio,'dd/mm/yyyy HH24:MI:SS') >SYSDATE ;--CAMBIO
   IF vCantidad>0 THEN--EL REGISTRO ESTA VIGENTE
      flag:='TRUE';
    END IF;
    RETURN flag;
  
    EXCEPTION 
    WHEN OTHERS THEN

              DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
     RETURN flag; 
     
     END;  
     
   /*****************************************************/
    FUNCTION ACTUALIZA_DATOS_ROL_TMP(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR, cFecInico IN CHAR,cFecFin IN CHAR ,cUsuario IN CHAR) 
  RETURN CHAR
  IS
  flag CHAR(10);
  vCantidad NUMBER;
  BEGIN
  flag:='FALSE';
  --FECMOD 16/04/2015--CHUANES
 UPDATE  PBL_ADMIN_TEMP  TMP  SET TMP.FEC_VENCIMIENTO=cFecFin,TMP.FEC_MOD_USU_EXE=SYSDATE,TMP.USU_MOD_EXE=cUsuario
 WHERE TMP.COD_GRUPO_CIA=cCodGrupoCia_in
 AND  TMP.COD_LOCAL=cCodLocal_in
 AND TMP.SEC_USU_LOCAL=(SELECT USU.SEC_USU_LOCAL FROM PBL_USU_LOCAL USU WHERE USU.DNI_USU=cDni)
 AND TMP.FEC_INICIO=TO_DATE(cFecInico,'dd/mm/yyyy HH24:MI:SS');--CAMBIO 
 flag:='TRUE';
 
    RETURN flag;
  
    EXCEPTION 
    WHEN OTHERS THEN

              DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
     RETURN flag; 
     
     END; 
  /*****************************************************/
  
      FUNCTION ELIMINA_DATOS_ROL_TMP(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR, cFecInico IN CHAR) 
  RETURN CHAR
  IS
  flag CHAR(10);
  vCantidad NUMBER;
  BEGIN
  flag:='FALSE';
  
DELETE FROM   PBL_ADMIN_TEMP  TMP
 
 WHERE TMP.COD_GRUPO_CIA=cCodGrupoCia_in
 AND  TMP.COD_LOCAL=cCodLocal_in
 AND TMP.SEC_USU_LOCAL=(SELECT USU.SEC_USU_LOCAL FROM PBL_USU_LOCAL USU WHERE USU.DNI_USU=cDni)
 AND TMP.FEC_INICIO=TO_DATE(cFecInico,'dd/mm/yyyy HH24:MI:SS');--CAMBIO
 flag:='TRUE';
 
    RETURN flag;
  
    EXCEPTION 
    WHEN OTHERS THEN

              DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
     RETURN flag; 
     
     END; 
     
     /*******************************************/
     
     
  FUNCTION VERIFICA_NO_DUPLICIDAD(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR, cFecInicio IN CHAR) 
  RETURN CHAR
  IS
  flag CHAR(10);
  vCantidad NUMBER;
  BEGIN
  flag:='FALSE';
  SELECT  COUNT(*) INTO  vCantidad FROM  PBL_ADMIN_TEMP TMP
  WHERE TMP.COD_GRUPO_CIA=cCodGrupoCia_in
  AND  TMP.COD_LOCAL=cCodLocal_in
  AND  TMP.SEC_USU_LOCAL=(SELECT USU.SEC_USU_LOCAL FROM PBL_USU_LOCAL  USU WHERE USU.DNI_USU=cDni)
  AND TMP.FEC_INICIO=TO_DATE(cFecInicio,'dd/mm/yyyy HH24:MI:SS');--CAMBIO
  IF vCantidad>0 THEN
  flag:='TRUE';
  END IF; 
 
  RETURN flag;
  
  EXCEPTION 
  WHEN OTHERS THEN

  DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
  RETURN flag; 
     
  END; 
  
/*******************************************************************************/      
  FUNCTION VERIFICA_CRUCE_FECHA(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR,cFecInicio IN CHAR) 
  RETURN CHAR
  IS
  flag CHAR(10);
  vCantidad NUMBER;
 
  BEGIN
  flag:='FALSE';
   
   SELECT  COUNT(*) INTO  vCantidad
   FROM  PBL_ADMIN_TEMP TMP
   WHERE 
   TMP.COD_GRUPO_CIA=cCodGrupoCia_in
   AND  TMP.COD_LOCAL=cCodLocal_in
   AND  TMP.SEC_USU_LOCAL=(SELECT USU.SEC_USU_LOCAL FROM PBL_USU_LOCAL  USU WHERE USU.DNI_USU=cDni)
   AND TMP.FEC_CREA_USU_EXE=(SELECT MAX(TMP.FEC_CREA_USU_EXE) FROM PBL_ADMIN_TEMP TMP WHERE TMp.Sec_Usu_Local=(SELECT USU.SEC_USU_LOCAL FROM PBL_USU_LOCAL  USU WHERE USU.DNI_USU=cDni))
   AND   TMP.FEC_VENCIMIENTO >TO_DATE(cFecInicio,'dd/mm/yyyy')- 1/24/60/60 ;
   
   IF vCantidad>0 THEN--NO ESTA VIGENTE
      flag:='TRUE';
      
    END IF;
   
    RETURN flag;
  
  EXCEPTION 
  WHEN OTHERS THEN

  DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
  RETURN flag; 
     
  END;     
        
/***********************************************************/
   
  FUNCTION VERIFICA_FECHA_INICIO(cFecInicio IN CHAR) 
  RETURN CHAR
  IS
  flag CHAR(10);
  vCantidad NUMBER;
  BEGIN
  flag:='FALSE';
  
  IF TO_DATE(TO_CHAR(SYSDATE,'dd/mm/yyyy')) >TO_DATE(cFecInicio,'dd/mm/yyyy') THEN 
  flag:='TRUE';
  END IF;
  RETURN flag;
  
  EXCEPTION 
  WHEN OTHERS THEN

  DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
  RETURN flag; 
     
  END;
  
  /*******************************************************************************/
  
  FUNCTION VERIFICA_CRUCE_FECHA_UPDATE(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,cDni IN CHAR,cFecInicio IN CHAR,cFecFin IN CHAR) 
  RETURN CHAR
  IS
  flag CHAR(10);
  vCantidad NUMBER;
  vPuesto   CHAR(5);
  BEGIN
  flag:='FALSE';
  
  SELECT  COUNT(*)INTO  vCantidad--M.FEC_INICIO
  FROM  
        (
    SELECT T.FEC_INICIO,
    RANK() OVER(PARTITION BY COD_GRUPO_CIA, COD_LOCAL, SEC_USU_LOCAL ORDER BY FEC_CREA_USU_EXE) "PUESTO",
    T.FEC_CREA_USU_EXE,T.SEC_USU_LOCAL
    FROM PBL_ADMIN_TEMP T
    WHERE SEC_USU_LOCAL = (SELECT USU.SEC_USU_LOCAL FROM PBL_USU_LOCAL  USU WHERE USU.DNI_USU=cDni)
    AND FEC_CREA_USU_EXE > (SELECT ROL.FEC_CREA_USU_EXE FROM  PBL_ADMIN_TEMP  ROL WHERE ROL.SEC_USU_LOCAL=(SELECT USU.SEC_USU_LOCAL FROM PBL_USU_LOCAL  USU WHERE USU.DNI_USU=cDni) AND ROL.FEC_INICIO=TO_DATE(cFecInicio,'dd/mm/yyyy HH24:MI:SS')) --CAMBIO  
        ) M
  WHERE PUESTO = 1
  AND   M.FEC_INICIO - 1/24/60/60 <TO_DATE(cFecFin,'dd/mm/yyyy') 
  ORDER BY SEC_USU_LOCAL, FEC_CREA_USU_EXE;
  
     --BUSCA DEL SIGUIENTE UNICO REGISTRO SU FECHA DE INICIO Y LO COMPARA CON EL REGISTRO SELECCIONADO.
   
   IF vCantidad>0 THEN--SI  HAY UN REGISTRO QUE CUMPLE LA CONDICION
      flag:='TRUE';
      
   END IF;
    RETURN flag;
  
  EXCEPTION 
  WHEN OTHERS THEN

  DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
  RETURN flag; 
     
  END; 
  /********************************************/
   FUNCTION VERIFICA_COD_TRAB(cCodGrupoCia_in IN CHAR, cCodLocal_in  IN CHAR,cDni IN CHAR)
                           
  RETURN CHAR
  IS
  flag CHAR(10);
  vCodCargo CHAR(4);
  vCantidad NUMBER;  
  BEGIN
  flag:='FALSE';
   
    SELECT  TRAB.Cod_Cargo INTO  vCodCargo FROM  PBL_USU_LOCAL USU ,CE_MAE_TRAB TRAB

   WHERE USU.COD_CIA=TRAB.COD_CIA
    AND USU.COD_TRAB=TRAB.COD_TRAB
    AND USU.COD_GRUPO_CIA=cCodGrupoCia_in
    AND USU.COD_LOCAL=cCodLocal_in
    AND USU.SEC_USU_LOCAL=(SELECT USU.SEC_USU_LOCAL FROM PBL_USU_LOCAL  USU WHERE USU.DNI_USU=cDni)
    AND USU.EST_USU='A';
   
   SELECT COUNT(*) INTO  vCantidad FROM CE_CARGO_AUX AUX 
   WHERE AUX.COD_CARGO=vCodCargo;
   
    
   IF (vCantidad>0) THEN
    flag:='TRUE';
    END IF;
   RETURN flag;
  
  EXCEPTION 
  WHEN OTHERS THEN

  DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
  RETURN flag; 
     
  END; 
  
  /***********************************************/
      FUNCTION LISTA_ROLES_RANGOFECHA(cCodGrupoCia_in IN CHAR,
      cCodLocal_in    IN CHAR,cFecInicio CHAR,cFecFin CHAR)
      
      RETURN FarmaCursor
      IS
      mifcur FarmaCursor;
      BEGIN
      OPEN mifcur FOR
      SELECT USU.DNI_USU       || 'Ã' ||
      USU.NOM_USU ||' '|| USU.APE_PAT || 'Ã' ||
      TO_CHAR(TMP.FEC_INICIO ,'dd/mm/yyyy HH24:MI:SS')|| 'Ã' ||
      TO_CHAR(TMP.FEC_VENCIMIENTO ,'dd/mm/yyyy')|| 'Ã' ||
      TMP.USU_CREA_EXE     || 'Ã' ||
      TO_CHAR(TMP.FEC_CREA_USU_EXE,'dd/mm/yyyy HH24:MI:SS')
      FROM  PBL_ADMIN_TEMP  TMP ,PBL_USU_LOCAL USU
      WHERE USU.COD_GRUPO_CIA=TMP.COD_GRUPO_CIA
      AND USU.COD_LOCAL=TMP.COD_LOCAL
      AND USU.SEC_USU_LOCAL=TMP.SEC_USU_LOCAL 
      AND TMP.COD_GRUPO_CIA=cCodGrupoCia_in
      AND TMP.COD_LOCAL=cCodLocal_in
      AND TMP.FEC_CREA_USU_EXE between to_date(cFecInicio,'dd/mm/yyyy') AND  to_date(cFecFin,'dd/mm/yyyy')+1
      ORDER BY FEC_CREA_USU_EXE DESC;
      RETURN mifcur;
  END;
  /*****************************************************************/
  FUNCTION VERIFICA_RANGO_ENTRE_FECHAS(cFecInicio CHAR,cFecFin CHAR)
                           
  RETURN CHAR
  IS
  flag CHAR(10);
  vFecInicio DATE;
  vFecFin DATE;
  vCantDias CHAR(3);
  BEGIN
  vFecInicio:=TO_DATE(cFecInicio,'dd/mm/yyyy');
  vFecFin:=TO_DATE(cFecFin,'dd/mm/yyyy');
  flag:='FALSE';
  SELECT m.Llave_Tab_Gral INTO vCantDias FROM PBL_TAB_GRAL m where id_tab_graL='678'; 
   IF (vFecFin-vFecInicio>NVL(vCantDias,0))
   THEN
   flag:='FALSE';
   ELSE
   flag:='TRUE';
   END IF;
  RETURN flag; 
  EXCEPTION 
  WHEN OTHERS THEN

  DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
  RETURN flag; 
     
  END; 
  
  /**********************************************************/
  FUNCTION MAXIMO_RANGO_FECHAS
                           
  RETURN CHAR
  IS
 
  vCantDias CHAR(3);
  BEGIN
  
  SELECT m.Llave_Tab_Gral INTO vCantDias FROM PBL_TAB_GRAL m where id_tab_graL='678'; 
   
  RETURN vCantDias; 
  EXCEPTION 
  WHEN OTHERS THEN

  DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
  RETURN '0'; 
     
  END;
  /**************************************************/
  FUNCTION MAXIMO_ULTIMAS_CLAVES
                           
  RETURN CHAR
  IS
 
  vUltimaClave CHAR(3);
  BEGIN
  
  SELECT m.Llave_Tab_Gral INTO vUltimaClave FROM PBL_TAB_GRAL m where id_tab_graL='677'; 
   
  RETURN vUltimaClave; 
  EXCEPTION 
  WHEN OTHERS THEN

  DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
  RETURN '0'; 
     
  END; 

/*************************************************************************************/
-- KMONCADA 04.12.2015
  FUNCTION F_VAR_SOLICITUD_HUELLAS 
    RETURN VARCHAR2 IS
    vValor VARCHAR2(20);
  BEGIN
    SELECT TAB.LLAVE_TAB_GRAL 
    INTO vValor
    FROM PBL_TAB_GRAL TAB 
    WHERE TAB.ID_TAB_GRAL = 585;
    
    RETURN vValor;
  END;

/*************************************************************************************/
-- 01.12.2015 KMONCADA
  FUNCTION F_CUR_USUARIO_FV(pCodGrupoCia_in IN PBL_USU_LOCAL.COD_GRUPO_CIA%TYPE,
                            pCodLocal_in    IN PBL_USU_LOCAL.COD_LOCAL%TYPE,
                            pSecUsu_in      IN PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE)
    RETURN FARMACURSOR IS
    curUsuario FARMACURSOR;
  BEGIN
    IF pSecUsu_in = '*' THEN
      OPEN curUsuario FOR
        SELECT 
           /*
           USU.SEC_USU_LOCAL SEC_USU,
           USU.DNI_USU NRO_DOCUMENTO,
           USU.APE_PAT AP_PATERNO,
           USU.APE_MAT AP_MATERNO,
           USU.NOM_USU NOMBRES,
           CE.HUELLA_INDICE_DER
           --CE.HUELLA HUELLA_DACTILAR
           */
           USU.SEC_USU_LOCAL SEC_USU,
           USU.DNI_USU       NRO_DOCUMENTO,
           USU.APE_PAT       AP_PATERNO,
           USU.APE_MAT       AP_MATERNO,
           USU.NOM_USU       NOMBRES,
           -- MANO IZQUIERDA
           CE.HUELLA_MENIQUE_IZQ IZQUIERDA_MENIQUE,
           CE.HUELLA_ANULAR_IZQ IZQUIERDA_ANULAR,
           CE.HUELLA_MEDIO_IZQ IZQUIERDA_MEDIO,
           CE.HUELLA_INDICE_IZQ IZQUIERDA_INDICE,
           CE.HUELLA_PULGAR_IZQ IZQUIERDA_PULGAR,
           -- MANO DERECHA
           CE.HUELLA_PULGAR_DER DERECHA_PULGAR,
           CE.HUELLA_INDICE_DER DERECHA_INDICE,
           CE.HUELLA_MEDIO_DER DERECHA_MEDIO,
           CE.HUELLA_ANULAR_DER DERECHA_ANULAR,
           CE.HUELLA_MENIQUE_DER DERECHA_MENIQUE
        FROM PBL_USU_LOCAL USU,
         CE_MAE_TRAB CE
        WHERE USU.COD_CIA = CE.COD_CIA
        AND USU.COD_TRAB = CE.COD_TRAB
        AND USU.COD_GRUPO_CIA = pCodGrupoCia_in
        AND USU.COD_LOCAL = pCodLocal_in
        AND USU.EST_USU = 'A'
        AND USU.SEC_USU_LOCAL BETWEEN '001' AND '899';
    ELSE
      OPEN curUsuario FOR
        SELECT 
           /*
           USU.SEC_USU_LOCAL SEC_USU,
           USU.DNI_USU NRO_DOCUMENTO,
           USU.APE_PAT AP_PATERNO,
           USU.APE_MAT AP_MATERNO,
           USU.NOM_USU NOMBRES,
           CE.HUELLA_INDICE_DER
           --CE.HUELLA HUELLA_DACTILAR
           */
           USU.SEC_USU_LOCAL SEC_USU,
           USU.DNI_USU       NRO_DOCUMENTO,
           USU.APE_PAT       AP_PATERNO,
           USU.APE_MAT       AP_MATERNO,
           USU.NOM_USU       NOMBRES,
           -- MANO IZQUIERDA
           CE.HUELLA_MENIQUE_IZQ IZQUIERDA_MENIQUE,
           CE.HUELLA_ANULAR_IZQ IZQUIERDA_ANULAR,
           CE.HUELLA_MEDIO_IZQ IZQUIERDA_MEDIO,
           CE.HUELLA_INDICE_IZQ IZQUIERDA_INDICE,
           CE.HUELLA_PULGAR_IZQ IZQUIERDA_PULGAR,
           -- MANO DERECHA
           CE.HUELLA_PULGAR_DER DERECHA_PULGAR,
           CE.HUELLA_INDICE_DER DERECHA_INDICE,
           CE.HUELLA_MEDIO_DER DERECHA_MEDIO,
           CE.HUELLA_ANULAR_DER DERECHA_ANULAR,
           CE.HUELLA_MENIQUE_DER DERECHA_MENIQUE
        FROM PBL_USU_LOCAL USU,
         CE_MAE_TRAB CE
        WHERE USU.COD_CIA = CE.COD_CIA
        AND USU.COD_TRAB = CE.COD_TRAB
        AND USU.COD_GRUPO_CIA = pCodGrupoCia_in
        AND USU.COD_LOCAL = pCodLocal_in
        AND USU.SEC_USU_LOCAL = pSecUsu_in;
    END IF;
    RETURN curUsuario;
  END;

/*************************************************************************************/
-- 01.12.2015 KMONCADA  
  FUNCTION F_REGISTRAR_HUELLA(pCodGrupoCia_in    IN PBL_USU_LOCAL.COD_GRUPO_CIA%TYPE,
                              pCodLocal_in       IN PBL_USU_LOCAL.COD_LOCAL%TYPE,
                              pSecUsu_in         IN PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE,
                              pHuella_in         IN PTOVENTA.CE_MAE_TRAB.HUELLA_MENIQUE_DER%TYPE,
                              pUsuMod_in         IN PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE,
                              pPosicionHuella_in IN NUMBER) 
    RETURN CHAR IS
    vCodCia  PTOVENTA.CE_MAE_TRAB.COD_CIA%TYPE;
    vCodTrab PTOVENTA.CE_MAE_TRAB.COD_TRAB%TYPE;
  BEGIN
    BEGIN
      SELECT LOC.COD_CIA, LOC.COD_TRAB
      INTO vCodCia, vCodTrab
      FROM PTOVENTA.PBL_USU_LOCAL LOC
      WHERE LOC.COD_GRUPO_CIA = pCodGrupoCia_in
      AND LOC.COD_LOCAL = pCodLocal_in
      AND LOC.SEC_USU_LOCAL = pSecUsu_in;
    EXCEPTION 
      WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20010,'NO SE PUDO UBICAR CODIGO DEL TRABAJADOR');
      WHEN TOO_MANY_ROWS THEN
        RAISE_APPLICATION_ERROR(-20011,'SE ENCONTRO MAS DE UN CODIGO DE TRABAJADOR DEL USUARIO');
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(SQLCODE, SQLERRM);
    END;
    
    IF pPosicionHuella_in = 0 THEN
        UPDATE PTOVENTA.CE_MAE_TRAB T SET T.HUELLA_MENIQUE_IZQ = pHuella_in WHERE T.COD_CIA = vCodCia AND T.COD_TRAB = vCodTrab;
    ELSIF pPosicionHuella_in = 1 THEN
        UPDATE PTOVENTA.CE_MAE_TRAB T SET T.HUELLA_ANULAR_IZQ = pHuella_in WHERE T.COD_CIA = vCodCia AND T.COD_TRAB = vCodTrab;
    ELSIF pPosicionHuella_in = 2 THEN
        UPDATE PTOVENTA.CE_MAE_TRAB T SET T.HUELLA_MEDIO_IZQ = pHuella_in WHERE T.COD_CIA = vCodCia AND T.COD_TRAB = vCodTrab;
    ELSIF pPosicionHuella_in = 3 THEN
        UPDATE PTOVENTA.CE_MAE_TRAB T SET T.HUELLA_INDICE_IZQ = pHuella_in WHERE T.COD_CIA = vCodCia AND T.COD_TRAB = vCodTrab;
    ELSIF pPosicionHuella_in = 4 THEN
        UPDATE PTOVENTA.CE_MAE_TRAB T SET T.HUELLA_PULGAR_IZQ = pHuella_in WHERE T.COD_CIA = vCodCia AND T.COD_TRAB = vCodTrab;
    ELSIF pPosicionHuella_in = 5 THEN
        UPDATE PTOVENTA.CE_MAE_TRAB T SET T.HUELLA_PULGAR_DER = pHuella_in WHERE T.COD_CIA = vCodCia AND T.COD_TRAB = vCodTrab;
    ELSIF pPosicionHuella_in = 6 THEN
        UPDATE PTOVENTA.CE_MAE_TRAB T SET T.HUELLA_INDICE_DER = pHuella_in WHERE T.COD_CIA = vCodCia AND T.COD_TRAB = vCodTrab;
    ELSIF pPosicionHuella_in = 7 THEN
        UPDATE PTOVENTA.CE_MAE_TRAB T SET T.HUELLA_MEDIO_DER = pHuella_in WHERE T.COD_CIA = vCodCia AND T.COD_TRAB = vCodTrab;
    ELSIF pPosicionHuella_in = 8 THEN
        UPDATE PTOVENTA.CE_MAE_TRAB T SET T.HUELLA_ANULAR_DER = pHuella_in WHERE T.COD_CIA = vCodCia AND T.COD_TRAB = vCodTrab;
    ELSIF pPosicionHuella_in = 9 THEN
        UPDATE PTOVENTA.CE_MAE_TRAB T SET T.HUELLA_MENIQUE_DER = pHuella_in WHERE T.COD_CIA = vCodCia AND T.COD_TRAB = vCodTrab;
    END IF;
    
    UPDATE PTOVENTA.CE_MAE_TRAB T
    SET T.FEC_MOD_TRAB = SYSDATE,
        T.USU_MOD_TRAB = pUsuMod_in
    WHERE T.COD_CIA = vCodCia
    AND T.COD_TRAB = vCodTrab;
    
    IF SQL%ROWCOUNT = 0 THEN
      RAISE_APPLICATION_ERROR(-20011,'SE ENCONTRO MAS DE UN CODIGO DE TRABAJADOR DEL USUARIO');
    ELSIF SQL%ROWCOUNT > 1 THEN
      RAISE_APPLICATION_ERROR(-20011,'SE ENCONTRO MAS DE UN CODIGO DE TRABAJADOR DEL USUARIO');
    END IF;
    RETURN 'S';
  END;
  
  
  FUNCTION F_NUM_REPITE_HUELLA
    RETURN NUMBER IS
    vRepite NUMBER;
  BEGIN
    BEGIN
      SELECT TO_NUMBER(TAB.LLAVE_TAB_GRAL,'99999.00')
      INTO vRepite
      FROM PTOVENTA.PBL_TAB_GRAL TAB
      WHERE TAB.ID_TAB_GRAL = 586;
    EXCEPTION 
      WHEN OTHERS THEN
        vRepite := 1;
    END;
    IF vRepite NOT IN (4,2,1) THEN
      vRepite := 1;
    END IF;
    RETURN vRepite;
  END;
END;
/
