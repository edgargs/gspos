create or replace force view ptoventa.v_fid_tarjeta_pedido as
select cod_grupo_cia, cod_local, num_pedido, dni_cli, cant_dcto, usu_crea_tarjeta_pedido, fec_crea_tarjeta_pedido, usu_mod_tarjeta_pedido,
fec_mod_tarjeta_pedido, cod_tarjeta, fec_recuperado
from ptoventa.FID_TARJETA_PEDIDO;

