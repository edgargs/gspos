CREATE OR REPLACE FORCE VIEW PTOVENTA.V_RECEP_LISTA_PROD AS
--ERIOS 14.03.2016 Se agrega condicion por lotes.
SELECT
			PRODUCTO COD_PRODUCTO,
			DESCRIP_PROD ,
			UNIDAD ,
			LAB ,
			CANT2 ,
			CANT1 ,
			CANTIDAD ,
			PRODUCTO ,
			SEC_CONTEO,
      LOTE,
      FECHA_VENCIMIENTO
		FROM (SELECT Q1.PROD PRODUCTO,
				   Q2.CANT2,
				   Q1.CANT1,
				   (Q2.CANT2 - Q1.CANT1) CANT,
				   Q3.DESC_PROD DESCRIP_PROD,
				   Q3.DESC_UNID_PRESENT UNIDAD,
				   (SELECT L.NOM_LAB FROM PTOVENTA.LGT_LAB L WHERE L.COD_LAB = Q3.COD_LAB) LAB,
				   Q1.SEC_CONTEO,
				   Q1.CANTIDAD,
           Q1.LOTE,
           Q1.FECHA_VENCIMIENTO
			  FROM (SELECT A.COD_PROD PROD,
						   NVL(A.CANTIDAD, 0) CANT1,
						   A.SEC_CONTEO SEC_CONTEO,
						   A.CANT_SEG_CONTEO CANTIDAD,
               A.LOTE LOTE,
               TO_CHAR(A.FECHA_VENCIMIENTO_LOTE, 'DD/MM/YYYY') FECHA_VENCIMIENTO
					  FROM PTOVENTA.LGT_PROD_CONTEO A
					 WHERE A.COD_GRUPO_CIA = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodGrupoCia
					   AND A.COD_LOCAL     = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodLocal
					   AND A.NRO_RECEP     = PTOVENTA_RECEP_CIEGA_JCG.get_g_cNroRecep) Q1,
				   (SELECT B.COD_PROD PROD,B.NUM_LOTE_PROD,
						   SUM(B.CANT_ENVIADA_MATR) CANT2,
						   ' ' COD_BARRA
					  FROM PTOVENTA.LGT_NOTA_ES_DET B
					 WHERE B.COD_GRUPO_CIA = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodGrupoCia
					   AND B.COD_LOCAL     = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodLocal
					   AND EXISTS
						   (SELECT 1
							  FROM PTOVENTA.LGT_RECEP_ENTREGA C
							 WHERE C.COD_GRUPO_CIA = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodGrupoCia
							   AND C.COD_LOCAL     = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodLocal
							   AND C.NRO_RECEP     = PTOVENTA_RECEP_CIEGA_JCG.get_g_cNroRecep
							   AND B.NUM_ENTREGA   = C.NUM_ENTREGA
							   AND B.NUM_NOTA_ES   = C.NUM_NOTA_ES
							   )
					 GROUP BY B.COD_PROD,B.NUM_LOTE_PROD) Q2,
				   LGT_PROD Q3
			 WHERE Q1.PROD = Q2.PROD
			   AND (PTOVENTA_RECEP_CIEGA_JCG.get_g_cIndLocalM = 'N'
			        OR (Q3.IND_LOTE_MAYORISTA = 'N'
						OR (Q3.IND_LOTE_MAYORISTA = 'S'
						    AND Q1.LOTE = Q2.NUM_LOTE_PROD)) )
			   AND Q3.COD_GRUPO_CIA = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodGrupoCia
			   AND Q3.COD_PROD = Q1.PROD
			)
		WHERE CANT > 0
			OR CANT < 0
		UNION
		SELECT H.COD_PROD ,
			   H.DESC_PROD ,
			   H.DESC_UNID_PRESENT ,
			   (SELECT L.NOM_LAB FROM PTOVENTA.LGT_LAB L WHERE L.COD_LAB = H.COD_LAB) ,
			   0 CANT2,
			   E.cantidad ,
				 E.CANT_SEG_CONTEO ,
				 E.COD_PROD ,
			   E.SEC_CONTEO,
         E.LOTE,
         TO_CHAR(E.FECHA_VENCIMIENTO_LOTE, 'DD/MM/YYYY')
		  FROM PTOVENTA.LGT_PROD_CONTEO E, PTOVENTA.LGT_PROD H
		 WHERE E.COD_GRUPO_CIA = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodGrupoCia
		   AND E.COD_LOCAL = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodLocal
		   AND E.NRO_RECEP = PTOVENTA_RECEP_CIEGA_JCG.get_g_cNroRecep
		   AND NOT EXISTS
			   (SELECT 1
				  FROM LGT_NOTA_ES_DET F, LGT_RECEP_ENTREGA G
				 WHERE F.COD_GRUPO_CIA = G.COD_GRUPO_CIA
				   AND F.COD_LOCAL     = G.COD_LOCAL
				   AND G.NRO_RECEP     = PTOVENTA_RECEP_CIEGA_JCG.get_g_cNroRecep
				   AND F.NUM_ENTREGA   = G.NUM_ENTREGA
				   AND F.COD_GRUPO_CIA = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodGrupoCia
				   AND F.COD_LOCAL     = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodLocal
				   AND F.NUM_NOTA_ES   = G.NUM_NOTA_ES
					  -- AND F.EST_NOTA_ES_DET = 'P'
				   AND F.SEC_GUIA_REM = G.SEC_GUIA_REM
				   AND F.COD_PROD     = E.COD_PROD
				   AND (PTOVENTA_RECEP_CIEGA_JCG.get_g_cIndLocalM = 'N'
				        OR (H.IND_LOTE_MAYORISTA = 'N'
						    OR (H.IND_LOTE_MAYORISTA = 'S'
                                AND F.NUM_LOTE_PROD = E.LOTE)) )
          )
		   AND E.COD_GRUPO_CIA = H.COD_GRUPO_CIA
		   AND E.COD_PROD = H.COD_PROD
		UNION
		SELECT K.COD_PROD ,
			   K.DESC_PROD ,
			   K.DESC_UNID_PRESENT ,
			   (SELECT L.NOM_LAB FROM PTOVENTA.LGT_LAB L WHERE L.COD_LAB = K.COD_LAB) ,
			   I.CANT_ENVIADA_MATR ,
			   0 ,
			   null ,
			   K.COD_PROD ,
			   0,
               I.NUM_LOTE_PROD,
               TO_CHAR(I.FEC_VCTO_PROD,'DD/MM/YYYY')
		  FROM PTOVENTA.LGT_NOTA_ES_DET I, PTOVENTA.LGT_PROD K
		 WHERE I.COD_GRUPO_CIA = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodGrupoCia
		   AND K.COD_GRUPO_CIA = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodGrupoCia
		   AND I.COD_LOCAL     = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodLocal
		   AND I.COD_GRUPO_CIA = K.COD_GRUPO_CIA
		   AND I.COD_PROD      = K.COD_PROD
		   AND exists
			   (SELECT 1
				  FROM PTOVENTA.LGT_RECEP_ENTREGA J
				 WHERE J.COD_GRUPO_CIA = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodGrupoCia
				   AND J.COD_LOCAL     = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodLocal
				   AND J.NRO_RECEP     = PTOVENTA_RECEP_CIEGA_JCG.get_g_cNroRecep
				   and j.num_entrega   = I.NUM_ENTREGA )
		   AND NOT EXISTS
			   (SELECT 1
				  FROM PTOVENTA.LGT_PROD_CONTEO E
				 WHERE E.COD_GRUPO_CIA = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodGrupoCia
				   AND E.COD_LOCAL     = PTOVENTA_RECEP_CIEGA_JCG.get_g_cCodLocal
				   AND E.NRO_RECEP     = PTOVENTA_RECEP_CIEGA_JCG.get_g_cNroRecep
				   AND E.COD_PROD      = I.COD_PROD
				   AND (PTOVENTA_RECEP_CIEGA_JCG.get_g_cIndLocalM = 'N'
				        OR (K.IND_LOTE_MAYORISTA = 'N' 
						    OR (K.IND_LOTE_MAYORISTA = 'S'
                                AND E.LOTE = I.NUM_LOTE_PROD)) )
				   )
;

