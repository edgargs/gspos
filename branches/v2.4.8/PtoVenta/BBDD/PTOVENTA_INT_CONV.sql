--------------------------------------------------------
--  DDL for Package PTOVENTA_INT_CONV
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_INT_CONV" is

  --ESTADO_ACTIVO		  CHAR(1):='A';
	--ESTADO_INACTIVO		CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';
	POS_INICIO		    CHAR(1):='I';

  TIP_MONEDA_SOLES CHAR(2) := '01';
  TIP_MONEDA_DOLARES CHAR(2) := '02';
  v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
  ARCHIVO_TEXTO      UTL_FILE.FILE_TYPE;

  C_C_ESTADO_ACTIVO           CHAR(1) := 'A';
  C_C_ESTADO_INACTIVO         CHAR(1) := 'I';

  C_COD_GRUPO_CIA             CHAR(3) := '001';
  C_COD_LOCAL                 CHAR(3) := '009';
  C_COD_CONVENIO              CHAR(10):= '0000000001';

  --vFechaActual                DATE    := SYSDATE;
  vPrefijo                    CHAR(4) := 'CONV';
  vSumaDia                    NUMBER  := 0;
  vCantFilasAfect             NUMBER  := 0;

  C_COD_NUMERA_COD_CLIENTE    CHAR(3) := '032';
  vNombreDirectorio           VARCHAR2(15) := 'DIR_SAP_EMP';
  vNombreArchivo              VARCHAR2(50) := 'Log_Carga_Trab_QS_'||
                                              TO_CHAR(SYSDATE,'dd')||'-'||
                                              TO_CHAR(SYSDATE,'mm')||'-'||
                                              TO_CHAR(SYSDATE,'yyyy')||'.txt';

 TYPE FarmaCursor IS REF CURSOR;

 PROCEDURE INT_GRABA_INTERFCAE_CONV (cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cCodTrabConv_in IN CHAR,
                                     cCodConvenio_in IN CHAR,
                                     cValImporte_in  IN CHAR,
                                     cNomTrab_in     IN CHAR,
                                     cUsuCrea_in     IN CHAR);

/* PROCEDURE INT_EJECUTA_CONVENIOS(cCodGrupoCia_in IN CHAR,
                                 cCodConvenio_in IN CHAR,
                                 cDiaInicio_in IN CHAR,
                                 cDiaFin_in IN CHAR,
                                 cFechaInicio_in IN CHAR,
                                 cFechaFin_in IN CHAR);
*/
/* PROCEDURE INT_EJECUTA_CIERRE_CONV(cCodGrupoCia_in IN CHAR);*/

 FUNCTION  INT_GENERA_ARCHIVO(cCodGrupoCia_in IN CHAR,
                              cCodEmpresa_in IN CHAR)
 RETURN VARCHAR2;

 PROCEDURE INT_ACTUALIZA_PROCESO;

 PROCEDURE INT_ENVIA_CORREO_INFORMACION(vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCodGrupoCia_in IN CHAR,
                                        cCodEmpresa_in IN CHAR);

