create or replace package PTOVENTA_GEST_SOLICITUD is
  
  TYPE FarmaCursor IS REF CURSOR;
  g_CodMaestro        NUMBER:=19;
  g_CodMaestroDet     NUMBER:=20;
  g_CodNumera         CHAR(3):='610';
  G_EST_SOL_RECHAZADA CHAR(1) := 'R';
  
  G_TIPO_VACACIONES NUMBER:=143;
  G_TIPO_SUBSIDIO   NUMBER:=144;
  G_TIPO_CESE       NUMBER:=145;
  G_TIPO_REG_SALIDA NUMBER:=152;
  G_TIPO_CORRECION  NUMBER:=153;
  G_TIPO_AUTORIZA_PLANTILLA  NUMBER:=156;
          

  /* ****************************************************************** */
  --Descripcion: Lista solicitudes por rango de fecha
  --Fecha       Usuario        Comentario
  --07/09/2015  ASOSA          Creacion 
  /* ****************************************************************** */  
  FUNCTION GEST_F_LIST_SOLICITUD(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in     IN CHAR,
                                 nDias_in         IN NUMBER)
  RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: Valida la existencia del usuario
  --Fecha       Usuario        Comentario
  --09/09/2015  CHUANES        Creacion 
  /* ****************************************************************** */
    FUNCTION GEST_F_VALIDA_USU(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cDni_in         IN CHAR)
    RETURN FarmaCursor;
  /* ****************************************************************** */
  --Descripcion: Graba el registro de solicitudes
  --Fecha       Usuario        Comentario
  --09/09/2015  CHUANES        Creacion 
  /* ****************************************************************** */  
    FUNCTION GEST_F_GRABA_SOLICITUD(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cDni_in         IN CHAR,
                                    cFechaInicio_in IN CHAR,
                                    cFechaFin_in    IN CHAR,
                                    cCodMaeTipo_in  IN CHAR,
                                    cCodMaeTipo2_in IN CHAR,
                                    vNomArchivo_in  IN VARCHAR2,
                                    cFecPropuesta   IN CHAR,
                                    vObservacion_in IN VARCHAR2,
                                    vUsuCreacion_in IN CHAR,
                                    cSecUsuCrea_in  IN CHAR
                                )
    RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Listado de subtipos de solicitudes
  --Fecha       Usuario        Comentario
  --09/09/2015  CHUANES        Creacion 
  /* ****************************************************************** */  
   FUNCTION GEST_F_LISTA_SUBTIPOS(cCodMaestro_in IN CHAR,
                                  cValor_in      IN CHAR)
   RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: Valida Cruces de fecha por usuario y tipo de solictud
  --Fecha       Usuario        Comentario
  --09/09/2015  CHUANES        Creacion  
  /* ****************************************************************** */  
    FUNCTION GEST_F_VALIDA_CRUCE_FECHA(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cDni_in           IN CHAR,
                                       cFechaInicio_in   IN CHAR,
                                       cFechaFin_in      IN CHAR,
                                       cTipoSolicitud_in IN CHAR)
    RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Buscar Solicitud por rango de fechas
  --Fecha       Usuario        Comentario
  --09/09/2015  CHUANES        Creacion      
  /* ****************************************************************** */  
  FUNCTION GEST_F_LIST_SOLICITUD(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cFechaInicio_in IN CHAR,
                                 cFechaFin_in    IN CHAR,
                                 cEstSol_in      IN CHAR)
  RETURN FarmaCursor;
  
  /* ****************************************************************** */
  --Descripcion: Verifica si la fecha de Inicio es mayor
  --Fecha       Usuario        Comentario
  --09/09/2015  CHUANES        Creacion  
  /* ****************************************************************** */  
  FUNCTION GEST_F_VALIDA_FEC_INICIO(cFecInicio_in IN CHAR)
  RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Verifica si la fecha es menor igual a la fecha actual
  --Fecha       Usuario        Comentario
  --03/12/2015  EMAQUERA       Creacion  
  /* ****************************************************************** */  
  FUNCTION GEST_F_VALIDA_FEC_CORRECION(cFecInicio_in IN CHAR)
  RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Obtiene la conexion remota de del ftp
  --Fecha       Usuario        Comentario
  --15/09/2015  CHUANES        Creacion  
  /* ****************************************************************** */  
  FUNCTION GEST_F_CNX_FTP(cCodGrupoCia_in IN CHAR,
							            cCodCia_in      IN CHAR,
                          cCodLocal_in    IN CHAR
                          ) 
  RETURN VARCHAR2;
                           
  /* ****************************************************************** */
  --Descripcion: Actualiza el nombre del archivo
  --Fecha       Usuario        Comentario
  --15/09/2015  CHUANES        Creacion  
  /* ****************************************************************** */  
  PROCEDURE GEST_P_UPD_FILENAME(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumRegSol_in   IN CHAR,
                                vNomArchivo_in  IN CHAR);
  /* ****************************************************************** */
  --Descripcion: Devuelve el codigo maestro
  --Fecha       Usuario        Comentario
  --18/09/2015  EMAQUERA       Creacion  
  /* ****************************************************************** */  
  FUNCTION GEST_F_COD_MAE(nCodTipo_in MAESTRO_DETALLE.COD_MAESTRO%TYPE, 
                          vValor_in   MAESTRO_DETALLE.VALOR1%TYPE)
  RETURN VARCHAR2;

  /* ****************************************************************** */
  --Descripcion: Devuelve el codigo maestro detalle
  --Fecha       Usuario        Comentario
  --01/11/2015  EMAQUERA       Creacion  
  /* ****************************************************************** */ 
  FUNCTION GEST_F_COD_MAE_DET(nCodTipo_in MAESTRO_DETALLE.COD_MAESTRO%TYPE, 
                              vValor1_in   MAESTRO_DETALLE.VALOR1%TYPE,
                              vValor2_in   MAESTRO_DETALLE.VALOR2%TYPE)
  RETURN VARCHAR2;
       
  /* ****************************************************************** */
  --Descripcion: Envia las solicitudes creadas y que no se enviaron
  --Fecha       Usuario        Comentario
  --18/09/2015  EMAQUERA       Creacion  
  /* ****************************************************************** */  
  PROCEDURE GEST_P_ERROR_SOL(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR,
                             cNumRegSol_in   IN CHAR);

  /* ****************************************************************** */
  --Descripcion: Actualiza solicitud cuando RRHH lo registra (G)
  --Fecha       Usuario        Comentario
  --18/09/2015  EMAQUERA       Creacion  
  /* ****************************************************************** */  
  PROCEDURE GEST_P_UPD_REG_SOL (v_CodLocal IN CHAR,
                                v_SecReg   IN CHAR,
                                v_UsuMod IN VARCHAR2);
                                
