CREATE OR REPLACE PACKAGE PTOVENTA."FARMA_GRAL" AS

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
    C_COD_PROV_TELF_ENTEL CHAR(1) := 'N'; --ASOSA - 01/12/2014 - RECAR - NEX
    ESTADO_ACTIVO CHAR(1) := '1';

    --INI ASOSA - 24/06/2015 - IGVAZNIA
    COD_IGV_CERO CHAR(3) := '00 ';
    COD_IGV_18 CHAR(3) := '01 ';
    COD_CIA_AMAZONIA CHAR(3) := '004';
    --FIN ASOSA - 24/06/2015 - IGVAZNIA
    
    --KMONCADA
    IND_APLICA_CAMPANA_FID_NO_PTOS INTEGER := 546;
    IND_APLICA_CAMPANA_AUTOMATICAS INTEGER := 544;
    

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
  FUNCTION GET_ENCARTES_APLICABLES(cCodGrupoCia_in    IN CHAR,
                                   cCodLocal_in       IN CHAR,
                                   vIsFidelizado_in   IN CHAR DEFAULT 'N')
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
  --07/08/2014  ERIOS       Se agregan parametros de local
  FUNCTION GET_MSG_ROL_LOGIN(cCodGrupoCia_in IN CHAR,
                             cCod_Rol_in   IN CHAR,
							cCodCia_in IN CHAR DEFAULT NULL,
							cCodLocal_in IN CHAR DEFAULT NULL)
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

   /**********************************************************************************************/
   FUNCTION F_VAR_ES_PEDIDO_ANTIGUO(cCodGrupoCia IN CHAR,
                                    cCodLocal    IN CHAR,
                                    cFechaPedido IN CHAR,
                                    cTipComp     IN CHAR,
                                    cNumCmpr     IN CHAR,
                                    cMontoCmpr   IN CHAR
                                  )
   RETURN VARCHAR2;

   /*
   Descripcion:   Obtiene mensaje a imprimir en voucher de confirmacion de numero de recarga para el cliente
   Fecha:         03/07/2014
   Usurio:        ASOSA
   Comentario:    Creación
   */
  FUNCTION F_IMPR_VOU_VERIF_RECARGA(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 cIpServ_in        IN CHAR,
--                                 cCodCupon_in      IN CHAR,
                                 cCodCia_in in char,
                                 vDescProducto IN VARCHAR2,
                                 vNumTelefono  IN VARCHAR2,
                                 vMonto        IN VARCHAR2
                                 )
 RETURN FARMACURSOR;

  FUNCTION F_VAR_GET_FARMA_EMAIL(vCodFarmaEmail_in PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE)
  RETURN VARCHAR2;


  /*
   Descripcion:   Configuracion de los filtros para listado de productos
   USUARIO        FECHA         COMENTARIO
   KMONCADA       13.01.2015    CREACION
   */
  FUNCTION GET_FILTRO_PRODUCTO
  RETURN FARMACURSOR;
  
    /*
   Descripcion:   Obtener los datos del maestro detalle
   USUARIO        FECHA         COMENTARIO
   LTAVARA       25.02.2015    CREACION
   */


 FUNCTION GET_MAESTRO_DETALLE(cCodMaestro IN VARCHAR,
                                cValor1    IN VARCHAR)
  RETURN VARCHAR2;

    /*
   Descripcion:   Obtener la lista de datos -  maestro detalle
   USUARIO        FECHA         COMENTARIO
   LTAVARA       02.03.2015    CREACION
   */
  FUNCTION GET_MAESTRO_DETALLE_LISTA(codMaestro in number)
 RETURN FarmaCursor;
 
   /**********************************************************************************************/
  --Descripcion: Obtiene valor igv para el local
  --Fecha       Usuario		    Comentario       TYPE
  --24/06/2015  ASOSA       	Creación         IGVAZNIA
     FUNCTION F_GET_IGV_LOCAL
    RETURN number;

   /**********************************************************************************************/

  --Descripcion: Obtiene un igv especifico a demanda
  --Fecha       Usuario		    Comentario       TYPE
  --24/06/2015  ASOSA       	Creación         IGVAZNIA   
   FUNCTION F_GET_ESPECIF_IGV_LOCAL(cCodigoIgv_In CHAR)
    RETURN number;
    
   /**********************************************************************************************/

  --Descripcion: INDICADOR DE APLICAR CAMPAÑAS AUTOMATICAS AL PEDIDO
  --Fecha       Usuario		    Comentario       
  --13/08/2015  KMONCADA      Creación         
   FUNCTION F_GET_IND_CAMP_AUTOMATICAS
      RETURN CHAR;
   
   /**********************************************************************************************/

  --Descripcion: INDICADOR DE APLICAR CAMPAÑAS Y ENCARTES A FIDELIZADOS NO MONEDERO
  --Fecha       Usuario		    Comentario       
  --13/08/2015  KMONCADA      Creación     
   FUNCTION F_GET_APLICA_PROM_FID_NO_PTOS
      RETURN CHAR;
      
   /**********************************************************************************************/
  --Descripcion: CANTIDAD DE DIGITOS A SOLICITAR PARA PAGOS CON TARJETAS
  --Fecha       Usuario		    Comentario       
  --08/09/2015  KMONCADA      Creación        
   FUNCTION F_GET_CANT_DIGT_TARJ_SOLICITA
      RETURN NUMBER;
   
   /**********************************************************************************************/
  --Descripcion: INDICADOR DE USO DE NUEVA VENTANA DE COBRO CON TARJETA POS
  --Fecha       Usuario		    Comentario       
  --08/09/2015  KMONCADA      Creación        
   FUNCTION F_GET_NVA_VENTANA_DATO_TARJ
      RETURN CHAR;

END Farma_Gral;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."FARMA_GRAL" IS


  /**
  * Copyright (c) 2006 MiFarma Peru S.A.
  *
  * Entorno de Desarrollo : Oracle9i
  * Nombre del Paquete    : FARMA_GRAL
  *
  * Histórico de Creación/Modificación
  * LMESIA       25.04.2006   Creación
  *
  * @author Luis Mesia Rivera
  * @version 1.0
  *
  */

  /************************************************************************************/

  FUNCTION GENERA_PEDIDO_SCRIPT(cCodGrupoCia_in IN CHAR,
				cCodLocal_in   	IN CHAR,
				cNumPedVtaCopia_in   IN CHAR)
    RETURN CHAR IS
    v_nNumPedVta VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
	v_nNumPedDiario VTA_PEDIDO_VTA_CAB.NUM_PED_DIARIO%TYPE;
  	CURSOR curCabecera IS
		   			   SELECT *
					   FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
					   WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
					   AND	  VTA_CAB.COD_LOCAL = cCodLocal_in
					   AND	  VTA_CAB.NUM_PED_VTA = cNumPedVtaCopia_in;

	CURSOR curDetalle IS
		   			   SELECT *
					   FROM   VTA_PEDIDO_VTA_DET VTA_DET
					   WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
					   AND	  VTA_DET.COD_LOCAL = cCodLocal_in
					   AND	  VTA_DET.NUM_PED_VTA = cNumPedVtaCopia_in;

  BEGIN
   	  v_nNumPedVta := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, '007');
	  v_nNumPedVta := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNumPedVta, 10, 0, 'I');

	  v_nNumPedDiario := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, '009');
	  v_nNumPedDiario := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNumPedDiario, 4, 0, 'I');

 	  FOR cabecera_rec IN curCabecera
	  LOOP
	  	  --dbms_output.put_line('1 Diario '|| v_nNumPedDiario);
	  	  --dbms_output.put_line('1 Vta '|| v_nNumPedVta);
	  	  Ptoventa_Vta.VTA_GRABAR_PEDIDO_VTA_CAB(cCodGrupoCia_in,
		                                   	  	 cCodLocal_in,
										   	  	 v_nNumPedVta,
											  	 cabecera_rec.COD_CLI_LOCAL,
											  	 '',
										   	  	 cabecera_rec.VAL_BRUTO_PED_VTA,
										   	  	 cabecera_rec.VAL_NETO_PED_VTA,
										   	  	 cabecera_rec.VAL_REDONDEO_PED_VTA,
											  	 cabecera_rec.VAL_IGV_PED_VTA,
											  	 cabecera_rec.VAL_DCTO_PED_VTA,
											  	 cabecera_rec.TIP_PED_VTA,
											  	 cabecera_rec.VAL_TIP_CAMBIO_PED_VTA,
											  	 v_nNumPedDiario,
											  	 cabecera_rec.CANT_ITEMS_PED_VTA,
											  	 cabecera_rec.EST_PED_VTA,
											  	 cabecera_rec.TIP_COMP_PAGO,
											  	 cabecera_rec.NOM_CLI_PED_VTA,
											  	 cabecera_rec.DIR_CLI_PED_VTA,
											  	 cabecera_rec.RUC_CLI_PED_VTA,
											  	 'JOLIVA',
											  	 cabecera_rec.IND_DISTR_GRATUITA,
                           CABECERA_REC.IND_PED_CONVENIO);
	  END LOOP;
	  FOR detalle_rec IN curDetalle
	  LOOP


	  	  --dbms_output.put_line('2');
	  	  Ptoventa_Vta.VTA_GRABAR_PEDIDO_VTA_DET(cCodGrupoCia_in,
			                                   	 cCodLocal_in,
											   	 v_nNumPedVta,
											   	 detalle_rec.SEC_PED_VTA_DET,
												 detalle_rec.COD_PROD,
												 detalle_rec.CANT_ATENDIDA,
												 detalle_rec.VAL_PREC_VTA,
											   	 detalle_rec.VAL_PREC_TOTAL,
											   	 detalle_rec.PORC_DCTO_1,
												 detalle_rec.PORC_DCTO_2,
												 detalle_rec.PORC_DCTO_3,
												 detalle_rec.PORC_DCTO_TOTAL,
												 detalle_rec.EST_PED_VTA_DET,
												 detalle_rec.VAL_TOTAL_BONO,
												 detalle_rec.VAL_FRAC,
												 '',
												 detalle_rec.SEC_USU_LOCAL,
												 detalle_rec.VAL_PREC_LISTA,
											   	 detalle_rec.VAL_IGV,
												 detalle_rec.UNID_VTA,
                         '',
												 'JOLIVA',
                         detalle_rec.Val_Prec_Public,
                         NULL,
                         NULL,
                         NULL,
                         NULL);
	  END LOOP;

	  Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, '007', 'JOLIVA');
	  Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, '009', 'JOLIVA');

	  RETURN v_nNumPedVta;
  END;

FUNCTION COBRA_PEDIDO_SCRIPT(cCodGrupoCia_in IN CHAR,
						   	   cCodLocal_in    IN CHAR,
							   cNumPedVta_in   IN CHAR,
							   cNumPedVtaCopia_in IN CHAR)
    RETURN CHAR IS

	v_nSecGrupoImpr NUMBER;
	v_cNumComp 		CHAR(10);
	v_cSecComp 		CHAR(10);
  v_Res         CHAR(10);
  	CURSOR curFormaPago IS
		   				SELECT *
						FROM   VTA_FORMA_PAGO_PEDIDO PAGO_PEDIDO
						WHERE  PAGO_PEDIDO.COD_GRUPO_CIA = cCodGrupoCia_in
						AND	   PAGO_PEDIDO.COD_LOCAL = cCodLocal_in
						AND	   PAGO_PEDIDO.NUM_PED_VTA = cNumPedVtaCopia_in;

  BEGIN
   	  --dbms_output.put_line('3 ' || cNumPedVta_in);
	  v_nSecGrupoImpr := Ptoventa_Caj.CAJ_AGRUPA_IMPRESION_DETALLE(cCodGrupoCia_in,
						   	   		 							   cCodLocal_in,
																   cNumPedVta_in,
																   8,
                                   'SIST');

 	  FOR formapago_rec IN curFormaPago
	  LOOP
	  	  --dbms_output.put_line('4');
	  	  Ptoventa_Caj.CAJ_GRABAR_FORMA_PAGO_PEDIDO(cCodGrupoCia_in,
				                                   	      cCodLocal_in,
													                        formapago_rec.COD_FORMA_PAGO,
												   	                      cNumPedVta_in,
												   	                      formapago_rec.IM_PAGO,
													                        formapago_rec.TIP_MONEDA,
												  	                      formapago_rec.VAL_TIP_CAMBIO,
											   	  	                    formapago_rec.VAL_VUELTO,
											   	  	                    formapago_rec.IM_TOTAL_PAGO,
												  	                      formapago_rec.NUM_TARJ,
												  	                      formapago_rec.FEC_VENC_TARJ,
												  	                      formapago_rec.NOM_TARJ,
                                                  formapago_rec.CANT_CUPON,
												  	                      'JOLIVA');
	  END LOOP;
	  --dbms_output.put_line('5');
	  v_Res := Ptoventa_Caj.CAJ_COBRA_PEDIDO(cCodGrupoCia_in,
								   	cCodLocal_in,
									cNumPedVta_in,
									'0000000009',
									'015',
									'01',
									'001',
								   	'01',
									'016',
									'JOLIVA');

	   SELECT NVL(VTA_DET.SEC_COMP_PAGO,' ')
	   INTO	  v_cSecComp
	   FROM   VTA_PEDIDO_VTA_DET VTA_DET
	   WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
	   AND	  VTA_DET.COD_LOCAL = cCodLocal_in
	   AND	  VTA_DET.NUM_PED_VTA = cNumPedVta_in
	   AND	  VTA_DET.SEC_GRUPO_IMPR <> 0
	   GROUP BY VTA_DET.SEC_GRUPO_IMPR, VTA_DET.SEC_COMP_PAGO
	   ORDER BY VTA_DET.SEC_GRUPO_IMPR;

	  SELECT IMPR_LOCAL.NUM_SERIE_LOCAL || '' ||
		   	 IMPR_LOCAL.NUM_COMP
	  INTO	 v_cNumComp
	  FROM   VTA_IMPR_LOCAL IMPR_LOCAL
	  WHERE  IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
	  AND	 IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
	  AND	 IMPR_LOCAL.SEC_IMPR_LOCAL = 1 FOR UPDATE;

	  --dbms_output.put_line('6');
	  Ptoventa_Caj.CAJ_ACTUALIZA_COMPROBANTE_IMPR(cCodGrupoCia_in,
		  		   						   	   	  cCodLocal_in,
											   	  cNumPedVta_in,
												  v_cSecComp,
												  '01',
												  v_cNumComp,
												  'JOLIVA');

	  --dbms_output.put_line('7');
	  Ptoventa_Caj.CAJ_ACTUALIZA_IMPR_NUM_COMP(cCodGrupoCia_in,
  		   						   	   		   cCodLocal_in,
											   1,
											   'JOLIVA');

	  --dbms_output.put_line('8');
	  Ptoventa_Caj.CAJ_ACTUALIZA_ESTADO_PEDIDO(cCodGrupoCia_in,
  		   						   	   		   cCodLocal_in,
											   cNumPedVta_in,
											   'C',
											   'JOLIVA');

	  RETURN cNumPedVta_in;
  END;


  PROCEDURE GENERA_COBRA_PEDIDO_SCRIPT(cCodGrupoCia_in	IN CHAR,
									   cCodLocal_in  	IN CHAR,
									   cNumPedVtaCopia_in   IN CHAR) IS
  v_cNumPedVta 		CHAR(10);
  v_cResultado 		CHAR(10);
  BEGIN
  	   v_cNumPedVta := GENERA_PEDIDO_SCRIPT(cCodGrupoCia_in,
	   										cCodLocal_in,
											cNumPedVtaCopia_in);

	   v_cResultado := COBRA_PEDIDO_SCRIPT(cCodGrupoCia_in,
									   	   cCodLocal_in,
										   v_cNumPedVta,
										   cNumPedVtaCopia_in);

		COMMIT;
	    dbms_output.put_line('PEDIDO ' || TRIM(v_cResultado) || ' GENERADO Y COBRADO' );

  EXCEPTION
  		WHEN OTHERS THEN
			 dbms_output.put_line('ERROR AL GENERAR Y COBRAR PEDIDO' ||  SQLERRM);
			 ROLLBACK;
  END;



  PROCEDURE CORRIGE_KARDEX(cCodGrupoCia_in IN CHAR,
									         cCodLocal_in  	 IN CHAR) IS
  v_nCantAMover NUMBER;
  v_nCantCorrecta NUMBER;
  v_nValorFraccionErroneo NUMBER;
  v_nValorFraccionCorrecto NUMBER;

  CURSOR curDetallePed IS
		   	SELECT VTA_DET.COD_PROD,KARDEX.SEC_KARDEX, COMP_PAGO.TIP_COMP_PAGO, COMP_PAGO.NUM_COMP_PAGO
        FROM   VTA_PEDIDO_VTA_DET VTA_DET,
               LGT_KARDEX KARDEX,
               VTA_COMP_PAGO COMP_PAGO
        WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    VTA_DET.COD_LOCAL = cCodLocal_in
        --AND    VTA_DET.NUM_PED_VTA = '0000000079'
        AND    VTA_DET.COD_GRUPO_CIA = KARDEX.COD_GRUPO_CIA
        AND    VTA_DET.COD_LOCAL = KARDEX.COD_LOCAL
        AND    VTA_DET.NUM_PED_VTA = KARDEX.NUM_TIP_DOC
        AND    VTA_DET.COD_PROD = KARDEX.COD_PROD
        AND    VTA_DET.COD_GRUPO_CIA = COMP_PAGO.COD_GRUPO_CIA
        AND    VTA_DET.COD_LOCAL = COMP_PAGO.COD_LOCAL
        AND    VTA_DET.SEC_COMP_PAGO = COMP_PAGO.SEC_COMP_PAGO;

  BEGIN
       FOR curDetallePed_REC IN curDetallePed
       LOOP
           UPDATE LGT_KARDEX K
                  SET K.TIP_COMP_PAGO = curDetallePed_REC.TIP_COMP_PAGO,
                      K.NUM_COMP_PAGO = curDetallePed_REC.NUM_COMP_PAGO,
                      K.USU_MOD_KARDEX = 'OPER',
                      K.FEC_MOD_KARDEX = SYSDATE
                WHERE K.COD_GRUPO_CIA = cCodGrupoCia_in
                AND   K.COD_LOCAL = cCodLocal_in
                AND   K.SEC_KARDEX = curDetallePed_REC.SEC_KARDEX
                AND   K.COD_PROD = curDetallePed_REC.COD_PROD
                AND   K.TIP_DOC_KARDEX = '01';
       END LOOP;
  END;

  /****************************************************************************/
  PROCEDURE CREA_GUIA_NOTA_E(cCodGrupoCia_in IN CHAR,cCodLocal_in  	 IN CHAR,vIdUsu_in IN VARCHAR2)
  AS
    CURSOR curGuias IS
    SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,NUM_DOC,vIdUsu_in AS USU
    FROM LGT_NOTA_ES_CAB
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_DOC IS NOT NULL
          AND TIP_ORIGEN_NOTA_ES IN ('01','02')
          AND NUM_NOTA_ES IN
                        (
                        SELECT DISTINCT NUM_TIP_DOC
                        FROM LGT_KARDEX
                        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                              AND COD_LOCAL = cCodLocal_in
                              AND TIP_DOC_KARDEX = '02'
                              AND TIP_COMP_PAGO IS NULL
                              AND NUM_COMP_PAGO IS NULL
                        MINUS
                        SELECT DISTINCT NUM_NOTA_ES
                        FROM LGT_GUIA_REM
                        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                              AND COD_LOCAL = cCodLocal_in
                              AND NUM_GUIA_REM IS NOT NULL
                              AND NUM_NOTA_ES IN (
                                        SELECT DISTINCT NUM_TIP_DOC
                                        FROM LGT_KARDEX
                                        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                              AND COD_LOCAL = cCodLocal_in
                                              AND TIP_DOC_KARDEX = '02'
                                              AND TIP_COMP_PAGO IS NULL
                                              AND NUM_COMP_PAGO IS NULL)
                          );
    v_rCurGuia curGuias%ROWTYPE;
  BEGIN
    FOR v_rCurGuia IN curGuias
    LOOP
      --CREA GUIA (SOLO SE CREARA PARA LAS GUIAS DE INGRESO)
      --DBMS_OUTPUT.PUT_LINE(v_rCurGuia.NUM_DOC);
      INSERT INTO LGT_GUIA_REM(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM,
                              NUM_GUIA_REM,USU_CREA_GUIA_REM,FEC_CREA_GUIA_REM)
      VALUES (v_rCurGuia.COD_GRUPO_CIA,v_rCurGuia.COD_LOCAL,v_rCurGuia.NUM_NOTA_ES,1,v_rCurGuia.NUM_DOC,v_rCurGuia.USU,SYSDATE);
      --ACTUALIZA DET DE GUIA
      UPDATE LGT_NOTA_ES_DET
      SET USU_MOD_NOTA_ES_DET = v_rCurGuia.USU , FEC_MOD_NOTA_ES_DET = SYSDATE,
         SEC_GUIA_REM = 1
      WHERE COD_GRUPO_CIA = v_rCurGuia.COD_GRUPO_CIA
            AND COD_LOCAL = v_rCurGuia.COD_LOCAL
            AND NUM_NOTA_ES = v_rCurGuia.NUM_NOTA_ES;
    END LOOP;
  END;
  /****************************************************************************/

  PROCEDURE CORRIGE_KARDEX_NOTA_ES(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,vIdUsu_in IN VARCHAR2)
  AS
    CURSOR curDocs IS
    SELECT DISTINCT D.COD_GRUPO_CIA,D.COD_LOCAL,D.NUM_NOTA_ES,D.SEC_GUIA_REM,D.COD_PROD,C.TIP_ORIGEN_NOTA_ES, vIdUsu_in AS USU, D.CANT_MOV,
          DECODE(C.TIP_ORIGEN_NOTA_ES,'01','03','02','03',C.TIP_DOC) AS TIP_DOC,
          DECODE(C.TIP_ORIGEN_NOTA_ES,'01',(SELECT A.NUM_GUIA_REM
                                            FROM   LGT_GUIA_REM A
                                            WHERE  A.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                                  AND A.COD_LOCAL = D.COD_LOCAL
                                                  AND A.NUM_NOTA_ES = D.NUM_NOTA_ES
                                                  AND A.SEC_GUIA_REM = D.SEC_GUIA_REM),
                                      '02',(SELECT A.NUM_GUIA_REM
                                            FROM   LGT_GUIA_REM A
                                            WHERE  A.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                                  AND A.COD_LOCAL = D.COD_LOCAL
                                                  AND A.NUM_NOTA_ES = D.NUM_NOTA_ES
                                                  AND A.SEC_GUIA_REM = D.SEC_GUIA_REM),C.NUM_DOC) AS NUM_DOC
    FROM LGT_NOTA_ES_DET D, LGT_NOTA_ES_CAB C, LGT_KARDEX K
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          --AND D.COD_PROD = '137100'
          AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND D.COD_LOCAL = C.COD_LOCAL
          AND D.NUM_NOTA_ES = C.NUM_NOTA_ES
          AND D.COD_GRUPO_CIA = K.COD_GRUPO_CIA
          AND D.COD_LOCAL = K.COD_LOCAL
          AND D.NUM_NOTA_ES = K.NUM_TIP_DOC
          AND D.COD_PROD = K.COD_PROD
          AND K.TIP_DOC_KARDEX = '02'
          AND K.TIP_COMP_PAGO IS NULL
          AND K.NUM_COMP_PAGO IS NULL
          --AND (D.CANT_MOV = K.CANT_MOV_PROD OR D.CANT_ENVIADA_MATR = K.CANT_MOV_PROD)
    ORDER BY 10,9;
    v_rCurDoc curDocs%ROWTYPE;
  BEGIN
    --ACTUALIZA EL KARDEX CON EL NUM_GUIA
    FOR v_rCurDoc IN curDocs
    LOOP
      --PTOVENTA_INV.INV_ACT_KARDEX_GUIA_REC(v_rCurDoc.COD_GRUPO_CIA,v_rCurDoc.COD_LOCAL,v_rCurDoc.NUM_NOTA_ES,v_rCurDoc.SEC_GUIA_REM,v_rCurDoc.COD_PROD,v_rCurDoc.USU,v_rCurDoc.TIP_ORIGEN_NOTA_ES);

      UPDATE LGT_KARDEX K SET K.USU_MOD_KARDEX = v_rCurDoc.USU, K.FEC_MOD_KARDEX = SYSDATE,
                K.TIP_COMP_PAGO = v_rCurDoc.TIP_DOC,
                K.NUM_COMP_PAGO = v_rCurDoc.NUM_DOC
           WHERE COD_GRUPO_CIA = v_rCurDoc.COD_GRUPO_CIA
                AND   COD_LOCAL = v_rCurDoc.COD_LOCAL
                AND   COD_PROD = v_rCurDoc.COD_PROD
                AND NUM_TIP_DOC = v_rCurDoc.NUM_NOTA_ES
                AND TIP_COMP_PAGO IS NULL
                AND NUM_COMP_PAGO IS NULL
                AND ROWNUM = 1
                ;
    END LOOP;
  END;

  PROCEDURE  INV_ACTUALIZA_IND_LINEA (cIdUsuario_in IN CHAR,
                                      cCodLocal_in  IN CHAR,
                                      cCodGrupoCia_in IN CHAR)
