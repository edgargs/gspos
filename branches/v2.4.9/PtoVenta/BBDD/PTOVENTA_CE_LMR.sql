--------------------------------------------------------
--  DDL for Package PTOVENTA_CE_LMR
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CE_LMR" is

  -- Author  : LMESIA
  -- Created : 07/08/2006 10:04:56 a.m.

  TYPE FarmaCursor IS REF CURSOR;

  ESTADO_ACTIVO		     CHAR(1):='A';
	ESTADO_INACTIVO		   CHAR(1):='I';
	INDICADOR_SI		     CHAR(1):='S';
	INDICADOR_NO		     CHAR(1):='N';
	TIP_MOV_APERTURA	   CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='A';
	TIP_MOV_CIERRE  	   CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='C';
	TIP_MOV_ARQUEO  	   CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='R';
	COD_TIP_COMP_BOLETA  VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='01';
	COD_TIP_COMP_FACTURA VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='02';

  TIP_VB_CAJERO  CE_HIST_VB_MOV_CAJA.TIP_VB_MOV_CAJA%TYPE:='01';
	TIP_VB_QF      CE_HIST_VB_MOV_CAJA.TIP_VB_MOV_CAJA%TYPE:='02';

  TIP_MONEDA_SOLES CHAR(2) := '01';
  TIP_MONEDA_DOLARES CHAR(2) := '02';

  COD_CUADRATURA_DEFICIT_CAJERO CE_CUADRATURA.COD_CUADRATURA%TYPE:='010';
  COD_CUADRATURA_DEFICIT_QF     CE_CUADRATURA.COD_CUADRATURA%TYPE:='022';
  COD_CUADRATURA_DEL_PERDIDO     CE_CUADRATURA.COD_CUADRATURA%TYPE:='023';


