CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_VTA" AS

  TYPE FarmaCursor IS REF CURSOR;

  ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';

  IND_ORIGEN_PROD CONSTANT CHAR(1) := '1';
  IND_ORIGEN_SUST CONSTANT CHAR(1) := '2';
  IND_ORIGEN_ALTE CONSTANT CHAR(1) := '3';
  IND_ORIGEN_COMP CONSTANT CHAR(1) := '4';
  IND_ORIGEN_OFER CONSTANT CHAR(1) := '5';
  IND_ORIGEN_REGA CONSTANT CHAR(1) := '6';

  IND_TIPO_REDONDEDO_CASO1 CONSTANT CHAR(1) := '1'; --JCHAVEZ 29102009
  IND_TIPO_REDONDEDO_CASO2 CONSTANT CHAR(1) := '2'; --JCHAVEZ 29102009
  IND_TIPO_REDONDEDO_CASO3 CONSTANT CHAR(1) := '3'; --JCHAVEZ 29102009
  
   COD_IND_EMI_CUPON NUMBER(3) := 679;                    --ASOSA - 10/04/2015 - NECUPYAYAYAYA

  --Descripcion: Obtiene el listado de los productos
  --Fecha       Usuario		Comentario
  --14/02/2006  LMESIA     Creación
  --21/11/2007 dubilluz modificacion
  --16/04/2008  ERIOS     DEPRECATED: PTOVENTA_VTA_LISTA
  FUNCTION VTA_LISTA_PROD(cCodGrupoCia_in IN CHAR,
  		   				  cCodLocal_in	  IN CHAR)
	RETURN FarmaCursor;

  --Descripcion: Obtiene Info del Producto para ser mostrado en el listado de productos
  --Fecha       Usuario		Comentario
  --14/02/2006  LMESIA     	Creacion
  --29/05/2008  ERIOS     	Modificacion: cIndVerificaSug
  FUNCTION VTA_OBTIENE_INFO_PROD(cCodGrupoCia_in IN CHAR,
  		   				  		           cCodLocal_in	 IN CHAR,
								                 cCodProd_in	 IN CHAR,
                                 cIndVerificaSug IN CHAR DEFAULT 'N')
	RETURN FarmaCursor;

  --Descripcion: Obtiene Info Complementaria del Producto para ser mostrado en el detalle de Producto
  --Fecha       Usuario		Comentario
  --14/02/2006  LMESIA     	Creacion
  --29/05/2008  ERIOS     	Modificacion: cIndVerificaSug
  FUNCTION VTA_OBTIENE_INFO_COMPL_PROD(cCodGrupoCia_in IN CHAR,
  		   				  		 	               cCodLocal_in	   IN CHAR,
								 	                     cCodProd_in	   IN CHAR,
                                       cIndVerificaSug IN CHAR DEFAULT 'N')
	RETURN FarmaCursor;

  --Descripcion: Obtiene Principios Activos por Producto
  --Fecha       Usuario		Comentario
  --15/02/2006  LMESIA     	Creación
  FUNCTION VTA_OBTIENE_PRINC_ACT_PROD(cCodGrupoCia_in IN CHAR,
								 	  cCodProd_in	  IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Obtiene Stock del Producto y bloquea el registro
  --Fecha       Usuario		Comentario
  --15/02/2006  LMESIA     	Creacion
  --06/06/2008  ERIOS       Modificacion:
  FUNCTION VTA_OBTIENE_STK_PROD_FORUPDATE(cCodGrupoCia_in IN CHAR,
  		   				  		 	   	  cCodLocal_in	  IN CHAR,
								 	   	  cCodProd_in	  IN CHAR,
                        cValFracVta_in IN NUMBER
                        )
  	RETURN FarmaCursor;

  --Descripcion: Actualiza Stock Comprometido del Producto
  --Fecha       Usuario		Comentario
  --15/02/2006  LMESIA     	Creacion
  --29/05/2008  ERIOS      	Modificacion: cValFracVta_in

  --Descripcion: Graba la Cabecera del Pedido
  --Fecha       Usuario		Comentario
  --17/02/2006  LMESIA     	Creación
  --22/03/2007  LREQUE      Modificación
PROCEDURE VTA_GRABAR_PEDIDO_VTA_CAB(cCodGrupoCia_in 	 	   IN CHAR,
                                   	  cCodLocal_in    	 	   IN CHAR,
                								   	  cNumPedVta_in   	 	   IN CHAR,
                									    cCodCliLocal_in		     IN CHAR,
                									    cSecMovCaja_in		     IN CHAR,
                								   	  nValBrutoPedVta_in 	   IN NUMBER,
                								   	  nValNetoPedVta_in  	   IN NUMBER,
                								   	  nValRedondeoPedVta_in  IN NUMBER,
                  									  nValIgvPedVta_in 		   IN NUMBER,
                  									  nValDctoPedVta_in 	   IN NUMBER,
                  									  cTipPedVta_in			     IN CHAR,
                  									  nValTipCambioPedVta_in IN NUMBER,
                  									  cNumPedDiario_in		   IN CHAR,
                  									  nCantItemsPedVta_in	   IN NUMBER,
                  									  cEstPedVta_in			     IN CHAR,
                  									  cTipCompPago_in		     IN CHAR,
                  									  cNomCliPedVta_in		   IN CHAR,
                  									  cDirCliPedVta_in		   IN CHAR,
                  									  cRucCliPedVta_in		   IN CHAR,
                  									  cUsuCreaPedVtaCab_in	 IN CHAR,
                  									  cIndDistrGratuita_in	 IN CHAR,
                                      cIndPedidoConvenio_in  IN CHAR,
                                      cCodConvenio_in        IN CHAR DEFAULT NULL,
                                      cCodUsuLocal_in        IN CHAR DEFAULT NULL,
                                            --dubilluz 09.06.0211
                                            cIndUsoEfectivo_in IN CHAR DEFAULT 'N',
                                            cIndUsoTarjeta_in IN CHAR DEFAULT 'N',
                                            cCodForma_Tarjeta_in IN CHAR DEFAULT 'N',
                                            cColegioMedico_in in char default '0',
                                           --FRAMIREZ 10.04.2012
                                      cCodCliente_in IN CHAR DEFAULT NULL ,
                                      cIndConvBTLMF  IN vta_pedido_vta_cab.ind_conv_btl_mf%TYPE DEFAULT NULL
                                      -- dubilluz 20.11.2013
                                      ,cCodSolicitud in varchar2 default 'X',
                                      vReferencia in varchar2 default '.',
                                      nPctBeneficiario in VTA_PEDIDO_VTA_CAB.PCT_BENEFICIARIO%TYPE default null,
                                      vIndCompMAnual in varchar2 default 'N',
                                      vTIP_COMP_MANUAL_in in varchar2 default '',
                                      vSERIE_COMP_MANUAL_in in varchar2 default '',
                                      vNUM_COMP_MANUAL_in in varchar2 default '',
                                      -- KMONCADA 2015.02.16 PROGRAMA PTOS
                                      cNroTarjeta_in      IN  VTA_PEDIDO_VTA_CAB.NUM_TARJ_PUNTOS%TYPE DEFAULT NULL,
                                      cIdTransaccion_in   IN  VTA_PEDIDO_VTA_CAB.ID_TRANSACCION%TYPE DEFAULT NULL,
                                      cPtoInicial_in      IN  VTA_PEDIDO_VTA_CAB.PT_INICIAL%TYPE DEFAULT NULL,
                                      cEstaTrxOrbis_in    IN  VTA_PEDIDO_VTA_CAB.EST_TRX_ORBIS%TYPE DEFAULT 'N'

                                            );
 --Descripcion: Graba el Detalle del Pedido
  --Fecha       Usuario		Comentario
  --17/02/2006  LMESIA     	Creación
  --29/02/2008  DUBILLUZ    Modificacion
PROCEDURE VTA_GRABAR_PEDIDO_VTA_DET(cCodGrupoCia_in 	 	  IN CHAR,
                                      cCodLocal_in    	 	  IN CHAR,
                            				  cNumPedVta_in   	 	  IN CHAR,
                            				  nSecPedVtaDet_in		  IN NUMBER,
                            				  cCodProd_in			      IN CHAR,
                            				  nCantAtendida_in	 	  IN NUMBER,
                            				  nValPrecVta_in 	 	    IN NUMBER,
                            				  nValPrecTotal_in  	  IN NUMBER,
                            				  nPorcDcto1_in  		    IN NUMBER,
                            				  nPorcDcto2_in  		    IN NUMBER,
                            				  nPorcDcto3_in  		    IN NUMBER,
                            				  nPorcDctoTotal_in  	  IN NUMBER,
                            				  cEstPedVtaDet_in		  IN CHAR,
                            				  nValTotalBono_in 	 	  IN NUMBER,
                            				  nValFrac_in			      IN NUMBER,
                            				  nSecCompPago_in		    IN NUMBER,
                            				  cSecUsuLocal_in 		  IN CHAR,
                            				  nValPrecLista_in 	 	  IN NUMBER,
                            				  nValIgv_in  	 		    IN NUMBER,
                            				  cUnidVta_in			      IN CHAR,
                                      cNumTelRecarga_in     IN CHAR,--agregado x luis mesia 08/01/2007
                            				  cUsuCreaPedVtaDet_in	IN CHAR,
                                      nValPrecPub           IN NUMBER,
                                      cCodProm_in           IN CHAR,
                                      cIndOrigen_in IN CHAR,
                                      nCantxDia_in IN NUMBER,
                                      nCantDias_in IN NUMBER,
                                       nAhorroPack IN NUMBER DEFAULT NULL
                                      );


  --Descripcion: Obtiene la ultima fecha de modificacion del pedido
  --Fecha       Usuario		Comentario
  --20/02/2006  LMESIA     	Creación
  FUNCTION VTA_OBTIENE_FEC_MOD_NUMERA_PED(cCodGrupoCia_in IN CHAR,
								 	  	  cCodLocal_in	  IN CHAR,
										  cCodNumera_in	  IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Obtiene la ultima fecha de modificacion del pedido
  --Fecha       Usuario		Comentario
  --20/02/2006  LMESIA     	Creacion
  --29/05/2008  ERIOS       DEPRECATED
  PROCEDURE VTA_ACTUALIZA_STK_PROD(cCodGrupoCia_in 		IN CHAR,
								   cCodLocal_in	  		IN CHAR,
								   cCodProd_in	  		IN CHAR,
								   nCantStk_in	  		IN NUMBER,
								   cUsuModProdLocal_in	IN CHAR);

  --Descripcion: Obtiene el ultimo pedido diario generado por un vendedor
  --Fecha       Usuario		Comentario
  --21/02/2006  LMESIA     	Creación
  FUNCTION VTA_OBTIENE_ULTIMO_PED_DIARIO(cCodGrupoCia_in IN CHAR,
								 	  	 cCodLocal_in	 IN CHAR,
										 cSecUsuLocal_in IN CHAR)
  	RETURN CHAR;

  --Descripcion: Obtiene el listado de los productos filtrado
  --Fecha       Usuario		Comentario
  --22/02/2006  LMESIA     Creación
  --21/11/2007 dubilluz modificacion
  --16/04/2008  ERIOS     DEPRECATED: PTOVENTA_VTA_LISTA
  FUNCTION VTA_LISTA_PROD_FILTRO(cCodGrupoCia_in IN CHAR,
  		   				  		 cCodLocal_in	 IN CHAR,
								 cTipoFiltro_in  IN CHAR,
  		   						 cCodFiltro_in 	 IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Obtiene las acciones terapeuticas de la tabla relacion acc terap para los productos complementarios
  --Fecha       Usuario		Comentario
  --27/02/2006  LMESIA     Creación
  FUNCTION VTA_OBTIENE_REL_PRINC_ACT_PROD(cCodGrupoCia_in IN CHAR,
								 	  	  cCodLocal_in	  IN CHAR,
										  cCodProd_in	  IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de los productos complementarios por producto y accion terapeutica
  --Fecha       Usuario		Comentario
  --27/02/2006  LMESIA     Creación
  FUNCTION VTA_LISTA_PROD_COMPLEMENTARIOS(cCodGrupoCia_in  IN CHAR,
								 	  	  cCodLocal_in	   IN CHAR,
										  cCodProd_in	   IN CHAR,
										  cCodPrinctAct_in IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Obtiene el codigo del producto a traves de su codigo de barras
  --Fecha       Usuario		Comentario
  --16/03/2006  LMESIA     Creación
  FUNCTION VTA_REL_COD_BARRA_COD_PROD(cCodGrupoCia_in IN CHAR,
								 	  cCodBarra_in	  IN CHAR)
    RETURN CHAR;


  --Descripcion: Obtiene las acciones terapeuticas de un producto
  --Fecha       Usuario		Comentario
  --03/05/2006  MHUAYTA     Creación
	FUNCTION VTA_OBTIENE_ACC_TERAP_PROD(cCodGrupoCia_in IN CHAR,
								 	    cCodProd_in	    IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: Agrega un registro cuando falta el stock es 0 en un producto
  --Fecha       Usuario		Comentario
  --15/06/2006  PAULO     Creación
  PROCEDURE VTA_FALTA_CERO(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in IN CHAR,
                           cCodProd_in IN CHAR,
                           cSecUsuLocal_in IN CHAR,
                           cUsuCrea_in IN CHAR);

  --Descripcion: Carga combo para la busqueda de medicos
  --Fecha       Usuario		Comentario
  --23/06/2006  PAULO     Creación
  FUNCTION vta_combo_medicos RETURN Farmacursor;

  --Descripcion: Carga la lista de la busqueda de medicos
  --Fecha       Usuario		Comentario
  --23/06/2006  PAULO     Creación
  FUNCTION vta_busca_medico(cCodigo_in IN CHAR,
                              cMatriculaApe_in IN CHAR,
                             -- cApellido_in  IN CHAR,
                              cTipoBusqueda_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Inserta en la tabla de ventas de medicos el codigo  matrucla del medico
  --             Asociado al nuemero de pedido de venta
  --Fecha       Usuario		Comentario
  --26/06/2006  PAULO     Creación
  PROCEDURE vta_agrega_medico_vta(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  cNumPedVta_in IN CHAR,
                                  cCodApmIN_in IN CHAR,
                                  cMatricula_in IN CHAR,
                                  cUsuCrea_in   IN CHAR);

  --Descripcion: Inserta en la tabla de ventas de medicos el codigo  matrucla del medico
  --             Asociado al nuemero de pedido de venta
  --Fecha       Usuario		Comentario
  --26/06/2006  PAULO     Creación
  --12/12/2006  ERIOS     Modificación:Graba NumPedRec
  PROCEDURE vta_agrega_medico_vta(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  cNumPedVta_in IN CHAR,
                                  cNumPedRec_in IN CHAR,
                                  cUsuCrea_in   IN CHAR);

  --Descripcion: Graba cabecera de una receta
  --Fecha       Usuario		Comentario
  --07/12/2006  ERIOS     Creación
  PROCEDURE VTA_GRABAR_PED_RECETA_CAB(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
            cNumPedVta_in IN CHAR,cCodApmIN_in IN CHAR,cMatricula_in IN CHAR,
						nValBrutoPedVta_in IN NUMBER,nValNetoPedVta_in IN NUMBER,
						nValRedondeoPedVta_in IN NUMBER,nValIgvPedVta_in IN NUMBER,
						nValDctoPedVta_in IN NUMBER,nValTipCambioPedVta_in IN NUMBER,
						nCantItemsPedVta_in IN NUMBER,cEstPedVta_in IN CHAR,
						cUsuCreaPedVtaCab_in IN CHAR);

  --Descripcion: Graba detalle de una receta
  --Fecha       Usuario		Comentario
  --07/12/2006  ERIOS     Creación
  PROCEDURE VTA_GRABAR_PED_RECETA_DET(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
						cNumPedVta_in IN CHAR,nSecPedVtaDet_in IN NUMBER,cCodProd_in IN CHAR,
					  nCantAtendida_in IN NUMBER,nValPrecVta_in IN NUMBER,nValPrecTotal_in IN NUMBER,
						nPorcDcto1_in IN NUMBER,nPorcDcto2_in IN NUMBER,nPorcDcto3_in IN NUMBER,
						nPorcDctoTotal_in IN NUMBER,cEstPedVtaDet_in IN CHAR,
						nValFrac_in IN NUMBER,nValPrecLista_in IN NUMBER,nValIgv_in IN NUMBER,
						cUnidVta_in IN CHAR,cUsuCreaPedVtaDet_in IN CHAR);

  --Descripcion: Obtiene info de producto virtual
  --Fecha       Usuario		Comentario
  --24/01/2007  LMESIA    Creación
  FUNCTION VTA_OBTIENE_INFO_PROD_VIRTUAL(cCodGrupoCia_in IN CHAR,
								 	                       cCodProd_in	   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Obtiene el valor para poder ver el stock de los locales
  --             mas cercanos al indicado
  --Fecha       Usuario		    Comentario
  --31/07/2007  pameghino     Creación
  FUNCTION VTA_OBT_IND_VER_STK_LOCALES(cCodGrupoCia_in IN CHAR,
								 	                         cCodLocal_in	   IN CHAR)
   RETURN CHAR;

  --Descripcion: Verifica si se puede llevar producto de regalo por el encarte
  --Fecha       Usuario		Comentario
  --09/04/2008  dubilluz   Creación
  FUNCTION VTA_PERMITE_PROD_REGALO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in	   IN CHAR,
                                   cIpPc_in        IN CHAR,
                                   cCodEncarte_in  IN CHAR DEFAULT '00001',
                                   cProdVentaPedido_in IN VARCHAR2)
  RETURN FarmaCursor;

  --Descripcion: Verifica si se puede llevar producto de regalo por el encarte
  --Fecha       Usuario		Comentario
  --09/04/2008  dubilluz   Creación
  PROCEDURE VTA_GRABAR_PED_REGALO_DET(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in	   IN CHAR,
                                    cNumPedVta_in    IN CHAR,
                                    cSecProdDet_in   IN NUMBER,
                                    cCodProd_in      IN CHAR,
                                    cCantAtend_in    IN NUMBER,
                                    cValPredVenta_in IN CHAR,
                                    cSecUsu_in       IN CHAR,
                                    cLoginUsu_in     IN CHAR);


  --Descripcion: Verifica si se puede llevar uno o mas cupones
  --Fecha       Usuario		Comentario
  --10/04/2008  dubilluz   Creación
  --11/04/2008  dubilluz   Modificacion
  FUNCTION VTA_PERMITE_CUPON(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in	   IN CHAR,
                             cIpPc_in        IN CHAR,
                             cNumPedVta_in   IN CHAR,
                             cCodCupon_in    IN CHAR  DEFAULT '00001',
							 cProdVentaPedido_in IN VARCHAR2)
  RETURN FarmaCursor;

  --Descripcion: Graba el pedido por cupon
  --Fecha       Usuario		Comentario
  --10/04/2008  dubilluz   Creación
  PROCEDURE VTA_GRABAR_PED_CUPON(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in	     IN CHAR,
                                 cNumPedVta_in      IN CHAR,
                                 cCodCupon_in       IN CHAR,
                                 cCantidad_in       IN NUMBER,
                                 cLoginUsu_in       IN CHAR,
                                 cTipo              IN CHAR,
                                 nMontConsumoCamp_in IN CHAR);

  FUNCTION VTA_OBTIENE_PROD_SUG(cCodGrupoCia_in IN CHAR,
								 	              cCodLocal_in    IN CHAR,
                                cCodProd_in     IN CHAR,
                                cCantVta_in     IN NUMBER)
  RETURN FarmaCursor;

 /* --Descripcion: Valida el cupon
  --Fecha       Usuario		Comentario
  --03/07/2008  ERIOS     Creacion
 FUNCTION VERIFICA_DATOS_CUPON(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cCadenaCupon_in IN CHAR,cIndMultiUso_in IN CHAR default 'N')
  RETURN FarmaCursor;
*/
  --Descripcion: Verifica que el prod este asociado a la campanha
  --Fecha       Usuario		Comentario
  --03/07/2008  ERIOS     Creacion
  FUNCTION VERIFICA_CAMP_PROD(cCodGrupoCia_in IN CHAR,cCodCamp_in IN CHAR,
  cCodProd_in IN CHAR)
  RETURN CHAR;

  --Descripcion: Se graba los cupones de uso.
  --Fecha       Usuario		Comentario
  --04/07/2008  ERIOS     Creacion
  PROCEDURE GRABA_CUPON_PEDIDO_USO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cNumPedVta_in IN CHAR, cCodCupon_in IN CHAR, cCodCamp_in IN CHAR, vIndUso_in in CHAR,
  vIdUsu_in IN VARCHAR2);

  --Descripcion: Procesa los cupones de descuento.
  --Fecha       Usuario		Comentario
  --07/07/2008  ERIOS     Creacion
  PROCEDURE PROCESA_CAMP_PED_CUPON(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cNumPedVta_in IN CHAR,vIdUsu_in IN VARCHAR2);

  --Descripcion: Determina el mejor descuento.
  --Fecha       Usuario		Comentario
  --07/07/2008  ERIOS     Creacion
  FUNCTION DETERMINA_USO_CUPON(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cNumPedVta_in IN CHAR,cCodProd_in IN CHAR,cCodCampana_in in char) RETURN VARCHAR2;

  --Descripcion: GENERA LOS CUPONES DEL PEDIDO
  --Fecha       Usuario		Comentario
  --15/07/2008  dubilluz   Creación
  --04/10/2008  Fveliz   Modificación
  --05/10/2008  Joliva   Modificación
  PROCEDURE VTA_PROCESO_CAMPANA_CUPON(cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in	    IN CHAR,
                                      cNumPedVta_in    IN CHAR,
                                      cLoginUsu_in     IN CHAR,
                                      cTipo            IN CHAR,
                                      pDniCli           IN CHAR DEFAULT NULL);

  --Descripcion: obtiene informacin de la campaña
  --Fecha       Usuario		Comentario
  --23/07/2008  JCORTEZ   Creación
  FUNCTION VTA_OBTIENE_INFO_CAMP(cCodGrupoCia_in IN CHAR,
                                cCodCamp_in     IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: obtiene indicador uso de la campaña
  --Fecha       Usuario		Comentario
  --15/08/2008  JCORTEZ   Creación
  FUNCTION VTA_OBTIENE_IND_CAMP(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cCodCupon_in    IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: Obtiene total ahorrado por pedido.
  --Fecha       Usuario		Comentario
  --20/08/2008  JCORTEZ     Creacion
  FUNCTION OBTIENE_TOTAL_AHORRO(cCodCia_in IN CHAR,
                                cTotalPedido_in IN NUMBER)
  RETURN CHAR;

    --Descripcion: Obtiene total ahorrado por pedido.
  --Fecha       Usuario		Comentario
  --20/08/2008  JCORTEZ     Creacion
  PROCEDURE GRABA_PROD_CAMP(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            cCodProd_in IN CHAR,
                            cValPrecTotal_in IN NUMBER);

  --Descripcion: Obtiene total ahorrado por pedido.
  --Fecha       Usuario		Comentario
  --20/08/2008  JCORTEZ     Creacion
  PROCEDURE GRABA_CAMP(cCodGrupoCia_in IN CHAR,
                       cCodLocal_in IN CHAR,
                       cCodCamp IN CHAR,
                       cCodCupon IN CHAR);

  --Descripcion: Se valida el codigo de barra en el local
  --Fecha       Usuario		Comentario
  --28/08/2008  JCORTEZ     Creacion
  FUNCTION VALIDA_COD_BARRA(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cCadena_in      IN CHAR)
  RETURN CHAR;

   --Descripcion: Actualiza los descuentos del pedido venta
  --Fecha       Usuario		Comentario
  --09/10/2008  DVELIZ     Creacion
PROCEDURE VTA_P_UPDATE_DET_PED_VTA( cNumPedVta IN CHAR,
                                                                                  cCodLocal IN CHAR,
                                                                                  cCodGrupoCia IN CHAR,
                                                                                  cCodProd IN CHAR,
                                                                                  cCodCamp IN CHAR,
                                                                                  cPorcDcto_1 IN NUMBER,
                                                                                  cAhorro  IN NUMBER,
                                                                                  cPorcDctoCalc IN NUMBER,
                                                                                  cSecPedVtaDet IN NUMBER);


--Descripcion: obtengo el  dcto de los productos de un pedido
  --Fecha       Usuario		Comentario
  --10/10/2008  DVELIZ     Creacion
 /* FUNCTION VTA_F_CUR_OBT_DCTO_PROD(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cCadena_in      IN CHAR)
  RETURN FarmaCursor;*/


  -- Descripcion: validar que el monto de venta total sea consistente entre
  --              la cabecera y el detalle
  --Fecha       Usuario		Comentario
  --04/11/2008  JCALLO     Creacion
   PROCEDURE VTA_P_VALIDAR_VALOR_VTA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in IN CHAR,
                                    cNumPedVta_in IN CHAR);

   -- Descripcion: OBTIENE EL PRECIO FINAL QUE APLICARA
   --              la cabecera y el detalle
   --Fecha       Usuario		Comentario
   --05/02/2009  JCALLO     Creacion
   FUNCTION VTA_F_GET_PRECIO_FINAL_VTA(  cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cCodProd_in     IN CHAR,
                                         cCodCamp_in     IN CHAR,
                                         nPrecioVtaDcto_in IN NUMBER,
                                         nPrecioVta_in     IN NUMBER,
                                         nFracionVta_in    IN NUMBER)
    RETURN CHAR;


   -- Descripcion: oBTIENE EL MONTO MINIMO DESDE DONDE VALIDARA DATOS CLIENTE
   --Fecha       Usuario		Comentario
   --25/02/2009  DUBILLUZ   Creacion
    FUNCTION VTA_F_GET_MONTO_VALIDA_DATOS
    RETURN VARCHAR2;

    --Descripcion: OBTENER DATOS DE CAMPANIA -CUPON
    --FECHA      : 04/03/2009
    --OBSERVACION : para ser usado con el nuevo framework
    FUNCTION VTA_F_CURSOR_DATOS_CUPON ( cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCampCupon_in   IN CHAR,
                                        cCodCupon_in IN CHAR  default '',
                                        -- dubilluz 13.06.2011
                                        cIndUsoEfectivo_in IN CHAR,
                                        cIndUsoTarjeta_in IN CHAR,
                                        cCodForma_Tarjeta_in IN CHAR
                                       )
    RETURN FarmaCursor;

    --Descripcion: Se verifica el rol del usuario
    --Fecha       Usuario   Comentario
    --24/07/2009  JCORTEZ     Creacion
    FUNCTION VERIFICA_ROL_USU (cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in     IN CHAR,
                               vSecUsu_in       IN CHAR,
                               cCodRol_in       IN CHAR)
    RETURN CHAR;
    /*
    --Descripcion: Se obtiene cupones del cliente
    --Fecha       Usuario   Comentario
    --04/08/2009  JCORTEZ     Creacion
   FUNCTION VTA_F_CUPON_CLI (cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cDni            IN CHAR)
    RETURN FarmaCursor;
    */

  /* ***********************************************************/
  --Descripcion: Actualiza la Cabecera del Pedido para Actualizar nombre del Cliente
  --Fecha       Usuario		    Comentario
  --11/08/2009  JMIRANDA     	Creación

  PROCEDURE VTA_P_UPDATE_PEDIDO_VTA_CAB(cCodGrupoCia_in 	 	   IN CHAR,
                                   	    cCodLocal_in    	 	   IN CHAR,
                								   	    cNumPedVta_in   	 	   IN CHAR,
                									      cNomCliPedVta_in		   IN VARCHAR2,
                  									    cDirCliPedVta_in		   IN VARCHAR2,
                  									    cRucCliPedVta_in		   IN VARCHAR2);



  /* ***********************************************************/
  --Descripcion: Se obtiene mensaje de ahorro mostrado en ticket, boleta, factura.
  --Fecha       Usuario		    Comentario
  --0309/2009  JCORTEZ     	Creación
  FUNCTION OBTIENE_MENSAJE_AHORRO(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in     IN CHAR,
                               cIndFid_in       IN CHAR)
  RETURN CHAR;

    /* ***********************************************************/
  --Descripcion: Se obtiene mensaje de ticket delivery
  --Fecha       Usuario		    Comentario
  --07/09/2009  JCORTEZ     	Creación
  FUNCTION VTA_F_GET_MENS_TICKET(cCodCia_in    IN CHAR,
                                cCod_local_in  IN CHAR)
  RETURN CHAR;

  /* **************************** */
  FUNCTION VTA_F_GET_DESC_PAQUETE(cCodGrupoCia_in IN CHAR,
                                  cCodProd        IN CHAR) RETURN VARCHAR2;

  /* **************************** */
  FUNCTION VTA_F_GET_IND_COD_BARRA(cCodGrupoCia_in IN CHAR, cCodProd_in IN CHAR)
  RETURN CHAR;

  /* **************************** */
  FUNCTION VTA_F_GET_IND_SOL_ID_USU(cCodGrupoCia_in IN CHAR, cCodProd_in IN CHAR)
  RETURN CHAR;

  /* **************************** */
  FUNCTION VTA_F_GET_MENS_PROD(cCodGrupoCia_in IN CHAR, cCodProd_in IN CHAR)
  RETURN VARCHAR2;

  /* ******************************************************* */
  FUNCTION VTA_F_GET_DELIM_MENS
  RETURN VARCHAR2;

  --Descripcion: Otiene el precio redondeado según el caso registrado
  --Fecha       Usuario		Comentario
  --29/10/2009  JCHAVEZ     Creación
  FUNCTION VTA_F_CHAR_PREC_REDONDEADO(nValPrecVta_in IN NUMBER)
  RETURN CHAR;

  --Descripcion: Otiene el precio redondeado según el caso registrado
  --Fecha       Usuario		Comentario
  --29/10/2009  JCHAVEZ     Creación
  FUNCTION VTA_F_NUMBER_PREC_REDONDEADO(nValPrecVta_in IN NUMBER)
  RETURN NUMBER;

  /* ******************************************************* */

      --Descripcion: Otiene indicador para aplciar redondedo o no
  --Fecha       Usuario		Comentario
  --29/10/2009  JCHAVEZ     Creación
    FUNCTION VTA_F_CHAR_IND_DE_REDONDEADO
  RETURN CHAR;

    --Descripcion: Otiene Dirección de Matriz para imprimir en comprobantes
  --Fecha       Usuario		Comentario
  --19/01/2010  JMIRANDA  Creación
  FUNCTION VTA_F_GET_DIRECCION_MATRIZ
  RETURN VARCHAR2;

  --Descripcion: Indicador para obtener imprimir en
  --Fecha       Usuario		Comentario
  --19/01/2010  JMIRANDA  Creación
  FUNCTION VTA_F_CHAR_IND_OBT_DIR_MATRIZ
  RETURN CHAR;

  --Descripcion: Indicador para obtener imprimir en
  --Fecha       Usuario		Comentario
  --14/04/2010  JMIRANDA  Creación
  FUNCTION VTA_F_GET_IND_FID_EMI(cCodGrupoCia_in CHAR,
                                 cIndFidelizado_in CHAR,
                                 cCod_camp_cupon_in CHAR,
                                 cFechaNac_in DATE DEFAULT NULL,
                                 cSexo_in CHAR DEFAULT NULL,
                                 cIndFidEmi_in CHAR)
  RETURN CHAR;

  --Descripcion: Obtiene Indicador que imprime Correlativo
  --Fecha       Usuario		Comentario
  --22/08/2011  JMIRANDA   Creación
  /* ******************************************************* */
  FUNCTION F_GET_IND_IMP_CORRELATIVO(cCodGrupoCia_in CHAR)
  RETURN CHAR;

  --Descripcion: Obtiene Correlativo y Monto Neto Pedido
  --Fecha       Usuario		Comentario
  --22/08/2011  JMIRANDA   Creación
  /* ******************************************************* */
  FUNCTION F_GET_CORRELATIVO_MONTO_NETO(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in IN CHAR,
                                        cTipo_Comp_in IN CHAR,
                                        cMonto_Neto_in IN CHAR,
                                        cNum_Comp_Pago_in IN CHAR)
  RETURN VARCHAR2;

  FUNCTION F_GETSTOCK_PROD_REGALO(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in IN CHAR,
                                        cCod_prod IN CHAR)
  RETURN VARCHAR2;

 FUNCTION F_GETDATOS_ENCARTE_AP(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in IN CHAR,
                                        cCod_Encarte_in in char,
                                        cMonto_in in char)
  RETURN FarmaCursor;

/*------------------------------------------------------------------------------------------------------------------------
GOAL : DEVOLVER EL PRECIO MINIMO PARA CONSIDERAR AL VENDER UN PRODUCTO
DATE : 27-DIC-12
AUTH : JCT
--------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_MIN_PREC_VTA RETURN VARCHAR2;
/*-------------------------------------------------------------------------------------------------------------------------
GOAL : Devolver el Codigo Real de la Campaña a la que Pertenece la Barra
History : 18-JUL-14   TCT  Create
---------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_COD_CAMPA(cCodGrupoCia_in IN CHAR,
                            cCodCupon_in    IN CHAR) RETURN CHAR;
/**************************************************************************************************************************/

--Descripcion: Obtiene los datos adicionales de codigo un producto que tenga fraccionamiento interno adicional
--Autor:       ASOSA
--Fecha:       06/08/2014
  FUNCTION VTA_GET_VAL_ADIC_BARRA(cCodGrupoCia_in IN CHAR,
                     cCodBarra_in    IN CHAR)
  RETURN VARCHAR2;

  /**************************************************************************************************************************/

END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_VTA" AS

  --21/11/2007 dubilluz modificacion
  --16/04/2008  ERIOS     DEPRECATED: PTOVENTA_VTA_LISTA
  FUNCTION VTA_LISTA_PROD(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
  SELECT distinct(PROD.COD_PROD) || 'Ã' ||
           PROD.DESC_PROD || 'Ã' ||
           DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
           LAB.NOM_LAB || 'Ã' ||
           (PROD_LOCAL.STK_FISICO) || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
           DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',TO_CHAR(PROD.VAL_BONO_VIG,'990.00'),TO_CHAR((PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL),'990.00')) || 'Ã' ||
           --TO_CHAR(PROD.VAL_BONO_VIG,'990.00') || 'Ã' ||
           PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
           PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
           --PROD_LOCAL.VAL_PREC_LISTA || 'Ã' ||
           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
           PROD.IND_PROD_FARMA || 'Ã' ||
           DECODE(NVL(PR_VRT.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) || 'Ã' ||
           NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
           PROD.IND_PROD_REFRIG          || 'Ã' ||
           PROD.IND_TIPO_PROD          || 'Ã' ||
           --DECODE(NVL(PROM.COD_PROD,'N'),'N','N','S') || 'Ã' ||
           DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
           PROD.DESC_PROD ||
           DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)
          -- NVL(trim(V2.COD_ENCARTE),' ')--ind encarte
    FROM   LGT_PROD PROD,
           LGT_PROD_LOCAL PROD_LOCAL,
           LGT_LAB LAB,
           PBL_IGV IGV,
           LGT_PROD_VIRTUAL PR_VRT,
           --21/11/2007 DUBILLUZ MODIFICADO
           --VTA_PROD_PAQUETE  PROM
           (SELECT DISTINCT(V1.COD_PROD) COD
            FROM  (SELECT COD_PAQUETE,COD_PROD
                   FROM   VTA_PROD_PAQUETE
                   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                   ) V1,
                   VTA_PROMOCION    P
           /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                    TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
            WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
            AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
            AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    P.ESTADO  = ESTADO_ACTIVO
            AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                    OR
                     P.COD_PAQUETE_2 = (V1.COD_PAQUETE))) Z
           --VTA_PROD_ENCARTE V2
    WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     PROD_LOCAL.COD_LOCAL = cCodLocal_in
    AND     PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
    AND     PROD.COD_PROD = PROD_LOCAL.COD_PROD
    AND     PROD.COD_LAB = LAB.COD_LAB
    AND     PROD.COD_IGV = IGV.COD_IGV
    AND     PROD.EST_PROD = ESTADO_ACTIVO
    AND    PR_VRT.EST_PROD_VIRTUAL(+) = ESTADO_ACTIVO
    AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
    --AND    PROD.COD_PROD = PROM.COD_PROD(+)
    --AND    PROD.COD_GRUPO_CIA= PROM.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = z.cod (+);
   /* AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
    AND   PROD.COD_PROD=V2.COD_PROD(+);--JCORTEZ*/



    RETURN curVta;
  END;

  /* ************************************************************************* */

  FUNCTION VTA_OBTIENE_INFO_PROD(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in     IN CHAR,
                                 cCodProd_in     IN CHAR,
                                 cIndVerificaSug IN CHAR DEFAULT 'N')
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    IF cIndVerificaSug = 'N' THEN
      OPEN curVta FOR
      SELECT (PROD_LOCAL.STK_FISICO) || 'Ã' ||
           PROD.DESC_UNID_PRESENT || 'Ã' ||
           PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
           TO_CHAR(VTA_F_CHAR_PREC_REDONDEADO((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
           PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
           /*TO_CHAR( CEIL(VAL_PREC_VTA*100)/100 +
             CASE WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) = 0.0 THEN 0.0
                  WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) <= 0.5 THEN
                       (0.5 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10
                  ELSE (1.0 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10 END ,'999,990.000') || 'Ã' ||*/
           TO_CHAR( VTA_F_CHAR_PREC_REDONDEADO(VAL_PREC_VTA) ,'999,990.000') || 'Ã' ||  --JCHAVEZ 29102009  precio redondeado
           NVL(DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA),' ')|| 'Ã' ||
           PROD_LOCAL.IND_PROD_HABIL_VTA || 'Ã' ||
           --TO_CHAR(PROD_LOCAL.PORC_DCTO_1,'990.000') || 'Ã' ||
            --MODIFICADO POR DVELIZ 10.10.08
           '0' || 'Ã' ||
           PROD.IND_TIPO_PROD || 'Ã' ||
           NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
           ' ' || 'Ã' ||
           ' ' || 'Ã' ||
           PROD_LOCAL.EST_PROD_LOC || 'Ã' || -- kmoncada 02.07.2014 para obtener esl estado del producto en el local
           nvl(PROD.IND_TIPO_CONSUMO,'-')   --ASOSA - 02/10/2014 - PANHD
      FROM LGT_PROD PROD,
           LGT_PROD_LOCAL PROD_LOCAL
      WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
      AND     PROD_LOCAL.COD_LOCAL = cCodLocal_in
      AND     PROD_LOCAL.COD_PROD = cCodProd_in
      AND     PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
      AND     PROD.COD_PROD = PROD_LOCAL.COD_PROD
      ;
    ELSE --ERIOS 29/05/2008 Se muestran los datos de venta sugerida
      OPEN curVta FOR
      SELECT
        CASE WHEN PROD.VAL_FRAC_VTA_SUG IS NOT NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S' AND
                 PROD.VAL_FRAC_VTA_SUG < PROD_LOCAL.VAL_FRAC_LOCAL  THEN
           TRUNC(((PROD_LOCAL.STK_FISICO)*PROD.VAL_FRAC_VTA_SUG)/PROD_LOCAL.VAL_FRAC_LOCAL)|| 'Ã' ||
           PROD.DESC_UNID_PRESENT || 'Ã' ||
           PROD.VAL_FRAC_VTA_SUG || 'Ã' ||
           --TO_CHAR((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL),'999,990.000') || 'Ã' || --ERIOS 19/06/2008
           TO_CHAR(VTA_F_CHAR_PREC_REDONDEADO( ( CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/100 +
               CASE WHEN (CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10)-TRUNC(CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10) = 0.0 THEN 0.0
                    WHEN (CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10)-TRUNC(CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10) <= 0.5 THEN
                         (0.5 -( (CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10)-TRUNC(CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10) ))/10
                    ELSE (1.0 -( (CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10)-TRUNC(CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10) ))/10 END )) ,'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
           PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
           TO_CHAR( VTA_F_CHAR_PREC_REDONDEADO((CEIL(VAL_PREC_VTA_SUG*100)/100 +
               CASE WHEN (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) = 0.0 THEN 0.0
                    WHEN (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) <= 0.5 THEN
                         (0.5 -( (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) ))/10
                    ELSE (1.0 -( (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) ))/10 END)) ,'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
           PROD.DESC_UNID_VTA_SUG || 'Ã' ||
           PROD_LOCAL.IND_PROD_HABIL_VTA || 'Ã' ||
          -- TO_CHAR(PROD_LOCAL.PORC_DCTO_1,'990.000') || 'Ã' ||
           --MODIFICADO POR DVELIZ 10.10.08
           '0' || 'Ã' ||
           PROD.IND_TIPO_PROD || 'Ã' ||
           NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
           TO_CHAR( ((PROD_LOCAL.STK_FISICO)-((TRUNC(((PROD_LOCAL.STK_FISICO)*PROD.VAL_FRAC_VTA_SUG)/PROD_LOCAL.VAL_FRAC_LOCAL)*PROD_LOCAL.VAL_FRAC_LOCAL)/PROD.VAL_FRAC_VTA_SUG ) ),'9990' ) || 'Ã' ||
           DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',' ',PROD_LOCAL.UNID_VTA)|| 'Ã' ||
           PROD_LOCAL.EST_PROD_LOC  || 'Ã' ||-- kmoncada 02.07.2014 para obtener esl estado del producto en el local
           nvl(PROD.IND_TIPO_CONSUMO,'-')   --ASOSA - 03/10/2014 - PANHD
         WHEN PROD.VAL_FRAC_VTA_SUG IS NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S' THEN
           (PROD_LOCAL.STK_FISICO) || 'Ã' ||
           PROD.DESC_UNID_PRESENT || 'Ã' ||
           PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
           --TO_CHAR((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL),'999,990.000') || 'Ã' || --ERIOS 19/06/2008
           TO_CHAR( VTA_F_CHAR_PREC_REDONDEADO(( CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/100 +
               CASE WHEN (CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10)-TRUNC(CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10) = 0.0 THEN 0.0
                    WHEN (CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10)-TRUNC(CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10) <= 0.5 THEN
                         (0.5 -( (CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10)-TRUNC(CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10) ))/10
                    ELSE (1.0 -( (CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10)-TRUNC(CEIL((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL)*100)/10) ))/10 END ) ) ,'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
           PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
           --TO_CHAR( VAL_PREC_VTA ,'999,990.000') || 'Ã' || --ERIOS 19/06/2008
           TO_CHAR( VTA_F_CHAR_PREC_REDONDEADO((CEIL(VAL_PREC_VTA*100)/100 +
               CASE WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) = 0.0 THEN 0.0
                    WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) <= 0.5 THEN
                         (0.5 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10
                    ELSE (1.0 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10 END)) ,'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
           DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)  || 'Ã' ||
           PROD_LOCAL.IND_PROD_HABIL_VTA || 'Ã' ||
           -- TO_CHAR(PROD_LOCAL.PORC_DCTO_1,'990.000') || 'Ã' ||
           --MODIFICADO POR DVELIZ 10.10.08
           '0' || 'Ã' ||
           PROD.IND_TIPO_PROD || 'Ã' ||
           NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
           ' ' || 'Ã' ||
           ' ' || 'Ã' ||
           PROD_LOCAL.EST_PROD_LOC || 'Ã' || -- kmoncada 02.07.2014 para obtener esl estado del producto en el local
           nvl(PROD.IND_TIPO_CONSUMO,'-')   --ASOSA - 03/10/2014 - PANHD
         ELSE
           (PROD_LOCAL.STK_FISICO) || 'Ã' ||
           PROD.DESC_UNID_PRESENT || 'Ã' ||
           PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
           --TO_CHAR((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL),'999,990.000') || 'Ã' ||
           TO_CHAR( VTA_F_CHAR_PREC_REDONDEADO((( CEIL(VAL_PREC_VTA*100)/100 +
               CASE WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) = 0.0 THEN 0.0
                    WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) <= 0.5 THEN
                         (0.5 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10
                    ELSE (1.0 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10 END ) * PROD_LOCAL.VAL_FRAC_LOCAL)) ,'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
           PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
           TO_CHAR(  VTA_F_CHAR_PREC_REDONDEADO( (CEIL(VAL_PREC_VTA*100)/100 +
               CASE WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) = 0.0 THEN 0.0
                    WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) <= 0.5 THEN
                         (0.5 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10
                    ELSE (1.0 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10 END)) ,'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
           DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)  || 'Ã' ||
           PROD_LOCAL.IND_PROD_HABIL_VTA || 'Ã' ||
           -- TO_CHAR(PROD_LOCAL.PORC_DCTO_1,'990.000') || 'Ã' ||
           --MODIFICADO POR DVELIZ 10.10.08
           '0' || 'Ã' ||
           PROD.IND_TIPO_PROD || 'Ã' ||
           NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
           ' ' || 'Ã' ||
           ' ' || 'Ã' ||
           PROD_LOCAL.EST_PROD_LOC || 'Ã' || -- kmoncada 02.07.2014 para obtener esl estado del producto en el local
           NVL(PROD.IND_TIPO_CONSUMO,'-')   --ASOSA - 02/10/2014 - PANHD
         END
      FROM LGT_PROD PROD,
           LGT_PROD_LOCAL PROD_LOCAL
      WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
      AND     PROD_LOCAL.COD_LOCAL = cCodLocal_in
      AND     PROD_LOCAL.COD_PROD = cCodProd_in
      AND     PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
      AND     PROD.COD_PROD = PROD_LOCAL.COD_PROD
      ;
    END IF;
    RETURN curVta;
  END;

  /* ************************************************************************* */

  FUNCTION VTA_OBTIENE_INFO_COMPL_PROD(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                        cCodProd_in     IN CHAR,
                                       cIndVerificaSug IN CHAR DEFAULT 'N')
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    IF cIndVerificaSug = 'N' THEN
      OPEN curVta FOR
      SELECT (PROD_LOCAL.STK_FISICO) || 'Ã' ||
           ' ' || 'Ã' ||
           --ACC_TERAP.DESC_ACC_TERAP || 'Ã' ||
           TO_CHAR(SYSDATE, 'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
          TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' || --ERIOS 04/06/2008
           /*TO_CHAR( CEIL(VAL_PREC_VTA*100)/100 +
                     CASE WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) = 0.0 THEN 0.0
                          WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) <= 0.5 THEN
                               (0.5 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10
                          ELSE (1.0 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10 END ,'999,990.000') || 'Ã' ||*/
           DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
           --DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',TO_CHAR(PROD.VAL_BONO_VIG,'990.00'),TO_CHAR((PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL),'990.00')) || 'Ã' ||
-- 2008-07-30 modificado JOLIVA
--           TO_CHAR(PROD.VAL_BONO_VIG,'990.00') || 'Ã' ||
           '-' || 'Ã' ||
           --TO_CHAR(PROD_LOCAL.PORC_DCTO_1,'990.000') || 'Ã' ||
           --MODIFICADO POR DVELIZ 10.10.08
           '0' || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000')|| 'Ã' ||
           --TO_CHAR(LOC.PORC_DSCTO_CASTIGO/100,'990.000') --JCORTEZ
           TO_CHAR((PROD_LOCAL.VAL_PREC_VTA*(1+LOC.PORC_DSCTO_CASTIGO/100)),'990.000') || 'Ã' ||
           PROD_LOCAL.VAL_FRAC_LOCAL|| 'Ã' ||
           NVL(PROD_LOCAL.IND_ZAN,' ')--JCORTEZ 16/07/08
      FROM   LGT_PROD PROD,
           LGT_PROD_LOCAL PROD_LOCAL,
           --LGT_ACC_TERAP ACC_TERAP
           PBL_LOCAL LOC
      WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
      AND     PROD_LOCAL.COD_LOCAL = cCodLocal_in
      AND     PROD_LOCAL.COD_PROD = cCodProd_in
      AND     PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
      AND     PROD.COD_PROD = PROD_LOCAL.COD_PROD
      AND     PROD.EST_PROD = 'A'
      AND    PROD_LOCAL.COD_GRUPO_CIA=LOC.COD_GRUPO_CIA --JCORTEZ
      AND    PROD_LOCAL.COD_LOCAL=LOC.COD_LOCAL --JCORTEZ
      ;
      --AND     PROD.COD_ACC_TERAP = ACC_TERAP.COD_ACC_TERAP;
    ELSE --ERIOS 29/05/2008 Se muestran los datos de venta sugerida --mejorar el query!
      OPEN curVta FOR
      SELECT CASE WHEN PROD.VAL_FRAC_VTA_SUG IS NOT NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S' THEN
                      TRUNC(((PROD_LOCAL.STK_FISICO)*PROD.VAL_FRAC_VTA_SUG)/PROD_LOCAL.VAL_FRAC_LOCAL) || 'Ã' ||
                      ' ' || 'Ã' ||
                      TO_CHAR(SYSDATE, 'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
                      TO_CHAR(VAL_PREC_VTA_SUG,'999,990.000') || 'Ã' || --ERIOS 18/06/2008
                      /*TO_CHAR( CEIL(VAL_PREC_VTA_SUG*100)/100 +
                         CASE WHEN (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) = 0.0 THEN 0.0
                              WHEN (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) <= 0.5 THEN
                                   (0.5 -( (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) ))/10
                              ELSE (1.0 -( (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) ))/10 END ,'999,990.000') || 'Ã' ||*/
                      PROD.DESC_UNID_VTA_SUG || 'Ã' ||
-- 2008-07-30 modificado JOLIVA
--                      TO_CHAR(PROD.VAL_BONO_VIG,'990.00') || 'Ã' ||
                      '-' || 'Ã' ||
                       --TO_CHAR(PROD_LOCAL.PORC_DCTO_1,'990.000') || 'Ã' ||
           --MODIFICADO POR DVELIZ 10.10.08
           '0' || 'Ã' ||
                      TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA_SUG,'999,990.000') || 'Ã' ||
                      TO_CHAR((PROD_LOCAL.VAL_PREC_VTA*(1+LOC.PORC_DSCTO_CASTIGO/100)),'990.000') || 'Ã' ||--por si acaso
                      PROD_LOCAL.VAL_FRAC_LOCAL|| 'Ã' ||
                      NVL(PROD_LOCAL.IND_ZAN,' ')--JCORTEZ 16/07/08
                 WHEN PROD.VAL_FRAC_VTA_SUG IS NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S' THEN
                      (PROD_LOCAL.STK_FISICO) || 'Ã' ||
                      ' ' || 'Ã' ||
                      TO_CHAR(SYSDATE, 'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
                      TO_CHAR(VAL_PREC_VTA,'999,990.000') || 'Ã' ||
                      DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
-- 2008-07-30 modificado JOLIVA
--                      TO_CHAR(PROD.VAL_BONO_VIG,'990.00') || 'Ã' ||
                      '-' || 'Ã' ||
                      TO_CHAR(PROD_LOCAL.PORC_DCTO_1,'990.000') || 'Ã' ||
                      TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
                      TO_CHAR((PROD_LOCAL.VAL_PREC_VTA*(1+LOC.PORC_DSCTO_CASTIGO/100)),'990.000') || 'Ã' ||--por si acaso
                      PROD_LOCAL.VAL_FRAC_LOCAL|| 'Ã' ||
                      NVL(PROD_LOCAL.IND_ZAN,' ')--JCORTEZ 16/07/08
                 ELSE
                      (PROD_LOCAL.STK_FISICO) || 'Ã' ||
                      ' ' || 'Ã' ||
                      TO_CHAR(SYSDATE, 'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
                      TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' || --ERIOS 18/06/2008
                      /*TO_CHAR( CEIL(VAL_PREC_VTA*100)/100 +
                               CASE WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) = 0.0 THEN 0.0
                                    WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) <= 0.5 THEN
                                         (0.5 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10
                                    ELSE (1.0 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10 END ,'999,990.000') || 'Ã' ||*/
                      DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
-- 2008-07-30 modificado JOLIVA
--                      TO_CHAR(PROD.VAL_BONO_VIG,'990.00') || 'Ã' ||
                      '-' || 'Ã' ||
                      TO_CHAR(PROD_LOCAL.PORC_DCTO_1,'990.000') || 'Ã' ||
                      TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
                      TO_CHAR((PROD_LOCAL.VAL_PREC_VTA*(1+LOC.PORC_DSCTO_CASTIGO/100)),'990.000') || 'Ã' ||--por si acaso
                      PROD_LOCAL.VAL_FRAC_LOCAL|| 'Ã' ||
                      NVL(PROD_LOCAL.IND_ZAN,' ')--JCORTEZ 16/07/08
               END
      FROM   LGT_PROD PROD,
             LGT_PROD_LOCAL PROD_LOCAL,
             PBL_LOCAL LOC
      WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
      AND     PROD_LOCAL.COD_LOCAL = cCodLocal_in
      AND     PROD_LOCAL.COD_PROD = cCodProd_in
      AND     PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
      AND     PROD.COD_PROD = PROD_LOCAL.COD_PROD
      AND     PROD.EST_PROD = 'A'
      AND    PROD_LOCAL.COD_GRUPO_CIA=LOC.COD_GRUPO_CIA --JCORTEZ
      AND    PROD_LOCAL.COD_LOCAL=LOC.COD_LOCAL --JCORTEZ
      ;
    END IF;
    RETURN curVta;
  END;

  /* ************************************************************************* */

  FUNCTION VTA_OBTIENE_PRINC_ACT_PROD(cCodGrupoCia_in IN CHAR,
                     cCodProd_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
    SELECT PRINC_ACT.COD_PRINC_ACT || 'Ã' ||
         PRINC_ACT.DESC_PRINC_ACT
    FROM   LGT_PROD PROD,
         LGT_PRINC_ACT_PROD PRINC_ACT_PROD,
         LGT_PRINC_ACT PRINC_ACT
    WHERE  PROD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     PROD.COD_PROD = cCodProd_in
    AND     PROD.COD_GRUPO_CIA = PRINC_ACT_PROD.COD_GRUPO_CIA
    AND     PROD.COD_PROD = PRINC_ACT_PROD.COD_PROD
    AND     PRINC_ACT.COD_PRINC_ACT = PRINC_ACT_PROD.COD_PRINC_ACT;
    RETURN curVta;
  END;

  /* ************************************************************************* */

  FUNCTION VTA_OBTIENE_STK_PROD_FORUPDATE(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                          cCodProd_in    IN CHAR,
                        cValFracVta_in IN NUMBER)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
    SELECT TRUNC(((PROD_LOCAL.STK_FISICO)*cValFracVta_in)/PROD_LOCAL.VAL_FRAC_LOCAL) || 'Ã' ||
         TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' || --ERIOS 06/06/2008 parece que no se usa.
         --TO_CHAR(PROD_LOCAL.PORC_DCTO_1,'990.000')
         --MODIFICADO POR DVELIZ 10.10.08
           '0'
    FROM   LGT_PROD_LOCAL PROD_LOCAL
    WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     PROD_LOCAL.COD_LOCAL = cCodLocal_in
    AND     PROD_LOCAL.COD_PROD = cCodProd_in FOR UPDATE;
    RETURN curVta;
  END;

  /* ************************************************************************* */
  /* ************************************************************************ */
PROCEDURE VTA_GRABAR_PEDIDO_VTA_CAB(cCodGrupoCia_in         IN CHAR,
                                       cCodLocal_in            IN CHAR,
                                       cNumPedVta_in           IN CHAR,
                                      cCodCliLocal_in         IN CHAR,
                                      cSecMovCaja_in         IN CHAR,
                                       nValBrutoPedVta_in      IN NUMBER,
                                       nValNetoPedVta_in       IN NUMBER,
                                       nValRedondeoPedVta_in  IN NUMBER,
                                      nValIgvPedVta_in        IN NUMBER,
                                      nValDctoPedVta_in      IN NUMBER,
                                      cTipPedVta_in           IN CHAR,
                                      nValTipCambioPedVta_in IN NUMBER,
                                      cNumPedDiario_in       IN CHAR,
                                      nCantItemsPedVta_in     IN NUMBER,
                                      cEstPedVta_in           IN CHAR,
                                      cTipCompPago_in         IN CHAR,
                                      cNomCliPedVta_in       IN CHAR,
                                      cDirCliPedVta_in       IN CHAR,
                                      cRucCliPedVta_in       IN CHAR,
                                      cUsuCreaPedVtaCab_in   IN CHAR,
                                      cIndDistrGratuita_in   IN CHAR,
                                      cIndPedidoConvenio_in  IN CHAR,
                                      cCodConvenio_in        IN CHAR DEFAULT NULL,
                                      cCodUsuLocal_in        IN CHAR DEFAULT NULL,
                                      --dubilluz 09.06.0211
                                      cIndUsoEfectivo_in IN CHAR DEFAULT 'N',
                                      cIndUsoTarjeta_in IN CHAR DEFAULT 'N',
                                      cCodForma_Tarjeta_in IN CHAR DEFAULT 'N',
                                      cColegioMedico_in in char default '0',
                                        --FRAMIREZ 10.04.2012
                                      cCodCliente_in IN CHAR DEFAULT NULL,
                                      cIndConvBTLMF  IN vta_pedido_vta_cab.ind_conv_btl_mf%TYPE DEFAULT NULL
                                      -- 20.11.2013 dubilluz
                                      ,cCodSolicitud in varchar2 default 'X',
                                      vReferencia in varchar2 default '.',
                                      nPctBeneficiario in VTA_PEDIDO_VTA_CAB.PCT_BENEFICIARIO%TYPE default null,
                                      -- dubilluz 14.10.2014
                                      vIndCompMAnual in varchar2 default 'N',
                                      vTIP_COMP_MANUAL_in in varchar2 default '',
                                      vSERIE_COMP_MANUAL_in in varchar2 default '',
                                      vNUM_COMP_MANUAL_in in varchar2 default '',
                                      -- KMONCADA 2015.02.16 PROGRAMA PTOS
                                      cNroTarjeta_in      IN  VTA_PEDIDO_VTA_CAB.NUM_TARJ_PUNTOS%TYPE DEFAULT NULL,
                                      cIdTransaccion_in   IN  VTA_PEDIDO_VTA_CAB.ID_TRANSACCION%TYPE DEFAULT NULL,
                                      cPtoInicial_in      IN  VTA_PEDIDO_VTA_CAB.PT_INICIAL%TYPE DEFAULT NULL,
                                      cEstaTrxOrbis_in    IN  VTA_PEDIDO_VTA_CAB.EST_TRX_ORBIS%TYPE DEFAULT 'N'
                                      
                                      ) IS
     TIPCOMP  CHAR(2);
     v_vDescLocal         VARCHAR2(200);
     v_vReceiverAddress   VARCHAR2(3000);
     v_vCCReceiverAddress VARCHAR2(120) := NULL;
     mesg_body            VARCHAR2(32767);
     vAsunto              VARCHAR2(500);
     vTitulo           VARCHAR2(50);
     vMensaje          VARCHAR2(32767);
      v_vIP             VARCHAR2(15);
     v_vTipCompAsignado         VARCHAR2(10);
     v_vTipCompCorresponde       VARCHAR2(10);
     vCodCliente MAE_CONVENIO.COD_CLIENTE%TYPE;
     vNomCliPedVta CON_BENEFICIARIO.DES_CLIENTE%TYPE;
     vRucCliPedVta CON_BENEFICIARIO.DNI%TYPE;

        ---grabar ind convenio BTL - MF
     v_ind_conv_BTL_MF  CHAR(1);

	 vFlg_Rimac      MAE_CONVENIO.FLG_DATA_RIMAC%TYPE;
	 vFlg_Beneficiarios      MAE_CONVENIO.FLG_BENEFICIARIOS%TYPE;

   -- KMONCADA 03.07.2014 GRABA COMP PAGO DEL CONVENIO
   v_TipDocBenificiario MAE_TIPO_COMP_PAGO_BTLMF.COD_TIPODOC%TYPE;
   v_TipDocCliente MAE_TIPO_COMP_PAGO_BTLMF.COD_TIPODOC%TYPE;
   vTipCompPago VTA_PEDIDO_VTA_CAB.TIP_COMP_PAGO%TYPE;
   vPctBeneficiario VTA_PEDIDO_VTA_CAB.PCT_BENEFICIARIO%TYPE;
   
   -- KMONCADA 2015.02.16 PROGRAMA PUNTOS
   vNroTarjetaPuntos VTA_PEDIDO_VTA_CAB.NUM_TARJ_PUNTOS%TYPE;
   vIdTrxPuntos      VTA_PEDIDO_VTA_CAB.ID_TRANSACCION%TYPE;
   vPtoIncial        VTA_PEDIDO_VTA_CAB.PT_INICIAL%TYPE;

  BEGIN
    /*INSERT INTO VTA_PEDIDO_VTA_CAB (COD_GRUPO_CIA,
                                      COD_LOCAL,
                                    NUM_PED_VTA,
                                    COD_CLI_LOCAL,
                                    SEC_MOV_CAJA,
                                    VAL_BRUTO_PED_VTA,
                                    VAL_NETO_PED_VTA,
                                    VAL_REDONDEO_PED_VTA,
                                    VAL_IGV_PED_VTA,
                                    VAL_DCTO_PED_VTA,
                                    TIP_PED_VTA,
                                    VAL_TIP_CAMBIO_PED_VTA,
                                    NUM_PED_DIARIO,
                                    CANT_ITEMS_PED_VTA,
                                    EST_PED_VTA,
                                    TIP_COMP_PAGO,
                                    NOM_CLI_PED_VTA,
                                    DIR_CLI_PED_VTA,
                                    RUC_CLI_PED_VTA,
                                    USU_CREA_PED_VTA_CAB,
                                    IND_DISTR_GRATUITA)
                            VALUES (cCodGrupoCia_in,
                                     cCodLocal_in,
                                     cNumPedVta_in,
                                    cCodCliLocal_in,
                                    cSecMovCaja_in,
                                     nValBrutoPedVta_in,
                                     nValNetoPedVta_in,
                                     nValRedondeoPedVta_in,
                                    nValIgvPedVta_in,
                                    nValDctoPedVta_in,
                                    cTipPedVta_in,
                                    nValTipCambioPedVta_in,
                                    cNumPedDiario_in,
                                    nCantItemsPedVta_in,
                                    cEstPedVta_in,
                                    cTipCompPago_in,
                                    cNomCliPedVta_in,
                                    cDirCliPedVta_in,
                                    cRucCliPedVta_in,
                                    cUsuCreaPedVtaCab_in,
                                    cIndDistrGratuita_in);*/

   -- JCHAVEZ 23092009.sn
    IF (cTipCompPago_in = '01' or cTipCompPago_in = '05')and vIndCompMAnual = 'N' THEN
       SELECT X.TIP_COMP INTO TIPCOMP
         FROM VTA_IMPR_IP X
         WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in
         AND X.COD_LOCAL = cCodLocal_in
         AND TRIM(X.IP)=(SELECT SYS_CONTEXT('USERENV','IP_ADDRESS')   FROM DUAL);

       IF TIPCOMP <> cTipCompPago_in THEN
          --Envia correo
             SELECT DESC_LOCAL INTO v_vDescLocal
             FROM PBL_LOCAL
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL=cCodLocal_in;
             SELECT SYS_CONTEXT('USERENV','IP_ADDRESS') INTO v_vIP  FROM DUAL;
              SELECT DECODE(cTipCompPago_in,
                      01,
                      'BOLETA',
                      02,
                      'FACTURA',
                      05,
                      'TICKET')
              INTO v_vTipCompAsignado
              FROM DUAL;
              SELECT DECODE(TIPCOMP,
                      01,
                      'BOLETA',
                      02,
                      'FACTURA',
                      05,
                      'TICKET')
              INTO v_vTipCompCorresponde
              FROM DUAL;
          vMensaje:='ALERTA AL REGISTRAR PEDIDO DE VENTA:' ||
                                    TO_CHAR(SYSDATE,
                                            'dd/MM/yyyy HH24:mi:ss') ||
                                    '</B>' || '<BR> <I>ALERTA : </I> <BR>' ||
                                    '<BR>'  || ' En el local ' || cCodLocal_in || '-' || v_vDescLocal ||'<BR>' ||
                                    '<BR>'  || ' IP ' ||v_vIP ||'<BR>' ||
                                    '<BR>'  || ' El pedido Nro.: ' || cNumPedVta_in || '<BR>' ||
                                    '<BR>'  || ' El tipo de comprobante que le corresponde al IP es ' || v_vTipCompCorresponde ||
                                    ' y se le está asignando ' || v_vTipCompAsignado ||'.<BR>';

          mesg_body := '<L><B>' || vMensaje || '</B></L>';
          vAsunto:= 'ALERTA DE REGISTRO DE VENTAS';
          vTitulo:=  'ALERTA';

          SELECT LLAVE_TAB_GRAL
          INTO v_vReceiverAddress
          FROM PBL_TAB_GRAL
          WHERE ID_TAB_GRAL = 299;


          FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             v_vReceiverAddress,
                             vAsunto,
                             vTitulo,
                             mesg_body,
                             v_vCCReceiverAddress,
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

       END IF;
    END IF;





    IF cIndConvBTLMF = 'S' THEN

        SELECT
               CONV.COD_CLIENTE,
               TRIM(CONV.RUC),
               TRIM(CONV.INSTITUCION),
			         CONV.FLG_DATA_RIMAC,
			         CONV.FLG_BENEFICIARIOS,
               -- KMONCADA 03.07.2014 TIPO COMP PAGO PARA VTA CONVENIO
               CONV.COD_TIPDOC_BENEFICIARIO,
			         CONV.Cod_Tipdoc_Cliente
        INTO
               vCodCliente,
               vRucCliPedVta,
               vNomCliPedVta,
				       vFlg_Rimac,
               vFlg_Beneficiarios,
               -- KMONCADA 03.07.2014  TIPO COMP PAGO PARA VTA CONVENIO
				       v_TipDocBenificiario,
				       v_TipDocCliente
        FROM   MAE_CONVENIO CONV
        WHERE  CONV.COD_CONVENIO = cCodConvenio_in;

        /* BEGIN
            SELECT BENEF.DES_CLIENTE,
                   BENEF.DNI
              INTO vNomCliPedVta,vRucCliPedVta
              FROM V_CON_BENEFICIARIO BENEF
             WHERE BENEF.COD_CONVENIO = cCodConvenio_in
               AND BENEF.COD_CLIENTE = vCodCliente;

          EXCEPTION
           WHEN OTHERS THEN
              raise_application_error(-20021,'ERROR NO ENCUENTRA EL RUC DE LA EMPRESA CONVENIO '||vCodCliente);
         END;*/
		 --ERIOS 2.4.3 Cuando no tiene beneficiarios
          IF vFlg_Rimac = '1' AND vFlg_Beneficiarios = '0' THEN
            --vCodCliente := null;
            -- ERIOS 02.12.2014 CAMBIO DE CODIGO DE BENEFICIARIO
            null;
          ELSE
            vCodCliente := cCodCliente_in;
          END IF;

          -- KMONCADA 03.07.2014 OBTIENE TIPO DE COMPROBANTE DE PAGO PARA VTA CONVENIO

          IF v_TipDocBenificiario IS   NOT NULL THEN
             BEGIN
                SELECT A.TIP_COMP_PAGO
                INTO vTipCompPago
                FROM MAE_TIPO_COMP_PAGO_BTLMF A
                WHERE A.COD_TIPODOC = v_TipDocBenificiario;

             EXCEPTION
                WHEN NO_DATA_FOUND THEN -- ASIGNA EL DATO QUE SE ENVIA POR DEFECTO
                     vTipCompPago := cTipCompPago_in;
             END;
          END IF;

          IF v_TipDocCliente IS  NOT NULL THEN
             BEGIN
                SELECT A.TIP_COMP_PAGO
                INTO vTipCompPago
                FROM MAE_TIPO_COMP_PAGO_BTLMF A
                WHERE A.COD_TIPODOC = v_TipDocCliente;

             EXCEPTION
                WHEN NO_DATA_FOUND THEN -- ASIGNA EL DATO QUE SE ENVIA POR DEFECTO
                     vTipCompPago := cTipCompPago_in;
            END;
          END IF;

     ELSE
         vTipCompPago := cTipCompPago_in;
         vNomCliPedVta := cNomCliPedVta_in;
         vRucCliPedVta := cRucCliPedVta_in;
         vCodCliente := cCodCliente_in;

     END IF;


     IF nPctBeneficiario = -1 THEN
       vPctBeneficiario := null;
     ELSE
       vPctBeneficiario := nPctBeneficiario;
     END IF;

     -- KMONCADA 2015.02.16 PROGRAMA DE PUNTOS
    IF vIndCompMAnual = 'S' OR cEstaTrxOrbis_in = 'N' THEN
      vNroTarjetaPuntos := NULL;
      vIdTrxPuntos := NULL;
      vPtoIncial := NULL;
    ELSE
      vNroTarjetaPuntos := cNroTarjeta_in;
      vIdTrxPuntos := TRIM(cIdTransaccion_in);
      vPtoIncial := cPtoInicial_in;
    END IF;
    
    IF vPtoIncial IS NULL THEN
      vPtoIncial:= 0.0;
    END IF;
    
    -- JCHAVEZ 23092009.en
    INSERT INTO VTA_PEDIDO_VTA_CAB (COD_GRUPO_CIA,
                                      COD_LOCAL,
                                    NUM_PED_VTA,
                                    COD_CLI_LOCAL,
                                    SEC_MOV_CAJA,
                                    VAL_BRUTO_PED_VTA,
                                    VAL_NETO_PED_VTA,
                                    VAL_REDONDEO_PED_VTA,
                                    VAL_IGV_PED_VTA,
                                    VAL_DCTO_PED_VTA,
                                    TIP_PED_VTA,
                                    VAL_TIP_CAMBIO_PED_VTA,
                                    NUM_PED_DIARIO,
                                    CANT_ITEMS_PED_VTA,
                                    EST_PED_VTA,
                                    TIP_COMP_PAGO,
                                    NOM_CLI_PED_VTA,
                                    DIR_CLI_PED_VTA,
                                    RUC_CLI_PED_VTA,
                                    USU_CREA_PED_VTA_CAB,
                                    IND_DISTR_GRATUITA,
                                    IND_PED_CONVENIO,
                                    COD_CONVENIO,
                                    SEC_USU_LOCAL,
                                    --DUBILLUZ 09.06.2011
                                    IND_FP_FID_EFECTIVO,
                                    IND_FP_FID_TARJETA,
                                    COD_FP_FID_TARJETA,
                                    --DUBILLUZ 07.12.2011
                                    NUM_CMP,
                                    --FRAMIREZ 10.04.2012
                                    COD_CLI_CONV,
                                    IND_CONV_BTL_MF,
                                    REFERENCIA_PED_DLV,
                                    PCT_BENEFICIARIO,
                                    IND_COMP_MANUAL,
                                    TIP_COMP_MANUAL,
                                      SERIE_COMP_MANUAL,
                                      NUM_COMP_MANUAL,
                                      -- KMONCADA 2015.02.16
                                      ID_TRANSACCION,
                                      PT_INICIAL,
                                      EST_TRX_ORBIS,
                                      NUM_TARJ_PUNTOS
                                    )
                            VALUES (cCodGrupoCia_in,
                                     cCodLocal_in,
                                     cNumPedVta_in,
                                    cCodCliLocal_in,
                                    cSecMovCaja_in,
                                     nValBrutoPedVta_in,
                                     nValNetoPedVta_in,
                                     nValRedondeoPedVta_in,
                                    nValIgvPedVta_in,
                                    nValDctoPedVta_in,
                                    cTipPedVta_in,
                                    nValTipCambioPedVta_in,
                                    cNumPedDiario_in,
                                    nCantItemsPedVta_in,
                                    cEstPedVta_in,
                                    -- KMONCADA 03.07.2014 TIPO DE COMP PAGO
                                    vTipCompPago, -- cTipCompPago_in,
                                    cNomCliPedVta_in,
                                    cDirCliPedVta_in,
                                    cRucCliPedVta_in,
                                    cUsuCreaPedVtaCab_in,
                                    cIndDistrGratuita_in,
                                    cIndPedidoConvenio_in,
                                    cCodConvenio_in,
                                    cCodUsuLocal_in,
                                    --dubilluz 09.06.2011
                                    cIndUsoEfectivo_in,
                                    cIndUsoTarjeta_in ,
                                    cCodForma_Tarjeta_in,
                                    -- dubilluz 07.12.2011
                                    cColegioMedico_in,
                                     -- FRAMIREZ 10.04.2012
                                    vCodCliente,
                                    cIndConvBTLMF,
                                    vReferencia,
                                    vPctBeneficiario,
                                    vIndCompMAnual,
                                    vTIP_COMP_MANUAL_in,
                                      vSERIE_COMP_MANUAL_in,
                                      vNUM_COMP_MANUAL_in,
                                      -- KMONCADA 2015.02.16
                                      vIdTrxPuntos,
                                      vPtoIncial,
                                      cEstaTrxOrbis_in,
                                      vNroTarjetaPuntos);
          -- COLOCAR LOS CAMPOS de FORMA PAGO en N si son NULL
          update VTA_PEDIDO_VTA_CAB
          set IND_FP_FID_EFECTIVO = decode(IND_FP_FID_EFECTIVO,'NULL','N',IND_FP_FID_EFECTIVO),
              IND_FP_FID_TARJETA = decode(IND_FP_FID_TARJETA,'NULL','N',IND_FP_FID_TARJETA),
              COD_FP_FID_TARJETA = decode(COD_FP_FID_TARJETA,'NULL','N',COD_FP_FID_TARJETA)
          where  COD_GRUPO_CIA = cCodGrupoCia_in
            and COD_LOCAL = cCodLocal_in
            and NUM_PED_VTA = cNumPedVta_in;

          if cCodSolicitud != 'X' and length(trim(cCodSolicitud)) > 0 then
            update cab_solicitud_stock
            set    NUM_PED_VTA = cNumPedVta_in
            where  COD_GRUPO_CIA = cCodGrupoCia_in
            and    COD_LOCAL = cCodLocal_in
            and    ID_SOLICITUD = to_number(cCodSolicitud);
          end if;


  END;
  /* ************************************************************************ */
    PROCEDURE VTA_GRABAR_PEDIDO_VTA_DET(cCodGrupoCia_in        IN CHAR,
                                      cCodLocal_in           IN CHAR,
                                      cNumPedVta_in          IN CHAR,
                                      nSecPedVtaDet_in      IN NUMBER,
                                      cCodProd_in            IN CHAR,
                                      nCantAtendida_in       IN NUMBER,
                                      nValPrecVta_in          IN NUMBER,
                                      nValPrecTotal_in      IN NUMBER,
                                      nPorcDcto1_in          IN NUMBER,
                                      nPorcDcto2_in          IN NUMBER,
                                      nPorcDcto3_in          IN NUMBER,
                                      nPorcDctoTotal_in      IN NUMBER,
                                      cEstPedVtaDet_in      IN CHAR,
                                      nValTotalBono_in        IN NUMBER,
                                      nValFrac_in            IN NUMBER,
                                      nSecCompPago_in        IN NUMBER,
                                      cSecUsuLocal_in       IN CHAR,
                                      nValPrecLista_in        IN NUMBER,
                                      nValIgv_in             IN NUMBER,
                                      cUnidVta_in            IN CHAR,
                                      cNumTelRecarga_in     IN CHAR,--agregado x luis mesia 08/01/2007
                                      cUsuCreaPedVtaDet_in  IN CHAR,
                                      nValPrecPub           IN NUMBER,
                                      cCodProm_in           IN CHAR,
                                      cIndOrigen_in IN CHAR,
                                      nCantxDia_in IN NUMBER,
                                      nCantDias_in IN NUMBER,
                                      nAhorroPack IN NUMBER DEFAULT NULL

                                      ) IS

    v_nFracLocal VTA_PEDIDO_VTA_DET.VAL_FRAC_LOCAL%TYPE;
    v_nCantFracLocal VTA_PEDIDO_VTA_DET.CANT_FRAC_LOCAL%TYPE;
-- 2008-07-30 modificado JOLIVA
    v_nBonoVig       VTA_PEDIDO_VTA_DET.VAL_TOTAL_BONO%TYPE;
    -- 11.05.2009 DUBILLUZ
    nCantItemProdReceta number;
    nCantUsuAdmLocal number;

-- 2009-11-09 JOLIVA: Se agrega el porcentaje de ZAN
   v_nPorcZan         VTA_PEDIDO_VTA_DET.PORC_ZAN%TYPE;

    --JCORTEZ 20.10.09
    CCOD_GRUPO_REP CHAR(3);
    CCOD_GRUPO_REP_EDMUNDO CHAR(3);
    -- 19-NOV-13, TCT
    vc_Cod_Cia CHAR(3);
  BEGIN

  select count(1)
  into   nCantItemProdReceta
  from   vta_receta_especial
  where  cod_grupo_cia = cCodGrupoCia_in
  and    cod_prod = cCodProd_in
  and    estado = 'A';


  if nCantItemProdReceta > 0 then
     select count(1)
     into   nCantUsuAdmLocal
     from   pbl_rol_usu r
     where  r.cod_grupo_cia = cCodGrupoCia_in
     and    cod_local = cCodLocal_in
     and    r.sec_usu_local = cSecUsuLocal_in
     and    r.cod_rol = '011';--rol de administrador Local

     if nCantUsuAdmLocal = 0 then
        RAISE_APPLICATION_ERROR(-20020,'Solo el Administrador del local puede generar el Pedido.');
     end if;
  end if;

    -- ERIOS 29/05/2008 Determina la fraccion del local.
	-- 07.01.2015 ERIOS Garantizado por local
    SELECT VAL_FRAC_LOCAL,(nCantAtendida_in*VAL_FRAC_LOCAL)/nValFrac_in,LC.COD_CIA -- TCT, add codcia
			,PL.PORC_ZAN
      INTO v_nFracLocal,v_nCantFracLocal,vc_Cod_Cia,
			v_nPorcZan
    FROM LGT_PROD_LOCAL PL JOIN PBL_LOCAL LC ON (PL.COD_GRUPO_CIA = LC.COD_GRUPO_CIA AND PL.COD_LOCAL = LC.COD_LOCAL)
    WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND PL.COD_LOCAL = cCodLocal_in
          AND PL.COD_PROD = cCodProd_in;

    -- 2008-07-30 modificado JOLIVA
    --19.10.09 MODIFICADO JCORTEZ ADD GRUPO_REP
-- 2009-11-09 JOLIVA: Se agrega el porcentaje de ZAN
    SELECT NVL(P.VAL_BONO_VIG,0), NVL(P.COD_GRUPO_REP,' '),NVL(P.COD_GRUPO_REP_EDMUNDO,' ')-- NVL(P.PORC_ZAN,0)
    INTO v_nBonoVig,CCOD_GRUPO_REP,CCOD_GRUPO_REP_EDMUNDO
    FROM LGT_PROD P
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in AND COD_PROD = cCodProd_in;

    INSERT INTO VTA_PEDIDO_VTA_DET (COD_GRUPO_CIA,
           COD_LOCAL,
          NUM_PED_VTA,
          SEC_PED_VTA_DET,
          COD_PROD,
          CANT_ATENDIDA,
          VAL_PREC_VTA,
          VAL_PREC_TOTAL,
          PORC_DCTO_1,
          PORC_DCTO_2,
          PORC_DCTO_3,
          PORC_DCTO_TOTAL,
          EST_PED_VTA_DET,
          VAL_TOTAL_BONO,
          VAL_FRAC,
          SEC_COMP_PAGO,
          SEC_USU_LOCAL,
          VAL_PREC_LISTA,
          VAL_IGV,
          UNID_VTA,
          USU_CREA_PED_VTA_DET,
          IND_EXONERADO_IGV,
          DESC_NUM_TEL_REC,
          VAL_PREC_PUBLIC,
          COD_PROM, -- GRABA EL CODIGO DE PROMOCION
          IND_ORIGEN_PROD,-- ERIOS 10/04/2008 GRABA EL ORIGEN
          VAL_FRAC_LOCAL,
          CANT_FRAC_LOCAL,
          CANT_XDIA_TRA,--ERIOS 03/06/2008 Tratamiento
          CANT_DIAS_TRA,
          COD_CAMP_CUPON, --DVELIZ 09.10.08
          AHORRO,--DVELIZ 09.10.08
          PORC_DCTO_CALC,--DVELIZ 09.10.08
          COD_GRUPO_REP,COD_GRUPO_REP_EDMUNDO,--JCORTEZ 19.10.09
          AHORRO_PACK,--JCHAVEZ 20102009
          PORC_DCTO_CALC_PACK, --JCHAVEZ 20102009
-- 2009-11-09 JOLIVA: Se agrega el porcentaje de ZAN
          PORC_ZAN,
          --Agregado Por FRAMIREZ 09.05.2012
          AHORRO_CONV
          )
  VALUES (cCodGrupoCia_in,
           cCodLocal_in,
          cNumPedVta_in,
          nSecPedVtaDet_in,
          cCodProd_in,
          nCantAtendida_in,
          nValPrecVta_in,
          nValPrecTotal_in,
          nPorcDcto1_in,
          nPorcDcto2_in,
          nPorcDcto3_in,
          nPorcDctoTotal_in,
          cEstPedVtaDet_in,
-- 2008-07-30 modificado JOLIVA
--          nValTotalBono_in,
          v_nBonoVig,
          nValFrac_in,
          nSecCompPago_in,
          cSecUsuLocal_in,
          nValPrecLista_in,
          nValIgv_in,
          cUnidVta_in,
          cUsuCreaPedVtaDet_in,
          DECODE(nValIgv_in,0,'S','N'),
          cNumTelRecarga_in,
          nValPrecPub,
          cCodProm_in,
          cIndOrigen_in,
          v_nFracLocal,
          v_nCantFracLocal,
          nCantxDia_in,
          nCantDias_in,
          null,
          0,
          0,
          CCOD_GRUPO_REP,CCOD_GRUPO_REP_EDMUNDO,--JCORTEZ 19.10.09)
          (nAhorroPack*nCantAtendida_in),  --JCHAVEZ 20102009
          nPorcDcto2_in, --JCHAVEZ 20102009
-- 2009-11-09 JOLIVA: Se agrega el porcentaje de ZAN
          v_nPorcZan,
          --Agregado Por FRAMIREZ 09.05.2012
          ROUND(nValPrecTotal_in-nValPrecPub,2)
          );
  END;

  /* ************************************************************************ */

  FUNCTION VTA_OBTIENE_FEC_MOD_NUMERA_PED(cCodGrupoCia_in IN CHAR,
                         cCodLocal_in    IN CHAR,
                      cCodNumera_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
       OPEN curVta FOR
           --SELECT TO_CHAR(DECODE(NUMERA.FEC_MOD_NUMERA,NULL,SYSDATE,NUMERA.FEC_MOD_NUMERA),'dd/MM/yyyy')
          SELECT TO_CHAR(NVL(NUMERA.FEC_MOD_NUMERA,SYSDATE),'dd/MM/yyyy')
      --INTO   v_cFecModNumeraPed
      FROM   PBL_NUMERA NUMERA
      WHERE  NUMERA.COD_GRUPO_CIA = cCodGrupoCia_in
      AND     NUMERA.COD_LOCAL = cCodLocal_in
      AND     NUMERA.COD_NUMERA = cCodNumera_in FOR UPDATE;
    RETURN curVta;
  END;

  /* ************************************************************************ */
  --ERIOS 29/05/2008 DEPRECATED
  PROCEDURE VTA_ACTUALIZA_STK_PROD(cCodGrupoCia_in     IN CHAR,
                   cCodLocal_in        IN CHAR,
                   cCodProd_in        IN CHAR,
                   nCantStk_in        IN NUMBER,
                   cUsuModProdLocal_in  IN CHAR)
  IS
  BEGIN
            UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = cUsuModProdLocal_in, FEC_MOD_PROD_LOCAL = SYSDATE ,
                  STK_FISICO = STK_FISICO - nCantStk_in
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND   COD_LOCAL = cCodLocal_in
            AND   COD_PROD = cCodProd_in;
  END;

  /* ************************************************************************ */

  FUNCTION VTA_OBTIENE_ULTIMO_PED_DIARIO(cCodGrupoCia_in IN CHAR,
                        cCodLocal_in   IN CHAR,
                     cSecUsuLocal_in IN CHAR)
  RETURN CHAR
  IS
    v_cUltimoPedDiario VTA_PEDIDO_VTA_CAB.NUM_PED_DIARIO%TYPE;
  BEGIN
    /*SELECT NVL(MAX(CAB.NUM_PED_DIARIO),'0000')
    INTO   v_cUltimoPedDiario
    FROM   VTA_PEDIDO_VTA_CAB CAB,
         VTA_PEDIDO_VTA_DET DET
    WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     CAB.COD_LOCAL = cCodLocal_in
    AND     TO_CHAR(CAB.FEC_PED_VTA,'dd/MM/yyyy') = TO_CHAR(SYSDATE,'dd/MM/yyyy')
    AND     DET.SEC_USU_LOCAL = cSecUsuLocal_in
    AND     CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
    AND     CAB.COD_LOCAL = DET.COD_LOCAL
    AND     CAB.NUM_PED_VTA = DET.NUM_PED_VTA;
    --dbms_output.put_line('v_cUltimoPedDiario 1 : ' || v_cUltimoPedDiario );
    RETURN v_cUltimoPedDiario;*/

    SELECT NVL(MAX(CAB.NUM_PED_DIARIO),'0000')
            INTO v_cUltimoPedDiario
    FROM   VTA_PEDIDO_VTA_CAB CAB,
           VTA_PEDIDO_VTA_DET DET
    WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CAB.COD_LOCAL = cCodLocal_in
    AND    det.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    det.COD_LOCAL = cCodLocal_in
           -- AND TO_CHAR(CAB.FEC_PED_VTA,'dd/MM/yyyy') = TO_CHAR(SYSDATE,'dd/MM/yyyy')
    AND    CAB.FEC_PED_VTA between trunc(sysdate) and to_date(TO_CHAR(SYSDATE,'yyyymmdd')||'235959','yyyymmddhh24miss')
    AND    DET.SEC_USU_LOCAL = cSecUsuLocal_in
    AND    CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
    AND    CAB.COD_LOCAL = DET.COD_LOCAL
    AND    CAB.NUM_PED_VTA = DET.NUM_PED_VTA;

    RETURN v_cUltimoPedDiario ;
  END;

  /* ************************************************************************ */
  --16/04/2008  ERIOS     DEPRECATED: PTOVENTA_VTA_LISTA
  FUNCTION VTA_LISTA_PROD_FILTRO(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in     IN CHAR,
                                 cTipoFiltro_in  IN CHAR,
                                  cCodFiltro_in    IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
     IF(cTipoFiltro_in = 1) THEN --principio activo
       OPEN curVta FOR
       SELECT PROD.COD_PROD || 'Ã' ||
               PROD.DESC_PROD || 'Ã' ||
               DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
               LAB.NOM_LAB || 'Ã' ||
               (PROD_LOCAL.STK_FISICO) || 'Ã' ||
               TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
               DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',TO_CHAR(PROD.VAL_BONO_VIG,'990.00'),TO_CHAR((PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL),'990.00')) || 'Ã' ||
               --TO_CHAR(PROD.VAL_BONO_VIG,'990.00') || 'Ã' ||
               PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
               PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
               TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
              --PROD_LOCAL.VAL_PREC_LISTA || 'Ã' ||
               TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
               PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD || 'Ã' ||
              --DECODE(NVL(PROM.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
              DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)

       FROM   LGT_PROD PROD,
               LGT_PROD_LOCAL PROD_LOCAL,
               LGT_LAB LAB,
               PBL_IGV IGV,
              LGT_PRINC_ACT_PROD PRINC_ACT_PROD,
              LGT_PROD_VIRTUAL PR_VRT,
              --VTA_PROD_PAQUETE  PROM
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = '001'
                      ) V1,
                      VTA_PROMOCION    P

                 /*    WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                    TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
             WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                     AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                      AND    P.COD_GRUPO_CIA = 001
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
                      ) Z

       WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    PROD_LOCAL.COD_LOCAL = cCodLocal_in
       AND    PRINC_ACT_PROD.COD_PRINC_ACT = cCodFiltro_in
       AND    PROD.COD_PROD = PRINC_ACT_PROD.COD_PROD
       AND    PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
       AND    PROD.COD_PROD = PROD_LOCAL.COD_PROD
       AND    PROD.COD_LAB = LAB.COD_LAB
       AND    PROD.COD_IGV = IGV.COD_IGV
       AND    PROD.EST_PROD = ESTADO_ACTIVO
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = ESTADO_ACTIVO
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       --AND    PROD.COD_PROD = PROM.COD_PROD(+)
       --AND    PROD.COD_GRUPO_CIA= PROM.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = Z.COD(+);

  ELSIF(cTipoFiltro_in = 2) THEN --accion terapeutica
    OPEN curVta FOR
       SELECT PROD.COD_PROD || 'Ã' ||
               PROD.DESC_PROD || 'Ã' ||
               DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
               LAB.NOM_LAB || 'Ã' ||
               (PROD_LOCAL.STK_FISICO) || 'Ã' ||
               TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
               DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',TO_CHAR(PROD.VAL_BONO_VIG,'990.00'),TO_CHAR((PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL),'990.00')) || 'Ã' ||
               --TO_CHAR(PROD.VAL_BONO_VIG,'990.00') || 'Ã' ||
               PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
               PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
               TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
              --PROD_LOCAL.VAL_PREC_LISTA || 'Ã' ||
               TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
               PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD  || 'Ã' ||
              --DECODE(NVL(PROM.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
              DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)

       FROM   LGT_PROD PROD,
               LGT_PROD_LOCAL PROD_LOCAL,
               LGT_LAB LAB,
               PBL_IGV IGV,
              LGT_ACC_TERAP ACC_TER,
              LGT_ACC_TERAP_PROD ACC_TERAP_PROD,
              LGT_PROD_VIRTUAL PR_VRT,
              --VTA_PROD_PAQUETE  PROM
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = '001'
                      ) V1,
                      VTA_PROMOCION    P
                   /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
                       WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                       AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                      AND  P.COD_GRUPO_CIA = 001
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
                      ) Z
       WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    PROD_LOCAL.COD_LOCAL = cCodLocal_in
       AND    ACC_TER.COD_ACC_TERAP = cCodFiltro_in
       AND    PROD.COD_GRUPO_CIA = ACC_TERAP_PROD.COD_GRUPO_CIA
       AND    PROD.COD_PROD = ACC_TERAP_PROD.COD_PROD
       AND    ACC_TERAP_PROD.COD_ACC_TERAP = ACC_TER.COD_ACC_TERAP
       AND    PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
       AND    PROD.COD_PROD = PROD_LOCAL.COD_PROD
       AND    PROD.COD_LAB = LAB.COD_LAB
       AND    PROD.COD_IGV = IGV.COD_IGV
       AND    PROD.EST_PROD = ESTADO_ACTIVO
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = ESTADO_ACTIVO
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       --AND    PROD.COD_PROD = PROM.COD_PROD(+)
       --AND    PROD.COD_GRUPO_CIA= PROM.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = Z.COD(+);

  ELSIF(cTipoFiltro_in = 3) THEN --laboratorio
    OPEN curVta FOR
       SELECT PROD.COD_PROD || 'Ã' ||
               PROD.DESC_PROD || 'Ã' ||
               DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
               LAB.NOM_LAB || 'Ã' ||
               (PROD_LOCAL.STK_FISICO) || 'Ã' ||
               TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
               DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',TO_CHAR(PROD.VAL_BONO_VIG,'990.00'),TO_CHAR((PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL),'990.00')) || 'Ã' ||
               --TO_CHAR(PROD.VAL_BONO_VIG,'990.00') || 'Ã' ||
               PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
               PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
               TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
              --PROD_LOCAL.VAL_PREC_LISTA || 'Ã' ||
               TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
               PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD  || 'Ã' ||
              --DECODE(NVL(PROM.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
              DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)

       FROM   LGT_PROD PROD,
               LGT_PROD_LOCAL PROD_LOCAL,
               LGT_LAB LAB,
               PBL_IGV IGV,
              LGT_PROD_VIRTUAL PR_VRT,
              --VTA_PROD_PAQUETE  PROM
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = '001'
                      ) V1,
                      VTA_PROMOCION    P
                     /* WHERE (SELECT SYSDATE FROM DUAL)
                      BETWEEN
                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
             WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                      AND   P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                      AND P.COD_GRUPO_CIA = 001
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
                      ) Z

       WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    PROD_LOCAL.COD_LOCAL = cCodLocal_in
       AND    LAB.COD_LAB = cCodFiltro_in
       AND    PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
       AND    PROD.COD_PROD = PROD_LOCAL.COD_PROD
       AND    PROD.COD_LAB = LAB.COD_LAB
       AND    PROD.COD_IGV = IGV.COD_IGV
       AND    PROD.EST_PROD = ESTADO_ACTIVO
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = ESTADO_ACTIVO
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       --AND    PROD.COD_PROD = PROM.COD_PROD(+)
       --AND    PROD.COD_GRUPO_CIA= PROM.COD_GRUPO_CIA(+);
       AND    PROD.COD_PROD = Z.COD(+);
    END IF;
  RETURN curVta;
  END;

    /* ************************************************************************ */

  FUNCTION VTA_OBTIENE_REL_PRINC_ACT_PROD(cCodGrupoCia_in IN CHAR,
                      cCodLocal_in    IN CHAR,
                      cCodProd_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
       OPEN curVta FOR
         SELECT DISTINCT REL_PRINC_ACT.COD_PRINC_ACT_HIJO || 'Ã' ||
           PRINC_ACT.DESC_PRINC_ACT
      FROM   LGT_PRINC_ACT_PROD PRINC_ACT_PROD,
           LGT_REL_PRINC_ACT REL_PRINC_ACT,
           LGT_PRINC_ACT PRINC_ACT
      WHERE  PRINC_ACT_PROD.COD_PRINC_ACT IN (SELECT PRINC_ACT_PROD.COD_PRINC_ACT
                          FROM   LGT_PROD PROD,
                               LGT_PROD_LOCAL PROD_LOCAL,
                               LGT_PRINC_ACT_PROD PRINC_ACT_PROD
                          WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
                          AND     PROD_LOCAL.COD_LOCAL = cCodLocal_in
                          AND     PROD_LOCAL.COD_PROD = cCodProd_in
                          AND     PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
                          AND     PROD.COD_PROD = PROD_LOCAL.COD_PROD
                          AND     PROD.COD_GRUPO_CIA = PRINC_ACT_PROD.COD_GRUPO_CIA
                          AND     PROD.COD_PROD = PRINC_ACT_PROD.COD_PROD)
      AND     PRINC_ACT_PROD.COD_PRINC_ACT = REL_PRINC_ACT.COD_PRINC_ACT_PADRE
      AND     REL_PRINC_ACT.COD_PRINC_ACT_HIJO = PRINC_ACT.COD_PRINC_ACT;
    RETURN curVta;
  END;

  /* ************************************************************************ */

  FUNCTION VTA_LISTA_PROD_COMPLEMENTARIOS(cCodGrupoCia_in  IN CHAR,
                         cCodLocal_in     IN CHAR,
                      cCodProd_in     IN CHAR,
                      cCodPrinctAct_in IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
       OPEN curVta FOR
           SELECT PROD.COD_PROD || 'Ã' ||
                 PROD.DESC_PROD || 'Ã' ||
                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
                 LAB.NOM_LAB || 'Ã' ||
                 (PROD_LOCAL.STK_FISICO) || 'Ã' ||
                 TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',TO_CHAR(PROD.VAL_BONO_VIG,'990.00'),TO_CHAR((PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL),'990.00')) || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
                 PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
                 TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
                 --PROD_LOCAL.VAL_PREC_LISTA || 'Ã' ||
                 TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
                  PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')
          FROM   LGT_PROD PROD,
                 LGT_PROD_LOCAL PROD_LOCAL,
                 LGT_LAB LAB,
                 PBL_IGV IGV,
                 LGT_PRINC_ACT_PROD PRINC_ACT_PROD,
                 LGT_PROD_VIRTUAL PR_VRT
          WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND     PROD_LOCAL.COD_LOCAL = cCodLocal_in
          AND     PROD_LOCAL.COD_PROD <> cCodProd_in
          AND     PRINC_ACT_PROD.COD_PRINC_ACT = cCodPrinctAct_in
          AND     PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND     PROD.COD_PROD = PROD_LOCAL.COD_PROD
          AND     PROD.COD_LAB = LAB.COD_LAB
          AND     PROD.COD_IGV = IGV.COD_IGV
          AND     PROD.COD_GRUPO_CIA = PRINC_ACT_PROD.COD_GRUPO_CIA
          AND     PROD.COD_PROD = PRINC_ACT_PROD.COD_PROD
          AND     PROD.EST_PROD = ESTADO_ACTIVO
          AND    PR_VRT.EST_PROD_VIRTUAL(+) = ESTADO_ACTIVO
          AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
          AND    PROD.COD_PROD = PR_VRT.COD_PROD(+);
    RETURN curVta;
  END;

  /* ************************************************************************ */

  FUNCTION VTA_REL_COD_BARRA_COD_PROD(cCodGrupoCia_in IN CHAR,
                     cCodBarra_in    IN CHAR)
  RETURN CHAR
  IS
    v_cCodProd LGT_COD_BARRA.COD_PROD%TYPE;
  BEGIN
        SELECT NVL(COD_BARRA.COD_PROD,'000000')
    INTO   v_cCodProd
    FROM   LGT_COD_BARRA COD_BARRA
    WHERE  COD_BARRA.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     COD_BARRA.COD_BARRA = cCodBarra_in;
    RETURN v_cCodProd;
  EXCEPTION
      WHEN NO_DATA_FOUND THEN
       RETURN '000000';
  END;

   /* ************************************************************************* */

  FUNCTION VTA_OBTIENE_ACC_TERAP_PROD(cCodGrupoCia_in IN CHAR,
                     cCodProd_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
    SELECT ACC_TERAP.COD_ACC_TERAP || 'Ã' ||
         ACC_TERAP.DESC_ACC_TERAP
    FROM   LGT_PROD PROD,
         LGT_ACC_TERAP ACC_TERAP,
         LGT_ACC_TERAP_PROD ACC_TERAP_PROD
    WHERE  PROD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     PROD.COD_PROD = cCodProd_in
    AND     PROD.COD_GRUPO_CIA = ACC_TERAP_PROD.COD_GRUPO_CIA
    AND     PROD.COD_PROD = ACC_TERAP_PROD.COD_PROD
    AND     ACC_TERAP.COD_ACC_TERAP = ACC_TERAP_PROD.COD_ACC_TERAP;
    RETURN curVta;
  END;

  PROCEDURE VTA_FALTA_CERO(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in IN CHAR,
                           cCodProd_in IN CHAR,
                           cSecUsuLocal_in IN CHAR,
                           cUsuCrea_in IN CHAR)
  IS
  BEGIN
       INSERT INTO lgt_prod_local_falta_stk (COD_GRUPO_CIA,COD_LOCAL,COD_PROD,SEC_USU_LOCAL,USU_CREA_PROD_LOCAL_FALTA_STK) VALUES
       (cCodGrupoCia_in,cCodLocal_in,cCodProd_in,cSecUsuLocal_in,cUsuCrea_in);
  END;

  FUNCTION vta_combo_medicos
  RETURN FarmaCursor
  IS
   curVta FarmaCursor;
   BEGIN
    OPEN curVta FOR
    SELECT  DISTINCT md.cdg_apm|| 'Ã' ||
            md.cdg_apm
    FROM vta_mae_medico md ;
    RETURN Curvta;
    END;

  FUNCTION vta_busca_medico(cCodigo_in IN CHAR,
                              cMatriculaApe_in IN CHAR,
                              --cApellido_in  IN CHAR,
                              cTipoBusqueda_in IN CHAR)
    RETURN FarmaCursor
    IS
      curVta FarmaCursor;
      BEGIN
        IF (Ctipobusqueda_In = '1') THEN -- codigo de medico matriculas
           OPEN curVta FOR
           --SELECT md.cdg_apm || 'Ã' ||
           --       md.matricula || 'Ã' ||
           --       md.nombre
           --FROM vta_mae_medico md
           --WHERE --md.cdg_apm = Ccodigo_In
           --      --AND
           --      md.matricula = CmatriculaApe_in;
           SELECT decode(COD_TIPO_COLEGIO,'01','CMP','02','ODO','03','OBS','CMP') || 'Ã' ||
                  me.num_cmp || 'Ã' ||
                  me.des_nom_medico || ' ' || me.des_ape_medico
           FROM mae_medico me
           WHERE me.num_cmp = CmatriculaApe_in;
        ELSIF (Ctipobusqueda_In = '2') THEN -- apellido
        DBMS_OUTPUT.PUT_LINE('ENTRO AL 2');
           OPEN curVta FOR
           --SELECT md.cdg_apm || 'Ã' ||
           --       md.matricula || 'Ã' ||
           --       md.nombre
           --FROM vta_mae_medico md
           --WHERE md.nombre LIKE '%'|| cMatriculaApe_in ||'%';
           SELECT decode(COD_TIPO_COLEGIO,'01','CMP','02','ODO','03','OBS','CMP') || 'Ã' ||
                  me.num_cmp || 'Ã' ||
                  me.des_nom_medico || ' ' || me.des_ape_medico
           FROM mae_medico me
           WHERE me.des_nom_medico LIKE '%'|| cMatriculaApe_in ||'%'
                 or me.des_ape_medico LIKE '%'|| cMatriculaApe_in ||'%';
       END IF;
     RETURN curVta;
  END;

  PROCEDURE vta_agrega_medico_vta(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  cNumPedVta_in IN CHAR,
                                  cCodApmIN_in IN CHAR,
                                  cMatricula_in IN CHAR,
                                  cUsuCrea_in   IN CHAR)
  IS
  BEGIN
    INSERT INTO VTA_PED_VTA_MED
    (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,CDG_APM,MATRICULA,USU_CREA_VTA_MED)VALUES
    (Ccodgrupocia_In,Ccodlocal_In,Cnumpedvta_In,Ccodapmin_In,Cmatricula_In,Cusucrea_In);
  END;

  PROCEDURE vta_agrega_medico_vta(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  cNumPedVta_in IN CHAR,
                                  cNumPedRec_in IN CHAR,
                                  cUsuCrea_in   IN CHAR)
  IS
  BEGIN
    /*INSERT INTO VTA_PED_VTA_MED
    (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,CDG_APM,MATRICULA,USU_CREA_VTA_MED)VALUES
    (Ccodgrupocia_In,Ccodlocal_In,Cnumpedvta_In,Ccodapmin_In,Cmatricula_In,Cusucrea_In);*/
    --GRABA LA REFERENCIA DE LA RECETA EN EL PEDIDO GENERADO
    UPDATE VTA_PEDIDO_VTA_CAB
    SET USU_MOD_PED_VTA_CAB = cUsuCrea_in, FEC_MOD_PED_VTA_CAB = SYSDATE,
        NUM_PED_REC = cNumPedRec_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in;
  END;
  /*****************************************************************************/
  PROCEDURE VTA_GRABAR_PED_RECETA_CAB(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
            cNumPedVta_in IN CHAR,cCodApmIN_in IN CHAR,cMatricula_in IN CHAR,
            nValBrutoPedVta_in IN NUMBER,nValNetoPedVta_in IN NUMBER,
            nValRedondeoPedVta_in IN NUMBER,nValIgvPedVta_in IN NUMBER,
            nValDctoPedVta_in IN NUMBER,nValTipCambioPedVta_in IN NUMBER,
            nCantItemsPedVta_in IN NUMBER,cEstPedVta_in IN CHAR,
            cUsuCreaPedVtaCab_in IN CHAR)
  IS
  BEGIN
    INSERT INTO VTA_PED_RECETA_CAB(COD_GRUPO_CIA,COD_LOCAL,NUM_PED_REC,
    CDG_APM,MATRICULA,
    VAL_BRUTO_PED_REC,VAL_NETO_PED_REC,VAL_REDONDEO_PED_REC,VAL_IGV_PED_REC,
    VAL_DCTO_PED_REC,VAL_TIP_CAMBIO_PED_REC,
    CANT_ITEMS_PED_REC,EST_PED_REC,
    USU_CREA_PED_REC_CAB)
    VALUES (cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,
    cCodApmIN_in,cMatricula_in,
    nValBrutoPedVta_in,nValNetoPedVta_in,nValRedondeoPedVta_in,nValIgvPedVta_in,
    nValDctoPedVta_in,nValTipCambioPedVta_in,
    nCantItemsPedVta_in,cEstPedVta_in,
    cUsuCreaPedVtaCab_in);
  END;
  /*****************************************************************************/
  PROCEDURE VTA_GRABAR_PED_RECETA_DET(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
            cNumPedVta_in IN CHAR,nSecPedVtaDet_in IN NUMBER,cCodProd_in IN CHAR,
            nCantAtendida_in IN NUMBER,nValPrecVta_in IN NUMBER,nValPrecTotal_in IN NUMBER,
            nPorcDcto1_in IN NUMBER,nPorcDcto2_in IN NUMBER,nPorcDcto3_in IN NUMBER,
            nPorcDctoTotal_in IN NUMBER,cEstPedVtaDet_in IN CHAR,
            nValFrac_in IN NUMBER,nValPrecLista_in IN NUMBER,nValIgv_in IN NUMBER,
            cUnidVta_in IN CHAR,cUsuCreaPedVtaDet_in IN CHAR)
  IS
    v_nStkDisponible LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nFracLocal LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
  BEGIN
    SELECT (STK_FISICO),VAL_FRAC_LOCAL
           INTO v_nStkDisponible, v_nFracLocal
    FROM LGT_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in;
    INSERT INTO VTA_PED_RECETA_DET (COD_GRUPO_CIA,COD_LOCAL,
    NUM_PED_REC,SEC_PED_REC_DET,
    COD_PROD,CANT_ATENDIDA,VAL_PREC_REC,VAL_PREC_TOTAL,
    PORC_DCTO_1,PORC_DCTO_2,PORC_DCTO_3,PORC_DCTO_TOTAL,
    EST_PED_REC_DET,
    VAL_FRAC,VAL_PREC_LISTA,VAL_IGV,UNID_VTA,
    USU_CREA_PED_REC_DET,IND_EXONERADO_IGV,
    STK_FISICO_DISP)
    VALUES (cCodGrupoCia_in,cCodLocal_in,
    cNumPedVta_in,nSecPedVtaDet_in,
    cCodProd_in,nCantAtendida_in,nValPrecVta_in,nValPrecTotal_in,
    nPorcDcto1_in,nPorcDcto2_in,nPorcDcto3_in,nPorcDctoTotal_in,
    cEstPedVtaDet_in,
    nValFrac_in,nValPrecLista_in,nValIgv_in,cUnidVta_in,
    cUsuCreaPedVtaDet_in,DECODE(nValIgv_in,0,'S','N'),
    ROUND((v_nStkDisponible*nValFrac_in)/v_nFracLocal)
    );
  END;


  FUNCTION VTA_OBTIENE_INFO_PROD_VIRTUAL(cCodGrupoCia_in IN CHAR,
                                          cCodProd_in     IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
       OPEN curVta FOR
            SELECT NVL(VIR.CANT_MIN_REC, 0) || 'Ã' ||
                   NVL(VIR.CANT_MAX_REC, 1000) || 'Ã' ||
                   NVL(TRIM(VIR.COD_PROV_TEL), 'X')
            FROM   LGT_PROD_VIRTUAL VIR
            WHERE  VIR.COD_GRUPO_CIA = cCodGrupoCia_in
            AND     VIR.COD_PROD = cCodProd_in;
    RETURN curVta;
  END;

  FUNCTION VTA_OBT_IND_VER_STK_LOCALES(cCodGrupoCia_in IN CHAR,
                                            cCodLocal_in     IN CHAR)
   RETURN CHAR
  IS
   c_IndVerLocales PBL_LOCAL.IND_VER_STOCK_LOCALES%TYPE;

  BEGIN
       SELECT LOC.IND_VER_STOCK_LOCALES INTO c_IndVerLocales
       FROM   PBL_LOCAL LOC
       WHERE  LOC.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    LOC.COD_LOCAL = cCodLocal_in;
   RETURN c_IndVerLocales;
  END;

/* **************************************************************** */
  /*FUNCTION VTA_PERMITE_PROD_REGALO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cIpPc_in        IN CHAR,
                                   cCodEncarte_in  IN CHAR DEFAULT '00001',
                                   cProdVentaPedido_in IN VARCHAR2
                                   )
  RETURN FarmaCursor
  IS
   cCod_encarte       CHAR(5);
   cPermiteProdRegalo CHAR(1);
   nStockProdRegalo   NUMBER;
   curVta             FarmaCursor;
  BEGIN

  delete from aux_prod_pedido_pc where  IP_PC = cIpPc_in;

  insert into aux_prod_pedido_pc
  (Cod_Ims,cod_prod_original,IP_PC)
  select p.Cod_Ims_Iv   ,P.cod_prod ,cIpPc_in
   from   lgt_prod p
   WHERE  p.cod_grupo_cia = cCodGrupoCia_in
   and    p.cod_prod in (SELECT trim(EXTRACTVALUE(xt.column_value,'e'))
                         FROM   TABLE(XMLSEQUENCE
                                 ( EXTRACT
                                   ( XMLTYPE('<coll><e>' ||
                                     REPLACE(cProdVentaPedido_in,'@','</e><e>') ||
                                       '</e></coll>')
                                     , '/coll/e') )) xt
                          );


       BEGIN
            SELECT E.COD_ENCARTE
            INTO   cCod_encarte
            FROM   VTA_ENCARTE E
            WHERE  TRUNC(SYSDATE) BETWEEN E.FECH_INICIO
                                  AND     E.FECH_FIN
            AND    E.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    E.COD_ENCARTE = cCodEncarte_in
            AND    E.ESTADO = 'A';

            cPermiteProdRegalo := 'S';
       EXCEPTION
       WHEN NO_DATA_FOUND THEN
         cPermiteProdRegalo := 'N';
       END;

       IF cPermiteProdRegalo = 'S' THEN

           SELECT L.STK_FISICO
           INTO   nStockProdRegalo
           FROM   LGT_PROD_LOCAL L
           WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    L.COD_LOCAL = cCodLocal_in
           AND    L.COD_PROD = (SELECT PE.COD_PROD_REGALO
                                FROM   VTA_ENCARTE PE
                                WHERE  PE.COD_GRUPO_CIA =  cCodGrupoCia_in
                                AND    PE.COD_ENCARTE   = cCod_encarte);

           IF nStockProdRegalo > 0 THEN
               OPEN curVta FOR
                     SELECT 'ENCARTE&'||E.COD_ENCARTE||'&'||
                             E.COD_PROD_REGALO||'&'||
                             --E.MONT_MIN||'&'||
                             TRIM(TO_CHAR(E.MONT_MIN,'99999.000'))||'&'|| --JMIRANDA 29.01.10
                             E.CANT_MAX_PROD
                      FROM   VTA_ENCARTE E
                      WHERE  E.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    E.COD_ENCARTE = cCod_encarte
                      UNION
                      SELECT R.COD_PROD_ORIGINAL
                      FROM   aux_prod_pedido_pc R,
                             VTA_PROD_ENCARTE E
                      WHERE  R.IP_PC = cIpPc_in
                      AND    E.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    E.COD_ENCARTE = cCod_encarte
                      AND    R.COD_PROD_ORIGINAL = E.COD_PROD
                      ORDER  BY 1 ASC
                      ;
           ELSE
               OPEN curVta FOR
               SELECT 'N' FROM DUAL;
           END IF;
       ELSE
               OPEN curVta FOR
               SELECT 'N' FROM DUAL;
       END IF;
       delete from aux_prod_pedido_pc where  IP_PC = cIpPc_in;
       RETURN curVta;

  END;*/

  FUNCTION VTA_PERMITE_PROD_REGALO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cIpPc_in        IN CHAR,
                                   cCodEncarte_in  IN CHAR DEFAULT '00001',
                                   cProdVentaPedido_in IN VARCHAR2
                                   )
  RETURN FarmaCursor
  IS
   cCod_encarte       CHAR(5);
   cPermiteProdRegalo CHAR(1);
   nStockProdRegalo   NUMBER;
   curVta             FarmaCursor;
  BEGIN

  delete from aux_prod_pedido_pc where  IP_PC = cIpPc_in;

  insert into aux_prod_pedido_pc
  (Cod_Ims,cod_prod_original,IP_PC)
  select p.Cod_Ims_Iv   ,P.cod_prod ,cIpPc_in
   from   lgt_prod p
   WHERE  p.cod_grupo_cia = cCodGrupoCia_in
   and    p.cod_prod in (SELECT trim(EXTRACTVALUE(xt.column_value,'e'))
                         FROM   TABLE(XMLSEQUENCE
                                 ( EXTRACT
                                   ( XMLTYPE('<coll><e>' ||
                                     REPLACE(cProdVentaPedido_in,'@','</e><e>') ||
                                       '</e></coll>')
                                     , '/coll/e') )) xt
                          );
--commit;

       BEGIN
            SELECT E.COD_ENCARTE
            INTO   cCod_encarte
            FROM   VTA_ENCARTE E
            WHERE  TRUNC(SYSDATE) BETWEEN E.FECH_INICIO
                                  AND     E.FECH_FIN
            AND    E.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    E.COD_ENCARTE = cCodEncarte_in
            AND    E.ESTADO = 'A';

            cPermiteProdRegalo := 'S';
       EXCEPTION
       WHEN NO_DATA_FOUND THEN
         cPermiteProdRegalo := 'N';
       END;

       IF cPermiteProdRegalo = 'S' THEN

          BEGIN
           SELECT L.STK_FISICO
           INTO   nStockProdRegalo
           FROM   LGT_PROD_LOCAL L
           WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    L.COD_LOCAL = cCodLocal_in
           AND    L.COD_PROD = (
                                SELECT  MIN(V.PRODUCTO)
                                FROM   (
                                        SELECT PE.COD_PROD_REGALO  PRODUCTO
                                        FROM   VTA_ENCARTE PE
                                        WHERE  PE.COD_GRUPO_CIA =  cCodGrupoCia_in
                                        AND    PE.COD_ENCARTE   = cCod_encarte
                                        UNION
                                        SELECT MIN(R.COD_PROD) PRODUCTO---OBTENGO EL MINIMO
                                        FROM   VTA_ENCARTE_REGALO R
                                        WHERE  R.COD_GRUPO_CIA =  cCodGrupoCia_in
                                        AND    R.COD_ENCARTE   = cCod_encarte
                                        ) V
                                 WHERE  --LENGTH(V.PRODUCTO) > 5
                                 TRIM(V.PRODUCTO) != 0
                                 --AND    ROWNUM = 1
                                );

           /*IF nStockProdRegalo > 0 THEN*/
               OPEN curVta FOR
                     SELECT 'ENCARTE&'||E.COD_ENCARTE||'&'||
                             (CASE WHEN E.COD_PROD_REGALO=0 THEN
                                (SELECT MIN(R.COD_PROD)---OBTENGO EL MINIMO
                                        FROM   VTA_ENCARTE_REGALO R
                                        WHERE  R.COD_GRUPO_CIA =  cCodGrupoCia_in
                                        AND    R.COD_ENCARTE   = cCod_encarte)
                             ELSE
                                E.COD_PROD_REGALO
                            END) ||'&'||
                             --E.MONT_MIN||'&'||
                             TRIM(TO_CHAR(E.MONT_MIN,'99999.000'))||'&'|| --JMIRANDA 29.01.10
                             E.CANT_MAX_PROD||'&'||
                             decode(E.COD_PROD_REGALO,0,'S','N')
                      FROM   VTA_ENCARTE E
                      WHERE  E.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    E.COD_ENCARTE = cCod_encarte
                      UNION
                      SELECT R.COD_PROD_ORIGINAL
                      FROM   aux_prod_pedido_pc R,
                             VTA_PROD_ENCARTE E
                      WHERE  R.IP_PC = cIpPc_in
                      AND    E.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    E.COD_ENCARTE = cCod_encarte
                      AND    R.COD_PROD_ORIGINAL = E.COD_PROD
                      ORDER  BY 1 ASC
                      ;
/*           ELSE
               OPEN curVta FOR
               SELECT 'N' FROM DUAL;
           END IF;*/
           EXCEPTION
               WHEN NO_DATA_FOUND THEN
                  OPEN curVta FOR
               SELECT 'N' FROM DUAL;
           END;
       ELSE
               OPEN curVta FOR
               SELECT 'N' FROM DUAL;
       END IF;
       delete from aux_prod_pedido_pc where  IP_PC = cIpPc_in;
       RETURN curVta;

  END;

/* ***************************************************************** */

  PROCEDURE VTA_GRABAR_PED_REGALO_DET(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    cNumPedVta_in    IN CHAR,
                                    cSecProdDet_in   IN NUMBER,
                                    cCodProd_in      IN CHAR,
                                    cCantAtend_in    IN NUMBER,
                                    cValPredVenta_in IN CHAR,
                                    cSecUsu_in       IN CHAR,
                                    cLoginUsu_in     IN CHAR)
  IS

  BEGIN

      INSERT INTO VTA_PEDIDO_VTA_DET
      (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,
      VAL_PREC_VTA,VAL_PREC_TOTAL,PORC_DCTO_1,PORC_DCTO_2,PORC_DCTO_3,
      PORC_DCTO_TOTAL,VAL_TOTAL_BONO,VAL_FRAC,
      SEC_USU_LOCAL,USU_CREA_PED_VTA_DET,VAL_PREC_LISTA,
      VAL_IGV,UNID_VTA,IND_EXONERADO_IGV,
      VAL_PREC_PUBLIC,IND_ORIGEN_PROD,
      VAL_FRAC_LOCAL, --ERIOS 10/06/2008
      CANT_FRAC_LOCAL,
      COD_GRUPO_REP,COD_GRUPO_REP_EDMUNDO, --JCORTEZ 19.10.09
      PORC_ZAN      -- 2009-11-09 JOLIVA
      )
      SELECT   cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cSecProdDet_in,cCodProd_in,cCantAtend_in,
               cValPredVenta_in,cValPredVenta_in*cCantAtend_in,
               PROD_LOCAL.PORC_DCTO_1,
               PROD_LOCAL.PORC_DCTO_2,
               PROD_LOCAL.PORC_DCTO_3,
               PROD_LOCAL.PORC_DCTO_1+ PROD_LOCAL.PORC_DCTO_2 +
               PROD_LOCAL.PORC_DCTO_3,
               DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.VAL_BONO_VIG,(PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL)),
               PROD_LOCAL.VAL_FRAC_LOCAL,
               cSecUsu_in,cLoginUsu_in,
               cValPredVenta_in,
               IGV.PORC_IGV,PROD.DESC_UNID_PRESENT,DECODE(IGV.PORC_IGV,0,'S','N'),
               PROD_LOCAL.VAL_PREC_VTA,
               IND_ORIGEN_REGA, --ERIOS 11/04/2008 Origen
               PROD_LOCAL.VAL_FRAC_LOCAL, --ERIOS 10/06/2008 Se graba los mismos valores del pedido.
               cCantAtend_in,
               NVL(PROD.COD_GRUPO_REP,' '),NVL(PROD.COD_GRUPO_REP_EDMUNDO,' '), --JCORTEZ 19.10.09
               PROD_LOCAL.PORC_ZAN   -- 2009-11-09 JOLIVA
      FROM     LGT_PROD PROD,
               LGT_PROD_LOCAL PROD_LOCAL,
               pbl_igv igv
      WHERE    PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
      AND       PROD_LOCAL.COD_LOCAL = cCodLocal_in
      AND       PROD_LOCAL.COD_PROD = cCodProd_in
      AND       PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
      AND       PROD.COD_PROD = PROD_LOCAL.COD_PROD
      AND      PROD.COD_IGV = IGV.COD_IGV;



  END;

/* **************************************************************** */
  FUNCTION VTA_PERMITE_CUPON(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in     IN CHAR,
                             cIpPc_in        IN CHAR,
                             cNumPedVta_in   IN CHAR,
                             cCodCupon_in    IN CHAR  DEFAULT '00001',
               cProdVentaPedido_in IN VARCHAR2)
  RETURN FarmaCursor
  IS
   cCod_Campana       CHAR(5);
   cPermiteCupon      CHAR(1);
   curVta             FarmaCursor;
  BEGIN

  delete from aux_prod_pedido_pc where  IP_PC = cIpPc_in;

  insert into aux_prod_pedido_pc
  (Cod_Ims,cod_prod_original,IP_PC)
  select p.Cod_Ims_Iv   ,P.cod_prod ,cIpPc_in
   from   lgt_prod p
   WHERE  p.cod_grupo_cia = cCodGrupoCia_in
   and    p.cod_prod in (SELECT trim(EXTRACTVALUE(xt.column_value,'e'))
                         FROM   TABLE(XMLSEQUENCE
                                 ( EXTRACT
                                   ( XMLTYPE('<coll><e>' ||
                                     REPLACE(cProdVentaPedido_in,'@','</e><e>') ||
                                       '</e></coll>')
                                     , '/coll/e') )) xt
                          );
                          commit;

       BEGIN
            SELECT C.COD_CAMP_CUPON
            INTO   cCod_Campana
            FROM   VTA_CAMPANA_CUPON C
            WHERE  TRUNC(SYSDATE) BETWEEN C.FECH_INICIO
                                  AND     C.FECH_FIN
            AND    C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    C.COD_CAMP_CUPON = cCodCupon_in
            AND    C.ESTADO = 'A';
       dbms_output.put_line('cCodCupon_in:'||cCodCupon_in);
            cPermiteCupon := 'S';
       EXCEPTION
       WHEN NO_DATA_FOUND THEN
         cPermiteCupon := 'N';
       END;
       dbms_output.put_line('cPermiteCupon:'||cPermiteCupon);

       IF cPermiteCupon = 'S' THEN
             if cNumPedVta_in = 'N' then
                          dbms_output.put_line('entro a');
               OPEN curVta FOR
                     SELECT 'CUPON&'||C.COD_CAMP_CUPON||'&'||
                             C.DESC_CUPON||'&'||
                             C.MONT_MIN||'&'||
                             C.NUM_CUPON
                      FROM   VTA_CAMPANA_CUPON C
                      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    C.COD_CAMP_CUPON = cCod_Campana
            --DUBILLUZ 15.10.2011
            UNION
                      SELECT CP.COD_PROD
                      FROM   aux_prod_pedido_pc R,
                             VTA_CAMPANA_PROD CP
                      WHERE  R.IP_PC = cIpPc_in
                      AND    CP.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    CP.COD_CAMP_CUPON = cCod_Campana
                      AND    CP.COD_PROD = R.COD_PROD_ORIGINAL
                      ORDER  BY 1 ASC;

             else
                 dbms_output.put_line('entro g');
               OPEN curVta FOR
                     SELECT 'CUPON&'||C.COD_CAMP_CUPON||'&'||
                             C.DESC_CUPON||'&'||
                             C.MONT_MIN||'&'||
                             C.NUM_CUPON
                      FROM   VTA_CAMPANA_CUPON C
                      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    C.COD_CAMP_CUPON = cCod_Campana
            union
            SELECT CP.COD_PROD
                      FROM   vta_pedido_vta_det R,
                             VTA_CAMPANA_PROD CP
                      WHERE  r.cod_grupo_cia = cCodGrupoCia_in
            and    r.cod_local = cCodLocal_in
            and    r.num_ped_vta = cNumPedVta_in
            and    CP.COD_GRUPO_CIA = r.cod_grupo_cia
                      AND    CP.COD_CAMP_CUPON = cCod_Campana
                      AND    CP.COD_PROD = R.COD_PROD
                      ORDER  BY 1 ASC;
             end if;

       ELSE
          dbms_output.put_line('entro aqui');
               OPEN curVta FOR
               SELECT 'N' FROM DUAL;
       END IF;

    delete from aux_prod_pedido_pc where  IP_PC = cIpPc_in;

       RETURN curVta;

  END;

/*-----------------------------------------------------------------------------------------------------------------------
GOAL : Cargar Cantidad de Cupones a usar
History : 17-JUL-14   TCT Modifica generacion de cupon
-------------------------------------------------------------------------------------------------------------------------*/
 PROCEDURE VTA_GRABAR_PED_CUPON(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in       IN CHAR,
                                 cNumPedVta_in      IN CHAR,
                                 cCodCupon_in       IN CHAR,
                                 cCantidad_in       IN NUMBER,
                                 cLoginUsu_in       IN CHAR,
                                 cTipo              IN CHAR,
                                 nMontConsumoCamp_in IN CHAR)
  IS


  vPermiteCamp    char(1);

  nUnidPedido     number;

  nMontoMin       number;
  nUnidMin        number;
  nNumCuponesRegalo number;
  vIndMultiplo_Mont char(1);
  vIndMultiplo_Unid char(1);
  nMaxCupones number;

  nCantidadNuevaCupones number := 0;
  nCantCupon_Monto number := 0;
  nCantCupon_Unidad number := 0;

  nMontConsumoCamp NUMBER := 0;
  BEGIN
   --- 16-jul-14  TCT Debug
   --- sp_graba_log('en VTA_GRABAR_PED_CUPON, Graba Cantidad Cupones!!!');
   ---


    IF cTipo = 'M' THEN
      INSERT INTO Vta_Pedido_Cupon
      (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,
       COD_CAMP_CUPON,CANTIDAD,USU_CREA_PED_CUPON)
      VALUES
      (cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,
       cCodCupon_in,cCantidad_in,cLoginUsu_in);
    ELSIF cTipo = 'C' THEN

      SELECT TO_NUMBER(nMontConsumoCamp_in,'9999999999.999')
      INTO   nMontConsumoCamp
      FROM DUAL;

      SELECT nvl(MONT_MIN,0),nvl(C.UNID_MIN,0),nvl(NUM_CUPON,1),
             IND_MULTIPLO_MONT,IND_MULTIPLO_UNIDAD,nvl(MAX_CUPONES,9999)
      INTO   nMontoMin,nUnidMin,nNumCuponesRegalo,
             vIndMultiplo_Mont,vIndMultiplo_Unid,
             nMaxCupones
      FROM   VTA_CAMPANA_CUPON C
      WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    C.COD_CAMP_CUPON = cCodCupon_in;

      SELECT SUM(D.CANT_ATENDIDA/D.VAL_FRAC)
      into   nUnidPedido
      FROM   VTA_PEDIDO_VTA_DET D,
             VTA_CAMPANA_PROD CD
      WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    D.COD_LOCAL = cCodLocal_in
      AND    D.NUM_PED_VTA = cNumPedVta_in
      AND    CD.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    CD.COD_CAMP_CUPON = cCodCupon_in
      AND    CD.COD_PROD = D.COD_PROD;

     if nMontoMin != 0 then
        if vIndMultiplo_Mont = 'S' then
           nCantCupon_Monto := trunc(nMontConsumoCamp/nMontoMin);
        else
           if trunc(nMontConsumoCamp/nMontoMin) > 0 then
              nCantCupon_Monto := 1;
           end if;
        end if;
     elsif nMontoMin = 0 then
           if vIndMultiplo_Mont = 'S' then
           nCantCupon_Monto := nMontConsumoCamp;
           end if;
     end if;

     if nUnidMin != 0 then
        if vIndMultiplo_Unid = 'S' then
             nCantCupon_Unidad := trunc(nUnidPedido/nUnidMin);
        else
           if trunc(nUnidPedido/nUnidMin) > 0 then
              nCantCupon_Unidad := 1;
           end if;
        end if;
     end if;

     if nCantCupon_Monto != 0 and nCantCupon_Unidad !=0 then
        if nCantCupon_Monto >= nCantCupon_Unidad then
           nCantidadNuevaCupones :=  nCantCupon_Unidad;
        else
             nCantidadNuevaCupones :=  nCantCupon_Monto;
        end if;
      else
        if nCantCupon_Monto >= nCantCupon_Unidad then
           nCantidadNuevaCupones :=  nCantCupon_Monto;
        else
             nCantidadNuevaCupones :=  nCantCupon_Unidad;
        end if;
      end if;

    nCantidadNuevaCupones := nCantidadNuevaCupones*nNumCuponesRegalo;

     if nCantidadNuevaCupones > nMaxCupones then
        nCantidadNuevaCupones := nMaxCupones;
     end if;

     DBMS_OUTPUT.put_line(''||nCantidadNuevaCupones);
     if nCantidadNuevaCupones > 0 then
         INSERT INTO Vta_Pedido_Cupon
          (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,
           COD_CAMP_CUPON,CANTIDAD,USU_CREA_PED_CUPON)
          VALUES
          (cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,
           cCodCupon_in,nCantidadNuevaCupones,cLoginUsu_in);
     end if;

   end if;

  END;

/* ************************************************************************* */
  FUNCTION VTA_OBTIENE_PROD_SUG(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                cCodProd_in     IN CHAR,
                                cCantVta_in     IN NUMBER)
     RETURN FarmaCursor
  IS
    curProd FarmaCursor;
    curVta FarmaCursor;
    totalventa  NUMBER(8,3);
    totalventaSug  NUMBER(8,3);
    total  NUMBER(8,3);
    stockUni  NUMBER(6);
    stockFrac NUMBER;
    vValPrecLocal      LGT_PROD_LOCAL.VAL_PREC_VTA%TYPE;
    vValFracSug        LGT_PROD.VAL_FRAC_VTA_SUG%TYPE;
    vPrecVtaSug        LGT_PROD_LOCAL.VAL_PREC_VTA_SUG%TYPE;
    vCodProd           LGT_PROD.COD_PROD%TYPE;
    vDescUnidSug       LGT_PROD.DESC_UNID_VTA_SUG%TYPE;
    vDescProd          LGT_PROD.DESC_PROD%TYPE;
    vPrecVtaListaSug   LGT_PROD_LOCAL.VAL_PREC_LISTA_SUG%TYPE;
    vValFracLocal      LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    cantSug  NUMBER;
    cantSug2  NUMBER;
    indDev  CHAR(1);

    v_nCantSug VTA_PEDIDO_VTA_DET.CANT_ATENDIDA%TYPE;
    v_nTotalSug VTA_PEDIDO_VTA_DET.VAL_PREC_TOTAL%TYPE;
    v_nCantSugFrac LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nCantRes LGT_PROD_LOCAL.STK_FISICO%TYPE;
    cTotalVta_in VTA_PEDIDO_VTA_DET.VAL_PREC_TOTAL%TYPE;
    v_nCantPorcSug NUMBER;

    v_nFracSug LGT_PROD.VAL_FRAC_VTA_SUG%TYPE;
    v_nFracLocal LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    v_nPrecSug LGT_PROD_LOCAL.VAL_PREC_VTA_SUG%TYPE;
    v_nStkSug LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nPrecVta LGT_PROD_LOCAL.VAL_PREC_VTA%TYPE;
    v_nPorcSug PBL_LOCAL.PORC_MIN_SUG%TYPE;
  BEGIN

    SELECT P.VAL_FRAC_VTA_SUG,L.VAL_FRAC_LOCAL,
           --L.VAL_PREC_VTA_SUG, --ERIOS 04/06/2008
           CEIL(VAL_PREC_VTA_SUG*100)/100 +
                         CASE WHEN (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) = 0.0 THEN 0.0
                              WHEN (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) <= 0.5 THEN
                                   (0.5 -( (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) ))/10
                              ELSE (1.0 -( (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) ))/10 END ,
           TRUNC(((L.STK_FISICO)*P.VAL_FRAC_VTA_SUG)/L.VAL_FRAC_LOCAL),
           --L.VAL_PREC_VTA
           (L.VAL_PREC_VTA*(1+LOC.PORC_DSCTO_CASTIGO/100)),
           LOC.PORC_MIN_SUG
      INTO v_nFracSug,v_nFracLocal,
           v_nPrecSug,
           v_nStkSug,
           v_nPrecVta,
           v_nPorcSug
    FROM LGT_PROD P,
         LGT_PROD_LOCAL L,
         PBL_LOCAL LOC
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND L.COD_LOCAL = cCodLocal_in
          AND L.COD_PROD = cCodProd_in
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD
          AND L.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
          AND L.COD_LOCAL = LOC.COD_LOCAL;

    --ERIOS 03/06/2008 Calcula total de vta fraccionada
    v_nCantSugFrac := FLOOR((cCantVta_in*v_nFracSug)/v_nFracLocal);
    DBMS_OUTPUT.put_line('v_nCantSugFrac: '||v_nCantSugFrac);
    v_nCantRes := cCantVta_in - (v_nCantSugFrac*v_nFracLocal)/v_nFracSug;
    DBMS_OUTPUT.put_line('v_nCantRes: '||v_nCantRes);
    cTotalVta_in := (v_nCantSugFrac*v_nPrecSug)+(v_nCantRes*v_nPrecVta);
    cTotalVta_in := CEIL(cTotalVta_in*10)/10;
    DBMS_OUTPUT.put_line('cTotalVta_in: '||cTotalVta_in);

    v_nCantSug := CEIL((cCantVta_in*v_nFracSug)/v_nFracLocal);
    DBMS_OUTPUT.put_line('v_nCantSug: '||v_nCantSug);
    v_nTotalSug := v_nCantSug*v_nPrecSug;
    --v_nTotalSug := CEIL(v_nTotalSug*10)/10;
    DBMS_OUTPUT.put_line('v_nTotalSug: '||v_nTotalSug);
    v_nCantPorcSug := ((v_nCantSug*v_nFracLocal)/v_nFracSug)*(v_nPorcSug/100);
    DBMS_OUTPUT.put_line('v_nCantPorcSug: '||v_nCantPorcSug);

      --ERIOS 05.11.2014 Cuando no hay prec_lista_sug, se muestra prec_vta_sug
      OPEN curProd FOR
      SELECT v_nCantSug || 'Ã' ||
             TO_CHAR(v_nTotalSug,'999,9990.00') || 'Ã' ||
             P.DESC_UNID_VTA_SUG || 'Ã' ||
             TRUNC(((L.STK_FISICO)*P.VAL_FRAC_VTA_SUG)/L.VAL_FRAC_LOCAL) || 'Ã' ||
             ' ' || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             --TO_CHAR(L.VAL_PREC_VTA_SUG,'999,990.000') || 'Ã' ||
             TO_CHAR( CEIL(VAL_PREC_VTA_SUG*100)/100 +
                         CASE WHEN (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) = 0.0 THEN 0.0
                              WHEN (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) <= 0.5 THEN
                                   (0.5 -( (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) ))/10
                              ELSE (1.0 -( (CEIL(VAL_PREC_VTA_SUG*100)/10)-TRUNC(CEIL(VAL_PREC_VTA_SUG*100)/10) ))/10 END ,'999,990.000') || 'Ã' ||
             ( (L.STK_FISICO) - ((TRUNC(((L.STK_FISICO)*P.VAL_FRAC_VTA_SUG)/L.VAL_FRAC_LOCAL)*L.VAL_FRAC_LOCAL)/P.VAL_FRAC_VTA_SUG ) ) || 'Ã' ||
             P.VAL_FRAC_VTA_SUG || 'Ã' ||
             TO_CHAR(NVL(L.VAL_PREC_LISTA_SUG,L.VAL_PREC_VTA_SUG),'999,9990.00') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             TO_CHAR( cTotalVta_in,'999,9990.00') || 'Ã' ||
             CASE WHEN --(cTotalVta_in > v_nTotalSug) AND
                       (cCantVta_in >= v_nCantPorcSug) AND
                       (v_nStkSug >= v_nCantSug) THEN 'S'
                  ELSE 'N' END
      FROM LGT_PROD P,
           LGT_PROD_LOCAL L
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND L.COD_PROD = cCodProd_in
            AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
            AND P.COD_PROD = L.COD_PROD
            --AND cTotalVta_in > v_nTotalSug
            --AND v_nStkSug >= v_nCantSug
            ;
      RETURN curProd;
  END;
  /***************************************************************************/
  FUNCTION VERIFICA_CAMP_PROD(cCodGrupoCia_in IN CHAR,cCodCamp_in IN CHAR,
  cCodProd_in IN CHAR)
  RETURN CHAR
  IS
    v_cVerifica CHAR(1);
  BEGIN

    SELECT DECODE(COUNT(*),0,'N','S') INTO v_cVerifica
    FROM VTA_CAMPANA_PROD_USO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_CAMP_CUPON = cCodCamp_in
          AND COD_PROD = cCodProd_in
          AND val_prec_prom is not null
          AND imp_valor is not null
          AND ESTADO = 'A'
          ;

    RETURN v_cVerifica;
  END;
  /***************************************************************************/
   PROCEDURE GRABA_CUPON_PEDIDO_USO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cNumPedVta_in IN CHAR, cCodCupon_in IN CHAR, cCodCamp_in IN CHAR, vIndUso_in in CHAR,
  vIdUsu_in IN VARCHAR2)
  AS
    v_nCantCuponA INTEGER;
    v_cEstadoCupon VTA_CUPON.ESTADO%TYPE;

    v_cCodigoCamp VTA_CAMPANA_CUPON.COD_CAMP_CUPON%TYPE;
    v_cCodLocalEmi PBL_LOCAL.COD_LOCAL%TYPE;
    v_cSecuencial CHAR(5);

    vFechaIni date;
    vFechaFin date;

    nCantCampFid number;
  BEGIN

  SELECT COUNT(1)
  into   nCantCampFid
  FROM   VTA_CAMPANA_CUPON C
  WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
  AND    C.COD_CAMP_CUPON = trim(cCodCamp_in)
  AND    C.NUM_CUPON > 0;
--  AND    C.IND_FID = 'N';

  -- solo opera si la campaña no es de fidelizacion
  IF nCantCampFid > 0 then
        v_cCodigoCamp := SUBSTR(cCodCupon_in,1,5);
        v_cCodLocalEmi := SUBSTR(cCodCupon_in,6,3);
        v_cSecuencial := SUBSTR(cCodCupon_in,9,5);

        SELECT COUNT(*) INTO v_nCantCuponA
        FROM VTA_CUPON
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              --AND COD_LOCAL = cCodLocal_in JCORTEZ 17/08/2008
              AND COD_CUPON = cCodCupon_in;
        IF v_nCantCuponA = 0 THEN

        SELECT c.fech_inicio_uso,c.fech_fin_uso
        INTO   vFechaIni,vFechaFin
        FROM   vta_campana_cupon C
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND c.cod_camp_cupon = v_cCodigoCamp;


          INSERT INTO VTA_CUPON(COD_GRUPO_CIA,COD_LOCAL,COD_CUPON,ESTADO,USU_CREA_CUP_CAB,
          COD_CAMPANA,SEC_CUPON,FEC_INI,FEC_FIN)
          VALUES(cCodGrupoCia_in,cCodLocal_in,cCodCupon_in,'A',vIdUsu_in,
          v_cCodigoCamp,v_cSecuencial,vFechaIni,vFechaFin);
        END IF;

        --Verifica uso de
        SELECT ESTADO INTO v_cEstadoCupon
        FROM VTA_CUPON
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              --AND COD_LOCAL = cCodLocal_in JCORTEZ 17/08/2008
              AND COD_CUPON = cCodCupon_in
              --FOR UPDATE
              ;

        IF v_cEstadoCupon = 'A' THEN
           INSERT INTO VTA_CAMP_PEDIDO_CUPON(COD_GRUPO_CIA,COD_LOCAL,COD_CUPON,
           NUM_PED_VTA,ESTADO,USU_CREA_CUPON_PED,COD_CAMP_CUPON,IND_USO)
           VALUES(cCodGrupoCia_in,cCodLocal_in,cCodCupon_in,
           cNumPedVta_in,'S',vIdUsu_in,cCodCamp_in,vIndUso_in);

           /*UPDATE VTA_CUPON
           SET ESTADO = 'U',
               USU_MOD_CUP_CAB = vIdUsu_in,
               FEC_MOD_CUP_CAB = SYSDATE
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                 AND COD_LOCAL = cCodLocal_in
                 AND COD_CUPON = cCodCupon_in;*/
        END IF;

    end if;

  END;
  /***************************************************************************/
  PROCEDURE PROCESA_CAMP_PED_CUPON(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cNumPedVta_in IN CHAR,vIdUsu_in IN VARCHAR2)
  AS
    CURSOR curOrdenCampana IS
    SELECT DISTINCT COD_CAMP_CUPON
    FROM
    (
    SELECT C.COD_CAMP_CUPON
          FROM   VTA_CAMPANA_CUPON C,
                 VTA_CAMPANA_PROD_USO P,
                 VTA_PEDIDO_VTA_DET D,
                 VTA_CAMP_PEDIDO_CUPON PC
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    C.TIP_CAMPANA   = 'C'
          AND    C.ESTADO = 'A'
          AND    D.IND_ORIGEN_PROD NOT IN (IND_ORIGEN_OFER,IND_ORIGEN_REGA)
          AND    TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND  C.FECH_FIN_USO
          AND PC.ESTADO = 'S'
          AND PC.IND_USO = 'S'
          AND    C.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND    C.COD_CAMP_CUPON = P.COD_CAMP_CUPON
          AND    D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    D.COD_LOCAL = cCodLocal_in
          AND    D.NUM_PED_VTA = cNumPedVta_in
          AND    P.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND    P.COD_PROD = D.COD_PROD
          AND D.COD_GRUPO_CIA = PC.COD_GRUPO_CIA
          AND D.COD_LOCAL = PC.COD_LOCAL
          AND D.NUM_PED_VTA = PC.NUM_PED_VTA
          AND C.COD_GRUPO_CIA = PC.COD_GRUPO_CIA
          AND C.COD_CAMP_CUPON = PC.COD_CAMP_CUPON
                ORDER BY PRIORIDAD ASC ,RANKING ASC ,
                         DECODE(C.TIP_CUPON,'P',1,'M',2) DESC,
                         VALOR_CUPON DESC
                         );

    CURSOR curProdsPed(cCodCampana char) IS
    SELECT V.COD_PROD,V.SEC_PED_VTA_DET,V.CANT_ATENDIDA,
           (V.CANT_ATENDIDA/V.VAL_FRAC) AS CANT_UNID,
           V.VAL_PREC_TOTAL
    FROM VTA_PEDIDO_VTA_DET V
    WHERE V.COD_GRUPO_CIA = cCodGrupoCia_in
          AND V.COD_LOCAL = cCodLocal_in
          AND V.NUM_PED_VTA = cNumPedVta_in
          AND V.COD_PROD IN (
                  SELECT D.COD_PROD
                  FROM VTA_PEDIDO_VTA_DET D,
                       VTA_CAMP_PEDIDO_CUPON PC,
                       VTA_CAMPANA_CUPON C,
                       VTA_CAMPANA_PROD_USO PU
                  WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND D.COD_LOCAL = cCodLocal_in
                        AND D.NUM_PED_VTA = cNumPedVta_in
                        AND D.IND_ORIGEN_PROD NOT IN (IND_ORIGEN_OFER,IND_ORIGEN_REGA)
                        AND C.ESTADO = 'A'
                        AND PC.ESTADO = 'S'
                        AND PC.IND_USO = 'S'
                        AND PU.ESTADO = 'A'
                        AND C.COD_CAMP_CUPON = cCodCampana
                        AND D.COD_GRUPO_CIA = PC.COD_GRUPO_CIA
                        AND D.COD_LOCAL = PC.COD_LOCAL
                        AND D.NUM_PED_VTA = PC.NUM_PED_VTA
                        AND PC.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                        AND PC.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                        AND C.COD_GRUPO_CIA = PU.COD_GRUPO_CIA
                        AND C.COD_CAMP_CUPON = PU.COD_CAMP_CUPON
                        AND D.COD_GRUPO_CIA = PU.COD_GRUPO_CIA
                        AND D.COD_PROD = PU.COD_PROD
                  MINUS
                  SELECT D.COD_PROD
                  FROM VTA_PEDIDO_CUPON_DET D
                  WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND D.COD_LOCAL = cCodLocal_in
                        AND D.NUM_PED_VTA = cNumPedVta_in)
    ORDER BY V.SEC_PED_VTA_DET;

    v_vCupon VTA_CUPON.COD_CUPON%TYPE;
    v_cCodCamp VTA_CAMPANA_CUPON.COD_CAMP_CUPON%TYPE;
    v_nUnidMax VTA_CAMPANA_CUPON.UNID_MAX_PROD%TYPE;
    v_nValorCupon VTA_CAMPANA_CUPON.VALOR_CUPON%TYPE;
    v_cTipCup VTA_CAMPANA_CUPON.TIP_CUPON%TYPE;

    CURSOR curCamp IS
    SELECT C.COD_CAMP_CUPON,C.MONTO_MAX_DESCT
    FROM (SELECT COD_CAMP_CUPON,SUM(VAL_DSCTO_CUPON) AS VALOR_DSCTO
         FROM VTA_PEDIDO_CUPON_DET
         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in
          AND ESTADO = 'A'
          AND TIP_CUPON = 'P'
          GROUP BY COD_CAMP_CUPON) D,
         VTA_CAMPANA_CUPON C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          --AND D.VALOR_DSCTO <= C.MONTO_MAX_DESCT
          AND C.COD_CAMP_CUPON = D.COD_CAMP_CUPON
          ;

    CURSOR curCuponDet(cCodCamp_in IN CHAR) IS
    SELECT SEC_PED_VTA_DET,COD_PROD,VAL_DSCTO_CUPON
    FROM VTA_PEDIDO_CUPON_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in
          AND COD_CAMP_CUPON = cCodCamp_in
    ORDER BY SEC_PED_VTA_DET;

    v_nAuxMontoDesct VTA_CAMPANA_CUPON.MONTO_MAX_DESCT%TYPE;
    v_nAuxDesct VTA_CAMPANA_CUPON.MONTO_MAX_DESCT%TYPE;

    v_nCantCupon INTEGER;

    -- variables nuevos para nueva metodologia de cupones
    -- dubilluz 23.07.2008
    v_nMontoPedido number;
    v_nMontoCupones number;

    CURSOR curCuponesValidos IS
    SELECT D.COD_GRUPO_CIA CIA,D.COD_LOCAL LOCAL,D.NUM_PED_VTA  NUMPED,
           C.COD_FORMA_PAGO,SUM(C.VALOR_CUPON) monto_1,SUM(C.VALOR_CUPON) monto_2,
           'ACT_CUPON_DCTO' USER_CREA
    FROM VTA_CAMP_PEDIDO_CUPON D,
         VTA_CAMPANA_CUPON C
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_PED_VTA = cNumPedVta_in
          AND D.ESTADO = 'S'
          AND D.IND_USO = 'S'
          AND C.TIP_CUPON = 'M'
          AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND D.COD_CAMP_CUPON = C.COD_CAMP_CUPON
    GROUP BY D.COD_GRUPO_CIA,D.COD_LOCAL,D.NUM_PED_VTA ,C.COD_FORMA_PAGO
    ;
  BEGIN
    
   IF PTOVENTA_MDIRECTA.GET_VAL_GEN_STRING(COD_IND_EMI_CUPON) = 'S' THEN                 --ASOSA - 10/04/2015 - NECUPYAYAYAYA
               /*FOR vCurCampana IN curOrdenCampana
               LOOP
                  FOR fila IN curProdsPed(vCurCampana.Cod_Camp_Cupon)
                  LOOP

                    v_vCupon := DETERMINA_USO_CUPON(cCodGrupoCia_in, cCodLocal_in,cNumPedVta_in,fila.COD_PROD,
                                                   vCurCampana.Cod_Camp_Cupon);

                    IF v_vCupon <> '00000' THEN
                    SELECT C.COD_CAMP_CUPON,C.UNID_MAX_PROD,C.VALOR_CUPON,C.TIP_CUPON
                      INTO v_cCodCamp, v_nUnidMax, v_nValorCupon, v_cTipCup
                    FROM VTA_CAMP_PEDIDO_CUPON PC,
                         VTA_CAMPANA_CUPON C
                    WHERE PC.COD_GRUPO_CIA = cCodGrupoCia_in
                          AND PC.COD_LOCAL = cCodLocal_in
                          AND PC.COD_CUPON = v_vCupon
                          AND PC.NUM_PED_VTA = cNumPedVta_in
                          AND PC.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                          AND PC.COD_CAMP_CUPON = C.COD_CAMP_CUPON;

                    INSERT INTO VTA_PEDIDO_CUPON_DET(COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,
                    SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,
                    VAL_PREC_TOTAL,COD_CUPON,COD_CAMP_CUPON,
                    CANT_CAMP_CUPON,
                    VAL_DSCTO_CUPON,
                    USU_CREA,
                    CANT_UNID,TIP_CUPON)
                    VALUES(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,
                    fila.SEC_PED_VTA_DET,fila.COD_PROD,fila.CANT_ATENDIDA,
                    fila.VAL_PREC_TOTAL,v_vCupon,v_cCodCamp,
                    CASE WHEN fila.CANT_UNID > v_nUnidMax THEN v_nUnidMax
                         ELSE fila.CANT_UNID
                    END,
                    CASE WHEN fila.CANT_UNID > v_nUnidMax THEN
                              (((fila.VAL_PREC_TOTAL*v_nUnidMax)/fila.CANT_UNID)*v_nValorCupon)/100
                         ELSE (fila.VAL_PREC_TOTAL*v_nValorCupon)/100
                    END,
                    vIdUsu_in,
                    fila.CANT_UNID,v_cTipCup
                    );
                    END IF;
                  END LOOP;

                END LOOP;*/

                --Valida campanas
                /*UPDATE VTA_PEDIDO_CUPON_DET
                SET ESTADO = 'I'
                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                        AND COD_LOCAL = cCodLocal_in
                        AND NUM_PED_VTA = cNumPedVta_in
                        AND COD_CAMP_CUPON NOT IN (
                  SELECT C.COD_CAMP_CUPON
                  FROM (SELECT COD_CAMP_CUPON,
                         SUM(CANT_UNID) AS TOT_UNID,SUM(VAL_PREC_TOTAL) AS TOT_PREC
                       FROM VTA_PEDIDO_CUPON_DET
                       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                        AND COD_LOCAL = cCodLocal_in
                        AND NUM_PED_VTA = cNumPedVta_in
                        --AND ESTADO = 'A'
                        GROUP BY COD_CAMP_CUPON) D,
                       VTA_CAMPANA_CUPON C
                  WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND C.UNID_MIN_USO <= D.TOT_UNID
                        AND C.MONT_MIN_USO <= D.TOT_PREC
                        AND C.COD_CAMP_CUPON = D.COD_CAMP_CUPON );*/

                --Determina Max monto descuento
                /*FOR camp IN curCamp
                LOOP
                  v_nAuxMontoDesct := camp.MONTO_MAX_DESCT;

                  FOR cuponDet IN curCuponDet(camp.COD_CAMP_CUPON)
                  LOOP
                    v_nAuxMontoDesct := v_nAuxMontoDesct - cuponDet.VAL_DSCTO_CUPON;

                    IF v_nAuxMontoDesct < 0 THEN
                      v_nAuxDesct := cuponDet.VAL_DSCTO_CUPON + v_nAuxMontoDesct;
                      IF v_nAuxDesct < 0 THEN
                        v_nAuxDesct := 0;
                      END IF;

                      UPDATE VTA_PEDIDO_CUPON_DET
                      SET VAL_DSCTO_CUPON_DET = v_nAuxDesct,
                          VAL_PREC_TOTAL_CUPON = VAL_PREC_TOTAL-v_nAuxDesct,
                          VAL_PREC_VTA_CUPON = (VAL_PREC_TOTAL-v_nAuxDesct)/CANT_ATENDIDA,
                          FEC_MOD = SYSDATE,
                          USU_MOD = vIdUsu_in
                      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                            AND COD_LOCAL = cCodLocal_in
                            AND NUM_PED_VTA = cNumPedVta_in
                            AND SEC_PED_VTA_DET = cuponDet.SEC_PED_VTA_DET;
                    ELSE
                      UPDATE VTA_PEDIDO_CUPON_DET
                      SET VAL_DSCTO_CUPON_DET = cuponDet.VAL_DSCTO_CUPON,
                          VAL_PREC_TOTAL_CUPON = VAL_PREC_TOTAL-cuponDet.VAL_DSCTO_CUPON,
                          VAL_PREC_VTA_CUPON = (VAL_PREC_TOTAL-cuponDet.VAL_DSCTO_CUPON)/CANT_ATENDIDA,
                          FEC_MOD = SYSDATE,
                          USU_MOD = vIdUsu_in
                      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                            AND COD_LOCAL = cCodLocal_in
                            AND NUM_PED_VTA = cNumPedVta_in
                            AND SEC_PED_VTA_DET = cuponDet.SEC_PED_VTA_DET;
                    END IF;
                  END LOOP;

                END LOOP;*/

                /*UPDATE VTA_PEDIDO_CUPON_DET
                SET ESTADO = 'I'
                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                            AND COD_LOCAL = cCodLocal_in
                            AND NUM_PED_VTA = cNumPedVta_in
                            AND VAL_DSCTO_CUPON_DET = 0;

                --Aplica Descuentos
                SELECT COUNT(*) INTO v_nCantCupon
                FROM VTA_PEDIDO_CUPON_DET
                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                      AND COD_LOCAL = cCodLocal_in
                      AND NUM_PED_VTA = cNumPedVta_in
                      AND VAL_DSCTO_CUPON_DET > 0
                      AND ESTADO = 'A'
                      AND TIP_CUPON = 'P';*/

                /*IF v_nCantCupon > 0 THEN
                  --Actualiza detalle
                  UPDATE VTA_PEDIDO_VTA_DET D
                  SET (D.VAL_PREC_VTA,D.VAL_PREC_TOTAL) = (SELECT C.VAL_PREC_VTA_CUPON,C.VAL_PREC_TOTAL_CUPON
                          FROM VTA_PEDIDO_CUPON_DET C
                        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND C.COD_LOCAL = cCodLocal_in
                              AND C.NUM_PED_VTA = cNumPedVta_in
                              AND C.VAL_DSCTO_CUPON_DET > 0
                              AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                              AND C.COD_LOCAL = D.COD_LOCAL
                              AND C.NUM_PED_VTA = D.NUM_PED_VTA
                              AND C.SEC_PED_VTA_DET = D.SEC_PED_VTA_DET),
                      D.USU_MOD_PED_VTA_DET = 'ACT_CUPON_DCTO',
                      D.FEC_MOD_PED_VTA_DET = SYSDATE
                  WHERE EXISTS (SELECT 1
                  FROM VTA_PEDIDO_CUPON_DET C
                  WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND C.COD_LOCAL = cCodLocal_in
                        AND C.NUM_PED_VTA = cNumPedVta_in
                        AND C.VAL_DSCTO_CUPON_DET > 0
                        AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                        AND C.COD_LOCAL = D.COD_LOCAL
                        AND C.NUM_PED_VTA = D.NUM_PED_VTA
                        AND C.SEC_PED_VTA_DET = D.SEC_PED_VTA_DET);
                  --Actualiza cabecera
                  UPDATE VTA_PEDIDO_VTA_CAB V
                  SET ( V.VAL_NETO_PED_VTA,
                        V.VAL_REDONDEO_PED_VTA,
                        V.VAL_IGV_PED_VTA,
                        V.VAL_DCTO_PED_VTA) = (SELECT ROUND(SUM(D.VAL_PREC_TOTAL),2),
                                                    0,
                                                    SUM( CASE WHEN D.VAL_IGV = 0 THEN 0
                                                              ELSE D.VAL_PREC_TOTAL-( D.VAL_PREC_TOTAL/(1+(D.VAL_IGV/100)) )
                                                         END),
                                                    V.VAL_BRUTO_PED_VTA-SUM(D.VAL_PREC_TOTAL)
                        FROM VTA_PEDIDO_VTA_DET D
                        WHERE D.COD_GRUPO_CIA = V.COD_GRUPO_CIA
                              AND D.COD_LOCAL = V.COD_LOCAL
                              AND D.NUM_PED_VTA = V.NUM_PED_VTA
                              ),
                       V.USU_MOD_PED_VTA_CAB = 'ACT_CUPON_DCTO',
                       V.FEC_MOD_PED_VTA_CAB = SYSDATE
                  WHERE V.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND V.COD_LOCAL = cCodLocal_in
                        AND V.NUM_PED_VTA = cNumPedVta_in;
                END IF;*/

                /*--2 se inserta las formas de pago
                INSERT INTO TMP_FORMA_PAGO_PED_CUPON(COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,
                COD_FORMA_PAGO,IM_PAGO,IM_TOTAL_PAGO,USU_CREA)
                SELECT cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,
                       C.COD_FORMA_PAGO,SUM(C.VALOR_CUPON),SUM(C.VALOR_CUPON),'ACT_CUPON_DCTO'
                FROM VTA_CAMP_PEDIDO_CUPON D,
                     VTA_CAMPANA_CUPON C
                WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND D.COD_LOCAL = cCodLocal_in
                      AND D.NUM_PED_VTA = cNumPedVta_in
                      AND D.ESTADO = 'S'
                      AND D.IND_USO = 'S'
                      AND C.TIP_CUPON = 'M'
                      AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                      AND D.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                GROUP BY C.COD_FORMA_PAGO
                ;

                --3 SE ACTUALIZA EL INDICADOR DE USO DEL CUPON
                UPDATE VTA_CAMP_PEDIDO_CUPON
                SET IND_USO = 'N'
                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                      AND COD_LOCAL = cCodLocal_in
                      AND NUM_PED_VTA = cNumPedVta_in
                      AND ESTADO = 'S'
                      AND IND_USO = 'S'
                      AND COD_CUPON IN (SELECT DISTINCT COD_CUPON
                                      FROM VTA_PEDIDO_CUPON_DET
                                      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND COD_LOCAL = cCodLocal_in
                                            AND NUM_PED_VTA = cNumPedVta_in
                                            AND ESTADO = 'A'
                                            AND TIP_CUPON = 'P')
                ;*/

                -- MODIFICACION
                -- DUBILLUZ 23.07.2008
                --SE ASUME QUE EL MONTO DE LOS CUPONES ES SIEMPRE SOLES, POR LO QUE LAS
                --FORMAS DE PAGO DEBEN DE ESTAR EN SOLES
                SELECT C.VAL_NETO_PED_VTA
                INTO   v_nMontoPedido
                FROM   VTA_PEDIDO_VTA_CAB C
                WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
                AND    C.COD_LOCAL     = cCodLocal_in
                AND    C.NUM_PED_VTA   = cNumPedVta_in;

                FOR vCurFPago IN curCuponesValidos
                LOOP
                    IF v_nMontoPedido > 0 THEN
                        if vCurFPago.Monto_1 <= v_nMontoPedido then
                           INSERT INTO TMP_FORMA_PAGO_PED_CUPON
                           (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,COD_FORMA_PAGO,IM_PAGO,IM_TOTAL_PAGO,USU_CREA)
                           VALUES
                           (vCurFPago.Cia,vCurFPago.Local,vCurFPago.Numped,
                           vCurFPago.Cod_Forma_Pago,vCurFPago.Monto_1,vCurFPago.Monto_2,vCurFPago.User_Crea);
                        ELSE
                           INSERT INTO TMP_FORMA_PAGO_PED_CUPON
                           (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,COD_FORMA_PAGO,IM_PAGO,IM_TOTAL_PAGO,USU_CREA)
                           VALUES
                           (vCurFPago.Cia,vCurFPago.Local,vCurFPago.Numped,
                           vCurFPago.Cod_Forma_Pago,v_nMontoPedido,v_nMontoPedido,vCurFPago.User_Crea);
                        end if;
                        v_nMontoPedido := v_nMontoPedido - vCurFPago.Monto_1;
                    END IF;
                END LOOP;
END IF;
  END;
  /***************************************************************************/
   FUNCTION DETERMINA_USO_CUPON(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cNumPedVta_in IN CHAR,cCodProd_in IN CHAR,cCodCampana_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vCupon VTA_CUPON.COD_CUPON%TYPE := '00000';

    /*CURSOR curCupones(nPrio_in IN NUMBER, nTip_in IN NUMBER) IS
    SELECT PC.COD_CUPON,C.VALOR_CUPON,MAX(C.PRIORIDAD)
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_CAMP_PEDIDO_CUPON PC,
         VTA_CAMPANA_CUPON C,
         VTA_CAMPANA_PROD_USO PU
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_PED_VTA = cNumPedVta_in
          AND D.COD_PROD = cCodProd_in
          AND D.IND_ORIGEN_PROD NOT IN (IND_ORIGEN_OFER,IND_ORIGEN_REGA)
          AND C.ESTADO = 'A'
          AND PC.ESTADO = 'S'
          AND PU.ESTADO = 'A'
          AND NVL(C.PRIORIDAD,0) = nPrio_in
          AND NVL(C.TIP_CUPON,' ') = DECODE(nTip_in,1,'P',2,'M',' ')
          AND D.COD_GRUPO_CIA = PC.COD_GRUPO_CIA
          AND D.COD_LOCAL = PC.COD_LOCAL
          AND D.NUM_PED_VTA = PC.NUM_PED_VTA
          AND PC.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND PC.COD_CAMP_CUPON = C.COD_CAMP_CUPON
          AND C.COD_GRUPO_CIA = PU.COD_GRUPO_CIA
          AND C.COD_CAMP_CUPON = PU.COD_CAMP_CUPON
          AND D.COD_GRUPO_CIA = PU.COD_GRUPO_CIA
          AND D.COD_PROD = PU.COD_PROD
    GROUP BY PC.COD_CUPON,C.VALOR_CUPON;*/

    CURSOR curCupones IS
    SELECT PC.COD_CUPON,C.VALOR_CUPON
    FROM   VTA_CAMP_PEDIDO_CUPON PC,
           VTA_CAMPANA_CUPON C
    WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    C.COD_CAMP_CUPON = cCodCampana_in
    AND    PC.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    PC.COD_LOCAL = cCodLocal_in
    AND    PC.NUM_PED_VTA = cNumPedVta_in
    AND    PC.COD_GRUPO_CIA = C.COD_GRUPO_CIA
    AND    PC.COD_CAMP_CUPON = C.COD_CAMP_CUPON
    AND    C.ESTADO = 'A'
    AND    PC.ESTADO = 'S'
    AND    cCodProd_in NOT IN (SELECT DE.COD_PROD
                                FROM   VTA_PEDIDO_CUPON_DET DE
                                WHERE  DE.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND    DE.COD_LOCAL = cCodLocal_in
                                AND    DE.NUM_PED_VTA = cNumPedVta_in);

    v_nAuxValor VTA_CAMPANA_CUPON.VALOR_CUPON%TYPE := 0;

    v_nPrio INTEGER;
    v_nTip INTEGER;


  BEGIN

    FOR fila IN curCupones
    LOOP
      IF fila.VALOR_CUPON > v_nAuxValor THEN
        v_vCupon := fila.COD_CUPON;
      END IF;
    END LOOP;
  --DBMS_OUTPUT.put_line('v_vCupon:'||v_vCupon);
    /*SELECT MAX(NVL(C.PRIORIDAD,0)),MAX( CASE C.TIP_CUPON
                                             WHEN 'P' THEN 1
                                             WHEN 'M' THEN 2
                                             ELSE 0 END)
           INTO v_nPrio, v_nTip
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_CAMP_PEDIDO_CUPON PC,
         VTA_CAMPANA_CUPON C,
         VTA_CAMPANA_PROD_USO PU
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_PED_VTA = cNumPedVta_in
          AND D.COD_PROD = cCodProd_in
          AND D.IND_ORIGEN_PROD NOT IN (IND_ORIGEN_OFER,IND_ORIGEN_REGA)
          AND C.ESTADO = 'A'
          AND PC.ESTADO = 'S'
          AND PU.ESTADO = 'A'
          AND D.COD_GRUPO_CIA = PC.COD_GRUPO_CIA
          AND D.COD_LOCAL = PC.COD_LOCAL
          AND D.NUM_PED_VTA = PC.NUM_PED_VTA
          AND PC.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND PC.COD_CAMP_CUPON = C.COD_CAMP_CUPON
          AND C.COD_GRUPO_CIA = PU.COD_GRUPO_CIA
          AND C.COD_CAMP_CUPON = PU.COD_CAMP_CUPON
          AND D.COD_GRUPO_CIA = PU.COD_GRUPO_CIA
          AND D.COD_PROD = PU.COD_PROD;

    FOR fila IN curCupones(v_nPrio, v_nTip)
    LOOP
      IF fila.VALOR_CUPON > v_nAuxValor THEN
        v_vCupon := fila.COD_CUPON;
      END IF;
    END LOOP;*/

    RETURN v_vCupon;
  END;
/*--------------------------------------------------------------------------------------------------------------------------
GOAL : Cargar Todos los Cupones Aplicables al Numero de Pedido
History : 17-JUL-14 TCT Modifica la creacion de cupones ya no debe usar el codigo de local, en su lugar se usara
          7 digitos para el correlativo de la campaña
---------------------------------------------------------------------------------------------------------------------------*/

  PROCEDURE VTA_PROCESO_CAMPANA_CUPON(cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cNumPedVta_in    IN CHAR,
                                      cLoginUsu_in     IN CHAR,
                                      cTipo            IN CHAR,
                                      pDniCli           IN CHAR DEFAULT NULL)
 IS

 --CURSOR curCampAplicables(numDia char, pDniCli CHAR);
  CURSOR curCampAplicables(numDia char, pDniCli CHAR,cSexo char,dFecNaci date ) IS
    --SELECT V.*,ROWNUM SEC_ORDEN
    SELECT V.COD_CAMP_CUPON,ROWNUM SEC_ORDEN
    FROM   (
      --SELECT C.COD_CAMP_CUPON
      SELECT  DISTINCT C.COD_CAMP_CUPON,PRIORIDAD,RANKING,TIP_CUPON,VALOR_CUPON
      FROM   VTA_CAMPANA_CUPON C,
             VTA_CAMPANA_PROD P,
             VTA_PEDIDO_VTA_DET D
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.TIP_CAMPANA   = cTipo
      AND    C.ESTADO = 'A'
      AND    TRUNC(SYSDATE) BETWEEN C.FECH_INICIO AND  C.FECH_FIN
      AND    C.COD_GRUPO_CIA = P.COD_GRUPO_CIA
      AND    C.COD_CAMP_CUPON = P.COD_CAMP_CUPON
      AND    D.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    D.COD_LOCAL = cCodLocal_in
      AND    D.NUM_PED_VTA = cNumPedVta_in
      AND    P.COD_GRUPO_CIA = D.COD_GRUPO_CIA
      AND    P.COD_PROD = D.COD_PROD
      AND    VTA_F_GET_IND_FID_EMI(cCodGrupoCia_in,
                                nvl2(trim(pDniCli),'S','N'),
                                  C.COD_CAMP_CUPON,
                                 dFecNaci,
                                 cSexo,
                                 c.Ind_Fid_Emi) = 'S'

      -- AND    DECODE(TRIM(pDniCli),NULL,'N',C.IND_FID) = C.IND_FID
      -- JMIRANDA 14.04.2010
     -- AND    (nvl2(trim(pDniCli),'S','N') = C.IND_FID_EMI OR nvl2(trim(pDniCli),'S','N') = 'S') --ASOSA 16.03.10

      -- agregado el filtro de los campos de SEXO y EDAD
         --JMIRANDA

/*         AND (nvl2(trim(pDniCli),'S','N') = C.IND_FID_EMI OR (C.TIPO_SEXO_E IS NULL OR C.TIPO_SEXO_E = cSexo))
         AND (nvl2(trim(pDniCli),'S','N') = C.IND_FID_EMI OR (C.FEC_NAC_INICIO_E IS NULL OR C.FEC_NAC_INICIO_E <= dFecNaci ))
         AND (nvl2(trim(pDniCli),'S','N') = C.IND_FID_EMI OR (C.FEC_NAC_FIN_E IS NULL OR C.FEC_NAC_FIN_E >= dFecNaci ))
  */

          --JMIRANDA
         /*AND (C.TIPO_SEXO_E IS NULL OR C.TIPO_SEXO_E = cSexo)
         AND (C.FEC_NAC_INICIO_E IS NULL OR C.FEC_NAC_INICIO_E <= dFecNaci )
         AND (C.FEC_NAC_FIN_E IS NULL OR C.FEC_NAC_FIN_E >= dFecNaci )
         */

      --fin de filtro de los campos de Sexo y Edad
      AND    C.COD_CAMP_CUPON IN (
                                           /* SELECT *
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
                                               AND X.TIP_CAMPANA=cTipo
                                               AND X.ESTADO='A'
                                               AND X.IND_CADENA='S'
                                               UNION
                                               SELECT Y.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON Y
                                               WHERE Y.COD_GRUPO_CIA='001'
                                               AND Y.TIP_CAMPANA=cTipo
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
                              DECODE(C.DIA_SEMANA,REGEXP_REPLACE(C.DIA_SEMANA,numDia,'S'),'N','S')
                              ) = 'S'
            ORDER BY PRIORIDAD ASC ,RANKING ASC ,
                     DECODE(C.TIP_CUPON,'P',1,'M',2) DESC,
                     VALOR_CUPON DESC
         )V;

  CURSOR curProdCampana(cCodGrupoCia_in  IN CHAR,
                        cCodLocal_in      IN CHAR,
                        cNumPedVta_in    IN CHAR,
                        cCodCampana_in IN CHAR) IS
    SELECT D.COD_PROD,D.VAL_PREC_TOTAL
    FROM   VTA_CAMPANA_PROD P,
           VTA_PEDIDO_VTA_DET D
    WHERE  P.COD_GRUPO_CIA = D.COD_GRUPO_CIA
    AND    P.COD_CAMP_CUPON = cCodCampana_in
    AND    P.COD_PROD = D.COD_PROD
    AND    D.COD_GRUPO_CIA  = cCodGrupoCia_in
    AND    D.COD_LOCAL      = cCodLocal_in
    AND    D.NUM_PED_VTA    = cNumPedVta_in
    AND    P.COD_PROD NOT IN (SELECT E.COD_PROD
                              FROM   TT_CAMPANA_PROD_PEDIDO E
                              WHERE  E.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND    E.COD_LOCAL = cCodLocal_in
                              AND    E.NUM_PED_VTA = cNumPedVta_in
                              AND    E.COD_CAMP_CUPON != cCodCampana_in);

  CURSOR curMontoCampanaPedido(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in      IN CHAR,
                               cNumPedVta_in    IN CHAR) is
  SELECT T.COD_GRUPO_CIA,T.COD_LOCAL,T.NUM_PED_VTA,
         T.COD_CAMP_CUPON,SUM(T.VAL_PREC_TOTAL) monto
  FROM   TT_CAMPANA_PROD_PEDIDO T
  WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
  AND    T.COD_LOCAL     = cCodLocal_in
  AND    T.NUM_PED_VTA   = cNumPedVta_in
  GROUP  BY T.COD_GRUPO_CIA,T.COD_LOCAL,T.NUM_PED_VTA,
            T.COD_CAMP_CUPON,T.ORD_CREACION
  ORDER BY T.ORD_CREACION ASC;


  nNumDia       VARCHAR(2);
  nSecCampana   NUMBER := 1;
  nMaxCampanaPedido   NUMBER;
  nCantCampPedido number;
  /**VARIABLE DE DATOS DE CLIENTE*/
  cSexo    char(1);
  dFecNaci date;
  /**FIN*/
    nListaNegraDNI number;
 BEGIN
   
 IF PTOVENTA_MDIRECTA.GET_VAL_GEN_STRING(COD_IND_EMI_CUPON) = 'S' THEN                 --ASOSA - 10/04/2015 - NECUPYAYAYAYA
       
       select count(1)
       into   nListaNegraDNI
       from   fid_dni_nulos f
       where  f.dni_cli  = pDniCli
       and    f.estado = 'A';

         IF nListaNegraDNI = 0 then

         nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);
         /*****************************************************************************/
         --OBTENIENDO EL SEXO Y LA FECHA DE NACIMIENTO DEL CLIENTE
         IF pDniCli is not null then
            SELECT CL.SEXO_CLI, trunc(CL.FEC_NAC_CLI) INTO cSexo, dFecNaci
            FROM PBL_CLIENTE CL
            WHERE CL.DNI_CLI = pDniCli;

            dbms_output.put_line('datos del cliente : sexo:'||cSexo||', fecha_nac:'||dFecNaci);
         end if;
          -- fin de agregado

          /*****************************************************************************/


         SELECT TO_NUMBER(T.LLAVE_TAB_GRAL,'999999')
         INTO   nMaxCampanaPedido
         FROM   PBL_TAB_GRAL T
         WHERE  T.ID_TAB_GRAL = 211;


            FOR vCampanas IN curCampAplicables(nNumDia, pDniCli, cSexo, dFecNaci)
            LOOP

               FOR vProdCamp IN curProdCampana(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,
                                               vCampanas.Cod_Camp_Cupon)
               LOOP
                   INSERT INTO TT_CAMPANA_PROD_PEDIDO
                               (COD_GRUPO_CIA,COD_LOCAL,
                                NUM_PED_VTA,ORD_CREACION,
                                COD_CAMP_CUPON,COD_PROD,
                                VAL_PREC_TOTAL)
                   VALUES
                   (cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,vCampanas.Sec_Orden,
                    vCampanas.Cod_Camp_Cupon,vProdCamp.Cod_Prod,vProdCamp.Val_Prec_Total);

               END LOOP;
            END LOOP;

            FOR vCampaPedido IN curMontoCampanaPedido(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in)
            LOOP

             SELECT COUNT(1)
             INTO   nCantCampPedido
             FROM   VTA_PEDIDO_CUPON C,
                    vta_campana_cupon camp
             WHERE  C.COD_GRUPO_CIA = vCampaPedido.Cod_Grupo_Cia
             AND    C.COD_LOCAL     = vCampaPedido.Cod_Local
             AND    C.NUM_PED_VTA   = vCampaPedido.Num_Ped_Vta
             and    c.cod_grupo_cia = camp.cod_grupo_cia
             and    c.cod_camp_cupon = camp.cod_camp_cupon
             and    camp.tip_campana = 'C';


            -- dbms_output.put_line('nMaxCampanaPedido: '||nMaxCampanaPedido);
             --       dbms_output.put_line('nCantCampPedido: '||nCantCampPedido);
             -- Se empieza a contar cupones desde el cero (0) por eso se valida con menor (>) y no mayor o igual
             if nMaxCampanaPedido > nCantCampPedido then
                VTA_GRABAR_PED_CUPON(vCampaPedido.Cod_Grupo_Cia,vCampaPedido.Cod_Local,
                                     vCampaPedido.Num_Ped_Vta,vCampaPedido.Cod_Camp_Cupon,
                                     0,cLoginUsu_in,cTipo,trim(to_char(vCampaPedido.Monto,'9999999999.99')));
             end if;

            END LOOP;
        end if;
END IF;
 END ;

 /*************************************************************/
 FUNCTION VTA_OBTIENE_INFO_CAMP(cCodGrupoCia_in IN CHAR,
                                cCodCamp_in     IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT A.COD_CAMP_CUPON || 'Ã' ||
             A.DESC_CUPON
      FROM VTA_CAMPANA_CUPON A
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
      AND A.COD_CAMP_CUPON=cCodCamp_in;
    RETURN curVta;
  END;

/*-------------------------------------------------------------------------------------------------------------------
GOAL : Devolver Informacion de Cupon
History : 18-JUL-14  TCT  Modifica para determinar a que campaña pertenece la Barra
---------------------------------------------------------------------------------------------------------------------*/
 FUNCTION VTA_OBTIENE_IND_CAMP(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cCodCupon_in    IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    v_cCodigoCamp VTA_CAMPANA_CUPON.COD_CAMP_CUPON%TYPE;
    v_cCodLocalEmi PBL_LOCAL.COD_LOCAL%TYPE;
  BEGIN

        --v_cCodigoCamp := SUBSTR(cCodCupon_in,1,5);
        --- < 18-JUL-14 TCT Nuevo Modo de Leer Codigo Cupon >
        v_cCodigoCamp:= fn_get_cod_campa(cCodGrupoCia_in,cCodCupon_in);
        --- < /18-JUL-14 TCT Nuevo Modo de Leer Codigo Cupon >

        v_cCodLocalEmi := SUBSTR(cCodCupon_in,6,3);


      OPEN curVta FOR
/*      SELECT Y.COD_CAMP_CUPON || 'Ã' ||
             Y.IND_MULTIUSO
      FROM VTA_CUPON X,
           VTA_CAMPANA_CUPON Y
      WHERE X.COD_GRUPO_CIA= cCodGrupoCia_in
      AND X.COD_GRUPO_CIA=Y.COD_GRUPO_CIA
      AND X.COD_CUPON=TRIM(cCodCupon_in)
      AND X.COD_CAMPANA=Y.COD_CAMP_CUPON;*/
      SELECT C.COD_CAMP_CUPON|| 'Ã' ||C.IND_MULTIUSO
      FROM   VTA_CAMPANA_CUPON C
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_CAMP_CUPON = v_cCodigoCamp;

    RETURN curVta;
  END;

  /***************************************************************/
   PROCEDURE GRABA_PROD_CAMP(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            cCodProd_in IN CHAR,
                            cValPrecTotal_in IN NUMBER)
  AS
   CANT NUMBER;

  BEGIN

  SELECT COUNT(*) INTO CANT
  FROM TT_COD_PROD_CAMP
  WHERE COD_PROD=cCodProd_in;

  IF(CANT=0)THEN
  INSERT INTO TT_COD_PROD_CAMP(COD_PROD,VAL_PREC_TOTAL) VALUES(cCodProd_in,cValPrecTotal_in);
  /*ELSE
  UPDATE TT_COD_PROD_CAMP
  SET VAL_PREC_TOTAL=cValPrecTotal_in
  WHERE COD_PROD=cCodProd_in;*/

  END IF;

  END;

  /********************************/
  PROCEDURE GRABA_CAMP(cCodGrupoCia_in IN CHAR,
                       cCodLocal_in IN CHAR,
                       cCodCamp IN CHAR,
                       cCodCupon IN CHAR)
  AS
  CANT NUMBER;
  BEGIN

  SELECT COUNT(*) INTO CANT
  FROM TT_COD_CAMP
  WHERE COD_CAMP_CUPON=cCodCamp
  AND COD_CUPON=cCodCupon;

  IF(CANT=0)THEN
  INSERT INTO TT_COD_CAMP(COD_CAMP_CUPON,COD_CUPON) VALUES(cCodCamp,cCodCupon);
  END IF;
  END;

  /*******************************************************/
  FUNCTION OBTIENE_TOTAL_AHORRO(cCodCia_in IN CHAR,
                                cTotalPedido_in IN NUMBER)
  RETURN CHAR
  IS

    v_cVerifica CHAR(1);
    v_nMonto1 number;
    v_nMonto2 number;
    v_nMontoA number;
    v_TotalAhorro  number;
  BEGIN

    SELECT  V2.TOTAL_VALOR ,SUM(V1.TOTAL) monto_2,
 CASE WHEN V2.TOTAL_VALOR>SUM(V1.TOTAL) THEN
           SUM(V1.TOTAL)
           WHEN  V2.TOTAL_VALOR<SUM(V1.TOTAL) THEN
           V2.TOTAL_VALOR
           END INTO v_nMonto1,v_nMonto2,v_nMontoA
    FROM --TT_COD_CAMP A,
         VTA_CAMPANA_CUPON C,
         VTA_CAMPANA_PROD_USO D,
         (SELECT COD_PROD,SUM(VAL_PREC_TOTAL) AS TOTAL
         FROM TT_COD_PROD_CAMP
         GROUP BY COD_PROD)V1,
         (SELECT X.COD_CAMP_CUPON,SUM(Y.VALOR_CUPON)AS TOTAL_VALOR
         FROM TT_COD_CAMP X,
              VTA_CAMPANA_CUPON Y
              WHERE X.COD_CAMP_CUPON=Y.COD_CAMP_CUPON
              GROUP BY X.COD_CAMP_CUPON)V2
    WHERE C.COD_GRUPO_CIA='001'
    AND C.TIP_CUPON = 'M'
    AND C.COD_CAMP_CUPON=V2.COD_CAMP_CUPON
    AND C.COD_CAMP_CUPON=D.COD_CAMP_CUPON
    AND D.COD_PROD=V1.COD_PROD
    GROUP BY  V2.TOTAL_VALOR;

    COMMIT;
    RETURN  TO_CHAR(v_nMontoA,'999999.99');
  EXCEPTION
      WHEN NO_DATA_FOUND THEN
           --ROLLBACK;
                RETURN TO_CHAR(0,'999999.99');

  END;

/******************************************************************************/
FUNCTION VALIDA_COD_BARRA(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cCadena_in      IN CHAR)
  RETURN CHAR
  IS
    CANT NUMBER;
  BEGIN

  SELECT COUNT(*) INTO  CANT
  FROM LGT_COD_BARRA X
  WHERE X.COD_BARRA=TRIM(cCadena_in);

  IF(CANT>0)THEN
  RETURN 'S';
  ELSE
  RETURN 'N';
  END IF;
  END;

    /*********************************************************************************/
  PROCEDURE VTA_P_UPDATE_DET_PED_VTA( cNumPedVta IN CHAR,
                                                                cCodLocal IN CHAR,
                                                                cCodGrupoCia IN CHAR,
                                                                cCodProd IN CHAR,
                                                                cCodCamp IN CHAR,
                                                                cPorcDcto_1 IN NUMBER,
                                                                cAhorro  IN NUMBER,
                                                                cPorcDctoCalc IN NUMBER,
                                                                cSecPedVtaDet IN NUMBER)

    AS

    BEGIN
        UPDATE VTA_PEDIDO_VTA_DET
        SET PORC_DCTO_1 = cPorcDcto_1,
        AHORRO = cAhorro,
        COD_CAMP_CUPON = cCodCamp,
        PORC_DCTO_CALC = cPorcDctoCalc
        WHERE NUM_PED_VTA = cNumPedVta
        AND COD_LOCAL = cCodLocal
        AND COD_GRUPO_CIA = cCodGrupoCia
        AND COD_PROD = cCodProd
        AND SEC_PED_VTA_DET = cSecPedVtaDet;
        ---COMMIT;
    EXCEPTION
    WHEN OTHERS THEN
      --RAISE;
      RAISE_APPLICATION_ERROR(-20998,'ERROR AL ACTUALIZAR EL DETALLE DEL PEDIDO PARA EL PRODUCTO:'||cCodProd||' .');
    END;
/************************************************************************************************************/

 /* FUNCTION VTA_F_CUR_OBT_DCTO_PROD(cCodGrupoCia_in IN CHAR,
                                                                                                         cCodLocal_in    IN CHAR,
                                                                                                         cNumPedVta_in      IN CHAR)
  RETURN FarmaCursor

  IS
  curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
          SELECT AHORRO
          FROM VTA_PEDIDO_VTA_DET
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in;

  RETURN curVta;
  END;*/


  PROCEDURE VTA_P_VALIDAR_VALOR_VTA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in IN CHAR,
                                    cNumPedVta_in IN CHAR)

    AS
    v_cIndValidarMonto    PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='N';
    v_cEmailErrorPtoVenta PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='joliva';
    v_nValNetoPedVta      VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_nValRedondeo        VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PED_VTA%TYPE;
    v_nSumaTotDet       NUMBER:=0;
    v_nSumaValorDet       NUMBER:=0;
    v_vDescLocal VARCHAR2(120);
    BEGIN

        SELECT TRIM(G.LLAVE_TAB_GRAL) INTO v_cIndValidarMonto
        FROM PBL_TAB_GRAL G
        WHERE G.ID_TAB_GRAL = 238;

        SELECT G.LLAVE_TAB_GRAL INTO v_cEmailErrorPtoVenta
        FROM PBL_TAB_GRAL  G
        WHERE G.ID_TAB_GRAL = 241;

        SELECT C.VAL_NETO_PED_VTA, C.VAL_REDONDEO_PED_VTA
        INTO v_nValNetoPedVta, v_nValRedondeo
        FROM  VTA_PEDIDO_VTA_CAB C
        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   C.COD_LOCAL     = cCodLocal_in
        AND   C.NUM_PED_VTA   = cNumPedVta_in;

        SELECT SUM(D.VAL_PREC_TOTAL) into v_nSumaValorDet
        FROM VTA_PEDIDO_VTA_DET D
        WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   D.COD_LOCAL     = cCodLocal_in
        AND   D.NUM_PED_VTA   = cNumPedVta_in;

        v_nSumaTotDet:= v_nSumaValorDet+v_nValRedondeo;

        IF ( v_nSumaTotDet <> v_nValNetoPedVta ) THEN

           --DESCRIPCION DE LOCAL
            SELECT COD_LOCAL ||' - '|| DESC_LOCAL
            INTO   v_vDescLocal
            FROM   PBL_LOCAL
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
            AND    COD_LOCAL = cCodLocal_in;

           -- ENVIANDO CORREO
           FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                           v_cEmailErrorPtoVenta,
                           'ERROR GENERAR PEDIDO: DIFER. TOTAL VALOR CABECERA Y DETALLE : '||v_vDescLocal,
                           'ALERTA',
                           '<H1>ERROR AL GENERAR PEDIDO DE VENTA</H1><BR>'||
                           '<i>inconsistencia de montos entre cabecera y detalle de pedido</i><BR>'||
                           '<br>CAB. PEDIDO : <b>'||to_char(v_nValNetoPedVta,'999,990.00')||' </b> &nbsp;&nbsp;&nbsp;=====>&nbsp; VAL_NETO_PED_VTA '||
                           '<br>DET.&nbsp; PEDIDO : <b>'||to_char(v_nSumaTotDet,'999,990.00')||' </b> &nbsp;&nbsp;&nbsp;=====>&nbsp; SUM(D.VAL_PREC_TOTAL):<b>'||
                           to_char(v_nSumaValorDet,'999,990.00')||'</b> + VAL_REDONDEO_PED_VTA: <B>'||to_char(v_nValRedondeo,'999,990.00')||'</B> )'||
                           '<BR><br> NUM_PEDIDO : <B>'||cNumPedVta_in||'</B>'||
                           '<BR> LOCAL : <B>'||v_vDescLocal||'</B>'||
                           '<BR><BR> FECHA : <B>'||to_char(SYSDATE,'dd/MM/yyyy HH24:MI:SS')||'</B>',
                           '',
                           FARMA_EMAIL.GET_EMAIL_SERVER,
                           TRUE);
            --generando el error en el aplicativo

           IF( v_cIndValidarMonto = 'S' ) THEN
                  RAISE_APPLICATION_ERROR(-20066,'NO SE LOGRO GENERAR EL PEDIDO. POR INCONSISTENCIA DEL VALOR DE VENTA.');
           END IF;
        END IF;

    EXCEPTION
    WHEN OTHERS THEN
      RAISE;
    END;

    /************************************************************************/
    FUNCTION VTA_F_GET_PRECIO_FINAL_VTA( cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cCodProd_in     IN CHAR,
                                         cCodCamp_in     IN CHAR,
                                         nPrecioVtaDcto_in IN NUMBER,
                                         nPrecioVta_in     IN NUMBER,
                                         nFracionVta_in    IN NUMBER)
    RETURN CHAR
    IS
        cIndicador   CHAR(1):='N';
        nCostoProm   NUMBER:= 0;
        nValorIgv    NUMBER:= 0;--valor del igv
        nPreciofinal NUMBER:= 0;--precio finals
        --cPrecioFinal  varchar(300):='';
        nPrecioVentaSinIgv NUMBER:= 0;
        nPrecioVtaDctoSinIvg NUMBER:=0;
        nValFracMax   NUMBER:=0;
        nValFracLocal NUMBER:=0;
    BEGIN

      --obtniendo el indicador de si c
      SELECT C.IND_VAL_COSTO_PROM
      INTO cIndicador
      FROM VTA_CAMPANA_CUPON C
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND   C.COD_CAMP_CUPON = cCodCamp_in;

     DBMS_OUTPUT.put_line('cIndicador:'||cIndicador);

      IF cIndicador = 'N' THEN--QUIER DECIR QUE NO DEBE PERMITIR VENDER POR DEBAJO DEL COSTO PROMEDIO

         --OBTIENE EL COSTO PROMEDIO
         SELECT P.VAL_PREC_PROM, I.PORC_IGV, P.VAL_MAX_FRAC
         INTO   nCostoProm, nValorIgv, nValFracMax
         FROM   LGT_PROD P, PBL_IGV I
         WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    P.COD_PROD      = cCodProd_in
         AND    P.COD_IGV       = I.COD_IGV;

         --OBTIENE EL PRECIO DE VENTA PUBLICO
         SELECT LP.VAL_FRAC_LOCAL
         INTO   nValFracLocal--nPrecioVentaSinIgv , nPrecioVenta,
         FROM   LGT_PROD_LOCAL LP
         WHERE  LP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    LP.COD_LOCAL     = cCodLocal_in
         AND    LP.COD_PROD      = cCodProd_in;

         DBMS_OUTPUT.put_line('nValFracMax:'||nValFracMax);
         DBMS_OUTPUT.put_line('nValFracLocal:'||nValFracLocal);
         DBMS_OUTPUT.put_line('nFracionVta_in:'||nFracionVta_in);
         DBMS_OUTPUT.put_line('nPrecioVta_in:'||nPrecioVta_in);
         DBMS_OUTPUT.put_line('nPrecioVtaDcto_in:'||nPrecioVtaDcto_in);
         DBMS_OUTPUT.put_line('nCostoProm:'||nCostoProm);
         nPrecioVentaSinIgv := (nPrecioVta_in*nFracionVta_in )/( 1 + ( nValorIgv/100 ) );
         nPrecioVtaDctoSinIvg := (nPrecioVtaDcto_in*nFracionVta_in ) / ( 1 + ( nValorIgv/100 ) );

         DBMS_OUTPUT.put_line('nPrecioVentaSinIgv:'||nPrecioVentaSinIgv);
         DBMS_OUTPUT.put_line('nPrecioVtaDctoSinIvg:'||nPrecioVtaDctoSinIvg);

         IF nPrecioVentaSinIgv < nCostoProm THEN
            nPreciofinal:= nPrecioVta_in; --TRIM(TO_CHAR(nPrecioVenta,'999999999999.9999999999999999'));--nPrecioVenta;
         ELSE
            IF nPrecioVtaDctoSinIvg < nCostoProm THEN
                DBMS_OUTPUT.put_line('nCostoProm:'||nCostoProm);
                nPreciofinal := (nCostoProm/nFracionVta_in) *( 1 + ( nValorIgv/100 ) );--TRIM(TO_CHAR(nCostoProm,'999999999999.9999999999999999'));--nCostoProm;

            ELSE
                nPreciofinal := nPrecioVtaDcto_in;
            END IF;
         END IF;

      ELSE
          nPreciofinal := nPrecioVtaDcto_in;
      END IF;

      RETURN TRIM(TO_CHAR(nPreciofinal,'999999999990.0000'));

    END;

    /* ********************************************************************************* */
    FUNCTION VTA_F_GET_MONTO_VALIDA_DATOS
    RETURN VARCHAR2
    is
      vRespta varchar2(500);
    begin
      begin
      select nvl(t.llave_tab_gral,0)
      into   vRespta
      from   pbl_tab_gral t
      where  t.id_tab_gral = 266;
      exception
      when no_data_found  then
           vRespta := '1000000';
      end;

      return vRespta;
    end;
/*---------------------------------------------------------------------------------------------------------------------------
GOAL : Devolver Datos de Cupon
History : 21-JUL-14  TCT  Modifica forma de Obtener el codigo camp cupon
----------------------------------------------------------------------------------------------------------------------------*/
 FUNCTION VTA_F_CURSOR_DATOS_CUPON ( cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCampCupon_in   IN CHAR,
                                        cCodCupon_in IN CHAR default '',
                                        -- dubilluz 13.06.2011
                                        cIndUsoEfectivo_in IN CHAR,
                                        cIndUsoTarjeta_in IN CHAR,
                                        cCodForma_Tarjeta_in IN CHAR
                                        )
    RETURN FarmaCursor
    AS
    curCupon FarmaCursor;
    v_vDescLocal VARCHAR2(120);

    v_cCodigoCamp VTA_CAMPANA_CUPON.COD_CAMP_CUPON%TYPE;
    v_cCodLocalEmi PBL_LOCAL.COD_LOCAL%TYPE;

    BEGIN
      ------------------
      -- DUBILLUZ - 14.05.2010
      if  Length(trim(cCodCupon_in)) = 0 or cCodCupon_in is null then

           select decode(cCampCupon_in,'12605','12613',cCampCupon_in)
           into   v_cCodigoCamp
           from   dual;

      else
        -- local 10
        -- v_cCodigoCamp := SUBSTR(cCodCupon_in,1,5); 21-JUL-2014 TCT Comment y Add Nueva call Function
        --- < 21-JUL-2014 TCT Comment y Add Nueva call Function >
        v_cCodigoCamp := ptoventa_vta.fn_get_cod_campa(ccodgrupocia_in => cCodGrupoCia_in,
                                           ccodcupon_in => cCampCupon_in);
        --- < /21-JUL-2014 TCT Comment y Add Nueva call Function >

        v_cCodLocalEmi := SUBSTR(cCodCupon_in,6,3);

        if v_cCodLocalEmi = '010' and v_cCodigoCamp = '12605' then
           v_cCodigoCamp := '12613';
        end if;
        ---jquispe; Cambio para campaña tena que no se pudo crear
        if v_cCodLocalEmi = '010' and v_cCodigoCamp = '12719' then
           v_cCodigoCamp := '12723';
        end if;

      -----

      end if;
      ------------------
      IF (cIndUsoEfectivo_in ||cIndUsoTarjeta_in || cCodForma_Tarjeta_in = 'NULLNULLNULL') OR
         (cIndUsoEfectivo_in ||cIndUsoTarjeta_in || cCodForma_Tarjeta_in = 'NNULLNULL')
       THEN


 OPEN curCupon FOR
       SELECT C.COD_CAMP_CUPON AS COD_CAMP_CUPON,
              NVL(C.DESC_CUPON,' ') AS DESC_CUPON,
              C.TIP_CUPON AS TIP_CUPON,
              C.VALOR_CUPON AS VALOR_CUPON,
              TRIM(to_char(NVL(C.MONT_MIN_USO, 0),'99999999.999')) AS MONT_MIN_USO,
              NVL(C.UNID_MIN_USO, 0) AS UNID_MIN_USO,
              NVL(C.UNID_MAX_PROD, 0) AS UNID_MAX_PROD,
              NVL(C.MONTO_MAX_DESCT, 0) AS MONTO_MAX_DESCT,

              --'N' AS IND_MULTIUSO,--POR DEFECTO INDICADOR MULTIUSO COMO N
              c.ind_multiuso AS IND_MULTIUSO,
              C.IND_FID AS IND_FID,
              '' AS COD_CUPON,
              nvl(C.IND_VAL_COSTO_PROM,'S') AS IND_VAL_COSTO_PROM,
              LPAD(C.PRIORIDAD, 8, '0') ||
              LPAD(C.RANKING, 8, '0') ||
              TIP_CUPON || TO_CHAR(100000 - VALOR_CUPON, '00000.000') AS ORDEN
       FROM   VTA_CAMPANA_CUPON C
       WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
       --AND    C.COD_CAMP_CUPON = cCampCupon_in;
       --DUBILLUZ - 14.05.2010
       AND    C.COD_CAMP_CUPON = v_cCodigoCamp;

      ELSE
       OPEN curCupon FOR
       SELECT C.COD_CAMP_CUPON AS COD_CAMP_CUPON,
              NVL(C.DESC_CUPON,' ') AS DESC_CUPON,
              C.TIP_CUPON AS TIP_CUPON,
              --C.VALOR_CUPON AS VALOR_CUPON,
             NVL(fpc.descuento_personalizado,0) AS VALOR_CUPON, -- dubilluz 09.06.2011
              TRIM(to_char(NVL(C.MONT_MIN_USO, 0),'99999999.999')) AS MONT_MIN_USO,
              NVL(C.UNID_MIN_USO, 0) AS UNID_MIN_USO,
              NVL(C.UNID_MAX_PROD, 0) AS UNID_MAX_PROD,
              NVL(C.MONTO_MAX_DESCT, 0) AS MONTO_MAX_DESCT,

              --'N' AS IND_MULTIUSO,--POR DEFECTO INDICADOR MULTIUSO COMO N
              c.ind_multiuso AS IND_MULTIUSO,
              C.IND_FID AS IND_FID,
              '' AS COD_CUPON,
              nvl(C.IND_VAL_COSTO_PROM,'S') AS IND_VAL_COSTO_PROM,
              LPAD(C.PRIORIDAD, 8, '0') ||
              LPAD(C.RANKING, 8, '0') ||
              TIP_CUPON || TO_CHAR(100000 - VALOR_CUPON, '00000.000') AS ORDEN
       FROM   VTA_CAMPANA_CUPON C,
              (
               select distinct cf.cod_camp_cupon,cf.cod_grupo_cia,cf.porc_dcto descuento_personalizado
                from   vta_camp_x_fpago_uso cf,
                       vta_forma_pago f
                where  f.cod_grupo_cia = '001'
                and    f.est_forma_pago in ('A','X')
                and    f.ind_forma_pago_efectivo = cIndUsoEfectivo_in
                and    f.ind_tarj = cIndUsoTarjeta_in
                and    ('NULL'=cCodForma_Tarjeta_in or ('T0000' = cCodForma_Tarjeta_in or f.cod_forma_pago = cCodForma_Tarjeta_in))
                and    cf.cod_grupo_cia = f.cod_grupo_cia
                and    cf.cod_forma_pago = f.cod_forma_pago
                union
                select a.cod_camp_cupon,a.cod_grupo_cia,a.valor_cupon descuento_personalizado
                from   vta_campana_cupon a
                where  not exists (select 1 from vta_camp_x_fpago_uso cx where cx.cod_grupo_cia = a.cod_grupo_cia
                                                                         and   cx.cod_camp_cupon = a.cod_camp_cupon
                                                                         and   cx.estado = 'A')
             ) fpc
       WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
       --AND    C.COD_CAMP_CUPON = cCampCupon_in;
       --DUBILLUZ - 14.05.2010
       AND    C.COD_CAMP_CUPON = v_cCodigoCamp

       -- nueva condición para aquellos que colocaron las forma de pago USO.
        -- 09.06.2011  - DUBILLUZ
        and  c.cod_grupo_cia = fpc.cod_grupo_cia(+)
        and  c.cod_camp_cupon = fpc.cod_camp_cupon(+)
       ;
       END IF;
       return curCupon;
    END;

    /********************************************************************************************/
   FUNCTION VERIFICA_ROL_USU(cCodGrupoCia_in  IN CHAR,
                             cCodLocal_in     IN CHAR,
                             vSecUsu_in       IN CHAR,
                             cCodRol_in       IN CHAR)
    RETURN CHAR
    IS
    vresultado  CHAR(1);
    vcontador   NUMBER;
    BEGIN

    BEGIN
    SELECT COUNT(*) INTO vcontador
    FROM  PBL_ROL_USU X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.COD_LOCAL=cCodLocal_in
    AND X.SEC_USU_LOCAL=vSecUsu_in
    AND X.COD_ROL=cCodRol_in;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
           vcontador :=0;
    END;

    IF vcontador > 0 THEN
      vresultado := 'S';
    ELSE
      vresultado := 'N';
    END IF;
    RETURN vresultado;

    END;

   /***************************************************************************************/
   /*FUNCTION VTA_F_CUPON_CLI (cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cDni            IN CHAR)
    RETURN FarmaCursor
    AS
    curCupon FarmaCursor;
    v_vDescLocal VARCHAR2(120);
    BEGIN

       OPEN curCupon FOR
       SELECT  V1.DESC_CUPON || 'Ã' ||
               V1.VALOR_CUPON|| 'Ã' ||
               TO_CHAR(V1.FEC_EMI,'DD/MM/YYYY') || 'Ã' ||
               V1.COD_CUPON|| 'Ã' ||
               V1.TEXTO
       FROM (SELECT ROWNUM,A.DESC_CUPON DESC_CUPON,
               A.VALOR_CUPON ||''|| CASE WHEN A.TIP_CUPON='P' THEN '%' ELSE 's/.' END VALOR_CUPON,
               CP.FEC_CREA_CUP_CAB FEC_EMI,CP.COD_CUPON COD_CUPON,
               A.VALOR_CUPON||''|| CASE WHEN A.TIP_CUPON='P' THEN ' %' ELSE ' s/.' END
               ||' Descuento.'||CHR(13)|| CHR(10)||A.TEXTO_LARGO TEXTO
        FROM   VTA_CAMP_PEDIDO_CUPON C,
               FID_TARJETA_PEDIDO T,
               VTA_CUPON CP,
               VTA_CAMPANA_CUPON A
        WHERE  t.cod_grupo_cia = cCodGrupoCia_in
        and    t.cod_local = cCodLocal_in
        and    t.dni_cli = cDni
        and    c.estado = 'E'
        and    c.ind_impr = INDICADOR_SI
        and    cp.estado = ESTADO_ACTIVO
        and    cp.fec_crea_cup_cab BETWEEN TRUNC(SYSDATE - (SELECT LLAVE_TAB_GRAL FROM  PBL_TAB_GRAL WHERE ID_TAB_GRAL=285))
                                       AND TRUNC(SYSDATE)
        and    t.cod_grupo_cia = c.cod_grupo_cia
        and    t.cod_local = c.cod_local
        and    t.num_pedido = c.num_ped_vta
        AND    c.cod_grupo_cia = cp.cod_grupo_cia
        and    c.cod_cupon = cp.cod_cupon
        AND    CP.COD_GRUPO_CIA=A.COD_GRUPO_CIA
        AND    CP.COD_CAMPANA=A.COD_CAMP_CUPON
        AND    ROWNUM<2
        ORDER BY A.VALOR_CUPON,A.PRIORIDAD DESC)V1;

       RETURN curCupon;
    END;*/

  /* ************************************************************************ */

  PROCEDURE VTA_P_UPDATE_PEDIDO_VTA_CAB(cCodGrupoCia_in         IN CHAR,
                                         cCodLocal_in            IN CHAR,
                                         cNumPedVta_in           IN CHAR,
                                        cNomCliPedVta_in       IN VARCHAR2,
                                        cDirCliPedVta_in       IN VARCHAR2,
                                        cRucCliPedVta_in       IN VARCHAR2) IS
  BEGIN
  UPDATE VTA_PEDIDO_VTA_CAB
     SET   NOM_CLI_PED_VTA = cNomCliPedVta_in,
           DIR_CLI_PED_VTA = cDirCliPedVta_in,
           RUC_CLI_PED_VTA = cRucCliPedVta_in,
           FEC_MOD_PED_VTA_CAB = SYSDATE
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
           COD_LOCAL = cCodLocal_in AND
           NUM_PED_VTA = cNumPedVta_in ;

  END;


 /********************************************************************************************/
   FUNCTION OBTIENE_MENSAJE_AHORRO(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cIndFid_in       IN CHAR)
    RETURN CHAR
    IS
    vresultado  VARCHAR2(200);
    vcontador   NUMBER;
    BEGIN
      --ERIOS 21.01.2014 Se considera la marca del local
      BEGIN
        IF cIndFid_in ='S' THEN
          SELECT DESC_LARGA INTO vresultado
          FROM PBL_TAB_GRAL
          WHERE COD_APL = 'PTOVENTA'
          AND COD_TAB_GRAL = 'MENSAJE_DESCUENTO_FI'
          AND LLAVE_TAB_GRAL = (SELECT COD_MARCA FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in)
          AND EST_TAB_GRAL = 'A';
        ELSIF cIndFid_in='N' THEN
          SELECT DESC_LARGA INTO vresultado
          FROM PBL_TAB_GRAL
          WHERE COD_APL = 'PTOVENTA'
          AND COD_TAB_GRAL = 'MENSAJE_DESCUENTO'
          AND LLAVE_TAB_GRAL = (SELECT COD_MARCA FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in)
          AND EST_TAB_GRAL = 'A';
        END IF;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vresultado :='N';
      END;

    RETURN vresultado;

    END;

  /*******************************************************/
  FUNCTION VTA_F_GET_MENS_TICKET(cCodCia_in    IN CHAR,
                                cCod_local_in  IN CHAR)
  RETURN CHAR
  IS
  cMensaje VARCHAR2(100);
    cMensajeAux VARCHAR2(100);
    v_cVerifica CHAR(1);
        v_cProv CHAR(1);

  BEGIN


      SELECT X.IND_LOCAL_PROV INTO v_cProv
      FROM PBL_LOCAL X
      WHERE X.COD_GRUPO_CIA='001'
      AND X.COD_LOCAL=cCod_local_in;

      SELECT A.LLAVE_TAB_GRAL INTO  cMensajeAux
      FROM PBL_TAB_GRAL A
      WHERE A.ID_TAB_GRAL=293;

  IF((cCod_local_in='044' OR  cCod_local_in='061' OR cCod_local_in='024' OR cCod_local_in='026') AND v_cProv='S') THEN

   cMensaje:=cMensajeAux;

  ELSIF (v_cProv='N')THEN

   cMensaje:='DELIVERY LIMA 213-0777';

  ELSIF (v_cProv='S')THEN

   cMensaje:='N';

  END IF;

    RETURN  cMensaje;
  EXCEPTION
      WHEN NO_DATA_FOUND THEN
                RETURN 'N';

  END;
  /* ********************************************************  */
  FUNCTION VTA_F_GET_DESC_PAQUETE(cCodGrupoCia_in IN CHAR,cCodProd IN CHAR)
  RETURN VARCHAR2
  IS
  v_vDescripcion VARCHAR2(30):='';
  BEGIN
     BEGIN
       SELECT DISTINCT NVL(P.DESCRIPCION,'PRODUCTO EN PACK')
       INTO   v_vDescripcion
       FROM   VTA_PROMOCION P , VTA_PROD_PAQUETE C
       WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in   AND
       C.COD_PROD=cCodProd                       AND
       P.COD_GRUPO_CIA = C.COD_GRUPO_CIA         AND
       P.ESTADO  = 'A'                           AND
       ( P.COD_PAQUETE_1 = (C.COD_PAQUETE) OR
         P.COD_PAQUETE_2 = (C.COD_PAQUETE));
     EXCEPTION WHEN OTHERS THEN
       v_vDescripcion:='PRODUCTO EN PACK';
     END;
      RETURN  v_vDescripcion;
  END;

  /* ******************************************************* */
  FUNCTION VTA_F_GET_IND_COD_BARRA(cCodGrupoCia_in IN CHAR, cCodProd_in IN CHAR)
  RETURN CHAR
  IS
   v_IndCodBarra CHAR(1);
  BEGIN
    SELECT p.ind_cod_barra INTO v_IndCodBarra
      FROM lgt_prod p
     WHERE p.cod_grupo_cia = cCodGrupoCia_in AND p.Cod_Prod = cCodProd_in;
    RETURN v_IndCodBarra;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RETURN 'N';
  END;

  /* ******************************************************* */
  FUNCTION VTA_F_GET_IND_SOL_ID_USU(cCodGrupoCia_in IN CHAR, cCodProd_in IN CHAR)
  RETURN CHAR
  IS
   v_IndSolIdUsu CHAR(1);
  BEGIN
    SELECT P.IND_SOL_ID_USU INTO v_IndSolIdUsu
      FROM lgt_prod p
     WHERE p.cod_grupo_cia = cCodGrupoCia_in AND p.Cod_Prod = cCodProd_in;
    RETURN v_IndSolIdUsu;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RETURN 'N';
  END;

  /* ******************************************************* */
  FUNCTION VTA_F_GET_MENS_PROD(cCodGrupoCia_in IN CHAR, cCodProd_in IN CHAR)
  RETURN VARCHAR2
  IS
   v_MensProd lgt_prod.mensaje_prod%TYPE;
  BEGIN

    SELECT P.Mensaje_Prod INTO v_MensProd
      FROM lgt_prod p
     WHERE p.cod_grupo_cia = cCodGrupoCia_in AND p.Cod_Prod = cCodProd_in;

    IF v_MensProd IS NOT NULL THEN
      RETURN v_MensProd;
    ELSE
      v_MensProd:='N';
      RETURN v_MensProd;
    END IF;

    EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RETURN 'N';
  END;

  /* ******************************************************* */
  FUNCTION VTA_F_GET_DELIM_MENS
    RETURN VARCHAR2
  IS
    v_Delimitador PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE;
  BEGIN
    SELECT G.LLAVE_TAB_GRAL INTO v_Delimitador
      FROM PBL_TAB_GRAL G
     WHERE ID_TAB_GRAL = '300';

    RETURN v_Delimitador;

  EXCEPTION
     WHEN NO_DATA_FOUND THEN
       RAISE_APPLICATION_ERROR(-20199,'NO EXISTE LA LLAVE EN PBL_TAB_GRAL');
  END;
  /* ***************************************************** */
  FUNCTION VTA_F_CHAR_PREC_REDONDEADO(nValPrecVta_in IN NUMBER)
  RETURN CHAR
  IS
   v_nResiduo NUMBER;
   v_nEntero  NUMBER;
   v_n_PrecioRedondeado NUMBER;
   v_cIndTipoRedondeo PBL_TAB_GRAL.Llave_Tab_Gral%TYPE;
   v_cIndRedondeo PBL_TAB_GRAL.Llave_Tab_Gral%TYPE;
  BEGIN

     SELECT A.LLAVE_TAB_GRAL INTO v_cIndTipoRedondeo
     FROM PBL_TAB_GRAL A
     WHERE A.ID_TAB_GRAL = '320'
     AND A.COD_APL = 'PTO_VENTA'
     AND A.COD_TAB_GRAL = 'REDONDEO';

     SELECT A.LLAVE_TAB_GRAL INTO v_cIndRedondeo
     FROM PBL_TAB_GRAL A
     WHERE A.ID_TAB_GRAL = '318'
     AND A.COD_APL = 'PTO_VENTA'
     AND A.COD_TAB_GRAL = 'REDONDEO';
     IF (TRIM(v_cIndRedondeo) = 'N') THEN
        RETURN nValPrecVta_in;
     END IF;

     if (TRIM(v_cIndTipoRedondeo)) = IND_TIPO_REDONDEDO_CASO1 then-- REDONDEO EN 0.05 CENTIMOS
       v_nResiduo := nValPrecVta_in MOD 0.05;
       v_nEntero := TRUNC(nValPrecVta_in/0.05);

       if v_nResiduo > 0 then
          v_n_PrecioRedondeado :=  (v_nEntero + 1 ) * 0.05;
       else
          v_n_PrecioRedondeado := nValPrecVta_in;
       end if;

     elsif (TRIM(v_cIndTipoRedondeo)) = IND_TIPO_REDONDEDO_CASO2 then-- REDONDEO EN 0.10 CENTIMOS
       v_nResiduo := nValPrecVta_in MOD 0.1;
       v_nEntero := TRUNC(nValPrecVta_in/0.1);

       if v_nResiduo > 0 then
          v_n_PrecioRedondeado :=  (v_nEntero + 1 ) * 0.1;
       else
          v_n_PrecioRedondeado := nValPrecVta_in;
       end if;

     elsif (TRIM(v_cIndTipoRedondeo)) = IND_TIPO_REDONDEDO_CASO3 then  -- REDONDEO MENOR MAYOR A 1 SOL
       if nValPrecVta_in >= 1 then

          v_nResiduo := nValPrecVta_in MOD 0.1;
          v_nEntero := TRUNC(nValPrecVta_in/0.1);

          if v_nResiduo > 0 then
             v_n_PrecioRedondeado :=  (v_nEntero + 1 ) * 0.1;
          else
              v_n_PrecioRedondeado := nValPrecVta_in;
          end if;

       else
           v_nResiduo := nValPrecVta_in MOD 0.05;
           v_nEntero := TRUNC(nValPrecVta_in/0.05);

           if v_nResiduo > 0 then
              v_n_PrecioRedondeado :=  (v_nEntero + 1 ) * 0.05;
           else
               v_n_PrecioRedondeado := nValPrecVta_in;
           end if;
       end if;
     end if;

     RETURN TO_CHAR(v_n_PrecioRedondeado);

  END;

 /****************************************************************************/

  FUNCTION VTA_F_NUMBER_PREC_REDONDEADO(nValPrecVta_in IN NUMBER)
  RETURN NUMBER
  IS
   v_nResiduo NUMBER;
   v_nEntero  NUMBER;
   v_n_PrecioRedondeado NUMBER;
   v_cIndTipoRedondeo PBL_TAB_GRAL.Llave_Tab_Gral%TYPE;
   v_cIndRedondeo PBL_TAB_GRAL.Llave_Tab_Gral%TYPE;
  BEGIN

     SELECT A.LLAVE_TAB_GRAL INTO v_cIndTipoRedondeo
     FROM PBL_TAB_GRAL A
     WHERE A.ID_TAB_GRAL = '320'
     AND A.COD_APL = 'PTO_VENTA'
     AND A.COD_TAB_GRAL = 'REDONDEO';

     SELECT A.LLAVE_TAB_GRAL INTO v_cIndRedondeo
     FROM PBL_TAB_GRAL A
     WHERE A.ID_TAB_GRAL = '318'
     AND A.COD_APL = 'PTO_VENTA'
     AND A.COD_TAB_GRAL = 'REDONDEO';
     IF (TRIM(v_cIndRedondeo) = 'N') THEN
        RETURN nValPrecVta_in;
     END IF;

     if (TRIM(v_cIndTipoRedondeo)) = IND_TIPO_REDONDEDO_CASO1 then-- REDONDEO EN 0.05 CENTIMOS
       v_nResiduo := nValPrecVta_in MOD 0.05;
       v_nEntero := TRUNC(nValPrecVta_in/0.05);

       if v_nResiduo > 0 then
          v_n_PrecioRedondeado :=  (v_nEntero + 1 ) * 0.05;
       else
          v_n_PrecioRedondeado := nValPrecVta_in;
       end if;

     elsif (TRIM(v_cIndTipoRedondeo)) = IND_TIPO_REDONDEDO_CASO2 then-- REDONDEO EN 0.10 CENTIMOS
       v_nResiduo := nValPrecVta_in MOD 0.1;
       v_nEntero := TRUNC(nValPrecVta_in/0.1);

       if v_nResiduo > 0 then
          v_n_PrecioRedondeado :=  (v_nEntero + 1 ) * 0.1;
       else
          v_n_PrecioRedondeado := nValPrecVta_in;
       end if;

     elsif (TRIM(v_cIndTipoRedondeo)) = IND_TIPO_REDONDEDO_CASO3 then  -- REDONDEO MENOR MAYOR A 1 SOL
       if nValPrecVta_in > 1 then

          v_nResiduo := nValPrecVta_in MOD 0.1;
          v_nEntero := TRUNC(nValPrecVta_in/0.1);

          if v_nResiduo > 0 then
             v_n_PrecioRedondeado :=  (v_nEntero + 1 ) * 0.1;
          else
              v_n_PrecioRedondeado := nValPrecVta_in;
          end if;

       else
           v_nResiduo := nValPrecVta_in MOD 0.05;
           v_nEntero := TRUNC(nValPrecVta_in/0.05);

           if v_nResiduo > 0 then
              v_n_PrecioRedondeado :=  (v_nEntero + 1 ) * 0.05;
           else
               v_n_PrecioRedondeado := nValPrecVta_in;
           end if;
       end if;
     end if;


     RETURN v_n_PrecioRedondeado;

  END;

  /*********************************************************  */
/* *********************************************************** */


  FUNCTION VTA_F_CHAR_IND_DE_REDONDEADO
  RETURN CHAR
  IS
  v_cIndRedondeo PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE;
  BEGIN
   BEGIN
    SELECT A.LLAVE_TAB_GRAL INTO v_cIndRedondeo
     FROM PBL_TAB_GRAL A
     WHERE A.ID_TAB_GRAL = '318'
     AND A.COD_APL = 'PTO_VENTA'
     AND A.COD_TAB_GRAL = 'REDONDEO';
     EXCEPTION WHEN OTHERS THEN
     v_cIndRedondeo:='S';
   END;
     RETURN v_cIndRedondeo;
  END;

/* *********************************************************** */
  FUNCTION VTA_F_GET_DIRECCION_MATRIZ
  RETURN VARCHAR2
  IS
   vDireccion VARCHAR2(400) := 'N';
  BEGIN
   BEGIN
   SELECT G.LLAVE_TAB_GRAL INTO vDireccion
     FROM PBL_TAB_GRAL G
    WHERE G.ID_TAB_GRAL = 329;

   EXCEPTION WHEN OTHERS THEN
     vDireccion := 'N';
   END;
    RETURN vDireccion;
  END;

/* *********************************************************** */
  FUNCTION VTA_F_CHAR_IND_OBT_DIR_MATRIZ
  RETURN CHAR
  IS
   vIndDir CHAR;
  BEGIN
   BEGIN
   SELECT G.LLAVE_TAB_GRAL INTO vIndDir
     FROM PBL_TAB_GRAL G
    WHERE G.ID_TAB_GRAL = 331;

   EXCEPTION WHEN OTHERS THEN
     vIndDir := 'N';
   END;
   RETURN vIndDir;
  END;

/* *********************************************************** */
  FUNCTION VTA_F_GET_IND_FID_EMI(cCodGrupoCia_in CHAR,
                                 cIndFidelizado_in CHAR,
                                 cCod_camp_cupon_in CHAR,
                                 cFechaNac_in DATE DEFAULT NULL,
                                 cSexo_in CHAR DEFAULT NULL,
                                 cIndFidEmi_in CHAR)
  RETURN CHAR
  IS
   cantCamp NUMBER(6) := 0;
   totalAcum NUMBER(6) := 0;
   rpta CHAR(1) := 'N';
   cIndFidEmi CHAR(1);
  BEGIN
       SELECT A.IND_FID_EMI INTO cIndFidEmi
         FROM VTA_CAMPANA_CUPON A
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
          AND A.COD_CAMP_CUPON = cCod_camp_cupon_in;
     BEGIN
       --IF (v_indFidEmi = 'S') THEN
       IF (cIndFidelizado_in = 'S') THEN
       --FIDELIZADO
         IF (cIndFidEmi = 'S') THEN
           SELECT COUNT(*) INTO cantCamp
             FROM vta_campana_cupon c
            WHERE c.cod_grupo_cia = cCodGrupoCia_in
              AND c.cod_camp_cupon = cCod_camp_cupon_in
              AND c.ind_fid_emi = cIndFidEmi --obligado
              AND (C.TIPO_SEXO_E IS NULL OR C.TIPO_SEXO_E = cSexo_in)
              --AND (C.FEC_NAC_INICIO_E IS NULL OR C.FEC_NAC_INICIO_E <= to_date(cFechaNac_in,'dd/mm/yyyy') )
              --AND (C.FEC_NAC_FIN_E IS NULL OR C.FEC_NAC_FIN_E >= to_date(cFechaNac_in,'dd/mm/yyyy') )
              AND (C.FEC_NAC_INICIO_E IS NULL OR C.FEC_NAC_INICIO_E <=  cFechaNac_in)
              AND (C.FEC_NAC_FIN_E IS NULL OR C.FEC_NAC_FIN_E >= cFechaNac_in )
              AND (trunc(SYSDATE) BETWEEN c.fech_inicio AND c.fech_fin);
              --dbms_output.put_line('ind S, es fidelizado: '||cantCamp);
              IF cantCamp > 0 THEN
                 --tiene campaña
               --  rpta := 'S';
                 totalAcum := totalAcum + cantCamp;
              --ELSE
              --   rpta := 'N';
              END IF;
          ELSE
          --fidelizado verifica si fid_emi_ en n
         SELECT COUNT(*) INTO cantCamp
           FROM vta_campana_cupon cc
          WHERE cc.cod_grupo_cia = cCodGrupoCia_in
            AND cc.cod_camp_cupon = cCod_camp_cupon_in
            AND ind_fid_emi = cIndFidEmi
           -- VALIDA FECHA NI SEXO
            AND (cc.tipo_sexo_e IS NULL OR CC.TIPO_SEXO_E  = cSexo_in)
            AND (Cc.FEC_NAC_INICIO_E IS NULL OR Cc.FEC_NAC_INICIO_E <= cFechaNac_in )
            AND (Cc.FEC_NAC_FIN_E IS NULL OR cC.FEC_NAC_FIN_E >= cFechaNac_in )
            AND (trunc(SYSDATE) BETWEEN cc.fech_inicio AND cc.fech_fin);
            --dbms_output.put_line('ind N, es fidelizado: '||cantCamp);
            IF cantCamp > 0 THEN
               --tiene campaña
              -- rpta := 'S';
               totalAcum := totalAcum + cantCamp;
            --ELSE
            --   rpta := 'N';
            END IF;
          END IF;
       ELSE
       --NO FIDELIZADO
         SELECT COUNT(*) INTO cantCamp
           FROM vta_campana_cupon cc
          WHERE cc.cod_grupo_cia = cCodGrupoCia_in
            AND cc.cod_camp_cupon = cCod_camp_cupon_in
            AND ind_fid_emi = 'N'
           -- NO VALIDA FECHA NI SEXO
           -- AND (cc.tipo_sexo_e IS NULL OR CC.TIPO_SEXO_E  = cSexo_in)
           -- AND (C.FEC_NAC_INICIO_E IS NULL OR C.FEC_NAC_INICIO_E <= to_date(cFechaNac_in,'dd/mm/yyyy') )
           -- AND (C.FEC_NAC_FIN_E IS NULL OR C.FEC_NAC_FIN_E >= to_date(cFechaNac_in,'dd/mm/yyyy') )
            AND (trunc(SYSDATE) BETWEEN cc.fech_inicio AND cc.fech_fin);
            IF cantCamp > 0 THEN
               --tiene campaña
              -- rpta := 'S';
               totalAcum := totalAcum + cantCamp;
            --ELSE
              -- rpta := 'N';
            END IF;
       --dbms_output.put_line('no es fidelizado: '||cantCamp);
       END IF;
       IF (totalAcum > 0 ) THEN
         rpta := 'S';
       ELSE
         rpta := 'N';
       END IF;
       --dbms_output.put_line('rpta: '||rpta);
     END;
     RETURN rpta;
  END;

  /* ******************************************************* */
  FUNCTION F_GET_IND_IMP_CORRELATIVO(cCodGrupoCia_in CHAR)
  RETURN CHAR
  IS
  vInd CHAR(1);
  BEGIN

  SELECT TRIM(LLAVE_TAB_GRAL) into vInd
    FROM PBL_TAB_GRAL
   WHERE ID_TAB_GRAL='389';

  RETURN vInd;

  EXCEPTION
    WHEN NO_DATA_FOUND THEN
       RAISE_APPLICATION_ERROR(-20198,'NO EXISTE INDICADOR PARA IMPRIMIR CORRELATIVO');
  END;

  /* ******************************************************* */
  FUNCTION F_GET_CORRELATIVO_MONTO_NETO(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in IN CHAR,
                                        cTipo_Comp_in IN CHAR,
                                        cMonto_Neto_in IN CHAR,
                                        cNum_Comp_Pago_in IN CHAR)
  RETURN VARCHAR2
  IS
  vRpta VARCHAR2(300) := '';

  BEGIN
	--ERIOS 02.03.2015 Se considera la busqueda de NCR.
  Select --ltavara 26/01/2015
     ( SELECT  C.NUM_PED_VTA ||';'||
           to_char(ABS(C.VAL_NETO_PED_VTA),'9999999.00') ||';'||
           to_char(C.FEC_PED_VTA,'dd/mm/YYYY')
     
      FROM ptoventa.VTA_PEDIDO_VTA_CAB C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCod_Local_in
       AND exists (select 1
            FROM ptoventa.VTA_COMP_PAGO CP
            WHERE CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA 
            AND CP.COD_LOCAL = C.COD_LOCAL
            and CP.NUM_PED_VTA =C.NUM_PED_VTA
            AND CP.TIP_COMP_PAGO = cTipo_Comp_in
            -- kmoncada 27.01.2015
            and  (nvl(CP.COD_TIP_PROC_PAGO,'0')='1' and cp.NUM_COMP_PAGO_E=cNum_Comp_Pago_in) 
            AND ABS(CP.VAL_NETO_COMP_PAGO+CP.VAL_REDONDEO_COMP_PAGO) = to_number(cMonto_Neto_in,'999999.000')
      )
      union 
      SELECT     C.NUM_PED_VTA ||';'||
           to_char(ABS(C.VAL_NETO_PED_VTA),'9999999.00') ||';'||
           to_char(C.FEC_PED_VTA,'dd/mm/YYYY')
      FROM ptoventa.VTA_PEDIDO_VTA_CAB C
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCod_Local_in
       AND exists (select 1
            FROM ptoventa.VTA_COMP_PAGO CP
            WHERE CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA 
            AND CP.COD_LOCAL = C.COD_LOCAL
            and CP.NUM_PED_VTA =C.NUM_PED_VTA
            AND CP.TIP_COMP_PAGO = cTipo_Comp_in
            -- kmoncada 27.01.2015
            and  (nvl(CP.COD_TIP_PROC_PAGO,'0')<>'1' and cp.NUM_COMP_PAGO=cNum_Comp_Pago_in)
            AND ABS(CP.VAL_NETO_COMP_PAGO+CP.VAL_REDONDEO_COMP_PAGO) = to_number(cMonto_Neto_in,'999999.000')
      )
      )
       INTO vRpta
       from dual;

  RETURN vRpta;

  EXCEPTION
    WHEN NO_DATA_FOUND THEN
       RAISE_APPLICATION_ERROR(-20198,'ERROR AL OBTENER MONTO NETO Y CORRELATIVO');
  END;

    /* ******************************************************* */
  FUNCTION F_GETSTOCK_PROD_REGALO(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in IN CHAR,
                                        cCod_prod IN CHAR)
  RETURN VARCHAR2
  IS
  stock VARCHAR2(100);
  BEGIN

    SELECT TRIM(STK_FISICO||' ')
      INTO stock
      FROM LGT_PROD_LOCAL C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCod_Local_in
       AND C.COD_PROD =  cCod_prod;

  RETURN (stock);

  EXCEPTION
    WHEN NO_DATA_FOUND THEN
       RAISE_APPLICATION_ERROR(-20198,'ERROR EL STOCK DEL PROD');
  END;


 FUNCTION F_GETDATOS_ENCARTE_AP(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in IN CHAR,
                                        cCod_Encarte_in in char,
                                        cMonto_in in char)
  RETURN FarmaCursor
  IS
  curVta FarmaCursor;

  BEGIN
    OPEN curVta FOR
    select  p.cod_prod|| 'Ã' ||
    p.DESC_PROD || '  '||p.desc_unid_present|| 'Ã' ||
     r.cant_regalo|| 'Ã' || r.monto_rango_regalo|| 'Ã' || l.stk_fisico
  from vta_encarte_regalo r, lgt_prod_local l,lgt_prod p
 where r.cod_encarte = cCod_Encarte_in
   and r.cod_prod = l.cod_prod
   and r.cod_prod = p.cod_prod
   and l.cod_local = cCod_Local_in
   and l.cod_grupo_cia=cCodGrupoCia_in
   and r.monto_rango_regalo =
       (select max(monto_rango_regalo)
          from vta_encarte_regalo e,lgt_prod_local loc
         where e.cod_encarte = cCod_Encarte_in
           and loc.cod_prod = e.cod_prod
           and loc.cod_grupo_cia=cCodGrupoCia_in
           and loc.stk_fisico>0
           and monto_rango_regalo < to_number(trim(cMonto_in))) ;
  RETURN curVta;

  EXCEPTION
    WHEN NO_DATA_FOUND THEN
       RAISE_APPLICATION_ERROR(-20198,'ERROR EL STOCK DEL PROD');
  END;
/*------------------------------------------------------------------------------------------------------------------------
GOAL : DEVOLVER EL PRECIO MINIMO PARA CONSIDERAR AL VENDER UN PRODUCTO
DATE : 27-DIC-12
AUTH : JCT
--------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_MIN_PREC_VTA RETURN VARCHAR2
IS
 vc_PRECIO_MIN_VTA VARCHAR2(18);
BEGIN
  -- 10.- Lectura Precio Minimo que debe tener un Producto para Venta
  BEGIN
    SELECT G.LLAVE_TAB_GRAL
    INTO vc_PRECIO_MIN_VTA
    FROM pbl_tab_gral g
    WHERE G.ID_TAB_GRAL=402;
  EXCEPTION WHEN OTHERS THEN
     vc_PRECIO_MIN_VTA:='N';
  END;
  RETURN vc_PRECIO_MIN_VTA;
END;

/*-------------------------------------------------------------------------------------------------------------------------
GOAL : Devolver el Codigo Real de la Campaña a la que Pertenece la Barra
History : 18-JUL-14   TCT  Create
---------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_COD_CAMPA(cCodGrupoCia_in IN CHAR,
                            cCodCupon_in    IN CHAR) RETURN CHAR
AS
 vc_Cod_Camp_Cupon CHAR(5);

BEGIN

 BEGIN
  SELECT cc.cod_camp_cupon
  INTO vc_Cod_Camp_Cupon
  FROM vta_campana_cupon cc
  WHERE cc.estado = 'A'
    AND trunc(SYSDATE) BETWEEN cc.fech_inicio_uso AND cc.fech_fin_uso
    AND cc.cod_camp_imp_cup = TRIM(substr(cCodCupon_in,1,3))
    AND cc.cod_grupo_cia = cCodGrupoCia_in;
   EXCEPTION
  WHEN OTHERS THEN
      vc_Cod_Camp_Cupon:= NULL;
 END;

 IF  vc_Cod_Camp_Cupon IS NULL THEN
  vc_Cod_Camp_Cupon:= TRIM(substr(cCodCupon_in,1,5));
 END IF;
 RETURN vc_Cod_Camp_Cupon;

END;

/***************************************************************************************************************************/

  FUNCTION VTA_GET_VAL_ADIC_BARRA(cCodGrupoCia_in IN CHAR,
                                     cCodBarra_in    IN CHAR)
  RETURN VARCHAR2
  IS
    COD_PROD_01                      LGT_PROD_LOCAL.COD_PROD%TYPE;
    VAL_FRAC_LOCAL_01         LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    PRECIO_LOCAL_01               LGT_PROD_LOCAL.VAL_PREC_VTA%TYPE;


    DATOS_ADIC VARCHAR2(500);
    VAL_FRAC                 LGT_COD_BARRA.Val_Frac%type;
    DESC_FRAC VARCHAR2(50);
    PRECIO VARCHAR2(20);
    STK_FISICO_01       LGT_PROD_LOCAL.STK_FISICO%TYPE;
    FRAC_PROD_LOCAL_01                                 LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;

  BEGIN
       --HALLO CODIGO DE PRODUCTO, FRACCIONAMIENTO Y DESCRIPCION DE UNIDAD DE ADICIONALES
        SELECT A.COD_PROD, NVL(A.VAL_FRAC,0), NVL(A.DESC_UNIDAD,'0')
        INTO   COD_PROD_01, VAL_FRAC, DESC_FRAC
        FROM   LGT_COD_BARRA A
        WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND     A.COD_BARRA = cCodBarra_in;

        --HALLO FRACCIONAMIENTO Y PRECIO DE LOCAL PARA CON ESO CALCULAR LUEGO EL PRECIO DE LA UNIDAD DE PRESENTACION ADICIONAL
        SELECT P.VAL_FRAC_LOCAL, P.VAL_PREC_VTA, p.stk_fisico, P.VAL_FRAC_LOCAL
        INTO   VAL_FRAC_LOCAL_01, PRECIO_LOCAL_01, STK_FISICO_01, FRAC_PROD_LOCAL_01
        FROM   LGT_PROD_LOCAL P
        WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in
        AND     P.COD_PROD = COD_PROD_01;

        --SI HAY DATOS
        IF VAL_FRAC <> 0 AND DESC_FRAC <> '0' THEN

                    /*PRECIO :=  TO_CHAR(
                                         VTA_F_CHAR_PREC_REDONDEADO(
                                                                    ((VAL_FRAC_LOCAL_01 * PRECIO_LOCAL_01)/VAL_FRAC )),
                                         '999,990.000');*/
                         PRECIO := TO_CHAR(PRECIO_LOCAL_01 * VAL_FRAC, '999,990.000');
                    DATOS_ADIC := VAL_FRAC ||'Ã' || DESC_FRAC ||'Ã' || PRECIO ||'Ã' || STK_FISICO_01 || 'Ã' || FRAC_PROD_LOCAL_01;
         ELSE
                    DATOS_ADIC := 'THERE ISNT';
        END IF;

    RETURN DATOS_ADIC;
  EXCEPTION
      WHEN NO_DATA_FOUND THEN
       RETURN 'THERE ISNT';
  END;

  /***************************************************************************************************************************/

END;
/
