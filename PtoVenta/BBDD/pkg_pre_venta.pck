CREATE OR REPLACE PACKAGE PTOVENTA."PKG_PRE_VENTA" IS
    /**********************************************************************/
    -------------------------------------------------------------
    CONS_PREVTA_REGULAR         CHAR(3) := '001';
    CONS_PREVTA_CONVENIO        CHAR(3) := '001';
    CONS_PREVTA_MAYORISTA       CHAR(3) := '002';
    CONS_PREVTA_COBRORESPON     CHAR(3) := '001';
    CONS_PREVTA_CANJE           CHAR(3) := '001';
    CONS_PREVTA_SERVICIOS       CHAR(3) := '001';
    CONS_PREVTA_COBRANZAVTACRED CHAR(3) := '001';
    CONS_PREVTA_COTIZACION      CHAR(3) := '001';
    CONS_PREVTA_FACT_SERVICIOS  CHAR(3) := '001';
    CONS_PREVTA_GUIAS           CHAR(3) := '001';
    CONS_PREVTA_MAGISTRAL       CHAR(3) := '001';
    -------------------------------------------------------------
    /**********************************************************************/
    /**********************************************************************/
    -- Author  : VTIJERO
    -- Created : 10/11/2006 03:30:13 p.m.
    -- Purpose :
    /*
    001   POR IMPORTE
    002   POR CANTIDAD
    004   COMERCIALIZACION
    005   ALMACENAR
    006   POR DESCUENTO
    */
    V_TIP_IMPORTE   VARCHAR2(3) := '001';
    V_TIP_CANTIDAD  VARCHAR2(3):= '002';
    V_TIP_DESCUENTO VARCHAR2(3) := '006';

FUNCTION FN_EVALUA_PETITORIO
                      (A_COD_PETITORIO             CAB_PETITORIO.COD_PETITORIO%TYPE ,
                       A_COD_PRODUCTO              LGT_PROD.COD_PROD%TYPE   ,
                       A_CTD_FRACCIONAM            FLOAT
                       )
             RETURN VARCHAR ;
    FUNCTION FN_EVALUA_PRC_PETITORIO(A_CIA                     VARCHAR2,
                                     A_COD_LOCAL               PBL_LOCAL.COD_LOCAL%TYPE,
                                     A_COD_PETITORIO           CAB_PETITORIO.COD_PETITORIO%TYPE,
                                     A_COD_PRODUCTO            LGT_PROD.COD_PROD%TYPE,
                                     A_PRC_UNITARIO            FLOAT,
                                     A_CTD_PRODUCTO             FLOAT DEFAULT 0,
                                      A_CTD_PRODUCTO_FRAC       FLOAT DEFAULT 0,
                                     A_CTD_FRACCIONAMIENTO     FLOAT DEFAULT 0,
                                     A_TIP_PRECIO              CHAR DEFAULT NULL,
                                     A_TIP_VTA                 CHAR  DEFAULT NULL,
                                     A_COD_CONVENIO            MAE_CONVENIO.COD_CONVENIO%TYPE DEFAULT NULL) RETURN FLOAT;

