create or replace package PTOVENTA_AUTORIZA IS

     TYPE TYP_ARR_VARCHAR IS TABLE OF VARCHAR2(100) INDEX BY PLS_INTEGER;
     TYPE FarmaCursor IS REF CURSOR;
     W_ARRAY_VACIO TYP_ARR_VARCHAR;
     g_CONS_SERVER             VARCHAR2(20) :='10.85.8.27';
     g_NombreDirectorio       VARCHAR2(50) := 'DIR_SOLICITUDES';  
     g_VACACIONES              NUMBER:=143;
     g_SUBSIDIO                NUMBER:=144;
     g_CESE                    NUMBER:=145;
     g_REGULARIZACION          NUMBER:=152;
     g_TIPO_CORRECION          NUMBER:=153; 
     g_AUTORIZA_PLANT          NUMBER:=156;     

  /* ****************************************************************** */
  --Descripcion: Envia email de solicitud desde el local al supervisor de de zona.
  --Fecha       Usuario        Comentario
  --10/09/2015  CHUANES        Creacion 
  /* ****************************************************************** */  
   PROCEDURE AUT_P_ENVIA_MAIL_SOL(cNumRegSol_in IN CHAR);

  /* ****************************************************************** */
  --Descripcion: ENCRIPTADO
  --Fecha       Usuario        Comentario
  --11/09/2015  CHUANES        Creacion 
  /* ****************************************************************** */  
   FUNCTION AUT_F_CRYPT(vCadena IN VARCHAR2) RETURN VARCHAR2;

  /* ****************************************************************** */
  --Descripcion: APRUEBA SOLICITUD
  --Fecha       Usuario        Comentario
  --11/09/2015  CHUANES        Creacion 
  /* ****************************************************************** */  
   PROCEDURE AUT_F_APRUEBA(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR,
                           cSecRegistro_in IN CHAR);

  /* ****************************************************************** */
  --Descripcion: DESAPRUEBA SOLICITUD
  --Fecha       Usuario        Comentario
  --11/09/2015  CHUANES        Creacion 
  /* ****************************************************************** */ 
   PROCEDURE AUT_F_RECHAZA(cCodGrupoCia_in IN CHAR ,
                           cCodLocal_in    IN CHAR ,
                           cSecRegistro_in IN CHAR);

  /* ****************************************************************** */
  --Descripcion: GESTIONA APROBACIONES O RECHAZO DE SOLICITUDES
  --Fecha       Usuario        Comentario
  --11/09/2015  CHUANES        Creacion 
  /* ****************************************************************** */   
   FUNCTION AUT_F_GEST_APROBACION (cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecRegistro_in IN CHAR,
                                   nIndicador_in   IN NUMBER)
   RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Envia Email ya se respuesta o rechazo
  --Fecha       Usuario        Comentario
  --11/09/2015  CHUANES        Creacion 
  /* ****************************************************************** */   
   PROCEDURE AUT_P_MENSAJE_RPTA(cCodGrupoCia_in IN CHAR, 
                                cCodLocal_in    IN CHAR, 
                                cNumRegSol_in   IN CHAR);
  /* ****************************************************************** */
  --Descripcion: Obtiene el saludo segun la hora
  --Fecha       Usuario        Comentario
  --21/09/2015  EMAQUERA       Creacion 
  /* ****************************************************************** */ 
   FUNCTION AUT_F_DEV_SALUDO RETURN VARCHAR2;

  /* ****************************************************************** */
  --Descripcion: Obtiene la descripcion del tipo de solicitud
  --Fecha       Usuario        Comentario
  --21/09/2015  EMAQUERA       Creacion 
  /* ****************************************************************** */ 
   FUNCTION AUT_F_DES_TIPO(nCodMae_in MAESTRO_DETALLE.COD_MAES_DET %TYPE)
   RETURN VARCHAR2;
  /* ****************************************************************** */
  --Descripcion: Obtiene datos del usuario
  --Fecha       Usuario        Comentario
  --21/09/2015  EMAQUERA       Creacion 
  /* ****************************************************************** */ 
   FUNCTION AUT_F_DES_USUARIO(v_SecUsu VARCHAR2) RETURN VARCHAR2;

  /* ****************************************************************** */
  --Descripcion: Actualiza el cronograma de horario en caso exista
  --Fecha       Usuario        Comentario
  --21/09/2015  EMAQUERA       Creacion 
  /* ****************************************************************** */ 
   PROCEDURE AUT_P_UPD_DET_HORARIO(cCodGrupoCia_in IN CHAR ,
                                   cCodLocal_in    IN CHAR ,
                                   cSecRegistro_in IN CHAR);

  /* ****************************************************************** */
  --Descripcion: Re-envia solicitudes pendientes de enviar correo
  --Fecha       Usuario        Comentario
  --21/09/2015  EMAQUERA       Creacion 
  /* ****************************************************************** */ 
   PROCEDURE AUT_P_ENVIA_SOL_PEND;

  /* ****************************************************************** */
  --Descripcion: Envia el archivo adjunto por FTP a Matriz
  --Fecha       Usuario        Comentario
  --29/09/2015  EMAQUERA       Creacion 
  /* ****************************************************************** */ 
   PROCEDURE AUT_P_ENVIA_FILE_ADJ_MATRIZ;
  /* ****************************************************************** */
  --Descripcion: Ejecuta Action que indique el CAMPO
  --Fecha       Usuario        Comentario
  --29/10/2015  dubilluz       Creacion 
  /* ****************************************************************** */ 
  PROCEDURE AUT_P_ACTION_APRUEBA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cSecRegistro_in IN CHAR,
                                 cTipoAction_in  IN CHAR);   
                                 
 /* ****************************************************************** */
  --Descripcion: Actualiza solicitud cuando RRHH lo registra (G)
  --Fecha       Usuario        Comentario
  --18/09/2015  EMAQUERA       Creacion  
  /* ****************************************************************** */                                  
  PROCEDURE AUT_P_CORRECION_MARCACION(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cSecRegistro_in IN CHAR);

 /* ****************************************************************** */
  --Descripcion: Actualiza Salida Propuesta por el colaborador
  --Fecha       Usuario        Comentario
  --02/11/2015  EMAQUERA       Creacion  
  /* ****************************************************************** */                                  
  PROCEDURE AUT_P_UPD_REG_SALIDA(cCodGrupoCia_in IN CHAR ,
                                 cCodLocal_in    IN CHAR ,
                                 cSecRegistro_in IN CHAR);                                       

 /* ****************************************************************** */
  --Descripcion: Actualiza la Plantilla Aprobada x el Supervisor
  --Fecha       Usuario        Comentario
  --27/10/2015  DUBILLUZ       Creacion  
  /* ****************************************************************** */                                   
  procedure AUT_P_APRUEBA_PLANTILLA(cCodGrupoCia_in  IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecRegistro_in IN CHAR);                                
 /* ****************************************************************** */
  --Descripcion: Actualiza y/o elimina  la Plantilla Recahazada x el Supervisor
  --Fecha       Usuario        Comentario
  --12/11/2015  EMAQUERA       Creacion  
  /* ****************************************************************** */  
  procedure AUT_P_RECHAZA_PLANTILLA(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecRegistro_in IN CHAR);                                     
