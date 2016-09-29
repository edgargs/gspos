create or replace force view ptoventa.v_lgt_kardex as
select "COD_GRUPO_CIA","COD_LOCAL","SEC_KARDEX","COD_PROD","COD_MOT_KARDEX","TIP_DOC_KARDEX","NUM_TIP_DOC","STK_ANTERIOR_PROD","CANT_MOV_PROD","STK_FINAL_PROD","VAL_FRACC_PROD","DESC_UNID_VTA","EST_KARDEX","FEC_KARDEX","USU_CREA_KARDEX","FEC_CREA_KARDEX","TIP_COMP_PAGO","NUM_COMP_PAGO","DESC_GLOSA_AJUSTE","NUM_AJUSTE","FEC_PROCESO_SAP","FEC_MOD_KARDEX","USU_MOD_KARDEX","IND_DIF_TOMA_DIARIA","IND_PROCESO_DIARIA","FECH_MOD_TOMA_DIARIA","SEC_KARDEX_ORIGEN","COD_AJUSTE"
from ptoventa.LGT_KARDEX
where FEC_KARDEX >= (case when to_char(sysdate,'hh24') <'01' then trunc(sysdate-1)
                          else trunc(sysdate) end)
and (FEC_KARDEX>to_date((select llave_tab_gral
                         from ptoventa.pbl_tab_gral
                         where id_tab_gral=377 ),'yyyymmdd hh24miss')
      or
      nvl(FEC_MOD_KARDEX,to_date('20140101','yyyymmdd'))>to_date((select llave_tab_gral
                                                                  from ptoventa.pbl_tab_gral
                                                                  where id_tab_gral=377 ),'yyyymmdd hh24miss')     );

