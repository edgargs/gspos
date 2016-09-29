CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CONV" AS

  TYPE FarmaCursor IS REF CURSOR;
  C_INDICADOR_NO CHAR(1) := 'N';
  C_INDICADOR_SI CHAR(1) := 'S';
  C_ESTADO_ACTIVO CHAR(1) := 'A';
  C_TITULAR CHAR(1) := 'T';

  C_INICIO_MSG  VARCHAR2(500):= '<html>'||
                                  '<head>'||
                                  '<style type="text/css">'||
                                  '<!--'||
                                  '.style1 {font-size: 8px}'||
                                  '.style3 {'||
                                  '  font-size: 10px;'||
                                   ' font-weight: bold;}'||
                                  '.style4 {font-size: 8px; font-weight: bold; }'||
                                  '.style5 {font-size: 18px;font-weight: bold;}'||
                                  '-->'||
                                  '</style>'||
                                  '</head>'||
                                  '<body>'||
                                  '<table width="436" height="254" border="0" cellpadding="0" cellspacing="0">'||
                                    '<tr>'||
                                      '<td height="56" colspan="5" align="center" class="style5">CONVENIO</td>'||
                                    '</tr>'||
                                    '<tr>'||
                                      '<td valign="top">';


  C_FORMA_PAGO  VARCHAR2(500) := '<table width="100%" border="0" cellpadding="0" cellspacing="0">'||
                                  '<tr>'||
                                  '  <td width="50%" class="style4">FORMA </td>'||
                                 -- '  <td width="12%" class="style4">MONEDA </td>'||
                                  '  <td width="12%" class="style4">MONTO </td>'||
                                 -- '  <td width="8%" class="style4">LOTE </td>'||
                                --  '  <td width="18%" class="style4">AUTORIZACION </td>'||
                                  '</tr>';

  C_FIN_FORMA_PAGO  VARCHAR2(100) :='<tr>'||
                                  '<td height="2" colspan="5"></td>'||
                                  '</tr></table>';


  C_FILA_VACIA  VARCHAR2(100) :='<tr> '||
                                  '<td height="2" colspan="3"></td> '||
                                  ' </tr> ';

  C_FIN_MSG     VARCHAR2(500) := '</td>'||
                                  '</tr>'||
                                  '</table>'||
                                  '</body>'||
                                  '</html>';
  --Ptoventa_Conv
  --Descripcion: Lista el maestro de Convenios
  --Fecha       Usuario		Comentario
  --16/03/2006  Paulo     Creación
  --Modificado 22/08/2007 DUBILLUZ
  --Modificado 31/08/2007 DUBILLUZ
 FUNCTION CONV_LISTA_CONVENIOS (cCodGrupoCia_in CHAR,
                                cCodLocal_in CHAR,
                                 cSecUsuLocal_in CHAR
                                )
  RETURN FarmaCursor;

  --Descripcion: Lista los campos a ingresar en cada convenio
  --Fecha       Usuario		Comentario
  --16/03/2006  Paulo     Creación
  --14/03/2008  JCORTEZ  listando campos dependientes
 FUNCTION CONV_LISTA_CONVENIO_CAMPOS(cCodConvenio_in IN CHAR)
  RETURN FarmaCursor ;

  --Descripcion: Lista los Clientes por convenio
  --Fecha       Usuario		Comentario
  --16/03/2006  Paulo     Creación
  --05/02/2008  DUBILLUZ  MODIFICACION
 FUNCTION CONV_LISTA_CLI_CONVENIO(cCodConvenio_in IN CHAR)
  RETURN FarmaCursor ;

  --Descripcion: lISTA LOS CLIENTES DEPENDIENTES DEL CLIENTE POR CONVENIO
  --Fecha       Usuario		Comentario
  --04/02/2008  DUBILLUZ   CREACION
 FUNCTION CONV_LISTA_CLI_DEP_CONVENIO(cCodConvenio_in IN CHAR,
                                  cCodCli_in      IN CHAR)
  RETURN FarmaCursor ;

/**************************************************************/

  --Descripcion: Obtiene el indicador de control de precio del producto
  --Fecha       Usuario		Comentario
  --16/03/2007  LREQUE    	Creación
  FUNCTION CON_OBTIENE_IND_PRECIO(cCodGrupoCia_in CHAR,
                                  cCodProd_in     CHAR) RETURN CHAR;

  --Descripcion: Obtiene el nuevo precio efectuando el descuento del convenio
  --Fecha       Usuario		Comentario
  --16/03/2007  LREQUE    	Creación
  --08/05/2007  LREQUE      Modificación pendiente
  FUNCTION CON_OBTIENE_NVO_PRECIO(cCodGrupoCia_in CHAR,
                                  cCodLocal_in    CHAR,
                                  cCodConv_in     CHAR,
                                  cCodProd_in     CHAR,
                                  nValPrecVta_in  NUMBER) RETURN CHAR;

  --Descripcion: Valida si, el cliente, ha superado o no el limite de crédito asignado
  --Fecha       Usuario		Comentario
  --16/03/2007  LREQUE    	Creación
  FUNCTION CON_OBTIENE_DIF_CREDITO(cCodConv_in     CHAR,
                                   cCodCliente_in  CHAR,
                                   nMonto_in      NUMBER) RETURN CHAR;

  --Descripcion: Agrega
  --Fecha       Usuario		Comentario
  --16/03/2007  LREQUE    	Creación
  --05/02/2008  DUBILLUZ  	Modificacion
  PROCEDURE CON_AGREGA_PEDIDO_CONVENIO(cCodGrupoCia_in   CHAR,
                                         cCodLocal_in      CHAR,
                                         cNumPedVta_in     CHAR,
                                         cCodConvenio_in   CHAR,
                                         cCodCli_in        CHAR,
                                         cUsuCreaPed_in    CHAR,
                                         cNumDocIden_in    CHAR,
                                         cCodTrab_in       CHAR,
                                         cApePatTit_in     CHAR,
                                         cApeMatTit_in     CHAR,
                                         cFecNacTit_in     CHAR,
                                         cCodSol_in        CHAR,
                                         nValPorcDcto_in   NUMBER,
                                         nValPorcCoPago_in NUMBER,
                                         cNumTelefCli_in   CHAR,
                                         cDirecCli_in      CHAR,
                                         cNomDistrito_in   CHAR,
                                         cValCopago_in     NUMBER,
                                         cCodInterno_in    CHAR,
                                         cnomCompleto_in   CHAR ,
                                         cCodCliDep        CHAR,
                                         cCodTrabDep       CHAR);

  --Descripcion: Actualiza el consumo del cliente
  --Fecha       Usuario		Comentario
  --16/03/2007  LREQUE    	Creación
  /**
  JCALLO 09/01/2008
  este procedimiento falta eliminar, no se hice ya que este procedimiento esta
  siendo invocado desde otro procedimiento del paquete ptoventa_caj_anul
  **/
  PROCEDURE CON_ACTUALIZA_CONSUMO_CLI(cCodConvenio_in   CHAR,
                                      cCodCli_in        CHAR,
                                      nMonto_in         NUMBER);

  --Descripcion: Obtiene el valor del copago según un convenio
  --Fecha       Usuario		Comentario
  --20/03/2007  LREQUE    	Creación
  FUNCTION  CON_OBTIENE_COPAGO(cCodConvenio   CHAR,
                               cCodCliente    CHAR,
                               nMonto         NUMBER) RETURN CHAR;

  --Descripcion: Obtiene la forma de pago del convenio
  --Fecha       Usuario		Comentario
  --20/03/2007  LREQUE    	Creación
  FUNCTION CON_OBTIENE_FORMA_PAGO_CONV(cCodGrupoCia   CHAR,
                                       cCodConvenio   CHAR) RETURN CHAR;

  --Descripcion: Graba la forma de pago en una tabla temporal
  --Fecha       Usuario		Comentario
  --20/03/2007  LREQUE    	Creación
  PROCEDURE CON_GRABAR_FORMA_PAGO_PED_CONV(cCodGrupoCia_in 	   IN CHAR,
  	                                       cCodLocal_in    	   IN CHAR,
  					                               cCodFormaPago_in    IN CHAR,
  					                               cNumPedVta_in   	   IN CHAR,
  					                               nImPago_in		       IN NUMBER,
  					                               cTipMoneda_in	     IN CHAR,
  				                                 nValTipCambio_in 	 IN NUMBER,
  					                               nValVuelto_in  	   IN NUMBER,
  					                               nImTotalPago_in 	   IN NUMBER,
  					                               cNumTarj_in  	     IN CHAR,
  					                               cFecVencTarj_in  	 IN CHAR,
  					                               cNomTarj_in  	     IN CHAR,
                                           cCanCupon_in  	     IN NUMBER,
  					                               cUsuCreaFormaPagoPed_in IN CHAR);

  --Descripcion: Graba la forma de pago en una tabla temporal
  --Fecha       Usuario		Comentario
  --21/03/2007  LREQUE    	Creación
FUNCTION CON_OBTIENE_INFO_CONV_PED(cCodGrupoCia_in 	IN CHAR,
  	                               cCodLocal_in    	IN CHAR,
                                   cNumPedVta_in    IN CHAR,
                                   nMontoPedido_in  IN CHAR) RETURN FarmaCursor;

  --Descripcion: Actualiza el pedido
  --Fecha       Usuario		Comentario
  --28/03/2007  LREQUE    	Creación
 PROCEDURE CON_ACTUALIZA_NUM_PED(cCodGrupoCia_in 	 	    IN CHAR,
  	                              cCodLocal_in    	 	    IN CHAR,
                                  cNumPedVta_in           IN CHAR,
                                  cNumPedVtaDel_in        IN CHAR);

 FUNCTION TMP_CON_OBTIENE_INFO_CONV_PED(cCodGrupoCia_in 	 	     IN CHAR,
  	                                     cCodLocal_in    	 	     IN CHAR,
                                         cNumPedVta_in           IN CHAR,
                                         nMontoPedido_in         IN NUMBER) RETURN FarmaCursor;

  --Descripcion: Inserta, a la lista de PRODUCTOS EXCLUIDOS, los productos con PRECIO CONTROLADO
  --Fecha       Usuario		Comentario
  --07/05/2007  LREQUE    	Creación
  PROCEDURE CON_LLENA_LISTA_EXCLUIDOS(cCodGrupoCia_in 	 	    IN CHAR);

  FUNCTION CON_OBTIENE_CREDITO(cCodConv_in     CHAR,
                              cCodCliente_in  CHAR) RETURN CHAR;

  FUNCTION CON_OBTIENE_CREDITO_UTIL(cCodConv_in     CHAR,
                                    cCodCliente_in  CHAR) RETURN CHAR;



