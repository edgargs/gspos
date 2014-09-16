--------------------------------------------------------
--  DDL for Package Body PKG_DOCUMENTO_MF
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "BTLPROD"."PKG_DOCUMENTO_MF" IS

FUNCTION SP_GRABA_TABLAS_TMP_RAC(pCodGrupo_Cia_in CHAR,
                                 pCod_local_in    CHAR,
                                 pNum_Ped_Vta_in  CHAR,
                                 pIndNotaCred     CHAR) RETURN CHAR AS
  V_CIA ptoventa.pbl_local.cod_cia%TYPE;
BEGIN

  select l.cia
    into v_cia
    from nuevo.aux_mae_local ml
   inner join nuevo.mae_local l
      on ml.cod_local = l.cod_local
   where ml.cod_local_sap = pcod_local_in;

  IF V_CIA IS NULL THEN
     ROLLBACK;
     RAISE_APPLICATION_ERROR(-20055,'No se Encuentra Homologado el Local');
     RETURN 'N';
  END IF;


  IF V_CIA = CONS_CIA_MIFARMA THEN
    IF TRIM(pIndNotaCred) ='S' THEN

         INSERT INTO BTLPROD.RAC_VTA_PEDIDO_VTA_CAB
         (cod_grupo_cia,   cod_local,   num_ped_vta,   cod_cli_local,   sec_mov_caja,   fec_ped_vta,   val_bruto_ped_vta,
          val_neto_ped_vta,   val_redondeo_ped_vta,   val_igv_ped_vta,   val_dcto_ped_vta,   tip_ped_vta,   val_tip_cambio_ped_vta,
          num_ped_diario,   cant_items_ped_vta,   est_ped_vta,   tip_comp_pago,   nom_cli_ped_vta,   dir_cli_ped_vta,
          ruc_cli_ped_vta,   usu_crea_ped_vta_cab,   fec_crea_ped_vta_cab,   usu_mod_ped_vta_cab,   fec_mod_ped_vta_cab,
          ind_pedido_anul,   ind_distr_gratuita,   cod_local_atencion,   num_ped_vta_origen,   obs_forma_pago,   obs_ped_vta,
          cod_dir,   num_telefono,   fec_ruteo_ped_vta_cab,   fec_salida_local,   fec_entrega_ped_vta_cab,   fec_retorno_local,
          cod_ruteador,   cod_motorizado,   ind_deliv_automatico,   num_ped_rec,   ind_conv_enteros,   ind_ped_convenio,
          cod_convenio,   num_pedido_delivery,   cod_local_procedencia,   ip_pc,   cod_rpta_recarga,   ind_fid,  motivo_anulacion,
          dni_cli,   ind_camp_acumulada,   fec_ini_cobro,   fec_fin_cobro,   sec_usu_local,   punto_llegada,   ind_fp_fid_efectivo,
          ind_fp_fid_tarjeta,   cod_fp_fid_tarjeta,   cod_cli_conv,   cod_barra_conv,   ind_conv_btl_mf,   name_pc_cob_ped,   ip_cob_ped,
          dni_usu_local,   fec_proceso_rac,   fecha_proceso_anula_rac)
         SELECT cod_grupo_cia,   cod_local,   num_ped_vta,   cod_cli_local,   sec_mov_caja,   fec_ped_vta,   val_bruto_ped_vta,
         val_neto_ped_vta,   val_redondeo_ped_vta,   val_igv_ped_vta,   val_dcto_ped_vta,   tip_ped_vta,   val_tip_cambio_ped_vta,
         num_ped_diario,   cant_items_ped_vta,   est_ped_vta,   tip_comp_pago,   nom_cli_ped_vta,   dir_cli_ped_vta,
         ruc_cli_ped_vta,   usu_crea_ped_vta_cab,   fec_crea_ped_vta_cab,   usu_mod_ped_vta_cab,   fec_mod_ped_vta_cab,
         ind_pedido_anul,   ind_distr_gratuita,   cod_local_atencion,   num_ped_vta_origen,   obs_forma_pago,   obs_ped_vta,
         cod_dir,   num_telefono,   fec_ruteo_ped_vta_cab,   fec_salida_local,   fec_entrega_ped_vta_cab,   fec_retorno_local,
         cod_ruteador,   cod_motorizado,   ind_deliv_automatico,   num_ped_rec,   ind_conv_enteros,   ind_ped_convenio,
         cod_convenio,   num_pedido_delivery,   cod_local_procedencia,   ip_pc,   cod_rpta_recarga,   ind_fid,  motivo_anulacion,
         dni_cli,   ind_camp_acumulada,   fec_ini_cobro,   fec_fin_cobro,   sec_usu_local,   punto_llegada,   ind_fp_fid_efectivo,
         ind_fp_fid_tarjeta,   cod_fp_fid_tarjeta,   cod_cli_conv,   cod_barra_conv,   ind_conv_btl_mf,   name_pc_cob_ped,   ip_cob_ped,
         dni_usu_local,   fec_proceso_rac,   fecha_proceso_anula_rac
         FROM PTOVENTA.VTA_PEDIDO_VTA_CAB@XE_000 C
         WHERE C.COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND C.COD_LOCAL = pCod_local_in
         AND C.NUM_PED_VTA = pNum_Ped_Vta_in;


         INSERT INTO BTLPROD.RAC_VTA_PEDIDO_VTA_DET
         (cod_grupo_cia,   cod_local,   num_ped_vta,   sec_ped_vta_det,   cod_prod,   cant_atendida,   val_prec_vta,   val_prec_total,   porc_dcto_1,   porc_dcto_2,
          porc_dcto_3,   porc_dcto_total,   est_ped_vta_det,   val_total_bono,   val_frac,   sec_comp_pago,   sec_usu_local,
          usu_crea_ped_vta_det,   fec_crea_ped_vta_det,   usu_mod_ped_vta_det,   fec_mod_ped_vta_det,   val_prec_lista,
          val_igv,   unid_vta,   ind_exonerado_igv,   sec_grupo_impr,   cant_usada_nc,   sec_comp_pago_origen,   num_lote_prod,
          fec_proceso_guia_rd,   desc_num_tel_rec,   val_num_trace,   val_cod_aprobacion,   desc_num_tarj_virtual,   val_num_pin,   fec_vencimiento_lote,
          val_prec_public,   ind_calculo_max_min,   fec_exclusion,   fecha_tx,   hora_tx,   cod_prom,   ind_origen_prod,   val_frac_local,
          cant_frac_local,   cant_xdia_tra,   cant_dias_tra,   ind_zan,   val_prec_prom,   datos_imp_virtual,   cod_camp_cupon,
          ahorro,   porc_dcto_calc,   porc_zan,   ind_prom_automatico,   ahorro_pack,   porc_dcto_calc_pack,   cod_grupo_rep,
          cod_grupo_rep_edmundo,   sec_respaldo_stk,   num_comp_pago,   sec_comp_pago_benef,   sec_comp_pago_empre)
         SELECT cod_grupo_cia,   cod_local,   num_ped_vta,   sec_ped_vta_det,   cod_prod,   cant_atendida,   val_prec_vta,   val_prec_total,   porc_dcto_1,   porc_dcto_2,
         porc_dcto_3,   porc_dcto_total,   est_ped_vta_det,   val_total_bono,   val_frac,   sec_comp_pago,   sec_usu_local,
         usu_crea_ped_vta_det,   fec_crea_ped_vta_det,   usu_mod_ped_vta_det,   fec_mod_ped_vta_det,   val_prec_lista,
         val_igv,   unid_vta,   ind_exonerado_igv,   sec_grupo_impr,   cant_usada_nc,   sec_comp_pago_origen,   num_lote_prod,
         fec_proceso_guia_rd,   desc_num_tel_rec,   val_num_trace,   val_cod_aprobacion,   desc_num_tarj_virtual,   val_num_pin,   fec_vencimiento_lote,
         val_prec_public,   ind_calculo_max_min,   fec_exclusion,   fecha_tx,   hora_tx,   cod_prom,   ind_origen_prod,   val_frac_local,
         cant_frac_local,   cant_xdia_tra,   cant_dias_tra,   ind_zan,   val_prec_prom,   datos_imp_virtual,   cod_camp_cupon,
         ahorro,   porc_dcto_calc,   porc_zan,   ind_prom_automatico,   ahorro_pack,   porc_dcto_calc_pack,   cod_grupo_rep,
         cod_grupo_rep_edmundo,   sec_respaldo_stk,   num_comp_pago,   sec_comp_pago_benef,   sec_comp_pago_empre
         FROM PTOVENTA.VTA_PEDIDO_VTA_DET@XE_000
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

         INSERT INTO BTLPROD.RAC_VTA_COMP_PAGO
         SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_COMP_PAGO,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_MOV_CAJA,SEC_MOV_CAJA_ANUL,CANT_ITEM,COD_CLI_LOCAL,
                NOM_IMPR_COMP,DIREC_IMPR_COMP,NUM_DOC_IMPR,VAL_BRUTO_COMP_PAGO,VAL_NETO_COMP_PAGO,VAL_DCTO_COMP_PAGO,VAL_AFECTO_COMP_PAGO,VAL_IGV_COMP_PAGO,
                VAL_REDONDEO_COMP_PAGO,PORC_IGV_COMP_PAGO,USU_CREA_COMP_PAGO,FEC_CREA_COMP_PAGO,USU_MOD_COMP_PAGO,FEC_MOD_COMP_PAGO,FEC_ANUL_COMP_PAGO,IND_COMP_ANUL,
                NUM_PEDIDO_ANUL,NUM_SEC_DOC_SAP,FEC_PROCESO_SAP,NUM_SEC_DOC_SAP_ANUL,FEC_PROCESO_SAP_ANUL,IND_RECLAMO_NAVSAT,VAL_DCTO_COMP,MOTIVO_ANULACION,FECHA_COBRO,
                FECHA_ANULACION,FECH_IMP_COBRO,FECH_IMP_ANUL,TIP_CLIEN_CONVENIO,VAL_COPAGO_COMP_PAGO,VAL_IGV_COMP_COPAGO,NUM_COMP_COPAGO_REF,IND_AFECTA_KARDEX,PCT_BENEFICIARIO,
                PCT_EMPRESA,IND_COMP_CREDITO,TIP_COMP_PAGO_REF
         FROM PTOVENTA.VTA_COMP_PAGO@XE_000
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

         INSERT INTO BTLPROD.RAC_VTA_FORMA_PAGO_PEDIDO
         SELECT COD_GRUPO_CIA,COD_LOCAL,COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,NUM_TARJ,FEC_VENC_TARJ,NOM_TARJ,
         FEC_CREA_FORMA_PAGO_PED,USU_CREA_FORMA_PAGO_PED,FEC_MOD_FORMA_PAGO_PED,USU_MOD_FORMA_PAGO_PED,CANT_CUPON,TIPO_AUTORIZACION,COD_LOTE,COD_AUTORIZACION,
         DNI_CLI_TARJ
         FROM PTOVENTA.VTA_FORMA_PAGO_PEDIDO@XE_000
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

         INSERT INTO BTLPROD.RAC_CON_BTL_MF_PED_VTA
         (cod_grupo_cia,   cod_local,   num_ped_vta,   cod_campo,   cod_convenio,   cod_cliente,   fec_crea_ped_vta_cli,   usu_crea_ped_vta_cli,
          fec_mod_ped_vta_cli,   usu_mod_ped_vta_cli,   descripcion_campo,   nombre_campo,   flg_imprime,   cod_valor_in)
         SELECT cod_grupo_cia,         cod_local,         num_ped_vta,         cod_campo,         cod_convenio,
         cod_cliente,         fec_crea_ped_vta_cli,         usu_crea_ped_vta_cli,         fec_mod_ped_vta_cli,         usu_mod_ped_vta_cli,         descripcion_campo,
         nombre_campo,         flg_imprime,         cod_valor_in
         FROM PTOVENTA.CON_BTL_MF_PED_VTA@XE_000
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

    ELSE
         INSERT INTO BTLPROD.RAC_VTA_PEDIDO_VTA_CAB(cod_grupo_cia,
         cod_local, num_ped_vta, cod_cli_local, sec_mov_caja, fec_ped_vta, val_bruto_ped_vta, val_neto_ped_vta,
         val_redondeo_ped_vta, val_igv_ped_vta, val_dcto_ped_vta, tip_ped_vta, val_tip_cambio_ped_vta, num_ped_diario,
         cant_items_ped_vta, est_ped_vta, tip_comp_pago, nom_cli_ped_vta, dir_cli_ped_vta, ruc_cli_ped_vta, usu_crea_ped_vta_cab,
         fec_crea_ped_vta_cab, usu_mod_ped_vta_cab, fec_mod_ped_vta_cab, ind_pedido_anul, ind_distr_gratuita, cod_local_atencion,
         num_ped_vta_origen, obs_forma_pago, obs_ped_vta, cod_dir, num_telefono, fec_ruteo_ped_vta_cab, fec_salida_local, fec_entrega_ped_vta_cab,
         fec_retorno_local, cod_ruteador, cod_motorizado, ind_deliv_automatico, num_ped_rec, ind_conv_enteros, ind_ped_convenio,
         cod_convenio, num_pedido_delivery, cod_local_procedencia, ip_pc, cod_rpta_recarga, ind_fid, motivo_anulacion, dni_cli,
         ind_camp_acumulada, fec_ini_cobro, fec_fin_cobro, punto_llegada, sec_usu_local, cod_nit, porc_copago, ind_fp_fid_efectivo,
         ind_fp_fid_tarjeta, cod_fp_fid_tarjeta, cod_cli_conv, cod_barra_conv, ind_conv_btl_mf, name_pc_cob_ped, ip_cob_ped, dni_usu_local,
         fec_proceso_rac, fecha_proceso_anula_rac)
         SELECT cod_grupo_cia,
         cod_local, num_ped_vta, cod_cli_local, sec_mov_caja, fec_ped_vta, val_bruto_ped_vta, val_neto_ped_vta,
         val_redondeo_ped_vta, val_igv_ped_vta, val_dcto_ped_vta, tip_ped_vta, val_tip_cambio_ped_vta, num_ped_diario,
         cant_items_ped_vta, est_ped_vta, tip_comp_pago, nom_cli_ped_vta, dir_cli_ped_vta, ruc_cli_ped_vta, usu_crea_ped_vta_cab,
         fec_crea_ped_vta_cab, usu_mod_ped_vta_cab, fec_mod_ped_vta_cab, ind_pedido_anul, ind_distr_gratuita, cod_local_atencion,
         num_ped_vta_origen, obs_forma_pago, obs_ped_vta, cod_dir, num_telefono, fec_ruteo_ped_vta_cab, fec_salida_local, fec_entrega_ped_vta_cab,
         fec_retorno_local, cod_ruteador, cod_motorizado, ind_deliv_automatico, num_ped_rec, ind_conv_enteros, ind_ped_convenio,
         cod_convenio, num_pedido_delivery, cod_local_procedencia, ip_pc, cod_rpta_recarga, ind_fid, motivo_anulacion, dni_cli,
         ind_camp_acumulada, fec_ini_cobro, fec_fin_cobro, punto_llegada, sec_usu_local, cod_nit, porc_copago, ind_fp_fid_efectivo,
         ind_fp_fid_tarjeta, cod_fp_fid_tarjeta, cod_cli_conv, cod_barra_conv, ind_conv_btl_mf, name_pc_cob_ped, ip_cob_ped, dni_usu_local,
         fec_proceso_rac, fecha_proceso_anula_rac
         FROM PTOVENTA.RAC_VTA_PEDIDO_VTA_CAB@XE_000
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;


         INSERT INTO BTLPROD.RAC_VTA_PEDIDO_VTA_DET
         (cod_grupo_cia,   cod_local,   num_ped_vta,   sec_ped_vta_det,   cod_prod,   cant_atendida,   val_prec_vta,
          val_prec_total,   porc_dcto_1,   porc_dcto_2,   porc_dcto_3,   porc_dcto_total,   est_ped_vta_det,   val_total_bono,
          val_frac,   sec_comp_pago,   sec_usu_local,   usu_crea_ped_vta_det,   fec_crea_ped_vta_det,   usu_mod_ped_vta_det,
          fec_mod_ped_vta_det,   val_prec_lista,   val_igv,   unid_vta,   ind_exonerado_igv,   sec_grupo_impr,
          cant_usada_nc,   sec_comp_pago_origen,   num_lote_prod,   fec_proceso_guia_rd,   desc_num_tel_rec,   val_num_trace,
          val_cod_aprobacion,   desc_num_tarj_virtual,   val_num_pin,   fec_vencimiento_lote,   val_prec_public,   ind_calculo_max_min,
          fec_exclusion,   fecha_tx,   hora_tx,   cod_prom,   ind_origen_prod,   val_frac_local,   cant_frac_local,   cant_xdia_tra,
          cant_dias_tra,   ind_zan,   val_prec_prom,   datos_imp_virtual,   cod_camp_cupon,   ahorro,   porc_dcto_calc,
          porc_zan,   porc_dcto_calc_pack,   cod_grupo_rep,   cod_grupo_rep_edmundo,   ahorro_pack,   ind_prom_automatico,
          sec_respaldo_stk,   num_comp_pago,   sec_comp_pago_benef,   sec_comp_pago_empre)
         SELECT cod_grupo_cia,   cod_local,   num_ped_vta,   sec_ped_vta_det,   cod_prod,   cant_atendida,   val_prec_vta,
         val_prec_total,   porc_dcto_1,   porc_dcto_2,   porc_dcto_3,   porc_dcto_total,   est_ped_vta_det,   val_total_bono,
         val_frac,   sec_comp_pago,   sec_usu_local,   usu_crea_ped_vta_det,   fec_crea_ped_vta_det,   usu_mod_ped_vta_det,
         fec_mod_ped_vta_det,   val_prec_lista,   val_igv,   unid_vta,   ind_exonerado_igv,   sec_grupo_impr,
         cant_usada_nc,   sec_comp_pago_origen,   num_lote_prod,   fec_proceso_guia_rd,   desc_num_tel_rec,   val_num_trace,
         val_cod_aprobacion,   desc_num_tarj_virtual,   val_num_pin,   fec_vencimiento_lote,   val_prec_public,   ind_calculo_max_min,
         fec_exclusion,   fecha_tx,   hora_tx,   cod_prom,   ind_origen_prod,   val_frac_local,   cant_frac_local,   cant_xdia_tra,
         cant_dias_tra,   ind_zan,   val_prec_prom,   datos_imp_virtual,   cod_camp_cupon,   ahorro,   porc_dcto_calc,
         porc_zan,   porc_dcto_calc_pack,   cod_grupo_rep,   cod_grupo_rep_edmundo,   ahorro_pack,   ind_prom_automatico,
         sec_respaldo_stk,   num_comp_pago,   sec_comp_pago_benef,   sec_comp_pago_empre
         FROM PTOVENTA.RAC_VTA_PEDIDO_VTA_DET@XE_000
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;


         INSERT INTO BTLPROD.RAC_VTA_COMP_PAGO
         SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_COMP_PAGO,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_MOV_CAJA,SEC_MOV_CAJA_ANUL,CANT_ITEM,COD_CLI_LOCAL,NOM_IMPR_COMP,DIREC_IMPR_COMP,
                NUM_DOC_IMPR,VAL_BRUTO_COMP_PAGO,VAL_NETO_COMP_PAGO,VAL_DCTO_COMP_PAGO,VAL_AFECTO_COMP_PAGO,VAL_IGV_COMP_PAGO,VAL_REDONDEO_COMP_PAGO,PORC_IGV_COMP_PAGO,USU_CREA_COMP_PAGO,
                FEC_CREA_COMP_PAGO,USU_MOD_COMP_PAGO,FEC_MOD_COMP_PAGO,FEC_ANUL_COMP_PAGO,IND_COMP_ANUL,NUM_PEDIDO_ANUL,NUM_SEC_DOC_SAP,FEC_PROCESO_SAP,NUM_SEC_DOC_SAP_ANUL,FEC_PROCESO_SAP_ANUL,
                IND_RECLAMO_NAVSAT,VAL_DCTO_COMP,MOTIVO_ANULACION,FECHA_COBRO,FECHA_ANULACION,FECH_IMP_COBRO,FECH_IMP_ANUL,TIP_CLIEN_CONVENIO,VAL_COPAGO_COMP_PAGO,VAL_IGV_COMP_COPAGO,NUM_COMP_COPAGO_REF,
                IND_AFECTA_KARDEX,PCT_BENEFICIARIO,PCT_EMPRESA,IND_COMP_CREDITO,TIP_COMP_PAGO_REF
         FROM PTOVENTA.RAC_VTA_COMP_PAGO@XE_000
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;


         INSERT INTO BTLPROD.RAC_VTA_FORMA_PAGO_PEDIDO
         SELECT COD_GRUPO_CIA,COD_LOCAL,COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,NUM_TARJ,FEC_VENC_TARJ,NOM_TARJ,FEC_CREA_FORMA_PAGO_PED,USU_CREA_FORMA_PAGO_PED,
                FEC_MOD_FORMA_PAGO_PED,USU_MOD_FORMA_PAGO_PED,CANT_CUPON,TIPO_AUTORIZACION,COD_LOTE,COD_AUTORIZACION,DNI_CLI_TARJ
         FROM PTOVENTA.RAC_VTA_FORMA_PAGO_PEDIDO@XE_000
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

         INSERT INTO BTLPROD.RAC_CON_BTL_MF_PED_VTA
         SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,COD_CAMPO,COD_CONVENIO,COD_CLIENTE,FEC_CREA_PED_VTA_CLI,USU_CREA_PED_VTA_CLI,FEC_MOD_PED_VTA_CLI,USU_MOD_PED_VTA_CLI,DESCRIPCION_CAMPO,NOMBRE_CAMPO,FLG_IMPRIME,
                COD_VALOR_IN
         FROM PTOVENTA.RAC_CON_BTL_MF_PED_VTA@XE_000
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;


    END IF;
END IF;