--*******************************************************************************************************                               
END PTOVENTA_GEST_SOLICITUD;
/
create or replace package body PTOVENTA_GEST_SOLICITUD is

--*********************************************************************************

  FUNCTION GEST_F_LIST_SOLICITUD(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in     IN CHAR,
                                 nDias_in         IN NUMBER)
  RETURN FarmaCursor
  IS
     v_curLista FarmaCursor;
  BEGIN
    OPEN v_curLista FOR
     SELECT U.DNI_USU || 'Ã' ||
            U.LOGIN_USU || 'Ã' ||
            to_char(fec_inicio,'dd/mm/yyyy') || 'Ã' ||
            nvl(to_char(fec_fin,'dd/mm/yyyy'),' ') || 'Ã' ||
            DECODE(est_solicitud,
                    'C','CREADO',
                    'E','ENVIADO',
                    'A','APROBADO',
                    'R','RECHAZADO',
                    'G','REGISTRADO') || 'Ã' ||
            nvl((SELECT  M.DESCRIPCION FROM MAESTRO_DETALLE M 
                 WHERE M.COD_MAES_DET=S.COD_MAE_TIPO),' ')|| 'Ã' ||
            nvl((SELECT  M.DESCRIPCION FROM MAESTRO_DETALLE M 
                 WHERE M.COD_MAES_DET=S.COD_MAE_SUBTIPO),' ')           
    FROM PBL_SOLICITUD S,
         PBL_USU_LOCAL U
    WHERE S.COD_GRUPO_CIA = U.COD_GRUPO_CIA
    AND S.COD_LOCAL = U.COD_LOCAL
    AND S.Sec_Usu_Col=U.SEC_USU_LOCAL
    AND S.COD_GRUPO_CIA = cCodGrupoCia_in
    AND S.COD_LOCAL = cCodLocal_in
    AND S.FEC_CREA>=TRUNC(SYSDATE-nDias_in)
    AND S.COD_MAE_TIPO NOT IN (G_TIPO_REG_SALIDA, G_TIPO_AUTORIZA_PLANTILLA)
    ORDER BY S.SEC_REGISTRO DESC;
    
    RETURN  v_curLista;
  END;
  
