CREATE OR REPLACE PACKAGE "PTOVENTA_RECEP_CIEGA_AS" is

TYPE FarmaCursor IS REF CURSOR;
C_INICIO_MSG VARCHAR2(20000) := '<html>'  ||
                                      '<head>'  ||
                                      '<style type="text/css">'  ||
                                      '.style3 {font-family: Arial, Helvetica, sans-serif}'  ||
                                      '.style8 {font-size: 27; }'  ||
                                      '.style9 {font-size: 25}'  ||
                                      '.style12 {'  ||
                                      'font-family: Arial, Helvetica, sans-serif;'  ||
                                      'font-size: 30;'  ||
                                      'font-weight: bold;'  ||
                                      '}'  ||
                                      '</style>'  ||
                                      '</head>'  ||
                                      '<body>'  ||
                                      '<table width="510"border="0">'  ||
                                      '<tr>'  ||
                                      '<td width="500" align="center" valign="top" class="style8"><b>CONSTANCIA <BR> INGRESO&nbsp;DE&nbsp;TRANSPORTISTA</b></td>'  ||
                                      '</tr>'  ||
                                      '</table>'  ||
                                      '<table width="504" border="0">';

  C_FIN_MSG VARCHAR2(2000) := '</table>' ||
                                  '</body>' ||
                                  '</html>';

   TIPO_PROVEEDOR CHAR(1) := 'P'; --ASOSA - 25/07/2014


--Descripcion: Obtiene listado de empresas de transporte para recepcion ciega
--Fecha       Usuario		Comentario
--05/04/2010  ASOSA     Creación
FUNCTION RECEP_F_LISTA_TRANSP
RETURN FarmaCursor;

--Descripcion: Inserta la recepcion con el codigo de empresa de ransporte para recepcion ciega
--Fecha       Usuario		Comentario
--06/04/2010  ASOSA     Creación
-- AAMPUERO 15.04.2014                      nCantPrecintos IN NUMBER,

FUNCTION RECEP_F_INS_TRANSPORTISTA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal      IN CHAR,
                                      cCantGuias     IN NUMBER,
                                      cIdUsu_in      IN CHAR,
                                      cNombTransp    IN CHAR,
                                      cPlaca         IN CHAR,
                                      nCantBultos    IN NUMBER,
                                      nCantBandejas IN NUMBER,
                                      cGlosa IN VARCHAR2 DEFAULT '',
                                      cSecUsu_in     IN CHAR,
                                      cCodTransp IN  CHAR,
                                       cNroHojaRes IN VARCHAR2,    --ASOSA - 24/07/2014
                                       cCodValija IN varchar2, --ASOSA - 25/07/2014
                                      aBandejas_IN  IN VARCHAR2_TABLE,
                                      aBandejas_OUT IN VARCHAR2_TABLE default null
                                      )
RETURN VARCHAR2;

--Descripcion: Diseña y devuelve el texto que se imprimira en la constancia de transportista
--Fecha       Usuario		Comentario
--06/04/2010  ASOSA     Creación
FUNCTION RECEP_F_VAR2_IMP_VOUCHER(cGrupoCia_in  IN CHAR,
                                              cCodLocal_in  IN CHAR,
                                              cNroRecep_in IN VARCHAR2)
RETURN VARCHAR2;

--Descripcion: Devuelve cantidad de tickets para imprimir.
--Fecha       Usuario		Comentario
--05/05/2010  JQUISPE     Creación
  FUNCTION  RECEP_F_GET_NUM_IMPRES
  RETURN CHAR;

--Descripcion: Devuelve el numero de DT mas la cantidad de bultos que contiene.
--Fecha       Usuario		Comentario
--18/07/2014  ASOSA    Creación
FUNCTION RECEP_F_GET_DOC_TRANSPORTE(vNroHoja_in IN varchar2)
RETURN FarmaCursor;

--Descripcion: Devuelve "S" si existe la bandeja asociada a la hoja de resumen (Documento de Transporte), o "N" si no.
--Fecha       Usuario		Comentario
--18/07/2014  ASOSA    Creación
FUNCTION RECEP_F_GET_BANDEJA_HR(vNroHoja_in IN varchar2,
                                                                      vNroBandeja_in IN varchar2)
RETURN CHAR;

--Descripcion: Devuelve "S" si existe grabado previamente el numero de hoja de resumen, o "N" si no.
--Fecha       Usuario		Comentario
--13/08/2014  ASOSA    Creación
FUNCTION RECEP_F_EXISTS_HOJA_RES(vNroHoja_in IN varchar2,
                                 vIndNvoAlgoritmo in varchar2)
RETURN CHAR;

FUNCTION F_VAR_IND_ACT_NVO_RCIEGA
RETURN VARCHAR2;

FUNCTION F_CUR_MOTV_NO_DEV_BANDEJA
RETURN FarmaCursor;

FUNCTION F_VAR_IND_PERMITE_FALTANTE
RETURN VARCHAR2;
FUNCTION F_VAR_IND_PERMITE_SOBRANTE
RETURN VARCHAR2;
FUNCTION F_VAR_EXISTE_HOJA_MAESTRO(cCodGrupoCia_in in varchar2,
                                   vNroHoja_in     IN varchar2) RETURN CHAR;
FUNCTION F_VAR_DATA_HOJA_RES(cCodGrupoCia_in in varchar2,
                                   vNroHoja_in     IN varchar2) 
RETURN VARCHAR2;

FUNCTION F_CUR_LIST_BANDEJA_IN(cCodGrupoCia_in in varchar2,
                             vNroHoja_in     IN varchar2) 
RETURN FarmaCursor;

FUNCTION F_VAR_SAVE_NVO_RECEP_TRANS(cCodGrupoCia_in IN CHAR,
                                  cCodLocal      IN CHAR,
                                  cCantGuias     IN NUMBER,
                                  cIdUsu_in      IN CHAR,
                                  cNombTransp    IN CHAR,
                                  cPlaca         IN CHAR,
                                  nCantBultos    IN NUMBER,
                                  nCantBandejas IN NUMBER,
                                  cGlosa IN VARCHAR2 DEFAULT '',
                                  cSecUsu_in     IN CHAR,
                                  cCodTransp IN CHAR,
                                  cNameTrans  IN varchar2,
                                  cNroHojaRes IN VARCHAR2,    
                                  cCodValija IN varchar2, 
                                  cMotNoDevolucion_in IN varchar2, 
                                  aBandejas_IN  IN VARCHAR2_TABLE,
                                  aBandejasDev_IN  IN VARCHAR2_TABLE,
                                  vIndModificar_in in char,
                                  vNuevo_in in char,
                                  vNumRecep_in in char,
                                  aBorraBandejas_IN  IN VARCHAR2_TABLE,
                                  aBorraBandejasDev_IN  IN VARCHAR2_TABLE,
                                  aBorraBandejasLazer_IN  IN VARCHAR2_TABLE,
                                  aBorraBandejasDevLazer_IN  IN VARCHAR2_TABLE    ,
                                  vIndHojaLazer_in in char,
                                  vDevolver_in in char                              
                                  )
                                  RETURN VARCHAR2;

FUNCTION F_VAR_GET_IMP_RECEP(cGrupoCia_in  IN CHAR,
                             cCodLocal_in  IN CHAR,
                             cNroRecep_in IN VARCHAR2) RETURN VARCHAR2;                                  

FUNCTION F_CUR_BAND_RECEP(cCodGrupoCia_in	IN CHAR,
                          cCodLocal_in	  	IN CHAR,
                          cFechaInicio      IN CHAR,
                          cFechaFin         IN CHAR)
RETURN FarmaCursor;

FUNCTION F_CUR_BAND_RECEP_DEFAULT(cCodGrupoCia_in	IN CHAR,
                          cCodLocal_in	  	IN CHAR)
RETURN FarmaCursor;

FUNCTION F_CUR_BAND_POR_DEVOL(cCodGrupoCia_in	IN CHAR,
                          cCodLocal_in	  	IN CHAR)
RETURN FarmaCursor;

procedure P_SAVE_PARA_DEVOLVER(cCodGrupoCia_in IN CHAR,
                               cCodLocal      IN CHAR,
                               cNroBandeja    IN char,
                               cNroHoja       IN char,
                               cNroRecp       IN char
                               );


procedure P_SAVE_PARA_REVERTIR(cCodGrupoCia_in IN CHAR,
                               cCodLocal      IN CHAR,
                               cNroBandeja    IN char,
                               cNroHoja       IN char,
                               cNroRecp       IN char
                               );

FUNCTION F_VAR_ACCION_HOJA_EXISTE(cCodGrupoCia_in in varchar2,
                                  cCodLocal_in    in varchar2,
                                  vNroHoja_in     IN varchar2) RETURN VARCHAR2;                               

FUNCTION F_CUR_BAND_RECEP(cCodGrupoCia_in	IN CHAR,
                          cCodLocal_in		IN CHAR,
                          cNumRecep_in    IN CHAR)
RETURN FarmaCursor;

FUNCTION F_CUR_BAND_DEVOL(cCodGrupoCia_in	IN CHAR,
                          cCodLocal_in		IN CHAR,
                          cNumRecep_in    IN CHAR)
RETURN FarmaCursor;

FUNCTION F_VAR_EXISTE_BANDEJA_RES(vCodGrupoCia_in IN varchar2,
                                  vCodLocal_in IN varchar2,
                                  vNroHoja_in IN varchar2,
                                  vNroBandeja_in IN varchar2)
RETURN CHAR;

FUNCTION F_VAR_IS_VALIDO_HOJA(cCodGrupoCia_in in varchar2,
                              cCodLocal_in    in varchar2,
                              vNroHoja_in     IN varchar2) RETURN VARCHAR2;

FUNCTION F_VAR_IS_VALIDO_BANDEJA(cCodGrupoCia_in in varchar2,
                                 cCodLocal_in    in varchar2,
                                 vBandeja_in     IN varchar2) RETURN VARCHAR2;
                                 
procedure P_SAVE_BANDEJA_POR_DEVOLVER(cCodGrupoCia_in IN CHAR,
                                      cCodLocal      IN CHAR,
                                      cNroBandeja    IN char,
                                      cIndExisteBandeja_in in char);                                 
FUNCTION F_VAR_IS_VALIDO_TIPO_DEVOL(cCodGrupoCia_in in varchar2,

                                    cCodLocal_in    in varchar2,
                                    vBandeja_in     IN varchar2) RETURN VARCHAR2;

end PTOVENTA_RECEP_CIEGA_AS;
/
CREATE OR REPLACE PACKAGE BODY "PTOVENTA_RECEP_CIEGA_AS" is

/********************************************************************************************************************************/

FUNCTION RECEP_F_LISTA_TRANSP
RETURN FarmaCursor
IS
cur FarmaCursor;
BEGIN
OPEN cur FOR
  SELECT c.cod_transp || 'Ã' || c.nom_transp
  FROM LGT_TRANSP_CIEGA C
 WHERE ESTADO='A'
 AND TIPO_TRANSP = TIPO_PROVEEDOR  --ASOSA - 25/07/2014
 ORDER BY  C.NOM_TRANSP ASC;
RETURN cur;
END;