END ;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PKG_PRE_VENTA" IS
FUNCTION FN_EVALUA_PETITORIO
                      (A_COD_PETITORIO             CAB_PETITORIO.COD_PETITORIO%TYPE ,
                       A_COD_PRODUCTO              LGT_PROD.COD_PROD%TYPE   ,
                       A_CTD_FRACCIONAM            FLOAT
                       )
             RETURN VARCHAR AS
    V_APTO                     CHAR(1):= '' ;
    V_CTD_PRODUCTO             FLOAT  := 0 ;
    V_CTD_PRODUCTO_FRAC        FLOAT  := 0 ;
    BEGIN
             V_APTO := PKG_PRODUCTO.FN_DEV_APTO_PETIT (A_COD_PETITORIO, A_COD_PRODUCTO  )  ;
             --REVISAR ESTE COMENTARIO
             IF V_APTO = 'I' THEN --INCLUIDO
                BEGIN

                   SELECT A.CTD_PRODUCTO, A.CTD_PRODUCTO_FRAC
                     INTO V_CTD_PRODUCTO, V_CTD_PRODUCTO_FRAC
                     FROM REL_PETITORIO_PRODUCTO A
                    WHERE A.COD_PETITORIO = A_COD_PETITORIO
                      AND A.COD_PRODUCTO = A_COD_PRODUCTO
                      AND A.FLG_ACTIVO = '1'
                      AND A.FLG_EXCLUIDO = '0';
                   EXCEPTION
                            WHEN NO_DATA_FOUND THEN
                                 --NO SE HA ESPECIFICADO X PRODUCTO, PERO AUN ASI ESTA APTO
                                 --ES DECIR X EJM X LA COMPRA DE CUALQUIER PROD DE ACC DE BB
                                 --YO ASUMO Q DEBIERAN ESPECIFICAR X PRODUCTO Y NO LO ASIGNO
                                 --LA VALIDACION LA HAGO EN LA Q LO LLAMA
                                 RETURN 'I'||'|1|0' ; --LE DEVOLVIA -1 VERIFICAR
                END ;
                --RETORNO LA CTD EN FRACCIONES DE LO Q LE PIDE Q COMPRE
                RETURN 'I|'||TO_CHAR( V_CTD_PRODUCTO )||'|'||TO_CHAR(V_CTD_PRODUCTO_FRAC) ;
             END IF ;
             IF V_APTO = 'E' THEN
                RETURN 'E' ; --EXCLUIDO DE LA LISTA
             END IF ;
             IF V_APTO = 'N' THEN
                RETURN 'N' ; --NO ESTA EN LA LISTA
             END IF ;
    END ;
    ----------------------------
        FUNCTION FN_EVALUA_PRC_PETITORIO
                      (A_CIA                     VARCHAR2,
                       A_COD_LOCAL               PBL_LOCAL.COD_LOCAL%TYPE ,
                       A_COD_PETITORIO           CAB_PETITORIO.COD_PETITORIO%TYPE    ,
                       A_COD_PRODUCTO            LGT_PROD.COD_PROD%TYPE      ,
                       A_PRC_UNITARIO            FLOAT ,
                       A_CTD_PRODUCTO             FLOAT DEFAULT 0,
                       A_CTD_PRODUCTO_FRAC       FLOAT DEFAULT 0,
                       A_CTD_FRACCIONAMIENTO     FLOAT DEFAULT 0,
                       A_TIP_PRECIO              CHAR DEFAULT NULL,
                       A_TIP_VTA                 CHAR  DEFAULT NULL,
                       A_COD_CONVENIO            MAE_CONVENIO.COD_CONVENIO%TYPE DEFAULT NULL
                      )
    RETURN FLOAT AS
    V_FLG_TIPO_PRECIO_LOCAL   CAB_PETITORIO.FLG_TIPO_PRECIO_LOCAL%TYPE ;
    V_FLG_MODO                CAB_PETITORIO.FLG_MODO%TYPE              ;
    V_FLG_TIPO_VALOR          CAB_PETITORIO.FLG_TIPO_VALOR%TYPE        ;
    V_IMP_VALOR               CAB_PETITORIO.IMP_VALOR%TYPE             ;
    V_FLG_TIPO_PRECIO         CAB_PETITORIO.FLG_TIPO_PRECIO%TYPE       ;
    V_COD_LOCAL               CAB_PETITORIO.COD_LOCAL%TYPE             ;
    V_EVALUA                  FLOAT := 0                                       ;
    V_COD_PETITORIO           CAB_PETITORIO.COD_PETITORIO%TYPE         ;
    V_COD_MONEDA              VARCHAR2(2)             ;
    V_FLG_PRECIO_FIJADO       CHAR(1)      ;
    V_TC                      FLOAT := 0                                       ;
    V_COD_MONEDA_CIA          VARCHAR2(2)                   ;
    V_FLG_APLICA_DSCTO_PFIJO    CAB_PETITORIO.FLG_APLICA_DSCTO_PFIJO%TYPE ;
    V_FLG_MODO_DET              REL_PETITORIO_PRODUCTO.FLG_MODO%TYPE ;
    V_FLG_TIPO_VALOR_DET        REL_PETITORIO_PRODUCTO.FLG_TIPO_VALOR%TYPE ;
    V_IMP_VALOR_DET             REL_PETITORIO_PRODUCTO.IMP_VALOR%TYPE ;
    ---
    V_CTD_PRODUCTO              REL_PETITORIO_PRODUCTO.CTD_PRODUCTO%TYPE;
    V_CTD_PRODUCTO_FRAC         REL_PETITORIO_PRODUCTO.CTD_PRODUCTO_FRAC%TYPE;
    V_FLG_POR_CADA              REL_PETITORIO_PRODUCTO.FLG_POR_CADA%TYPE;
    V_PRECIO_ANT                NUMBER(20,2);
    V_CTD_TOPE_UND              REL_PETITORIO_PRODUCTO.CTD_TOPE_UND%TYPE;
    V_CTD_TOPE_FRA              REL_PETITORIO_PRODUCTO.CTD_TOPE_FRA%TYPE;

    /*Angello 19/08/2010*/
    V_PRECIO_ANT_2              NUMBER(20,2);
    V_CUENTA_PETITORIO          INTEGER:=0;
    V_CONVENIO                  NUMBER(20,2);
    V_FLG_ENCARTE               INTEGER:=0;

    V_PRECIO_VENTA_REGULAR      NUMBER(20,2);


    BEGIN




             V_PRECIO_ANT_2:=PKG_PRODUCTO.FN_PRE_VTA_PRECIOLOCAL(A_CIA,
                                                                     A_COD_LOCAL,
                                                                     A_COD_PRODUCTO,
                                                                     NVL(A_TIP_PRECIO, '001')) ;


           /* V_PRECIO_VENTA_REGULAR:= fn_evalua_prc_petitorio(a_cia => A_CIA,
                                                           a_cod_local => A_COD_LOCAL,
                                                           a_cod_petitorio => A_COD_PETITORIO,
                                                           a_cod_producto => A_COD_PRODUCTO,
                                                           a_prc_unitario => A_PRC_UNITARIO,
                                                           a_ctd_producto => A_CTD_PRODUCTO,
                                                           a_ctd_producto_frac => A_CTD_PRODUCTO_FRAC,
                                                           a_ctd_fraccionamiento => A_CTD_PRODUCTO_FRAC,
                                                           a_tip_precio => '001',
                                                           a_tip_vta => NULL,
                                                           a_cod_convenio => NULL);*/

   --INSERT INTO cmr.tmp VALUES ('PRECIO ANT2-->'|| V_PRECIO_ANT_2||' PRECIO VENTA REGULAR-->'||V_PRECIO_VENTA_REGULAR);

             SELECT COUNT(*)
             INTO V_CUENTA_PETITORIO
             FROM Rel_Petitorio_Convenio AAAA
             INNER JOIN CAB_PETITORIO CCCC
             ON AAAA.COD_PETITORIO = CCCC.COD_PETITORIO
             WHERE AAAA.COD_CONVENIO = A_COD_CONVENIO
             AND AAAA.COD_PETITORIO = A_COD_PETITORIO
             AND AAAA.FLG_ACTIVO = '1';



           BEGIN
                SELECT FLG_MODO, FLG_TIPO_VALOR, IMP_VALOR, CTD_PRODUCTO, CTD_PRODUCTO_FRAC,
                       FLG_POR_CADA, NVL(CTD_TOPE_UND, 0), NVL(CTD_TOPE_FRA, 0)
                    INTO V_FLG_MODO_DET,V_FLG_TIPO_VALOR_DET, V_IMP_VALOR_DET, V_CTD_PRODUCTO, V_CTD_PRODUCTO_FRAC,
                         V_FLG_POR_CADA, V_CTD_TOPE_UND, V_CTD_TOPE_FRA
                    FROM REL_PETITORIO_PRODUCTO
                    WHERE COD_PETITORIO = A_COD_PETITORIO
                        AND COD_PRODUCTO = A_COD_PRODUCTO
                        AND FLG_ACTIVO = '1' ;
           EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    V_FLG_MODO_DET := 0 ;
                    V_FLG_TIPO_VALOR_DET := 0 ;
                    V_IMP_VALOR_DET := 0 ;

           END ;


           BEGIN

                SELECT B.FLG_TIPO_PRECIO_LOCAL   , B.FLG_MODO          , B.FLG_TIPO_VALOR  ,
                       B.IMP_VALOR               , B.FLG_TIPO_PRECIO   , B.COD_LOCAL       ,
                       B.FLG_APLICA_DSCTO_PFIJO
                  INTO V_FLG_TIPO_PRECIO_LOCAL   , V_FLG_MODO          , V_FLG_TIPO_VALOR  ,
                       V_IMP_VALOR               , V_FLG_TIPO_PRECIO   , V_COD_LOCAL       ,
                       V_FLG_APLICA_DSCTO_PFIJO
                  FROM CAB_PETITORIO B
                 WHERE B.COD_PETITORIO   = A_COD_PETITORIO         AND
                       B.FLG_ACTIVO      = '1' ;
                EXCEPTION
                         WHEN NO_DATA_FOUND THEN
                              V_COD_PETITORIO := NULL ;
           END ;
           V_EVALUA     := A_PRC_UNITARIO ;
