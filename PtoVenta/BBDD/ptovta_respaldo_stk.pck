CREATE OR REPLACE PACKAGE PTOVENTA."PTOVTA_RESPALDO_STK" IS
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
  
  --INI ASOSA - 02/10/2014 - PANHD
  TIPO_PROD_FINAL CONSTANT CHAR(1) := 'F';
  TIPO_PROD_COMPONENTE CONSTANT CHAR(1) := 'C';
   FLAG_ACTIVO CONSTANT CHAR(1) := 'A';
   IND_SI CONSTANT CHAR(1) := 'S';
  --FIN ASOSA - 02/10/2014 - PANHD
  --INI ASOSA - 07/10/2014 - PANHD
	ALTA_VTA_PROD CONSTANT CHAR(3) := '113';
  VTA_NORMAL CONSTANT CHAR(3) := '001';
	BAJA_ANUL_VTA_PROD CONSTANT CHAR(3) := '114';
	CONSUMO_MATERIAL CONSTANT CHAR(3) := '115';
  --FIN ASOSA - 07/10/2014 - PANHD

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
                                      cSecResp_in IN CHAR,
                                       nValMult			      IN NUMBER default 1,--ASOSA - 08/08/2014
                                       vDniRimac           IN VARCHAR2  DEFAULT NULL,     --ASOSA - 07/01/2015
                                       --KMONCADA 2015.02.16
                                      vIndInscritoLealtad_in IN CHAR DEFAULT NULL,
                                      vCodProdLealtad_in     IN CHAR DEFAULT NULL
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
                                         secRespaldo      IN CHAR,
                                          --INI ASOSA - 22/08/2014
                                         cantExceso in number DEFAULT 0,    --como esto es devolucion el exceso es lo maximo que esperaba devolver.
                                         cantUsadaExceso in number DEFAULT 0,--es la cantidad que use del exceso.
                                         numeroOc in varchar2 default null,
                                         secuencialDetalle in varchar2 default NULL
                                         --FIN ASOSA - 22/08/2014
                                         );

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

  --Descripcion: Metodo que me determina que un producto final sea viable para vender
  --Fecha          02/10/2014  
  --Author         ASOSA
    FUNCTION GET_INFO_RECETA_PFINAL(cCodGrupoCia_in IN CHAR,
                                                                             cCodProd_in IN CHAR)
    RETURN VARCHAR2;

  --Descripcion: Metodo que me determina si los componentes de un producto final tienen el stock necesario.
  --Fecha          02/10/2014  
  --Author         ASOSA
    FUNCTION GET_VALIDA_STOCK_COMP(cCodGrupoCia_in IN CHAR,
                                                                             cCodLocal_in IN CHAR,
                                                                             cCodProd_in IN CHAR,
                                                                             nCantPedido IN NUMBER)
    RETURN VARCHAR2;
    
  --Descripcion: Metodo para grabar el detalle de la transaccion de los componentes de un producto final.
  --Fecha          03/10/2014  
  --Author         ASOSA
    PROCEDURE PVTA_P_INS_DET_TRANSC_COMP(cCodGrupoCia_in 	 	  IN CHAR,
                                      cCodLocal_in    	 	  IN CHAR,
                            				  cNumPedVta_in   	 	  IN CHAR,
                            				  nSecPedVtaDet_in		  IN NUMBER,
                            				  cCodProd_in			      IN CHAR,
                            				  nCantAtendida_in	 	  IN NUMBER,
                                      cUsuCreaPedVtaDet_in	IN CHAR
                                      ) ;

  --Descripcion: Metodo que valida en el cobro si existe la cantidad suficiente de componentes para el pedido de producto final.
  --Fecha          06/10/2014  
  --Author         ASOSA              
FUNCTION F_EXISTE_STK_PED_PROD_FINAL(cCodGrupoCia_in IN CHAR,
  		   				  		         cCodLocal_in	 IN CHAR,
								               cNumPedVta_in IN CHAR)
    RETURN char;
    
  --Descripcion: Metodo que actualiza el stock y el registra el movimiento de kardex para los productos finales y sus componentes.
  --Fecha          07/10/2014  
  --Author         ASOSA 
      PROCEDURE CAJ_UPD_STK_PROD_COMP(cCodGrupoCia_in 	   IN CHAR,
						   	 			                     cCodLocal_in    	   IN CHAR,
							 			                       cNumPedVta_in   	   IN CHAR,
                                           cCodMotKardex_in IN CHAR,    --ASOSA - 17/10/2014
						   	  			                   cTipDocKardex_in    IN CHAR,
										                       cCodNumeraKardex_in IN CHAR,
							 			                       cUsuModProdLocal_in IN CHAR);


END PTOVTA_RESPALDO_STK;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVTA_RESPALDO_STK" IS

/***********************************************************************************************************************/
/***********************************************************************************************************************/
/***********************************************************************************************************************/

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
                                      cSecResp_in IN CHAR, --ASOSA, 05.07.2010,
                                      nValMult			      IN NUMBER default 1, --ASOSA - 08/08/2014
                                      vDniRimac           IN VARCHAR2  DEFAULT NULL,     --ASOSA - 07/01/2015
                                      --KMONCADA 2015.02.16
                                      vIndInscritoLealtad_in IN CHAR DEFAULT NULL,
                                      vCodProdLealtad_in     IN CHAR DEFAULT NULL
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
    vc_Desc    VARCHAR2(1000);
    
    vMsgValida varchar(100) := ''; --ASOSA - 03/10/2014 - PANHD

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
   /* SELECT VAL_FRAC_LOCAL,(nCantAtendida_in*VAL_FRAC_LOCAL)/nValFrac_in
      INTO v_nFracLocal,v_nCantFracLocal
    FROM LGT_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in;*/

   SELECT VAL_FRAC_LOCAL,(nCantAtendida_in*VAL_FRAC_LOCAL)/nValFrac_in,LC.COD_CIA -- TCT, add codcia
			,PL.PORC_ZAN
      INTO v_nFracLocal,v_nCantFracLocal,vc_Cod_Cia                               
			,v_nPorcZan
      --TENER EN CUENTA QUE ESTE CALCULO PARA ES PARA CONVERTIR EL STOCK VENDIDO A LA FRACCION DEL LOCAL
      --      SE DEBE VALIDAR BIEN AL CREAR LOS FRACCIONAMIENTOS NORMALES Y ADICIONALES PORQUE SINO
      --      OCURRIRA UN ERROR POR ESTAR MAL CONFIGURADO.
    FROM LGT_PROD_LOCAL PL JOIN PBL_LOCAL LC ON (PL.COD_GRUPO_CIA = LC.COD_GRUPO_CIA AND PL.COD_LOCAL = LC.COD_LOCAL)
    WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND PL.COD_LOCAL = cCodLocal_in
          AND PL.COD_PROD = cCodProd_in;

    -- 2008-07-30 modificado JOLIVA
    --19.10.09 MODIFICADO JCORTEZ ADD GRUPO_REP
-- 2009-11-09 JOLIVA: Se agrega el porcentaje de ZAN
    /*SELECT NVL(P.VAL_BONO_VIG,0), NVL(P.COD_GRUPO_REP,' '),NVL(P.COD_GRUPO_REP_EDMUNDO,' '), NVL(P.PORC_ZAN,0)
    INTO v_nBonoVig,CCOD_GRUPO_REP,CCOD_GRUPO_REP_EDMUNDO, v_nPorcZan
    FROM LGT_PROD P
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in AND COD_PROD = cCodProd_in;*/

    SELECT NVL(P.VAL_BONO_VIG,0), NVL(P.COD_GRUPO_REP,' '),NVL(P.COD_GRUPO_REP_EDMUNDO,' ')-- NVL(P.PORC_ZAN,0)
    INTO v_nBonoVig,CCOD_GRUPO_REP,CCOD_GRUPO_REP_EDMUNDO
    FROM LGT_PROD P
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in AND COD_PROD = cCodProd_in;

    ----
    vc_Desc:='vc_cod_cia ='||vc_cod_cia||',v_nPorcZan =  '||v_nPorcZan;
    ptovta_respaldo_stk.sp_graba_debug(ac_desc => vc_Desc);
    ----

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
          SEC_RESPALDO_STK, --ASOSA, 05.07.2010
          DNI_RIMAC,         --ASOSA - 07/01/2015
          -- KMONCADA 2015.02.16 PROGRAMA PUNTOS
          IND_PROD_MAS_1,
          COD_PROD_PUNTOS
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
--					nValTotalBono_in,
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
          --(nAhorroPack*nCantAtendida_in), -- KMONCADA 11.08.2015 SE GRABARA EL AHORRO DE PACK
          0,
          CCOD_GRUPO_REP,CCOD_GRUPO_REP_EDMUNDO,--JCORTEZ 19.10.09)
          (nAhorroPack*nCantAtendida_in),  --JCHAVEZ 20102009
          nPorcDcto2_in, --JCHAVEZ 20102009
-- 2009-11-09 JOLIVA: Se agrega el porcentaje de ZAN
          v_nPorcZan,
          to_number(cSecResp_in,'9999999999'), --ASOSA, 05.07.2010
          vDniRimac,                            --ASOSA - 07/01/2015
          -- KMONCADA 2015.02.16 PROGRAMA PUNTOS
          vIndInscritoLealtad_in,
          vCodProdLealtad_in
          );
          
          vMsgValida := GET_INFO_RECETA_PFINAL(cCodGrupoCia_in, cCodProd_in);
          
          vMsgValida := substr(trim(vMsgValida),1,1);
          
          IF vMsgValida = IND_SI THEN
                  PVTA_P_INS_DET_TRANSC_COMP(cCodGrupoCia_in 	 	,
                                      cCodLocal_in    	 	  ,
                            				  cNumPedVta_in   	 	  ,
                            				  nSecPedVtaDet_in		  ,
                            				  cCodProd_in			      ,
                            				  nCantAtendida_in	 	  ,
                                      cUsuCreaPedVtaDet_in	
                                      ) ;
          END IF;
  END;

/***********************************************************************************************************************/

PROCEDURE PROCESO_PROM_REGALO(cCodGrupoCia_in IN CHAR,
  		   				                cCodLocal_in	  IN CHAR,
                                cNumPedVta_in   IN CHAR,
                                cSecUsu_in      IN CHAR,
                                cLogin_in       IN CHAR,
                                cIp_in          IN CHAR)
   IS

 CURSOR cur IS
    --ERIOS 17.11.2014 Cambios de JLUNA
    SELECT DISTINCT P.COD_PROM
    FROM   VTA_PROMOCION P,
           VTA_PROD_PAQUETE Q
      WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
    AND  P.ESTADO = 'A'
    AND    P.COD_PAQUETE_2 IN (SELECT V.COD_PAQUETE
                               FROM (
                               SELECT T.COD_PAQUETE,COUNT(1)
                               FROM   VTA_PROD_PAQUETE T
                               WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
                               AND T.PORC_DCTO = 100
                               GROUP BY T.COD_PAQUETE
                               HAVING  COUNT(1) =
                                       (SELECT COUNT(1) FROM VTA_PROD_PAQUETE X 
                                        WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in
                                        AND X.COD_PAQUETE = T.COD_PAQUETE)
                                     )V
                               )
    AND    P.COD_GRUPO_CIA = Q.COD_GRUPO_CIA
    AND    P.COD_PAQUETE_1 = Q.COD_PAQUETE
    AND    Q.COD_PROD IN (select d.cod_prod
                          from   vta_pedido_vta_det d
                          where  d.cod_grupo_cia = cCodGrupoCia_in
                          and    d.cod_local     = cCodLocal_in
                          and    d.num_ped_vta   = cNumPedVta_in);


    CURSOR curPromMultiplo(cCodProm_in IN CHAR,
                      cCodCia_in  IN CHAR,
                      cLocal_in   IN CHAR,
                      cNumPed_in  IN CHAR) IS
     SELECT R1.COD_PROM,MIN(R1.MULTIPLO) FACTOR--, R1.cant_atendida,R1.sec_ped_vta_det  --JCHAVEZ 16102009 se agregó R1.cant_atendida,R1.sec_ped_vta_det
      FROM   (
              SELECT P.COD_PROM,P.COD_PAQUETE_1,
                     Q.COD_PROD,Q.CANTIDAD * PROD_LOCAL.VAL_FRAC_LOCAL / Q.VAL_FRAC,
                     DET_PEDIDO.cant_atendida*DET_PEDIDO.val_frac_local/DET_PEDIDO.val_frac,
                     TRUNC((DET_PEDIDO.cant_atendida*DET_PEDIDO.val_frac_local/DET_PEDIDO.val_frac)/(Q.CANTIDAD * PROD_LOCAL.VAL_FRAC_LOCAL / Q.VAL_FRAC)) MULTIPLO--,
                  --   DET_PEDIDO.cant_atendida, --JCHAVEZ 16102009 se agregó DET_PEDIDO.cant_atendida
                  --   DET_PEDIDO.sec_ped_vta_det  --JCHAVEZ 16102009 se agregó DET_PEDIDO.sec_ped_vta_det

              FROM   VTA_PROMOCION P,
                     VTA_PROD_PAQUETE Q,
                     LGT_PROD_LOCAL PROD_LOCAL,
                     (
                        select d.cod_prod,d.cant_atendida,d.val_frac,d.cod_prom,d.val_frac_local, d.sec_ped_vta_det --JCHAVEZ 16102009 se agregó d.sec_ped_vta_det
                        from   vta_pedido_vta_det d
                        where  d.cod_grupo_cia = cCodCia_in
                        and    d.cod_local     = cLocal_in
                        and    d.num_ped_vta   = cNumPed_in
                        and    d.val_prec_vta  != 0
                        and    d.val_prec_total != 0
                        and    d.cod_prom is null --JCHAVEZ 19102009
                     )DET_PEDIDO,
                     (
                      SELECT Q.COD_PAQUETE,COUNT(1) CANTIDAD
                      FROM   VTA_PROD_PAQUETE Q
                      GROUP BY Q.COD_PAQUETE
                      ) TOT_PAQUETE_PROD

           /*   WHERE (SELECT SYSDATE FROM DUAL)
              BETWEEN
               TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     */
                WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
              AND P.COD_PROM = cCodProm_in
              AND    P.ESTADO   = 'A'
              AND    P.COD_GRUPO_CIA = Q.COD_GRUPO_CIA
              AND    P.COD_PAQUETE_1 = Q.COD_PAQUETE
              AND    Q.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
              AND    Q.COD_PROD = PROD_LOCAL.COD_PROD
              AND    PROD_LOCAL.COD_LOCAL = cLocal_in
              AND    DET_PEDIDO.COD_PROD  = Q.COD_PROD
              AND    TOT_PAQUETE_PROD.COD_PAQUETE = Q.COD_PAQUETE
              AND    TOT_PAQUETE_PROD.CANTIDAD = (SELECT COUNT(1)
                                                  FROM   VTA_PROD_PAQUETE   X,
                                                         VTA_PEDIDO_VTA_DET T
                                                  WHERE  X.COD_GRUPO_CIA = Q.COD_GRUPO_CIA
                                                  AND    X.COD_PAQUETE = Q.COD_PAQUETE
                                                  AND    X.COD_PROD = T.COD_PROD
                                                  AND    T.COD_GRUPO_CIA = cCodCia_in
                                                  AND    T.COD_LOCAL = cLocal_in
                                                  AND    T.NUM_PED_VTA = cNumPed_in
                                                  AND    T.VAL_PREC_VTA  != 0
                                                  AND    T.VAL_PREC_TOTAL  != 0)
             )R1
      GROUP  BY R1.COD_PROM--, R1.cant_atendida,R1.sec_ped_vta_det --JCHAVEZ 16102009 se agregó  R1.cant_atendida,R1.sec_ped_vta_det
      ;


   CURSOR cCurProdRegalo(cCodCia_in IN CHAR,cCodProm_in IN CHAR) is
    SELECT PROM.COD_PROM,
           PAK.COD_PROD,PL.VAL_FRAC_LOCAL,
           PAK.CANTIDAD * PL.VAL_FRAC_LOCAL / PAK.VAL_FRAC CANTIDAD
    FROM   VTA_PROD_PAQUETE PAK,
           VTA_PROMOCION PROM,
           LGT_PROD_LOCAL PL

   /*WHERE (SELECT SYSDATE FROM DUAL)
            BETWEEN

             TO_DATE( TO_CHAR(PROM.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(PROM.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')

*/

  WHERE   PROM.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND PROM.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
   AND

    PAK.COD_GRUPO_CIA = cCodCia_in
    AND    PROM.COD_PROM     = cCodProm_in
    AND    PAK.PORC_DCTO     = 100
    AND    PROM.COD_GRUPO_CIA = PAK.COD_GRUPO_CIA
    AND    PROM.COD_PAQUETE_2 = PAK.COD_PAQUETE
    AND    PL.COD_GRUPO_CIA   = PAK.COD_GRUPO_CIA
    AND    PL.COD_PROD        = PAK.COD_PROD
    AND    PL.COD_LOCAL = cCodLocal_in                --JCHAVEZ 19102009
    AND    PAK.COD_PROD NOT IN (
                                select d.cod_prod
                                from   vta_pedido_vta_det d
                                where  d.cod_grupo_cia = cCodGrupoCia_in
                                and    d.cod_local     = cCodLocal_in
                                and    d.num_ped_vta   = cNumPedVta_in
                                and    d.val_prec_vta = 0
                                and    d.val_prec_total = 0
                                );

    nCantItemPed number;
    nStockProd   number;
    nCantNueva   number;
    v_nCanAtendidaProm number; --JCHAVEZ 19102009
    v_nPorcDscto VTA_PROD_PAQUETE.Porc_Dcto%type; --JCHAVEZ 19102009

    varStkFisico NUMBER(6); --ASOSA, 06.07.2010
    varStkCompro NUMBER(6); --ASOSA, 06.07.2010
    varSecRespaldo NUMBER(10); --ASOSA, 06.07.2010

    nContItemRegaloProm INTEGER;        -- 2011-02-21 JOLIVA: PARA CONTAR CANTIDAD DE ITEMS REGALADOS X PACK

    nStkProdPedido number;
   BEGIN

      SELECT MAX(D.SEC_PED_VTA_DET)
      into   nCantItemPed
      FROM   VTA_PEDIDO_VTA_DET D
      WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    D.COD_LOCAL     = cCodLocal_in
      AND    D.NUM_PED_VTA   = cNumPedVta_in;

      FOR cursor_prom IN cur
      LOOP
       DBMS_OUTPUT.put_line(''||cursor_prom.cod_prom);

       FOR cPromFactor IN curPromMultiplo(cursor_prom.cod_prom,cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in)
       LOOP
           DBMS_OUTPUT.put_line(''||cPromFactor.Cod_Prom||' - '||cPromFactor.Factor);

           nContItemRegaloProm := 0; -- 2011-02-21 JOLIVA: LLEVARÁ LA CANTIDAD DE ITEMS REGALADOS POR PACK
         --  VTA_PEDIDO_VTA_DET
         FOR cProdRegalo IN cCurProdRegalo(cCodGrupoCia_in,cPromFactor.Cod_Prom)
         LOOP
              DBMS_OUTPUT.put_line('cProdRega '||cProdRegalo.Cod_Prod ||' '|| cProdRegalo.Cantidad );
           nCantNueva := 0;

           nContItemRegaloProm := nContItemRegaloProm + 1;  -- 2011-02-21 JOLIVA: LLEVARÁ LA CANTIDAD DE ITEMS REGALADOS POR PACK

           --SELECT PL.STK_FISICO - PL.STK_COMPROMETIDO
           begin
           select nvl(sum(nvl(d.cant_atendida*d.val_frac_local/d.val_frac,0)),0)
           into   nStkProdPedido
           from   vta_pedido_vta_det d
           where  d.cod_grupo_cia = cCodGrupoCia_in
           and    d.cod_local = cCodLocal_in
           and    d.num_ped_vta  = cNumPedVta_in
           AND    D.COD_PROD = cProdRegalo.Cod_Prod;
           exception
           when no_data_found then
           nStkProdPedido := 0;
           end;

           SELECT --PL.STK_FISICO--, PL.STK_COMPROMETIDO --ASOSA, 06.07.2010
            --INI - ASOSA - 22/10/2014 - PANHD
                               
                               DECODE(PTOVENTA.PTOVENTA_PROMOCIONES.GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                          cCodLocal_in,
                                                                                                          PL.COD_PROD),0,PL.STK_FISICO,PTOVENTA.PTOVENTA_PROMOCIONES.GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                                                                                                                  cCodLocal_in,
                                                                                                                                                                                                  PL.COD_PROD))      
                               
           --INI - ASOSA - 22/10/2014 - PANHD
           INTO   varStkFisico--, varStkCompro
           FROM   LGT_PROD_LOCAL PL
           WHERE  PL.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    PL.COD_LOCAL     = cCodLocal_in
           AND    PL.COD_PROD      = cProdRegalo.Cod_Prod FOR UPDATE;

           --nStockProd:=varStkFisico - varStkCompro; --ASOSA, 06.07.2010

           --siempre habra stock para dar REGALo dado que ahora NO SE COMPROMETE STOCK
           nStockProd := varStkFisico-nStkProdPedido;
