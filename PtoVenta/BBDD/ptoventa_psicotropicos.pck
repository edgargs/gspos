CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_PSICOTROPICOS" AS

    TYPE FarmaCursor IS REF CURSOR;

    PROCEDURE VTA_GRABA_PEDIDO(pCodGruCia    IN CHAR,
                              pCodLocal     IN CHAR,
                              pNumPedVta    IN CHAR,
                              pDni          IN VARCHAR2,
                              pNomPaciente  IN VARCHAR2,
                              pCmp          IN VARCHAR2,
                              pNomMedico    IN VARCHAR2,
                              pIdUsu        IN VARCHAR2,
							                nIndBusqueda_in IN CHAR,
                              -- KMONCADA 2016.01.21 GENERA PROFORMA [LOCAL M]
                              cGeneraProforma_in  IN  CHAR DEFAULT 'N'
                                      );

    FUNCTION F_GET_VENTA_RESTRINGIDA (pCodGruCia  IN CHAR,
                                      pCodLocal	  IN CHAR,
                                      pNumPedVta  IN CHAR,
                                      -- KMONCADA 2016.01.21 GENERA PROFORMA [LOCAL M]
                                      cGeneraProforma_in  IN  CHAR DEFAULT 'N' )
    RETURN CHAR;


    --Descripcion: Retorna listado de reporte de psicotropicos entre pFecInicial y pFecFinal
    --Fecha        Usuario		    Comentario
    --23/Sep/2013  Luis Leiva     Creación
    FUNCTION REPORTE_PSICOTROPICOS (pCodGruCia_in  IN CHAR,
                                    pCodCia_in     IN CHAR,
                                    pCodLocal_in	 IN CHAR,
                                    pFecInicial_in IN CHAR,
                                    pFecFinal_in   IN CHAR,
                                    pCodProd_in    IN CHAR )
    RETURN FarmaCursor;

    --Descripcion: Retorna detalle de registro de psicotropicos seleccionado
    --Fecha        Usuario		    Comentario
    --24/Sep/2013  Luis Leiva     Creación
    FUNCTION DETALLE_PSICOTROPICOS (pSecuencia_in  IN CHAR)
    RETURN FarmaCursor;

    --Descripcion: Verifica si el producto es un Psicotropico
    --Fecha        Usuario		    Comentario
    --22/Oct/2013  Luis Leiva     Creación
    FUNCTION VERIFICA_PROD_PSICOTROPICO (pCodProd_in  IN CHAR)
    RETURN VARCHAR2;
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_PSICOTROPICOS" IS

  PROCEDURE VTA_GRABA_PEDIDO( pCodGruCia    IN CHAR,
                              pCodLocal     IN CHAR,
                              pNumPedVta    IN CHAR,
                              pDni          IN VARCHAR2,
                              pNomPaciente  IN VARCHAR2,
                              pCmp          IN VARCHAR2,
                              pNomMedico    IN VARCHAR2,
                              pIdUsu        IN VARCHAR2,
                              nIndBusqueda_in IN CHAR,
                              -- KMONCADA 2016.01.21 GENERA PROFORMA [LOCAL M]
                              cGeneraProforma_in  IN  CHAR DEFAULT 'N'
                                  )
  AS
  BEGIN
    IF cGeneraProforma_in = 'N' THEN
      --wvillagomez 02.09.2013
      INSERT INTO PTOVENTA.VTA_PEDIDO_PSICOTROPICO
        (COD_GRUPO_CIA,
         COD_LOCAL,
         NUM_PED_VTA,
         DNI_CLI,
         NOM_APE_CLI,
         FEC_PED_VTA,
         NUM_CMP,
         NOM_APE_MED,
         USU_CREA,
       IND_BUSQUEDA_CMP)
      VALUES
        (pCodGruCia,
         pCodLocal,
         pNumPedVta,
         pDni,
         pNomPaciente,
         SYSDATE,
         pCmp,
         pNomMedico,
         pIdUsu,
       nIndBusqueda_in);
     -- KMONCADA 12.05.2016 REGISTRO FARMA DE CMP
     PTOVENTA_VTA.P_REGISTRA_CMP_FARMA(cCodGrupoCia_in => pCodGruCia,
                                       cCodLocal_in => pCodLocal,
                                       cNumPedVta_in => pNumPedVta,
                                       cNumCmp_in => pCmp);
    ELSE
      --wvillagomez 02.09.2013
      INSERT INTO PTOVENTA.TMP_VTA_PEDIDO_PSICOTROPICO
        (COD_GRUPO_CIA,
         COD_LOCAL,
         NUM_PED_VTA,
         DNI_CLI,
         NOM_APE_CLI,
         FEC_PED_VTA,
         NUM_CMP,
         NOM_APE_MED,
         USU_CREA,
       IND_BUSQUEDA_CMP)
      VALUES
        (pCodGruCia,
         pCodLocal,
         pNumPedVta,
         pDni,
         pNomPaciente,
         SYSDATE,
         pCmp,
         pNomMedico,
         pIdUsu,
       nIndBusqueda_in);
    END IF;

  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20999,
                              'ERROR AL INSERTAR RCD_GRABA_RECETA_PEDIDO.' ||
                              SQLERRM);
  END VTA_GRABA_PEDIDO;


  FUNCTION F_GET_VENTA_RESTRINGIDA (pCodGruCia  IN CHAR,
                                    pCodLocal	  IN CHAR,
                                    pNumPedVta  IN CHAR,
                                    -- KMONCADA 2016.01.21 GENERA PROFORMA [LOCAL M]
                                    cGeneraProforma_in  IN  CHAR DEFAULT 'N' )
  RETURN CHAR
  IS
        vCantRestringidos INTEGER;
        vEstado           CHAR(1);
        vVtaRestringida   CHAR(1);
  BEGIN
        --wvillagomez 03.09.2013
        vCantRestringidos :=  0;
        vEstado           := 'A';
        vVtaRestringida   := 'N';
        
        -- KMONCADA 22.01.2016 [LOCAL M]
        IF cGeneraProforma_in = 'N' THEN
          SELECT COUNT(*)
            INTO vCantRestringidos
            FROM VTA_PEDIDO_VTA_DET DET
            JOIN VTA_PROD_RESTRINGIDOS RES ON (DET.COD_GRUPO_CIA = RES.COD_GRUPO_CIA
                                                AND DET.COD_PROD = RES.COD_PROD)
           WHERE DET.COD_GRUPO_CIA  = pCodGruCia
             AND DET.COD_LOCAL      = pCodLocal
             AND DET.NUM_PED_VTA    = pNumPedVta
             AND RES.EST_PROD       = vEstado;
        ELSE
          SELECT COUNT(*)
            INTO vCantRestringidos
            FROM TMP_VTA_PEDIDO_VTA_DET DET
            JOIN VTA_PROD_RESTRINGIDOS RES ON (DET.COD_GRUPO_CIA = RES.COD_GRUPO_CIA
                                                AND DET.COD_PROD = RES.COD_PROD)
           WHERE DET.COD_GRUPO_CIA  = pCodGruCia
             AND DET.COD_LOCAL      = pCodLocal
             AND DET.NUM_PED_VTA    = pNumPedVta
             AND RES.EST_PROD       = vEstado;
        END IF;
        
        IF vCantRestringidos > 0 THEN
            vVtaRestringida := 'S';
        END IF;
        RETURN vVtaRestringida;
  END;

  --Descripcion: Retorna listado de reporte de psicotropicos entre pFecInicial y pFecFinal
  --Fecha        Usuario		    Comentario
  --23/Sep/2013  Luis Leiva     Creación
  FUNCTION REPORTE_PSICOTROPICOS (pCodGruCia_in  IN CHAR,
                                  pCodCia_in     IN CHAR,
                                  pCodLocal_in	 IN CHAR,
                                  pFecInicial_in IN CHAR,
                                  pFecFinal_in   IN CHAR,
                                  pCodProd_in    IN CHAR )
  RETURN FarmaCursor
  IS
      curListado FarmaCursor;
  BEGIN
      --ERIOS 21.04.2015 Para venta por Delivery (Provincia), la descripcion es VENTA ESPECIAL.
	  --ERIOS 03.12.2015 Se agrega el documento de identidad.
	  --                 Se muestran prod. psicotropicos sin movimiento.
      OPEN curListado FOR
		SELECT FECHA ||'Ã'||
				COD_PROD ||'Ã'||
				DESC_PROD ||'Ã'||
				DESC_UNID_PRESENT ||'Ã'||
				MOTIVO ||'Ã'||
				TIP_COMP ||'Ã'||
				NUM_COMP ||'Ã'||
				STK_ANT ||'Ã'||
				CANT_MOV ||'Ã'||
				STK_FIN ||'Ã'||
				VAL_FRAC ||'Ã'||
				DNI_CLI ||'Ã'||
				NOM_APE_CLI ||'Ã'||
				NUM_CMP ||'Ã'||
				NOM_APE_MED ||'Ã'||
				USU_FV ||'Ã'||
				GLOSA ||'Ã'||
				ORD
				AS RESULTADO
		FROM(
		SELECT TO_CHAR(K.FEC_KARDEX,'dd/MM/yyyy') FECHA,
				P.COD_PROD ,
				P.DESC_PROD ,
				P.DESC_UNID_PRESENT ,
				CASE  WHEN K.COD_MOT_KARDEX = '002' THEN 'VENTA ESPECIAL'
				      ELSE NVL(MK.DESC_CORTA_MOT_KARDEX,' ') END MOTIVO,
                CASE k.tip_comp_pago
					WHEN '01' THEN 'BOLETA'
					WHEN '02' THEN 'FACTURA'
					WHEN '03' THEN 'GUIA'
					WHEN '04' THEN 'NOTA CREDITO'
					WHEN '05' THEN DECODE(K.TIP_DOC_KARDEX,'01','TICKET','ENTREGA')
					ELSE NVL(DECODE(K.TIP_DOC_KARDEX,'01','VENTA','02','GUIA ENTRADA/SALIDA','03','AJUSTE DE INVENTARIO'),' ')
                END TIP_COMP,
				NVL(DECODE(K.TIP_DOC_KARDEX,'01',NVL((select
                                                 Farma_Utility.GET_T_COMPROBANTE_2(x.COD_TIP_PROC_PAGO,x.NUM_COMP_PAGO_E,x.NUM_COMP_PAGO)
                                                from vta_comp_pago X
                                                 where x.num_comp_pago = k.NUM_COMP_PAGO
                                                 and   x.tip_comp_pago = k.tip_comp_pago),K.NUM_TIP_DOC),
                                           '02',K.NUM_COMP_PAGO,
                                           '03',' '),' ') NUM_COMP,
                NVL(K.STK_ANTERIOR_PROD,0) STK_ANT,
                NVL(K.CANT_MOV_PROD,0) CANT_MOV,
                NVL(K.STK_FINAL_PROD,0) STK_FIN,
                NVL(K.VAL_FRACC_PROD,0) VAL_FRAC,
				NVL(VTAS.DNI_CLI,' ') DNI_CLI,
                NVL(VTAS.NOM_APE_CLI,' ') NOM_APE_CLI,
                NVL(VTAS.Num_Cmp ,' ') NUM_CMP,
                NVL(VTAS.NOM_APE_MED,' ') NOM_APE_MED,
                nvl(decode(K.TIP_DOC_KARDEX,'01',nvl(vtas.usuario,' ')),' ') USU_FV,
                NVL(k.DESC_GLOSA_AJUSTE,' ') GLOSA,
                K.SEC_KARDEX ORD
          FROM LGT_KARDEX K
          INNER JOIN LGT_MOT_KARDEX MK ON (K.COD_GRUPO_CIA = MK.COD_GRUPO_CIA AND
                                          K.COD_MOT_KARDEX = MK.COD_MOT_KARDEX)
           INNER JOIN VTA_PROD_RESTRINGIDOS PR ON (PR.COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                              AND PR.COD_PROD = K.COD_PROD
                                              )
          LEFT JOIN (SELECT  PVD.USU_CREA_PED_VTA_DET usuario,
                              pvd.num_ped_vta venta,
							  VRE.DNI_CLI ,
                              VRE.NOM_APE_CLI ,
                              VRE.Num_Cmp,
                              VRE.NOM_APE_MED,
							  PVD.COD_PROD
                       FROM   vTa_pedido_vta_det pvd
                       INNER JOIN VTA_PEDIDO_PSICOTROPICO VRE ON VRE.COD_GRUPO_CIA(+) = PVD.COD_GRUPO_CIA
                                                                 AND VRE.COD_LOCAL(+) = PVD.COD_LOCAL
                                                                 AND VRE.NUM_PED_VTA(+) = pvd.num_ped_vta

                       WHERE  PVD.COD_GRUPO_CIA = pCodGruCia_in AND
                              PVD.COD_LOCAL = pCodLocal_in AND
                              pvd.est_ped_vta_det = 'A'
                              ) vtas ON (k.num_tip_doc = vtas.venta
                                        AND K.COD_PROD = VTAS.COD_PROD)
                JOIN LGT_PROD P ON (K.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                                    AND K.COD_PROD = P.COD_PROD)
          WHERE K.COD_GRUPO_CIA = pCodGruCia_in AND
                K.COD_LOCAL = pCodLocal_in AND
				K.COD_PROD LIKE NVL(pCodProd_in,'%') AND
                K.FEC_KARDEX BETWEEN TO_DATE(pFecInicial_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss') AND
                                     TO_DATE(pFecFinal_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
          union
          SELECT ' ' ,
				P.COD_PROD ,
				P.DESC_PROD ,
				P.DESC_UNID_PRESENT ,
				' ' ,
                ' ',
                ' ' ,
                0 ,
                0 ,
                NVL(PL.STK_FISICO,0) ,
                NVL(PL.VAL_FRAC_LOCAL,0) ,
				' ' ,
                ' ' ,
                ' ' ,
                ' ' ,
                ' ' ,
                ' ' ,
                '9999999999'
          FROM PTOVENTA.VTA_PROD_RESTRINGIDOS PR JOIN PTOVENTA.LGT_PROD_LOCAL PL ON (PR.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
                                                                                  AND PR.COD_PROD = PL.COD_PROD)
               JOIN LGT_PROD P ON (PR.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                                    AND PR.COD_PROD = P.COD_PROD)
         WHERE PL.COD_GRUPO_CIA = pCodGruCia_in
         AND PL.COD_LOCAL = pCodLocal_in
         AND PL.EST_PROD_LOC = 'A'
         AND PL.STK_FISICO > 0
         AND PL.COD_PROD LIKE NVL(pCodProd_in,'%')
		 AND SYSDATE BETWEEN TO_DATE(pFecInicial_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss') AND
										 TO_DATE(pFecFinal_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
         AND NOT EXISTS (         
			 SELECT 1
			 FROM LGT_KARDEX K
			WHERE K.COD_GRUPO_CIA = pCodGruCia_in AND
					K.COD_LOCAL = pCodLocal_in AND				
					K.FEC_KARDEX BETWEEN TO_DATE(pFecInicial_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss') AND
										 TO_DATE(pFecFinal_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
					AND K.COD_PROD = PL.COD_PROD
          )
		  )ORDER BY ORD,COD_PROD;
	  
          RETURN curListado;
  END;

  --Descripcion: Retorna detalle de registro de psicotropicos seleccionado
  --Fecha        Usuario		    Comentario
  --24/Sep/2013  Luis Leiva     Creación
  FUNCTION DETALLE_PSICOTROPICOS (pSecuencia_in  IN CHAR)
  RETURN FarmaCursor
  IS
      curListado FarmaCursor;
  BEGIN
      OPEN curListado FOR
            SELECT TO_CHAR(K.FEC_KARDEX,'dd/MM/yyyy') ||'Ã'||
                    NVL(MK.DESC_CORTA_MOT_KARDEX,' ') ||'Ã'||

                    CASE k.tip_comp_pago
                    WHEN '01' THEN 'BOLETA'
                    WHEN '02' THEN 'FACTURA'
                    WHEN '03' THEN 'GUIA'
                    WHEN '04' THEN 'NOTA CREDITO'
                    WHEN '05' THEN DECODE(K.TIP_DOC_KARDEX,'01','TICKET','ENTREGA')
                    ELSE NVL(DECODE(K.TIP_DOC_KARDEX,'01','VENTA','02','GUIA ENTRADA/SALIDA','03','AJUSTE DE INVENTARIO'),' ')
                    END
                    ||'Ã'||
                    DECODE(K.NUM_COMP_PAGO,NULL,NVL(K.NUM_TIP_DOC,' '),K.NUM_COMP_PAGO) ||'Ã'||
                    NVL(K.STK_ANTERIOR_PROD,0) ||'Ã'||
                    NVL(K.CANT_MOV_PROD,0) ||'Ã'||
                    NVL(K.STK_FINAL_PROD,0) ||'Ã'||
                    NVL(K.VAL_FRACC_PROD,0) ||'Ã'||
                    NVL(VTAS.NOM_APE_CLI,' ') ||'Ã'||
                    NVL(VTAS.NOM_APE_MED,' ') ||'Ã'||
                    nvl(decode(K.TIP_DOC_KARDEX,'01',nvl(vtas.usuario,' ')),' ') ||'Ã'||
                    NVL(k.DESC_GLOSA_AJUSTE,' ') ||'Ã'||
                    K.SEC_KARDEX AS RESULTADO
              FROM LGT_KARDEX K
              INNER JOIN LGT_MOT_KARDEX MK ON K.COD_GRUPO_CIA = MK.COD_GRUPO_CIA AND
                                              K.COD_MOT_KARDEX = MK.COD_MOT_KARDEX
              INNER JOIN (SELECT  PVD.USU_CREA_PED_VTA_DET usuario,
                                  pvd.num_ped_vta venta,
                                  VRE.NOM_APE_CLI ,
                                  VRE.NOM_APE_MED
                           FROM   vTa_pedido_vta_det pvd
                           INNER JOIN VTA_PEDIDO_PSICOTROPICO VRE ON VRE.COD_GRUPO_CIA = PVD.COD_GRUPO_CIA AND
                                                                     VRE.COD_LOCAL(+) = PVD.COD_LOCAL AND
                                                                     VRE.NUM_PED_VTA(+) = pvd.num_ped_vta
                           WHERE  pvd.est_ped_vta_det = 'A'
                                  ) vtas ON k.num_tip_doc = vtas.venta
              WHERE K.SEC_KARDEX=pSecuencia_in;
              RETURN curListado;
  END;

  --Descripcion: Verifica si el producto es un Psicotropico
  --Fecha        Usuario		    Comentario
  --24/Sep/2013  Luis Leiva     Creación
  FUNCTION VERIFICA_PROD_PSICOTROPICO (pCodProd_in  IN CHAR)
  RETURN VARCHAR2
  IS
      cant NUMBER;
  BEGIN
      select COUNT(*) into cant
      from vta_prod_restringidos
      WHERE COD_PROD = pCodProd_in;

      if(cant = 1) THEN
          return 'V';
      else
          return 'F';
      end if;
  END;
END;
/
