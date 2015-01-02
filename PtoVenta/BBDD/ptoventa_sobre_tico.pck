CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_SOBRE_TICO" AS

  TYPE FarmaCursor IS REF CURSOR;
  ESTADO_ACTIVO        CHAR := 'A';
  ESTADO_INACTIVO      CHAR := 'I';
  NO_ENVIADO           CHAR := 'N';
  ENVIADO              CHAR := 'S';
  ELIMINADO            CHAR := 'X';
  IND_LOCAL_MARKET     CHAR(3) :='002';
  IND_LOCAL_FARMA      CHAR(3) :='001';
  CodGrupoCia_in      CHAR(3) :='001';

  FUNCTION F_LISTAR_SOBRES(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
    RETURN FarmaCursor;

  PROCEDURE P_INSERT_SOBRE_TICO(cFecVta     CHAR,
                                cCodLocal   CHAR,
                                cCodSobre   VARCHAR2,
                                cMoneda     VARCHAR2,
                                cMontoTotal NUMBER,
                                cFormaPago  VARCHAR2,
                                cUsuCrea    VARCHAR2,
                                cUsuLogin   VARCHAR2,
                                cMonSol     NUMBER,
                                cMonDol     NUMBER,
                                cIndSol     CHAR,--NUMBER,
                                cIndDol     CHAR--NUMBER
                                );

  FUNCTION P_DELETE_SOBRE(cFecVta   CHAR,
                           cCodLocal CHAR,
                           cCodSobre VARCHAR2,
                           cUsuMod   VARCHAR2)
  RETURN CHAR;

  FUNCTION F_TICO_SOBRES_LOCAL(cCodLocal IN CHAR) RETURN FarmaCursor;

  PROCEDURE P_UPDATE_IND_PROCESO
                            (indProceso IN CHAR
                            );

  PROCEDURE P_UPDATE_REMITO(cCodSobre  IN VARCHAR2,
                            cCodLocal  IN CHAR,
                            cFecVta    IN CHAR,
                            cCodRemito IN VARCHAR2,
                            cPrecinto  IN VARCHAR2,
                            cUsuMod    IN VARCHAR2);

  FUNCTION F_LISTA_SOBRE_TICO(cCodLocal IN CHAR) RETURN FarmaCursor;

  FUNCTION F_OBTENER_FECVTA(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cCodSobre_in    IN CHAR)
  
  RETURN CHAR;

  FUNCTION GET_IND_TICO (cCodLocal CHAR) RETURN CHAR ;

  FUNCTION GET_IP_PADRE(cCodLocal CHAR) RETURN VARCHAR2;
  
  FUNCTION GET_IND_SIN_MARKET(cCodLocal CHAR) RETURN CHAR;
  
  PROCEDURE  P_INSERT_CIERRE_DIA_MARKET (cFecCierreDia   IN CHAR ,
                                         cCodLocal       IN CHAR ,
                                         cUsuLogin       IN CHAR ,
                                         cUsuCrea        IN CHAR
                                        );
                                        
  FUNCTION P_IND_CIERRE_MARKET (cFecDiaCierre     IN CHAR,
                                cCodMarket   IN CHAR)
    RETURN CHAR;          

      FUNCTION GET_IPS_TICOS(cCodLocal CHAR) RETURN FarmaCursor;                                                                          

      -- Descripcion:Obtiene local tico asociado al market
--Fecha       Usuario    Comentario
--28.10.2014  RHERRERA      Creación
FUNCTION GET_LOCAL_TICO(cCodlocal char) RETURN CHAR;

END; --FIN DEL PACKAGE
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_SOBRE_TICO" AS

  FUNCTION F_LISTAR_SOBRES(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
    RETURN FarmaCursor
  
   IS
    curSobre FarmaCursor;
    
    ------
    CURSOR C_SOBRE (c_grupocia in char , c_codLocal in char) IS 
     SELECT to_char(A.FEC_DIA_VTA, 'dd/mm/yyyy') FEC_DIA, A.COD_SOBRE
       FROM CE_SOBRE              A,
            CE_FORMA_PAGO_ENTREGA B,
            VTA_FORMA_PAGO        C,
            CE_DIA_REMITO         E,
            pbl_usu_local         u,
            pbl_local             loc
      WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_LOCAL = cCodLocal_in
        AND A.COD_REMITO IS NULL
        and a.estado = ESTADO_ACTIVO
        -----------------------------------
        AND A.IND_ENVIO_MARKET = NO_ENVIADO
        -----------------------------------
        AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
        AND A.COD_LOCAL = B.COD_LOCAL
        AND A.SEC_MOV_CAJA = B.SEC_MOV_CAJA
        AND A.SEC_FORMA_PAGO_ENTREGA = B.SEC_FORMA_PAGO_ENTREGA
        AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
        AND B.COD_FORMA_PAGO = C.COD_FORMA_PAGO
        AND A.COD_GRUPO_CIA = E.COD_GRUPO_CIA
        AND A.COD_LOCAL = E.COD_LOCAL
        AND A.FEC_DIA_VTA = E.FEC_DIA_VTA
        AND a.cod_grupo_cia = u.cod_grupo_cia
        and a.cod_local = u.cod_local
        and a.sec_usu_qf = u.sec_usu_local
        and a.cod_local = loc.cod_local
        AND NVL(a.ind_etv, loc.ind_prosegur) = 'S' -- kmoncada 19.05.2014 indicador de tipo de sobre portavalor o deposito
     union -- selecciona sobres de REMITO.
     SELECT to_char(A.FEC_DIA_VTA, 'dd/mm/yyyy') FEC_DIA, A.COD_SOBRE
       FROM CE_SOBRE_TMP   A,
            VTA_FORMA_PAGO C,
            pbl_usu_local  u,
            pbl_local      loc
      WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_LOCAL = cCodLocal_in
        AND A.COD_REMITO IS NULL
        and a.estado = ESTADO_ACTIVO
        --------------------------------------
        AND A.IND_ENVIO_MARKET = NO_ENVIADO
        ---------------------------------------
        AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
        AND A.COD_FORMA_PAGO = C.COD_FORMA_PAGO
        AND a.cod_grupo_cia = u.cod_grupo_cia
        and a.cod_local = u.cod_local
        and a.sec_usu_qf = u.sec_usu_local
        and a.cod_local = loc.cod_local
        AND NVL(a.ind_etv, loc.ind_prosegur) = 'S' -- kmoncada 19.05.2014 indicador de tipo de sobre portavalor o deposito
        and not exists (select 1
               from ce_sobre sobre
              where sobre.cod_sobre = a.cod_sobre);
    
    
  BEGIN
  
    OPEN curSobre FOR
      SELECT to_char(E.FEC_DIA_VTA, 'dd/mm/yyyy') || 'Ã' || A.COD_SOBRE || 'Ã' ||
              DECODE(B.TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES') || 'Ã' ||
              TO_CHAR(NVL(CASE
                            WHEN B.TIP_MONEDA = '01' THEN
                             B.MON_ENTREGA_TOTAL
                            WHEN B.TIP_MONEDA = '02' THEN
                             B.MON_ENTREGA
                          END,
                          0),
                      '999,999,990.00') || 'Ã' ||
              NVL(C.DESC_FORMA_PAGO, ' ') || 'Ã' ||
              NVL(B.USU_CREA_FORMA_PAGO_ENT, ' ') || 'Ã' ||
             --A.USU_MOD_SOBRE,
              nvl(u.login_usu, '-') || 'Ã' ||
              TO_CHAR(NVL(CASE
                            WHEN B.TIP_MONEDA = '01' THEN
                             B.MON_ENTREGA_TOTAL
                          END,
                          0),
                      '999,999,990.00') || 'Ã' ||
              TO_CHAR(NVL(CASE
                            WHEN B.TIP_MONEDA = '02' THEN
                             B.MON_ENTREGA
                          END,
                          0),
                      '999,999,990.00') || 'Ã' ||
              NVL(CASE
                    WHEN B.TIP_MONEDA = '01' THEN
                     1
                  END,
                  0) || 'Ã' || NVL(CASE
                                     WHEN B.TIP_MONEDA = '02' THEN
                                      1
                                   END,
                                   0)
        FROM CE_SOBRE              A,
             CE_FORMA_PAGO_ENTREGA B,
             VTA_FORMA_PAGO        C,
             CE_DIA_REMITO         E,
             pbl_usu_local         u,
             pbl_local             loc
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.COD_REMITO IS NULL
         and a.estado = ESTADO_ACTIVO
         --RHERRERA 16.09.2014
         --------------------------------------
         --AND A.IND_ENVIO_MARKET = NO_ENVIADO
         --------------------------------------
         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.SEC_MOV_CAJA = B.SEC_MOV_CAJA
         AND A.SEC_FORMA_PAGO_ENTREGA = B.SEC_FORMA_PAGO_ENTREGA
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_FORMA_PAGO = C.COD_FORMA_PAGO
         AND A.COD_GRUPO_CIA = E.COD_GRUPO_CIA
         AND A.COD_LOCAL = E.COD_LOCAL
         AND A.FEC_DIA_VTA = E.FEC_DIA_VTA
         AND a.cod_grupo_cia = u.cod_grupo_cia
         and a.cod_local = u.cod_local
         and a.sec_usu_qf = u.sec_usu_local
         and a.cod_local = loc.cod_local
         AND NVL(a.ind_etv, loc.ind_prosegur) = 'S' -- kmoncada 19.05.2014 indicador de tipo de sobre portavalor o deposito
      union -- selecciona sobres de REMITO.
      SELECT to_char(A.FEC_DIA_VTA, 'dd/mm/yyyy') || 'Ã' || A.COD_SOBRE || 'Ã' ||
              DECODE(A.TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES') || 'Ã' ||
              TO_CHAR(NVL(CASE
                            WHEN A.TIP_MONEDA = '01' THEN
                             A.MON_ENTREGA_TOTAL
                            WHEN A.TIP_MONEDA = '02' THEN
                             A.MON_ENTREGA
                          END,
                          0),
                      '999,999,990.00') || 'Ã' ||
              NVL(C.DESC_FORMA_PAGO, ' ') || 'Ã' ||
              NVL(A.Usu_Crea_Sobre, ' ') || 'Ã' ||
             --A.USU_MOD_SOBRE,
              nvl(u.login_usu, '-') || 'Ã' ||
              TO_CHAR(NVL(CASE
                            WHEN A.TIP_MONEDA = '01' THEN
                             A.MON_ENTREGA_TOTAL
                          END,
                          0),
                      '999,999,990.00') || 'Ã' ||
              TO_CHAR(NVL(CASE
                            WHEN A.TIP_MONEDA = '02' THEN
                             A.MON_ENTREGA
                          END,
                          0),
                      '999,999,990.00') || 'Ã' ||
              NVL(CASE
                    WHEN A.TIP_MONEDA = '01' THEN
                     1
                  END,
                  0) || 'Ã' || NVL(CASE
                                     WHEN A.TIP_MONEDA = '02' THEN
                                      1
                                   END,
                                   0)
        FROM CE_SOBRE_TMP   A,
             VTA_FORMA_PAGO C,
             pbl_usu_local  u,
             pbl_local      loc
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.COD_REMITO IS NULL
         and a.estado = ESTADO_ACTIVO
          --RHERRERA 16.09.2014
         --------------------------------------
         --AND A.IND_ENVIO_MARKET = NO_ENVIADO
         --------------------------------------
         AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND A.COD_FORMA_PAGO = C.COD_FORMA_PAGO
         AND a.cod_grupo_cia = u.cod_grupo_cia
         and a.cod_local = u.cod_local
         and a.sec_usu_qf = u.sec_usu_local
         and a.cod_local = loc.cod_local
         AND NVL(a.ind_etv, loc.ind_prosegur) = 'S'
         and not exists (select 1
                from ce_sobre sobre
               where sobre.cod_sobre = a.cod_sobre);
  
  
  
   BEGIN  
   
   FOR sobre IN C_SOBRE (cCodGrupoCia_in,cCodLocal_in)
       LOOP
          
       UPDATE CE_SOBRE_TMP T
       SET    T.FEC_ENVIO_MARKET = SYSDATE,
              T.IND_ENVIO_MARKET = ENVIADO
              
              WHERE T.COD_SOBRE   =  sobre.cod_sobre             
              AND   T.FEC_DIA_VTA =  sobre.fec_dia
              AND   T.ESTADO = ESTADO_ACTIVO
              ;
       
      
       
       
       UPDATE CE_SOBRE T
       SET    T.FEC_ENVIO_MARKET = SYSDATE,
              T.IND_ENVIO_MARKET = ENVIADO
              
              WHERE T.COD_SOBRE   =  sobre.cod_sobre             
              AND   T.FEC_DIA_VTA =  sobre.fec_dia
              AND   T.ESTADO      =  ESTADO_ACTIVO
              ;
       
       END LOOP;
      
   COMMIT;
   EXCEPTION
           WHEN OTHERS THEN
      ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('NO SE PUDO ACTUALIZAR EL ENVIO');
   END;               
  
  
  
  
  
  
    RETURN curSobre;
  
  END;

  PROCEDURE P_INSERT_SOBRE_TICO(cFecVta     CHAR,
                                cCodLocal   CHAR,
                                cCodSobre   VARCHAR2,
                                cMoneda     VARCHAR2,
                                cMontoTotal NUMBER,
                                cFormaPago  VARCHAR2,
                                cUsuCrea    VARCHAR2,
                                cUsuLogin   VARCHAR2,
                                cMonSol     NUMBER,
                                cMonDol     NUMBER,
                                cIndSol     CHAR,--NUMBER
                                cIndDol     CHAR --NUMBER
                                ) IS
    i integer := 0;
    clocal_tico CHAR(3):= GET_LOCAL_TICO(cCodLocal); -- local tico    
  BEGIN
  
    SELECT COUNT(*)
      INTO i
      FROM CE_SOBRE_TICO CT
     WHERE CT.COD_SOBRE = cCodSobre
       AND CT.COD_LOCAL = cCodLocal
       AND CT.FEC_VTA = cFecVta
       AND CT.COD_LOCAL = clocal_tico;
  
    if i > 0 then
      return;
    end if;
  
    INSERT INTO CE_SOBRE_TICO
      (FEC_VTA,
       COD_LOCAL,
       COD_SOBRE,
       MONEDA,
       MON_ENTREGA_TOTAL,
       DESC_FORMA_PAGO,
       USU_CREA_SOBRE,
       USU_LOGIN,
       MON_ENTRE_SOL,
       MON_ENTRE_DOL,
       TIP_MOND_SOL,
       TIP_MOND_DOL)
    VALUES
      (cFecVta,
       cCodLocal,
       cCodSobre,
       cMoneda,
       cMontoTotal,
       cFormaPago,
       cUsuCrea,
       cUsuLogin,
       cMonSol,
       cMonDol,
       cIndSol,
       cIndDol);
       COMMIT;---
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;---
      RAISE_APPLICATION_ERROR(-20000,
                              CHR(13) ||
                              'ERROR AL GRABAR SOBRES DE TICO - RAC');
    
  END;

  FUNCTION P_DELETE_SOBRE(cFecVta   CHAR,
                           cCodLocal CHAR,
                           cCodSobre VARCHAR2,
                           cUsuMod   VARCHAR2)
  RETURN CHAR                           
                            IS
  VALOR CHAR(1):= NO_ENVIADO;
  i      integer:= 0 ;
  i_2      integer:= 0 ;
    clocal_tico CHAR(3):= GET_LOCAL_TICO(cCodLocal); -- local tico  
  BEGIN
        
     SELECT COUNT(*) INTO i
     FROM   CE_SOBRE_TICO ST
     WHERE 
           ST.IND_TICO  =  ENVIADO
       AND ST.IND_LOCAL =  NO_ENVIADO
       AND ST.EST_SOBRE = ESTADO_ACTIVO
       AND (ST.COD_REMITO IS NULL OR LENGTH(ST.COD_REMITO) <= 1)
       AND ST.COD_LOCAL   =  clocal_tico;
       
     SELECT COUNT(*) INTO i_2
     FROM   CE_SOBRE_TICO ST
     WHERE 
           ST.COD_LOCAL = cCodLocal
       AND ST.COD_SOBRE = cCodSobre
       AND ST.IND_TICO  =  NO_ENVIADO
       AND ST.IND_LOCAL =  NO_ENVIADO
       AND ST.EST_SOBRE = ESTADO_ACTIVO
       AND (ST.COD_REMITO IS NULL OR LENGTH(ST.COD_REMITO) <= 1)
       AND ST.COD_LOCAL   =     clocal_tico;

       
       IF i > 0 THEN
    
       VALOR := NO_ENVIADO;
       
       ELSE
          
       VALOR := ENVIADO;         

       IF i_2 > 0   THEN
                     
             UPDATE CE_SOBRE_TICO ST
             SET ST.EST_SOBRE     = ESTADO_INACTIVO,
                 ST.IND_LOCAL     = ELIMINADO,
                 ST.IND_TICO      = ENVIADO,
                 ST.USU_MOD_SOBRE = cUsuMod,
                 ST.FEC_MOD_SOBRE = SYSDATE
           WHERE ST.FEC_VTA = cFecVta
             AND ST.COD_LOCAL = cCodLocal
             AND ST.COD_SOBRE = cCodSobre
             AND ST.IND_LOCAL = NO_ENVIADO
             AND ST.IND_TICO = NO_ENVIADO
             AND ST.EST_SOBRE = ESTADO_ACTIVO
             and (ST.COD_REMITO IS NULL OR LENGTH(ST.COD_REMITO) > 1)
             AND ST.COD_LOCAL = clocal_tico;
               COMMIT;--
             END IF;


       END IF;
       
       
       
       
   
   RETURN VALOR;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;--
      RAISE_APPLICATION_ERROR(-20000,
                              CHR(13) || 'ERROR AL ELIMINAR EL SOBRE - RAC');
    RETURN VALOR;                                  
  END;

  FUNCTION F_TICO_SOBRES_LOCAL(cCodLocal IN CHAR) RETURN FarmaCursor AS
  
    cSobres FarmaCursor;
    clocal_tico CHAR(3):= GET_LOCAL_TICO(cCodLocal); -- local tico  
  BEGIN
    OPEN cSobres FOR
      SELECT TI.FEC_VTA || 'Ã' || TI.COD_SOBRE || 'Ã' || TI.MONEDA || 'Ã' ||
             TI.MON_ENTREGA_TOTAL || 'Ã' || TI.DESC_FORMA_PAGO || 'Ã' ||
             TI.USU_CREA_SOBRE || 'Ã' || TI.USU_LOGIN || 'Ã' ||
             TI.MON_ENTRE_SOL || 'Ã' || TI.MON_ENTRE_DOL || 'Ã' ||
             TI.TIP_MOND_SOL || 'Ã' || TI.TIP_MOND_DOL
        FROM CE_SOBRE_TICO TI
       WHERE TI.COD_LOCAL = cCodLocal
         AND (TI.COD_REMITO IS NULL OR LENGTH(TI.COD_REMITO) <= 1)
         AND TI.IND_LOCAL = NO_ENVIADO
         AND TI.IND_TICO = NO_ENVIADO
         AND TI.EST_SOBRE = ESTADO_ACTIVO
         AND TI.COD_LOCAL = clocal_tico;
  
    RETURN cSobres;
  
  END;

  PROCEDURE P_UPDATE_IND_PROCESO
                            (indProceso IN CHAR
                             ) AS
  
  BEGIN
  
    UPDATE CE_SOBRE_TICO T
       SET T.IND_TICO     = indProceso
     WHERE T.IND_LOCAL    = NO_ENVIADO
       AND (T.COD_REMITO IS NULL OR LENGTH(T.COD_REMITO) <= 1)
       AND T.EST_SOBRE = ESTADO_ACTIVO;
       COMMIT;---xx
  EXCEPTION
     WHEN OTHERS THEN
          ROLLBACK;--xx  
    RAISE_APPLICATION_ERROR(-20000,
                              CHR(13) ||
                              'ERROR AL ACTULIZAR ESTADO ENVIADO - RAC ');
    
  END;

  PROCEDURE P_UPDATE_REMITO(cCodSobre  IN VARCHAR2,
                            cCodLocal  IN CHAR,
                            cFecVta    IN CHAR,
                            cCodRemito IN VARCHAR2,
                            cPrecinto  IN VARCHAR2,
                            cUsuMod    IN VARCHAR2) AS
  
  BEGIN
  
  PTOVENTA_CE_REMITO.CE_P_AGREGA_REMITO_DU(
                            CodGrupoCia_in,
                            cCodLocal,
                            cUsuMod,                                       
                            cCodRemito,
                            cFecVta,
                            cCodSobre,
                            cPrecinto  );
    
      -- ACTUALIZA INDICADO DE ENVIO A MATRIZ COMO NEGATIVO
      --(NO DEBE ENVIAR A MATRIZ).
      --    30.10.2014
            UPDATE CE_REMITO R
            SET    R.IND_ENVIO_MATRIZ = NO_ENVIADO
            WHERE  R.COD_REMITO       = cCodRemito
            AND    R.COD_GRUPO_CIA    = CodGrupoCia_in
            AND    R.COD_LOCAL        = cCodLocal ;
  
  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20000,
                              CHR(13) ||
                              'ERROR AL ACTULIZAR REMITO EN SOBRE TICO ');
    
  END;

  FUNCTION F_LISTA_SOBRE_TICO(cCodLocal IN CHAR) RETURN FarmaCursor IS
  
    curSobreRemi FarmaCursor;
    clocal_tico CHAR(3):= GET_LOCAL_TICO(cCodLocal); -- local tico  
  BEGIN
  
    OPEN curSobreRemi FOR
      SELECT TI.FEC_VTA || 'Ã' || TI.COD_SOBRE || 'Ã' || TI.COD_REMITO || 'Ã' ||
             TI.PRECINTO
        FROM CE_SOBRE_TICO TI
       WHERE TI.COD_LOCAL = cCodLocal
         AND TI.COD_REMITO IS NOT NULL
         AND TI.IND_LOCAL = ENVIADO
         AND TI.EST_SOBRE = ESTADO_ACTIVO
         AND TI.IND_TICO = NO_ENVIADO
         AND TI.COD_LOCAL = clocal_tico;
  
    RETURN curSobreRemi;
  
  END;

  FUNCTION F_OBTENER_FECVTA(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cCodSobre_in    IN CHAR) RETURN CHAR IS
    cCantSobreTemp NUMBER;
    fecDiaVta      CHAR(10);
  BEGIN
    SELECT COUNT(*)
      INTO cCantSobreTemp
      FROM CE_SOBRE_TMP E
     WHERE E.COD_GRUPO_CIA = cCodGrupoCia_in
       AND E.COD_LOCAL = cCodLocal_in
       AND E.COD_SOBRE = cCodSobre_in;
  
    IF (cCantSobreTemp > 0) THEN
    
      --actualizando el estado de sobre temp a INACTIVO
      --INICIO
    
      SELECT TO_CHAR(E.FEC_DIA_VTA, 'DD/MM/YYYY')
        INTO fecDiaVta
        FROM CE_SOBRE_TMP E
       WHERE E.COD_GRUPO_CIA = cCodGrupoCia_in
         AND E.COD_LOCAL = cCodLocal_in
         AND E.COD_SOBRE = cCodSobre_in;
    
    ELSE
      SELECT TO_CHAR(S.FEC_DIA_VTA, 'DD/MM/YYYY')
        INTO fecDiaVta
        FROM CE_SOBRE S
       WHERE S.COD_SOBRE = cCodSobre_in
         AND S.COD_GRUPO_CIA = cCodGrupoCia_in
         AND S.COD_LOCAL = cCodLocal_in;
    
    END IF;
  
    RETURN fecDiaVta;
  END;

  FUNCTION GET_IND_TICO (cCodLocal CHAR) RETURN CHAR IS
  
    ind_tico CHAR(1):='N';
    i integer:=0;
  BEGIN
  
    SELECT COUNT(*)
      into i
      FROM PBL_LOCAL x
     WHERE X.COD_LOCAL  =  cCodLocal
     AND   X.cod_tip_local_venta=IND_LOCAL_MARKET;
     
     IF I>0 THEN
        ind_tico:='S';
     END IF;
     
    RETURN ind_tico;
  END;

  FUNCTION GET_IP_PADRE(cCodLocal CHAR) RETURN VARCHAR2 
    IS
  
    --cLocalesMarket FarmaCursor;
    IP_PADRE PBL_LOCAL.IP_SERVIDOR_LOCAL%TYPE ;
    P_PADRE  PBL_LOCAL.COD_LOCAL_PADRE%TYPE;
  BEGIN
  
    --OPEN cLocalesMarket FOR
      SELECT  P.COD_LOCAL_PADRE
      INTO    P_PADRE            
      FROM PBL_LOCAL P
      WHERE  P.COD_LOCAL = cCodLocal
             AND
             P.COD_TIP_LOCAL_VENTA = IND_LOCAL_MARKET;
             
    
    
      SELECT PL.IP_SERVIDOR_LOCAL
             INTO         IP_PADRE
        FROM PBL_LOCAL PL
       WHERE PL.COD_LOCAL = P_PADRE;
  
    RETURN IP_PADRE;
  END;
  
  FUNCTION GET_IND_SIN_MARKET(cCodLocal CHAR) RETURN CHAR IS
  
           IND_PADRE CHAR(1):='N';
           i integer:=0;
  
  BEGIN
    
     SELECT COUNT(*)
            into i
       FROM PBL_LOCAL PL
      WHERE PL.COD_LOCAL_PADRE = cCodLocal
        AND PL.COD_TIP_LOCAL_VENTA = IND_LOCAL_MARKET;
     
     IF I > 0 THEN
       IND_PADRE := 'S';
     END IF;
     
     RETURN IND_PADRE;
  
  END;
  
  PROCEDURE  P_INSERT_CIERRE_DIA_MARKET (cFecCierreDia   IN CHAR ,
                                         cCodLocal       IN CHAR ,
                                         cUsuLogin       IN CHAR ,
                                         cUsuCrea        IN CHAR
                                        )
  AS  
   
      i integer := 0;
 
 BEGIN

    SELECT COUNT(*)
      INTO i
      FROM CE_CIERRE_DIA_MARKET CT
     WHERE CT.COD_LOCAL          = cCodLocal
       AND CT.FEC_CIERRE_DIA = cFecCierreDia;

    if i > 0 then
      return;
    end if; 
    
    INSERT INTO CE_CIERRE_DIA_MARKET
    (
    FEC_CIERRE_DIA,
    COD_LOCAL,
    USU_LOGIN,
    USU_CREA_CIERRE_DIA
    )
    VALUES
    (
    cFecCierreDia,
    cCodLocal,
    cUsuLogin||'-'||cCodLocal,
    cUsuCrea
    );
    COMMIT;  --                          
  EXCEPTION
    WHEN OTHERS THEN
         ROLLBACK;--
      RAISE_APPLICATION_ERROR(-20000,
                              CHR(13) ||
                              'ERROR AL GRABAR CIERRE DIA MARKET EN ');

  END;
  
  FUNCTION P_IND_CIERRE_MARKET (cFecDiaCierre     IN CHAR,
                                cCodMarket   IN CHAR)
    RETURN CHAR
    IS
           i integer:='0';
           ind_cierre_market char(1):='N';
           ind_market CHAR(1);
    BEGIN
         
      ind_market:=GET_IND_TICO(cCodMarket);
           if ind_market = 'N' then
              return 'S';
             end if;
      
        SELECT count(*)
        INTO   i
          FROM CE_CIERRE_DIA_MARKET x
         WHERE x.Fec_Cierre_Dia = cFecDiaCierre
           AND x.Cod_Local = cCodMarket;
    
       if i>0 then
         ind_cierre_market:='S';
       end if;
    
    
    RETURN ind_cierre_market;
    
    END;
  
  
  FUNCTION GET_IPS_TICOS(cCodLocal CHAR) RETURN FarmaCursor
    IS
  
    cLocalesMarket FarmaCursor;
    
  BEGIN
  
    OPEN cLocalesMarket FOR
      SELECT  P.COD_LOCAL|| 'Ã' ||P.IP_SERVIDOR_LOCAL
      FROM    PBL_LOCAL P
      WHERE   P.COD_LOCAL_PADRE = cCodLocal
      AND     P.COD_TIP_LOCAL_VENTA = IND_LOCAL_MARKET;
             
  
    RETURN cLocalesMarket;
  END;
  
 FUNCTION GET_LOCAL_TICO(cCodlocal char) RETURN CHAR IS
   vlocalTico CHAR(3);
 BEGIN
   --inicio
   --rherrera 28.10.2014
   -- obtiene local tico
   BEGIN
     SELECT P.COD_LOCAL
       INTO vlocalTico
       FROM PBL_LOCAL P
      WHERE P.COD_LOCAL_PADRE = cCodlocal;
   
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
       vlocalTico := cCodlocal;
   END;
   ---- fin              
   RETURN vlocalTico;
 END;
  
END; --FIN DEL PACKAGE BODY
/
