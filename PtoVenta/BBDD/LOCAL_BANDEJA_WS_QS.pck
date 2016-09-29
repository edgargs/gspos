CREATE OR REPLACE PACKAGE LOCAL_BANDEJA_WS_QS AS
TYPE FarmaCursor IS REF CURSOR;

/*
AUTHOR:          ASOSA
DESCRIPTION:     METODO PARA LISTAR LAS BANDEJAS QUE SE VAN A DEVOLVER A QS
DATE:            29/05/2015
*/
  FUNCTION FN_LIST_BANDEJA_DEVOL
    RETURN FarmaCursor;

/*
AUTHOR:          ASOSA
DESCRIPTION:     METODO PARA ACTUALIZAR LAS BANDEJAS DEVUELTAS PARA QUE NO SE VUELVAN A DEVOLVER
DATE:            29/05/2015
*/
 PROCEDURE SP_UPD_BANDEJA_DEVOL(cCodGrupoCia_in in char,
                                  cCodLocal_in in char,
                                  cNroRecep_in in char,
                                  cRetorno_out in out char
                                  );

/*
AUTHOR:          ASOSA
DESCRIPTION:     METODO PARA LISTAR LAS BANDEJAS QUE SE HAN RECEPCIONADO DE QS
DATE:            29/05/2015
*/
  FUNCTION FN_LIST_BANDEJA_RECEP
    RETURN FarmaCursor;

/*
AUTHOR:          ASOSA
DESCRIPTION:     METODO PARA ACTUALIZAR LAS BANDEJAS RECEPCIONADAS PARA QUE NO SE VUELVAN A RECIBIR
DATE:            29/05/2015
*/
 PROCEDURE SP_UPD_BANDEJA_RECEP(cCodGrupoCia_in in char,
                                  cCodLocal_in in char,
                                  cNroRecep_in in char,
                                  cRetorno_out in out char
                                  );

END;
/
CREATE OR REPLACE PACKAGE BODY LOCAL_BANDEJA_WS_QS AS

