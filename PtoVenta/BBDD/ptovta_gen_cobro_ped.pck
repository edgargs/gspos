CREATE OR REPLACE PACKAGE PTOVENTA."PTOVTA_GEN_COBRO_PED" is

TYPE FarmaCursor IS REF CURSOR;

--Descripcion: Indicador para saber si se llama a la nueva ventana de cobro o no
--Fecha       Usuario   Comentario
--16/02/2010  ASOSA     Creación
FUNCTION PVTA_F_ELEGIR_VENT_COBRO
RETURN CHAR;

--Descripcion: Indicador para saber si se debe llamar a la nueva ventana de cobro o se debe de cobrar en el resumen de venta
--Fecha       Usuario   Comentario
--16/02/2010  ASOSA     Creación
FUNCTION PVTA_F_NEW_VENT_COBRO(cCia_in IN CHAR,
                               cLocal_in IN CHAR,
                               cNumPed_in IN CHAR)
RETURN CHAR;

--Descripcion: Inssertar las formas de pago con las cuales se pago un pedido. (Se agrego el nro de DNI del propietario de la tarjeta, el codigo de voucher o autorizacion y el codigo de lote)
--Fecha       Usuario   Comentario
--23/02/2010  ASOSA     Creación
PROCEDURE PVTA_P_INS_FORM_PAGO_PED(cCodGrupoCia_in 	 	     IN CHAR,
	                                   	   cCodLocal_in    	 	     IN CHAR,
										                     cCodFormaPago_in   	   IN CHAR,
									   	                   cNumPedVta_in   	 	     IN CHAR,
									   	                   nImPago_in		 		       IN NUMBER,
										                     cTipMoneda_in			     IN CHAR,
									  	                   nValTipCambio_in 	 	   IN NUMBER,
								   	  	                 nValVuelto_in  	 	     IN NUMBER,
								   	  	                 nImTotalPago_in 		     IN NUMBER,
									  	                   cNumTarj_in  		 	     IN CHAR,
									  	                   cFecVencTarj_in  		   IN CHAR,
									  	                   cNomTarj_in  	 		     IN CHAR,
                                         cCanCupon_in  	 		     IN NUMBER,
									  	                   cUsuCreaFormaPagoPed_in IN CHAR,
                                         cDNI_in                 IN CHAR,
                                         vCodvou_in              IN VARCHAR,
                                         cLote_in                IN VARCHAR);

--Descripcion: Obtener la informacion de la tarjeta ingresada y la forma de pago a la que pertenece
--Fecha       Usuario   Comentario
--24/02/2010  ASOSA     Creación
FUNCTION PVTA_F_OBTENER_TARJETA(cCodCia_in IN CHAR,
                                cCodTarj_in IN VARCHAR)
RETURN FarmaCursor;

--Descripcion: Saber si el pedido es recarga virtual y ademas con convenio credito
--Fecha       Usuario   Comentario
--24/02/2010  ASOSA     Creación
FUNCTION PVTA_F_RECVIR_CONVCRED(cCia_in IN CHAR,
                               cLocal_in IN CHAR,
                               cNumPed_in IN CHAR)
RETURN VARCHAR2;

--Descripcion: Obtener la forma de pago de un convenio
--Fecha       Usuario   Comentario
--01/03/2010  ASOSA     Creación
FUNCTION PVTA_F_OBTENER_FPAGO(cCia_in IN CHAR,
                              cConv_in IN CHAR)
RETURN VARCHAR2;

--Descripcion: Obtengo el detalle de un pedido
--Fecha       Usuario   Comentario
--05/03/2010  ASOSA     Creación
FUNCTION PVTA_LISTA_DETA_PED(cCodGrupoCia_in IN CHAR,
  		   						           cCod_Local_in   IN CHAR,
								               cNum_Ped_Vta_in IN CHAR)
RETURN FarmaCursor;

--Descripcion: Obtengo el valor de tolerancia de diferencia solo cuando la tarjeta es en dolares
--Fecha       Usuario   Comentario
--26/05/2010  ASOSA     Creación
FUNCTION PVTA_F_GET_TOLERA_DOLARES(cCodGia_in IN CHAR)
RETURN VARCHAR2;