--*********************************************************************************

    FUNCTION GEST_F_VALIDA_USU(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cDni_in         IN CHAR)
    RETURN FarmaCursor
    IS
        v_curUsuario FarmaCursor;
    BEGIN
       OPEN v_curUsuario FOR
        SELECT USU.APE_PAT ||' '|| 
               USU.APE_MAT ||', '||
               USU.NOM_USU AS "NOMBRE"
        FROM  PBL_USU_LOCAL USU 
        WHERE USU.COD_GRUPO_CIA = cCodGrupoCia_in
          AND USU.COD_LOCAL     = cCodLocal_in
          AND USU.DNI_USU       = cDni_in;
        RETURN v_curUsuario;
    END;
  
--*********************************************************************************

    FUNCTION GEST_F_GRABA_SOLICITUD(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cDni_in         IN CHAR,
                                    cFechaInicio_in IN CHAR,
                                    cFechaFin_in    IN CHAR,
                                    
                                    cCodMaeTipo_in  IN CHAR,
                                    cCodMaeTipo2_in IN CHAR,
                                    vNomArchivo_in  IN VARCHAR2,
                                    cFecPropuesta   IN CHAR,
                                    vObservacion_in IN VARCHAR2,
                                    
                                    vUsuCreacion_in IN CHAR,
                                    cSecUsuCrea_in  IN CHAR)
    RETURN CHAR
    IS
    v_Flag          CHAR(10):='X';
    v_SecUsu         CHAR(3);
    VNUMREGISTROSOL PBL_SOLICITUD.SEC_REGISTRO%TYPE;
    v_CodAprobador  CHAR(3);
    v_DescAprobador VARCHAR2(100);
    v_CodTipo       MAESTRO_DETALLE.COD_MAES_DET%TYPE;
    v_CodMotivo     MAESTRO_DETALLE.COD_MAES_DET%TYPE;
    v_FechaInicio   DATE;
    v_FechaFin      DATE;
    v_FecPropuesta  DATE;
    BEGIN

    VNUMREGISTROSOL := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.Obtener_Numeracion(cCodGrupoCia_in, cCodLocal_in,g_CodNumera),
                                                            10,
                                                            '0',
                                                            'I');
  

     Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               g_CodNumera,
                                               vUsuCreacion_in); 
  
      v_CodTipo   := GEST_F_COD_MAE(g_CodMaestro, cCodMaeTipo_in);
      v_CodMotivo := GEST_F_COD_MAE_DET(g_CodMaestroDet, cCodMaeTipo_in, cCodMaeTipo2_in);
      
      IF v_CodTipo = PTOVENTA_CONTROL_ASISTENCIA.g_TIPO_SOL_REGULARIZACION THEN        
         SELECT TO_DATE(cFechaInicio_in,'DD/MM/YYYY HH24:MI'), 
                TO_DATE(cFechaFin_in,'DD/MM/YYYY HH24:MI'),
                TO_DATE(cFecPropuesta,'DD/MM/YYYY HH24:MI')
         INTO v_FechaInicio, v_FechaFin, v_FecPropuesta
         FROM DUAL;
      ELSE
         SELECT TO_DATE(SUBSTR(cFechaInicio_in,0,10),'DD/MM/YYYY'), 
                TO_DATE(SUBSTR(cFechaFin_in,0,10),'DD/MM/YYYY'),
                TO_DATE(cFecPropuesta,'dd/mm/yyyy HH24:MI:SS')
         INTO v_FechaInicio, v_FechaFin, v_FecPropuesta
         FROM DUAL;        
      END IF;
      
      begin
      SELECT  USU.SEC_USU_LOCAL 
      INTO v_SecUsu 
      FROM PBL_USU_LOCAL USU 
      WHERE USU.COD_GRUPO_CIA=cCodGrupoCia_in
      AND USU.COD_LOCAL=cCodLocal_in
      AND USU.DNI_USU=cDni_in;
      exception 
        when others then
          null;
      end;
      
      SELECT Z.COD_ZONA_VTA, NVL(Z.NOM_JEFE_ZONA,' ')
      INTO v_CodAprobador, v_DescAprobador
      FROM V_MAE_JEFE_ZONA Z
      WHERE COD_GRUPO_CIA=cCodGrupoCia_in
      AND COD_LOCAL=cCodLocal_in;
      
      INSERT INTO PBL_SOLICITUD(COD_GRUPO_CIA, 
                                COD_LOCAL, 
                                SEC_REGISTRO, 
                                FEC_INICIO, 
                                FEC_FIN, 
                                EST_SOLICITUD, 
                                COD_MAE_TIPO, 
                                COD_MAE_SUBTIPO, 
                                OBSERVACION, 
                                NOM_ARCHIVO,
                                FEC_PROPUESTA, 
                                FEC_CREA, 
                                USU_CREA, 
                                SEC_USU_SOL, 
                                SEC_USU_COL, 
                                COD_APROBADOR, 
                                DESC_APROBADOR)
      VALUES(cCodGrupoCia_in,
             cCodLocal_in,
             VNUMREGISTROSOL,
--             TO_DATE(cFechaInicio_in,'dd/mm/yyyy'),
--             TO_DATE(cFechaFin_in,'dd/mm/yyyy'),
             v_FechaInicio,
             v_FechaFin,
             'C',
              v_CodTipo,
              v_CodMotivo,
              vObservacion_in,
              vNomArchivo_in,
--              TO_DATE(cFecPropuesta,'dd/mm/yyyy HH24:MI:SS'),
              v_FecPropuesta,
              SYSDATE,
              vUsuCreacion_in,
              cSecUsuCrea_in,
              v_SecUsu,
              v_CodAprobador,
              v_DescAprobador
              );
      ----------------------------------------------------------------
      /*
          V_TIPO_VACACIONES NUMBER:=143;
          V_TIPO_SUBSIDIO   NUMBER:=144;
          V_TIPO_CESE       NUMBER:=145;
          V_TIPO_REG_SALIDA NUMBER:=152;
          V_TIPO_CORRECION  NUMBER:=153;
      */
      update PBL_SOLICITUD
         set action_aprueba = (case
                                when v_CodTipo IN (G_TIPO_VACACIONES,G_TIPO_SUBSIDIO,
                                                   G_TIPO_CESE)
                                 then
                                 'ptoventa_autoriza.AUT_P_UPD_DET_HORARIO(' || '''' ||
                                 cCodGrupoCia_in || '''' || ',' || '''' ||
                                 cCodLocal_in || '''' || ',' || '''' ||
                                 VNUMREGISTROSOL || '''' || ')'
                                when v_CodTipo = G_TIPO_CORRECION then
                                 'ptoventa_autoriza.AUT_P_CORRECION_MARCACION(' || '''' ||
                                 cCodGrupoCia_in || '''' || ',' || '''' ||
                                 cCodLocal_in || '''' || ',' || '''' ||
                                 VNUMREGISTROSOL || '''' || ')'
                                when v_CodTipo = G_TIPO_REG_SALIDA then
                                 'ptoventa_autoriza.AUT_P_UPD_REG_SALIDA(' || '''' ||
                                 cCodGrupoCia_in || '''' || ',' || '''' ||
                                 cCodLocal_in || '''' || ',' || '''' ||
                                 VNUMREGISTROSOL || '''' || ')' 
                                when v_CodTipo = G_TIPO_AUTORIZA_PLANTILLA then
                                 'ptoventa_autoriza.AUT_P_APRUEBA_PLANTILLA(' || '''' ||
                                 cCodGrupoCia_in || '''' || ',' || '''' ||
                                 cCodLocal_in || '''' || ',' || '''' ||
                                 VNUMREGISTROSOL || '''' || ')'                                                                  
                                else
                                 null
                              end),
             action_rechaza = (case
                                when v_CodTipo = G_TIPO_AUTORIZA_PLANTILLA then
                                 'ptoventa_autoriza.AUT_P_RECHAZA_PLANTILLA(' || '''' ||
                                 cCodGrupoCia_in || '''' || ',' || '''' ||
                                 cCodLocal_in || '''' || ',' || '''' ||
                                 VNUMREGISTROSOL || '''' || ')'                                                                  
                                else
                                 null
                              end)                           
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL     = cCodLocal_in
         and SEC_REGISTRO  = VNUMREGISTROSOL;
      --------------------------------------------------------------------
      v_Flag:=VNUMREGISTROSOL;
      
      RETURN  v_Flag;
      
    END;