DBMS_OUTPUT.put_line('>>>>nStockProd '||nStockProd);
DBMS_OUTPUT.put_line('>>>>varStkFisico '||varStkFisico);
           IF nStockProd > 0 THEN
              DBMS_OUTPUT.put_line('cProdRegalo.Cantidad '||cProdRegalo.Cantidad);
              DBMS_OUTPUT.put_line('cPromFactor.Factor '||cPromFactor.Factor);


               nCantNueva := cProdRegalo.Cantidad*cPromFactor.Factor;
               DBMS_OUTPUT.put_line('nCantNueva_1 '||nCantNueva);

               DBMS_OUTPUT.put_line('nStockProd '||nStockProd);
               if nCantNueva > 0 then
                --JCHAVEZ 20102009
                UPDATE VTA_PEDIDO_VTA_DET A
                SET A.COD_PROM = cursor_prom.Cod_Prom, A.IND_PROM_AUTOMATICO = 'S'
                WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in AND
                A.COD_LOCAL = cCodLocal_in AND
                A.NUM_PED_VTA = cNumPedVta_in AND
                A.COD_PROD IN (
                select a.cod_prod
                from vta_prod_paquete a
                where a.cod_grupo_cia =  cCodGrupoCia_in
                and a.cod_paquete in ( select b.cod_paquete_1
                                       from vta_promocion b
                                       where b.cod_grupo_cia = cCodGrupoCia_in
                                       and b.cod_prom = cProdRegalo.Cod_Prom
                                     ) );

                select min(a.cant_atendida) into v_nCanAtendidaProm
                from vta_pedido_vta_det a
                where a.cod_grupo_cia = cCodGrupoCia_in
                and a.cod_local = cCodLocal_in
                and a.num_ped_vta = cNumPedVta_in
                and a.cod_prom =  cProdRegalo.Cod_Prom;

                -- 2011-02-21 JOLIVA: SOLO SE INSERTA SI EL PRIMER CÓDIGO REGALADO DEL PACK
                IF nContItemRegaloProm = 1 THEN
                   INSERT INTO VTA_PEDIDO_PACK(COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,COD_PROM,CANTIDAD,USU_CREA_PROMOCION,FEC_CREA_PROMOCION,IND_PROM_AUTOMATICO)
                   VALUES(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in, cursor_prom.Cod_Prom,v_nCanAtendidaProm,cLogin_in,SYSDATE,'S');
                END IF;

                SELECT A.PORC_DCTO INTO v_nPorcDscto
                FROM VTA_PROD_PAQUETE A
                WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                AND A.COD_PAQUETE IN ( SELECT B.COD_PAQUETE_2 FROM VTA_PROMOCION B WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in AND B.COD_PROM = cProdRegalo.Cod_Prom)
                AND A.COD_PROD = cProdRegalo.Cod_Prod;
                --JCHAVEZ 20102009

                   nCantItemPed := nCantItemPed + 1;

                   IF  nCantNueva >= nStockProd  THEN
                       nCantNueva := nStockProd;
                   END IF;
                      DBMS_OUTPUT.put_line('nCantNueva '||nCantNueva);
                   UPDATE LGT_PROD_LOCAL
                   SET    USU_MOD_PROD_LOCAL = cLogin_in,
                          FEC_MOD_PROD_LOCAL = SYSDATE--,
                          --STK_COMPROMETIDO   = STK_COMPROMETIDO + nCantNueva
        	         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        	         AND    COD_LOCAL     = cCodLocal_in
        	         AND    COD_PROD      = cProdRegalo.Cod_Prod;
                   /*
                   SELECT SQ_PBL_RESPALDO_STK.NEXTVAL --ASOSA, 06.07.2010
                   INTO varSecRespaldo
                   FROM Dual;
                   */
                   varSecRespaldo := 0;
                	  /*
                    INSERT INTO PBL_RESPALDO_STK (SEC_RESPALDO_STK, --ASOSA, 06.07.2010
                                                 COD_GRUPO_CIA,COD_LOCAL,NUM_IP_PC,
                									                COD_PROD,CANT_MOV,VAL_FRAC_LOCAL,
                									                USU_CREA_RESPALDO_STK,MODULO,
                                                  NUM_PED_VTA,IND_REGALO)
                							            VALUES (varSecRespaldo, --ASOSA, 06.07.2010
                                                 cCodGrupoCia_in,cCodLocal_in,cIp_in,
                									                cProdRegalo.Cod_Prod,nCantNueva,
                								 	                cProdRegalo.Val_Frac_Local,
                						   		 	              cLogin_in,'V',cNumPedVta_in,'A');
                    */
                     -- insertando los productos regalo que tiene el paquete 2 de la promocion
                            INSERT INTO VTA_PEDIDO_VTA_DET
                            (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,
                            VAL_PREC_VTA,VAL_PREC_TOTAL,PORC_DCTO_1,PORC_DCTO_2,PORC_DCTO_3,
                            PORC_DCTO_TOTAL,VAL_TOTAL_BONO,VAL_FRAC,
                            SEC_USU_LOCAL,USU_CREA_PED_VTA_DET,VAL_PREC_LISTA,
                            VAL_IGV,UNID_VTA,IND_EXONERADO_IGV,
                            VAL_PREC_PUBLIC,IND_ORIGEN_PROD,
                            VAL_FRAC_LOCAL,
                            CANT_FRAC_LOCAL,
                            COD_PROM,
                            IND_PROM_AUTOMATICO,  --JCHAVEZ 19102009
                            AHORRO_PACK, --JCHAVEZ 19102009
                            PORC_DCTO_CALC_PACK, --JCHAVEZ 19102009
                            SEC_RESPALDO_STK --ASOSA, 06.07.2010
                            )
                            SELECT   cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,nCantItemPed,
                                     cProdRegalo.Cod_Prod,nCantNueva,
                                     0,0,
                                     PROD_LOCAL.PORC_DCTO_1,
                                     PROD_LOCAL.PORC_DCTO_2,
                                     PROD_LOCAL.PORC_DCTO_3,
                                     PROD_LOCAL.PORC_DCTO_1+ PROD_LOCAL.PORC_DCTO_2 +
                                     PROD_LOCAL.PORC_DCTO_3,
                                     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.VAL_BONO_VIG,(PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL)),
                            			   PROD_LOCAL.VAL_FRAC_LOCAL,
                                     cSecUsu_in,cLogin_in,
                                     0,
                                     IGV.PORC_IGV,
                                     -- 2010-09-07 JOLIVA: Se corrige para que se muestre la Unidad de Venta del local
                                     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.Unid_Vta),
                                     -- PROD.DESC_UNID_PRESENT,
                                     DECODE(IGV.PORC_IGV,0,'S','N'),
                                     PROD_LOCAL.VAL_PREC_VTA,
                                     null,
                                     PROD_LOCAL.VAL_FRAC_LOCAL,
                                     nCantNueva,--
                                     cProdRegalo.Cod_Prom, --
                                     'S',  --JCHAVEZ 19102009
                                     --(PROD_LOCAL.VAL_PREC_VTA*nCantNueva), --JCHAVEZ 19102009
                                     (((PROD_LOCAL.VAL_PREC_VTA)*DECODE(((100-v_nPorcDscto)/100),1,1,(100-v_nPorcDscto)/100))*nCantNueva),--JCHAVEZ 19102009
                                     v_nPorcDscto, --JCHAVEZ 19102009
                                     varSecRespaldo --ASOSA, 06.07.2010
                            FROM     LGT_PROD PROD,
                            	       LGT_PROD_LOCAL PROD_LOCAL,
                                     pbl_igv igv
                            WHERE    PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND	     PROD_LOCAL.COD_LOCAL = cCodLocal_in
                            AND	     PROD_LOCAL.COD_PROD = cProdRegalo.Cod_Prod
                            AND	     PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
                            AND	     PROD.COD_PROD = PROD_LOCAL.COD_PROD
                            AND      PROD.COD_IGV = IGV.COD_IGV;


             end if;
             -- fin de inssert prod regalo

             END IF;
         end loop;

       END LOOP;

      END LOOP;

   NULL;
   END;

/***********************************************************************************************************************/

PROCEDURE CA_P_ADD_PROD_REGALO_CAMP(
                                      cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cNumPed_in       IN CHAR,
                                      cDni_in          IN CHAR,
                                      cCodCamp_in      IN CHAR,
                                      cSecUsu_in       IN CHAR,
                                      cIpPc_in         IN CHAR,
                                      cUsuCrea_in      IN CHAR
                                     )
  is

  nCantItemPed number;

  vRestoCanjeFraccionLocal number;
  nCantidadRegalo           number;
  vCodProdRegalo char(6);
  vFracLocal number;

  nStockProd   number;

  varStkFisico NUMBER(6); --ASOSA, 06.07.2010
  varStkCompro NUMBER(6); --ASOSA, 06.07.2010
  varSecRespaldo NUMBER(10); --ASOSA, 06.07.2010
  nStkProdPedido number;
 begin

      SELECT MAX(D.SEC_PED_VTA_DET)
      into   nCantItemPed
      FROM   VTA_PEDIDO_VTA_DET D
      WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    D.COD_LOCAL     = cCodLocal_in
      AND    D.NUM_PED_VTA   = cNumPed_in;

     SELECT MOD((CA_CANT_PROD * P.VAL_FRAC_LOCAL), CA_VAL_FRAC) CA,
            (CAMP.CA_CANT_PROD / CAMP.CA_VAL_FRAC) * P.VAL_FRAC_LOCAL cCantRegalo, --esta en fraccion del local
            CAMP.CA_COD_PROD,
            P.VAL_FRAC_LOCAL
       INTO vRestoCanjeFraccionLocal, nCantidadRegalo,vCodProdRegalo,vFracLocal
       FROM VTA_CAMPANA_CUPON CAMP, LGT_PROD_LOCAL P, LGT_PROD MP
      WHERE CAMP.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CAMP.COD_CAMP_CUPON = cCodCamp_in
        AND P.COD_LOCAL = cCodLocal_in
        AND P.COD_GRUPO_CIA = CAMP.COD_GRUPO_CIA
        AND P.COD_PROD = CAMP.CA_COD_PROD
        AND P.COD_GRUPO_CIA = MP.COD_GRUPO_CIA
        AND P.COD_PROD = MP.COD_PROD;

       if vRestoCanjeFraccionLocal = 0  then

           DBMS_OUTPUT.put_line('cProdRega '||vCodProdRegalo ||' cantidad'|| nCantidadRegalo  || ' fracLocal:' ||vFracLocal);

           --SELECT PL.STK_FISICO - PL.STK_COMPROMETIDO
           begin
           select sum(nvl(d.cant_atendida*d.val_frac_local/d.val_frac,0))
           into   nStkProdPedido
           from   vta_pedido_vta_det d
           where  d.cod_grupo_cia = cCodGrupoCia_in
           and    d.cod_local = cCodLocal_in
           and    d.num_ped_vta  = cNumPed_in
           AND    D.COD_PROD = vCodProdRegalo;
           exception
           when no_data_found then
           nStkProdPedido := 0;
           end;

           SELECT PL.STK_FISICO--,PL.STK_COMPROMETIDO --ASOSA, 07.07.2010
           INTO   varStkFisico--, varStkCompro
           FROM   LGT_PROD_LOCAL PL
           WHERE  PL.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    PL.COD_LOCAL     = cCodLocal_in
           AND    PL.COD_PROD      = vCodProdRegalo FOR UPDATE; --ASOSA, 07.07.2010

           --nStockProd:=varStkFisico - varStkCompro; --ASOSA, 07.07.2010
           nStockProd := varStkFisico - nStkProdPedido;
           --Solo si hay stock podra dar el regalo

            --DBMS_OUTPUT.put_line('nStockProd:'||nStockProd);
            --DBMS_OUTPUT.put_line('nCantidadRegalo:'||nCantidadRegalo);


           if nStockProd >= nCantidadRegalo then

              nCantItemPed := nCantItemPed + 1;

                   UPDATE LGT_PROD_LOCAL
                   SET    USU_MOD_PROD_LOCAL = cUsuCrea_in,
                          FEC_MOD_PROD_LOCAL = SYSDATE--,
                          --STK_COMPROMETIDO   = STK_COMPROMETIDO + nCantidadRegalo
        	         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        	         AND    COD_LOCAL     = cCodLocal_in
        	         AND    COD_PROD      = vCodProdRegalo;


           -- DBMS_OUTPUT.put_line('INSERT');
                   /*
                   SELECT SQ_PBL_RESPALDO_STK.NEXTVAL --/ASOSA, 06.07.2010
                   INTO varSecRespaldo
                   FROM Dual;
                   */
                   varSecRespaldo := 0;
                	  /*
                    INSERT INTO PBL_RESPALDO_STK (SEC_RESPALDO_STK, --/ASOSA, 06.07.2010
                                                  COD_GRUPO_CIA,COD_LOCAL,NUM_IP_PC,
                									                COD_PROD,CANT_MOV,VAL_FRAC_LOCAL,
                									                USU_CREA_RESPALDO_STK,MODULO,
                                                  NUM_PED_VTA,IND_REGALO)
                							            VALUES (varSecRespaldo, --/ASOSA, 06.07.2010
                                                 cCodGrupoCia_in,cCodLocal_in,cIpPc_in,
                									                vCodProdRegalo,nCantidadRegalo,
                								 	                vFracLocal,
                						   		 	              cUsuCrea_in,'V',cNumPed_in,'S');*/

                     -- insertando los productos regalo que tiene el paquete 2 de la promocion
                            INSERT INTO VTA_PEDIDO_VTA_DET
                            (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,
                            VAL_PREC_VTA,VAL_PREC_TOTAL,PORC_DCTO_1,PORC_DCTO_2,PORC_DCTO_3,
                            PORC_DCTO_TOTAL,VAL_TOTAL_BONO,VAL_FRAC,
                            SEC_USU_LOCAL,USU_CREA_PED_VTA_DET,VAL_PREC_LISTA,
                            VAL_IGV,UNID_VTA,IND_EXONERADO_IGV,
                            VAL_PREC_PUBLIC,IND_ORIGEN_PROD,
                            VAL_FRAC_LOCAL,
                            CANT_FRAC_LOCAL,
                            COD_CAMP_CUPON,
                            PORC_ZAN,       -- 2009-11-09 JOLIVA
                            SEC_RESPALDO_STK --/ASOSA, 06.07.2010
                            )
                            SELECT   cCodGrupoCia_in,cCodLocal_in,cNumPed_in,nCantItemPed,
                                     vCodProdRegalo,nCantidadRegalo,
                                     0,0,
                                     PROD_LOCAL.PORC_DCTO_1,
                                     PROD_LOCAL.PORC_DCTO_2,
                                     PROD_LOCAL.PORC_DCTO_3,
                                     PROD_LOCAL.PORC_DCTO_1+ PROD_LOCAL.PORC_DCTO_2 +
                                     PROD_LOCAL.PORC_DCTO_3,
                                     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.VAL_BONO_VIG,(PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL)),
                            			   PROD_LOCAL.VAL_FRAC_LOCAL,
                                     cSecUsu_in,cUsuCrea_in,
                                     0,
                                     IGV.PORC_IGV,prod_local.unid_vta,DECODE(IGV.PORC_IGV,0,'S','N'),
                                     PROD_LOCAL.VAL_PREC_VTA,
                                     '8',-- ORIGEN DE REGALO DE CAMPAÑAS ACUMULADAS
                                     PROD_LOCAL.VAL_FRAC_LOCAL,
                                     nCantidadRegalo,
                                     cCodCamp_in,
                                     PROD_LOCAL.PORC_ZAN,   -- 2009-11-09 JOLIVA
                                     varSecRespaldo --/ASOSA, 06.07.2010
                            FROM     LGT_PROD PROD,
                            	       LGT_PROD_LOCAL PROD_LOCAL,
                                     pbl_igv igv
                            WHERE    PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND	     PROD_LOCAL.COD_LOCAL     = cCodLocal_in
                            AND	     PROD_LOCAL.COD_PROD      = vCodProdRegalo
                            AND	     PROD.COD_GRUPO_CIA       = PROD_LOCAL.COD_GRUPO_CIA
                            AND	     PROD.COD_PROD = PROD_LOCAL.COD_PROD
                            AND      PROD.COD_IGV  = IGV.COD_IGV;



                            CA_P_INSERT_CANJ_CLI (cCodGrupoCia_in,cCodLocal_in,
                                   cNumPed_in,
                                   cDni_in,
                                   cCodCamp_in,
                                   nCantItemPed,
                                   vCodProdRegalo,
                                   nCantidadRegalo,
                                   vFracLocal,
                                   'P',--todo estare P pendiente porque aunn no es cobrado el pedido
                                   cUsuCrea_in
                                 );


           end if;


       end if;

 end;

/***********************************************************************************************************************/

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
                                 )
  IS

  dFechaPed date;

  BEGIN
          select c.fec_ped_vta
          into   dFechaPed
          from   vta_pedido_vta_cab c
          where  c.cod_grupo_cia = cCodGrupoCia_in
          and    c.cod_local     = cCodLocal_in
          and    c.num_ped_vta   = cNumPed_in;

          INSERT INTO CA_CANJ_CLI_PED
          (
            DNI_CLI,
            COD_GRUPO_CIA,
            COD_CAMP_CUPON,
            COD_LOCAL,
            NUM_PED_VTA,
            FEC_PED_VTA,
            SEC_PED_VTA,
            COD_PROD,
            CANT_ATENDIDA,
            VAL_FRAC,
            ESTADO,
            USU_CREA_CA_CANJ_CLI,
            FEC_CREA_CA_CANJ_CLI
           )
          VALUES
          (cDni_in,
           cCodGrupoCia_in,
           cCodCamp_in,
           cCodLocal_in,
           cNumPed_in,
           dFechaPed,
           nSecPedVta_in,
           cCodProd_in,
           nCantPedido_in,
           nValFracPedido_in,
           cEstado_in,
           cUsuCrea_in,
           sysdate
           );

  END;

/***********************************************************************************************************************/

  PROCEDURE VTA_GRABAR_PED_REGALO_DET(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in	   IN CHAR,
                                    cNumPedVta_in    IN CHAR,
                                    cSecProdDet_in   IN NUMBER,
                                    cCodProd_in      IN CHAR,
                                    cCantAtend_in    IN NUMBER,
                                    cValPredVenta_in IN CHAR,
                                    cSecUsu_in       IN CHAR,
                                    cLoginUsu_in     IN CHAR,
                                    vSecRes_in       IN CHAR)
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
      PORC_ZAN,      -- 2009-11-09 JOLIVA
      SEC_RESPALDO_STK --ASOSA, 09.08.2010
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
               PROD_LOCAL.PORC_ZAN,   -- 2009-11-09 JOLIVA
               vSecRes_in --ASOSA, 09.07.2010
      FROM     LGT_PROD PROD,
      	       LGT_PROD_LOCAL PROD_LOCAL,
               pbl_igv igv
      WHERE    PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
      AND	     PROD_LOCAL.COD_LOCAL = cCodLocal_in
      AND	     PROD_LOCAL.COD_PROD = cCodProd_in
      AND	     PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
      AND	     PROD.COD_PROD = PROD_LOCAL.COD_PROD
      AND      PROD.COD_IGV = IGV.COD_IGV;
  END;

/***********************************************************************************************************************/

