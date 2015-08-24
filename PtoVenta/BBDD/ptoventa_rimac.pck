CREATE OR REPLACE PACKAGE "PTOVENTA_RIMAC" AS

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: determinar si el producto es un producto RIMAC.
  --Fecha       Usuario		Comentario
  --05/01/2015  ASOSA     Creación
  FUNCTION GET_IND_IS_PROD_RIMAC(cGrupoCia_in IN CHAR,
                                  cCia_in      IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  cCodProd_in IN VARCHAR2)
    RETURN CHAR;
    
      --Descripcion: Obtener rango de cantidad de meses permitido.
  --Fecha       Usuario		Comentario
  --05/01/2015  ASOSA     Creación
    Function  GET_RANGO_PROD_RIMAC (cCodGrupoCia_in in char,
                                                        cCodCia_in in char,
                                                        cCodLocal_in in char,
                                                        cCodProd_in in char)
  return varchar2;
  
  --Descripcion: listar pedido con producto rimac. Si envio num ped vta vacio listo todos los faltantes por enviar
  --Fecha       Usuario		Comentario
  --07/01/2015  ASOSA     Creación
      FUNCTION LIST_PEDIDO_RIMAC_COBRADO (cCodGrupoCia_in IN CHAR,
                                     cCodCia_in in char,
  		   						   		  cCodLocal_in	 IN CHAR)
	RETURN FarmaCursor;

  --Descripcion: listar pedido ANULADO con producto rimac. Si envio num ped vta vacio listo todos los faltantes por enviar
  --Fecha       Usuario		Comentario
  --07/01/2015  ASOSA     Creación
      FUNCTION LIST_PEDIDO_RIMAC_ANULADO(cCodGrupoCia_in IN CHAR,
                                     cCodCia_in in char,
  		   						   		  cCodLocal_in	 IN CHAR)
	RETURN FarmaCursor;
  
    --Descripcion: Marcar como enviado a matriz a un pedido en el local
  --Fecha       Usuario		Comentario
  --09/01/2015  ASOSA     Creación
        FUNCTION UPD_VTA_PED_RIMAC(cCodGrupoCia_in IN CHAR,
                                                                           cCodCia_in IN CHAR,
                                                                           cCodLocal_in IN CHAR,
                                                                           cNumPedVta_in IN CHAR
                                                                           )
RETURN CHAR;

  --Descripcion: Marcar como enviado a matriz a un pedido DE ANULACION O NOTA DE CREDITO en el local
  --Fecha       Usuario		Comentario
  --09/01/2015  ASOSA     Creación
        FUNCTION UPD_VTA_PED_RIMAC_ANUL(cCodGrupoCia_in IN CHAR,
                                                                           cCodCia_in IN CHAR,
                                                                           cCodLocal_in IN CHAR,
                                                                           cNumPedVta_in IN CHAR
                                                                           )
RETURN CHAR;

  --Descripcion: determinar si el pedido tiene un producto rimac
  --Fecha       Usuario		Comentario
  --11/01/2015  ASOSA     Creación
FUNCTION GET_IND_EXISTE_rimac_ped(cCodGrupoCia_in IN CHAR,
                                                                           cCodCia_in IN CHAR,
                                                                           cCodLocal_in IN CHAR,
                                                                           cNumPedVta_in IN CHAR)
	RETURN CHAR;
  
  --Descripcion: IMPRIMIR VOUCHER DE VERIFICACION RIMAC
  --Fecha       Usuario		Comentario
  --12/01/2015  ASOSA     Creación  
    FUNCTION F_IMPR_VOU_VERIF_RIMAC(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 cIpServ_in        IN CHAR,
                                 cCodCia_in in char,
                                 vDescProducto IN VARCHAR2,
                                 vNumDni  IN VARCHAR2,
                                 vCantMeses in varchar2,
                                 vMonto        IN VARCHAR2
                                 )
  RETURN FARMACURSOR;
  
    --Descripcion: determinar mensaje para productos rimac
  --Fecha       Usuario		Comentario
  --12/01/2015  ASOSA     Creación 
      FUNCTION CAJ_OBTIENE_MSJ_PROD_RIMAC(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    cNumPedido_in    IN CHAR)
  RETURN FarmaCursor;
  
      --Descripcion: Insertar producto rimac como regalo
  --Fecha       Usuario		Comentario
  --15/01/2015  ASOSA     Creación 
         FUNCTION INS_PROD_RIMAC_REGALO(cCodGrupoCia_in IN CHAR,
                                                                           cCodCia_in IN CHAR,
                                                                           cCodLocal_in IN CHAR,
                                                                           cNumPedVta_in IN CHAR
                                                                           )
                                                                           RETURN CHAR;

      --Descripcion: obtener stock producto
  --Fecha       Usuario		Comentario
  --19/01/2015  ASOSA     Creación 
  FUNCTION GET_STOCK_PROD_REGALO(cCodGrupoCia_in IN CHAR,
                                                                           cCodCia_in IN CHAR,
                                                                           cCodLocal_in IN CHAR,
                                                                           cCodProd_in IN CHAR)
	RETURN VARCHAR2;

  end;
