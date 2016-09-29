CREATE OR REPLACE PACKAGE "PKG_CAMP_PRECIO" is

  -- Author  : TCANCHES
  -- Created : 04/12/2013 11:16:16 a.m.
  -- Purpose : PROCESAR CAMPAÑAS POR PRECIO

  -- Public type declarations
  TYPE FarmaCursor IS REF CURSOR;

  ESTADO_ACTIVO      CHAR(1):='A';
  ESTADO_INACTIVO      CHAR(1):='I';
  INDICADOR_SI      CHAR(1):='S';
  INDICADOR_NO      CHAR(1):='N';
/****************************************************************************************************************************************/
/*--------------------------------------------------------------------------------------------------------------------------------------
GOAL: Devolver el Precio del Producto con La Mejor Campaña por Precio
Ammedmentes:
04-DIC-13   TCT   Create
--------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_MEJOR_PREC_PROD(
                                ac_CodGrupoCia IN CHAR DEFAULT '001',
                                ac_CodProd IN CHAR,
                                vIsFidelizado_in   IN CHAR DEFAULT 'N'
                                ) RETURN FarmaCursor;
/****************************************************************************************************************************************/

end PKG_CAMP_PRECIO;
/
CREATE OR REPLACE PACKAGE BODY "PKG_CAMP_PRECIO" is



  -- Function and procedure implementations
/*******************************************************************************************************************************************/
/*--------------------------------------------------------------------------------------------------------------------------------------
GOAL: Devolver el Precio del Producto de la Mejor Campaña por Precio
Hist: 04-DIC-13   TCT   Create
      26-NOV-14   TCT   Se agrega Control de Locales  y Dia Semana
--------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_MEJOR_PREC_PROD(
                                ac_CodGrupoCia IN CHAR DEFAULT '001',
                                ac_CodProd IN CHAR,
                                vIsFidelizado_in   IN CHAR DEFAULT 'N'
                                )
  RETURN FarmaCursor
AS
 --vn_Mejor_Prec_Prom_Prod NUMBER(8,3);
 curPrec FarmaCursor;
 vc_CodLocal CHAR(3);
 nNumDia VARCHAR2(2);

BEGIN

 nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);
  --- < 21-NOV-14   Obtener Codigo de Local >
  BEGIN
    SELECT DISTINCT i.cod_local
    INTO vc_CodLocal
    FROM vta_impr_local i;
  EXCEPTION
   WHEN OTHERS THEN
      raise_application_error(-20010,'Error al Leer Codigo de Local en Camp Prec !!!'||' => '||Sqlerrm);
  END;

  --- < /21-NOV-14   Obtener Codigo de Local >

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
          AND (CC.IND_FID ='N' OR CC.IND_FID=vIsFidelizado_in) -- KMONCADA 13.08.2015
          AND trunc(SYSDATE) BETWEEN cc.fech_inicio_uso
                                 AND cc.fech_fin_uso +1-1/24/60/60
          --- Validaciones Adicionales
          AND CC.COD_CAMP_CUPON IN
               (
                -- Cadena o Local asignado
                SELECT *
                  FROM (SELECT X.COD_CAMP_CUPON
                           FROM VTA_CAMPANA_CUPON X
                          WHERE X.COD_GRUPO_CIA = '001'
                            AND X.TIP_CAMPANA = 'F'
                            AND X.ESTADO = 'A'
                            AND X.IND_CADENA = 'S'
                         UNION
                         SELECT Y.COD_CAMP_CUPON
                           FROM VTA_CAMPANA_CUPON Y
                          WHERE Y.COD_GRUPO_CIA = '001'
                            AND Y.TIP_CAMPANA   = 'F'
                            AND Y.ESTADO        = 'A'
                            AND Y.IND_CADENA    = 'N'
                            AND Y.COD_CAMP_CUPON IN
                                (SELECT COD_CAMP_CUPON
                                   FROM VTA_CAMP_X_LOCAL Z
                                  WHERE Z.COD_GRUPO_CIA = ac_CodGrupoCia
                                    AND Z.COD_LOCAL = vc_CodLocal
                                    AND Z.ESTADO = 'A')

                         ))
           AND CC.COD_CAMP_CUPON IN
               (
           SELECT *
                 FROM (SELECT *
                          FROM (
                    SELECT COD_CAMP_CUPON
                                  FROM VTA_CAMPANA_CUPON
                                MINUS
                                SELECT H.COD_CAMP_CUPON
                FROM VTA_CAMP_HORA H
                )
                        UNION
                        SELECT H.COD_CAMP_CUPON
                          FROM VTA_CAMP_HORA H
                         WHERE TRIM(TO_CHAR(SYSDATE, 'HH24')) BETWEEN
                               H.HORA_INICIO AND H.HORA_FIN)
         )
           -- dia de semana valido
           AND DECODE(CC.DIA_SEMANA,
                      NULL, 'S',
                      DECODE(CC.DIA_SEMANA, REGEXP_REPLACE(CC.DIA_SEMANA, nNumDia, 'S'), 'N', 'S')
           ) = 'S'


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