--  RETURN VARCHAR2
  IS
--   v_valor VARCHAR2(3);
   v_time_1 TIMESTAMP;
   v_IndLinea CHAR(1);
   v_time_2 TIMESTAMP;
   V_RESULT INTERVAL DAY TO SECOND;
   V_TIME_ESTIMADO CHAR(20);

  BEGIN
        SELECT IND_EN_LINEA INTO v_IndLinea
        FROM   PBL_LOCAL
        WHERE  COD_LOCAL = cCodLocal_in
        AND    COD_GRUPO_CIA = cCodGrupoCia_in ;

        SELECT LLAVE_TAB_GRAL INTO V_TIME_ESTIMADO
        FROM   PBL_TAB_GRAL
        WHERE  ID_TAB_GRAL = 76
        AND    COD_APL = 'PTO_VENTA'
        AND    COD_TAB_GRAL = 'REPOSICION';

        IF (v_IndLinea = 'S')THEN

         SELECT CURRENT_TIMESTAMP INTO v_time_1 FROM dual ;
         EXECUTE IMMEDIATE ' SELECT COD_LOCAL ' ||
                           ' FROM LGT_PROD_LOCAL@XE_DEL_999' ||
                           ' WHERE ROWNUM < 2 ';

         SELECT CURRENT_TIMESTAMP INTO v_time_2 FROM dual ;

         V_RESULT := v_time_2 - v_time_1 ;
         dbms_output.put_line('Real '|| V_RESULT);
         dbms_output.put_line('get  '|| V_TIME_ESTIMADO);
         IF(TO_CHAR(V_RESULT) > TRIM(V_TIME_ESTIMADO)) THEN
           UPDATE PBL_LOCAL SET FEC_MOD_LOCAL = SYSDATE, USU_MOD_LOCAL = cIdUsuario_in,
                                IND_EN_LINEA = 'N'
           WHERE   COD_LOCAL = cCodLocal_in
           AND     COD_GRUPO_CIA = cCodGrupoCia_in ;
         END IF;
        END IF;
/*        EXECUTE IMMEDIATE ' SELECT COD_LOCAL ' ||
                          ' FROM LGT_PROD_LOCAL@XE_DEL_999 ' ||
                          ' WHERE ROWNUM < 2 ' INTO v_valor;
        IF(v_valor IS NOT NULL) THEN
           UPDATE PBL_LOCAL SET FEC_MOD_LOCAL = SYSDATE, USU_MOD_LOCAL = cIdUsuario_in,
                                IND_EN_LINEA = 'S'
           WHERE   COD_LOCAL = cCodLocal_in
           AND     COD_GRUPO_CIA = cCodGrupoCia_in;
        END IF ;
*/
        EXCEPTION
          WHEN OTHERS THEN
             dbms_output.put_line('se cayo');
           UPDATE PBL_LOCAL SET FEC_MOD_LOCAL = SYSDATE, USU_MOD_LOCAL = cIdUsuario_in,
                                IND_EN_LINEA = 'N'
           WHERE   COD_LOCAL = cCodLocal_in
           AND     COD_GRUPO_CIA = cCodGrupoCia_in ;
   END;


  /*****************************************************************************/
   FUNCTION INV_OBTIENE_IND_LINEA(cCodLocal_in  IN CHAR,
                                  cCodGrupoCia_in IN CHAR)
   RETURN VARCHAR2
   IS
     v_Retorno VARCHAR2(10);
     v_IndLinea CHAR(1);
     BEGIN
          SELECT IND_EN_LINEA INTO v_IndLinea
          FROM PBL_LOCAL
          WHERE COD_LOCAL = cCodLocal_in
          AND   COD_GRUPO_CIA = cCodGrupoCia_in ;

          IF(v_IndLinea = 'S') THEN
           v_Retorno := 'TRUE' ;
          ELSIF (v_IndLinea = 'N') THEN
           v_retorno := 'FALSE' ;
          END IF ;

    RETURN V_RETORNO;
   END;

/*******************************************************************/

 FUNCTION CAJ_OBTIENE_MSJ_PROD_VIRT(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    cNumPedido_in    IN CHAR,
                                    cCodProd         IN CHAR)
  RETURN FarmaCursor
  IS
  curdesc       FarmaCursor;
  v_cod_prov_tel  VARCHAR2(4);
  ID_TX           CHAR(12);
  COD_APROBACION  CHAR(6);
  NUM_TELEFONO    VARCHAR2(20);
  DESC_CABECERA   VARCHAR2(400);
  DESC_LINEA_01   VARCHAR2(500) := '';
  DESC_LINEA_02   VARCHAR2(500) := '';
  v_lineaTiempo_anulacion VARCHAR2(500) := '';

  v_vAuxDatos VTA_PEDIDO_VTA_DET.DATOS_IMP_VIRTUAL%TYPE;

  vIndDirecTV  char(1):= 'N';
  nMontoRecarga number;
  BEGIN

      SELECT D.VAL_NUM_TRACE ID_TX, D.VAL_COD_APROBACION APROBACION,  D.DESC_NUM_TEL_REC TELEFONO,
             D.VAL_PREC_TOTAL
      INTO   ID_TX,COD_APROBACION,NUM_TELEFONO,nMontoRecarga
      FROM   VTA_PEDIDO_VTA_DET D
      WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    D.COD_LOCAL     = cCodLocal_in
      AND    D.NUM_PED_VTA   = cNumPedido_in;

      SELECT V.COD_PROV_TEL
      INTO   v_cod_prov_tel
      FROM   LGT_PROD_VIRTUAL V
      WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    V.COD_PROD      = cCodProd;


    v_lineaTiempo_anulacion := Farma_Gral.GET_TIEMPO_MAX_ANULACION(cCodGrupoCia_in,cCodLocal_in,cNumPedido_in);

    if v_lineaTiempo_anulacion = '0' then
       v_lineaTiempo_anulacion:= '  (LA RECARGA NO PUEDE ANULARSE)';
    else
        v_lineaTiempo_anulacion:=
            -- '  (Tiempo Maximo de Anulacion de '||v_lineaTiempo_anulacion|| ' minutos.)'; KMONCADA 04.07.2014
            ' ';
    end if;



      v_cod_prov_tel := TRIM(v_cod_prov_tel);

      IF (v_cod_prov_tel = C_COD_PROV_TELF_CLARO) THEN --CLARO
        begin
          SELECT G.DESC_LARGA
          INTO   DESC_CABECERA
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '157'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'CLARO_CABECERA'
          ;

          SELECT G.DESC_LARGA
          INTO   DESC_LINEA_01
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '158'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'CLARO_LINEA_01';

          SELECT G.DESC_LARGA
          INTO   DESC_LINEA_02
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '159'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'CLARO_LINEA_02';

        EXCEPTION
      		WHEN NO_DATA_FOUND THEN
    			dbms_output.put_line('no se encontro informacion!!!');
        END;
      ELSIF (v_cod_prov_tel = C_COD_PROV_TELF_MOVISTAR) THEN --MOVISTAR
        BEGIN
             --INI ASOSA - 20/01/2015 - RECAR - PLJOVEN
             IF cCodProd = '552391' THEN                --especial para tuenti, son la misma empresa pero ellos quieren q salga algo diferente y avisaron fuera de tiempo

                     SELECT G.LLAVE_TAB_GRAL
                      INTO   DESC_LINEA_01
                      FROM   PBL_TAB_GRAL G
                      WHERE  G.ID_TAB_GRAL  = '633'
                      AND    G.EST_TAB_GRAL = 'A';
                      
             ELSE
             --FIN ASOSA - 20/01/2015 - RECAR - PLJOVEN

          SELECT G.DESC_LARGA
          INTO   DESC_CABECERA
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '160'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'MOVISTAR_CABECERA';

          SELECT G.DESC_LARGA
          INTO   DESC_LINEA_01
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '161'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'MOVISTAR_LINEA_01';


          SELECT G.DESC_LARGA
          INTO   DESC_LINEA_02
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '162'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'MOVISTAR_LINEA_02';

             END IF;

          

        EXCEPTION
      		WHEN NO_DATA_FOUND THEN
    			dbms_output.put_line('no se encontro informacion!!!');
        END;

        --INI ASOSA - 01/12/2014 - RECAR - NEX

        ELSIF (v_cod_prov_tel = C_COD_PROV_TELF_ENTEL) THEN --ENTEL
        BEGIN

          SELECT G.DESC_LARGA
          INTO   DESC_CABECERA
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '620'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'ENTEL_CABECERA';

          SELECT G.DESC_LARGA
          INTO   DESC_LINEA_01
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '621'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'ENTEL_LINEA_01';

          SELECT G.DESC_LARGA
          INTO   DESC_LINEA_02
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '622'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'ENTEL_LINEA_02';

        EXCEPTION
      		WHEN NO_DATA_FOUND THEN
    			dbms_output.put_line('no se encontro informacion!!!');
        END;
        --FIN ASOSA - 01/12/2014 - RECAR - NEX

      ELSIF (v_cod_prov_tel = C_COD_PROV_DIRECTV) THEN --DIRECTV
        BEGIN
          vIndDirecTV := 'S';

          SELECT G.DESC_LARGA
          INTO   DESC_CABECERA
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '160'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'MOVISTAR_CABECERA';

          SELECT 'CONFIRMACION: ' || Substrb(DATOS_IMP_VIRTUAL, 10, 15) ||
                 '   SALDO: ' || TO_NUMBER(Substrb(DATOS_IMP_VIRTUAL, 35, 3),'99999')
                 || CASE when TO_NUMBER(Substrb(DATOS_IMP_VIRTUAL, 35, 3),'99999') > 1 THEN ' dias ' else 'dia' end
          INTO   DESC_LINEA_01
          FROM   VTA_PEDIDO_VTA_DET G
          WHERE  G.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    G.COD_LOCAL = cCodLocal_in
          AND    G.NUM_PED_VTA = cNumPedido_in
          AND    G.COD_PROD = cCodProd;

          SELECT 'EL VALOR DE ESTE TICKET NO ES REEMBOLSABLE'
          INTO   DESC_LINEA_02
          from dual;

        EXCEPTION
      		WHEN NO_DATA_FOUND THEN
    			dbms_output.put_line('no se encontro informacion!!!');
        END;
      END IF ;

        if vIndDirecTV = 'N' then
           
                         --INI ASOSA - 20/01/2015 - RECAR - PLJOVEN
                         IF cCodProd = '552391' THEN
                             OPEN curdesc FOR
                            SELECT 'NUMERO RECARGA: '||NUM_TELEFONO    || ' '||
                                   'APROBACION: ' || COD_APROBACION  || ' '||
                                    DECODE(v_cod_prov_tel,C_COD_PROV_TELF_MOVISTAR,'Id Tx: ' || ID_TX,' ')  || 'Ã' ||
                                    DESC_LINEA_01
                            FROM DUAL;
                         
                         ELSE
                         --FIN ASOSA - 20/01/2015 - RECAR - PLJOVEN
            OPEN curdesc FOR
              SELECT DESC_CABECERA || v_lineaTiempo_anulacion || 'Ã' ||
                     'NUMERO RECARGA: '||NUM_TELEFONO    || ' '||
                     'APROBACION: ' || COD_APROBACION  || ' '||
                      DECODE(v_cod_prov_tel,C_COD_PROV_TELF_MOVISTAR,'Id Tx: ' || ID_TX,' ')  || 'Ã' ||
                      DESC_LINEA_01 || 'Ã' ||
                      DESC_LINEA_02
              FROM DUAL;
                         
                         END IF;
              
         elsif  vIndDirecTV = 'S' then
            OPEN curdesc FOR
              SELECT DESC_CABECERA || v_lineaTiempo_anulacion || 'Ã' ||
                     'Tarjeta: '||NUM_TELEFONO    || ' '||
                     'Monto: '  || nMontoRecarga  || 'Ã'||

                      DESC_LINEA_01 || 'Ã' ||
                      DESC_LINEA_02
              FROM DUAL;
         end if;

    RETURN curdesc;

  END;
 /* *******************************************************************/
 FUNCTION GET_MENSAJE_PERSONALIZADO(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR)

  RETURN VARCHAR2
  IS
    cMensaje VARCHAR2(250);
    cFecha date;
    vIndParam varchar2(100);
    vCodRol   char(3);
    nNumPedDel number;
    vFecha_Max varchar2(3000);
  BEGIN
    select trunc(sysdate)
    into   cFecha
    from   dual;

    SELECT COUNT(1)
    INTO   nNumPedDel
    FROM   TMP_VTA_PEDIDO_VTA_CAB D
    WHERE  D.EST_PED_VTA = 'P';
    if nNumPedDel = 0 then
       vIndParam := ' ';
    else
    begin
      SELECT nvl(trim(to_char(max(D.fec_ped_vta),'DD/MM HH24:MI')),' ') fecha_del
      INTO   vFecha_Max
      FROM   TMP_VTA_PEDIDO_VTA_CAB D
      WHERE  D.EST_PED_VTA = 'P';
    exception
    when no_data_found then
      vFecha_Max := ' ';
    end;


       if nNumPedDel > 1 then
           vIndParam := 'Existen '|| nNumPedDel || ' pedidos Delivery.';
        else
           vIndParam := 'Existe '|| nNumPedDel || ' pedido Delivery.';
        end if;

       if  vFecha_Max != ' ' then
        vIndParam := vIndParam || ' Ã Último Pedido Delivery: ' || vFecha_Max;
       end if;

    end if;
    return vIndParam || ' Ã ';/*
    begin
    SELECT p.mensaje
    into cMensaje
    FROM   PBL_MENSAJES P
    WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    P.ESTADO = 'A'
    AND    P.COD_MENSAJE = '001'
    AND    FEC_INICIO <= cFecha
    AND    FEC_FIN >= cFecha;
    exception
    when NO_DATA_FOUND then
    cMensaje := ' Ã ';
    end;
    return  cMensaje;*/

  END;


/* ****************************************************************** */
  FUNCTION GET_MAXIMO_LOG_NUMERO_TELF(cCodGrupoCia_in IN CHAR)
  RETURN varchar2
  is
   nMaxlongitud varchar2(30);
  begin
       begin
        select g.llave_tab_gral
        into   nMaxlongitud
        from   pbl_tab_gral g
        where  ID_TAB_GRAL = 189
        and    g.cod_apl   = 'PTO_VENTA'
        and    g.cod_tab_gral = 'MAX_NUMERO_TELF'
        and    g.est_tab_gral = 'A';
       exception
       when no_data_found then
        nMaxlongitud := 2;
       end;

       return nMaxlongitud;
  end;

/* ***************************************************************************/

  FUNCTION GET_TIEMPO_MAX_ANULACION(cGrupoCia_in   IN CHAR,
                                    cCodLocal_in   IN CHAR,
                                    cNumPedVta_in  IN CHAR)
  RETURN CHAR
  IS
  TIEMPO  VARCHAR2(20);
  vTipo_prov LGT_PROD_VIRTUAL.COD_PROV_TEL%TYPE;
  BEGIN
/*      SELECT G.LLAVE_TAB_GRAL INTO TIEMPO
        FROM   PBL_TAB_GRAL G
        WHERE  G.ID_TAB_GRAL = '163'
        AND    G.COD_APL     = 'PTO_VENTA'
        AND    G.COD_TAB_GRAL = 'TIEMPO_ANULACION';*/
        BEGIN
        --LLEIVA 28-Mar-2014 DUBILLUZ INDICO QUE SE COMENTE Y SE PASE UN VALOR ALTO
        --DEBIDO A QUE SOLO SE ANULA EL TICKET Y NO LA RECARGA
        /*SELECT V.COD_PROV_TEL
        INTO   vTipo_prov
        FROM   LGT_PROD_VIRTUAL V
        WHERE  V.COD_GRUPO_CIA = cGrupoCia_in
        AND    V.TIP_PROD_VIRTUAL = 'R'
        AND    V.COD_PROD IN (
                              SELECT D.COD_PROD
                              FROM   VTA_PEDIDO_VTA_DET D
                              WHERE  D.COD_GRUPO_CIA = cGrupoCia_in
                              AND    D.COD_LOCAL = cCodLocal_in
                              AND    D.NUM_PED_VTA = cNumPedVta_in
                              );
        EXCEPTION
        WHEN OTHERS THEN
             vTipo_prov := 'N';
        END;

        BEGIN
        DBMS_OUTPUT.put_line('vTipo_prov ' || vTipo_prov);

        SELECT G.DESC_CORTA
        INTO TIEMPO
        FROM   PBL_TAB_GRAL G
        WHERE  \*G.ID_TAB_GRAL IN (191,192)
        AND    *\G.COD_APL     = 'PTO_VENTA'
        AND    G.COD_TAB_GRAL = 'TIEMPO_ANULACION'
        AND    G.LLAVE_TAB_GRAL = TRIM(vTipo_prov);*/

        TIEMPO := '999999999';

        EXCEPTION
        WHEN NO_DATA_FOUND THEN
          TIEMPO := '999999999';
        END;


      RETURN TIEMPO;
  END;

  /* ******************************************************************** */

  FUNCTION GET_DESCRIPCION_PROD(cGrupoCia_in   IN CHAR,
                                cCodProd_in    IN CHAR)
  RETURN CHAR
  IS
   vDescProd   VARCHAR2(200);

  BEGIN
       BEGIN
         SELECT P.DESC_PROD || '  '||p.desc_unid_present
         INTO   vDescProd
         FROM   LGT_PROD P
         WHERE  P.COD_GRUPO_CIA = cGrupoCia_in
         AND    P.COD_PROD = cCodProd_in;
       EXCEPTION
       WHEN NO_DATA_FOUND THEN
         vDescProd := ' ';
       END;

       RETURN  vDescProd;
  end;
  /* ******************************************************************** */
  FUNCTION GET_TIPO_PEDIDO(cGrupoCia_in   IN CHAR,
                           cCod_local     IN CHAR,
                           cNum_ped_Vta_in IN CHAR)
  RETURN CHAR
  IS
   vTipo_Ped   VARCHAR2(200);
  BEGIN
       BEGIN
          SELECT VTA_CAB.TIP_PED_VTA
          into   vTipo_Ped
          FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
          WHERE  VTA_CAB.COD_GRUPO_CIA = cGrupoCia_in
          AND	   VTA_CAB.COD_LOCAL = cCod_local
          AND	   VTA_CAB.NUM_PED_VTA = cNum_ped_Vta_in;
       EXCEPTION
       WHEN NO_DATA_FOUND THEN
         vTipo_Ped := ' ';
       END;

       RETURN  vTipo_Ped;
  end;
  /* ******************************************************************** */
  FUNCTION GET_FORMAS_PAGO_PEDIDO(cGrupoCia_in   IN CHAR,
                                  cCod_local     IN CHAR,
                                  cNum_ped_Vta_in IN CHAR)
  RETURN CHAR
  IS
   vCadenaFP   VARCHAR2(200);
   vCodFPDolares char(5);
   vIndPagoDolares char(1):= 'N';
   vTipoCambio varchar2(200);
  BEGIN

      SELECT T.LLAVE_TAB_GRAL
      into   vCodFPDolares
      FROM   PBL_TAB_GRAL T
      WHERE  T.ID_TAB_GRAL = 145;

       BEGIN
            for x in
                    (
                    SELECT F.COD_FORMA_PAGO,F.DESC_CORTA_FORMA_PAGO,P.VAL_TIP_CAMBIO
                    FROM   VTA_FORMA_PAGO_PEDIDO P,
                           VTA_FORMA_PAGO F
                    WHERE  P.COD_GRUPO_CIA = F.COD_GRUPO_CIA
                    AND    P.COD_FORMA_PAGO = F.COD_FORMA_PAGO
                    AND    P.COD_GRUPO_CIA = cGrupoCia_in
                    AND    P.COD_LOCAL = cCod_local
                    AND    P.NUM_PED_VTA = cNum_ped_Vta_in
                    )loop

              vCadenaFP := vCadenaFP || ',' || x.desc_corta_forma_pago  ;

              if x.cod_forma_pago = vCodFPDolares then
                 vIndPagoDolares := 'S';
                 vTipoCambio := ' Tipo Cambio:  ' || trim(to_char(x.val_tip_cambio,'99999.00'));
              end if;

            end loop;

              if vIndPagoDolares = 'S' then
                 vCadenaFP := vCadenaFP ||'  ' ||vTipoCambio;
              end if;



       EXCEPTION
       WHEN others THEN
         vCadenaFP := ' ';
       END;
       if Length(vCadenaFP) > 1   then
       vCadenaFP := Substr(vCadenaFP, 2, Length(vCadenaFP));

       end if;
       RETURN  NVL(vCadenaFP,' ');
  end;

  /* ******************************************************************** */
  FUNCTION GET_ENCARTES_APLICABLES(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   vIsFidelizado_in   IN CHAR DEFAULT 'N')
