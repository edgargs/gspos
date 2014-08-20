--------------------------------------------------------
--  DDL for Package PTOVTA_RESPALDO_STK
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVTA_RESPALDO_STK" IS
/**=-_Author: Alfredo Sosa Dordán_-=**/

  IND_ORIGEN_PROD CONSTANT CHAR(1) := '1';
  IND_ORIGEN_SUST CONSTANT CHAR(1) := '2';
  IND_ORIGEN_ALTE CONSTANT CHAR(1) := '3';
  IND_ORIGEN_COMP CONSTANT CHAR(1) := '4';
  IND_ORIGEN_OFER CONSTANT CHAR(1) := '5';
  IND_ORIGEN_REGA CONSTANT CHAR(1) := '6';

  INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';

  EST_PED_PENDIENTE  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='P';
	EST_PED_ANULADO  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='N';
	EST_PED_COBRADO  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='C';
	EST_PED_COB_NO_IMPR  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='S';

  g_cTipoOrigenLocal CHAR(2):= '01';
  g_cTipoOrigenMatriz CHAR(2):= '02';
  g_cTipoOrigenProveedor CHAR(2):= '03';
  g_cTipoOrigenCompetencia CHAR(2):= '04';

  COD_NUMERA_SEC_MOV_AJUSTE_KARD            PBL_NUMERA.COD_NUMERA%TYPE := '017';
  COD_NUMERA_SEC_KARDEX                       PBL_NUMERA.COD_NUMERA%TYPE := '016';

  g_cTipCompGuia LGT_KARDEX.Tip_Comp_Pago%TYPE := '03';
  g_cTipDocKdxGuiaES LGT_KARDEX.Tip_Comp_Pago%TYPE := '02';
  g_cTipCompNumEntrega LGT_KARDEX.Tip_Comp_Pago%TYPE := '05';

  g_cMotKardexIngMatriz      LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '101';
  g_cTipoMotKardexAjusteGuia LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '008';

  g_cMotKardexSobRecep       LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '011';

  --Descripcion: Graba el Detalle del Pedido
  --Fecha       Usuario		Comentario
  --02.07.2010  ASOSA     	Creación, metodo copiado y modificado
  PROCEDURE PVTA_P_GRAB_PED_VTA_DET(cCodGrupoCia_in 	 	  IN CHAR,
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
                                      nAhorroPack IN NUMBER DEFAULT NULL,
                                      cSecResp_in IN CHAR
                                      );
  --Descripcion: Procesa los pack que dan productos regalo
  -- para añadirlo en el pedido
  --Fecha       Usuario		  Comentario
  --06/07/2010  ASOSA       Creacion, copiado para ser modificado
  PROCEDURE PROCESO_PROM_REGALO(cCodGrupoCia_in IN CHAR,
  		   				                cCodLocal_in	  IN CHAR,
                                cNumPedVta_in   IN CHAR,
                                cSecUsu_in      IN CHAR,
                                cLogin_in       IN CHAR,
                                cIp_in          IN CHAR);

  --Descripcion: Añade prod Regalo
  --Fecha       Usuario		Comentario
  --07/07/2010  ASOSA     Creación, copiado para ser modificado
  PROCEDURE CA_P_ADD_PROD_REGALO_CAMP(
                                      cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cNumPed_in       IN CHAR,
                                      cDni_in          IN CHAR,
                                      cCodCamp_in      IN CHAR,
                                      cSecUsu_in       IN CHAR,
                                      cIpPc_in         IN CHAR,
                                      cUsuCrea_in      IN CHAR
                                     );

  --Descripcion: Inserta canje
  --Fecha       Usuario		Comentario
  --07/07/2010  ASOSA     Creación, copiado para ser modficado
  PROCEDURE CA_P_INSERT_CANJ_CLI (
                                   cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cNumPed_in        IN CHAR,
                                   cDni_in           IN CHAR,
                                   cCodCamp_in       IN CHAR,
                                   nSecPedVta_in     IN NUMBER,
                                   cCodProd_in       IN CHAR,
                                   nCantPedido_in    IN NUMBER,
                                   nValFracPedido_in IN NUMBER,
                                   cEstado_in        IN CHAR,
                                   cUsuCrea_in       IN CHAR
                                 );

  --Descripcion: Verifica si se puede llevar producto de regalo por el encarte
  --Fecha       Usuario		Comentario
  --09/07/2010  ASOSA     Creación, copiado para ser modificado
  PROCEDURE VTA_GRABAR_PED_REGALO_DET(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in	   IN CHAR,
                                    cNumPedVta_in    IN CHAR,
                                    cSecProdDet_in   IN NUMBER,
                                    cCodProd_in      IN CHAR,
                                    cCantAtend_in    IN NUMBER,
                                    cValPredVenta_in IN CHAR,
                                    cSecUsu_in       IN CHAR,
                                    cLoginUsu_in     IN CHAR,
                                    vSecRes_in       IN CHAR);

  --Descripcion: Actualiza el stock del producto cuando se cobre un pedido Y GRABA KARDEX
  --Fecha       Usuario		Comentario
  --11/07/2010  ASOSA     Creacion, copiado de PTOVENTA_CAJ para ser modificado
  PROCEDURE CAJ_UPD_STK_PROD_DETALLE(cCodGrupoCia_in 	   IN CHAR,
						   	 			   cCodLocal_in    	   IN CHAR,
							 			   cNumPedVta_in   	   IN CHAR,
										   cCodMotKardex_in    IN CHAR,
						   	  			   cTipDocKardex_in    IN CHAR,
										   cCodNumeraKardex_in IN CHAR,
							 			   cUsuModProdLocal_in IN CHAR);

  --Descripcion: Anula los pedidos pendientes despues de X minutos
  --Fecha       Usuario		Comentario
  --12/07/2010  ASOSA     Creación, copiado de PTOVENTA_CAJ para ser modificado
  PROCEDURE CAJ_ANULA_PED_PEND_MASIVO(cCodGrupoCia_in 	  IN CHAR,
  		   						   	  cCodLocal_in    	  IN CHAR,
									  nMinutos_in	  	  IN NUMBER,
									  cUsuModPedVtaCab_in IN CHAR);

  --Descripcion: Anula Pedido Pendiente.
  --Fecha       Usuario		Comentario
  --12/07/2010  ASOSA    	Creación, copiado de PTOVENTA_CAJ_ANUL para ser modificado
  PROCEDURE CAJ_ANULAR_PEDIDO_PENDIENTE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        vIdUsu_in       IN CHAR,
                                        cModulo_in      IN CHAR DEFAULT 'V');

  --Descripcion: Se descompromete stock de productos regalo por encarte (optimizar proceso)
  --Fecha       Usuario		Comentario
  --13/07/2010  ASOSA     Creación, copiado de PTOVENTA_CA_CLIENTE para ser modficado
  PROCEDURE CA_P_UPDATE_ELIMINA_REGALO(cCodGrupoCia_in       IN CHAR,
                                    cCodLocal_in          IN CHAR,
                                    cUsuMod_in            IN CHAR,
                                    cNumPed_in            IN CHAR,
                                    cAccion_in            IN CHAR,
                                    cIndQuitaRespaldo_in  IN CHAR);

  --Descripcion: Anula los regalos de campaña acumulada y automatico
  --Fecha       Usuario		Comentario
  --13/07/2010  ASOSA     Creación, copiado de PTOVENTA_CAJ_ANUL para ser modficado
  PROCEDURE CAJ_P_ANULA_PED_SIN_RESPALDO(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        vIdUsu_in       IN VARCHAR2,
                                        cModulo_in      IN CHAR DEFAULT 'V');

  --Descripcion: Agrega el detalle de una guia de ingreso.
  --Fecha       Usuario		Comentario
  --15/07/2010  ASOSA     Creación, copiado de PTOVENTA_INV para ser modficado
  PROCEDURE INV_AGREGA_DET_TRANSFERENCIA(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNumNota_in      IN CHAR,
                                         cCodProd_in      IN CHAR,
                                         nValPrecUnit_in  IN NUMBER,
                                         nValPrecTotal_in IN NUMBER,
                                         nCantMov_in      IN NUMBER,
                                         vFecVecProd_in   IN VARCHAR2,
                                         vNumLote_in      IN VARCHAR2,
                                         cCodMotKardex_in IN CHAR,
                                         cTipDocKardex_in IN CHAR,
                                         vValFrac_in      IN NUMBER,
                                         vUsu_in          IN VARCHAR2,
                                         vTipDestino_in   IN CHAR,
                                         cCodDestino_in   IN CHAR,
                                         vIndFrac_in      IN CHAR DEFAULT 'N',
                                         secRespaldo      IN CHAR);

  --Descripcion: Anula de una transferencia.
  --Fecha       Usuario		Comentario
  --15/07/2010  ASOSA     Creación, copiado de PTOVENTA_INV para ser modficado
  PROCEDURE INV_ANULA_TRANSFERENCIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,
                                    cCodMotKardex_in IN CHAR, cTipDocKardex_in IN CHAR,
                                    vIdUsu_in IN VARCHAR2);

  --Descripcion: Actualiza el kardex con el numero de guia fisica de guia de recepcion
  --Fecha       Usuario	  Comentario
  --15/07/2010  ASOSA    Creación, copiado de PTOVENTA_INV solo para ser utilizado por el procedimiento anterior
  PROCEDURE INV_ACT_KARDEX_GUIA_REC(cGrupoCia_in   IN CHAR,
                                    cCodLocal_in 	 IN CHAR,
                                    cNumNota_in  	 IN CHAR,
                                    cSecDetNota_in IN CHAR,
                                    cCodProd_in 	 IN CHAR,
                                    cIdUsu_in 		 IN CHAR,
                                    cTipOrigen_in IN CHAR DEFAULT NULL);

  --Descripcion: Agregar detalle de la transferencia delivery
  --Fecha       Usuario	  Comentario
  --15/07/2010  ASOSA    Creación, copiado de PTOVENTA_TRANF_DEL solo para ser utilizado por el procedimiento anterior
  PROCEDURE TRANSF_P_AGREGA_DETALLE(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in     IN CHAR,
                                  cNumNota_in      IN CHAR,
                                  cCodProd_in      IN CHAR,
                                  nValPrecUnit_in  IN NUMBER,
                                  nValPrecTotal_in IN NUMBER,
                                  nCantMov_in      IN NUMBER,
                                  vFecVecProd_in   IN VARCHAR2 DEFAULT NULL,
                                  vNumLote_in      IN VARCHAR2 DEFAULT NULL,
                                  cCodMotKardex_in IN CHAR,
                                  cTipDocKardex_in IN CHAR,
                                  vValFrac_in      IN NUMBER,
                                  vUsu_in          IN VARCHAR2,
                                  vTipDestino_in   IN CHAR,
                                  cCodDestino_in   IN CHAR,
                                  vIndFrac_in      IN CHAR DEFAULT 'N',
                                  cSecResp_in      IN VARCHAR2 DEFAULT '0');

  --Descripcion: Afecta la cantidad contada por cada entrega por página
  --Fecha       Usuario   Comentario
  --21/07/2010  ASOSA     Creación, copiado de PTOVENTA_RECEP_CIEGA_JC para ser modificado
  PROCEDURE RECEP_P_OPERAC_ANEXAS_PROD(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumNota_in     IN CHAR,
                                       nSecGuia_in     IN NUMBER,
                                       cCodProd_in     IN CHAR,
                                       nCantMov_in     IN NUMBER,
                                       cIdUsu_in       IN CHAR,
                                       nTotalGuia_in   IN NUMBER DEFAULT NULL,
                                       nDiferencia_in  IN NUMBER DEFAULT NULL);

  --Descripcion: Ejecuta la recuperacion de los stocks de los productos
  --Fecha       Usuario   Comentario
  --24/08/2010  ASOSA     Creación, copiado de FARMA_UTILITY para ser modificado
  PROCEDURE RECUPERACION_RESPALDO_STK(cCodGrupoCia_in IN CHAR,
	                           		  cCodLocal_in    IN CHAR,
	                           		  cNumIpPc_in     IN CHAR,
									  vIdUsuario_in   IN VARCHAR2);

  --Descripcion:
  --Fecha       Usuario   Comentario
  --24/08/2010  ASOSA     Creación, copiado de FARMA_UTILITY para ser modificado
  FUNCTION VERIFICA_STOCK_RESPALDO(cCodGrupoCia_in IN CHAR,
									                 cCodLocal_in  	 IN CHAR,
									                 cCodProd_in     IN CHAR,
                                   cSecResp_in IN NUMBER)RETURN NUMBER ;


  --Descripcion:
  --Fecha       Usuario   Comentario
  --27/07/2011  JMIRANDA
