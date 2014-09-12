--------------------------------------------------------
--  DDL for Package Body PTOVENTA_DEL_CLI
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_DEL_CLI" AS

  FUNCTION CLI_OBTIENE_CLI_NOMB_DNI(cCodGrupoCia_in	IN CHAR,
  		   						   cCodLocal_in		IN CHAR,
  		   						   cTelefono_in     IN CHAR,
								   c_TipoTelefono_in IN CHAR)
 RETURN FarmaCursor
 IS
   curCli FarmaCursor;
  BEGIN
    OPEN curCli FOR
		 SELECT cl.cod_cli_local || '�' ||
				cl.nom_cli ||' '|| cl.ape_pat_cli ||' '|| cl.ape_mat_cli  || '�' ||
				--NVL(cl.num_doc_iden,' ') || '�' ||
				NVL(DECODE(cl.num_doc_iden,cl.cod_cli_local,' ',cl.num_doc_iden),' ') || '�' ||
				md.COD_DIR || '�' ||
				NVL(cl.ape_pat_cli,' ') || '�' ||
				NVL(cl.ape_mat_cli,' ') || '�' ||
				cl.nom_cli
		 FROM   VTA_CLI_LOCAL cl,
				VTA_DIR_CLI dc,
				VTA_MAE_DIR md,
				VTA_MAE_TELEFONO mt
		 WHERE 	cl.cod_grupo_cia = cCodGrupoCia_in AND
			  	cl.cod_local =cCodLocal_in AND
				mt.num_telefono = cTelefono_in AND
				mt.TIP_TELEF = c_TipoTelefono_in AND
				cl.cod_grupo_cia=dc.cod_grupo_cia AND
				cl.COD_LOCAL = dc.cod_local AND
				cl.COD_CLI_LOCAL = dc.cod_cli_local AND
				dc.cod_local= md.cod_local AND
				dc.cod_grupo_cia = md.cod_grupo_cia AND
				dc.cod_dir = md.cod_dir AND
				md.cod_grupo_cia=mt.cod_grupo_cia AND
				md.cod_local = mt.cod_local AND
				md.cod_dir = mt.cod_dir;
	RETURN 	curCli;
  END;

  FUNCTION CLI_OBTIENE_CLI_TELF_DIR(cCodGrupoCia_in	IN CHAR,
  		   						  	cCodLocal_in	IN CHAR,
  		   						    cTelefono_in    IN CHAR,
									c_TipoTelefono_in IN CHAR)
  RETURN FarmaCursor
  IS
   curCli FarmaCursor;
  BEGIN
    OPEN curCli FOR
		 SELECT mt.num_telefono || '�' ||
	   	 		CASE md.tip_calle
				WHEN TIPO_CALLE_AVENIDA THEN 'AV'
				WHEN TIPO_CALLE_JIRON 	THEN 'JR'
				WHEN TIPO_CALLE_CALLE	THEN 'CL'
				WHEN TIPO_CALLE_PASAJE  THEN 'PSJ'
				END
				||' '||md.nom_calle ||' '|| md.num_calle ||' '||
	   	 		md.num_int ||' '||'URB.'||' '|| md.nom_urbaniz || '�' ||
				NVL(md.nom_Distrito,' ') || '�' ||
				md.COD_DIR || '�' ||
				NVL(md.NOM_CALLE,' ') || '�' ||
				NVL(md.num_calle,' ') || '�' ||
				NVL(md.NUM_INT,' ') || '�' ||
				NVL(md.NOM_URBANIZ,' ') || '�' ||
				NVL(md.REF_DIREC,' ') || '�' ||
				CASE md.tip_calle
				WHEN TIPO_CALLE_AVENIDA THEN 'Avenida'
				WHEN TIPO_CALLE_JIRON 	THEN 'Jiron'
				WHEN TIPO_CALLE_CALLE	THEN 'Calle'
				WHEN TIPO_CALLE_PASAJE  THEN 'Pasaje'
				END	 || '�' ||
				md.tip_calle
		 FROM   VTA_MAE_DIR md,
	   	 		VTA_MAE_TELEFONO mt
		 WHERE 	mt.cod_grupo_cia =cCodGrupoCia_in AND
	  	 		mt.cod_local =cCodLocal_in AND
	  			mt.num_telefono = cTelefono_in AND
				mt.TIP_TELEF = c_TipoTelefono_in AND
				md.cod_grupo_cia=mt.cod_grupo_cia AND
	  			md.cod_local = mt.cod_local AND
	  			md.cod_dir = mt.cod_dir;
	RETURN 	curCli;
  END;

  FUNCTION CLI_BUSCA_DNI_APELLIDO_CLI(cCodGrupoCia_in  IN CHAR,
  		   						 	  cCodLocal_in	   IN CHAR,
  		   						 	  cDniApellido_in  IN CHAR,
									  cTipoBusqueda_in IN CHAR)
  RETURN FarmaCursor

  IS
  	curCli FarmaCursor;
  BEGIN
  IF(cTipoBusqueda_in = '1') THEN --DNI
    OPEN curCli FOR
		 SELECT cl.cod_cli_local || '�' ||
	   	 		cl.ape_pat_cli ||' '|| cl.ape_mat_cli ||' '||cl.nom_cli || '�' ||
	   			--cl.num_doc_iden || '�' ||
          NVL(DECODE(cl.num_doc_iden,cl.cod_cli_local,' ',cl.num_doc_iden),' ') || '�' ||
				' ' || '�' ||
				NVL(cl.ape_pat_cli,' ') || '�' ||
				NVL(cl.ape_mat_cli,' ') || '�' ||
				cl.nom_cli
		 FROM   VTA_CLI_LOCAL cl
		 WHERE 	cl.cod_grupo_cia = cCodGrupoCia_in AND
	  	 		cl.cod_local = cCodLocal_in AND
	  			cl.num_doc_iden = cDniApellido_in;
  ELSIF(cTipoBusqueda_in = '2') THEN --APELLIDO
  	OPEN curCli FOR
		 SELECT cl.cod_cli_local || '�' ||
	   	 		cl.ape_pat_cli ||' '|| cl.ape_mat_cli ||' '||cl.nom_cli || '�' ||
	   			--cl.num_doc_iden || '�' ||
          NVL(DECODE(cl.num_doc_iden,cl.cod_cli_local,' ',cl.num_doc_iden),' ') || '�' ||
				' ' || '�' ||
				NVL(cl.ape_pat_cli,' ') || '�' ||
				NVL(cl.ape_mat_cli,' ') || '�' ||
				cl.nom_cli
		 FROM   VTA_CLI_LOCAL cl
		 WHERE 	cl.cod_grupo_cia = cCodGrupoCia_in AND
	  	 		cl.cod_local = cCodLocal_in AND
	  			cl.ape_pat_cli LIKE cDniApellido_in || '%';
  END IF ;
	RETURN curCli;
  END;

  PROCEDURE CLI_AGREGA_DETALLE_DIRECCION(cCodLocal_in IN CHAR,
  		   							    cCodDir_in IN CHAR,
  		   								cCodGrupoCia_in IN CHAR,
										cCodCli_in IN CHAR)
  IS
  BEGIN
  	   INSERT INTO VTA_DIR_CLI (COD_LOCAL,COD_DIR,COD_GRUPO_CIA,COD_CLI_LOCAL)
	   		  	   	    VALUES (cCodLocal_in,cCodDir_in,cCodGrupoCia_in,cCodcli_in);
 END;

 FUNCTION CLI_AGREGA_CLIENTE(cCodGrupoCia_in CHAR,
  		   					  cCodLocal_in CHAR,
							  cCodNumera_in CHAR,
							  cNomCli_in CHAR,
							  cApePatCli_in CHAR,
							  cApeMatCli_in CHAR,
							  cTipDocIdent_in CHAR,
							  cNumDocIdent_in CHAR,
							  cIndCliJur_in CHAR,
							  cUsuCreaCli_in CHAR)
  RETURN CHAR
  IS
  	v_nCodCli	NUMBER;
	v_cCodCli	CHAR(7);
	--v_cResultado	CHAR(1);
  	BEGIN
	   v_nCodCli := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in);
	   v_cCodCli := Farma_Utility.COMPLETAR_CON_SIMBOLO(TO_CHAR(v_nCodCli), 7, '0', 'I');
	   INSERT INTO VTA_CLI_LOCAL (COD_GRUPO_CIA,COD_LOCAL,COD_CLI_LOCAL,NOM_CLI,APE_PAT_CLI,APE_MAT_CLI,
	   		  	   				 TIP_DOC_IDENT,NUM_DOC_IDEN,IND_CLI_JUR,USU_CREA_CLI_LOCAL)
						   VALUES(cCodGrupoCia_in,cCodLocal_in,v_cCodCli,cNomCli_in,cApePatCli_in,cApeMatCli_in,
						   		  cTipDocIdent_in,NVL(cNumDocIdent_in,v_cCodCli),cIndCliJur_in,cUsuCreaCli_in);
	   Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,cCodNumera_in,cUsuCreaCli_in);
	   --v_cResultado := '1';--grabo corectamente
	   RETURN v_cCodCli;
 END;

 FUNCTION CLI_AGREGA_DIRECCION_CLIENTE(cCodGrupoCia_in CHAR,
  		   							   cCodLocal_in	   CHAR,
									   cCodNumera_in		CHAR,
									   cTipCalle_in		CHAR,
									   cNomCalle_in		CHAR,
									   cNumCalle_in		CHAR,
									   cNomUrb_in	CHAR,
									   cNomDistrito_in		CHAR,
									   cNumInt_in			CHAR,
									   cRefDirec_in		CHAR,
									   cUsuCreaDir_in		CHAR)
  RETURN CHAR
  IS
  	v_nCodDir	NUMBER;
	v_cCodDir	CHAR(10);
	--v_cResultado CHAR(1);
	BEGIN
		v_nCodDir := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in);
	   	v_cCodDir := Farma_Utility.COMPLETAR_CON_SIMBOLO(TO_CHAR(v_nCodDir), 10, '0', 'I');
		INSERT INTO VTA_MAE_DIR (COD_GRUPO_CIA,COD_LOCAL,COD_DIR,TIP_CALLE,NOM_CALLE,NUM_CALLE,NOM_URBANIZ,
			   					 NOM_DISTRITO,NUM_INT,REF_DIREC,USU_CREA_MAE_DIR)
						VALUES	(cCodGrupoCia_in,cCodLocal_in,v_cCodDir,cTipCalle_in,cNomCalle_in,cNumCalle_in,
								 cNomUrb_in,cNomDistrito_in,cNumInt_in,cRefDirec_in,cUsuCreaDir_in);
		Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,cCodNumera_in,cUsuCreaDir_in);
		RETURN v_cCodDir;
 END;

 FUNCTION CLI_AGREGA_TELEFONO_CLIENTE(cCodGrupoCia_in CHAR,
  		   							   cCodLocal_in	   CHAR,
									   cCodDir_in	   CHAR,
									   cCodNumera_in	   CHAR,
									   cNumTelefono_in	   CHAR,
									   cTipTelefono_in	   CHAR,
									   cUsuCreaTel_in	   CHAR)
  RETURN CHAR
  IS
    v_nCodTel	NUMBER;
	v_cCodTel	CHAR(10);
	--v_cResultado CHAR(1);
	BEGIN
		v_nCodTel := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in);
	   	v_cCodTel := Farma_Utility.COMPLETAR_CON_SIMBOLO(TO_CHAR(v_nCodTel), 10, '0', 'I');
		INSERT INTO VTA_MAE_TELEFONO(COD_GRUPO_CIA,COD_LOCAL,COD_DIR,SEC_TELEF,NUM_TELEFONO,TIP_TELEF,USU_CREA_MAE_TEL,IND_TELEF_LIMA)
			   		VALUES		    (cCodGrupoCia_in,cCodLocal_in,cCodDir_in,v_cCodTel,cNumTelefono_in,cTipTelefono_in,cUsuCreaTel_in,'S');
		Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,cCodNumera_in,cUsuCreaTel_in);
	RETURN v_cCodTel;
 END;

 FUNCTION cli_actualiza_cliente(cCodGrupoCia_in CHAR,
 		  						cCodLocal_in CHAR,
								cCodCliLocal_in CHAR,
								cNomCli_in CHAR,
								cApePatCli_in CHAR,
								cApeMatCli_in CHAR,
								cNumDocCli_in	  CHAR,
								cUsuModCli_in	  CHAR)
 RETURN CHAR
 IS
 v_cResultado	CHAR(1);
 BEGIN
 UPDATE VTA_CLI_LOCAL
 SET    NOM_CLI = cNomCli_in,
			  APE_PAT_CLI = cApePatCli_in,
			  APE_MAT_CLI = cApeMatCli_in,
			  NUM_DOC_IDEN = cNumDocCli_in,
			  FEC_MOD_CLI_LOCAL = SYSDATE,
			  USU_MOD_CLI_LOCAL = cUsuModCli_in
	   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
	   AND	  COD_LOCAL = cCodLocal_in
	   AND	  COD_CLI_LOCAL = cCodCliLocal_in;
	   v_cResultado := '1';
 RETURN v_cResultado;
 END;

 FUNCTION cli_actualiza_direccion (cCodGrupoCia_in CHAR,
 		  						  cCodLocal_in CHAR,
								  cCodDir_in CHAR,
								  cTipCalle_in	CHAR,
								  cNomCalle_in	CHAR,
								  cNumCalle_in	CHAR,
								  cNomUrb_in	CHAR,
								  cNomDistrito_in	CHAR,
								  cNumInt_in	CHAR,
								  cRefDirec_in	CHAR,
								  cUsuModDir_in	CHAR)
  RETURN CHAR
  IS
  v_cResultado	CHAR(1);
  BEGIN
  UPDATE VTA_MAE_DIR
  		 SET tip_calle = cTipCalle_in,
		 	 nom_calle = cNomCalle_in,
			 num_calle = cNumCalle_in,
			 nom_urbaniz = cNomUrb_in,
			 nom_distrito = cNomDistrito_in,
			 num_int = cNumInt_in,
			 ref_direc = cRefDirec_in,
			 usu_Mod_mae_dir = cUsuModDir_in,
			 fec_mod_mae_dir = SYSDATE
			 WHERE
			 cod_grupo_cia = cCodGrupoCia_in AND
			 cod_local = cCodLocal_in AND
			 cod_dir = cCodDir_in;
			 v_cResultado := '1';
 RETURN v_cResultado;
 END;

 PROCEDURE cli_graba_datos_cli(cCodGrupoCia_in CHAR,
  		   							         cCodLocal_in	   CHAR,
									             --cCodDir_in	   CHAR,
									             cCodNumeraCli_in	   CHAR,
                               cCodNumeraDir_in	   CHAR,
                               cCodNumeraTel_in    CHAR,
									             cNumTelefono_in	   CHAR,
									             cTipTelefono_in	   CHAR,
									             cUsuCreaTel_in	   CHAR,
                               cTipCalle_in		CHAR,
                               cNomCalle_in		CHAR,
									             cNumCalle_in		CHAR,
									             cNomUrb_in	CHAR,
									             cNomDistrito_in		CHAR,
									             cNumInt_in			CHAR,
									             cRefDirec_in		CHAR,
									             cUsuCreaDir_in		CHAR,
                               cNomCli_in CHAR,
							                 cApePatCli_in CHAR,
							                 cApeMatCli_in CHAR,
							                 cTipDocIdent_in CHAR,
							                 cNumDocIdent_in CHAR,
							                 cIndCliJur_in CHAR,
							                 cUsuCreaCli_in CHAR)
                						   --cCodCli_in IN CHAR)
   IS
   v_ResultCli CHAR (7) ;
   v_ResultDirec CHAR (10);
   v_ResultTel CHAR (10) ;
   v_Retorno_cli CHAR(7);
   v_Retorno_tel CHAR(10);
   v_Retorno_dir CHAR(10);
   BEGIN
   v_retorno_cli := cli_valida_cli_repetido(cCodGrupoCia_in,cCodLocal_in,cNumDocIdent_in,cNomCli_in,cApePatCli_in,cApeMatCli_in);
   v_retorno_tel := cli_valida_num_tel(cCodGrupoCia_in,cCodLocal_in,cNumTelefono_in);
   v_retorno_dir := cli_valida_direccion(cCodGrupoCia_in,cCodLocal_in,cNomCalle_in,cNumCalle_in,cNomDistrito_in,cNumTelefono_in);

   dbms_output.put_line('v_retorno_cli ' || v_retorno_cli);
   dbms_output.put_line('v_retorno_tel ' || v_retorno_tel);
   dbms_output.put_line('v_retorno_dir ' || v_retorno_dir);

     IF v_retorno_cli = 'TRUE' THEN
       v_ResultCli:= cli_agrega_cliente(cCodGrupoCia_in,cCodLocal_in,cCodNumeraCli_in,cNomCli_in,cApePatCli_in,cApeMatCli_in,cTipDocIdent_in,cNumDocIdent_in,cIndCliJur_in,cUsuCreaCli_in);
       dbms_output.put_line('v_ResultCli ' || v_ResultCli);
       IF (v_ResultCli = '1' ) THEN
          RAISE_APPLICATION_ERROR(-2000, 'Ocurrio un error al agregar el cliente');
       END IF;
     END IF ;
     IF v_retorno_dir ='TRUE' THEN
       v_ResultDirec:= cli_agrega_direccion_cliente(cCodGrupoCia_in,cCodLocal_in,cCodNumeraDir_in,cTipCalle_in,cNomCalle_in,cNumCalle_in,cNomUrb_in,cNomDistrito_in,cNumInt_in,cRefDirec_in,cUsuCreaDir_in);
       dbms_output.put_line('v_ResultDirec ' || v_ResultDirec);
       IF (v_ResultDirec = '1') THEN
          RAISE_APPLICATION_ERROR(-2001, 'Ocurrio un error al agregar la direccion del cliente');
       END IF;
     END IF;
     IF v_retorno_tel = 'TRUE' THEN
       v_ResultTel:= cli_agrega_telefono_cliente(cCodGrupoCia_in,cCodLocal_in,v_ResultDirec,cCodNumeraTel_in,cNumTelefono_in,cTipTelefono_in,cUsuCreaTel_in);
       dbms_output.put_line('v_ResultTel ' || v_ResultTel);
       IF (v_ResultTel = '1' ) THEN
          RAISE_APPLICATION_ERROR(-2002, 'Ocurrio un error al agregar el telefono');
       END IF;
     END IF;
     IF v_Resultdirec IS NOT NULL AND v_resultcli IS NOT NULL THEN
      cli_agrega_detalle_direccion(cCodLocal_in,v_ResultDirec,cCodGrupoCia_in,v_ResultCli);
      ELSE
      cli_agrega_detalle_direccion(cCodLocal_in,v_retorno_dir,cCodGrupoCia_in,v_retorno_cli);
     END IF;

   END;

   FUNCTION cli_valida_cli_repetido ( cCodGrupoCia_in  CHAR,
  		   							                cCodLocal_in	   CHAR,
                                      cNumDocIdent_in  CHAR,
                                      cNomCli_in       CHAR,
                                      cApePatCli_in    CHAR,
                                      cApeMatCli_in    CHAR)
   RETURN CHAR
   IS
   v_nCountDNI CHAR(1);
   v_nCountNom CHAR(1);
   v_cCodCli   CHAR(7);
   BEGIN
        IF trim(cNumDocIdent_in) IS NOT NULL THEN
           SELECT COUNT(*),cl.cod_cli_local INTO v_Ncountdni,v_ccodcli
           FROM vta_cli_local cl
           WHERE cl.cod_grupo_cia = cCodGrupoCia_in
           AND   cl.cod_local = Ccodlocal_In
           AND   cl.num_doc_iden = Cnumdocident_In
           GROUP BY cl.cod_cli_local;
        ELSE
           SELECT COUNT(*),cl.cod_cli_local INTO v_ncountnom,v_ccodcli
           FROM vta_cli_local cl
           WHERE cl.cod_grupo_cia = cCodGrupoCia_in
           AND   cl.cod_local = Ccodlocal_In
           AND   cl.nom_cli = Cnomcli_In
           AND   cl.ape_pat_cli = capepatcli_in
           AND   cl.ape_mat_cli = capematcli_in
           GROUP BY cl.cod_cli_local;
        END IF;
    RETURN v_ccodcli;--Codigo del Cliente
        EXCEPTION
          WHEN No_data_found THEN
             RETURN 'TRUE'; -- NO EXISTE
        /*IF v_ncountdni = '0' THEN
           RETURN 'TRUE'; -- NO EXISTE
        END IF ;
        IF v_ncountnom = '0' THEN
           RETURN 'TRUE'; -- NO EXISTE
        END IF;*/


   END;

   FUNCTION cli_valida_num_tel(cCodGrupoCia_in  CHAR,
  		   							         cCodLocal_in	   CHAR,
                               cNumTelf_in  CHAR)
   RETURN CHAR
   IS
   v_nCountNom CHAR(1);
   BEGIN
         SELECT COUNT(*) INTO v_ncountnom
         FROM vta_mae_telefono mt
         WHERE mt.cod_grupo_cia = Ccodgrupocia_In
         AND   mt.cod_local = ccodlocal_in
         AND   mt.num_telefono = Cnumtelf_In;

         IF v_ncountnom = '0' THEN
         --EXCEPTION
          --WHEN No_data_found THEN
            RETURN 'TRUE'; -- NO EXISTE
         END IF ;
   RETURN 'FALSE';
   END;

   FUNCTION cli_valida_direccion(cCodGrupoCia_in CHAR,
                                 cCodLocal_in    CHAR,
                                 cNombCalle_in   CHAR,
                                 cNumCalle_in    CHAR,
                                 cNomDist_in     CHAR,
                                 cNumTelf_in     CHAR)
   RETURN CHAR
   IS
   v_nCountNom CHAR(1);
   v_cCodDir   CHAR(10);
   BEGIN
        SELECT COUNT(*),md.cod_dir INTO v_ncountnom,v_cCodDir
        FROM vta_mae_dir md,vta_dir_cli dc, vta_mae_telefono mt
        WHERE md.cod_grupo_cia = cCodGrupoCia_in
        AND   md.cod_local = cCodLocal_in
        AND   md.nom_calle = cNombCalle_in
        AND   md.num_calle = cNumCalle_in
        AND   md.nom_distrito = cNomDist_in
        AND   MT.NUM_TELEFONO = cNumTelf_in
        AND   dc.cod_grupo_cia = md.cod_grupo_cia
        AND   dc.cod_local = md.cod_local
        AND   dc.cod_dir = md.cod_dir
        AND   dc.cod_grupo_cia = mt.cod_grupo_cia
        AND   dc.cod_local = mt.cod_local
        AND   dc.cod_dir= mt.Cod_Dir
        GROUP BY md.cod_dir;
   RETURN v_cCodDir;
