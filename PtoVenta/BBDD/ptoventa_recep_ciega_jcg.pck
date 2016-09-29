CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_RECEP_CIEGA_JCG" is

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
  
    g_cCodGrupoCia CHAR(3);
	g_cCodLocal CHAR(3);
	g_cNroRecep CHAR(10);
	g_cIndLocalM CHAR(1);
	
	FUNCTION GET_G_CCODGRUPOCIA RETURN CHAR;
	FUNCTION GET_G_CCODLOCAL RETURN CHAR;
	FUNCTION GET_G_CNRORECEP RETURN CHAR;
	FUNCTION GET_G_CINDLOCALM RETURN CHAR;

  C_INICIO_MSG VARCHAR2(20000) := '<html>'  ||
                                      '<head>'  ||
									  '<style type="text/css">'  ||
									  'body {font-family: sans-serif;}' ||
									  'table {border-spacing: 0;}' ||
									  'td {padding: 0px;}' ||
									  '.style8 {font-size: 15px; }'  ||
									  '.style9 {font-size: 20px;font-weight: bold; }' ||
                                      '</style>'  ||
                                      '</head>'  ||
                                      '<body>'  ||
                                      '<table width="410" border="1">'  ||
                                      '<tr><td align="center" colspan=4 class="style9">RECONTEO DE DIFERENCIAS</td></tr>';

  C_FIN_MSG VARCHAR2(2000) := '</td>' ||
                                  '</tr>' ||
                                  '</table>' ||
                                  '</body>' ||
                                  '</html>';
   ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;
   
  --Descripcion: Obtiene listado de guias pendientes por asociar
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  FUNCTION RECEP_F_CUR_LISTA_PROD(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR) RETURN FarmaCursor;

  --Descripcion: Registra la cantidad ingresada en etapa de verificaci�n de conteo � segundo conteo
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  PROCEDURE RECEP_P_ACT_CANT_VERF_CONTEO(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cCantidad_in     IN CHAR,
                                         cSecConteo_in    IN CHAR,
                                         --cCodBarra_in     IN CHAR,
                                         cCodProducto_in  IN CHAR,
                                         cUsuario_in      IN CHAR,
                                         cLote            IN CHAR,
                                         cFechaVencimiento   IN CHAR);

  --Descripcion: Actualiza en LGT_DONA_DET, la cantidad de productos contados
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  PROCEDURE RECEP_P_ACT_CANT_RECEP_ENTREGA(cGrupoCia_in  IN CHAR,
                                           cCodLocal_in  IN CHAR,
                                           cNroRecepcion IN CHAR,
                                           cUsuario_in   IN CHAR);

  --Descripcion: Obtiene el total de guias pendientes en la recepci�n
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  FUNCTION RECEP_F_INT_CANT_GUIAS_PEND(cGrupoCia_in  IN CHAR,
                                       cCodLocal_in  IN CHAR,
                                       cNroRecepcion IN CHAR) RETURN INTEGER;

  --Descripcion: Obtiene la lista de guias pendientes
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  FUNCTION RECEP_F_CUR_LISTA_GUIAS_PEND(cGrupoCia_in  IN CHAR,
                                        cCodLocal_in  IN CHAR,
                                        cNroRecepcion IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Elimina las entregas  pendiente en caso no se haya contado por lo menos un producto de la guia
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  PROCEDURE RECEP_P_ELI_EST_GUIAS_A_PEND(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR);

  --Descripcion: Afecta la cantidad contada por cada entrega
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  PROCEDURE RECEP_P_AFECTA_PRODUCTOS(cGrupoCia_in     IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cNroRecepcion_in IN CHAR,
                                     cIdUsu_in        IN CHAR);

  --Descripcion: Afecta la cantidad contada por cada entrega por p�gina
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  PROCEDURE RECEP_P_ACT_REG_GUIA_RECEP(cGrupoCia_in   IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNumNota_in    IN CHAR,
                                       cSecDetNota_in IN CHAR,
                                       cNumPag_in     IN CHAR,
                                       cIdUsu_in      IN CHAR);

  --Descripcion: Afecta la cantidad contada por cada entrega por p�gina
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
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
                                  cNumDoc_in          IN CHAR DEFAULT NULL,
                                  nNumLoteProd_in     IN CHAR DEFAULT NULL,
                                  nFecVctoProd_in     IN CHAR DEFAULT NULL);
  --Descripcion: Afecta la cantidad contada por cada entrega por p�gina
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  PROCEDURE RECEP_P_ACT_EST_GUIA_RECEP(cGrupoCia_in IN CHAR,
                                       cCodLocal_in IN CHAR,
                                       cNumNota_in  IN CHAR,
                                       cIdUsu_in    IN CHAR);

  --Descripcion: Afecta la cantidad contada por cada entrega por p�gina
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  PROCEDURE RECEP_P_ACT_EST_GUIA(cGrupoCia_in IN CHAR,
                                 cCodLocal_in IN CHAR,
                                 cNumNota_in  IN CHAR,
                                 cNumPag_in   IN CHAR,
                                 cIdUsu_in    IN CHAR);

  --Descripcion: Obtiene estado de guia
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  FUNCTION RECEP_F_CHAR_OBT_ESTRECEPGUIA(cGrupoCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cNumNota_in  IN CHAR) RETURN CHAR;

  --Descripcion: Obtiene estado de guia
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  FUNCTION RECEP_F_CHAR_OBTIENE_EST_GUIA(cGrupoCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cNumNota_in  IN CHAR,
                                         cNumPag_in   IN CHAR) RETURN CHAR;

  --Descripcion: Afecta productos en LGT_PROD_CONTEO, los que ser�n afectados en sus respectivas guias
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  PROCEDURE RECEP_P_ACT_AFECTA_PROD_CONTEO(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cIdUsu_in        IN CHAR);

  --Descripcion: Actualiza el campo indicador de segundo conteo de la recepcion
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  PROCEDURE RECEP_P_ACT_IND_SEG_CONTEO(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cIdUsu_in        IN CHAR);

  --Descripcion: Obtiene la lista de productos que faltan contar
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  FUNCTION RECEP_F_CUR_LISTA_PROD_FALTAN(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene la lista de productos que han sobrado al contar
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  FUNCTION RECEP_F_CUR_LISTA_PROD_SOBRANT(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR) RETURN FarmaCursor;


  --Descripcion: Obtiene los datos para imprimir las diferencias
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  FUNCTION RECEP_F_VAR2_IMP_DATOS_DIFE(cGrupoCia_in  IN CHAR,
                                              cCodLocal_in  IN CHAR,
                                              cNroRecepcion IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene la lista de diferencias para imprimir
  --Fecha       Usuario   Comentario
  --16/11/2009  JCHAVEZ   Creaci�n
  FUNCTION RECEP_F_CUR_LISTA_DIFERENCIAS(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR)
  RETURN FarmaCursor;

  FUNCTION RECEP_F_BOOL_VERIFICA_ROL_USU(cCodGrupoCia_in  IN CHAR,
                             cCodLocal_in     IN CHAR,
                             vSecUsu_in       IN CHAR,
                             cCodRol_in       IN CHAR)
  RETURN CHAR;

  --Descripcion: Obtiene Informacion de producto
  --Fecha       Usuario   Comentario
  --27/11/2009  JCHAVEZ   Creaci�n
  FUNCTION RECEP_F_CUR_DATOS_PRODUCTO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cCodProd_in       IN CHAR) RETURN FarmaCursor;


  --Descripcion: Verifica si la cantidad a transferir es menor o igual al stock afectado en la recepci�n
  --Fecha       Usuario   Comentario
  --27/11/2009  JCHAVEZ   Creaci�n
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

  --Descripcion: Obtiene indicador para activar la funcionalidad recepci�n de almacen o recepcion ciega
  --Fecha       Usuario		Comentario
  --17/12/2009  JCHAVEZ     Creaci�n
  FUNCTION INV_F_GET_IND_TIPO_RECEP_ALM(cCodGrupoCia_in IN CHAR,
      						  			                   cCod_Local_in   IN CHAR)
   RETURN CHAR;

  --Descripcion: Obtiene indicador para activar la funcionalidad recepci�n de almacen o recepcion ciega
  --Fecha       Usuario	  Comentario
  --07/01/2010  JMIRANDA    Creaci�n
  FUNCTION RECEP_F_LISTA_PROD(cCodGrupoCia_in IN CHAR,
      						  			   cCod_Local_in   IN CHAR,
                             cNumRecepcion_in IN CHAR
                                               )
  RETURN FarmaCursor;

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
  --09/08/2011  JMIRANDA    Creaci�n
  FUNCTION INV_F_GET_IND_SOB_AFECTA(cCodGrupoCia_in IN CHAR) RETURN CHAR;

  --Descripcion: Obtiene listado de bultos por producto
  --Fecha       Usuario	  Comentario
  --09/06/2015  ERIOS     Creacion
  FUNCTION GET_LISTA_BULTOS(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR,
								  cCodProd_in IN CHAR) RETURN FarmaCursor;

	--Descripcion: Obtiene listado de bultos por producto para impresion
	--Fecha       Usuario	  Comentario
	--10/06/2015  ERIOS     Creacion
	FUNCTION GET_LISTA_BULTOS_IMP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNroRecepcion_in IN CHAR,cCodProducto_in IN CHAR) RETURN VARCHAR2;
	
end PTOVENTA_RECEP_CIEGA_JCG;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_RECEP_CIEGA_JCG" is

    FUNCTION RECEP_F_CUR_LISTA_PROD(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR) RETURN FarmaCursor IS
    curProductos FarmaCursor;
  BEGIN
    PTOVENTA_RECEP_CIEGA_JCG.g_cCodGrupoCia := cGrupoCia_in;
    PTOVENTA_RECEP_CIEGA_JCG.g_cCodLocal := cCodLocal_in;
    PTOVENTA_RECEP_CIEGA_JCG.g_cNroRecep := cNroRecepcion;
	PTOVENTA_RECEP_CIEGA_JCG.g_cIndLocalM := FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cGrupoCia_in, cCodLocal_in);
   
    OPEN curProductos FOR
SELECT
		RECEP.COD_PRODUCTO || '�' ||
		RECEP.DESCRIP_PROD || '�' || RECEP.UNIDAD || '�' || RECEP.LAB  || '�' ||
    nvl(RECEP.LOTE, ' ') || '�' ||
 		RECEP.CANT2 || '�' ||
		nvl(RECEP.CANT1,'0') || '�' ||
		nvl(to_char(RECEP.CANTIDAD),' ') || '�' ||    
		CASE WHEN RECEP.CANT2 = RECEP.CANTIDAD THEN 'Sin diferencia'
          WHEN RECEP.CANTIDAD > RECEP.CANT2 THEN '+'||(RECEP.CANTIDAD - RECEP.CANT2)||' Sobrante'
          WHEN RECEP.CANTIDAD < RECEP.CANT2 THEN (RECEP.CANTIDAD - RECEP.CANT2)||' Faltante'
          ELSE  ' '
          END || '�' ||
		RECEP.PRODUCTO || '�' ||
		RECEP.SEC_CONTEO || '�' ||
    nvl(RECEP.FECHA_VENCIMIENTO,' ') || '�' ||
    nvl(PROD.IND_LOTE_MAYORISTA ,'N')
    FROM PTOVENTA.V_RECEP_LISTA_PROD RECEP
         LEFT JOIN PTOVENTA.LGT_PROD PROD ON PROD.COD_PROD = RECEP.COD_PRODUCTO;



