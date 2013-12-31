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
  SELECT c.cod_transp || 'Ã' || c.nom_transp
  FROM LGT_TRANSP_CIEGA C
 WHERE ESTADO='A' ORDER BY  C.NOM_TRANSP ASC;
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
                                      nCantPrecintos IN NUMBER,
                                      cGlosa IN VARCHAR2 DEFAULT '',
                                      cSecUsu_in     IN CHAR,
                                      cCodTransp IN CHAR)
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
             CANT_PRECINTOS,
             IND_AFEC_RECEP_CIEGA,
             GLOSA,
             sec_usu_crea ,
             Cod_Transp)
      VALUES (cCodGrupoCia_in,
              cCodLocal,
              vRecep,
              SYSDATE,
              cCantGuias,
              'E',--EMITIDO
              cIdUsu_in,
              SYSDATE,
              v_ip,
              cNombTransp,SYSDATE,cPlaca,nCantBultos,nCantPrecintos,
              vIndRecepCiega, --'S' RECEP_CIEGA, 'N' ANTIGUA
              cGlosa,
              cSecUsu_in,
              cCodTransp);

            Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal,'074',cIdUsu_in);

  RETURN vRecep;

  EXCEPTION
     WHEN DUP_VAL_ON_INDEX THEN
        RAISE_APPLICATION_ERROR(-20002,'EL NUMERO DE INGRESO YA EXISTE. VERIFIQUE!!!');

  END;

/********************************************************************************************************************************/

FUNCTION RECEP_F_VAR2_IMP_VOUCHER(cGrupoCia_in  IN CHAR,
                                              cCodLocal_in  IN CHAR,
                                              cNroRecep_in IN VARCHAR2) RETURN VARCHAR2
    IS
    vMsg_out VARCHAR2(32767) := '';
    vMsg_1 VARCHAR2(32767) := '';
    vCab_TIPO VARCHAR2(10000) := '';


    vTransp VARCHAR(500);
    vTipo CHAR(1);
    vEstado CHAR(1);
    vCantBultos LGT_RECEP_MERCADERIA.CANT_BULTOS%TYPE;
    vCantPrecintos LGT_RECEP_MERCADERIA.CANT_PRECINTOS%TYPE;
    vFechaCrea LGT_RECEP_MERCADERIA.FEC_CREA_RECEP%TYPE ;

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

   SELECT M.FEC_CREA_RECEP, M.NOMBRE_TRANS, M.CANT_BULTOS, M.CANT_PRECINTOS, M.USU_CREA_RECEP,
          M.SEC_USU_CREA, M.PLACA_UND,M.GLOSA, M.COD_TRANSP
     INTO vFechaCrea, vTransp, vCantBultos, vCantPrecintos, vLogin, vSec_Usu_crea, vPlaca,vGlosa, cCodTransp
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

       vCab_Tipo := '<tr>'||
                    '<td align="center"><h2>'||vLocal||'</h2>'||
                    '</td></tr>';
          vMsg_1 := '<tr><td class="style9">'||
                    'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
                    'Yo, '||vUsu_Crea||', Adm. Local&nbsp <b>CONFIRMO LA RECEPCI&Oacute;N DE LA MERCADER&Iacute;A </b> '||
                    'entregada por el Sr. Transportista '||vTransp||' de la '||
                    'empresa de transporte '||vNomTransp||
                    ', en la unidad con Placa: '||vPlaca||'.<br>'||
                    'La recepci&oacute;n consta de: '||vCantBultos||' Bulto(s)'||
                    ' y '||vCantPrecintos||' Precinto(s).<br>'||
                    'Glosa: '||vGlosa||'<br>'||
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

end PTOVENTA_RECEP_CIEGA_AS;

/