--      IF v_ncountnom = '0' THEN
        EXCEPTION
          WHEN No_data_found THEN
           RETURN 'TRUE'; -- NO EXISTE
        --END IF ;

  END;

  FUNCTION CLI_LISTA_CAB_PEDIDOS(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR)
    RETURN FarmaCursor
  IS
    curCli FarmaCursor;
  BEGIN
    OPEN curCli FOR
          SELECT LOC.DESC_CORTA_LOCAL || '�' ||
                 NUM_PED_VTA || '�' ||
          		   TO_CHAR(FEC_RUTEO_PED_VTA_CAB,'dd/MM/yyyy HH24:mi:ss') || '�' ||
          			 TO_CHAR(VAL_NETO_PED_VTA,'999,990.00')  || '�' ||
          			 DECODE(EST_PED_VTA,EST_PED_PENDIENTE,'PENDIENTE',EST_PED_COBRADO,'COBRADO',EST_PED_ANULADO,'ANULADO',' ')|| '�' ||
          			 DECODE(Tip_Comp_Pago,'01','BOLETA','FACTURA')|| '�' ||
                 NVL(NOM_CLI_PED_VTA,' ')|| '�' ||
                 NVL(DIR_CLI_PED_VTA,' ')|| '�' ||
                 nvl(num_telefono,' ') || '�' ||
                 TMP_CAB.COD_LOCAL || '�' ||
                 TO_CHAR(FEC_RUTEO_PED_VTA_CAB,'yyyyMMdd HH24:mi:ss')|| '�' ||                 
                 NVL(IND_CONV_BTL_MF,'N')|| '�' ||  
                 COD_CONVENIO
          FROM   TMP_VTA_PEDIDO_VTA_CAB TMP_CAB,
                 PBL_LOCAL LOC
          WHERE  TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
          --AND	   TMP_CAB.COD_LOCAL = LOCAL_DELIVERY
          AND    TMP_CAB.COD_LOCAL_ATENCION = Ccodlocal_In
          AND    TMP_CAB.EST_PED_VTA = EST_PED_PENDIENTE
          AND    TMP_CAB.NUM_PED_VTA_ORIGEN IS NULL
          AND    TMP_CAB.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
          AND    TMP_CAB.COD_LOCAL = LOC.COD_LOCAL;
    RETURN CURCLI;
 END;

  FUNCTION CLI_LISTA_DETALLE_PEDIDOS(cCodGrupoCia_in     IN CHAR,
                                    cCodLocal_in         IN CHAR,
                                    cCodLocalAtencion_in IN CHAR,
                                    cNumPedido_in        IN CHAR)
    RETURN FarmaCursor
  IS
    curCli FarmaCursor;
  BEGIN
    OPEN curCli FOR
          SELECT TVTA_DET.COD_PROD|| '�' ||
          	     NVL(PROD.DESC_PROD,' ')|| '�' ||
          	     NVL(DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA),' ')|| '�' ||
          	     TO_CHAR(TVTA_DET.VAL_PREC_VTA,'999,990.000') || '�' ||
          	     --NVL(TVTA_DET.CANT_ATENDIDA,0) || '�' ||
                 TO_CHAR(((TVTA_DET.CANT_ATENDIDA*PROD_LOCAL.VAL_FRAC_LOCAL)/TVTA_DET.VAL_FRAC),'999990.00') || '�' ||
          	     TO_CHAR(NVL(TVTA_DET.VAL_PREC_TOTAL,0),'999,990.000') || '�' ||
                 NVL(LAB.NOM_LAB,' ') || '�' ||
                 NVL(PROD_LOCAL.Stk_Fisico, 0) || '�' ||
                 MOD((TVTA_DET.CANT_ATENDIDA) *(PROD_LOCAL.VAL_FRAC_LOCAL) , TVTA_DET.VAL_FRAC)
          FROM   TMP_VTA_PEDIDO_VTA_DET TVTA_DET,
          	     LGT_PROD_LOCAL PROD_LOCAL,
          	     LGT_PROD PROD,
          	     LGT_LAB LAB,
                 TMP_VTA_PEDIDO_VTA_CAB TVTA_CAB
          WHERE  TVTA_DET.COD_GRUPO_CIA = ccodgrupocia_in
          AND	   TVTA_DET.COD_LOCAL = cCodLocal_in
          AND	   TVTA_DET.NUM_PED_VTA = cnumpedido_in
          AND    TVTA_CAB.COD_LOCAL_ATENCION = cCodLocalAtencion_in
          AND    TVTA_CAB.COD_GRUPO_CIA = TVTA_DET.COD_GRUPO_CIA
          AND    TVTA_CAB.COD_LOCAL = TVTA_DET.COD_LOCAL
          AND    TVTA_CAB.NUM_PED_VTA = TVTA_DET.NUM_PED_VTA
          AND	   TVTA_CAB.COD_LOCAL_ATENCION = PROD_LOCAL.COD_LOCAL
          AND    TVTA_CAB.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND    TVTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   TVTA_DET.COD_PROD = PROD_LOCAL.COD_PROD
          AND	   PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
          AND	   PROD_LOCAL.COD_PROD = PROD.COD_PROD
          AND	   TVTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   PROD.COD_LAB = LAB.COD_LAB;
    RETURN curcli;
 END;

  /* ********************************************************************************************** */

  PROCEDURE CLI_GENERA_PEDIDO_DA(cCodGrupoCia_in 	    IN CHAR,
						   	                 cCodLocal_in    	    IN CHAR,
                                 cCodLocalAtencion_in IN CHAR,
							                   cNumPedVtaDel_in 	  IN CHAR,
                                 cNuevoNumPedVta_in   IN CHAR,
                                 cNumPedDiario_in 	  IN CHAR,
                                 cIPPedido_in         IN CHAR,
                                 cSecUsuVen_in        IN CHAR,
							                   cUsuCreaPedVta_in    IN CHAR,
                                 cNumCaja_in          IN CHAR)
  IS
    --v_cNuevoNumeroPed	 VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
	  --v_cNumeroPedDiario VTA_PEDIDO_VTA_CAB.NUM_PED_DIARIO%TYPE;
    v_cIndGraboCab	   CHAR(1);
    v_cIndGraboDet	   CHAR(1);
    v_cIndEstado CHAR(1);
    vTip_Comprobante     CHAR(2);
    cIP                  VARCHAR(20);

    --JCORTEZ 19.10.09
    CCOD_GRUPO_REP CHAR(3);
    CCOD_GRUPO_REP_EDMUNDO CHAR(3);

	CURSOR infoCabeceraPedido IS
		   	SELECT *
				FROM   TMP_VTA_PEDIDO_VTA_CAB TMP_CAB
				WHERE  TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
				AND	   TMP_CAB.COD_LOCAL = cCodLocal_in
				AND	   TMP_CAB.NUM_PED_VTA = cNumPedVtaDel_in
        AND    TMP_CAB.EST_PED_VTA = EST_PED_PENDIENTE;
  CURSOR infoDetallePedido IS
		   	SELECT TMP_DET.COD_GRUPO_CIA,TMP_DET.COD_LOCAL,TMP_DET.NUM_PED_VTA,TMP_DET.SEC_PED_VTA_DET,
               TMP_DET.COD_PROD,((TMP_DET.CANT_ATENDIDA*PL.VAL_FRAC_LOCAL)/TMP_DET.VAL_FRAC) CANT_ATENDIDA,
               TO_NUMBER(TO_CHAR(TMP_DET.VAL_PREC_TOTAL / ((TMP_DET.CANT_ATENDIDA*PL.VAL_FRAC_LOCAL)/TMP_DET.VAL_FRAC),'999,990.000'),'999,990.000') VAL_PREC_VTA,TMP_DET.VAL_PREC_TOTAL,
               TMP_DET.PORC_DCTO_1,TMP_DET.PORC_DCTO_2,TMP_DET.PORC_DCTO_3,TMP_DET.PORC_DCTO_TOTAL,
               TMP_DET.EST_PED_VTA_DET,TMP_DET.VAL_TOTAL_BONO,PL.VAL_FRAC_LOCAL,TMP_DET.SEC_COMP_PAGO,
               TMP_DET.SEC_USU_LOCAL,TMP_DET.USU_CREA_PED_VTA_DET,TMP_DET.FEC_CREA_PED_VTA_DET,
               TMP_DET.USU_MOD_PED_VTA_DET,TMP_DET.FEC_MOD_PED_VTA_DET,
               TO_NUMBER(TO_CHAR(TMP_DET.VAL_PREC_LISTA /PL.VAL_FRAC_LOCAL,'999,990.000'),'999,990.000') VAL_PREC_LISTA,
               --TMP_DET.VAL_PREC_LISTA,
               TMP_DET.VAL_IGV,
               DECODE(PL.IND_PROD_FRACCIONADO,'N',P.DESC_UNID_PRESENT,PL.UNID_VTA) UNID_VTA,
               TMP_DET.IND_EXONERADO_IGV,TMP_DET.SEC_GRUPO_IMPR,
               TMP_DET.CANT_USADA_NC,TMP_DET.SEC_COMP_PAGO_ORIGEN,TMP_DET.VAL_PREC_PUBLIC,
               TMP_DET.COD_PROM ,--JCORTEZ 28/03/2008
               P.IND_ZAN, --DUBILLUZ 25/11/2008
               P.PORC_ZAN, -- 2009-11-09 JOLIVA
               --JCORTEZ 11.11.09
               NVL(P.COD_GRUPO_REP,' ') COD_GRUPO_REP,
               NVL(P.COD_GRUPO_REP_EDMUNDO,' ')  COD_GRUPO_REP_EDMUNDO
				FROM   TMP_VTA_PEDIDO_VTA_DET TMP_DET,
               LGT_PROD_LOCAL PL,
               LGT_PROD P
				WHERE  TMP_DET.COD_GRUPO_CIA = cCodGrupoCia_in
				AND	   TMP_DET.COD_LOCAL = cCodLocal_in
				AND	   TMP_DET.NUM_PED_VTA = cNumPedVtaDel_in
        AND    TMP_DET.EST_PED_VTA_DET = ESTADO_ACTIVO
        AND    P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
        AND    P.COD_PROD = PL.COD_PROD
        AND    PL.COD_GRUPO_CIA = TMP_DET.COD_GRUPO_CIA
				AND	   PL.COD_LOCAL = cCodLocalAtencion_in
        AND    PL.COD_PROD = TMP_DET.COD_PROD;
  CURSOR infoPedidoConv IS
         SELECT *
         FROM   TMP_CON_PED_VTA_CLI TMP_CON_PED
         WHERE  TMP_CON_PED.COD_GRUPO_CIA  = cCodGrupoCia_in
         AND    TMP_CON_PED.COD_LOCAL      = cCodLocalAtencion_in
         AND    TMP_CON_PED.NUM_PED_VTA    = cNumPedVtaDel_in;
   --SE MODIFICO PARA AGRUPAR LAS PROMOCIONES
   --19/11/2007 DUBILLUZ MODIFICACION
  CURSOR infoDetallePedidoRespaldo IS
		   	SELECT TMP_DET.COD_PROD,
               SUM(((TMP_DET.CANT_ATENDIDA*PL.VAL_FRAC_LOCAL)/TMP_DET.VAL_FRAC)) CANT_ATENDIDA,
               PL.VAL_FRAC_LOCAL
				FROM   TMP_VTA_PEDIDO_VTA_DET TMP_DET,
               LGT_PROD_LOCAL PL,
               LGT_PROD P
				WHERE  TMP_DET.COD_GRUPO_CIA = cCodGrupoCia_in
				AND	   TMP_DET.COD_LOCAL = cCodLocal_in
				AND	   TMP_DET.NUM_PED_VTA = cNumPedVtaDel_in
        AND    TMP_DET.EST_PED_VTA_DET = ESTADO_ACTIVO
        AND    P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
        AND    P.COD_PROD = PL.COD_PROD
        AND    PL.COD_GRUPO_CIA = TMP_DET.COD_GRUPO_CIA
				AND	   PL.COD_LOCAL = cCodLocalAtencion_in
        AND    PL.COD_PROD = TMP_DET.COD_PROD
        GROUP BY TMP_DET.COD_PROD, PL.VAL_FRAC_LOCAL;
        
 CURSOR infoDetalleDatosAdic is
        SELECT * FROM TMP_CON_BTL_MF_PED_VTA  
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
				AND	   COD_LOCAL = cCodLocal_in
				AND	   NUM_PED_VTA = cNumPedVtaDel_in;

        SEC_RESPALDO_STK_in	NUMBER(10);

  BEGIN
       v_cIndGraboCab := INDICADOR_NO;
       v_cIndGraboDet := INDICADOR_NO;


       --v_cNuevoNumeroPed := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, NUMERA_PEDIDO_VTA);
       --v_cNuevoNumeroPed := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_cNuevoNumeroPed, 10, 0, POS_INICIO);
  	   --v_cNumeroPedDiario := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, NUMERA_PEDIDO_DIARIO);
       --v_cNumeroPedDiario := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_cNumeroPedDiario, 4, 0, POS_INICIO);
	     --dbms_output.put_line('v_cIndGraboComp: ' || v_cIndGraboComp );
     	   FOR cabecera_rec IN infoCabeceraPedido
  	     LOOP

       --JCORTEZ 03.04.2009 Se valida tipo de comprobante por caja
       --vTip_Comprobante:=PTOVENTA_CAJ.CAJ_GET_TIPO_COMPR(cCodGrupoCia_in,cCodLocal_in,cNumCaja_in,cabecera_rec.TIP_COMP_PAGO);
       --JCORTEZ 10.06.2009 Se valida la impresora relacionada a la IP
        IF(TRIM(cabecera_rec.TIP_COMP_PAGO)=COD_TIP_COMP_BOLETA)THEN
          SELECT SYS_CONTEXT('USERENV','IP_ADDRESS') INTO cIP FROM DUAL;
          vTip_Comprobante:=PTOVENTA_ADMIN_IMP.IMP_GET_TIPCOMP_IP(cCodGrupoCia_in,cCodLocal_in,cIP,cabecera_rec.TIP_COMP_PAGO);
        ELSE
          vTip_Comprobante:=cabecera_rec.TIP_COMP_PAGO;
        END IF;

  	  	     INSERT INTO VTA_PEDIDO_VTA_CAB
                         (COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, FEC_PED_VTA,
                          VAL_BRUTO_PED_VTA, VAL_NETO_PED_VTA, VAL_REDONDEO_PED_VTA,
                          VAL_IGV_PED_VTA, VAL_DCTO_PED_VTA,
                          TIP_PED_VTA,
                          VAL_TIP_CAMBIO_PED_VTA, NUM_PED_DIARIO, CANT_ITEMS_PED_VTA,
                          EST_PED_VTA, TIP_COMP_PAGO, NOM_CLI_PED_VTA, DIR_CLI_PED_VTA,
                          RUC_CLI_PED_VTA, USU_CREA_PED_VTA_CAB, FEC_CREA_PED_VTA_CAB,
                          OBS_FORMA_PAGO, OBS_PED_VTA, NUM_TELEFONO, IND_DELIV_AUTOMATICO,
                          IND_CONV_ENTEROS, IND_PED_CONVENIO,COD_CONVENIO,
                        --a�adido
                        --dubilluz 11.07.2007
                          NUM_PEDIDO_DELIVERY,COD_LOCAL_PROCEDENCIA,
                          --JMIRANDA 15.12.09
                          PUNTO_LLEGADA,
                          IND_CONV_BTL_MF,
                          Cod_Cli_Conv,
                          name_pc_cob_ped,
                          ip_cob_ped,
                          dni_usu_local
                          )
  							   VALUES(cCodGrupoCia_in, cCodLocalAtencion_in, cNuevoNumPedVta_in, SYSDATE,
  							 		      cabecera_rec.VAL_BRUTO_PED_VTA, cabecera_rec.VAL_NETO_PED_VTA, cabecera_rec.VAL_REDONDEO_PED_VTA,
  							 		      cabecera_rec.VAL_IGV_PED_VTA, cabecera_rec.VAL_DCTO_PED_VTA,
                          DECODE(cCodLocal_in, LOCAL_INSTITUCIONAL, TIP_PEDIDO_INSTITUCIONAL, cabecera_rec.TIP_PED_VTA),
  									      cabecera_rec.VAL_TIP_CAMBIO_PED_VTA, cNumPedDiario_in, cabecera_rec.CANT_ITEMS_PED_VTA,
  									      cabecera_rec.EST_PED_VTA, vTip_Comprobante, cabecera_rec.NOM_CLI_PED_VTA, cabecera_rec.DIR_CLI_PED_VTA,
  									      cabecera_rec.RUC_CLI_PED_VTA, cUsuCreaPedVta_in, SYSDATE,
  									      cabecera_rec.OBS_FORMA_PAGO, cabecera_rec.OBS_PED_VTA, cabecera_rec.NUM_TELEFONO, INDICADOR_SI,
                          cabecera_rec.IND_CONV_ENTEROS, cabecera_rec.ind_ped_convenio,cabecera_rec.COD_CONVENIO,
                        --a�adido
                        --dubilluz 11.07.2007
                          cabecera_rec.num_pedido_delivery,cabecera_rec.cod_local_procedencia,
                           --JMIRANDA 15.12.09
                          cabecera_rec.punto_llegada,
                          cabecera_rec.IND_CONV_BTL_MF,
                          cabecera_rec.Cod_Cli_Conv,
                          cabecera_rec.name_pc_cob_ped,
                          cabecera_rec.ip_cob_ped,
                          cabecera_rec.dni_usu_local);

             UPDATE TMP_VTA_PEDIDO_VTA_CAB
             SET    NUM_PED_VTA_ORIGEN = cNuevoNumPedVta_in
                    --Modificando el Estado a Generado
                    --DUBILLUZ 24/08/2007
                    ,EST_PED_VTA =   ESTADO_GENERADO
  				   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
  				   AND	  COD_LOCAL = cCodLocal_in
  				   AND	  NUM_PED_VTA = cNumPedVtaDel_in;

             v_cIndGraboCab := INDICADOR_SI;
         END LOOP;
         FOR detalle_rec IN infoDetallePedido
  	     LOOP

           /*   --JCORTEZ 19.10.09  // se cambios por consulta ya hecha en el detalle
              SELECT NVL(X.COD_GRUPO_REP,' '),NVL(X.COD_GRUPO_REP_EDMUNDO,' ')
              INTO CCOD_GRUPO_REP,CCOD_GRUPO_REP
              FROM LGT_PROD X
              WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
              AND X.COD_PROD=detalle_rec.COD_PROD;
      */


       SEC_RESPALDO_STK_in := 0;
       /*
       ptovta_respaldo_stk.pvta_f_ins_del_upd_stk_res(cCodGrupoCia_in,
                                                            cCodLocalAtencion_in,
                                                            detalle_rec.COD_PROD,
                                                            detalle_rec.CANT_ATENDIDA,
                                                            detalle_rec.VAL_FRAC_LOCAL,
                                                            '',
                                                            'V', --Venta
                                                            cUsuCreaPedVta_in
                                                            );*/


  	  	     INSERT INTO VTA_PEDIDO_VTA_DET
                         (COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, SEC_PED_VTA_DET,
                          COD_PROD, CANT_ATENDIDA, VAL_PREC_VTA, VAL_PREC_TOTAL,
                          PORC_DCTO_1, PORC_DCTO_2, PORC_DCTO_3, PORC_DCTO_TOTAL,
                          EST_PED_VTA_DET, VAL_TOTAL_BONO, VAL_FRAC,
                          SEC_USU_LOCAL, USU_CREA_PED_VTA_DET, FEC_CREA_PED_VTA_DET,
                          VAL_PREC_LISTA, VAL_IGV, UNID_VTA,
                          IND_EXONERADO_IGV, CANT_USADA_NC,VAL_PREC_PUBLIC,
                          COD_PROM,----JCORTEZ 28/03/2008
                          VAL_FRAC_LOCAL, --ERIOS 10/06/2008
                          CANT_FRAC_LOCAL,
                          IND_ZAN,
                          COD_GRUPO_REP,COD_GRUPO_REP_EDMUNDO, --JCORTEZ 19.10.09
                          PORC_ZAN  ,      -- 2009-11-09 JOLIVA,
                          SEC_RESPALDO_STK
                          )
  			 			    VALUES(cCodGrupoCia_in, cCodLocalAtencion_in, cNuevoNumPedVta_in, detalle_rec.SEC_PED_VTA_DET,
  			 			 		       detalle_rec.COD_PROD, detalle_rec.CANT_ATENDIDA, detalle_rec.VAL_PREC_VTA, round(detalle_rec.VAL_PREC_TOTAL,2), --paulo
  			 			 		       detalle_rec.PORC_DCTO_1, detalle_rec.PORC_DCTO_2, detalle_rec.PORC_DCTO_3, detalle_rec.PORC_DCTO_TOTAL,
  			 					       detalle_rec.EST_PED_VTA_DET, detalle_rec.VAL_TOTAL_BONO, detalle_rec.VAL_FRAC_LOCAL,
  			 					       cSecUsuVen_in, cUsuCreaPedVta_in, SYSDATE,
  			 					       detalle_rec.VAL_PREC_LISTA, detalle_rec.VAL_IGV, detalle_rec.UNID_VTA,
                         detalle_rec.IND_EXONERADO_IGV, detalle_rec.CANT_USADA_NC,detalle_rec.Val_Prec_Public,
                         detalle_rec.Cod_Prom,----JCORTEZ 28/03/2008
                         detalle_rec.VAL_FRAC_LOCAL,--ERIOS 10/06/2008 Se copia los mismos valores del pedido
                         detalle_rec.CANT_ATENDIDA,
                         detalle_rec.IND_ZAN,
                         detalle_rec.COD_GRUPO_REP,detalle_rec.COD_GRUPO_REP_EDMUNDO, --JCORTEZ 11.11.09
                         detalle_rec.PORC_ZAN, -- 2009-11-09 JOLIVA
                         SEC_RESPALDO_STK_in
                         );



             v_cIndGraboDet := INDICADOR_SI;
         END LOOP;
         /******/
         --DUBILLUZ 20.08.2010 - ya no es necesario agrupar
         --SE MODIFICO PARA AGRUPAR LAS PROMOCIONES
         --19/11/2007 DUBILLUZ MODIFICACION

         /*****/
         FOR pedidoconv_rec IN infoPedidoConv
  	     LOOP
             INSERT INTO CON_PED_VTA_CLI
                         (COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, COD_CONVENIO,
                          COD_CLI, FEC_CREA_PED_VTA_CLI, USU_CREA_PED_VTA_CLI, NUM_DOC_IDEN,
                          COD_TRAB_EMPRESA, APE_PAT_TIT, APE_MAT_TIT, FEC_NAC_TIT,COD_SOLICITUD,
                          VAL_PORC_DCTO, VAL_PORC_COPAGO,NUM_TELEF_CLI,DIRECC_CLI,NOM_DISTRITO,VAL_COPAGO_DISP)
                   VALUES(cCodGrupoCia_in, cCodLocalAtencion_in, cNuevoNumPedVta_in, pedidoconv_rec.cod_convenio,
                          pedidoconv_rec.cod_cli, SYSDATE, cUsuCreaPedVta_in, pedidoconv_rec.num_doc_iden,
                          pedidoconv_rec.cod_trab_empresa, pedidoconv_rec.ape_pat_tit, pedidoconv_rec.ape_mat_tit,
                          pedidoconv_rec.fec_nac_tit, pedidoconv_rec.cod_solicitud,
                          pedidoconv_rec.val_porc_dcto, pedidoconv_rec.val_porc_copago, pedidoconv_rec.num_telef_cli,
                          pedidoconv_rec.direcc_cli, pedidoconv_rec.nom_distrito,pedidoconv_rec.val_copago_disp);
         END LOOP;
  		   Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocalAtencion_in, NUMERA_PEDIDO_VTA, cUsuCreaPedVta_in);
         Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocalAtencion_in, NUMERA_PEDIDO_DIARIO, cUsuCreaPedVta_in);
  	     IF(v_cIndGraboCab = INDICADOR_NO) THEN
            RAISE_APPLICATION_ERROR(-20032, 'Ocurrio un error al grabar la cabecera del pedido - ' || SQLERRM);
         ELSIF(v_cIndGraboDet = INDICADOR_NO) THEN
            RAISE_APPLICATION_ERROR(-20033, 'Ocurrio un error al grabar el detalle del pedido - ' || SQLERRM);
  		   END IF;

         -- AGREGA LOS CUPONES DEL PEDIDO
         CLI_AGREGA_CUPON_PED(cCodGrupoCia_in,cCodLocal_in,cNumPedVtaDel_in,cCodLocalAtencion_in,cNuevoNumPedVta_in);

         FOR datosadic_rec in infoDetalleDatosAdic LOOP
             INSERT INTO CON_BTL_MF_PED_VTA
             (cod_grupo_cia,cod_local, num_ped_vta, cod_campo, cod_convenio, 
             cod_cliente,fec_crea_ped_vta_cli, usu_crea_ped_vta_cli, fec_mod_ped_vta_cli, usu_mod_ped_vta_cli, 
             descripcion_campo, nombre_campo, flg_imprime, cod_valor_in)
             VALUES
             (
             cCodGrupoCia_in, cCodLocalAtencion_in, cNuevoNumPedVta_in,datosadic_rec.Cod_Campo,datosadic_rec.cod_convenio,
             datosadic_rec.Cod_Cliente,SYSDATE,cUsuCreaPedVta_in,NULL,NULL,
             datosadic_rec.Descripcion_Campo,datosadic_rec.Nombre_Campo,datosadic_rec.Flg_Imprime,datosadic_rec.Cod_Valor_In
             );
         
         END LOOP;
       -- ADICION
       --CLI_ACTUALIZA_VALORES_PD(cCodGrupoCia_in,cCodLocal_in,cNuevoNumPedVta_in);
       -- ADICION
    --RETURN v_cNumeroPedDiario;
  END;

  FUNCTION CLI_OBTIENE_ESTADO_PEDIDO(cCodGrupoCia_in IN CHAR,
  		   						   	             cCodLocal_in    IN CHAR,
									                   cNumPedVta_in 	 IN CHAR)
    RETURN CHAR
  IS
    v_cEstPedido TMP_VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
  BEGIN
       SELECT TMP_CAB.EST_PED_VTA
	     INTO   v_cEstPedido
	     FROM   TMP_VTA_PEDIDO_VTA_CAB TMP_CAB
	     WHERE  TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
	     AND	  TMP_CAB.COD_LOCAL = cCodLocal_in
	     AND	  TMP_CAB.NUM_PED_VTA   = cNumPedVta_in FOR UPDATE;
  	RETURN v_cEstPedido;
  EXCEPTION
  	   WHEN NO_DATA_FOUND THEN
		        v_cEstPedido := EST_PED_ANULADO; --SE ENVIA "N" PARA SIMULAR Q EL PEDIDO NO SE ENCUENTRA PENDIENTE
		RETURN v_cEstPedido;
  END;

  FUNCTION CLI_LISTA_FORMA_PAGO_PED(cCodGrupoCia_in IN CHAR,
  		   						   	            cCodLocal_in    IN CHAR,
									                  cNumPedVta_in 	IN CHAR)
    RETURN FarmaCursor
  IS
    curCli FarmaCursor;
  BEGIN
       OPEN curCli FOR
            SELECT TFPP.COD_FORMA_PAGO || '�' ||
                   FP.DESC_CORTA_FORMA_PAGO || '�' ||
                   '0' || '�' ||
                   DECODE(TFPP.TIP_MONEDA,COD_TIP_MON_SOLES,DESC_TIP_MON_SOLES,DESC_TIP_MON_DOLARES) || '�' ||
                   TO_CHAR(TFPP.IM_PAGO,'999,990.00') || '�' ||
                   TO_CHAR(TFPP.IM_TOTAL_PAGO,'999,990.00') || '�' ||
                   TFPP.TIP_MONEDA || '�' ||
                   TFPP.VAL_VUELTO || '�' ||
                   ' ' || '�' ||
                   ' ' || '�' ||
                   ' '|| '�' ||
                   ' '|| '�' ||
                   ' '|| '�' ||
                   ' '|| '�' ||
                   ' '
            FROM   VTA_PEDIDO_VTA_CAB VTA_CAB,
                   TMP_VTA_FORMA_PAGO_PEDIDO TFPP,
                   TMP_VTA_PEDIDO_VTA_CAB TMP_CAB,
                   VTA_FORMA_PAGO FP
            WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    VTA_CAB.COD_LOCAL = cCodLocal_in
            AND    VTA_CAB.NUM_PED_VTA = cNumPedVta_in
            AND    VTA_CAB.COD_GRUPO_CIA = TMP_CAB.COD_GRUPO_CIA
            AND    VTA_CAB.COD_LOCAL = TMP_CAB.COD_LOCAL_ATENCION
            AND    VTA_CAB.NUM_PED_VTA = TMP_CAB.NUM_PED_VTA_ORIGEN
            AND    TMP_CAB.COD_GRUPO_CIA = TFPP.COD_GRUPO_CIA
            AND    TMP_CAB.COD_LOCAL = TFPP.COD_LOCAL
            AND    TMP_CAB.NUM_PED_VTA = TFPP.NUM_PED_VTA
            AND    TFPP.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
            AND    TFPP.COD_FORMA_PAGO = FP.COD_FORMA_PAGO;
    RETURN CURCLI;
  END;

  PROCEDURE CLI_ANULA_DELIVERY_AUTOMATICO(cCodGrupoCia_in  IN CHAR,
  		   						   	   	              cCodLocal_in     IN CHAR,
									   	                    cNumPedVta_in    IN CHAR,
										                      cUsuModPedido_in IN CHAR)
  IS
  BEGIN

       UPDATE TMP_VTA_PEDIDO_VTA_CAB TMP_CAB SET TMP_CAB.USU_MOD_PED_VTA_CAB = cUsuModPedido_in, TMP_CAB.FEC_MOD_PED_VTA_CAB = SYSDATE,
              TMP_CAB.EST_PED_VTA = EST_PED_ANULADO
       WHERE  TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    TMP_CAB.COD_LOCAL = cCodLocal_in
       AND    TMP_CAB.NUM_PED_VTA = cNumPedVta_in;

  END;

  FUNCTION CLI_OBTIENE_LOCAL_ORIGEN(cCodGrupoCia_in  IN CHAR,
  		   						   	   	        cCodLocal_in     IN CHAR,
									   	              cNumPedVta_in    IN CHAR)
  RETURN CHAR
  IS
  v_cCodLocalOrigen CHAR(3);
    BEGIN
        SELECT c.cod_local INTO v_ccodlocalorigen
        FROM   tmp_vta_pedido_vta_cab c
        WHERE  c.cod_grupo_cia = cCodGrupoCia_in
        AND    c.num_ped_vta = cnumpedvta_in
        AND    c.cod_local_atencion = ccodLocal_in;
    RETURN v_ccodlocalorigen ;
  END;

  FUNCTION CLI_LISTA_DETALLE_PEDIDOS_INST(cCodGrupoCia_in      IN CHAR,
                                          cCodLocal_in         IN CHAR,
                                          cCodLocalAtencion_in IN CHAR,
                                          cNumPedido_in        IN CHAR)
    RETURN FarmaCursor
  IS
    curCli FarmaCursor;
  BEGIN
    OPEN curCli FOR
          SELECT TVTA_DET.COD_PROD|| '�' ||
          	     NVL(PROD.DESC_PROD,' ')|| '�' ||
          	     NVL(DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA),' ')|| '�' ||
          	     TO_CHAR(TVTA_DET.VAL_PREC_VTA,'999,990.000') || '�' ||
                 TO_CHAR(((TVTA_DET.CANT_ATENDIDA*PROD_LOCAL.VAL_FRAC_LOCAL)/TVTA_DET.VAL_FRAC),'999990') || '�' ||
          	     --NVL(TVTA_DET.CANT_ATENDIDA,0) || '�' ||
                 --TO_CHAR(((TVTA_DET.CANT_ATENDIDA*PROD_LOCAL.VAL_FRAC_LOCAL)/TVTA_DET.VAL_FRAC),'9,990.00') || '�' ||
                 ' '|| '�' ||
          	     TO_CHAR(NVL(TVTA_DET.VAL_PREC_TOTAL,0),'999,990.000') || '�' ||
                 NVL(LAB.NOM_LAB,' ') || '�' ||
                 NVL(PROD_LOCAL.Stk_Fisico, 0) || '�' ||
                 MOD((TVTA_DET.CANT_ATENDIDA) *(PROD_LOCAL.VAL_FRAC_LOCAL) , TVTA_DET.VAL_FRAC)
          FROM   TMP_VTA_PEDIDO_VTA_DET TVTA_DET,
          	     LGT_PROD_LOCAL PROD_LOCAL,
          	     LGT_PROD PROD,
          	     LGT_LAB LAB,
                 TMP_VTA_PEDIDO_VTA_CAB TVTA_CAB
          WHERE  TVTA_DET.COD_GRUPO_CIA = ccodgrupocia_in
          AND	   TVTA_DET.COD_LOCAL = cCodLocal_in
          AND	   TVTA_DET.NUM_PED_VTA = cnumpedido_in
          AND    TVTA_CAB.COD_LOCAL_ATENCION = cCodLocalAtencion_in
          AND    TVTA_CAB.COD_GRUPO_CIA = TVTA_DET.COD_GRUPO_CIA
          AND    TVTA_CAB.COD_LOCAL = TVTA_DET.COD_LOCAL
          AND    TVTA_CAB.NUM_PED_VTA = TVTA_DET.NUM_PED_VTA
          AND	   TVTA_CAB.COD_LOCAL_ATENCION = PROD_LOCAL.COD_LOCAL
          AND    TVTA_CAB.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND    TVTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   TVTA_DET.COD_PROD = PROD_LOCAL.COD_PROD
          AND	   PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
          AND	   PROD_LOCAL.COD_PROD = PROD.COD_PROD
          AND	   TVTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   PROD.COD_LAB = LAB.COD_LAB;
    RETURN curcli;
 END;

  FUNCTION CLI_VERIFICA_LOTE_PROD(cCodGrupoCia_in IN CHAR,
									   	            cCodProd_in     IN CHAR,
                                  cNumLote_in     IN CHAR)
    RETURN CHAR
  IS
    v_cExisteLote CHAR(1);
    v_nCantLotes  NUMBER;
  BEGIN
       v_cExisteLote := INDICADOR_NO;
       SELECT COUNT(*)
       INTO   v_nCantLotes
       FROM   LGT_MAE_LOTE_PROD LOT
       WHERE  LOT.COD_GRUPO_CIA= cCodGrupoCia_in
       AND    LOT.COD_PROD= cCodProd_in
       AND    LOT.NUM_LOTE_PROD = cNumLote_in;

       IF v_nCantLotes > 0 THEN
          v_cExisteLote := INDICADOR_SI;
       END IF;

    RETURN v_cExisteLote;
  END;

  PROCEDURE CLI_AGREGA_VTA_INSTI_DET(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
  		   								             cNumPedido_in   IN CHAR,
										                 cCodProd_in     IN CHAR,
                                     cNumLote_in     IN CHAR,
                                     cCant_in        IN NUMBER,
                                     cUsuCrea_in     IN CHAR,
                                     cFechaVencimiento_in IN CHAR)
  IS
    v_nNumeroSecMax TMP_VTA_INSTITUCIONAL_DET.SEC_VTA_INST_DET%TYPE;
  BEGIN
       SELECT NVL(MAX(TMP.SEC_VTA_INST_DET),0) + 1
       INTO   v_nNumeroSecMax
       FROM   TMP_VTA_INSTITUCIONAL_DET TMP
       WHERE  TMP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    TMP.COD_LOCAL = cCodLocal_in
       AND    TMP.NUM_PED_VTA = cNumPedido_in;

  	   INSERT INTO
              TMP_VTA_INSTITUCIONAL_DET (COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, SEC_VTA_INST_DET,
                                         COD_PROD, NUM_LOTE_PROD, CANT_ATENDIDA, USU_CREA_VTA_INST_DET, Fec_Vencimiento_Lote)-- paulo
	   		  	   	                 VALUES (cCodGrupoCia_in, cCodLocal_in, cNumPedido_in, v_nNumeroSecMax,
                                         cCodProd_in, cNumLote_in, cCant_in, cUsuCrea_in ,to_date(cFechaVencimiento_in,'dd/MM/yyyy'));
  END;

  PROCEDURE CLI_ELIMINA_VTA_INSTI_DET(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
  		   								              cNumPedido_in   IN CHAR,
										                  cCodProd_in     IN CHAR)
  IS
  BEGIN
       NULL;
