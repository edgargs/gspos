--------------------------------------------------------
--  DDL for Package Body PTOVENTA_CUPON
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_CUPON" is

  PROCEDURE ACTUALIZA_CUPONES_MATRIZ(cCodGrupoCia_in IN CHAR)
  AS
    v_cIndLinea CHAR(1);
    v_nCant INTEGER;
  BEGIN
    --Verifica pendientes
    SELECT COUNT(*) INTO v_nCant
    FROM VTA_CUPON C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.FEC_PROCESA_MATRIZ IS NULL;
    IF v_nCant > 0 THEN
      --Verifica conn matriz
      v_cIndLinea := ACT_VERIFICA_CONN_MATRIZ;
      IF v_cIndLinea = 'S' THEN
        --Cupones emitidos
        ACT_CUPONES_EMITIDOS(cCodGrupoCia_in);
        --Cupones anulados
        ACT_CUPONES_ANULADOS(cCodGrupoCia_in);
        --Cupones usados
        ACT_CUPONES_USADOS(cCodGrupoCia_in);
      END IF;
    END IF;

    COMMIT;
  END;
  /***************************************************************************/
  PROCEDURE ACT_CUPONES_EMITIDOS(cCodGrupoCia_in IN CHAR)
  AS
    CURSOR curCupones IS
    SELECT TRIM(COD_CUPON)AS COD_CUPON,FEC_INI,FEC_FIN
    FROM VTA_CUPON
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND ESTADO = 'A'
          AND FEC_PROCESA_MATRIZ IS NULL;

    v_vIdUsu VARCHAR2(15) := 'PCK_ACT_EMIT';
    vRetorno CHAR(1) := 'X';
  BEGIN
    FOR cupon IN curCupones
    LOOP
      EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.GRABAR_CUPON@XE_000(:1,:2,:3,:4,:5,:6); END;'
      --USING cCodGrupoCia_in,cupon.COD_CUPON,v_vIdUsu,IN OUT vRetorno;
      USING cCodGrupoCia_in,cupon.COD_CUPON,v_vIdUsu,TRIM(cupon.FEC_INI),TRIM(cupon.FEC_FIN),IN OUT vRetorno;--JCORTEZ 30/07/08
      DBMS_OUTPUT.PUT_LINE('vRetorno:'||vRetorno);
      IF vRetorno = 'S' THEN
        UPDATE VTA_CUPON
        SET FEC_PROCESA_MATRIZ = SYSDATE,
            USU_PROCESA_MATRIZ = v_vIdUsu
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_CUPON = cupon.COD_CUPON;
        COMMIT;
      END IF;
    END LOOP;
  END;
  /***************************************************************************/
  PROCEDURE ACT_CUPONES_ANULADOS(cCodGrupoCia_in IN CHAR)
  AS
    CURSOR curCupones IS
    SELECT TRIM(COD_CUPON) AS COD_CUPON,FEC_INI,FEC_FIN
    FROM VTA_CUPON
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND ESTADO = 'N'
          AND FEC_PROCESA_MATRIZ IS NULL;

    v_vIdUsu VARCHAR2(15) := 'PCK_ACT_ANUL';
    vRetorno CHAR(1) := 'X';
  BEGIN
    FOR cupon IN curCupones
    LOOP
      EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.CONSULTA_ESTADO_CUPON@XE_000(:1,:2,:3); END;'
      USING cCodGrupoCia_in,cupon.COD_CUPON,IN OUT vRetorno;
      IF vRetorno = '0' THEN
        EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.GRABAR_CUPON@XE_000(:1,:2,:3,:4,:5,:6); END;'
        --USING cCodGrupoCia_in,cupon.COD_CUPON,v_vIdUsu,IN OUT vRetorno;
        USING cCodGrupoCia_in,cupon.COD_CUPON,v_vIdUsu,TRIM(cupon.FEC_INI),TRIM(cupon.FEC_FIN),IN OUT vRetorno;--JCORTEZ 30/07/08
        COMMIT;
      END IF;

      EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.ACT_ESTADO_CUPON@XE_000(:1,:2,:3,:4,:5); END;'
      USING cCodGrupoCia_in,cupon.COD_CUPON,'N',v_vIdUsu,IN OUT vRetorno;
      DBMS_OUTPUT.PUT_LINE('vRetorno:'||vRetorno);
      IF vRetorno = 'S' THEN
        UPDATE VTA_CUPON
        SET FEC_PROCESA_MATRIZ = SYSDATE,
            USU_PROCESA_MATRIZ = v_vIdUsu
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_CUPON = cupon.COD_CUPON;
        COMMIT;
      END IF;
    END LOOP;
  END;
  /***************************************************************************/
  PROCEDURE ACT_CUPONES_USADOS(cCodGrupoCia_in IN CHAR)
  AS
    CURSOR curCupones IS
    SELECT TRIM(COD_CUPON) AS COD_CUPON,FEC_INI,FEC_FIN
    FROM VTA_CUPON
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND ESTADO = 'U'
          AND FEC_PROCESA_MATRIZ IS NULL;

    v_vIdUsu VARCHAR2(15) := 'PCK_ACT_USO';
    vRetorno CHAR(1) := 'X';
  BEGIN
    FOR cupon IN curCupones
    LOOP
      EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.CONSULTA_ESTADO_CUPON@XE_000(:1,:2,:3); END;'
      USING cCodGrupoCia_in,cupon.COD_CUPON,IN OUT vRetorno;
      IF vRetorno = '0' THEN
        EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.GRABAR_CUPON@XE_000(:1,:2,:3,:4,:5,:6); END;'
        --USING cCodGrupoCia_in,cupon.COD_CUPON,v_vIdUsu,IN OUT vRetorno;
        USING cCodGrupoCia_in,cupon.COD_CUPON,v_vIdUsu,TRIM(cupon.FEC_INI),TRIM(cupon.FEC_FIN),IN OUT vRetorno;--JCORTEZ 30/07/08
        COMMIT;
      END IF;

      EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.ACT_ESTADO_CUPON@XE_000(:1,:2,:3,:4,:5); END;'
      USING cCodGrupoCia_in,cupon.COD_CUPON,'U',v_vIdUsu,IN OUT vRetorno;
      DBMS_OUTPUT.PUT_LINE('vRetorno:'||vRetorno);
      IF vRetorno = 'S' THEN
        UPDATE VTA_CUPON
        SET FEC_PROCESA_MATRIZ = SYSDATE,
            USU_PROCESA_MATRIZ = v_vIdUsu
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_CUPON = cupon.COD_CUPON;
        COMMIT;
      END IF;
    END LOOP;
  END;
  /***************************************************************************/

PROCEDURE CUP_P_ACT_GENERAL_CUPON(cCodGrupoCia_in IN CHAR,
                                          cCodCupon_in    IN CHAR,
                                          vIdUsu_in       IN VARCHAR2,
                                          cTipoConsulta   IN CHAR)
  IS
  BEGIN
        IF cTipoConsulta = TIPO_CONSULTA_ACTUALIZA_MATRIZ THEN

            UPDATE VTA_CUPON
            SET FEC_PROCESA_MATRIZ = SYSDATE,
                USU_PROCESA_MATRIZ = vIdUsu_in
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND   COD_CUPON     = cCodCupon_in;

        ELSIF  cTipoConsulta = TIPO_CONSULTA_ACTUALIZA_LOCAL THEN

            UPDATE VTA_CUPON
            SET    ESTADO = 'U',
                   FEC_PROCESA_MATRIZ = NULL,
                   USU_PROCESA_MATRIZ = NULL,
                   FEC_MOD_CUP_CAB = SYSDATE,
                   USU_MOD_CUP_CAB = vIdUsu_in
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND    COD_CUPON    = cCodCupon_in;

        END IF;

  END;
  /***************************************************************************/
  FUNCTION CUP_F_CHAR_BLOQ_EST(cCodGrupoCia_in IN CHAR,
                               cCodCupon_in    IN CHAR)
  RETURN CHAR
  IS
  v_cEstado VTA_CUPON.ESTADO%TYPE;
  BEGIN
      SELECT ESTADO
      INTO   v_cEstado
      FROM   VTA_CUPON
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_CUPON     = cCodCupon_in
      FOR UPDATE;

      return v_cEstado;
  END;