IF V_CIA=CONS_CIA_FASA or V_CIA=CONS_CIA_BTL THEN
    IF TRIM(pIndNotaCred) ='S' THEN

         INSERT INTO BTLPROD.RAC_VTA_PEDIDO_VTA_CAB
         (cod_grupo_cia,   cod_local,   num_ped_vta,   cod_cli_local,   sec_mov_caja,   fec_ped_vta,   val_bruto_ped_vta,
          val_neto_ped_vta,   val_redondeo_ped_vta,   val_igv_ped_vta,   val_dcto_ped_vta,   tip_ped_vta,   val_tip_cambio_ped_vta,
          num_ped_diario,   cant_items_ped_vta,   est_ped_vta,   tip_comp_pago,   nom_cli_ped_vta,   dir_cli_ped_vta,
          ruc_cli_ped_vta,   usu_crea_ped_vta_cab,   fec_crea_ped_vta_cab,   usu_mod_ped_vta_cab,   fec_mod_ped_vta_cab,
          ind_pedido_anul,   ind_distr_gratuita,   cod_local_atencion,   num_ped_vta_origen,   obs_forma_pago,   obs_ped_vta,
          cod_dir,   num_telefono,   fec_ruteo_ped_vta_cab,   fec_salida_local,   fec_entrega_ped_vta_cab,   fec_retorno_local,
          cod_ruteador,   cod_motorizado,   ind_deliv_automatico,   num_ped_rec,   ind_conv_enteros,   ind_ped_convenio,
          cod_convenio,   num_pedido_delivery,   cod_local_procedencia,   ip_pc,   cod_rpta_recarga,   ind_fid,  motivo_anulacion,
          dni_cli,   ind_camp_acumulada,   fec_ini_cobro,   fec_fin_cobro,   sec_usu_local,   punto_llegada,   ind_fp_fid_efectivo,
          ind_fp_fid_tarjeta,   cod_fp_fid_tarjeta,   cod_cli_conv,   cod_barra_conv,   ind_conv_btl_mf,   name_pc_cob_ped,   ip_cob_ped,
          dni_usu_local,   fec_proceso_rac,   fecha_proceso_anula_rac)
         SELECT cod_grupo_cia,   cod_local,   num_ped_vta,   cod_cli_local,   sec_mov_caja,   fec_ped_vta,   val_bruto_ped_vta,
         val_neto_ped_vta,   val_redondeo_ped_vta,   val_igv_ped_vta,   val_dcto_ped_vta,   tip_ped_vta,   val_tip_cambio_ped_vta,
         num_ped_diario,   cant_items_ped_vta,   est_ped_vta,   tip_comp_pago,   nom_cli_ped_vta,   dir_cli_ped_vta,
         ruc_cli_ped_vta,   usu_crea_ped_vta_cab,   fec_crea_ped_vta_cab,   usu_mod_ped_vta_cab,   fec_mod_ped_vta_cab,
         ind_pedido_anul,   ind_distr_gratuita,   cod_local_atencion,   num_ped_vta_origen,   obs_forma_pago,   obs_ped_vta,
         cod_dir,   num_telefono,   fec_ruteo_ped_vta_cab,   fec_salida_local,   fec_entrega_ped_vta_cab,   fec_retorno_local,
         cod_ruteador,   cod_motorizado,   ind_deliv_automatico,   num_ped_rec,   ind_conv_enteros,   ind_ped_convenio,
         cod_convenio,   num_pedido_delivery,   cod_local_procedencia,   ip_pc,   cod_rpta_recarga,   ind_fid,  motivo_anulacion,
         dni_cli,   ind_camp_acumulada,   fec_ini_cobro,   fec_fin_cobro,   sec_usu_local,   punto_llegada,   ind_fp_fid_efectivo,
         ind_fp_fid_tarjeta,   cod_fp_fid_tarjeta,   cod_cli_conv,   cod_barra_conv,   ind_conv_btl_mf,   name_pc_cob_ped,   ip_cob_ped,
         dni_usu_local,   fec_proceso_rac,   fecha_proceso_anula_rac
         FROM PTOVENTA.VTA_PEDIDO_VTA_CAB C
         WHERE C.COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND C.COD_LOCAL = pCod_local_in
         AND C.NUM_PED_VTA = pNum_Ped_Vta_in;


         INSERT INTO BTLPROD.RAC_VTA_PEDIDO_VTA_DET
         (cod_grupo_cia,   cod_local,   num_ped_vta,   sec_ped_vta_det,   cod_prod,   cant_atendida,   val_prec_vta,   val_prec_total,   porc_dcto_1,   porc_dcto_2,
          porc_dcto_3,   porc_dcto_total,   est_ped_vta_det,   val_total_bono,   val_frac,   sec_comp_pago,   sec_usu_local,
          usu_crea_ped_vta_det,   fec_crea_ped_vta_det,   usu_mod_ped_vta_det,   fec_mod_ped_vta_det,   val_prec_lista,
          val_igv,   unid_vta,   ind_exonerado_igv,   sec_grupo_impr,   cant_usada_nc,   sec_comp_pago_origen,   num_lote_prod,
          fec_proceso_guia_rd,   desc_num_tel_rec,   val_num_trace,   val_cod_aprobacion,   desc_num_tarj_virtual,   val_num_pin,   fec_vencimiento_lote,
          val_prec_public,   ind_calculo_max_min,   fec_exclusion,   fecha_tx,   hora_tx,   cod_prom,   ind_origen_prod,   val_frac_local,
          cant_frac_local,   cant_xdia_tra,   cant_dias_tra,   ind_zan,   val_prec_prom,   datos_imp_virtual,   cod_camp_cupon,
          ahorro,   porc_dcto_calc,   porc_zan,   ind_prom_automatico,   ahorro_pack,   porc_dcto_calc_pack,   cod_grupo_rep,
          cod_grupo_rep_edmundo,   sec_respaldo_stk,   num_comp_pago,   sec_comp_pago_benef,   sec_comp_pago_empre)
         SELECT cod_grupo_cia,   cod_local,   num_ped_vta,   sec_ped_vta_det,   cod_prod,   cant_atendida,   val_prec_vta,   val_prec_total,   porc_dcto_1,   porc_dcto_2,
         porc_dcto_3,   porc_dcto_total,   est_ped_vta_det,   val_total_bono,   val_frac,   sec_comp_pago,   sec_usu_local,
         usu_crea_ped_vta_det,   fec_crea_ped_vta_det,   usu_mod_ped_vta_det,   fec_mod_ped_vta_det,   val_prec_lista,
         val_igv,   unid_vta,   ind_exonerado_igv,   sec_grupo_impr,   cant_usada_nc,   sec_comp_pago_origen,   num_lote_prod,
         fec_proceso_guia_rd,   desc_num_tel_rec,   val_num_trace,   val_cod_aprobacion,   desc_num_tarj_virtual,   val_num_pin,   fec_vencimiento_lote,
         val_prec_public,   ind_calculo_max_min,   fec_exclusion,   fecha_tx,   hora_tx,   cod_prom,   ind_origen_prod,   val_frac_local,
         cant_frac_local,   cant_xdia_tra,   cant_dias_tra,   ind_zan,   val_prec_prom,   datos_imp_virtual,   cod_camp_cupon,
         ahorro,   porc_dcto_calc,   porc_zan,   ind_prom_automatico,   ahorro_pack,   porc_dcto_calc_pack,   cod_grupo_rep,
         cod_grupo_rep_edmundo,   sec_respaldo_stk,   num_comp_pago,   sec_comp_pago_benef,   sec_comp_pago_empre
         FROM PTOVENTA.VTA_PEDIDO_VTA_DET
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

         INSERT INTO BTLPROD.RAC_VTA_COMP_PAGO
         SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_COMP_PAGO,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_MOV_CAJA,SEC_MOV_CAJA_ANUL,CANT_ITEM,COD_CLI_LOCAL,
                NOM_IMPR_COMP,DIREC_IMPR_COMP,NUM_DOC_IMPR,VAL_BRUTO_COMP_PAGO,VAL_NETO_COMP_PAGO,VAL_DCTO_COMP_PAGO,VAL_AFECTO_COMP_PAGO,VAL_IGV_COMP_PAGO,
                VAL_REDONDEO_COMP_PAGO,PORC_IGV_COMP_PAGO,USU_CREA_COMP_PAGO,FEC_CREA_COMP_PAGO,USU_MOD_COMP_PAGO,FEC_MOD_COMP_PAGO,FEC_ANUL_COMP_PAGO,IND_COMP_ANUL,
                NUM_PEDIDO_ANUL,NUM_SEC_DOC_SAP,FEC_PROCESO_SAP,NUM_SEC_DOC_SAP_ANUL,FEC_PROCESO_SAP_ANUL,IND_RECLAMO_NAVSAT,VAL_DCTO_COMP,MOTIVO_ANULACION,FECHA_COBRO,
                FECHA_ANULACION,FECH_IMP_COBRO,FECH_IMP_ANUL,TIP_CLIEN_CONVENIO,VAL_COPAGO_COMP_PAGO,VAL_IGV_COMP_COPAGO,NUM_COMP_COPAGO_REF,IND_AFECTA_KARDEX,PCT_BENEFICIARIO,
                PCT_EMPRESA,IND_COMP_CREDITO,TIP_COMP_PAGO_REF
         FROM PTOVENTA.VTA_COMP_PAGO
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

         INSERT INTO BTLPROD.RAC_VTA_FORMA_PAGO_PEDIDO
         SELECT COD_GRUPO_CIA,COD_LOCAL,COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,NUM_TARJ,FEC_VENC_TARJ,NOM_TARJ,
         FEC_CREA_FORMA_PAGO_PED,USU_CREA_FORMA_PAGO_PED,FEC_MOD_FORMA_PAGO_PED,USU_MOD_FORMA_PAGO_PED,CANT_CUPON,TIPO_AUTORIZACION,COD_LOTE,COD_AUTORIZACION,
         DNI_CLI_TARJ
         FROM PTOVENTA.VTA_FORMA_PAGO_PEDIDO
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

         INSERT INTO BTLPROD.RAC_CON_BTL_MF_PED_VTA
         (cod_grupo_cia,   cod_local,   num_ped_vta,   cod_campo,   cod_convenio,   cod_cliente,   fec_crea_ped_vta_cli,   usu_crea_ped_vta_cli,
          fec_mod_ped_vta_cli,   usu_mod_ped_vta_cli,   descripcion_campo,   nombre_campo,   flg_imprime,   cod_valor_in)
         SELECT cod_grupo_cia,         cod_local,         num_ped_vta,         cod_campo,         cod_convenio,
         cod_cliente,         fec_crea_ped_vta_cli,         usu_crea_ped_vta_cli,         fec_mod_ped_vta_cli,         usu_mod_ped_vta_cli,         descripcion_campo,
         nombre_campo,         flg_imprime,         cod_valor_in
         FROM PTOVENTA.CON_BTL_MF_PED_VTA
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

    ELSE
    -- 1 --
         INSERT INTO BTLPROD.RAC_VTA_PEDIDO_VTA_CAB(cod_grupo_cia,
         cod_local, num_ped_vta, cod_cli_local, sec_mov_caja, fec_ped_vta, val_bruto_ped_vta, val_neto_ped_vta,
         val_redondeo_ped_vta, val_igv_ped_vta, val_dcto_ped_vta, tip_ped_vta, val_tip_cambio_ped_vta, num_ped_diario,
         cant_items_ped_vta, est_ped_vta, tip_comp_pago, nom_cli_ped_vta, dir_cli_ped_vta, ruc_cli_ped_vta, usu_crea_ped_vta_cab,
         fec_crea_ped_vta_cab, usu_mod_ped_vta_cab, fec_mod_ped_vta_cab, ind_pedido_anul, ind_distr_gratuita, cod_local_atencion,
         num_ped_vta_origen, obs_forma_pago, obs_ped_vta, cod_dir, num_telefono, fec_ruteo_ped_vta_cab, fec_salida_local, fec_entrega_ped_vta_cab,
         fec_retorno_local, cod_ruteador, cod_motorizado, ind_deliv_automatico, num_ped_rec, ind_conv_enteros, ind_ped_convenio,
         cod_convenio, num_pedido_delivery, cod_local_procedencia, ip_pc, cod_rpta_recarga, ind_fid, motivo_anulacion, dni_cli,
         ind_camp_acumulada, fec_ini_cobro, fec_fin_cobro, punto_llegada, sec_usu_local, cod_nit, porc_copago, ind_fp_fid_efectivo,
         ind_fp_fid_tarjeta, cod_fp_fid_tarjeta, cod_cli_conv, cod_barra_conv, ind_conv_btl_mf, name_pc_cob_ped, ip_cob_ped, dni_usu_local,
         fec_proceso_rac, fecha_proceso_anula_rac)
         SELECT cod_grupo_cia,
         cod_local, num_ped_vta, cod_cli_local, sec_mov_caja, fec_ped_vta, val_bruto_ped_vta, val_neto_ped_vta,
         val_redondeo_ped_vta, val_igv_ped_vta, val_dcto_ped_vta, tip_ped_vta, val_tip_cambio_ped_vta, num_ped_diario,
         cant_items_ped_vta, est_ped_vta, tip_comp_pago, nom_cli_ped_vta, dir_cli_ped_vta, ruc_cli_ped_vta, usu_crea_ped_vta_cab,
         fec_crea_ped_vta_cab, usu_mod_ped_vta_cab, fec_mod_ped_vta_cab, ind_pedido_anul, ind_distr_gratuita, cod_local_atencion,
         num_ped_vta_origen, obs_forma_pago, obs_ped_vta, cod_dir, num_telefono, fec_ruteo_ped_vta_cab, fec_salida_local, fec_entrega_ped_vta_cab,
         fec_retorno_local, cod_ruteador, cod_motorizado, ind_deliv_automatico, num_ped_rec, ind_conv_enteros, ind_ped_convenio,
         cod_convenio, num_pedido_delivery, cod_local_procedencia, ip_pc, cod_rpta_recarga, ind_fid, motivo_anulacion, dni_cli,
         ind_camp_acumulada, fec_ini_cobro, fec_fin_cobro, punto_llegada, sec_usu_local, cod_nit, porc_copago, ind_fp_fid_efectivo,
         ind_fp_fid_tarjeta, cod_fp_fid_tarjeta, cod_cli_conv, cod_barra_conv, ind_conv_btl_mf, name_pc_cob_ped, ip_cob_ped, dni_usu_local,
         fec_proceso_rac, fecha_proceso_anula_rac
         FROM PTOVENTA.RAC_VTA_PEDIDO_VTA_CAB
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

    -- 2 --
         INSERT INTO BTLPROD.RAC_VTA_PEDIDO_VTA_DET
         (cod_grupo_cia,   cod_local,   num_ped_vta,   sec_ped_vta_det,   cod_prod,   cant_atendida,   val_prec_vta,
          val_prec_total,   porc_dcto_1,   porc_dcto_2,   porc_dcto_3,   porc_dcto_total,   est_ped_vta_det,   val_total_bono,
          val_frac,   sec_comp_pago,   sec_usu_local,   usu_crea_ped_vta_det,   fec_crea_ped_vta_det,   usu_mod_ped_vta_det,
          fec_mod_ped_vta_det,   val_prec_lista,   val_igv,   unid_vta,   ind_exonerado_igv,   sec_grupo_impr,
          cant_usada_nc,   sec_comp_pago_origen,   num_lote_prod,   fec_proceso_guia_rd,   desc_num_tel_rec,   val_num_trace,
          val_cod_aprobacion,   desc_num_tarj_virtual,   val_num_pin,   fec_vencimiento_lote,   val_prec_public,   ind_calculo_max_min,
          fec_exclusion,   fecha_tx,   hora_tx,   cod_prom,   ind_origen_prod,   val_frac_local,   cant_frac_local,   cant_xdia_tra,
          cant_dias_tra,   ind_zan,   val_prec_prom,   datos_imp_virtual,   cod_camp_cupon,   ahorro,   porc_dcto_calc,
          porc_zan,   porc_dcto_calc_pack,   cod_grupo_rep,   cod_grupo_rep_edmundo,   ahorro_pack,   ind_prom_automatico,
          sec_respaldo_stk,   num_comp_pago,   sec_comp_pago_benef,   sec_comp_pago_empre)
         SELECT cod_grupo_cia,   cod_local,   num_ped_vta,   sec_ped_vta_det,   cod_prod,   cant_atendida,   val_prec_vta,
         val_prec_total,   porc_dcto_1,   porc_dcto_2,   porc_dcto_3,   porc_dcto_total,   est_ped_vta_det,   val_total_bono,
         val_frac,   sec_comp_pago,   sec_usu_local,   usu_crea_ped_vta_det,   fec_crea_ped_vta_det,   usu_mod_ped_vta_det,
         fec_mod_ped_vta_det,   val_prec_lista,   val_igv,   unid_vta,   ind_exonerado_igv,   sec_grupo_impr,
         cant_usada_nc,   sec_comp_pago_origen,   num_lote_prod,   fec_proceso_guia_rd,   desc_num_tel_rec,   val_num_trace,
         val_cod_aprobacion,   desc_num_tarj_virtual,   val_num_pin,   fec_vencimiento_lote,   val_prec_public,   ind_calculo_max_min,
         fec_exclusion,   fecha_tx,   hora_tx,   cod_prom,   ind_origen_prod,   val_frac_local,   cant_frac_local,   cant_xdia_tra,
         cant_dias_tra,   ind_zan,   val_prec_prom,   datos_imp_virtual,   cod_camp_cupon,   ahorro,   porc_dcto_calc,
         porc_zan,   porc_dcto_calc_pack,   cod_grupo_rep,   cod_grupo_rep_edmundo,   ahorro_pack,   ind_prom_automatico,
         sec_respaldo_stk,   num_comp_pago,   sec_comp_pago_benef,   sec_comp_pago_empre
         FROM PTOVENTA.RAC_VTA_PEDIDO_VTA_DET
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

    -- 3 --
         INSERT INTO BTLPROD.RAC_VTA_COMP_PAGO
         SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_COMP_PAGO,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_MOV_CAJA,SEC_MOV_CAJA_ANUL,CANT_ITEM,COD_CLI_LOCAL,NOM_IMPR_COMP,DIREC_IMPR_COMP,
                NUM_DOC_IMPR,VAL_BRUTO_COMP_PAGO,VAL_NETO_COMP_PAGO,VAL_DCTO_COMP_PAGO,VAL_AFECTO_COMP_PAGO,VAL_IGV_COMP_PAGO,VAL_REDONDEO_COMP_PAGO,PORC_IGV_COMP_PAGO,USU_CREA_COMP_PAGO,
                FEC_CREA_COMP_PAGO,USU_MOD_COMP_PAGO,FEC_MOD_COMP_PAGO,FEC_ANUL_COMP_PAGO,IND_COMP_ANUL,NUM_PEDIDO_ANUL,NUM_SEC_DOC_SAP,FEC_PROCESO_SAP,NUM_SEC_DOC_SAP_ANUL,FEC_PROCESO_SAP_ANUL,
                IND_RECLAMO_NAVSAT,VAL_DCTO_COMP,MOTIVO_ANULACION,FECHA_COBRO,FECHA_ANULACION,FECH_IMP_COBRO,FECH_IMP_ANUL,TIP_CLIEN_CONVENIO,VAL_COPAGO_COMP_PAGO,VAL_IGV_COMP_COPAGO,NUM_COMP_COPAGO_REF,
                IND_AFECTA_KARDEX,PCT_BENEFICIARIO,PCT_EMPRESA,IND_COMP_CREDITO,TIP_COMP_PAGO_REF
         FROM PTOVENTA.RAC_VTA_COMP_PAGO
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

    -- 4 --
         INSERT INTO BTLPROD.RAC_VTA_FORMA_PAGO_PEDIDO
         SELECT COD_GRUPO_CIA,COD_LOCAL,COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,NUM_TARJ,FEC_VENC_TARJ,NOM_TARJ,FEC_CREA_FORMA_PAGO_PED,USU_CREA_FORMA_PAGO_PED,
                FEC_MOD_FORMA_PAGO_PED,USU_MOD_FORMA_PAGO_PED,CANT_CUPON,TIPO_AUTORIZACION,COD_LOTE,COD_AUTORIZACION,DNI_CLI_TARJ
         FROM PTOVENTA.RAC_VTA_FORMA_PAGO_PEDIDO
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;

    -- 5 --
         INSERT INTO BTLPROD.RAC_CON_BTL_MF_PED_VTA
         SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,COD_CAMPO,COD_CONVENIO,COD_CLIENTE,FEC_CREA_PED_VTA_CLI,USU_CREA_PED_VTA_CLI,FEC_MOD_PED_VTA_CLI,USU_MOD_PED_VTA_CLI,DESCRIPCION_CAMPO,NOMBRE_CAMPO,FLG_IMPRIME,
                COD_VALOR_IN
         FROM PTOVENTA.RAC_CON_BTL_MF_PED_VTA
         WHERE NUM_PED_VTA = pNum_Ped_Vta_in
         AND COD_GRUPO_CIA = pCodGrupo_Cia_in
         AND COD_LOCAL = pCod_local_in;


    END IF;

END IF;

RETURN 'S';

