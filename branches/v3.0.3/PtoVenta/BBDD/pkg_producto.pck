CREATE OR REPLACE PACKAGE PTOVENTA."PKG_PRODUCTO" IS
 V_PRECIO_PUBLICO       VARCHAR2(3) := '001'    ;
 V_PRECIO_FARMACIA      VARCHAR2(3) := '002'    ;
 V_PRECIO_DLV           VARCHAR2(3) := '003'    ;

 FUNCTION FN_PRECIO_LOCAL(A_COD_PROD          PTOVENTA.LGT_PROD_LOCAL.COD_PROD%TYPE,
                    A_COD_LOCAL         PTOVENTA.LGT_PROD_LOCAL.COD_LOCAL%TYPE,
                    --A_COD_CIA           PTOVENTA.LGT_PROD_LOCAL.COD_CIA%TYPE,
                    A_COD_GRUPO_CIA     PTOVENTA.LGT_PROD_LOCAL.COD_GRUPO_CIA%TYPE
                    ) RETURN NUMBER;
FUNCTION FN_PRECIO
    (
        A_CIA          VARCHAR2,
        A_COD_LOCAL    LGT_PROD_LOCAL.COD_LOCAL%TYPE,
        A_COD_PRODUCTO LGT_PROD_LOCAL.COD_PROD%TYPE,
        A_COD_PRECIO   MAE_TIPO_PRECIO.COD_TIPO_PRECIO%TYPE,
        A_COD_CONVENIO MAE_CONVENIO.COD_CONVENIO%TYPE DEFAULT NULL
    ) RETURN FLOAT;
    FUNCTION FN_PRE_VTA_PRECIOLOCAL
    (
        A_COD_CIA      VARCHAR2,
        A_COD_LOCAL      VARCHAR2,
        A_COD_PRODUCTO VARCHAR2,
        A_COD_PRECIO   VARCHAR2,
        A_COD_CONVENIO VARCHAR2 DEFAULT NULL
    ) RETURN FLOAT ;
FUNCTION FN_DEV_APTO_PETIT
    (
        A_COD_PETITORIO CAB_PETITORIO.COD_PETITORIO%TYPE,
        A_COD_PRODUCTO  LGT_PROD.COD_PROD%TYPE
    ) RETURN CHAR;
    /**********************************************************************/
    /**********************************************************************/
    /**********************************************************************/
    FUNCTION FN_DEV_APTO_PETIT
    (
        A_COD_PETITORIO CAB_PETITORIO.COD_PETITORIO%TYPE,
        A_COD_PRODUCTO  LGT_PROD.COD_PROD%TYPE,
        A_FLG_EXCLUYE   VARCHAR2
    ) RETURN FLOAT;
FUNCTION FN_SEGMENTO(A_CADENA    VARCHAR2,
                                       A_ITEM      INT,
                                       A_SEPARADOR CHAR DEFAULT '|')
    RETURN VARCHAR2;
    	FUNCTION FN_PRE_C_PVP ( A_COD_PRODUCTO VARCHAR2, A_COD_TIPO VARCHAR2 )
	  RETURN FLOAT;
    FUNCTION FN_PRE_ULT_COMPRA(A_COD_PRODUCTO lgt_prod.COD_PROd%TYPE)
    RETURN FLOAT ;
    FUNCTION FN_BTL_DEVUELVE_PRECIO_MAY(
  A_COD_LOCAL         PTOVENTA.LGT_PROD_LOCAL.COD_LOCAL%TYPE,
  A_COD_PROD          PTOVENTA.LGT_PROD_LOCAL.COD_PROD%TYPE,
  A_COD_GRUPO_CIA     PTOVENTA.LGT_PROD_LOCAL.COD_GRUPO_CIA%TYPE )
  RETURN NUMBER;
    FUNCTION FN_DEV_APTO_CONV(
         A_COD_PRODUCTO  LGT_PROD.COD_PROD%TYPE,
         A_COD_CONVENIO  MAE_CONVENIO.COD_CONVENIO%TYPE
      ) RETURN VARCHAR2;