/*------------------------------------------------------------------------------------------------------------------------
GOAL : Validacion de Cupon Multiuso
History : 21-JUL-14  TCT Modifica forma de leer codigo de campaña
--------------------------------------------------------------------------------------------------------------------------*/
  FUNCTION CUP_F_CHAR_IND_MULTIPLO_CUP(cCodGrupoCia_in IN CHAR,
                                      cCodCupon_in    IN CHAR)
  RETURN CHAR
  IS
    vIndMultiUso VTA_CAMPANA_CUPON.IND_MULTIUSO%TYPE := 'A'; --JCORTEZ 17/08/2008
    v_cCodigoCamp VTA_CAMPANA_CUPON.COD_CAMP_CUPON%TYPE;

  BEGIN
    --- <21-JUL-14  TCT New Forma Lee Codigo Cupon > 
      --v_cCodigoCamp := SUBSTR(cCodCupon_in,1,5);
      v_cCodigoCamp := ptoventa_vta.fn_get_cod_campa(ccodgrupocia_in => cCodGrupoCia_in,
                                           ccodcupon_in => cCodCupon_in);
   --- < /21-JUL-14  TCT New Forma Lee Codigo Cupon >                                         

      /*SELECT nvl(B.IND_MULTIUSO,'N')
      INTO   vIndMultiUso
      FROM   VTA_CUPON A,
             VTA_CAMPANA_CUPON B
      WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    A.COD_CUPON     = cCodCupon_in
      AND    A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
      AND    A.COD_CAMPANA   = B.COD_CAMP_CUPON;*/

      SELECT nvl(B.IND_MULTIUSO,'N')
      INTO   vIndMultiUso
      FROM   VTA_CAMPANA_CUPON B
      WHERE  B.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    B.COD_CAMP_CUPON   = v_cCodigoCamp;

      return vIndMultiUso;
  END;
  /**************************************************************************/
 FUNCTION CUP_F_CUR_CUP_PED(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cNumPedVta_in   IN CHAR,
                            cIndLineaMatriz IN CHAR DEFAULT 'N',
                            cTipoConsulta   IN CHAR)
   RETURN FarmaCursor
   IS
    cur FarmaCursor;
    v_cIndLinea CHAR(1);
    v_nCant     INTEGER;
    vRetorno    CHAR(1) := 'X';
  BEGIN
    v_cIndLinea := cIndLineaMatriz;
    IF cTipoConsulta = TIPO_CONSULTA_CUP_ANUL_MATRIZ THEN
              --Retorna los cupones emitidos anulados
              --para anularlas en matriz
              SELECT COUNT(*) INTO v_nCant
              FROM VTA_CAMP_PEDIDO_CUPON C,
                   VTA_CUPON O
              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
              AND   C.COD_LOCAL     = cCodLocal_in
              AND   C.NUM_PED_VTA   = cNumPedVta_in
              AND   C.ESTADO   = 'E'
              AND   C.IND_IMPR = 'S'
              AND   C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
              AND   C.COD_CUPON     = O.COD_CUPON
              AND   O.ESTADO = 'N'
              AND   O.FEC_PROCESA_MATRIZ IS NULL;

              IF v_nCant > 0 THEN

                    OPEN cur FOR
                        SELECT TRIM(O.COD_CUPON) || 'Ã' ||
                               o.FEC_INI   || 'Ã' ||
                               o.FEC_FIN
                        FROM VTA_CAMP_PEDIDO_CUPON C,
                             VTA_CUPON O
                        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND   C.COD_LOCAL     = cCodLocal_in
                        AND   C.NUM_PED_VTA   = cNumPedVta_in
                        AND   C.ESTADO   = 'E'
                        AND   C.IND_IMPR = 'S'
                        AND   C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
                        AND   C.COD_CUPON     = O.COD_CUPON
                        AND   O.ESTADO        = 'N'
                        AND   O.FEC_PROCESA_MATRIZ IS NULL;

              END IF;

    ELSE
        IF cTipoConsulta = TIPO_CONSULTA_ACTUALIZA_MATRIZ THEN
              SELECT SUM(CANT) INTO v_nCant
              FROM   (
                      SELECT COUNT(COD_CUPON) AS CANT
                      FROM VTA_CAMP_PEDIDO_CUPON
                      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                      AND   COD_LOCAL     = cCodLocal_in
                      AND   NUM_PED_VTA   = cNumPedVta_in
                      --01/09/2008 dubilluz
                      AND   ESTADO = 'S'
                      AND   IND_USO = 'S'
                      );

              IF v_nCant > 0 THEN

               -- IF v_cIndLinea = 'S' THEN
                    OPEN cur FOR
                        SELECT A.COD_CUPON || 'Ã' ||
                               A.ESTADO    || 'Ã' ||
                               B.FEC_INI   || 'Ã' ||
                               B.FEC_FIN
                        FROM VTA_CAMP_PEDIDO_CUPON A,
                             VTA_CUPON B
                        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND   A.COD_LOCAL = cCodLocal_in
                        AND   A.NUM_PED_VTA = cNumPedVta_in
                        --01/09/2008 dubilluz
                        AND   A.Estado = 'S'
                        AND   A.IND_USO = 'S' --Los emitidos se graban por defecto con 'S'
                        AND   A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
                        AND   A.COD_CAMP_CUPON=B.COD_CAMPANA
                        AND   A.COD_CUPON=B.COD_CUPON;

                --END IF;

              END IF;
        ELSE
           IF cTipoConsulta = TIPO_CONSULTA_VALIDA_CUPON THEN
              --EN ESTA CONSULTA SOLO SE RETORNARA LOS CUPONES
              --USADOS Y QUE SE EFECTUARON AL PEDIDO
              SELECT COUNT(COD_CUPON) INTO v_nCant
              FROM (
                SELECT COD_CUPON
                FROM VTA_CAMP_PEDIDO_CUPON
                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                      AND COD_LOCAL = cCodLocal_in
                      AND NUM_PED_VTA = cNumPedVta_in
                      AND ESTADO = 'S'
                      AND IND_USO = 'S'
                    );

              IF v_nCant > 0 THEN

                    OPEN cur FOR
                        SELECT C.COD_CUPON || 'Ã' ||
                               CU.FEC_INI   || 'Ã' ||
                               CU.FEC_FIN
                        FROM VTA_CAMP_PEDIDO_CUPON C,
                             VTA_CUPON CU
                        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND C.COD_LOCAL = cCodLocal_in
                              AND C.NUM_PED_VTA = cNumPedVta_in
                              AND C.ESTADO = 'S'
                              AND C.IND_USO = 'S'
                              AND C.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
                              AND C.COD_CAMP_CUPON = CU.COD_CAMPANA
                              AND C.COD_CUPON = CU.COD_CUPON;
              END IF;
           ELSE
             IF cTipoConsulta = TIPO_CONSULTA_NOTA_CREDITO THEN
                 IF v_cIndLinea = 'S' THEN
                    OPEN cur FOR
                    SELECT C.COD_GRUPO_CIA || 'Ã' ||
                           TRIM(C.COD_CUPON)
                    FROM VTA_CAMP_PEDIDO_CUPON C,
                         VTA_CUPON O
                    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND   C.COD_LOCAL     = cCodLocal_in
                    AND   C.NUM_PED_VTA   = cNumPedVta_in
                    AND   C.ESTADO   = 'E'
                    AND   C.IND_IMPR = 'S'
                    AND   C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
                    AND   C.COD_CUPON     = O.COD_CUPON
                    AND   O.ESTADO        = 'A';
                 END IF;
             ELSE
                 IF cTipoConsulta = TIPO_CONSULTA_CUP_EMIT_USADOS THEN
                    -- cupones emitidos usados
                    SELECT COUNT(*) INTO v_nCant
                    FROM   VTA_CAMP_PEDIDO_CUPON C,
                           VTA_CUPON O
                    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND   C.COD_LOCAL     = cCodLocal_in
                    AND   C.NUM_PED_VTA   = cNumPedVta_in
                    AND   C.ESTADO   = 'E'
                    AND   C.IND_IMPR = 'S'
                    AND   C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
                    AND   C.COD_CUPON     = O.COD_CUPON
                    AND   O.ESTADO = 'U';

                    IF v_nCant > 0 THEN
                          OPEN cur FOR
                              SELECT c.cod_cupon
                              FROM   VTA_CAMP_PEDIDO_CUPON C,
                                     VTA_CUPON O
                              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND   C.COD_LOCAL     = cCodLocal_in
                              AND   C.NUM_PED_VTA   = cNumPedVta_in
                              AND   C.ESTADO   = 'E'
                              AND   C.IND_IMPR = 'S'
                              AND   C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
                              AND   C.COD_CUPON     = O.COD_CUPON
                              AND   O.ESTADO = 'U';
                    END IF;
                 END IF;
             END IF;

           END IF;

        END IF;
    END IF;

    -- PARA TODA CONSULTA QUE NO DE RESULTADO SE RETORNARA EL VALOR DE NULO
    /*
    if cur is null then
    OPEN cur FOR
    select 'NULO' from dual;
    end if;
    */
    if cur is null then
    OPEN cur FOR
    select 'Ã' from dual where 1=2;
    end if;


    return cur;

  END ;
  /***********************************************************************************/
  FUNCTION ACT_VERIFICA_CONN_MATRIZ
  RETURN CHAR
  IS
    v_cIndLinea CHAR(1);

    V_TIME_ESTIMADO CHAR(20);
    v_time_1 TIMESTAMP;
    v_time_2 TIMESTAMP;
    V_RESULT INTERVAL DAY TO SECOND;
  BEGIN
    BEGIN
      SELECT LLAVE_TAB_GRAL INTO V_TIME_ESTIMADO
          FROM   PBL_TAB_GRAL
          WHERE  ID_TAB_GRAL = 76
          AND    COD_APL = 'PTO_VENTA'
          AND    COD_TAB_GRAL = 'REPOSICION';

      SELECT CURRENT_TIMESTAMP INTO v_time_1 FROM dual ;
      EXECUTE IMMEDIATE ' SELECT COD_CLI ' ||
                        ' FROM CON_CLI_CONV@XE_000' ||
                        ' WHERE ROWNUM < 2 ';
      SELECT CURRENT_TIMESTAMP INTO v_time_2 FROM dual ;

      V_RESULT := v_time_2 - v_time_1 ;

      IF(TO_CHAR(V_RESULT) > TRIM(V_TIME_ESTIMADO)) THEN
        v_cIndLinea  := 'N';
      ELSE
        v_cIndLinea  := 'S';
      END IF;
    EXCEPTION
      WHEN OTHERS THEN
      v_cIndLinea := 'N';
    END;