/* PROCEDURE INT_GENERA_DETALLE_COMP(cCodGrupoCia_in IN CHAR,
                                   cCodConvenio_in IN CHAR,
                                   cDiaInicio_in IN CHAR,
                                   cDiaFin_in IN CHAR,
                                   cFechaInicio_in IN CHAR,
                                   cFechaFin_in IN CHAR);

*/----------------------------------------------------------------------


  --Descripcion: Actualiza la relacion trabajador- convenio
  --Fecha       Usuario		Comentario
  --16/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_CARGA_TRAB_QS;*/

  --Descripcion: Carga el archivo enviado por QS
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_INSERTA_AUX_TRAB_QS;*/

  --Descripcion: Carga el maestro de trabajadores QS con los nuevos trabajadores
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_INSERTA_TRAB_QS;*/

  --Descripcion: Actualiza el estado del trabajador, en caso no se encuentre en el archivo
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_INACTIVA_TRAB_QS;--INT_ACTUALIZA_ESTADO_TRAB_QS;*/

  --Descripcion: Inserta la relacion trabajador - convenio
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_INSERTA_CON_CLI_CONV(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR);*/

  --Descripcion: Actualiza el estado del trabajador en la tabla TMP_CON_CLI_CONV
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_INACTIVA_TRAB_CONV;*/

  --Descripcion: Obtiene un cliente por el DNI
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
  --FUNCTION INT_OBTIENE_CLIENTE_X_DNI(cDni_in    IN CHAR) RETURN FarmaCursor;

  --Descripcion: Inserta en el maestro de trabajadores
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
/*  FUNCTION INT_INSERTA_CLIENTE_TRAB(cCodGrupoCia_in      IN CHAR,
                                    cCodLocal_in         IN CHAR,
                                    cNomCompletoCli_in   IN CHAR,
                                    cTipoDoc_in          IN CHAR,
                                    cNumDoc_in           IN CHAR) RETURN CHAR;*/

  --Descripcion: Activa los trabajadores de QS
  --Fecha       Usuario		Comentario
  --18/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_ACTIVA_TRAB_QS;*/

  --Descripcion: Inactiva los trabajadores de QS
  --Fecha       Usuario		Comentario
  --18/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_ACTIVA_TRAB_CONV;*/

  --Descripcion: GRABA CONVENIOS POR EMPRESAS
  --Fecha       Usuario		Comentario
  --09/05/2007  PAULO     Creacion
   PROCEDURE INT_EJECUTA_CONVENIOS_EMPR(cCodGrupoCia_in IN CHAR,
                                        cCodConvenio_in IN CHAR,
                                        cFechaInicio_in IN CHAR,
                                        cFechaFin_in IN CHAR);
  --Descripcion: EJECUTA CONVENIOR PARA EMPRESAS
  --Fecha       Usuario		Comentario
  --09/05/2007  PAULO     Creacion
  PROCEDURE INT_EJECUTA_CIERRE_CONV_EMPR(cCodGrupoCia_in IN CHAR);

  --Descripcion: GENERA ARCHIVO DE COMPROBANTES
  --Fecha       Usuario		Comentario
  --09/05/2007  PAULO     Creacion
  PROCEDURE INT_GENERA_DETALLE_COMP_EMPR(cCodGrupoCia_in IN CHAR,
                                         cCodConvenio_in IN CHAR,
                                         cFechaInicio_in IN CHAR,
                                         cFechaFin_in IN CHAR);

  --Descripcion: OBTIENE LA FECHA DE INICIO DEL CICLO DE FACTURACION PARA EL CONVENIO
  --Fecha       Usuario		Comentario
  --04/07/2007  PAULO     Creacion
  FUNCTION INT_OBTIENE_FECHA_INICIO_FACT(cCodGrupoCia_in IN CHAR,
                                         cCodEmpresa_in IN CHAR)
  RETURN CHAR;

  --Descripcion: OBTIENE LA FECHA DE FIN DEL CICLO DE FACTURACION PARA EL CONVENIO
  --Fecha       Usuario		Comentario
  --04/07/2007  PAULO     Creacion
  FUNCTION INT_OBTIENE_FECHA_FIN_FACT(cCodGrupoCia_in IN CHAR,
                                      cCodEmpresa_in IN CHAR)
  RETURN CHAR;

  --Descripcion: OBTIENE los numeros de comprobantes para los pedidos
  --Fecha       Usuario		Comentario
  --24/09/2007  PAULO     Creacion
    FUNCTION INT_OBTIENE_COMP_PEDIDO (cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNumPedVta IN CHAR)
   RETURN VARCHAR2;

  --Descripcion: MUESTRA LOS PEDIDOS DE LOS COLABORADORES CON SUS CREDITOS UTILIZADOS Y LOS COMPROBANTES CONCATENADOS
  --Fecha       Usuario		Comentario
  --24/09/2007  PAULO     Creacion
  PROCEDURE INT_CREDITOS_PEDIDO(cCodGrupoCia_in IN CHAR,
                                cCodConvenio_in IN CHAR,
                                cFechaInicio_in IN CHAR,
                                cFechaFin_in IN CHAR);




end PTOVENTA_INT_CONV;

/
