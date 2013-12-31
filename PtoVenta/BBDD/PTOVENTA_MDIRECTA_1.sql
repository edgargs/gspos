--------------------------------------------------------
--  DDL for Package Body PTOVENTA_MDIRECTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_MDIRECTA" AS

--LISTA ORDENES DE COMPRA NO TERMINADAS.
  FUNCTION LISTA_ORDEN_COMPRA_CAB(cCodGrupoCia_in IN CHAR,
                                  cCodCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFechaIni_in IN VARCHAR2,vFechaFin_in IN VARCHAR2)
    RETURN FarmaCursor
    IS
        cursListado FarmaCursor;
    BEGIN
        OPEN cursListado FOR
           SELECT  (CAB.COD_OC || 'Ã' ||
                    cab.COD_LOCAL || 'Ã' ||
                    prov.ruc_prov || 'Ã' ||
                    prov.nom_prov || 'Ã' ||
                    CAB.CANT_ITEMS  || 'Ã' ||
                    TO_CHAR(cab.VAL_TOTAL_OC_CAB, '999,999.000') || 'Ã' ||
                    TO_CHAR(CAB.FEC_INI, 'dd/MM/yyyy') || 'Ã' ||
                    CAB.FEC_PROG_ENTREGA  || 'Ã' ||
                    CAB.COD_PROV || 'Ã' ||
                    CAB.COD_FORMA_PAGO || 'Ã' ||
                    CAB.DESC_FORMA_PAGO || 'Ã' ||
                    PROV.RUC_PROV || 'Ã' ||
                    CAB.PROC_OC_CAB) AS RESULTADO
           FROM LGT_OC_CAB CAB
           INNER JOIN LGT_PROV PROV ON (CAB.cod_prov = PROV.cod_prov)
           WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
           AND CAB.COD_CIA   = cCodCia_in
           AND CAB.COD_LOCAL = cCodLocal_in
           AND CAB.FEC_INI  BETWEEN TO_DATE(vFechaIni_in, 'DD/MM/YYYY') AND  TO_DATE(vFechaFin_in, 'DD/MM/YYYY');
        RETURN cursListado;
    END;

    FUNCTION LISTA_CAB_ORDEN_COMPRA(vCod_OC_in IN VARCHAR2)
      RETURN FarmaCursor
      IS
        cursListado FarmaCursor;
      BEGIN
        OPEN cursListado FOR
          SELECT (cab.num_doc_cab || 'Ã' ||
                  cab.ser_doc_cab || 'Ã' ||
                  CAB.FEC_RECEPCION || 'Ã' ||
                  comp.desc_comp) AS RESULTADO
          FROM LGT_OC_CAB CAB JOIN VTA_TIP_COMP COMP ON (cab.tip_comp_cab = comp.tip_comp)
          WHERE CAB.COD_OC = vCod_OC_in;
        RETURN cursListado;
    END;
    -- LISTAR_PRODUCTOS_ORDEN_COMPRA
    FUNCTION DETALLE_ORDEN_COMPRA(vCodOrdenComp_in IN VARCHAR2)
      RETURN FarmaCursor
      IS
        cursListado FarmaCursor;
      BEGIN
        OPEN cursListado FOR
          SELECT  (' ' || 'Ã' ||
                   DET.COD_PROD || 'Ã' ||
                   PROD.desc_prod||' - '||prod.desc_unid_present || 'Ã' ||
                   det.cant_ped || 'Ã' ||
                   det.cant_recep || 'Ã' ||
                   TO_CHAR(det.prec_prod, '999,990.00') || 'Ã' ||
                   det.DET_IGV_PROD || 'Ã' ||
                   prod.cant_unid_present ) AS RESULTADO
          FROM LGT_OC_DET DET JOIN LGT_PROD PROD ON (DET.COD_PROD = PROD.COD_PROD)
          WHERE DET.COD_OC = vCodOrdenComp_in;
        RETURN cursListado;
      END;
    -- LISTAR_PRODUCTOS_ORDEN_COMPRA
    FUNCTION DETALLE_ORDEN_COMPRA_RECEP(vOrdenComp_in IN VARCHAR2)
      RETURN FarmaCursor
      IS
        cursListado FarmaCursor;
      BEGIN
        OPEN cursListado FOR
          SELECT  (DET.COD_PROD || 'Ã' ||
                   PROD.desc_prod||' - '||prod.desc_unid_present || 'Ã' ||
                   det.cant_ped || 'Ã' ||
                   det.cant_recep || 'Ã' ||
                   TO_CHAR(det.prec_prod, '999,990.00') || 'Ã' ||
                   det.DET_IGV_PROD || 'Ã' ||
                   prod.cant_unid_present) AS RESULTADO
          FROM LGT_OC_DET DET JOIN LGT_PROD PROD ON (DET.COD_PROD = PROD.COD_PROD)
          WHERE DET.COD_OC = vOrdenComp_in;
        RETURN cursListado;
      END;
    -- LISTAR PRODUCTOS DE ORDEN DE COMPRA
    FUNCTION LISTAR_DETALLE_ORDEN_COMPRA(vCodProducto_in IN VARCHAR2)
      RETURN FarmaCursor
      IS
          cursListado FarmaCursor;
      BEGIN
        OPEN cursListado FOR
            SELECT (DET.COD_PROD || 'Ã' ||
                    PROD.desc_prod||' - '||prod.desc_unid_present || 'Ã' ||
                    det.cant_ped || 'Ã' ||
                    det.cant_recep || 'Ã' ||
                    TO_CHAR(det.prec_prod, '999,990.00') || 'Ã' ||
                    det.DET_IGV_PROD || 'Ã' ||
                    prod.cant_unid_present) AS RESULTADO
            FROM LGT_OC_DET DET JOIN LGT_PROD PROD ON (DET.COD_PROD = PROD.COD_PROD)
            WHERE DET.COD_OC = vCodProducto_in;
          RETURN cursListado;
        END;

    FUNCTION DETALLE_ORDEN_COMPRA_RECEP(vOrnComp_in IN VARCHAR2)
      RETURN FarmaCursor
      IS
        cursListado FarmaCursor;
      BEGIN
        OPEN cursListado FOR
          SELECT  (DET.COD_PROD || 'Ã' ||
                   PROD.desc_prod||' - '||prod.desc_unid_present || 'Ã' ||
                   det.cant_ped || 'Ã' ||
                   det.cant_recep || 'Ã' ||
                   TRIM(TO_CHAR(det.prec_prod, '999,990.00')) || 'Ã' ||
                   det.DET_IGV_PROD || 'Ã' ||
                   prod.cant_unid_present) AS RESULTADO
          FROM LGT_OC_DET DET JOIN LGT_PROD PROD ON (DET.COD_PROD = PROD.COD_PROD)
          WHERE DET.COD_OC = vOrnComp_in;
        RETURN cursListado;
      END;

  FUNCTION LISTA_CAB_ORDEN_COMPRA_RECEP(cCodGrupoCia_in IN CHAR,
                                cCodCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
								cCodOC_in IN CHAR,
								nSecCab_in IN NUMBER)
    RETURN FarmaCursor
    IS
      cursListado FarmaCursor;
    BEGIN
      OPEN cursListado FOR
        SELECT (trim(CABRE.FEC_CREA) || 'Ã' ||
                trim(PROV.COD_PROV) || 'Ã' ||
                trim(prov.nom_prov) || 'Ã' ||
                trim(tip.desc_comp) || 'Ã' ||
                trim(CABRE.TIP_COMP_CAB) || 'Ã' ||
                trim(CABRE.SER_DOC_CAB) || 'Ã' ||
                trim(CABRE.NUM_DOC_CAB) || 'Ã' ||
                trim(CAB.COD_FORMA_PAGO) || 'Ã' ||
                trim(CAB.DESC_FORMA_PAGO) || 'Ã' ||
                trim(to_char(nvl(cabre.val_par_total_oc_cab,0), '999,999.999')) || 'Ã' ||
                trim(to_char(nvl(cabre.redon_oc_cab,0), 'FM990.099')) || 'Ã' ||
                TRIM(NGUIA.est_nota_es_cab) || 'Ã' ||
                TRIM(CABRE.COD_CIA)) AS RESULTADO
        FROM LGT_OC_CAB_RECEP CABRE
        INNER JOIN LGT_PROV PROV ON(CABRE.COD_PROV = PROV.COD_PROV)
        INNER JOIN LGT_OC_CAB CAB ON(CABRE.COD_OC = CAB.COD_OC)
        INNER JOIN VTA_TIP_COMP TIP ON(CABRE.TIP_COMP_CAB = TIP.TIP_COMP)
        INNER JOIN LGT_NOTA_ES_CAB NGUIA ON(CABRE.NUM_DOC_CAB = NGUIA.NUM_DOC)
        WHERE CABRE.COD_GRUPO_CIA = cCodGrupoCia_in
		AND CABRE.COD_CIA = cCodCia_in
		AND CABRE.COD_LOCAL = cCodLocal_in
		AND CABRE.COD_OC = cCodOC_in  -- Visualiza los anulados AND CABRE.EST_OC_CAB = 'A'
		AND CABRE.SEC_CAB_ORD_COMP = nSecCab_in
        order by CABRE.fec_crea ASC;
      RETURN cursListado;
    END;

    --INSERTA LGT_OC_CAB_RECEP;
  FUNCTION MDIR_GRABA_OC_CAB_RECEP(cCodGrupoCia_in   IN CHAR,
            cCod_Cia_in    IN CHAR, cCod_Local_in       IN CHAR,
            vId_User_in         IN VARCHAR2, cNumer_Ord_Comp_in  IN CHAR,
            cfecha_in           IN DATE, cId_Docum_in        IN CHAR,
            cSerie_Docm_in      IN CHAR, cNumer_Docm_in      IN CHAR,
            nCant_Item_in       IN CHAR, cCod_Prov_in        IN CHAR,
            nImport_Total_in    IN CHAR, nImport_Parc_in     IN CHAR,
            nRedondeo_in        IN CHAR)
  RETURN CHAR
      IS
        bEstado    CHAR(7);
        nDocumento lgt_oc_cab_recep.CANT_ITEMS%TYPE;
        nSecOrdComp  LGT_OC_CAB_RECEP.SEC_CAB_ORD_COMP%TYPE;
        BEGIN
            bEstado := 'FALSE';
            SELECT (NVL(COUNT(*), 0)+1)
            INTO nDocumento
            FROM lgt_oc_cab_recep
            where cod_oc       = cNumer_Ord_Comp_in
            and   cod_prov     = cCod_Prov_in
            and   tip_comp_cab = cId_Docum_in
            and   ser_doc_cab  = cSerie_Docm_in
            and   num_doc_cab  = cNumer_Docm_in
            and   cod_local    = cCod_Local_in;
           
           --GFONSECA 16.12.2013 AGREGAR "SEC_CAB_ORD_COMP" A LA CABECERA                        
           SELECT ( NVL(MAX(SEC_CAB_ORD_COMP),0) + 1 ) 
           into nSecOrdComp
           FROM  LGT_OC_CAB_RECEP CABR
           WHERE CABR.COD_GRUPO_CIA = cCodGrupoCia_in
           AND CABR.COD_OC = cNumer_Ord_Comp_in
           AND CABR.COD_CIA = cCod_Cia_in
           AND CABR.COD_LOCAL = cCod_Local_in
           ; 
            
            IF nDocumento = 1 THEN
              INSERT INTO LGT_OC_CAB_RECEP(COD_GRUPO_CIA, COD_LOCAL, COD_OC, TIP_COMP_CAB, SER_DOC_CAB, NUM_DOC_CAB, EST_OC_CAB,
                                          COD_CIA,
                                          CANT_ITEMS, VAL_TOTAL_OC_CAB,
                                          USU_CREA, FEC_CREA, USU_MOD, FEC_MOD,
                                          VAL_PAR_TOTAL_OC_CAB, REDON_OC_CAB,
                                          COD_PROV, fec_ingreso, PROC_OC_CAB, SEC_CAB_ORD_COMP)
              VALUES(
                    cCodGrupoCia_in,cCod_Local_in, cNumer_Ord_Comp_in,cId_Docum_in,cSerie_Docm_in, cNumer_Docm_in, 'A',
                    cCod_Cia_in,
                    TRIM(TO_NUMBER(nCant_Item_in, '99999')),
                    TRIM(TO_NUMBER(nImport_Total_in, '999,999.999')),
                    UPPER(vId_User_in),
                    TRIM(TO_DATE(cfecha_in, 'dd/MM/yyyy')),
                    NULL,
                    NULL,
                    TRIM(TO_NUMBER(nImport_Parc_in, '999,999.999')),
                    TRIM(TO_NUMBER(nRedondeo_in, 'FM999.990')),
                    TRIM(TO_CHAR(cCod_Prov_in, '999999')),
                    SYSDATE,
                    'P',
                    nSecOrdComp);
              UPDATE LGT_OC_CAB
              SET FEC_RECEPCION  = TRUNC(SYSDATE),
                  USU_MOD = vId_User_in,
                  FEC_MOD = SYSDATE,
                  TIP_COMP_CAB   = TRIM(TO_CHAR(cId_Docum_in, '09')),
                  NUM_DOC_CAB    = cNumer_Docm_in,
                  SER_DOC_CAB    = cSerie_Docm_in,
                  VAL_PAR_TOTAL_OC_CAB = TRIM(TO_NUMBER(nImport_Parc_in, '999,999.999'))
                  --,VAL_DIF_TOTAL  = TRIM(TO_NUMBER(nDif_Monto_in, '999.999'))
              WHERE COD_OC  = cNumer_Ord_Comp_in
              AND COD_PROV  = cCod_Prov_in
              AND COD_LOCAL = cCod_Local_in
              AND COD_CIA   = cCod_Cia_in;

              bEstado := 'TRUE';

          ELSE
            bEstado := 'FALSE';

          END IF;

        RETURN bEstado;
    --EXCEPTION
    --WHEN OTHERS THEN

         --DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
         --RETURN bEstado;
    END;

  /*** ***/
  PROCEDURE MDIR_GRABA_OC_DET_RECEP(cCodGrupoCia_in       IN CHAR,
                                cCod_Cia_in              IN CHAR,
                                cCod_Local_in            IN CHAR,
                                vId_User_in              IN VARCHAR2,
                                cNumer_Ord_Comp_in       IN CHAR,
                                cCod_Prod_in             IN CHAR,
                                nCant_Solict_in          IN CHAR,
                                nCant_Recep_in           IN CHAR,
                                nPrecio_Unit_in          IN CHAR,
                                nIGV_in                  IN CHAR,
                                nCant_Recep_Total_in     IN CHAR,
                                nSec_Det_in              IN CHAR,
                                cId_Docum_in              IN CHAR,
                                cSerie_Docm_in            IN CHAR,
                                cNumer_Docm_in            IN CHAR)
    IS
     nSecDetalle LGT_OC_DET_RECEP.SEC_DET_ORD_COMP%TYPE;
     nSecCabComp LGT_OC_CAB_RECEP.SEC_CAB_ORD_COMP%type;
    BEGIN
        SELECT (NVL(MAX(SEC_DET_ORD_COMP),0)+1)
        INTO   nSecDetalle
        FROM   LGT_OC_DET_RECEP
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_CIA       = cCod_Cia_in
        AND COD_LOCAL     = cCod_Local_in
        AND COD_OC        = cNumer_Ord_Comp_in;
        
        --GFONSECA 16.12.2013 AGREGAR "SEC_CAB_ORD_COMP" AL DETALLE
        SELECT SEC_CAB_ORD_COMP
        INTO   nSecCabComp
        FROM   LGT_OC_CAB_RECEP
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_CIA       = cCod_Cia_in
        AND COD_LOCAL     = cCod_Local_in
        AND COD_OC        = cNumer_Ord_Comp_in         
        AND NUM_DOC_CAB = cNumer_Docm_in; 
        

        INSERT INTO LGT_OC_DET_RECEP(COD_GRUPO_CIA, COD_LOCAL, COD_OC, 
                                     COD_CIA,
                                     SEC_DET_NOTA_ES, SEC_DET_ORD_COMP, COD_PROD,
                                     PORC_IGV, VAL_TOTAL_OC_DET_RECP,
                                     USU_CREA_OC_DET_RECP,FEC_CREA_OC_DET_RECP,
                                     CANT_RECEP, CANT_PED, SEC_CAB_ORD_COMP )

        VALUES (cCodGrupoCia_in,cCod_Local_in,cNumer_Ord_Comp_in,
                cCod_Cia_in,
                TRIM(TO_NUMBER(nSec_Det_in, '9999')),nSecDetalle,cCod_Prod_in,
              TRIM(TO_NUMBER(nIGV_in, '999.999')), TRIM(TO_NUMBER(nCant_Recep_Total_in, '999,999.999')),
              vId_User_in,SYSDATE,
              TRIM(TO_NUMBER(nCant_Recep_in, '999,999.999')),TRIM(TO_NUMBER(nCant_Solict_in, '999,999.999')),
              nSecCabComp);
           
        UPDATE LGT_OC_DET
        SET CANT_RECEP = (SELECT SUM(RECEP.CANT_RECEP)
                          FROM LGT_OC_DET_RECEP RECEP
                          WHERE RECEP.COD_OC = cNumer_Ord_Comp_in
                          AND RECEP.COD_PROD = cCod_Prod_in
                          )
        WHERE COD_OC = cNumer_Ord_Comp_in
        AND COD_PROD = cCod_Prod_in;

        /*IF CANT_RECEP = CANT_PED THEN
          UPDATE LGT_
        END IF;*/

  END MDIR_GRABA_OC_DET_RECEP;

  FUNCTION LISTAR_DET_ORDEN_COMPRA_RECEP (cCodGrupoCia_in IN CHAR,
										  cCodCia_in   IN CHAR,
										  cCodLocal_in   IN CHAR,
										  cCod_OC_in   IN CHAR,
                                          nSecCab_in IN NUMBER)
  RETURN FarmaCursor
      IS
        cursListado FarmaCursor;
      BEGIN
        OPEN cursListado FOR
            SELECT (DET.COD_PROD || 'Ã' ||
                    PROD.desc_prod||' - '||prod.desc_unid_present || 'Ã' ||
                    det.cant_ped || 'Ã' ||
                    det.cant_recep || 'Ã' ||
                    TRIM(TO_CHAR(det.val_total_oc_det_recp/det.cant_recep, '999,999.999') )|| 'Ã' ||
                    DET.PORC_IGV || 'Ã' ||
                    prod.cant_unid_present) AS RESULTADO
            FROM LGT_OC_DET_RECEP DET JOIN LGT_PROD PROD ON (DET.COD_PROD = PROD.COD_PROD)
            WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
			AND DET.COD_CIA = cCodCia_in
			AND DET.COD_LOCAL = cCodLocal_in
			AND DET.COD_OC = cCod_OC_in
            AND DET.SEC_CAB_ORD_COMP = nSecCab_in;
          RETURN cursListado;
        END;

  FUNCTION LISTAR_NUM_GUIA_ANULAR_RECEP (vCodGrupoCia_in IN CHAR, vCodLocal_in IN CHAR,
                      vNumerGuia_in IN CHAR, vIdeDocumento_in IN CHAR,
                      vNumeroDocument_in IN CHAR)
  RETURN CHAR
    IS
      FLAG CHAR(7);
      estado LGT_NOTA_ES_CAB.EST_NOTA_ES_CAB%TYPE;
      BEGIN
        FLAG := 'FALSE';
        SELECT EST_NOTA_ES_CAB
        INTO estado
        FROM LGT_NOTA_ES_CAB
        WHERE COD_GRUPO_CIA = vCodGrupoCia_in
        AND COD_LOCAL = vCodLocal_in
        AND NUM_NOTA_ES = vNumerGuia_in
        AND TIP_DOC = vIdeDocumento_in
        AND NUM_DOC = vNumeroDocument_in;
        IF TRIM(estado) = 'A' THEN
          FLAG := 'TRUE';
        ELSIF TRIM(estado) = 'N' THEN
          FLAG := 'FLASE';
        END IF;
        RETURN FLAG;
        EXCEPTION
          WHEN OTHERS THEN

              DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
          RETURN FLAG;
      END;