--     v_cIndLinea := 'N';
    RETURN v_cIndLinea;
  END;


/* ***************************************************************************** */
 FUNCTION CUP_F_CHAR_CUP_PED(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cNumPedVta_in   IN CHAR,
                            cTipoCupon IN CHAR)
 RETURN CHAR
 IS
  vResRpta char(1) :=  'N';
  v_nCant number;
 BEGIN

     IF cTipoCupon = 'U' THEN

        SELECT COUNT(*) INTO v_nCant
        FROM   VTA_CAMP_PEDIDO_CUPON  c
        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   C.COD_LOCAL     = cCodLocal_in
        AND   C.NUM_PED_VTA   = cNumPedVta_in
        AND   C.ESTADO   = 'S';


        IF v_nCant > 0 THEN
           vResRpta := 'S';
        END IF;
     ELSIF
                cTipoCupon = 'E' THEN
                SELECT COUNT(*) INTO v_nCant
                FROM   VTA_CAMP_PEDIDO_CUPON  c
                WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                AND   C.COD_LOCAL     = cCodLocal_in
                AND   C.NUM_PED_VTA   = cNumPedVta_in
                AND   C.ESTADO   = 'E';

                IF v_nCant > 0 THEN
                   vResRpta := 'S';
                END IF;


     END IF ;

        return vResRpta;

 END;

/* -----------------------------------------------------------------------------------------------------------------------
GOAL : VALIDACION DE CUPON
History : 21-JUL-14  TCT Modifica lectura de codigo de Campaña
--------------------------------------------------------------------------------------------------------------------------*/
FUNCTION CUP_F_CUR_VALIDA_CUPON(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cCadenaCupon_in IN CHAR,
                                cIndMultiUso_in IN CHAR default 'N',
                                cDniCliente     IN CHAR DEFAULT NULL)
  RETURN FarmaCursor
  IS
    curLista FarmaCursor;

    v_cCodigoCamp VTA_CAMPANA_CUPON.COD_CAMP_CUPON%TYPE;
    v_cCodLocalEmi PBL_LOCAL.COD_LOCAL%TYPE;
    v_cSecuencial VARCHAR2(10);

    v_eControlCupon EXCEPTION;

    v_nCantCamp INTEGER;
    v_nCantLocal INTEGER;
    v_nCantLocalA INTEGER;
    v_nCantLocalB INTEGER;
    v_nCantLocalC INTEGER;
    v_nCantCupon INTEGER;
    v_nCantCuponA INTEGER;
    v_nCantCuponB INTEGER;
    v_nCampVal INTEGER;

    v_nCantProd INTEGER;
    v_eControlBarra EXCEPTION;

    v_cEstCupon VTA_CUPON.ESTADO%TYPE;
    -- jcallo 09.10.2008 para validacion de sexo y edad de cliente --
    cSexo    char(1);
    dFecNaci date;
    cIndFid    char(1);
    cValido    char(1) := 'N';

    -- fin jcallo --
  BEGIN

    --Verifica si es un producto
    SELECT COUNT(*) INTO v_nCantProd
    FROM LGT_COD_BARRA B
    WHERE B.COD_BARRA = cCadenaCupon_in;
    IF v_nCantProd > 0 THEN
      RAISE v_eControlBarra;
    END IF;

    IF CUP_F_CHAR_VALIDA_EAN13(cCadenaCupon_in)= 'N' THEN
       RAISE_APPLICATION_ERROR(-20018,'Cupón no cumple el correcto formato correcto.');
    END IF;

    --JCORTEZ 15/08/2008 se consulta el cupon en caso de multiuso
    --Se inicializa las variables segun el tipo de IndMultiuso
    IF(cIndMultiUso_in='N')THEN
     ---< 21-jul14  TCT NEW FORM CODIGO CAMP >
      --v_cCodigoCamp := SUBSTR(cCadenaCupon_in,1,5);
            v_cCodigoCamp := ptoventa_vta.fn_get_cod_campa(ccodgrupocia_in => cCodGrupoCia_in,
                                           ccodcupon_in => cCadenaCupon_in);
    ---< /21-jul14  TCT NEW FORM CODIGO CAMP >                                      
      
      v_cCodLocalEmi := SUBSTR(cCadenaCupon_in,6,3);
      v_cSecuencial := SUBSTR(cCadenaCupon_in,9,5);

    ELSIF (cIndMultiUso_in='S')THEN
     begin
        SELECT X.COD_CAMPANA,X.COD_LOCAL,X.SEC_CUPON
        INTO   v_cCodigoCamp,v_cCodLocalEmi,v_cSecuencial
        FROM   VTA_CUPON X
        WHERE  X.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    X.COD_CUPON     = TRIM(cCadenaCupon_in);
        exception
        when no_data_found then

        RAISE_APPLICATION_ERROR(-20010,'Cupon no esta vigente.');

        end;



    END IF;

     --jquispe 23.07.2012
        if v_cCodigoCamp = '12719' then
            v_cCodigoCamp := '12723';
        end if;

    --JCALLO 09.10.2008 --

    IF cDniCliente IS NOT NULL AND LENGTH(TRIM(cDniCliente))>0 THEN
       --SI TIENE DNI QUIERE DECIR QUE ES UN CLIENTE FIDELIZADO
       --SE DEBE VALIDAR DE QUE EL CUPON SEA VALIDO YA SEA CUPO DE FID O NO.
       cValido := CUP_F_CHAR_VALIDA_CUPON_FID(cCodGrupoCia_in, cCodLocal_in, v_cCodigoCamp ,cDniCliente);
    ELSE -- SI NO TIENE DNI ES UN CLIENTE NO FIDELIZADO POR LO CUAL SOLO DEBE PERMITIR USAR CUP0NES DE NO FIDELIZACION
       -- indicador de fidelizacion
       select c.ind_fid into cIndFid
       from vta_campana_cupon c
       where c.cod_grupo_cia=cCodGrupoCia_in
         and c.cod_camp_cupon = v_cCodigoCamp;

    -- 2009-04-22 Se cambia para que sólo los clientes fidelizados puedan usar estos cupones
       IF cIndFid = 'S' THEN
          cValido := 'N';
       ELSE
          cValido := 'S';
       END IF;
        --siempre dejara uzsar el cupon sea o no sea de fidelizacion