end ;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PKG_PRODUCTO" is
 FUNCTION FN_PRECIO_LOCAL(A_COD_PROD          PTOVENTA.LGT_PROD_LOCAL.COD_PROD%TYPE,
                    A_COD_LOCAL         PTOVENTA.LGT_PROD_LOCAL.COD_LOCAL%TYPE,
                    --A_COD_CIA           PTOVENTA.LGT_PROD_LOCAL.COD_CIA%TYPE,
                    A_COD_GRUPO_CIA     PTOVENTA.LGT_PROD_LOCAL.COD_GRUPO_CIA%TYPE
                    ) RETURN NUMBER
  AS
  C_PRC_VENTA NUMBER;
  BEGIN
       SELECT   round(val_prec_vta *val_frac_local,3)
       INTO C_PRC_VENTA
       FROM PTOVENTA.LGT_PROD_LOCAL
       WHERE COD_PROD = A_COD_PROD
       AND COD_LOCAL = A_COD_LOCAL
       AND COD_GRUPO_CIA = A_COD_GRUPO_CIA;
                    return C_PRC_VENTA;
  END;

FUNCTION FN_BTL_DEVUELVE_PRECIO_MAY(
  A_COD_LOCAL         PTOVENTA.LGT_PROD_LOCAL.COD_LOCAL%TYPE,
  A_COD_PROD          PTOVENTA.LGT_PROD_LOCAL.COD_PROD%TYPE,
  A_COD_GRUPO_CIA     PTOVENTA.LGT_PROD_LOCAL.COD_GRUPO_CIA%TYPE )
  RETURN NUMBER
  AS
  BEGIN
    RETURN FN_PRECIO_LOCAL (A_COD_PROD, A_COD_LOCAL, A_COD_GRUPO_CIA );
  END;

FUNCTION FN_BTL_DEVUELVE_PRECIO_DLV(
  A_COD_LOCAL         PTOVENTA.LGT_PROD_LOCAL.COD_LOCAL%TYPE,
  A_COD_PROD          PTOVENTA.LGT_PROD_LOCAL.COD_PROD%TYPE,
  A_COD_GRUPO_CIA     PTOVENTA.LGT_PROD_LOCAL.COD_GRUPO_CIA%TYPE)
  RETURN NUMBER
  AS
  BEGIN
    RETURN FN_PRECIO_LOCAL (A_COD_PROD, A_COD_LOCAL, A_COD_GRUPO_CIA );
  END;

FUNCTION FN_PRE_VTA_PRECIOLOCAL
    (
        A_COD_CIA      VARCHAR2,
        A_COD_LOCAL      VARCHAR2,
        A_COD_PRODUCTO VARCHAR2,
        A_COD_PRECIO   VARCHAR2,
        A_COD_CONVENIO VARCHAR2 DEFAULT NULL
    ) RETURN FLOAT AS
        V_PRECIO     FLOAT := 0;
        V_TC         FLOAT := 0;
        V_COD_MONEDA VARCHAR2(2);
        CONS_APP_PRECIOS_ONLINE        CHAR(1)                                         := '0'     ;
        A_COD_GRUPO_CIA VARCHAR(3):='001';
    BEGIN

        IF A_COD_PRECIO IS NULL THEN
            RETURN - 10000;
        ELSE

            IF CONS_APP_PRECIOS_ONLINE = '1' THEN
--                dbms_output.put_line('1');
                IF A_COD_CONVENIO IS NULL THEN
--                    dbms_output.put_line('1.1');


                    V_PRECIO := FN_PRECIO_LOCAL(A_COD_LOCAL,
                                          A_COD_PRODUCTO,
                                          A_COD_PRECIO);
                ----------------------------------------
                --RAISE_APPLICATION_ERROR(-20003,BTLPROD.PKG_CONSTANTES.CONS_APP_PRECIOS_ONLINE);
                --- AQUI TAMBIEN ENTRA SI SOLO SI NO ES CONVENIO

                     --RAISE_APPLICATION_ERROR(-20003,A_COD_CIA||'***'||V_COD_MONEDA);
                        V_PRECIO := ROUND(V_PRECIO * 1, 2);
                END IF;
                ----------------------------------------
                IF A_COD_CONVENIO IS NOT NULL THEN
                    V_PRECIO := PKG_PRODUCTO.FN_PRECIO(A_COD_CIA,
                                                       A_COD_LOCAL,
                                                       A_COD_PRODUCTO,
                                                       V_PRECIO_PUBLICO,
                                                       A_COD_CONVENIO);
                END IF;
            ELSE
--                dbms_output.put_line('2');
                --RAISE_APPLICATION_ERROR(-20003,BTLPROD.PKG_CONSTANTES.CONS_APP_PRECIOS_ONLINE||'**'||A_COD_PRECIO||PK_COM_PRECIOS.V_PRECIO_PUBLICO);
                IF A_COD_CONVENIO IS NULL THEN
