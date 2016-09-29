CREATE OR REPLACE PACKAGE "PTOVENTA_RECEP_CIEGA_NEW" IS

 TYPE FarmaCursor IS REF CURSOR;
  g_nCodNumeraAuxConteoProd       PBL_NUMERA.COD_NUMERA%TYPE := '075';
  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;
  v_gNombreDiretorioAlert VARCHAR2(50) := 'DIR_INTERFACES';
  
   C_INICIO_MSG2  VARCHAR2(2000) := --'<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
--                                  <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
                                  /*'<html>
                                  <head>
                                  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

                                  <title></title>
                                  <style>
                                  body { font-family: Arial, Helvetica, sans-serif;}
                                  .es{
                                      -webkit-transform: rotate(-90deg); 
                                      -moz-transform: rotate(-90deg);
                                      -o-transform: rotate(-90deg);
                                      transform: rotate(-90deg);
                                      filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=3);
                                  }
                                  </style>
                                  </head>
                                '*/
                                '<html>
                                 <head>
                                 <style>
                                  body { font-family: Arial, Helvetica, sans-serif;}
                                  .es{

                                      transform: rotate(-90deg);
                                  }
                                  table{
                                  font-size: 90px;
                                  }
                                  td{
                                  line-height:40pt;
                                  }
                                  </style>
                                  </head><body>
                                '
                                ;
                                
 C_FIN_MSG     VARCHAR2(2000) := '</body>
                                 </html>';
  
/****************************************************************************/

  --Descripcion: Insertar el auxiliar de conteo por cada producto escaneado
  --Fecha       Usuario	  	Comentario
  --05/06/2015  ASOSA       Creación
 PROCEDURE RECEP_P_INS_AUX_CONTEO (cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNroRecep_in IN CHAR,
                                      nSecAuxConteo_in IN NUMBER,
                                      cCodBarra_in IN VARCHAR2,
                                      nCantidad_in IN NUMBER,
                                      cUsuConteo_in IN CHAR,
                                      vIp_in IN VARCHAR,
                                      vNroBloque_in IN NUMBER,
                                      vCodBulto_in in varchar2,
                                      cCorrBulto_in in char,
                                      cIndLectora_in in char,
                                      vIndDeteriorado_in IN CHAR DEFAULT 'N',
                                      vIndFueraLote_in IN CHAR DEFAULT 'N',
                                      vIndNoFound_in IN CHAR DEFAULT 'N');


/****************************************************************************/

  --Descripcion: Actualiza y devolver correlativo de bulto.
  --Fecha       Usuario	  	Comentario
  --05/06/2015  ASOSA       Creación
   FUNCTION RECEP_F_UPD_NUM_BULTO(cCodGrupoCia_in IN CHAR,
                                    cCodCia_in      in char,
                                    cCodLocal_in    IN CHAR,
                                    cNroRecep_in    in char) 
   return char;
 
/****************************************************************************/

  --Descripcion: Listar productos que voy escaneando.
  --Fecha       Usuario	  	Comentario
  --08/06/2015  ASOSA       Creación
FUNCTION RECEP_F_CUR_LIS_PRIMER_CONTEO(cCodGrupoCia_in IN CHAR,
                          		   			  cCodLocal_in	 IN CHAR,
                                        cNroRecep_in IN CHAR,
                                        cNroBloque_in IN CHAR)
  RETURN FarmaCursor;
  
/****************************************************************************/

  --Descripcion: Finalizar conteo insertando los conteos de la tabla auxiliar a la final.
  --Fecha       Usuario	  	Comentario
  --08/06/2015  ASOSA       Creación
PROCEDURE RECEP_P_INS_PROD_CONTEO(   cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNroRecep_in IN CHAR,
                                      cUsuConteo_in IN CHAR,
                                      vIp_in IN VARCHAR);
                                      
/****************************************************************************/

  --Descripcion: Determinar si un bulto existe en una recepcion.
  --Fecha       Usuario	  	Comentario
  --08/06/2015  ASOSA       Creación
FUNCTION RECEP_F_IND_BULTO_EXISTS(cCodGrupoCia_in IN CHAR,
                                    cCodCia_in      in char,
                                    cCodLocal_in    IN CHAR,
                                    cNroRecep_in    IN CHAR,
                                    vNroBulto_in    IN VARCHAR2) 
   return char;
   
/****************************************************************************/

  --Descripcion: Determinar si un bulto existe en una recepcion.
  --Fecha       Usuario	  	Comentario
  --12/06/2015  ASOSA       Creación
FUNCTION F_IMPR_VOU_BULTO_HTML(cCodGrupoCia_in   IN CHAR,
                                 cCodCia_in in char,
                                 cCodLocal_in      IN CHAR,
                                 cCorrBulto_in in char
                							 )
  RETURN VARCHAR2;
  
