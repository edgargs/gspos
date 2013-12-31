--------------------------------------------------------
--  DDL for Package FARMA_GRAL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."FARMA_GRAL" AS

  /**
  * Copyright (c) 2006 MiFarma Peru S.A.
  *
  * Entorno de Desarrollo : Oracle9i
  * Nombre del Paquete    : FARMA_UTILITY
  *
  * Histórico de Creación/Modificación
  * RCASTRO       15.01.2006   Creación
  *
  * @author Rolando Castro
  * @version 1.0
  *
  */

  TYPE FarmaCursor IS REF CURSOR;

  g_cCodMotKardexCruceArticulo LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '03';
  C_COD_PROV_TELF_CLARO    CHAR(1) := 'F' ;
  C_COD_PROV_TELF_MOVISTAR CHAR(1) := 'A';
  C_COD_PROV_DIRECTV CHAR(3) := 'DTV';


  /*******************************************************************/

  FUNCTION GENERA_PEDIDO_SCRIPT(cCodGrupoCia_in IN CHAR,
						   	    cCodLocal_in   	IN CHAR,
								cNumPedVtaCopia_in   IN CHAR)
    RETURN CHAR;

  FUNCTION COBRA_PEDIDO_SCRIPT(cCodGrupoCia_in IN CHAR,
						   	   cCodLocal_in    IN CHAR,
							   cNumPedVta_in IN CHAR,
							   cNumPedVtaCopia_in   IN CHAR)
    RETURN CHAR;

  PROCEDURE GENERA_COBRA_PEDIDO_SCRIPT(cCodGrupoCia_in	IN CHAR,
									   cCodLocal_in  	IN CHAR,
									   cNumPedVtaCopia_in   IN CHAR);

  PROCEDURE CORRIGE_KARDEX(cCodGrupoCia_in IN CHAR,
									         cCodLocal_in  	 IN CHAR);


  --Descripcion: Crea guías de ingreso faltantes.
  --Fecha       Usuario		Comentario
  --23/06/2006  ERIOS     	Creación
  PROCEDURE CREA_GUIA_NOTA_E(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,vIdUsu_in IN VARCHAR2);

  --Descripcion: Actualiza el Numero de Guía en el Kardex. DEBE EJECUTARSE 2 VECES!
  --Fecha       Usuario		Comentario
  --23/06/2006  ERIOS     	Creación
  PROCEDURE CORRIGE_KARDEX_NOTA_ES(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,vIdUsu_in IN VARCHAR2);

  PROCEDURE  INV_ACTUALIZA_IND_LINEA (cIdUsuario_in IN CHAR,
                                      cCodLocal_in  IN CHAR,
                                      cCodGrupoCia_in IN CHAR);

  FUNCTION INV_OBTIENE_IND_LINEA(cCodLocal_in  IN CHAR,
                                  cCodGrupoCia_in IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene la informacion requerida para la impresion de un
  --pedido de Recarga Virtual.
  --Fecha       Usuario		Comentario
  --02/11/2007  DUBILLUZ  Creación
  --31/03/2008  DUBILLUZ  Modificacion
  FUNCTION CAJ_OBTIENE_MSJ_PROD_VIRT(cCodGrupoCia_in     IN CHAR,
                                     cCodLocal_in        IN CHAR,
                                     cNumPedido_in       IN CHAR,
                                     cCodProd            IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: Obtiene la informacion requerida para la impresion de un
  --pedido de Recarga Virtual.
  --Fecha       Usuario		Comentario
  --29/02/2008  DUBILLUZ  Creacion
  FUNCTION GET_MENSAJE_PERSONALIZADO(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR)

  RETURN VARCHAR2;


  --Descripcion:Obtiene la maximo longitud para el telefono de recarga
  --Fecha       Usuario		Comentario
  --26/03/2008  DUBILLUZ  Creacion
  FUNCTION GET_MAXIMO_LOG_NUMERO_TELF(cCodGrupoCia_in IN CHAR)
  RETURN varchar2;

  --Descripcion: Obtiene la informacion requerida para la impresion de un
  --pedido de Recarga Virtual.
  --Fecha       Usuario		Comentario
  --02/11/2007  DUBILLUZ  Creación
  FUNCTION GET_TIEMPO_MAX_ANULACION(cGrupoCia_in   IN CHAR,
                                    cCodLocal_in   IN CHAR,
                                    cNumPedVta_in  IN CHAR)
  RETURN CHAR;


  --Descripcion: Obtiene la descripcion del producto
  --Fecha       Usuario		Comentario
  --09/04/2008  DUBILLUZ  Creación
  FUNCTION GET_DESCRIPCION_PROD(cGrupoCia_in   IN CHAR,
                                cCodProd_in    IN CHAR)
  RETURN CHAR;

  --Descripcion: Obtiene Tipo del Pedido
  --Fecha       Usuario		Comentario
  --06/05/2008  DUBILLUZ  Creación
  FUNCTION GET_TIPO_PEDIDO(cGrupoCia_in   IN CHAR,
                           cCod_local     IN CHAR,
                           cNum_ped_Vta_in IN CHAR)
  RETURN CHAR;
  --Descripcion: Obtiene la descripcion de la forma de pago
  --Fecha       Usuario		Comentario
  --06/05/2008  DUBILLUZ  Creación
  FUNCTION GET_FORMAS_PAGO_PEDIDO(cGrupoCia_in   IN CHAR,
                                  cCod_local     IN CHAR,
                                  cNum_ped_Vta_in IN CHAR)
  RETURN CHAR;

  --Descripcion: Obtiene los codigos de los encartes aplicables
  --Fecha       Usuario		Comentario
  --06/05/2008  DUBILLUZ  Creación
  FUNCTION GET_ENCARTES_APLICABLES(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene el parametro para la busqueda especializada
  --Fecha       Usuario		Comentario
  --08/05/2008  DUBILLUZ  Creación
  FUNCTION GET_PARAM_FILTRO_ESPECIALIZADO(cGrupoCia_in   IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene el parametro para la busqueda especializada
  --Fecha       Usuario		Comentario
  --08/05/2008  DUBILLUZ  Creación
  FUNCTION GET_LONGITUD_MIN_FILTRO(cGrupoCia_in   IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene mesaje de login por usuario
  --Fecha       Usuario		Comentario
  --08/05/2008  DUBILLUZ  Creación
  FUNCTION GET_MSG_ROL_LOGIN(cGrupoCia_in  IN CHAR,
                             cCod_Rol_in   IN CHAR)
  RETURN VARCHAR2;


  FUNCTION GET_IND_CMM_ANTES_RECARGA
  RETURN VARCHAR2;

  FUNCTION GET_TIPO_IMPR_CONSEJO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene Cupones Aplicables
  --Fecha       Usuario		Comentario
  --04/07/2008  DUBILLUZ  Creación
  FUNCTION GET_CAMP_CUPO_APLICABLES(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cTipo_in        IN CHAR,
                                    cNumPed_in      IN CHAR)
                                    RETURN FarmaCursor;



  FUNCTION GET_IND_PEDIDO_REP_AUTOMATICO(cGrupoCia_in   IN CHAR,
                                         cCod_local     IN CHAR)
  RETURN CHAR;


  --Descripcion: Obtiene el listado de Modelo  de Impresoras
  --Fecha       Usuario		Comentario
  --04/11/2008  ASOLIS   Creacion
  FUNCTION GET_LISTA_MODELO_IMPRESORA
  RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de Formatos de impresion para guias
  --Fecha       Usuario		Comentario
  --13/0/2009  MFAJARDO   Creacion
  --20/12/2013  ERIOS       Se agrega los parametros cCodGrupoCia_in, cCodCia_in
  FUNCTION GET_LISTA_FORMATO_IMPRESION(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: VALIDA DESFASE
  --Fecha       Usuario		Comentario
  --27/11/2008  DUBILLUZ   Creacion
  FUNCTION F_EXISTE_DESFASE_COMP(
                                 cGrupoCia_in  IN CHAR,
                                 cCod_local_in IN CHAR,
                                 cFechaDia_in  IN CHAR
                                )
  RETURN CHAR;

  --Descripcion: VALIDA DELIVERY PENDIENTE
  --Fecha       Usuario		Comentario
  --28/11/2008  DUBILLUZ   Creacion
  FUNCTION F_EXISTE_DEL_PEND_SIN_REG(
                                 cGrupoCia_in  IN CHAR,
                                 cCod_local_in IN CHAR,
                                 cFechaDia_in  IN CHAR
                                )
  RETURN CHAR;

  FUNCTION F_EXISTE_ANUL_PED_PEND_SIN_REG(
                                 cGrupoCia_in  IN CHAR,
                                 cCod_local_in IN CHAR,
                                 cFechaDia_in  IN CHAR
                                )
  RETURN CHAR;

  --Descripcion: VALIDA SI EXISTEN PEDIDOS MANUALES NO REGULARIZADOS
  --Fecha       Usuario		Comentario
  --01/11/2008  DUBILLUZ   Creacion
  FUNCTION F_EXISTE_PED_MANUAL_SIN_REG(
                                 cGrupoCia_in  IN CHAR,
                                 cCod_local_in IN CHAR,
                                 cFechaDia_in  IN CHAR
                                )
  RETURN CHAR;



  FUNCTION F_IS_MOTIVO_CUADRATURA(
                                  cGrupoCia_in  IN CHAR,
                                  cCodCuadratura_in IN CHAR
                                  )
  RETURN CHAR;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 --Actualizacion: Se agrega el campo COD_CIA para obtener la imagen    Autor: Luigy Terrazos     Fecha: 01/03/2013
  FUNCTION F_PRUEBA_IMPR_TERMICA(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 cIpServ_in        IN CHAR,
                                 cCodCupon_in      IN CHAR,
                                 cCodCia_in in char
                                 )
 RETURN VARCHAR2;

--Descripcion : VALIDACION DIARIA PARA CAJA ELECTRONICA
--Fecha         Usuario Comentario
--11/12/2008   ASOLIS   Creacion
--12/12/2008   JOLIVA   Modificacion
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL);

--Descripcion : VALIDACION DIARIA PARA CAJA ELECTRONICA
--Fecha         Usuario Comentario
--09/12/2008   ASOLIS   CREACION

PROCEDURE P_VALIDACION_CAJA_ELECTRONICA(
                                 cGrupoCia_in  IN CHAR,
                                 cCod_local_in IN CHAR,
                                 cFechaDia_in  IN CHAR default null
                                );






  --Descripcion: Se obtiene el tipo de la impresora termica
  --Fecha       Usuario		Comentario
  --03/07/2009  JCHAVEZ    Creación
  FUNCTION GET_TIPO_IMPR_CONSEJO_X_IP(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN VARCHAR2;

FUNCTION F_CHAR_OBT_DIAS_ELIMINAR_TXT
RETURN CHAR;


  --Descripcion: Graba el inicio y fin de cobro
  --Fecha       Usuario		Comentario
  --09/07/2009  JCHAVEZ   Creación
  PROCEDURE CAJ_REGISTRA_TMP_INI_FIN_COBRO(cCodGrupoCia_in IN CHAR,
							  			                   cCod_Local_in   IN CHAR,
                                         cNum_Ped_Vta_in IN CHAR,
							  			                   cTmpTipo_in 	   IN CHAR);


 FUNCTION F_CHAR_GET_IND_IMP_COLOR
 RETURN CHAR;

  FUNCTION F_VAR2_GET_MENSAJE(cGrupoCia_in  IN CHAR,
                             cCod_Rol_in   IN CHAR)
  RETURN VARCHAR2;

 /* ************************************************************/
 --Descripcion: Busca los Destinatarios para enviar Email Error Cobro
 --Fecha     Usuario     Comentario
 --03/08/09  JMIRANDA    Creacion
 FUNCTION F_VAR2_GET_EMAIL_COBRO
 RETURN VARCHAR2;

  /* ************************************************************/
 --Descripcion: Busca los Destinatarios para enviar Email Error Anulacion
 --Fecha     Usuario     Comentario
 --03/08/09  JMIRANDA    Creacion
 FUNCTION F_VAR2_GET_EMAIL_ANULACION
 RETURN VARCHAR2;

  /* ************************************************************/
 --Descripcion: Busca los Destinatarios para enviar Email Error Impresion
 --Fecha     Usuario     Comentario
 --03/08/09  JMIRANDA    Creacion
 FUNCTION F_VAR2_GET_EMAIL_IMPRESION
 RETURN VARCHAR2;

  /* ************************************************************/
 --Descripcion: Obtiene el ind stock locales
 --Fecha     Usuario     Comentario
 --25/08 /09  dubilluz    Creacion
 FUNCTION F_VAR2_GET_IND_VER_STOCK
 RETURN VARCHAR2;

  /**********************************************************************************************/
  --Descripcion: Se obtiene indicador para mostrar la opción "VER CUPONES" en el resumen de pedido
  --Fecha       Usuario		    Comentario
  --08/10/2009  JCHAVEZ     	Creación
  FUNCTION F_GET_CHAR_IND_VER_CUPONES RETURN CHAR;

  /**********************************************************************************************/
  --Descripcion: Se obtiene indicador para mostrar la opción "Pedido Delivery" en el resumen de pedido
  --Fecha       Usuario		    Comentario
  --08/10/2009  JCHAVEZ     	Creación
  FUNCTION F_GET_CHAR_IND_VER_PED_DELIV RETURN CHAR;

  /**********************************************************************************************/
  --Descripcion: Se imprime sticker en impresora termica.
  --Fecha       Usuario		    Comentario
  --27/12/2010  JQUISPE     	Creación
  --Actualizacion: Se agrega el campo COD_CIA para obtener la imagen    Autor: Luigy Terrazos     Fecha: 01/03/2013 C_COD_CIA in char
   FUNCTION F_IMPR_TERMICA_STICK(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 cIpServ_in        IN CHAR,
                                 cCodCupon_in      IN CHAR,
                                 cInicio_in      IN CHAR,
                                 cFin_in      IN CHAR,
                                 cCodCia_in in char
                                 )
  RETURN VARCHAR2;

  --Descripcion: Busca producto regalo del encarte segun el monto
  --Fecha       Usuario		    Comentario
  --05/12/2011  JQUISPE     	Creación
  FUNCTION F_EMITE_REGALO_X_MONTO(cCodGrupoCia_in char,
                                  cCodLocal_in char,
                                  cCodEncarte_in  char,
                                  cMonto_in       varchar2)
  RETURN FARMACURSOR;

  /* ************************************************************/
  --Descripcion: Obtiene el ind stock locales
  --Fecha     Usuario     Comentario
  --25/08 /09  dubilluz    Creacion
  --20/12/2013 ERIOS      Se agrega el parametro cCodCia_in
  FUNCTION F_VAR_GET_IND_VER_RECETARIO(cCodCia_in IN CHAR)
  RETURN VARCHAR2;
  
  /*************************************************************/
  --Descripcion: Obtiene el codigo de barras de un producto
  --Fecha     Usuario    Comentario
  --09/09/13  mgrasso    Creacion
  FUNCTION F_VAR_GET_COD_BARRAS(cCodProd_in char)
  RETURN VARCHAR2;                        
 
  /**********************************************************************************************/
  --Descripcion: Se imprime varios stickers en la impresora térmica.
  --Fecha       Usuario		    Comentario
  --20/09/2013  CVILCA      	Creación
   FUNCTION F_IMPR_TERMICA_STICKER_PROD(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       vCodigos          IN VARCHAR2)
  RETURN FarmaCursor;
 /**********************************************************************************************/
  --Descripcion: Obtiene los datos básicos de los productos
  --Fecha       Usuario		    Comentario
  --01/10/2013  CVILCA      	Creación
   FUNCTION F_GET_PRODUCTOS(cCodGrupoCia   IN CHAR,
                            cCodLocal      IN CHAR,
                            vDescripcion   IN VARCHAR2)
   RETURN FarmaCursor;
   
   /**********************************************************************************************/
  --Descripcion: Obtiene el código EPL para el sticker de un producto
  --Fecha       Usuario		    Comentario
  --02/10/2013  CVILCA      	Creación
   
   FUNCTION F_VAR_GET_EPL_STICKER(cCodGrupoCia IN CHAR,
                                  cCodLocal    IN CHAR,
                                  cCodProd     IN CHAR)
   RETURN VARCHAR2;
   
   /**********************************************************************************************/
  --Descripcion: Obtiene los datos de la forma de pago de un pedido
  --Fecha       Usuario		    Comentario
  --28/10/2013  CVILCA      	Creación
   FUNCTION F_GET_FORMAS_PAGO_TICKET(cCodGrupoCia   IN CHAR,
                                    cCodCia         IN CHAR,
                                    cCodLocal       IN CHAR,
                                    vNumPedVta      IN VARCHAR2)
   RETURN FarmaCursor;
   /**********************************************************************************************/
  --Descripcion: Busca los datos basicos del producto por Codigo de Barra
  --Fecha       Usuario		    Comentario
  --25/11/2013  CHUANES      	Creación
   
    FUNCTION F_GET_PRODUC_COD_BARRA(cCodGrupoCia IN CHAR, cCodLocal IN CHAR,  cCodBarra IN CHAR )
     
   RETURN FarmaCursor;
   
END Farma_Gral;

/
