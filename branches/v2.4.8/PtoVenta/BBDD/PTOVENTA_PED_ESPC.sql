--------------------------------------------------------
--  DDL for Package PTOVENTA_PED_ESPC
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_PED_ESPC" AS

  g_cNumPedRep PBL_NUMERA.COD_NUMERA%TYPE := '014';

  g_nCantDiasPeriodoMes NUMBER := '28';

  g_cTipoNotaEntrada LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '01';
  g_cTipoNotaSalida LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '02';
  g_cTipoNotaRecepcion LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '03';

  g_cCodMatriz PBL_LOCAL.COD_LOCAL%TYPE := '009';

  TYPE FarmaCursor IS REF CURSOR;

  g_nTipoFiltroPrincAct NUMBER(1) := 1;
  g_nTipoFiltroAccTerap NUMBER(1) := 2;
  g_nTipoFiltroLab NUMBER(1) := 3;


  --Descripcion: Se lista los pedidos especiales
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
    FUNCTION PED_ESPC_F_CUR_LISTA_PEDIDOS( cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cFecIni_in IN CHAR,
                                         cFecFin_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se lista los productos Especiales
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
  FUNCTION PED_ESPC_F_CUR_LISTA_PROD(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: Se guarda la cabecera del pedido especial
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
  PROCEDURE PED_ESPC_P_ING_CAB_PED_ESP(cCodGrupoCia_in    IN CHAR,
                                cCodLocal_in      IN CHAR,
                                cIdUsu_in         IN CHAR,
                                cNumPed_in        IN CHAR,
                                cCantItem_in      IN CHAR);

  --Descripcion: Se guarda el detalle del pedido especial
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
  PROCEDURE PED_ESPC_P_ING_DET_PED_ESP(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cIdUsu_in         IN CHAR,
                                   cNumPed_in        IN CHAR,
                                   cSec_in           IN CHAR,
                                   cCodProd_in       IN CHAR,
                                   cCantIngre_in     IN CHAR);

  --Descripcion: Se lista el detalle del pedido especial
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
  FUNCTION PED_ESPC_F_CUR_LISTA_PED_DET(cGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNumPed_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se actualiza detalle pedido especial
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
  PROCEDURE PED_ESPC_P_UPT_PROD_DET(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cIdUsu_in         IN CHAR,
                                   cNumPed_in        IN CHAR,
                                   cCodProd_in       IN CHAR,
                                   cCantIngre_in     IN CHAR,
                                   cEstProd_in       IN CHAR);

  --Descripcion: Se actualiza cabecera pedido especial
  --Fecha       Usuario   Comentario
  --10/09/2008  JCORTEZ     Creación
  PROCEDURE PED_ESPC_P_UPT_CAB(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cIdUsu_in         IN CHAR,
                             cNumPed_in        IN CHAR);

  --Descripcion: retorna el numero maximo de item por pedido especial
  --Fecha       Usuario   Comentario
  --22/09/2008  DUBILLUZ     Creación
  FUNCTION PED_ESPC_F_CHAR_MAX_ITEM_PED(cGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR)
  RETURN varCHAR2;


  --Descripcion: Se anula el pedido completo del local
  --Fecha       Usuario   Comentario
  --25/09/2008  JCORTEZ   Creación
  PROCEDURE PED_ESPC_ANULA(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cIdUsu_in         IN CHAR,
                             cNumPed_in        IN CHAR);

  --Descripcion: Cambia el estado del pedido a M
  --Fecha       Usuario   Comentario
  --18/10/2008  DVELIZ   Creación
  PROCEDURE PED_ESPC_P_MOD_EST_PEDIDO(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cIdUsu_in         IN CHAR,
                             cNumPed_in        IN CHAR,
                             cEstPedVta        IN CHAR,
                             cIpPC             IN CHAR);

  --Descripcion: Carga el detalle de un determinado pedido para modificarlo.
  --Fecha       Usuario   Comentario
  --18/10/2008  DVELIZ   Creación
  FUNCTION PED_ESPC_P_CUR_CARGA_DETALLE(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cNumPed_in        IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Limpia el detalle de cierto pedido para actualizarlo
  --Fecha       Usuario   Comentario
  --18/10/2008  DVELIZ   Creación
  PROCEDURE PED_ESPC_P_DEL_DET_PEDIDO(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cNumPed_in        IN CHAR);

  --Descripcion: Envia un mensaje de alerta con pedido en esta M
  --Fecha       Usuario   Comentario
  --18/10/2008  DVELIZ   Creación
  FUNCTION PED_ESPC_F_VAR_MENSAJE(cCodGrupoCia_in         IN CHAR,
                                   cCodLocal_in            IN CHAR,
                                   cFechaInicial_in        IN CHAR,
                                   cFechaFinal_in          IN CHAR)
  RETURN VARCHAR2;


  FUNCTION PED_ESPC_F_GET_EST_PEDIDO(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cNumPed_in        IN CHAR)
  RETURN CHAR;
end;

/
