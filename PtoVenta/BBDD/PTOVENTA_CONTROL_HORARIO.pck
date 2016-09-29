create or replace package PTOVENTA_CONTROL_HORARIO is

    TYPE FarmaCursor IS REF CURSOR;

  /* ****************************************************************** */
  --Descripcion: GRABA EN LA TABLA MAESTRO DE TURNOS
  --Fecha       Usuario        Comentario
  --04/05/2015  CHUANES        Creacion 
  /* ****************************************************************** */          
  FUNCTION CTRL_F_GRABA_MAE_TURNO(cCodGrupoCia_in IN CHAR,
                                  cHoraInicio_in IN CHAR, 
                                  cHoraFin_in IN CHAR,
                                  usuCreacion IN CHAR,
                                  cEstado_in IN CHAR)
  RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: ACTUALIZA EL MAESTRO DE TURNOS
  --Fecha       Usuario        Comentario
  --04/05/2015  CHUANES        Creacion 
  /* ****************************************************************** */           
  FUNCTION CTRL_F_UPD_MAE_TURNO(cIdTurno_in IN CHAR,
                                cHoraInicio_in  IN CHAR, 
                                cHoraFin_in     IN CHAR,
                                cUsuMod_in IN CHAR,
                                cEstado_in IN CHAR)
  RETURN CHAR;
    
  /* ****************************************************************** */
  --Descripcion: VERIFICA LA NO DUPLICDAD CUANDO DE GRABA UN NUEVO REGISTRO
  --Fecha       Usuario        Comentario
  --04/05/2015  CHUANES        Creacion 
  /* ****************************************************************** */        
  FUNCTION CTRL_F_VERIF_DUPLICADO_GRABA(cHoraInicio_in IN CHAR, 
                                        cHoraFin_in    IN CHAR)
  RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: VERIFICA LA NO DUPLICDAD CUANDO SE ACTUALIZA UN REGISTRO
  --Fecha       Usuario        Comentario
  --04/05/2015  CHUANES        Creacion 
  /* ****************************************************************** */       
  FUNCTION CTRL_F_VERIF_DUPLICADO_UPD(cIdTurno_in    IN CHAR,
                                      cHoraInicio_in IN CHAR, 
                                      cHoraFin_in    IN CHAR)
  RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: VERIFICA SI UN TURNO YA GENERO UNA PLANTILLA , DE SER ASI, NO SE DEBE PERMITIR EDITAR
  --Fecha       Usuario        Comentario
  --04/05/2015  CHUANES        Creacion 
  /* ****************************************************************** */    
  FUNCTION CTRL_F_VERIF_TURNO_PLANTILLA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        cIdTurno_in IN CHAR)
  RETURN CHAR;
    
  /* ****************************************************************** */
  --Descripcion: VERIFICA SI UN TURNO YA GENERO UNA HORARIO , DE SER ASI, NO SE DEBE PERMITIR EDITAR
  --Fecha       Usuario        Comentario
  --04/05/2015  CHUANES        Creacion 
  /* ****************************************************************** */       
  FUNCTION CTRL_F_VERIF_TURNO_HORARIO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cIdTurno_in  IN CHAR)
  RETURN CHAR;
    
  /* ****************************************************************** */
  --Descripcion: VERIFICA SI UN TURNO YA SE ASIGNO A UN LOCAL
  --Fecha       Usuario        Comentario
  --04/05/2015  CHUANES        Creacion 
  /* ****************************************************************** */     
  FUNCTION CTRL_F_VERIF_TURNO_ASIGNA(cCodCia IN CHAR,
                                     cCodLocal_in IN CHAR, 
                                     cIdTurno_in  IN CHAR)
  RETURN CHAR;
    
  /* ****************************************************************** */
  --Descripcion: ELIMINA UN TURNO DE LA TABLA PBL_TURNO
  --Fecha       Usuario        Comentario
  --04/05/2015  CHUANES        Creacion 
  /* ****************************************************************** */    
  FUNCTION CTRL_F_BORRA_TURNO(cIdTurno_in IN CHAR) RETURN CHAR;
    
  /* ****************************************************************** */
  --Descripcion: VALIDA EL MAXIMO  DE HORAS TRABAJAS POR DIA
  --Fecha       Usuario        Comentario
  --05/05/2015  CHUANES        Creacion 
  /* ****************************************************************** */       
  FUNCTION CTRL_F_VALIDA_MAX_HORA(cHoraInicio_in IN CHAR, 
                                  cHoraFin_in    IN CHAR)
  RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: VALIDA EL MINIMO  DE HORAS TRABAJAS POR DIA
  --Fecha       Usuario        Comentario
  --05/05/2015  CHUANES        Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_VALIDA_MIN_HORA(cHoraInicio_in IN CHAR, 
                                  cHoraFin_in IN CHAR)
  RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: DESASIGNAR TURNO AL LOCAL
  --Fecha       Usuario        Comentario
  --05/05/2015  CHUANES        Creacion 
  /* ****************************************************************** */     
  FUNCTION CTRL_F_DESASIGNAR_TURNO(cCodLocal_in IN CHAR,
                                   cIdTurno_in IN CHAR)
  RETURN CHAR;
    
  /* ****************************************************************** */
  --Descripcion: LISTADO DE ROLES EN EL COMBO
  --Fecha       Usuario        Comentario
  --16/05/2015  CHUANES        Creacion 
  /* ****************************************************************** */         
  FUNCTION CTRL_F_LISTA_ROLES(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in IN CHAR)
  RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: IMPRESION DE HORARIO
  --Fecha       Usuario        Comentario
  --02/06/2015  CHUANES        Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_PRINT_HORARIO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cCodHorario_in  IN CHAR)
  RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: LISTA  LAS HORAS DE HORARIOS
  --Fecha       Usuario        Comentario
  --03/06/2015  CHUANES        Creacion 
  /* ****************************************************************** */     
  FUNCTION CTRL_F_CUR_LISTA_HORA(cCodCia IN CHAR,
                                 cCodLocal_in IN CHAR)
  RETURN FarmaCursor;
    
  /* ****************************************************************** */
  --Descripcion: INDICA SI UN HORARIO SE DEBE EDITAR
  --Fecha       Usuario        Comentario
  --28/06/2015  CHUANES        Creacion 
  /* ****************************************************************** */         
    FUNCTION CTRL_F_EDITAR_HORARIO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in IN CHAR, 
                                   cCodHorario_in IN CHAR)
    RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: ENVIAR CORREO A SUGERENCIA SI EXCEDE MAS DE 48 SEMANANES EN LOS LOCALES
  --Fecha       Usuario        Comentario
  --14/08/2015  CHUANES        Creacion 
  /* ****************************************************************** */           
    PROCEDURE CTRL_P_ENVIA_MAIL(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in   IN CHAR,
                                 cCodHorario_in IN CHAR);
                                 
  /* ****************************************************************** */
  --Descripcion: ENVIAR CORREO A A JEFES DE LOCALES
  --Fecha       Usuario        Comentario
  --14/08/2015  CHUANES        Creacion 
  /* ****************************************************************** */        
    PROCEDURE CTRL_P_ENVIA_MAIL_HORARIO(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in   IN CHAR,
                                        cCodHorario_in IN CHAR);
                                        
  /* ****************************************************************** */
  --Descripcion: ENVIA UN MENSAJE SI UN USUARIO PRESENTA VACACIONES, SUBSIDIO O CESE
  --Fecha       Usuario        Comentario
  --29/08/2015  CHUANES        Creacion 
  /* ****************************************************************** */        
  FUNCTION CTRL_F_USU_SOLICITUD(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                pSecUsuLocal IN CHAR, 
                                pFecInicio   IN CHAR,
                                pFecFin  IN CHAR,
                                pCodDia1 IN CHAR,
                                pCodDia2 IN CHAR,
                                pCodDia3 IN CHAR,
                                pCodDia4 IN CHAR,
                                pCodDia5 IN CHAR, 
                                pCodDia6 IN CHAR,
                                pCodDia7 IN CHAR)
  RETURN CHAR;
  /* ****************************************************************** */    
