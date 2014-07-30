--------------------------------------------------------
--  DDL for Package Body PTOVENTA_REPLICA_FID_LOCAL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_REPLICA_FID_LOCAL" IS

  /************************************/
  PROCEDURE SP_INICIA_REPLICA_LOCAL(COD_GRUPO_CIA_IN IN CHAR DEFAULT '001') IS
    CURSOR cursor_local IS
    SELECT DISTINCT(COD_LOCAL) COD_LOCAL FROM VTA_IMPR_IP;
/*     SELECT COD_LOCAL
      FROM PBL_LOCAL P
      WHERE COD_GRUPO_CIA = COD_GRUPO_CIA_IN
            AND EST_LOCAL = 'A'
            AND TIP_LOCAL = 'V'
            and P.IND_EN_LINEA='S'
            and p.est_config_local in ('2','3')
--            and p.cod_local in ('001','142','154','051')
--            and p.cod_local in ('022','024')
      ORDER BY 1;*/
  BEGIN

  DBMS_OUTPUT.PUT_LINE('TRAE--');
  FOR C_LOC IN cursor_local
  LOOP
    --TRAE
    BEGIN
      DBMS_OUTPUT.PUT_LINE(C_LOC.COD_LOCAL);
      SP_LLEVA_LOCALES(COD_GRUPO_CIA_IN,C_LOC.COD_LOCAL);
      COMMIT;
      EXCEPTION
      WHEN OTHERS THEN
      ROLLBACK;