PROCEDURE RECEP_P_OPERAC_ANEXAS_PROD_LI(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumNota_in     IN CHAR,
                                       nSecGuia_in     IN NUMBER,
                                       cCodProd_in     IN CHAR,
                                       nCantMov_in     IN NUMBER,
                                       cIdUsu_in       IN CHAR,
                                       nTotalGuia_in   IN NUMBER DEFAULT NULL,
                                       nDiferencia_in  IN NUMBER DEFAULT NULL);

PROCEDURE RECEP_P_OPERAC_ANEXAS_PROD_SO(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumNota_in     IN CHAR,
                                       nSecGuia_in     IN NUMBER,
                                       cCodProd_in     IN CHAR,
                                       nCantMov_in     IN NUMBER,
                                       cIdUsu_in       IN CHAR);
FUNCTION F_EXISTE_STOCK_PEDIDO(cCodGrupoCia_in IN CHAR,
  		   				  		         cCodLocal_in	 IN CHAR,
								               cNumPedVta_in IN CHAR)
    RETURN char;

   --Descripcion:
  --Fecha       Usuario   Comentario
  --14/01/2013  CHUANES    Anula la devolucíon de productos
  PROCEDURE INV_ANULA_DEVOLUCION_MERCA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,
                                        cCodMotKardex_in IN CHAR, cTipDocKardex_in IN CHAR,
                                        vIdUsu_in IN VARCHAR2);

    --Descripcion:
  --Fecha       Usuario   Comentario
  --14/01/2013  CHUANES    Consulta el estado de una de devolucíon y anula la transaccíon
    FUNCTION INV_ANULA_DEVOLUCION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cCodMotKardex_in IN CHAR, cTipDocKardex_in IN CHAR,
                                        vIdUsu_in IN VARCHAR2)
    RETURN CHAR;
   --Descripcion:
  --Fecha       Usuario   Comentario
  --14/01/2013  CHUANES   Consulta el estado de una devolucíon

    FUNCTION INV_GET_ESTADO_DEVOLUCION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR)
    RETURN CHAR;



/*-------------------------------------------------------------------------------------------------------------------------
GOAL : Grabar Datos Para Depuracion
Ammedments:
14-ENE-14  TCT      Create
---------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_GRABA_DEBUG(ac_Desc IN VARCHAR2);


--Descripcion:
--Fecha       Usuario   Comentario
PROCEDURE CAJ_DELIVERY_GENERA_PENDIENTE(cCodGrupoCia_in IN CHAR,

                                        cCodLocal_in    IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        vIdUsu_in       IN CHAR,
                                        cModulo_in      IN CHAR DEFAULT 'V');


END PTOVTA_RESPALDO_STK;

/