END PTOVENTA_CONTROL_HORARIO;
/
create or replace package body PTOVENTA_CONTROL_HORARIO is
  
--*********************************************************************************
  FUNCTION CTRL_F_GRABA_MAE_TURNO(cCodGrupoCia_in IN CHAR,
                                  cHoraInicio_in IN CHAR, 
                                  cHoraFin_in IN CHAR,
                                  usuCreacion IN CHAR,
                                  cEstado_in IN CHAR)
  RETURN CHAR
  IS
     v_FLAG CHAR(5);
     v_NOMTURNO VARCHAR2(100);
  BEGIN
     v_FLAG:='FALSE';
     v_NOMTURNO:=cHoraInicio_in||'-'||cHoraFin_in;
  --LP 04/09/2015
     INSERT INTO pbl_turno
       ( cod_grupo_cia
       , cod_turno
       , nom_turno
       , hora_inic
       , hora_fin
       , usu_crea
       , ind_viajero )
     VALUES ( cCodGrupoCia_in
        , LPAD ( TO_CHAR ( incremento_tidturno.NEXTVAL ), 4, '0' )
        , v_NOMTURNO
        , cHoraInicio_in
        , cHoraFin_in
        , usucreacion
        , cEstado_in );
     v_FLAG:='TRUE';
     RETURN v_FLAG;
  EXCEPTION 
    WHEN OTHERS  THEN
    DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
    RETURN v_FLAG;
  END;
--*********************************************************************************
  FUNCTION CTRL_F_UPD_MAE_TURNO(cIdTurno_in IN CHAR,
                                cHoraInicio_in  IN CHAR, 
                                cHoraFin_in     IN CHAR,
                                cUsuMod_in IN CHAR,
                                cEstado_in IN CHAR)
  RETURN CHAR
  IS
     v_FLAG CHAR(5);
     v_NOMTURNO VARCHAR2(100);
  BEGIN
     v_FLAG:='FALSE';
     v_NOMTURNO:=cHoraInicio_in||'-'||cHoraFin_in;

  --LP 04/09/2015
    UPDATE pbl_turno
    SET nom_turno = v_NOMTURNO
      , hora_inic = cHoraInicio_in
      , hora_fin = cHoraFin_in
      , fec_mod = SYSDATE
      , usu_mod = cUsuMod_in
      , ind_viajero = cEstado_in
    WHERE cod_turno = cIdTurno_in;
    v_FLAG:='TRUE';
    RETURN v_FLAG;
  
  EXCEPTION 
    WHEN OTHERS  THEN
      DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
      RETURN v_FLAG;
  END;
--*********************************************************************************
  FUNCTION CTRL_F_VERIF_DUPLICADO_GRABA(cHoraInicio_in IN CHAR, 
                                        cHoraFin_in    IN CHAR)
  RETURN CHAR
  IS
      v_FLAG CHAR(5);
      v_Cant NUMBER;
  BEGIN
      v_FLAG:='FALSE';
     --LP 04/09/2015
     SELECT COUNT ( 1 ) 
       INTO v_Cant
       FROM pbl_turno
      WHERE hora_inic = cHoraInicio_in
        AND hora_fin = cHoraFin_in;
      IF v_Cant>0 
      THEN
         v_FLAG:='TRUE';
      END IF;
      RETURN v_FLAG;
  EXCEPTION 
     WHEN OTHERS  THEN
      DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
  RETURN v_FLAG;
  END; 