/********************************************************************************************************************************/
FUNCTION RECEP_F_INS_TRANSPORTISTA(cCodGrupoCia_in IN CHAR,
                                  cCodLocal      IN CHAR,
                                  cCantGuias     IN NUMBER,
                                  cIdUsu_in      IN CHAR,
                                  cNombTransp    IN CHAR,
                                  cPlaca         IN CHAR,
                                  nCantBultos    IN NUMBER,
                                  nCantBandejas IN NUMBER,
                                  cGlosa IN VARCHAR2 DEFAULT '',
                                  cSecUsu_in     IN CHAR,
                                  cCodTransp IN CHAR,
                                  cNroHojaRes IN VARCHAR2,    --ASOSA - 24/07/2014
                                  cCodValija IN varchar2, --ASOSA - 25/07/2014
                                  aBandejas_IN  IN VARCHAR2_TABLE,
                                  aBandejas_OUT IN VARCHAR2_TABLE default null
                                  )
                                  RETURN VARCHAR2
  IS
  v_nCant  NUMBER;
  nroRecep NUMBER;
  vRecep   VARCHAR2(15);
  v_ip VARCHAR2(20);
   vIndRecepCiega CHAR(1) := 'S';
  BEGIN

  SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip
    FROM DUAL;

  SELECT (L.LLAVE_TAB_GRAL)
    INTO vIndRecepCiega
    FROM PBL_TAB_GRAL L
   WHERE L.ID_TAB_GRAL = 326;
    DBMS_OUTPUT.put_line('vIndRecepCiega: '||vIndRecepCiega);
    nroRecep := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal, '074');
     vRecep :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(nroRecep,10,0,'I');

    INSERT INTO LGT_RECEP_MERCADERIA(
             COD_GRUPO_CIA,
             COD_LOCAL,
             NRO_RECEP,
             FEC_RECEP,
             CANT_GUIAS,
             ESTADO,
             USU_CREA_RECEP,
             FEC_CREA_RECEP,
             IP_CREA_RECEP,
             NOMBRE_TRANS,
             HORA_LLEGADA,
             PLACA_UND,
             CANT_BULTOS,
             CANT_BANDEJAS,
             IND_AFEC_RECEP_CIEGA,
             GLOSA,
             sec_usu_crea ,
             Cod_Transp,
             NRO_HOJA_RESUMEN,    --ASOSA - 24/07/2014
             COD_VALIJA_DEV       --ASOSA - 25/07/2014
             )
      VALUES (cCodGrupoCia_in,
              cCodLocal,
              vRecep,
              SYSDATE,
              cCantGuias,
              'E',--EMITIDO
              cIdUsu_in,
              SYSDATE,
              v_ip,
              cNombTransp,SYSDATE,cPlaca,nCantBultos,nCantBandejas,
              vIndRecepCiega, --'S' RECEP_CIEGA, 'N' ANTIGUA
              cGlosa,
              cSecUsu_in,
              cCodTransp,
              cNroHojaRes,    --ASOSA - 24/07/2014
              cCodValija --ASOSA - 25/07/2014
              );

              --INI ASOSA - 25/07/2014
              FOR c IN 1 .. aBandejas_IN.COUNT LOOP

                  INSERT INTO LGT_BANDEJA_HOJA_RES(NRO_HOJA_RES,NRO_BANDEJA)
                   VALUES(cNroHojaRes, aBandejas_IN(c));

              END LOOP;
              --FIN ASOSA - 25/07/2014

            Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal,'074',cIdUsu_in);

  RETURN vRecep;

  EXCEPTION
     WHEN DUP_VAL_ON_INDEX THEN
        RAISE_APPLICATION_ERROR(-20002,'EL NUMERO DE INGRESO YA EXISTE. VERIFIQUE!!!');

  END;

/********************************************************************************************************************************/
-- AAMPUERO 15.04.2014 nCantPrecintos IN NUMBER,
FUNCTION RECEP_F_VAR2_IMP_VOUCHER(cGrupoCia_in  IN CHAR,
                                              cCodLocal_in  IN CHAR,
                                              cNroRecep_in IN VARCHAR2) RETURN VARCHAR2
    IS
    vMsg_out VARCHAR2(32767) := '';
    vMsg_1 VARCHAR2(32767) := '';
    vCab_TIPO VARCHAR2(10000) := '';

    vBandejas VARCHAR2(10000) := ''; --ASOSA - 25/07/2014

    vTransp VARCHAR(500);
    vTipo CHAR(1);
    vEstado CHAR(1);
    vCantBultos LGT_RECEP_MERCADERIA.CANT_BULTOS%TYPE;
    vCantPrecintos LGT_RECEP_MERCADERIA.CANT_PRECINTOS%TYPE;
    vCantBandejas LGT_RECEP_MERCADERIA.CANT_BANDEJAS%TYPE;
    vFechaCrea LGT_RECEP_MERCADERIA.FEC_CREA_RECEP%TYPE ;
    vHojaRes LGT_RECEP_MERCADERIA.NRO_HOJA_RESUMEN%TYPE ; --ASOSA - 24/07/2014
    vCodValija LGT_RECEP_MERCADERIA.COD_VALIJA_DEV%TYPE ; --ASOSA - 25/07/2014

    vLocal VARCHAR2(300) := '';
    vUsu_Crea VARCHAR2(500) := '';
    vSec_Usu_crea lgt_recep_mercaderia.sec_usu_crea%TYPE;
    vLogin VARCHAR2(30) := '';
    vPlaca VARCHAR2(30) := '';
    vGlosa LGT_RECEP_MERCADERIA.GLOSA%TYPE;
    cCodTransp Lgt_Recep_Mercaderia.Cod_Transp%TYPE;
    vNomTransp Lgt_Transp_Ciega.Nom_Transp%TYPE;
    vIndNvoFunc char(1);
   BEGIN
   
   SELECT nvl(IND_NVO_FUNC,'N')
   INTO   vIndNvoFunc 
   FROM   LGT_RECEP_MERCADERIA T
   WHERE  T.COD_GRUPO_CIA =  cGrupoCia_in  
   AND    T.COD_LOCAL = cCodLocal_in
   AND    T.NRO_RECEP = cNroRecep_in;
     
   IF vIndNvoFunc = 'S' then
      RETURN F_VAR_GET_IMP_RECEP(cGrupoCia_in,cCodLocal_in,cNroRecep_in);
   else   

   SELECT O.COD_LOCAL||' '||O.DESC_CORTA_LOCAL
     INTO vLocal
     FROM PBL_LOCAL O
    WHERE O.COD_GRUPO_CIA = cGrupoCia_in
      AND O.COD_LOCAL = cCodLocal_in;

   SELECT M.FEC_CREA_RECEP, M.NOMBRE_TRANS, M.CANT_BULTOS, M.CANT_BANDEJAS, M.USU_CREA_RECEP,
          M.SEC_USU_CREA, M.PLACA_UND,M.GLOSA, M.COD_TRANSP, M.Nro_Hoja_Resumen, M.COD_VALIJA_DEV
     INTO vFechaCrea, vTransp, vCantBultos, vCantBandejas, vLogin, vSec_Usu_crea, vPlaca,vGlosa, cCodTransp,
                      vHojaRes, --ASOSA - 24/07/2014
                      vCodValija --ASOSA - 25/07/2014
     FROM LGT_RECEP_MERCADERIA M
    WHERE M.COD_GRUPO_CIA = cGrupoCia_in
      AND M.COD_LOCAL = cCodLocal_in
      AND M.NRO_RECEP = cNroRecep_in;

  BEGIN
    SELECT nvl(x.nom_transp,' ') INTO vNomTransp
    FROM lgt_transp_ciega x
    WHERE x.cod_transp=cCodTransp;
  EXCEPTION WHEN NO_DATA_FOUND THEN
    vNomTransp:='SIN ASIGNAR';
  END;

   IF trim(vSec_Usu_crea) IS NULL THEN
        vUsu_Crea := vLogin;
   ELSE
     SELECT L.NOM_USU||' '||L.APE_PAT||' '||L.APE_MAT
       INTO vUsu_Crea
       FROM PBL_USU_LOCAL L
      WHERE L.COD_GRUPO_CIA = cGrupoCia_in
        AND L.COD_LOCAL = cCodLocal_in
        AND L.SEC_USU_LOCAL = vSec_Usu_crea;
   END IF;

       --INI ASOSA - 25/07/2014
       BEGIN
             FOR cur_recep IN (
                   SELECT distinct(H.NRO_BANDEJA)
                   FROM LGT_BANDEJA_HOJA_RES H
                   WHERE H.NRO_HOJA_RES = vHojaRes)
             LOOP
                   vBandejas := vBandejas || cur_recep.nro_bandeja || ' - ';
             END LOOP;
             vBandejas := SUBSTR(vBandejas, 1, length(vBandejas)-3);
      END;
       --FIN ASOSA - 25/07/2014

       vCab_Tipo := '<tr>'||
                    '<td align="center"><h2> LOCAL : '||vLocal||'</h2>'||
                    '</td></tr>';
          vMsg_1 := '<tr><td class="style9">'||
                    'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
                    'Yo, '||vUsu_Crea||', Adm. Local&nbsp <b>CONFIRMO LA RECEPCI&Oacute;N DE LA MERCADER&Iacute;A </b> '||
                    'entregada por el Sr. Transportista '||vTransp||' de la '||
                    'empresa de transporte '||vNomTransp||
                    ', en la unidad con Placa: '||vPlaca||'.<br>'||
                    '<br>'||
                    'NRO HOJA DE RESUMEN: '|| vHojaRes ||'<br>'||  --ASOSA - 24/07/2014
                    'LA RECEPCION CONSTA DE: '||vCantBultos||' Bulto/Bandeja(s).<br>'||
                    'RELACION DE BANDEJAS: <br>'|| vBandejas ||'<br>'||  --ASOSA - 25/07/2014
                    'SE ESTA DEVOLVIENDO LA CANT. DE: '||vCantBandejas||' Bandeja(s).<br>'||
                    'SE ESTA DEVOLVIENDO LA VALIJA NRO: '||vCodValija||'<br>'||
                    'Glosa: '||vGlosa||'<br>'||
                    '<br>'||
                    'Firma Transportista:&nbsp; ____________________<br><br>'||
                    'Firma Adm. Local:&nbsp;&nbsp;&nbsp;&nbsp;____________________<br><br>'||
                    '_'||
                    '</td></tr>';

    vMsg_out := C_INICIO_MSG || vCab_Tipo || vMsg_1 ||
              C_FIN_MSG;
              
    RETURN vMsg_out;
    
    end if;
    
    END;

/********************************************************************************************************************************/

  FUNCTION  RECEP_F_GET_NUM_IMPRES
  RETURN CHAR
  is
  vNumImpres VARCHAR2(10);
  BEGIN

  select p.llave_tab_gral into vNumImpres
  from pbl_tab_gral p
  where p.id_tab_gral='358';

  RETURN vNumImpres;
  END;

/********************************************************************************************************************************/

FUNCTION RECEP_F_GET_DOC_TRANSPORTE(vNroHoja_in IN varchar2)
RETURN FarmaCursor
IS
cur FarmaCursor;
BEGIN
OPEN cur FOR
        SELECT NUM_HOJA || 'Ã' || CANT_BULTO
        FROM(
        SELECT distinct a.nro_hoja_resumen AS NUM_HOJA,
                        count(distinct a.nro_bandeja) AS CANT_BULTO
        FROM int_recep_prod_qs a
        where a.nro_hoja_resumen is not null
        and a.nro_bandeja is not null
        and a.txt_23_nro_hoja_resumen is not null
        and a.txt_24_nro_bandeja is not null
        AND A.NRO_HOJA_RESUMEN = vNroHoja_in
        group by a.nro_hoja_resumen
        );