--*********************************************************************************

    FUNCTION GEST_F_LISTA_SUBTIPOS(cCodMaestro_in IN CHAR,
                                   cValor_in      IN CHAR)
     RETURN FarmaCursor
    IS
        v_curSubTipos FarmaCursor;
    BEGIN
         OPEN v_curSubTipos FOR
         SELECT A.VALOR2 || 'Ã' || 
                         A.DESCRIPCION
         FROM MAESTRO_DETALLE A
         WHERE A.COD_MAESTRO = cCodMaestro_in
           AND A.VALOR1 = cValor_in
           AND A.ESTADO = 1;
    
         RETURN v_curSubTipos;
    END;

--**************************************************************************************    

    FUNCTION GEST_F_VALIDA_CRUCE_FECHA(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cDni_in           IN CHAR,
                                       cFechaInicio_in   IN CHAR,
                                       cFechaFin_in      IN CHAR,
                                       cTipoSolicitud_in IN CHAR)
    RETURN CHAR
    IS
    v_Flag     CHAR(1):='N';
    v_SecUsu   CHAR(4);
    v_Cantidad NUMBER;
    v_CodTipo  MAESTRO_DETALLE.COD_MAES_DET%TYPE;
    BEGIN
    
    v_CodTipo   := GEST_F_COD_MAE(g_CodMaestro, cTipoSolicitud_in);
    
    SELECT  USU.SEC_USU_LOCAL INTO v_SecUsu FROM PBL_USU_LOCAL USU WHERE USU.COD_GRUPO_CIA=cCodGrupoCia_in
    AND USU.COD_LOCAL=cCodLocal_in
    AND USU.DNI_USU=cDni_in;
    --VERIFICAMOS SI HAY ALGUNA SOLICITUD DONDE HAY CRUCE DE FECHA
    SELECT  COUNT(1) 
    INTO v_Cantidad 
    FROM PBL_SOLICITUD SOL 
    WHERE SOL.COD_GRUPO_CIA = cCodGrupoCia_in
      AND SOL.COD_LOCAL    = cCodLocal_in
      AND SOL.SEC_USU_COL  = v_SecUsu
      AND SOL.COD_MAE_TIPO = v_CodTipo
      AND ((to_date(cFechaInicio_in,'dd/mm/yyyy') 
      BETWEEN SOL.FEC_INICIO AND SOL.FEC_FIN) OR
      (to_date(cFechaFin_in,'dd/mm/yyyy') 
      BETWEEN SOL.FEC_INICIO AND SOL.FEC_FIN))
    AND SOL.EST_SOLICITUD <> G_EST_SOL_RECHAZADA;
    
    IF v_Cantidad >0 THEN
    v_Flag :='S'; 
    END IF;
    RETURN v_Flag;
  
    EXCEPTION WHEN OTHERS THEN
    RETURN v_Flag;
    END;

