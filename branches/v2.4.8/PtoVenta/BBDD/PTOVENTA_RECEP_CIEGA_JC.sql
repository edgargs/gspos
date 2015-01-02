--------------------------------------------------------
--  DDL for Package PTOVENTA_RECEP_CIEGA_JC
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_RECEP_CIEGA_JC" is


  -- Author  : JCORTEZ
  -- Created : 16/11/2009 04:06:37 p.m.

  TYPE FarmaCursor IS REF CURSOR;

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
                                      '<td width="500" align="center" valign="top"><h1>CONSTANCIA <BR> INGRESO&nbsp;DE&nbsp;TRANSPORTISTA</h1></td>'  ||
                                      '</tr>'  ||
                                      '</table>'  ||
                                      '<table width="504" border="0">';

  C_FIN_MSG VARCHAR2(2000) := '</table>' ||
                                  '</body>' ||
                                  '</html>';
  --Descripcion: Obtiene listado de recepciones de mercaderia
  --Fecha       Usuario		Comentario
  --16/11/2009  JCORTEZ     	Creación
  FUNCTION RECEP_F_LISTA_MERCADERIA_RANGO(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  cFecIni_in  IN CHAR,
                                  cFecFin_in  IN CHAR)
  RETURN FarmaCursor;

  FUNCTION RECEP_F_LISTA_MERCADERIA(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene listado de guias pendientes por asociar
  --Fecha       Usuario		Comentario
  --16/11/2009  JCORTEZ     	Creación
  FUNCTION RECEP_F_OBTIENE_GUIAS_PEND(cCodGrupoCia_in IN CHAR,
								 	               cCodLocal_in	  IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene listado de guias asociadas
  --Fecha       Usuario		Comentario
  --16/11/2009  JCORTEZ     	Creación
  FUNCTION RECEP_F_OBTIENE_GUIAS_ASOC(cCodGrupoCia_in IN CHAR,
								 	               cCodLocal_in	        IN CHAR,
                                 cNumIngreso_in       IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene detalle de las guias
  --Fecha       Usuario		Comentario
  --16/11/2009  JCORTEZ     	Creación
   FUNCTION RECEP_F_LISTA_DET_GUIA(cGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cNumNota_in IN CHAR,
                                     cNumGuia_in IN CHAR)
   RETURN FarmaCursor;

  --Descripcion: Se crea nueva recepcion
  --Fecha       Usuario		Comentario
  --16/11/2009  JCORTEZ   Creación
   FUNCTION RECEP_P_NEW_RECEPCION(cCodGrupoCia_in IN CHAR,
                                    cCodLocal      IN CHAR,
                                    cIdUsu_in      IN CHAR,
                                    cCantGuias     IN NUMBER,
                                    cNombTransp    IN CHAR,
                                    cHoraTransp    IN CHAR,
                                    cPlaca         IN CHAR,
                                    nCantBultos    IN NUMBER,
                                    nCantPrecintos IN NUMBER,
                                    cGlosa         IN VARCHAR2 DEFAULT '')
   RETURN VARCHAR2;

  --Descripcion: Se asocian guias con la nueva recepcion
  --Fecha       Usuario		Comentario
  --16/11/2009  JCORTEZ   Creación
  PROCEDURE RECEP_P_AGREGA_GUIAS_RECEPCION(cCodGrupoCia_in IN CHAR,
                                cCodLocal       IN CHAR,
                                cIdUsu_in       IN CHAR,
                                cNumRecep_in    IN CHAR,
                                cNumNotaEs_in   IN CHAR,
                                cNumGuiaRem_in  IN CHAR,
                                cNumEntrega_in  IN CHAR,
                                cSecGuia        IN NUMBER);

  --Descripcion: Se listan guias asociadas a una entrega
  --Fecha       Usuario		Comentario
  --16/11/2009  JCORTEZ   Creación
  FUNCTION RECEP_F_OBTIENE_GUIAS_RECEP(cCodGrupoCia_in IN CHAR,
								 	                       cCodLocal_in	  IN CHAR,
                                         cNumRecep_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se IP para ingreso y detalle
  --Fecha       Usuario		Comentario
  --16/11/2009  JCORTEZ   Creación
  FUNCTION REPCEP_VALIDA_IP(cCodGrupoCia_in    IN CHAR,
                      		  cCod_Local_in      IN CHAR)
  RETURN CHAR;

  --Descripcion: Se valida rol de usuario
  --Fecha       Usuario		Comentario
  --16/11/2009  JCORTEZ   Creación
  FUNCTION RECEP_VERIFICA_ROL_USU(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in     IN CHAR,
                                  vSecUsu_in       IN CHAR,
                                  cCodRol_in       IN CHAR)
  RETURN CHAR;

     /****************************************************************************/
                                      --nCantPrecintos IN NUMBER,
   FUNCTION RECEP_F_INS_TRANSPORTISTA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal      IN CHAR,
                                      cCantGuias     IN NUMBER,
                                      cIdUsu_in      IN CHAR,
                                      cNombTransp    IN CHAR,
                                      cPlaca         IN CHAR,
                                      nCantBultos    IN NUMBER,
                                      nCantBandejas IN NUMBER,
                                      cGlosa IN VARCHAR2 DEFAULT '',
                                      cSecUsu_in     IN CHAR)
                                      RETURN VARCHAR2;

     /****************************************************************************/
    FUNCTION RECEP_F_VAR2_IMP_VOUCHER(cGrupoCia_in  IN CHAR,
                                              cCodLocal_in  IN CHAR,
                                              cNroRecep_in IN VARCHAR2) RETURN VARCHAR2;

     /****************************************************************************/
    FUNCTION RECEP_F_LISTA_TRANSP(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in IN CHAR)
    RETURN FarmaCursor;
   /****************************************************************************/
    FUNCTION RECEP_F_LISTA_TRANSP_RANGO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in IN CHAR,
                                    cFecIni_in  IN CHAR,
                                    cFecFin_in  IN CHAR)
    RETURN FarmaCursor;

   /****************************************************************************/
    FUNCTION RECEP_F_CANT_GUIAS (cCod_Grupo_cia_in CHAR,
                                 cCod_Local_in CHAR,
                                 cNro_Recepcion CHAR)
                                 RETURN NUMBER;

   /****************************************************************************/
    FUNCTION RECEP_F_DESASOCIA_ENTREGA(cCod_Grupo_cia_in CHAR,
                                     cCod_Local_in CHAR,
                                     cNro_Recepcion_in CHAR,
                                     cNro_Entrega_in CHAR)
                                     RETURN CHAR;

   /****************************************************************************/
    FUNCTION RECEP_F_MAX_PROD_VERIF
                                     RETURN NUMBER;

   /****************************************************************************/
    FUNCTION RECEP_F_TIENE_LOTE_SAP (cCod_GrupoCia_in IN CHAR,
                                     cCod_Local_in IN CHAR,
                                     cNro_Recepcion IN CHAR,
                                     cCod_Prod_in IN CHAR,
                                     cLote_in IN CHAR)
                                     RETURN VARCHAR2;

   /****************************************************************************/
    FUNCTION RECEP_F_IND_HAB_TRANSP
    RETURN CHAR;

    PROCEDURE RECEP_P_ELIMINA_CAB_RECEP(cCodGrupoCia_in IN CHAR,
                                    cCodLocal       IN CHAR,
                                    cIdUsu_in       IN CHAR,
                                    cNumRecep_in    IN CHAR);
    FUNCTION RECEP_F_PERMITE_INGR(cCod_Grupo_cia_in CHAR,
                              cCod_Local_in CHAR,
                              cNro_Recepcion CHAR,
                              cIdUsu_in  CHAR,
                              cIpPc_in  CHAR
                              ) return varchar2;

END;

/
