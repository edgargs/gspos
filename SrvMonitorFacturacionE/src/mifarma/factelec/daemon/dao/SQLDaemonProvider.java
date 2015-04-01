package mifarma.factelec.daemon.dao;

import org.apache.ibatis.jdbc.SQL;

public class SQLDaemonProvider {
    
    public String selectMonVtaCompPagoE(){
        return new SQL()
            .SELECT("*")
            .FROM("PTOVENTA.MON_VTA_COMP_PAGO_E")
            .WHERE("COD_GRUPO_CIA = #{cCodGrupoCia}")
            .WHERE("COD_CIA = #{cCodCia}")
            .WHERE("ESTADO = 'R'")
            .WHERE("ROWNUM <= #{nCantidad}")
        .toString();
    }
    
    public String updateMonVtaCompPagoE(){
        return new SQL()
            .UPDATE("PTOVENTA.MON_VTA_COMP_PAGO_E")
            .SET("estado = #{estado}")
            .SET("codigo = #{codigo}")
            .SET("mensaje = #{mensaje}")
            .SET("usu_mod = #{usu_mod}")
            .SET("fec_mod = #{fec_mod}")
            .WHERE("cod_grupo_cia = #{cod_grupo_cia}")
            .WHERE("cod_cia = #{cod_cia}") 
            .WHERE("cod_local = #{cod_local}")
            .WHERE("num_ped_vta = #{num_ped_vta}")
            .WHERE("sec_comp_pago = #{sec_comp_pago}")                  
        .toString();
    }
    
    public String selectRucCia(){
        return new SQL()
            .SELECT("NUM_RUC_CIA")
            .FROM("PTOVENTA.PBL_CIA")
            .WHERE("COD_GRUPO_CIA = #{cCodGrupoCia}")
            .WHERE("COD_CIA = #{cCodCia}")
        .toString();
    }
    
    public String selectLocales(){
        return new SQL()
            .SELECT_DISTINCT("COD_GRUPO_CIA codGrupoCia")
            .SELECT_DISTINCT("COD_CIA codCia")
            .SELECT_DISTINCT("COD_LOCAL codLocal")
            .SELECT_DISTINCT("IP_SERVIDOR_LOCAL ipServidorLocal")
            .FROM("PTOVENTA.MON_VTA_COMP_PAGO_E")
            .INNER_JOIN("PTOVENTA.PBL_LOCAL USING (COD_GRUPO_CIA,COD_LOCAL,COD_CIA)")
            .WHERE("COD_GRUPO_CIA = #{cCodGrupoCia}")
            .WHERE("COD_CIA = #{cCodCia}")
            .WHERE("ESTADO = 'C'")
            .WHERE("ROWNUM <= #{nCantidad}")
        .toString();
    }
    
    public String deleteMonVtaCompPagoE(){
        return new SQL()
            .DELETE_FROM("PTOVENTA.MON_VTA_COMP_PAGO_E")
            .WHERE("COD_GRUPO_CIA = #{cCodGrupoCia}")
            .WHERE("COD_CIA = #{cCodCia}")
            .WHERE("COD_LOCAL = #{cCodLocal}")
            .WHERE("NUM_PED_VTA = #{cNumPedVta}")
            .WHERE("SEC_COMP_PAGO = #{cSecCompPago}")
        .toString();
    }
    
    public String insertMonVtaCompPagoE(){
        return new SQL()
            .INSERT_INTO("PTOVENTA.MON_VTA_COMP_PAGO_E")
            .VALUES("cod_grupo_cia","#{cod_grupo_cia}")
            .VALUES("cod_cia","#{cod_cia}") 
            .VALUES("cod_local","#{cod_local}")
            .VALUES("num_ped_vta","#{num_ped_vta}")
            .VALUES("sec_comp_pago","#{sec_comp_pago}")
            .VALUES("tip_doc_sunat","#{tip_doc_sunat}")
            .VALUES("num_comp_pago_e","#{num_comp_pago_e}")
            .VALUES("estado","#{estado}")
            .VALUES("codigo","#{codigo}")
            .VALUES("mensaje","#{mensaje}")
            .VALUES("usu_crea","#{usu_crea}")
            .VALUES("fec_crea","#{fec_crea}")
            .VALUES("usu_mod","#{usu_mod}")
            .VALUES("fec_mod","#{fec_mod}")
            .VALUES("ind_valida","#{ind_valida}")
        .toString();
    }
    
