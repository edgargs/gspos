CREATE OR REPLACE PACKAGE PTOVENTA_DESPACHO AS

  TYPE FarmaCursor IS REF CURSOR;

  /***********************************************************************************************/
  --Descripcion: RETORNA LISTA LOS PISOS ASIGNADOS COMO DESPACHO
  --Fecha        Usuario        Comentario
  --13.05.2016   KMONCADA       CREACION   
  FUNCTION F_LISTA_PISOS_DESPACHO
  RETURN FARMACURSOR;

  /***********************************************************************************************/
  --Descripcion: RETORNA LISTADO DE IMPRESORAS QUE USAN LOS PISOS DE DESPACHO
  --Fecha        Usuario		    Comentario
  --13.05.2016   KMONCADA       CREACION
  FUNCTION F_OBTENER_IMPR_DESPACHO(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cNumPedVta_in     IN CHAR)
  RETURN FARMACURSOR;

  /***********************************************************************************************/
  --Descripcion: RETORNA FORMATO DE IMRPESION DE LA COMANDA DE DESPACHO
  --Fecha        Usuario		    Comentario
  --13.05.2016   KMONCADA       CREACION  
  FUNCTION F_OBTENER_COMANDA_DESPACHO(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cNumPedVta_in     IN CHAR,
                                      cNumPiso          IN CHAR,
                                      cNroComanda_in    IN CHAR,
                                      cTotalComanda_in  IN CHAR)
  RETURN FARMACURSOR;

  /***********************************************************************************************/
  --Descripcion: FORMATO DE IMPRESION DE COMANDAD DE DESPACHO DE UNA PROFORMA DE VENTA
  --Fecha        Usuario		    Comentario
  --13.05.2016   KMONCADA       CREACION  
  FUNCTION F_GET_COMANDA_DESPACHO_PROF(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cNumPedVta_in     IN CHAR,
                                       cNumPiso          IN CHAR,
                                       cNroComanda_in    IN CHAR,
                                       cTotalComanda_in  IN CHAR)
  RETURN FARMACURSOR;

  /***********************************************************************************************/
  --Descripcion: FORMATO DE IMPRESION DE COMANDAD DE DESPACHO DE UNA VENTA
  --Fecha        Usuario		    Comentario
  --13.05.2016   KMONCADA       CREACION  
  FUNCTION F_GET_COMANDA_DESPACHO_VTA(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cNumPedVta_in     IN CHAR,
                                      cNumPiso          IN CHAR,
                                      cNroComanda_in    IN CHAR,
                                      cTotalComanda_in  IN CHAR)
  RETURN FARMACURSOR;
 
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA_DESPACHO AS

  /***********************************************************************************************/
  --Descripcion: RETORNA LISTA LOS PISOS ASIGNADOS COMO DESPACHO
  --Fecha        Usuario        Comentario
  --13.05.2016   KMONCADA       CREACION  
  FUNCTION F_LISTA_PISOS_DESPACHO RETURN FARMACURSOR IS
    vCur        FARMACURSOR;
    vListaPisos VARCHAR2(30);
  BEGIN
    BEGIN
      SELECT TRIM(P.LLAVE_TAB_GRAL)
      INTO vListaPisos
      FROM PBL_TAB_GRAL P
      WHERE P.ID_TAB_GRAL = 599;
    EXCEPTION
      WHEN OTHERS THEN
        vListaPisos := '';
    END;
    
    OPEN vCur FOR
      SELECT X.PISO || 'Ã' || 
             'PISO ' || X.PISO
      FROM(
      SELECT VAL PISO 
        FROM (SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL 
                      FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                         REPLACE( REPLACE((REPLACE((
              vListaPisos
                  ),'&','Ã')),'<','Ë'),',','</e><e>') ||'</e></coll>'),'/coll/e'))) xt)
      ) X
      WHERE X.PISO IS NOT NULL;
    RETURN vCur;
  END;

  /***********************************************************************************************/
  --Descripcion: RETORNA LISTADO DE IMPRESORAS QUE USAN LOS PISOS DE DESPACHO
  --Fecha        Usuario		    Comentario
  --13.05.2016   KMONCADA       CREACION  
  FUNCTION F_OBTENER_IMPR_DESPACHO(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cNumPedVta_in     IN CHAR)
    RETURN FARMACURSOR IS
    vCursorPisos FARMACURSOR;
    curPisos FARMACURSOR;
    vVal VARCHAR2(100);
    vContador NUMBER;
  BEGIN
    -- DETERMINA SI HAY PISOS ASIGNADOS PARA DESPACHO
    curPisos := F_LISTA_PISOS_DESPACHO;
    vContador := 0;
    LOOP
      FETCH curPisos INTO vVal;
      EXIT WHEN curPisos%NOTFOUND;
        vContador := vContador + 1;
    END LOOP;
    CLOSE curPisos;
    
    OPEN vCursorPisos FOR
      SELECT A.PISO_DESPACHO PISO,
             NVL(A.TIPO_IMPR_TERMICA,' ') TIPO, 
             NVL(A.DESC_IMPR_LOCAL_TERM,' ') IMPRESORA
      FROM /*(
        SELECT DISTINCT SUBSTR(PROD.COD_POSICION,0,INSTR(PROD.COD_POSICION,'.',1)-1) PISO
        FROM TMP_VTA_PEDIDO_VTA_DET D,
             LGT_PROD_LOCAL PROD
        WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND D.COD_LOCAL = cCodLocal_in
        AND D.NUM_PED_VTA = cNumPedVta_in
        AND PROD.COD_GRUPO_CIA = D.COD_GRUPO_CIA
        AND PROD.COD_LOCAL = D.COD_LOCAL
        AND PROD.COD_PROD = D.COD_PROD
      ) P,*/
        VTA_IMPR_LOCAL_TERMICA A
      WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
      AND A.COD_LOCAL = cCodLocal_in
      AND NVL(A.PISO_DESPACHO,0) > 0
      AND vContador > 0 -- CUANDO EXISTAN PISOS PARA DESPACHO
      ORDER BY A.PISO_DESPACHO;
    RETURN vCursorPisos;
  END;

  /***********************************************************************************************/
  --Descripcion: RETORNA FORMATO DE IMRPESION DE LA COMANDA DE DESPACHO
  --Fecha        Usuario		    Comentario
  --13.05.2016   KMONCADA       CREACION    
  FUNCTION F_OBTENER_COMANDA_DESPACHO(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cNumPedVta_in     IN CHAR,
                                      cNumPiso          IN CHAR,
                                      cNroComanda_in    IN CHAR,
                                      cTotalComanda_in  IN CHAR)
    RETURN FARMACURSOR IS
    vIsProforma CHAR(1);
  BEGIN 
    -- DETERMINA QUE TIPO DE COMANDA IMPRIMIRA
    SELECT DECODE(COUNT(1),0,'N','S')
    INTO vIsProforma
    FROM TMP_VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in
    AND CAB.TIP_PED_VTA = '01'
    AND TRUNC(CAB.FEC_PED_VTA) = TRUNC(SYSDATE);
    
    IF vIsProforma = 'S' THEN
      RETURN F_GET_COMANDA_DESPACHO_PROF(cCodGrupoCia_in => cCodGrupoCia_in,
                                         cCodLocal_in => cCodLocal_in,
                                         cNumPedVta_in => cNumPedVta_in,
                                         cNumPiso => cNumPiso,
                                         cNroComanda_in => cNroComanda_in,
                                         cTotalComanda_in => cTotalComanda_in);
    ELSE
      RETURN F_GET_COMANDA_DESPACHO_VTA(cCodGrupoCia_in => cCodGrupoCia_in,
                                        cCodLocal_in => cCodLocal_in,
                                        cNumPedVta_in => cNumPedVta_in,
                                        cNumPiso => cNumPiso,
                                        cNroComanda_in => cNroComanda_in,
                                        cTotalComanda_in => cTotalComanda_in);
    END IF;
    
  END;

  /***********************************************************************************************/
  --Descripcion: FORMATO DE IMPRESION DE COMANDAD DE DESPACHO DE UNA PROFORMA DE VENTA
  --Fecha        Usuario		    Comentario
  --13.05.2016   KMONCADA       CREACION      
  FUNCTION F_GET_COMANDA_DESPACHO_PROF(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cNumPedVta_in     IN CHAR,
                                       cNumPiso          IN CHAR,
                                       cNroComanda_in    IN CHAR,
                                       cTotalComanda_in  IN CHAR)
    RETURN FARMACURSOR IS                                      