EXCEPTION
  when DUP_VAL_ON_INDEX then
    declare
        reg_cab          btlprod.cab_documento%rowtype;
        reg_cab2         btlcero.cab_guia_cliente%rowtype;
    begin
        for reg in (select xx.cod_grupo_cia,
                           xx.cod_local,
                           xx.num_ped_vta,
                           xx.sec_comp_pago,
                           tdc.cod_tipodoc,
                           xx.num_comp_pago
                      from hmeza1.tmp_ventas_convenios xx
                     inner join btlprod.aux_tipo_doc_conv tdc
                        on tdc.cod_grupo_cia = xx.cod_grupo_cia
                       and tdc.tip_comp_pago = xx.tip_comp_pago_cp
                     where xx.cod_grupo_cia = pcodgrupo_cia_in
                       and xx.cod_local = pcod_local_in
                       and xx.num_ped_vta = pnum_ped_vta_in) loop

            if reg.cod_tipodoc != 'GRL' then
                begin
                    select *
                      into reg_cab
                      from btlprod.cab_documento z
                     where z.cod_grupo_cia = reg.cod_grupo_cia
                       and z.cod_local_mf = reg.cod_local
                       and z.num_ped_vta = reg.num_ped_vta
                       and z.sec_comp_pago = reg.sec_comp_pago;

                    if (reg.cod_tipodoc = reg_cab.cod_tipo_documento) then
                      if (reg.num_comp_pago != reg_cab.num_documento) then
                        hmeza1.pkg_sincro_vtas_conv.sp_correlativo(a_cia                => reg_cab.cia,
                                                                   a_cod_local          => reg_cab.cod_local,
                                                                   a_cod_tipo_documento => reg_cab.cod_tipo_documento,
                                                                   a_num_documento_dice => reg_cab.num_documento,
                                                                   a_num_documento_debe => reg.num_comp_pago);
                      end if;
                    else
                      RAISE_APPLICATION_ERROR(-20050,'Tipos de documentos diferentes -> '||chr(13)||
                                                     'Tipo en rac: '||reg.cod_tipodoc||chr(13)||
                                                     'Nuevo tipo: '||reg_cab.cod_tipo_documento);
                    end if;

                exception
                    when no_data_found then
                        RAISE_APPLICATION_ERROR(-20050,'No se encontro documento para cambio de correlativo -> ' || sqlerrm);
                    when others then
                        RAISE_APPLICATION_ERROR(-20050,'Antes del cambio de correlativo -> ' || sqlerrm);
                end;
/*
                if reg_cab.num_documento is not null and
                   (reg.cod_tipodoc = reg_cab.cod_tipo_documento and
                   reg.num_comp_pago != reg_cab.num_documento) then
                    hmeza1.pkg_sincro_vtas_conv.sp_correlativo(a_cia                => reg_cab.cia,
                                                               a_cod_local          => reg_cab.cod_local,
                                                               a_cod_tipo_documento => reg_cab.cod_tipo_documento,
                                                               a_num_documento_dice => reg_cab.num_documento,
                                                               a_num_documento_debe => reg.num_comp_pago);
                end if;
*/
            else

                begin
                    select *
                      into reg_cab2
                      from btlcero.cab_guia_cliente z
                     where z.cod_grupo_cia = reg.cod_grupo_cia
                       and z.cod_local_mf = reg.cod_local
                       and z.num_ped_vta = reg.num_ped_vta
                       and z.sec_comp_pago = reg.sec_comp_pago;
                exception
                    when others then
                        RAISE_APPLICATION_ERROR(-20050,'GRL - No se encontro documento para cambio de correlativo -> ' || sqlerrm);
                end;

                if reg_cab2.num_guia is not null and
                   reg_cab2.num_guia != reg.num_comp_pago then
                    hmeza1.pkg_sincro_vtas_conv.sp_correlativo_guia(a_cia                => reg_cab2.cia,
                                                                    a_cod_local          => reg_cab2.cod_local,
                                                                    a_cod_tipo_documento => 'GRL',
                                                                    a_num_documento_dice => reg_cab2.num_guia,
                                                                    a_num_documento_debe => reg.num_comp_pago);
                end if;
            end if;

        end loop;
    end;
    RETURN 'X';
  WHEN OTHERS THEN
     ROLLBACK;
     RAISE_APPLICATION_ERROR(-20050,'Error al grabar tablas de Matriz a RAC: ' || sqlerrm);
     RETURN 'N';
END;