/************************CIERRE DE TURNO*****************************/

  --Descripcion: Busca Cajas Asignadas a un Usuario para un dia de Venta.
  --Fecha       Usuario	  Comentario
  --07/08/2006  LMESIA    Creación
  FUNCTION CE_BUSCA_CAJAS_USU_DIAVENTA(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in	   IN CHAR,
                                       cDiaVenta_in    IN CHAR,
                                       cUsuCaja_in     IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Busca Turno de la Caja Asignada a un Usuario para un dia de Venta.
  --Fecha       Usuario	  Comentario
  --07/08/2006  LMESIA    Creación
  FUNCTION CE_LISTA_TURNOS_CAJA_USU_DIA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in	  IN CHAR,
                                        cDiaVenta_in    IN CHAR,
                                        cUsuCaja_in     IN CHAR,
                                        cNumeroCaja_in  IN NUMBER)
    RETURN FarmaCursor;

  --Descripcion: Busca Y Movimiento de Cierre de una Caja asignada a un Usuario en un turno para un dia de Venta.
  --Fecha       Usuario	  Comentario
  --07/08/2006  LMESIA    Creación
  FUNCTION CE_VALIDA_DATO_CAJ(cCodGrupoCia_in	IN CHAR,
  		   						          cCodLocal_in	  IN CHAR,
  		   						          cDiaVenta_in    IN CHAR,
 		  						            cUsuCaja_in     IN CHAR,
                              cNumCaja_in     IN NUMBER,
                              cTurnoCaja_in   IN NUMBER)
    RETURN CHAR;

  --Descripcion: Obtiene las fechas de apertura y cierre de un mov de caja.
  --Fecha       Usuario	  Comentario
  --08/08/2006  LMESIA    Creación
  FUNCTION CE_OBTIENE_FEC_APER_CER(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in	     IN CHAR,
                                   cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Obtiene el nombre de un usuario
  --Fecha       Usuario	  Comentario
  --08/08/2006  LMESIA    Creación
  FUNCTION CE_OBTIENE_NOMBRE_USUARIO(cCodGrupoCia_in IN CHAR,
  		   						                 cCodLocal_in	   IN CHAR,
 		  						                   cUsuCaja_in     IN CHAR)
    RETURN CHAR;

  --Descripcion: Lista las formas de pago entregadas en el cierre de caja
  --Fecha       Usuario	  Comentario
  --08/08/2006  LMESIA    Creación
  FUNCTION CE_LISTA_FORMA_PAGO_CIERRE(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in	     IN CHAR,
                                      cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Lista las cuadraturas realizadas en el cierre de caja
  --Fecha       Usuario	  Comentario
  --10/08/2006  LMESIA    Creación
  FUNCTION CE_LISTA_CUADRATURA_CIERRE(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in	     IN CHAR,
                                      cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Guarda el historico de VB DE CAJERO
  --Fecha       Usuario	  Comentario
  --10/08/2006  LMESIA    Creación
  PROCEDURE CE_REGISTRA_HIST_VB_CAJ(cCodGrupoCia_in IN CHAR,
						   	                     cCodLocal_in    IN CHAR,
							                       cSecMovCaja_in  IN CHAR,
                                     cIndVB_in       IN CHAR,
                                     cObsCierreTurno IN CHAR,
							                       cUsuCreaVB_in   IN CHAR);

  --Descripcion: Guarda el historico de VB DE QF
  --Fecha       Usuario	  Comentario
  --10/08/2006  LMESIA    Creación
  PROCEDURE CE_REGISTRA_HIST_VB_QF(cCodGrupoCia_in IN CHAR,
						   	                    cCodLocal_in    IN CHAR,
							                      cSecMovCaja_in  IN CHAR,
                                    cIndVB_in       IN CHAR,
							                      cUsuCreaVB_in   IN CHAR);

  --Descripcion: Obtiene el indicador de VB por tipo bloqueando el registro
  --Fecha       Usuario	  Comentario
  --11/08/2006  LMESIA    Creación
  FUNCTION CE_OBTIENE_IND_VB_FOR_UPDATE(cCodGrupoCia_in IN CHAR,
						   	                         cCodLocal_in    IN CHAR,
							                           cSecMovCaja_in  IN CHAR,
                                         cTipVB_in       IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Actualiza el VB segun el tipo y el indicador
  --Fecha       Usuario	  Comentario
  --11/08/2006  LMESIA    Creación
  PROCEDURE CE_ACTUALIZA_VB(cCodGrupoCia_in  IN CHAR,
   	                        cCodLocal_in     IN CHAR,
	                          cSecMovCaja_in   IN CHAR,
                            cIndVB_in        IN CHAR,
                            cTipVB_in        IN CHAR,
                            cUsuModMovCaj_in IN CHAR,
                            cSecUsuQf in char DEFAULT '000');

  --Descripcion: Obtiene el monto total registrado por el sistema para el mov de caja
  --Fecha       Usuario	  Comentario
  --11/08/2006  LMESIA    Creación
  FUNCTION CE_OBTIENE_MONTO_TOTAL_SISTEMA(cCodGrupoCia_in IN CHAR,
  		   						                      cCodLocal_in	  IN CHAR,
 		  						                        cSecMovCaja_in  IN CHAR)
    RETURN CHAR;

  --Descripcion: EVALUA EL DEFICIT ASUMIDO POR EL CAJERO
  --Fecha       Usuario	  Comentario
  --14/08/2006  LMESIA    Creación
  --11/02/2014  RHERRERA  Modificacion: Se cambio el DELETE de la tabla por
                          --            UPDATE de estado.
  PROCEDURE CE_EVALUA_DEFICIT_ASUMIDO_CAJ(cCodGrupoCia_in  IN CHAR,
  		   						                      cCodLocal_in	   IN CHAR,
 		  						                        cSecMovCaja_in   IN CHAR,
                                          nMontoDeficit_in IN NUMBER,
                                          vIdUsu_in        IN CHAR);

  --Descripcion: EVALUA LA ELIMINACION DEL VB DE CAJERO
  --Fecha       Usuario	  Comentario
  --29/08/2006  LMESIA    Creación
  FUNCTION CE_EVALUA_ELIMINACION_VB_CAJ(cCodGrupoCia_in IN CHAR,
  		   						                    cCodLocal_in	  IN CHAR,
 		  						                      cSecMovCaja_in  IN CHAR)
    RETURN NUMBER;

  --Descripcion: Devuelve el rango de comprobantes trabajados en un cierre de caja
  --Fecha       Usuario	  Comentario
  --13/09/2006  LMESIA    Creación
  FUNCTION CE_COMPROBANTES_VALIDOS_CT(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in	    IN CHAR,
                                      cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Devuelve el indicador si los comprobantes son validos para el cierre de turno
  --Fecha       Usuario	  Comentario
  --13/09/2006  LMESIA    Creación
  FUNCTION CE_IND_COMP_VALIDOS_USUARIO(cCodGrupoCia_in   IN CHAR,
  		   						                   cCodLocal_in	     IN CHAR,
 		  						                     cMovCajaCierre_in IN CHAR)
    RETURN CHAR;

  --Descripcion: Actualiza la informacion de los comprobantes para un cierre de turno
  --             en un movimiento de caja
  --Fecha       Usuario	  Comentario
  --13/09/2006  LMESIA    Creación
  PROCEDURE CE_ACTUALIZA_COMPROBANTES_CT(cCodGrupoCia_in   IN CHAR,
   	                                     cCodLocal_in      IN CHAR,
	                                       cSecMovCaja_in    IN CHAR,
                                         cBoletaIni_in     IN CHAR,
                                         cBoletaFin_in     IN CHAR,
                                         cFacturaIni_in    IN CHAR,
                                         cFacturaFin_in    IN CHAR,
                                         cIndCompValido_in IN CHAR,
                                         cUsuModMovCaj_in  IN CHAR);

  --Descripcion: Devuelve La observacion ingresada para el cierre de turno
  --Fecha       Usuario	  Comentario
  --14/09/2006  LMESIA    Creación
  FUNCTION CE_OBTIENE_OBS_CIERRE_TURNO(cCodGrupoCia_in   IN CHAR,
  		   						                   cCodLocal_in	     IN CHAR,
 		  						                     cMovCajaCierre_in IN CHAR)
    RETURN VARCHAR2;

  --Descripcion: Actualiza la observacion para un cierre de turno en un movimiento de caja
  --Fecha       Usuario	  Comentario
  --14/09/2006  LMESIA    Creación
  PROCEDURE CE_ACT_OBSERV_CIERRE_TURNO(cCodGrupoCia_in    IN CHAR,
   	                                   cCodLocal_in       IN CHAR,
	                                     cSecMovCaja_in     IN CHAR,
                                       cObsCierreTurno_in IN CHAR,
                                       cUsuModMovCaj_in   IN CHAR);

  --Descripcion: Actualiza la el indicador de comprobantes validos en un cierre de turno
  --Fecha       Usuario	  Comentario
  --14/09/2006  LMESIA    Creación
  PROCEDURE CE_ACT_IND_COMP_VALIDO_CIERRET(cCodGrupoCia_in   IN CHAR,
   	                                       cCodLocal_in      IN CHAR,
	                                         cSecMovCaja_in    IN CHAR,
                                           cIndCompValido_in IN CHAR,
                                           cUsuModMovCaj_in  IN CHAR);

  --Descripcion: Lista los cajeros que han trabajado en un dia de venta
  --Fecha       Usuario	  Comentario
  --03/10/2006  LMESIA    Creación
  FUNCTION CE_LISTA_CAJEROS_DIA_VENTA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in	  IN CHAR,
                                      cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;

/************************CIERRE DE DIA*****************************/

  --Descripcion: Lista el historico de cierres de dia de venta
  --Fecha       Usuario	  Comentario
  --24/08/2006  LMESIA    Creación
  FUNCTION CE_LISTA_HIST_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in	  IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Crea el registro del cierre de dia de venta
  --Fecha       Usuario	  Comentario
  --24/08/2006  LMESIA    Creación
  PROCEDURE CE_REGISTRA_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
						   	                   cCodLocal_in    IN CHAR,
							                     cCierreDia_in   IN CHAR,
                                   cSecUsuLocal_in IN CHAR,
                                   nTipoCambio_in  IN NUMBER,
							                     cUsuCrea_in     IN CHAR);

  --Descripcion: Valida que no exista la fecha de cierre de dia a registrar
  --Fecha       Usuario	  Comentario
  --24/08/2006  LMESIA    Creación
  FUNCTION CE_VALIDA_REGISTRO_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
  		   						                     cCodLocal_in	   IN CHAR,
 		  						                       cCierreDia_in   IN CHAR)
    RETURN NUMBER;

  --Descripcion: Obtiene la cantidad de cajas aperturadas en un dia de venta
  --Fecha       Usuario	  Comentario
  --24/08/2006  LMESIA    Creación
  FUNCTION CE_OBTIENE_CAJ_APERTURADAS_DIA(cCodGrupoCia_in IN CHAR,
  		   						                      cCodLocal_in	  IN CHAR,
 		  						                        cCierreDia_in   IN CHAR)
    RETURN NUMBER;

  --Descripcion: Valida la cantidad de cajas cerradas con VB de cajero en un dia de venta
  --Fecha       Usuario	  Comentario
  --25/08/2006  LMESIA    Creación
  FUNCTION CE_VALIDA_CAJA_CON_VB_CAJERO(cCodGrupoCia_in IN CHAR,
  		   						                    cCodLocal_in	  IN CHAR,
 		  						                      cCierreDia_in   IN CHAR)
    RETURN NUMBER;

  --Descripcion: Lista el consolidado de formas de pago entrega de un dia de venta
  --Fecha       Usuario	  Comentario
  --25/08/2006  LMESIA    Creación
  FUNCTION CE_CONSO_FOR_PAG_ENT_CIER_DIA(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in	   IN CHAR,
                                         cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Lista el consolidado de cuadraturas de un dia de venta
  --Fecha       Usuario	  Comentario
  --25/08/2006  LMESIA    Creación
  FUNCTION CE_CONSO_CUADRATURA_CIER_DIA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in	  IN CHAR,
                                        cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Lista el consolidado de efectivo recaudado de un dia de venta
  --Fecha       Usuario	  Comentario
  --25/08/2006  LMESIA    Creación
  FUNCTION CE_CONSO_EFEC_RECAUDADO_CIERRE(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in	   IN CHAR,
                                          cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Obtiene el monto total registrado por el sistema por cierre dia venta
  --Fecha       Usuario	  Comentario
  --28/08/2006  LMESIA    Creación
  FUNCTION CE_MONTO_TOTAL_SIST_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
  		   						                      cCodLocal_in	  IN CHAR,
 		  						                        cCierreDia_in   IN CHAR)
    RETURN CHAR;

  --Descripcion: Lista efectivo rendido de cierre de dia
  --Fecha       Usuario	  Comentario
  --28/08/2006  LMESIA    Creación
  FUNCTION CE_CONSO_EFEC_RENDIDO_CIERRE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in	  IN CHAR,
                                        cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Obtiene el indicador de VB de cierre de dia bloqueando el registro
  --Fecha       Usuario	  Comentario
  --28/08/2006  LMESIA    Creación
  FUNCTION CE_IND_VB_CIERRE_DIA_FORUPDATE(cCodGrupoCia_in IN CHAR,
						   	                          cCodLocal_in    IN CHAR,
							                            cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Actualiza el VB de Cierre de Dia segun el indicador
  --Fecha       Usuario	  Comentario
  --28/08/2006  LMESIA    Creación
  PROCEDURE CE_ACTUALIZA_VB_CIERRE_DIA(cCodGrupoCia_in     IN CHAR,
   	                                   cCodLocal_in        IN CHAR,
	                                     cCierreDia_in       IN CHAR,
                                       cIndVBCierreDia_in  IN CHAR,
                                       cDescObs_in         IN CHAR,
                                       cSecUsuVB_in        IN CHAR,
                                       cUsuModCierreDia_in IN CHAR);

  --Descripcion: Guarda el historico de VB de Cierre de Dia
  --Fecha       Usuario	  Comentario
  --28/08/2006  LMESIA    Creación
  PROCEDURE CE_REGISTRA_HIST_VB_CIERRE(cCodGrupoCia_in      IN CHAR,
						   	                       cCodLocal_in         IN CHAR,
							                         cCierreDia_in        IN CHAR,
                                       cIndVBCierreDia_in   IN CHAR,
                                       cSecUsuCrea_in       IN CHAR,
                                       cSecUsuVB_in         IN CHAR,
                                       cDescObs_in          IN CHAR,
                                       cTipoCambio_in       IN NUMBER,
							                         cUsuCreaCierreDia_in IN CHAR);

  --Descripcion: Actualiza Informacion del historico VB Cierre de Dia
  --Fecha       Usuario	  Comentario
  --29/08/2006  LMESIA    Creación
  PROCEDURE CE_ACT_INFO_HIST_VB_CIER_DIA(cCodGrupoCia_in     IN CHAR,
   	                                     cCodLocal_in        IN CHAR,
	                                       cCierreDia_in       IN CHAR,
                                         cSecUsuVB_in        IN CHAR,
                                         cDescMotivo_in      IN CHAR);

  --Descripcion: Lista las series por tipo de documento para la validacion de comprobantes
  --Fecha       Usuario	  Comentario
  --12/09/2006  LMESIA    Creación
  FUNCTION CE_LISTA_SERIES_TIP_DOC(cCodGrupoCia_in IN CHAR,
						   	                   cCodLocal_in    IN CHAR,
							                     cTipDoc_in      IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Obtiene la observacion y que persona creo o dio VB al cierre de dia
  --Fecha       Usuario	  Comentario
  --20/09/2006  LMESIA    Creación
  FUNCTION CE_OBTIENE_OBS_USU_VB_CD(cCodGrupoCia_in IN CHAR,
  		   						                cCodLocal_in	  IN CHAR,
 		  						                  cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Devuelve el indicador si los comprobantes son validos para el cierre de dia
  --Fecha       Usuario	  Comentario
  --21/09/2006  LMESIA    Creación
  FUNCTION CE_IND_COMP_VALIDOS_DIA(cCodGrupoCia_in IN CHAR,
  		   						               cCodLocal_in	   IN CHAR,
 		  						                 cCierreDia_in   IN CHAR)
    RETURN CHAR;

  --Descripcion: Devuelve el rango de comprobantes trabajados en un cierre de dia
  --Fecha       Usuario	  Comentario
  --21/09/2006  LMESIA    Creación
  FUNCTION CE_COMPROBANTES_VALIDOS_CD(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in	  IN CHAR,
                                      cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Actualiza la informacion de los comprobantes para un cierre de dia
  --Fecha       Usuario	  Comentario
  --21/09/2006  LMESIA    Creación
  PROCEDURE CE_ACTUALIZA_COMPROBANTES_CD(cCodGrupoCia_in   IN CHAR,
   	                                     cCodLocal_in      IN CHAR,
	                                       cCierreDia_in     IN CHAR,
                                         cBoletaIni_in     IN CHAR,
                                         cBoletaFin_in     IN CHAR,
                                         cFacturaIni_in    IN CHAR,
                                         cFacturaFin_in    IN CHAR,
                                         cIndCompValido_in IN CHAR,
                                         cUsuModCD_in      IN CHAR);

  --Descripcion: Actualiza la el indicador de comprobantes validos en un cierre de dia
  --Fecha       Usuario	  Comentario
  --21/09/2006  LMESIA    Creación
  PROCEDURE CE_ACT_IND_COMP_VALIDO_CIERRED(cCodGrupoCia_in   IN CHAR,
   	                                       cCodLocal_in      IN CHAR,
	                                         cCierreDia_in     IN CHAR,
                                           cIndCompValido_in IN CHAR,
                                           cUsuModCD_in      IN CHAR);

  --Descripcion: EVALUA EL DEFICIT ASUMIDO POR EL QF
  --Fecha       Usuario	  Comentario
  --21/08/2006  LMESIA    Creación
  --14/02/2014  RHERRERA  Modificacion: Se cambio el DELETE de la TABLA por
                          --            UPDATE de estado.
  PROCEDURE CE_EVALUA_DEFICIT_ASUMIDO_QF(cCodGrupoCia_in  IN CHAR,
  		   						                     cCodLocal_in	    IN CHAR,
 		  						                       cCierreDia_in    IN CHAR,
                                         nMontoDeficit_in IN NUMBER,
                                         vSecUsuLocal_in  IN CHAR,
                                         vIdUsu_in        IN CHAR);

  --Descripcion: Valida la cantidad de cajas cerradas con VB de QF en un dia de venta
  --Fecha       Usuario	  Comentario
  --04/10/2006  LMESIA    Creación
  FUNCTION CE_VALIDA_CAJA_CON_VB_QF(cCodGrupoCia_in IN CHAR,
  		   						                cCodLocal_in	  IN CHAR,
 		  						                  cCierreDia_in   IN CHAR)
    RETURN NUMBER;

  --Descripcion: Valida existencia de VB en un cierre de dia
  --Fecha       Usuario	  Comentario
  --04/10/2006  LMESIA    Creación
  FUNCTION CE_VALIDA_CIERRE_DIA_CON_VB(cCodGrupoCia_in IN CHAR,
  		   						                   cCodLocal_in	   IN CHAR,
 		  						                     cCierreDia_in   IN CHAR)
    RETURN NUMBER;

--Descripcion: Lista los tipos de comprobantes de la cadena
  --Fecha       Usuario	  Comentario
  --06/02/2007  LMESIA    Creación
  FUNCTION CE_LISTA_TIP_COMP(cCodGrupoCia_in IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Lista los rangos de comprobantes de un mov de caja
  --Fecha       Usuario	  Comentario
  --06/02/2007  LMESIA    Creación
  FUNCTION CE_LISTA_RANGO_COMP_MOV_CAJ(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in	     IN CHAR,
                                       cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Lista los rangos de comprobantes de un cierre de dia
  --Fecha       Usuario	  Comentario
  --06/02/2007  LMESIA    Creación
  FUNCTION CE_LISTA_RANGO_COMP_CIE_DIA(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in	   IN CHAR,
                                       cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: obtiene los rangos de comprobantes correctos por mov caja
  --Fecha       Usuario	  Comentario
  --06/02/2007  LMESIA    Creación
  FUNCTION CE_OBTIENE_RANGO_COMP_MOV_CAJ(cCodGrupoCia_in   IN CHAR,
                                         cCodLocal_in	     IN CHAR,
                                         cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: obtiene los rangos de comprobantes correctos por cierre dia
  --Fecha       Usuario	  Comentario
  --06/02/2007  LMESIA    Creación
  FUNCTION CE_OBTIENE_RANGO_COMP_CIE_DIA(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in	   IN CHAR,
                                         cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: elimina un rango de comprobantes en un mov de caja
  --Fecha       Usuario	  Comentario
  --07/02/2007  LMESIA    Creación
  PROCEDURE CE_ELIMINA_RANGO_COMP_MOV_CAJ(cCodGrupoCia_in	IN CHAR,
  		   						                      cCodLocal_in	  IN CHAR,
                                          cSecMovCaja_in  IN CHAR,
                                          cTipComp_in     IN CHAR,
                                          cNumSerie_in    IN CHAR);

  --Descripcion: elimina un rango de comprobantes en un cierre dia
  --Fecha       Usuario	  Comentario
  --07/02/2007  LMESIA    Creación
  PROCEDURE CE_ELIMINA_RANGO_COMP_CIE_DIA(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in	  IN CHAR,
                                          cCierreDia_in   IN CHAR,
                                          cTipComp_in     IN CHAR,
                                          cNumSerie_in    IN CHAR);

  --Descripcion: inserta un rango de comprobantes en un mov de caja
  --Fecha       Usuario	  Comentario
  --07/02/2007  LMESIA    Creación
  PROCEDURE CE_INSERTA_RANGO_COMP_MOV_CAJ(cCodGrupoCia_in	IN CHAR,
  		   						                      cCodLocal_in	  IN CHAR,
                                          cSecMovCaja_in  IN CHAR,
                                          cTipComp_in     IN CHAR,
                                          cNumSerie_in    IN CHAR,
                                          cRangoIni_in    IN CHAR,
                                          cRangoFin_in    IN CHAR,
                                          cUsuCrea_in     IN CHAR);

  --Descripcion: inserta un rango de comprobantes en un cierre dia
  --Fecha       Usuario	  Comentario
  --07/02/2007  LMESIA    Creación
  PROCEDURE CE_INSERTA_RANGO_COMP_CIE_DIA(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in	  IN CHAR,
                                          cCierreDia_in   IN CHAR,
                                          cTipComp_in     IN CHAR,
                                          cNumSerie_in    IN CHAR,
                                          cRangoIni_in    IN CHAR,
                                          cRangoFin_in    IN CHAR,
                                          cUsuCrea_in     IN CHAR,
                                          cMontoMin_in    IN char,
                                          cMontoMax_in    IN char
                                          );

 --Descripcion: obtiene típo de caja para cierre turno
 --Fecha       Usuario	  Comentario
 --02/02/2009  JCORTEZ    Creación
 FUNCTION GET_TIPO_CAJA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
 RETURN VARCHAR2;

 --Descripcion: Valda visa manual rendido
 --Fecha       Usuario	  Comentario
 --02/02/2009  JQUISPE    Creación
 FUNCTION CE_F_VALIDA_VISA_MANUAL(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in	  IN CHAR,
                                          cCierreDia_in   IN CHAR
                                         )
                                          RETURN CHAR;

 --Descripcion: Aprueba los sobres de cierre de turno con el codigo de QF
 --Fecha       Usuario	  Comentario
 --11/08/2010  ASOSA    Creación
PROCEDURE CE_APROBAR_SOBRES(cCodGrupoCia_in  IN CHAR,
   	                        cCodLocal_in     IN CHAR,
	                          cSecMovCaja_in   IN CHAR,
                            cUsuModMovCaj_in IN CHAR,
                            cSecUsuQf in char DEFAULT '000');
FUNCTION CE_F_ANUL_PEND_REGULARIZAR(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in	  IN CHAR,
                                    cFechCierreDia_in in char)
    RETURN FarmaCursor;

FUNCTION CE_GET_MONTO_COMP(cCodGrupoCia_in IN CHAR,
  		   						       cCodLocal_in	   IN CHAR,
                           cTipComp_in 	   IN CHAR,
                           cCierreDia_in   IN CHAR,
                           cTipMonto       IN CHAR,
                           cSerieComp      IN CHAR
                           )
    RETURN CHAR;

 FUNCTION LISTA_HIST_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in	  IN CHAR)
    RETURN FarmaCursor;

FUNCTION CE_GET_IMG_BOLETA(cCodGrupoCia_in IN CHAR,
  		   						       cCodLocal_in	   IN CHAR)
    RETURN varchar2;

FUNCTION CE_GET_IMG_FACTURA(cCodGrupoCia_in IN CHAR,
  		   						        cCodLocal_in	   IN CHAR)
    RETURN varchar2;

  --Descripcion: Muestra cajas sin cerrar
  --Fecha       Usuario	  Comentario
  --06/02/2014  ERIOS     Creacion
  FUNCTION CE_MUESTRA_CAJA_APER(cCodGrupoCia_in IN CHAR,
  		   						                cCodLocal_in	  IN CHAR,
 		  						                  cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;

end PTOVENTA_CE_LMR;

/