--Descripcion: Enviar un correo informando que se cobro un pedido utilizando "el cambio de precio ¡¡¡"
--Fecha       Usuario   Comentario
--26/05/2010  ASOSA     Creación
PROCEDURE PVTA_P_ENVIAR_MAIL_DIFERENCIA(cCodCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        cNumPed_in IN CHAR,
                                        vSoles_in IN VARCHAR2,
                                        vDolares_in IN VARCHAR2,
                                        vMonto_in IN VARCHAR2,
                                        vMonto2_in IN VARCHAR2,
                                        vDiferencia_in IN VARCHAR2);

--Descripcion: Salvo las forma de pago en cierre de dia"
--Fecha       Usuario   Comentario
--03/06/2010  jquispe     Creación
PROCEDURE PVTA_P_CHANGE_FORM_PAGO_PED(cCodGrupoCia_in 	 	     IN CHAR,
	                                   	   cCodLocal_in    	 	     IN CHAR,
										                     cCodFormaPago_in   	   IN CHAR,
									   	                   cNumPedVta_in   	 	     IN CHAR,
									   	                   nImPago_in		 		       IN NUMBER,
										                     cTipMoneda_in			     IN CHAR,
									  	                   nValTipCambio_in 	 	   IN NUMBER,
								   	  	                 nValVuelto_in  	 	     IN NUMBER,
								   	  	                 nImTotalPago_in 		     IN NUMBER,
									  	                   cNumTarj_in  		 	     IN CHAR,
									  	                   cFecVencTarj_in  		   IN CHAR,
									  	                   cNomTarj_in  	 		     IN CHAR,
                                         cCanCupon_in  	 		     IN NUMBER,
									  	                   cUsuCreaFormaPagoPed_in IN CHAR,
                                         cDNI_in                 IN CHAR,
                                         vCodvou_in              IN VARCHAR,
                                         cLote_in                IN VARCHAR);

--Descripcion: Save de forma de pago en cierre de dia
--Fecha       Usuario   Comentario
--03/06/2010  jquispe     Creación
PROCEDURE PVTA_P_SAVE_FORM_PAGO_PED(cCodGrupoCia_in 	 	     IN CHAR,
	                                  cCodLocal_in    	 	     IN CHAR,
									   	              cNumPedVta_in   	 	     IN CHAR
									   	                );

--Descripcion: Borrar de forma de pago en cierre de dia"
--Fecha       Usuario   Comentario
--03/06/2010  jquispe     Creación
PROCEDURE PVTA_P_DEL_FORM_PAGO_PED(cCodGrupoCia_in 	 	     IN CHAR,
                              	   cCodLocal_in    	 	 IN CHAR,
						   	                   cNumPedVta_in   	 	 IN CHAR
									   	                );



END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVTA_GEN_COBRO_PED" is

/*************************************************************************************************************/

FUNCTION PVTA_F_ELEGIR_VENT_COBRO
RETURN CHAR
IS
indicador CHAR(1);
BEGIN
     SELECT a.llave_tab_gral INTO indicador
     FROM pbl_tab_gral a
     WHERE a.id_tab_gral='332'
     AND a.cod_apl='PTO_VENTA'
     AND a.est_tab_gral='A';

     RETURN indicador;
END;

/*************************************************************************************************************/

FUNCTION PVTA_F_NEW_VENT_COBRO(cCia_in IN CHAR,
                               cLocal_in IN CHAR,
                               cNumPed_in IN CHAR)