function SP_GRABA_DOCUMENTOS( pCodGrupo_Cia_in CHAR,
                              pCod_local_in    CHAR,
                              pNum_Ped_Vta_in  CHAR,
                              pIndNotaCred     CHAR default 'N',
                              pIndGrabaPed     CHAR default 'N'
                            ) RETURN CHAR
  AS
    --Cargo el cursor con LA CABECERA de los documento de ventas
    CURSOR C_CABECERA IS
      SELECT T.*,(T.MTO_TOTAL-T.MTO_EXONERADO-T.MTO_IMPUESTO) MTO_BASE_IMP
        FROM (
              SELECT COD_CIA,
                     COD_LOCAL,
                     COD_TIPO_DOCUMENTO,
                     NUM_DOCUMENTO,
                     USU_EMISION,
                     COD_CLIENTE,
                     COD_CONVENIO,
                     NUM_RUC,
                     FCH_EMISION,
                     CASE PORC_IGV_COMP_PAGO WHEN 0 THEN MTO_TOTAL ELSE 0 END MTO_EXONERADO,
                     VAL_IGV_COMP_PAGO MTO_IMPUESTO,
                     MTO_TOTAL,
                     COD_MAQUINA,
                     ITEMS,
                     TIP_CLIEN_CONVENIO,
                     PCT_BENEFICIARIO,
                     IND_AFECTA_KARDEX,
                     COD_MEDICO,
                     SEC_COMP_PAGO,
                     NUM_DOCUMENTO_REF,
                     TIPO_DOCUMENTO_REF,
                     COD_TIPO_VTAFIN,
                     PORC_IGV_COMP_PAGO,
                     FLG_DOC_COPAGO,
                     VAL_COPAGO_COMP_PAGO
                FROM (
                       SELECT (SELECT ML.CIA
                                 FROM NUEVO.AUX_MAE_LOCAL L
                                INNER JOIN NUEVO.MAE_LOCAL ML
                                   ON L.COD_LOCAL = ML.COD_LOCAL
                                WHERE (L.COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null)
                                  AND L.COD_LOCAL_SAP = R.COD_LOCAL) COD_CIA,
                              (SELECT L.COD_LOCAL
                                 FROM NUEVO.AUX_MAE_LOCAL L
                                WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599'*/
                                (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null)
                                  AND COD_LOCAL_SAP = R.COD_LOCAL) COD_LOCAL,
                              (SELECT COD_TIPODOC
                                 FROM  BTLPROD.AUX_TIPO_DOC_CONV
                                WHERE TIP_COMP_PAGO = R.TIP_COMP_PAGO
                                  AND COD_GRUPO_CIA = R.COD_GRUPO_CIA) COD_TIPO_DOCUMENTO,
                              R.NUM_COMP_PAGO NUM_DOCUMENTO,
                              '99999' USU_EMISION, /*(SELECT cod_usuario  from nuevo.mae_usuario_btl a where a.num_doc_identidad =P.DNI_USU_LOCAL)*/
                              NVL(P.COD_CLI_CONV, (SELECT COD_CLIENTE FROM CMR.MAE_CONVENIO WHERE COD_CONVENIO=P.COD_CONVENIO)) COD_CLIENTE,
                              P.COD_CONVENIO,
                              (SELECT NUM_DOCUMENTO_ID
                                 FROM CMR.MAE_CLIENTE
                                WHERE COD_CLIENTE = P.COD_CLI_CONV) NUM_RUC,
                              TO_CHAR(TRUNC(P.FEC_PED_VTA), 'DD/MM/YYYY') FCH_EMISION,
                              (R.VAL_NETO_COMP_PAGO + R.VAL_REDONDEO_COMP_PAGO) MTO_TOTAL,
                              (SELECT VAL_PARAMETRO
                                 FROM NUEVO.MAE_PARAMETRO
                                WHERE COD_PARAMETRO = 'CJE_PORIGV') IGV,
                              R.VAL_IGV_COMP_PAGO,
                              R.PORC_IGV_COMP_PAGO,
                              P.IP_PC COD_MAQUINA,
                              (SELECT COUNT(1)
                                 FROM BTLPROD.RAC_VTA_PEDIDO_VTA_DET
                                WHERE NUM_PED_VTA = R.NUM_PED_VTA
                                  AND COD_GRUPO_CIA = R.COD_GRUPO_CIA
                                  AND COD_LOCAL = R.COD_LOCAL) ITEMS,
                              R.TIP_CLIEN_CONVENIO,
                              R.PCT_BENEFICIARIO,
                              R.IND_AFECTA_KARDEX,
                              (SELECT cod_valor_in
                                 FROM BTLPROD.RAC_CON_BTL_MF_PED_VTA
                                WHERE NUM_PED_VTA = R.NUM_PED_VTA
                                  AND COD_GRUPO_CIA = R.COD_GRUPO_CIA
                                  AND COD_LOCAL = R.COD_LOCAL
                                  AND COD_CAMPO = 'D_005') COD_MEDICO,
                              R.SEC_COMP_PAGO,
                              R.NUM_COMP_COPAGO_REF NUM_DOCUMENTO_REF,
                              (SELECT COD_TIPODOC
                                 FROM BTLPROD.AUX_TIPO_DOC_CONV
                                WHERE TIP_COMP_PAGO = R.TIP_COMP_PAGO_REF
                                  AND COD_GRUPO_CIA = R.COD_GRUPO_CIA) TIPO_DOCUMENTO_REF,
                              -- kmoncada 20.06.2014
                              -- para identificar el tipo de venta institucional
                              CASE
                                  WHEN P.TIP_PED_VTA='03' THEN '1'
                                  ELSE DECODE(R.IND_COMP_CREDITO, 'S', '2', '0') END COD_TIPO_VTAFIN,
                              -- DECODE(R.IND_COMP_CREDITO, 'S', '2', '0') COD_TIPO_VTAFIN,

                              DECODE(R.IND_COMP_CREDITO, 'S', '0', '1') FLG_DOC_COPAGO,
                              NVL(R.VAL_COPAGO_COMP_PAGO, 0) VAL_COPAGO_COMP_PAGO
                         FROM BTLPROD.RAC_VTA_COMP_PAGO R
                        INNER JOIN BTLPROD.RAC_VTA_PEDIDO_VTA_CAB P
                           ON R.NUM_PED_VTA = P.NUM_PED_VTA
                          AND R.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                          AND R.COD_LOCAL = P.COD_LOCAL
                        WHERE R.NUM_PED_VTA = pNum_Ped_Vta_in
                          AND R.COD_GRUPO_CIA = pCodGrupo_Cia_in
                          AND R.COD_LOCAL = pCod_local_in
                     )
              ) T;


    CURSOR C_CABECERA_NC IS
      SELECT T.*,(T.MTO_TOTAL-T.MTO_EXONERADO-T.MTO_IMPUESTO) MTO_BASE_IMP
        FROM (
               SELECT COD_CIA,
                      COD_LOCAL,
                      COD_TIPO_DOCUMENTO,
                      NUM_DOCUMENTO,
                      --NEW_NUM_DOCUMENTO NUM_DOCUMENTO,
                      USU_EMISION,
                      COD_CLIENTE,
                      COD_CONVENIO,
                      NUM_RUC,
                      FCH_EMISION,
                      CASE PORC_IGV_COMP_PAGO WHEN 0 THEN MTO_TOTAL ELSE 0 END MTO_EXONERADO,
                      VAL_IGV_COMP_PAGO MTO_IMPUESTO,
                      MTO_TOTAL,
                      COD_MAQUINA,
                      ITEMS,
                      TIP_CLIEN_CONVENIO,
                      PCT_BENEFICIARIO,
                      IND_AFECTA_KARDEX,
                      COD_MEDICO,
                      SEC_COMP_PAGO,
                      NUM_DOCUMENTO_REF,
                      TIPO_DOCUMENTO_REF,
                      COD_TIPO_VTAFIN,
                      PORC_IGV_COMP_PAGO,
                      FLG_DOC_COPAGO,
                      VAL_COPAGO_COMP_PAGO,
                      NUM_PED_VTA_ORIGEN
                 FROM (
                        SELECT (SELECT ML.CIA
                                  FROM NUEVO.AUX_MAE_LOCAL L
                                 INNER JOIN NUEVO.MAE_LOCAL ML
                                    ON L.COD_LOCAL = ML.COD_LOCAL
                                 WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599'*/
                                 (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null)
                                   AND COD_LOCAL_SAP = R.COD_LOCAL) COD_CIA,
                               (SELECT L.Cod_Local
                                  FROM NUEVO.AUX_MAE_LOCAL l
                                 WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599'*/
                                 (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null)
                                   AND COD_LOCAL_SAP = R.COD_LOCAL) COD_LOCAL,
                               (SELECT COD_TIPODOC
                                  FROM  BTLPROD.AUX_TIPO_DOC_CONV
                                 WHERE TIP_COMP_PAGO = CP.TIP_COMP_PAGO
                                   AND COD_GRUPO_CIA = CP.COD_GRUPO_CIA) COD_TIPO_DOCUMENTO,
                               CP.NUM_COMP_PAGO NUM_DOCUMENTO,
                               SUBSTR(CP.NUM_COMP_PAGO,1,3)||LPAD(ROWNUM,2,'0')||SUBSTR(CP.NUM_COMP_PAGO,6,5)  NEW_NUM_DOCUMENTO,
                               '99999' USU_EMISION,/*(SELECT cod_usuario  from nuevo.mae_usuario_btl a where a.num_doc_identidad =P.DNI_USU_LOCAL)*/
                               NVL(P.COD_CLI_CONV, (SELECT COD_CLIENTE FROM CMR.MAE_CONVENIO WHERE COD_CONVENIO = P.COD_CONVENIO)) COD_CLIENTE,
                               P.COD_CONVENIO,
                               (SELECT NUM_DOCUMENTO_ID
                                  FROM CMR.MAE_CLIENTE
                                 WHERE COD_CLIENTE = P.COD_CLI_CONV) NUM_RUC,
                               TO_CHAR(TRUNC(P.FEC_PED_VTA),'DD/MM/YYYY') FCH_EMISION,
                               (R.VAL_NETO_COMP_PAGO + R.VAL_REDONDEO_COMP_PAGO) MTO_TOTAL,
                               (SELECT VAL_PARAMETRO
                                  FROM NUEVO.MAE_PARAMETRO
                                 WHERE COD_PARAMETRO = 'CJE_PORIGV') IGV,
                               R.VAL_IGV_COMP_PAGO,
                               R.PORC_IGV_COMP_PAGO,
                               P.IP_PC COD_MAQUINA,
                               (SELECT COUNT(1)
                                  FROM BTLPROD.RAC_VTA_PEDIDO_VTA_DET
                                 WHERE NUM_PED_VTA = R.NUM_PED_VTA
                                   AND COD_GRUPO_CIA = R.COD_GRUPO_CIA
                                   AND COD_LOCAL = R.COD_LOCAL) ITEMS,
                               R.TIP_CLIEN_CONVENIO,
                               R.PCT_BENEFICIARIO,
                               R.IND_AFECTA_KARDEX,
                               (SELECT cod_valor_in
                                  FROM BTLPROD.RAC_CON_BTL_MF_PED_VTA
                                 WHERE NUM_PED_VTA = R.NUM_PED_VTA
                                   AND COD_GRUPO_CIA = R.COD_GRUPO_CIA
                                   AND COD_LOCAL = R.COD_LOCAL
                                   AND COD_CAMPO = 'D_005') COD_MEDICO,
                               CP.SEC_COMP_PAGO,
                               R.NUM_COMP_PAGO NUM_DOCUMENTO_REF,
                               (SELECT COD_TIPODOC
                                  FROM BTLPROD.AUX_TIPO_DOC_CONV
                                 WHERE TIP_COMP_PAGO = R.TIP_COMP_PAGO
                                   AND COD_GRUPO_CIA = R.COD_GRUPO_CIA) TIPO_DOCUMENTO_REF,
                                   -- kmoncada 20.06.2014
                              -- para identificar el tipo de venta institucional
                              CASE
                                  WHEN P.TIP_PED_VTA='03' THEN '1'
                                  ELSE DECODE(R.IND_COMP_CREDITO, 'S', '2', '0') END COD_TIPO_VTAFIN,
--                               DECODE(R.IND_COMP_CREDITO, 'S', '2', '0') COD_TIPO_VTAFIN,
                               DECODE(R.IND_COMP_CREDITO, 'S', '0', '1') FLG_DOC_COPAGO,
                               0 VAL_COPAGO_COMP_PAGO,
                               P.NUM_PED_VTA_ORIGEN
                          FROM BTLPROD.RAC_VTA_COMP_PAGO R
                         INNER JOIN BTLPROD.RAC_VTA_PEDIDO_VTA_CAB P
                            ON R.NUM_PED_VTA = P.NUM_PED_VTA_ORIGEN
                           AND R.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                           AND R.COD_LOCAL = P.COD_LOCAL
                           /* -- DJARA: Asociar NCR al documento origen -- */
                           and r.sec_comp_pago =
                               (select max(sec_comp_pago_origen)
                                  from btlprod.rac_vta_pedido_vta_det d
                                 where d.num_ped_vta = p.num_ped_vta
                                   and d.cod_grupo_cia = p.cod_grupo_cia
                                   and d.cod_local = p.cod_local)
                           /* -- DJARA: FIN Asociar NCR al documento origen -- */
                         INNER JOIN BTLPROD.RAC_VTA_COMP_PAGO CP
                            ON CP.NUM_PED_VTA = P.NUM_PED_VTA
                           AND CP.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                           AND CP.COD_LOCAL = P.COD_LOCAL
                         WHERE P.NUM_PED_VTA = pNum_Ped_Vta_in
                           AND P.COD_GRUPO_CIA = pCodGrupo_Cia_in
                           AND P.COD_LOCAL = pCod_local_in
                           AND R.TIP_COMP_PAGO <> '03'
                       )
              ) T;

          --Cargo el cursor con el detalle de los documento de ventas
          CURSOR C_DETALLE (A_COD_CIA_DOC CHAR, A_COD_TIPO_DOC CHAR,A_NUM_DOCUMENTO_DOC CHAR) IS
            SELECT * FROM
                         (
                          SELECT
                                (SELECT ML.CIA FROM NUEVO.AUX_MAE_LOCAL L INNER JOIN NUEVO.Mae_Local ML ON L.COD_LOCAL=ML.COD_LOCAL
                                 WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599' */
                                 (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null)
                                 AND COD_LOCAL_SAP = CP.COD_LOCAL) COD_CIA,
                                (SELECT L.Cod_Local FROM NUEVO.AUX_MAE_LOCAL l WHERE
                                (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null)
                                /*COD_LOCAL_SAP BETWEEN '001' AND '599'*/ AND COD_LOCAL_SAP = CP.COD_LOCAL) COD_LOCAL,
                                (SELECT COD_TIPODOC FROM  BTLPROD.AUX_TIPO_DOC_CONV WHERE TIP_COMP_PAGO=CP.TIP_COMP_PAGO AND COD_GRUPO_CIA=CP.COD_GRUPO_CIA) COD_TIPO_DOCUMENTO,
                                CP.NUM_COMP_PAGO NUM_DOCUMENTO,
                                P.COD_PRODUCTO,
        D.CANT_ATENDIDA CTD_PRODUCTO,
        --D.CANT_ATENDIDA * (L.VAL_MAX_FRAC / D.VAL_FRAC) CTD_PRODUCTO,

        D.VAL_FRAC CTD_FRACCIONA,
        DECODE(D.VAL_FRAC,'1','0','1') FLG_FRACCION,
        --(D.VAL_PREC_VTA*((CP.PCT_BENEFICIARIO)/100)) PRC_UNITARIO,
                                (D.VAL_PREC_VTA*((DECODE(CP.IND_COMP_CREDITO,'S',DECODE(CP.PCT_BENEFICIARIO,0,100,CP.PCT_BENEFICIARIO),CP.PCT_BENEFICIARIO))/100)) PRC_UNITARIO,
        --(D.VAL_PREC_TOTAL*((CP.PCT_BENEFICIARIO)/100)) MTO_SUBTOTAL,
        (D.VAL_PREC_TOTAL*(DECODE(CP.IND_COMP_CREDITO,'S',DECODE(CP.PCT_BENEFICIARIO,0,100,CP.PCT_BENEFICIARIO),CP.PCT_BENEFICIARIO)/100)) MTO_SUBTOTAL,
                                nvl(D.PORC_DCTO_CALC,0) PCT_DESCUENTO,
                                D.VAL_IGV/100 PCT_IGV,
                                --ROUND((((D.VAL_PREC_TOTAL*((CP.PCT_BENEFICIARIO)/100))/(1+(D.VAL_IGV/100)))*(D.VAL_IGV/100)),2) MTO_IGV,
                                ROUND((((D.VAL_PREC_TOTAL*(DECODE(CP.IND_COMP_CREDITO,'S',DECODE(CP.PCT_BENEFICIARIO,0,100,CP.PCT_BENEFICIARIO),CP.PCT_BENEFICIARIO)/100))/(1+(D.VAL_IGV/100)))*(D.VAL_IGV/100)),2) MTO_IGV,
                                D.SEC_PED_VTA_DET NUM_ITEM,
                                PC.DES_PRODUCTO,
                                NVL((SELECT NVL((PD.VAL_PREC_TOTAL*(PG.PCT_EMPRESA/100)),0)
                                FROM BTLPROD.RAC_VTA_COMP_PAGO PG
        INNER JOIN BTLPROD.RAC_VTA_PEDIDO_VTA_DET PD
                                ON PG.COD_GRUPO_CIA=PD.COD_GRUPO_CIA AND PG.COD_LOCAL=PD.COD_LOCAL AND PG.NUM_PED_VTA=PD.NUM_PED_VTA
                                WHERE PG.COD_GRUPO_CIA=D.COD_GRUPO_CIA AND  PG.COD_LOCAL=D.COD_LOCAL AND PG.NUM_PED_VTA=D.NUM_PED_VTA
                                AND PG.TIP_COMP_PAGO=CP.TIP_COMP_PAGO_REF AND PG.NUM_COMP_PAGO=CP.NUM_COMP_COPAGO_REF AND PD.COD_PROD=D.COD_PROD and pd.sec_ped_vta_det=d.sec_ped_vta_det),0) IMP_COPAGO,
                                L.COD_PROD PROD_APPS,
                                D.COD_PROD PROD_DET,
                                D.CANT_ATENDIDA,
                                D.VAL_FRAC
                          FROM BTLPROD.RAC_VTA_PEDIDO_VTA_DET D INNER JOIN CMR.AUX_MAE_PRODUCTO_COM P
                          ON D.COD_PROD=P.COD_CODIGO_SAP INNER JOIN BTLPROD.RAC_VTA_COMP_PAGO CP
                          ON CP.COD_GRUPO_CIA=D.COD_GRUPO_CIA AND CP.COD_LOCAL=D.COD_LOCAL AND CP.NUM_PED_VTA=D.NUM_PED_VTA
                          AND D.SEC_COMP_PAGO_BENEF=CP.SEC_COMP_PAGO
                          LEFT JOIN APPS.LGT_PROD L ON L.COD_PROD=D.COD_PROD INNER JOIN CMR.MAE_PRODUCTO_COM PC
                          ON PC.COD_PRODUCTO=P.COD_PRODUCTO
                          WHERE CP.TIP_CLIEN_CONVENIO=1                    --DOCUMENTO DE BENEFICIARIO
                          AND D.NUM_PED_VTA = pNum_Ped_Vta_in
                          AND d.COD_GRUPO_CIA = pCodGrupo_Cia_in
                          AND D.COD_LOCAL = pCod_local_in

                          UNION ALL

        SELECT (SELECT ML.CIA
          FROM NUEVO.AUX_MAE_LOCAL L
        INNER JOIN NUEVO.Mae_Local ML
        ON L.COD_LOCAL=ML.COD_LOCAL
                                 WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599' */
                                 (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null)
                                 AND COD_LOCAL_SAP = CP.COD_LOCAL) COD_CIA,
                                (SELECT L.Cod_Local FROM NUEVO.AUX_MAE_LOCAL l WHERE
                                (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null)
                                /*COD_LOCAL_SAP BETWEEN '001' AND '599'*/ AND COD_LOCAL_SAP = CP.COD_LOCAL) COD_LOCAL,
                                (SELECT COD_TIPODOC FROM  BTLPROD.AUX_TIPO_DOC_CONV WHERE TIP_COMP_PAGO=CP.TIP_COMP_PAGO AND COD_GRUPO_CIA=CP.COD_GRUPO_CIA) COD_TIPO_DOCUMENTO,
                                CP.NUM_COMP_PAGO NUM_DOCUMENTO,
                                P.COD_PRODUCTO,
                                D.CANT_ATENDIDA CTD_PRODUCTO,
                                --D.CANT_ATENDIDA * (L.VAL_MAX_FRAC / D.VAL_FRAC) CTD_PRODUCTO,
                                D.VAL_FRAC CTD_FRACCIONA,
                                DECODE(D.VAL_FRAC,'1','0','1') FLG_FRACCION,
        (D.VAL_PREC_VTA*(CP.PCT_EMPRESA/100)) PRC_UNITARIO,
        (D.VAL_PREC_TOTAL*(CP.PCT_EMPRESA/100)) MTO_SUBTOTAL,
        nvl(D.PORC_DCTO_CALC, 0) PCT_DESCUENTO,
                                D.VAL_IGV/100 PCT_IGV,
                                ROUND((((D.VAL_PREC_TOTAL*((CP.PCT_EMPRESA)/100))/(1+(D.VAL_IGV/100)))*(D.VAL_IGV/100)),2) MTO_IGV,
                                D.SEC_PED_VTA_DET NUM_ITEM,
                                PC.DES_PRODUCTO,
                                nvl((SELECT NVL((PD.VAL_PREC_TOTAL*(DECODE(PG.IND_COMP_CREDITO,'S',DECODE(PG.PCT_BENEFICIARIO,0,100,PG.PCT_BENEFICIARIO),PG.PCT_BENEFICIARIO)/100)),0)
                                FROM BTLPROD.RAC_VTA_COMP_PAGO PG INNER JOIN BTLPROD.RAC_VTA_PEDIDO_VTA_DET PD
                                ON PG.COD_GRUPO_CIA=PD.COD_GRUPO_CIA AND PG.COD_LOCAL=PD.COD_LOCAL AND PG.NUM_PED_VTA=PD.NUM_PED_VTA
                                WHERE PG.COD_GRUPO_CIA=D.COD_GRUPO_CIA AND  PG.COD_LOCAL=D.COD_LOCAL AND PG.NUM_PED_VTA=D.NUM_PED_VTA
                                AND PG.TIP_COMP_PAGO=CP.TIP_COMP_PAGO_REF AND PG.NUM_COMP_PAGO=CP.NUM_COMP_COPAGO_REF AND PD.COD_PROD=D.COD_PROD and pd.sec_ped_vta_det=d.sec_ped_vta_det ),0) IMP_COPAGO,
                                L.COD_PROD PROD_APPS,
                                D.COD_PROD PROD_DET,
                                D.CANT_ATENDIDA,
                                D.VAL_FRAC
                          FROM BTLPROD.RAC_VTA_PEDIDO_VTA_DET D INNER JOIN CMR.AUX_MAE_PRODUCTO_COM P
                          ON D.COD_PROD=P.COD_CODIGO_SAP INNER JOIN BTLPROD.RAC_VTA_COMP_PAGO CP
                          ON CP.COD_GRUPO_CIA=D.COD_GRUPO_CIA AND CP.COD_LOCAL=D.COD_LOCAL AND CP.NUM_PED_VTA=D.NUM_PED_VTA
                          AND D.SEC_COMP_PAGO_EMPRE=CP.SEC_COMP_PAGO
                          LEFT JOIN APPS.LGT_PROD L ON L.COD_PROD=D.COD_PROD INNER JOIN CMR.MAE_PRODUCTO_COM PC
                          ON PC.COD_PRODUCTO=P.COD_PRODUCTO
                          WHERE CP.TIP_CLIEN_CONVENIO=2                     --DOCUMENTO DE EMPRESA
                          AND D.NUM_PED_VTA = pNum_Ped_Vta_in
                          AND d.COD_GRUPO_CIA = pCodGrupo_Cia_in
                          AND D.COD_LOCAL = pCod_local_in
                          ) WHERE COD_CIA=A_COD_CIA_DOC AND COD_TIPO_DOCUMENTO=A_COD_TIPO_DOC AND NUM_DOCUMENTO=A_NUM_DOCUMENTO_DOC
            ORDER BY COD_CIA,COD_LOCAL,COD_TIPO_DOCUMENTO,NUM_DOCUMENTO;




           CURSOR C_DETALLE_NC (A_NUM_PED_VTA_ORIGEN CHAR,A_COD_CIA_DOC CHAR, A_COD_TIPO_DOC CHAR,A_NUM_DOCUMENTO_DOC CHAR) IS

               SELECT * FROM
                         (
                          SELECT
                                (SELECT ML.CIA FROM NUEVO.AUX_MAE_LOCAL L INNER JOIN NUEVO.Mae_Local ML ON L.COD_LOCAL=ML.COD_LOCAL
                                 WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599' */
                                 (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null) AND COD_LOCAL_SAP = CP.COD_LOCAL) COD_CIA,
                                (SELECT L.Cod_Local FROM NUEVO.AUX_MAE_LOCAL l WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599'*/
                                (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null) AND COD_LOCAL_SAP = CP.COD_LOCAL) COD_LOCAL,
                                (SELECT COD_TIPODOC FROM  BTLPROD.AUX_TIPO_DOC_CONV WHERE TIP_COMP_PAGO=CP.TIP_COMP_PAGO AND COD_GRUPO_CIA=CP.COD_GRUPO_CIA) COD_TIPO_DOCUMENTO,
                                CP.NUM_COMP_PAGO NUM_DOCUMENTO,
                                P.COD_PRODUCTO,
                                D.CANT_ATENDIDA CTD_PRODUCTO,
                                --D.CANT_ATENDIDA * (L.VAL_MAX_FRAC / D.VAL_FRAC) CTD_PRODUCTO,
                                D.VAL_FRAC CTD_FRACCIONA,
                                DECODE(D.VAL_FRAC,'1','0','1') FLG_FRACCION,
                                --(D.VAL_PREC_VTA*((CP.PCT_BENEFICIARIO)/100)) PRC_UNITARIO,
                                (D.VAL_PREC_VTA*((DECODE(CP.IND_COMP_CREDITO,'S',DECODE(CP.PCT_BENEFICIARIO,0,100,CP.PCT_BENEFICIARIO),CP.PCT_BENEFICIARIO))/100)) PRC_UNITARIO,
                                --(D.VAL_PREC_TOTAL*((CP.PCT_BENEFICIARIO)/100)) MTO_SUBTOTAL,
                                (D.VAL_PREC_TOTAL*(DECODE(CP.IND_COMP_CREDITO,'S',DECODE(CP.PCT_BENEFICIARIO,0,100,CP.PCT_BENEFICIARIO),CP.PCT_BENEFICIARIO)/100)) MTO_SUBTOTAL,
                                nvl(D.PORC_DCTO_CALC, 0) PCT_DESCUENTO,
                                D.VAL_IGV/100 PCT_IGV,
                                --ROUND((((D.VAL_PREC_TOTAL*((CP.PCT_BENEFICIARIO)/100))/(1+(D.VAL_IGV/100)))*(D.VAL_IGV/100)),2) MTO_IGV,
                                ROUND((((D.VAL_PREC_TOTAL*(DECODE(CP.IND_COMP_CREDITO,'S',DECODE(CP.PCT_BENEFICIARIO,0,100,CP.PCT_BENEFICIARIO),CP.PCT_BENEFICIARIO)/100))/(1+(D.VAL_IGV/100)))*(D.VAL_IGV/100)),2) MTO_IGV,
                                D.SEC_PED_VTA_DET NUM_ITEM,
                                PC.DES_PRODUCTO,
                                 NVL((SELECT NVL((PD.VAL_PREC_TOTAL*(PG.PCT_EMPRESA/100)),0)
                                 FROM BTLPROD.RAC_VTA_COMP_PAGO PG INNER JOIN BTLPROD.RAC_VTA_PEDIDO_VTA_DET PD
                                 ON PG.COD_GRUPO_CIA=PD.COD_GRUPO_CIA AND PG.COD_LOCAL=PD.COD_LOCAL AND PG.NUM_PED_VTA=PD.NUM_PED_VTA
                                 WHERE PG.COD_GRUPO_CIA=D.COD_GRUPO_CIA AND  PG.COD_LOCAL=D.COD_LOCAL AND PG.NUM_PED_VTA=D.NUM_PED_VTA
                                 AND PG.TIP_COMP_PAGO=CP.TIP_COMP_PAGO_REF AND PG.NUM_COMP_PAGO=CP.NUM_COMP_COPAGO_REF AND PD.COD_PROD=D.COD_PROD
                                 and pd.sec_ped_vta_det=d.sec_ped_vta_det -- KMONCADA
                                 ),0) IMP_COPAGO
                          FROM BTLPROD.RAC_VTA_PEDIDO_VTA_DET D INNER JOIN CMR.AUX_MAE_PRODUCTO_COM P
                          ON D.COD_PROD=P.COD_CODIGO_SAP INNER JOIN BTLPROD.RAC_VTA_COMP_PAGO CP
                          ON CP.COD_GRUPO_CIA=D.COD_GRUPO_CIA AND CP.COD_LOCAL=D.COD_LOCAL AND CP.NUM_PED_VTA=D.NUM_PED_VTA
                          AND D.SEC_COMP_PAGO_BENEF=CP.SEC_COMP_PAGO
                          INNER JOIN APPS.LGT_PROD L ON L.COD_PROD=D.COD_PROD INNER JOIN CMR.MAE_PRODUCTO_COM PC
                          ON PC.COD_PRODUCTO=P.COD_PRODUCTO
                          WHERE CP.TIP_CLIEN_CONVENIO=1                    --DOCUMENTO DE BENEFICIARIO
                          AND D.NUM_PED_VTA = A_NUM_PED_VTA_ORIGEN
                          AND d.COD_GRUPO_CIA = pCodGrupo_Cia_in
                          AND D.COD_LOCAL = pCod_local_in

                          UNION ALL

                          SELECT
                                (SELECT ML.CIA FROM NUEVO.AUX_MAE_LOCAL L INNER JOIN NUEVO.Mae_Local ML ON L.COD_LOCAL=ML.COD_LOCAL
                                 WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599' */
                                 (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null)
                                 AND COD_LOCAL_SAP = CP.COD_LOCAL) COD_CIA,
                                (SELECT L.Cod_Local FROM NUEVO.AUX_MAE_LOCAL l WHERE COD_LOCAL_SAP BETWEEN '001' AND '599' AND COD_LOCAL_SAP = CP.COD_LOCAL) COD_LOCAL,
                                (SELECT COD_TIPODOC FROM  BTLPROD.AUX_TIPO_DOC_CONV WHERE TIP_COMP_PAGO=CP.TIP_COMP_PAGO AND COD_GRUPO_CIA=CP.COD_GRUPO_CIA) COD_TIPO_DOCUMENTO,
                                CP.NUM_COMP_PAGO NUM_DOCUMENTO,
                                P.COD_PRODUCTO,
                                D.CANT_ATENDIDA CTD_PRODUCTO,
                                --D.CANT_ATENDIDA * (L.VAL_MAX_FRAC / D.VAL_FRAC) CTD_PRODUCTO,
                                D.VAL_FRAC CTD_FRACCIONA,
                                DECODE(D.VAL_FRAC,'1','0','1') FLG_FRACCION,
                                (D.VAL_PREC_VTA*(CP.PCT_EMPRESA/100)) PRC_UNITARIO,
                                (D.VAL_PREC_TOTAL*(CP.PCT_EMPRESA/100)) MTO_SUBTOTAL,
                                nvl(D.PORC_DCTO_CALC,0) PCT_DESCUENTO,
                                D.VAL_IGV/100 PCT_IGV,
                                ROUND((((D.VAL_PREC_TOTAL*((CP.PCT_EMPRESA)/100))/(1+(D.VAL_IGV/100)))*(D.VAL_IGV/100)),2) MTO_IGV,
                                D.SEC_PED_VTA_DET NUM_ITEM,
                                PC.DES_PRODUCTO,
                                nvl((SELECT NVL((PD.VAL_PREC_TOTAL*(DECODE(PG.IND_COMP_CREDITO,'S',DECODE(PG.PCT_BENEFICIARIO,0,100,PG.PCT_BENEFICIARIO),PG.PCT_BENEFICIARIO)/100)),0)
                                 FROM BTLPROD.RAC_VTA_COMP_PAGO PG INNER JOIN BTLPROD.RAC_VTA_PEDIDO_VTA_DET PD
                                 ON PG.COD_GRUPO_CIA=PD.COD_GRUPO_CIA AND PG.COD_LOCAL=PD.COD_LOCAL AND PG.NUM_PED_VTA=PD.NUM_PED_VTA
                                 WHERE PG.COD_GRUPO_CIA=D.COD_GRUPO_CIA AND  PG.COD_LOCAL=D.COD_LOCAL AND PG.NUM_PED_VTA=D.NUM_PED_VTA
                                 AND PG.TIP_COMP_PAGO=CP.TIP_COMP_PAGO_REF AND PG.NUM_COMP_PAGO=CP.NUM_COMP_COPAGO_REF AND PD.COD_PROD=D.COD_PROD
                                 and pd.sec_ped_vta_det=d.sec_ped_vta_det -- KMONCADA
                                 ),0) IMP_COPAGO
                          FROM BTLPROD.RAC_VTA_PEDIDO_VTA_DET D INNER JOIN CMR.AUX_MAE_PRODUCTO_COM P
                          ON D.COD_PROD=P.COD_CODIGO_SAP INNER JOIN BTLPROD.RAC_VTA_COMP_PAGO CP
                          ON CP.COD_GRUPO_CIA=D.COD_GRUPO_CIA AND CP.COD_LOCAL=D.COD_LOCAL AND CP.NUM_PED_VTA=D.NUM_PED_VTA
                          AND D.SEC_COMP_PAGO_EMPRE=CP.SEC_COMP_PAGO
                          INNER JOIN APPS.LGT_PROD L ON L.COD_PROD=D.COD_PROD INNER JOIN CMR.MAE_PRODUCTO_COM PC
                          ON PC.COD_PRODUCTO=P.COD_PRODUCTO
                          WHERE CP.TIP_CLIEN_CONVENIO=2                     --DOCUMENTO DE EMPRESA
                          AND D.NUM_PED_VTA = A_NUM_PED_VTA_ORIGEN
                          AND d.COD_GRUPO_CIA =pCodGrupo_Cia_in
                          AND D.COD_LOCAL = pCod_local_in
                          ) WHERE COD_CIA=A_COD_CIA_DOC AND COD_TIPO_DOCUMENTO=A_COD_TIPO_DOC AND NUM_DOCUMENTO=A_NUM_DOCUMENTO_DOC
                          ORDER BY COD_CIA,COD_LOCAL,COD_TIPO_DOCUMENTO,NUM_DOCUMENTO;


          CURSOR C_DETALLE_REGULAR (A_COD_CIA_DOC CHAR, A_COD_TIPO_DOC CHAR,A_NUM_DOCUMENTO_DOC CHAR) IS
          SELECT * FROM
                       (
                        SELECT
                              (SELECT ML.CIA FROM NUEVO.AUX_MAE_LOCAL L INNER JOIN NUEVO.Mae_Local ML ON L.COD_LOCAL=ML.COD_LOCAL
                               WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599' */
                               (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null) AND COD_LOCAL_SAP = CP.COD_LOCAL) COD_CIA,
                              (SELECT L.Cod_Local FROM NUEVO.AUX_MAE_LOCAL l WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599' */
                              (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null) AND COD_LOCAL_SAP = CP.COD_LOCAL) COD_LOCAL,
                              (SELECT COD_TIPODOC FROM  BTLPROD.AUX_TIPO_DOC_CONV WHERE TIP_COMP_PAGO=CP.TIP_COMP_PAGO AND COD_GRUPO_CIA=CP.COD_GRUPO_CIA) COD_TIPO_DOCUMENTO,
                              CP.NUM_COMP_PAGO NUM_DOCUMENTO,
                              P.COD_PRODUCTO,
                              D.CANT_ATENDIDA CTD_PRODUCTO,
                              --D.CANT_ATENDIDA * (L.VAL_MAX_FRAC / D.VAL_FRAC) CTD_PRODUCTO,
                              D.VAL_FRAC CTD_FRACCIONA,
                              DECODE(D.VAL_FRAC,'1','0','1') FLG_FRACCION,
                              D.VAL_PREC_VTA PRC_UNITARIO,
                              D.VAL_PREC_TOTAL MTO_SUBTOTAL,
                              nvl(D.PORC_DCTO_CALC,0) PCT_DESCUENTO,
                              D.VAL_IGV/100 PCT_IGV,
                              ROUND((D.VAL_PREC_TOTAL/(1+(D.VAL_IGV/100))),2) MTO_IGV,
                              D.SEC_PED_VTA_DET NUM_ITEM,
                              PC.DES_PRODUCTO
                        FROM BTLPROD.RAC_VTA_PEDIDO_VTA_DET D INNER JOIN CMR.AUX_MAE_PRODUCTO_COM P
                        ON D.COD_PROD=P.COD_CODIGO_SAP INNER JOIN BTLPROD.RAC_VTA_COMP_PAGO CP
                        ON CP.COD_GRUPO_CIA=D.COD_GRUPO_CIA AND CP.COD_LOCAL=D.COD_LOCAL AND CP.NUM_PED_VTA=D.NUM_PED_VTA
                        INNER JOIN APPS.LGT_PROD L ON L.COD_PROD=D.COD_PROD INNER JOIN CMR.MAE_PRODUCTO_COM PC
                        ON PC.COD_PRODUCTO=P.COD_PRODUCTO
                        WHERE D.NUM_PED_VTA = pNum_Ped_Vta_in--'0000231560'
                        AND d.COD_GRUPO_CIA = pCodGrupo_Cia_in--'001'
                        AND D.COD_LOCAL = pCod_local_in--'071'
                        ) WHERE COD_CIA=A_COD_CIA_DOC AND COD_TIPO_DOCUMENTO=A_COD_TIPO_DOC AND NUM_DOCUMENTO=A_NUM_DOCUMENTO_DOC
          ORDER BY COD_CIA,COD_LOCAL,COD_TIPO_DOCUMENTO,NUM_DOCUMENTO;

          ------------------------------------------------------------------------------------

          C_COD_MODALIDAD        CHAR(3);
          C_COD_LOCAL            BTLPROD.CAB_DOCUMENTO.COD_LOCAL%TYPE;
          V_COD_CLIENTE          CMR.MAE_CLIENTE.COD_CLIENTE%TYPE;
          V_NUM_RUC_EMPRESA      CMR.MAE_CLIENTE.NUM_DOCUMENTO_ID%TYPE;
          V_NUM_RUC_EMPRESA_FIN  CMR.MAE_CLIENTE.NUM_DOCUMENTO_ID%TYPE;
          V_NOM_CLIENTE          CMR.MAE_CLIENTE.DES_CLIENTE%TYPE;
          V_NOM_CLIENTE_FIN      CMR.MAE_CLIENTE.DES_CLIENTE%TYPE;
          V_NOM_CLIENTE_CONV     CMR.MAE_CLIENTE.DES_CLIENTE%TYPE;
          V_COD_CONVENIO         CMR.MAE_CONVENIO.COD_CONVENIO%TYPE;
          V_COD_EMPRESA          CMR.MAE_CONVENIO.COD_CLIENTE%TYPE;
          -- V_DIR_CLI_EMPRESA      BTLPROD.CAB_DOCUMENTO.DES_AUX_CLI_DIRECC%TYPE;
          V_DIR_CLI_EMPRESA      CMR.MAE_CLIENTE.DES_DIRECCION_SOCIAL%TYPE; -- KMONCADA 11.08.2014
          V_COD_EMPRESA_FIN      CMR.MAE_CONVENIO.COD_CLIENTE%TYPE;
          C_COD_TIPO_VENTA       BTLPROD.CAB_DOCUMENTO.COD_TIPO_VENTA%TYPE;
          C_SEQ_VENTA            FLOAT;
          vRspta char(1);
          XX_sec_caja            btlprod.aux_control_caja.sec_caja%type;
          XX_cod_liquidacion     btlprod.aux_control_caja.cod_liquidacion%type;
          C_NUM_RECETA           BTLPROD.rac_con_btl_mf_ped_vta.descripcion_campo%type;
          C_FCH_RECETA           BTLPROD.rac_con_btl_mf_ped_vta.descripcion_campo%type;
          C_COD_LOCAL_RECETA     BTLPROD.rac_con_btl_mf_ped_vta.descripcion_campo%type;
          C_COD_MEDICO           BTLPROD.rac_con_btl_mf_ped_vta.cod_valor_in%TYPE;
          C_COD_REPARTIDOR       BTLPROD.rac_con_btl_mf_ped_vta.cod_valor_in%TYPE;
          A_CAD_COD_DIAGNOSTICO  VARCHAR2(500);
          V_FLG_RECETA           CMR.MAE_CONVENIO.FLG_RECETA%TYPE;
          V_FLG_REPARTIDOR       CMR.MAE_CONVENIO.FLG_REPARTIDOR%TYPE;
          V_FLG_DOC_COPAGO       CHAR(1);
          V_COD_REPARTIDOR       BTLPROD.CAB_DOCUMENTO.COD_REPARTIDOR%TYPE;
          V_CODIGO_CONV          BTLPROD.RAC_VTA_PEDIDO_VTA_CAB.COD_CONVENIO%TYPE;
          V_IND_CONVENIO         BTLPROD.RAC_VTA_PEDIDO_VTA_CAB.IND_CONV_BTL_MF%TYPE;
          V_PCT_BENEFICIARIO     BTLPROD.CAB_DOCUMENTO.PCT_BENEFICIARIO%TYPE;
          CONTADOR_DATOS         INTEGER;
          V_COD_CLIENTE_SAP      CMR.MAE_CLIENTE.COD_CLIENTE_SAP%type;
          V_TIP_COMP             BTLPROD.RAC_VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE;
          V_CANT_PRODUCTO        BTLPROD.DET_DOCUMENTO.CTD_PRODUCTO%TYPE;
          V_CANT_FRACCION        BTLPROD.DET_DOCUMENTO.CTD_FRACCIONAMIENTO%TYPE;
          v_count_guia           integer;
          v_guia_existe          char(1) default 'N';
          v_num_guia             btlcero.cab_guia_cliente.num_guia%type;



          V_COD_MODALIDAD        btlprod.cab_documento.cod_modalidad_venta%type;

		MSG_ERRM VARCHAR2(500);