-- KMONCADA 10.08.2015 SE AGREGA CAMPO PARA DETERMINAR SI ENCARTE APLICA PARA FIDELIZADOS O NO
  RETURN FarmaCursor
  IS
  curdesc       FarmaCursor;
  BEGIN
    OPEN curdesc FOR
            SELECT E.COD_ENCARTE
            FROM   VTA_ENCARTE E
            WHERE  TRUNC(SYSDATE) BETWEEN E.FECH_INICIO
                                  AND     E.FECH_FIN
            AND    E.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    E.ESTADO = 'A'
            AND    (E.IND_FID_USO = 'N' OR E.IND_FID_USO = vIsFidelizado_in);
    RETURN curdesc;

  END;

  /* ******************************************************************** */
  FUNCTION GET_CAMP_CUPO_APLICABLES(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cTipo_in        IN CHAR,
                                    cNumPed_in      IN CHAR)

  RETURN FarmaCursor
  IS
  curdesc       FarmaCursor;
  nNumDia       VARCHAR(2);
  IND_MULTIMARCA char(1):= 'M';
  IND_CUPON char(1):= 'C';
  BEGIN
     nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);
    IF cTipo_in = 'M' THEN
     OPEN curdesc FOR
      -- LISTA DE MULTIMARCA UNIDO A CAMPAÑAS CUPON
      SELECT C.COD_CAMP_CUPON || 'Ã' ||
             C.TIP_CAMPANA || 'Ã' ||
             IND_MENSAJE
      FROM   VTA_CAMPANA_CUPON C
      WHERE  TRUNC(SYSDATE) BETWEEN C.FECH_INICIO
                            AND     C.FECH_FIN
      AND    C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.ESTADO = 'A'
      AND    C.TIP_CAMPANA = IND_MULTIMARCA;

    ELSIF  cTipo_in = 'C' THEN
     IF cNumPed_in != 'N'  THEN -- ya no se utiliza
        OPEN curdesc FOR
        select * from VTA_CAMPANA_CUPON;
        /*SELECT DISTINCT CP.COD_PROD|| 'Ã' ||C.COD_CAMP_CUPON|| 'Ã' ||
                         C.PRIORIDAD|| 'Ã' ||
                         C.VALOR_CUPON|| 'Ã' ||C.TIP_CUPON
                  FROM   VTA_PEDIDO_VTA_DET D,
                         VTA_CAMPANA_PROD CP,
                         VTA_CAMPANA_CUPON C,
                         (
                         SELECT DET.COD_PROD,MAX(CAM.PRIORIDAD) MAX_PRIORIDAD
                         FROM   VTA_PEDIDO_VTA_DET DET,
                                VTA_CAMPANA_CUPON CAM,
                                VTA_CAMPANA_PROD PROD
                         WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND    DET.COD_LOCAL = cCodLocal_in
                         AND    DET.NUM_PED_VTA =cNumPed_in
                         AND    CAM.TIP_CAMPANA = IND_CUPON
                         AND    CAM.ESTADO = 'A'
                         AND    CAM.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
                         AND    CAM.COD_CAMP_CUPON = PROD.COD_CAMP_CUPON
                         AND    PROD.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                         AND    PROD.COD_PROD = DET.COD_PROD
                         GROUP  BY DET.COD_PROD
                         )V
                  WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    D.COD_LOCAL = cCodLocal_in
                  AND    D.NUM_PED_VTA = cNumPed_in
                  AND    C.ESTADO = 'A'
                  AND    C.TIP_CAMPANA = IND_CUPON
                  AND    TRUNC(SYSDATE) BETWEEN C.FECH_INICIO AND  C.FECH_FIN
                  AND    C.PRIORIDAD = V.MAX_PRIORIDAD
                  AND    D.COD_PROD = V.COD_PROD
                  AND    CP.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                  AND    CP.COD_PROD = D.COD_PROD
                  AND    C.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                  AND    C.COD_CAMP_CUPON = CP.COD_CAMP_CUPON

        AND    C.COD_CAMP_CUPON IN (
                                      SELECT *
                                      FROM   (
                                              SELECT *
                                              FROM
                                                  (
                                                  SELECT COD_CAMP_CUPON
                                                  FROM   VTA_CAMPANA_CUPON
                                                  MINUS
                                                  SELECT CL.COD_CAMP_CUPON
                                                  FROM   VTA_CAMP_X_LOCAL CL
                                                  )
                                              UNION
                                              SELECT COD_CAMP_CUPON
                                              FROM   VTA_CAMP_X_LOCAL
                                              WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                                              AND    COD_LOCAL = cCodLocal_in
                                              AND    ESTADO = 'A')
                                      )
          AND  C.COD_CAMP_CUPON IN (
                                    SELECT *
                                    FROM
                                        (
                                          SELECT *
                                          FROM
                                          (
                                            SELECT COD_CAMP_CUPON
                                            FROM   VTA_CAMPANA_CUPON
                                            MINUS
                                            SELECT H.COD_CAMP_CUPON
                                            FROM   VTA_CAMP_HORA H
                                          )
                                          UNION
                                          SELECT H.COD_CAMP_CUPON
                                          FROM   VTA_CAMP_HORA H
                                          WHERE  TRIM(TO_CHAR(SYSDATE,'HH24')) BETWEEN H.HORA_INICIO  AND H.HORA_FIN
                                        ))
          AND  DECODE(C.DIA_SEMANA,NULL,'S',
                        DECODE(C.DIA_SEMANA,REGEXP_REPLACE(C.DIA_SEMANA,nNumDia,'S'),'N','S')
                        ) = 'S';*/


     ELSE
         OPEN curdesc FOR
          SELECT C.COD_CAMP_CUPON || 'Ã' ||
                 C.TIP_CAMPANA || 'Ã' ||
                 IND_MENSAJE
          FROM   VTA_CAMPANA_CUPON C
          WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
          AND    C.ESTADO = 'A'
          AND    C.TIP_CAMPANA = IND_CUPON
          AND    TRUNC(SYSDATE) BETWEEN C.FECH_INICIO AND  C.FECH_FIN
          AND    C.COD_CAMP_CUPON IN (
                                      /*SELECT *
                                      FROM   (
                                              SELECT *
                                              FROM
                                                  (
                                                  SELECT COD_CAMP_CUPON
                                                  FROM   VTA_CAMPANA_CUPON
                                                  MINUS
                                                  SELECT CL.COD_CAMP_CUPON
                                                  FROM   VTA_CAMP_X_LOCAL CL
                                                  )
                                              UNION
                                              SELECT COD_CAMP_CUPON
                                              FROM   VTA_CAMP_X_LOCAL
                                              WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                                              AND    COD_LOCAL = cCodLocal_in
                                              AND    ESTADO = 'A')*/
                                    --JCORTEZ 19.10.09 cambio de logica
                                      SELECT *
                                        FROM   (
                                               SELECT X.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON X
                                               WHERE X.COD_GRUPO_CIA='001'
                                               AND X.TIP_CAMPANA=IND_CUPON
                                               AND X.ESTADO='A'
                                               AND X.IND_CADENA='S'
                                               UNION
                                               SELECT Y.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON Y
                                               WHERE Y.COD_GRUPO_CIA='001'
                                               AND Y.TIP_CAMPANA=IND_CUPON
                                               AND Y.ESTADO='A'
                                               AND Y.IND_CADENA='N'
                                               AND Y.COD_CAMP_CUPON IN (SELECT COD_CAMP_CUPON
                                                                        FROM   VTA_CAMP_X_LOCAL Z
                                                                        WHERE  Z.COD_GRUPO_CIA =cCodGrupoCia_in
                                                                        AND    Z.COD_LOCAL = cCodLocal_in
                                                                        AND    Z.ESTADO = 'A')

                                               )
                                      )
          AND  C.COD_CAMP_CUPON IN (
                                    SELECT *
                                    FROM
                                        (
                                          SELECT *
                                          FROM
                                          (
                                            SELECT COD_CAMP_CUPON
                                            FROM   VTA_CAMPANA_CUPON
                                            MINUS
                                            SELECT H.COD_CAMP_CUPON
                                            FROM   VTA_CAMP_HORA H
                                          )
                                          UNION
                                          SELECT H.COD_CAMP_CUPON
                                          FROM   VTA_CAMP_HORA H
                                          WHERE  TRIM(TO_CHAR(SYSDATE,'HH24')) BETWEEN H.HORA_INICIO  AND H.HORA_FIN
                                        ))
          AND  DECODE(C.DIA_SEMANA,NULL,'S',
                        DECODE(C.DIA_SEMANA,REGEXP_REPLACE(C.DIA_SEMANA,nNumDia,'S'),'N','S')
                        ) = 'S';

      END IF;
     END IF;


    RETURN curdesc;

  END;
 /* ******************************************************************** */
  FUNCTION GET_PARAM_FILTRO_ESPECIALIZADO(cGrupoCia_in   IN CHAR)
  RETURN VARCHAR2
  IS
  vIndParam varchar2(100);
  BEGIN
      BEGIN
      SELECT TRIM(T.LLAVE_TAB_GRAL)
      INTO   vIndParam
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'PRM_FILTRO'
      AND    T.ID_TAB_GRAL = 195;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vIndParam := '*';
      END;

      return  vIndParam;
  END;
 /* ***************************************************************** */
  FUNCTION GET_LONGITUD_MIN_FILTRO(cGrupoCia_in   IN CHAR)
  RETURN VARCHAR2
  IS
  vIndParam varchar2(100);
  BEGIN
      BEGIN
      SELECT TRIM(T.LLAVE_TAB_GRAL)
      INTO   vIndParam
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'PRM_FILTRO'
      AND    T.ID_TAB_GRAL = 196;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vIndParam := '4';
      END;

      return  vIndParam;
  END;
/* **************************************************************** */
  FUNCTION GET_MSG_ROL_LOGIN(cCodGrupoCia_in IN CHAR,
                             cCod_Rol_in   IN CHAR,
							cCodCia_in IN CHAR DEFAULT NULL,
							cCodLocal_in IN CHAR DEFAULT NULL)
  RETURN VARCHAR2
  IS
  vIndParam varchar2(100);
  vCodRol   char(3);
  nNumPedDel number;
   vFecha_Max varchar2(3000);
  BEGIN

      BEGIN

        vCodRol := trim(cCod_Rol_in);

        IF vCodRol = '010' THEN
            begin
				--ERIOS 2.4.5 Cambios proyecto Conveniencia
				SELECT COUNT(1)
				INTO   nNumPedDel
				FROM   TMP_VTA_PEDIDO_VTA_CAB D
				WHERE  D.EST_PED_VTA = 'P'
				and    d.cod_local_atencion = cCodLocal_in;
            exception
            when others then
            nNumPedDel := 0;
            end;

            if nNumPedDel = 0 then
               vIndParam := ' ';
            else

               begin
                    SELECT nvl(trim(to_char(MAX(D.fec_ped_vta),'DD/MM HH24:MI')),' ') fecha_del
                    INTO   vFecha_Max
                    FROM   TMP_VTA_PEDIDO_VTA_CAB D
                    WHERE  D.EST_PED_VTA = 'P'
					and    d.cod_local_atencion = cCodLocal_in;
                  exception
                  when no_data_found then
                    vFecha_Max := ' ';
                  end;


              if nNumPedDel > 1 then
                 vIndParam := ''|| nNumPedDel || ' pedidos Delivery.';
              else
                 vIndParam := ''|| nNumPedDel || ' pedido Delivery.';
              end if;

               if  vFecha_Max != ' ' then
                vIndParam := vIndParam || '  Ultimo Gen: ' || vFecha_Max;
               end if;

            end if;

        ELSE
            SELECT NVL(P.MSG_LOGIN,' ')
            INTO   vIndParam
            FROM   PBL_ROL P
            WHERE  P.COD_ROL = vCodRol;
        END IF;

      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vIndParam := '';
      END;

      return vIndParam;
    END ;

/* ******************************************************************* */

 FUNCTION GET_IND_CMM_ANTES_RECARGA
  RETURN VARCHAR2
  IS
  ID_COMMIT_RECARGA_VIRTUAL           VARCHAR2(10);

  BEGIN
       BEGIN
        SELECT NVL(TRIM(T.LLAVE_TAB_GRAL),' ')
        INTO   ID_COMMIT_RECARGA_VIRTUAL
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL  = 209
        AND    T.COD_APL      = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'COMMIT_BEFORE_RELOAD';
       EXCEPTION
       WHEN NO_DATA_FOUND THEN
            ID_COMMIT_RECARGA_VIRTUAL := 'N';
       END;

       RETURN  ID_COMMIT_RECARGA_VIRTUAL;
  END ;

/* ******************************************************************* */

 FUNCTION GET_TIPO_IMPR_CONSEJO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR)
  RETURN VARCHAR2
  IS
  vTipoImpr           VARCHAR2(10);

  BEGIN
       BEGIN

        SELECT NVL(TRIM(L.TIPO_IMPR_TERMICA),'01')
        INTO   vTipoImpr
        FROM   PBL_LOCAL L
        WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    L.COD_LOCAL = cCodLocal_in;
       EXCEPTION
       WHEN NO_DATA_FOUND THEN
            vTipoImpr := '01';
       END;

       RETURN  vTipoImpr;
  END ;