RETURN cur;
END;


/********************************************************************************************************************************/

FUNCTION RECEP_F_GET_BANDEJA_HR(vNroHoja_in IN varchar2,
                                vNroBandeja_in IN varchar2)
RETURN CHAR
IS
    cantidad number(5) :=  0;
    flag CHAR(1) := 'N';
    BEGIN
             select count(*)
             into cantidad
             from int_recep_prod_qs a
             where a.nro_hoja_resumen is not null
            and a.nro_bandeja is not null
            and a.txt_23_nro_hoja_resumen is not null
            and a.txt_24_nro_bandeja is not null
            AND A.NRO_HOJA_RESUMEN = vNroHoja_in
            AND A.NRO_BANDEJA = 'BDP-' || vNroBandeja_in;

    IF cantidad > 0 then
                flag := 'S';
    END IF;

    RETURN flag;
END;

/********************************************************************************************************************************/
FUNCTION RECEP_F_EXISTS_HOJA_RES(vNroHoja_in IN varchar2,
                                 vIndNvoAlgoritmo in varchar2)
RETURN CHAR
IS
    cantidad number(5) :=  0;
    flag CHAR(1) := 'N';
    BEGIN
      if vIndNvoAlgoritmo = 'N' then
             select count(*)
             into cantidad
             from LGT_RECEP_MERCADERIA a
             where a.nro_hoja_resumen is not null
            and upper(a.nro_hoja_resumen) = upper(trim(vNroHoja_in));
      else
             select count(*)
             into cantidad
             from LGT_RECEP_MERCADERIA a
             where a.cod_grupo_cia = '001'
             and   a.fec_recep >= to_date('01/04/2015','dd/mm/yyyy')
             and   a.num_hoja_res is not null
            and    a.num_hoja_res = upper(trim(vNroHoja_in));        
      end if;       

    IF cantidad > 0 then
                flag := 'S';
    END IF;

    RETURN flag;
END;

/********************************************************************************************************************************/
FUNCTION F_VAR_IND_ACT_NVO_RCIEGA
RETURN VARCHAR2
IS
    flag VARCHAR2(10) := 'N';
BEGIN
  BEGIN 
    SELECT LLAVE_TAB_GRAL  INTO flag FROM   pbl_tab_gral WHERE  ID_TAB_GRAL = 698;  
  EXCEPTION
    WHEN OTHERS THEN 
      flag := 'N'  ;
  END;   
  
  RETURN flag;
END;
/* ******************************************************************* */
FUNCTION F_CUR_MOTV_NO_DEV_BANDEJA
RETURN FarmaCursor
IS
cur FarmaCursor;
BEGIN
OPEN cur FOR
SELECT DESC_LARGA  || 'Ã' || LLAVE_TAB_GRAL
FROM   pbl_tab_gral 
WHERE  cod_apl = 'PTOVENTA' 
and cod_tab_gral = 'MOT_NO_DEV_BANDEJA' 
AND EST_TAB_GRAL = 'A'
order by LLAVE_TAB_GRAL asc;

 
RETURN cur;
END;
/* ******************************************************************* */
FUNCTION F_VAR_IND_PERMITE_FALTANTE
RETURN VARCHAR2
IS
    flag VARCHAR2(10) := 'N';
BEGIN
  BEGIN 
    SELECT LLAVE_TAB_GRAL  INTO flag FROM   pbl_tab_gral WHERE  ID_TAB_GRAL = 703;  
  EXCEPTION
    WHEN OTHERS THEN 
      flag := 'N'  ;
  END;   
  
  RETURN flag;
END;
/* ******************************************************************* */
FUNCTION F_VAR_IND_PERMITE_SOBRANTE
RETURN VARCHAR2
IS
    flag VARCHAR2(10) := 'N';
BEGIN
  BEGIN 
    SELECT LLAVE_TAB_GRAL  INTO flag FROM   pbl_tab_gral WHERE  ID_TAB_GRAL = 704;  
  EXCEPTION
    WHEN OTHERS THEN 
      flag := 'N'  ;
  END;   
  
  RETURN flag;
END;
/* ******************************************************************* */
FUNCTION F_VAR_EXISTE_HOJA_MAESTRO(cCodGrupoCia_in in varchar2,
                                   vNroHoja_in     IN varchar2) RETURN CHAR IS
  cantidad number(5) := 0;
  flag     CHAR(1) := 'N';
BEGIN
  select count(*)
    into cantidad
    from LGT_HOJA_RESUMEN_CAB h
   where h.cod_grupo_cia = cCodGrupoCia_in
     and h.num_hoja_res = vNroHoja_in;

  IF cantidad > 0 then
    flag := 'S';
  END IF;

  RETURN flag;
END;
/* ******************************************************************* */
FUNCTION F_VAR_DATA_HOJA_RES(cCodGrupoCia_in in varchar2,
                             vNroHoja_in     IN varchar2) 
RETURN VARCHAR2 is
  vDataHojaRes VARCHAR2(5000) := '';
BEGIN
  select nvl(h.cod_transportista,' ') ||'@'||
         nvl(h.nom_transportista,' ') ||'@'||
         nvl(h.placa,' ')
    into vDataHojaRes
    from LGT_HOJA_RESUMEN_CAB h
   where h.cod_grupo_cia = cCodGrupoCia_in
     and h.num_hoja_res = vNroHoja_in;
     
  RETURN vDataHojaRes;
END;
/* ********************************************************************* */
FUNCTION F_CUR_LIST_BANDEJA_IN(cCodGrupoCia_in in varchar2,
                             vNroHoja_in     IN varchar2) 
RETURN FarmaCursor
IS
cur FarmaCursor;
BEGIN
OPEN cur FOR
SELECT d.num_bandeja||'@'||nvl(d.nro_bandeja_ext,' ')
FROM   LGT_HOJA_RESUMEN_DET d 
WHERE  d.cod_grupo_cia = cCodGrupoCia_in
and    d.num_hoja_res = vNroHoja_in;
 
