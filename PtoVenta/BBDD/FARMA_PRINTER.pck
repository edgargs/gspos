CREATE OR REPLACE PACKAGE FARMA_PRINTER is

 TYPE FarmaCursor IS REF CURSOR;
 /******* * TIPO DE VALOR * ********/  
 SEPARADOR   CHAR(2) := CHR(260); --  SE COLOCA EL CODIGO PARA QUE SOPORTE EN CUALQUIER S.O
 
 SEP_PALABRA   VARCHAR(5) := '‰';
 
 -- BOLD PALABRA 
 BOL_INI   VARCHAR(5) := '√i√';  
 BOL_FIN   VARCHAR(5) := '√f√';   
  
 /******* * TIPO DE VALOR * ********/ 
 V_TEXTO     INTEGER := 1; 
 V_PDF417    INTEGER := 2; 
 V_BARCODE   INTEGER := 3; 
 V_QR        INTEGER := 4; 
 V_LOGO      INTEGER := 5; 
 V_REPETIR_SIMBOLO INTEGER := 6;  
 V_LINEA_BLANCO    INTEGER := 7; 
 /******* * TAMA—O DE LETRA * ********/ 
 T_SIZE_UNO    INTEGER := 9; 
 T_SIZE_DOS    INTEGER := 0; 
 T_SIZE_TRES   INTEGER := 1; 
 T_SIZE_CUATRO INTEGER := 2; 
 T_SIZE_CINCO  INTEGER := 3; 
 T_SIZE_SEIS   INTEGER := 4; 
 T_SIZE_SIETE  INTEGER := 5; 
 /******* * ALINEACION DE IMPRESION * ********/ 
 A_IZQ    INTEGER := 1;
 A_CENTRO INTEGER := 2;
 A_DER    INTEGER := 3;
 /******* * ACTIVAR NEGRITA * ********/ 
 BOLD_A   INTEGER := 1;
 BOLD_I   INTEGER := 0; 
 /******* * ACTIVAR ESPACIADO ENTRE LINEAS * ********/ 
 ESPACIADO_A   INTEGER := 1; 
 ESPACIADO_I   INTEGER := 0; 
 /******* * INVERTIDO_COLOR * ********/
  INVERTIDO_COLOR_A   INTEGER := 1; 
  INVERTIDO_COLOR_I   INTEGER := 0;
  
  -- KMONCADA 01.06.2015 NUEVO METODO DE IMPRESION
  TD_TEXTO            CHAR(1) := 'T';
  TD_PDF417           CHAR(1) := 'P';
  TD_LOGO             CHAR(1) := 'L';
  TD_COD_BAR_EAN13    CHAR(1) := 'C';
  TD_COD_BAR_CODE39   CHAR(1) := 'B';
  
  SIZE_0              CHAR(1) := '0';
  SIZE_1              CHAR(1) := '1';
  SIZE_2              CHAR(1) := '2';
  SIZE_3              CHAR(1) := '3';
  SIZE_4              CHAR(1) := '4';
  SIZE_5              CHAR(1) := '5';
  SIZE_6              CHAR(1) := '6';
  SIZE_7              CHAR(1) := '7';
  
  ALING_IZQ           CHAR(1) := 'I';
  ALING_CEN           CHAR(1) := 'C';
  ALING_DER           CHAR(1) := 'D';
  ALING_JUSTI         CHAR(1) := 'J';
  
  BOLD_ACT            CHAR(1) := 'S';
  BOLD_DESACT         CHAR(1) := 'N';
  
  SUBRAY_ACT          CHAR(1) := 'S';
  SUBRAY_DESACT       CHAR(1) := 'N';
  
  LINEADO_SIMPLE      CHAR(1) := '1';
  LINEADO_DOBLE       CHAR(1) := '2';
  
  INVERSO_ACT         CHAR(1) := 'S';
  INVERSO_DESACT      CHAR(1) := 'N';
  
  SALTO_LINEA_ACT     CHAR(1) := 'S';
  SALTO_LINEA_DESACT  CHAR(1) := 'N';
  
  /* *************************************************************** */
  -- FUNCIONES A DECLARAR -- 
  /* *************************************************************** */
  FUNCTION F_VAR_ID_DOC  RETURN VARCHAR2 ; 
  /* *************************************************************** */    
  FUNCTION F_VAR_IP_SESS RETURN VARCHAR2;
  /* *************************************************************** */      
  FUNCTION F_INT_LINEA_DOC (vIdDoc_in in varchar2,
                            vIpPc_in  in varchar2)
            RETURN INTEGER ;
  /* *************************************************************** */
  FUNCTION  F_CREA_DOC(vTamanio_in in integer,
                       vAlineado_in in integer,
                       vNegrita_in in integer,
                       vEspaciado_in in integer,
                       vInvColor_in  in integer) RETURN VARCHAR2;
  /* *************************************************************** */                                         
  FUNCTION F_DOC_TAMANIO(vIdDoc_in in varchar2,
                        vIpPc_in  in varchar2)  RETURN integer ;
  /* *************************************************************** */                                          
  FUNCTION F_DOC_ALINEACION(vIdDoc_in in varchar2,
                           vIpPc_in  in varchar2)  RETURN integer ;
  /* *************************************************************** */                                             
  FUNCTION F_DOC_NEGRITA(vIdDoc_in in varchar2,
                        vIpPc_in  in varchar2)  RETURN integer ;
  /* *************************************************************** */                                          
  FUNCTION F_DOC_ESPACIADO(vIdDoc_in in varchar2,
                        vIpPc_in  in varchar2)  RETURN integer ;
  /* *************************************************************** */                                          
  FUNCTION F_DOC_COLOR_INV(vIdDoc_in in varchar2,
                           vIpPc_in  in varchar2)  RETURN integer ;                       
  /* *************************************************************** */                  
  PROCEDURE P_ADD_LINEA_DOC(vIdDoc_in in varchar2,
                            vIpPc_in  in varchar2,
                            vTipVal_in in integer,
                            vTamanio_in in integer,
                            vAlineado_in in integer,
                            vNegrita_in in integer,
                            vEspaciado_in in integer,
                            vInvColor_in  in integer,
                            vCadena_in   in varchar2);
  /* *************************************************************** */                                              
  PROCEDURE P_CODE_PDF417(vIdDoc_in  in varchar2,
                      vIpPc_in   in varchar2,
                      vCadena_in in varchar2
                      );
  /* *************************************************************** */                                                                    
  PROCEDURE P_CODE_QR(vIdDoc_in  in varchar2,
                      vIpPc_in   in varchar2,
                      vCadena_in in varchar2
                      );
  /* *************************************************************** */                                                                    
  PROCEDURE P_CODE_BAR(vIdDoc_in  in varchar2,
                      vIpPc_in   in varchar2,
                      vCadena_in in varchar2
                      );
  /* *************************************************************** */                                                                    
  PROCEDURE P_LOGO_MARCA(vIdDoc_in  in varchar2,
                       vIpPc_in   in varchar2,
                       vCodGrupoCia in varchar2,
                       vCodLocal_in in varchar2);
  /* *************************************************************** */                                                                    
  PROCEDURE P_LINEA_BLANCO(vIdDoc_in  in varchar2,
                           vIpPc_in   in varchar2);                         
  /* *************************************************************** */                                                                                          
  PROCEDURE P_CADENA(vIdDoc_in in varchar2,
                     vIpPc_in  in varchar2,
                     vTamanio_in in integer,
                     vAlineado_in in integer,
                     vNegrita_in in integer,
                     vEspaciado_in in integer,
                     vInvColor_in  in integer,
                     vCadena_in   in varchar2);
  /* *************************************************************** */
  PROCEDURE P_CADENA(vIdDoc_in in varchar2,
                     vIpPc_in  in varchar2,
                     vCadena_in   in varchar2);
  /* *************************************************************** */                     