/*
       DELETE FROM TMP_VTA_INSTITUCIONAL_DET TMP
       WHERE  TMP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    TMP.COD_LOCAL = cCodLocal_in
       AND    TMP.NUM_PED_VTA = cNumPedido_in
       AND    TMP.COD_PROD = cCodProd_in;
*/
  END;

  FUNCTION CLI_LISTA_INST_DET_PROD_LOTE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
  		   								                cNumPedido_in   IN CHAR,
										                    cCodProd_in     IN CHAR)
    RETURN FarmaCursor
  IS
    curCli FarmaCursor;
  BEGIN
       OPEN curCli FOR
            SELECT TMP.CANT_ATENDIDA || '�' ||
                   NVL(TMP.NUM_LOTE_PROD,' ') || '�' ||
                   TMP.NUM_PED_VTA || '�' ||
                   TMP.COD_PROD || '�' ||
                   'S'|| '�' ||
                   NVL(TO_CHAR(TMP.FEC_VENCIMIENTO_LOTE,'dd/MM/yyyy'),' ')
            FROM   TMP_VTA_INSTITUCIONAL_DET TMP
            WHERE  TMP.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    TMP.COD_LOCAL = cCodLocal_in
            AND    TMP.NUM_PED_VTA = cNumPedido_in
            AND    TMP.COD_PROD = cCodProd_in;
    RETURN CURCLI;
  END;

  PROCEDURE CLI_ELIMINA_PED_INST_PROD_LOTE(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR,
  		   								                   cNumPedido_in   IN CHAR,
										                       cCodProd_in     IN CHAR,
                                           cNumLote_in     IN CHAR)
  IS
  BEGIN
       IF cNumLote_in IS NULL THEN
         DELETE FROM TMP_VTA_INSTITUCIONAL_DET TMP
         WHERE  TMP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    TMP.COD_LOCAL = cCodLocal_in
         AND    TMP.NUM_PED_VTA = cNumPedido_in
         AND    TMP.COD_PROD = cCodProd_in
         AND    TMP.NUM_LOTE_PROD IS NULL;
       ELSE
         DELETE FROM TMP_VTA_INSTITUCIONAL_DET TMP
         WHERE  TMP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    TMP.COD_LOCAL = cCodLocal_in
         AND    TMP.NUM_PED_VTA = cNumPedido_in
         AND    TMP.COD_PROD = cCodProd_in
         AND    TMP.NUM_LOTE_PROD = cNumLote_in;
       END IF;
  END;

