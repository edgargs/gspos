CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_MATRIZ_CA_CLI" AS

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

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_MATRIZ_CA_CLI" AS

  /************************************************************* */

  FUNCTION CA_F_CUR_DATOS_DNI(cDNI_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCamp FarmaCursor;
    vDNI varchar2(20);
    nCant number;
  BEGIN

    OPEN curCamp FOR

        SELECT
          A.DNI_CLI                                    || 'Ã' ||
          nvl(A.NOM_CLI,'N')                           || 'Ã' ||
          nvl(A.APE_PAT_CLI,'N')                       || 'Ã' ||
          nvl(A.APE_MAT_CLI,'N')                       || 'Ã' ||
          nvl(A.SEXO_CLI,'N')                          || 'Ã' ||
          nvl(TO_CHAR(A.FEC_NAC_CLI,'dd/MM/yyyy'),'N') || 'Ã' ||
          nvl(A.DIR_CLI,'N')                           || 'Ã' ||
          nvl(''||A.FONO_CLI,'N')                      || 'Ã' ||
          nvl(''||A.CELL_CLI,'N')                      || 'Ã' ||
          nvl(''||A.EMAIL,'N')
        FROM PBL_CLIENTE A
        WHERE A.DNI_CLI = cDNI_in;

    RETURN curCamp;

  END CA_F_CUR_DATOS_DNI;

  --numero de las tarjetas asociada a cada cliente.
  -- jcallo    17.12.2008
  FUNCTION CA_F_CUR_TARJETAS_CLI( cDniCliente_in IN CHAR )
  RETURN FarmaCursor  IS
         curFarma FarmaCursor;
  BEGIN

    OPEN curFarma FOR
         SELECT FT.COD_TARJETA
         FROM   FID_TARJETA FT
         WHERE  FT.DNI_CLI = cDniCliente_in;
    RETURN curFarma;
  END CA_F_CUR_TARJETAS_CLI;

  -- obtiene la cantidad restante de compras de los productos de la campañas para obtener regalo
  -- y/o promoion
  -- jcallo    17.12.2008
  FUNCTION CA_F_CUR_CAMP_MATRIZ_REST(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cDni_in         IN CHAR,
                                      cNumPed_in      IN CHAR)
  RETURN  FarmaCursor IS

    curFarma  FarmaCursor;

  BEGIN

    OPEN curFarma FOR
    SELECT CH.COD_CAMP_CUPON            || 'Ã' ||--0
           CH.VAL_FRAC_MIN              || 'Ã' ||--1
           SUM(CH.CANT_RESTANTE)        || 'Ã' ||--2
           CAMP.CA_CANT_CANJE           || 'Ã' ||--3
           CAMP.CA_VAL_FRAC_CANJE       || 'Ã' ||--4
           CAMP.CA_MENSAJE_CAMP         || 'Ã' ||--5
           CAMP.CA_UNIDAD_CANJ          || 'Ã' ||--6
           '<b>'||CAMP.CA_MENSAJE_CAMP||'.</b><br><b>Vigencia:</b> '||TO_CHAR(CAMP.FECH_INICIO,'DD-MON-YYYY')||' al '||TO_CHAR(CAMP.FECH_FIN,'DD-MON-YYYY')||
           '.<br><b>Restricciones:&nbsp; </b>&nbsp;M&aacute;ximo '||TRIM(TO_CHAR(NVL(CAMP.CA_MAX_CANJE,0),'999999999'))||DECODE(CAMP.CA_MAX_CANJE,1,' premio',' premios')||
           DECODE(NVL(CAMP.CA_NUM_CANJE_X_PER,0),1, ' por ',' por cada ')||
           TRIM( DECODE( NVL(CAMP.CA_NUM_CANJE_X_PER,0) ,
                           1, ' ',
                           TO_CHAR(NVL(CAMP.CA_NUM_CANJE_X_PER,0),'999999999')
                       )
               )||' '||DECODE(CAMP.CA_NUM_CANJE_X_PER,1,DECODE(CAMP.CA_PER_MAX_CANJE,'S','semana.','M','mes.'),DECODE(CAMP.CA_PER_MAX_CANJE,'S','semanas.','M','meses.'))
                                        || 'Ã' ||--7
             trim(to_char( ( ( (CAMP.Ca_Cant_Canje/CAMP.Ca_Val_Frac_Canje)*CH.Val_Frac_Min )/CH.Val_Frac_Min )*CAMP.Ca_Val_Frac_Canje,'999999999') )
                                        || 'Ã' ||--8
             trim(to_char( ( SUM(CH.CANT_RESTANTE)/CH.Val_Frac_Min )*CAMP.Ca_Val_Frac_Canje,'999999999') )
                                        || 'Ã' ||--9
             trim(to_char( ( ( (CAMP.Ca_Cant_Canje/CAMP.Ca_Val_Frac_Canje)*CH.Val_Frac_Min - SUM(CH.CANT_RESTANTE) )/CH.Val_Frac_Min )*CAMP.Ca_Val_Frac_Canje,'999999999'))
                                        --10



    FROM CA_CLI_CAMP CCC,  VTA_CAMPANA_CUPON CAMP, CA_HIS_CLI_PED CH
    WHERE CCC.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CCC.ESTADO        = 'A'
       AND CCC.DNI_CLI       = cDni_in
       AND CAMP.TIP_CAMPANA  = 'A'
       AND CAMP.ESTADO       = 'A'
       AND CH.ESTADO = 'A'
       AND CCC.DNI_CLI        = CH.DNI_CLI
       AND CCC.COD_GRUPO_CIA  = CH.COD_GRUPO_CIA
       AND CCC.COD_CAMP_CUPON = CH.COD_CAMP_CUPON


       AND CCC.COD_GRUPO_CIA  = CAMP.COD_GRUPO_CIA
       AND CCC.COD_CAMP_CUPON = CAMP.COD_CAMP_CUPON

       AND CCC.COD_CAMP_CUPON IN ( --PARA OBTENER SOLO LAS CAMPANIAS QUE SE HAYA ACUMULADO VENTAS O GANADO ALGUN PREMIO
            SELECT CH.COD_CAMP_CUPON
            FROM   CA_HIS_CLI_PED CH, VTA_CAMPANA_CUPON CAMP
            WHERE  CH.DNI_CLI = cDNI_in
            AND    CH.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    CH.NUM_PED_VTA   = cNumPed_in
            AND    CH.COD_LOCAL     = cCodLocal_in

            AND    CH.COD_GRUPO_CIA = CAMP.COD_GRUPO_CIA
            AND    CH.COD_CAMP_CUPON = CAMP.COD_CAMP_CUPON

            UNION

            SELECT CAMP.COD_CAMP_CUPON
            FROM   CA_CANJ_CLI_PED CA, VTA_CAMPANA_CUPON CAMP
            WHERE  CA.COD_GRUPO_CIA  = CAMP.COD_GRUPO_CIA
            AND    CA.COD_CAMP_CUPON = CAMP.COD_CAMP_CUPON
            AND    CA.DNI_CLI        = cDNI_in
            AND    CA.COD_GRUPO_CIA  = cCodGrupoCia_in
            AND    CA.NUM_PED_VTA    = cNumPed_in
            AND    CA.COD_LOCAL      = cCodLocal_in
       )

       AND CCC.COD_CAMP_CUPON IN (
                                      SELECT *
                                      FROM   (
                                              SELECT *
                                              FROM
                                                  (
                                                  SELECT COD_CAMP_CUPON
                                                  FROM   VTA_CAMPANA_CUPON
                                                  WHERE    TIP_CAMPANA='A'
                                                  MINUS
                                                  SELECT CL.COD_CAMP_CUPON
                                                  FROM   VTA_CAMP_X_LOCAL CL
                                                  )
                                              UNION
                                              SELECT COD_CAMP_CUPON
                                              FROM   VTA_CAMP_X_LOCAL
                                              WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                                              AND    COD_LOCAL = cCodLocal_in
                                              AND    ESTADO = 'A')
                                      )
       GROUP BY CH.COD_CAMP_CUPON           ,
           CH.VAL_FRAC_MIN              ,
           CAMP.CA_CANT_CANJE           ,
           CAMP.CA_VAL_FRAC_CANJE       ,
           CAMP.CA_MENSAJE_CAMP         ,
           CAMP.CA_UNIDAD_CANJ   ,
           CAMP.FECH_INICIO,
           CAMP.FECH_FIN,
           CAMP.CA_MAX_CANJE,
           CAMP.CA_NUM_CANJE_X_PER,
           CAMP.CA_PER_MAX_CANJE;

    RETURN curFarma;

  END;

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
                                 pIndEstado  IN CHAR)
   AS

   vCount NUMBER;
   vCantCampo1 number;
   vCantCampo2 number;
  BEGIN

    SELECT COUNT(1)
      INTO vCount
      FROM PBL_CLIENTE
     WHERE DNI_CLI = vDni_cli;
