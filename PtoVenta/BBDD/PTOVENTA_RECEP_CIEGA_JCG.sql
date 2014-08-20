--------------------------------------------------------
--  DDL for Package PTOVENTA_RECEP_CIEGA_JCG
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_RECEP_CIEGA_JCG" is

  -- Author  : JCHAVEZ
  -- Created : 16/11/2009 06:29:33 p.m.
  -- Purpose :

  TYPE FarmaCursor IS REF CURSOR;
  COD_NUMERA_SEC_KARDEX      PBL_NUMERA.COD_NUMERA%TYPE := '016';
  g_cMotKardexIngMatriz      LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '101';
  g_cTipDocKdxGuiaES         LGT_KARDEX.Tip_Comp_Pago%TYPE := '02';
  g_cTipCompNumEntrega       LGT_KARDEX.Tip_Comp_Pago%TYPE := '05';
  g_cTipoMotKardexAjusteGuia LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '008';
   v_gNombreDiretorioAlert VARCHAR2(50) := 'DIR_INTERFACES';--'DIR_REPORTES';
     g_cTipoOrigenLocal CHAR(2):= '01';
  g_cTipCompGuia LGT_KARDEX.Tip_Comp_Pago%TYPE := '03';
    g_cTipoOrigenMatriz CHAR(2):= '02';
      g_cTipoOrigenProveedor CHAR(2):= '03';
        g_cTipoOrigenCompetencia CHAR(2):= '04';

  g_cMotKardexSobRecep       LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '011';

  C_INICIO_MSG VARCHAR2(20000) := '<html>'  ||
                                      '<head>'  ||
                                      '<style type="text/css">'  ||
                                      '.style3 {font-family: Arial, Helvetica, sans-serif}'  ||
                                      '.style8 {font-size: 24; }'  ||
                                      '.style9 {font-size: larger}'  ||
                                      '.style12 {'  ||
                                      'font-family: Arial, Helvetica, sans-serif;'  ||
                                      'font-size: larger;'  ||
                                      'font-weight: bold;'  ||
                                      '}'  ||
                                      '</style>'  ||
                                      '</head>'  ||
                                      '<body>'  ||
                                      '<table width="510"border="0">'  ||
                                      '<tr>'  ||
                                      '<td width="487" align="center" valign="top"><h1>DIFERENCIAS</h1></td>'  ||
                                      '</tr>'  ||
                                      '</table>'  ||
                                      '<table width="504" border="1">'  ||
                                      '<tr>'  ||
                                      '<td height="43" align="center" colspan="1"><h2>Producto </h2></td>'  ||
                                      '<td colspan="1" align="center" ><h2>Laboratorio</h2> </td>'  ||
                                      '</tr>';

  C_FIN_MSG VARCHAR2(2000) := '</td>' ||
                                  '</tr>' ||
                                  '</table>' ||
                                  '</body>' ||
                                  '</html>';
   ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;
  --Descripcion: Obtiene listado de guias pendientes por asociar
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  FUNCTION RECEP_F_CUR_LISTA_PROD(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR) RETURN FarmaCursor;

  --Descripcion: Registra la cantidad ingresada en etapa de verificación de conteo ó segundo conteo
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_ACT_CANT_VERF_CONTEO(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cCantidad_in     IN CHAR,
                                         cSecConteo_in    IN CHAR,
                                         --cCodBarra_in     IN CHAR,
                                         cCodProducto_in  IN CHAR,
                                         cUsuario_in      IN CHAR);

  --Descripcion: Actualiza en LGT_DONA_DET, la cantidad de productos contados
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_ACT_CANT_RECEP_ENTREGA(cGrupoCia_in  IN CHAR,
                                           cCodLocal_in  IN CHAR,
                                           cNroRecepcion IN CHAR,
                                           cUsuario_in   IN CHAR);

  --Descripcion: Obtiene el total de guias pendientes en la recepción
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  FUNCTION RECEP_F_INT_CANT_GUIAS_PEND(cGrupoCia_in  IN CHAR,
                                       cCodLocal_in  IN CHAR,
                                       cNroRecepcion IN CHAR) RETURN INTEGER;

  --Descripcion: Obtiene la lista de guias pendientes
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  FUNCTION RECEP_F_CUR_LISTA_GUIAS_PEND(cGrupoCia_in  IN CHAR,
                                        cCodLocal_in  IN CHAR,
                                        cNroRecepcion IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Elimina las entregas  pendiente en caso no se haya contado por lo menos un producto de la guia
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_ELI_EST_GUIAS_A_PEND(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR);

  --Descripcion: Afecta la cantidad contada por cada entrega
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_AFECTA_PRODUCTOS(cGrupoCia_in     IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cNroRecepcion_in IN CHAR,
                                     cIdUsu_in        IN CHAR);

  --Descripcion: Afecta la cantidad contada por cada entrega por página
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_ACT_REG_GUIA_RECEP(cGrupoCia_in   IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNumNota_in    IN CHAR,
                                       cSecDetNota_in IN CHAR,
                                       cNumPag_in     IN CHAR,
                                       cIdUsu_in      IN CHAR);
  --Descripcion: Afecta la cantidad contada por cada entrega por página
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_OPERAC_ANEXAS_PROD(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumNota_in     IN CHAR,
                                       nSecGuia_in     IN NUMBER,
                                       cCodProd_in     IN CHAR,
                                       nCantMov_in     IN NUMBER,
                                       cIdUsu_in       IN CHAR,
                                       nTotalGuia_in   IN NUMBER DEFAULT NULL,
                                       nDiferencia_in  IN NUMBER DEFAULT NULL);

  --Descripcion: Afecta la cantidad contada por cada entrega por página
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_GRABAR_KARDEX(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
                                  cCodProd_in         IN CHAR,
                                  cCodMotKardex_in    IN CHAR,
                                  cTipDocKardex_in    IN CHAR,
                                  cNumTipDoc_in       IN CHAR,
                                  nStkAnteriorProd_in IN NUMBER,
                                  nCantMovProd_in     IN NUMBER,
                                  nValFrac_in         IN NUMBER,
                                  cDescUnidVta_in     IN CHAR,
                                  cUsuCreaKardex_in   IN CHAR,
                                  cCodNumera_in       IN CHAR,
                                  cGlosa_in           IN CHAR DEFAULT NULL,
                                  cTipDoc_in          IN CHAR DEFAULT NULL,
                                  cNumDoc_in          IN CHAR DEFAULT NULL);
  --Descripcion: Afecta la cantidad contada por cada entrega por página
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_ACT_EST_GUIA_RECEP(cGrupoCia_in IN CHAR,
                                       cCodLocal_in IN CHAR,
                                       cNumNota_in  IN CHAR,
                                       cIdUsu_in    IN CHAR);

  --Descripcion: Afecta la cantidad contada por cada entrega por página
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_ACT_EST_GUIA(cGrupoCia_in IN CHAR,
                                 cCodLocal_in IN CHAR,
                                 cNumNota_in  IN CHAR,
                                 cNumPag_in   IN CHAR,
                                 cIdUsu_in    IN CHAR);

  --Descripcion: Obtiene estado de guia
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  FUNCTION RECEP_F_CHAR_OBT_ESTRECEPGUIA(cGrupoCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cNumNota_in  IN CHAR) RETURN CHAR;

  --Descripcion: Obtiene estado de guia
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  FUNCTION RECEP_F_CHAR_OBTIENE_EST_GUIA(cGrupoCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cNumNota_in  IN CHAR,
                                         cNumPag_in   IN CHAR) RETURN CHAR;

  --Descripcion: Afecta productos en LGT_PROD_CONTEO, los que serán afectados en sus respectivas guias
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_ACT_AFECTA_PROD_CONTEO(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cIdUsu_in        IN CHAR);

  --Descripcion: Actualiza el campo indicador de segundo conteo de la recepcion
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_ACT_IND_SEG_CONTEO(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cIdUsu_in        IN CHAR);

/*
  --Descripcion: Obtiene indicador que indica si IP tiene acceso a segundo conteo en la recepcion
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  FUNCTION RECEP_F_CHAR_INDSEGCONTEO_X_IP(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR)RETURN CHAR;

*/

  --Descripcion: Obtiene la lista de productos que faltan contar
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  FUNCTION RECEP_F_CUR_LISTA_PROD_FALTAN(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene la lista de productos que han sobrado al contar
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  FUNCTION RECEP_F_CUR_LISTA_PROD_SOBRANT(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR) RETURN FarmaCursor;


  --Descripcion: Obtiene los datos para imprimir las diferencias
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  FUNCTION RECEP_F_VAR2_IMP_DATOS_DIFE(cGrupoCia_in  IN CHAR,
                                              cCodLocal_in  IN CHAR,
                                              cNroRecepcion IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene la lista de diferencias para imprimir
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  FUNCTION RECEP_F_CUR_LISTA_DIFERENCIAS(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene la lista de diferencias para imprimir
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_ENVIA_CORREO_DIFE(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR);

  --Descripcion: Envia correo con diferencias
  --Fecha       Usuario   Comentario
  --27/11/2009  JCHAVEZ   Creación
  PROCEDURE RECEP_P_ENVIA_CORREO_ADJUNTO(vAsunto_in        IN CHAR,
                                     vTitulo_in        IN CHAR,
                                     vMensaje_in       IN CHAR,
                                     vInd_Archivo_in   IN CHAR DEFAULT 'N',
                                     vNombre_Archivo_in IN VARCHAR2 DEFAULT null
                                     );
                                        FUNCTION RECEP_F_BOOL_VERIFICA_ROL_USU(cCodGrupoCia_in  IN CHAR,
                             cCodLocal_in     IN CHAR,
                             vSecUsu_in       IN CHAR,
                             cCodRol_in       IN CHAR)
  RETURN CHAR;

  --Descripcion: Obtiene Informacion de producto
  --Fecha       Usuario   Comentario
  --27/11/2009  JCHAVEZ   Creación
  FUNCTION RECEP_F_CUR_DATOS_PRODUCTO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cCodProd_in       IN CHAR) RETURN FarmaCursor;


  --Descripcion: Verifica si la cantidad a transferir es menor o igual al stock afectado en la recepción
  --Fecha       Usuario   Comentario
  --27/11/2009  JCHAVEZ   Creación
   FUNCTION RECEP_F_CHAR_VERIFICA_STOCK(cCodGrupoCia_in  IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        cNroRecepcion    IN CHAR,
                                        cCodProd_in      IN CHAR,
                                        nCantidad_in     IN CHAR
                                       ) RETURN CHAR;
     FUNCTION RECEP_F_CHAR_INDVERFCONTEO(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR) RETURN CHAR;
   PROCEDURE RECEP_P_ACT_CANT_RECEPCIONADA( cGrupoCia_in     IN CHAR,
                                         cCodLocal_in        IN CHAR,
                                         cNroRecepcion_in    IN CHAR,
                                         cCodProd_in         IN CHAR,
                                         nCantMov_in         IN INTEGER,
                                         cUsuario_in         IN CHAR,
                                         cFechaVcto_in       IN CHAR DEFAULT NULL,
                                         cLote_in            IN CHAR DEFAULT NULL,
                                         cNumNotaEs_in       IN CHAR DEFAULT NULL);

    PROCEDURE RECEP_P_ACT_IND_SEG_PARAM(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cIdUsu_in        IN CHAR,
                                         cIndicador_in    IN CHAR);

     FUNCTION  RECEP_F_STR_TXTFRACC_MOTIVO RETURN FarmaCursor;
     FUNCTION RECEP_F_CHAR_EXISTE_PRODUCTO(cCodGrupoCia_in IN CHAR,
                                        cCodProd IN CHAR)
                                        RETURN CHAR;
       FUNCTION RECEP_F_CUR_LISTA_MATRIZ(cCodGrupoCia_in IN CHAR)RETURN FarmaCursor;
  PROCEDURE RECEP_P_COMPLETA_CON_CEROS(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cSecConteo_in    IN CHAR,
                                         cCodProducto_in  IN CHAR,
                                         cUsuario_in      IN CHAR)
                                         ;

  PROCEDURE RECEP_P_INS_PROD_TRANSFERENCIA(cGrupoCia_in     IN CHAR,
                                           cCodLocal_in     IN CHAR,
                                           cNumNotaEs_in    IN CHAR,
                                           cNumEntrega      IN CHAR,
                                           cNroRecepcion_in IN CHAR,
                                           cCodProducto_in  IN CHAR,
                                           cLote_in         IN CHAR,
                                           cCantidad_in     IN CHAR,
                                           cFechaVcto_in    IN CHAR,
                                           cUsuario_in      IN CHAR);

  --Descripcion: Obtiene indicador para activar la funcionalidad recepción de almacen o recepcion ciega
  --Fecha       Usuario		Comentario
  --17/12/2009  JCHAVEZ     Creación
  FUNCTION INV_F_GET_IND_TIPO_RECEP_ALM(cCodGrupoCia_in IN CHAR,
      						  			                   cCod_Local_in   IN CHAR)
   RETURN CHAR;

  --Descripcion: Obtiene indicador para activar la funcionalidad recepción de almacen o recepcion ciega
  --Fecha       Usuario	  Comentario
  --07/01/2010  JMIRANDA    Creación
  FUNCTION RECEP_F_LISTA_PROD(cCodGrupoCia_in IN CHAR,
      						  			   cCod_Local_in   IN CHAR,
                             cNumRecepcion_in IN CHAR
                                               )
  RETURN FarmaCursor;

  PROCEDURE ENVIA_CORREO_ATTACH3(cSendorAddress_in in char,
                                cReceiverAddress_in in char,
                                cSubject_in in varchar2,
                                ctitulo_in in varchar2,
                                cmensaje_in in varchar2,
                                pDirectorio IN VARCHAR2,
                                pfilename IN VARCHAR2,
                                cCCReceiverAddress_in in char,
                                cip_servidor in char);

PROCEDURE attach_report3(conn         IN OUT NOCOPY utl_smtp.connection,
			mime_type    IN VARCHAR2 DEFAULT 'text/plain',
			inline       IN BOOLEAN  DEFAULT TRUE,
      directory IN VARCHAR2 DEFAULT NULL,
			filename     IN VARCHAR2 DEFAULT NULL,
		        last         IN BOOLEAN  DEFAULT FALSE);

--JMIRANDA 02.02.10
  PROCEDURE RECEP_P_ENVIA_DIFE_TIEMPO(cCodGrupoCia_in IN CHAR,
      						  			   cCod_Local_in   IN CHAR);
---------------------------------------------------------------------------------------

  FUNCTION RECEP_F_GET_LIM_TRANSF(cCodGrupoCia_in IN CHAR,
      						  			   cCod_Local_in   IN CHAR,
                             cNroRecepcion IN CHAR)
  RETURN CHAR;
---------------------------------------------------------------------------------------
  FUNCTION RECEP_F_CHAR_LIM_FECHA_CANJE (cCodGrupoCia_in IN CHAR,
      						  			   cCod_Local_in   IN CHAR,
                             cCodProd_in IN CHAR,
                             cFechaVenc_in IN DATE)
  RETURN CHAR;
---------------------------------------------------------------------------------------
FUNCTION RECEP_F_CHAR_IND_TIENE_DATA(cGrupoCia_in  CHAR,
                                     cCodLocal_in CHAR,
                                     cNroRecepcion CHAR)
                                     RETURN CHAR;
---------------------------------------------------------------------------------------
  FUNCTION RECEP_F_CHAR_FECHA_CANJE_PROD(cCodGrupoCia_in IN CHAR,
      						  			   cCod_Local_in   IN CHAR,
                             cCodProd_in IN CHAR,
                             cFechaVenc_in IN DATE,
                             cLote_in IN VARCHAR2)
                             RETURN CHAR;
---------------------------------------------------------------------------------------
    FUNCTION RECEP_F_NO_TIENE_FECHA_SAP (cCod_GrupoCia_in IN CHAR,
                                     cCod_Local_in IN CHAR,
                                     cNro_Recepcion IN CHAR,
                                     cCod_Prod_in IN CHAR)

                                     RETURN CHAR;

---------------------------------------------------------------------------------------
 /* JMIRANDA Pendientes para afectar
    27.02.2011
    */
  PROCEDURE RECEP_AFECTA_ENT_PENDIENTES(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cUsu_in IN CHAR);

---------------------------------------------------------------------------------------
  PROCEDURE RECEP_P_AFECTA_PROD_LIBRES(cGrupoCia_in     IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cNroRecepcion_in IN CHAR,
                                     cNumEntrega_in IN CHAR,
                                     cIdUsu_in        IN CHAR);

  /* */
  PROCEDURE RECEP_P_ACT_REG_GUIA_RECEP_L(cGrupoCia_in   IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNumNota_in    IN CHAR,
                                       cSecDetNota_in IN CHAR,
                                       cNumPag_in     IN CHAR,
                                       cIdUsu_in      IN CHAR);

  PROCEDURE RECEP_AFECTA_SOBRANTES(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cUsu_in IN CHAR);

  PROCEDURE RECEP_P_AFECTA_SOB_1(cGrupoCia_in   IN CHAR,
                                 cCodLocal_in   IN CHAR,
                                 cNroRecepcion_in IN CHAR,
                                 cUsu_in IN CHAR);

  PROCEDURE RECEP_P_AFECTA_SOB_2(cGrupoCia_in   IN CHAR,
                                 cCodLocal_in   IN CHAR,
                                 cNroRecepcion_in IN CHAR,
                                 cUsu_in IN CHAR);

  PROCEDURE RECEP_P_ACT_REG_GUIA_RECEP_S(cGrupoCia_in   IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNumNota_in    IN CHAR,
                                       cSecDetNota_in IN CHAR,
                                       cNumPag_in     IN CHAR,
                                       cIdUsu_in      IN CHAR);

  --Descripcion: Obtiene indicador para activar el nuevo proceso de afectar
  --Fecha       Usuario	  Comentario
  --09/08/2011  JMIRANDA    Creación
  FUNCTION INV_F_GET_IND_SOB_AFECTA(cCodGrupoCia_in IN CHAR) RETURN CHAR;

end PTOVENTA_RECEP_CIEGA_JCG;

/