/****************************************************************************/

  --Descripcion: Obtener el indicador del local si se utilizara el beep de la placa o no.
  --Fecha       Usuario	  	Comentario    Type
  --22/06/2015  ASOSA       Creación      RCIEGAMCHANGES01
FUNCTION RECEP_F_IND_LOCAL_BEEP(cCodGrupoCia_in IN CHAR,
                                    cCodCia_in      in char,
                                    cCodLocal_in    IN CHAR) 
   return char;

/****************************************************************************/

END PTOVENTA_RECEP_CIEGA_NEW;
/
CREATE OR REPLACE PACKAGE BODY "PTOVENTA_RECEP_CIEGA_NEW" IS

 /****************************************************************************/
 
 PROCEDURE RECEP_P_INS_AUX_CONTEO (cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNroRecep_in IN CHAR,
                                      nSecAuxConteo_in IN NUMBER,
                                      cCodBarra_in IN VARCHAR2,
                                      nCantidad_in IN NUMBER,
                                      cUsuConteo_in IN CHAR,
                                      vIp_in IN VARCHAR,
                                      vNroBloque_in IN NUMBER,
                                      vCodBulto_in in varchar2,
                                      cCorrBulto_in in char,
                                      cIndLectora_in in char,
                                      vIndDeteriorado_in IN CHAR DEFAULT 'N',
                                      vIndFueraLote_in IN CHAR DEFAULT 'N',
                                      vIndNoFound_in IN CHAR DEFAULT 'N')

   IS
    Cod_Prod CHAR(6);
   BEGIN
     
   Cod_Prod := PTOVENTA_VTA.VTA_REL_COD_BARRA_COD_PROD(cCodGrupoCia_in, cCodBarra_in);

     INSERT INTO AUX_LGT_PROD_CONTEO
     (COD_GRUPO_CIA, COD_LOCAL, NRO_RECEP, COD_PROD, COD_BARRA, CANTIDAD,
     USU_INS_CONTEO, FEC_INS_CONTEO, IP_INS_CONTEO, NRO_BLOQUE, IND_DETERIORADO, IND_FUERA_LOTE,
     IND_NO_FOUND, SEC_AUX_CONTEO,
     cod_bulto, 
      corr_bulto, 
      ind_uso_lectora
      )
     VALUES
     (cCodGrupoCia_in, cCodLocal_in, cNroRecep_in, Cod_Prod, cCodBarra_in, nCantidad_in,
     cUsuConteo_in, SYSDATE, vIp_in, vNroBloque_in, vIndDeteriorado_in,  vIndFueraLote_in,
     vIndNoFound_in, nSecAuxConteo_in,
     vCodBulto_in,
     cCorrBulto_in,
     cIndLectora_in
     );

   END;

 /****************************************************************************/ 
    
   FUNCTION RECEP_F_UPD_NUM_BULTO(cCodGrupoCia_in IN CHAR,
                                    cCodCia_in      in char,
                                    cCodLocal_in    IN CHAR,
                                    cNroRecep_in    in char) 
   return char 
   iS
     corrBulto CHAR(5) := '';
     cant number(5) := 0;
   BEGIN
   
     SELECT COUNT(1)
     INTO cant
     FROM LGT_REL_BULTO_RECEP A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
     AND A.COD_LOCAL = cCodLocal_in
     AND A.NRO_RECEP = cNroRecep_in;
     
     LOCK TABLE LGT_REL_BULTO_RECEP IN exclusive MODE;
   
     SELECT NVL(MAX(A.CORR_BULTO),'B0001')
     INTO corrBulto
     FROM LGT_REL_BULTO_RECEP A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
     AND A.COD_LOCAL = cCodLocal_in
     AND A.NRO_RECEP = cNroRecep_in;
     
     IF cant > 0 THEN
        SELECT 'B' || LPAD(TO_NUMBER(SUBSTR(corrBulto,2,4),9999) + 1, 4, '0')
        INTO corrBulto
        FROM DUAL;
     END IF;
     
     
     INSERT INTO LGT_REL_BULTO_RECEP(cod_grupo_cia, 
                                    cod_local, 
                                    nro_recep, 
                                    corr_bulto
                                    )
     VALUES(cCodGrupoCia_in,
            cCodLocal_in,
            cNroRecep_in,
            corrBulto);
     
     RETURN corrBulto;
   
   END;

 /****************************************************************************/
 
   FUNCTION RECEP_F_CUR_LIS_PRIMER_CONTEO(cCodGrupoCia_in IN CHAR,
                          		   			  cCodLocal_in	 IN CHAR,
                                        cNroRecep_in IN CHAR,
                                        cNroBloque_in IN CHAR)
  RETURN FarmaCursor
  IS
    curPrimerConteo FarmaCursor;
    vIP_pc VARCHAR2(15) := '';
  BEGIN

  SELECT SYS_CONTEXT ('USERENV', 'IP_ADDRESS') INTO vIP_pc  FROM dual;
    OPEN curPrimerConteo FOR