--*********************************************************************************
  FUNCTION CTRL_F_VERIF_DUPLICADO_UPD(cIdTurno_in    IN CHAR,
                                      cHoraInicio_in IN CHAR, 
                                      cHoraFin_in    IN CHAR)
  RETURN CHAR
  IS
    v_FLAG CHAR(5);
    v_Cant NUMBER; 
  BEGIN
    v_FLAG:='FALSE';
    --LP 04/09/2015
    SELECT COUNT ( 1 )
      INTO v_Cant
      FROM pbl_turno
     WHERE cod_turno != cIdTurno_in
       AND hora_inic = cHoraInicio_in
       AND hora_fin = cHoraFin_in;
    IF v_Cant>0 THEN
      v_FLAG:='TRUE';
    END IF;
    RETURN v_FLAG;
  EXCEPTION 
    WHEN OTHERS  THEN
    DBMS_OUTPUT.PUT_LINE(SQLCODE || SQLERRM);
    RETURN v_FLAG;
  END;
--*********************************************************************************
  FUNCTION CTRL_F_VERIF_TURNO_PLANTILLA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        cIdTurno_in IN CHAR)
    RETURN CHAR
    IS
    v_FLAG CHAR(5);
    v_Cant NUMBER;
  BEGIN
    v_FLAG:='N';
   --LP 04/09/2015
    /* Formatted on 2015/09/04 15:35 (Formatter Plus v4.8.8) */
    SELECT COUNT ( 1 )
      INTO v_Cant
      FROM pbl_det_plantilla p
     WHERE p.cod_grupo_cia = cCodGrupoCia_in
       AND p.cod_local = cCodLocal_in
       AND (    p.cod_turno1 = cIdTurno_in
             OR p.cod_turno2 = cIdTurno_in
             OR p.cod_turno3 = cIdTurno_in
             OR p.cod_turno4 = cIdTurno_in
             OR p.cod_turno5 = cIdTurno_in
             OR p.cod_turno6 = cIdTurno_in
             OR p.cod_turno7 = cIdTurno_in );    
    IF v_Cant>0 THEN
       v_FLAG:='S';
    END IF;
    RETURN v_FLAG;
  EXCEPTION 
    WHEN OTHERS  THEN
    DBMS_OUTPUT.PUT_LINE(SQLCODE || SQLERRM);
    RETURN v_FLAG;
  END;
--*********************************************************************************
  FUNCTION CTRL_F_VERIF_TURNO_HORARIO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cIdTurno_in  IN CHAR)
    RETURN CHAR
    IS
    v_FLAG CHAR(5);
    v_Cant NUMBER;
  BEGIN
    v_FLAG:='N';
   --LP 04/09/2015
    SELECT COUNT ( 1 )
    INTO v_Cant
    FROM pbl_det_horario p
   WHERE p.cod_grupo_cia = cCodGrupoCia_in
     AND p.cod_local = cCodLocal_in
     AND (    p.cod_dia1 = cIdTurno_in
           OR p.cod_dia2 = cIdTurno_in
           OR p.cod_dia3 = cIdTurno_in
           OR p.cod_dia4 = cIdTurno_in
           OR p.cod_dia5 = cIdTurno_in
           OR p.cod_dia6 = cIdTurno_in
           OR p.cod_dia6 = cIdTurno_in );
    
    IF v_Cant>0 THEN
       v_FLAG:='S';
    END IF;
    RETURN v_FLAG;
  EXCEPTION 
    WHEN OTHERS  THEN
    DBMS_OUTPUT.PUT_LINE(SQLCODE || SQLERRM);
    RETURN v_FLAG;
  END;
--*********************************************************************************
  FUNCTION CTRL_F_VERIF_TURNO_ASIGNA(cCodCia IN CHAR,
                                     cCodLocal_in IN CHAR, 
                                     cIdTurno_in  IN CHAR)
    RETURN CHAR
    IS
    v_FLAG CHAR(5);
    v_Cant NUMBER;
  BEGIN
    v_FLAG:='FALSE';
    SELECT COUNT ( m.cod_turno )
      INTO v_Cant
      FROM pbl_turno_local m
     WHERE m.cod_cia = ccodcia
       AND m.cod_local = cCodLocal_in
       AND m.cod_turno = cIdTurno_in;
    IF v_Cant>0 THEN
      v_FLAG:='TRUE';
    END IF;
    RETURN v_FLAG;
  EXCEPTION 
    WHEN OTHERS  THEN
    DBMS_OUTPUT.PUT_LINE(SQLCODE || SQLERRM);
    RETURN v_FLAG;
  END;
--*********************************************************************************
  FUNCTION CTRL_F_BORRA_TURNO(cIdTurno_in IN CHAR)
    RETURN CHAR
  IS
    v_FLAG CHAR(5);
  BEGIN
    v_FLAG:='FALSE';
    DELETE FROM PBL_TURNO  WHERE COD_TURNO=cIdTurno_in;
    v_FLAG:='TRUE';
    RETURN v_FLAG;
  EXCEPTION 
    WHEN OTHERS  THEN
    DBMS_OUTPUT.PUT_LINE(SQLCODE || SQLERRM);
    RETURN v_FLAG;
  END;
--*********************************************************************************
  FUNCTION CTRL_F_VALIDA_MAX_HORA(cHoraInicio_in IN CHAR, 
                                  cHoraFin_in    IN CHAR)
    RETURN CHAR
  IS
    v_FLAG CHAR(5);
    v_HoraInicio DATE;
    v_HoraFin DATE;
    v_CantHoras NUMBER;
    v_CantHorasTrab NUMBER;
  BEGIN
    v_FLAG:='FALSE';
    v_HoraInicio:=TO_DATE(cHoraInicio_in,'HH24:MI');
    v_HoraFin:=TO_DATE(cHoraFin_in,'HH24:MI');
    v_CantHoras:=ROUND(TO_NUMBER((v_HoraFin-v_HoraInicio)*24),2);
    --LP 04/09/2015
    SELECT TO_NUMBER(X.LLAVE_TAB_GRAL) 
      INTO v_CantHorasTrab 
      FROM PBL_TAB_GRAL  X 
     WHERE X.ID_TAB_GRAL=707 ;
    IF v_CantHoras > v_CantHorasTrab THEN
       v_FLAG:='TRUE';
    END IF;
    RETURN v_FLAG;
  EXCEPTION 
    WHEN OTHERS  THEN
    DBMS_OUTPUT.PUT_LINE(SQLCODE || SQLERRM);
    RETURN v_FLAG;
  END;