/* ******************************************************************* */
  FUNCTION GET_IND_PEDIDO_REP_AUTOMATICO(cGrupoCia_in   IN CHAR,
                                         cCod_local     IN CHAR)
  RETURN CHAR
  IS
    vIndTipo  CHAR(1);
  BEGIN
       BEGIN
       SELECT NVL(TRIM(T.LLAVE_TAB_GRAL),'N')
       INTO   vIndTipo
       FROM   PBL_TAB_GRAL T
       WHERE  T.ID_TAB_GRAL = 999;
       EXCEPTION
       WHEN NO_DATA_FOUND THEN
            vIndTipo := 'N';
       END;

       RETURN  vIndTipo;
  END;
  /****************************************************************************/
  FUNCTION GET_LISTA_MODELO_IMPRESORA
  RETURN FarmaCursor
    IS
      curTipo FarmaCursor;
    BEGIN
         OPEN curTipo FOR
              SELECT LLAVE_TAB_GRAL || 'Ã' ||
                     DESC_CORTA
              FROM PBL_TAB_GRAL
              WHERE COD_TAB_GRAL='MODELO_IMP_TERMICA' and
                    COD_APL='PTO_VENTA';

         RETURN curTipo ;
  END;

  /****************************************************************************/
  FUNCTION GET_LISTA_FORMATO_IMPRESION(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR)
  RETURN FarmaCursor
    IS
      curTipo FarmaCursor;
    BEGIN
         OPEN curTipo FOR
              SELECT LLAVE_TAB_GRAL || 'Ã' ||
                     DESC_CORTA
              FROM PBL_TAB_GRAL e
              WHERE COD_TAB_GRAL='CONST_FORMATO_IMP' and
                    COD_APL='PTO_VENTA'
                    and  e.est_tab_gral = 'A'
					AND COD_CIA = cCodCia_in;

         RETURN curTipo ;
  END;
  /****************************************************************************/
  FUNCTION F_EXISTE_DESFASE_COMP(
                                 cGrupoCia_in  IN CHAR,
                                 cCod_local_in IN CHAR,
                                 cFechaDia_in  IN CHAR
                                )
  RETURN CHAR
  IS


   nCantComp_P number;
   nCantComp_S number;
   --por defecto si existen desfaces
   vResult char(1):= 'S';
  BEGIN

     DELETE FROM AUX_RANGO_COMP_DIA;

     INSERT INTO AUX_RANGO_COMP_DIA (COD_LOCAL, TIP_COMP_PAGO, SERIE, MIN_COMP, MAX_COMP)
     SELECT COD_LOCAL,
            TIP_COMP_PAGO,
            SERIE,
            (SELECT MIN(CP.NUM_COMP_PAGO)
            /*MIN(FARMA_UTILITY.GET_T_CORR_COMPROBA
            (CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO))*/
            -- KMONCADA 19.12.2014 GRABA NUMERO COMPLETO DE COMPROBANTE DE PAGO
              -- MIN(FARMA_UTILITY.GET_T_CORR_COMPROBA(NVL(CP.COD_TIP_PROC_PAGO,'0'),CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO))
             FROM VTA_COMP_PAGO CP 
             WHERE CP.COD_GRUPO_CIA = cGrupoCia_in 
             AND CP.COD_LOCAL = CD.COD_LOCAL 
             AND CP.TIP_COMP_PAGO = CD.TIP_COMP_PAGO 
             AND SUBSTR(CP.NUM_COMP_PAGO,1,3) = CD.SERIE 
             AND CP.NUM_PED_VTA = CD.NUM_PED_MIN) MIN_COMP,
            
            (SELECT MAX(CP.NUM_COMP_PAGO)
            /*MAX(FARMA_UTILITY.GET_T_CORR_COMPROBA
            (CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO))*/
            -- KMONCADA 19.12.2014 GRABA NUMERO COMPLETO DE COMPROBANTE DE PAGO
            /*MAX(
            FARMA_UTILITY.GET_T_CORR_COMPROBA(NVL(CP.COD_TIP_PROC_PAGO,'0'),CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)*/
            /*CASE
                 WHEN NVL(CP.COD_TIP_PROC_PAGO,'0') = '1' THEN CP.NUM_COMP_PAGO_e
                 ELSE  CP.NUM_COMP_PAGO END)*/
            FROM VTA_COMP_PAGO CP WHERE CP.COD_GRUPO_CIA = cGrupoCia_in AND CP.COD_LOCAL = CD.COD_LOCAL AND CP.TIP_COMP_PAGO = CD.TIP_COMP_PAGO 
            AND SUBSTR(CP.NUM_COMP_PAGO,1,3)
            --FAC-ELECTRONICA :09.10.2014
            -- FARMA_UTILITY.GET_T_SERIE_COMPROBA (CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
            = CD.SERIE AND CP.NUM_PED_VTA = CD.NUM_PED_MAX) MAX_COMP
     FROM
          (
             SELECT /*+ RULE(IX_VTA_PEDIDO_VTA_CAB_01) */
                    C.COD_LOCAL,
                   CP.TIP_COMP_PAGO TIP_COMP_PAGO,
                   SUBSTR(CP.NUM_COMP_PAGO,1,3) SERIE,
                   /*FARMA_UTILITY.GET_T_SERIE_COMPROBA
                      (CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO) SERIE,*/
                   --FAC-ELECTRONICA :09.10.2014
                   MIN(C.NUM_PED_VTA) NUM_PED_MIN,
                   MAX(C.NUM_PED_VTA) NUM_PED_MAX
             FROM VTA_PEDIDO_VTA_CAB C,
                  VTA_COMP_PAGO CP
             WHERE C.COD_GRUPO_CIA = cGrupoCia_in
               AND C.COD_LOCAL = cCod_local_in
               AND C.EST_PED_VTA = 'C'
               AND CP.TIP_COMP_PAGO IN ('01', '02')
               AND C.FEC_PED_VTA BETWEEN TO_DATE(TO_CHAR(TRUNC(SYSDATE,'MM'),'dd/MM/yyyy') || ' 00:00:00', 'dd/MM/yyyy HH24:mi:ss') AND
                                         TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
               AND CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
               AND CP.COD_LOCAL = C.COD_LOCAL
               AND CP.NUM_PED_VTA = C.NUM_PED_VTA
               -- KMONCADA 29.01.2015 SOLO CONSIDERA COMPROBANTES NO ELECTRONICOS
               AND NVL(CP.COD_TIP_PROC_PAGO,'0') = '0'
             GROUP BY
                   C.COD_LOCAL,
                   CP.TIP_COMP_PAGO,
                   SUBSTR(CP.NUM_COMP_PAGO,1,3)
                   /*FARMA_UTILITY.GET_T_SERIE_COMPROBA
                      (CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)*/
                      --FAC-ELECTRONICA :09.10.2014
          ) CD;

     COMMIT;

          SELECT COUNT(*)
          INTO nCantComp_S
          FROM
               (
                 SELECT
                        CD.COD_LOCAL, CD.TIP_COMP_PAGO, SERIE,
                        (MAX_COMP - MIN_COMP + 1) DIF,
                        COUNT(*) +                        
                        NVL((SELECT (MAX_COMP-MIN_COMP)+1
                        FROM AUX_RANGO_COMP_BAJA 
                        WHERE TIP_COMP_PAGO=CD.TIP_COMP_PAGO
                        AND SERIE=CD.SERIE
                        AND COD_LOCAL=CD.COD_LOCAL
                        AND MIN_COMP>=CD.MIN_COMP
                        AND MAX_COMP<=CD.MAX_COMP
                        ),0) CANT   
                 FROM VTA_COMP_PAGO CP,
                      AUX_RANGO_COMP_DIA CD
                 WHERE CP.COD_GRUPO_CIA = cGrupoCia_in
                   AND CP.COD_LOCAL = CD.COD_LOCAL
                   AND CP.TIP_COMP_PAGO = CD.TIP_COMP_PAGO
                   AND SUBSTR(CP.NUM_COMP_PAGO,1,3) = CD.SERIE 
                   /*
                   --rherrera 11.11.2014
                   AND (CASE  NVL(CP.COD_TIP_PROC_PAGO,'0')
                         WHEN '1' THEN SUBSTR(NUM_COMP_PAGO_E,1,4)
                         WHEN '0' THEN SUBSTR(NUM_COMP_PAGO,1,3)
                        END
                       ) = CD.SERIE*/
                   AND CP.NUM_COMP_PAGO BETWEEN CD.MIN_COMP AND CD.MAX_COMP 
                   /*
                   ----rherrera 11.11.2014
                   AND (CASE  NVL(CP.COD_TIP_PROC_PAGO,'0')
                         WHEN '1' THEN NUM_COMP_PAGO_E
                         WHEN '0' THEN NUM_COMP_PAGO
                        END
                       ) BETWEEN CD.SERIE||CD.MIN_COMP AND CD.SERIE||CD.MAX_COMP
                       */
                 GROUP BY
                        CD.COD_LOCAL, CD.TIP_COMP_PAGO, SERIE,
                        (MAX_COMP - MIN_COMP)
                         ,CD.MIN_COMP,CD.MAX_COMP
               )
          WHERE DIF != CANT;

          if nCantComp_S = 0 then
            vResult := 'N';
          end if;

/*
        ---Se revisan los primeros comprobantes del dia cobrados
        ---luego se validan que existan los inmediatos anteriores
        ---si existen se validaran los comprobantes del dia caso contrario retorna 'S' que tiene
        ---comprobantes desfasados
        SELECT COUNT(1)
          into nCantComp_P
          FROM (SELECT CP.COD_GRUPO_CIA,
                       CP.COD_LOCAL,
                       CP.TIP_COMP_PAGO,
                       Substr(CP.NUM_COMP_PAGO, 0, 3) SERIE,
                       TRIM(TO_CHAR(MIN(CP.NUM_COMP_PAGO) - 1, '0000000000')) MAX_COM_PAGO_ANTERIOR
                  FROM (SELECT C.COD_GRUPO_CIA,
                               C.COD_LOCAL,
                               C.TIP_COMP_PAGO TIPO_PAGO,
                               MIN(C.NUM_PED_VTA) NUM_PED_MIN,
                               MAX(C.NUM_PED_VTA) NUM_PED_MAX
                          FROM VTA_PEDIDO_VTA_CAB C
                         WHERE C.COD_GRUPO_CIA = cGrupoCia_in
                           AND C.COD_LOCAL = cCod_local_in
                           AND C.EST_PED_VTA = 'C'
                           AND C.TIP_COMP_PAGO IN ('01', '02')
                           AND C.FEC_PED_VTA BETWEEN
                               TO_DATE(cFechaDia_in || ' 00:00:00',
                                       'dd/MM/yyyy HH24:mi:ss') AND
                               TO_DATE(cFechaDia_in || ' 23:59:59',
                                       'dd/MM/yyyy HH24:mi:ss')
                         GROUP BY C.COD_GRUPO_CIA, C.COD_LOCAL, C.TIP_COMP_PAGO) V,
                       VTA_COMP_PAGO CP
                 WHERE CP.COD_GRUPO_CIA = V.COD_GRUPO_CIA
                   AND CP.COD_LOCAL = V.COD_LOCAL
                   AND CP.TIP_COMP_PAGO = V.TIPO_PAGO
                   AND CP.NUM_PED_VTA between  V.NUM_PED_MIN and V.NUM_PED_MAX
                 GROUP BY CP.COD_GRUPO_CIA,
                          CP.COD_LOCAL,
                          CP.TIP_COMP_PAGO,
                          Substr(CP.NUM_COMP_PAGO, 0, 3)) V,
               VTA_COMP_PAGO CP
         WHERE CP.COD_GRUPO_CIA = V.COD_GRUPO_CIA
           AND CP.COD_LOCAL = V.COD_LOCAL
           AND CP.TIP_COMP_PAGO = V.TIP_COMP_PAGO
           AND CP.NUM_COMP_PAGO = V.MAX_COM_PAGO_ANTERIOR;

        --SI existen los comprobantes por lo tanto no hay un desfase entre el primero comprobante del dia y
        --el ultimo del dia o ultimo dia cobrado anteriormente.
        if nCantComp_P > 0 then
          --la validacion se realiza de la siguiente manera
          ---Se obtiene el primero y ultimo comprobante
          ---Se suman todos los comprobantes que existen en ese tramo luego
          ---por una simple operacion de serie se realiza la suma con el minimo y el maximo sabiendo que la razon
          ---debe de ser "1" y la diferencia con la suma de comprobantes
          SELECT COUNT(1)
            into nCantComp_S
            FROM (SELECT P.TIP_COMP_PAGO,
                         SUBSTR(P.NUM_COMP_PAGO, 0, 3) SERIE,
                         (MAX(P.NUM_COMP_PAGO) + MIN(P.NUM_COMP_PAGO)) *
                         ((MAX(P.NUM_COMP_PAGO) - MIN(P.NUM_COMP_PAGO)) + 1) / 2 -
                         SUM(P.NUM_COMP_PAGO) DIF
                    FROM (SELECT CP.COD_GRUPO_CIA,
                                 CP.COD_LOCAL,
                                 CP.TIP_COMP_PAGO,
                                 Substr(CP.NUM_COMP_PAGO, 0, 3) SERIE,
                                 MIN(CP.NUM_COMP_PAGO) MIN_COM_PAGO,
                                 MAX(CP.NUM_COMP_PAGO) MAX_COM_PAGO
                            FROM (SELECT C.COD_GRUPO_CIA,
                                         C.COD_LOCAL,
                                         C.TIP_COMP_PAGO TIPO_PAGO,
                                         MIN(C.NUM_PED_VTA) NUM_PED_MIN,
                                         MAX(C.NUM_PED_VTA) NUM_PED_MAX
                                    FROM VTA_PEDIDO_VTA_CAB C
                                   WHERE C.COD_GRUPO_CIA = cGrupoCia_in
                                     AND C.COD_LOCAL = cCod_local_in
                                     AND C.EST_PED_VTA = 'C'
                                     AND C.TIP_COMP_PAGO IN ('01', '02')
                                     AND C.FEC_PED_VTA BETWEEN
                                         TO_DATE(cFechaDia_in || ' 00:00:00',
                                                 'dd/MM/yyyy HH24:mi:ss') AND
                                         TO_DATE(cFechaDia_in || ' 23:59:59',
                                                 'dd/MM/yyyy HH24:mi:ss')
                                   GROUP BY C.COD_GRUPO_CIA,
                                            C.COD_LOCAL,
                                            C.TIP_COMP_PAGO) V,
                                 VTA_COMP_PAGO CP
                           WHERE CP.COD_GRUPO_CIA = V.COD_GRUPO_CIA
                             AND CP.COD_LOCAL = V.COD_LOCAL
                             AND CP.TIP_COMP_PAGO = V.TIPO_PAGO
                             AND CP.NUM_PED_VTA between  V.NUM_PED_MIN and V.NUM_PED_MAX
                           GROUP BY CP.COD_GRUPO_CIA,
                                    CP.COD_LOCAL,
                                    CP.TIP_COMP_PAGO,
                                    Substr(CP.NUM_COMP_PAGO, 0, 3)) VC,
                         VTA_COMP_PAGO P
                   WHERE P.COD_GRUPO_CIA = VC.COD_GRUPO_CIA
                     AND P.COD_LOCAL = VC.COD_LOCAL
                     AND P.TIP_COMP_PAGO = VC.TIP_COMP_PAGO
                     AND P.NUM_COMP_PAGO BETWEEN VC.MIN_COM_PAGO AND VC.MAX_COM_PAGO
                   GROUP BY P.TIP_COMP_PAGO, SUBSTR(P.NUM_COMP_PAGO, 0, 3)) V
           WHERE V.DIF != 0;

          --Si existe una diferencias diferente de "0" existe desfase
          --caso contrario se mantiene con el valor por defecto
          --que tiene desfase
          if nCantComp_S = 0 then
            vResult := 'N';
          end if;

        end if;
*/

        return vResult;
  END;

  /****************************************************************************/
  FUNCTION F_EXISTE_DEL_PEND_SIN_REG(
                                 cGrupoCia_in  IN CHAR,
                                 cCod_local_in IN CHAR,
                                 cFechaDia_in  IN CHAR
                                )
  RETURN CHAR
  IS


   nCantDelPendiente number;
   nCantDelReg number;

   vResult char(1):= 'S';
  BEGIN
    --ERIOS 14.11.2014 Afinamiento de consultas

        --CANTIDAD DELIVERY PENDIENTE INGRESADOS
        select count(1)
        into   nCantDelPendiente
        from   ce_cuadratura_caja ce
        where  ce.cod_grupo_cia = cGrupoCia_in
        AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    ce.cod_local = cCod_local_in
        and    ce.cod_cuadratura = '003' --cuadratura delivery pendiente
        AND    ce.fec_crea_cuadratura_caja
                      BETWEEN TO_DATE(cFechaDia_in,'dd/MM/yyyy')-INTERVAL '1' YEAR
                      AND     TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') -1
        and exists (select 1 from vta_comp_pago cp where 1=1
                    and    ce.cod_grupo_cia = cp.cod_grupo_cia
                    and    ce.cod_local = cp.cod_local
                    and    ce.tip_comp = cp.tip_comp_pago
                    AND (
                         NVL(CP.COD_TIP_PROC_PAGO,'0') = '1' AND (ce.num_serie_local || ce.num_comp_pago = NUM_COMP_PAGO_E)
                         or NVL(CP.COD_TIP_PROC_PAGO,'0') = '0' AND (ce.num_serie_local || ce.num_comp_pago = NUM_COMP_PAGO)
                         )
                    );
            --FAC-ELECTRONICA :09.10.2014


        --CANTIDAD DELIVERY PENDIETE REGULARIZADOS DE LOS DECLARADOS
        select count(1)
        into   nCantDelReg
        from   ce_cuadratura_caja ce,
               (
                select ce.cod_grupo_cia,ce.cod_local,ce.tip_comp, ce.num_serie_local || ce.num_comp_pago num_pago
                        from   ce_cuadratura_caja ce
                where  ce.cod_grupo_cia = cGrupoCia_in
                AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                and    ce.cod_local = cCod_local_in
                and    ce.cod_cuadratura = '003' --cuadratura delivery pendiente
                AND    ce.fec_crea_cuadratura_caja
                              BETWEEN TO_DATE(cFechaDia_in,'dd/MM/yyyy')-INTERVAL '1' YEAR
                              AND     TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') -1
                and exists (select 1 from vta_comp_pago cp where 1=1
                            and    ce.cod_grupo_cia = cp.cod_grupo_cia
                            and    ce.cod_local = cp.cod_local
                            and    ce.tip_comp = cp.tip_comp_pago
                            AND (
                                 NVL(CP.COD_TIP_PROC_PAGO,'0') = '1' AND (ce.num_serie_local || ce.num_comp_pago = NUM_COMP_PAGO_E)
                                 or NVL(CP.COD_TIP_PROC_PAGO,'0') = '0' AND (ce.num_serie_local || ce.num_comp_pago = NUM_COMP_PAGO)
                                 )
                            )
            --FAC-ELECTRONICA :09.10.2014

               )v
        where  ce.cod_cuadratura in ('004','005') --cuadraturas cobro y anulacion delivery pendiente
        AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    ce.cod_grupo_cia  = cGrupoCia_in
        and    ce.cod_local      = cCod_local_in
        and    ce.cod_grupo_cia = v.cod_grupo_cia
        and    ce.cod_local = v.cod_local
        and    ce.tip_comp = v.tip_comp
        and    (ce.num_serie_local || ce.num_comp_pago) = v.num_pago;



          if nCantDelPendiente = nCantDelReg then
            vResult := 'N';
          end if;

        return vResult;
  END;


  /****************************************************************************/
  FUNCTION F_EXISTE_ANUL_PED_PEND_SIN_REG(
                                 cGrupoCia_in  IN CHAR,
                                 cCod_local_in IN CHAR,
                                 cFechaDia_in  IN CHAR
                                )
  RETURN CHAR
  IS


   nCantAnulPendiente number;
   nCantAnulReg number;

   vResult char(1):= 'S';
  BEGIN
    --ERIOS 14.11.2014 Afinamiento de consultas

        --CANTIDAD DELIVERY PENDIENTE INGRESADOS
        select count(1)
        into   nCantAnulPendiente
        from   ce_cuadratura_caja ce
        where  ce.cod_grupo_cia = cGrupoCia_in
        AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    ce.cod_local = cCod_local_in
        and    ce.cod_cuadratura = '001' --Anulación pendiente de ingreso al sistema.
        AND    ce.fec_crea_cuadratura_caja
                      BETWEEN TO_DATE(cFechaDia_in,'dd/MM/yyyy')-INTERVAL '1' YEAR
                      AND     TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') -1
        and exists (select 1 from vta_comp_pago cp where 1=1
                    and    ce.cod_grupo_cia = cp.cod_grupo_cia
                    and    ce.cod_local = cp.cod_local
                    and    ce.tip_comp = cp.tip_comp_pago
                    AND (
                         NVL(CP.COD_TIP_PROC_PAGO,'0') = '1' AND (ce.num_serie_local || ce.num_comp_pago = NUM_COMP_PAGO_E)
                         or NVL(CP.COD_TIP_PROC_PAGO,'0') = '0' AND (ce.num_serie_local || ce.num_comp_pago = NUM_COMP_PAGO)
                         )
                    );
            --FAC-ELECTRONICA :09.10.2014


        --CANTIDAD DELIVERY PENDIETE REGULARIZADOS DE LOS DECLARADOS
        select count(1)
        into   nCantAnulReg
        from   ce_cuadratura_caja ce,
               (
                select ce.cod_grupo_cia,ce.cod_local,ce.tip_comp, ce.num_serie_local || ce.num_comp_pago num_pago
                        from   ce_cuadratura_caja ce
                where  ce.cod_grupo_cia = cGrupoCia_in
                AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                and    ce.cod_local = cCod_local_in
                and    ce.cod_cuadratura = '001' --Anulación pendiente de ingreso al sistema.
                AND    ce.fec_crea_cuadratura_caja
                              BETWEEN TO_DATE(cFechaDia_in,'dd/MM/yyyy')-INTERVAL '1' YEAR
                              AND     TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') -1
                and exists (select 1 from vta_comp_pago cp where 1=1
                            and    ce.cod_grupo_cia = cp.cod_grupo_cia
                            and    ce.cod_local = cp.cod_local
                            and    ce.tip_comp = cp.tip_comp_pago
                            AND (
                                 NVL(CP.COD_TIP_PROC_PAGO,'0') = '1' AND (ce.num_serie_local || ce.num_comp_pago = NUM_COMP_PAGO_E)
                                 or NVL(CP.COD_TIP_PROC_PAGO,'0') = '0' AND (ce.num_serie_local || ce.num_comp_pago = NUM_COMP_PAGO)
                                 )
                            )
            --FAC-ELECTRONICA :09.10.2014

               )v
        where  ce.cod_cuadratura in ('002') --cuadraturas Regularización de anulados pendientes.
        AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    ce.cod_grupo_cia  = cGrupoCia_in
        and    ce.cod_local      = cCod_local_in
        and    ce.cod_grupo_cia = v.cod_grupo_cia
        and    ce.cod_local = v.cod_local
        and    ce.tip_comp = v.tip_comp
        and    (ce.num_serie_local || ce.num_comp_pago) = v.num_pago;



          if nCantAnulPendiente = nCantAnulReg then
            vResult := 'N';
          end if;

        return vResult;
  END;



/****************************************************************************/
  FUNCTION F_EXISTE_PED_MANUAL_SIN_REG(
                                 cGrupoCia_in  IN CHAR,
                                 cCod_local_in IN CHAR,
                                 cFechaDia_in  IN CHAR
                                )
  RETURN CHAR
  IS


   nCantPedManual number;
   nCantPedManualGenerados number;
   nCantPedManualRegCE number;

   vResult char(1):= 'S';
  BEGIN


        select count(1) --CUENTA CUANTOS COMPROBANTES MANUAL EXISTEN
        into   nCantPedManual
        from   ce_cuadratura_caja e
        where  e.cod_cuadratura = '006'
        AND    E.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    e.cod_grupo_cia = cGrupoCia_in
        and    e.cod_local = cCod_local_in
        --and    e.fec_crea_cuadratura_caja >= to_date('01/12/2008','dd/mm/yyyy')
        and    e.fec_crea_cuadratura_caja
                             BETWEEN add_months(trunc(sysdate,'MM') ,-4)---TO_DATE('01/02/2012' || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                             AND     TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') - 1;

        dbms_output.put_line('HITO 01 nCantPedManual: '||nCantPedManual);

        select count(1)
        into   nCantPedManualGenerados
        from   ce_cuadratura_caja ce,
               vta_comp_pago cp
        where  ce.cod_grupo_cia = cGrupoCia_in
        AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    ce.cod_local = cCod_local_in
        and    ce.cod_cuadratura = '006'
        --and    ce.fec_crea_cuadratura_caja >= to_date('01/12/2008','dd/mm/yyyy')
        and    ce.fec_crea_cuadratura_caja
                             BETWEEN add_months(trunc(sysdate,'MM') ,-4)--TO_DATE('01/02/2012' || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                             AND     TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') - 1
        and    ce.cod_grupo_cia = cp.cod_grupo_cia
        and    ce.cod_local = cp.cod_local
        and    ce.tip_comp = cp.tip_comp_pago
        and    (ce.num_serie_local || ce.num_comp_pago) = --cp.num_comp_pago;
               FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   ;
            --FAC-ELECTRONICA :09.10.2014


        dbms_output.put_line('HITO 02 nCantPedManualGenerados: '||nCantPedManualGenerados);

        select count(1)
        into   nCantPedManualRegCE
        from   ce_cuadratura_caja ce_d,
               ce_cuadratura_caja ce_r
        where  ce_r.cod_grupo_cia = cGrupoCia_in
        and    ce_r.cod_local = cCod_local_in
        AND    CE_D.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    CE_R.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    ce_r.cod_cuadratura = '007'
        and    ce_d.cod_cuadratura = '006'
        --and    ce_d.fec_crea_cuadratura_caja >= to_date('01/12/2008','dd/mm/yyyy')
        and    ce_d.fec_crea_cuadratura_caja
                             BETWEEN add_months(trunc(sysdate,'MM') ,-4) ---TO_DATE('01/02/2012' || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                             AND     TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') - 1
        and    ce_d.cod_grupo_cia = ce_r.cod_grupo_cia
        and    ce_d.cod_local = ce_r.cod_local
        and    ce_d.num_serie_local = ce_r.num_serie_local
        and    ce_d.tip_comp = ce_r.tip_comp
        and    ce_d.num_comp_pago = ce_r.num_comp_pago;

        dbms_output.put_line('HITO 03 nCantPedManualRegCE: '||nCantPedManualRegCE);

          if nCantPedManual = nCantPedManualGenerados THEN
          dbms_output.put_line('HITO 04');
            if nCantPedManual = nCantPedManualRegCE THEN
            dbms_output.put_line('HITO 05');
              vResult := 'N';
            end if;
          end if;

          dbms_output.put_line('HITO 06 vResult: '||vResult);

        return vResult;
  END;








/****************************************************************************/
  FUNCTION F_IS_MOTIVO_CUADRATURA(
                                  cGrupoCia_in  IN CHAR,
                                  cCodCuadratura_in IN CHAR
                                  )
  RETURN CHAR
  IS


   nCantl number;

   vResult char(1):= 'N';
  BEGIN

        select count(1)
        into   nCantl
        from   ce_cuadratura c
        where  c.cod_grupo_cia = cGrupoCia_in
        and    c.cod_cuadratura = cCodCuadratura_in
        and    c.ind_motivo = 'S';

        IF  nCantl >0 THEN
            vResult := 'S';
        END IF;

        return vResult;
  END;

/****************************************************************************/
  FUNCTION F_PRUEBA_IMPR_TERMICA(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 cIpServ_in        IN CHAR,
                                 cCodCupon_in      IN CHAR,
                                 cCodCia_in in char
                                 )
  RETURN VARCHAR2
  IS
 C_INICIO_MSG  VARCHAR2(2000) := '
                                  <html>
                                  <head>
                                  </head>
                                  <body>
                                  <table width="337" border="0">
                                  <tr>
                                   <td width="8">&nbsp;&nbsp;</td>
                                   <td width="319"><table width="316" height="293" border="0">
                                ';


 C_FILA_VACIA  VARCHAR2(2000) :='<tr> '||
                                '<td height="13" colspan="3"></td> '||
                                ' </tr> ';

 C_FIN_MSG     VARCHAR2(2000) := '
                                 </table></td>
                                 </tr>
                                 </table>
                                 </body>
                                 </html>';

  vFila_IMG_Cabecera_MF varchar2(2800):= '';
  vFila_IMG_Cabecera_Consejo varchar2(2800):= '';
  vFila_Cupon     varchar2(22767):= '';

  vMsg_out varchar2(32767):= '';

  vMensajeLocal varchar2(2200):= '';

  vRuta varchar2(500);
      v_vCabecera1 VARCHAR2(500);
      v_vCabecera2 VARCHAR2(500);
  BEGIN

   vRuta := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\';

  select 'Local: '||l.cod_local || '-' || l.desc_corta_local || ' <br> Fecha:' || to_char(sysdate,'dd/mm/yyyy HH24:MI:SS')
  into   vMensajeLocal
  from   pbl_local l
  where  l.cod_grupo_cia = cCodGrupoCia_in
  and    l.cod_local = cCodLocal_in;

    v_vCabecera1 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_1;
    v_vCabecera2 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);

    vFila_IMG_Cabecera_Consejo:= '<tr> <td colspan="4"><div align="center" class="style8">'||
                         '<img src=file:'||
                         v_vCabecera1||
                         ' width="203" height="22" class="style3"></div></td>'||
                         ' </tr> ';

     vFila_IMG_Cabecera_MF:= '<tr> <td colspan="4">'||
                         '<img src=file:'||
                         v_vCabecera2||
                         ' width="300" height="90"></td>'||
                         '</tr> ';
     --FV 19.08.08
     vFila_Cupon := '<tr>' ||
                    '<td height="50" colspan="3">'||
                    '<div align="center" class="style8">'||
                     -- '<img src=file://///C:/'||cCodCupon_in||'.jpg width="229" height="107" class="style3"></div></td></tr>';
                     -- '<img src=file:'||vRuta||''||cCodCupon_in||'.jpg width="229" height="107" class="style3"></div></td></tr>';
                     '<<codigoBarra>></div></td></tr>'; -- kmoncada 30.07.2014 codigo de barra generado en el farmaventa



     vMsg_out := C_INICIO_MSG ||
                 '<tr>
                  <td height="21" colspan="3" align="center"
                          style="font:Arial, Helvetica, sans-serif">
                          ****PRUEBA DE IMPRESION TERMICA*****
                  </td>
                  </tr>'||
                 C_FILA_VACIA ||
                 '<tr>
                  <td height="21" colspan="3" align="center"
                          style="font:Arial, Helvetica, sans-serif">'||
                  vMensajeLocal||
                  '</td>
                  </tr>'||
                 '<tr>
                  <td height="21" colspan="3" align="left"
                          style="font:Arial, Helvetica, sans-serif">
                 ...imprimiendo imagenes..
                  </td>
                  </tr>'||
                 vFila_IMG_Cabecera_MF  ||
                 C_FILA_VACIA ||
                 vFila_IMG_Cabecera_Consejo ||
                 C_FILA_VACIA ||
                 vFila_Cupon ||
                 '<tr>
                  <td height="21" colspan="3" style="font:Arial, Helvetica, sans-serif">Si la impresion no esta centrada verifique que el modelo de impresora termica configurada sea la misma que posee.Para esto ir a administracion/mantenimiento/parametro</td>
                  </tr>'||
                 C_FIN_MSG ;

                 return vMsg_out;



  END;
/****************************************************************************/


 /* ***************************************************************** */

 /* ***************************************************************** */

 /* ***************************************************************** */

PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL)
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_INTER_CE;
    CCReceiverAddress VARCHAR2(120) := 'joliva, operador';
    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN

   select mail_local
   into   ReceiverAddress
   from   pbl_local
   where  cod_grupo_cia = cCodGrupoCia_in
   and    cod_local = cCodLocal_in;

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
                             vAsunto_in||v_vDescLocal,--'VIAJERO EXITOSO: '||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             NVL(cCopiaCorreo,CCReceiverAddress),
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;


 /****************************************************************************/


PROCEDURE P_VALIDACION_CAJA_ELECTRONICA(
                                 cGrupoCia_in  IN CHAR,
                                 cCod_local_in IN CHAR,
                                 cFechaDia_in  IN CHAR DEFAULT NULL)


  IS

   vResult_EXISTE_DESFASE_COMP char (1):='N';
   vResult_EXISTE_DEL_PEND_SIN char (1):='N';
   vResult_EXISTE_PED_MANUAL char (1):='N';
   vResult_EXISTE_ANUL_PED_PEND char (1):='N';


   vMensaje1  varchar (500);
   vMensaje2  varchar (500);
   vMensaje3  varchar (500);
   vMensaje4  varchar (500);

   vMensajeDetalle1   varchar2 (900);
   vMensajeDetalle2   varchar2 (900);
   vMensajeDetalle3   varchar2 (900);
   vMensajeDetalle4   varchar2 (900);

   vMensajeFila1   varchar2 (5200);
   vMensajeFila2   varchar2 (5200);
   vMensajeFila3   varchar2 (5200);
   vMensajeFila4   varchar2 (5200);

   vMensajeTotal1  varchar2 (8000);
   vMensajeTotal2  varchar2 (8000);
   vMensajeTotal3  varchar2 (8000);
   vMensajeTotal4  varchar2 (8000);

   vMensajeTotal   varchar2 (32700);

   cFechaSugerida DATE ;
   cFechaMensaje  varchar (150);

 --------------------------------------CURSOR COMPROBANTES DESFASADOS---------------------------------------------------------

CURSOR  curValComprobantes  IS
SELECT
       DECODE(TIP_COMP_PAGO,'01','BOL','02','FACT') TIP_COMP_PAGO,
       SERIE ||'-'|| LPAD(SUBSTR(NUM_COMP_PAGO,-7)+1,7,'0') SERIE_NUM_COMP_PAGO,
       TRUNC(FEC_CREA_COMP_PAGO) FEC_CREA_COMP_PAGO ,
       (DIF-1) FALTANTES
FROM
     (
           SELECT
                  CD.COD_LOCAL, CD.TIP_COMP_PAGO, SERIE, CP.FEC_CREA_COMP_PAGO,
                  CP.NUM_COMP_PAGO,
                  LEAD(CP.NUM_COMP_PAGO, 1) OVER (ORDER BY CD.TIP_COMP_PAGO, CP.NUM_COMP_PAGO) - CP.NUM_COMP_PAGO DIF,
                  LEAD(CP.NUM_COMP_PAGO, 1) OVER (ORDER BY CD.TIP_COMP_PAGO, CP.NUM_COMP_PAGO) SIG
           FROM VTA_COMP_PAGO CP,
                AUX_RANGO_COMP_DIA CD
           WHERE CP.COD_GRUPO_CIA = cGrupoCia_in
             AND CP.COD_LOCAL = CD.COD_LOCAL
             AND CP.TIP_COMP_PAGO = CD.TIP_COMP_PAGO
             AND SUBSTR(CP.NUM_COMP_PAGO,1,3) = CD.SERIE
             AND CP.NUM_COMP_PAGO BETWEEN CD.MIN_COMP AND CD.MAX_COMP
     )
          WHERE DIF BETWEEN 2 AND 5000000000
          ORDER BY
          TIP_COMP_PAGO,
          NUM_COMP_PAGO,
          TRUNC(FEC_CREA_COMP_PAGO),
          DIF-1;

                v_rcurValComprobantes curValComprobantes%ROWTYPE;

 --------------------------------------CURSOR DELIVERY PENDIENTES SIN REGULARIZAR---------------------------------------------

 CURSOR curValDelivery(FECHA DATE)  IS



        --CANTIDAD DELIVERY PENDIENTE INGRESADOS
        select cp.fec_crea_comp_pago,DECODE(ce.tip_comp,'01','Boleta','02','Factura') tip_comp, ce.num_serie_local, ce.num_comp_pago, ce.mon_total

        from   ce_cuadratura_caja ce,
               vta_comp_pago cp
        where  ce.cod_grupo_cia = cGrupoCia_in
        AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    ce.cod_local = cCod_local_in
        and    ce.cod_cuadratura = '003' --cuadratura delivery pendiente
        AND    ce.fec_crea_cuadratura_caja
                      BETWEEN (TO_DATE (TO_CHAR (TRUNC (SYSDATE, 'MM'), 'DD/MM/YYYY '))) --El primer dia del mes actual
                       AND  FECHA - 1
        and    ce.cod_grupo_cia = cp.cod_grupo_cia
        and    ce.cod_local = cp.cod_local
        and    ce.tip_comp = cp.tip_comp_pago
        and    (ce.num_serie_local || ce.num_comp_pago) = --cp.num_comp_pago
               FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)

            --FAC-ELECTRONICA :09.10.2014


        minus

        --CANTIDAD DELIVERY PENDIETE REGULARIZADOS DE LOS DECLARADOS
        select v.fec_crea_comp_pago,DECODE(ce.tip_comp,'01','Boleta','02','Factura') tip_comp, ce.num_serie_local, ce.num_comp_pago, ce.mon_total

        from   ce_cuadratura_caja ce,
               (
                select cp.fec_crea_comp_pago,ce.cod_grupo_cia,ce.cod_local,ce.tip_comp, ce.num_serie_local || ce.num_comp_pago num_pago
                from   ce_cuadratura_caja ce,
                       vta_comp_pago cp
                where  ce.cod_grupo_cia = cGrupoCia_in
                AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                and    ce.cod_local = cCod_local_in
                and    ce.cod_cuadratura = '003'
                AND    ce.fec_crea_cuadratura_caja
                      BETWEEN (TO_DATE (TO_CHAR (TRUNC (SYSDATE , 'MM'), 'DD/MM/YYYY ')))
                      AND  FECHA
                and    ce.cod_grupo_cia = cp.cod_grupo_cia
                and    ce.cod_local = cp.cod_local
                and    ce.tip_comp = cp.tip_comp_pago
                and    (ce.num_serie_local || ce.num_comp_pago) = --cp.num_comp_pago
               FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)

            --FAC-ELECTRONICA :09.10.2014

               )v
        where  ce.cod_cuadratura in ('004','005') --cuadraturas cobro y anulacion delivery pendiente
        and    ce.cod_grupo_cia  = cGrupoCia_in
        AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    ce.cod_local      = cCod_local_in
        and    ce.cod_grupo_cia = v.cod_grupo_cia
        and    ce.cod_local = v.cod_local
        and    ce.tip_comp = v.tip_comp
        and    (ce.num_serie_local || ce.num_comp_pago) = v.num_pago;



         v_rCurValDelivery curValDelivery%ROWTYPE;


  ----------------------------------CURSOR PEDIDOS MANUALES SIN REGULARIZAR-----------------------------


      CURSOR curValManualSR(FECHA DATE)   IS

      (
      select e.fec_crea_cuadratura_caja, DECODE(e.tip_comp,'01','Boleta','02','Factura') tip_comp, e.num_serie_local, e.num_comp_pago, e.mon_total

        from   ce_cuadratura_caja e
        where  e.cod_cuadratura = '006'
        AND    E.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    e.cod_grupo_cia = cGrupoCia_in
        and    e.cod_local = cCod_local_in

        and    e.fec_crea_cuadratura_caja
                            -- BETWEEN TO_DATE('01/02/2008' || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                             BETWEEN (TO_DATE (TO_CHAR (TRUNC (SYSDATE, 'MM'), 'DD/MM/YYYY ')))
                            -- AND     TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') - 1
                              AND  FECHA - 1
        MINUS

    SELECT  ce.fec_crea_cuadratura_caja, DECODE(ce.tip_comp,'01','Boleta','02','Factura') tip_comp, ce.num_serie_local, ce.num_comp_pago, ce.mon_total

        from   ce_cuadratura_caja ce,
               vta_comp_pago cp
        where  ce.cod_grupo_cia = cGrupoCia_in
        AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    ce.cod_local = cCod_local_in
        and    ce.cod_cuadratura = '006'

        and    ce.fec_crea_cuadratura_caja
--                 BETWEEN TO_DATE('01/02/2008' || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                             BETWEEN (TO_DATE (TO_CHAR (TRUNC (SYSDATE, 'MM'), 'DD/MM/YYYY ')))
                            -- AND     TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') -- - 1
                            AND  FECHA
        and    ce.cod_grupo_cia = cp.cod_grupo_cia
        and    ce.cod_local = cp.cod_local
        and    ce.tip_comp = cp.tip_comp_pago
        and    (ce.num_serie_local || ce.num_comp_pago) = --cp.num_comp_pago);
               FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   );
            --FAC-ELECTRONICA :09.10.2014



       v_rcurValManualSR curValManualSR%ROWTYPE;

   ---------------------------CURSOR ANULACION DE PEDIDOS PENDIENTES SIN REGULARIZAR------------------------------


        CURSOR  curValAnulPEDPEND(FECHA DATE) IS
        (select cp.fec_crea_comp_pago,cp.cod_grupo_cia,cp.cod_local, cp.num_ped_vta,DECODE(ce.tip_comp,'01','Boleta','02','Factura') tip_comp, ce.num_serie_local ,cp.num_comp_pago, cp.tip_comp_pago,ce.mon_total--cp.fec_crea_comp_pago,ce.fec_crea_cuadratura_caja, DECODE(ce.tip_comp,'01','Boleta','02','Factura') tip_comp, ce.num_serie_local, ce.num_comp_pago, ce.mon_total,ce.cod_cuadratura

        from   ce_cuadratura_caja ce,
               vta_comp_pago cp
        where  ce.cod_grupo_cia = cGrupoCia_in
        AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    ce.cod_local = cCod_local_in
       and    ce.cod_cuadratura ='001' --Anulación pendiente de ingreso al sistema.
        AND    ce.fec_crea_cuadratura_caja
--                 BETWEEN TO_DATE('01/02/2008' || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                        BETWEEN (TO_DATE (TO_CHAR (TRUNC (SYSDATE, 'MM'), 'DD/MM/YYYY ')))
                       --AND     TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') - 1
                       AND  FECHA - 1
        and    ce.cod_grupo_cia = cp.cod_grupo_cia
        and    ce.cod_local = cp.cod_local
        and    ce.tip_comp = cp.tip_comp_pago
        and    (ce.num_serie_local || ce.num_comp_pago) = --cp.num_comp_pago
               FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)

            --FAC-ELECTRONICA :09.10.2014


        MINUS

        select v.fec_crea_comp_pago,v.cod_grupo_cia,v.cod_local, v.num_ped_vta,DECODE(v.tip_comp_pago,'01','Boleta','02','Factura') ,ce.num_serie_local ,v.num_comp_pago, v.tip_comp_pago,v.mon_total--v.fec_crea_comp_pago,ce.fec_crea_cuadratura_caja, ce.tip_comp, ce.num_serie_local, ce.num_comp_pago, ce.mon_total,'001'--ce.cod_cuadratura

        from   ce_cuadratura_caja ce,
               (
                select cp.fec_crea_comp_pago,cp.cod_grupo_cia, cp.cod_local, cp.num_ped_vta, cp.num_comp_pago, cp.tip_comp_pago,ce.mon_total, ce.num_serie_local || ce.num_comp_pago num_pago
                from   ce_cuadratura_caja ce,
                       vta_comp_pago cp
                where  ce.cod_grupo_cia = cGrupoCia_in
                AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                and    ce.cod_local = cCod_local_in
                and    ce.cod_cuadratura  = '001' --Anulación pendiente de ingreso al sistema.
                AND    ce.fec_crea_cuadratura_caja
--                 BETWEEN TO_DATE('01/02/2008' || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                       BETWEEN (TO_DATE (TO_CHAR (TRUNC (SYSDATE, 'MM'), 'DD/MM/YYYY '))) --EL PRIMER DIA DEL MES ACTUAL
                       --AND     TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') -- - 1
                       AND  FECHA
                and    ce.cod_grupo_cia = cp.cod_grupo_cia
                and    ce.cod_local = cp.cod_local
                and    ce.tip_comp = cp.tip_comp_pago
                and    (ce.num_serie_local || ce.num_comp_pago) = --cp.num_comp_pago
               FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)

            --FAC-ELECTRONICA :09.10.2014

               ) v
        where  ce.cod_cuadratura = '002' --cuadraturas Regularización de anulados pendientes.
        and    ce.cod_grupo_cia  = cGrupoCia_in
        AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        and    ce.cod_local      = cCod_local_in
        and    ce.cod_grupo_cia = v.cod_grupo_cia
        and    ce.cod_local = v.cod_local
        and    ce.tip_comp = v.tip_comp_pago
        and    (ce.num_serie_local || ce.num_comp_pago) = v.num_comp_pago);

            v_rcurValAnulPEDPEND curValAnulPEDPEND%ROWTYPE;




     BEGIN


        -----------------------------------------VALIDANDO LA FECHA----------------------------------------------------------------------
         IF cFechaDia_in IS NULL THEN
            cFechaSugerida := TRUNC(sysdate);
            DBMS_OUTPUT.PUT_LINE('' ||cFechaSugerida);
            ELSE
            cFechaSugerida := TO_DATE(cFechaDia_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');
             DBMS_OUTPUT.PUT_LINE('' ||cFechaSugerida);
         END IF;

        vResult_EXISTE_DESFASE_COMP := F_EXISTE_DESFASE_COMP( cGrupoCia_in,cCod_local_in , TO_CHAR (cFechaSugerida, 'DD/MM/YYYY '))  ;
         ------------------------------------------------------------------------------------------------------------------
         --vResult_EXISTE_DEL_PEND_SIN :='S'; F_EXISTE_DEL_PEND_SIN_REG(cGrupoCia_in  ,cCod_local_in , cFechaDia_in ) ;
         --vResult_EXISTE_PED_MANUAL:='S'; F_EXISTE_PED_MANUAL_SIN_REG(cGrupoCia_in  , cCod_local_in ,cFechaDia_in );
         --vResult_EXISTE_ANUL_PED_PEND :='S'; F_EXISTE_ANUL_PED_PEND_SIN_REG(cGrupoCia_in ,cCod_local_in,cFechaDia_in );


         ------------------------------------COMPROBANTES DESFASADOS--------------------------------------------------------

         FOR v_rcurValComprobantes IN curValComprobantes

         LOOP

         IF  v_rcurValComprobantes.Faltantes IS NOT NULL THEN


            vMensajeDetalle1 :=  '<TR><TD>' || v_rcurValComprobantes.Fec_Crea_Comp_Pago|| '</TD>' ||'<TD>' || v_rcurValComprobantes.Tip_Comp_Pago || '</TD>' || '<TD>' || v_rcurValComprobantes.Serie_Num_Comp_Pago || '</TD>' ||'<TD>' || v_rcurValComprobantes.Faltantes || '</TD></TR>';
             vMensajeFila1 := vMensajeFila1 || vMensajeDetalle1;
           END IF;

             vResult_EXISTE_DESFASE_COMP := 'S';


         END LOOP;


          IF (vResult_EXISTE_DESFASE_COMP = 'S') then

                  vMensaje1 :='<BR> -COMPROBANTES DESFASADOS  <BR>' || '<TABLE  BORDER=1>' || '<TR><TD>' || 'Fecha' || '</TD>' || '<TD>' ||'Tipo' || '</TD>' || '<TD>' || 'Comprobante' || '</TD>' ||'<TD>' ||'Faltantes' || '</TD></TR>';
                  vMensajeTotal1 := vMensaje1 || vMensajeFila1  ||'</TABLE>' ;

              END IF;


            DBMS_OUTPUT.PUT_LINE('' ||vResult_EXISTE_DESFASE_COMP);




         --------------------------------DELIVERY PENDIENTES SIN REGULARIZAR-------------------------------------------------


              FOR v_rCurValDelivery IN curValDelivery(cFechaSugerida)
              LOOP

              IF v_rCurValDelivery.Num_Comp_Pago IS NOT NULL  THEN


              vMensajeDetalle2 := '<TR><TD>' || v_rCurValDelivery.Fec_Crea_Comp_Pago|| '</TD>' ||'<TD>' || v_rCurValDelivery.Num_Serie_Local ||'-' || v_rCurValDelivery.num_comp_pago || '</TD>' || '<TD>' || v_rCurValDelivery.tip_comp || '</TD>' ||'<TD>' ||  v_rCurValDelivery.mon_total || '</TD></TR>';
              vMensajeFila2 := vMensajeFila2 || vMensajeDetalle2;
              END IF;

                vResult_EXISTE_DEL_PEND_SIN :='S';

              END LOOP;

              IF (vResult_EXISTE_DEL_PEND_SIN = 'S') then

                  vMensaje2 :='<BR> -DELIVERY PENDIENTES SIN REGULARIZAR <BR>' || '<TABLE  BORDER=1>' || '<TR><TD>' || 'Fecha' || '</TD>' || '<TD>' ||'Numero de Comprobante' || '</TD>' || '<TD>' ||'Tipo' || '</TD>' ||'<TD>' ||'Monto Total' || '</TD></TR>';
                  vMensajeTotal2 := vMensaje2 || vMensajeFila2  ||'</TABLE>' ;

              END IF;


           ------------------------------PEDIDOS MANUALES SIN REGULARIZAR---------------------------------------------------


              FOR v_rcurValManualSR IN curValManualSR(cFechaSugerida)
              LOOP
              IF v_rcurValManualSR.num_comp_pago IS NOT NULL  THEN



             vMensajeDetalle3 := '<TR><TD>' || v_rcurValManualSR.fec_crea_cuadratura_caja  || '</TD>'|| '<TD>' || v_rcurValManualSR.Num_Serie_Local ||'-' || v_rcurValManualSR.num_comp_pago || '</TD>' || '<TD>' ||  v_rcurValManualSR.tip_comp|| '</TD>' ||'<TD>' || v_rcurValManualSR.mon_total  || '</TD></TR>';
              vMensajeFila3 := vMensajeFila3 || vMensajeDetalle3;


              END IF;
                vResult_EXISTE_PED_MANUAL :='S';
              END LOOP;


               IF (vResult_EXISTE_PED_MANUAL = 'S') then



                 vMensaje3 :='<BR> -PEDIDOS MANUALES SIN REGULARIZAR <BR>' || '<TABLE  BORDER=1>' || '<TR><TD>' || 'Fecha' || '</TD>' || '<TD>' ||'Numero de Comprobante' || '</TD>' || '<TD>' ||'Tipo' || '</TD>' ||'<TD>' ||'Monto Total' || '</TD></TR>';
                  vMensajeTotal3 := vMensaje3 || vMensajeFila3  ||'</TABLE>' ;



              END IF;



            ------------------------------ANULACION DE PEDIDOS PENDIENTES SIN REGULARIZAR------------------------------------------


              FOR v_rcurValAnulPEDPEND IN curValAnulPEDPEND(cFechaSugerida)
              LOOP
              IF v_rcurValAnulPEDPEND.num_comp_pago IS NOT NULL  THEN


              vMensajeDetalle4 := '<TR><TD>' || v_rcurValAnulPEDPEND.Fec_Crea_Comp_Pago|| '</TD>' ||'<TD>' ||  v_rcurValAnulPEDPEND.Num_Serie_Local ||'-' || v_rcurValAnulPEDPEND.num_comp_pago || '</TD>' || '<TD>' || v_rcurValAnulPEDPEND.tip_comp || '</TD>' ||'<TD>' ||  v_rcurValAnulPEDPEND.mon_total || '</TD></TR>';
              vMensajeFila4 := vMensajeFila4 || vMensajeDetalle4;




              END IF;
                vResult_EXISTE_ANUL_PED_PEND :='S';
              END LOOP;


              IF (vResult_EXISTE_ANUL_PED_PEND = 'S') then

                   vMensaje4 :='<BR> -ANULACION DE PEDIDOS PENDIENTES SIN REGULARIZAR <BR>' || '<TABLE  BORDER=1>' || '<TR><TD>' || 'Fecha' || '</TD>' || '<TD>' ||'Numero de Comprobante' || '</TD>' || '<TD>' ||'Tipo' || '</TD>' ||'<TD>' ||'Monto Total' || '</TD></TR>';

                  vMensajeTotal4 := vMensaje4 || vMensajeFila4  ||'</TABLE>' ;

              END IF;

           ---------------------------------------ENVIAR EMAIL---------------------------------------------------------------------------------

       IF ( vResult_EXISTE_DESFASE_COMP ='S' OR vResult_EXISTE_DEL_PEND_SIN ='S' OR vResult_EXISTE_PED_MANUAL ='S' OR vResult_EXISTE_ANUL_PED_PEND ='S') THEN


          cFechaMensaje := TO_CHAR (TRUNC (SYSDATE, 'MM'), 'DD/MM/YYYY ') || ' AL ' || (cFechaSugerida - 1);
          vMensajeTotal := vMensajeTotal1 || vMensajeTotal2 || vMensajeTotal3 || vMensajeTotal4;

             INT_ENVIA_CORREO_INFORMACION(cGrupoCia_in,cCod_local_in,
                                                'ALERTA CAJA ELECTRONICA:',
                                                'ALERTA',
                                                'EXISTEN PROBLEMAS EN EL RANGO DE FECHAS : '|| cFechaMensaje ||'</B>'||
                                                '<BR> <I>VERIFIQUE:</I> <BR>'|| vMensajeTotal || '<B>');


         END IF;


     END ;


/*********************************************************************************************/

   /* ******************************************************************* */

   FUNCTION GET_TIPO_IMPR_CONSEJO_X_IP(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR)
    RETURN VARCHAR2
    IS
    vTipoImpr           VARCHAR2(10);
    vIp      VARCHAR2(15);
    BEGIN
        SELECT SYS_CONTEXT('USERENV','IP_ADDRESS')  INTO vIp FROM DUAL;

        SELECT A.TIPO_IMPR_TERMICA   INTO vTipoImpr
        FROM VTA_IMPR_LOCAL_TERMICA A
        WHERE
        A.COD_GRUPO_CIA =cCodGrupoCia_in AND
        A.COD_LOCAL =cCodLocal_in AND
        A.SEC_IMPR_LOC_TERM IN (SELECT B.SEC_IMPR_LOC_TERM FROM VTA_IMPR_IP B WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
                            AND B.COD_LOCAL=cCodLocal_in AND B.IP=vIp);
        RETURN  vTipoImpr;
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
          SELECT NVL(TRIM(L.TIPO_IMPR_TERMICA),'01')
          INTO   vTipoImpr
          FROM   PBL_LOCAL L
          WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    L.COD_LOCAL = cCodLocal_in;
        RETURN  vTipoImpr;
   END;

   /*********************************************************************************************/

  FUNCTION F_CHAR_OBT_DIAS_ELIMINAR_TXT
  RETURN CHAR

  IS
  vDias VARCHAR2(500);
  BEGIN

  SELECT TRIM(LLAVE_TAB_GRAL) into vDias
  FROM PBL_TAB_GRAL
  WHERE ID_TAB_GRAL='278';


       RETURN vDias;
  END;

  /* ************************************************************************************ */
  PROCEDURE CAJ_REGISTRA_TMP_INI_FIN_COBRO(cCodGrupoCia_in IN CHAR,
							  			                   cCod_Local_in   IN CHAR,
                                         cNum_Ped_Vta_in IN CHAR,
							  			                   cTmpTipo_in 	   IN CHAR)
 IS
  BEGIN

      IF cTmpTipo_in='I' THEN
         UPDATE VTA_PEDIDO_VTA_CAB
         SET FEC_INI_COBRO=SYSTIMESTAMP
         WHERE
         COD_GRUPO_CIA=cCodGrupoCia_in AND
         COD_LOCAL=cCod_Local_in AND
         NUM_PED_VTA=cNum_Ped_Vta_in;

      ELSIF cTmpTipo_in='F' THEN
         UPDATE VTA_PEDIDO_VTA_CAB
         SET FEC_FIN_COBRO=SYSTIMESTAMP
         WHERE
         COD_GRUPO_CIA=cCodGrupoCia_in AND
         COD_LOCAL=cCod_Local_in AND
         NUM_PED_VTA=cNum_Ped_Vta_in;
      END IF;
  END;
 /* ************************************************************************************ */
 FUNCTION F_CHAR_GET_IND_IMP_COLOR
 RETURN CHAR
 is
  vInd VARCHAR2(500);
 BEGIN
  begin
  SELECT TRIM(LLAVE_TAB_GRAL) into vInd
  FROM PBL_TAB_GRAL
  WHERE ID_TAB_GRAL='281';
  exception
  when others then
       vInd := 'N';
  end;
  return vInd;
 END;
/* ************************************************************************************ */
  FUNCTION F_VAR2_GET_MENSAJE(cGrupoCia_in  IN CHAR,
                             cCod_Rol_in   IN CHAR)
  RETURN VARCHAR2
  IS
  vIndParam varchar2(100);
  vCodRol   char(3);
  nNumPedDel number;
   vFecha_Max varchar2(3000);
  BEGIN

      BEGIN

      vCodRol := trim(cCod_Rol_in);
      select NVL(M.TEXTO,' ')
      INTO   vIndParam
      from   PBL_MENSAJE_PERSONAL M,
             PBL_ROL_X_MENSAJE RM
      WHERE  RM.COD_ROL = vCodRol
      AND    RM.SECMENSAJE = M.SECMENSAJE
      --AND    TRUNC(SYSDATE) BETWEEN TRUNC(M.FECH_INI) AND TRUNC(M.FECH_FIN)
      AND    M.ESTADO = 'A';

      /*
          SELECT 1
          FROM DUAL
          WHERE TO_CHAR(SYSDATE,'HH24MISS') BETWEEN '185834' AND '195900'
      */


      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vIndParam := '';
      END;

      return vIndParam;
    END ;

 /* ************************************************************************************ */
 FUNCTION F_VAR2_GET_EMAIL_COBRO
 RETURN VARCHAR2
 is
  vInd VARCHAR2(500);
 BEGIN
  SELECT LLAVE_TAB_GRAL into vInd
  FROM PBL_TAB_GRAL
  WHERE ID_TAB_GRAL='282';

  return vInd;
 END;

 /* ************************************************************************************ */
 FUNCTION F_VAR2_GET_EMAIL_ANULACION
 RETURN VARCHAR2
 is
  vInd VARCHAR2(500);
 BEGIN
  SELECT LLAVE_TAB_GRAL into vInd
  FROM PBL_TAB_GRAL
  WHERE ID_TAB_GRAL='283';

  return vInd;
 END;

 /* ************************************************************************************ */
 FUNCTION F_VAR2_GET_EMAIL_IMPRESION
 RETURN VARCHAR2
 is
  vInd VARCHAR2(500);
 BEGIN
	vInd := F_VAR_GET_FARMA_EMAIL('22');

  return vInd;
 END;

 /* ************************************************************************************ */
 FUNCTION F_VAR2_GET_IND_VER_STOCK
 RETURN VARCHAR2
 is
  vInd VARCHAR2(500);
  --289', 'PTO_VENTA', 'IND_VER_STOCK'
 BEGIN
  SELECT LLAVE_TAB_GRAL into vInd
  FROM PBL_TAB_GRAL
  WHERE ID_TAB_GRAL='289';

  return vInd;
 END;

 /****************************************************************************************************/
  FUNCTION F_GET_CHAR_IND_VER_CUPONES RETURN CHAR
  IS
    v_cIndVerCupones CHAR;
  BEGIN
    BEGIN
       SELECT A.LLAVE_TAB_GRAL INTO v_cIndVerCupones
       FROM PBL_TAB_GRAL A
       WHERE A.ID_TAB_GRAL = '310'
       AND A.COD_APL = 'PTO_VENTA'
       AND A.COD_TAB_GRAL = 'IND_VER_CUPONES';
    EXCEPTION WHEN OTHERS THEN
       v_cIndVerCupones := 'N';
    END;
    RETURN UPPER(TRIM(v_cIndVerCupones));

  END;

   /****************************************************************************************************/
  FUNCTION F_GET_CHAR_IND_VER_PED_DELIV RETURN CHAR
  IS
    v_cIndVerPedDelivery CHAR;
  BEGIN
    BEGIN
       SELECT A.LLAVE_TAB_GRAL INTO v_cIndVerPedDelivery
       FROM PBL_TAB_GRAL A
       WHERE A.ID_TAB_GRAL = '311'
       AND A.COD_APL = 'PTO_VENTA'
       AND A.COD_TAB_GRAL = 'IND_PED_DELIVERY';
    EXCEPTION WHEN OTHERS THEN
       v_cIndVerPedDelivery := 'N';
    END;
    RETURN UPPER(TRIM(v_cIndVerPedDelivery));

  END;

  /****************************************************************************/
  FUNCTION F_IMPR_TERMICA_STICK(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 cIpServ_in        IN CHAR,
                                 cCodCupon_in      IN CHAR,
                                 cInicio_in      IN CHAR,
                                 cFin_in      IN CHAR,
                                 cCodCia_in IN CHAR
                                 )
  RETURN VARCHAR2
  IS



/*   C_INICIO_MSG  VARCHAR2(2000) := '
                                  <html>
                                  <head>
                                  </head>
                                  <body>
                                  <table width="337" border="0">
                                  <tr>
                                   <td width="8">&nbsp;&nbsp;</td>
                                   <td width="319"><table width="316" height="293" border="0">
                                ';*/



   C_INICIO_MSG  VARCHAR2(2000) := '<html>'||
                                  '<head>'||
                                  '</head>'||
                                  '<body style="margin-top: 0px;">'||
                                  --'<table border=0>';
                                 '<table width="750" height="250" border="1" cellpading = "0" cellspacing="0">';

/* C_INICIO_MSG  VARCHAR2(2000) := '
                                  <html>
                                  <head>
                                  </head>
                                  <body>
                                  <table width="337" border="1">
                                      <tr>
                                       <td width="8"><table width="316" height="293" border="0"><TR>PRUEBA 1</TR></TABLE></td>
                                       <td width="319"><table width="316" height="293" border="0"><TR>PRUEBA 2</TR></TABLE></td>
                                      </tr>
                                   </table>
                                 </body>
                                 </html>
                                ';*/


 C_FILA_VACIA  VARCHAR2(2000) :='<tr> '||
                                '<td height="8" colspan="3"></td> '||
                                ' </tr> ';


 C_FIN_MSG     VARCHAR2(2000) := '</table>'||
                                 '</body>'||
                                 '</html>';

  vFila_IMG_Cabecera_MF varchar2(2800):= '';
  vFila_IMG_Cabecera_Consejo varchar2(2800):= '';
  vFila_Cupon     varchar2(22767):= '';

  vMsg_out varchar2(32767):= '';
    vMsg_out2 varchar2(32767):= '';

  vMensajeLocal varchar2(2200):= '';

  vRuta varchar2(500);
    v_vCabecera1 VARCHAR2(500);
    v_vCabecera2 VARCHAR2(500);
  CURSOR curPrecios IS

/*
SELECT *
FROM   (
        SELECT F.*,ROWNUM FILA
        FROM   (
                  select d.cod_prod,d.desc_prod,D.DESC_UNID_PRESENT,e.precios,--D.VAL_PREC_VTA_VIG,
                         LEAD(d.COD_PROD, 1) OVER (ORDER BY d.cod_prod) AS COD_PROD_2,
                         LEAD(d.desc_prod, 1) OVER (ORDER BY d.cod_prod) AS DESC_PROD_2,
                         LEAD(d.DESC_UNID_PRESENT, 1) OVER (ORDER BY d.cod_prod) AS DESC_UNID_PRESENT_2,
                         --LEAD(VAL_PREC_VTA_VIG, 1) OVER (ORDER BY cod_prod) AS VAL_PREC_VTA_2
                         LEAD(e.precios, 1) OVER (ORDER BY d.cod_prod) AS VAL_PREC_VTA_2
                  from   lgt_prod d,
                         aux_cambio_precio e
                  where  --rownum <= 5
                      d.cod_grupo_cia=e.cod_grupo_cia
                  and    e.cod_local=cCodLocal_in
                  and    d.cod_prod=e.cod_prod
                )F
       )
WHERE  MOD(FILA,2) != 0;
    */
    select *
from   (
        SELECT g.*,ROWNUM FILA_sup
        FROM   (
                SELECT F.*,ROWNUM FILA
                FROM   (
                          select distinct d.cod_prod,d.desc_prod,D.DESC_UNID_PRESENT,e.precios,--D.VAL_PREC_VTA_VIG,
                                 LEAD(d.COD_PROD, 1) OVER (ORDER BY d.cod_prod) AS COD_PROD_2,
                                 LEAD(d.desc_prod, 1) OVER (ORDER BY d.cod_prod) AS DESC_PROD_2,
                                 LEAD(d.DESC_UNID_PRESENT, 1) OVER (ORDER BY d.cod_prod) AS DESC_UNID_PRESENT_2,
                                 --LEAD(VAL_PREC_VTA_VIG, 1) OVER (ORDER BY cod_prod) AS VAL_PREC_VTA_2
                                 LEAD(e.precios, 1) OVER (ORDER BY d.cod_prod) AS VAL_PREC_VTA_2,to_char(e.fecha_cambio,'dd-mm-yyyy') fecha_cambio
                          from   lgt_prod d,
                                 aux_cambio_precio e
                          where  --rownum <= 5 and
                              d.cod_grupo_cia=e.cod_grupo_cia
                              --Autor Luigy Terrazos    Fecha 01/03/2013
                              --Se cambio el codigo 058 por el parametro cCodLocal_in
                          and    e.cod_local='071'
                          and    d.cod_prod=e.cod_prod
                          and    e.ind_impresora='N'
                          and    e.cod_prod=(select cod_prod from lgt_cod_barra where cod_barra=cCodCupon_in)
                        )F
               )g
        WHERE  MOD(FILA,2) != 0
        )
where   FILA_sup between cInicio_in and cFin_in;

v_rCurPrecio curPrecios%ROWTYPE;
  aux varchar2(3000);
  num_reg NUMBER;
  BEGIN
  select count(*)
  into   num_reg
from   (
        SELECT g.*,ROWNUM FILA_sup
        FROM   (
                SELECT F.*,ROWNUM FILA
                FROM   (
                          select distinct d.cod_prod,d.desc_prod,D.DESC_UNID_PRESENT,e.precios,--D.VAL_PREC_VTA_VIG,
                                 LEAD(d.COD_PROD, 1) OVER (ORDER BY d.cod_prod) AS COD_PROD_2,
                                 LEAD(d.desc_prod, 1) OVER (ORDER BY d.cod_prod) AS DESC_PROD_2,
                                 LEAD(d.DESC_UNID_PRESENT, 1) OVER (ORDER BY d.cod_prod) AS DESC_UNID_PRESENT_2,
                                 --LEAD(VAL_PREC_VTA_VIG, 1) OVER (ORDER BY cod_prod) AS VAL_PREC_VTA_2
                                 LEAD(e.precios, 1) OVER (ORDER BY d.cod_prod) AS VAL_PREC_VTA_2,to_char(e.fecha_cambio,'dd-mm-yyyy') fecha_cambio
                          from   lgt_prod d,
                                 aux_cambio_precio e
                          where  --rownum <= 5 and
                              d.cod_grupo_cia=e.cod_grupo_cia
                              --Autor Luigy Terrazos    Fecha 01/03/2013
                              --Se cambio el codigo 058 por el parametro cCodLocal_in
                          and    e.cod_local='071'
                          and    d.cod_prod=e.cod_prod
                          and    e.ind_impresora='N'
                          and    e.cod_prod=(select cod_prod from lgt_cod_barra where cod_barra=cCodCupon_in)
                        )F
               )g
      --  WHERE  MOD(FILA,2) != 0
        )
where   FILA_sup between cInicio_in and cFin_in;


   IF (num_reg<>0) THEN

         vRuta := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\';

        select 'Local: '||l.cod_local || '-' || l.desc_corta_local || ' <br> Fecha:' || to_char(sysdate,'dd/mm/yyyy HH24:MI:SS')
        into   vMensajeLocal
        from   pbl_local l
        where  l.cod_grupo_cia = cCodGrupoCia_in
        and    l.cod_local = cCodLocal_in;

    v_vCabecera1 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_1;
    v_vCabecera2 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);

          vFila_IMG_Cabecera_Consejo:= '<tr> <td colspan="4"><div align="center" class="style8">'||
                               '<img src=file:'||
                               v_vCabecera1||
                               ' width="203" height="22" class="style3"></div></td>'||
                               ' </tr> ';

           vFila_IMG_Cabecera_MF:= '<tr> <td colspan="4">'||
                               '<img src=file:'||
                               v_vCabecera2||
                               ' width="300" height="90"></td>'||
                               '</tr> ';
           --FV 19.08.08
           /* vFila_Cupon := '<tr>' ||
                          '<td height="50" colspan="3">'||
                          '<div align="center" class="style8">'||
                           -- '<img src=file://///C:/'||cCodCupon_in||'.jpg width="229" height="107" class="style3"></div></td></tr>';
                           '<img src=file:'||vRuta||''||cCodCupon_in||'.jpg width="229" height="107" class="style3"></div></td></tr>';*/
           vFila_Cupon := '<td width="250" height="150" align="center" rowspan=1>'||
                           -- '<img src=file://///C:/'||cCodCupon_in||'.jpg width="229" height="107" class="style3"></div></td></tr>';
                           '<img src=file:'||vRuta||''||cCodCupon_in||'.jpg width="250" height="150" class="style3" align="center"></td>';


      --vMsg_out := C_INICIO_MSG;

      FOR v_rCurPrecio IN curPrecios
      LOOP


       select /*''||
                       '<tr align="center">'||
                       '<td align="center" width="246" colspan="2">'||
                       '<b><font size="4">'||SUBSTR(trim(v_rCurPrecio.Desc_Prod),1,27) ||'</font></b>'||'<b><font size="4">'
                       ||trim(v_rCurPrecio.Desc_Unid_Present)
                       ||'</font></b></td></tr>'
                       ||'<tr>'
                       ||'<td align="center">'
                       ||'<b><font size="4">'||trim(v_rCurPrecio.Cod_Prod)||'</font></b>'||'</td>'
                       ||'<td align="center">'
                       ||'<b><font size="4">'||trim(v_rCurPrecio.fecha_cambio)||'</font></b>'||'</td>'
                       ||'/<tr>'
                       ||'<tr>'
                       ||'<td align="center" colspan="2">'
                       ||'<font size="10"><b>S/.'||TO_CHAR(v_rCurPrecio.precios,'9,999,999.00')||'</b></font>'
                       ||'</td>'
                       ||'</tr>'
                       ||'<tr rowspan="3">'
                       ||'<td align="center">'
                       ||vFila_Cupon
                       ||'</td>'
                       ||'</tr>'*/
                       '<tr align="center">
                       <td>
                        <table border="1" width=400 cellpadding=0 cellspacing=0 style="margin-top: 0px;">
                        	<tr align="center"><td style="font-size:16; margin-top: 0px;" colspan=3>&nbsp;&nbsp;<br>'||SUBSTR(trim(v_rCurPrecio.Desc_Prod),1,27)||' '||trim(v_rCurPrecio.Desc_Unid_Present)||'</td></tr>
                        	<tr align="center"><td  style="font-size:16" colspan=2>&nbsp;'||trim(v_rCurPrecio.Cod_Prod)||'</font></b>'||'</td><td style="font-size:16">'||trim(v_rCurPrecio.fecha_cambio)||'</td></tr>
                        	<tr style="font-family:tahoma;font-weight: bold" align="center"><td  width=50% style="font-size:95" align="center" rowspan=1>'||'&nbsp;'||SUBSTR(trim(TO_CHAR(v_rCurPrecio.precios,'9,999,999.00')),1,INSTR(trim(TO_CHAR(v_rCurPrecio.precios,'9,999,999.00')),'.'))||'</td><td  width=50% style="font-size:65" align="center" rowspan=1>'||SUBSTR(trim(TO_CHAR(v_rCurPrecio.precios,'9,999,999.00')),INSTR(trim(TO_CHAR(v_rCurPrecio.precios,'9,999,999.00')),'.')+1)||'</td>'--'<p style="font-size:30"> 40</p></td>'
                          ||vFila_Cupon||'</tr>
                        </table>
                        </td>
                        </tr>'
                        /*<table border=0 width=280 cellpadding=0 cellspacing=0>
	<tr><td style="font-size:8" colspan=2>&nbsp;&nbsp;CODERA 3M UND </td></tr>
	<tr><td  style="font-size:8">&nbsp;571193</td><td style="font-size:8">12-09-2013</td></tr>
	<tr style="font-family:tahoma;font-weight: bold"><td  width=50% rowspan=1 style="font-size:35">&nbsp;38.<a style="font-size:25"> 80</a></td>
<td ><iframe id="external"  marginheight="0" style="height:76px;width:150px" marginwidth="0" noresize scrolling="No" frameborder="0" src="barra.php?BARRA=4005800210259"></iframe>
</td></tr>
	<tr><td  colspan=2 ><hr></td></tr>
</table>
</td><td>
*/
                        /*<td>
                        <table border=0 width=280 cellpadding=0 cellspacing=0>
                        	<!--<<td width=100% colspan=2 ><hr></td></tr>-->
                        	<tr><td style="font-size:8" colspan=2>&nbsp;&nbsp;ESPARA MICROPORE PAPEL 1.25CMX5M PIEL UND </td></tr>
                        	<tr><td  style="font-size:8">&nbsp;120073</td><td style="font-size:8">09-09-2013</td></tr>
                        	<tr style="font-family:tahoma;font-weight: bold"><td  width=50% rowspan=1 style="font-size:35">&nbsp;7.<a style="font-size:25"> 40</a></td>
                        <td ><iframe id="external"  marginheight="0" style="height:76px;width:150px" marginwidth="0" noresize scrolling="No" frameborder="0" src="barra.php?BARRA=7750373046008"></iframe>
                        </td></tr>
                        	<tr><td  colspan=2 ><hr></td></tr>
                        </table>
                        </td>*/
                       --'<img src=file:'||vRuta||''||cCodCupon_in||'.jpg
                        /*''||
                        '<tr >'||
                       '<td align="center">'||
                                        '<table cellpading = "0" cellspacing="0" border = "0" align="center">'||
                                         '<tr >'||
                                         '<td align="center">'||
                                               '<font size="20"><b>S/.'||TO_CHAR(v_rCurPrecio.precios,'9,999,999.00')||'</b></font>'||
      				   '</td>'||
                                         '</tr>'||
      '<tr align="center">'||
      '<td align="center" width="246">'||
                                               '<b><font size="6">'||SUBSTR(trim(v_rCurPrecio.Desc_Prod),1,27) ||'</font></b></td>'||
                                         '</tr>'||
                                         '<tr><td align="center"><b><font size="4">'||trim(v_rCurPrecio.Desc_Unid_Present)||' nbsp;nbsp;nbsp;nbsp;nbsp;nbsp;nbsp;nbsp;nbsp;nbsp;'
                                         ||trim(v_rCurPrecio.Cod_Prod)||
                                         '</font></b></td></tr>'||
                                         '</table>'||
                        '</td>'||
                        '<td algin="center" width="10">'||
                        '</td>'||
                        '<td align="center">'||
                                        '<table cellpading = "0" cellspacing="0" border = "0" align="center">'||
                                        '<tr>'||
                                        '<td align="center">'||
                                               '<font size="50"><b>'||
                                               nvl2(v_rCurPrecio.Val_Prec_Vta_2,'S/.'||TO_CHAR(v_rCurPrecio.Val_Prec_Vta_2,'9,999,999.00'),' ')
                                               --decode(v_rCurPrecio.Val_Prec_Vta_2,null,' ','S/.'||TO_CHAR(v_rCurPrecio.Val_Prec_Vta_2,'9,999,999.00'))
                                               ||'</b></font>'||
      				   '</td>'||
                                        '</tr>'||
      				  '<tr align="center">'||
                                        '<td align="center" width="256">'||
                                               'nbsp;nbsp;nbsp;nbsp;nbsp<b><font size="4">'||SUBSTR(trim(v_rCurPrecio.Desc_Prod_2),1,27)||'</font></b></td></tr>'||
                                        '<tr><td align="center"><b><font size="4">'||trim(v_rCurPrecio.Desc_Unid_Present_2)||vFila_Cupon||trim(v_rCurPrecio.Cod_Prod_2)||'</font></b></td></tr>'||
                        '</table>'||
                        '</td>  '||
                        --'</tr>'
                        '</tr>'||C_FILA_VACIA*/
                        into aux
                        from dual;

            vMsg_out2:= vMsg_out2|| aux ;
                        /**************************************************************/
-- COMENTADO PARA LAS PRUEBAS
/*

                   UPDATE AUX_CAMBIO_PRECIO A
                   SET    A.IND_IMPRESORA='S', A.CONT_IMPRESION=A.CONT_IMPRESION+1,
                          --A.FECHA_IMPRESION=TO_CHAR(SYSDATE,'dd/MM/yyyy hh24:mi:ss')
                          A.FECHA_IMPRESION=SYSDATE
                   WHERE  A.COD_PROD in(v_rCurPrecio.Cod_Prod,v_rCurPrecio.Cod_Prod_2)
                   AND    A.COD_GRUPO_CIA=cCodGrupoCia_in
                   AND    A.COD_LOCAL=cCodLocal_in;
                   COMMIT;
*/
                   --Autor: Luigy Terrazos      Fecha:01/03/2013    Motivo: Se comenta por que el select no tiene restricciones
                   /*INSERT INTO AUX_CAMBIO_PRECIO_HIST(COD_GRUPO_CIA,COD_PROD,DESCR_PROD,COD_LOCAL,PRECIOS,IND_PROD_FRACCIONADO,VAL_FRAC_LOCAL,IND_IMPRESORA,FECHA_CAMBIO,FECHA_IMPRESION,CONT_IMPRESION,FECHA)
                   SELECT              A.COD_GRUPO_CIA,A.COD_PROD,A.DESCR_PROD,A.COD_LOCAL,A.PRECIOS,A.IND_PROD_FRACCIONADO,A.VAL_FRAC_LOCAL,A.IND_IMPRESORA,A.FECHA_CAMBIO,A.FECHA_IMPRESION,A.CONT_IMPRESION,SYSDATE
                   FROM   AUX_CAMBIO_PRECIO A;
                   COMMIT;*/

            END LOOP;
             --INSERT INTO AUX_CAMBIO_PRECIO_HIST(COD_GRUPO_CIA,COD_PROD,DESCR_PROD,COD_LOCAL,PRECIOS,IND_PROD_FRACCIONADO,VAL_FRAC_LOCAL,IND_IMPRESORA,FECHA_CAMBIO,FECHA_IMPRESION,CONT_IMPRESION,FECHA)
             --      SELECT              A.COD_GRUPO_CIA,A.COD_PROD,A.DESCR_PROD,A.COD_LOCAL,A.PRECIOS,A.IND_PROD_FRACCIONADO,A.VAL_FRAC_LOCAL,A.IND_IMPRESORA,A.FECHA_CAMBIO,A.FECHA_IMPRESION,A.CONT_IMPRESION,SYSDATE
             --      FROM   AUX_CAMBIO_PRECIO A;
                   COMMIT;


                     vMsg_out := C_INICIO_MSG ||vMsg_out2||C_FIN_MSG ;

      /*vMsg_out :=     */        /*'<html>
                              <head>
                              </head>
                              <body>
                              <table width="319" height="1299" border="0">
                              <tr>
                               <td><table width="295" height="591" border="10">PRUEBA 1</table></td>
                               <td><table width="10" height="4"></table></td>
                               <td><table width="295" height="591" border="10">PRUEBA 2</table></td>
                              </tr>
                              </table></td>
                             </tr>
                             </table>
                             </body>
                             </html>'*/



                       return vMsg_out;

    ELSE
      DBMS_OUTPUT.PUT_LINE('NO ACTUALIZA NADA');
      RAISE_APPLICATION_ERROR(-20055,'ERROR : REGISTROS YA IMPRESOS');
       return null;
    END IF;

  END;

  FUNCTION F_EMITE_REGALO_X_MONTO(cCodGrupoCia_in char,
                                  cCodLocal_in char,
                                  cCodEncarte_in  char,
                                  cMonto_in       varchar2) RETURN FarmaCursor IS

    curProdRegalo       FarmaCursor;
    cCodProd_Regalo     lgt_prod.cod_prod%type;
    cMontoMinimo    vta_encarte.mont_min%type;
    nCountProd      number;
    vAux_encarte_aplica char(1);
    stk_prod_regalo LGT_PROD_LOCAL.STK_FISICO%TYPE;
    NO_APLICA_ENCARTE_MONTO EXCEPTION;
    NO_TIENE_STOCK_REGALO EXCEPTION;
    NO_EXISTE_REGALO_ENCARTE EXCEPTION;
    NMONTO NUMBER(9,3);
  BEGIN
    /*open curProdRegalo for
    select 'N' from dual;*/
    --curProdRegalo := null;
    NMONTO:=0;
--      NMONTO := ROUND(TO_NUMBER(TRIM(cMonto_in),'999999.99'),3);
     NMONTO := trunc(to_number(replace(REPLACE(trim(cMonto_in),',','.'),'.',',')),3);

    --
    BEGIN

      select decode(e.cod_prod_regalo, 0, 'S', 'N')
        into vAux_encarte_aplica
        from vta_encarte e
       where e.cod_grupo_cia = cCodGrupoCia_in
         and e.cod_encarte = trim(cCodEncarte_in);

      IF vAux_encarte_aplica != 'N' THEN

      select count(a.cod_prod)
           into nCountProd
           FROM (
                   SELECT c.monto_rango_regalo,c.cod_prod,
                          DENSE_RANK() OVER(PARTITION by c.cod_encarte ORDER BY c.monto_rango_regalo desc) AS ORDEN
                     FROM vta_encarte_regalo c
                    WHERE c.cod_grupo_cia = cCodGrupoCia_in
                      AND c.cod_encarte = trim(cCodEncarte_in)
                      AND NMONTO >= c.monto_rango_regalo

                  ) A
            WHERE A.ORDEN = 1;

      IF nCountProd>0 THEN --EXISTE EL PROD.

                  SELECT a.cod_prod
                  INTO cCodProd_Regalo
                   FROM (
                         SELECT c.monto_rango_regalo,c.cod_prod,
                                DENSE_RANK() OVER(PARTITION by c.cod_encarte ORDER BY c.monto_rango_regalo desc) AS ORDEN
                           FROM vta_encarte_regalo c
                          WHERE c.cod_grupo_cia = cCodGrupoCia_in
                            AND c.cod_encarte = trim(cCodEncarte_in)
                            AND NMONTO >= c.monto_rango_regalo
                        ) A
                  WHERE A.ORDEN = 1;

                  SELECT l.stk_fisico
                  INTO stk_prod_regalo
                  FROM  LGT_PROD_LOCAL L
                  WHERE L.COD_GRUPO_CIA= cCodGrupoCia_in
                  AND   L.COD_LOCAL=cCodLocal_in
                  AND   L.COD_PROD=cCodProd_Regalo;

            IF  stk_prod_regalo > 0 THEN
                 OPEN curProdRegalo FOR
                    SELECT  V.COD_PROD|| 'Ã' ||
                            V.MONTO_RANGO_REGALO|| 'Ã' ||
                            (select min(monto_rango_regalo) from vta_encarte_regalo
                             where monto_rango_regalo>=NMONTO
                             and    cod_encarte=trim(cCodEncarte_in)
                            )

                            || 'Ã' ||
                            (select p.cod_prod from vta_encarte_regalo t,lgt_prod p  where t.monto_rango_regalo =
                                (select min(monto_rango_regalo) from vta_encarte_regalo
                                 where monto_rango_regalo>NMONTO
                                 and    cod_encarte=trim(cCodEncarte_in)
                                ) and p.cod_prod = t.cod_prod
                                and    cod_encarte=trim(cCodEncarte_in)
                            )
                            || 'Ã' ||
                            (select p.desc_prod from vta_encarte_regalo t,lgt_prod p  where t.monto_rango_regalo =
                                (select min(monto_rango_regalo) from vta_encarte_regalo
                                 where monto_rango_regalo>NMONTO
                                 and    cod_encarte=trim(cCodEncarte_in)
                                ) and p.cod_prod = t.cod_prod
                                and    cod_encarte=trim(cCodEncarte_in)
                            )
                    FROM VTA_ENCARTE_REGALO V
                    WHERE
                    V.COD_GRUPO_CIA=cCodGrupoCia_in
                    AND V.COD_PROD= cCodProd_Regalo
                    AND   V.COD_ENCARTE=trim(cCodEncarte_in)
                    AND   V.EST_PROD_REGALO='A'
                    AND   SYSDATE BETWEEN V.FECHA_INI AND V.FECHA_FIN
                    UNION
                    SELECT ' ' FROM DUAL;
            ELSE
               OPEN curProdRegalo FOR
               /*SELECT ' ' FROM DUAL;*/
            select a.cod_prod
            || 'Ã' ||a.monto_rango_regalo
            || 'Ã' ||a.monto_rango_regalo2
            || 'Ã' ||a.cod_prod2
            || 'Ã' ||(select desc_prod from lgt_prod where cod_prod = a.cod_prod2)
            from
            (
            select cod_prod,
                   monto_rango_regalo,
                   lead(cod_prod) over (order by monto_rango_regalo asc) cod_prod2,
                   lead(monto_rango_regalo) over (order by monto_rango_regalo asc) monto_rango_regalo2 ,
                   rownum orden
            from VTA_ENCARTE_REGALO e where e.cod_encarte=cCodEncarte_in
            and e.cod_grupo_cia=cCodGrupoCia_in
            --and rownum<2
            order by monto_rango_regalo asc
            )a where a.orden =1
          UNION
          SELECT ' ' FROM DUAL;

            END IF;
        ELSE

        OPEN curProdRegalo FOR
        SELECT C.COD_PROD  || 'Ã' ||
               C.MONTO_RANGO_REGALO|| 'Ã' ||
               (SELECT MIN(E.MONTO_RANGO_REGALO) FROM VTA_ENCARTE_REGALO E WHERE
               E.MONTO_RANGO_REGALO>C.MONTO_RANGO_REGALO AND
               E.COD_ENCARTE=cCodEncarte_in AND
               E.COD_GRUPO_CIA=cCodGrupoCia_in
               ) || 'Ã' ||
               (SELECT g.cod_prod FROM VTA_ENCARTE_REGALO G
               WHERE G.MONTO_RANGO_REGALO IN
                   (SELECT MIN(E.MONTO_RANGO_REGALO) FROM VTA_ENCARTE_REGALO E WHERE
                   E.MONTO_RANGO_REGALO>C.MONTO_RANGO_REGALO AND
                   E.COD_ENCARTE=cCodEncarte_in AND
                   E.COD_GRUPO_CIA=cCodGrupoCia_in
                   )
                   AND G.COD_GRUPO_CIA=cCodGrupoCia_in
                   AND G.COD_ENCARTE = cCodEncarte_in)
                   || 'Ã' ||
               (SELECT D.DESC_PROD FROM VTA_ENCARTE_REGALO G,lgt_prod d
               WHERE G.MONTO_RANGO_REGALO IN
                   (SELECT MIN(E.MONTO_RANGO_REGALO) FROM VTA_ENCARTE_REGALO E WHERE
                   E.MONTO_RANGO_REGALO>C.MONTO_RANGO_REGALO AND
                   E.COD_ENCARTE=cCodEncarte_in AND
                   E.COD_GRUPO_CIA=cCodGrupoCia_in
                   )
                   AND D.COD_PROD=G.COD_PROD
                   AND G.COD_GRUPO_CIA=cCodGrupoCia_in
                   AND G.COD_ENCARTE = cCodEncarte_in)
        FROM VTA_ENCARTE_REGALO C
        WHERE C.MONTO_RANGO_REGALO IN
        (SELECT     MIN(V.MONTO_RANGO_REGALO)
                    FROM VTA_ENCARTE_REGALO V
                    WHERE
                    V.COD_GRUPO_CIA=cCodGrupoCia_in
                    AND   V.COD_ENCARTE=trim(cCodEncarte_in)
                    AND   V.EST_PROD_REGALO='A'
                    AND   NMONTO < V.monto_rango_regalo
                    AND   SYSDATE BETWEEN V.FECHA_INI AND V.FECHA_FIN)
          AND   C.COD_GRUPO_CIA=cCodGrupoCia_in
          AND   C.COD_ENCARTE=trim(cCodEncarte_in)
          AND   C.EST_PROD_REGALO='A'
          UNION
          SELECT ' ' FROM DUAL;

        END IF;
     ELSE
        OPEN curProdRegalo FOR
        SELECT ' ' FROM DUAL;
     END IF;

     --curProdRegalo si nunca abrio el cursor lo seteo con vacio.
     --como si no tiene stock
        /* IF  not curProdRegalo%ISOPEN THEN
             OPEN curProdRegalo FOR
             SELECT ' ' FROM DUAL;
         END IF;*/

        RETURN curProdRegalo;

    EXCEPTION
      WHEN NO_APLICA_ENCARTE_MONTO THEN
        RAISE_APPLICATION_ERROR('-20001',
                                'Este encarte no se aplica Metodo Nuevo Regalo');
      WHEN NO_EXISTE_REGALO_ENCARTE THEN
        RAISE_APPLICATION_ERROR('-20002',
                                'Este encarte no tiene prod. que da regalo definido' ||
                                cCodProd_Regalo);
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR('-20003',
                                'Error Encarte ' || cCodEncarte_in ||
                                ',Error:' || sqlerrm);
    END;
  END;

   FUNCTION F_VAR_GET_IND_VER_RECETARIO(cCodCia_in IN CHAR)
   RETURN VARCHAR2
   is
    vInd VARCHAR2(500);
   BEGIN
    SELECT DESC_CORTA into vInd
    FROM PBL_TAB_GRAL
    WHERE --ID_TAB_GRAL='405'
    COD_APL = 'PTO_VENTA'
    AND COD_TAB_GRAL = 'IND_VER_RECE_MAGIS'
    AND EST_TAB_GRAL = 'A'
    AND COD_CIA = cCodCia_in;

    return vInd;
   END;


   FUNCTION F_VAR_GET_COD_BARRAS(cCodProd_in char)
      RETURN VARCHAR2
      is
       vCodBarra varchar(20);
      BEGIN
       select a.cod_barra into vCodBarra
                               from (select b.* from lgt_cod_barra b
                               where cod_prod = cCodProd_in
                               order by fec_crea_cod_barra desc
                               ) a where rownum=1;
       return vCodBarra;
      END;

FUNCTION F_IMPR_TERMICA_STICKER_PROD(cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                       vCodigos         IN VARCHAR2)
  RETURN FarmaCursor
  IS
  V_SQL VARCHAR2(32767) := '';
  V_RCURPRECIO FarmaCursor;
  BEGIN
 V_SQL := ' SELECT DISTINCT ' || ''''
            || 'R50,0?'
            || 'N?'
            || 'S2?'
            || 'D5?'
            || 'ZB?'
            || 'A80,20,0,3,1,1,N,"' || ''''
            || ' || SUBSTR(trim(d.desc_prod),1,27) || ' ||''''|| ' / ' || '''' || ' ||  DECODE(l.IND_PROD_FRACCIONADO,'|| '''' ||'N'|| '''' ||',d.DESC_UNID_PRESENT,l.UNID_VTA)  || '|| ''''
            || '"?'
            || 'A345,80,0,1,1,1,N,"'|| ''''
            || ' || trim(d.cod_prod) || '|| ''''
            || '"?'
            ||'A525,80,0,1,1,1,N,"' || ''''
            || ' || trim(to_char(e.fecha_cambio,'
            || '''' ||'dd-mm-yyyy' || '''' || ')) || ' || ''''
            || '"?'
            || 'A130,80,0,5,1,2,N,"'|| ''''
            || ' || SUBSTR(trim(TO_CHAR(e.precios,'|| '''' || '9,999,999.00' || '''' || ')),1,INSTR(trim(TO_CHAR(e.precios,' || '''' ||'9,999,999.00' || '''' ||')),' || '''' || '.' ||'''' || ')) || ' || ''''
            || '"?'
            || 'A' || ''''
            || ' || CASE WHEN e.precios > 100 THEN 260 WHEN e.precios < 10 THEN 190 ELSE 220 END || '  || ''''
            || ',80,0,4,1,2,N,"'|| ''''
            || ' || SUBSTR(trim(TO_CHAR(e.precios,' || '''' || '9,999,999.00' ||'''' || ')),INSTR(trim(TO_CHAR(e.precios,' || '''' || '9,999,999.00' || '''' || ')),' || '''' || '.' || '''' || ')+1) || '|| ''''
            || '"?'
            || 'B320,110,0,E30,3,8,60,B,"'|| ''''
            ||' || NVL(FARMA_GRAL.F_VAR_GET_COD_BARRAS(trim(d.cod_prod)),' || '''' || '0000000000000' || '''' || ') || ' || ''''
            || '"?'
            || 'B320,110,0,E80,3,8,60,B,"'|| ''''--CESAR HUANES--EAN 8
            ||' || NVL(FARMA_GRAL.F_VAR_GET_COD_BARRAS(trim(d.cod_prod)),' || '''' || '00000000' || '''' || ') || ' || ''''--CESAR HUANES--EAN 8
            || '"?'--CESAR HUANES
            || 'P1?' || ''''
            || ' AS HTML from lgt_prod d,lgt_prod_local l,aux_cambio_precio e '
            || ' where d.cod_grupo_cia=e.cod_grupo_cia '
            || ' and d.cod_grupo_cia=l.cod_grupo_cia '
            || ' and d.cod_prod=l.cod_prod '
            || ' and e.cod_local=' || '''' || cCodLocal_in || ''''
            || ' and d.cod_prod=e.cod_prod'
            /*|| ' and d.cod_grupo_rep_edmundo IN(' || '''' || '003' || ''''
                                                || ',' || '''' || '007' || ''''
                                                || ',' || '''' || '006' || ''''
                                                || ',' || '''' || '004' || ''''
                                                || ')'*/
            || ' and d.est_prod=' || '''' || 'A' || ''''
            || ' and e.ind_impresora=' || '''' || 'N' || ''''
            || ' and e.cod_prod in (' || vCodigos || ') ';
        DBMS_OUTPUT.PUT_LINE('V_SQL : ' || V_SQL);

        OPEN V_RCURPRECIO
        FOR V_SQL ;
       --Actualizando el flag de impresión de los productos impresos
       EXECUTE IMMEDIATE
         ' UPDATE AUX_CAMBIO_PRECIO A' ||
         ' SET  A.IND_IMPRESORA=' || '''' || 'S' || '''' ||
         '     ,A.CONT_IMPRESION=A.CONT_IMPRESION+1' ||
         '     ,A.FECHA_IMPRESION=SYSDATE '  ||
         ' WHERE  A.COD_PROD in(' || vCodigos || ') '||
         ' AND    A.COD_GRUPO_CIA=' || cCodGrupoCia_in ||
         ' AND    A.COD_LOCAL=' || cCodLocal_in;

         COMMIT;
        RETURN  V_RCURPRECIO;
     END;

     /************************************************************/
     --Descripcion: Obtiene los datos básicos de los productos
  --Fecha       Usuario		    Comentario
   --23.03.2014  CHUANES      	Modificacion
   FUNCTION F_GET_PRODUCTOS(cCodGrupoCia   IN CHAR,
                            cCodLocal      IN CHAR,
                            vDescripcion   IN VARCHAR2)
   RETURN FarmaCursor
   IS
     V_RCURPROD FarmaCursor;
     BEGIN
          OPEN V_RCURPROD
          FOR
                        SELECT (PROD.COD_PROD || 'Ã' ||
                      PROD.DESC_PROD || 'Ã' ||
                      DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
                      TO_CHAR(Farma_Utility.OBTENER_REDONDEO2(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA)),'999,990.000') ) AS RESULTADO
                      FROM   LGT_PROD PROD,
                      LGT_PROD_LOCAL PROD_LOCAL,
                      LGT_LAB LAB,
                      PBL_IGV IGV,
                      LGT_PROD_VIRTUAL PR_VRT,
                      LGT_COD_BARRA BARRA
                      WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia
                      AND         PROD_LOCAL.COD_LOCAL =  cCodLocal
                      AND         PROD_LOCAL.EST_PROD_LOC='A'
                      AND         PROD_LOCAL.COD_GRUPO_CIA=BARRA.COD_GRUPO_CIA
                      AND         PROD_LOCAL.COD_PROD=BARRA.COD_PROD
                      AND         PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
                      AND         PROD.COD_PROD = PROD_LOCAL.COD_PROD
                      AND         PROD.COD_LAB = LAB.COD_LAB
                      AND         PROD.COD_IGV = IGV.COD_IGV
                      AND         PROD.EST_PROD = 'A'
                AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
                AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
                AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
                 AND PROD.DESC_PROD LIKE '%' || vDescripcion || '%';
          RETURN V_RCURPROD;
     END;

  /**********************************************************************************************/
  --Descripcion: Obtiene el código EPL para el sticker de un producto
  --Fecha       Usuario		    Comentario
  --23.03.2014  CHUANES      	Modificacion

   FUNCTION F_VAR_GET_EPL_STICKER(cCodGrupoCia IN CHAR,
                                  cCodLocal    IN CHAR,
                                  cCodProd     IN CHAR)
   RETURN VARCHAR2
   IS
     V_EPL VARCHAR2(4000) := '';
   BEGIN
        SELECT DISTINCT
              'R50,0?N?S2?D5?ZB?A80,20,0,3,1,1,N,"'
              || SUBSTR(TRIM(P.DESC_PROD),1,27) || ' / ' || DECODE(PL.IND_PROD_FRACCIONADO,'N',P.DESC_UNID_PRESENT,PL.UNID_VTA)
              || '"?A345,80,0,1,1,1,N,"' || TRIM(P.COD_PROD)
              || '"?A525,80,0,1,1,1,N,"' || TRIM(TO_CHAR(PL.FEC_MOD_PROD_LOCAL,'dd-mm-yyyy'))
              || '"?A130,80,0,5,1,2,N,"' || SUBSTR(TRIM(TO_CHAR(FARMA_UTILITY.OBTENER_REDONDEO2(PL.VAL_PREC_VTA),'9,999,990.00')),1,INSTR(TRIM(TO_CHAR(FARMA_UTILITY.OBTENER_REDONDEO2(PL.VAL_PREC_VTA),'9,999,990.00')),'.'))
              || '"?A' || CASE WHEN PL.VAL_PREC_VTA > 100 THEN 260 WHEN PL.VAL_PREC_VTA < 10 THEN 190 ELSE 220 END || ',80,0,4,1,2,N,"'
              || SUBSTR(TRIM(TO_CHAR(FARMA_UTILITY.OBTENER_REDONDEO2(PL.VAL_PREC_VTA),'9,999,990.00')),INSTR(TRIM(TO_CHAR(FARMA_UTILITY.OBTENER_REDONDEO2(PL.VAL_PREC_VTA),'9,999,990.00')),'.')+1)
              || '"?B320,110,0,E30,3,8,60,B,"'
              || NVL(FARMA_GRAL.F_VAR_GET_COD_BARRAS(TRIM(P.COD_PROD)),'0000000000000')
              || '"?B320,110,0,E80,3,8,60,B,"'--cesar huanes
              || NVL(FARMA_GRAL.F_VAR_GET_COD_BARRAS(TRIM(P.COD_PROD)),'00000000')--cesar huanes
              || '"?P1?' || 'Ã' || NVL(PL.STK_FISICO,0) INTO V_EPL -- KMONCADA 19.09.2014 SE AGREGA STOCK DE PRODUCTO
          FROM LGT_PROD P,LGT_PROD_LOCAL PL
          WHERE P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
          AND P.COD_PROD = PL.COD_PROD
          --and p.cod_grupo_rep_edmundo IN('003','007','006','004')
          AND P.EST_PROD='A'
          AND PL.EST_PROD_LOC = 'A'
          AND PL.COD_GRUPO_CIA = cCodGrupoCia
          AND PL.COD_LOCAL = cCodLocal
          AND P.COD_PROD =  cCodProd;
       RETURN V_EPL;
   END;

   /**********************************************************************************************/
  --Descripcion: Obtiene los datos de la forma de pago de un pedido
  --Fecha       Usuario		    Comentario
  --28/10/2013  CVILCA      	Creación
   FUNCTION F_GET_FORMAS_PAGO_TICKET(cCodGrupoCia   IN CHAR,
                                    cCodCia         IN CHAR,
                                    cCodLocal       IN CHAR,
                                    vNumPedVta      IN VARCHAR2)
   RETURN FarmaCursor
   IS
     V_RCURFP FarmaCursor;
     BEGIN
          OPEN V_RCURFP
          FOR
            SELECT  (FP.DESC_CORTA_FORMA_PAGO || 'Ã' ||
                      DECODE(PP.TIP_MONEDA,'01','S/. ','02','$   ',PP.TIP_MONEDA) ||
                      TO_CHAR(PP.IM_PAGO,'999,990.00') || 'Ã' ||
                      TO_CHAR(PP.VAL_VUELTO,'9,999,990.00')) AS RESULTADO
              FROM  VTA_FORMA_PAGO_PEDIDO PP,
                    VTA_FORMA_PAGO FP
             WHERE  PP.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
               AND  PP.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
               AND  PP.COD_GRUPO_CIA = cCodGrupoCia
               AND  PP.COD_CIA = cCodCia
               AND  PP.COD_LOCAL = cCodLocal
               AND  PP.NUM_PED_VTA = vNumPedVta;
          RETURN V_RCURFP;
     END;


 /**********************************************************************************************/
  --Descripcion: Busca los datos basicos del producto por Codigo de Barra
  --Fecha       Usuario		    Comentario
  --25/11/2013  CHUANES      	Creación

  FUNCTION F_GET_PRODUC_COD_BARRA(cCodGrupoCia IN CHAR, cCodLocal IN CHAR,  cCodBarra IN CHAR )

   RETURN FarmaCursor
   IS
    V_RCURCOD_PROD FarmaCursor;

    BEGIN

    OPEN V_RCURCOD_PROD

    FOR
     SELECT (PROD.COD_PROD || 'Ã' ||
          PROD.DESC_PROD || 'Ã' ||
          DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
          TO_CHAR(Farma_Utility.OBTENER_REDONDEO2(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA)),'999,990.000') ) AS RESULTADO
          FROM   LGT_PROD PROD,
          LGT_PROD_LOCAL PROD_LOCAL,
          LGT_LAB LAB,
          PBL_IGV IGV,
          LGT_PROD_VIRTUAL PR_VRT,
          LGT_COD_BARRA BARRA
          WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia
          AND         PROD_LOCAL.COD_LOCAL =  cCodLocal
          AND         PROD_LOCAL.EST_PROD_LOC='A'
          AND         PROD_LOCAL.COD_GRUPO_CIA=BARRA.COD_GRUPO_CIA
          AND         PROD_LOCAL.COD_PROD=BARRA.COD_PROD
          AND         PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND         PROD.COD_PROD = PROD_LOCAL.COD_PROD
          AND         PROD.COD_LAB = LAB.COD_LAB
          AND         PROD.COD_IGV = IGV.COD_IGV
          AND         PROD.EST_PROD = 'A'
    AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
    AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
    AND    BARRA.COD_BARRA= cCodBarra;


      RETURN  V_RCURCOD_PROD;

    END;