PROCEDURE CLI_GENERA_PEDIDO_INST_A(cCodGrupoCia_in 	   IN CHAR,
						   	                     cCodLocal_in    	   IN CHAR,
                                     cCodLocalAtencion_in IN CHAR,
							                       cNumPedVtaDel_in 	   IN CHAR,
                                     cNuevoNumPedVta_in   IN CHAR,
                                     cNumPedDiario_in 	   IN CHAR,
                                     cIPPedido_in         IN CHAR,
                                     cSecUsuVen_in        IN CHAR,
							                       cUsuCreaPedVta_in    IN CHAR)
  IS
    v_cIndGraboCab	   CHAR(1);
    v_cIndGraboDet	   CHAR(1);
    v_nSecDetProd	     NUMBER := 0;

    --JCORTEZ 19.10.09
    CCOD_GRUPO_REP CHAR(3);
    CCOD_GRUPO_REP_EDMUNDO CHAR(3);

	CURSOR infoCabeceraPedido IS
		   	SELECT *
				FROM   TMP_VTA_PEDIDO_VTA_CAB TMP_CAB
				WHERE  TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
				AND	   TMP_CAB.COD_LOCAL = cCodLocal_in
				AND	   TMP_CAB.NUM_PED_VTA = cNumPedVtaDel_in
        AND    TMP_CAB.EST_PED_VTA = EST_PED_PENDIENTE;
  CURSOR infoDetallePedido IS
        SELECT TMP_DET.COD_GRUPO_CIA,TMP_DET.COD_LOCAL,TMP_DET.NUM_PED_VTA,
               INS.COD_PROD,INS.CANT_ATENDIDA,
               TO_NUMBER(TO_CHAR(TMP_DET.VAL_PREC_TOTAL / ((TMP_DET.CANT_ATENDIDA*PL.VAL_FRAC_LOCAL)/TMP_DET.VAL_FRAC),'999,990.000'),'999,990.000') VAL_PREC_VTA,
               --TMP_DET.VAL_PREC_VTA,
               ((INS.CANT_ATENDIDA * TMP_DET.Val_Prec_Total)/TMP_DET.CANT_ATENDIDA)/pl.val_frac_local AS VAL_PREC_TOTAL,
               TMP_DET.PORC_DCTO_1,TMP_DET.PORC_DCTO_2,TMP_DET.PORC_DCTO_3,TMP_DET.PORC_DCTO_TOTAL,
               TMP_DET.EST_PED_VTA_DET,TMP_DET.VAL_TOTAL_BONO,PL.VAL_FRAC_LOCAL,
               --TMP_DET.VAL_PREC_LISTA,
               TO_NUMBER(TO_CHAR(TMP_DET.VAL_PREC_LISTA /PL.VAL_FRAC_LOCAL,'999,990.000'),'999,990.000') VAL_PREC_LISTA,
               TMP_DET.VAL_IGV,
               DECODE(PL.IND_PROD_FRACCIONADO,'N',P.DESC_UNID_PRESENT,PL.UNID_VTA) UNID_VTA,
               TMP_DET.IND_EXONERADO_IGV,
               INS.NUM_LOTE_PROD,
               INS.FEC_VENCIMIENTO_LOTE,
               P.PORC_ZAN               -- 2009-11-09 JOLIVA
        FROM   TMP_VTA_PEDIDO_VTA_DET TMP_DET,
               TMP_VTA_INSTITUCIONAL_DET INS,
               LGT_PROD_LOCAL PL,
               LGT_PROD P
        WHERE  TMP_DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND	   TMP_DET.COD_LOCAL = cCodLocal_in
        AND	   TMP_DET.NUM_PED_VTA = cNumPedVtaDel_in
        AND    TMP_DET.EST_PED_VTA_DET = ESTADO_ACTIVO
        AND    P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
        AND    P.COD_PROD = PL.COD_PROD
        AND    PL.COD_GRUPO_CIA = TMP_DET.COD_GRUPO_CIA
        AND	   PL.COD_LOCAL = cCodLocalAtencion_in
        AND    PL.COD_PROD = TMP_DET.COD_PROD
        AND    INS.COD_GRUPO_CIA = TMP_DET.COD_GRUPO_CIA
        AND    INS.COD_LOCAL= cCodLocalAtencion_in
        AND    INS.NUM_PED_VTA = TMP_DET.NUM_PED_VTA
        AND    INS.COD_PROD = TMP_DET.COD_PROD
