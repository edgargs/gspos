--------------------------------------------------------
--  DDL for Package PTOVENTA_CE_SEGURIDAD
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CE_SEGURIDAD" AS

  TYPE FarmaCursor IS REF CURSOR;
  COD_NUM_SEC_SOBRE PBL_NUMERA.COD_NUMERA%TYPE := '062';

  C_INICIO_MSG_1 VARCHAR2(1000) := '<html><head><style type="text/css">' ||
                                   '.titulo {font-size: 10;font-family:sans-serif;font-style: italic;}' ||
                                   '.cajero {font-size: 20;font-family: Arial, Helvetica, sans-serif;border-style: solid;} ' ||
                                   '.histcab {font-size: 10;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.historico{font-size: 10;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.msgfinal {font-size: 10;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.tip{font-size: 10;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.fila{border-style: solid;}' ||
                                   '</style>' || '</head>' || '<body>' ||
                                   '<table width="200" border="0">' ||
                                   '<tr>' || '<td>&nbsp;&nbsp;</td>' ||
                                   '<td>' ||
                                   '<table width="300"  border="1" cellspacing="0" cellpadding="5">';

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
                                    ' <body>' ||
                                    ' <table width="510"border="0">' ||
                                    ' <tr>' ||
                                    ' <td width="487" align="center" valign="top"><h1>REMITO</h1></td>' ||
                                    ' </tr>' ||
                                    ' </table>' ||
                                    ' <table width="504" border="1">' ||
                                    ' <tr>' ||
                                    ' <td height="43" colspan="3"><h2>Deposito En Soles (CTA. CTE. N&deg;)</h2></td>' ||
                                    ' <td colspan="3"><h2>Deposito En Dolares (CTA. CTE. N&deg;)</h2> </td>' ||
                                    ' </tr>' ||
                                    ' <tr>' ||
                                    ' <td width="78" height="61"><strong>FECHA</strong></td>' ||
                                    ' <td width="50"><strong>N&deg; SOBRES </strong></td>' ||
                                    ' <td width="110"><strong>MONTO  S/.</strong></td>' ||
                                    ' <td width="78"><strong>FECHA</strong></td>' ||
                                    ' <td width="50"><strong>N&deg; SOBRES</strong></td>' ||
                                    ' <td width="110"><strong>MONTO  US$</strong></td>' ||
                                    ' </tr>';

  C_LINEA VARCHAR2(20000) := '<tr>' ||
                                ' <td height="25">&nbsp;</td>' ||
                                ' <td>&nbsp;</td>' ||
                                ' <td>&nbsp;</td>' ||
                                ' <td>&nbsp;</td>' ||
                                ' <td>&nbsp;</td>' ||
                                ' <td>&nbsp;</td>' ||
                               '</tr>';

  C_MSG_MED VARCHAR2(2000) := '<td></td>
                                 </tr>
                                 <tr>
                                 <td></td>
                                 </tr>
                                </tr>
                                </table>
                                <table width="570" height="450" border="0">
                                  <tr>
                                    <td width="4" >&nbsp;</td>
                                    <td width="4" >&nbsp;</td>
                                    <td>
                                ';

  C_FIN_MSG VARCHAR2(2000) := '</td>' ||
                                  '</tr>' ||
                                  '</table>' ||
                                  '</body>' ||
                                  '</html>';

  COD_NUM_SEC_SOBRE PBL_NUMERA.COD_NUMERA%TYPE := '062';

  ACC_INGRESO  CHAR(1) := 'I';
  ACC_MODIFICA CHAR(1) := 'M';
  ACC_ELIMINA  CHAR(1) := 'E';
  ACC_APRUEBA  CHAR(1) := 'A';

  ESTADO_PENDIENTE CE_SOBRE.ESTADO%TYPE := 'P';
  ESTADO_APROBADO  CE_SOBRE.ESTADO%TYPE := 'A';
  ESTADO_INACTIVO  CE_SOBRE.ESTADO%TYPE := 'I';


  COD_MONEDA_SOLES CHAR(1):= 'S';
  DES_MONEDA_SOLES CHAR(8):= 'SOLES';
  COD_MONEDA_DOLARES CHAR(1):= 'D';
  DES_MONEDA_DOLARES CHAR(8):= 'DOLARES';
  COD_EFECTIVO_SOLES CHAR(5):='00001';
  COD_EFECTIVO_DOLARES CHAR(5):='00002';


  FUNCTION SEG_F_CHAR_IND_SEGUR_LOCAL(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cCodFormPago_in IN CHAR) RETURN CHAR;

  PROCEDURE SEG_P_VALIDA_SOBRE(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodFormPago_in IN CHAR,
                               cSecCaja_in     IN CHAR);

  PROCEDURE SEG_P_INSERT_SOBRE(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in     IN CHAR,
                               cSecCaja_in      IN CHAR,
                               cSecFormaPago_in IN CHAR,
                               cIdUsuario_in    IN CHAR,
                               cCodSobre_in     IN CHAR);

  PROCEDURE SEG_P_ELIMINA_SOBRE(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cCodSobre_in    IN CHAR,
                                cCodUser        IN CHAR);

  FUNCTION SEG_F_VAR_IMP_HTML_SOBRES(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cMovCaja_in     IN CHAR,
                                     cCodSobre_in    IN CHAR) return varchar2;

  FUNCTION SEG_F_CUR_GET_SOBRES(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cMovCaja_in     IN CHAR) RETURN FarmaCursor;

  FUNCTION SEG_F_CUR_REMITOS(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             FechaIni        IN CHAR,
                             FechaFin        IN CHAR) RETURN FarmaCursor;

  FUNCTION SEG_F_CUR_FEC_REMITO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cCodRemito      IN CHAR) RETURN FarmaCursor;

  FUNCTION SEG_F_CUR_DIA_SIN_REMITO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
    RETURN FarmaCursor;

  PROCEDURE SEG_P_AGREGA_REMITO(cCodGrupoCia_in IN CHAR,
                                cCodLocal       IN CHAR,
                                cIdUsu_in       IN CHAR,
                                cNumRemito      IN CHAR,
                                cFecha          IN CHAR);

  FUNCTION SEG_F_CUR_GET_SOBRE_FECHA(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cFecha          IN CHAR) RETURN FarmaCursor;

  FUNCTION SEG_F_CUR_SOBRE_FECHA(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cFecha          IN CHAR) RETURN FarmaCursor;

 FUNCTION SEG_F_CUR_SOBRE_FECHA_DET(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cFecha          IN CHAR) RETURN FarmaCursor;

 FUNCTION SEG_F_CUR_SOBRE_REMITO(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cCodigoRemito   IN CHAR)

 RETURN FarmaCursor;

  FUNCTION SEG_F_VAR2_EXISTE_REMITO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumRemito      IN CHAR) RETURN VARCHAR2;

  FUNCTION SEG_F_VAR2_IMP_DATOS_VOUCHER(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodRemito      IN CHAR,
                                        cIpServ_in      IN CHAR)
    RETURN VARCHAR2;

  FUNCTION SEG_F_CUR_GET_DATA4(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR) RETURN FarmaCursor;

  FUNCTION SEG_F_CUR_GET_DATA3(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR) RETURN FarmaCursor;

  FUNCTION SEG_F_CHAR_IND_PROSEGUR(
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR
                                  ) RETURN CHAR;
  --OBTENER INDICADOR DE SI PUEDE O NO MODIFICAR EL LA FORMA DE DECLARAR EN CIERRE DE CAJA
  --JCALLO 02/02/2008
  FUNCTION SEG_F_CHAR_IND_CHCB_SOBRE
  RETURN CHAR;

  --OBTENER CANTIDAD DE INTENTOS DE MODIFICACION
  --JCALLO 02/02/2008
  FUNCTION SEG_F_CHAR_CANT_MOD_SOBRE
  RETURN CHAR;

  FUNCTION SEG_F_CHAR_SOBRES_ELI(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cFechaCierreDia IN CHAR,
                                 cSecMovCaja     IN CHAR)
  RETURN CHAR;

  --ENVIAR CORREO DE ALERTA
  --JCALLO 02/02/2008
  PROCEDURE SEG_P_ENVIAR_CORREO_ALERTA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in          IN CHAR,
                                 cFechaCierreDia       IN CHAR,
                                 cSecMovCaja           IN CHAR,
                                 cSecUsuLocal_in       IN CHAR,
                                 cTipMensaje_in        IN CHAR,
                                 cCodSobre_in          IN CHAR);

   /*********************************CAMBIO DE INGRESO DE SOBRES*************************************************************/

  --Descripcion: Se valida la asociacion del sobre con un remito
  --Fecha       Usuario		 Comentario
  --30/03/2010  JCORTEZ   Creación
   FUNCTION SEG_F_VALIDA_SOBRE_REMITO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cFechaCierreDia IN CHAR,
                                      cSecMovCaja     IN CHAR,
                                      cCodSobre       IN CHAR)
   RETURN CHAR;

   --Descripcion: Se listan los sobres para el nuevo remito
   --Fecha       Usuario		 Comentario
   --08/04/2010  JCORTEZ   Creación
   FUNCTION SEG_F_CUR_GET_SOBRES_APROBAR(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cFecIni_in      IN CHAR,
                                         cFecFin_in      IN CHAR)
   RETURN FarmaCursor;

    --Descripcion: Se valida el estado del sobre
    --Fecha       Usuario		 Comentario
    --08/04/2010  JCORTEZ   Creación
    PROCEDURE  SEG_P_VALIDA_EST_SOBRE(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cFecVta_in      IN CHAR,
                                      cCodSobre_in    IN CHAR);

    --Descripcion: Se aprueba el sobre seleccionado
    --Fecha       Usuario		 Comentario
    --08/04/2010  JCORTEZ   Creación
    PROCEDURE SEG_P_VALIDA_APRUEBA_SOBRE(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cIdUsu_in         IN CHAR,
                                         cFecVta_in      IN CHAR,
                                         cCodSobre_in    IN CHAR,
                                         cSecUsuQF  IN CHAR);

    --Descripcion: Se valida el rol de usuario que debe aprobar los sobres.
    --Fecha       Usuario		 Comentario
    --08/04/2010  JCORTEZ   Creación
   FUNCTION SEG_F_VERIFICA_ROL_USU(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   vSecUsu_in       IN CHAR,
                                   cCodRol_in       IN CHAR)
    RETURN CHAR;

    --Descripcion: Si el indicador de concepto de sobres esta en 'S' y la forma de pago puede ponerse en sobres entonces devuelve 'S' sino 'N'
    --Fecha       Usuario		 Comentario
    --31/05/2010  ASOSA   Creación
    FUNCTION SEG_F_CHAR_IND_SOBRES(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cCodFormPago_in IN CHAR)
    RETURN CHAR;



  FUNCTION SEG_F_ACCION_SOBRE_TMP(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cSec_in         IN CHAR,
                               cSecMovCaja_in  IN CHAR,
                               cIdUsu_in       IN CHAR,
                               cCodSobre_in    IN CHAR,

                               cCodFormaPago_in    IN CHAR,
                               cTipMoneda_in       IN CHAR,
                               cMonEntrega_in      IN NUMBER,
                               cMonEntregaTotal_in IN NUMBER,

                               cTipoAccion_in  IN CHAR)
  RETURN CHAR;

  FUNCTION SEG_F_BLOQUEO_ESTADO(cCodGrupoCia_in  IN CHAR,
                                cCodLocal_in     IN CHAR,
                                cSec_in          IN CHAR,
                                cSecMovCaja_in   IN CHAR,
                                cCodSobre_in     IN CHAR,
                                cTipoSobre_in    IN CHAR)
  RETURN CHAR;

  FUNCTION CAJ_F_OBTIENE_SECSOBRE(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in   IN CHAR,
                                        cSecMovCaja_in  IN CHAR,
                                        cFecDiaVta_in   IN DATE)
  RETURN NUMBER;

 FUNCTION CAJ_F_OBTIENE_SEC_MOV_CAJA(cCodGrupoCia_in IN CHAR,
                                 cCod_Local_in   IN CHAR,
                                 cSecUsuLocal_in  IN CHAR)
 return char;

 FUNCTION  SEG_F_GET_TIPO_CAMBIO(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cSecUsuLocal_in  IN CHAR)
 RETURN NUMBER;

   function SEG_F_CUR_DET_EFECTIVO_TURNO(cCodGrupoCia_in in char,
                                      cCodLocal_in in char,
                                      cCodTurno_in in char,
                                      cCodCaja_in in char,
                                      cFecha_in in char,
                                      cTipoEfectivo_in in char
                                     )
  return FarmaCursor;

  function SEG_F_CUR_DET_EFECTIVO_DIA(cCodGrupoCia_in in char,
                                      cCodLocal_in in char,
                                      cFecha_in in char,
                                      cTipoEfectivo_in in char
                                     )
  return FarmaCursor;

END;

/
