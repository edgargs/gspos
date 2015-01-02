--------------------------------------------------------
--  DDL for Package PTOVENTA_RECEP_CIEGA_JOB
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_RECEP_CIEGA_JOB" is

  -- Author  : JCHAVEZ
  -- Created : 16/11/2009 06:29:33 p.m.
  -- Purpose :

  TYPE FarmaCursor IS REF CURSOR;
  V_CORREO_LOCAL VARCHAR2(300) := '';
  COD_NUMERA_SEC_KARDEX      PBL_NUMERA.COD_NUMERA%TYPE := '016';
  g_cMotKardexIngMatriz      LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '101';
  g_cTipDocKdxGuiaES         LGT_KARDEX.Tip_Comp_Pago%TYPE := '02';
  g_cTipCompNumEntrega       LGT_KARDEX.Tip_Comp_Pago%TYPE := '05';
  g_cTipoMotKardexAjusteGuia LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '008';
   v_gNombreDiretorioAlert VARCHAR2(50) := 'DIR_INTERFACES';--'DIR_REPORTES';
     g_cTipoOrigenLocal CHAR(2):= '01';
  g_cTipCompGuia LGT_KARDEX.Tip_Comp_Pago%TYPE := '03';
    g_cTipoOrigenMatriz CHAR(2):= '02';
      g_cTipoOrigenProveedor CHAR(2):= '03';
        g_cTipoOrigenCompetencia CHAR(2):= '04';
  C_INICIO_MSG VARCHAR2(20000) := '<html>'  ||
                                      '<head>'  ||
                                      '<style type="text/css">'  ||
                                      '.style3 {font-family: Arial, Helvetica, sans-serif}'  ||
                                      '.style8 {font-size: 24; }'  ||
                                      '.style9 {font-size: larger}'  ||
                                      '.style12 {'  ||
                                      'font-family: Arial, Helvetica, sans-serif;'  ||
                                      'font-size: larger;'  ||
                                      'font-weight: bold;'  ||
                                      '}'  ||
                                      '</style>'  ||
                                      '</head>'  ||
                                      '<body>'  ||
                                      '<table width="510"border="0">'  ||
                                      '<tr>'  ||
                                      '<td width="487" align="center" valign="top"><h1>DIFERENCIAS</h1></td>'  ||
                                      '</tr>'  ||
                                      '</table>'  ||
                                      '<table width="504" border="1">'  ||
                                      '<tr>'  ||
                                      '<td height="43" align="center" colspan="1"><h2>Producto </h2></td>'  ||
                                      '<td colspan="1" align="center" ><h2>Laboratorio</h2> </td>'  ||
                                      '</tr>';

  C_FIN_MSG VARCHAR2(2000) := '</td>' ||
                                  '</tr>' ||
                                  '</table>' ||
                                  '</body>' ||
                                  '</html>';
   ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;

  PROCEDURE ENVIA_CORREO_ATTACH3(cSendorAddress_in in char,
                                cReceiverAddress_in in char,
                                cSubject_in in varchar2,
                                ctitulo_in in varchar2,
                                cmensaje_in in varchar2,
                                pDirectorio IN VARCHAR2,
                                pfilename IN VARCHAR2,
                                cCCReceiverAddress_in in char,
                                cip_servidor in char);

PROCEDURE attach_report3(conn         IN OUT NOCOPY utl_smtp.connection,
			mime_type    IN VARCHAR2 DEFAULT 'text/plain',
			inline       IN BOOLEAN  DEFAULT TRUE,
      directory IN VARCHAR2 DEFAULT NULL,
			filename     IN VARCHAR2 DEFAULT NULL,
		        last         IN BOOLEAN  DEFAULT FALSE);

--JMIRANDA 02.02.10
  PROCEDURE RECEP_P_ENVIA_DIFE_TIEMPO(cCodGrupoCia_in IN CHAR);
      						  			   --cCod_Local_in   IN CHAR);

    PROCEDURE RECEP_P_ENVIA_CORREO_DIFE(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR);
  PROCEDURE RECEP_P_ENVIA_CORREO_ADJUNTO(vAsunto_in        IN CHAR,
                                     vTitulo_in        IN CHAR,
                                     vMensaje_in       IN CHAR,
                                     vInd_Archivo_in   IN CHAR DEFAULT 'N',
                                     vNombre_Archivo_in IN VARCHAR2 DEFAULT null
                                     );
----------------
FUNCTION RECEP_F_CHAR_IND_TIENE_DATA(cGrupoCia_in  CHAR,
                                     cCodLocal_in CHAR,
                                     cNroRecepcion CHAR)
                                     RETURN CHAR;

  --Descripcion: obtiene data de recpciones de locales
  --Fecha       Usuario	  Comentario
  --07/05/2010  JCORTEZ     Creación
  PROCEDURE RECEP_P_ENVIA_CORREO_DIFE_2(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR);

  --Descripcion: obtiene data de recpciones de locales
  --Fecha       Usuario	  Comentario
  --17/08/2010  JMIRANDA     Creación
  FUNCTION RECEP_F_EMAIL_LOCAL(cCod_Grupo_Cia_in IN CHAR, cCod_Local_in IN CHAR) RETURN VARCHAR2;

end;

/
