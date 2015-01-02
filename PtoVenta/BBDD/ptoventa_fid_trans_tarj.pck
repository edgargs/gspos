CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_FID_TRANS_TARJ" IS
--autor jluna 20080930
 procedure EJECT_TRANS_TARJ_CLI(cCodGrupoCia_in IN CHAR);
 PROCEDURE GET_ORIGEN_TRANS_TARJ(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);
 PROCEDURE set_destino_TRANS_TARJ(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);
 procedure merge_fid_tarjeta;
 procedure merge_pbl_cliente;
END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_FID_TRANS_TARJ" IS

procedure EJECT_TRANS_TARJ_CLI(cCodGrupoCia_in IN CHAR) is
  CURSOR curLocales IS
      SELECT COD_LOCAL
      FROM PBL_LOCAL P
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND EST_LOCAL = 'A'
            AND TIP_LOCAL = 'V'
            and P.IND_EN_LINEA='S'
            and p.est_config_local in ('2','3')
--            and p.cod_local in ('001','003')
      ORDER BY 1;
      v_rCurLocales curLocales%ROWTYPE;
  BEGIN
    execute immediate 'truncate table tmp_fid_tarjeta';
    execute immediate 'truncate table tmp_PBL_CLIENTE';
    FOR v_rCurLocales IN curLocales
    LOOP
      GET_ORIGEN_TRANS_TARJ(cCodGrupoCia_in,v_rCurLocales.COD_LOCAL);
    END LOOP;
    --merge en matriz
    merge_pbl_cliente;
    merge_fid_tarjeta;

    --
    if to_char(sysdate,'hh24mi') between '0800' and '0830' then
--    if (1=1 )then
--    if to_char(sysdate,'hh24mi') between '1140' and '1355' then
      FOR v_rCurLocales IN curLocales
      LOOP
        SET_DESTINO_TRANS_TARJ(cCodGrupoCia_in,v_rCurLocales.COD_LOCAL);
      END LOOP;
    end if;
    commit;
  END;

