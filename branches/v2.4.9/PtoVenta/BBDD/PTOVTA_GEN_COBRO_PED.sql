--------------------------------------------------------
--  DDL for Package PTOVTA_GEN_COBRO_PED
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVTA_GEN_COBRO_PED" is

TYPE FarmaCursor IS REF CURSOR;

--Descripcion: Indicador para saber si se llama a la nueva ventana de cobro o no
--Fecha       Usuario   Comentario
--16/02/2010  ASOSA     Creación
FUNCTION PVTA_F_ELEGIR_VENT_COBRO
RETURN CHAR;

--Descripcion: Indicador para saber si se debe llamar a la nueva ventana de cobro o se debe de cobrar en el resumen de venta
--Fecha       Usuario   Comentario
--16/02/2010  ASOSA     Creación
FUNCTION PVTA_F_NEW_VENT_COBRO(cCia_in IN CHAR,
                               cLocal_in IN CHAR,
                               cNumPed_in IN CHAR)
RETURN CHAR;

--Descripcion: Inssertar las formas de pago con las cuales se pago un pedido. (Se agrego el nro de DNI del propietario de la tarjeta, el codigo de voucher o autorizacion y el codigo de lote)
--Fecha       Usuario   Comentario
--23/02/2010  ASOSA     Creación
PROCEDURE PVTA_P_INS_FORM_PAGO_PED(cCodGrupoCia_in 	 	     IN CHAR,
	                                   	   cCodLocal_in    	 	     IN CHAR,
										                     cCodFormaPago_in   	   IN CHAR,
									   	                   cNumPedVta_in   	 	     IN CHAR,
									   	                   nImPago_in		 		       IN NUMBER,
										                     cTipMoneda_in			     IN CHAR,
									  	                   nValTipCambio_in 	 	   IN NUMBER,
								   	  	                 nValVuelto_in  	 	     IN NUMBER,
								   	  	                 nImTotalPago_in 		     IN NUMBER,
									  	                   cNumTarj_in  		 	     IN CHAR,
									  	                   cFecVencTarj_in  		   IN CHAR,
									  	                   cNomTarj_in  	 		     IN CHAR,
                                         cCanCupon_in  	 		     IN NUMBER,
									  	                   cUsuCreaFormaPagoPed_in IN CHAR,
                                         cDNI_in                 IN CHAR,
                                         vCodvou_in              IN VARCHAR,
                                         cLote_in                IN VARCHAR);

--Descripcion: Obtener la informacion de la tarjeta ingresada y la forma de pago a la que pertenece
--Fecha       Usuario   Comentario
--24/02/2010  ASOSA     Creación
FUNCTION PVTA_F_OBTENER_TARJETA(cCodCia_in IN CHAR,
                                cCodTarj_in IN VARCHAR)
RETURN FarmaCursor;

--Descripcion: Saber si el pedido es recarga virtual y ademas con convenio credito
--Fecha       Usuario   Comentario
--24/02/2010  ASOSA     Creación
FUNCTION PVTA_F_RECVIR_CONVCRED(cCia_in IN CHAR,
                               cLocal_in IN CHAR,
                               cNumPed_in IN CHAR)
RETURN VARCHAR2;

--Descripcion: Obtener la forma de pago de un convenio
--Fecha       Usuario   Comentario
--01/03/2010  ASOSA     Creación
FUNCTION PVTA_F_OBTENER_FPAGO(cCia_in IN CHAR,
                              cConv_in IN CHAR)
RETURN VARCHAR2;

--Descripcion: Obtengo el detalle de un pedido
--Fecha       Usuario   Comentario
--05/03/2010  ASOSA     Creación
FUNCTION PVTA_LISTA_DETA_PED(cCodGrupoCia_in IN CHAR,
  		   						           cCod_Local_in   IN CHAR,
								               cNum_Ped_Vta_in IN CHAR)
RETURN FarmaCursor;

--Descripcion: Obtengo el valor de tolerancia de diferencia solo cuando la tarjeta es en dolares
--Fecha       Usuario   Comentario
--26/05/2010  ASOSA     Creación
FUNCTION PVTA_F_GET_TOLERA_DOLARES(cCodGia_in IN CHAR)
RETURN VARCHAR2;

--Descripcion: Enviar un correo informando que se cobro un pedido utilizando "el cambio de precio ¡¡¡"
--Fecha       Usuario   Comentario
--26/05/2010  ASOSA     Creación
PROCEDURE PVTA_P_ENVIAR_MAIL_DIFERENCIA(cCodCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        cNumPed_in IN CHAR,
                                        vSoles_in IN VARCHAR2,
                                        vDolares_in IN VARCHAR2,
                                        vMonto_in IN VARCHAR2,
                                        vMonto2_in IN VARCHAR2,
                                        vDiferencia_in IN VARCHAR2);

--Descripcion: Salvo las forma de pago en cierre de dia"
--Fecha       Usuario   Comentario
--03/06/2010  jquispe     Creación
PROCEDURE PVTA_P_CHANGE_FORM_PAGO_PED(cCodGrupoCia_in 	 	     IN CHAR,
	                                   	   cCodLocal_in    	 	     IN CHAR,
										                     cCodFormaPago_in   	   IN CHAR,
									   	                   cNumPedVta_in   	 	     IN CHAR,
									   	                   nImPago_in		 		       IN NUMBER,
										                     cTipMoneda_in			     IN CHAR,
									  	                   nValTipCambio_in 	 	   IN NUMBER,
								   	  	                 nValVuelto_in  	 	     IN NUMBER,
								   	  	                 nImTotalPago_in 		     IN NUMBER,
									  	                   cNumTarj_in  		 	     IN CHAR,
									  	                   cFecVencTarj_in  		   IN CHAR,
									  	                   cNomTarj_in  	 		     IN CHAR,
                                         cCanCupon_in  	 		     IN NUMBER,
									  	                   cUsuCreaFormaPagoPed_in IN CHAR,
                                         cDNI_in                 IN CHAR,
                                         vCodvou_in              IN VARCHAR,
                                         cLote_in                IN VARCHAR);

--Descripcion: Save de forma de pago en cierre de dia
--Fecha       Usuario   Comentario
--03/06/2010  jquispe     Creación
PROCEDURE PVTA_P_SAVE_FORM_PAGO_PED(cCodGrupoCia_in 	 	     IN CHAR,
	                                  cCodLocal_in    	 	     IN CHAR,
									   	              cNumPedVta_in   	 	     IN CHAR
									   	                );

--Descripcion: Borrar de forma de pago en cierre de dia"
--Fecha       Usuario   Comentario
--03/06/2010  jquispe     Creación
PROCEDURE PVTA_P_DEL_FORM_PAGO_PED(cCodGrupoCia_in 	 	     IN CHAR,
                              	   cCodLocal_in    	 	 IN CHAR,
						   	                   cNumPedVta_in   	 	 IN CHAR
									   	                );



END;

/