RETURN cur;
END;
/* *********************************************************************** */
FUNCTION F_VAR_SAVE_NVO_RECEP_TRANS(cCodGrupoCia_in IN CHAR,
                                  cCodLocal      IN CHAR,
                                  cCantGuias     IN NUMBER,
                                  cIdUsu_in      IN CHAR,
                                  cNombTransp    IN CHAR,
                                  cPlaca         IN CHAR,
                                  nCantBultos    IN NUMBER,
                                  nCantBandejas IN NUMBER,
                                  cGlosa IN VARCHAR2 DEFAULT '',
                                  cSecUsu_in     IN CHAR,
                                  cCodTransp IN CHAR,
                                  cNameTrans  IN varchar2,
                                  cNroHojaRes IN VARCHAR2,    
                                  cCodValija IN varchar2, 
                                  cMotNoDevolucion_in IN varchar2, 
                                  aBandejas_IN  IN VARCHAR2_TABLE,
                                  aBandejasDev_IN  IN VARCHAR2_TABLE,
                                  vIndModificar_in in char,
                                  vNuevo_in in char,
                                  vNumRecep_in in char,
                                  aBorraBandejas_IN  IN VARCHAR2_TABLE,
                                  aBorraBandejasDev_IN  IN VARCHAR2_TABLE,
                                  aBorraBandejasLazer_IN  IN VARCHAR2_TABLE,
                                  aBorraBandejasDevLazer_IN  IN VARCHAR2_TABLE,
                                  vIndHojaLazer_in in char,
                                  vDevolver_in in char
                                  )
                                  RETURN VARCHAR2
  IS
  v_nCant  NUMBER;
  nroRecep NUMBER;
  vRecep   VARCHAR2(15);
  v_ip VARCHAR2(20);
   vIndRecepCiega CHAR(1) := 'S';
   nNoExiste number;
  BEGIN
  
  if vDevolver_in = 'S' then 
  SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip
    FROM DUAL;

  SELECT (L.LLAVE_TAB_GRAL)
    INTO vIndRecepCiega
    FROM PBL_TAB_GRAL L
   WHERE L.ID_TAB_GRAL = 326;
    DBMS_OUTPUT.put_line('vIndRecepCiega: '||vIndRecepCiega);
    nroRecep := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal, '074');
     vRecep :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(nroRecep,10,0,'I');

    INSERT INTO LGT_RECEP_MERCADERIA(
             COD_GRUPO_CIA,
             COD_LOCAL,
             NRO_RECEP,
             FEC_RECEP,
             CANT_GUIAS,
             ESTADO,
             USU_CREA_RECEP,
             FEC_CREA_RECEP,
             IP_CREA_RECEP,
             NOMBRE_TRANS,
             HORA_LLEGADA,
             PLACA_UND,
             CANT_BULTOS,
             CANT_BANDEJAS,
             IND_AFEC_RECEP_CIEGA,
             GLOSA,
             sec_usu_crea ,
             Cod_Transp,
             NRO_HOJA_RESUMEN,
             num_hoja_res,
             COD_VALIJA_DEV ,
             NAME_EMP_TRANS,
             MOTIVO_NO_DEV,
             ind_nvo_func,
             IND_LAZER
             )
      VALUES (cCodGrupoCia_in,
              cCodLocal,
              vRecep,
              SYSDATE,
              cCantGuias,
              'D',--EMITIDO
              cIdUsu_in,
              SYSDATE,
              v_ip,
              cNombTransp,
              SYSDATE,cPlaca,nCantBultos,nCantBandejas,
              vIndRecepCiega, 
              cGlosa,
              cSecUsu_in,
              cCodTransp,
              cNroHojaRes,
              cNroHojaRes,              
              cCodValija ,
              cNameTrans,
              cMotNoDevolucion_in,
              'S',
              vIndHojaLazer_in
              );

              FOR c IN 1 .. aBandejasDev_IN.COUNT LOOP
               
                  UPDATE LGT_RECEP_BANDEJA_RECEP T
                  SET  T.ESTADO = 'D'
                  WHERE T.ROWID in (
                      SELECT c.rowid
                      FROM   LGT_RECEP_MERCADERIA RE,
                             LGT_RECEP_BANDEJA_RECEP C
                     WHERE   RE.COD_GRUPO_CIA = cCodGrupoCia_in
                     AND     RE.COD_LOCAL = cCodLocal
                     AND     RE.FEC_RECEP >= trunc(sysdate-60)
                     AND    RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                     AND    RE.COD_LOCAL = C.COD_LOCAL
                     AND    RE.NRO_RECEP = C.NRO_RECEP
                     AND    C.ESTADO in ('P','R')
                     and    C.nro_bandeja = aBandejasDev_IN(c));
              
                  INSERT INTO lgt_recep_bandeja_devol
                  (cod_grupo_cia, cod_local, nro_recep, nro_bandeja, usu_crea, fec_crea)
                   VALUES(cCodGrupoCia_in,cCodLocal,vRecep,aBandejasDev_IN(c),cIdUsu_in,sysdate);
              END LOOP;


              FOR c IN 1 .. aBorraBandejasDev_IN.COUNT LOOP
                  INSERT INTO lgt_recep_bandeja_devol_BORRA
                  (cod_grupo_cia, cod_local, nro_recep, nro_bandeja, usu_crea, fec_crea)
                   VALUES(cCodGrupoCia_in,cCodLocal,vRecep,aBorraBandejasDev_IN(c),cIdUsu_in,sysdate);
              END LOOP;  
              
                  
                  update LGT_RECEP_BANDEJA_DEVOL t 
                  set   t.ind_lazer = 'N'
                  where  cod_grupo_cia =cCodGrupoCia_in
                  and    cod_local =cCodLocal
                  and    nro_recep=vRecep;
              

              FOR c IN 1 .. aBorraBandejasDevLazer_IN.COUNT LOOP

                  update LGT_RECEP_BANDEJA_DEVOL t 
                  set   t.ind_lazer = 'S'
                  where  cod_grupo_cia =cCodGrupoCia_in
                  and    cod_local =cCodLocal
                  and    nro_recep=vRecep
                  and    nro_bandeja = aBorraBandejasDevLazer_IN(c);
              END LOOP;                            
              
              update lgt_recep_mercaderia re
              set    re.cant_bandejas = (
                                        select count(1)
                                        from   LGT_RECEP_BANDEJA_RECEP tt
                                        where  tt.cod_grupo_cia = cCodGrupoCia_in
                                        and    tt.cod_local = cCodLocal
                                        and    tt.nro_recep = vRecep
                                        and    tt.cod_grupo_cia = re.cod_grupo_cia
                                        and    tt.cod_local = re.cod_local
                                        and    tt.nro_recep = re.nro_recep
                                        )
              where  re.cod_grupo_cia =  cCodGrupoCia_in
              and    re.cod_local = cCodLocal
              and    re.nro_recep = vRecep;

            Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal,'074',cIdUsu_in);
  
  else
  if vNuevo_in = 'S' then 
  SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip
    FROM DUAL;

  SELECT (L.LLAVE_TAB_GRAL)
    INTO vIndRecepCiega
    FROM PBL_TAB_GRAL L
   WHERE L.ID_TAB_GRAL = 326;
    DBMS_OUTPUT.put_line('vIndRecepCiega: '||vIndRecepCiega);
    nroRecep := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal, '074');
     vRecep :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(nroRecep,10,0,'I');

    INSERT INTO LGT_RECEP_MERCADERIA(
             COD_GRUPO_CIA,
             COD_LOCAL,
             NRO_RECEP,
             FEC_RECEP,
             CANT_GUIAS,
             ESTADO,
             USU_CREA_RECEP,
             FEC_CREA_RECEP,
             IP_CREA_RECEP,
             NOMBRE_TRANS,
             HORA_LLEGADA,
             PLACA_UND,
             CANT_BULTOS,
             CANT_BANDEJAS,
             IND_AFEC_RECEP_CIEGA,
             GLOSA,
             sec_usu_crea ,
             Cod_Transp,
             NRO_HOJA_RESUMEN,
             num_hoja_res,
             COD_VALIJA_DEV ,
             NAME_EMP_TRANS,
             MOTIVO_NO_DEV,
             ind_nvo_func,
             IND_LAZER
             )
      VALUES (cCodGrupoCia_in,
              cCodLocal,
              vRecep,
              SYSDATE,
              cCantGuias,
              'E',--EMITIDO
              cIdUsu_in,
              SYSDATE,
              v_ip,
              cNombTransp,
              SYSDATE,cPlaca,nCantBultos,nCantBandejas,
              vIndRecepCiega, 
              cGlosa,
              cSecUsu_in,
              cCodTransp,
              cNroHojaRes,
              cNroHojaRes,              
              cCodValija ,
              cNameTrans,
              cMotNoDevolucion_in,
              'S',
              vIndHojaLazer_in
              );


              FOR c IN 1 .. aBandejas_IN.COUNT LOOP
                  INSERT INTO LGT_RECEP_BANDEJA_RECEP
                  (cod_grupo_cia, cod_local, nro_recep, nro_bandeja, usu_crea, fec_crea,estado)
                   VALUES(cCodGrupoCia_in,cCodLocal,vRecep,aBandejas_IN(c),cIdUsu_in,sysdate,'R');
              END LOOP;

              FOR c IN 1 .. aBandejasDev_IN.COUNT LOOP
               
                  UPDATE LGT_RECEP_BANDEJA_RECEP T
                  SET  T.ESTADO = 'D'
                  WHERE T.ROWID in (
                      SELECT c.rowid
                      FROM   LGT_RECEP_MERCADERIA RE,
                             LGT_RECEP_BANDEJA_RECEP C
                     WHERE   RE.COD_GRUPO_CIA = cCodGrupoCia_in
                     AND     RE.COD_LOCAL = cCodLocal
                     AND     RE.FEC_RECEP >= trunc(sysdate-60)
                     AND    RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                     AND    RE.COD_LOCAL = C.COD_LOCAL
                     AND    RE.NRO_RECEP = C.NRO_RECEP
                     AND    C.ESTADO = 'P'
                     and    C.nro_bandeja = aBandejasDev_IN(c));
              
                  INSERT INTO lgt_recep_bandeja_devol
                  (cod_grupo_cia, cod_local, nro_recep, nro_bandeja, usu_crea, fec_crea)
                   VALUES(cCodGrupoCia_in,cCodLocal,vRecep,aBandejasDev_IN(c),cIdUsu_in,sysdate);
              END LOOP;

              FOR c IN 1 .. aBorraBandejas_IN.COUNT LOOP
                  INSERT INTO LGT_RECEP_BANDEJA_RECEP_BORRA
                  (cod_grupo_cia, cod_local, nro_recep, nro_bandeja, usu_crea, fec_crea)
                   VALUES(cCodGrupoCia_in,cCodLocal,vRecep,aBorraBandejas_IN(c),cIdUsu_in,sysdate);
              END LOOP;

              FOR c IN 1 .. aBorraBandejasDev_IN.COUNT LOOP
                  INSERT INTO lgt_recep_bandeja_devol_BORRA
                  (cod_grupo_cia, cod_local, nro_recep, nro_bandeja, usu_crea, fec_crea)
                   VALUES(cCodGrupoCia_in,cCodLocal,vRecep,aBorraBandejasDev_IN(c),cIdUsu_in,sysdate);
              END LOOP;  
              

                  update LGT_RECEP_BANDEJA_RECEP t 
                  set   t.ind_lazer = 'N'
                  where  cod_grupo_cia =cCodGrupoCia_in
                  and    cod_local =cCodLocal
                  and    nro_recep=vRecep;
                  
                  update LGT_RECEP_BANDEJA_DEVOL t 
                  set   t.ind_lazer = 'N'
                  where  cod_grupo_cia =cCodGrupoCia_in
                  and    cod_local =cCodLocal
                  and    nro_recep=vRecep;
              
              FOR c IN 1 .. aBorraBandejasLazer_IN.COUNT LOOP
                  update LGT_RECEP_BANDEJA_RECEP t 
                  set   t.ind_lazer = 'S'
                  where  cod_grupo_cia =cCodGrupoCia_in
                  and    cod_local =cCodLocal
                  and    nro_recep=vRecep
                  and    nro_bandeja = aBorraBandejasLazer_IN(c);
              END LOOP;

              FOR c IN 1 .. aBorraBandejasDevLazer_IN.COUNT LOOP

                  update LGT_RECEP_BANDEJA_DEVOL t 
                  set   t.ind_lazer = 'S'
                  where  cod_grupo_cia =cCodGrupoCia_in
                  and    cod_local =cCodLocal
                  and    nro_recep=vRecep
                  and    nro_bandeja = aBorraBandejasDevLazer_IN(c);
              END LOOP;                            
              
              update lgt_recep_mercaderia re
              set    re.cant_bandejas = (
                                        select count(1)
                                        from   LGT_RECEP_BANDEJA_RECEP tt
                                        where  tt.cod_grupo_cia = cCodGrupoCia_in
                                        and    tt.cod_local = cCodLocal
                                        and    tt.nro_recep = vRecep
                                        and    tt.cod_grupo_cia = re.cod_grupo_cia
                                        and    tt.cod_local = re.cod_local
                                        and    tt.nro_recep = re.nro_recep
                                        )
              where  re.cod_grupo_cia =  cCodGrupoCia_in
              and    re.cod_local = cCodLocal
              and    re.nro_recep = vRecep;

            Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal,'074',cIdUsu_in);
  else
    if vIndModificar_in = 'S' then
              FOR c IN 1 .. aBandejas_IN.COUNT LOOP

                select count(1)
                into  nNoExiste
                from  lgt_recep_bandeja_recep p
                where p.cod_grupo_cia = cCodGrupoCia_in
                and   p.cod_local = cCodLocal
                and   p.nro_recep = vNumRecep_in
                and   p.nro_bandeja = aBandejas_IN(c);
                
                if nNoExiste = 0 then
                  INSERT INTO LGT_RECEP_BANDEJA_RECEP
                  (cod_grupo_cia, cod_local, nro_recep, nro_bandeja, usu_crea, fec_crea)
                   VALUES(cCodGrupoCia_in,cCodLocal,vNumRecep_in,aBandejas_IN(c),cIdUsu_in,sysdate);
                end if;  
                
              END LOOP;

              FOR c IN 1 .. aBandejasDev_IN.COUNT LOOP
                select count(1)
                into  nNoExiste
                from  lgt_recep_bandeja_devol p
                where p.cod_grupo_cia = cCodGrupoCia_in
                and   p.cod_local = cCodLocal
                and   p.nro_recep = vNumRecep_in
                and   p.nro_bandeja = aBandejasDev_IN(c);

              UPDATE LGT_RECEP_BANDEJA_RECEP T
                  SET  T.ESTADO = 'D'
                  WHERE T.ROWID in (
                      SELECT c.rowid
                      FROM   LGT_RECEP_MERCADERIA RE,
                             LGT_RECEP_BANDEJA_RECEP C
                     WHERE   RE.COD_GRUPO_CIA = cCodGrupoCia_in
                     AND     RE.COD_LOCAL = cCodLocal
                     AND     RE.FEC_RECEP >= trunc(sysdate-60)
                     AND    RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                     AND    RE.COD_LOCAL = C.COD_LOCAL
                     AND    RE.NRO_RECEP = C.NRO_RECEP
                     AND    C.ESTADO in ('P','R')
                     and    C.nro_bandeja = aBandejasDev_IN(c));

                if nNoExiste = 0 then
                
                  INSERT INTO lgt_recep_bandeja_devol
                  (cod_grupo_cia, cod_local, nro_recep, nro_bandeja, usu_crea, fec_crea)
                   VALUES(cCodGrupoCia_in,cCodLocal,vNumRecep_in,aBandejasDev_IN(c),cIdUsu_in,sysdate);
                   
                end if;   
              END LOOP;
              
              FOR c IN 1 .. aBorraBandejas_IN.COUNT LOOP

                select count(1)
                into  nNoExiste
                from  lgt_recep_bandeja_recep_borra p
                where p.cod_grupo_cia = cCodGrupoCia_in
                and   p.cod_local = cCodLocal
                and   p.nro_recep = vNumRecep_in
                and   p.nro_bandeja = aBorraBandejas_IN(c);
                
                if nNoExiste = 0 then
                  INSERT INTO LGT_RECEP_BANDEJA_RECEP_borra
                  (cod_grupo_cia, cod_local, nro_recep, nro_bandeja, usu_crea, fec_crea)
                   VALUES(cCodGrupoCia_in,cCodLocal,vNumRecep_in,aBorraBandejas_IN(c),cIdUsu_in,sysdate);
                end if;  
                
              END LOOP;

              FOR c IN 1 .. aBorraBandejasDev_IN.COUNT LOOP
                select count(1)
                into  nNoExiste
                from  lgt_recep_bandeja_devol_borra p
                where p.cod_grupo_cia = cCodGrupoCia_in
                and   p.cod_local = cCodLocal
                and   p.nro_recep = vNumRecep_in
                and   p.nro_bandeja = aBorraBandejasDev_IN(c);
                
                if nNoExiste = 0 then
                
                  INSERT INTO lgt_recep_bandeja_devol_borra
                  (cod_grupo_cia, cod_local, nro_recep, nro_bandeja, usu_crea, fec_crea)
                   VALUES(cCodGrupoCia_in,cCodLocal,vNumRecep_in,aBorraBandejasDev_IN(c),cIdUsu_in,sysdate);
                   
                end if;   
              END LOOP;    
              

              FOR c IN 1 .. aBorraBandejasLazer_IN.COUNT LOOP
                  update LGT_RECEP_BANDEJA_RECEP t 
                  set   t.ind_lazer = 'S'
                  where  cod_grupo_cia =cCodGrupoCia_in
                  and    cod_local =cCodLocal
                  and    nro_recep=vNumRecep_in
                  and    nro_bandeja = aBorraBandejasLazer_IN(c);
              END LOOP;

              FOR c IN 1 .. aBorraBandejasDevLazer_IN.COUNT LOOP

                  update LGT_RECEP_BANDEJA_DEVOL t 
                  set   t.ind_lazer = 'S'
                  where  cod_grupo_cia =cCodGrupoCia_in
                  and    cod_local =cCodLocal
                  and    nro_recep=vNumRecep_in
                  and    nro_bandeja = aBorraBandejasDevLazer_IN(c);
              END LOOP;                        
              
              update lgt_recep_mercaderia re
              set    re.cant_bandejas = (
                                        select count(1)
                                        from   LGT_RECEP_BANDEJA_RECEP tt
                                        where  tt.cod_grupo_cia = cCodGrupoCia_in
                                        and    tt.cod_local = cCodLocal
                                        and    tt.nro_recep = vNumRecep_in
                                        and    tt.cod_grupo_cia = re.cod_grupo_cia
                                        and    tt.cod_local = re.cod_local
                                        and    tt.nro_recep = re.nro_recep
                                        ),
                     re.cod_valija_dev = cCodValija,
                     re.motivo_no_dev = (case
                                        when (
                                        select count(1)
                                        from   LGT_RECEP_BANDEJA_DEVOL tt
                                        where  tt.cod_grupo_cia = cCodGrupoCia_in
                                        and    tt.cod_local = cCodLocal
                                        and    tt.nro_recep = vNumRecep_in                                        
                                        )   > 0 then ''
                                        else   re.motivo_no_dev
                                        end)             
              where  re.cod_grupo_cia =  cCodGrupoCia_in
              and    re.cod_local = cCodLocal
              and    re.nro_recep = vNumRecep_in;      
      
    end if;
  end if;
  end if;
  RETURN nvl(vRecep,vNumRecep_in);

  EXCEPTION
     WHEN DUP_VAL_ON_INDEX THEN
        RAISE_APPLICATION_ERROR(-20002,'EL NUMERO DE INGRESO YA EXISTE. VERIFIQUE!!!');

  END;

