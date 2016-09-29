create or replace force view ptoventa.v_rcd_recaudacion_cab as
select "COD_GRUPO_CIA","COD_CIA","COD_LOCAL","COD_RECAU","SEC_MOV_CAJA","NRO_TARJETA","TIPO_RCD","TIPO_PAGO","TIP_COMP_PAGO","EST_RCD","EST_CUENTA","COD_CLIENTE","TIP_MONEDA","IM_TOTAL","IM_TOTAL_PAGO","IM_MIN_PAGO","VAL_TIP_CAMBIO","FECHA_VENC_RECAU","NOM_CLIENTE","FEC_VEN_TRJ","FEC_CREA_RECAU_PAGO","USU_CREA_RECAU_PAGO","FEC_MOD_RECAU_PAGO","USU_MOD_RECAU_PAGO","EST_IMPRESION","COD_RECAU_ANUL_REF","COD_AUTORIZACION","COD_TRSSC_SIX","EST_TRSSC_SIX","COD_TRSSC_SIX_ANUL","NUM_PED_VTA","IND_ANUL","FECHA_ORIG","FECHA_VENC_CUOTA","NRO_CUOTAS","IM_CUOTA"
from ptoventa.RCD_RECAUDACION_CAB
where FEC_CREA_RECAU_PAGO >= (case when to_char(sysdate,'hh24') <'01' then trunc(sysdate-1)
                                   else trunc(sysdate) end)
and (FEC_CREA_RECAU_PAGO>to_date((select llave_tab_gral
                                  from ptoventa.pbl_tab_gral
                                  where id_tab_gral=377 ),'yyyymmdd hh24miss')
     or
     nvl(FEC_mod_RECAU_PAGO,to_date('20140101','yyyymmdd'))>to_date((select llave_tab_gral
                                                                     from ptoventa.pbl_tab_gral
                                                                     where id_tab_gral=377 ),'yyyymmdd hh24miss')
    );