--------------------
PROCEDURE GET_ORIGEN_TRANS_TARJ(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
  sql_Text varchar2(4000);
  BEGIN
    --TARJETA
    sql_Text:='insert into tmp_fid_tarjeta(cod_tarjeta,dni_cli,cod_local,usu_crea_tarjeta,FEC_CREA_TARJETA,usu_mod_tarjeta,fec_mod_tarjeta)';
    sql_Text:=sql_Text||' select cod_tarjeta,dni_cli,cod_local,usu_crea_tarjeta,FEC_CREA_TARJETA,usu_mod_tarjeta,fec_mod_tarjeta from fid_tarjeta@xe_'||cCodLocal_in||' ';
--    sql_Text:=sql_Text||' where fec_crea_tarjeta>sysdate-3 or fec_mod_tarjeta>sysdate-3 ';
    sql_Text:=sql_Text||' where fec_mod_tarjeta>sysdate-2 ';
    execute immediate sql_Text;
    COMMIT;
    --CLIENTE
    sql_Text:='insert into tmp_pbl_cliente(DNI_CLI,NOM_CLI,APE_PAT_CLI,APE_MAT_CLI,FONO_CLI,SEXO_CLI,DIR_CLI,FEC_NAC_CLI,FEC_CREA_CLIENTE,USU_CREA_CLIENTE,FEC_MOD_CLIENTE,USU_MOD_CLIENTE,IND_ESTADO,cod_local_origen)';
    sql_Text:=sql_Text||' select DNI_CLI,NOM_CLI,APE_PAT_CLI,APE_MAT_CLI,FONO_CLI,SEXO_CLI,DIR_CLI,FEC_NAC_CLI,FEC_CREA_CLIENTE,USU_CREA_CLIENTE,FEC_MOD_CLIENTE,USU_MOD_CLIENTE,IND_ESTADO,cod_local_origen from pbl_cliente@xe_'||cCodLocal_in||' ';
    sql_Text:=sql_Text||' where (fec_crea_CLIENTE>sysdate-1 or fec_mod_CLIENTE>sysdate-1) and cod_local and cod_local_origen='''||cCodLocal_in||'''  ';
    execute immediate sql_Text;
    COMMIT;
  EXCEPTION
   WHEN OTHERS THEN
     NULL;
  END;
----------------
PROCEDURE set_destino_TRANS_TARJ(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
  sql_Text varchar2(4000);
  BEGIN
    --CLIENTE
    sql_Text:='delete TMP_PBL_CLIENTE@XE_'||cCodLocal_in ;
    execute immediate sql_Text;

    sql_Text:='INSERT INTO TMP_PBL_CLIENTE@XE_'||cCodLocal_in||' (DNI_CLI,NOM_CLI,APE_PAT_CLI,APE_MAT_CLI,FONO_CLI,SEXO_CLI,DIR_CLI,FEC_NAC_CLI,FEC_CREA_CLIENTE,USU_CREA_CLIENTE,FEC_MOD_CLIENTE,USU_MOD_CLIENTE,IND_ESTADO)';
    sql_Text:=sql_Text||' select distinct DNI_CLI,NOM_CLI,APE_PAT_CLI,APE_MAT_CLI,FONO_CLI,SEXO_CLI,DIR_CLI,FEC_NAC_CLI,FEC_CREA_CLIENTE,USU_CREA_CLIENTE,FEC_MOD_CLIENTE,USU_MOD_CLIENTE,IND_ESTADO from tmp_PBL_CLIENTE';
    execute immediate sql_Text;
    --merge remoto
    sql_Text:='begin PTOVENTA_FID_TRANS_TARJ.merge_pbl_cliente@XE_'||cCodLocal_in||'; end;' ;
    execute immediate sql_Text;
    COMMIT;
    --TARJETA
    sql_Text:='delete tmp_fid_tarjeta@XE_'||cCodLocal_in ;
    execute immediate sql_Text;

    sql_Text:='INSERT INTO tmp_fid_tarjeta@XE_'||cCodLocal_in||' (cod_tarjeta,dni_cli,cod_local,usu_crea_tarjeta,FEC_CREA_TARJETA,usu_mod_tarjeta,fec_mod_tarjeta)';
    sql_Text:=sql_Text||' select cod_tarjeta,dni_cli,cod_local,usu_crea_tarjeta,FEC_CREA_TARJETA,usu_mod_tarjeta,fec_mod_tarjeta from tmp_fid_tarjeta';
    sql_Text:=sql_Text||' where  COD_LOCAL<>'''||cCodLocal_in||'''  ';
    execute immediate sql_Text;
    --merge remoto
    sql_Text:='begin PTOVENTA_FID_TRANS_TARJ.merge_fid_tarjeta@XE_'||cCodLocal_in||'; end;' ;
    execute immediate sql_Text;
    COMMIT;
  EXCEPTION
   WHEN OTHERS THEN
     dbms_output.put_line('fallo envio datos ');
  END;
----------------
 procedure merge_fid_tarjeta is
 begin
    merge into fid_tarjeta f
    using (SELECT *
           FROM tmp_fid_tarjeta AA
           WHERE AA.FEC_MOD_TARJETA=(SELECT MIN(AAA.FEC_MOD_TARJETA)
                                     FROM tmp_fid_tarjeta AAA
                                     WHERE AAA.COD_TARJETA=AA.COD_TARJETA)
           ) t
    on     (f.cod_tarjeta=t.cod_tarjeta)
    when matched then update set
    f.dni_cli=t.dni_cli,
    f.cod_local=t.cod_local,
    f.usu_crea_tarjeta=t.usu_crea_tarjeta,
    f.fec_crea_tarjeta=t.fec_crea_tarjeta,
    f.usu_mod_tarjeta=t.usu_mod_tarjeta,
    f.fec_mod_tarjeta=t.fec_mod_tarjeta
    when not matched then insert
    (cod_tarjeta,dni_cli,cod_local,usu_crea_tarjeta,fec_crea_tarjeta,usu_mod_tarjeta,fec_mod_tarjeta)
    values (t.cod_tarjeta,t.dni_cli,t.cod_local,t.usu_crea_tarjeta,t.fec_crea_tarjeta,t.usu_mod_tarjeta,t.fec_mod_tarjeta);
 end;
----------------
 procedure merge_pbl_cliente is
 begin
    merge into pbl_cliente f
    using tmp_pbl_cliente  t
    on     (t.DNI_CLI=f.DNI_CLI)
    when matched then update set
      f.NOM_CLI=t.NOM_CLI,
      f.APE_PAT_CLI=t.APE_PAT_CLI,
      f.APE_MAT_CLI=t.APE_MAT_CLI,
      f.FONO_CLI=t.FONO_CLI,
      f.SEXO_CLI=t.SEXO_CLI,
      f.DIR_CLI=t.DIR_CLI,
      f.FEC_NAC_CLI=t.FEC_NAC_CLI,
      f.FEC_CREA_CLIENTE=t.FEC_CREA_CLIENTE,
      f.USU_CREA_CLIENTE=t.USU_CREA_CLIENTE,
      f.FEC_MOD_CLIENTE=t.FEC_MOD_CLIENTE,
      f.USU_MOD_CLIENTE=t.USU_MOD_CLIENTE,
      f.IND_ESTADO=t.IND_ESTADO,
      f.cod_local_origen=t.cod_local_origen,
      f.COD_TIP_DOCUMENTO=t.COD_TIP_DOCUMENTO,
      f.ID_USU_CONFIR=t.ID_USU_CONFIR
     when not matched then insert
    (DNI_CLI,NOM_CLI,APE_PAT_CLI,APE_MAT_CLI,FONO_CLI,SEXO_CLI,DIR_CLI,FEC_NAC_CLI,FEC_CREA_CLIENTE,USU_CREA_CLIENTE,FEC_MOD_CLIENTE,USU_MOD_CLIENTE,IND_ESTADO,cod_local_origen,COD_TIP_DOCUMENTO ,  ID_USU_CONFIR)
    values (t.DNI_CLI,t.NOM_CLI,t.APE_PAT_CLI,t.APE_MAT_CLI,t.FONO_CLI,t.SEXO_CLI,t.DIR_CLI,t.FEC_NAC_CLI,t.FEC_CREA_CLIENTE,t.USU_CREA_CLIENTE,t.FEC_MOD_CLIENTE,t.USU_MOD_CLIENTE,t.IND_ESTADO,t.cod_local_origen,t.COD_TIP_DOCUMENTO ,  t.ID_USU_CONFIR);
    /*
    merge into pbl_cliente f
    using (select  aa.* from tmp_pbl_cliente aa where (cod_local_origen||to_char(nvl(Fec_mod_Cliente,trunc(sysdate,'yyyy')),'yyyymmddhh24miss')||to_char(Fec_Crea_Cliente,'yyyymmddhh24miss'))=
                         (select    max(cod_local_origen||to_char(nvl(Fec_mod_Cliente,trunc(sysdate,'yyyy')),'yyyymmddhh24miss')||to_char(Fec_Crea_Cliente,'yyyymmddhh24miss'))
                                    from tmp_pbl_cliente  d
                                    where d.dni_cli=aa.dni_cli)
           ) t
    on     (f.DNI_CLI=t.DNI_CLI)
    when matched then update set
      f.NOM_CLI=t.NOM_CLI,
      f.APE_PAT_CLI=t.APE_PAT_CLI,
      f.APE_MAT_CLI=t.APE_MAT_CLI,
      f.FONO_CLI=t.FONO_CLI,
      f.SEXO_CLI=t.SEXO_CLI,
      f.DIR_CLI=t.DIR_CLI,
      f.FEC_NAC_CLI=t.FEC_NAC_CLI,
      f.FEC_CREA_CLIENTE=t.FEC_CREA_CLIENTE,
      f.USU_CREA_CLIENTE=t.USU_CREA_CLIENTE,
      f.FEC_MOD_CLIENTE=t.FEC_MOD_CLIENTE,
      f.USU_MOD_CLIENTE=t.USU_MOD_CLIENTE,
      f.IND_ESTADO=t.IND_ESTADO,
      f.cod_local_origen=t.cod_local_origen,
      f.COD_TIP_DOCUMENTO=t.COD_TIP_DOCUMENTO,
      f.ID_USU_CONFIR=t.ID_USU_CONFIR
     when not matched then insert
    (DNI_CLI,NOM_CLI,APE_PAT_CLI,APE_MAT_CLI,FONO_CLI,SEXO_CLI,DIR_CLI,FEC_NAC_CLI,FEC_CREA_CLIENTE,USU_CREA_CLIENTE,FEC_MOD_CLIENTE,USU_MOD_CLIENTE,IND_ESTADO,cod_local_origen,COD_TIP_DOCUMENTO ,  ID_USU_CONFIR)
    values (t.DNI_CLI,t.NOM_CLI,t.APE_PAT_CLI,t.APE_MAT_CLI,t.FONO_CLI,t.SEXO_CLI,t.DIR_CLI,t.FEC_NAC_CLI,t.FEC_CREA_CLIENTE,t.USU_CREA_CLIENTE,t.FEC_MOD_CLIENTE,t.USU_MOD_CLIENTE,t.IND_ESTADO,t.cod_local_origen,t.COD_TIP_DOCUMENTO ,  t.ID_USU_CONFIR);
    */
 end;
----------------

END;
/

