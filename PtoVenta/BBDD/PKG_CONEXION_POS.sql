--------------------------------------------------------
--  DDL for Package PKG_CONEXION_POS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PKG_CONEXION_POS" as

 TYPE DocumentoCursor IS REF CURSOR;
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
                                                       
FUNCTION FN_PRUEBA(FECHA VARCHAR2) 
        RETURN  DocumentoCursor;
        
            FUNCTION FN_MODIFICAR_DOCUMENTO(
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cNumCompPagoE VARCHAR2,
                                  pdf417 VARCHAR2) 
        RETURN NUMBER ;
    -- Author  : CESAR HUANES
    -- Created : 19/08/2014 03:09:35 p.m.
    -- Proposito :Imprime la cabecera de los documentos electronicos (factura, boleta y nota de credito)         
   FUNCTION FN_IMPRIMIR_CABECERA(cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoDocumento VARCHAR2
                                  ) 
   RETURN DocumentoCursor; 
  -- Author  : CESAR HUANES
  -- Created : 19/08/2014 03:09:35 p.m.
  -- Proposito :Imprime la detalle de los documentos electronicos (factura, boleta y nota de credito)   
  FUNCTION FN_IMPRIMIR_DETALLE(cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoClienteConvenio VARCHAR2
                                  ) 
 RETURN DocumentoCursor ; 
  -- Author  : CESAR HUANES
  -- Created : 19/08/2014 03:09:35 p.m.
  -- Proposito :Imprime Montos Globales (factura, boleta y nota de credito) 
    
 FUNCTION FN_IMPRIMIR_MONTOS_GLOBAL(
                                cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2) 
   RETURN DocumentoCursor ;
   
 -- Author  : CESAR HUANES
  -- Created : 20/08/2014 03:09:37 p.m.
  -- Proposito : Imprime todos lo datos de Convenio
  FUNCTION FN_IMPRIMIR_MENSAJE_CONVENIO(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoDocumento VARCHAR2,
                                   cTipoClienteConvenio VARCHAR2) 
  RETURN VARCHAR2;
   -- Author  : CESAR HUANES
   -- Created : 20/08/2014 03:09:37 p.m.
   -- Proposito : Imprime el pie de Pagina
  FUNCTION FN_IMPRIMIR_PIE_PAGINA
   
  RETURN DocumentoCursor;
  -- Author  : CESAR HUANES
   -- Created : 22/08/2014 03:09:37 p.m.
   -- Proposito : Imprime notas
   FUNCTION FN_IMPRIME_NOTAS(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoDocumento VARCHAR2) 
     RETURN DocumentoCursor ;        
END PKG_CONEXION_POS;
 

/