FUNCTION F_VAR_GET_IMP_RECEP(cGrupoCia_in  IN CHAR,
                             cCodLocal_in  IN CHAR,
                             cNroRecep_in IN VARCHAR2) RETURN VARCHAR2
    IS
    vMsg_out VARCHAR2(32767) := '';
    vMsg_1 VARCHAR2(32767) := '';
    vCab_TIPO VARCHAR2(10000) := '';

    vBandejas VARCHAR2(10000) := ''; 
    vBandejasDev VARCHAR2(10000) := '';     
    vBandejasFalt VARCHAR2(10000) := '';

    vTransp VARCHAR(500);
    vTipo CHAR(1);
    vEstado CHAR(1);
    vCantBultos LGT_RECEP_MERCADERIA.CANT_BULTOS%TYPE;
    vCantPrecintos LGT_RECEP_MERCADERIA.CANT_PRECINTOS%TYPE;
    vCantBandejas LGT_RECEP_MERCADERIA.CANT_BANDEJAS%TYPE;
    vCantBandejasRecep LGT_RECEP_MERCADERIA.CANT_BANDEJAS%TYPE;
    vFechaCrea LGT_RECEP_MERCADERIA.FEC_CREA_RECEP%TYPE ;
    vHojaRes LGT_RECEP_MERCADERIA.NRO_HOJA_RESUMEN%TYPE ; 
    vCodValija LGT_RECEP_MERCADERIA.COD_VALIJA_DEV%TYPE ; 

    vLocal VARCHAR2(300) := '';
    vUsu_Crea VARCHAR2(500) := '';
    vSec_Usu_crea lgt_recep_mercaderia.sec_usu_crea%TYPE;
    vLogin VARCHAR2(30) := '';
    vPlaca VARCHAR2(30) := '';
    vGlosa LGT_RECEP_MERCADERIA.GLOSA%TYPE;
    cCodTransp Lgt_Recep_Mercaderia.Cod_Transp%TYPE;
    vNomTransp Lgt_Transp_Ciega.Nom_Transp%TYPE;
    V_MOTIVO_NO_DEV_IN Lgt_Recep_Mercaderia.MOTIVO_NO_DEV%TYPE;
    visDevolver char(1) := 'N';
   BEGIN

   SELECT O.COD_LOCAL||' '||O.DESC_CORTA_LOCAL
     INTO vLocal
     FROM PBL_LOCAL O
    WHERE O.COD_GRUPO_CIA = cGrupoCia_in
      AND O.COD_LOCAL = cCodLocal_in;

   SELECT M.FEC_CREA_RECEP, M.NOMBRE_TRANS, M.CANT_BULTOS, M.CANT_BANDEJAS, M.USU_CREA_RECEP,
          M.SEC_USU_CREA, M.PLACA_UND,M.GLOSA, M.COD_TRANSP, M.Nro_Hoja_Resumen, M.COD_VALIJA_DEV,
          nvl(M.MOTIVO_NO_DEV,'N'),
          CASE
            WHEN M.ESTADO = 'D' THEN 'S'
              ELSE 'N'
          END       
     INTO vFechaCrea, vTransp, vCantBultos, vCantBandejas, vLogin, vSec_Usu_crea, vPlaca,vGlosa, cCodTransp,
                      vHojaRes, 
                      vCodValija,V_MOTIVO_NO_DEV_IN,
                      visDevolver
     FROM LGT_RECEP_MERCADERIA M
    WHERE M.COD_GRUPO_CIA = cGrupoCia_in
      AND M.COD_LOCAL = cCodLocal_in
      AND M.NRO_RECEP = cNroRecep_in;

  BEGIN
    SELECT nvl(M.NAME_EMP_TRANS,' ') INTO vNomTransp
    FROM LGT_RECEP_MERCADERIA     M
    WHERE M.COD_GRUPO_CIA = cGrupoCia_in
      AND M.COD_LOCAL = cCodLocal_in
      AND M.NRO_RECEP = cNroRecep_in;
  EXCEPTION WHEN NO_DATA_FOUND THEN
    vNomTransp:='SIN ASIGNAR';
  END;

   IF trim(vSec_Usu_crea) IS NULL THEN
        vUsu_Crea := vLogin;
   ELSE
     SELECT L.NOM_USU||' '||L.APE_PAT||' '||L.APE_MAT
       INTO vUsu_Crea
       FROM PBL_USU_LOCAL L
      WHERE L.COD_GRUPO_CIA = cGrupoCia_in
        AND L.COD_LOCAL = cCodLocal_in
        AND L.SEC_USU_LOCAL = vSec_Usu_crea;
   END IF;

       
       BEGIN
             FOR cur_recep IN (
                   SELECT distinct(H.Nro_Bandeja)
                   FROM LGT_RECEP_BANDEJA_RECEP H
                   WHERE h.cod_grupo_cia = cGrupoCia_in
                   and   h.cod_local     = cCodLocal_in
                   and   h.nro_recep     = cNroRecep_in)
             LOOP
                   vBandejas := vBandejas || cur_recep.nro_bandeja || ' - ';
             END LOOP;
             vBandejas := SUBSTR(vBandejas, 1, length(vBandejas)-3);
      END;

       BEGIN
             FOR cur_recep IN (
                   SELECT distinct(H.Nro_Bandeja)
                   FROM lgt_recep_bandeja_devol H
                   WHERE h.cod_grupo_cia = cGrupoCia_in
                   and   h.cod_local     = cCodLocal_in
                   and   h.nro_recep     = cNroRecep_in)
             LOOP
                   vBandejasDev := vBandejasDev || cur_recep.nro_bandeja || ' - ';
             END LOOP;
             vBandejasDev := SUBSTR(vBandejasDev, 1, length(vBandejasDev)-3);
      END;   
      
      
      BEGIN
             FOR cur_recep IN (
                   SELECT distinct(HD.NUM_BANDEJA)
                   FROM   LGT_RECEP_MERCADERIA RE,
                          LGT_HOJA_RESUMEN_CAB HC,
                          LGT_HOJA_RESUMEN_DET HD
                   WHERE RE.cod_grupo_cia = cGrupoCia_in
                   and   RE.cod_local     = cCodLocal_in
                   and   RE.NRO_RECEP    = cNroRecep_in
                   AND   RE.COD_GRUPO_CIA = HC.COD_GRUPO_CIA
                   AND    RE.COD_LOCAL = HC.COD_LOCAL
                   AND    RE.NUM_HOJA_RES = HC.NUM_HOJA_RES
                   AND    HC.COD_GRUPO_CIA = HD.COD_GRUPO_CIA
                   AND    HC.NUM_HOJA_RES = HD.NUM_HOJA_RES
                   AND    NOT EXISTS (
                                       SELECT 1
                                       FROM   LGT_RECEP_BANDEJA_RECEP HR
                                       WHERE  HR.COD_GRUPO_CIA = RE.COD_GRUPO_CIA
                                       AND    HR.COD_LOCAL = RE.COD_LOCAL
                                       AND    HR.NRO_RECEP = RE.NRO_RECEP
                                       AND    (HR.NRO_BANDEJA = HD.NUM_BANDEJA or hr.nro_bandeja = hd.nro_bandeja_ext)
                                       )
                   )
             LOOP
                   vBandejasFalt := vBandejasFalt || cur_recep.NUM_BANDEJA || ' - ';
             END LOOP;
             vBandejasFalt := SUBSTR(vBandejasFalt, 1, length(vBandejasFalt)-3);
      END;   

      SELECT COUNT(distinct(H.Nro_Bandeja))
      INTO   vCantBandejasRecep
      FROM lgt_recep_bandeja_recep H
      WHERE h.cod_grupo_cia = cGrupoCia_in
      and   h.cod_local     = cCodLocal_in
      and   h.nro_recep     = cNroRecep_in;
            
      SELECT COUNT(distinct(H.Nro_Bandeja))
      INTO   vCantBandejas
      FROM lgt_recep_bandeja_devol H
      WHERE h.cod_grupo_cia = cGrupoCia_in
      and   h.cod_local     = cCodLocal_in
      and   h.nro_recep     = cNroRecep_in;
       

       vCab_Tipo := '<tr>'||
                    '<td align="center" class="style9" >LOCAL : '||vLocal||''||
                    '</td></tr>';
          vMsg_1 := '<tr><td class="style9">'||
                    'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
                    'Yo, '||vUsu_Crea||', Adm. Local&nbsp <b>CONFIRMO LA RECEPCI&Oacute;N DE LA MERCADER&Iacute;A </b> '||
                    '&nbsp entregada por el Sr. Transportista '||vTransp||' de la '||
                    'empresa de transporte '||vNomTransp||
                    ', en la unidad con Placa: '||vPlaca||'.<br>'||
                    '<br>'||
                    'NRO HOJA DE RESUMEN: '|| vHojaRes ||'<br>'||  
                    --'LA RECEPCION CONSTA DE: '||vCantBandejasRecep||' Bulto/Bandeja(s).<br>'||
                    '<b>RELACION DE BANDEJAS RECEPCIONADAS: </b> <br>'||
                    vCantBandejasRecep||' Bulto/Bandeja(s).<br>'||
                    vBandejas ||'<br>';
                    IF LENGTH(TRIM(                    vBandejasFalt))>0 THEN
                    vMsg_1 := vMsg_1|| '<b>RELACION DE BANDEJAS FALTANTES:  </b> <br>'|| 
                    vBandejasFalt ||'<br>';
                    END IF;
                    vMsg_1 := vMsg_1|| 
                    '<b>RELACION DE BANDEJAS DEVUELTAS:  </b> <br>'|| 
                    vCantBandejas||' Bulto/Bandeja(s).<br>'||
                    vBandejasDev ||'<br>';
                    
                    
                    
                    --'SE ESTA DEVOLVIENDO LA CANT. DE: '||vCantBandejas||' Bandeja(s).<br>'||
                    if Length(trim(vCodValija)) > 0 then
                     vMsg_1 := vMsg_1 ||'SE ESTA DEVOLVIENDO LA VALIJA NRO: '||vCodValija||'<br>';
                    end if;
                    if V_MOTIVO_NO_DEV_IN != 'N' then
                    vMsg_1 := vMsg_1||
                    'El motivo por no haber devoluciones es por : '||V_MOTIVO_NO_DEV_IN||'<br>';
                    end if;
                    
         vMsg_1 := vMsg_1||
                    'Glosa: '||vGlosa||'<br>'||
                    '<br>'||
                    'Firma Transportista:&nbsp; ____________________<br><br>'||
                    'Firma Adm. Local:&nbsp;&nbsp;&nbsp;&nbsp;____________________<br><br>'||
                    '_'||
                    '</td></tr>';
    if visDevolver = 'S' then 
      vMsg_out := '<html>'  ||
                                      '<head>'  ||
                                      '<style type="text/css">'  ||
                                      '.style3 {font-family: Arial, Helvetica, sans-serif}'  ||
                                      '.style8 {font-size: 27; }'  ||
                                      '.style9 {font-size: 25}'  ||
                                      '.style12 {'  ||
                                      'font-family: Arial, Helvetica, sans-serif;'  ||
                                      'font-size: 30;'  ||
                                      'font-weight: bold;'  ||
                                      '}'  ||
                                      '</style>'  ||
                                      '</head>'  ||
                                      '<body>'  ||
                                      '<table width="510"border="0">'  ||
                                      '<tr>'  ||
                                      '<td width="500" align="center" valign="top" class="style8"><b>CONSTANCIA <BR> DEVOLUCION DE BANDEJAS</b></td>'  ||
                                      '</tr>'  ||
                                      '</table>'  ||
                                      '<table width="504" border="0">';
       vMsg_out := vMsg_out|| vCab_Tipo;
       vMsg_out := vMsg_out|| 
        '<tr><td class="style9">'||
          'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
          'Yo, '||vUsu_Crea||', Adm. Local&nbsp <b>CONFIRMO LA DEVOLUCI&Oacute;N DE LAS BANDEJAS </b> '||
          '&nbsp entregada al Sr. Transportista '||vTransp||' de la '||
          'empresa de transporte '||vNomTransp||
          ', en la unidad con Placa: '||vPlaca||'.<br>'||
          '<br>'||
          '<b>RELACION DE BANDEJAS DEVUELTAS:  </b> <br>'|| 
          vCantBandejas||' &nbsp Bulto/Bandeja(s).<br>'||
          vBandejasDev ||'<br>'||
                    'Glosa: '||vGlosa||'<br>'||
                    '<br>'||
                    'Firma Transportista:&nbsp; ____________________<br><br>'||
                    'Firma Adm. Local:&nbsp;&nbsp;&nbsp;&nbsp;____________________<br><br>'||
                    '_'||
                    '</td></tr>'||
              C_FIN_MSG;                                    
    else
    vMsg_out := C_INICIO_MSG || vCab_Tipo || vMsg_1 ||
              C_FIN_MSG;
    end if;          

    RETURN vMsg_out;

    END;
 /* *********************************************************************** */