--            INSERT INTO CMR.TMP VALUES ( 'ENTRANDO TIENE-->'||V_FLG_MODO||'->'||A_PRC_UNITARIO );
              --RAISE_APPLICATION_ERROR(-20000,V_FLG_TIPO_PRECIO);
           IF PKG_PRODUCTO.FN_SEGMENTO( PKG_PRODUCTO.FN_DEV_APTO_PETIT ( A_COD_PETITORIO, A_COD_PRODUCTO ),1) = 'I' THEN

              IF V_FLG_TIPO_PRECIO NOT IN ('3') THEN
                    BEGIN --TIPPRE 0:Costo                   1:PVS (Prc Vta Sugerido = Prc Kairos Publico)
                          --       2:PNB (Precio Neto Base)  3:PNP (Precio Neto Publico = Precio del Local)
                          --       4:Costo de Fact Stock Actual
                          SELECT DECODE( V_FLG_TIPO_PRECIO,
                                           '0', b.val_prec_prom,
                                           '1', A.PRC_KAIROS,
                                           '2', A.PRC_NETO_BASE    --, '3', V_EVALUA
                                       )  ,
                                 (CASE WHEN A.FCH_FIJADO_INI IS NOT NULL AND A.FCH_FIJADO_INI <= SYSDATE AND ( A.FCH_FIJADO_FIN >= SYSDATE OR A.FCH_FIJADO_FIN IS NULL )
                                            THEN
                                              A.FLG_PRECIO_FIJADO
                                       ELSE
                                            '0'
                                  END ) ,
                                   1 COD_MONEDA
                            INTO V_EVALUA , V_FLG_PRECIO_FIJADO, V_COD_MONEDA
                            FROM LGT_PROD B, AUX_MAE_PRODUCTO_COM A
                           WHERE B.COD_PROD =  A.COD_PROD
                           AND B.COD_PROD = A_COD_PRODUCTO ;
                          EXCEPTION
                                   WHEN NO_DATA_FOUND THEN
                                        V_EVALUA := -1 ;
                    END ;

                    -- JLOPEZ
                    -- 22/06/2007
                    -- SE CAMBIA EL VALOR DE LA COLUMNA A.PRC_KAIROS POR LA FUNCION cmr.pk_com_precios.fn_pre_c_pvp


                    IF V_FLG_TIPO_PRECIO = '1' THEN

                        V_EVALUA := pkg_producto.FN_PRE_C_PVP(A_COD_PRODUCTO,'ACT') ;
                        IF V_EVALUA < 0 THEN
                            RAISE_APPLICATION_ERROR(-20000,'Producto no tiene precio Kairos') ;
                        END IF ;
                    END IF ;


                    IF V_FLG_TIPO_PRECIO = '4' THEN --Costo de Ult Factura de la que se Consume Stock
