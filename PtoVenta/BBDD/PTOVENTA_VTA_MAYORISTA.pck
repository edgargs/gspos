create or replace package PTOVENTA_VTA_MAYORISTA is

  -- Author  : LTAVARA
  -- Created : 08/01/2016 16:57:05
  -- Purpose : 
  
  TIPO_CLI_FINAL CONSTANT CHAR(1) := 'F';
  TIPO_CLI_MAYORISTA CONSTANT CHAR(1) := 'M';
    TYPE FarmaCursor IS REF CURSOR;
  
  
FUNCTION VTA_OBTIENE_VAL_PREC_VTA(cCodGrupoCia_in IN CHAR,
                        cCodLocal_in  IN CHAR,
                        cCodProd_in IN LGT_PROD.COD_PROD%TYPE,
                        cTipoCliente_in   IN CHAR)
  RETURN NUMBER;


FUNCTION F_CUR_DESC_VOLUMEN_MAYORISTA(cCodGrupoCia_in   IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                                      cCodLocal_in      IN VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                                      cCodProd_in       IN LGT_PROD.COD_PROD%TYPE, 
                                      cRUC IN CHAR DEFAULT 'N'
                                      )
RETURN FARMACURSOR ;

 PROCEDURE P_INS_LOTE_MAYORISTA(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cCodProd_in IN CHAR,
                             vNumLote_in IN VARCHAR2,
                             dFecVenc_in IN VARCHAR2 DEFAULT NULL,
                             vUsu_in IN VARCHAR2,
                             vFlag   IN CHAR DEFAULT 'N');
--Rafael Bullon Mucha 26/01/2016
FUNCTION OBTIENE_PRECIO_MINIMO (cCodGrupoCia_in IN CHAR,
                                    cCod_Local_in IN CHAR,
                                    cCodProd_in IN CHAR)
RETURN VARCHAR2;  

--Rafael Bullon Mucha 24/02/2016
 FUNCTION TI_F_GET_VALIDA_DEV_IMPORT (cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in IN CHAR,cnumPedidoVenta_in IN CHAR)
  RETURN VARCHAR;    
  
--Rafael Bullon Mucha 25/02/2016
 FUNCTION F_MODIFICA_DEV_IMPORTE(    cCodGrupoCia_in  IN CHAR,
 		  							                 cCodLocal_in    IN CHAR,	
                                     vNuSecUsu       IN CHAR,                                                                       
                                     vIdUsu_in       IN CHAR,
                                     cnumPedidoVenta_in IN CHAR
                                     )

RETURN INTEGER;
/****************************************************************************/  
	--Descripcion: VALIDA SI PRODUCTO ES CONTROLADO Y SI SE PUEDE ATENDER AL CLIENTE
  --FECHA         USUARIO		   COMENTARIO
  --07.06.2016    KMONCADA     Creación
  FUNCTION F_IS_PROD_CONTROLADO(cCodGrupoCia_in       IN CHAR,
                                cCodLocal_in          IN CHAR,
                                cCodProd_in           IN CHAR)
  RETURN CHAR;
/****************************************************************************/  

  FUNCTION F_PERMITE_AJUSTE_AUTOMATICO
  RETURN CHAR;
end PTOVENTA_VTA_MAYORISTA;
/
create or replace package body PTOVENTA_VTA_MAYORISTA is

/*
OBTENER EL PRECIO DE VENTA POR TIPO CLIENTE FINAL O MAYORISTA
*/

