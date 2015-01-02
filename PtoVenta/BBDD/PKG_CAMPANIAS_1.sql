--------------------------------------------------------
--  DDL for Package Body PKG_CAMPANIAS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PKG_CAMPANIAS" is

/*  -- Private type declarations
  type <TypeName> is <Datatype>;
  
  -- Private constant declarations
  <ConstantName> constant <Datatype> := <Value>;

  -- Private variable declarations
  <VariableName> <Datatype>;

  -- Function and procedure implementations
  function <FunctionName>(<Parameter> <Datatype>) return <Datatype> is
    <LocalVariable> <Datatype>;
  begin
    <Statement>;
    return(<Result>);
  end;

begin
  -- Initialization
  <Statement>;*/
/****************************************************************************************************************************************************/ 
/*--------------------------------------------------------------------------------------------------------------------------------------------
GOAL : Devolver las Campañas Activas asociadas a un Producto
History : 10-ABR-14      Create
----------------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_CAMPA_X_PROD(ac_Cod_Prod IN CHAR) RETURN SYS_REFCURSOR
AS
 cr_rtn SYS_REFCURSOR;
BEGIN
 OPEN cr_rtn FOR
   SELECT T1.*
      FROM
      (
        SELECT cc.cod_camp_cupon,cc.fech_inicio,cc.fech_fin,cc.mont_min,cc.desc_cupon,cc.estado,cc.tip_campana,cc.fech_inicio_uso,cc.fech_fin_uso,
               cc.mont_min_uso,cc.unid_min_uso,cc.unid_max_prod,cc.valor_cupon,cc.dia_semana,cc.ind_fid,cc.ind_cadena,cc.flg_camp_prec,'>>'sep_01,
               pu.cod_prod,p.desc_prod,pl.unid_vta,pl.val_prec_vta,pl.val_frac_local,pl.stk_fisico,
               CASE
                WHEN cc.tip_campana = 'F' THEN
                 (pu.val_prec_prom/pl.val_frac_local)
                ELSE
                 (pl.val_prec_vta * (1-cc.valor_cupon/100)) 
               END prec_promo
        FROM vta_campana_cupon cc
        INNER JOIN vta_campana_prod_uso pu ON (cc.cod_camp_cupon = pu.cod_camp_cupon)
        INNER JOIN lgt_prod_local       pl ON (pu.cod_prod       = pl.cod_prod)
        INNER JOIN lgt_prod             p  ON (pu.cod_prod       = p.cod_prod)
        WHERE cc.cod_grupo_cia = '001'
          AND cc.estado        = 'A'
          AND cc.fech_fin_uso >= trunc(SYSDATE)
          ---
          AND pu.cod_grupo_cia = '001'
          AND pu.cod_prod      = ac_Cod_Prod--'123470'
          ---
          AND pl.cod_grupo_cia = '001'
          --
          AND p.cod_grupo_cia  =  '001'
          AND p.est_prod       = 'A'
     )T1     
  ORDER BY T1.prec_promo;
  
  RETURN cr_rtn;                           
END;
/****************************************************************************************************************************************************/ 
end PKG_CAMPANIAS;

/