FUNCTION F_CUR_DATA_DOC(vIdDoc_in in varchar2,
                        vIpPc_in  in varchar2)
  RETURN FarmaCursor;
  
  /* *************************************************************** */
  -- METODO DE IMPRESION DE VOUCHER DE AFILIACION AL PROGRAMA DE PUNTOS
  -- KMONCADA 02.06.2015
  FUNCTION F_IMPR_AFILIACION_PTOS(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumDoc_in      IN CHAR,
                                  cSecUsu_in      IN CHAR)
    RETURN FarmaCursor;
END FARMA_PRINTER;
/
CREATE OR REPLACE PACKAGE BODY FARMA_PRINTER is
/* *************************************************************** */
  FUNCTION F_VAR_ID_DOC  RETURN VARCHAR2  IS
    vCampo varchar2(4000);
    begin
    
    select trim(TO_char(systimestamp, 'ddmmyyyyHH24MISSFF'))
    into   vCampo
    from   dual;
    
    RETURN vCampo ;

  END;
/* *************************************************************** */
  FUNCTION F_VAR_IP_SESS  RETURN VARCHAR2  IS
  BEGIN
   RETURN SYS_CONTEXT('USERENV','IP_ADDRESS');
  END;
/* *************************************************************** */
  FUNCTION F_INT_LINEA_DOC (vIdDoc_in in varchar2,
                            vIpPc_in  in varchar2)
            RETURN INTEGER  IS
    nValPos integer;        
  BEGIN
   
   SELECT count(1)+1
   into   nValPos
   FROM   DOC_FARMA_PRINT_DET D  
   where  d.ip_pc  = vIpPc_in
   and    d.id_doc = vIdDoc_in;
  
   RETURN nValPos;
  END;