--       AND IND_ESTADO = 'A';

    IF trim(vDni_cli) is not NULL THEN

      --dbms_output.put_line('vCount ' || vCount);

      IF (vCount = 0) THEN

        INSERT INTO PBL_CLIENTE
          (DNI_CLI,
           NOM_CLI,
           APE_PAT_CLI,
           APE_MAT_CLI,
           SEXO_CLI,
           FEC_NAC_CLI,
           DIR_CLI,
           FONO_CLI,
           CELL_CLI,
           EMAIL,
           FEC_CREA_CLIENTE,
           USU_CREA_CLIENTE,
           FEC_MOD_CLIENTE,
           USU_MOD_CLIENTE,
           IND_ESTADO)
        VALUES
          (vDni_cli,
           DECODE(vNom_cli, 'N', NULL, null, null, vNom_cli),
           DECODE(vApat_cli, 'N', NULL, null, null, vApat_cli),
           DECODE(vAmat_cli, 'N', NULL, null, null, vAmat_cli),
           DECODE(vSexo_cli, 'N', NULL, null, null, vSexo_cli),
           DECODE(vFecNac_cli,
                  'N',
                  NULL,
                  null,
                  null,
                  TO_DATE(vFecNac_cli, 'DD/MM/YYYY')),
           DECODE(vDir_cli, 'N', NULL, null, null, vDir_cli),
           decode(vFono_cli,
                  'N',
                  NULL,
                  null,
                  null,
                  TO_NUMBER(vFono_cli, '9999999999')),
           decode(vCell_cli,
                  'N',
                  NULL,
                  null,
                  null,
                  TO_NUMBER(vCell_cli, '9999999999')),
           DECODE(vEmail_cli, 'N', NULL, null, null, vEmail_cli),
           SYSDATE,
           pUser,
           NULL,
           NULL,
           pIndEstado);
      ELSE

        --solo si se  falta campos.

        -- se obtiene el numero de campos ingresados por el cliente
        vCantCampo1 := CA_F_NUM_CAMPOS_CLIENTE(vDni_cli);

        SELECT COUNT(*) INTO vCantCampo2
        FROM FID_CAMPOS_FIDELIZACION
        WHERE IND_MOD = CC_MOD_CA_CLI;

        IF(vCantCampo2 >= vCantCampo1)THEN

        UPDATE PBL_CLIENTE L
           SET NOM_CLI = DECODE(vNom_cli, 'N', NULL, vNom_cli),
               APE_PAT_CLI = DECODE(vApat_cli, 'N', NULL, vApat_cli),
               APE_MAT_CLI = DECODE(vAmat_cli, 'N', NULL, vAmat_cli),
               SEXO_CLI = DECODE(vSexo_cli, 'N', NULL, vSexo_cli),
               FEC_NAC_CLI = DECODE(vFecNac_cli,'N',NULL,TO_DATE(vFecNac_cli, 'DD/MM/YYYY')),
               DIR_CLI = DECODE(vDir_cli, 'N', NULL, vDir_cli),
               FONO_CLI = decode(vFono_cli,'N',NULL,TO_NUMBER(vFono_cli, '9999999999')),
               CELL_CLI = decode(vCell_cli,'N',NULL,TO_NUMBER(vCell_cli, '9999999999')),
               EMAIL = DECODE(vEmail_cli, 'N', NULL, vEmail_cli),
               USU_MOD_CLIENTE = pUser,
               FEC_MOD_CLIENTE = SYSDATE
         WHERE L.DNI_CLI = vDni_cli;
        end if;
      END IF;


    END IF;

  EXCEPTION

    WHEN OTHERS THEN
         DBMS_OUTPUT.put_line('error en matriz: al insertar cliente');
  END;

  /***numero campos del cliente con datos**/
  FUNCTION CA_F_NUM_CAMPOS_CLIENTE(cDni   IN CHAR)
  RETURN number
  is
   cCant number;
  begin
      select decode(DNI_CLI, null, 0, 1) + decode(NOM_CLI, null, 0, 1) +
             decode(APE_PAT_CLI, null, 0, 1) + decode(APE_MAT_CLI, null, 0, 1) +
             decode(SEXO_CLI, null,0, 1) + decode(FEC_NAC_CLI, null, 0, 1)+
             decode(DIR_CLI, null, 0, 1) + decode(FONO_CLI, null, 0, 1) +
             decode(CELL_CLI, null, 0, 1) +decode(l.email, null, 0, 1)
      into cCant
      from pbl_cliente l
      where l.dni_cli = trim(cDni);
       --AND l.ind_estado = 'A';

       return cCant;
  end;

  /*****DE AQUI EN ADELANTE SON PROCEDIMIENTOS DE DUBILLUZ******/