--        cValido := 'S';


    END IF;

    -- SI PASO LA VALIDACION DE FIDELIZACION CONTINUA CON TODA LA VALIDACION NORMAL DE LA CAMPAÑA
    IF( cValido = 'S') THEN
      --1. Verifica la validez de uso y estado de la campaña
      SELECT COUNT(*) INTO v_nCantCamp
      FROM VTA_CAMPANA_CUPON
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_CAMP_CUPON = v_cCodigoCamp
            AND ESTADO = 'A'
            AND TRUNC(SYSDATE) BETWEEN FECH_INICIO_USO AND FECH_FIN_USO;

      IF v_nCantCamp <> 1 THEN
         dbms_output.put_line('error 01');
         RAISE_APPLICATION_ERROR(-20003,'Campana '||v_cCodigoCamp||' no es valida.');
      END IF;

      --2. Verifica que la campana tenga locales asignados.
      SELECT COUNT(*)
      INTO   v_nCantLocal
      FROM   VTA_CAMP_X_LOCAL
      WHERE  COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    COD_CAMP_CUPON = v_cCodigoCamp;

      --Si la campaña esta asignada a varios locales

      IF v_nCantLocal > 0 THEN
        --Verifica que el local de uso permita la campana.
        SELECT COUNT(*) INTO v_nCantLocalA
        FROM VTA_CAMP_X_LOCAL
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_CAMP_CUPON = v_cCodigoCamp
              AND COD_LOCAL = cCodLocal_in;

        IF v_nCantLocalA <> 1 THEN
          dbms_output.put_line('error 2');
          RAISE_APPLICATION_ERROR(-20004,'Local no valido para el uso del cupon.');
        END IF;

        --Verifica que el local de emision permita la campana
        SELECT COUNT(*) INTO v_nCantLocalB
        FROM VTA_CAMP_X_LOCAL
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_CAMP_CUPON = v_cCodigoCamp
              AND COD_LOCAL = v_cCodLocalEmi;

        IF v_nCantLocalB <> 1 THEN
          dbms_output.put_line('error 3');
          RAISE_APPLICATION_ERROR(-20005,'Local de emision no valido.');
        END IF;

      ELSIF (cIndMultiUso_in='N')THEN
        --Esta validacion no aplica a Cupones MultiUso porque
        --los cupones multiuso no tendran un codigo de local de venta
        --Verifica que el local de emision sea un local de venta.
        SELECT COUNT(*)
        INTO   v_nCantLocalC
        FROM   PBL_LOCAL
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL     = v_cCodLocalEmi
        AND    TIP_LOCAL     = 'V';
        -- < 16-JUN-14 TCT ya no se validara Local Emisor de Cupon >
       /* IF v_nCantLocalC <> 1 and v_cCodLocalEmi <> '000' THEN
          dbms_output.put_line('error 4');
          RAISE_APPLICATION_ERROR(-20006,'Local de emision no es local de venta.');
        END IF;*/

      END IF;

      --FIN VALIDACION 2.

      --3. Verifica que exista el cupon en el local
      SELECT COUNT(*)
      INTO   v_nCantCuponA
      FROM   VTA_CUPON
      WHERE  COD_GRUPO_CIA   = cCodGrupoCia_in
      AND    COD_LOCAL       = cCodLocal_in
      AND    TRIM(COD_CUPON) = cCadenaCupon_in;

      --DBMS_OUTPUT.PUT_LINE('Existe cupon: '||v_nCantCuponA);
      --jquispe 26.03.2012
      --4. Verifica que el cupon este vigente
      SELECT COUNT(*)
      INTO   v_nCantCuponB
      FROM   VTA_CUPON
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    decode(cCodLocal_in,'000',cCodLocal_in,COD_LOCAL)     = cCodLocal_in
      AND    COD_CUPON     = cCadenaCupon_in
      AND    FEC_INI IS NOT NULL
      AND    FEC_FIN IS NOT NULL
      AND    TRUNC(SYSDATE) BETWEEN FEC_INI AND FEC_FIN;

      -- Si el cupon existe en el local
      IF v_nCantCuponA > 0 THEN
        --Verifica que el cupon este activo porque se encuentra en el local
        SELECT COUNT(*)
        INTO   v_nCantCupon
        FROM   VTA_CUPON
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL     = cCodLocal_in
        AND    TRIM(COD_CUPON) = cCadenaCupon_in
        AND    ESTADO = 'A';

        --DBMS_OUTPUT.PUT_LINE('Cupon no valido: '||v_nCantCupon);

        --El cupon no se encuentra activo en el local
        IF v_nCantCupon <> 1 THEN
          SELECT ESTADO
          INTO   v_cEstCupon
          FROM   VTA_CUPON
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND    COD_LOCAL     = cCodLocal_in
          AND    TRIM(COD_CUPON) = cCadenaCupon_in;

          --Se valida el estado del cupon que se encuentra en el local
          IF v_cEstCupon = 'U' THEN
            dbms_output.put_line('error 5');
            RAISE_APPLICATION_ERROR(-20007,'Cupon ya fue usado.');
          ELSE
            dbms_output.put_line('error 6');
            RAISE_APPLICATION_ERROR(-20008,'Cupon esta anulado.');
          END IF;

        END IF;

        -- Si el cupon no esta vigente
        IF v_nCantCuponB = 0 THEN
          dbms_output.put_line('error 7');
          RAISE_APPLICATION_ERROR(-20010,'Cupon no esta vigente.');
        END IF;

      ELSE
          IF cCodLocal_in = v_cCodLocalEmi THEN
          dbms_output.put_line('error 8');
          RAISE_APPLICATION_ERROR(-20010,'Cupon no esta vigente.');
          END IF;

      END IF;

      --6. Verifica que la campana tenga los valores indicados.
      SELECT COUNT(*)
      INTO   v_nCampVal
      FROM   VTA_CAMPANA_CUPON
      WHERE  COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    COD_CAMP_CUPON = v_cCodigoCamp
      AND    TIP_CAMPANA    = 'C'
      AND    TIP_CUPON   IS NOT NULL
      AND    VALOR_CUPON IS NOT NULL;

      -- La campaña se creo bien con los datos exactos y necesarios
      IF v_nCampVal <> 1 THEN
        dbms_output.put_line('error 9');
        RAISE_APPLICATION_ERROR(-20009,'Campana no valido.');
      END IF;

      -- Si el cupon existe en el local O NO se envia la informacion
      -- Luego si hay linea validara en Matriz caso contrario
      -- Segun los acordado se permitira usar el cupon
      OPEN curLista FOR
           SELECT cCadenaCupon_in || 'Ã' ||
                  COD_CAMP_CUPON || 'Ã' ||
                  TIP_CUPON || 'Ã' ||
                  VALOR_CUPON || 'Ã' ||
                  NVL(MONT_MIN_USO,0) || 'Ã' ||
                  NVL(UNID_MIN_USO,0) || 'Ã' ||
                  NVL(UNID_MAX_PROD,0) || 'Ã' ||
                  NVL(MONTO_MAX_DESCT,0) || 'Ã' ||
                  'N' || 'Ã' ||
                  LPAD(PRIORIDAD,8,'0')||LPAD(RANKING,8,'0')||TIP_CUPON||TO_CHAR(100000-VALOR_CUPON,'00000.000')
           FROM VTA_CAMPANA_CUPON
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
           AND   COD_CAMP_CUPON = v_cCodigoCamp;

    ELSE
        RAISE_APPLICATION_ERROR(-20009,'Campana no valido.');
    END IF;




    RETURN curLista;

  EXCEPTION
    WHEN v_eControlBarra THEN
      RAISE_APPLICATION_ERROR(-20002,'Es codigo barras');
    WHEN v_eControlCupon THEN
      RAISE_APPLICATION_ERROR(-20001,'Cupon no valido');
  END;

