CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_ADMIN_CAJA" AS
	  TYPE FarmaCursor IS REF CURSOR;
	  /*************************************************************/
	  TIPO_DOC_BOLETA   	  VTA_TIP_COMP.TIP_COMP%TYPE := '01';
	  TIPO_DOC_FACTURA   	  VTA_TIP_COMP.TIP_COMP%TYPE := '02';
	  TIPO_DOC_GUIA   	  	  VTA_TIP_COMP.TIP_COMP%TYPE := '03';
    TIPO_DOC_TICKET  	  	  VTA_TIP_COMP.TIP_COMP%TYPE := '05';
	  COD_NUMERA_CAJA         PBL_NUMERA.COD_NUMERA%TYPE := '005';
	  TIPO_ROL_CAJERO   	  PBL_ROL.COD_ROL%TYPE 		 := '009';
	  ESTADO_ACTIVO		  	  CHAR(1):='A';
	  ESTADO_INACTIVO		  CHAR(1):='I';
	  INDICADOR_SI		  	  CHAR(1):='S';
	  POS_INICIO		      CHAR(1):='I';
	  /*************************************************************/

  --Descripcion: Obtiene el listado de las cajas
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación
  FUNCTION CAJ_LISTA_CAJAS(cCodGrupoCia_in IN CHAR,
  		   				   cCodLocal_in	   IN CHAR)
  RETURN FarmaCursor;
  /*************************************************************/

   --Descripcion: Obtiene el listado de los usuarios(Cajeros) del local
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación
  FUNCTION CAJ_LISTA_USUARIOS_LOCAL(cCodGrupoCia_in IN CHAR,
  		   				            cCodLocal_in	IN CHAR)
  RETURN FarmaCursor;
  /*************************************************************/

  --Descripcion: Ingresa una caja con un usuario asignado
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación

	PROCEDURE CAJ_INGRESA_CAJA(cCodGrupoCia_in IN CHAR,
		                       cCodLocal_in    IN CHAR,
						       cSecUsuLocal_in IN CHAR,
						       cDescCaja_in    IN CHAR,
						       cCodUsu_in      IN CHAR);
  /*************************************************************/

  --Descripcion: Modifica una caja
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación

	PROCEDURE CAJ_MODIFICA_CAJA(cCodGrupoCia_in IN CHAR,
		                        cCodLocal_in    IN CHAR,
						        nNumCajaPago_in IN NUMBER,
						   	    cSecUsuLocal_in IN CHAR,
						   	    cDescCaja_in    IN CHAR,
						   	    cCodUsu_in      IN CHAR);

   /*************************************************************/
   --Descripcion: Cambia el estado de una caja
   --Fecha       Usuario		Comentario
   --14/02/2006  MHUAYTA     Creación

   PROCEDURE CAJ_CAMBIAESTADO_CAJA(cCodGrupoCia_in IN CHAR,
		  					       cCodLocal_in    IN CHAR,
							       nNumCajaPago_in IN NUMBER,
								   cCodUsu_in      IN CHAR);

	/*************************************************************/
	--Descripcion: Inserta una asociacion Caja-Usuario
    --Fecha       Usuario		Comentario
    --14/02/2006  MHUAYTA     Creación

   PROCEDURE CAJ_INSERTA_ASOCIA_CAJA(cCodGrupoCia_in IN CHAR,
		  					         cCodLocal_in    IN CHAR,
							     	 nNumCajaPago_in IN NUMBER,
								 	 cCodUsu_in      IN CHAR
								 );
	/*************************************************************/
    --Descripcion: Obtiene los datos de un usuario
    --Fecha       Usuario		Comentario
    --14/02/2006  MHUAYTA     Creación

   FUNCTION CAJ_OBTENER_DATOS_USU_LOCAL(cCodGrupoCia_in IN CHAR,
  		   				                cCodLocal_in	IN CHAR,
							      		cSecUsuLocal_in IN CHAR)
   RETURN FarmaCursor;

   /*************************************************************/
   --Descripcion: Verifica si existen impresoras de los tipos requeridos para una caja
    --Fecha       Usuario		Comentario
    --14/02/2006  MHUAYTA     Creación
   FUNCTION CAJ_VERIF_EXISTEN_IMPR_DISP(cCodGrupoCia_in IN CHAR,
  		   						        cCodLocal_in    IN CHAR)
   RETURN  NUMBER;

   /*************************************************************/
    --Descripcion:Obtiene las impresoras de una caja
    --Fecha       Usuario		Comentario
    --14/02/2006  MHUAYTA     Creación
    FUNCTION CAJ_LISTA_IMPRESORAS_CAJA(cCodGrupoCia_in IN CHAR,
  		   			   		           cCodLocal_in	   IN CHAR,
							           cNumCaja_in     IN CHAR)
    RETURN FarmaCursor;

   /*************************************************************/
    --Descripcion:Muestra una lista de impresoras de reemplazo para una caja
    --Fecha       Usuario		Comentario
    --29/03/2006  MHUAYTA     Creación
	FUNCTION CAJ_LISTA_IMPRESORAS_REEMPLAZO(cCodGrupoCia_in   IN CHAR,
  		   			   		                cCodLocal_in	  IN CHAR,
							     			cNumCaja_in       IN CHAR,
							     			cTipComp_in 	  IN CHAR)
    RETURN FarmaCursor;

   /*************************************************************/
    --Descripcion:Modifica la relación Caja-Impresora
    --Fecha       Usuario		Comentario
    --29/03/2006  MHUAYTA     Creación
     PROCEDURE CAJ_MODIFICA_CAJA_IMPRESORA(cCodGrupoCia_in   IN CHAR,
                                            cCodLocal_in      IN CHAR,
                                            nNumCajaPago_in   IN NUMBER,
                                            cSecImprLocal1_in IN CHAR,
                                            cSecImprLocal2_in IN CHAR,
                                            cCodUsu_in        IN CHAR);

	/*************************************************************/
    --Descripcion:Obtiene la cantidad de cajeros disponibles en un local
    --Fecha       Usuario		Comentario
    --29/03/2006  MHUAYTA     Creación
   FUNCTION CAJ_CANT_CAJEROS_DISP_LOCAL(cCodGrupoCia_in IN CHAR,
  		   				       	        cCodLocal_in	IN CHAR)
   RETURN NUMBER;
  /*************************************************************/
    --Descripcion:Obtiene el estado de la caja
    --Fecha       Usuario		Comentario
    --11/07/2007  DUBILLUZ     Creación
   FUNCTION CAJ_OBTIENE_ESTADO_CAJA(cCodGrupoCia_in  IN CHAR,
  		   				       		        cCodLocal_in	   IN CHAR,
                                  nNumCajaPago_in  IN NUMBER)

    RETURN VARCHAR;