/*   
    CURSOR curPosXPiso IS
      SELECT DISTINCT PROD_LOC.COD_POSICION
      FROM TMP_VTA_PEDIDO_VTA_DET DET,
           LGT_PROD_LOCAL PROD_LOC
      WHERE DET.COD_GRUPO_CIA = PROD_LOC.COD_GRUPO_CIA
      AND DET.COD_LOCAL = PROD_LOC.COD_LOCAL
      AND DET.COD_PROD = PROD_LOC.COD_PROD
      AND DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND DET.COD_LOCAL = cCodLocal_in
      AND DET.NUM_PED_VTA = cNumPedVta_in
      AND PROD_LOC.COD_POSICION LIKE cNumPiso||'.%'
      ORDER BY PROD_LOC.COD_POSICION;

    CURSOR curDetalleComanda(codPosicion IN CHAR) IS
      SELECT '  '||
             RPAD(A.CODIGO, 7, ' ') || 
             RPAD(SUBSTR(A.DESCRIPCION,0,18), 18, ' ') ||' '||
             RPAD(A.CANTIDAD, 7, ' ') || ' ' ||
             RPAD(A.PRESENTACION,12,' ') ||
             '  '||
             RPAD(SUBSTR(A.LABORATORIO,0,20), 20, ' ') ||' '||
             RPAD(A.NUM_LOTE, 20, ' ')
             DETALLE
      
      FROM (
      SELECT DET.COD_PROD CODIGO,
             PROD.DESC_PROD DESCRIPCION,
             DECODE(DET_LOTE.VAL_FRAC,1,DET_LOTE.CANT_ATENDIDA||'',DET_LOTE.CANT_ATENDIDA||'/'||DET_LOTE.VAL_FRAC) CANTIDAD,
             SUBSTR(TRIM(DECODE(DET_LOTE.VAL_FRAC,1,PROD.DESC_UNID_PRESENT,PROD_LOC.UNID_VTA)),0,24) PRESENTACION,
             TRIM(DET_LOTE.NUM_LOTE_PROD) NUM_LOTE,
             SUBSTR(TRIM(LAB.NOM_LAB), 0, 20) LABORATORIO
      FROM TMP_VTA_PEDIDO_VTA_DET DET,
           TMP_VTA_INSTITUCIONAL_DET DET_LOTE,
           LGT_PROD_LOCAL PROD_LOC,
           LGT_PROD       PROD,
           LGT_LAB        LAB
      WHERE DET.COD_GRUPO_CIA = DET_LOTE.COD_GRUPO_CIA
      AND DET.COD_LOCAL = DET_LOTE.COD_LOCAL
      AND DET.NUM_PED_VTA = DET_LOTE.NUM_PED_VTA
      AND DET.SEC_PED_VTA_DET = DET_LOTE.SEC_PED_VTA_DET
      AND DET.COD_GRUPO_CIA = PROD_LOC.COD_GRUPO_CIA
      AND DET.COD_LOCAL = PROD_LOC.COD_LOCAL
      AND DET.COD_PROD = PROD_LOC.COD_PROD
      AND PROD_LOC.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
      AND PROD_LOC.COD_PROD = PROD.COD_PROD
      AND LAB.COD_LAB = PROD.COD_LAB
      AND DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND DET.COD_LOCAL = cCodLocal_in
      AND DET.NUM_PED_VTA = cNumPedVta_in
      AND PROD_LOC.COD_POSICION = codPosicion
      ORDER BY DET.COD_PROD, DET.SEC_PED_VTA_DET)A;
      
    filaPosXPiso curPosXPiso%ROWTYPE;
    filaDetalleComanda curDetalleComanda%ROWTYPE;
    vIdDoc IMPRESION_TERMICA.ID_DOC%TYPE;
    vIpPc IMPRESION_TERMICA.ID_DOC%TYPE;
    vValor VARCHAR2(1000);
    vNumPedDiario TMP_VTA_PEDIDO_VTA_CAB.NUM_PED_DIARIO%TYPE;
    vNomLocal PBL_LOCAL.DESC_CORTA_LOCAL%TYPE;
    cursorComprobante FARMACURSOR;
    vFechaPedido TMP_VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE;*/
  BEGIN
