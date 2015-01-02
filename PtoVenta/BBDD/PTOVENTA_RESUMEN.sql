--------------------------------------------------------
--  DDL for Package PTOVENTA_RESUMEN
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_RESUMEN" is

  --Descripcion: Carga la tabla AUX_CONSOLIDADO_CAMP_AUX_CUP
  --Fecha       Usuario	  Comentario
  -- 11/05/2010  JOLIVA     Creacion
  PROCEDURE CARGA_CANT_CUPON_X_CAMPANA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR);

  --Descripcion: Carga la tabla AUX_CONSOLIDADO_CAMP_AUX_VENTA
  --Fecha       Usuario	  Comentario
  -- 11/05/2010  JOLIVA     Creacion
  PROCEDURE CARGA_USO_CUPON_X_CAMPANA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR);

  --Descripcion: Carga la tabla AUX_CONSOLIDADO_PACK
  --Fecha       Usuario	  Comentario
  -- 11/05/2010  JOLIVA     Creacion
  PROCEDURE CARGA_USU_PACKS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR);

  --Descripcion: Carga la tabla AUX_LISTA_PED_FIDELIZADOS
  --Fecha       Usuario	  Comentario
  -- 19/05/2010  JOLIVA     Creacion
  PROCEDURE CARGA_AUX_LISTA_PED_FID(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR);

  --Descripcion: Carga la tabla AUX_VTA_FID_DIA_LOCAL
  --Fecha       Usuario	  Comentario
  -- 19/05/2010  JOLIVA     Creacion
  PROCEDURE CARGA_AUX_VTA_FID_DIA_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR);

  --Descripcion: Invoca a todos los procedimientos de generacion de resumenes que se deben correr en la madrugada
  --Fecha       Usuario	  Comentario
  -- 21/05/2010  JOLIVA     Creacion
  PROCEDURE CARGA_RESUMENES(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR);

  --Descripcion: Carga la tabla AUX_CONS_REP_CAMP
  --Fecha       Usuario	  Comentario
  -- 21/05/2010  JOLIVA     Creacion
  PROCEDURE CARGA_AUX_CONS_REP_CAMP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR);

  --Descripcion: Carga la tabla AUX_CONS_CAMP_AUX_CUP_V2
  --Fecha       Usuario	  Comentario
  -- 17/06/2010  JMIRANDA     Creacion
  PROCEDURE CARGA_CANT_CUPON_X_CAMPANA_2(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR);

  --Descripcion: Invoca a todos los NUEVOS procedimientos de generacion de resumenes que se deben correr en la madrugada
  --Fecha       Usuario	  Comentario
  -- 17/06/2010  JMIRANDA     Creacion
  PROCEDURE CARGA_RESUMENES_2(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR);

  --Descripcion: Genera el nuevo resumen
  --Fecha       Usuario	  Comentario
  -- 17/06/2010  JMIRANDA     Creacion
  PROCEDURE CARGA_AUX_CONS_REP_CAMP_2(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cFechaVta 	IN CHAR);

  --Descripcion: Genera el nuevo resumen packs dscto
  --Fecha       Usuario	  Comentario
  -- 18/06/2010  JMIRANDA     Creacion
  PROCEDURE CARGA_USU_PACKS_2(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cFechaVta 	IN CHAR);

  --Descripcion: Genera el nuevo resumen DE CAMPANA CUPON X DETALLE
  --Fecha       Usuario	  Comentario
  -- 15/07/2010  JMIRANDA     Creacion
  PROCEDURE CARGA_AUX_VTA_DET (cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cFechaVta 	IN CHAR);
end;

/
