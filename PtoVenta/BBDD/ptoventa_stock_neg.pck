CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_STOCK_NEG" is

  -- Author  : LLEIVA
  -- Created : 26/12/2013 06:07:28 p.m.
  -- Purpose :

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Retorna listado de Solicitudes de Stock Negativo
  --Fecha       Usuario	Comentario
  --26/Dic/2013  LLEIVA  Creacion
  function LISTADO_SOL_STOCK_NEGATIVO(cNumSolic_in    IN CHAR,
                                      cEstado_in      IN CHAR,
                                      cSolicitante_in IN CHAR,
                                      cAprobador_in   IN CHAR,
                                      cFechaIni_in    IN CHAR,
                                      cFechaFin_in    IN CHAR) return FarmaCursor;

  --Descripcion: Retorna listado de detalles de Solicitudes de Stock Negativo
  --Fecha       Usuario	Comentario
  --27/Dic/2013  LLEIVA  Creacion
  function LISTADO_DET_STOCK_NEGATIVO(cNumSolic_in IN CHAR) return FarmaCursor;

  --Descripcion: Retorna listado de detalles de Solicitudes de Stock Negativo
  --Fecha       Usuario	Comentario
  --27/Dic/2013  LLEIVA  Creacion
  FUNCTION INV_REGULARIZAR_AJUSTE_KARDEX(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cCodProd_in      IN CHAR,
                                         cCodProdAnul_in  IN CHAR,
                                         cNeoCant_in      IN CHAR,
                                         cCodSol_in       IN CHAR,
                                         cUsu_in          IN CHAR) RETURN CHAR;

  --Descripcion: Envia un correo relativo a la regularizacion del stock negativo
  --Fecha       Usuario	Comentario
  --30/Dic/2013 LLEIVA  Creacion
  PROCEDURE ENVIAR_CORREO_REGULARIZACION(v_vDescLocal    IN CHAR,
                                         v_vUsuario      IN CHAR,
                                         v_vCodSol       IN CHAR,
                                         v_vCodProdReg   IN CHAR,
                                         v_vMovKardex    IN CHAR,
                                         v_vCantProd     IN CHAR);

  function LISTADO_VER_KARDEX(cCodProd    IN CHAR,
                              cCodLocal   IN CHAR) return FarmaCursor;

