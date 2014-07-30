--------------------------------------------------------
--  DDL for Package FARMA_UTILITY
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."FARMA_UTILITY" AS

  /**
  * Copyright (c) 2006 MiFarma Peru S.A.
  *
  * Entorno de Desarrollo : Oracle9i
  * Nombre del Paquete    : FARMA_UTILITY
  *
  * Histórico de Creación/Modificación
  * RCASTRO       15.01.2006   Creación
  * LMESIA        10.07.2006   Modificacion stock
  *
  * @author Rolando Castro
  * @version 1.0
  *
  */

  TYPE FarmaCursor IS REF CURSOR;

  PROCEDURE LIBERAR_TRANSACCION;

  PROCEDURE ACEPTAR_TRANSACCION;

  -- Ejemplo de uso :
  --      COMPLETAR_CON_SIMBOLO(54,10,'*','D');
  --      resultado : 54********
  --      COMPLETAR_CON_SIMBOLO(54,10,'0','I');
  --      resultado : 0000000054
  FUNCTION COMPLETAR_CON_SIMBOLO(nValor_in    IN NUMBER,
                                 iLongitud_in IN INTEGER,
                   cSimbolo_in  IN CHAR,
                   cUbica_in    IN CHAR)
           RETURN VARCHAR2;


  FUNCTION OBTENER_REDONDEO(nValor_in IN NUMBER,
                            cTipo_in  IN CHAR)
           RETURN NUMBER;

  --Descripcion: Obtiene el VALOR DE LA TABLA NUMERACION
  --Fecha       Usuario    Comentario
  --27/01/2006  LMESIA     Creación
  FUNCTION OBTENER_NUMERACION(cCodGrupoCia_in   IN CHAR,
                cCodLocal_in     IN CHAR,
                cCodNumera_in    IN CHAR)
         RETURN NUMBER;

  --Descripcion: Actualiza un registro de la tabla numeracion Sin Commit
  --Fecha       Usuario    Comentario
  --27/01/2006  LMESIA     Creación
  PROCEDURE ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in  IN CHAR,
                     cCodLocal_in      IN CHAR,
                     cCodNumera_in     IN CHAR,
                         vIdUsuario_in     IN VARCHAR2);

  --Descripcion: Inicializa un registro de la tabla numeracion Sin Commit
  --Fecha       Usuario    Comentario
  --27/01/2006  LMESIA     Creación
  PROCEDURE INICIALIZA_NUMERA_SIN_COMMIT(cCodGrupoCia_in  IN CHAR,
                     cCodLocal_in      IN CHAR,
                     cCodNumera_in     IN CHAR,
                         vIdUsuario_in     IN VARCHAR2);

  --Descripcion: Obtiene el tipo de cambio para un dia determinado
  --Fecha       Usuario    Comentario
  --03/02/2006  LMESIA     Creación
  FUNCTION OBTIENE_TIPO_CAMBIO(cCodGrupoCia_in IN CHAR,
                    cFecCambio_in   IN CHAR)
           RETURN NUMBER;

  --Descripcion: Ejecuta las acciones en la tabla respaldo stock
  --Fecha       Usuario    Comentario
  --22/02/2006  LMESIA     CreaciÃ³n
  PROCEDURE EJECUTA_RESPALDO_STK(cCodGrupoCia_in   IN CHAR,
                                cCodLocal_in      IN CHAR,
                                cNumIpPc_in         IN CHAR,
                                cCodProd_in       IN CHAR,
                 cNumPedVta_in       IN CHAR,
                 cTipoOperacion_in  IN CHAR,
                                nCantMov_in         IN NUMBER,
                 nValFracc_in    IN NUMBER,
                    vIdUsuario_in     IN VARCHAR2,
                                                                 cModulo_in IN CHAR);

  --Descripcion: Ejecuta la recuperacion de los stocks de los productos
  --Fecha       Usuario    Comentario
  --22/02/2006  LMESIA     Creación
  PROCEDURE RECUPERACION_RESPALDO_STK(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cNumIpPc_in     IN CHAR,
                    vIdUsuario_in   IN VARCHAR2);

  --Descripcion: Verifica si es valdo o no el Ruc ingresado
  --Fecha       Usuario    Comentario
  --22/02/2006  LMESIA     Creación
  FUNCTION VERIFICA_RUC_VALIDO(cNumRuc_in IN CHAR)
    RETURN CHAR;

  /*******************************************************************/

FUNCTION GENERA_PEDIDO_SCRIPT(cCodGrupoCia_in    IN CHAR,
            cCodLocal_in      IN CHAR,
            cNumPedVtaCopia_in IN CHAR,
            nDiasRestaFecha_in IN NUMBER)