--                    dbms_output.put_line('2.1');

                    IF A_COD_PRECIO = V_PRECIO_PUBLICO THEN
                        --RAISE_APPLICATION_ERROR(-20003,A_COD_BTL||A_COD_PRODUCTO||'**'||V_COD_MONEDA) ;
--                        dbms_output.put_line('2.2');
                        V_PRECIO := FN_PRECIO_LOCAL(A_COD_PROD=>A_COD_PRODUCTO,
                        A_COD_LOCAL => A_COD_LOCAL,
                        A_COD_GRUPO_CIA=> A_COD_GRUPO_CIA
                                                                           );

                    END IF;
                    IF A_COD_PRECIO = V_PRECIO_FARMACIA THEN
--                        dbms_output.put_line('2.3');
                        V_PRECIO := FN_BTL_DEVUELVE_PRECIO_MAY(A_COD_LOCAL, A_COD_PRODUCTO, A_COD_GRUPO_CIA);
                    END IF;

                    IF A_COD_PRECIO = V_PRECIO_DLV THEN
                       V_PRECIO := FN_BTL_DEVUELVE_PRECIO_MAY(A_COD_LOCAL,
                                                                         A_COD_PRODUCTO, A_COD_GRUPO_CIA);


                              V_COD_MONEDA:='1';
                            -- RAISE_APPLICATION_ERROR(-20003,A_COD_BTL||A_COD_PRODUCTO||'***'||V_COD_MONEDA);

                            V_PRECIO := ROUND(V_PRECIO * 1, 2);
                    END IF;

                END IF;
                IF A_COD_CONVENIO IS NOT NULL THEN
                    V_PRECIO := PKG_PRODUCTO.FN_PRECIO(A_COD_CIA,
                                                       A_COD_LOCAL,
                                                       A_COD_PRODUCTO,
                                                       V_PRECIO_PUBLICO,
                                                       A_COD_CONVENIO);
                END IF;

            END IF;

            --dbms_output.put_line('Valores =>  '||V_PRECIO||' '||V_TC);

        END IF;
        IF V_PRECIO < 0 THEN
            V_PRECIO := 0;
        END IF;
        RETURN ROUND(V_PRECIO, 2);
    END;
FUNCTION FN_PRECIO
    (
        A_CIA          VARCHAR2,
        A_COD_LOCAL    LGT_PROD_LOCAL.COD_LOCAL%TYPE,
        A_COD_PRODUCTO LGT_PROD_LOCAL.COD_PROD%TYPE,
        A_COD_PRECIO   MAE_TIPO_PRECIO.COD_TIPO_PRECIO%TYPE,
        A_COD_CONVENIO MAE_CONVENIO.COD_CONVENIO%TYPE DEFAULT NULL
    ) RETURN FLOAT IS
        V_PRECIO        FLOAT := 0;
        V_EVALUA        FLOAT := 0;
        V_FCH_INICIO    DATE;
        V_FCH_FIN       DATE;
        V_BOL_APTO      BOOLEAN := TRUE;
        V_PET_ANT       BOOLEAN := FALSE;
        V_COD_PETITORIO MAE_CONVENIO.COD_PETITORIO%TYPE;
        V_FLG_PETITORIO MAE_CONVENIO.FLG_PETITORIO%TYPE;
        V_IMP_VALOR     MAE_CONVENIO.IMP_VALOR%TYPE;
        V_EVALUA_APTO   CHAR(1);
    BEGIN

    --raise_application_error(-20000,'Codigo'||' '||A_COD_PRECIO);

        IF A_COD_PRECIO IS NULL THEN
            RETURN '-1';
        END IF;

        -- JRAZURI
        -- 22/01/2008
        -- ENTRABA AQUI A PESAR DE QUE ERA CONVENIO PROVOCANDO LA DOBLE EVALUACION

       -- IF A_COD_CONVENIO IS NULL THEN
        -----------------------------------------------------------------------------


            V_PRECIO := FN_PRE_VTA_PRECIOLOCAL(A_COD_CIA      => A_CIA,
                                                A_COD_LOCAL      => A_COD_LOCAL,
                                                A_COD_PRODUCTO => A_COD_PRODUCTO,
                                                A_COD_PRECIO   => A_COD_PRECIO);
