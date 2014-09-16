--------------------------------------------------------
--  DDL for Package PTOVENTA_ADMIN_CAJA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_ADMIN_CAJA" AS
	  TYPE FarmaCursor IS REF CURSOR;
	  /*************************************************************/
	  TIPO_DOC_BOLETA   	  VTA_TIP_COMP.TIP_COMP%TYPE := '01';
	  TIPO_DOC_FACTURA   	  VTA_TIP_COMP.TIP_COMP%TYPE := '02';
	  TIPO_DOC_GUIA   	  	  VTA_TIP_COMP.TIP_COMP%TYPE := '03';
    TIPO_DOC_TICKET  	  	  VTA_TIP_COMP.TIP_COMP%TYPE := '05';
	  COD_NUMERA_CAJA         PBL_NUMERA.COD_NUMERA%TYPE := '005';
	  TIPO_ROL_CAJERO   	  PBL_ROL.COD_ROL%TYPE 		 := '009';
	  ESTADO_ACTIVO		  	  CHAR(1):='A';
	  ESTADO_INACTIVO		  CHAR(1):='I';
	  INDICADOR_SI		  	  CHAR(1):='S';
	  POS_INICIO		      CHAR(1):='I';
	  /*************************************************************/

  --Descripcion: Obtiene el listado de las cajas
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación
  FUNCTION CAJ_LISTA_CAJAS(cCodGrupoCia_in IN CHAR,
  		   				   cCodLocal_in	   IN CHAR)
  RETURN FarmaCursor;
  /*************************************************************/

   --Descripcion: Obtiene el listado de los usuarios(Cajeros) del local
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación
  FUNCTION CAJ_LISTA_USUARIOS_LOCAL(cCodGrupoCia_in IN CHAR,
  		   				            cCodLocal_in	IN CHAR)
  RETURN FarmaCursor;
  /*************************************************************/

  --Descripcion: Ingresa una caja con un usuario asignado
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación

	PROCEDURE CAJ_INGRESA_CAJA(cCodGrupoCia_in IN CHAR,
		                       cCodLocal_in    IN CHAR,
						       cSecUsuLocal_in IN CHAR,
						       cDescCaja_in    IN CHAR,
						       cCodUsu_in      IN CHAR);
  /*************************************************************/

  --Descripcion: Modifica una caja
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación

	PROCEDURE CAJ_MODIFICA_CAJA(cCodGrupoCia_in IN CHAR,
		                        cCodLocal_in    IN CHAR,
						        nNumCajaPago_in IN NUMBER,
						   	    cSecUsuLocal_in IN CHAR,
						   	    cDescCaja_in    IN CHAR,
						   	    cCodUsu_in      IN CHAR);

   /*************************************************************/
   --Descripcion: Cambia el estado de una caja
   --Fecha       Usuario		Comentario
   --14/02/2006  MHUAYTA     Creación

   PROCEDURE CAJ_CAMBIAESTADO_CAJA(cCodGrupoCia_in IN CHAR,
		  					       cCodLocal_in    IN CHAR,
							       nNumCajaPago_in IN NUMBER,
								   cCodUsu_in      IN CHAR);

	/*************************************************************/
	--Descripcion: Inserta una asociacion Caja-Usuario
    --Fecha       Usuario		Comentario
    --14/02/2006  MHUAYTA     Creación

   PROCEDURE CAJ_INSERTA_ASOCIA_CAJA(cCodGrupoCia_in IN CHAR,
		  					         cCodLocal_in    IN CHAR,
							     	 nNumCajaPago_in IN NUMBER,
								 	 cCodUsu_in      IN CHAR
								 );
	/*************************************************************/
    --Descripcion: Obtiene los datos de un usuario
    --Fecha       Usuario		Comentario
    --14/02/2006  MHUAYTA     Creación

   FUNCTION CAJ_OBTENER_DATOS_USU_LOCAL(cCodGrupoCia_in IN CHAR,
  		   				                cCodLocal_in	IN CHAR,
							      		cSecUsuLocal_in IN CHAR)
   RETURN FarmaCursor;

   /*************************************************************/
   --Descripcion: Verifica si existen impresoras de los tipos requeridos para una caja
    --Fecha       Usuario		Comentario
    --14/02/2006  MHUAYTA     Creación
   FUNCTION CAJ_VERIF_EXISTEN_IMPR_DISP(cCodGrupoCia_in IN CHAR,
  		   						        cCodLocal_in    IN CHAR)
   RETURN  NUMBER;

   /*************************************************************/
    --Descripcion:Obtiene las impresoras de una caja
    --Fecha       Usuario		Comentario
    --14/02/2006  MHUAYTA     Creación
    FUNCTION CAJ_LISTA_IMPRESORAS_CAJA(cCodGrupoCia_in IN CHAR,
  		   			   		           cCodLocal_in	   IN CHAR,
							           cNumCaja_in     IN CHAR)
    RETURN FarmaCursor;

   /*************************************************************/
    --Descripcion:Muestra una lista de impresoras de reemplazo para una caja
    --Fecha       Usuario		Comentario
    --29/03/2006  MHUAYTA     Creación
	FUNCTION CAJ_LISTA_IMPRESORAS_REEMPLAZO(cCodGrupoCia_in   IN CHAR,
  		   			   		                cCodLocal_in	  IN CHAR,
							     			cNumCaja_in       IN CHAR,
							     			cTipComp_in 	  IN CHAR)
    RETURN FarmaCursor;

   /*************************************************************/
    --Descripcion:Modifica la relación Caja-Impresora
    --Fecha       Usuario		Comentario
    --29/03/2006  MHUAYTA     Creación
     PROCEDURE CAJ_MODIFICA_CAJA_IMPRESORA(cCodGrupoCia_in   IN CHAR,
                                            cCodLocal_in      IN CHAR,
                                            nNumCajaPago_in   IN NUMBER,
                                            cSecImprLocal1_in IN CHAR,
                                            cSecImprLocal2_in IN CHAR,
                                            cCodUsu_in        IN CHAR);

	/*************************************************************/
    --Descripcion:Obtiene la cantidad de cajeros disponibles en un local
    --Fecha       Usuario		Comentario
    --29/03/2006  MHUAYTA     Creación
   FUNCTION CAJ_CANT_CAJEROS_DISP_LOCAL(cCodGrupoCia_in IN CHAR,
  		   				       	        cCodLocal_in	IN CHAR)
   RETURN NUMBER;
  /*************************************************************/
    --Descripcion:Obtiene el estado de la caja
    --Fecha       Usuario		Comentario
    --11/07/2007  DUBILLUZ     Creación
   FUNCTION CAJ_OBTIENE_ESTADO_CAJA(cCodGrupoCia_in  IN CHAR,
  		   				       		        cCodLocal_in	   IN CHAR,
                                  nNumCajaPago_in  IN NUMBER)

    RETURN VARCHAR;


END;

/
