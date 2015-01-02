--------------------------------------------------------
--  DDL for Package PTOVENTA_VTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_VTA" AS

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
                                      nPctBeneficiario in VTA_PEDIDO_VTA_CAB.PCT_BENEFICIARIO%TYPE default null

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

  --Descripcion: Obtiene el listado de los productos alternativos
  --Fecha       Usuario		Comentario
  --24/02/2006  LMESIA     Creación
  --04/12/2007  dubilluz   Modificacion
  --16/04/2008  ERIOS     DEPRECATED: PTOVENTA_VTA_LISTA
  FUNCTION VTA_LISTA_PROD_ALTERNATIVOS(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR)
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
