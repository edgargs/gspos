--------------------------------------------------------
--  DDL for Package PTOVENTA_INT_REP
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_INT_REP" AS

  g_cNumNotaEs PBL_NUMERA.COD_NUMERA%TYPE := '011';
  g_cTipoNotaRecepcion LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '03';

  g_cCodMatriz PBL_LOCAL.COD_LOCAL%TYPE := '009';
  --Descripcion: Procesa envio Pedido Reposicion por local.
  --Fecha       Usuario		Comentario
  --10/04/2006  ERIOS    	Creación
  PROCEDURE PROCESA_PED_REP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vUsu_in IN VARCHAR2);

  --Descripcion: Genera cabecera.
  --Fecha       Usuario		Comentario
  --10/04/2006  ERIOS    	Creación
  FUNCTION GENERAR_CABECERA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            vUsu_in IN VARCHAR2) RETURN CHAR;

  --Descripcion: Genera Guia Remision.
  --Fecha       Usuario		Comentario
  --10/04/2006  ERIOS    	Creación
  PROCEDURE GENERAR_GUIA_REMISION(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                                  cNumNota_in IN CHAR, cNumGuia_in IN CHAR,
                                  nSecGuia_in IN NUMBER, vUsu_in IN VARCHAR2,
                                  vTipo_in IN VARCHAR2);
  --Descripcion: Genera detalle.
  --Fecha       Usuario		Comentario
  --10/04/2006  ERIOS    	Creación
  PROCEDURE GENERAR_DETALLE(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            cNumNota_in IN CHAR, cNumGuia_in IN CHAR,
                            nSecGuia_in IN NUMBER, vUsu_in IN VARCHAR2);

  --Descripcion: Actualiza Cabecera.
  --Fecha       Usuario		Comentario
  --10/04/2006  ERIOS    	Creación
  PROCEDURE ACTUALIZA_CABECERA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                                cNumNota_in IN CHAR, vUsu_in IN VARCHAR2);

  --Descripcion: Actualiza el Maestro de Lote.
  --Fecha       Usuario		Comentario
  --18/05/2006  ERIOS    	Creación
  PROCEDURE ACTUALIZA_MAE_LOTE(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                cCodProd_in IN CHAR,vNumLote_in IN VARCHAR2,dFecVec_in IN VARCHAR2,
                                vUsu_in IN CHAR);

  --Descripcion: Envia mail si el prodecimiento falla.
  --Fecha       Usuario		Comentario
  --22/06/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ENVIA_CORREO_ALERTA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2);
END;

/
