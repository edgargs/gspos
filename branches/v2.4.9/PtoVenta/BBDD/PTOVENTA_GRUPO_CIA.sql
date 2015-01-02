--------------------------------------------------------
--  DDL for Package PTOVENTA_GRUPO_CIA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_GRUPO_CIA" AS

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Busca cliente juridico por RUC o por Razon Social
  --Fecha       Usuario		Comentario
  --16/08/2010  jquispe     Creación
  FUNCTION CIA_GET_COD_GRUPO_CIA
	RETURN CHAR;


  FUNCTION VTA_F_VERIFICAR_PED_COMP(vCodGrupoCia_in IN CHAR,
                                    vNumPedVta_in   IN VARCHAR2,
                                    vCodLocal_in    IN CHAR)
  RETURN CHAR;

  END;

/
