--------------------------------------------------------
--  DDL for Package Body PTOVENTA_INV
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "DELIVERY"."PTOVENTA_INV" AS

  FUNCTION INV_STOCK_LOCALES_PREFERIDOS(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodProd_in     IN CHAR,
                                        cIndVerTodos_in IN CHAR DEFAULT 'N')
  RETURN FarmaCursor
  IS
    curInv FarmaCursor;
	cantPreferidos INTEGER;
	v_cCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN

  SELECT count(COD_LOCAL_PREFERIDO) INTO cantPreferidos
                                FROM   PBL_LOCAL_PREFERIDO
                                WHERE  COD_LOCAL =  ccodlocal_in;

	SELECT COD_CIA into v_cCodCia
	FROM PBL_LOCAL
	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
	AND COD_LOCAL = cCodLocal_in;								
								
    OPEN curInv FOR
         SELECT PROD_LOCAL.cod_local || 'Ã' ||
                LOCAL.DESC_CORTA_LOCAL || 'Ã' ||
                to_char(PROD_LOCAL.STK_FISICO - PROD_LOCAL.STK_COMPROMETIDO,'999,990')|| 'Ã' ||
                decode(PROD_LOCAL.Ind_Prod_Fraccionado,'S',PROD_LOCAL.unid_vta,' ')
         FROM   LGT_PROD_LOCAL PROD_LOCAL,
                PBL_LOCAL LOCAL
         WHERE  PROD_LOCAL.COD_GRUPO_CIA =   cCodGrupoCia_in
		 AND LOCAL.COD_CIA = v_cCodCia
         AND   (PROD_LOCAL.COD_LOCAL IN (SELECT COD_LOCAL_PREFERIDO
                                FROM   PBL_LOCAL_PREFERIDO
                                WHERE  COD_LOCAL =  ccodlocal_in) OR (0=cantPreferidos AND 'S'= cIndVerTodos_in))
         AND    LOCAL.Tip_Local =  g_cTipoLocalVenta
         AND    LOCAL.EST_LOCAL = 'A'
         AND    PROD_LOCAL.cod_prod = ccodprod_in
         AND    LOCAL.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
         AND    LOCAL.COD_LOCAL = PROD_LOCAL.COD_LOCAL ;

    RETURN curInv;

  END;

  --Descripcion: oBTIENE TIEM STAM
  --Fecha       Usuario		    Comentario
  --12/09/2007  DUBILLUZ      Creacion
  FUNCTION CONSULTA_LOCAL(cCodGrupoCia_in  IN CHAR)
                                                RETURN varchar2
   IS
      v_valor VARCHAR2(3);
   BEGIN

   SELECT COD_LOCAL into v_valor
   FROM LGT_PROD_LOCAL WHERE ROWNUM < 2;

   return  v_valor;
   END;




end Ptoventa_Inv;

/