RETURN CHAR
IS
ind CHAR(1):='S';
cant NUMBER(8);
cant2 NUMBER(8);
BEGIN
     --si el pedido tiene convenio con forma de pago fija
     SELECT COUNT(*) INTO cant
     FROM vta_pedido_vta_cab r
     WHERE r.cod_grupo_cia=cCia_in
     AND r.cod_local=cLocal_in
     AND r.num_ped_vta=cNumPed_in
     AND r.cod_convenio IN (SELECT M.COD_CONVENIO
                            FROM con_mae_convenio m, CON_CONVENIO_X_FORMA_PAGO E
                            WHERE M.EST_CONVENIO='A'
                            AND M.COD_CONVENIO=E.COD_CONVENIO);
     IF cant>0 THEN
        --si el pedido es de recarga virtual
        SELECT COUNT(*) INTO cant2
        FROM vta_pedido_vta_det g,lgt_prod_virtual l
        WHERE g.cod_grupo_cia=cCia_in
        AND g.cod_local=cLocal_in
        AND g.num_ped_vta=cNumPed_in
        AND g.cod_prod=l.cod_prod
        AND g.cod_grupo_cia=l.cod_grupo_cia
        AND l.tip_prod_virtual='R';
        IF cant2=0 THEN
           ind:='N';
        END IF;
     END IF;
     RETURN ind;
END;

/*************************************************************************************************************/

PROCEDURE PVTA_P_INS_FORM_PAGO_PED(cCodGrupoCia_in 	 	     IN CHAR,
	                                   	   cCodLocal_in    	 	     IN CHAR,
										                     cCodFormaPago_in   	   IN CHAR,
									   	                   cNumPedVta_in   	 	     IN CHAR,
									   	                   nImPago_in		 		       IN NUMBER,
										                     cTipMoneda_in			     IN CHAR,
									  	                   nValTipCambio_in 	 	   IN NUMBER,
								   	  	                 nValVuelto_in  	 	     IN NUMBER,
								   	  	                 nImTotalPago_in 		     IN NUMBER,
									  	                   cNumTarj_in  		 	     IN CHAR,
									  	                   cFecVencTarj_in  		   IN CHAR,
									  	                   cNomTarj_in  	 		     IN CHAR,
                                         cCanCupon_in  	 		     IN NUMBER,
									  	                   cUsuCreaFormaPagoPed_in IN CHAR,
                                         cDNI_in                 IN CHAR,
                                         vCodvou_in              IN VARCHAR,
                                         cLote_in                IN VARCHAR) IS
  BEGIN
    INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA, COD_LOCAL, COD_FORMA_PAGO, NUM_PED_VTA,
	   							  	                IM_PAGO, TIP_MONEDA, VAL_TIP_CAMBIO, VAL_VUELTO,
								  	                  IM_TOTAL_PAGO, NUM_TARJ, FEC_VENC_TARJ, NOM_TARJ,
								  	                  USU_CREA_FORMA_PAGO_PED, CANT_CUPON, Dni_Cli_Tarj, Cod_Autorizacion, Cod_Lote)
                              VALUES (cCodGrupoCia_in, cCodLocal_in, cCodFormaPago_in, cNumPedVta_in,
									                    nImPago_in, cTipMoneda_in, nValTipCambio_in, nValVuelto_in,
									                    nImTotalPago_in, cNumTarj_in, cFecVencTarj_in, cNomTarj_in,
									                    cUsuCreaFormaPagoPed_in, cCanCupon_in, cDNI_in, vCodvou_in, cLote_in);
  END;

/*************************************************************************************************************/

FUNCTION PVTA_F_OBTENER_TARJETA(cCodCia_in IN CHAR,
                                cCodTarj_in IN VARCHAR)
RETURN FarmaCursor
IS
cur FarmaCursor;
BEGIN
     OPEN cur FOR
          SELECT a.bin  || 'Ã' ||
                 a.desc_prod  || 'Ã' ||
                 a.cod_forma_pago  || 'Ã' ||
                 b.desc_corta_forma_pago
          FROM vta_fpago_tarj a, vta_forma_pago b
          WHERE a.cod_grupo_cia=b.cod_grupo_cia
          AND a.cod_grupo_cia=cCodCia_in
          AND a.cod_forma_pago=b.cod_forma_pago
          AND cCodTarj_in LIKE trim(a.bin)||'%';
     RETURN cur;
END;

/*************************************************************************************************************/