--*********************************************************************************
  FUNCTION CTRL_F_VALIDA_MIN_HORA(cHoraInicio_in IN CHAR, 
                                  cHoraFin_in IN CHAR)
    RETURN CHAR
    IS
    v_FLAG CHAR(5);
    v_HoraInicio DATE;
    v_HoraFin DATE;
    v_CantHoras NUMBER;
    v_CantHorasTrab NUMBER;
  BEGIN
    v_FLAG:='FALSE';
    v_HoraInicio:=TO_DATE(cHoraInicio_in,'HH24:MI');
    v_HoraFin:=TO_DATE(cHoraFin_in,'HH24:MI');
    v_CantHoras:=ROUND(TO_NUMBER((v_HoraFin-v_HoraInicio)*24),2);
    --LP 04/09/2015
    SELECT TO_NUMBER(X.LLAVE_TAB_GRAL) 
      INTO v_CantHorasTrab 
      FROM PBL_TAB_GRAL  X 
     WHERE X.ID_TAB_GRAL=693 ;
    IF v_CantHoras < v_CantHorasTrab THEN
      v_FLAG:='TRUE';
    END IF;
    RETURN v_FLAG;
  EXCEPTION 
    WHEN OTHERS  THEN
    DBMS_OUTPUT.PUT_LINE(SQLCODE || SQLERRM);
    RETURN v_FLAG;
  END;
--*********************************************************************************
  FUNCTION CTRL_F_DESASIGNAR_TURNO(cCodLocal_in IN CHAR,
                                   cIdTurno_in IN CHAR)
  RETURN CHAR
  IS
    v_FLAG CHAR(5):='N';
  BEGIN
    DELETE FROM PBL_TURNO_LOCAL  
    WHERE COD_TURNO=cIdTurno_in 
    AND COD_LOCAL=cCodLocal_in;
    v_FLAG:='S';
    RETURN v_FLAG;
  EXCEPTION 
    WHEN OTHERS  THEN
    DBMS_OUTPUT.PUT_LINE(SQLCODE || SQLERRM);
    RETURN v_FLAG;
  END;
--*********************************************************************************
  FUNCTION CTRL_F_LISTA_ROLES(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS 
     v_curPlantilla FarmaCursor;
  BEGIN
     OPEN v_curPlantilla FOR
     SELECT DISTINCT ( r.cod_rol ) || 'Ã' || r.desc_rol
               FROM pbl_rol_usu u
                  , pbl_rol r
                  , pbl_usu_local usu
              WHERE usu.cod_grupo_cia = u.cod_grupo_cia
                AND usu.cod_local = u.cod_local
                AND usu.sec_usu_local = u.sec_usu_local
                AND usu.cod_grupo_cia = cCodGrupoCia_in
                AND usu.cod_local = cCodLocal_in
                AND r.cod_rol = u.cod_rol
                AND u.est_rol_usu = 'A'
                AND usu.est_usu = 'A'
                AND u.cod_rol NOT IN ('000', '012', '014', '027');
     RETURN v_curPlantilla;
  END;    
--*********************************************************************************
  FUNCTION CTRL_F_PRINT_HORARIO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cCodHorario_in  IN CHAR)
  RETURN FarmaCursor
  IS 
     v_curHorario FarmaCursor;
  BEGIN
     OPEN v_curHorario FOR
   --lp 05/09/2015
      SELECT   RPAD ( ( SELECT UPPER ( r.desc_rol )
                       FROM pbl_rol r
                      WHERE r.cod_rol = rol.cod_rol ), 10, ' ' ) || 'Ã'
             || RPAD ( NVL ( ( SELECT u.ape_pat || '-' || u.nom_usu
                                FROM pbl_usu_local u
                               WHERE u.sec_usu_local = det.sec_usu_local ), ' ' ), 20, ' ' ) || 'Ã'
             || RPAD ( NVL ( ( SELECT t.nom_turno
                                FROM pbl_turno t
                               WHERE t.cod_turno = det.cod_dia1 ), ' ' ), 14, ' ' ) || 'Ã'
             || RPAD ( NVL ( ( SELECT t.nom_turno
                                FROM pbl_turno t
                               WHERE t.cod_turno = det.cod_dia2 ), ' ' ), 14, ' ' ) || 'Ã'
             || RPAD ( NVL ( ( SELECT t.nom_turno
                                FROM pbl_turno t
                               WHERE t.cod_turno = det.cod_dia3 ), ' ' ), 14, ' ' ) || 'Ã'
             || RPAD ( NVL ( ( SELECT t.nom_turno
                                FROM pbl_turno t
                               WHERE t.cod_turno = det.cod_dia4 ), ' ' ), 14, ' ' ) || 'Ã'
             || RPAD ( NVL ( ( SELECT t.nom_turno
                                FROM pbl_turno t
                               WHERE t.cod_turno = det.cod_dia5 ), ' ' ), 14, ' ' ) || 'Ã'
             || RPAD ( NVL ( ( SELECT t.nom_turno
                                FROM pbl_turno t
                               WHERE t.cod_turno = det.cod_dia6 ), ' ' ), 14, ' ' ) || 'Ã'
             || RPAD ( NVL ( ( SELECT t.nom_turno
                                FROM pbl_turno t
                               WHERE t.cod_turno = det.cod_dia7 ), ' ' ), 14, ' ' )
        FROM pbl_det_horario det
           , pbl_rol rol
       WHERE det.cod_rol = rol.cod_rol
         AND det.cod_grupo_cia = cCodGrupoCia_in
         AND det.cod_local = cCodLocal_in
         AND det.cod_horario = cCodHorario_in
    ORDER BY rol.desc_rol
           , RPAD ( NVL ( ( SELECT t.nom_turno
                             FROM pbl_turno t
                            WHERE t.cod_turno = det.cod_dia1 ), ' ' ), 14, ' ' ) DESC;

  RETURN v_curHorario;
 
  END;