/*SELECT
		COD_PRODUCTO || '�' ||
		DESCRIP_PROD || '�' || UNIDAD || '�' || LAB  || '�' ||
    nvl(LOTE, ' ') || '�' ||
 		CANT2 || '�' ||
		nvl(CANT1,'0') || '�' ||
		nvl(to_char(CANTIDAD),' ') || '�' ||    
		CASE WHEN CANT2 = CANTIDAD THEN 'Sin diferencia'
          WHEN CANTIDAD > CANT2 THEN '+'||(CANTIDAD - CANT2)||' Sobrante'
          WHEN CANTIDAD < CANT2 THEN (CANTIDAD - CANT2)||' Faltante'
          ELSE  ' '
          END || '�' ||
		PRODUCTO || '�' ||
		SEC_CONTEO || '�' ||
    nvl(FECHA_VENCIMIENTO,' ')
    FROM (
		select *
		from PTOVENTA.V_RECEP_LISTA_PROD
	);*/
  
  
    RETURN curProductos;
  END;
  /***************************************************************************************************************/
  PROCEDURE RECEP_P_ACT_CANT_VERF_CONTEO(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cCantidad_in     IN CHAR,
                                         cSecConteo_in    IN CHAR,
                                         --cCodBarra_in     IN CHAR,
                                         cCodProducto_in  IN CHAR,
                                         cUsuario_in      IN CHAR,
                                         cLote            IN CHAR,
                                         cFechaVencimiento   IN CHAR) AS
  v_vSecConteo LGT_PROD_CONTEO.SEC_CONTEO%TYPE;
  v_Lote varchar2(10) := cLote;
  v_FechaVencimiento CHAR(10) := cFechaVencimiento;
  BEGIN
    IF (FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cGrupoCia_in, cCodLocal_in) = 'S') THEN
      IF (TRIM(cLote) <> '' OR cLote IS NULL) THEN
         IF (TO_NUMBER(TRIM(cCantidad_in)) = 0) THEN
            v_Lote := '';
         ELSE 
            v_Lote := PTOVENTA_TOMA_INV.GET_SIN_LOTE;
         END IF;
         v_FechaVencimiento := TO_CHAR(SYSDATE,'DD/MM/YYYY');
       END IF;
    END IF;

    IF (cSecConteo_in = 0) THEN
		--ERIOS 14.03.2016 Se agrega la condicion por lotes.
        BEGIN
          SELECT SEC_CONTEO  INTO v_vSecConteo
          FROM LGT_PROD_CONTEO
          WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL=cCodLocal_in
          AND NRO_RECEP=cNroRecepcion_in
          AND COD_PROD=cCodProducto_in
		  AND LOTE = cLote;
        EXCEPTION WHEN NO_DATA_FOUND THEN
          v_vSecConteo:=0;
        END;
        IF (v_vSecConteo=0) THEN
              INSERT INTO LGT_PROD_CONTEO(COD_GRUPO_CIA,COD_LOCAL,NRO_RECEP,SEC_CONTEO,COD_PROD,
           USU_SEG_CONTEO,FEC_SEG_CONTEO,CANT_SEG_CONTEO,IP_SEG_CONTEO, LOTE, FECHA_VENCIMIENTO_LOTE)
           VALUES(cGrupoCia_in,
           cCodLocal_in,
           cNroRecepcion_in,
           (SELECT NVL(MAX(A.SEC_CONTEO),0)+1
            FROM LGT_PROD_CONTEO A
            WHERE A.COD_GRUPO_CIA=cGrupoCia_in AND A.COD_LOCAL=cCodLocal_in AND A.NRO_RECEP=cNroRecepcion_in),
            cCodProducto_in,
           cUsuario_in,
            SYSDATE,
            cCantidad_in,
            (SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') FROM DUAL),
            v_Lote,
            to_date(v_FechaVencimiento,'dd/mm/yyyy')
           );
        ELSIF (v_vSecConteo>0) THEN
            UPDATE LGT_PROD_CONTEO A
             SET A.CANT_SEG_CONTEO = cCantidad_in,
                 A.LOTE = v_Lote,
                 A.FECHA_VENCIMIENTO_LOTE = to_date(v_FechaVencimiento,'dd/mm/yyyy'),
                 A.USU_MOD_CONTEO  = cUsuario_in,
                 A.USU_SEG_CONTEO  = cUsuario_in,
                 A.FEC_SEG_CONTEO  = SYSDATE,
                 A.FEC_MOD_CONTEO  = SYSDATE,
                 A.IP_SEG_CONTEO   = (SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS')
                                        FROM DUAL)
           WHERE A.COD_GRUPO_CIA = cGrupoCia_in
             AND A.COD_LOCAL = cCodLocal_in
             AND A.NRO_RECEP = cNroRecepcion_in
             AND A.SEC_CONTEO = v_vSecConteo
           --  AND A.COD_BARRA = cCodBarra_in
             AND A.COD_PROD = cCodProducto_in;
        END IF;

    ELSE

     UPDATE LGT_PROD_CONTEO A
       SET A.CANT_SEG_CONTEO = cCantidad_in,
           A.LOTE = v_Lote,
           A.FECHA_VENCIMIENTO_LOTE = to_date(v_FechaVencimiento,'dd/mm/yyyy'),
           A.USU_MOD_CONTEO  = cUsuario_in,
           A.USU_SEG_CONTEO  = cUsuario_in,
           A.FEC_SEG_CONTEO  = SYSDATE,
           A.FEC_MOD_CONTEO  = SYSDATE,
           A.IP_SEG_CONTEO   = (SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS')
                                  FROM DUAL)
     WHERE A.COD_GRUPO_CIA = cGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.NRO_RECEP = cNroRecepcion_in
       AND A.SEC_CONTEO = cSecConteo_in
     --  AND A.COD_BARRA = cCodBarra_in
       AND A.COD_PROD = cCodProducto_in;
   END IF;
  END;

  /***************************************************************************************************************/
  PROCEDURE RECEP_P_ACT_CANT_RECEP_ENTREGA(cGrupoCia_in  IN CHAR,
                                           cCodLocal_in  IN CHAR,
                                           cNroRecepcion IN CHAR,
                                           cUsuario_in   IN CHAR) AS
    CURSOR curProductos(cIsLocalM CHAR) IS
      SELECT A.COD_GRUPO_CIA,
             A.COD_LOCAL,
             A.NRO_RECEP,
             A.COD_PROD,
             NVL(A.CANT_SEG_CONTEO, A.CANTIDAD) CANTIDAD,
             DECODE(cIsLocalM, 'S', A.LOTE, NULL) LOTE
        FROM LGT_PROD_CONTEO A
       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.NRO_RECEP = cNroRecepcion;

    CURSOR curEntregas(cCodProd LGT_PROD.COD_PROD%TYPE, cLoteProd LGT_PROD_CONTEO.LOTE%TYPE) IS
      SELECT B.NUM_NOTA_ES,
             B.NUM_ENTREGA,
             B.NUM_LOTE_PROD,
             B.COD_PROD,
             B.CANT_ENVIADA_MATR,
             B.CANT_RECEPCIONADA,
             B.SEC_DET_NOTA_ES
        FROM LGT_NOTA_ES_DET B, LGT_RECEP_ENTREGA C
       WHERE B.COD_GRUPO_CIA = cGrupoCia_in
         AND B.COD_LOCAL = cCodLocal_in
         AND C.NRO_RECEP = cNroRecepcion
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_LOCAL = C.COD_LOCAL
         AND B.NUM_NOTA_ES = C.NUM_NOTA_ES
         AND B.NUM_ENTREGA = C.NUM_ENTREGA
         AND B.SEC_GUIA_REM = C.SEC_GUIA_REM
         AND B.COD_PROD = cCodProd
         AND B.NUM_LOTE_PROD = cLoteProd
       ORDER BY B.CANT_ENVIADA_MATR ASC;
    v_CantProdAux NUMBER := 0;
    v_vNumNotaEsAux LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_nCantContadaAux NUMBER := 0;
    v_vSecNotaEsDetAux LGT_NOTA_ES_DET.SEC_DET_NOTA_ES%TYPE;

    v_Cur_Detalle FarmaCursor;
    v_Cur_Prod_Conteo FarmaCursor;

    v_Cant_Error NUMBER;
    v_Cod_Prod_Det lgt_prod_local.cod_prod%TYPE;
    v_Cant_Cont_Det NUMBER;
    v_Cod_Prod_Conteo lgt_prod_local.cod_prod%TYPE;
    v_Cant_Cont_Conteo NUMBER;

    vExisteProducto number;
    v_desc_Local pbl_local.desc_corta_local%TYPE;
    v_Mensaje VARCHAR2(3500) := '';
  BEGIN
       SELECT l.desc_corta_local INTO v_desc_Local
         FROM pbl_local l WHERE l.cod_grupo_cia = cGrupoCia_in AND l.cod_local = cCodLocal_in;

    FOR producto IN curProductos(FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cGrupoCia_in,cCodLocal_in)) LOOP
      v_CantProdAux := producto.CANTIDAD;
      -- JMIRANDA 18.05.2010 LIMPIAR VARIABLES
      v_vSecNotaEsDetAux := '';
      v_vNumNotaEsAux := '';
      v_nCantContadaAux := 0;

      FOR entregas IN curEntregas(producto.Cod_Prod, producto.lote) LOOP
        IF v_CantProdAux < entregas.CANT_ENVIADA_MATR THEN
          UPDATE LGT_NOTA_ES_DET T
             SET T.CANT_RECEPCIONADA   = v_CantProdAux,
                 T.USU_MOD_NOTA_ES_DET = cUsuario_in,
                 T.FEC_MOD_NOTA_ES_DET = SYSDATE,
                 T.CANT_CONTADA        = v_CantProdAux
           WHERE T.COD_GRUPO_CIA = producto.Cod_Grupo_Cia
             AND T.COD_LOCAL = producto.Cod_Local
             AND T.NUM_NOTA_ES = entregas.Num_Nota_Es
             AND T.SEC_DET_NOTA_ES = entregas.Sec_Det_Nota_Es;
          v_CantProdAux := 0;
          dbms_output.put_line('1');
            dbms_output.put_line('entregas.Num_Nota_Es: ' || entregas.Num_Nota_Es);
            dbms_output.put_line('entregas.Sec_Det_Nota_Es: ' || entregas.Sec_Det_Nota_Es);
            dbms_output.put_line('producto.Cod_Prod: ' ||producto.Cod_Prod);
            dbms_output.put_line('CANT A ACT: ' ||v_CantProdAux);
        ELSIF v_CantProdAux > entregas.CANT_ENVIADA_MATR THEN
          UPDATE LGT_NOTA_ES_DET T
             SET T.CANT_RECEPCIONADA   = entregas.CANT_ENVIADA_MATR,
                 T.USU_MOD_NOTA_ES_DET = cUsuario_in,
                 T.FEC_MOD_NOTA_ES_DET = SYSDATE,
                 T.CANT_CONTADA        = entregas.CANT_ENVIADA_MATR
           WHERE T.COD_GRUPO_CIA = producto.Cod_Grupo_Cia
             AND T.COD_LOCAL = producto.Cod_Local
             AND T.NUM_NOTA_ES = entregas.Num_Nota_Es
             AND T.SEC_DET_NOTA_ES = entregas.Sec_Det_Nota_Es;
          v_CantProdAux := v_CantProdAux - entregas.CANT_ENVIADA_MATR;
          v_vNumNotaEsAux := entregas.Num_Nota_Es;
          v_vSecNotaEsDetAux :=entregas.Sec_Det_Nota_Es;
          v_nCantContadaAux:= entregas.CANT_ENVIADA_MATR;
                    dbms_output.put_line('2');
                           dbms_output.put_line('entregas.Num_Nota_Es: ' || entregas.Num_Nota_Es);
            dbms_output.put_line('entregas.Sec_Det_Nota_Es: ' || entregas.Sec_Det_Nota_Es);
            dbms_output.put_line('producto.Cod_Prod: ' ||producto.Cod_Prod);
            dbms_output.put_line('CANT A ACT: ' ||entregas.CANT_ENVIADA_MATR);
        ELSIF v_CantProdAux = entregas.CANT_ENVIADA_MATR THEN
          UPDATE LGT_NOTA_ES_DET T
             SET T.CANT_RECEPCIONADA   = entregas.CANT_ENVIADA_MATR,
                 T.USU_MOD_NOTA_ES_DET = cUsuario_in,
                 T.FEC_MOD_NOTA_ES_DET = SYSDATE,
                 T.CANT_CONTADA    = entregas.CANT_ENVIADA_MATR
           WHERE T.COD_GRUPO_CIA = producto.Cod_Grupo_Cia
             AND T.COD_LOCAL = producto.Cod_Local
             AND T.NUM_NOTA_ES = entregas.Num_Nota_Es
             AND T.SEC_DET_NOTA_ES = entregas.Sec_Det_Nota_Es;
          v_CantProdAux := 0;
                    dbms_output.put_line('3');
                           dbms_output.put_line('entregas.Num_Nota_Es: ' || entregas.Num_Nota_Es);
            dbms_output.put_line('entregas.Sec_Det_Nota_Es: ' || entregas.Sec_Det_Nota_Es);
            dbms_output.put_line('producto.Cod_Prod: ' ||producto.Cod_Prod);
            dbms_output.put_line('CANT A ACT: ' ||entregas.CANT_ENVIADA_MATR);
        END IF;
                 dbms_output.put_line('4');
      END LOOP;
      --PARA DETALLE DE PRODUCTOS CUANDO LA CANTIDAD CONTADA ES MAYOR QUE LA CANTIDAD ENVIADA POR MATRIZ
      --SOBRANTES
          IF v_CantProdAux > 0 THEN
          dbms_output.put_line('eNRA AL FINAL v_CantProdAux: ' ||v_CantProdAux);
           dbms_output.put_line('eNRA AL FINAL USUAIRO: ' ||cUsuario_in);
           dbms_output.put_line('eNRA AL FINAL producto.Cod_Grupo_Cia: ' ||producto.Cod_Grupo_Cia);
           dbms_output.put_line('eNRA AL FINAL  producto.Cod_Local: ' || producto.Cod_Local);
           dbms_output.put_line('eNRA AL FINAL  v_vNumNotaEsAux: ' || v_vNumNotaEsAux);
            dbms_output.put_line('eNRA AL FINAL  v_vSecNotaEsDetAux: ' || v_vSecNotaEsDetAux);
            UPDATE LGT_NOTA_ES_DET T
               SET T.USU_MOD_NOTA_ES_DET = cUsuario_in,
                   T.FEC_MOD_NOTA_ES_DET = SYSDATE,
                   T.CANT_CONTADA        = (v_nCantContadaAux+v_CantProdAux) --CANT CONTADA > CANT ENV MATRIZ (SOBRANTE)
             WHERE T.COD_GRUPO_CIA = producto.Cod_Grupo_Cia
               AND T.COD_LOCAL = producto.Cod_Local
               AND T.NUM_NOTA_ES = v_vNumNotaEsAux
               AND T.SEC_DET_NOTA_ES = v_vSecNotaEsDetAux; --modifica el ULTIMO SECUENCIAL

          END IF;
      --JMIRANDA 18.05.2010 GUARDA MENSAJE PARA MANDAR
        IF (v_nCantContadaAux > 0) THEN
          --debe enviar correo
          v_Mensaje := 'PRODUCTO SOBRANTE. <br>'||
                       'NUM_NOTA_ES: '||v_vNumNotaEsAux||'<br>'||
                       'SEC_NOTA_ES_DET: '||v_vSecNotaEsDetAux||'<br>'||
                       'CANT CONTADA: '||(v_nCantContadaAux+v_CantProdAux);
        END IF;
        -- JMIRANDA 18.05.2010 LIMPIAR VARIABLES
        v_nCantContadaAux := 0;
        v_CantProdAux := 0;
        v_vSecNotaEsDetAux := '';
        v_vNumNotaEsAux := '';
              dbms_output.put_line('5');