FUNCTION PVTA_F_RECVIR_CONVCRED(cCia_in IN CHAR,
                               cLocal_in IN CHAR,
                               cNumPed_in IN CHAR)
RETURN VARCHAR2
IS
fpago VARCHAR2(100):='N';
cant NUMBER(5,2);
cant2 NUMBER(5,2);
BEGIN
     SELECT COUNT(*) INTO cant
     FROM vta_pedido_vta_cab r
     WHERE r.cod_grupo_cia=cCia_in
     AND r.cod_local=cLocal_in
     AND r.num_ped_vta=cNumPed_in
     AND r.cod_convenio IN (SELECT M.COD_CONVENIO
                            FROM con_mae_convenio m, CON_CONVENIO_X_FORMA_PAGO E
                            WHERE M.EST_CONVENIO='A'
                            AND M.COD_CONVENIO=E.COD_CONVENIO);
                            --AND m.porc_copago_conv=100);
     IF cant>0 THEN --para mayor seguridad, si hubo un error en amarrar los convenios o la recarga se notara aca.
        --si el pedido es de recarga virtual
        SELECT COUNT(*) INTO cant2
        FROM vta_pedido_vta_det g,lgt_prod_virtual l
        WHERE g.cod_grupo_cia=cCia_in
        AND g.cod_local=cLocal_in
        AND g.num_ped_vta=cNumPed_in
        AND g.cod_prod=l.cod_prod
        AND g.cod_grupo_cia=l.cod_grupo_cia
        AND l.tip_prod_virtual='R';
        IF cant2>0 THEN
           SELECT d.cod_forma_pago || ',' || d.desc_corta_forma_pago INTO fpago
           FROM vta_pedido_vta_cab a, con_mae_convenio b, con_convenio_x_forma_pago c, vta_forma_pago d
           WHERE a.num_ped_vta=cNumPed_in
           AND a.cod_grupo_cia=cCia_in
           AND a.cod_local=cLocal_in
           AND a.cod_convenio=b.cod_convenio
           AND b.cod_convenio=c.cod_convenio
           AND c.cod_forma_pago=d.cod_forma_pago;
        END IF;
     END IF;
     RETURN fpago;
END;

/*************************************************************************************************************/

FUNCTION PVTA_F_OBTENER_FPAGO(cCia_in IN CHAR,
                              cConv_in IN CHAR)
RETURN VARCHAR2
IS
fpago VARCHAR2(100):='N';
cant NUMBER(8):=0;
BEGIN
     SELECT COUNT(*) INTO cant
     FROM con_convenio_x_forma_pago a, vta_forma_pago b
     WHERE a.cod_grupo_cia=b.cod_grupo_cia
     AND a.cod_forma_pago=b.cod_forma_pago
     AND a.cod_grupo_cia=cCia_in
     AND a.cod_convenio=cConv_in;
     IF cant>0 THEN
       SELECT a.cod_forma_pago || ',' || b.desc_corta_forma_pago INTO fpago
       FROM con_convenio_x_forma_pago a, vta_forma_pago b
       WHERE a.cod_grupo_cia=b.cod_grupo_cia
       AND a.cod_forma_pago=b.cod_forma_pago
       AND a.cod_grupo_cia=cCia_in
       AND a.cod_convenio=cConv_in;
     END IF;
     RETURN fpago;
END;

/*************************************************************************************************************/

