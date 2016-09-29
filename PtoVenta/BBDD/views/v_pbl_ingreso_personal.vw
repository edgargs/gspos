create or replace force view ptoventa.v_pbl_ingreso_personal as
select COD_CIA,COD_GRUPO_CIA,COD_HORARIO,COD_LOCAL,COD_MAE_DET_MOT,COD_TRAB,DNI,ENTRADA,ENTRADA_2,ENTRADA_MOD,FECHA
,FEC_CREA,FEC_MOD,FEC_PROCESO,GLOSA,HHEE_25,HHEE_35,IND_ENTRADA_1,IND_ENTRADA_2,IND_REGISTRO,IND_SALIDA_1,IND_SALIDA_2
,OBSERVACION,SALIDA,SALIDA_2,SALIDA_MOD,TARD,USU_CREA,USU_MOD
from ptoventa.pbl_ingreso_personal pi
where pi.cod_grupo_cia='001'
and  pi.fec_crea>= (case when to_char(sysdate,'hh24') <'01' then trunc(sysdate-1) else trunc(sysdate) end)
and (pi.fec_crea>to_date((select llave_tab_gral from ptoventa.pbl_tab_gral where id_tab_gral=377 ),'yyyymmdd hh24miss')-2/24/60
or
nvl(pi.fec_mod,to_date('20140101','yyyymmdd'))>to_date((select llave_tab_gral from ptoventa.pbl_tab_gral where id_tab_gral=377 ),'yyyymmdd hh24miss')-2/24/60 );