SELECT NVL(A1.DESC_PROD,' ') || 'Ã' ||
       NVL(A1.UNIDAD,' ') || 'Ã' ||
       TO_CHAR(A1.CANTIDAD,'999999') || 'Ã' ||
       ' ' || 'Ã' ||
       ' ' || 'Ã' ||
       ' ' || 'Ã' ||
       ' ' || 'Ã' ||
       TO_CHAR(A1.SEC_AUX_CONTEO,'99999999') || 'Ã' ||
       A1.COD_BARRA || 'Ã' ||
       TO_CHAR(A1.NRO_BLOQUE,'9999999999') || 'Ã' ||
       A1.CORR_BULTO
FROM
(
--LISTADO DE PRODUCTOS AUXILIARES
SELECT AU.COD_GRUPO_CIA,
       AU.COD_LOCAL,
       AU.NRO_RECEP,
       AU.COD_PROD,
       P.DESC_PROD,
       P.DESC_UNID_PRESENT UNIDAD,
       AU.CANTIDAD,
       AU.SEC_AUX_CONTEO,
       AU.COD_BARRA,
       AU.NRO_BLOQUE,
       AU.CORR_BULTO
  FROM AUX_LGT_PROD_CONTEO AU, LGT_PROD P, LGT_COD_BARRA CB
 WHERE AU.COD_GRUPO_CIA = P.COD_GRUPO_CIA(+)
   AND AU.COD_PROD = P.COD_PROD(+)
   AND AU.COD_GRUPO_CIA = CB.COD_GRUPO_CIA(+)
   AND AU.COD_BARRA = CB.COD_BARRA(+)
   AND AU.COD_PROD = CB.COD_BARRA(+)
   AND AU.COD_GRUPO_CIA = cCodGrupoCia_in
   AND AU.COD_LOCAL = cCodLocal_in
   AND AU.NRO_RECEP = cNroRecep_in
   AND AU.NRO_BLOQUE = cNroBloque_in
   AND AU.IP_INS_CONTEO = vIP_pc
 --LISTADO DE PRODUCTOS AUXILIARES
) A1
--mezclo
WHERE A1.COD_GRUPO_CIA = cCodGrupoCia_in
  AND A1.COD_LOCAL = cCodLocal_in
  AND A1.NRO_RECEP = cNroRecep_in
  AND A1.COD_PROD NOT IN ('000000')
ORDER BY A1.SEC_AUX_CONTEO DESC;

--------------------------------

    RETURN curPrimerConteo;
  END;

  /****************************************************************************/
  
    PROCEDURE RECEP_P_INS_PROD_CONTEO(   cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNroRecep_in IN CHAR,
                                      cUsuConteo_in IN CHAR,
                                      vIp_in IN VARCHAR)
  IS
  BEGIN
   --TENER EN CUENTA FINALIZAR LA TOMA
   INSERT INTO LGT_PROD_CONTEO
   (COD_GRUPO_CIA, COD_LOCAL, NRO_RECEP, SEC_CONTEO, COD_PROD, CANTIDAD,
    USU_INS_CONTEO, FEC_INS_CONTEO, IP_INS_CONTEO
   )
