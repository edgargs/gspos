create or replace package ptoventa.ptoventa_viaj_zona is
--autor jluna 20140424
--solicitud joliva
procedure ejecuta_viajero;
end ptoventa_viaj_zona;
/

create or replace package body ptoventa.ptoventa_viaj_zona is
--autor jluna 20140424
--solicitud joliva
--viajero de precios por zona
procedure ejecuta_viajero
is
begin
  update ptoventa.lgt_prod_local lpl
  set
  lpl.val_prec_vta       = ceil((select t.tprecnuev
                                from   apps_tmp.t_preczonaactu t
                                where  t.tcodiprod=lpl.cod_prod)/(lpl.val_frac_local)*1000)/1000
  ,lpl.val_prec_vta_sug   = ceil((select t.tprecnuev
                                from   apps_tmp.t_preczonaactu t
                                where  t.tcodiprod=lpl.cod_prod)/( select p.VAL_FRAC_VTA_SUG
                                                                   from   ptoventa.lgt_prod p
                                                                   where  p.cod_grupo_cia=lpl.cod_grupo_cia
                                                                   and    p.cod_prod     =lpl.cod_prod)*1000)/1000
  ,lpl.usu_mod_prod_local = 'VIAJZONA'
  ,lpl.fec_mod_prod_local = sysdate
  where exists (select t.tprecnuev
                from   apps_tmp.t_preczonaactu t
                where  t.tcodiprod=lpl.cod_prod)
  and   (lpl.val_prec_vta       <>ceil((select t.tprecnuev
                                        from   apps_tmp.t_preczonaactu t
                                        where  t.tcodiprod=lpl.cod_prod)/lpl.val_frac_local*1000)/1000
         or
          (
           (nvl(lpl.val_prec_vta_sug,-1)<>ceil((select t.tprecnuev
                                        from   apps_tmp.t_preczonaactu t
                                        where  t.tcodiprod=lpl.cod_prod)/( select p.VAL_FRAC_VTA_SUG
                                                                           from   ptoventa.lgt_prod p
                                                                           where  p.cod_grupo_cia=lpl.cod_grupo_cia
                                                                           and    p.cod_prod     =lpl.cod_prod)*1000)/1000
           )
           and -- y sea fraccionable y tenga fraccion sugerida
           exists (select 1
                   from   ptoventa.lgt_prod p
                   where  p.cod_grupo_cia=lpl.cod_grupo_cia
                   and    p.cod_prod=lpl.cod_prod
                   and    p.ind_prod_fraccionable='S'
                   and    p.val_frac_vta_sug is not null)
          )
         ) --que haya cambiado algun precio
  and nvl((select (t.tprecnuev*(100-i.porc_igv)/100 - p.val_prec_prom)/(t.tprecnuev*(100-i.porc_igv)/100)
       from   apps_tmp.t_preczonaactu t,
              ptoventa.lgt_prod p,
              ptoventa.pbl_igv i
       where  t.tcodiprod=lpl.cod_prod
       and    p.cod_grupo_cia=lpl.cod_grupo_cia
       and    p.cod_prod=lpl.cod_prod
       and    p.cod_igv=i.cod_igv
       and    t.tprecnuev<>0),0)>-0.15;
  commit;
end;
end ptoventa_viaj_zona;
/