BEGIN
  BEGIN
--raise_application_erro(-20000,'x');
    --ERIOS 2.4.4 Graba pedido convenio directo
    IF pIndGrabaPed = 'N' THEN
     vRspta := SP_GRABA_TABLAS_TMP_RAC( pCodGrupo_Cia_in,
                                        pCod_local_in,
                                        pNum_Ped_Vta_in,
                                        pIndNotaCred);

     IF vRspta ='N' then
        RAISE_APPLICATION_ERROR(-20050,'Error al grabar tablas de Matriz a RAC');
     END IF;

     IF vRspta ='X' then
        goto fin_transaccion;
     END IF;
    END IF;

     BEGIN
        --SELECT L.Cod_Local INTO C_COD_LOCAL
        --  FROM NUEVO.AUX_MAE_LOCAL l
        -- WHERE COD_LOCAL_SAP BETWEEN '001' AND '599'
        --   AND COD_LOCAL_SAP = pCod_local_in;
		--ERIOS 26.08.2014 No duplica el resultado
    SELECT COD_LOCAL
      INTO C_COD_LOCAL
    from (
      SELECT L.Cod_Local
        FROM NUEVO.AUX_MAE_LOCAL l
       WHERE COD_LOCAL_SAP BETWEEN '001' AND '599'
         AND COD_LOCAL_SAP = pCod_local_in
      Union /*all*/
      SELECT L.COD_LOCAL
      FROM NUEVO.AUX_mae_LOCAL L
      WHERE COD_LOCAL_SAP = pCod_local_in
      and not fch_migracion is null
    );
     EXCEPTION
        WHEN NO_DATA_FOUND THEN
           RAISE_APPLICATION_ERROR(-20000,'No se ha encontrado el local: '||pCod_local_in );
     END;


     sp_crea_cajas_liquidaciones(pCodGrupo_Cia_in,
                                 pCod_local_in,
                                 pNum_Ped_Vta_in);

     BEGIN
         SELECT COD_CONVENIO, IND_CONV_BTL_MF
           INTO V_CODIGO_CONV, V_IND_CONVENIO
           FROM BTLPROD.RAC_VTA_PEDIDO_VTA_CAB C
          WHERE C.NUM_PED_VTA= pNum_Ped_Vta_in
            AND C.COD_GRUPO_CIA =pCodGrupo_Cia_in
            AND C.COD_LOCAL =pCod_local_in
          group by COD_CONVENIO, IND_CONV_BTL_MF;
     EXCEPTION
        WHEN OTHERS THEN
           RAISE_APPLICATION_ERROR(-20000,CHR(13)||'No se ha Insertado el Pedido al RAC'|| sqlerrm || CHR(13) ||
                                        pNum_Ped_Vta_in || CHR(13) ||
                                        pCodGrupo_Cia_in || CHR(13) ||
                                        pCod_local_in ) ;
     END;

     IF V_CODIGO_CONV IS NOT NULL AND V_IND_CONVENIO = 'S' THEN
        C_COD_MODALIDAD := BTLPROD.PKG_CONSTANTES.CONS_MODAL_VTA_002_CONVEMP;
     ELSE
        C_COD_MODALIDAD := BTLPROD.PKG_CONSTANTES.CONS_MODAL_VTA_001_REGULAR;
     END IF;

     --C_COD_MODALIDAD := BTLPROD.PKG_CONSTANTES.CONS_MODAL_VTA_002_CONVEMP;
     C_COD_TIPO_VENTA := BTLPROD.PKG_CONSTANTES.CONS_TIPO_VENTA_LOCAL;
     C_SEQ_VENTA      := '';

     SELECT TIP_COMP_PAGO INTO V_TIP_COMP
       FROM BTLPROD.RAC_VTA_PEDIDO_VTA_CAB P
      WHERE P.NUM_PED_VTA = pNum_Ped_Vta_in
        AND P.COD_GRUPO_CIA = pCodGrupo_Cia_in
        AND P.COD_LOCAL = pCod_local_in
      group by TIP_COMP_PAGO;

     IF C_COD_MODALIDAD = BTLPROD.PKG_CONSTANTES.CONS_MODAL_VTA_002_CONVEMP THEN
        IF V_TIP_COMP <> '04' THEN
             --Numero de Receta
             C_NUM_RECETA:='';
             BEGIN
                  SELECT C.DESCRIPCION_CAMPO
                    INTO C_NUM_RECETA
                    FROM BTLPROD.RAC_CON_BTL_MF_PED_VTA C
                   WHERE C.NUM_PED_VTA = pNum_Ped_Vta_in
                     AND C.COD_GRUPO_CIA = pCodGrupo_Cia_in
                     AND C.COD_LOCAL = pCod_local_in
                     AND C.COD_CAMPO IN ('0000000001','0000000023', '0000000031')
                     AND ROWNUM = 1;
             EXCEPTION
                WHEN OTHERS THEN
                   C_NUM_RECETA := '';
             END;
             dbms_output.put_line(C_NUM_RECETA);
              ---Codigo de Repartidor
             BEGIN
                  SELECT  C.COD_VALOR_IN
                    INTO C_COD_REPARTIDOR
                    FROM BTLPROD.RAC_CON_BTL_MF_PED_VTA C
                   WHERE C.NUM_PED_VTA = pNum_Ped_Vta_in
                     AND C.COD_GRUPO_CIA = pCodGrupo_Cia_in
                     AND C.COD_LOCAL = pCod_local_in
                     AND C.COD_CAMPO = 'D_001';
             EXCEPTION
                WHEN OTHERS THEN
                   C_COD_REPARTIDOR := '';
             END;


             --Local Origen de la Receta
             C_COD_LOCAL_RECETA :='';
             BEGIN
                  SELECT C.Cod_Valor_In
                    INTO C_COD_LOCAL_RECETA
                    FROM BTLPROD.RAC_CON_BTL_MF_PED_VTA C
                   WHERE C.NUM_PED_VTA  = pNum_Ped_Vta_in
                     AND C.COD_GRUPO_CIA = pCodGrupo_Cia_in
                     AND C.COD_LOCAL  = pCod_local_in
                     AND C.COD_CAMPO = 'D_002';
             EXCEPTION
                WHEN OTHERS THEN
                   C_COD_LOCAL_RECETA := ' ';
             END;



            ---Fecha de la Receta
             C_FCH_RECETA:='';
             BEGIN
                  SELECT C.DESCRIPCION_CAMPO
                         INTO C_FCH_RECETA
                  FROM BTLPROD.RAC_CON_BTL_MF_PED_VTA C
                  WHERE C.NUM_PED_VTA = pNum_Ped_Vta_in
                  AND   C.COD_GRUPO_CIA = pCodGrupo_Cia_in
                  AND   C.COD_LOCAL = pCod_local_in
                  AND c.cod_campo = 'D_003';
             EXCEPTION
                      WHEN OTHERS THEN
                                      C_FCH_RECETA := ' ';
             END;

            --Diagnosticos
             A_CAD_COD_DIAGNOSTICO:='';

             FOR REG3 IN (SELECT C.DESCRIPCION_CAMPO FROM BTLPROD.RAC_CON_BTL_MF_PED_VTA C
                          WHERE C.NUM_PED_VTA = pNum_Ped_Vta_in
                          AND C.COD_GRUPO_CIA = pCodGrupo_Cia_in
                          and C.COD_LOCAL = pCod_local_in
                          and C.COD_CAMPO ='D_004')
             LOOP
                 A_CAD_COD_DIAGNOSTICO   :=A_CAD_COD_DIAGNOSTICO    ||REG3.DESCRIPCION_CAMPO||'|';
             END LOOP;

            ---Codigo de Medico
             BEGIN
                  SELECT C.COD_VALOR_IN
                         INTO C_COD_MEDICO
                  FROM BTLPROD.RAC_CON_BTL_MF_PED_VTA C
                  WHERE C.NUM_PED_VTA = pNum_Ped_Vta_in
                  AND C.COD_GRUPO_CIA = pCodGrupo_Cia_in
                  AND C.COD_LOCAL = pCod_local_in
                  AND C.COD_CAMPO = 'D_005';


             EXCEPTION
                      WHEN OTHERS THEN
                                      C_COD_MEDICO := '';
             END;

        END IF;

    END IF;

    CONTADOR_DATOS :=0;

    if v_tip_comp <> '04' then
        for reg_cab in c_cabecera loop
            /* -- Parche cuando no grabo el codigo de beneficiario -- */
            declare
                v_cod_cliente cmr.mae_beneficiario.cod_cliente%type;
            begin
                if reg_cab.cod_cliente = '0000000000' then
                    begin
                        select a.cod_cliente
                          into v_cod_cliente
                          from cmr.mae_beneficiario a
                         where a.cod_convenio = reg_cab.cod_convenio
                           and a.des_cliente like (select '%' || descripcion_campo || '%'
                                                   from ptoventa.con_btl_mf_ped_vta
                                                  where cod_grupo_cia = pcodgrupo_cia_in
                                                    and cod_local = pcod_local_in
                                                    and num_ped_vta = pnum_ped_vta_in
                                                    and cod_campo = 'D_000')
                           and exists (select 1
                                  from cmr.mae_beneficiario x
                                 where x.cod_cliente = a.cod_cliente
                                   and x.cod_convenio = reg_cab.cod_convenio)
                           and rownum = 1;
                    exception
                       when others then
                           v_cod_cliente := null;
                    end;
                end if;
                reg_cab.cod_cliente := nvl(v_cod_cliente, reg_cab.cod_cliente);
            end;
            /* -- */
            begin
                if c_cod_modalidad = btlprod.pkg_constantes.cons_modal_vta_002_convemp then

                    begin
                        select c.cod_cliente, c.des_cliente
                          into v_cod_cliente, v_nom_cliente
                          from cmr.mae_cliente c
                         where c.cod_cliente = reg_cab.cod_cliente;
                    exception
                        when no_data_found then
                            begin
                                select a.cod_cliente, mc.des_cliente
                                  into v_cod_cliente, v_nom_cliente
                                  from cmr.mae_beneficiario a
                                 inner join cmr.mae_cliente mc
                                    on a.cod_cliente = mc.cod_cliente
                                 where cod_convenio = reg_cab.cod_convenio
                                   and rpad(nvl(cod_asegurado, ' '), 5, ' ') || '-' || rpad(num_poliza, 6, ' ') || '-' || rpad(num_plan, 5, ' ') || '-' || rpad(num_item, 2, ' ') = reg_cab.cod_cliente
                                   and rownum = 1;
                            exception
                                when no_data_found then
                                    raise_application_error(-20000, 'No se ha encontrado el Cliente: ' || reg_cab.cod_cliente);
                            end;
                    end;

                    begin
                        select cc.cod_convenio,
                               cc.cod_cliente,
                               mc.des_cliente,
                               mc.num_documento_id,
                               cc.flg_receta,
                               cc.flg_repartidor,
                               mc.des_direccion_social
                          into v_cod_convenio,
                               v_cod_empresa,
                               v_nom_cliente_conv,
                               v_num_ruc_empresa,
                               v_flg_receta,
                               v_flg_repartidor,
                               v_dir_cli_empresa
                          from cmr.mae_convenio cc
                         inner join cmr.mae_cliente mc
                            on cc.cod_cliente = mc.cod_cliente
                         where cc.cod_convenio = reg_cab.cod_convenio;
                    exception
                        when no_data_found then
                            raise_application_error(-20000, 'No se ha encontrado al Convenio: ' || reg_cab.cod_convenio);
                    end;

                    if v_flg_repartidor = '1' then
                        v_cod_repartidor := c_cod_repartidor;
                    else
                        v_cod_repartidor := '';
                    end if;

                    begin
                        select cod_cliente_sap
                          into v_cod_cliente_sap
                          from cmr.mae_cliente
                         where cod_cliente = v_cod_empresa;
                    exception
                        when others then
                            v_cod_cliente_sap := null;
                    end;

                    if not reg_cab.cod_convenio is null then
                        begin
                            select nvl(cod_cliente_sap_bolsa, v_cod_cliente_sap)
                              into v_cod_cliente_sap
                              from cmr.mae_convenio
                             where cod_convenio = reg_cab.cod_convenio;
                        end;
                    end if;

                end if;

                if c_seq_venta is null then
                    c_seq_venta := btlprod.pkg_documento.fn_seq_de_venta(reg_cab.cod_cia,
                                                                         lpad(reg_cab.cod_local, '3'));
                end if;

                begin
                    insert into btlprod.aux_sec_venta
                        (cia, sec_venta, cod_tipo_documento, num_documento)
                    values
                        (reg_cab.cod_cia,
                         c_seq_venta,
                         reg_cab.cod_tipo_documento,
                         reg_cab.num_documento);
                exception
                    when others then
                        raise_application_error(-20003,
                                                '- CIA:' || reg_cab.cod_cia ||
                                                '- DOCUMENTO:' || reg_cab.num_documento ||
                                                '- TIPO DOCUMENTO:' || reg_cab.cod_tipo_documento ||
                                                '- SECUENCIA-->' || c_seq_venta || ' ' ||
                                                sqlerrm);
                end;

                if to_date(substr(reg_cab.fch_emision, 1, 10), 'DD/MM/YYYY') > trunc(sysdate) then

                    raise_application_error(-20003,
                                            'La fecha de emision del documento TIPO:' || reg_cab.cod_tipo_documento ||
                                            '-> NUMERO:' || reg_cab.num_documento ||
                                            '-> FECHA:' || substr(reg_cab.fch_emision, 1, 10));
                end if;

                begin
                    xx_sec_caja        := '';
                    xx_cod_liquidacion := '';

                    select c.sec_caja, c.cod_liquidacion
                      into xx_sec_caja, xx_cod_liquidacion
                      from btlprod.aux_control_caja c
                     where c.cia = reg_cab.cod_cia
                       and c.cod_local = lpad(reg_cab.cod_local, '3')
                       and c.fch_inicio = to_date(substr(reg_cab.fch_emision, 1, 10), 'DD/MM/YYYY')
                       and c.cod_maquina = reg_cab.cod_maquina
                       and c.flg_estado_caja = '0'
                       and c.flg_activo = '1'
                       and c.cod_usuario = lpad(reg_cab.usu_emision, 5, '0');
                exception
                    when others then
                        raise_application_error(-20000,
                                                ' cia:' || reg_cab.cod_cia ||
                                                ' local:' || lpad(reg_cab.cod_local, '3') ||
                                                ' fecha:' || to_date(substr(reg_cab.fch_emision, 1, 10), 'DD/MM/YYYY') ||
                                                ' Maquina:' || reg_cab.cod_maquina ||
                                                ' Usuario:' || lpad(reg_cab.usu_emision, 5, '0') || sqlerrm);
                end;

                v_flg_doc_copago := reg_cab.flg_doc_copago;

                if c_cod_modalidad = btlprod.pkg_constantes.cons_modal_vta_002_convemp then

                    --VENTA POR CONVENIO
                    if reg_cab.tip_clien_convenio is null then
                        --V_FLG_DOC_COPAGO:='0';
                        v_cod_empresa_fin     := null;
                        v_num_ruc_empresa_fin := null;
                        v_nom_cliente_fin     := null;
                        v_dir_cli_empresa     := null;
                        v_pct_beneficiario    := 100;
                    else
                        if reg_cab.tip_clien_convenio = '1' then
                            --DOCUMENTO DE BENEFICIARIO
                            if reg_cab.cod_tipo_vtafin = 2 then
                                --V_FLG_DOC_COPAGO:='0';
                                v_cod_empresa_fin     := v_cod_empresa;
                                v_num_ruc_empresa_fin := v_num_ruc_empresa;
                                v_nom_cliente_fin     := v_nom_cliente_conv;
                                v_pct_beneficiario    := 100;
                            else
                                --V_FLG_DOC_COPAGO:='1';
                                v_cod_empresa_fin     := null;
                                v_num_ruc_empresa_fin := null;
                                v_nom_cliente_fin     := v_nom_cliente;
                                v_pct_beneficiario    := reg_cab.pct_beneficiario;
                                v_dir_cli_empresa     := null;
                            end if;
                        else
                            --DOCUMENTO DE CLIENTE
                            --V_FLG_DOC_COPAGO:='0';
                            v_cod_empresa_fin     := v_cod_empresa;
                            v_num_ruc_empresa_fin := v_num_ruc_empresa;
                            v_nom_cliente_fin     := v_nom_cliente_conv;
                            v_pct_beneficiario    := 100 - reg_cab.pct_beneficiario;
                        end if;
                    end if;
                else
                    --V_FLG_DOC_COPAGO:='0';
                    v_cod_empresa_fin     := null;
                    v_num_ruc_empresa_fin := null;
                    v_nom_cliente_fin     := null;
                    v_dir_cli_empresa     := null;
                    v_pct_beneficiario    := 100;
                end if;

                execute immediate 'ALTER SESSION SET NLS_DATE_FORMAT=''DD/MM/YYYY HH24:MI:SS''';
                execute immediate 'ALTER SESSION SET NLS_NUMERIC_CHARACTERS =''.,''';

                if reg_cab.cod_tipo_documento <> 'GRL' then

                    --begin

                    if reg_cab.cod_tipo_vtafin = '1' then
                        V_COD_MODALIDAD := pkg_constantes.CONS_MODAL_VTA_003_MAYORIS ;
                        v_cod_convenio := null;
                    ELSE
                      V_COD_MODALIDAD := C_COD_MODALIDAD;
                    end if;


                        insert into btlprod.cab_documento
                            (cia, --1
                             cod_tipo_documento, --2
                             num_documento, --3
                             cod_local, --4
                             cod_cliente, --5
                             cod_modalidad_venta, --6
                             cod_empresa, --7
                             cod_convenio, --8
                             cod_maquina, --9
                             sec_caja, --10
                             cod_tipo_venta, --11
                             tot_item, --12
                             cod_estado, --13
                             num_ruc_empresa, --14
                             des_razon_social, --15
                             des_aux_cli_tlf, --16
                             des_aux_cli_nombre, --17
                             des_aux_cli_direcc, --18
                             mto_base_imp, --19
                             mto_exonerado, --20
                             mto_impuesto, --21
                             mto_total, --22
                             flg_transferencia, --23
                             flg_urgente, --24
                             flg_reserva_stk, --25
                             imp_tip_cambio, --26
                             num_pedido_padre, --27
                             num_receta, --28
                             pct_beneficiario, --29
                             cod_concepto, --30
                             cod_subconcepto_fact, --31
                             cod_origen_documento, --32
                             flg_kardex, --33
                             flg_estado_comprobante, --34
                             cod_local_refer, --35
                             cod_tipo_documento_refer, --36
                             num_documento_refer, --37
                             cod_urbanizacion, --38
                             cod_usuario_dependiente, --39
                             cod_ubigeo, --40
                             cod_liquidacion, --41
                             cod_motivo_anulacion, --42
                             cod_usuario, --43
                             fch_registra, --44
                             cod_usuario_actualiza, --45
                             fch_actualiza, --46
                             usuario, --47
                             fecha, --48
                             flg_entrega_local, --49
                             cod_medico, --50
                             cod_direccion_cli, --51
                             flg_doc_copago, --52
                             cod_tipo_vtafin, --53
                             fch_emision, --54
                             cod_formato, --55
                             cod_cliente_imp, --56
                             num_item_vta, --57
                             mto_comision, --58
                             num_hora, --59
                             cod_repartidor, --60
                             nro_items_sel, --61
                             mto_seleccionado, --62
                             cod_serie_rel, --,63
                             cod_grupo_cia, --64
                             cod_local_mf, --65
                             num_ped_vta, --66
                             sec_comp_pago, --67
                             cod_cliente_sap --68 -- kmoncada 20.06.2014 graba codigo cliente sap
                             )
                        values
                            (reg_cab.cod_cia, --1
                             reg_cab.cod_tipo_documento, --2
                             reg_cab.num_documento, --3
                             lpad(reg_cab.cod_local, '3'), --4
                             -- kmoncada 20.07.2014
                             reg_cab.cod_cliente, -- v_cod_cliente, --5
                             V_COD_MODALIDAD, -- c_cod_modalidad, --6
                             v_cod_empresa_fin, --7
                             v_cod_convenio, --8
                             reg_cab.cod_maquina, --9
                             xx_sec_caja, --10
                             c_cod_tipo_venta, --11
                             reg_cab.items, --12
                             'EMI', --13
                             v_num_ruc_empresa_fin, --14
                             v_nom_cliente_fin, --15
                             null, --16
                             null, --17
                             v_dir_cli_empresa, --18
                             reg_cab.mto_base_imp, --19
                             reg_cab.mto_exonerado, --20
                             reg_cab.mto_impuesto, --21
                             reg_cab.mto_total, --22
                             0, --23
                             0, --24
                             0, --25
                             0, --IMP_TIP_CAMBIO, --26
                             null, --27
                             null, --28
                             v_pct_beneficiario, --29
                             null, --30
                             null, --31
                             null, --32
                             decode(reg_cab.ind_afecta_kardex, 'S', '1', '0'), --33
                             null, --34
                             null, --35
                             reg_cab.tipo_documento_ref, --36
                             reg_cab.num_documento_ref, --37
                             null, --38
                             lpad(reg_cab.usu_emision, 5, '0'), --39
                             null, --40
                             xx_cod_liquidacion, --41
                             null, --42
                             lpad(reg_cab.usu_emision, 5, '0'), --43
                             to_date(substr(reg_cab.fch_emision, 1, 10), 'DD/MM/YYYY'), --44
                             null, --45
                             null, --46
                             user, --47
                             sysdate, --48
                             0, --49
                             reg_cab.cod_medico, --50
                             null, --51
                             v_flg_doc_copago, --52
                             null, --53
                             to_date(substr(reg_cab.fch_emision, 1, 10), 'DD/MM/YYYY'), --54
                             '001', --55
                             null, --56
                             reg_cab.items, --57
                             0, --58
                             0, --59
                             null, --60
                             0, --61
                             0, --62
                             '', --63
                             pcodgrupo_cia_in, --64
                             pcod_local_in, --65
                             pnum_ped_vta_in, --66
                             reg_cab.sec_comp_pago, --67
                             V_COD_CLIENTE_SAP -- kmoncada 20.06.2014 graba codigo cliente sap
                             );
                    --exception
                    --    when others then
                    --        raise_application_error(-20000, sqlerrm);
                    --end;

                    --begin

                        update btlprod.cab_documento
                           set cod_tipo_vtafin = reg_cab.cod_tipo_vtafin
                         where cia = reg_cab.cod_cia
                           and cod_tipo_documento =
                               reg_cab.cod_tipo_documento
                           and num_documento = reg_cab.num_documento;

                    --exception
                    --    when others then
                    --        raise_application_error(-20000, sqlerrm);
                    --end;

                    --ACA COMIENZO A INGRESAR LA RECETA

                    if c_cod_modalidad = btlprod.pkg_constantes.cons_modal_vta_002_convemp then

                        if c_cod_modalidad = btlprod.pkg_constantes.cons_modal_vta_002_convemp and contador_datos = 0 then
                            sp_graba_datos_adicionales(pcodgrupo_cia_in,
                                                       pcod_local_in,
                                                       pnum_ped_vta_in,
                                                       v_cod_convenio);
                            contador_datos := 1;
                        end if;

                        if trim(v_cod_convenio) = pkg_constantes.cons_cnv_rimac then
                            begin
                                if c_fch_receta is not null and c_cod_medico is not null and c_cod_local_receta is not null and  a_cad_cod_diagnostico is not null then
                                    btlprod.pkg_convenio.sp_graba_receta(reg_cab.num_documento,
                                                                         btlprod.pkg_constantes.cons_tip_doc_guia_loc,
                                                                         lpad(reg_cab.cod_local, '3'),
                                                                         to_date(c_fch_receta, 'DD/MM/YYYY'),
                                                                         c_cod_medico,
                                                                         lpad(reg_cab.usu_emision, 5, '0'),
                                                                         c_cod_local_receta,
                                                                         a_cad_cod_diagnostico,
                                                                         v_cod_cliente,
                                                                         null,
                                                                         v_cod_convenio);
                                else
                                    raise_application_error(-20000, 'R1 Faltan Ingresar Datos de la Receta. '|| reg_cab.cod_tipo_documento||'-'||reg_cab.num_documento || '-' || c_fch_receta || '-' || c_cod_medico || '-' || c_cod_local_receta || '-' || a_cad_cod_diagnostico);
                                end if;
                            end;
                        else
                            if v_flg_receta = '1' then
                                if c_fch_receta is not null and c_cod_medico is not null and c_cod_local_receta is not null and a_cad_cod_diagnostico is not null then
                                    if v_flg_doc_copago = '0' then
                                        btlprod.pkg_convenio.sp_graba_receta(reg_cab.num_documento,
                                                                             reg_cab.cod_tipo_documento,
                                                                             lpad(reg_cab.cod_local, '3'),
                                                                             to_date(c_fch_receta, 'DD/MM/YYYY'),
                                                                             c_cod_medico, lpad(reg_cab.usu_emision, 5, '0'),
                                                                             c_cod_local_receta,
                                                                             a_cad_cod_diagnostico,
                                                                             v_cod_cliente,
                                                                             c_num_receta,
                                                                             v_cod_convenio);
                                    end if;
                                else
                                    raise_application_error(-20000, 'R2 Faltan Ingresar Datos de la Receta. '|| reg_cab.cod_tipo_documento||'-'||reg_cab.num_documento || '-' || c_fch_receta || '-' || c_cod_medico || '-' || c_cod_local_receta || '-' || a_cad_cod_diagnostico);
                                end if;
                            end if;
                        end if;
                    end if;

                    --ACA COMIENZO A BARRER TODO EL DETALLE DE LOS DOCUMENTOS
                    if c_cod_modalidad = btlprod.pkg_constantes.cons_modal_vta_002_convemp then
                        for reg1 in c_detalle(reg_cab.cod_cia,
                                              reg_cab.cod_tipo_documento,
                                              reg_cab.num_documento) loop
                            begin

                                if reg1.prod_apps is null then
                                    select t.val_max_frac
                                      into v_cant_fraccion
                                      from apps.lgt_prod@xe_999 t
                                     where cod_prod = reg1.prod_det;

                                    v_cant_producto := reg1.cant_atendida * (v_cant_fraccion / reg1.val_frac);
                                else
                                    v_cant_fraccion := reg1.ctd_fracciona;
                                    v_cant_producto := reg1.ctd_producto;
                                end if;

                                insert into btlprod.det_documento
                                    (cia, --1
                                     cod_tipo_documento, --2
                                     num_documento, --3
                                     cod_modalidad, --4
                                     cod_producto, --5
                                     ctd_producto, --6
                                     ctd_producto_frac, --7
                                     ctd_fraccionamiento, --8
                                     pct_dsct_unit, --9
                                     prc_unit_kairo, --10
                                     prc_unit_vta, --11
                                     prc_unit_neto_vta, --12
                                     imp_unit_base_imp, --13
                                     imp_unit_impuesto, --14
                                     pct_igv, --15
                                     pct_comision, --16
                                     mto_subtotal, --17
                                     cod_promocion, --18
                                     usuario, --19
                                     fecha, --20
                                     cod_servicio, --21
                                     num_servicio, --22
                                     mto_comision, --23
                                     num_form_sunat, --24
                                     cod_laboratorio, --25
                                     num_item, --26
                                     des_producto, --27
                                     flg_regalo --28
                                     )
                                values
                                    (reg1.cod_cia, --1
                                     reg1.cod_tipo_documento, --2
                                     reg1.num_documento, --3
                                     V_COD_MODALIDAD,--  c_cod_modalidad, --4
                                     reg1.cod_producto, --5
                                     decode(reg1.flg_fraccion, '1', 0, v_cant_producto /*REG1.CTD_PRODUCTO*/), --6
                                     decode(reg1.flg_fraccion, '1', v_cant_producto /*REG1.CTD_PRODUCTO*/, 0), --7
                                     v_cant_fraccion, --REG1.CTD_FRACCIONA, --8
                                     reg1.pct_descuento, --9
                                     0, --REVISAR DE DONDE SACARE EL KAIROS --10
                                     reg1.prc_unitario, --11
                                     reg1.prc_unitario, --12
                                     reg1.mto_subtotal - reg1.mto_igv, --13
                                     reg1.mto_igv, --14
                                     reg1.pct_igv, --15
                                     0, --16
                                     reg1.mto_subtotal, --17
                                     null, --18
                                     user, --19
                                     sysdate, --20
                                     null, --21
                                     null, --22
                                     reg1.mto_subtotal * 0, --23
                                     null, --24
                                     (select cod_laboratorio from cmr.mae_producto_com where cod_producto = reg1.cod_producto), --25
                                     reg1.num_item, --26
                                     reg1.des_producto, --27
                                     '0' --28
                                     );
                            exception
                                when others then
                                    raise_application_error(-20003, 'PRODUCTO:*' || reg1.cod_tipo_documento||'-'||reg1.num_documento||'-'||reg1.cod_producto || chr(13) || sqlerrm);
                            end;
                        end loop;
                    else
                        for reg1 in c_detalle_regular(reg_cab.cod_cia,
                                                      reg_cab.cod_tipo_documento,
                                                      reg_cab.num_documento) loop
                            begin

                                insert into btlprod.det_documento
                                    (cia, --1
                                     cod_tipo_documento, --2
                                     num_documento, --3
                                     cod_modalidad, --4
                                     cod_producto, --5
                                     ctd_producto, --6
                                     ctd_producto_frac, --7
                                     ctd_fraccionamiento, --8
                                     pct_dsct_unit, --9
                                     prc_unit_kairo, --10
                                     prc_unit_vta, --11
                                     prc_unit_neto_vta, --12
                                     imp_unit_base_imp, --13
                                     imp_unit_impuesto, --14
                                     pct_igv, --15
                                     pct_comision, --16
                                     mto_subtotal, --17
                                     cod_promocion, --18
                                     usuario, --19
                                     fecha, --20
                                     cod_servicio, --21
                                     num_servicio, --22
                                     mto_comision, --23
                                     num_form_sunat, --24
                                     cod_laboratorio, --25
                                     num_item, --26
                                     des_producto, --27
                                     flg_regalo --28
                                     )
                                values
                                    (reg1.cod_cia, --1
                                     reg1.cod_tipo_documento, --2
                                     reg1.num_documento, --3
                                     c_cod_modalidad, --4
                                     reg1.cod_producto, --5
                                     decode(reg1.flg_fraccion, '1', 0, reg1.ctd_producto), --6
                                     decode(reg1.flg_fraccion, '1', reg1.ctd_producto, 0), --7
                                     reg1.ctd_fracciona, --8
                                     reg1.pct_descuento, --9
                                     0, --PRC_UNIT_KAIRO, --REVISAR DE DONDE SACARE EL KAIROS --10
                                     reg1.prc_unitario, --11
                                     reg1.prc_unitario, --12
                                     reg1.mto_subtotal - reg1.mto_igv, --13
                                     reg1.mto_igv, --14
                                     reg1.pct_igv, --15
                                     0, --C_REG_PRODUCTO.PCT_COMISION, --PCT_COMISION, --BUSCAR DE DONDE CARNAL EL PORENTAJE DE COMISION --16
                                     reg1.mto_subtotal, --17
                                     null, --LE PONGO NULL PORQUE NO HAY PROMOCIONES EN LA VENTA NORMAL --18
                                     user, --19
                                     sysdate, --20
                                     null, --21
                                     null, --22
                                     reg1.mto_subtotal * 0, --MTO_COMISION, --VER DE DONDE CALCULAR EL MONTO DE LA COMISION      --23
                                     null, --24
                                     (select cod_laboratorio from cmr.mae_producto_com where cod_producto = reg1.cod_producto), --25
                                     reg1.num_item, --26
                                     reg1.des_producto, --27
                                     '0' --28
                                     );
                            exception
                                when others then
                                    raise_application_error(-20003, 'PRODUCTO:*' || reg1.cod_tipo_documento||'-'||reg1.num_documento||'-'||reg1.cod_producto || chr(13) || sqlerrm);
                            end;
                        end loop;
                    end if;

                else
                    /* -- Parche para cuando la guia ya existe -- */
                    /*
                    select count(1)
                      into v_count_guia
                      from btlcero.cab_guia_cliente
                     where cia = reg_cab.cod_cia
                       and cod_local = reg_cab.cod_local
                       and num_guia = reg_cab.num_documento;

                    if (v_count_guia > 0) then
                        v_num_guia := substr(reg_cab.num_documento, 1, 3) || 'O' || substr(reg_cab.num_documento, 5);
                        v_guia_existe := 'S';
                    else*/
                        v_num_guia := reg_cab.num_documento;
                        v_guia_existe := 'N';
                    /*end if;
                    /* -- */
                    if c_cod_modalidad = btlprod.pkg_constantes.cons_modal_vta_002_convemp and contador_datos = 0 then
                        sp_graba_datos_adicionales(pcodgrupo_cia_in,
                                                   pcod_local_in,
                                                   pnum_ped_vta_in,
                                                   v_cod_convenio,
                                                   v_guia_existe);
                        contador_datos := 1;
                    end if;

                    --begin
                        insert into btlcero.cab_guia_cliente
                            (cod_btl, --1
                             num_guia, --2
                             est_guia, --3
                             cod_cliente, --4
                             fch_emision, --5
                             usu_emision, --6
                             tot_item, --7
                             cod_tipodoc, --8
                             cod_motivo_guia, --9
                             cod_origen, --10
                             cod_destino, --11
                             cod_periodo, --12
                             flg_kardex, --13
                             cod_convenio, --14
                             cod_beneficiario, --15
                             num_documento_ref, --16
                             mto_base_imp, --17
                             mto_impuesto, --18
                             mto_exonerado, --19
                             mto_total, --20
                             pct_igv, --21
                             pct_copago, --22
                             imp_copago, --23
                             cod_tipodoc_ref, --24
                             cia, --25
                             flg_doc_copago, --26
                             cod_formato, --27
                             cod_liquidacion, --28
                             cod_cliente_sap, --29
                             cod_grupo_cia, --30
                             cod_local_mf, --31
                             num_ped_vta, --32
                             sec_comp_pago, --33
                             cod_local --34
                             )
                        values
                            (lpad(reg_cab.cod_local, '3'), --1
                             v_num_guia, --reg_cab.num_documento, --2
                             'TRA', --3
                             v_cod_empresa, --4
                             to_date(substr(reg_cab.fch_emision, 1, 10), 'DD/MM/YYYY'), --5
                             lpad(reg_cab.usu_emision, 5, '0'), --6
                             reg_cab.items, --7
                             reg_cab.cod_tipo_documento, --8
                             '005', --9
                             lpad(reg_cab.cod_local, '3'), --10
                             'CLI', --11
                             to_char(to_date(substr(reg_cab.fch_emision, 1, 10), 'DD/MM/YYYY'), 'YYYYMM'), --12
                             decode(reg_cab.ind_afecta_kardex, 'S', '1', '0'), --13
                             v_cod_convenio, --14
                             v_cod_cliente, --15
                             reg_cab.num_documento_ref, --16
                             reg_cab.mto_base_imp, --17
                             reg_cab.mto_impuesto, --18
                             reg_cab.mto_exonerado, --19
                             reg_cab.mto_total, --20
                             reg_cab.porc_igv_comp_pago, --21
                             reg_cab.pct_beneficiario, --22
                             reg_cab.val_copago_comp_pago, --23
                             reg_cab.tipo_documento_ref, --24
                             reg_cab.cod_cia, --25
                             v_flg_doc_copago, --26
                             '005', --27
                             xx_cod_liquidacion, --28
                             v_cod_cliente_sap, --29
                             pcodgrupo_cia_in, --30
                             pcod_local_in, --31
                             pnum_ped_vta_in, --32
                             reg_cab.sec_comp_pago, --33
                             lpad(reg_cab.cod_local, '3') --34
                             );
                    --exception
                    --    when others then
                    --        raise_application_error(-20000, sqlerrm);
                    --end;

                    if c_cod_modalidad = btlprod.pkg_constantes.cons_modal_vta_002_convemp then

                        if c_cod_modalidad = btlprod.pkg_constantes.cons_modal_vta_002_convemp and contador_datos = 0 then
                            sp_graba_datos_adicionales(pcodgrupo_cia_in,
                                                       pcod_local_in,
                                                       pnum_ped_vta_in,
                                                       v_cod_convenio,
                                                       v_guia_existe);
                            contador_datos := 1;
                        end if;

                        if trim(v_cod_convenio) = pkg_constantes.cons_cnv_rimac then
                            begin
                                if c_fch_receta is not null and c_cod_medico is not null and c_cod_local_receta is not null and a_cad_cod_diagnostico is not null then
                                    btlprod.pkg_convenio.sp_graba_receta(v_num_guia, --reg_cab.num_documento,
                                                                         btlprod.pkg_constantes.cons_tip_doc_guia_loc,
                                                                         lpad(reg_cab.cod_local, '3'),
                                                                         to_date(c_fch_receta, 'DD/MM/YYYY'),
                                                                         c_cod_medico,
                                                                         lpad(reg_cab.usu_emision, 5, '0'),
                                                                         c_cod_local_receta,
                                                                         a_cad_cod_diagnostico,
                                                                         v_cod_cliente,
                                                                         null,
                                                                         v_cod_convenio);
                                else
                                    raise_application_error(-20000, 'R3 Faltan Ingresar Datos de la Receta. ' || reg_cab.cod_tipo_documento || '-' || reg_cab.num_documento || '-' || c_fch_receta || '-' || c_cod_medico || '-' || c_cod_local_receta || '-' || a_cad_cod_diagnostico);
                                end if;
                            end;
                        else
                            if v_flg_receta = '1' then
                                if c_fch_receta is not null and c_cod_medico is not null and c_cod_local_receta is not null and a_cad_cod_diagnostico is not null then
                                    if v_flg_doc_copago = '0' then
                                        btlprod.pkg_convenio.sp_graba_receta(v_num_guia, --reg_cab.num_documento,
                                                                             reg_cab.cod_tipo_documento,
                                                                             lpad(reg_cab.cod_local, '3'),
                                                                             to_date(c_fch_receta, 'DD/MM/YYYY'),
                                                                             c_cod_medico,
                                                                             lpad(reg_cab.usu_emision, 5, '0'),
                                                                             c_cod_local_receta,
                                                                             a_cad_cod_diagnostico,
                                                                             v_cod_cliente,
                                                                             c_num_receta,
                                                                             v_cod_convenio);
                                    end if;
                                else
                                    raise_application_error(-20000,
                                                            'R4 Faltan Ingresar Datos de la Receta. ' || reg_cab.cod_tipo_documento || '-' || reg_cab.num_documento || '-' || c_fch_receta || '-' || c_cod_medico || '-' || c_cod_local_receta || '-' || a_cad_cod_diagnostico);
                                end if;
                            end if;
                        end if;
                    end if;

                    if c_cod_modalidad = btlprod.pkg_constantes.cons_modal_vta_002_convemp then
                        for reg1 in c_detalle(reg_cab.cod_cia,
                                              reg_cab.cod_tipo_documento,
                                              reg_cab.num_documento) loop
                            begin

                                if reg1.prod_apps is null then
                                    select t.val_max_frac
                                      into v_cant_fraccion
                                      from apps.lgt_prod@xe_999 t
                                     where cod_prod = reg1.prod_det;

                                    v_cant_producto := reg1.cant_atendida * (v_cant_fraccion / reg1.val_frac);
                                else
                                    v_cant_fraccion := reg1.ctd_fracciona;
                                    v_cant_producto := reg1.ctd_producto;
                                end if;

                                insert into btlcero.det_guia_cliente
                                    (num_guia, --1
                                     num_item, --2
                                     cod_producto, --3
                                     ctd_producto, --4
                                     ctd_producto_frac, --5
                                     flg_fracciono, --6
                                     ctd_fracciono, --7
                                     prc_kairos, --8
                                     prc_venta, --9
                                     pct_igv, --10
                                     mto_base_imp, --11
                                     mto_exonerado, --12
                                     mto_impuesto, --13
                                     mto_total, --14
                                     pct_copago, --15
                                     imp_copago, --16
                                     cia, --17
                                     cod_local --18
                                     )
                                values
                                    (v_num_guia, -- reg1.num_documento, --1
                                     reg1.num_item, --2
                                     reg1.cod_producto, --3
                                     decode(reg1.flg_fraccion, '1', 0, v_cant_producto /*REG1.CTD_PRODUCTO*/), --4
                                     decode(reg1.flg_fraccion, '1', v_cant_producto /*REG1.CTD_PRODUCTO*/, 0), --5
                                     reg1.flg_fraccion, --6
                                     v_cant_fraccion, --REG1.CTD_FRACCIONA,                                         --7
                                     0, --8
                                     reg1.prc_unitario, --9
                                     reg1.pct_igv * 100, --10
                                     decode(reg1.mto_igv, 0, 0, reg1.mto_subtotal - reg1.mto_igv), --11
                                     decode(reg1.mto_igv, 0, reg1.mto_subtotal - reg1.mto_igv, 0), --12
                                     reg1.mto_igv, --13
                                     reg1.mto_subtotal, --14
                                     0, --15
                                     reg1.imp_copago, --16
                                     reg_cab.cod_cia, --17
                                     lpad(reg_cab.cod_local, '3') --18
                                     );
                            exception
                                when others then
                                    raise_application_error(-20003, 'PRODUCTO:*' || reg1.cod_tipo_documento||'-'||reg1.num_documento||'-'||reg1.cod_producto || chr(13) || sqlerrm);
                            end;
                        end loop;
                    end if;
                end if;
            end;
        end loop;
    else
        for reg_cab_nc in c_cabecera_nc loop
            if c_cod_modalidad = btlprod.pkg_constantes.cons_modal_vta_002_convemp then
                begin
                    select c.cod_cliente, c.des_cliente
                      into v_cod_cliente, v_nom_cliente
                      from cmr.mae_cliente c
                     where c.cod_cliente = reg_cab_nc.cod_cliente;
                exception
                    when no_data_found then
                        begin
                            select a.cod_cliente, mc.des_cliente
                              into v_cod_cliente, v_nom_cliente
                              from cmr.mae_beneficiario a
                             inner join cmr.mae_cliente mc
                                on a.cod_cliente = mc.cod_cliente
                             where cod_convenio = reg_cab_nc.cod_convenio
                               and rpad(nvl(cod_asegurado, ' '), 5, ' ') || '-' || rpad(num_poliza, 6, ' ') || '-' || rpad(num_plan, 5, ' ') || '-' || rpad(num_item, 2, ' ') = reg_cab_nc.cod_cliente
                               and rownum = 1;
                        exception
                            when no_data_found then
                                raise_application_error(-20001, 'No se ha encontrado el Cliente: ' || reg_cab_nc.cod_cliente);
                        end;
                end;

                begin
                    select cc.cod_convenio,
                           cc.cod_cliente,
                           mc.des_cliente,
                           mc.num_documento_id,
                           cc.flg_receta,
                           cc.flg_repartidor,
                           mc.des_direccion_social
                      into v_cod_convenio,
                           v_cod_empresa,
                           v_nom_cliente_conv,
                           v_num_ruc_empresa,
                           v_flg_receta,
                           v_flg_repartidor,
                           v_dir_cli_empresa
                      from cmr.mae_convenio cc
                     inner join cmr.mae_cliente mc
                        on cc.cod_cliente = mc.cod_cliente
                     where cc.cod_convenio = reg_cab_nc.cod_convenio;
                exception
                    when no_data_found then
                        raise_application_error(-20000, 'No se ha encontrado al Convenio: ' || reg_cab_nc.cod_convenio);
                end;

                begin
                    select cod_cliente_sap
                      into v_cod_cliente_sap
                      from cmr.mae_cliente
                     where cod_cliente = v_cod_empresa;
                exception
                    when others then
                        v_cod_cliente_sap := null;
                end;

                if not reg_cab_nc.cod_convenio is null then
                    begin
                        select nvl(cod_cliente_sap_bolsa, v_cod_cliente_sap)
                          into v_cod_cliente_sap
                          from cmr.mae_convenio
                         where cod_convenio = reg_cab_nc.cod_convenio;
                    end;
                end if;

                if c_seq_venta is null then
                    c_seq_venta := btlprod.pkg_documento.fn_seq_de_venta(reg_cab_nc.cod_cia, lpad(reg_cab_nc.cod_local, '3'));
                end if;

                begin
                    insert into btlprod.aux_sec_venta
                        (cia, sec_venta, cod_tipo_documento, num_documento)
                    values
                        (reg_cab_nc.cod_cia,
                         c_seq_venta,
                         reg_cab_nc.cod_tipo_documento,
                         reg_cab_nc.num_documento);
                exception
                    when others then
                        raise_application_error(-20003,
                                                '- CIA:' || reg_cab_nc.cod_cia ||
                                                '- DOCUMENTO:' || reg_cab_nc.num_documento ||
                                                '- TIPO DOCUMENTO:' || reg_cab_nc.cod_tipo_documento ||
                                                '- SECUENCIA-->' || c_seq_venta || ' ' ||
                                                sqlerrm);
                end;

                if to_date(substr(reg_cab_nc.fch_emision, 1, 10), 'DD/MM/YYYY') > trunc(sysdate) then
                    raise_application_error(-20003,
                                            'La fecha de emision del documento TIPO:' || reg_cab_nc.cod_tipo_documento ||
                                            '->NUMERO:' || reg_cab_nc.num_documento ||
                                            ' FECHA:' || substr(reg_cab_nc.fch_emision, 1, 10));
                end if;

                begin
                    xx_sec_caja        := '';
                    xx_cod_liquidacion := '';

                    select c.sec_caja, c.cod_liquidacion
                      into xx_sec_caja, xx_cod_liquidacion
                      from btlprod.aux_control_caja c
                     where c.cia = reg_cab_nc.cod_cia
                       and c.cod_local = lpad(reg_cab_nc.cod_local, '3')
                       and c.fch_inicio = to_date(substr(reg_cab_nc.fch_emision, 1, 10), 'DD/MM/YYYY')
                       and c.cod_maquina = reg_cab_nc.cod_maquina
                       and c.flg_estado_caja = '0'
                       and c.flg_activo = '1'
                       and c.cod_usuario = lpad(reg_cab_nc.usu_emision, 5, '0');
                exception
                    when others then
                        raise_application_error(-20000,
                                                ' cia:' || reg_cab_nc.cod_cia ||
                                                ' local:' || lpad(reg_cab_nc.cod_local, '3') ||
                                                ' fecha:' || to_date(substr(reg_cab_nc.fch_emision, 1, 10), 'DD/MM/YYYY') ||
                                                ' Maquina:' || reg_cab_nc.cod_maquina ||
                                                ' Usuario:' || lpad(reg_cab_nc.usu_emision, 5, '0') || sqlerrm);
                end;

                v_flg_doc_copago := reg_cab_nc.flg_doc_copago;

                if reg_cab_nc.tip_clien_convenio is null then
                    --V_FLG_DOC_COPAGO:='0';
                    v_cod_empresa_fin     := null;
                    v_num_ruc_empresa_fin := null;
                    v_nom_cliente_fin     := null;
                    v_dir_cli_empresa     := null;
                    v_pct_beneficiario    := 100;
                else
                    if reg_cab_nc.tip_clien_convenio = '1' then
                        --DOCUMENTO DE BENEFICIARIO
                        if reg_cab_nc.cod_tipo_vtafin = 2 then
                            --V_FLG_DOC_COPAGO:='0';
                            v_cod_empresa_fin     := v_cod_empresa;
                            v_num_ruc_empresa_fin := v_num_ruc_empresa;
                            v_nom_cliente_fin     := v_nom_cliente_conv;
                            v_pct_beneficiario    := 100;
                        else
                            --V_FLG_DOC_COPAGO:='1';
                            v_cod_empresa_fin     := null;
                            v_num_ruc_empresa_fin := null;
                            v_nom_cliente_fin     := v_nom_cliente;
                            v_pct_beneficiario    := reg_cab_nc.pct_beneficiario;
                            v_dir_cli_empresa     := null;
                        end if;
                    else
                        --DOCUMENTO DE CLIENTE
                        --V_FLG_DOC_COPAGO:='0';
                        v_cod_empresa_fin     := v_cod_empresa;
                        v_num_ruc_empresa_fin := v_num_ruc_empresa;
                        v_nom_cliente_fin     := v_nom_cliente_conv;
                        v_pct_beneficiario    := 100 -
                                                 reg_cab_nc.pct_beneficiario;
                    end if;
                end if;

                execute immediate 'ALTER SESSION SET NLS_DATE_FORMAT=''DD/MM/YYYY HH24:MI:SS''';
                execute immediate 'ALTER SESSION SET NLS_NUMERIC_CHARACTERS =''.,''';

                --begin

                    if reg_cab_nc.cod_tipo_vtafin = '1' then
                        V_COD_MODALIDAD := pkg_constantes.CONS_MODAL_VTA_003_MAYORIS ;
                        v_cod_convenio := null;
            --            V_COD_TIPO_VTAFIN = reg_cab.cod_tipo_vtafin;
                     ELSE
                        V_COD_MODALIDAD := C_COD_MODALIDAD;