/
CREATE OR REPLACE PACKAGE BODY "PTOVENTA_RIMAC" AS

	/****************************************************************************************************/

  FUNCTION GET_IND_IS_PROD_RIMAC(cGrupoCia_in IN CHAR,
                                  cCia_in      IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  cCodProd_in IN VARCHAR2)
    RETURN CHAR
  IS
  ind char(1) := 'N';
  cant number(3) := 0;
  BEGIN
  	   
  	        SELECT count(1)
            into cant
  	        FROM   LGT_PROD_RIMAC C
  	        WHERE  C.COD_GRUPO_CIA = cGrupoCia_in
  	        AND    C.COD_PROD = cCodProd_in;
            
            IF cant = 1 THEN
                    IND := 'S';
            END IF;

	  RETURN IND;
  END;

	/****************************************************************************************************/
  
Function  GET_RANGO_PROD_RIMAC (cCodGrupoCia_in in char,
                                                        cCodCia_in in char,
                                                        cCodLocal_in in char,
                                                        cCodProd_in in char)
  return varchar2
is
valor varchar2(500);
begin
     select nvl(a.cant_min,'0') || 'Ã' ||
                   NVL(A.cant_max, '0')
     into valor
     from lgt_prod_rimac a
     where a.cod_grupo_cia = cCodGrupoCia_in
     and a.cod_prod = cCodProd_in;
 return valor;
