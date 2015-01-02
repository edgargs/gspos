CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_RECARGA" AS
TYPE FarmaCursor IS REF CURSOR;

	COD_NUM_SEC_MOV_CAJ   PBL_NUMERA.COD_NUMERA%TYPE := '010';
	ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';
	POS_INICIO		      CHAR(1):='I';
  TIP_MOV_APERTURA	  CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='A';
	TIP_MOV_CIERRE  	  CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='C';
	TIP_MOV_ARQUEO  	  CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='R';
	EST_PED_PENDIENTE  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='P';
	EST_PED_ANULADO  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='N';
	EST_PED_COBRADO  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='C';
	EST_PED_COB_NO_IMPR  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='S';
	COD_TIP_COMP_BOLETA    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='01';
	COD_TIP_COMP_FACTURA   VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='02';
	COD_TIP_COMP_GUIA      VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='03';
	COD_TIP_COMP_NOTA_CRED VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='04';

	TIPO_COMPROBANTE_99		  CHAR(2):='99';

C_CARACTER_INIC CHAR(1) := '$';
  C_ESTADO_ACTIVO CHAR(1) := 'A';
  C_ESTADO_EMITIDO CHAR(1) := 'E';

  C_ESTADO_ANULADO CHAR(1) := 'N';
  C_ESTADO_USADO CHAR(1) := 'U';

  C_C_COD_EAN_CUPON PBL_NUMERA.COD_NUMERA%TYPE := '045';

  C_C_SERIE_VTA_INSTITUCIONAL NUMBER := 500;
  C_C_COD_NUMERA_TRACE CHAR(3) := '028';
  COD_TIP_MON_SOLES    CHAR(2) := '01';
	COD_TIP_MON_DOLARES  CHAR(2) := '02';
  DESC_TIP_MON_SOLES   VARCHAR2(10) := 'SOLES';
	DESC_TIP_MON_DOLARES VARCHAR2(10) := 'DOLARES';

  C_COD_PROV_TELF_CLARO    CHAR(1) := 'F' ;
  C_COD_PROV_TELF_MOVISTAR CHAR(1) := 'A';
  C_COD_PROV_DIRECTV CHAR(3) := 'DTV';

 C_INICIO_MSG_1 VARCHAR2(1000) := '<html><head><style type="text/css">' ||
                                   '.titulo {font-size: 10;font-family:sans-serif;font-style: italic;}' ||
                                   '.cajero {font-size: 12;font-family: Arial, Helvetica, sans-serif;border-style: solid;} ' ||
                                   '.histcab {font-size: 11;font-family: Arial, Helvetica, sans-serif;border-style: solid;} ' ||
                                   '.historico{font-size: 12;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.msgfinal {font-size: 11;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.tip{font-size: 12;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.fila{border-style: solid;}' ||
                                   '</style>' || '</head>' || '<body>' ||
                                   '<table width="210" border="0">' ||
                                   '<tr>' || '<td>&nbsp;&nbsp;</td>' ||
                                   '<td>' ||
                                   '<table width="310" height="350"  border="0" cellspacing="0" cellpadding="5">';