/* ********************************************************************************** */
  FUNCTION CUP_F_CHAR_VALIDA_EAN13(vCodigoCupon_in IN VARCHAR2)
  RETURN CHAR
  IS
    cCodEan13 CHAR(13);
    vDig1 NUMBER(1);
    vDig2 NUMBER(1);
    vDig3 NUMBER(1);
    vDig4 NUMBER(1);
    vDig5 NUMBER(1);
    vDig6 NUMBER(1);
    vDig7 NUMBER(1);
    vDig8 NUMBER(1);
    vDig9 NUMBER(1);
    vDig10 NUMBER(1);
    vDig11 NUMBER(1);
    vDig12 NUMBER(1);
    vDig13 NUMBER(1);

    vSumImp NUMBER;
    vSumPar NUMBER;
    vAux NUMBER;
    vAux2 NUMBER;

    vCodigo_in  varchar2(3000);

  BEGIN

    vCodigo_in  := trim(SUBSTR(vCodigoCupon_in,1,12));

    vDig1 := TO_NUMBER(SUBSTR(vCodigo_in,1,1));
    vDig2 := TO_NUMBER(SUBSTR(vCodigo_in,2,1));
    vDig3 := TO_NUMBER(SUBSTR(vCodigo_in,3,1));
    vDig4 := TO_NUMBER(SUBSTR(vCodigo_in,4,1));
    vDig5 := TO_NUMBER(SUBSTR(vCodigo_in,5,1));
    vDig6 := TO_NUMBER(SUBSTR(vCodigo_in,6,1));
    vDig7 := TO_NUMBER(SUBSTR(vCodigo_in,7,1));
    vDig8 := TO_NUMBER(SUBSTR(vCodigo_in,8,1));
    vDig9 := TO_NUMBER(SUBSTR(vCodigo_in,9,1));
    vDig10 := TO_NUMBER(SUBSTR(vCodigo_in,10,1));
    vDig11 := TO_NUMBER(SUBSTR(vCodigo_in,11,1));
    vDig12 := TO_NUMBER(SUBSTR(vCodigo_in,12,1));

    vSumImp := (vDig12+vDig10+vDig8+vDig6+vDig4+vDig2)*3;
    vSumPar := vDig11+vDig9+vDig7+vDig5+vDig3+vDig1;

    vAux := vSumImp+vSumPar;
    vAux2 := MOD(vAux,10);
    --RAISE_APPLICATION_ERROR(-20005,vCodigo_in||'*'||vAux||'/'||vAux2);
    IF vAux2 = 0 THEN
      vDig13 := 0;
    ELSE
      vDig13 := 10 - vAux2;
    END IF;

    cCodEan13 := vCodigo_in||vDig13;


    DBMS_OUTPUT.put_line('CUPONE'||cCodEan13);

    if cCodEan13 = vCodigoCupon_in then
       return 'S';
    else
       return 'N';
    end if;

  END;

/* ********************************************************************************** */
  FUNCTION CUP_F_CHAR_VALIDA_CUPON_FID(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cCodCampCupon_in IN CHAR,
                                       cDniCliente     IN CHAR)
  RETURN CHAR
  IS
    nNumDia  VARCHAR(2);
    cSexo    char(1);
    dFecNaci date;
    cValido  char(1) :='N';
    cIndFid  char(1) :='N';
  BEGIN

    --primero verificamos si la campania es de fidelizacion--
    select c.ind_fid into cIndFid
    from vta_campana_cupon c
    where c.cod_grupo_cia=cCodGrupoCia_in
      and c.cod_camp_cupon = cCodCampCupon_in;
    --verificar si es cupon de fidelizacion
    IF cIndFid = 'S' THEN --si es campana de tipo fidelizacion

       nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);--obtenemos el numero de dia de la semana
---
    BEGIN
       --OBTENIENDO EL SEXO Y LA FECHA DE NACIMIENTO DEL CLIENTE
       SELECT CL.SEXO_CLI, trunc(CL.FEC_NAC_CLI) INTO cSexo, dFecNaci
       FROM PBL_CLIENTE CL
       WHERE CL.DNI_CLI = cDniCliente;

       dbms_output.put_line('datos del cliente : sexo:'||cSexo||', fecha_nac:'||dFecNaci);

       SELECT 'S' into cValido ---VERIFICANDO DE QUE CUMPLE CON LAS CONDICION DE EDAD Y SEXO DEL CLIENTE
       FROM VTA_CAMPANA_CUPON C
       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_CAMP_CUPON = cCodCampCupon_in
          AND C.ESTADO = 'A'
          AND C.IND_FID = 'S'
          AND C.TIP_CAMPANA = 'C'
          AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND C.FECH_FIN_USO
          -- agregado el filtro de los campos de SEXO y EDAD
          AND ( C.TIPO_SEXO_U IS NULL OR C.TIPO_SEXO_U = cSexo)
          AND ( C.FEC_NAC_INICIO_U IS NULL OR C.FEC_NAC_INICIO_U <= dFecNaci )
          AND ( C.FEC_NAC_FIN_U IS NULL OR C.FEC_NAC_FIN_U >= dFecNaci )
          --fin de filtro de los campos de Sexo y Edad
          AND C.COD_CAMP_CUPON IN
             (/*SELECT *
              FROM (SELECT *
                    FROM (SELECT COD_CAMP_CUPON
                          FROM VTA_CAMPANA_CUPON
                          MINUS
                          SELECT CL.COD_CAMP_CUPON
                          FROM VTA_CAMP_X_LOCAL CL)
                    UNION
                    SELECT COD_CAMP_CUPON
                    FROM VTA_CAMP_X_LOCAL
                    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                    AND COD_LOCAL = cCodLocal_in
                    AND ESTADO = 'A')*/

                    --JCORTEZ 19.10.09 cambio de logica
                    SELECT *
                      FROM   (
                             SELECT X.COD_CAMP_CUPON
                             FROM VTA_CAMPANA_CUPON X
                             WHERE X.COD_GRUPO_CIA='001'
                             AND X.TIP_CAMPANA='C'
                             AND X.ESTADO='A'
                             AND X.IND_CADENA='S'
                             UNION
                             SELECT Y.COD_CAMP_CUPON
                             FROM VTA_CAMPANA_CUPON Y
                             WHERE Y.COD_GRUPO_CIA='001'
                             AND Y.TIP_CAMPANA='C'
                             AND Y.ESTADO='A'
                             AND Y.IND_CADENA='N'
                             AND Y.COD_CAMP_CUPON IN (SELECT COD_CAMP_CUPON
                                                      FROM   VTA_CAMP_X_LOCAL Z
                                                      WHERE  Z.COD_GRUPO_CIA =cCodGrupoCia_in
                                                      AND    Z.COD_LOCAL = cCodLocal_in
                                                      AND    Z.ESTADO = 'A')

                             )
                                               )
          AND C.COD_CAMP_CUPON IN
              (SELECT *
               FROM (SELECT *
                     FROM (SELECT COD_CAMP_CUPON
                           FROM VTA_CAMPANA_CUPON
                           MINUS
                           SELECT H.COD_CAMP_CUPON FROM VTA_CAMP_HORA H)
                     UNION
                     SELECT H.COD_CAMP_CUPON
                     FROM VTA_CAMP_HORA H
                     WHERE TRIM(TO_CHAR(SYSDATE, 'HH24')) BETWEEN
                                 H.HORA_INICIO AND H.HORA_FIN))
          AND DECODE(C.DIA_SEMANA,
                     NULL,
                     'S',
                      DECODE(C.DIA_SEMANA,
                             REGEXP_REPLACE(C.DIA_SEMANA, nNumDia, 'S'),
                             'N',
                             'S')) = 'S'

         --QUITANDO LAS CAMPAÑAS LIMITADAS POR CANTIDAS DE USOS POR CLIENTE A LAS CAMPAÑAS AUTOMATICAS
         --JCALLO 13/02/2009
         AND C.COD_CAMP_CUPON NOT IN ( SELECT COD_CAMP_CUPON
                                       FROM CL_CLI_CAMP
                                       WHERE DNI_CLI       = cDniCliente
                                       AND   MAX_USOS      = NRO_USOS )
         ;
      EXCEPTION
      WHEN no_data_found THEN
         cValido := 'N';
      END;
