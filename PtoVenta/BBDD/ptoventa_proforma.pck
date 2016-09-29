CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_PROFORMA" AS

TYPE FarmaCursor IS REF CURSOR;

  EST_PED_PENDIENTE  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='P';
  EST_PED_ANULADO  	    VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='N';
  EST_PED_COBRADO  	    VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='C';
  EST_PED_PEND_VERIFICA VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='S';

  ESTADO_ACTIVO		  CHAR(1):='A';
  ESTADO_INACTIVO		  CHAR(1):='I';
	
  COD_TIP_COMP_BOLETA       VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='01';
  COD_TIP_COMP_FACTURA      VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='02';
  COD_TIP_COMP_GUIA         VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='03';
  COD_TIP_COMP_TICKET_BOL   VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='05';
	
  TIP_PEDIDO_DELIVERY      VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE:='02';
  TIP_PEDIDO_INSTITUCIONAL VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE:='03';
  
  COD_NUMERA_SEC_MOV_AJUSTE_KARD            PBL_NUMERA.COD_NUMERA%TYPE := '017';
  COD_NUMERA_SEC_KARDEX PTOVENTA.PBL_NUMERA.COD_NUMERA%TYPE := '016';
  
  g_cTipDocKdxGuiaES PTOVENTA.LGT_KARDEX.Tip_Comp_Pago%TYPE := '02';
  
  MUEVE_STOCK_PROFORMA PTOVENTA.LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '117';
  RETORNA_STOCK_PROFORMA PTOVENTA.LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '118';
  AJUSTE_ENTREGA PTOVENTA.LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '119';
  
  COD_FORMA_PAGO_PUNTOS CONSTANT CHAR(5) := '00090';
  COD_FORMA_PAGO_NCR CONSTANT CHAR(5) := '00091';
  COD_FORMA_PAGO_REDONDEO_PTOS  CONSTANT CHAR(5) := '00092';
  
  TIP_CARGA_LOTE_X_ENTEROS    CONSTANT CHAR(1) := '1';
  TIP_CARGA_LOTE_X_FRACCION   CONSTANT CHAR(1) := '2';
  TIP_CARGA_LOTE_X_TODO       CONSTANT CHAR(1) := '3';
  
  --Descripcion: LISTA LOS PEDIDOS PENDIENTES DE ENTREGA
  --Fecha       Usuario		Comentario
  --11/01/2016  ERIOS       Creacion
  --27/01/2016  KMONCADA    MODIFICACION: SE AGREGA PARAMETRO PARA LISTAR SEGUN ESTADO DE PROFORMA
  FUNCTION F_LISTA_CAB_PEDIDOS(cCodGrupoCia_in      IN CHAR,
                               cCodLocal_in         IN CHAR,
                               cTipoVenta_in        IN CHAR,
                               cEstadoProforma_in   IN TMP_VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE)
    RETURN FarmaCursor;
	
  --Descripcion: LISTA EL DETALLE DE UNA PROFORMA
  --Fecha       Usuario		Comentario
  --11/01/2016  ERIOS       Creacion
  FUNCTION F_LISTA_DETALLE_PEDIDOS(cCodGrupoCia_in     IN CHAR,
                                    cCodLocal_in         IN CHAR,
                                    cCodLocalAtencion_in IN CHAR,
                                    cNumPedido_in        IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Lista el detalle de pedido en multiseleccion
  --Fecha       Usuario		Comentario
  --11/01/2016  ERIOS       Creacion
  FUNCTION F_LISTA_DETALLE_PEDIDOS_INST(cCodGrupoCia_in      IN CHAR,
                                          cCodLocal_in         IN CHAR,
                                          cCodLocalAtencion_in IN CHAR,
                                          cNumPedido_in        IN CHAR,
                                          cFlagFiltro_in       IN CHAR DEFAULT 'N')
  RETURN FarmaCursor;

  --Descripcion: Obtiene cajero de la proforma
  --Fecha       Usuario		Comentario
  --12/01/2016  ERIOS       Creacion
  FUNCTION F_GET_SECUENCIA_CAJERO(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
  		   						  cNumPedido_in   IN CHAR) RETURN VARCHAR2;									 

  --Descripcion: Retornar el stock
  --Fecha       Usuario		Comentario
  --12/01/2016  ERIOS       Creacion
  PROCEDURE P_RETORNA_STOCK_TEMPORAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
  		   						  cNumPedido_in   IN CHAR,
								  cUsuModProdLocal_in IN VARCHAR2);		

  --Descripcion: Mueve el stock al almacen temporal
  --Fecha       Usuario		Comentario
  --13/01/2016  ERIOS       Creacion
  PROCEDURE P_MUEVE_STOCK_TEMPORAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
  		   						  cNumPedido_in   IN CHAR,
								  cUsuModProdLocal_in IN VARCHAR2);		

  --Descripcion: Afecta stock temporal
  --Fecha       Usuario		Comentario
  --13/01/2016  ERIOS       Creacion
  PROCEDURE P_AFECTA_STOCK_TEMPORAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
  		   						  cNumProforma_in   IN CHAR,
								  cMovKardex_in IN CHAR,
								  cUsuModProdLocal_in IN VARCHAR2,
								  nIndMovimiento_in INTEGER DEFAULT 1);

  FUNCTION F_OBTIENE_INFO_PEDIDO(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 cNumPedDiario_in IN CHAR,
                                 cFecPedVta_in    IN CHAR,
                                 nValorSelCopago_in number default -1)
  RETURN FarmaCursor;								  
  
  PROCEDURE P_CAJ_GRAB_FORMA_PAGO(cCodGrupoCia_in           IN CHAR,
                                  cCodLocal_in              IN CHAR,
                                  cCodFormaPago_in          IN CHAR,
                                  cNumPedVta_in             IN CHAR,
                                  nImPago_in                IN NUMBER,
                                  cTipMoneda_in             IN CHAR,
                                  nValTipCambio_in          IN NUMBER,
                                  nValVuelto_in             IN NUMBER,
                                  nImTotalPago_in           IN NUMBER,
                                  cNumTarj_in               IN CHAR,
                                  cFecVencTarj_in           IN CHAR,
                                  cNomTarj_in               IN CHAR,
                                  cCanCupon_in              IN NUMBER,
                                  cUsuCreaFormaPagoPed_in   IN CHAR,
                                  cDNI_in                   IN CHAR,
                                  cCodAtori_in              IN CHAR,
                                  cLote_in                  IN CHAR,
										              cNumPedVtaNCR_in          IN CHAR DEFAULT NULL);
                                  
  FUNCTION F_FID_VALIDA_COBRO_PEDIDO(cCodGrupoCia_in in char,
                                     cCodLocal_in    in char,
                                     cNumPedVta_in   in char) return varchar2;

  FUNCTION F_CAJ_VERIFICA_PED_FOR_PAG(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cNumPedVta_in   IN CHAR)
  RETURN CHAR;
  
  FUNCTION F_ACTUALIZAR_ESTADO_PEDIDO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cNumPedVta_in    IN CHAR,
                                      cEstadoNuevo_in  IN CHAR,
                                      cSecMovCaja_in   IN CHAR)
  RETURN CHAR;
  
  PROCEDURE P_ASIGNAR_LOTE_PROD_PROFORMA(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNumPedVta_in    IN CHAR);
                                         
  PROCEDURE P_ASIGNAR_LOTE_PROD_PROFORMA(cCodGrupoCia_in  IN CHAR,
                                          cCodLocal_in     IN CHAR,
                                          cNumPedVta_in    IN CHAR,
                                          cTipoCarga       IN CHAR);

  PROCEDURE P_AJUSTE_POR_ENTEGRA(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
  		   						  cCodProd_in   IN CHAR,
								  nCantidad_in IN NUMBER,
								  vLote_in IN VARCHAR2,
								  vFechaVenc_in IN VARCHAR2,
								  vUsuCrea_in IN VARCHAR2);
  
  FUNCTION F_VALIDA_ASIGNA_LOTE(cCodGrupoCia_in   IN CHAR,
                                cCodLocal_in      IN CHAR,
                                cNumPedVta_in     IN CHAR)
  RETURN CHAR;
  
  FUNCTION F_OBTENER_CANT_PISOS_AVISAR(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cNumPedVta_in     IN CHAR)
    RETURN FARMACURSOR;
    
  FUNCTION F_GET_IMPR_CONSTANCIA_PAGO(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cNumPedVta_in     IN CHAR)
    RETURN FARMACURSOR;
  
  FUNCTION F_DATOS_VALIDAR_VTA_EMPRESA(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cNumPedVta_in     IN CHAR)
    RETURN FARMACURSOR;

  FUNCTION F_LISTA_PISOS_DESPACHO
    RETURN FARMACURSOR;
  
  PROCEDURE P_ANULAR_PROFORMA(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cNumProforma_in IN CHAR,
                              cUsuario_in     IN CHAR);
  
  FUNCTION F_IMPR_VOUCHER_PROFORMA(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cNumPedVta_in     IN CHAR)
  RETURN FARMACURSOR;
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_PROFORMA" AS

  FUNCTION F_LISTA_CAB_PEDIDOS(cCodGrupoCia_in      IN CHAR,
                               cCodLocal_in         IN CHAR,
                               cTipoVenta_in        IN CHAR,
                               cEstadoProforma_in   IN TMP_VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE)
    RETURN FarmaCursor
  IS
    curCli FarmaCursor;
  BEGIN
    OPEN curCli FOR
      SELECT ( 
        --TMP_CAB.COD_LOCAL || 'Ã' || -- 0
        TMP_CAB.NUM_PED_VTA || 'Ã' || -- 0
        TMP_CAB.NUM_PED_DIARIO || 'Ã' || -- 1
        TO_CHAR(TMP_CAB.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss') || 'Ã' || -- 2
        TO_CHAR(TMP_CAB.VAL_NETO_PED_VTA,'999,990.00')  || 'Ã' || -- 3
        DECODE(TMP_CAB.EST_PED_VTA, EST_PED_PENDIENTE, 'PEND.COBRO',
                                    EST_PED_COBRADO, 'COBRADO',
                                    EST_PED_PEND_VERIFICA, 'PEND.ENTREGA',
                                    EST_PED_ANULADO, 'ANULADO',
                                    ' ') || 'Ã' || -- 4
        -- KMONCADA 16.09.2014 MOSTRAR COMPROBANTES SEGUN CONVENIO
        CASE
          WHEN TMP_CAB.COD_CONVENIO IS NOT NULL THEN
            ( SELECT REPLACE(
                      REPLACE(
                        DECODE(CONV.COD_TIPDOC_CLIENTE, NULL,'', (SELECT A.DES_TIPODOC||'@' FROM MAE_TIPO_COMP_PAGO_BTLMF A WHERE A.COD_TIPODOC=CONV.COD_TIPDOC_CLIENTE))||
                        DECODE(CONV.COD_TIPDOC_BENEFICIARIO, NULL,'', (SELECT '@'||A.DES_TIPODOC FROM MAE_TIPO_COMP_PAGO_BTLMF A WHERE A.COD_TIPODOC=CONV.COD_TIPDOC_BENEFICIARIO))
                      ,'@@','/')
                    ,'@','')
              FROM MAE_CONVENIO CONV
              WHERE CONV.COD_CONVENIO = TMP_CAB.COD_CONVENIO
            )
          ELSE
            DECODE(TMP_CAB.TIP_COMP_PAGO, COD_TIP_COMP_BOLETA, 'BOLETA',
                                          COD_TIP_COMP_FACTURA, 'FACTURA',
                                          COD_TIP_COMP_TICKET_BOL, 'TICKET',
                                          COD_TIP_COMP_GUIA, 'GUIA',
                                          'ND-' || TMP_CAB.TIP_COMP_PAGO)
        END || 'Ã' || -- 5
        NVL(TMP_CAB.NOM_CLI_PED_VTA,' ')|| 'Ã' || -- 6
        -- KMONCADA 18.09.2014 PARA QUE SE MUESTRA DIRECCION DEL CLIENTE DELIVERY
        NVL(TMP_CAB.DIR_CLI_PED_VTA,' ') || 'Ã' || -- 7
        ' ' || 'Ã' || -- 8
        TMP_CAB.COD_LOCAL || 'Ã' || -- 9
        TO_CHAR(TMP_CAB.FEC_PED_VTA,'yyyyMMdd HH24:mi:ss')|| 'Ã' || -- 10
        NVL(TMP_CAB.IND_CONV_BTL_MF,'N')|| 'Ã' || -- 11
        NVL(TMP_CAB.COD_CONVENIO,' ') || 'Ã' || -- 12
        NVL(TMP_CAB.IND_DLV_LOCAL,'N')|| 'Ã' || -- 13
        'N' || 'Ã' || -- 14
        ' '  || 'Ã' || -- 15
        ' '  || 'Ã' || -- 16
        ' '  || 'Ã' || -- 17
        ' '  || 'Ã' || -- 18
        ' '  || 'Ã' || -- 19
        ' '  || 'Ã' || -- 20
        ' ' || 'Ã' || -- 21
        NVL((SELECT CONV.DES_CONVENIO 
              FROM MAE_CONVENIO CONV
              WHERE CONV.COD_CONVENIO = TMP_CAB.COD_CONVENIO)
            ,' ')|| 'Ã' || -- 22
        NVL(TMP_CAB.RUC_CLI_PED_VTA,' ') || 'Ã' || -- 23
        TMP_CAB.TIP_COMP_PAGO || 'Ã' || -- 24
        TMP_CAB.EST_PED_VTA || 'Ã' || -- 25
        TMP_CAB.NUM_PED_DIARIO  -- 26
			) RESULTADO
      FROM   TMP_VTA_PEDIDO_VTA_CAB TMP_CAB
      WHERE  TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    TMP_CAB.COD_LOCAL = cCodlocal_In
      AND    TMP_CAB.EST_PED_VTA = cEstadoProforma_in
      --AND    TMP_CAB.TIP_PED_VTA = cTipoVenta_in
      -- KMONCADA 09.06.2016 [PROYECTO M] PARA EL CASO DE LOS PEDIDOS PENDIENTES DE ENTREGA 
      -- SE MOSTRARA TODOS LOS PEDIDOS SIN IMPORTAR EL DIA
		  AND    TRUNC(TMP_CAB.FEC_CREA_PED_VTA_CAB) = CASE
                                                     WHEN cEstadoProforma_in = EST_PED_PEND_VERIFICA THEN
                                                       TRUNC(TMP_CAB.FEC_CREA_PED_VTA_CAB)
                                                     ELSE
                                                       TRUNC(SYSDATE)
                                                   END
		  ;

    RETURN CURCLI;
 END;

  FUNCTION F_LISTA_DETALLE_PEDIDOS(cCodGrupoCia_in     IN CHAR,
                                    cCodLocal_in         IN CHAR,
                                    cCodLocalAtencion_in IN CHAR,
                                    cNumPedido_in        IN CHAR)
    RETURN FarmaCursor
  IS
    curCli FarmaCursor;
  BEGIN
          
    OPEN curCli FOR
      SELECT (
             TVTA_DET.COD_PROD|| 'Ã' ||
             NVL(PROD.DESC_PROD,' ')|| 'Ã' ||
             ------------------------------UNIDAD DE VENTA
             CASE 
               WHEN TVTA_DET.VAL_FRAC = 1 THEN PROD.DESC_UNID_PRESENT
               ELSE PROD_LOCAL.UNID_VTA
             END || 'Ã' ||
             -----------------------------PRECIO VENTA
             CASE 
               WHEN TVTA_DET.VAL_FRAC = 1 THEN
                 TO_CHAR(((TVTA_DET.VAL_PREC_VTA*TVTA_DET.VAL_FRAC)
                                                    ),'999990.000')
               ELSE
                 TO_CHAR(((TVTA_DET.VAL_PREC_VTA*TVTA_DET.VAL_FRAC)/PROD_LOCAL.VAL_FRAC_LOCAL
                                                    ),'999990.000')
             END || 'Ã' ||
             -------------------------------CANTIDAD ATENDIDA
             CASE 
               WHEN TVTA_DET.VAL_FRAC = 1 THEN
                 TO_CHAR(((TVTA_DET.CANT_ATENDIDA)/TVTA_DET.VAL_FRAC),'999990.00')
               ELSE
                    TO_CHAR((TVTA_DET.CANT_ATENDIDA/TVTA_DET.VAL_FRAC)*PROD_LOCAL.VAL_FRAC_LOCAL
                                                               ,'999990.00')
              END|| 'Ã' ||
             TO_CHAR(NVL(TVTA_DET.VAL_PREC_TOTAL,0),'999,990.000')  || 'Ã' ||
             NVL(LAB.NOM_LAB,' ') || 'Ã' ||
             NVL(PROD_LOCAL.Stk_Fisico, 0) || 'Ã' ||
             MOD((TVTA_DET.CANT_ATENDIDA) *(PROD_LOCAL.VAL_FRAC_LOCAL) , TVTA_DET.VAL_FRAC) || 'Ã' ||
             CASE WHEN TVTA_DET.VAL_FRAC = 1 AND PROD_LOCAL.VAL_FRAC_LOCAL<> 1  THEN
                  PROD_LOCAL.VAL_FRAC_LOCAL
                  ELSE 1 END  || 'Ã' ||
             NVL(PROD_LOCAL.EST_PROD_LOC,' ') || 'Ã' ||
             PROD_LOCAL.UNID_VTA
           ) RESULTADO
            FROM   TMP_VTA_PEDIDO_VTA_DET TVTA_DET,
                   LGT_PROD_LOCAL PROD_LOCAL,
                   LGT_PROD PROD,
                   LGT_LAB LAB,
                   TMP_VTA_PEDIDO_VTA_CAB TVTA_CAB
            WHERE  TVTA_DET.COD_GRUPO_CIA = ccodgrupocia_in
            AND	   TVTA_DET.COD_LOCAL = cCodLocal_in 
            AND	   TVTA_DET.NUM_PED_VTA = cnumpedido_in
            AND    TVTA_CAB.COD_GRUPO_CIA = TVTA_DET.COD_GRUPO_CIA
            AND    TVTA_CAB.COD_LOCAL = TVTA_DET.COD_LOCAL
            AND    TVTA_CAB.NUM_PED_VTA = TVTA_DET.NUM_PED_VTA
            AND	   TVTA_CAB.COD_LOCAL = PROD_LOCAL.COD_LOCAL
            AND    TVTA_CAB.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
            AND    TVTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
            AND	   TVTA_DET.COD_PROD = PROD_LOCAL.COD_PROD
            AND	   PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
            AND	   PROD_LOCAL.COD_PROD = PROD.COD_PROD
            AND	   TVTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
            AND	   PROD.COD_LAB = LAB.COD_LAB;

    RETURN curcli;
 END;

  FUNCTION F_LISTA_DETALLE_PEDIDOS_INST(cCodGrupoCia_in      IN CHAR,
                                          cCodLocal_in         IN CHAR,
                                          cCodLocalAtencion_in IN CHAR,
                                          cNumPedido_in        IN CHAR,
                                          cFlagFiltro_in       IN CHAR DEFAULT 'N')
    RETURN FarmaCursor
  IS
    curCli FarmaCursor;
  BEGIN
    OPEN curCli FOR
          SELECT (TVTA_DET.COD_PROD|| 'Ã' || -- 0
          	     NVL(PROD.DESC_PROD,' ')|| 'Ã' || --1
                 CASE 
				   WHEN TVTA_DET.VAL_FRAC = 1 THEN PROD.DESC_UNID_PRESENT
				   ELSE PROD_LOCAL.UNID_VTA
				 END || 'Ã' || -- 2
                 CASE WHEN TVTA_DET.VAL_FRAC = 1 THEN
					TO_CHAR(((TVTA_DET.VAL_PREC_VTA*TVTA_DET.VAL_FRAC)),'999990.00')
                 ELSE
					TO_CHAR(((TVTA_DET.VAL_PREC_VTA*TVTA_DET.VAL_FRAC)/PROD_LOCAL.VAL_FRAC_LOCAL),'999990.00')
                 END   || 'Ã' || -- 3
                 CASE WHEN TVTA_DET.VAL_FRAC = 1 THEN
                      TO_CHAR(((TVTA_DET.CANT_ATENDIDA)/TVTA_DET.VAL_FRAC),'999990')
                 ELSE
                      TO_CHAR((TVTA_DET.CANT_ATENDIDA/**//TVTA_DET.VAL_FRAC)*PROD_LOCAL.VAL_FRAC_LOCAL,'999990')
                 END || 'Ã' || -- 4
                 TO_CHAR(NVL(TVTA_DET.VAL_PREC_TOTAL,0),'999,990.000') || 'Ã' || -- 5
                 NVL(LAB.NOM_LAB,' ') || 'Ã' || -- 6
                 NVL(PROD_LOCAL.Stk_Fisico, 0) || 'Ã' || -- 7
                 MOD((TVTA_DET.CANT_ATENDIDA) *(PROD_LOCAL.VAL_FRAC_LOCAL) , TVTA_DET.VAL_FRAC) || 'Ã' || -- 8
				 TVTA_DET.VAL_FRAC || 'Ã' || -- 9
				 TVTA_DET.SEC_PED_VTA_DET || 'Ã' || -- 10
				 PROD_LOCAL.VAL_FRAC_LOCAL -- 11
				 ) RESULTADO
          FROM   TMP_VTA_PEDIDO_VTA_DET TVTA_DET,
          	     LGT_PROD_LOCAL PROD_LOCAL,
          	     LGT_PROD PROD,
          	     LGT_LAB LAB,
                 TMP_VTA_PEDIDO_VTA_CAB TVTA_CAB
          WHERE  TVTA_DET.COD_GRUPO_CIA = ccodgrupocia_in
--          AND	   TVTA_DET.COD_LOCAL = cCodLocal_in -- kmoncada
          AND	   TVTA_DET.NUM_PED_VTA = cnumpedido_in
          AND    TVTA_CAB.COD_LOCAL_ATENCION = cCodLocalAtencion_in
          AND    TVTA_CAB.COD_GRUPO_CIA = TVTA_DET.COD_GRUPO_CIA
          AND    TVTA_CAB.COD_LOCAL = TVTA_DET.COD_LOCAL
          AND    TVTA_CAB.NUM_PED_VTA = TVTA_DET.NUM_PED_VTA
          AND	   TVTA_CAB.COD_LOCAL_ATENCION = PROD_LOCAL.COD_LOCAL
          AND    TVTA_CAB.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND    TVTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   TVTA_DET.COD_PROD = PROD_LOCAL.COD_PROD
          AND	   PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
          AND	   PROD_LOCAL.COD_PROD = PROD.COD_PROD
          AND	   TVTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   PROD.COD_LAB = LAB.COD_LAB
          AND    (cFlagFiltro_in = 'N'
                  OR TVTA_DET.COD_PROD NOT  IN (
                      SELECT Y.COD_PROD
                      FROM (SELECT DET.COD_PROD COD_PROD,
                                   (SELECT SUM(INS.CANT_ATENDIDA)

                                      FROM TMP_VTA_INSTITUCIONAL_DET INS
                                     WHERE INS.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                                       AND INS.COD_LOCAL = DET.COD_LOCAL
                                       AND INS.NUM_PED_VTA = DET.NUM_PED_VTA
                                       AND INS.COD_PROD = DET.COD_PROD) nCantProdLote,
                                   (SELECT SUM(CASE
                                                 WHEN TMP_DET.VAL_FRAC = 1 THEN
                                                  (TMP_DET.CANT_ATENDIDA / TMP_DET.VAL_FRAC)
                                                 ELSE
                                                  ((TMP_DET.CANT_ATENDIDA * PL.VAL_FRAC_LOCAL) /
                                                  TMP_DET.VAL_FRAC)
                                               END)

                                      FROM TMP_VTA_PEDIDO_VTA_DET TMP_DET, LGT_PROD_LOCAL PL
                                     WHERE TMP_DET.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                                       AND TMP_DET.COD_LOCAL = DET.COD_LOCAL
                                       AND TMP_DET.NUM_PED_VTA = DET.NUM_PED_VTA
                                       AND TMP_DET.EST_PED_VTA_DET = ESTADO_ACTIVO
                                       AND TMP_DET.COD_PROD = PL.COD_PROD
                                       AND TMP_DET.COD_PROD = DET.COD_PROD) nCantDetalle
                              FROM TMP_VTA_PEDIDO_VTA_DET DET
                             WHERE DET.COD_GRUPO_CIA = ccodgrupocia_in
                               AND DET.NUM_PED_VTA = cnumpedido_in
                               AND DET.EST_PED_VTA_DET = ESTADO_ACTIVO) Y
                     WHERE Y.nCantProdLote = Y.nCantDetalle)
                );
    RETURN curcli;
 END; 

  FUNCTION F_GET_SECUENCIA_CAJERO(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumPedido_in   IN CHAR) RETURN VARCHAR2 IS
    v_vSecUsuLocal PTOVENTA.CE_MOV_CAJA.SEC_USU_LOCAL%TYPE;
  BEGIN
    SELECT MCA.SEC_USU_LOCAL
      INTO v_vSecUsuLocal
      FROM PTOVENTA.TMP_VTA_PEDIDO_VTA_CAB TVC
      JOIN PTOVENTA.CE_MOV_CAJA MCA
        ON (TVC.COD_GRUPO_CIA = MCA.COD_GRUPO_CIA AND
           TVC.COD_LOCAL = MCA.COD_LOCAL AND
           TVC.SEC_MOV_CAJA = MCA.SEC_MOV_CAJA)
     WHERE TVC.COD_GRUPO_CIA = cCodGrupoCia_in
       AND TVC.COD_LOCAL = cCodLocal_in
       AND TVC.NUM_PED_VTA_ORIGEN = cNumPedido_in;
  
    RETURN v_vSecUsuLocal;
  END;

  PROCEDURE P_RETORNA_STOCK_TEMPORAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
  		   						  cNumPedido_in   IN CHAR,
								  cUsuModProdLocal_in IN VARCHAR2)
  IS
	
	cNumPedVta_in PTOVENTA.VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
	v_cTipPedVta PTOVENTA.VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE;
	
  BEGIN
	
	/*SELECT TVC.NUM_PED_VTA, TVC.TIP_PED_VTA
		INTO cNumPedVta_in, v_cTipPedVta
	FROM PTOVENTA.TMP_VTA_PEDIDO_VTA_CAB TVC
	WHERE TVC.COD_GRUPO_CIA = cCodGrupoCia_in
	AND TVC.COD_LOCAL = cCodLocal_in
	AND TVC.NUM_PED_VTA_ORIGEN = cNumPedido_in;*/
	
	--Proformas de local-mayorista
	--IF v_cTipPedVta = '01' THEN
  IF FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cCodGrupoCia_in, cCodLocal_in) = 'S' THEN
	  P_AFECTA_STOCK_TEMPORAL(cCodGrupoCia_in,cCodLocal_in,cNumPedido_in,RETORNA_STOCK_PROFORMA,cUsuModProdLocal_in);
    END IF;
		
  END;								  
  
  PROCEDURE P_MUEVE_STOCK_TEMPORAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
  		   						  cNumPedido_in   IN CHAR,
								  cUsuModProdLocal_in IN VARCHAR2)
  IS
    v_cTipPedVta PTOVENTA.VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE;
  BEGIN
    SELECT TVC.TIP_PED_VTA
		INTO v_cTipPedVta
	FROM PTOVENTA.TMP_VTA_PEDIDO_VTA_CAB TVC
	WHERE TVC.COD_GRUPO_CIA = cCodGrupoCia_in
	AND TVC.COD_LOCAL = cCodLocal_in
	AND TVC.NUM_PED_VTA = cNumPedido_in;
	
	--Proformas de local-mayorista
	--IF v_cTipPedVta = '01' THEN
  IF FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cCodGrupoCia_in, cCodLocal_in) = 'S' THEN
      P_AFECTA_STOCK_TEMPORAL(cCodGrupoCia_in,cCodLocal_in,cNumPedido_in,MUEVE_STOCK_PROFORMA,cUsuModProdLocal_in,-1);
	END IF;
  END;
  
  PROCEDURE P_AFECTA_STOCK_TEMPORAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
  		   						  cNumProforma_in   IN CHAR,
								  cMovKardex_in IN CHAR,
								  cUsuModProdLocal_in IN VARCHAR2,
								  nIndMovimiento_in INTEGER DEFAULT 1)
  IS
    CURSOR curProductosProforma IS
	SELECT VTA_DET.COD_PROD, 
	       INS.CANT_ATENDIDA * nIndMovimiento_in "CANT_ATENDIDA",
         -- KMONCADA 03.02.2016 CANTIDAD EN FRACCION DEL LOCAL
         --((PROD_LOCAL.VAL_FRAC_LOCAL * INS.CANT_ATENDIDA) / INS.VAL_FRAC) * nIndMovimiento_in "CANT_ATENDIDA",
	       VTA_DET.VAL_FRAC AS VAL_FRAC_VTA,
		   NVL( (SELECT STK_FISICO FROM PTOVENTA.LGT_PROD_LOCAL_LOTE
            WHERE COD_GRUPO_CIA = INS.COD_GRUPO_CIA AND COD_LOCAL = INS.COD_LOCAL
            AND COD_PROD = INS.COD_PROD AND LOTE = INS.NUM_LOTE_PROD) 
            , PROD_LOCAL.STK_FISICO) STK_FISICO,
		   PROD_LOCAL.VAL_FRAC_LOCAL,
		   PROD_LOCAL.UNID_VTA,
		   INS.NUM_LOTE_PROD,
		   INS.fec_vencimiento_lote
	FROM PTOVENTA.TMP_VTA_PEDIDO_VTA_DET VTA_DET
		 JOIN PTOVENTA.LGT_PROD_LOCAL PROD_LOCAL ON (VTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
				 AND	  VTA_DET.COD_LOCAL = PROD_LOCAL.COD_LOCAL
				 AND	  VTA_DET.COD_PROD = PROD_LOCAL.COD_PROD)
		 JOIN PTOVENTA.TMP_VTA_MAYORISTA_DET INS ON (INS.COD_GRUPO_CIA = VTA_DET.COD_GRUPO_CIA
                 AND INS.COD_LOCAL = VTA_DET.COD_LOCAL
                 AND INS.NUM_PED_VTA = VTA_DET.NUM_PED_VTA
                 AND INS.SEC_PED_VTA_DET = VTA_DET.SEC_PED_VTA_DET)
	WHERE VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
	AND VTA_DET.COD_LOCAL = cCodLocal_in
	AND VTA_DET.NUM_PED_VTA = cNumProforma_in;

  BEGIN
		FOR productos_K IN curProductosProforma
		LOOP
		  Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
		  												   cCodLocal_in,
																 productos_K.COD_PROD,
																 cMovKardex_in,
																 g_cTipDocKdxGuiaES,
																 cNumProforma_in,
																 productos_K.STK_FISICO,
																 (productos_k.CANT_ATENDIDA*productos_K.VAL_FRAC_LOCAL)/productos_K.VAL_FRAC_VTA,
																 productos_K.VAL_FRAC_LOCAL,
																 productos_K.UNID_VTA,
																 cUsuModProdLocal_in,
																 COD_NUMERA_SEC_KARDEX,
																 NULL,NULL,NULL,
																 to_char(productos_K.fec_vencimiento_lote,'dd/mm/yyyy'),
															     productos_K.NUM_LOTE_PROD
																 );
																 
		  PTOVTA_RESPALDO_STK.P_ACT_STOCK_PRODUCTO(cCodGrupoCia_in,
								cCodLocal_in,
								productos_K.COD_PROD,
								productos_K.CANT_ATENDIDA,
								productos_K.VAL_FRAC_LOCAL,
								productos_K.VAL_FRAC_VTA,
								cUsuModProdLocal_in,
								productos_K.NUM_LOTE_PROD);
		END LOOP;  
  END;
  
  FUNCTION F_OBTIENE_INFO_PEDIDO(cCodGrupoCia_in    IN CHAR,
                                 cCodLocal_in       IN CHAR,
                                 cNumPedDiario_in   IN CHAR,
                                 cFecPedVta_in      IN CHAR,
                                 nValorSelCopago_in number default -1)
    RETURN FarmaCursor IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
      SELECT VTA_CAB.NUM_PED_VTA || 'Ã' || -- 0
             TO_CHAR(VTA_CAB.VAL_NETO_PED_VTA, '999,990.00') || 'Ã' || ---1
             TO_CHAR((VTA_CAB.VAL_NETO_PED_VTA /
                      VTA_CAB.VAL_TIP_CAMBIO_PED_VTA) +
                      DECODE(VTA_CAB.IND_DISTR_GRATUITA,
                             'N',
                             DECODE(VTA_CAB.VAL_NETO_PED_VTA, 0, 0, 0.05),
                             0.00),
                      '999,990.00') || 'Ã' || -- 2
             TO_CHAR(VTA_CAB.VAL_TIP_CAMBIO_PED_VTA, '990.00') || 'Ã' || -- 3
             NVL(VTA_CAB.TIP_COMP_PAGO, ' ') || 'Ã' || -- 4
             -- KMONCADA 01.09.2014 EN CASO DE CONVENIO MUESTRE LOS DOCUMENTOS A IMPRIMIR
             CASE
               WHEN VTA_CAB.COD_CONVENIO IS NOT NULL AND
                    VTA_CAB.TIP_PED_VTA <> TIP_PEDIDO_INSTITUCIONAL THEN
                PTOVENTA_CONV_BTLMF.BTLMF_F_VARCHAR_MSG_COMP(cCodGrupoCia_in,
                                                              VTA_CAB.COD_CONVENIO,
                                                              VTA_CAB.VAL_NETO_PED_VTA,
                                                              nValorSelCopago_in)
               ELSE
                 DECODE(VTA_CAB.TIP_COMP_PAGO,
                        COD_TIP_COMP_BOLETA,
                        'BOLETA',
                        COD_TIP_COMP_FACTURA,
                        'FACTURA',
                        COD_TIP_COMP_TICKET_BOL,
                        'TICKET',
                        'OTRO')
              END || 'Ã' || -- 5
              NVL(VTA_CAB.NOM_CLI_PED_VTA, ' ') || 'Ã' || -- 6
              NVL(VTA_CAB.RUC_CLI_PED_VTA, ' ') || 'Ã' || -- 7
              NVL(VTA_CAB.DIR_CLI_PED_VTA, ' ') || 'Ã' || -- 8
              NVL(VTA_CAB.TIP_PED_VTA, ' ') || 'Ã' || -- 9
              TO_CHAR(VTA_CAB.FEC_PED_VTA, 'dd/MM/yyyy') || 'Ã' || -- 10
              NVL(VTA_CAB.IND_DISTR_GRATUITA, ' ') || 'Ã' || -- 11
              NVL(VTA_CAB.IND_DELIV_AUTOMATICO, ' ') || 'Ã' ||-- 12
              VTA_CAB.CANT_ITEMS_PED_VTA || 'Ã' || -- 13
              NVL(VTA_CAB.IND_PED_CONVENIO, ' ') || 'Ã' || -- 14
              NVL(VTA_CAB.COD_CONVENIO, ' ') || 'Ã' || -- 15
              NVL(VTA_CAB.COD_CLI_CONV, ' ') || 'Ã' || -- 16
              NVL(VTA_CAB.PUNTO_LLEGADA, ' ') || 'Ã' || -- 17
              NVL(VTA_CAB_EMP.NUM_OC, ' ') || 'Ã' || -- 18
             -- KMONCADA 30.06.2014 MENSAJE DE POLIZA EN EL CASO DE VENTA EMPRESA
              (CASE
                WHEN VTA_CAB_EMP.COD_POLIZA IS NOT NULL AND
                     LENGTH(VTA_CAB_EMP.COD_POLIZA) > 1 THEN
                 'ATENCION BOTIQUIN ' || VTA_CAB_EMP.NOMBRE_CLIENTE_POLIZA ||
                 ' POLIZA N° ' || VTA_CAB_EMP.COD_POLIZA || '.'
                ELSE
                 ' '
              END) || 'Ã' || -- 19
              (CASE
              -- dubilluz 15.09.2014
                WHEN VTA_CAB.PCT_BENEFICIARIO IS NULL THEN
                 '-1'
                ELSE
                 to_char(VTA_CAB.PCT_BENEFICIARIO, '99999990.00')
              END) || 'Ã' ||--20
             --ERIOS 05.03.2015 Mensaje de ahorro
              (SELECT LLAVE_TAB_GRAL
                 FROM PBL_TAB_GRAL
                WHERE ID_TAB_GRAL = 467) || ' : S/.' || '@' ||
              TO_CHAR(VTA_CAB.VAL_DCTO_PED_VTA, '999,990.00') || 'Ã' || -- 21
              -- KMONCADA 18.04.2016 PERCEPCION
               TO_CHAR(
                 CASE 
                   WHEN NVL(VTA_CAB.VAL_PERCEPCION_PED_VTA,0) = 0 AND NVL(VTA_CAB.VAL_REDONDEO_PERCEPCION,0) != 0 THEN
                     NVL(VTA_CAB.VAL_REDONDEO_PERCEPCION,0) * (-1)
                   WHEN NVL(VTA_CAB.VAL_PERCEPCION_PED_VTA,0) != 0 THEN
                     NVL(VTA_CAB.VAL_PERCEPCION_PED_VTA,0)
                   ELSE
                     0
                  END
               ,'999,990.00') || 'Ã'|| -- 22
               TO_CHAR(NVL(VTA_CAB.VAL_REDONDEO_PERCEPCION*(-1),0),'999,990.00') || 'Ã'|| -- 23
               NVL(FARMA_UTILITY.F_GET_MSJ_APLICA_PERCEPCION(cCodGrupoCia_in, cCodLocal_in, VTA_CAB.NUM_PED_VTA),' ') || 'Ã' || -- 24
               VTA_CAB.EST_PED_VTA  -- 25
        FROM TMP_VTA_PEDIDO_VTA_CAB VTA_CAB
      -- KMONCADA
        LEFT JOIN VTA_PEDIDO_VTA_CAB_EMP VTA_CAB_EMP
          ON VTA_CAB.COD_GRUPO_CIA = VTA_CAB_EMP.COD_GRUPO_CIA
         AND VTA_CAB.COD_LOCAL = VTA_CAB_EMP.COD_LOCAL
         AND VTA_CAB.NUM_PED_VTA = VTA_CAB_EMP.NUM_PED_VTA
       WHERE VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
         AND VTA_CAB.COD_LOCAL = cCodLocal_in
         AND VTA_CAB.NUM_PED_DIARIO = cNumPedDiario_in
         AND TO_CHAR(VTA_CAB.FEC_PED_VTA, 'dd/MM/yyyy') =
             DECODE(cFecPedVta_in,
                    NULL,
                    TO_CHAR(SYSDATE, 'dd/MM/yyyy'),
                    TO_CHAR(TO_DATE(cFecPedVta_in, 'dd/MM/yyyy'),
                            'dd/MM/yyyy'))
         AND VTA_CAB.EST_PED_VTA IN (EST_PED_PENDIENTE);
    RETURN curCaj;
  END;
  
  PROCEDURE P_CAJ_GRAB_FORMA_PAGO(cCodGrupoCia_in           IN CHAR,
                                  cCodLocal_in              IN CHAR,
                                  cCodFormaPago_in          IN CHAR,
                                  cNumPedVta_in             IN CHAR,
                                  nImPago_in                IN NUMBER,
                                  cTipMoneda_in             IN CHAR,
                                  nValTipCambio_in          IN NUMBER,
                                  nValVuelto_in             IN NUMBER,
                                  nImTotalPago_in           IN NUMBER,
                                  cNumTarj_in               IN CHAR,
                                  cFecVencTarj_in           IN CHAR,
                                  cNomTarj_in               IN CHAR,
                                  cCanCupon_in              IN NUMBER,
                                  cUsuCreaFormaPagoPed_in   IN CHAR,
                                  cDNI_in                   IN CHAR,
                                  cCodAtori_in              IN CHAR,
                                  cLote_in                  IN CHAR,
										              cNumPedVtaNCR_in          IN CHAR DEFAULT NULL) IS
     valMontoPedido VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
     vRedime     CHAR(1) := 'N';
     vRedimeTodo CHAR(1) := 'N';
	 v_cNumPedVtaOrigen VTA_PEDIDO_VTA_CAB.NUM_PED_VTA_ORIGEN%TYPE;
  BEGIN
   --- KMONCADA 27.01.2016 VALIDAR SI SE USARA PUNTOS MONEDERO EN LAS PROFORMAS DEL LOCAL M
    IF 1=2 AND cCodFormaPago_in = COD_FORMA_PAGO_PUNTOS THEN
      SELECT CAB.VAL_NETO_PED_VTA
      INTO   valMontoPedido 
      FROM TMP_VTA_PEDIDO_VTA_CAB CAB
      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND   CAB.COD_LOCAL = cCodLocal_in
      AND   CAB.NUM_PED_VTA = cNumPedVta_in;
      
      IF valMontoPedido = nImTotalPago_in THEN
        vRedimeTodo := 'S';
      END IF;
      
      UPDATE TMP_VTA_PEDIDO_VTA_CAB CAB
      SET PT_REDIMIDO = nImPago_in
      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND CAB.COD_LOCAL = cCodLocal_in
      AND CAB.NUM_PED_VTA = cNumPedVta_in;
  		
      UPDATE TMP_VTA_PEDIDO_VTA_CAB CAB
      SET PT_TOTAL = PT_INICIAL+PT_ACUMULADO-PT_REDIMIDO
      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND CAB.COD_LOCAL = cCodLocal_in
      AND CAB.NUM_PED_VTA = cNumPedVta_in;
      -- KMONCADA 01.10.2015 SE VALIDARA SI SE USO TODOS LOS PUNTOS PERMITIDOS PARA REDIMIR SEGUN EL DETALLE DEL PEDIDO 
      vRedimeTodo := FARMA_PUNTOS.F_VAR_VALIDA_TODO_REDIMIDO(cCodGrupoCia_in => cCodGrupoCia_in, 
                                                             cCodLocal_in => cCodLocal_in, 
                                                             cNumPedVta_in => cNumPedVta_in, 
                                                             cMontoRedimido => nImTotalPago_in);
      
      FARMA_LEALTAD.RECALCULO_REDENCION_PEDIDO(cCodGrupoCia_in,
                                            cCodLocal_in         ,
                                            cNumPedVta_in        ,
                        nImTotalPago_in);
  --		RETURN;
      vRedime := 'S';
      
                                                                 
    END IF;  
    -- KMONCADA 27.01.2016 FIN DE REDENCION DE PUNTOS
    
	   IF vRedime = 'N' THEN
        INSERT INTO TMP_VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA,             COD_LOCAL,         COD_FORMA_PAGO,
                                              NUM_PED_VTA,               IM_PAGO,           TIP_MONEDA,
                                              VAL_TIP_CAMBIO,            VAL_VUELTO,        IM_TOTAL_PAGO,
                                              NUM_TARJ,                  FEC_VENC_TARJ,     NOM_TARJ,
                                              USU_CREA_FORMA_PAGO_PED,   CANT_CUPON,        DNI_CLI_TARJ,
                                              COD_AUTORIZACION,          COD_LOTE,          NUM_PED_VTA_NC)
                        VALUES (cCodGrupoCia_in,                cCodLocal_in,                                cCodFormaPago_in,
                                cNumPedVta_in,                  nImPago_in,                                  cTipMoneda_in,
                                nValTipCambio_in,               nValVuelto_in,                               nImTotalPago_in,
                                cNumTarj_in,                    TO_DATE(cFecVencTarj_in,'dd/MM/yyyy'),       cNomTarj_in,
                                cUsuCreaFormaPagoPed_in,        cCanCupon_in,                                cDNI_in,
                                -- KMONCADA 14.09.2015 SOLO GRABARA EN CASO DE PAGOS CON TARJETA
                                CASE 
                                  WHEN cCodFormaPago_in IN ('00001','00002','00050','00080') THEN
                                    ''
                                  ELSE
                                    cCodAtori_in
                                END,
                                CASE 
                                  WHEN cCodFormaPago_in IN ('00001','00002','00050','00080') THEN
                                    ''
                                  ELSE
                                    cLote_in
                                END,
									              cNumPedVtaNCR_in);
    
	
      --ERIOS 03.03.2015 NCR
      IF cCodFormaPago_in = COD_FORMA_PAGO_NCR THEN
	  
	       SELECT NUM_PED_VTA_ORIGEN
		     INTO v_cNumPedVtaOrigen
         FROM VTA_PEDIDO_VTA_CAB
         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND NUM_PED_VTA = cNumPedVtaNCR_in;
	       PTOVENTA_CAJ.MARCA_USO_NCR(cCodGrupoCia_in,cCodLocal_in,v_cNumPedVtaOrigen,cNumPedVta_in);
      END IF;
    END IF;
    
    IF vRedimeTodo = 'S' THEN
      -- KMONCADA 01.10.2015 EN CASO DE HABER REDIMIDO TODO LO PERMITIDO SE OBTENDRA EL MONTO DE LOS PRODUCTOS REDIMIDOS
        SELECT SUM(CAB.VAL_PREC_TOTAL)
        INTO   valMontoPedido 
        FROM TMP_VTA_PEDIDO_VTA_DET CAB
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   CAB.COD_LOCAL = cCodLocal_in
        AND   CAB.NUM_PED_VTA = cNumPedVta_in
        AND   NVL(CAB.FACTOR_PUNTOS,0) > 0;
      
      
        INSERT INTO TMP_VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA,
                                      COD_LOCAL,
                                      COD_FORMA_PAGO,
                                      NUM_PED_VTA,
                                      IM_PAGO,
                                      TIP_MONEDA,
                                      VAL_TIP_CAMBIO,
                                      VAL_VUELTO,
                                      IM_TOTAL_PAGO,
                                      NUM_TARJ,
                                      FEC_VENC_TARJ,
                                      NOM_TARJ,
                                      USU_CREA_FORMA_PAGO_PED,
                                      CANT_CUPON,
                                      DNI_CLI_TARJ,
                                      COD_AUTORIZACION,
                                      COD_LOTE,
									  NUM_PED_VTA_NC)
                              VALUES (cCodGrupoCia_in,
                                      cCodLocal_in,
                                      COD_FORMA_PAGO_REDONDEO_PTOS,
                                      cNumPedVta_in,
                                      valMontoPedido,
                                      cTipMoneda_in,
                                      nValTipCambio_in,
                                      nValVuelto_in,
                                      valMontoPedido,
                                      cNumTarj_in,
                                      TO_DATE(cFecVencTarj_in,'dd/MM/yyyy'),
                                      cNomTarj_in,
                                      cUsuCreaFormaPagoPed_in,
                                      cCanCupon_in,
                                      cDNI_in,
                                      cCodAtori_in,
                                      cLote_in,
									  cNumPedVtaNCR_in);
    END IF;
  END;
  
  FUNCTION F_FID_VALIDA_COBRO_PEDIDO(cCodGrupoCia_in in char,
                                     cCodLocal_in    in char,
                                     cNumPedVta_in   in char) return varchar2 is

    vResultado      char(1) := 'N';
    vCantCampanaUso number;
  begin

    select count(1)
      into vCantCampanaUso
      from TMP_vta_pedido_vta_det d, 
           vta_camp_x_fpago_uso cp
     where d.cod_grupo_cia = cCodGrupoCia_in
       and d.cod_local = cCodLocal_in
       and d.num_ped_vta = cNumPedVta_in
       and cp.estado = 'A'
       and d.cod_grupo_cia = cp.cod_grupo_cia
       and d.cod_camp_cupon = cp.cod_camp_cupon
       and cp.cod_forma_pago not in ('E0000', 'T0000');

    if vCantCampanaUso > 0 then
        select decode(count(1), 0, 'N', 'S')
          into vResultado
          from TMP_vta_pedido_vta_det    d,
               vta_camp_x_fpago_uso  cp,
               TMP_vta_forma_pago_pedido fp
         where d.cod_grupo_cia = cCodGrupoCia_in
           and d.cod_local = cCodLocal_in
           and d.num_ped_vta = cNumPedVta_in
           and cp.estado = 'A'
           and d.cod_grupo_cia = cp.cod_grupo_cia
           and d.cod_camp_cupon = cp.cod_camp_cupon
           and d.cod_grupo_cia = fp.cod_grupo_cia
           and d.cod_local = fp.cod_local
           and d.num_ped_vta = fp.num_ped_vta
           and cp.cod_grupo_cia = fp.cod_grupo_cia
           and (cp.cod_forma_pago = fp.cod_forma_pago
                or exists (select 1 from vta_rel_forma_pago
                            where cod_grupo_cia = fp.cod_grupo_cia
                                and cod_forma_hijo = fp.cod_forma_pago
                                and cod_forma_pago = cp.cod_forma_pago));
    else
      vResultado := 'S';
    end if;
    return vResultado;
  end;
  
  FUNCTION F_CAJ_VERIFICA_PED_FOR_PAG(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cNumPedVta_in   IN CHAR)
  RETURN CHAR
  IS

    v_cIndValidarMonto    PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='N';
    v_cEmailErrorPtoVenta PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='joliva';
    v_nValNetoPedVta      VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_nValRedondeo        VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PED_VTA%TYPE;
    v_nSumaTotDet       NUMBER:=0;
    v_nSumaValorDet     NUMBER:=0;
    v_nSumaFormaPago       NUMBER:=0;

    v_vDescLocal VARCHAR2(120);

    mesg_body   VARCHAR2(4000);

  BEGIN

    SELECT TRIM(G.LLAVE_TAB_GRAL) 
    INTO v_cIndValidarMonto
    FROM PBL_TAB_GRAL G
    WHERE G.ID_TAB_GRAL = 238;

    SELECT G.LLAVE_TAB_GRAL 
    INTO v_cEmailErrorPtoVenta
    FROM PBL_TAB_GRAL  G
    WHERE G.ID_TAB_GRAL = 241;

    SELECT C.VAL_NETO_PED_VTA, C.VAL_REDONDEO_PED_VTA
    INTO v_nValNetoPedVta, v_nValRedondeo
    FROM  TMP_VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   C.COD_LOCAL     = cCodLocal_in
    AND   C.NUM_PED_VTA   = cNumPedVta_in;

    SELECT SUM(D.VAL_PREC_TOTAL) into v_nSumaValorDet
    FROM TMP_VTA_PEDIDO_VTA_DET D
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   D.COD_LOCAL     = cCodLocal_in
    AND   D.NUM_PED_VTA   = cNumPedVta_in;

    --dubilluz 14.10.2011
    SELECT nvl(SUM(IM_TOTAL_PAGO - VAL_VUELTO),0) INTO v_nSumaFormaPago
    FROM   TMP_VTA_FORMA_PAGO_PEDIDO
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    NUM_PED_VTA = cNumPedVta_in;

    v_nSumaTotDet:= v_nSumaValorDet+v_nValRedondeo;

    IF (v_nSumaFormaPago = v_nSumaTotDet) AND (v_nSumaFormaPago = v_nValNetoPedVta ) THEN
        RETURN 'EXITO';
    ELSE
        IF v_cIndValidarMonto = 'S' THEN
           RETURN 'ERROR';
        ELSE
           RETURN 'EXITO';
        END IF;
      END IF;
  END;
  
  FUNCTION F_ACTUALIZAR_ESTADO_PEDIDO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cNumPedVta_in    IN CHAR,
                                      cEstadoNuevo_in  IN CHAR,
                                      cSecMovCaja_in   IN CHAR)
  RETURN CHAR IS
  BEGIN
    UPDATE TMP_VTA_PEDIDO_VTA_CAB
    SET EST_PED_VTA = cEstadoNuevo_in,
        SEC_MOV_CAJA = cSecMovCaja_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_LOCAL = cCodLocal_in
    AND NUM_PED_VTA = cNumPedVta_in;
    RETURN 'S';
  END;  
  
  PROCEDURE P_ASIGNAR_LOTE_PROD_PROFORMA(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNumPedVta_in    IN CHAR)
    IS
    vRspta CHAR(1);
    vMsjError VARCHAR2(1000);
    CURSOR PRODUCTOS IS
      SELECT X.* 
      FROM (
      SELECT A.*,
             (SELECT COUNT(DISTINCT D.VAL_FRAC)
              FROM TMP_VTA_INSTITUCIONAL_DET D
              WHERE D.COD_GRUPO_CIA = A.COD_GRUPO_CIA
              AND D.COD_LOCAL = A.COD_LOCAL
              AND D.NUM_PED_VTA = A.NUM_PED_VTA
              AND D.SEC_PED_VTA_DET = A.SEC_PED_VTA_DET) CANT
      FROM TMP_VTA_PEDIDO_VTA_DET A 
      WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
      AND A.COD_LOCAL = cCodLocal_in
      AND A.NUM_PED_VTA = cNumPedVta_in ) X
      WHERE X.CANT > 1;
    FILA PRODUCTOS%ROWTYPE;
    vNewSecDet NUMBER;
    vCodProd TMP_VTA_PEDIDO_VTA_DET.COD_PROD%TYPE;
    vFraccDif TMP_VTA_PEDIDO_VTA_DET.VAL_FRAC%TYPE;
    vCantIgual NUMBER;
    vCantDif NUMBER;
  BEGIN
    P_ASIGNAR_LOTE_PROD_PROFORMA(cCodGrupoCia_in => cCodGrupoCia_in,
                                 cCodLocal_in => cCodLocal_in,
                                 cNumPedVta_in => cNumPedVta_in, 
                                 cTipoCarga => TIP_CARGA_LOTE_X_ENTEROS);
    vRspta := F_VALIDA_ASIGNA_LOTE(cCodGrupoCia_in => cCodGrupoCia_in,
                                   cCodLocal_in => cCodLocal_in,
                                   cNumPedVta_in => cNumPedVta_in);

    IF vRspta = 'N' THEN
      P_ASIGNAR_LOTE_PROD_PROFORMA(cCodGrupoCia_in => cCodGrupoCia_in,
                                   cCodLocal_in => cCodLocal_in,
                                   cNumPedVta_in => cNumPedVta_in, 
                                   cTipoCarga => TIP_CARGA_LOTE_X_FRACCION);
      vRspta := F_VALIDA_ASIGNA_LOTE(cCodGrupoCia_in => cCodGrupoCia_in,
                                     cCodLocal_in => cCodLocal_in,
                                     cNumPedVta_in => cNumPedVta_in);
      IF vRspta = 'N' THEN
        P_ASIGNAR_LOTE_PROD_PROFORMA(cCodGrupoCia_in => cCodGrupoCia_in,
                                     cCodLocal_in => cCodLocal_in,
                                     cNumPedVta_in => cNumPedVta_in, 
                                     cTipoCarga => TIP_CARGA_LOTE_X_TODO);
        vRspta := PTOVENTA_PROFORMA.F_VALIDA_ASIGNA_LOTE(cCodGrupoCia_in => cCodGrupoCia_in,
                                                         cCodLocal_in => cCodLocal_in,
                                                         cNumPedVta_in => cNumPedVta_in);
      END IF;
    END IF;
    IF vRspta = 'N' THEN
      FOR LISTA  IN
        (
          SELECT COD_PROD || ' - ' || PENDIENTE  PRODUCTO
          FROM (
          SELECT A.COD_PROD, A.CANT - SUM(NVL(A.CANT_ATEND, 0)) PENDIENTE
            FROM (SELECT DET.COD_PROD,
                         (PROD.VAL_FRAC_LOCAL * DET.CANT_ATENDIDA) / DET.VAL_FRAC CANT,
                         NVL((PROD.VAL_FRAC_LOCAL * DET_LOTE.CANT_ATENDIDA) /
                             DET_LOTE.VAL_FRAC,
                             0) CANT_ATEND
                    FROM TMP_VTA_PEDIDO_VTA_DET    DET,
                         TMP_VTA_INSTITUCIONAL_DET DET_LOTE,
                         LGT_PROD_LOCAL            PROD
                   WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
                     AND DET.COD_LOCAL = cCodLocal_in
                     AND DET.NUM_PED_VTA = cNumPedVta_in
                     AND DET.COD_GRUPO_CIA = DET_LOTE.COD_GRUPO_CIA(+)
                     AND DET.COD_LOCAL = DET_LOTE.COD_LOCAL(+)
                     AND DET.NUM_PED_VTA = DET_LOTE.NUM_PED_VTA(+)
                     AND DET.SEC_PED_VTA_DET = DET_LOTE.SEC_PED_VTA_DET(+)
                     AND PROD.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                     AND PROD.COD_LOCAL = DET.COD_LOCAL
                     AND PROD.COD_PROD = DET.COD_PROD) A
                    GROUP BY A.COD_PROD, A.CANT
                   ) W
        WHERE W.PENDIENTE > 0 ) LOOP
        vMsjError := vMsjError||CHR(10)||LISTA.PRODUCTO;
      END LOOP;
      RAISE_APPLICATION_ERROR(-20120,'LOS SIGUIENTE PRODUCTOS NO CUENTAN CON STOCK SUFICIENTE:'||vMsjError);
    
    ELSE
      NULL;
      UPDATE TMP_VTA_INSTITUCIONAL_DET A
      SET (A.CANT_ATENDIDA, A.VAL_FRAC) =
           (SELECT CASE
                     WHEN DET.VAL_FRAC = DET_LOTE.VAL_FRAC THEN
                      DET_LOTE.CANT_ATENDIDA
                     WHEN MOD(DET_LOTE.CANT_ATENDIDA, DET_LOTE.VAL_FRAC) = 0 THEN
                      (DET.VAL_FRAC * DET_LOTE.CANT_ATENDIDA) / DET_LOTE.VAL_FRAC
                     ELSE
                      DET_LOTE.CANT_ATENDIDA
                   END CANTIDAD_LOTE,
                   CASE
                     WHEN DET.VAL_FRAC = DET_LOTE.VAL_FRAC THEN
                      DET_LOTE.VAL_FRAC
                     WHEN MOD(DET_LOTE.CANT_ATENDIDA, DET_LOTE.VAL_FRAC) = 0 THEN
                      DET.VAL_FRAC
                     ELSE
                      DET_LOTE.VAL_FRAC
                   END VAL_FRAC_LOTE
              FROM TMP_VTA_PEDIDO_VTA_DET    DET,
                   TMP_VTA_INSTITUCIONAL_DET DET_LOTE
             WHERE DET.COD_GRUPO_CIA = DET_LOTE.COD_GRUPO_CIA
               AND DET.COD_LOCAL = DET_LOTE.COD_LOCAL
               AND DET.NUM_PED_VTA = DET_LOTE.NUM_PED_VTA
               AND DET.SEC_PED_VTA_DET = DET_LOTE.SEC_PED_VTA_DET
               AND DET_LOTE.COD_GRUPO_CIA = A.COD_GRUPO_CIA
               AND DET_LOTE.COD_LOCAL = A.COD_LOCAL
               AND DET_LOTE.NUM_PED_VTA = A.NUM_PED_VTA
               AND DET_LOTE.SEC_VTA_INST_DET = A.SEC_VTA_INST_DET)
      WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
      AND A.COD_LOCAL = cCodLocal_in
      AND A.NUM_PED_VTA = cNumPedVta_in;
      
      SELECT COUNT(1)
      INTO vNewSecDet
      FROM TMP_VTA_PEDIDO_VTA_DET DET
      WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND DET.COD_LOCAL = cCodLocal_in
      AND DET.NUM_PED_VTA=cNumPedVta_in;
      
      OPEN PRODUCTOS;
      LOOP
        FETCH PRODUCTOS INTO FILA;
        EXIT WHEN PRODUCTOS%NOTFOUND;
          vNewSecDet := vNewSecDet + 1;
          
          SELECT COD_PROD,
                 SUM(CASE
                       WHEN L.VAL_FRAC = FILA.VAL_FRAC THEN L.CANT_ATENDIDA
                       ELSE 0
                     END),
                 SUM(CASE
                       WHEN L.VAL_FRAC != FILA.VAL_FRAC THEN L.CANT_ATENDIDA
                       ELSE 0
                     END)
            INTO vCodProd, vCantIgual, vCantDif
            FROM TMP_VTA_INSTITUCIONAL_DET L
           WHERE L.COD_GRUPO_CIA = FILA.COD_GRUPO_CIA
             AND L.COD_LOCAL = FILA.COD_LOCAL
             AND L.NUM_PED_VTA = FILA.NUM_PED_VTA
             AND L.SEC_PED_VTA_DET = FILA.SEC_PED_VTA_DET
           GROUP BY COD_PROD;

          -- ACTUALIZAMOS REGISTRO ACTUAL
          UPDATE TMP_VTA_PEDIDO_VTA_DET D
          SET D.CANT_ATENDIDA = vCantIgual,
              D.VAL_PREC_TOTAL = D.VAL_PREC_VTA * vCantIgual,
              D.CANT_FRAC_LOCAL = (D.VAL_FRAC_LOCAL * vCantIgual)/D.VAL_FRAC  
          WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_PED_VTA = cNumPedVta_in
          AND D.SEC_PED_VTA_DET = FILA.SEC_PED_VTA_DET;
          
          -- AGREGAMOS EL DETALLE EN FRACCION
          INSERT INTO TMP_VTA_PEDIDO_VTA_DET(
                 cod_grupo_cia,        cod_local,             num_ped_vta,               sec_ped_vta_det, 
                 cod_prod,             cant_atendida,         val_prec_vta,              val_prec_total, 
                 porc_dcto_1,          porc_dcto_2,           porc_dcto_3,               porc_dcto_total, 
                 est_ped_vta_det,      val_total_bono,        val_frac,                  sec_comp_pago, 
                 sec_usu_local,        usu_crea_ped_vta_det,  fec_crea_ped_vta_det,      usu_mod_ped_vta_det, 
                 fec_mod_ped_vta_det,  val_prec_lista,        val_igv,                   unid_vta, 
                 ind_exonerado_igv,    sec_grupo_impr,        cant_usada_nc,             sec_comp_pago_origen, 
                 val_prec_public,      ind_calculo_max_min,   cod_prom,                  porc_dcto_calc, 
                 num_lote_prod,        fec_proceso_guia_rd,   desc_num_tel_rec,          val_num_trace, 
                 val_cod_aprobacion,   desc_num_tarj_virtual, val_num_pin,               fec_vencimiento_lote, 
                 fec_exclusion,        fecha_tx,              hora_tx,                   ind_origen_prod, 
                 val_frac_local,       cant_frac_local,       cant_xdia_tra,             cant_dias_tra, 
                 ind_zan,              val_prec_prom,         datos_imp_virtual,         cod_camp_cupon, 
                 ahorro,               porc_zan,              ind_prom_automatico,       ahorro_pack, 
                 porc_dcto_calc_pack,  cod_grupo_rep,         cod_grupo_rep_edmundo,     sec_respaldo_stk, 
                 sec_comp_pago_benef,  sec_comp_pago_empre,   num_comp_pago,             ahorro_conv, 
                 val_prec_total_empre, val_prec_total_benef,  tip_clien_convenio,        cod_tip_afec_igv_e, 
                 cod_tip_prec_vta_e,   val_prec_vta_unit_e,   cant_unid_vdd_e,           val_vta_item_e, 
                 val_total_igv_item_e, val_total_desc_item_e, desc_item_e,               val_vta_unit_item_e, 
                 dni_rimac,            ctd_puntos_acum,       ind_prod_mas_1,            ind_bonificado, 
                 cod_prod_puntos,      ind_cambio_cant,       ahorro_camp,               factor_puntos)
                 
          select cod_grupo_cia,        cod_local,             num_ped_vta,               vNewSecDet, 
                 cod_prod,             vCantDif,              val_prec_vta,              val_prec_total, 
                 porc_dcto_1,          porc_dcto_2,           porc_dcto_3,               porc_dcto_total, 
                 est_ped_vta_det,      val_total_bono,        val_frac,                  sec_comp_pago, 
                 sec_usu_local,        usu_crea_ped_vta_det,  fec_crea_ped_vta_det,      usu_mod_ped_vta_det, 
                 fec_mod_ped_vta_det,  val_prec_lista,        val_igv,                   unid_vta, 
                 ind_exonerado_igv,    sec_grupo_impr,        cant_usada_nc,             sec_comp_pago_origen, 
                 val_prec_public,      ind_calculo_max_min,   cod_prom,                  porc_dcto_calc, 
                 num_lote_prod,        fec_proceso_guia_rd,   desc_num_tel_rec,          val_num_trace, 
                 val_cod_aprobacion,   desc_num_tarj_virtual, val_num_pin,               fec_vencimiento_lote, 
                 fec_exclusion,        fecha_tx,              hora_tx,                   ind_origen_prod, 
                 val_frac_local,       cant_frac_local,       cant_xdia_tra,             cant_dias_tra, 
                 ind_zan,              val_prec_prom,         datos_imp_virtual,         cod_camp_cupon, 
                 ahorro,               porc_zan,              ind_prom_automatico,       ahorro_pack, 
                 porc_dcto_calc_pack,  cod_grupo_rep,         cod_grupo_rep_edmundo,     sec_respaldo_stk, 
                 sec_comp_pago_benef,  sec_comp_pago_empre,   num_comp_pago,             ahorro_conv, 
                 val_prec_total_empre, val_prec_total_benef,  tip_clien_convenio,        cod_tip_afec_igv_e, 
                 cod_tip_prec_vta_e,   val_prec_vta_unit_e,   cant_unid_vdd_e,           val_vta_item_e, 
                 val_total_igv_item_e, val_total_desc_item_e, desc_item_e,               val_vta_unit_item_e, 
                 dni_rimac,            ctd_puntos_acum,       ind_prod_mas_1,            ind_bonificado, 
                 cod_prod_puntos,      ind_cambio_cant,       ahorro_camp,               factor_puntos 
           from tmp_vta_pedido_vta_det DET
           WHERE DET.COD_GRUPO_CIA = FILA.COD_GRUPO_CIA
           AND DET.COD_LOCAL = FILA.COD_LOCAL
           AND DET.NUM_PED_VTA = FILA.NUM_PED_VTA
           AND DET.SEC_PED_VTA_DET = FILA.SEC_PED_VTA_DET;

            UPDATE TMP_VTA_PEDIDO_VTA_DET D
            SET (D.CANT_ATENDIDA,         D.VAL_PREC_VTA,     D.VAL_PREC_TOTAL,    D.VAL_FRAC,
                 D.VAL_PREC_LISTA,        D.UNID_VTA,         D.VAL_PREC_PUBLIC,   D.CANT_FRAC_LOCAL) 
                 = 
                (SELECT 
                 vCantDif,                (D.VAL_PREC_VTA/X.VAL_FRAC), ((D.VAL_PREC_VTA/X.VAL_FRAC) * vCantDif), X.VAL_FRAC,
                 (D.VAL_PREC_LISTA/X.VAL_FRAC),X.UNID_VTA, (D.VAL_PREC_PUBLIC/X.VAL_FRAC), vCantDif
                 FROM(
                     SELECT DISTINCT L.VAL_FRAC, PROD.UNID_VTA
                     FROM TMP_VTA_INSTITUCIONAL_DET L,
                          LGT_PROD_LOCAL PROD
                     WHERE L.COD_GRUPO_CIA = FILA.COD_GRUPO_CIA
                     AND L.COD_LOCAL = FILA.COD_LOCAL
                     AND L.NUM_PED_VTA = FILA.NUM_PED_VTA
                     AND L.SEC_PED_VTA_DET = FILA.SEC_PED_VTA_DET
                     AND L.VAL_FRAC != FILA.VAL_FRAC
                     AND PROD.COD_GRUPO_CIA = L.COD_GRUPO_CIA
                     AND PROD.COD_LOCAL = L.COD_LOCAL
                     AND PROD.COD_PROD = L.COD_PROD
                 ) X
               )
            WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
            AND D.COD_LOCAL = cCodLocal_in
            AND D.NUM_PED_VTA = cNumPedVta_in
            AND D.SEC_PED_VTA_DET = vNewSecDet;
            
            UPDATE TMP_VTA_INSTITUCIONAL_DET D
            SET D.SEC_PED_VTA_DET = vNewSecDet
            WHERE D.COD_GRUPO_CIA = FILA.COD_GRUPO_CIA
            AND D.COD_LOCAL = FILA.COD_LOCAL
            AND D.NUM_PED_VTA = FILA.NUM_PED_VTA
            AND D.SEC_PED_VTA_DET = FILA.SEC_PED_VTA_DET
            AND D.VAL_FRAC != FILA.VAL_FRAC;
            
      END LOOP;
      CLOSE PRODUCTOS;
      
      INSERT INTO TMP_VTA_MAYORISTA_DET (
             COD_GRUPO_CIA,             COD_LOCAL,              NUM_PED_VTA,          SEC_VTA_INST_DET, 
             COD_PROD,                  NUM_LOTE_PROD,          CANT_ATENDIDA,        USU_CREA_VTA_INST_DET, 
             FEC_CREA_VTA_INST_DET,     FEC_VENCIMIENTO_LOTE,   SEC_PED_VTA_DET,      VAL_FRAC)
      SELECT COD_GRUPO_CIA,             COD_LOCAL,              NUM_PED_VTA,          ROW_NUMBER() OVER(ORDER BY SEC_PED_VTA_DET, SEC_VTA_INST_DET), 
             COD_PROD,                  NUM_LOTE_PROD,          CANT_ATENDIDA,        USU_CREA_VTA_INST_DET, 
             FEC_CREA_VTA_INST_DET,     FEC_VENCIMIENTO_LOTE,   SEC_PED_VTA_DET,      VAL_FRAC 
      FROM TMP_VTA_INSTITUCIONAL_DET
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
      AND NUM_PED_VTA = cNumPedVta_in;
      
      DELETE FROM TMP_VTA_INSTITUCIONAL_DET
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
      AND NUM_PED_VTA = cNumPedVta_in;
      
      INSERT INTO TMP_VTA_INSTITUCIONAL_DET (
             COD_GRUPO_CIA,             COD_LOCAL,              NUM_PED_VTA,          SEC_VTA_INST_DET, 
             COD_PROD,                  NUM_LOTE_PROD,          CANT_ATENDIDA,        USU_CREA_VTA_INST_DET, 
             FEC_CREA_VTA_INST_DET,     FEC_VENCIMIENTO_LOTE,   SEC_PED_VTA_DET,      VAL_FRAC)
      SELECT COD_GRUPO_CIA,             COD_LOCAL,              NUM_PED_VTA,          ROW_NUMBER() OVER(ORDER BY SEC_PED_VTA_DET, SEC_VTA_INST_DET), 
             COD_PROD,                  NUM_LOTE_PROD,          CANT_ATENDIDA,        USU_CREA_VTA_INST_DET, 
             FEC_CREA_VTA_INST_DET,     FEC_VENCIMIENTO_LOTE,   SEC_PED_VTA_DET,      VAL_FRAC 
      FROM TMP_VTA_MAYORISTA_DET
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
      AND NUM_PED_VTA = cNumPedVta_in;
      
      UPDATE TMP_VTA_PEDIDO_VTA_CAB C
      SET  C.IND_LOTE_SEPARADO = 'S',
           C.CANT_ITEMS_PED_VTA = (SELECT COUNT(1)
                                   FROM TMP_VTA_PEDIDO_VTA_DET D
                                   WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                                   AND D.COD_LOCAL = cCodLocal_in
                                   AND D.NUM_PED_VTA = cNumPedVta_in)
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND C.COD_LOCAL = cCodLocal_in
      AND C.NUM_PED_VTA = cNumPedVta_in;
    END IF;
  END;
  
  PROCEDURE P_ASIGNAR_LOTE_PROD_PROFORMA(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNumPedVta_in    IN CHAR,
                                         cTipoCarga       IN CHAR)  
  IS
    vCantidad NUMBER;
  BEGIN
    SELECT COUNT(1)
    INTO vCantidad
    FROM TMP_VTA_INSTITUCIONAL_DET D
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
    AND D.COD_LOCAL = cCodLocal_in
    AND D.NUM_PED_VTA = cNumPedVta_in;
    
    INSERT INTO TMP_VTA_INSTITUCIONAL_DET (
           COD_GRUPO_CIA,         COD_LOCAL,          NUM_PED_VTA,  
           SEC_PED_VTA_DET,       COD_PROD,           NUM_LOTE_PROD, 
           FEC_VENCIMIENTO_LOTE,  CANT_ATENDIDA,      USU_CREA_VTA_INST_DET,
           FEC_CREA_VTA_INST_DET, SEC_VTA_INST_DET,   VAL_FRAC)
        
    SELECT K.COD_GRUPO_CIA,          K.COD_LOCAL,                       K.NUM_PED_VTA,
           K.SEC_DET,                K.COD_PROD,                        K.LOTE,
           K.FECHA_LOTE,             (K.STK_LOTE_DISPONIBLE - K.STK_FINAL_LOTE) CANT_USADA,   K.USUARIO,
           SYSDATE,                  (ROWNUM+vCantidad),                            K.VAL_FRAC_LOCAL
    FROM(
        SELECT  X.*,
                CASE
                 WHEN ORDEN = 1 AND CANT_PENDIENTE <= STK_LOTE_DISPONIBLE THEN
                  STK_LOTE_DISPONIBLE - CANT_PENDIENTE
                 WHEN ORDEN = 1 AND CANT_PENDIENTE > STK_LOTE_DISPONIBLE AND LEAD(STK_LOTE_ACUM) OVER(PARTITION BY SEC_DET ORDER BY SEC_DET ASC) IS NULL THEN
                  NULL
                 WHEN ORDEN = 1 AND CANT_PENDIENTE > STK_LOTE_DISPONIBLE AND LEAD(STK_LOTE_ACUM) OVER(PARTITION BY SEC_DET ORDER BY SEC_DET ASC) IS NOT NULL THEN
                  0
                 WHEN ORDEN > 1 AND STK_LOTE_ACUM = CANT_PENDIENTE THEN
                  0
                 WHEN ORDEN > 1 AND (LAG(STK_LOTE_ACUM - CANT_PENDIENTE) OVER(PARTITION BY SEC_DET ORDER BY SEC_DET ASC)) >= 0 THEN
                  STK_LOTE_DISPONIBLE
                 WHEN ORDEN > 1 AND CANT_PENDIENTE >= STK_LOTE_ACUM THEN
                  0
                 WHEN ORDEN > 1 AND (STK_LOTE_ACUM - CANT_PENDIENTE) <= STK_LOTE_DISPONIBLE THEN
                  STK_LOTE_ACUM - CANT_PENDIENTE
                 ELSE
                  NULL
                END STK_FINAL_LOTE
        FROM (
            SELECT Q.*,
                   SUM(Q.STK_LOTE_DISPONIBLE) OVER(PARTITION BY Q.SEC_DET ORDER BY Q.SEC_DET ROWS UNBOUNDED PRECEDING) STK_LOTE_ACUM
            FROM (
               SELECT PROD.*,
                      ROW_NUMBER() OVER(PARTITION BY PROD.SEC_DET ORDER BY PROD.FECHA_LOTE ) ORDEN
               FROM (
                  SELECT A.*,
                         (A.CANT_PEND_REAL - A.CANT_SEPARADA) CANT_PENDIENTE,
                         --(A.STK_LOTE_REAL - A.STK_LOTE_USADO) STK_LOTE_DISPONIBLE,
                         CASE 
                           WHEN cTipoCarga IN (TIP_CARGA_LOTE_X_ENTEROS,TIP_CARGA_LOTE_X_FRACCION) THEN
                             A.STK_LOTE_REAL
                           WHEN cTipoCarga = TIP_CARGA_LOTE_X_TODO THEN
                             (A.STK_LOTE_REAL - A.STK_LOTE_USADO) 
                         END STK_LOTE_DISPONIBLE
                  FROM(
                     SELECT DET.COD_GRUPO_CIA,
                            DET.COD_LOCAL,
                            DET.NUM_PED_VTA,
                            DET.USU_CREA_PED_VTA_DET USUARIO,
                            DET.SEC_PED_VTA_DET SEC_DET,
                            DET.COD_PROD,
                            DET.CANT_ATENDIDA CANT_PEND,
                            CASE 
                             WHEN cTipoCarga = TIP_CARGA_LOTE_X_ENTEROS THEN
                               (PROD.VAL_FRAC_LOCAL * TRUNC(((PROD.VAL_FRAC_LOCAL * DET.CANT_ATENDIDA) / DET.VAL_FRAC)
                                                            /PROD.VAL_FRAC_LOCAL)) 
                             ELSE
                               ((PROD.VAL_FRAC_LOCAL * DET.CANT_ATENDIDA) / DET.VAL_FRAC)
                            END CANT_PEND_REAL,
                            DET.VAL_FRAC VAL_FRAC_DET,
                            PROD.VAL_FRAC_LOCAL,
                            PROD.STK_FISICO STK_PROD,
                            LOTE.LOTE,
                            TO_DATE(TO_CHAR(LOTE.FECHA_VENCIMIENTO_LOTE, 'DD/MM/YYYY'), 'DD/MM/YYYY') FECHA_LOTE,
                            LOTE.STK_FISICO STK_LOTE,
                            CASE 
                              WHEN cTipoCarga = TIP_CARGA_LOTE_X_ENTEROS THEN
                                (PROD.VAL_FRAC_LOCAL * TRUNC(LOTE.STK_FISICO/PROD.VAL_FRAC_LOCAL))
                              WHEN cTipoCarga = TIP_CARGA_LOTE_X_FRACCION THEN 
                                MOD(LOTE.STK_FISICO,PROD.VAL_FRAC_LOCAL) 
                              WHEN cTipoCarga = TIP_CARGA_LOTE_X_TODO THEN
                                LOTE.STK_FISICO
                            END STK_LOTE_REAL,
                            NVL(( SELECT SUM((PROD.VAL_FRAC_LOCAL * D.CANT_ATENDIDA) / D.VAL_FRAC)
                                  FROM TMP_VTA_INSTITUCIONAL_DET D 
                                  WHERE D.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                                  AND D.COD_LOCAL = DET.COD_LOCAL
                                  AND D.NUM_PED_VTA = DET.NUM_PED_VTA
                                  AND D.SEC_PED_VTA_DET = DET.SEC_PED_VTA_DET),0) CANT_SEPARADA,
                                 
                            NVL(( SELECT SUM((PROD.VAL_FRAC_LOCAL * D.CANT_ATENDIDA) / D.VAL_FRAC)
                                  FROM TMP_VTA_INSTITUCIONAL_DET D 
                                  WHERE D.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                                  AND D.COD_LOCAL = DET.COD_LOCAL
                                  AND D.NUM_PED_VTA = DET.NUM_PED_VTA
                                  AND D.COD_PROD = LOTE.COD_PROD
                                  AND D.NUM_LOTE_PROD = LOTE.LOTE), 0) STK_LOTE_USADO
                                 
                          FROM TMP_VTA_PEDIDO_VTA_CAB CAB,
                               TMP_VTA_PEDIDO_VTA_DET DET,
                               LGT_PROD_LOCAL         PROD,
                               LGT_PROD_LOCAL_LOTE    LOTE
                         WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
                           AND DET.COD_LOCAL = cCodLocal_in
                           AND DET.NUM_PED_VTA = cNumPedVta_in
                           AND CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                           AND CAB.COD_LOCAL = DET.COD_LOCAL
                           AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
                           AND DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
                           AND DET.COD_LOCAL = PROD.COD_LOCAL
                           AND DET.COD_PROD = PROD.COD_PROD
                           AND PROD.COD_GRUPO_CIA = LOTE.COD_GRUPO_CIA
                           AND PROD.COD_LOCAL = LOTE.COD_LOCAL
                           AND PROD.COD_PROD = LOTE.COD_PROD
                           AND LOTE.STK_FISICO > 0
                           AND TRUNC(LOTE.FECHA_VENCIMIENTO_LOTE) > TRUNC(SYSDATE)
                           AND LOTE.LOTE NOT IN (SELECT LLAVE_TAB_GRAL 
                                                 FROM PBL_TAB_GRAL 
                                                 WHERE ID_TAB_GRAL = '597')
                           AND CASE
                                 WHEN NVL(CAB.IND_LOTE_SEPARADO,'N') = 'N' THEN 'S'
                                 WHEN NVL(CAB.IND_LOTE_SEPARADO,'N') = 'S' AND cTipoCarga = TIP_CARGA_LOTE_X_ENTEROS AND DET.VAL_FRAC=1 THEN 'S'
                                 WHEN NVL(CAB.IND_LOTE_SEPARADO,'N') = 'S' AND cTipoCarga != TIP_CARGA_LOTE_X_ENTEROS AND DET.VAL_FRAC=1 THEN 'N'
                                 WHEN NVL(CAB.IND_LOTE_SEPARADO,'N') = 'S' AND cTipoCarga IN (TIP_CARGA_LOTE_X_FRACCION,TIP_CARGA_LOTE_X_TODO) AND DET.VAL_FRAC!=1 THEN 'S'
                                 WHEN NVL(CAB.IND_LOTE_SEPARADO,'N') = 'S' AND cTipoCarga NOT IN (TIP_CARGA_LOTE_X_FRACCION,TIP_CARGA_LOTE_X_TODO) AND DET.VAL_FRAC!=1 THEN 'N'
                           END  = 'S'
                           ORDER BY DET.SEC_PED_VTA_DET, LOTE.FECHA_VENCIMIENTO_LOTE
                       ) A
                    )PROD 
                    WHERE CASE
                            WHEN cTipoCarga = TIP_CARGA_LOTE_X_ENTEROS AND PROD.CANT_PENDIENTE >= PROD.VAL_FRAC_LOCAL THEN 'S'
                            WHEN cTipoCarga = TIP_CARGA_LOTE_X_ENTEROS AND PROD.CANT_PENDIENTE < PROD.VAL_FRAC_LOCAL THEN 'N'
                            WHEN cTipoCarga IN (TIP_CARGA_LOTE_X_FRACCION,TIP_CARGA_LOTE_X_TODO) THEN 'S'
                          END = 'S'
                          AND PROD.STK_LOTE_DISPONIBLE!=0
                 ) Q
             ) X
        ) K 
        WHERE (K.STK_LOTE_DISPONIBLE - K.STK_FINAL_LOTE) >0
        ORDER BY K.SEC_DET, K.ORDEN;
  END;
  
  PROCEDURE P_AJUSTE_POR_ENTEGRA(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
  		   						  cCodProd_in   IN CHAR,
								  nCantidad_in IN NUMBER,
								  vLote_in IN VARCHAR2,
								  vFechaVenc_in IN VARCHAR2,
								  vUsuCrea_in IN VARCHAR2)
  IS
    CURSOR curProductosProforma IS
	SELECT NVL( (SELECT STK_FISICO FROM PTOVENTA.LGT_PROD_LOCAL_LOTE
            WHERE COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA AND COD_LOCAL = PROD_LOCAL.COD_LOCAL
            AND COD_PROD = cCodProd_in AND LOTE = vLote_in) 
            , PROD_LOCAL.STK_FISICO) STK_FISICO,
		   PROD_LOCAL.VAL_FRAC_LOCAL,
		   PROD_LOCAL.UNID_VTA
	FROM PTOVENTA.LGT_PROD_LOCAL PROD_LOCAL 
	WHERE PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
	AND PROD_LOCAL.COD_LOCAL = cCodLocal_in
	AND PROD_LOCAL.COD_PROD = cCodProd_in;

	v_nNeoCod CHAR(10);
  vStkFisico LGT_PROD_LOCAL_LOTE.STK_FISICO%TYPE; 
  vMensajeEmail VARCHAR2(2000);
  BEGIN
    SELECT NVL(SUM(STK_FISICO),-1)
    INTO vStkFisico
    FROM LGT_PROD_LOCAL_LOTE
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_LOCAL = cCodLocal_in
    AND COD_PROD = cCodProd_in
    AND LOTE = vLote_in;
    
    IF vStkFisico = -1 THEN
      PTOVENTA_VTA_MAYORISTA.P_INS_LOTE_MAYORISTA(cCodGrupoCia_in,
                                                  cCodLocal_in,
                                                  cCodProd_in,
                                                  vLote_in,
                                                  vFechaVenc_in,
                                                  vUsuCrea_in,
                                                  'S');
    END IF;
		
		FOR productos_K IN curProductosProforma
		LOOP
		  
		  v_nNeoCod:=Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_SEC_MOV_AJUSTE_KARD),10,'0','I' );
          Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_SEC_MOV_AJUSTE_KARD, vUsuCrea_in);
		  Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
		  												   cCodLocal_in,
																 cCodProd_in,
																 AJUSTE_ENTREGA,
																 g_cTipDocKdxGuiaES,
																 v_nNeoCod,
																 productos_K.STK_FISICO,
																 nCantidad_in,
																 productos_K.VAL_FRAC_LOCAL,
																 productos_K.UNID_VTA,
																 vUsuCrea_in,
																 COD_NUMERA_SEC_KARDEX,
																 NULL,NULL,NULL,
																 vFechaVenc_in,
															     vLote_in
																 );
																 
		  PTOVTA_RESPALDO_STK.P_ACT_STOCK_PRODUCTO(cCodGrupoCia_in,
								cCodLocal_in,
								cCodProd_in,
								nCantidad_in,
								productos_K.VAL_FRAC_LOCAL,
								productos_K.VAL_FRAC_LOCAL,
								vUsuCrea_in,
								vLote_in);
		END LOOP;
    
    -- ENVIAR CORREO DEL AJUSTE REALIZADO
    SELECT 'SE HA GENERADO UN AJUSTE AUTOMATICO, POR QUE EL DESPACHADOR '||vUsuCrea_in||' HA INDICADO<br>'||
           'QUE EL LOTE DEL PRODUCTO '||
           DECODE(vStkFisico,-1,'NO ESTABA REGISTRADO', 'NO CONTABA CON STOCK')||
           ' PARA PODER ATENDER UN PEDIDO.<br><br>'||
           '<b>DATOS DE PRODUCTO:<b> <br>'||
           '<b>COD.PRODUCTO:</b> '||cCodProd_in||'<br>'||
           '<b>DESC.PRODUCTO:</b> '||L.DESC_PROD||'<br>'||
           '<b>UNID.PRESENTACION:</b> '||L.DESC_UNID_PRESENT||'<br>'||
           '<b>LOTE/FECHA VENCIMIENTO:</b> '||vLote_in||' - '||vFechaVenc_in||'<br>'||
           '<b>CANT.MOVIMIENTO:</b> '||nCantidad_in||'<br>'||
           '<b>STOCK ACTUAL:</b> '||DECODE(vStkFisico,-1,'NO REGISTRADO',TO_CHAR(vStkFisico,'9990.00'))
    INTO vMensajeEmail
    FROM LGT_PROD L
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_PROD = cCodProd_in;
    
    FARMA_EMAIL.ENVIA_CORREO(csendoraddress_in => 'superfarma@mifarma.com.pe',
                             creceiveraddress_in => FARMA_GRAL.F_VAR_GET_FARMA_EMAIL('25'),
                             csubject_in => '[SUPERFARMA] LOCAL '||cCodLocal_in||' AJUSTE AUTOMATICO POR DESPACHO',
                             ctitulo_in => 'SE REALIZO UN AJUSTE AUTOMATICO AL REALIZAR EL DESPACHO DE UN PRODUCTO',
                             cmensaje_in => vMensajeEmail,
                             cin_html => true);
  END;
                                   
  FUNCTION F_VALIDA_ASIGNA_LOTE(cCodGrupoCia_in   IN CHAR,
                                cCodLocal_in      IN CHAR,
                                cNumPedVta_in     IN CHAR)
  RETURN CHAR IS
    vResultado CHAR(1);
  BEGIN

    SELECT DECODE(COUNT(1),0,'S','N')
    INTO vResultado
      FROM (SELECT A.COD_PROD, A.CANT - SUM(NVL(A.CANT_ATEND, 0)) PENDIENTE
              FROM (SELECT DET.COD_PROD,
                           (PROD.VAL_FRAC_LOCAL * DET.CANT_ATENDIDA) /
                           DET.VAL_FRAC CANT,
                           NVL((PROD.VAL_FRAC_LOCAL * DET_LOTE.CANT_ATENDIDA) /
                               DET_LOTE.VAL_FRAC,
                               0) CANT_ATEND
                      FROM TMP_VTA_PEDIDO_VTA_DET    DET,
                           TMP_VTA_INSTITUCIONAL_DET DET_LOTE,
                           LGT_PROD_LOCAL            PROD
                     WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND DET.COD_LOCAL = cCodLocal_in
                       AND DET.NUM_PED_VTA = cNumPedVta_in
                       AND DET.COD_GRUPO_CIA = DET_LOTE.COD_GRUPO_CIA(+)
                       AND DET.COD_LOCAL = DET_LOTE.COD_LOCAL(+)
                       AND DET.NUM_PED_VTA = DET_LOTE.NUM_PED_VTA(+)
                       AND DET.SEC_PED_VTA_DET = DET_LOTE.SEC_PED_VTA_DET(+)
                       AND PROD.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                       AND PROD.COD_LOCAL = DET.COD_LOCAL
                       AND PROD.COD_PROD = DET.COD_PROD) A
             GROUP BY A.COD_PROD, A.CANT) W
     WHERE W.PENDIENTE > 0;
     RETURN vResultado;
  END;
  
  FUNCTION F_OBTENER_CANT_PISOS_AVISAR(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cNumPedVta_in     IN CHAR)
    RETURN FARMACURSOR IS
    vCursorPisos FARMACURSOR;
  BEGIN
    OPEN vCursorPisos FOR
      SELECT P.PISO,
             NVL(A.TIPO_IMPR_TERMICA,' ') TIPO, 
             NVL(A.DESC_IMPR_LOCAL_TERM,' ') IMPRESORA
      FROM (
        SELECT DISTINCT SUBSTR(PROD.COD_POSICION,0,INSTR(PROD.COD_POSICION,'.',1)-1) PISO
        FROM TMP_VTA_PEDIDO_VTA_DET D,
             LGT_PROD_LOCAL PROD
        WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND D.COD_LOCAL = cCodLocal_in
        AND D.NUM_PED_VTA = cNumPedVta_in
        AND PROD.COD_GRUPO_CIA = D.COD_GRUPO_CIA
        AND PROD.COD_LOCAL = D.COD_LOCAL
        AND PROD.COD_PROD = D.COD_PROD
      ) P,
        VTA_IMPR_LOCAL_TERMICA A
      WHERE A.COD_GRUPO_CIA(+) = cCodGrupoCia_in
      AND A.COD_LOCAL(+) = cCodLocal_in
      AND A.PISO_DESPACHO(+) = P.PISO
      ORDER BY P.PISO;
    RETURN vCursorPisos;
  END;
  
  FUNCTION F_GET_IMPR_CONSTANCIA_PAGO(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cNumPedVta_in     IN CHAR)
    RETURN FARMACURSOR IS                                      
   
    CURSOR curDetalleProforma IS
      SELECT '  '||
             RPAD(A.CODIGO, 7, ' ') || 
             RPAD(SUBSTR(A.DESCRIPCION,0,18), 18, ' ') ||' '||
             RPAD(A.CANTIDAD, 7, ' ') || ' ' ||
             RPAD(A.PRESENTACION,12,' ') ||
             '  '||
             RPAD(SUBSTR(A.LABORATORIO,0,20), 20, ' ') /*||' '||
             RPAD(A.NUM_LOTE, 20, ' ')*/
             DETALLE
      
      FROM (
      SELECT DET.COD_PROD CODIGO,
             PROD.DESC_PROD DESCRIPCION,
             DECODE(DET_LOTE.VAL_FRAC,1,DET_LOTE.CANT_ATENDIDA||'',DET_LOTE.CANT_ATENDIDA||'/'||DET_LOTE.VAL_FRAC) CANTIDAD,
             SUBSTR(TRIM(DECODE(DET_LOTE.VAL_FRAC,1,PROD.DESC_UNID_PRESENT,PROD_LOC.UNID_VTA)),0,24) PRESENTACION,
             SUBSTR(TRIM(LAB.NOM_LAB), 0, 20) LABORATORIO
      FROM TMP_VTA_PEDIDO_VTA_DET DET,
           TMP_VTA_INSTITUCIONAL_DET DET_LOTE,
           LGT_PROD_LOCAL PROD_LOC,
           LGT_PROD       PROD,
           LGT_LAB        LAB
      WHERE DET.COD_GRUPO_CIA = DET_LOTE.COD_GRUPO_CIA
      AND DET.COD_LOCAL = DET_LOTE.COD_LOCAL
      AND DET.NUM_PED_VTA = DET_LOTE.NUM_PED_VTA
      AND DET.SEC_PED_VTA_DET = DET_LOTE.SEC_PED_VTA_DET
      AND DET.COD_GRUPO_CIA = PROD_LOC.COD_GRUPO_CIA
      AND DET.COD_LOCAL = PROD_LOC.COD_LOCAL
      AND DET.COD_PROD = PROD_LOC.COD_PROD
      AND PROD_LOC.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
      AND PROD_LOC.COD_PROD = PROD.COD_PROD
      AND LAB.COD_LAB = PROD.COD_LAB
      AND DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND DET.COD_LOCAL = cCodLocal_in
      AND DET.NUM_PED_VTA = cNumPedVta_in
      ORDER BY DET.SEC_PED_VTA_DET)A;
      
    filaDetalleProforma curDetalleProforma%ROWTYPE;
    vIdDoc IMPRESION_TERMICA.ID_DOC%TYPE;
    vIpPc IMPRESION_TERMICA.ID_DOC%TYPE;
    vValor VARCHAR2(1000);
    vNumPedDiario TMP_VTA_PEDIDO_VTA_CAB.NUM_PED_DIARIO%TYPE;
    vNombreCliente TMP_VTA_PEDIDO_VTA_CAB.NOM_CLI_PED_VTA%TYPE;
    vMontoPedido VARCHAR2(20);
    vNomLocal PBL_LOCAL.DESC_CORTA_LOCAL%TYPE;
    cursorComprobante FARMACURSOR;
    vFechaPedido TMP_VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE;
    vCantProductos NUMBER := 0;
  BEGIN
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc := FARMA_PRINTER.F_GET_IP_SESS;
  
    SELECT CAB.NUM_PED_DIARIO,
           CAB.FEC_PED_VTA,
           NVL(TRIM(CAB.NOM_CLI_PED_VTA),' '),
           TRIM(TO_CHAR(CAB.VAL_NETO_PED_VTA,'999,999,990.00'))
    INTO vNumPedDiario,
         vFechaPedido,
         vNombreCliente,
         vMontoPedido
    FROM TMP_VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;
    
    SELECT L.DESC_CORTA_LOCAL
    INTO vNomLocal
    FROM PBL_LOCAL L
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
    AND L.COD_LOCAL = cCodLocal_in;
    
                                 
    FARMA_PRINTER.P_AGREGA_BARCODE_CODE39(vIdDoc_in => vIdDoc,
                                          vIpPc_in => vIpPc,
                                          vValor_in => vNumPedDiario);
                                          
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                         
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => 'COMANDA DE DESPACHO - COPIA CLIENTE',
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                        
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => cCodLocal_in||'-'||vNomLocal,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN); 
 
                                                           
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
    
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc,
                                         vValor_in => 'FECHA PROFORMA : ',
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => TO_CHAR(vFechaPedido,'DD/MM/YYYY HH24:MI:SS'),
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT); 
    
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc,
                                         vValor_in => 'CLIENTE : ',
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => vNombreCliente,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT);

    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => 'DETALLE DE PEDIDO',
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN);
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, 
                                                 vIpPc_in => vIpPc,
                                                 vCaracter => '-',
                                                 vTamanio_in => FARMA_PRINTER.TAMANIO_0);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => ' '||RPAD('CODIGO',8,' ')||RPAD('DESCRIPCION',18,' ')||RPAD('CANT.',7,' ')||RPAD('PRESENTACION',12,' '),
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                 vJustifica_in => FARMA_PRINTER.JUSTIFICA_NO);
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, 
                                                 vIpPc_in => vIpPc,
                                                 vCaracter => '-',
                                                 vTamanio_in => FARMA_PRINTER.TAMANIO_0);
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc);                             

    OPEN curDetalleProforma;
    LOOP
      FETCH curDetalleProforma INTO filaDetalleProforma;
      EXIT WHEN curDetalleProforma%NOTFOUND;
      
        FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                     vIpPc_in => vIpPc, 
                                     vValor_in => filaDetalleProforma.DETALLE,
                                     vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                     vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                     vJustifica_in => FARMA_PRINTER.JUSTIFICA_NO);
        vCantProductos := vCantProductos + 1;
    END LOOP;
    CLOSE curDetalleProforma;
    
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, 
                                                 vIpPc_in => vIpPc,
                                                 vCaracter => '-',
                                                 vTamanio_in => FARMA_PRINTER.TAMANIO_0);
                                                 
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc); 
    
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc,
                                         vValor_in => 'TOTAL DE PRODUCTOS : ',
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => vCantProductos,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_IZQ);

    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc,
                                         vValor_in => 'TOTAL PAGADO : ',
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => vMontoPedido,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_IZQ);
                                                              
    cursorComprobante := FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc,
                                                                         vIpPc_in => vIpPc);
    RETURN cursorComprobante;
  END;
  
  FUNCTION F_DATOS_VALIDAR_VTA_EMPRESA(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cNumPedVta_in     IN CHAR)
    RETURN FARMACURSOR IS
    vCur FARMACURSOR;
  BEGIN
    OPEN vCur FOR
      SELECT CAB.COD_LOCAL COD_LOCAL,
             CAB.NUM_PED_VTA NUM_PED_VTA,
             CAB.COD_CONVENIO COD_CONVENIO,
             NVL(CAB.COD_CLI_CONV,
                 (SELECT COD_CLIENTE
                    FROM MAE_CONVENIO
                   WHERE COD_CONVENIO = CAB.COD_CONVENIO)) COD_CLIENTE,
             TDOC.COD_TIPODOC TIPO_DOC,
             TO_CHAR(CAB.VAL_NETO_PED_VTA, '999,999,999.90') MONTO,
             '1' VTAFIN
        FROM MAE_TIPO_COMP_PAGO_BTLMF TDOC, TMP_VTA_PEDIDO_VTA_CAB CAB
       WHERE CAB.TIP_COMP_PAGO = TDOC.TIP_COMP_PAGO
         AND CAB.COD_GRUPO_CIA = cCodGrupoCia_in
         AND CAB.COD_LOCAL = cCodLocal_in
         AND CAB.NUM_PED_VTA = cNumPedVta_in;
    RETURN vCur;
  END;
  
  FUNCTION F_LISTA_PISOS_DESPACHO
    RETURN FARMACURSOR IS
    vCur FARMACURSOR;
  BEGIN
    OPEN vCur FOR
        SELECT VAL || 'Ã' ||
               'PISO ' ||VAL
        FROM (
              SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL 
                      FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                         REPLACE( REPLACE((REPLACE((
              SELECT P.LLAVE_TAB_GRAL
                  FROM PBL_TAB_GRAL P
                  WHERE P.ID_TAB_GRAL = 599
                  ),'&','Ã')),'<','Ë'),',','</e><e>') ||'</e></coll>'),'/coll/e'))) xt
       );
   RETURN vCur;
    
  END;
  
  PROCEDURE P_ANULAR_PROFORMA(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cNumProforma_in IN CHAR,
                              cUsuario_in     IN CHAR)
  IS
    vEstadoPedido TMP_VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
  BEGIN
    SELECT C.EST_PED_VTA
    INTO  vEstadoPedido
    FROM TMP_VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
    AND C.COD_LOCAL = cCodLocal_in
    AND C.NUM_PED_VTA = cNumProforma_in;
    
    IF vEstadoPedido = EST_PED_PEND_VERIFICA THEN
    
      /*UPDATE TMP_VTA_PEDIDO_VTA_CAB C
      SET C.NUM_PED_VTA_ORIGEN = cNumProforma_in
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND C.COD_LOCAL = cCodLocal_in
      AND C.NUM_PED_VTA = cNumProforma_in;*/
      
      PTOVENTA_PROFORMA.P_RETORNA_STOCK_TEMPORAL(ccodgrupocia_in => cCodGrupoCia_in,
                                                 ccodlocal_in => cCodLocal_in,
                                                 cnumpedido_in => cNumProforma_in,
                                                 cusumodprodlocal_in => cUsuario_in);
    END IF;
    
    UPDATE TMP_VTA_PEDIDO_VTA_CAB C
    SET C.NUM_PED_VTA_ORIGEN = NULL,
        C.EST_PED_VTA = EST_PED_ANULADO,
        C.FEC_MOD_PED_VTA_CAB = SYSDATE,
        C.USU_MOD_PED_VTA_CAB = cUsuario_in
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
    AND C.COD_LOCAL = cCodLocal_in
    AND C.NUM_PED_VTA = cNumProforma_in;
                                                   
  END;
  
  FUNCTION F_IMPR_VOUCHER_PROFORMA(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cNumPedVta_in     IN CHAR)
    RETURN FARMACURSOR IS                                      
   
    CURSOR curDetalleProforma IS
      SELECT '  '||
             RPAD(A.CODIGO, 7, ' ') || 
             RPAD(SUBSTR(A.DESCRIPCION,0,18), 18, ' ') ||' '||
             RPAD(A.CANTIDAD, 7, ' ') || ' ' ||
             RPAD(A.PRESENTACION,12,' ') ||
             '  '||
             RPAD(SUBSTR(A.LABORATORIO,0,20), 20, ' ') /*||' '||
             RPAD(A.NUM_LOTE, 20, ' ')*/
             DETALLE
      
      FROM (
      SELECT DET.COD_PROD CODIGO,
             PROD.DESC_PROD DESCRIPCION,
             DECODE(DET.VAL_FRAC,1,DET.CANT_ATENDIDA||'',DET.CANT_ATENDIDA||'/'||DET.VAL_FRAC) CANTIDAD,
             SUBSTR(TRIM(DECODE(DET.VAL_FRAC,1,PROD.DESC_UNID_PRESENT,PROD_LOC.UNID_VTA)),0,24) PRESENTACION,
             ' '/*SUBSTR(TRIM(LAB.NOM_LAB), 0, 20)*/ LABORATORIO
      FROM TMP_VTA_PEDIDO_VTA_DET DET,
           LGT_PROD_LOCAL PROD_LOC,
           LGT_PROD       PROD,
           LGT_LAB        LAB
      WHERE DET.COD_GRUPO_CIA = PROD_LOC.COD_GRUPO_CIA
      AND DET.COD_LOCAL = PROD_LOC.COD_LOCAL
      AND DET.COD_PROD = PROD_LOC.COD_PROD
      AND PROD_LOC.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
      AND PROD_LOC.COD_PROD = PROD.COD_PROD
      AND LAB.COD_LAB = PROD.COD_LAB
      AND DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND DET.COD_LOCAL = cCodLocal_in
      AND DET.NUM_PED_VTA = cNumPedVta_in
      ORDER BY DET.SEC_PED_VTA_DET)A;
      
    filaDetalleProforma curDetalleProforma%ROWTYPE;
    vIdDoc IMPRESION_TERMICA.ID_DOC%TYPE;
    vIpPc IMPRESION_TERMICA.ID_DOC%TYPE;
    vValor VARCHAR2(1000);
    vNumPedDiario TMP_VTA_PEDIDO_VTA_CAB.NUM_PED_DIARIO%TYPE;
    vNombreCliente TMP_VTA_PEDIDO_VTA_CAB.NOM_CLI_PED_VTA%TYPE;
    vMontoPedido VARCHAR2(20);
    vNomLocal PBL_LOCAL.DESC_CORTA_LOCAL%TYPE;
    cursorComprobante FARMACURSOR;
    vFechaPedido TMP_VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE;
    vCantProductos NUMBER := 0;
  BEGIN
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc := FARMA_PRINTER.F_GET_IP_SESS;
  
    SELECT CAB.NUM_PED_DIARIO,
           CAB.FEC_PED_VTA,
           NVL(TRIM(CAB.NOM_CLI_PED_VTA),' '),
           TRIM(TO_CHAR(CAB.VAL_NETO_PED_VTA,'999,999,990.00'))
    INTO vNumPedDiario,
         vFechaPedido,
         vNombreCliente,
         vMontoPedido
    FROM TMP_VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;
    
    SELECT L.DESC_CORTA_LOCAL
    INTO vNomLocal
    FROM PBL_LOCAL L
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
    AND L.COD_LOCAL = cCodLocal_in;
    
                                 
    FARMA_PRINTER.P_AGREGA_BARCODE_CODE39(vIdDoc_in => vIdDoc,
                                          vIpPc_in => vIpPc,
                                          vValor_in => vNumPedDiario);
                                          
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                         
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => 'PROFORMA DE PEDIDO',
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                        
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => cCodLocal_in||'-'||vNomLocal,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN); 
 
                                                           
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
    
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc,
                                         vValor_in => 'FECHA PROFORMA : ',
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => TO_CHAR(vFechaPedido,'DD/MM/YYYY HH24:MI:SS'),
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT); 
    
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc,
                                         vValor_in => 'CLIENTE : ',
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => vNombreCliente,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT);

    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => 'DETALLE DE PEDIDO',
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN);
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, 
                                                 vIpPc_in => vIpPc,
                                                 vCaracter => '-',
                                                 vTamanio_in => FARMA_PRINTER.TAMANIO_0);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => ' '||RPAD('CODIGO',8,' ')||RPAD('DESCRIPCION',18,' ')||RPAD('CANT.',7,' ')||RPAD('PRESENTACION',12,' '),
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                 vJustifica_in => FARMA_PRINTER.JUSTIFICA_NO);
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, 
                                                 vIpPc_in => vIpPc,
                                                 vCaracter => '-',
                                                 vTamanio_in => FARMA_PRINTER.TAMANIO_0);
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc);                             

    OPEN curDetalleProforma;
    LOOP
      FETCH curDetalleProforma INTO filaDetalleProforma;
      EXIT WHEN curDetalleProforma%NOTFOUND;
      
        FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                     vIpPc_in => vIpPc, 
                                     vValor_in => filaDetalleProforma.DETALLE,
                                     vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                     vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                     vJustifica_in => FARMA_PRINTER.JUSTIFICA_NO);
        vCantProductos := vCantProductos + 1;
    END LOOP;
    CLOSE curDetalleProforma;
    
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, 
                                                 vIpPc_in => vIpPc,
                                                 vCaracter => '-',
                                                 vTamanio_in => FARMA_PRINTER.TAMANIO_0);
                                                 
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc); 
    
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc,
                                         vValor_in => 'TOTAL DE PRODUCTOS : ',
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => vCantProductos,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_IZQ);

    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc,
                                         vValor_in => 'TOTAL A PAGAR : ',
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => vMontoPedido,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_IZQ);
                                                              
    cursorComprobante := FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc,
                                                                         vIpPc_in => vIpPc);
    RETURN cursorComprobante;
  END;
END;
/