-- 2009-12-31 JOLIVA: NO SE TOMA EN CUENTA EL SECUENCIAL DE DETALLE
--        AND    INS.SEC_VTA_INST_DET = TMP_DET.SEC_PED_VTA_DET -- DUBILLUZ - 18.12.2009
        ORDER BY INS.COD_PROD;

        SEC_RESPALDO_STK_in NUMBER(10);
  BEGIN
       v_cIndGraboCab := INDICADOR_NO;
       v_cIndGraboDet := INDICADOR_NO;
       FOR cabecera_rec IN infoCabeceraPedido
	     LOOP
	  	     INSERT INTO VTA_PEDIDO_VTA_CAB
                       (COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, FEC_PED_VTA,
                        VAL_BRUTO_PED_VTA, VAL_NETO_PED_VTA, VAL_REDONDEO_PED_VTA,
                        VAL_IGV_PED_VTA, VAL_DCTO_PED_VTA,
                        TIP_PED_VTA,
                        VAL_TIP_CAMBIO_PED_VTA, NUM_PED_DIARIO, CANT_ITEMS_PED_VTA,
                        EST_PED_VTA, TIP_COMP_PAGO, NOM_CLI_PED_VTA, DIR_CLI_PED_VTA,
                        RUC_CLI_PED_VTA, USU_CREA_PED_VTA_CAB, FEC_CREA_PED_VTA_CAB,
                        OBS_FORMA_PAGO, OBS_PED_VTA, NUM_TELEFONO, IND_DELIV_AUTOMATICO,
                        --a�adido
                        --dubilluz 11.07.2007
                        NUM_PEDIDO_DELIVERY,COD_LOCAL_PROCEDENCIA,
                        --JMIRANDA 15.12.09
                        PUNTO_LLEGADA)

							   VALUES(cCodGrupoCia_in, cCodLocalAtencion_in, cNuevoNumPedVta_in, SYSDATE,
							 		      cabecera_rec.VAL_BRUTO_PED_VTA, cabecera_rec.VAL_NETO_PED_VTA, cabecera_rec.VAL_REDONDEO_PED_VTA,
							 		      cabecera_rec.VAL_IGV_PED_VTA, cabecera_rec.VAL_DCTO_PED_VTA,
                        TIP_PEDIDO_INSTITUCIONAL,
									      cabecera_rec.VAL_TIP_CAMBIO_PED_VTA, cNumPedDiario_in, cabecera_rec.CANT_ITEMS_PED_VTA,
									      cabecera_rec.EST_PED_VTA, cabecera_rec.TIP_COMP_PAGO, cabecera_rec.NOM_CLI_PED_VTA, cabecera_rec.DIR_CLI_PED_VTA,
									      cabecera_rec.RUC_CLI_PED_VTA, cUsuCreaPedVta_in, SYSDATE,
									      cabecera_rec.OBS_FORMA_PAGO, cabecera_rec.OBS_PED_VTA, cabecera_rec.NUM_TELEFONO, INDICADOR_SI,
                        --a�adido
                        --dubilluz 11.07.2007
                        cabecera_rec.num_pedido_delivery,cabecera_rec.cod_local_procedencia,
                        --JMIRANDA 15.12.09
                        cabecera_rec.punto_llegada);

           UPDATE TMP_VTA_PEDIDO_VTA_CAB
           SET    NUM_PED_VTA_ORIGEN = cNuevoNumPedVta_in
				   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
				   AND	  COD_LOCAL = cCodLocal_in
				   AND	  NUM_PED_VTA = cNumPedVtaDel_in;

           v_cIndGraboCab := INDICADOR_SI;
       END LOOP;
       FOR detalle_rec IN infoDetallePedido
	     LOOP

             --JCORTEZ 19.10.09
              SELECT NVL(X.COD_GRUPO_REP,' '),NVL(X.COD_GRUPO_REP_EDMUNDO,' ')
              INTO CCOD_GRUPO_REP,CCOD_GRUPO_REP_EDMUNDO
              FROM LGT_PROD X
              WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
              AND X.COD_PROD=detalle_rec.COD_PROD;

           v_nSecDetProd := v_nSecDetProd + 1;

       SEC_RESPALDO_STK_in := 0;
       /*ptovta_respaldo_stk.pvta_f_ins_del_upd_stk_res(cCodGrupoCia_in,
                                                            cCodLocalAtencion_in,
                                                            detalle_rec.COD_PROD,
                                                            detalle_rec.CANT_ATENDIDA,
                                                            detalle_rec.VAL_FRAC_LOCAL,
                                                            '',
                                                            'V', --Venta
                                                            cUsuCreaPedVta_in
                                                            );*/


           INSERT INTO VTA_PEDIDO_VTA_DET
                       (COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, SEC_PED_VTA_DET,
                        COD_PROD, CANT_ATENDIDA, VAL_PREC_VTA,
                        VAL_PREC_TOTAL,
                        PORC_DCTO_1, PORC_DCTO_2, PORC_DCTO_3, PORC_DCTO_TOTAL,
                        EST_PED_VTA_DET, VAL_TOTAL_BONO, VAL_FRAC,
                        SEC_USU_LOCAL, USU_CREA_PED_VTA_DET, FEC_CREA_PED_VTA_DET,
                        VAL_PREC_LISTA, VAL_IGV, UNID_VTA,
                        IND_EXONERADO_IGV, NUM_LOTE_PROD, FEC_VENCIMIENTO_LOTE ,
                       -- VAL_FRAC_LOCAL,
                        CANT_FRAC_LOCAL,
                        VAL_FRAC_LOCAL,
                        COD_GRUPO_REP,COD_GRUPO_REP_EDMUNDO, --JCORTEZ 19.10.09
                         PORC_ZAN  ,     -- 2009-11-09 JOLIVA
                         SEC_RESPALDO_STK
                        )
			 			    VALUES(cCodGrupoCia_in, cCodLocalAtencion_in, cNuevoNumPedVta_in, v_nSecDetProd,
			 			 		       detalle_rec.COD_PROD, detalle_rec.CANT_ATENDIDA, detalle_rec.VAL_PREC_VTA,
                       ROUND(detalle_rec.VAL_PREC_TOTAL,2),
			 			 		       detalle_rec.PORC_DCTO_1, detalle_rec.PORC_DCTO_2, detalle_rec.PORC_DCTO_3, detalle_rec.PORC_DCTO_TOTAL,
			 					       detalle_rec.EST_PED_VTA_DET, detalle_rec.VAL_TOTAL_BONO, detalle_rec.VAL_FRAC_LOCAL,
			 					       cSecUsuVen_in, cUsuCreaPedVta_in, SYSDATE,
			 					       detalle_rec.VAL_PREC_LISTA, detalle_rec.VAL_IGV, detalle_rec.UNID_VTA,
                       detalle_rec.IND_EXONERADO_IGV, detalle_rec.NUM_LOTE_PROD, detalle_rec.fec_vencimiento_lote,
                       detalle_rec.CANT_ATENDIDA,
                       detalle_rec.VAL_FRAC_LOCAL,
                       CCOD_GRUPO_REP,CCOD_GRUPO_REP_EDMUNDO,
                       detalle_rec.PORC_ZAN  ,    -- 2009-11-09 JOLIVA
                       SEC_RESPALDO_STK_in
                       );


           v_cIndGraboDet := INDICADOR_SI;
       END LOOP;
       --actualiza la cantidad de items de cabecera
       UPDATE VTA_PEDIDO_VTA_CAB
       SET    CANT_ITEMS_PED_VTA = v_nSecDetProd
		   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
		   AND	  COD_LOCAL = cCodLocalAtencion_in
		   AND	  NUM_PED_VTA = cNuevoNumPedVta_in;

		   Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocalAtencion_in, NUMERA_PEDIDO_VTA, cUsuCreaPedVta_in);
       Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocalAtencion_in, NUMERA_PEDIDO_DIARIO, cUsuCreaPedVta_in);
       IF(v_cIndGraboCab = INDICADOR_NO) THEN
          RAISE_APPLICATION_ERROR(-20032, 'Ocurrio un error al grabar la cabecera del pedido institucional- ' || SQLERRM);
       ELSIF(v_cIndGraboDet = INDICADOR_NO) THEN
          RAISE_APPLICATION_ERROR(-20033, 'Ocurrio un error al grabar el detalle del pedido institucional- ' || SQLERRM);
		   END IF;

         -- AGREGA LOS CUPONES DEL PEDIDO
         CLI_AGREGA_CUPON_PED(cCodGrupoCia_in,cCodLocal_in,cNumPedVtaDel_in,cCodLocalAtencion_in,cNuevoNumPedVta_in);
       -- ADICION
       --CLI_ACTUALIZA_VALORES_PD(cCodGrupoCia_in,cCodLocal_in,cNuevoNumPedVta_in);
       -- ADICION

    --RETURN v_cNumeroPedDiario;
  END;

  FUNCTION CLI_LISTA_PROD_LOTE_SEL(cCodGrupoCia_in      IN CHAR,
                                   cCodLocal_in         IN CHAR,
  		   								           cNumPedido_in        IN CHAR,
										               cCodLocalAtencion_in IN CHAR)
    RETURN FarmaCursor
  IS
    curCli FarmaCursor;
  BEGIN
       OPEN curCli FOR
            SELECT TMP_DET.COD_PROD
            FROM   TMP_VTA_PEDIDO_VTA_DET TMP_DET,
                   LGT_PROD_LOCAL PL,
                   (SELECT INS.NUM_PED_VTA,
                           INS.COD_PROD,
                           ins.num_lote_prod,
                           ins.fec_vencimiento_lote,
                           SUM(INS.CANT_ATENDIDA) CANTIDAD
                    FROM   TMP_VTA_INSTITUCIONAL_DET INS
                    WHERE  INS.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND	   INS.COD_LOCAL = cCodLocalAtencion_in
                    AND	   INS.NUM_PED_VTA = cNumPedido_in
                    GROUP BY INS.NUM_PED_VTA, INS.COD_PROD,ins.num_lote_prod,ins.fec_vencimiento_lote) INS
            WHERE  TMP_DET.COD_GRUPO_CIA = cCodGrupoCia_in
            AND	   TMP_DET.COD_LOCAL = cCodLocal_in
            AND	   TMP_DET.NUM_PED_VTA = cNumPedido_in
            AND    TMP_DET.EST_PED_VTA_DET = ESTADO_ACTIVO
            AND    TMP_DET.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
            AND	   cCodLocalAtencion_in = PL.COD_LOCAL
            AND    TMP_DET.COD_PROD = PL.COD_PROD
            AND    INS.NUM_PED_VTA = TMP_DET.NUM_PED_VTA
            AND    INS.COD_PROD = TMP_DET.COD_PROD
            AND    INS.CANTIDAD = ((TMP_DET.CANT_ATENDIDA*PL.VAL_FRAC_LOCAL)/TMP_DET.VAL_FRAC);
    RETURN CURCLI;
  END;

  PROCEDURE CLI_REINICIALIZA_PEDIDO_AUTO(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
  		   								                 cNumPedido_in   IN CHAR)
  IS
  BEGIN
       UPDATE TMP_VTA_PEDIDO_VTA_CAB TCAB
       SET    TCAB.EST_PED_VTA = EST_PED_PENDIENTE,
              TCAB.NUM_PED_VTA_ORIGEN = NULL
       WHERE  TCAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    TCAB.COD_LOCAL = cCodLocal_in
       AND    TCAB.NUM_PED_VTA_ORIGEN = cNumPedido_in;
  END;

  FUNCTION CLI_OBTIENE_IND_MONTOS(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
  		   								          cNumPedido_in   IN CHAR)
  RETURN CHAR
  IS
    v_mont_cab VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_mont_det VTA_PED_RECETA_DET.VAL_PREC_TOTAL%TYPE;
    v_retorno CHAR (4):= 'FALS' ;
  BEGIN
       SELECT CAB.VAL_NETO_PED_VTA - CAB.VAL_REDONDEO_PED_VTA INTO v_mont_cab
       FROM   VTA_PEDIDO_VTA_CAB CAB
       WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CAB.COD_LOCAL = cCodLocal_in
       AND    CAB.NUM_PED_VTA = cNumPedido_in;
          dbms_output.put_line(v_mont_cab);

       SELECT SUM(DET.VAL_PREC_TOTAL) INTO v_mont_det
       FROM   VTA_PEDIDO_VTA_DET DET
       WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    DET.COD_LOCAL = cCodLocal_in
       AND    DET.NUM_PED_VTA = cNumPedido_in;
          dbms_output.put_line(v_mont_det);

       IF(v_mont_cab <> v_mont_det) THEN
          v_retorno:= 'TRUE';
       END IF ;

       RETURN v_retorno ;
 END;

 PROCEDURE  CLI_ACTUALIZA_VALORES_PD(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
  		   		  						           cNumPedido_in   IN CHAR)
 IS
   v_ind_diferencias CHAR(4);
 BEGIN
   v_ind_diferencias:= CLI_OBTIENE_IND_MONTOS (cCodGrupoCia_in,cCodLocal_in,cNumPedido_in);
   dbms_output.put_line(v_ind_diferencias);

   IF(v_ind_diferencias = 'TRUE') THEN
     UPDATE VTA_PEDIDO_VTA_CAB CAB
     SET    CAB.VAL_NETO_PED_VTA = (SELECT SUM(DET.VAL_PREC_TOTAL) + CASE
                                           WHEN trunc(MOD(SUM(DET.VAL_PREC_TOTAL)*100,10)) >= 5 THEN
                                                  -1 * 0.01 * trunc(((MOD(SUM(DET.VAL_PREC_TOTAL)*100,10))) - 5)
                                           WHEN trunc(MOD(SUM(DET.VAL_PREC_TOTAL)*100,10)) < 5 THEN
                                                  -1 * 0.01 * trunc(((MOD(SUM(DET.VAL_PREC_TOTAL)*100,10))))
                                           END
                                    FROM   VTA_PEDIDO_VTA_DET DET
                                    WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND    DET.COD_LOCAL = cCodLocal_in
                                    AND    DET.NUM_PED_VTA = cNumPedido_in),
            CAB.VAL_REDONDEO_PED_VTA = (SELECT CASE
                                           WHEN trunc(MOD(SUM(DET.VAL_PREC_TOTAL)*100,10)) >= 5 THEN
                                                  -1 * 0.01 * trunc(((MOD(SUM(DET.VAL_PREC_TOTAL)*100,10))) - 5)
                                           WHEN trunc(MOD(SUM(DET.VAL_PREC_TOTAL)*100,10)) < 5 THEN
                                                  -1 * 0.01 * trunc(((MOD(SUM(DET.VAL_PREC_TOTAL)*100,10))))
                                           END
                                        FROM   VTA_PEDIDO_VTA_DET DET
                                        WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
                                        AND    DET.COD_LOCAL = cCodLocal_in
                                        AND    DET.NUM_PED_VTA = cNumPedido_in),
            CAB.VAL_DCTO_PED_VTA = CAB.VAL_BRUTO_PED_VTA - CAB.VAL_NETO_PED_VTA
     WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
     AND   CAB.COD_LOCAL = cCodLocal_in
     AND   CAB.NUM_PED_VTA = cNumPedido_in;
     END IF;
   END;

   FUNCTION CLI_OBTIENE_FECHA_VENCIMIENTO(cCodGrupoCia_in IN CHAR,
									   	                    cCodProd_in     IN CHAR,
                                          cNumLote_in     IN CHAR)
    RETURN CHAR
  IS
    v_fechaLote  CHAR(10);
  BEGIN
       SELECT nvl(TO_CHAR(LOT.FEC_VENC_LOTE,'dd/MM/yyyy'),' ')
       INTO   v_FechaLote
       FROM   LGT_MAE_LOTE_PROD LOT
       WHERE  LOT.COD_GRUPO_CIA= cCodGrupoCia_in
       AND    LOT.COD_PROD= cCodProd_in
       AND    LOT.NUM_LOTE_PROD = cNumLote_in;

    RETURN v_fechaLote ;
  END;


    PROCEDURE CON_ACTUALIZA_NUM_PED(cCodGrupoCia_in 	 IN CHAR,
    	                              cCodLocal_in    	 IN CHAR,
                                    cNumPedVta_in        IN CHAR,
                                    cNumPedVtaDel_in     IN CHAR) IS
    BEGIN

         UPDATE TMP_VTA_FORMA_PAGO_PEDIDO_CON FPP_CON
         SET    NUM_PED_VTA = cNumPedVta_in
         WHERE  FPP_CON.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    FPP_CON.COD_LOCAL     = cCodLocal_in
         AND    FPP_CON.NUM_PED_VTA   = cNumPedVtaDel_in;

  END CON_ACTUALIZA_NUM_PED;
  /* *********************************************************** */
  PROCEDURE CLI_AGREGA_CUPON_PED(cCodGrupoCia_in 	IN CHAR,
     	                           cCodLocal_in    	IN CHAR,
                                 cNumPedVta_in    IN CHAR,
                                 cCodLocalAtencion_in IN CHAR ,
                                 cNuevoNumPedVta_in    IN CHAR
                                 )
 is
 begin

      INSERT INTO VTA_PEDIDO_CUPON
      (
        COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,COD_CAMP_CUPON,
        CANTIDAD,USU_CREA_PED_CUPON,FEC_CREA_PED_CUPON,
        USU_MOD_PED_CUPON,FEC_MOD_PED_CUPON
      )
      (
      SELECT COD_GRUPO_CIA,cCodLocalAtencion_in,cNuevoNumPedVta_in,COD_CAMP_CUPON,
      CANTIDAD,USU_CREA_PED_CUPON,FEC_CREA_PED_CUPON,USU_MOD_PED_CUPON,
      FEC_MOD_PED_CUPON
      FROM   TMP_VTA_PEDIDO_CUPON C
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL  = cCodLocal_in
      AND    C.NUM_PED_VTA = cNumPedVta_in
      );
 end;

  /* ********************************************************************************************** */

