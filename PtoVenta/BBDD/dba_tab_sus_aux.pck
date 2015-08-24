CREATE OR REPLACE PACKAGE PTOVENTA."DBA_TAB_SUS_AUX" is
--autor jluna 20101021 por solicitud de desarrollo
procedure fill_aux_sustitutos(cCodGrupoCia_in in char,cCodLocal_in in char) ;
procedure fill_aux_complementarios(cCodGrupoCia_in in char,cCodLocal_in in char) ;
procedure fill_tablas_aux_nocturna(cCodGrupoCia_in in char);

end;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."DBA_TAB_SUS_AUX" is
procedure fill_aux_sustitutos(cCodGrupoCia_in in char,cCodLocal_in in char) is
begin
  delete aux_prod_sustitutos;
  for t in (
            select cod_prod
            from   lgt_prod_local PROD_LOCAL
            where  (PROD_LOCAL.STK_FISICO) > 0
            union
            select distinct cod_prod
            from   vta_pedido_vta_det det
            where  det.fec_crea_ped_vta_det>trunc(sysdate)-10
           )
  loop
  -- 07.01.2015 ERIOS Garantizado por local
    insert into aux_prod_sustitutos
    select *
    from
    (
    SELECT           prod.cod_grupo_cia                        cod_grupo_cia,
                     t.cod_prod                                cod_prod1,
                     PROD.COD_PROD                             cod_prod2,
                     DECODE(NVL(Z.COD,'N'),'N','N','S')        COD,
                     NVL(trim(V2.COD_ENCARTE),' ')             COD_ENCARTE
              FROM   LGT_PROD       PROD,
              		   LGT_PROD_LOCAL PROD_LOCAL,
              		   PBL_IGV        IGV,
                     (SELECT DISTINCT(V1.COD_PROD)             COD
                      FROM  (SELECT COD_PAQUETE,
                                    COD_PROD
                             FROM   VTA_PROD_PAQUETE
                             WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                             ) V1,
                             VTA_PROMOCION    P
                      WHERE  P.FEC_PROMOCION_INICIO <= TRUNC(SYSDATE)
                      AND    P.FEC_PROMOCION_FIN    >= TRUNC(SYSDATE)
                      AND    P.IND_DELIVERY         = 'N'
                      AND    P.COD_GRUPO_CIA        = cCodGrupoCia_in
                      AND    P.ESTADO               = 'A'
                      AND    ( P.COD_PAQUETE_1      = (V1.COD_PAQUETE) OR
                               P.COD_PAQUETE_2      = (V1.COD_PAQUETE)
                             )
                    ) Z,
                    (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                            PROD_ENCARTE.COD_ENCARTE,
                            PROD_ENCARTE.COD_PROD
                     FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                            VTA_ENCARTE ENCARTE
                     WHERE  TRUNC(SYSDATE)             BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
                     AND    ENCARTE.ESTADO             = 'A'
                     AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
                     AND    PROD_ENCARTE.COD_ENCARTE   = ENCARTE.COD_ENCARTE
                     )   V2
              WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
              AND	   PROD_LOCAL.COD_LOCAL     = cCodLocal_in
              AND    (PROD_LOCAL.STK_FISICO) > 0
              AND	   PROD_LOCAL.COD_PROD IN ((
                                             select cod_prod
                                             from   lgt_prod pp
                                             where  COD_GRUPO_CIA = cCodGrupoCia_in
                                             AND    cod_prod in (select cod_prod
                                                                 from   lgt_princ_act_prod
                                                                 where  COD_GRUPO_CIA = cCodGrupoCia_in
                                                                 AND    (cod_prod     > t.Cod_Prod or cod_prod < t.Cod_Prod)
                                                                 and    cod_princ_act in (select cod_princ_act
                                                                                          from   lgt_princ_act_prod
                                                                                          where  COD_GRUPO_CIA = cCodGrupoCia_in
                                                                                          AND    cod_prod      = t.Cod_Prod)
                                                                 group by cod_prod
                                                                 having count(*) = (select count(*)
                                                                                    from   lgt_princ_act_prod
                                                                                    where  COD_GRUPO_CIA = cCodGrupoCia_in
                                                                                    AND    cod_prod      = t.Cod_Prod))
                                             and cod_prod IN (select cod_prod
                                                              from   lgt_princ_act_prod
                                                              where  COD_GRUPO_CIA = cCodGrupoCia_in
                                                              AND    (cod_prod     > t.Cod_Prod or cod_prod < t.Cod_Prod)
                                                              group by cod_prod
                                                              having count(*) = (select count(*)
                                                                                 from   lgt_princ_act_prod
                                                                                 where  COD_GRUPO_CIA = cCodGrupoCia_in
                                                                                 AND    cod_prod      = t.Cod_Prod)))
                                             UNION
                                             (
                                             select cod_prod
                                             from   lgt_prod PP
                                             where  COD_GRUPO_CIA = cCodGrupoCia_in
                                             AND    EXISTS       (SELECT 1
                                                                  FROM  LGT_CAT_SUST_CAB C,
                                                                        LGT_CAT_SUST_DET D
                                                                  WHERE C.COD_CAT_SUST = D.COD_CAT_SUST
                                                                  AND   D.cod_ind_cat  = PP.cod_prod
                                                                  AND  (D.cod_ind_cat  > t.Cod_Prod or D.cod_ind_cat < t.Cod_Prod )
                                                                  AND   C.IND_CAT      = 'PROD'
                                                                  AND   C.EST_CAT      = 'A'
                                                                  and   EXISTS            (SELECT 1
                                                                                           FROM   LGT_CAT_SUST_CAB C1,
                                                                                                  LGT_CAT_SUST_DET D
                                                                                           WHERE  C1.COD_CAT_SUST = D.COD_CAT_SUST
                                                                                           AND    C1.COD_CAT_SUST = C.COD_CAT_SUST
                                                                                           AND    C1.IND_CAT      = 'PROD'
                                                                                           AND    C1.EST_CAT      = 'A'
                                                                                           AND    D.COD_IND_CAT   = t.Cod_Prod))
                                            )
                                            UNION
                                            (select cod_prod
                                             from   lgt_prod pp
                                             where  COD_GRUPO_CIA = cCodGrupoCia_in
                                             and  exists (select  1
                                                            FROM  lgt_prod_grupo_similar p,lgt_grupo_similar g
                                                            where p.cod_grupo              = g.cod_grupo
                                                            and   p.cod_grupo_cia          = cCodGrupoCia_in
                                                            and   (p.cod_prod              > t.Cod_Prod or p.cod_prod     < t.Cod_Prod)
                                                            and   p.est_prod_grupo_similar ='A'
                                                            and   g.est_grupo_similar      ='A'
                                                            and   p.cod_prod               = pp.cod_prod
                                                            and   p.cod_grupo IN (SELECT cod_grupo
                                                                                  FROM   lgt_prod_grupo_similar
                                                                                  WHERE  cod_prod = t.Cod_Prod)))

                               )
              AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
              AND	   PROD.COD_PROD      = PROD_LOCAL.COD_PROD
              AND	   PROD.COD_IGV       = IGV.COD_IGV
              AND	   PROD.EST_PROD      = 'A'
              AND    PROD.COD_PROD      = Z.COD (+)
              AND    PROD.COD_GRUPO_CIA = V2.COD_GRUPO_CIA(+)
              AND    PROD.COD_PROD      = V2.COD_PROD(+)
              AND    exists(SELECT 1
                            FROM   LGT_PROD     PADRE,
                                   LGT_REL_UNID UPADRE,
                                   LGT_REL_UNID UHIJO,
                                   LGT_PROD     HIJO
                            WHERE HIJO.EST_PROD ='A'
                                  AND PADRE.COD_PROD         = t.Cod_Prod
                                  and HIJO.cod_prod          = PROD.COD_PROD
                                  and HIJO.COD_GRUPO_CIA     = cCodGrupoCia_in
                                  and PADRE.COD_GRUPO_CIA    = cCodGrupoCia_in
                                  AND UPADRE.COD_UNID_MEDIDA = PADRE.COD_UNID_MIN_FRAC
                                  AND UHIJO.COD_REL          = UPADRE.COD_REL
                                  AND HIJO.COD_UNID_MIN_FRAC = UHIJO.COD_UNID_MEDIDA
                          )
              ORDER BY NVL(PROD_LOCAL.IND_ZAN || TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ') DESC
    )
    where rownum<=10;
  end loop;
  commit;
end;

procedure fill_aux_complementarios(cCodGrupoCia_in in char,cCodLocal_in in char)  is
begin
delete aux_prod_complementarios;
for t in (
          select cod_prod
          from   lgt_prod_local PROD_LOCAL
          where  (PROD_LOCAL.STK_FISICO) > 0
          and    prod_local.cod_grupo_cia                              = cCodGrupoCia_in
          and    prod_local.cod_local                                  = cCodLocal_in
          union
          select distinct cod_prod
          from   vta_pedido_vta_det det
          where  det.fec_crea_ped_vta_det                              > trunc(sysdate)-15
          and    det.cod_grupo_cia                                     = cCodGrupoCia_in
          and    det.cod_local                                         = cCodLocal_in
         )
loop
  insert into aux_prod_complementarios(COD_GRUPO_CIA,COD_PROD_COMP,COD_PROD_ORIGINAL,DESC_MOTIVO)
  select DISTINCT prod.cod_grupo_cia,
                  prod.cod_prod,
                  cod_prod_original,
                  desc_motivo
                from   lgt_prod prod ,
                       lgt_prod prod2,
                       lgt_prod_local PLOCAL,
                       lgt_prod_local PLOCAL2,
                       (
                        select crtc.cod_cat_padre,
                               crtc.cod_cat_hijo,
                               c_iv2.ims_iv     ims_iv_hijo,
                               c_iv_ped.COD_ims ims_iv_original, --
                               c_iv_ped.cod_prod_original,  --
                               crtc.desc_motivo
                        from   CAT_REL_CAT_COMPLEMENT_IMS crtc,
                               (select p.Cod_Ims_Iv  Cod_Ims ,t.cod_prod cod_prod_original
                                       from   lgt_prod p
                                       where  p.cod_prod =t.cod_prod
                                       and    p.cod_grupo_cia  = cCodGrupoCia_in
                                       AND    COD_IMS_IV IS NOT NULL
                               ) c_iv_ped,
                               cat_producto_Iv c_iv2
                        where   trim(crtc.cod_cat_padre)= substr(c_iv_ped.COD_ims,1,length(trim(crtc.cod_cat_padre)))
                        and     trim(crtc.cod_cat_hijo)=  substr(c_iv2.ims_iv,1,length(trim(crtc.cod_cat_hijo)))
                       ) q2
                where  prod.Cod_Ims_Iv     = q2.ims_iv_hijo
                and    prod2.cod_prod      = q2.cod_prod_original
                and    prod.cod_grupo_cia  = plocal.cod_grupo_cia
                and    prod2.cod_grupo_cia = cCodGrupoCia_in
                and    prod.cod_prod       = plocal.cod_prod
                and    prod.cod_grupo_cia  = cCodGrupoCia_in
                and    plocal.cod_local    = cCodLocal_in
                and    prod2.cod_grupo_cia = plocal2.cod_grupo_cia
                and    prod2.cod_prod      = plocal2.cod_prod
                and    prod2.cod_grupo_cia = cCodGrupoCia_in
                and    plocal2.cod_local   = cCodLocal_in
                and    plocal.stk_fisico  >0
                and    plocal.val_prec_vta < 2*plocal2.val_prec_vta
                and    q2.ims_iv_hijo not in
                       (select p.Cod_Ims_Iv  Cod_Ims
                                       from   lgt_prod p
                                       where  p.cod_prod =t.cod_prod
                                       and    p.cod_grupo_cia  = cCodGrupoCia_in
                                       AND    COD_IMS_IV IS NOT NULL
                       );
end loop;
commit;
end;
procedure fill_tablas_aux_nocturna(cCodGrupoCia_in in char) is
xCodLocal char(3);
 begin
  select distinct l.cod_local
  into   xCodLocal
  from   vta_impr_local l
  where  l.cod_grupo_cia=cCodGrupoCia_in;

  fill_aux_sustitutos(cCodGrupoCia_in ,xCodLocal);
  fill_aux_complementarios(cCodGrupoCia_in ,xCodLocal);

  exception
  when others then
   null;
 end;
begin
null;
end ;
/

