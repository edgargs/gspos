--------------------------------------------------------
--  DDL for Package PKG_CONEXION_POS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PKG_CONEXION_POS" as


    -- Author  : LTAVARA
    -- Created : 14/07/2014 06:37:35 p.m.
    -- Purpose : GENERAR DOCUMENTO ELECTRONICO

       FUNCTION FN_GENERAR_DOCUMENTO_ELEC(
                                     cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoDocumento VARCHAR2,
                                  cTipoClienteConvenio VARCHAR2) 
        RETURN VARCHAR2;
        
    -- Author  : LTAVARA
    -- Created : 23/07/2014 06:50:35 p.m.
    -- Purpose : INSERTAR TRAMA
        
        FUNCTION FN_INSERTAR_TRAMA(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoTrama VARCHAR2,
                                  cTrama VARCHAR2) 
        RETURN NUMBER;
        
        
     -- Author  : LTAVARA
    -- Created : 23/07/2014 06:50:35 p.m.
    -- Purpose : MODIFICAR TRAMA
        FUNCTION FN_MODIFICAR_TRAMA(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoTrama VARCHAR2,
                                  cTrama VARCHAR2,
                                  cCodTrama VARCHAR2) 
        RETURN NUMBER;
        
               
     -- Author  : LTAVARA
    -- Created : 14/07/2014 06:50:35 p.m.
    -- Purpose : Validar si el convenio puede emitir comprobante electrónico


  procedure sp_upd_comp_pago_e(p_cod_grupo_cia      varchar2,
                                                       p_cod_local          varchar2,
                                                       p_num_ped_vta        varchar2,
                                                       p_sec_com_pago       varchar2,
                                                       p_tip_clien_convenio varchar2 default null
                                                       );     
END PKG_CONEXION_POS;
 

/