---
    ELSE--si NO es campana de fidelizacion
       cValido := 'S';
    END IF;

    RETURN cValido;

 end;

  --Descripcion: Activa cupones que tenia el pedido
  --Fecha       Usuario		Comentario
  --02/12/2008  DUBILLUZ     Creacion
  PROCEDURE CUP_P_ACTIVA_CUPONES(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPedVta_in   IN CHAR,
                                cIdUsu_in IN CHAR)
  IS
  BEGIN
          update vta_cupon ca
          set    (ca.estado,ca.FECHA_ACTIVACION,ca.usu_activacion ) = ( select 'A',sysdate,cIdUsu_in
                                                                        from   vta_camp_pedido_cupon cp
                                                                        where  cp.cod_grupo_cia = cCodGrupoCia_in
                                                                        and    cp.cod_local = cCodLocal_in
                                                                        and    cp.num_ped_vta = cNumPedVta_in
                                                                        and    cp.estado = 'S'
                                                                        and    cp.ind_uso = 'S'
                                                                        and    cp.cod_grupo_cia = ca.cod_grupo_cia
                                                                        and    cp.cod_local = ca.cod_local
                                                                        and    cp.cod_cupon = ca.cod_cupon
                                                                        )
          where  exists (
                              select 1
                              from   vta_camp_pedido_cupon cp1
                              where  cp1.cod_grupo_cia = cCodGrupoCia_in
                              and    cp1.cod_local = cCodLocal_in
                              and    cp1.num_ped_vta = cNumPedVta_in
                              and    cp1.estado = 'S'
                              and    cp1.ind_uso = 'S'
                              and    cp1.cod_grupo_cia = ca.cod_grupo_cia
                              and    cp1.cod_local = ca.cod_local
                              and    cp1.cod_cupon = ca.cod_cupon
                           );
  END;
/**************************************************************************/
 FUNCTION CUP_F_CUR_CUP_USADOS(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cNumPedVta_in   IN CHAR)
   RETURN FarmaCursor
   IS
    cur FarmaCursor;

  BEGIN
        OPEN cur FOR
            select cp1.cod_cupon
            from   vta_camp_pedido_cupon cp1
            where  cp1.cod_grupo_cia = cCodGrupoCia_in
            and    cp1.cod_local = cCodLocal_in
            and    cp1.num_ped_vta = cNumPedVta_in
            and    cp1.estado = 'S'
            and    cp1.ind_uso = 'S';

    return cur;

  END ;
/*-------------------------------------------------------------------------------------------------------------------------
GOAL : Validacion de Cupon
History : 22-JUL-2014    TCT  Modifica Lectura de Codigo de Camp Cupon
-------------------------------------------------------------------------------------------------------------------------*/
 FUNCTION CUP_F_CHAR_VALIDA_CUPON(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cCadenaCupon_in IN CHAR,
                                    cIndMultiUso_in IN CHAR default 'N',
                                    cDniCliente     IN CHAR DEFAULT NULL)
  RETURN CHAR
  IS
    c_resultado CHAR(1):='N';

    v_cCodigoCamp VTA_CAMPANA_CUPON.COD_CAMP_CUPON%TYPE;
    v_cCodLocalEmi PBL_LOCAL.COD_LOCAL%TYPE;
    v_cSecuencial VARCHAR2(10);

    v_eControlCupon EXCEPTION;

    v_nCantCamp INTEGER;
    v_nCantLocal INTEGER;
    v_nCantLocalA INTEGER;
    v_nCantLocalB INTEGER;
    v_nCantLocalC INTEGER;
    v_nCantCupon INTEGER;
    v_nCantCuponA INTEGER;
    v_nCantCuponB INTEGER;
    v_nCampVal INTEGER;

    v_nCantProd INTEGER;
    v_eControlBarra EXCEPTION;

    v_cEstCupon VTA_CUPON.ESTADO%TYPE;
    -- jcallo 09.10.2008 para validacion de sexo y edad de cliente --
    cSexo    char(1);
    dFecNaci date;
    cIndFid    char(1);
    cValido    char(1) := 'N';

    -- fin jcallo --
  BEGIN

    --Verifica si es un producto
    SELECT COUNT(*) INTO v_nCantProd
    FROM LGT_COD_BARRA B
    WHERE B.COD_BARRA = cCadenaCupon_in;
    IF v_nCantProd > 0 THEN
      RAISE v_eControlBarra;
    END IF;

    IF CUP_F_CHAR_VALIDA_EAN13(cCadenaCupon_in)= 'N' THEN
       c_resultado:='N';
       RAISE_APPLICATION_ERROR(-20018,'Cupón no cumple el correcto formato correcto.');
    END IF;

    --JCORTEZ 15/08/2008 se consulta el cupon en caso de multiuso
    --Se inicializa las variables segun el tipo de IndMultiuso
    IF(cIndMultiUso_in='N')THEN
    
     --- < 21 - JUL - 14  TCT Nuew Mode codigo camp >
      --v_cCodigoCamp := SUBSTR(cCadenaCupon_in,1,5);
      
      v_cCodigoCamp := ptoventa_vta.fn_get_cod_campa(ccodgrupocia_in => cCodGrupoCia_in,
                                           ccodcupon_in => cCadenaCupon_in);
     --- < /21 - JUL - 14  TCT New Mode codigo camp >                                      
                                           
      v_cCodLocalEmi := SUBSTR(cCadenaCupon_in,6,3);
      v_cSecuencial := SUBSTR(cCadenaCupon_in,9,5);

    ELSIF (cIndMultiUso_in='S')THEN
     begin
        SELECT X.COD_CAMPANA,X.COD_LOCAL,X.SEC_CUPON
        INTO   v_cCodigoCamp,v_cCodLocalEmi,v_cSecuencial
        FROM   VTA_CUPON X
        WHERE  X.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    X.COD_CUPON     = TRIM(cCadenaCupon_in);
        exception
        when no_data_found then
        c_resultado:='N';
        RAISE_APPLICATION_ERROR(-20010,'Cupon no esta vigente.');

        end;



    END IF;

    --JCALLO 09.10.2008 --

    if v_cCodigoCamp = '12719' then
               v_cCodigoCamp :='12723';
     end if;


    IF cDniCliente IS NOT NULL AND LENGTH(TRIM(cDniCliente))>0 THEN
       --SI TIENE DNI QUIERE DECIR QUE ES UN CLIENTE FIDELIZADO
       --SE DEBE VALIDAR DE QUE EL CUPON SEA VALIDO YA SEA CUPO DE FID O NO.
       cValido := CUP_F_CHAR_VALIDA_CUPON_FID(cCodGrupoCia_in, cCodLocal_in, v_cCodigoCamp ,cDniCliente);
    ELSE -- SI NO TIENE DNI ES UN CLIENTE NO FIDELIZADO POR LO CUAL SOLO DEBE PERMITIR USAR CUP0NES DE NO FIDELIZACION
       -- indicador de fidelizacion
       select c.ind_fid into cIndFid
       from vta_campana_cupon c
       where c.cod_grupo_cia=cCodGrupoCia_in
         and c.cod_camp_cupon = v_cCodigoCamp;

    -- 2009-04-22 Se cambia para que sólo los clientes fidelizados puedan usar estos cupones
       IF cIndFid = 'S' THEN
          cValido := 'N';
       ELSE
          cValido := 'S';
       END IF;

        --siempre dejara uzsar el cupon sea o no sea de fidelizacion