end;

	/****************************************************************************************************/
  
    FUNCTION LIST_PEDIDO_RIMAC_COBRADO (cCodGrupoCia_in IN CHAR,
                               cCodCia_in in char,
  		   						   		  cCodLocal_in	 IN CHAR)
	RETURN FarmaCursor
	IS
		curProd FarmaCursor ;
	BEGIN
	OPEN curProd FOR
		 
               select A.COD_GRUPO_CIA || 'Ã' ||
                   A.COD_CIA || 'Ã' ||
                   A.COD_LOCAL || 'Ã' ||
                   A.NUM_PED_VTA || 'Ã' ||
                   nvl(C.NUM_COMP_PAGO_E,' ') || 'Ã' ||
                   to_char(C.FEC_CREA_COMP_PAGO,'dd/MM/yyyy HH:MI:SS am') || 'Ã' ||
                   B.COD_PROD || 'Ã' ||
                   B.CANT_ATENDIDA || 'Ã' ||
                   B.VAL_PREC_VTA || 'Ã' ||
                   B.VAL_PREC_TOTAL || 'Ã' ||
                   B.DNI_RIMAC
                from vta_pedido_vta_cab a,
                     vta_pedido_vta_det b,
                     vta_comp_pago c
                where a.cod_grupo_cia = B.COD_GRUPO_CIA
                AND A.COD_LOCAL = B.COD_LOCAL
                AND A.NUM_PED_VTA = B.NUM_PED_VTA
                AND C.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                AND C.COD_LOCAL = A.COD_LOCAL
                AND C.NUM_PED_VTA = A.NUM_PED_VTA
                --AND A.NUM_PED_VTA = NVL(cNumPedVta_in, A.NUM_PED_VTA)
                AND A.COD_GRUPO_CIA = cCodGrupoCia_in
                AND A.COD_CIA = cCodCia_in
                AND A.COD_LOCAL = cCodLocal_in
                AND B.DNI_RIMAC IS NOT NULL
                AND (a.ind_envio_rimac is null or A.IND_ENVIO_RIMAC != 'S')
                AND A.EST_PED_VTA = 'C'
                AND A.NUM_PED_VTA_ORIGEN IS NULL
                AND B.COD_PROD IN (SELECT COD_PROD FROM LGT_PROD_RIMAC)
                AND C.SEC_COMP_PAGO = CASE 
                                                                          WHEN B.SEC_COMP_PAGO_BENEF  IS NOT NULL 
                                                                                                       AND C.TIP_COMP_PAGO <> '04' THEN
                                                                                B.SEC_COMP_PAGO_BENEF 
                                                                          ELSE 
                                                                               B.SEC_COMP_PAGO
                                                                          END
                GROUP BY A.COD_GRUPO_CIA,
                                         A.COD_CIA,
                                   A.COD_LOCAL,
                                   A.NUM_PED_VTA,
                                   C.NUM_COMP_PAGO_E,
                                   C.FEC_CREA_COMP_PAGO,
                                   B.COD_PROD,
                                   B.CANT_ATENDIDA,
                                   B.VAL_PREC_VTA,
                                   B.VAL_PREC_TOTAL,
                                   B.DNI_RIMAC;
     
    RETURN curProd;
  END;

	/****************************************************************************************************/
  
      FUNCTION LIST_PEDIDO_RIMAC_ANULADO (cCodGrupoCia_in IN CHAR,
                               cCodCia_in in char,
  		   						   		  cCodLocal_in	 IN CHAR)
	RETURN FarmaCursor
	IS
		curProd FarmaCursor ;
	BEGIN
	OPEN curProd FOR
		 
               select A.COD_GRUPO_CIA || 'Ã' ||
                   A.COD_CIA || 'Ã' ||
                   A.COD_LOCAL || 'Ã' ||
                   A.NUM_PED_VTA_ORIGEN || 'Ã' ||
                   nvl(C.NUM_COMP_PAGO_E,' ') || 'Ã' ||
                   to_char(C.FEC_CREA_COMP_PAGO,'dd/MM/yyyy HH:MI:SS am') || 'Ã' ||
                   B.COD_PROD || 'Ã' ||
                   B.CANT_ATENDIDA || 'Ã' ||
                   B.VAL_PREC_VTA || 'Ã' ||
                   B.VAL_PREC_TOTAL || 'Ã' ||
                   B.DNI_RIMAC
                from vta_pedido_vta_cab a,
                     vta_pedido_vta_det b,
                     vta_comp_pago c
                where a.cod_grupo_cia = B.COD_GRUPO_CIA
                AND A.COD_LOCAL = B.COD_LOCAL
                AND A.NUM_PED_VTA = B.NUM_PED_VTA
                AND C.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                AND C.COD_LOCAL = A.COD_LOCAL
                AND C.NUM_PED_VTA = A.NUM_PED_VTA
                --AND A.NUM_PED_VTA = NVL(cNumPedVta_in, A.NUM_PED_VTA)
                AND A.COD_GRUPO_CIA = cCodGrupoCia_in
                AND A.COD_CIA = cCodCia_in
                AND A.COD_LOCAL = cCodLocal_in
                --AND B.DNI_RIMAC IS NOT NULL
                AND (a.ind_envio_rimac is null or A.IND_ENVIO_RIMAC != 'S')
                AND A.EST_PED_VTA = 'C'
                AND A.NUM_PED_VTA_ORIGEN IS NOT NULL
                AND B.COD_PROD IN (SELECT COD_PROD FROM LGT_PROD_RIMAC)
                AND C.SEC_COMP_PAGO = CASE 
                                                                          WHEN B.SEC_COMP_PAGO_BENEF  IS NOT NULL 
                                                                                                      AND C.TIP_COMP_PAGO <> '04' THEN
                                                                              B.SEC_COMP_PAGO_BENEF 
                                                                          ELSE 
                                                                               B.SEC_COMP_PAGO
                                                                          END
                GROUP BY A.COD_GRUPO_CIA,
                                         A.COD_CIA,
                                   A.COD_LOCAL,
                                   A.NUM_PED_VTA_origen,
                                   C.NUM_COMP_PAGO_E,
                                   C.FEC_CREA_COMP_PAGO,
                                   B.COD_PROD,
                                   B.CANT_ATENDIDA,
                                   B.VAL_PREC_VTA,
                                   B.VAL_PREC_TOTAL,
                                   B.DNI_RIMAC;
     
    RETURN curProd;
  END;

	/****************************************************************************************************/
  
      FUNCTION UPD_VTA_PED_RIMAC(cCodGrupoCia_in IN CHAR,
                                                                           cCodCia_in IN CHAR,
                                                                           cCodLocal_in IN CHAR,
                                                                           cNumPedVta_in IN CHAR
                                                                           )
                                                                           RETURN CHAR
  IS
  cRetorno_out CHAR(1) := 'N';
  BEGIN
    BEGIN

     UPDATE VTA_PEDIDO_VTA_CAB A
     SET A.IND_ENVIO_RIMAC = 'S'
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
     AND A.COD_CIA = cCodCia_in
     AND A.COD_LOCAL = cCodLocal_in
     AND A.NUM_PED_VTA = cNumPedVta_in;

      cRetorno_out := 'S';
    EXCEPTION
        WHEN OTHERS THEN
          cRetorno_out := 'N' || SQLERRM;
    END;
    return cRetorno_out;

  END;