PROCEDURE CAJ_UPD_STK_PROD_DETALLE(cCodGrupoCia_in 	   IN CHAR,
						   	 			                     cCodLocal_in    	   IN CHAR,
							 			                       cNumPedVta_in   	   IN CHAR,
										                       cCodMotKardex_in    IN CHAR,
						   	  			                   cTipDocKardex_in    IN CHAR,
										                       cCodNumeraKardex_in IN CHAR,
							 			                       cUsuModProdLocal_in IN CHAR)
  IS
    v_cIndProdVirtual CHAR(1);
    mesg_body VARCHAR2(4000);
	CURSOR productos_Kardex IS
         SELECT VTA_DET.COD_PROD,
                SUM(VTA_DET.CANT_FRAC_LOCAL) CANT_ATENDIDA, --ERIOS 29/05/2008 Cantidad calculada
                PROD_LOCAL.STK_FISICO,
				 	      PROD_LOCAL.VAL_FRAC_LOCAL,
				 	      PROD_LOCAL.UNID_VTA,
                DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR
				 FROM   VTA_PEDIDO_VTA_DET VTA_DET,
					      LGT_PROD_LOCAL PROD_LOCAL,
                LGT_PROD_VIRTUAL VIR
			   WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
				 AND    VTA_DET.COD_LOCAL     = cCodLocal_in
				 AND	  VTA_DET.NUM_PED_VTA   = cNumPedVta_in
				 AND	  VTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
				 AND	  VTA_DET.COD_LOCAL     = PROD_LOCAL.COD_LOCAL
				 AND	  VTA_DET.COD_PROD      = PROD_LOCAL.COD_PROD
         AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
         AND    PROD_LOCAL.COD_PROD = VIR.COD_PROD(+)
          --INI ASOSA - 07/10/2014 - PANHD
             AND  NOT EXISTS (
                                                                    SELECT 1
                                                                    FROM LGT_PROD PP
                                                                    WHERE PP.IND_TIPO_CONSUMO = TIPO_PROD_FINAL
                                                                    AND VTA_DET.COD_PROD = PP.COD_PROD
                                                                    AND VTA_DET.COD_GRUPO_CIA = PP.COD_GRUPO_CIA
                                                                    )
             --FIN ASOSA - 07/10/2014 - PANHD
				 GROUP BY VTA_DET.COD_PROD,
					   	    PROD_LOCAL.VAL_FRAC_LOCAL,
					   	    PROD_LOCAL.UNID_VTA,
						      PROD_LOCAL.STK_FISICO,
                  DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI);
  --21/11/2007 dubilluz modificado
  CURSOR productos_Respaldo IS
         SELECT VTA_DET.COD_PROD,
                VTA_DET.CANT_ATENDIDA CANT_ATENDIDA,
                VTA_DET.VAL_FRAC AS VAL_FRAC_VTA,
					      PROD_LOCAL.STK_FISICO,
					      PROD_LOCAL.VAL_FRAC_LOCAL,
					      PROD_LOCAL.UNID_VTA,
                DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR,
                VTA_DET.SEC_RESPALDO_STK --ASOSA, 11.07.2010
				 FROM   VTA_PEDIDO_VTA_DET VTA_DET,
					      LGT_PROD_LOCAL PROD_LOCAL,
                LGT_PROD_VIRTUAL VIR
			   WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
				 AND	  VTA_DET.COD_LOCAL = cCodLocal_in
				 AND	  VTA_DET.NUM_PED_VTA = cNumPedVta_in
				 AND	  VTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
				 AND	  VTA_DET.COD_LOCAL = PROD_LOCAL.COD_LOCAL
				 AND	  VTA_DET.COD_PROD = PROD_LOCAL.COD_PROD
         AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
         AND    PROD_LOCAL.COD_PROD = VIR.COD_PROD(+)
         --INI ASOSA - 07/10/2014 - PANHD
             AND  NOT EXISTS (
                                                                    SELECT 1
                                                                    FROM LGT_PROD PP
                                                                    WHERE PP.IND_TIPO_CONSUMO = TIPO_PROD_FINAL
                                                                    AND VTA_DET.COD_PROD = PP.COD_PROD
                                                                    AND VTA_DET.COD_GRUPO_CIA = PP.COD_GRUPO_CIA
                                                                    )
             --FIN ASOSA - 07/10/2014 - PANHD
         ORDER  BY VTA_DET.CANT_ATENDIDA DESC;

    v_nCantAtendida VTA_PEDIDO_VTA_DET.CANT_ATENDIDA%TYPE;

    stkComp lgt_prod_local.stk_fisico%TYPE;
            --lgt_prod_local.stk_comprometido%TYPE;
    stkFis  lgt_prod_local.stk_fisico%TYPE;
    fecMod  lgt_prod_local.fec_mod_prod_local%TYPE;
    usuMod  lgt_prod_local.usu_mod_prod_local%TYPE;
  BEGIN
  	--v_cIndActStk := INDICADOR_NO;
   	FOR productos_K IN productos_Kardex
	  LOOP
	  	  --GRABAR KARDEX
        v_cIndProdVirtual := productos_K.IND_PROD_VIR;
        IF v_cIndProdVirtual = INDICADOR_SI THEN
    		  Ptoventa_Inv.INV_GRABAR_KARDEX_VIRTUAL(cCodGrupoCia_in,
    		  								                       cCodLocal_in,
                            										 productos_K.COD_PROD,
                            										 cCodMotKardex_in,
                            										 cTipDocKardex_in,
                            										 cNumPedVta_in,
                            										 (productos_k.CANT_ATENDIDA * -1),
                            										 productos_K.VAL_FRAC_LOCAL,
                            										 productos_K.UNID_VTA,
                            										 cUsuModProdLocal_in,
                            										 cCodNumeraKardex_in);
        ELSE
          Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
    		  								               cCodLocal_in,
                    										 productos_K.COD_PROD,
                    										 cCodMotKardex_in,
                    										 cTipDocKardex_in,
                    										 cNumPedVta_in,
                    										 productos_K.STK_FISICO,
                    										 (productos_k.CANT_ATENDIDA * -1),
                    										 productos_K.VAL_FRAC_LOCAL,
                    										 productos_K.UNID_VTA,
                    										 cUsuModProdLocal_in,
                    										 cCodNumeraKardex_in);
        END IF;
		    --v_cIndActStk := INDICADOR_SI;
    END LOOP;
    FOR productos_R IN productos_Respaldo
	  LOOP
        v_cIndProdVirtual := productos_R.IND_PROD_VIR;
        IF v_cIndProdVirtual = INDICADOR_NO THEN

          SELECT USU_MOD_PROD_LOCAL, FEC_MOD_PROD_LOCAL, STK_FISICO, 0--STK_COMPROMETIDO --ASOSA, 11.07.2010
          INTO usuMod, fecMod, stkFis, stkComp
          FROM LGT_PROD_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    			AND	   COD_LOCAL = cCodLocal_in
    			AND	   COD_PROD = productos_R.COD_PROD FOR UPDATE;

          --ACTUALIZA STK PRODUCTO
          --ERIOS 29/05/2008 Calcula la cantidad atendida a la fraccion del local.
          v_nCantAtendida := (productos_R.CANT_ATENDIDA*productos_R.VAL_FRAC_LOCAL)/productos_R.VAL_FRAC_VTA;
    		  UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = cUsuModProdLocal_in, FEC_MOD_PROD_LOCAL = SYSDATE,
                 STK_FISICO = STK_FISICO - v_nCantAtendida--,
    						 --STK_COMPROMETIDO = STK_COMPROMETIDO - v_nCantAtendida
    			WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    			AND	   COD_LOCAL = cCodLocal_in
    			AND	   COD_PROD = productos_R.COD_PROD;
    	    -- LIMPIA PEDIDO COBRADO DE RESPALDO STOCK
      	  --IF(v_cIndActStk = INDICADOR_SI) THEN
          /*
          DELETE PBL_RESPALDO_STK
    	    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
     	    AND 	 COD_LOCAL = cCodLocal_in
    	    AND 	 COD_PROD = productos_R.COD_PROD
    	    AND	   NUM_PED_VTA = cNumPedVta_in
          AND    CANT_MOV = productos_R.CANT_ATENDIDA
          AND    MODULO = 'V'
          AND    ROWNUM = 1;*/
          --se modifico el modo de actualizar el respaldo stock
          --21/11/2007 dubilluz modificado
          /*UPDATE PBL_RESPALDO_STK
          SET    CANT_MOV = CANT_MOV -  productos_R.CANT_ATENDIDA
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    	    AND 	 COD_PROD = productos_R.COD_PROD
    	    AND	   NUM_PED_VTA = cNumPedVta_in
          AND    CANT_MOV >= productos_R.CANT_ATENDIDA
          AND    MODULO = 'V'
          AND    ROWNUM = 1;*/
      	  --END IF;
          --DELETE FROM PBL_RESPALDO_STK x --ASOSA, 11.07.2010
          --WHERE x.sec_respaldo_stk=productos_R.SEC_RESPALDO_STK;
        END IF;
    END LOOP;
          --ahora elimina los respaldo
          --21/11/2007 dubilluz  modificado
          /*DELETE PBL_RESPALDO_STK
    	    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
     	    AND 	 COD_LOCAL = cCodLocal_in
    	    AND	   NUM_PED_VTA = cNumPedVta_in
          AND    CANT_MOV = 0
          AND    MODULO = 'V';*/
  EXCEPTION
    WHEN OTHERS THEN
         mesg_body := 'ERROR AL COBRAR PEDIDO No ' || cNumPedVta_in || '. ' || SQLERRM;
         FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                    cCodLocal_in,
                                    'DUBILLUZ@mifarma.com.pe',
                                    'ERROR AL COBRAR PEDIDO',
                                    'ERROR',
                                    mesg_body,
                                    '');
         RAISE;
  END;

/***********************************************************************************************************************/

 PROCEDURE CAJ_ANULA_PED_PEND_MASIVO(cCodGrupoCia_in 	  IN CHAR,
  		   						   	  cCodLocal_in    	  IN CHAR,
									  nMinutos_in	  	  IN NUMBER,
									  cUsuModPedVtaCab_in IN CHAR)
  IS

  CURSOR pedidos IS
    SELECT VTA_CAB.NUM_PED_VTA NUMERO
		FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
		WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   VTA_CAB.COD_LOCAL = cCodLocal_in
    --27/09/2007 DUBILLUZ  MODIFICADO
    --08.07.2014 DUBILLUZ  MODIFICADO
    --AND	   VTA_CAB.IND_DELIV_AUTOMATICO <> 'S'
		AND	   VTA_CAB.FEC_PED_VTA < (SYSDATE - (nMinutos_in/24/60))
		AND	   VTA_CAB.EST_PED_VTA = EST_PED_PENDIENTE;

  --18/02/2014 RHERRERA : Cursor retorna los pedidos Recarga Virtuales PENDIENTES
  CURSOR pedidos_virtual IS
    SELECT D.NUM_PED_VTA  NUMERO
     FROM  vta_pedido_vta_det d,
        lgt_prod_virtual p
    WHERE d.cod_grupo_cia = cCodGrupoCia_in
    AND   d.cod_local     = cCodLocal_in
    AND   d.num_ped_vta   IN
    (
    SELECT VTA_CAB.NUM_PED_VTA
        FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
        WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND	   VTA_CAB.COD_LOCAL = cCodLocal_in
    --27/09/2007 DUBILLUZ  MODIFICADO
    AND	   VTA_CAB.IND_DELIV_AUTOMATICO <> 'S'
        AND	   VTA_CAB.FEC_PED_VTA < (SYSDATE - (30/24/60))
        AND	   VTA_CAB.EST_PED_VTA = 'P'
                  )
    and   d.cod_grupo_cia = p.cod_grupo_cia
    and   d.cod_prod      = p.cod_prod
    and   p.tip_prod_virtual = 'R';



  nIsPedVirtual number;
  BEGIN

  	 --18/02/2014 RHERRERA: Actualiza los PEDIDOS RECARGA VIRTUAL PENDIENTE A ANULADOS.
     --08.07.2014 DUBILLUZ: No se va anular los pedidos pendientes de RECARGA
	  /*FOR pedidos_recvir IN pedidos_virtual
			LOOP


      	  	  CAJ_ANULAR_PEDIDO_PENDIENTE(cCodGrupoCia_in,
      		  		   						   	  cCodLocal_in,
      											  pedidos_recvir.NUMERO,
      											  cUsuModPedVtaCab_in);

		END LOOP;*/



	  FOR pedidos_rec IN pedidos
	  LOOP
        select count(1)
        into  nIsPedVirtual
        from  vta_pedido_vta_det d,
              lgt_prod_virtual p
        where d.cod_grupo_cia = cCodGrupoCia_in
        and   d.cod_local     = cCodLocal_in
        and   d.num_ped_vta   = pedidos_rec.NUMERO
        and   d.cod_grupo_cia = p.cod_grupo_cia
        and   d.cod_prod      = p.cod_prod
        and   p.tip_prod_virtual = 'R';

        if nIsPedVirtual = 0 then
      	  	  CAJ_ANULAR_PEDIDO_PENDIENTE(cCodGrupoCia_in,
      		  		   						   	        cCodLocal_in,
                                          pedidos_rec.NUMERO,
                                          cUsuModPedVtaCab_in);
        end if;
	  END LOOP;
  END;

/***********************************************************************************************************************/

PROCEDURE CAJ_ANULAR_PEDIDO_PENDIENTE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        vIdUsu_in       IN CHAR,
                                        cModulo_in      IN CHAR DEFAULT 'V')
  AS
    mesg_body VARCHAR2(4000);
    v_cIndProdVirtual CHAR(1);
    v_cIndDelivAutomatico VTA_PEDIDO_VTA_CAB.IND_DELIV_AUTOMATICO%TYPE;
    CURSOR curProd IS
           SELECT DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.CANT_FRAC_LOCAL,--ERIOS 05/06/2008 Se obtiene el cantida en el frac. del local.
                  DET.SEC_PED_VTA_DET,
                  DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR,
                  DET.Sec_Respaldo_Stk, --ASOSA, 12.07.2010
                  DET.ROWID
           FROM   VTA_PEDIDO_VTA_DET DET,
                  LGT_PROD_VIRTUAL VIR
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+)
           ORDER BY DET.CANT_ATENDIDA DESC FOR UPDATE;
    v_rCurProd curProd%ROWTYPE;

    v_cEstPedido  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
    v_cIndAnulado VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
