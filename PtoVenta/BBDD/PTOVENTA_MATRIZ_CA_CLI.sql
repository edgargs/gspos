--------------------------------------------------------
--  DDL for Package PTOVENTA_MATRIZ_CA_CLI
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_MATRIZ_CA_CLI" AS

  -- Author  : JCALLO
  -- Created : 28/10/2008
  -- Purpose :

  C_TIP_CAMP_ACUMULADAS CHAR(1) :='A';

  CC_MOD_CA_CLI CHAR(2) :='CA';

  C_TIPO_HIST_PEDIDO_PENDIENTE CHAR(1) :='P';-- Estado para los pedidos acumuladas que se generaron pero no cobrados


  TYPE FarmaCursor IS REF CURSOR;

  /* USADOS PARA IMPRIMIR*/
  C_INICIO_MSG  VARCHAR2(3000) := '<html><head><style type="text/css">'||
                                  '.titulo {font-size: 14;font-weight: bold;font-family: Arial, Helvetica, sans-serif;}'||
                                  '.cliente {font-size: 12;font-family: Arial, Helvetica, sans-serif;} '||
                                  '.histcab {font-size: 12;font-family: Arial, Helvetica, sans-serif; font-weight: bold;}'||
                                  '.historico {font-size: 12;font-family: Arial, Helvetica, sans-serif; }'||
                                  '.msgfinal {font-size: 14;font-style: italicfont-family: Arial, Helvetica, sans-serif;}'||
                                  '.tip {font-size: 9;font-style: italic;font-family: Arial, Helvetica, sans-serif;}'||
                                  '</style>'||
                                  '</head>'||
                                  '<body>'||
                                  '<table width="200" border="0">'||
                                  '<tr>'||
                                  '<td>&nbsp;&nbsp;</td>'||
                                  '<td>'||
                                  '<table width="300"  border="1" cellspacing="0" cellpading="0">';

  --Descripcion: RETORNA UN INDICADOR SI LA TARJETA NO TIENE ASIGNADO CLIENTE EN LOCAL
  --Fecha       Usuario		Comentario
  --28/10/2008  JCALLO    CREACION
  FUNCTION CA_F_CUR_DATOS_DNI(cDNI_in IN CHAR)
  RETURN FarmaCursor      ;

  --Descripcion: --numero de las tarjetas asociada a cada cliente.
  --Fecha       Usuario		Comentario
  --17/12/2008  JCALLO    CREACION
  FUNCTION CA_F_CUR_TARJETAS_CLI( cDniCliente_in IN CHAR )
  RETURN FarmaCursor;

  -- obtiene la cantidad restante de compras de los productos de la campañas para obtener regalo
  -- y/o promoion
  -- jcallo    17.12.2008
  FUNCTION CA_F_CUR_CAMP_MATRIZ_REST(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cDni_in         IN CHAR,
                                      cNumPed_in      IN CHAR)
  RETURN  FarmaCursor;

  -- haber que sale
  -- jcallo    23.12.2008
  PROCEDURE CA_P_INSERT_CLIENTE_MATRIZ( vDni_cli    IN CHAR,
                                 vNom_cli    IN VARCHAR2,
                                 vApat_cli   IN VARCHAR2,
                                 vAmat_cli   IN VARCHAR2,
                                 vSexo_cli   IN CHAR,
                                 vFecNac_cli IN CHAR,
                                 vDir_cli    IN VARCHAR2,
                                 vFono_cli   IN CHAR,
                                 vCell_cli   IN CHAR,
                                 vEmail_cli  IN VARCHAR2,
                                 pCodLocal   IN CHAR,
                                 pUser       IN CHAR,
                                 pIndEstado  IN CHAR);

  -- haber que sale
  -- jcallo    23.12.2008
  FUNCTION CA_F_NUM_CAMPOS_CLIENTE(cDni   IN CHAR)
  RETURN number;