--                        V_COD_CONVENIO := COD_CONVENIO;
            --            V_COD_TIPO_VTAFIN = '1'
                     end if;
                    insert into btlprod.cab_documento
                        (cia, --1
                         cod_tipo_documento, --2
                         num_documento, --3
                         cod_local, --4
                         cod_cliente, --5
                         cod_modalidad_venta, --6
                         cod_empresa, --7
                         cod_convenio, --8
                         cod_maquina, --9
                         sec_caja, --10
                         cod_tipo_venta, --11
                         tot_item, --12
                         cod_estado, --13
                         num_ruc_empresa, --14
                         des_razon_social, --15
                         des_aux_cli_tlf, --16
                         des_aux_cli_nombre, --17
                         des_aux_cli_direcc, --18
                         mto_base_imp, --19
                         mto_exonerado, --20
                         mto_impuesto, --21
                         mto_total, --22
                         flg_transferencia, --23
                         flg_urgente, --24
                         flg_reserva_stk, --25
                         imp_tip_cambio, --26
                         num_pedido_padre, --27
                         num_receta, --28
                         pct_beneficiario, --29
                         cod_concepto, --30
                         cod_subconcepto_fact, --31
                         cod_origen_documento, --32
                         flg_kardex, --33
                         flg_estado_comprobante, --34
                         cod_local_refer, --35
                         cod_tipo_documento_refer, --36
                         num_documento_refer, --37
                         cod_urbanizacion, --38
                         cod_usuario_dependiente, --39
                         cod_ubigeo, --40
                         cod_liquidacion, --41
                         cod_motivo_anulacion, --42
                         cod_usuario, --43
                         fch_registra, --44
                         cod_usuario_actualiza, --45
                         fch_actualiza, --46
                         usuario, --47
                         fecha, --48
                         flg_entrega_local, --49
                         cod_medico, --50
                         cod_direccion_cli, --51
                         flg_doc_copago, --52
                         cod_tipo_vtafin, --53
                         fch_emision, --54
                         cod_formato, --55
                         cod_cliente_imp, --56
                         num_item_vta, --57
                         mto_comision, --58
                         num_hora, --59
                         cod_repartidor, --60
                         nro_items_sel, --61
                         mto_seleccionado, --62
                         cod_serie_rel, --,63
                         cod_grupo_cia, --64
                         cod_local_mf, --65
                         num_ped_vta, --66
                         sec_comp_pago --67
                         )
                    values
                        (reg_cab_nc.cod_cia, --1
                         reg_cab_nc.cod_tipo_documento, --2
                         reg_cab_nc.num_documento, --3
                         lpad(reg_cab_nc.cod_local, '3'), --4
                         v_cod_cliente, --5
                         V_COD_MODALIDAD, -- c_cod_modalidad, --6
                         v_cod_empresa_fin, --7
                         v_cod_convenio, --8
                         reg_cab_nc.cod_maquina, --9
                         xx_sec_caja, --10
                         c_cod_tipo_venta, --11
                         reg_cab_nc.items, --12
                         'EMI', --13
                         v_num_ruc_empresa_fin, --14
                         v_nom_cliente_fin, --15
                         null, --16
                         null, --17
                         v_dir_cli_empresa, --18
                         reg_cab_nc.mto_base_imp, --19
                         reg_cab_nc.mto_exonerado, --20
                         reg_cab_nc.mto_impuesto, --21
                         reg_cab_nc.mto_total, --22
                         0, --23
                         0, --24
                         0, --25
                         0, --IMP_TIP_CAMBIO, --26
                         null, --27
                         null, --28
                         v_pct_beneficiario, --29
                         null, --30
                         null, --31
                         null, --32
                         decode(reg_cab_nc.ind_afecta_kardex, 'S', '1', '0'), --33
                         null, --34
                         null, --35
                         reg_cab_nc.tipo_documento_ref, --36
                         reg_cab_nc.num_documento_ref, --37
                         null, --38
                         lpad(reg_cab_nc.usu_emision, 5, '0'), --39
                         null, --40
                         xx_cod_liquidacion, --41
                         null, --42
                         lpad(reg_cab_nc.usu_emision, 5, '0'), --43
                         to_date(substr(reg_cab_nc.fch_emision, 1, 10), 'DD/MM/YYYY'), --44
                         null, --45
                         null, --46
                         user, --47
                         sysdate, --48
                         0, --49
                         reg_cab_nc.cod_medico, --50
                         null, --51
                         v_flg_doc_copago, --52
                         null, --53
                         to_date(substr(reg_cab_nc.fch_emision, 1, 10), 'DD/MM/YYYY'), --54
                         '001', --55
                         null, --56
                         reg_cab_nc.items, --57
                         0, --58
                         0, --59
                         null, --60
                         0, --61
                         0, --62
                         '', --63
                         pcodgrupo_cia_in, --64
                         pcod_local_in, --65
                         pnum_ped_vta_in, --66
                         reg_cab_nc.sec_comp_pago --67
                         );
                --exception
                --    when others then
                --        raise_application_error(-20000, sqlerrm);
                --end;

                --begin
                    update btlprod.cab_documento
                       set cod_tipo_vtafin = reg_cab_nc.cod_tipo_vtafin
                     where cia = reg_cab_nc.cod_cia
                       and cod_tipo_documento =
                           reg_cab_nc.cod_tipo_documento
                       and num_documento = reg_cab_nc.num_documento;

                --exception
                --    when others then
                --        raise_application_error(-20000, sqlerrm);
                --end;

                --ACA COMIENZO A BARRER TODO EL DETALLE DE LOS DOCUMENTOS
                for reg1_nc in c_detalle_nc(reg_cab_nc.num_ped_vta_origen,
                                            reg_cab_nc.cod_cia,
                                            reg_cab_nc.tipo_documento_ref,
                                            reg_cab_nc.num_documento_ref) loop
                    begin

                        insert into btlprod.det_documento
                            (cia, --1
                             cod_tipo_documento, --2
                             num_documento, --3
                             cod_modalidad, --4
                             cod_producto, --5
                             ctd_producto, --6
                             ctd_producto_frac, --7
                             ctd_fraccionamiento, --8
                             pct_dsct_unit, --9
                             prc_unit_kairo, --10
                             prc_unit_vta, --11
                             prc_unit_neto_vta, --12
                             imp_unit_base_imp, --13
                             imp_unit_impuesto, --14
                             pct_igv, --15
                             pct_comision, --16
                             mto_subtotal, --17
                             cod_promocion, --18
                             usuario, --19
                             fecha, --20
                             cod_servicio, --21
                             num_servicio, --22
                             mto_comision, --23
                             num_form_sunat, --24
                             cod_laboratorio, --25
                             num_item, --26
                             des_producto, --27
                             flg_regalo --28
                             )
                        values
                            (reg_cab_nc.cod_cia, --1
                             reg_cab_nc.cod_tipo_documento, --2
                             reg_cab_nc.num_documento, --3
                             c_cod_modalidad, --4
                             reg1_nc.cod_producto, --5
                             decode(reg1_nc.flg_fraccion, '1', 0, reg1_nc.ctd_producto), --6
                             decode(reg1_nc.flg_fraccion, '1', reg1_nc.ctd_producto, 0), --7
                             reg1_nc.ctd_fracciona, --8
                             reg1_nc.pct_descuento, --9
                             0, --REVISAR DE DONDE SACARE EL KAIROS --10
                             reg1_nc.prc_unitario, --11
                             reg1_nc.prc_unitario, --12
                             reg1_nc.mto_subtotal - reg1_nc.mto_igv, --13
                             reg1_nc.mto_igv, --14
                             reg1_nc.pct_igv, --15
                             0, --16
                             reg1_nc.mto_subtotal, --17
                             null, --18
                             user, --19
                             sysdate, --20
                             null, --21
                             null, --22
                             reg1_nc.mto_subtotal * 0, --23
                             null, --24
                             (select cod_laboratorio from cmr.mae_producto_com where cod_producto = reg1_nc.cod_producto), --25
                             reg1_nc.num_item, --26
                             reg1_nc.des_producto, --27
                             '0' --28
                             );
                    exception
                        when others then
                                    raise_application_error(-20003, 'PRODUCTO:*' || reg1_nc.cod_tipo_documento||'-'||reg1_nc.num_documento||'-'||reg1_nc.cod_producto || chr(13) || sqlerrm);
                    end;
                end loop;
            end if;
        end loop;
    end if;

     ---------------------------------------------------------------------------------------------------------------------------------------------------------

     --ACA COMIENZO A INGRESAR TODA LAS FORMAS DE PAGO

     SP_GRABA_DET_FPAGO(pCodGrupo_Cia_in,pCod_local_in,C_COD_LOCAL,pNum_Ped_Vta_in);

     --ACA COMIENZO A INGRESAR TODOS LOS DATOS ADICIONALES