--*********************************************************************************
  FUNCTION CTRL_F_CUR_LISTA_HORA(cCodCia IN CHAR,
                                 cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
  v_curPlantilla FarmaCursor;
  
      BEGIN
      OPEN v_curPlantilla FOR
      SELECT * FROM (select '000'||'Ã' || '--:--/--:--'||'Ã' ||'0.0' from dual
      union
      SELECT l.cod_turno ||'Ã' || 
      t.nom_turno ||'Ã' ||
      (trim(to_number((case
      when         
      (to_date('01/01/2015 '||t.hora_fin,'dd/MM/yyyy HH24:mi:ss')- to_date('01/01/2015 '||t.hora_inic,'dd/MM/yyyy HH24:mi:ss'))<0
      then  
      (to_date('02/01/2015 '||t.hora_fin,'dd/MM/yyyy HH24:mi:ss')- to_date('01/01/2015 '||t.hora_inic,'dd/MM/yyyy HH24:mi:ss'))

      else
      (to_date('01/01/2015 '||t.hora_fin,'dd/MM/yyyy HH24:mi:ss')- to_date('01/01/2015 '||t.hora_inic,'dd/MM/yyyy HH24:mi:ss'))
      end)*24*60,'9999990.00'))-(nvl(t.MINUTOS_Refrigerio,0))
      
      ) -- minutos rango de hora
 
      FROM pbl_turno t , pbl_turno_local l     
      WHERE t.cod_turno = l.cod_turno
        AND l.cod_cia = ccodcia
        AND l.cod_local = cCodLocal_in
        AND T.IND_CONCEPTO IS NULL
        union
         SELECT t.cod_turno ||'Ã' || 
      t.nom_turno ||'Ã' ||
      (trim(to_number((case
      when         
      (to_date('01/01/2015 '||t.hora_fin,'dd/MM/yyyy HH24:mi:ss')- to_date('01/01/2015 '||t.hora_inic,'dd/MM/yyyy HH24:mi:ss'))<0
      then  
      (to_date('02/01/2015 '||t.hora_fin,'dd/MM/yyyy HH24:mi:ss')- to_date('01/01/2015 '||t.hora_inic,'dd/MM/yyyy HH24:mi:ss'))

      else
      (to_date('01/01/2015 '||t.hora_fin,'dd/MM/yyyy HH24:mi:ss')- to_date('01/01/2015 '||t.hora_inic,'dd/MM/yyyy HH24:mi:ss'))
      end)*24*60,'9999990.00'))-(nvl(t.MINUTOS_Refrigerio,0))
      
      ) -- minutos rango de hora
 
      FROM pbl_turno t where t.ind_concepto in ('S')
      
        ) ORDER BY 1 ;
    RETURN v_curPlantilla;
    END;
--*********************************************************************************
    FUNCTION CTRL_F_EDITAR_HORARIO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in IN CHAR, 
                                   cCodHorario_in IN CHAR)
    RETURN CHAR
    IS
    v_FLAG CHAR(1);  
    v_Cant INTEGER; 
    BEGIN
    v_FLAG:='N';
    --LP 05/09/2015
    SELECT  COUNT(1) 
      INTO v_Cant   
      FROM   PBL_CAB_HORARIO CAB 
      WHERE CAB.COD_GRUPO_CIA=cCodGrupoCia_in
       AND CAB.COD_LOCAL=cCodLocal_in
       AND CAB.COD_HORARIO=cCodHorario_in
       AND SYSDATE BETWEEN CAB.FEC_INICIO AND CAB.FEC_FIN;
    IF v_Cant>0 THEN
       v_FLAG:='N';
    ELSE
       v_FLAG:='S';
    END IF;
    RETURN v_FLAG; 
    END;
--*********************************************************************************   
    PROCEDURE CTRL_P_ENVIA_MAIL(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in   IN CHAR,
                                 cCodHorario_in IN CHAR)
    AS 
       v_Cant INTEGER :=0;
       v_CantHoraLegal INTEGER;
    BEGIN 
       --LP 05/09/2015
       SELECT TO_NUMBER(TAB.LLAVE_TAB_GRAL) 
         INTO v_CantHoraLegal  
         FROM  PBL_TAB_GRAL TAB 
         WHERE TAB.ID_TAB_GRAL=696; 
       SELECT  COUNT(1) INTO v_Cant
         FROM  PBL_DET_HORARIO DET 
        WHERE DET.COD_GRUPO_CIA=cCodGrupoCia_in
          AND DET.COD_LOCAL=cCodLocal_in
          AND DET.COD_HORARIO=cCodHorario_in
         AND  DET.NUM_HORA_PROG>v_CantHoraLegal;
    IF(v_Cant>0) THEN 
       CTRL_P_ENVIA_MAIL_HORARIO(cCodGrupoCia_in ,cCodLocal_in ,cCodHorario_in );
    END IF;
    EXCEPTION
        WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE ('ERROR AL ENVIAR EMAIL..VERIFIQUE SU EMAIL EN BASE DE DATOS');
    END;
