--------------------------------------------------------
--  DDL for Package Body PTOVENTA_STOCK_LOCAL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "DELIVERY"."PTOVENTA_STOCK_LOCAL" AS

  FUNCTION INV_STOCK_LOCALES_PREFERIDOS(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodProd_in     IN CHAR,
                                        cIndVerTodos_in IN CHAR DEFAULT 'N')
  RETURN FarmaCursor
  IS
    curInv FarmaCursor;
  BEGIN

    OPEN curInv FOR
         SELECT PROD_LOCAL.cod_local || '?' ||
                LOCAL.DESC_CORTA_LOCAL || '?' ||
                to_char(PROD_LOCAL.STK_FISICO - PROD_LOCAL.STK_COMPROMETIDO,'999,990')|| '?' ||
                decode(PROD_LOCAL.Ind_Prod_Fraccionado,'S',PROD_LOCAL.unid_vta,' ')
         FROM   LGT_PROD_LOCAL PROD_LOCAL,
                PBL_LOCAL LOCAL
         WHERE  PROD_LOCAL.COD_GRUPO_CIA =   cCodGrupoCia_in
         AND   (PROD_LOCAL.COD_LOCAL IN (SELECT COD_LOCAL_PREFERIDO
                                FROM   PBL_LOCAL_PREFERIDO
                                WHERE  COD_LOCAL =  ccodlocal_in) OR 'S'= cIndVerTodos_in)
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

/* ************************************************************************ */

    FUNCTION INV_OBTIENE_IND_STOCK(cCodGrupoCia_in IN CHAR,
                                 cCodProd_in IN CHAR)
  RETURN VARCHAR2

  IS
   v_vSentencia VARCHAR2(10);
  BEGIN
    --DUBILLUZ 07.07.2015 Se modifica el query.
	SELECT (CASE WHEN CTD_STK_LIMA > 0 THEN 'S' ELSE 'N' END) || '?' ||
		   (CASE WHEN CTD_STK_PROV > 0 THEN 'S' ELSE 'N' END) || '?' ||
		   (CASE WHEN CTD_STK_ALM  > 0 THEN 'S' ELSE 'N' END)
		   into v_vSentencia
	FROM (
	Select ----------STOCK LIMA
			 SUM(case when pl.ind_local_prov = 'N' and lgt.COD_LOCAL != '009' THEN 0 ELSE 1 END) CTD_STK_LIMA,
			 ----------STOCK PROVINCIA
			 SUM(case when pl.ind_local_prov = 'S' and lgt.COD_LOCAL != '009' THEN 0 ELSE 1 END) CTD_STK_PROV,
			 ----------STOCK ALMACEN
			 SUM(CASE WHEN lgt.COD_LOCAL = '009' THEN 1 ELSE 0 END) CTD_STK_ALM
	   FROM LGT_PROD_LOCAL  lgt,
			pbl_local pl 
	   WHERE lgt.cod_grupo_cia = cCodGrupoCia_in
	   and   lgt.COD_PROD = cCodProd_in
	   AND   lgt.STK_FISICO > 0  
	   and   lgt.cod_grupo_cia = pl.cod_grupo_cia
	   and   lgt.cod_local = pl.cod_local
	   );

   dbms_output.put_line(v_vSentencia);
 RETURN V_VSENTENCIA ;
 END;


end Ptoventa_STOCK_LOCAL;

/