--        END IF;
        -----------------------------------------------------------------------------
        -----------------------------------------------------------------------------
       -- raise_application_error(-20000,'entro aqui xx'||' '||V_PRECIO);


        IF A_COD_CONVENIO IS NOT NULL THEN
            --TIPLOCAL 1: Referencia al mismo Local de compra.
            --         2: Referencia a un Local especifico.
            --CODLOCAL Local Referenciado
            BEGIN
                SELECT DECODE(A.FLG_PETITORIO, '1', NULL, A.COD_PETITORIO),
                       TRUNC(A.FCH_INICIO),
                       TRUNC(A.FCH_FIN),
                       A.FLG_PETITORIO,
                       IMP_VALOR
                --YO HAGO CASO A ESTOS DOS ULT VALORES SOLO SI FLG_PETITORIO = '0'
                --ASUMO QUE ES A TODO EL CATALOGO
                  INTO V_COD_PETITORIO,
                       V_FCH_INICIO,
                       V_FCH_FIN,
                       V_FLG_PETITORIO,
                       V_IMP_VALOR
                  FROM MAE_CONVENIO A
                 WHERE --A.CIA = A_CIA AND
                   A.COD_CONVENIO = A_COD_CONVENIO
                   AND A.FLG_ACTIVO = '1';
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    V_COD_PETITORIO := NULL;
            END;
            --V_PRECIO := 0 ; NO ACTIVAR
            IF V_FCH_INICIO IS NOT NULL AND V_FCH_INICIO <= TRUNC(SYSDATE) AND
               (V_FCH_FIN IS NULL OR V_FCH_FIN >= TRUNC(SYSDATE)) THEN
                --SI ESTA VIGENTE

                IF V_FLG_PETITORIO = '0' THEN
                    --EVALUA EL PETITORIO --NO IGNORA EL PETITORIO
                    FOR XXX IN (SELECT A.COD_PETITORIO
                                  FROM REL_PETITORIO_CONVENIO A
                                 WHERE --A.CIA = A_CIA AND
                                   A.COD_CONVENIO = A_COD_CONVENIO
                                   AND A.FLG_ACTIVO = '1'
                                 ORDER BY NUM_ORDEN)
                    LOOP
                        ------SI ES QUE MANEJA PETITORIO

                        --RAISE_APPLICATION_ERROR(-20005,A_COD_CONVENIO || ' - ' || XXX.COD_PETITORIO || ' - ' || A_COD_PRECIO) ;

                        V_EVALUA_APTO := FN_DEV_APTO_PETIT(XXX.COD_PETITORIO,
                                                                            A_COD_PRODUCTO);
