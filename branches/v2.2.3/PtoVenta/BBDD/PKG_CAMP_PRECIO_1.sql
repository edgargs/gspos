--------------------------------------------------------
--  DDL for Package Body PKG_CAMP_PRECIO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PKG_CAMP_PRECIO" is



  -- Function and procedure implementations
/*******************************************************************************************************************************************/  
/*--------------------------------------------------------------------------------------------------------------------------------------
GOAL: Devolver el Precio del Producto de la Mejor Campaña por Precio
Ammedmentes:
04-DIC-13   TCT   Create
--------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_MEJOR_PREC_PROD(
                                ac_CodGrupoCia IN CHAR DEFAULT '001',
                                ac_CodProd IN CHAR
                                ) 
  RETURN FarmaCursor
AS
 --vn_Mejor_Prec_Prom_Prod NUMBER(8,3);
 curPrec FarmaCursor;
 
BEGIN

 -- 10.- Buscar el Menor Precio de Producto Para Promocion Activa
 --dbms_output.put_line('ac_CodGrupoCia ='||ac_CodGrupoCia);
 BEGIN
 OPEN curPrec FOR
 SELECT T1.COD_CAMP_CUPON,T1.COD_PROD,T1.VAL_PREC_PROM_ENT,T1.VAL_PREC_PROM,
        T1.UNID_MIN_USO,T1.MONT_MIN_USO,T1.UNID_MAX_PROD,T1.VALOR_CUPON,
        T1.MONTO_MAX_DESCT
  FROM
    (
                                         
       WITH produso AS 
         (
          SELECT pu.*
          FROM vta_campana_prod_uso pu
          WHERE pu.cod_grupo_cia = ac_CodGrupoCia
            AND pu.cod_prod      = ac_CodProd
        ),
        datosprod AS
        (
         SELECT p.cod_grupo_cia,p.cod_prod,p.val_max_frac,p.val_frac_vta_sug,pl.val_frac_local,nvl(p.val_frac_vta_sug,pl.val_frac_local)val_frac_vta_loc
         FROM lgt_prod p 
         INNER JOIN lgt_prod_local pl ON (
                                          p.cod_grupo_cia = pl.cod_grupo_cia AND
                                          p.cod_prod      = pl.cod_prod
                                         )
         WHERE p.cod_grupo_cia = ac_CodGrupoCia
           AND p.cod_prod      = ac_CodProd
        )  
        SELECT cc.*,
              'PU>>' SEP_PU,pu.cod_prod,pu.val_prec_prom val_prec_prom_Ent,
              round((pu.val_prec_prom/ p.val_frac_vta_loc),2)val_prec_prom,
              rank() over (PARTITION BY pu.cod_prod ORDER BY pu.val_prec_prom,ROWNUM ASC)posi
        FROM vta_campana_cupon cc
        INNER JOIN produso  pu ON (
                                                  pu.cod_grupo_cia  = cc.cod_grupo_cia AND
                                                  pu.cod_camp_cupon = cc.cod_camp_cupon
                                                 )
        INNER JOIN datosprod p  ON (
                                    pu.cod_grupo_cia  = p.cod_grupo_cia AND
                                    pu.cod_prod       = p.cod_prod
                                  )
        
        WHERE  cc.cod_grupo_cia = ac_CodGrupoCia
          AND cc.estado         = 'A'
          AND cc.flg_camp_prec  = 'S'
          AND trunc(SYSDATE) BETWEEN cc.fech_inicio_uso
                                 AND cc.fech_fin_uso +1-1/24/60/60 
                                 
                                 
    )T1
  WHERE T1.posi = 1;
 EXCEPTION
  WHEN OTHERS THEN
   dbms_output.put_line('ERROR:'||Sqlerrm);
    
 END; 
  
  RETURN curPrec;
END;
                                
/*******************************************************************************************************************************************/
end PKG_CAMP_PRECIO;

/