--*********************************************************************************  
    PROCEDURE CTRL_P_ENVIA_MAIL_HORARIO(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in   IN CHAR,
                                        cCodHorario_in IN CHAR)
    AS
  
    v_vNombreArchivo  varchar(100) := '';
    v_NameFileZip VARCHAR2(2000) := '';
    v_Fila_Msg_01       varchar2(2800):= '';
    v_FechaInicio VARCHAR2(20);
    v_FechaFin VARCHAR(20);
     
     v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
      C_INICIO_MSG  VARCHAR2(2000) := '<html>'||
                                      '<head>'||
                                      '</head>'||
                                      '<body>'||
                                      '<table width="100%" border="0">';

      C_FIN_MSG     VARCHAR2(2000) := '</table>'||
                                        '</body>'||
                                        '</html> ';
      vFecha CHAR(10);                                  

    CURSOR curHorario IS
        
  SELECT  
    TO_CHAR((SELECT  UPPER(R.DESC_ROL) FROM PBL_ROL R WHERE R.COD_ROL=ROL.COD_ROL)) AS ROL,
    NVL((SELECT U.APE_PAT ||' '||U.APE_MAT FROM PBL_USU_LOCAL U WHERE U.SEC_USU_LOCAL=DET.SEC_USU_LOCAL),' ') AS USUARIO,
    NVL((SELECT  T.NOM_TURNO FROM PBL_TURNO T WHERE T.COD_TURNO=DET.COD_DIA1),' ') AS LUNES,
    NVL((SELECT  T.NOM_TURNO FROM PBL_TURNO T WHERE T.COD_TURNO=DET.COD_DIA2),' ') AS MARTES,
    NVL((SELECT  T.NOM_TURNO FROM PBL_TURNO T WHERE T.COD_TURNO=DET.COD_DIA3),' ') AS MIERCOLES,
    NVL((SELECT  T.NOM_TURNO FROM PBL_TURNO T WHERE T.COD_TURNO=DET.COD_DIA4),' ') AS JUEVES,
    NVL((SELECT  T.NOM_TURNO FROM PBL_TURNO T WHERE T.COD_TURNO=DET.COD_DIA5),' ') AS VIERNES,
    NVL((SELECT  T.NOM_TURNO FROM PBL_TURNO T WHERE T.COD_TURNO=DET.COD_DIA6),' ') AS SABADO,
    NVL((SELECT  T.NOM_TURNO FROM PBL_TURNO T WHERE T.COD_TURNO=DET.COD_DIA7),' ') AS DOMINGO,
    TO_CHAR(NVL(DET.NUM_HORA_PROG,0.00),'999,999.00') AS HRS_EFECTIVAS
    FROM PBL_DET_HORARIO DET,PBL_ROL ROL
    WHERE 
    DET.COD_ROL=ROL.COD_ROL
    AND DET.COD_GRUPO_CIA=cCodGrupoCia_in
    AND DET.COD_LOCAL=cCodLocal_in
    AND DET.COD_HORARIO=cCodHorario_in ORDER BY DET.SEC_HORARIO ASC;
   

    v_CurHorario curHorario%ROWTYPE;
    v_vDescLocal VARCHAR2(120);
    v_Email VARCHAR2(120);
    v_Email_Zona VARCHAR2(120);
    j NUMBER(10) := 0; -- Horarios
    ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;
    mesg_body VARCHAR2(4000);

  BEGIN
    --
    SELECT  TO_CHAR(CAB.FEC_INICIO,'dd/mm/yyyy') ,TO_CHAR(CAB.FEC_FIN,'dd/mm/yyyy')
     INTO v_FechaInicio,v_FechaFin FROM PBL_CAB_HORARIO CAB
    WHERE CAB.COD_GRUPO_CIA=cCodGrupoCia_in
    AND CAB.COD_LOCAL=cCodLocal_in
    AND CAB.COD_HORARIO=cCodHorario_in;
    --DESC LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
      INTO v_vDescLocal
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;
       --EMAIL DEL LOCAL
     SELECT  LC.MAIL_LOCAL INTO v_Email  FROM PBL_LOCAL LC
    WHERE LC.COD_GRUPO_CIA = cCodGrupoCia_in
          AND LC.COD_LOCAL = cCodLocal_in;
      --EMAIL JEFE ZONA    
    SELECT  ZN.EMAIL_JEFE_ZONA INTO v_Email_Zona FROM VTA_LOCAL_X_ZONA LZ ,VTA_ZONA_VTA ZN
    WHERE LZ.COD_GRUPO_CIA=ZN.COD_GRUPO_CIA
    AND LZ.COD_ZONA_VTA=ZN.COD_ZONA_VTA
    AND LZ.COD_GRUPO_CIA=cCodGrupoCia_in
    AND LZ.COD_LOCAL=cCodLocal_in;      
  
   vfecha:=TO_CHAR(SYSDATE,'dd-mm-yyyy');
   v_vNombreArchivo := 'REP.HORARIOS_EXCESO 48 HRS  '||cCodLocal_in||' - '|| vfecha||'.xls';

    --INICIO ARCHIVO
    ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
    
  
    --INICIO HTML
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<html>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<head>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <meta content="text/html; charset=ISO-8859-1"');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,' http-equiv="content-type">');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <title>REPORTES DE HORARIOS-EXCESO 48 HRS SEMANALES</title>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'</head>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<body>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<span style="font-weight: bold; font-style: italic;">LOCAL: '||v_vDescLocal||'</span><br>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<span style="font-weight: bold; font-style: italic;">FECHA: '|| vfecha ||'</span><br>');

    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<br>');

    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<table style="text-align: left; width: 100%;" border="1"');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,' cellpadding="2" cellspacing="1">');
    ----1° REPORTE
    
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<caption><big> HORARIO: SEMANA '||v_FechaInicio ||' - '|| v_FechaFin||'</big></caption>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <tbody>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'    <tr>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<th><small>ROL</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<th><small>APELLIDOS</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<th><small>LUNES</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<th><small>MARTES</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<th><small>MIERCOLES</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<th><small>JUEVES</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<th><small>VIERNES</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<th><small>SABADO</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<th><small>DOMINGO</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<th><small>HRS.EFECTIVAS</small></th>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'    </tr>');
   
    FOR v_CurHorario IN curHorario
    LOOP
      j := j+1;
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'   <tr>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<center> <td><center><small>'||v_CurHorario.Rol||'</small></center></td></center>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<td><small>'||v_CurHorario.Usuario||'</small></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<td><center><small>'||v_CurHorario.Lunes||'</small></center></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<td><center><small>'||v_CurHorario.Martes||'</small></center></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<td><center><small>'||v_CurHorario.Miercoles||'</small></center></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<td><center><small>'||v_CurHorario.Jueves||'</small></center></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<td><center><small>'||v_CurHorario.Viernes||'</small></center></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<td><center><small>'||v_CurHorario.Sabado||'</small></center></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<td><center><small>'||v_CurHorario.Domingo||'</small></center></td>');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<td><center><small>'||v_CurHorario.Hrs_Efectivas||'</small></center></td>');

      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'   </tr>');
    END LOOP;
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  </tbody>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'</table>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<br><br>');
    
    COMMIT;
    --FIN HTML
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'</body>');
    UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'</html>');

    --FIN ARCHIVO
    UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
    
     v_NameFileZip := util_email_oracle.out_zip_file(v_vNombreArchivo,v_gNombreDiretorio);
      DBMS_OUTPUT.put_line('v_NameFileZip-'||v_NameFileZip);
      DBMS_LOCK.sleep(2);
      
    DBMS_OUTPUT.PUT_LINE('GRABO ARCHIVO DE CAMBIOS');
    
    v_Fila_Msg_01:='Buenos dias,<BR><BR>Con el fin de mantener informado le adjuntamos el sgte reporte<BR><BR>Saludos,';

    mesg_body:=C_INICIO_MSG||v_Fila_Msg_01||C_FIN_MSG;
    
    
     FARMA_EMAIL.ENVIA_CORREO_ATTACH(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                            v_Email||'',--ReceiverAddress
                            'Reporte de Horarios '|| v_vDescLocal ||' - '|| vfecha ,
                            'Horarios con Exceso de Horas '|| v_vDescLocal,
                            CASE WHEN (j) > 0 THEN mesg_body ELSE NULL END,
                            TRIM(v_vNombreArchivo),--ReceiverAddress
                            (j),

                            v_Email_Zona ||CASE WHEN (j) > 0 THEN ', ' || v_Email_Zona ELSE '' END,
                             

                            FARMA_EMAIL.GET_EMAIL_SERVER);

                              
      EXCEPTION
        WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE ('ERROR AL ENVIAR EMAIL..VERIFIQUE SU EMAIL EN BASE DE DATOS');                      

  
  END;
