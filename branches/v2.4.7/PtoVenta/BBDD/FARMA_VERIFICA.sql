--------------------------------------------------------
--  DDL for Package FARMA_VERIFICA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."FARMA_VERIFICA" AS

  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;
  v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
  --SendorAddress  VARCHAR2(30)  := ' <oracle@mifarma.com.pe>';
  --EmailServer     VARCHAR2(30) := '192.168.0.236';
  --EmailServer     VARCHAR2(30) := '10.11.1.252';

  --Descripcion: Verifica el kardex de cada producto de un local.
  --Fecha       Usuario		Comentario
  --02/05/2006  ERIOS    	Creación
  PROCEDURE VER_PROD_KARDEX(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cIndImpProdOk_in IN CHAR DEFAULT NULL);

  --Descripcion: Corrige Kardex, bajo su propio riesgo.
  --Fecha       Usuario		Comentario
  --02/06/2006  ERIOS    	Creación
  PROCEDURE CORRECCION_PROD_KARDEX(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cCodProd_in IN CHAR,vIdUsu_in IN VARCHAR2);

  --Descripcion: Verifica comprobantes.
  --Fecha       Usuario		Comentario
  --30/06/2006  ERIOS    	Creación
  PROCEDURE VERIFICA_COMPROBANTES(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecIni_in IN VARCHAR2,vFecFin_in IN VARCHAR2);

  --Descripcion: Graba Log de Comprobantes Faltantes.
  --Fecha       Usuario		Comentario
  --30/06/2006  ERIOS    	Creación
  PROCEDURE GRABA_LOG_COMP(cCodLocal_in IN CHAR,cTipDoc_in IN CHAR,vDescDoc_in IN VARCHAR2,cSerie_in IN CHAR,dFechaDoc_in IN DATE,cNumFalta_in IN CHAR,cNumFaltaFin_in IN CHAR);

  --Descripcion: Envía mail de comprobantes faltantes.
  --Fecha       Usuario		Comentario
  --30/06/2006  ERIOS    	Creación
  PROCEDURE VER_ENVIA_CORREO_ALERTA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

  --Descripcion: Envía mail de productos falta cero.
  --Fecha       Usuario		Comentario
  --30/06/2006  ERIOS    	Creación
  PROCEDURE VER_ENVIA_FALTA_CERO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);
END;

/