--*******************************************************************************************************     

END;
/
CREATE OR REPLACE package BODY PTOVENTA_AUTORIZA is

--************************************************************************************
    PROCEDURE AUT_P_ENVIA_MAIL_SOL(cNumRegSol_in IN CHAR)
    AS
  
    v_NombreArchivo   VARCHAR2(100) := '';    
    v_DescLocal       VARCHAR2(120);
    v_TipoSol         MAESTRO_DETALLE.DESCRIPCION%TYPE;
    v_NomAprobador    VARCHAR2(100);
    v_Email_Aprobador VARCHAR2(120);    
    v_DES_MENSAJE     VARCHAR2(32700);
    v_DES_INICIO      VARCHAR2(500):='<tr><th align="left" width="140px">';
    v_DES_MEDIO       VARCHAR2(500):='</th><td width="100%"><i>';
    v_DES_FIN         VARCHAR2(500):='</i></td></tr>';    
    v_REG_SOL         PBL_SOLICITUD%ROWTYPE;    

    BEGIN

    SELECT *
    INTO v_REG_SOL
    FROM PBL_SOLICITUD P
    WHERE P.SEC_REGISTRO = cNumRegSol_in; 
      
    
    --DESCRIPCION Y EMAIL DEL LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_CORTA_LOCAL
    INTO v_DescLocal 
    FROM PBL_LOCAL 
    WHERE COD_GRUPO_CIA = v_REG_SOL.COD_GRUPO_CIA
    AND COD_LOCAL = v_REG_SOL.COD_LOCAL;

    --EMAIL JEFE ZONA
    IF v_REG_SOL.COD_MAE_TIPO = g_SUBSIDIO THEN
      SELECT LLAVE_TAB_GRAL,
             DESC_CORTA
      INTO v_Email_Aprobador,
           v_NomAprobador
      FROM PBL_TAB_GRAL
      WHERE ID_TAB_GRAL =558;
    ELSE 
      SELECT M.NOM_JEFE_ZONA, M.EMAIL_JEFE_ZONA 
      INTO v_NomAprobador, v_Email_Aprobador
      FROM V_MAE_JEFE_ZONA M
      WHERE M.COD_GRUPO_CIA = v_REG_SOL.COD_GRUPO_CIA
      AND M.COD_LOCAL = v_REG_SOL.COD_LOCAL
      AND M.ESTADO='A';      
    END IF;
      
    v_NombreArchivo := NVL(v_REG_SOL.NOM_ARCHIVO,' ');
    
    --INICIO ARCHIVO
   
    DBMS_OUTPUT.PUT_LINE('GRABO ARCHIVO DE CAMBIOS');

    v_TipoSol:=AUT_F_DES_TIPO(v_REG_SOL.COD_MAE_TIPO);

    --MENSAJE DEL EMAIL
    v_DES_MENSAJE := v_DES_MENSAJE||'<TABLE BORDER="0" width="500px">'; 
    v_DES_MENSAJE := v_DES_MENSAJE||'<TR><TD COLSPAN="2"><H2>'||AUT_F_DEV_SALUDO||' '||v_NomAprobador||'</H2></TD></TR>
                     <TR><TD COLSPAN="2"><i>'||' Se ha solicitado autorizacion para '||v_TipoSol||'</i></TD></TR>'||
                    v_DES_INICIO||'Local:'||v_DES_MEDIO||v_DescLocal||v_DES_FIN||
                    v_DES_INICIO||'Fecha Solicitud: '||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_CREA,'DD/MM/YYYY HH24:MI')||v_DES_FIN||
                    v_DES_INICIO||'Solicitante: '||v_DES_MEDIO||AUT_F_DES_USUARIO(v_REG_SOL.SEC_USU_SOL)||v_DES_FIN;
                    
    IF v_REG_SOL.COD_MAE_TIPO = g_REGULARIZACION THEN      
      v_DES_MENSAJE := v_DES_MENSAJE||v_DES_INICIO||'Colaborador Asociado: '||v_DES_MEDIO||AUT_F_DES_USUARIO(v_REG_SOL.SEC_USU_COL)||v_DES_FIN||
                                      v_DES_INICIO||'Su ultima Transaccion Sistema fue :'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_FIN,'DD/MM/YYYY HH:MI AM')||v_DES_FIN||
                                      v_DES_INICIO||'Su horario planificado de salida era :'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_INICIO,'DD/MM/YYYY HH:MI AM')||v_DES_FIN||
                                      v_DES_INICIO||'La hora de salida propuesta :'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_PROPUESTA,'DD/MM/YYYY HH:MI AM')||v_DES_FIN;
    ELSIF v_REG_SOL.COD_MAE_TIPO != g_AUTORIZA_PLANT THEN      
      v_DES_MENSAJE := v_DES_MENSAJE||v_DES_INICIO||'Colaborador Asociado: '||v_DES_MEDIO||AUT_F_DES_USUARIO(v_REG_SOL.SEC_USU_COL)||v_DES_FIN||
                                      v_DES_INICIO||'Fecha Inicio:'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_INICIO,'DD/MM/YYYY')||v_DES_FIN;
    END IF;

    IF v_REG_SOL.COD_MAE_TIPO = g_VACACIONES OR v_REG_SOL.COD_MAE_TIPO = g_SUBSIDIO THEN 
      v_DES_MENSAJE := v_DES_MENSAJE||v_DES_INICIO||'Fecha Fin:'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_FIN,'DD/MM/YYYY')||v_DES_FIN;
    END IF;
    
    IF v_REG_SOL.COD_MAE_TIPO = g_TIPO_CORRECION THEN
      v_DES_MENSAJE := v_DES_MENSAJE||v_DES_INICIO||'Fecha Propuesta:'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_PROPUESTA,'DD/MM/YYYY HH:MI AM')||v_DES_FIN;
    END IF;
      
    IF v_REG_SOL.COD_MAE_TIPO != g_VACACIONES AND v_REG_SOL.COD_MAE_TIPO != g_AUTORIZA_PLANT THEN 
      v_DES_MENSAJE := v_DES_MENSAJE||v_DES_INICIO||'Motivo:'||v_DES_MEDIO||AUT_F_DES_TIPO(v_REG_SOL.COD_MAE_SUBTIPO)||v_DES_FIN;      
    END IF;

    IF v_REG_SOL.COD_MAE_TIPO = g_AUTORIZA_PLANT THEN 
      v_DES_MENSAJE := v_DES_MENSAJE||v_DES_INICIO||'Motivo:'||v_DES_MEDIO||'Se solicita autorización para la plantilla Nro '||v_REG_SOL.OBSERVACION||' que tiene horarios semanales con más de las 48 horas permitidas por ley.'||v_DES_FIN;
    END IF;    

    IF NOT v_REG_SOL.OBSERVACION IS NULL  AND v_REG_SOL.COD_MAE_TIPO != g_AUTORIZA_PLANT THEN
      v_DES_MENSAJE := v_DES_MENSAJE||'<TR><Th align=left COLSPAN=2>'||'&nbsp;</Th></TR>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TR><Th align=left COLSPAN=2>'||'Observacion: </Th></TR>'||'<TR><TD COLSPAN=2><i>'||v_REG_SOL.OBSERVACION||'</i></TD></TR>';
    END IF;

    v_DES_MENSAJE := v_DES_MENSAJE||'</TABLE>';
    
    IF v_REG_SOL.COD_MAE_TIPO = g_AUTORIZA_PLANT THEN
      v_DES_MENSAJE := v_DES_MENSAJE||'<BR><TABLE>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TR><TH>COD_PLANTILLA</TH>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TH>SECUENCIAL</TH>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TH>DESCRIPCION_ROL</TH>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TH>LUNES</TH>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TH>MARTES</TH>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TH>MIERCOLES</TH>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TH>JUEVES</TH>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TH>VIERNES</TH>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TH>SABADO</TH>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TH>DOMINGO</TH>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TH>HORAS PROGR.</TH></TR>';      
      FOR v_curPlantilla IN (SELECT A.COD_PLANTILLA, 
                             A.SEC_PLANTILLA SECUENCIAL,
                             (SELECT R.DESC_ROL FROM PBL_ROL R 
                              WHERE R.COD_ROL = A.COD_ROL) DESC_ROL,
                             (SELECT T.NOM_TURNO FROM PBL_TURNO T
                              WHERE T.COD_TURNO = A.COD_TURNO1) LUNES,
                             (SELECT T.NOM_TURNO FROM PBL_TURNO T
                              WHERE T.COD_TURNO = A.COD_TURNO2) MARTES,
                             (SELECT T.NOM_TURNO FROM PBL_TURNO T
                              WHERE T.COD_TURNO = A.COD_TURNO3) MIERCOLES,
                             (SELECT T.NOM_TURNO FROM PBL_TURNO T
                              WHERE T.COD_TURNO = A.COD_TURNO4) JUEVES,
                             (SELECT T.NOM_TURNO FROM PBL_TURNO T
                              WHERE T.COD_TURNO = A.COD_TURNO5) VIERNES,
                             (SELECT T.NOM_TURNO FROM PBL_TURNO T
                              WHERE T.COD_TURNO = A.COD_TURNO6) SABADO,
                             (SELECT T.NOM_TURNO FROM PBL_TURNO T
                              WHERE T.COD_TURNO = A.COD_TURNO7) DOMINGO,
                              A.NUM_HORA_PROG              
                      FROM PBL_DET_PLANTILLA A
                      WHERE COD_GRUPO_CIA = v_REG_SOL.Cod_Grupo_Cia
                        AND COD_LOCAL     = v_REG_SOL.Cod_Local
                        AND COD_PLANTILLA = v_REG_SOL.Observacion
                        AND NUM_HORA_PROG > 48)
        LOOP           
           v_DES_MENSAJE := v_DES_MENSAJE||'<TR><TD>'||v_curPlantilla.Cod_Plantilla||'</TD>';
           v_DES_MENSAJE := v_DES_MENSAJE||'<TD>'||v_curPlantilla.Secuencial||'</TD>';
           v_DES_MENSAJE := v_DES_MENSAJE||'<TD>'||v_curPlantilla.Desc_Rol||'</TD>';
           v_DES_MENSAJE := v_DES_MENSAJE||'<TD>'||v_curPlantilla.Lunes||'</TD>';
           v_DES_MENSAJE := v_DES_MENSAJE||'<TD>'||v_curPlantilla.Martes||'</TD>';
           v_DES_MENSAJE := v_DES_MENSAJE||'<TD>'||v_curPlantilla.Miercoles||'</TD>';
           v_DES_MENSAJE := v_DES_MENSAJE||'<TD>'||v_curPlantilla.Jueves||'</TD>';
           v_DES_MENSAJE := v_DES_MENSAJE||'<TD>'||v_curPlantilla.Viernes||'</TD>';
           v_DES_MENSAJE := v_DES_MENSAJE||'<TD>'||v_curPlantilla.Sabado||'</TD>';
           v_DES_MENSAJE := v_DES_MENSAJE||'<TD>'||v_curPlantilla.Domingo||'</TD>';
           v_DES_MENSAJE := v_DES_MENSAJE||'<TD>'||v_curPlantilla.Num_Hora_Prog||'</TD></TR>';           
        END LOOP;
        v_DES_MENSAJE := v_DES_MENSAJE||'</TABLE><BR>';
    END IF;    
    
    v_DES_MENSAJE := v_DES_MENSAJE||'<h2>NRO. SOLICITUD: '||v_REG_SOL.SEC_REGISTRO||'</h2><br>';
    v_DES_MENSAJE := v_DES_MENSAJE||
                  '<a href="http://'||g_CONS_SERVER||'/aprobacionestesting.php?key='||AUT_F_CRYPT(v_REG_SOL.COD_LOCAL||':::ptoventa.PTOVENTA_AUTORIZA.AUT_F_GEST_APROBACION('''||v_REG_SOL.COD_GRUPO_CIA||''','''||v_REG_SOL.COD_LOCAL||''','''||v_REG_SOL.SEC_REGISTRO||''',0)')||'">Aprobar</a>'||
                  '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'||
                  '<a href="http://'||g_CONS_SERVER||'/aprobacionestesting.php?key='||AUT_F_CRYPT(v_REG_SOL.COD_LOCAL||':::ptoventa.PTOVENTA_AUTORIZA.AUT_F_GEST_APROBACION('''||v_REG_SOL.COD_GRUPO_CIA||''','''||v_REG_SOL.COD_LOCAL||''','''||v_REG_SOL.SEC_REGISTRO||''',1)')||'">Rechazar</a><br>';
    DBMS_OUTPUT.PUT_LINE(v_DES_MENSAJE);
    UTIL_EMAIL_ORACLE.ENVIA_CORREO_ORACLE(v_Email_Aprobador,
                                          '',
                                          '',
                                          'Local '|| v_DescLocal ||' - '|| v_TipoSol ,
                                          '',
                                          v_DES_MENSAJE,
                                          TRIM(v_NombreArchivo),
                                          g_NombreDirectorio);                                                   
                                   
     --ACTUALIZAMOS LA SOLICITUD A ENVIADO
     UPDATE PBL_SOLICITUD S 
     SET S.EST_SOLICITUD='E',
         S.FEC_CREA=SYSDATE
     WHERE S.COD_GRUPO_CIA = v_REG_SOL.COD_GRUPO_CIA
       AND S.COD_LOCAL     = v_REG_SOL.COD_LOCAL
       AND S.SEC_REGISTRO  = v_REG_SOL.SEC_REGISTRO;
     COMMIT;
                              
      EXCEPTION
        WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE ('ERROR AL ENVIAR EMAIL..VERIFIQUE SU EMAIL EN BASE DE DATOS');                      
        DBMS_OUTPUT.PUT_LINE (SQLERRM);
  
  END;
--************************************************************************************
   FUNCTION AUT_F_CRYPT(vCadena IN VARCHAR2) 
   RETURN VARCHAR2 
   AS
   v_Data VARCHAR2(255);
    BEGIN
    v_Data := RPAD(vCadena, (TRUNC(LENGTH(vCadena) / 8) + 1) * 8, CHR(0));
    DBMS_OBFUSCATION_TOOLKIT.DESENCRYPT(INPUT_STRING     => v_Data,
                                        KEY_STRING       => 'MagicKey',
                                        ENCRYPTED_STRING => v_Data);
    RETURN UTL_RAW.CAST_TO_RAW(v_Data);
    END;
--************************************************************************************
  PROCEDURE AUT_F_APRUEBA(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in    IN CHAR,
                          cSecRegistro_in IN CHAR)
  IS
  BEGIN
    UPDATE PBL_SOLICITUD S 
    SET S.EST_SOLICITUD='A', 
        S.FEC_MOD = SYSDATE,
        S.USU_MOD  = S.COD_APROBADOR 
    WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
      AND S.COD_LOCAL     = cCodLocal_in
      AND S.SEC_REGISTRO  = cSecRegistro_in;
      
    --AUT_P_UPD_DET_HORARIO(cCodGrupoCia_in, cCodLocal_in, cSecRegistro_in);

  END;
--************************************************************************************
  PROCEDURE AUT_F_RECHAZA(cCodGrupoCia_in IN CHAR ,
                          cCodLocal_in    IN CHAR ,
                          cSecRegistro_in IN CHAR)
  IS
  BEGIN
    UPDATE PBL_SOLICITUD S 
    SET S.EST_SOLICITUD='R',
        S.FEC_MOD=SYSDATE,
        S.USU_MOD  = S.COD_APROBADOR 
    WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
      AND S.COD_LOCAL     = cCodLocal_in
      AND S.SEC_REGISTRO  = cSecRegistro_in;

  END;
--************************************************************************************
  FUNCTION AUT_F_GEST_APROBACION (cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cSecRegistro_in IN CHAR,
                                  nIndicador_in   IN NUMBER)
  RETURN CHAR                                   
  IS
  v_Resultado VARCHAR2(250):='';
  v_Estado   PBL_SOLICITUD.EST_SOLICITUD%TYPE;  
  BEGIN        
    
    SELECT EST_SOLICITUD
    INTO v_Estado
    FROM PTOVENTA.PBL_SOLICITUD
    WHERE COD_GRUPO_CIA=cCodGrupoCia_in
    AND COD_LOCAL    = cCodLocal_in
    AND SEC_REGISTRO = cSecRegistro_in;
         
  IF v_Estado  = 'A' THEN
    RETURN 'La solicitud ya se encuentra aprobada.';
  END IF;

  IF v_Estado  = 'R' THEN
    RETURN 'La solicitud se encuentra rechazada.';
  END IF;

  IF v_Estado  = 'G' THEN
    RETURN 'La solicitud ya se encuentra aprobada y registrada por RRHH.';
  END IF;
  
  IF v_Estado != 'A' AND v_Estado != 'R' AND v_Estado !='G' THEN  
    IF nIndicador_in = 0 THEN --APRUEBA    
     aut_p_action_aprueba(ccodgrupocia_in => cCodGrupoCia_in,
                          ccodlocal_in => cCodLocal_in,
                          csecregistro_in => cSecRegistro_in,
                          ctipoaction_in => 'A');
    ELSE --1 RECHAZA
     aut_p_action_aprueba(ccodgrupocia_in => cCodGrupoCia_in,
                          ccodlocal_in => cCodLocal_in,
                          csecregistro_in => cSecRegistro_in,
                          ctipoaction_in => 'R');
    END IF;
    
   --NOTIFICAR AL SOLICITANTE
    AUT_P_MENSAJE_RPTA(cCodGrupoCia_in , cCodLocal_in, cSecRegistro_in);
               
     SELECT EST_SOLICITUD
     INTO v_Estado
     FROM PTOVENTA.PBL_SOLICITUD
     WHERE COD_GRUPO_CIA=cCodGrupoCia_in
     AND COD_LOCAL    = cCodLocal_in
     AND SEC_REGISTRO = cSecRegistro_in;

     IF v_Estado = 'A' OR v_Estado = 'R' THEN
       v_Resultado:='Se ha procesado correctamente.<br>';
     ELSE
       v_Resultado:='Se ha producido un error favor intentarlo nuevamente...<br>';
       v_Resultado:=v_Resultado||'En caso el error persista comunicarse con Mesa de Ayuda.';
     END IF;
     
   END IF; 
    COMMIT;
    RETURN v_Resultado;
  EXCEPTION 
    WHEN OTHERS THEN
     ROLLBACK;
     DBMS_OUTPUT.put_line(SQLERRM);
     v_Resultado:='Se ha producido un error favor intentarlo nuevamente...<br>';
     v_Resultado:=v_Resultado||'En caso el error persista comunicarse con Mesa de Ayuda.';
     RETURN v_Resultado;     
  END;
--************************************************************************************
  PROCEDURE AUT_P_MENSAJE_RPTA(cCodGrupoCia_in IN CHAR, 
                               cCodLocal_in    IN CHAR, 
                               cNumRegSol_in   IN CHAR)
   AS
  
    v_DescLocal      VARCHAR2(120);
    v_Email           VARCHAR2(120);
    v_TipoSol         VARCHAR2(120);
    v_Accion          VARCHAR2(120);
    v_NomAprobador    VARCHAR2(100);  
    v_DES_MENSAJE     VARCHAR2(30000);
    v_DES_INICIO      VARCHAR2(500):='<tr><th align="left" width="70px">';
    v_DES_MEDIO       VARCHAR2(500):='</th><td width="100%"><i>';
    v_DES_FIN         VARCHAR2(500):='</i></td></tr>';    
    v_REG_SOL         PBL_SOLICITUD%ROWTYPE;
    
    BEGIN
  
    SELECT *
    INTO v_REG_SOL
    FROM PBL_SOLICITUD P
    WHERE P.COD_GRUPO_CIA=cCodGrupoCia_in
    AND P.COD_LOCAL = cCodLocal_in
    AND P.SEC_REGISTRO = cNumRegSol_in; 
      
    --DESCRIPCION Y EMAIL DEL LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_CORTA_LOCAL, MAIL_LOCAL
    INTO v_DescLocal, v_Email 
    FROM PBL_LOCAL l
    WHERE COD_GRUPO_CIA = v_REG_SOL.COD_GRUPO_CIA
    AND COD_LOCAL = v_REG_SOL.COD_LOCAL;

    SELECT M.NOM_JEFE_ZONA
    INTO v_NomAprobador
    FROM V_MAE_JEFE_ZONA M
    WHERE M.COD_GRUPO_CIA = v_REG_SOL.COD_GRUPO_CIA
    AND M.COD_LOCAL = v_REG_SOL.COD_LOCAL
    AND M.ESTADO='A'; 
    
    --INICIO ARCHIVO       
    v_TipoSol:= AUT_F_DES_TIPO(v_REG_SOL.COD_MAE_TIPO);
    SELECT DECODE(v_REG_SOL.EST_SOLICITUD,'A','APROBADO','R','RECHAZADO','PRODUCIDO UN ERROR')
    INTO v_Accion FROM DUAL;
    --MENSAJE DEL EMAIL
    v_DES_MENSAJE := v_DES_MENSAJE||'<TABLE BORDER="0" width="500px">'; 
    v_DES_MENSAJE := v_DES_MENSAJE||'<TR><TD COLSPAN="2"><H2>'||AUT_F_DEV_SALUDO||' '||AUT_F_DES_USUARIO(v_REG_SOL.SEC_USU_SOL)||'</H2></TD></TR>
                     <TR><TD COLSPAN="2"><i>'||' Se ha <b>'||v_Accion||'</b> su solicitud de '||v_TipoSol||'</i></TD></TR>'||
                    v_DES_INICIO||'Local:'||v_DES_MEDIO||v_DescLocal||v_DES_FIN||
                    v_DES_INICIO||'Fecha Solicitud: '||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_CREA,'DD/MM/YYYY HH24:MI')||v_DES_FIN||
                    v_DES_INICIO||'Solicitante: '||v_DES_MEDIO||AUT_F_DES_USUARIO(v_REG_SOL.SEC_USU_SOL)||v_DES_FIN||
                    v_DES_INICIO||'Colaborador Asociado: '||v_DES_MEDIO||AUT_F_DES_USUARIO(v_REG_SOL.SEC_USU_COL)||v_DES_FIN||
                    v_DES_INICIO||'Fecha Inicio:'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_INICIO,'DD/MM/YYYY')||v_DES_FIN;

    IF v_REG_SOL.COD_MAE_TIPO = g_REGULARIZACION THEN
      v_DES_MENSAJE := v_DES_MENSAJE||v_DES_INICIO||'Su ultima Transaccion Sistema fue :'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_FIN,'DD/MM/YYYY HH:MI AM')||v_DES_FIN||
                                      v_DES_INICIO||'Su horario planificado de salida era :'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_INICIO,'DD/MM/YYYY HH:MI AM')||v_DES_FIN||
                                      v_DES_INICIO||'La hora de salida propuesta :'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_PROPUESTA,'DD/MM/YYYY HH:MI AM')||v_DES_FIN;
    ELSE
      v_DES_MENSAJE := v_DES_MENSAJE||v_DES_INICIO||'Fecha Inicio:'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_INICIO,'DD/MM/YYYY')||v_DES_FIN;
    END IF;

    IF v_REG_SOL.COD_MAE_TIPO = g_VACACIONES OR v_REG_SOL.COD_MAE_TIPO = g_SUBSIDIO THEN 
      v_DES_MENSAJE := v_DES_MENSAJE||v_DES_INICIO||'Fecha Fin:'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_FIN,'DD/MM/YYYY')||v_DES_FIN;
    END IF;
    
    IF v_REG_SOL.COD_MAE_TIPO = g_TIPO_CORRECION THEN
      v_DES_MENSAJE := v_DES_MENSAJE||v_DES_INICIO||'Fecha Propuesta:'||v_DES_MEDIO||TO_CHAR(v_REG_SOL.FEC_PROPUESTA,'DD/MM/YYYY HH:MI AM')||v_DES_FIN;
    END IF;
      
    IF v_REG_SOL.COD_MAE_TIPO != g_VACACIONES OR v_REG_SOL.COD_MAE_TIPO != g_AUTORIZA_PLANT THEN 
      v_DES_MENSAJE := v_DES_MENSAJE||v_DES_INICIO||'Motivo:'||v_DES_MEDIO||AUT_F_DES_TIPO(v_REG_SOL.COD_MAE_SUBTIPO)||v_DES_FIN;
    END IF;

    IF NOT v_REG_SOL.OBSERVACION IS NULL THEN
      v_DES_MENSAJE := v_DES_MENSAJE||'<TR><Th align=left COLSPAN=2>'||'&nbsp;</Th></TR>';
      v_DES_MENSAJE := v_DES_MENSAJE||'<TR><Th align=left COLSPAN=2>'||'Observacion: </Th></TR>'||'<TR><TD COLSPAN=2><i>'||v_REG_SOL.OBSERVACION||'</i></TD></TR>';
    END IF;
    v_DES_MENSAJE := v_DES_MENSAJE||'<TR><Th align=left COLSPAN=2></Th></TR>';
    v_DES_MENSAJE := v_DES_MENSAJE||'<TR><Th align=left COLSPAN=2>Atentamente;</Th></TR>';
    v_DES_MENSAJE := v_DES_MENSAJE||'<TR><Th align=left COLSPAN=2>'||v_NomAprobador||'</Th></TR>';

    v_DES_MENSAJE := v_DES_MENSAJE||'</TABLE>';
    
  
                             
     FARMA_EMAIL.envia_correo('oracle@mifarma.com.pe',--REMITENTE
                               v_Email,        --Para
                               'Local '||v_DescLocal||' - Solicitud:'||v_REG_SOL.SEC_REGISTRO||' - '||v_TipoSol, --MENSAJE
                               '',                               --Titulo
                               v_DES_MENSAJE,                    --Mensaje
                                '',                              --Con Copia
                               FARMA_EMAIL.GET_EMAIL_SERVER,
                               TRUE);       
  
     COMMIT;                                    
      EXCEPTION
        WHEN OTHERS THEN
        ROLLBACK; 
        DBMS_OUTPUT.PUT_LINE ('ERROR AL ENVIAR EMAIL..VERIFIQUE SU EMAIL EN BASE DE DATOS');                      
  END;
--************************************************************************************
FUNCTION AUT_F_DEV_SALUDO RETURN VARCHAR2
IS
v_Hora    INTEGER:= TO_NUMBER(TO_CHAR(SYSDATE,'HH24'));
v_Saludo  VARCHAR2(100);
BEGIN
     SELECT
       CASE
        WHEN v_Hora  >=0 AND v_Hora <12 THEN   'Buenos dias'
        WHEN v_Hora  >=12 AND v_Hora <19 THEN  'Buenas tardes'
        WHEN v_Hora  >=19 AND v_Hora <=24 THEN  'Buenas noches'
      END
      INTO v_Saludo
     FROM DUAl;
RETURN v_Saludo;   
END;
--************************************************************************************
FUNCTION AUT_F_DES_TIPO(nCodMae_in MAESTRO_DETALLE.COD_MAES_DET %TYPE)
   RETURN VARCHAR2 
 AS
     v_Result VARCHAR2(120);
BEGIN

     BEGIN
          SELECT TRIM(DESCRIPCION)
            INTO v_Result
            FROM MAESTRO_DETALLE
           WHERE COD_MAES_DET = nCodMae_in;
     EXCEPTION
          WHEN NO_DATA_FOUND THEN
               v_Result := NULL;
     END;

     RETURN v_Result;

END;
--************************************************************************************
FUNCTION AUT_F_DES_USUARIO(v_SecUsu VARCHAR2)
   RETURN VARCHAR2 
 AS
     v_Result VARCHAR2(200);
BEGIN

     BEGIN           
       SELECT A.APE_PAT||' '||A.APE_MAT||', '||A.NOM_USU
       INTO v_Result
       FROM  PBL_USU_LOCAL A
       WHERE SEC_USU_LOCAL=v_SecUsu;
     EXCEPTION
          WHEN NO_DATA_FOUND THEN
               v_Result := NULL;
     END;

     RETURN v_Result;

END;
--************************************************************************************
  PROCEDURE AUT_P_UPD_DET_HORARIO(cCodGrupoCia_in IN CHAR ,
                                  cCodLocal_in    IN CHAR ,
                                  cSecRegistro_in IN CHAR)
  IS
  CURSOR SEMANAL(v_SecUsu IN CHAR, v_Fec_Ini IN DATE, v_Fec_Fin IN DATE) IS 
    SELECT C.*, D.SEC_USU_LOCAL
    FROM PBL_CAB_HORARIO C,
         PBL_DET_HORARIO D
    WHERE C.COD_GRUPO_CIA=D.COD_GRUPO_CIA
    AND C.COD_LOCAL=D.COD_LOCAL
    AND C.COD_HORARIO=D.COD_HORARIO     
    AND D.SEC_USU_LOCAL=v_SecUsu
    AND FEC_INICIO <= TO_DATE(TO_CHAR(v_Fec_Fin,'DD/MM/YYYY'),'DD/MM/YYYY')
    AND FEC_FIN    >= TO_DATE(TO_CHAR(v_Fec_Ini,'DD/MM/YYYY'),'DD/MM/YYYY');
    
    v_REG_SOL    PBL_SOLICITUD%ROWTYPE;
    v_SQL        VARCHAR2(3000):='';
    V_QUERY      VARCHAR2(30000):='';
    v_Fecha      DATE;
    v_cont       number:=1;
    curFecha     FarmaCursor;
    v_CodTurno   PBL_TURNO.COD_TURNO%TYPE;
    
  BEGIN
    SELECT *
    INTO v_REG_SOL
    FROM PBL_SOLICITUD S 
    WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
      AND S.COD_LOCAL     = cCodLocal_in
      AND S.SEC_REGISTRO  = cSecRegistro_in;
     
    SELECT T.COD_TURNO
    INTO v_CodTurno
    FROM PBL_TURNO T
    WHERE T.IND_CONCEPTO = DECODE(v_REG_SOL.COD_MAE_TIPO,143,'V',144,'S',145,'C','X');

    FOR c_SEMANA IN SEMANAL(v_REG_SOL.SEC_USU_COL, v_REG_SOL.FEC_INICIO, v_REG_SOL.FEC_FIN)
      LOOP
         v_SQL:=v_SQL||' SELECT N.FECHA FROM (';
         FOR i IN 0..6 
          LOOP
            IF i = 6 THEN   
               v_SQL:=v_SQL||' SELECT TO_DATE('''||TO_CHAR(c_SEMANA.FEC_INICIO,'DD/MM/YYYY')||''',''DD/MM/YYYY'')+'||i||' AS FECHA FROM DUAL ';
            ELSE
               v_SQL:=v_SQL||' SELECT TO_DATE('''||TO_CHAR(c_SEMANA.FEC_INICIO,'DD/MM/YYYY')||''',''DD/MM/YYYY'')+'||i||' AS FECHA FROM DUAL UNION ';
            END IF;
          END LOOP;
         v_SQL:=v_SQL||' ) N ';
         v_SQL:=v_SQL||' WHERE N.FECHA >= TO_DATE('''||TO_CHAR(v_REG_SOL.FEC_INICIO,'DD/MM/YYYY')||''',''DD/MM/YYYY'')';
         v_SQL:=v_SQL||' AND N.FECHA <= TO_DATE('''||TO_CHAR(v_REG_SOL.FEC_FIN,'DD/MM/YYYY')||''',''DD/MM/YYYY'')';
         
         V_QUERY:=V_QUERY||'UPDATE PBL_DET_HORARIO D ';
         V_QUERY:=V_QUERY||'SET ';
         OPEN curFecha FOR v_SQL;
          LOOP
           FETCH curFecha INTO v_Fecha;
           EXIT WHEN curFecha%NOTFOUND;

            IF v_cont = 1 THEN
             V_QUERY:=V_QUERY||' COD_DIA'||TO_CHAR(v_Fecha,'D')||'='''||v_CodTurno||'''';
            ELSE 
             V_QUERY:=V_QUERY||', COD_DIA'||TO_CHAR(v_Fecha,'D')||'='''||v_CodTurno||'''';
            END IF;
            v_cont:=v_cont+1;
          END LOOP;
          V_QUERY:=V_QUERY||' WHERE COD_GRUPO_CIA = '''||cCodGrupoCia_in||'''';
          V_QUERY:=V_QUERY||'   AND COD_LOCAL     = '''||cCodLocal_in||'''';
          V_QUERY:=V_QUERY||'   AND COD_HORARIO   = '''||c_SEMANA.Cod_Horario||''''; 
          V_QUERY:=V_QUERY||'   AND SEC_USU_LOCAL = '''||c_SEMANA.Sec_Usu_Local||''''; 
          
          EXECUTE IMMEDIATE V_QUERY;
          --Inicializando Variables
          v_SQL:=''; 
          v_cont:=1;
      END LOOP;
  END;
--************************************************************************************
  PROCEDURE AUT_P_ENVIA_SOL_PEND AS
    CURSOR curSolicitudes IS
      SELECT P.SEC_REGISTRO
      FROM PBL_SOLICITUD P
      WHERE P.EST_SOLICITUD='C'
      AND P.FEC_CREA>=TRUNC(SYSDATE);
  BEGIN
    FOR v_Sol IN curSolicitudes
     LOOP
       BEGIN         
        AUT_P_ENVIA_MAIL_SOL(v_Sol.SEC_REGISTRO);       
       EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
       END;
     END LOOP;  
  END;
--************************************************************************************
  PROCEDURE AUT_P_ENVIA_FILE_ADJ_MATRIZ AS
    CURSOR curArchivos IS
      SELECT P.COD_LOCAL, P.NOM_ARCHIVO
      FROM PBL_SOLICITUD P
      WHERE LENGTH(P.NOM_ARCHIVO)>16
      AND P.EST_SOLICITUD IN ('E', 'A')
      AND P.FEC_CREA>=TRUNC(SYSDATE);
  BEGIN
    FOR v_FILE IN curArchivos
     LOOP
       BEGIN     
        ptoventa_ftp.FTP_P_ENVIA_FILE_SOL(10,
                                          v_FILE.NOM_ARCHIVO,
                                          v_FILE.COD_LOCAL);     
       EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
       END;
     END LOOP;  
  END;
--************************************************************************************
PROCEDURE AUT_P_ACTION_APRUEBA(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cSecRegistro_in IN CHAR,
                               cTipoAction_in  IN CHAR)
  IS
   vAction PBL_SOLICITUD.Action_Aprueba%type;
  BEGIN
    select CASE
            WHEN cTipoAction_in = 'A' THEN S.ACTION_APRUEBA
            WHEN cTipoAction_in = 'R' THEN S.ACTION_RECHAZA  
            ELSE NULL
           END    
    into   vAction
    from   PBL_SOLICITUD S 
    WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
      AND S.COD_LOCAL     = cCodLocal_in
      AND S.SEC_REGISTRO  = cSecRegistro_in;
     
    IF vAction IS NOT NULL AND LENGTH(vAction)>0 THEN  
        execute immediate 'begin '||vAction||'; end;';
    END IF;

    UPDATE PBL_SOLICITUD S 
    SET S.EST_SOLICITUD = cTipoAction_in, 
        S.FEC_MOD = SYSDATE,
        S.USU_MOD  = S.COD_APROBADOR 
    WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
      AND S.COD_LOCAL     = cCodLocal_in
      AND S.SEC_REGISTRO  = cSecRegistro_in;

  END;
--************************************************************************************  
PROCEDURE AUT_P_CORRECION_MARCACION(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecRegistro_in IN CHAR)
  is
  v_FilaSolicitud PBL_SOLICITUD%rowtype;
  C_ENTRADA  NUMBER := 154;
  C_SALIDA   NUMBER := 155;

begin                      
  
  SELECT *
  INTO   v_FilaSolicitud
  FROM   PBL_SOLICITUD A
  WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
  AND    A.COD_LOCAL  = cCodLocal_in
  AND    A.SEC_REGISTRO = cSecRegistro_in;  
  
  if v_FilaSolicitud.Cod_Mae_Tipo =  G_TIPO_CORRECION then
  if v_FilaSolicitud.Cod_Mae_Subtipo =  C_ENTRADA then  
      update pbl_ingreso_personal i
      SET    I.ENTRADA = v_FilaSolicitud.Fec_Propuesta,
             I.FEC_MOD = SYSDATE
      WHERE  I.DNI = (select u.dni_usu
                      from pbl_usu_local u 
                      where u.cod_grupo_cia =  cCodGrupoCia_in
                      and   u.cod_local = cCodLocal_in
                      and   u.sec_usu_local =v_FilaSolicitud.Sec_Usu_Col)
      AND    I.COD_GRUPO_CIA =cCodGrupoCia_in 
      AND    I.COD_LOCAL = cCodLocal_in
      AND    I.FECHA = trunc(v_FilaSolicitud.Fec_Inicio);
   ELSIF v_FilaSolicitud.Cod_Mae_Subtipo =  C_SALIDA then  
      update pbl_ingreso_personal i
      SET    I.SALIDA = v_FilaSolicitud.Fec_Propuesta,
             I.FEC_MOD = SYSDATE
      WHERE  I.DNI  = (select u.dni_usu
                      from pbl_usu_local u 
                      where u.cod_grupo_cia =  cCodGrupoCia_in
                      and   u.cod_local = cCodLocal_in
                      and   u.sec_usu_local =v_FilaSolicitud.Sec_Usu_Col)
      AND    I.COD_GRUPO_CIA =cCodGrupoCia_in 
      AND    I.COD_LOCAL = cCodLocal_in
      AND    I.FECHA =  trunc(v_FilaSolicitud.Fec_Inicio);   
   END IF;   
  end if;  
end;
--*************************************************************************************  
  PROCEDURE AUT_P_UPD_REG_SALIDA(cCodGrupoCia_in IN CHAR ,
                                 cCodLocal_in    IN CHAR ,
                                 cSecRegistro_in IN CHAR)
  IS    
    v_REG_SOL    PBL_SOLICITUD%ROWTYPE;
    v_REG_ING    PBL_INGRESO_PERSONAL%ROWTYPE;
    v_Dni        PBL_USU_LOCAL.DNI_USU%TYPE;
  BEGIN
    SELECT *
    INTO v_REG_SOL
    FROM PBL_SOLICITUD S 
    WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
      AND S.COD_LOCAL     = cCodLocal_in
      AND S.SEC_REGISTRO  = cSecRegistro_in;
    
    SELECT U.DNI_USU
    INTO v_Dni
    FROM PBL_USU_LOCAL U
    WHERE U.SEC_USU_LOCAL = v_REG_SOL.Sec_Usu_Col;
    
    SELECT * 
    INTO v_REG_ING
    FROM PBL_INGRESO_PERSONAL I
    WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
      AND I.COD_LOCAL     = cCodLocal_in
      AND I.DNI           = v_Dni
      AND I.FECHA         = TO_CHAR(v_REG_SOL.Fec_Inicio,'DD/MM/YYYY');
      
      
    IF v_REG_ING.Entrada_2 IS NULL THEN
      UPDATE PBL_INGRESO_PERSONAL I
      SET I.SALIDA  = v_REG_SOL.Fec_Propuesta,
          I.USU_MOD = 'GEST_SOLICITUD',
          I.FEC_MOD = SYSDATE
      WHERE I.COD_GRUPO_CIA = v_REG_SOL.Cod_Grupo_Cia
        AND I.COD_LOCAL     = v_REG_SOL.Cod_Local
        AND I.DNI           = v_Dni
        AND I.FECHA         = TO_CHAR(v_REG_SOL.Fec_Inicio,'DD/MM/YYYY');
    ELSE 
      UPDATE PBL_INGRESO_PERSONAL I
      SET I.SALIDA_2 = v_REG_SOL.Fec_Propuesta,
          I.USU_MOD  = 'GEST_SOLICITUD',
          I.FEC_MOD  = SYSDATE
      WHERE I.COD_GRUPO_CIA = v_REG_SOL.Cod_Grupo_Cia
        AND I.COD_LOCAL     = v_REG_SOL.Cod_Local
        AND I.DNI           = v_Dni
        AND I.FECHA         = TO_CHAR(v_REG_SOL.Fec_Inicio,'DD/MM/YYYY');      
    END IF;    
  END;
--********************************************************************************
  procedure AUT_P_APRUEBA_PLANTILLA(cCodGrupoCia_in  IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecRegistro_in IN CHAR) is
  v_REG_SOL    PBL_SOLICITUD%ROWTYPE;
  BEGIN
    SELECT *
    INTO v_REG_SOL
    FROM PBL_SOLICITUD S 
    WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
      AND S.COD_LOCAL     = cCodLocal_in
      AND S.SEC_REGISTRO  = cSecRegistro_in;
          
    UPDATE PBL_CAB_PLANTILLA C
    SET    C.EST_PLANTILLA = 'C',
           C.USU_MOD  = 'SISTEMAS',
           C.FEC_MOD  = SYSDATE
    WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    C.COD_LOCAL = cCodLocal_in
    AND    C.COD_PLANTILLA =v_REG_SOL.Observacion;        
  END;  
--************************************************************************************
  procedure AUT_P_RECHAZA_PLANTILLA(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecRegistro_in IN CHAR) is
  v_REG_SOL    PBL_SOLICITUD%ROWTYPE;
  v_cant       NUMBER(4);
  BEGIN
    SELECT *
    INTO v_REG_SOL
    FROM PBL_SOLICITUD S 
    WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
      AND S.COD_LOCAL     = cCodLocal_in
      AND S.SEC_REGISTRO  = cSecRegistro_in;

    DELETE FROM PBL_DET_PLANTILLA D
    WHERE COD_PLANTILLA = v_REG_SOL.Observacion
    AND D.NUM_HORA_PROG > 48;
    
    SELECT COUNT(*) 
    INTO v_cant
    FROM PBL_DET_PLANTILLA
    WHERE COD_PLANTILLA = v_REG_SOL.Observacion;
    
    IF v_cant > 0 THEN
      UPDATE PBL_CAB_PLANTILLA C
      SET    C.EST_PLANTILLA = 'N',
             C.USU_MOD  = 'SISTEMAS',
             C.FEC_MOD  = SYSDATE
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL = cCodLocal_in
      AND    C.COD_PLANTILLA =v_REG_SOL.Observacion; 
    ELSE
      DELETE FROM PBL_CAB_PLANTILLA C
      WHERE COD_PLANTILLA = v_REG_SOL.Observacion;
    END IF;
    COMMIT;       
  END;  
--************************************************************************************
END ;
/
