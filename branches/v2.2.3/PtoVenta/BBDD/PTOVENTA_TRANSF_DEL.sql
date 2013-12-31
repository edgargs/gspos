--------------------------------------------------------
--  DDL for Package PTOVENTA_TRANSF_DEL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_TRANSF_DEL" is

  -- Author  : ERIOS
  -- Created : 12/09/2006 10:09:58 a.m.
  -- Purpose : TRANSFERENCIAS AUTOMATICAS

  TYPE FarmaCursor IS REF CURSOR;

  g_cNumNotaEs PBL_NUMERA.COD_NUMERA%TYPE := '011';

  g_cTipCompGuia LGT_KARDEX.Tip_Comp_Pago%TYPE := '03';

  g_cTipoNotaSalida LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '02';

  g_cTipoOrigenLocal CHAR(2):= '01';
  g_cTipoOrigenMatriz CHAR(2):= '02';

  COD_NUMERA_SEC_KARDEX  PBL_NUMERA.COD_NUMERA%TYPE := '016';
  COD_MOTIVO_TRANS_DEL CHAR(2) := '99';

  --Descripcion: Obtiene la lista de pedidos de transferencia pendientes.
  --Fecha       Usuario	  Comentario
  --06/11/2008  JCALLO    Creación
  FUNCTION TRANF_F_CUR_DEL_PEND(cGrupoCia_in IN CHAR,
                                 cCodLocal_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: detalle de los productos y cantidad del pedido de transferencia.
  --Fecha       Usuario	  Comentario
  --06/11/2008  JCALLO    Creación
  FUNCTION TRANF_F_CUR_DET_PEDIDO(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in 	      IN CHAR,
                                  cNumPedTransf_in    IN CHAR,
                                  nSecGrupo_in        IN NUMBER,
                                  cCodLocalOrigen_in  IN CHAR,
                                  cCodLocalDest_in    IN CHAR,
                                  nSecTrans_in        IN NUMBER
                                  )
  RETURN FarmaCursor;

  --Descripcion: Encargado de actualizar el estado del pedido.
  --Fecha       Usuario	  Comentario
  --06/11/2008  JCALLO    Creación
  PROCEDURE TRANF_P_UPDATE_CAB_PEDIDO(cCodGrupoCia_in 	   IN CHAR,
                                      cCodLocal_in    	   IN CHAR,
                                      cNumPed_in           IN CHAR,
                                      nSecGrupo_in         IN NUMBER,
                                      cCodLocalOrigen_in   IN char,
                                      cCodLocalDest_in     IN CHAR,
                                      nSecTrans_in         IN NUMBER,
                                      cEstado_in           IN CHAR,
                                      cUsuId_in            IN CHAR);

 FUNCTION TRANSF_F_CHAR_AGREGA_CABECERA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        vTipDestino_in IN CHAR,
                                        cCodDestino_in IN VARCHAR2,
                                        cTipMotivo_in IN CHAR,
                                        vDesEmp_in IN VARCHAR2,
                                        vRucEmp_in IN VARCHAR2,
                                        vDirEmp_in IN VARCHAR2,
                                        vDesTran_in IN VARCHAR2,
                                        vRucTran_in IN VARCHAR2,
                                        vDirTran_in IN VARCHAR2,
                                        vPlacaTran_in IN VARCHAR2,
                                        nCantItems_in IN NUMBER,
                                        nValTotal_in IN NUMBER,
                                        vUsu_in IN VARCHAR2,
                                        cCodMotTransInterno_in CHAR,

                                        cCodLocalDel_in IN CHAR,
                                        cNumPed_in IN CHAR,
                                        nSecGrupo  IN NUMBER

                                        )
  RETURN CHAR;

  PROCEDURE TRANSF_P_GRABAR_KARDEX(cCodGrupoCia_in 	   IN CHAR,
                                   cCodLocal_in    	   IN CHAR,
                                   cCodProd_in		   IN CHAR,
							                     cCodMotKardex_in 	   IN CHAR,
						   	                   cTipDocKardex_in     IN CHAR,
						   	                   cNumTipDoc_in  	   IN CHAR,
							                     nStkAnteriorProd_in  IN NUMBER,
							                     nCantMovProd_in  	   IN NUMBER,
							                     nValFrac_in		   IN NUMBER,
							                     cDescUnidVta_in	   IN CHAR,
							                     cUsuCreaKardex_in	   IN CHAR,
							                     cCodNumera_in	   	   IN CHAR,
							                     cGlosa_in			   IN CHAR DEFAULT NULL,
                                   cTipDoc_in IN CHAR DEFAULT NULL,
                                   cNumDoc_in IN CHAR DEFAULT NULL);
/*
  PROCEDURE TRANSF_P_AGREGA_DETALLE(cCodGrupoCia_in  IN CHAR,
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
                                    cCodDestino_in   IN CHAR);
*/
PROCEDURE TRANSF_P_AGREGA_DETALLE(cCodGrupoCia_in  IN CHAR,
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
                                  vIndFrac_in      IN CHAR DEFAULT 'N');


  PROCEDURE TRANSF_P_CONFIRMAR(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumNotaEs_in IN CHAR, vIdUsu_in IN VARCHAR2);

  PROCEDURE TRANSF_P_GENERA_GUIA(cGrupoCia_in   IN CHAR,
  										           cCodLocal_in   IN CHAR,
										             cNumNota_in    IN CHAR,
                                 nCantMAxDet_in IN NUMBER,
										             nCantItems_in  IN NUMBER,
										             cIdUsu_in      IN CHAR);

  FUNCTION TRANF_F_CHAR_VAL_ESTADO_PED(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in 	      IN CHAR,
                                  cNumPedTransf_in    IN CHAR,
                                  nSecGrupo_in        IN NUMBER,
                                  cCodLocalOrigen_in  IN CHAR,
                                  cCodLocalDest_in    IN CHAR,
                                  nSecTrans_in        IN NUMBER
                                  )
  RETURN CHAR;

end PTOVENTA_TRANSF_DEL;

/
