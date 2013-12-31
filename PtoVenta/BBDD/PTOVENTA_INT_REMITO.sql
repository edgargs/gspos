--------------------------------------------------------
--  DDL for Package PTOVENTA_INT_REMITO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_INT_REMITO" is

  ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';
	POS_INICIO		      CHAR(1):='I';

  TIP_MONEDA_SOLES CHAR(2) := '01';
  TIP_MONEDA_DOLARES CHAR(2) := '02';

  INDICADOR_ERROR CHAR(1) := '1';
  INDICADOR_CORRECTO CHAR(1) := '0';

  TIP_MONEDA_SOLES_SAP CHAR(3) := 'PEN';
  TIP_MONEDA_DOLARES_SAP CHAR(3) := 'USD';

  CLASE_DOC_SA CHAR(2) := 'SA';
  CLASE_DOC_DA CHAR(2) := 'DA';
  CLASE_DOC_KO CHAR(2) := 'KO';

  IND_IMPUESTO_S3 CHAR(2) := 'S3';
  IND_IMPUESTO_S0 CHAR(2) := 'S0';

  CENTRO_COSTO_CC CHAR(2) := 'CC';

  IND_CODIGO_DEPOSITO CHAR(2) := 'OP';

  NOM_TITULAR_MF CHAR(7) := 'MIFARMA';

  C_C_USU_CREA_INT_CE CHAR(8) := 'SISTEMAS';

  C_C_RT CHAR(2) := 'RT';

  C_C_MF CHAR(2) := 'MF';

  C_C_ERC CHAR(3) := 'ERC';

  TIP_MOV_CIERRE  	   CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='C';



  v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;

  /*********************************************************/

  --11/12/2007 dubilluz modificacion
  PROCEDURE INT_GENERA_ARCHIVO(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in     IN CHAR,
                               cRemito_in       IN CHAR,
                               cIndEnviaAdjunto in char default 'N'
                               );

  PROCEDURE INT_EJECT_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cIndEnviaAdjunto in char default 'N');


  PROCEDURE INT_GRABA_REMITO(cCodGrupoCia_in    IN CHAR,
                                   cCodLocal_in       IN CHAR,
                                   vCodRemito_in      IN CHAR,
                                   vFecOperacion_in   IN CHAR,
                                   cCodCuadratura_in  IN CHAR,
                                   cSecIntCe_in       IN CHAR,
                                   vClaseDoc_in       IN CHAR,
                                   cFecDocumento_in   IN CHAR,
                                   cFecContable_in    IN CHAR,
                                   vDescReferencia_in IN CHAR,
                                   cDescTextCab_in    IN CHAR,
                                   cTipMoneda_in      IN CHAR,
                                   vClaveCue1_in      IN CHAR,
                                   vCuenta1_in        IN CHAR,
                                   cMarcaImp_in       IN CHAR,
                                   cValImporte_in     IN CHAR,
                                   vDescAsig1_in      IN CHAR,
                                   cDescTextDet1_in   IN CHAR,
                                   cClaveCue2_in      IN CHAR,
                                   vCuenta2_in        IN CHAR,
                                   vCentroCosto_in    IN CHAR,
                                   cIndImp_in         IN CHAR,
                                   cDescTextDet2_in   IN CHAR,
                                   cUsuCreaIntCe_in   IN CHAR,
                                   CME_in             IN CHAR);

  --OBTIENE EL SECUENCIAL PARA EL CAMPO CONTADOR
  FUNCTION CE_GET_SECUENCIAL_INT(cCodGrupoCia_in      IN CHAR,
                                 cCodLocal_in         IN CHAR,
                                 vFecCierreDia_in     IN CHAR)
    RETURN CHAR;

   FUNCTION CE_VALIDA_DATA_INTERFACE(cCodRemito       IN CHAR,
                                    cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR)
    RETURN CHAR;

  PROCEDURE CE_ACTUALIZA_FECHA_PROCESO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cCodRemito_in    IN CHAR,
                                       vFecProceso_in   IN DATE);

  PROCEDURE CE_ACTUALIZA_FECHA_ARCHIVO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cCodRemito_in    IN CHAR,
                                       vFecArchivo_in   IN DATE);

  --11/12/2007  dubilluz  modificacion
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        vEnviarOper_in   IN CHAR DEFAULT 'N');

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

PROCEDURE RECEP_P_ENVIA_CORREO_ADJUNTO(vAsunto_in        IN CHAR,
                                     vTitulo_in        IN CHAR,
                                     vMensaje_in       IN CHAR,
                                     vNombre_Archivo_in IN VARCHAR2 DEFAULT null
                                     );

end PTOVENTA_INT_REMITO;

/