/*********************************************************************************************************************************/


  --Descripcion: Envia informacion de la recarga
  --Fecha       Usuario		Comentario
  --13/01/2009  ASolis    Creación


   FUNCTION RE_F_VERIFICA_RECARGA_PEDIDO(cCodGrupoCia_in  IN CHAR,
              		                         cCodLocal_in    IN CHAR,
              		                         cNumPedVta_in   IN CHAR,
                                           nMontoVta_in    IN NUMBER)
   RETURN FarmaCursor;

   --Descripcion: Verifica si el pedido existe
 --Fecha       Usuario		Comentario
  --13/01/2009  ASolis    Creación
  FUNCTION RE_F_VERIFICA_RECARGA_COMPROB(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cTipComp_in     IN CHAR,
                                    cNumComp_in     IN CHAR,
                                    nMontoVta_in    IN NUMBER)

  RETURN FarmaCursor;

  ---Descripcion : Muestra el Mensaje de ERROR de la recarga Virtual
  --Fecha       Usuario		Comentario
  --15/01/2009  ASolis    Creación
  FUNCTION RE_F_MOSTRAR_MENSAJE_ERROR (cCodigoError_in IN CHAR)

  RETURN CHAR;


  --Descripcion: Obtiene las series asignadas al local por tipo de documento.
  --Fecha       Usuario		Comentario
  --15/01/2009  ASolis    Creación

  FUNCTION RE_F_GET_SERIE_RE(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cTipDoc_in      IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene el numero pedido venta  por tipo de documento.
  --Fecha       Usuario		Comentario
  --16/01/2009  ASolis    Creación


  FUNCTION RE_F_GET_NUMERO_PEDIDO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cTipComp_in     IN CHAR,
                                    cNumComp_in     IN CHAR,
                                    nMontoVta_in    IN NUMBER)

  RETURN CHAR;

  FUNCTION RE_F_IS_PERMITE_ANULACION(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cNumPedVta_in   IN CHAR)

  RETURN CHAR;


  --Descripcion: Numero de intentos para obtener informacion de Recarga Virtual
  --autor:  asolis
  --fecha:  05/02/2009

  FUNCTION RE_F_CANT_INT_RECARGA_VIRTUAL
  RETURN CHAR;


  --Descripcion: Impresión de Ticket para consulta de Recarga
  --autor:  asolis
  --fecha:  10/02/2009

   FUNCTION RE_F_IMP_HTML_RECARGA_PEDIDO(cNumPedVta_in     IN CHAR,
                                       /*  \*cFechaPedido      IN CHAR,*\
                                         cProveedor        IN CHAR,
                                         cTelefono         IN CHAR,*/
                                           nMontoVta_in       IN NUMBER/*,
                                         cRespuestaRecarga IN CHAR,
                                         cComunicado       IN CHAR*/)
  return varchar2;




 /* --Fecha       Usuario		Comentario
  --10/02/2009  ASolis    Creación


   FUNCTION RE_F_VERIFICA_TipoProveedor(cCodGrupoCia_in    IN CHAR,
              		                         cCodLocal_in    IN CHAR,
              		                         cNumPedVta_in   IN CHAR,
                                           vCodProd        IN CHAR,
                                           v_cod_prov_tel  IN VARCHAR2)
   RETURN FarmaCursor;*/
  --Descripcion: Devuelve el mensaje formateado de acuerdo a si es para label y Message
  --Fecha       Usuario   Comentario
  --19/04/2010  ASOSA    Creación
  FUNCTION REC_F_GET_MSG(CCODMSG_IN IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Devuelve el indicador de label para el codigo de mensaje indicado
  --Fecha       Usuario   Comentario
  --19/04/2010  ASOSA    Creación
  FUNCTION REC_F_GET_IND_LABEL(cCodMsg_in IN CHAR)
  RETURN CHAR;


END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_RECARGA" AS

 /*COD_PROV_TEL , DESC_PROD,DESC_NUM_TEL_REC,VAL_PREC_TOTAL*/

 --CORRELATIVO ,NROPEDIDOVENTA
    FUNCTION RE_F_VERIFICA_RECARGA_PEDIDO(cCodGrupoCia_in  IN CHAR,
                                            cCodLocal_in     IN CHAR,
                                            cNumPedVta_in    IN CHAR,
                                            nMontoVta_in     IN NUMBER)
    RETURN FarmaCursor
   IS
    curDet FarmaCursor;
    BEGIN

     OPEN curDet FOR





     SELECT nvl(DECODE(TRIM(VIR.COD_PROV_TEL),'A','Movistar','F','Claro','DTV','Direct Tv'),' ') || 'Ã' ||
            nvl(P.DESC_PROD,' ') || 'Ã' ||
            nvl(DET.DESC_NUM_TEL_REC,' ') || 'Ã' ||
            CAB.VAL_NETO_PED_VTA || 'Ã' ||
            nvl(TO_CHAR(CAB.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss'),' ')|| 'Ã' ||
            TO_CHAR(SYSDATE,'dd/MM/yyyy HH24:mi:ss')


     FROM   VTA_PEDIDO_VTA_DET DET,
                  VTA_PEDIDO_VTA_CAB CAB,
                  LGT_PROD_VIRTUAL VIR,
                  LGT_PROD P

    WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
           AND    DET.COD_LOCAL = CAB.COD_LOCAL
           AND    DET.NUM_PED_VTA = CAB.NUM_PED_VTA
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+)
           AND    CAB.VAL_NETO_PED_VTA = nMontoVta_in
           AND    P.COD_PROD = VIR.COD_PROD
           AND  VIR.COD_PROD  IN (SELECT COD_PROD
                                 FROM   VTA_PEDIDO_VTA_DET DET
                                 WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
                                 AND    DET.COD_LOCAL = cCodLocal_in
                                 AND    DET.NUM_PED_VTA = cNumPedVta_in);


   -- dbms_output.put_line('a');

       RETURN curDet;
    END;


-----------------------------------------------------------------------------------------------------------------
FUNCTION RE_F_VERIFICA_RECARGA_COMPROB(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cTipComp_in     IN CHAR,
                                    cNumComp_in     IN CHAR,
                                    nMontoVta_in    IN NUMBER)

  RETURN FarmaCursor
  IS
   curDet FarmaCursor;
    BEGIN

     OPEN curDet FOR

       SELECT DISTINCT (DECODE(TRIM(VIR.COD_PROV_TEL),'A','Movistar','F','Claro','DTV','Direct Tv')) || 'Ã' ||
            P.DESC_PROD || 'Ã' ||
            DET.DESC_NUM_TEL_REC || 'Ã' ||
            (SELECT VAL_NETO_PED_VTA FROM VTA_PEDIDO_VTA_CAB WHERE NUM_PED_VTA = C.NUM_PED_VTA AND COD_GRUPO_CIA =cCodGrupoCia_in AND COD_LOCAL=cCodLocal_in)|| 'Ã' ||
            C.NUM_COMP_PAGO || 'Ã' ||
            TO_CHAR((SELECT CAB.FEC_PED_VTA FROM VTA_PEDIDO_VTA_CAB CAB WHERE CAB.NUM_PED_VTA = C.NUM_PED_VTA AND COD_GRUPO_CIA =cCodGrupoCia_in AND COD_LOCAL=cCodLocal_in),'dd/MM/yyyy HH24:mi:ss') || 'Ã' ||
            TO_CHAR(SYSDATE,'dd/MM/yyyy HH24:mi:ss')


       FROM   VTA_COMP_PAGO C,
              VTA_PEDIDO_VTA_DET DET,
              VTA_PEDIDO_VTA_CAB CAB,
              LGT_PROD_VIRTUAL VIR,
              LGT_PROD P

       WHERE  C.TIP_COMP_PAGO = cTipComp_in
       AND    C.NUM_COMP_PAGO = cNumComp_in
       AND    C.VAL_NETO_COMP_PAGO + C.VAL_REDONDEO_COMP_PAGO = nMontoVta_in
       AND    C.IND_COMP_ANUL = 'N'
       AND    C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    C.COD_LOCAL = cCodLocal_in
       AND    C.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
       AND    C.COD_LOCAL = DET.COD_LOCAL
       AND    C.NUM_PED_VTA = DET.NUM_PED_VTA
       AND    DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
       AND    DET.COD_LOCAL = CAB.COD_LOCAL
       AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
       AND    DET.COD_PROD = VIR.COD_PROD(+)
       AND    P.COD_PROD = VIR.COD_PROD;

       RETURN curDet;

    END;

----------------------------------------------------------------------------------------------------
 FUNCTION RE_F_MOSTRAR_MENSAJE_ERROR (cCodigoError_in IN CHAR)

  RETURN CHAR

   IS
     v_desc_mensaje CHAR(200);
   BEGIN

   SELECT DESC_MENSAJE
          INTO  v_desc_mensaje
          FROM  RES_MENSAJE_ERROR
          WHERE COD_MENSAJE = cCodigoError_in;

    RETURN  v_desc_mensaje;

   END;
 ------------------------------------------------------------------------------------------------------
  FUNCTION RE_F_GET_SERIE_RE(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cTipDoc_in IN CHAR)
  RETURN FarmaCursor
  IS
    curSerie Farmacursor;
  BEGIN
    OPEN curSerie FOR
    SELECT NUM_SERIE_LOCAL|| 'Ã' ||
          NUM_SERIE_LOCAL
    FROM  VTA_SERIE_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND TIP_COMP = cTipDoc_in;

    RETURN curSerie;
  END;
---------------------------------------------------------------------------------------------------------


  FUNCTION RE_F_GET_NUMERO_PEDIDO( cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cTipComp_in     IN CHAR,
                                 cNumComp_in     IN CHAR,
                                 nMontoVta_in    IN NUMBER
                               )

    RETURN CHAR

    IS
    v_cNumPed char(10);

    BEGIN
    begin
    SELECT C.NUM_PED_VTA into v_cNumPed
       FROM   VTA_COMP_PAGO C
       WHERE  C.TIP_COMP_PAGO = cTipComp_in
       AND    C.NUM_COMP_PAGO = cNumComp_in
       AND    C.VAL_NETO_COMP_PAGO + C.VAL_REDONDEO_COMP_PAGO = nMontoVta_in
       AND    C.IND_COMP_ANUL = 'N'
       AND    C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    C.COD_LOCAL = cCodLocal_in;
        exception
        when no_data_found then
        v_cNumPed :=' ';
        end;


       return v_cNumPed;
    END;
/*--------------------------------------------------------------------------------------------------------------*/

FUNCTION RE_F_IS_PERMITE_ANULACION(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cNumPedVta_in   IN CHAR)
RETURN CHAR
IS
    TIEMPO_MAXIMO  VARCHAR2(30);

    CURSOR curProdVirtual IS
           SELECT DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR,
                  NVL(VIR.TIP_PROD_VIRTUAL,' ') TIPO,
                  SYSDATE - CAB.FEC_PED_VTA FECHA
           FROM   VTA_PEDIDO_VTA_DET DET,
                  VTA_PEDIDO_VTA_CAB CAB,
                  LGT_PROD_VIRTUAL VIR
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
           AND    DET.COD_LOCAL = CAB.COD_LOCAL
           AND    DET.NUM_PED_VTA = CAB.NUM_PED_VTA
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+);

    cRepta char(1):= 'S';
BEGIN
    --se obtiene el tiempo maximo para anular un pedido recarga virtual
    --en minutos
    -- se modifico el modo de obtener el tie,pop
    TIEMPO_MAXIMO :=
      Farma_Gral.GET_TIEMPO_MAX_ANULACION(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          cNumPedVta_in);

     FOR v_rCurProdVirtual IN curProdVirtual
         LOOP
         dbms_output.put_line('fecha: '||v_rCurProdVirtual.FECHA);
         dbms_output.put_line('TIEMPO: '||TO_NUMBER(TIEMPO_MAXIMO)/24/60);
           IF v_rCurProdVirtual.FECHA > (TO_NUMBER(TIEMPO_MAXIMO)/24/60) THEN --0.007 INDICA 10 MINUTOS
              --RAISE_APPLICATION_ERROR(-20005,'Este pedido fue cobrado hace mas de ' || TIEMPO_MAXIMO || ' minutos. No se puede anular.');
              cRepta := 'N';
           END IF;
      END LOOP;

   return cRepta;
END;


/*------------------------------------------------------------------------------------------------------------------*/
FUNCTION RE_F_CANT_INT_RECARGA_VIRTUAL
  RETURN CHAR
  IS
  v_cant_intentos CHAR(2);
  BEGIN

  select llave_tab_gral into v_cant_intentos
  from pbl_tab_gral
  where ID_TAB_GRAL='260'
  and est_tab_gral='A';

  RETURN v_cant_intentos;

  END;

/*\*------------------------------------------------------------------------------------------------------------------*\

  FUNCTION  RE_F_IMP_HTML_RECARGA_PEDIDO(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in      IN CHAR,
                                         cNumPedVta_in       IN CHAR,
                                         nMontoVta_in      IN NUMBER,
                                         cRespuestaRecarga IN CHAR,
                                         cComunicado IN CHAR)
   RETURN VARCHAR2 IS

    msgPrincipal VARCHAR2(1000) := cRespuestaRecarga;
    msgFinal VARCHAR2(1000) :=   cComunicado;
    msgHTMLCab VARCHAR2(4000) := '';
    msgHTMLDet VARCHAR2(4000) := '';
    msgHTMLPie VARCHAR2(4000) := '';
    msgHTML    VARCHAR2(4000) := '';



 \*   vSecUsuLocal PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE;
    dFechaVenta  CE_MOV_CAJA.FEC_DIA_VTA%TYPE;*\

    ------------------------------------

    CURSOR curRecargas IS
              SELECT DECODE(TRIM(VIR.COD_PROV_TEL),'A','Movistar','F','Claro','DTV','Direct Tv') proveedor,
            P.DESC_PROD ,
            DET.DESC_NUM_TEL_REC  telefono ,
            CAB.VAL_NETO_PED_VTA Monto,
            TO_CHAR(CAB.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss') Fecha_Venta,
            TO_CHAR(SYSDATE,'dd/MM/yyyy HH24:mi:ss') Fecha_Actual


     FROM   VTA_PEDIDO_VTA_DET DET,
                  VTA_PEDIDO_VTA_CAB CAB,
                  LGT_PROD_VIRTUAL VIR,
                  LGT_PROD P

    WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
           AND    DET.COD_LOCAL = CAB.COD_LOCAL
           AND    DET.NUM_PED_VTA = CAB.NUM_PED_VTA
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+)
           AND    CAB.VAL_NETO_PED_VTA = nMontoVta_in
           AND    P.COD_PROD = VIR.COD_PROD
           AND  VIR.COD_PROD  IN (SELECT COD_PROD
                                 FROM   VTA_PEDIDO_VTA_DET DET
                                 WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
                                 AND    DET.COD_LOCAL = cCodLocal_in
                                 AND    DET.NUM_PED_VTA = cNumPedVta_in);
    rRecargas curRecargas%ROWTYPE;

  BEGIN


\*  '<table width="200" border="0">' ||
                                   '<tr>' || '<td>&nbsp;&nbsp;</td>' ||
                                   '<td>' ||
                                   '<table width="300"  border="1" cellspacing="0" cellpadding="5">';*\


    if (cRespuestaRecarga ='00') then
        msgPrincipal := 'Recarga Exitosa';
    end if;

    --obteniedo la cabecera del mensaje
    msgHTMLCab := C_INICIO_MSG_1 ||
                  '<tr><td align="center" colspan="1" class="cajero">TICKET DE CONSULTA RECARGA VIRTUAL</td></tr> ' ||
                  '<tr><td colspan="1" class="cajero"> Mensaje: '||msgPrincipal||'</td></tr>'||
                  '<tr><td colspan="1" class="cajero"> Nro Comprobante: '|| cNumPedVta_in || '</td></tr>'\* ||
                  '<tr><td colspan="1" class="cajero"> Terminalista: ' || TRIM(vTerminalista) ||'</td></tr>'||
                  '<tr><td colspan="1" class="cajero"> Cajero: ' || TRIM(vCajero) ||'</td></tr>'*\;




    FOR rRecargas IN curRecargas LOOP

      msgHTMLDet := msgHTMLDet || '<tr><td class="cajero">Fecha de Recarga : ' || rRecargas.Fecha_Venta ||'</td></tr>'
                    || '<tr><td class="cajero">Proveedor : ' || trim(rRecargas.Proveedor) || '</td></tr>'
                    || '<tr><td class="cajero">Teléfono : ' || trim(rRecargas.Telefono) || '</td></tr>'
                    || '<tr><td class="cajero">Monto : ' || trim(rRecargas.Monto) || '</td></tr>'
                    || '<tr><td class="cajero">Fecha Actual : ' || trim(rRecargas.Fecha_Actual) || '</td></tr>';
    END LOOP;

   --añadiendo el  mensaje final
    msgHTMLDet := msgHTMLDet || '<tr><td class="cajero">: ' || msgFinal ||'</td></tr>';
    --fin del html
    msgHTMLPie := '</table></td></tr></table></html>';

    msgHTML := trim(msgHTMLCab) || trim(msgHTMLDet) || trim(msgHTMLPie);

    return msgHTML;
  END;  */

  /*------------------------------------------------------------------------------------------------------------------*/

  FUNCTION  RE_F_IMP_HTML_RECARGA_PEDIDO(cNumPedVta_in     IN CHAR,
                                        /* cFechaPedido      IN CHAR,*/
                                        /* cProveedor        IN CHAR\*,*/
                                      /*   cTelefono         IN CHAR,*\*/
                                         nMontoVta_in       IN NUMBER/*,*/
                                       /*  cRespuestaRecarga IN CHAR,*/
                                       /*  cComunicado       IN CHAR */)
   RETURN VARCHAR2 IS
     curdesc       FarmaCursor;
 /*   msgPrincipal VARCHAR2(1000) := '';\*cRespuestaRecarga;*\*/
    msgFinal VARCHAR2(1000) :=  ''; /*cComunicado;*/
    msgHTMLCab VARCHAR2(4000) := '';
    msgHTMLDet VARCHAR2(4500) := '';
    msgHTMLPie VARCHAR2(4000) := '';
    msgHTML    VARCHAR2(4000) := '';


    v_cod_prov_tel  VARCHAR2(4);
    ID_TX           CHAR(12);
    COD_APROBACION  CHAR(6);
    NUM_TELEFONO    VARCHAR2(20);




    v_lineaTiempo_anulacion VARCHAR2(500) := '';

    vIndDirecTV  char(1):= 'N';
    nMontoRecarga number;
    vCodProd char(6);
    vCodGrupoCia char(4);
    vCodLocal char(4);

    DESC_CABECERA   VARCHAR2(500);
    DESC_LINEA_01   VARCHAR2(200) := '';
    DESC_LINEA_02   VARCHAR2(200) := '';

    DESC_BODY  VARCHAR2(1500);

    vNUM_TELEFONO    VARCHAR2(20);
    vDESC_CABECERA   VARCHAR2(700);
    vCOD_APROBACION   CHAR(6);
    v_vcod_prov_tel  VARCHAR2(4);
    vDESC_LINEA_01   VARCHAR2(200) := '';
    vDESC_LINEA_02   VARCHAR2(200) := '';
    vnMontoRecarga number;
    vFechaRecarga  VARCHAR2(100):='';
    vProveedor VARCHAR2(80):='';








  BEGIN

      select cod_grupo_cia,cod_local,cod_prod
      into vCodGrupoCia,vCodLocal,vCodProd
       from   vta_pedido_vta_DET
      where  NUM_PED_VTA=cNumPedVta_in;


       SELECT D.VAL_NUM_TRACE ID_TX, D.VAL_COD_APROBACION APROBACION,  D.DESC_NUM_TEL_REC TELEFONO,
             D.VAL_PREC_TOTAL
      INTO   ID_TX,COD_APROBACION,NUM_TELEFONO,nMontoRecarga
      FROM   VTA_PEDIDO_VTA_DET D
      WHERE  D.COD_GRUPO_CIA = vCodGrupoCia
      AND    D.COD_LOCAL     = vCodLocal
      AND    D.NUM_PED_VTA   = cNumPedVta_in;

      SELECT V.COD_PROV_TEL
      INTO   v_cod_prov_tel
      FROM   LGT_PROD_VIRTUAL V
      WHERE  V.COD_GRUPO_CIA = vCodGrupoCia
      AND    V.COD_PROD      = vCodProd;



       SELECT TO_CHAR(CAB.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss') ,
            DECODE(TRIM(VIR.COD_PROV_TEL),'A','Movistar','F','Claro','DTV','Direct Tv')
              INTO vFechaRecarga , vProveedor

     FROM   VTA_PEDIDO_VTA_DET DET,
                  VTA_PEDIDO_VTA_CAB CAB,
                  LGT_PROD_VIRTUAL VIR,
                  LGT_PROD P

    WHERE  DET.COD_GRUPO_CIA = vCodGrupoCia
           AND    DET.COD_LOCAL = vCodLocal
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
           AND    DET.COD_LOCAL = CAB.COD_LOCAL
           AND    DET.NUM_PED_VTA = CAB.NUM_PED_VTA
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+)
           AND    CAB.VAL_NETO_PED_VTA = nMontoVta_in
           AND    P.COD_PROD = VIR.COD_PROD
           AND  VIR.COD_PROD  IN (SELECT COD_PROD
                                 FROM   VTA_PEDIDO_VTA_DET DET
                                 WHERE  DET.COD_GRUPO_CIA = vCodGrupoCia
                                 AND    DET.COD_LOCAL = vCodLocal
                                 AND    DET.NUM_PED_VTA = cNumPedVta_in);


    v_lineaTiempo_anulacion := Farma_Gral.GET_TIEMPO_MAX_ANULACION(vCodGrupoCia,vCodLocal,cNumPedVta_in);

    if v_lineaTiempo_anulacion = '0' then
       v_lineaTiempo_anulacion:= '  (LA RECARGA NO PUEDE ANULARSE)';
    else
        v_lineaTiempo_anulacion:=
            '  (Tiempo Máximo de Anulación de '||v_lineaTiempo_anulacion|| ' minutos.)';
    end if;

      v_cod_prov_tel := TRIM(v_cod_prov_tel);

         IF (v_cod_prov_tel = C_COD_PROV_TELF_CLARO) THEN --CLARO
        begin
          SELECT G.DESC_LARGA
          INTO   DESC_CABECERA
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '157'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'CLARO_CABECERA'
          ;

          SELECT G.DESC_LARGA
          INTO   DESC_LINEA_01
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '158'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'CLARO_LINEA_01';

          SELECT G.DESC_LARGA
          INTO   DESC_LINEA_02
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '159'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'CLARO_LINEA_02';

        EXCEPTION
      		WHEN NO_DATA_FOUND THEN
    			dbms_output.put_line('no se encontro informacion!!!');
        END;
      ELSIF (v_cod_prov_tel = C_COD_PROV_TELF_MOVISTAR) THEN --MOVISTAR
        BEGIN

          SELECT G.DESC_LARGA
          INTO   DESC_CABECERA
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '160'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'MOVISTAR_CABECERA';

          SELECT G.DESC_LARGA
          INTO   DESC_LINEA_01
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '161'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'MOVISTAR_LINEA_01';


          SELECT G.DESC_LARGA
          INTO   DESC_LINEA_02
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '162'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'MOVISTAR_LINEA_02';

        EXCEPTION
      		WHEN NO_DATA_FOUND THEN
    			dbms_output.put_line('no se encontro informacion!!!');
        END;
      ELSIF (v_cod_prov_tel = C_COD_PROV_DIRECTV) THEN --DIRECTV
        BEGIN
          vIndDirecTV := 'S';


          SELECT G.DESC_LARGA
          INTO   DESC_CABECERA
          FROM   PBL_TAB_GRAL G
          WHERE  G.ID_TAB_GRAL  = '160'
          AND    G.EST_TAB_GRAL = 'A'
          AND    G.COD_APL      = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'MOVISTAR_CABECERA';

          SELECT 'CONFIRMACION: ' || Substrb(DATOS_IMP_VIRTUAL, 10, 15) ||
                 '   SALDO: ' || TO_NUMBER(Substrb(DATOS_IMP_VIRTUAL, 35, 3),'99999')
                 || CASE when TO_NUMBER(Substrb(DATOS_IMP_VIRTUAL, 35, 3),'99999') > 1 THEN ' dias ' else 'dia' end
          INTO   DESC_LINEA_01
          FROM   VTA_PEDIDO_VTA_DET G
          WHERE  G.COD_GRUPO_CIA = vCodGrupoCia
          AND    G.COD_LOCAL = vCodLocal
          AND    G.NUM_PED_VTA = cNumPedVta_in
          AND    G.COD_PROD = vCodProd;

          SELECT 'EL VALOR DE ESTE TICKET NO ES REEMBOLSABLE'
          INTO   DESC_LINEA_02
          from dual;

        EXCEPTION
      		WHEN NO_DATA_FOUND THEN
    			dbms_output.put_line('no se encontro informacion!!!');
        END;
      END IF ;




       if vIndDirecTV = 'N' then

              SELECT
                      DESC_CABECERA || '<BR>'||
                      v_lineaTiempo_anulacion ||  '<BR>'||
                     'Número Recarga:'||NUM_TELEFONO    || '<BR>'||
                     'Fecha  Recarga:'|| vFechaRecarga || '<BR>'||
                     'Proveedor :' || vProveedor || '<BR>'||
                     'Aprobación:' || COD_APROBACION  || '<BR>'||
                     'Monto :'|| 'S/.'|| nMontoRecarga  || '<BR>'||
                     'Nro:' || TRIM(cNumPedVta_in) || '<BR>'||
                      DECODE(v_cod_prov_tel,C_COD_PROV_TELF_MOVISTAR,'Id Tx: ' || ID_TX,' ')  || '<BR>' ||
                      DESC_LINEA_01 || '<BR>'||
                      DESC_LINEA_02
                      into DESC_BODY
                      FROM DUAL;




          msgHTMLDet := msgHTMLDet || '<tr><td class="cajero">' || trim(DESC_BODY) || '.' ||'</td></tr>';
         elsif  vIndDirecTV = 'S' then

              SELECT DESC_CABECERA ||  '<BR>'||
                     v_lineaTiempo_anulacion ||'<BR>'||
                     'Tarjeta:'||NUM_TELEFONO    || '<BR>'||
                     'Fecha  Recarga:'|| vFechaRecarga || '<BR>'||
                     'Proveedor :' || vProveedor || '<BR>'||
                     'Monto :'|| 'S/.'|| nMontoRecarga  || '<BR>'||
                     'Nro:'|| TRIM(cNumPedVta_in) || '<BR>'||
                      DESC_LINEA_01 || '<BR>'||
                      DESC_LINEA_02
                      into DESC_BODY
                      FROM DUAL;

                msgHTMLDet := msgHTMLDet || '<tr><td class="cajero">' || trim(DESC_BODY) ||'</td></tr>';
         end if;


    --obteniedo la cabecera del mensaje
    msgHTMLCab := C_INICIO_MSG_1 ||
                  '<tr><td align="center" colspan="1" class="cajero">CONSULTA RECARGA VIRTUAL</td></tr>';


   --añadiendo el  fecha de impresion
    msgHTMLDet := msgHTMLDet  || '<tr><td align="center" class="histcab">' || trim(TO_CHAR(SYSDATE,'dd/MM/yyyy HH24:mi:ss')) || '</td>

                                  </tr>';


    --fin del html
    msgHTMLPie := '</table></td></tr></table></html>';

    msgHTML := trim(msgHTMLCab) || trim(msgHTMLDet) || trim(msgHTMLPie);

 	  dbms_output.put_line(msgHTML);

    return msgHTML;
  END;
/*------------------------------------------------------------------------------------------------------------------*/

/************************************************************************************************************/

FUNCTION REC_F_GET_MSG(cCodMsg_in IN CHAR)
RETURN VARCHAR2
IS
descmsg VARCHAR2(600);
indlabel CHAR(1);
cont NUMBER:=1;
indice NUMBER;
cantmin VARCHAR2(500);
flag BOOLEAN:=TRUE;
mensaje VARCHAR(400);
descmsg02 VARCHAR(600):='';
inicio NUMBER:=1;
cadena VARCHAR(400):='';
cond2 NUMBER:=1;
indice2 NUMBER;
longitud NUMBER:=0;
BEGIN
     SELECT a.desc_mensaje, a.ind_label INTO descmsg, indlabel
     FROM res_mensaje_error a
     WHERE a.cod_mensaje=cCodMsg_in;

     IF indlabel='S' THEN
        descmsg:='<table><tr><td><b><font face=Arial color=red>'||descmsg||'</font></b></td></tr></table>';
     ELSIF indlabel='N' THEN
        IF instr(descmsg,'Ã',1,1)=0 THEN
           IF length(descmsg)>80 THEN
              cadena:=TRIM(substr(descmsg,1,(instr(descmsg,' ',1,1))));
              descmsg02:=TRIM(substr(descmsg,1,(instr(descmsg,' ',1,1))));
              WHILE flag=TRUE LOOP
                    IF instr(descmsg,' ',inicio,cont)>0 THEN
                       indice2:=instr(descmsg,' ',inicio,cont);
                    END IF;
                    indice:=instr(descmsg,' ',inicio,cont);
                    dbms_output.put_line('indice: '||indice);
                    mensaje:=TRIM(substr(descmsg,indice,(instr(descmsg,' ',inicio,cont+1)-indice)));
                    dbms_output.put_line('mensaje: '||mensaje);
                    dbms_output.put_line('descmsg02: '||descmsg02);
                    dbms_output.put_line('cadena: '||cadena);
                    dbms_output.put_line('----------------------------');
                    IF (length(cadena)+length('d'||mensaje))>80 THEN
                       descmsg02:=descmsg02||'ººº'||mensaje;
                       longitud:=1;
                       cadena:='';
                    ELSE
                        descmsg02:=descmsg02||' '||mensaje;
                        IF longitud=1 THEN
                           cadena:=TRIM(cadena||' '||mensaje);
                        ELSE
                            cadena:=descmsg02;
                        END IF;
                    END IF;
                    cont:=cont+1;
                    IF indice=0 THEN
                       flag:=FALSE;
                    END IF;
              END LOOP;
              descmsg:=/*TRIM(substr(descmsg,1,(instr(descmsg,' ',1,1))))||' '||*/TRIM(descmsg02)||' '||TRIM(substr(descmsg,indice2));
           END IF;
        ELSE
           descmsg := REPLACE(descmsg,'Ã','\n');
        END IF;
     END IF;

     IF cCodMsg_in = '00' THEN
        SELECT b.llave_tab_gral INTO cantmin
        FROM pbl_tab_gral b
        WHERE b.id_tab_gral='163';
        descmsg:=REPLACE(descmsg,'XYZ',cantmin);
     END IF;

     RETURN '<html>'||descmsg||'</html>';
END;

/************************************************************************************************************/

FUNCTION REC_F_GET_IND_LABEL(cCodMsg_in IN CHAR)
RETURN CHAR
IS
ind CHAR(1);
BEGIN
      SELECT c.ind_label INTO ind
      FROM res_mensaje_error c
      WHERE c.cod_mensaje=cCodMsg_in;
      RETURN ind;
END;


END;
/

