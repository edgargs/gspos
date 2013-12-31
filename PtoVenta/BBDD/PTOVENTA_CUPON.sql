--------------------------------------------------------
--  DDL for Package PTOVENTA_CUPON
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CUPON" is

  -- Author  : ERIOS
  -- Created : 11/07/2008 08:55:08 a.m.
  -- Purpose :

  -- Public type declarations
  TYPE FarmaCursor IS REF CURSOR;

    TIPO_CONSULTA_ACTUALIZA_MATRIZ VARCHAR2(1000) := 'ACTUALIZA_MATRIZ';
    TIPO_CONSULTA_ACTUALIZA_LOCAL VARCHAR2(1000) := 'ACTUALIZA_CUPON_LOCAL';

  TIPO_CONSULTA_VALIDA_CUPON     VARCHAR2(1000) := 'VALIDA_CUPON';
  TIPO_CONSULTA_NOTA_CREDITO     VARCHAR2(1000) := 'PROCESO_NOTA_CREDITO';
  TIPO_CONSULTA_CUP_EMIT_USADOS     VARCHAR2(1000) := 'CUPONES_EMITIDOS_USADOS';
  TIPO_CONSULTA_CUP_ANUL_MATRIZ  VARCHAR2(1000) := 'CUPONES_ANULADOS_SIN_PROC_MATRIZ';


  C_ESTADO_ACTIVO CHAR(1) := 'A';
  C_ESTADO_EMITIDO CHAR(1) := 'E';
  C_ESTADO_ANULADO CHAR(1) := 'N';
  C_ESTADO_USADO CHAR(1) := 'U';

  PROCEDURE ACTUALIZA_CUPONES_MATRIZ(cCodGrupoCia_in IN CHAR);

  PROCEDURE ACT_CUPONES_EMITIDOS(cCodGrupoCia_in IN CHAR);

  PROCEDURE ACT_CUPONES_ANULADOS(cCodGrupoCia_in IN CHAR);

  PROCEDURE ACT_CUPONES_USADOS(cCodGrupoCia_in IN CHAR);

 --Descripcion: Actualiza la fecha de proceso Matriz del cupon
  --Fecha       Usuario		Comentario
  --18/08/2008  DUBILLUZ  Creación
  PROCEDURE CUP_P_ACT_GENERAL_CUPON(cCodGrupoCia_in IN CHAR,
                                          cCodCupon_in    IN CHAR,
                                          vIdUsu_in       IN VARCHAR2,
                                          cTipoConsulta   IN CHAR);

  --Descripcion: Bloquea el estado del cupon en el local
  --Fecha       Usuario		Comentario
  --18/08/2008  DUBILLUZ  Creación
  FUNCTION CUP_F_CHAR_BLOQ_EST(cCodGrupoCia_in IN CHAR,
                               cCodCupon_in    IN CHAR)
  RETURN CHAR;

  --Descripcion: RETORNA EL INDICADOR DE MULTIPLOUSO DE LA CAMPAÑA
  --Fecha       Usuario		Comentario
  --18/08/2008  DUBILLUZ  Creación
  FUNCTION CUP_F_CHAR_IND_MULTIPLO_CUP(cCodGrupoCia_in IN CHAR,
                                       cCodCupon_in    IN CHAR)
  RETURN CHAR;

  --Descripcion: Obtiene los cupones del pedido
  --Fecha       Usuario		Comentario
  --19/08/2008  DUBILLUZ  CREACION
 FUNCTION CUP_F_CUR_CUP_PED(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cNumPedVta_in   IN CHAR,
                            cIndLineaMatriz IN CHAR DEFAULT 'N',
                            cTipoConsulta   IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se verifica la conexion con matriz
  --Fecha       Usuario		Comentario
  --10/07/2008  ERIOS     Creacion
  FUNCTION ACT_VERIFICA_CONN_MATRIZ
  RETURN CHAR;


  --Descripcion: Obtiene los cupones del pedido
  --Fecha       Usuario		Comentario
  --28/08/2008  DUBILLUZ  CREACION
 FUNCTION CUP_F_CHAR_CUP_PED(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cNumPedVta_in   IN CHAR,
                            cTipoCupon IN CHAR)
  RETURN CHAR;


  --Descripcion: Valida el cupon al momento de ingresar el cupon
  -- Esta Function se trajo del PtoVenta_Vta
  --Fecha       Usuario		Comentario
  --03/07/2008  ERIOS     Creacion
  --01/09/2008  DUBILLUZ  Modificacion
 FUNCTION CUP_F_CUR_VALIDA_CUPON(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cCadenaCupon_in IN CHAR,
                                 cIndMultiUso_in IN CHAR default 'N',
                                 cDniCliente     IN CHAR DEFAULT NULL)
 RETURN FarmaCursor;

  FUNCTION CUP_F_CHAR_VALIDA_EAN13(vCodigoCupon_in IN VARCHAR2)
  RETURN CHAR;

  --Descripcion: Valida el Campana cupon con fidelizacion
  --Fecha       Usuario		Comentario
  --10/10/2008  JCALLO     Creacion
  FUNCTION CUP_F_CHAR_VALIDA_CUPON_FID(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cCodCampCupon_in IN CHAR,
                                       cDniCliente     IN CHAR)
  RETURN CHAR;

  --Descripcion: Activa cupones que tenia el pedido
  --Fecha       Usuario		Comentario
  --02/12/2008  DUBILLUZ     Creacion
  PROCEDURE CUP_P_ACTIVA_CUPONES(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPedVta_in   IN CHAR,
                                cIdUsu_in IN CHAR);

 FUNCTION CUP_F_CUR_CUP_USADOS(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cNumPedVta_in   IN CHAR)
   RETURN FarmaCursor;

   --DESCRIPCON: funcion nueva de valida de uso de cupones
   --autor:jcallo
   --fecha:04/03/2009
   FUNCTION CUP_F_CHAR_VALIDA_CUPON(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cCadenaCupon_in IN CHAR,
                                    cIndMultiUso_in IN CHAR default 'N',
                                    cDniCliente     IN CHAR DEFAULT NULL)
   RETURN CHAR;


   --DESCRIPCON: Se genera N cupones de X campaña cuando se marca ingreso de QF
   --FECHA       Usuario		Comentario
   --17/08/2009  JCORTEZ     Creacion
  PROCEDURE CAJ_P_GENERA_CUPON_REGALO(pCodGrupoCia_in         IN CHAR,
                                      cCodLocal_in            IN CHAR,
                								 	  	cIdUsu_in               IN CHAR,
                                      cNumDocIdent            IN CHAR);

   --DESCRIPCON: Se obtiene cupones de regalo diarios
   --FECHA       Usuario		Comentario
   --17/08/2009  JCORTEZ     Creacion
  FUNCTION CUP_F_CUR_CUP_REGALOS(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in     IN CHAR,
                                 cdni_in          IN CHAR)
  RETURN FarmaCursor;

  --DESCRIPCON: Se valida si se permite generar cupones
  --FECHA       Usuario		Comentario
  --17/08/2009  JCORTEZ     Creacion
  FUNCTION CUP_F_VERI_EXIST_CUP(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in     IN CHAR,
                               cDni_in          IN CHAR)
  RETURN CHAR;
end;

/