--*********************************************************************************

  FUNCTION GEST_F_LIST_SOLICITUD(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cFechaInicio_in IN CHAR,
                                 cFechaFin_in    IN CHAR,
                                 cEstSol_in      IN CHAR)
  RETURN FarmaCursor
  IS
     v_curLista FarmaCursor;
  BEGIN
    OPEN v_curLista FOR
 SELECT     U.DNI_USU || 'Ã' ||
            U.LOGIN_USU || 'Ã' ||
            to_char(fec_inicio,'dd/mm/yyyy') || 'Ã' ||
            nvl(to_char(fec_fin,'dd/mm/yyyy'),'-') || 'Ã' ||
            DECODE(est_solicitud,
                    'C','CREADO',
                    'E','ENVIADO',
                    'A','APROBADO',
                    'R','RECHAZADO',
                    'G','REGISTRADO') || 'Ã' ||
            nvl((SELECT  M.DESCRIPCION FROM MAESTRO_DETALLE M 
                 WHERE M.COD_MAES_DET=S.COD_MAE_TIPO),' ')|| 'Ã' ||
            nvl((SELECT  M.DESCRIPCION FROM MAESTRO_DETALLE M 
                 WHERE M.COD_MAES_DET=S.COD_MAE_SUBTIPO),' ')
    FROM PBL_SOLICITUD S,
         PBL_USU_LOCAL U
    WHERE S.COD_GRUPO_CIA = U.COD_GRUPO_CIA
    AND S.COD_LOCAL = U.COD_LOCAL
    AND S.SEC_USU_COL=U.SEC_USU_LOCAL
    AND S.COD_GRUPO_CIA = cCodGrupoCia_in
    AND S.COD_LOCAL = cCodLocal_in
    AND S.FEC_CREA>=TO_DATE(cFechaInicio_in,'dd/mm/yyyy')
    AND S.FEC_CREA<=TO_DATE(cFechaFin_in,'dd/mm/yyyy')
    AND S.EST_SOLICITUD=NVL(cEstSol_in,S.EST_SOLICITUD)
    AND S.COD_MAE_TIPO NOT IN (G_TIPO_REG_SALIDA, G_TIPO_AUTORIZA_PLANTILLA)
    ORDER BY S.SEC_REGISTRO DESC;
   
    RETURN  v_curLista;
  END;
  