/* ******************************************************************* */
  FUNCTION OBTIEN_NUM_DIA(cDate_in IN DATE)
    RETURN VARCHAR2
  IS
    v_nCant VARCHAR2(2);
  BEGIN
      --lunes = 1  & Domingo = 7
      SELECT DECODE(MOD(TRUNC(cDate_in)-TO_DATE('20080629','YYYYMMDD')+3500,7),0,7,MOD(TRUNC(cDate_in)-TO_DATE('20080629','YYYYMMDD')+3500,7))
      INTO   v_nCant
      FROM   DUAL;

	  RETURN v_nCant;
  END;
  /* ************************************************************************************ */
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
                                   )
  is
  BEGIN
                      INSERT INTO  CA_AUX_CANJE_MATRIZ
                      (
                      DNI_CLI,
                      COD_CAMP_CUPON,
                      COD_GRUPO_CIA,
                      COD_LOCAL_CANJ,
                      NUM_PED_CANJ,

                      COD_LOCAL_ORIGEN,
                      NUM_PED_ORIGEN,
                      SEC_PED_ORIGEN,
                      COD_PROD_ORIGEN,
                      CANT_USO,
                      VAL_FRAC_MIN

                      )
                      VALUES
                      (cDni_in,
                      cCodCamp_in,
                      cCodGrupoCia_in,
                      cCodLocalCanj_in,
                      cNumPedCanj_in,

                      cCodLocalOrigen_in,
                      cNumPedOrigen_in,
                      nSecPedVtaOrigen_in,
                      cCodProdOrigen_in,
                      nCantUso_in,
                      nValFracMin_in
                      );

  END;

  /* ************************************************************************************ */

  FUNCTION CA_F_OPERA_BENEFICIO_CAMPANA(
                                         cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNumPed_in       IN CHAR,
                                         cDni_in          IN CHAR,
                                         cUsuCrea_in      IN CHAR
                                        )
 return FarmaCursor
 is
  cur FarmaCursor;
      --OBTENEMOS LAS CAMPAÑAS QUE POSIBLES PARA REVISAR SI LLEVARA CANJES
      CURSOR curCampxCli IS
      SELECT DISTINCT C.COD_CAMP_CUPON--,H.IND_OPERA_CANJE
      FROM   VTA_CAMPANA_CUPON C,
             LGT_PROD p,
             CA_CLI_CAMP CLI_CAMP,
             CA_HIS_CLI_PED H
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.TIP_CAMPANA = 'A'
      AND    C.COD_GRUPO_CIA = P.COD_GRUPO_CIA
      AND    C.CA_COD_PROD = P.COD_PROD
      AND    H.ESTADO IN ('P','A')
      AND    H.IND_OPERA_CANJE IN ('N')
      AND    CLI_CAMP.DNI_CLI = cDni_in
      AND    CLI_CAMP.DNI_CLI = H.DNI_CLI
      AND    CLI_CAMP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
      AND    CLI_CAMP.COD_CAMP_CUPON = C.COD_CAMP_CUPON
      AND    H.COD_GRUPO_CIA = CLI_CAMP.COD_GRUPO_CIA
      AND    H.COD_CAMP_CUPON = CLI_CAMP.COD_CAMP_CUPON;

      CURSOR curPedCampCli (cCodCampana IN CHAR) IS
      SELECT H.FEC_PED_VTA,H.COD_GRUPO_CIA,H.COD_LOCAL,H.NUM_PED_VTA,
             H.SEC_PED_VTA,H.COD_PROD,
             H.CANT_RESTANTE
      FROM   CA_HIS_CLI_PED H
      WHERE  H.DNI_CLI = cDni_in
      AND    H.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    H.COD_CAMP_CUPON = cCodCampana
      AND    H.ESTADO IN ('P','A')
      AND    H.IND_OPERA_CANJE = 'X'
      ORDER  BY H.FEC_PED_VTA ASC;

      cIndPermiteCanjeCamp char(1);

      nValFracMax       number;
      --Cantidad de unidades minima de compra del pedido
      nCantUndMinCompra number;
      nNumDia    VARCHAR2(2);

      --variables para operar y ver si llevara el regalo
      nValFrac number;
      nMetaUnid number; --cantidad que debe de llegar en unidades minimas para llevarse el regalo
      nCantAcumCamp number;--cantidad acumulada por campaña para ver
                           --si llega a la meta de la camapña para dar el reagalo
      nCantUsada number;--cantidad de unidades usada de un pedido para llevar el regalo
  BEGIN
    nNumDia := OBTIEN_NUM_DIA(SYSDATE);

    FOR aCampCli IN curCampxCli
    LOOP
        DBMS_OUTPUT.put_line('.CAMP:'||aCampCli.COD_CAMP_CUPON);
        cIndPermiteCanjeCamp :=CA_F_PERMITE_CANJE(cCodGrupoCia_in,cCodLocal_in,
                                                  cDni_in,aCampCli.COD_CAMP_CUPON,
                                                  nNumDia);
        if cIndPermiteCanjeCamp = 'S' then

          ------- VERIFICA POR CADA CAMPAÑA LAS UNIDADES MINIMAS DE CANJE
          SELECT /*C.CA_COD_PROD,
                 (C.CA_CANT_PROD/C.CA_VAL_FRAC)*P.VAL_MAX_FRAC CANT_REGALO,*/
                 (C.CA_CANT_CANJE/C.CA_VAL_FRAC_CANJE)*P.VAL_MAX_FRAC META_CANJE,
                 P.VAL_MAX_FRAC
          into   nMetaUnid,nValFrac
          FROM   VTA_CAMPANA_CUPON C,
                 LGT_PROD p
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    C.TIP_CAMPANA = 'A'
          AND    C.COD_CAMP_CUPON = aCampCli.COD_CAMP_CUPON
          AND    C.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND    C.CA_COD_PROD = P.COD_PROD;

          DBMS_OUTPUT.put_line('nMetaUnid:'||nMetaUnid);
          DBMS_OUTPUT.put_line('nValFrac:'||nValFrac);

          --SE CAMBIA COMO POSIBLE SELECCION ESTO INDICA QUE SE ESTAN OPERANDO PARA
          UPDATE CA_HIS_CLI_PED H
          SET    H.IND_OPERA_CANJE = 'X',
                 H.FEC_INI_OPERA_CANJE = SYSDATE
          WHERE  H.DNI_CLI = cDni_in
          AND    H.COD_GRUPO_CIA  = cCodGrupoCia_in
          AND    H.COD_CAMP_CUPON = aCampCli.COD_CAMP_CUPON
          --MODIFICADO POR JCORTEZ 06.01.2009
          AND    H.CANT_RESTANTE > 0
          AND    H.ESTADO IN ('P','A')
          AND    H.IND_OPERA_CANJE = 'N';

            nCantAcumCamp := 0;
            --------inicio
            FOR aPedAcum IN curPedCampCli (aCampCli.COD_CAMP_CUPON)
            LOOP
                --DBMS_OUTPUT.put_line('.CAMP:'||aCampCli.COD_CAMP_CUPON);
                if nCantAcumCamp < nMetaUnid then
                    nCantUsada := aPedAcum.Cant_Restante;

                    if (nCantAcumCamp + nCantUsada) < nMetaUnid then
                       nCantAcumCamp := nCantAcumCamp + nCantUsada;
                    else
                       if (nCantAcumCamp + nCantUsada) = nMetaUnid then
                           nCantAcumCamp := nCantAcumCamp + nCantUsada;
                       elsif (nCantAcumCamp + nCantUsada) > nMetaUnid then
                              nCantUsada := nMetaUnid - nCantAcumCamp;
                              nCantAcumCamp := nCantAcumCamp + nCantUsada;
                       end if;
                    end if;

                    if nCantUsada >0 then
                        -----
                        UPDATE CA_HIS_CLI_PED H
                        SET    H.IND_OPERA_CANJE = 'S',
                               H.FEC_INI_OPERA_CANJE = SYSDATE,
                               H.CANT_USO_OPER_MATRIZ = nCantUsada
                        WHERE  H.DNI_CLI = cDni_in
                        AND    H.COD_GRUPO_CIA  = cCodGrupoCia_in
                        AND    H.COD_CAMP_CUPON = aCampCli.COD_CAMP_CUPON
                        AND    H.COD_LOCAL = aPedAcum.Cod_Local
                        AND    H.NUM_PED_VTA = aPedAcum.Num_Ped_Vta
                        AND    H.SEC_PED_VTA = aPedAcum.Sec_Ped_Vta
                        AND    H.COD_PROD = aPedAcum.Cod_Prod
                        AND    H.ESTADO IN ('P','A')
                        AND    H.IND_OPERA_CANJE = 'X';

                       --inserta auxiliar de acumulacion
                       CA_P_INSERT_AUX_CANJE(cDni_in,aCampCli.COD_CAMP_CUPON,
                                             --datos del pedido canje
                                             cCodGrupoCia_in,cCodLocal_in,cNumPed_in,
                                             --datos del pedido de origen para usar las unidades
                                             aPedAcum.Cod_Local,aPedAcum.Num_Ped_Vta,aPedAcum.Sec_Ped_Vta,aPedAcum.Cod_Prod,
                                             nCantUsada,nValFrac
                                             );
                    end if;

                else
                    --exit when 1 = 1;--dejo de recorrer el cursor si ya se completo las unidades
                    UPDATE CA_HIS_CLI_PED H
                    SET    H.IND_OPERA_CANJE = 'N',
                           H.FEC_INI_OPERA_CANJE = null
                    WHERE  H.DNI_CLI = cDni_in
                    AND    H.COD_GRUPO_CIA  = cCodGrupoCia_in
                    AND    H.COD_CAMP_CUPON = aCampCli.COD_CAMP_CUPON
                    AND    H.COD_LOCAL = aPedAcum.Cod_Local
                    AND    H.NUM_PED_VTA = aPedAcum.Num_Ped_Vta
                    AND    H.SEC_PED_VTA = aPedAcum.Sec_Ped_Vta
                    AND    H.COD_PROD = aPedAcum.Cod_Prod
                    AND    H.ESTADO IN ('P','A')
                    AND    H.IND_OPERA_CANJE = 'X';
                end if;

            END LOOP;
            --------fin

        end if;

    END LOOP;

    ---CONSULTA LA TABLA AUXILIAR DONDE SE ASOCIA LAS UNIDADES DE CANJE PARA OBTENER.
    OPEN cur FOR
    SELECT COD_CAMP_CUPON   || 'Ã' ||
           COD_LOCAL_ORIGEN || 'Ã' ||
           NUM_PED_ORIGEN   || 'Ã' ||
           SEC_PED_ORIGEN   || 'Ã' ||
           COD_PROD_ORIGEN  || 'Ã' ||
           CANT_USO         || 'Ã' ||
           VAL_FRAC_MIN
    FROM   CA_AUX_CANJE_MATRIZ
    WHERE  COD_GRUPO_CIA  = cCodGrupoCia_in
    AND    COD_LOCAL_CANJ = cCodLocal_in
    AND    NUM_PED_CANJ   = cNumPed_in;

    return cur;

 end;

 /* ******************************************************************************** */
  FUNCTION CA_F_PERMITE_CANJE(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cDni_in         IN CHAR,
                              cCodCamp_in     IN CHAR,
                              nNumDia_in      IN NUMBER) RETURN CHAR IS

    vRestoCanjeFraccionLocal number;
    nNumPerCanje             number;
    cTipoPeriodoCanje        char(1);
    nCantMaxCanje            number;

    nNumeroCanjesRealizados number;
    metaUnidades NUMBER;

    dFechaIniValida date;
    dFechaFinValida date;

    nCantUnidadesAcumuladas number;
    vRespuesta char(1) := 'N'; --por defecto no permite el canje

  BEGIN

    SELECT MOD((CA_CANT_PROD * P.VAL_FRAC_LOCAL), CA_VAL_FRAC) CA,
           CA_MAX_CANJE,
           CA_PER_MAX_CANJE,
           CA_NUM_CANJE_X_PER,
       /*
        CA_MAX_CANJE  NUMBER(30)  Y   CA Numero maximo de canjes segun el periodo que indique
        CA_PER_MAX_CANJE  CHAR(1) Y   CA indica el periodo para validar maximo numero de canjes S semana o M mes
        CA_NUM_CANJE_X_PER  NUMBER(30)  Y   CA indica el numero de veces para cada canje si es 1 cada mes o 2 cada mes
        */
          (CAMP.CA_CANT_CANJE/CAMP.CA_VAL_FRAC_CANJE)*MP.VAL_MAX_FRAC META_CANJE
      INTO vRestoCanjeFraccionLocal,
           nCantMaxCanje,
           cTipoPeriodoCanje,
           nNumPerCanje,
           metaUnidades
      FROM VTA_CAMPANA_CUPON CAMP, LGT_PROD_LOCAL P,LGT_PROD MP
     WHERE CAMP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CAMP.COD_CAMP_CUPON = cCodCamp_in
       AND P.COD_LOCAL = cCodLocal_in
       AND P.COD_GRUPO_CIA = CAMP.COD_GRUPO_CIA
       AND P.COD_PROD = CAMP.CA_COD_PROD
       AND P.COD_GRUPO_CIA = MP.COD_GRUPO_CIA
       AND P.COD_PROD = MP.COD_PROD;


 dbms_output.put_line('vRestoCanjeFraccionLocal:'||vRestoCanjeFraccionLocal);

    IF vRestoCanjeFraccionLocal = 0 THEN

      IF cTipoPeriodoCanje = 'S' THEN
        SELECT to_date(to_char(trunc(SYSDATE - (nNumDia_in - 1) - 7 * (nNumPerCanje - 1)),
                               'dd/mm/yyyy') || ' 00:00:00',
                       'DD/MM/YYYY HH24:MI:SS') fecha_ini,
               --to_date(to_char(trunc(SYSDATE + (7-4)),'dd/mm/yyyy')||' 23:59:59','DD/MM/YYYY HH24:MI:SS') fecha_fin
               to_date(to_char(trunc(SYSDATE), 'dd/mm/yyyy') || ' 23:59:59',
                       'DD/MM/YYYY HH24:MI:SS') fecha_fin
          INTO dFechaIniValida, dFechaFinValida
          FROM DUAL;
      ELSIF cTipoPeriodoCanje = 'M' THEN
        select TRUNC(ADD_MONTHS(sysdate, - (nNumPerCanje - 1)), 'MM') fecha_ini,
               to_date(to_char(trunc(SYSDATE), 'dd/mm/yyyy') || ' 23:59:59',
                       'DD/MM/YYYY HH24:MI:SS') fecha_fin
          INTO dFechaIniValida, dFechaFinValida
          from dual;
      END IF;
      dbms_output.put_line('cTipoPeriodoCanje:'||cTipoPeriodoCanje);
      dbms_output.put_line('dFechaIniValida:'||dFechaIniValida);
            dbms_output.put_line('dFechaFinValida:'||dFechaFinValida);
    -- dbms_output.put_line('PnNumeroCanjesRealizados:'||nNumeroCanjesRealizados);

      SELECT count(1)
        INTO nNumeroCanjesRealizados
        FROM CA_CANJ_CLI_PED C
       WHERE C.DNI_CLI = cDni_in
         AND C.COD_GRUPO_CIA = cCodGrupoCia_in
         AND C.COD_CAMP_CUPON = cCodCamp_in
         AND C.FEC_PED_VTA between dFechaIniValida and dFechaFinValida + 8
         AND C.ESTADO = 'A'; --CANJES ACTIVOS

    dbms_output.put_line('PnNumeroCanjesRealizados:'||nNumeroCanjesRealizados);
    dbms_output.put_line('nCantMaxCanje:'||nCantMaxCanje);
      if (nNumeroCanjesRealizados+1) <= nCantMaxCanje then

            SELECT SUM(H.CANT_RESTANTE)
            INTO   nCantUnidadesAcumuladas
            FROM   CA_HIS_CLI_PED H
            WHERE  H.DNI_CLI =cDni_in
            AND    H.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    H.COD_CAMP_CUPON = cCodCamp_in
            AND    H.ESTADO IN ('P','A')
            AND    H.IND_OPERA_CANJE = 'N';

      dbms_output.put_line('nCantUnidadesAcumuladas:'||nCantUnidadesAcumuladas);
      dbms_output.put_line('metaUnidades:'||metaUnidades);

            if nCantUnidadesAcumuladas>= metaUnidades then
              vRespuesta := 'S';
            end if;
      end if;

    END IF;
         dbms_output.put_line('Permite Canje Campaña:'||vRespuesta);

    RETURN vRespuesta;

  END;

  /* ************************************************************************************ */
  PROCEDURE CA_P_INSERT_HIS_PED_CLI (
                                     cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     cNumPed_in        IN CHAR,
                                     cDni_in           IN CHAR,
                                     cCodCamp_in       IN CHAR,
                                     nSecPedVta_in     IN CHAR,--
                                     cCodProd_in       IN CHAR,
                                     nCantPedido_in    IN CHAR,--
                                     nValFracPedido_in IN CHAR,--
                                     cEstado_in        IN CHAR,
                                     nValFracMin_in    IN CHAR,--
                                     cUsuCrea_in       IN CHAR,
                                     nCantRest_in      IN CHAR
                                   )
  is

  dFechaPed date;

  BEGIN
  /*select c.fec_ped_vta
  into   dFechaPed
  from   vta_pedido_vta_cab c
  where  c.cod_grupo_cia = cCodGrupoCia_in
  and    c.cod_local = cCodLocal_in
  and    c.num_ped_vta = cNumPed_in;*/

  SELECT SYSDATE
  into   dFechaPed
  FROM   DUAL;
        -- RAISE_APPLICATION_ERROR(-20018,'Fecha:'||to_char(sysdate,'dd/mm/yyyy'));
                        INSERT INTO CA_HIS_CLI_PED
                        (DNI_CLI,
                        COD_GRUPO_CIA,
                        COD_CAMP_CUPON,
                        COD_LOCAL,
                        NUM_PED_VTA,
                        FEC_PED_VTA,
                        SEC_PED_VTA,
                        COD_PROD,
                        CANT_ATENDIDA,
                        VAL_FRAC,
                        ESTADO,
                        CANT_RESTANTE,
                        VAL_FRAC_MIN,
                        USU_CREA_CA_HIS_CLI_PED,
                        FEC_CREA_CA_HIS_CLI_PED,
                        IND_PROC_MATRIZ,
                        FEC_PROC_MATRIZ
                        )
                        VALUES
                        (
                        cDni_in,
                        cCodGrupoCia_in,
                        cCodCamp_in,
                        cCodLocal_in,
                        cNumPed_in,
                        --dFechaPed ,
                        sysdate,
                        to_number(nSecPedVta_in,'9999999'),
                        cCodProd_in,
                        to_number(nCantPedido_in,'999999999'),
                        to_number(nValFracPedido_in,'999999999'),
                        cEstado_in,
                        to_number(nCantRest_in,'999999999'),
                        to_number(nValFracMin_in,'999999999'),
                        cUsuCrea_in,
                        SYSDATE,
                        'S',
                        SYSDATE
                        );

  END;

  /* ************************************************************************************ */

  PROCEDURE CA_P_ANALIZA_CANJE (
                                cCodGrupoCia_in  IN CHAR,
                                cCodLocal_in     IN CHAR,
                                cNumPed_in       IN CHAR,
                                cDni_in          IN CHAR,
                                cUsuMod_in       IN CHAR,
                                cAccion_in       IN CHAR
                                )
  is

  begin


  if cAccion_in = 'C' then -- Se esta cobrando el pedido