/* *************************************************************** */  
FUNCTION F_CREA_DOC(vTamanio_in in integer,
                       vAlineado_in in integer,
                       vNegrita_in in integer,
                       vEspaciado_in in integer,
                       vInvColor_in  in integer) RETURN VARCHAR2 IS
   vIdDoc DOC_FARMA_PRINT_CAB.ID_DOC%type;
  BEGIN
    vIdDoc := F_VAR_ID_DOC;
    insert into DOC_FARMA_PRINT_CAB
    (ip_pc, 
     id_doc, 
     tamanio, 
     alineacion, 
     negrita, 
     espaciado, 
     inv_color
     )
     values
     (F_VAR_IP_SESS,
      vIdDoc,
      vTamanio_in,
      vAlineado_in,
      vNegrita_in,
      vEspaciado_in,
      vInvColor_in);
      
      return vIdDoc;
  END;  
/* *************************************************************** */
  FUNCTION F_DOC_TAMANIO(vIdDoc_in in varchar2,
                        vIpPc_in  in varchar2)  RETURN integer IS
    vRpt integer;
  BEGIN
   SELECT tamanio  into vRpt FROM DOC_FARMA_PRINT_CAB WHERE  ip_pc = vIpPc_in AND id_doc = vIdDoc_in;
   RETURN vRpt;
  END;
/* *************************************************************** */
  FUNCTION F_DOC_ALINEACION(vIdDoc_in in varchar2,
                           vIpPc_in  in varchar2)  RETURN integer IS
    vRpt integer;
  BEGIN
   SELECT alineacion  into vRpt FROM DOC_FARMA_PRINT_CAB WHERE  ip_pc = vIpPc_in AND id_doc = vIdDoc_in;
   RETURN vRpt;
  END;
/* *************************************************************** */
  FUNCTION F_DOC_NEGRITA(vIdDoc_in in varchar2,
                        vIpPc_in  in varchar2)  RETURN integer IS
    vRpt integer;
  BEGIN
   SELECT negrita  into vRpt FROM DOC_FARMA_PRINT_CAB WHERE  ip_pc = vIpPc_in AND id_doc = vIdDoc_in;
   RETURN vRpt;
  END;
/* *************************************************************** */
  FUNCTION F_DOC_ESPACIADO(vIdDoc_in in varchar2,
                        vIpPc_in  in varchar2)  RETURN integer IS
    vRpt integer;
  BEGIN
   SELECT espaciado   into vRpt FROM DOC_FARMA_PRINT_CAB WHERE  ip_pc = vIpPc_in AND id_doc = vIdDoc_in;
   RETURN vRpt;
  END;
/* *************************************************************** */
  FUNCTION F_DOC_COLOR_INV(vIdDoc_in in varchar2,
                           vIpPc_in  in varchar2)  RETURN integer IS
    vRpt integer;
  BEGIN
   SELECT inv_color  into vRpt FROM DOC_FARMA_PRINT_CAB WHERE  ip_pc = vIpPc_in AND id_doc = vIdDoc_in;
   RETURN vRpt;
  END;