FUNCTION F_CUR_BAND_RECEP(cCodGrupoCia_in  IN CHAR,
                          cCodLocal_in      IN CHAR,
                          cFechaInicio      IN CHAR,
                          cFechaFin         IN CHAR)
RETURN FarmaCursor
IS
cur FarmaCursor;
BEGIN
OPEN cur FOR
select vv.datos
from  (
  SELECT C.NRO_BANDEJA || 'Ã' || 
         TO_CHAR(RE.FEC_RECEP,'dd/MM/yyyy')|| 'Ã' || 
         CASE
           WHEN C.ESTADO = 'P' THEN 'POR DEVOLVER '
           WHEN C.ESTADO = 'R' THEN 'PENDIENTE DE ENVIAR'
           ELSE 'ERROR'
         END || 'Ã' || 
         NVL(RE.NRO_RECEP,' ')|| 'Ã' || 
         NVL(RE.NUM_HOJA_RES,' ')|| 'Ã' || 
         nvl(c.estado,' ') datos,re.fec_recep FECHA
  FROM   LGT_RECEP_MERCADERIA RE,
         LGT_RECEP_BANDEJA_RECEP C,
         -----------------------------
         LGT_HOJA_RESUMEN_CAB HC,
         LGT_HOJA_RESUMEN_DET HD
 WHERE   RE.COD_GRUPO_CIA = cCodGrupoCia_in
 AND     RE.COD_LOCAL = cCodLocal_in
 AND     RE.FEC_RECEP BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                         AND   TO_DATE(cFechaFin    || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
 AND    RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
 AND    RE.COD_LOCAL = C.COD_LOCAL
 AND    RE.NRO_RECEP = C.NRO_RECEP
 AND    C.ESTADO IN ('P','R')
 AND    RE.COD_GRUPO_CIA = HC.COD_GRUPO_CIA
 AND    RE.NUM_HOJA_RES = HC.NUM_HOJA_RES
 AND    RE.COD_LOCAL = HC.COD_LOCAL
 AND    HC.COD_GRUPO_CIA = HD.COD_GRUPO_CIA
 AND    HC.NUM_HOJA_RES = HD.NUM_HOJA_RES
 AND    HD.TIPO_BULTO IN ('BDP','KNA')
  and    c.nro_bandeja = hd.num_bandeja
  and   not exists (
                     select 1
                    from  lgt_recep_bandeja_devol d
                    where  d.cod_grupo_cia = c.cod_grupo_cia
                    and    d.cod_local = c.cod_local
                    and    d.nro_bandeja = c.nro_bandeja)
                      
 -- order by re.fec_recep desc;
/* union
  SELECT C.NRO_BANDEJA || 'Ã' ||
         TO_CHAR(RE.FEC_RECEP,'dd/MM/yyyy')|| 'Ã' ||
         CASE
           WHEN C.ESTADO = 'P' THEN 'POR DEVOLVER '
           WHEN C.ESTADO = 'R' THEN 'PENDIENTE DE ENVIAR'
           ELSE 'ERROR'
         END || 'Ã' ||
         NVL(RE.NRO_RECEP,' ')|| 'Ã' ||
         NVL(RE.NUM_HOJA_RES,' ')|| 'Ã' ||
         nvl(c.estado,' ') datos,re.fec_recep FECHA
  FROM   LGT_RECEP_MERCADERIA RE,
         LGT_RECEP_BANDEJA_RECEP C,
         -----------------------------
         LGT_HOJA_RESUMEN_CAB HC,
         LGT_HOJA_RESUMEN_DET HD
 WHERE   RE.COD_GRUPO_CIA = cCodGrupoCia_in
 AND     RE.COD_LOCAL = cCodLocal_in
 AND     RE.FEC_RECEP BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                         AND   TO_DATE(cFechaFin    || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
 AND    RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
 AND    RE.COD_LOCAL = C.COD_LOCAL
 AND    RE.NRO_RECEP = C.NRO_RECEP
 AND    C.ESTADO IN ('P','R')
 AND    RE.COD_GRUPO_CIA = HC.COD_GRUPO_CIA
 AND    RE.NUM_HOJA_RES = HC.NUM_HOJA_RES
 AND    RE.COD_LOCAL = HC.COD_LOCAL
 AND    HC.COD_GRUPO_CIA = HD.COD_GRUPO_CIA
 AND    HC.NUM_HOJA_RES = HD.NUM_HOJA_RES
 AND    HD.TIPO_BULTO IN ('BDP','KNA')
 and  not exists (
                 select 1
                 from   LGT_HOJA_RESUMEN_CAB HC,
                        LGT_HOJA_RESUMEN_DET HD
                 where  HC.COD_GRUPO_CIA = RE.COD_GRUPO_CIA
                 AND    HC.NUM_HOJA_RES = RE.NUM_HOJA_RES
                 AND    HC.COD_LOCAL = RE.COD_LOCAL
                 AND    HC.COD_GRUPO_CIA = HD.COD_GRUPO_CIA
                 AND    HC.NUM_HOJA_RES = HD.NUM_HOJA_RES
                 and    hd.num_bandeja = c.nro_bandeja
                 )*/
 )vv
 order by vv.fecha desc;

RETURN cur;
END;
/* ********************************************************************* */
FUNCTION F_CUR_BAND_RECEP_DEFAULT(cCodGrupoCia_in  IN CHAR,
                          cCodLocal_in      IN CHAR)
RETURN FarmaCursor
IS
cur FarmaCursor;
BEGIN
  -- bandejas por devolver --
OPEN cur FOR
select vv.datos
from  (
  SELECT C.NRO_BANDEJA || 'Ã' || 
         TO_CHAR(RE.FEC_RECEP,'dd/MM/yyyy')|| 'Ã' || 
         CASE
           WHEN C.ESTADO = 'P' THEN 'POR DEVOLVER '
           WHEN C.ESTADO = 'R' THEN 'PENDIENTE ENVÍO'
           ELSE 'ERROR'
         END || 'Ã' || 
         NVL(RE.NRO_RECEP,' ')|| 'Ã' || 
         NVL(RE.NUM_HOJA_RES,' ')|| 'Ã' || 
         nvl(c.estado,' ') DATOS,
         re.fec_recep FECHA
  FROM   LGT_RECEP_MERCADERIA RE,
         LGT_RECEP_BANDEJA_RECEP C,
         -----------------------------
         LGT_HOJA_RESUMEN_CAB HC,
         LGT_HOJA_RESUMEN_DET HD
 WHERE   RE.COD_GRUPO_CIA = cCodGrupoCia_in
 AND     RE.COD_LOCAL = cCodLocal_in
 AND     RE.FEC_RECEP >= trunc(sysdate-60)
 AND    RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
 AND    RE.COD_LOCAL = C.COD_LOCAL
 AND    RE.NRO_RECEP = C.NRO_RECEP
 AND    C.ESTADO IN ('P','R')
 AND    RE.COD_GRUPO_CIA = HC.COD_GRUPO_CIA
 AND    RE.NUM_HOJA_RES = HC.NUM_HOJA_RES
 AND    RE.COD_LOCAL = HC.COD_LOCAL
 AND    HC.COD_GRUPO_CIA = HD.COD_GRUPO_CIA
 AND    HC.NUM_HOJA_RES = HD.NUM_HOJA_RES
 AND    HD.TIPO_BULTO IN ('BDP','KNA')
  and    c.nro_bandeja = hd.num_bandeja
  and   not exists (
                     select 1
                    from  lgt_recep_bandeja_devol d
                    where  d.cod_grupo_cia = c.cod_grupo_cia
                    and    d.cod_local = c.cod_local
                    and    d.nro_bandeja = c.nro_bandeja
                    )/*
 -- order by re.fec_recep asc;
 union
  SELECT C.NRO_BANDEJA || 'Ã' || 
         TO_CHAR(RE.FEC_RECEP,'dd/MM/yyyy')|| 'Ã' || 
         CASE
           WHEN C.ESTADO = 'P' THEN 'POR DEVOLVER '
           WHEN C.ESTADO = 'R' THEN 'PENDIENTE ENVÍO'
           ELSE 'ERROR'
         END || 'Ã' || 
         NVL(RE.NRO_RECEP,' ')|| 'Ã' || 
         NVL(RE.NUM_HOJA_RES,' ')|| 'Ã' || 
         nvl(c.estado,' ') DATOS,
         re.fec_recep FECHA
  FROM   LGT_RECEP_MERCADERIA RE,
         LGT_RECEP_BANDEJA_RECEP C
 WHERE   RE.COD_GRUPO_CIA = cCodGrupoCia_in
 AND     RE.COD_LOCAL = cCodLocal_in
 AND     RE.FEC_RECEP >= trunc(sysdate-60)
 AND    RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
 AND    RE.COD_LOCAL = C.COD_LOCAL
 AND    RE.NRO_RECEP = C.NRO_RECEP
 AND    C.ESTADO IN ('P','R')
  and  not exists (
                 select 1
                 from   LGT_HOJA_RESUMEN_CAB HC,
                        LGT_HOJA_RESUMEN_DET HD
                 where  HC.COD_GRUPO_CIA = RE.COD_GRUPO_CIA
                 AND    HC.NUM_HOJA_RES = RE.NUM_HOJA_RES
                 AND    HC.COD_LOCAL = RE.COD_LOCAL
                 AND    HC.COD_GRUPO_CIA = HD.COD_GRUPO_CIA
                 AND    HC.NUM_HOJA_RES = HD.NUM_HOJA_RES
                 and    hd.num_bandeja = c.nro_bandeja
                 )
 and not exists  (
                     select 1
                    from  lgt_recep_bandeja_devol d
                    where  d.cod_grupo_cia = c.cod_grupo_cia
                    and    d.cod_local = c.cod_local
                    and    d.nro_bandeja = c.nro_bandeja
                    )         */        
) vv
order by vv.fecha desc;
      
RETURN cur;
END;
/* ************************************************************************* */
FUNCTION F_CUR_BAND_POR_DEVOL(cCodGrupoCia_in  IN CHAR,
                          cCodLocal_in      IN CHAR)
RETURN FarmaCursor
IS
cur FarmaCursor;
BEGIN
  -- bandejas por devolver --
OPEN cur FOR
select vv.datos
from   (
  SELECT C.NRO_BANDEJA || 'Ã' || 
         TO_CHAR(RE.FEC_RECEP,'dd/MM/yyyy')|| 'Ã' || 
         c.cod_grupo_cia|| 'Ã' || 
          c.cod_local|| 'Ã' || 
          c.nro_recep|| 'Ã' || 
          nvl(re.num_hoja_res,' ') datos,re.fec_recep fecha
  FROM   LGT_RECEP_MERCADERIA RE,
         LGT_RECEP_BANDEJA_RECEP C,
         -----------------------------
         LGT_HOJA_RESUMEN_CAB HC,
         LGT_HOJA_RESUMEN_DET HD
 WHERE   RE.COD_GRUPO_CIA = cCodGrupoCia_in
 AND     RE.COD_LOCAL = cCodLocal_in
 AND     RE.FEC_RECEP >= trunc(sysdate-60)
 AND    RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
 AND    RE.COD_LOCAL = C.COD_LOCAL
 AND    RE.NRO_RECEP = C.NRO_RECEP
 AND    C.ESTADO = 'P'
 AND    RE.COD_GRUPO_CIA = HC.COD_GRUPO_CIA
 AND    RE.NUM_HOJA_RES = HC.NUM_HOJA_RES
 AND    RE.COD_LOCAL = HC.COD_LOCAL
 AND    HC.COD_GRUPO_CIA = HD.COD_GRUPO_CIA
 AND    HC.NUM_HOJA_RES = HD.NUM_HOJA_RES
 AND    HD.TIPO_BULTO IN ('BDP','KNA')
  and    c.nro_bandeja = hd.num_bandeja 
union

  SELECT C.NRO_BANDEJA || 'Ã' || 
         TO_CHAR(RE.FEC_RECEP,'dd/MM/yyyy')|| 'Ã' || 
         c.cod_grupo_cia|| 'Ã' || 
          c.cod_local|| 'Ã' || 
          c.nro_recep|| 'Ã' || 
          nvl(re.num_hoja_res,' ') datos,re.fec_recep fecha
  FROM   LGT_RECEP_MERCADERIA RE,
         LGT_RECEP_BANDEJA_RECEP C
 WHERE   RE.COD_GRUPO_CIA = cCodGrupoCia_in
 AND     RE.COD_LOCAL = cCodLocal_in
 AND     RE.FEC_RECEP >= trunc(sysdate-60)
 AND    RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
 AND    RE.COD_LOCAL = C.COD_LOCAL
 AND    RE.NRO_RECEP = C.NRO_RECEP
 AND    C.ESTADO = 'P'
  and  not exists (
                 select 1
                 from   LGT_HOJA_RESUMEN_CAB HC,
                        LGT_HOJA_RESUMEN_DET HD
                 where  HC.COD_GRUPO_CIA = RE.COD_GRUPO_CIA
                 AND    HC.NUM_HOJA_RES = RE.NUM_HOJA_RES
                 AND    HC.COD_LOCAL = RE.COD_LOCAL
                 AND    HC.COD_GRUPO_CIA = HD.COD_GRUPO_CIA
                 AND    HC.NUM_HOJA_RES = HD.NUM_HOJA_RES
                 and    hd.num_bandeja = c.nro_bandeja
                 )
  ) vv 
  order by vv.fecha desc;
      
RETURN cur;
END;
/* ************************************************************************* */     
procedure P_SAVE_PARA_DEVOLVER(cCodGrupoCia_in IN CHAR,
                               cCodLocal      IN CHAR,
                               cNroBandeja    IN char,
                               cNroHoja       IN char,
                               cNroRecp       IN char
                               )
  IS
  v_nCant  NUMBER;
  nroRecep NUMBER;
  vRecep   VARCHAR2(15);
  v_ip VARCHAR2(20);
   vIndRecepCiega CHAR(1) := 'S';
  BEGIN
   
  update LGT_RECEP_BANDEJA_RECEP t
  set    t.estado = 'P',
         t.fec_mod = sysdate
  where  t.cod_grupo_cia = cCodGrupoCia_in
  and    t.cod_local = cCodLocal
  and    t.nro_recep = cNroRecp
  and    t.nro_bandeja = cNroBandeja;

end;    


procedure P_SAVE_PARA_REVERTIR(cCodGrupoCia_in IN CHAR,
                               cCodLocal      IN CHAR,
                               cNroBandeja    IN char,
                               cNroHoja       IN char,
                               cNroRecp       IN char
                               )
  IS
  v_nCant  NUMBER;
  nroRecep NUMBER;
  vRecep   VARCHAR2(15);
  v_ip VARCHAR2(20);
   vIndRecepCiega CHAR(1) := 'S';
  BEGIN
   
  update LGT_RECEP_BANDEJA_RECEP t
  set    t.estado = 'R',
         t.fec_mod = sysdate
  where  t.cod_grupo_cia = cCodGrupoCia_in
  and    t.cod_local = cCodLocal
  and    t.nro_recep = cNroRecp
  and    t.nro_bandeja = cNroBandeja;

end;    

FUNCTION F_VAR_ACCION_HOJA_EXISTE(cCodGrupoCia_in in varchar2,
                                  cCodLocal_in    in varchar2,
                                  vNroHoja_in     IN varchar2) RETURN VARCHAR2 IS
  cantidad number(5) := 0;
  vMSJ     varchar2(1000) := '';
BEGIN
  select count(*)
    into cantidad
    from LGT_RECEP_MERCADERIA R
   where r.cod_grupo_cia = cCodGrupoCia_in
     and r.cod_local = cCodLocal_in
     and r.num_hoja_res = vNroHoja_in;

  IF cantidad > 0 then
    
  -- los ultimos 15 dias de recepcion
    select (case
           when r.estado = 'T' then '2' -- debe crear nuevo
           else '1' -- podra modificar
           end) || '@'||
           r.nro_recep|| '@'||
           to_char(r.fec_recep,'dd/MM/yyyy HH24:MI:SS')|| '@'||
           nvl(r.cod_transp,' ')|| '@'||
           nvl(r.name_emp_trans,' ')|| '@'||
           nvl(r.nombre_trans,' ')|| '@'||
           nvl(r.glosa,' ')|| '@'||
           nvl(r.cod_valija_dev,' ')|| '@'
      into vMSJ
      from LGT_RECEP_MERCADERIA R
     where r.cod_grupo_cia = cCodGrupoCia_in
       and r.cod_local = cCodLocal_in
       and r.num_hoja_res = vNroHoja_in
       and r.fec_recep >= trunc(sysdate-15);
  else
    vMSJ := 'N';
  END IF;

  RETURN vMSJ;
END;

/* *************************************************************************** */
FUNCTION F_CUR_BAND_RECEP(cCodGrupoCia_in  IN CHAR,
                          cCodLocal_in    IN CHAR,
                          cNumRecep_in    IN CHAR)
RETURN FarmaCursor
IS
cur FarmaCursor;
BEGIN
OPEN cur FOR
  SELECT C.NRO_BANDEJA 
  FROM   LGT_RECEP_MERCADERIA RE,
         LGT_RECEP_BANDEJA_RECEP C
 WHERE   RE.COD_GRUPO_CIA = cCodGrupoCia_in
 AND     RE.COD_LOCAL = cCodLocal_in
 AND     RE.NRO_RECEP = cNumRecep_in
 AND    RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
 AND    RE.COD_LOCAL = C.COD_LOCAL
 AND    RE.NRO_RECEP = C.NRO_RECEP;
      
RETURN cur;
END;  
/* *************************************************************************** */
FUNCTION F_CUR_BAND_DEVOL(cCodGrupoCia_in  IN CHAR,
                          cCodLocal_in    IN CHAR,
                          cNumRecep_in    IN CHAR)
RETURN FarmaCursor
IS
cur FarmaCursor;
BEGIN
OPEN cur FOR
  SELECT C.NRO_BANDEJA 
  FROM   LGT_RECEP_MERCADERIA RE,
         LGT_RECEP_BANDEJA_DEVOL C
 WHERE   RE.COD_GRUPO_CIA = cCodGrupoCia_in
 AND     RE.COD_LOCAL = cCodLocal_in
 AND     RE.NRO_RECEP = cNumRecep_in
 AND    RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
 AND    RE.COD_LOCAL = C.COD_LOCAL
 AND    RE.NRO_RECEP = C.NRO_RECEP;
      
RETURN cur;
END;             

/* *************************************************************************** */

FUNCTION F_VAR_EXISTE_BANDEJA_RES(vCodGrupoCia_in IN varchar2,
                                  vCodLocal_in IN varchar2,
                                  vNroHoja_in IN varchar2,
                                  vNroBandeja_in IN varchar2)
RETURN CHAR
IS
    flag CHAR(1) := 'N';
    nExisteHoja number;
    nExisteBandeja number;
    BEGIN
    
    SELECT count(1)
    into   nExisteHoja
    FROM   LGT_HOJA_RESUMEN_CAB J
    where  j.cod_grupo_cia = vCodGrupoCia_in
    and    j.num_hoja_res = vNroHoja_in
    and    j.cod_local = vCodLocal_in;
    
    if nExisteHoja = 0 then
      flag := 'S';
    else
      
      SELECT count(1)
      into   nExisteBandeja
      FROM   LGT_HOJA_RESUMEN_DET B,
             LGT_HOJA_RESUMEN_CAB J
      where  j.cod_grupo_cia = vCodGrupoCia_in
      and    j.num_hoja_res = vNroHoja_in
      and    j.cod_local = vCodLocal_in
      and    B.NUM_BANDEJA = vNroBandeja_in
      AND    J.COD_GRUPO_CIA = B.COD_GRUPO_CIA
      AND    J.NUM_HOJA_RES = B.NUM_HOJA_RES;
      
      if nExisteBandeja = 0 then
              SELECT count(1)
              into   nExisteBandeja
              FROM   LGT_HOJA_RESUMEN_DET B,
                     LGT_HOJA_RESUMEN_CAB J
              where  j.cod_grupo_cia = vCodGrupoCia_in
              and    j.num_hoja_res = vNroHoja_in
              and    j.cod_local = vCodLocal_in
              and    B.nro_bandeja_ext = vNroBandeja_in
              AND    J.COD_GRUPO_CIA = B.COD_GRUPO_CIA
              AND    J.NUM_HOJA_RES = B.NUM_HOJA_RES;
      end if;
      
      
    end if;

    IF nExisteBandeja > 0 then
       flag := 'S';
    else
       flag := 'N';   
    END IF;

    RETURN flag;
END;
/* *************************************************************** */
FUNCTION F_VAR_IS_VALIDO_HOJA(cCodGrupoCia_in in varchar2,
                              cCodLocal_in    in varchar2,
                              vNroHoja_in     IN varchar2) RETURN VARCHAR2 IS
  nNumHoja_Max number := 0;
  vMSJ     varchar2(1000) := '';
  vValSuma number := 1000;
BEGIN
    /*select NVL(MAX(C.NUM_HOJA_RES),0)
      into nNumHoja_Max
      from LGT_HOJA_RESUMEN_CAB C
     where C.COD_GRUPO_CIA = cCodGrupoCia_in
       and C.COD_LOCAL = cCodLocal_in
       and C.FEC_CREA >= trunc(sysdate-30);
       
      SELECT LLAVE_TAB_GRAL
      INTO   vValSuma
      FROM   PBL_TAB_GRAL T
      WHERE  T.ID_TAB_GRAL = 706;  

  if vNroHoja_in >= nNumHoja_Max and vNroHoja_in <= nNumHoja_Max + vValSuma then
    vMSJ := 'S';
  else
    vMSJ := 'N';    
  end if;*/
  --indicado por Pedro y Autorizado por Rolando 
  --ya no debe hacerse esta validacion debido al desorden que llegan las guias al local
  -- 09.02.2016
  vMSJ := 'S';
  RETURN vMSJ;
END;
/* ************************************************************** */
FUNCTION F_VAR_IS_VALIDO_BANDEJA(cCodGrupoCia_in in varchar2,
                                 cCodLocal_in    in varchar2,
                                 vBandeja_in     IN varchar2) RETURN VARCHAR2 IS
  nExiste number := 0;
  vMSJ     varchar2(2) := '';
BEGIN
    select COUNT(1)
      into nExiste
      from LGT_HOJA_RESUMEN_DET C
     where C.COD_GRUPO_CIA = cCodGrupoCia_in
       and C.NUM_BANDEJA  = vBandeja_in
       and c.fec_crea>= to_date('01/06/2015','dd/mm/yyyy');
  
  if  nExiste = 0 then
 select COUNT(1)
      into nExiste
      from LGT_HOJA_RESUMEN_DET C
     where C.COD_GRUPO_CIA = cCodGrupoCia_in
       and C.Nro_Bandeja_Ext  = vBandeja_in
       and c.fec_crea>= to_date('01/06/2015','dd/mm/yyyy');
       
  end if;     

  if nExiste>0 then
    vMSJ := 'S';
  else
    vMSJ := 'N';    
  end if;

  RETURN vMSJ;
END;
/* ************************************************************** */
procedure P_SAVE_BANDEJA_POR_DEVOLVER(cCodGrupoCia_in IN CHAR,
                                      cCodLocal      IN CHAR,
                                      cNroBandeja    IN char,
                                      cIndExisteBandeja_in in char)
  IS
  v_nCant  NUMBER;
  nroRecep NUMBER := -1;
  vRecep   VARCHAR2(15);  
  v_ip varchar2(20);
  vHojaRes number;
  vFilaBandeja lgt_hoja_resumen_det%rowtype;
  vHojaResumen_in varchar2(20);
  BEGIN
  
  begin  
  select tt.nro_recep
  into   vRecep
  from   LGT_RECEP_MERCADERIA tt
  where  tt.cod_grupo_cia = cCodGrupoCia_in
  and    tt.cod_local = cCodLocal
  and    tt.fec_recep between trunc(sysdate) and trunc(sysdate) +1-1/24/60/60
  and    tt.estado = 'X';
  exception
  when others then
    vRecep := 'X';
  end;
  
  
  if vRecep = 'X' then 
  SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip
    FROM DUAL;

    nroRecep := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal, '074');
     vRecep :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(nroRecep,10,0,'I');

    INSERT INTO LGT_RECEP_MERCADERIA(
             COD_GRUPO_CIA,
             COD_LOCAL,
             NRO_RECEP,
             FEC_RECEP,
             ESTADO,
             USU_CREA_RECEP,
             FEC_CREA_RECEP,
             ind_nvo_func,
             ip_crea_recep

             )
      VALUES (cCodGrupoCia_in,
              cCodLocal,
              vRecep,
              SYSDATE,
              'X',--es para que no liste
              'SYS',
              SYSDATE,
              'S',
              v_ip
              );
  
     Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal,'074','SYS');
    
    end if;
      
    if cIndExisteBandeja_in = 'S' then
      -- la bandeja si existe --
      begin
       select max(de.hojres)
       into   vHojaRes
       from   lgt_hoja_resumen_det de
       where  de.cod_grupo_cia = cCodGrupoCia_in
       and    de.num_bandeja = cNroBandeja;

       select de.*
       into   vFilaBandeja
       from   lgt_hoja_resumen_det de
       where  de.cod_grupo_cia = cCodGrupoCia_in
       and    de.num_hoja_res = lpad(vHojaRes,10,'0')
       and    de.num_bandeja = cNroBandeja;

       
      exception
      when no_data_found then
       select max(de.hojres)
       into   vHojaRes
       from   lgt_hoja_resumen_det de
       where  de.cod_grupo_cia = cCodGrupoCia_in
       and    de.nro_bandeja_ext = cNroBandeja;        

       select de.*
       into   vFilaBandeja
       from   lgt_hoja_resumen_det de
       where  de.cod_grupo_cia = cCodGrupoCia_in
       and    de.num_hoja_res = lpad(vHojaRes,10,'0')
       and    de.nro_bandeja_ext = cNroBandeja;

       
      end;  
       
       
       vHojaResumen_in := vFilaBandeja.Num_Hoja_Res;
       
    end if;
    
    INSERT INTO LGT_RECEP_BANDEJA_RECEP
    (cod_grupo_cia, cod_local, nro_recep, nro_bandeja, usu_crea, fec_crea,estado)
    VALUES(cCodGrupoCia_in,cCodLocal,vRecep,cNroBandeja,'SYS',sysdate,'P');
    
              
      update lgt_recep_mercaderia re
      set    re.cant_bandejas = (
                                select count(1)
                                from   LGT_RECEP_BANDEJA_RECEP tt
                                where  tt.cod_grupo_cia = cCodGrupoCia_in
                                and    tt.cod_local = cCodLocal
                                and    tt.nro_recep = vRecep
                                and    tt.cod_grupo_cia = re.cod_grupo_cia
                                and    tt.cod_local = re.cod_local
                                and    tt.nro_recep = re.nro_recep
                                ),
             re.num_hoja_res =  vHojaResumen_in                  
      where  re.cod_grupo_cia =  cCodGrupoCia_in
      and    re.cod_local = cCodLocal
      and    re.nro_recep = vRecep;    