/* JMIRANDA 17/06/09
      --actualiza acumulaciones para que se operen
      UPDATE CA_HIS_CLI_PED H
      SET    H.ESTADO = 'A',
             H.USU_MOD_CA_HIS_CLI_PED = cUsuMod_in,
             H.FEC_MOD_CA_HIS_CLI_PED =SYSDATE
      WHERE  H.DNI_CLI        = cDni_in
      AND    H.ESTADO = 'P'
      AND    H.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    H.COD_LOCAL      = cCodLocal_in
      AND    H.NUM_PED_VTA    = cNumPed_in;

      --actualiza el uso de algunas de las actualizaciones en el local
      UPDATE CA_HIS_CLI_PED H
      SET    (
             H.IND_OPERA_CANJE,
             H.CANT_USO_OPER_MATRIZ,
             H.FEC_INI_OPERA_CANJE,
             H.CANT_RESTANTE,
             H.USU_MOD_CA_HIS_CLI_PED,
             H.FEC_MOD_CA_HIS_CLI_PED
             ) = (
                  SELECT 'N',0,NULL,
                         H.CANT_RESTANTE - O.CANT_USO,cUsuMod_in,SYSDATE
                  FROM   CA_AUX_CANJE_MATRIZ O
                  WHERE  O.DNI_CLI = cDni_in
                  AND    O.COD_GRUPO_CIA  = cCodGrupoCia_in
                  AND    O.COD_LOCAL_CANJ = cCodLocal_in
                  AND    O.NUM_PED_CANJ = cNumPed_in
                  AND    O.DNI_CLI = H.DNI_CLI
                  AND    O.COD_GRUPO_CIA = H.COD_GRUPO_CIA
                  AND    O.COD_CAMP_CUPON = H.COD_CAMP_CUPON
                  AND    O.COD_LOCAL_ORIGEN = H.COD_LOCAL
                  AND    O.NUM_PED_ORIGEN = H.NUM_PED_VTA
                  AND    O.SEC_PED_ORIGEN = H.SEC_PED_VTA
                  AND    O.COD_PROD_ORIGEN = H.COD_PROD
                 )

      WHERE  EXISTS (
                      SELECT 1
                      FROM   CA_AUX_CANJE_MATRIZ W
                        WHERE  W.DNI_CLI = cDni_in
                        AND    W.COD_GRUPO_CIA  = cCodGrupoCia_in
                        AND    W.COD_LOCAL_CANJ = cCodLocal_in
                        AND    W.NUM_PED_CANJ = cNumPed_in
                        AND    W.DNI_CLI = H.DNI_CLI
                        AND    W.COD_GRUPO_CIA = H.COD_GRUPO_CIA
                        AND    W.COD_CAMP_CUPON = H.COD_CAMP_CUPON
                        AND    W.COD_LOCAL_ORIGEN = H.COD_LOCAL
                        AND    W.NUM_PED_ORIGEN = H.NUM_PED_VTA
                        AND    W.SEC_PED_ORIGEN = H.SEC_PED_VTA
                        AND    W.COD_PROD_ORIGEN = H.COD_PROD
                    );
*/
      DELETE CA_AUX_CANJE_MATRIZ A
      WHERE  A.DNI_CLI = cDni_in
      AND    A.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    A.COD_LOCAL_CANJ = cCodLocal_in
      AND    A.NUM_PED_CANJ = cNumPed_in;



  elsif cAccion_in = 'N' then -- Se anula el pedido pedido cobrado anulado