/* *************************************************************** */
  PROCEDURE P_ADD_LINEA_DOC(vIdDoc_in in varchar2,
                            vIpPc_in  in varchar2,
                            vTipVal_in in integer,
                            vTamanio_in in integer,
                            vAlineado_in in integer,
                            vNegrita_in in integer,
                            vEspaciado_in in integer,
                            vInvColor_in  in integer,
                            vCadena_in   in varchar2) AS
  BEGIN
    insert into DOC_FARMA_PRINT_DET
    (ip_pc, 
     id_doc, 
     orden, 
     tipo_val, 
     tamanio, 
     alineacion, 
     negrita, 
     espaciado, 
     inv_color, 
     val_cadena
     )
     values
     (vIpPc_in,
     vIdDoc_in,
      f_int_linea_doc(vIdDoc_in,vIpPc_in),
      vTipVal_in,
      vTamanio_in,
      vAlineado_in,
      vNegrita_in,
      vEspaciado_in,
      vInvColor_in,
      vCadena_in);
         /* dbms_output.put_line('insert');      
          dbms_output.put_line(sql%rowcount);
          commit;*/
  END;    
/* *************************************************************** */
  
  PROCEDURE P_CODE_PDF417(vIdDoc_in  in varchar2,
                          vIpPc_in   in varchar2,
                          vCadena_in in varchar2
                          ) AS
  BEGIN
    p_add_linea_doc(vIdDoc_in,vIpPc_in,
                    V_PDF417,
                    T_SIZE_UNO,
                    A_CENTRO,
                    BOLD_I,
                    ESPACIADO_I,
                    INVERTIDO_COLOR_I,
                    vCadena_in);
  END;  
/* *************************************************************** */
  PROCEDURE P_CODE_QR(vIdDoc_in  in varchar2,
                      vIpPc_in   in varchar2,
                      vCadena_in in varchar2
                      ) AS
  BEGIN
    p_add_linea_doc(vIdDoc_in,vIpPc_in,
                    V_QR,
                    T_SIZE_UNO,
                    A_CENTRO,
                    BOLD_I,
                    ESPACIADO_I,
                    INVERTIDO_COLOR_I,
                    vCadena_in);
  END;  
/* *************************************************************** */
  PROCEDURE P_CODE_BAR(vIdDoc_in  in varchar2,
                      vIpPc_in   in varchar2,
                      vCadena_in in varchar2
                      ) AS
  BEGIN
    p_add_linea_doc(vIdDoc_in,vIpPc_in,
                    V_BARCODE,
                    T_SIZE_UNO,
                    A_CENTRO,
                    BOLD_I,
                    ESPACIADO_I,
                    INVERTIDO_COLOR_I,
                    vCadena_in);
  END;  
/* *************************************************************** */  
PROCEDURE P_LOGO_MARCA(vIdDoc_in  in varchar2,
                       vIpPc_in   in varchar2,
                       vCodGrupoCia in varchar2,
                       vCodLocal_in in varchar2) as
 vCodMarca varchar2(20);                       
begin

  SELECT COD_MARCA 
  INTO vCodMarca
  FROM PBL_LOCAL
  WHERE COD_GRUPO_CIA = vCodGrupoCia
  AND COD_LOCAL       = vCodLocal_in;  

    p_add_linea_doc(vIdDoc_in,vIpPc_in,
                    V_LOGO,
                    T_SIZE_UNO,
                    A_CENTRO,
                    BOLD_I,
                    ESPACIADO_I,
                    INVERTIDO_COLOR_I,
                    vCodMarca);  
end;  
/* *************************************************************** */  
PROCEDURE P_LINEA_BLANCO(vIdDoc_in  in varchar2,
                         vIpPc_in   in varchar2) as
begin
    p_add_linea_doc(vIdDoc_in,vIpPc_in,
                    V_LINEA_BLANCO,
                    T_SIZE_UNO,
                    A_CENTRO,
                    BOLD_I,
                    ESPACIADO_I,
                    INVERTIDO_COLOR_I,
                    ' ');  
end;                       
/* *************************************************************** */
  PROCEDURE P_CADENA(vIdDoc_in in varchar2,
                     vIpPc_in  in varchar2,
                     vTamanio_in in integer,
                     vAlineado_in in integer,
                     vNegrita_in in integer,
                     vEspaciado_in in integer,
                     vInvColor_in  in integer,
                     vCadena_in   in varchar2) AS

    CURSOR curLineas is
    SELECT EXTRACTVALUE(xt.column_value, 'e') cadena
      FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                             REPLACE(
                                                     vCadena_in,
                                                     SEPARADOR,
                                                     '</e><e>') ||
                                             '</e></coll>'),
                                     '/coll/e'))) xt;
    fSC curLineas%rowtype;
                         
  BEGIN
    if trim(vCadena_in) is not null  then    
      open curLineas;
      LOOP
        FETCH curLineas INTO fSC;
        EXIT WHEN curLineas%NOTFOUND;        
        p_add_linea_doc(vIdDoc_in,vIpPc_in,
                        V_TEXTO,
                        vTamanio_in,
                        vAlineado_in,
                        vNegrita_in,
                        vEspaciado_in,
                        vInvColor_in,
                        fSC.Cadena);

       END LOOP;
       close curLineas; 
    end if;                      
  END;  
