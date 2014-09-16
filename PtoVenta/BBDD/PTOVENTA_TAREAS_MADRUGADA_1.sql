--------------------------------------------------------
--  DDL for Package Body PTOVENTA_TAREAS_MADRUGADA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_TAREAS_MADRUGADA" is

/******************************************************************************/

  PROCEDURE P_PROCESA_TAREAS_MADRUGADA IS
  BEGIN
       P_ENVIA_ENTREGAS_SIN_AFECTAR;
  END;

  PROCEDURE P_ENVIA_ENTREGAS_SIN_AFECTAR IS

           v_vHtmlCorreo VARCHAR2(32767):='';

           v_codGrupoCia         PBL_LOCAL.COD_GRUPO_CIA%TYPE;
           v_codLocal            PBL_LOCAL.COD_LOCAL%TYPE;
           v_descCortaLocal      PBL_LOCAL.DESC_CORTA_LOCAL%TYPE;
           v_mailLocal           PBL_LOCAL.MAIL_LOCAL%TYPE;

           v_codZonaVta          VTA_ZONA_VTA.COD_ZONA_VTA%TYPE;
           v_descCortaZonaVta    VTA_ZONA_VTA.DESC_CORTA_ZONA_VTA%TYPE;
           v_emailJefeZona       VTA_ZONA_VTA.EMAIL_JEFE_ZONA%TYPE;
           v_gerenteVenta        VARCHAR2(20);

           v_Mensaje             VARCHAR2(500);
           v_cantGuias           INTEGER;

           v_cantDiasRetraso     INTEGER := 2;

           CURSOR curAlerta (cCodGrupoCia_in CHAR, cCodLocal_in CHAR, cDias_in CHAR) IS
                  SELECT DISTINCT
                        L.COD_LOCAL ||'-'|| L.DESC_CORTA_LOCAL                               "LOCAL",
                        TO_CHAR(G.FEC_CREA_GUIA_REM,'YYYY-MM-DD')                            "FECHA",
                        G.NUM_ENTREGA,
                        G.NUM_GUIA_REM,
                        DECODE(G.IND_GUIA_LIBERADA,'S','UB-L',NVL(G.TIPO_PED_REP,' '))       "TIPO_PED_REP"
                  FROM LGT_NOTA_ES_CAB C,LGT_NOTA_ES_DET D, LGT_GUIA_REM G, PBL_LOCAL L
                  WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND C.COD_LOCAL = cCodLocal_in
                        AND C.NUM_NOTA_ES LIKE '%%'
                        AND C.TIP_NOTA_ES = '03'
                        AND D.IND_PROD_AFEC = 'N'
                        AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                        AND C.COD_LOCAL = D.COD_LOCAL
                        AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
                        AND D.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                        AND D.COD_LOCAL = G.COD_LOCAL
                        AND D.NUM_NOTA_ES = G.NUM_NOTA_ES
                        AND D.SEC_GUIA_REM = G.SEC_GUIA_REM
                        AND L.COD_LOCAL = C.COD_LOCAL
                        AND G.FEC_CREA_GUIA_REM < TRUNC(SYSDATE)- cDias_in
                  ORDER BY 1, 2, 3;

/*
                  SELECT
                        L.COD_LOCAL ||'-'|| L.DESC_CORTA_LOCAL  "LOCAL",
                        TO_CHAR(G.FEC_CREA_GUIA_REM,'YYYY-MM-DD')              "FECHA",
                        G.NUM_ENTREGA,
                        G.NUM_GUIA_REM,
                        G.TIPO_PED_REP
                  FROM PBL_LOCAL L,
                       LGT_GUIA_REM G
                  WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND L.COD_LOCAL = cCodLocal_in
                    AND G.COD_GRUPO_CIA = L.COD_GRUPO_CIA
                    AND G.COD_LOCAL = L.COD_LOCAL
                    AND G.NUM_ENTREGA IS NOT NULL
                    AND G.IND_GUIA_CERRADA = 'N'
                    AND G.FEC_CREA_GUIA_REM < TRUNC(SYSDATE)- cDias_in
                  ORDER BY 1, 2, 3;
*/

  BEGIN

        SELECT DISTINCT L.COD_GRUPO_CIA, L.COD_LOCAL, L.DESC_CORTA_LOCAL, L.MAIL_LOCAL,
-- 2012-05-10 JOLIVA
--                        Z.COD_ZONA_VTA, CASE WHEN Z.DESC_CORTA_ZONA_VTA LIKE 'LIMA%' THEN 'rgilardi' ELSE 'jponce' END "GERENTE_VENTA",
                        Z.COD_ZONA_VTA, 'rgilardi' "GERENTE_VENTA",
                        Z.DESC_CORTA_ZONA_VTA, Z.EMAIL_JEFE_ZONA
        INTO v_codGrupoCia, v_codLocal, v_descCortaLocal, v_mailLocal,
             v_codZonaVta, v_gerenteVenta,
             v_descCortaZonaVta, v_emailJefeZona
        FROM VTA_IMPR_LOCAL I,
             PBL_LOCAL L,
             VTA_LOCAL_X_ZONA LZ,
             VTA_ZONA_VTA Z
        WHERE L.COD_GRUPO_CIA = I.COD_GRUPO_CIA
          AND L.COD_LOCAL = I.COD_LOCAL
          AND LZ.COD_GRUPO_CIA(+) = L.COD_GRUPO_CIA
          AND LZ.COD_LOCAL(+) = L.COD_LOCAL
          AND Z.COD_GRUPO_CIA(+) = LZ.COD_GRUPO_CIA
          AND Z.COD_ZONA_VTA(+) = LZ.COD_ZONA_VTA;

