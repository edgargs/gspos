package mifarma.ptoventa.caja.dao;

import java.util.Map;

import oracle.sql.CHAR;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MapperCaja.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      16.07.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public interface MapperCaja {
    
    
    /**
     * REGISTRAR UNA RECARGA PARA SER PROCESADA POR EL SIX
     * @author GFonseca
     * @since 12.08.2013
     * @param mapParametros
     */     
    @Select(value =
            "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " +
            "ADMCENTRAL_RECAUDACION.RCD_REGTR_RECARGA(" +
                    "#{cCodGrupoCia_in}," +
                    "#{cCod_Cia_in}," +
                    "#{cCod_Local_in}," +
            
                    "#{cTip_Msj_in}," +     
                    "#{cEst_Trscc_in}," + 
                    "#{cTipo_Trssc_in}," + 
            
                    "#{cTipo_Rcd_in}," +            
                    "#{cMonto_in}," +
                    "#{cTerminal_in}," +
            
                    "#{cComercio_in}," +
                    "#{cUbicacion_in}," +
                    "#{cTelefono_in}," +
                    
                    "#{cNumPedido_in}," + 
                    "#{cUsu_Crea_in}" +     
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void registrarTrsscRecarga(Map mapParametros);
    
    
    /**
     * OBTENER EL ESTADO DE UNA RECARGA PARA MOSTRAR SUS DATOS
     * @author GFonseca
     * @since 14.08.2013
     * @param mapParametros
     */     
    @Select(value =
            "{call  #{tmpEst, mode=OUT, jdbcType=CHAR} := " +
            "ADMCENTRAL_RECAUDACION.RCD_OBT_ESTADO_TRSSC_RECARGA(" +
                    "#{cCodGrupoCia_in}," +
                    "#{cCod_Cia_in}," +
                    "#{cCod_Local_in}," +
                    "#{cNum_Pedido_in}" +  
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerEstTrsscRecarga(Map mapParametros);  
    
    
    /**
     * OBTENER LA DESCRIPCION DE ERRORES DEL SIX
     * @author GFonseca
     * @since 14.08.2013
     * @param mapParametros
     */         
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " +
                  "ADMCENTRAL_RECAUDACION.GET_DESC_MSJ_ERROR_SIX(" +
                  "#{cCod_error_six}" +
                  ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerDescErrorSix(Map mapParametros);      
    
     /**
      * REGISTRAR UNA VENTA CON TARJETA CMR
      * @author GFonseca
      * @since 31.07.2013
      * @param mapParametros
      */     
     @Select(value =
             "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " +
             "ADMCENTRAL_RECAUDACION.RCD_REGTR_VENTA_CMR(" +
                     "#{cCodGrupoCia_in}," +
                     "#{cCod_Cia_in}," +
                     "#{cCod_Local_in}," +
             
                     "#{cTip_Msj_in}," +     
                     "#{cEst_Trscc_in}," + 
                     "#{cTipo_Trssc_in}," + 
             
                     "#{cTipo_Rcd_in}," +            
                     "#{cNro_Trjt_in}," +
                     "#{cMonto_in}," +
             
                     "#{cTerminal_in}," +
                     "#{cComercio_in}," +
                     "#{cUbicacion_in}," +
             
                     "#{cNro_Cuotas_in}," +
                     "#{cId_cajero_in}," +
                     "#{cNro_Doc}," +
                     "#{cUsu_Crea_in}" +
                     ")}")
     @Options(statementType = StatementType.CALLABLE)
     void registrarTrsscVentaCMR(Map mapParametros);
           
     
    /**
     * OBTIENE LAS OPCIONES BLOQUEADAS DEL SISTEMA
     * @author CVILCA
     * @since 18.10.2013
     * @param mapParametros
     */ 
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " +
                  "PTOVENTA_GRAL.GET_OPCION_BLOQUEADA(" +
                    "#{cCodGrupoCia_in}," +
                    "#{cCodCia_in}" +
                    ")}")
    @Options(statementType = StatementType.CALLABLE)      
     void obtenerOpcionesBloqueadas(Map mapParametros);      
    
}