/*     IF C_COD_MODALIDAD=BTLPROD.PKG_CONSTANTES.CONS_MODAL_VTA_002_CONVEMP  THEN
        SP_GRABA_DATOS_ADICIONALES(pCodGrupo_Cia_in,pCod_local_in,pNum_Ped_Vta_in,V_COD_CONVENIO);
     END IF;*/

     <<fin_transaccion>>

     COMMIT;

     vRspta := 'S';
    return vRspta;
  EXCEPTION
      WHEN OTHERS THEN
        --ERIOS 14.08.2014 Control de errores
         ROLLBACK;
		 MSG_ERRM := SUBSTR(sqlerrm,1,500);
         RAISE_APPLICATION_ERROR(-20050,'SP_GRABA_DOCUMENTOS:' || MSG_ERRM);
  END;
END;


procedure sp_graba_datos_adicionales(pcodgrupo_cia_in char,
                                     pcod_local_in    char,
                                     pnum_ped_vta_in  char,
                                     pcodconvenio     cmr.mae_convenio.cod_convenio%type,
                                     pguiaexiste      char default 'N') as
    v_existe                 float := 0;
    i                        integer;
    a_cad_cod_campo          typ_arr_varchar;
    a_cad_val_campo          typ_arr_varchar;
    a_cad_cod_cia            typ_arr_varchar;
    a_cad_cod_tipo_documento typ_arr_varchar;
    a_cad_num_documento      typ_arr_varchar;
begin
    i := 0;

    begin
        select count(1)
          into v_existe
          from cmr.mae_convenio a, btlprod.rel_convenio_tipo_campo b
         where a.cod_convenio = b.cod_convenio
           and a.cod_convenio = pcodconvenio;
    end;
    begin
        i := 1;
        for reg4 in (select c.cod_campo,
                            c.descripcion_campo,
                            (select ml.cia
                               from nuevo.aux_mae_local l
                              inner join nuevo.mae_local ml
                                 on l.cod_local = ml.cod_local
                              where /*cod_local_sap between '001' and '599'*/
                              (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null)
                                and cod_local_sap = cp.cod_local) cia,
                            (select cod_tipodoc
                               from btlprod.aux_tipo_doc_conv
                              where tip_comp_pago = cp.tip_comp_pago
                                and cod_grupo_cia = cp.cod_grupo_cia) cod_tipo_documento,
                            cp.num_comp_pago num_documento
                       from btlprod.rac_con_btl_mf_ped_vta c
                      inner join btlprod.rac_vta_comp_pago cp
                         on c.cod_grupo_cia = cp.cod_grupo_cia
                        and c.cod_local = cp.cod_local
                        and c.num_ped_vta = cp.num_ped_vta
                      where c.cod_grupo_cia = pcodgrupo_cia_in
                        and c.cod_local = pcod_local_in
                        and c.num_ped_vta = pnum_ped_vta_in
                        and cod_campo not like 'D_%'
                      order by 3, 4, 5) loop
            a_cad_cod_campo(i) := reg4.cod_campo;
            a_cad_val_campo(i) := reg4.descripcion_campo;
            a_cad_cod_cia(i) := reg4.cia;
            a_cad_cod_tipo_documento(i) := reg4.cod_tipo_documento;
            if pguiaexiste = 'S' then
                a_cad_num_documento(i) := substr(reg4.num_documento, 1, 3) || 'O' || substr(reg4.num_documento, 5);
            else
                a_cad_num_documento(i) := reg4.num_documento;
            end if;
            i := i + 1;
        end loop;
    end;

    begin
        if v_existe > 0 and a_cad_cod_campo.count > 0 and
           a_cad_cod_campo(1) is not null then
            begin
                v_existe := 0;
                for x in a_cad_cod_campo.first .. a_cad_cod_campo.last loop
                    v_existe := v_existe + 1;
                    begin
                        insert into btlprod.rel_documento_vta_tipo_campo
                            (cia,
                             cod_tipo_documento,
                             num_documento,
                             cod_tipo_campo,
                             des_valor,
                             usuario,
                             fecha)
                        values
                            (a_cad_cod_cia(x),
                             a_cad_cod_tipo_documento(x),
                             a_cad_num_documento(x),
                             a_cad_cod_campo(x),
                             a_cad_val_campo(x),
                             '99999',
                             sysdate);
                    exception
                        when others then
                            raise_application_error(-20000,
                                                    chr(13) ||
                                                    'Error de Registro de Datos Adicionales ' ||
                                                    sqlerrm);
                    end;
                end loop;
                if v_existe = 0 then
                    raise_application_error(-20000,
                                            chr(13) ||
                                            'Se esperaban datos Adicionales al Convenio');
                end if;
            end;
        end if;
    end;

exception
    when others then
        raise_application_error(-20000,
                                chr(13) ||
                                'Error de Registro de Datos Adicionales' ||
                                sqlerrm);
end;


PROCEDURE SP_GRABA_DET_FPAGO(pCodGrupo_Cia_in CHAR,
                             pCod_local_in CHAR,
                             pCod_local_btl CHAR,
                             pNum_Ped_Vta_in CHAR) AS