/**************************************************************************************************/

   FUNCTION FN_LIST_BANDEJA_DEVOL
    RETURN FarmaCursor
    IS
           curProd FarmaCursor;
           cCodGrupoCia_in char(3) := '';
           cCodLocal_in char(3) := '';
    BEGIN

       SELECT DISTINCT I.COD_GRUPO_CIA, I.COD_LOCAL
       INTO cCodGrupoCia_in, cCodLocal_in
       FROM VTA_IMPR_LOCAL I;

       OPEN curProd FOR
       SELECT A.COD_GRUPO_CIA|| 'Ã' ||
              A.COD_LOCAL|| 'Ã' ||
              A.NRO_RECEP|| 'Ã' ||
              A.NRO_BANDEJA || 'Ã' || --AS NUMBULTO,
              B.NUM_HOJA_RES || 'Ã' ||
              ' ' || 'Ã' ||
              ' '|| 'Ã' ||
              ' '|| 'Ã' ||
              ' '|| 'Ã' ||
              to_char(A.FEC_CREA,'YYYY-MM-DD HH24:MI:SS')-- AS FECTRANS
       FROM LGT_RECEP_BANDEJA_DEVOL A,
            LGT_RECEP_MERCADERIA B
       WHERE A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
       AND A.COD_LOCAL = B.COD_LOCAL
       AND A.NRO_RECEP = B.NRO_RECEP
       AND b.COD_GRUPO_CIA = cCodGrupoCia_in
       AND b.COD_LOCAL = cCodLocal_in
       AND A.FEC_ENVIA_WS IS NULL
       AND B.FEC_CREA_RECEP >= TRUNC(SYSDATE-60);

       RETURN curProd;
    END;

    /**************************************************************************************************/

   PROCEDURE SP_UPD_BANDEJA_DEVOL(cCodGrupoCia_in in char,
                                  cCodLocal_in in char,
                                  cNroRecep_in in char,
                                  cRetorno_out in out char
                                  )
    AS
    BEGIN
        UPDATE LGT_RECEP_BANDEJA_DEVOL A
        SET a.fec_envia_ws = sysdate
        where a.cod_grupo_cia = cCodGrupoCia_in
        AND A.COD_LOCAL = cCodLocal_in
        AND A.NRO_RECEP = cNroRecep_in;

        cRetorno_out := 'S';
    EXCEPTION
        WHEN OTHERS THEN
          cRetorno_out := 'N' || SQLERRM;
    END;

   /**************************************************************************************************/

      FUNCTION FN_LIST_BANDEJA_RECEP
    RETURN FarmaCursor
    IS
           curProd FarmaCursor;
           cCodGrupoCia_in char(3) := '';
           cCodLocal_in char(3) := '';
    BEGIN

       SELECT DISTINCT I.COD_GRUPO_CIA, I.COD_LOCAL
       INTO cCodGrupoCia_in, cCodLocal_in
       FROM VTA_IMPR_LOCAL I;

       OPEN curProd FOR
       SELECT A.COD_GRUPO_CIA|| 'Ã' ||
              A.COD_LOCAL|| 'Ã' ||
              A.NRO_RECEP|| 'Ã' ||
              A.NRO_BANDEJA || 'Ã' ||--AS NUMBULTO,
              B.NUM_HOJA_RES || 'Ã' ||--AS HOJRES,
              D.KUNNR|| 'Ã' ||
              D.NAMEDC|| 'Ã' ||
              D.LIFNR|| 'Ã' ||
              D.NAMET|| 'Ã' ||
              to_char(A.FEC_CREA,'YYYY-MM-DD HH24:MI:SS') --AS FECTRANS
       FROM LGT_RECEP_BANDEJA_RECEP A,
            LGT_RECEP_MERCADERIA B,
            LGT_HOJA_RESUMEN_DET D,
            LGT_HOJA_RESUMEN_CAB E
       WHERE A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
       AND A.COD_LOCAL = B.COD_LOCAL
       AND A.NRO_RECEP = B.NRO_RECEP
       AND B.COD_GRUPO_CIA = E.COD_GRUPO_CIA
       AND B.COD_LOCAL = E.COD_LOCAL
       AND B.NUM_HOJA_RES = E.NUM_HOJA_RES
       AND E.COD_GRUPO_CIA = D.COD_GRUPO_CIA
       AND E.NUM_HOJA_RES = D.NUM_HOJA_RES
       AND (A.NRO_BANDEJA = D.NUM_BANDEJA or  A.NRO_BANDEJA = D.Nro_Bandeja_Ext)
       AND A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.FEC_ENVIA_WS IS NULL
       AND B.FEC_CREA_RECEP >= TRUNC(SYSDATE-60);       
       /*SELECT A.COD_GRUPO_CIA|| 'Ã' ||
              A.COD_LOCAL|| 'Ã' ||
              A.NRO_RECEP|| 'Ã' ||
              A.NRO_BANDEJA || 'Ã' ||--AS NUMBULTO,
              B.NUM_HOJA_RES || 'Ã' ||--AS HOJRES,
              D.KUNNR|| 'Ã' ||
              D.NAMEDC|| 'Ã' ||
              D.LIFNR|| 'Ã' ||
              D.NAMET|| 'Ã' ||
              to_char(A.FEC_CREA,'YYYY-MM-DD HH24:MI:SS') --AS FECTRANS
       FROM LGT_RECEP_BANDEJA_RECEP A,
            LGT_RECEP_MERCADERIA B,
            LGT_HOJA_RESUMEN_DET D,
            LGT_HOJA_RESUMEN_CAB E
       WHERE A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
       AND A.COD_LOCAL = B.COD_LOCAL
       AND A.NRO_RECEP = B.NRO_RECEP
       AND B.COD_GRUPO_CIA = E.COD_GRUPO_CIA
       AND B.COD_LOCAL = E.COD_LOCAL
       AND B.NUM_HOJA_RES = E.NUM_HOJA_RES
       AND E.COD_GRUPO_CIA = D.COD_GRUPO_CIA
       AND E.NUM_HOJA_RES = D.NUM_HOJA_RES
       AND A.NRO_BANDEJA = D.NUM_BANDEJA
       AND A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.FEC_ENVIA_WS IS NULL
       AND B.FEC_CREA_RECEP >= TRUNC(SYSDATE-60);*/

       RETURN curProd;
    END;

    /**************************************************************************************************/

   PROCEDURE SP_UPD_BANDEJA_RECEP(cCodGrupoCia_in in char,
                                  cCodLocal_in in char,
                                  cNroRecep_in in char,
                                  cRetorno_out in out char
                                  )
    AS
    BEGIN
        UPDATE LGT_RECEP_BANDEJA_RECEP A
        SET a.fec_envia_ws = sysdate
        where a.cod_grupo_cia = cCodGrupoCia_in
        AND A.COD_LOCAL = cCodLocal_in
        AND A.NRO_RECEP = cNroRecep_in;

        cRetorno_out := 'S';
    EXCEPTION
        WHEN OTHERS THEN
          cRetorno_out := 'N' || SQLERRM;
    END;


       /**************************************************************************************************/

END;
/