--                        raise_application_error(-20000,'entro aqui xx'||' '||V_EVALUA_APTO);
                        IF V_BOL_APTO = FALSE THEN
                            V_PRECIO := 0; --PARA Q NO SE VENDA
                        ELSE


                            IF V_EVALUA_APTO = 'E' THEN
                                V_BOL_APTO := FALSE;
                                V_PRECIO   := 0; --PARA Q NO SE VENDA
                                V_PET_ANT  := TRUE;
                            ELSE
                                IF V_EVALUA_APTO = 'I' AND
                                   V_PET_ANT = FALSE THEN

                                   --RAISE_APPLICATION_ERROR(-20000,A_COD_PRECIO);
                                   --INSERT INTO CMR.TMP VALUES ('PRECIO EN PKG_PRODUCTO-->'||V_PRECIO);

                                    V_PRECIO   := PKG_PRE_VENTA.FN_EVALUA_PRC_PETITORIO(A_CIA,
                                                                                                A_COD_LOCAL,
                                                                                                XXX.COD_PETITORIO,
                                                                                                A_COD_PRODUCTO,
                                                                                                V_PRECIO,
                                                                                                0,0,0,A_COD_PRECIO,'2',A_COD_CONVENIO);
                                    V_BOL_APTO := TRUE; --SI ESTA APTO EN UN PETITORIO
                                    V_PET_ANT  := TRUE; --SI ESTA INCLUIDO EN UN PETITORIO
                                END IF;
                                IF V_EVALUA_APTO = 'N' AND
                                   V_PET_ANT = FALSE THEN
                                    V_PRECIO := PKG_PRODUCTO.FN_PRE_VTA_PRECIOLOCAL(A_COD_CIA      => A_CIA,
                                                                                        A_COD_LOCAL      => A_COD_LOCAL,
                                                                                        A_COD_PRODUCTO => A_COD_PRODUCTO,
                                                                                        A_COD_PRECIO   => V_PRECIO_PUBLICO);
                                END IF;
                            END IF;
                        END IF;
                    END LOOP;
                ELSE
                    --OSEA NO USA PETITORIO POR LO TANTO ES DE TODO EL CATALODO
                    V_PRECIO := PKG_PRODUCTO.FN_PRE_VTA_PRECIOLOCAL(A_COD_CIA      => A_CIA,
                                                                        A_COD_LOCAL      => A_COD_LOCAL,
                                                                        A_COD_PRODUCTO => A_COD_PRODUCTO,
                                                                        A_COD_PRECIO   => V_PRECIO_PUBLICO);
                END IF;
            END IF;
        END IF;
        /****************************************/
        IF V_PRECIO < 0 THEN
            --Los negativos son los cod de error
            V_PRECIO := 0;
        END IF;
        /****************************************/
        RETURN V_PRECIO;
    END FN_PRECIO;
     FUNCTION FN_DEV_APTO_PETIT
    (
        A_COD_PETITORIO CAB_PETITORIO.COD_PETITORIO%TYPE,
        A_COD_PRODUCTO  LGT_PROD.COD_PROD%TYPE
    ) RETURN CHAR IS
        V_EXISTE FLOAT := 0;
    BEGIN
        --INCLUYE
        --V_EXISTE := FN_DEV_APTO_PETIT (A_COD_PETITORIO, A_COD_PRODUCTO, '0' )  ;
        --EXCLUYE
        --V_EXISTE := V_EXISTE - FN_DEV_APTO_PETIT (A_COD_PETITORIO, A_COD_PRODUCTO, '1' )  ;
        ------------------------------------------------------------------------
        --RAISE_APPLICATION_ERROR(-20005,A_COD_PETITORIO||A_COD_PRODUCTO);
        V_EXISTE := FN_DEV_APTO_PETIT(A_COD_PETITORIO, A_COD_PRODUCTO, '1');
        IF V_EXISTE = 1 THEN
            RETURN 'E';
        END IF;
        V_EXISTE := FN_DEV_APTO_PETIT(A_COD_PETITORIO, A_COD_PRODUCTO, '0');
        IF V_EXISTE = 1 THEN
            RETURN 'I';
        ELSE
            RETURN 'N';
        END IF;
        --SI VALE 1 ES APTO
        --SI VALE 0 NO ESTA EN LISTA O NO NO ES APTO
        RETURN V_EXISTE;
    END;

    FUNCTION FN_DEV_APTO_CONV(
         A_COD_PRODUCTO  LGT_PROD.COD_PROD%TYPE,
         A_COD_CONVENIO  MAE_CONVENIO.COD_CONVENIO%TYPE
      ) RETURN VARCHAR2
      AS
      I INTEGER:=0;
      C_EXCLUIDO         INTEGER:=0;
      C_INCLUIDO         INTEGER:=0;
      C_NOPETITORIO      INTEGER:=0;
      BEGIN
          begin
            SELECT SUM(DECODE(INDICADOR, 'E', 1, 0))  EXCLUIDO,
                   SUM(DECODE(INDICADOR, 'I', 1, 0))  INCLUIDO,
                   SUM(DECODE(INDICADOR, 'N', 1, 0))  NOPETITORIO
                   INTO
                   C_EXCLUIDO         ,
                   C_INCLUIDO         ,
                   C_NOPETITORIO

            FROM (
                    SELECT DISTINCT A_COD_PRODUCTO COD_PRODUCTO, PTOVENTA.PKG_PRODUCTO.FN_DEV_APTO_PETIT(COD_PETITORIO, A_COD_PRODUCTO ) INDICADOR
                    FROM PTOVENTA.REL_PETITORIO_CONVENIO
                    WHERE COD_CONVENIO= A_COD_CONVENIO
            )
            GROUP BY COD_PRODUCTO;
         exception
             when no_data_found then
                 RETURN 'N';
         end;
--@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
--@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
--SI ESTA EXCLUIDO NUNCA LO VENDO
     IF C_EXCLUIDO<>0 THEN
        RETURN 'E';
     END IF;

     IF C_INCLUIDO<>0 THEN
        RETURN 'I';
     END IF;

     RETURN 'N';