-- JMIRANDA 11.05.10
   select count(a)
   into  vExisteProducto
   from  (
       SELECT count(1) a
        FROM LGT_NOTA_ES_DET B, LGT_RECEP_ENTREGA C
       WHERE B.COD_GRUPO_CIA = cGrupoCia_in
         AND B.COD_LOCAL = cCodLocal_in
         AND C.NRO_RECEP = cNroRecepcion
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_LOCAL = C.COD_LOCAL
         AND B.NUM_NOTA_ES = C.NUM_NOTA_ES
         AND B.NUM_ENTREGA = C.NUM_ENTREGA
         AND B.SEC_GUIA_REM = C.SEC_GUIA_REM
         AND B.COD_PROD = producto.Cod_Prod
       GROUP BY B.COD_PROD
       ORDER BY B.COD_PROD ASC);

      if vExisteProducto > 0 then

      SELECT --B.COD_PROD, sum(b.cant_contada)
             sum(b.cant_contada)
        INTO --v_Cod_Prod_Det, v_Cant_Cont_Det
             v_Cant_Cont_Det
        FROM LGT_NOTA_ES_DET B, LGT_RECEP_ENTREGA C
       WHERE B.COD_GRUPO_CIA = cGrupoCia_in
         AND B.COD_LOCAL = cCodLocal_in
         AND C.NRO_RECEP = cNroRecepcion
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_LOCAL = C.COD_LOCAL
         AND B.NUM_NOTA_ES = C.NUM_NOTA_ES
         AND B.NUM_ENTREGA = C.NUM_ENTREGA
         AND B.SEC_GUIA_REM = C.SEC_GUIA_REM
         AND B.COD_PROD = producto.Cod_Prod
       GROUP BY B.COD_PROD
       ORDER BY B.COD_PROD ASC;
      else
          v_Cant_Cont_Det := 0;
      end if;

       v_Mensaje := '';
    END LOOP;
            dbms_output.put_line('6');
  END;

  /***************************************************************************************************************/
  FUNCTION RECEP_F_INT_CANT_GUIAS_PEND(cGrupoCia_in  IN CHAR,
                                       cCodLocal_in  IN CHAR,
                                       cNroRecepcion IN CHAR) RETURN INTEGER IS
    CURSOR curGuias IS
      SELECT DISTINCT A.COD_GRUPO_CIA,
                      A.COD_LOCAL,
                      A.NUM_ENTREGA,
                      A.NUM_NOTA_ES,
                      A.SEC_GUIA_REM,
                      A.NRO_RECEP
        FROM LGT_RECEP_ENTREGA A
       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.NRO_RECEP = cNroRecepcion;
    v_nCantProdGuiasPendientes INT := 0;
    v_nCantProdTotalGuias      INT := 0;
    v_nCantGuiasPendientes     INT := 0;
  BEGIN

    FOR numEntrega IN curGuias LOOP
      SELECT COUNT(*)
        INTO v_nCantProdGuiasPendientes
        FROM LGT_NOTA_ES_DET B
       WHERE B.COD_GRUPO_CIA = numEntrega.COD_GRUPO_CIA
         AND B.COD_LOCAL = numEntrega.COD_LOCAL
         AND B.NUM_NOTA_ES = numEntrega.NUM_NOTA_ES
         AND B.SEC_GUIA_REM = numEntrega.SEC_GUIA_REM
         AND B.NUM_ENTREGA = numEntrega.NUM_ENTREGA
         AND B.CANT_RECEPCIONADA = 0;

      SELECT COUNT(*)
        INTO v_nCantProdTotalGuias
        FROM LGT_NOTA_ES_DET C
       WHERE C.COD_GRUPO_CIA = numEntrega.COD_GRUPO_CIA
         AND C.COD_LOCAL = numEntrega.COD_LOCAL
         AND C.NUM_NOTA_ES = numEntrega.NUM_NOTA_ES
         AND C.SEC_GUIA_REM = numEntrega.SEC_GUIA_REM
         AND C.NUM_ENTREGA = numEntrega.NUM_ENTREGA;

      IF (v_nCantProdGuiasPendientes = v_nCantProdTotalGuias) THEN
        v_nCantGuiasPendientes := v_nCantGuiasPendientes + 1;
      END IF;
    END LOOP;
    RETURN v_nCantGuiasPendientes;
  END;

  /***************************************************************************************************************/
  FUNCTION RECEP_F_CUR_LISTA_GUIAS_PEND(cGrupoCia_in  IN CHAR,
                                        cCodLocal_in  IN CHAR,
                                        cNroRecepcion IN CHAR)
    RETURN FarmaCursor IS
    curGuiasPendientes FarmaCursor;
  BEGIN

    OPEN curGuiasPendientes FOR
      SELECT Q1.NUM_GUIA_REM || '�' || Q1.NUM_ENTREGA || '�' ||
             Q1.FEC_CREA_GUIA_REM
        FROM (SELECT C.NUM_GUIA_REM,
                     B.NUM_ENTREGA,
                     D.FEC_CREA_GUIA_REM,
                     SUM(B.CANT_RECEPCIONADA)
                FROM LGT_NOTA_ES_DET B, LGT_RECEP_ENTREGA C, LGT_GUIA_REM D
               WHERE B.COD_GRUPO_CIA = cGrupoCia_in
                 AND B.COD_LOCAL = cCodLocal_in
                 AND C.NRO_RECEP = cNroRecepcion
                 AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND B.COD_LOCAL = C.COD_LOCAL
                 AND B.NUM_NOTA_ES = C.NUM_NOTA_ES
                 AND B.NUM_ENTREGA = C.NUM_ENTREGA
                 AND B.SEC_GUIA_REM = C.SEC_GUIA_REM
                 AND B.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                 AND B.COD_LOCAL = D.COD_LOCAL
                 AND B.NUM_NOTA_ES = D.NUM_NOTA_ES
                 AND B.SEC_GUIA_REM = D.SEC_GUIA_REM
                 AND B.NUM_ENTREGA = D.NUM_ENTREGA
                 AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                 AND C.COD_LOCAL = D.COD_LOCAL
                 AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
                 AND C.NUM_GUIA_REM = D.NUM_GUIA_REM
                 AND C.NUM_ENTREGA = D.NUM_ENTREGA
                 AND C.SEC_GUIA_REM = D.SEC_GUIA_REM
               GROUP BY B.NUM_ENTREGA, C.NUM_GUIA_REM, D.FEC_CREA_GUIA_REM
              HAVING SUM(B.CANT_RECEPCIONADA) = 0) Q1;

    RETURN curGuiasPendientes;
  END;

  /***************************************************************************************************************/

  PROCEDURE RECEP_P_ELI_EST_GUIAS_A_PEND(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR) AS
    CURSOR curGuiasPendientes IS
      SELECT B.NUM_ENTREGA, SUM(B.CANT_RECEPCIONADA)
        FROM LGT_NOTA_ES_DET B, LGT_RECEP_ENTREGA C, LGT_GUIA_REM D
       WHERE B.COD_GRUPO_CIA = cGrupoCia_in
         AND B.COD_LOCAL = cCodLocal_in
         AND C.NRO_RECEP = cNroRecepcion_in
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_LOCAL = C.COD_LOCAL
         AND B.NUM_NOTA_ES = C.NUM_NOTA_ES
         AND B.NUM_ENTREGA = C.NUM_ENTREGA
         AND B.SEC_GUIA_REM = C.SEC_GUIA_REM
         AND B.COD_GRUPO_CIA = D.COD_GRUPO_CIA
         AND B.COD_LOCAL = D.COD_LOCAL
         AND B.NUM_NOTA_ES = D.NUM_NOTA_ES
         AND B.SEC_GUIA_REM = D.SEC_GUIA_REM
         AND B.NUM_ENTREGA = D.NUM_ENTREGA
         AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
         AND C.COD_LOCAL = D.COD_LOCAL
         AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
         AND C.NUM_GUIA_REM = D.NUM_GUIA_REM
         AND C.NUM_ENTREGA = D.NUM_ENTREGA
         AND C.SEC_GUIA_REM = D.SEC_GUIA_REM
       GROUP BY B.NUM_ENTREGA
      HAVING SUM(B.CANT_RECEPCIONADA) = 0;

  BEGIN
    dbms_output.put_line('Elimina entregas de recepcion, porque no se han contado sus productos');

    FOR guiasEliminadas IN curGuiasPendientes LOOP

    --JMIRANDA 01.02.10 LIBERA LAS GUIAS Y GUARDA EN TABLA HISTORICA DE ENTREGAS DESACIOCIADAS
      INSERT INTO LGT_RECEP_ENTREGA_LIBERADA
      (COD_GRUPO_CIA, COD_LOCAL, NRO_RECEP, NUM_NOTA_ES, NUM_GUIA_REM, NUM_ENTREGA, SEC_GUIA_REM,
       USU_CREA_ENT_LIB, FEC_CREA_ENT_LIB)
      (SELECT  R.COD_GRUPO_CIA, R.COD_LOCAL, R.NRO_RECEP, R.NUM_NOTA_ES, R.NUM_GUIA_REM,
        R.NUM_ENTREGA, R.SEC_GUIA_REM, R.USU_CREA_REC_ENT,SYSDATE
        FROM LGT_RECEP_ENTREGA R
       WHERE R.COD_GRUPO_CIA = cGrupoCia_in
         AND R.COD_LOCAL = cCodLocal_in
         AND R.NRO_RECEP = cNroRecepcion_in
         AND R.NUM_ENTREGA = guiasEliminadas.Num_Entrega);
         dbms_output.put_line('ERROR INSERTAR ENTREGA LIBERADA: '||SQLERRM);

 /*      UPDATE LGT_GUIA_REM GR
      SET GR.TIPO_PED_REP = 'UB'
      WHERE GR.COD_GRUPO_CIA = cGrupoCia_in
        AND GR.COD_LOCAL = cCodLocal_in
        AND GR.NUM_ENTREGA = guiasEliminadas.Num_Entrega;*/
   UPDATE LGT_GUIA_REM GR
      SET GR.IND_GUIA_LIBERADA = 'S',
          GR.FEC_MOD_GUIA_REM = SYSDATE,
          GR.USU_MOD_GUIA_REM = 'USU_LIB_GUIA'
      WHERE GR.COD_GRUPO_CIA = cGrupoCia_in
        AND GR.COD_LOCAL = cCodLocal_in
        AND GR.NUM_ENTREGA = guiasEliminadas.Num_Entrega;
    -- termina de guardar las guias liberadas
      DELETE LGT_RECEP_ENTREGA A
       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.NRO_RECEP = cNroRecepcion_in
         AND A.NUM_ENTREGA = guiasEliminadas.Num_Entrega;
    -- JMIRANDA 01.02.10 ACTUALIZA LA CANT DE GUIAS
      UPDATE LGT_RECEP_MERCADERIA M
         SET M.CANT_GUIAS = (SELECT COUNT(*) FROM LGT_RECEP_ENTREGA WHERE COD_GRUPO_CIA = cGrupoCia_in
                                                               AND COD_LOCAL = cCodLocal_in
                                                               AND NRO_RECEP = cNroRecepcion_in)
       WHERE M.COD_GRUPO_CIA = cGrupoCia_in
         AND M.COD_LOCAL = cCodLocal_in
         AND M.NRO_RECEP = cNroRecepcion_in;
    END LOOP;
  END;

  /***************************************************************************************************************/
  PROCEDURE RECEP_P_AFECTA_PRODUCTOS(cGrupoCia_in     IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cNroRecepcion_in IN CHAR,
                                     cIdUsu_in        IN CHAR) AS

    CURSOR curProd IS
      SELECT A.COD_GRUPO_CIA,
             A.COD_LOCAL,
             A.SEC_DET_NOTA_ES,
             A.NUM_NOTA_ES,
             A.NUM_PAG_RECEP
        FROM LGT_NOTA_ES_DET A, LGT_RECEP_ENTREGA B/*, LGT_PROD_CONTEO C*/
       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND B.NRO_RECEP = cNroRecepcion_in
         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.NUM_NOTA_ES = B.NUM_NOTA_ES
         AND A.SEC_GUIA_REM = B.SEC_GUIA_REM
         AND A.NUM_ENTREGA = B.NUM_ENTREGA
         AND A.IND_PROD_AFEC <> 'S'
         /*AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND A.COD_LOCAL = C.COD_LOCAL
         AND A.COD_PROD = C.COD_PROD
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_LOCAL = C.COD_LOCAL
         AND B.NRO_RECEP = C.NRO_RECEP*/
         --AND A.CANT_RECEPCIONADA > 0 -- para que afecte todo = AFECTADO TOTAL
         ;
    v_rCurProd curProd%ROWTYPE;
  BEGIN
    dbms_output.put_line('Elimina entregas de recepcion, porque no se han contado sus productos');
    --se esta afectado total
    RECEP_P_ACT_AFECTA_PROD_CONTEO(cGrupoCia_in,
                                 cCodLocal_in,
                                 cNroRecepcion_in,
                                 cIdUsu_in);

    FOR v_rCurProd IN curProd LOOP
      RECEP_P_ACT_REG_GUIA_RECEP(v_rCurProd.Cod_Grupo_Cia,
                                 v_rCurProd.Cod_Local,
                                 v_rCurProd.Num_Nota_Es,
                                 v_rCurProd.SEC_DET_NOTA_ES,
                                 v_rCurProd.Num_Pag_Recep,
                                 cIdUsu_in);
    END LOOP;

    UPDATE LGT_RECEP_MERCADERIA A
       SET A.ESTADO      = 'T',
           FEC_MOD_RECEP = SYSDATE,
           USU_MOD_RECEP = cIdUsu_in
     WHERE COD_GRUPO_CIA = cGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NRO_RECEP = cNroRecepcion_in;

  END;

  /*********************************************************************************************************************/

  PROCEDURE RECEP_P_ACT_REG_GUIA_RECEP(cGrupoCia_in   IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNumNota_in    IN CHAR,
                                       cSecDetNota_in IN CHAR,
                                       cNumPag_in     IN CHAR,
                                       cIdUsu_in      IN CHAR) IS
    v_cEstDet   LGT_NOTA_ES_DET.EST_NOTA_ES_DET%TYPE;
    v_nSecGuia  LGT_NOTA_ES_DET.SEC_GUIA_REM%TYPE;
    v_cCodProd  LGT_NOTA_ES_DET.COD_PROD%TYPE;
    v_nCantMov  LGT_NOTA_ES_DET.CANT_MOV%TYPE;
    v_nCantMatr LGT_NOTA_ES_DET.CANT_ENVIADA_MATR%TYPE;
    v_nCantDif  LGT_NOTA_ES_DET.CANT_MOV%TYPE;
    -- KMONCADA 14.07.2016 [PROYECTO M]
    v_nNumLoteProd LGT_NOTA_ES_DET.NUM_LOTE_PROD%TYPE;
    v_nFecVctoProd LGT_NOTA_ES_DET.FEC_VCTO_PROD%TYPE;
  BEGIN
    SELECT EST_NOTA_ES_DET,
           SEC_GUIA_REM,
           COD_PROD,
           CANT_RECEPCIONADA,
           CANT_ENVIADA_MATR,
           (CANT_ENVIADA_MATR - CANT_RECEPCIONADA/*CANT_MOV*/),
           NUM_LOTE_PROD,
           FEC_VCTO_PROD
      INTO v_cEstDet,
           v_nSecGuia,
           v_cCodProd,
           v_nCantMov,
           v_nCantMatr,
           v_nCantDif,
           v_nNumLoteProd,
           v_nFecVctoProd
      FROM LGT_NOTA_ES_DET
     WHERE COD_GRUPO_CIA = cGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in
       AND SEC_DET_NOTA_ES = cSecDetNota_in
       AND IND_PROD_AFEC = 'N'
       FOR UPDATE;

    IF v_cEstDet = 'T' THEN
      --RECEP_P_OPERAC_ANEXAS_PROD comentado y reemplazado para pruebas ASOSA, 21.07.2010
      PTOVTA_RESPALDO_STK.RECEP_P_OPERAC_ANEXAS_PROD(cGrupoCia_in, --ASOSA, 21.07.2010
                                 cCodLocal_in,
                                 cNumNota_in,
                                 v_nSecGuia,
                                 v_cCodProd,
                                 v_nCantMov,
                                 cIdUsu_in,
                                 v_nCantMatr,
                                 v_nCantDif,
                                 nNumLoteProd_in => v_nNumLoteProd,
                                 nFecVctoProd_in => TO_CHAR(v_nFecVctoProd,'DD/MM/YYYY'));
      UPDATE LGT_NOTA_ES_DET
         SET USU_MOD_NOTA_ES_DET = cIdUsu_in,
             FEC_MOD_NOTA_ES_DET = SYSDATE,
             IND_PROD_AFEC       = 'S',
             EST_NOTA_ES_DET     = 'A'
       WHERE COD_GRUPO_CIA = cGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND NUM_NOTA_ES = cNumNota_in
         AND SEC_DET_NOTA_ES = cSecDetNota_in
         AND IND_PROD_AFEC <> 'S';
    ELSIF v_cEstDet = 'P' THEN
      --RECEP_P_OPERAC_ANEXAS_PROD comentado y reemplazado para pruebas ASOSA, 21.07.2010
      PTOVTA_RESPALDO_STK.RECEP_P_OPERAC_ANEXAS_PROD(cGrupoCia_in, --ASOSA, 21.07.2010
                                 cCodLocal_in,
                                 cNumNota_in,
                                 v_nSecGuia,
                                 v_cCodProd,
                                 v_nCantMov,
                                 cIdUsu_in,
                                 v_nCantMatr,
                                 v_nCantDif,
                                 nNumLoteProd_in => v_nNumLoteProd,
                                 nFecVctoProd_in => TO_CHAR(v_nFecVctoProd,'DD/MM/YYYY')/*0*/);
      UPDATE LGT_NOTA_ES_DET
         SET USU_MOD_NOTA_ES_DET = cIdUsu_in,
             FEC_MOD_NOTA_ES_DET = SYSDATE,
             CANT_MOV            = v_nCantMov,
             IND_PROD_AFEC       = 'S',
             EST_NOTA_ES_DET     = 'A'
       WHERE COD_GRUPO_CIA = cGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND NUM_NOTA_ES = cNumNota_in
         AND SEC_DET_NOTA_ES = cSecDetNota_in
         AND IND_PROD_AFEC <> 'S';
    END IF;

    RECEP_P_ACT_EST_GUIA_RECEP(cGrupoCia_in,
                               cCodLocal_in,
                               cNumNota_in,
                               cIdUsu_in);

    RECEP_P_ACT_EST_GUIA(cGrupoCia_in,
                         cCodLocal_in,
                         cNumNota_in,
                         cNumPag_in,
                         cIdUsu_in);

  END;

  /* ***********************************************************************************************************/
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
                                  cNumDoc_in          IN CHAR DEFAULT NULL,
                                  nNumLoteProd_in     IN CHAR DEFAULT NULL,
                                  nFecVctoProd_in     IN CHAR DEFAULT NULL) IS
    v_cSecKardex LGT_KARDEX.SEC_KARDEX%TYPE;
    v_Lote varchar2(10) := nNumLoteProd_in;
    v_FechaVencimiento CHAR(10) := nFecVctoProd_in;
  BEGIN
    IF (FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cCodGrupoCia_in, cCodLocal_in) = 'S') THEN
      IF (TRIM(nNumLoteProd_in) <> '' OR nNumLoteProd_in IS NULL) THEN
         v_Lote := PTOVENTA_TOMA_INV.GET_SIN_LOTE;
         v_FechaVencimiento := TO_CHAR(SYSDATE,'DD/MM/YYYY');
       END IF;
    END IF;
    
    v_cSecKardex := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,
                                                                                         cCodLocal_in,
                                                                                         cCodNumera_in),
                                                        10,
                                                        '0',
                                                        'I');
    INSERT INTO LGT_KARDEX
      (COD_GRUPO_CIA,
       COD_LOCAL,
       SEC_KARDEX,
       COD_PROD,
       COD_MOT_KARDEX,
       TIP_DOC_KARDEX,
       NUM_TIP_DOC,
       STK_ANTERIOR_PROD,
       CANT_MOV_PROD,
       STK_FINAL_PROD,
       VAL_FRACC_PROD,
       DESC_UNID_VTA,
       USU_CREA_KARDEX,
       DESC_GLOSA_AJUSTE,
       TIP_COMP_PAGO,
       NUM_COMP_PAGO,
       NRO_LOTE,
       FECHA_VENCIMIENTO_LOTE)
    VALUES
      (cCodGrupoCia_in,
       cCodLocal_in,
       v_cSecKardex,
       cCodProd_in,
       cCodMotKardex_in,
       cTipDocKardex_in,
       cNumTipDoc_in,
       nStkAnteriorProd_in,
       nCantMovProd_in,
       (nStkAnteriorProd_in + nCantMovProd_in),
       nValFrac_in,
       cDescUnidVta_in,
       cUsuCreaKardex_in,
       cGlosa_in,
       cTipDoc_in,
       cNumDoc_in,
       v_Lote,
       TO_DATE(v_FechaVencimiento,'DD/MM/YYYY'));
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cCodNumera_in,
                                               cUsuCreaKardex_in);
  END;

  /*******************************************************************************************************************/
  PROCEDURE RECEP_P_ACT_EST_GUIA_RECEP(cGrupoCia_in IN CHAR,
                                       cCodLocal_in IN CHAR,
                                       cNumNota_in  IN CHAR,
                                       cIdUsu_in    IN CHAR) IS
    vEst CHAR(1);
  BEGIN
    vEst := RECEP_F_CHAR_OBT_ESTRECEPGUIA(cGrupoCia_in,
                                          cCodLocal_in,
                                          cNumNota_in);

    UPDATE LGT_NOTA_ES_CAB
       SET USU_MOD_NOTA_ES_CAB = cIdUsu_in,
           FEC_MOD_NOTA_ES_CAB = SYSDATE,
           EST_RECEPCION       = vEst
     WHERE COD_GRUPO_CIA = cGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in;
  END;

  /***************************************************************************************************************/
  PROCEDURE RECEP_P_ACT_EST_GUIA(cGrupoCia_in IN CHAR,
                                 cCodLocal_in IN CHAR,
                                 cNumNota_in  IN CHAR,
                                 cNumPag_in   IN CHAR,
                                 cIdUsu_in    IN CHAR) AS
    vEst CHAR(1);
  BEGIN
    vEst := RECEP_F_CHAR_OBTIENE_EST_GUIA(cGrupoCia_in,
                                          cCodLocal_in,
                                          cNumNota_in,
                                          cNumPag_in);

    UPDATE LGT_GUIA_REM
       SET USU_MOD_GUIA_REM = cIdUsu_in,
           FEC_MOD_GUIA_REM = SYSDATE,
           IND_GUIA_CERRADA = vEst
     WHERE COD_GRUPO_CIA = cGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in
       AND SEC_GUIA_REM = cNumPag_in;
  END;

  /******************************************************************************************************************/
  FUNCTION RECEP_F_CHAR_OBT_ESTRECEPGUIA(cGrupoCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cNumNota_in  IN CHAR) RETURN CHAR IS
    vCantTotal     NUMBER;
    vCantAfectados NUMBER;
    vEstNeo        CHAR(1);
    vEstOrig       CHAR(1);
  BEGIN

    SELECT COUNT(*)
      INTO vCantAfectados
      FROM LGT_NOTA_ES_DET
     WHERE COD_GRUPO_CIA = cGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in
       AND IND_PROD_AFEC = 'S';

    SELECT COUNT(*)
      INTO vCantTotal
      FROM LGT_NOTA_ES_DET
     WHERE COD_GRUPO_CIA = cGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in;

    SELECT EST_RECEPCION
      INTO vEstOrig
      FROM LGT_NOTA_ES_CAB
     WHERE COD_GRUPO_CIA = cGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in;

    IF vEstOrig <> 'N' THEN
      BEGIN
        IF vCantTotal = vCantAfectados THEN
          vEstNeo := 'C'; --CERRADA
        END IF;

        IF vCantAfectados = 0 THEN
          vEstNeo := 'E';
        END IF;

        IF vCantTotal > vCantAfectados THEN
          vEstNeo := 'P';
        END IF;
      END;
    ELSE
      BEGIN
        vEstNeo := 'N';
      END;
    END IF;

    RETURN vEstNeo;

  END;

  /****************************************************************************************************************/
  FUNCTION RECEP_F_CHAR_OBTIENE_EST_GUIA(cGrupoCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cNumNota_in  IN CHAR,
                                         cNumPag_in   IN CHAR) RETURN CHAR IS
    vCantTotal     NUMBER;
    vCantAfectados NUMBER;
    vEstNeo        CHAR(1);
    vEstOrig       CHAR(1);
  BEGIN

    SELECT COUNT(*)
      INTO vCantAfectados
      FROM LGT_NOTA_ES_DET
     WHERE COD_GRUPO_CIA = cGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in
       AND NUM_PAG_RECEP = cNumPag_in
       AND IND_PROD_AFEC = 'S';

    SELECT COUNT(*)
      INTO vCantTotal
      FROM LGT_NOTA_ES_DET
     WHERE COD_GRUPO_CIA = cGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in
       AND NUM_PAG_RECEP = cNumPag_in;

    IF vCantTotal = vCantAfectados THEN
      vEstNeo := 'S'; --CERRADA
    ELSE
      vEstNeo := 'N';
    END IF;

    RETURN vEstNeo;
  END;
 /******************************************************************************************************/
  PROCEDURE RECEP_P_ACT_AFECTA_PROD_CONTEO(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cIdUsu_in        IN CHAR) AS
  BEGIN

    UPDATE LGT_PROD_CONTEO A
       SET A.IP_SEG_CONTEO   = (SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS')
                                  FROM DUAL),
           A.ESTADO_AFECTADO = 'S',
           A.FEC_MOD_CONTEO  = SYSDATE,
           A.FEC_SEG_CONTEO  = SYSDATE,
           A.USU_MOD_CONTEO  = cIdUsu_in,
           A.USU_SEG_CONTEO  = cIdUsu_in
     WHERE A.COD_GRUPO_CIA = cGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.NRO_RECEP = cNroRecepcion_in
       AND A.COD_PROD IN
           (SELECT Q1.COD_PROD
              FROM (SELECT B.COD_PROD, SUM(B.CANT_RECEPCIONADA)
                      FROM LGT_NOTA_ES_DET B, LGT_RECEP_ENTREGA C
                     WHERE B.COD_GRUPO_CIA = cGrupoCia_in
                       AND B.COD_LOCAL = cCodLocal_in
                       AND C.NRO_RECEP = cNroRecepcion_in
                       AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                       AND B.COD_LOCAL = C.COD_LOCAL
                       AND B.NUM_NOTA_ES = C.NUM_NOTA_ES
                       AND B.NUM_ENTREGA = C.NUM_ENTREGA
                       AND B.SEC_GUIA_REM = C.SEC_GUIA_REM
                     GROUP BY B.COD_PROD
                    HAVING SUM(B.CANT_RECEPCIONADA) > 0) Q1);

  END;

  /*********************************************************************************************************************/
  PROCEDURE RECEP_P_ACT_IND_SEG_CONTEO(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cIdUsu_in        IN CHAR)
  AS
  BEGIN
       UPDATE LGT_RECEP_MERCADERIA A
       SET A.IND_SEG_CONTEO = 'S',
           A.FEC_MOD_RECEP  = SYSDATE,
           A.USU_MOD_RECEP  = cIdUsu_in
       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
       AND   A.COD_LOCAL     = cCodLocal_in
       AND   A.NRO_RECEP     = cNroRecepcion_in;

  END;

  /*********************************************************************************************************************/
  FUNCTION RECEP_F_CUR_LISTA_PROD_FALTAN(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR) RETURN FarmaCursor
  IS
    curProductosFaltantes FarmaCursor;
  BEGIN
      OPEN curProductosFaltantes FOR
      --JMIRANDA 09.08.2011
      SELECT PRODUCTO || '�' || DESCRIP_PROD || '�' || UNIDAD || '�' || LAB || '�' || CANT_CONTADA || '�' || CANT_ENTREGADA || '�' ||
      --SELECT  /*PRODUCTO || '�' || */DESCRIP_PROD || '�' || UNIDAD || '�' || LAB || '�' || CANT_CONTADA || '�' || CANT_ENTREGADA || '�' ||
      --SELECT  DESCRIP_PROD || '�' || UNIDAD || '�' || LAB || '�' || CANT_CONTADA || '�' || CANT_ENTREGADA || '�' ||
              NUM_ENTREGA  || '�' ||
             (SELECT CASE
                       WHEN CANT < 0 THEN
                        CANT * (-1)
                       ELSE
                        CANT
                     END
                FROM DUAL) || '�' ||
                PRODUCTO  --JMIRANDA 14.01.10 SE AGREGO PARA MOSTRAR COD_PROD EN IMPRESION
        FROM (SELECT Q1.PROD PRODUCTO,
                     (Q2.CANT2 - Q2.CANT_CONTADA) CANT,
                     --Q1.COD_BARRA,
                     Q3.DESC_PROD DESCRIP_PROD,
                     Q3.DESC_UNID_PRESENT UNIDAD,
                     (SELECT L.NOM_LAB
                        FROM LGT_LAB L
                       WHERE L.COD_LAB = Q3.COD_LAB) LAB,
                     Q1.SEC_CONTEO,
                     Q1.CANT1 CANTTOTAL_CONTADA,
                     Q2.CANT2 CANT_ENTREGADA,
                     Q2.NUM_ENTREGA,
                     Q2.CANT_CONTADA CANT_CONTADA
                FROM (SELECT A.COD_PROD PROD,
                             NVL(A.CANT_SEG_CONTEO, A.CANTIDAD) CANT1,
                             --A.COD_BARRA COD_BARRA,
                             A.SEC_CONTEO SEC_CONTEO
                        FROM LGT_PROD_CONTEO A --, LGT_NOTA_ES_DET B
                       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
                         AND A.COD_LOCAL = cCodLocal_in
                         AND A.NRO_RECEP = cNroRecepcion) Q1,

                     (SELECT B.COD_PROD PROD,
                             SUM(B.CANT_ENVIADA_MATR) CANT2,
                             ' ' COD_BARRA,
                             B.NUM_ENTREGA NUM_ENTREGA,
                             SUM(B.CANT_CONTADA) CANT_CONTADA
                        FROM LGT_NOTA_ES_DET B
                       WHERE B.COD_GRUPO_CIA = cGrupoCia_in
                         AND B.COD_LOCAL = cCodLocal_in
                         AND B.NUM_ENTREGA || B.NUM_NOTA_ES IN
                             (SELECT C.NUM_ENTREGA || C.NUM_NOTA_ES
                                FROM LGT_RECEP_ENTREGA C
                               WHERE C.COD_GRUPO_CIA = cGrupoCia_in
                                 AND C.COD_LOCAL = cCodLocal_in
                                 AND C.NRO_RECEP = cNroRecepcion)
                       GROUP BY B.COD_PROD,B.NUM_ENTREGA,B.CANT_CONTADA) Q2,
                     LGT_PROD Q3

               WHERE Q1.PROD = Q2.PROD
                 AND Q3.COD_GRUPO_CIA = cGrupoCia_in
                 AND Q3.COD_PROD = Q1.PROD
              )
       WHERE CANT > 0
       UNION
       --JMIRANDA 09.08.2011
       SELECT K.COD_PROD || '�' || K.DESC_PROD || '�' || K.DESC_UNID_PRESENT || '�' ||  (SELECT L.NOM_LAB FROM LGT_LAB L WHERE L.COD_LAB = K.COD_LAB) || '�' || 0 || '�' || I.CANT_ENVIADA_MATR || '�' ||I.NUM_ENTREGA || '�' || I.CANT_ENVIADA_MATR || '�' ||K.COD_PROD
       --SELECT/* K.COD_PROD || '�' ||*/ K.DESC_PROD || '�' || K.DESC_UNID_PRESENT || '�' ||  (SELECT L.NOM_LAB FROM LGT_LAB L WHERE L.COD_LAB = K.COD_LAB) || '�' || 0 || '�' || I.CANT_ENVIADA_MATR || '�' ||I.NUM_ENTREGA || '�' || I.CANT_ENVIADA_MATR
        --SELECT K.DESC_PROD || '�' || K.DESC_UNID_PRESENT || '�' ||  (SELECT L.NOM_LAB FROM LGT_LAB L WHERE L.COD_LAB = K.COD_LAB) || '�' || 0 || '�' || I.CANT_ENVIADA_MATR || '�' ||I.NUM_ENTREGA || '�' || I.CANT_ENVIADA_MATR || '�' ||
        --K.COD_PROD --JMIRANDA 14.01.10 SE AGREGO PARA AGREGAR EL COD_PROD
        FROM LGT_NOTA_ES_DET I, LGT_PROD K
        WHERE I.COD_GRUPO_CIA = cGrupoCia_in
        AND I.COD_LOCAL       = cCodLocal_in
        AND I.NUM_ENTREGA IN (SELECT J.NUM_ENTREGA FROM  LGT_RECEP_ENTREGA J
                              WHERE J.COD_GRUPO_CIA = cGrupoCia_in
                              AND J.COD_LOCAL = cCodLocal_in
                              AND J.NRO_RECEP = cNroRecepcion
                             )
        AND I.COD_PROD NOT IN (
                                SELECT K.COD_PROD FROM LGT_PROD_CONTEO K
                                WHERE K.COD_GRUPO_CIA = cGrupoCia_in
                                AND K.COD_LOCAL       = cCodLocal_in
                                AND K.NRO_RECEP       = cNroRecepcion
                              )
        AND I.COD_GRUPO_CIA = K.COD_GRUPO_CIA
        AND I.COD_PROD      = K.COD_PROD       ;

      RETURN curProductosFaltantes;
  END;

  /*********************************************************************************************************************/
  FUNCTION RECEP_F_CUR_LISTA_PROD_SOBRANT(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR) RETURN FarmaCursor
  IS
    curProductosSobrantes FarmaCursor;
  BEGIN
      OPEN curProductosSobrantes FOR
      --JMIRANDA 09.08.2011
       SELECT  PRODUCTO || '�' || DESCRIP_PROD || '�' || UNIDAD || '�' || LAB || '�' || CANT_CONTADA || '�' || CANT_ENTREGADA || '�' ||
       --SELECT  DESCRIP_PROD || '�' || UNIDAD || '�' || LAB || '�' || CANT_CONTADA || '�' || CANT_ENTREGADA || '�' ||
              NUM_ENTREGA  || '�' ||
             (SELECT CASE
                       WHEN CANT < 0 THEN
                        CANT * (-1)
                       ELSE
                        CANT
                     END
                FROM DUAL) || '�' ||
                PRODUCTO
        FROM (SELECT Q1.PROD PRODUCTO,
                     (Q2.CANT2 - Q2.CANT_CONTADA) CANT,
                     --Q1.COD_BARRA,
                     Q3.DESC_PROD DESCRIP_PROD,
                     Q3.DESC_UNID_PRESENT UNIDAD,
                     (SELECT L.NOM_LAB
                        FROM LGT_LAB L
                       WHERE L.COD_LAB = Q3.COD_LAB) LAB,
                     Q1.SEC_CONTEO,
                     Q1.CANT1 CANTTOTAL_CONTADA,
                     Q2.CANT2 CANT_ENTREGADA,
                     Q2.NUM_ENTREGA,
                     Q2.CANT_CONTADA CANT_CONTADA
                FROM (SELECT A.COD_PROD PROD,
                             NVL(A.CANT_SEG_CONTEO, A.CANTIDAD) CANT1,
                             --A.COD_BARRA COD_BARRA,
                             A.SEC_CONTEO SEC_CONTEO
                        FROM LGT_PROD_CONTEO A --, LGT_NOTA_ES_DET B
                       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
                         AND A.COD_LOCAL = cCodLocal_in
                         AND A.NRO_RECEP = cNroRecepcion) Q1,

                     (SELECT B.COD_PROD PROD,
                             SUM(B.CANT_ENVIADA_MATR) CANT2,
                             ' ' COD_BARRA,
                             B.NUM_ENTREGA NUM_ENTREGA,
                             SUM(B.CANT_CONTADA) CANT_CONTADA
                        FROM LGT_NOTA_ES_DET B
                       WHERE B.COD_GRUPO_CIA = cGrupoCia_in
                         AND B.COD_LOCAL = cCodLocal_in
                         AND B.NUM_ENTREGA || B.NUM_NOTA_ES IN
                             (SELECT C.NUM_ENTREGA || C.NUM_NOTA_ES
                                FROM LGT_RECEP_ENTREGA C
                               WHERE C.COD_GRUPO_CIA = cGrupoCia_in
                                 AND C.COD_LOCAL = cCodLocal_in
                                 AND C.NRO_RECEP = cNroRecepcion)
                       GROUP BY B.COD_PROD,B.NUM_ENTREGA,B.CANT_CONTADA) Q2,
                     LGT_PROD Q3

               WHERE Q1.PROD = Q2.PROD
                 AND Q3.COD_GRUPO_CIA = cGrupoCia_in
                 AND Q3.COD_PROD = Q1.PROD
              )
       WHERE CANT < 0

      UNION
      --FUERA DE GUIA
      SELECT H.COD_PROD || '�' || H.DESC_PROD || '�' || H.DESC_UNID_PRESENT || '�' ||
      --SELECT  H.DESC_PROD || '�' || H.DESC_UNID_PRESENT || '�' ||
             (SELECT L.NOM_LAB FROM LGT_LAB L WHERE L.COD_LAB = H.COD_LAB) || '�' ||
             NVL(E.CANT_SEG_CONTEO, E.CANTIDAD) || '�' ||
             0  || '�' || ' ' || 0 || '�' || H.COD_PROD
        FROM LGT_PROD_CONTEO E, LGT_PROD H
       WHERE E.COD_GRUPO_CIA = cGrupoCia_in
         AND E.COD_LOCAL = cCodLocal_in
         AND E.NRO_RECEP = cNroRecepcion
         AND E.COD_PROD NOT IN
             (SELECT F.COD_PROD
                FROM LGT_NOTA_ES_DET F, LGT_RECEP_ENTREGA G
               WHERE F.COD_GRUPO_CIA = cGrupoCia_in
                 AND F.COD_LOCAL = cCodLocal_in
                 AND G.NRO_RECEP = cNroRecepcion
               --  AND F.EST_NOTA_ES_DET = 'A'
                 AND F.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                 AND F.COD_LOCAL = G.COD_LOCAL
                 AND F.NUM_NOTA_ES = G.NUM_NOTA_ES
                 AND F.NUM_ENTREGA = G.NUM_ENTREGA
                 AND F.SEC_GUIA_REM = G.SEC_GUIA_REM)
         AND E.COD_GRUPO_CIA = H.COD_GRUPO_CIA
         AND E.COD_PROD = H.COD_PROD
         and NVL(E.CANT_SEG_CONTEO, E.CANTIDAD) > 0;
      RETURN curProductosSobrantes;
  END;

  /*************************************************************************************************************/
  FUNCTION RECEP_F_VAR2_IMP_DATOS_DIFE(cGrupoCia_in  IN CHAR,
                                              cCodLocal_in  IN CHAR,
                                              cNroRecepcion IN CHAR) RETURN VARCHAR2
    IS
    vMsg_out VARCHAR2(32767) := '';
    vFila_1  VARCHAR2(32767) := '';
    vFila_41 VARCHAR2(32767) := '';

    vProducto      VARCHAR2(400);
	vDescProd LGT_PROD.DESC_PROD%TYPE;
    vLaboratorio VARCHAR2(400);
    vUnidadPresentacion VARCHAR2(400);
	v_nCant2 NUMBER;
	v_vUnidadCorta VARCHAR2(100);
	
    i NUMBER(7) := 0;

    curDiferencias FarmaCursor := RECEP_F_CUR_LISTA_DIFERENCIAS(cGrupoCia_in,
                                              cCodLocal_in,
                                              cNroRecepcion);

    v_vBultos VARCHAR2(300);
  BEGIN
	LOOP
      FETCH curDiferencias
        INTO vProducto,vDescProd,vUnidadPresentacion, vLaboratorio, v_nCant2,v_vUnidadCorta;
      EXIT WHEN curDiferencias%NOTFOUND;
      IF (LENGTH(vFila_1) >= 32767 - 150) THEN
        i := i + 1;
        IF (i = 1) THEN
          vFila_41 := vFila_41 || vFila_1;
        END IF;
        vFila_41 := vFila_41 || 
					'<tr><td colspan=2 width="300">' || vProducto||'-'||vDescProd || '</td><td class="caja" width="50">'|| vUnidadPresentacion ||'</td><td>'||vLaboratorio || '</td></tr>'
					||CHR(10);
      ELSE
        vFila_1  := vFila_1 || 
					'<tr><td colspan=2 width="300">' || vProducto||'-'||vDescProd || '</td><td class="caja" width="50">'|| vUnidadPresentacion ||'</td><td>'||vLaboratorio || '</td></tr>'
					||CHR(10);

      END IF;

	    --ERIOS 10.06.2015 Se imprime bultos		
		v_vBultos := GET_LISTA_BULTOS_IMP(cGrupoCia_in,cCodLocal_in,cNroRecepcion,vProducto);
		vFila_1 := vFila_1 || '<tr><td width="60">En G/R: '||v_nCant2||' '||v_vUnidadCorta||'</td><td colspan=3>'|| v_vBultos ||'</td></tr>'||CHR(10);
	  
    END LOOP;

    vMsg_out := C_INICIO_MSG || vFila_1 || vFila_41 ||
              C_FIN_MSG;

    RETURN vMsg_out;

  END;


  /*************************************************************************************************************/
  FUNCTION RECEP_F_CUR_LISTA_DIFERENCIAS(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR)
  RETURN FarmaCursor
  IS
    curDiferencias FarmaCursor;
  BEGIN
    PTOVENTA_RECEP_CIEGA_JCG.g_cCodGrupoCia := cGrupoCia_in;
    PTOVENTA_RECEP_CIEGA_JCG.g_cCodLocal := cCodLocal_in;
    PTOVENTA_RECEP_CIEGA_JCG.g_cNroRecep := cNroRecepcion;
	PTOVENTA_RECEP_CIEGA_JCG.g_cIndLocalM := FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cGrupoCia_in, cCodLocal_in);
	
    OPEN curDiferencias FOR
	--ERIOS 10.06.2015 Mismo query de RECEP_F_CUR_LISTA_PROD
	SELECT
		COD_PRODUCTO ,
		SUBSTR(DESCRIP_PROD ,1,30), 
		SUBSTR(UNIDAD,1,10),
		SUBSTR(LAB,1,8),
 		CANT2,
		SUBSTR( (SELECT TRIM(UNI.DESC_CORTA_UNID_MEDIDA)
				FROM PTOVENTA.LGT_PROD PROD JOIN PTOVENTA.LGT_UNID_MEDIDA UNI ON (PROD.COD_UNID_PRESENT = UNI.COD_UNID_MEDIDA)
				WHERE PROD.COD_GRUPO_CIA = cGrupoCia_in
				AND PROD.COD_PROD = PRODUCTO) ,1,3) UNIDAD_CORTA
    FROM (
		select *
		from PTOVENTA.V_RECEP_LISTA_PROD
	)
	--where rownum < 6 --PARA PRUEBAS
	;

   RETURN curDiferencias;
  END;

  /*******************************************************************************************************************/
   FUNCTION RECEP_F_BOOL_VERIFICA_ROL_USU(cCodGrupoCia_in  IN CHAR,
                             cCodLocal_in     IN CHAR,
                             vSecUsu_in       IN CHAR,
                             cCodRol_in       IN CHAR)

    RETURN CHAR
    IS
      v_vresultado  CHAR(1);
      v_nContAdmLocal   NUMBER:=0;
      v_nContAudi NUMBER:=0;

   BEGIN

     BEGIN
      SELECT COUNT(*) INTO v_nContAdmLocal
      FROM  PBL_ROL_USU X
      WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in
      AND X.SEC_USU_LOCAL=vSecUsu_in
      AND X.COD_ROL = '011';
     EXCEPTION WHEN OTHERS THEN
      v_nContAdmLocal:=0;
    END;

    BEGIN
      SELECT COUNT(*) INTO v_nContAudi
      FROM  PBL_ROL_USU X
      WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in
      AND X.SEC_USU_LOCAL=vSecUsu_in
      AND X.COD_ROL = '012';
     EXCEPTION WHEN OTHERS THEN
      v_nContAdmLocal:=0;
    END;
      IF (v_nContAdmLocal = 1 AND v_nContAudi = 1) THEN
         v_vresultado := 'C';
      ELSIF (v_nContAdmLocal = 1 AND v_nContAudi = 0) THEN
         v_vresultado := 'C';
      ELSIF (v_nContAdmLocal = 0 AND v_nContAudi = 1) THEN
         v_vresultado := 'V';
      ELSE
         v_vresultado := 'N';
      END IF;

    RETURN v_vresultado;

   END;

   /*******************************************************************************************************************/
   FUNCTION RECEP_F_CUR_DATOS_PRODUCTO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cCodProd_in       IN CHAR) RETURN FarmaCursor
   IS
     datosProducto FarmaCursor;
   BEGIN
        OPEN datosProducto FOR
        SELECT A.DESC_PROD || '�' || A.DESC_UNID_PRESENT || '�' || B.VAL_PREC_VTA || '�' || B.VAL_FRAC_LOCAL
        FROM LGT_PROD A, LGT_PROD_LOCAL B
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_PROD        = cCodProd_in
        AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
        AND A.COD_PROD      = B.COD_PROD
        AND B.COD_LOCAL     = cCodLocal_in;

        RETURN datosProducto;
   END;
   /*******************************************************************************************************************/

   FUNCTION RECEP_F_CHAR_VERIFICA_STOCK(cCodGrupoCia_in  IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        cNroRecepcion    IN CHAR,
                                        cCodProd_in      IN CHAR,
                                        nCantidad_in     IN CHAR
                                       ) RETURN CHAR
   IS
      v_nCantTotalAfectada NUMBER;
      v_nCantTotalTrans NUMBER:=0;
      v_vResultado CHAR(1);
   BEGIN
        BEGIN
          SELECT SUM(A.CANT_RECEPCIONADA) INTO v_nCantTotalAfectada
          FROM LGT_NOTA_ES_DET A
          WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
          AND A.COD_LOCAL       = cCodLocal_in
          AND A.COD_PROD        = cCodProd_in
          AND A.NUM_NOTA_ES || A.NUM_ENTREGA IN (SELECT B.NUM_NOTA_ES || B.NUM_ENTREGA
                                                FROM LGT_RECEP_ENTREGA B
                                                WHERE B.COD_GRUPO_CIA  = cCodGrupoCia_in
                                                AND B.COD_LOCAL        = cCodLocal_in
                                                AND B.NRO_RECEP        = cNroRecepcion
                                                )
          GROUP BY A.COD_PROD;
          dbms_output.put_line('v_nCantTotalAfectada: ' ||v_nCantTotalAfectada);
          SELECT SUM(CANT_MOV) INTO v_nCantTotalTrans
          FROM LGT_RECEP_PROD_TRANSF A
          WHERE COD_GRUPO_CIA   = cCodGrupoCia_in
          AND   COD_LOCAL       = cCodLocal_in
          AND   COD_PROD        = cCodProd_in
          AND   NRO_RECEP       = cNroRecepcion
          GROUP BY COD_PROD;
          dbms_output.put_line('v_nCantTotalTrans: ' ||v_nCantTotalTrans);
        EXCEPTION WHEN OTHERS THEN
          v_nCantTotalTrans:=0;
        END;
        IF (v_nCantTotalAfectada >= (v_nCantTotalTrans +  nCantidad_in) )THEN
           v_vResultado := 'S';
        ELSE
           v_vResultado := 'N';
        END IF;

        RETURN v_vResultado;
   END;
   /*************************************************************************************************/
   FUNCTION RECEP_F_CHAR_INDVERFCONTEO(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR) RETURN CHAR
   IS
     v_cIndSegConteo LGT_RECEP_MERCADERIA.IND_SEG_CONTEO%TYPE;
   BEGIN
       BEGIN
       SELECT A.IND_SEG_CONTEO INTO v_cIndSegConteo
       FROM LGT_RECEP_MERCADERIA A
       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
       AND A.COD_LOCAL   = cCodLocal_in
       AND A.NRO_RECEP  = cNroRecepcion;
       EXCEPTION WHEN OTHERS THEN
       v_cIndSegConteo:='N';
       END;
       RETURN v_cIndSegConteo;
   END;
   /*********************************************************************************************************/
   PROCEDURE RECEP_P_ACT_CANT_RECEPCIONADA( cGrupoCia_in     IN CHAR,
                                         cCodLocal_in        IN CHAR,
                                         cNroRecepcion_in    IN CHAR,
                                         cCodProd_in         IN CHAR,
                                         nCantMov_in         IN INTEGER,
                                         cUsuario_in         IN CHAR,
                                         cFechaVcto_in       IN CHAR DEFAULT NULL,
                                         cLote_in            IN CHAR DEFAULT NULL,
                                         cNumNotaEs_in       IN CHAR DEFAULT NULL)
   AS
     CURSOR curNota_Es_det IS
     SELECT COD_GRUPO_CIA, COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES,CANT_RECEPCIONADA,NUM_ENTREGA
     FROM LGT_NOTA_ES_DET
     WHERE COD_GRUPO_CIA = cGrupoCia_in
     AND   COD_LOCAL     = cCodLocal_in
     AND   NUM_ENTREGA || NUM_NOTA_ES IN (
                                               SELECT NUM_ENTREGA || NUM_NOTA_ES
                                               FROM LGT_RECEP_ENTREGA
                                               WHERE COD_GRUPO_CIA  = cGrupoCia_in
                                               AND   COD_LOCAL      = cCodLocal_in
                                               AND   NRO_RECEP      = cNroRecepcion_in
                                         )
     AND   COD_PROD  = cCodProd_in
     AND   CANT_RECEPCIONADA > 0
     ORDER BY CANT_RECEPCIONADA ASC;

     v_nCantAux NUMBER;
     v_vSecTrans LGT_RECEP_PROD_TRANSF.SEC_TRANSF%TYPE;
     v_Lote varchar2(10) := cLote_in;
     v_FechaVencimiento CHAR(10) := cFechaVcto_in;
  BEGIN
    IF (FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cGrupoCia_in, cCodLocal_in) = 'S') THEN
      IF (TRIM(cLote_in) <> '' OR cLote_in IS NULL) THEN
         IF (TO_NUMBER(TRIM(nCantMov_in)) = 0) THEN
            v_Lote := '';
         ELSE 
            v_Lote := PTOVENTA_TOMA_INV.GET_SIN_LOTE;
         END IF;
         v_FechaVencimiento := TO_CHAR(SYSDATE,'DD/MM/YYYY');
       END IF;
    END IF;
    /*v_nCantAux := nCantMov_in;
    FOR v_curProductos IN curNota_Es_det LOOP
       IF (v_nCantAux > 0) THEN
           IF  (v_nCantAux < v_curProductos.CANT_RECEPCIONADA) THEN
                UPDATE LGT_NOTA_ES_DET A
                SET A.CANT_RECEPCIONADA = A.CANT_RECEPCIONADA -v_nCantAux,
                A.FEC_MOD_NOTA_ES_DET   = SYSDATE,
                A.USU_MOD_NOTA_ES_DET    = cUsuario_in
                WHERE A.COD_GRUPO_CIA =  v_curProductos.COD_GRUPO_CIA
                AND A.COD_LOCAL       =  v_curProductos.COD_LOCAL
                AND A.NUM_NOTA_ES     =   v_curProductos.NUM_NOTA_ES
                AND A.SEC_DET_NOTA_ES =  v_curProductos.SEC_DET_NOTA_ES;

               RECEP_P_INS_PROD_TRANSFERENCIA(v_curProductos.COD_GRUPO_CIA,v_curProductos.COD_LOCAL,v_curProductos.NUM_NOTA_ES,
               v_curProductos.NUM_ENTREGA,cNroRecepcion_in,cCodProd_in,cLote_in,v_nCantAux,
               cFechaVcto_in,cUsuario_in);

               v_nCantAux := 0;
           ELSIF (v_nCantAux > v_curProductos.CANT_RECEPCIONADA) THEN
                UPDATE LGT_NOTA_ES_DET A
                SET A.CANT_RECEPCIONADA = 0,
                A.FEC_MOD_NOTA_ES_DET   = SYSDATE,
                A.USU_MOD_NOTA_ES_DET    = cUsuario_in
                WHERE A.COD_GRUPO_CIA =  v_curProductos.COD_GRUPO_CIA
                AND A.COD_LOCAL       =  v_curProductos.COD_LOCAL
                AND A.NUM_NOTA_ES     =   v_curProductos.NUM_NOTA_ES
                AND A.SEC_DET_NOTA_ES =  v_curProductos.SEC_DET_NOTA_ES;

                RECEP_P_INS_PROD_TRANSFERENCIA(v_curProductos.COD_GRUPO_CIA,v_curProductos.COD_LOCAL,v_curProductos.NUM_NOTA_ES,
                v_curProductos.NUM_ENTREGA,cNroRecepcion_in,cCodProd_in,cLote_in,v_curProductos.CANT_RECEPCIONADA,
                cFechaVcto_in,cUsuario_in);

                v_nCantAux := v_nCantAux -  v_curProductos.CANT_RECEPCIONADA;
           ELSIF (v_nCantAux = v_curProductos.CANT_RECEPCIONADA) THEN
                 UPDATE LGT_NOTA_ES_DET A
                SET A.CANT_RECEPCIONADA = 0,
                A.FEC_MOD_NOTA_ES_DET   = SYSDATE,
                A.USU_MOD_NOTA_ES_DET    = cUsuario_in
                WHERE A.COD_GRUPO_CIA =  v_curProductos.COD_GRUPO_CIA
                AND A.COD_LOCAL       =  v_curProductos.COD_LOCAL
                AND A.NUM_NOTA_ES     =   v_curProductos.NUM_NOTA_ES
                AND A.SEC_DET_NOTA_ES =  v_curProductos.SEC_DET_NOTA_ES;

                RECEP_P_INS_PROD_TRANSFERENCIA(v_curProductos.COD_GRUPO_CIA,v_curProductos.COD_LOCAL,v_curProductos.NUM_NOTA_ES,
                v_curProductos.NUM_ENTREGA,cNroRecepcion_in,cCodProd_in,cLote_in,v_nCantAux,
                cFechaVcto_in,cUsuario_in);

                v_nCantAux := 0;
           END IF;


       END IF;

    END LOOP;*/

    RECEP_P_INS_PROD_TRANSFERENCIA(cGrupoCia_in,cCodLocal_in,cNumNotaEs_in,
                null,cNroRecepcion_in,cCodProd_in,v_Lote,nCantMov_in,
                v_FechaVencimiento,cUsuario_in);
    END;

   /* ***************************************************************************** */
  PROCEDURE RECEP_P_ACT_IND_SEG_PARAM(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cIdUsu_in        IN CHAR,
                                         cIndicador_in    IN CHAR)
  AS
  BEGIN
       UPDATE LGT_RECEP_MERCADERIA A
       SET A.IND_SEG_CONTEO = cIndicador_in,
           A.FEC_MOD_RECEP  = SYSDATE,
           A.USU_MOD_RECEP  = cIdUsu_in
       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
       AND   A.COD_LOCAL     = cCodLocal_in
       AND   A.NRO_RECEP     = cNroRecepcion_in;

  END;

  /* ***************************************************************************/
  FUNCTION  RECEP_F_STR_TXTFRACC_MOTIVO
  RETURN FarmaCursor
  IS
  	curDest FarmaCursor;
  BEGIN
  	OPEN curDest FOR
  	SELECT LLAVE_TAB_GRAL || '�' ||
  		DESC_CORTA
  	FROM PBL_TAB_GRAL
  	WHERE COD_APL = 'PTOVENTA'
  	      AND COD_TAB_GRAL = 'MOTIVOS DE AJUSTE'
              AND EST_TAB_GRAL = 'A';
  	RETURN curDest;
   /*IF cCodTipo_in <> g_cTipoOrigenLocal THEN

    SELECT DESC_CORTA   INTO vDescMotivo
    FROM   PBL_TAB_GRAL T
    WHERE  T.COD_APL = 'PTOVENTA'
  --  AND    T.LLAVE_TAB_GRAL = cCodMotivo
    AND    T.COD_TAB_GRAL = 'MOTIVOS DE AJUSTE';

    SELECT LLAVE_TAB_GRAL INTO vIndTextFraccion
    FROM   PBL_TAB_GRAL F
    WHERE  COD_APL = 'PTOVENTA'
    AND    DESC_CORTA  = vDescMotivo
    AND    DESC_LARGA  = vDescMotivo;

   ELSE
    vIndTextFraccion := 'N';
   END IF;

    RETURN vIndTextFraccion ;

    EXCEPTION
    WHEN OTHERS THEN
       dbms_output.put_line('OCURRIO UN ERROR');*/
  END;

  /****************************************************************************************/
  FUNCTION RECEP_F_CHAR_EXISTE_PRODUCTO(cCodGrupoCia_in IN CHAR,
                                        cCodProd IN CHAR)
                                        RETURN CHAR
  IS
    v_nCantidad NUMBER;
    v_cResultado CHAR:='N';
  BEGIN
       SELECT COUNT(*) INTO v_nCantidad
       FROM LGT_PROD
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_PROD = cCodProd;

       IF (v_nCantidad = 1  ) THEN
          v_cResultado := 'S';
       ELSE
          v_cResultado := 'N';
       END IF;

       RETURN v_cResultado;
  END;
  /**************************************************************************************************/
  FUNCTION RECEP_F_CUR_LISTA_MATRIZ(cCodGrupoCia_in IN CHAR)RETURN FarmaCursor
  IS
    curGral FarmaCursor;
  BEGIN
     OPEN curGral FOR
    SELECT NVL(COD_LOCAL,' ') || '�' ||
               NVL(DESC_CORTA_LOCAL,' ')
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND TIP_LOCAL = 'A'
          AND EST_LOCAL = 'A';
    RETURN curGral;
  END;
  /**************************************************************************************************/
  PROCEDURE RECEP_P_COMPLETA_CON_CEROS(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cSecConteo_in    IN CHAR,
                                         cCodProducto_in  IN CHAR,
                                         cUsuario_in      IN CHAR)
  AS
    v_vSecConteo LGT_PROD_CONTEO.SEC_CONTEO%TYPE;
  BEGIN

    IF (cSecConteo_in = 0) THEN

        BEGIN
          SELECT SEC_CONTEO  INTO v_vSecConteo
          FROM LGT_PROD_CONTEO
          WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL=cCodLocal_in
          AND NRO_RECEP=cNroRecepcion_in
          AND COD_PROD=cCodProducto_in;
        EXCEPTION WHEN NO_DATA_FOUND THEN
          v_vSecConteo:=0;
        END;
        IF (v_vSecConteo=0) THEN
              INSERT INTO LGT_PROD_CONTEO(COD_GRUPO_CIA,COD_LOCAL,NRO_RECEP,SEC_CONTEO,COD_PROD,
           USU_SEG_CONTEO,FEC_SEG_CONTEO,CANT_SEG_CONTEO,IP_SEG_CONTEO)
           VALUES(cGrupoCia_in,
           cCodLocal_in,
           cNroRecepcion_in,
           (SELECT NVL(MAX(A.SEC_CONTEO),0)+1
            FROM LGT_PROD_CONTEO A
            WHERE A.COD_GRUPO_CIA=cGrupoCia_in AND A.COD_LOCAL=cCodLocal_in AND A.NRO_RECEP=cNroRecepcion_in),
            cCodProducto_in,
           cUsuario_in,
            SYSDATE,
            0,
            (SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') FROM DUAL)
           );
        ELSIF (v_vSecConteo>0) THEN
            UPDATE LGT_PROD_CONTEO A
             SET A.CANT_SEG_CONTEO = 0,
                 A.USU_MOD_CONTEO  = cUsuario_in,
                 A.USU_SEG_CONTEO  = cUsuario_in,
                 A.FEC_SEG_CONTEO  = SYSDATE,
                 A.FEC_MOD_CONTEO  = SYSDATE,
                 A.IP_SEG_CONTEO   = (SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS')
                                        FROM DUAL)
           WHERE A.COD_GRUPO_CIA = cGrupoCia_in
             AND A.COD_LOCAL = cCodLocal_in
             AND A.NRO_RECEP = cNroRecepcion_in
             AND A.SEC_CONTEO = v_vSecConteo
             AND A.COD_PROD = cCodProducto_in
             AND A.CANT_SEG_CONTEO IS NULL;
        END IF;

    ELSE

     UPDATE LGT_PROD_CONTEO A
       SET A.CANT_SEG_CONTEO = 0,
           A.USU_MOD_CONTEO  = cUsuario_in,
           A.USU_SEG_CONTEO  = cUsuario_in,
           A.FEC_SEG_CONTEO  = SYSDATE,
           A.FEC_MOD_CONTEO  = SYSDATE,
           A.IP_SEG_CONTEO   = (SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS')
                                  FROM DUAL)
     WHERE A.COD_GRUPO_CIA = cGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.NRO_RECEP = cNroRecepcion_in
       AND A.SEC_CONTEO = cSecConteo_in
       AND A.COD_PROD = cCodProducto_in
       AND A.CANT_SEG_CONTEO IS NULL;
   END IF;
  END;
  /**************************************************************************************************/
  PROCEDURE RECEP_P_INS_PROD_TRANSFERENCIA(cGrupoCia_in     IN CHAR,
                                           cCodLocal_in     IN CHAR,
                                           cNumNotaEs_in       IN CHAR,
                                           cNumEntrega     IN CHAR,
                                           cNroRecepcion_in  IN CHAR,
                                           cCodProducto_in  IN CHAR,
                                           cLote_in      IN CHAR,
                                           cCantidad_in     IN CHAR,
                                           cFechaVcto_in     IN CHAR,
                                           cUsuario_in      IN CHAR)
  AS
    v_vSecTrans LGT_RECEP_PROD_TRANSF.SEC_TRANSF%TYPE:=0;
    v_Lote varchar2(10) := cLote_in;
    v_FechaVencimiento CHAR(10) := cFechaVcto_in;
  BEGIN
    IF (FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cGrupoCia_in, cCodLocal_in) = 'S') THEN
      IF (TRIM(cLote_in) <> '' OR cLote_in IS NULL) THEN
         v_Lote := PTOVENTA_TOMA_INV.GET_SIN_LOTE;
         v_FechaVencimiento := TO_CHAR(SYSDATE,'DD/MM/YYYY');
       END IF;
    END IF;
    
       dbms_output.put_line('inserta');
       IF ( (v_FechaVencimiento IS NOT NULL) AND (v_Lote IS NOT NULL) AND (cNumNotaEs_in IS NOT NULL)) THEN
              dbms_output.put_line('ENTRO A INSERTAR');
          SELECT NVL(MAX(T.SEC_TRANSF),0) + 1 INTO v_vSecTrans
          FROM LGT_RECEP_PROD_TRANSF T
          WHERE T.COD_GRUPO_CIA = cGrupoCia_in
          AND T.COD_LOCAL       = cCodLocal_in
          AND T.NUM_NOTA_ES     = cNumNotaEs_in;

          INSERT INTO LGT_RECEP_PROD_TRANSF(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_TRANSF,NUM_ENTREGA,
          NRO_RECEP,COD_PROD,NUM_LOTE_PROD,CANT_MOV,FEC_VCTO,USU_CREA_RECEP_TRANSF_PROD,FEC_CREA_RECEP_TRANSF_PROD,EST_TRANSF)
          VALUES(cGrupoCia_in,cCodLocal_in,cNumNotaEs_in,
          v_vSecTrans,cNumEntrega,cNroRecepcion_in,cCodProducto_in,v_Lote,cCantidad_in,
          TO_DATE(v_FechaVencimiento,'DD/MM/YYYY'),cUsuario_in,SYSDATE,'A');
       END IF;
  END;

  /******************************************************************************** */

 FUNCTION INV_F_GET_IND_TIPO_RECEP_ALM(cCodGrupoCia_in IN CHAR,
      						  			                   cCod_Local_in   IN CHAR)
                                                RETURN CHAR
 IS
   v_vIndicador  PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE;
 BEGIN
    BEGIN
      SELECT A.LLAVE_TAB_GRAL INTO v_vIndicador
      FROM PBL_TAB_GRAL A
      WHERE A.ID_TAB_GRAL  = '326'
      AND   A.COD_APL      = 'PTO_VENTA'
      AND   A.COD_TAB_GRAL = 'IND_RECEP_CIEGA';
    EXCEPTION WHEN OTHERS THEN
      v_vIndicador := 'A';
    END;

    RETURN v_vIndicador;
 END;

 /******************************************************************************** */
 FUNCTION RECEP_F_LISTA_PROD(cCodGrupoCia_in IN CHAR,
      						  			   cCod_Local_in   IN CHAR,
                             cNumRecepcion_in IN CHAR
                                               )
 RETURN FarmaCursor
 IS
  curListaProd FarmaCursor;
  BEGIN
  -- 07.01.2015 ERIOS Garantizado por local
   OPEN curListaProd for

