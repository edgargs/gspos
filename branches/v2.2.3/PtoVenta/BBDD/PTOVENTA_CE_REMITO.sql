--------------------------------------------------------
--  DDL for Package PTOVENTA_CE_REMITO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CE_REMITO" AS

  TYPE FarmaCursor IS REF CURSOR;

C_INICIO_MSG_2 VARCHAR2(20000) := ' <html>' ||
                                    ' <head>' ||
                                    ' <style type="text/css">' ||
                                    ' .style3 {font-family: Arial, Helvetica, sans-serif}' ||
                                    ' .style8 {font-size: 24; }' ||
                                    ' .style9 {font-size: larger}' ||
                                    ' .style12 {' ||
                                    ' font-family: Arial, Helvetica, sans-serif;' ||
                                    ' font-size: larger;' ||
                                    ' font-weight: bold;' ||
                                    ' }' ||
                                    ' </style>' ||
                                    ' </head>' ||
                                    ' <body>' ;


    C_FIN_MSG VARCHAR2(2000) := '</td>' ||
                                  '</tr>' ||
                                  '</table>' ||
                                  '</body>' ||
                                  '</html>';
    C_INI_NODEBENIR VARCHAR2(2000) := '<TR><TD COLSPAN=6 ALIGN=CENTER><h3>Días con sobres que no se deben añadir a este remito</h3></TD></TR>';

    C_DEPOSITO_SD VARCHAR2(2000):=' <tr>' ||
                                    ' <td height="43" colspan="3"><h2>Deposito En Soles (CTA. CTE. N&deg;)</h2></td>' ||
                                    ' <td colspan="3"><h2>Deposito En Dolares (CTA. CTE. N&deg;)</h2> </td>' ||
                                    ' </tr>';

  g_cNumPed PBL_NUMERA.COD_NUMERA%TYPE := '007';
  g_cNumPedDia PBL_NUMERA.COD_NUMERA%TYPE := '009';
  g_cCodMotKardex LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '106';
  g_cFormPagoEfectivo VTA_FORMA_PAGO_PEDIDO.COD_FORMA_PAGO%TYPE := '00001';
  g_cFormPagoDolares VTA_FORMA_PAGO_PEDIDO.COD_FORMA_PAGO%TYPE := '00002';

  g_nDiasPermitidosIntercambio INTEGER := 1;

  TIPO_COMPROBANTE_99		  CHAR(2):='99';

  INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';

  TIPO_PROD_VIRTUAL_TARJETA CHAR(1):='T';
  TIPO_PROD_VIRTUAL_RECARGA CHAR(1):='R';

  EST_PED_PENDIENTE  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='P';
	EST_PED_ANULADO  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='N';
	EST_PED_COBRADO  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='C';
	EST_PED_COB_NO_IMPR  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='S';

  COD_CIA_MIFARMA CHAR(3):='001';
  TBL_GRAL_CTA_DOLARES INTEGER := 382;
  TBL_GRAL_CTA_SOLES INTEGER := 383;
  
 FUNCTION SEG_F_CUR_SOBRE_REMITO_DU(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cCodigoRemito   IN CHAR)
 RETURN FarmaCursor;

 FUNCTION SEG_F_CUR_REMITOS_DU(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             FechaIni        IN CHAR,
                             FechaFin        IN CHAR) RETURN FarmaCursor;


PROCEDURE CE_P_AGREGA_REMITO_DU(cCodGrupoCia_in IN CHAR,
                                cCodLocal       IN CHAR,
                                cIdUsu_in       IN CHAR,
                                cNumRemito      IN CHAR,
                                cFecha          IN CHAR,
                                cCodSobre       IN CHAR,
                                cPrecinto       IN CHAR DEFAULT '-'
                                );

PROCEDURE CE_P_SAVE_HIST_REMI(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cCodRemito      IN CHAR,
                             vUsu_in IN VARCHAR2);

FUNCTION SEG_F_CUR_GET_DATA4(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR) RETURN FarmaCursor ;

  FUNCTION SEG_F_CUR_GET_DATA3(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR) RETURN FarmaCursor;


FUNCTION CE_F_LIST_SOBRE_NOREMI_SD(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR) RETURN FarmaCursor;

FUNCTION CE_F_LISTA_TOTALES_SD(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                vUsu_in IN VARCHAR2) RETURN FarmaCursor;
  FUNCTION CE_F_HTML_VOUCHER_REMITO_DU(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodRemito      IN CHAR,
                                        cIpServ_in      IN CHAR)
    RETURN VARCHAR2;

--Descripcion: Cambia el nro de remito de un determinado remito
--Fecha       Usuario		 Comentario
--09/08/2010  ASOSA      Creación
  PROCEDURE CE_P_CAMBIAR_COD_REMITO(cCodCia_in IN CHAR,
                                   cCodLoca_in IN CHAR,
                                   cCodRemitoNew_in IN VARCHAR2,
                                   cCodRemitoOld_in IN VARCHAR2,
                                   cCodPrecinto_in IN VARCHAR2);

    FUNCTION GET_DAT_RMT_MATRICIAL(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cCodRemito      IN CHAR) RETURN VARCHAR2;


  FUNCTION GET_IND_IMPR_MATRI(cCodGrupoCia_in IN CHAR) RETURN VARCHAR2;

END;

/