-------Ptoventa_Conv
  --Descripcion: Graba la forma de pago en una tabla temporal LOCAL
  --Fecha       Usuario		Comentario
  --20/09/2007   DUBILLUZ   CREACION
  PROCEDURE CON_GRABAR_FP_PED_CONV_LOCAL(cCodGrupoCia_in 	 	     IN CHAR,
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
  									  	                   cUsuCreaFormaPagoPed_in IN CHAR);

  --Descripcion: Actualiza el credito disponible del cliente.
  --Fecha       Usuario		Comentario
  --06/03/2008  ERIOS     Creacion
  PROCEDURE CONV_ACTUALIZA_CRED_DISP(cCodConvenio_in   IN CHAR,
                               cCodCliente_in          IN CHAR,
                               cCodGrupoCia_in 	 	  IN CHAR,
  	                           cCodLocal_in    	 	  IN CHAR,
  									   	       cNumPedVta_in   	 	  IN CHAR,
                               nMonto_in            IN NUMBER);

  --Descripcion: Retorna el nombre del cliente.
  --Fecha       Usuario		Comentario
  --13/06/2008  ERIOS     Creacion
  FUNCTION GET_NOMBRE_CLIENTE_NUMDOC(cCodConvenio_in IN CHAR,cNumDoc_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Retorna el valor porcentaje convenio.
  --Fecha       Usuario           Comentario
  --15/12/2008  asolis     Creacion
  FUNCTION CON_PORC_COPAGO_CONV(cCodConvenio_in   IN CHAR)
  RETURN CHAR ;


  --Descripcion: Se obtiene formato de voucher convenio
  --Fecha       Usuario   Comentario
  --15/12/20  JCORTEZ     Creacion
  FUNCTION IMP_DATOS_CONVENIO(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cCodConvenio_in   IN CHAR,
                                cCodCli_in        IN CHAR,
                                cIpServ_in        IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Se obtiene cabecera de voucher convenio
  --Fecha       Usuario   Comentario
  --15/12/20  JCORTEZ     Creacion
  FUNCTION VTA_OBTENER_DATA4(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in	   IN CHAR,
                               cNumPedVta_in   IN CHAR,
                               cCodConvenio_in IN CHAR,
                               cCodCli_in      IN CHAR)
  RETURN FarmaCursor;

 FUNCTION VTA_OBTENER_DATA1(cCodGrupoCia_in IN CHAR,
  		   				             cCodLocal_in	   IN CHAR,
							               cNumPedVta_in   IN CHAR,
                             cCodConvenio_in IN CHAR,
                             cCodCli_in      IN CHAR)
  RETURN FarmaCursor;

  FUNCTION VTA_OBTENER_DATA2(cCodGrupoCia_in  IN CHAR,
   				                   cCodLocal_in	    IN CHAR,
  	                         cNumPedVta_in    IN CHAR,
                             cCodConvenio_in  IN CHAR,
                             cCodCli_in       IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene el porcentaje de la forma de pago segun su codigo
  --Fecha       Usuario         Comentario
  --26.03.2013  Luigy Terrazos  Creacion
  FUNCTION CONSUL_PORC_FORM_PAG(cCodConvenio_in   IN CHAR)
  RETURN CHAR ;
END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CONV" AS

--Modificado 22/08/2007 DUBILLUZ
--Modificado 31/08/2007 DUBILLUZ
 FUNCTION CONV_LISTA_CONVENIOS (cCodGrupoCia_in CHAR,
                                cCodLocal_in CHAR,
                                cSecUsuLocal_in CHAR
                                )
  RETURN FarmaCursor
  IS
    curConv FarmaCursor;
  BEGIN
    OPEN curConv FOR
        /* SELECT mc.cod_convenio|| 'Ã' ||
                mc.desc_larga_conv|| 'Ã' ||
                mc.porc_dcto_conv|| 'Ã' ||
                mc.porc_copago_conv
         FROM con_mae_convenio mc,
              con_local_x_conv lc
         WHERE lc.cod_grupo_cia = cCodGrupoCia_in
         AND   lc.cod_local = cCodLocal_in
         AND   lc.Est_Local_x_Conv = 'A'
         AND   mc.Est_Convenio = 'A'
         AND   LC.COD_CONVENIO = MC.COD_CONVENIO ;*/
        SELECT mc.cod_convenio  || 'Ã' ||
               mc.desc_larga_conv || 'Ã' ||
               mc.porc_dcto_conv  || 'Ã' ||
               mc.porc_copago_conv ||  'Ã' ||
               mc.ind_dependencia_cli ||  'Ã' ||-- se añadio el indicador de Dependencia de Clienteses
               mc.ind_vta_solo_cred
        FROM   con_local_x_conv lc,
               con_mae_convenio mc,
               CON_USU_X_CONV  uc
        WHERE  lc.cod_grupo_cia = cCodGrupoCia_in
        AND    lc.cod_local = cCodLocal_in
        AND    lc.Est_Local_x_Conv = 'A'
        AND    mc.Est_Convenio = 'A'
        and    uc.sec_usu_local = cSecUsuLocal_in
        AND    lc.cod_grupo_cia = UC.COD_GRUPO_CIA
        AND    lc.cod_local     = UC.COD_LOCAL
        AND    LC.COD_CONVENIO = MC.COD_CONVENIO
        and    mc.cod_convenio  = uc.cod_convenio
        UNION
        SELECT mc.cod_convenio  || 'Ã' ||
               mc.desc_larga_conv || 'Ã' ||
               mc.porc_dcto_conv  || 'Ã' ||
               mc.porc_copago_conv ||  'Ã' ||
               mc.ind_dependencia_cli ||  'Ã' ||-- se añadio el indicador de Dependencia de Clienteses
               mc.ind_vta_solo_cred
        FROM   con_local_x_conv lc,
               con_mae_convenio mc
        WHERE  lc.cod_grupo_cia = cCodGrupoCia_in
        AND    lc.cod_local = cCodLocal_in
        AND    lc.Est_Local_x_Conv = 'A'
        AND    mc.Est_Convenio = 'A'
        AND    lc.cod_convenio = mc.cod_convenio
        AND    NOT EXISTS (SELECT CONV.COD_CONVENIO
                           FROM   CON_USU_X_CONV uc,
                                  con_mae_convenio CONV
                           WHERE  UC.COD_GRUPO_CIA = cCodGrupoCia_in
                           AND    UC.COD_LOCAL = cCodLocal_in
                           AND    UC.COD_CONVENIO = CONV.COD_CONVENIO
                           AND    UC.COD_CONVENIO = MC.COD_CONVENIO);
    RETURN curConv;
  END;


  FUNCTION CONV_LISTA_CONVENIO_CAMPOS(cCodConvenio_in IN CHAR)
  RETURN FarmaCursor
  IS
    X         NUMBER;
    curConv FarmaCursor;
  BEGIN
  --JCORTEZ 14/03/2008
    SELECT  COUNT(1) INTO X
    FROM CON_CAMPOS_CONVENIO Y
    WHERE Y.SEC_CAMPO IS NOT NULL
    AND Y.COD_CONVENIO=cCodConvenio_in;

     IF(X>0) THEN
	  	   OPEN curConv FOR
         SELECT NOM_CAMPO|| 'Ã' ||
                 ' '     || 'Ã' ||
                 CF.COD_CAMPO|| 'Ã' ||
                 IND_TIP_DATO|| 'Ã' ||
                 IND_SOLO_LECTURA || 'Ã' ||
                 IND_OBLIGATORIO || 'Ã' ||
                 NVL(CO.IND_BUSQUEDA,' ')
          FROM   CON_CAMPOS_FORMULARIO CF,
                 CON_CAMPOS_CONVENIO CO
          WHERE  CO.COD_CONVENIO = cCodConvenio_in
          AND    CF.COD_CAMPO = CO.COD_CAMPO
          ORDER BY CO.SEC_CAMPO ASC;
     ELSE
     OPEN curConv FOR
          SELECT NOM_CAMPO|| 'Ã' ||
                 ' ' || 'Ã' ||
                 CF.COD_CAMPO|| 'Ã' ||
                 IND_TIP_DATO|| 'Ã' ||
                 IND_SOLO_LECTURA || 'Ã' ||
                 IND_OBLIGATORIO || 'Ã' ||
                 NVL(CO.IND_BUSQUEDA,' ')
          FROM   CON_CAMPOS_FORMULARIO CF,
                 CON_CAMPOS_CONVENIO CO
          WHERE  CO.COD_CONVENIO = cCodConvenio_in
          AND    CF.COD_CAMPO = CO.COD_CAMPO
          ORDER BY cf.cod_campo DESC ;
     END IF;
    RETURN curConv;
  END;


 FUNCTION CONV_LISTA_CLI_CONVENIO(cCodConvenio_in IN CHAR)
  RETURN FarmaCursor
  IS
    curConv FarmaCursor;
  BEGIN
    OPEN curConv FOR
         SELECT MCL.cod_cli || 'Ã' ||
                CC.COD_TRAB_CONV || 'Ã' ||
                DECODE(TRIM(MCL.NOM_COMPLETO),'',MCL.NOM_CLI || ' ' || MCL.APE_PAT_CLI || ' ' || MCL.APE_MAT_CLI,MCL.NOM_COMPLETO)|| 'Ã' ||
                MCL.NUM_DOC_CLI  || 'Ã' ||
                to_number(CC.COD_TRAB_CONV )
         FROM   con_mae_cliente mcL,
                CON_MAE_CONVENIO MC,
                CON_CLI_CONV CC
         WHERE  MCL.COD_CLI = CC.COD_CLI
         AND    MC.COD_CONVENIO = CC.COD_CONVENIO
         AND    MC.COD_CONVENIO = cCodConvenio_in
         AND    CC.EST_CONV_CLI = C_ESTADO_ACTIVO
         AND    CC.TIPO_CLI = C_TITULAR;
    RETURN curConv;
  END;

/***********************************************************************************/

  FUNCTION CON_OBTIENE_IND_PRECIO(cCodGrupoCia_in CHAR,
                                  cCodProd_in     CHAR) RETURN CHAR
  IS
  V_IND CHAR(1);
  BEGIN
       SELECT IND_PRECIO_CONTROL INTO V_IND
       FROM   LGT_PROD
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_PROD      = cCodProd_in;

       RETURN V_IND;
  END CON_OBTIENE_IND_PRECIO;

------------------------------------------------------------------------------------------
  FUNCTION CON_OBTIENE_NVO_PRECIO(cCodGrupoCia_in CHAR,
                                  cCodLocal_in    CHAR,
                                  cCodConv_in     CHAR,
                                  cCodProd_in     CHAR,
                                  nValPrecVta_in  NUMBER) RETURN CHAR
  IS
  V_NVO_PRECIO       NUMBER(8,3);
  V_PORC_DCTO_CONV   NUMBER(5,2);
    V_PORC_DCTO_CONV_LAB     NUMBER(5,2);
    V_PORC_DCTO_CONV_PROD   NUMBER(5,2);
  v_count_prod NUMBER ;
  v_count_lab NUMBER ;

  vConvExistLocal    NUMBER(1);
  --vIndTipoLista      CHAR(1);
  vContLstExcluye    NUMBER(2);

  v_prec_vta       NUMBER(8,3);

  v_indComp   CHAR(1);
  valComp    NUMBER(1):=1;

-- 2010-06-23 JOLIVA: Variable que indica si el convenio aplica a todo el maestro de productos
  v_indMaeProd  CON_MAE_CONVENIO.IND_MAE_PROD%TYPE;

  BEGIN

      /*-- busca si existe el producto
      SELECT COUNT(*) INTO v_count_prod
      FROM   con_lista_convenio lc,
             con_lista cl,
             con_prod_lista pl
      WHERE  lc.cod_lista = cl.cod_lista
      AND    cl.cod_lista = pl.cod_lista
      AND    lc.cod_lista IN (SELECT lc.cod_lista
                              FROM   con_lista_convenio lc
                              WHERE  lc.cod_convenio = cCodConv_in
                              AND    lc.est_lista_convenio = C_ESTADO_ACTIVO)
      AND    PL.COD_PROD = cCodProd_in
      AND    pl.est_prod_lista = C_ESTADO_ACTIVO
      AND    lc.est_lista_convenio = C_ESTADO_ACTIVO
      AND    cl.est_lista = C_ESTADO_ACTIVO;

      dbms_output.put_line('v_count_prod :' || v_count_prod);

      -- busca si existe en laboratorios
      SELECT COUNT(*) INTO v_count_lab
      FROM   con_lista_convenio lc,
             con_lista cl,
             Con_Lab_Lista cll
      WHERE  lc.cod_lista = cl.cod_lista
      AND    cl.cod_lista = cll.cod_lista
      AND    lc.cod_lista IN (SELECT lc.cod_lista
                              FROM   con_lista_convenio lc
                              WHERE  lc.cod_convenio = cCodConv_in
                              AND    lc.est_lista_convenio = C_ESTADO_ACTIVO)
      AND    cll.cod_lab IN (SELECT lab.cod_lab
                             FROM   lgt_prod lp,
                                    lgt_lab lab,
                                    lgt_prod_local plocal
                             WHERE  plocal.est_prod_loc = C_ESTADO_ACTIVO
                             AND    lp.est_prod = C_ESTADO_ACTIVO
                             AND    plocal.cod_prod = cCodProd_in--'118074' --'13003'
                             AND    lp.cod_lab = lab.cod_lab
                             AND    lp.cod_grupo_cia = plocal.cod_grupo_cia
                             AND    lp.cod_prod = plocal.cod_prod
                             AND    plocal.cod_local = cCodLocal_in)
      AND    lc.est_lista_convenio = C_ESTADO_ACTIVO
      AND    cl.est_lista = C_ESTADO_ACTIVO
      AND    CLL.EST_LAB_LISTA = C_ESTADO_ACTIVO;

      dbms_output.put_line('v_count_lab :' || v_count_lab);

      IF(v_count_prod >= 1 OR v_count_lab >= 1) THEN
        SELECT LISTA_CONV.VAL_DCTO_LIST_CONV/100 INTO V_PORC_DCTO_CONV
        FROM   CON_LISTA_CONVENIO LISTA_CONV
        WHERE  LISTA_CONV.COD_CONVENIO    = cCodConv_in
        AND    LISTA_CONV.SEC_LISTA_CONV IN (SELECT MIN(LISTA_CONV.SEC_LISTA_CONV)
                                             FROM   CON_LISTA_CONVENIO LISTA_CONV
                                             WHERE  LISTA_CONV.COD_CONVENIO    = cCodConv_in);

        SELECT nValPrecVta_in-(nValPrecVta_in*V_PORC_DCTO_CONV) INTO V_NVO_PRECIO
        FROM   LGT_PROD_LOCAL     PROD_LOCAL
        WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    PROD_LOCAL.COD_LOCAL     = cCodLocal_in
        AND    PROD_LOCAL.COD_PROD      = cCodProd_in;

      ELSIF (v_count_prod = 0 AND v_count_lab = 0) THEN
        SELECT PORC_DCTO_CONV/100 INTO V_PORC_DCTO_CONV
        FROM   CON_MAE_CONVENIO CONV
        WHERE  CONV.COD_CONVENIO    = cCodConv_in;

        SELECT nValPrecVta_in-(nValPrecVta_in*V_PORC_DCTO_CONV) INTO V_NVO_PRECIO
        FROM   LGT_PROD_LOCAL     PROD_LOCAL
        WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    PROD_LOCAL.COD_LOCAL     = cCodLocal_in
        AND    PROD_LOCAL.COD_PROD      = cCodProd_in;
      END IF;

      RETURN TRIM(TO_CHAR(V_NVO_PRECIO,'999,990.990'));*/

      --JCORTEZ 10/10/2008
-- 2010-06-23 JOLIVA: Se obtiene el indicador si el convenio aplica a todo el maestro de productos
      SELECT C.IND_COMPETENCIA, C.IND_MAE_PROD
      INTO v_indComp, v_indMaeProd
      FROM CON_MAE_CONVENIO C
      WHERE C.COD_CONVENIO=cCodConv_in;

      IF(v_indComp='S')THEN
          valComp:=-1;
      ELSE
          valComp:=1;
      END IF;

      SELECT  COUNT(*) INTO vConvExistLocal
      FROM    CON_LOCAL_X_CONV CL
      WHERE   CL.COD_GRUPO_CIA    = cCodGrupoCia_in
      AND     CL.COD_LOCAL        = cCodLocal_in
      AND     CL.COD_CONVENIO     = cCodConv_in
      AND     CL.EST_LOCAL_X_CONV = C_ESTADO_ACTIVO;

      IF vConvExistLocal > 0 THEN--El convenio está relacionado al local
         DBMS_OUTPUT.put_line('Existe en local: ');

         SELECT    COUNT(*) INTO vContLstExcluye
         FROM      CON_PROD_LISTA     CPL,
                   CON_LISTA          CL,
                   CON_LISTA_CONVENIO CLC
         WHERE     CPL.COD_GRUPO_CIA   = cCodGrupoCia_in
         AND       CPL.COD_PROD        = cCodProd_in
         AND       CL.Ind_Tipo_Lista   = 'E'
         AND       CPL.EST_PROD_LISTA  = C_ESTADO_ACTIVO
         AND       CL.EST_LISTA        = C_ESTADO_ACTIVO
         AND       CL.COD_LISTA = CPL.COD_LISTA
         AND       CLC.COD_CONVENIO = cCodConv_in
         AND       CLC.EST_LISTA_CONVENIO = C_ESTADO_ACTIVO
         AND       CLC.COD_LISTA = CL.COD_LISTA;

         DBMS_OUTPUT.put_line('vContLstExcluye: '||vContLstExcluye);
         --Si el producto no se encuentra en ninguna lista de exclusión
         IF vContLstExcluye = 0 THEN
            DBMS_OUTPUT.PUT_LINE('No Excluye producto');

            --si existe producto en lista
            SELECT COUNT(*) INTO v_count_prod
            FROM   CON_LISTA_CONVENIO LC,
                   CON_LISTA CL,
                   CON_PROD_LISTA PL
            WHERE  LC.COD_LISTA = CL.COD_LISTA
            AND    CL.COD_LISTA = PL.COD_LISTA
            AND    LC.COD_LISTA IN (SELECT LC.COD_LISTA
                                    FROM   CON_LISTA_CONVENIO LC
                                    WHERE  LC.COD_CONVENIO       = cCodConv_in
                                    AND    LC.EST_LISTA_CONVENIO = C_ESTADO_ACTIVO)
            AND    PL.COD_PROD           = cCodProd_in
            AND    PL.EST_PROD_LISTA     = C_ESTADO_ACTIVO
            AND    LC.EST_LISTA_CONVENIO = C_ESTADO_ACTIVO
            AND    CL.EST_LISTA          = C_ESTADO_ACTIVO;

            DBMS_OUTPUT.PUT_LINE('v_count_prod :' || v_count_prod);

            -- busca si existe en laboratorios
            SELECT COUNT(*) INTO v_count_lab
            FROM   CON_LISTA_CONVENIO LC,
                   CON_LISTA CL,
                   CON_LAB_LISTA CLL
            WHERE  LC.COD_LISTA = CL.COD_LISTA
            AND    CL.COD_LISTA = CLL.COD_LISTA
            AND    LC.COD_LISTA IN (SELECT LC.COD_LISTA
                                    FROM   CON_LISTA_CONVENIO LC
                                    WHERE  LC.COD_CONVENIO       = cCodConv_in
                                    AND    LC.EST_LISTA_CONVENIO = C_ESTADO_ACTIVO)
            AND    CLL.COD_LAB IN (SELECT LAB.COD_LAB
                                   FROM   LGT_PROD LP,
                                          LGT_LAB LAB,
                                          LGT_PROD_LOCAL PLOCAL
                                   WHERE  PLOCAL.EST_PROD_LOC  = C_ESTADO_ACTIVO
                                   AND    LP.EST_PROD          = C_ESTADO_ACTIVO
                                   AND    PLOCAL.COD_PROD      = cCodProd_in--'118074' --'13003'
                                   AND    LP.COD_LAB           = LAB.COD_LAB
                                   AND    LP.COD_GRUPO_CIA     = PLOCAL.COD_GRUPO_CIA
                                   AND    LP.COD_PROD          = PLOCAL.COD_PROD
                                   AND    PLOCAL.cod_local     = cCodLocal_in)
            AND    LC.EST_LISTA_CONVENIO = C_ESTADO_ACTIVO
            AND    CL.EST_LISTA          = C_ESTADO_ACTIVO
            AND    CLL.EST_LAB_LISTA     = C_ESTADO_ACTIVO;

            DBMS_OUTPUT.PUT_LINE('v_count_lab :' || v_count_lab);

            IF(v_count_prod >= 1 OR v_count_lab >= 1) THEN
              /*SELECT LISTA_CONV.VAL_DCTO_LIST_CONV/100 INTO V_PORC_DCTO_CONV
              FROM   CON_LISTA_CONVENIO LISTA_CONV
              WHERE  LISTA_CONV.COD_CONVENIO    = cCodConv_in
              AND    LISTA_CONV.SEC_LISTA_CONV IN (SELECT MIN(LISTA_CONV.SEC_LISTA_CONV)
                                                   FROM   CON_LISTA_CONVENIO LISTA_CONV
                                                   WHERE  LISTA_CONV.COD_CONVENIO    = cCodConv_in);*/
              --

              --si es valido el producto de la lista
              SELECT PL.PREC_VTA INTO v_prec_vta
              FROM   CON_LISTA_CONVENIO LC,
                     CON_LISTA CL,
                     CON_PROD_LISTA PL
              WHERE  LC.COD_LISTA = CL.COD_LISTA
              AND    CL.COD_LISTA = PL.COD_LISTA
              AND    LC.COD_LISTA IN (SELECT LC.COD_LISTA
                                      FROM   CON_LISTA_CONVENIO LC
                                      WHERE  LC.COD_CONVENIO       = cCodConv_in
                                      AND    LC.EST_LISTA_CONVENIO = C_ESTADO_ACTIVO)
              AND    PL.COD_PROD           = cCodProd_in
              AND    PL.EST_PROD_LISTA     = C_ESTADO_ACTIVO
              AND    LC.EST_LISTA_CONVENIO = C_ESTADO_ACTIVO
              AND    CL.EST_LISTA          = C_ESTADO_ACTIVO
              --AND    PL.PREC_VTA IS NOT NULL
              AND    ROWNUM=1;--si existe dos precio para el mismo productos


              IF (v_prec_vta IS NOT NULL)THEN

                DBMS_OUTPUT.PUT_LINE('v_prec_vta :' || v_prec_vta);
                SELECT (v_prec_vta/PROD_LOCAL.Val_Frac_Local)*valComp INTO V_NVO_PRECIO
                FROM   LGT_PROD_LOCAL     PROD_LOCAL
                WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
                AND    PROD_LOCAL.COD_LOCAL     = cCodLocal_in
                AND    PROD_LOCAL.COD_PROD      = cCodProd_in;


              ELSE

              DBMS_OUTPUT.PUT_LINE('v_prec_vta :' || v_prec_vta);
                V_PORC_DCTO_CONV := 0;

                BEGIN
                SELECT LISTA_CONV.VAL_DCTO_LIST_CONV/100 INTO V_PORC_DCTO_CONV_LAB
                FROM   CON_LISTA_CONVENIO LISTA_CONV
                WHERE  LISTA_CONV.COD_CONVENIO    = cCodConv_in
                AND    LISTA_CONV.SEC_LISTA_CONV IN (SELECT MIN(CL1.SEC_LISTA_CONV)
                                                     FROM LGT_PROD P1,
                                                         CON_LAB_LISTA C1,
                                                         CON_LISTA_CONVENIO CL1
                                                     WHERE P1.COD_GRUPO_CIA = cCodGrupoCia_in
                                                          AND P1.COD_PROD = cCodProd_in
                                                          AND P1.EST_PROD = C_ESTADO_ACTIVO
                                                          AND C1.EST_LAB_LISTA = C_ESTADO_ACTIVO
                                                          AND CL1.COD_CONVENIO = cCodConv_in
                                                          AND CL1.EST_LISTA_CONVENIO = C_ESTADO_ACTIVO
                                                          AND P1.COD_LAB = C1.COD_LAB
                                                          AND C1.COD_LISTA = CL1.COD_LISTA
                                                          AND CL1.IND_DCTO_PORC='S'
                                                          AND ROWNUM=1);
                EXCEPTION
                WHEN  NO_DATA_FOUND THEN
                  V_PORC_DCTO_CONV_LAB:=0;
                END;

              BEGIN
              SELECT LISTA_CONV.VAL_DCTO_LIST_CONV/100 INTO V_PORC_DCTO_CONV_PROD
              FROM   CON_LISTA_CONVENIO LISTA_CONV
              WHERE  LISTA_CONV.COD_CONVENIO    = cCodConv_in
              AND    LISTA_CONV.SEC_LISTA_CONV IN ( SELECT MIN(LC.SEC_LISTA_CONV)
                                                      FROM   CON_LISTA_CONVENIO LC,
                                                             CON_LISTA CL,
                                                             CON_PROD_LISTA PL
                                                      WHERE  LC.COD_LISTA = CL.COD_LISTA
                                                      AND    CL.COD_LISTA = PL.COD_LISTA
                                                      AND    LC.COD_LISTA IN (SELECT LC.COD_LISTA
                                                                              FROM   CON_LISTA_CONVENIO LC
                                                                              WHERE  LC.COD_CONVENIO       = cCodConv_in
                                                                              AND    LC.EST_LISTA_CONVENIO = C_ESTADO_ACTIVO)
                                                      AND    PL.COD_PROD           = cCodProd_in
                                                      AND    PL.EST_PROD_LISTA     = C_ESTADO_ACTIVO
                                                      AND    LC.EST_LISTA_CONVENIO = C_ESTADO_ACTIVO
                                                      AND    CL.EST_LISTA          = C_ESTADO_ACTIVO
                                                      AND    LC.IND_DCTO_PORC='S'
                                                      --AND    PL.PREC_VTA IS NOT NULL
                                                      AND    ROWNUM=1);
              EXCEPTION
              WHEN  NO_DATA_FOUND THEN
                  V_PORC_DCTO_CONV_PROD:=0;
              END;

                 IF (V_PORC_DCTO_CONV_PROD>0)THEN
                  V_PORC_DCTO_CONV:= V_PORC_DCTO_CONV_PROD;
                 ELSIF(V_PORC_DCTO_CONV_LAB>0 AND V_PORC_DCTO_CONV_PROD=0)THEN
                  V_PORC_DCTO_CONV:= V_PORC_DCTO_CONV_LAB;
                 END IF;


                DBMS_OUTPUT.PUT_LINE('Dscto. a aplicar=' || V_PORC_DCTO_CONV);

                SELECT nValPrecVta_in-((nValPrecVta_in*V_PORC_DCTO_CONV)*valComp) INTO V_NVO_PRECIO
                FROM   LGT_PROD_LOCAL     PROD_LOCAL
                WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
                AND    PROD_LOCAL.COD_LOCAL     = cCodLocal_in
                AND    PROD_LOCAL.COD_PROD      = cCodProd_in;

                DBMS_OUTPUT.PUT_LINE('Dscto. por lista');


              END IF;

            ELSIF (v_count_prod = 0 AND v_count_lab = 0) THEN
/*
2010-06-23 JOLIVA:
 SI NO FORMA PARTE DE NINGUNA LISTA
    SE VERIFICA SI APLICA A TODO EL MAESTRO
       SI APLICA A TODO EL MAESTRO, SE CALCULA EL DESCUENTO
       CASO CONTRARIO, NO APLICA DESCUENTO
*/
                  IF v_indMaeProd = 'S' THEN
                      SELECT PORC_DCTO_CONV/100 INTO V_PORC_DCTO_CONV
                      FROM   CON_MAE_CONVENIO CONV
                      WHERE  CONV.COD_CONVENIO    = cCodConv_in;
                  ELSE
                      V_PORC_DCTO_CONV := 0;
                  END IF;

                SELECT nValPrecVta_in-((nValPrecVta_in*V_PORC_DCTO_CONV)*valComp) INTO V_NVO_PRECIO
                FROM   LGT_PROD_LOCAL     PROD_LOCAL
                WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
                AND    PROD_LOCAL.COD_LOCAL     = cCodLocal_in
                AND    PROD_LOCAL.COD_PROD      = cCodProd_in;
                DBMS_OUTPUT.PUT_LINE('Dscto. por convenio');

            END IF;
         ELSE
             DBMS_OUTPUT.PUT_LINE('Sin dscto. por exclusión');
             V_NVO_PRECIO := nValPrecVta_in;
         END IF;
      ELSE
          V_NVO_PRECIO := nValPrecVta_in;
          DBMS_OUTPUT.PUT_LINE('sin dscto. por convenio no existente en local');
      END IF;

      RETURN TRIM(TO_CHAR(V_NVO_PRECIO,'999,999.990'));
  END CON_OBTIENE_NVO_PRECIO;


  /*FUNCTION CON_OBTIENE_NVO_PRECIO(cCodGrupoCia_in CHAR,
                                  cCodLocal_in    CHAR,
                                  cCodConv_in     CHAR,
                                  cCodProd_in     CHAR,
                                  nValPrecVta_in  NUMBER) RETURN CHAR
  IS
  V_NVO_PRECIO       NUMBER(8,3);
  V_PORC_DCTO_CONV   NUMBER(5,2);
  BEGIN
      SELECT PORC_DCTO_CONV/100 INTO V_PORC_DCTO_CONV
      FROM   CON_MAE_CONVENIO CONV
      WHERE  CONV.COD_CONVENIO    = cCodConv_in;

      SELECT nValPrecVta_in-(nValPrecVta_in*V_PORC_DCTO_CONV) INTO V_NVO_PRECIO
      FROM   LGT_PROD_LOCAL     PROD_LOCAL
      WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    PROD_LOCAL.COD_LOCAL     = cCodLocal_in
      AND    PROD_LOCAL.COD_PROD      = cCodProd_in;

      RETURN TRIM(TO_CHAR(V_NVO_PRECIO,'999,990.990'));
  END CON_OBTIENE_NVO_PRECIO;
*/
------------------------------------------------------------------------------------------

  FUNCTION CON_OBTIENE_DIF_CREDITO(cCodConv_in     CHAR,
                                   cCodCliente_in  CHAR,
                                   nMonto_in      NUMBER) RETURN CHAR
  IS
  V_DIFERENCIA NUMBER(10,2);
  BEGIN
       --SELECT CLI_CONV.VAL_CREDITO_MAX-(CLI_CONV.VAL_CREDITO_UTIL+nMonto_in) INTO V_DIFERENCIA
       /*SELECT DECODE(CLI_CONV.VAL_CREDITO_MAX,'0','1',CLI_CONV.VAL_CREDITO_MAX-(CLI_CONV.VAL_CREDITO_UTIL+nMonto_in)) INTO V_DIFERENCIA
       FROM   CON_CLI_CONV@XE_000   CLI_CONV
       WHERE  CLI_CONV.COD_CONVENIO    = cCodConv_in
       AND    CLI_CONV.COD_CLI         = cCodCliente_in;
       */
       BEGIN
       EXECUTE IMMEDIATE
       ' SELECT DECODE(CLI_CONV.VAL_CREDITO_MAX,'''||0||''','''||1||''',CLI_CONV.VAL_CREDITO_MAX-(CLI_CONV.VAL_CREDITO_UTIL+:1)) '||
       ' FROM   CON_CLI_CONV@XE_000   CLI_CONV '||
       ' WHERE  CLI_CONV.COD_CONVENIO    = :2' ||
       ' AND    CLI_CONV.COD_CLI         = :3' INTO V_DIFERENCIA USING nMonto_in, cCodConv_in, cCodCliente_in;
       EXCEPTION
       WHEN  NO_DATA_FOUND THEN
         V_DIFERENCIA:=0;
       END;

       RETURN TRIM(TO_CHAR(V_DIFERENCIA,'9999999,990.90'));
  END CON_OBTIENE_DIF_CREDITO;

------------------------------------------------------------------------------------------

  PROCEDURE CON_AGREGA_PEDIDO_CONVENIO(cCodGrupoCia_in   CHAR,
                                       cCodLocal_in      CHAR,
                                       cNumPedVta_in     CHAR,
                                       cCodConvenio_in   CHAR,
                                       cCodCli_in        CHAR,
                                       cUsuCreaPed_in    CHAR,
                                       cNumDocIden_in    CHAR,
                                       cCodTrab_in       CHAR,
                                       cApePatTit_in     CHAR,
                                       cApeMatTit_in     CHAR,
                                       cFecNacTit_in     CHAR,
                                       cCodSol_in        CHAR,
                                       nValPorcDcto_in   NUMBER,
                                       nValPorcCoPago_in NUMBER,
                                       cNumTelefCli_in   CHAR,
                                       cDirecCli_in      CHAR,
                                       cNomDistrito_in   CHAR,
                                       cValCopago_in     NUMBER,
                                       cCodInterno_in    CHAR,
                                       cNomCompleto_in   CHAR,
                                       cCodCliDep        CHAR ,
                                       cCodTrabDep       CHAR )
  IS
  BEGIN
      INSERT INTO CON_PED_VTA_CLI(COD_GRUPO_CIA,
                                  COD_LOCAL,
                                  NUM_PED_VTA,
                                  COD_CONVENIO,
                                  COD_CLI,
                                  FEC_CREA_PED_VTA_CLI,
                                  USU_CREA_PED_VTA_CLI,
                                  NUM_DOC_IDEN,
                                  COD_TRAB_EMPRESA,
                                  APE_PAT_TIT,
                                  APE_MAT_TIT,
                                  FEC_NAC_TIT,
                                  COD_SOLICITUD,
                                  VAL_PORC_DCTO,
                                  VAL_PORC_COPAGO,
                                  NUM_TELEF_CLI,
                                  DIRECC_CLI,
                                  NOM_DISTRITO,VAL_COPAGO_DISP,COD_CLI_INTERNO,Nom_Completo,
                                  --GRABA CODIGOS DEPENDIENTE
                                  COD_CLI_DEP,
                                  COD_TRAB_DEP)
            VALUES(cCodGrupoCia_in,
                   cCodLocal_in,
                   cNumPedVta_in,
                   cCodConvenio_in,
                   cCodCli_in,
                   SYSDATE,
                   cUsuCreaPed_in,
                   cNumDocIden_in,
                   cCodTrab_in,
                   cApePatTit_in,
                   cApeMatTit_in,
                   cFecNacTit_in,
                   cCodSol_in,
                   nValPorcDcto_in,
                   nValPorcCoPago_in,
                   cNumTelefCli_in,
                   cDirecCli_in,
                   cNomDistrito_in,
                   cValCopago_in,
                   cCodInterno_in,cNomCompleto_in,
                   cCodCliDep,
                   cCodTrabDep);
  END CON_AGREGA_PEDIDO_CONVENIO;

------------------------------------------------------------------------------------------
  /**
  JCALLO 09/01/2008
  este procedimiento falta eliminar, no se hice ya que este procedimiento esta
  siendo invocado desde otro procedimiento del paquete ptoventa_caj_anul
  **/
  PROCEDURE CON_ACTUALIZA_CONSUMO_CLI(cCodConvenio_in   CHAR,
                                      cCodCli_in        CHAR,
                                      nMonto_in         NUMBER)
  IS
  BEGIN
       /*UPDATE CON_CLI_CONV@XE_000 CLI_CONV SET VAL_CREDITO_UTIL = VAL_CREDITO_UTIL+nMonto
       WHERE  CLI_CONV.COD_CONVENIO = cCodConvenio
       AND    CLI_CONV.COD_CLI      = cCodCli;*/
       EXECUTE IMMEDIATE
       'UPDATE CON_CLI_CONV@XE_000 '||
       'SET    VAL_CREDITO_UTIL = VAL_CREDITO_UTIL +'|| TRIM(TO_CHAR(nMonto_in,'999999.00')) || ''||
       'WHERE  COD_CONVENIO = '|| ''''||cCodConvenio_in||''''||
       'AND    COD_CLI      = '|| ''''||cCodCli_in||''''; --' USING TO_CHAR(nMonto,'999999'), cCodConvenio, cCodCli;


  END CON_ACTUALIZA_CONSUMO_CLI;

------------------------------------------------------------------------------------------

  FUNCTION CON_OBTIENE_COPAGO(cCodConvenio   CHAR,
                               cCodCliente    CHAR,
                               nMonto         NUMBER) RETURN CHAR
  IS
    V_COPAGO NUMBER(9,3);
  BEGIN
      BEGIN

        SELECT ((nMonto*CONV.PORC_COPAGO_CONV)/100) INTO V_COPAGO
        FROM   CON_MAE_CONVENIO CONV,
               CON_CLI_CONV     CLI_CONV
        WHERE  CONV.COD_CONVENIO = cCodConvenio
        AND    CLI_CONV.COD_CLI  = cCodCliente
        AND    CONV.COD_CONVENIO = CLI_CONV.COD_CONVENIO;
      EXCEPTION
           WHEN NO_DATA_FOUND THEN
                V_COPAGO:=0;
      END;
     RETURN TRIM(TO_CHAR(V_COPAGO,'999,990.90'));
  END CON_OBTIENE_COPAGO;

--------------------------------------------------------------------------------------------

  FUNCTION CON_OBTIENE_FORMA_PAGO_CONV(cCodGrupoCia   CHAR,
                                        cCodConvenio   CHAR) RETURN CHAR
  IS
    V_COD_FORMA_PAGO CHAR(5);
  BEGIN
      BEGIN
        SELECT  FP.COD_FORMA_PAGO INTO V_COD_FORMA_PAGO
        FROM    VTA_FORMA_PAGO FP
        WHERE   FP.COD_GRUPO_CIA   = cCodGrupoCia
        AND     FP.COD_CONVENIO    = cCodConvenio;
      EXCEPTION
           WHEN NO_DATA_FOUND THEN
                V_COD_FORMA_PAGO:='00000';
      END;

     RETURN V_COD_FORMA_PAGO;
  END CON_OBTIENE_FORMA_PAGO_CONV;

------------------------------------------------------------------------------

  PROCEDURE CON_GRABAR_FORMA_PAGO_PED_CONV(cCodGrupoCia_in 	   IN CHAR,
  	                                   cCodLocal_in    	   IN CHAR,
  			                   cCodFormaPago_in   	   IN CHAR,
  					   cNumPedVta_in   	   IN CHAR,
  					   nImPago_in		   IN NUMBER,
  					   cTipMoneda_in	   IN CHAR,
  					   nValTipCambio_in 	   IN NUMBER,
  					   nValVuelto_in  	   IN NUMBER,
  					   nImTotalPago_in 	   IN NUMBER,
  					   cNumTarj_in  	   IN CHAR,
  					   cFecVencTarj_in  	   IN CHAR,
  					   cNomTarj_in  	   IN CHAR,
                                           cCanCupon_in  	   IN NUMBER,
  					   cUsuCreaFormaPagoPed_in IN CHAR)
  IS
  BEGIN
    INSERT INTO TMP_VTA_FORMA_PAGO_PEDIDO_CON(COD_GRUPO_CIA, COD_LOCAL, COD_FORMA_PAGO, NUM_PED_VTA,
        	   				IM_PAGO, TIP_MONEDA, VAL_TIP_CAMBIO, VAL_VUELTO,
        					IM_TOTAL_PAGO, NUM_TARJ, FEC_VENC_TARJ, NOM_TARJ,
        					USU_CREA_FORMA_PAGO_PED, CANT_CUPON)
                              VALUES (cCodGrupoCia_in, cCodLocal_in, cCodFormaPago_in, cNumPedVta_in,
					nImPago_in, cTipMoneda_in, nValTipCambio_in, nValVuelto_in,
					nImTotalPago_in, cNumTarj_in, cFecVencTarj_in, cNomTarj_in,
					cUsuCreaFormaPagoPed_in, cCanCupon_in);
  END CON_GRABAR_FORMA_PAGO_PED_CONV;

------------------------------------------------------------------------------

FUNCTION CON_OBTIENE_INFO_CONV_PED(cCodGrupoCia_in 	IN CHAR,
  	                           cCodLocal_in    	IN CHAR,
                                   cNumPedVta_in        IN CHAR,
                                   nMontoPedido_in      IN CHAR) RETURN FarmaCursor
  IS
  V_COD_CLI       CHAR(10);
  V_NUM_PED_CON   CHAR(10);
  curConv FarmaCursor;
  BEGIN

       SELECT CONV_PED.NUM_PED_VTA, CONV_PED.COD_CLI INTO V_NUM_PED_CON, V_COD_CLI
       FROM   CON_PED_VTA_CLI CONV_PED
       WHERE  CONV_PED.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CONV_PED.COD_LOCAL     = cCodLocal_in
       AND    CONV_PED.NUM_PED_VTA   = cNumPedVta_in;

       IF TRIM(V_NUM_PED_CON) <> ' ' THEN
           IF TRIM(V_COD_CLI) <> ' ' THEN
              Dbms_Output.put_line('A');
             OPEN curConv FOR
                SELECT   CON_PED.COD_CONVENIO                                                              || 'Ã' ||
                         NVL(CON_PED.COD_CLI,' ')                                                          || 'Ã' ||
                         TRIM(TO_CHAR(CON_PED.VAL_PORC_DCTO,'999,990.90'))                                 || 'Ã' ||
                         TRIM(TO_CHAR( CON_PED.VAL_PORC_COPAGO,'999,990.90'))                              || 'Ã' ||
                         CLI.NOM_CLI ||' '|| CLI.APE_PAT_CLI ||' '|| CLI.APE_MAT_CLI                       || 'Ã' ||
                         TRIM(TO_CHAR(nMontoPedido_in*(CON_PED.VAL_PORC_COPAGO/100),'999,990.90'))    || 'Ã' ||
                         TRIM(TO_CHAR(nMontoPedido_in-nMontoPedido_in*(CON_PED.VAL_PORC_COPAGO/100),'999,990.90'))
                FROM     CON_PED_VTA_CLI      CON_PED,
                         VTA_PEDIDO_VTA_CAB   CAB,
                         CON_MAE_CLIENTE      CLI
                WHERE    CON_PED.COD_GRUPO_CIA  = cCodGrupoCia_in
                AND      CON_PED.COD_LOCAL      = cCodLocal_in
                AND      CON_PED.NUM_PED_VTA    = cNumPedVta_in
                AND      CON_PED.COD_GRUPO_CIA  = CAB.COD_GRUPO_CIA
                AND      CON_PED.COD_LOCAL      = CAB.COD_LOCAL
                AND      CON_PED.NUM_PED_VTA    = CAB.NUM_PED_VTA
                AND      CON_PED.COD_CLI        = CLI.COD_CLI;
           ELSE
               Dbms_Output.put_line('B');
              OPEN curConv FOR
                SELECT   CON_PED.COD_CONVENIO                                                              || 'Ã' ||
                         NVL(CON_PED.COD_CLI,' ')                                                          || 'Ã' ||
                         TRIM(TO_CHAR(CON_PED.VAL_PORC_DCTO,'999,990.90'))                                 || 'Ã' ||
                         TRIM(TO_CHAR( CON_PED.VAL_PORC_COPAGO,'999,990.90'))                              || 'Ã' ||
                         CON_PED.APE_PAT_TIT ||' '|| CON_PED.APE_MAT_TIT                                   || 'Ã' ||
                         TRIM(TO_CHAR(nMontoPedido_in*(CON_PED.VAL_PORC_COPAGO/100),'999,990.90'))    || 'Ã' ||
                         TRIM(TO_CHAR(nMontoPedido_in-nMontoPedido_in*(CON_PED.VAL_PORC_COPAGO/100),'999,990.90'))
                FROM     CON_PED_VTA_CLI      CON_PED,
                         VTA_PEDIDO_VTA_CAB   CAB
                WHERE    CON_PED.COD_GRUPO_CIA  = cCodGrupoCia_in
                AND      CON_PED.COD_LOCAL      = cCodLocal_in
                AND      CON_PED.NUM_PED_VTA    = cNumPedVta_in
                AND      CON_PED.COD_GRUPO_CIA  = CAB.COD_GRUPO_CIA
                AND      CON_PED.COD_LOCAL      = CAB.COD_LOCAL
                AND      CON_PED.NUM_PED_VTA    = CAB.NUM_PED_VTA;
           END IF;
       END IF;
     RETURN curConv;
  END CON_OBTIENE_INFO_CONV_PED;

-------------------------------------------------------------------

  PROCEDURE CON_ACTUALIZA_NUM_PED(cCodGrupoCia_in 	 	    IN CHAR,
  	                              cCodLocal_in    	 	    IN CHAR,
                                  cNumPedVta_in           IN CHAR,
                                  cNumPedVtaDel_in        IN CHAR) IS
  BEGIN

       UPDATE TMP_VTA_FORMA_PAGO_PEDIDO_CON FPP_CON
       SET    NUM_PED_VTA = NUM_PED_VTA
       WHERE  FPP_CON.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    FPP_CON.COD_LOCAL     = cCodLocal_in
       AND    FPP_CON.NUM_PED_VTA   = cNumPedVtaDel_in;

  END CON_ACTUALIZA_NUM_PED;

------------------------------------------------------------------------------------

  FUNCTION TMP_CON_OBTIENE_INFO_CONV_PED(cCodGrupoCia_in 	 	     IN CHAR,
  	                                     cCodLocal_in    	 	     IN CHAR,
                                         cNumPedVta_in           IN CHAR,
                                         nMontoPedido_in         IN NUMBER) RETURN FarmaCursor
  IS
  V_COD_CLI       CHAR(10);
  V_NUM_PED_CON   CHAR(10);
  curConv FarmaCursor;
  BEGIN

       SELECT CONV_PED.NUM_PED_VTA, CONV_PED.COD_CLI INTO V_NUM_PED_CON, V_COD_CLI
       FROM   CON_PED_VTA_CLI CONV_PED
       WHERE  CONV_PED.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CONV_PED.COD_LOCAL     = cCodLocal_in
       AND    CONV_PED.NUM_PED_VTA   = cNumPedVta_in;

       IF TRIM(V_NUM_PED_CON) <> ' ' THEN
           IF TRIM(V_COD_CLI) <> ' ' THEN
              Dbms_Output.put_line('A');
             OPEN curConv FOR
                SELECT   CON_PED.COD_CONVENIO                                                              || 'Ã' ||
                         NVL(CON_PED.COD_CLI,' ')                                                          || 'Ã' ||
                         TRIM(TO_CHAR(CON_PED.VAL_PORC_DCTO,'999,990.90'))                                 || 'Ã' ||
                         TRIM(TO_CHAR( CON_PED.VAL_PORC_COPAGO,'999,990.90'))                              || 'Ã' ||
                         DECODE(TRIM(CLI.NOM_COMPLETO),'',CLI.NOM_CLI || ' ' || CLI.APE_PAT_CLI || ' ' || CLI.APE_MAT_CLI,CLI.NOM_COMPLETO) || 'Ã' ||
                         --TRIM(TO_CHAR(nMontoPedido_in*(CON_PED.VAL_PORC_COPAGO/100),'999,990.90'))    || 'Ã' ||
                         --TRIM(TO_CHAR(nMontoPedido_in-nMontoPedido_in*(CON_PED.VAL_PORC_COPAGO/100),'999,990.90'))
                         TRIM(TO_CHAR(CON_PED.VAL_COPAGO_DISP,'999,990.90'))    || 'Ã' ||
                         TRIM(TO_CHAR(CAB.VAL_NETO_PED_VTA-CON_PED.VAL_COPAGO_DISP,'999,990.90')) || 'Ã' ||
                         CASE
                         WHEN CON.IND_MUESTRA_CRED_DISP = 'S' THEN  TRIM(TO_CHAR(NVL(CON_PED.VAL_CREDITO_DISP,0),'999,990.90'))

                         ELSE ' '
                         END|| 'Ã' ||
                         TRIM(CON.IND_COMPETENCIA)--JCORTEZ 10/10/2008
                FROM     CON_PED_VTA_CLI      CON_PED,
                         VTA_PEDIDO_VTA_CAB   CAB,
                         CON_MAE_CLIENTE      CLI,
                         CON_MAE_CONVENIO CON
                WHERE    CON_PED.COD_GRUPO_CIA  = cCodGrupoCia_in
                AND      CON_PED.COD_LOCAL      = cCodLocal_in
                AND      CON_PED.NUM_PED_VTA    = cNumPedVta_in
                AND      CON_PED.COD_GRUPO_CIA  = CAB.COD_GRUPO_CIA
                AND      CON_PED.COD_LOCAL      = CAB.COD_LOCAL
                AND      CON_PED.NUM_PED_VTA    = CAB.NUM_PED_VTA
                AND      CON_PED.COD_CLI        = CLI.COD_CLI
                AND      CAB.COD_CONVENIO = CON.COD_CONVENIO;
           ELSE
               Dbms_Output.put_line('B');
              OPEN curConv FOR
                SELECT   CON_PED.COD_CONVENIO                                                              || 'Ã' ||
                         NVL(CON_PED.COD_CLI,' ')                                                          || 'Ã' ||
                         TRIM(TO_CHAR(CON_PED.VAL_PORC_DCTO,'999,990.90'))                                 || 'Ã' ||
                         TRIM(TO_CHAR( CON_PED.VAL_PORC_COPAGO,'999,990.90'))                              || 'Ã' ||
                         --CON_PED.NOM_COMPLETO ||' '|| CON_PED.APE_PAT_TIT ||' '|| CON_PED.APE_MAT_TIT                                   || 'Ã' ||
                         CON.DESC_CORTA_CONV                                            || 'Ã' ||
                         TRIM(TO_CHAR(nMontoPedido_in*(CON_PED.VAL_PORC_COPAGO/100),'999,990.90'))    || 'Ã' ||
                         TRIM(TO_CHAR(nMontoPedido_in-nMontoPedido_in*(CON_PED.VAL_PORC_COPAGO/100),'999,990.90')) || 'Ã' ||
                         CASE
                         WHEN CON.IND_MUESTRA_CRED_DISP = 'S' THEN  TRIM(TO_CHAR(NVL(CON_PED.VAL_CREDITO_DISP,0),'999,990.90'))
                         ELSE ' '
                         END|| 'Ã' ||
                         TRIM(CON.IND_COMPETENCIA) --JCORTEZ 10/10/2008
                FROM     CON_PED_VTA_CLI      CON_PED,
                         VTA_PEDIDO_VTA_CAB   CAB,
                         CON_MAE_CONVENIO    CON --JCORTEZ  16/01/2007
                WHERE    CON_PED.COD_GRUPO_CIA  = cCodGrupoCia_in
                AND      CON_PED.COD_LOCAL      = cCodLocal_in
                AND      CON_PED.NUM_PED_VTA    = cNumPedVta_in
                AND      CON_PED.COD_GRUPO_CIA  = CAB.COD_GRUPO_CIA
                AND      CON_PED.COD_LOCAL      = CAB.COD_LOCAL
                AND      CON_PED.NUM_PED_VTA    = CAB.NUM_PED_VTA
                AND      CAB.COD_CONVENIO=CON.COD_CONVENIO; --JCORTEZ  16/01/2007
           END IF;
       END IF;
     RETURN curConv;
  END;
-------------------------------------------------------------------

  PROCEDURE CON_LLENA_LISTA_EXCLUIDOS(cCodGrupoCia_in 	 	    IN CHAR)
  IS
  vCodLista CHAR(5) := '00001';
  BEGIN
    INSERT INTO CON_PROD_LISTA(COD_GRUPO_CIA,COD_LISTA,COD_PROD,EST_PROD_LISTA)
         (SELECT cCodGrupoCia_in,
                 vCodLista,
                 V1.COD_PROD,
                 C_ESTADO_ACTIVO
          FROM   (SELECT  PROD.COD_PROD
                  FROM    LGT_PROD PROD
                  WHERE   PROD.IND_PRECIO_CONTROL = 'S'
                  MINUS
                  SELECT   CPL.COD_PROD
                  FROM     CON_PROD_LISTA CPL
                  WHERE    CPL.COD_GRUPO_CIA  = cCodGrupoCia_in) V1);
  END CON_LLENA_LISTA_EXCLUIDOS;

  FUNCTION CON_OBTIENE_CREDITO(cCodConv_in     CHAR,
                              cCodCliente_in  CHAR) RETURN CHAR
  IS
  V_CREDITO NUMBER(10,2);
  BEGIN
       EXECUTE IMMEDIATE 'SELECT VAL_CREDITO_MAX '||
       'FROM   CON_CLI_CONV@XE_000   CLI_CONV '||
       'WHERE  CLI_CONV.COD_CONVENIO    = :1' ||
       'AND    CLI_CONV.COD_CLI         = :2' INTO V_CREDITO USING cCodConv_in, cCodCliente_in;

       RETURN TRIM(TO_CHAR(V_CREDITO,'999,990.00'));
  END CON_OBTIENE_CREDITO;

  FUNCTION CON_OBTIENE_CREDITO_UTIL(cCodConv_in     CHAR,
                                    cCodCliente_in  CHAR) RETURN CHAR
  IS
  V_CREDITO_UTIL NUMBER(10,2);
  BEGIN
       EXECUTE IMMEDIATE 'SELECT VAL_CREDITO_UTIL '||
       'FROM   CON_CLI_CONV@XE_000   CLI_CONV '||
       'WHERE  CLI_CONV.COD_CONVENIO    = :1' ||
       'AND    CLI_CONV.COD_CLI         = :2' INTO V_CREDITO_UTIL USING cCodConv_in, cCodCliente_in;

       RETURN TRIM(TO_CHAR(V_CREDITO_UTIL,'999,990.00'));
  END CON_OBTIENE_CREDITO_UTIL;


  PROCEDURE CON_GRABAR_FP_PED_CONV_LOCAL(cCodGrupoCia_in 	 	     IN CHAR,
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
  									  	                   cUsuCreaFormaPagoPed_in IN CHAR)
  IS
  BEGIN
    INSERT INTO TMP_VTA_FPAGO_PED_CON_LOCAL(COD_GRUPO_CIA, COD_LOCAL, COD_FORMA_PAGO, NUM_PED_VTA,
        	   							  	                IM_PAGO, TIP_MONEDA, VAL_TIP_CAMBIO, VAL_VUELTO,
        								  	                  IM_TOTAL_PAGO, NUM_TARJ, FEC_VENC_TARJ, NOM_TARJ,
        								  	                  USU_CREA_FORMA_PAGO_PED, CANT_CUPON)
                              VALUES (cCodGrupoCia_in, cCodLocal_in, cCodFormaPago_in, cNumPedVta_in,
									                    nImPago_in, cTipMoneda_in, nValTipCambio_in, nValVuelto_in,
									                    nImTotalPago_in, cNumTarj_in, cFecVencTarj_in, cNomTarj_in,
									                    cUsuCreaFormaPagoPed_in, cCanCupon_in);
  END CON_GRABAR_FP_PED_CONV_LOCAL;

  --Descripcion: lISTA LOS CLIENTES DEPENDIENTES DEL CLIENTE POR CONVENIO
  --Fecha       Usuario		Comentario
  --04/02/2008  DUBILLUZ   CREACION
 FUNCTION CONV_LISTA_CLI_DEP_CONVENIO(cCodConvenio_in IN CHAR,
                                      cCodCli_in      IN CHAR)
  RETURN FarmaCursor
  IS
    curConv FarmaCursor;
  BEGIN
    OPEN curConv FOR
    SELECT M.cod_cli  || 'Ã' ||
           (select C.COD_TRAB_CONV
            from   CON_CLI_CONV  C
            WHERE  C.COD_CONVENIO =cCodConvenio_in
            AND    C.COD_CLI = D.COD_CLI_DEP )|| 'Ã' ||
           DECODE(TRIM(M.NOM_COMPLETO),'',M.NOM_CLI || ' ' || M.APE_PAT_CLI || ' ' || M.APE_MAT_CLI,M.NOM_COMPLETO)  || 'Ã' ||
           M.NUM_DOC_CLI || 'Ã' ||
           to_number(C.COD_TRAB_CONV )
    FROM   CON_CLI_DEP_CONV D,
           CON_CLI_CONV C,
           CON_MAE_CLIENTE M
    WHERE  D.COD_CONVENIO = cCodConvenio_in
    AND    D.COD_CLI = cCodCli_in
    AND    D.EST_CLI_DEP = C_ESTADO_ACTIVO
    AND    D.COD_CLI_DEP = M.COD_CLI
    AND    D.COD_CONVENIO = C.COD_CONVENIO
    AND    D.COD_CLI = C.COD_CLI;


    RETURN curConv;
  END;
  /***************************************************************************/
  PROCEDURE CONV_ACTUALIZA_CRED_DISP(cCodConvenio_in   IN CHAR,
                               cCodCliente_in          IN CHAR,
                               cCodGrupoCia_in 	 	  IN CHAR,
  	                           cCodLocal_in    	 	  IN CHAR,
  									   	       cNumPedVta_in   	 	  IN CHAR,
                               nMonto_in            IN NUMBER)
  IS
  BEGIN
    UPDATE CON_PED_VTA_CLI C
    SET C.VAL_CREDITO_DISP = nMonto_in
    WHERE C.COD_CONVENIO = cCodConvenio_in
          AND C.COD_CLI = cCodCliente_in
          AND C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.NUM_PED_VTA = cNumPedVta_in;
  END;
  /***************************************************************************/
  FUNCTION GET_NOMBRE_CLIENTE_NUMDOC(cCodConvenio_in IN CHAR,cNumDoc_in IN CHAR)
  RETURN FarmaCursor
  IS
    vDatos FarmaCursor;
  BEGIN
    OPEN vDatos FOR
    SELECT MCL.cod_cli || 'Ã' ||
                --CC.COD_TRAB_CONV || 'Ã' ||
                DECODE(TRIM(MCL.NOM_COMPLETO),'',MCL.NOM_CLI || ' ' || MCL.APE_PAT_CLI || ' ' || MCL.APE_MAT_CLI,MCL.NOM_COMPLETO)|| 'Ã' ||
                --MCL.NUM_DOC_CLI  || 'Ã' ||
                to_number(CC.COD_TRAB_CONV )
         FROM   con_mae_cliente mcL,
                CON_MAE_CONVENIO MC,
                CON_CLI_CONV CC
         WHERE  MCL.COD_CLI = CC.COD_CLI
         AND    MC.COD_CONVENIO = CC.COD_CONVENIO
         AND    MC.COD_CONVENIO = cCodConvenio_in
         AND    CC.EST_CONV_CLI = 'A'
                AND MCL.NUM_DOC_CLI = cNumDoc_in
         AND    CC.TIPO_CLI = C_TITULAR
    ;

    RETURN vDatos;
  END;
  /***************************************************************************/

  FUNCTION CON_PORC_COPAGO_CONV(cCodConvenio_in   IN CHAR)

   RETURN CHAR

   IS
   v_porc_copago number(8,2);
   v_escredito       char(1);
   BEGIN

     SELECT  PORC_COPAGO_CONV  INTO v_porc_copago
     FROM   con_mae_convenio
     WHERE COD_CONVENIO = cCodConvenio_in ;

     if v_porc_copago = 100 then

      v_escredito := 'S';

     else

      v_escredito := 'N';

     end if;

    --RETURN TRIM(TO_CHAR(v_porc_copago,'999,999.990'));

    RETURN v_escredito;
   END;


/********************************************************************* */
  FUNCTION IMP_DATOS_CONVENIO(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cCodConvenio_in   IN CHAR,
                                cCodCli_in        IN CHAR,
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

  cursor1 FarmaCursor:=VTA_OBTENER_DATA1(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cCodConvenio_in,cCodCli_in);
  cursor2 FarmaCursor:=VTA_OBTENER_DATA2(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cCodConvenio_in,cCodCli_in);
  --cursor3 FarmaCursor:=VTA_OBTENER_DATA3(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);
  cursor4 FarmaCursor:=VTA_OBTENER_DATA4(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cCodConvenio_in,cCodCli_in);

  vCodCampana CHAR(5) := 'N';
  vCantidad   vta_pedido_cupon.CANTIDAD%type := 0;
  vDescripMensaje varchar2(3000);
  vNumPedidoLocal CHAR(10);
  vMensaje varchar2(3000);

  v_montoMonedaOrigen       varchar2(200);

  vCodConvenio              VARCHAR2(10);
    vCodCli             VARCHAR2(10);
    vDescConv           VARCHAR2(50);
    vNomusu             VARCHAR2(50);
    vCredito            VARCHAR2(50);

  BEGIN

    --------------
    LOOP
    FETCH cursor4  INTO v_fechaComanda,v_numPedDel,vCodConvenio, vCodCli,vDescConv;
    EXIT WHEN cursor4%NOTFOUND;
        vFila_4 := vFila_4 || '<table width="100%" border="0" cellpadding="1" cellspacing="1">'||
                              '<tr>'||
                                '<td width="50%" align="right" class="style1">FECHA Y HORA : </td>'||
                                '<td width="50%" class="style1">'||TRIM(v_fechaComanda)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td align="right" class="style1">NUM PEDDIDO : </td>'||
                                '<td class="style1">'||TRIM(v_numPedDel)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td align="right" class="style1">COD CONVENIO : </td>'||
                                '<td class="style1">'||TRIM(vCodConvenio)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td align="right" class="style1">COD CLI : </td>'||
                                '<td class="style1">'||TRIM(cCodCli_in)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td align="right" class="style3">CONVENIO : </td>'||
                                '<td class="style3">'|| TRIM(vDescConv)||'</td>'||
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
      FETCH cursor1 INTO  vNomusu,v_nomcli,v_montoRed,v_vuelto,vCredito;
      EXIT WHEN cursor1%NOTFOUND;
       DBMS_OUTPUT.PUT_LINE('..............');
        vFila_1 := vFila_1 || '<table width="100%" border="0" cellpadding="0" cellspacing="0">'||
                              '<tr>'||
                                '<td width="100" class="style1">ATENDIDO : </td>'||
                                '<td width="310" class="style4">'||TRIM(vNomusu)||'</td>'||
                              '</tr>'||
                              '<tr>'||
                                '<td class="style1">NOM / CLI : </td>'||
                                '<td class="style4">'||TRIM(v_nomcli)||'</td>'||
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
                                '<td class="style1">CREDITO DISPONIBLE : </td>'||
                                '<td class="style4">'||TRIM(vCredito)||'</td>'||
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
    FETCH cursor2 INTO v_formaPago,v_moneda,v_monto,v_numTarj,v_FecVenc,v_numDocIdent,v_CodLote, v_CodAutorizacion, v_montoMonedaOrigen;

    EXIT WHEN cursor2%NOTFOUND;
        vFila_2 := vFila_2||'<tr>'||
                          '  <td width="30%" class="style1">'||TRIM(v_formaPago)||' '||TRIM(v_numTarj)||' '||TRIM(v_FecVenc)||' '||TRIM(v_numDocIdent)||'</td>'||
                          --'  <td width="12%" class="style1">'||TRIM(v_moneda) ||'</td>'||
                          --'  <td width="14%" class="style1">'|| CASE WHEN TRIM(UPPER(v_moneda)) = 'DOLARES' THEN '( ' || v_montoMonedaOrigen || ' )' ELSE ' ---------- ' END ||'</td>'||
                          '  <td width="14%" class="style1">'||TRIM(v_monto) ||'</td>'||
                         -- '  <td width="8%" class="style1">'||TRIM(v_CodLote) ||'</td>'||
                          --'  <td width="18%" class="style1">'||TRIM(v_CodAutorizacion) ||'</td>'||
                          '</tr>';
    END
    LOOP;


    -------------------------------------------------------

    vFila_3:= '<table width="100%" border="0" cellpadding="0" cellspacing="0">'||
                '<tr>' ||
               ' <td width="5%" class="style1">FIRMA : </td> '||
               '  <td width="40%" class="style4">     ................................................................................................................................</td>'||
               '  </tr> ';

     dbms_output.put_line('CAN :'||vFila_4);


     vMsg_out := TRIM(C_INICIO_MSG) ||
                 vFila_4  ||
                 vFila_1  ||
                 TRIM(C_FORMA_PAGO)||
                 vFila_2 ||
                 TRIM(C_FIN_FORMA_PAGO)||
                 C_FILA_VACIA||
                 C_FILA_VACIA||
                 vFila_3||
                 TRIM(C_FIN_MSG) ;

         dbms_output.put_line('CANT :'||LENGTH(vMsg_out));
         dbms_output.put_line(vMsg_out);

     RETURN vMsg_out;

  END;


  /*************************************************************************************************************/
  FUNCTION VTA_OBTENER_DATA4(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in	   IN CHAR,
                               cNumPedVta_in   IN CHAR,
                               cCodConvenio_in IN CHAR,
                               cCodCli_in      IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    vCod_local_origen char(3);
  BEGIN
    OPEN curVta FOR
    SELECT TO_CHAR(A.FEC_PED_VTA,'DD/MM/YYYY HH24:MI:SS') FECHA,
           A.NUM_PED_VTA,
           B.COD_CONVENIO,
           A.COD_CLI_LOCAL,
           B.DESC_CORTA_CONV
    FROM VTA_PEDIDO_VTA_CAB A,
         CON_MAE_CONVENIO B
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.NUM_PED_VTA=cNumPedVta_in
    AND B.COD_CONVENIO=cCodConvenio_in
    AND A.COD_CONVENIO=B.COD_CONVENIO;
  RETURN curVta;
  END;

/**********************************************************************************************************/

 FUNCTION VTA_OBTENER_DATA1(cCodGrupoCia_in IN CHAR,
  		   				             cCodLocal_in	   IN CHAR,
							               cNumPedVta_in   IN CHAR,
                             cCodConvenio_in IN CHAR,
                             cCodCli_in      IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    int_total number;
    vCod_local_origen char(3);
  BEGIN
    SELECT SUM(IM_TOTAL_PAGO) INTO int_total
    FROM VTA_FORMA_PAGO_PEDIDO X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.NUM_PED_VTA=cNumPedVta_in
    AND X.COD_LOCAL=cCodLocal_in;

    OPEN curVta FOR
    SELECT   E.NOM_USU||' '||E.APE_PAT||' '||E.APE_MAT NOM_USU,
             D.NOM_COMPLETO,
             TO_CHAR(A.VAL_NETO_PED_VTA,'999,999,990.00')v_montoRed,
             TO_CHAR(int_total-A.VAL_NETO_PED_VTA,'999,999,990.00')v_vuelto,
             TO_CHAR(VAL_CREDITO_MAX-VAL_CREDITO_UTIL,'999,999,990.00') CREDITO
    FROM VTA_PEDIDO_VTA_CAB A,
         CON_PED_VTA_CLI B,
         CON_CLI_CONV@XE_000 C,
         CON_MAE_CLIENTE D,
         PBL_USU_LOCAL E
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.NUM_PED_VTA=cNumPedVta_in
    AND A.COD_CONVENIO=cCodConvenio_in
    AND C.COD_CLI=cCodCli_in
    AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
    AND A.COD_LOCAL=B.COD_LOCAL
    AND A.NUM_PED_VTA=B.NUM_PED_VTA
    AND A.COD_CONVENIO=B.COD_CONVENIO
    AND B.COD_CONVENIO=C.COD_CONVENIO
    AND B.COD_CLI=C.COD_CLI
    AND C.COD_CLI=D.COD_CLI
    AND A.COD_GRUPO_CIA=E.COD_GRUPO_CIA
    AND A.COD_LOCAL=E.COD_LOCAL
    AND A.SEC_USU_LOCAL=E.SEC_USU_LOCAL;
    RETURN curVta;
  END;


  /****************************************************************************************/
   FUNCTION VTA_OBTENER_DATA2(cCodGrupoCia_in IN CHAR,
  		   				             cCodLocal_in	    IN CHAR,
							               cNumPedVta_in    IN CHAR,
                             cCodConvenio_in  IN CHAR,
                             cCodCli_in       IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    vCod_local_origen char(3);
  BEGIN
    OPEN curVta FOR
    SELECT nvl(C.DESC_CORTA_FORMA_PAGO,' '),
          CASE B.TIP_MONEDA
          WHEN '01' THEN 'Soles'
          WHEN '02' THEN 'Dolares'
          END,
          TO_CHAR(B.IM_TOTAL_PAGO,'999,999,990.00'),
           NVL(B.NUM_TARJ,' '),
           TO_CHAR(B.FEC_VENC_TARJ,'DD/MM'),
           NVL(B.DNI_CLI_TARJ,' '),
           NVL(B.COD_LOTE, ' '),
           NVL(B.COD_AUTORIZACION, ' '),
          TO_CHAR(B.IM_PAGO,'999,999,990.00')
    FROM VTA_PEDIDO_VTA_CAB A,
         VTA_FORMA_PAGO_PEDIDO B,
         VTA_FORMA_PAGO C
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.NUM_PED_VTA=cNumPedVta_in
    AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
    AND A.COD_LOCAL=B.COD_LOCAL
    AND A.NUM_PED_VTA=B.NUM_PED_VTA
    AND B.COD_FORMA_PAGO=C.COD_FORMA_PAGO;


  RETURN curVta;
  END;
/**********************************************************************************************************/
  FUNCTION CONSUL_PORC_FORM_PAG(cCodConvenio_in   IN CHAR)

   RETURN CHAR

   IS
   v_porc_copago number(8,2);
   BEGIN

      SELECT  PORC_COPAGO_CONV  INTO v_porc_copago
      FROM   con_mae_convenio
      WHERE COD_CONVENIO = cCodConvenio_in ;

      RETURN TRIM(TO_CHAR(v_porc_copago,'999,999'));
   END;

END;
/