SELECT cCodGrupoCia_in,
       cCodLocal_in,
       cNroRecep_in,
       ROWNUM SEC_CONTEO,
       V1.COD_PROD,
       V1.CANT,
       cUsuConteo_in,
       SYSDATE,
       vIp_in
  FROM (SELECT COD_PROD, 
               SUM(CANTIDAD) CANT
          FROM AUX_LGT_PROD_CONTEO AP
         WHERE AP.COD_GRUPO_CIA = cCodGrupoCia_in
           AND AP.COD_LOCAL = cCodLocal_in
           AND AP.NRO_RECEP = cNroRecep_in
           AND AP.COD_PROD NOT IN ('000000')--no incluye los que no tienen codigo de barra
         GROUP BY COD_PROD) V1
 ORDER BY ROWNUM ASC;
 
 --TABLA RESUMEN POR BULTO
  INSERT INTO LGT_PROD_CONTEO_BULTO
   (cod_grupo_cia, 
    cod_local, 
    nro_recep, 
    cod_prod, 
    corr_bulto, 
    cantidad, 
    fec_crea, 
    usu_crea
   )
   SELECT V1.COD_GRUPO_CIA,
           V1.COD_LOCAL,
           V1.NRO_RECEP,
           V1.COD_PROD,
           V1.CORR_BULTO,
           V1.CANTIDAD,
           SYSDATE,
           cUsuConteo_in
   FROM (
        SELECT A.COD_GRUPO_CIA,
               A.COD_LOCAL,
               A.NRO_RECEP,
               A.COD_PROD,
               A.CORR_BULTO,
               SUM(A.CANTIDAD) CANTIDAD
        FROM AUX_LGT_PROD_CONTEO A
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.NRO_RECEP = cNroRecep_in
         AND A.COD_PROD NOT IN ('000000')
        GROUP BY A.COD_GRUPO_CIA,
                 A.COD_LOCAL,
                 A.NRO_RECEP,
                 A.COD_PROD,
                 A.CORR_BULTO,
                 A.COD_BULTO) V1;

  END;

  /****************************************************************************/
  
  FUNCTION RECEP_F_IND_BULTO_EXISTS(cCodGrupoCia_in IN CHAR,
                                    cCodCia_in      in char,
                                    cCodLocal_in    IN CHAR,
                                    cNroRecep_in    IN CHAR,
                                    vNroBulto_in    IN VARCHAR2) 
   return char 
   iS
     CANTIDAD number(10) := 0;
     IND CHAR(1) := 'S';
   BEGIN
   
     SELECT COUNT(1)
     into CANTIDAD
     FROM LGT_RECEP_BANDEJA_RECEP A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
     AND A.COD_LOCAL = cCodLocal_in
     AND A.NRO_RECEP = cNroRecep_in
     AND A.NRO_BANDEJA = vNroBulto_in;
     
     IF CANTIDAD = 0 THEN
        IND := 'N';
     END IF;
     
     RETURN IND;
   
   END;

    /*********************************************************************/
    
    FUNCTION F_IMPR_VOU_BULTO_HTML(cCodGrupoCia_in   IN CHAR,
                                 cCodCia_in in char,
                                 cCodLocal_in      IN CHAR,
                                 cCorrBulto_in in char
                							 )
  RETURN VARCHAR2
  IS

  vContenido varchar2(6000):= '';
  
  vMsg_out varchar2(32767):= '';
  numero number(4) := 0;
    
  BEGIN
    
  SELECT TO_NUMBER(SUBSTR(cCorrBulto_in,2,4),9999)
        INTO numero
        FROM DUAL;

     vContenido := '<table width="337" border="0" 
                   align="center" cellpadding="0" cellspacing="0">                                  
								   <tr>
                   <td align="center">'|| 
                substr(cCorrBulto_in,1,1) || '<br>';
     IF numero > 999 THEN   
         vContenido := vContenido ||
                    substr(cCorrBulto_in,2,1) || '<br>';
     END IF;
                
     vContenido:= vContenido ||
                substr(cCorrBulto_in,3,1) || '<br>' ||
                substr(cCorrBulto_in,4,1) || '<br>' ||
                substr(cCorrBulto_in,5,1) || '<br>' ||
									 
                                    '</td>
                                      </tr>
                              </table>';
      
                            /*  '<table width="337" border="0" align="center" cellpadding="0" cellspacing="0">                                  
								   <tr>
                                   <td align="center">
                                   ' || substr(cCorrBulto_in,1,1) || '
								   </td>
								   </tr>';
--
               IF numero > 999 THEN
                   vContenido := vContenido ||		   
                                 '<tr>
                                                 <td align="center">
                                                 ' || substr(cCorrBulto_in,2,1) || '
                                 </td>
                                 </tr>';
                END IF;
--
     vContenido := vContenido ||			   
								   '<tr>
                                   <td align="center">
                                   ' || substr(cCorrBulto_in,3,1) || '
								   </td>
								   </tr>
								   
								   <tr>
                                   <td align="center">
                                   ' || substr(cCorrBulto_in,4,1) || '
								   </td>
								   </tr>
								   
								   <tr>
                                   <td align="center">
                                   ' || substr(cCorrBulto_in,5,1) || '
								   </td>
								   </tr>
	
                              </table>';*/
    

     vMsg_out := C_INICIO_MSG2 ||
                 vContenido ||
                 C_FIN_MSG ;

     RETURN vMsg_out;

  END;
  
   /****************************************************************************/
   
   FUNCTION RECEP_F_IND_LOCAL_BEEP(cCodGrupoCia_in IN CHAR,
                                    cCodCia_in      in char,
                                    cCodLocal_in    IN CHAR) 
   return char 
   iS
     IND CHAR(1) := 'S';
   BEGIN
   
     SELECT NVL(A.IND_USAR_BEEP_BIOS, 'N')
     into IND
     FROM PBL_LOCAL A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
     AND A.COD_LOCAL = cCodLocal_in
     AND A.COD_CIA = cCodCia_in;
     
     RETURN IND;
   
   END;

    /*********************************************************************/
 
 END PTOVENTA_RECEP_CIEGA_NEW;
/