--@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
--@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


      END;

    FUNCTION FN_DEV_APTO_PETIT
    (
        A_COD_PETITORIO CAB_PETITORIO.COD_PETITORIO%TYPE,
        A_COD_PRODUCTO  LGT_PROD.COD_PROD%TYPE,
        A_FLG_EXCLUYE   VARCHAR2
    ) RETURN FLOAT IS
        --A_FLG_EXCLUYE             0 INCLUYE
        --                          1 EXCLUYE
        V_COD_PRODUCTO LGT_PROD.COD_PROD%TYPE;
    BEGIN
        BEGIN
            --RAISE_APPLICATION_ERROR(-20005,A_COD_PETITORIO||A_COD_PRODUCTO ) ;

            SELECT COD_PROD
              INTO V_COD_PRODUCTO
              FROM (
              SELECT  B.COD_PROD
                      FROM REL_PETITORIO_CLASE A,
                           AUX_MAE_PRODUCTO_COM B
                     WHERE A.COD_PETITORIO = A_COD_PETITORIO
                       AND A.COD_CLASE_COM = B.COD_CLASE_COM
                       AND A.FLG_ACTIVO = '1'
                       AND TRIM(NVL(A.FLG_EXCLUIDO, '0')) = A_FLG_EXCLUYE
                       AND B.cod_prod = A_COD_PRODUCTO
                    UNION ALL
                    SELECT B.COD_PROD
                      FROM REL_PETITORIO_SUBCLASE A,
                           AUX_MAE_PRODUCTO_COM           B
                     WHERE A.COD_PETITORIO = A_COD_PETITORIO
                       AND A.COD_CLASE_COM = B.COD_CLASE_COM
                       AND A.COD_SUBCLASE_COM = B.COD_SUBCLASE_COM
                       AND A.FLG_ACTIVO = '1'
                       AND TRIM(NVL(A.FLG_EXCLUIDO, '0')) = A_FLG_EXCLUYE
                       AND B.COD_PROD = A_COD_PRODUCTO
                    UNION ALL
                    SELECT B.COD_PROD
                      FROM REL_PETITORIO_FAMILIA A,
                           AUX_MAE_PRODUCTO_COM          B
                     WHERE A.COD_PETITORIO = A_COD_PETITORIO
                       AND A.COD_CLASE_COM = B.COD_CLASE_COM
                       AND A.COD_SUBCLASE_COM = B.COD_SUBCLASE_COM
                       AND A.COD_FAM_COM = B.COD_FAM_COM
                       AND A.FLG_ACTIVO = '1'
                       AND TRIM(NVL(A.FLG_EXCLUIDO, '0')) = A_FLG_EXCLUYE
                       AND B.COD_PROD = A_COD_PRODUCTO
                    UNION ALL
                    SELECT B.COD_PROD
                      FROM REL_PETITORIO_CATEG A,
                           AUX_MAE_PRODUCTO_COM        B
                     WHERE A.COD_PETITORIO = A_COD_PETITORIO
                       AND A.COD_CLASE_COM = B.COD_CLASE_COM
                       AND A.COD_SUBCLASE_COM = B.COD_SUBCLASE_COM
                       AND A.COD_FAM_COM = B.COD_FAM_COM
                       AND A.COD_CATEGORIA_COM = B.COD_CATEGORIA_COM
                       AND A.FLG_ACTIVO = '1'
                       AND TRIM(NVL(A.FLG_EXCLUIDO, '0')) = A_FLG_EXCLUYE
                       AND B.COD_PROD = A_COD_PRODUCTO
                    UNION ALL
                    SELECT B.COD_PROD
                      FROM REL_PETITORIO_GRUPO A,
                           AUX_MAE_PRODUCTO_COM        B
                     WHERE A.COD_PETITORIO = A_COD_PETITORIO
                       AND A.COD_GRUPO_TERAP = B.COD_GRUPO_TERAP
                       AND A.FLG_ACTIVO = '1'
                       AND TRIM(NVL(A.FLG_EXCLUIDO, '0')) = A_FLG_EXCLUYE
                       AND B.COD_PROD = A_COD_PRODUCTO
                    UNION ALL
                    SELECT Y.COD_PROD
                      FROM REL_PETITORIO_ACCION X,
                           AUX_MAE_PRODUCTO_COM Y
                     WHERE X.COD_PETITORIO = A_COD_PETITORIO
                       AND X.COD_GRUPO_TERAP = Y.COD_GRUPO_TERAP
                       AND X.COD_ACCION_TERAP = Y.COD_ACCION_TERAP
                       AND X.FLG_ACTIVO = '1'
                       AND TRIM(NVL(X.FLG_EXCLUIDO, '0')) = A_FLG_EXCLUYE
                       AND Y.COD_PROD = A_COD_PRODUCTO
                    UNION ALL
                    SELECT B.COD_PROD
                      FROM REL_PETITORIO_LABORATORIO A,
                           AUX_MAE_PRODUCTO_COM              B
                     WHERE A.COD_PETITORIO = A_COD_PETITORIO
                       AND A.COD_LABORATORIO = B.COD_LABORATORIO
                       AND A.FLG_ACTIVO = '1'
                       AND TRIM(NVL(A.FLG_EXCLUIDO, '0')) = A_FLG_EXCLUYE
                       AND B.COD_PROD = A_COD_PRODUCTO
                    UNION ALL
                    SELECT B.COD_PROD
                      FROM REL_PETITORIO_LINEA A,
                           AUX_MAE_PRODUCTO_COM        B
                     WHERE A.COD_PETITORIO = A_COD_PETITORIO
                       AND A.COD_LABORATORIO = B.COD_LABORATORIO
                       AND A.COD_LINEA = B.COD_LINEA
                       AND A.FLG_ACTIVO = '1'
                       AND TRIM(NVL(A.FLG_EXCLUIDO, '0')) = A_FLG_EXCLUYE
                       AND B.COD_PROD = A_COD_PRODUCTO
                    UNION ALL
                    SELECT B.COD_PROD COD_PRODUCTO
                      FROM REL_PETITORIO_PRODUCTO A,
                           LGT_PROD                       B
                     WHERE A.COD_PETITORIO = A_COD_PETITORIO
                       AND A.COD_PRODUCTO = B.COD_PROD
                       AND A.FLG_ACTIVO = '1'
                       AND TRIM(NVL(A.FLG_EXCLUIDO, '0')) = A_FLG_EXCLUYE
                       AND B.COD_PROD = A_COD_PRODUCTO)
             WHERE COD_PROD = A_COD_PRODUCTO AND ROWNUM<2;
            RETURN 1;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                RETURN 0;
        END;
    END FN_DEV_APTO_PETIT;