FUNCTION VTA_OBTIENE_VAL_PREC_VTA(cCodGrupoCia_in IN CHAR,
                        cCodLocal_in  IN CHAR,
                        cCodProd_in IN LGT_PROD.COD_PROD%TYPE,
                        cTipoCliente_in   IN CHAR)
  RETURN NUMBER
  IS
    v_cVAL_PREC_VTA VARCHAR2(20);
  BEGIN
    
    SELECT CASE 
             WHEN FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cCodGrupoCia_in, cCodLocal_in) = 'N' THEN
               ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(NVL(PROD_LOCAL.VAL_PREC_VTA,0))
             WHEN cTipoCliente_in = TIPO_CLI_FINAL THEN
                ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(NVL(PROD_LOCAL.VAL_PREC_MINORISTA,0))
             WHEN cTipoCliente_in = TIPO_CLI_MAYORISTA THEN
                ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(NVL(PROD_LOCAL.VAL_PREC_VTA,0))
             ELSE
               '0.00'
           END
    INTO v_cVAL_PREC_VTA
    FROM LGT_PROD_LOCAL PROD_LOCAL
    WHERE PROD_LOCAL.COD_GRUPO_CIA=cCodGrupoCia_in
    AND PROD_LOCAL.COD_LOCAL=cCodLocal_in
    AND PROD_LOCAL.COD_PROD=cCodProd_in;

     
    RETURN TO_NUMBER(TO_CHAR(v_cVAL_PREC_VTA,'9999990.000'),'9999999.000');
    
   EXCEPTION
       WHEN NO_DATA_FOUND THEN 
         RETURN 0;
     END;
 
  FUNCTION F_CUR_DESC_VOLUMEN_MAYORISTA(cCodGrupoCia_in IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                                        cCodLocal_in    IN VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                                        cCodProd_in     IN LGT_PROD.COD_PROD%TYPE,
                                        cRUC            IN CHAR DEFAULT 'N')
   RETURN FARMACURSOR IS
   vCurPrecioVolumen FarmaCursor;
   vCodPrecVol       PRECIO_POR_VOLUMEN_CAB.Cod_Prec_Vol%TYPE;
   vPorcentajeCab    PRECIO_POR_VOLUMEN_CAB.Porcentaje%TYPE;
 
 BEGIN
   BEGIN
     SELECT X.COD_PREC_VOL, X.PORCENTAJE
       INTO vCodPrecVol, vPorcentajeCab
       FROM (SELECT RANK() OVER(order by A.INDICADOR ASC) ORDEN, A.*
               FROM (SELECT '3' AS "INDICADOR", V.*
                       FROM PRECIO_POR_VOLUMEN_CAB V, 
                            PREC_VOL_CADENA_DET D
                      WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND D.COD_PREC_VOL = V.COD_PREC_VOL
                        AND V.ESTADO = 'A'
                        AND D.ESTADO = 'A'
                        AND V.FEC_FIN >= TO_CHAR(SYSDATE, 'DD/MM/YYYY')
                     UNION
                     
                     SELECT '2' AS "INDICADOR", V.*
                       FROM PRECIO_POR_VOLUMEN_CAB   V,
                            PREC_VOL_LABORATORIO_DET L,
                            LGT_PROD                 PROD
                      WHERE PROD.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND PROD.COD_PROD = cCodProd_in
                        AND L.COD_PREC_VOL = V.COD_PREC_VOL
                        AND L.COD_LAB = PROD.COD_LAB
                        AND V.ESTADO = 'A'
                        AND L.ESTADO = 'A'
                        AND V.FEC_FIN >= TO_CHAR(SYSDATE, 'DD/MM/YYYY')
                     UNION
                     
                     SELECT '1' AS "INDICADOR", V.*
                       FROM PRECIO_POR_VOLUMEN_CAB V, 
                            PREC_VOL_PRODUCTO_DET P
                      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND P.COD_PROD = cCodProd_in
                        AND P.COD_PREC_VOL = V.COD_PREC_VOL
                        AND V.ESTADO = 'A'
                        AND P.ESTADO = 'A'
                        AND V.FEC_FIN >= TO_CHAR(SYSDATE, 'DD/MM/YYYY')) A
              ORDER BY A.INDICADOR ASC) X
      WHERE ORDEN = 1;
   EXCEPTION
     WHEN OTHERS THEN
       vCodPrecVol    := '0';
       vPorcentajeCab := '0';
   END;
 
   OPEN vCurPrecioVolumen FOR
     SELECT UNIDAD_MIN || 'Ã' || 
            UNIDAD_MAX || 'Ã' || 
            TRIM(CASE
                   WHEN VAL_PREC_DSCTO < VAL_PREC_MINIMO THEN
                    TO_CHAR(VAL_PREC_MINIMO, '9,999,990.000')
                   ELSE
                    TO_CHAR(VAL_PREC_DSCTO, '9,999,990.000')
                 END) || 'Ã' || 
            CODIGO_CAB || 'Ã' || 
            CODIGO_DET || 'Ã' ||
            DSCTO 
       FROM (SELECT DET.UNIDAD_MIN,
                    DET.UNIDAD_MAX,
                    DET.DESCTO DSCTO,
                    ((P_LOCAL.VAL_PREC_VTA * P_LOCAL.VAL_FRAC_LOCAL) * (1 - DET.DESCTO)) VAL_PREC_DSCTO,
                    (PROD.VAL_PREC_PROM * (1 + vPorcentajeCab)) * (1 + IGV.PORC_IGV/100) VAL_PREC_MINIMO,
                    vCodPrecVol CODIGO_CAB,
                    DET.COD_DET_PREC_VOL CODIGO_DET
               FROM LGT_PROD_LOCAL         P_LOCAL,
                    LGT_PROD               PROD,
                    PBL_IGV                IGV,
                    PRECIO_POR_VOLUMEN_DET DET
              WHERE P_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
                AND P_LOCAL.COD_PROD = PROD.COD_PROD
                AND PROD.COD_IGV = IGV.COD_IGV
                AND DET.COD_PREC_VOL = vCodPrecVol
                AND P_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
                AND P_LOCAL.COD_LOCAL = cCodLocal_in
                AND P_LOCAL.COD_PROD = cCodProd_in
              ORDER BY DET.COD_DET_PREC_VOL
             
             ) W;
 
   RETURN vCurPrecioVolumen;
 END;
 /*
 LTAVARA - Insertar lote mayorista
 */
 
 PROCEDURE P_INS_LOTE_MAYORISTA(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cCodProd_in IN CHAR,
                             vNumLote_in IN VARCHAR2,
                             dFecVenc_in IN VARCHAR2 DEFAULT NULL,
                             vUsu_in IN VARCHAR2,
                             vFlag   IN CHAR DEFAULT 'N')
  AS
  vIp VARCHAR2(15):='';
    vExisteKardex CHAR(1) := 'N';
  BEGIN
    -- SI SE INVOCA DESDE EL FV LA INSERCION DEL LOTE MAYORISTA
    IF vFlag = 'N' THEN
    -- KMONCADA 02.08.2016 [PROYECTO M] NO SE PERMITIRA INSERTAR DIRECTAMENTE EN LA TABLA LOCAL LOTE
    -- SOLO MEDIATE AJUSTE DE KARDEX
    RAISE_APPLICATION_ERROR(-20100,'COORDINE CON JEFE DE LOCAL:'||CHR(10)||
                                  'PARA REGISTRAR EL AJUSTE DEL PRODUCTO Y LOTE.');
    END IF;
       SELECT SYS_CONTEXT ('USERENV', 'IP_ADDRESS') INTO vIp  FROM dual;
       INSERT INTO LGT_PROD_LOCAL_LOTE(
              COD_GRUPO_CIA,
              COD_LOCAL,
              COD_PROD,
              LOTE,
              FECHA_VENCIMIENTO_LOTE,
              STK_FISICO,
              USU_CREA_PROD_LOCAL_LOTE,
              FEC_CREA_PROD_LOCAL_LOTE,
              IP_PROD_LOCAL)
       VALUES(cCodGrupoCia_in,
              cCodLocal_in,
              cCodProd_in,
              upper(vNumLote_in),
              nvl2(dFecVenc_in,to_date(dFecVenc_in,'dd/MM/yyyy'),NULL),
              0,
              vUsu_in,
              SYSDATE,
              vIp
              );
  END;
 
  --Rafael Bullon Mucha 26/01/2016
  
  FUNCTION OBTIENE_PRECIO_MINIMO(cCodGrupoCia_in IN CHAR,
                                 cCod_Local_in   IN CHAR,
                                 cCodProd_in     IN CHAR)
  
   RETURN VARCHAR2 IS
    vPrecio        VARCHAR2(30);
  BEGIN
  
    SELECT TO_CHAR(ROUND(prod.val_prec_prom * (1 + igv.porc_igv / 100), 2),'99999990.00')
      INTO vPrecio
      from ptoventa.lgt_prod       prod,
           ptoventa.lgt_prod_local loc,
           ptoventa.pbl_igv        igv
     where LOC.cod_grupo_cia = cCodGrupoCia_in
       and LOC.cod_local = cCod_Local_in
       and LOC.COD_PROD = cCodProd_in
       and LOC.est_prod_loc = 'A'
       AND prod.cod_grupo_cia = loc.cod_grupo_cia
       and prod.cod_prod = loc.cod_prod
       and prod.cod_igv = igv.cod_igv;
       
    RETURN vPrecio;
  END;
  
 /*
  * Descripcion: Valida codigo de devolucion del importe
  * Por: Rafael Bullon Mucha   
  * Fecha: 24/02/2016
  */
  FUNCTION TI_F_GET_VALIDA_DEV_IMPORT (cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in IN CHAR,cnumPedidoVenta_in IN CHAR)

  RETURN varchar
  IS
    cRegistro varchar(50):='';

  BEGIN
  
  SELECT 'S'|| 'Ã' ||
  CDD.VAL_DIF|| 'Ã' ||
  to_char(VPVC.FEC_PED_VTA,'dd/MM/yyyy HH:mm:ss')|| 'Ã' ||CDD.EST_DEV_DIF into cRegistro
  FROM CAJ_DEVOLUCION_DIFERENCIA CDD,VTA_PEDIDO_VTA_CAB VPVC
  WHERE CDD.COD_GRUPO_CIA=VPVC.COD_GRUPO_CIA AND
        CDD.COD_GRUPO_CIA=VPVC.COD_GRUPO_CIA AND
        CDD.NUM_PED_VTA=VPVC.NUM_PED_VTA AND
        CDD.COD_GRUPO_CIA=cCodGrupoCia_in AND CDD.COD_LOCAL=cCod_Local_in
        AND CDD.NUM_PED_VTA=cnumPedidoVenta_in;  
       RETURN cRegistro;          
  EXCEPTION
     WHEN OTHERS        
      THEN
          cRegistro:='N';
     
        RETURN cRegistro; 
     
  END; 
  
 /****************************************************************************/ 
    -- Agregado por: Rafael Bullo Mucha
    -- Fecha: 25/02/2016
 FUNCTION F_MODIFICA_DEV_IMPORTE(    cCodGrupoCia_in  IN CHAR,
 		  							                 cCodLocal_in    IN CHAR,	
                                     vNuSecUsu       IN CHAR,
                                     vIdUsu_in       IN CHAR,
                                     cnumPedidoVenta_in IN CHAR
                                     )