/*

           CURSOR curListaProdRegaloAutomatico IS
		   				  SELECT COD_PROD,
	                     trim(to_char(CANT_MOV,'999999')) CANT_MOVx,
								       trim(to_char(VAL_FRAC_LOCAL,'9999')) VAL_FRAC_LOCALx,
                       trim(to_char(SEC_RESPALDO_STK,'9999999999')) SEC_RESPALDO_STKx --ASOSA, 13.07.2010
			          FROM 	 PBL_RESPALDO_STK
			          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
					 	    AND 	 COD_LOCAL     = cCodLocal_in
                AND    NUM_PED_VTA   = cNumPedVta_in
                AND    IND_REGALO    = 'A';*/

    stkComp  lgt_prod_local.stk_fisico%TYPE;
             ---lgt_prod_local.stk_comprometido%TYPE; --ASOSA, 12.07.2010
    cantidad lgt_prod_local.stk_fisico%TYPE; --ASOSA, 12.07.2010
    valFracLocal lgt_prod_local.val_frac_local%TYPE; --ASOSA, 12.07.2010
    cantMov     vta_pedido_vta_det.cant_atendida%type;-- pbl_respaldo_stk.cant_mov%TYPE; --ASOSA, 12.07.2010
    valFracResp vta_pedido_vta_det.val_frac_local%type;--pbl_respaldo_stk.val_frac_local%TYPE; --ASOSA, 12.07.2010
    ind VARCHAR2(200); --ASOSA, 13.07.2010


    cantidadRespaldo NUMBER(3); --ASOSA, 24.08.2010
    
    CURSOR curPedidos IS
      SELECT EST_PED_VTA,
             IND_PEDIDO_ANUL,
             IND_DELIV_AUTOMATICO,
             ROWID
      FROM   VTA_PEDIDO_VTA_CAB
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL = cCodLocal_in
      AND    NUM_PED_VTA = cNumPedVta_in  FOR UPDATE;
    vRowCurPedidos  curPedidos%ROWTYPE;
    
  BEGIN
    --JCORTEZ 09/01/2007
    --SE VALIDA EL ESTADO DEL PEDIDO ANTES DE ANULAR
    /*SELECT EST_PED_VTA,
           IND_PEDIDO_ANUL
    INTO   v_cEstPedido,
           v_cIndAnulado
    FROM   VTA_PEDIDO_VTA_CAB
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    NUM_PED_VTA = cNumPedVta_in  FOR UPDATE;*/
    OPEN curPedidos;
    LOOP
      FETCH curPedidos INTO vRowCurPedidos;
      EXIT WHEN curPedidos%NOTFOUND;
    -- AND    VAL_NETO_PED_VTA = nMontoVta_in

        --if v_cEstPedido = 'N' THEN
        IF vRowCurPedidos.EST_PED_VTA = 'N' THEN
         RAISE_APPLICATION_ERROR(-20002,'El Pedido esta anulado.');
        ELSIF vRowCurPedidos.EST_PED_VTA = 'C' THEN--elsif v_cEstPedido = 'C' THEN
         RAISE_APPLICATION_ERROR(-20003,'El Pedido fue cobrado. ¡No puede Anular este Pedido!');
        END IF;

        --IF v_cEstPedido <> 'P' THEN
        IF vRowCurPedidos.EST_PED_VTA <> 'P' THEN
          RAISE_APPLICATION_ERROR(-20001,'El Pedido no esta pendiente. ¡No puede Anular este Pedido!');
        END IF;
        -- KMONCADA 21.04.2016 SE COMENTO PARA REEMPLAZAR X CURSOR
        /* 
        SELECT VTA_CAB.IND_DELIV_AUTOMATICO
        INTO	  v_cIndDelivAutomatico
        FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
        WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND	  VTA_CAB.COD_LOCAL = cCodLocal_in
        AND	  VTA_CAB.NUM_PED_VTA = cNumPedVta_in;*/

        /*
        UPDATE VTA_PEDIDO_VTA_CAB 
        SET USU_MOD_PED_VTA_CAB = vIdUsu_in,  
           FEC_MOD_PED_VTA_CAB = SYSDATE,
           EST_PED_VTA = EST_PED_ANULADO,
           IND_PEDIDO_ANUL = INDICADOR_SI
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_LOCAL     = cCodLocal_in
        AND NUM_PED_VTA   = cNumPedVta_in;
        */
        UPDATE VTA_PEDIDO_VTA_CAB 
        SET USU_MOD_PED_VTA_CAB = vIdUsu_in,  
            FEC_MOD_PED_VTA_CAB = SYSDATE,
            EST_PED_VTA = EST_PED_ANULADO,
            IND_PEDIDO_ANUL = INDICADOR_SI
        WHERE ROWID = vRowCurPedidos.ROWID;
        
        dbms_output.put_line('ddddddddddddddddd');
        /*
        IF(v_cIndDelivAutomatico = INDICADOR_SI) THEN
          UPDATE TMP_VTA_PEDIDO_VTA_CAB TMP_CAB SET TMP_CAB.USU_MOD_PED_VTA_CAB = vIdUsu_in, TMP_CAB.FEC_MOD_PED_VTA_CAB = SYSDATE,
                 --MODIFICADO UBILLUZ
                 TMP_CAB.EST_PED_VTA = EST_PED_PENDIENTE,--EST_PED_ANULADO
                 TMP_CAB.Num_Ped_Vta_Origen = '' ,
                 TMP_CAB.IND_PEDIDO_ANUL = INDICADOR_SI --DUBILLUZ  24/08/2007
          WHERE  TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    TMP_CAB.COD_LOCAL_ATENCION = cCodLocal_in
          AND    TMP_CAB.NUM_PED_VTA_ORIGEN = cNumPedVta_in;
        END IF;
        */
        IF(vRowCurPedidos.Ind_Deliv_Automatico = INDICADOR_SI) THEN
          UPDATE TMP_VTA_PEDIDO_VTA_CAB TMP_CAB 
          SET TMP_CAB.USU_MOD_PED_VTA_CAB = vIdUsu_in, 
              TMP_CAB.FEC_MOD_PED_VTA_CAB = SYSDATE,
              --MODIFICADO UBILLUZ
              TMP_CAB.EST_PED_VTA = EST_PED_PENDIENTE,--EST_PED_ANULADO
              TMP_CAB.Num_Ped_Vta_Origen = '' ,
              TMP_CAB.IND_PEDIDO_ANUL = INDICADOR_SI --DUBILLUZ  24/08/2007
          WHERE TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
          AND TMP_CAB.COD_LOCAL_ATENCION = cCodLocal_in
          AND TMP_CAB.NUM_PED_VTA_ORIGEN = cNumPedVta_in;
        END IF;
        
        OPEN curProd;
        --FOR v_rCurProd IN curProd
        LOOP
          FETCH curProd INTO v_rCurProd;
          EXIT WHEN curProd%NOTFOUND;
           v_cIndProdVirtual := v_rCurProd.IND_PROD_VIR;
           --IF v_cIndProdVirtual = INDICADOR_SI THEN
             -- KMONCADA 21.04.2016 CAMBIO PARA EVITAR EL BLOQUEO
             /*UPDATE VTA_PEDIDO_VTA_DET  
             SET USU_MOD_PED_VTA_DET = vIdUsu_in, 
                 FEC_MOD_PED_VTA_DET = SYSDATE,
  	  		       EST_PED_VTA_DET = 'N'
             WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
             AND    COD_LOCAL     = cCodLocal_in
             AND    NUM_PED_VTA   = cNumPedVta_in
             AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;*/
             UPDATE VTA_PEDIDO_VTA_DET  
             SET USU_MOD_PED_VTA_DET = vIdUsu_in, 
                 FEC_MOD_PED_VTA_DET = SYSDATE,
  	  		       EST_PED_VTA_DET = 'N'
             WHERE  ROWID = v_rCurProd.ROWID;
           --ELSE
               /*
               SELECT a.stk_comprometido, a.val_frac_local --/ ASOSA, 12.07.2010
               INTO stkComp, valFracLocal
               FROM lgt_prod_local a
               WHERE a.cod_grupo_cia=cCodGrupoCia_in
               AND a.cod_local=cCodLocal_in
               AND a.cod_prod=v_rCurProd.Cod_Prod FOR UPDATE;

               SELECT COUNT(*) INTO cantidadRespaldo  --INI ASOSA, 24.08.2010
               FROM pbl_respaldo_stk b
               WHERE b.cod_grupo_cia=cCodGrupoCia_in
               AND b.cod_prod=v_rCurProd.Cod_Prod
               AND b.sec_respaldo_stk=v_rCurProd.Sec_Respaldo_Stk;
               */
               /*
               IF cantidadRespaldo =1 THEN

                     SELECT b.cant_mov, b.val_frac_local --/ ASOSA, 12.07.2010
                     INTO cantMov, valFracResp
                     FROM pbl_respaldo_stk b
                     WHERE b.cod_grupo_cia=cCodGrupoCia_in
                     AND b.cod_prod=v_rCurProd.Cod_Prod
                     AND b.sec_respaldo_stk=v_rCurProd.Sec_Respaldo_Stk;

                     cantidad := (valFracLocal/valFracResp)*cantMov; --ASOSA, 12.07.2010

                     UPDATE LGT_PROD_LOCAL
                     SET USU_MOD_PROD_LOCAL = vIdUsu_in, FEC_MOD_PROD_LOCAL = SYSDATE,
                            STK_COMPROMETIDO = STK_COMPROMETIDO - cantidad --ASOSA, 12.07.2010
                     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                     AND   COD_LOCAL = cCodLocal_in
                     AND   COD_PROD = v_rCurProd.COD_PROD;

                     UPDATE VTA_PEDIDO_VTA_DET
                     SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
          	  		          EST_PED_VTA_DET = 'N'
                     WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                     AND    COD_LOCAL     = cCodLocal_in
                     AND    NUM_PED_VTA   = cNumPedVta_in
                     AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;

               DELETE FROM pbl_respaldo_stk b
               WHERE b.sec_respaldo_stk=v_rCurProd.Sec_Respaldo_Stk;

             ELSE
                     UPDATE VTA_PEDIDO_VTA_DET
                 SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
      	  		          EST_PED_VTA_DET = 'N'
                 WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                 AND    COD_LOCAL     = cCodLocal_in
                 AND    NUM_PED_VTA   = cNumPedVta_in
                 AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;
             END IF;
             */                                                    --FIN ASOSA, 24.08.2010

          /*UPDATE PBL_RESPALDO_STK
          SET    CANT_MOV = CANT_MOV -  v_rCurProd.CANT_ATENDIDA
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    	    AND 	 COD_PROD = v_rCurProd.COD_PROD
    	    AND	   NUM_PED_VTA = cNumPedVta_in
          AND    CANT_MOV >= v_rCurProd.CANT_ATENDIDA
          AND    MODULO = cModulo_in
          AND    ROWNUM = 1;*/

           --END IF;
      END LOOP;
      CLOSE curProd;

          --ahora elimina los respaldo
          --21/11/2007 dubilluz  modificado
          /*DELETE PBL_RESPALDO_STK
    	    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
     	    AND 	 COD_LOCAL = cCodLocal_in
    	    AND	   NUM_PED_VTA = cNumPedVta_in
          AND    CANT_MOV = 0
          AND    MODULO = 'V';*/

          -------------------------
          --Inicio de añadir
          --Añadido para Eliminar los productos automaticos
          --dubilluz 18.02.2009
          /*FOR respaldoStk_rec IN curListaProdRegaloAutomatico LOOP
            /*UPDATE LGT_PROD_LOCAL l
               SET FEC_MOD_PROD_LOCAL = SYSDATE, USU_MOD_PROD_LOCAL = vIdUsu_in,
                   STK_COMPROMETIDO = NVL(STK_COMPROMETIDO,0) - respaldoStk_rec.CANT_MOV/NVL(respaldoStk_rec.VAL_FRAC_LOCAL,l.val_frac_local)*l.val_frac_local --MODIFICACION JLUNA 20090216
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       	     AND 	 COD_LOCAL = cCodLocal_in
             AND 	 COD_PROD  = respaldoStk_rec.COD_PROD;

            DELETE PBL_RESPALDO_STK
      	    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       	    AND    COD_LOCAL     = cCodLocal_in
            AND    NUM_PED_VTA   = cNumPedVta_in
            AND    IND_REGALO    = 'A'
            AND 	 COD_PROD      = respaldoStk_rec.COD_PROD
            AND    VAL_FRAC_LOCAL = respaldoStk_rec.VAL_FRAC_LOCAL
            AND    CANT_MOV = respaldoStk_rec.CANT_MOV
            AND    ROWNUM = 1;
            ind:=PVTA_F_INS_DEL_UPD_STK_RES(cCodGrupoCia_in,      --/ASOSA, 13.07.2010
                                                cCodLocal_in,
                                                respaldoStk_rec.Cod_Prod,
                                                '0',
                                                respaldoStk_rec.Val_Frac_Localx,
                                                respaldoStk_rec.Sec_Respaldo_Stkx,
                                                'V',
                                                vIdUsu_in);
          END LOOP;
          */
      update vta_camp_pedido_cupon p
      set    p.estado = 'N'
      where  p.cod_grupo_cia = cCodGrupoCia_in
      and    p.cod_local = cCodLocal_in
      and    p.num_ped_vta = cNumPedVta_in
      and    p.estado = 'S';


  /*EXCEPTION
    WHEN OTHERS THEN
         mesg_body := 'ERROR AL ANULAR PEDIDO PENDIENTE No ' || cNumPedVta_in || '. ' || SQLERRM;
         FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                    cCodLocal_in,
                                    'asosa@mifarma.com.pe, operador',
                                    'ERROR AL ANULAR PEDIDO PENDIENTE',
                                    'ERROR',
                                    mesg_body,
                                    '');
         RAISE;*/
     END LOOP;
     CLOSE curPedidos;
  END;

/***********************************************************************************************************************/

PROCEDURE CA_P_UPDATE_ELIMINA_REGALO(cCodGrupoCia_in       IN CHAR,
                                    cCodLocal_in          IN CHAR,
                                    cUsuMod_in            IN CHAR,
                                    cNumPed_in            IN CHAR,
                                    cAccion_in            IN CHAR,
                                    cIndQuitaRespaldo_in  IN CHAR)
 IS

   cCodEncarte varchar2(20);
  cCodRegalo varchar2(20);

 CURSOR curProdRegaloGeneral IS
           SELECT DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  --DET.Val_Frac_Local,
                  DET.SEC_PED_VTA_DET,
                  DET.SEC_RESPALDO_STK --ASOSA, 13.07.2010
           FROM   VTA_PEDIDO_VTA_DET DET
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPed_in
           ORDER BY DET.CANT_ATENDIDA DESC;

  CURSOR curProdEncartes IS
    SELECT X.COD_ENCARTE,X.COD_PROD_REGALO
    FROM VTA_ENCARTE X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.ESTADO='A'
    AND TRUNC(SYSDATE) BETWEEN TRUNC(X.FECH_INICIO) AND TRUNC(X.FECH_FIN);

    stkFis lgt_prod_local.stk_fisico%TYPE; --ASOSA, 13.07.2010
    stkComp lgt_prod_local.stk_fisico%TYPE;
            --lgt_prod_local.stk_comprometido%TYPE; --ASOSA, 13.07.2010
    valFracLocal lgt_prod_local.val_frac_local%TYPE; --ASOSA, 13.07.2010
    cantRegResp vta_pedido_vta_det.cant_atendida%type;
                --pbl_respaldo_stk.cant_mov%TYPE; --ASOSA, 13.07.2010
    valFracRegResp vta_pedido_vta_det.val_frac_local%type;
                  -- pbl_respaldo_stk.val_frac_local%TYPE; --ASOSA, 13.07.2010
    cantidad lgt_prod_local.stk_fisico%TYPE; --ASOSA, 13.07.2010
 BEGIN

          IF cAccion_in = 'N' THEN -- Se anula el pedido pedido cobrado anulado
            IF cIndQuitaRespaldo_in = 'S' THEN --quita respaldo de stock si tiene regalo
              FOR v_rCurProd1 IN curProdRegaloGeneral
              LOOP

               FOR v_rCurEncartes IN curProdEncartes
                LOOP
                      --INTO cCodEncarte,cCodRegalo
                      IF(v_rCurEncartes.COD_PROD_REGALO =v_rCurProd1.COD_PROD) THEN
                        /*
                        SELECT a.stk_fisico, a.stk_comprometido, a.val_frac_local --/ASOSA, 13.07.2010
                        INTO stkFis, stkComp, valFracLocal
                        FROM lgt_prod_local a
                        WHERE a.cod_grupo_cia=cCodGrupoCia_in
                        AND a.cod_local=cCodLocal_in
                        AND a.cod_prod=v_rCurProd1.Cod_Prod FOR UPDATE;

                        SELECT b.cant_mov, b.val_frac_local --/ASOSA, 13.07.2010
                        INTO cantRegResp, valFracRegResp
                        FROM pbl_respaldo_stk b
                        WHERE b.sec_respaldo_stk=v_rCurProd1.Sec_Respaldo_Stk;

                        cantidad := (valFracLocal/valFracRegResp)*cantRegResp; --ASOSA, 13.07.2010

                        UPDATE LGT_PROD_LOCAL
                        SET    USU_MOD_PROD_LOCAL = cUsuMod_in,
                        FEC_MOD_PROD_LOCAL = SYSDATE,
                        --STK_COMPROMETIDO = STK_COMPROMETIDO - v_rCurProd1.CANT_FRAC_LOCAL
                        STK_COMPROMETIDO=STK_COMPROMETIDO - cantidad --ASOSA, 13.07.2010
                        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                        AND   COD_LOCAL     = cCodLocal_in
                        AND   COD_PROD      = v_rCurProd1.COD_PROD;
                        */
                        UPDATE VTA_PEDIDO_VTA_DET
                        SET    USU_MOD_PED_VTA_DET = cUsuMod_in,
                               FEC_MOD_PED_VTA_DET = SYSDATE,
                               --EST_PED_VTA_DET = 'N'
                               EST_PED_VTA_DET = INDICADOR_NO --ASOSA, 13.07.2010
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL     = cCodLocal_in
                        AND    NUM_PED_VTA   = cNumPed_in
                        AND    SEC_PED_VTA_DET = v_rCurProd1.SEC_PED_VTA_DET;

                        /*UPDATE PBL_RESPALDO_STK
                        SET    CANT_MOV = CANT_MOV -  v_rCurProd1.CANT_ATENDIDA
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND 	 COD_PROD = v_rCurProd1.COD_PROD
                        AND	   NUM_PED_VTA = cNumPed_in --al regresar a resumen se blanquea campo
                        AND    CANT_MOV >= v_rCurProd1.CANT_ATENDIDA
                        AND    MODULO = 'V'
                        AND    ROWNUM = 1;

                        DELETE PBL_RESPALDO_STK
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND 	 COD_LOCAL = cCodLocal_in
                        AND	   NUM_PED_VTA = cNumPed_in
                        AND    COD_PROD=v_rCurProd1.COD_PROD
                        AND    CANT_MOV = 0
                        AND    MODULO = 'V';*/
                        /*
                        DELETE FROM pbl_respaldo_stk b --/ASOSA, 13.07.2010
                        WHERE b.sec_respaldo_stk=v_rCurProd1.Sec_Respaldo_Stk;
                        */
                       -- COMMIT;
                      END IF;
                 END
                LOOP;

              END
              LOOP;

            END IF;
          END IF;
 END;

/***********************************************************************************************************************/

PROCEDURE CAJ_P_ANULA_PED_SIN_RESPALDO(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        vIdUsu_in       IN VARCHAR2,
                                        cModulo_in      IN CHAR DEFAULT 'V')
  AS
    mesg_body VARCHAR2(4000);
    v_cIndProdVirtual CHAR(1);
    v_cIndDelivAutomatico VTA_PEDIDO_VTA_CAB.IND_DELIV_AUTOMATICO%TYPE;
    CURSOR curProd IS
           SELECT DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.CANT_FRAC_LOCAL,
                  DET.SEC_PED_VTA_DET,
                  DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR,
                  DET.Sec_Respaldo_Stk --ASOSA, 13.07.2010
           FROM   VTA_PEDIDO_VTA_DET DET,
                  LGT_PROD_VIRTUAL VIR
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+)
           ORDER BY DET.CANT_ATENDIDA DESC ;
    v_rCurProd curProd%ROWTYPE;

    CURSOR curProdRegalo IS
           SELECT DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.CANT_FRAC_LOCAL,
                  DET.SEC_PED_VTA_DET,
                  DET.Sec_Respaldo_Stk --ASOSA, 13.07.2010
           FROM   VTA_PEDIDO_VTA_DET DET,
                  CA_CANJ_CLI_PED  CANJ
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = CANJ.COD_GRUPO_CIA
           AND    DET.COD_LOCAL = CANJ.COD_LOCAL
           AND    DET.NUM_PED_VTA = CANJ.NUM_PED_VTA
           AND    DET.SEC_PED_VTA_DET = CANJ.SEC_PED_VTA
           ORDER BY DET.CANT_ATENDIDA DESC;
           /*
           CURSOR curListaProdRegaloAutomatico IS
		   				  SELECT COD_PROD,
	                     trim(to_char(CANT_MOV,'999999')) CANT_MOVx,
								       trim(to_char(VAL_FRAC_LOCAL,'9999')) VAL_FRAC_LOCALx,
                       trim(to_char(SEC_RESPALDO_STK,'9999999999')) SEC_RESPALDO_STKx --ASOSA, 13.07.2010
			          FROM 	 PBL_RESPALDO_STK
			          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
					 	    AND 	 COD_LOCAL     = cCodLocal_in
                AND    NUM_PED_VTA   = cNumPedVta_in
                AND    IND_REGALO    = 'A';*/

    v_cEstPedido  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
    v_cIndAnulado VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;

    stkFis lgt_prod_local.stk_fisico%TYPE; --ASOSA, 13.07.2010
    stkComp lgt_prod_local.stk_fisico%TYPE; --ASOSA, 13.07.2010
    valFracLocal lgt_prod_local.val_frac_local%TYPE; --ASOSA, 13.07.2010
    cantRegResp vta_pedido_vta_det.cant_atendida%TYPE; --ASOSA, 13.07.2010
    valFracRegResp vta_pedido_vta_det.val_frac_local%TYPE; --ASOSA, 13.07.2010
    cantidad lgt_prod_local.stk_fisico%TYPE; --ASOSA, 13.07.2010
    ind VARCHAR2(200); --ASOSA, 13.07.2010

    cantidadx VARCHAR2(200);
    fraccionx VARCHAR2(200);
    secRespx VARCHAR2(10);

  BEGIN

       SELECT EST_PED_VTA,
              IND_PEDIDO_ANUL
       INTO   v_cEstPedido,
              v_cIndAnulado
       FROM   VTA_PEDIDO_VTA_CAB
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    NUM_PED_VTA = cNumPedVta_in  FOR UPDATE;

       if v_cEstPedido = 'N' THEN
             RAISE_APPLICATION_ERROR(-20002,'El Pedido esta anulado.');
          elsif v_cEstPedido = 'C' THEN
                RAISE_APPLICATION_ERROR(-20003,'El Pedido fue cobrado. ¡No puede Anular este Pedido!');
       END IF;

       IF v_cEstPedido <> 'P' THEN
          RAISE_APPLICATION_ERROR(-20001,'El Pedido no esta pendiente. ¡No puede Anular este Pedido!');
       END IF;



       UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = vIdUsu_in,  FEC_MOD_PED_VTA_CAB = SYSDATE,
    	        EST_PED_VTA = EST_PED_ANULADO,
              IND_PEDIDO_ANUL = INDICADOR_SI
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL     = cCodLocal_in
       AND    NUM_PED_VTA   = cNumPedVta_in;


       FOR v_rCurProd IN curProd
       LOOP
           v_cIndProdVirtual := v_rCurProd.IND_PROD_VIR;
           IF v_cIndProdVirtual = INDICADOR_SI THEN
               UPDATE VTA_PEDIDO_VTA_DET  SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
    	  		          EST_PED_VTA_DET = 'N'
               WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
               AND    COD_LOCAL     = cCodLocal_in
               AND    NUM_PED_VTA   = cNumPedVta_in
               AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;
           ELSE
               UPDATE VTA_PEDIDO_VTA_DET  SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
    	  		          EST_PED_VTA_DET = 'N'
               WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
               AND    COD_LOCAL     = cCodLocal_in
               AND    NUM_PED_VTA   = cNumPedVta_in
               AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;
           END IF;
      END LOOP;
            ----------------------------------.
            --SE AGREGO PARA CAMPAÑAS ACUMULA TU COMPRA Y GANA
             FOR v_rCurProd IN curProdRegalo
             LOOP

             SELECT a.stk_fisico, /*a.stk_comprometido*/0, a.val_frac_local --/ASOSA, 13.07.2010
             INTO stkFis, stkComp, valFracLocal
             FROM lgt_prod_local a
             WHERE a.cod_grupo_cia=cCodGrupoCia_in
             AND a.cod_local=cCodLocal_in
             AND a.cod_prod=v_rCurProd.Cod_Prod FOR UPDATE;
             /*
             SELECT b.cant_mov, b.val_frac_local --/ASOSA, 13.07.2010
             INTO cantRegResp, valFracRegResp
             FROM pbl_respaldo_stk b
             WHERE b.sec_respaldo_stk=v_rCurProd.Sec_Respaldo_Stk;

             cantidad := (valFracLocal/valFracRegResp)*v_rCurProd.Cant_Atendida; --ASOSA, 13.07.2010

             UPDATE LGT_PROD_LOCAL
             SET    USU_MOD_PROD_LOCAL = vIdUsu_in,
                    FEC_MOD_PROD_LOCAL = SYSDATE,
                    --STK_COMPROMETIDO = STK_COMPROMETIDO - v_rCurProd.CANT_FRAC_LOCAL
                    STK_COMPROMETIDO = STK_COMPROMETIDO - cantidad --ASOSA, 13.07.2010
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND   COD_LOCAL     = cCodLocal_in
             AND   COD_PROD      = v_rCurProd.COD_PROD;
             */
             UPDATE VTA_PEDIDO_VTA_DET
             SET    USU_MOD_PED_VTA_DET = vIdUsu_in,
                    FEC_MOD_PED_VTA_DET = SYSDATE,
  	  		          EST_PED_VTA_DET = 'N'
             WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
             AND    COD_LOCAL     = cCodLocal_in
             AND    NUM_PED_VTA   = cNumPedVta_in
             AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;

            /*UPDATE PBL_RESPALDO_STK                            antes
            SET    CANT_MOV = CANT_MOV -  v_rCurProd.CANT_ATENDIDA
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      	    AND 	 COD_PROD = v_rCurProd.COD_PROD
      	    AND	   NUM_PED_VTA = cNumPedVta_in
            AND    CANT_MOV >= v_rCurProd.CANT_ATENDIDA
            AND    MODULO = 'V'
            AND    ROWNUM = 1;

            DELETE PBL_RESPALDO_STK_AUX
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      	    AND 	 COD_PROD      = v_rCurProd.COD_PROD
            AND    COD_LOCAL     = cCodLocal_in
      	    AND	   NUM_PED_VTA   = cNumPedVta_in
            AND    IND_REGALO = 'S';

            DELETE PBL_RESPALDO_STK
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      	    AND 	 COD_PROD      = v_rCurProd.COD_PROD
            AND    COD_LOCAL     = cCodLocal_in
      	    AND	   NUM_PED_VTA   = cNumPedVta_in
            AND    IND_REGALO = 'S';*/
            /*
            DELETE FROM pbl_respaldo_stk b --/ASOSA, 13.07.2010
            WHERE b.sec_respaldo_stk=v_rCurProd.Sec_Respaldo_Stk;
            */
            END LOOP;
          -------------------------
          --Inicio de añadir
          --Añadido para Eliminar los productos automaticos
          --dubilluz 18.02.2009

          --FOR respaldoStk_rec IN curListaProdRegaloAutomatico LOOP
            /*UPDATE LGT_PROD_LOCAL l                         antes
               SET FEC_MOD_PROD_LOCAL = SYSDATE, USU_MOD_PROD_LOCAL = vIdUsu_in,
                   STK_COMPROMETIDO = NVL(STK_COMPROMETIDO,0) - respaldoStk_rec.CANT_MOV/NVL(respaldoStk_rec.VAL_FRAC_LOCAL,l.val_frac_local)*l.val_frac_local --MODIFICACION JLUNA 20090216
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       	     AND 	 COD_LOCAL = cCodLocal_in
             AND 	 COD_PROD  = respaldoStk_rec.COD_PROD;

            DELETE PBL_RESPALDO_STK
      	    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       	    AND    COD_LOCAL     = cCodLocal_in
            AND    NUM_PED_VTA   = cNumPedVta_in
            AND    IND_REGALO    = 'A'
            AND 	 COD_PROD      = respaldoStk_rec.COD_PROD
            AND    VAL_FRAC_LOCAL = respaldoStk_rec.VAL_FRAC_LOCAL
            AND    CANT_MOV = respaldoStk_rec.CANT_MOV
            AND    ROWNUM = 1;*/
            /*
            dbms_output.put_line('cCodGrupoCia_in: '||cCodGrupoCia_in);
            dbms_output.put_line('cCodLocal_in: '||cCodLocal_in);
            dbms_output.put_line('respaldoStk_rec.Cod_Prod: '||respaldoStk_rec.Cod_Prod);
            dbms_output.put_line('respaldoStk_rec.Cant_Mov_02: '||TRIM(respaldoStk_rec.Cant_Movx));
            dbms_output.put_line('respaldoStk_rec.Val_Frac_Local_02: '||TRIM(respaldoStk_rec.Val_Frac_Localx));
            dbms_output.put_line('respaldoStk_rec.Sec_Respaldo_Stk_02: '||TRIM(respaldoStk_rec.Sec_Respaldo_Stkx));
            */
                   /*cantidadx := respaldoStk_rec.Cant_Mov;
                   fraccionx := respaldoStk_rec.Val_Frac_Local;
                   secRespx := respaldoStk_rec.Sec_Respaldo_Stk;*/
                   /*
                   ind:=PVTA_F_INS_DEL_UPD_STK_RES(cCodGrupoCia_in,      --/ASOSA, 13.07.2010
                                                cCodLocal_in,
                                                respaldoStk_rec.Cod_Prod,
                                                '0',--trim(respaldoStk_rec.Cant_Movx),
                                                respaldoStk_rec.Val_Frac_Localx,--trim(respaldoStk_rec.Val_Frac_Localx),
                                                respaldoStk_rec.Sec_Respaldo_Stkx,--trim(respaldoStk_rec.Sec_Respaldo_Stkx),
                                                'V',
                                                vIdUsu_in);
             dbms_output.put_line('fin');*/
            /*ind:=PVTA_F_INS_DEL_UPD_STK_RES(cCodGrupoCia_in,      --/ASOSA, 13.07.2010
                                                cCodLocal_in,
                                                trim(respaldoStk_rec.Cod_Prod),
                                                '0',--trim(respaldoStk_rec.Cant_Movx),
                                                trim(respaldoStk_rec.Val_Frac_Localx),
                                                trim(respaldoStk_rec.Sec_Respaldo_Stkx),
                                                'V',
                                                vIdUsu_in);*/

              /*FUNCTION PVTA_F_INS_DEL_UPD_STK_RES(cCodCia_in IN CHAR,
                                    cCodLocal_in IN CHAR,
                                    cCodProd_in IN CHAR,
                                    vCantMov_in IN VARCHAR2,
                                    vFracVta_in IN VARCHAR2,
                                    cSecResp_in IN CHAR DEFAULT '0',
                                    vModulo_in IN CHAR,
                                    vIdUsu_in IN VARCHAR2)*/
          /*END LOOP;

          -------------------------
          UPDATE PBL_RESPALDO_STK C
          SET    NUM_PED_VTA = NULL
    	    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
     	    AND 	 COD_LOCAL = cCodLocal_in
    	    AND	   NUM_PED_VTA = cNumPedVta_in
          AND    MODULO = 'V';
          */
      update vta_camp_pedido_cupon p
      set    p.estado = 'N'
      where  p.cod_grupo_cia = cCodGrupoCia_in
      and    p.cod_local = cCodLocal_in
      and    p.num_ped_vta = cNumPedVta_in
      and    p.estado = 'S';



  /*EXCEPTION
    WHEN OTHERS THEN
         mesg_body := 'ERROR AL ANULAR PEDIDO PENDIENTE No ' || cNumPedVta_in || '. ' || SQLERRM;
         FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                    cCodLocal_in,
                                    'asosa@mifarma.com.pe, operador',
                                    'ERROR AL ANULAR PEDIDO PENDIENTE',
                                    'ERROR',
                                    mesg_body,
                                    '');
         RAISE;*/
  END;