/* ***************************************************************************** */
FUNCTION F_VAR_ES_PEDIDO_ANTIGUO(cCodGrupoCia IN CHAR,
                                 cCodLocal    IN CHAR,
                                 cFechaPedido IN CHAR,
                                 cTipComp     IN CHAR,
                                 cNumCmpr     IN CHAR,
                                 cMontoCmpr   IN CHAR
                                 )
   RETURN VARCHAR2
   IS
     vResultado VARCHAR2(10) := 'N';
     pFechaApertura date;
     nExistePedido number;
     bFechaAntigua boolean := false;
     bNoExistePedido boolean := false;
   BEGIN
       begin
         select lo.fech_migra_mfa
         into   pFechaApertura
         from   PBL_LOCAL_MIGRA lo
         where  lo.cod_local_sap = cCodLocal;
       exception
       when others then
        pFechaApertura := null;
       end;

       -- para que se considere un pedido antiguo debe de cumplir 2 condiciones
       -- 1.-la fecha debe ser antes de la fecha de migracion
       -- 2.-el comprobante no debe existir en el local luego de la fecha de migracion

       if pFechaApertura is not null then
         -- 1.-
         if to_date(cFechaPedido,'dd/mm/yyyy') < pFechaApertura then
            bFechaAntigua := true;
         end if;

         -- 2.-
         select count(1)
         into   nExistePedido
         from   vta_comp_pago cp
         where  cp.cod_grupo_cia = cCodGrupoCia
         and    cp.cod_local = cCodLocal
         and    cp.tip_comp_pago = cTipComp
         and --LTAVARA  08.09.2014 18:50  VALIDACION PARA COMPROBANTE ELECTRONICO
         DECODE(CP.COD_TIP_PROC_PAGO,'1',cp.NUM_COMP_PAGO_E,cp.NUM_COMP_PAGO)=cNumCmpr
          /*(CASE     --LTAVARA  08.09.2014 18:50  VALIDACION PARA COMPROBANTE ELECTRONICO
              WHEN  INSTR(cNumCmpr,'F')!= 0 OR INSTR(cNumCmpr,'B')!= 0   THEN
                    cp.NUM_COMP_PAGO_E
               ELSE
                    cp.NUM_COMP_PAGO
                 END ) =cNumCmpr*/

         and    cp.val_neto_comp_pago = TO_NUMBER(cMontoCmpr,'9999900.00');--DUBILLUZ 05.03.2013

         if nExistePedido = 0 then
            bNoExistePedido := true;
         end if;

       end if;

       if bFechaAntigua = true and bNoExistePedido = true then
          vResultado := 'S' ;
       end if;

       RETURN vResultado;
   END;

   /****************************************************************************/
  FUNCTION F_IMPR_VOU_VERIF_RECARGA(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 cIpServ_in        IN CHAR,
                                 --cCodCupon_in      IN CHAR,
                                 cCodCia_in in char,
                                 vDescProducto IN VARCHAR2,
                                 vNumTelefono  IN VARCHAR2,
                                 vMonto        IN VARCHAR2
                                 )
  RETURN FARMACURSOR
  IS
    curTicket FARMACURSOR;
