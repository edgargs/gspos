--------------------------------------------------------
--  DDL for Package Body PTOVENTA_CONSUMO_AUTOMATICO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_CONSUMO_AUTOMATICO" AS

  -- Creación           :  RHERRERA
  -- Motivo             : Lógica del Ajuste automático.
  -- Fecha              : 25.09.2014
  -- Requerimiento      : Ajuste Autoático de Insumos
  -- Jefe Requriemiento : JOLIVA
  -- Usuario interesado : PYOVERA
  ------------------------------------------------
  -- Usu y Fecha Modific.       :
  -- Motivo                     :
  PROCEDURE P_PROCESAR_AJUSTE IS
  
    c_ajuste_dia number;
    cvalor       number;
    diferencia   number;
    pendiente    number;
    cant_x_aj    number;
    cNeoCant_in  number;
    sobrante     number;
    --cant         integer := 0;
    v_nStkFisico NUMBER;
    v_nValFrac   NUMBER;
  
    COD_PROD_T TAB_COD_PROD;
    indi       integer := 0;
    vFecProce  tmp_lgt_ajuste_auto.fec_ajuste_dia%type;
    vLocal     tmp_lgt_ajuste_auto.cod_local%type;
  
    --  Lista de productos a ajustar
    CURSOR cLista IS
      select a.cod_local,
             a.cod_prod,
             a.cant_mov_mes,
             --a.val_frac,
             a.desc_unid_present,
             a.desc_prod
        from lgt_ajuste_auto a, LGT_PROD P
       where P.COD_PROD = A.COD_PROD
         and P.EST_PROD = ESTADO_ACTIVO
         and a.est_prod = ESTADO_ACTIVO;
    --AND   A.COD_PROD='572223';--USO PARA PRUEBAS
  
    -- LISTA EL MAESTRO DE PRODUCTOS Y SU AJUSTE BASE.
    CURSOR cListaMaestros IS
      select lp.cod_local,
             p.cod_prod,
             lp.tot_unid_vta_rdm,
             --mfp.stk_maximo,
             --a.val_frac,
             p.desc_unid_present,
             p.desc_prod
        from --MF_LGT_PROD_LOCAL_REP MFP,
             LGT_PARAM_PROD_LOCAL LP,
             LGT_PROD             P
       WHERE --P.COD_PROD = MFP.COD_PROD_MF
       P.COD_PROD = LP.COD_PROD
       AND P.COD_GRUPO_REP = cCodGrupoInsumo
       AND P.EST_PROD = ESTADO_ACTIVO
       AND TRUNC(lp.IND_ACTIVO) >= TRUNC(v_SYSDATE) --TO_CHAR(SYSDATE,'DD/MM/YYYY')
       AND LP.COD_LOCAL = (select distinct cod_local from vta_impr_local);
  
  BEGIN
    FOR x in cListaMaestros LOOP
      if x.tot_unid_vta_rdm > 0 then
        P_INSERT_AJUSTE_AUTO(x.cod_local,
                             x.cod_prod,
                             x.desc_prod,
                             x.tot_unid_vta_rdm,
                             x.desc_unid_present,
                             vseciniajuste);
      end if;
    
    END LOOP;
  
    FOR i in cLista LOOP
      -- APERTURO EL LOOP
      -- contador
    
      --
      vLocal := i.cod_local;
    
      --- Obtengo stock fisio y valor de fraccionamiento del local
      SELECT STK_FISICO, VAL_FRAC_LOCAL
        INTO v_nStkFisico, v_nValFrac
        FROM LGT_PROD_LOCAL
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = i.cod_local
         AND COD_PROD = i.cod_prod;
    
      -- obtenga el valor del ajuste diario.
      c_ajuste_dia := round((i.cant_mov_mes / v_num_dia) * v_nValFrac, 3);
    
      --obtengo en valor entero del ajuste diario
      Select TRUNC(c_ajuste_dia) into cvalor From dual;
    
      -- obtengo la suma total de cantidades pendientes a AJUSTAR
      -- por producto de dias anteriores.
      -- que cuenten con estado procesado 'N' y fecha de proceso NULL
      select NVL(sum(p.CANT_PENDIENTE), 0) --,trunc(sum(CANT_PENDIENTE))
        into pendiente --, e_pendiente
        from TMP_LGT_AJUSTE_AUTO p
       where p.cod_local = i.cod_local
         and p.cod_prod = i.cod_prod
         and p.ind_procesado = VALOR_NO --;
         and p.fec_procesado is null;
    
      -- obtengo la cantidad final ajustar, sumando
      -- el ajuste del dia + cantidad pendiente ajustar.
      cant_x_aj := c_ajuste_dia + pendiente;
    
      -- obtengo el stock fisico real despues del ajuste
      -- con la diferencia del stockReal - cantidad entera del ajuste final
      cNeoCant_in := v_nStkFisico - trunc(cant_x_aj);
    
      --si cNeoCant_in es mayor a cero ajuste la cantida entera
      --de la diferencia del stock y cantida ajustar
      if cNeoCant_in >= 0 then
        cNeoCant_in := cNeoCant_in;
      else
        --caso contrario si la cantida ajustar es mayor al stock,
        --ajusto todo hasta tener valor del stock 0;
        cNeoCant_in := 0;
      
        if v_nStkFisico = 0 then
          /*              cant_x_aj := c_ajuste_dia + pendiente;
                         else
          */
          cant_x_aj := 0;
        end if;
      
        ---Guardo los productos que no cuenta con stock para ajustar
        indi := indi + 1;
        COD_PROD_T(indi) := i.cod_prod;
      
      end if;
    
      /***************************MUEVE STOCK****************************/
      -- muevo stock y kardex, con la cantidad antera a ajustar el
      -- presente dia.
    
      IF cNeoCant_in <> v_nStkFisico THEN
      
        PTOVENTA_INV.INV_INGRESA_AJUSTE_KARDEX(cCodGrupoCia_in,
                                               i.cod_local,
                                               i.cod_prod,
                                               c_COD_MOT_AJUST_AUTO, --522
                                               cNeoCant_in, -- cantidad entera a ajustar
                                               '', -- glosa
                                               TIP_DOC_KARDEX_AJUSTE, --
                                               USU_CONSUMO_AUTO, -- usuario del consumo automatico
                                               VALOR_NO --sin Correo
                                               );
      END IF;
      /******************************************************************/
    
      -- SI  la cantidad final a ajustar final es mayor a 1
      if cant_x_aj >= 1 then
        --- actualizo el estado y fecha de procesado a
        -- 'S'--> procesado, a las fechas sin procesar y estaod 'N'
        -- esto para no sumar las cantidades pendientes.
        update TMP_LGT_AJUSTE_AUTO p
           set p.FEC_PROCESADO = v_SYSDATE, p.IND_PROCESADO = VALOR_SI --procesado
         where COD_LOCAL = i.cod_local
           and COD_PROD = i.cod_prod
           and IND_PROCESADO = VALOR_NO; -- sin procesar a procesado
      
        --obtiene el monto de la diferencia.
        diferencia := v_nStkFisico - trunc(cant_x_aj);
        if diferencia >= 0 then
          -- obtengo la cantidad sobrante del ajuste
          sobrante := cant_x_aj - trunc(cant_x_aj);
        else
          sobrante := cant_x_aj + diferencia;
        end if;
      
      else
        -- SI  la cantidad final a ajustar final es menor a 1
        sobrante := c_ajuste_dia;
      end if;
    
      /* DBMS_OUTPUT.put_line(''||'');*/
    
      -- QUERY PARA SIMULAR LA CANTIDA DE DIAS.
      ----- TEST
      /* select nvl(max(a.sec_ajuste_dia), 0) + 1
       into cant
       from TMP_LGT_AJUSTE_AUTO a
      where a.cod_local = i.cod_local
        and a.cod_prod = i.cod_prod
        and a.fec_ajuste_dia = TO_CHAR(v_SYSDATE, 'DD/MM/YYYY');*/
    
      -- inserto un nuevo registro con los datos del ajuste del presente dia
      -- además del sobrante generado.
      INSERT INTO TMP_LGT_AJUSTE_AUTO X
        ( --sec_ajuste_dia, --1
         COD_LOCAL, --2
         COD_PROD, --3
         FEC_AJUSTE_DIA, --4
         CANT_AJUSTE, --5
         CANT_PENDIENTE, --6
         STK_FISICO_LOCAL, --7
         VAL_FRAC, ---8
         USU_CREA_AJUSTE --9
         )
      VALUES
        ( --cant, --1
         i.cod_local, --2
         i.cod_prod, --3
         TO_CHAR(v_SYSDATE, 'DD/MM/YYYY'), --4
         cant_x_aj, --5
         sobrante, --6
         cNeoCant_in, --7
         v_nValFrac, --8
         USU_CONSUMO_AUTO --9
         );
    
      --- Si existe un sobrante para ajustar, este se guarda en el registro
      --- y actulizamos su estado como 'N', para indicar pendiente de procesar.
      if sobrante > 0 then
      
        update TMP_LGT_AJUSTE_AUTO C
           set CANT_PENDIENTE = sobrante, IND_PROCESADO = VALOR_NO -- proceso pendiente
         where COD_LOCAL = i.cod_local
           AND COD_PROD = i.cod_prod
              --AND SEC_AJUSTE_DIA = cant
           AND FEC_AJUSTE_DIA = TRUNC(v_SYSDATE); --TO_CHAR(v_SYSDATE, 'DD/MM/YYYY');
      
      else
      
        ---  Caso contrario (si no tiene sobrante para ajustar), se actualizar el
        ---  indicador de procesado y la fecha de procesamiento.
      
        update TMP_LGT_AJUSTE_AUTO
           set FEC_PROCESADO = v_SYSDATE
         where COD_LOCAL = i.cod_local
           AND COD_PROD = i.cod_prod
              --AND SEC_AJUSTE_DIA = cant
           AND FEC_AJUSTE_DIA = TRUNC(v_SYSDATE); --TO_CHAR(v_SYSDATE, 'DD/MM/YYYY');
      
      end if;
    
      -- Entro al procedimiento para actualizar la cantidad maxima de movimientos
      -- del producto.
      P_UPDATE_CANT_AJUSTE(i.cod_local, i.cod_prod);
    
    END LOOP; -- cierro el LOOP
  
    select to_char(v_SYSDATE, 'DD/MM/YYYY') into vFecProce from dual;
  
    commit; -- se acepta el commit para los cambios realizados.
  
    if indi > 0 then
      P_EMAIL_CONSUMO_AUTO(vLocal, vFecProce, COD_PROD_T);
    end if;
  
  EXCEPTION
    WHEN OTHERS THEN
      --DBMS_OUTPUT.put_line('ERROR: ' || SQLERRM);
    
      --Envia correo de ERROR
      error:= 'ERROR AL GRABAR AJUSTE AUTOMÁTICO - '||SQLERRM||'.' ;
      P_EMAIL_ERROR_AJUSTE(vLocal, v_SYSDATE, error);
    
      ROLLBACK; -- roolback por cualquier caida.
    
      RAISE_APPLICATION_ERROR(-20000,
                              CHR(13) ||
                              'ERROR AL GRABAR AJUSTE AUTOMÁTICO ' ||
                              SQLERRM);
  END;

  -- Creación           : RHERRERA
  -- Motivo             : Actualizar o eliminar el ajuste automatico del producto cuando ocurra un
  --                      ajuste realizado por el usuario del FarmaVenta.
  -- Fecha              : 25.09.2014
  -- Requerimiento      : Ajuste Autoático de Insumos
  -- Jefe Requriemiento : JOLIVA
  -- Usuario interesado : PYOVERA
  ------------------------------------------------
  -- Usu y Fecha Modific.       :
  -- Motivo                     :
  PROCEDURE P_LIBERAR_AJUSTE(cCodLocaL CHAR, cCodProd CHAR) AS
  
  BEGIN
  
    DELETE FROM TMP_LGT_AJUSTE_AUTO P
     WHERE P.COD_LOCAL = cCodLocaL
       AND P.COD_PROD = cCodProd
    --AND      P.IND_PROCESADO =  VALOR_NO
    --AND      P.FEC_PROCESADO is null
    ;
  
    /*
      UPDATE TMP_LGT_AJUSTE_AUTO P
         SET P.IND_PROCESADO = VALOR_SI, P.FEC_PROCESADO = v_SYSDATE
       WHERE P.COD_LOCAL = cCodLocaL
         AND P.COD_PROD = cCodProd
         AND P.IND_PROCESADO = VALOR_NO
         AND P.FEC_PROCESADO is null;
    */
  
    COMMIT;
  
  EXCEPTION
    WHEN OTHERS THEN
      -- DBMS_OUTPUT.put_line('ERROR: ' || SQLERRM);
      error:= 'ERROR AL LIBERAR EL AJUSTE AUTOMÁTICO  - '||SQLERRM||'.' ;
      P_EMAIL_ERROR_AJUSTE(cCodLocaL, v_SYSDATE, error);
      
      ROLLBACK; -- roolback por cualquier caida.
      RAISE_APPLICATION_ERROR(-20000,
                              CHR(13) ||
                              'ERROR AL LIBERAR EL AJUSTE AUTOMÁTICO ' ||
                              SQLERRM);
  END;

  PROCEDURE P_EMAIL_CONSUMO_AUTO(cCodLocal  char,
                                 vFecProce  CHAR,
                                 COD_PROD_T TAB_COD_PROD) AS
    mesg_cab    VARCHAR2(32767);
    mesg_body_c VARCHAR2(32767);
    mesg_body   VARCHAR2(32767);
    mesg_body_f VARCHAR2(32767);
    msg         VARCHAR2(1000);
    stock_ante  NUMBER(6);
    vDesProd    VARCHAR2(120);
    vDesLocal   VARCHAR2(120);
    vFecEmail   VARCHAR2(20);
    v_mail      pbl_local.mail_local%type;
  
    CURSOR vListaProd(CodLocal char, FecProce CHAR, cCodProd char) IS
      SELECT A.COD_PROD,
             A.STK_FISICO_LOCAL,
             TRUNC(A.CANT_AJUSTE) CANT_AJUSTE,
             A.VAL_FRAC
        FROM TMP_LGT_AJUSTE_AUTO A
       WHERE A.COD_LOCAL = CodLocal
         AND A.COD_PROD = cCodProd
         AND A.FEC_AJUSTE_DIA = FecProce;
  
  BEGIN
  
    SELECT P.Desc_Local, TO_CHAR(v_SYSDATE, 'DD/MM/YYYY HH24:MI')
      INTO vDesLocal, vFecEmail
      FROM PBL_LOCAL P
     WHERE P.COD_LOCAL = cCodLocal;
  
    --PRODUCTO
    mesg_cab := mesg_cab ||
                '<br><table style="text-align: left; width: 97%;" border="1"';
    mesg_cab := mesg_cab || ' cellpadding="2" cellspacing="1">';
    mesg_cab := mesg_cab || '  <tbody>';
    mesg_cab := mesg_cab || '    <tr>';
    mesg_cab := mesg_cab || '      <th><small>CODIGO</small></th>';
    mesg_cab := mesg_cab || '      <th><small>DESC LOCAL</small></th>';
    mesg_cab := mesg_cab || '      <th><small>FECHA </small></th>';
    mesg_cab := mesg_cab || '      <th><small>MOTIVO</small></th>';
    mesg_cab := mesg_cab || '    </tr>';
  
    mesg_cab := mesg_cab || '   <tr>' || '      <td align = center><small>' ||
                cCodLocal || '</small></td>' ||
                '      <td align = center><small>' || vDesLocal ||
                '</small></td>' || '      <td align = center><small>' ||
                vFecEmail || '</small></td>' ||
                '      <td align = center><small>' || MOT_AJUSTE_AUTO ||
                '</small></td>' || '   </tr>';
  
    mesg_cab := mesg_cab || '  </tbody>';
    mesg_cab := mesg_cab || '</table>';
    mesg_cab := mesg_cab || '<br>';
  
    mesg_body_c := mesg_body_c ||
                   '<table style="text-align: left; width: 97%;" border="1"';
    mesg_body_c := mesg_body_c || ' cellpadding="2" cellspacing="1">';
    mesg_body_c := mesg_body_c || '  <tbody>';
    mesg_body_c := mesg_body_c || '    <tr>';
    mesg_body_c := mesg_body_c ||
                   '      <th><small>FECHA AJUSTE</small></th>';
    mesg_body_c := mesg_body_c || '      <th><small>CODIGO</small></th>';
    mesg_body_c := mesg_body_c ||
                   '      <th><small>DESCRIPCION PRODUCTO</small></th>';
    mesg_body_c := mesg_body_c ||
                   '      <th><small>STK ANTERIOR</small></th>';
    mesg_body_c := mesg_body_c ||
                   '      <th><small>CANT AJUSTADO</small></th>';
    mesg_body_c := mesg_body_c ||
                   '      <th><small>STK FISICO</small></th>';
    mesg_body_c := mesg_body_c ||
                   '      <th><small>VAL FRACCION</small></th>';
    mesg_body_c := mesg_body_c || '      <th><small>USUARIO</small></th>';
    mesg_body_c := mesg_body_c || '    </tr>';
  
    FOR i IN 1 .. COD_PROD_T.COUNT LOOP
      FOR cprod IN vListaProd(cCodLocal, vFecProce, COD_PROD_T(I)) LOOP
        stock_ante := cprod.cant_ajuste + cprod.stk_fisico_local;
        select l.desc_prod
          into vDesProd
          from LGT_AJUSTE_AUTO L
         WHERE L.COD_PROD = cprod.Cod_Prod
           AND L.EST_PROD = ESTADO_ACTIVO;
      
        /*      mesg_body := mesg_body||'<table style="text-align: left; width: 97%;" border="1"';
        mesg_body:= mesg_body||' cellpadding="2" cellspacing="1">';
        mesg_body := mesg_body||'  <tbody>';  */
      
        mesg_body := mesg_body || '   <tr>' ||
                     '      <td align = center><small>' || vFecProce ||
                     '</small></td>' || '      <td align = center><small>' ||
                     cprod.Cod_Prod || '</small></td>' ||
                     '      <td align = left  ><small>' || vDesProd ||
                     '</small></td>' || '      <td align = center><small>' ||
                     stock_ante || '</small></td>' ||
                     '      <td align = center><small>' ||
                     cprod.cant_ajuste || '</small></td>' ||
                     '      <td align = center><small>' ||
                     cprod.stk_fisico_local || '</small></td>' ||
                     '      <td align = center><small>' || cprod.val_frac ||
                     '</small></td>' || '      <td align = center><small>' ||
                     USU_CONSUMO_AUTO || '</small></td>' || '   </tr>';
        /* mesg_body := mesg_body||'  </tbody>';
        mesg_body := mesg_body||'</table>';
        mesg_body := mesg_body||'';    */
      
      END LOOP;
    END LOOP;
    mesg_body_f := mesg_body_f || '  </tbody>';
    mesg_body_f := mesg_body_f || '</table>';
    mesg_body_f := mesg_body_f || '';
  
    select mail_local
      into v_mail
      from pbl_local x
     where x.cod_local = cCodLocal;
  
    msg := 'Estimado Jefe de Local:' || chr(13) ||
           'debe realizar inventario físico de los siguientes insumos, ' ||
           'para ello debe ingresar al sistema la cantidad de stock físico del local.';
  
    -- msg := 'ALERTA PRUEBA';
    FARMA_EMAIL.envia_correo('ALERTA' || FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             v_mail || ',' || v_usu_cargo, --'desarrollo4', -- --PARA
                             'Ajuste Automatico - ' || cCodLocal, --'VIAJERO EXITOSO: '||v_vDescLocal,
                             msg,
                             --'AJUSTE AUTOMÁTICO', --'EXITO',
                             mesg_cab || mesg_body_c || mesg_body ||
                             mesg_body_f,
                             v_correo, --CC
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);
  
  END;

  PROCEDURE P_UPDATE_CANT_AJUSTE(cCodLocal char, cCodProd char) IS
    i            integer := 0;
    cant         integer := 0;
    vDescProd    LGT_AJUSTE_AUTO.Desc_Prod%type;
    vuniDescProd LGT_AJUSTE_AUTO.Desc_Unid_Present%type;
    vCantMesMax  number;
    vMaxValor    number;
    vCantMes     number;
    vUnidadPvm   number;
  BEGIN
  
    --verifica si el producto esta en activo y pertece al mes anterior
    --Esto con el fin de 1 vez realizar esta operación
    SELECT count(*)
      into i
      FROM LGT_AJUSTE_AUTO X
     WHERE TO_CHAR(NVL(X.Fec_Mod, x.fec_crea), 'YYYYMM') =
           TO_CHAR(ADD_MONTHS(TRUNC(v_SYSDATE, 'MM'), -1), 'YYYYMM')
       AND x.Est_Prod = ESTADO_ACTIVO
       AND X.COD_LOCAL = cCodLocal
       AND X.COD_PROD = cCodProd;
  
    -- Obtiene los datos de descripción del producto
    SELECT x.desc_prod, x.desc_unid_present
      into vDescProd, vuniDescProd
      FROM LGT_AJUSTE_AUTO X
     WHERE x.Est_Prod = ESTADO_ACTIVO
       AND X.COD_LOCAL = cCodLocal
       AND X.COD_PROD = cCodProd;
  
    -- Obtiene la cantidad del PVM vigente del producto
    BEGIN
      SELECT nvl(ROUND(L.TOT_UNID_VTA_RDM, 2), -1)
        into vUnidadPvm
        FROM LGT_PARAM_PROD_LOCAL L
       WHERE L.COD_LOCAL = cCodLocal
         AND L.COD_PROD = cCodProd
         AND L.IND_AUTORIZADO = VALOR_SI
         AND TRUNC(L.IND_ACTIVO) >= TRUNC(v_SYSDATE); --TO_CHAR(v_SYSDATE, 'DD/MM/YYYY');
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        vUnidadPvm := -1;
    END;
    -- obtiene el ultimo correlativo y le aumenta +1
    select nvl(max(a.SEC_AJUSTE), 0) + 1
      into cant
      from LGT_AJUSTE_AUTO a
     where a.cod_local = cCodLocal
       and a.cod_prod = cCodProd;
  
    --Obtiene la cantidad mensual ajustar (Valor entero)
    select ROUND(a.cant_mov_mes, 2) --CEIL(a.cant_mov_mes / cValFrac)
      into vCantMes
      from LGT_AJUSTE_AUTO a
     where a.cod_local = cCodLocal
       and a.cod_prod = cCodProd
       and a.est_prod = ESTADO_ACTIVO;
  
    --Sí el producto se encuentra activo, en un mes anterior.
    if ((i > vcero AND vUnidadPvm = -1) OR vUnidadPvm = -1) then
      --Luego obtiene la cantidad maxima entre los ultimos 4 meses atras
      select ROUND(GREATEST(NVL(MFP.UND_VTA_MES_0, 0),
                            NVL(MFP.UND_VTA_MES_1, 0),
                            NVL(MFP.UND_VTA_MES_2, 0),
                            NVL(MFP.UND_VTA_MES_3, 0),
                            NVL(MFP.UND_VTA_MES_4, 0)),
                   2)
        into vCantMesMax
        from MF_LGT_PROD_LOCAL_REP MFP, LGT_PROD P
       WHERE P.COD_PROD = MFP.COD_PROD_MF
         AND P.COD_GRUPO_REP = cCodGrupoInsumo
         AND MFP.COD_LOCAL_MF = cCodLocal
         AND MFP.COD_PROD_MF = cCodProd;
    
      -- valida si la mejor cantidad mensual es mayor a la
      -- cantidad de ajuste mensual asignado al inicio.
      select GREATEST(vCantMes, vCantMesMax) into vMaxValor from dual;
    
      --Luego inserto un nuevo registro con las cantidades obtenidas.
      if vCantMes <> vMaxValor then
        P_INSERT_AJUSTE_AUTO(cCodLocal,
                             cCodProd,
                             vDescProd,
                             vMaxValor,
                             vuniDescProd,
                             cant);
      end if;
    
    elsif vUnidadPvm >= vcero and vUnidadPvm <> vCantMes then
      --caso contrario regreso.
      --Luego inserto un nuevo registro con las cantidades obtenidas.
      P_INSERT_AJUSTE_AUTO(cCodLocal,
                           cCodProd,
                           vDescProd,
                           vUnidadPvm,
                           vuniDescProd,
                           cant);
    end if;
  
  END;

  PROCEDURE P_INSERT_AJUSTE_AUTO(cCodLocal    CHAR,
                                 cCodProd     CHAR,
                                 vDescProd    VARCHAR2,
                                 vMaxValor    NUMBER,
                                 vuniDescProd VARCHAR2,
                                 cant         NUMBER default 1) IS
    valor integer := 0;
  
  BEGIN
  
    select count(*)
      into valor
      from LGT_AJUSTE_AUTO p
     where p.cod_local = cCodLocal
       and p.cod_prod = cCodProd;
  
    if valor > vcero and cant = vseciniajuste then
      return;
    else
    
      INSERT INTO LGT_AJUSTE_AUTO
        (COD_LOCAL, --1
         COD_PROD, --2
         SEC_AJUSTE, --3
         DESC_PROD, --4
         CANT_MOV_MES, --5
         desc_unid_present, --6
         FEC_CREA, --7
         USU_CREA --8
         
         )
      VALUES
        (cCodLocal, --1
         cCodProd, --2
         cant, --3
         vDescProd, --4
         vMaxValor, --5
         vuniDescProd, --6
         v_SYSDATE, --7
         USU_CONSUMO_AUTO --8
         );
      COMMIT;
    
      if cant > 1 then
        -- Ahora actualizo el registro con la cantidad antigua.
        UPDATE LGT_AJUSTE_AUTO L
           SET l.fec_mod  = v_SYSDATE,
               l.usu_mod  = USU_CONSUMO_AUTO,
               l.est_prod = ESTADO_ACTIVO_NO
         WHERE l.cod_local = cCodLocal
           AND l.cod_prod = cCodProd
           AND l.sec_ajuste = cant - 1
           AND L.EST_PROD = ESTADO_ACTIVO;
      end if;
    
    end if;
  
  END;

  PROCEDURE P_EMAIL_ERROR_AJUSTE(cCodLocal char,
                                 vFecProce CHAR,
                                 error     varchar2) AS
  
    msg VARCHAR2(32767);
  
  BEGIN
  
    msg := 'Error: ' || error || '.' || chr(13) || 'Verificar ';
  
    -- msg := 'ALERTA PRUEBA';
    FARMA_EMAIL.envia_correo('ALERTA ERROR' ||
                             FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             'desarrollo4', -- --PARA
                             'AJUSTE AUTOMATICO - ' || cCodLocal, --'VIAJERO EXITOSO: '||v_vDescLocal,
                             'ERROR al momento de realizar el ajuste automático en local ' ||
                             cCodLocal || chr(13) || 'Fecha: ' || vFecProce,
                             --'AJUSTE AUTOMÁTICO', --'EXITO',
                             msg,
                             '', --CC
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);
  
  END;

END; --FIN DEL PACKAGE

/