SELECT distinct(PROD.COD_PROD)  || '�' ||
			     PROD.DESC_PROD  || '�' ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)  || '�' ||
           LAB.NOM_LAB  || '�' ||
			     (PROD_LOCAL.STK_FISICO)  || '�' ||
           TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000')  || '�' || --JCHAVEZ 29102009  precio redondeado
           NVL(PROD_LOCAL.IND_ZAN,' ')  || '�' ||
           NVL(PROD_LOCAL.IND_PROD_CONG,'')  || '�' ||
			     NVL(PROD_LOCAL.VAL_FRAC_LOCAL,'')  || '�' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000')  || '�' ||
           TO_CHAR(IGV.PORC_IGV,'990.00')  || '�' ||
			     PROD.IND_PROD_FARMA  || '�' ||
           DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S')  || '�' ||
           NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || '�' ||
           PROD.IND_PROD_REFRIG           || '�' ||
           PROD.IND_TIPO_PROD           || '�' ||
           DECODE(NVL(Z.COD,'N'),'N','N','S')  || '�' ||
           PROD.DESC_PROD || DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || '�' ||
           NVL(trim(V2.COD_ENCARTE),' ') || '�' ||
           '1'/*IND_ORIGEN_PROD*/
           || '�'
		FROM   LGT_PROD PROD,
			     LGT_PROD_LOCAL PROD_LOCAL,
			     LGT_LAB LAB,
			     PBL_IGV IGV,
           LGT_PROD_VIRTUAL PR_VRT,
           (SELECT DISTINCT(V1.COD_PROD) COD
            FROM  (SELECT COD_PAQUETE,COD_PROD
                   FROM   VTA_PROD_PAQUETE
                   WHERE  COD_GRUPO_CIA =  cCodGrupoCia_in
                   ) V1,
                   VTA_PROMOCION    P

                   WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
            AND  P.IND_DELIVERY='N'
            AND  P.COD_GRUPO_CIA =  cCodGrupoCia_in
            AND    P.ESTADO  = 'A'
            AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                    OR
                     P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
           ) Z,
           (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                  PROD_ENCARTE.COD_ENCARTE,
                  PROD_ENCARTE.COD_PROD
           FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                  VTA_ENCARTE ENCARTE
           WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
           AND    ENCARTE.ESTADO = 'A'
           AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
           AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
           )   V2
		WHERE  PROD_LOCAL.COD_GRUPO_CIA =  cCodGrupoCia_in
		AND	   PROD_LOCAL.COD_LOCAL =  cCod_Local_in
    AND    PROD_LOCAL.COD_PROD IN
                                  (
                                    select ld.cod_prod
                                    from   lgt_recep_entrega re,
                                           lgt_nota_es_det ld
                                    where  re.cod_grupo_cia =  cCodGrupoCia_in
                                    and    re.cod_local =  cCod_Local_in
                                    and    re.nro_recep =  cNumRecepcion_in
                                    and    re.cod_grupo_cia = ld.cod_grupo_cia
                                    and    re.cod_local = ld.cod_local
                                    and    re.num_nota_es = ld.num_nota_es
                                    AND    re.sec_guia_rem = ld.sec_guia_rem         --JMIRANDA 15.02.09
                                    AND    re.num_entrega = ld.num_entrega           --JMIRANDA 15.02.09
                                    and    ld.cant_recepcionada > 0
                                  )
		AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
		AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
		AND	   PROD.COD_LAB = LAB.COD_LAB
		AND	   PROD.COD_IGV = IGV.COD_IGV
		AND	   PROD.EST_PROD = 'A'
    AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
    AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
    AND    PROD.COD_PROD = z.cod (+)
    AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+)
    AND   PROD.COD_PROD=V2.COD_PROD(+);
   return curListaProd;



 END;

  /*********************************************************************************************************/
  -- verifica si puede hacer transferencias calcula el tiempo limite
  FUNCTION RECEP_F_GET_LIM_TRANSF(cCodGrupoCia_in IN CHAR,
      						  			   cCod_Local_in   IN CHAR,
                             cNroRecepcion IN CHAR)
  RETURN CHAR
   IS
    vValor NUMBER := 0;
    vIndTransf CHAR;
    vHoras NUMBER(4);
  BEGIN
   BEGIN
    SELECT to_number(trim(G.LLAVE_TAB_GRAL),'9999') INTO vHoras
      FROM PBL_TAB_GRAL G
     WHERE G.ID_TAB_GRAL = '333';

     --FILTRA LOS REPORTES QUE AUN NO HAN SIDO ENVIADOS Y SE ENCUENTRAN DENTRO DE LA FECHA DE ENVIO
     SELECT COUNT(1) INTO vValor
       FROM LGT_RECEP_MERCADERIA RM
      WHERE RM.ESTADO = 'T'
        AND RM.COD_GRUPO_CIA = cCodGrupoCia_in
        AND RM.COD_LOCAL = cCod_Local_in
        AND RM.NRO_RECEP = cNroRecepcion
        AND RM.IND_ENV_REPORTE = 'N'