/*                       SELECT CMR.PKG_UTIL.FN_SEGMENTO(CMR.FN_PRE_UFV(A_COD_PRODUCTO), 8)
                         INTO V_EVALUA
                         FROM DUAL ;
*/
                       /*SELECT CMR.PKG_UTIL.FN_SEGMENTO(CMR.FN_PRE_UO(A_COD_PRODUCTO), 4)
                        INTO V_EVALUA
                        FROM DUAL ; */
                       -----------------------------
                        -- 10/05/2007
                        -- JRAZURI -> MODIFICO PARA USAR EL PRECIO DE COMPRA LA ULTIMA FACTURA
                        ---V_EVALUA := CMR.PKG_UTIL.FN_SEGMENTO(CMR.FN_PRE_UFV(A_COD_PRODUCTO), 6);
                        -------------
                        -- 1/06/2007
                        --V_EVALUA := CMR.PKG_UTIL.FN_SEGMENTO(CMR.FN_PRE_UOCR(A_COD_PRODUCTO), 5);
                        -- 19/06/2007

                        V_EVALUA := pkg_producto.FN_PRE_ULT_COMPRA(A_COD_PRODUCTO) ;

                        IF V_EVALUA IS NULL THEN
                            RAISE_APPLICATION_ERROR(-20000,'Producto no tiene ultima Oferta ni ultima Compra, Comuniquese con precios ' ) ;
                        END IF ;


                    END IF ;

                       V_TC := 1 ;
                    V_EVALUA := ROUND(V_EVALUA * V_TC ,2) ;
              END IF ;
              --------------------------------------
              IF V_EVALUA >= 0 THEN
                  --APLICABLE SOLO SI V_FLG_TIPO_PRECIO = 3
                 -- INSERT INTO cmr.tmp VALUES ('ANTES DE V_FL_TIPO_PRECIO=3-->'||v_evalua);
                  IF V_FLG_TIPO_PRECIO = 3 THEN
                      IF V_FLG_TIPO_PRECIO_LOCAL = 2 THEN   --DE OTRO LOCAL
                         V_EVALUA := PKG_PRODUCTO.FN_PRE_VTA_PRECIOLOCAL
                                          ( A_COD_CIA      => A_CIA,
                                            A_COD_local      => V_COD_LOCAL,
                                            A_COD_PRODUCTO => A_COD_PRODUCTO,
                                            A_COD_PRECIO   => pkg_producto.V_PRECIO_PUBLICO
                                           ) ;
                      ELSE
                          NULL ; --NO HACE NADA XQ SE TIENE EL VALOR EN V_EVALUA
                      END IF ;
                  END IF     ;
                  --------------------------------------

                  --IF V_FLG_PRECIO_FIJADO= '0' OR (V_FLG_PRECIO_FIJADO= '1' AND V_FLG_APLICA_DSCTO_PFIJO = '1') THEN
                      --MODO     0: Descuento    1: Utilidad   2: Precio Fijo
                      --TIPVAL   0: Porcentaje   1: Importe

                    /*\*Angello 25/08/2010 evaluar encarte*\
                        SELECT COUNT(*) INTO V_FLG_ENCARTE
                        FROM CMR.DET_ENCARTE_BTL AAAA
                        INNER JOIN CMR.CAB_ENCARTE_BTL BBBB
                        ON AAAA.NUM_ENCARTE = BBBB.NUM_ENCARTE
                        WHERE AAAA.COD_PRODUCTO = A_COD_PRODUCTO
                        AND TRUNC(BBBB.FCH_FIN_VIG) >= TRUNC(SYSDATE);
                     \*Encarte****************************\

                     IF V_FLG_ENCARTE=1 THEN
                       SELECT PRC_NETO_BASE\*,PRC_NETO_BASE*\
                       INTO V_PRECIO_ANT_2\*,V_EVALUA*\
                       FROM CMR.AUX_PRODUCTO_PRC
                       WHERE COD_PRODUCTO = A_COD_PRODUCTO;

                     END IF;*/

                      IF V_FLG_MODO = 0 THEN  --DESCUENTO

                         V_PRECIO_ANT := V_EVALUA;

                         /*V_EVALUA := V_EVALUA - ( (CASE V_FLG_TIPO_VALOR
                                                       WHEN '0' THEN V_EVALUA * (V_IMP_VALOR/100)
                                                       WHEN '1' THEN V_IMP_VALOR END
                                                 )) ; */
                         /*23/06/08 pherrera jrazuri Parcheeee!!!!. para que funcione el 2x1 sin esto hace el descuento a partir de la cantidad
                         puesta en el petitorio, es decir si es por cada 2 productos 50% de dscto, cuando es 2 descuenta, 3 tambien
                         cuando no debe*/
                         /*
                           31/07/08 pherrera, para que funcione las promociones que son por la compra de cierta cantidad de unidades
                                  es decir, si compras 1 unidad hay descuento, si compras 2 unidades hay descuento, pero si
                                  compras 1 unidad con 2 fracciones debe tener descuento solo la unidad y las fracciones
                                  deben salir al precio normal

                         */

                         /*Angello 27/08/2010*********************************/

                         IF V_FLG_TIPO_PRECIO<>3 THEN
                              V_PRECIO_ANT_2:=V_PRECIO_ANT;
                         END IF;

                         IF V_CUENTA_PETITORIO='1' AND A_TIP_VTA='1' THEN
                           V_IMP_VALOR:=0;
                         END IF;

                         V_EVALUA := V_PRECIO_ANT_2 - ((CASE V_FLG_TIPO_VALOR
                                                        WHEN '0' THEN V_PRECIO_ANT_2 * (V_IMP_VALOR/100)
                                                        WHEN '1' THEN V_IMP_VALOR END
                                                       )) ;
                         --INSERT INTO cmr.tmp VALUES ('Precio ant2: ' || V_PRECIO_ANT_2 ||' Precio evalua '|| v_evalua);

                          IF V_PRECIO_ANT < V_EVALUA THEN
                            V_EVALUA:=V_PRECIO_ANT;
                          END IF;


