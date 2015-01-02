CREATE OR REPLACE PACKAGE PTOVENTA."PTOVTA_MATRIZ_CONV_BTLMF" AS

  PANTALLA_ABA_PIXEL_ALTO  VARCHAR2(30) := 'PANT_ABA_PIX_ALTO';
  PANTALLA_ABA_PIXEL_ANCHO VARCHAR2(30) := 'PANT_ABA_PIX_ANCHO';
  PANTALLA_DER_PIXEL_ALTO  VARCHAR2(30) := 'PANT_DER_PIX_ALTO';
  PANTALLA_DER_PIXEL_ANCHO VARCHAR2(30) := 'PANT_DER_PIX_ANCHO';
  PANTALLA_IZQ_PIXEL_ALTO  VARCHAR2(30) := 'PANT_IZQ_PIX_ALTO';
  PANTALLA_IZQ_PIXEL_ANCHO VARCHAR2(30) := 'PANT_IZQ_PIX_ANCHO';

  COD_DATO_CONV_REPARTIDOR  VARCHAR2(10) := 'D_001';
  COD_DATO_CONV_MEDICO      VARCHAR2(10) := 'D_005';
  COD_DATO_CONV_ORIG_RECETA VARCHAR2(10) := 'D_002';

  COD_DATO_CONV_DIAGNOSTICO_UIE VARCHAR2(10) := 'D_004';
  COD_DATO_CONV_BENIFICIARIO    VARCHAR2(10) := 'D_000';

  OBJ_IN_TEXTO       VARCHAR2(30) := 'INGRESO_TEXTO';
  OBJ_LISTA_PANTALLA VARCHAR2(30) := 'LISTA_PANTALLA';
  OBJ_LISTA_COMBO    VARCHAR2(30) := 'LISTA_COMBO';

  CONS_NRO_RESOLUCION VARCHAR2(20) := '800x600';

  FLG_RETENCION CHAR(1) := '0';

  ---VARIABLES JQUISPE 23.12.2011
  C_ESTADO_ACTIVO CHAR(1) := 'A';

  TYPE TYP_ARR_VARCHAR is table of VARCHAR2(100) index by PLS_INTEGER;

  FUNCTION CON_AGREGA_DATOS_TMP(cCodGrupoCia_in CHAR,
                              cCodLocal_in    CHAR,
                              cNumPedVta_in   CHAR) RETURN CHAR;

END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVTA_MATRIZ_CONV_BTLMF" AS
FUNCTION CON_AGREGA_DATOS_TMP(cCodGrupoCia_in CHAR,
                              cCodLocal_in    CHAR,
                              cNumPedVta_in   CHAR) RETURN CHAR
IS
PRAGMA AUTONOMOUS_TRANSACTION;

/*rspta CHAR(1);*/
vCADENA VARCHAR2(30000);

existeNroPedido RAC_VTA_PEDIDO_VTA_CAB.Num_Ped_Vta%TYPE;


BEGIN


       BEGIN

           SELECT DISTINCT CAB.NUM_PED_VTA
             INTO existeNroPedido
             FROM RAC_VTA_PEDIDO_VTA_CAB CAB
            WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND CAB.COD_LOCAL     = cCodLocal_in
              AND CAB.NUM_PED_VTA   = cNumPedVta_in;

             EXCEPTION
              WHEN NO_DATA_FOUND THEN
              existeNroPedido := NULL;
       END;


      IF   existeNroPedido IS NOT NULL THEN

           DELETE FROM RAC_VTA_PEDIDO_VTA_CAB CAB WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                                                    AND CAB.COD_LOCAL     = cCodLocal_in
                                                    AND CAB.NUM_PED_VTA   = cNumPedVta_in;

           DELETE FROM RAC_VTA_PEDIDO_VTA_DET DET WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
                                                    AND DET.COD_LOCAL     = cCodLocal_in
                                                    AND DET.NUM_PED_VTA   = cNumPedVta_in;


           DELETE FROM RAC_VTA_FORMA_PAGO_PEDIDO FPAGO WHERE FPAGO.COD_GRUPO_CIA = cCodGrupoCia_in
                                                          AND FPAGO.COD_LOCAL     = cCodLocal_in
                                                          AND FPAGO.NUM_PED_VTA   = cNumPedVta_in;

           DELETE FROM RAC_VTA_COMP_PAGO CPAGO WHERE CPAGO.COD_GRUPO_CIA = cCodGrupoCia_in
                                                 AND CPAGO.COD_LOCAL     = cCodLocal_in
                                                 AND CPAGO.NUM_PED_VTA   = cNumPedVta_in;

           DELETE FROM RAC_CON_BTL_MF_PED_VTA PED WHERE PED.COD_GRUPO_CIA = cCodGrupoCia_in
                                                    AND PED.COD_LOCAL     = cCodLocal_in
                                                    AND PED.NUM_PED_VTA   = cNumPedVta_in;

      END IF;



