package mifarma.transf.daemon.dao;

import org.apache.ibatis.jdbc.SQL;

public class SQLDaemonProvider {
    
    public String selectLgtNotaEsCab(){
        return selectTablaNumPedido("PTOVENTA.T_LGT_NOTA_ES_CAB");
    }
    
    public String selectLgtNotaEsDet(){
        return selectTablaNumPedido("PTOVENTA.T_LGT_NOTA_ES_DET");
    }
    
    public String selectLgtGuiaRem(){
        return selectTablaNumPedido("PTOVENTA.T_LGT_GUIA_REM");
    }
    
    private String selectTablaNumPedido(String pTabla){
        return new SQL()
            .SELECT("*")
            .FROM(pTabla)
            .WHERE("COD_GRUPO_CIA = #{cCodGrupoCia}")
            .WHERE("COD_LOCAL = #{cCodLocal}")
            .WHERE("NUM_NOTA_ES = #{cNumNotaEs}")
        .toString();
    }
    
    public String insertLgtNotaEsCab(){
        String sql = new SQL()
            .INSERT_INTO("PTOVENTA.T_LGT_NOTA_ES_CAB")
            .VALUES("cod_grupo_cia","#{cod_grupo_cia}")
            .VALUES("cod_local","#{cod_local}")
            .VALUES("num_nota_es","#{num_nota_es}")
            .VALUES("fec_nota_es_cab","#{fec_nota_es_cab}")
            .VALUES("est_nota_es_cab","#{est_nota_es_cab}")
            .VALUES("tip_doc","#{tip_doc}")
            .VALUES("num_doc","#{num_doc}")
            .VALUES("cod_origen_nota_es","#{cod_origen_nota_es}")
            .VALUES("cant_items","#{cant_items}")
            .VALUES("val_total_nota_es_cab","#{val_total_nota_es_cab}")
            .VALUES("cod_destino_nota_es","#{cod_destino_nota_es}")
            .VALUES("desc_empresa","#{desc_empresa}")
            .VALUES("ruc_empresa","#{ruc_empresa}")
            .VALUES("dir_empresa","#{dir_empresa}")
            .VALUES("desc_trans","#{desc_trans}")
            .VALUES("ruc_trans","#{ruc_trans}")
            .VALUES("dir_trans","#{dir_trans}")
            .VALUES("placa_trans","#{placa_trans}")
            .VALUES("tip_nota_es","#{tip_nota_es}")
            .VALUES("tip_origen_nota_es","#{tip_origen_nota_es}")
            .VALUES("tip_mot_nota_es","#{tip_mot_nota_es}")
            .VALUES("est_recepcion","#{est_recepcion}")
            .VALUES("usu_crea_nota_es_cab","#{usu_crea_nota_es_cab}")
            .VALUES("fec_crea_nota_es_cab","#{fec_crea_nota_es_cab}")
            .VALUES("usu_mod_nota_es_cab","#{usu_mod_nota_es_cab}")
            .VALUES("fec_mod_nota_es_cab","#{fec_mod_nota_es_cab}")
            .VALUES("ind_nota_impresa","#{ind_nota_impresa}")
            .VALUES("fec_proceso_sap","#{fec_proceso_sap}")
            .VALUES("ind_trans_automatica","#{ind_trans_automatica}")
            .toString();
        return sql;
    }
    
    public String insertLgtNotaEsDet(){
        String sql = new SQL()
            .INSERT_INTO("PTOVENTA.T_LGT_NOTA_ES_DET")
            .VALUES("cod_grupo_cia","#{cod_grupo_cia}")
            .VALUES("cod_local","#{cod_local}")
            .VALUES("num_nota_es","#{num_nota_es}")
            .VALUES("sec_det_nota_es","#{sec_det_nota_es}")
            .VALUES("cod_prod","#{cod_prod}")
            .VALUES("sec_guia_rem","#{sec_guia_rem}")
            .VALUES("val_prec_unit","#{val_prec_unit}")
            .VALUES("val_prec_total","#{val_prec_total}")
            .VALUES("cant_mov","#{cant_mov}")
            .VALUES("val_frac","#{val_frac}")
            .VALUES("est_nota_es_det","#{est_nota_es_det}")
            .VALUES("fec_nota_es_det","#{fec_nota_es_det}")
            .VALUES("desc_unid_vta","#{desc_unid_vta}")
            .VALUES("fec_vcto_prod","#{fec_vcto_prod}")
            .VALUES("num_lote_prod","#{num_lote_prod}")
            .VALUES("cant_enviada_matr","#{cant_enviada_matr}")
            .VALUES("num_pag_recep","#{num_pag_recep}")
            .VALUES("ind_prod_afec","#{ind_prod_afec}")
            .VALUES("usu_crea_nota_es_det","#{usu_crea_nota_es_det}")
            .VALUES("fec_crea_nota_es_det","#{fec_crea_nota_es_det}")
            .VALUES("usu_mod_nota_es_det","#{usu_mod_nota_es_det}")
            .VALUES("fec_mod_nota_es_det","#{fec_mod_nota_es_det}")
            .VALUES("fec_proceso_sap","#{fec_proceso_sap}")
            .VALUES("posicion","#{posicion}")
            .VALUES("num_entrega","#{num_entrega}")    
            .toString();
        return sql;
    }
    
    public String insertLgtGuiaRem(){
        String sql = new SQL()
            .INSERT_INTO("PTOVENTA.T_LGT_GUIA_REM")
            .VALUES("cod_grupo_cia","#{cod_grupo_cia}")
            .VALUES("cod_local","#{cod_local}")
            .VALUES("num_nota_es","#{num_nota_es}")
            .VALUES("sec_guia_rem","#{sec_guia_rem}")
            .VALUES("num_guia_rem","#{num_guia_rem}")
            .VALUES("fec_crea_guia_rem","#{fec_crea_guia_rem}")
            .VALUES("usu_crea_guia_rem","#{usu_crea_guia_rem}")
            .VALUES("fec_mod_guia_rem","#{fec_mod_guia_rem}")
            .VALUES("usu_mod_guia_rem","#{usu_mod_guia_rem}")
            .VALUES("est_guia_rem","#{est_guia_rem}")
            .VALUES("num_entrega","#{num_entrega}")
            .VALUES("ind_guia_cerrada","#{ind_guia_cerrada}")
            .VALUES("ind_guia_impresa","#{ind_guia_impresa}")
            .toString();
        return sql;
    }
    
}
