create or replace package ptoventa.ptoventa_viaj_fraccion is
--autor jluna 20140506
--solicitud joliva
procedure ejecuta_viajero;
end ptoventa_viaj_fraccion;
/

create or replace package body ptoventa.ptoventa_viaj_fraccion is
--autor jluna 20140506
--solicitud joliva
--viajero de fracciones por local
procedure ejecuta_viajero
is
cursor cr1 is
 select lpl.cod_grupo_cia,lpl.cod_local,lpl.cod_prod,lpl.val_frac_local,lpl.stk_fisico,hf.val_frac_local val_frac_local_new,hf.desc_frac_local
   from apps_tmp.t_adm_hist_frac hf,lgt_prod_local lpl
  where  hf.cod_local      =  lpl.cod_local
  and    hf.cod_prod       =  lpl.cod_prod
  and    hf.val_frac_local <> lpl.val_frac_local
  and    lpl.stk_fisico*hf.val_frac_local/lpl.val_frac_local=trunc(lpl.stk_fisico*hf.val_frac_local/lpl.val_frac_local)
;
r1 cr1%rowtype;
begin
open cr1;
loop
  fetch cr1 into r1;
  exit when cr1%notfound;
  update ptoventa.lgt_prod_local lpl
    set
    lpl.stk_fisico    = lpl.stk_fisico*(select hf.val_frac_local
                                        from apps_tmp.t_adm_hist_frac hf
                                        where hf.cod_local=lpl.cod_local
                                        and hf.cod_prod=lpl.cod_prod)/lpl.val_frac_local
    ,lpl.val_prec_vta = ceil(lpl.val_prec_vta/(select hf.val_frac_local
                                          from apps_tmp.t_adm_hist_frac hf
                                          where hf.cod_local=lpl.cod_local
                                          and hf.cod_prod=lpl.cod_prod)*lpl.val_frac_local*1000)/1000
    ,lpl.val_frac_local = ( select hf.val_frac_local
                            from apps_tmp.t_adm_hist_frac hf
                            where hf.cod_local=lpl.cod_local
                            and hf.cod_prod=lpl.cod_prod)
    ,lpl.unid_vta= DECODE(R1.VAL_FRAC_LOCAL_NEW,1,' ',
                   NVL( (select hf.desc_frac_local
                            from apps_tmp.t_adm_hist_frac hf
                            where hf.cod_local=lpl.cod_local
                            and hf.cod_prod=lpl.cod_prod),
                         (SELECT P.DESC_UNID_PRESENT
                            FROM PTOVENTA.LGT_PROD P
                           WHERE P.COD_PROD=LPL.COD_PROD)
                       ))
    ,lpl.IND_PROD_FRACCIONADO=DECODE(R1.VAL_FRAC_LOCAL_NEW,1,'N','S')
    ,lpl.fec_mod_prod_local=sysdate
    ,lpl.usu_mod_prod_local='VIAJFRAC'
    where exists (select 1
                  from apps_tmp.t_adm_hist_frac hf
                  where hf.cod_local=lpl.cod_local
                  and hf.cod_prod=lpl.cod_prod) --el producto esta entre los enviados
    and   lpl.stk_fisico*(select hf.val_frac_local
                          from apps_tmp.t_adm_hist_frac hf
                          where hf.cod_local=lpl.cod_local
                          and hf.cod_prod=lpl.cod_prod)/lpl.val_frac_local =  trunc(lpl.stk_fisico*(select hf.val_frac_local
                                                                                                    from apps_tmp.t_adm_hist_frac hf
                                                                                                    where hf.cod_local=lpl.cod_local
                                                                                                    and hf.cod_prod=lpl.cod_prod)/lpl.val_frac_local)
                                                          --el producto tendra una fraccion cuyo stock sera entero
    and  lpl.val_frac_local<>(select hf.val_frac_local
                              from apps_tmp.t_adm_hist_frac hf
                              where hf.cod_local=lpl.cod_local
                              and hf.cod_prod=lpl.cod_prod)
    and  lpl.cod_grupo_cia=r1.cod_grupo_cia
    and  lpl.cod_local    =r1.cod_local
    and  lpl.cod_prod     =r1.cod_prod; --el producto tiene una fraccion diferente a la que se esta enviando
    ptoventa_viajero.INV_GRABAR_KARDEX_FRACCION(r1.cod_grupo_cia,
                              r1.COD_LOCAL,
                              r1.COD_PROD,
                              r1.stk_fisico,
                              (r1.stk_fisico*r1.val_frac_local_new)/r1.val_frac_local,
                              r1.val_frac_local_new,
                              r1.desc_frac_local,
                              'SISTEMAS'
                              );
  commit;
end loop;
close cr1;


end;
end ptoventa_viaj_fraccion;
/