--        cValido := 'S';


    END IF;

    -- SI PASO LA VALIDACION DE FIDELIZACION CONTINUA CON TODA LA VALIDACION NORMAL DE LA CAMPAÑA
    IF( cValido = 'S') THEN
      --1. Verifica la validez de uso y estado de la campaña
      SELECT COUNT(*) INTO v_nCantCamp
      FROM VTA_CAMPANA_CUPON
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_CAMP_CUPON = v_cCodigoCamp
            AND ESTADO = 'A'
            AND TRUNC(SYSDATE) BETWEEN FECH_INICIO_USO AND FECH_FIN_USO;

      IF v_nCantCamp <> 1 THEN
         dbms_output.put_line('error 01');
         c_resultado:='N';
         RAISE_APPLICATION_ERROR(-20003,'Campana '||v_cCodigoCamp||' no es valida.');
      END IF;

      --2. Verifica que la campana tenga locales asignados.
      SELECT COUNT(*)
      INTO   v_nCantLocal
      FROM   VTA_CAMP_X_LOCAL
      WHERE  COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    COD_CAMP_CUPON = v_cCodigoCamp;

      --Si la campaña esta asignada a varios locales

      IF v_nCantLocal > 0 THEN
        --Verifica que el local de uso permita la campana.
        SELECT COUNT(*) INTO v_nCantLocalA
        FROM VTA_CAMP_X_LOCAL
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_CAMP_CUPON = v_cCodigoCamp
              AND COD_LOCAL = cCodLocal_in;

        IF v_nCantLocalA <> 1 THEN
          dbms_output.put_line('error 2');
          c_resultado:='N';
          RAISE_APPLICATION_ERROR(-20004,'Local no valido para el uso del cupon.');
        END IF;

        --Verifica que el local de emision permita la campana
        SELECT COUNT(*) INTO v_nCantLocalB
        FROM VTA_CAMP_X_LOCAL
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_CAMP_CUPON = v_cCodigoCamp
              AND COD_LOCAL = v_cCodLocalEmi;

        IF v_nCantLocalB <> 1 THEN
          dbms_output.put_line('error 3');
          c_resultado:='N';
          RAISE_APPLICATION_ERROR(-20005,'Local de emision no valido.');
        END IF;

      ELSIF (cIndMultiUso_in='N')THEN
        --Esta validacion no aplica a Cupones MultiUso porque
        --los cupones multiuso no tendran un codigo de local de venta
        --Verifica que el local de emision sea un local de venta.
        SELECT COUNT(*)
        INTO   v_nCantLocalC
        FROM   PBL_LOCAL
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL     = v_cCodLocalEmi
        AND    TIP_LOCAL     = 'V';

        ---jquispe , valida que si el cupon fue emitido tambien en casa matriz
        -- < 16-JUN-14  TCT ya no sera necesario Validar el Local de Emision de la Campaña >
       /* IF v_nCantLocalC <> 1 and v_cCodLocalEmi<>'000' THEN
          dbms_output.put_line('error 4');
          c_resultado:='N';
          RAISE_APPLICATION_ERROR(-20006,'Local de emision no es local de venta.');
        END IF;*/

      END IF;

      --FIN VALIDACION 2.

      --3. Verifica que exista el cupon en el local
      SELECT COUNT(*)
      INTO   v_nCantCuponA
      FROM   VTA_CUPON
      WHERE  COD_GRUPO_CIA   = cCodGrupoCia_in
      AND    (COD_LOCAL       = cCodLocal_in OR cCodLocal_in <> '010' OR cCodLocal_in = '000')
      AND    TRIM(COD_CUPON) = cCadenaCupon_in;

      --DBMS_OUTPUT.PUT_LINE('Existe cupon: '||v_nCantCuponA);

      --4. Verifica que el cupon este vigente
      SELECT COUNT(*)
      INTO   v_nCantCuponB
      FROM   VTA_CUPON
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      --AND    COD_LOCAL     = cCodLocal_in
      AND    (COD_LOCAL       = cCodLocal_in OR cCodLocal_in <> '010' OR cCodLocal_in = '000')
      AND    COD_CUPON     = cCadenaCupon_in
      AND    FEC_INI IS NOT NULL
      AND    FEC_FIN IS NOT NULL
      AND    TRUNC(SYSDATE) BETWEEN FEC_INI AND FEC_FIN;

      -- Si el cupon existe en el local
      IF v_nCantCuponA > 0 THEN
        --Verifica que el cupon este activo porque se encuentra en el local
        SELECT COUNT(*)
        INTO   v_nCantCupon
        FROM   VTA_CUPON
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        --AND    COD_LOCAL     = cCodLocal_in
        AND    (COD_LOCAL       = cCodLocal_in OR cCodLocal_in <> '010' OR cCodLocal_in = '000')
        AND    TRIM(COD_CUPON) = cCadenaCupon_in
        AND    ESTADO = 'A';

        --DBMS_OUTPUT.PUT_LINE('Cupon no valido: '||v_nCantCupon);

        --El cupon no se encuentra activo en el local
        IF v_nCantCupon <> 1 THEN
          SELECT ESTADO
          INTO   v_cEstCupon
          FROM   VTA_CUPON
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          --AND    COD_LOCAL     = cCodLocal_in
          AND    (COD_LOCAL       = cCodLocal_in OR cCodLocal_in <> '010' OR cCodLocal_in = '000')
          AND    TRIM(COD_CUPON) = cCadenaCupon_in;

          --Se valida el estado del cupon que se encuentra en el local
          IF v_cEstCupon = 'U' THEN
            dbms_output.put_line('error 5');
            c_resultado:='N';
            RAISE_APPLICATION_ERROR(-20007,'Cupon ya fue usado.');
          ELSE
            dbms_output.put_line('error 6');
            c_resultado:='N';
            RAISE_APPLICATION_ERROR(-20008,'Cupon esta anulado.');
          END IF;

        END IF;

        -- Si el cupon no esta vigente
        IF v_nCantCuponB = 0 THEN
          dbms_output.put_line('error 7');
          c_resultado:='N';
          RAISE_APPLICATION_ERROR(-20010,'Cupon no esta vigente.');
        END IF;

      ELSE
          IF cCodLocal_in = v_cCodLocalEmi THEN
          dbms_output.put_line('error 8');
          c_resultado:='N';
          RAISE_APPLICATION_ERROR(-20010,'Cupon no esta vigente.');
          END IF;

      END IF;

      --6. Verifica que la campana tenga los valores indicados.
      SELECT COUNT(*)
      INTO   v_nCampVal
      FROM   VTA_CAMPANA_CUPON
      WHERE  COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    COD_CAMP_CUPON = v_cCodigoCamp
      AND    TIP_CAMPANA    = 'C'
      AND    TIP_CUPON   IS NOT NULL
      AND    VALOR_CUPON IS NOT NULL;

      -- La campaña se creo bien con los datos exactos y necesarios
      IF v_nCampVal <> 1 THEN
        dbms_output.put_line('error 9');
        c_resultado:='N';
        RAISE_APPLICATION_ERROR(-20009,'Campana no valido.');
      END IF;

      -- Si el cupon existe en el local O NO se envia la informacion
      -- Luego si hay linea validara en Matriz caso contrario
      -- Segun los acordado se permitira usar el cupon

      c_resultado:='S';

    ELSE
        c_resultado:='N';
        RAISE_APPLICATION_ERROR(-20009,'Campana no valido.');
    END IF;


    RETURN c_resultado;

  EXCEPTION
    WHEN v_eControlBarra THEN
      c_resultado:='N';
      RAISE_APPLICATION_ERROR(-20002,'Es codigo barras');
      RETURN c_resultado;
    WHEN v_eControlCupon THEN
      c_resultado:='N';
      RAISE_APPLICATION_ERROR(-20001,'Cupon no valido');
      RETURN c_resultado;
    /*WHEN OTHERS THEN
      c_resultado:='N';
      RAISE_APPLICATION_ERROR(-20777,'ERROR AL VALIDAR CUPON'||SQLERRM);
      RETURN c_resultado;*/

  END;

  /***************************************************************************************************/
  PROCEDURE CAJ_P_GENERA_CUPON_REGALO(pCodGrupoCia_in         IN CHAR,
                                      cCodLocal_in            IN CHAR,
                								 	  	cIdUsu_in               IN CHAR,
                                      cNumDocIdent            IN CHAR)
  IS
	v_rVtaPedidoVtaCab VTA_PEDIDO_VTA_CAB%ROWTYPE;
  v_SecEan  NUMBER;
  c_SecEan  CHAR(5);
  v_CodCupon   VARCHAR2(20);
  n_cant    NUMBER(8);
  n_CantD    NUMBER(8);
  n_canteExist NUMBER;
  i number:=0;
  NumDiasIni NUMBER:=0;
  NumDiasVig NUMBER:=0;
  v_IP   VARCHAR2(30);

  FEC_INI_AUX DATE;
  FEC_FIN_AUX DATE;
  FEC_INI_AUX2 DATE;
  FEC_FIN_AUX2 DATE;

  cod_cupon VARCHAR2(20);
  codCampCupon CHAR(5);

  CURSOR CURCAMP IS
  SELECT ROWNUM
  FROM LGT_PROD  X
  WHERE ROWNUM<=(SELECT TO_NUMBER(A.LLAVE_TAB_GRAL) FROM PBL_TAB_GRAL A WHERE A.ID_TAB_GRAL=287);

  ROWCURCAMP CURCAMP%ROWTYPE;

       BEGIN

         SELECT TO_NUMBER(TRIM(A.LLAVE_TAB_GRAL)) INTO n_cant
          FROM PBL_TAB_GRAL A WHERE A.ID_TAB_GRAL=287;

            SELECT  TRIM(X.LLAVE_TAB_GRAL) INTO codCampCupon
            FROM  PBL_TAB_GRAL X WHERE X.ID_TAB_GRAL=288;

            SELECT Y.FECH_INICIO_USO,Y.FECH_FIN_USO,NVL(Y.NUM_DIAS_INI,0),NUM_DIAS_VIG
            INTO FEC_INI_AUX,FEC_FIN_AUX,NumDiasIni,NumDiasVig
            FROM VTA_CAMPANA_CUPON Y
            WHERE Y.COD_CAMP_CUPON=TRIM(codCampCupon);

            SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_IP
            FROM DUAL;

            --Se valida generacion  una sola vez al dia por QF
          SELECT COUNT(*) INTO n_CantD
          FROM VTA_CUPON X
          WHERE X.COD_GRUPO_CIA=pCodGrupoCia_in
          AND X.COD_LOCAL=cCodLocal_in
          AND TRUNC(X.FEC_CREA_CUP_CAB)=TRUNC(SYSDATE)
          AND X.COD_CAMPANA=TRIM(codCampCupon)
          AND X.NUM_DOC_IDENT=cNumDocIdent;

          IF(n_CantD>0) THEN --solo si no han creado cupones regalo en el dia
            n_cant:=0;
          END IF;

          dbms_output.put_line('CANTIDAD CUPONES A IMPRIMIR -->'||n_cant);

          FOR i IN 1..n_cant
          LOOP
           v_SecEan := PTOVENTA_CAJ.OBTENER_NUMERACION(pCodGrupoCia_in, cCodLocal_in,codCampCupon);
           IF v_SecEan <= 9999 THEN

             --RAISE_APPLICATION_ERROR(-20022,'Se ha superado el límite de cupones a imprimir.');
             c_SecEan := Farma_Utility.COMPLETAR_CON_SIMBOLO(TO_CHAR(v_SecEan),4,'0','I');
             v_CodCupon:=TRIM(codCampCupon)||TRIM(cCodLocal_in||TRIM(c_SecEan));

             dbms_output.put_line('CUPO GENERADO--> '||v_CodCupon);

             v_CodCupon := PTOVENTA_IMP_CUPON.GENERA_EAN13(v_CodCupon);

             --Se asume que la vigenci del cupon debe estar entre las fechas de uso de la campaña
             IF(NumDiasIni=0)THEN
                 FEC_INI_AUX2:=TRUNC(SYSDATE);
                 IF(FEC_INI_AUX2<FEC_INI_AUX)THEN
                   FEC_INI_AUX2:=FEC_INI_AUX;
                 END IF;
             ELSIF (NumDiasIni>0) THEN
                 FEC_INI_AUX2:=TRUNC(SYSDATE+NumDiasIni);
                 IF(FEC_INI_AUX2<FEC_INI_AUX)THEN
                   FEC_INI_AUX2:=FEC_INI_AUX;
                 END IF;
             END IF;

             IF(NumDiasIni=0)THEN
                 FEC_FIN_AUX2:=FEC_FIN_AUX;
             ELSIF (NumDiasIni>0)THEN
                 FEC_FIN_AUX2:= TRUNC(FEC_INI_AUX + NumDiasVig);
                 IF(FEC_FIN_AUX2>FEC_FIN_AUX)THEN
                   FEC_FIN_AUX2:=FEC_FIN_AUX;
                 END IF;
             END IF;

             INSERT INTO VTA_CUPON(COD_GRUPO_CIA
                                  ,COD_LOCAL
                                  ,COD_CUPON
                                  ,ESTADO
                                  ,USU_CREA_CUP_CAB
                                  ,USU_MOD_CUP_CAB
                                  ,FEC_MOD_CUP_CAB
                                  ,COD_CAMPANA
                                  ,SEC_CUPON
                                  ,FEC_INI
                                  ,FEC_FIN
                                  ,IP,NUM_DOC_IDENT)--ultimos campos agredos
             VALUES (pCodGrupoCia_in,
                    cCodLocal_in,
                    v_CodCupon,
                    C_ESTADO_ACTIVO,
                    cIdUsu_in,
                    NULL,NULL,codCampCupon,TRIM(c_SecEan),FEC_INI_AUX2,FEC_FIN_AUX2,v_IP,cNumDocIdent);

              --Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(pCodGrupoCia_in,cCodLocal_in,C_C_COD_EAN_CUPON,cIdUsu_in);

              UPDATE VTA_NUMERA_CUPON X
              SET X.SEC_CUPON=v_SecEan
              WHERE X.COD_GRUPO_CIA=pCodGrupoCia_in
              AND X.COD_LOCAL=cCodLocal_in
              AND X.COD_CAMP_CUPON=codCampCupon;

            END IF;
          END
          LOOP;
      /*EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK;  */
  END;


  /**************************************************************************/
 FUNCTION CUP_F_CUR_CUP_REGALOS(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in     IN CHAR,
                                 cdni_in          IN CHAR)
   RETURN FarmaCursor
   IS
    cur FarmaCursor;
    v_ip  VARCHAR2(20);

  BEGIN
   SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip
      FROM DUAL;

        OPEN cur FOR
           SELECT X.COD_CUPON,X.COD_CAMPANA
           FROM VTA_CUPON X
           WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
           AND X.COD_LOCAL=cCodLocal_in
           AND TRUNC(X.FEC_CREA_CUP_CAB)=TRUNC(SYSDATE)
           --AND X.ESTADO=C_ESTADO_ACTIVO
           --AND X.IP=TRIM(v_ip)
           AND X.NUM_DOC_IDENT=cdni_in;
    RETURN cur;

       EXCEPTION
        WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20099,'NO EXISTEN CUPONES POR IMPRIMIR.');
  END ;


  /******************************************************************/
  FUNCTION CUP_F_VERI_EXIST_CUP(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cDni_in          IN CHAR)
  RETURN CHAR
  IS
  vresultado  CHAR(1);
  vcontador   NUMBER;

    BEGIN
      SELECT COUNT(*) INTO vcontador
      FROM VTA_CUPON A
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
      AND TRUNC(A.FEC_CREA_CUP_CAB)=TRUNC(SYSDATE)
      AND A.COD_LOCAL=cCodLocal_in
      AND A.NUM_DOC_IDENT=cDni_in;

     BEGIN
       IF vcontador > 0 THEN
         vresultado := 'S';
       ELSE
         vresultado := 'N';
       END IF;
     END;
    RETURN vresultado;

  END;

end;

/
