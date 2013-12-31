--------------------------------------------------------
--  DDL for Package FARMA_EMAIL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."FARMA_EMAIL" is

  -- Author  : JLUNA
  -- Created : 05/06/2006 11:55:16 a.m.
  -- Purpose : enviar emails

  -- Public type declarations
procedure envia_correo(cSendorAddress_in in char,
                            cReceiverAddress_in in char,
                            cSubject_in in varchar2,
                            ctitulo_in in varchar2,
                            cmensaje_in in varchar2,
                            cCCReceiverAddress_in in char default null,
                            --cip_servidor in char default '192.168.0.236',
                            cip_servidor in char DEFAULT '10.18.0.16',
                            cin_html boolean default false);

  --Descripcion: Envia mail con attachment.
  --Fecha       Usuario		Comentario
  --14/06/2006  ERIOS    	Creaci贸n
  PROCEDURE ENVIA_CORREO_ATTACH(cSendorAddress_in in char,
                                cReceiverAddress_in in char,
                                cSubject_in in varchar2,
                                ctitulo_in in varchar2,
                                cmensaje_in in varchar2,
                                pfilename IN VARCHAR2,
                                i IN NUMBER,
                                cCCReceiverAddress_in in char default null,
                                cip_servidor in char default '192.168.0.236');

  --Descripcion: Adjunta reporte html.
  --Fecha       Usuario		Comentario
  --15/06/2006  ERIOS    	Creaci贸n
  PROCEDURE attach_report(conn         IN OUT NOCOPY utl_smtp.connection,
			mime_type    IN VARCHAR2 DEFAULT 'text/plain',
			inline       IN BOOLEAN  DEFAULT TRUE,
			filename     IN VARCHAR2 DEFAULT NULL,
		        last         IN BOOLEAN  DEFAULT FALSE);

  --Descripcion: Obtiene la direcccion con que se envia.
  --Fecha       Usuario		Comentario
  --11/07/2006  ERIOS    	Creaci每鲁n
  FUNCTION GET_SENDDOR_ADDRESS RETURN VARCHAR2;

  --Descripcion: Obtiene la direcccion ip del servidor se correo.
  --Fecha       Usuario		Comentario
  --11/07/2006  ERIOS    	Creaci贸n
  FUNCTION GET_EMAIL_SERVER RETURN VARCHAR2;

  --Descripcion: Obtiene los destinatarios del Farma_Verifica.
  --Fecha       Usuario		Comentario
  --13/07/2006  ERIOS    	Creaci髇
  FUNCTION GET_RECEIVER_ADDRESS_VERIFICA RETURN VARCHAR2;

  --Descripcion: Obtiene los destinatarios encargados para verificar la Interface.
  --Fecha       Usuario		Comentario
  --13/07/2006  ERIOS    	Creaci髇
  FUNCTION GET_RECEIVER_ADDRESS_INTERFACE RETURN VARCHAR2;

  --Descripcion: Obtiene los destinatarios encargados para verificar el log de Viajero.
  --Fecha       Usuario		Comentario
  --13/07/2006  ERIOS    	Creaci髇
  FUNCTION GET_RECEIVER_ADDRESS_VIAJERO RETURN VARCHAR2;

  --Descripcion: Obtiene los destinatarios encargados para verificar el cambio
  --            de precios y fracionamiento que realiza el viajero en los locales.
  --Fecha       Usuario		Comentario
  --13/07/2006  ERIOS    	Creaci髇
  FUNCTION GET_RECEIVER_ADDRESS_CAMBIOS RETURN VARCHAR2;

  --Descripcion: Obtiene los destinatarios de auditoria.
  --Fecha       Usuario		Comentario
  --07/09/2006  ERIOS    	Creaci髇
  FUNCTION GET_RECEIVER_ADDRESS_AJUSTES RETURN VARCHAR2;

  --Descripcion: Obtiene mail del local
  --Fecha       Usuario		Comentario
  --05/01/2007  ERIOS    	Creacion
  FUNCTION GET_RECEIVER_ADDRESS_LOCAL(cCodLocal_in IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene los destinatarios de transferencias
  --Fecha       Usuario		Comentario
  --05/01/2007  ERIOS    	Creacion
  FUNCTION GET_RECEIVER_ADDRESS_TRANSF
  RETURN VARCHAR2;

  --Descripcion: Obtiene los destinatarios de Interfaz de CE
  --Fecha       Usuario		Comentario
  --11/01/2007  LMESIA    Creacion
  FUNCTION GET_RECEIVER_ADDRESS_INTER_CE
    RETURN VARCHAR2;

  --Descripcion: Obtiene los destinatarios de Administracion de Usuarios
  --Fecha       Usuario		Comentario
  --16/01/2007  PAULO    Creacion
  FUNCTION GET_RECEIVER_ADDRESS_ADMIN_USU
    RETURN VARCHAR2;

  --Descripcion: Obtiene los destinatarios de CONVENIO
  --Fecha       Usuario		Comentario
  --17/04/2007  PAULO    Creacion
  FUNCTION GET_RECEIVER_ADDRESS_INT_CONV
    RETURN VARCHAR2    ;

  --Descripcion: Adjunta reporte html 2.
  --Fecha       Usuario		Comentario
  --20/03/2007  ERIOS    	Creacion
  PROCEDURE attach_report2(conn         IN OUT NOCOPY utl_smtp.connection,
			mime_type    IN VARCHAR2 DEFAULT 'text/plain',
			inline       IN BOOLEAN  DEFAULT TRUE,
      directory IN VARCHAR2 DEFAULT NULL,
			filename     IN VARCHAR2 DEFAULT NULL,
		        last         IN BOOLEAN  DEFAULT FALSE);

  --Descripcion: Envia mail con attachment 2.
  --Fecha       Usuario		Comentario
  --20/03/2007  ERIOS    	Creacion
  PROCEDURE ENVIA_CORREO_ATTACH2(cSendorAddress_in in char,
                                cReceiverAddress_in in char,
                                cSubject_in in varchar2,
                                ctitulo_in in varchar2,
                                cmensaje_in in varchar2,
                                pDirectorio IN VARCHAR2,
                                pfilename IN VARCHAR2,
                                i IN NUMBER,
                                cCCReceiverAddress_in in char,
                                cip_servidor in char);

  --Descripcion: Envia mail con attachment 3.
  --Fecha       Usuario     	Comentario
  --09/07/2007  pameghino    	Creacion
  PROCEDURE ENVIA_CORREO_ATTACH3(cSendorAddress_in in char,
                                cReceiverAddress_in in char,
                                cSubject_in in varchar2,
                                ctitulo_in in varchar2,
                                cmensaje_in in varchar2,
                                pDirectorio IN VARCHAR2,
                                pfilename IN VARCHAR2,
                                cCCReceiverAddress_in in char,
                                cip_servidor in char);

  --Descripcion: Obtiene los destinatarios de IndLinea
  --Fecha       Usuario		Comentario
  --04/10/2007  DUBILLUZ   creacion
  FUNCTION GET_RECEIVER_ADDRESS_IND_LINEA
  RETURN VARCHAR2    ;

  --Descripcion: Obtiene los destinatarios de Mant.Local
  --Fecha       Usuario		Comentario
  --04/10/2007  DUBILLUZ   creacion
  FUNCTION GET_RECEIVER_ADDRESS_IMP_LOCAL
  RETURN VARCHAR2    ;

  --Descripcion: Obtiene los destinatarios de Gerencia Comercial.
  --Fecha       Usuario		Comentario
  --30/11/2007  ERIOS    	Creacion
  FUNCTION GET_RECEIVER_ADDRESS_GER_COMER
    RETURN VARCHAR2;

  --Descripcion: Obtiene los destinatarios de Interfaz de CE
  --Fecha       Usuario		Comentario
  --11/12/2007  DUBILLUZ   CREACION
  FUNCTION GET_RECEIVER_ADDRESS_VL_INT_CE
    RETURN VARCHAR2;


end farma_email;

/