--*********************************************************************************
  FUNCTION CTRL_F_USU_SOLICITUD(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                pSecUsuLocal IN CHAR, 
                                pFecInicio   IN CHAR,
                                pFecFin  IN CHAR,
                                pCodDia1 IN CHAR,
                                pCodDia2 IN CHAR,
                                pCodDia3 IN CHAR,
                                pCodDia4 IN CHAR,
                                pCodDia5 IN CHAR, 
                                pCodDia6 IN CHAR,
                                pCodDia7 IN CHAR)
  RETURN CHAR
--LP 05/09/2015
  IS 
      v_MSN_RESPUESTA VARCHAR2(500);
      v_DNI CHAR(8);
      v_CANT_VACACIONES INTEGER; 
      v_CANT_SUBSIDIO INTEGER;
      v_CANT_CESE INTEGER;
      v_FECHA_INICIO VARCHAR2(60);
      v_FECHA_FIN VARCHAR2(60);
      v_USUARIO VARCHAR2(100);
      v_SEC_REGISTRO CHAR(10);
      v_IND_CONCEPTO1 CHAR(1);
      v_IND_CONCEPTO2 CHAR(1);
      v_IND_CONCEPTO3 CHAR(1);
      v_IND_CONCEPTO4 CHAR(1);
      v_IND_CONCEPTO5 CHAR(1);
      v_IND_CONCEPTO6 CHAR(1);
      v_IND_CONCEPTO7 CHAR(1);
  BEGIN 
      v_MSN_RESPUESTA:='N';
      SELECT  NVL(T.IND_CONCEPTO,'-') 
        INTO v_IND_CONCEPTO1  
        FROM PBL_TURNO T 
       WHERE T.COD_GRUPO_CIA=cCodGrupoCia_in 
         AND T.COD_TURNO=pCodDia1;
      SELECT  NVL(T.IND_CONCEPTO,'-') 
        INTO v_IND_CONCEPTO2  
        FROM PBL_TURNO T 
       WHERE T.COD_GRUPO_CIA=cCodGrupoCia_in 
         AND T.COD_TURNO=pCodDia2;
      SELECT  NVL(T.IND_CONCEPTO,'-') 
        INTO v_IND_CONCEPTO3  
        FROM PBL_TURNO T 
       WHERE T.COD_GRUPO_CIA=cCodGrupoCia_in 
         AND T.COD_TURNO=pCodDia3;
      SELECT  NVL(T.IND_CONCEPTO,'-') 
        INTO v_IND_CONCEPTO4  
        FROM PBL_TURNO T 
       WHERE T.COD_GRUPO_CIA=cCodGrupoCia_in 
         AND T.COD_TURNO=pCodDia4;
      SELECT  NVL(T.IND_CONCEPTO,'-') 
        INTO v_IND_CONCEPTO5  
        FROM PBL_TURNO T 
       WHERE T.COD_GRUPO_CIA=cCodGrupoCia_in 
        AND T.COD_TURNO=pCodDia5;
      SELECT  NVL(T.IND_CONCEPTO,'-') 
        INTO v_IND_CONCEPTO6  
        FROM PBL_TURNO T 
       WHERE T.COD_GRUPO_CIA=cCodGrupoCia_in 
         AND T.COD_TURNO=pCodDia6;
      SELECT  NVL(T.IND_CONCEPTO,'-') 
        INTO v_IND_CONCEPTO7  
        FROM PBL_TURNO T 
       WHERE T.COD_GRUPO_CIA=cCodGrupoCia_in 
         AND T.COD_TURNO=pCodDia7;

  IF v_IND_CONCEPTO1 <>'N' 
     OR v_IND_CONCEPTO2<>'N' 
     OR v_IND_CONCEPTO3<>'N'
     OR v_IND_CONCEPTO4 <>'N' 
     OR v_IND_CONCEPTO5<>'N' 
     OR v_IND_CONCEPTO6<>'N' 
     OR v_IND_CONCEPTO7<>'N' 
  THEN
    SELECT  U.DNI_USU ,U.NOM_USU||' '||U.APE_PAT INTO v_DNI,v_USUARIO FROM PBL_USU_LOCAL U
    WHERE U.COD_GRUPO_CIA=cCodGrupoCia_in
    AND U.COD_LOCAL=cCodLocal_in
    AND U.SEC_USU_LOCAL=pSecUsuLocal;