--       DBMS_OUTPUT.put_line('TRAE:' ||C_LOC.COD_LOCAL||' '||SQLERRM);
       DBMS_OUTPUT.put_line('1'||SQLERRM);
    END;

  END LOOP;

 /*
  DBMS_OUTPUT.PUT_LINE('LLEVA--');
  FOR C_LOC IN cursor_local
  LOOP
    --LLEVA
    BEGIN

      SP_LLEVA_LOCALES(COD_GRUPO_CIA_IN,C_LOC.COD_LOCAL);
      DBMS_OUTPUT.PUT_LINE(C_LOC.COD_LOCAL);
      COMMIT;
      EXCEPTION
      WHEN OTHERS THEN
      ROLLBACK;
--       DBMS_OUTPUT.put_line('LLEVA:' ||C_LOC.COD_LOCAL||' '||SQLERRM);
       DBMS_OUTPUT.put_line('2'||SQLERRM);
    END;
  END LOOP;
  */
  END;

  /************************************/
  PROCEDURE SP_TRAE_LOCALES(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
  BEGIN
     SP_GET_TABLA_1(COD_GRUPO_CIA_IN,COD_LOCAL_IN);
     SP_GET_TABLA_2(COD_GRUPO_CIA_IN,COD_LOCAL_IN);
     SP_GET_TABLA_3(COD_GRUPO_CIA_IN,COD_LOCAL_IN);
     SP_GET_TABLA_4(COD_GRUPO_CIA_IN,COD_LOCAL_IN);
     SP_GET_TABLA_5(COD_GRUPO_CIA_IN,COD_LOCAL_IN);
  END;

  /************************************/
  PROCEDURE SP_LLEVA_LOCALES(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
  BEGIN
     SP_SET_TABLA_2(COD_GRUPO_CIA_IN,COD_LOCAL_IN);  --cliente
     SP_SET_TABLA_1(COD_GRUPO_CIA_IN,COD_LOCAL_IN);  --fid_tarjeta
     SP_SET_TABLA_3(COD_GRUPO_CIA_IN,COD_LOCAL_IN);
     SP_SET_TABLA_4(COD_GRUPO_CIA_IN,COD_LOCAL_IN);
     SP_SET_TABLA_5(COD_GRUPO_CIA_IN,COD_LOCAL_IN);
     SP_SET_TABLA_6(COD_GRUPO_CIA_IN,COD_LOCAL_IN);
  END;

  /************************************/
  PROCEDURE SP_GET_TABLA_1(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
   sql_Text VARCHAR2(32500) := '';
  BEGIN

      -- 1.
      ----*** fid_tarjeta
      --INSERTAR DE LOCAL EN MATRIZ
  sql_Text := 'INSERT INTO fid_tarjeta ';
  sql_Text := sql_Text||' (cod_tarjeta, dni_cli, cod_local, usu_crea_tarjeta, fec_crea_tarjeta, usu_mod_tarjeta, fec_mod_tarjeta, cod_grupo_cia )';
  sql_Text := sql_Text||' SELECT cod_tarjeta, dni_cli, cod_local, usu_crea_tarjeta, fec_crea_tarjeta,'''||V_USER||''', SYSDATE, '''||COD_GRUPO_CIA_IN||'''';
  --sql_Text := sql_Text||' SELECT SUBSTR(cod_tarjeta,1,10), dni_cli, cod_local, usu_crea_tarjeta, fec_crea_tarjeta,'''||V_USER||''', SYSDATE, '''||COD_GRUPO_CIA_IN||'''' ;
  sql_Text := sql_Text||' FROM fid_tarjeta@XE_'||COD_LOCAL_IN;
  sql_Text := sql_Text||' WHERE DNI_CLI LIKE ''TU%''';
  sql_Text := sql_Text||' AND LENGTH(TRIM(COD_TARJETA)) > 13';
  sql_Text := sql_Text||' AND TRIM(COD_TARJETA)||TRIM(DNI_CLI) IN';
  sql_Text := sql_Text||' (';
          --VEO DIFERENCIAS LOCAL CON MATRIZ
  sql_Text := sql_Text||' SELECT TRIM(cod_tarjeta)||TRIM(dni_cli)';
  sql_Text := sql_Text||' FROM fid_tarjeta@XE_'||COD_LOCAL_IN;
  sql_Text := sql_Text||' WHERE DNI_CLI LIKE ''TU%''';
  sql_Text := sql_Text||' AND LENGTH(TRIM(COD_TARJETA)) > 13';
  sql_Text := sql_Text||' MINUS ';
  sql_Text := sql_Text||' SELECT TRIM(cod_tarjeta)||TRIM(dni_cli)';
  sql_Text := sql_Text||' FROM fid_tarjeta';
  sql_Text := sql_Text||' WHERE DNI_CLI LIKE ''TU%''';
  sql_Text := sql_Text||' AND LENGTH(TRIM(COD_TARJETA)) > 13';
  sql_Text := sql_Text||' )';

     --DBMS_OUTPUT.PUT_LINE(sql_Text);
   execute immediate sql_Text;
/*
   EXCEPTION
   WHEN OTHERS THEN
   DBMS_OUTPUT.PUT_LINE(sql_Text);
   DBMS_OUTPUT.PUT_LINE(SQLERRM);
*/
  END;

  /************************************/
  PROCEDURE SP_GET_TABLA_2(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
   sql_Text VARCHAR2(32500) := '';
  BEGIN


        -- 2.
      ----*** PBL_CLIENTE

      -- INSERTAR DE LOCAL A MATRIZ
  sql_Text := 'INSERT INTO PBL_CLIENTE';
  sql_Text := sql_Text||' (dni_cli, nom_cli, ape_pat_cli, ape_mat_cli, fono_cli, sexo_cli, dir_cli, ';
  sql_Text := sql_Text||' fec_nac_cli, fec_crea_cliente, usu_crea_cliente, fec_mod_cliente, usu_mod_cliente, ';
  sql_Text := sql_Text||' ind_estado, email, cod_local_origen, cell_cli, cod_tip_documento, id_usu_confir, ';
  sql_Text := sql_Text||' cod_local_confir, ip_confir, COD_GRUPO_CIA)';
  sql_Text := sql_Text||' SELECT dni_cli, nom_cli, ape_pat_cli, ape_mat_cli, fono_cli, sexo_cli, dir_cli, ';
  sql_Text := sql_Text||' fec_nac_cli, fec_crea_cliente, usu_crea_cliente, SYSDATE, '''||V_USER||''',';
  sql_Text := sql_Text||' ind_estado, email, cod_local_origen, cell_cli, cod_tip_documento, id_usu_confir, ';
  sql_Text := sql_Text||' cod_local_confir, ip_confir, '''||COD_GRUPO_CIA_IN||'''';
  sql_Text := sql_Text||' FROM pbl_cliente@XE_'||COD_LOCAL_IN;
  sql_Text := sql_Text||' WHERE DNI_CLI IN';
      --VEO DIFERENCIAS LOCAL CON MATRIZ
  sql_Text := sql_Text||' (';
  sql_Text := sql_Text||' SELECT trim(DNI_CLI)';
  sql_Text := sql_Text||' FROM PBL_CLIENTE@XE_'||COD_LOCAL_IN;
  sql_Text := sql_Text||' WHERE DNI_CLI LIKE ''TU%''';
  sql_Text := sql_Text||' MINUS';
  sql_Text := sql_Text||' SELECT TRIM(DNI_CLI)';
  sql_Text := sql_Text||' FROM PBL_CLIENTE';
  sql_Text := sql_Text||' WHERE COD_GRUPO_CIA = '''||COD_GRUPO_CIA_IN||'''';
  sql_Text := sql_Text||' AND DNI_CLI LIKE ''TU%''';
  sql_Text := sql_Text||' )';

     --DBMS_OUTPUT.PUT_LINE(sql_Text);
   execute immediate sql_Text;
  END;

  /************************************/
  PROCEDURE SP_GET_TABLA_3(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
   sql_Text VARCHAR2(32500) := '';
  BEGIN

      -- 3.
      ----*** VTA_CAMP_X_TARJETA

      -- INSERTAR DE LOCAL A MATRIZ
      sql_Text := 'INSERT INTO VTA_CAMP_X_TARJETA';
      sql_Text := sql_Text||' (cod_grupo_cia, cod_camp_cupon, tarjeta_ini, tarjeta_fin, ';
      sql_Text := sql_Text||' usu_crea_camp_x_tarj, fec_crea_camp_x_tarj, usu_mod__camp_x_tarj, fec_mod__camp_x_tarj)';
      sql_Text := sql_Text||' SELECT cod_grupo_cia, cod_camp_cupon, tarjeta_ini, tarjeta_fin, ';
      --sql_Text := sql_Text||' SELECT cod_grupo_cia, cod_camp_cupon, SUBSTR(tarjeta_ini,1,13), SUBSTR(tarjeta_fin,1,13), ';
      sql_Text := sql_Text||' usu_crea_camp_x_tarj, fec_crea_camp_x_tarj, '''||V_USER||''', SYSDATE ';
      sql_Text := sql_Text||' FROM VTA_CAMP_X_TARJETA@XE_'||COD_LOCAL_IN;
      sql_Text := sql_Text||' WHERE COD_CAMP_CUPON = ''A0255''';
      sql_Text := sql_Text||' AND TARJETA_INI IN (SELECT COD_TARJETA';
      sql_Text := sql_Text||' FROM FID_TARJETA@XE_'||COD_LOCAL_IN||' WHERE COD_GRUPO_CIA = '''||COD_GRUPO_CIA_IN||''' AND DNI_CLI LIKE ''TU%'')';
      sql_Text := sql_Text||' AND TARJETA_FIN IN (SELECT COD_TARJETA ';
      sql_Text := sql_Text||' FROM FID_TARJETA@XE_'||COD_LOCAL_IN||' WHERE COD_GRUPO_CIA = '''||COD_GRUPO_CIA_IN||''' AND DNI_CLI LIKE ''TU%'')';
      sql_Text := sql_Text||' AND cod_camp_cupon||tarjeta_ini||tarjeta_fin IN ';
      --VEO DIFERENCIAS LOCAL CON MATRIZ
      sql_Text := sql_Text||' (';
      sql_Text := sql_Text||' SELECT cod_camp_cupon||tarjeta_ini||tarjeta_fin';
      sql_Text := sql_Text||' FROM VTA_CAMP_X_TARJETA@XE_'||COD_LOCAL_IN;
      sql_Text := sql_Text||' WHERE COD_CAMP_CUPON = ''A0255''';
      sql_Text := sql_Text||' AND TARJETA_INI IN (SELECT COD_TARJETA';
      sql_Text := sql_Text||' FROM FID_TARJETA@XE_'||COD_LOCAL_IN||' WHERE COD_GRUPO_CIA = '''||COD_GRUPO_CIA_IN||''' AND DNI_CLI LIKE ''TU%'')';
      sql_Text := sql_Text||' AND TARJETA_FIN IN (SELECT COD_TARJETA';
      sql_Text := sql_Text||' FROM FID_TARJETA@XE_'||COD_LOCAL_IN||' WHERE COD_GRUPO_CIA = '''||COD_GRUPO_CIA_IN||''' AND DNI_CLI LIKE ''TU%'')';
      sql_Text := sql_Text||' MINUS';
      sql_Text := sql_Text||' SELECT cod_camp_cupon||tarjeta_ini||tarjeta_fin';
      sql_Text := sql_Text||' FROM VTA_CAMP_X_TARJETA';
      sql_Text := sql_Text||' WHERE COD_CAMP_CUPON = ''A0255''';
      sql_Text := sql_Text||' AND TARJETA_INI IN (SELECT COD_TARJETA ';
      sql_Text := sql_Text||' FROM FID_TARJETA WHERE COD_GRUPO_CIA = '''||COD_GRUPO_CIA_IN||''' AND DNI_CLI LIKE ''TU%'')';
      sql_Text := sql_Text||' AND TARJETA_FIN IN (SELECT COD_TARJETA';
      sql_Text := sql_Text||' FROM FID_TARJETA WHERE COD_GRUPO_CIA = '''||COD_GRUPO_CIA_IN||''' AND DNI_CLI LIKE ''TU%'')';
      sql_Text := sql_Text||' ) ';

     --DBMS_OUTPUT.PUT_LINE(sql_Text);
   execute immediate sql_Text;
  END;

  /************************************/
  PROCEDURE SP_GET_TABLA_4(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
   sql_Text VARCHAR2(32500) := '';
  BEGIN

    -- 4.
    ----*** PBL_DCTO_X_DNI
    -- INSERTAR DE LOCAL A MATRIZ
    sql_Text := 'INSERT INTO PBL_DCTO_X_DNI';
    sql_Text := sql_Text||' (dni_cli, max_dcto, fec_crea, usu_crea, cod_grupo_cia)';
    sql_Text := sql_Text||' SELECT dni_cli, max_dcto, fec_crea, usu_crea, '''||COD_GRUPO_CIA_IN||'''';
    sql_Text := sql_Text||' FROM pbl_dcto_x_dni@XE_'||COD_LOCAL_IN||' D';
    sql_Text := sql_Text||' WHERE D.DNI_CLI LIKE ''TU%''';
    sql_Text := sql_Text||' AND D.DNI_CLI IN';
    sql_Text := sql_Text||' (';
    sql_Text := sql_Text||' SELECT dni_cli';
    sql_Text := sql_Text||' FROM pbl_dcto_x_dni@XE_'||COD_LOCAL_IN||' D';
    sql_Text := sql_Text||' WHERE D.DNI_CLI LIKE ''TU%''';
    sql_Text := sql_Text||' MINUS';
    sql_Text := sql_Text||' SELECT dni_cli';
    sql_Text := sql_Text||' FROM pbl_dcto_x_dni D';
    sql_Text := sql_Text||' WHERE D.COD_GRUPO_CIA = ''001''';
    sql_Text := sql_Text||' AND D.DNI_CLI LIKE ''TU%''';
    sql_Text := sql_Text||' )';

     --DBMS_OUTPUT.PUT_LINE(sql_Text);
   execute immediate sql_Text;
  END;

  /************************************/
  PROCEDURE SP_GET_TABLA_5(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
   sql_Text VARCHAR2(32500) := '';
  BEGIN
    -- 5.
    ----*** PBL_DCTO_X_DNI
    -- INSERTAR DE LOCAL A MATRIZ
    sql_Text := 'INSERT INTO vta_ped_dcto_cli_aux';
    sql_Text := sql_Text||' (cod_grupo_cia, cod_local, num_ped_vta, val_dcto_vta, dni_cliente, fec_crea_ped_vta_cab )';
    sql_Text := sql_Text||' SELECT cod_grupo_cia, cod_local, num_ped_vta, val_dcto_vta, dni_cliente, fec_crea_ped_vta_cab';
    sql_Text := sql_Text||' FROM vta_ped_dcto_cli_aux@XE_'||COD_LOCAL_IN;
    sql_Text := sql_Text||' WHERE DNI_CLIENTE LIKE ''TU%''';
    sql_Text := sql_Text||' AND DNI_CLIENTE IN';
    sql_Text := sql_Text||' ( SELECT dni_cliente';
    sql_Text := sql_Text||' FROM vta_ped_dcto_cli_aux@XE_'||COD_LOCAL_IN;
    sql_Text := sql_Text||' WHERE DNI_CLIENTE LIKE ''TU%''';
    sql_Text := sql_Text||' MINUS';
    sql_Text := sql_Text||' SELECT dni_cliente';
    sql_Text := sql_Text||' FROM vta_ped_dcto_cli_aux';
    sql_Text := sql_Text||' WHERE DNI_CLIENTE LIKE ''TU%''';
    sql_Text := sql_Text||' )';

     --DBMS_OUTPUT.PUT_LINE(sql_Text);
   execute immediate sql_Text;
  END;


  /************************************/
  PROCEDURE SP_SET_TABLA_1(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
   sql_Text VARCHAR2(32500) := '';
   CURSOR CUR_DNI_CHAR IS
   SELECT CHAR_NUEVO_DNI "COD_DNI"
     FROM VTA_CAMP_X_FPAGO_USO
    WHERE CHAR_NUEVO_DNI IS NOT NULL;
  BEGIN

   FOR C_DNI_CHAR IN CUR_DNI_CHAR
    LOOP
    --INSERTAR EN LOCAL DESDE MATRIZ
     sql_Text := sql_Text||' INSERT INTO fid_tarjeta';
     sql_Text := sql_Text||' (cod_tarjeta, dni_cli, cod_local, usu_crea_tarjeta, fec_crea_tarjeta, usu_mod_tarjeta, fec_mod_tarjeta )';
     sql_Text := sql_Text||' SELECT cod_tarjeta, dni_cli, cod_local, usu_crea_tarjeta, fec_crea_tarjeta, ''SP_ACT_LOC'', SYSDATE ';
--     sql_Text := sql_Text||' SELECT SUBSTR(cod_tarjeta,1,13), dni_cli, cod_local, usu_crea_tarjeta, fec_crea_tarjeta, ''SP_ACT_LOC'', SYSDATE ';
     sql_Text := sql_Text||' FROM PTOVENTA.fid_tarjeta@XE_000';
     sql_Text := sql_Text||' WHERE COD_GRUPO_CIA = ''001''';
     sql_Text := sql_Text||' AND DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
     sql_Text := sql_Text||' AND TRIM(COD_TARJETA)||TRIM(DNI_CLI) IN';
     sql_Text := sql_Text||' (';
        --VEO DIFERENCIAS MATRIZ CON LOCAL
     sql_Text := sql_Text||' SELECT TRIM(COD_TARJETA)||TRIM(DNI_CLI)';
     sql_Text := sql_Text||' FROM PTOVENTA.fid_tarjeta@XE_000';
     sql_Text := sql_Text||' WHERE COD_GRUPO_CIA = ''001''';
--     sql_Text := sql_Text||' AND DNI_CLI LIKE ''TU%''';
     sql_Text := sql_Text||' AND DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
     sql_Text := sql_Text||' AND LENGTH(TRIM(COD_TARJETA)) > 13';
     sql_Text := sql_Text||' MINUS';
     sql_Text := sql_Text||' SELECT TRIM(COD_TARJETA)||TRIM(DNI_CLI)';
     sql_Text := sql_Text||' FROM FID_TARJETA';
     --sql_Text := sql_Text||' WHERE DNI_CLI LIKE ''TU%''';
     sql_Text := sql_Text||' WHERE DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
     sql_Text := sql_Text||' AND LENGTH(TRIM(COD_TARJETA)) > 13';
     sql_Text := sql_Text||' )';

--     DBMS_OUTPUT.PUT_LINE(sql_Text);
     execute immediate sql_Text;
     sql_Text := '';
    END LOOP;
  END;

  /************************************/
  PROCEDURE SP_SET_TABLA_2(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
   sql_Text VARCHAR2(32500) := '';

   CURSOR CUR_DNI_CHAR IS
   SELECT CHAR_NUEVO_DNI "COD_DNI"
     FROM VTA_CAMP_X_FPAGO_USO
    WHERE CHAR_NUEVO_DNI IS NOT NULL;
  BEGIN

   FOR C_DNI_CHAR IN CUR_DNI_CHAR
    LOOP
    -- INSERTAR EN LOCAL DIFERENCIAS DE MATRIZ
    sql_Text := ' INSERT INTO PBL_CLIENTE';
    sql_Text := sql_Text||' (dni_cli, nom_cli, ape_pat_cli, ape_mat_cli, fono_cli, sexo_cli, dir_cli,';
    sql_Text := sql_Text||' fec_nac_cli, fec_crea_cliente, usu_crea_cliente, fec_mod_cliente, usu_mod_cliente,';
    sql_Text := sql_Text||' ind_estado, email, cod_local_origen, cell_cli, cod_tip_documento, id_usu_confir,';
    sql_Text := sql_Text||' cod_local_confir, ip_confir)';
    sql_Text := sql_Text||' SELECT dni_cli, nom_cli, ape_pat_cli, ape_mat_cli, fono_cli, sexo_cli, dir_cli,';
    sql_Text := sql_Text||' fec_nac_cli, fec_crea_cliente, usu_crea_cliente, SYSDATE, ''SP_ACT_MAT'',';
    sql_Text := sql_Text||' ind_estado, email, cod_local_origen, cell_cli, cod_tip_documento, id_usu_confir, ';
    sql_Text := sql_Text||' cod_local_confir, ip_confir';
    sql_Text := sql_Text||' FROM PTOVENTA.PBL_CLIENTE@XE_000';
    sql_Text := sql_Text||' WHERE COD_GRUPO_CIA = ''001''';
    sql_Text := sql_Text||' AND DNI_CLI IN';
    --VEO DIFERENCIAS MATRIZ CON LOCAL
    sql_Text := sql_Text||' (';
    sql_Text := sql_Text||' SELECT TRIM(DNI_CLI)';
    sql_Text := sql_Text||' FROM PTOVENTA.PBL_CLIENTE@XE_000';
    sql_Text := sql_Text||' WHERE COD_GRUPO_CIA = ''001''';
--    sql_Text := sql_Text||' AND DNI_CLI LIKE ''TU%''';  --COMENTADO PARA HACERLO GENERAL
    sql_Text := sql_Text||' AND DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
    sql_Text := sql_Text||' MINUS';
    sql_Text := sql_Text||' SELECT trim(DNI_CLI)';
    sql_Text := sql_Text||' FROM PBL_CLIENTE';
--    sql_Text := sql_Text||' WHERE DNI_CLI LIKE ''TU%'''; --COMENTADO PARA HACERLO GENERAL
    sql_Text := sql_Text||' WHERE DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
    sql_Text := sql_Text||' )';
     --DBMS_OUTPUT.PUT_LINE(sql_Text);
    execute immediate sql_Text;
    sql_Text := '';
    END LOOP;

  END;

  /************************************/
  PROCEDURE SP_SET_TABLA_3(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
   sql_Text VARCHAR2(32500) := '';
   CURSOR CUR_DNI_CHAR IS
   SELECT CHAR_NUEVO_DNI "COD_DNI", COD_CAMP_CUPON
     FROM VTA_CAMP_X_FPAGO_USO
    WHERE CHAR_NUEVO_DNI IS NOT NULL;
  BEGIN

   FOR C_DNI_CHAR IN CUR_DNI_CHAR
    LOOP
      -- INSERTAR DE MATRIZ A LOCAL
     sql_Text := ' INSERT INTO VTA_CAMP_X_TARJETA';
     sql_Text := sql_Text||' (cod_grupo_cia, cod_camp_cupon, tarjeta_ini, tarjeta_fin, ';
     sql_Text := sql_Text||' usu_crea_camp_x_tarj, fec_crea_camp_x_tarj, usu_mod__camp_x_tarj, fec_mod__camp_x_tarj)';
     sql_Text := sql_Text||' SELECT cod_grupo_cia, cod_camp_cupon, tarjeta_ini, tarjeta_fin, ';
    -- sql_Text := sql_Text||' SELECT cod_grupo_cia, cod_camp_cupon, SUBSTR(tarjeta_ini,1,13), SUBSTR(tarjeta_fin,1,13), ';
     sql_Text := sql_Text||' usu_crea_camp_x_tarj, fec_crea_camp_x_tarj, ''SP_ACT_LOC'', SYSDATE ';
     sql_Text := sql_Text||' FROM PTOVENTA.VTA_CAMP_X_TARJETA@XE_000';
--     sql_Text := sql_Text||' WHERE COD_CAMP_CUPON = ''A0255''';
     sql_Text := sql_Text||' WHERE COD_CAMP_CUPON = '''||C_DNI_CHAR.COD_CAMP_CUPON||'''';
     sql_Text := sql_Text||' AND TARJETA_INI IN (SELECT COD_TARJETA';
     sql_Text := sql_Text||' FROM PTOVENTA.FID_TARJETA@XE_000 WHERE COD_GRUPO_CIA = ''001'' AND DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%'')';
     sql_Text := sql_Text||' AND TARJETA_FIN IN (SELECT COD_TARJETA';
     sql_Text := sql_Text||' FROM PTOVENTA.FID_TARJETA@XE_000 WHERE COD_GRUPO_CIA = ''001'' AND DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%'')';
     sql_Text := sql_Text||' AND cod_camp_cupon||tarjeta_ini||tarjeta_fin IN';
    --VEO DIFERENCIAS MATRIZ CON LOCAL
     sql_Text := sql_Text||' (';
     sql_Text := sql_Text||' SELECT cod_camp_cupon||tarjeta_ini||tarjeta_fin';
     sql_Text := sql_Text||' FROM PTOVENTA.VTA_CAMP_X_TARJETA@XE_000';
--     sql_Text := sql_Text||' WHERE COD_CAMP_CUPON = ''A0255''';
     sql_Text := sql_Text||' WHERE COD_CAMP_CUPON = '''||C_DNI_CHAR.COD_CAMP_CUPON||'''';
     sql_Text := sql_Text||' AND TARJETA_INI IN (SELECT COD_TARJETA';
     sql_Text := sql_Text||' FROM PTOVENTA.FID_TARJETA@XE_000 WHERE COD_GRUPO_CIA = ''001'' AND DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%'')';
     sql_Text := sql_Text||' AND TARJETA_FIN IN (SELECT COD_TARJETA';
     sql_Text := sql_Text||' FROM PTOVENTA.FID_TARJETA@XE_000 WHERE COD_GRUPO_CIA = ''001'' AND DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%'')';
     sql_Text := sql_Text||' MINUS';
     sql_Text := sql_Text||' SELECT cod_camp_cupon||tarjeta_ini||tarjeta_fin';
     sql_Text := sql_Text||' FROM VTA_CAMP_X_TARJETA';
--     sql_Text := sql_Text||' WHERE COD_CAMP_CUPON = ''A0255''';
     sql_Text := sql_Text||' WHERE COD_CAMP_CUPON = '''||C_DNI_CHAR.COD_CAMP_CUPON||'''';
     sql_Text := sql_Text||' AND TARJETA_INI IN (SELECT COD_TARJETA';
     sql_Text := sql_Text||' FROM FID_TARJETA WHERE DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%'')';
     sql_Text := sql_Text||' AND TARJETA_FIN IN (SELECT COD_TARJETA';
     sql_Text := sql_Text||' FROM FID_TARJETA WHERE DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%'')';
     sql_Text := sql_Text||')';

     --DBMS_OUTPUT.PUT_LINE(sql_Text);
     execute immediate sql_Text;
     sql_Text := '';
    END LOOP;
  END;

  /************************************/
  --PBL_DCTO_X_DNI
  PROCEDURE SP_SET_TABLA_4(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
   sql_Text VARCHAR2(32500) := '';

   CURSOR CUR_DNI_CHAR IS
   SELECT CHAR_NUEVO_DNI "COD_DNI"
     FROM VTA_CAMP_X_FPAGO_USO
    WHERE CHAR_NUEVO_DNI IS NOT NULL;
  BEGIN
   FOR C_DNI_CHAR IN CUR_DNI_CHAR
    LOOP
    -- INSERTAR DE MATRIZ A LOCAL
     sql_Text := ' INSERT INTO PBL_DCTO_X_DNI';
     sql_Text := sql_Text||' (dni_cli, max_dcto, fec_crea, usu_crea)';
     sql_Text := sql_Text||' SELECT dni_cli, max_dcto, fec_crea, usu_crea ';
     sql_Text := sql_Text||' FROM PTOVENTA.pbl_dcto_x_dni@XE_000 D';
     sql_Text := sql_Text||' WHERE D.COD_GRUPO_CIA = ''001''';
     sql_Text := sql_Text||' AND D.DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
     sql_Text := sql_Text||' AND D.DNI_CLI IN';
     sql_Text := sql_Text||' (';
     sql_Text := sql_Text||' SELECT dni_cli';
     sql_Text := sql_Text||' FROM PTOVENTA.pbl_dcto_x_dni@XE_000';
     sql_Text := sql_Text||' WHERE COD_GRUPO_CIA = ''001''';
     sql_Text := sql_Text||' AND DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
     sql_Text := sql_Text||' MINUS';
     sql_Text := sql_Text||' SELECT dni_cli';
     sql_Text := sql_Text||' FROM pbl_dcto_x_dni';
     sql_Text := sql_Text||' WHERE COD_GRUPO_CIA = ''001''';
     sql_Text := sql_Text||' AND DNI_CLI LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
     sql_Text := sql_Text||' )';

     --DBMS_OUTPUT.PUT_LINE(sql_Text);
   execute immediate sql_Text;
   sql_Text := '';
   END LOOP;
  END;

  /************************************/
  --vta_ped_dcto_cli_aux
  PROCEDURE SP_SET_TABLA_5(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
   sql_Text VARCHAR2(32500) := '';
   CURSOR CUR_DNI_CHAR IS
   SELECT CHAR_NUEVO_DNI "COD_DNI"
     FROM VTA_CAMP_X_FPAGO_USO
    WHERE CHAR_NUEVO_DNI IS NOT NULL;

  BEGIN
   FOR C_DNI_CHAR IN CUR_DNI_CHAR
    LOOP
      -- INSERTAR DE MATRIZ A LOCAL
    sql_Text := sql_Text||' INSERT INTO vta_ped_dcto_cli_aux';
    sql_Text := sql_Text||' (cod_grupo_cia, cod_local, num_ped_vta, val_dcto_vta, dni_cliente, fec_crea_ped_vta_cab )';
    sql_Text := sql_Text||' SELECT cod_grupo_cia, cod_local, num_ped_vta, val_dcto_vta, dni_cliente, fec_crea_ped_vta_cab';
    sql_Text := sql_Text||' FROM PTOVENTA.vta_ped_dcto_cli_aux@XE_000';
    sql_Text := sql_Text||' WHERE DNI_CLIENTE LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
    sql_Text := sql_Text||' AND DNI_CLIENTE IN';
    sql_Text := sql_Text||' ( SELECT dni_cliente';
    sql_Text := sql_Text||' FROM PTOVENTA.vta_ped_dcto_cli_aux@XE_000';
    sql_Text := sql_Text||' WHERE DNI_CLIENTE LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
    sql_Text := sql_Text||' MINUS';
    sql_Text := sql_Text||' SELECT dni_cliente';
    sql_Text := sql_Text||' FROM vta_ped_dcto_cli_aux';
    sql_Text := sql_Text||' WHERE DNI_CLIENTE LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
    sql_Text := sql_Text||' )';

    --  DBMS_OUTPUT.PUT_LINE(sql_Text);
    execute immediate sql_Text;
    sql_Text := '';
   END LOOP;
  END;

  /************************************/
  --fid_dni_nulos
  PROCEDURE SP_SET_TABLA_6(COD_GRUPO_CIA_IN IN CHAR, COD_LOCAL_IN IN CHAR) IS
   sql_Text VARCHAR2(32500) := '';
   CURSOR CUR_DNI_CHAR IS
   SELECT CHAR_NUEVO_DNI "COD_DNI"
     FROM VTA_CAMP_X_FPAGO_USO
    WHERE CHAR_NUEVO_DNI IS NOT NULL;

  BEGIN

   FOR C_DNI_CHAR IN CUR_DNI_CHAR
    LOOP
        -- 6.
      ----*** FID_DNI_NULOS
      -- INSERTAR DE MATRIZ A LOCAL
      sql_Text := ' INSERT INTO fid_dni_nulos';
      sql_Text := sql_Text||' (dni_cli, estado, usu_crea_dni_nulo, fec_crea_dni_nulo, usu_mod_dni_nulo, fec_mod_dni_nulo )';
      sql_Text := sql_Text||' SELECT dni_cli, estado, usu_crea_dni_nulo, fec_crea_dni_nulo, ''USU_ACT_MAT'', SYSDATE ';
      sql_Text := sql_Text||' FROM PTOVENTA.fid_dni_nulos@XE_000';
      sql_Text := sql_Text||' WHERE dni_cli LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
      sql_Text := sql_Text||' AND dni_cli IN';
      sql_Text := sql_Text||' (';
      sql_Text := sql_Text||' SELECT dni_cli';
      sql_Text := sql_Text||' FROM PTOVENTA.fid_dni_nulos@XE_000';
      sql_Text := sql_Text||' WHERE dni_cli LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
      sql_Text := sql_Text||' MINUS';
      sql_Text := sql_Text||' SELECT dni_cli';
      sql_Text := sql_Text||' FROM fid_dni_nulos';
      sql_Text := sql_Text||' WHERE dni_cli LIKE '''||C_DNI_CHAR.COD_DNI||'%''';
      sql_Text := sql_Text||' )';

      --DBMS_OUTPUT.PUT_LINE(sql_Text);
      execute immediate sql_Text;
      sql_Text := '';
    END LOOP;
  END;

END PTOVENTA_REPLICA_FID_LOCAL;

/