--                           INSERT INTO CMR.TMP VALUES ('Evalua: ' || V_EVALUA );

                         /****************************************************/

                           IF V_FLG_POR_CADA = '1' THEN

                                /*
                                    07/10/2009 Crueda parche para las promociones con tope, aqui se evalua para evitar que la promocion salga mas del tope que se coloca a la promocion
                                    tanto en unidades y fracciones, se aplica segun lo configurado en el petitorio en el tipo promocion, modo descuento
                                */

                                IF V_CTD_PRODUCTO_FRAC >0 AND A_CTD_PRODUCTO_FRAC >0 THEN

                                      IF (V_CTD_TOPE_FRA = 0) OR  (V_CTD_TOPE_FRA  > 0 AND  V_CTD_TOPE_FRA >= A_CTD_PRODUCTO_FRAC ) THEN

                                          /* Cuando el tope de la fraccion se mayor a cero y este sea mayor a la cantidad de fraccion ingresada  */
                                          V_EVALUA:= (
                                                      ((TRUNC(A_CTD_PRODUCTO_FRAC / CASE V_CTD_PRODUCTO_FRAC WHEN 0 THEN 1 ELSE V_CTD_PRODUCTO_FRAC END ) * V_CTD_PRODUCTO_FRAC) * V_EVALUA) +
                                                      (MOD(A_CTD_PRODUCTO_FRAC, V_CTD_PRODUCTO_FRAC) * V_PRECIO_ANT)
                                                      )
                                                      / (CASE A_CTD_PRODUCTO_FRAC WHEN 0 THEN 1 ELSE A_CTD_PRODUCTO_FRAC END);
                                       ELSE
                                          /* Cuando el tope de la fraccion se igual a cero y este sea menor a la cantidad de fraccion ingresada  */
                                          /*V_EVALUA:= (
                                                      ((TRUNC(V_CTD_TOPE_FRA / CASE V_CTD_PRODUCTO_FRAC WHEN 0 THEN 1 ELSE V_CTD_PRODUCTO_FRAC END ) * V_CTD_PRODUCTO_FRAC) * V_EVALUA) +
                                                      (MOD(V_CTD_TOPE_FRA, V_CTD_PRODUCTO_FRAC) * V_PRECIO_ANT)
                                                      + (A_CTD_PRODUCTO_FRAC - V_CTD_TOPE_FRA)*V_PRECIO_ANT
                                                      )
                                                      / (CASE A_CTD_PRODUCTO_FRAC WHEN 0 THEN 1 ELSE A_CTD_PRODUCTO_FRAC END);*/

                                           IF A_TIP_VTA='2' THEN /*Evalua el convenio despues de llegado al tope*/
                                                   V_CONVENIO:= PKG_PRODUCTO.FN_PRECIO(A_CIA,A_COD_LOCAL,A_COD_PRODUCTO,'001',A_COD_CONVENIO);
                                                   V_EVALUA:= ((V_EVALUA * V_CTD_TOPE_FRA) + (V_CONVENIO * (A_CTD_PRODUCTO_FRAC - V_CTD_TOPE_FRA)))/A_CTD_PRODUCTO_FRAC;
                                           ELSE  --Evalua Venta Regular.
                                                   V_EVALUA:= ((V_PRECIO_ANT_2 * A_CTD_PRODUCTO_FRAC) -
                                                    (((CASE V_FLG_TIPO_VALOR
                                                       WHEN '0' THEN V_PRECIO_ANT_2 * (V_IMP_VALOR/100)
                                                       WHEN '1' THEN V_IMP_VALOR END )) * V_CTD_TOPE_FRA))/A_CTD_PRODUCTO_FRAC;
                                           END IF;


                                       END IF;

                                ELSIF V_CTD_PRODUCTO >0 AND A_CTD_PRODUCTO >0 THEN

                                          IF (V_CTD_TOPE_UND = 0) OR (V_CTD_TOPE_UND > 0 AND V_CTD_TOPE_UND >= A_CTD_PRODUCTO) THEN

                                               /* Cuando el tope de la unidad se mayor a cero y este sea mayor a la cantidad de unidad ingresada  */
                                                V_EVALUA:= (
                                                              ((TRUNC(A_CTD_PRODUCTO / V_CTD_PRODUCTO) * V_CTD_PRODUCTO) * V_EVALUA) +
                                                              (MOD(A_CTD_PRODUCTO, V_CTD_PRODUCTO) * V_PRECIO_ANT) +
                                                              (A_CTD_PRODUCTO_FRAC * (V_PRECIO_ANT/A_CTD_FRACCIONAMIENTO))
                                                              )
                                                              / (A_CTD_PRODUCTO + (A_CTD_PRODUCTO_FRAC /A_CTD_FRACCIONAMIENTO));



                                            ELSE
                                                /* Cuando el tope de la unidad se igual a cero y este sea menor a la cantidad de unidad ingresada  */



                                                --INSERT INTO CMR.TMP VALUES (A_COD_PRODUCTO||'-'||'PRECIO ANTERIOR-->'||V_PRECIO_ANT ||' PRECIO V_EVALUA-->'||V_EVALUA ||'-'||'PRECIO ANTERIOR 2-->'||V_PRECIO_ANT_2 || ' PRECIO CONVENIO ' || V_CONVENIO );

                                               /* V_EVALUA:= (
                                                        ((TRUNC(V_CTD_TOPE_UND / V_CTD_PRODUCTO) * V_CTD_PRODUCTO) * V_EVALUA) +
                                                        (MOD(V_CTD_TOPE_UND, V_CTD_PRODUCTO) * V_PRECIO_ANT) +
                                                        (A_CTD_PRODUCTO_FRAC * (V_PRECIO_ANT/A_CTD_FRACCIONAMIENTO))
                                                         + (A_CTD_PRODUCTO - V_CTD_TOPE_UND) * V_PRECIO_ANT
                                                        )
                                                        / (A_CTD_PRODUCTO + (A_CTD_PRODUCTO_FRAC /A_CTD_FRACCIONAMIENTO)); */


                                                IF A_TIP_VTA='2' THEN /*Evalua el convenio despues de llegado al tope*/
                                                   V_CONVENIO:= PKG_PRODUCTO.FN_PRECIO(A_CIA,A_COD_LOCAL,A_COD_PRODUCTO,'001',A_COD_CONVENIO);
                                                   V_EVALUA:= ((V_EVALUA * V_CTD_TOPE_UND) + (V_CONVENIO * (A_CTD_PRODUCTO - V_CTD_TOPE_UND)))/A_CTD_PRODUCTO;

                                                 ELSE  --Evalua Venta Regular.
                                                   V_EVALUA:= ((V_PRECIO_ANT_2 * A_CTD_PRODUCTO) -
                                                    (((CASE V_FLG_TIPO_VALOR
                                                       WHEN '0' THEN V_PRECIO_ANT_2 * (V_IMP_VALOR/100)
                                                       WHEN '1' THEN V_IMP_VALOR END )) * V_CTD_TOPE_UND))/A_CTD_PRODUCTO;

                                                       /*IF V_FLG_ENCARTE=1 THEN

                                                       V_EVALUA:=(((V_EVALUA  * A_CTD_PRODUCTO) - (V_PRECIO_ANT_2 * (A_CTD_PRODUCTO - V_CTD_TOPE_UND ))) + (V_PRECIO_ANT * (A_CTD_PRODUCTO - V_CTD_TOPE_UND )))/A_CTD_PRODUCTO;

                                                       END IF;*/
                                                 END IF;

                                                 --INSERT INTO CMR.TMP VALUES ( 'ENTRANDO TIENE-->'||V_EVALUA || ' PRC_CONVENIO ' || V_CONVENIO);

                                          END IF;


                                  ELSE
                                    V_EVALUA:= V_PRECIO_ANT;

                               END IF;

                         END IF;
                        ----------------------------
                      END IF ;
                      IF V_FLG_MODO = 1 THEN  --UTILIDAD

                         V_EVALUA := V_EVALUA + ((CASE V_FLG_TIPO_VALOR
  --                                                     WHEN '0' THEN V_EVALUA * (V_IMP_VALOR/100)
                                                       WHEN '0' THEN (V_EVALUA / ((100-V_IMP_VALOR)/100) ) - V_EVALUA
                                                       WHEN '1' THEN V_IMP_VALOR END
                                                 )) ;

                      END IF ;

                      IF V_FLG_MODO = 2 THEN  --PRECIO FIJO
                         V_EVALUA := V_IMP_VALOR     ;
                      END IF ;


                      IF V_FLG_MODO = 3 THEN    --PRECIO FIJO DETALLE

                        /*
                           -----------------------------------------------------------------------------------------
                           Es aqui donde se tiene que evaluar el tope de la cantidad ( unidades y/o fracciones )
                           que a su vez con el descuento sera para calcular el precio final
                           08/04/2009 Cristhian
                           -----------------------------------------------------------------------------------------
                        */

                        V_PRECIO_ANT := V_EVALUA;

                        /** Condicion para que evalue el modo descuento y tipo del "PRECIO FIJO DETALLE" **/
                        /** 24/06/2009 Cristhian Rueda **/

                        IF V_FLG_MODO_DET = 0 THEN
                               V_PRECIO_ANT := V_EVALUA;

                               V_EVALUA := V_EVALUA - ( (CASE V_FLG_TIPO_VALOR_DET
                                                             WHEN '0' THEN V_EVALUA * (V_IMP_VALOR_DET/100)
                                                             WHEN '1' THEN V_IMP_VALOR_DET END
                                                       )) ;


                         END IF;
                         --  Begin 06-DIC-13    TCT          Precio Fijo Detalle
                         IF V_FLG_MODO_DET = 2 THEN
                           V_EVALUA := V_IMP_VALOR_DET;
                         END IF;
                         --  End 06-DIC-13    TCT          Precio Fijo Detalle

                       -- ================================================================================================================================================================== --
                       -- =======================================  COMENTANDO LA FUNCIONALIDAD AQUI 07/10/2009 CRUEDA, LA LOGICA ESTA EN LA LINEA 1791 REVISARLO =========================== --
                       -- ================================================================================================================================================================== --

                           -- @@***** COMENTADO POR REVISAR LOS TOPES 05/10/2009 *******@@ --
                               /*IF A_CTD_PRODUCTO_FRAC >= V_CTD_TOPE_FRA THEN

                                    IF V_CTD_PRODUCTO_FRAC >= V_CTD_TOPE_FRA THEN

                                        V_EVALUA:= (
                                              ((TRUNC(A_CTD_PRODUCTO_FRAC / CASE V_CTD_TOPE_FRA WHEN 0 THEN 1 ELSE V_CTD_TOPE_FRA END ) * V_CTD_TOPE_FRA) * V_EVALUA) +
                                              (MOD(A_CTD_PRODUCTO_FRAC, V_CTD_TOPE_FRA) * V_PRECIO_ANT)
                                              )
                                              / (CASE A_CTD_PRODUCTO_FRAC WHEN 0 THEN 1 ELSE A_CTD_PRODUCTO_FRAC END);
                                      ELSE

                                        V_EVALUA:= (( A_CTD_PRODUCTO_FRAC / CASE A_CTD_FRACCIONAMIENTO WHEN 0 THEN 1 ELSE A_CTD_PRODUCTO_FRAC END ) * V_PRECIO_ANT) ;

                                    END IF;

                                ELSIF V_CTD_PRODUCTO_FRAC < V_CTD_TOPE_FRA THEN

                                        V_EVALUA:= (( A_CTD_PRODUCTO_FRAC / CASE A_CTD_FRACCIONAMIENTO WHEN 0 THEN 1 ELSE A_CTD_PRODUCTO_FRAC END ) * V_PRECIO_ANT) ;

                                END IF;  */
                           -- @@***** COMENTADO POR REVISAR LOS TOPES *******@@ --

                           -- @@***** COMENTADO POR REVISAR LOS TOPES 05/10/2009 *******@@ --
                                /*IF A_CTD_PRODUCTO > V_CTD_TOPE_UND THEN

                                      V_EVALUA:= (
                                                    (V_CTD_TOPE_UND * V_EVALUA) +
                                                    (MOD(A_CTD_PRODUCTO, V_CTD_TOPE_UND) * V_PRECIO_ANT) +
                                                    (A_CTD_PRODUCTO_FRAC * (V_PRECIO_ANT/A_CTD_FRACCIONAMIENTO))
                                                    )
                                                    / (A_CTD_PRODUCTO + (A_CTD_PRODUCTO_FRAC /A_CTD_FRACCIONAMIENTO));

                                  ELSE

                                     V_EVALUA:= (
                                              ((TRUNC(A_CTD_PRODUCTO / V_CTD_PRODUCTO) * V_CTD_PRODUCTO) * V_EVALUA) +
                                              (MOD(A_CTD_PRODUCTO, V_CTD_PRODUCTO) * V_PRECIO_ANT) +
                                              (A_CTD_PRODUCTO_FRAC * (V_PRECIO_ANT/A_CTD_FRACCIONAMIENTO))
                                              )
                                              / (A_CTD_PRODUCTO + (A_CTD_PRODUCTO_FRAC /A_CTD_FRACCIONAMIENTO));

                                END IF;                    */
                            -- @@***** COMENTADO POR REVISAR LOS TOPES 05/10/2009 *******@@ --

                          -- ================================================================================================================================================================== --
                          -- ================================================================================================================================================================== --

                      END IF;

                           /*
                           -----------------------------------------------------------------------------------------
                           Fin de la logica que hace que se evalue las cantidad teniendo un tope segun las unidades
                           y/o fracciones.
                           -----------------------------------------------------------------------------------------
                        */

                    END IF;


                    --Arutores:  jrazuri, jescate
                    -- fecha 17/06/2008
                    -- se supone que es cuando se pone margen no nos queda otra
                    -- Dios nos ampare

                     IF V_FLG_MODO = 4 THEN  --MARGEN
--                                    RAISE_APPLICATION_ERROR(-20003,V_IMP_VALOR||' ** '||V_FLG_TIPO_VALOR );
                         V_EVALUA := V_EVALUA + ((CASE V_FLG_TIPO_VALOR
                                                      WHEN '0' THEN V_EVALUA * (V_IMP_VALOR/100)
---                                                       WHEN '0' THEN (V_EVALUA / ((100-V_IMP_VALOR)/100) ) - V_EVALUA
                                                       WHEN '1' THEN V_IMP_VALOR END
                                                 )) ;

                      END IF ;

           ELSE
              V_EVALUA    := A_PRC_UNITARIO  ;
           END IF ;
           IF V_EVALUA < 0 THEN
              V_EVALUA := 0    ;
           END IF ;

  --         raise_application_error(-20000,V_EVALUA);


          RETURN V_EVALUA ;
    END;

    END;
/

