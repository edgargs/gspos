--------------------------------------------------------
--  DDL for Package PTOVENTA_PED_ADICIONAL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_PED_ADICIONAL" is

  -- Author  : DUBILLUZ
  -- Created : 09/09/2008 12:18:41 p.m.
  -- Purpose :

  TYPE FarmaCursor IS REF CURSOR;
  cCodGrupoIns     CHAR(3):='010';--codGrupo insumo.
  
  
  --Descripcion: LISTA LOS PRODUCTOS CON EL SUGERIDO DEL ULTIMO PEDIDO DE REPOSICION
  --Fecha       Usuario		Comentario
  --10/09/2008  DVELIZ    CREACION
  FUNCTION PED_F_CUR_LISTA_PROD_PED(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: OBTIENE EL DETALLE DE REPOSICION POR PEDIDO
  --Fecha       Usuario		Comentario
  --10/09/2008  DVELIZ    CREACION
  FUNCTION PED_F_CUR_DET_PROD_PED(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodProd_in     IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: INSERTA CANTIDAD SOLICITADA AL PEDIDO ADICIONAL LOCAL
  --Fecha       Usuario		Comentario
  --10/09/2008  DVELIZ    CREACION
  PROCEDURE PED_P_INSER_PED_REP_ADI_LOCAL(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCodProd_in     IN CHAR,
                                          cCantSol_in     IN NUMBER,
                                          cCantAuto_in    IN NUMBER,
                                          cIndAutori_in   IN CHAR,
                                          cCodUsu_in      IN VARCHAR2);

  --Descripcion: INSERTA CANTIDAD SOLICITADA AL PEDIDO ADICIONAL MATRIZ
  --Fecha       Usuario		Comentario
  --10/09/2008  DVELIZ    CREACION
  PROCEDURE PED_P_INSER_PED_REP_ADI_MATRIZ(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCodProd_in     IN CHAR,
                                          cCantAuto_in    IN NUMBER,
                                          cIndAutori_in   IN CHAR,
                                          cCodUsu_in      IN VARCHAR2);

  --Descripcion: MUESTRA EL RESUMEN DEL PEDIDO ADICIONAL
  --Fecha       Usuario		Comentario
  --12/09/2008  DVELIZ    CREACION
  FUNCTION PED_F_CUR_LISTA_PED_BK(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodProd_in     IN CHAR)
  RETURN FarmaCursor;

  FUNCTION PED_F_CUR_CARGAR_MESES
  RETURN FarmaCursor;

  FUNCTION PED_F_CHAR_GET_TRANSITO (cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodProd_in     IN CHAR)
  RETURN VARCHAR2;
  
  
  --Descripcion: MUESTRA PRODUCTO Y SU MOVIMIENTO EN LOS ULTIMOS MESES
  --Fecha       Usuario		Comentario
  --12/09/2008  DVELIZ    CREACION  
    FUNCTION PED_F_PROD_PED_INS(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cCodProd        IN CHAR)

    RETURN FarmaCursor;

end;

/