FUNCTION PVTA_LISTA_DETA_PED(cCodGrupoCia_in IN CHAR,
  		   						           cCod_Local_in   IN CHAR,
								               cNum_Ped_Vta_in IN CHAR)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
		SELECT NVL(TO_CHAR(DET.COD_PROD),' ')        || 'Ã' ||
       		   NVL(TO_CHAR(P.DESC_PROD),' ')         || 'Ã' ||
	  		   NVL(TO_CHAR(DET.UNID_VTA),' ')        || 'Ã' ||
	   		   TO_CHAR(NVL(DET.VAL_PREC_VTA,0),'999,999,990.000')  || 'Ã' ||
	   		   TO_CHAR(NVL(DET.CANT_ATENDIDA,0),'999,999,990.00')   || 'Ã' ||
	   		   TO_CHAR( ( NVL(DET.VAL_PREC_TOTAL,0) ),'999,999,990.00')|| 'Ã' ||
           nvl(U.LOGIN_USU,' ')
	    FROM  VTA_PEDIDO_VTA_DET DET,
     		  LGT_PROD P,
          pbl_usu_local u
	    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in   AND
			  DET.COD_LOCAL     = cCod_Local_in     AND
			  DET.NUM_PED_VTA   = cNum_Ped_Vta_in	AND
			  P.COD_GRUPO_CIA   = DET.COD_GRUPO_CIA AND
      	P.COD_PROD        = DET.COD_PROD      and
        DET.COD_GRUPO_CIA = U.COD_GRUPO_CIA AND
        DET.COD_LOCAL = U.COD_LOCAL AND
        DET.SEC_USU_LOCAL = U.SEC_USU_LOCAL  ;
    RETURN curDet;
  END;

/*****************************************************************************************************/

FUNCTION PVTA_F_GET_TOLERA_DOLARES(cCodGia_in IN CHAR)
RETURN VARCHAR2
IS
  ind VARCHAR2(10):='';
BEGIN
  SELECT nvl(a.llave_tab_gral,'N')||';'||nvl(a.desc_corta,'N') INTO ind
  FROM pbl_tab_gral a
  WHERE a.id_tab_gral='364';
  --AND a.cod_grupo_cia=cCodGia_in;
  RETURN ind;
EXCEPTION WHEN OTHERS THEN
  ind:='N';
  RETURN ind;
END;

/*****************************************************************************************************/

PROCEDURE PVTA_P_ENVIAR_MAIL_DIFERENCIA(cCodCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        cNumPed_in IN CHAR,
                                        vSoles_in IN VARCHAR2,
                                        vDolares_in IN VARCHAR2,
                                        vMonto_in IN VARCHAR2,
                                        vMonto2_in IN VARCHAR2,
                                        vDiferencia_in IN VARCHAR2)
AS
  v_vIP                 VARCHAR2(15);
  v_vDescLocal          VARCHAR2(200);
  mesg_body             VARCHAR2(32767);
  vAsunto               VARCHAR2(500);
  vTitulo               VARCHAR2(50);
  vMensaje              VARCHAR2(32767);
  v_vReceiverAddress    VARCHAR2(3000);
  v_vCCReceiverAddress  VARCHAR2(120) := NULL;
BEGIN
      DBMS_OUTPUT.put_line('ENVIA CORREO');
     SELECT DESC_LOCAL
     INTO v_vDescLocal
     FROM PBL_LOCAL
     WHERE COD_GRUPO_CIA = cCodCia_in
     AND COD_LOCAL = cCodLocal_in;

     SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') INTO v_vIP FROM DUAL;


     vMensaje := '<B>DIFERENCIA POR REDONDEO AL COBRAR PEDIDO NRO : ' || cNumPed_in || '</B>' ||
                 '<BR>LOCAL: ' || cCodLocal_in || '-' || v_vDescLocal ||
                 '<BR>' || ' Fecha: ' ||  TO_CHAR(SYSDATE, 'dd/MM/yyyy HH24:mi:ss')  || '<BR>' ||
                 '<BR>' || ' Monto soles: ' ||  vSoles_in  || '<BR>' ||
                 '<BR>' || ' Monto dolares: ' ||  vDolares_in  || '<BR>' ||
                 '<BR>' || ' Monto tarjeta: ' ||  vMonto_in  || '<BR>' ||
                 '<BR>' || ' Monto Calculado: ' ||  vMonto2_in  || '<BR>' ||
                 '<BR>' || ' Para ser cobrado se le adiciono: ' ||  vDiferencia_in  || '<BR>' ||
                 '<BR>' || ' En el IP  ' ||  v_vIP || '.<BR>';

            mesg_body := '<table><tr><td>' || vMensaje || '</td></tr></table>';
            vAsunto   := 'ALERTA DE DIFERENCIA POR REDONDEO DEL LOCAL ' || cCodLocal_in || '-' || v_vDescLocal;
            vTitulo   := 'ALERTA';

            SELECT LLAVE_TAB_GRAL
              INTO v_vReceiverAddress
              FROM PBL_TAB_GRAL
             WHERE ID_TAB_GRAL = 365;
             --AND COD_GRUPO_CIA=cCodCia_in;