RETURN CHAR;

  FUNCTION COBRA_PEDIDO_SCRIPT(cCodGrupoCia_in     IN CHAR,
                    cCodLocal_in        IN CHAR,
                 cNumPedVta_in       IN CHAR,
                 cNumPedVtaCopia_in IN CHAR,
                 cSecMovCaja_in    IN CHAR,
                 nDiasRestaFecha_in IN NUMBER)
    RETURN CHAR;

  PROCEDURE GENERA_COBRA_PEDIDO_SCRIPT(cCodGrupoCia_in    IN CHAR,
                     cCodLocal_in      IN CHAR,
                     cNumPedVtaCopia_in IN CHAR,
                     cSecMovCaja_in    IN CHAR,
                        nDiasRestaFecha_in IN NUMBER);

  /*********************************************************************/

  FUNCTION VERIFICA_STOCK_RESPALDO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cCodProd_in     IN CHAR)
    RETURN NUMBER;

  /**********************************************************************/

  FUNCTION OBTIENE_CANTIDAD_SESIONES(cNombrPc_in    IN CHAR,
                                     cUsuarioCon_in IN CHAR)
    RETURN NUMBER;

  /**********************************************************************/

  PROCEDURE ENVIA_CORREO(cCodGrupoCia_in       IN CHAR,
                         cCodLocal_in          IN CHAR,
                         vReceiverAddress_in   IN CHAR,
                         vAsunto_in            IN CHAR,
                         vTitulo_in            IN CHAR,
                         vMensaje_in           IN CHAR,
                         vCCReceiverAddress_in IN CHAR);

  /**********************************************************************/

  FUNCTION OBTIEN_NUM_DIA(cDate_in IN DATE)
    RETURN VARCHAR2;

  /* ****************************************************************** */
  --Descripcion: Obtiene el Tiempo Estimado de Consulta
  --Fecha       Usuario        Comentario
  --19/08/2008  DUBILLUZ      Creación
  FUNCTION GET_TIEMPOESTIMADO_CONEXION(cCodGrupoCia_in  IN CHAR)
                                           RETURN varchar2;

  /* ****************************************************************** */
  --Descripcion: Obtiene el TIME OUT CONN REMOTA
  --Fecha       Usuario        Comentario
  --19/08/2008  DUBILLUZ      Creación
  FUNCTION GET_TIME_OUT_CONN_REMOTA(cCodGrupoCia_in  IN CHAR)
                                    RETURN varchar2;

  /* ****************************************************************** */
  --Descripcion: Obtiene tipo de cambio del dia de la venta, o ultimo vigente
  --Fecha       Usuario        Comentario
  --19/05/2009  JCORTEZ      Creación
  FUNCTION OBTIENE_TIPO_CAMBIO2(cCodGrupoCia_in IN CHAR,
                                cFecCambio_in   IN CHAR)
  RETURN NUMBER;

function split(input_list   varchar2,
               ret_this_one number,
               delimiter    varchar2) return varchar2;

  --Descripcion: Obtiene tipo de cambio del dia de la venta, o ultimo vigente
  --Fecha       Usuario        Comentario
  --18/1/2013  AESCATE      Creacion
	function FN_DEV_TIPO_CAMBIO_F(cCodGrupoCia_in IN CHAR,
													cFecCambio_in   IN CHAR,
													A_cod_cia       char DEFAULT NULL,
													A_COD_LOCAL     char DEFAULT NULL,
													A_TIPO_RCD      CHAR DEFAULT NULL)
	  RETURN NUMBER;
	  
  /* ****************************************************************** */
  --Descripcion: Obtiene tipo de cambio del dia
  --Fecha       Usuario        Comentario
  --19/12/2013  ERIOS          Creacion
  FUNCTION OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in IN CHAR,
								cCodCia_in IN CHAR,
                                cFecCambio_in   IN CHAR,
								cIndTipo_in IN CHAR)  
  RETURN NUMBER;
  
  /* ****************************************************************** */
  --Descripcion: Obtiene las iniciales del texto ingresado
  --Fecha       Usuario        Comentario
  --03/02/2014  LLEIVA         Creacion
  FUNCTION OBTIENE_INICIALES(cTexto IN CHAR)  
  RETURN VARCHAR2;

  /* ****************************************************************** */
  --Descripcion: Obtiene la fecha actual del sistema en formato ddmmyyyy
  --Fecha       Usuario        Comentario
  --07/02/2014  LLEIVA         Creacion
  FUNCTION GET_FECHA_ACTUAL
  RETURN VARCHAR2;
  
  --Descripcion: Ajusta el redondeo sobre el listado de precio de venta
  --Fecha       Usuario        Comentario
  --11.03.2014  CHUANES         Creacion
  
  FUNCTION OBTENER_REDONDEO2(nValor_in IN NUMBER)
                            
   RETURN NUMBER ;

 FUNCTION PING(p_HOST_NAME VARCHAR2,
             p_PORT      NUMBER DEFAULT 1000)
  RETURN VARCHAR2;   
  
END Farma_Utility;

/