/*
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc := FARMA_PRINTER.F_GET_IP_SESS;
    
    SELECT CAB.NUM_PED_DIARIO,
           CAB.FEC_PED_VTA
    INTO vNumPedDiario,
         vFechaPedido
    FROM TMP_VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;
    
    SELECT L.DESC_CORTA_LOCAL
    INTO vNomLocal
    FROM PBL_LOCAL L
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
    AND L.COD_LOCAL = cCodLocal_in;
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => cNroComanda_in||' DE '||cTotalComanda_in,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT);
                                 
    FARMA_PRINTER.P_AGREGA_BARCODE_CODE39(vIdDoc_in => vIdDoc,
                                          vIpPc_in => vIpPc,
                                          vValor_in => vNumPedDiario);
                                          
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                         
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => 'COMANDA DE DESPACHO',
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                        
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => cCodLocal_in||'-'||vNomLocal,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN); 
                                 
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => 'PISO '||cNumPiso,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT); 
                                                           
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
    
    
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc,
                                         vValor_in => 'FECHA PROFORMA : ',
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => TO_CHAR(vFechaPedido,'DD/MM/YYYY HH24:MI:SS'),
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT); 
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                                                            
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => 'DETALLE DE PROFORMA',
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN);
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, 
                                                 vIpPc_in => vIpPc,
                                                 vCaracter => '-',
                                                 vTamanio_in => FARMA_PRINTER.TAMANIO_0);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => ' '||RPAD('CODIGO',8,' ')||RPAD('DESCRIPCION',18,' ')||RPAD('CANT.',7,' ')||RPAD('PRESENTACION',12,' '),
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                 vJustifica_in => FARMA_PRINTER.JUSTIFICA_NO);
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, 
                                                 vIpPc_in => vIpPc,
                                                 vCaracter => '-',
                                                 vTamanio_in => FARMA_PRINTER.TAMANIO_0);
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc);                             
    OPEN curPosXPiso;
    LOOP
      FETCH curPosXPiso INTO filaPosXPiso;
      EXIT WHEN curPosXPiso%NOTFOUND;
        FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => 'POSICION: '||filaPosXPiso.COD_POSICION,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                 vAlineado_in => FARMA_PRINTER.ALING_IZQ);   
        OPEN curDetalleComanda(filaPosXPiso.COD_POSICION);
        LOOP
          FETCH curDetalleComanda INTO filaDetalleComanda;
          EXIT WHEN curDetalleComanda%NOTFOUND;
            FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc, 
                                         vValor_in => filaDetalleComanda.DETALLE,
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                         vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                         vJustifica_in => FARMA_PRINTER.JUSTIFICA_NO);
            FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                                vIpPc_in => vIpPc);
        END LOOP;
        CLOSE curDetalleComanda;
      NULL;
    END LOOP;
    CLOSE curPosXPiso;
    cursorComprobante := FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc,
                                                                         vIpPc_in => vIpPc);
    RETURN cursorComprobante;*/
    RETURN NULL;
  END;

  /***********************************************************************************************/
  --Descripcion: FORMATO DE IMPRESION DE COMANDAD DE DESPACHO DE UNA VENTA
  --Fecha        Usuario		    Comentario
  --13.05.2016   KMONCADA       CREACION    
  FUNCTION F_GET_COMANDA_DESPACHO_VTA(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cNumPedVta_in     IN CHAR,
                                      cNumPiso          IN CHAR,
                                      cNroComanda_in    IN CHAR,
                                      cTotalComanda_in  IN CHAR)
    RETURN FARMACURSOR IS                                      
   
    CURSOR curPosXPiso IS
      SELECT DISTINCT '1'/*PROD_LOC.COD_POSICION*/ COD_POSICION
      FROM VTA_PEDIDO_VTA_DET DET,
           LGT_PROD_LOCAL PROD_LOC
      WHERE DET.COD_GRUPO_CIA = PROD_LOC.COD_GRUPO_CIA
      AND DET.COD_LOCAL = PROD_LOC.COD_LOCAL
      AND DET.COD_PROD = PROD_LOC.COD_PROD
      AND DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND DET.COD_LOCAL = cCodLocal_in
      AND DET.NUM_PED_VTA = cNumPedVta_in
      --AND PROD_LOC.COD_POSICION LIKE cNumPiso||'.%'
      --ORDER BY PROD_LOC.COD_POSICION
      ;


    CURSOR curDetalleComanda(codPosicion IN CHAR) IS
      SELECT '  '||
             RPAD(A.CODIGO, 7, ' ') || 
             RPAD(SUBSTR(A.DESCRIPCION,0,18), 18, ' ') ||' '||
             RPAD(A.CANTIDAD, 7, ' ') || ' ' ||
             RPAD(A.PRESENTACION,12,' ') ||
             '  '||
             RPAD(SUBSTR(A.LABORATORIO,0,20), 20, ' ') ||' '||
             RPAD(A.NUM_LOTE, 20, ' ')
             DETALLE
      
      FROM (
            SELECT DET.COD_PROD CODIGO,
                   PROD.DESC_PROD DESCRIPCION,
                   --DECODE(DET_LOTE.VAL_FRAC,1,DET_LOTE.CANT_ATENDIDA||'',DET_LOTE.CANT_ATENDIDA||'/'||DET_LOTE.VAL_FRAC) CANTIDAD,
                   DECODE(DET.VAL_FRAC,1,DET.CANT_ATENDIDA||'',DET.CANT_ATENDIDA||'/'||DET.VAL_FRAC) CANTIDAD,
                   --SUBSTR(TRIM(DECODE(DET_LOTE.VAL_FRAC,1,PROD.DESC_UNID_PRESENT,PROD_LOC.UNID_VTA)),0,24) PRESENTACION,
                   SUBSTR(TRIM(DECODE(DET.VAL_FRAC,1,PROD.DESC_UNID_PRESENT,PROD_LOC.UNID_VTA)),0,24) PRESENTACION,
                   ' '/*TRIM(DET_LOTE.NUM_LOTE_PROD)*/ NUM_LOTE,
                   SUBSTR(TRIM(LAB.NOM_LAB), 0, 20) LABORATORIO
            FROM VTA_PEDIDO_VTA_DET DET,
                 LGT_PROD_LOCAL PROD_LOC,
                 LGT_PROD       PROD,
                 LGT_LAB        LAB
            WHERE DET.COD_GRUPO_CIA = PROD_LOC.COD_GRUPO_CIA
            AND DET.COD_LOCAL = PROD_LOC.COD_LOCAL
            AND DET.COD_PROD = PROD_LOC.COD_PROD
            AND PROD_LOC.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
            AND PROD_LOC.COD_PROD = PROD.COD_PROD
            AND LAB.COD_LAB = PROD.COD_LAB
            AND DET.COD_GRUPO_CIA = cCodGrupoCia_in
            AND DET.COD_LOCAL = cCodLocal_in
            AND DET.NUM_PED_VTA = cNumPedVta_in
      --      AND PROD_LOC.COD_POSICION = codPosicion
            ORDER BY DET.COD_PROD, DET.SEC_PED_VTA_DET
         )A;
      
    filaPosXPiso curPosXPiso%ROWTYPE;
    filaDetalleComanda curDetalleComanda%ROWTYPE;
    vIdDoc IMPRESION_TERMICA.ID_DOC%TYPE;
    vIpPc IMPRESION_TERMICA.ID_DOC%TYPE;
    vValor VARCHAR2(1000);
    vNumPedDiario TMP_VTA_PEDIDO_VTA_CAB.NUM_PED_DIARIO%TYPE;
    vNomLocal PBL_LOCAL.DESC_CORTA_LOCAL%TYPE;
    cursorComprobante FARMACURSOR;
    vFechaPedido TMP_VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE;
  BEGIN

    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc := FARMA_PRINTER.F_GET_IP_SESS;
    
    SELECT CAB.NUM_PED_DIARIO,
           CAB.FEC_PED_VTA
    INTO vNumPedDiario,
         vFechaPedido
    FROM VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;
    
    SELECT L.DESC_CORTA_LOCAL
    INTO vNomLocal
    FROM PBL_LOCAL L
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
    AND L.COD_LOCAL = cCodLocal_in;
    