--        AND RM.FEC_CREA_RECEP BETWEEN SYSDATE-(vHoras/24) AND SYSDATE +(vHoras)/24;
        AND SYSDATE BETWEEN RM.FEC_CREA_RECEP AND RM.FEC_CREA_RECEP +(vHoras)/24;

     IF vValor = 1 THEN
        vIndTransf := 'S';
     ELSIF vValor = 0 THEN
        vIndTransf := 'N';
     END IF;

     EXCEPTION
     WHEN NO_DATA_FOUND THEN
       vIndTransf := 'N';
     WHEN OTHERS THEN
       vIndTransf := 'N';
    END;
     RETURN vIndTransf;
  END;

---------------------------------------------------------------------------------------
--JMIRANDA 11.02.2010 VERIFICA EL LIMITE PARA LA TRANSFERENCIAS POR CANJE
  FUNCTION RECEP_F_CHAR_LIM_FECHA_CANJE (cCodGrupoCia_in IN CHAR,
      						  			   cCod_Local_in   IN CHAR,
                             cCodProd_in IN CHAR,
                             cFechaVenc_in IN DATE)
  RETURN CHAR
   IS
   n_Meses AUX_POLITICA_CANJE_LAB.MESES%TYPE;
   n_Dif NUMBER(6);
   v_Out CHAR;
  BEGIN
   BEGIN

    SELECT NVL(CJ.MESES,-1) INTO  n_Meses
      FROM LGT_PROD P, LGT_LAB L, AUX_POLITICA_CANJE_LAB CJ
     WHERE P.COD_GRUPO_CIA = '001'
       AND P.COD_LAB = L.COD_LAB
       AND L.COD_LAB = CJ.COD_LAB
       AND P.COD_PROD = cCodProd_in;

    IF (n_Meses = -1) THEN
    --no existe fecha toma fecha por defecto
       SELECT LLAVE_TAB_GRAL INTO n_Meses
         FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL = 339;

       SELECT ( ADD_MONTHS(TRUNC(cFechaVenc_in,'MM'),-(1+n_Meses)) - trunc(SYSDATE,'MM') ) INTO n_Dif FROM DUAL;
       IF n_Dif > 0 THEN
         v_Out := 'N'; --S
         DBMS_OUTPUT.PUT_LINE('caso -1, dif>0');
       ELSE
         v_Out := 'S';  --N
         DBMS_OUTPUT.PUT_LINE('caso -1, dif<0');
       END IF;
       n_Meses := -1;
    END IF ;

    IF (n_Meses >= 0) THEN
    --existe fecha calcular
       SELECT ( ADD_MONTHS(TRUNC(cFechaVenc_in,'MM'),-(1+n_Meses)) - trunc(SYSDATE,'MM') ) INTO n_Dif FROM DUAL;
       IF n_Dif > 0 THEN
         v_Out := 'N'; --S
         DBMS_OUTPUT.PUT_LINE('caso >0, dif>0');
       ELSE
         v_Out := 'S'; --N
         DBMS_OUTPUT.PUT_LINE('caso >0, dif<0');
       END IF;
    END IF;
   EXCEPTION
    WHEN NO_DATA_FOUND THEN
     v_Out := 'N';
       SELECT LLAVE_TAB_GRAL INTO n_Meses
         FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL = 339;

       SELECT ( ADD_MONTHS(TRUNC(cFechaVenc_in,'MM'),-(1+n_Meses)) - trunc(SYSDATE,'MM') ) INTO n_Dif FROM DUAL;
       IF n_Dif > 0 THEN
         v_Out := 'N'; --S
         DBMS_OUTPUT.PUT_LINE('caso -1, dif>0');
       ELSE
         v_Out := 'S'; --N
         DBMS_OUTPUT.PUT_LINE('caso -1, dif<0');
       END IF;
     DBMS_OUTPUT.PUT_LINE('NO DATA FOUND');

   END;

    RETURN v_Out;
  END;