--*********************************************************************************

  FUNCTION GEST_F_VALIDA_FEC_INICIO(cFecInicio_in IN CHAR)
  RETURN CHAR
  IS
  v_Flag CHAR(1):='N';

  BEGIN
    IF TO_CHAR(SYSDATE,'dd/mm/yyyy') > TO_DATE(cFecInicio_in,'dd/mm/yyyy') THEN
    v_Flag:='S';
    END IF;    
    RETURN v_Flag;

    EXCEPTION
    WHEN OTHERS THEN

    DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
    RETURN v_Flag;
  END;    

--*********************************************************************************

  FUNCTION GEST_F_VALIDA_FEC_CORRECION(cFecInicio_in IN CHAR)
  RETURN CHAR
  IS
  v_Flag CHAR(1):='N';

  BEGIN
    IF TO_CHAR(SYSDATE,'dd/mm/yyyy') < TO_DATE(cFecInicio_in,'dd/mm/yyyy') THEN
    v_Flag:='S';
    END IF;    
    RETURN v_Flag;

    EXCEPTION
    WHEN OTHERS THEN

    DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
    RETURN v_Flag;
  END;  

--*********************************************************************************

  FUNCTION GEST_F_CNX_FTP(cCodGrupoCia_in IN CHAR,
                          cCodCia_in      IN CHAR,
                          cCodLocal_in    IN CHAR
                          ) 
  RETURN VARCHAR2 IS
    v_Resultado varchar2(10000);
  BEGIN
    SELECT 
    TRIM(IP_SERVIDOR)||'@'||
    TRIM(USU_BD)     ||'@'||
    TRIM(CLAVE_BD)   ||'@'||
    TRIM(SID_BD)     ||'@'||
    TRIM(PUERTO_BD)  ||'@'||
    TRIM(TIME_OUT)   ||'@'||
    NVL(SERVICE_NAME,' ')
    INTO v_Resultado
    FROM PTOVENTA.PBL_CNX_REMOTO V
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_CIA   = cCodCia_in
      AND COD_LOCAL = cCodLocal_in
      AND SERVIDOR  = 'FTP';

    RETURN v_Resultado;
  END;
  
--*********************************************************************************

  PROCEDURE GEST_P_UPD_FILENAME(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumRegSol_in   IN CHAR,
                                vNomArchivo_in  IN CHAR)
  IS
  BEGIN
  UPDATE PBL_SOLICITUD S 
  SET S.NOM_ARCHIVO = vNomArchivo_in
  WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
    AND S.COD_LOCAL     = cCodLocal_in
    AND S.SEC_REGISTRO  = cNumRegSol_in;
  COMMIT;
  
  END;
  
--*********************************************************************************
  FUNCTION GEST_F_COD_MAE(nCodTipo_in MAESTRO_DETALLE.COD_MAESTRO%TYPE, 
                          vValor_in   MAESTRO_DETALLE.VALOR1%TYPE)
  RETURN VARCHAR2 
   AS
    v_Result VARCHAR2(120);
  BEGIN

       BEGIN
            SELECT COD_MAES_DET
              INTO v_Result
              FROM MAESTRO_DETALLE 
             WHERE COD_MAESTRO = nCodTipo_in
               AND VALOR1 = vValor_in;
       EXCEPTION
            WHEN NO_DATA_FOUND THEN
                 v_Result := NULL;
       END;

       RETURN v_Result;
  END;          
