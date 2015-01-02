--------------------------------------------------------
--  DDL for Package PTOVENTA_RECARGA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_RECARGA" AS
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
