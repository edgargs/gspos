CREATE OR REPLACE PACKAGE PTOVENTA."PKG_ENVIA_VENTA" is

  -- Author  : TCANCHES
  -- Created : 05/02/2014 01:36:38 p.m.
  -- Purpose : Cargar las Ventas de Local  a Matriz

  TYPE TYP_VTA_PEDIDO_VTA_CAB IS TABLE OF VTA_PEDIDO_VTA_CAB%ROWTYPE;
  TYPE TYP_VTA_PEDIDO_VTA_DET IS TABLE OF VTA_PEDIDO_VTA_DET%ROWTYPE;
  TYPE TYP_VTA_COMP_PAGO IS TABLE OF VTA_COMP_PAGO%ROWTYPE;
  TYPE TYP_FID_TARJETA_PEDIDO IS TABLE OF FID_TARJETA_PEDIDO%ROWTYPE;

/******************************************************************************************************************************************/
/*-----------------------------------------------------------------------------------------------------------------------------------------
GOAL : Generar Temporales de Ventas
-----------------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_GENE_TEMP_VTA(
                            ac_Fec_Ini IN CHAR DEFAULT to_char(SYSDATE-1,'dd/MM/yyyy'),
                            ac_Fec_Fin IN CHAR DEFAULT to_char(SYSDATE-1,'dd/MM/yyyy'),
                            ac_Cod_Local IN CHAR DEFAULT NULL
                           );
/*-------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_VTA_PEDIDO_VTA_CAB(
                                   ac_Fec_Ini IN CHAR,
                                   ac_Fec_Fin IN CHAR,
                                   ac_Cod_Local IN CHAR
                                   )
RETURN TYP_VTA_PEDIDO_VTA_CAB;

/******************************************************************************************************************************************/

end PKG_ENVIA_VENTA;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PKG_ENVIA_VENTA" is