dbms_output.put_line('texto: '||mesg_body);

            FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                     v_vReceiverAddress,
                                     vAsunto,
                                     vTitulo,
                                     mesg_body,
                                     v_vCCReceiverAddress,
                                     FARMA_EMAIL.GET_EMAIL_SERVER,
                                     true);

                                     dbms_output.put_line('holahola: '||v_vReceiverAddress);
END;

/*****************************************************************************************************/

/*************************************************************************************************************/

PROCEDURE PVTA_P_CHANGE_FORM_PAGO_PED(cCodGrupoCia_in 	 	     IN CHAR,
	                                   	   cCodLocal_in    	 	     IN CHAR,
										                     cCodFormaPago_in   	   IN CHAR,
									   	                   cNumPedVta_in   	 	     IN CHAR,
									   	                   nImPago_in		 		       IN NUMBER,
										                     cTipMoneda_in			     IN CHAR,
									  	                   nValTipCambio_in 	 	   IN NUMBER,
								   	  	                 nValVuelto_in  	 	     IN NUMBER,
								   	  	                 nImTotalPago_in 		     IN NUMBER,
									  	                   cNumTarj_in  		 	     IN CHAR,
									  	                   cFecVencTarj_in  		   IN CHAR,
									  	                   cNomTarj_in  	 		     IN CHAR,
                                         cCanCupon_in  	 		     IN NUMBER,
									  	                   cUsuCreaFormaPagoPed_in IN CHAR,
                                         cDNI_in                 IN CHAR,
                                         vCodvou_in              IN VARCHAR,
                                         cLote_in                IN VARCHAR) IS
  BEGIN

    ---INSERTO LA NUEVO FORMA DE PAGO DEL PEDIDO

    INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA, COD_LOCAL, COD_FORMA_PAGO, NUM_PED_VTA,
	   							  	                IM_PAGO, TIP_MONEDA, VAL_TIP_CAMBIO, VAL_VUELTO,
								  	                  IM_TOTAL_PAGO, NUM_TARJ, FEC_VENC_TARJ, NOM_TARJ,
								  	                  USU_CREA_FORMA_PAGO_PED, CANT_CUPON, Dni_Cli_Tarj, Cod_Autorizacion, Cod_Lote)
                              VALUES (cCodGrupoCia_in, cCodLocal_in, cCodFormaPago_in, cNumPedVta_in,
									                    nImPago_in, cTipMoneda_in, nValTipCambio_in, nValVuelto_in,
									                    nImTotalPago_in, cNumTarj_in, cFecVencTarj_in, cNomTarj_in,
									                    cUsuCreaFormaPagoPed_in, cCanCupon_in, cDNI_in, vCodvou_in, cLote_in);
  END;



  /*************************************************************************************************************/

