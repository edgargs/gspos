create or replace force view ptoventa.v_con_benef_parcial as
select
    SUBSTR(COD_CONVENIO,1,10) COD_CONVENIO,
SUBSTR(cod_cliente,1,10) cod_cliente,
SUBSTR(des_cliente,1,50) des_cliente,
SUBSTR(flg_activo,1,1) flg_activo,
SUBSTR(dni,1,11) dni,
SUBSTR(num_poliza,1,10) num_poliza,
SUBSTR(num_plan,1,10) num_plan,
SUBSTR(cod_asegurado,1,12) cod_asegurado,
SUBSTR(num_item,1,15) num_item,
SUBSTR(prt,1,1) prt,
SUBSTR(num_contrato,1,10) num_contrato,
SUBSTR(tipo_seguro,1,2) tipo_seguro,
SUBSTR(imp_linea_credito,1,5) imp_linea_credito,
SUBSTR(estado,1,1) estado,'' cod_cliente_sap
  from ptoventa.et_tmp_listado2
    where estado='A';