/***********************************************************************************************************************/

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
                                         secRespaldo      IN CHAR,
                                         --INI ASOSA - 22/08/2014
                                         cantExceso in number DEFAULT 0,    --como esto es devolucion el exceso es lo maximo que esperaba devolver.
                                         cantUsadaExceso in number DEFAULT 0,--es la cantidad que use del exceso.
                                         numeroOc in varchar2 default null,
                                         secuencialDetalle in varchar2 default NULL
                                         --FIN ASOSA - 22/08/2014
                                         )
    AS
      v_dFechaNota DATE;
      nSec LGT_NOTA_ES_DET.SEC_DET_NOTA_ES%TYPE;
    	v_nValFrac LGT_NOTA_ES_DET.VAL_FRAC%TYPE;
    	v_vDescUnidVta LGT_NOTA_ES_DET.DESC_UNID_VTA%TYPE;
          v_nStkFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
      v_cInd CHAR(1);

    stkFis lgt_prod_local.stk_fisico%TYPE; --ASOSA, 15.07.2010
    stkComp lgt_prod_local.stk_fisico%TYPE; --ASOSA, 15.07.2010
    valFracLocal lgt_prod_local.val_frac_local%TYPE; --ASOSA, 15.07.2010

    BEGIN
      SELECT FEC_NOTA_ES_CAB INTO v_dFechaNota
      FROM LGT_NOTA_ES_CAB
      WHERE COD_GRUPO_CIA =  cCodGrupoCia_in
            AND COD_LOCAL =  cCodLocal_in
            AND NUM_NOTA_ES = cNumNota_in;

      SELECT COUNT(SEC_DET_NOTA_ES)+1 INTO nSec
    	FROM LGT_NOTA_ES_DET
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND NUM_NOTA_ES = cNumNota_in;

      SELECT a.stk_fisico, /*a.stk_comprometido*/0, a.val_frac_local --/ASOSA, 15.07.2010
      INTO stkFis, stkComp, valFracLocal
      FROM lgt_prod_local a
      WHERE a.cod_grupo_cia=cCodGrupoCia_in
      AND a.cod_local=cCodLocal_in
      AND a.cod_prod=cCodProd_in FOR UPDATE;

      SELECT VAL_FRAC_LOCAL,NVL(TRIM(UNID_VTA),(SELECT DESC_UNID_PRESENT
                                                FROM LGT_PROD
                                                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                                AND COD_PROD = cCodProd_in )),STK_FISICO INTO
             v_nValFrac,v_vDescUnidVta,v_nStkFisico
    	FROM LGT_PROD_LOCAL
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND COD_PROD = cCodProd_in;

      IF v_nValFrac <> vValFrac_in THEN
         RAISE_APPLICATION_ERROR(-20083,'LA FRACCION HA CAMBIADO PARA EL PRODUCTO:'||cCodProd_in||'. VUELVA A INGRESAR EL

  PRODUCTO.');
      END IF;
      --AGREGADO 07/09/2006 ERIOS
      --VALIDA QUE LA FRACCION DEL LOCAL DESTINO, PERMITA ACEPTAR LA TRANSFERENCIA
      --SI HAY CONEXION CON MATRIZ
      IF vTipDestino_in = g_cTipoOrigenLocal THEN
   /*     EXECUTE IMMEDIATE 'BEGIN  PTOVENTA_TRANSF.GET_FRACCION_LOCAL@XE_000(:1,:2,:3,:4,:5,:6); END;'
                          USING cCodGrupoCia_in,cCodDestino_in,cCodProd_in,nCantMov_in,v_nValFrac,IN OUT v_cInd;
        --v_cInd := PTOVENTA_TRANSF.GET_FRACCION_LOCAL(cCodGrupoCia_in,cCodDestino_in,cCodProd_in,nCantMov_in,v_nValFrac);
   */
        IF vIndFrac_in != 'V' THEN
          RAISE_APPLICATION_ERROR(-20081,'ALGUNOS PRODUCTOS NO PUEDEN SER TRANSFERIDOS, DEBIDO A LA FRACCION ACTUAL DEL LOCAL

  DESTINO.');
        END IF;
      END IF;

    	INSERT INTO LGT_NOTA_ES_DET(COD_GRUPO_CIA,COD_LOCAl,NUM_NOTA_ES,SEC_DET_NOTA_ES,

  COD_PROD,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,FEC_NOTA_ES_DET,FEC_VCTO_PROD,NUM_LOTE_PROD, USU_CREA_NOTA_ES_DET,

  VAL_FRAC,DESC_UNID_VTA,Sec_Respaldo_Stk,--ASOSA, 15.07.2010, secRespaldo
  CANT_EXCESO, CANT_USADA_EXCESO                   --ASOSA - 22/08/2014
  ) 
    	VALUES(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,nSec,

  cCodProd_in,nValPrecUnit_in,nValPrecTotal_in,nCantMov_in,v_dFechaNota,TO_DATE(vFecVecProd_in,'dd/MM/yyyy'),vNumLote_in,

  vUsu_in, v_nValFrac,v_vDescUnidVta,secRespaldo,--ASOSA, 15.07.2010, secRespaldo
  cantExceso, cantUsadaExceso                             --ASOSA - 22/08/2014
  );

      --INSERTAR KARDEX
      Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in ,
                                      cCodLocal_in,
                                      cCodProd_in,
                                      cCodMotKardex_in,
                                      cTipDocKardex_in,
                                      cNumNota_in,
                                      v_nStkFisico,
                                      nCantMov_in*-1,
                                      v_nValFrac,
                                      v_vDescUnidVta,
                                      vUsu_in,
                                      COD_NUMERA_SEC_KARDEX,'');

      --DESCONTAR STOCK FISICO,STOCK COMPROMETIDO
      UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = vUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
                STK_FISICO = STK_FISICO - nCantMov_in--,
                --STK_COMPROMETIDO = STK_COMPROMETIDO - nCantMov_in
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND COD_PROD = cCodProd_in;
      --BORRAR REGISTRO RESPALDO

      /*DELETE PBL_RESPALDO_STK b      --antes, ASOSA 15.07.2010
  	     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
   	     AND 	 COD_LOCAL = cCodLocal_in
  	     AND 	 COD_PROD = cCodProd_in
  	     AND CANT_MOV = nCantMov_in
               AND MODULO = 'T'
         AND   ROWNUM = 1;*/
         /*
       DELETE FROM pbl_respaldo_stk b --/ASOSA, 15.07.2010
       WHERE b.sec_respaldo_stk=secRespaldo;*/
       
       UPDATE LGT_OC_DET AA                   --PARA QUE SI QUIERO DEVOLVER MAS SOBRE LA MISMA OC NO SALGA DE NUEVO EL DETALLE QUE DEVOLVI
       SET AA.FECHA_DEV_EXCESO = SYSDATE
       WHERE AA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND AA.COD_LOCAL = cCodLocal_in
       AND AA.COD_OC = numeroOc
       AND AA.SEC_DET_NOTA_ES = secuencialDetalle;
       
       UPDATE LGT_OC_CAB OCC                 --PARA MARCAR QUE ESA OC YA FUE REGULARIZADA (PARA BIEN O PARA MAL)
       SET OCC.FECHA_DEV_EXCESO = SYSDATE
       WHERE OCC.COD_GRUPO_CIA = cCodGrupoCia_in
       AND OCC.COD_LOCAL = cCodLocal_in
       AND OCC.COD_OC = numeroOc;

    END;

/***********************************************************************************************************************/

  PROCEDURE INV_ANULA_TRANSFERENCIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,
                                    cCodMotKardex_in IN CHAR, cTipDocKardex_in IN CHAR,
                                    vIdUsu_in IN VARCHAR2)
  AS
    CURSOR curDet IS
    SELECT SEC_DET_NOTA_ES,COD_PROD,CANT_MOV,SEC_GUIA_REM,VAL_FRAC
    FROM LGT_NOTA_ES_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;
--
    v_nFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nComprometido LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nValFrac LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    v_vDescUnidVta LGT_PROD_LOCAL.UNID_VTA%TYPE;
--
    det curDet%ROWTYPE;

    v_nCant LGT_NOTA_ES_DET.CANT_MOV%TYPE;
    v_cProdCong LGT_PROD_LOCAL.IND_PROD_CONG%TYPE;

    stkFis lgt_prod_local.stk_fisico%TYPE; --ASOSA, 15.07.2010
    stkComp lgt_prod_local.stk_fisico%TYPE; --ASOSA, 15.07.2010
    valFracLocal lgt_prod_local.val_frac_local%TYPE; --ASOSA, 15.07.2010
    unidVta lgt_prod_local.unid_vta%TYPE; --ASOSA, 15.07.2010
    indPrdCong lgt_prod_local.ind_prod_cong%TYPE; --ASOSA, 15.07.2010
  BEGIN
    UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = vIdUsu_in,FEC_MOD_NOTA_ES_CAB = SYSDATE,
          EST_NOTA_ES_CAB = 'N'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;

    FOR det IN curDet
    LOOP
        SELECT l.stk_fisico, /*l.stk_comprometido*/0, l.val_frac_local, l.unid_vta, l.ind_prod_cong --/ASOSA, 15.07.2010
        INTO stkFis, stkComp, valFracLocal, unidVta, indPrdCong
        FROM lgt_prod_local l
        WHERE l.cod_grupo_cia=cCodGrupoCia_in
        AND l.cod_local=cCodLocal_in
        AND l.cod_prod=det.COD_PROD FOR UPDATE;

      SELECT STK_FISICO,STK_FISICO-/*STK_COMPROMETIDO*/0,VAL_FRAC_LOCAL,UNID_VTA,L.IND_PROD_CONG
        INTO v_nFisico,v_nComprometido,v_nValFrac, v_vDescUnidVta, v_cProdCong
      FROM LGT_PROD_LOCAL L
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = det.COD_PROD;
      --VERIFICA PRODUCTO CONGELADO
      IF v_cProdCong = 'S' THEN
        RAISE_APPLICATION_ERROR(-20003,'ERROR AL ANULAR. PROD:'||det.COD_PROD||' CONGELADO');
      END IF;
      --VERIFICA FRACCION ACTUAL
      IF MOD(det.CANT_MOV*v_nValFrac,det.VAL_FRAC) = 0 THEN
        v_nCant := ((det.CANT_MOV*v_nValFrac)/det.VAL_FRAC);
      ELSE
        RAISE_APPLICATION_ERROR(-20002,'Error al anular. Prod:'||det.COD_PROD||',Cant:'||det.CANT_MOV||' ,Frac:'||det.VAL_FRAC||' ,Frac. Act:'||v_nValFrac);
      END IF;

      UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = vIdUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
            STK_FISICO = STK_FISICO + v_nCant
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = det.COD_PROD;

      UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET = vIdUsu_in,FEC_MOD_NOTA_ES_DET = SYSDATE,
            EST_NOTA_ES_DET = 'N'
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in
          AND SEC_DET_NOTA_ES = det.SEC_DET_NOTA_ES;

      --KARDEX
      PTOVENTA_INV.INV_GRABAR_KARDEX(cCodGrupoCia_in ,
                          cCodLocal_in,
                          det.COD_PROD,
                          cCodMotKardex_in,
                          cTipDocKardex_in,
                          cNumNota_in,
                          v_nFisico,
                          v_nCant,
                          v_nValFrac,
                          v_vDescUnidVta,
                          vIdUsu_in,
                          COD_NUMERA_SEC_KARDEX,'');

        --AGREGADO 13/06/2006 ERIOS
        INV_ACT_KARDEX_GUIA_REC(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,det.SEC_GUIA_REM,
                                det.COD_PROD,
                                vIdUsu_in);
    END LOOP;

    --JCHAVEZ 29122009 inactivando la transferencia por recepcion ciega
    UPDATE LGT_RECEP_PROD_TRANSF
    SET   EST_TRANSF = 'I'
    WHERE COD_GRUPO_CIA   = cCodGrupoCia_in
    AND   COD_LOCAL       = cCodLocal_in
    AND   NUM_NOTA_ES     = cNumNota_in;
  END;