/*
                  SELECT COUNT(*)
                  INTO v_cantGuias
                  FROM PBL_LOCAL L,
                       LGT_GUIA_REM G
                  WHERE L.COD_GRUPO_CIA = v_codGrupoCia
                    AND L.COD_LOCAL = v_codLocal
                    AND G.COD_GRUPO_CIA = L.COD_GRUPO_CIA
                    AND G.COD_LOCAL = L.COD_LOCAL
                    AND G.NUM_ENTREGA IS NOT NULL
                    AND G.IND_GUIA_CERRADA = 'N'
                    AND G.FEC_CREA_GUIA_REM < TRUNC(SYSDATE)- v_cantDiasRetraso;
*/

                  SELECT COUNT(*)
                  INTO v_cantGuias
                  FROM LGT_NOTA_ES_CAB C,LGT_NOTA_ES_DET D, LGT_GUIA_REM G, PBL_LOCAL L
                  WHERE C.COD_GRUPO_CIA = v_codGrupoCia
                        AND C.COD_LOCAL = v_codLocal
                        AND C.NUM_NOTA_ES LIKE '%%'
                        AND C.TIP_NOTA_ES = '03'
                        AND D.IND_PROD_AFEC = 'N'
                        AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                        AND C.COD_LOCAL = D.COD_LOCAL
                        AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
                        AND D.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                        AND D.COD_LOCAL = G.COD_LOCAL
                        AND D.NUM_NOTA_ES = G.NUM_NOTA_ES
                        AND D.SEC_GUIA_REM = G.SEC_GUIA_REM
                        AND L.COD_LOCAL = C.COD_LOCAL
                        AND G.FEC_CREA_GUIA_REM < TRUNC(SYSDATE)- v_cantDiasRetraso;

          IF v_cantGuias != 0 THEN
                        v_vHtmlCorreo:= v_vHtmlCorreo ||
                                        '<html>'||
                                       '<head>'||
                                        '  <meta http-equiv="Content-Type" content="application/vnd.ms-excel">'||
--                                        '  <title> ALERTA DE ENTREGAS SIN AFECTAR </title>'||
                                        '</head>'||
                                        '<body>'||
--                                        '<h3 align="center"> ALERTA DE ENTREGAS SIN AFECTAR </h3>'||
                                        '<h3 align="center">Fecha : '||to_char(sysdate,'dd/mm/yyyy')||' </h3>'||
                                        '<table style="text-align: left; width: 100%;" border="1"'||
                                        ' cellpadding="2" cellspacing="1">'||
                                        '    <tr>'||
--                                        '      <th>#</th>'||
                                        '      <th>LOCAL</th>'||
                                        '      <th>FECHA ENTREGA</th>'||
                                        '      <th>ENTREGA</th>'||
                                        '      <th>GUIA</th>'||
                                        '      <th>TIPO</th>'||
                                        '    </tr>';

                       FOR v_rcurAlerta IN curAlerta(v_codGrupoCia, v_codLocal, v_cantDiasRetraso)
                       LOOP
                              v_vHtmlCorreo := v_vHtmlCorreo ||
                                             '<tr>'||
                                             '<td>'||v_rcurAlerta.LOCAL||'</td>'||
                                             '<td>'||v_rcurAlerta.FECHA||'</td>'||
                                             '<td>'||v_rcurAlerta.NUM_ENTREGA||'</td>'||
                                             '<td>'||v_rcurAlerta.NUM_GUIA_REM||'</td>'||
                                             '<td>'||v_rcurAlerta.TIPO_PED_REP||'</td>'||
                                             '</tr>';
                       END LOOP;

                        v_vHtmlCorreo := v_vHtmlCorreo ||
                                       ' </table> '||
                                       ' </body> '||
                                       ' </html> ';

                     v_Mensaje := ' Estimado Jefe de Local, <BR><BR>';
                     v_Mensaje := v_Mensaje || ' Se envía la relación de guías en tránsito, favor de realizar la regularización:';

                      FARMA_EMAIL.envia_correo( v_codLocal ||'-'|| v_descCortaLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                               v_mailLocal,
--                                               'joliva',
                                               'Alerta de entregas (G/R) pendientes de afectar en el local ' || v_codLocal ||'-'|| v_descCortaLocal,
--                                               'Alerta de entregas (G/R) pendientes de afectar en el local ' || v_codLocal ||'-'|| v_descCortaLocal,
                                               ' ',
                                               v_Mensaje || v_vHtmlCorreo,
                                               v_emailJefeZona ||',' || v_gerenteVenta ||',joliva, llopez, ccasas',
--                                               'joliva',
                                               FARMA_EMAIL.GET_EMAIL_SERVER,
                                               true);

          ELSE
                      FARMA_EMAIL.envia_correo( v_codLocal ||'-'|| v_descCortaLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                               'joliva',
                                               'SIN GUIAS pendientes de afectar en ' || v_codLocal ||'-'|| v_descCortaLocal,
                                               'Alerta de entregas (G/R) pendientes de afectar en el local ' || v_codLocal ||'-'|| v_descCortaLocal,
                                               v_Mensaje || v_vHtmlCorreo,
                                               'ccasas',
                                               FARMA_EMAIL.GET_EMAIL_SERVER,
                                               true);
          END IF;

  END;


/******************************************************************************/

end;

/