/*    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => cNroComanda_in||' DE '||cTotalComanda_in,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT);*/
                                 
    FARMA_PRINTER.P_AGREGA_BARCODE_CODE39(vIdDoc_in => vIdDoc,
                                          vIpPc_in => vIpPc,
                                          vValor_in => cNumPedVta_in/*vNumPedDiario*/);
                                          
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                         
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => 'COMANDA PARA DESPACHO',
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                        
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => cCodLocal_in||'-'||vNomLocal,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN); 
                                 
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => 'PISO '||cNumPiso,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT); 
                                                           
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
    
    
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc,
                                         vValor_in => 'FECHA PEDIDO : ',
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => TO_CHAR(vFechaPedido,'DD/MM/YYYY HH24:MI:SS'),
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT); 
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                                                            
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => 'DETALLE DE PROFORMA',
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN);
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, 
                                                 vIpPc_in => vIpPc,
                                                 vCaracter => '-',
                                                 vTamanio_in => FARMA_PRINTER.TAMANIO_0);

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => ' '||RPAD('CODIGO',8,' ')||RPAD('DESCRIPCION',18,' ')||RPAD('CANT.',7,' ')||RPAD('PRESENTACION',12,' '),
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                 vJustifica_in => FARMA_PRINTER.JUSTIFICA_NO);
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, 
                                                 vIpPc_in => vIpPc,
                                                 vCaracter => '-',
                                                 vTamanio_in => FARMA_PRINTER.TAMANIO_0);
                                 
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc);                             
    OPEN curPosXPiso;
    LOOP
      FETCH curPosXPiso INTO filaPosXPiso;
      EXIT WHEN curPosXPiso%NOTFOUND;
        /*FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                 vIpPc_in => vIpPc, 
                                 vValor_in => 'POSICION: '||filaPosXPiso.COD_POSICION,
                                 vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                 vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                 vAlineado_in => FARMA_PRINTER.ALING_IZQ);*/
        OPEN curDetalleComanda(filaPosXPiso.COD_POSICION);
        LOOP
          FETCH curDetalleComanda INTO filaDetalleComanda;
          EXIT WHEN curDetalleComanda%NOTFOUND;
            FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc, 
                                         vValor_in => filaDetalleComanda.DETALLE,
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                         vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                         vJustifica_in => FARMA_PRINTER.JUSTIFICA_NO);
            FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                                vIpPc_in => vIpPc);
        END LOOP;
        CLOSE curDetalleComanda;
      NULL;
    END LOOP;
    CLOSE curPosXPiso;
    cursorComprobante := FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc,
                                                                         vIpPc_in => vIpPc);
    RETURN cursorComprobante;
  END;

END;
/