/****************************************************************************************************/
  
      FUNCTION UPD_VTA_PED_RIMAC_ANUL(cCodGrupoCia_in IN CHAR,
                                                                           cCodCia_in IN CHAR,
                                                                           cCodLocal_in IN CHAR,
                                                                           cNumPedVta_in IN CHAR
                                                                           )
                                                                           RETURN CHAR
  IS
  cRetorno_out CHAR(1) := 'N';
  BEGIN
    BEGIN

     UPDATE VTA_PEDIDO_VTA_CAB A
     SET A.IND_ENVIO_RIMAC = 'S'
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
     AND A.COD_CIA = cCodCia_in
     AND A.COD_LOCAL = cCodLocal_in
     AND A.NUM_PED_VTA_ORIGEN = cNumPedVta_in;

      cRetorno_out := 'S';
    EXCEPTION
        WHEN OTHERS THEN
          cRetorno_out := 'N' || SQLERRM;
    END;
    
    return cRetorno_out;

  END;


	/****************************************************************************************************/
  
  FUNCTION GET_IND_EXISTE_rimac_ped(cCodGrupoCia_in IN CHAR,
                                                                           cCodCia_in IN CHAR,
                                                                           cCodLocal_in IN CHAR,
                                                                           cNumPedVta_in IN CHAR)
	RETURN CHAR
	IS
		ind char(1) := 'N';
    cant number(3) := 0;
	BEGIN
		 
               select count(1)
               into cant
              from vta_pedido_vta_cab c,
                   vta_pedido_vta_det d
              where c.num_ped_vta = d.num_ped_vta
              and c.cod_grupo_cia = d.cod_grupo_cia
              and c.cod_local = d.cod_local
              and c.cod_grupo_cia = cCodGrupoCia_in
              and c.cod_local = cCodLocal_in
              and c.cod_cia = NVL(cCodCia_in,C.COD_CIA)
              and c.num_ped_vta = cNumPedVta_in
              and d.cod_prod in (select cod_prod
                                  from lgt_prod_rimac);
                
                if cant > 0 then
                       ind := 'S' ;
                end if;
     
    RETURN ind;
  END;


	/****************************************************************************************************/
  
  FUNCTION F_IMPR_VOU_VERIF_RIMAC(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 cIpServ_in        IN CHAR,
                                 cCodCia_in in char,
                                 vDescProducto IN VARCHAR2,
                                 vNumDni  IN VARCHAR2,
                                 vCantMeses in varchar2,
                                 vMonto        IN VARCHAR2
                                 )
  RETURN FARMACURSOR
  IS
    curTicket FARMACURSOR;
    vAdvertencia varchar2(500) := '';
  BEGIN
    DELETE TMP_DOCUMENTO_ELECTRONICOS;
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
    SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'0','C','N','N'
          FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
    SELECT 'POR FAVOR, VALIDE SU DOCUMENTO DE IDENTIDAD'||'@'||
           ' '||'@'||
           'LOCAL: '||( select l.cod_local || '-' || l.desc_corta_local  
                        from   pbl_local l
                        where  l.cod_grupo_cia = cCodGrupoCia_in
                        and    l.cod_local = cCodLocal_in)||'@'||
           'FECHA: ' || to_char(sysdate,'dd/mm/yyyy HH24:MI:SS')||'@'||