--*********************************************************************************
  FUNCTION GEST_F_COD_MAE_DET(nCodTipo_in MAESTRO_DETALLE.COD_MAESTRO%TYPE, 
                              vValor1_in   MAESTRO_DETALLE.VALOR1%TYPE,
                              vValor2_in   MAESTRO_DETALLE.VALOR2%TYPE)
  RETURN VARCHAR2 
   AS
    v_Result VARCHAR2(120);
  BEGIN

       BEGIN
            SELECT COD_MAES_DET
              INTO v_Result
              FROM MAESTRO_DETALLE 
             WHERE COD_MAESTRO = nCodTipo_in
               AND VALOR1 = vValor1_in
               AND VALOR2 = vValor2_in;
       EXCEPTION
            WHEN NO_DATA_FOUND THEN
                 v_Result := NULL;
       END;

       RETURN v_Result;
  END;          
--*********************************************************************************
  PROCEDURE GEST_P_ERROR_SOL(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR,
                             cNumRegSol_in   IN CHAR)
  IS
  v_Email     VARCHAR2(200);
  v_DescLocal VARCHAR2(200);
  v_Mensaje   VARCHAR2(800):='';
  v_Reg_Sol   PBL_SOLICITUD%ROWTYPE;
  BEGIN     
    SELECT *
    INTO v_Reg_Sol
    FROM PBL_SOLICITUD
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
     AND COD_LOCAL      = cCodLocal_in
     AND SEC_REGISTRO = cNumRegSol_in;
  
    SELECT COD_LOCAL||' - '||P.DESC_CORTA_LOCAL,
           P.MAIL_LOCAL
    INTO v_DescLocal, v_Email
    FROM PBL_LOCAL P
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL     = cCodLocal_in;
     
    UPDATE PBL_SOLICITUD S
    SET S.EST_SOLICITUD='R',
        S.FEC_MOD=SYSDATE,
        S.USU_MOD='SISTEMAS'    
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
     AND COD_LOCAL      = cCodLocal_in
     AND S.SEC_REGISTRO = cNumRegSol_in;
    COMMIT;
    v_Mensaje:=v_Mensaje||'<b>Se produjo un error al generar la solicitud'||v_Reg_Sol.SEC_REGISTRO||', favor generarla nuevamente</b><br>';
    v_Mensaje:=v_Mensaje||'Tipo Solicitud       : '||PTOVENTA_AUTORIZA.AUT_F_DES_TIPO(v_Reg_Sol.COD_MAE_TIPO)||'<br>'; 
    v_Mensaje:=v_Mensaje||'Colaborador Asociado : '||PTOVENTA_AUTORIZA.AUT_F_DES_USUARIO(v_Reg_Sol.SEC_USU_COL)||'<br>'; 
    v_Mensaje:=v_Mensaje||'Fecha Solicitud      : '||TO_CHAR(v_Reg_Sol.FEC_CREA,'DD/MM/YYYY HH24:MI')||'<br>';     
    v_Mensaje:=v_Mensaje||'Atentamente';  
    
     FARMA_EMAIL.envia_correo('oracle@mifarma.com.pe',--REMITENTE
                               v_Email,        --Para
                               'Local '||v_DescLocal||' - Solicitud:'||cNumRegSol_in, --MENSAJE
                               'ERROR AL GENERAR SOLICITUD', --Titulo
                               v_Mensaje,  --Mensaje
                                '',                              --Con Copia
                               FARMA_EMAIL.GET_EMAIL_SERVER,
                               TRUE);    
  
  END;
--*********************************************************************************
  PROCEDURE GEST_P_UPD_REG_SOL  (v_CodLocal IN CHAR,
                                 v_SecReg   IN CHAR,
                                 v_UsuMod IN VARCHAR2)
    IS
  BEGIN
    UPDATE PBL_SOLICITUD S
    SET S.EST_SOLICITUD = 'G',
        S.USU_MOD  = v_UsuMod,
        S.FEC_MOD = SYSDATE
    WHERE S.COD_GRUPO_CIA = '001'
      AND S.COD_LOCAL     = v_CodLocal
      AND S.SEC_REGISTRO  = v_SecReg
      AND S.EST_SOLICITUD = 'A';
  END;
--*************************************************************************************  
end PTOVENTA_GEST_SOLICITUD;
/
