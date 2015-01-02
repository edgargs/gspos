CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CE_LMR" is

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

  C_NOTA_CRED CHAR(2) := '04';

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

 --Descripcion: Obtiene los comprobantes Electronicos
 --Fecha       Usuario	  Comentario
 --07/10/2014  RHERRERA    Creación
FUNCTION CE_OBTIENE_RANGO_COMP_ELECT(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in	   IN CHAR,
                                         cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;


end PTOVENTA_CE_LMR;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CE_LMR" is

/************************CIERRE DE TURNO*****************************/

  /****************************************************************************/
  FUNCTION CE_BUSCA_CAJAS_USU_DIAVENTA(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cDiaVenta_in    IN CHAR,
                                       cUsuCaja_in     IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT MOV_CAJA.NUM_CAJA_PAGO
         FROM   CE_MOV_CAJA MOV_CAJA
         WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
         AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cDiaVenta_in,'dd/MM/yyyy')
         AND    MOV_CAJA.SEC_USU_LOCAL = cUsuCaja_in
         AND    MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_CIERRE;

    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_LISTA_TURNOS_CAJA_USU_DIA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        cDiaVenta_in    IN CHAR,
                                        cUsuCaja_in     IN CHAR,
                                        cNumeroCaja_in  IN NUMBER)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT MOV_CAJA.NUM_TURNO_CAJA || 'Ã' ||
                MOV_CAJA.NUM_TURNO_CAJA
         FROM   CE_MOV_CAJA MOV_CAJA
         WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
         AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cDiaVenta_in,'dd/MM/yyyy')
         AND    MOV_CAJA.SEC_USU_LOCAL = cUsuCaja_in
         AND    MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_CIERRE
         AND    MOV_CAJA.NUM_CAJA_PAGO = cNumeroCaja_in;

    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_VALIDA_DATO_CAJ(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cDiaVenta_in    IN CHAR,
                               cUsuCaja_in     IN CHAR,
                              cNumCaja_in     IN NUMBER,
                              cTurnoCaja_in   IN NUMBER)
    RETURN CHAR
  IS
    v_cSecMovCaja  CHAR(10);
    v_cIndVBCajero CHAR(1);
    v_cIndVBQF     CHAR(1);
    v_cIndicador   CHAR(1);
    v_cRpta        CHAR(11);
  BEGIN
       SELECT MOV_CAJA.SEC_MOV_CAJA,
              MOV_CAJA.IND_VB_CAJERO,
              MOV_CAJA.IND_VB_QF
       INTO   v_cSecMovCaja,
              v_cIndVBCajero,
              v_cIndVBQF
       FROM   CE_MOV_CAJA MOV_CAJA
       WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cDiaVenta_in,'dd/MM/yyyy')
       AND    MOV_CAJA.SEC_USU_LOCAL = cUsuCaja_in
       AND    MOV_CAJA.NUM_CAJA_PAGO = cNumCaja_in
       AND    MOV_CAJA.NUM_TURNO_CAJA = cTurnoCaja_in
       AND    MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_CIERRE;

       IF v_cIndVBCajero = INDICADOR_NO THEN
          v_cIndicador := '1';--existe mov de cierre sin VB
       ELSIF v_cIndVBCajero = INDICADOR_SI AND v_cIndVBQF = INDICADOR_NO THEN
          v_cIndicador := '2';--existe mov de cierre con VB de Cajero pero sin VB de QF
       ELSIF v_cIndVBCajero = INDICADOR_SI AND v_cIndVBQF = INDICADOR_SI THEN
          v_cIndicador := '3';--existe mov de cierre con VB de Cajero y con VB de QF
       END IF;

       v_cRpta := v_cIndicador || v_cSecMovCaja;
     RETURN v_cRpta;
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
          v_cIndicador := '4';--NO SE ENCONTRO MOVIMIENTO PARA LOS DATOS INGRESADOS
          v_cRpta := v_cIndicador;
     RETURN v_cRpta;
 END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_FEC_APER_CER(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in       IN CHAR,
                                   cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT MOV_CAJA.TIP_MOV_CAJA || 'Ã' ||
               TO_CHAR(MOV_CAJA.FEC_CREA_MOV_CAJA,'dd/MM/yyyy HH12:MI:SS AM')
        FROM   CE_MOV_CAJA MOV_CAJA
        WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
        AND    MOV_CAJA.SEC_MOV_CAJA = cMovCajaCierre_in
        UNION
        SELECT MOV_CAJA.TIP_MOV_CAJA || 'Ã' ||
               TO_CHAR(MOV_CAJA.FEC_CREA_MOV_CAJA,'dd/MM/yyyy HH12:MI:SS AM')
        FROM   CE_MOV_CAJA MOV_CAJA
        WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
        AND    MOV_CAJA.SEC_MOV_CAJA IN (SELECT MOV_CAJA.SEC_MOV_CAJA_ORIGEN
                                         FROM   CE_MOV_CAJA MOV_CAJA
                                         WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
                                         AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
                                         AND    MOV_CAJA.SEC_MOV_CAJA = cMovCajaCierre_in);

    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_NOMBRE_USUARIO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cUsuCaja_in     IN CHAR)
    RETURN CHAR
  IS
    v_cNombreUsu CHAR(100);
  BEGIN
       SELECT USU.NOM_USU || ' ' ||
              USU.APE_PAT || ' ' ||
              USU.APE_MAT
       INTO   v_cNombreUsu
       FROM   PBL_USU_LOCAL USU
       WHERE  USU.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    USU.COD_LOCAL = cCodLocal_in
       AND    USU.SEC_USU_LOCAL = cUsuCaja_in;

     RETURN v_cNombreUsu;
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
          v_cNombreUsu := '';--NO SE ENCONTRO EL USUARIO
     RETURN v_cNombreUsu;
  END;

  /****************************************************************************/
  FUNCTION CE_LISTA_FORMA_PAGO_CIERRE(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in       IN CHAR,
                                      cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT FP.DESC_CORTA_FORMA_PAGO || 'Ã' ||
               DECODE(FPE.TIP_MONEDA,'02','DOLARES','SOLES') || 'Ã' ||
               TO_CHAR(SUM(FPE.MON_ENTREGA),'999,990.00') || 'Ã' ||
               TO_CHAR(SUM(FPE.MON_ENTREGA_TOTAL),'999,990.00') || 'Ã' ||
               FPE.COD_FORMA_PAGO
        FROM   CE_FORMA_PAGO_ENTREGA FPE,
               VTA_FORMA_PAGO FP
        WHERE  FPE.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    FPE.COD_LOCAL = cCodLocal_in
        AND    FPE.SEC_MOV_CAJA = cMovCajaCierre_in
        AND    FPE.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
        AND    FPE.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
        AND    FPE.EST_FORMA_PAGO_ENT = 'A'
        GROUP BY FP.DESC_CORTA_FORMA_PAGO,FPE.TIP_MONEDA,FPE.COD_FORMA_PAGO;

    RETURN curCE;
  END;
  /****************************************************************************/
  FUNCTION CE_LISTA_CUADRATURA_CIERRE(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in       IN CHAR,
                                      cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT CUADRATURA_CAJA.COD_CUADRATURA || 'Ã' ||
               NVL(CUADRATURA.DESC_CUADRATURA,' ') || 'Ã' ||
               TO_CHAR(SUM(CUADRATURA_CAJA.MON_TOTAL) * CUADRATURA.VAL_SIGNO,'999,999,990.00')
        FROM   CE_CUADRATURA_CAJA CUADRATURA_CAJA,
               CE_CUADRATURA CUADRATURA
        WHERE  CUADRATURA_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CUADRATURA_CAJA.COD_LOCAL = cCodLocal_in
        AND    CUADRATURA_CAJA.SEC_MOV_CAJA = cMovCajaCierre_in
        AND    CUADRATURA_CAJA.COD_GRUPO_CIA = CUADRATURA.COD_GRUPO_CIA
        AND    CUADRATURA_CAJA.COD_CUADRATURA = CUADRATURA.COD_CUADRATURA
        AND    CUADRATURA_CAJA.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO

        GROUP BY CUADRATURA_CAJA.COD_CUADRATURA,
                 CUADRATURA.DESC_CUADRATURA,
                 CUADRATURA.VAL_SIGNO;

    RETURN curCE;
  END;

  /****************************************************************************/
  PROCEDURE CE_REGISTRA_HIST_VB_CAJ(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                     cSecMovCaja_in  IN CHAR,
                                     cIndVB_in       IN CHAR,
                                     cObsCierreTurno IN CHAR,
                                     cUsuCreaVB_in   IN CHAR)
  IS
    v_nSecHistVB CE_HIST_VB_MOV_CAJA.SEC_HIST_VB_MOV_CAJA%TYPE;

    CURSOR formasPagoEntrega IS
           SELECT *
          FROM   CE_FORMA_PAGO_ENTREGA F
          WHERE  F.COD_GRUPO_CIA = cCodGrupoCia_in
          AND     F.COD_LOCAL = cCodLocal_in
          AND     F.SEC_MOV_CAJA = cSecMovCaja_in
          AND    F.EST_FORMA_PAGO_ENT = 'A'
          ORDER BY F.SEC_FORMA_PAGO_ENTREGA;

    CURSOR cuadraturasEntrega IS
           SELECT *
          FROM   CE_CUADRATURA_CAJA C
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND     C.COD_LOCAL = cCodLocal_in
          AND     C.SEC_MOV_CAJA = cSecMovCaja_in
          AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
          ORDER BY C.SEC_CUADRATURA_CAJA;
  BEGIN
      SELECT COUNT(*) + 1
      INTO   v_nSecHistVB
      FROM   CE_HIST_VB_MOV_CAJA A
      WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    A.COD_LOCAL = cCodLocal_in
      AND    A.SEC_MOV_CAJA = cSecMovCaja_in;

      INSERT INTO CE_HIST_VB_MOV_CAJA
                   (COD_GRUPO_CIA,COD_LOCAL,SEC_MOV_CAJA,SEC_HIST_VB_MOV_CAJA,
                    FEC_VB_MOV_CAJA,IND_VB_MOV_CAJA,TIP_VB_MOV_CAJA,
                    USU_CREA_VB_MOV_CAJA,FEC_CREA_VB_MOV_CAJA,DESC_OBS_CIERRE_TURNO_HIST)
             VALUES(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,v_nSecHistVB,
                    SYSDATE, cIndVB_in, TIP_VB_CAJERO,
                    cUsuCreaVB_in, SYSDATE,NULL);

      --dbms_output.put_line('v_cIndGraboComp: ' || v_cIndGraboComp );

      IF cIndVB_in = INDICADOR_NO THEN
         UPDATE CE_HIST_VB_MOV_CAJA
            SET DESC_OBS_CIERRE_TURNO_HIST = cObsCierreTurno
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND   COD_LOCAL = cCodLocal_in
          AND   SEC_MOV_CAJA = cSecMovCaja_in
          AND   SEC_HIST_VB_MOV_CAJA = v_nSecHistVB;

         FOR formasPagoEntrega_rec IN formasPagoEntrega
         LOOP
             INSERT INTO CE_HIST_FORMA_PAGO_ENTREGA
                           (COD_GRUPO_CIA,COD_LOCAL,
                            SEC_MOV_CAJA,SEC_HIST_VB_MOV_CAJA,
                            SEC_FORMA_PAGO_ENTREGA,COD_FORMA_PAGO_HIST,
                            TIP_MONEDA_HIST,CANT_VOUCHER_HIST,
                            MON_ENTREGA_HIST,MON_ENTREGA_TOTAL_HIST,
                            USU_CREA_HIST_FORMA_PAGO_ENT,FEC_CREA_HIST_FORMA_PAGO_ENT)
                    VALUES (formasPagoEntrega_rec.COD_GRUPO_CIA, formasPagoEntrega_rec.COD_LOCAL,
                            formasPagoEntrega_rec.SEC_MOV_CAJA, v_nSecHistVB,
                            formasPagoEntrega_rec.SEC_FORMA_PAGO_ENTREGA, formasPagoEntrega_rec.COD_FORMA_PAGO,
                            formasPagoEntrega_rec.TIP_MONEDA, formasPagoEntrega_rec.CANT_VOUCHER,
                            formasPagoEntrega_rec.MON_ENTREGA, formasPagoEntrega_rec.MON_ENTREGA_TOTAL,
                            cUsuCreaVB_in, SYSDATE);
         END LOOP;
         FOR cuadraturasEntrega_rec IN cuadraturasEntrega
         LOOP
             INSERT INTO CE_HIST_CUADRATURA_CAJA
                           (COD_GRUPO_CIA,COD_LOCAL,
                            SEC_MOV_CAJA,SEC_HIST_VB_MOV_CAJA,
                            SEC_CUADRATURA_CAJA,COD_CUADRATURA,
                            NUM_SERIE_LOCAL_HIST,TIP_COMP_HIST,
                            NUM_COMP_PAGO_HIST,MON_MONEDA_ORIGEN_HIST,
                            MON_TOTAL_HIST,MON_VUELTO_HIST,
                            TIP_DINERO_HIST,TIP_MONEDA_HIST,
                            SERIE_BILLETE_HIST,COD_FORMA_PAGO_HIST,
                            USU_CREA_CUADRA_CAJ_HIST,FEC_CREA_CUADRA_CAJ_HIST)
                    VALUES (cuadraturasEntrega_rec.COD_GRUPO_CIA, cuadraturasEntrega_rec.COD_LOCAL,
                            cuadraturasEntrega_rec.SEC_MOV_CAJA, v_nSecHistVB,
                            cuadraturasEntrega_rec.SEC_CUADRATURA_CAJA, cuadraturasEntrega_rec.COD_CUADRATURA,
                            cuadraturasEntrega_rec.NUM_SERIE_LOCAL, cuadraturasEntrega_rec.TIP_COMP,
                            cuadraturasEntrega_rec.NUM_COMP_PAGO, cuadraturasEntrega_rec.MON_MONEDA_ORIGEN,
                            cuadraturasEntrega_rec.MON_TOTAL, cuadraturasEntrega_rec.MON_VUELTO,
                            cuadraturasEntrega_rec.TIP_DINERO, cuadraturasEntrega_rec.TIP_MONEDA,
                            cuadraturasEntrega_rec.SERIE_BILLETE, cuadraturasEntrega_rec.COD_FORMA_PAGO,
                            cUsuCreaVB_in, SYSDATE);
         END LOOP;
      END IF;
  END;

  /****************************************************************************/
  PROCEDURE CE_REGISTRA_HIST_VB_QF(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                    cSecMovCaja_in  IN CHAR,
                                    cIndVB_in       IN CHAR,
                                    cUsuCreaVB_in   IN CHAR)
  IS
    v_nSecHistVB CE_HIST_VB_MOV_CAJA.SEC_HIST_VB_MOV_CAJA%TYPE;

  BEGIN
      SELECT COUNT(*) + 1
      INTO   v_nSecHistVB
      FROM   CE_HIST_VB_MOV_CAJA A
      WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    A.COD_LOCAL = cCodLocal_in
      AND    A.SEC_MOV_CAJA = cSecMovCaja_in;

      INSERT INTO CE_HIST_VB_MOV_CAJA
                   (COD_GRUPO_CIA,COD_LOCAL,SEC_MOV_CAJA,SEC_HIST_VB_MOV_CAJA,
                    FEC_VB_MOV_CAJA,IND_VB_MOV_CAJA,TIP_VB_MOV_CAJA,
                    USU_CREA_VB_MOV_CAJA,FEC_CREA_VB_MOV_CAJA,DESC_OBS_CIERRE_TURNO_HIST)
             VALUES(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,v_nSecHistVB,
                    SYSDATE, cIndVB_in, TIP_VB_QF,
                    cUsuCreaVB_in, SYSDATE,NULL);

  END;

  /* ************************************************************************* */
  FUNCTION CE_OBTIENE_IND_VB_FOR_UPDATE(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                         cSecMovCaja_in  IN CHAR,
                                         cTipVB_in       IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    IF cTipVB_in = TIP_VB_CAJERO THEN
       OPEN curCE FOR
            SELECT MOV_CAJA.IND_VB_CAJERO
            FROM   CE_MOV_CAJA MOV_CAJA
            WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
            AND    MOV_CAJA.SEC_MOV_CAJA = cSecMovCaja_in FOR UPDATE;


    ELSIF cTipVB_in = TIP_VB_QF THEN
       OPEN curCE FOR
            SELECT MOV_CAJA.IND_VB_QF
            FROM   CE_MOV_CAJA MOV_CAJA
            WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
            AND    MOV_CAJA.SEC_MOV_CAJA = cSecMovCaja_in FOR UPDATE;

    END IF;
    RETURN curCE;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ACTUALIZA_VB(cCodGrupoCia_in  IN CHAR,
                             cCodLocal_in     IN CHAR,
                            cSecMovCaja_in   IN CHAR,
                            cIndVB_in        IN CHAR,
                            cTipVB_in        IN CHAR,
                            cUsuModMovCaj_in IN CHAR,
                            cSecUsuQf in char DEFAULT '000')
  IS
    cIndicador VARCHAR2(1);
    indProsegur CHAR(1); --ASOSA, 02.06.2010
  BEGIN
       IF cTipVB_in = TIP_VB_CAJERO THEN

          UPDATE CE_MOV_CAJA MOV_CAJA
          SET MOV_CAJA.Usu_Mod_Mov_Caja = cUsuModMovCaj_in,
              MOV_CAJA.Fec_Mod_Mov_Caja = SYSDATE,
              MOV_CAJA.Ind_Vb_Cajero = cIndVB_in,
              MOV_CAJA.FEC_CIERRE_TURNO_CAJA = DECODE(cIndVB_in,INDICADOR_SI,SYSDATE,NULL)
          WHERE MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
            AND MOV_CAJA.COD_LOCAL = cCodLocal_in
            AND MOV_CAJA.SEC_MOV_CAJA= cSecMovCaja_in;

       ELSIF cTipVB_in = TIP_VB_QF THEN

           UPDATE CE_MOV_CAJA MOV_CAJA
           SET MOV_CAJA.Usu_Mod_Mov_Caja = cUsuModMovCaj_in,
               MOV_CAJA.Fec_Mod_Mov_Caja = SYSDATE,
               MOV_CAJA.Ind_Vb_Qf = cIndVB_in
           WHERE MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
             AND MOV_CAJA.COD_LOCAL = cCodLocal_in
             AND MOV_CAJA.SEC_MOV_CAJA= cSecMovCaja_in;

           --Indicador de sobres
           --SELECT TRIM(NVL(A.DESC_CORTA,' ')) INTO cIndicador
           SELECT TRIM(NVL(A.Llave_Tab_Gral,'N')) INTO cIndicador --ASOSA, 02.06.2010
           FROM PBL_TAB_GRAL A
           WHERE A.ID_TAB_GRAL=317;
           -- AND a.cod_grupo_cia=cCodGrupoCia_in;

           --Indicador de Portavalor
           --ASOSA, 02.06.2010
           --LLEIVA 10-Feb-2014 Modificación para consulta a la tabla PBL_ETV_LOCAL
           --SELECT nvl(x.ind_prosegur,'N') INTO indProsegur
           --FROM pbl_local x
           --WHERE x.cod_grupo_cia=cCodGrupoCia_in
           --AND x.cod_local=cCodLocal_in;
           select DECODE(COUNT(*),0,'N','S') INTO indProsegur
           from pbl_etv_local
           where COD_GRUPO_CIA = cCodGrupoCia_in AND
                 COD_LOCAL = cCodLocal_in AND
                 EST_ETV_LOCAL = 'A';

           IF(cIndicador='S' OR indProsegur='S')THEN

               --Se aprueban automaticamente los sobres del turno.
               UPDATE CE_SOBRE A
               SET A.ESTADO='A',
                   A.FEC_MOD_SOBRE=SYSDATE,
                   A.USU_MOD_SOBRE=cUsuModMovCaj_in,
                   A.sec_usu_qf = cSecUsuQf
               WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
                 AND A.COD_LOCAL=cCodLocal_in
                 AND A.SEC_MOV_CAJA=cSecMovCaja_in --todos los sobres creados en cierre de turno
                 AND A.ESTADO IN ('P'); --ASOSA, 14.06.2010

               UPDATE CE_SOBRE_TMP B
               SET B.ESTADO='A',
                   B.FEC_MOD_SOBRE=SYSDATE,
                   B.USU_MOD_SOBRE=cUsuModMovCaj_in,
                   B.sec_usu_qf = cSecUsuQf
               WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
                 AND B.COD_LOCAL=cCodLocal_in
                 AND B.ESTADO IN ('P') --ASOSA, 14.06.2010
                 AND B.SEC_MOV_CAJA= (SELECT C.SEC_MOV_CAJA_ORIGEN
                                      FROM CE_MOV_CAJA C
                                      WHERE  C.COD_GRUPO_CIA=B.COD_GRUPO_CIA
                                      AND C.COD_LOCAL=B.COD_LOCAL
                                      AND C.SEC_MOV_CAJA=cSecMovCaja_in); --todos los sobres creados en el ingreso parcial de cobro
           END IF;
       END IF;
  END;

  /* ************************************************************************ */
  FUNCTION CE_OBTIENE_MONTO_TOTAL_SISTEMA(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR,
                                           cSecMovCaja_in  IN CHAR)
    RETURN CHAR
  IS
    v_cTotalSistema CHAR(20);
  BEGIN
       SELECT TO_CHAR(NVL(E.MON_TOT,0),'999,990.00')
       INTO   v_cTotalSistema
       FROM   CE_MOV_CAJA E
       WHERE  E.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    E.COD_LOCAL = cCodLocal_in
       AND    E.SEC_MOV_CAJA = cSecMovCaja_in;

     RETURN v_cTotalSistema;
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
          v_cTotalSistema := '0.00';--NO SE ENCONTRO MONTO TOTAL DE SISTEMA
     RETURN v_cTotalSistema;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_EVALUA_DEFICIT_ASUMIDO_CAJ(cCodGrupoCia_in  IN CHAR,
                                           cCodLocal_in     IN CHAR,
                                           cSecMovCaja_in   IN CHAR,
                                          nMontoDeficit_in IN NUMBER,
                                          vIdUsu_in        IN CHAR)
  IS
    v_nSecCuadraturaCaj NUMBER;
  BEGIN
       IF nMontoDeficit_in <> 0 THEN --sirve para eliminar si o si el deficit asumido

          UPDATE CE_CUADRATURA_CAJA  SET
             USU_MOD_CUADRATURA_CAJA= vIdUsu_in ,
             FEC_MOD_CUADRATURA_CAJA = SYSDATE,
             EST_CUADRATURA_CAJA = 'I'
             WHERE    COD_GRUPO_CIA   = cCodGrupoCia_in
                AND   COD_LOCAL       = cCodLocal_in
                AND   SEC_MOV_CAJA    = cSecMovCaja_in
                AND   COD_CUADRATURA  = COD_CUADRATURA_DEFICIT_CAJERO;

        /*      Se evita Eliminar los registros.

          DELETE FROM CE_CUADRATURA_CAJA E
                WHERE E.COD_GRUPO_CIA = cCodGrupoCia_in
                AND   E.COD_LOCAL = cCodLocal_in
                AND   E.SEC_MOV_CAJA = cSecMovCaja_in
                AND   E.COD_CUADRATURA = COD_CUADRATURA_DEFICIT_CAJERO;
        */

       END IF;

       IF nMontoDeficit_in > 0 THEN

          SELECT NVL(MAX(A.SEC_CUADRATURA_CAJA),0) + 1
          INTO   v_nSecCuadraturaCaj
          FROM   CE_CUADRATURA_CAJA A
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND    COD_LOCAL = cCodLocal_in
          AND    SEC_MOV_CAJA = cSecMovCaja_in
         -- AND    A.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
          ;

          INSERT INTO CE_CUADRATURA_CAJA(COD_GRUPO_CIA ,COD_LOCAL ,SEC_MOV_CAJA ,
                                         SEC_CUADRATURA_CAJA, COD_CUADRATURA ,MON_MONEDA_ORIGEN  ,
                                         MON_TOTAL  ,TIP_MONEDA  ,
                                         USU_CREA_CUADRATURA_CAJA)
                                  VALUES(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in,
                                         v_nSecCuadraturaCaj,COD_CUADRATURA_DEFICIT_CAJERO,nMontoDeficit_in,
                                         nMontoDeficit_in,TIP_MONEDA_SOLES,
                                         vIdUsu_in);
       END IF;
  END;

  /* ************************************************************************ */
  FUNCTION CE_EVALUA_ELIMINACION_VB_CAJ(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cSecMovCaja_in  IN CHAR)
    RETURN NUMBER
  IS
    v_nCant NUMBER;
  BEGIN
       SELECT COUNT(1)
       INTO   v_nCant
       FROM   CE_HIST_VB_MOV_CAJA HVBM
       WHERE  HVBM.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    HVBM.COD_LOCAL = cCodLocal_in
       AND    HVBM.SEC_MOV_CAJA = cSecMovCaja_in
       AND    HVBM.IND_VB_MOV_CAJA = INDICADOR_NO
       AND    HVBM.TIP_VB_MOV_CAJA = TIP_VB_CAJERO;

     RETURN v_nCant;
   EXCEPTION
     WHEN OTHERS THEN
          v_nCant := 1;--devolvemos 1 y asi no pasara la validacion
     RETURN v_nCant;
  END;

  /****************************************************************************/

  FUNCTION CE_COMPROBANTES_VALIDOS_CT(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in       IN CHAR,
                                      cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT NVL(MOV_CAJA.NUM_BOLETA_INICIAL,' ') || 'Ã' ||
                NVL(MOV_CAJA.NUM_BOLETA_FINAL,' ') || 'Ã' ||
                NVL(MOV_CAJA.NUM_FACTURA_INICIAL,' ') || 'Ã' ||
                NVL(MOV_CAJA.NUM_FACTURA_FINAL,' ')
         FROM   CE_MOV_CAJA MOV_CAJA
         WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
         AND    MOV_CAJA.SEC_MOV_CAJA = cMovCajaCierre_in;

    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_IND_COMP_VALIDOS_USUARIO(cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in       IN CHAR,
                                        cMovCajaCierre_in IN CHAR)
    RETURN CHAR
  IS
    v_cIndCompValidos CHAR(1);
  BEGIN
       SELECT MOV_CAJA.IND_COMP_VALIDOS
       INTO   v_cIndCompValidos
       FROM   CE_MOV_CAJA MOV_CAJA
       WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND    MOV_CAJA.SEC_MOV_CAJA = cMovCajaCierre_in;

     RETURN v_cIndCompValidos;
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
          v_cIndCompValidos := INDICADOR_NO;--NO SE ENCONTRO EL REGISTRO
     RETURN v_cIndCompValidos;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ACTUALIZA_COMPROBANTES_CT(cCodGrupoCia_in   IN CHAR,
                                          cCodLocal_in      IN CHAR,
                                         cSecMovCaja_in    IN CHAR,
                                         cBoletaIni_in     IN CHAR,
                                         cBoletaFin_in     IN CHAR,
                                         cFacturaIni_in    IN CHAR,
                                         cFacturaFin_in    IN CHAR,
                                         cIndCompValido_in IN CHAR,
                                         cUsuModMovCaj_in  IN CHAR)
  IS
  BEGIN
        UPDATE CE_MOV_CAJA MOV_CAJA SET MOV_CAJA.Usu_Mod_Mov_Caja = cUsuModMovCaj_in, MOV_CAJA.Fec_Mod_Mov_Caja = SYSDATE ,
               MOV_CAJA.NUM_BOLETA_INICIAL = cBoletaIni_in,
               MOV_CAJA.NUM_BOLETA_FINAL = cBoletaFin_in,
               MOV_CAJA.NUM_FACTURA_INICIAL = cFacturaIni_in,
               MOV_CAJA.NUM_FACTURA_FINAL = cFacturaFin_in,
               MOV_CAJA.IND_COMP_VALIDOS = cIndCompValido_in
        WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
        AND    MOV_CAJA.SEC_MOV_CAJA= cSecMovCaja_in;
  END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_OBS_CIERRE_TURNO(cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in       IN CHAR,
                                        cMovCajaCierre_in IN CHAR)
    RETURN VARCHAR2
  IS
    v_cObsCierreTurno VARCHAR2(300);
  BEGIN
       SELECT NVL(MOV_CAJA.DESC_OBS_CIERRE_TURNO,' ')
       INTO   v_cObsCierreTurno
       FROM   CE_MOV_CAJA MOV_CAJA
       WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND    MOV_CAJA.SEC_MOV_CAJA = cMovCajaCierre_in;

     RETURN v_cObsCierreTurno;
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
          v_cObsCierreTurno := ' ';--NO SE ENCONTRO EL REGISTRO
     RETURN v_cObsCierreTurno;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ACT_OBSERV_CIERRE_TURNO(cCodGrupoCia_in    IN CHAR,
                                        cCodLocal_in       IN CHAR,
                                       cSecMovCaja_in     IN CHAR,
                                       cObsCierreTurno_in IN CHAR,
                                       cUsuModMovCaj_in   IN CHAR)
  IS
  BEGIN
        UPDATE CE_MOV_CAJA MOV_CAJA
        SET MOV_CAJA.Usu_Mod_Mov_Caja = cUsuModMovCaj_in,
            MOV_CAJA.Fec_Mod_Mov_Caja = SYSDATE,
            MOV_CAJA.DESC_OBS_CIERRE_TURNO = cObsCierreTurno_in
        WHERE MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
          AND MOV_CAJA.COD_LOCAL = cCodLocal_in
          AND MOV_CAJA.SEC_MOV_CAJA= cSecMovCaja_in;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ACT_IND_COMP_VALIDO_CIERRET(cCodGrupoCia_in   IN CHAR,
                                            cCodLocal_in      IN CHAR,
                                           cSecMovCaja_in    IN CHAR,
                                           cIndCompValido_in IN CHAR,
                                           cUsuModMovCaj_in  IN CHAR)
  IS
  BEGIN
        UPDATE CE_MOV_CAJA MOV_CAJA SET MOV_CAJA.Usu_Mod_Mov_Caja = cUsuModMovCaj_in, MOV_CAJA.Fec_Mod_Mov_Caja = SYSDATE ,
               MOV_CAJA.IND_COMP_VALIDOS = cIndCompValido_in
        WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
        AND    MOV_CAJA.SEC_MOV_CAJA= cSecMovCaja_in;
  END;

  /****************************************************************************/
  FUNCTION CE_LISTA_CAJEROS_DIA_VENTA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT USU_LOCAL.SEC_USU_LOCAL || 'Ã' ||
                NVL(TRIM(USU_LOCAL.NOM_USU || ' ' || USU_LOCAL.APE_PAT || ' ' || USU_LOCAL.APE_MAT),' ')
         FROM   PBL_USU_LOCAL USU_LOCAL
         WHERE  USU_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    USU_LOCAL.COD_LOCAL = cCodLocal_in
         AND    USU_LOCAL.SEC_USU_LOCAL IN (SELECT MOV_CAJA.SEC_USU_LOCAL
                                            FROM   CE_MOV_CAJA MOV_CAJA
                                            WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
                                            AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cCierreDia_in ,'dd/MM/yyyy'));
    RETURN curCE;
  END;

/************************CIERRE DE DIA*****************************/

  /****************************************************************************/
FUNCTION CE_LISTA_HIST_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
    vNumeroDias number;
  BEGIN

 SELECT to_number(nvl(A.LLAVE_TAB_GRAL,'60'))
   INTO vNumeroDias
   FROM PBL_TAB_GRAL A
  WHERE A.ID_TAB_GRAL = 386;


    OPEN curCE FOR
        SELECT
               TO_CHAR(CIERRE.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy') || 'Ã' ||
               NVL(USU_LOCAL.NOM_USU || ' ' || USU_LOCAL.APE_PAT || ' ' || USU_LOCAL.APE_MAT,' ') || 'Ã' ||
               NVL(TO_CHAR(CIERRE.FEC_VB_CIERRE_DIA,'dd/MM/yyyy HH24:MI'),' ') || 'Ã' ||
               NVL(TO_CHAR(CIERRE.FEC_VB_CONTABLE,'dd/MM/yyyy HH24:MI'),' ') || 'Ã' ||--NVL(CIERRE.IND_VB_CONTABLE,' ')|| 'Ã' ||
               NVL(TO_CHAR(CIERRE.FEC_PROCESO,'dd/MM/yyyy HH24:MI'),' ') || 'Ã' ||
               NVL(TO_CHAR(CIERRE.FEC_ARCHIVO,'dd/MM/yyyy HH24:MI'),' ') || 'Ã' ||
               NVL(CIERRE.SEC_USU_LOCAL_CREA,' ') || 'Ã' ||
               CIERRE.IND_VB_CIERRE_DIA || 'Ã' ||
               TO_CHAR(NVL(CIERRE.TIP_CAMBIO_CIERRE_DIA,0),'990.00') || 'Ã' ||
               TO_CHAR(CIERRE.FEC_CIERRE_DIA_VTA,'yyyyMMdd') || 'Ã' ||
               -----
               NVL(CIERRE.IND_VB_CONTABLE,' ')
        FROM   CE_CIERRE_DIA_VENTA CIERRE,
               PBL_USU_LOCAL USU_LOCAL
        WHERE  CIERRE.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CIERRE.COD_LOCAL = cCodLocal_in
        AND    CIERRE.FEC_CIERRE_DIA_VTA BETWEEN TRUNC(SYSDATE - vNumeroDias) AND TRUNC(SYSDATE) + 1 -1/24/60/60
        AND    CIERRE.COD_GRUPO_CIA = USU_LOCAL.COD_GRUPO_CIA
        AND    CIERRE.COD_LOCAL = USU_LOCAL.COD_LOCAL
        AND    CIERRE.SEC_USU_LOCAL_CREA = USU_LOCAL.SEC_USU_LOCAL;
    RETURN curCE;
  END;

  /****************************************************************************/
  PROCEDURE CE_REGISTRA_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                   cCierreDia_in   IN CHAR,
                                   cSecUsuLocal_in IN CHAR,
                                   nTipoCambio_in  IN NUMBER,
                                   cUsuCrea_in     IN CHAR)
  IS
    v_nSecHistVB CE_HIST_VB_MOV_CAJA.SEC_HIST_VB_MOV_CAJA%TYPE;

    cursor vCurPedInvDiario is
    select c.cod_grupo_cia,c.cod_local,trunc(c.fec_ped_vta) fecha,
       '021' CodigoCuadratura,c.tip_comp_pago,c.val_neto_ped_vta,
       d.cod_grupo_cia dCia,d.cod_trab,d.monto,
       cUsuCrea_in,'TOMA INV DIARIO' motivo,f.tip_moneda,c.num_ped_vta
    from   vta_pedido_vta_cab c,
           vta_forma_pago_pedido f,
           tmp_ped_cab_inv_diario t,
           tmp_ped_dcto_x_trab d
    where  c.cod_grupo_cia = cCodGrupoCia_in
    and    c.cod_local = cCodLocal_in
    AND    c.est_ped_vta = 'C'
    and    c.fec_ped_vta BETWEEN TO_DATE(cCierreDia_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                           AND     TO_DATE(cCierreDia_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
    and    t.estado = 'P'
    and    c.cod_grupo_cia = f.cod_grupo_cia
    and    c.cod_local = f.cod_local
    and    c.num_ped_vta = f.num_ped_vta
    and    c.cod_grupo_cia = T.COD_GRUPO_CIA
    and    c.cod_local = t.cod_local
    and    c.num_ped_vta = t.num_ped_vta
    and    t.cod_grupo_cia = d.cod_grupo_cia
    and    t.cod_local = d.cod_local
    and    t.sec_pedido = d.sec_pedido;



  BEGIN

      INSERT INTO CE_CIERRE_DIA_VENTA
                   (COD_GRUPO_CIA,COD_LOCAL,FEC_CIERRE_DIA_VTA,
                    SEC_USU_LOCAL_CREA,IND_VB_CIERRE_DIA,FEC_VB_CIERRE_DIA,DESC_OBSV_CIERRE_DIA,TIP_CAMBIO_CIERRE_DIA,
                    USU_CREA_CIERRE_DIA,FEC_CREA_CIERRE_DIA,USU_MOD_CIERRE_DIA,FEC_MOD_CIERRE_DIA)
             VALUES(cCodGrupoCia_in, cCodLocal_in, TO_DATE(cCierreDia_in,'dd/MM/yyyy'),
                    cSecUsuLocal_in, INDICADOR_NO, NULL, NULL, nTipoCambio_in,
                    cUsuCrea_in ,SYSDATE, NULL, NULL);

      UPDATE CE_MOV_CAJA MOV_CAJA
         SET MOV_CAJA.USU_MOD_MOV_CAJA = cUsuCrea_in, MOV_CAJA.FEC_MOD_MOV_CAJA = SYSDATE,
             MOV_CAJA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       WHERE MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND   MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND   MOV_CAJA.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND   MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_CIERRE;


       ---coloca las cuadratura de Descuento a Personal
       for vCurPed in vCurPedInvDiario  loop
          ptoventa_ce_ern.valida_cuadratura_021(vCurPed.Cod_Grupo_Cia,
                                                vCurPed.Cod_Local,
                                                cCierreDia_in,
                                                vCurPed.Codigocuadratura,
                                                vCurPed.Tip_Comp_Pago,
                                                vCurPed.Val_Neto_Ped_Vta,
                                                vCurPed.Dcia,
                                                vCurPed.cod_trab,
                                                vCurPed.Monto,
                                                cUsuCrea_in,
                                                vCurPed.Monto,
                                                vCurPed.Tip_Moneda,
                                                vCurPed.Num_Ped_Vta);
       end loop;

  END;

  /* ************************************************************************ */
  FUNCTION CE_VALIDA_REGISTRO_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in     IN CHAR,
                                          cCierreDia_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nContador NUMBER;
  BEGIN
       SELECT COUNT(1)
       INTO   v_nContador
       FROM   CE_CIERRE_DIA_VENTA CIERRE
       WHERE  CIERRE.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CIERRE.COD_LOCAL = cCodLocal_in
       AND    CIERRE.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');

     RETURN v_nContador;
   EXCEPTION
     WHEN OTHERS THEN
          v_nContador := 1;--ERROR Y DEVOLVEMOS 1 PARA QUE NO PASE LA VALIDACION
     RETURN v_nContador;
  END;

  /* ************************************************************************ */
  FUNCTION CE_OBTIENE_CAJ_APERTURADAS_DIA(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR,
                                           cCierreDia_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nContador NUMBER;
  BEGIN
       SELECT COUNT(1)
       INTO   v_nContador
       FROM   CE_MOV_CAJA MOV_CAJA
       WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_APERTURA;

     RETURN v_nContador;
   EXCEPTION
     WHEN OTHERS THEN
          v_nContador := 0;--ERROR Y DEVOLVEMOS 0 PARA QUE NO PASE LA VALIDACION
     RETURN v_nContador;
  END;

  /* ************************************************************************ */
  FUNCTION CE_VALIDA_CAJA_CON_VB_CAJERO(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cCierreDia_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nCantCajAperturadas NUMBER;
    v_nCantCajCerradas    NUMBER;
    v_nDiferencia         NUMBER;
  BEGIN
       v_nCantCajAperturadas := CE_OBTIENE_CAJ_APERTURADAS_DIA(cCodGrupoCia_in,
                                                                cCodLocal_in,
                                                               cCierreDia_in);

       SELECT COUNT(1)
       INTO   v_nCantCajCerradas
       FROM   CE_MOV_CAJA MOV_CAJA
       WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_CIERRE
       AND    MOV_CAJA.IND_VB_CAJERO = INDICADOR_SI;

       v_nDiferencia := v_nCantCajAperturadas - v_nCantCajCerradas;

     RETURN v_nDiferencia;
   EXCEPTION
     WHEN OTHERS THEN
          v_nDiferencia := 1;--ERROR Y DEVOLVEMOS 1 PARA QUE NO PASE LA VALIDACION
     RETURN v_nDiferencia;
  END;

  /****************************************************************************/
  FUNCTION CE_CONSO_FOR_PAG_ENT_CIER_DIA(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT P.DESC_CORTA_FORMA_PAGO || 'Ã' ||
               DECODE(F.TIP_MONEDA,TIP_MONEDA_DOLARES,'DOLARES','SOLES') || 'Ã' ||
               TO_CHAR(SUM(F.MON_ENTREGA),'999,990.00') || 'Ã' ||
               TO_CHAR(SUM(F.MON_ENTREGA_TOTAL),'999,990.00') || 'Ã' ||
               F.COD_FORMA_PAGO
        FROM   CE_FORMA_PAGO_ENTREGA F,
               VTA_FORMA_PAGO P
        WHERE  F.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    F.COD_LOCAL = cCodLocal_in
        AND    F.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                  FROM   CE_MOV_CAJA A
                                  WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND    A.COD_LOCAL = cCodLocal_in
                                  AND    A.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
                                  AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
        AND    F.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND    F.COD_FORMA_PAGO = P.COD_FORMA_PAGO
        AND    F.EST_FORMA_PAGO_ENT = 'A'
        GROUP BY P.DESC_CORTA_FORMA_PAGO, F.TIP_MONEDA, F.COD_FORMA_PAGO;

    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_CONSO_CUADRATURA_CIER_DIA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT CAJ.COD_CUADRATURA || 'Ã' ||
               CUA.DESC_CUADRATURA || 'Ã' ||
               TO_CHAR(SUM(CAJ.MON_TOTAL) * CUA.VAL_SIGNO,'999,990.00')
        FROM   CE_CUADRATURA_CAJA CAJ,
               CE_CUADRATURA CUA
        WHERE  CAJ.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CAJ.COD_LOCAL = cCodLocal_in
        AND    CAJ.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                    FROM   CE_MOV_CAJA A
                                    WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND    A.COD_LOCAL = cCodLocal_in
                                    AND    A.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
                                    AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
        AND    CUA.COD_GRUPO_CIA = CAJ.COD_GRUPO_CIA
        AND    CUA.COD_CUADRATURA= CAJ.COD_CUADRATURA
        AND    CAJ.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        GROUP BY CAJ.COD_CUADRATURA, CUA.DESC_CUADRATURA, CUA.VAL_SIGNO;

    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_CONSO_EFEC_RECAUDADO_CIERRE(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in     IN CHAR,
                                          cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT P.DESC_CORTA_FORMA_PAGO || 'Ã' ||
               DECODE(F.TIP_MONEDA,TIP_MONEDA_DOLARES,'DOLARES','SOLES') || 'Ã' ||
               TO_CHAR(SUM(F.MON_ENTREGA),'999,990.00') || 'Ã' ||
               TO_CHAR(SUM(F.MON_ENTREGA_TOTAL),'999,990.00') || 'Ã' ||
               F.COD_FORMA_PAGO
        FROM   CE_FORMA_PAGO_ENTREGA F,
               VTA_FORMA_PAGO P
        WHERE  F.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    F.COD_LOCAL = cCodLocal_in
        AND    F.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                  FROM   CE_MOV_CAJA A
                                  WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND    A.COD_LOCAL = cCodLocal_in
                                  AND    A.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
                                  AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
        AND    F.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND    F.COD_FORMA_PAGO = P.COD_FORMA_PAGO
        AND    F.EST_FORMA_PAGO_ENT = 'A'
        AND    P.IND_FORMA_PAGO_EFECTIVO = INDICADOR_SI
        GROUP BY P.DESC_CORTA_FORMA_PAGO, F.TIP_MONEDA, F.COD_FORMA_PAGO;

    RETURN curCE;
  END;

  /* ************************************************************************ */
  FUNCTION CE_MONTO_TOTAL_SIST_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR,
                                           cCierreDia_in   IN CHAR)
    RETURN CHAR
  IS
    v_cTotalSistema CHAR(20);
  BEGIN
       SELECT TO_CHAR(SUM(NVL(E.MON_TOT,0)),'999,990.00')
       INTO   v_cTotalSistema
       FROM   CE_MOV_CAJA E
       WHERE  E.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    E.COD_LOCAL = cCodLocal_in
       AND    E.Fec_Cierre_Dia_Vta = TO_DATE(cCierreDia_in,'dd/MM/yyyy');

     RETURN v_cTotalSistema;
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
          v_cTotalSistema := '0.00';--NO SE ENCONTRO MONTO TOTAL DE SISTEMA
     RETURN v_cTotalSistema;
  END;

  /****************************************************************************/
  FUNCTION CE_CONSO_EFEC_RENDIDO_CIERRE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT CUADRATURA_CIERRE_DIA.COD_CUADRATURA || 'Ã' ||
               NVL(CUADRATURA.DESC_CUADRATURA,' ') || 'Ã' ||
               --TO_CHAR(SUM(DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL) ),'999,999,990.00')
               TO_CHAR(DECODE(CUADRATURA.COD_CUADRATURA,COD_CUADRATURA_DEL_PERDIDO,SUM(CUADRATURA_CIERRE_DIA.MON_PERDIDO_TOTAL),SUM(DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL * DECODE(CUADRATURA_CIERRE_DIA.TIP_MONEDA,TIP_MONEDA_DOLARES,DV.TIP_CAMBIO_CIERRE_DIA,1)))),'999,999,990.00')
               --TO_CHAR(SUM( DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL) * DECODE(CUADRATURA_CIERRE_DIA.TIP_MONEDA,TIP_MONEDA_DOLARES,DV.TIP_CAMBIO_CIERRE_DIA,1)),'999,999,990.00')
        FROM   CE_CUADRATURA_CIERRE_DIA CUADRATURA_CIERRE_DIA,
               CE_CUADRATURA CUADRATURA,
               CE_CIERRE_DIA_VENTA DV
        WHERE  CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CUADRATURA_CIERRE_DIA.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    CUADRATURA_CIERRE_DIA.COD_LOCAL = cCodLocal_in
        AND    CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
        AND    CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = CUADRATURA.COD_GRUPO_CIA
        AND    CUADRATURA_CIERRE_DIA.COD_CUADRATURA = CUADRATURA.COD_CUADRATURA
        AND    DV.COD_GRUPO_CIA = CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA
        AND    DV.COD_LOCAL = CUADRATURA_CIERRE_DIA.COD_LOCAL
        AND    DV.FEC_CIERRE_DIA_VTA = CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA
        GROUP BY CUADRATURA_CIERRE_DIA.COD_CUADRATURA,
                 CUADRATURA.DESC_CUADRATURA,
                 CUADRATURA.COD_CUADRATURA
        union
        SELECT '011' ||'Ã'||
               'PROSEGUR'||'Ã'||
               TO_CHAR(V.MONT_SOBRES,'999,999,990.00')
        FROM   DUAL,
               (
               SELECT nvl(sum(CF.MON_ENTREGA_TOTAL),0) MONT_SOBRES
                FROM   CE_SOBRE S,
                       CE_FORMA_PAGO_ENTREGA CF
                WHERE  CF.COD_GRUPO_CIA = cCodGrupoCia_in
                AND    CF.COD_LOCAL     = cCodLocal_in
                AND    CF.EST_FORMA_PAGO_ENT = 'A'
                AND    S.FEC_DIA_VTA    = to_date(cCierreDia_in,'dd/MM/yyyy')
                AND    S.COD_GRUPO_CIA = CF.COD_GRUPO_CIA
                AND    S.COD_LOCAL = CF.COD_LOCAL
                AND    S.SEC_MOV_CAJA = CF.SEC_MOV_CAJA
                AND    S.SEC_FORMA_PAGO_ENTREGA = CF.SEC_FORMA_PAGO_ENTREGA
                ) V
        WHERE   V.MONT_SOBRES > 0;



    RETURN curCE;
  END;

  /* ************************************************************************* */
  FUNCTION CE_IND_VB_CIERRE_DIA_FORUPDATE(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR,
                                          cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT CIERRE_DIA_VENTA.IND_VB_CIERRE_DIA
         FROM   CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA
         WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
         AND    CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy') FOR UPDATE;
    RETURN curCE;
  END;

  /* ************************************************************************* */
  PROCEDURE CE_ACTUALIZA_VB_CIERRE_DIA(cCodGrupoCia_in     IN CHAR,
                                        cCodLocal_in        IN CHAR,
                                       cCierreDia_in       IN CHAR,
                                       cIndVBCierreDia_in  IN CHAR,
                                       cDescObs_in         IN CHAR,
                                       cSecUsuVB_in        IN CHAR,
                                       cUsuModCierreDia_in IN CHAR)
  IS
  vMontDif number;
  vMontVentas number;
  vMontTurno  number;
  BEGIN
       IF cIndVBCierreDia_in = INDICADOR_SI THEN


          SELECT SUM(C.VAL_NETO_PED_VTA) MONTO_VTA
          into   vMontVentas
          FROM VTA_PEDIDO_VTA_CAB C
          WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.EST_PED_VTA = 'C'
            AND C.FEC_PED_VTA BETWEEN TO_DATE(cCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                      TO_DATE(cCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS');

        SELECT SUM(M.MON_TOT) MONTO_CJA
        into   vMontTurno
        FROM   CE_MOV_CAJA M
        WHERE  M.FEC_DIA_VTA BETWEEN TO_DATE(cCierreDia_in  || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                      TO_DATE(cCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND M.TIP_MOV_CAJA = 'C'
        AND M.COD_GRUPO_CIA = cCodGrupoCia_in
        AND M.COD_LOCAL = cCodLocal_in;

        SELECT ABS(vMontVentas - vMontTurno) INTO vMontDif FROM DUAL;

          IF vMontDif !=0 THEN
            RAISE_APPLICATION_ERROR(-20002,'Existe diferencia de S/.'||
                                           trim(to_char(vMontDif,'999999.00'))||
                                           ' en los cierre de turno y las ventas del dia.');
          END IF;


          UPDATE CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA SET CIERRE_DIA_VENTA.Usu_Mod_Cierre_Dia = cUsuModCierreDia_in, CIERRE_DIA_VENTA.Fec_Mod_Cierre_Dia = SYSDATE ,
                 CIERRE_DIA_VENTA.Ind_Vb_Cierre_Dia = cIndVBCierreDia_in,
                 CIERRE_DIA_VENTA.DESC_OBSV_CIERRE_DIA = cDescObs_in,
                 CIERRE_DIA_VENTA.SEC_USU_LOCAL_VB = cSecUsuVB_in,
                 CIERRE_DIA_VENTA.FEC_VB_CIERRE_DIA= SYSDATE
          WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
          AND     CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
          AND     CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');


       ELSIF cIndVBCierreDia_in = INDICADOR_NO THEN
          UPDATE CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA SET CIERRE_DIA_VENTA.Usu_Mod_Cierre_Dia = cUsuModCierreDia_in, CIERRE_DIA_VENTA.Fec_Mod_Cierre_Dia = SYSDATE ,
                 CIERRE_DIA_VENTA.Ind_Vb_Cierre_Dia = cIndVBCierreDia_in,
                 CIERRE_DIA_VENTA.FEC_VB_CIERRE_DIA = NULL,
                 CIERRE_DIA_VENTA.SEC_USU_LOCAL_VB = NULL
          WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
          AND     CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
          AND     CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');
       END IF;
  END;

  /****************************************************************************/
  PROCEDURE CE_REGISTRA_HIST_VB_CIERRE(cCodGrupoCia_in      IN CHAR,
                                        cCodLocal_in         IN CHAR,
                                       cCierreDia_in        IN CHAR,
                                       cIndVBCierreDia_in   IN CHAR,
                                       cSecUsuCrea_in       IN CHAR,
                                       cSecUsuVB_in         IN CHAR,
                                       cDescObs_in          IN CHAR,
                                       cTipoCambio_in       IN NUMBER,
                                       cUsuCreaCierreDia_in IN CHAR)
  IS
    v_nSecHistVBCierreDia CE_HIST_VB_CIERRE_DIA.Sec_Hist_Vb_Cierre_Dia%TYPE;

    CURSOR efectivoRendido IS
           SELECT *
          FROM   CE_CUADRATURA_CIERRE_DIA C
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    C.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
          AND     C.COD_LOCAL = cCodLocal_in
          AND     C.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
          ORDER BY C.SEC_CUADRATURA_CIERRE_DIA;
  BEGIN
      SELECT COUNT(*) + 1
      INTO   v_nSecHistVBCierreDia
      FROM   CE_HIST_VB_CIERRE_DIA A
      WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    A.COD_LOCAL = cCodLocal_in
      AND    A.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');

      INSERT INTO CE_HIST_VB_CIERRE_DIA
                   (COD_GRUPO_CIA,COD_LOCAL,FEC_CIERRE_DIA_VTA,
                    SEC_HIST_VB_CIERRE_DIA,SEC_USU_CREA,SEC_USU_VB,IND_VB_CIERRE_DIA,DESC_MOTIVO,
                    DESC_OBSV_HIST, TIP_CAMBIO_HIST, USU_CREA_VB_CIERRE_DIA,FEC_CREA_VB_CIERRE_DIA)
             VALUES(cCodGrupoCia_in, cCodLocal_in, TO_DATE(cCierreDia_in,'dd/MM/yyyy'),
                    v_nSecHistVBCierreDia, cSecUsuCrea_in, cSecUsuVB_in, cIndVBCierreDia_in, NULL,
                    cDescObs_in, cTipoCambio_in, cUsuCreaCierreDia_in, SYSDATE);

      IF cIndVBCierreDia_in = INDICADOR_NO THEN
         FOR efectivoRendido_rec IN efectivoRendido
         LOOP
             INSERT INTO CE_HIST_CUADRATURA_CIERRE_DIA
                         (COD_GRUPO_CIA,COD_LOCAL,
                          FEC_CIERRE_DIA_VTA,SEC_HIST_VB_CIERRE_DIA,
                          SEC_CUADRATURA_CIERRE_DIA,COD_CUADRATURA_HIST,
                          TIP_MONEDA_HIST,MON_MONEDA_ORIGEN_HIST,
                          MON_TOTAL_HIST,SEC_ENT_FINAN_CUENTA_HIST,
                          FEC_OPERACION_HIST,NUM_OPERACION_HIST,
                          NOM_AGENCIA_HIST,FEC_EMISION_HIST,
                          NUM_SERIE_LOCAL_HIST,TIP_COMP_HIST,
                          NUM_COMP_PAGO_HIST,NOM_TITULAR_SERVICIO_HIST,
                          COD_AUTORIZACION_HIST,COD_CIA_HIST,
                          COD_TRABAJADOR_HIST,DESC_MOTIVO_HIST,
                          COD_FORMA_PAGO_HIST,SERIE_BILLETE_HIST,
                          TIP_DINERO_HIST,MON_PARCIAL_HIST,
                          DESC_RUC_HIST,DESC_RAZON_SOCIAL_HIST,
                          COD_LOCAL_NUEVO_HIST,COD_SERVICIO_HIST,
                          USU_CREA_CUADRA_CIERRE_HIST,FEC_CREA_CUADRA_CIERRE_HIST,
                          NUM_NOTA_ES_HIST,COD_PROVEEDOR_HIST,
                          FEC_VENCIMIENTO_HIST, TIP_DOCUMENTO_HIST,
                          NUM_DOCUMENTO_HIST,MES_PERIODO_HIST,
                          ANO_EJERCICIO_HIST, NUM_REFERENCIA_HIST)
                  VALUES (efectivoRendido_rec.COD_GRUPO_CIA, efectivoRendido_rec.COD_LOCAL,
                          efectivoRendido_rec.FEC_CIERRE_DIA_VTA, v_nSecHistVBCierreDia,
                          efectivoRendido_rec.SEC_CUADRATURA_CIERRE_DIA, efectivoRendido_rec.COD_CUADRATURA,
                          efectivoRendido_rec.TIP_MONEDA, efectivoRendido_rec.MON_MONEDA_ORIGEN,
                          efectivoRendido_rec.MON_TOTAL, efectivoRendido_rec.SEC_ENT_FINAN_CUENTA,
                          efectivoRendido_rec.FEC_OPERACION, efectivoRendido_rec.NUM_OPERACION,
                          efectivoRendido_rec.NOM_AGENCIA, efectivoRendido_rec.FEC_EMISION,
                          efectivoRendido_rec.NUM_SERIE_LOCAL, efectivoRendido_rec.TIP_COMP,
                          efectivoRendido_rec.NUM_COMP_PAGO, efectivoRendido_rec.NOM_TITULAR_SERVICIO,
                          efectivoRendido_rec.COD_AUTORIZACION, efectivoRendido_rec.COD_CIA,
                          efectivoRendido_rec.COD_TRAB, efectivoRendido_rec.DESC_MOTIVO,
                          efectivoRendido_rec.COD_FORMA_PAGO, efectivoRendido_rec.SERIE_BILLETE,
                          efectivoRendido_rec.TIP_DINERO, efectivoRendido_rec.MON_PARCIAL,
                          efectivoRendido_rec.DESC_RUC, efectivoRendido_rec.DESC_RAZON_SOCIAL,
                          efectivoRendido_rec.COD_LOCAL_NUEVO, efectivoRendido_rec.COD_SERVICIO,
                          cUsuCreaCierreDia_in, SYSDATE,
                          efectivoRendido_rec.NUM_NOTA_ES, efectivoRendido_rec.COD_PROVEEDOR,
                          efectivoRendido_rec.FEC_VENCIMIENTO, efectivoRendido_rec.TIP_DOCUMENTO,
                          efectivoRendido_rec.NUM_DOCUMENTO,efectivoRendido_rec.MES_PERIODO,
                          efectivoRendido_rec.ANO_EJERCICIO, efectivoRendido_rec.NUM_REFERENCIA );
         END LOOP;
      END IF;
  END;

  /* ************************************************************************* */
  PROCEDURE CE_ACT_INFO_HIST_VB_CIER_DIA(cCodGrupoCia_in     IN CHAR,
                                          cCodLocal_in        IN CHAR,
                                         cCierreDia_in       IN CHAR,
                                         cSecUsuVB_in        IN CHAR,
                                         cDescMotivo_in      IN CHAR)
  IS
  PRAGMA AUTONOMOUS_TRANSACTION;
  BEGIN
      UPDATE CE_HIST_VB_CIERRE_DIA HVBCD
             SET HVBCD.SEC_USU_VB= cSecUsuVB_in,
                 HVBCD.DESC_MOTIVO = cDescMotivo_in
           WHERE HVBCD.COD_GRUPO_CIA = cCodGrupoCia_in
           AND   HVBCD.COD_LOCAL = cCodLocal_in
           AND   HVBCD.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
           AND   HVBCD.SEC_HIST_VB_CIERRE_DIA IN (SELECT MAX(SEC_HIST_VB_CIERRE_DIA)
                                                  FROM   CE_HIST_VB_CIERRE_DIA
                                                  WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                                                  AND    COD_LOCAL = cCodLocal_in
                                                  AND    FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy'));
      COMMIT;
  EXCEPTION
      WHEN OTHERS THEN
           ROLLBACK;
  END;

  /* ************************************************************************* */
  FUNCTION CE_LISTA_SERIES_TIP_DOC(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                   cTipDoc_in      IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT NUM_SERIE_LOCAL || 'Ã' ||
                NUM_SERIE_LOCAL
         FROM   VTA_SERIE_LOCAL SERIE_LOCAL
         WHERE  SERIE_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    SERIE_LOCAL.COD_LOCAL = cCodLocal_in
         AND    SERIE_LOCAL.TIP_COMP = cTipDoc_in
         AND    SERIE_LOCAL.EST_SERIE_LOCAL = ESTADO_ACTIVO;
    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_OBS_USU_VB_CD(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
       SELECT NVL(CIERRE_DIA_VENTA.DESC_OBSV_CIERRE_DIA,' ') || 'Ã' ||
              NVL(USU_LOCAL.NOM_USU || ' ' || USU_LOCAL.APE_PAT || ' ' || USU_LOCAL.APE_MAT,' ')
       FROM   CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA,
              PBL_USU_LOCAL USU_LOCAL
       WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
       AND    CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA= TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    CIERRE_DIA_VENTA.COD_GRUPO_CIA = USU_LOCAL.COD_GRUPO_CIA(+)
       AND    CIERRE_DIA_VENTA.COD_LOCAL = USU_LOCAL.COD_LOCAL(+)
       AND    CIERRE_DIA_VENTA.Sec_Usu_Local_Vb = USU_LOCAL.SEC_USU_LOCAL(+                   );

     RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_IND_COMP_VALIDOS_DIA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    cCierreDia_in   IN CHAR)
    RETURN CHAR
  IS
    v_cIndCompValidos CHAR(1);
  BEGIN
       SELECT CIERRE_DIA_VENTA.IND_COMP_VALIDOS
       INTO   v_cIndCompValidos
       FROM   CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA
       WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
       AND    CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');

     RETURN v_cIndCompValidos;
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
          v_cIndCompValidos := INDICADOR_NO;--NO SE ENCONTRO EL REGISTRO
     RETURN v_cIndCompValidos;
  END;

  /****************************************************************************/
  FUNCTION CE_COMPROBANTES_VALIDOS_CD(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT NVL(CIERRE_DIA_VENTA.NUM_BOLETA_INICIAL,' ') || 'Ã' ||
                NVL(CIERRE_DIA_VENTA.NUM_BOLETA_FINAL,' ') || 'Ã' ||
                NVL(CIERRE_DIA_VENTA.NUM_FACTURA_INICIAL,' ') || 'Ã' ||
                NVL(CIERRE_DIA_VENTA.NUM_FACTURA_FINAL,' ')
         FROM   CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA
         WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
         AND    CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');

    RETURN curCE;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ACTUALIZA_COMPROBANTES_CD(cCodGrupoCia_in   IN CHAR,
                                          cCodLocal_in      IN CHAR,
                                         cCierreDia_in     IN CHAR,
                                         cBoletaIni_in     IN CHAR,
                                         cBoletaFin_in     IN CHAR,
                                         cFacturaIni_in    IN CHAR,
                                         cFacturaFin_in    IN CHAR,
                                         cIndCompValido_in IN CHAR,
                                         cUsuModCD_in      IN CHAR)
  IS
  BEGIN
        UPDATE CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA SET CIERRE_DIA_VENTA.USU_MOD_CIERRE_DIA = cUsuModCD_in, CIERRE_DIA_VENTA.FEC_MOD_CIERRE_DIA = SYSDATE ,
               CIERRE_DIA_VENTA.NUM_BOLETA_INICIAL = cBoletaIni_in,
               CIERRE_DIA_VENTA.NUM_BOLETA_FINAL = cBoletaFin_in,
               CIERRE_DIA_VENTA.NUM_FACTURA_INICIAL = cFacturaIni_in,
               CIERRE_DIA_VENTA.NUM_FACTURA_FINAL = cFacturaFin_in,
               CIERRE_DIA_VENTA.IND_COMP_VALIDOS = cIndCompValido_in
        WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
        AND    CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ACT_IND_COMP_VALIDO_CIERRED(cCodGrupoCia_in   IN CHAR,
                                            cCodLocal_in      IN CHAR,
                                           cCierreDia_in     IN CHAR,
                                           cIndCompValido_in IN CHAR,
                                           cUsuModCD_in      IN CHAR)
  IS
  BEGIN
        UPDATE CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA SET CIERRE_DIA_VENTA.USU_MOD_CIERRE_DIA = cUsuModCD_in, CIERRE_DIA_VENTA.FEC_MOD_CIERRE_DIA = SYSDATE ,
               CIERRE_DIA_VENTA.IND_COMP_VALIDOS = cIndCompValido_in
        WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
        AND    CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');
  END;

  /* ************************************************************************ */
  PROCEDURE CE_EVALUA_DEFICIT_ASUMIDO_QF(cCodGrupoCia_in  IN CHAR,
                                          cCodLocal_in      IN CHAR,
                                          cCierreDia_in    IN CHAR,
                                         nMontoDeficit_in IN NUMBER,
                                         vSecUsuLocal_in  IN CHAR,
                                         vIdUsu_in        IN CHAR)
  IS
    v_nSecCuadraturaCierreDia NUMBER;
    v_cCodCiaTrab             PBL_USU_LOCAL.COD_CIA%TYPE;
    v_cCodTrab                PBL_USU_LOCAL.COD_TRAB%TYPE;
  BEGIN
       IF nMontoDeficit_in <> 0 THEN --sirve para eliminar si o si el deficit asumido

       UPDATE CE_CUADRATURA_CIERRE_DIA  SET
             usu_mod_cuadra_cierre_dia= vIdUsu_in ,
             fec_mod_cuadra_cierre_dia = SYSDATE,
             est_cuadrat_c_dia = 'I'
                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND   COD_LOCAL = cCodLocal_in
                AND   FEC_CIERRE_DIA_VTA= TO_DATE(cCierreDia_in,'dd/MM/yyyy')
                AND   COD_CUADRATURA= COD_CUADRATURA_DEFICIT_QF;

       /*
        DELETE FROM CE_CUADRATURA_CIERRE_DIA CD
                WHERE CD.COD_GRUPO_CIA = cCodGrupoCia_in
                AND   CD.COD_LOCAL = cCodLocal_in
                AND   CD.FEC_CIERRE_DIA_VTA= TO_DATE(cCierreDia_in,'dd/MM/yyyy')
                AND   CD.COD_CUADRATURA= COD_CUADRATURA_DEFICIT_QF;
        */
       END IF;

       IF nMontoDeficit_in > 0 THEN

          SELECT NVL(MAX(CD.SEC_CUADRATURA_CIERRE_DIA),0) + 1
          INTO   v_nSecCuadraturaCierreDia
          FROM   CE_CUADRATURA_CIERRE_DIA CD
          WHERE  CD.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    CD.COD_LOCAL = cCodLocal_in
          AND    CD.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');

          SELECT NVL(U.COD_CIA,''),
                 NVL(U.COD_TRAB,'')
          INTO   v_cCodCiaTrab,
                 v_cCodTrab
          FROM   PBL_USU_LOCAL U
          WHERE  U.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    U.COD_LOCAL = cCodLocal_in
          AND    U.SEC_USU_LOCAL = vSecUsuLocal_in;

          /*dbms_output.put_line('v_cCodCiaTrab: ' || v_cCodCiaTrab );
          dbms_output.put_line('v_cCodTrab: ' || v_cCodTrab );
          dbms_output.put_line('cCierreDia_in : ' || cCierreDia_in );
          dbms_output.put_line('TO_DATE(cCierreDia_in,dd/MM/yyyy): ' || TO_DATE(cCierreDia_in,'dd/MM/yyyy') );*/

          INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA ,COD_LOCAL ,FEC_CIERRE_DIA_VTA ,
                                               SEC_CUADRATURA_CIERRE_DIA, COD_CUADRATURA ,MON_MONEDA_ORIGEN  ,
                                               MON_TOTAL , MON_PARCIAL, TIP_MONEDA  , COD_CIA, COD_TRAB,
                                               MES_PERIODO, ANO_EJERCICIO, USU_CREA_CUADRA_CIERRE_DIA)
                                        VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(cCierreDia_in,'dd/MM/yyyy'),
                                               v_nSecCuadraturaCierreDia,COD_CUADRATURA_DEFICIT_QF,nMontoDeficit_in,
                                               nMontoDeficit_in,nMontoDeficit_in,TIP_MONEDA_SOLES,v_cCodCiaTrab, v_cCodTrab,
                                               To_char(TO_date(cCierreDia_in,'dd/MM/yyyy'),'MM'),
                                               to_char(TO_date(cCierreDia_in,'dd/MM/yyyy'),'yyyy'),
                                               vIdUsu_in);
       END IF;
  END;

  /* ************************************************************************ */
  FUNCTION CE_VALIDA_CAJA_CON_VB_QF(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cCierreDia_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nCantCajAperturadas NUMBER;
    v_nCantCajCerradas    NUMBER;
    v_nDiferencia         NUMBER;
  BEGIN
       v_nCantCajAperturadas := CE_OBTIENE_CAJ_APERTURADAS_DIA(cCodGrupoCia_in,
                                                                cCodLocal_in,
                                                               cCierreDia_in);

       SELECT COUNT(1)
       INTO   v_nCantCajCerradas
       FROM   CE_MOV_CAJA MOV_CAJA
       WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_CIERRE
       AND    MOV_CAJA.IND_VB_QF = INDICADOR_SI;

       v_nDiferencia := v_nCantCajAperturadas - v_nCantCajCerradas;

     RETURN v_nDiferencia;
   EXCEPTION
     WHEN OTHERS THEN
          v_nDiferencia := 1;--ERROR Y DEVOLVEMOS 1 PARA QUE NO PASE LA VALIDACION
     RETURN v_nDiferencia;
  END;

  /* ************************************************************************ */
  FUNCTION CE_VALIDA_CIERRE_DIA_CON_VB(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        cCierreDia_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nContador NUMBER;
  BEGIN
       SELECT COUNT(1)
       INTO   v_nContador
       FROM   CE_CIERRE_DIA_VENTA CIERRE
       WHERE  CIERRE.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CIERRE.COD_LOCAL = cCodLocal_in
       AND    CIERRE.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    CIERRE.IND_VB_CIERRE_DIA = INDICADOR_SI;

     RETURN v_nContador;
   EXCEPTION
     WHEN OTHERS THEN
          v_nContador := 0;--ERROR Y DEVOLVEMOS 0 PARA QUE PASE LA VALIDACION
     RETURN v_nContador;
  END;

  FUNCTION CE_LISTA_TIP_COMP(cCodGrupoCia_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT TIP_COMP.TIP_COMP || 'Ã' ||
                TIP_COMP.DESC_COMP
         FROM   VTA_TIP_COMP TIP_COMP
         WHERE  TIP_COMP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    TIP_COMP.IND_NECESITA_IMPR = INDICADOR_SI;
    RETURN curCE;
  END;

  /* ************************************************************************ */
  FUNCTION CE_LISTA_RANGO_COMP_MOV_CAJ(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in       IN CHAR,
                                       cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         /*SELECT TIP.DESC_COMP || 'Ã' ||
                CMC.NUM_SERIE_LOCAL || 'Ã' ||
                CMC.NUM_INICIAL || 'Ã' ||
                CMC.NUM_FINAL || 'Ã' ||
                CMC.TIP_COMP || 'Ã' ||
                'S'
         FROM   CE_COMP_MOV_CAJA CMC,
                VTA_TIP_COMP TIP
         WHERE  CMC.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CMC.COD_LOCAL = cCodLocal_in
         AND    CMC.SEC_MOV_CAJA = cMovCajaCierre_in
         AND    CMC.COD_GRUPO_CIA = TIP.COD_GRUPO_CIA
         AND    CMC.TIP_COMP = TIP.TIP_COMP;*/
SELECT TIP.DESC_COMP || 'Ã' ||
                CMC.NUM_SERIE_LOCAL || 'Ã' ||
                CMC.NUM_INICIAL || 'Ã' ||
                CMC.NUM_FINAL || 'Ã' ||
                to_char(nvl(cmc.mont_min,0),'999999990.00')|| 'Ã' ||
                to_char(nvl(cmc.mont_min,0),'999999990.00')|| 'Ã' ||
                CMC.TIP_COMP || 'Ã' ||
                'S'
         FROM   CE_COMP_MOV_CAJA CMC,
                VTA_TIP_COMP TIP
         WHERE  CMC.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CMC.COD_LOCAL = cCodLocal_in
         AND    CMC.SEC_MOV_CAJA = cMovCajaCierre_in
         AND    CMC.COD_GRUPO_CIA = TIP.COD_GRUPO_CIA
         AND    CMC.TIP_COMP = TIP.TIP_COMP;

    RETURN curCE;
  END;

  /* ************************************************************************ */
  FUNCTION CE_LISTA_RANGO_COMP_CIE_DIA(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR/*
         SELECT TIP.DESC_COMP || 'Ã' ||
                CCD.NUM_SERIE_LOCAL || 'Ã' ||
                CCD.NUM_INICIAL || 'Ã' ||
                CCD.NUM_FINAL || 'Ã' ||
                CCD.TIP_COMP || 'Ã' ||
                'S'
         FROM   CE_COMP_CIERRE_DIA_VENTA CCD,
                VTA_TIP_COMP TIP
         WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CCD.COD_LOCAL = cCodLocal_in
         AND    CCD.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in, 'dd/MM/yyyy')
         AND    CCD.COD_GRUPO_CIA = TIP.COD_GRUPO_CIA
         AND    CCD.TIP_COMP = TIP.TIP_COMP;*/
SELECT TIP.DESC_COMP || 'Ã' ||
                CCD.NUM_SERIE_LOCAL || 'Ã' ||
                CCD.NUM_INICIAL || 'Ã' ||
                CCD.NUM_FINAL || 'Ã' ||
                to_char(nvl(ccd.mont_min,0),'999999990.00')|| 'Ã' ||
                to_char(nvl(ccd.mont_min,0),'999999990.00')|| 'Ã' ||
                CCD.TIP_COMP || 'Ã' ||
                'S'
         FROM   CE_COMP_CIERRE_DIA_VENTA CCD,
                VTA_TIP_COMP TIP
         WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CCD.COD_LOCAL = cCodLocal_in
         AND    CCD.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in, 'dd/MM/yyyy')
         AND    CCD.COD_GRUPO_CIA = TIP.COD_GRUPO_CIA
         AND    CCD.TIP_COMP = TIP.TIP_COMP;
    RETURN curCE;
  END;

  /* ************************************************************************ */
  FUNCTION CE_OBTIENE_RANGO_COMP_MOV_CAJ(cCodGrupoCia_in   IN CHAR,
                                         cCodLocal_in       IN CHAR,
                                         cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
    isLocalMultifuncional char(1);
  BEGIN
  select decode(trim(l.tip_caja),'M','S','N')
  into  isLocalMultifuncional
  from   pbl_local l
  where  l.cod_grupo_cia = cCodGrupoCia_in
  and    l.cod_local = cCodLocal_in;

  if isLocalMultifuncional = 'N' then
    OPEN curCE FOR
         SELECT CP.TIP_COMP_PAGO || 'Ã' ||
                SUBSTR(CP.NUM_COMP_PAGO,1,3) || 'Ã' ||
                SUBSTR(MIN(CP.NUM_COMP_PAGO),4) || 'Ã' ||
                SUBSTR(MAX(CP.NUM_COMP_PAGO),4)
         FROM   VTA_COMP_PAGO CP
         WHERE  CP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CP.COD_LOCAL = cCodLocal_in
         AND    CP.TIP_COMP_PAGO IN (SELECT TIP_COMP.TIP_COMP
                                     FROM   VTA_TIP_COMP TIP_COMP
                                     WHERE  TIP_COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                                     AND    TIP_COMP.IND_NECESITA_IMPR = INDICADOR_SI)
         AND    CP.SEC_MOV_CAJA IN (SELECT CMC.SEC_MOV_CAJA_ORIGEN
                                    FROM   CE_MOV_CAJA CMC
                                    WHERE  CMC.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND    CMC.COD_LOCAL = cCodLocal_in
                                    AND    CMC.SEC_MOV_CAJA = cMovCajaCierre_in)
         AND    NVL(CP.COD_TIP_PROC_PAGO,'0') <> '1' --INDICADOR ELECTRONICO
         GROUP BY CP.TIP_COMP_PAGO,
                  SUBSTR(CP.NUM_COMP_PAGO,1,3)
        -- ORDER BY CP.TIP_COMP_PAGO, SUBSTR(CP.NUM_COMP_PAGO,1,3)--;
--INICIO
         --RH:06.10.2014 FAC-ELECTRONICA
         -- UNIMOS LOS COMPRANTES DE FAC-ELECTRONICA
         UNION

         SELECT CP.TIP_COMP_PAGO || 'Ã' ||
                SUBSTR(CP.NUM_COMP_PAGO_E,1,4) || 'Ã' ||
                SUBSTR(MIN(CP.NUM_COMP_PAGO_E),-8) || 'Ã' ||
                SUBSTR(MAX(CP.NUM_COMP_PAGO_E),-8)
         FROM   VTA_COMP_PAGO CP
         WHERE  CP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CP.COD_LOCAL = cCodLocal_in
         AND    CP.TIP_COMP_PAGO IN (SELECT TIP_COMP.TIP_COMP
                                     FROM   VTA_TIP_COMP TIP_COMP
                                     WHERE  TIP_COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                                     AND    TIP_COMP.IND_NECESITA_IMPR = INDICADOR_SI
                                     OR    TIP_COMP.TIP_COMP = C_NOTA_CRED
                              --FAC-ELECT: muestra las notas de credito electronicas.
                                       --28.10.2014                                                                                   
                                     )
         AND    CP.SEC_MOV_CAJA IN (SELECT CMC.SEC_MOV_CAJA_ORIGEN
                                    FROM   CE_MOV_CAJA CMC
                                    WHERE  CMC.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND    CMC.COD_LOCAL = cCodLocal_in
                                    AND    CMC.SEC_MOV_CAJA = cMovCajaCierre_in)
        
                                          
         AND    NVL(CP.COD_TIP_PROC_PAGO,'0') = '1'   --INDICADOR ELECTRONICO
         GROUP BY CP.TIP_COMP_PAGO,
                  SUBSTR(CP.NUM_COMP_PAGO_E,1,4)

         ;

---FIN

  else
    OPEN curCE FOR
             SELECT  trim('01Ã'||
                     /*cCodLocal_in*/
                     (
                      select l.num_serie_local
                      from   vta_impr_local l
                      where  l.tip_comp = '01'
                      and    l.cod_grupo_cia = cCodGrupoCia_in
                      and    l.cod_local = cCodLocal_in
                     )
                     ||'Ã'||
                     '0000000'||'Ã'||'0000000')
             FROM   dual;
  end if;
    RETURN curCE;
  END;

  /* ************************************************************************ */
  FUNCTION CE_OBTIENE_RANGO_COMP_CIE_DIA(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT CP.TIP_COMP_PAGO || 'Ã' ||
                SUBSTR(CP.NUM_COMP_PAGO,1,3) || 'Ã' ||
                SUBSTR(MIN(CP.NUM_COMP_PAGO),4) || 'Ã' ||
                SUBSTR(MAX(CP.NUM_COMP_PAGO),4)
         FROM   VTA_COMP_PAGO CP
         WHERE  CP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CP.COD_LOCAL = cCodLocal_in
         AND    CP.TIP_COMP_PAGO IN (SELECT TIP_COMP.TIP_COMP
                                     FROM   VTA_TIP_COMP TIP_COMP
                                     WHERE  TIP_COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                                     AND    TIP_COMP.IND_NECESITA_IMPR = INDICADOR_SI)
         AND    CP.SEC_MOV_CAJA IN (SELECT CMC.SEC_MOV_CAJA_ORIGEN
                                    FROM   CE_MOV_CAJA CMC
                                    WHERE  CMC.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND    CMC.COD_LOCAL = cCodLocal_in
                                    AND    CMC.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy'))
         AND    NVL(CP.COD_TIP_PROC_PAGO,'0') <> '1'   --INDICADOR NO ELECTRONICO
         GROUP BY CP.TIP_COMP_PAGO,
                  SUBSTR(CP.NUM_COMP_PAGO,1,3)
      --   ORDER BY CP.TIP_COMP_PAGO, SUBSTR(CP.NUM_COMP_PAGO,1,3);
--INICIO
         --RH:06.10.2014 FAC-ELECTRONICA
         -- UNIMOS LOS COMPRANTES DE FAC-ELECTRONICA

      UNION
            SELECT CP.TIP_COMP_PAGO || 'Ã' ||
                SUBSTR(CP.NUM_COMP_PAGO_E,1,4) || 'Ã' ||
                SUBSTR(MIN(CP.NUM_COMP_PAGO_E),-8) || 'Ã' ||
                SUBSTR(MAX(CP.NUM_COMP_PAGO_E),-8)
         FROM   VTA_COMP_PAGO CP
         WHERE  CP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CP.COD_LOCAL = cCodLocal_in
         AND    CP.TIP_COMP_PAGO IN (SELECT TIP_COMP.TIP_COMP
                                     FROM   VTA_TIP_COMP TIP_COMP
                                     WHERE  TIP_COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                                     AND    TIP_COMP.IND_NECESITA_IMPR = INDICADOR_SI
                                     OR     TIP_COMP.TIP_COMP = C_NOTA_CRED
                              --FAC-ELECT: muestra las notas de credito electronicas.
                                       --28.10.2014                                          
                                     )
         AND    CP.SEC_MOV_CAJA IN (SELECT CMC.SEC_MOV_CAJA_ORIGEN
                                    FROM   CE_MOV_CAJA CMC
                                    WHERE  CMC.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND    CMC.COD_LOCAL = cCodLocal_in
                                    AND    CMC.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy'))
         AND    NVL(CP.COD_TIP_PROC_PAGO,'0') = '1'   --INDICADOR SI ELECTRONICO
         GROUP BY CP.TIP_COMP_PAGO,
                  SUBSTR(CP.NUM_COMP_PAGO_E,1,4)
                  ;
--FIN

    RETURN curCE;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ELIMINA_RANGO_COMP_MOV_CAJ(cCodGrupoCia_in  IN CHAR,
                                           cCodLocal_in    IN CHAR,
                                          cSecMovCaja_in  IN CHAR,
                                          cTipComp_in     IN CHAR,
                                          cNumSerie_in    IN CHAR)
  IS
  BEGIN
       DELETE FROM CE_COMP_MOV_CAJA
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    SEC_MOV_CAJA = cSecMovCaja_in
       AND    NUM_SERIE_LOCAL = cNumSerie_in
       AND    TIP_COMP = cTipComp_in;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ELIMINA_RANGO_COMP_CIE_DIA(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCierreDia_in   IN CHAR,
                                          cTipComp_in     IN CHAR,
                                          cNumSerie_in    IN CHAR)
  IS
  BEGIN
       DELETE FROM CE_COMP_CIERRE_DIA_VENTA
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    NUM_SERIE_LOCAL = cNumSerie_in
       AND    TIP_COMP = cTipComp_in;
  END ;

  /* ************************************************************************ */
  PROCEDURE CE_INSERTA_RANGO_COMP_MOV_CAJ(cCodGrupoCia_in  IN CHAR,
                                           cCodLocal_in    IN CHAR,
                                          cSecMovCaja_in  IN CHAR,
                                          cTipComp_in     IN CHAR,
                                          cNumSerie_in    IN CHAR,
                                          cRangoIni_in    IN CHAR,
                                          cRangoFin_in    IN CHAR,
                                          cUsuCrea_in     IN CHAR)
  IS
  nCant NUMBER;
  cNumSerie_in_new varchar2(5);
  BEGIN

  SELECT COUNT(*) INTO nCant
  FROM CE_COMP_MOV_CAJA X
  WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
  AND X.COD_LOCAL=cCodLocal_in
  AND X.SEC_MOV_CAJA=cSecMovCaja_in;

  if cTipComp_in != '05' then
    select ip.num_serie_local
    into   cNumSerie_in_new
    from   vta_impr_local ip
    where  ip.cod_grupo_cia = cCodGrupoCia_in
    and    ip.cod_local = cCodLocal_in
    and    ip.tip_comp = cTipComp_in;

  else
    cNumSerie_in_new := cNumSerie_in;
  end if;

  IF(nCant=0)THEN
   INSERT INTO CE_COMP_MOV_CAJA(COD_GRUPO_CIA, COD_LOCAL, SEC_MOV_CAJA, NUM_SERIE_LOCAL,TIP_COMP,
                                NUM_INICIAL, NUM_FINAL, USU_CREA_COMP_MOV_CAJA)
     VALUES(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,
                             --cNumSerie_in,
                             cNumSerie_in_new,cTipComp_in,
                                cRangoIni_in, cRangoFin_in, cUsuCrea_in);
  END IF;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_INSERTA_RANGO_COMP_CIE_DIA(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCierreDia_in   IN CHAR,
                                          cTipComp_in     IN CHAR,
                                          cNumSerie_in    IN CHAR,
                                          cRangoIni_in    IN CHAR,
                                          cRangoFin_in    IN CHAR,
                                          cUsuCrea_in     IN CHAR,
                                          cMontoMin_in    IN char,
                                          cMontoMax_in    IN char
                                          )
  IS
  BEGIN
       INSERT INTO CE_COMP_CIERRE_DIA_VENTA
                   (COD_GRUPO_CIA, COD_LOCAL, FEC_CIERRE_DIA_VTA, NUM_SERIE_LOCAL,
                    TIP_COMP, NUM_INICIAL, NUM_FINAL, USU_CREA_COMP_CIERRE_DIA,
                    MONT_MIN,MONT_MAX)
             VALUES(cCodGrupoCia_in, cCodLocal_in, TO_DATE(cCierreDia_in,'dd/MM/yyyy'), cNumSerie_in,
                    cTipComp_in, cRangoIni_in, cRangoFin_in, cUsuCrea_in,
                    to_number(cMontoMin_in,'999999990.00'),
                    to_number(cMontoMax_in,'999999990.00'));
  END ;

  /*******************************************************************************/
  FUNCTION GET_TIPO_CAJA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vTipoCaja PBL_LOCAL.TIP_CAJA%TYPE;
  BEGIN

    SELECT  DECODE(trim(X.TIP_CAJA),'M','S','N') INTO v_vTipoCaja
    FROM PBL_LOCAL X
    WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in
          AND X.COD_LOCAL = cCodLocal_in;

    RETURN v_vTipoCaja;
  END;

 /*
 * JQUISPE 14.04.2010
 * VALIDACION PARA VISA MANUAL DEL CIERRE DIA
 */
 FUNCTION CE_F_VALIDA_VISA_MANUAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCierreDia_in   IN CHAR
                                  )
                                          RETURN CHAR
  IS
        IND CHAR(1):='S';
        monto_cuadratura number:=0.0;
        monto_FPago number:=0.0;

  BEGIN
     SELECT NVL(MAX(A.MONTO),0) INTO monto_cuadratura
     FROM
       (SELECT
               CUADRATURA_CIERRE_DIA.COD_FORMA_PAGO forma_pago,
               CUADRATURA_CIERRE_DIA.COD_CUADRATURA cod_cuadratura,
               NVL(CUADRATURA.DESC_CUADRATURA,' ') desc_cuadratura,
               DECODE(CUADRATURA.COD_CUADRATURA,
               COD_CUADRATURA_DEL_PERDIDO,
               SUM(CUADRATURA_CIERRE_DIA.MON_PERDIDO_TOTAL),
               SUM(DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL * DECODE(CUADRATURA_CIERRE_DIA.TIP_MONEDA,TIP_MONEDA_DOLARES,DV.TIP_CAMBIO_CIERRE_DIA,1)))) MONTO
        FROM   CE_CUADRATURA_CIERRE_DIA CUADRATURA_CIERRE_DIA,
               CE_CUADRATURA CUADRATURA,
               CE_CIERRE_DIA_VENTA DV
        WHERE  CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CUADRATURA_CIERRE_DIA.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    CUADRATURA_CIERRE_DIA.COD_LOCAL = cCodLocal_in
        AND    CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
        AND    CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = CUADRATURA.COD_GRUPO_CIA
        AND    CUADRATURA_CIERRE_DIA.COD_CUADRATURA = CUADRATURA.COD_CUADRATURA
        AND    DV.COD_GRUPO_CIA = CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA
        AND    DV.COD_LOCAL = CUADRATURA_CIERRE_DIA.COD_LOCAL
        AND    DV.FEC_CIERRE_DIA_VTA = CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA
        AND CUADRATURA_CIERRE_DIA.COD_FORMA_PAGO ='00005'
        AND CUADRATURA_CIERRE_DIA.COD_CUADRATURA='011'
        GROUP BY CUADRATURA_CIERRE_DIA.COD_FORMA_PAGO,
                 CUADRATURA_CIERRE_DIA.COD_CUADRATURA,
                 CUADRATURA.DESC_CUADRATURA,
                 CUADRATURA.COD_CUADRATURA
        ) A;


        SELECT NVL(MAX(SUM(F.MON_ENTREGA_TOTAL)),0) INTO monto_FPago
        FROM   CE_FORMA_PAGO_ENTREGA F,
               VTA_FORMA_PAGO P
        WHERE  F.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    F.COD_LOCAL = cCodLocal_in
        AND    F.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                  FROM   CE_MOV_CAJA A
                                  WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND    A.COD_LOCAL = cCodLocal_in
                                  AND    A.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
                                  AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
        AND    F.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND    F.COD_FORMA_PAGO = P.COD_FORMA_PAGO
        AND    F.EST_FORMA_PAGO_ENT = 'A'
        AND    F.Cod_Forma_Pago='00005'
        GROUP BY P.DESC_CORTA_FORMA_PAGO,
        --F.TIP_MONEDA,
        F.COD_FORMA_PAGO;

       if monto_cuadratura <> monto_FPago then
           IND:='N';
       else
           IND:='S';
       end if;

       DBMS_OUTPUT.put_line('INDICADOR: '||IND);
       DBMS_OUTPUT.put_line('cuadratura: '||monto_cuadratura);
       DBMS_OUTPUT.put_line('forma pago: '||monto_FPago);

       RETURN IND;

  END ;

PROCEDURE CE_APROBAR_SOBRES(cCodGrupoCia_in  IN CHAR,
                             cCodLocal_in     IN CHAR,
                            cSecMovCaja_in   IN CHAR,
                            cUsuModMovCaj_in IN CHAR,
                            cSecUsuQf in char DEFAULT '000')
  IS
  BEGIN
      --Se aprueban automaticamente los sobres del turno.
      UPDATE CE_SOBRE A
      SET A.ESTADO='A',
      A.FEC_MOD_SOBRE=SYSDATE,
      A.USU_MOD_SOBRE=cUsuModMovCaj_in,
      a.sec_usu_qf = cSecUsuQf
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
      AND A.COD_LOCAL=cCodLocal_in
      AND A.SEC_MOV_CAJA=cSecMovCaja_in --todos los sobres creados en cierre de turno
      AND A.ESTADO IN ('P'); --ASOSA, 14.06.2010

      UPDATE CE_SOBRE_TMP B
      SET B.ESTADO='A',
      B.FEC_MOD_SOBRE=SYSDATE,
      B.USU_MOD_SOBRE=cUsuModMovCaj_in,
      b.sec_usu_qf = cSecUsuQf
      WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
      AND B.COD_LOCAL=cCodLocal_in
      AND B.ESTADO IN ('P') --ASOSA, 14.06.2010
      AND B.SEC_MOV_CAJA= (SELECT C.SEC_MOV_CAJA_ORIGEN
                   FROM CE_MOV_CAJA C
                   WHERE  C.COD_GRUPO_CIA=B.COD_GRUPO_CIA
                   AND C.COD_LOCAL=B.COD_LOCAL
                   AND C.SEC_MOV_CAJA=cSecMovCaja_in); --todos los sobres creados en el ingreso parcial de cobro

  END;

FUNCTION CE_F_ANUL_PEND_REGULARIZAR(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cFechCierreDia_in in char)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN



    OPEN curCE FOR
--        SELECT TO_CHAR(CIERRE.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy') || 'Ã' ||
select M.SEC_USU_LOCAL|| 'Ã' ||
       CE.USU_CREA_CUADRATURA_CAJA|| 'Ã' ||
       M.NUM_CAJA_PAGO|| 'Ã' ||
       M.NUM_TURNO_CAJA|| 'Ã' ||
       DECODE(CE.TIP_COMP,
              '01',
              'BOLETA',
              '02',
              'FACTURA',
              '05',
              'TICKET',
              '03',
              'GUIA',
              '') || 'Ã' ||
       CE.NUM_SERIE_LOCAL|| 'Ã' ||
       CE.NUM_COMP_PAGO|| 'Ã' ||
       CE.MON_MONEDA_ORIGEN|| 'Ã' ||
       CE.MON_TOTAL
  from ce_cuadratura_caja ce, vta_comp_pago cp, CE_MOV_CAJA M
 where ce.cod_grupo_cia = cCodGrupoCia_in
   and ce.cod_local = cCodLocal_in
  AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
   and ce.cod_cuadratura = '001' --Anulación pendiente de ingreso al sistema.
   AND ce.fec_crea_cuadratura_caja BETWEEN
       TO_DATE('01/12/2008' || ' 00:00:00', 'dd/MM/yyyy HH24:mi:ss') AND
       TO_DATE(cFechCierreDia_in || ' 23:59:59', 'dd/MM/yyyy HH24:mi:ss') - 1
   and ce.cod_grupo_cia = cp.cod_grupo_cia
   and ce.cod_local = cp.cod_local
   and ce.tip_comp = cp.tip_comp_pago
   and (ce.num_serie_local || ce.num_comp_pago) = --cp.num_comp_pago
       Farma_Utility.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
          --FAC-ELECTRONICA :09.10.2014
   AND CE.COD_GRUPO_CIA = M.COD_GRUPO_CIA
   AND CE.COD_LOCAL = M.COD_LOCAL
   AND CE.SEC_MOV_CAJA = M.SEC_MOV_CAJA
   and (ce.COD_GRUPO_CIA, ce.COD_LOCAL, ce.SEC_MOV_CAJA,
        ce.SEC_CUADRATURA_CAJA) not in
       (select v.COD_GRUPO_CIA,
               v.COD_LOCAL,
               v.SEC_MOV_CAJA,
               v.SEC_CUADRATURA_CAJA
          from ce_cuadratura_caja ce,
               (select ce.cod_grupo_cia,
                       ce.cod_local,
                       ce.SEC_MOV_CAJA,
                       ce.SEC_CUADRATURA_CAJA,
                       ce.tip_comp,
                       ce.num_serie_local || ce.num_comp_pago num_pago
                  from ce_cuadratura_caja ce, vta_comp_pago cp
                 where ce.cod_grupo_cia = cCodGrupoCia_in
                  AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                  and ce.cod_local = cCodLocal_in
                   and ce.cod_cuadratura = '001' --Anulación pendiente de ingreso al sistema.
                   AND ce.fec_crea_cuadratura_caja BETWEEN
                       TO_DATE('01/12/2008' || ' 00:00:00',
                               'dd/MM/yyyy HH24:mi:ss') AND
                       TO_DATE(cFechCierreDia_in || ' 23:59:59',
                               'dd/MM/yyyy HH24:mi:ss') - 1
                   and ce.cod_grupo_cia = cp.cod_grupo_cia
                   and ce.cod_local = cp.cod_local
                   and ce.tip_comp = cp.tip_comp_pago
                   and (ce.num_serie_local || ce.num_comp_pago) =
                       --cp.num_comp_pago
                       Farma_Utility.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                 --FAC-ELECTRONICA :09.10.2014
                       ) v
         where ce.cod_cuadratura in ('002') --cuadraturas Regularización de anulados pendientes.
           and ce.cod_grupo_cia = cCodGrupoCia_in
           and ce.cod_local = cCodLocal_in
           and ce.cod_grupo_cia = v.cod_grupo_cia
           and ce.cod_local = v.cod_local
           and ce.tip_comp = v.tip_comp
           and (ce.num_serie_local || ce.num_comp_pago) = v.num_pago
           AND CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
             );

    RETURN curCE;
  END;




/* ************************************************************************ */
FUNCTION CE_GET_MONTO_COMP(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in     IN CHAR,
                           cTipComp_in      IN CHAR,
                           cCierreDia_in   IN CHAR,
                           cTipMonto       IN CHAR,
                           cSerieComp      IN CHAR
                           )

    RETURN CHAR
  IS
    v_cTotalSistema varCHAR2(50);
    vNumCompPago_in varCHAR2(20);
  BEGIN
    if cTipMonto = 'MIN' then
--  RH:07.10.2014: FAC-Electronica
      SELECT --lpad(min(cp.num_comp_pago),10,'0')
       MIN(Farma_Utility.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO))
          --FAC-ELECTRONICA :09.10.2014
       INTO   vNumCompPago_in
       FROM   vta_comp_pago cp,
              vta_pedido_vta_cab ca
       WHERE  ca.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    ca.COD_LOCAL = cCodLocal_in
       AND    ca.fec_ped_vta between TO_DATE(cCierreDia_in,'dd/MM/yyyy')and
                                           TO_DATE(cCierreDia_in,'dd/MM/yyyy') + 1-1/24/60/60
       and    ca.est_ped_vta  = 'C'
       and    cp.tip_comp_pago = cTipComp_in
       --and    cp.num_comp_pago like cSerieComp||'%'
       /*and    Farma_Utility.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,
                               CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                               like cSerieComp||'%'
                        --FAC-ELECTRONICA :09.10.2014 */
       and (
       case nvl(CP.COD_TIP_PROC_PAGO ,0)
         when '1' then NUM_COMP_PAGO_E
         when '0' then NUM_COMP_PAGO        
       end
       ) like cSerieComp||'%'
       ------------         04.11.2014 rherrera                                 
       and    ca.cod_grupo_cia = cp.cod_grupo_cia
       and    ca.cod_local = cp.cod_local
       and    ca.num_ped_vta = cp.num_ped_vta;
   else
--  RH:07.10.2014: FAC-Electronica
       SELECT --lpad(max(cp.num_comp_pago),10,'0')
       MAX(Farma_Utility.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO))
          --FAC-ELECTRONICA :09.10.2014
       INTO   vNumCompPago_in
       FROM   vta_comp_pago cp,
              vta_pedido_vta_cab ca
       WHERE  ca.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    ca.COD_LOCAL = cCodLocal_in
       AND    ca.fec_ped_vta between TO_DATE(cCierreDia_in,'dd/MM/yyyy')and
                                           TO_DATE(cCierreDia_in,'dd/MM/yyyy') + 1-1/24/60/60
       and    ca.est_ped_vta  = 'C'
       and    cp.tip_comp_pago = cTipComp_in
       --and    cp.num_comp_pago like cSerieComp||'%'
       /*and    Farma_Utility.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,
                               CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                               like cSerieComp||'%'
                        --FAC-ELECTRONICA :09.10.2014  */
       and (
       case nvl(CP.COD_TIP_PROC_PAGO ,0)
         when '1' then NUM_COMP_PAGO_E
         when '0' then NUM_COMP_PAGO        
       end
       ) like cSerieComp||'%'
       -------------    04.11.2014 rherrera                    
       and    ca.cod_grupo_cia = cp.cod_grupo_cia
       and    ca.cod_local = cp.cod_local
       and    ca.num_ped_vta = cp.num_ped_vta;
   end if;

      begin
       SELECT TO_CHAR(NVL(cp.val_neto_comp_pago,0),'9999990.00')
       INTO   v_cTotalSistema
       FROM   vta_comp_pago cp,
              vta_pedido_vta_cab ca
       WHERE  ca.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    ca.COD_LOCAL = cCodLocal_in
       AND    ca.fec_ped_vta between TO_DATE(cCierreDia_in,'dd/MM/yyyy')and
                                           TO_DATE(cCierreDia_in,'dd/MM/yyyy') + 1-1/24/60/60
       and    ca.est_ped_vta  = 'C'
       and    cp.tip_comp_pago = cTipComp_in
--  RH:07.10.2014: FAC-Electronica
     /*  AND
      Farma_Utility.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
          --FAC-ELECTRONICA :09.10.2014
                            =vNumCompPago_in */
     and (
       case nvl(CP.COD_TIP_PROC_PAGO ,0)
         when '1' then NUM_COMP_PAGO_E
         when '0' then NUM_COMP_PAGO        
       end
       ) = vNumCompPago_in                        
       -------------------     04.11.2014 rherrera                 
       --and    cp.num_comp_pago = vNumCompPago_in
       and    ca.cod_grupo_cia = cp.cod_grupo_cia
       and    ca.cod_local = cp.cod_local
       and    ca.num_ped_vta = cp.num_ped_vta;
      exception
      when no_data_found then
        v_cTotalSistema := '0';
      end ;
     RETURN v_cTotalSistema;

  END;


  FUNCTION LISTA_HIST_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;

  BEGIN



    OPEN curCE FOR
        SELECT     to_date(mc.fec_dia_vta, 'dd/MM/yyyy')|| 'Ã' ||
                   nvl(c.usu_crea_cuadratura_caja,' ')|| 'Ã' ||
                   concat(us.nom_usu ,concat('   ',us.ape_pat))  || 'Ã' ||
                   nvl(mc.num_caja_pago,0)|| 'Ã' ||
                   nvl(mc.num_turno_caja,0)|| 'Ã' ||
                   decode(C.tip_comp,'01','BOLETA','02','FACTURA','03','GUIA','05,','TICKET',' ')|| 'Ã' ||
                   nvl(c.num_serie_local,'0')|| 'Ã' ||
                   nvl(c.num_comp_pago,'0')|| 'Ã' ||
                   nvl(to_char(c.mon_moneda_origen,'999,990.00'),'0')|| 'Ã' ||
                   to_char(nvl(c.mon_total,'0'),'999,990.00')

            FROM   ce_cuadratura_caja c,
                   ce_mov_caja mc ,pbl_usu_local us
            WHERE  c.cod_grupo_cia = cCodgrupocia_in
            AND    c.cod_local = cCodlocal_in
            AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO

             and c.cod_grupo_cia = mc.cod_grupo_cia
             and c.cod_local = mc.cod_local
             and c.sec_mov_caja = mc.sec_mov_caja
              and mc.cod_grupo_cia=us.cod_grupo_cia
             and mc.cod_local=us.cod_local
             and mc.sec_usu_local=us.sec_usu_local

             AND  (C.COD_GRUPO_CIA,C.COD_LOCAL,C.SEC_MOV_CAJA, SEC_CUADRATURA_CAJA) IN (
            SELECT COD_GRUPO_CIA,COD_LOCAL,SEC_MOV_CAJA ,SEC_CUADRATURA_CAJA FROM  ce_cuadratura_caja WHERE COD_GRUPO_CIA=cCodgrupocia_in AND COD_LOCAL=cCodlocal_in AND COD_CUADRATURA='001'
                          AND    EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
             MINUS
            (select ce.COD_GRUPO_CIA,ce.COD_LOCAL,v.SEC_MOV_CAJA ,v.SEC_CUADRATURA_CAJA

                from   ce_cuadratura_caja ce,
               (
                select ce.cod_grupo_cia,ce.cod_local,ce.tip_comp, ce.num_serie_local || ce.num_comp_pago num_pago,
                       ce.sec_mov_caja,ce.sec_cuadratura_caja
                from   ce_cuadratura_caja ce,
                       vta_comp_pago cp
                where  ce.cod_grupo_cia = cCodGrupoCia_in
                and    ce.cod_local = cCodLocal_in
                and    ce.cod_cuadratura  = '001'
                AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO

                and    ce.cod_grupo_cia = cp.cod_grupo_cia
                and    ce.cod_local = cp.cod_local
                and    ce.tip_comp = cp.tip_comp_pago
                and    (ce.num_serie_local || ce.num_comp_pago) =
                       --cp.num_comp_pago
                       Farma_Utility.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                       --FAC-ELECTRONICA :09.10.2014
               )v
                where  ce.cod_cuadratura in ('002')
                AND    CE.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                and    ce.cod_grupo_cia  = cCodGrupoCia_in
                and    ce.cod_local      = cCodLocal_in
                and    ce.cod_grupo_cia = v.cod_grupo_cia
                and    ce.cod_local = v.cod_local
                and    ce.tip_comp = v.tip_comp
                and    (ce.num_serie_local || ce.num_comp_pago) = v.num_pago

             ));


    RETURN curCE;
  END;
/* ************************************************************************ */
FUNCTION CE_GET_IMG_BOLETA(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in     IN CHAR)
    RETURN varchar2
  AS
    vCadena varchar2(1000);
  BEGIN

       SELECT t.llave_tab_gral
       INTO   vCadena
       FROM   pbl_tab_gral t,
              pbl_local l
       WHERE  id_tab_gral = 513
       and    l.cod_grupo_cia = cCodGrupoCia_in
       and    l.cod_local = cCodLocal_in;

     RETURN vCadena;
  END;
/* *************************************************************** */
FUNCTION CE_GET_IMG_FACTURA(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in     IN CHAR)
    RETURN varchar2
  AS
    vCadena varchar2(1000);
  BEGIN

       SELECT t.llave_tab_gral
       INTO   vCadena
       FROM   pbl_tab_gral t,
              pbl_local l
       WHERE  id_tab_gral = 514
       and    l.cod_grupo_cia = cCodGrupoCia_in
       and    l.cod_local = cCodLocal_in;

     RETURN vCadena;
  END;
/* *************************************************************** */
  FUNCTION CE_MUESTRA_CAJA_APER(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCajas FarmaCursor;
  BEGIN
    OPEN curCajas FOR
    WITH APER AS
      (SELECT COD_GRUPO_CIA,
      cod_local,
      sec_mov_caja,
      MOV_CAJA.FEC_DIA_VTA,
      sec_usu_local,
      num_caja_pago,
      num_turno_caja
      FROM CE_MOV_CAJA MOV_CAJA
      WHERE MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
      AND MOV_CAJA.COD_LOCAL       = cCodLocal_in
      AND MOV_CAJA.FEC_DIA_VTA     = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
      AND MOV_CAJA.TIP_MOV_CAJA    = TIP_MOV_APERTURA
      ),
      CIE AS
      (SELECT COD_GRUPO_CIA,
      cod_local,
      sec_mov_caja_origen,
      mov_caja.ind_vb_cajero,
      mov_caja.ind_vb_qf
      FROM CE_MOV_CAJA MOV_CAJA
      WHERE MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
      AND MOV_CAJA.COD_LOCAL       = cCodLocal_in
      AND MOV_CAJA.FEC_DIA_VTA     = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
      AND MOV_CAJA.TIP_MOV_CAJA    = TIP_MOV_CIERRE
      )
    SELECT --APER.FEC_DIA_VTA,
      (USU.LOGIN_USU || 'Ã' ||
      APER.NUM_CAJA_PAGO || 'Ã' ||
      APER.NUM_TURNO_CAJA || 'Ã' ||
      NVL2(CIE.SEC_MOV_CAJA_ORIGEN,'S','N') || 'Ã' ||
      NVL(cie.ind_vb_cajero,'N') || 'Ã' ||
      NVL(cie.ind_vb_qf,'N') ) RESULTADO
    FROM APER
    LEFT JOIN CIE
    ON (APER.COD_GRUPO_CIA = CIE.COD_GRUPO_CIA
    AND APER.COD_LOCAL     = CIE.COD_LOCAL
    AND APER.SEC_MOV_CAJA  = CIE.SEC_MOV_CAJA_ORIGEN)
    JOIN PBL_USU_LOCAL USU
    ON (USU.COD_GRUPO_CIA = APER.COD_GRUPO_CIA
    AND USU.COD_LOCAL     = APER.COD_LOCAL
    AND USU.SEC_USU_LOCAL = APER.SEC_USU_LOCAL)
    ORDER BY APER.NUM_CAJA_PAGO,
      APER.NUM_TURNO_CAJA ;
  RETURN curCajas;
  END;
/* *************************************************************** */

  FUNCTION CE_OBTIENE_RANGO_COMP_ELECT(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR

            SELECT CP.TIP_COMP_PAGO || 'Ã' ||
                SUBSTR(CP.NUM_COMP_PAGO_E,1,4) || 'Ã' ||
                SUBSTR(MIN(CP.NUM_COMP_PAGO_E),-8) || 'Ã' ||
                SUBSTR(MAX(CP.NUM_COMP_PAGO_E),-8) || 'Ã' ||
                CE_GET_MONTO_COMP(
                           cCodGrupoCia_in ,
                            cCodLocal_in     ,
                           CP.TIP_COMP_PAGO ,
                           cCierreDia_in   ,
                           'MIN'       ,
                           SUBSTR(CP.NUM_COMP_PAGO_E,1,4)
                           ) || 'Ã' ||
                CE_GET_MONTO_COMP(
                           cCodGrupoCia_in ,
                            cCodLocal_in     ,
                           CP.TIP_COMP_PAGO ,
                           cCierreDia_in   ,
                           'MAX'       ,
                           SUBSTR(CP.NUM_COMP_PAGO_E,1,4)
                           )|| 'Ã' ||
                CP.TIP_COMP_PAGO|| 'Ã' ||
                'N'

         FROM   VTA_COMP_PAGO CP
         WHERE  CP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CP.COD_LOCAL = cCodLocal_in
         AND    CP.TIP_COMP_PAGO IN (SELECT TIP_COMP.TIP_COMP
                                     FROM   VTA_TIP_COMP TIP_COMP
                                     WHERE  TIP_COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                                     AND    TIP_COMP.IND_NECESITA_IMPR = INDICADOR_SI
                                     OR    TIP_COMP.TIP_COMP = C_NOTA_CRED 
                                     )
         
         --FAC.ELCT. 28.10.2014
         --- Muestra tambien las notas de credito electronicas.
         AND    CP.SEC_MOV_CAJA IN (SELECT CMC.SEC_MOV_CAJA_ORIGEN
                                    FROM   CE_MOV_CAJA CMC
                                    WHERE  CMC.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND    CMC.COD_LOCAL = cCodLocal_in
                                    AND    CMC.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy'))
         AND    NVL(CP.COD_TIP_PROC_PAGO,'0') = '1'   --INDICADOR SI ELECTRONICO
         GROUP BY CP.TIP_COMP_PAGO,
                  SUBSTR(CP.NUM_COMP_PAGO_E,1,4)
                  ;
--FIN

    RETURN curCE;
  END;



end PTOVENTA_CE_LMR;
/
