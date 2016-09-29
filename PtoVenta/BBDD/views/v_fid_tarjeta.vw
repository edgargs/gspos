create or replace force view ptoventa.v_fid_tarjeta as
select dni_cli, cod_local, usu_crea_tarjeta, fec_crea_tarjeta, usu_mod_tarjeta, fec_mod_tarjeta,
       cod_tarjeta, ind_puntos, ind_enviado_orbis
from ptoventa.fid_tarjeta f
where fec_crea_tarjeta>= (case when to_char(sysdate,'hh24') <'01' then trunc(sysdate-1) else trunc(sysdate) end)
and   fec_crea_tarjeta>to_date((select min(llave_tab_gral) from ptoventa.pbl_tab_gral where id_tab_gral in (377,378) ),'yyyymmdd hh24miss')-2/24;