/* C_INICIO_MSG  VARCHAR2(2000) := '
                                  <html>
                                  <head>
                                  </head>
                                  <body>
                                  <table width="337" border="0">
                                  <tr>
                                   <td width="8">&nbsp;&nbsp;</td>
                                   <td width="319"><table width="316" height="293" border="0">
                                ';


 C_FILA_VACIA  VARCHAR2(2000) :='<tr> '||
                                '<td height="13" colspan="3"></td> '||
                                ' </tr> ';

 C_FIN_MSG     VARCHAR2(2000) := '
                                 </table></td>
                                 </tr>
                                 </table>
                                 </body>
                                 </html>';

  vFila_IMG_Cabecera_MF varchar2(2800):= '';
  vFila_IMG_Cabecera_Consejo varchar2(2800):= '';
  vFila_Cupon     varchar2(22767):= '';

  vMsg_out varchar2(32767):= '';

  vMensajeLocal varchar2(2200):= '';

  vRuta varchar2(500);
      v_vCabecera1 VARCHAR2(500);
      v_vCabecera2 VARCHAR2(500);*/
    vIdDoc VARCHAR2(50);
    vIpPc  VARCHAR2(50);
    vValor VARCHAR2(100);
  BEGIN
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc := FARMA_PRINTER.F_GET_IP_SESS;
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => 'VERIFICACION DE RECARGA VIRTUAL',
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
    
    select 'LOCAL: '||l.cod_local || '-' || l.desc_corta_local
    INTO  vValor
    from   pbl_local l
    where  l.cod_grupo_cia = cCodGrupoCia_in
    and    l.cod_local = cCodLocal_in;          
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vValor,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => 'FECHA: ' || to_char(sysdate,'dd/mm/yyyy HH24:MI:SS'),
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
                                             
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vDescProducto,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ, 
                                      vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => 'TELEFONO:',
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ, 
                                      vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
                                      
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vNumTelefono,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_4,
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);
                                      
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => 'MONTO:',
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ, 
                                      vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => 'S/. '||vMonto,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_4,
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => 'Firma:__________________________________________',
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ, 
                                      vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
                                             
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => 'DNI  :__________________________________________',
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ, 
                                      vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
                                             
    curTicket := FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
    RETURN curTicket;
    /*DELETE TMP_DOCUMENTO_ELECTRONICOS;
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
    SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'0','C','N','N'
          FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
    SELECT 'VERIFICACION DE RECARGA VIRTUAL'||'@'||
           ' '||'@'||
           'LOCAL: '||( select l.cod_local || '-' || l.desc_corta_local
                        from   pbl_local l
                        where  l.cod_grupo_cia = cCodGrupoCia_in
                        and    l.cod_local = cCodLocal_in)||'@'||
           'FECHA: ' || to_char(sysdate,'dd/mm/yyyy HH24:MI:SS')||'@'||
--           ' '||'@'||
           ' '
           FROM DUAL
    ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;


    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
    SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'0','I','N','N'
          FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
    SELECT vDescProducto ||'@'||
           ' '||'@'||
           'TELEFONO: '||'@'||
  --         ' '||'@'||
           ' '
           FROM DUAL
    ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;

    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
    SELECT vNumTelefono ,'3','C','S','N' FROM DUAL;

    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
    SELECT 'MONTO: ' ,'0','I','N','N' FROM DUAL;

    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
    SELECT 'S/. '||vMonto ,'3','C','S','N' FROM DUAL;

    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
    SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'0','I','N','N'
          FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
    SELECT ' '||'@'||
           ' '||'@'||
           ' '||'@'||
           ' '||'@'||
           'Firma:_________________________________________' ||'@'||
           ' ' ||'@'||
           'DNI  :_________________________________________'
           FROM DUAL
    ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;

    OPEN curTicket FOR
        SELECT A.VALOR, A.TAMANIO, A.ALINEACION, A.BOLD, A.AJUSTE
        FROM TMP_DOCUMENTO_ELECTRONICOS A;
--        WHERE A.VALOR IS NOT NULL;*/
    
    

  /*
   vRuta := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\';

  select 'Local: '||l.cod_local || '-' || l.desc_corta_local || ' <br> Fecha:' || to_char(sysdate,'dd/mm/yyyy HH24:MI:SS')
  into   vMensajeLocal
  from   pbl_local l
  where  l.cod_grupo_cia = cCodGrupoCia_in
  and    l.cod_local = cCodLocal_in;

    v_vCabecera1 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_1;
    v_vCabecera2 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);

    vFila_IMG_Cabecera_Consejo:= '<tr> <td colspan="4"><div align="center" class="style8">'||
                         '<img src=file:'||
                         v_vCabecera1||
                         ' width="203" height="22" class="style3"></div></td>'||
                         ' </tr> ';

     vFila_IMG_Cabecera_MF:= '<tr> <td colspan="4">'||
                         '<img src=file:'||
                         v_vCabecera2||
                         ' width="300" height="90"></td>'||
                         '</tr> ';
     --FV 19.08.08
     \*
     vFila_Cupon := '<tr>' ||
                    '<td height="50" colspan="3">'||
                    '<div align="center" class="style8">'||
                     -- '<img src=file://///C:/'||cCodCupon_in||'.jpg width="229" height="107" class="style3"></div></td></tr>';
                     '<img src=file:'||vRuta||''||cCodCupon_in||'.jpg width="229" height="107" class="style3"></div></td></tr>';
*\


     vMsg_out := C_INICIO_MSG ||
                 '<tr>
                  <td height="21" colspan="3" align="center"
                          style="font:Arial, Helvetica, sans-serif">
                          VERIFICACION DE RECARGA VIRTUAL
                  </td>
                  </tr>'||

                 '<tr>
                  <td height="21" colspan="3" align="center"
                          style="font:Arial, Helvetica, sans-serif">'||
                  vMensajeLocal||
                  '</td>
                  </tr>'||

                  '<tr>
                  <td height="21" colspan="3" align="left"
                          style="font:Arial, Helvetica, sans-serif">
                          ' || vDescProducto || '
                  </td>
                  </tr>'||

                  '<tr>
                  <td height="21" colspan="3" align="left"
                          style="font:Arial, Helvetica, sans-serif">
                          Teléfono: <b><font size="+4">' || vNumTelefono || '</font></b>
                  </td>
                  </tr>'||

                  '<tr>
                  <td height="21" colspan="3" align="left"
                          style="font:Arial, Helvetica, sans-serif">
                          Monto: <b><font size="+4">S/.' || vMonto || '</font></b>
                  </td>
                  </tr>'||

                  '<tr>
                  <td height="21" colspan="3" align="left"
                          style="font:Arial, Helvetica, sans-serif">
                          Firma: ______________________________
                  </td>
                  </tr>'||

                  '<tr>
                  <td height="21" colspan="3" align="left"
                          style="font:Arial, Helvetica, sans-serif">
                          DNI:   ________________________________
                  </td>
                  </tr>'||
                  '<tr>
                  <td height="21" colspan="3" align="left"
                          style="font:Arial, Helvetica, sans-serif">

                  </td>
                  </tr>'||
                  \*
                 '<tr>
                  <td height="21" colspan="3" style="font:Arial, Helvetica, sans-serif">Si la impresion no esta centrada verifique que el modelo de impresora termica configurada sea la misma que posee.Para esto ir a administracion/mantenimiento/parametro</td>
                  </tr>'||
                  *\
                 C_FIN_MSG ;

                 return vMsg_out;*/



  END;