CURSOR C_DET(pNum_Ped_Vta_in CHAR, pCodGrupo_Cia_in CHAR,pCod_local_in CHAR) IS
       SELECT
             (SELECT COD_TIPODOC FROM  BTLPROD.AUX_TIPO_DOC_CONV WHERE TIP_COMP_PAGO=R.TIP_COMP_PAGO AND COD_GRUPO_CIA=R.COD_GRUPO_CIA) COD_TIPO_DOCUMENTO,
             R.NUM_COMP_PAGO NUM_DOCUMENTO
       FROM BTLPROD.RAC_VTA_COMP_PAGO R
       WHERE R.NUM_PED_VTA=pNum_Ped_Vta_in AND R.COD_GRUPO_CIA=pCodGrupo_Cia_in AND R.COD_LOCAL=pCod_local_in;


CURSOR C_PAG(pNum_Ped_Vta_in CHAR, pCodGrupo_Cia_in CHAR,pCod_local_in CHAR) IS
       SELECT ROWNUM NUM_ITEM ,
              f.cod_forma_pago_btl cod_forma_pago_btl,
              f.cod_hijo cod_hijo,
              decode(p.tip_moneda,'01',1,'02',2) COD_MONEDA,
              p.val_tip_cambio TIPO_CAMBIO,
              p.im_pago MTO_IMPORTE,
              P.VAL_VUELTO MTO_VUELTO,
              p.im_total_pago MTO_SOLES
       FROM BTLPROD.rac_vta_forma_pago_pedido p INNER JOIN AUX_FORMA_PAGO_MF f
       ON f.cod_forma_pago_mf = p.cod_forma_pago
       WHERE p.NUM_PED_VTA = pNum_Ped_Vta_in
       AND p.COD_GRUPO_CIA = pCodGrupo_Cia_in
       AND p.COD_LOCAL = pCod_local_in;

    C_CIA                  BTLPROD.CAB_DOCUMENTO.CIA%TYPE;
    V_SECUENCIA BTLPROD.CAB_FORPAG_DOC.SEC_FORPAG_DOC%TYPE;

     BEGIN

          SELECT ML.CIA INTO C_CIA FROM NUEVO.AUX_MAE_LOCAL L INNER JOIN NUEVO.MAE_LOCAL ML
          ON L.COD_LOCAL=ML.COD_LOCAL
          WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599' */
          (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null) AND COD_LOCAL_SAP = pCod_local_in;


          V_SECUENCIA := CMR.FN_DEV_PROX_SEC('BTLPROD',
                                             'CAB_FORPAG_DOC',
                                             C_CIA,
                                             pCod_local_btl,
                                             '0');

          IF V_SECUENCIA IS NULL OR V_SECUENCIA < 1 THEN
               RAISE_APPLICATION_ERROR(-20000, CHR(13) || 'Error al Determinar Secuencia de Pagos ');
          END IF;

          --DBMS_OUTPUT.put_line(V_SECUENCIA);

          BEGIN
               INSERT INTO BTLPROD.CAB_FORPAG_DOC
                    (SEC_FORPAG_DOC)
               VALUES
                    (V_SECUENCIA);
          EXCEPTION
               WHEN OTHERS THEN
                    RAISE_APPLICATION_ERROR(-20000, CHR(13) || 'Error de registro en CAB_FORPAG_DOC: ' ||V_SECUENCIA || SQLERRM);
          END;

            --DBMS_OUTPUT.put_line('CAB_FORPAG_DOC');

          FOR REG IN C_DET(pNum_Ped_Vta_in,pCodGrupo_Cia_in,pCod_local_in) LOOP
               BEGIN
                    INSERT INTO BTLPROD.REL_FORPAG_DOC
                         (CIA,
                          COD_TIPO_DOCUMENTO,
                          NUM_DOCUMENTO,
                          SEC_FORPAG_DOC)
                    VALUES
                         (C_CIA,
                          REG.COD_TIPO_DOCUMENTO,
                          REG.NUM_DOCUMENTO,
                          V_SECUENCIA);
               EXCEPTION
                    WHEN DUP_VAL_ON_INDEX THEN
                         RAISE_APPLICATION_ERROR(-20000, CHR(13) || 'Forma de Pago ya Ingresada del documento TIPO:'||REG.COD_TIPO_DOCUMENTO||' Numero:'||REG.NUM_DOCUMENTO || SQLERRM);
                    WHEN OTHERS THEN
                         RAISE_APPLICATION_ERROR(-20000, CHR(13) || 'Error de relacion de Documento - Pago_1 ' || SQLERRM);
               END;

          END LOOP;

          --DBMS_OUTPUT.put_line('INICIO DET_FORPAG_DOC');

          FOR REG IN C_PAG(pNum_Ped_Vta_in,pCodGrupo_Cia_in,pCod_local_in) LOOP

               BEGIN



                    IF REG.COD_FORMA_PAGO_BTL IS NULL THEN
                       RAISE_APPLICATION_ERROR(-20000, 'No Existe la Forma de Pago');
                    END IF;

                    IF REG.COD_HIJO IS NULL THEN
                       RAISE_APPLICATION_ERROR(-20000, 'No Existe el hijo de la Forma de Pago');
                    END IF;

                    --RAISE_APPLICATION_ERROR(-20000,V_SECUENCIA);


                    INSERT INTO BTLPROD.DET_FORPAG_DOC
                         (SEC_FORPAG_DOC,
                          SEC_ITEM,
                          COD_FORMA_PAGO,
                          COD_HIJO,
                          COD_MONEDA,
                          IMP_TIPO_CAMBIO,
                          -------------------------------------------------------------------
                          COD_TIPO_TARJETA,
                          NUM_TARJETA,
                          FLG_CUOTA_NORMAL,
                          NUM_CUOTAS,
                          FCH_VENCIMIENTO,
                          NUM_AUTORIZACION,
                          -------------------------------------------------------------------
                          IMP_SIN_REDONDEO,
                          IMP_MONEDA_NAC,
                          IMP_DIFERENCIA,
                          IMP_VUELTO,
                          COD_ANULACION,
                          USUARIO,
                          FECHA,
                          COD_USUARIO)
                    VALUES
                         (V_SECUENCIA,
                          REG.NUM_ITEM,
                          REG.COD_FORMA_PAGO_BTL,
                          REG.COD_HIJO,
                          REG.COD_MONEDA,
                          REG.TIPO_CAMBIO,
                          -------------------------------------------------------------------
                          REG.COD_HIJO,
                          '*',
                          '0',
                          1,
                          NULL,
                          NULL,
                          -------------------------------------------------------------------
                          REG.MTO_IMPORTE,
                          REG.MTO_SOLES,
                          0,
                          REG.MTO_VUELTO,
                          NULL,
                          USER,
                          SYSDATE,
                          '99999');
               EXCEPTION
                    WHEN DUP_VAL_ON_INDEX THEN
                         RAISE_APPLICATION_ERROR(-20000, CHR(13) || 'La forma de pago esta duplicada Duplicado ');
                    WHEN OTHERS THEN
                         RAISE_APPLICATION_ERROR(-20000, CHR(13) || 'Error de Registro Formas de Pago ' || SQLERRM);
               END;

          END LOOP;

          --DBMS_OUTPUT.put_line('FIN DET_FORPAG_DOC');

END SP_GRABA_DET_FPAGO;


PROCEDURE sp_crea_cajas_liquidaciones(pCodGrupo_Cia_in CHAR,
                           pCod_local_in CHAR,
                           pNum_Ped_Vta_in CHAR)
AS
cursor c_datos_liquidaciones is
SELECT DISTINCT COD_LOCAL,COD_MAQUINA,NUM_IP,COD_CIA,COD_USUARIO,FCH_EMISION
    FROM
    (  SELECT
             (SELECT L.Cod_Local FROM NUEVO.AUX_MAE_LOCAL l WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599' */
             (COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null) AND COD_LOCAL_SAP = R.COD_LOCAL) COD_LOCAL,
             --P.IP_PC COD_MAQUINA,
             (SELECT COD_MAQUINA FROM BTLPROD.AUX_MAQUINA_X_LOCAL WHERE NUM_IP=P.IP_PC) COD_MAQUINA,
             P.IP_PC NUM_IP,
             (SELECT ML.CIA FROM NUEVO.AUX_MAE_LOCAL L INNER JOIN NUEVO.Mae_Local ML ON L.COD_LOCAL=ML.COD_LOCAL
              WHERE /*COD_LOCAL_SAP BETWEEN '001' AND '599' */(COD_LOCAL_SAP BETWEEN '001' AND '599' or fch_migracion is not null)
              AND COD_LOCAL_SAP = R.COD_LOCAL) COD_CIA,
             '99999'/*(SELECT cod_usuario  from nuevo.mae_usuario_btl a where a.num_doc_identidad =P.DNI_USU_LOCAL)*/ COD_USUARIO,
             TRUNC(R.FEC_CREA_COMP_PAGO) FCH_EMISION
      FROM BTLPROD.RAC_VTA_COMP_PAGO R INNER JOIN BTLPROD.RAC_VTA_PEDIDO_VTA_CAB P
      ON R.NUM_PED_VTA=P.NUM_PED_VTA AND R.COD_GRUPO_CIA=P.COD_GRUPO_CIA AND R.COD_LOCAL=P.COD_LOCAL
      WHERE R.NUM_PED_VTA=pNum_Ped_Vta_in AND R.COD_GRUPO_CIA=pCodGrupo_Cia_in AND R.COD_LOCAL=pCod_local_in
    );

V_CONTEO    integer:=0;
C_LIQUIDA   btlprod.aux_control_caja.cod_liquidacion%type;
V_SEC_CAJA btlprod.aux_control_caja.sec_caja%type;
V_COD_MAQUINA BTLPROD.AUX_MAQUINA_X_LOCAL.COD_MAQUINA%TYPE;
BEGIN


     FOR REG IN c_datos_liquidaciones LOOP
        --para validar que no habra liquidacion si no ha cerrado o tiene pendientes
        IF REG.COD_LOCAL IS NULL THEN
           RAISE_APPLICATION_ERROR(-20000,'EL LOCAL NO ESTA REGISTRADO EN BTL');
        END IF;

        V_COD_MAQUINA:=reg.num_ip;

         IF REG.COD_MAQUINA IS NULL THEN
           --RAISE_APPLICATION_ERROR(-20000,'LA MAQUINA NO ESTA REGISTRADA EN BTL');
            BEGIN
               INSERT INTO BTLPROD.AUX_MAQUINA_X_LOCAL
               (CIA,
                COD_LOCAL,
                COD_MAQUINA,
                COD_TIPO_MAQUINA,
                FLG_DELIVERY,
                NUM_IP,
                FLG_ACTIVO,
                COD_USUARIO,
                FCH_REGISTRA,
                USUARIO,
                FECHA,
                FLG_DELIVERY_PROV,
                COD_TIPO_DOC_DEFECT
                )
               VALUES
               (reg.COD_CIA,
                reg.cod_local,
                V_COD_MAQUINA,
                '002',
                '0',
                reg.num_ip,
                '1',
                '99999',
                sysdate,
                '99999',
                sysdate,
                '0',
                'PRO'
               );

            EXCEPTION
                     WHEN NO_DATA_FOUND THEN
                                             NULL;
                                             UPDATE BTLPROD.AUX_MAQUINA_X_LOCAL
                                             SET FCH_ACTUALIZA = SYSDATE,
                                                 COD_MAQUINA = reg.num_ip
                                             WHERE num_ip = reg.num_ip;
            WHEN OTHERS THEN
                  RAISE_APPLICATION_ERROR(-20000,'Error al registrar la Maquina: '||reg.num_ip||' , '||sqlerrm);
            END;
        END IF;

        IF REG.COD_USUARIO IS NULL THEN
           RAISE_APPLICATION_ERROR(-20000,'EL USUARIO NO ESTA REGISTRADO EN BTL');
        END IF;






        SELECT COUNT(1)
               INTO V_CONTEO
        FROM BTLPROD.AUX_CONTROL_CAJA C
        WHERE C.CIA = reg.COD_CIA
        AND C.COD_LOCAL = reg.cod_local
        AND C.FCH_INICIO  = reg.fch_emision
        AND C.FLG_ESTADO_CAJA = '0'
        AND C.COD_USUARIO = reg.COD_USUARIO
        AND C.COD_MAQUINA=V_COD_MAQUINA;

        --DBMS_OUTPUT.put_line(V_CONTEO);

        IF V_CONTEO = 0 THEN
           BEGIN

                BEGIN
                     C_LIQUIDA := CMR.FN_DEV_PROX_SEC('BTLPROD',
                                                      'CAB_LIQUIDACION',
                                                      reg.COD_CIA,
                                                      reg.COD_LOCAL,
                                                      '0');
                EXCEPTION
                         WHEN OTHERS THEN
                                         RAISE_APPLICATION_ERROR(-20000,'SE PRODUJO UN ERROR AL DETERMINAR LA SECUENCIA DE LIQUIDACION');
                END;

                IF C_LIQUIDA < 0 THEN
                   RAISE_APPLICATION_ERROR(-20000,'NO EXISTE CORRELATIVO DE LIQUIDACIONES.');
                END IF;

                <<SEC_CAJA>>
                BEGIN
                     V_SEC_CAJA := FN_SEC_CAJA;--TO_CHAR(SYSDATE, 'YYYYMMDDHH24SS');
                     --SELECT SUBSTR(REPLACE(TO_CHAR(systimestamp,'yyyymmddhh24SS.ff5'),'.',''),1,14) INTO V_SEC_CAJA FROM DUAL;
                     --DBMS_OUTPUT.put_line(V_SEC_CAJA);

                EXCEPTION
                         WHEN OTHERS THEN
                                         RAISE_APPLICATION_ERROR(-20000,'SE PRODUJO UN ERROR AL DETERMINAR LA SECUENCIA DE LA CAJA ' ||V_SEC_CAJA||';'||SQLERRM);

                END;


                BEGIN
                     INSERT INTO BTLPROD.AUX_CONTROL_CAJA
                     (CIA,
                      COD_LOCAL,
                      COD_MAQUINA,
                      SEC_CAJA,
                      COD_ADMIN_INICIO,
                      COD_ADMIN_FIN,
                      FCH_INICIO,
                      FCH_FIN,
                      FCH_HORA_INICIO,
                      FCH_HORA_FIN,
                      FLG_ESTADO_CAJA,
                      FLG_ACTIVO,
                      COD_USUARIO,
                      FCH_REGISTRA,
                      COD_USUARIO_ACTUALIZA,
                      FCH_ACTUALIZA,
                      COD_LIQUIDACION)
                     VALUES
                      (reg.COD_CIA,
                      reg.COD_LOCAL,
                      V_COD_MAQUINA,
                      V_SEC_CAJA,
                      NULL,
                      NULL,
                      reg.fch_emision,
                      NULL,
                      SYSDATE,
                      NULL,
                      '0',
                      '1',
                      reg.COD_USUARIO,
                      SYSDATE,
                      NULL,
                      NULL,
                      C_LIQUIDA);
                EXCEPTION
                         WHEN DUP_VAL_ON_INDEX THEN
                                                   GOTO SEC_CAJA;--RAISE_APPLICATION_ERROR(-20000,'Error de Insercion Control Caja ' ||SQLERRM);
                         WHEN OTHERS THEN
                                         RAISE_APPLICATION_ERROR(-20000,'Error inesperado ' ||SQLERRM);
                END;
           END;
        END IF;
     END LOOP;

     --COMMIT;

END;

--ERIOS 09.09.2014 Se agrega parametro pIndNotaCred
function sp_anula_documento(pcodtipodoc char,
                            pnumdoc     char,
                            pcodlocal   char default null,
							pIndNotaCred CHAR DEFAULT 'N') return char as
  v_conteo_doc float := 0;
  v_conteo_grl float := 0;
  v_conteo_pla float := 0;

  --ERIOS 09.09.2014 Cuando se invoca por NC, solo anula las guias.
  cursor c_datos(vcia char) is
    select *
      from btlprod.aux_sec_venta
     where (cia, sec_venta) in
           (select s.cia, s.sec_venta
              from btlprod.aux_sec_venta s
             inner join btlprod.aux_tipo_doc_conv t
                on s.cod_tipo_documento = t.cod_tipodoc
             where cia = vcia
               and t.tip_comp_pago = pcodtipodoc
               and s.num_documento = pnumdoc)
			AND ( 'N'=pIndNotaCred OR ('S'=pIndNotaCred AND COD_TIPO_DOCUMENTO = 'GRL') );

  v_estado char;
  v_cia    varchar2(2);
  v_entro  char;
  v_guia   char(3);
  -- kmoncada 08.07.14 cod local homologado en el rac.
  v_cod_local nuevo.aux_mae_local.cod_local%type;

begin

  if pcodtipodoc <> '04' then
    v_estado := 'S';
    v_entro  := 'N';

    select cia
      into v_cia
      from nuevo.mae_local
     where cod_local in (select cod_local
                           from nuevo.aux_mae_local
                          where cod_local_sap = pcodlocal);

    -- kmoncada 08.07.14 obtiene cod local en rac
    SELECT A.COD_LOCAL
    into v_cod_local
    FROM NUEVO.AUX_MAE_LOCAL A
    WHERE A.COD_LOCAL_SAP=pcodlocal;

    for reg in c_datos(v_cia) loop
      v_entro := 'S';
      if reg.cod_tipo_documento = 'GRL' then


        begin
			--ERIOS 21.08.2014 Se agrega campos de PK
         select cg.est_guia into v_guia
         from btlcero.cab_guia_cliente cg
         where   cg.CIA = v_cia
			AND cg.COD_LOCAL = v_cod_local
			AND cg.num_guia = reg.num_documento ;

         if v_guia = 'FAT' then
           raise_application_error(-20000,
                            'La GUIA '||reg.num_documento ||' se encuentra Facturada.');
         end if;
         -- KMONCADA 13.08.2014 VALIDA QUE LA GUIA NO SE ENCUENTRE DIFERENTE A ESTADO TRANSITO
		 --ERIOS 21.08.2014 Si la guia esta anulada, continua.
		 if v_guia <> 'ANU' AND  v_guia <> 'TRA' then
           raise_application_error(-20000,
                            'La GUIA '||reg.num_documento ||' no se encuentra en TRANSITO.');
         end if;
        end;

        select count(1)
          into v_conteo_grl
          from btlcero.cab_guia_cliente
         where cod_tipodoc = 'GRL'
           and cia = reg.cia
           -- kmoncada 08.07.14 se modifica campo de condicional debido
           -- campo cod_local siempre es nulo
           and cod_local = v_cod_local --pcodlocal
           --and cod_btl = v_cod_local
           and num_guia = reg.num_documento
           and est_guia <> 'TRA';
        v_conteo_doc := 0;
      else
        select count(1)
          into v_conteo_doc
          from btlprod.cab_documento
         where cia = reg.cia
           and cod_tipo_documento = reg.cod_tipo_documento
           and num_documento = reg.num_documento
           and cod_estado <> 'EMI';

        select count(1)
          into v_conteo_pla
          from btlprod.cab_documento c
         where cia = reg.cia
           and cod_tipo_documento = reg.cod_tipo_documento
           and num_documento = reg.num_documento
           and exists (select 1
                  from medco.pre_placobd
                 where cia = c.cia
                   and nro_doc = c.num_documento
                   and flag = '1' -- El documento existe y fue marcado para cobro (DJARA 18/08/2014)
                   and tip_doc = (case c.cod_tipo_documento
                         when 'FAC' then
                          'FA'
                         when 'BOL' then
                          'BV'
                         when 'TKB' then
                          'TB'
                         when 'TKF' then
                          'TF'
                         when 'NA' then
                          'NCR'
                       end));
        v_conteo_grl := 0;
      end if;

      if v_conteo_doc = 0 and v_conteo_grl = 0 and v_conteo_pla = 0 then

         if reg.cod_tipo_documento <> 'GRL' then
			update btlprod.cab_documento
			   set cod_estado = 'ANU'
			 where cia = reg.cia
			   and cod_tipo_documento = reg.cod_tipo_documento
			   and num_documento = reg.num_documento;

         else

			update btlcero.cab_guia_cliente
			   set est_guia = 'ANU'
			 where cia = reg.cia
			   and cod_tipodoc = 'GRL'
			   -- kmoncada 08.07.14 se modifica campo de condicional debido
			   -- campo cod_local siempre es nulo
			   and cod_local = v_cod_local --pcodlocal
			   --and cod_btl = v_cod_local
			   and num_guia = reg.num_documento;
         end if;
        v_estado := 'S';
      else
      -- KMONCADA 13.08.2014 VALIDA SI LA FACTURA GENERO PRE-PLANILLA NO PERMITIRA ANULAR
        IF v_conteo_pla<>0 THEN
          raise_application_error(-20000,
                            'La FACTURA '||reg.num_documento ||' genero una PRE-PLANILLA.');
        END IF;
		
		if reg.cod_tipo_documento <> 'GRL' then
		   select count(1)
			  into v_conteo_doc
			  from btlprod.cab_documento
			 where cia = reg.cia
			   and cod_tipo_documento = reg.cod_tipo_documento
			   and num_documento = reg.num_documento
			   and cod_estado = 'ANU';

		  if v_conteo_doc > 0 then
		  -- SI YA ESTA ANULADO DEJA ANULAR
			v_estado := 'S';
		  else
			v_estado := 'N';
			goto termina;
		  end if;
		  
		end if;
		
      end if;
    end loop;

    if v_entro = 'N' then
      v_estado := 'P';
    end if;

    <<termina>>

    return v_estado;
  else
    return 'N';
  end if;

exception
  when others then
    raise_application_error(-20000,
                            'Error inesperado ' || pcodtipodoc || '|' ||
                            pnumdoc || '|' || pcodlocal || chr(13) ||
                            sqlerrm);
    return 'N';
end;

  PROCEDURE LIBERAR_TRANSACCION IS
  BEGIN
    ROLLBACK;
  END;

  PROCEDURE ACEPTAR_TRANSACCION IS
  BEGIN
    COMMIT;
  END;

 FUNCTION FN_SEC_CAJA RETURN VARCHAR2 IS
 BEGIN
      RETURN TO_CHAR(SYSDATE, 'YYMMDDHH24MiSS');
 END;


END;

/