end;
/* ************************************************************** */
FUNCTION F_VAR_IS_VALIDO_TIPO_DEVOL(cCodGrupoCia_in in varchar2,
                                    cCodLocal_in    in varchar2,
                                    vBandeja_in     IN varchar2) RETURN VARCHAR2 IS
  nExiste number := 0;
  vMSJ     varchar2(2) := '';
BEGIN
    select COUNT(1)
      into nExiste
      from LGT_HOJA_RESUMEN_DET C
     where C.COD_GRUPO_CIA = cCodGrupoCia_in
       and C.NUM_BANDEJA  = vBandeja_in
       and c.fec_crea>= to_date('01/06/2015','dd/mm/yyyy')
       and c.tipo_bulto in ('BDP','KNA');
       
  if nExiste = 0 then
    select COUNT(1)
      into nExiste
      from LGT_HOJA_RESUMEN_DET C
     where C.COD_GRUPO_CIA = cCodGrupoCia_in
       and C.Nro_Bandeja_Ext  = vBandeja_in
       and c.fec_crea>= to_date('01/06/2015','dd/mm/yyyy')
       and c.tipo_bulto in ('BDP','KNA');    
  end if;       
  
  if nExiste>0 then
    vMSJ := 'S';
  else
    vMSJ := 'N';    
  end if;

  RETURN vMSJ;
END;  
end PTOVENTA_RECEP_CIEGA_AS;
/