/******************************************************************************************************************************************/
/*-----------------------------------------------------------------------------------------------------------------------------------------
GOAL : Generar Temporales de Ventas
-----------------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_GENE_TEMP_VTA(
                            ac_Fec_Ini IN CHAR DEFAULT to_char(SYSDATE-1,'dd/MM/yyyy'),
                            ac_Fec_Fin IN CHAR DEFAULT to_char(SYSDATE-1,'dd/MM/yyyy'),
                            ac_Cod_Local IN CHAR DEFAULT NULL
                           )
AS
 vc_Cod_Local CHAR(3);
BEGIN
    -- 05.- Carga Codigo de Local
    IF  ac_Cod_Local IS NULL THEN
     SELECT DISTINCT v.cod_local
     INTO vc_Cod_Local
     FROM vta_impr_local v
     WHERE v.cod_grupo_cia = '001';

    ELSE
      vc_Cod_Local:=ac_Cod_Local;
    END IF;

    -- 07.- Carga Usuarios x Local
    EXECUTE IMMEDIATE 'truncate TABLE tt_pbl_usu_local drop storage';
    INSERT INTO tt_pbl_usu_local(
      cod_grupo_cia,
      cod_local,
      sec_usu_local,
      login_usu,
      cod_trab,
      cod_cia,
      nom_usu,
      ape_pat,
      ape_mat,
      clave_usu,
      telef_usu,
      direcc_usu,
      fec_nac,
      est_usu,
      fec_crea_usu_local,
      usu_crea_usu_local,
      fec_mod_usu_local,
      usu_mod_usu_local,
      ind_distr_gratuita,
      dni_usu,
      cod_trab_rrhh,
      carne_sanidad,
      fec_expedicion,
      fec_vencimiento,
      fec_ult_camb_clave
    )
    SELECT
      cod_grupo_cia,
      cod_local,
      sec_usu_local,
      login_usu,
      cod_trab,
      cod_cia,
      nom_usu,
      ape_pat,
      ape_mat,
      clave_usu,
      telef_usu,
      direcc_usu,
      fec_nac,
      est_usu,
      fec_crea_usu_local,
      usu_crea_usu_local,
      fec_mod_usu_local,
      usu_mod_usu_local,
      ind_distr_gratuita,
      dni_usu,
      cod_trab_rrhh,
      carne_sanidad,
      fec_expedicion,
      fec_vencimiento,
      fec_ult_camb_clave
    FROM pbl_usu_local u
    WHERE u.cod_grupo_cia = '001';
    ----
    --CREATE TABLE TT_CE_MOV_CAJA AS
    EXECUTE IMMEDIATE 'truncate TABLE TT_CE_MOV_CAJA drop storage';
    INSERT INTO TT_CE_MOV_CAJA(
              cod_grupo_cia,
              cod_local,
              sec_mov_caja,
              num_caja_pago,
              sec_usu_local,
              tip_mov_caja,
              fec_dia_vta,
              num_turno_caja,
              fec_crea_mov_caja,
              usu_crea_mov_caja,
              fec_mod_mov_caja,
              usu_mod_mov_caja,
              cant_bol_emi,
              mon_bol_emi,
              cant_fact_emi,
              mon_fact_emi,
              cant_guia_emi,
              mon_guia_emi,
              cant_bol_anu,
              mon_bol_anu,
              cant_fact_anu,
              mon_fact_anu,
              cant_guia_anu,
              mon_guia_anu,
              cant_bol_tot,
              mon_bol_tot,
              cant_fact_tot,
              mon_fact_tot,
              cant_guia_tot,
              mon_guia_tot,
              mon_tot_gen,
              mon_tot_anu,
              mon_tot,
              sec_mov_caja_origen,
              cant_nc_boletas,
              mon_nc_boletas,
              cant_nc_fact,
              mon_nc_fact,
              mon_tot_nc,
              ip_mov_caja,
              ind_vb_qf,
              ind_vb_cajero,
              fec_cierre_turno_caja,
              desc_obs_cierre_turno,
              num_boleta_inicial,
              num_boleta_final,
              num_factura_inicial,
              num_factura_final,
              ind_comp_validos,
              fec_cierre_dia_vta,
              ind_mov_caja,
              cant_tick_emi,
              mon_tick_emi,
              cant_tick_anu,
              mon_tick_anu,
              cant_nc_tickets,
              mon_nc_tickets,
              cant_tick_tot,
              mon_tick_tot
    )
    SELECT
            cod_grupo_cia,
            cod_local,
            sec_mov_caja,
            num_caja_pago,
            sec_usu_local,
            tip_mov_caja,
            fec_dia_vta,
            num_turno_caja,
            fec_crea_mov_caja,
            usu_crea_mov_caja,
            fec_mod_mov_caja,
            usu_mod_mov_caja,
            cant_bol_emi,
            mon_bol_emi,
            cant_fact_emi,
            mon_fact_emi,
            cant_guia_emi,
            mon_guia_emi,
            cant_bol_anu,
            mon_bol_anu,
            cant_fact_anu,
            mon_fact_anu,
            cant_guia_anu,
            mon_guia_anu,
            cant_bol_tot,
            mon_bol_tot,
            cant_fact_tot,
            mon_fact_tot,
            cant_guia_tot,
            mon_guia_tot,
            mon_tot_gen,
            mon_tot_anu,
            mon_tot,
            sec_mov_caja_origen,
            cant_nc_boletas,
            mon_nc_boletas,
            cant_nc_fact,
            mon_nc_fact,
            mon_tot_nc,
            ip_mov_caja,
            ind_vb_qf,
            ind_vb_cajero,
            fec_cierre_turno_caja,
            desc_obs_cierre_turno,
            num_boleta_inicial,
            num_boleta_final,
            num_factura_inicial,
            num_factura_final,
            ind_comp_validos,
            fec_cierre_dia_vta,
            ind_mov_caja,
            cant_tick_emi,
            mon_tick_emi,
            cant_tick_anu,
            mon_tick_anu,
            cant_nc_tickets,
            mon_nc_tickets,
            cant_tick_tot,
            mon_tick_tot
    FROM CE_MOV_CAJA t
    WHERE t.cod_grupo_cia = '001'
      AND t.cod_local     = vc_Cod_Local
      AND t.fec_dia_vta BETWEEN to_date(ac_Fec_Ini,'dd/MM/yyyy')
                            AND to_date(ac_Fec_Fin,'dd/MM/yyyy')+1-1/24/60/60;
    ----
    -- 10.- Cabecera de Venta
    --CREATE TABLE TT_VTA_PEDIDO_VTA_CAB AS
    EXECUTE IMMEDIATE 'truncate TABLE TT_VTA_PEDIDO_VTA_CAB drop storage';
    INSERT INTO TT_VTA_PEDIDO_VTA_CAB(
               cod_grupo_cia,
               cod_local,
               num_ped_vta,
               cod_cli_local,
               sec_mov_caja,
               fec_ped_vta,
               val_bruto_ped_vta,
               val_neto_ped_vta,
               val_redondeo_ped_vta,
               val_igv_ped_vta,
               val_dcto_ped_vta,
               tip_ped_vta,
               val_tip_cambio_ped_vta,
               num_ped_diario,
               cant_items_ped_vta,
               est_ped_vta,
               tip_comp_pago,
               nom_cli_ped_vta,
               dir_cli_ped_vta,
               ruc_cli_ped_vta,
               usu_crea_ped_vta_cab,
               fec_crea_ped_vta_cab,
               usu_mod_ped_vta_cab,
               fec_mod_ped_vta_cab,
               ind_pedido_anul,
               ind_distr_gratuita,
               cod_local_atencion,
               num_ped_vta_origen,
               obs_forma_pago,
               obs_ped_vta,
               cod_dir,
               num_telefono,
               fec_ruteo_ped_vta_cab,
               fec_salida_local,
               fec_entrega_ped_vta_cab,
               fec_retorno_local,
               cod_ruteador,
               cod_motorizado,
               ind_deliv_automatico,
               num_ped_rec,
               ind_conv_enteros,
               ind_ped_convenio,
                cod_convenio,
                num_pedido_delivery,
                cod_local_procedencia,
                ip_pc,
                cod_rpta_recarga,
                ind_fid,
                motivo_anulacion,
                dni_cli,
                ind_camp_acumulada,
                fec_ini_cobro,
                fec_fin_cobro,
                punto_llegada,
                sec_usu_local,
                --cod_nit,
                --porc_copago,
                ind_fp_fid_efectivo,
                ind_fp_fid_tarjeta,
                cod_fp_fid_tarjeta,
                cod_cli_conv,
                cod_barra_conv,
                ind_conv_btl_mf,
                ip_cob_ped,
                dni_usu_local,
                fec_proceso_rac,
                fecha_proceso_nc_rac,
                fecha_proceso_anula_rac,
                name_pc_cob_ped
       )
    SELECT
               cod_grupo_cia,
               cod_local,
               num_ped_vta,
               cod_cli_local,
               sec_mov_caja,
               fec_ped_vta,
               val_bruto_ped_vta,
               val_neto_ped_vta,
               val_redondeo_ped_vta,
               val_igv_ped_vta,
               val_dcto_ped_vta,
               tip_ped_vta,
               val_tip_cambio_ped_vta,
               num_ped_diario,
               cant_items_ped_vta,
               est_ped_vta,
               tip_comp_pago,
               nom_cli_ped_vta,
               dir_cli_ped_vta,
               ruc_cli_ped_vta,
               usu_crea_ped_vta_cab,
               fec_crea_ped_vta_cab,
               usu_mod_ped_vta_cab,
               fec_mod_ped_vta_cab,
               ind_pedido_anul,
               ind_distr_gratuita,
               cod_local_atencion,
               num_ped_vta_origen,
               obs_forma_pago,
               obs_ped_vta,
               cod_dir,
               num_telefono,
               fec_ruteo_ped_vta_cab,
               fec_salida_local,
               fec_entrega_ped_vta_cab,
               fec_retorno_local,
               cod_ruteador,
               cod_motorizado,
               ind_deliv_automatico,
               num_ped_rec,
               ind_conv_enteros,
               ind_ped_convenio,
                cod_convenio,
                num_pedido_delivery,
                cod_local_procedencia,
                ip_pc,
                cod_rpta_recarga,
                ind_fid,
                motivo_anulacion,
                dni_cli,
                ind_camp_acumulada,
                fec_ini_cobro,
                fec_fin_cobro,
                punto_llegada,
                sec_usu_local,
                --cod_nit,
                --porc_copago,
                ind_fp_fid_efectivo,
                ind_fp_fid_tarjeta,
                cod_fp_fid_tarjeta,
                cod_cli_conv,
                cod_barra_conv,
                ind_conv_btl_mf,
                ip_cob_ped,
                dni_usu_local,
                fec_proceso_rac,
                fecha_proceso_nc_rac,
                fecha_proceso_anula_rac,
                name_pc_cob_ped

    FROM VTA_PEDIDO_VTA_CAB c
    WHERE c.cod_grupo_cia = '001'
      AND c.cod_local     = vc_Cod_Local
      AND c.fec_ped_vta BETWEEN to_date(ac_Fec_Ini,'dd/MM/yyyy')
                            AND to_date(ac_Fec_Fin,'dd/MM/yyyy')+1-1/24/60/60;


    -- 20.- Detalle de Venta
    --CREATE TABLE TT_VTA_PEDIDO_VTA_DET AS
    EXECUTE IMMEDIATE 'truncate TABLE TT_VTA_PEDIDO_VTA_DET drop storage';
    INSERT INTO TT_VTA_PEDIDO_VTA_DET (
       cod_grupo_cia,
    cod_local,
    num_ped_vta,
    sec_ped_vta_det,
    cod_prod,
    cant_atendida,
    val_prec_vta,
    val_prec_total,
    porc_dcto_1,
    porc_dcto_2,
    porc_dcto_3,
    porc_dcto_total,
    est_ped_vta_det,
    val_total_bono,
    val_frac,
    sec_comp_pago,
    sec_usu_local,
    usu_crea_ped_vta_det,
    fec_crea_ped_vta_det,
    usu_mod_ped_vta_det,
    fec_mod_ped_vta_det,
    val_prec_lista,
    val_igv,
    unid_vta,
    ind_exonerado_igv,
    sec_grupo_impr,
    cant_usada_nc,
    sec_comp_pago_origen,
    num_lote_prod,
    fec_proceso_guia_rd,
    desc_num_tel_rec,
    val_num_trace,
    val_cod_aprobacion,
    desc_num_tarj_virtual,
    val_num_pin,
    fec_vencimiento_lote,
    val_prec_public,
    ind_calculo_max_min,
    fec_exclusion,
    fecha_tx,
    hora_tx,
    cod_prom,
    ind_origen_prod,
    val_frac_local,
    cant_frac_local,
    cant_xdia_tra,
    cant_dias_tra,
    ind_zan,
    val_prec_prom,
    datos_imp_virtual,
    cod_camp_cupon,
    ahorro,
    porc_dcto_calc,
    porc_zan,
    ind_prom_automatico,
    ahorro_pack,
    porc_dcto_calc_pack,
    cod_grupo_rep,
    cod_grupo_rep_edmundo,
    sec_respaldo_stk,
    sec_comp_pago_benef,
    sec_comp_pago_empre,
    num_comp_pago,
    ahorro_conv
    )
    SELECT
    cod_grupo_cia,
    cod_local,
    num_ped_vta,
    sec_ped_vta_det,
    cod_prod,
    cant_atendida,
    val_prec_vta,
    val_prec_total,
    porc_dcto_1,
    porc_dcto_2,
    porc_dcto_3,
    porc_dcto_total,
    est_ped_vta_det,
    val_total_bono,
    val_frac,
    sec_comp_pago,
    sec_usu_local,
    usu_crea_ped_vta_det,
    fec_crea_ped_vta_det,
    usu_mod_ped_vta_det,
    fec_mod_ped_vta_det,
    val_prec_lista,
    val_igv,
    unid_vta,
    ind_exonerado_igv,
    sec_grupo_impr,
    cant_usada_nc,
    sec_comp_pago_origen,
    num_lote_prod,
    fec_proceso_guia_rd,
    desc_num_tel_rec,
    val_num_trace,
    val_cod_aprobacion,
    desc_num_tarj_virtual,
    val_num_pin,
    fec_vencimiento_lote,
    val_prec_public,
    ind_calculo_max_min,
    fec_exclusion,
    fecha_tx,
    hora_tx,
    cod_prom,
    ind_origen_prod,
    val_frac_local,
    cant_frac_local,
    cant_xdia_tra,
    cant_dias_tra,
    ind_zan,
    val_prec_prom,
    datos_imp_virtual,
    cod_camp_cupon,
    ahorro,
    porc_dcto_calc,
    porc_zan,
    ind_prom_automatico,
    ahorro_pack,
    porc_dcto_calc_pack,
    cod_grupo_rep,
    cod_grupo_rep_edmundo,
    sec_respaldo_stk,
    sec_comp_pago_benef,
    sec_comp_pago_empre,
    num_comp_pago,
    ahorro_conv
    FROM VTA_PEDIDO_VTA_DET d
    WHERE d.cod_grupo_cia = '001'
      AND (d.cod_local, d.num_ped_vta) IN (
                                           SELECT c.cod_local,c.num_ped_vta
                                            FROM VTA_PEDIDO_VTA_CAB c
                                            WHERE c.cod_grupo_cia = '001'
                                              AND c.cod_local     = vc_Cod_Local
                                              AND c.fec_ped_vta BETWEEN to_date(ac_Fec_Ini,'dd/MM/yyyy')
                                                                    AND to_date(ac_Fec_Fin,'dd/MM/yyyy')+1-1/24/60/60
                                          );

    -- 30.- Comprobantes de Pago
    -- XPKVTA_COMP_PAGO primary key (COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, SEC_COMP_PAGO)
    --CREATE TABLE TT_VTA_COMP_PAGO AS
    EXECUTE IMMEDIATE 'truncate TABLE TT_VTA_COMP_PAGO drop storage';
    INSERT INTO TT_VTA_COMP_PAGO(
      cod_grupo_cia,
      cod_local,
      num_ped_vta,
      sec_comp_pago,
      tip_comp_pago,
      num_comp_pago,
      sec_mov_caja,
      sec_mov_caja_anul,
      cant_item,
      cod_cli_local,
      nom_impr_comp,
      direc_impr_comp,
      num_doc_impr,
      val_bruto_comp_pago,
      val_neto_comp_pago,
      val_dcto_comp_pago,
      val_afecto_comp_pago,
      val_igv_comp_pago,
      val_redondeo_comp_pago,
      porc_igv_comp_pago,
      usu_crea_comp_pago,
      fec_crea_comp_pago,
      usu_mod_comp_pago,
      fec_mod_comp_pago,
      fec_anul_comp_pago,
      ind_comp_anul,
      num_pedido_anul,
      num_sec_doc_sap,
      fec_proceso_sap,
      num_sec_doc_sap_anul,
      fec_proceso_sap_anul,
      ind_reclamo_navsat,
      val_dcto_comp,
      motivo_anulacion,
      fecha_cobro,
      fecha_anulacion,
      fech_imp_cobro,
      fech_imp_anul,
      tip_clien_convenio,
      val_copago_comp_pago,
      val_igv_comp_copago,
      num_comp_copago_ref,
      ind_afecta_kardex,
      pct_beneficiario,
      pct_empresa,
      ind_comp_credito,
      tip_comp_pago_ref,
      cod_tipo_convenio,
      ind_afecto_igv,
      cod_cliente_sap,
      old_num_sec_doc_sap,
      old_num_sec_doc_sap_anul

  )
    SELECT
      cod_grupo_cia,
      cod_local,
      num_ped_vta,
      sec_comp_pago,
      tip_comp_pago,
      num_comp_pago,
      sec_mov_caja,
      sec_mov_caja_anul,
      cant_item,
      cod_cli_local,
      nom_impr_comp,
      direc_impr_comp,
      num_doc_impr,
      val_bruto_comp_pago,
      val_neto_comp_pago,
      val_dcto_comp_pago,
      val_afecto_comp_pago,
      val_igv_comp_pago,
      val_redondeo_comp_pago,
      porc_igv_comp_pago,
      usu_crea_comp_pago,
      fec_crea_comp_pago,
      usu_mod_comp_pago,
      fec_mod_comp_pago,
      fec_anul_comp_pago,
      ind_comp_anul,
      num_pedido_anul,
      num_sec_doc_sap,
      fec_proceso_sap,
      num_sec_doc_sap_anul,
      fec_proceso_sap_anul,
      ind_reclamo_navsat,
      val_dcto_comp,
      motivo_anulacion,
      fecha_cobro,
      fecha_anulacion,
      fech_imp_cobro,
      fech_imp_anul,
      tip_clien_convenio,
      val_copago_comp_pago,
      val_igv_comp_copago,
      num_comp_copago_ref,
      ind_afecta_kardex,
      pct_beneficiario,
      pct_empresa,
      ind_comp_credito,
      tip_comp_pago_ref,
      cod_tipo_convenio,
      ind_afecto_igv,
      cod_cliente_sap,
      old_num_sec_doc_sap,
      old_num_sec_doc_sap_anul
     FROM VTA_COMP_PAGO cp
    WHERE cp.cod_grupo_cia = '001'
      AND (cp.cod_local, cp.num_ped_vta) IN (
                                           SELECT c.cod_local,c.num_ped_vta
                                            FROM VTA_PEDIDO_VTA_CAB c
                                            WHERE c.cod_grupo_cia = '001'
                                              AND c.cod_local     = vc_Cod_Local
                                              AND c.fec_ped_vta BETWEEN to_date(ac_Fec_Ini,'dd/MM/yyyy')
                                                                    AND to_date(ac_Fec_Fin,'dd/MM/yyyy')+1-1/24/60/60
                                          );
    -- 40.- Fidelizado Tarjeta
    -- FID_TARJETA_PEDIDO_PK primary key (COD_GRUPO_CIA, COD_LOCAL, NUM_PEDIDO, COD_TARJETA, DNI_CLI)
    --CREATE TABLE TT_FID_TARJETA_PEDIDO AS
    EXECUTE IMMEDIATE 'truncate TABLE TT_FID_TARJETA_PEDIDO drop storage';
    INSERT INTO TT_FID_TARJETA_PEDIDO (cod_grupo_cia,
                                        cod_local,
                                        num_pedido,
                                        dni_cli,
                                        cant_dcto,
                                        usu_crea_tarjeta_pedido,
                                        fec_crea_tarjeta_pedido,
                                        usu_mod_tarjeta_pedido,
                                        fec_mod_tarjeta_pedido,
                                        cod_tarjeta
                                        )
    SELECT
    f.cod_grupo_cia,
    f.cod_local,
    f.num_pedido,
    f.dni_cli,
    f.cant_dcto,
    f.usu_crea_tarjeta_pedido,
    f.fec_crea_tarjeta_pedido,
    f.usu_mod_tarjeta_pedido,
    f.fec_mod_tarjeta_pedido,
    f.cod_tarjeta
    FROM FID_TARJETA_PEDIDO f
    WHERE f.cod_grupo_cia = '001'
    AND (f.cod_local, f.num_pedido ) IN (
                                           SELECT c.cod_local,c.num_ped_vta
                                            FROM VTA_PEDIDO_VTA_CAB c
                                            WHERE c.cod_grupo_cia = '001'
                                              AND c.cod_local     = vc_Cod_Local
                                              AND c.fec_ped_vta BETWEEN to_date(ac_Fec_Ini,'dd/MM/yyyy')
                                                                    AND to_date(ac_Fec_Fin,'dd/MM/yyyy')+1-1/24/60/60
                                          );
     COMMIT;

END;
/*------------------------------------------------------------------------------------------------------------------------------------------
GOAL : Enviar los Temporales a Matriz, Temporalmente en desusso por que genera lentitud
------------------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_ENVIA_TEMP_A_MATRIZ
AS
 --vc_SQL VARCHAR2(32767);
BEGIN
 NULL;
/* -- 10.- Eliminar Datos Antiguos en Matriz XE_000
 vc_SQL:='DELETE FROM ptoventa.TT_VTA_PEDIDO_VTA_CAB@XE_000';
 EXECUTE IMMEDIATE vc_SQL;

 DELETE FROM ptoventa.TT_VTA_PEDIDO_VTA_DET@XE_000;
 DELETE FROM ptoventa.TT_VTA_COMP_PAGO@XE_000;
 DELETE FROM ptoventa.TT_FID_TARJETA_PEDIDO@XE_000;

 -- 20.- Carga Datos Nuevos
 INSERT INTO ptoventa.TT_VTA_PEDIDO_VTA_CAB@XE_000
 SELECT * FROM TT_VTA_PEDIDO_VTA_CAB;

 INSERT INTO ptoventa.TT_VTA_PEDIDO_VTA_DET@XE_000
 SELECT * FROM TT_VTA_PEDIDO_VTA_DET;

 INSERT INTO ptoventa.TT_VTA_COMP_PAGO@XE_000
 SELECT * FROM TT_VTA_COMP_PAGO;

 INSERT INTO ptoventa.TT_FID_TARJETA_PEDIDO@XE_000
 SELECT * FROM TT_FID_TARJETA_PEDIDO;*/


 COMMIT;

END;
/*-------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_VTA_PEDIDO_VTA_CAB(
                                   ac_Fec_Ini IN CHAR,
                                   ac_Fec_Fin IN CHAR,
                                   ac_Cod_Local IN CHAR
                                   )
RETURN TYP_VTA_PEDIDO_VTA_CAB
AS
  Arr_VTA_PEDIDO_VTA_CAB TYP_VTA_PEDIDO_VTA_CAB;
BEGIN
   SELECT c.*
   BULK COLLECT
   INTO Arr_VTA_PEDIDO_VTA_CAB
   FROM VTA_PEDIDO_VTA_CAB c
    WHERE c.cod_grupo_cia = '001'
      AND c.cod_local     = ac_Cod_Local
      AND c.fec_ped_vta BETWEEN to_date(ac_Fec_Ini,'dd/MM/yyyy')
                            AND to_date(ac_Fec_Fin,'dd/MM/yyyy')+1-1/24/60/60;
   RETURN Arr_VTA_PEDIDO_VTA_CAB;

END;
/******************************************************************************************************************************************/
end PKG_ENVIA_VENTA;
/

