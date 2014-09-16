--------------------------------------------------------
--  DDL for Package Body PKG_CONVENIO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "BTLPROD"."PKG_CONVENIO" IS

   /************************************************************************************/
   FUNCTION FN_LISTA_PETITORIO_CONVENIO(A_COD_CONVENIO CHAR)
      RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT A.COD_PETITORIO, B.DES_PETITORIO
           FROM BTLPROD.REL_PETITORIO_CONVENIO A, BTLPROD.CAB_PETITORIO B
          WHERE A.FLG_ACTIVO = '1'
            AND A.COD_PETITORIO = B.COD_PETITORIO
            AND A.COD_CONVENIO = A_COD_CONVENIO
          ORDER BY NUM_ORDEN;
      RETURN C_CURSOR;
   END FN_LISTA_PETITORIO_CONVENIO;
   /*===============================================================================
                   DEVUELVE LA TABLA CMR.MAE_CLASE_CONVENIO CUANDO EL FLG_ACTIVO ES 1
   ===============================================================================*/

   FUNCTION FN_LISTA_DOC_VER(A_COD_DOCUMENTO_VERIFICACION CMR.MAE_DOCUMENTO_VERIFICACION.COD_DOCUMENTO_VERIFICACION%TYPE DEFAULT NULL)
      RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN
      IF A_COD_DOCUMENTO_VERIFICACION IS NULL THEN
         OPEN C_CURSOR FOR
            SELECT COD_DOCUMENTO_VERIFICACION "Codigo",
                   DES_DOCUMENTO_VERIFICACION "Descripcion",
                   DES_ABREVIATURA,
                   FLG_ESTADO,
                   COD_USUARIO,
                   FCH_REGISTRA,
                   COD_USUARIO_ACTUALIZA,
                   FCH_ACTUALIZA
              FROM CMR.MAE_DOCUMENTO_VERIFICACION
              ORDER BY CMR.MAE_DOCUMENTO_VERIFICACION.COD_DOCUMENTO_VERIFICACION;
      ELSE
         OPEN C_CURSOR FOR
            SELECT COD_DOCUMENTO_VERIFICACION "Descripcion",
                   DES_DOCUMENTO_VERIFICACION "Codigo",
                   DES_ABREVIATURA,
                   FLG_ESTADO,
                   COD_USUARIO,
                   FCH_REGISTRA,
                   COD_USUARIO_ACTUALIZA,
                   FCH_ACTUALIZA
              FROM CMR.MAE_DOCUMENTO_VERIFICACION
             WHERE COD_DOCUMENTO_VERIFICACION = A_COD_DOCUMENTO_VERIFICACION
             ORDER BY CMR.MAE_DOCUMENTO_VERIFICACION.COD_DOCUMENTO_VERIFICACION;
      END IF;
      RETURN C_CURSOR;
   END FN_LISTA_DOC_VER;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   FUNCTION FN_LISTA_CLASE(A_COD_CLASE_CONVENIO CMR.MAE_CLASE_CONVENIO.COD_CLASE_CONVENIO%TYPE DEFAULT NULL)
      RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN
      IF A_COD_CLASE_CONVENIO IS NULL THEN

         OPEN C_CURSOR FOR
            SELECT COD_CLASE_CONVENIO,
                   DES_CLASE_CONVENIO,
                   FLG_ACTIVO,
                   COD_USUARIO,
                   FCH_REGISTRA,
                   COD_USUARIO_ACTUALIZA,
                   FCH_ACTUALIZA
              FROM CMR.MAE_CLASE_CONVENIO;
      ELSE
         OPEN C_CURSOR FOR
            SELECT COD_CLASE_CONVENIO,
                   DES_CLASE_CONVENIO,
                   FLG_ACTIVO,
                   COD_USUARIO,
                   FCH_REGISTRA,
                   COD_USUARIO_ACTUALIZA,
                   FCH_ACTUALIZA
              FROM CMR.MAE_CLASE_CONVENIO
             WHERE COD_CLASE_CONVENIO = A_COD_CLASE_CONVENIO;
      END IF;
      RETURN C_CURSOR;
   END FN_LISTA_CLASE;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   /*===============================================================================
                   ACTUALIZA UN REGISTRO EN LA TABLA CMR.MAE_CLASE_CONVENIO
   ===============================================================================*/
   PROCEDURE SP_GRABA_CLASE(A_COD_CLASE_CONVENIO CMR.MAE_CLASE_CONVENIO.COD_CLASE_CONVENIO%TYPE DEFAULT NULL,
                            A_DES_CLASE_CONVENIO CMR.MAE_CLASE_CONVENIO.DES_CLASE_CONVENIO%TYPE,
                            A_FLG_ACTIVO         CMR.MAE_CLASE_CONVENIO.FLG_ACTIVO%TYPE,
                            A_COD_USUARIO        CMR.MAE_CLASE_CONVENIO.COD_USUARIO%TYPE) IS
      C_COD_CLASE_CONVENIO CMR.MAE_CLASE_CONVENIO.COD_CLASE_CONVENIO%TYPE;
   BEGIN
      IF A_COD_CLASE_CONVENIO IS NULL THEN
         C_COD_CLASE_CONVENIO := CMR.FN_DEV_PROX_SEC('CMR',
                                                     'MAE_CLASE_CONVENIO');
         INSERT INTO CMR.MAE_CLASE_CONVENIO
            (COD_CLASE_CONVENIO,
             DES_CLASE_CONVENIO,
             FLG_ACTIVO,
             COD_USUARIO,
             FCH_REGISTRA,
             COD_USUARIO_ACTUALIZA,
             FCH_ACTUALIZA)
         VALUES
            (C_COD_CLASE_CONVENIO,
             A_DES_CLASE_CONVENIO,
             A_FLG_ACTIVO,
             A_COD_USUARIO,
             SYSDATE,
             NULL,
             NULL);
      ELSE
         UPDATE CMR.MAE_CLASE_CONVENIO
            SET DES_CLASE_CONVENIO    = A_DES_CLASE_CONVENIO,
                FLG_ACTIVO            = A_FLG_ACTIVO,
                COD_USUARIO_ACTUALIZA = A_COD_USUARIO,
                FCH_ACTUALIZA         = SYSDATE
          WHERE COD_CLASE_CONVENIO = A_COD_CLASE_CONVENIO;
         IF SQL%NOTFOUND THEN
            RAISE_APPLICATION_ERROR(-20003,
                                    'NO SE PUDO ACTUALIZAR LA TABLA CMR.MAE_CLASE_CONVENIO');
         END IF;
      END IF;
      COMMIT;
   EXCEPTION
      WHEN DUP_VAL_ON_INDEX THEN
         RAISE_APPLICATION_ERROR(-20003, 'EL CODIGO DE CLASE YA EXISTE.');
      WHEN OTHERS THEN
         RAISE_APPLICATION_ERROR(-20003, 'SE PRODUJO UN ERROR AL REGISTRAR LA CLASE.'||CHR(13)||SQLERRM);
   END SP_GRABA_CLASE;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   /*===============================================================================
                   DEVUELVE LA TABLA CMR.MAE_CONVENIO CUANDO EL FLG_ACTIVO ES 1
   ===============================================================================*/
   FUNCTION FN_LISTA(A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE DEFAULT NULL,
                     A_ORDEN        NUMBER DEFAULT 0) RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN
      IF A_COD_CONVENIO IS NULL THEN
         OPEN C_CURSOR FOR
            SELECT A.COD_CONVENIO,
                   A.COD_CLIENTE,
                   DECODE(B.FLG_TIPO_JURIDICA,
                          '0',
                          B.DES_NOM_CLIENTE,
                          B.DES_RAZON_SOCIAL) CLIENTE,
                   A.COD_CLASE_CONVENIO,
                   C.DES_CLASE_CONVENIO,
                   A.COD_PETITORIO,
                   A.DES_CONVENIO,
                   A.FLG_TIPO_CONVENIO,
                   DECODE(A.FLG_TIPO_CONVENIO,
                          '1',
                          'Credito Co-Pago',
                          '2',
                          'Dcto Automatico') TIPO_CONVENIO,
                   A.PCT_BENEFICIARIO,
                   A.PCT_EMPRESA,
                   A.IMP_LINEA_CREDITO,
                   A.FLG_POLITICA,
                   A.FLG_PETITORIO,
                   A.FLG_PERIODO_VALIDEZ,
                   A.FCH_INICIO,
                   A.FCH_FIN,
                   A.FLG_RENOVACION_AUTO,
                   A.FLG_ATENCION_LOCAL,
                   A.FLG_ATENCION_DELIVERY,
                   A.IMP_MINIMO_DELIVERY,
                   A.FLG_TIPO_PERIODO,
                   A.DIA_CORTE,
                   A.CTD_CANCELACION,
                   A.FLG_BENEFICIARIOS,
                   A.COD_TIPDOC_CLIENTE,
                   A.COD_TIPDOC_BENEFICIARIO,
                   A.FLG_NOTACREDITO,
                   A.FLG_FACTURA_LOCAL,
                   A.IMP_MINIMO,
                   A.IMP_PRIMERA_COMPRA,
                   A.FLG_TARJETA,
                   A.FLG_TIPO_VALOR,
                   A.IMP_VALOR,
                   A.FLG_TIPO_PRECIO,
                   A.FLG_TIPO_PRECIO_LOCAL,
                   A.COD_LOCAL_REF,
                   A.DES_OBSERVACION,
                   A.FLG_ATENCION_TODOS_LOCALES,
                   A.FLG_COBRO_TODOS_LOCALES,
                   A.FLG_ACTIVO,
                   A.COD_USUARIO,
                   A.FCH_REGISTRA,
                   A.COD_USUARIO_ACTUALIZA,
                   A.FCH_ACTUALIZA,
                   A.FLG_REPARTIDOR,
                   A.FLG_MEDICO,
                   A.FLG_REPARTIDOR,
                   A.NUM_DOC_FLG_KDX,
                   A.FLG_TIPO_PAGO,
                   B.FLG_TIPO_JURIDICA,
                   B.NUM_DOCUMENTO_ID,
                   A.FLG_VALIDA_LINCRE_BENEF,
                   A.FLG_RECETA,
                   A.FLG_DIAGNOSTICO,
                   A.COD_CONVENIO_GLM,
                   B.DES_RAZON_SOCIAL,
                   A.FLG_IMPRIME_IMPORTES,
                   A.FLG_PRECIO_MENOR,
                   A.FLG_PRECIO_DEDUCIBLE,
                   A.DES_IMPRESION,
                   A.FCH_IMPRESION_INI,
                   A.FCH_IMPRESION_FIN,
                   FLG_IMPRIME_COPAGO,
                   A.NUM_NIVELES,
                   A.NUM_MAX_BENEF,
                   A.MTO_MAX_LINEA_COMPRA,
                   A.FLG_PER_ANU_DOC,
                   A.FLG_PER_NOT_CRE,
                   A.FLG_AFILIACION_ACT,
                   A.FLG_ESCALA_UNICA,
                   A.COD_EJECUTIVO,
                   A.COD_TRAMA,
                   A.DES_DESTINATARIO_TRAMA,
                   A.FLG_PLAN_VITAL,
                   A.CTD_MAX_UND_PRODUCTO,
                   A.COD_CONCEPTO,
                   a.mto_linea_cred_base,
                   a.flg_ing_beneficiario,
                   nvl(a.cod_cliente_sap_bolsa,' ') cod_cliente_sap_bolsa
              FROM CMR.MAE_CONVENIO       A,
                   CMR.MAE_CLIENTE        B,
                   CMR.MAE_CLASE_CONVENIO C
             WHERE A.COD_CLIENTE = B.COD_CLIENTE
               AND A.COD_CLASE_CONVENIO = C.COD_CLASE_CONVENIO
             ORDER BY DECODE(A_ORDEN, 1, A.DES_CONVENIO, A.COD_CONVENIO);
      ELSE
         IF CMR.PKG_UTIL.FN_ES_NUMERO(A_COD_CONVENIO) THEN
            OPEN C_CURSOR FOR
               SELECT A.COD_CONVENIO,
                      A.COD_CLIENTE,
                      DECODE(B.FLG_TIPO_JURIDICA,
                             '0',
                             B.DES_NOM_CLIENTE,
                             B.DES_RAZON_SOCIAL) CLIENTE,
                      A.COD_CLASE_CONVENIO,
                      C.DES_CLASE_CONVENIO,
                      A.COD_PETITORIO,
                      A.DES_CONVENIO,
                      A.FLG_TIPO_CONVENIO,
                      DECODE(A.FLG_TIPO_CONVENIO,
                             '1',
                             'Credito Co-Pago',
                             '2',
                             'Dcto Automatico') TIPO_CONVENIO,
                      A.PCT_BENEFICIARIO,
                      A.PCT_EMPRESA,
                      A.IMP_LINEA_CREDITO,
                      A.FLG_POLITICA,
                      A.FLG_PETITORIO,
                      A.FLG_PERIODO_VALIDEZ,
                      A.FCH_INICIO,
                      A.FCH_FIN,
                      A.FLG_RENOVACION_AUTO,
                      A.FLG_ATENCION_LOCAL,
                      A.FLG_ATENCION_DELIVERY,
                      A.IMP_MINIMO_DELIVERY,
                      A.FLG_TIPO_PERIODO,
                      A.DIA_CORTE,
                      A.CTD_CANCELACION,
                      A.FLG_BENEFICIARIOS,
                      A.COD_TIPDOC_CLIENTE,
                      A.COD_TIPDOC_BENEFICIARIO,
                      A.FLG_NOTACREDITO,
                      A.FLG_FACTURA_LOCAL,
                      A.IMP_MINIMO,
                      A.IMP_PRIMERA_COMPRA,
                      A.FLG_TARJETA,
                      A.FLG_TIPO_VALOR,
                      A.IMP_VALOR,
                      A.FLG_TIPO_PRECIO,
                      A.FLG_TIPO_PRECIO_LOCAL,
                      A.COD_LOCAL_REF,
                      A.DES_OBSERVACION,
                      A.FLG_ATENCION_TODOS_LOCALES,
                      A.FLG_COBRO_TODOS_LOCALES,
                      A.FLG_ACTIVO,
                      A.COD_USUARIO,
                      A.FCH_REGISTRA,
                      A.COD_USUARIO_ACTUALIZA,
                      A.FCH_ACTUALIZA,
                      A.FLG_REPARTIDOR,
                      A.FLG_MEDICO,
                      A.FLG_REPARTIDOR,
                      A.NUM_DOC_FLG_KDX,
                      A.FLG_TIPO_PAGO,
                      B.FLG_TIPO_JURIDICA,
                      B.NUM_DOCUMENTO_ID,
                      A.FLG_VALIDA_LINCRE_BENEF,
                      A.FLG_RECETA,
                      A.FLG_DIAGNOSTICO,
                      A.COD_CONVENIO_GLM,
                      B.DES_RAZON_SOCIAL,
                      A.FLG_IMPRIME_IMPORTES,
                      A.FLG_PRECIO_MENOR,
                      A.FLG_PRECIO_DEDUCIBLE,
                      A.DES_IMPRESION,
                      A.FCH_IMPRESION_INI,
                      A.FCH_IMPRESION_FIN,
                      FLG_IMPRIME_COPAGO,
                     A.NUM_NIVELES,
                     A.NUM_MAX_BENEF,
                     A.MTO_MAX_LINEA_COMPRA,
                     A.FLG_PER_ANU_DOC,
                     A.FLG_PER_NOT_CRE,
                     A.FLG_AFILIACION_ACT,
                   A.FLG_ESCALA_UNICA,
                   A.COD_EJECUTIVO,
                   A.COD_TRAMA,
                   A.DES_DESTINATARIO_TRAMA,
                   A.FLG_PLAN_VITAL,
                   A.CTD_MAX_UND_PRODUCTO,
                   A.COD_CONCEPTO  ,
                   a.mto_linea_cred_base,
                   a.flg_ing_beneficiario ,
                   nvl(a.cod_cliente_sap_bolsa,' ') cod_cliente_sap_bolsa
                 FROM CMR.MAE_CONVENIO       A,
                      CMR.MAE_CLIENTE        B,
                      CMR.MAE_CLASE_CONVENIO C
                WHERE A.COD_CLIENTE = B.COD_CLIENTE
                  AND A.COD_CONVENIO = A_COD_CONVENIO
                  AND A.COD_CLASE_CONVENIO = C.COD_CLASE_CONVENIO
                ORDER BY DECODE(A_ORDEN, 1, A.DES_CONVENIO, A.COD_CONVENIO);
         ELSE
            OPEN C_CURSOR FOR
               SELECT A.COD_CONVENIO,
                      A.COD_CLIENTE,
                      DECODE(B.FLG_TIPO_JURIDICA,
                             '0',
                             B.DES_NOM_CLIENTE,
                             B.DES_RAZON_SOCIAL) CLIENTE,
                      A.COD_CLASE_CONVENIO,
                      C.DES_CLASE_CONVENIO,
                      A.COD_PETITORIO,
                      A.DES_CONVENIO,
                      A.FLG_TIPO_CONVENIO,
                      DECODE(A.FLG_TIPO_CONVENIO,
                             '1',
                             'Credito Co-Pago',
                             '2',
                             'Dcto Automatico') TIPO_CONVENIO,
                      A.PCT_BENEFICIARIO,
                      A.PCT_EMPRESA,
                      A.IMP_LINEA_CREDITO,
                      A.FLG_POLITICA,
                      A.FLG_PETITORIO,
                      A.FLG_PERIODO_VALIDEZ,
                      A.FCH_INICIO,
                      A.FCH_FIN,
                      A.FLG_RENOVACION_AUTO,
                      A.FLG_ATENCION_LOCAL,
                      A.FLG_ATENCION_DELIVERY,
                      A.IMP_MINIMO_DELIVERY,
                      A.FLG_TIPO_PERIODO,
                      A.DIA_CORTE,
                      A.CTD_CANCELACION,
                      A.FLG_BENEFICIARIOS,
                      A.COD_TIPDOC_CLIENTE,
                      A.COD_TIPDOC_BENEFICIARIO,
                      A.FLG_NOTACREDITO,
                      A.FLG_FACTURA_LOCAL,
                      A.IMP_MINIMO,
                      A.IMP_PRIMERA_COMPRA,
                      A.FLG_TARJETA,
                      A.FLG_TIPO_VALOR,
                      A.IMP_VALOR,
                      A.FLG_TIPO_PRECIO,
                      A.FLG_TIPO_PRECIO_LOCAL,
                      A.COD_LOCAL_REF,
                      A.DES_OBSERVACION,
                      A.FLG_ATENCION_TODOS_LOCALES,
                      A.FLG_COBRO_TODOS_LOCALES,
                      A.FLG_ACTIVO,
                      A.COD_USUARIO,
                      A.FCH_REGISTRA,
                      A.COD_USUARIO_ACTUALIZA,
                      A.FCH_ACTUALIZA,
                      A.FLG_REPARTIDOR,
                      A.FLG_MEDICO,
                      A.FLG_REPARTIDOR,
                      A.NUM_DOC_FLG_KDX,
                      A.FLG_TIPO_PAGO,
                      B.FLG_TIPO_JURIDICA,
                      B.NUM_DOCUMENTO_ID, -- SE AGGREGO PARA EFECTOS DE GRABACION DE CONVENIO 04/02/2008 Por Cristhian Rueda
                      A.FLG_VALIDA_LINCRE_BENEF,
                      A.FLG_RECETA,
                      A.FLG_DIAGNOSTICO,
                      A.COD_CONVENIO_GLM,
                      B.DES_RAZON_SOCIAL,
                      A.FLG_IMPRIME_IMPORTES,    -- 30/12/2008
                      A.FLG_PRECIO_MENOR,        -- 30/12/2008
                      A.FLG_PRECIO_DEDUCIBLE,     -- 30/12/2008
                      A.DES_IMPRESION,
                      A.FCH_IMPRESION_INI,
                      A.FCH_IMPRESION_FIN ,
                      FLG_IMPRIME_COPAGO,
                       A.NUM_NIVELES,
                       A.NUM_MAX_BENEF,
                       A.MTO_MAX_LINEA_COMPRA,
                       A.FLG_PER_ANU_DOC,
                       A.FLG_PER_NOT_CRE,
                       A.FLG_AFILIACION_ACT,
                   A.FLG_ESCALA_UNICA,
                   A.COD_EJECUTIVO,
                   A.COD_TRAMA,
                   A.DES_DESTINATARIO_TRAMA,
                   A.FLG_PLAN_VITAL,
                   A.CTD_MAX_UND_PRODUCTO,
                   A.COD_CONCEPTO,
                   a.flg_ing_beneficiario   ,
                   nvl(a.cod_cliente_sap_bolsa,' ') cod_cliente_sap_bolsa
                 FROM CMR.MAE_CONVENIO       A,
                      CMR.MAE_CLIENTE        B,
                      CMR.MAE_CLASE_CONVENIO C
                WHERE A.COD_CLIENTE = B.COD_CLIENTE
                  AND UPPER(A.DES_CONVENIO) LIKE
                      UPPER(A_COD_CONVENIO) || '%'
                  AND A.COD_CLASE_CONVENIO = C.COD_CLASE_CONVENIO
                ORDER BY DECODE(A_ORDEN, 1, A.DES_CONVENIO, A.COD_CONVENIO);
         END IF;
      END IF;
      RETURN C_CURSOR;
   END FN_LISTA;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   /*===============================================================================
                   ACTUALIZA UN REGISTRO EN LA TABLA CMR.MAE_CONVENIO
   ===============================================================================*/
    PROCEDURE SP_GRABA
   /*1*/
   (A_CIA CMR.MAE_CONVENIO.CIA%TYPE,
    /*2*/
    A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
    /*3*/
    A_COD_CLIENTE CMR.MAE_CONVENIO.COD_CLIENTE%TYPE,
    /*4*/
    A_COD_CLASE_CONVENIO CMR.MAE_CONVENIO.COD_CLASE_CONVENIO%TYPE,
    /*5*/
    A_COD_PETITORIO CMR.MAE_CONVENIO.COD_PETITORIO%TYPE,
    /*6*/
    A_DES_CONVENIO CMR.MAE_CONVENIO.DES_CONVENIO%TYPE,
    /*7*/
    A_FLG_TIPO_CONVENIO CMR.MAE_CONVENIO.FLG_TIPO_CONVENIO%TYPE,
    /*8*/
    A_PCT_BENEFICIARIO CMR.MAE_CONVENIO.PCT_BENEFICIARIO%TYPE,
    /*9*/
    A_PCT_EMPRESA CMR.MAE_CONVENIO.PCT_EMPRESA%TYPE,
    /*10*/
    A_IMP_LINEA_CREDITO CMR.MAE_CONVENIO.IMP_LINEA_CREDITO%TYPE,
    /*11*/
    A_FLG_POLITICA CMR.MAE_CONVENIO.FLG_POLITICA%TYPE,
    /*12*/
    A_FLG_PETITORIO CMR.MAE_CONVENIO.FLG_PETITORIO%TYPE,
    /*13*/
    A_FLG_PERIODO_VALIDEZ CMR.MAE_CONVENIO.FLG_PERIODO_VALIDEZ%TYPE,
    /*14*/
    A_FCH_INICIO CMR.MAE_CONVENIO.FCH_INICIO%TYPE,
    /*15*/
    A_FCH_FIN CMR.MAE_CONVENIO.FCH_FIN%TYPE,
    /*16*/
    A_FLG_RENOVACION_AUTO CMR.MAE_CONVENIO.FLG_RENOVACION_AUTO%TYPE,
    /*17*/
    A_FLG_ATENCION_LOCAL CMR.MAE_CONVENIO.FLG_ATENCION_LOCAL%TYPE,
    /*18*/
    A_FLG_ATENCION_DELIVERY CMR.MAE_CONVENIO.FLG_ATENCION_DELIVERY%TYPE,
    /*19*/
    A_IMP_MINIMO_DELIVERY CMR.MAE_CONVENIO.IMP_MINIMO_DELIVERY%TYPE,
    /*20*/
    A_FLG_TIPO_PERIODO CMR.MAE_CONVENIO.FLG_TIPO_PERIODO%TYPE,
    /*21*/
    A_DIA_CORTE CMR.MAE_CONVENIO.DIA_CORTE%TYPE,
    /*22*/
    A_CTD_CANCELACION CMR.MAE_CONVENIO.CTD_CANCELACION%TYPE,
    /*23*/
    A_FLG_BENEFICIARIOS CMR.MAE_CONVENIO.FLG_BENEFICIARIOS%TYPE,
    /*24*/
    A_COD_TIPDOC_CLIENTE CMR.MAE_CONVENIO.COD_TIPDOC_CLIENTE%TYPE,
    /*25*/
    A_COD_TIPDOC_BENEFICIARIO CMR.MAE_CONVENIO.COD_TIPDOC_BENEFICIARIO%TYPE,
    /*26*/
    A_FLG_NOTACREDITO CMR.MAE_CONVENIO.FLG_NOTACREDITO%TYPE,
    /*27*/
    A_FLG_FACTURA_LOCAL CMR.MAE_CONVENIO.FLG_FACTURA_LOCAL%TYPE,
    /*28*/
    A_IMP_MINIMO CMR.MAE_CONVENIO.IMP_MINIMO%TYPE,
    /*29*/
    A_IMP_PRIMERA_COMPRA CMR.MAE_CONVENIO.IMP_PRIMERA_COMPRA%TYPE,
    /*30*/
    A_FLG_TARJETA CMR.MAE_CONVENIO.FLG_TARJETA%TYPE,
    /*31*/
    A_FLG_TIPO_VALOR CMR.MAE_CONVENIO.FLG_TIPO_VALOR%TYPE,
    /*32*/
    A_IMP_VALOR CMR.MAE_CONVENIO.IMP_VALOR%TYPE,
    /*33*/
    A_FLG_TIPO_PRECIO CMR.MAE_CONVENIO.FLG_TIPO_PRECIO%TYPE,
    /*34*/
    A_FLG_TIPO_PRECIO_LOCAL CMR.MAE_CONVENIO.FLG_TIPO_PRECIO_LOCAL%TYPE,
    /*35*/
    A_COD_LOCAL_REF CMR.MAE_CONVENIO.COD_LOCAL_REF%TYPE,
    /*36*/
    A_DES_OBSERVACION CMR.MAE_CONVENIO.DES_OBSERVACION%TYPE,
    /*37*/
    A_FLG_ATENCION_TODOS_LOCALES CMR.MAE_CONVENIO.FLG_ATENCION_TODOS_LOCALES%TYPE,
    /*38*/
    A_FLG_COBRO_TODOS_LOCALES CMR.MAE_CONVENIO.FLG_COBRO_TODOS_LOCALES%TYPE,
    /*39*/
    A_FLG_ACTIVO CMR.MAE_CONVENIO.FLG_ACTIVO%TYPE,
    /*40*/
    A_COD_USUARIO CMR.MAE_CONVENIO.COD_USUARIO%TYPE,
    /*41*/
    A_FLG_TIPO_PAGO CMR.MAE_CONVENIO.FLG_TIPO_PAGO%TYPE,
    -----------------------------------------------------------------------------------------------
    /*42*/
    A_CAD_COD_VERIF CHAR,
    /*43*/
    A_CAD_BTL CHAR,
    /*44*/
    A_CAD_FORMA_PAGO CHAR,
    /*45*/
    A_CAD_FORMA_PAGO_HIJO CHAR,
    /*46*/
    A_CAD_TIPO_VENTA CHAR,
    /*47*/
    A_CAD_FLG_RETENCION_DOC CHAR,
    ------------------------------------------------------------------------------------------------
    /*48*/
    A_FLG_MEDICO CHAR,
    /*49*/
    A_CAD_COD_MEDICO CHAR,
    /*50*/
    A_NUM_DOC_FLG_KDX CHAR,
    /*51*/
    A_CAD_PCT_BENEF_X_LOCAL CHAR,
    /*52*/
    A_CAD_PCT_EMPRE_X_LOCAL CHAR,
    /*53*/
    A_FLG_REPARTIDOR CHAR,
    /*54*/
    A_CAD_TIPO_DATO_ADIC CHAR,
    /*55*/
    A_CAD_PETITORIO CHAR,
    /*56*/
    A_FLG_VALIDA_LINCRE_BENEF CHAR,
    /*57*/
    A_CAD_LOCAL_RECETA CHAR,
    /*58*/
    A_FLG_RECETA       CHAR,
    A_COD_CONVENIO_GLM CNV.M_CONVENIOS.COD_CONVENIO%TYPE DEFAULT NULL,

    A_CAD_ESCALA1          VARCHAR2 DEFAULT NULL,
    A_CAD_ESCALA2          VARCHAR2 DEFAULT NULL,
    A_CAD_ESCALA3          VARCHAR2 DEFAULT NULL,
    A_CAD_ESCALA4          VARCHAR2 DEFAULT NULL,
    A_CAD_ESCALA5          VARCHAR2 DEFAULT NULL,
    A_FLG_IMPRIME_IMP      VARCHAR2 DEFAULT NULL,
    A_FLG_PRECIO_MENOR     VARCHAR2 DEFAULT NULL,
    A_FLG_PRECIO_DEDUCIBLE VARCHAR2 DEFAULT NULL,
    A_DES_IMPRESION        VARCHAR2 DEFAULT NULL,
    A_FCH_IMPRESION_INI    VARCHAR2 DEFAULT NULL,
    A_FCH_IMPRESION_FIN    VARCHAR2 DEFAULT NULL,
    A_FLG_IMPRESION        VARCHAR2 DEFAULT 1,
--PARA EL CLUB BTL
    A_NUM_NIVELES               CMR.MAE_CONVENIO.NUM_NIVELES%TYPE DEFAULT NULL,
    A_NUM_MAX_BENEF             CMR.MAE_CONVENIO.NUM_MAX_BENEF%TYPE DEFAULT NULL,
    A_MTO_MAX_LINEA_COMPRA     CMR.MAE_CONVENIO.MTO_MAX_LINEA_COMPRA%TYPE DEFAULT NULL,
    A_FLG_PER_ANU_DOC           CMR.MAE_CONVENIO.FLG_PER_ANU_DOC%TYPE DEFAULT NULL,
    A_FLG_PER_NOT_CRE           CMR.MAE_CONVENIO.FLG_PER_NOT_CRE%TYPE DEFAULT NULL,
    A_FLG_AFILIACION_ACT       CMR.MAE_CONVENIO.FLG_AFILIACION_ACT%TYPE DEFAULT NULL,
    A_CAD_NIVEL                VARCHAR2 DEFAULT NULL,
    A_CAD_imp_minimo           VARCHAR2 DEFAULT NULL,
    A_CAD_imp_maximo           VARCHAR2 DEFAULT NULL,
    A_CAD_flg_porcentaje       VARCHAR2 DEFAULT NULL,
    A_CAD_imp_monto            VARCHAR2 DEFAULT NULL,
    A_FLG_ESCALA_UNICA         INTEGER DEFAULT 0,
    --DATOS ADCIONALES PARA MAYOR CONTROL
    A_COD_EJECUTIVO             VARCHAR2 DEFAULT NULL,
    A_COD_TRAMA                 VARCHAR2 DEFAULT NULL,
    A_DESTINATARIO_TRAMA        VARCHAR2 DEFAULT NULL,
    -- INDICADOR CARGAR ARCHIVO PLAN VITAL 28/01/2010 CRUEDA
    A_FLG_PLAN_VITAL            VARCHAR2 DEFAULT NULL,
    -- MIGUEL LAGUNA PARAMETROS AFILIACION MAS SALUD 23/03/2010 MLAGUNA
    A_MAX_UND_PRODUCTO          INTEGER DEFAULT NULL,
    A_COD_CON_COMISION          CMR.MAE_CONVENIO.COD_CONCEPTO%TYPE DEFAULT NULL,
    A_MTO_LINEA_CRED_BASE       CMR.MAE_CONVENIO.MTO_LINEA_CRED_BASE%TYPE DEFAULT 0,
    A_FLG_ING_BENEFICIARIO      CMR.MAE_CONVENIO.FLG_ING_BENEFICIARIO%TYPE DEFAULT 0,
    ---CCIEZA PARAMENTROS PARA EL ENVIO DE EMAIL DE PRODUCTOS AGOTADOS  25/08/2010
    A_CAD_ENVIO1               VARCHAR2 DEFAULT NULL,
    A_CAD_ENVIO2               VARCHAR2 DEFAULT NULL,
    A_CAD_ENVIO3               VARCHAR2 DEFAULT NULL,
    A_CAD_ENVIO4               VARCHAR2 DEFAULT NULL,
    A_CAD_ESCALA6              VARCHAR2 DEFAULT NULL,
    A_CAD_ESCALA7              VARCHAR2 DEFAULT NULL,
    A_cod_cliente_sap_bolsa    VARCHAR2 DEFAULT NULL
    )
   ------------------------------------------------------------------------------------------------

    IS
      V_ITEM                  INTEGER;
      C_COD_CONVENIO          CMR.MAE_CONVENIO.COD_CONVENIO%TYPE;
      V_CAD_DOC_VERIF         CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_BTL               CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_FORMA_PAGO        CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_FORMA_PAGO_HIJO   CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_FLG_RETENCION_DOC CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_COD_MEDICO        CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_PCT_EMPRE_X_LOCAL CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_PCT_BENEF_X_LOCAL CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_TIPO_DATO_ADIC    CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_PETITORIO         CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_LOCAL_RECETA      CMR.PKG_UTIL.TIPO_ARREGLO;
      V_EST_CONVENIO_GLM      CHAR;
      V_CAD_ESCALA1           CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_ESCALA2           CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_ESCALA3           CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_ESCALA4           CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_ESCALA5           CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_ESCALA6           CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_ESCALA7           CMR.PKG_UTIL.TIPO_ARREGLO;

      V_CAD_ENVIO1            CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_ENVIO2            CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_ENVIO3            CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_ENVIO4            CMR.PKG_UTIL.TIPO_ARREGLO;

   BEGIN

      IF A_CIA IS NULL THEN
         RAISE_APPLICATION_ERROR(-20000, 'EL CODIGO DE COMPANIA NO PUEDE SER NULO.');
      END IF;
      ----------------------------------------------------------------------------------
      --1 Fija           (por lo general)
      --0 Variable
      IF A_FLG_POLITICA = '1' THEN
         --FIJA
         --0=Porcentaje         --1=Importe
         IF A_FLG_TIPO_VALOR = '1' THEN
            --IMPORTE
            RAISE_APPLICATION_ERROR(-20000,
                                    'DEBE INDICAR SOLO LOS PORCENTAJES, NO IMPORTES.');
         END IF;
      END IF;
      ----------------------------------------------------------------------------------
      IF A_FLG_TIPO_PAGO = '0' THEN
         IF TO_NUMBER(A_PCT_BENEFICIARIO) < 0 OR
            TO_NUMBER(A_PCT_EMPRESA) < 0 THEN
            RAISE_APPLICATION_ERROR(-20000,
                                    CHR(13) ||
                                    'VERIFIQUE LOS PORCENTAJES DE EMPRESA Y BENEFICIARIO.');
         END IF;
         IF TO_NUMBER(A_PCT_BENEFICIARIO) + TO_NUMBER(A_PCT_EMPRESA) <> 100 THEN
            RAISE_APPLICATION_ERROR(-20000,
                                    'VERIFIQUE LOS PORCENTAJES DE EMPRESA Y BENEFICIARIO. DEBEN SUMAR 100% ');
         END IF;
      END IF;
      --Agregado por Arturo Escate el 23/01/2008, para la sincronizacin con el sistema antiguio
      --Quitado por Arturo Escate el 13/08/2009, para evitar creen el usuario en el sistema antiguo
      /*IF A_COD_CONVENIO_GLM IS NULL THEN
         RAISE_APPLICATION_ERROR(-20000,
                                 'DEBE INGRESAR EL CODIGO DEL SISTEMA ANTIGUO.');
      END IF;
      BEGIN
         SELECT FLG_ESTADO
           INTO V_EST_CONVENIO_GLM
           FROM CNV.M_CONVENIOS
          WHERE COD_CONVENIO = LPAD(A_COD_CONVENIO_GLM, 8, '0');
      EXCEPTION
         WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20000,
                                    'EL CODIGO DE CONVENIO '||A_COD_CONVENIO_GLM||' NO EXISTE EN EL ANTIGUO SISTEMA.');
         WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20000,
                                    'SE PRODUJO UN ERROR BUSCANDO EL CONVENIO EN EL SISTEMA ANTIGUO.' ||CHR(13)|| sqlerrm);
      END;
      IF V_EST_CONVENIO_GLM = '0' THEN
         RAISE_APPLICATION_ERROR(-20000,
                                 'EL CONVENIO '||A_COD_CONVENIO_GLM||' SE ENCUENTRA INACTIVO EN EL ANTIGUO SISTEMA.');
      END IF;
      */
      --1 Copago  Eld e 100% se tomaria como copago
      --2 Descuento (Dscto. Automatico) --> 100% Benef.
      ----------------------------------------------------------------------------------

      IF A_COD_CONVENIO IS NULL THEN

         BEGIN
            /*
            C_COD_CONVENIO := CMR.FN_DEV_PROX_SEC('CMR','MAE_CONVENIO');
            */
            SELECT LPAD(TO_CHAR(TO_NUMBER(NVL(MAX(TO_NUMBER(A.COD_CONVENIO)),0)) + 1),10,'0')
              INTO C_COD_CONVENIO
              FROM CMR.MAE_CONVENIO A
             WHERE A.CIA = A_CIA;
         EXCEPTION
            WHEN OTHERS THEN
               RAISE_APPLICATION_ERROR(-20000,
                                       'SE PRODUJO UN ERROR AL DETERMINAR LA SECUENCIA DEL CONVENIO.' ||CHR(13)||
                                       SQLERRM);
         END;
         --- 1 ---
         BEGIN
            INSERT INTO CMR.MAE_CONVENIO
               (CIA,
                COD_CONVENIO,
                COD_CLIENTE,
                COD_CLASE_CONVENIO,
                COD_PETITORIO,
                DES_CONVENIO,
                FLG_TIPO_CONVENIO,
                PCT_BENEFICIARIO,
                PCT_EMPRESA,
                IMP_LINEA_CREDITO,
                FLG_POLITICA,
                FLG_PETITORIO,
                FLG_PERIODO_VALIDEZ,
                FCH_INICIO,
                FCH_FIN,
                FLG_RENOVACION_AUTO,
                FLG_ATENCION_LOCAL,
                FLG_ATENCION_DELIVERY,
                IMP_MINIMO_DELIVERY,
                FLG_TIPO_PERIODO,
                DIA_CORTE,
                CTD_CANCELACION,
                FLG_BENEFICIARIOS,
                COD_TIPDOC_CLIENTE,
                COD_TIPDOC_BENEFICIARIO,
                FLG_NOTACREDITO,
                FLG_FACTURA_LOCAL,
                IMP_MINIMO,
                IMP_PRIMERA_COMPRA,
                FLG_TARJETA,
                FLG_TIPO_VALOR,
                IMP_VALOR,
                FLG_TIPO_PRECIO,
                FLG_TIPO_PRECIO_LOCAL,
                COD_LOCAL_REF,
                DES_OBSERVACION,
                FLG_ATENCION_TODOS_LOCALES,
                FLG_COBRO_TODOS_LOCALES,
                FLG_ACTIVO,
                COD_USUARIO,
                FCH_REGISTRA,
                COD_USUARIO_ACTUALIZA,
                FCH_ACTUALIZA,
                FLG_TIPO_PAGO,
                FLG_MEDICO,
                NUM_DOC_FLG_KDX,
                FLG_REPARTIDOR,
                FLG_VALIDA_LINCRE_BENEF,
                FLG_RECETA,
                FLG_DIAGNOSTICO,
                COD_CONVENIO_GLM,
                FLG_IMPRIME_IMPORTES,
                FLG_PRECIO_MENOR,
                FLG_PRECIO_DEDUCIBLE,
                DES_IMPRESION,
                FCH_IMPRESION_INI,
                FCH_IMPRESION_FIN,
                FLG_IMPRIME_COPAGO,
                NUM_NIVELES,
                NUM_MAX_BENEF,
                MTO_MAX_LINEA_COMPRA,
                FLG_PER_ANU_DOC           ,
                FLG_PER_NOT_CRE           ,
                FLG_AFILIACION_ACT       ,
                FLG_ESCALA_UNICA         ,
                COD_EJECUTIVO            ,
                COD_TRAMA                ,
                DES_DESTINATARIO_TRAMA   ,
                FLG_PLAN_VITAL           ,
                CTD_MAX_UND_PRODUCTO     ,
                COD_CONCEPTO             ,
                MTO_LINEA_CRED_BASE      ,
                FLG_ING_BENEFICIARIO,
                cod_cliente_sap_bolsa
                )
            VALUES
               (A_CIA,
                C_COD_CONVENIO,
                A_COD_CLIENTE,
                A_COD_CLASE_CONVENIO,
                A_COD_PETITORIO,
                A_DES_CONVENIO,
                A_FLG_TIPO_CONVENIO,
                A_PCT_BENEFICIARIO,
                A_PCT_EMPRESA,
                A_IMP_LINEA_CREDITO,
                A_FLG_POLITICA,
                A_FLG_PETITORIO,
                A_FLG_PERIODO_VALIDEZ,
                A_FCH_INICIO,
                A_FCH_FIN,
                A_FLG_RENOVACION_AUTO,
                A_FLG_ATENCION_LOCAL,
                A_FLG_ATENCION_DELIVERY,
                A_IMP_MINIMO_DELIVERY,
                A_FLG_TIPO_PERIODO,
                A_DIA_CORTE,
                A_CTD_CANCELACION,
                A_FLG_BENEFICIARIOS,
                A_COD_TIPDOC_CLIENTE,
                A_COD_TIPDOC_BENEFICIARIO,
                A_FLG_NOTACREDITO,
                A_FLG_FACTURA_LOCAL,
                A_IMP_MINIMO,
                A_IMP_PRIMERA_COMPRA,
                A_FLG_TARJETA,
                A_FLG_TIPO_VALOR,
                A_IMP_VALOR,
                A_FLG_TIPO_PRECIO,
                A_FLG_TIPO_PRECIO_LOCAL,
                A_COD_LOCAL_REF,
                A_DES_OBSERVACION,
                A_FLG_ATENCION_TODOS_LOCALES,
                A_FLG_COBRO_TODOS_LOCALES,
                A_FLG_ACTIVO,
                A_COD_USUARIO,
                SYSDATE,
                NULL,
                NULL,
                A_FLG_TIPO_PAGO,
                A_FLG_MEDICO,
                A_NUM_DOC_FLG_KDX,
                A_FLG_REPARTIDOR,
                A_FLG_VALIDA_LINCRE_BENEF,
                A_FLG_RECETA,
                A_FLG_RECETA,
                LPAD(A_COD_CONVENIO_GLM, 8, '0'),
                A_FLG_IMPRIME_IMP,
                A_FLG_PRECIO_MENOR,
                A_FLG_PRECIO_DEDUCIBLE,
                A_DES_IMPRESION        ,
                A_FCH_IMPRESION_INI    ,
                A_FCH_IMPRESION_FIN    ,
                A_FLG_IMPRESION        ,

                A_NUM_NIVELES               ,
                A_NUM_MAX_BENEF             ,
                A_MTO_MAX_LINEA_COMPRA     ,
                A_FLG_PER_ANU_DOC           ,
                A_FLG_PER_NOT_CRE           ,
                A_FLG_AFILIACION_ACT       ,
                A_FLG_ESCALA_UNICA         ,
                A_COD_EJECUTIVO            ,
                A_COD_TRAMA                ,
                A_DESTINATARIO_TRAMA       ,
                A_FLG_PLAN_VITAL           ,
                A_MAX_UND_PRODUCTO         ,
                A_COD_CON_COMISION         ,
                NVL(A_MTO_LINEA_CRED_BASE,0),
                NVL(A_FLG_ING_BENEFICIARIO,0),
                A_cod_cliente_sap_bolsa
                );
         EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN
               RAISE_APPLICATION_ERROR(-20000,
                                       'EL CODIGO DE CONVENIO YA EXISTE' ||CHR(13)||
                                       SQLERRM);
            WHEN OTHERS THEN
               RAISE_APPLICATION_ERROR(-20000,
                                       'SE PRODUJO UN ERROR AL REGISTRAR EL CONVENIO ' ||CHR(13)||
                                       SQLERRM);
         END;
      ELSE
         C_COD_CONVENIO := A_COD_CONVENIO;
         IF C_COD_CONVENIO IS NULL THEN
            RAISE_APPLICATION_ERROR(-20000,
                                    'CODIGO DE CONVENIO ES NULO AL INTENTAR ACTUALIZAR');
         END IF;
         --- 2 ---
         BEGIN
            UPDATE CMR.MAE_CONVENIO
               SET COD_CLIENTE                = A_COD_CLIENTE,
                   COD_CLASE_CONVENIO         = A_COD_CLASE_CONVENIO,
                   COD_PETITORIO              = A_COD_PETITORIO,
                   DES_CONVENIO               = A_DES_CONVENIO,
                   FLG_TIPO_CONVENIO          = A_FLG_TIPO_CONVENIO,
                   PCT_BENEFICIARIO           = A_PCT_BENEFICIARIO,
                   PCT_EMPRESA                = A_PCT_EMPRESA,
                   IMP_LINEA_CREDITO          = A_IMP_LINEA_CREDITO,
                   FLG_POLITICA               = A_FLG_POLITICA,
                   FLG_PETITORIO              = A_FLG_PETITORIO,
                   FLG_PERIODO_VALIDEZ        = A_FLG_PERIODO_VALIDEZ,
                   FCH_INICIO                 = A_FCH_INICIO,
                   FCH_FIN                    = A_FCH_FIN,
                   FLG_RENOVACION_AUTO        = A_FLG_RENOVACION_AUTO,
                   FLG_ATENCION_LOCAL         = A_FLG_ATENCION_LOCAL,
                   FLG_ATENCION_DELIVERY      = A_FLG_ATENCION_DELIVERY,
                   IMP_MINIMO_DELIVERY        = A_IMP_MINIMO_DELIVERY,
                   FLG_TIPO_PERIODO           = A_FLG_TIPO_PERIODO,
                   DIA_CORTE                  = A_DIA_CORTE,
                   CTD_CANCELACION            = A_CTD_CANCELACION,
                   FLG_BENEFICIARIOS          = A_FLG_BENEFICIARIOS,
                   COD_TIPDOC_CLIENTE         = A_COD_TIPDOC_CLIENTE,
                   COD_TIPDOC_BENEFICIARIO    = A_COD_TIPDOC_BENEFICIARIO,
                   FLG_NOTACREDITO            = A_FLG_NOTACREDITO,
                   FLG_FACTURA_LOCAL          = A_FLG_FACTURA_LOCAL,
                   IMP_MINIMO                 = A_IMP_MINIMO,
                   IMP_PRIMERA_COMPRA         = A_IMP_PRIMERA_COMPRA,
                   FLG_TARJETA                = A_FLG_TARJETA,
                   FLG_TIPO_VALOR             = A_FLG_TIPO_VALOR,
                   IMP_VALOR                  = A_IMP_VALOR,
                   FLG_TIPO_PRECIO            = A_FLG_TIPO_PRECIO,
                   FLG_TIPO_PRECIO_LOCAL      = A_FLG_TIPO_PRECIO_LOCAL,
                   COD_LOCAL_REF              = A_COD_LOCAL_REF,
                   DES_OBSERVACION            = A_DES_OBSERVACION,
                   FLG_ATENCION_TODOS_LOCALES = A_FLG_ATENCION_TODOS_LOCALES,
                   FLG_COBRO_TODOS_LOCALES    = A_FLG_COBRO_TODOS_LOCALES,
                   FLG_ACTIVO                 = A_FLG_ACTIVO,
                   COD_USUARIO_ACTUALIZA      = A_COD_USUARIO,
                   FCH_ACTUALIZA              = SYSDATE,
                   NUM_DOC_FLG_KDX            = A_NUM_DOC_FLG_KDX,
                   FLG_MEDICO                 = A_FLG_MEDICO,
                   FLG_REPARTIDOR             = A_FLG_REPARTIDOR,
                   FLG_VALIDA_LINCRE_BENEF    = A_FLG_VALIDA_LINCRE_BENEF,
                   FLG_RECETA                 = A_FLG_RECETA,
                   FLG_DIAGNOSTICO            = A_FLG_RECETA,
                   FLG_TIPO_PAGO              = A_FLG_TIPO_PAGO,
                   COD_CONVENIO_GLM           = LPAD(A_COD_CONVENIO_GLM,
                                                     8,
                                                     '0'),
                   FLG_IMPRIME_IMPORTES       = A_FLG_IMPRIME_IMP,
                   FLG_PRECIO_MENOR           = A_FLG_PRECIO_MENOR,
                   FLG_PRECIO_DEDUCIBLE       = A_FLG_PRECIO_DEDUCIBLE,
                   DES_IMPRESION              = A_DES_IMPRESION        ,
                   FCH_IMPRESION_INI          = A_FCH_IMPRESION_INI    ,
                   FCH_IMPRESION_FIN          = A_FCH_IMPRESION_FIN    ,
                   FLG_IMPRIME_COPAGO         = A_FLG_IMPRESION        ,
                   NUM_NIVELES                = A_NUM_NIVELES,
                   NUM_MAX_BENEF              = A_NUM_MAX_BENEF,
                   MTO_MAX_LINEA_COMPRA        = A_MTO_MAX_LINEA_COMPRA,
                   FLG_PER_ANU_DOC            = A_FLG_PER_ANU_DOC,
                   FLG_PER_NOT_CRE            = A_FLG_PER_NOT_CRE,
                   FLG_AFILIACION_ACT          = A_FLG_AFILIACION_ACT,
                   FLG_ESCALA_UNICA           = A_FLG_ESCALA_UNICA,
                   COD_EJECUTIVO              = NVL(A_COD_EJECUTIVO, COD_EJECUTIVO) ,
                   COD_TRAMA                  = NVL(A_COD_TRAMA, COD_TRAMA),
                   DES_DESTINATARIO_TRAMA     = NVL(A_DESTINATARIO_TRAMA,DES_DESTINATARIO_TRAMA),
                   FLG_PLAN_VITAL             = A_FLG_PLAN_VITAL,
                   CTD_MAX_UND_PRODUCTO       = A_MAX_UND_PRODUCTO,
                   COD_CONCEPTO               = A_COD_CON_COMISION,
                   MTO_LINEA_CRED_BASE        = NVL(A_MTO_LINEA_CRED_BASE,0),
                   FLG_ING_BENEFICIARIO       = NVL(A_FLG_ING_BENEFICIARIO,0),
                   cod_cliente_sap_bolsa      = A_cod_cliente_sap_bolsa

             WHERE COD_CONVENIO = A_COD_CONVENIO;

            IF SQL%NOTFOUND THEN
               RAISE_APPLICATION_ERROR(-20003,
                                       'NO SE PUDO ACTUALIZAR LA TABLA CMR.MAE_CONVENIO');
            END IF;
         EXCEPTION
            WHEN OTHERS THEN
               RAISE_APPLICATION_ERROR(-20000,
                                       'Error de Actualizacion convenio ' ||
                                       SQLERRM);
         END;
      END IF;
      ----------------------------------------------------------------------------------------------------------
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_COD_VERIF, V_CAD_DOC_VERIF, V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_FLG_RETENCION_DOC, V_CAD_FLG_RETENCION_DOC, V_ITEM);

      ---- Inserta a los documentos ----
      BEGIN
         BEGIN
            DELETE FROM BTLPROD.REL_CONVENIO_DOCUM_VERIF A
             WHERE A.CIA = A_CIA
               AND A.COD_CONVENIO = A_COD_CONVENIO;
            IF SQL%NOTFOUND THEN
               NULL;
            END IF;
         END;
         FOR I IN 1 .. V_ITEM LOOP
            IF V_CAD_DOC_VERIF(I) IS NULL THEN
               RAISE_APPLICATION_ERROR(-20000,
                                       'EL DOCUMENTO DE VERIFICACION ' || I ||
                                       ' NO PUEDE SER NULO.');
            END IF;
            IF V_CAD_FLG_RETENCION_DOC(I) IS NULL THEN
               RAISE_APPLICATION_ERROR(-20000,
                                       'EL INDICADOR DE RETENCION ' || I ||
                                       ' NO PUEDE SER NULO.');
            END IF;
            BEGIN
               INSERT INTO BTLPROD.REL_CONVENIO_DOCUM_VERIF
                  (COD_CONVENIO,
                   FLG_RETENCION,
                   COD_DOCUMENTO_VERIFICACION,
                   FLG_ACTIVO,
                   CIA)
               VALUES
                  (C_COD_CONVENIO,
                   V_CAD_FLG_RETENCION_DOC(I),
                   V_CAD_DOC_VERIF(I),
                   '1',
                   A_CIA);
            EXCEPTION
               WHEN DUP_VAL_ON_INDEX THEN
                  RAISE_APPLICATION_ERROR(-20000,
                                          'EL DOCUMENTO DE VERIFICACION '||V_CAD_DOC_VERIF(I) ||' YA SE ENCUENTRA REGISTRADO.'||CHR(13)||
                                          SQLERRM);
               WHEN OTHERS THEN
                  RAISE_APPLICATION_ERROR(-20000,
                                          'SE PRODUJO UN ERROR AL REGISTRAR LOS DOCUMENTOS DE VERIFICACION.' || CHR(13)||
                                          SQLERRM);
            END;
         END LOOP;
      END;
      -----------------------------------------------------------------------------------------
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_BTL, V_CAD_BTL, V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_PCT_BENEF_X_LOCAL,
                              V_CAD_PCT_BENEF_X_LOCAL,
                              V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_PCT_EMPRE_X_LOCAL,
                              V_CAD_PCT_EMPRE_X_LOCAL,
                              V_ITEM);
      -----------------------------------------------------------------------------------------
      ---- Inserta las BTL's ----
      BEGIN
         BEGIN
            DELETE FROM BTLPROD.REL_CONVENIO_LOCAL A
             WHERE A.CIA = A_CIA
               AND A.COD_CONVENIO = A_COD_CONVENIO;
            IF SQL%NOTFOUND THEN
               NULL;
            END IF;
         END;
         IF V_ITEM = 0 AND A_FLG_ATENCION_TODOS_LOCALES = '0' THEN
            RAISE_APPLICATION_ERROR(-20000,
                                    'DEBE INDICAR A QUE LOCALES SE APLICA EL CONVENIO.');
         END IF;
         FOR I IN 1 .. V_ITEM LOOP
            BEGIN
               INSERT INTO BTLPROD.REL_CONVENIO_LOCAL
                  (CIA,
                   COD_LOCAL,
                   COD_CONVENIO,
                   FLG_ACTIVO,
                   PCT_BENEFICIARIO,
                   PCT_EMPRESA)
               VALUES
                  (A_CIA,
                   V_CAD_BTL(I),
                   C_COD_CONVENIO,
                   '1',
                   V_CAD_PCT_BENEF_X_LOCAL(I),
                   V_CAD_PCT_EMPRE_X_LOCAL(I));
            EXCEPTION
               WHEN OTHERS THEN
                  RAISE_APPLICATION_ERROR(-20000,
                                          'SE PRODUJO UN ERROR INSERTANDO LOS LOCALES.' ||CHR(13)||
                                          SQLERRM);
            END;
         END LOOP;
      END;
      -----------------------------------------------------------------------------------------
      ---- Inserta las BTL's de receta ----
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_LOCAL_RECETA,
                              V_CAD_LOCAL_RECETA,
                              V_ITEM);
      BEGIN
         BEGIN
            DELETE FROM BTLPROD.REL_CONVENIO_LOCAL_RECETA A
             WHERE A.CIA = A_CIA
               AND A.COD_CONVENIO = A_COD_CONVENIO;
            IF SQL%NOTFOUND THEN
               NULL;
            END IF;
         END;

         FOR I IN 1 .. V_ITEM LOOP
            BEGIN
               --               CIA,  COD_LOCAL, COD_CONVENIO ,  COD_USUARIO, FCH_REGISTRA, FLG_ACTIVO
               INSERT INTO BTLPROD.REL_CONVENIO_LOCAL_RECETA
                  (CIA,
                   COD_LOCAL,
                   COD_CONVENIO,
                   FLG_ACTIVO,
                   FCH_REGISTRA,
                   COD_USUARIO)
               VALUES
                  (A_CIA,
                   V_CAD_LOCAL_RECETA(I),
                   C_COD_CONVENIO,
                   '1',
                   SYSDATE,
                   A_COD_USUARIO);
            EXCEPTION
               WHEN OTHERS THEN
                  RAISE_APPLICATION_ERROR(-20000,
                                          'SE PRODUJO UN ERROR INSERTANDO LAS SEDES.' ||CHR(13)||
                                          SQLERRM);
            END;
         END LOOP;
      END;

      -----------------------------------------------------------------------------------------
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_PETITORIO, V_CAD_PETITORIO, V_ITEM);
      -----------------------------------------------------------------------------------------
      ---- Inserta los Petitorios en BTLPROD.REL_PETITORIO_CONVENIO ----


      BEGIN
         BEGIN
            DELETE FROM BTLPROD.REL_PETITORIO_CONVENIO A
             WHERE A.CIA = A_CIA
               AND A.COD_CONVENIO = A_COD_CONVENIO;
            IF SQL%NOTFOUND THEN
               NULL;
            END IF;
         END;
         FOR I IN 1 .. V_ITEM LOOP
            BEGIN
               INSERT INTO BTLPROD.REL_PETITORIO_CONVENIO
                  (CIA, COD_CONVENIO, COD_PETITORIO, FLG_ACTIVO, NUM_ORDEN)
               VALUES
                  (A_CIA, C_COD_CONVENIO, V_CAD_PETITORIO(I), '1', I);
            EXCEPTION
               WHEN DUP_VAL_ON_INDEX THEN
                  RAISE_APPLICATION_ERROR(-20000,
                                          'EL PETITORIO ' ||V_CAD_PETITORIO(I) ||' YA SE ENCUENTRA REGISTRADO.'||CHR(13)||
                                          SQLERRM);
               WHEN OTHERS THEN
                  RAISE_APPLICATION_ERROR(-20000,
                                          'SE PRODUJO UN ERROR INSERTANDO EL PETITORIO ' ||V_CAD_PETITORIO(I)||CHR(13)||
                                          SQLERRM);
            END;
         END LOOP;
      END;
      -----------------------------------------------------------------------------------------
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_FORMA_PAGO, V_CAD_FORMA_PAGO, V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_FORMA_PAGO_HIJO,
                              V_CAD_FORMA_PAGO_HIJO,
                              V_ITEM);
      -----------------------------------------------------------------------------------------
      ---- Forma de Pago ( Padre , Hijo ) ----
      BEGIN
         BEGIN
            DELETE FROM BTLPROD.REL_FORMA_PAGO_CONVENIO A
             WHERE A.CIA = A_CIA
               AND A.COD_CONVENIO = A_COD_CONVENIO;
            IF SQL%NOTFOUND THEN
               NULL;
            END IF;
         END;
         FOR I IN 1 .. V_ITEM LOOP
            IF V_CAD_FORMA_PAGO(I) IS NULL THEN
               RAISE_APPLICATION_ERROR(-20000,
                                       'LA FORMA DE PAGO ' || I ||' NO PUEDE SER NULA.');
            END IF;
            BEGIN
               INSERT INTO BTLPROD.REL_FORMA_PAGO_CONVENIO
                  (COD_CONVENIO,
                   FLG_ACTIVO,
                   COD_FORMA_PAGO,
                   COD_HIJO,
                   FLG_TIPO,
                   CIA)
               VALUES
                  (C_COD_CONVENIO,
                   '1',
                   V_CAD_FORMA_PAGO(I),
                   V_CAD_FORMA_PAGO_HIJO(I),
                   '1',
                   A_CIA);
            EXCEPTION
               WHEN DUP_VAL_ON_INDEX THEN
                  RAISE_APPLICATION_ERROR(-20000,
                                          'LA FORMA DE PAGO ' ||
                                          V_CAD_FORMA_PAGO(I) || '-' ||
                                          V_CAD_FORMA_PAGO_HIJO(I) ||
                                          ' YA SE ENCUENTRA REGISTRADA');
               WHEN OTHERS THEN
                  RAISE_APPLICATION_ERROR(-20000,
                                          'SE PRODUJO UN ERROR INSERTANDO LA FORMA DE PAGO ' ||CHR(13)||
                                          SQLERRM);
            END;
         END LOOP;
      END;
      -----------------------------------------------------------------------------------------
      V_ITEM := 0;
      -----------------------------------------------------------------------------------------
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_TIPO_DATO_ADIC,
                              V_CAD_TIPO_DATO_ADIC,
                              V_ITEM);
      -----------------------------------------------------------------------------------------
      ---- Datos Adicionales ----
      BEGIN
         BEGIN
            DELETE FROM BTLPROD.REL_CONVENIO_TIPO_CAMPO A
             WHERE A.CIA = A_CIA
               AND A.COD_CONVENIO = C_COD_CONVENIO;
            IF SQL%NOTFOUND THEN
               NULL;
            END IF;
         END;
         FOR I IN 1 .. V_ITEM LOOP
            IF V_CAD_TIPO_DATO_ADIC(I) IS NULL THEN
               RAISE_APPLICATION_ERROR(-20000,
                                       'EL CODIGO DEL DOCUMENTO ADICIONAL ' || I ||
                                       ' NO PUEDE SER NULO.');
            END IF;
            BEGIN
               INSERT INTO BTLPROD.REL_CONVENIO_TIPO_CAMPO
                  (COD_CONVENIO,
                   COD_TIPO_CAMPO,
                   FLG_TIPO_DATO,
                   CTD_LONG_MAX,
                   CTD_LONG_MIN,
                   USUARIO,
                   FECHA,
                   CIA,
                   FLG_IMPRIME)
               VALUES
                  (C_COD_CONVENIO,
                   V_CAD_TIPO_DATO_ADIC(I),
                   '1',
                   50,
                   1,
                   A_COD_USUARIO,
                   SYSDATE,
                   A_CIA,
                   '1');
            EXCEPTION
               WHEN DUP_VAL_ON_INDEX THEN
                  RAISE_APPLICATION_ERROR(-20000,
                                          'EL DATO ADICIONAL ' ||
                                          A_CAD_TIPO_DATO_ADIC ||' YA SE ENCUENTRA REGISTRADO.'|| CHR(13) ||
                                          SQLERRM);
               WHEN OTHERS THEN
                  RAISE_APPLICATION_ERROR(-20000,
                                          'SE PRODUJO UN ERROR INSERTANDO LOS DATOS ADICIONALES.' ||CHR(13)||
                                          SQLERRM);
            END;
         END LOOP;
      END;
      -----------------------------------------------------------------------------------------
      --Autor: Juan Arturo Escate Espichan
      --Feche:04/08/2009
      --Proposito: Que se graben las escalas por niveles para el club BTL
      BTLPROD.PKG_CONVENIO.SP_GRABA_ESCALA_NIVEL(
                                                 C_COD_CONVENIO        ,
                                                  A_CAD_NIVEL           ,
                                                  A_CAD_imp_minimo      ,
                                                  A_CAD_imp_maximo      ,
                                                  A_CAD_flg_porcentaje  ,
                                                  A_CAD_imp_monto       ,
                                                  A_FLG_ESCALA_UNICA,
                                                  A_NUM_NIVELES
          );
      -----------------------------------------------------------------------------------------
      V_ITEM := 0;
      -----------------------------------------------------------------------------------------
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_ESCALA1, V_CAD_ESCALA1, V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_ESCALA2, V_CAD_ESCALA2, V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_ESCALA3, V_CAD_ESCALA3, V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_ESCALA4, V_CAD_ESCALA4, V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_ESCALA5, V_CAD_ESCALA5, V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_ESCALA6, V_CAD_ESCALA6, V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_ESCALA7, V_CAD_ESCALA7, V_ITEM);
      -----------------------------------------------------------------------------------------
      ---- Datos Adicionales ----
      BEGIN
         BEGIN
            DELETE FROM BTLPROD.REL_CONVENIO_ESCALA A
             WHERE A.COD_CONVENIO = C_COD_CONVENIO;
            IF SQL%NOTFOUND THEN
               NULL;
            END IF;
         END;
       --  FOR I IN 1 .. 5 LOOP
             FOR I IN 1 .. V_ITEM LOOP
                  IF V_CAD_ESCALA1(I) IS NULL THEN
                     RAISE_APPLICATION_ERROR(-20000,
                                             'Datos de la Escala ' || I ||
                                             ' no puede ser nulo ');
                  END IF;
                  IF V_CAD_ESCALA2(I) IS NULL THEN
                     RAISE_APPLICATION_ERROR(-20000,
                                             'Datos de la Escala ' || I ||
                                             ' no puede ser nulo ');
                  END IF;
                  IF V_CAD_ESCALA3(I) IS NULL THEN
                     RAISE_APPLICATION_ERROR(-20000,
                                             'Datos de la Escala ' || I ||
                                             ' no puede ser nulo ');
                  END IF;
                  IF V_CAD_ESCALA4(I) IS NULL THEN
                     RAISE_APPLICATION_ERROR(-20000,
                                             'Datos de la Escala ' || I ||
                                             ' no puede ser nulo ');
                  END IF;
                  IF V_CAD_ESCALA5(I) IS NULL THEN
                     RAISE_APPLICATION_ERROR(-20000,
                                             'Datos de la Escala ' || I ||
                                             ' no puede ser nulo ');
                  END IF;
                   IF V_CAD_ESCALA6(I) IS NULL THEN
                     RAISE_APPLICATION_ERROR(-20000,
                                             'Datos de la Escala ' || I ||
                                             ' no puede ser nulo ');
                  END IF;
                   IF V_CAD_ESCALA7(I) IS NULL THEN
                     RAISE_APPLICATION_ERROR(-20000,
                                             'Datos de la Escala ' || I ||
                                             ' no puede ser nulo ');
                  END IF;

                  BEGIN
                     INSERT INTO BTLPROD.REL_CONVENIO_ESCALA
                        (COD_CONVENIO,
                         IMP_MINIMO,
                         IMP_MAXIMO,
                         FLG_PORCENTAJE,
                         IMP_MONTO,
                         FLG_PRC_PUBLICO,
                         FLG_COASEGURO,
                         CTD_COASEGURO)
                     VALUES
                        (C_COD_CONVENIO,
                         V_CAD_ESCALA1(I),
                         V_CAD_ESCALA2(I),
                         REPLACE(V_CAD_ESCALA3(I),'-1','1'),
                         V_CAD_ESCALA4(I),
                         V_CAD_ESCALA5(I),
                         REPLACE(V_CAD_ESCALA6(I),'-1','1'),
                         V_CAD_ESCALA7(I)
                         );
                  EXCEPTION
                     WHEN DUP_VAL_ON_INDEX THEN
                        RAISE_APPLICATION_ERROR(-20000,
                                                'Insercion Duplicada. Total Reg Recibidos ' ||
                                                V_ITEM || '  ' ||
                                                A_CAD_TIPO_DATO_ADIC || CHR(13) ||
                                                SQLERRM);
                     WHEN OTHERS THEN
                        RAISE_APPLICATION_ERROR(-20000,
                                                'Error de Insercion de Datos de la Escala ' ||
                                                SQLERRM);
                  END;
             END LOOP;
         --END LOOP;
      END;
      -----------------------------------------------------------------------------------------
      V_ITEM := 0;
      -----------------------------------------------------------------------------------------
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_ENVIO1, V_CAD_ENVIO1, V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_ENVIO2, V_CAD_ENVIO2, V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_ENVIO3, V_CAD_ENVIO3, V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_ENVIO4, V_CAD_ENVIO4, V_ITEM);
      -----------------------------------------------------------------------------------------
      ---- Datos Envio Emails ----
      BEGIN
       --  FOR I IN 1 .. 5 LOOP
             FOR I IN 1 .. V_ITEM LOOP

                  BEGIN
                     UPDATE BTLPROD.REL_PETITORIO_CONVENIO
                        SET COD_FORMATO   = V_CAD_ENVIO3(I),
                            FLG_ENVIO     = REPLACE(V_CAD_ENVIO2(I),'-1','1'),
                            DES_EMAIL     = V_CAD_ENVIO4(I)
                      WHERE CIA           = A_CIA
                        AND COD_CONVENIO  = C_COD_CONVENIO
                        AND COD_PETITORIO = TRIM(V_CAD_ENVIO1(I));

                  EXCEPTION
                     WHEN NO_DATA_FOUND THEN
                        RAISE_APPLICATION_ERROR(-20000,
                                                'EL CODIGO DE PETITORIO '||V_CAD_ENVIO1(I)||' NO ENCONTRO EN EL SISTEMA.');

                     WHEN OTHERS THEN
                        RAISE_APPLICATION_ERROR(-20000,
                                                'Error al Actualizar de Datos de la Envios ' ||
                                                SQLERRM);
                  END;
             END LOOP;
      END;
      -----------------------------------------------------------------------------------------
     COMMIT;

   END SP_GRABA;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   PROCEDURE SP_ACTUALIZA_DATOS_CONTROL(
                                        /*1*/A_CIA CMR.MAE_CONVENIO.CIA%TYPE,
                                        /*2*/
                                        A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
                                        /*3*/
                                        A_IMP_LINEA_CREDITO CMR.MAE_CONVENIO.IMP_LINEA_CREDITO%TYPE,
                                        /*4*/
                                        A_FLG_PETITORIO CMR.MAE_CONVENIO.FLG_PETITORIO%TYPE,
                                        /*5*/
                                        A_FLG_RENOVACION_AUTO CMR.MAE_CONVENIO.FLG_RENOVACION_AUTO%TYPE,
                                        /*6*/
                                        A_FLG_TIPO_PERIODO CMR.MAE_CONVENIO.FLG_TIPO_PERIODO%TYPE,
                                        /*7*/
                                        A_DIA_CORTE CMR.MAE_CONVENIO.DIA_CORTE%TYPE,
                                        /*8*/
                                        A_CTD_CANCELACION CMR.MAE_CONVENIO.CTD_CANCELACION%TYPE,
                                        /*9*/
                                        A_COD_TIPDOC_CLIENTE CMR.MAE_CONVENIO.COD_TIPDOC_CLIENTE%TYPE,
                                        /*10*/
                                        A_COD_TIPDOC_BENEFICIARIO CMR.MAE_CONVENIO.COD_TIPDOC_BENEFICIARIO%TYPE,
                                        /*11*/
                                        A_FLG_NOTACREDITO CMR.MAE_CONVENIO.FLG_NOTACREDITO%TYPE,
                                        /*12*/
                                        A_FLG_FACTURA_LOCAL CMR.MAE_CONVENIO.FLG_FACTURA_LOCAL%TYPE,
                                        /*13*/
                                        A_IMP_MINIMO CMR.MAE_CONVENIO.IMP_MINIMO%TYPE,
                                        /*14*/
                                        A_IMP_PRIMERA_COMPRA CMR.MAE_CONVENIO.IMP_PRIMERA_COMPRA%TYPE,
                                        /*15*/
                                        A_FLG_TARJETA CMR.MAE_CONVENIO.FLG_TARJETA%TYPE,
                                        /*16*/
                                        A_FLG_TIPO_VALOR CMR.MAE_CONVENIO.FLG_TIPO_VALOR%TYPE,
                                        /*17*/
                                        A_FLG_TIPO_PRECIO CMR.MAE_CONVENIO.FLG_TIPO_PRECIO%TYPE,
                                        /*18*/
                                        A_FLG_TIPO_PRECIO_LOCAL CMR.MAE_CONVENIO.FLG_TIPO_PRECIO_LOCAL%TYPE,
                                        /*19*/
                                        A_COD_LOCAL_REF CMR.MAE_CONVENIO.COD_LOCAL_REF%TYPE,
                                        /*20*/
                                        A_COD_USUARIO CMR.MAE_CONVENIO.COD_USUARIO%TYPE,
                                        /*21*/
                                        A_NUM_DOC_FLG_KDX CHAR) AS
      ------------------------------------------------------------------------------------------------

   BEGIN
      IF A_CIA IS NULL THEN
         RAISE_APPLICATION_ERROR(-20000, 'EL CODIGO DE LA COMPANIA NO PUEDE SER NULO.');
      END IF;


      IF A_COD_CONVENIO IS NULL THEN
         RAISE_APPLICATION_ERROR(-20000,
                                 'EL CODIGO DE CONVENIO NO PUEDE SER NULO.');
      END IF;

      BEGIN

         UPDATE CMR.MAE_CONVENIO
            SET IMP_LINEA_CREDITO       = A_IMP_LINEA_CREDITO,
                FLG_PETITORIO           = A_FLG_PETITORIO,
                FLG_RENOVACION_AUTO     = A_FLG_RENOVACION_AUTO,
                FLG_TIPO_PERIODO        = A_FLG_TIPO_PERIODO,
                DIA_CORTE               = A_DIA_CORTE,
                CTD_CANCELACION         = A_CTD_CANCELACION,
                COD_TIPDOC_CLIENTE      = A_COD_TIPDOC_CLIENTE,
                COD_TIPDOC_BENEFICIARIO = A_COD_TIPDOC_BENEFICIARIO,
                FLG_NOTACREDITO         = A_FLG_NOTACREDITO,
                FLG_FACTURA_LOCAL       = A_FLG_FACTURA_LOCAL,
                IMP_MINIMO              = A_IMP_MINIMO,
                IMP_PRIMERA_COMPRA      = A_IMP_PRIMERA_COMPRA,
                FLG_TARJETA             = A_FLG_TARJETA,
                FLG_TIPO_VALOR          = A_FLG_TIPO_VALOR,
                FLG_TIPO_PRECIO         = A_FLG_TIPO_PRECIO,
                FLG_TIPO_PRECIO_LOCAL   = A_FLG_TIPO_PRECIO_LOCAL,
                COD_LOCAL_REF           = A_COD_LOCAL_REF,
                COD_USUARIO_ACTUALIZA   = A_COD_USUARIO,
                FCH_ACTUALIZA           = SYSDATE,
                NUM_DOC_FLG_KDX         = A_NUM_DOC_FLG_KDX
          WHERE COD_CONVENIO = A_COD_CONVENIO;

         IF SQL%NOTFOUND THEN
            RAISE_APPLICATION_ERROR(-20003,
                                    'NO SE PUDO ACTUALIZAR LA TABLA CMR.MAE_CONVENIO');
         END IF;
      EXCEPTION
         WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20000,
                                    'SE PRODUJO UN ERROR ACTUALIZANDO LOS DATOS DEL CONVENIO.' ||CHR(13)||
                                    SQLERRM);
      END;

      -----------------------------------------------------------------------------------------
      COMMIT;
   EXCEPTION
      WHEN OTHERS THEN
         RAISE_APPLICATION_ERROR(-20003, 'SE PRODUJO UN ERROR ACTUALIZANDO LOS DATOS DE CONTROL'||CHR(13)||SQLERRM);

   END SP_ACTUALIZA_DATOS_CONTROL;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   FUNCTION FN_LISTA_DOC_VERIF(A_COD_CONVENIO CHAR) RETURN CURSOR_TYPE IS

      C_CURSOR CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT A.COD_DOCUMENTO_VERIFICACION "Codigo",
                A.DES_DOCUMENTO_VERIFICACION "Descripcion",
                FLG_RETENCION
           FROM CMR.MAE_DOCUMENTO_VERIFICACION A,
                REL_CONVENIO_DOCUM_VERIF       B
          WHERE B.COD_CONVENIO = A_COD_CONVENIO
            AND A.COD_DOCUMENTO_VERIFICACION = B.COD_DOCUMENTO_VERIFICACION
          ORDER BY A.COD_DOCUMENTO_VERIFICACION;

      RETURN C_CURSOR;
   END FN_LISTA_DOC_VERIF;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   -- 2 --
   FUNCTION FN_LISTA_BENEFICIARIO(A_CIA          CMR.MAE_BENEFICIARIO.CIA%TYPE,
                                  A_COD_CONVENIO CMR.MAE_BENEFICIARIO.COD_CONVENIO%TYPE,
                                  A_BENEFICIARIO CHAR,
                                  A_FLG_CODIGO   CHAR,
                                  A_FLG_ESTADO   VARCHAR2 DEFAULT NULL) RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN

      IF A_BENEFICIARIO IS NOT NULL THEN
         IF A_FLG_CODIGO = '1' THEN
            OPEN C_CURSOR FOR
               SELECT A.COD_CLIENTE,
                      B.DES_CLIENTE,
                      B.DES_NOM_CLIENTE,
                      B.DES_APE_CLIENTE,
                      B.DES_APE2_CLIENTE,
                      B.COD_ESTADO_CIVIL,
                      A.FLG_ACTIVO,
                      DECODE(A.FLG_ACTIVO, '1', 'A', 'I') ESTADO,
                      C.DES_DOCUMENTO_IDENTIDAD,
                      B.NUM_DOCUMENTO_ID,
                      A.COD_REFERENCIA,
                      A.FCH_REGISTRA,
                      B.DES_CARGO,
                      NVL(A.IMP_LINEA_CREDITO_ORI, 0) IMP_LINEA_CREDITO,
                      NVL(A.IMP_LINEA_CREDITO, 0) IMP_LINEA_NVA,
                      A.FLG_CAMB_TEMP_LIN_CRE,
                      A.FCH_INI_NVA_LINCRED,
                      A.FCH_FIN_NVA_LINCRED,
                      A.DES_OBSERVACION,
                      -- NVL(A.IMP_LINEA_CREDITO_ORI,0) IMP_LINEA_CREDITO,
                      0 /*--NVL(BTLPROD.PKG_BENEFICIARIO.FN_RET_CONSUMO_BENEFICIARIO(A.COD_CONVENIO,A.COD_CLIENTE),0)*/ IMP_CONSUMO,
                      0 /*--NVL(A.IMP_LINEA_CREDITO_ORI,0) - NVL(BTLPROD.PKG_BENEFICIARIO.FN_RET_CONSUMO_BENEFICIARIO(A.COD_CONVENIO,A.COD_CLIENTE),0)*/ CREDITO_REAL,
                      B.DES_DIRECCION_SOCIAL,
                      TO_CHAR(B.FCH_NACIMIENTO, 'DD/MM/YYYY') FCH_NACIMIENTO,
                      B.DES_EMAIL,
                      A.COD_ZONAL
                 FROM CMR.MAE_BENEFICIARIO A,
                      CMR.MAE_CLIENTE      B,
                      CMR.MAE_DOC_IDENT    C
                WHERE A.COD_CLIENTE = B.COD_CLIENTE
                  AND A.FLG_ACTIVO = NVL(A_FLG_ESTADO,A.FLG_ACTIVO)
                  AND B.COD_DOCUMENTO_IDENTIDAD =
                      C.COD_DOCUMENTO_IDENTIDAD(+)
                  AND
                     /*A.CIA             = A_CIA                              AND */
                      A.COD_CONVENIO = A_COD_CONVENIO
                  AND (A.COD_REFERENCIA = TRIM(A_BENEFICIARIO) OR
                      B.NUM_DOCUMENTO_ID = TRIM(A_BENEFICIARIO))
               /*AND
                                                                                                                                                                                                                                A.FLG_ACTIVO      = '1'*/
               ;
            RETURN C_CURSOR;
         ELSE
         if UPPER(substr(user,1,3)) !='BTL' THEN
             --cmr.sp_llena_convenio2(A_COD_CONVENIO);
               cmr.sp_llena_convenio(A_COD_CONVENIO, A_BENEFICIARIO);



            OPEN C_CURSOR FOR
               SELECT A.COD_CLIENTE,--jluna 20130123
                      B.DES_CLIENTE,
                      B.DES_NOM_CLIENTE,
                      B.DES_APE_CLIENTE,
                      B.DES_APE2_CLIENTE,
                      B.COD_ESTADO_CIVIL,
                      A.FLG_ACTIVO,
                      DECODE(A.FLG_ACTIVO, '1', 'A', 'I') ESTADO,
                      C.DES_DOCUMENTO_IDENTIDAD,
                      B.NUM_DOCUMENTO_ID,
                      A.COD_REFERENCIA,
                      A.FCH_REGISTRA,
                      B.DES_CARGO,
                      NVL(A.IMP_LINEA_CREDITO_ORI, 0) IMP_LINEA_CREDITO,
                      NVL(A.IMP_LINEA_CREDITO, 0) IMP_LINEA_NVA,
                      A.FLG_CAMB_TEMP_LIN_CRE,
                      A.FCH_INI_NVA_LINCRED,
                      A.FCH_FIN_NVA_LINCRED,
                      A.DES_OBSERVACION,
                      -- NVL(A.IMP_LINEA_CREDITO_ORI,0) IMP_LINEA_CREDITO,
                      0 /*--NVL(BTLPROD.PKG_BENEFICIARIO.FN_RET_CONSUMO_BENEFICIARIO(A.COD_CONVENIO,A.COD_CLIENTE),0)*/ IMP_CONSUMO,
                      0 /*--NVL(A.IMP_LINEA_CREDITO_ORI,0) - NVL(BTLPROD.PKG_BENEFICIARIO.FN_RET_CONSUMO_BENEFICIARIO(A.COD_CONVENIO,A.COD_CLIENTE),0)*/ CREDITO_REAL,
                      B.DES_DIRECCION_SOCIAL,
                      TO_CHAR(B.FCH_NACIMIENTO, 'DD/MM/YYYY') FCH_NACIMIENTO,
                      B.DES_EMAIL,
                      A.COD_ZONAL
                 FROM cmr.tmp_mae_beneficiario2 A,

                      CMR.MAE_CLIENTE      B,
                      CMR.MAE_DOC_IDENT    C
                WHERE A.COD_CLIENTE = B.COD_CLIENTE
                  AND A.FLG_ACTIVO = NVL(A_FLG_ESTADO,A.FLG_ACTIVO)
                  AND B.COD_DOCUMENTO_IDENTIDAD =
                      C.COD_DOCUMENTO_IDENTIDAD(+)
                  AND
                     /*A.CIA             = A_CIA                                  AND */
                      A.COD_CONVENIO = A_COD_CONVENIO
                  AND (/*UPPER(*/b.DES_CLIENTE/*)*/ LIKE
                      /*'%' ||*/ TRIM(UPPER(A_BENEFICIARIO)) || '%' OR
                      a.NUM_DOCUMENTO_ID = TRIM(UPPER(A_BENEFICIARIO)));

           ELSE

            OPEN C_CURSOR FOR
               SELECT /*+ INDEX(A IDX_DES_USUARIO)*/A.COD_CLIENTE,--jluna 20130123
                      B.DES_CLIENTE,
                      B.DES_NOM_CLIENTE,
                      B.DES_APE_CLIENTE,
                      B.DES_APE2_CLIENTE,
                      B.COD_ESTADO_CIVIL,
                      A.FLG_ACTIVO,
                      DECODE(A.FLG_ACTIVO, '1', 'A', 'I') ESTADO,
                      C.DES_DOCUMENTO_IDENTIDAD,
                      B.NUM_DOCUMENTO_ID,
                      A.COD_REFERENCIA,
                      A.FCH_REGISTRA,
                      B.DES_CARGO,
                      NVL(A.IMP_LINEA_CREDITO_ORI, 0) IMP_LINEA_CREDITO,
                      NVL(A.IMP_LINEA_CREDITO, 0) IMP_LINEA_NVA,
                      A.FLG_CAMB_TEMP_LIN_CRE,
                      A.FCH_INI_NVA_LINCRED,
                      A.FCH_FIN_NVA_LINCRED,
                      A.DES_OBSERVACION,
                      -- NVL(A.IMP_LINEA_CREDITO_ORI,0) IMP_LINEA_CREDITO,
                      0 /*--NVL(BTLPROD.PKG_BENEFICIARIO.FN_RET_CONSUMO_BENEFICIARIO(A.COD_CONVENIO,A.COD_CLIENTE),0)*/ IMP_CONSUMO,
                      0 /*--NVL(A.IMP_LINEA_CREDITO_ORI,0) - NVL(BTLPROD.PKG_BENEFICIARIO.FN_RET_CONSUMO_BENEFICIARIO(A.COD_CONVENIO,A.COD_CLIENTE),0)*/ CREDITO_REAL,
                      B.DES_DIRECCION_SOCIAL,
                      TO_CHAR(B.FCH_NACIMIENTO, 'DD/MM/YYYY') FCH_NACIMIENTO,
                      B.DES_EMAIL,
                      A.COD_ZONAL
                 FROM CMR.MAE_BENEFICIARIO A,
                      CMR.MAE_CLIENTE      B,
                      CMR.MAE_DOC_IDENT    C
                WHERE A.COD_CLIENTE = B.COD_CLIENTE
                  AND A.FLG_ACTIVO = NVL(A_FLG_ESTADO,A.FLG_ACTIVO)
                  AND B.COD_DOCUMENTO_IDENTIDAD =
                      C.COD_DOCUMENTO_IDENTIDAD(+)
                  AND
                     /*A.CIA             = A_CIA                                  AND */
                      A.COD_CONVENIO = A_COD_CONVENIO
                  AND (/*UPPER(*/B.DES_CLIENTE/*)*/ LIKE
                      /*'%' ||*/ TRIM(UPPER(A_BENEFICIARIO)) || '%' OR
                      B.NUM_DOCUMENTO_ID = TRIM(UPPER(A_BENEFICIARIO)));
                      END IF;
            RETURN C_CURSOR;
         END IF;

      ELSE

         OPEN C_CURSOR FOR
            SELECT A.COD_CLIENTE,
                   B.DES_CLIENTE,
                   B.DES_NOM_CLIENTE,
                   B.DES_APE_CLIENTE,
                   B.DES_APE2_CLIENTE,
                   B.COD_ESTADO_CIVIL,
                   A.FLG_ACTIVO,
                   DECODE(A.FLG_ACTIVO, '1', 'A', 'I') ESTADO,
                   C.DES_DOCUMENTO_IDENTIDAD,
                   B.NUM_DOCUMENTO_ID,
                   A.COD_REFERENCIA,
                   A.FCH_REGISTRA,
                   B.DES_CARGO,
                   NVL(A.IMP_LINEA_CREDITO_ORI, 0) IMP_LINEA_CREDITO,
                   NVL(A.IMP_LINEA_CREDITO, 0) IMP_LINEA_NVA,
                   A.FLG_CAMB_TEMP_LIN_CRE,
                   A.FCH_INI_NVA_LINCRED,
                   A.FCH_FIN_NVA_LINCRED,
                   A.DES_OBSERVACION,
                   0 /*--NVL(BTLPROD.PKG_BENEFICIARIO.FN_RET_CONSUMO_BENEFICIARIO(A.COD_CONVENIO,A.COD_CLIENTE),0)*/ IMP_CONSUMO,
                   0 /*--NVL(A.IMP_LINEA_CREDITO_ORI,0) - NVL(BTLPROD.PKG_BENEFICIARIO.FN_RET_CONSUMO_BENEFICIARIO(A.COD_CONVENIO,A.COD_CLIENTE),0)*/ CREDITO_REAL,
                   A.COD_ZONAL
              FROM CMR.MAE_BENEFICIARIO A,
                   CMR.MAE_CLIENTE      B,
                   CMR.MAE_DOC_IDENT    C
             WHERE A.COD_CLIENTE = B.COD_CLIENTE
               AND B.COD_DOCUMENTO_IDENTIDAD = C.COD_DOCUMENTO_IDENTIDAD
               AND A.FLG_ACTIVO = NVL(A_FLG_ESTADO,A.FLG_ACTIVO)
               AND A.CIA = A_CIA
               AND A.COD_CONVENIO = A_COD_CONVENIO;
         --AND A.FLG_ACTIVO='1';-- AND
         /*B.NUM_DOCUMENTO_ID = TRIM(A_BENEFICIARIO)*/ --AND
         --A.FLG_ACTIVO      = '1' ;
         --                        ORDER BY B.DES_APE_CLIENTE ;
         RETURN C_CURSOR;

      END IF;

   END FN_LISTA_BENEFICIARIO;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   -- 1 --
   FUNCTION FN_LISTA_BENEFICIARIO(A_CIA          CMR.MAE_BENEFICIARIO.CIA%TYPE,
                                  A_COD_CONVENIO CMR.MAE_BENEFICIARIO.COD_CONVENIO%TYPE,
                                  A_BENEFICIARIO CHAR,
                                  A_FLG_ESTADO   VARCHAR2 DEFAULT NULL) RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
      C_CODIGO CMR.MAE_BENEFICIARIO.COD_REFERENCIA%TYPE;
      C_INDICADOR    CMR.MAE_CONVENIO.FLG_DATA_RIMAC%TYPE;
   BEGIN

   ---

      BEGIN
         SELECT COD_REFERENCIA
           INTO C_CODIGO
           FROM CMR.MAE_BENEFICIARIO W
          WHERE /*W.CIA = A_CIA  AND */
          W.COD_CONVENIO = A_COD_CONVENIO
          AND W.COD_REFERENCIA = A_BENEFICIARIO;
      EXCEPTION
         WHEN NO_DATA_FOUND THEN
            C_CODIGO := NULL;
      END;

      IF C_CODIGO IS NOT NULL THEN
         C_CURSOR := BTLPROD.PKG_CONVENIO.FN_LISTA_BENEFICIARIO(A_CIA          => A_CIA,
                                                                A_COD_CONVENIO => A_COD_CONVENIO,
                                                                A_BENEFICIARIO => A_BENEFICIARIO,
                                                                A_FLG_CODIGO   => '1',
                                                                A_FLG_ESTADO => A_FLG_ESTADO);
      ELSE
         C_CURSOR := BTLPROD.PKG_CONVENIO.FN_LISTA_BENEFICIARIO(A_CIA          => A_CIA,
                                                                A_COD_CONVENIO => A_COD_CONVENIO,
                                                                A_BENEFICIARIO => A_BENEFICIARIO,
                                                                A_FLG_CODIGO   => '0',
                                                                A_FLG_ESTADO => A_FLG_ESTADO);
      END IF;

      RETURN C_CURSOR;
   END FN_LISTA_BENEFICIARIO;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   FUNCTION FN_LISTA_REPARTIDOR(A_COD_CONVENIO CHAR) RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT A.COD_REPARTIDOR, A.DES_REPARTIDOR
           FROM CMR.MAE_REPARTIDOR A, REL_CONVENIO_REPARTIDOR B
          WHERE A.COD_REPARTIDOR = B.COD_REPARTIDOR
            AND B.COD_CONVENIO = A_COD_CONVENIO
            AND A.FLG_ACTIVO = '1'
          ORDER BY A.DES_REPARTIDOR;
      RETURN C_CURSOR;
   END FN_LISTA_REPARTIDOR;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   FUNCTION FN_LISTA_MEDICO(A_COD_CONVENIO CHAR) RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT A.COD_MEDICO,
                DES_APE_MEDICO || ', ' || A.DES_NOM_MEDICO "NOMBRE",
                A.NUM_CMP
           FROM CMR.MAE_MEDICO A, REL_CONVENIO_MEDICO B
          WHERE A.COD_MEDICO = B.COD_MEDICO
            AND B.COD_CONVENIO = A_COD_CONVENIO
            AND B.FLG_ACTIVO = '1'
          ORDER BY NOMBRE;
      RETURN C_CURSOR;
   END FN_LISTA_MEDICO;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   FUNCTION FN_LISTA_TIPO_CAMPO(A_COD_CONVENIO CHAR) RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT A.COD_TIPO_CAMPO,
                B.DES_TIPO_CAMPO,
                A.FLG_TIPO_DATO,
                A.CTD_LONG_MAX,
                A.CTD_LONG_MIN,
                B.FLG_EDITABLE
           FROM REL_CONVENIO_TIPO_CAMPO A, CMR.MAE_TIPO_CAMPO B
          WHERE COD_CONVENIO = A_COD_CONVENIO
            AND A.COD_TIPO_CAMPO = B.COD_TIPO_CAMPO
            AND B.FLG_ACTIVO = '1'
          ORDER BY A.COD_TIPO_CAMPO;
      RETURN C_CURSOR;
   END FN_LISTA_TIPO_CAMPO;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   FUNCTION FN_LISTA_X_LOCAL(A_CIA       BTLPROD.REL_CONVENIO_LOCAL.CIA%TYPE,
                             A_COD_LOCAL BTLPROD.REL_CONVENIO_LOCAL.COD_LOCAL%TYPE,
                             A_CRITERIO  CHAR DEFAULT NULL) RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN

      IF A_COD_LOCAL IS NOT NULL THEN
         OPEN C_CURSOR FOR
            SELECT A.COD_CONVENIO,
                   A.COD_CLIENTE,
                   (CASE A.FLG_TIPO_CONVENIO
                      WHEN '1' THEN
                       ''
                      WHEN '2' THEN
                       '(' || A.IMP_VALOR || '%) '
                   END) || A.DES_CONVENIO DES_CONVENIO,
                   A.FLG_BENEFICIARIOS,
                   --A.PCT_BENEFICIARIO,
                   A.FLG_REPARTIDOR,
                   A.FLG_MEDICO,
                   A.FLG_POLITICA,
                   --A.PCT_BENEFICIARIO,
                   --A.PCT_EMPRESA,
                   (CASE A.FLG_TIPO_CONVENIO
                      WHEN '1' THEN
                       A.PCT_BENEFICIARIO
                      WHEN '2' THEN
                       0
                   END) PCT_BENEFICIARIO,
                   (CASE A.FLG_TIPO_CONVENIO
                      WHEN '1' THEN
                       A.PCT_EMPRESA
                      WHEN '2' THEN
                       0
                   END) PCT_EMPRESA,
                   A.IMP_LINEA_CREDITO,
                   (CASE A.FLG_TIPO_CONVENIO
                      WHEN '1' THEN
                       'COP'
                      WHEN '2' THEN
                       'DSC'
                   END) FLG_TIPO_CONVENIO,
                   a.FLG_ATENCION_TODOS_LOCALES,
                   A.FLG_PERIODO_VALIDEZ,
                   A.FCH_INICIO,
                   A.FCH_FIN,
                   A.COD_TIPDOC_CLIENTE,
                   A.COD_TIPDOC_BENEFICIARIO,
                   A.FLG_DIAGNOSTICO,
                   C.DES_DIRECCION_SOCIAL,
                   A.FLG_VALIDA_LINCRE_BENEF,
                   a.CTD_MAX_UND_PRODUCTO
              FROM CMR.MAE_CONVENIO A, CMR.MAE_CLIENTE C
             WHERE /*A.CIA            = A_CIA       AND*/
             A.COD_CLIENTE = C.COD_CLIENTE
             AND A.FLG_ACTIVO = '1'
             AND UPPER(A.DES_CONVENIO) LIKE '%' || UPPER(A_CRITERIO) || '%'
             AND ((A.FLG_ATENCION_LOCAL = '1' AND
             A_COD_LOCAL <> PKG_CONSTANTES.CONS_LOCAL_DELIVERY AND
             (A.FLG_ATENCION_TODOS_LOCALES = '1' OR
             A.FLG_ATENCION_TODOS_LOCALES = '0' AND EXISTS
              (SELECT '1'
                   FROM REL_CONVENIO_LOCAL B
                  WHERE A.COD_CONVENIO = B.COD_CONVENIO
                    AND
                       /*A.CIA          = B.CIA          AND */
                        B.COD_LOCAL = A_COD_LOCAL))) OR
             (A.FLG_ATENCION_DELIVERY = '1' AND
             A_COD_LOCAL = PKG_CONSTANTES.CONS_LOCAL_DELIVERY))
             ORDER BY A.DES_CONVENIO;
      ELSE
         OPEN C_CURSOR FOR
            SELECT A.COD_CONVENIO,
                   A.COD_CLIENTE,
                   A.DES_CONVENIO,
                   A.FLG_BENEFICIARIOS,
                   A.PCT_BENEFICIARIO,
                   A.FLG_REPARTIDOR,
                   A.FLG_MEDICO,
                   A.FLG_POLITICA,
                   A.PCT_BENEFICIARIO,
                   A.PCT_EMPRESA,
                   A.IMP_LINEA_CREDITO,
                   A.FLG_TIPO_CONVENIO,
                   A.FLG_ATENCION_TODOS_LOCALES,
                   A.FLG_PERIODO_VALIDEZ,
                   A.FCH_INICIO,
                   A.FCH_FIN,
                   A.COD_TIPDOC_CLIENTE,
                   A.COD_TIPDOC_BENEFICIARIO,
                   A.FLG_DIAGNOSTICO,
                   A.FLG_VALIDA_LINCRE_BENEF,
                   a.CTD_MAX_UND_PRODUCTO
              FROM CMR.MAE_CONVENIO A
             WHERE FLG_ACTIVO = '1'
               AND (A.FLG_ATENCION_TODOS_LOCALES = '1' OR
                   A.FLG_ATENCION_TODOS_LOCALES = '0' AND EXISTS
                    (SELECT '1'
                       FROM REL_CONVENIO_LOCAL B
                      WHERE A.COD_CONVENIO = B.COD_CONVENIO
                        AND
                           /*A.CIA          = B.CIA          AND */
                            B.COD_LOCAL = A_COD_LOCAL))
             ORDER BY A.DES_CONVENIO;
      END IF;
      RETURN C_CURSOR;
   END FN_LISTA_X_LOCAL;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   -----------------------------------------------------------------------------------------
   --- Lista documentos de Emprea --
   FUNCTION FN_LISTA_DOCUMENTOS(A_CIA       BTLPROD.REL_DOCUMENTO_LOCAL.CIA%TYPE,
                                A_COD_LOCAL BTLPROD.REL_DOCUMENTO_LOCAL.COD_LOCAL%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT A.COD_TIPO_DOCUMENTO COD, B.DES_TIPODOC DES, '1' POS
           FROM BTLPROD.REL_DOCUMENTO_LOCAL A, CMR.MAE_TIPO_DOCUMENTO B
          WHERE A.COD_TIPO_DOCUMENTO = B.COD_TIPODOC
            AND A.CIA = A_CIA
            AND A.COD_LOCAL = A_COD_LOCAL
         UNION
         SELECT ' ' COD, ' ' DES, '0' POS
           FROM BTLPROD.REL_DOCUMENTO_LOCAL A, CMR.MAE_TIPO_DOCUMENTO B
          WHERE A.COD_TIPO_DOCUMENTO = B.COD_TIPODOC
            AND A.CIA = A_CIA
            AND A.COD_LOCAL = A_COD_LOCAL
          ORDER BY POS;
      RETURN C_CURSOR;
   END FN_LISTA_DOCUMENTOS;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- Lista Petitorio ---
   FUNCTION FN_LISTA_PETITORIO(A_COD_PETITORIO BTLPROD.CAB_PETITORIO.COD_PETITORIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN
      IF A_COD_PETITORIO IS NULL THEN
         OPEN C_CURSOR FOR
            SELECT COD_PETITORIO, DES_PETITORIO
              FROM CAB_PETITORIO
             WHERE FLG_ACTIVO = '1';
      ELSE
         IF CMR.PKG_UTIL.FN_ES_NUMERO(A_COD_PETITORIO) THEN
            OPEN C_CURSOR FOR
               SELECT COD_PETITORIO, DES_PETITORIO
                 FROM CAB_PETITORIO
                WHERE FLG_ACTIVO = '1'
                  AND COD_PETITORIO = A_COD_PETITORIO;
         ELSE
            OPEN C_CURSOR FOR
               SELECT COD_PETITORIO, DES_PETITORIO
                 FROM CAB_PETITORIO
                WHERE FLG_ACTIVO = '1'
                  AND UPPER(DES_PETITORIO) LIKE
                      UPPER(A_COD_PETITORIO) || '%';
         END IF;
      END IF;
      RETURN C_CURSOR;
   END FN_LISTA_PETITORIO;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- LISTA DE MEDICOS ---
   FUNCTION FN_LISTA_MEDICO RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT COD_MEDICO COD,
                DES_NOM_MEDICO || ' ' || DES_APE_MEDICO NOMB
           FROM CMR.MAE_MEDICO
          WHERE FLG_ACTIVO = '1'
          ORDER BY COD_MEDICO;
      RETURN C_CURSOR;
   END FN_LISTA_MEDICO;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- PARA LISTAR PARAMETROS DE UN CONVENIO ---
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- DOCUMENTOS VERIFICACION ---
   FUNCTION FN_LISTA_DOCUM_VERIF(A_CIA          BTLPROD.REL_CONVENIO_DOCUM_VERIF.CIA%TYPE,
                                 A_COD_CONVENIO BTLPROD.REL_CONVENIO_DOCUM_VERIF.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT A.COD_DOCUMENTO_VERIFICACION,
                B.DES_DOCUMENTO_VERIFICACION,
                A.FLG_RETENCION
           FROM BTLPROD.REL_CONVENIO_DOCUM_VERIF A,
                CMR.MAE_DOCUMENTO_VERIFICACION   B
          WHERE A.COD_DOCUMENTO_VERIFICACION = B.COD_DOCUMENTO_VERIFICACION
            AND A.CIA = A_CIA
            AND A.COD_CONVENIO = A_COD_CONVENIO
            AND A.FLG_ACTIVO = '1';
      RETURN C_CURSOR;
   END FN_LISTA_DOCUM_VERIF;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- FORMAS DE PAGO DE UN CONVENIO ---
   FUNCTION FN_LISTA_CONVENIO_FORMAPAGOS(A_CIA          BTLPROD.REL_FORMA_PAGO_CONVENIO.CIA%TYPE,
                                         A_COD_CONVENIO BTLPROD.REL_FORMA_PAGO_CONVENIO.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN

      /*SELECT  B.DES_HIJO, A.COD_HIJO, '1' FLG_FP
      FROM BTLPROD.REL_FORMA_PAGO_CONVENIO A, BTLPROD.AUX_FORMA_PAGO B
      WHERE A.COD_FORMA_PAGO = B.COD_FORMA_PAGO
      AND A.COD_HIJO = B.COD_HIJO
      AND A.CIA = A_CIA
      AND A.COD_CONVENIO = A_COD_CONVENIO
      AND A.FLG_ACTIVO = '1';*/

      IF A_COD_CONVENIO IS NULL THEN
         OPEN C_CURSOR FOR
            SELECT A.COD_FORMA_PAGO,
                   A.DES_FORMA_PAGO,
                   B.COD_HIJO,
                   B.DES_HIJO,
                   '0' FLAG
              FROM CMR.MAE_FORMA_PAGO A, BTLPROD.AUX_FORMA_PAGO B
             WHERE A.COD_FORMA_PAGO = B.COD_FORMA_PAGO
             ORDER BY A.COD_FORMA_PAGO, B.COD_HIJO;
         RETURN C_CURSOR;
      ELSE
         OPEN C_CURSOR FOR
            SELECT A.COD_FORMA_PAGO,
                   A.DES_FORMA_PAGO,
                   B.COD_HIJO,
                   B.DES_HIJO,
                   NVL((SELECT '1'
                         FROM BTLPROD.REL_FORMA_PAGO_CONVENIO C
                        WHERE C.CIA = A_CIA
                          AND C.COD_CONVENIO = A_COD_CONVENIO
                          AND C.COD_FORMA_PAGO = B.COD_FORMA_PAGO
                          AND C.COD_HIJO = B.COD_HIJO),
                       '0') FLAG
              FROM CMR.MAE_FORMA_PAGO A, BTLPROD.AUX_FORMA_PAGO B
             WHERE A.COD_FORMA_PAGO = B.COD_FORMA_PAGO
             ORDER BY A.COD_FORMA_PAGO, B.COD_HIJO;
         RETURN C_CURSOR;
      END IF;
      RETURN C_CURSOR;
   END FN_LISTA_CONVENIO_FORMAPAGOS;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- LOCALES DONDE SE HACE EL CONVENIO ---
   FUNCTION FN_LISTA_CONVENIO_LOCALES(A_CIA          BTLPROD.REL_CONVENIO_LOCAL.CIA%TYPE,
                                      A_COD_CONVENIO BTLPROD.REL_CONVENIO_LOCAL.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT B.DES_LOCAL,
                A.COD_LOCAL,
                NVL((SELECT COD_LOCAL_SAP FROM NUEVO.AUX_MAE_LOCAL WHERE COD_LOCAL=B.COD_LOCAL),B.COD_LOCAL) COD_LOCAL_SAP,
                NVL(PCT_BENEFICIARIO, 0) PCT_BENEFICIARIO,
                NVL(PCT_EMPRESA, 0) PCT_EMPRESA
           FROM BTLPROD.REL_CONVENIO_LOCAL A, NUEVO.MAE_LOCAL B
          WHERE A.COD_LOCAL = B.COD_LOCAL
            AND A.CIA = A_CIA
            AND A.COD_CONVENIO = A_COD_CONVENIO
            AND A.FLG_ACTIVO = '1';
      RETURN C_CURSOR;
   END FN_LISTA_CONVENIO_LOCALES;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- MEDICOS DE ESTE CONVENIO ---
   FUNCTION FN_LISTA_MEDICO_CONVENIO(A_CIA          BTLPROD.REL_CONVENIO_MEDICO.CIA%TYPE,
                                     A_COD_CONVENIO BTLPROD.REL_CONVENIO_MEDICO.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT A.COD_MEDICO,
                B.NUM_CMP,
                B.DES_NOM_MEDICO || ' ' || B.DES_APE_MEDICO MEDICO,
                DES_DIRECCION
           FROM BTLPROD.REL_CONVENIO_MEDICO A, CMR.MAE_MEDICO B
          WHERE A.COD_MEDICO = B.COD_MEDICO
            AND A.CIA = A_CIA
            AND A.COD_CONVENIO = A_COD_CONVENIO
            AND A.FLG_ACTIVO = '1';
      RETURN C_CURSOR;
   END FN_LISTA_MEDICO_CONVENIO;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- PARA LA EDICION LOS PARAMETROS DEL CONVENIO ---
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- DOCUMENTOS VERIFICACION ---
   FUNCTION FN_LST_EDICION_DOCUM_VERIF(A_CIA          BTLPROD.REL_CONVENIO_DOCUM_VERIF.CIA%TYPE,
                                       A_COD_CONVENIO BTLPROD.REL_CONVENIO_DOCUM_VERIF.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT COD_DOCUMENTO_VERIFICACION, DES_DOCUMENTO_VERIFICACION
           FROM CMR.MAE_DOCUMENTO_VERIFICACION A
          WHERE NOT EXISTS (SELECT '1'
                   FROM BTLPROD.REL_CONVENIO_DOCUM_VERIF
                  WHERE A.COD_DOCUMENTO_VERIFICACION =
                        COD_DOCUMENTO_VERIFICACION
                    AND CIA = A_CIA
                    AND COD_CONVENIO = A_COD_CONVENIO
                    AND FLG_ACTIVO = '1')
          ORDER BY COD_DOCUMENTO_VERIFICACION;
      RETURN C_CURSOR;
   END FN_LST_EDICION_DOCUM_VERIF;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- FORMAS DE PAGO DE UN CONVENIO ---
   FUNCTION FN_LST_EDICION_FORMAPAGOS_CNV(A_CIA          BTLPROD.REL_FORMA_PAGO_CONVENIO.CIA%TYPE,
                                          A_COD_CONVENIO BTLPROD.REL_FORMA_PAGO_CONVENIO.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT COD_FORMA_PAGO, DES_FORMA_PAGO
           FROM CMR.MAE_FORMA_PAGO B
          WHERE NOT EXISTS (SELECT '1'
                   FROM REL_FORMA_PAGO_CONVENIO
                  WHERE COD_FORMA_PAGO = B.COD_FORMA_PAGO
                    AND CIA = A_CIA
                    AND COD_CONVENIO = A_COD_CONVENIO
                    AND FLG_ACTIVO = '1')
          ORDER BY COD_FORMA_PAGO;
      RETURN C_CURSOR;
   END FN_LST_EDICION_FORMAPAGOS_CNV;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- LOCALES DONDE SE HACE EL CONVENIO ---
   FUNCTION FN_LST_EDICION_LOCALES_CNV(A_CIA          BTLPROD.REL_CONVENIO_LOCAL.CIA%TYPE,
                                       A_COD_CONVENIO BTLPROD.REL_CONVENIO_LOCAL.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT COD_LOCAL, DES_LOCAL,
           NVL((SELECT COD_LOCAL_SAP FROM NUEVO.AUX_MAE_LOCAL WHERE COD_LOCAL=C.COD_LOCAL),C.COD_LOCAL) COD_LOCAL_SAP
           FROM NUEVO.MAE_LOCAL C
          WHERE NOT EXISTS (SELECT '1'
                   FROM BTLPROD.REL_CONVENIO_LOCAL
                  WHERE COD_LOCAL = C.COD_LOCAL
                    --AND CIA = A_CIA
                    AND COD_CONVENIO = A_COD_CONVENIO
                    AND FLG_ACTIVO = '1')
          --AND C.CIA=A_CIA
          ORDER BY COD_LOCAL;
      RETURN C_CURSOR;
   END FN_LST_EDICION_LOCALES_CNV;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- MEDICOS DE ESTE CONVENIO ---
   FUNCTION FN_LST_EDICION_MEDICO_CNV(A_CIA          BTLPROD.REL_CONVENIO_MEDICO.CIA%TYPE,
                                      A_COD_CONVENIO BTLPROD.REL_CONVENIO_MEDICO.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT COD_MEDICO, DES_NOM_MEDICO || ' ' || DES_APE_MEDICO MEDICO
           FROM CMR.MAE_MEDICO D
          WHERE EXISTS (SELECT '1'
                   FROM BTLPROD.REL_CONVENIO_MEDICO
                  WHERE COD_MEDICO = D.COD_MEDICO
                    AND CIA = A_CIA
                    AND COD_CONVENIO = A_COD_CONVENIO
                    AND FLG_ACTIVO = '1')
          ORDER BY COD_MEDICO;
      RETURN C_CURSOR;
   END FN_LST_EDICION_MEDICO_CNV;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   FUNCTION FN_LST_VALORES_MOV_KARDEX RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT ' ' COD, ' ' DES, 0 POS
           FROM DUAL
         UNION
         SELECT '1' COD, 'DOC. DEL BENEFICIARIO' DES, 1 POS
           FROM DUAL
         UNION
         SELECT '2' COD, 'DOC. DEL CLIENTE' DES, 2 POS
           FROM DUAL
          ORDER BY POS;
      RETURN C_CURSOR;

   END FN_LST_VALORES_MOV_KARDEX;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   FUNCTION FN_LISTA_CLIENTE_CLASE(A_COD_CLASE_CONVENIO CMR.MAE_CONVENIO.COD_CLASE_CONVENIO%TYPE)
      RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN

      OPEN C_CURSOR FOR
         SELECT A.COD_CONVENIO, A.COD_CLIENTE, A.DES_CONVENIO
           FROM CMR.MAE_CONVENIO       A,
                CMR.MAE_CLIENTE        B,
                CMR.MAE_CLASE_CONVENIO C
          WHERE A.COD_CLIENTE = B.COD_CLIENTE
            AND A.COD_CLASE_CONVENIO = C.COD_CLASE_CONVENIO
            AND A.COD_CLASE_CONVENIO = A_COD_CLASE_CONVENIO;
      RETURN C_CURSOR;
   END FN_LISTA_CLIENTE_CLASE;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- Graba relacion de benefiario con el convenio ---
   PROCEDURE SP_GRABA_BENEFICIARIO_CNV(A_CIA                         CMR.MAE_BENEFICIARIO.CIA%TYPE,
                                       A_COD_CONVENIO                CHAR,
                                       A_COD_BENEFICIARIO            CHAR,
                                       A_IMP_LINEA_CREDITO           CHAR,
                                       A_FLG_CAMB_TEMP_LIN_CRE       CHAR,
                                       A_IMP_LINEA_CREDITO_ORI       CHAR,
                                       A_COD_USUARIO_AUTORIZA_CAMBIO CHAR,
                                       A_DES_OBSERVACION             CHAR,
                                       A_COD_REFERENCIA              CHAR,
                                       A_FLG_ACTIVO                  CHAR,
                                       A_COD_USUARIO                 CHAR,
                                       A_FCH_INI_NVA_LINCRED         CMR.MAE_BENEFICIARIO.FCH_INI_NVA_LINCRED%TYPE,
                                       A_FCH_FIN_NVA_LINCRED         CMR.MAE_BENEFICIARIO.FCH_FIN_NVA_LINCRED%TYPE,
                                       A_IMP_CONSUMO                 CMR.MAE_BENEFICIARIO.IMP_CONSUMO%TYPE,
                                       A_COD_ZONAL                   CMR.MAE_BENEFICIARIO.COD_ZONAL%TYPE DEFAULT NULL

                                       )

      --A_CIA                     CMR.MAE_BENEFICIARIO.CIA%TYPE,
      --A_COD_CONVENIO            CMR.MAE_BENEFICIARIO.COD_CONVENIO%TYPE,
      --A_COD_CLIENTE             CMR.MAE_BENEFICIARIO.COD_CLIENTE%TYPE,
      --A_IMP_LINEA_CREDITO       CMR.MAE_BENEFICIARIO.IMP_LINEA_CREDITO%TYPE,
      --A_FLG_CAMB_TEMP_LIN_CRE   CMR.MAE_BENEFICIARIO.FLG_CAMB_TEMP_LIN_CRE%TYPE,
      --A_DES_OBSERVACION         CMR.MAE_BENEFICIARIO.DES_OBSERVACION%TYPE,
      --A_COD_REFERENCIA          CMR.MAE_BENEFICIARIO.COD_REFERENCIA%TYPE,
      --A_FLG_ACTIVO              CMR.MAE_BENEFICIARIO.FLG_ACTIVO%TYPE,
      --A_COD_USUARIO             CMR.MAE_BENEFICIARIO.COD_USUARIO%TYPE,
      --A_FCH_INI_NVA_LINCRED     CMR.MAE_BENEFICIARIO.FCH_INI_NVA_LINCRED%TYPE,
      --A_FCH_FIN_NVA_LINCRED     CMR.MAE_BENEFICIARIO.FCH_FIN_NVA_LINCRED%TYPE,
      --A_IMP_CONSUMO             CMR.MAE_BENEFICIARIO.IMP_CONSUMO%TYPE
    AS
   BEGIN
      BEGIN
         UPDATE CMR.MAE_BENEFICIARIO
            SET IMP_LINEA_CREDITO           = A_IMP_LINEA_CREDITO,
                FLG_CAMB_TEMP_LIN_CRE       = A_FLG_CAMB_TEMP_LIN_CRE,
                IMP_LINEA_CREDITO_ORI       = A_IMP_LINEA_CREDITO_ORI,
                COD_USUARIO_AUTORIZA_CAMBIO = A_COD_USUARIO_AUTORIZA_CAMBIO,
                DES_OBSERVACION             = A_DES_OBSERVACION,
                COD_REFERENCIA              = A_COD_REFERENCIA,
                FLG_ACTIVO                  = A_FLG_ACTIVO,
                COD_ZONAL                   = A_COD_ZONAL
          WHERE COD_CONVENIO = A_COD_CONVENIO
            AND COD_CLIENTE = A_COD_BENEFICIARIO
            AND CIA = A_CIA;
         IF SQL%NOTFOUND THEN
            /********************************************************************************/
            --????? **** ESTO SE CAMBIO PORQUE HACIA DOBLE INSERCION ******* ??????
            /*PKG_BENEFICIARIO.SP_GRABA ( A_CIA                     , A_COD_CONVENIO          ,
             A_COD_BENEFICIARIO        , A_IMP_LINEA_CREDITO     ,
             A_FLG_CAMB_TEMP_LIN_CRE   , A_DES_OBSERVACION       ,
             A_COD_REFERENCIA          , A_FLG_ACTIVO            ,
             A_COD_USUARIO             , A_FCH_INI_NVA_LINCRED   ,
             A_FCH_FIN_NVA_LINCRED     , A_IMP_CONSUMO
            ) ; */
            /********************************************************************************/

            BEGIN
               INSERT INTO CMR.MAE_BENEFICIARIO
                  (COD_CONVENIO,
                   CIA,
                   COD_CLIENTE,
                   IMP_LINEA_CREDITO,
                   FLG_CAMB_TEMP_LIN_CRE,
                   IMP_LINEA_CREDITO_ORI,
                   COD_USUARIO_AUTORIZA_CAMBIO,
                   DES_OBSERVACION,
                   COD_REFERENCIA,
                   FLG_ACTIVO,
                   COD_USUARIO,
                   FCH_REGISTRA,
                   COD_ZONAL)
               VALUES
                  (A_COD_CONVENIO,
                   A_CIA,
                   A_COD_BENEFICIARIO,
                   A_IMP_LINEA_CREDITO,
                   A_FLG_CAMB_TEMP_LIN_CRE,
                   A_IMP_LINEA_CREDITO_ORI,
                   A_COD_USUARIO_AUTORIZA_CAMBIO,
                   A_DES_OBSERVACION,
                   A_COD_REFERENCIA,
                   A_FLG_ACTIVO,
                   A_COD_USUARIO,
                   SYSDATE,
                   A_COD_ZONAL);
            EXCEPTION
               WHEN DUP_VAL_ON_INDEX THEN
                  RAISE_APPLICATION_ERROR(-20000,
                                          'EL BENEFICIARIO YA SE ENCUENTRA ASOCIADO AL CONVENIO.');
            END;
         END IF;
      END;
      COMMIT;

   EXCEPTION
      WHEN OTHERS THEN
         RAISE_APPLICATION_ERROR('-20000', 'SE PRODUJO UN ERROR INSERTANDO EL BENEFICIARIO.'||CHR(13)||SQLERRM);

   END SP_GRABA_BENEFICIARIO_CNV;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   /*
   Autor: Pablo Herrera
   Fecha: 18/01/07
   Motivo: Esta funcion devuelve el codigo del convenio BTL para efectos de operaciones en el
       sistema financiero contable
   */
   FUNCTION FN_LISTA_CONVENIO_BTL RETURN VARCHAR2 IS
   BEGIN
      RETURN '0000000011';
   END;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   ---  FECHA => 03/04/2007
   ---  AUTOR => CRISTHIAN RUEDA

   FUNCTION FN_LISTA_DATOS_ADICIONALES(A_DATO_CAMPO CHAR) RETURN SYS_REFCURSOR AS
      C_CURSOR SYS_REFCURSOR;
      C_SQL    VARCHAR2(4000);
      C_DES    VARCHAR2(1000);
   BEGIN
      IF A_DATO_CAMPO IS NULL THEN

         OPEN C_CURSOR FOR
            SELECT COD_TIPO_CAMPO,
                   DES_TIPO_CAMPO,
                   DECODE(FLG_ACTIVO, '1', 'ACTIVO', 'INACTIVO') ESTADO,
                   DECODE(FLG_EDITABLE, '1', 'ACTIVO', 'BLOQUEADO') EDITABLE
              FROM CMR.MAE_TIPO_CAMPO
             ORDER BY COD_TIPO_CAMPO;
      ELSE
         C_SQL := ' SELECT COD_TIPO_CAMPO, DES_TIPO_CAMPO,
                          DECODE(FLG_ACTIVO,''1'',''ACTIVO'',''INACTIVO'') ESTADO,
                          DECODE(FLG_EDITABLE,''1'',''ACTIVO'',''BLOQUEADO'')EDITABLE
                        FROM CMR.MAE_TIPO_CAMPO  ';

         IF CMR.PKG_UTIL.FN_ES_NUMERO(A_DATO_CAMPO) THEN
            C_SQL := C_SQL || ' WHERE COD_TIPO_CAMPO = :A_DATO_CAMPO ';
            C_DES := TRIM(A_DATO_CAMPO);
         ELSE
            C_SQL := C_SQL ||
                     ' WHERE DES_TIPO_CAMPO LIKE :A_DATO_CAMPO ORDER BY COD_TIPO_CAMPO ';
            C_DES := '%' || REPLACE(UPPER(TRIM(A_DATO_CAMPO)), ' ', '%') || '%';
         END IF;

         C_SQL := REPLACE(C_SQL, ':A_DATO_CAMPO', '''' || C_DES || '''');

         OPEN C_CURSOR FOR C_SQL;

      END IF;

      RETURN C_CURSOR;

   END FN_LISTA_DATOS_ADICIONALES;

   ---- SACA EL MAXIMO CODIGO ADICIONAL ----
   FUNCTION FN_MAX_COD RETURN VARCHAR2 AS
      C_MAX_VALOR CHAR(10);
   BEGIN
      SELECT LPAD(TO_NUMBER(MAX(COD_TIPO_CAMPO)) + 1, 10, '0')
        INTO C_MAX_VALOR
        FROM CMR.MAE_TIPO_CAMPO;

      RETURN C_MAX_VALOR;
   EXCEPTION
      WHEN NO_DATA_FOUND THEN
         RETURN ' ';
   END FN_MAX_COD;

   ---- GRABACION DE DATOS ADICIONALES ----
   PROCEDURE SP_GRABA_DATOS_ADICIONALES(A_COD_CAMPO    CMR.MAE_TIPO_CAMPO.COD_TIPO_CAMPO%TYPE,
                                        A_DES_CAMPO    CMR.MAE_TIPO_CAMPO.DES_TIPO_CAMPO%TYPE,
                                        A_FLG_ESTADO   CMR.MAE_TIPO_CAMPO.FLG_ACTIVO%TYPE,
                                        A_FLG_EDITABLE CMR.MAE_TIPO_CAMPO.FLG_EDITABLE%TYPE,
                                        A_COD_USUARIO  CMR.MAE_TIPO_CAMPO.COD_USUARIO%TYPE) AS
      C_COD_GRP_TIP_CAMPO CHAR(10) := '0000000001';
   BEGIN
      BEGIN
         UPDATE CMR.MAE_TIPO_CAMPO
            SET DES_TIPO_CAMPO        = A_DES_CAMPO,
                FLG_ACTIVO            = A_FLG_ESTADO,
                COD_USUARIO_ACTUALIZA = A_COD_USUARIO,
                FLG_EDITABLE          = A_FLG_EDITABLE,
                FCH_ACTUALIZA         = SYSDATE
          WHERE COD_TIPO_CAMPO = A_COD_CAMPO;

         IF SQL%NOTFOUND THEN
            BEGIN
               INSERT INTO CMR.MAE_TIPO_CAMPO
                  (COD_TIPO_CAMPO,
                   COD_GRP_TIP_CAMPO,
                   COD_TIPO_CAMPO_PADRE,
                   DES_TIPO_CAMPO,
                   FLG_ACTIVO,
                   COD_USUARIO,
                   FLG_EDITABLE,
                   FCH_REGISTRO)
               VALUES
                  (A_COD_CAMPO,
                   C_COD_GRP_TIP_CAMPO,
                   NULL,
                   A_DES_CAMPO,
                   A_FLG_ESTADO,
                   A_COD_USUARIO,
                   A_FLG_EDITABLE,
                   SYSDATE);
            EXCEPTION
               WHEN DUP_VAL_ON_INDEX THEN
                  RAISE_APPLICATION_ERROR(-20000,
                                          'EL DATO ADICIONAL YA SE ENCUENTRA REGISTRADO.' ||CHR(13)||
                                          SQLERRM);
            END;
         END IF;
      END;
      COMMIT;
   EXCEPTION
      WHEN OTHERS THEN
         RAISE_APPLICATION_ERROR(-20000, 'SE PRODUJO UN ERROR EN LA GRABACION DEL DATO ADICIONAL.'||CHR(13)||SQLERRM);

   END SP_GRABA_DATOS_ADICIONALES;

   ---- DATOS ADICIONALES ASOCIADO A UN CONVENIO ----
   FUNCTION FN_LISTA_DATOS_ADIC_CNV(A_COD_CONVENIO BTLPROD.REL_CONVENIO_TIPO_CAMPO.COD_CONVENIO%TYPE)
      RETURN SYS_REFCURSOR AS
      C_CURSOR SYS_REFCURSOR;
   BEGIN
      IF A_COD_CONVENIO IS NULL THEN
         OPEN C_CURSOR FOR
            SELECT A.DES_TIPO_CAMPO DES, A.COD_TIPO_CAMPO COD
              FROM CMR.MAE_TIPO_CAMPO A --, BTLPROD.REL_CONVENIO_TIPO_CAMPO B
--             WHERE A.COD_TIPO_CAMPO = B.COD_TIPO_CAMPO
--          GROUP BY A.DES_TIPO_CAMPO, A.COD_TIPO_CAMPO
          ORDER BY A.COD_TIPO_CAMPO, A.DES_TIPO_CAMPO;
      ELSE
         OPEN C_CURSOR FOR
            SELECT A.DES_TIPO_CAMPO DES, A.COD_TIPO_CAMPO COD
              FROM CMR.MAE_TIPO_CAMPO A, BTLPROD.REL_CONVENIO_TIPO_CAMPO B
             WHERE A.COD_TIPO_CAMPO = B.COD_TIPO_CAMPO
               AND B.COD_CONVENIO = A_COD_CONVENIO
          ORDER BY A.COD_TIPO_CAMPO, A.DES_TIPO_CAMPO;
      END IF;
      RETURN C_CURSOR;
   END FN_LISTA_DATOS_ADIC_CNV;

   ---- CONSULTA PARA LA EDICION DE DATOS ADICIONALES ----
   FUNCTION FN_LST_EDICION_DATOS_ADIC(A_COD_CONVENIO BTLPROD.REL_CONVENIO_TIPO_CAMPO.COD_CONVENIO%TYPE)
      RETURN SYS_REFCURSOR AS
      C_CURSOR SYS_REFCURSOR;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT A.DES_TIPO_CAMPO DES, A.COD_TIPO_CAMPO COD
           FROM CMR.MAE_TIPO_CAMPO A
          WHERE NOT EXISTS (SELECT '1'
                   FROM BTLPROD.REL_CONVENIO_TIPO_CAMPO B
                  WHERE A.COD_TIPO_CAMPO = B.COD_TIPO_CAMPO
                    AND B.COD_CONVENIO = A_COD_CONVENIO)
          ORDER BY A.COD_TIPO_CAMPO, A.DES_TIPO_CAMPO;
      RETURN C_CURSOR;
   END FN_LST_EDICION_DATOS_ADIC;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   FUNCTION FN_LISTA_DIAGNOSTICO(A_DES_DIAGNOSTICO CMR.MAE_DIAGNOSTICO.DES_DIAGNOSTICO%TYPE DEFAULT NULL)
      RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN
      IF A_DES_DIAGNOSTICO IS NULL THEN
         OPEN C_CURSOR FOR
            SELECT COD_DIAGNOSTICO, COD_CIE_10, DES_DIAGNOSTICO
              FROM CMR.MAE_DIAGNOSTICO;
      ELSE
         OPEN C_CURSOR FOR
            SELECT F.COD_DIAGNOSTICO, F.COD_CIE_10, F.DES_DIAGNOSTICO
              FROM CMR.MAE_DIAGNOSTICO F
             WHERE (UPPER(DES_DIAGNOSTICO) LIKE
                   '%' || UPPER(A_DES_DIAGNOSTICO) || '%' OR
                   UPPER(F.COD_CIE_10) LIKE
                   '%' || UPPER(A_DES_DIAGNOSTICO) || '%');
      END IF;
      RETURN C_CURSOR;
   END FN_LISTA_DIAGNOSTICO;

   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--

   FUNCTION SP_LISTA_PACIENTE_UM(A_CRITERIO CHAR,
                                 A_COD_lOCAL VARCHAR2 DEFAULT NULL ) RETURN CURSOR_TYPE AS
      CUR_LISTA_PACIENTE CURSOR_TYPE;
      C_FLG_OFF_LINE NUEVO.MAE_LOCAL.FLG_OFF_LINE%TYPE;
   BEGIN

   IF NOT A_COD_lOCAL IS NULL THEN
           SELECT FLG_OFF_LINE
           INTO C_FLG_OFF_LINE
           FROM NUEVO.MAE_LOCAL
           WHERE COD_LOCAL = A_COD_LOCAL;
   ELSE
      C_FLG_OFF_LINE:='0';
   END IF;

   IF C_FLG_OFF_LINE='1' THEN
            OPEN CUR_LISTA_PACIENTE FOR
         SELECT A.POLIZA POLIZA,
                A.PLAN PLAN,
                A.CODASG CODASEG,
                A.NUMITM N,
                A.NOMBRES NOMBRE,
                A.PRT PRT,
                C.CODCONT || '-' || C.NOMCONT NOMCONT,
                A.TIPO_SEGURO ORG,
                0 CREDITO_REAL
           FROM NEWCNV.MAE_GENERAL_TELEFONICA@CENTRAL A,
                NEWCNV.MAE_POLIZA_RIMAC@CENTRAL       B,
                NEWCNV.MAE_CONTRATANTE_RIMAC@CENTRAL  C
          WHERE A.POLIZA = B.POLIZA
            AND B.CODCONT = C.CODCONT
            AND UPPER(A.NOMBRES) LIKE UPPER('' || A_CRITERIO || '%')
            AND B.ESTADO = '1'
          ORDER BY A.NOMBRES;
       ELSE

      OPEN CUR_LISTA_PACIENTE FOR
         SELECT A.POLIZA POLIZA,
                A.PLAN PLAN,
                A.CODASG CODASEG,
                A.NUMITM N,
                A.NOMBRES NOMBRE,
                A.PRT PRT,
                C.CODCONT || '-' || C.NOMCONT NOMCONT,
                A.TIPO_SEGURO ORG,
                0 CREDITO_REAL
           FROM NEWCNV.MAE_GENERAL_TELEFONICA A,
                NEWCNV.MAE_POLIZA_RIMAC       B,
                NEWCNV.MAE_CONTRATANTE_RIMAC  C
          WHERE A.POLIZA = B.POLIZA
            AND B.CODCONT = C.CODCONT
            AND UPPER(A.NOMBRES) LIKE UPPER('' || A_CRITERIO || '%')
            AND B.ESTADO = '1'
          ORDER BY A.NOMBRES;

       END IF;
      RETURN CUR_LISTA_PACIENTE;
   END SP_LISTA_PACIENTE_UM;

   FUNCTION SP_LISTA_PACIENTE_UM( A_CRITERIO CHAR,
                                A_COD_lOCAL VARCHAR2 DEFAULT NULL ,
                                A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE) RETURN CURSOR_TYPE AS
      CUR_LISTA_PACIENTE CURSOR_TYPE;
      C_FLG_OFF_LINE NUEVO.MAE_LOCAL.FLG_OFF_LINE%TYPE;
   BEGIN

   IF NOT A_COD_lOCAL IS NULL THEN
           SELECT FLG_OFF_LINE
           INTO C_FLG_OFF_LINE
           FROM NUEVO.MAE_LOCAL
           WHERE COD_LOCAL = A_COD_LOCAL;
   ELSE
      C_FLG_OFF_LINE:='0';
   END IF;

--   SELECT * FROM NEWCNV.REL_CONTRANTE_CONVENIO

   IF C_FLG_OFF_LINE='1' THEN
            OPEN CUR_LISTA_PACIENTE FOR
/*         SELECT A.POLIZA POLIZA,
                A.PLAN PLAN,
                A.CODASG CODASEG,
                A.NUMITM N,
                A.NOMBRES NOMBRE,
                A.PRT PRT,
                C.CODCONT || '-' || C.NOMCONT NOMCONT,
                A.TIPO_SEGURO ORG,
                0 CREDITO_REAL
           FROM NEWCNV.MAE_ASEGURADO_RIMAC@CENTRAL A,
                NEWCNV.MAE_POLIZA_RIMAC@CENTRAL       B,
                NEWCNV.MAE_CONTRATANTE_RIMAC@CENTRAL  C
          WHERE A.POLIZA = B.POLIZA
            AND B.CODCONT = C.CODCONT
            AND (B.POLIZA, B.CODCONT)IN(
                    SELECT Z1.POLIZA , Z1.CODCONT
                    FROM NEWCNV.MAE_POLIZA_RIMAC@CENTRAL Z1, NEWCNV.REL_CONTRANTE_CONVENIO@CENTRAL Z2
                    WHERE Z1.CODCONT = Z2.CODCONT
                    AND Z2.POLIZA IS NULL
                    AND Z2.COD_CONVENIO = A_COD_CONVENIO
                    UNION
                    SELECT POLIZA, CODCONT
                    FROM NEWCNV.REL_CONTRANTE_CONVENIO@CENTRAL
                    WHERE NOT POLIZA IS NULL
                    AND COD_CONVENIO = A_COD_CONVENIO
                    UNION
                    SELECT Z1.POLIZA, Z1.CODCONT
                    FROM NEWCNV.MAE_POLIZA_RIMAC@CENTRAL Z1, NEWCNV.REL_CONTRANTE_CONVENIO@CENTRAL Z2
                    WHERE Z2.POLIZA IS NULL
                    AND Z2.CODCONT IS NULL
                    AND Z2.COD_CONVENIO = A_COD_CONVENIO
            )
            AND UPPER(A.NOMBRES) LIKE UPPER('' || A_CRITERIO || '%')
            AND B.ESTADO = '1'
          ORDER BY A.NOMBRES;*/

        SELECT A.POLIZA POLIZA,
               A.PLAN PLAN,
               A.CODASG CODASEG,
               A.NUMITM N,
               A.NOMBRES NOMBRE,
               A.PRT PRT,
               C.CODCONT || '-' || C.NOMCONT NOMCONT,
               A.TIPO_SEGURO ORG,
               0 CREDITO_REAL,
               A.CODASG||'-'||A.poLIZA||'-'||A.PLAN||'-'||A.NUMITM COD_REFERENCIA,
               A.NOMBRES DES_CLIENTE
          FROM NEWCNV.MAE_ASEGURADO_RIMAC@CENTRAL   A,
               NEWCNV.MAE_POLIZA_RIMAC@CENTRAL      B,
               NEWCNV.MAE_CONTRATANTE_RIMAC@CENTRAL C,
               NEWCNV.REL_CONTRANTE_CONVENIO@CENTRAL D
         WHERE C.CODCONT = B.CODCONT
            AND B.POLIZA = A.POLIZA
            AND C.CODCONT = D.CODCONT
            AND B.POLIZA = NVL(D.POLIZA, B.POLIZA)
            and a.flg_visualizacion='1'
            AND D.COD_CONVENIO = A_COD_CONVENIO
            AND A.NOMBRES LIKE UPPER('' || A_CRITERIO || '%')
         ORDER BY A.NOMBRES;
       ELSE

      OPEN CUR_LISTA_PACIENTE FOR
         /*SELECT A.POLIZA POLIZA,
                A.PLAN PLAN,
                A.CODASG CODASEG,
                A.NUMITM N,
                A.NOMBRES NOMBRE,
                A.PRT PRT,
                C.CODCONT || '-' || C.NOMCONT NOMCONT,
                A.TIPO_SEGURO ORG,
                0 CREDITO_REAL
           FROM NEWCNV.MAE_ASEGURADO_RIMAC A,
                NEWCNV.MAE_POLIZA_RIMAC       B,
                NEWCNV.MAE_CONTRATANTE_RIMAC  C
          WHERE A.POLIZA = B.POLIZA
            AND B.CODCONT = C.CODCONT
            AND UPPER(A.NOMBRES) LIKE UPPER('' || A_CRITERIO || '%')
            AND (B.POLIZA, B.CODCONT)IN(
                    SELECT Z1.POLIZA , Z1.CODCONT
                    FROM NEWCNV.MAE_POLIZA_RIMAC Z1, NEWCNV.REL_CONTRANTE_CONVENIO Z2
                    WHERE Z1.CODCONT = Z2.CODCONT
                    AND Z2.POLIZA IS NULL
                    AND Z2.COD_CONVENIO = A_COD_CONVENIO
                    UNION
                    SELECT POLIZA, CODCONT
                    FROM NEWCNV.REL_CONTRANTE_CONVENIO
                    WHERE NOT POLIZA IS NULL
                    AND COD_CONVENIO = A_COD_CONVENIO
                    UNION
                    SELECT Z1.POLIZA, Z1.CODCONT
                    FROM NEWCNV.MAE_POLIZA_RIMAC Z1, NEWCNV.REL_CONTRANTE_CONVENIO Z2
                    WHERE Z2.POLIZA IS NULL
                    AND Z2.CODCONT IS NULL
                    AND Z2.COD_CONVENIO = A_COD_CONVENIO
            )
            AND B.ESTADO = '1'
          ORDER BY A.NOMBRES;
*/
        SELECT A.POLIZA POLIZA,
               A.PLAN PLAN,
               A.CODASG CODASEG,
               A.NUMITM N,
               A.NOMBRES NOMBRE,
               A.PRT PRT,
               C.CODCONT || '-' || C.NOMCONT NOMCONT,
               A.TIPO_SEGURO ORG,
               0 CREDITO_REAL,
               A.CODASG||'-'||A.poLIZA||'-'||A.PLAN||'-'||A.NUMITM COD_REFERENCIA,
               A.NOMBRES DES_CLIENTE
          FROM NEWCNV.MAE_ASEGURADO_RIMAC   A,
               NEWCNV.MAE_POLIZA_RIMAC      B,
               NEWCNV.MAE_CONTRATANTE_RIMAC C,
               NEWCNV.REL_CONTRANTE_CONVENIO D
         WHERE C.CODCONT = B.CODCONT
            AND B.POLIZA = A.POLIZA
            AND C.CODCONT = D.CODCONT
            and a.flg_visualizacion='1'
            AND B.POLIZA = NVL(D.POLIZA, B.POLIZA)
            AND D.COD_CONVENIO = A_COD_CONVENIO
            AND A.NOMBRES LIKE UPPER('' || A_CRITERIO || '%')
         ORDER BY A.NOMBRES;
       END IF;
      RETURN CUR_LISTA_PACIENTE;
   END SP_LISTA_PACIENTE_UM;
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@--
   --- Graba relacion de benefiario con el convenio ---
   PROCEDURE SP_GRABA_RECETA(A_NUM_DOCUMENTO       BTLPROD.CAB_RECETA.NUM_DOCUMENTO%TYPE,
                             A_COD_TIPO_DOCUMENTO  BTLPROD.CAB_RECETA.COD_TIPO_DOCUMENTO%TYPE,
                             A_COD_LOCAL           BTLPROD.CAB_RECETA.COD_LOCAL%TYPE,
                             A_FCH_RECETA          BTLPROD.CAB_RECETA.FCH_RECETA%TYPE,
                             A_COD_MEDICO          BTLPROD.CAB_RECETA.COD_MEDICO%TYPE,
                             A_COD_USUARIO         BTLPROD.CAB_RECETA.COD_USUARIO%TYPE,
                             A_COD_LOCAL_EMISION   BTLPROD.CAB_RECETA.COD_LOCAL_EMISION%TYPE,
                             A_CAD_COD_DIAGNOSTICO CHAR,
                             A_COD_CLIENTE         CMR.MAE_BENEFICIARIO.COD_CLIENTE%TYPE DEFAULT NULL,
                             A_NUM_RECETA          BTLPROD.CAB_RECETA.NUM_RECETA%TYPE DEFAULT NULL,
                             A_COD_CONVENIO        BTLPROD.CAB_DOCUMENTO.COD_CONVENIO%TYPE DEFAULT NULL) AS
      V_NUM_ITEM            INTEGER;
      V_CAD_COD_DIAGNOSTICO CMR.PKG_UTIL.TIPO_ARREGLO;
      C_CUENTA              INTEGER;
      C_REG13               BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO%ROWTYPE;
      C_REG14               BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO%ROWTYPE;
      C_REG15               BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO%ROWTYPE;
      C_REG16               BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO%ROWTYPE;
      C_REG17               BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO%ROWTYPE;
      C_REG18               BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO%ROWTYPE;
      C_REG19               BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO%ROWTYPE;
      C_REG20               BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO%ROWTYPE;
      C_REG21               BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO%ROWTYPE;
      C_REG_POLIZA          NEWCNV.MAE_POLIZA_RIMAC%ROWTYPE;

      V_NUM_RECETA    BTLPROD.CAB_RECETA.NUM_RECETA%TYPE;
      V_NOM_PACIENTE  BTLPROD.CAB_RECETA.DES_NOMBRE%TYPE;
      V_NUM_POLIZA    BTLPROD.CAB_RECETA.NUM_POLIZA%TYPE;
      V_NUM_ITEM_P    BTLPROD.CAB_RECETA.NUM_ITEM%TYPE;
      V_COD_ASEGURADO BTLPROD.CAB_RECETA.COD_ASEGURADO%TYPE;
      V_NUM_PLAN      BTLPROD.CAB_RECETA.COD_PLAN%TYPE;
      X_SECUENCIA_ANTIGUA FLOAT;
      X_SECUENCIA_NUEVA FLOAT;
      V_CIA NUEVO.MAE_LOCAL.CIA%TYPE;
   BEGIN

      SELECT CIA INTO V_CIA FROM NUEVO.MAE_LOCAL WHERE COD_LOCAL=A_COD_LOCAL;

      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_COD_DIAGNOSTICO,
                              V_CAD_COD_DIAGNOSTICO,
                              V_NUM_ITEM);

      --************************************************************************
      --************************************************************************
      --************************************************************************

      IF (/*A_COD_CLIENTE IS NULL AND*/
         A_COD_CONVENIO = BTLPROD.PKG_CONSTANTES.CONS_CNV_RIMAC) OR
         (A_COD_CONVENIO = '0000000122') OR (FN_FLG_RIMAC(A_COD_CONVENIO)=1) THEN
         BEGIN

            SELECT *
              INTO C_REG13
              FROM BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO
             WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
               AND NUM_DOCUMENTO = A_NUM_DOCUMENTO
               AND COD_TIPO_CAMPO =
                   BTLPROD.PKG_CONSTANTES.CONS_RIMAC_POLIZA; --consulto la poliza
         EXCEPTION
            WHEN OTHERS THEN
               RAISE_APPLICATION_ERROR(-20005, 'SE PRODUJO UN ERROR CARGANDO LOS DATOS ADICIONALES.'||CHR(13)
                                  ||'CONSTANTE DE POLIZA -->'||BTLPROD.PKG_CONSTANTES.CONS_RIMAC_POLIZA||CHR(13)
                                  ||'POLIZA -->'||A_COD_CLIENTE||CHR(13)
                                  ||SQLERRM);

         END;

         BEGIN
            SELECT *
              INTO C_REG14
              FROM BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO
             WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
               AND NUM_DOCUMENTO = A_NUM_DOCUMENTO
               AND COD_TIPO_CAMPO = BTLPROD.PKG_CONSTANTES.CONS_RIMAC_PLAN; --consulto el plan

         EXCEPTION
            WHEN OTHERS THEN
               RAISE_APPLICATION_ERROR(-20005, 'SE PRODUJO UN ERROR CARGANDO LOS DATOS ADICIONALES.'||CHR(13)
                                  ||'CONSTANTE DE PLAN -->'||BTLPROD.PKG_CONSTANTES.CONS_RIMAC_PLAN ||CHR(13)
                                  ||SQLERRM);
         END;

         BEGIN
            SELECT *
              INTO C_REG15
              FROM BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO
             WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
               AND NUM_DOCUMENTO = A_NUM_DOCUMENTO
               AND COD_TIPO_CAMPO =
                   BTLPROD.PKG_CONSTANTES.CONS_RIMAC_COD_ASEG; --consulto el codigo se asegurado
         EXCEPTION
            WHEN OTHERS THEN
               RAISE_APPLICATION_ERROR(-20005, 'SE PRODUJO UN ERROR CARGANDO LOS DATOS ADICIONALES.'||CHR(13)
                                  ||'CONSTANTE DE COD. ASEG. -->'||BTLPROD.PKG_CONSTANTES.CONS_RIMAC_COD_ASEG ||CHR(13)
                                  || SQLERRM);
         END;
         BEGIN
            SELECT *
              INTO C_REG16
              FROM BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO
             WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
               AND NUM_DOCUMENTO = A_NUM_DOCUMENTO
               AND COD_TIPO_CAMPO = BTLPROD.PKG_CONSTANTES.CONS_RIMAC_ORDEN; --consulto el orden
         EXCEPTION
            WHEN OTHERS THEN
               RAISE_APPLICATION_ERROR(-20005, 'SE PRODUJO UN ERROR CARGANDO LOS DATOS ADICIONALES.'||CHR(13)
                      ||'CONSTANTE DE ORDEN -->'||BTLPROD.PKG_CONSTANTES.CONS_RIMAC_ORDEN||CHR(13)
                      ||SQLERRM);
         END;
         BEGIN
            SELECT *
              INTO C_REG17
              FROM BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO
             WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
               AND NUM_DOCUMENTO = A_NUM_DOCUMENTO
               AND COD_TIPO_CAMPO =
                   BTLPROD.PKG_CONSTANTES.CONS_RIMAC_NOM_PACIENTE; --consulto el nombre del paciente
         EXCEPTION
            WHEN OTHERS THEN
              RAISE_APPLICATION_ERROR(-20005, 'SE PRODUJO UN ERROR CARGANDO LOS DATOS ADICIONALES.'||CHR(13)
                        ||'CONSTANTE DE NOM. PAC. -->'||BTLPROD.PKG_CONSTANTES.CONS_RIMAC_NOM_PACIENTE||CHR(13)
                        ||SQLERRM);
         END;
         BEGIN
            SELECT *
              INTO C_REG18
              FROM BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO
             WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
               AND NUM_DOCUMENTO = A_NUM_DOCUMENTO
               AND COD_TIPO_CAMPO = BTLPROD.PKG_CONSTANTES.CONS_RIMAC_PRT; --consutlo el PRT
         EXCEPTION
            WHEN OTHERS THEN
               RAISE_APPLICATION_ERROR(-20005,
                                       BTLPROD.PKG_CONSTANTES.CONS_RIMAC_PRT || ' ' ||
                                       SQLERRM);
         END;
         BEGIN
            SELECT *
              INTO C_REG19
              FROM BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO
             WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
               AND NUM_DOCUMENTO = A_NUM_DOCUMENTO
               AND COD_TIPO_CAMPO =
                   BTLPROD.PKG_CONSTANTES.CONS_RIMAC_NOM_CONTRANTE; --consutlo el nombre del contratante
         EXCEPTION
            WHEN OTHERS THEN
               RAISE_APPLICATION_ERROR(-20005,
                                       BTLPROD.PKG_CONSTANTES.CONS_RIMAC_NOM_CONTRANTE || ' ' ||
                                       SQLERRM);
         END;
         BEGIN
            SELECT *
              INTO C_REG20
              FROM BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO
             WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
               AND NUM_DOCUMENTO = A_NUM_DOCUMENTO
               AND COD_TIPO_CAMPO =
                   BTLPROD.PKG_CONSTANTES.CONS_RIMAC_ORIGEN; --consutlo el origen
         EXCEPTION
            WHEN OTHERS THEN
               RAISE_APPLICATION_ERROR(-20005,
                                       BTLPROD.PKG_CONSTANTES.CONS_RIMAC_ORIGEN || ' ' ||
                                       SQLERRM);
         END;
         BEGIN
            SELECT *
              INTO C_REG21
              FROM BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO
             WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
               AND NUM_DOCUMENTO = A_NUM_DOCUMENTO
               AND COD_TIPO_CAMPO =
                   BTLPROD.PKG_CONSTANTES.CONS_RIMAC_NUM_RECETA; --consulto el numero de receta
         EXCEPTION
            WHEN OTHERS THEN
                RAISE_APPLICATION_ERROR(-20005, 'SE PRODUJO UN ERROR CARGANDO LOS DATOS ADICIONALES DEL DOCUMENTO '||A_COD_TIPO_DOCUMENTO || '-' ||A_NUM_DOCUMENTO || CHR(13)||SQLERRM);
         END;
      ELSE
         --TODOS LOS CASOS MENOS RIMAC
         IF V_CIA ='10' OR V_CIA ='11' THEN
             BEGIN
                SELECT T.DES_CLIENTE,
                       X.NUM_POLIZA,
                       X.NUM_ITEM,
                       X.COD_ASEGURADO,
                       X.NUM_PLAN
                  INTO V_NOM_PACIENTE,
                       V_NUM_POLIZA,
                       V_NUM_ITEM_P,
                       V_COD_ASEGURADO,
                       V_NUM_PLAN
                  FROM CMR.MAE_BENEFICIARIO X, CMR.MAE_CLIENTE T
                 WHERE X.COD_CLIENTE = T.COD_CLIENTE
                   AND X.COD_CLIENTE = A_COD_CLIENTE
                   AND X.COD_CONVENIO = A_COD_CONVENIO
                   AND X.FLG_ACTIVO = '1';
             EXCEPTION
                WHEN NO_DATA_FOUND THEN
                   RAISE_APPLICATION_ERROR('-20000',
                                           'NO SE ENCONTRO EL BENEFICIARIO ' ||
                                           A_COD_CLIENTE || ' EN EL CONVENIO ' ||
                                           A_COD_CONVENIO);
                WHEN OTHERS THEN
                   RAISE_APPLICATION_ERROR('-20000','SE PRODUJO UN ERROR CARGANDO LOS DATOS DEL BENEFICIARIO.'||CHR(13)||SQLERRM);

             END;
         ELSE
             BEGIN
                SELECT T.DES_CLIENTE,
                       X.NUM_POLIZA,
                       X.NUM_ITEM,
                       X.COD_ASEGURADO,
                       X.NUM_PLAN
                  INTO V_NOM_PACIENTE,
                       V_NUM_POLIZA,
                       V_NUM_ITEM_P,
                       V_COD_ASEGURADO,
                       V_NUM_PLAN
                  FROM CMR.MAE_BENEFICIARIO X, CMR.MAE_CLIENTE T
                 WHERE X.COD_CLIENTE = T.COD_CLIENTE
                   AND X.COD_CLIENTE = A_COD_CLIENTE
                   AND X.COD_CONVENIO = A_COD_CONVENIO;
             EXCEPTION
                WHEN NO_DATA_FOUND THEN
                   RAISE_APPLICATION_ERROR('-20000',
                                           'NO SE ENCONTRO EL BENEFICIARIO ' ||
                                           A_COD_CLIENTE || ' EN EL CONVENIO ' ||
                                           A_COD_CONVENIO);
                WHEN OTHERS THEN
                   RAISE_APPLICATION_ERROR('-20000','SE PRODUJO UN ERROR CARGANDO LOS DATOS DEL BENEFICIARIO.'||CHR(13)||SQLERRM);

             END;
         END IF;


         BEGIN
            SELECT *
              INTO C_REG21
              FROM BTLPROD.REL_DOCUMENTO_VTA_TIPO_CAMPO
             WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
               AND NUM_DOCUMENTO = A_NUM_DOCUMENTO
               AND COD_TIPO_CAMPO IN ('0000000031', '0000000023')
               AND CIA = V_CIA;
               --AND COD_TIPO_CAMPO = '0000000031'; --consulto el numero de receta
         EXCEPTION
            WHEN OTHERS THEN
               RAISE_APPLICATION_ERROR('-20000',
                                       'NO ESTA CONFIGURADA LA RECETA.'||CHR(13)||SQLERRM||CHR(13)||A_COD_TIPO_DOCUMENTO||'|'||A_NUM_DOCUMENTO);
         END;
      END IF;

      --************************************************************************

      V_NUM_RECETA := C_REG21.DES_VALOR; --numero de receta

      IF (/*A_COD_CLIENTE IS NULL AND*/
         A_COD_CONVENIO = BTLPROD.PKG_CONSTANTES.CONS_CNV_RIMAC
         OR FN_FLG_RIMAC(A_COD_CONVENIO)=1) THEN
         --   IF A_COD_CLIENTE IS NULL THEN
         V_NOM_PACIENTE  := C_REG17.DES_VALOR; --nombre del paciente
         V_NUM_POLIZA    := C_REG13.DES_VALOR; --numero de poliza
         V_NUM_ITEM_P    := C_REG16.DES_VALOR; --numero de item
         V_COD_ASEGURADO := C_REG15.DES_VALOR; --codigo asegurado
         V_NUM_PLAN      := C_REG14.DES_VALOR; --plan
      END IF;

      IF NOT A_NUM_RECETA IS NULL THEN
         V_NUM_RECETA := A_NUM_RECETA;
      END IF;

      --************************************************************************
      --************************************************************************
      --aca voy  ha hacer todas las validaciones de la receta
      IF A_NUM_DOCUMENTO IS NULL THEN
         --VALIDO QUE EL DOCUMENTO NO SEA NULO
         RAISE_APPLICATION_ERROR('-20000',
                                 'EL NUMERO DE DOCUMENTO ESTA VACIO.');
      END IF;

      IF A_COD_TIPO_DOCUMENTO IS NULL THEN
         --VALIDO QUE EL TIPO DE DOCUMENTO NO SEA NULO
         RAISE_APPLICATION_ERROR('-20000',
                                 'EL TIPO DE DOCUMENTO SE ENCUENTRA VACIO.');
      END IF;

      IF A_COD_LOCAL IS NULL THEN
         --VALIDO QUE EL local NO SEA NULO
         RAISE_APPLICATION_ERROR('-20000',
                                 'EL CODIGO DE LOCAL SE ENCUENTRA VACIO.');
      END IF;

      IF V_NUM_RECETA IS NULL THEN
         --VALIDO QUE EL numero de receta no se nula
         RAISE_APPLICATION_ERROR('-20000',
                                 'EL NUMERO DE RECETA NO PUEDE SER VACIO' || '**' ||
                                 V_NUM_RECETA || '**');
      END IF;

      IF V_NOM_PACIENTE IS NULL THEN
         --Valido que el nombre del paciente no sea nulo
         RAISE_APPLICATION_ERROR('-20000',
                                 'EL NOMBRE DEL PACIENTE SE ENCUENTRA VACIO');
      END IF;

      IF V_NUM_POLIZA IS NULL AND A_COD_CLIENTE IS NULL THEN
         --Numero de poliza
         RAISE_APPLICATION_ERROR('-20000', 'EL NUMERO DE POLIZA VACIO');
      ELSE
         /*
         BEGIN--ACA ME VOY HASTA LA TABLA PARA VALIDAR LA POLIZA
           SELECT * INTO C_REG_POLIZA
           FROM NEWCNV.MAE_POLIZA_RIMAC X
           WHERE POLIZA = C_REG13.DES_VALOR;
           IF NOT C_REG_POLIZA.ESTADO = '1' THEN
              RAISE_APPLICATION_ERROR('-20000','La poliza no se encuentra activa ' || C_REG13.DES_VALOR);
           END IF;
         EXCEPTION
           WHEN NO_DATA_FOUND THEN
             RAISE_APPLICATION_ERROR('-20000','La poliza no existe' || C_REG13.DES_VALOR);
           WHEN OTHERS THEN
             RAISE_APPLICATION_ERROR('-20000','Ha ocurrido un error al evaluar la poliza'||SQLERRM);
         END;
         */
         null;
      END IF;

      IF V_NUM_ITEM_P IS NULL AND A_COD_CLIENTE IS NULL THEN
         --Valido que el nombre del paciente no sea nulo
         RAISE_APPLICATION_ERROR('-20000',
                                 'EL NUMERO DE ITEM SE ENCUENTRA VACIO');
      END IF;

      IF V_COD_ASEGURADO IS NULL AND A_COD_CLIENTE IS NULL THEN
         --Valido el codigo de asegurado del beneficiario
         RAISE_APPLICATION_ERROR('-20000',
                                 'EL CODIGO DE ASEGURADO SE ENCUENTRA VACIO');
      END IF;

      IF V_NUM_PLAN IS NULL AND A_COD_CLIENTE IS NULL THEN
         --Valido el plan del beneficiario
         RAISE_APPLICATION_ERROR('-20000',
                                 'EL PLAN SE ENCUENTRA VACIO ***' ||
                                 C_REG14.DES_VALOR || '***');
      END IF;


      IF A_FCH_RECETA IS NULL THEN--Valido el plan del beneficiario
         RAISE_APPLICATION_ERROR('-20000','LA FECHA SE ENCUENTRA VACIA '||A_FCH_RECETA);
      ELSE
         IF V_CIA ='10' OR V_CIA ='11' THEN
            BEGIN
                 IF (ABS(TRUNC(SYSDATE) - trunc(A_FCH_RECETA) ) > 5) THEN
                   RAISE_APPLICATION_ERROR('-20000','LA RECETA SE ENCUENTRA VENCIDA '|| TRUNC(SYSDATE) ||'<-->'|| trunc(A_FCH_RECETA) ||' dias');
                 END IF;
            END;
         END IF;
      END IF;


      IF A_COD_MEDICO IS NULL THEN
         --Valido que el nombre del paciente no sea nulo
         RAISE_APPLICATION_ERROR('-20000', 'DEBE INDICAR EL MEDICO');
         --falta validar con tabla de medicos
      END IF;
      IF A_COD_LOCAL_EMISION IS NULL THEN
         --Valido que el nombre del paciente no sea nulo
         RAISE_APPLICATION_ERROR('-20000',
                                 'No se se?alo el local de emision de la receta');
         --falta validar con tabla de medicos
      END IF;
      IF A_CAD_COD_DIAGNOSTICO IS NULL THEN
         RAISE_APPLICATION_ERROR('-20000', 'El diagnostico esta vacio');
         --falta validar con tabla de medicos
      END IF;
      --************************************************************************
      --************************************************************************
      --************************************************************************
      C_CUENTA := 0;

BEGIN
        --&
        BEGIN
         SELECT SEC_VENTA
         INTO X_SECUENCIA_ANTIGUA
         FROM BTLPROD.AUX_SEC_VENTA
         WHERE COD_TIPO_DOCUMENTO  = A_COD_TIPO_DOCUMENTO
         AND NUM_DOCUMENTO  = A_NUM_DOCUMENTO;
                  SELECT SEC_VENTA
         INTO X_SECUENCIA_NUEVA
         FROM BTLPROD.CAB_RECETA
         WHERE NUM_RECETA = V_NUM_RECETA;

         IF NOT X_SECUENCIA_ANTIGUA = X_SECUENCIA_NUEVA THEN
           RAISE_APPLICATION_ERROR('-20000',
                                    'EL NUMERO DE RECETA ' || V_NUM_RECETA ||
                                    ' YA EXISTE, EN LA SECUENCIA DE VENTA');
         END IF;

       EXCEPTION
       WHEN OTHERS  THEN
            NULL;
       END;

     --&
         /*
         SELECT COUNT(1)
           INTO C_CUENTA
           FROM BTLPROD.CAB_RECETA R
          WHERE R.NUM_RECETA = V_NUM_RECETA
            AND R.FLG_ACTIVO = '1'
            AND R.COD_TIPO_DOCUMENTO <>
                BTLPROD.PKG_CONSTANTES.CONS_TIP_DOC_PROF;
         IF C_CUENTA >= 1 THEN
            RAISE_APPLICATION_ERROR('-20000',
                                    '***' || C_CUENTA || '**' ||
                                    'El numero ' || V_NUM_RECETA ||
                                    ' de Receta ya existe');
         END IF;
         */


      END;

      BEGIN
		--ERIOS 26.08.2014 Se agrega el campo COD_CIA
         INSERT INTO BTLPROD.CAB_RECETA
            (CIA,
			 NUM_DOCUMENTO,
             COD_TIPO_DOCUMENTO,
             COD_LOCAL,
             NUM_RECETA,
             DES_NOMBRE,
             NUM_POLIZA,
             NUM_ITEM,
             COD_ASEGURADO,
             COD_PLAN,
             FCH_RECETA,
             COD_MEDICO,
             FCH_REGISTRO,
             COD_USUARIO,
             FCH_ACTUALIZA,
             COD_USUARIO_ACTUALIZA,
             COD_LOCAL_EMISION,
             FLG_ACTIVO,
             NUM_RECETA_ORIGINAL
             )
         VALUES
            (V_CIA,
			 A_NUM_DOCUMENTO, --numero de documento
             A_COD_TIPO_DOCUMENTO, --codigo de tipo de documento
             A_COD_LOCAL, --codigo del local
             V_NUM_RECETA, --numero de receta
             V_NOM_PACIENTE, --nombre del paciente
             V_NUM_POLIZA, --numero de poliza
             V_NUM_ITEM_P, --numero de item
             V_COD_ASEGURADO, --codigo asegurado
             V_NUM_PLAN, --plan
             A_FCH_RECETA,              --fecha de la receta
             A_COD_MEDICO, --codigo del medico
             SYSDATE,
             A_COD_USUARIO,
             NULL,
             NULL,
             A_COD_LOCAL_EMISION,
             '1',
             NULL);
      EXCEPTION
         WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR('-20000',
                                    'SE PRODUJO UN ERROR INSERTANDO LA CABECERA DE LA RECETA.' ||CHR(13) ||
                                    sqlerrm);
      END;
      --************************************************************************
      --************************************************************************
      --************************************************************************
      BEGIN
         FOR I IN 1 .. V_NUM_ITEM LOOP
            INSERT INTO BTLPROD.DET_RECETA
               (CIA,NUM_DOCUMENTO, COD_TIPO_DOCUMENTO, COD_DIAGNOSTICO)
            VALUES
               (V_CIA,A_NUM_DOCUMENTO,
                A_COD_TIPO_DOCUMENTO,
                TRIM(V_CAD_COD_DIAGNOSTICO(I)));
         END LOOP;
      EXCEPTION
         WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR('-20000',
                                    'SE PRODUJO UN ERROR INSERTANDO EL DETALLE DE LA RECETA.' ||CHR(13)||sqlerrm );
      END;
      --PARCHECITO
      --Autor:Juan Arturo Escate Espichan
      --Fecha:26/09/2008
      --Proposito: ampliar la llave y la validacion para que puede generar dos o mas documento en una sola venta
      UPDATE BTLPROD.CAB_RECETA
      SET SEC_VENTA  = ( SELECT SEC_VENTA
                         FROM BTLPROD.AUX_SEC_VENTA
                         WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
                         AND NUM_DOCUMENTO = A_NUM_DOCUMENTO 
						 AND CIA = V_CIA)
       WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
       AND NUM_DOCUMENTO = A_NUM_DOCUMENTO 
	   AND CIA = V_CIA;

      --************************************************************************
      --************************************************************************
      --************************************************************************
      /*       EXCEPTION
             WHEN OTHERS THEN
              RAISE_APPLICATION_ERROR('-20000','Error en la grabacion');
      */

   END SP_GRABA_RECETA;

   FUNCTION FN_LISTA_CAB_RECETA(A_COD_TIPO_DOCUMENTO BTLPROD.CAB_DOCUMENTO.COD_TIPO_DOCUMENTO%TYPE,
                                A_NUM_DOCUMENTO      BTLPROD.CAB_DOCUMENTO.NUM_DOCUMENTO%TYPE)
      RETURN CURSOR_TYPE IS
      C_CURSOR  CURSOR_TYPE;
      C_DETALLE VARCHAR2(200);

      CURSOR C_CURSOR_DETALLE IS
         SELECT DIAG.COD_CIE_10
           FROM BTLPROD.DET_RECETA REC, CMR.MAE_DIAGNOSTICO DIAG
          WHERE REC.COD_DIAGNOSTICO = DIAG.COD_DIAGNOSTICO
            AND COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
            AND NUM_DOCUMENTO = A_NUM_DOCUMENTO;

   BEGIN
      FOR REG IN C_CURSOR_DETALLE LOOP
         C_DETALLE := C_DETALLE || ',' || REG.COD_CIE_10;
      END LOOP;

      OPEN C_CURSOR FOR
         SELECT NUM_RECETA "Cod. Atencion",
                COD_ASEGURADO "Nro Carnet",
                (SELECT X.NUM_CMP || '-' || X.DES_NOM_MEDICO || ',' ||
                        X.DES_APE_MEDICO
                   FROM CMR.MAE_MEDICO X
                  WHERE X.COD_MEDICO = CAB.COD_MEDICO) "Medico",
                C_DETALLE "CIE10",
                (SELECT T.DES_LOCAL
                   FROM NUEVO.MAE_LOCAL T
                  WHERE COD_LOCAL = CAB.COD_LOCAL_EMISION) "Unidad Medica",
                CAB.NUM_RECETA,
                TO_CHAR(CAB.FCH_RECETA, 'DD/MM/YYYY') FECHA,
                CAB.COD_LOCAL_EMISION,
                CAB.COD_MEDICO
           FROM BTLPROD.CAB_RECETA CAB
          WHERE COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
            AND NUM_DOCUMENTO = A_NUM_DOCUMENTO;
      RETURN C_CURSOR;
   END FN_LISTA_CAB_RECETA;

   FUNCTION FN_LISTA_DET_RECETA(A_COD_TIPO_DOCUMENTO BTLPROD.CAB_DOCUMENTO.COD_TIPO_DOCUMENTO%TYPE,
                                A_NUM_DOCUMENTO      BTLPROD.CAB_DOCUMENTO.NUM_DOCUMENTO%TYPE)
      RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT *
           FROM BTLPROD.DET_RECETA A, CMR.MAE_DIAGNOSTICO B
          WHERE A.COD_DIAGNOSTICO = B.COD_DIAGNOSTICO
            AND COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
            AND NUM_DOCUMENTO = A_NUM_DOCUMENTO;

      RETURN C_CURSOR;
   END FN_LISTA_DET_RECETA;

   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ---
   --- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ---
   --- ============================================================================== ---
   ---- GRABACION DE DOCUMENTO DE VERIFICACION ----
   ----        HECHO EL 15/05/2007
   --- ============================================================================== ---
   PROCEDURE SP_GRABA_DOCUM_VERIF(A_COD_DOCUMENTO_VERIFICACION CMR.MAE_DOCUMENTO_VERIFICACION.COD_DOCUMENTO_VERIFICACION%TYPE,
                                  A_DES_DOCUMENTO_VERIFICACION CMR.MAE_DOCUMENTO_VERIFICACION.DES_DOCUMENTO_VERIFICACION%TYPE,
                                  A_DES_ABREVIATURA            CMR.MAE_DOCUMENTO_VERIFICACION.DES_ABREVIATURA%TYPE,
                                  A_FLG_ESTADO                 CMR.MAE_DOCUMENTO_VERIFICACION.FLG_ESTADO%TYPE,
                                  A_COD_USUARIO                CMR.MAE_DOCUMENTO_VERIFICACION.COD_USUARIO%TYPE) AS
      C_DOCUM_VERIF CMR.MAE_DOCUMENTO_VERIFICACION.COD_DOCUMENTO_VERIFICACION%TYPE;

   BEGIN

      IF A_COD_DOCUMENTO_VERIFICACION IS NULL THEN
         BEGIN
            C_DOCUM_VERIF := CMR.FN_DEV_PROX_SEC('CMR',
                                                 'MAE_DOCUMENTO_VERIFICACION');

            INSERT INTO CMR.MAE_DOCUMENTO_VERIFICACION
               (COD_DOCUMENTO_VERIFICACION,
                DES_DOCUMENTO_VERIFICACION,
                DES_ABREVIATURA,
                FLG_ESTADO,
                COD_USUARIO,
                FCH_REGISTRA,
                COD_USUARIO_ACTUALIZA,
                FCH_ACTUALIZA)
            VALUES
               (C_DOCUM_VERIF,
                A_DES_DOCUMENTO_VERIFICACION,
                A_DES_ABREVIATURA,
                A_FLG_ESTADO,
                A_COD_USUARIO,
                SYSDATE,
                NULL,
                NULL);
         END;
      ELSE
         --C_DOCUM_VERIF := A_COD_DOCUMENTO_VERIFICACION;
         UPDATE CMR.MAE_DOCUMENTO_VERIFICACION
            SET DES_DOCUMENTO_VERIFICACION = A_DES_DOCUMENTO_VERIFICACION,
                DES_ABREVIATURA            = A_DES_ABREVIATURA,
                FLG_ESTADO                 = A_FLG_ESTADO,
                COD_USUARIO_ACTUALIZA      = A_COD_USUARIO,
                FCH_ACTUALIZA              = SYSDATE
          WHERE COD_DOCUMENTO_VERIFICACION = A_COD_DOCUMENTO_VERIFICACION;
      END IF;

      COMMIT;

   END SP_GRABA_DOCUM_VERIF;

   --- Para saber si venta es por Convenio y se vende por Delivery
   --- Determinar si es un 100% asume todo la empresa
   --- HECHO 06/07/2007 Por CRUEDA
   FUNCTION FN_VTA_CNV_X_DLV_EMPRESA_ASUME(A_CIA          CMR.MAE_CONVENIO.CIA%TYPE,
                                           A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
                                           A_FLG_ACTIVO   CMR.MAE_CONVENIO.FLG_ACTIVO%TYPE, -- 1  ACTIVO
                                           A_FLG_TIPO_CNV CMR.MAE_CONVENIO.FLG_TIPO_CONVENIO%TYPE -- 2  100% ASUME LA EMPRESA
                                           ) RETURN FLOAT AS

      V_PCT_EMP CMR.MAE_CONVENIO.PCT_EMPRESA%TYPE;
   BEGIN
      SELECT CN.PCT_EMPRESA
        INTO V_PCT_EMP
        FROM CMR.MAE_CONVENIO CN
       WHERE CN.CIA = A_CIA
         AND CN.COD_CONVENIO = A_COD_CONVENIO
         AND CN.FLG_ACTIVO = A_FLG_ACTIVO
         AND CN.FLG_TIPO_CONVENIO = A_FLG_TIPO_CNV;

      RETURN V_PCT_EMP;
   EXCEPTION
      WHEN NO_DATA_FOUND THEN
         RETURN 0;

   END FN_VTA_CNV_X_DLV_EMPRESA_ASUME;

   --- Autor : JRAZURI
   --- Fecha : 18/09/2007
   --- Pone a cero lo consumido cuando se ha pasado un dia despues del corte
   --- LISTA DE MEDICOS ---
   FUNCTION FN_LISTA_ZONAL(A_COD_ZONAL CMR.MAE_ZONAL.DES_ZONAL%TYPE DEFAULT NULL)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;
   BEGIN

      IF A_COD_ZONAL IS NULL THEN
         OPEN C_CURSOR FOR
            SELECT COD_ZONAL,
                   DES_ZONAL,
                   FLG_ACTIVO,
                   COD_USUARIO,
                   FCH_REGISTRA
              FROM CMR.MAE_ZONAL
             WHERE FLG_ACTIVO = '1';
      ELSE
         OPEN C_CURSOR FOR
            SELECT COD_ZONAL,
                   DES_ZONAL,
                   FLG_ACTIVO,
                   COD_USUARIO,
                   FCH_REGISTRA
              FROM CMR.MAE_ZONAL
             WHERE FLG_ACTIVO = '1'
               AND COD_ZONAL = A_COD_ZONAL;
      END IF;

      RETURN C_CURSOR;
   END FN_LISTA_ZONAL;

   PROCEDURE SP_GRABA_ZONAL(A_COD_ZONAL   CMR.MAE_ZONAL.COD_ZONAL%TYPE DEFAULT NULL,
                            A_DES_ZONAL   CMR.MAE_ZONAL.DES_ZONAL%TYPE,
                            A_FLG_ACTIVO  CMR.MAE_ZONAL.FLG_ACTIVO%TYPE,
                            A_COD_USUARIO CMR.MAE_ZONAL.COD_USUARIO%TYPE) AS
      C_COD_ZONAL CMR.MAE_ZONAL.COD_ZONAL%TYPE;
   BEGIN
      IF A_COD_ZONAL IS NULL THEN
         SELECT LPAD(NVL(MAX(COD_ZONAL), 0) + 1, 2, '0')
           INTO C_COD_ZONAL
           FROM CMR.MAE_ZONAL;

         INSERT INTO CMR.MAE_ZONAL
            (COD_ZONAL, DES_ZONAL, FLG_ACTIVO, COD_USUARIO, FCH_REGISTRA)
         VALUES
            (C_COD_ZONAL,
             A_DES_ZONAL,
             A_FLG_ACTIVO,
             A_COD_USUARIO,
             SYSDATE);

      ELSE
         UPDATE CMR.MAE_ZONAL
            SET DES_ZONAL    = A_DES_ZONAL,
                FLG_ACTIVO   = A_FLG_ACTIVO,
                COD_USUARIO  = A_COD_USUARIO,
                FCH_REGISTRA = SYSDATE
          WHERE COD_ZONAL = A_COD_ZONAL;
      END IF;

      COMMIT;
   EXCEPTION
      WHEN OTHERS THEN
         RAISE_APPLICATION_ERROR(-20000,
                                 'SE PRODUJO UN ERROR INSERTANDO EL ZONAL.' ||CHR(13)||sqlerrm);

   END SP_GRABA_ZONAL;

---------------------------------------------------------------------------------
--Autor : Juan Arturo Escate Espichan
--Fecha : 20/07/2009
--Motivo: Recargamos la funcion para validar posteriormente en la grabacion
   FUNCTION FN_REG_LOTE(A_COD_MODALIDAD_VENTA BTLPROD.REL_MODALIDAD_LOTE.COD_MODALIDAD_VENTA%TYPE,
                    A_COD_CONVENIO        BTLPROD.REL_MODALIDAD_LOTE.COD_CONVENIO%TYPE DEFAULT NULL,
                    A_MTO_TOTAL           FLOAT DEFAULT NULL)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;

   BEGIN

          IF NOT A_MTO_TOTAL IS NULL THEN
             IF A_COD_CONVENIO IS NULL AND
                A_COD_MODALIDAD_VENTA <> BTLPROD.PKG_CONSTANTES.CONS_MODAL_VTA_002_CONVEMP THEN
                    OPEN C_CURSOR FOR
                    SELECT *
                    FROM BTLPROD.REL_MODALIDAD_LOTE
                    WHERE COD_MODALIDAD_VENTA = A_COD_MODALIDAD_VENTA
                    AND A_MTO_TOTAL BETWEEN  NVL(MTO_MINIMO, A_MTO_TOTAL) AND NVL(MTO_MAXIMO, A_MTO_TOTAL);
             ELSE
                    OPEN C_CURSOR FOR
                    SELECT *
                    FROM BTLPROD.REL_MODALIDAD_LOTE
                    WHERE COD_MODALIDAD_VENTA = A_COD_MODALIDAD_VENTA
                    AND COD_CONVENIO = A_COD_CONVENIO
                    AND A_MTO_TOTAL BETWEEN  NVL(MTO_MINIMO, A_MTO_TOTAL) AND NVL(MTO_MAXIMO, A_MTO_TOTAL);
             END IF;
          ELSE
                IF A_COD_CONVENIO IS NULL AND
                A_COD_MODALIDAD_VENTA <>
                BTLPROD.PKG_CONSTANTES.CONS_MODAL_VTA_002_CONVEMP THEN
                      OPEN C_CURSOR FOR
                      SELECT *
                      FROM BTLPROD.REL_MODALIDAD_LOTE
                      WHERE COD_MODALIDAD_VENTA = A_COD_MODALIDAD_VENTA;
                ELSE
                      OPEN C_CURSOR FOR
                      SELECT *
                      FROM BTLPROD.REL_MODALIDAD_LOTE
                      WHERE COD_MODALIDAD_VENTA = A_COD_MODALIDAD_VENTA
                      AND COD_CONVENIO = A_COD_CONVENIO;
                END IF;
          END IF;

      RETURN C_CURSOR;
   END ;

---------------------------------------------------------------------------------
--Autor : Juan Arturo Escate Espichan
--Fecha : 20/07/2009
--Motivo: Recargamos la funcion para validar posteriormente en la grabacion
   FUNCTION FN_LOTE(A_COD_MODALIDAD_VENTA BTLPROD.REL_MODALIDAD_LOTE.COD_MODALIDAD_VENTA%TYPE,
                    A_COD_CONVENIO        BTLPROD.REL_MODALIDAD_LOTE.COD_CONVENIO%TYPE DEFAULT NULL,
                    A_MTO_TOTAL           FLOAT DEFAULT NULL)
      RETURN VARCHAR2 AS
      C_MAX_VALOR CHAR(1);
   BEGIN
      C_MAX_VALOR := '0';
      --RETURN 0;
      IF NOT A_MTO_TOTAL IS NULL THEN

              IF A_COD_CONVENIO IS NULL AND
                 A_COD_MODALIDAD_VENTA <>
                 BTLPROD.PKG_CONSTANTES.CONS_MODAL_VTA_002_CONVEMP THEN
                 SELECT COUNT(1)
                   INTO C_MAX_VALOR
                   FROM BTLPROD.REL_MODALIDAD_LOTE
                  WHERE COD_MODALIDAD_VENTA = A_COD_MODALIDAD_VENTA
                  AND A_MTO_TOTAL BETWEEN  NVL(MTO_MINIMO, A_MTO_TOTAL) AND NVL(MTO_MAXIMO, A_MTO_TOTAL);
              ELSE
                 SELECT COUNT(1)
                   INTO C_MAX_VALOR
                   FROM BTLPROD.REL_MODALIDAD_LOTE
                  WHERE COD_MODALIDAD_VENTA = A_COD_MODALIDAD_VENTA
                    AND COD_CONVENIO = A_COD_CONVENIO
                    AND A_MTO_TOTAL BETWEEN  NVL(MTO_MINIMO, A_MTO_TOTAL) AND NVL(MTO_MAXIMO, A_MTO_TOTAL);
              END IF;
      ELSE
               IF A_COD_CONVENIO IS NULL AND
                   A_COD_MODALIDAD_VENTA <>
                   BTLPROD.PKG_CONSTANTES.CONS_MODAL_VTA_002_CONVEMP THEN

                   SELECT COUNT(1)
                     INTO C_MAX_VALOR
                     FROM BTLPROD.REL_MODALIDAD_LOTE
                    WHERE COD_MODALIDAD_VENTA = A_COD_MODALIDAD_VENTA
                    AND FLG_MUESTRA_SIEMPRE=1;
                ELSE
                   SELECT COUNT(1)
                     INTO C_MAX_VALOR
                     FROM REL_MODALIDAD_LOTE
                    WHERE COD_MODALIDAD_VENTA = A_COD_MODALIDAD_VENTA
                      AND COD_CONVENIO = A_COD_CONVENIO
                      AND FLG_MUESTRA_SIEMPRE=1;
                END IF;
      END IF;

      RETURN C_MAX_VALOR;
   EXCEPTION
      WHEN NO_DATA_FOUND THEN
         RETURN 0;
   END ;

   -----------------------------------------------------------------------
   --Autor : Arturo Escate
   --Fecha : 07/02/2008
   --Proposito : Mostrar los convenios que tiene petitorio
   FUNCTION FN_LISTA_C_PETITORIO RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT *
           FROM CMR.MAE_CONVENIO
          WHERE COD_CONVENIO IN (select DISTINCT COD_CONVENIO
                                   from BTLPROD.REL_PETITORIO_CONVENIO
                                  WHERE FLG_ACTIVO = '1')
            AND FLG_ACTIVO = '1'
          ORDER BY DES_CONVENIO;

      RETURN C_CURSOR;
   END FN_LISTA_C_PETITORIO;

   -----------------------------------------------------------------------
   --Autor : JRAZURI
   --Fecha : 27/03/2008
   --Proposito : Mostrar los convenios que tiene asociado determinado cliente
   -----------------------------------------------------------------------
   FUNCTION FN_LISTA_X_CLIENTE(A_COD_CLIENTE CMR.MAE_CLIENTE.COD_CLIENTE%TYPE)
      RETURN SYS_REFCURSOR IS
      C_CURSOR SYS_REFCURSOR;
   BEGIN
      OPEN C_CURSOR FOR
         SELECT A.COD_CONVENIO COD, A.DES_CONVENIO DES, 1 POS
           FROM CMR.MAE_CONVENIO A
          WHERE A.COD_CLIENTE = A_COD_CLIENTE
            AND A.FLG_ACTIVO = '1'
         UNION ALL
         SELECT '*' COD, '[ NINGUNO ]' DES, 0 POS
           FROM DUAL
          ORDER BY POS, DES;
      RETURN C_CURSOR;
   END FN_LISTA_X_CLIENTE;

   ---VARCHAR2(32000):='A36.8,A33';
   FUNCTION FN_LISTA_DIAGNOSTICO_X_CIE10(A_CADENA VARCHAR2) RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;

      A_CAD_CIE VARCHAR2(32000);
   BEGIN

      A_CAD_CIE := REPLACE(A_CADENA, ',', '''' || ',' || '''');

      OPEN C_CURSOR FOR 'SELECT * FROM CMR.MAE_DIAGNOSTICO WHERE COD_CIE_10 IN (' || '''' || A_CAD_CIE || '''' || ')';
      RETURN C_CURSOR;

   END;
--Autor : Juan Arturo Escate Espichan
--Fecha : 05/11/2008
--Proposito: Esto recalculo el copago variable segun las escalas
-------------------------------------------------------------------
    FUNCTION FN_CALCULA_COPAGO(
            A_COD_CONVENIO     CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
            A_IMP_TOTAL        FLOAT) RETURN FLOAT
        IS
            CURSOR C_RANGO IS
            select *
            from btlprod.rel_convenio_escala
            WHERE COD_CONVENIO=A_COD_CONVENIO;
            C_IMP_TOTAL FLOAT;
            V_REG_CONVENIO CMR.MAE_CONVENIO%ROWTYPE;
            V_INDICA       FLOAT:=-1;
    BEGIN
         SELECT *
         INTO V_REG_CONVENIO
         FROM CMR.MAE_CONVENIO
         WHERE COD_CONVENIO = A_COD_CONVENIO;
         IF V_REG_CONVENIO.FLG_POLITICA='1' and  NOT V_REG_CONVENIO.FLG_TIPO_CONVENIO='1' THEN
            RAISE_APPLICATION_ERROR(-20000,'Esto no se aplica para convenios que no sean co-pago variable');
         END IF;

         FOR REG IN C_RANGO LOOP
             IF A_IMP_TOTAL >= REG.IMP_MINIMO AND  A_IMP_TOTAL< REG.IMP_MAXIMO THEN
                IF REG.FLG_PORCENTAJE = '1' THEN
                   C_IMP_TOTAL := A_IMP_TOTAL*(REG.IMP_MONTO/100);
                ELSE
                   C_IMP_TOTAL := REG.IMP_MONTO;
                END IF;
                IF REG.FLG_COASEGURO=1 THEN
                  C_IMP_TOTAL:=REG.CTD_COASEGURO+(C_IMP_TOTAL-REG.CTD_COASEGURO)*REG.CTD_COASEGURO/100;
                END IF;
             END IF;
             V_INDICA := V_INDICA + 1;
         END LOOP;
         IF C_IMP_TOTAL IS NULL THEN
            C_IMP_TOTAL:= A_IMP_TOTAL;
         END IF;
         IF V_INDICA =-1 THEN
            RETURN V_INDICA;
         ELSE
           RETURN C_IMP_TOTAL;
         END IF;
    END;

  --Autor : Carlos CIeza
  --Fecha : 26/11/2008
  --Proposito: Devuelve las Escalas que existen segun el convenio
  -------------------------------------------------------------------
   FUNCTION FN_LISTA_ESCALAS(A_COD_CONVENIO     CMR.MAE_CONVENIO.COD_CONVENIO%TYPE)

     RETURN CURSOR_TYPE IS
     C_CURSOR CURSOR_TYPE;

   BEGIN
      OPEN C_CURSOR FOR
         SELECT *
           FROM BTLPROD.REL_CONVENIO_ESCALA A
          WHERE A.COD_CONVENIO = A_COD_CONVENIO
       ORDER BY A.COD_CONVENIO, A.IMP_MINIMO;

      RETURN C_CURSOR;
   END FN_LISTA_ESCALAS;

  --Autor : Cristhian Rueda
  --Fecha : 02/12/2008
  --Proposito: Devuelve si se imprime o no los importes para el convenio
  FUNCTION FN_FLG_IMPRIME_IMP(A_COD_CONVENIO  CMR.MAE_CONVENIO.COD_CONVENIO%TYPE) RETURN VARCHAR2
  AS
   C_FLG_IMPRIME_IMP  CHAR(1);
  BEGIN
     SELECT C.FLG_IMPRIME_IMPORTES INTO C_FLG_IMPRIME_IMP
        FROM CMR.MAE_CONVENIO C WHERE C.COD_CONVENIO = A_COD_CONVENIO ;

     RETURN C_FLG_IMPRIME_IMP;

     EXCEPTION
     WHEN NO_DATA_FOUND THEN
        RETURN '';

  END FN_FLG_IMPRIME_IMP;

    --Autor : Cristhian Rueda
  --Fecha : 02/12/2008
  --Proposito: Devuelve si se imprime o no los importes para el convenio
  FUNCTION FN_FLG_PRECIO_MENOR(A_COD_CONVENIO  CMR.MAE_CONVENIO.COD_CONVENIO%TYPE) RETURN VARCHAR2
  AS
   C_FLG_PRECIO_MENOR  CHAR(1);
  BEGIN
     SELECT C.FLG_PRECIO_MENOR INTO C_FLG_PRECIO_MENOR
        FROM CMR.MAE_CONVENIO C WHERE C.COD_CONVENIO = A_COD_CONVENIO ;

     RETURN C_FLG_PRECIO_MENOR;

     EXCEPTION
     WHEN NO_DATA_FOUND THEN
        RETURN '';

  END FN_FLG_PRECIO_MENOR;

  --Autor : Cristhian Rueda
  --Fecha : 26/12/2008
  --Proposito: Devuelve si se el precio deducible se obtiene del precio publico
  FUNCTION FN_FLG_PRECIO_DEDUCIBLE(A_COD_CONVENIO  CMR.MAE_CONVENIO.COD_CONVENIO%TYPE) RETURN VARCHAR2
  AS
   C_FLG_PRECIO_DEDUCIBLE  CHAR(1);
  BEGIN
     SELECT C.FLG_PRECIO_DEDUCIBLE INTO C_FLG_PRECIO_DEDUCIBLE
        FROM CMR.MAE_CONVENIO C WHERE C.COD_CONVENIO = A_COD_CONVENIO ;

     RETURN C_FLG_PRECIO_DEDUCIBLE;

     EXCEPTION
     WHEN NO_DATA_FOUND THEN
        RETURN '';

  END FN_FLG_PRECIO_DEDUCIBLE;

--Autor      : Juan Arturo Escate Espichan
--Fecha      : 09/02/2009
--Proposito  : Verifica si va a la tabla de RIMAC
  FUNCTION FN_FLG_RIMAC(A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE)
    RETURN INTEGER AS
    C_FLG_RIMAC INTEGER;
  BEGIN

    SELECT NVL(FLG_CONV_ASEGURADORA, 0)
      INTO C_FLG_RIMAC
      FROM CMR.MAE_CONVENIO
     WHERE COD_CONVENIO = A_COD_CONVENIO;

    RETURN C_FLG_RIMAC;

  END;

-----------------------------------------
  FUNCTION FN_FLG_DATA_RIMAC(A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE)
    RETURN INTEGER AS
    C_FLG_RIMAC INTEGER;
  BEGIN

    SELECT NVL(FLG_DATA_RIMAC, 0)
      INTO C_FLG_RIMAC
      FROM CMR.MAE_CONVENIO
     WHERE COD_CONVENIO = A_COD_CONVENIO;

    RETURN C_FLG_RIMAC;

  END;
/*
  Bitacora
    20/04/09 Pherrera - se agrego la busqueda del local por el codigo de usuario para no tenere que cambiar la app
      no funcionaba off-line
*/
  FUNCTION FN_TRANS_PAC_RIMAC(A_CIA          CMR.MAE_CLIENTE.CIA%TYPE,
                              A_COD_USUARIO  CMR.MAE_CLIENTE.COD_USUARIO%TYPE,
                              A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
                              A_DATO         VARCHAR2,
                              A_NOMBRE       VARCHAR2) RETURN CURSOR_TYPE IS
     C_CURSOR         CURSOR_TYPE;
     C_COD_CLIENTE    CMR.MAE_CLIENTE.COD_CLIENTE%TYPE := '';
     REG              NEWCNV.MAE_ASEGURADO_RIMAC%ROWTYPE;
     V_COD_LOCAL      NUEVO.MAE_LOCAL.COD_LOCAL%TYPE;
     V_LOCAL_OFF_LINE NUEVO.MAE_LOCAL.FLG_OFF_LINE%TYPE;
     --V_TABLA          VARCHAR2(1000);
     V_SQL            VARCHAR2(32000);
     V_ARROBA         VARCHAR2(10);
  BEGIN
     BEGIN
          SELECT COD_BTL
            INTO V_COD_LOCAL
            FROM NUEVO.MAE_USUARIO_BTL
           WHERE COD_USUARIO = A_COD_USUARIO;
     EXCEPTION
          WHEN NO_DATA_FOUND THEN
               RAISE_APPLICATION_ERROR(-20000, 'NO SE ENCONTRO EL USUARIO EN EL MAESTRO.');
     END;

     V_LOCAL_OFF_LINE := BTLPROD.PKG_LOCAL.FN_LISTA_LOCAL_OFF_LINE(A_CIA, V_COD_LOCAL);

     IF V_LOCAL_OFF_LINE = '0' THEN
        V_ARROBA := '';
     ELSE
        V_ARROBA := '@CENTRAL';
     END IF;

     V_SQL := 'SELECT POLIZA,
                      PLAN,
                      CODASG,
                      NUMITM,
                      FECNAC,
                      TIPO_SEGURO,
                      ESTADO,
                      FECHATRANS,
                      PRT,
                      FLG_VISUALIZACION,
                      TRIM(NOMBRES) NOMBRES
                 FROM NEWCNV.MAE_ASEGURADO_RIMAC' || V_ARROBA || '
                WHERE CODASG || ''-'' || POLIZA || ''-'' || PLAN || ''-'' || NUMITM = '''|| A_DATO ||'''
                  AND NOMBRES LIKE TRIM('''||A_NOMBRE||''') || ''%'' ';
     --DBMS_OUTPUT.put_line(V_SQL);
     EXECUTE IMMEDIATE V_SQL INTO REG;

     -- Cliente
     -- Buscar codigo de cliente
     BEGIN
          V_SQL := 'SELECT TRIM(A.COD_CLIENTE)
                      FROM CMR.MAE_CLIENTE'|| V_ARROBA || ' A
                     WHERE A.DES_CLIENTE = RPAD(BTLCADENA.FN_NOMBRE_CAMPO('''||REG.NOMBRES||''', 0) || '' '' ||
                                                BTLCADENA.FN_NOMBRE_CAMPO('''||REG.NOMBRES||''', 1) || '', '' ||
                                                TRIM(BTLCADENA.FN_NOMBRE_CAMPO('''||REG.NOMBRES||''', 2)), 50, '' '')
                       AND A.CIA = '''|| A_CIA || ''' ' ;
          --DBMS_OUTPUT.put_line(V_SQL);
          EXECUTE IMMEDIATE V_SQL INTO C_COD_CLIENTE;
     EXCEPTION
          WHEN NO_DATA_FOUND THEN
               C_COD_CLIENTE := NULL;
          WHEN OTHERS THEN
               C_COD_CLIENTE := NULL;
     END;
     ---------------------------------------------------------------
     -- Si el cliente no existe insertarlo en el maestro
     IF C_COD_CLIENTE IS NULL THEN
          BEGIN
               C_COD_CLIENTE := CMR.FN_DEV_PROX_SEC('CMR', 'MAE_CLIENTE', A_CIA, V_COD_LOCAL);

               V_SQL := 'INSERT INTO CMR.MAE_CLIENTE' || V_ARROBA || '
                              (COD_CLIENTE,
                               DES_NOM_CLIENTE,
                               DES_APE_CLIENTE,
                               FLG_TIPO_JURIDICA,
                               DES_OBSERVACION,
                               FCH_NACIMIENTO,
                               NUM_EMPLEADOS,
                               NUM_HIJOS,
                               FLG_ESTADO,
                               FLG_CLIENTE_VERIFICADO,
                               COD_USUARIO,
                               FCH_REGISTRA,
                               USUARIO,
                               FECHA,
                               FLG_VTA_CREDITO,
                               CIA,
                               DES_APE2_CLIENTE,
                               FLG_LISTA_PRC,
                               FLG_RESTRING_LISTA,
                               FLG_CERO,
                               COD_LOCAL_CREA,
                               FLG_ADD_OFF_LINE,
                               FLG_PROVEEDOR)
                         VALUES
                              ('''||C_COD_CLIENTE||''',
                               NVL(TRIM(BTLCADENA.FN_NOMBRE_CAMPO'||V_ARROBA||'(TRIM('''||REG.NOMBRES||'''), 2)), ''*''), --NOMBRE
                               TRIM(BTLCADENA.FN_NOMBRE_CAMPO'||V_ARROBA||'(TRIM('''||REG.NOMBRES||'''), 0)), --paterno
                               ''0'',
                               ''AUTOMATICO DE RIMAC '||A_COD_CONVENIO||''',
                               TO_DATE('''||REG.FECNAC||''', ''YYYYMMDD''),
                               0,
                               0,
                               ''1'',
                               ''1'',
                               '''||A_COD_USUARIO||''',
                               SYSDATE,
                               USER,
                               SYSDATE,
                               ''0'',
                               '''||A_CIA||''',
                               TRIM(BTLCADENA.FN_NOMBRE_CAMPO'||V_ARROBA||'(TRIM('''||REG.NOMBRES||'''), 1)), --materno
                               0,
                               0,
                               0,
                               '''||V_COD_LOCAL||''',
                               '''||V_LOCAL_OFF_LINE||''',
                               0)';
               DBMS_OUTPUT.put_line(V_SQL);
               EXECUTE IMMEDIATE V_SQL;
          EXCEPTION
               WHEN OTHERS THEN
                    RAISE_APPLICATION_ERROR(-20000, SQLERRM);
                    NULL;
          END;
     END IF;

     -- Beneficiario
     DECLARE
          I INTEGER := 0;
     BEGIN
          -- Buecar beneficiario
          V_SQL := 'SELECT COUNT(1)
                      FROM CMR.MAE_BENEFICIARIO' || V_ARROBA || '
                     WHERE COD_CONVENIO = ''' || A_COD_CONVENIO || '''
                       AND COD_CLIENTE  = ''' || C_COD_CLIENTE || ''' ';
          --DBMS_OUTPUT.put_line(V_SQL);
          EXECUTE IMMEDIATE V_SQL INTO I;

          IF I = 0 THEN
               V_SQL := 'INSERT INTO CMR.MAE_BENEFICIARIO' || V_ARROBA || '
                                   (CIA,
                                    COD_CONVENIO,
                                    COD_CLIENTE,
                                    IMP_LINEA_CREDITO,
                                    FLG_CAMB_TEMP_LIN_CRE,
                                    IMP_LINEA_CREDITO_ORI,
                                    FCH_INI_NVA_LINCRED,
                                    FCH_FIN_NVA_LINCRED,
                                    COD_USUARIO_AUTORIZA_CAMBIO,
                                    IMP_CONSUMO,
                                    DES_OBSERVACION,
                                    COD_REFERENCIA,
                                    FLG_ACTIVO,
                                    NUM_VTAS_PERIODO,
                                    COD_USUARIO,
                                    FCH_REGISTRA,
                                    COD_USUARIO_ACTUALIZA,
                                    FCH_ACTUALIZA,
                                    USUARIO,
                                    FECHA,
                                    NUM_CARNET,
                                    COD_AFILIADO,
                                    NUM_CONTRATO,
                                    NUM_POLIZA,
                                    NUM_PLAN,
                                    NUM_ITEM,
                                    COD_PARENTESCO,
                                    NUM_DOCUMENTO,
                                    DES_RAZON_SOCIAL,
                                    TIP_SERVICIO,
                                    FLG_DEDUCCIBLE,
                                    COD_ASEGURADO,
                                    COD_ZONAL,
                                    TIPO_SEGURO,
                                    PRT,
                                    CODCONT)
                              VALUES
                                   ('''||A_CIA||''',
                                    '''||A_COD_CONVENIO||''',
                                    '''||C_COD_CLIENTE||''',
                                    0,
                                    0,
                                    0,
                                    NULL,
                                    NULL,
                                    NULL,
                                    0,
                                    ''AUTOMATICO RIMAC '' || SYSDATE,
                                    '''||REG.CODASG||''',
                                    ''1'',
                                    NULL,
                                    '''||A_COD_USUARIO||''',
                                    SYSDATE,
                                    NULL,
                                    NULL,
                                    USER,
                                    SYSDATE,
                                    NULL,
                                    NULL,
                                    NULL,
                                    '''||REG.POLIZA||''',
                                    '''||REG.PLAN||''',
                                    '''||REG.NUMITM||''',
                                    '''||REG.PRT||''',
                                    NULL,
                                    NULL,
                                    NULL,
                                    0,
                                    '''||REG.CODASG||''',
                                    NULL,
                                    '''||REG.TIPO_SEGURO||''',
                                    '''||REG.PRT||''',
                                    NULL)';
               --DBMS_OUTPUT.put_line(V_SQL);
               EXECUTE IMMEDIATE V_SQL;
          END IF;
     END;

     COMMIT;

     V_SQL := 'SELECT A.COD_CLIENTE,
                      B.DES_CLIENTE,
                      B.DES_NOM_CLIENTE,
                      B.DES_APE_CLIENTE,
                      B.DES_APE2_CLIENTE,
                      B.COD_ESTADO_CIVIL,
                      A.FLG_ACTIVO,
                      DECODE(A.FLG_ACTIVO, ''1'', ''A'', ''I'') ESTADO,
                      C.DES_DOCUMENTO_IDENTIDAD,
                      B.NUM_DOCUMENTO_ID,
                      A.COD_REFERENCIA,
                      A.FCH_REGISTRA,
                      B.DES_CARGO,
                      NVL(A.IMP_LINEA_CREDITO_ORI, 0) IMP_LINEA_CREDITO,
                      NVL(A.IMP_LINEA_CREDITO, 0) IMP_LINEA_NVA,
                      A.FLG_CAMB_TEMP_LIN_CRE,
                      A.FCH_INI_NVA_LINCRED,
                      A.FCH_FIN_NVA_LINCRED,
                      A.DES_OBSERVACION,
                      0 IMP_CONSUMO,
                      0 CREDITO_REAL,
                      B.DES_DIRECCION_SOCIAL,
                      TO_CHAR(B.FCH_NACIMIENTO, ''DD/MM/YYYY'') FCH_NACIMIENTO,
                      B.DES_EMAIL,
                      A.COD_ZONAL
                 FROM CMR.MAE_BENEFICIARIO'|| V_ARROBA || ' A,
                      CMR.MAE_CLIENTE'|| V_ARROBA || '      B,
                      CMR.MAE_DOC_IDENT'|| V_ARROBA || '    C
                WHERE A.COD_CLIENTE = B.COD_CLIENTE
                  AND B.COD_DOCUMENTO_IDENTIDAD = C.COD_DOCUMENTO_IDENTIDAD(+)
                  AND A.COD_CONVENIO = ''' || A_COD_CONVENIO || '''
                  AND A.COD_CLIENTE = ''' || C_COD_CLIENTE || ''' ';

     OPEN C_CURSOR FOR V_SQL;

     RETURN C_CURSOR;

  END;

  FUNCTION FN_MENSAJE_TICKET(A_COD_CONVENIO VARCHAR2) RETURN VARCHAR2

   IS
    C_MENSAJE VARCHAR2(32000);
  BEGIN
    SELECT DES_IMPRESION
      INTO C_MENSAJE
      FROM CMR.MAE_CONVENIO
     WHERE COD_CONVENIO = A_COD_CONVENIO
       AND TRUNC(SYSDATE) BETWEEN NVL(FCH_IMPRESION_INI, TRUNC(SYSDATE)) AND
           NVL(FCH_IMPRESION_FIN, TRUNC(SYSDATE))
       AND NOT DES_IMPRESION IS NULL;
    RETURN C_MENSAJE;
  EXCEPTION
    WHEN OTHERS THEN
      RETURN NULL;
  END;

   FUNCTION FN_TIPO_CONVENIO( A_COD_CONVENIO     CMR.MAE_CONVENIO.COD_CONVENIO%TYPE )RETURN INTEGER
      IS
      C_FLG_TIPO_CONVENIO   INTEGER;
   BEGIN
       SELECT TO_NUMBER(FLG_TIPO_CONVENIO)
         INTO C_FLG_TIPO_CONVENIO
         FROM CMR.MAE_CONVENIO
        WHERE COD_CONVENIO = A_COD_CONVENIO;
       RETURN C_FLG_TIPO_CONVENIO;
   END;

PROCEDURE SP_GRABA_ESCALA_NIVEL(
          A_COD_CONVENIO        BTLPROD.REL_ESCALA_NIVEL.COD_CONVENIO%TYPE,
          A_CAD_NIVEL           VARCHAR2,
          A_CAD_imp_minimo      VARCHAR2,
          A_CAD_imp_maximo      VARCHAR2,
          A_CAD_flg_porcentaje  VARCHAR2,
          A_CAD_imp_monto       VARCHAR2,
          A_FLG_UNICO_NIVEL     INTEGER,
          A_NUM_NIVELES         INTEGER

)
AS
      V_ITEM                INTEGER;
      V_CAD_NIVEL           CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_imp_minimo      CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_imp_maximo      CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_flg_porcentaje  CMR.PKG_UTIL.TIPO_ARREGLO;
      V_CAD_imp_monto       CMR.PKG_UTIL.TIPO_ARREGLO;

BEGIN

  V_ITEM := 0;
      -----------------------------------------------------------------------------------------
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_NIVEL          , V_CAD_NIVEL            , V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_imp_minimo     , V_CAD_imp_minimo       , V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_imp_maximo     , V_CAD_imp_maximo       , V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_flg_porcentaje , V_CAD_flg_porcentaje   , V_ITEM);
      CMR.PKG_UTIL.SP_ARREGLO(A_CAD_imp_monto      , V_CAD_imp_monto        , V_ITEM);
      -----------------------------------------------------------------------------------------
      ---- Datos Adicionales ----
         BEGIN
            DELETE FROM BTLPROD.REL_ESCALA_NIVEL A
             WHERE A.COD_CONVENIO = A_COD_CONVENIO;
            IF SQL%NOTFOUND THEN
               NULL;
            END IF;
         END;
       --  FOR I IN 1 .. 5 LOOP
             FOR I IN 1 .. V_ITEM LOOP
                  BEGIN
                     INSERT INTO BTLPROD.REL_ESCALA_NIVEL
                        (cod_convenio,
                        nivel,
                        item,
                        imp_minimo,
                        imp_maximo,
                        flg_porcentaje,
                        imp_monto)
                     VALUES(
                     A_cod_convenio,
                     V_CAD_NIVEL(I),
                     I,
                     V_CAD_imp_minimo(I),
                     V_CAD_imp_maximo(I),
                     V_CAD_flg_porcentaje(I),
                     V_CAD_imp_monto(I)
                         );
                  EXCEPTION
                     WHEN DUP_VAL_ON_INDEX THEN
                        RAISE_APPLICATION_ERROR(-20000,
                                                'Insercion Duplicada. Total Reg Recibidos ' ||
                                                SQLERRM);
                     WHEN OTHERS THEN
                        RAISE_APPLICATION_ERROR(-20000,
                                                'Error de Insercion de Datos de la Escala X niveles' ||
                                                SQLERRM);
                  END;
             END LOOP;

             IF   A_FLG_UNICO_NIVEL=1 THEN
                  DELETE BTLPROD.REL_ESCALA_NIVEL
                  WHERE COD_CONVENIO = A_COD_CONVENIO
                  AND NOT NIVEL  = 1;

                      FOR I IN 1 .. A_NUM_NIVELES-1 LOOP
                          INSERT INTO BTLPROD.REL_ESCALA_NIVEL
                          SELECT cod_convenio,
                                 I+1 nivel,
                                 item,
                                 imp_minimo,
                                 imp_maximo,
                                 flg_porcentaje,
                                 imp_monto
                          FROM BTLPROD.REL_ESCALA_NIVEL
                          WHERE COD_CONVENIO = A_COD_CONVENIO
                          AND  NIVEL  = 1;
                      END LOOP;
             END IF;

END;

---------------------------------------------------------------------------------
--Autor : Juan Arturo Escate Espichan
--Fecha : 20/07/2009
--Motivo: Recargamos la funcion para validar posteriormente en la grabacion
   FUNCTION FN_LISTA_ESCALAXNIVEL(
                A_COD_CONVENIO        BTLPROD.REL_MODALIDAD_LOTE.COD_CONVENIO%TYPE
                )
      RETURN CMR.PKG_UTIL.CURSOR_TYPE AS
      C_CURSOR CMR.PKG_UTIL.CURSOR_TYPE;

   BEGIN

            OPEN C_CURSOR FOR
            SELECT * FROM btlprod.rel_escala_nivel
            WHERE COD_CONVENIO = A_COD_CONVENIO
            ORDER BY ITEM;

      RETURN C_CURSOR;
   END ;


  FUNCTION FN_DEV_CON_AFILIACION(A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE)
    RETURN INTEGER IS
    C_RETORNO INTEGER;
  BEGIN
    SELECT FLG_AFILIACION_ACT
      INTO C_RETORNO
      FROM CMR.MAE_CONVENIO
     WHERE COD_CONVENIO = A_COD_CONVENIO;

    RETURN NVL(C_RETORNO, 0);
  END;

  -------------------------------------------------------------------
   FUNCTION FN_LISTA_EJECUTIVO(A_FLG_NINGUNO   INTEGER DEFAULT 0)
     RETURN CURSOR_TYPE IS
     C_CURSOR CURSOR_TYPE;

   BEGIN


        IF A_FLG_NINGUNO = 0 THEN
            OPEN C_CURSOR FOR
                 SELECT COD_USUARIO,  DES_NOMBRE||', '||APE_PAT_USUARIO||' '||APE_MAT_USUARIO DES_USUARIO
                 FROM NUEVO.MAE_USUARIO_BTL
                 WHERE CNT_COSTO='925'
                 AND EST_USUARIO='ACT'
             ORDER BY DES_NOMBRE||', '||APE_PAT_USUARIO||' '||APE_MAT_USUARIO ;
       ELSE

           OPEN C_CURSOR FOR
           SELECT COD_USUARIO,  DES_USUARIO
           FROM (
           SELECT COD_USUARIO,  DES_NOMBRE||', '||APE_PAT_USUARIO||' '||APE_MAT_USUARIO DES_USUARIO, 1 ORDEN
           FROM NUEVO.MAE_USUARIO_BTL
           WHERE CNT_COSTO='925'
           AND EST_USUARIO='ACT'
       --ORDER BY DES_NOMBRE||', '||APE_PAT_USUARIO||' '||APE_MAT_USUARIO ;
             UNION ALL
               SELECT NULL COD_USUARIO, 'SIN EJECUTIVO' DES_USUARIO, 0 ORDEN
               FROM DUAL
               )
           ORDER BY ORDEN, DES_USUARIO;

        END IF;


      RETURN C_CURSOR;
   END ;

  -------------------------------------------------------------------
  -------------------------------------------------------------------
   FUNCTION FN_LISTA_TRAMA(A_FLG_NINGUNO   INTEGER DEFAULT 0)
     RETURN CURSOR_TYPE IS
     C_CURSOR CURSOR_TYPE;

   BEGIN
        IF A_FLG_NINGUNO = 0 THEN
           OPEN C_CURSOR FOR
           SELECT COD_TRAMA,  DES_TRAMA
           FROM BTLPROD.MAE_TRAMA
           WHERE FLG_ESTADO='1';
        ELSE

           OPEN C_CURSOR FOR
           SELECT COD_TRAMA,  DES_TRAMA
           FROM (
               SELECT COD_TRAMA,  DES_TRAMA, 1 ORDEN
               FROM BTLPROD.MAE_TRAMA
               WHERE FLG_ESTADO='1'
               UNION ALL
               SELECT NULL COD_TRAMA, 'NINGUNA TRAMA' DES_TRAMA, 0 ORDEN
               FROM DUAL
               )
           ORDER BY ORDEN;

        END IF;
      RETURN C_CURSOR;
   END ;
  -------------------------------------------------------------------
  -------------------------------------------------------------------
   FUNCTION FN_CNV_TRAMA
     RETURN CURSOR_TYPE IS
     C_CURSOR CURSOR_TYPE;

   BEGIN
           OPEN C_CURSOR FOR
           SELECT *
           FROM CMR.MAE_CONVENIO
           WHERE NOT COD_TRAMA IS NULL
           ORDER BY DES_CONVENIO;
      RETURN C_CURSOR;
   END ;

  -------------------------------------------------------------------
  -------------------------------------------------------------------
   FUNCTION FN_EVAL_RECETA(
                           A_COD_CONVENIO     CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
                           A_FCH_INICIO       VARCHAR2,
                           A_FCH_FIN          VARCHAR2,
                           A_FLG_OBSERVACION  VARCHAR2
                           )
     RETURN CURSOR_TYPE IS
       CURSOR C_RECETAS IS
       select
          A.*,
          (SELECT COD_SUCURSAL_PROV FROM NUEVO.MAE_LOCAL WHERE COD_LOCAL = cod_local_emision) LOCAL_CLINICA,
          TO_NUMBER(TO_CHAR(A.FCH_RECETA,'YY'))  ANIO_RECETA
      FROM BTLPROD.CAB_RECETA A
      where cod_local_emision IN (  SELECT COD_LOCAL FROM BTLPROD.REL_CONVENIO_LOCAL_RECETA
                                    WHERE COD_CONVENIO in(
                                      SELECT COD_CONVENIO
                                      FROM CMR.MAE_CONVENIO
                                      WHERE COD_CONVENIO = A_COD_CONVENIO )
                                )

    and fch_REGISTRO>= to_date(A_FCH_INICIO,'dd/mm/yyyy')
    and fch_REGISTRO< to_date(A_FCH_FIN,'dd/mm/yyyy')+1;
    C_RECETA_NEW   VARCHAR(100):=NULL;
    C_RECETA_FINAL VARCHAR(100):=NULL;
    --
    C_RECETA_MANUAL   VARCHAR(100):=NULL;
    C_ANIO_MANUAL     VARCHAR(100):=NULL;
    C_LOCAL_MANUAL    VARCHAR(100):=NULL;
    --
    C_OBSERVACION  VARCHAR(100):=NULL;
     C_CURSOR CURSOR_TYPE;

   BEGIN
           CONS_INTRANET.PKG_CREA_TABLA.SP_CREA_TABLA('CONS_INTRANET.TMP_RECETAS','COD_LOCAL VARCHAR2(100), COD_TIPO_DOCUMENTO VARCHAR2(100), NUM_DOCUMENTO VARCHAR2(100), NUM_RECETA VARCHAR2(100), OBSERVACION VARCHAR2(100), NUM_RECETA_NEW VARCHAR2(100)','BTLPROD');
        FOR REG IN C_RECETAS LOOP
--        raise_application_error(-20000,'porque sera');
            C_RECETA_NEW := TRIM(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REG.NUM_RECETA,' ','-'),'_','-'),'*','-'), '/','-'),'.','-')) ;
            C_RECETA_FINAL:=C_RECETA_NEW;
            --Aca evaluo las recetas manuales
            IF Instr(C_RECETA_NEW,'-', 1)<=0 THEN--Si no tiene un guion, pues asumo que son manuales
               IF REG.NUM_RECETA = C_RECETA_NEW THEN
                   C_OBSERVACION:=NULL;
                   C_RECETA_FINAL := C_RECETA_NEW;
                   --DBMS_OUTPUT.put_line('Receta Manual:'||C_RECETA_NEW||'=>'||C_OBSERVACION);
               ELSE
                  C_OBSERVACION:='Se modifico';
                  C_RECETA_FINAL := C_RECETA_NEW;
                  --DBMS_OUTPUT.put_line('Receta Manual:'||C_RECETA_NEW||'=>'||C_OBSERVACION);
              END IF;
            ELSE--aca llegan si tienen guiones y es donde me rayo con lo que escriben en el punto de venta
               C_RECETA_NEW := C_RECETA_NEW||'-'; --Este guion es para poder segmentarlo
               C_LOCAL_MANUAL    := CMR.PKG_UTIL.FN_SEGMENTO(C_RECETA_NEW,1,'-');
               C_ANIO_MANUAL     := CMR.PKG_UTIL.FN_SEGMENTO(C_RECETA_NEW,2,'-');
               C_RECETA_MANUAL   := CMR.PKG_UTIL.FN_SEGMENTO(C_RECETA_NEW,3,'-');

               IF C_LOCAL_MANUAL||'-'||C_ANIO_MANUAL||'-'||C_RECETA_MANUAL =  REG.NUM_RECETA THEN--CUANDO RA RECETA ESTA BIEN PERO VERIFICO QUE LOS DATOS SEAN CORRECTO
                  IF NOT C_LOCAL_MANUAL = REG.LOCAL_CLINICA THEN--reviso que la receta coincida con el local grabado
                       C_OBSERVACION  :='Pertenece a otra unidad medica: '||C_LOCAL_MANUAL||'=>'||REG.LOCAL_CLINICA;
                       C_RECETA_FINAL := REG.NUM_RECETA;
                       --DBMS_OUTPUT.put_line(REG.LOCAL_CLINICA||'-'||C_ANIO_MANUAL||'-'||C_RECETA_MANUAL ||'=>'||C_OBSERVACION);
                   ELSE
                          IF NOT TO_NUMBER(C_ANIO_MANUAL) = REG.ANIO_RECETA THEN--reviso que la receta coincida con el a?o de la receta
                               C_OBSERVACION  :='Es de otro a?o : '||C_ANIO_MANUAL||'=>'||REG.ANIO_RECETA;
                               C_RECETA_FINAL := REG.NUM_RECETA;
                               DBMS_OUTPUT.put_line(REG.LOCAL_CLINICA||'-'||C_ANIO_MANUAL||'-'||C_RECETA_MANUAL ||'=>'||C_OBSERVACION);
                          ELSE
                              IF Instr(C_RECETA_NEW,'-', 1,2)<=0 THEN
                               C_OBSERVACION  :='FUERA DE CUALQUIER FORMATO : ';
                               --DBMS_OUTPUT.put_line(C_OBSERVACION ||C_RECETA_FINAL);
                               ELSE
                                   NULL;
                               END IF;
                          END IF;

                  END IF;

               ELSE

                      IF C_LOCAL_MANUAL||'-'||C_ANIO_MANUAL = REG.NUM_RECETA THEN
                         C_OBSERVACION  :='No pusieron el local : '||REG.LOCAL_CLINICA;
                         C_RECETA_FINAL := REG.LOCAL_CLINICA||'-'||REG.NUM_RECETA;
                         --DBMS_OUTPUT.put_line(C_OBSERVACION||'=>'||C_RECETA_FINAL);
                       ELSE
                         C_OBSERVACION  :='No pusieron el local : '||REG.LOCAL_CLINICA;
                         --DBMS_OUTPUT.put_line('FUERA DE TODO'||C_RECETA_FINAL);
                      END IF;
               END IF;
            END IF;
            C_RECETA_FINAL:= TRIM(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(C_RECETA_FINAL,' ','-'),'_','-'),'*','-'), '/','-'),'.','-')) ;
            EXECUTE IMMEDIATE 'INSERT INTO CONS_INTRANET.TMP_RECETAS VALUES ('''||REG.COD_LOCAL||''','''|| REG.COD_TIPO_DOCUMENTO||''','''||REG.NUM_DOCUMENTO||''','''||REG.NUM_RECETA||''','''||C_OBSERVACION||''','''||C_RECETA_FINAL||''')';
--            COMMIT;
            C_RECETA_NEW    :=NULL;
            C_RECETA_FINAL  :=NULL;
            C_RECETA_MANUAL :=NULL;
            C_ANIO_MANUAL   :=NULL;
            C_LOCAL_MANUAL  :=NULL;
            C_OBSERVACION   :=NULL;

        END LOOP;

            IF A_FLG_OBSERVACION IS NULL THEN
               OPEN C_CURSOR FOR 'SELECT * FROM CONS_INTRANET.TMP_RECETAS ORDER BY OBSERVACION  ';
             ELSE
               OPEN C_CURSOR FOR 'SELECT * FROM CONS_INTRANET.TMP_RECETAS WHERE NOT OBSERVACION IS NULL ORDER BY OBSERVACION ';
           END IF;

      RETURN C_CURSOR;
   END ;

   PROCEDURE SP_ACTULIZA_RECETA(A_NUM_RECETA               BTLPROD.CAB_RECETA.NUM_RECETA%TYPE,
                                A_NUM_RECETA_NEW           BTLPROD.CAB_RECETA.NUM_RECETA%TYPE,
                                A_NUM_DOCUMENTO            BTLPROD.CAB_RECETA.NUM_DOCUMENTO%TYPE,
                                A_COD_TIPO_DOCUMENTO       BTLPROD.CAB_RECETA.COD_TIPO_DOCUMENTO%TYPE,
                                A_COD_LOCAL                BTLPROD.CAB_RECETA.COD_LOCAL%TYPE,
                                A_cod_usuario              BTLPROD.CAB_RECETA.COD_USUARIO_ACTUALIZA%TYPE
                                )
    AS
    BEGIN
         INSERT INTO BTLPROD.AUX_LOG_RECETA
         VALUES( A_cod_tipo_documento,
                 A_num_documento,
                 A_cod_usuario,
                 A_num_receta,
                 A_num_receta_new,
                 A_cod_local,
                 USER,
                 SYSDATE,
                 USERENV('TERMINAL'));

        UPDATE BTLPROD.CAB_RECETA
        SET COD_USUARIO_ACTUALIZA = A_COD_USUARIO,
            FCH_ACTUALIZA = SYSDATE,
            NUM_RECETA= A_NUM_RECETA_NEW
        WHERE COD_LOCAL = A_COD_LOCAL
        AND COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
        AND NUM_DOCUMENTO = A_NUM_DOCUMENTO
        AND NUM_RECETA  = A_NUM_RECETA;
        COMMIT;
    END;
--
   FUNCTION FN_LISTA_C_PLANVITAL(A_NROORD   VARCHAR2)
       RETURN CURSOR_TYPE IS
       C_CURSOR CURSOR_TYPE;

   BEGIN
           OPEN C_CURSOR FOR
               SELECT LPAD(' ', 1, ' ') || LPAD('01360', 5, ' ') ||
                      LPAD(' ', 10, ' ') ||
                      LPAD(TO_CHAR(FCH_EMISION, 'DDMMYYYY'), 8, ' ') ||
                      LPAD((SELECT G.RAZ_SOCIAL
                             FROM NUEVO.MAE_EMPRESA G
                            WHERE G.CIA = A.CIA),
                           30,
                           ' ') || LPAD(A.TOT_ITEM, 5, '0') ||
                      LPAD((SELECT X.TIPO_FACTURACION
                             FROM BTLPROD.REL_PROFORMA_DOCUMENTO Y,
                                  BTLPROD.CAB_PLAN_VITAL         X
                            WHERE Y.COD_TIPO_DOCUMENTO = 'GRL'
                              AND X.NUM_PROFORMA = Y.NUM_PROFORMA
                              AND Y.NUM_DOCUMENTO = A.NUM_GUIA
                              AND X.COD_ESTADO = 'ATE'),
                           4,
                           ' ') || SUBSTR(NUM_GUIA, 1, 3) || SUBSTR(NUM_GUIA, 4, 7) ||
                      LPAD(A.MTO_TOTAL, 11, ' ') || LPAD(0, 11, ' ')
                 FROM BTLCERO.CAB_GUIA_CLIENTE A, MEDCO.ORDFACD I
                WHERE A.NUM_GUIA = I.NUM_DOCUMENTO(+)
                  AND I.NROORD = A_NROORD;

      RETURN C_CURSOR;
   END ;

   FUNCTION FN_LISTA_D_PLANVITAL(A_NROORD   VARCHAR2)
     RETURN CURSOR_TYPE IS
     C_CURSOR CURSOR_TYPE;

   BEGIN
           OPEN C_CURSOR FOR
                SELECT
                       LPAD('01360',5,' ') ||
                       LPAD(J.NUM_ATENCION,10,' ')||
                       LPAD((SELECT TO_CHAR(TO_DATE(T.FCH_HORA,'YYYYMMDDHH24MI'),'DDMMYYYY') FROM BTLPROD.AUX_MOV_SEGUIM_PROF T WHERE T.NUM_PROFORMA = J.NUM_PROFORMA AND COD_ESTADO_PEDIDO = '008'),8,' ')||
                       LPAD((SELECT TO_CHAR(TO_DATE(T.FCH_HORA,'YYYYMMDDHH24MI'),'HH24MI') FROM BTLPROD.AUX_MOV_SEGUIM_PROF T WHERE T.NUM_PROFORMA = J.NUM_PROFORMA AND COD_ESTADO_PEDIDO = '008'),4,' ')||
                        LPAD(TRIM(REPLACE(REPLACE(RPAD(RTRIM(DECODE(NVL(RTRIM(BTLCADENA.FN_CODIGO_CUMS(A.COD_PRODUCTO, NULL, NULL)),
                                                                                  RPAD('X', 11, 'X')),
                                                                                  RPAD('X', 11, 'X'),
                                                                                  RPAD(RTRIM(A.COD_PRODUCTO), 11, '0'),
                                                                                  BTLCADENA.FN_CODIGO_CUMS(A.COD_PRODUCTO, NULL, NULL))),
                                                          11,
                                                          '0'),
                                                     'XXXXXXX',
                                                     '0000000'),
                                             'XXXX',
                                             '0000')),15, ' ')||
                        LPAD(A.COD_PRODUCTO,15,' ')||
                        LPAD(DECODE(A.FLG_FRACCIONO,'1', A.CTD_PRODUCTO_FRAC,A.CTD_PRODUCTO),5,' ')||
                        LPAD(99,2,' ')||
                        LPAD(TO_CHAR(A.MTO_BASE_IMP + A.MTO_EXONERADO,'999,999.00'),11 ,' ')||
                        LPAD(TO_CHAR(A.MTO_TOTAL/DECODE(A.FLG_FRACCIONO,'1',A.CTD_PRODUCTO_FRAC,A.CTD_PRODUCTO),'999,999.00'),11 ,' ')||
                        LPAD(TO_CHAR(0,'999,999.00'),11 ,' ')||
                        LPAD(Substr(NUM_GUIA, 1, 3),3,' ')||
                        LPAD(Substr(NUM_GUIA, 4, 7),8,' ')||
                        LPAD('',15,' ')||
                        LPAD(TO_CHAR(100-A.PCT_COPAGO,'999,999.00'),11 ,' ')
                FROM BTLCERO.DET_GUIA_CLIENTE A, MEDCO.ORDFACD I, BTLPROD.REL_PROFORMA_DOCUMENTO E , BTLPROD.CAB_PLAN_VITAL J
                WHERE E.CIA = I.CIA
                AND E.NUM_PROFORMA = J.NUM_PROFORMA
                AND E.NUM_DOCUMENTO = I.NUM_DOCUMENTO
                AND I.NUM_DOCUMENTO = A.NUM_GUIA
                AND A.NUM_GUIA = I.NUM_DOCUMENTO
                and i.cod_btl = e.cod_local
                AND I.NROORD= A_NROORD;

      RETURN C_CURSOR;
   END ;

   FUNCTION FN_VALIDA_CONVENIO_BTL(
       A_COD_CONVENIO       CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
       A_RUC_EMPRESA        VARCHAR2,
       A_COD_CLIENTE        CMR.MAE_CLIENTE.COD_CLIENTE%TYPE,
       A_COD_USUARIO        NUEVO.MAE_USUARIO_BTL.COD_USUARIO%TYPE
   ) RETURN VARCHAR2
   IS
       C_RUC_EMPRESA        VARCHAR2(11);
       C_NUM_DOCUMENTO_ID_1 VARCHAR2(10);
       C_NUM_DOCUMENTO_ID_2 VARCHAR2(10);
   BEGIN
     SELECT TMP_RUC
      INTO C_RUC_EMPRESA
      FROM CMR.MAE_CONVENIO
      WHERE COD_CONVENIO = A_COD_CONVENIO;
      IF C_RUC_EMPRESA = A_RUC_EMPRESA THEN
              SELECT NUM_DOCUMENTO_ID
                     INTO C_NUM_DOCUMENTO_ID_1
              FROM CMR.MAE_CLIENTE
              WHERE COD_CLIENTE= A_COD_CLIENTE;
              SELECT NUM_DOC_IDENTIDAD
              INTO C_NUM_DOCUMENTO_ID_2
              FROM NUEVO.MAE_USUARIO_BTL
              WHERE COD_USUARIO=A_COD_USUARIO;
              IF (C_NUM_DOCUMENTO_ID_1 = C_NUM_DOCUMENTO_ID_2) THEN
                 RAISE_APPLICATION_ERROR(-20000,CHR(13)||CHR(13)||'En los convenios BTL no se puede realizar la venta cuando el beneficiario es el mismo vendedor'||CHR(13)||CHR(13)     );
              END IF;
      END IF;
      RETURN 1;
   END;

   --LISTA LAS CONCEPTOS DE COMISION
   --MLAGUNA 23/03/2010
   FUNCTION FN_COMISION_VENTA(A_COD_CIA NUEVO.MAE_EMPRESA.COD_EMPRESA%TYPE)
      RETURN CURSOR_TYPE IS
      C_CURSOR CURSOR_TYPE;
   BEGIN

         OPEN C_CURSOR FOR
         SELECT COD_CONCEPTO, DES_CONCEPTO
         FROM CMR.MAE_CONCEPTO_COMISION WHERE CIA = A_COD_CIA;
   RETURN C_CURSOR;
   END FN_COMISION_VENTA;

   --LISTA LOS BENEFICIARIOS QUE RECIBIRAN EL PAGO DE COMISIONES POR VENTA
   --MLAGUNA
   --FECHA 05/04/2010
   FUNCTION FN_CARGA_PAGO_COMISION (A_ANIO BTLPROD.AUX_PAGO_COMISION_CNV.ANIO%TYPE,
                                    A_MES BTLPROD.AUX_PAGO_COMISION_CNV.MES%TYPE) RETURN CURSOR_TYPE
   IS
      C_CURSOR CURSOR_TYPE;
   BEGIN

      OPEN C_CURSOR FOR
      SELECT RUC_EMPRESA, BTLPROD.AUX_PAGO_COMISION_CNV.COD_CONVENIO, CMR.MAE_CONVENIO.DES_CONVENIO,
      BTLPROD.AUX_PAGO_COMISION_CNV.COD_CLIENTE,
      DNI, DES_NOMBRE,
      FCH_CALCULO,
      DECODE(FLG_PROCESADO,0,'Pendiente',1,'Procesado') as FLG_PROCESADO,
      BTLPROD.AUX_PAGO_COMISION_CNV.COD_USUARIO,
      CTD_DOCUMENTO,
      MTO_TOTAL,
      MTO_COMISION
      FROM BTLPROD.AUX_PAGO_COMISION_CNV, CMR.MAE_CONVENIO
      WHERE CMR.MAE_CONVENIO.COD_CONVENIO = BTLPROD.AUX_PAGO_COMISION_CNV.COD_CONVENIO
      AND BTLPROD.AUX_PAGO_COMISION_CNV.ANIO = A_ANIO
      AND BTLPROD.AUX_PAGO_COMISION_CNV.MES = A_MES;

   RETURN C_CURSOR;
   END FN_CARGA_PAGO_COMISION;

   --ARCHIVO PARA PAGO DE COMISIONES
   --MLAGUNA
   --FECHA 07/04/2010
   FUNCTION FN_ARCHIVO_COMISION    (A_ANIO BTLPROD.AUX_PAGO_COMISION_CNV.ANIO%TYPE,
                                    A_MES BTLPROD.AUX_PAGO_COMISION_CNV.MES%TYPE) RETURN CURSOR_TYPE
   IS
      C_CURSOR CURSOR_TYPE;
   BEGIN

      OPEN C_CURSOR FOR
          SELECT AUX_PAGO_COMISION_CNV.COD_PLANILLA AS AUX_CODAUX,
                 COD_CONCEPTO_PLANILLA AS COD_CONCOD,
                 'SO' AS TMO_CODTMO,
                 MTO_COMISION AS RGO_IMPTOT
            FROM BTLPROD.AUX_PAGO_COMISION_CNV,
                 CMR.MAE_CONVENIO,
                 CMR.MAE_CONCEPTO_COMISION
           WHERE CMR.MAE_CONVENIO.COD_CONVENIO =
                 BTLPROD.AUX_PAGO_COMISION_CNV.COD_CONVENIO
             AND CMR.MAE_CONCEPTO_COMISION.COD_CONCEPTO =
                 CMR.MAE_CONVENIO.COD_CONCEPTO
             AND BTLPROD.AUX_PAGO_COMISION_CNV.ANIO = A_ANIO
             AND BTLPROD.AUX_PAGO_COMISION_CNV.MES = A_MES
             AND AUX_PAGO_COMISION_CNV.COD_PLANILLA IS NOT NULL;

   RETURN C_CURSOR;
   END FN_ARCHIVO_COMISION;

   FUNCTION FN_TOTAL_PAGO_COMISION (A_ANIO BTLPROD.AUX_PAGO_COMISION_CNV.ANIO%TYPE,
                                    A_MES BTLPROD.AUX_PAGO_COMISION_CNV.MES%TYPE) RETURN CURSOR_TYPE
   IS
      C_CURSOR CURSOR_TYPE;
   BEGIN

      OPEN C_CURSOR FOR
          SELECT SUM(MTO_TOTAL) AS TOT_MTO_TOTAL,
                 SUM(MTO_COMISION) AS TOT_MTO_COMISION
            FROM BTLPROD.AUX_PAGO_COMISION_CNV
           WHERE BTLPROD.AUX_PAGO_COMISION_CNV.ANIO = A_ANIO
             AND BTLPROD.AUX_PAGO_COMISION_CNV.MES = A_MES;

   RETURN C_CURSOR;
   END FN_TOTAL_PAGO_COMISION;

  /*
    Bitacora:
      20/07/10 Pherrera - Se cambio para que tome los datos grabados en la cabecera de la guia de remision
  */
   FUNCTION FN_DEV_REFERENCIAS (A_CIA                BTLPROD.AUX_SEC_VENTA.CIA%TYPE,
                                A_COD_LOCAL          NUEVO.MAE_LOCAL.COD_LOCAL%TYPE,
                                A_COD_TIPO_DOCUMENTO BTLPROD.AUX_SEC_VENTA.COD_TIPO_DOCUMENTO%TYPE,
                                A_NUM_DOCUMENTO      BTLPROD.AUX_SEC_VENTA.NUM_DOCUMENTO%TYPE) RETURN CURSOR_TYPE
   IS
      V_LOCAL_OFF_LINE CHAR(1);
      C_CURSOR CURSOR_TYPE;
   BEGIN

        V_LOCAL_OFF_LINE := BTLPROD.PKG_LOCAL.FN_LISTA_LOCAL_OFF_LINE(A_CIA, A_COD_LOCAL);

        IF V_LOCAL_OFF_LINE = '0' THEN
          OPEN C_CURSOR FOR
                SELECT cod_tipodoc_ref COD_TIPO_DOCUMENTO, num_documento_ref NUM_DOCUMENTO
                FROM BTLCERO.CAB_GUIA_CLIENTE
                WHERE NUM_GUIA = A_NUM_DOCUMENTO
                  AND COD_ORIGEN = A_COD_LOCAL
                  AND CIA = A_CIA;
                /*SELECT COD_TIPO_DOCUMENTO, NUM_DOCUMENTO
                  FROM BTLPROD.AUX_SEC_VENTA
                 WHERE SEC_VENTA = (SELECT SEC_VENTA
                                      FROM BTLPROD.AUX_SEC_VENTA X
                                     WHERE NUM_DOCUMENTO = A_NUM_DOCUMENTO
                                       AND COD_TIPO_DOCUMENTO = A_COD_TIPO_DOCUMENTO
                                       AND CIA = A_CIA)
                   AND COD_TIPO_DOCUMENTO != A_COD_TIPO_DOCUMENTO
                   AND NUM_DOCUMENTO != A_NUM_DOCUMENTO;*/
        ELSE
          OPEN C_CURSOR FOR
                SELECT cod_tipodoc_ref COD_TIPO_DOCUMENTO, num_documento_ref NUM_DOCUMENTO
                FROM BTLCERO.CAB_GUIA_CLIENTE@CENTRAL
                WHERE NUM_GUIA = A_NUM_DOCUMENTO
                  AND COD_ORIGEN = A_COD_LOCAL
                  AND CIA = A_CIA;
        END IF;

        RETURN C_CURSOR;

   END FN_DEV_REFERENCIAS;

   FUNCTION FN_DEV_LINEA_BASE(A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE)RETURN FLOAT
   IS
   C_LINEA_BASE  FLOAT:=0;
   BEGIN
         SELECT MTO_LINEA_CRED_BASE
           INTO C_LINEA_BASE
           FROM CMR.MAE_CONVENIO
          WHERE COD_CONVENIO = A_COD_CONVENIO;
         RETURN NVL(C_LINEA_BASE, 0);
   END;

   FUNCTION FN_LISTA_ADD_BENEF RETURN SYS_REFCURSOR IS
      C_CURSOR SYS_REFCURSOR;
    BEGIN
      OPEN C_CURSOR FOR
        SELECT *
          FROM CMR.MAE_CONVENIO
         WHERE FLG_ACTIVO = '1'
           AND FLG_BENEFICIARIOS = '1'
           AND FLG_ING_BENEFICIARIO = '1'
         ORDER BY DES_CONVENIO;
      RETURN C_CURSOR;
   END;

   FUNCTION FN_LISTA_ENVIO_RUPTURAS (A_CIA              NUEVO.MAE_EMPRESA.COD_EMPRESA%TYPE,
                                     A_COD_CONVENIO     CMR.MAE_CONVENIO.COD_CONVENIO%TYPE
                                    ) RETURN SYS_REFCURSOR
    AS
        C_CURSOR SYS_REFCURSOR;

    BEGIN
        OPEN C_CURSOR FOR
           SELECT A.COD_PETITORIO, A.FLG_ENVIO, B.DES_PETITORIO, A.COD_FORMATO, A.DES_EMAIL
             FROM BTLPROD.REL_PETITORIO_CONVENIO A
            RIGHT JOIN BTLPROD.CAB_PETITORIO B
               ON B.COD_PETITORIO = A.COD_PETITORIO
            WHERE A.CIA = A_CIA
              AND A.COD_CONVENIO = A_COD_CONVENIO
              AND A.FLG_ACTIVO = '1'
              AND B.FLG_ACTIVO = '1'
            ORDER BY NUM_ORDEN;

        RETURN C_CURSOR;
    END;

    FUNCTION FN_LISTAS_FORMATOS_ENVIOS(A_COD_FORMATO CMR.MAE_FORMATO_ENVIO.COD_FORMATO%TYPE DEFAULT NULL,
                                       A_TEXTO       VARCHAR2 DEFAULT NULL
                                      ) RETURN SYS_REFCURSOR
     AS
       C_CURSOR SYS_REFCURSOR;
    BEGIN

       IF A_TEXTO IS NULL THEN
           OPEN C_CURSOR FOR
             SELECT COD_FORMATO, DES_FORMATO
               FROM CMR.MAE_FORMATO_ENVIO
              WHERE FLG_ESTADO = '1'
                AND COD_FORMATO = NVL(A_COD_FORMATO, COD_FORMATO);
       ELSE
           OPEN C_CURSOR FOR
             SELECT COD_FORMATO, DES_FORMATO
               FROM (SELECT 0 ORDEN, '000' COD_FORMATO, A_TEXTO DES_FORMATO FROM DUAL
                     UNION ALL
                     SELECT 1 ORDEN, COD_FORMATO, DES_FORMATO
                       FROM CMR.MAE_FORMATO_ENVIO
                      WHERE FLG_ESTADO = '1'
                        AND COD_FORMATO = NVL(A_COD_FORMATO, COD_FORMATO))
              ORDER BY ORDEN;
       END IF;
       RETURN C_CURSOR;
    END;
    FUNCTION FN_DEV_lISTA_DA(A_COD_TIPO_CAMPO CMR.MAE_TIPO_CAMPO.COD_TIPO_CAMPO%TYPE) RETURN INTEGER
    AS
    C_CURSOR INTEGER;
    BEGIN

    SELECT NVL(FLG_LISTA,0) INTO C_CURSOR  FROM CMR.MAE_TIPO_CAMPO WHERE COD_TIPO_CAMPO=A_COD_TIPO_CAMPO;
    RETURN C_CURSOR;
    END;

   FUNCTION FN_lISTA_DATOS_DA(A_COD_TIPO_CAMPO CMR.MAE_TIPO_CAMPO.COD_TIPO_CAMPO%TYPE) RETURN SYS_REFCURSOR
   AS
    C_CURSOR SYS_REFCURSOR;
    BEGIN
    OPEN C_CURSOR FOR
    SELECT * FROM CMR.aux_datos_tipo_campo WHERE COD_TIPO_CAMPO=A_COD_TIPO_CAMPO AND FLG_ACTIVO=1 ORDER BY NUM_ITEM;
    RETURN C_CURSOR;
    END;
    FUNCTION FN_LISTA_BARRA(A_COD_BARRA BTLPROD.REL_CONVENIO_BARRA.COD_BARRA%TYPE) RETURN SYS_REFCURSOR
      AS
      C_CURSOR SYS_REFCURSOR;
      BEGIN
        OPEN C_CURSOR FOR
        select A.* , B.DES_CONVENIO, C.DES_CLIENTE
        from btlprod.rel_convenio_barra A, CMR.MAE_CONVENIO B, CMR.MAE_CLIENTE C
        WHERE A.COD_CONVENIO = B.COD_CONVENIO
        AND A.COD_CLIENTE = C.COD_CLIENTE(+)
        AND A.COD_BARRA= A_COD_BARRA;

     RETURN C_CURSOR;
     END;

   FUNCTION FN_LISTA_CONVENIO_COMPETENCIA(A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE)RETURN INTEGER
   IS
   C_FLG_COMPETENCIA  INTEGER:=0;
   BEGIN
         SELECT FLG_COMPETENCIA
           INTO C_FLG_COMPETENCIA
           FROM CMR.MAE_CONVENIO
          WHERE COD_CONVENIO = A_COD_CONVENIO;
         RETURN NVL(C_FLG_COMPETENCIA, 0);
   END;


END PKG_CONVENIO;

/