/***********************************************************************************************************************/

  PROCEDURE INV_ACT_KARDEX_GUIA_REC(cGrupoCia_in   IN CHAR,
                                    cCodLocal_in 	 IN CHAR,
                                    cNumNota_in  	 IN CHAR,
                                    cSecDetNota_in IN CHAR,
                                    cCodProd_in 	 IN CHAR,
                                    cIdUsu_in 		 IN CHAR,
                                    cTipOrigen_in IN CHAR DEFAULT NULL)
  IS
    v_cNumGuiaRem LGT_GUIA_REM.Num_Guia_Rem%TYPE;
    v_cTipDoc LGT_NOTA_ES_CAB.TIP_DOC%TYPE;
    v_nCant LGT_KARDEX.CANT_MOV_PROD%TYPE;
  BEGIN
    --MODIFICADO 23/06/2006 ERIOS
    IF (cTipOrigen_in = g_cTipoOrigenLocal) OR (cTipOrigen_in = g_cTipoOrigenMatriz) THEN
      SELECT g_cTipCompGuia,A.NUM_GUIA_REM,SUM(D.CANT_MOV)
        INTO   v_cTipDoc,v_cNumGuiaRem,v_nCant
      FROM   LGT_GUIA_REM A, LGT_NOTA_ES_DET D
      WHERE  A.COD_GRUPO_CIA = cGrupoCia_in
            AND A.COD_LOCAL = cCodLocal_in
            AND A.NUM_NOTA_ES = cNumNota_in
            AND A.SEC_GUIA_REM = cSecDetNota_in
            AND D.COD_PROD = cCodProd_in
            AND A.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND A.COD_LOCAL = D.COD_LOCAL
            AND A.NUM_NOTA_ES = D.NUM_NOTA_ES
             AND A.SEC_GUIA_REM = D.SEC_GUIA_REM
       GROUP BY  g_cTipCompGuia,A.NUM_GUIA_REM
             ;
    ELSIF (cTipOrigen_in = g_cTipoOrigenProveedor) OR (cTipOrigen_in = g_cTipoOrigenCompetencia) THEN
      SELECT NVL(C.TIP_DOC,g_cTipCompGuia),C.NUM_DOC,SUM(D.CANT_MOV) --JCHAVEZ agregue el SUM 29122009
        INTO   v_cTipDoc,v_cNumGuiaRem,v_nCant
      FROM   LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
      WHERE  C.COD_GRUPO_CIA = cGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.NUM_NOTA_ES = cNumNota_in
            AND D.COD_PROD = cCodProd_in
            AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND C.COD_LOCAL = D.COD_LOCAL
            AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
            GROUP BY  NVL(C.TIP_DOC,g_cTipCompGuia),C.NUM_DOC;
    ELSIF cTipOrigen_in IS NULL THEN
      --SELECT NVL(C.TIP_DOC,g_cTipCompGuia),A.NUM_GUIA_REM
      /*SELECT g_cTipCompGuia,A.NUM_GUIA_REM
        INTO   v_cTipDoc,v_cNumGuiaRem
      FROM   LGT_GUIA_REM A, LGT_NOTA_ES_CAB C
      WHERE  A.COD_GRUPO_CIA = cGrupoCia_in
            AND A.COD_LOCAL = cCodLocal_in
            AND A.NUM_NOTA_ES = cNumNota_in
            AND A.SEC_GUIA_REM = cSecDetNota_in
            AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
            AND A.COD_LOCAL = C.COD_LOCAL
            AND A.NUM_NOTA_ES = C.NUM_NOTA_ES;*/
      SELECT g_cTipCompGuia,A.NUM_GUIA_REM,SUM(D.CANT_MOV)--JCHAVEZ agregue el SUM 29122009
       INTO   v_cTipDoc,v_cNumGuiaRem,v_nCant
      FROM   LGT_GUIA_REM A, LGT_NOTA_ES_DET D
      WHERE  A.COD_GRUPO_CIA = cGrupoCia_in
            AND A.COD_LOCAL = cCodLocal_in
            AND A.NUM_NOTA_ES = cNumNota_in
            AND A.SEC_GUIA_REM = cSecDetNota_in
            AND D.COD_PROD = cCodProd_in
            AND A.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND A.COD_LOCAL = D.COD_LOCAL
            AND A.NUM_NOTA_ES = D.NUM_NOTA_ES
             AND A.SEC_GUIA_REM = D.SEC_GUIA_REM
             GROUP BY  g_cTipCompGuia,A.NUM_GUIA_REM;
    END IF;
    DBMS_OUTPUT.PUT_LINE('ACTUALIZA:'||v_cNumGuiaRem);
    UPDATE LGT_KARDEX K SET K.USU_MOD_KARDEX = cIdUsu_in, K.FEC_MOD_KARDEX = SYSDATE,
          K.TIP_COMP_PAGO = v_cTipDoc,
          K.NUM_COMP_PAGO = v_cNumGuiaRem
     WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND   COD_LOCAL = cCodLocal_in
          AND   COD_PROD = cCodProd_in
	  AND	 NUM_TIP_DOC = cNumNota_in
          --AND CANT_MOV_PROD = v_nCant
          AND TIP_COMP_PAGO IS NULL
          AND NUM_COMP_PAGO IS NULL
          AND ROWNUM = 1;

  END;

/*************************************************************************************************************************/