/*  rspta := 'N';*/


            vCADENA := ' BEGIN ';
            vCADENA:=vCADENA ||
                    ' INSERT INTO RAC_VTA_PEDIDO_VTA_CAB '||
                    '(cod_grupo_cia,cod_local,num_ped_vta,cod_cli_local,sec_mov_caja,fec_ped_vta,val_bruto_ped_vta,'||
                    'val_neto_ped_vta,val_redondeo_ped_vta,val_igv_ped_vta,val_dcto_ped_vta,tip_ped_vta,val_tip_cambio_ped_vta,'||
                    'num_ped_diario,cant_items_ped_vta,est_ped_vta,tip_comp_pago,nom_cli_ped_vta,'||
                    'dir_cli_ped_vta,ruc_cli_ped_vta,usu_crea_ped_vta_cab,fec_crea_ped_vta_cab,'||
                    'usu_mod_ped_vta_cab,fec_mod_ped_vta_cab,ind_pedido_anul,ind_distr_gratuita,'||
                    'cod_local_atencion,num_ped_vta_origen,obs_forma_pago,obs_ped_vta,cod_dir,'||
                    'num_telefono,fec_ruteo_ped_vta_cab,fec_salida_local,fec_entrega_ped_vta_cab,'||
                    'fec_retorno_local,cod_ruteador,cod_motorizado,ind_deliv_automatico,num_ped_rec,'||
                    'ind_conv_enteros,ind_ped_convenio,cod_convenio,num_pedido_delivery,cod_local_procedencia,'||
                    'ip_pc,cod_rpta_recarga,ind_fid,motivo_anulacion,dni_cli,ind_camp_acumulada,fec_ini_cobro,'||
                    'fec_fin_cobro,sec_usu_local,punto_llegada,ind_fp_fid_efectivo,ind_fp_fid_tarjeta,'||
                    'cod_fp_fid_tarjeta,cod_cli_conv,cod_barra_conv,ind_conv_btl_mf,name_pc_cob_ped,ip_cob_ped,'||
                    'dni_usu_local,fec_proceso_rac,fecha_proceso_anula_rac) '||
                    'SELECT cod_grupo_cia,cod_local,num_ped_vta,cod_cli_local,sec_mov_caja,fec_ped_vta,val_bruto_ped_vta,'||
                             'val_neto_ped_vta,val_redondeo_ped_vta,val_igv_ped_vta,val_dcto_ped_vta,tip_ped_vta,'||
                             'val_tip_cambio_ped_vta,num_ped_diario,cant_items_ped_vta,est_ped_vta,tip_comp_pago,'||
                             'nom_cli_ped_vta,dir_cli_ped_vta,ruc_cli_ped_vta,usu_crea_ped_vta_cab,fec_crea_ped_vta_cab,'||
                             'usu_mod_ped_vta_cab,fec_mod_ped_vta_cab,ind_pedido_anul,ind_distr_gratuita,cod_local_atencion,'||
                             'num_ped_vta_origen,obs_forma_pago,obs_ped_vta,cod_dir,num_telefono,fec_ruteo_ped_vta_cab,'||
                             'fec_salida_local,fec_entrega_ped_vta_cab,fec_retorno_local,cod_ruteador,cod_motorizado,'||
                             'ind_deliv_automatico,num_ped_rec,ind_conv_enteros,ind_ped_convenio,cod_convenio,'||
                             'num_pedido_delivery,cod_local_procedencia,ip_pc,cod_rpta_recarga,ind_fid,'||
                             'motivo_anulacion,dni_cli,ind_camp_acumulada,fec_ini_cobro,fec_fin_cobro,sec_usu_local,'||
                             'punto_llegada,ind_fp_fid_efectivo,ind_fp_fid_tarjeta,cod_fp_fid_tarjeta,cod_cli_conv,'||
                             'cod_barra_conv,ind_conv_btl_mf,name_pc_cob_ped,ip_cob_ped,dni_usu_local,fec_proceso_rac,'||
                             'fecha_proceso_anula_rac '||
                        'FROM ptoventa.VTA_PEDIDO_VTA_CAB@xe_'||cCodLocal_in||' C '||
                       'WHERE C.COD_GRUPO_CIA = '||cCodGrupoCia_in ||' '||
                         'AND C.COD_LOCAL = '||cCodLocal_in ||' '||
                         'AND C.NUM_PED_VTA = '||cNumPedVta_in ||' ; ';




            vCADENA:= vCADENA || ' INSERT INTO  RAC_VTA_PEDIDO_VTA_DET '||
            '(cod_grupo_cia,cod_local,num_ped_vta,sec_ped_vta_det,cod_prod,cant_atendida,'||
            'val_prec_vta,val_prec_total,porc_dcto_1,porc_dcto_2,porc_dcto_3,porc_dcto_total,'||
            'est_ped_vta_det,val_total_bono,val_frac,sec_comp_pago,sec_usu_local,usu_crea_ped_vta_det,'||
            'fec_crea_ped_vta_det,usu_mod_ped_vta_det,fec_mod_ped_vta_det,val_prec_lista,val_igv,'||
            'unid_vta,ind_exonerado_igv,sec_grupo_impr,cant_usada_nc,sec_comp_pago_origen,num_lote_prod,'||
            'fec_proceso_guia_rd,desc_num_tel_rec,val_num_trace,val_cod_aprobacion,desc_num_tarj_virtual,'||
            'val_num_pin,fec_vencimiento_lote,val_prec_public,ind_calculo_max_min,fec_exclusion,fecha_tx,'||
            'hora_tx,cod_prom,ind_origen_prod,val_frac_local,cant_frac_local,cant_xdia_tra,cant_dias_tra,'||
            'ind_zan,val_prec_prom,datos_imp_virtual,cod_camp_cupon,ahorro,porc_dcto_calc,porc_zan,'||
            'ind_prom_automatico,ahorro_pack,porc_dcto_calc_pack,cod_grupo_rep,cod_grupo_rep_edmundo,'||
            'sec_respaldo_stk,num_comp_pago,sec_comp_pago_benef,sec_comp_pago_empre) '||
            'SELECT '||
            'cod_grupo_cia,cod_local,num_ped_vta,sec_ped_vta_det,cod_prod,cant_atendida,'||
            'val_prec_vta,val_prec_total,porc_dcto_1,porc_dcto_2,porc_dcto_3,porc_dcto_total,'||
            'est_ped_vta_det,val_total_bono,val_frac,sec_comp_pago,sec_usu_local,usu_crea_ped_vta_det,'||
            'fec_crea_ped_vta_det,usu_mod_ped_vta_det,fec_mod_ped_vta_det,val_prec_lista,val_igv,'||
            'unid_vta,ind_exonerado_igv,sec_grupo_impr,cant_usada_nc,sec_comp_pago_origen,num_lote_prod,'||
            'fec_proceso_guia_rd,desc_num_tel_rec,val_num_trace,val_cod_aprobacion,desc_num_tarj_virtual,'||
            'val_num_pin,fec_vencimiento_lote,val_prec_public,ind_calculo_max_min,fec_exclusion,'||
            'fecha_tx,hora_tx,cod_prom,ind_origen_prod,val_frac_local,cant_frac_local,cant_xdia_tra,'||
            'cant_dias_tra,ind_zan,val_prec_prom,datos_imp_virtual,cod_camp_cupon,ahorro,'||
            'porc_dcto_calc,porc_zan,ind_prom_automatico,ahorro_pack,porc_dcto_calc_pack,'||
            'cod_grupo_rep,cod_grupo_rep_edmundo,sec_respaldo_stk,num_comp_pago,sec_comp_pago_benef,'||
            'sec_comp_pago_empre '||
                'FROM ptoventa.VTA_PEDIDO_VTA_DET_TEMP@xe_'||cCodLocal_in||' C '||
               'WHERE C.COD_GRUPO_CIA = '||cCodGrupoCia_in||' '||
                 'AND C.COD_LOCAL = '||cCodLocal_in||' '||
                 'AND C.NUM_PED_VTA = '||cNumPedVta_in||' ; ';


            vCADENA:= vCADENA || ' INSERT INTO RAC_VTA_COMP_PAGO '||
            '(COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_COMP_PAGO,TIP_COMP_PAGO,NUM_COMP_PAGO	,'||
            'SEC_MOV_CAJA,SEC_MOV_CAJA_ANUL,CANT_ITEM,COD_CLI_LOCAL,NOM_IMPR_COMP,'||
            'DIREC_IMPR_COMP,NUM_DOC_IMPR,VAL_BRUTO_COMP_PAGO,VAL_NETO_COMP_PAGO,'||
            'VAL_DCTO_COMP_PAGO,VAL_AFECTO_COMP_PAGO,VAL_IGV_COMP_PAGO,VAL_REDONDEO_COMP_PAGO,'||
            'PORC_IGV_COMP_PAGO,USU_CREA_COMP_PAGO,FEC_CREA_COMP_PAGO,USU_MOD_COMP_PAGO,'||
            'FEC_MOD_COMP_PAGO,FEC_ANUL_COMP_PAGO,IND_COMP_ANUL,NUM_PEDIDO_ANUL,'||
            'NUM_SEC_DOC_SAP,FEC_PROCESO_SAP,NUM_SEC_DOC_SAP_ANUL,FEC_PROCESO_SAP_ANUL,'||
            'IND_RECLAMO_NAVSAT,VAL_DCTO_COMP,MOTIVO_ANULACION,FECHA_COBRO,FECHA_ANULACION,'||
            'FECH_IMP_COBRO,FECH_IMP_ANUL,TIP_CLIEN_CONVENIO,VAL_COPAGO_COMP_PAGO,VAL_IGV_COMP_COPAGO,'||
            'NUM_COMP_COPAGO_REF,IND_AFECTA_KARDEX,PCT_BENEFICIARIO,PCT_EMPRESA,'||
            'IND_COMP_CREDITO,TIP_COMP_PAGO_REF) '||
            'SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_COMP_PAGO,TIP_COMP_PAGO,'||
            'NUM_COMP_PAGO,SEC_MOV_CAJA,SEC_MOV_CAJA_ANUL,CANT_ITEM,'||
            'COD_CLI_LOCAL,NOM_IMPR_COMP,DIREC_IMPR_COMP,NUM_DOC_IMPR,'||
            'VAL_BRUTO_COMP_PAGO,VAL_NETO_COMP_PAGO,VAL_DCTO_COMP_PAGO,VAL_AFECTO_COMP_PAGO,'||
            'VAL_IGV_COMP_PAGO,VAL_REDONDEO_COMP_PAGO,PORC_IGV_COMP_PAGO,'||
            'USU_CREA_COMP_PAGO,FEC_CREA_COMP_PAGO,USU_MOD_COMP_PAGO,FEC_MOD_COMP_PAGO,'||
            'FEC_ANUL_COMP_PAGO,IND_COMP_ANUL,NUM_PEDIDO_ANUL,NUM_SEC_DOC_SAP,'||
            'FEC_PROCESO_SAP,NUM_SEC_DOC_SAP_ANUL,FEC_PROCESO_SAP_ANUL,IND_RECLAMO_NAVSAT,'||
            'VAL_DCTO_COMP,MOTIVO_ANULACION,FECHA_COBRO,FECHA_ANULACION,'||
            'FECH_IMP_COBRO,FECH_IMP_ANUL,TIP_CLIEN_CONVENIO,VAL_COPAGO_COMP_PAGO,'||
            'VAL_IGV_COMP_COPAGO,NUM_COMP_COPAGO_REF,IND_AFECTA_KARDEX,'||
            'PCT_BENEFICIARIO,PCT_EMPRESA,C.IND_COMP_CREDITO,C.TIP_COMP_PAGO_REF '||
            'FROM ptoventa.VTA_COMP_PAGO_TEMP@xe_'||cCodLocal_in||' C '||
              'WHERE C.COD_GRUPO_CIA = '|| cCodGrupoCia_in ||' '||
                'AND C.COD_LOCAL = '|| cCodLocal_in||' '||
                'AND C.NUM_PED_VTA = '|| cNumPedVta_in||' ; ';


            vCADENA:= vCADENA || ' INSERT INTO RAC_VTA_FORMA_PAGO_PEDIDO '||
            '(COD_GRUPO_CIA,COD_LOCAL,COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA, '||
            'VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,NUM_TARJ,FEC_VENC_TARJ, '||
            'NOM_TARJ,FEC_CREA_FORMA_PAGO_PED,USU_CREA_FORMA_PAGO_PED, '||
            'FEC_MOD_FORMA_PAGO_PED,USU_MOD_FORMA_PAGO_PED,CANT_CUPON, '||
            'TIPO_AUTORIZACION,COD_LOTE,COD_AUTORIZACION,DNI_CLI_TARJ) '||
            'SELECT COD_GRUPO_CIA,COD_LOCAL,COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA, '||
            'VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,NUM_TARJ,FEC_VENC_TARJ, '||
            'NOM_TARJ,FEC_CREA_FORMA_PAGO_PED,USU_CREA_FORMA_PAGO_PED, '||
            'FEC_MOD_FORMA_PAGO_PED,USU_MOD_FORMA_PAGO_PED,CANT_CUPON, '||
            'TIPO_AUTORIZACION,COD_LOTE,COD_AUTORIZACION,DNI_CLI_TARJ '||
                'FROM ptoventa.VTA_FORMA_PAGO_PEDIDO_TEMP@xe_'||cCodLocal_in||' C '||
               'WHERE C.COD_GRUPO_CIA = '|| cCodGrupoCia_in ||' '||
                 'AND C.COD_LOCAL = '|| cCodLocal_in||' '||
                 'AND C.NUM_PED_VTA = '|| cNumPedVta_in||' ; ';


            vCADENA:= vCADENA || ' INSERT INTO RAC_CON_BTL_MF_PED_VTA '||
            '(COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,COD_CAMPO,COD_CONVENIO,COD_CLIENTE, '||
            'FEC_CREA_PED_VTA_CLI,USU_CREA_PED_VTA_CLI,FEC_MOD_PED_VTA_CLI,USU_MOD_PED_VTA_CLI, '||
            'DESCRIPCION_CAMPO,NOMBRE_CAMPO,FLG_IMPRIME,COD_VALOR_IN) '||
              'SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,COD_CAMPO,COD_CONVENIO,COD_CLIENTE, '||
                    'FEC_CREA_PED_VTA_CLI,USU_CREA_PED_VTA_CLI,FEC_MOD_PED_VTA_CLI,USU_MOD_PED_VTA_CLI ,'||
                    'DESCRIPCION_CAMPO,NOMBRE_CAMPO,FLG_IMPRIME,COD_VALOR_IN '||
                'FROM ptoventa.CON_BTL_MF_PED_VTA@xe_'||cCodLocal_in||' C '||
               'WHERE C.COD_GRUPO_CIA =  '|| cCodGrupoCia_in ||' '||
                 'AND C.COD_LOCAL = '|| cCodLocal_in||' '||
                 'AND C.NUM_PED_VTA = '|| cNumPedVta_in||' ;';

            vCADENA := vCADENA || ' COMMIT; END;';

                 execute immediate vCADENA;


     RETURN 'S';

EXCEPTION
   WHEN OTHERS THEN
      ROLLBACK;
         RAISE_APPLICATION_ERROR(-20030,
            'ERROR AL GRABAR TABLAS TEMPORALES' ||SQLERRM);
      RETURN   'N';

END;
END;
/