/****************************************************************************/


/* ***************************************************************************** */
   FUNCTION F_VAR_GET_FARMA_EMAIL(vCodFarmaEmail_in PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE)
   RETURN VARCHAR2 IS
     vInd VARCHAR2(500);
   BEGIN
     BEGIN
       SELECT A.DESC_CORTA
       INTO vInd
       FROM PBL_TAB_GRAL A
       WHERE COD_TAB_GRAL='FARMA_EMAIL'
       AND   TRIM(A.LLAVE_TAB_GRAL) = vCodFarmaEmail_in;
     EXCEPTION
       WHEN OTHERS THEN
         vInd := '';
     END;
     RETURN vInd;
   END;


  FUNCTION GET_FILTRO_PRODUCTO
  RETURN FARMACURSOR
  IS
    curFiltro FarmaCursor;
  BEGIN
    OPEN curFiltro FOR
       SELECT LLAVE_TAB_GRAL CODIGO,
		DESC_CORTA DESCRIPCION,
		SUBSTR(DESC_LARGA,1,1) ORDEN,
		SUBSTR(DESC_LARGA,3,1) IND_TIPO
		FROM PBL_TAB_GRAL
		WHERE COD_APL = 'PTO_VENTA'
		AND COD_TAB_GRAL = 'REL_FILTRO_PRODUCTO'
		ORDER BY ID_TAB_GRAL;
    RETURN curFiltro;
  END;