/* ******************************************************************** */
  FUNCTION IMP_DATOS_DELIVERY(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cIpServ_in        IN CHAR)
  RETURN VARCHAR2
  IS
  vMsg_out varchar2(32767):= '';

  /*vFila_IMG_Cabecera_MF varchar2(2800):= '';
  vFila_IMG_Cabecera_Consejo varchar2(2800):= '';
  vFila_Cliente      varchar2(2800):= '';
  vFila_Num_Ped      varchar2(2800):= '';
  vFila_Msg_01       varchar2(2800):= '';
  vFila_Msg_02       varchar2(2800):= '';
  vFila_Pie_Pagina   varchar2(2800):= '';*/

  vFila_Consejos     varchar2(22767):= '';
  vFila_1 VARCHAR2(2800):='';
  vFila_2 VARCHAR2(2800):='';
  vFila_3 VARCHAR2(2800):='';
  vFila_4 VARCHAR2(2800):='';

  v_usuario varchar2(200);
  v_nomcli  varchar2(200);
  v_direcc  varchar2(400);
  v_referen varchar2(400);
  v_ruc     varchar2(200);
  v_obs     varchar2(400);

  v_montoRed   varchar2(200);
  v_vuelto     varchar2(200);

  v_formaPago    varchar2(200);
  v_moneda      varchar2(200);
  v_monto       varchar2(200);

  --agregado por DVELIZ 15.12.2008
  v_CodLote       varchar2(200);
  v_CodAutorizacion       varchar2(200);

  v_obsFormaPago     varchar2(400);
  v_tipoDoc          varchar2(200);
  v_nombreDe         varchar2(200);
  v_direEnvio        varchar2(400);
  v_obsPedido        varchar2(400);
  v_comentRuteo      varchar2(400);
  v_numPedVta      varchar2(200);
  v_numPedDel      varchar2(200);

  v_numTarj      varchar2(20);
  v_FecVenc      varchar2(20);
  v_numDocIdent  varchar2(15);

  v_fechaComanda varchar2(300);

  --Agregado por dveliz 20.02.2009
  v_categoriaCliente    varchar2(20);

  --Agregado por dveliz 23.03.2009
  v_nombreMotorizado    VARCHAR2(1000);

  cursor1 FarmaCursor:=VTA_OBTENER_DATA1(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);
  cursor2 FarmaCursor:=VTA_OBTENER_DATA2(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);
  cursor3 FarmaCursor:=VTA_OBTENER_DATA3(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);
  cursor4 FarmaCursor:=VTA_OBTENER_DATA4(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);

  vCodCampana CHAR(5) := 'N';
  vCantidad   vta_pedido_cupon.CANTIDAD%type := 0;
  vDescripMensaje varchar2(3000);
  vNumPedidoLocal CHAR(10);
  vMensaje varchar2(3000);

  v_montoMonedaOrigen       varchar2(200);

  BEGIN
    ---------------

    select trim(max(e.num_ped_vta))
    into   vNumPedidoLocal
    from   vta_pedido_vta_cab e
    where  e.cod_grupo_cia = cCodGrupoCia_in
    and    e.cod_local = cCodLocal_in
    and    e.num_pedido_delivery = cNumPedVta_in;



    begin
      SELECT C.COD_CAMP_CUPON,C.CANTIDAD
      into   vCodCampana,vCantidad
      FROM   VTA_PEDIDO_CUPON C
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL     = cCodLocal_in
      AND    C.NUM_PED_VTA   = vNumPedidoLocal;
    exception
    when others then
         vCodCampana := 'N';
    end;

    if vCodCampana != 'N' then

       select w.desc_cupon
       into   vDescripMensaje
       from   vta_campana_cupon w
       where  w.cod_grupo_cia  = cCodGrupoCia_in
       and    w.cod_camp_cupon = vCodCampana;


       if vCantidad >0 then
          IF vCantidad = 1 then
             vMensaje := 'GANO '|| vCantidad ||' CUPON de la campa�a "' ||vDescripMensaje||'"';
          ELSE
             vMensaje := 'GANO '|| vCantidad ||' CUPONES de la campa�a "' ||vDescripMensaje||'"';
          END IF;
       end if;


     if Length(vMensaje) > 59 then
           vMensaje := Substrb(vMensaje,0,20);

      end if;

    end if;






    --------------
    LOOP
    FETCH cursor4  INTO v_numPedVta,v_numPedDel,v_fechaComanda, v_categoriacliente;
    EXIT WHEN cursor4%NOTFOUND;
      /*vFila_4 := vFila_4||'<td>'||
                           ' <table width="800" border="0">'||
                           ' <tr> '||
                           ' <td align="right" valign="top"><FONT FACE="ARIAL" SIZE="5">Fecha y Hora :</FONT></td> '||
                           ' <td valign="top"><FONT FACE="ARIAL" SIZE="5">'||v_fechaComanda||'</FONT></td> '||
                           '</tr>'||

                           ' <tr> '||
                           ' <td align="right" valign="top"><FONT FACE="ARIAL" SIZE="5">Comanda :</FONT></td> '||
                           ' <td valign="top"><FONT FACE="ARIAL" SIZE="5" weight: bold>'||v_numPedVta||'</FONT></td> '||
                           '</tr>'||

                           '<tr>'||
                        	 ' <td align="right" valign="top"><FONT FACE="ARIAL" SIZE="5">Ped Local :</FONT></td>'||
                        	 ' <td valign="top"><FONT FACE="ARIAL" SIZE="5" weight: bold>'||v_numPedDel||'</FONT></td>'||
                           ' </tr>'||

                           --Agregado por dveliz 20.02.2009
                             '<tr>'||
                             '<td align="right" valign="top"><FONT FACE="ARIAL" SIZE="6">CATEGORIA :</FONT></td>'||
                             '<td valign="top"><strong><FONT FACE="VERDANA" SIZE="6">'|| v_categoriacliente||'</FONT></strong></td>'||
                             '</tr>'||
                             --fin dveliz

                           '</table>'||
                        	 ' </td> '||
                           '</tr>'||
                           '</table>'||
                           '<table width="200" border="0">'||
                           '<tr>'||
                           '<td width="4" >&nbsp;</td>'||
                           '<td>';*/

        vFila_4 := vFila_4 || '<table width="100%" border="0" cellpadding="1" cellspacing="1">'||
                              '<tr>'||
                                '<td width="50%" align="right" class="style1">FECHA Y HORA : </td>'||
                                '<td width="50%" class="style1">'||TRIM(v_fechaComanda)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td align="right" class="style1">COMANDA : </td>'||
                                '<td class="style1">'||TRIM(v_numPedVta)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td align="right" class="style1">PED. LOCAL : </td>'||
                                '<td class="style1">'||TRIM(v_numPedDel)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td align="right" class="style3">CATEGORIA : </td>'||
                                '<td class="style3">'|| TRIM(v_categoriacliente)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td></td>'||
                                '<td></td>'||
                              '</tr>'||
                            '</table>';
    END
    LOOP;
    -------------------------------------------------------------------------------
      LOOP
      FETCH cursor1 INTO v_usuario,v_nomcli,v_direcc,v_referen,v_ruc,v_obs,v_montoRed,v_vuelto, v_nombreMotorizado;
      EXIT WHEN cursor1%NOTFOUND;
       DBMS_OUTPUT.PUT_LINE('..............');
       /* vFila_1 := vFila_1|| '<table width="800" border="0">'||
                             '<tr>'||
                             '<td width="120"><FONT FACE="ARIAL" SIZE="4">ATENDIDO : </FONT></td>'||
                             '<td ><strong>'||v_usuario||'</strong></td>'||
                             '</tr>'||
                             '<tr>'||
                             '<td><FONT FACE="ARIAL" SIZE="4">NOM/ CLI : </FONT></td>'||
                             '<td ><strong>'||v_nomcli||'</strong></td>'||
                             '</tr>'||
                             '<tr>'||
                             '<td><FONT FACE="ARIAL" SIZE="4">DIRECC. : </FONT></td>'||
                             '<td ><strong>'||v_direcc||'</strong></td>'||
                             '</tr>'||
                             '<tr>'||
                             '  <td><FONT FACE="ARIAL" SIZE="4">REFERN. : </FONT></td>'||
                             '  <td><strong>'||v_referen||'</strong></td>'||
                             '</tr>'||
                             '<tr>'||
                             '  <td><FONT FACE="ARIAL" SIZE="4">R.U.C : </FONT></td>'||
                             '  <td><strong>'||v_ruc||'</strong></td>'||
                             '</tr>'||
                             '<tr>'||
                             '  <td><FONT FACE="ARIAL" SIZE="4">OBS. CLI. :</FONT></td>'||
                             '  <td><strong>'||v_obs||'</strong></td>'||
                             '</tr>'||
                             '<tr>'||
                             '  <td><FONT FACE="ARIAL" SIZE="4">MONTO RED : </FONT></td>'||
                             '  <td><strong>'||v_montoRed||'</strong></td>'||
                             '</tr>'||
                            '<tr>'||
                             '  <td ><FONT FACE="ARIAL" SIZE="4">VUELTO : </FONT></td>'||
                             '  <td ><strong>'||v_vuelto||'</strong></td>'||
                             '</tr>'||
                             '</table>';*/

        vFila_1 := vFila_1 || '<table width="100%" border="0" cellpadding="0" cellspacing="0">'||
                              '<tr>'||
                                '<td width="100" class="style1">ATENDIDO : </td>'||
                                '<td width="310" class="style4">'||TRIM(v_usuario)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">NOM / CLI : </td>'||
                                '<td class="style4">'||TRIM(v_nomcli)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">DIRECC : </td>'||
                                '<td class="style4">'||TRIM(v_direcc)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">REFEREN : </td>'||
                                '<td class="style4">'||TRIM(v_referen)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">R.U.C. : </td>'||
                                '<td class="style4">'||TRIM(v_ruc)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">OBS. CLI : </td>'||
                                '<td class="style4">'||TRIM(v_obs)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">TOT.VTA : </td>'||
                                '<td class="style4">'||TRIM(v_montoRed)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">VUELTO : </td>'||
                                '<td class="style4">'||TRIM(v_vuelto)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">MOTORIZADO : </td>'||
                                '<td class="style4">'||TRIM(v_nombreMotorizado)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td></td>'||
                                '<td></td>'||
                              '</tr>'||
                            '</table>';
     END
     LOOP;
    -------------------------------------------------
    LOOP
    --FETCH cursor2 INTO v_formaPago,v_moneda,v_monto,v_numTarj,v_FecVenc,v_numDocIdent;--200