PROCEDURE TRANSF_P_AGREGA_DETALLE(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in     IN CHAR,
                                  cNumNota_in      IN CHAR,
                                  cCodProd_in      IN CHAR,
                                  nValPrecUnit_in  IN NUMBER,
                                  nValPrecTotal_in IN NUMBER,
                                  nCantMov_in      IN NUMBER,
                                  vFecVecProd_in   IN VARCHAR2 DEFAULT NULL, --ASOSA 09.02.2010
                                  vNumLote_in      IN VARCHAR2 DEFAULT NULL, --ASOSA 09.02.2010
                                  cCodMotKardex_in IN CHAR,
                                  cTipDocKardex_in IN CHAR,
                                  vValFrac_in      IN NUMBER,
                                  vUsu_in          IN VARCHAR2,
                                  vTipDestino_in   IN CHAR,
                                  cCodDestino_in   IN CHAR,
                                  vIndFrac_in      IN CHAR DEFAULT 'N',
                                  cSecResp_in      IN VARCHAR2 DEFAULT '0')
  AS
  fecha DATE; --ASOSA 09.02.2010
    v_dFechaNota DATE;
    nSec LGT_NOTA_ES_DET.SEC_DET_NOTA_ES%TYPE;
  	v_nValFrac LGT_NOTA_ES_DET.VAL_FRAC%TYPE;
  	v_vDescUnidVta LGT_NOTA_ES_DET.DESC_UNID_VTA%TYPE;
        v_nStkFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_cInd CHAR(1);

    v_cEmailErrorPtoVenta PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='joliva';
    v_vDescLocalOrigen VARCHAR2(120);
    v_vDescLocalDestino VARCHAR2(120);

    stkFis lgt_prod_local.stk_fisico%TYPE; --INI - ASOSA, 20.07.2010
    stkComp lgt_prod_local.stk_fisico%TYPE;
    valFracLocal lgt_prod_local.val_frac_local%TYPE;
    unidVta lgt_prod_local.unid_vta%TYPE;
    ind VARCHAR2(200); --FIN - ASOSA, 20.07.2010

    --INI ASOSA, 01.09.2010
    cantMovDEL lgt_nota_es_det.cant_mov%TYPE;
    fracMovDEL lgt_nota_es_det.val_frac%TYPE;
    division NUMBER(9,2);
    stockAComprometer NUMBER(4);
    --FIN ASOSA, 01.09.2010
  BEGIN
      SELECT FEC_NOTA_ES_CAB INTO v_dFechaNota
      FROM LGT_NOTA_ES_CAB
      WHERE COD_GRUPO_CIA =  cCodGrupoCia_in
            AND COD_LOCAL =  cCodLocal_in
            AND NUM_NOTA_ES = cNumNota_in;

      SELECT COUNT(SEC_DET_NOTA_ES)+1 INTO nSec
    	FROM LGT_NOTA_ES_DET
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND NUM_NOTA_ES = cNumNota_in;

            SELECT a.stk_fisico, /*a.stk_comprometido*/0, a.val_frac_local, a.unid_vta --/ASOSA, 20.07.2010
            INTO stkFis, stkComp, valFracLocal, unidVta
            FROM lgt_prod_local a
            WHERE a.cod_grupo_cia=cCodGrupoCia_in
            AND a.cod_local=cCodLocal_in
            AND a.cod_prod=cCodProd_in FOR UPDATE;

      SELECT VAL_FRAC_LOCAL,NVL(TRIM(UNID_VTA),(SELECT DESC_UNID_PRESENT
                                                FROM LGT_PROD
                                                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                                AND COD_PROD = cCodProd_in )),STK_FISICO INTO

      v_nValFrac,v_vDescUnidVta,v_nStkFisico
    	FROM LGT_PROD_LOCAL
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND COD_PROD = cCodProd_in;

      IF v_nValFrac <> vValFrac_in THEN
         RAISE_APPLICATION_ERROR(-20083,'LA FRACCION HA CAMBIADO PARA EL PRODUCTO:'||cCodProd_in||'. VUELVA A INGRESAR EL

PRODUCTO.');
      END IF;
      --AGREGADO 07/09/2006 ERIOS
      --VALIDA QUE LA FRACCION DEL LOCAL DESTINO, PERMITA ACEPTAR LA TRANSFERENCIA
      --SI HAY CONEXION CON MATRIZ
      IF vTipDestino_in = g_cTipoOrigenLocal THEN
      /*  EXECUTE IMMEDIATE 'BEGIN  PTOVENTA_TRANSF.GET_FRACCION_LOCAL@XE_000(:1,:2,:3,:4,:5,:6); END;'
                          USING cCodGrupoCia_in,cCodDestino_in,cCodProd_in,nCantMov_in,v_nValFrac,IN OUT v_cInd;
        --v_cInd := PTOVENTA_TRANSF.GET_FRACCION_LOCAL(cCodGrupoCia_in,cCodDestino_in,cCodProd_in,nCantMov_in,v_nValFrac);
       */
        IF vIndFrac_in != 'V' THEN
          RAISE_APPLICATION_ERROR(-20081,'ALGUNOS PRODUCTOS NO PUEDEN SER TRANSFERIDOS, DEBIDO A LA FRACCION ACTUAL DEL LOCAL

DESTINO.');
        END IF;
      END IF;

IF vFecVecProd_in IS NULL THEN --INI ASOSA 09.02.2010
   fecha:=NULL;
ELSE
    fecha:=TO_DATE(vFecVecProd_in,'dd/MM/yyyy');
END IF;                      --FIN ASOSA 09.20.2010

    	INSERT INTO LGT_NOTA_ES_DET(COD_GRUPO_CIA,COD_LOCAl,NUM_NOTA_ES,SEC_DET_NOTA_ES,

COD_PROD,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,FEC_NOTA_ES_DET,FEC_VCTO_PROD,NUM_LOTE_PROD, USU_CREA_NOTA_ES_DET,
VAL_FRAC,DESC_UNID_VTA,
                      Sec_Respaldo_Stk) --ASOSA, 31.08.2010
    	VALUES(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,nSec,

cCodProd_in,nValPrecUnit_in,nValPrecTotal_in,nCantMov_in,v_dFechaNota,fecha/*TO_DATE(vFecVecProd_in,'dd/MM/yyyy')*/,vNumLote_in, --ASOSA 09.02.2010

vUsu_in, v_nValFrac,v_vDescUnidVta,
         cSecResp_in); --ASOSA, 31.08.2010

      --INSERTAR KARDEX
      PTOVENTA_TRANSF_DEL.TRANSF_P_GRABAR_KARDEX(cCodGrupoCia_in ,
                                      cCodLocal_in,
                                      cCodProd_in,
                                      cCodMotKardex_in,
                                      cTipDocKardex_in,
                                      cNumNota_in,
                                      v_nStkFisico,
                                      nCantMov_in*-1,
                                      v_nValFrac,
                                      v_vDescUnidVta,
                                      vUsu_in,
                                      COD_NUMERA_SEC_KARDEX,'');

      --DESCONTAR STOCK FISICO,STOCK COMPROMETIDO
      UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = vUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
                STK_FISICO = STK_FISICO - nCantMov_in--,
                --STK_COMPROMETIDO = STK_COMPROMETIDO - nCantMov_in
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND COD_PROD = cCodProd_in;
      --BORRAR REGISTRO RESPALDO
      /*
      DELETE PBL_RESPALDO_STK
  	     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
   	     AND 	 COD_LOCAL = cCodLocal_in
  	     AND 	 COD_PROD = cCodProd_in
  	     AND CANT_MOV = nCantMov_in
               AND MODULO = 'T'
         AND   ROWNUM = 1;
         */
          --INI ASOSA, 01.09.2010
        /*SELECT b.cant_mov, b.val_frac_local
        INTO cantMovDEL, fracMovDEL
        FROM pbl_respaldo_stk b
        WHERE b.sec_respaldo_stk=cSecResp_in;
        division:=valFracLocal/fracMovDEL;
        stockAComprometer:=division*cantMovDEL;
        UPDATE lgt_prod_local aa
        SET aa.stk_fisico=aa.stk_fisico - stockAComprometer
        WHERE aa.cod_grupo_cia=cCodGrupoCia_in
        AND aa.cod_local=cCodLocal_in
        AND aa.cod_prod=cCodProd_in;*/
        --FIN ASOSA, 01.09.2010
         /*ind:=PVTA_F_INS_DEL_UPD_STK_RES(cCodGrupoCia_in,      --/ASOSA, 13.07.2010
                                                cCodLocal_in,
                                                cCodProd_in,
                                                '0',
                                                vValFrac_in,
                                                cSecResp_in,
                                                'V',
                                                vUsu_in,
                                                '321');*/
          ind := 0;


  END;

/***********************************************************************************************************************/

PROCEDURE RECEP_P_OPERAC_ANEXAS_PROD(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumNota_in     IN CHAR,
                                       nSecGuia_in     IN NUMBER,
                                       cCodProd_in     IN CHAR,
                                       nCantMov_in     IN NUMBER,
                                       cIdUsu_in       IN CHAR,
                                       nTotalGuia_in   IN NUMBER DEFAULT NULL,
                                       nDiferencia_in  IN NUMBER DEFAULT NULL) AS
    v_nValFrac     LGT_NOTA_ES_DET.VAL_FRAC%TYPE;
    v_vDescUnidVta LGT_NOTA_ES_DET.DESC_UNID_VTA%TYPE;
    v_nStkFisico   LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nNumGuia     LGT_GUIA_REM.NUM_GUIA_REM%TYPE;

    stkFis lgt_prod_local.stk_fisico%TYPE; --INI - ASOSA, 21.07.2010
    stkComp lgt_prod_local.stk_fisico%TYPE;
    usuMod lgt_prod_local.usu_mod_prod_local%TYPE;
    fecMod lgt_prod_local.fec_mod_prod_local%TYPE; --FIN - ASOSA, 21.07.2010
  BEGIN
    SELECT DECODE(VAL_FRAC_LOCAL, NULL, 1, 0, 1, VAL_FRAC_LOCAL),
           UNID_VTA,
           STK_FISICO
      INTO v_nValFrac, v_vDescUnidVta, v_nStkFisico
      FROM LGT_PROD_LOCAL
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND COD_PROD = cCodProd_in;

       SELECT a.stk_fisico, /*a.stk_comprometido*/0, a.usu_mod_prod_local, a.fec_mod_prod_local --/ASOSA, 21.07.2010
       INTO stkFis, stkComp, usuMod, fecMod
       FROM lgt_prod_local a
       WHERE a.cod_grupo_cia=cCodGrupoCia_in
       AND a.cod_local=cCodLocal_in
       AND a.cod_prod=cCodProd_in FOR UPDATE;

    --ACTUALIZA PROD LOCAL
    UPDATE LGT_PROD_LOCAL
       SET USU_MOD_PROD_LOCAL = cIdUsu_in,
           FEC_MOD_PROD_LOCAL = SYSDATE,
           STK_FISICO         = STK_FISICO + nCantMov_in * v_nValFrac
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND COD_PROD = cCodProd_in;

    SELECT NUM_ENTREGA
      INTO v_nNumGuia
      FROM LGT_GUIA_REM
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in
       AND SEC_GUIA_REM = nSecGuia_in;
    --INSERTAR KARDEX
    IF (nTotalGuia_in IS NOT NULL) AND (nDiferencia_in IS NOT NULL) AND
       (nDiferencia_in <> 0) THEN
      PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_GRABAR_KARDEX(cCodGrupoCia_in,
                            cCodLocal_in,
                            cCodProd_in,
                            g_cMotKardexIngMatriz,
                            g_cTipDocKdxGuiaES,
                            cNumNota_in,
                            v_nStkFisico,
                            nTotalGuia_in * v_nValFrac,
                            v_nValFrac,
                            v_vDescUnidVta,
                            cIdUsu_in,
                            COD_NUMERA_SEC_KARDEX,
                            '',
                            g_cTipCompNumEntrega,
                            v_nNumGuia);
      PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_GRABAR_KARDEX(cCodGrupoCia_in,
                            cCodLocal_in,
                            cCodProd_in,
                            g_cTipoMotKardexAjusteGuia,
                            g_cTipDocKdxGuiaES,
                            cNumNota_in,
                            v_nStkFisico + (nTotalGuia_in * v_nValFrac),
                            (ABS(nDiferencia_in) * -1) * v_nValFrac,
                            v_nValFrac,
                            v_vDescUnidVta,
                            cIdUsu_in,
                            COD_NUMERA_SEC_KARDEX,
                            '',
                            g_cTipCompNumEntrega,
                            v_nNumGuia);
    ELSE
      PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_GRABAR_KARDEX(cCodGrupoCia_in,
                            cCodLocal_in,
                            cCodProd_in,
                            g_cMotKardexIngMatriz,
                            g_cTipDocKdxGuiaES,
                            cNumNota_in,
                            v_nStkFisico,
                            nCantMov_in * v_nValFrac,
                            v_nValFrac,
                            v_vDescUnidVta,
                            cIdUsu_in,
                            COD_NUMERA_SEC_KARDEX,
                            '',
                            g_cTipCompNumEntrega,
                            v_nNumGuia);
    END IF;
  END;

/***********************************************************************************************************************/

PROCEDURE RECUPERACION_RESPALDO_STK(cCodGrupoCia_in IN CHAR,
	                           		  cCodLocal_in    IN CHAR,
	                           		  cNumIpPc_in     IN CHAR,
									  vIdUsuario_in   IN VARCHAR2) IS
  /*	CURSOR curRespaldoStk IS
		   				  SELECT COD_PROD,
	                     CANT_MOV,
								       VAL_FRAC_LOCAL,
                       SEC_RESPALDO_STK
			          FROM 	 PBL_RESPALDO_STK
			          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
					 	    AND 	 COD_LOCAL = cCodLocal_in
						    AND 	 NUM_IP_PC = cNumIpPc_in
						    AND	   NUM_PED_VTA IS NULL;*/

   nStockComprometido number;
   nCalculado number;
   cantidad_restar number;
  BEGIN
    /*FOR respaldoStk_rec IN curRespaldoStk LOOP

    select l.stk_comprometido,respaldoStk_rec.CANT_MOV/NVL(respaldoStk_rec.VAL_FRAC_LOCAL,l.val_frac_local)*l.val_frac_local
    into   nStockComprometido,nCalculado
    from   lgt_prod_local l
    where  l.cod_grupo_cia = cCodGrupoCia_in
    and    l.cod_local = cCodLocal_in
    and    l.cod_prod  = respaldoStk_rec.COD_PROD;

    select case
             when nStockComprometido >= nCalculado then
              nCalculado
             else
              nStockComprometido
           end
    into cantidad_restar
    from dual;

       UPDATE LGT_PROD_LOCAL l
         SET FEC_MOD_PROD_LOCAL = SYSDATE, USU_MOD_PROD_LOCAL = vIdUsuario_in,
             STK_COMPROMETIDO = NVL(STK_COMPROMETIDO,0)
                                - cantidad_restar
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
 	     AND 	 COD_LOCAL = cCodLocal_in
       AND 	 COD_PROD = respaldoStk_rec.COD_PROD;

      \*
      UPDATE LGT_PROD_LOCAL l
         SET FEC_MOD_PROD_LOCAL = SYSDATE, USU_MOD_PROD_LOCAL = vIdUsuario_in,
             STK_COMPROMETIDO = NVL(STK_COMPROMETIDO,0)
                                - respaldoStk_rec.CANT_MOV/NVL(respaldoStk_rec.VAL_FRAC_LOCAL,l.val_frac_local)*l.val_frac_local --MODIFICACION JLUNA 20090216
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
 	     AND 	 COD_LOCAL = cCodLocal_in
       AND 	 COD_PROD = respaldoStk_rec.COD_PROD;
      *\

      DELETE PBL_RESPALDO_STK
	    WHERE  \*COD_GRUPO_CIA = cCodGrupoCia_in
 	    AND    COD_LOCAL = cCodLocal_in
	    AND    NUM_IP_PC = cNumIpPc_in
      AND 	 COD_PROD = respaldoStk_rec.COD_PROD
      AND    VAL_FRAC_LOCAL = respaldoStk_rec.VAL_FRAC_LOCAL
      AND    CANT_MOV = respaldoStk_rec.CANT_MOV
	    AND	   NUM_PED_VTA IS NULL
      AND    ROWNUM = 1;
      *\
      SEC_RESPALDO_STK=respaldoStk_rec.Sec_Respaldo_Stk
      AND NUM_PED_VTA IS NULL;
    END LOOP;*/
    null;
  END;

/***********************************************************************************************************************/

FUNCTION VERIFICA_STOCK_RESPALDO(cCodGrupoCia_in IN CHAR,
									                 cCodLocal_in  	 IN CHAR,
									                 cCodProd_in     IN CHAR,
                                   cSecResp_in IN NUMBER)
    RETURN NUMBER IS
  v_nStkComprometido NUMBER;
  v_nSumaRespaldo    NUMBER;
  v_nResultado       NUMBER;
  v_nMultiplo NUMBER;
  BEGIN
    /*SELECT P.VAL_FRAC_VTA_SUG*L.VAL_FRAC_LOCAL
      INTO v_nMultiplo
    FROM LGT_PROD P,
         LGT_PROD_LOCAL L
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND L.COD_LOCAL = cCodLocal_in
          AND L.COD_PROD = cCodProd_in
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD;

  	   SELECT NVL(PROD_LOCAL.STK_COMPROMETIDO*v_nMultiplo/PROD_LOCAL.VAL_FRAC_LOCAL,0)
       INTO   v_nStkComprometido
       FROM   LGT_PROD_LOCAL PROD_LOCAL
       WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    PROD_LOCAL.COD_LOCAL = cCodLocal_in
       AND    PROD_LOCAL.COD_PROD = cCodProd_in;

       SELECT NVL(SUM(RESPALDO_STK.CANT_MOV*v_nMultiplo/RESPALDO_STK.VAL_FRAC_LOCAL),0)
       INTO   v_nSumaRespaldo
       FROM   PBL_RESPALDO_STK RESPALDO_STK
       WHERE  RESPALDO_STK.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    RESPALDO_STK.COD_LOCAL = cCodLocal_in
       AND    RESPALDO_STK.COD_PROD = cCodProd_in;
       --AND    RESPALDO_STK.SEC_RESPALDO_STK=cSecResp_in;

       DBMS_OUTPUT.PUT_LINE('v_nStkComprometido: '||v_nStkComprometido);
       DBMS_OUTPUT.PUT_LINE('v_nSumaRespaldo: '||v_nSumaRespaldo);

       IF v_nStkComprometido = (v_nSumaRespaldo) THEN
          v_nResultado := 1;--NO HAY ERROR
       ELSE
          v_nResultado := 2;--HAY ERROR
       END IF;
       */
       v_nResultado := 1;
       RETURN v_nResultado;
  EXCEPTION
  		WHEN OTHERS THEN
			     v_nResultado := 2;--HAY ERROR
           RETURN v_nResultado;
  END;


/***********************************************************************************************************************/

PROCEDURE RECEP_P_OPERAC_ANEXAS_PROD_LI(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumNota_in     IN CHAR,
                                       nSecGuia_in     IN NUMBER,
                                       cCodProd_in     IN CHAR,
                                       nCantMov_in     IN NUMBER,
                                       cIdUsu_in       IN CHAR,
                                       nTotalGuia_in   IN NUMBER DEFAULT NULL,
                                       nDiferencia_in  IN NUMBER DEFAULT NULL) AS
    v_nValFrac     LGT_NOTA_ES_DET.VAL_FRAC%TYPE;
    v_vDescUnidVta LGT_NOTA_ES_DET.DESC_UNID_VTA%TYPE;
    v_nStkFisico   LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nNumGuia     LGT_GUIA_REM.NUM_GUIA_REM%TYPE;

    stkFis lgt_prod_local.stk_fisico%TYPE; --INI - ASOSA, 21.07.2010
    stkComp lgt_prod_local.stk_fisico%TYPE;
    usuMod lgt_prod_local.usu_mod_prod_local%TYPE;
    fecMod lgt_prod_local.fec_mod_prod_local%TYPE; --FIN - ASOSA, 21.07.2010
  BEGIN
    SELECT DECODE(VAL_FRAC_LOCAL, NULL, 1, 0, 1, VAL_FRAC_LOCAL),
           UNID_VTA,
           STK_FISICO
      INTO v_nValFrac, v_vDescUnidVta, v_nStkFisico
      FROM LGT_PROD_LOCAL
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND COD_PROD = cCodProd_in;

       SELECT a.stk_fisico, /*a.stk_comprometido*/0, a.usu_mod_prod_local, a.fec_mod_prod_local --/ASOSA, 21.07.2010
       INTO stkFis, stkComp, usuMod, fecMod
       FROM lgt_prod_local a
       WHERE a.cod_grupo_cia=cCodGrupoCia_in
       AND a.cod_local=cCodLocal_in
       AND a.cod_prod=cCodProd_in FOR UPDATE;

    --ACTUALIZA PROD LOCAL
    UPDATE LGT_PROD_LOCAL
       SET USU_MOD_PROD_LOCAL = cIdUsu_in,
           FEC_MOD_PROD_LOCAL = SYSDATE,
           STK_FISICO         = STK_FISICO + nCantMov_in * v_nValFrac
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND COD_PROD = cCodProd_in;

    SELECT NUM_ENTREGA
      INTO v_nNumGuia
      FROM LGT_GUIA_REM
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in
       AND SEC_GUIA_REM = nSecGuia_in;
    --INSERTAR KARDEX AQUELLAS GUIAS QUE NO ESTAN
    IF (nTotalGuia_in IS NOT NULL) AND (nDiferencia_in IS NOT NULL) AND
       (nDiferencia_in <> 0) THEN
      PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_GRABAR_KARDEX(cCodGrupoCia_in,
                            cCodLocal_in,
                            cCodProd_in,
                            g_cMotKardexIngMatriz,
                            g_cTipDocKdxGuiaES,
                            cNumNota_in,
                            v_nStkFisico,
                            nTotalGuia_in * v_nValFrac,
                            v_nValFrac,
                            v_vDescUnidVta,
                            cIdUsu_in,
                            COD_NUMERA_SEC_KARDEX,
                            '',
                            g_cTipCompNumEntrega,
                            v_nNumGuia);
      PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_GRABAR_KARDEX(cCodGrupoCia_in,
                            cCodLocal_in,
                            cCodProd_in,
                            g_cTipoMotKardexAjusteGuia,
                            g_cTipDocKdxGuiaES,
                            cNumNota_in,
                            v_nStkFisico + (nTotalGuia_in * v_nValFrac),
                            (ABS(nDiferencia_in) * -1) * v_nValFrac,
                            v_nValFrac,
                            v_vDescUnidVta,
                            cIdUsu_in,
                            COD_NUMERA_SEC_KARDEX,
                            '',
                            g_cTipCompNumEntrega,
                            v_nNumGuia);

    END IF;

  END;


/***********************************************************************************************************************/

PROCEDURE RECEP_P_OPERAC_ANEXAS_PROD_SO(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumNota_in     IN CHAR,
                                       nSecGuia_in     IN NUMBER,
                                       cCodProd_in     IN CHAR,
                                       nCantMov_in     IN NUMBER,
                                       cIdUsu_in       IN CHAR) AS

    v_nValFrac     LGT_NOTA_ES_DET.VAL_FRAC%TYPE;
    v_vDescUnidVta LGT_NOTA_ES_DET.DESC_UNID_VTA%TYPE;
    v_nStkFisico   LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nNumGuia     LGT_GUIA_REM.NUM_GUIA_REM%TYPE;

    stkFis lgt_prod_local.stk_fisico%TYPE; --INI - ASOSA, 21.07.2010
    stkComp lgt_prod_local.stk_fisico%TYPE;
    usuMod lgt_prod_local.usu_mod_prod_local%TYPE;
    fecMod lgt_prod_local.fec_mod_prod_local%TYPE; --FIN - ASOSA, 21.07.2010
  BEGIN
    SELECT DECODE(VAL_FRAC_LOCAL, NULL, 1, 0, 1, VAL_FRAC_LOCAL),
           UNID_VTA,
           STK_FISICO
      INTO v_nValFrac, v_vDescUnidVta, v_nStkFisico
      FROM LGT_PROD_LOCAL
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND COD_PROD = cCodProd_in;

       SELECT a.stk_fisico, /*a.stk_comprometido*/0, a.usu_mod_prod_local, a.fec_mod_prod_local --/ASOSA, 21.07.2010
       INTO stkFis, stkComp, usuMod, fecMod
       FROM lgt_prod_local a
       WHERE a.cod_grupo_cia=cCodGrupoCia_in
       AND a.cod_local=cCodLocal_in
       AND a.cod_prod=cCodProd_in FOR UPDATE;

    --ACTUALIZA PROD LOCAL
    UPDATE LGT_PROD_LOCAL
       SET USU_MOD_PROD_LOCAL = cIdUsu_in,
           FEC_MOD_PROD_LOCAL = SYSDATE,
           STK_FISICO         = STK_FISICO + nCantMov_in * v_nValFrac
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND COD_PROD = cCodProd_in;

    SELECT NUM_ENTREGA
      INTO v_nNumGuia
      FROM LGT_GUIA_REM
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in
       AND SEC_GUIA_REM = nSecGuia_in;

      PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_GRABAR_KARDEX(cCodGrupoCia_in,
                            cCodLocal_in,
                            cCodProd_in,
                            g_cMotKardexSobRecep,
                            g_cTipDocKdxGuiaES,
                            cNumNota_in,
                            v_nStkFisico,
                            nCantMov_in* v_nValFrac,
                            v_nValFrac,
                            v_vDescUnidVta,
                            cIdUsu_in,
                            COD_NUMERA_SEC_KARDEX,
                            '',
                            g_cTipCompNumEntrega,
                            v_nNumGuia);



  END;

/***********************************************************************************************************************/

FUNCTION F_EXISTE_STOCK_PEDIDO(cCodGrupoCia_in IN CHAR,
  		   				  		         cCodLocal_in	 IN CHAR,
								               cNumPedVta_in IN CHAR)
    RETURN char
  IS
    curVta char(1);
    esNegativo number;
    indVtaNegativa CHAR(1);
    cantidad number;
    resto number;
  BEGIN

   EXECUTE IMMEDIATE
   'SELECT L.COD_PROD,L.STK_FISICO,L.VAL_FRAC_LOCAL '||
   'FROM   LGT_PROD_LOCAL L '||
   'WHERE  EXISTS ( '||
                 'SELECT 1 '||
                 'FROM   VTA_PEDIDO_VTA_DET D '||
                 'WHERE  D.COD_GRUPO_CIA = :1 '||
                 'AND    D.COD_LOCAL     = :2 '||
                 'AND    D.NUM_PED_VTA   = :3 '||
                 'AND    D.COD_GRUPO_CIA = L.COD_GRUPO_CIA '||
                 'AND    D.COD_LOCAL = L.COD_LOCAL '||
                 'AND    D.COD_PROD  = L.COD_PROD '||
                 ')FOR UPDATE' 
    using cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in;

    select count(1)
    into   esNegativo
    from  (
            select d.cod_prod,l.stk_fisico +
                   nvl((
                   SELECT a.cant_producto
                  FROM    DET_SOLICITUD_STOCK A, CAB_SOLICITUD_STOCK B,
                          LGT_PROD_LOCAL PROD_LOCAL
                 WHERE   B.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND   B.COD_LOCAL = cCodLocal_in
                   and  b.num_ped_vta = cNumPedVta_in
                   and  a.cod_producto = d.cod_prod
                   AND A.COD_GRUPO_CIA = b.COD_GRUPO_CIA
                   AND A.COD_LOCAL = b.COD_LOCAL
                   AND A.ID_SOLICITUD = b.id_solicitud
                   and a.cod_grupo_cia = PROD_LOCAL.COD_GRUPO_CIA
                   and a.cod_local = PROD_LOCAL.Cod_Local
                   and a.cod_producto = PROD_LOCAL.COD_PROD
                   ),0) as "STK_FISICO",
                   sum(d.cant_atendida*l.val_frac_local/d.val_frac) cant_pedida
            from   vta_pedido_vta_det d,
                   lgt_prod_local l
            where  d.cod_grupo_cia = cCodGrupoCia_in
            and    d.cod_local = cCodLocal_in
            and    d.num_ped_vta = cNumPedVta_in
            and    d.cod_grupo_cia = l.cod_grupo_cia
            and    d.cod_local = l.cod_local
            and    d.cod_prod = l.cod_prod
            AND    D.COD_PROD NOT IN (
                                      SELECT V.COD_PROD
                                      FROM  LGT_PROD_VIRTUAL V
                                      WHERE  V.TIP_PROD_VIRTUAL IN ('R','M','B') -- KMONCADA SE AGREGA BALANZA KEITO
                                      AND    V.COD_GRUPO_CIA = cCodGrupoCia_in
                                     )
             --INI ASOSA - 06/10/2014 - PANHD
             AND  NOT EXISTS (
                                                                    SELECT 1
                                                                    FROM LGT_PROD PP
                                                                    WHERE PP.IND_TIPO_CONSUMO = TIPO_PROD_FINAL
                                                                    AND D.COD_PROD = PP.COD_PROD
                                                                    AND D.COD_GRUPO_CIA = PP.COD_GRUPO_CIA
                                                                    )
             --FIN ASOSA - 06/10/2014 - PANHD
            group by d.cod_prod,l.stk_fisico
          ) v
     where v.stk_fisico < v.cant_pedida;

     if esNegativo > 0  then
        -- esto NO ESTA PERMITIDO ya que no puede vender NEGATIVO si
        -- no esta ACTIVO el INDICADOR
        -- POR LO TANTO NO VA DEJAR COBRAR EL PEDIDO.
        curVta := 'N';
     else

            select sum(cant_pedida)
            into   cantidad
            from  (
                    select d.cod_prod,l.stk_fisico,sum(d.cant_atendida*l.val_frac_local/d.val_frac) cant_pedida
                    from   vta_pedido_vta_det d,
                           lgt_prod_local l
                    where  d.cod_grupo_cia = cCodGrupoCia_in
                    and    d.cod_local = cCodLocal_in
                    and    d.num_ped_vta = cNumPedVta_in
                    and    d.cod_grupo_cia = l.cod_grupo_cia
                    and    d.cod_local = l.cod_local
                    and    d.cod_prod = l.cod_prod
                    AND    D.COD_PROD NOT IN (
                                      SELECT V.COD_PROD
                                      FROM  LGT_PROD_VIRTUAL V
                                      WHERE  V.TIP_PROD_VIRTUAL = 'R'
                                      AND    V.COD_GRUPO_CIA = cCodGrupoCia_in
                                     )
                     --INI ASOSA - 06/10/2014 - PANHD
                     AND  NOT EXISTS (
                                                                            SELECT 1
                                                                            FROM LGT_PROD PP
                                                                            WHERE PP.IND_TIPO_CONSUMO = TIPO_PROD_FINAL
                                                                            AND D.COD_PROD = PP.COD_PROD
                                                                    AND D.COD_GRUPO_CIA = PP.COD_GRUPO_CIA
                                                                            )
                     --FIN ASOSA - 06/10/2014 - PANHD
                    group by d.cod_prod,l.stk_fisico
                  ) v;

           select  cantidad  - trunc(cantidad)
           into    resto
           from    dual;
        if resto > 0 then
         curVta := 'F';
        else
         curVta := 'S';
        end if;
     end if;
     
     

  RETURN curVta;
end;

  /*Procedimiento que anula las devoluciones de un producto desde el Legacy hacia el FarmaVenta*/
   PROCEDURE INV_ANULA_DEVOLUCION_MERCA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,
                                        cCodMotKardex_in IN CHAR, cTipDocKardex_in IN CHAR,
                                        vIdUsu_in IN VARCHAR2)

   AS
    CURSOR curDet IS
    SELECT SEC_DET_NOTA_ES,COD_PROD,CANT_MOV,SEC_GUIA_REM,VAL_FRAC
    FROM LGT_NOTA_ES_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;
--
    v_nFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nComprometido LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nValFrac LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    v_vDescUnidVta LGT_PROD_LOCAL.UNID_VTA%TYPE;
--
    det curDet%ROWTYPE;

    v_nCant LGT_NOTA_ES_DET.CANT_MOV%TYPE;
    v_cProdCong LGT_PROD_LOCAL.IND_PROD_CONG%TYPE;


 BEGIN
   --ACTUALIZA EL ESTADO DE LA CABECERA
   UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = vIdUsu_in,FEC_MOD_NOTA_ES_CAB = SYSDATE,
          EST_NOTA_ES_CAB = 'N'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;

   FOR det IN curDet
    LOOP

      SELECT STK_FISICO,STK_FISICO-/*STK_COMPROMETIDO*/0,VAL_FRAC_LOCAL,UNID_VTA,L.IND_PROD_CONG
        INTO v_nFisico,v_nComprometido,v_nValFrac, v_vDescUnidVta, v_cProdCong
      FROM LGT_PROD_LOCAL L
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = det.COD_PROD FOR UPDATE;
      --VERIFICA PRODUCTO CONGELADO
      IF v_cProdCong = 'S' THEN
        RAISE_APPLICATION_ERROR(-20003,'ERROR AL ANULAR. PROD:'||det.COD_PROD||' CONGELADO');
      END IF;
      --VERIFICA FRACCION ACTUAL
      IF MOD(det.CANT_MOV*v_nValFrac,det.VAL_FRAC) = 0 THEN
        v_nCant := ((det.CANT_MOV*v_nValFrac)/det.VAL_FRAC);
      ELSE
        RAISE_APPLICATION_ERROR(-20002,'Error al anular. Prod:'||det.COD_PROD||',Cant:'||det.CANT_MOV||' ,Frac:'||det.VAL_FRAC||' ,Frac. Act:'||v_nValFrac);
      END IF;
      --ACTUALIZA EL STOCK DEL PRODUCTO DEL LOCAL
      UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = vIdUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
            STK_FISICO = STK_FISICO + v_nCant
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = det.COD_PROD;
      --ACTUALIZA EL ESTADO DE LA NOTA DE DETALLE
      UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET = vIdUsu_in,FEC_MOD_NOTA_ES_DET = SYSDATE,
            EST_NOTA_ES_DET = 'N'
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in
          AND SEC_DET_NOTA_ES = det.SEC_DET_NOTA_ES;

  --GRABA KARDER SEGUN EL STOCK ANTERIOR Y EL STOCK FINAL DEL PRODUCTO
      PTOVENTA_INV.INV_GRABAR_KARDEX(cCodGrupoCia_in ,
                          cCodLocal_in,
                          det.COD_PROD,
                          cCodMotKardex_in,
                          cTipDocKardex_in,
                          cNumNota_in,
                          v_nFisico,
                          v_nCant,
                          v_nValFrac,
                          v_vDescUnidVta,
                          vIdUsu_in,
                          COD_NUMERA_SEC_KARDEX,'');

    --ACTUALIZA KARDEX SEGUN EL TIPO Y NUMERO DE COMPROBANTE DE PAGO
      PTOVTA_RESPALDO_STK.INV_ACT_KARDEX_GUIA_REC(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,det.SEC_GUIA_REM,
                                det.COD_PROD,
                                vIdUsu_in);

    END LOOP;
    END;


    FUNCTION INV_ANULA_DEVOLUCION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cCodMotKardex_in IN CHAR, cTipDocKardex_in IN CHAR,
                                        vIdUsu_in IN VARCHAR2)
    RETURN CHAR
    IS
    v_cEstado CHAR(1);
    flag CHAR(7);
    BEGIN
      flag:='false';
      SELECT EST_NOTA_ES_CAB
      INTO v_cEstado
      FROM LGT_NOTA_ES_CAB
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_NOTA_ES = cNumNota_in FOR UPDATE;

            IF v_cEstado='N' THEN
            flag:='FALSE';
            DBMS_OUTPUT.PUT_LINE('ESTE REGISTRO YA SE ENCUENTRA ANULADO:'||cNumNota_in);

            ELSIF  v_cEstado='C' THEN
            flag:='FALSE';
            DBMS_OUTPUT.PUT_LINE('EL REGISTRO SE ENCUENTRA CONFIRMADO:'||cNumNota_in);

            ELSE
            PTOVENTA_INV.INV_P_REG_TMP_INIFIN_ANUTRANS(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,'I');
            PTOVTA_RESPALDO_STK.INV_ANULA_DEVOLUCION_MERCA(cCodGrupoCia_in ,cCodLocal_in ,cNumNota_in ,
                                        cCodMotKardex_in , cTipDocKardex_in ,
                                        vIdUsu_in );
            PTOVENTA_INV.INV_P_REG_TMP_INIFIN_ANUTRANS(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,'F');
            flag:='TRUE';
            DBMS_OUTPUT.PUT_LINE('REGISTRO ANULADO:'||cNumNota_in);
          END IF;
    RETURN flag;

    EXCEPTION
          WHEN OTHERS THEN

              DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
     RETURN flag;

  END;


    FUNCTION INV_GET_ESTADO_DEVOLUCION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR)
    RETURN CHAR
           IS
          v_cEstado CHAR(1);
          v_Message CHAR(20);
     BEGIN

          SELECT EST_NOTA_ES_CAB
          INTO v_cEstado
          FROM LGT_NOTA_ES_CAB
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND NUM_NOTA_ES = cNumNota_in;

            IF v_cEstado='N' THEN
            v_Message:='ANULADO';
            DBMS_OUTPUT.PUT_LINE('ESTE REGISTRO YA SE ENCUENTRA ANULADO:'||cNumNota_in);

            ELSIF  v_cEstado='C' THEN
            v_Message:='CONFIRMADO';
            DBMS_OUTPUT.PUT_LINE('EL REGISTRO SE ENCUENTRA CONFIRMADO:'||cNumNota_in);

            ELSE
            v_Message:='OK';
            DBMS_OUTPUT.PUT_LINE('REGISTRO HABILITADO:'||cNumNota_in);

            END IF;

    RETURN v_Message;
  END;


