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
}