PROCEDURE PVTA_P_SAVE_FORM_PAGO_PED(cCodGrupoCia_in 	 	     IN CHAR,
	                                   	   cCodLocal_in    	 	 IN CHAR,
									   	                   cNumPedVta_in   	 	 IN CHAR
									   	                ) IS
  v_Sec_Count NUMBER(5,2);
  /*v_Sec_Count2 NUMBER(5,2);*/
  v_Local char(3);
  cur FarmaCursor;
  BEGIN

   --obtengo el secuencial del pedido
    SELECT count(*) into v_Sec_Count
    FROM
    VTA_FORMA_PAGO_PEDIDO_DEL
    WHERE
    COD_GRUPO_CIA=cCodGrupoCia_in AND
    COD_LOCAL=cCodLocal_in AND
    NUM_PED_VTA=cNumPedVta_in;

    --bloqueo la tabl

     OPEN cur FOR
    SELECT
    *
    FROM
    VTA_FORMA_PAGO_PEDIDO FP
    WHERE
    FP.COD_GRUPO_CIA=cCodGrupoCia_in AND
    FP.COD_LOCAL=cCodLocal_in AND
    FP.NUM_PED_VTA=cNumPedVta_in
    for update;
    /*SELECT
    FROM
    VTA_FORMA_PAGO_PEDIDO pd
    WHERE
    COD_GRUPO_CIA=cCodGrupoCia_in AND
    COD_LOCAL=cCodLocal_in AND
    NUM_PED_VTA=cNumPedVta_in AND
    PD.COD_FORMA_PAGO IN ('001','002','005')
    for update;*/

    /*SELECT M.COD_LOCAL
    INTO   codLocal_in
    FROM   CE_MOV_CAJA M
    WHERE  M.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    M.COD_LOCAL     = cCodLocal_in
    AND    M.TIP_MOV_CAJA  = 'A'
    AND    M.SEC_MOV_CAJA  = cSecCaja_in for update;


    SELECT
    FROM
    VTA_FORMA_PAGO_PEDIDO_DEL d
    WHERE
    COD_GRUPO_CIA='001' AND
    COD_LOCAL='071' AND
    NUM_PED_VTA='0000097621'
    for update;*/
--dbms_utility.compile_schema('ptoventa',false);

    --IF v_Sec_Count = 0 THEN

    v_Sec_Count:=v_Sec_Count+1;

    --END IF;

    --GRABO LA FORMA DE PAGO ANTERIOR EN UN BACKUP

    INSERT INTO VTA_FORMA_PAGO_PEDIDO_DEL(COD_GRUPO_CIA,COD_LOCAL,COD_FORMA_PAGO,NUM_PED_VTA,SEC_FORMA_PAGO,IM_PAGO,TIP_MONEDA,VAL_TIP_CAMBIO,
    VAL_VUELTO,IM_TOTAL_PAGO,NUM_TARJ,FEC_VENC_TARJ,NOM_TARJ,FEC_CREA_FORMA_PAGO_PED,USU_CREA_FORMA_PAGO_PED,FEC_MOD_FORMA_PAGO_PED,
    USU_MOD_FORMA_PAGO_PED,CANT_CUPON,TIPO_AUTORIZACION,COD_LOTE,COD_AUTORIZACION,DNI_CLI_TARJ)
    SELECT COD_GRUPO_CIA,COD_LOCAL,COD_FORMA_PAGO,NUM_PED_VTA,v_Sec_Count,IM_PAGO,TIP_MONEDA,VAL_TIP_CAMBIO,
    VAL_VUELTO,IM_TOTAL_PAGO,NUM_TARJ,FEC_VENC_TARJ,NOM_TARJ,FEC_CREA_FORMA_PAGO_PED,USU_CREA_FORMA_PAGO_PED,FEC_MOD_FORMA_PAGO_PED,
    USU_MOD_FORMA_PAGO_PED,CANT_CUPON,TIPO_AUTORIZACION,COD_LOTE,COD_AUTORIZACION,DNI_CLI_TARJ
    FROM
    VTA_FORMA_PAGO_PEDIDO
    WHERE
    COD_GRUPO_CIA=cCodGrupoCia_in AND
    COD_LOCAL=cCodLocal_in AND
    NUM_PED_VTA=cNumPedVta_in;

  END;


  PROCEDURE PVTA_P_DEL_FORM_PAGO_PED(cCodGrupoCia_in 	 	     IN CHAR,
	                                   	   cCodLocal_in    	 	 IN CHAR,
									   	                   cNumPedVta_in   	 	 IN CHAR
									   	                ) IS
  BEGIN

    ---BORRO
    DELETE FROM VTA_FORMA_PAGO_PEDIDO WHERE
    COD_GRUPO_CIA=cCodGrupoCia_in AND
    COD_LOCAL=cCodLocal_in AND
    NUM_PED_VTA=cNumPedVta_in;

  END;

/*****************************************************************************************************/

END;
/