--    FETCH cursor2 INTO v_formaPago,v_moneda,v_monto,v_numTarj,v_FecVenc,v_numDocIdent,v_CodLote, v_CodAutorizacion; --modificado por DVELIZ 15.12.2008
-- 2010-02-25 JOLIVA: Se agrega el IM_PAGO
    FETCH cursor2 INTO v_formaPago,v_moneda,v_monto,v_numTarj,v_FecVenc,v_numDocIdent,v_CodLote, v_CodAutorizacion, v_montoMonedaOrigen;

    EXIT WHEN cursor2%NOTFOUND;
     /* vFila_2 := vFila_2||'<tr>'||
                          '  <td width="370"><strong>'||v_formaPago||' '||v_numTarj||' '||v_FecVenc||' '||v_numDocIdent||'</strong></td>'||
                          '  <td align="left"><strong>'||v_moneda ||'</strong></td>'||
                          '  <td align="left"><strong>'||v_monto ||'</strong></td>'||
                          '  <td align="left"><strong>'||v_CodLote ||'</strong></td>'|| --Agregado por DVELIZ 17.12.2008
                          '  <td align="left"><strong>'||v_CodAutorizacion ||'</strong></td>'|| --Agregado por DVELIZ 17.12.2008
                          '</tr>';*/

        vFila_2 := vFila_2||'<tr>'||
                          '  <td width="34%" class="style1">'||TRIM(v_formaPago)||' '||TRIM(v_numTarj)||' '||TRIM(v_FecVenc)||' '||TRIM(v_numDocIdent)||'</td>'||
                          '  <td width="12%" class="style1">'||TRIM(v_moneda) ||'</td>'||
                          '  <td width="14%" class="style1">'|| CASE WHEN TRIM(UPPER(v_moneda)) = 'DOLARES' THEN '( ' || v_montoMonedaOrigen || ' )' ELSE ' ---------- ' END ||'</td>'||
                          '  <td width="14%" class="style1">'||TRIM(v_monto) ||'</td>'||
                          '  <td width="8%" class="style1">'||TRIM(v_CodLote) ||'</td>'|| --Agregado por DVELIZ 17.12.2008
                          '  <td width="18%" class="style1">'||TRIM(v_CodAutorizacion) ||'</td>'|| --Agregado por DVELIZ 17.12.2008
                          '</tr>';
    END
    LOOP;


    -------------------------------------------------------
    LOOP
    FETCH cursor3 INTO v_obsFormaPago,v_tipoDoc,v_nombreDe,v_direEnvio,v_obsPedido,v_comentRuteo;
    EXIT WHEN cursor3%NOTFOUND;
      /*vFila_3 := vFila_3||'<table width="800" border="0">'||
                          '<tr>'||
                          '  <td><FONT FACE="ARIAL" SIZE="4">OBS. FORMA PAGO:</FONT></td>'||
                          '  <td><strong>'||v_obsFormaPago||'</strong></td>'||
                          '</tr>'||
                          '<tr>'||
                          '  <td><FONT FACE="ARIAL" SIZE="4">'||v_tipoDoc||' a Nombre de :</FONT></td>'||
                          '  <td><strong>'||v_nombreDe||'</strong></td>'||
                          '</tr>'||
                          '<tr>'||
                          '  <td><FONT FACE="ARIAL" SIZE="4">DIRECCION :</FONT> </td>'||
                          '  <td><strong>'||v_direEnvio||'</strong></td>'||
                          '</tr>'||
                          '<tr>'||
                          '  <td><FONT FACE="ARIAL" SIZE="4">OBS. DEL PEDIDO : </FONT></td>'||
                          '  <td><strong>'||v_obsPedido||'</strong></td>'||
                          '</tr>'||
                          '<tr>'||
                          '  <td><FONT FACE="ARIAL" SIZE="4">COMENT. DEL RUTEO :</FONT> </td>'||
                          '  <td><strong>'||v_comentRuteo||'</strong></td>'||
                          '</tr>'||
                          '</table>';*/

      vFila_3 := vFila_3||'<table width="100%" border="0" cellpadding="0" cellspacing="0">'||
                          '<tr>'||
                          '  <td width="30%" class="style1">OBS. FORMA PAGO : </td>'||
                          '  <td width="70%" class="style4">'||TRIM(v_obsFormaPago)||'</td>'||
                          '</tr>'||
                          '<tr>'||
                          '  <td class="style1">'||TRIM(v_tipoDoc)||' A NOMBRE DE : </td>'||
                          '  <td class="style4">'||TRIM(v_nombreDe)||'</td>'||
                          '</tr>'||
                          '<tr>'||
                          '  <td class="style1">DIRECCION : </td>'||
                          '  <td class="style4">'||TRIM(v_direEnvio)||'</td>'||
                          '</tr>'||
                          '<tr>'||
                          '  <td class="style1">OBS. PEDIDO : </td>'||
                          '  <td class="style4">'||TRIM(v_obsPedido)||'</td>'||
                          '</tr>'||
                          '<tr>'||
                          '  <td class="style1">COMENT. RUTEO : </td>'||
                          '  <td class="style4">'||TRIM(v_comentRuteo)||'</td>'||
                          '</tr>';
                          -- '</table>';

                          if vCantidad >0 then
                          vFila_3 := vFila_3|| '<tr  >'||
                                             '<td class="style1"></td>'||
                                             '<td class="style4">'||vMensaje||'</td>'||
                                            '</tr>';
                          end if;

                     vFila_3 := vFila_3|| '</table>';
    END
    LOOP;

     dbms_output.put_line('CAN :'||vFila_4);


     vMsg_out := TRIM(C_INICIO_MSG) ||
                 vFila_4  ||
                 vFila_1  ||
                 TRIM(C_FORMA_PAGO)||
                 vFila_2 ||
                 TRIM(C_FIN_FORMA_PAGO)||
                 vFila_3 ||
                 TRIM(C_FIN_MSG) ;

         dbms_output.put_line('CANT :'||LENGTH(vMsg_out));
         dbms_output.put_line(vMsg_out);

     RETURN vMsg_out;

  END;

 FUNCTION VTA_OBTENER_DATA1(cCodGrupoCia_in IN CHAR,
  		   				             cCodLocal_in	   IN CHAR,
							               cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    int_total number;
    vCod_local_origen char(3);
  BEGIN
    /*select DECODE(X.COD_LOCAL_PROCEDENCIA,'998','998',X.cod_local)
    into   vCod_local_origen
    --from   VTA_PEDIDO_VTA_CAB x
    from   TMP_VTA_PEDIDO_VTA_CAB x
    where DECODE(COD_LOCAL_PROCEDENCIA,'998',x.num_ped_vta_origen, x.num_ped_vta)=cNumPedVta_in
    and   x.cod_grupo_cia = cCodGrupoCia_in
    --and   x.cod_local = cCodLocal_in;
    AND  X.COD_LOCAL=DECODE(X.COD_LOCAL,'998','998',cCodLocal_in);*/

   IF(TO_NUMBER(cNumPedVta_in)<20000)THEN
      vCod_local_origen:='998';
    ELSE
      vCod_local_origen:=cCodLocal_in;
    END IF;



    SELECT SUM(IM_TOTAL_PAGO) INTO int_total
    FROM TMP_VTA_FORMA_PAGO_PEDIDO X
    --WHERE X.NUM_PED_VTA=(select x.num_pedido_delivery from VTA_PEDIDO_VTA_CAB x where x.num_ped_vta=cNumPedVta_in)
    WHERE X.NUM_PED_VTA=cNumPedVta_in
    AND X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.COD_LOCAL=vCod_local_origen;

    OPEN curVta FOR
      SELECT X.DATOS_ATENDIDO,
             X.DATOS_CLI,
             X.DIRECCION,
             X.REF_DIREC,
             X.RUC,
             X.OBS_CLI_LOCAL,
             --TO_CHAR(X.MONTO_RED,'999,999,990.00'),}
             TO_CHAR(Y.VAL_NETO_PED_VTA,'999,999,990.00'),--07.09.09 Por solicitud de joliva
             TO_CHAR(int_total-Y.VAL_NETO_PED_VTA,'999,999,990.00'),
             --AGREGADO POR DVELIZ 23.03.2009
             Z.NOM_MOTORIZADO ||' '|| Z.APE_PAT_MOTORIZADO ||' '|| Z.APE_MAT_MOTORIZADO
      FROM TMP_CE_CAMPOS_COMANDA X,
           TMP_VTA_PEDIDO_VTA_CAB Y,
         --AGREGADO POR DVELIZ 23.03.2009
           DEL_MOTORIZADOS Z
           --TMP_VTA_FORMA_PAGO_PEDIDO Z
      WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
      AND X.COD_LOCAL= vCod_local_origen --cCodLocal_in
      AND X.NUM_PED_VTA=cNumPedVta_in
      AND X.COD_GRUPO_CIA=Y.COD_GRUPO_CIA
      AND X.COD_LOCAL=Y.COD_LOCAL--
      AND X.NUM_PED_VTA=Y.NUM_PED_VTA
      --AGREGADO POR DVELIZ 23.03.2009
      AND Y.COD_GRUPO_CIA = Z.COD_GRUPO_CIA
      AND Y.COD_LOCAL_PROCEDENCIA = Z.COD_LOCAL
      AND Y.COD_MOTORIZADO = Z.COD_MOTORIZADO;

    RETURN curVta;
  END;

  /****************************************************************************************/
   FUNCTION VTA_OBTENER_DATA2(cCodGrupoCia_in IN CHAR,
        		   				        cCodLocal_in	  IN CHAR,
      							          cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    vCod_local_origen char(3);
  BEGIN

  /* select DECODE(X.COD_LOCAL_PROCEDENCIA,'998','998',X.cod_local)
    into   vCod_local_origen
    --from   VTA_PEDIDO_VTA_CAB x
    FROM TMP_VTA_PEDIDO_VTA_CAB X
    where DECODE(COD_LOCAL_PROCEDENCIA,'998',x.num_ped_vta_origen, x.num_ped_vta)=cNumPedVta_in
--    where x.num_ped_vta=cNumPedVta_in
    and   x.cod_grupo_cia = cCodGrupoCia_in
    -- and   x.cod_local = cCodLocal_in;
    AND  X.COD_LOCAL=DECODE(X.COD_LOCAL,'998','998',cCodLocal_in);*/

   IF(TO_NUMBER(cNumPedVta_in)<20000)THEN
      vCod_local_origen:='998';
    ELSE
      vCod_local_origen:=cCodLocal_in;
    END IF;


    OPEN curVta FOR
    SELECT nvl(H.DESC_CORTA_FORMA_PAGO,' '),
          CASE Z.TIP_MONEDA
          WHEN '01' THEN 'Soles'
          WHEN '02' THEN 'Dolares'
          END,
          TO_CHAR(Z.IM_TOTAL_PAGO,'999,999,990.00'),
           NVL(Z.NUM_TARJ,' '),
           TO_CHAR(Z.FEC_VENC_TARJ,'DD/MM'),
           NVL(Z.NUM_DOC_IDENT,' '),
           NVL(Z.COD_LOTE, ' '),
           NVL(Z.COD_AUTORIZACION, ' '),
          TO_CHAR(Z.IM_PAGO,'999,999,990.00')
    FROM TMP_CE_CAMPOS_COMANDA X,
         TMP_VTA_PEDIDO_VTA_CAB Y,
         TMP_VTA_FORMA_PAGO_PEDIDO Z,
         VTA_FORMA_PAGO H
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.COD_LOCAL=vCod_local_origen--cCodLocal_in
    AND X.NUM_PED_VTA=cNumPedVta_in
    AND X.COD_GRUPO_CIA=Y.COD_GRUPO_CIA
    AND X.COD_LOCAL=Y.COD_LOCAL
    AND X.NUM_PED_VTA=Y.NUM_PED_VTA
    AND Y.COD_GRUPO_CIA=Z.COD_GRUPO_CIA
    AND Y.COD_LOCAL=Z.COD_LOCAL
    AND Y.NUM_PED_VTA=Z.NUM_PED_VTA
    AND Z.COD_FORMA_PAGO=H.COD_FORMA_PAGO;


  RETURN curVta;
  END;
/***************************************************************************/
  FUNCTION VTA_OBTENER_DATA3(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in	   IN CHAR,
                              cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    vCod_local_origen char(3);
  BEGIN
  /* select DECODE(X.COD_LOCAL_PROCEDENCIA,'998','998',X.cod_local)
    into   vCod_local_origen
    --from   VTA_PEDIDO_VTA_CAB x
    FROM TMP_VTA_PEDIDO_VTA_CAB X
    where DECODE(COD_LOCAL_PROCEDENCIA,'998',x.num_ped_vta_origen, x.num_ped_vta)=cNumPedVta_in
--    where x.num_ped_vta=cNumPedVta_in
    and   x.cod_grupo_cia = cCodGrupoCia_in
    --and   x.cod_local = cCodLocal_in;
    AND  X.COD_LOCAL=DECODE(X.COD_LOCAL,'998','998',cCodLocal_in);*/

    IF(TO_NUMBER(cNumPedVta_in)<20000)THEN
      vCod_local_origen:='998';
    ELSE
      vCod_local_origen:=cCodLocal_in;
    END IF;


    OPEN curVta FOR
    SELECT NVL(X.OBS_FORMA_PAGO,' '),
           NVL(H.DESC_COMP,' '),
           X.NOMBRE_DE,
           X.DIR_ENVIO,
           NVL(X.OBS_PED_VTA,' '),
           NVL(X.COMENTARIO,' ')
    FROM TMP_CE_CAMPOS_COMANDA X,
         --TMP_VTA_FORMA_PAGO_PEDIDO Y,
         --VTA_FORMA_PAGO Z,
         VTA_TIP_COMP H
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.COD_LOCAL=vCod_local_origen--cCodLocal_in
    --AND X.NUM_PED_VTA=(select x.num_pedido_delivery from VTA_PEDIDO_VTA_CAB x where x.num_ped_vta=cNumPedVta_in)
    --AND H.TIP_COMP=(select x.Tip_Comp_Pago from VTA_PEDIDO_VTA_CAB x where x.num_ped_vta=cNumPedVta_in);
    AND X.NUM_PED_VTA=cNumPedVta_in
    AND H.TIP_COMP=(select x.Tip_Comp_Pago from TMP_VTA_PEDIDO_VTA_CAB x where X.NUM_PED_VTA=cNumPedVta_in AND COD_LOCAL=vCod_local_origen);---

  RETURN curVta;
  END;

  /*************************************************************************************************************/
  FUNCTION VTA_OBTENER_DATA4(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in	   IN CHAR,
                             cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    vCod_local_origen char(3);
  BEGIN

    /*select DECODE(X.COD_LOCAL_PROCEDENCIA,'998','998',X.cod_local)
    into   vCod_local_origen
    --from   VTA_PEDIDO_VTA_CAB x
    from   TMP_VTA_PEDIDO_VTA_CAB x
    where DECODE(COD_LOCAL_PROCEDENCIA,'998',x.num_ped_vta_origen, x.num_ped_vta)=cNumPedVta_in
--    where x.num_ped_vta=cNumPedVta_in
    and   x.cod_grupo_cia = cCodGrupoCia_in
   -- and   x.cod_local = cCodLocal_in;
   AND  X.COD_LOCAL=DECODE(X.COD_LOCAL,'998','998',cCodLocal_in);*/

    IF(TO_NUMBER(cNumPedVta_in)<20000)THEN
      vCod_local_origen:='998';
    ELSE
      vCod_local_origen:=cCodLocal_in;
    END IF;


    OPEN curVta FOR
    SELECT NVL(Y.NUM_PED_VTA,' '),
            NVL(Y.NUM_PED_VTA_ORIGEN,' '),
            nvl(trim(to_char(y.fec_ped_vta,'DD/MM/YYYY HH24:MI:SS')),' ') fecha_del,

             --Agregado por dveliz 20.02.2009
             CASE Y.CAT_CLI_LOCAL
             WHEN '1' THEN 'VIP'
             WHEN '2' THEN 'FRECUENTE'
             WHEN '3' THEN 'IMPORTANTE (3)'
             WHEN '4' THEN 'REGULAR'
             WHEN '5' THEN 'IMPORTANTE (5)'
             WHEN '0' THEN 'BASICO'
             ELSE 'NINGUNA' END
    FROM TMP_CE_CAMPOS_COMANDA X,
         TMP_VTA_PEDIDO_VTA_CAB Y
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.COD_LOCAL=vCod_local_origen--cCodLocal_in
    AND X.NUM_PED_VTA=cNumPedVta_in
    AND X.COD_GRUPO_CIA=Y.COD_GRUPO_CIA
    AND X.COD_LOCAL=Y.COD_LOCAL --
    AND X.NUM_PED_VTA=Y.NUM_PED_VTA;
  RETURN curVta;
  END;

   /* ************************************************************************ */
  FUNCTION GET_NUM_PED_DELIVERY(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                                cNumPed_in        IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(14000):= '';
  vCod_local_origen char(3);
  BEGIN
/*
    select DECODE(X.COD_LOCAL_PROCEDENCIA,'998','998',X.cod_local)
    into   vCod_local_origen
    --from   VTA_PEDIDO_VTA_CAB x
    from   TMP_VTA_PEDIDO_VTA_CAB x
    --where x.num_ped_vta=(select x.num_pedido_delivery from VTA_PEDIDO_VTA_CAB x where x.num_ped_vta=cNumPed_in)
    where x.num_ped_vta=(select x.num_pedido_delivery from VTA_PEDIDO_VTA_CAB x where DECODE(COD_LOCAL_PROCEDENCIA,'998',x.num_ped_vta_origen, x.num_ped_vta)=cNumPed_in)
    and   x.cod_grupo_cia = cCodGrupoCia_in
    --and   x.cod_local = cCodLocal_in;
    AND  X.COD_LOCAL=DECODE(X.COD_LOCAL,'998','998',cCodLocal_in);
*/
      BEGIN
      SELECT  x.num_pedido_delivery
      INTO   vResultado
      --FROM  VTA_PEDIDO_VTA_CAB x
      FROM  VTA_PEDIDO_VTA_CAB x
      WHERE  X.COD_GRUPO_CIA=cCodGrupoCia_in
--      AND X.COD_LOCAL=cCodLocal_in
      AND x.num_ped_vta=cNumPed_in;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := 'N';
      END;
   RETURN vResultado;
   END;
/*****************************************************************/
  FUNCTION OBTENER_FORMATO_PALABRA(palabra   IN CHAR)
  RETURN VARCHAR2
  IS
    palabrafinal VARCHAR2(3000):='';
    cant    NUMBER:=0;
    tam     NUMBER:=34;
    salto  varchar2(4):='<br>';
    i number:=1;
    -- curVta FarmaCursor;
  BEGIN

     cant:= Length(palabra);
      /*for i in 0..cant
      loop
        palabrafinal:= palabrafinal||Substr(palabra,i,tam)||salto;
        --i:=i+tam;
      end
      loop;*/
      if(cant>0 and cant>tam)then
      while i<=cant
      loop
        palabrafinal:= palabrafinal||Substr(palabra,i,tam)||salto;
        DBMS_OUTPUT.PUT_LINE('palabrafinal :'||palabrafinal);
        i:=i+tam;
      end loop;
      else
      palabrafinal:=palabra;
      end if;


  RETURN palabrafinal;
  END;
 /* ********************************************************************** */
  FUNCTION CLI_F_GET_DATOS_PED_DELIVERY(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
  		   								                cNumPedido_in   IN CHAR)
  RETURN VARCHAR2
  IS

    vCadena varchar(3000);
  BEGIN

       BEGIN

       --MODIFICADO POR DVELIZ 12.12.2008
      -- SELECT C.cod_grupo_cia || '%' || C.cod_local_pro || '%' || C.num_ped_vta
       /*SELECT C.cod_grupo_cia || '%' || C.cod_local_procedencia || '%' || C.num_ped_vta
         INTO vCadena
         FROM TMP_VTA_PEDIDO_VTA_CAB C
        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL_ATENCION = cCodLocal_in
          AND C.NUM_PED_VTA = cNumPedido_in;*/

      SELECT C.cod_grupo_cia || '%' || C.cod_local_procedencia || '%' || C.num_pedido_delivery|| '%' ||
      C.IND_DELIV_AUTOMATICO || '%' || C.TIP_PED_VTA
         INTO vCadena
         FROM VTA_PEDIDO_VTA_CAB C
        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.NUM_PED_VTA = cNumPedido_in
          AND C.TIP_PED_VTA = '02';

       EXCEPTION
       WHEN NO_DATA_FOUND THEN
            vCadena:= 'N';
       END;


       return vCadena;
  END;

  --MODIFICADO POR DVELIZ 12.12.2008
/* ********************************************************************** */
  PROCEDURE CLI_P_ENVIA_ALERTA_DELIVERY(cCodGrupoCia_del_in IN CHAR,
                                        cCodLocal_del_in    IN CHAR,
  		   								                cNumPedido_del_in   IN CHAR,
                                        cCodLocal_in        IN CHAR)
  IS

    vCadena                 VARCHAR2(3000);
    vCuerpo                 VARCHAR2(32000);
    vNumPedVta              VARCHAR2(10);
    vFechaPedDelivery       VARCHAR2(10);
    vMontoPedDelivery       VARCHAR2(10);
    vTipoCompPed            VARCHAR2(10);
    vNroCompPed             VARCHAR2(10);
  BEGIN
    select trim(l.desc_corta_local)
      into vCadena
      from pbl_local l
     where l.cod_grupo_cia = cCodGrupoCia_del_in
       and l.cod_local = cCodLocal_in;

  --obtengo el numero de pedido de venta del local de ruteo
    SELECT  TRIM(TO_CHAR(MAX(NUM_PED_VTA)))
    INTO    vNumPedVta
    FROM    VTA_PEDIDO_VTA_CAB
    WHERE   IND_DELIV_AUTOMATICO = 'S'
      AND   NUM_PEDIDO_DELIVERY = cNumPedido_del_in
      AND   COD_LOCAL_PROCEDENCIA = cCodLocal_del_in
      AND   COD_GRUPO_CIA = cCodGrupoCia_del_in;

    --obtengo la fecha de venta del pedido delivery.
    SELECT  TO_CHAR(FEC_PED_VTA, 'dd/MM/yyyy'),
            TRIM(TO_CHAR(VAL_NETO_PED_VTA, '999,999,990.00'))
    INTO    vFechaPedDelivery, vMontoPedDelivery
    FROM    TMP_VTA_PEDIDO_VTA_CAB
    WHERE   NUM_PEDIDO_DELIVERY = cNumPedido_del_in
      AND   COD_LOCAL_PROCEDENCIA = cCodLocal_del_in
      AND   COD_GRUPO_CIA = cCodGrupoCia_del_in;

    --obtengo informacion del o los comprabsnte generados para este pedido
   /* SELECT  A.NUM_COMP_PAGO,
            CASE A.TIP_COMP_PAGO
            WHEN '01' THEN 'BOLETA'
            WHEN '02' THEN 'FACTURA'
            ELSE 'NOTA DE CREDITO' END
    INTO    vNroCompPed, vTipoCompPed
    FROM    VTA_COMP_PAGO A,
            VTA_PEDIDO_VTA_CAB B
    WHERE   A.NUM_PED_VTA = vNumPedVta
      AND   A.NUM_PED_VTA = B.NUM_PED_VTA
      AND   A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
      AND   A.COD_LOCAL = B.COD_LOCAL
      AND   A.COD_GRUPO_CIA = cCodGrupoCia_del_in;*/

  vCuerpo:= CLI_F_VAR_MENSAJE_ANULACION(cCodGrupoCia_del_in,
                                        cCodLocal_del_in,
                                        cNumPedido_del_in,
                                        cCodLocal_in,
                                        vCadena,
                                        vNumPedVta,
                                        vFechaPedDelivery,
                                        vMontoPedDelivery,
                                        vTipoCompPed,
                                        vNroCompPed);

  /*CLI_ENVIA_CORREO_INFORMACION(cCodGrupoCia_del_in,
                               cCodLocal_del_in,
                               'ANULACION PEDIDO DELIVERY: ',
                               'ALERTA',
                               'SE ANULO EL SIGUIENTE PEDIDO DELIVERY NRO: ' ||
                               cNumPedido_del_in || '  EN EL LOCAL ' ||
                               vCadena || ' </B>');*/

  CLI_ENVIA_CORREO_INFORMACION(cCodGrupoCia_del_in,
                               cCodLocal_del_in,
                               ccodlocal_in,
                               'ANULACION PEDIDO DELIVERY: ',
                               'ALERTA DE ANULACION DE PEDIDOS DE DELIVERY',vCuerpo);


  EXCEPTION
  WHEN OTHERS THEN
    RAISE;
  END;

  --CREADO POR DVELIZ 12.12.2008
  /*************************************************************************/
  FUNCTION CLI_F_VAR_MENSAJE_ANULACION(cCodGrupoCia_del_in IN CHAR,
                                       cCodLocal_del_in    IN CHAR,
  		   								               cNumPedido_del_in   IN CHAR,
                                       cCodLocal_in        IN CHAR,
                                       cDescLocal_in       IN CHAR,
                                       cNumPedVta_in       IN CHAR,
                                       cFechaPedDelivery   IN CHAR,
                                       cMontoPedDelivery   IN CHAR,
                                       cTipoCompPed        IN CHAR,
                                       cNroCompPed         IN CHAR)
  RETURN VARCHAR2
  IS
  vMensaje                VARCHAR2(32000):='MENSAJE';
  BEGIN

    vMensaje:='<table border="1" align="center" width="75%" >
                  <tr>
                    <td colspan="2" align="center" bgcolor="#FFFFFF" ><span class="style5"><B><span class="style6">ANULACION DE PEDIDO DELIVERY</span></span></td>
                  </tr>
                  <tr>
                    <td width="146" align="left"><span class="style5"><strong>Fecha de Pedido</strong></span></td>
                    <td width="298"><span class="style5">'||cFechaPedDelivery||'</span></td>
                  </tr>
                  <tr>
                    <td align="left"><span class="style5"><strong>Nro Pedido Delivery </strong></span></td>
                    <td><span class="style5">'|| cNumPedido_del_in ||'</span></td>
                  </tr>
                  <tr>
                    <td align="left"><span class="style5"><strong>Monto</strong></span></td>
                    <td><span class="style5">'|| cMontoPedDelivery ||'</span></td>
                  </tr>
                  <tr>
                    <td align="left"><span class="style5"><strong>Local Ruteo </strong></span></td>
                    <td><span class="style5">'|| cDescLocal_in ||'</span></td>
                  </tr>
                  <tr>
                    <td align="left"><span class="style5"><strong>Nro Pedido Local </strong></span></td>
                    <td><span class="style5">'|| cNumPedVta_in ||'</span></td>
                  </tr>'||
                  /*<tr>
                    <td align="left"><span class="style5"><strong>Tipo Comprobante </strong></span></td>
                    <td><span class="style5">'||cTipoCompPed||'</span></td>
                  </tr>
                  '<tr>
                    <td align="left"><span class="style5"><strong>Nro Comprobante </strong></span></td>
                    <td><span class="style5">'||cNroCompPed||'</span></td>
                  </tr>*/
               ' </table>';
    --DBMS_OUTPUT.PUT_LINE(vMensaje);

    RETURN vMensaje;
  EXCEPTION
  WHEN OTHERS THEN
    RETURN vMensaje;
    RAISE;

  END CLI_F_VAR_MENSAJE_ANULACION;

  --MODIFICADO POR DVELIZ 12.12.2008
/* ********************************************************************** */
  PROCEDURE CLI_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_del_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        vEnviarOper_in   IN CHAR DEFAULT 'N')
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_INTER_CE;
    CCReceiverAddress VARCHAR2(120) := 'joliva, dubilluz';
    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN

   if TRIM(cCodLocal_del_in) = '999' then

        SELECT T.LLAVE_TAB_GRAL
        INTO   ReceiverAddress
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 251
        AND    T.COD_APL = 'PTOVENTA'
        AND    T.COD_TAB_GRAL = 'EMAIL_ALERTA';

   else if TRIM(cCodLocal_del_in) = '998' then

        SELECT T.LLAVE_TAB_GRAL
        INTO   ReceiverAddress
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 252
        AND    T.COD_APL = 'PTOVENTA'
        AND    T.COD_TAB_GRAL = 'EMAIL_ALERTA';

        else
           ReceiverAddress := 'OPERADOR';
        end if;
   end if;

    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,
                             vTitulo_in,
                             mesg_body,
                             CCReceiverAddress,
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;


  FUNCTION IMP_DATOS_DELIVERY_L(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cIpServ_in        IN CHAR)
  RETURN VARCHAR2
  IS
  vMsg_out varchar2(32767):= '';

  vFila_Consejos     varchar2(22767):= '';
  vFila_1 VARCHAR2(2800):='';
  vFila_2 VARCHAR2(2800):='';
  vFila_3 VARCHAR2(2800):='';
  vFila_4 VARCHAR2(2800):='';

  v_usuario varchar2(200);
  v_nomcli  varchar2(200);
  v_direcc  varchar2(400);
  v_referen varchar2(400);
  v_ruc     varchar2(200);
  v_obs     varchar2(400);

  v_montoRed   varchar2(200);
  v_vuelto     varchar2(200);
  v_MontTotal    varchar2(200);

  v_formaPago    varchar2(200);
  v_moneda      varchar2(200);
  v_monto       varchar2(200);

  v_CodLote       varchar2(200);
  v_CodAutorizacion       varchar2(200);

  v_obsFormaPago     varchar2(400);
  v_tipoDoc          varchar2(200);
  v_nombreDe         varchar2(200);
  v_direEnvio        varchar2(400);
  v_obsPedido        varchar2(400);
  v_comentRuteo      varchar2(400);
  v_numPedVta      varchar2(200);
  v_numPedDel      varchar2(200);

  v_numTarj      varchar2(20);
  v_FecVenc      varchar2(20);
  v_numDocIdent  varchar2(15);

  v_fechaComanda varchar2(300);

  v_categoriaCliente    varchar2(20);

  v_nombreMotorizado    VARCHAR2(1000);

  v_UsuAten      varchar2(200);
      v_NumTel      varchar2(200);

  cursor1 FarmaCursor:=VTA_OBTENER_DATA1_L(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);
  cursor2 FarmaCursor:=VTA_OBTENER_DATA2_L(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);
  --cursor3 FarmaCursor:=VTA_OBTENER_DATA3(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);
  cursor4 FarmaCursor:=VTA_OBTENER_DATA4_L(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);

  BEGIN


    LOOP
    FETCH cursor4  INTO v_numPedVta,v_fechaComanda;
    EXIT WHEN cursor4%NOTFOUND;

        vFila_4 := vFila_4 || '<table width="100%" border="0" cellpadding="1" cellspacing="1">'||
                              '<tr>'||
                                '<td width="50%" align="right" class="style1">FECHA Y HORA : </td>'||
                                '<td width="50%" class="style1">'||TRIM(v_fechaComanda)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td align="right" class="style1">N�PEDIDO : </td>'||
                                '<td class="style1">'||TRIM(v_numPedVta)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td></td>'||
                                '<td></td>'||
                              '</tr>'||
                            '</table>';
    END
    LOOP;
    -------------------------------------------------------------------------------
      LOOP
      FETCH cursor1 INTO v_usuario,v_nomcli,v_direcc,v_NumTel,v_montoRed,v_vuelto,v_MontTotal;
      EXIT WHEN cursor1%NOTFOUND;
       DBMS_OUTPUT.PUT_LINE('..............');

        vFila_1 := vFila_1 || '<table width="100%" border="0" cellpadding="0" cellspacing="0">'||
                              '<tr>'||
                                '<td width="100" class="style1">ATENDIDO : </td>'||
                                '<td width="310" class="style4">'||TRIM(v_usuario)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">NOM / CLI : </td>'||
                                '<td class="style4">'||TRIM(v_nomcli)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">DIRECC : </td>'||
                                '<td class="style4">'||TRIM(v_direcc)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">MONTO RED : </td>'||
                                '<td class="style4">'||TRIM(v_montoRed)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">VUELTO : </td>'||
                                '<td class="style4">'||TRIM(v_vuelto)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">TOTAL : </td>'||
                                '<td class="style4">'||TRIM(v_MontTotal)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td></td>'||
                                '<td></td>'||
                              '</tr>'||
                            '</table>';
     END
     LOOP;
    -------------------------------------------------
    LOOP
    FETCH cursor2 INTO v_formaPago,v_moneda,v_monto,v_numTarj;
    EXIT WHEN cursor2%NOTFOUND;

        vFila_2 := vFila_2||'<tr>'||
                          '  <td width="50%" class="style1">'||TRIM(v_formaPago)||' '||TRIM(v_numTarj)||'</td>'||
                          '  <td width="12%" class="style1">'||TRIM(v_moneda) ||'</td>'||
                          '  <td width="12%" class="style1">'||TRIM(v_monto) ||'</td>'||
                          '</tr>';
    END
    LOOP;
    -------------------------------------------------------
    /*LOOP
    FETCH cursor3 INTO v_obsFormaPago,v_tipoDoc,v_nombreDe,v_direEnvio,v_obsPedido,v_comentRuteo;
    EXIT WHEN cursor3%NOTFOUND;

      vFila_3 := vFila_3||'<table width="100%" border="0" cellpadding="0" cellspacing="0">'||
                          '<tr>'||
                          '  <td width="30%" class="style1">OBS. FORMA PAGO : </td>'||
                          '  <td width="70%" class="style4">'||TRIM(v_obsFormaPago)||'</td>'||
                          '</tr>'||
                          '<tr>'||
                          '  <td class="style1">'||TRIM(v_tipoDoc)||' A NOMBRE DE : </td>'||
                          '  <td class="style4">'||TRIM(v_nombreDe)||'</td>'||
                          '</tr>'||
                          '<tr>'||
                          '  <td class="style1">DIRECCION : </td>'||
                          '  <td class="style4">'||TRIM(v_direEnvio)||'</td>'||
                          '</tr>'||
                          '<tr>'||
                          '  <td class="style1">OBS. PEDIDO : </td>'||
                          '  <td class="style4">'||TRIM(v_obsPedido)||'</td>'||
                          '</tr>'||
                          '<tr>'||
                          '  <td class="style1">COMENT. RUTEO : </td>'||
                          '  <td class="style4">'||TRIM(v_comentRuteo)||'</td>'||
                          '</tr>'||
                        '</table>';
    END
    LOOP;*/

     dbms_output.put_line('CAN :'||vFila_4);


     vMsg_out := TRIM(C_INICIO_MSG_L) ||
                 vFila_4  ||
                 vFila_1  ||
                 TRIM(C_FORMA_PAGO_L)||
                 vFila_2 ||
                 TRIM(C_FIN_FORMA_PAGO_L)||
                 --vFila_3 ||
                 TRIM(C_FIN_MSG_L) ;

         dbms_output.put_line('CANT :'||LENGTH(vMsg_out));
         dbms_output.put_line(vMsg_out);

     RETURN vMsg_out;

  END;


  /****************************************************************************************/
  FUNCTION VTA_OBTENER_DATA1_L(cCodGrupoCia_in IN CHAR,
  		   				             cCodLocal_in	   IN CHAR,
							               cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    int_total number;
    vCod_local_origen char(3);
  BEGIN

    SELECT SUM(IM_TOTAL_PAGO) INTO int_total
    FROM VTA_FORMA_PAGO_PEDIDO X
    WHERE X.NUM_PED_VTA=cNumPedVta_in
    AND X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.COD_LOCAL=cCodLocal_in;

    OPEN curVta FOR
       SELECT A.USU_CREA_PED_VTA_CAB,
              A.NOM_CLI_PED_VTA,
              A.DIR_CLI_PED_VTA,
              A.NUM_TELEFONO,
              A.VAL_REDONDEO_PED_VTA,
              TO_CHAR(int_total-A.VAL_NETO_PED_VTA,'999,999,990.00'),
              A.VAL_NETO_PED_VTA
        FROM VTA_PEDIDO_VTA_CAB A
        WHERE A.COD_GRUPO_CIA='001'
        AND A.COD_LOCAL=cCodLocal_in
        AND A.NUM_PED_VTA=cNumPedVta_in;

    RETURN curVta;
  END;

    /****************************************************************************************/
   FUNCTION VTA_OBTENER_DATA2_L(cCodGrupoCia_in IN CHAR,
        		   				        cCodLocal_in	  IN CHAR,
      							          cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    vCod_local_origen char(3);
  BEGIN
    OPEN curVta FOR
    SELECT nvl(H.DESC_CORTA_FORMA_PAGO,' '),
          CASE Z.TIP_MONEDA WHEN '01' THEN 'Soles' WHEN '02' THEN 'Dolares' END,
          TO_CHAR(Z.IM_TOTAL_PAGO,'999,999,990.00'),
           NVL(Z.NUM_TARJ,' ')
    FROM VTA_PEDIDO_VTA_CAB Y,
         VTA_FORMA_PAGO_PEDIDO Z,
         VTA_FORMA_PAGO H
    WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
    AND Y.COD_LOCAL=cCodLocal_in
    AND Y.NUM_PED_VTA=cNumPedVta_in
    AND Y.COD_GRUPO_CIA=Z.COD_GRUPO_CIA
    AND Y.COD_LOCAL=Z.COD_LOCAL
    AND Y.NUM_PED_VTA=Z.NUM_PED_VTA
    AND Z.COD_FORMA_PAGO=H.COD_FORMA_PAGO;

  RETURN curVta;
  END;

  /*************************************************************************************************************/
  FUNCTION VTA_OBTENER_DATA4_L(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in	   IN CHAR,
                             cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    vCod_local_origen char(3);
  BEGIN

    OPEN curVta FOR
    SELECT NVL(A.NUM_PED_VTA,' '),
           NVL(TRIM(TO_CHAR(A.FEC_PED_VTA,'DD/MM/YYYY HH24:MI:SS')),' ') FECHA_DEL
    FROM VTA_PEDIDO_VTA_CAB A
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.NUM_PED_VTA=cNumPedVta_in;
  RETURN curVta;
  END;

  /* ********************************************************************************************** */

  PROCEDURE CAJ_P_UP_DATOS_DELIVERY(cCodGrupoCia_in 	IN CHAR,
                                    cCodLocal_in    	IN CHAR,
                                    cNumPedVta_in 		IN CHAR,
                                    cEstPedVta_in		  IN CHAR,
                                    cUsuModPedVtaCab_in IN CHAR,
                                    cCodcli             IN CHAR,
                                    cNomCli             IN CHAR,
                                    cTelCli             IN CHAR,
                                    cDirCli             IN CHAR,
                                    cNroCli             IN CHAR)
  IS
  cTipCompPago CHAR(2);
  BEGIN

  SELECT A.TIP_COMP_PAGO INTO cTipCompPago
  FROM VTA_PEDIDO_VTA_CAB A
  WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
  AND A.COD_LOCAL=cCodLocal_in
  AND A.NUM_PED_VTA=cNumPedVta_in
  AND A.EST_PED_VTA=cEstPedVta_in;

   IF (cTipCompPago <> COD_TIP_COMP_FACTURA) THEN
  	--ACTUALIZA DATOS DELIVERY  (pedido tipo 02, indicador automatico N)
  	UPDATE VTA_PEDIDO_VTA_CAB  X
    SET X.COD_CLI_LOCAL=cCodcli,
        X.NOM_CLI_PED_VTA=cNomCli,
        X.NUM_TELEFONO=cTelCli,
        X.DIR_CLI_PED_VTA=cDirCli,
        X.DNI_CLI=cNroCli,
        X.NUM_PEDIDO_DELIVERY=cNumPedVta_in --forma de identificar pedido delivery local
		  WHERE X.COD_GRUPO_CIA= cCodGrupoCia_in
		  AND	X.COD_LOCAL= cCodLocal_in
		  AND	X.NUM_PED_VTA= cNumPedVta_in
      AND X.EST_PED_VTA=cEstPedVta_in
      AND X.IND_DELIV_AUTOMATICO='N';
   ELSE
    UPDATE VTA_PEDIDO_VTA_CAB  X
    SET X.NUM_TELEFONO=cTelCli,
        X.NUM_PEDIDO_DELIVERY=cNumPedVta_in --forma de identificar pedido delivery local
		  WHERE X.COD_GRUPO_CIA= cCodGrupoCia_in
		  AND	X.COD_LOCAL= cCodLocal_in
		  AND	X.NUM_PED_VTA= cNumPedVta_in
      AND X.EST_PED_VTA=cEstPedVta_in
      AND X.IND_DELIV_AUTOMATICO='N';
   END IF;

  END;
 /* ********************************************************************************************** */
  FUNCTION CLI_F_VAR_PARTIDA_LLEGADA(
                                     cCodGrupoCia_del_in IN CHAR,
                                     cCodLocal_del_in    IN CHAR,
  		   								             cNumPedido_del_in   IN CHAR
                                     )
  RETURN VARCHAR2
  IS

  vpartida  VTA_PEDIDO_VTA_CAB.punto_llegada%type;
  vllegada  VTA_PEDIDO_VTA_CAB.punto_llegada%type;
  BEGIN

       begin
       SELECT nvl(l.direc_local_corta,'X')
       INTO   vpartida
       FROM   pbl_local l
       WHERE  l.COD_GRUPO_CIA = cCodGrupoCia_del_in
       AND    l.COD_LOCAL = cCodLocal_del_in;


       SELECT nvl(c.punto_llegada,'X')
       INTO   vllegada
       FROM   VTA_PEDIDO_VTA_CAB C
       WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_del_in
       AND    C.COD_LOCAL = cCodLocal_del_in
       AND    C.NUM_PED_VTA = cNumPedido_del_in;

       exception
       when others then
            vpartida := 'X';
            vllegada := 'X';
       end;

  return vpartida||'�'||vllegada;

  END;

 /* ***************************************************************************************** */
  FUNCTION CLI_F_VAR_EXIST_LOTE_PED(
                                    cCodGrupoCia_in 	   IN CHAR,
						   	                    cCodLocal_in    	   IN CHAR,
                                    cCodLocalAtencion_in IN CHAR,
							                      cNumPedVtaDel_in 	   IN CHAR
                                   )
  RETURN VARCHAR2
  IS
  nCantDetalle  number;
  nCantProdLote number;
  vResultado char(2):= 'N';
  BEGIN
    begin
        /*
        SELECT COUNT(1)
        into   nCantProdLote
        FROM   TMP_VTA_PEDIDO_VTA_DET TMP_DET,
               TMP_VTA_INSTITUCIONAL_DET INS
        WHERE  TMP_DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND	   TMP_DET.COD_LOCAL = cCodLocal_in
        AND	   TMP_DET.NUM_PED_VTA = cNumPedVtaDel_in
        AND    TMP_DET.EST_PED_VTA_DET = ESTADO_ACTIVO
        AND    INS.COD_GRUPO_CIA = TMP_DET.COD_GRUPO_CIA
        AND    INS.COD_LOCAL= cCodLocalAtencion_in
        AND    INS.NUM_PED_VTA = TMP_DET.NUM_PED_VTA
        AND    INS.COD_PROD = TMP_DET.COD_PROD;
        */
        SELECT COUNT(1)
        into   nCantProdLote
        FROM   TMP_VTA_INSTITUCIONAL_DET INS
        WHERE  INS.COD_GRUPO_CIA = cCodGrupoCia_in
        AND	   INS.COD_LOCAL = cCodLocalAtencion_in
        AND	   INS.NUM_PED_VTA = cNumPedVtaDel_in;


        SELECT COUNT(1)
        into   nCantDetalle
        FROM   TMP_VTA_PEDIDO_VTA_DET TMP_DET
        WHERE  TMP_DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND	   TMP_DET.COD_LOCAL = cCodLocal_in
        AND	   TMP_DET.NUM_PED_VTA = cNumPedVtaDel_in
        AND    TMP_DET.EST_PED_VTA_DET = ESTADO_ACTIVO;

    exception
    when others then
         nCantProdLote := 'P';
         nCantDetalle := 'D';
         vResultado := 'N';
    end ;

    if nCantProdLote = nCantDetalle then
       vResultado := 'S';
    end if;

    return trim(vResultado);

  END;

 /* ***************************************************************************************** */


END;

/