--LP 05/09/2015  
  --OBTENEMOS EL DNI
  IF  v_DNI IS NOT NULL THEN
    --VERIFICAMOS SI HAY VACACIONES
    SELECT  COUNT(1) 
      INTO v_CANT_VACACIONES 
      FROM PBL_SOLICITUD P
     WHERE P.COD_GRUPO_CIA=cCodGrupoCia_in
       AND P.COD_LOCAL=cCodLocal_in
       AND ((P.FEC_INICIO<=pFecInicio AND P.FEC_FIN>= pFecInicio) OR  (P.FEC_INICIO<=pFecFin AND  P.FEC_FIN>=pFecFin));

    SELECT  COUNT(1) 
      INTO v_CANT_SUBSIDIO 
      FROM PBL_SOLICITUD P
    WHERE P.COD_GRUPO_CIA=cCodGrupoCia_in
      AND P.COD_LOCAL=cCodLocal_in
      AND ((P.FEC_INICIO<=pFecInicio AND P.FEC_FIN>= pFecInicio) OR  (P.FEC_INICIO<=pFecFin AND  P.FEC_FIN>=pFecFin));

   --VERFICAMOS SI HAY CESE
    SELECT  COUNT(1) 
      INTO v_CANT_CESE 
      FROM PBL_SOLICITUD P
     WHERE P.COD_GRUPO_CIA=cCodGrupoCia_in
     AND P.COD_LOCAL=cCodLocal_in
     AND pFecInicio>=P.FEC_INICIO;
  END IF;
  --MENSAJE DE RESPUESTA DE VACACIONES

  IF v_CANT_VACACIONES>0 THEN
  
    SELECT  MIN(P.SEC_REGISTRO) INTO  v_SEC_REGISTRO FROM PBL_SOLICITUD P
    WHERE P.COD_GRUPO_CIA=cCodGrupoCia_in
    AND P.COD_LOCAL=cCodLocal_in
    AND ((P.FEC_INICIO<=pFecInicio AND P.FEC_FIN>= pFecInicio) OR  (P.FEC_INICIO<=pFecFin AND  P.FEC_FIN>=pFecFin));
    
    SELECT  to_char(S.FEC_INICIO,'day')||to_char(S.FEC_INICIO,'dd/mm/yyyy'),to_char(S.FEC_FIN,'day')||to_char(S.FEC_FIN,'dd/mm/yyyy')
    INTO v_FECHA_INICIO,v_FECHA_FIN FROM PBL_SOLICITUD S WHERE S.COD_GRUPO_CIA=cCodGrupoCia_in
    AND S.COD_LOCAL=cCodLocal_in
    AND S.SEC_REGISTRO= v_SEC_REGISTRO;
  
     v_MSN_RESPUESTA:='El Usuario '|| v_USUARIO || ' presenta vacaciones,
     desde el '||v_FECHA_INICIO||' hasta el '|| v_FECHA_FIN;
  END IF;
  
  --MENSAJE DE RESPUESTA DE SUBSIDIO
   IF v_CANT_SUBSIDIO>0 THEN
      SELECT  MIN(P.SEC_REGISTRO) INTO  v_SEC_REGISTRO FROM PBL_SOLICITUD P
       WHERE P.COD_GRUPO_CIA=cCodGrupoCia_in
        AND P.COD_LOCAL=cCodLocal_in
        AND ((P.FEC_INICIO<=pFecInicio AND P.FEC_FIN>= pFecInicio) OR  (P.FEC_INICIO<=pFecFin AND  P.FEC_FIN>=pFecFin));
  
      SELECT  to_char(S.FEC_INICIO,'day')||to_char(S.FEC_INICIO,'dd/mm/yyyy'),to_char(S.FEC_FIN,'day')||to_char(S.FEC_FIN,'dd/mm/yyyy')
        INTO v_FECHA_INICIO,v_FECHA_FIN FROM PBL_SOLICITUD S WHERE S.COD_GRUPO_CIA=cCodGrupoCia_in
        AND S.COD_LOCAL=cCodLocal_in
        AND S.SEC_REGISTRO= v_SEC_REGISTRO;
  
      v_MSN_RESPUESTA:='El Usuario '|| v_USUARIO || ' presenta subsidio,
     desde el '||v_FECHA_INICIO||' hasta el '|| v_FECHA_FIN;
   END IF;
  
  --MENSAJE DE RESPUESTA DE CESE
   IF v_CANT_CESE>0 THEN
       SELECT  MIN(P.SEC_REGISTRO) INTO  v_SEC_REGISTRO FROM PBL_SOLICITUD P
        WHERE P.COD_GRUPO_CIA=cCodGrupoCia_in
         AND P.COD_LOCAL=cCodLocal_in
         AND pFecInicio>=P.FEC_INICIO;
      SELECT  to_char(S.FEC_INICIO,'day')||to_char(S.FEC_INICIO,'dd/mm/yyyy')
        INTO v_FECHA_INICIO FROM PBL_SOLICITUD S WHERE S.COD_GRUPO_CIA=cCodGrupoCia_in
        AND S.COD_LOCAL=cCodLocal_in
        AND S.SEC_REGISTRO= v_SEC_REGISTRO;
   
      v_MSN_RESPUESTA:='El Usuario '|| v_USUARIO || ' esta cesado,
       desde el '||v_FECHA_INICIO;
  END IF;
 
  END IF; 
  RETURN  v_MSN_RESPUESTA;
  END ;
--*********************************************************************************    
END PTOVENTA_CONTROL_HORARIO;
/