--Descripcion: Obtiene numero de Dia
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  FUNCTION OBTIEN_NUM_DIA(cDate_in IN DATE)
  RETURN VARCHAR2;


  --Descripcion: inserta auxiliar de canje
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  PROCEDURE CA_P_INSERT_AUX_CANJE (
                                   cDni_in       IN CHAR,
                                   cCodCamp_in   IN CHAR,

                                   cCodGrupoCia_in     IN CHAR,
                                   cCodLocalCanj_in    IN CHAR,
                                   cNumPedCanj_in      IN CHAR,

                                   cCodLocalOrigen_in   IN CHAR,
                                   cNumPedOrigen_in     IN CHAR,
                                   nSecPedVtaOrigen_in IN NUMBER,
                                   cCodProdOrigen_in   IN CHAR,
                                   nCantUso_in   IN NUMBER,
                                   nValFracMin_in IN NUMBER
                                   );


  --Descripcion: Opera beneficio de campaña
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
      FUNCTION CA_F_OPERA_BENEFICIO_CAMPANA(
                                         cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNumPed_in       IN CHAR,
                                         cDni_in          IN CHAR,
                                         cUsuCrea_in      IN CHAR
                                        )
                                         return FarmaCursor;
  --Descripcion: Verifica si permite canje
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  FUNCTION CA_F_PERMITE_CANJE(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cDni_in         IN CHAR,
                              cCodCamp_in     IN CHAR,
                              nNumDia_in      IN NUMBER)
  RETURN CHAR;


  --Descripcion: inserta historico
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  PROCEDURE CA_P_INSERT_HIS_PED_CLI (
                                   cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cNumPed_in       IN CHAR,
                                   cDni_in       IN CHAR,
                                   cCodCamp_in   IN CHAR,
                                   nSecPedVta_in IN CHAR,
                                   cCodProd_in   IN CHAR,
                                   nCantPedido_in   IN CHAR,
                                   nValFracPedido_in IN CHAR,
                                   cEstado_in IN CHAR,
                                   nValFracMin_in IN CHAR,
                                   cUsuCrea_in IN CHAR  ,
                                   nCantRest_in      IN CHAR
                                   );
  -------------------
  --VERIFICA SI SE COBRO O NO EL PEDIDO
  --SI SE COBRO SE CAMBIA EL ESTADO CASO CONTRARIO SE ELIMINAN LOS DATOS
  PROCEDURE CA_P_ANALIZA_CANJE (
                                cCodGrupoCia_in  IN CHAR,
                                cCodLocal_in     IN CHAR,
                                cNumPed_in       IN CHAR,
                                cDni_in          IN CHAR,
                                cUsuMod_in       IN CHAR,
                                cAccion_in       IN CHAR
                                );

  PROCEDURE CA_P_INSERT_CLI_CAMPANIA( cCodGrupoCia_in IN CHAR,
                                      cCodCampCupon_in  IN CHAR,
                                      cDni_cli_in       IN CHAR,
                                      cUsuario_in       IN CHAR);

  --metodo encargado de asociar cliente con tarjeta de fidelizacion
  --29.12.2008 JCALLO
  PROCEDURE CA_P_UPD_TARJETA_CLIENTE( cTarjeta_in    IN CHAR,
                                      cDni_cli_in    IN CHAR,
                                      cCodLocal_in   IN CHAR,
                                      cUsuario_in    IN CHAR);

  PROCEDURE CA_P_INSERT_CANJ_PED_CLI (
                                     cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     cNumPed_in        IN CHAR,
                                     dFechaPed_in      IN CHAR,
                                     cDni_in           IN CHAR,
                                     cCodCamp_in       IN CHAR,
                                     nSecPedVta_in     IN CHAR,--
                                     cCodProd_in       IN CHAR,
                                     nCantPedido_in    IN CHAR,--
                                     nValFracPedido_in IN CHAR,--
                                     cEstado_in        IN CHAR,
                                     cUsuCrea_in       IN CHAR
                                   );

    PROCEDURE CA_P_INSERT_ORIG_PED_CLI (
                                     cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     cNumPed_in        IN CHAR,
                                     dFechaPed_in      IN CHAR,
                                     cDni_in           IN CHAR,
                                     cCodCamp_in       IN CHAR,
                                     nSecPedVta_in     IN CHAR,--
                                     cCodProd_in       IN CHAR,
                                     nValFracPedido_in IN CHAR,--
                                     cEstado_in        IN CHAR,
                                     cUsuCrea_in       IN CHAR,

                                     cCodLocalOrigen_in  IN CHAR,
                                     cNumPedOrigen_in    IN CHAR,
                                     cSecPedVtaOrigen_in IN CHAR,
                                     cCodProdOrigen_in   IN CHAR,
                                     cCantidadUso_in  IN CHAR
                                   );

   PROCEDURE CA_P_REVERTIR_CANJE_MATRIZ(
                                       cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cNumPed_in       IN CHAR,
                                       cDni_in       IN CHAR,
                                       cCodCamp_in   IN CHAR,
                                       cUsuMod_in    IN CHAR
                                       );

END PTOVENTA_MATRIZ_CA_CLI;

/
