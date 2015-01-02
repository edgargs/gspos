--------------------------------------------------------
--  DDL for Package Body PTOVENTA_RECEP_CIEGA_AS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_RECEP_CIEGA_AS" is

/********************************************************************************************************************************/

FUNCTION RECEP_F_LISTA_TRANSP
RETURN FarmaCursor
IS
cur FarmaCursor;
BEGIN
OPEN cur FOR
  SELECT c.cod_transp || '�' || c.nom_transp
  FROM LGT_TRANSP_CIEGA C
 WHERE ESTADO='A' 
 AND TIPO_TRANSP = TIPO_PROVEEDOR  --ASOSA - 25/07/2014
 ORDER BY  C.NOM_TRANSP ASC;
RETURN cur;
END;

/********************************************************************************************************************************/
-- AAMPUERO 15.04.2014 nCantPrecintos IN NUMBER,
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
                                      aBandejas IN VARCHAR2_TABLE --ASOSA - 25/07/2014                                      
                                      )
                                      RETURN VARCHAR2
  IS
  v_nCant	NUMBER;
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
              FOR c IN 1 .. aBandejas.COUNT LOOP
              
                  INSERT INTO LGT_BANDEJA_HOJA_RES(NRO_HOJA_RES,
                                                                                NRO_BANDEJA)
                   VALUES(cNroHojaRes, aBandejas(c));
              
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
   BEGIN

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
        SELECT NUM_HOJA || '�' || CANT_BULTO
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

FUNCTION RECEP_F_EXISTS_HOJA_RES(vNroHoja_in IN varchar2)
RETURN CHAR
IS
    cantidad number(5) :=  0;
    flag CHAR(1) := 'N';
    BEGIN
             select count(*)
             into cantidad
             from LGT_RECEP_MERCADERIA a
             where a.nro_hoja_resumen is not null
            and upper(a.nro_hoja_resumen) = upper(trim(vNroHoja_in));
    
    IF cantidad > 0 then
                flag := 'S';
    END IF;
    
    RETURN flag;
END;

/********************************************************************************************************************************/

end PTOVENTA_RECEP_CIEGA_AS;

/