---------------------------------------------------------------------------------------
--JMIRANDA 11.02.2010 VERIFICA EL LIMITE PARA LA TRANSFERENCIAS POR CANJE
  FUNCTION RECEP_F_CHAR_FECHA_CANJE_PROD(cCodGrupoCia_in IN CHAR,
      						  			   cCod_Local_in   IN CHAR,
                             cCodProd_in IN CHAR,
                             cFechaVenc_in IN DATE,
                             cLote_in IN VARCHAR2)
  RETURN CHAR
  IS
   vRpta CHAR := 'N';
   vMeses NUMBER(4) := 0;
   n_Dif NUMBER(6);
   v_Lote varchar2(10) := cLote_in;
   v_FechaVencimiento CHAR(10) := cFechaVenc_in;
  BEGIN
    IF (FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cCodGrupoCia_in, cCod_Local_in) = 'S') THEN
      IF (TRIM(cLote_in) <> '' OR cLote_in IS NULL) THEN
         v_Lote := PTOVENTA_TOMA_INV.GET_SIN_LOTE;
         v_FechaVencimiento := TO_CHAR(SYSDATE,'DD/MM/YYYY');
       END IF;
    END IF;
    
     BEGIN
       SELECT NVL(P.MESES,-1) INTO vMeses
         FROM AUX_POLITICA_CANJE_PROD P
        WHERE P.COD_PROD = cCodProd_in
          AND UPPER(TRIM(P.LOTE)) = v_Lote;
     EXCEPTION
      WHEN no_data_found THEN
       vMeses := -1;
     END;

     IF vMeses = -1 THEN
       vRpta := PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_LIM_FECHA_CANJE (cCodGrupoCia_in,
      						  			   cCod_Local_in,
                             cCodProd_in,
                             v_FechaVencimiento);
     END IF;
     IF vMeses >= 0 THEN
      --VALIDA SI TIENE MESES
       SELECT ( ADD_MONTHS(TRUNC(to_date(v_FechaVencimiento,'dd/mm/yyyy'),'MM'),-(1+vMeses)) - trunc(SYSDATE,'MM') ) INTO n_Dif FROM DUAL;
       IF n_Dif > 0 THEN
         vRpta := 'N';
         DBMS_OUTPUT.PUT_LINE('caso >0, dif>0');
       ELSE
         vRpta := 'S';
         DBMS_OUTPUT.PUT_LINE('caso >0, dif<0');
       END IF;
     END IF;
    RETURN  vRpta;
  END;