/*-------------------------------------------------------------------------------------------------------------------------
GOAL : Grabar Datos Para Depuracion
Ammedments:
14-ENE-14  TCT      Create
---------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_GRABA_DEBUG(ac_Desc IN VARCHAR2)
AS
PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
 INSERT INTO aux_debug (
                        fech_load,
                        descrip
                        )
 VALUES (SYSDATE,ac_Desc);
 ---
 COMMIT;

END;
/*******************************************************************************************************************************/

PROCEDURE CAJ_DELIVERY_GENERA_PENDIENTE(cCodGrupoCia_in IN CHAR,

                                        cCodLocal_in    IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        vIdUsu_in       IN CHAR,
                                        cModulo_in      IN CHAR DEFAULT 'V')
  AS
    mesg_body VARCHAR2(4000);
    v_cIndProdVirtual CHAR(1);
    v_cIndDelivAutomatico VTA_PEDIDO_VTA_CAB.IND_DELIV_AUTOMATICO%TYPE;
    CURSOR curProd IS
           SELECT DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.CANT_FRAC_LOCAL,--ERIOS 05/06/2008 Se obtiene el cantida en el frac. del local.
                  DET.SEC_PED_VTA_DET,
                  DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR,
                  DET.Sec_Respaldo_Stk --ASOSA, 12.07.2010
           FROM   VTA_PEDIDO_VTA_DET DET,
                  LGT_PROD_VIRTUAL VIR
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+)
           ORDER BY DET.CANT_ATENDIDA DESC ;
    v_rCurProd curProd%ROWTYPE;

    v_cEstPedido  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
    v_cIndAnulado VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;


    stkComp  lgt_prod_local.stk_fisico%TYPE;
            
    cantidad lgt_prod_local.stk_fisico%TYPE; 
    valFracLocal lgt_prod_local.val_frac_local%TYPE; 
    cantMov     vta_pedido_vta_det.cant_atendida%type;
    valFracResp vta_pedido_vta_det.val_frac_local%type;
    ind VARCHAR2(200); 


    cantidadRespaldo NUMBER(3); 
  BEGIN
       --SE VALIDA EL ESTADO DEL PEDIDO ANTES DE ANULAR
       SELECT EST_PED_VTA,
              IND_PEDIDO_ANUL
       INTO   v_cEstPedido,
              v_cIndAnulado
       FROM   VTA_PEDIDO_VTA_CAB
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    NUM_PED_VTA = cNumPedVta_in  FOR UPDATE;
      -- AND    VAL_NETO_PED_VTA = nMontoVta_in

       if v_cEstPedido = 'N' THEN
             RAISE_APPLICATION_ERROR(-20002,'El Pedido esta anulado.');
          elsif v_cEstPedido = 'C' THEN
                RAISE_APPLICATION_ERROR(-20003,'El Pedido fue cobrado. ¡No puede Anular este Pedido!');
       END IF;

       IF v_cEstPedido <> 'P' THEN
          RAISE_APPLICATION_ERROR(-20001,'El Pedido no esta pendiente. ¡No puede Anular este Pedido!');
       END IF;

       SELECT VTA_CAB.IND_DELIV_AUTOMATICO
       INTO	  v_cIndDelivAutomatico
       FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
       WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND	  VTA_CAB.COD_LOCAL = cCodLocal_in
       AND	  VTA_CAB.NUM_PED_VTA = cNumPedVta_in;

       UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = vIdUsu_in,  FEC_MOD_PED_VTA_CAB = SYSDATE,
    	        EST_PED_VTA = EST_PED_ANULADO,
              IND_PEDIDO_ANUL = INDICADOR_SI
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL     = cCodLocal_in
       AND    NUM_PED_VTA   = cNumPedVta_in;
dbms_output.put_line('ddddddddddddddddd');
       IF(v_cIndDelivAutomatico = INDICADOR_SI) THEN
	        UPDATE TMP_VTA_PEDIDO_VTA_CAB TMP_CAB SET TMP_CAB.USU_MOD_PED_VTA_CAB = vIdUsu_in, TMP_CAB.FEC_MOD_PED_VTA_CAB = SYSDATE,
                 TMP_CAB.EST_PED_VTA = EST_PED_PENDIENTE, -- RHERRERA
                 TMP_CAB.Num_Ped_Vta_Origen = '' ,
                 TMP_CAB.IND_PEDIDO_ANUL = INDICADOR_NO
          WHERE  TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    TMP_CAB.COD_LOCAL_ATENCION = cCodLocal_in
          AND    TMP_CAB.NUM_PED_VTA_ORIGEN = cNumPedVta_in;
       END IF;

       FOR v_rCurProd IN curProd
       LOOP
           v_cIndProdVirtual := v_rCurProd.IND_PROD_VIR;
             UPDATE VTA_PEDIDO_VTA_DET  SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
  	  		          EST_PED_VTA_DET = 'N'
             WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
             AND    COD_LOCAL     = cCodLocal_in
             AND    NUM_PED_VTA   = cNumPedVta_in
             AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;
          
      END LOOP;

      update vta_camp_pedido_cupon p
      set    p.estado = 'N'
      where  p.cod_grupo_cia = cCodGrupoCia_in
      and    p.cod_local = cCodLocal_in
      and    p.num_ped_vta = cNumPedVta_in
      and    p.estado = 'S';


  END;
  /*****************************************************************/

    FUNCTION GET_INFO_RECETA_PFINAL(cCodGrupoCia_in IN CHAR,
                                                                             cCodProd_in IN CHAR)
    RETURN VARCHAR2
           IS
          rpta  VARCHAR2(1000) := '';
          codTipoConsumo CHAR(1) := '';
          cantCompConfig number(3) := 0;
--          cantCompConStock number(3) := 0;
     BEGIN

          SELECT A.IND_TIPO_CONSUMO
          INTO codTipoConsumo
          FROM LGT_PROD A
          WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
           AND A.COD_PROD = cCodProd_in;

            SELECT COUNT(1)
            INTO cantCompConfig
            FROM LGT_REL_PROD_TICO B
            WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
            AND B.COD_PROD_PADRE = cCodProd_in
            AND B.CANT_RECETA > 0 --si le configuraron la cantidad a utilizar
            AND B.EST_REL_PROD = FLAG_ACTIVO;
            
            IF codTipoConsumo = TIPO_PROD_FINAL THEN
                              IF cantCompConfig > 0 THEN
                                     rpta := 'S' || 'Ã' || 'OK';
                              ELSE
                                     rpta := 'N' || 'Ã' || 'EL PRODUCTO FINAL NO TIENE COMPONENTES ACTIVOS CONFIGURADOS';
                              END IF;
            ELSE 
                              rpta := 'N' || 'Ã' || 'EL PRODUCTO NO ESTA DEFINIDO COMO UN PRODUCTO FINAL PERECEDERO';
            END IF;
            
    RETURN rpta;
  END;

  /*****************************************************************/
  
      FUNCTION GET_VALIDA_STOCK_COMP(cCodGrupoCia_in IN CHAR,
                                                                             cCodLocal_in IN CHAR,
                                                                             cCodProd_in IN CHAR,
                                                                             nCantPedido IN NUMBER)
    RETURN VARCHAR2
           IS
          rpta  VARCHAR2(1000) := 'S';
          cantCompWithoutStock number(3) :=  0;
     BEGIN

           SELECT COUNT(1)
           INTO cantCompWithoutStock
           FROM 
                  (SELECT pl.cod_prod, 
                                   pl.stk_fisico-(nCantPedido *((TT.CANT_RECETA / TT.VAL_FRAC) * pl.val_frac_local)) AS CANTIDAD
                  FROM LGT_PROD_LOCAL PL,
                                LGT_REL_PROD_TICO TT
                  WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND PL.COD_LOCAL = cCodLocal_in
                  AND PL.COD_GRUPO_CIA = TT.COD_GRUPO_CIA
                  AND PL.COD_PROD = TT.COD_PROD_HIJO
                  AND TT.COD_PROD_PADRE = cCodProd_in)
            WHERE CANTIDAD < 0;
            
            IF cantCompWithoutStock > 0 THEN
                         rpta := 'N';
            END IF;
            
    RETURN rpta;
  END;

  /*****************************************************************/
  
PROCEDURE PVTA_P_INS_DET_TRANSC_COMP(cCodGrupoCia_in 	 	  IN CHAR,
                                      cCodLocal_in    	 	  IN CHAR,
                            				  cNumPedVta_in   	 	  IN CHAR,
                            				  nSecPedVtaDet_in		  IN NUMBER,
                            				  cCodProd_in			      IN CHAR,
                            				  nCantAtendida_in	 	  IN NUMBER,
                                      cUsuCreaPedVtaDet_in	IN CHAR
                                      ) 
    IS
       cursor componentes is
       select a.cod_prod_hijo, 
                     a.cant_receta,
                     a.val_frac,
                     b.val_frac_local
       from LGT_REL_PROD_TICO a,
                  LGT_PROD_LOCAL b
       where a.cod_grupo_cia = b.cod_grupo_cia
       and a.cod_prod_hijo = b.cod_prod
       and a.cod_grupo_cia = cCodGrupoCia_in
       and a.cod_prod_padre = cCodProd_in
       and a.est_rel_prod = FLAG_ACTIVO;
       
   begin
   
      	FOR comp IN componentes
      	LOOP
              INSERT INTO VTA_DET_REL_PROD_TICO(cod_grupo_cia, 
                                                                                            cod_local, 
                                                                                            num_ped_vta, 
                                                                                            sec_ped_vta_det, 
                                                                                            cod_prod_padre, 
                                                                                            cod_prod_hijo, 
                                                                                            cant_receta, 
                                                                                            cant_usada, 
                                                                                            usu_crea_deta_rel, 
                                                                                            fec_crea_deta_rel,
                                                                                            val_frac)
              VALUES(cCodGrupoCia_in, 
                               cCodLocal_in,
                               cNumPedVta_in,
                               nSecPedVtaDet_in,
                               cCodProd_in,
                               comp.cod_prod_hijo,
                               comp.cant_receta,
                               (nCantAtendida_in * ((comp.cant_receta/comp.val_frac) * comp.val_frac_local)),
                               cUsuCreaPedVtaDet_in,
                               sysdate,
                               comp.val_frac);
              
        end loop;

  END;  

  /*****************************************************************/

FUNCTION F_EXISTE_STK_PED_PROD_FINAL(cCodGrupoCia_in IN CHAR,
  		   				  		         cCodLocal_in	 IN CHAR,
								               cNumPedVta_in IN CHAR)
    RETURN char

  IS
    flag char(1) := 'S';
    subFlag char(1) := '';
    cant number(3) := 0;
    
    cursor componentes is
          select a.cod_prod_hijo, 
                     a.cant_receta, 
                     b.cant_atendida
       from LGT_REL_PROD_TICO a,
                   VTA_PEDIDO_VTA_DET b
       where a.cod_grupo_cia = b.cod_grupo_cia
       and a.cod_prod_padre = b.cod_prod      
       and b.COD_GRUPO_CIA = cCodGrupoCia_in 
       and b.COD_LOCAL     = cCodLocal_in
       and b.NUM_PED_VTA   = cNumPedVta_in
       and a.est_rel_prod = FLAG_ACTIVO;
    
  BEGIN

   EXECUTE IMMEDIATE                
    'SELECT L.COD_PROD,L.STK_FISICO,L.VAL_FRAC_LOCAL '||
   'FROM   LGT_PROD_LOCAL L '||
   'WHERE  L.COD_PROD IN ( '||
  '                                               SELECT PT.COD_PROD_HIJO '||
  '                                              FROM LGT_REL_PROD_TICO PT '||
  '                                               WHERE PT.COD_PROD_PADRE IN (SELECT D.COD_PROD '||
    '                                                                                                           FROM   VTA_PEDIDO_VTA_DET D '||
   '                                                                                                            WHERE  D.COD_GRUPO_CIA = :1  '||
  '                                                                                                             AND    D.COD_LOCAL     = :2 '||
 '                                                                                                              AND    D.NUM_PED_VTA   = :3) '||
  '                                             )FOR UPDATE '
    using cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in;

    	FOR comp IN componentes
      	LOOP
               
               subFlag := GET_VALIDA_STOCK_COMP(cCodGrupoCia_in ,
                                                                          cCodLocal_in,
                                                                          comp.cod_prod_hijo,
                                                                          comp.cant_atendida);
               IF subFlag = 'N' THEN
                      cant := cant + 1;
               END IF;
        END LOOP;
        
        IF cant > 0 THEN
                flag := 'N';
        END IF;
       
     return flag;
END;

  /*****************************************************************/
  
  PROCEDURE CAJ_UPD_STK_PROD_COMP(cCodGrupoCia_in 	   IN CHAR,
						   	 			                     cCodLocal_in    	   IN CHAR,
							 			                       cNumPedVta_in   	   IN CHAR,
                                           cCodMotKardex_in IN CHAR,    --ASOSA - 17/10/2014
						   	  			                   cTipDocKardex_in    IN CHAR,
										                       cCodNumeraKardex_in IN CHAR,
							 			                       cUsuModProdLocal_in IN CHAR)
  IS

    mesg_body VARCHAR2(4000);
--productos finales del detalle.
	CURSOR prod_final_Kardex IS
         SELECT VTA_DET.COD_PROD,
                SUM(VTA_DET.CANT_FRAC_LOCAL) CANT_ATENDIDA, 
                PROD_LOCAL.STK_FISICO,
				 	      PROD_LOCAL.VAL_FRAC_LOCAL,
				 	      PROD_LOCAL.UNID_VTA
				 FROM   VTA_PEDIDO_VTA_DET VTA_DET,
					      LGT_PROD_LOCAL PROD_LOCAL
			   WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
				 AND    VTA_DET.COD_LOCAL     = cCodLocal_in
				 AND	  VTA_DET.NUM_PED_VTA   = cNumPedVta_in
				 AND	  VTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
				 AND	  VTA_DET.COD_LOCAL     = PROD_LOCAL.COD_LOCAL
				 AND	  VTA_DET.COD_PROD      = PROD_LOCAL.COD_PROD
          AND  EXISTS (
                                                                    SELECT 1
                                                                    FROM LGT_PROD PP
                                                                    WHERE PP.IND_TIPO_CONSUMO = TIPO_PROD_FINAL
                                                                    AND VTA_DET.COD_PROD = PP.COD_PROD
                                                                    AND VTA_DET.COD_GRUPO_CIA = PP.COD_GRUPO_CIA
                                                                    )
				 GROUP BY VTA_DET.COD_PROD,
					   	    PROD_LOCAL.VAL_FRAC_LOCAL,
					   	    PROD_LOCAL.UNID_VTA,
						      PROD_LOCAL.STK_FISICO;
                  
--productos componentes del detalle.   
	CURSOR prod_comp_Kardex IS
SELECT A.COD_PROD_HIJO AS COD_PROD, 
                        A.CANT_USADA AS CANT_ATENDIDA,
                        A.VAL_FRAC AS VAL_FRAC_VTA,
                        B.STK_FISICO,
                        B.VAL_FRAC_LOCAL,
                        B.UNID_VTA
FROM VTA_DET_REL_PROD_TICO A,
              LGT_PROD_LOCAL B
WHERE A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
AND A.COD_LOCAL = B.COD_LOCAL
AND A.COD_PROD_HIJO = B.COD_PROD
AND A.NUM_PED_VTA = cNumPedVta_in
AND A.COD_LOCAL = cCodLocal_in
AND A.COD_GRUPO_CIA = cCodGrupoCia_in;

    v_nCantAtendida VTA_PEDIDO_VTA_DET.CANT_ATENDIDA%TYPE;
    stkFis  lgt_prod_local.stk_fisico%TYPE;
    fecMod  lgt_prod_local.fec_mod_prod_local%TYPE;
    usuMod  lgt_prod_local.usu_mod_prod_local%TYPE;
    
  BEGIN
  /**
  ALTA_VTA_PROD CONSTANT CHAR(3) := '113';
	BAJA_VTA_PROD CONSTANT CHAR(3) := '114';
	CONSUMO_MATERIAL CONSTANT CHAR(3) := '117';
  **/

    --INSERTA KARDEX ALTA Y BAJA PRODUCTOS FINALES
   	FOR productos_K IN prod_final_Kardex
	  LOOP
	  	    --GRABAR KARDEX ALTA_VTA_PROD
          Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
    		  								               cCodLocal_in,
                    										 productos_K.COD_PROD,
                    										 ALTA_VTA_PROD,
                    										 cTipDocKardex_in,
                    										 cNumPedVta_in,
                    										 productos_K.STK_FISICO,
                    										 productos_k.CANT_ATENDIDA,
                    										 productos_K.VAL_FRAC_LOCAL,
                    										 productos_K.UNID_VTA,
                    										 cUsuModProdLocal_in,
                    										 cCodNumeraKardex_in);
                                         
          --GRABAR KARDEX VTA_NORMAL
          Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
    		  								               cCodLocal_in,
                    										 productos_K.COD_PROD,
                    										 cCodMotKardex_in,
                    										 cTipDocKardex_in,
                    										 cNumPedVta_in,
                    										 (productos_K.STK_FISICO + productos_k.CANT_ATENDIDA),
                    										 (productos_k.CANT_ATENDIDA * -1),
                    										 productos_K.VAL_FRAC_LOCAL,
                    										 productos_K.UNID_VTA,
                    										 cUsuModProdLocal_in,
                    										 cCodNumeraKardex_in);
        
    END LOOP;
    
    --INSERTA KARDEX PRODUCTOS COMPONENTES
    FOR productos_K IN prod_comp_Kardex
	  LOOP
	  	                                            
          --GRABAR KARDEX CONSUMO_MATERIAL
          Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
    		  								               cCodLocal_in,
                    										 productos_K.COD_PROD,
                    										 CONSUMO_MATERIAL,
                    										 cTipDocKardex_in,
                    										 cNumPedVta_in,
                    										 productos_K.STK_FISICO,
                    										 (productos_k.CANT_ATENDIDA * -1),
                    										 productos_K.VAL_FRAC_LOCAL,
                    										 productos_K.UNID_VTA,
                    										 cUsuModProdLocal_in,
                    										 cCodNumeraKardex_in);
        
    END LOOP;
    
    --ACTUALIZA STOCK PRODUCTOS COMPONENTES
    FOR productos_R IN prod_comp_Kardex
	  LOOP

          SELECT USU_MOD_PROD_LOCAL, FEC_MOD_PROD_LOCAL, STK_FISICO
          INTO usuMod, fecMod, stkFis
          FROM LGT_PROD_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    			AND	   COD_LOCAL = cCodLocal_in
    			AND	   COD_PROD = productos_R.COD_PROD FOR UPDATE;

          --ACTUALIZA STK PRODUCTO
          v_nCantAtendida := (productos_R.CANT_ATENDIDA*productos_R.VAL_FRAC_LOCAL)/productos_R.VAL_FRAC_VTA;
    		  UPDATE LGT_PROD_LOCAL 
          SET USU_MOD_PROD_LOCAL = cUsuModProdLocal_in, 
                  FEC_MOD_PROD_LOCAL = SYSDATE,
                  STK_FISICO = STK_FISICO - v_nCantAtendida
    			WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    			AND	   COD_LOCAL = cCodLocal_in
    			AND	   COD_PROD = productos_R.COD_PROD;
    	    
    END LOOP;
         
          
  EXCEPTION
    WHEN OTHERS THEN
         mesg_body := 'ERROR AL COBRAR PEDIDO No ' || cNumPedVta_in || '. ' || SQLERRM;
         FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                    cCodLocal_in,
                                    'DUBILLUZ@mifarma.com.pe',
                                    'ERROR AL COBRAR PEDIDO',
                                    'ERROR',
                                    mesg_body,
                                    '');
         RAISE;
  END;
  
  /*****************************************************************/  

END PTOVTA_RESPALDO_STK;
/