END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_ADMIN_CAJA" AS

/*****************************************************************************************************************/
FUNCTION CAJ_LISTA_CAJAS(cCodGrupoCia_in IN CHAR,
  		   				 cCodLocal_in	 IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR

		SELECT   VTA_CAJA_PAGO.NUM_CAJA_PAGO  	   	 						|| 'Ã' ||
		         VTA_CAJA_PAGO.DESC_CAJA_PAGO 								|| 'Ã' ||
		         CASE WHEN VTA_CAJA_PAGO.EST_CAJA_PAGO=ESTADO_ACTIVO
				       THEN 'Activo'
					   ELSE 'Inactivo'
				 END   				  					    				|| 'Ã' ||
		         CASE WHEN VTA_CAJA_PAGO.SEC_USU_LOCAL IS NULL
		 	     THEN ' Ã Ã' ELSE
		                      ( SELECT PBL_USU_LOCAL.APE_PAT  				|| ' '  ||
		 					           PBL_USU_LOCAL.APE_MAT 				|| ', ' ||
		 							   PBL_USU_LOCAL.NOM_USU 				|| 'Ã'  ||
		 							   PBL_USU_LOCAL.SEC_USU_LOCAL
		 					    FROM PBL_USU_LOCAL WHERE
		 	  					     PBL_USU_LOCAL.COD_LOCAL     = cCodLocal_in                AND
		 	  						 PBL_USU_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in             AND
		 	  						 PBL_USU_LOCAL.COD_GRUPO_CIA = VTA_CAJA_PAGO.COD_GRUPO_CIA AND
		 	  						 PBL_USU_LOCAL.COD_LOCAL     = VTA_CAJA_PAGO.COD_LOCAL     AND
		 	  						 PBL_USU_LOCAL.SEC_USU_LOCAL = VTA_CAJA_PAGO.SEC_USU_LOCAL
		                     )
		         END
        FROM    VTA_CAJA_PAGO
		WHERE   VTA_CAJA_PAGO.COD_GRUPO_CIA =  cCodGrupoCia_in AND
			    VTA_CAJA_PAGO.COD_LOCAL     =  cCodLocal_in;
    RETURN curVta;
  END;

/*****************************************************************************************************************/

  FUNCTION CAJ_LISTA_USUARIOS_LOCAL(cCodGrupoCia_in    IN CHAR,
  		   				            cCodLocal_in	   IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
		SELECT SEC_USU_LOCAL || 'Ã'  ||
			   APE_PAT       || ' '  ||
			   APE_MAT       || ', ' ||
			   NOM_USU
		FROM   PBL_USU_LOCAL
		WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
		       COD_LOCAL     = cCodLocal_in    AND
			   EST_USU       = ESTADO_ACTIVO   AND
			   COD_GRUPO_CIA || COD_LOCAL || SEC_USU_LOCAL IN (SELECT COD_GRUPO_CIA  ||
														  		 	   COD_LOCAL     ||
																 	   SEC_USU_LOCAL
														  		FROM   PBL_ROL_USU
														  		WHERE  COD_ROL = TIPO_ROL_CAJERO) AND
				COD_GRUPO_CIA || COD_LOCAL || SEC_USU_LOCAL NOT IN (SELECT COD_GRUPO_CIA ||
							  	 		   	  					   		   COD_LOCAL     ||
																		   SEC_USU_LOCAL
																	FROM   VTA_CAJA_PAGO);
    RETURN curVta;
  END;
/*****************************************************************************************************************/


PROCEDURE CAJ_INGRESA_CAJA(cCodGrupoCia_in IN CHAR,
		                   cCodLocal_in    IN CHAR,
						   cSecUsuLocal_in IN CHAR,
						   cDescCaja_in    IN CHAR,
						   cCodUsu_in      IN CHAR)
IS
  v_nNeoCod  NUMBER;
  v_IndImpDisp NUMBER;
  vCant NUMBER;
BEGIN
	SELECT COUNT(*) INTO vCant
	FROM VTA_CAJA_PAGO
	WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
          COD_LOCAL     = cCodLocal_in    AND
          SEC_USU_LOCAL = NVL(cSecUsuLocal_in,'_');

	 IF vCant>0 THEN
	   RAISE_APPLICATION_ERROR(-20006,'El usuario ya ha sido asignado a una caja');
     END IF;

	 v_IndImpDisp:=CAJ_VERIF_EXISTEN_IMPR_DISP(cCodGrupoCia_in ,cCodLocal_in);

	  IF v_IndImpDisp=0 THEN
		RAISE_APPLICATION_ERROR(-20007,'No se encontraron impresoras suficientes para asignar a la caja');
	  END IF;

    v_nNeoCod:= Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_CAJA);

                INSERT INTO VTA_CAJA_PAGO (COD_GRUPO_CIA,
                                           COD_LOCAL,
                						   NUM_CAJA_PAGO,
										   SEC_USU_LOCAL,
										   DESC_CAJA_PAGO,
										   EST_CAJA_PAGO,
										   FEC_CREA_CAJA_PAGO,
										   USU_CREA_CAJA_PAGO,
										   FEC_MOD_CAJA_PAGO,
										   USU_MOD_CAJA_PAGO)
                VALUES(cCodGrupoCia_in,
                       cCodLocal_in,
                	   v_nNeoCod ,
                	   cSecUsuLocal_in,
					   'CAJA '|| v_nNeoCod,
					   ESTADO_ACTIVO,
					   SYSDATE,
					   cCodUsu_in,
					   NULL,
					   NULL
					   );
	  CAJ_INSERTA_ASOCIA_CAJA(cCodGrupoCia_in,cCodLocal_in,v_nNeoCod ,cCodUsu_in);
END;

/*****************************************************************************************************************/

PROCEDURE CAJ_MODIFICA_CAJA(cCodGrupoCia_in IN CHAR,
		                    cCodLocal_in    IN CHAR,
						    nNumCajaPago_in IN NUMBER,
						    cSecUsuLocal_in IN CHAR,
						    cDescCaja_in    IN CHAR,
						    cCodUsu_in      IN CHAR)
IS
 vCant NUMBER;
 vIndCajaAbierta CHAR(1);
BEGIN
	 SELECT COUNT(*) INTO vCant
	 FROM   VTA_CAJA_PAGO
	 WHERE  COD_GRUPO_CIA = cCodGrupoCia_in            AND
            COD_LOCAL     = cCodLocal_in               AND
            SEC_USU_LOCAL = NVL(cSecUsuLocal_in,'_');

	 IF vCant>0 THEN
	   RAISE_APPLICATION_ERROR(-20006,'El usuario ya ha sido asignado a una caja');
	 END IF;

	 SELECT IND_CAJA_ABIERTA
	 INTO   vIndCajaAbierta
	 FROM   VTA_CAJA_PAGO
	 WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
            COD_LOCAL     = cCodLocal_in    AND
			NUM_CAJA_PAGO = nNumCajaPago_in;

	 IF vIndCajaAbierta=INDICADOR_SI THEN
	   RAISE_APPLICATION_ERROR(-20008,'No se puede modificar una caja aperturada.');
     END IF;

			    UPDATE VTA_CAJA_PAGO SET FEC_MOD_CAJA_PAGO = SYSDATE, USU_MOD_CAJA_PAGO = cCodUsu_in,
                       SEC_USU_LOCAL     = cSecUsuLocal_in,
                	   DESC_CAJA_PAGO    = cDescCaja_in
                WHERE
               		   COD_GRUPO_CIA = cCodGrupoCia_in AND
                	   COD_LOCAL     = cCodLocal_in    AND
                	   NUM_CAJA_PAGO = nNumCajaPago_in;
              END;
/*****************************************************************************************************************/

PROCEDURE CAJ_CAMBIAESTADO_CAJA(cCodGrupoCia_in IN CHAR,
		  					    cCodLocal_in    IN CHAR,
							    nNumCajaPago_in IN NUMBER,
								cCodUsu_in      IN CHAR
							   )
 IS
  v_est CHAR(1);
  vIndCajaAbierta CHAR(1);
 BEGIN
      SELECT EST_CAJA_PAGO
	  INTO   v_est
	  FROM   VTA_CAJA_PAGO
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
             COD_LOCAL     = cCodLocal_in    AND
             NUM_CAJA_PAGO = nNumCajaPago_in;

      IF   v_est = ESTADO_ACTIVO THEN
           v_est:= ESTADO_INACTIVO;
      ELSE v_est:= ESTADO_ACTIVO;
      END IF;

	  IF v_est=ESTADO_INACTIVO THEN
	    BEGIN
		  SELECT IND_CAJA_ABIERTA INTO vIndCajaAbierta
		  FROM VTA_CAJA_PAGO
		  WHERE COD_GRUPO_CIA  = cCodGrupoCia_in AND
                COD_LOCAL      = cCodLocal_in    AND
				 NUM_CAJA_PAGO = nNumCajaPago_in;

			IF vIndCajaAbierta=INDICADOR_SI THEN
			  RAISE_APPLICATION_ERROR(-20009,'No se puede inactivar una caja aperturada.');
			END IF;
		END;
	  END IF;

      UPDATE VTA_CAJA_PAGO SET FEC_MOD_CAJA_PAGO = SYSDATE,USU_MOD_CAJA_PAGO = cCodUsu_in,
 	         EST_CAJA_PAGO = v_est
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
             COD_LOCAL     = cCodLocal_in    AND
             NUM_CAJA_PAGO = nNumCajaPago_in;
 END;

 /*****************************************************************************************************************/

 PROCEDURE CAJ_INSERTA_ASOCIA_CAJA(cCodGrupoCia_in IN CHAR,
		  					       cCodLocal_in    IN CHAR,
							       nNumCajaPago_in IN NUMBER,
								   cCodUsu_in      IN CHAR
								 )
 IS
   vSecImprLocal NUMBER;
   vDataRow PBL_GRUPO_CIA%ROWTYPE;
   CURSOR vCurSorImpresoras IS
   					  SELECT MIN(SEC_IMPR_LOCAL),
			   			  		 TIP_COMP
						  FROM   VTA_IMPR_LOCAL
						  WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
               AND COD_LOCAL     = cCodLocal_in
               AND TIP_COMP IN (SELECT TIP_COMP
			   			   		 		  	    FROM VTA_TIP_COMP
											          WHERE IND_NECESITA_IMPR = INDICADOR_SI)
               AND TIP_COMP NOT IN (TIPO_DOC_TICKET) --solo para la primera asignacion
               GROUP BY TIP_COMP;

 BEGIN
	 OPEN vCurSorImpresoras;
        LOOP
		     FETCH vCurSorImpresoras
			 INTO vDataRow;
		     EXIT WHEN vCurSorImpresoras%NOTFOUND;

			 INSERT INTO VTA_CAJA_IMPR(COD_GRUPO_CIA,
						               COD_LOCAL,
						  			    NUM_CAJA_PAGO,
						   				SEC_IMPR_LOCAL,
						   				EST_CAJA_IMPR,
						   				FEC_CREA_CAJA_IMPR,
						   				USU_CREA_CAJA_IMPR)
			        VALUES(cCodGrupoCia_in,
				           cCodLocal_in,
				   		   nNumCajaPago_in,
				   		   vDataRow.COD_GRUPO_CIA,
				   		   ESTADO_ACTIVO,
				   		   SYSDATE,
				   		   cCodUsu_in);
        END LOOP;
     CLOSE vCurSorImpresoras;
END;
 /****************************************************************************************************************/

 FUNCTION CAJ_OBTENER_DATOS_USU_LOCAL(cCodGrupoCia_in    IN CHAR,
  		   				              cCodLocal_in	     IN CHAR,
							          cSecUsuLocal_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
		SELECT SEC_USU_LOCAL || 'Ã'  ||
			   APE_PAT       || ' '  ||
			   APE_MAT       || ', ' ||
			   NOM_USU
		FROM   PBL_USU_LOCAL
		WHERE  COD_GRUPO_CIA = cCodGrupoCia_in 			 		 								 AND
		       COD_LOCAL     = cCodLocal_in    													 AND
			   EST_USU       = ESTADO_ACTIVO   													 AND
			   SEC_USU_LOCAL = cSecUsuLocal_in 													 AND
			   COD_GRUPO_CIA || COD_LOCAL || SEC_USU_LOCAL IN (SELECT COD_GRUPO_CIA ||
														  		      COD_LOCAL     ||
																 	  SEC_USU_LOCAL
														  	   FROM   PBL_ROL_USU
														  	   WHERE  COD_ROL = TIPO_ROL_CAJERO) AND
			   COD_GRUPO_CIA || COD_LOCAL || SEC_USU_LOCAL NOT IN (SELECT COD_GRUPO_CIA ||
							  	 		   	  					   		  COD_LOCAL     ||
																		  SEC_USU_LOCAL
																	FROM  VTA_CAJA_PAGO);
    RETURN curVta;
  END;

/*****************************************************************************************************************/

  FUNCTION CAJ_VERIF_EXISTEN_IMPR_DISP(cCodGrupoCia_in IN CHAR,
  		   							   cCodLocal_in    IN CHAR)
  RETURN
  NUMBER
  IS
	   vRpta NUMBER;
	   vCantImpBol NUMBER;
	   vCantImpFac NUMBER;
	   vCantImpGui NUMBER;
 BEGIN
	 vRpta:=1;
	 SELECT COUNT(*) INTO vCantImpBol
	 FROM   VTA_IMPR_LOCAL
	 WHERE  EST_IMPR_LOCAL = ESTADO_ACTIVO    AND
	  	    COD_GRUPO_CIA  = cCodGrupoCia_in  AND
	  	    COD_LOCAL	   = cCodLocal_in	  AND
			tip_comp	   = TIPO_DOC_BOLETA;

	 SELECT COUNT(*) INTO vCantImpFac
	 FROM   VTA_IMPR_LOCAL
	 WHERE  EST_IMPR_LOCAL = ESTADO_ACTIVO    AND
	  	    COD_GRUPO_CIA  = cCodGrupoCia_in  AND
	  	    COD_LOCAL	   = cCodLocal_in	  AND
			tip_comp	   = TIPO_DOC_FACTURA;


	 SELECT COUNT(*) INTO vCantImpGui
	 FROM   VTA_IMPR_LOCAL
	 WHERE  EST_IMPR_LOCAL = ESTADO_ACTIVO    AND
	  	    COD_GRUPO_CIA  = cCodGrupoCia_in  AND
	  	    COD_LOCAL	   = cCodLocal_in	  AND
			tip_comp	   = TIPO_DOC_GUIA;

			IF vCantImpBol=0 THEN
			   vRpta:=0;
			END IF;

			IF vCantImpFac=0 THEN
			   vRpta:=0;
			END IF;

			IF vCantImpGui=0 THEN
			   vRpta:=0;
			END IF;
	 RETURN vRpta;
 END;

 /*****************************************************************************************************************/

 FUNCTION CAJ_LISTA_IMPRESORAS_CAJA(cCodGrupoCia_in IN CHAR,
  		   			   		        cCodLocal_in	IN CHAR,
							        cNumCaja_in     IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
		SELECT IL.SEC_IMPR_LOCAL            || 'Ã' ||
			   IL.DESC_IMPR_LOCAL           || 'Ã' ||
    		   TC.DESC_COMP                 || 'Ã' ||
    		   IL.NUM_SERIE_LOCAL           || 'Ã' ||
    		   IL.NUM_COMP                  || 'Ã' ||
    		   IL.RUTA_IMPR                 || 'Ã' ||
    		   CASE WHEN IL.EST_IMPR_LOCAL=ESTADO_ACTIVO
	     	   THEN 'Activo'
         	   ELSE 'Inactivo'
		 	   END    		 		 		|| 'Ã' ||
			   IL.TIP_COMP

		FROM VTA_IMPR_LOCAL  IL ,
     		 VTA_SERIE_LOCAL SL ,
     		 VTA_TIP_COMP    TC	,
	 		 VTA_CAJA_IMPR   CI
	    WHERE
	  		  CI.NUM_CAJA_PAGO	 = cNumCaja_in
		AND	  CI.COD_LOCAL		 = cCodLocal_in
		AND   CI.COD_GRUPO_CIA	 = cCodGrupoCia_in
		AND   IL.SEC_IMPR_LOCAL	 = CI.SEC_IMPR_LOCAL
		AND   IL.COD_LOCAL       = CI.COD_LOCAL
		AND   IL.COD_GRUPO_CIA   = CI.COD_GRUPO_CIA
		AND   IL.COD_GRUPO_CIA   = CI.COD_GRUPO_CIA
		AND   SL.TIP_COMP        = TC.TIP_COMP
		AND   SL.COD_GRUPO_CIA   = TC.COD_GRUPO_CIA
		AND   IL.TIP_COMP        = SL.TIP_COMP
	    AND   IL.NUM_SERIE_LOCAL = SL.NUM_SERIE_LOCAL
	    AND   IL.COD_LOCAL       = SL.COD_LOCAL
	    AND   IL.COD_GRUPO_CIA   = SL.COD_GRUPO_CIA;
    RETURN curVta;
  END;

/*****************************************************************************************************************/

  FUNCTION CAJ_LISTA_IMPRESORAS_REEMPLAZO(cCodGrupoCia_in  IN CHAR,
                                          cCodLocal_in	   IN CHAR,
                                          cNumCaja_in      IN CHAR,
                                          cTipComp_in 	   IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
		SELECT IL.SEC_IMPR_LOCAL            || 'Ã' ||
			   IL.DESC_IMPR_LOCAL           || 'Ã' ||
    		   TC.DESC_COMP                 || 'Ã' ||
    		   IL.NUM_SERIE_LOCAL           || 'Ã' ||
    		   IL.NUM_COMP                  || 'Ã' ||
    		   IL.RUTA_IMPR                 || 'Ã' ||
    		   CASE WHEN IL.EST_IMPR_LOCAL=ESTADO_ACTIVO
	     	        THEN 'Activo'
         			ELSE 'Inactivo'
		 			END    		 		    || 'Ã' ||
			   IL.TIP_COMP
		FROM VTA_IMPR_LOCAL  IL ,
     		 VTA_TIP_COMP    TC
	    WHERE IL.COD_GRUPO_CIA = cCodGrupoCia_in
      AND IL.COD_LOCAL     = cCodLocal_in
       --IL.TIP_COMP      = cTipComp_in
      AND IL.TIP_COMP     NOT IN  ('04') --menos nota de credito
      AND TC.COD_GRUPO_CIA = IL.COD_GRUPO_CIA
      AND TC.TIP_COMP      = IL.TIP_COMP
      AND IL.SEC_IMPR_LOCAL NOT IN (SELECT SEC_IMPR_LOCAL
                                    FROM VTA_CAJA_IMPR
                                    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND COD_LOCAL     = cCodLocal_in
                                    AND NUM_CAJA_PAGO = TO_NUMBER(cNumCaja_in));
    RETURN curVta;
  END;

 /*****************************************************************************************************************/

  PROCEDURE CAJ_MODIFICA_CAJA_IMPRESORA(cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in      IN CHAR,
                                        nNumCajaPago_in   IN NUMBER,
                                        cSecImprLocal1_in IN CHAR,
                                        cSecImprLocal2_in IN CHAR,
                                        cCodUsu_in        IN CHAR)
   IS
    TipoComp2 VTA_IMPR_LOCAL.TIPO_IMPRESORA%TYPE;
    CANT   NUMBER;
     BEGIN

     SELECT A.TIPO_IMPRESORA INTO TipoComp2
     FROM VTA_IMPR_LOCAL A
     WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
     AND A.COD_LOCAL=cCodLocal_in
     AND A.SEC_IMPR_LOCAL=TRIM(cSecImprLocal2_in);

     SELECT COUNT(*) INTO CANT
     FROM VTA_IMPR_LOCAL X,
          VTA_CAJA_IMPR Y
     WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
     AND X.COD_LOCAL=cCodLocal_in
     AND X.SEC_IMPR_LOCAL<>cSecImprLocal1_in
     AND Y.NUM_CAJA_PAGO=nNumCajaPago_in
     AND X.TIPO_IMPRESORA=TipoComp2
     AND X.COD_GRUPO_CIA=Y.COD_GRUPO_CIA
     AND X.COD_LOCAL=Y.COD_LOCAL
     AND X.SEC_IMPR_LOCAL=Y.SEC_IMPR_LOCAL;


     IF(CANT>0)THEN--no se permite mismo tipo de impresora
          RAISE_APPLICATION_ERROR(-20010,'No se puede asignar este tipo de impresora.');
     ELSE
        UPDATE VTA_CAJA_IMPR
        SET FEC_MOD_CAJA_IMPR = SYSDATE,
            USU_MOD_CAJA_IMPR = cCodUsu_in,
            SEC_IMPR_LOCAL = cSecImprLocal2_in
        WHERE COD_GRUPO_CIA  = cCodGrupoCia_in
        AND COD_LOCAL      = cCodLocal_in
        AND NUM_CAJA_PAGO  = nNumCajaPago_in
        AND SEC_IMPR_LOCAL = cSecImprLocal1_in;
     END IF;

	 END;

  /*****************************************************************************************************************/

 FUNCTION CAJ_CANT_CAJEROS_DISP_LOCAL(cCodGrupoCia_in  IN CHAR,
  		   				       		      cCodLocal_in	   IN CHAR)
 RETURN NUMBER
   IS
       vCant NUMBER;
   BEGIN

		SELECT count(*) into vCant
		FROM   PBL_USU_LOCAL
		WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
		       COD_LOCAL     = cCodLocal_in    AND
			   EST_USU       = ESTADO_ACTIVO   AND
			   COD_GRUPO_CIA || COD_LOCAL || SEC_USU_LOCAL IN (SELECT COD_GRUPO_CIA ||
																  	  COD_LOCAL     ||
																	  SEC_USU_LOCAL
															   FROM   PBL_ROL_USU
															   WHERE  COD_ROL = TIPO_ROL_CAJERO) AND
				COD_GRUPO_CIA || COD_LOCAL || SEC_USU_LOCAL NOT IN (SELECT COD_GRUPO_CIA ||
									  	 		   	  					   COD_LOCAL     ||
																		   SEC_USU_LOCAL
																	FROM   VTA_CAJA_PAGO);
	    RETURN vCant;
   END;
/*****************************************************************************************************************/
 FUNCTION CAJ_OBTIENE_ESTADO_CAJA(cCodGrupoCia_in  IN CHAR,
  		   				       		        cCodLocal_in	   IN CHAR,
                                  nNumCajaPago_in  IN NUMBER)

 RETURN VARCHAR
   IS
  v_Estado VARCHAR2(5);
  BEGIN
   SELECT EST_CAJA_PAGO  INTO v_Estado
	 FROM   VTA_CAJA_PAGO v
	 WHERE  COD_GRUPO_CIA = cCodGrupoCia_in            AND
          COD_LOCAL     = cCodLocal_in               AND
          NUM_CAJA_PAGO = nNumCajaPago_in   ;

  RETURN v_Estado;
 END CAJ_OBTIENE_ESTADO_CAJA;


END;
/

