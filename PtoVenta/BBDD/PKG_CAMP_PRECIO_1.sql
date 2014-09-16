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
14-ABR-14   TCT   Add Validacion de cantidad de decimales en precio de unidad de venta
--------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_MEJOR_PREC_PROD(
                                ac_CodGrupoCia IN CHAR DEFAULT '001',
                                ac_CodProd IN CHAR
                                )
  RETURN FarmaCursor
AS
 --vn_new_prec_prom_ent NUMBER(8,3);
 curPrec FarmaCursor;
 vn_Cuenta NUMBER;
BEGIN

 -- 05.- Deterrminar cantidad de decimales al dividir  el precio del entero entre la fraccion del local
 SELECT COUNT(*)
 INTO vn_Cuenta
 FROM vta_campana_prod_uso pu
 INNER JOIN lgt_prod_local pl ON (pu.cod_prod = pl.cod_prod)
 INNER JOIN vta_campana_cupon cc ON (pu.cod_camp_cupon = cc.cod_camp_cupon)
 WHERE pu.cod_prod  = ac_CodProd
  AND pu.val_prec_prom > 0    --- monto de precio fijo  
  AND pu.flg_modo      = '2'  --- precio fijo
  ---
  AND cc.cod_grupo_cia = '001'
  AND cc.estado        = 'A'
  AND cc.tip_campana   = 'F'
  AND cc.flg_camp_prec = 'S'
  AND cc.fech_fin_uso >= trunc(SYSDATE)
  --
  AND pl.val_frac_local > 1
  AND pl.cod_grupo_cia  = '001'
  -- Cantidad de Decimales en Division, el resul siempre sera -> 0.0...
  AND length(MOD(pu.val_prec_prom,pl.val_frac_local)/pl.val_frac_local)> 3;
  
  
 -- 08.- Modificar Precio en Tabla de Productos Uso
  
   --- Detalle Precio Fijo
 IF vn_Cuenta >0 THEN  
     DECLARE
       CURSOR cr_Det IS
       SELECT T1.*,'>>'Sep_02,((T1.ent_prec+T1.new_modu) * T1.val_frac_local) new_prec_prom_ent
             FROM (
             
                       SELECT pu.*,'>>' sep_01,pl.val_frac_local,(pu.val_prec_prom/pl.val_frac_local)prec_prom_fracc,
                              --(trunc(pu.val_prec_prom/pl.val_frac_local,2)*pl.val_frac_local) new_prec_ent
                              --(round(pu.val_prec_prom/pl.val_frac_local,2)*pl.val_frac_local) new_prec_ent
                              trunc(pu.val_prec_prom/pl.val_frac_local,0) ent_prec,
                              MOD(pu.val_prec_prom,pl.val_frac_local)/pl.val_frac_local modu,
                              CASE
                               WHEN length(MOD(pu.val_prec_prom,pl.val_frac_local)/pl.val_frac_local)> 3 THEN
                                 trunc(MOD(pu.val_prec_prom,pl.val_frac_local)/pl.val_frac_local,2)+0.01
                               ELSE
                                MOD(pu.val_prec_prom,pl.val_frac_local)/pl.val_frac_local
                              END new_modu
                              
                       FROM vta_campana_prod_uso pu
                       INNER JOIN lgt_prod_local pl    ON (pu.cod_prod = pl.cod_prod)
                       INNER JOIN vta_campana_cupon cc ON (pu.cod_camp_cupon = cc.cod_camp_cupon)
                       WHERE pu.cod_prod = ac_CodProd
                        AND pu.val_prec_prom > 0
                        AND pu.flg_modo      = '2' 
                        AND pu.cod_grupo_cia = '001'
                        --- campaña cupon
                        AND cc.cod_grupo_cia = '001'
                        AND cc.estado        = 'A'
                        AND cc.tip_campana   = 'F'
                        AND cc.flg_camp_prec = 'S'
                        AND cc.fech_fin_uso >= trunc(SYSDATE)
                        -- producto local
                        AND pl.cod_grupo_cia  = '001'
                        AND pl.val_frac_local > 1
                        -- Cantidad de Decimales en Division, el Result Siempre sera -> 0.0...
                        AND length(MOD(pu.val_prec_prom,pl.val_frac_local)/pl.val_frac_local)> 3
                    ) T1; 
     BEGIN
     FOR reg IN cr_Det LOOP
       UPDATE vta_campana_prod_uso pu
         SET pu.val_prec_prom   = reg.new_prec_prom_ent,
             pu.usu_mod_cup_det = 'SP_CAMP_PREC',
             pu.fec_mod_cup_det = SYSDATE
       WHERE pu.cod_grupo_cia   = '001'
         AND pu.cod_camp_cupon  = reg.COD_CAMP_CUPON
         AND pu.cod_prod        = reg.cod_prod;  
     END LOOP;
     COMMIT;
     END;
        
 END IF;       
                 
 

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
           AND p.est_prod      = 'A'
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
          AND pu.val_prec_prom IS NOT NULL
          AND pu.imp_valor IS NOT NULL
          AND cc.tip_campana    = 'F'           -- Tipo F = Campaña de Precio Fijo
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

/*--------------------------------------------------------------------------------------------------------------------------------------
GOAL :Determinar si existen campañas con productos a precio fijo que al divirse en la fraccion de local devuelven mas de 2 decimales
History : 14-ABR-14   TCT   Create
----------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_EXISTS_CAMPS_PREC_MAS2DEC(ac_CodProd IN CHAR) RETURN NUMBER
IS
 vn_Cuenta NUMBER:=0;
BEGIN
 SELECT COUNT(*)
 INTO vn_Cuenta
 FROM vta_campana_prod_uso pu
 INNER JOIN lgt_prod_local pl ON (pu.cod_prod = pl.cod_prod)
 INNER JOIN vta_campana_cupon cc ON (pu.cod_camp_cupon = cc.cod_camp_cupon)
 WHERE pu.cod_prod  = ac_CodProd
  AND pu.val_prec_prom > 0    --- monto de precio fijo  
  AND pu.flg_modo      = '2'  --- precio fijo
  ---
  AND cc.cod_grupo_cia = '001'
  AND cc.estado        = 'A'
  AND cc.tip_campana   = 'F'
  AND cc.flg_camp_prec = 'S'
  AND cc.fech_fin_uso >= trunc(SYSDATE)
  --
  AND pl.val_frac_local > 1
  AND pl.cod_grupo_cia  = '001'
  -- Cantidad de Decimales en Division, el resul siempre sera -> 0.0...
  AND length(MOD(pu.val_prec_prom,pl.val_frac_local)/pl.val_frac_local)> 3;
  RETURN vn_Cuenta;
  
 EXCEPTION 
 WHEN OTHERS THEN  
   RETURN 0;
  
END;  
/*******************************************************************************************************************************************/
end PKG_CAMP_PRECIO;

/