PROCEDURE ANULAR_INGRESO_RECEPCION(cCodGrupoCia_in IN CHAR,
                                cCodCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
								cCodOC_in IN CHAR,
								nSecCab_in IN NUMBER)
  AS
    CURSOR curDet IS
    SELECT COD_PROD, CANT_RECEP
    FROM LGT_OC_DET_RECEP
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_CIA    = cCodCia_in
	  AND COD_LOCAL = cCodLocal_in
      AND COD_OC    = cCodOC_in
	  AND SEC_CAB_ORD_COMP = nSecCab_in;

    det curDet%ROWTYPE;
    vEstado      LGT_OC_CAB_RECEP.EST_OC_CAB%TYPE;
    vProceso     LGT_OC_CAB_RECEP.PROC_OC_CAB%TYPE;
    nCant        LGT_OC_DET_RECEP.CANT_RECEP%TYPE;
	  
    BEGIN
      SELECT EST_OC_CAB, PROC_OC_CAB
      INTO  vEstado, vProceso
      FROM LGT_OC_CAB_RECEP
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_CIA    = cCodCia_in
	  AND COD_LOCAL = cCodLocal_in
      AND COD_OC    = cCodOC_in
	  AND SEC_CAB_ORD_COMP = nSecCab_in;

      IF vEstado = 'A' THEN
       FOR det IN curDet
        LOOP
          SELECT CANT_RECEP
          INTO nCant
          FROM LGT_OC_DET
          WHERE COD_OC = cCodOC_in
          AND COD_PROD = det.COD_PROD;

          IF (nCant - det.CANT_RECEP) >= 0 THEN
            UPDATE LGT_OC_DET
            SET CANT_RECEP = nCant - det.CANT_RECEP
            WHERE  COD_OC      = cCodOC_in
            AND COD_PROD       = det.COD_PROD;

            IF vProceso = 'T' THEN
              UPDATE LGT_OC_CAB_RECEP
              SET  PROC_OC_CAB   = 'P'
              WHERE COD_GRUPO_CIA  = cCodGrupoCia_in
              AND COD_CIA        = cCodCia_in
              AND COD_LOCAL      = cCodLocal_in
              AND COD_OC         = cCodOC_in
			   AND SEC_CAB_ORD_COMP = nSecCab_in;

              UPDATE LGT_OC_CAB
              SET PROC_OC_CAB = 'P'
              WHERE COD_GRUPO_CIA  = CCodGrupoCia_in
              AND COD_CIA        = cCodCia_in
              AND COD_LOCAL      = cCodLocal_in
              AND COD_OC         = cCodOC_in;

            END IF;

          END IF;

          /*UPDATE LGT_OC_DET_RECEP
          SET EST_OC_CAB = 'N'
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
		  AND COD_CIA    = cCodCia_in
		  AND COD_LOCAL = cCodLocal_in
		  AND COD_OC    = cCodOC_in
		  AND SEC_CAB_ORD_COMP = nSecCab_in
          AND COD_PROD       = det.COD_PROD;*/

      END LOOP;
      END IF;

      UPDATE LGT_OC_CAB_RECEP
      SET EST_OC_CAB = 'N'
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
		  AND COD_CIA    = cCodCia_in
		  AND COD_LOCAL = cCodLocal_in
		  AND COD_OC    = cCodOC_in
		  AND SEC_CAB_ORD_COMP = nSecCab_in;

    EXCEPTION
    WHEN OTHERS THEN

         DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);

  END ANULAR_INGRESO_RECEPCION;

  FUNCTION LISTAR_DOCUMEN_RECEP(cCodGrupoCia_in IN CHAR,
                                cCodCia_in in CHAR,
                                cCodLocal_in IN CHAR,
                                cCodOrdComp_in IN CHAR,
                                cCodProv_in IN CHAR)
  RETURN FarmaCursor
  IS
        cursListado FarmaCursor;
    BEGIN
        OPEN cursListado FOR
           SELECT (CABR.COD_OC || 'Ã' ||
                   NOTA.NUM_NOTA_ES || 'Ã' ||
                   TIP.DESC_COMP || 'Ã' ||
                   CABR.SER_DOC_CAB || 'Ã' ||
                   CABR.NUM_DOC_CAB || 'Ã' ||
                   PROV.NOM_PROV || 'Ã' ||
                   TO_DATE(CABR.FEC_CREA, 'dd/MM/yyyy') || 'Ã' ||
                   TO_CHAR(nvl(CABR.REDON_OC_CAB,0), 'FM990.990') || 'Ã' ||
                   TRIM(TO_CHAR(nvl(CABR.VAL_PAR_TOTAL_OC_CAB,0), '999,999.999')) || 'Ã' ||
                   NOTA.EST_NOTA_ES_CAB || 'Ã' ||
                   TRIM(TO_CHAR(CABR.TIP_COMP_CAB, '09'))|| 'Ã' ||
                   PROV.COD_PROV|| 'Ã' ||
				   CABR.SEC_CAB_ORD_COMP
				   ) AS RESULTADO
           FROM  LGT_OC_CAB_RECEP CABR
           INNER JOIN  LGT_NOTA_ES_CAB NOTA ON(NOTA.NUM_DOC = CABR.NUM_DOC_CAB)
           INNER JOIN  VTA_TIP_COMP TIP ON(CABR.TIP_COMP_CAB = TIP.TIP_COMP AND TIP.TIP_COMP = NOTA.TIP_DOC)
           INNER JOIN  LGT_PROV PROV ON(PROV.COD_PROV = CABR.COD_PROV AND NOTA.COD_ORIGEN_NOTA_ES = PROV.COD_PROV)
           WHERE CABR.COD_GRUPO_CIA = cCodGrupoCia_in
           AND CABR.COD_OC = cCodOrdComp_in
           AND CABR.COD_CIA = cCodCia_in
           AND CABR.COD_LOCAL = cCodLocal_in
           ORDER BY CABR.FEC_CREA DESC;
        RETURN cursListado;
    END;

  --CAMBIA EL ESTADO PROCESADO(P) A TERMINADO(T)  EN LA TABLA LGT_OC_CAB, LGT_OC_CAB_RECEP
  FUNCTION CIERRE_ORD_COMPRA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumerGuia_in IN CHAR)
  RETURN CHAR
  AS
    CURSOR curDet IS
      SELECT CANT_RECEP, CANT_PED
      FROM LGT_OC_DET
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL       = cCodLocal_in
      AND COD_OC          = cNumerGuia_in;

    det curDet%ROWTYPE;
    nCantRecep  LGT_OC_DET_RECEP.CANT_RECEP%TYPE;
    nCantPed    LGT_OC_DET_RECEP.CANT_RECEP%TYPE;
    FLAG CHAR(7);
    BEGIN
      FLAG := 'FALSE';
      nCantRecep := 0;
      nCantPed   := 0 ;
      FOR  det IN curDet
      LOOP
        nCantRecep := nCantRecep + det.CANT_RECEP;
        nCantPed   := nCantPed + det.CANT_PED;
      END LOOP;
      IF nCantRecep = nCantPed THEN
        UPDATE LGT_OC_CAB_RECEP
        SET PROC_OC_CAB = 'T'
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_LOCAL       = cCodLocal_in
        AND COD_OC          = cNumerGuia_in
        AND EST_OC_CAB      = 'A';

        UPDATE LGT_OC_CAB
        SET PROC_OC_CAB = 'T'
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_LOCAL       = cCodLocal_in
        AND COD_OC          = cNumerGuia_in
        AND EST_OC_CAB      = 'A';

        FLAG := 'TRUE';
      ELSE
        FLAG := 'FLASE';
      END IF;

    RETURN FLAG;
    EXCEPTION
      WHEN OTHERS THEN

        DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
        RETURN FLAG;
 END;

  FUNCTION INV_LISTA_DEVOLUCION(cGrupoCia_in IN CHAR,
                              cCia_in IN CHAR,
                              cCodLocal_in IN CHAR,
                              vFiltro_in   IN CHAR,
                              cTipoOrigen  IN CHAR)
    RETURN FarmaCursor
  IS
  	curGuias FarmaCursor;
  BEGIN
       OPEN curGuias FOR
            SELECT (NC.NUM_NOTA_ES || 'Ã' ||
                   NC.DESC_EMPRESA || 'Ã' ||
                   TO_CHAR(NC.FEC_NOTA_ES_CAB,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
                   NC.CANT_ITEMS || 'Ã' ||
                   DECODE(NC.EST_NOTA_ES_CAB,'N','ANULADO'
                                             ,'C','CONFIRMADO'
                                             ,'X','ERROR'
                                             ,'R','RECHAZADO'
                                             ,'A','ACEPTADO'
                                             ,'P','POR CONFIRMAR'
                                             ,' ') || 'Ã' ||
                   NVL(GR.NUM_GUIA_REM,' ')  || 'Ã' ||
                   DECODE(GR.IND_GUIA_IMPRESA ,'S','SI'
                                              ,'N',' ') || 'Ã' ||
                   K.DESC_CORTA_MOT_KARDEX || 'Ã' ||
                   NC.EST_NOTA_ES_CAB) AS RESULTADO
            FROM   LGT_NOTA_ES_CAB NC
            INNER JOIN LGT_GUIA_REM  GR
          	ON (NC.COD_GRUPO_CIA   = GR.COD_GRUPO_CIA
            AND NC.COD_LOCAL   = GR.COD_LOCAL
            AND NC.NUM_NOTA_ES = GR.NUM_NOTA_ES)
            INNER JOIN LGT_MOT_KARDEX K
          	ON (NC.COD_GRUPO_CIA = K.COD_GRUPO_CIA
            AND NC.TIP_MOT_NOTA_ES = K.COD_MOT_KARDEX)
  	        WHERE  NC.COD_GRUPO_CIA = cGrupoCia_in
  	        AND NC.COD_LOCAL = cCodLocal_in
  	        AND NC.TIP_NOTA_ES = PTOVENTA_INV.g_cTipoNotaSalida
            AND NC.TIP_ORIGEN_NOTA_ES = cTipoOrigen
            AND K.COD_MOT_KARDEX LIKE vFiltro_in;
  	RETURN CURGUIAS;
  END;


   /****************************************************************************************************/

  FUNCTION INV_GET_MOTIVO_DEVOLUCION
  RETURN FarmaCursor
  IS
    curMot FarmaCursor;
  BEGIN

      OPEN curMot FOR
          SELECT LLAVE_TAB_GRAL || 'Ã' ||
                 DESC_CORTA
          FROM PBL_TAB_GRAL
          WHERE COD_APL = g_vCodPtoVenta
          AND COD_TAB_GRAL = g_vMotivoAjuste;

    RETURN CURMOT;
  END;

  /****************************************************************************************************/

  FUNCTION INV_GET_ORDEN_COMPRA(cGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cProv_in     IN CHAR)
  RETURN FarmaCursor
  IS
    cursListado FarmaCursor;
  BEGIN
      OPEN cursListado FOR
          SELECT (OC.COD_OC || 'Ã' ||
                 P.NOM_PROV || 'Ã' ||
                 OC.FEC_VENC_OC || 'Ã' ||
                 OC.CANT_DIAS || 'Ã' ||
                 OC.FEC_PROG_ENTREGA || 'Ã' ||
                 OC.CANT_ITEMS) AS RESULTADO
          FROM LGT_OC_CAB OC
          INNER JOIN LGT_PROV P
          ON (OC.COD_PROV  = P.COD_PROV)
          WHERE P.COD_PROV = cProv_in
          --AND   OC.COD_CIA   = cCia_in
  	      AND   OC.COD_LOCAL = cCodLocal_in
          AND   OC.COD_GRUPO_CIA = cGrupoCia_in;
    RETURN cursListado;
  END;


  FUNCTION OBTENER_DETALLE_PRODUCTOS_OC(cCodOrdenCompr IN CHAR,
                                        cBuscar IN VARCHAR2)

 RETURN FarmaCursor
  IS
    curOc FarmaCursor;
  BEGIN

      OPEN CUROC FOR
         SELECT (OCD.COD_PROD  || 'Ã' ||
                P.desc_prod||' - '||p.desc_unid_present || 'Ã' ||
                P.CANT_UNID_PRESENT  || 'Ã' ||
                PL.STK_FISICO   || 'Ã' ||
                PL.UNID_VTA    || 'Ã' ||
                P.IND_PROD_FRACCIONABLE || 'Ã' ||
                L.NOM_LAB || 'Ã' ||
                PL.IND_PROD_CONG || 'Ã' ||
                TO_CHAR(SYSDATE,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
                PL.VAL_PREC_VTA || 'Ã' ||
                PL.VAL_FRAC_LOCAL)
                AS RESULTADO
         FROM LGT_PROD P
         INNER JOIN LGT_OC_DET OCD ON (P.COD_PROD = OCD.COD_PROD)
         INNER JOIN LGT_PROD_LOCAL PL ON (P.COD_PROD = PL.COD_PROD)
         INNER JOIN LGT_LAB L ON (P.COD_LAB = L.COD_LAB)
         WHERE OCD.COD_OC = CCODORDENCOMPR
         AND P.COD_PROD LIKE cBuscar;


    RETURN CUROC;
  END;

  PROCEDURE INV_CONFIRMAR_DEVOL(cGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumNotaEs_in IN CHAR, vIdUsu_in IN VARCHAR2)
  AS
    v_cTipOrigen        LGT_NOTA_ES_CAB.TIP_ORIGEN_NOTA_ES%TYPE;
    v_cIND_NOTA_IMPRESA LGT_NOTA_ES_CAB.IND_NOTA_IMPRESA%TYPE;
  BEGIN
    SELECT TIP_ORIGEN_NOTA_ES,IND_NOTA_IMPRESA
      INTO v_cTipOrigen,v_cIND_NOTA_IMPRESA
    FROM LGT_NOTA_ES_CAB
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          --AND COD_CIA = cCodCia_in
          AND COD_LOCAL = cCodLocal_in
          AND TIP_NOTA_ES = PTOVENTA_INV.g_cTipoNotaSalida
          AND COD_ORIGEN_NOTA_ES = cCodLocal_in
          AND COD_DESTINO_NOTA_ES <> cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in
          ;
    IF (v_cIND_NOTA_IMPRESA='S') THEN
      IF v_cTipOrigen = PTOVENTA_INV.g_cTipoOrigenProveedor OR
         v_cTipOrigen = PTOVENTA_INV.g_cTipoOrigenGuiaSalida	  THEN
        UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = vIdUsu_in, FEC_MOD_NOTA_ES_CAB = SYSDATE,
              EST_NOTA_ES_CAB = 'A'
        WHERE COD_GRUPO_CIA = cGrupoCia_in
              --AND COD_CIA = cCodCia_in
              AND COD_LOCAL = cCodLocal_in
              AND TIP_NOTA_ES = PTOVENTA_INV.g_cTipoNotaSalida
              AND EST_NOTA_ES_CAB = 'P'
              AND IND_NOTA_IMPRESA = 'S'
              AND NUM_NOTA_ES = cNumNotaEs_in;
      END IF;
    END IF;
  END;
  
  --==============================================================================================
  --INVOCA AL PROCEDIMIENTO DE ACTUALIZACIÓN DE LA ORDEN DE COMPRA EN COMIS
  --Fecha        Usuario		    Comentario
  --26/10/2013   CVILCA         Creación
  
  FUNCTION OBTENER_PRODUCTOS_POR_NOTA(cCodGrupoCia_in IN CHAR,
                                      cCodCia_in      IN CHAR,  
                                      cCod_Local_in   IN CHAR,
                                      cNumOrdCom_in   IN VARCHAR2,
                                      vNumNota_in     IN VARCHAR2)
  RETURN FarmaCursor
  IS  
    curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
                  select (p.cod_prod || 'Ã' || 
                          sum(dr.cant_recep) ) AS RESULTADO
                    from lgt_oc_det_recep dr,
                         (   select nd.cod_prod
                            from lgt_nota_es_det nd 
                            where nd.cod_grupo_cia = cCodGrupoCia_in
                            --and nd.cod_cia = cCodCia_in
                            and nd.cod_local = cCod_Local_in
                            and nd.num_nota_es = vNumNota_in) p
                    where dr.cod_prod = p.cod_prod
                      and dr.cod_oc = cNumOrdCom_in
                      group by p.cod_prod;
    RETURN curProd;
  END OBTENER_PRODUCTOS_POR_NOTA; 
          
                         
 FUNCTION GET_PROD_INNER_PACK(cCodGrupoCia_in IN CHAR,
                               cCodProd_in      IN CHAR)
  RETURN NUMBER
  IS  
    valInnerPack NUMBER(4);
  BEGIN
      
      SELECT DECODE(PROD.VAL_INNER_PACK, NULL, 0, PROD.VAL_INNER_PACK)
      into valInnerPack
      FROM LGT_PROD PROD 
      WHERE PROD.COD_GRUPO_CIA = cCodGrupoCia_in
      AND PROD.COD_PROD = cCodProd_in;              
      RETURN valInnerPack;
   EXCEPTION 
   WHEN OTHERS THEN
      RETURN 0;
          
 END GET_PROD_INNER_PACK;                        
                                         
END PTOVENTA_MDIRECTA;

/