    public String selectMonVtaCompPagoELocal(){
        return new SQL()
            .SELECT("*")
            .FROM("PTOVENTA.MON_VTA_COMP_PAGO_E")
            .WHERE("COD_GRUPO_CIA = #{cCodGrupoCia}")
            .WHERE("COD_CIA = #{cCodCia}")
            .WHERE("COD_LOCAL = #{cCodLocal}")
            .WHERE("ESTADO = 'C'")
            .WHERE("ROWNUM <= #{nCantidad}")
        .toString();
    }
    
    public String updateMonVtaCompPagoEPend(){
        return new SQL()
            .UPDATE("PTOVENTA.MON_VTA_COMP_PAGO_E")
            .SET("estado = 'P'")
            .SET("usu_mod = 'DOC_PEND_ENV'")
            .SET("fec_mod = SYSDATE")
            .WHERE("cod_grupo_cia = #{cCodGrupoCia}")
            .WHERE("cod_cia = #{cCodCia}") 
            .WHERE("cod_local = #{cCodLocal}")
            .WHERE("ESTADO = 'C'")
        .toString();
    }
    
    public String mergeMonVtaCompPagoE(){
        StringBuilder strBuilder = new StringBuilder();
        
        strBuilder.append(" MERGE INTO PTOVENTA.MON_VTA_COMP_PAGO_E M USING DUAL ON (M.cod_grupo_cia = #{cod_grupo_cia}");
        strBuilder.append(" AND M.cod_cia = #{cod_cia}");
        strBuilder.append(" AND M.cod_local = #{cod_local}");
        strBuilder.append(" AND M.num_ped_vta = #{num_ped_vta}");
        strBuilder.append(" AND M.sec_comp_pago = #{sec_comp_pago})");
        strBuilder.append(" WHEN MATCHED THEN");
        strBuilder.append(" UPDATE SET");
        strBuilder.append(" M.tip_doc_sunat = #{tip_doc_sunat},");
        strBuilder.append(" M.num_comp_pago_e = #{num_comp_pago_e},");
        strBuilder.append(" M.estado = #{estado},");
        strBuilder.append(" M.codigo = #{codigo},");
        strBuilder.append(" M.mensaje = #{mensaje},");
        strBuilder.append(" M.usu_crea = #{usu_crea},");
        strBuilder.append(" M.fec_crea = #{fec_crea},");
        strBuilder.append(" M.usu_mod = #{usu_mod},");
        strBuilder.append(" M.fec_mod = #{fec_mod},");
        strBuilder.append(" M.ind_valida = #{ind_valida}");
        strBuilder.append(" WHEN NOT MATCHED THEN");
        strBuilder.append(" INSERT(cod_grupo_cia,cod_cia,cod_local,num_ped_vta,sec_comp_pago,tip_doc_sunat,num_comp_pago_e,estado,codigo,mensaje,usu_crea,fec_crea,usu_mod,fec_mod,ind_valida)");
        strBuilder.append(" VALUES(#{cod_grupo_cia},#{cod_cia},#{cod_local},#{num_ped_vta},#{sec_comp_pago},#{tip_doc_sunat},#{num_comp_pago_e},#{estado},#{codigo},#{mensaje},#{usu_crea},#{fec_crea},#{usu_mod},#{fec_mod},#{ind_valida})");
        
        return strBuilder.toString();
    }
    
}