-- LTAVARA 25/02/2015 - OBTENER EL MAESTRO DETALLE
-- REPUESTA VARCHAR
 FUNCTION GET_MAESTRO_DETALLE(cCodMaestro IN VARCHAR,
                                cValor1    IN VARCHAR)
  RETURN VARCHAR2
  IS
  vDescripcion          VARCHAR2(100);

  BEGIN
       BEGIN

       SELECT NVL(D.DESCRIPCION,0)
       INTO vDescripcion
       FROM MAESTRO_DETALLE D
       WHERE D.COD_MAESTRO= cCodMaestro
       AND D.VALOR1=cValor1
       AND D.ESTADO= ESTADO_ACTIVO;
        
       EXCEPTION
       WHEN NO_DATA_FOUND THEN
            vDescripcion := 'VACIO';
       END;

       RETURN  vDescripcion;
  END ;
-- LTAVARA 02/03/2015 - OBTENER EL MAESTRO DETALLE  
-- REPUESTA CURSOR
  FUNCTION GET_MAESTRO_DETALLE_LISTA(codMaestro in number)
 RETURN FarmaCursor
IS
    curProvs FarmaCursor;
BEGIN
     OPEN curProvs FOR
     SELECT A.VALOR1 || 'Ã' || 
                     A.DESCRIPCION
     FROM MAESTRO_DETALLE A
     WHERE A.COD_MAESTRO = codMaestro
     AND A.ESTADO = ESTADO_ACTIVO;

     RETURN curProvs;
END;

/*********************************************************************************/

    FUNCTION F_GET_IGV_LOCAL
    RETURN number IS
           porc number(6,2) := '';
           codLocal char(3) := '';
           codGrupoCia char(3) := '';
           codCia char(3) := '';
           codigoIgv char(3) := '';
    BEGIN
        --obtener codigo de local y grupo cia
        select distinct b.cod_local, b.cod_grupo_cia
        into codLocal, codGrupoCia
        from vta_impr_ip b;
        --obtener codigo cia
        select a.cod_cia
        into codCia
        from pbl_local a
        where a.cod_grupo_cia = codGrupoCia
        and a.cod_local = codLocal;
        --si es amazonia el codigo de igv debe ser 00
        IF codCia = COD_CIA_AMAZONIA THEN
           codigoIgv := COD_IGV_CERO;
        ELSE
           codigoIgv := COD_IGV_18;
        END IF;
        --optener el porcentaje adecuado del igv
        select round(c.porc_igv,2)
        into porc
        from pbl_igv c
        where trim(c.cod_igv) = trim(codigoIgv);
                      
    RETURN porc;
    END;
   
/*********************************************************************************/

    FUNCTION F_GET_ESPECIF_IGV_LOCAL(cCodigoIgv_In CHAR)
    RETURN number IS
           porc number(6,2) := '';
    BEGIN

        --optener el porcentaje adecuado del igv
        select round(c.porc_igv,2)
        into porc
        from pbl_igv c
        where trim(c.cod_igv) = trim(cCodigoIgv_In);
                      
    RETURN porc;
    END;
   
/*********************************************************************************/
    FUNCTION F_GET_IND_CAMP_AUTOMATICAS
      RETURN CHAR IS
      vRstp CHAR(1);
    BEGIN
      BEGIN
        SELECT NVL(LLAVE_TAB_GRAL,'N')
        INTO vRstp
        FROM PBL_TAB_GRAL 
        WHERE ID_TAB_GRAL = IND_APLICA_CAMPANA_AUTOMATICAS
        AND EST_TAB_GRAL = 'A';
      EXCEPTION
        WHEN OTHERS THEN
          vRstp := 'N';
      END;
    RETURN vRstp;
    END;

/*********************************************************************************/
    
    FUNCTION F_GET_APLICA_PROM_FID_NO_PTOS
      RETURN CHAR IS
      vRstp CHAR(1);
    BEGIN
      BEGIN
        SELECT NVL(LLAVE_TAB_GRAL,'N')
        INTO vRstp
        FROM PBL_TAB_GRAL 
        WHERE ID_TAB_GRAL = IND_APLICA_CAMPANA_FID_NO_PTOS
        AND EST_TAB_GRAL = 'A';
      EXCEPTION
        WHEN OTHERS THEN
          vRstp := 'N';
      END;
    RETURN vRstp;
    END;
    
   /**********************************************************************************************/
    FUNCTION F_GET_CANT_DIGT_TARJ_SOLICITA
      RETURN NUMBER IS
      vCantidad NUMBER;
    BEGIN
      BEGIN
        SELECT TO_NUMBER(NVL(TRIM(LLAVE_TAB_GRAL),'0'),'999990.00')
        INTO vCantidad
        FROM PBL_TAB_GRAL 
        WHERE ID_TAB_GRAL = 552;
      EXCEPTION
        WHEN OTHERS THEN
          vCantidad := 4;
      END;
      RETURN vCantidad;
    END;
    
   /**********************************************************************************************/    
    FUNCTION F_GET_NVA_VENTANA_DATO_TARJ
      RETURN CHAR IS
      vCantidad VARCHAR2(2);
    BEGIN
      BEGIN
        SELECT NVL(LLAVE_TAB_GRAL,'N')
        INTO vCantidad
        FROM PBL_TAB_GRAL 
        WHERE ID_TAB_GRAL = 551;
      EXCEPTION
        WHEN OTHERS THEN
          vCantidad := 'N';
      END;
      RETURN TRIM(vCantidad);
    END;
END Farma_Gral;
/
