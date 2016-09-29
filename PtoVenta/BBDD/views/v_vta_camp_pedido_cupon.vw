CREATE OR REPLACE FORCE VIEW PTOVENTA.V_VTA_CAMP_PEDIDO_CUPON AS
SELECT cod_grupo_cia, cod_local, cod_cupon, num_ped_vta, estado, usu_crea_cupon_ped, fec_crea_cupon_ped,
usu_mod_cupon_ped, fec_mod_cupon_ped, ind_impr, cod_camp_cupon, ind_uso
FROM PTOVENTA.VTA_CAMP_PEDIDO_CUPON where (COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA) in (select COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA from ptoventa.v_Vta_pedido_vta_cab);