FUNCTION FN_SEGMENTO(A_CADENA    VARCHAR2,
                                       A_ITEM      INT,
                                       A_SEPARADOR CHAR DEFAULT '|')
    RETURN VARCHAR2 AS
    W_POSICION_INI INT;
    W_POSICION_FIN INT;
BEGIN
    IF A_ITEM = 1 THEN
        W_POSICION_INI := 1;
        W_POSICION_FIN := INSTR(A_CADENA, A_SEPARADOR, 1, 1) - 1;
        IF W_POSICION_FIN = -1 THEN
            W_POSICION_FIN := 1;
        END IF;
    ELSE
        W_POSICION_INI := INSTR(A_CADENA, A_SEPARADOR, 1, A_ITEM - 1) + 1;
        IF W_POSICION_INI = 1 THEN
            RETURN '';
        END IF;
        W_POSICION_FIN := INSTR(A_CADENA, A_SEPARADOR, 1, A_ITEM) - 1;
        IF W_POSICION_FIN = -1 THEN
            W_POSICION_FIN := LENGTH(A_CADENA);
        END IF;
    END IF;
    RETURN SUBSTR(A_CADENA,
                  W_POSICION_INI,
                  W_POSICION_FIN - W_POSICION_INI + 1);
END;
  FUNCTION FN_PRE_C_PVP ( A_COD_PRODUCTO VARCHAR2, A_COD_TIPO VARCHAR2 )
    RETURN FLOAT

       AS
       c_precio float:=0;
begin

       select prc_kairos
       into  c_precio
       from ptoventa.aux_mae_producto_com
       where cod_prod = A_COD_PRODUCTO;
       return nvl(c_precio,0);
end;
FUNCTION FN_PRE_ULT_COMPRA(A_COD_PRODUCTO lgt_prod.COD_PROD%TYPE)
    RETURN FLOAT IS

    C_PRECIO FLOAT;
BEGIN
  select val_prec_prom
  into C_PRECIO
  from lgt_prod
  where cod_prod = a_cod_producto;
  return C_PRECIO;
  end;
end ;
/