/*
--actualiza el uso de algunas de las actualizaciones en el local
      delete CA_HIS_CLI_PED H
      WHERE  H.ESTADO = 'P'
      AND    H.DNI_CLI        = cDni_in
      AND    H.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    H.COD_LOCAL      = cCodLocal_in
      AND    H.NUM_PED_VTA    = cNumPed_in;
*/
      UPDATE CA_HIS_CLI_PED H
      SET    (
             H.IND_OPERA_CANJE,
             H.CANT_USO_OPER_MATRIZ,
             H.FEC_INI_OPERA_CANJE,
             H.USU_MOD_CA_HIS_CLI_PED,
             H.FEC_MOD_CA_HIS_CLI_PED
             ) = (
                  SELECT 'N',0,NULL,
                         cUsuMod_in,SYSDATE
                  FROM   CA_AUX_CANJE_MATRIZ O
                  WHERE  O.DNI_CLI = cDni_in
                  AND    O.COD_GRUPO_CIA  = cCodGrupoCia_in
                  AND    O.COD_LOCAL_CANJ = cCodLocal_in
                  AND    O.NUM_PED_CANJ = cNumPed_in
                  AND    O.DNI_CLI = H.DNI_CLI
                  AND    O.COD_GRUPO_CIA = H.COD_GRUPO_CIA
                  AND    O.COD_CAMP_CUPON = H.COD_CAMP_CUPON
                  AND    O.COD_LOCAL_ORIGEN = H.COD_LOCAL
                  AND    O.NUM_PED_ORIGEN = H.NUM_PED_VTA
                  AND    O.SEC_PED_ORIGEN = H.SEC_PED_VTA
                  AND    O.COD_PROD_ORIGEN = H.COD_PROD
                 )

      WHERE  EXISTS (
                      SELECT 1
                      FROM   CA_AUX_CANJE_MATRIZ W
                        WHERE  W.DNI_CLI = cDni_in
                        AND    W.COD_GRUPO_CIA  = cCodGrupoCia_in
                        AND    W.COD_LOCAL_CANJ = cCodLocal_in
                        AND    W.NUM_PED_CANJ = cNumPed_in
                        AND    W.DNI_CLI = H.DNI_CLI
                        AND    W.COD_GRUPO_CIA = H.COD_GRUPO_CIA
                        AND    W.COD_CAMP_CUPON = H.COD_CAMP_CUPON
                        AND    W.COD_LOCAL_ORIGEN = H.COD_LOCAL
                        AND    W.NUM_PED_ORIGEN = H.NUM_PED_VTA
                        AND    W.SEC_PED_ORIGEN = H.SEC_PED_VTA
                        AND    W.COD_PROD_ORIGEN = H.COD_PROD
                    );

      DELETE CA_AUX_CANJE_MATRIZ A
      WHERE  A.DNI_CLI = cDni_in
      AND    A.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    A.COD_LOCAL_CANJ = cCodLocal_in
      AND    A.NUM_PED_CANJ = cNumPed_in;

  end if;


  end;


  --metodo encargado de asociar cliente con campanias de acumulacion de compras
  --29.12.2008 JCALLO
  PROCEDURE CA_P_INSERT_CLI_CAMPANIA( cCodGrupoCia_in IN CHAR,
                                      cCodCampCupon_in  IN CHAR,
                                      cDni_cli_in       IN CHAR,
                                      cUsuario_in       IN CHAR)
  AS
  nExisteReg   number:=0;
  nExisteDni   number:=0;
  BEGIN

       select count(1)
       into   nExisteDni
       from   pbl_cliente c
       where  c.dni_cli = cDni_cli_in;

       if nExisteDni = 0 then
          insert into pbl_cliente
          (DNI_CLI,FEC_CREA_CLIENTE,USU_CREA_CLIENTE)
          values
          (trim(cDni_cli_in),sysdate,cUsuario_in);

       end if;


       SELECT count(*) into nExisteReg
       FROM CA_CLI_CAMP
       WHERE DNI_CLI = cDni_cli_in
       AND   COD_GRUPO_CIA = cCodGrupoCia_in
       AND   COD_CAMP_CUPON = cCodCampCupon_in;

       IF nExisteReg = 0 THEN -- quiere decir que no esta aun el cliente asociado a la campaña

         INSERT INTO CA_CLI_CAMP (COD_GRUPO_CIA,
                                  COD_CAMP_CUPON,
                                  DNI_CLI,
                                  ESTADO,
                                  USU_CREA_CA_CLI_CAMP,
                                  FEC_CREA_CA_CLI_CAMP)
         VALUES (cCodGrupoCia_in,
                 cCodCampCupon_in,
                 cDni_cli_in,
                 'A',
                 cUsuario_in,
                 SYSDATE);

       END IF;

  EXCEPTION

    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20015,'ERROR AL AFILIAR CLIENTE CON CAMPAÑA DE ACUMULACION DE VENTAS:'||SQLERRM);
  END;


  --metodo encargado de asociar cliente con tarjeta de fidelizacion
  --29.12.2008 JCALLO
  PROCEDURE CA_P_UPD_TARJETA_CLIENTE( cTarjeta_in    IN CHAR,
                                      cDni_cli_in    IN CHAR,
                                      cCodLocal_in   IN CHAR,
                                      cUsuario_in    IN CHAR)
   AS

  BEGIN

       UPDATE FID_TARJETA
       SET DNI_CLI         = cDni_cli_in,
           USU_MOD_TARJETA = cUsuario_in,
           FEC_MOD_TARJETA = SYSDATE,
           COD_LOCAL       = cCodLocal_in
       WHERE COD_TARJETA   = cTarjeta_in
       --cambio para no actualizar tarjetas que NO SEA NECESARIO
       --30.10.2009 dubilluz
       and   nvl(dni_cli,' ') != nvl(cDni_cli_in,' ');


  EXCEPTION

    WHEN OTHERS THEN

      --dbms_output.put_line('error');
      RAISE_APPLICATION_ERROR(-20007,'ERROR AL AFILIAR CLIENTE CON TARJETA DE FIDELIZACION:'||SQLERRM);


      --rollback;

  END;
 /* ******************************************************************************* */
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
                                   )
  is

  dFechaPed date;

  BEGIN
  /*SELECT SYSDATE
    into   dFechaPed
  FROM   DUAL;*/

                        INSERT INTO CA_CANJ_CLI_PED
                        (
                        DNI_CLI,
                        COD_GRUPO_CIA,
                        COD_CAMP_CUPON,
                        COD_LOCAL,
                        NUM_PED_VTA,
                        FEC_PED_VTA,
                        SEC_PED_VTA,
                        COD_PROD,
                        CANT_ATENDIDA,
                        VAL_FRAC,
                        ESTADO,
                        USU_CREA_CA_CANJ_CLI,
                        FEC_CREA_CA_CANJ_CLI,
                        IND_PROC_MATRIZ,
                        FEC_PROC_MATRIZ
                        )
                        VALUES
                        (
                        cDni_in,
                        cCodGrupoCia_in,
                        cCodCamp_in,
                        cCodLocal_in,
                        cNumPed_in,
                        to_date(trim(dFechaPed_in),'DD/MM/YYYY HH24:MI:SS') ,
                        to_number(nSecPedVta_in,'9999999'),
                        cCodProd_in,
                        to_number(nCantPedido_in,'999999999'),
                        to_number(nValFracPedido_in,'999999999'),
                        'A',
                        cUsuCrea_in,
                        SYSDATE,
                        'S',
                        SYSDATE
                        );

  END;
 /* ******************************************************************************* */
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
                                   )
  is

  dFechaPed date;

  BEGIN
  /*SELECT SYSDATE
    into   dFechaPed
  FROM   DUAL;*/

                        INSERT INTO CA_PED_ORIGEN_CANJ
                        (
                          DNI_CLI,
                          COD_GRUPO_CIA,
                          COD_CAMP_CUPON,
                          COD_LOCAL_CANJ,
                          NUM_PED_CANJ,
                          FEC_PED_VTA_CANJ,
                          SEC_PED_CANJ,
                          COD_PROD_CANJ,

                          COD_LOCAL_ORIGEN,
                          NUM_PED_ORIGEN,
                          SEC_PED_ORIGEN,
                          COD_PROD_ORIGEN,

                          ESTADO,
                          CANT_USO,
                          VAL_FRAC_MIN,
                          USU_CREA_CA_PED_ORIG,
                          FEC_CREA_CA_PED_ORIG,
                          IND_PROC_MATRIZ,
                          FEC_PROC_MATRIZ
                        )
                        VALUES
                        (
                        cDni_in,
                        cCodGrupoCia_in,
                        cCodCamp_in,
                        cCodLocal_in,
                        cNumPed_in,
                        to_date(trim(dFechaPed_in),'DD/MM/YYYY HH24:MI:SS') ,
                        to_number(nSecPedVta_in,'9999999'),
                        cCodProd_in,

                        cCodLocalOrigen_in,
                        cNumPedOrigen_in,
                        to_number(cSecPedVtaOrigen_in,'9999999'),
                        cCodProdOrigen_in,

                        cEstado_in,
                        to_number(cCantidadUso_in,'999999999'),
                        to_number(nValFracPedido_in,'999999999'),
                        cUsuCrea_in,
                        SYSDATE,
                        'S',
                        SYSDATE
                        );

  END;
  /* ******************************************************************************* */

   PROCEDURE CA_P_REVERTIR_CANJE_MATRIZ(
                                       cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cNumPed_in       IN CHAR,
                                       cDni_in       IN CHAR,
                                       cCodCamp_in   IN CHAR,
                                       cUsuMod_in    IN CHAR
                                       )
   IS
   nm number;
   BEGIN


      UPDATE CA_HIS_CLI_PED H
      SET    (
             H.IND_OPERA_CANJE,
             H.CANT_USO_OPER_MATRIZ,
             H.FEC_INI_OPERA_CANJE,
             H.USU_MOD_CA_HIS_CLI_PED,
             H.FEC_MOD_CA_HIS_CLI_PED
             ) = (
                  SELECT 'N',0,NULL,
                         cUsuMod_in,SYSDATE
                  FROM   CA_AUX_CANJE_MATRIZ O
                  WHERE  O.DNI_CLI = cDni_in
                  AND    O.COD_GRUPO_CIA  = cCodGrupoCia_in
                  AND    O.COD_LOCAL_CANJ = cCodLocal_in
                  AND    O.NUM_PED_CANJ = cNumPed_in
                  AND    O.DNI_CLI = cDni_in
                  AND    O.COD_CAMP_CUPON = cCodCamp_in
                  AND    O.DNI_CLI = H.DNI_CLI
                  AND    O.COD_GRUPO_CIA = H.COD_GRUPO_CIA
                  AND    O.COD_CAMP_CUPON = H.COD_CAMP_CUPON
                  AND    O.COD_LOCAL_ORIGEN = H.COD_LOCAL
                  AND    O.NUM_PED_ORIGEN = H.NUM_PED_VTA
                  AND    O.SEC_PED_ORIGEN = H.SEC_PED_VTA
                  AND    O.COD_PROD_ORIGEN = H.COD_PROD
                 )

      WHERE  EXISTS (
                      SELECT 1
                      FROM   CA_AUX_CANJE_MATRIZ W
                        WHERE  W.DNI_CLI = cDni_in
                        AND    W.COD_GRUPO_CIA  = cCodGrupoCia_in
                        AND    W.COD_LOCAL_CANJ = cCodLocal_in
                        AND    W.NUM_PED_CANJ = cNumPed_in
                        AND    W.DNI_CLI = cDni_in
                        AND    W.COD_CAMP_CUPON = cCodCamp_in
                        AND    W.DNI_CLI = H.DNI_CLI
                        AND    W.COD_GRUPO_CIA = H.COD_GRUPO_CIA
                        AND    W.COD_CAMP_CUPON = H.COD_CAMP_CUPON
                        AND    W.COD_LOCAL_ORIGEN = H.COD_LOCAL
                        AND    W.NUM_PED_ORIGEN = H.NUM_PED_VTA
                        AND    W.SEC_PED_ORIGEN = H.SEC_PED_VTA
                        AND    W.COD_PROD_ORIGEN = H.COD_PROD
                    );

      DELETE CA_AUX_CANJE_MATRIZ A
      WHERE  A.DNI_CLI = cDni_in
      AND    A.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    A.COD_LOCAL_CANJ = cCodLocal_in
      AND    A.NUM_PED_CANJ = cNumPed_in
      AND    A.DNI_CLI      = cDni_in
      AND    A.COD_CAMP_CUPON = cCodCamp_in;


     /* select count(1)--w.cant_restante
      into   nm
      from   ca_his_cli_ped w
      where  W.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    W.COD_LOCAL = cCodLocal_in
      AND    W.Num_Ped_Vta = cNumPed_in;
  --    AND    W.DNI_CLI = cDni_in
--      AND    W.COD_CAMP_CUPON = cCodCamp_in;

      RAISE_APPLICATION_ERROR(-20666,'cantidad restante..' ||nm);*/

   END;

END PTOVENTA_MATRIZ_CA_CLI;
/

