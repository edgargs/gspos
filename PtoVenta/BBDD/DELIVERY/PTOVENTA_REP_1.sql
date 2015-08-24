--------------------------------------------------------
--  DDL for Package Body PTOVENTA_REP
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "DELIVERY"."PTOVENTA_REP" AS

    /* ************************************************************************ */

    FUNCTION INV_OBTIENE_IND_STOCK(cCodGrupoCia_in IN CHAR,
                                 cCodProd_in IN CHAR)
  RETURN VARCHAR2

  IS
   v_vSentencia VARCHAR2(10);
  BEGIN
    --DUBILLUZ 07.07.2015 Se modifica el query.
	SELECT (CASE WHEN CTD_STK_LIMA > 0 THEN 'S' ELSE 'N' END) || 'Ã' ||
		   (CASE WHEN CTD_STK_PROV > 0 THEN 'S' ELSE 'N' END) || 'Ã' ||
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

END;

/