/* *************************************************************** */
  PROCEDURE P_CADENA(vIdDoc_in in varchar2,
                     vIpPc_in  in varchar2,
                     vCadena_in   in varchar2) AS
    CURSOR curLineas is
    SELECT EXTRACTVALUE(xt.column_value, 'e') cadena
      FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                             REPLACE(
                                                     vCadena_in,
                                                     SEPARADOR,
                                                     '</e><e>') ||
                                             '</e></coll>'),
                                     '/coll/e'))) xt;
    fSC curLineas%rowtype;                     
  BEGIN
    if trim(vCadena_in) is not null then
      open curLineas;
      LOOP
        FETCH curLineas INTO fSC;
        EXIT WHEN curLineas%NOTFOUND;        
        p_add_linea_doc(vIdDoc_in,vIpPc_in,
                        V_TEXTO,
                        F_DOC_TAMANIO(vIdDoc_in,vIpPc_in),
                        F_DOC_ALINEACION(vIdDoc_in,vIpPc_in),
                        F_DOC_NEGRITA(vIdDoc_in,vIpPc_in),
                        F_DOC_ESPACIADO(vIdDoc_in,vIpPc_in),
                        F_DOC_COLOR_INV(vIdDoc_in,vIpPc_in),
                        fSC.Cadena);
     END LOOP;
     close curLineas;                                  
    end if; 
  END;
/* *************************************************************** */  
FUNCTION F_CUR_DATA_DOC(vIdDoc_in in varchar2,
                        vIpPc_in  in varchar2)
  RETURN FarmaCursor
  IS
    curData FarmaCursor;
  BEGIN
    OPEN curData FOR
    SELECT IP_PC, 
           ID_DOC, 
           ORDEN, 
           TIPO_VAL, 
           TAMANIO, 
           ALINEACION, 
           NEGRITA, 
           ESPACIADO, 
           INV_COLOR, 
           VAL_CADENA
    FROM   DOC_FARMA_PRINT_DET T
    WHERE T.IP_PC = vIpPc_in
          AND T.ID_DOC = vIdDoc_in
    ORDER BY T.ORDEN ASC;

    RETURN curData;
  END;