---------------------------------------------------------------------------------------
    FUNCTION RECEP_F_NO_TIENE_FECHA_SAP (cCod_GrupoCia_in IN CHAR,
                                     cCod_Local_in IN CHAR,
                                     cNro_Recepcion IN CHAR,
                                     cCod_Prod_in IN CHAR)

                                     RETURN CHAR
    IS
     vRpta CHAR(1) := 'N';
     vCant NUMBER(5) := 0;
     vNumEntrega lgt_nota_es_det.num_entrega%TYPE;
     vTotal NUMBER(10) := 0;

     CURSOR curEntregas IS
     SELECT e.num_entrega
       FROM lgt_recep_entrega e
      WHERE e.cod_grupo_cia = cCod_GrupoCia_in
      AND e.cod_local = cCod_Local_in
      AND e.nro_recep = cNro_Recepcion;

    BEGIN

    FOR v_curEntregas IN curEntregas LOOP
      BEGIN
        SELECT COUNT(1)
               INTO vCant
          FROM lgt_nota_es_det d
         WHERE d.cod_grupo_cia = cCod_GrupoCia_in
           AND d.cod_local = cCod_Local_in
           AND d.num_entrega = v_curEntregas.Num_Entrega
           AND d.cod_prod = cCod_Prod_in
           AND d.fec_vcto_prod IS NULL;
      EXCEPTION
      WHEN no_data_found THEN
       vRpta := 'N';
      END;
      vTotal := vTotal + vCant;
    END LOOP;
      IF (vTotal > 0) THEN
       vRpta := 'S';
      END IF;
      RETURN vRpta;
    END;


  /***************************************************************************************************************/
  --JMIRANDA 25.07.2011
  PROCEDURE RECEP_AFECTA_ENT_PENDIENTES(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cUsu_in IN CHAR) AS

   --OBTENGO LAS ENTREGAS QUE NO TIENEN PRODUCTOS
    CURSOR curGuiasPendientes IS
      SELECT B.NUM_ENTREGA, SUM(B.CANT_RECEPCIONADA)
        FROM LGT_NOTA_ES_DET B, LGT_RECEP_ENTREGA C, LGT_GUIA_REM D
       WHERE B.COD_GRUPO_CIA = cGrupoCia_in
         AND B.COD_LOCAL = cCodLocal_in
         AND C.NRO_RECEP = cNroRecepcion_in
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_LOCAL = C.COD_LOCAL
         AND B.NUM_NOTA_ES = C.NUM_NOTA_ES
         AND B.NUM_ENTREGA = C.NUM_ENTREGA
         AND B.SEC_GUIA_REM = C.SEC_GUIA_REM
         AND B.COD_GRUPO_CIA = D.COD_GRUPO_CIA
         AND B.COD_LOCAL = D.COD_LOCAL
         AND B.NUM_NOTA_ES = D.NUM_NOTA_ES
         AND B.SEC_GUIA_REM = D.SEC_GUIA_REM
         AND B.NUM_ENTREGA = D.NUM_ENTREGA
         AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
         AND C.COD_LOCAL = D.COD_LOCAL
         AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
         AND C.NUM_GUIA_REM = D.NUM_GUIA_REM
         AND C.NUM_ENTREGA = D.NUM_ENTREGA
         AND C.SEC_GUIA_REM = D.SEC_GUIA_REM
       GROUP BY B.NUM_ENTREGA
      HAVING SUM(B.CANT_RECEPCIONADA) = 0;

  BEGIN

  FOR C_GUIA_PEND IN curGuiasPendientes
    LOOP
    --DBMS_OUTPUT.PUT_LINE('ENT: '||C_GUIA_PEND.NUM_ENTREGA);
    RECEP_P_AFECTA_PROD_LIBRES(cGrupoCia_in,cCodLocal_in,cNroRecepcion_in,C_GUIA_PEND.NUM_ENTREGA, cUsu_in);
    END LOOP;

  END;


  /***************************************************************************************************************/
  PROCEDURE RECEP_P_AFECTA_PROD_LIBRES(cGrupoCia_in     IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cNroRecepcion_in IN CHAR,
                                     cNumEntrega_in IN CHAR,
                                     cIdUsu_in        IN CHAR) AS

    CURSOR curProd IS
      SELECT A.COD_GRUPO_CIA,
             A.COD_LOCAL,
             A.SEC_DET_NOTA_ES,
             A.NUM_NOTA_ES,
             A.NUM_PAG_RECEP
        FROM LGT_NOTA_ES_DET A, LGT_RECEP_ENTREGA B/*, LGT_PROD_CONTEO C*/
       WHERE A.COD_GRUPO_CIA = cGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND B.NRO_RECEP = cNroRecepcion_in
         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.NUM_NOTA_ES = B.NUM_NOTA_ES
         AND A.SEC_GUIA_REM = B.SEC_GUIA_REM
         AND A.NUM_ENTREGA = B.NUM_ENTREGA
         AND A.IND_PROD_AFEC <> 'S'
         AND A.NUM_ENTREGA = cNumEntrega_in
         ;
    v_rCurProd curProd%ROWTYPE;
  BEGIN
   -- SETEA LOS VALORES DE LA GUIAS PENDIENTES CON 0
   UPDATE LGT_NOTA_ES_DET D
      SET D.CANT_CONTADA = 0,
          D.CANT_RECEPCIONADA = 0,
          D.USU_MOD_NOTA_ES_DET = 'SP_LIBERA',
          D.FEC_MOD_NOTA_ES_DET = SYSDATE
    WHERE D.COD_GRUPO_CIA = cGrupoCia_in
      AND D.COD_LOCAL = cCodLocal_in
      AND D.NUM_ENTREGA = cNumEntrega_in;


    FOR v_rCurProd IN curProd LOOP
      RECEP_P_ACT_REG_GUIA_RECEP_L(v_rCurProd.Cod_Grupo_Cia,
                                 v_rCurProd.Cod_Local,
                                 v_rCurProd.Num_Nota_Es,
                                 v_rCurProd.SEC_DET_NOTA_ES,
                                 v_rCurProd.Num_Pag_Recep,
                                 cIdUsu_in);
    END LOOP;

  END;

  /*********************************************************************************************************************/

  PROCEDURE RECEP_P_ACT_REG_GUIA_RECEP_L(cGrupoCia_in   IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNumNota_in    IN CHAR,
                                       cSecDetNota_in IN CHAR,
                                       cNumPag_in     IN CHAR,
                                       cIdUsu_in      IN CHAR) IS
    v_cEstDet   LGT_NOTA_ES_DET.EST_NOTA_ES_DET%TYPE;
    v_nSecGuia  LGT_NOTA_ES_DET.SEC_GUIA_REM%TYPE;
    v_cCodProd  LGT_NOTA_ES_DET.COD_PROD%TYPE;
    v_nCantMov  LGT_NOTA_ES_DET.CANT_MOV%TYPE;
    v_nCantMatr LGT_NOTA_ES_DET.CANT_ENVIADA_MATR%TYPE;
    v_nCantDif  LGT_NOTA_ES_DET.CANT_MOV%TYPE;
    v_nNumLoteProd LGT_NOTA_ES_DET.NUM_LOTE_PROD%TYPE;
    v_nFecVctoProd LGT_NOTA_ES_DET.FEC_VCTO_PROD%TYPE;
  BEGIN
    SELECT EST_NOTA_ES_DET,
           SEC_GUIA_REM,
           COD_PROD,
           CANT_RECEPCIONADA,
           CANT_ENVIADA_MATR,
           (CANT_ENVIADA_MATR - CANT_RECEPCIONADA),
           NUM_LOTE_PROD,
           FEC_VCTO_PROD
      INTO v_cEstDet,
           v_nSecGuia,
           v_cCodProd,
           v_nCantMov,
           v_nCantMatr,
           v_nCantDif,
           v_nNumLoteProd,
           v_nFecVctoProd
      FROM LGT_NOTA_ES_DET
     WHERE COD_GRUPO_CIA = cGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in
       AND SEC_DET_NOTA_ES = cSecDetNota_in
       AND IND_PROD_AFEC = 'N'
       FOR UPDATE;

    IF v_cEstDet = 'T' THEN
      --RECEP_P_OPERAC_ANEXAS_PROD comentado y reemplazado para pruebas ASOSA, 21.07.2010
      PTOVTA_RESPALDO_STK.RECEP_P_OPERAC_ANEXAS_PROD_LI(cGrupoCia_in, --ASOSA, 21.07.2010
                                 cCodLocal_in,
                                 cNumNota_in,
                                 v_nSecGuia,
                                 v_cCodProd,
                                 v_nCantMov,
                                 cIdUsu_in,
                                 v_nCantMatr,
                                 v_nCantDif,
                                 v_nNumLoteProd,
                                 TO_CHAR(v_nFecVctoProd,'DD/MM/YYYY'));
      UPDATE LGT_NOTA_ES_DET
         SET USU_MOD_NOTA_ES_DET = cIdUsu_in,
             FEC_MOD_NOTA_ES_DET = SYSDATE,
             IND_PROD_AFEC       = 'S',
             EST_NOTA_ES_DET     = 'A'
       WHERE COD_GRUPO_CIA = cGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND NUM_NOTA_ES = cNumNota_in
         AND SEC_DET_NOTA_ES = cSecDetNota_in
         AND IND_PROD_AFEC <> 'S';
    ELSIF v_cEstDet = 'P' THEN
      --RECEP_P_OPERAC_ANEXAS_PROD comentado y reemplazado para pruebas ASOSA, 21.07.2010
      PTOVTA_RESPALDO_STK.RECEP_P_OPERAC_ANEXAS_PROD_LI(cGrupoCia_in, --ASOSA, 21.07.2010
                                 cCodLocal_in,
                                 cNumNota_in,
                                 v_nSecGuia,
                                 v_cCodProd,
                                 v_nCantMov,
                                 cIdUsu_in,
                                 v_nCantMatr,
                                 v_nCantDif/*0*/,
                                 v_nNumLoteProd,
                                 TO_CHAR(v_nFecVctoProd,'DD/MM/YYYY'));
      UPDATE LGT_NOTA_ES_DET
         SET USU_MOD_NOTA_ES_DET = cIdUsu_in,
             FEC_MOD_NOTA_ES_DET = SYSDATE,
             CANT_MOV            = v_nCantMov,
             IND_PROD_AFEC       = 'S',
             EST_NOTA_ES_DET     = 'A'
       WHERE COD_GRUPO_CIA = cGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND NUM_NOTA_ES = cNumNota_in
         AND SEC_DET_NOTA_ES = cSecDetNota_in
         AND IND_PROD_AFEC <> 'S';
    END IF;
    --ESTADO DE LA CABECERA
    RECEP_P_ACT_EST_GUIA_RECEP(cGrupoCia_in,
                               cCodLocal_in,
                               cNumNota_in,
                               cIdUsu_in);

    --cierra guias
    RECEP_P_ACT_EST_GUIA(cGrupoCia_in,
                         cCodLocal_in,
                         cNumNota_in,
                         cNumPag_in,
                         cIdUsu_in);

  END;

  /***************************************************************************************************************/
  --JMIRANDA 25.07.2011
  PROCEDURE RECEP_AFECTA_SOBRANTES(cGrupoCia_in     IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNroRecepcion_in IN CHAR,
                                         cUsu_in IN CHAR)
  IS
  BEGIN
   DBMS_OUTPUT.PUT_LINE('HOLA');

   RECEP_P_AFECTA_SOB_1(cGrupoCia_in,
                                 cCodLocal_in,
                                 cNroRecepcion_in,
                                 cUsu_in);

   RECEP_P_AFECTA_SOB_2(cGrupoCia_in,
                                 cCodLocal_in,
                                 cNroRecepcion_in,
                                 cUsu_in);
  END;

  /***************************************************************************************************************/
  --AFECTA SOBRANTES QUE ESTAN EN LA ENTREGA
  --JMIRANDA 26.07.2011
  PROCEDURE RECEP_P_AFECTA_SOB_1(cGrupoCia_in   IN CHAR,
                                 cCodLocal_in   IN CHAR,
                                 cNroRecepcion_in IN CHAR,
                                 cUsu_in IN CHAR)
  IS
  --PARA PRODUCTOS QUE ESTAN SOBRANTES Y ESTAN EN LA ENTREGA
  CURSOR CUR_PROD IS
      SELECT D.NUM_NOTA_ES,
           D.NUM_PAG_RECEP,
           D.SEC_DET_NOTA_ES,
           sum(d.cant_contada-d.cant_recepcionada) "CANT"
      FROM lgt_nota_es_det d, LGT_RECEP_ENTREGA E
     WHERE E.COD_GRUPO_CIA = cGrupoCia_in
       AND E.COD_LOCAL = cCodLocal_in
       AND E.NRO_RECEP = cNroRecepcion_in
       AND D.COD_GRUPO_CIA = E.COD_GRUPO_CIA
       AND D.COD_LOCAL = E.COD_LOCAL
       AND D.NUM_NOTA_ES = E.NUM_NOTA_ES
       AND D.SEC_GUIA_REM = E.SEC_GUIA_REM
       AND D.NUM_ENTREGA = E.NUM_ENTREGA
       AND D.IND_PROD_AFEC != 'S' -- EMAQUERA - 17/06/2015 - Para que solo tome los productos sin afectar.
       AND D.num_entrega IS NOT NULL
       AND d.cant_contada > d.cant_recepcionada
      GROUP BY D.NUM_NOTA_ES,
           D.SEC_DET_NOTA_ES,
           D.NUM_PAG_RECEP;

  BEGIN

    FOR C_PROD IN CUR_PROD
    LOOP
     RECEP_P_ACT_REG_GUIA_RECEP_S(cGrupoCia_in,
                                         cCodLocal_in,
                                         C_PROD.NUM_NOTA_ES,
                                         C_PROD.SEC_DET_NOTA_ES,
                                         C_PROD.NUM_PAG_RECEP,
                                         cUsu_in);
    END LOOP;
    
  END;

  /***************************************************************************************************************/
  PROCEDURE RECEP_P_AFECTA_SOB_2(cGrupoCia_in   IN CHAR,
                                 cCodLocal_in   IN CHAR,
                                 cNroRecepcion_in IN CHAR,
                                 cUsu_in IN CHAR)
  IS
  V_NumNota LGT_NOTA_ES_DET.NUM_NOTA_ES%TYPE;
  v_nSecGuia LGT_NOTA_ES_DET.SEC_GUIA_REM%TYPE;
  v_nNumLoteProd LGT_NOTA_ES_DET.NUM_LOTE_PROD%TYPE;
  v_nFecVctoProd LGT_NOTA_ES_DET.FEC_VCTO_PROD%TYPE;
  -- KMONCADA 20.07.2016 [PROYECTO M] RECEPCION POR LOTE Y FECHA DE VENCIMIENTO
  CURSOR CUR_PROD(vIsLocalM CHAR) IS
   -- PARA PRODUCTOS SOBRANTES QUE NO ESTAN EN LA ENTREGA
      SELECT H.COD_PROD,
             NVL(E.CANT_SEG_CONTEO, E.CANTIDAD) "CANT",
             DECODE(vIsLocalM, 'S', E.LOTE, NULL) NRO_LOTE,
             DECODE(vIsLocalM, 'S', TO_CHAR(E.FECHA_VENCIMIENTO_LOTE,'DD/MM/YYYY'), NULL) FCH_VENCE_LOTE
        FROM LGT_PROD_CONTEO E, LGT_PROD H
       WHERE E.COD_GRUPO_CIA = cGrupoCia_in
         AND E.COD_LOCAL = cCodLocal_in
         AND E.NRO_RECEP = cNroRecepcion_in
         -- KMONCADA 20.07.2016 [PROYECTO M] RECEPCION POR LOTE Y FECHA DE VENCIMIENTO
         AND (E.COD_PROD, DECODE(vIsLocalM, 'S', E.LOTE, NULL)) NOT IN
             (SELECT F.COD_PROD, DECODE(vIsLocalM, 'S', F.NUM_LOTE_PROD, NULL)
                FROM LGT_NOTA_ES_DET F, LGT_RECEP_ENTREGA G
               WHERE F.COD_GRUPO_CIA = cGrupoCia_in
                 AND F.COD_LOCAL = cCodLocal_in
                 AND G.NRO_RECEP = cNroRecepcion_in
               --  AND F.EST_NOTA_ES_DET = 'A'
                 AND F.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                 AND F.COD_LOCAL = G.COD_LOCAL
                 AND F.NUM_NOTA_ES = G.NUM_NOTA_ES
                 AND F.NUM_ENTREGA = G.NUM_ENTREGA
                 AND F.SEC_GUIA_REM = G.SEC_GUIA_REM)
         AND E.ESTADO_AFECTADO != 'S' -- EMAQUERA - 17/06/2015 - Para que solo tome los productos sin afectar.                 
         AND E.COD_GRUPO_CIA = H.COD_GRUPO_CIA
         AND E.COD_PROD = H.COD_PROD
         and NVL(E.CANT_SEG_CONTEO, E.CANTIDAD) > 0;
  BEGIN
  --OBTENGO CUALQUIER NUM_NOTA_ES Y SEC_GUIA_REM
  SELECT D.NUM_NOTA_ES,
         D.SEC_GUIA_REM,
         D.NUM_LOTE_PROD,
         D.FEC_VCTO_PROD
    INTO V_NumNota, v_nSecGuia, v_nNumLoteProd, v_nFecVctoProd
    FROM LGT_NOTA_ES_DET D
   WHERE D.COD_GRUPO_CIA = cGrupoCia_in
     AND D.COD_LOCAL = cCodLocal_in
     AND D.NUM_ENTREGA IN (SELECT NUM_ENTREGA
                             FROM LGT_RECEP_ENTREGA
                            WHERE COD_GRUPO_CIA = cGrupoCia_in
                              AND COD_LOCAL = cCodLocal_in
                              AND NRO_RECEP = cNroRecepcion_in)
     AND ROWNUM = 1;

    --KMONCADA 20.07.2016 [PROYECTO M] RECEPCION POR LOTE Y FECHA DE VENCIMIENTO
    FOR C_PROD IN CUR_PROD(FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cGrupoCia_in,cCodLocal_in))
    LOOP
          
          PTOVTA_RESPALDO_STK.RECEP_P_OPERAC_ANEXAS_PROD_SO(cGrupoCia_in, --ASOSA, 21.07.2010
                                                           cCodLocal_in,
                                                           V_NumNota,
                                                           v_nSecGuia,
                                                           C_PROD.COD_PROD,
                                                           C_PROD.CANT,   --MOV
                                                           cUsu_in,
                         --KMONCADA 20.07.2016 [PROYECTO M] RECEPCION POR LOTE Y FECHA DE VENCIMIENTO
                                                           NVL(C_PROD.NRO_LOTE,v_nNumLoteProd),
                                                           NVL(C_PROD.FCH_VENCE_LOTE,TO_CHAR(v_nFecVctoProd,'DD/MM/YYYY')));
           
    UPDATE LGT_PROD_CONTEO E
    SET E.IP_SEG_CONTEO = (SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS')
                              FROM DUAL),
        E.ESTADO_AFECTADO = 'S',
        E.FEC_MOD_CONTEO  = SYSDATE,
        E.FEC_SEG_CONTEO  = SYSDATE,
        E.USU_MOD_CONTEO  = cUsu_in,
        E.USU_SEG_CONTEO  = cUsu_in             
    WHERE E.COD_GRUPO_CIA = cGrupoCia_in
      AND E.COD_LOCAL = cCodLocal_in
      AND E.NRO_RECEP = cNroRecepcion_in
      AND E.COD_PROD = C_PROD.COD_PROD;
    END LOOP;
    
  END;


  /*********************************************************************************************************************/

  PROCEDURE RECEP_P_ACT_REG_GUIA_RECEP_S(cGrupoCia_in   IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNumNota_in    IN CHAR,
                                       cSecDetNota_in IN CHAR,
                                       cNumPag_in     IN CHAR,
                                       cIdUsu_in      IN CHAR) IS
    v_cEstDet   LGT_NOTA_ES_DET.EST_NOTA_ES_DET%TYPE;
    v_nSecGuia  LGT_NOTA_ES_DET.SEC_GUIA_REM%TYPE;
    v_cCodProd  LGT_NOTA_ES_DET.COD_PROD%TYPE;
    v_nCantMov  LGT_NOTA_ES_DET.CANT_MOV%TYPE;
    v_nNumLoteProd LGT_NOTA_ES_DET.NUM_LOTE_PROD%TYPE;
    v_nFecVctoProd LGT_NOTA_ES_DET.FEC_VCTO_PROD%TYPE;

  BEGIN
    SELECT D.SEC_GUIA_REM,
           D.COD_PROD,
           (D.CANT_CONTADA-D.CANT_RECEPCIONADA), --mov
           D.NUM_LOTE_PROD,
           D.FEC_VCTO_PROD
      INTO
           v_nSecGuia,
           v_cCodProd,
           v_nCantMov,
           v_nNumLoteProd,
           v_nFecVctoProd
      FROM LGT_NOTA_ES_DET D
     WHERE D.COD_GRUPO_CIA = cGrupoCia_in
       AND D.COD_LOCAL = cCodLocal_in
       AND D.NUM_NOTA_ES = cNumNota_in
       AND D.SEC_DET_NOTA_ES = cSecDetNota_in
       FOR UPDATE;


      PTOVTA_RESPALDO_STK.RECEP_P_OPERAC_ANEXAS_PROD_SO(cGrupoCia_in, --ASOSA, 21.07.2010
                                 cCodLocal_in,
                                 cNumNota_in,
                                 v_nSecGuia,
                                 v_cCodProd,
                                 v_nCantMov,   --MOV
                                 --cIdUsu_in);
                                 'AUT_SP',
                                 v_nNumLoteProd,
                                 TO_CHAR(v_nFecVctoProd,'DD/MM/YYYY'));

  END;

  /******************************************************************************** */

 FUNCTION INV_F_GET_IND_SOB_AFECTA(cCodGrupoCia_in IN CHAR)
                                                RETURN CHAR
 IS
   v_vIndicador  PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE;
 BEGIN
    BEGIN
      SELECT A.LLAVE_TAB_GRAL INTO v_vIndicador
        FROM PBL_TAB_GRAL A
       WHERE A.ID_TAB_GRAL  = '388';
    EXCEPTION WHEN OTHERS THEN
      v_vIndicador := 'S';
    END;

    RETURN v_vIndicador;
 END;

  --Descripcion: Obtiene listado de bultos por producto
  --Fecha       Usuario	  Comentario
  --09/06/2015  ERIOS     Creacion
  FUNCTION GET_LISTA_BULTOS(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNroRecepcion IN CHAR,
								  cCodProd_in IN CHAR) RETURN FarmaCursor
  IS
    curListado FarmaCursor;  
	v_nColmunas INTEGER;
	v_nFilas INTEGER;
  BEGIN
    
	v_nColmunas := 5;
	
	SELECT CEIL(COUNT(1)/v_nColmunas)
		INTO v_nFilas
    FROM PTOVENTA.LGT_PROD_CONTEO_BULTO
    WHERE
    COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_LOCAL = cCodLocal_in
    AND NRO_RECEP = cNroRecepcion
    AND COD_PROD = cCodProd_in;
	
    OPEN curListado FOR
	WITH
	--PIVOT PARA COLUMNAS
	PIV AS (SELECT CEIL(rownum/v_nColmunas) GRUPO,
			       ROW_NUMBER() OVER(PARTITION BY CEIL(rownum/v_nColmunas) ORDER BY 1) COLUMNA
			FROM (SELECT 1 FROM PTOVENTA.PBL_LOCAL WHERE ROWNUM<=v_nColmunas*v_nFilas)) ,
    --DATOS A MOSTRAR
	TAB AS (SELECT CEIL(rownum/v_nColmunas) GRUPO,
				   ROW_NUMBER() OVER(PARTITION BY CEIL(rownum/v_nColmunas) ORDER BY CORR_BULTO) COLUMNA,
				   CORR_BULTO||' ('||CANTIDAD||' '||(SELECT TRIM(UNI.DESC_CORTA_UNID_MEDIDA)
													FROM PTOVENTA.LGT_PROD PROD JOIN PTOVENTA.LGT_UNID_MEDIDA UNI ON (PROD.COD_UNID_PRESENT = UNI.COD_UNID_MEDIDA)
													WHERE PROD.COD_GRUPO_CIA = C.COD_GRUPO_CIA
													AND PROD.COD_PROD = C.COD_PROD)||')' VALOR
			FROM PTOVENTA.LGT_PROD_CONTEO_BULTO C
			WHERE
			COD_GRUPO_CIA = cCodGrupoCia_in
			AND COD_LOCAL = cCodLocal_in
			AND NRO_RECEP = cNroRecepcion
			AND COD_PROD = cCodProd_in )
	--FILAS EN COLUMNAS		
	SELECT listagg (nvl(VALOR,' '), '�') WITHIN GROUP (ORDER BY VALOR) RESULTADO 
	FROM TAB RIGHT JOIN PIV USING (GRUPO,COLUMNA)
	GROUP BY GRUPO;
	
	RETURN curListado;
  END;

	--Descripcion: Obtiene listado de bultos por producto para impresion
	--Fecha       Usuario	  Comentario
	--10/06/2015  ERIOS     Creacion
	FUNCTION GET_LISTA_BULTOS_IMP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNroRecepcion_in IN CHAR,cCodProducto_in IN CHAR) RETURN VARCHAR2
	IS
		v_vBultos VARCHAR2(300);
	BEGIN
		BEGIN
			WITH
			--DATOS A MOSTRAR
			TAB AS (SELECT 
					   --CORR_BULTO||'('||CANTIDAD||')' VALOR
					   CORR_BULTO||'('||CANTIDAD||' '||(SELECT TRIM(UNI.DESC_CORTA_UNID_MEDIDA)
													FROM PTOVENTA.LGT_PROD PROD JOIN PTOVENTA.LGT_UNID_MEDIDA UNI ON (PROD.COD_UNID_PRESENT = UNI.COD_UNID_MEDIDA)
													WHERE PROD.COD_GRUPO_CIA = C.COD_GRUPO_CIA
													AND PROD.COD_PROD = C.COD_PROD)||')' VALOR
					FROM PTOVENTA.LGT_PROD_CONTEO_BULTO C
					WHERE
					COD_GRUPO_CIA = cCodGrupoCia_in
					AND COD_LOCAL = cCodLocal_in
					AND NRO_RECEP = cNroRecepcion_in
					AND COD_PROD = cCodProducto_in )
			--FILAS EN COLUMNAS    
			SELECT listagg (nvl(VALOR,' '), ', ') WITHIN GROUP (ORDER BY VALOR) RESULTADO 
				INTO v_vBultos
			FROM TAB 
			;
			SELECT NVL2(v_vBultos,'<B>R.CIEGA:</B> '||v_vBultos,' ')
			INTO v_vBultos
			FROM DUAL;
		EXCEPTION
			WHEN NO_DATA_FOUND THEN
				v_vBultos := ' ';
		END;
		RETURN v_vBultos;
	END;
	
	FUNCTION GET_G_CCODGRUPOCIA RETURN CHAR
	IS
	BEGIN 
		RETURN g_cCodGrupoCia;
	END;
	FUNCTION GET_G_CCODLOCAL RETURN CHAR
	IS
	BEGIN 
		RETURN g_cCodLocal;
	END;
	FUNCTION GET_G_CNRORECEP RETURN CHAR
	IS
	BEGIN 
		RETURN g_cNroRecep;
	END;
	FUNCTION GET_G_CINDLOCALM RETURN CHAR
	IS
	BEGIN
	  RETURN g_cIndLocalM;
	END;
	
end PTOVENTA_RECEP_CIEGA_JCG;
/