end PTOVENTA_STOCK_NEG;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_STOCK_NEG" is

  --Descripcion: Retorna listado de Solicitudes de Stock Negativo
  --Fecha       Usuario	Comentario
  --26/12/2013  LLEIVA  Creacion
  function LISTADO_SOL_STOCK_NEGATIVO(cNumSolic_in    IN CHAR,
                                      cEstado_in      IN CHAR,
                                      cSolicitante_in IN CHAR,
                                      cAprobador_in   IN CHAR,
                                      cFechaIni_in    IN CHAR,
                                      cFechaFin_in    IN CHAR) return FarmaCursor
  IS
    curRetorno FarmaCursor;
    query VARCHAR2(2000);
  begin

    query := 'SELECT CSS.COD_LOCAL || ''Ã'' ||
                     CSS.ID_SOLICITUD || ''Ã'' ||
                     DECODE(CSS.COD_ESTADO,''G'',''Generado'',''R'',''Resuelto'',CSS.COD_ESTADO) || ''Ã'' ||
                     (select COUNT(*) from DET_SOLICITUD_STOCK where id_solicitud = CSS.ID_SOLICITUD) || ''Ã'' ||
                     CSS.USU_SOLICITUD || ''Ã'' ||
                     CSS.QF_APROBADOR || ''Ã'' ||
                     TO_CHAR(CSS.FCH_SOLICITUD, ''dd/MM/yyyy'') as resultado
              FROM CAB_SOLICITUD_STOCK CSS
              WHERE CSS.NUM_PED_VTA is not null';
    IF(cNumSolic_in is not null) THEN
        query := query ||' and CSS.ID_SOLICITUD = '''||cNumSolic_in||'''';
    END IF;

    IF(cEstado_in is not null) THEN
        query := query ||' and CSS.COD_ESTADO = '''||cEstado_in||'''';
    END IF;

    IF(cSolicitante_in is not null) THEN
        query := query ||' and CSS.USU_SOLICITUD like ''%'||cSolicitante_in||'%''';
    END IF;

    IF(cAprobador_in is not null) THEN
        query := query ||' and CSS.QF_APROBADOR like ''%'||cAprobador_in||'%''';
    END IF;

    IF(cFechaIni_in is not null and
       cFechaFin_in is not null) THEN

        query := query ||' and CSS.FCH_SOLICITUD between TO_DATE('''||cFechaIni_in||' 00:00:00'',''dd/MM/YYYY HH24:MI:SS'') AND
                                                         TO_DATE('''||cFechaFin_in||' 23:59:59'',''dd/MM/YYYY HH24:MI:SS'')';
    END IF;
    query := query || ' ORDER BY CSS.FCH_SOLICITUD DESC';
    OPEN curRetorno FOR query;
    return curRetorno;

  end;

  --Descripcion: Retorna listado de detalles de Solicitudes de Stock Negativo
  --Fecha       Usuario	Comentario
  --27/Dic/2013  LLEIVA  Creacion
  function LISTADO_DET_STOCK_NEGATIVO(cNumSolic_in IN CHAR) return FarmaCursor
  IS
    curRetorno FarmaCursor;
  BEGIN
    OPEN curRetorno FOR
    select DSS.COD_PRODUCTO || 'Ã' ||
           P.DESC_PROD || 'Ã' ||
           DSS.CANT_PRODUCTO || 'Ã' ||
           --DSS.VAL_FRACCION || 'Ã' ||
           P.DESC_UNID_PRESENT || 'Ã' ||
           ( select STK_FISICO
             from lgt_prod_local PL
             where PL.COD_PROD = P.COD_PROD
                   and PL.COD_LOCAL = DSS.COD_LOCAL
                   and PL.COD_GRUPO_CIA = DSS.COD_GRUPO_CIA
           ) || 'Ã' ||
           DECODE(P2.COD_PROD,null,' ',P2.COD_PROD) || 'Ã' ||
           DECODE(P2.DESC_PROD,null,' ',P2.DESC_PROD) as resultado
    from DET_SOLICITUD_STOCK DSS
    inner join LGT_PROD P on P.COD_PROD=DSS.COD_PRODUCTO
    left join LGT_MOT_KARDEX MK on MK.COD_MOT_KARDEX = DSS.TIP_MOT_KARDEX
    left join LGT_KARDEX K on K.SEC_KARDEX = DSS.SEC_KARDEX
    left join LGT_PROD P2 on K.COD_PROD=P2.COD_PROD
    where DSS.ID_SOLICITUD = cNumSolic_in;
    return curRetorno;

  END;

  --Descripcion: Regulariza el kardex con el producto elegido
  --Fecha       Usuario	Comentario
  --27/12/2013  LLEIVA  Creacion
  FUNCTION INV_REGULARIZAR_AJUSTE_KARDEX(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cCodProd_in      IN CHAR,
                                         cCodProdAnul_in  IN CHAR,
                                         cNeoCant_in      IN CHAR,
                                         cCodSol_in       IN CHAR,
                                         cUsu_in          IN CHAR) RETURN CHAR
  IS
       v_nStkFisico   NUMBER;
       v_nValFrac     NUMBER;
       v_vDescUnidVta VARCHAR2(30);
       vCantMov_in    NUMBER;
       v_nNeoCod      CHAR(10);
       v_cSecKardex   LGT_KARDEX.SEC_KARDEX%TYPE;

       cCodMotKardex_in VARCHAR2(30);
       cGlosa_in VARCHAR2(30);
       cTipDoc_in VARCHAR2(30);

       valorRet VARCHAR2(2);
  BEGIN

       cCodMotKardex_in := '011';

       select DESC_CORTA_MOT_KARDEX into cGlosa_in
       from lgt_mot_kardex t
       where COD_MOT_KARDEX = cCodMotKardex_in;

       cTipDoc_in := '03';

       --Obtener STK Actual
       SELECT STK_FISICO, VAL_FRAC_LOCAL, UNID_VTA
       INTO v_nStkFisico, v_nValFrac, v_vDescUnidVta
       FROM LGT_PROD_LOCAL
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
               AND COD_LOCAL = cCodLocal_in
               AND COD_PROD = cCodProd_in;

       --Si el stock del producto es menor a la cantidad de movimiento, retornar error
       IF(v_nStkFisico < cNeoCant_in) THEN
            valorRet := '-1';
       ELSE
            --vCantMov_in := cNeoCant_in - v_nStkFisico;
            vCantMov_in := cNeoCant_in;
            v_nNeoCod   := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,
                                                                                                cCodLocal_in,
                                                                                                Ptoventa_Inv.COD_NUMERA_SEC_MOV_AJUSTE_KARD),
                                                               10,
                                                               '0',
                                                               'I');

            dbms_output.put_line('><cCodProd_in>'||cCodProd_in);
            dbms_output.put_line('><Adicional >'||vCantMov_in);
            dbms_output.put_line('><cNeoCant_in>'||cNeoCant_in);
            dbms_output.put_line('><v_nStkFisico>'||v_nStkFisico);

            IF vCantMov_in > 0 THEN
                 Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                                            cCodLocal_in,
                                                            Ptoventa_Inv.COD_NUMERA_SEC_MOV_AJUSTE_KARD,
                                                            cUsu_in);

                 --Actualizar Stock de Prod Local
                 UPDATE LGT_PROD_LOCAL
                 SET USU_MOD_PROD_LOCAL = cUsu_in,
                     FEC_MOD_PROD_LOCAL = SYSDATE,
                     STK_FISICO         = stk_fisico - TO_NUMBER(cNeoCant_in)
                 WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                       AND COD_LOCAL = cCodLocal_in
                       AND COD_PROD = cCodProd_in;

                 --INSERTAR KARDEX
                 v_cSecKardex := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,
                                                                                                      cCodLocal_in,
                                                                                                      Ptoventa_Inv.COD_NUMERA_SEC_KARDEX),
                                                                     10,
                                                                     '0',
                                                                     'I');
                 INSERT INTO LGT_KARDEX(COD_GRUPO_CIA,
                                        COD_LOCAL,
                                        SEC_KARDEX,
                                        COD_PROD,
                                        COD_MOT_KARDEX,
                                        TIP_DOC_KARDEX,
                                        NUM_TIP_DOC,
                                        STK_ANTERIOR_PROD,
                                        CANT_MOV_PROD,
                                        STK_FINAL_PROD,
                                        VAL_FRACC_PROD,
                                        DESC_UNID_VTA,
                                        USU_CREA_KARDEX,
                                        DESC_GLOSA_AJUSTE)
                 VALUES(cCodGrupoCia_in,
                        cCodLocal_in,
                        v_cSecKardex,
                        cCodProd_in,
                        cCodMotKardex_in,
                        cTipDoc_in,
                        v_nNeoCod,
                        v_nStkFisico,
                        (vCantMov_in * -1),
                        (v_nStkFisico - vCantMov_in),
                        v_nValFrac,
                        v_vDescUnidVta,
                        cUsu_in,
                        cGlosa_in);

                 Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                                            cCodLocal_in,
                                                            Ptoventa_Inv.COD_NUMERA_SEC_KARDEX,
                                                            cUsu_in);

                 --actualiza la DET_SOLICITUD_STOCK con los datos del Kardex
                 UPDATE DET_SOLICITUD_STOCK
                 SET TIP_MOT_KARDEX = cCodMotKardex_in,
                     NUM_TIP_KARDEX = cTipDoc_in,
                     SEC_KARDEX = v_cSecKardex,
                     USU_REGULACION = cUsu_in,
                     FEC_REGULACION = SYSDATE
                 WHERE ID_SOLICITUD = cCodSol_in and
                       COD_PRODUCTO = cCodProdAnul_in and
                       COD_LOCAL = cCodLocal_in and
                       COD_GRUPO_CIA = cCodGrupoCia_in and
                       TIP_MOT_KARDEX is NULL and
                       NUM_TIP_KARDEX is NULL and
                       SEC_KARDEX is NULL;

                 --si se actualizo algo, indicar que todo es correcto
                 if(sql%rowcount > 0) then
                       valorRet := '0';
                 else
                       RAISE VALUE_ERROR;
                 end if;

                 --Si todos los detalles de la solicitu han sido resultos, actualizar el estado de la cabecera
                 UPDATE CAB_SOLICITUD_STOCK
                 SET COD_ESTADO = 'R'
                 WHERE ID_SOLICITUD = cCodSol_in and
                       COD_ESTADO = 'G' and
                       (select COUNT(*) from DET_SOLICITUD_STOCK
                        where id_solicitud = cCodSol_in)
                       =
                       (select COUNT(*) from DET_SOLICITUD_STOCK
                        where id_solicitud = cCodSol_in
                        and TIP_MOT_KARDEX is not null);


                 commit;

                 ENVIAR_CORREO_REGULARIZACION(cCodLocal_in,
                                              cUsu_in,
                                              cCodSol_in,
                                              cCodProd_in,
                                              v_cSecKardex,
                                              vCantMov_in);
            END IF;
       END IF;
       return valorRet;
  EXCEPTION
       WHEN OTHERS THEN
            dbms_output.put_line(SQLcode ||' '||SQLERRM );
            rollback;
            return null;
  END;

  --Descripcion: Envia un correo relativo a la regularizacion del stock negativo
  --Fecha       Usuario	Comentario
  --30/Dic/2013 LLEIVA  Creacion
  PROCEDURE ENVIAR_CORREO_REGULARIZACION(v_vDescLocal    IN CHAR,
                                         v_vUsuario      IN CHAR,
                                         v_vCodSol       IN CHAR,
                                         v_vCodProdReg   IN CHAR,
                                         v_vMovKardex    IN CHAR,
                                         v_vCantProd     IN CHAR)
  IS
       email varchar2(50);
       mensaje varchar2(500);
       nombreLocal varchar2(50);
  BEGIN
      --obtener dirección de envio
      select LLAVE_TAB_GRAL into email
      from PBL_TAB_GRAL
      where COD_TAB_GRAL = 'EMAIL_STOCK_NEG';

      --obtiene nombre de local
      select DESC_CORTA_LOCAL into nombreLocal
      from PBL_LOCAL
      where COD_LOCAL = v_vDescLocal;

      --generar HTML de cuerpo de mensaje
      mensaje:='<LI> <B>' || 'LOCAL: '||v_vDescLocal  ||CHR(9)||'</B><BR>'||
                    '<I>MENSAJE: </I><BR>El usuario '||v_vUsuario||' realizo una regularización de la solicitud de stock negativo #'||
                    v_vCodSol||'  regularizandola con el producto '||v_vCodProdReg||' generando el movimiento del Kardex #'||
                    v_vMovKardex||' con la cantidad de '||v_vCantProd||
                 '</LI>';

      --ENVIA MAIL
      FARMA_EMAIL.envia_correo(v_vDescLocal||'-'||nombreLocal||' Farmaventa 2.0'||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                               email,
                               'REGULARIZACION STOCK NEGATIVO '||v_vDescLocal,
                               'REGULARIZACION STOCK NEGATIVO ',
                               mensaje,
                               '',
                               FARMA_EMAIL.GET_EMAIL_SERVER,
                               true);
  END;

  --Descripcion: Retorna listado de detalles de Solicitudes de Stock Negativo
  --Fecha       Usuario	Comentario
  --27/Dic/2013  LLEIVA  Creacion
  function LISTADO_VER_KARDEX(cCodProd    IN CHAR,
                              cCodLocal   IN CHAR) return FarmaCursor
  IS
    curRetorno FarmaCursor;
  BEGIN
    OPEN curRetorno FOR
    select P.COD_PROD || 'Ã' ||
           P.DESC_PROD || 'Ã' ||
           P.DESC_UNID_PRESENT || 'Ã' ||
           L.NOM_LAB || 'Ã' ||
           PL.STK_FISICO || 'Ã' ||
           PL.VAL_FRAC_LOCAL || 'Ã' ||
           P.VAL_MAX_FRAC || 'Ã' ||
           'N' as resultado
    from LGT_PROD P
    inner join LGT_PROD_LOCAL PL on PL.COD_LOCAL=cCodLocal and PL.COD_PROD=P.COD_PROD
    left join LGT_LAB L on L.COD_LAB = P.COD_LAB
    where P.COD_PROD = cCodProd;
    return curRetorno;

  END;

end PTOVENTA_STOCK_NEG;
/