--           ' '||'@'||
           ' '
           FROM DUAL
    ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;  
    
    SELECT P.LLAVE_TAB_GRAL
    INTO vAdvertencia
    FROM PBL_TAB_GRAL P
    WHERE P.ID_TAB_GRAL = '632';    
    
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
    SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'0','I','N','N'
          FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
    SELECT vDescProducto ||'@'||
           ' '||'@'||
           vAdvertencia ||'@'||
           ' '||'@'||           
           'DOC. IDENTIDAD: '||'@'||
  --         ' '||'@'||
           ' '
           FROM DUAL
    ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;       

    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
    SELECT vNumDni ,'3','C','S','N' FROM DUAL;
    
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
    SELECT 'CANT MESES: ' ,'0','I','N','N' FROM DUAL;

    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
    SELECT vCantMeses ,'3','C','S','N' FROM DUAL;

    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
    SELECT 'MONTO TOTAL: ' ,'0','I','N','N' FROM DUAL;

    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
    SELECT 'S/. '||vMonto ,'3','C','S','N' FROM DUAL;

    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
    SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'0','I','N','N'
          FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
    SELECT ' '||'@'||
           ' '||'@'||
           ' '||'@'||
           ' '||'@'||
           'Firma          :________________________________' ||'@'||
           ' ' ||'@'||
           'DOC. IDENTIDAD :________________________________' 
           FROM DUAL
    ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;     
    
    OPEN curTicket FOR
        SELECT A.VALOR, A.TAMANIO, A.ALINEACION, A.BOLD, A.AJUSTE
        FROM TMP_DOCUMENTO_ELECTRONICOS A;

    RETURN curTicket;
  

  END;  
  
  	/****************************************************************************************************/
    
    
    FUNCTION CAJ_OBTIENE_MSJ_PROD_RIMAC(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    cNumPedido_in    IN CHAR)
  RETURN FarmaCursor
  IS
  curdesc       FarmaCursor;
  contPed01 number(3) := 0; --ASOSA - 11/02/2015 - RIMAC
  NUM_DNI    VARCHAR2(20);
  DESC_LINEA_01   VARCHAR2(1000) := '';

  BEGIN
      --INI ASOSA - 11/02/2015 - RIMAC
       SELECT COUNT(1)
       INTO contPed01
      FROM   VTA_PEDIDO_VTA_DET D
      WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    D.COD_LOCAL     = cCodLocal_in
      AND    D.NUM_PED_VTA   = cNumPedido_in
      AND    D.DNI_RIMAC IS NOT NULL;
      
      IF contPed01 = 1 THEN

            SELECT D.DNI_RIMAC
            INTO   NUM_DNI
            FROM   VTA_PEDIDO_VTA_DET D
            WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    D.COD_LOCAL     = cCodLocal_in
            AND    D.NUM_PED_VTA   = cNumPedido_in
            AND    D.DNI_RIMAC IS NOT NULL;
      
      ELSE
      
              SELECT D.DNI_RIMAC
                INTO   NUM_DNI
                FROM   VTA_PEDIDO_VTA_DET D
                WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
                AND    D.COD_LOCAL     = cCodLocal_in
                AND    D.NUM_PED_VTA   = (SELECT C.NUM_PED_VTA_ORIGEN
                                                                        FROM VTA_PEDIDO_VTA_CAB C
                                                                        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                                                        AND C.COD_LOCAL = cCodLocal_in
                                                                        AND C.NUM_PED_VTA = cNumPedido_in  )
                AND    D.DNI_RIMAC IS NOT NULL;
      
      END IF;
      --FIN ASOSA - 11/02/2015 - RIMAC
      
        begin
                SELECT G.Llave_Tab_Gral
                INTO   DESC_LINEA_01
                FROM   PBL_TAB_GRAL G
                WHERE  G.ID_TAB_GRAL  = '631';

        EXCEPTION
      		WHEN NO_DATA_FOUND THEN
    			dbms_output.put_line('no se encontro informacion!!!');
        END;
        
            OPEN curdesc FOR
              --SELECT 'NUMERO DNI: '||NUM_DNI    || 'Ã' || --COMERCIAL Y RIMAC QUERIAN QUE MUESTRE VARIOS TIPOS DE DOCUMENTOS
              SELECT 'DOC. IDENTIDAD: '||NUM_DNI    || 'Ã' ||
                      DESC_LINEA_01
              FROM DUAL;
         

    RETURN curdesc;

  END;
  
 /* *******************************************************************/
 
       FUNCTION INS_PROD_RIMAC_REGALO(cCodGrupoCia_in IN CHAR,
                                                                           cCodCia_in IN CHAR,
                                                                           cCodLocal_in IN CHAR,
                                                                           cNumPedVta_in IN CHAR                                                                           
                                                                           )
                                                                           RETURN CHAR
  IS
  cRetorno_out CHAR(1) := 'N';
  cant number(3) := 0;
  codProdOrigen char(6) := '';
  codRegalo char(6) := '';
  nCantItemPed NUMBER(6) := 1;
  
  cSecUsu_in CHAR(3) := ' ';
 cLogin_in VARCHAR2(15) := ' ';
 
  BEGIN
  
    BEGIN 
    
          select cod_prod, a.sec_usu_local, a.usu_crea_ped_vta_det
          into codProdOrigen, cSecUsu_in, cLogin_in
          from vta_pedido_vta_det a
          where a.cod_grupo_cia = cCodGrupoCia_in
          and a.cod_local = cCodLocal_in
          and a.num_ped_vta = cNumPedVta_in;
          
          select b.cod_prod_regalo
          into codRegalo
          from lgt_prod_rimac b
          where b.cod_grupo_cia = cCodGrupoCia_in
          and b.cod_prod = codProdOrigen;
    
                             INSERT INTO VTA_PEDIDO_VTA_DET
                            (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,
                            VAL_PREC_VTA,VAL_PREC_TOTAL,PORC_DCTO_1,PORC_DCTO_2,PORC_DCTO_3,
                            PORC_DCTO_TOTAL,VAL_TOTAL_BONO,VAL_FRAC,
                            SEC_USU_LOCAL,USU_CREA_PED_VTA_DET,VAL_PREC_LISTA,
                            VAL_IGV,UNID_VTA,IND_EXONERADO_IGV,
                            VAL_PREC_PUBLIC,IND_ORIGEN_PROD,
                            VAL_FRAC_LOCAL,
                            CANT_FRAC_LOCAL
                            )
                            SELECT   cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,2,
                                     codRegalo,nCantItemPed,
                                     0,0,
                                     PROD_LOCAL.PORC_DCTO_1,
                                     PROD_LOCAL.PORC_DCTO_2,
                                     PROD_LOCAL.PORC_DCTO_3,
                                     PROD_LOCAL.PORC_DCTO_1+ PROD_LOCAL.PORC_DCTO_2 +
                                     PROD_LOCAL.PORC_DCTO_3,
                                     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.VAL_BONO_VIG,(PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL)),
                            			   PROD_LOCAL.VAL_FRAC_LOCAL,
                                     cSecUsu_in,cLogin_in,
                                     0,
                                     IGV.PORC_IGV,
                                     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.Unid_Vta),
                                     DECODE(IGV.PORC_IGV,0,'S','N'),
                                     PROD_LOCAL.VAL_PREC_VTA,
                                     null,
                                     PROD_LOCAL.VAL_FRAC_LOCAL,
                                     nCantItemPed
                            FROM     LGT_PROD PROD,
                            	       LGT_PROD_LOCAL PROD_LOCAL,
                                     pbl_igv igv
                            WHERE    PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND	     PROD_LOCAL.COD_LOCAL = cCodLocal_in
                            AND	     PROD_LOCAL.COD_PROD = codRegalo
                            AND	     PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
                            AND	     PROD.COD_PROD = PROD_LOCAL.COD_PROD
                            AND      PROD.COD_IGV = IGV.COD_IGV;

      cRetorno_out := 'S';
    EXCEPTION
        WHEN OTHERS THEN
          cRetorno_out := 'N' || SQLERRM;
    END;
    
    return cRetorno_out;

  END;
  
  	/****************************************************************************************************/
  
  FUNCTION GET_STOCK_PROD_REGALO(cCodGrupoCia_in IN CHAR,
                                                                           cCodCia_in IN CHAR,
                                                                           cCodLocal_in IN CHAR,
                                                                           cCodProd_in IN CHAR)
	RETURN VARCHAR2
	IS
    stock number(6) := 0;
     cant number(6) := 0;
    codRegalo char(6) := ' ';
    rpta varchar2(100) := ' ';
	BEGIN
		 
               select count(1)
               into cant
               from lgt_prod_rimac a
               where a.cod_grupo_cia = cCodGrupoCia_in
               and a.cod_prod = cCodProd_in;
               
               if cant > 0 then
                     select a.cod_prod_regalo
                     into codRegalo
                     from lgt_prod_rimac a
                     where a.cod_grupo_cia = cCodGrupoCia_in
                     and a.cod_prod = cCodProd_in;
                     
                     select b.stk_fisico
                     into stock
                     from lgt_prod_local b
                     where b.cod_grupo_cia = cCodGrupoCia_in
                     and b.cod_local = cCodLocal_in
                     and b.cod_prod = codRegalo;
               end if;
                
                rpta := codRegalo || '@' || stock;
     
    RETURN rpta;
  END;
  
  /****************************************************************************************************/

END;
/