/* *************************************************************** */  
  
  FUNCTION F_IMPR_AFILIACION_PTOS(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumDoc_in      IN CHAR,
                                  cSecUsu_in      IN CHAR)
    RETURN FarmaCursor
    IS
    cursorComprobante    FarmaCursor;
    vIDENTIFICA       VARCHAR2(30);
  BEGIN
    
    vIDENTIFICA := F_VAR_ID_DOC;
   
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO, NEGRITA)
    SELECT vIDENTIFICA, PC.RAZ_SOC_CIA || ' - RUC: ' || PC.NUM_RUC_CIA, SIZE_1, BOLD_ACT
      FROM PBL_CIA PC, PBL_LOCAL LOC
     WHERE LOC.COD_GRUPO_CIA = cCodGrupoCia_in
       AND LOC.COD_LOCAL = cCodLocal_in
       AND LOC.COD_CIA = PC.COD_CIA;
       
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO) VALUES (vIDENTIFICA, ' ',SIZE_1);
    
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO, NEGRITA, SALTO_LINEA)
    SELECT vIDENTIFICA, A.VAL, SIZE_1,
           CASE
             WHEN MOD(A.Q, 3) = 1 THEN 'S'
             ELSE 'N'
           END BOLD,
           CASE
             WHEN MOD(A.Q, 3) = 1 OR A.Q = 8 OR A.Q = 30 THEN 'N'
             ELSE 'S'
           END SALTO
    FROM (SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),
                                  '√','&')),'À','<') VAL,
                 CASE
                   WHEN ROWNUM > 27 THEN (ROWNUM + 3)
                   WHEN ROWNUM > 8 THEN (ROWNUM + 1)
                   ELSE ROWNUM
                 END Q
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                                   REPLACE(REPLACE((REPLACE((
                  SELECT RPAD('DNI/CE:',12) || '@' ||
                         A.DNI_CLI || '@' || ' ' || '@' ||
                         RPAD('Nombre:', 12) || '@' ||
                         NVL(A.NOM_CLI || ' ' || A.APE_PAT_CLI || ' ' || A.APE_MAT_CLI, '_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _') || '@' || ' ' || '@' ||
                         RPAD('FN:',12) || '@' ||
                         RPAD(NVL(TO_CHAR(A.FEC_NAC_CLI, 'dd/MM/yyyy'),'_ _ _ _ _ '), 14) || ' ' || '@' ||
                         RPAD('Sexo:', 8) || '@' || NVL(A.SEXO_CLI,'_ _ ') || '@' || ' ' || '@' ||
                         RPAD('Email:', 12) || '@' ||
                         NVL(A.EMAIL,'_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _') || '@' || ' ' || '@' ||
                         RPAD('Celular:',12) || '@' ||
                         NVL(A.CELL_CLI,'_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _') || '@' || ' ' || '@' ||
                         RPAD('Telf.Fijo:', 12) || '@' ||
                         NVL(A.FONO_CLI,'_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _') || '@' || ' ' || '@' ||
                         RPAD('DirecciÛn:',12) || '@' ||
                         NVL(a.dir_cli,'_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _') || '@' || ' ' || '@' || 
                         CASE
                           WHEN a.dir_cli IS NULL THEN
                             RPAD(' ', 12) || '@' ||'_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _'
                           ELSE
                             ' '
                         END || '@' || ' ' || '@' || ' ' || '@' ||
                         RPAD('Firma:', 12) || '@' || '_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _' || '@' || ' '
                  FROM PBL_CLIENTE A
                  WHERE A.DNI_CLI = cNumDoc_in)
                  ,'&','√')),'<','À'),'@','</e><e>') || '</e></coll>'),'/coll/e'))) xt) A;
    
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO) VALUES (vIDENTIFICA,' ',SIZE_1);
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO) VALUES (vIDENTIFICA,' ',SIZE_1);
    
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO, ALINEACION)
    SELECT vIDENTIFICA, A.DESC_LARGA, SIZE_0, ALING_JUSTI
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL = 687;
    
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO) VALUES (vIDENTIFICA, ' ',SIZE_1);
    
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR,TIPO_DATO, ALINEACION) 
    VALUES (vIDENTIFICA, cNumDoc_in, TD_COD_BAR_CODE39, ALING_CEN);
    
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO) VALUES (vIDENTIFICA, ' ',SIZE_1);
    
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO, ALINEACION)
    SELECT vIDENTIFICA, A.DESC_LARGA, SIZE_0, ALING_JUSTI
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL = 506;
    
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO) VALUES (vIDENTIFICA, ' ',SIZE_1);
    
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO, NEGRITA, SALTO_LINEA) 
    VALUES (vIDENTIFICA, 'CodV: ', SIZE_1, BOLD_ACT, SALTO_LINEA_DESACT);
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO, SALTO_LINEA) 
    VALUES (vIDENTIFICA, cSecUsu_in, SIZE_1, SALTO_LINEA_DESACT);
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO, NEGRITA, SALTO_LINEA) 
    VALUES (vIDENTIFICA, ' - CodL: ', SIZE_1, BOLD_ACT, SALTO_LINEA_DESACT);
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO) 
    VALUES (vIDENTIFICA, cCodLocal_in, SIZE_1);
    
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO) VALUES (vIDENTIFICA, ' ',SIZE_1);
    
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO, NEGRITA, SALTO_LINEA) 
    VALUES (vIDENTIFICA, 'Fecha Actual: ', SIZE_1, BOLD_ACT, SALTO_LINEA_DESACT);
    INSERT INTO IMPRESION_TERMICA (IDENTIFICA, VALOR, TAMANIO) 
    VALUES (vIDENTIFICA, to_char(sysdate,'dd/MM/yyyy HH12:MI:SS A.M.'), SIZE_1);
    
    OPEN cursorComprobante FOR
      SELECT A.*
      FROM IMPRESION_TERMICA A
      WHERE A.IP_PC = F_VAR_IP_SESS
      AND   A.IDENTIFICA = vIDENTIFICA;
    RETURN cursorComprobante;
  END;                                  
END FARMA_PRINTER;
/
