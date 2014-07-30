--------------------------------------------------------
--  DDL for Package PTOVENTA_ENV_ALERTAS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_ENV_ALERTAS" AS

  TYPE FarmaCursor IS REF CURSOR;

  v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;

    ----------------
    PROCEDURE ENVIA_CORREO(cCodGrupoCia_in       IN CHAR,
                         cCodLocal_in          IN CHAR,
                         vReceiverAddress_in   IN CHAR,
                         vAsunto_in            IN CHAR,
                         vTitulo_in            IN CHAR,
                         vMensaje_in           IN CHAR,
                         vCCReceiverAddress_in IN CHAR);

    PROCEDURE ENVIA_CORREO_ATTACH3(cSendorAddress_in in char,
                                cReceiverAddress_in in char,
                                cSubject_in in varchar2,
                                ctitulo_in in varchar2,
                                cmensaje_in in varchar2,
                                pDirectorio IN VARCHAR2,
                                pfilename IN VARCHAR2,
                                cCCReceiverAddress_in in char,
                                cip_servidor in char);

    PROCEDURE attach_report3(conn IN OUT NOCOPY utl_smtp.connection,
                             mime_type    IN VARCHAR2 DEFAULT 'text/plain',
                             inline       IN BOOLEAN  DEFAULT TRUE,
                             directory IN VARCHAR2 DEFAULT NULL,
                             filename     IN VARCHAR2 DEFAULT NULL,
                  		        last         IN BOOLEAN  DEFAULT FALSE);

-- 2009-04-16 JOLIVA: Reporte de OCs modificadas
  PROCEDURE ALERTA_PORC_VTA_FID_VEND;

END;

/