RETURN INTEGER
IS
 vSecMovCaja char(10):='';
 INDICADOR INTEGER:= 0;

 BEGIN
     BEGIN
        SELECT SEC_MOV_CAJA INTO vSecMovCaja 
        FROM CE_MOV_CAJA A 
        WHERE COD_GRUPO_CIA=cCodGrupoCia_in AND COD_LOCAL=cCodLocal_in 
        AND TIP_MOV_CAJA='A' AND FEC_DIA_VTA=trunc(sysdate)
        AND SEC_USU_LOCAL=vNuSecUsu
        AND 0= (SELECT COUNT(1) 
                FROM CE_MOV_CAJA C 
                WHERE C.COD_GRUPO_CIA = A.COD_GRUPO_CIA 
                AND C.COD_LOCAL = A.COD_LOCAL
                AND C.TIP_MOV_CAJA = 'C'
                AND C.SEC_MOV_CAJA_ORIGEN = A.SEC_MOV_CAJA);
        EXCEPTION
      WHEN OTHERS THEN
        INDICADOR:=-1; 
    END;
    
   IF INDICADOR = -1 THEN
     RETURN INDICADOR;
   END IF;
   
    UPDATE CAJ_DEVOLUCION_DIFERENCIA CDD
    SET CDD.EST_DEV_DIF='D', CDD.FEC_DEVOL=SYSDATE, CDD.SEC_MOV_CAJA=vSecMovCaja,
        CDD.FEC_MOD=SYSDATE, CDD.USU_MOD=vIdUsu_in
    WHERE CDD.COD_GRUPO_CIA=cCodGrupoCia_in AND CDD.COD_LOCAL=cCodLocal_in
        AND CDD.NUM_PED_VTA=cnumPedidoVenta_in;
       
       INDICADOR:=1;
  RETURN INDICADOR;
END;

/****************************************************************************/  
	--Descripcion: VALIDA SI PRODUCTO ES CONTROLADO Y SI SE PUEDE ATENDER AL CLIENTE
  --FECHA         USUARIO		   COMENTARIO
  --07.06.2016    KMONCADA     Creación
  FUNCTION F_IS_PROD_CONTROLADO(cCodGrupoCia_in       IN CHAR,
                                cCodLocal_in          IN CHAR,
                                cCodProd_in           IN CHAR)
    RETURN CHAR IS
    vIsProdControlado CHAR(1) := 'N';
  BEGIN
    SELECT DECODE(COUNT(1),0,'N','S')
    INTO vIsProdControlado
    FROM VTA_PROD_CONTROLADOS A
    WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND A.COD_PROD = cCodProd_in
    AND A.EST_PROD = 'A';

    RETURN vIsProdControlado;
  END;
/****************************************************************************/  

  FUNCTION F_PERMITE_AJUSTE_AUTOMATICO
    RETURN CHAR IS
    vRspta PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE;
  BEGIN
    SELECT LLAVE_TAB_GRAL
    INTO vRspta
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL = 649;
    RETURN vRspta;
  END;
end PTOVENTA_VTA_MAYORISTA;
/
