create or replace package PTOVENTA_CONTROL_ASISTENCIA is

  TYPE FarmaCursor IS REF CURSOR;
  g_TIPO_TURNO      CHAR(1) := 'T'; --TIPO TURNO
  g_TIPO_VACACIONES CHAR(1) := 'V'; --TIPO VACACIONES
  g_TIPO_SUBSIDIO   CHAR(1) := 'S'; --TIPO SUBSIDIO
  g_TIPO_CESE       CHAR(1) := 'C'; --TIPO CESE
  g_TIPO_DESCANSO   CHAR(1) := 'D'; --TIPO DESCANSO
  g_TIPO_REFRIGERIO CHAR(1) := 'R'; --TIPO REFRIGERIO  

  g_IND_REGULARIZAR CHAR(1) := 'R'; --SE NECESITA REGULARIZACION
  g_IND_ENTRADA     CHAR(1) := 'E'; --SE NECESITA MARCAR ENTRADA
  g_IND_SALIDA      CHAR(1) := 'S'; --SE NECESITA MARCAR SALIDA

  g_IND_ACTIVO   CHAR(1) := 'A';
  g_IND_INACTIVO CHAR(1) := 'I';

  -- MAESTRO 
  g_TIPO_SOLICITUD_VACACIONES PBL_SOLICITUD.cod_mae_tipo%TYPE := 143;
  g_TIPO_SOLICITUD_SUBSIDIO   PBL_SOLICITUD.cod_mae_tipo%TYPE := 144;
  g_TIPO_SOLICITUD_CESE       PBL_SOLICITUD.cod_mae_tipo%TYPE := 145;
  g_TIPO_SOL_REGULARIZACION   PBL_SOLICITUD.cod_mae_tipo%TYPE := 152;  

  g_COD_MAE_SALIDA            MAESTRO.COD_MAESTRO%TYPE := 18;
  /* ****************************************************************** */
  --Descripcion: Listar turnos asignados al local.
  --Fecha       Usuario        Comentario
  --09/09/2015  ASOSA          Creacion 
  /* ****************************************************************** */  
  FUNCTION CTRL_F_GET_LIST_TURNO_LOCAL(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR) RETURN FarmaCursor;
  /* ****************************************************************** */
  --Descripcion: Listar turnos de refrigerio 
  --Fecha       Usuario        Comentario
  --09/09/2015  ASOSA          Creacion 
  /* ****************************************************************** */  
  FUNCTION CTRL_F_GET_LIST_REFRIGERIO
                                       (cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR) RETURN FarmaCursor;
  /* ****************************************************************** */
  --Descripcion: Listar el maestro de turnos MENOS los turnos ya asignados.
  --Fecha       Usuario        Comentario
  --09/09/2015  ASOSA          Creacion 
  /* ****************************************************************** */  
  FUNCTION CTRL_F_GET_LIST_MAE_TURNO(cCodGrupoCia_in IN CHAR,
                                     cCodCia_in      IN CHAR,
                                     cCodLocal_in    IN CHAR) RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: Asignar turno al local
  --Fecha       Usuario        Comentario
  --10/09/2015  ASOSA          Creacion 
  /* ****************************************************************** */  
  FUNCTION CTRL_F_INS_ASIG_TURNO_LOCAL(cCodGrupoCia_in IN CHAR,
                                       cCodCia_in      IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cIdTurno_in      IN CHAR,
                                       cUsuCrea_in IN CHAR) RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Devuelve un indicador para ver si debe mostrar la marcacion de entrada, salida o regularizar la salida.
  --Fecha       Usuario        Comentario
  --10/09/2015  ASOSA          Creacion 
  /* ****************************************************************** */   
  FUNCTION CTRL_F_IND_MARC_ENTR_SALIDA(cCodGrupoCia_in    IN CHAR,
                                       cCodLocal_in       IN CHAR,
                                       cIndentificador_in IN CHAR) RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Devuelve un DATA del usuario que va a regularizar su hora de salida.
  --Fecha       Usuario        Comentario
  --14/09/2015  ASOSA          Creacion 
  /* ****************************************************************** */     
  FUNCTION CTRL_F_GET_DATA_USUARIO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cDni_in IN CHAR) RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: Actualiza la forma fecha de salida del trabajador
  --Fecha       Usuario        Comentario
  --14/09/2015  ASOSA          Creacion 
  /* ****************************************************************** */  
  PROCEDURE CTRL_F_UPD_MARC_SALIDA(cCodGrupoCia_in IN CHAR,
                                   cCodCia_in      IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cDni_in         IN CHAR,
                                   vFecRegistro_in IN varchar,
                                   vFecSalida_in   IN varchar,
                                   vHoraSalida_in  IN varchar,
                                   nCodMotivo_in   IN number,
                                   vObservacion_in IN varchar2,
                                   cUsuMod_in      IN CHAR,
                                   vFechAGrabar    IN VARCHAR2);

  /* ****************************************************************** */
  --Descripcion: Lista filas por defecto para crear una plantilla
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */  
  FUNCTION CTRL_F_GET_DEFAULT_PLANTILLA RETURN FarmaCursor;
  
  /* ****************************************************************** */
  --Descripcion: Lista filas por defecto para crear un horario
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */    
  FUNCTION CTRL_F_GET_DEFAULT_HORARIO(cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cCodPlantilla_in IN CHAR) RETURN FarmaCursor;
                               
  /* ****************************************************************** */
  --Descripcion: Lista todas las plantilla grabadas
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */                                 
   FUNCTION CTRL_F_GET_LISTA_PLANTILLA_CAB(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR)
    RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: Lista las filas definidas para una plantilla.
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */  
  FUNCTION CTRL_F_GET_LISTA_PLANTILLA_DET(cCodGrupoCia_in  IN CHAR,
                                          cCodLocal_in     IN CHAR,
                                          cCodPlantilla_in IN CHAR)
    RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: Lista los roles que se seleccionaran en el objeto de JTIMETABLE
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */  
  FUNCTION CTRL_F_GET_SELEC_LISTA_ROL(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR)
    RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: Lista los rangos de horas que se seleccionaran en el objeto de JTIMETABLE
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */  
  FUNCTION CTRL_F_GET_SELEC_RANGO_HORAS(cCodCia_in   IN CHAR,
                                        cCodLocal_in IN CHAR) RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: Graba datos de plantilla cabecera.
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */  
  FUNCTION CTRL_F_SAVE_PLANTILLA_CAB(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cDescCorta_in   IN CHAR,
                                     cUsuCrea_in     IN CHAR) RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Graba datos de plantilla Detalle
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  --13/11/2015  EMAQUERA       Modificacion   
  /* ****************************************************************** */                                 
  procedure CTRL_P_SAVE_PLANTILLA_DET(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cCodPlantilla_in  IN CHAR,
                                      cSecu_in          IN CHAR,
                                      cIdRol_in         IN CHAR,
                                      cDiaRefrigerio_in IN CHAR,
                          	          cDia1_in          IN CHAR,
                                      cDia2_in          IN CHAR,
                                      cDia3_in          IN CHAR,
                                      cDia4_in          IN CHAR,
                                      cDia5_in          IN CHAR,
                                      cDia6_in          IN CHAR,
                                      cDia7_in          IN CHAR,
                                      cUsuCrea_in       IN CHAR,
                                      cIndUltimaFila_in IN CHAR,
                                      nCantHrs_in       IN NUMBER,
                                      cSecUsu_in        IN CHAR);

  /* ****************************************************************** */
  --Descripcion: Modifica datos de plantilla cabecera.
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */  
  FUNCTION CTRL_F_MODIFY_PLANTILLA_CAB(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cCodPlantilla_in IN CHAR,
                                       cDescCorta_in    IN CHAR,
                                       cUsuCrea_in      IN CHAR) RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Modifica datos de plantilla Detalle
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion
  --13/11/2015  EMAQUERA       Modificacion 
  /* ****************************************************************** */ 
  procedure CTRL_P_MODIFY_PLANTILLA_DET(cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in      IN CHAR,
                                        cCodPlantilla_in  IN CHAR,
                                        cSecu_in          IN CHAR,
                                        cIdRol_in         IN CHAR,
                                        cDiaRefrigerio_in IN CHAR,
                                        cDia1_in          IN CHAR,
                                        cDia2_in          IN CHAR,
                                        cDia3_in          IN CHAR,
                                        cDia4_in          IN CHAR,
                                        cDia5_in          IN CHAR,
                                        cDia6_in          IN CHAR,
                                        cDia7_in          IN CHAR,
                                        cUsuCrea_in       IN CHAR,
                                        cIndUltimaFila_in IN CHAR,
                                        nCantHrs_in       IN NUMBER,
                                        cSecUsu_in        IN CHAR);
                                   
  /* ****************************************************************** */
  --Descripcion: Obtiene horas legal semanal de trabajo
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */                                    
  FUNCTION CTRL_F_GET_HORAS_SEM_LEGAL RETURN NUMBER;

  /* ****************************************************************** */
  --Descripcion: Obtiene el máximo de horas por semana según MIFARMA
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_GET_MAX_HORA_SEM RETURN NUMBER;

  /* ****************************************************************** */
  --Descripcion: Obtiene las horas de 1 turno
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
    FUNCTION CTRL_F_GET_HORAS_TURNO(cCodGrupoCia_in IN CHAR, 
                                  cCodTurno_in IN CHAR)
    RETURN NUMBER;

  /* ****************************************************************** */
  --Descripcion: Obtiene los minutos que dura un rango de horario.
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
   FUNCTION CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in IN CHAR, 
                                      cCodTurno_in IN CHAR)
    RETURN NUMBER;

  /* ****************************************************************** */
  --Descripcion: Graba datos de horario cabecera.
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_SAVE_HORARIO_CAB(cCodGrupoCia_in     IN CHAR,
                                   cCodLocal_in        IN CHAR,
                                   cFecInicio_IN       IN CHAR,
                                   cFecFin_IN          IN CHAR,
                                   cRefCodPlantilla_IN IN CHAR,
                                   cUsuCrea_in         IN CHAR) RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Graba datos de horario Detalle
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
  procedure CTRL_P_SAVE_HORARIO_DET(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cCodHorario_in  IN CHAR,
                                    cSecHorario_in  IN CHAR,
                                    cIdRol_in       IN CHAR,                               
                                    cSecUsu_in IN CHAR,
                                    cDiaRefrigerio_in  IN CHAR,   
                                    cDia1_in   IN CHAR,
                                    cDia2_in   IN CHAR,
                                    cDia3_in   IN CHAR,
                                    cDia4_in   IN CHAR,                               
                                    cDia5_in          IN CHAR,
                                    cDia6_in          IN CHAR,
                                    cDia7_in          IN CHAR,
                                    cUsuCrea_in       IN CHAR,
                                    cIndUltimaFila_in in char,
                                    nCantHrs_in       IN NUMBER);

  /* ****************************************************************** */
  --Descripcion: Modifica datos de horario cabecera.
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_MODIFY_HORARIO_CAB(cCodGrupoCia_in     IN CHAR,
                                     cCodLocal_in        IN CHAR,
                                     cCodHorario_in      IN CHAR,
                                     cRefCodPlantilla_IN IN CHAR,
                                     cUsuCrea_in         IN CHAR) RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Modifica datos de horario Detalle
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */                                 
  procedure CTRL_P_MODIFY_HORARIO_DET(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cCodHorario_in  IN CHAR,
                                      cSecHorario_in  IN CHAR,
                                      cIdRol_in       IN CHAR,                                 
                                      cSecUsu_in IN CHAR,
                                    cDiaRefrigerio_in  IN CHAR,   
                                      cDia1_in   IN CHAR,
                                      cDia2_in   IN CHAR,
                                      cDia3_in   IN CHAR,
                                      cDia4_in   IN CHAR,                                 
                                      cDia5_in          IN CHAR,
                                      cDia6_in          IN CHAR,
                                      cDia7_in          IN CHAR,
                                      cUsuCrea_in       IN CHAR,
                                      cIndUltimaFila_in in char,
                                      nCantHrs_in IN NUMBER);

  /* ****************************************************************** */
  --Descripcion: Lista todos los horarios grabadas
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */                                  
  FUNCTION CTRL_F_GET_LISTA_HORARIO_CAB(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR) RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: Lista las filas definidas para un horario
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */                                  
  FUNCTION CTRL_F_GET_LISTA_HORARIO_DET(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodHorario_IN  IN CHAR) RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: Lista las plantillas para cargar en el jcombobox
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */                                  
   FUNCTION CTRL_F_GET_CMBO_PLANTILLA(cCodGrupoCia_in IN CHAR, 
                                     cCodLocal_in IN CHAR)
   RETURN FarmaCursor;   
  /* ****************************************************************** */
  --Descripcion: Obtiene usuario solicitud
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */     
  FUNCTION CTRL_F_VALIDA_USU_SOLICITUD(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cSecUsuLocal_in IN CHAR,
                                       cFecInicio_in   IN CHAR,
                                       cFecFin_in      IN CHAR,
                                       cCodDia1_in     IN CHAR,
                                       cCodDia2_in     IN CHAR,
                                       cCodDia3_in     IN CHAR,
                                       cCodDia4_in     IN CHAR,
                                       cCodDia5_in     IN CHAR,
                                       cCodDia6_in     IN CHAR,
                                       cCodDia7_in     IN CHAR) RETURN CHAR;
  /* ****************************************************************** */
  --Descripcion: Obtiene la lista de usuarios con su tipo de solicitudes aprobadas
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_VALIDA_SOL_TIPO(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cSecUsuLocal_in  IN CHAR,
                                   nSecTipoSol_in   IN NUMBER,
                                   cFecInicio_in    IN CHAR,
                                   cFecFin_in       IN CHAR) RETURN VARCHAR2;

  /* ****************************************************************** */
  --Descripcion: Obtiene la fecha de lunes a viernes , para la semana actual
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_GET_FECHA_INI_FIN RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: Lista los usuarios del local con los roles que tiene 
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */   
  FUNCTION CTRL_F_CUR_LISTA_USU_ROL(cCodGrupoCia_in IN CHAR, 
                                    cCodLocal_in IN CHAR)
  RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion:  Valida si el horario ya esta terminado para dejar grabar
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */     
  FUNCTION CTRL_F_VALIDA_EST_HORARIO(cCodGrupoCia_in  IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cCodPlantilla_in IN CHAR) RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Valida el formato de la hora ingresada ingresada en marcación
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
   FUNCTION CTRL_F_VALIDA_HORA(cFecEntrada_in IN CHAR, 
                              cFecSalida_in IN CHAR)
   RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Valida el forma de fecha ingresada en marcación
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_VALIDA_FORMAT_DATE(cFecha_in IN CHAR) RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Valida el forma de rango hora ingresada en marcación
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_VALIDA_RANGO_HORAS(cFecEntrada_in  IN CHAR,
                                     cFecSalida_in   IN CHAR,
                                     cCantMarcacion_in IN CHAR) RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Valida si la marcación ingresada y muestra mensaje si este no es valido.
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_MSJ_RANGO_HORA(cCantMarcacion_in IN CHAR) RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Retorna fecha de salida para marcación
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_FEC_SALIDA_SUG(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in IN CHAR,
                                 cDni_in      IN CHAR,
                                 cFecha_in    IN CHAR) RETURN DATE;

  /* ****************************************************************** */
  --Descripcion: Valida la marcacion de salida
  --Fecha       Usuario        Comentario
  --09/08/2015  CHUANES        Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_VALIDA_MARC_SALIDA RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion: Rango maximo de 48 horas
  --Fecha       Usuario        Comentario
  --09/08/2015  CHUANES        Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_MAX_RANGO_48HRS(cFecEntrada_in  IN CHAR,
                                  cFecSalida_in   IN CHAR,
                                  cCantMarcacion_in IN CHAR) RETURN CHAR;

  /* ****************************************************************** */
  --Descripcion:  Obtiene los turnos que tiene solicitudes aprobadas
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_GET_TURNO_X_SOL_APR(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cFecIni_in in char,
                                      cFecFin_in in char) RETURN FarmaCursor;
  /* ****************************************************************** */
  --Descripcion: Verifica si se validara la marcación de usuario
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */                                     
   FUNCTION CTRL_F_VALIDA_MARC_USU(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cDni_in IN CHAR)
   RETURN CHAR;                  

  /* ****************************************************************** */
  --Descripcion: Obtiene indicador si falta marcar temperatura para un determinado dia
  --Fecha       Usuario        Comentario
  --24/09/2015  ASOSA          Creacion 
  /* ****************************************************************** */ 
  FUNCTION CTRL_F_GET_IND_FALTA_TEMP(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     vFecha_in IN VARCHAR) 
  RETURN CHAR;         
           
  /* ****************************************************************** */
  --Descripcion: Obtiene los datos completos de un usuario de local
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */    
  FUNCTION CTRL_F_VAR_DATOS_USU(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                vSecUsuLocal_in IN VARCHAR) 
  RETURN varchar2;

  /* ****************************************************************** */
  --Descripcion: Verifica si se validara la asistencia si el usuario tiene cargo a validar.
  --Fecha       Usuario        Comentario
  --16/09/2015  DUBILLUZ       Creacion 
  /* ****************************************************************** */  
  FUNCTION CTRL_F_VAR_IS_CARGO_VALIDA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      vSecUsuLocal_in IN VARCHAR) 
  RETURN varchar2;  

  /* ****************************************************************** */
  --Descripcion: Obtiene el nro de horas trabajadas entre dos marcaciones.
  --Fecha       Usuario        Comentario
  --05/10/2015  ASOSA          Creacion 
  /* ****************************************************************** */
  FUNCTION CTRL_F_VAR_VAL_HRS_TRAB(vHoraFin_in IN date,
                                   vHoraIni_in IN date,
                                   nCantMinRef_in IN NUMBER) 
  RETURN VARCHAR2;

  /* ****************************************************************** */
  --Descripcion: Realiza una copia de una plantilla creada.
  --Fecha       Usuario        Comentario
  --05/10/2015  DUBILLUZ       Creacion 
  --13/11/2015  EMAQUERA       Modificacion
  /* ****************************************************************** */  
   PROCEDURE CTRL_P_SAVE_COPIA_PLANTILLA(cCodGrupoCia_in     IN CHAR,
                                        cCodLocal_in        IN CHAR,
                                        cCodPlantillaRef_in IN CHAR,
                                        cSecUsu_in          IN CHAR,
                                        cUsuCrea_in         IN CHAR);

  /* ****************************************************************** */
  --Descripcion: Valida los dias que faltan por acabar un horario 
  --             y si no hay uno creadoa futuro
  --Fecha       Usuario        Comentario
  --27/10/2015  DUBILLUZ          Creacion 
  /* ****************************************************************** */                                       
  FUNCTION CTRL_P_AVISO_FIN_HORARIO(cCodGrupoCia_in     IN CHAR,
                                    cCodLocal_in        IN CHAR) RETURN VARCHAR;                                     

  /* ****************************************************************** */
  --Descripcion: INDICA SI NO DEJA AVANZAR SI LA VALIDACION ANTERIOR DA RESULTADO
  --             y si no hay uno creadoa futuro
  --Fecha       Usuario        Comentario
  --27/10/2015  DUBILLUZ          Creacion 
  /* ****************************************************************** */          
  FUNCTION CTRL_F_IND_REPITE_VALIDACION(cCodGrupoCia_in     IN CHAR,
                                        cCodLocal_in        IN CHAR) RETURN VARCHAR;
  /* ****************************************************************** */
  --Descripcion: INDICA SI NO DEJA AVANZAR SI LA VALIDACION ANTERIOR DA RESULTADO
  --             y si no hay uno creadoa futuro
  --Fecha       Usuario        Comentario
  --27/10/2015  DUBILLUZ          Creacion 
  /* ****************************************************************** */          
  FUNCTION CTRL_F_MSJ_FRECUENTE_MARCACION(cCodGrupoCia_in     IN CHAR,
                                        cCodLocal_in        IN CHAR) RETURN VARCHAR ;                                    
  /* ****************************************************************** */
  --Descripcion: INDICA SI NO DEJA AVANZAR SI LA VALIDACION ANTERIOR DA RESULTADO
  --             y si no hay uno creadoa futuro
  --Fecha       Usuario        Comentario
  --27/10/2015  DUBILLUZ          Creacion 
  /* ****************************************************************** */          
  FUNCTION CTRL_F_GET_LIST_CESE(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR) RETURN FarmaCursor ;

  /* ****************************************************************** */
  --Descripcion: LISTA LAS INASISTENCIA PARA UN PERIODO
  --             y si no hay uno creadoa futuro
  --Fecha       Usuario        Comentario
  --27/10/2015  DUBILLUZ          Creacion 
  /* ****************************************************************** */          
  FUNCTION CTRL_F_GET_LIST_INASISTENCIA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR) RETURN FarmaCursor;

  /* ****************************************************************** */
  --Descripcion: VERIFICA SI ES NECESARIO APROBAR LA PLANTILLA 
  --             y si no hay uno creadoa futuro
  --Fecha       Usuario        Comentario
  --27/10/2015  DUBILLUZ       Creacion 
  --13/11/2015  EMAQUERA       Modificacion   
  /* ****************************************************************** */          
  procedure CTRL_P_VALIDA_APROBACION(cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     cCodPlantilla_in  IN CHAR,
                                     cSecUsu_in        IN CHAR);                                        
  /* ****************************************************************** */
END PTOVENTA_CONTROL_ASISTENCIA;
/
create or replace package body PTOVENTA_CONTROL_ASISTENCIA is

--********************************************************************************
  FUNCTION CTRL_F_GET_LIST_TURNO_LOCAL(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR) RETURN FarmaCursor IS
    v_curTurno FarmaCursor;
  BEGIN
    OPEN v_curTurno FOR
      SELECT t.cod_turno || 'Ã' || 
             t.nom_turno || 'Ã' || 
             TO_CHAR(hora_inic,'HH24:MI') || 'Ã' ||
             TO_CHAR(hora_fin,'HH24:MI') || 'Ã' || 
             NVL(t.minutos_refrigerio, 0) || 'Ã' ||
             PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VAR_VAL_HRS_TRAB(hora_fin,
                                                                hora_inic,
                                                                minutos_refrigerio)
        FROM pbl_turno t, pbl_turno_local l
       WHERE L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
         AND l.cod_turno = t.cod_turno
         AND L.COD_GRUPO_CIA = cCodGrupoCia_in
         AND l.cod_local = cCodLocal_in
         AND T.IND_CONCEPTO = g_TIPO_TURNO
       ORDER BY t.nom_turno DESC;
    RETURN v_curTurno;
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_LIST_REFRIGERIO
                                      (cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR) RETURN FarmaCursor IS
    v_curTurno FarmaCursor;
  BEGIN
    OPEN v_curTurno FOR
      SELECT t.cod_turno || 'Ã' || 
             t.nom_turno || 'Ã' || 
             TO_CHAR(hora_inic,'HH24:MI') || 'Ã' ||
             TO_CHAR(hora_fin,'HH24:MI') || 'Ã' || 
             NVL(t.minutos_refrigerio, 0) || 'Ã' ||
             PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VAR_VAL_HRS_TRAB(hora_fin,
                                                                hora_inic,
                                                                minutos_refrigerio)
        FROM pbl_turno t
       WHERE T.IND_CONCEPTO = g_TIPO_REFRIGERIO
       ORDER BY t.nom_turno DESC;
    RETURN v_curTurno;
  END;  
--********************************************************************************
  FUNCTION CTRL_F_GET_LIST_MAE_TURNO(cCodGrupoCia_in IN CHAR,
                                     cCodCia_in      IN CHAR,
                                     cCodLocal_in    IN CHAR) RETURN FarmaCursor IS
    v_curTurno FarmaCursor;
  BEGIN
    OPEN v_curTurno FOR
    
    SELECT cod_turno || 'Ã' || 
           nom_turno || 'Ã' || 
           TO_CHAR(hora_inic,'HH24:MI') || 'Ã' ||
           TO_CHAR(hora_fin,'HH24:MI') || 'Ã' || 
           NVL(minutos_refrigerio, 0) || 'Ã' ||
           PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VAR_VAL_HRS_TRAB(hora_fin,
                                                                hora_inic,
                                                                minutos_refrigerio)
    FROM pbl_turno t
    WHERE t.ind_concepto = g_TIPO_TURNO 
    AND T.COD_GRUPO_CIA = cCodGrupoCia_in
    AND T.COD_TURNO NOT IN (
                           SELECT A.COD_TURNO
                           FROM PBL_TURNO_LOCAL A
                           WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                           AND A.COD_LOCAL = cCodLocal_in
                           )
    
    ORDER BY hora_inic asc;
    RETURN v_curTurno;
  
  END;
--********************************************************************************
  FUNCTION CTRL_F_INS_ASIG_TURNO_LOCAL(cCodGrupoCia_in IN CHAR,
                                       cCodCia_in      IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cIdTurno_in      IN CHAR,
                                       cUsuCrea_in IN CHAR) RETURN CHAR IS
    v_flag  CHAR(5) := 'N';
    v_Cant NUMBER := 0;
  BEGIN
    SELECT COUNT(1)
      INTO v_Cant
      FROM PBL_TURNO_LOCAL L
     WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
       AND L.COD_CIA = cCodCia_in
       AND L.COD_LOCAL = cCodLocal_in
       AND L.COD_TURNO = cIdTurno_in;
  
    IF v_Cant = 0 THEN
      INSERT INTO pbl_turno_local
        (cod_grupo_cia, cod_cia, cod_local, cod_turno)
      VALUES
        (cCodGrupoCia_in, cCodCia_in, cCodLocal_in, cIdTurno_in);
      v_flag := 'S';
    END IF;
  
    RETURN v_flag;
  
  END;
--********************************************************************************
  FUNCTION CTRL_F_IND_MARC_ENTR_SALIDA(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cIndentificador_in IN CHAR) RETURN CHAR IS 
    v_ENTRADA        DATE;
    v_ENTRADA2       DATE;
    v_SALIDA         DATE;
    v_SALIDA2        DATE;
    v_dDiaTrabajo    DATE;
    v_CantMarcaciones number(6) := 0;
    v_Flag           CHAR(1) := '';
    v_vDni           VARCHAR2(20);
    v_nCantHrs       NUMBER(6,2);
    v_nCantHrsParam  NUMBER(6,2);
  BEGIN
    
    IF lengTH(TRIM(cIndentificador_in)) = 3 THEN
       SELECT A.DNI_USU
       INTO v_vDni
       FROM PBL_USU_LOCAL A
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.SEC_USU_LOCAl = cIndentificador_in;
    ELSIF lengTH(TRIM(cIndentificador_in)) = 8 THEN
       v_vDni := cIndentificador_in;
    END IF;
    
    SELECT a.llave_tab_gral
    into v_nCantHrsParam
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL = 589;
  
    --CANTIDAD DE MARCACIONES
    SELECT COUNT(1)
      INTO v_CantMarcaciones
      FROM PBL_INGRESO_PERSONAL I
     WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
       AND I.COD_LOCAL = cCodLocal_in
       AND I.DNI = v_vDni;
  
    --VEFICAMOS SI ES DIFERENTE DE NULLO
    IF v_vDni IS NOT NULL THEN
    
      --OBTENGO LA ULTIMA v_fecha REGISTRADA POR EL TRABAJADOR
      SELECT nvl(MAX(I.FECHA), trunc(sysdate))
        INTO v_dDiaTrabajo
        FROM PBL_INGRESO_PERSONAL I
       WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
         AND I.COD_LOCAL = cCodLocal_in
         AND I.DNI = v_vDni;
    
      IF v_CantMarcaciones > 0 THEN
        --OBTENGO MARCACIONES
        SELECT I.ENTRADA, I.SALIDA, I.ENTRADA_2, I.SALIDA_2
          INTO v_ENTRADA, v_SALIDA, v_ENTRADA2, v_SALIDA2
          FROM PBL_INGRESO_PERSONAL I
         WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
           AND I.COD_LOCAL = cCodLocal_in
           AND I.DNI = v_vDni
           and I.FECHA = TRUNC(v_dDiaTrabajo);
      END IF;
    
      IF (v_ENTRADA IS NOT NULL AND v_SALIDA IS NOT NULL AND
         v_ENTRADA2 IS NOT NULL AND v_SALIDA2 IS NOT NULL) THEN
        --SI HAY FULL MARCACION ASUMIENDO Q ES OTRO DIA
        v_Flag := g_IND_ENTRADA;
        --SI HAY FULL MARCACION Y RESULTA QUE VUELVE A QUERER ENTRAR 
        --para que cuando ya halla marcado todo entre nomas. En realidad no 
        --deberia entrar pero como aca todo esta abierto mejor le dejamos la posibilidad
        --ENTIENDO QUE ESTO ES PORQUE QUIEREN QUE PUEDA SEGUIR CHAMBEANDO SI DESEA
        IF TRUNC(SYSDATE) = TRUNC(v_dDiaTrabajo) THEN
           v_Flag := ' '; 
        END IF;
      ELSE
        IF (v_ENTRADA IS NOT NULL AND v_SALIDA IS NOT NULL AND
           v_ENTRADA2 IS NOT NULL) THEN
          --SI FALTA SALIDA2
          IF TRUNC(SYSDATE) = TRUNC(v_dDiaTrabajo) THEN
            --SI ES EL DIA DE HOY
            v_Flag := g_IND_SALIDA;
          ELSE
            v_nCantHrs := TO_NUMBER(PTOVENTA.PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VAR_VAL_HRS_TRAB(TO_DATE(TO_CHAR(SYSDATE,'HH24:MI'),'HH24:MI'),
                                                                                                TO_DATE(TO_CHAR(v_ENTRADA2,'HH24:MI'),'HH24:MI'),
                                                                                                60),
                                    '9990.00');
            v_Flag := g_IND_REGULARIZAR;
            IF v_nCantHrs <= v_nCantHrsParam THEN
               v_Flag := g_IND_SALIDA;
            END IF;            
          END IF;
        ELSE
          IF (v_ENTRADA IS NOT NULL AND v_SALIDA IS NOT NULL) THEN
            --SI HAY MARCACION NORMAL
            v_Flag := g_IND_ENTRADA;
          ELSE
            IF (v_ENTRADA IS NOT NULL) THEN
              --SI FALTA SALIDA
              IF TRUNC(SYSDATE) = TRUNC(v_dDiaTrabajo) THEN
                --SI ES EL DIA DE HOY
                v_Flag := g_IND_SALIDA;
              ELSE
                DBMS_OUTPUT.put_line('I: ' || TO_CHAR(v_ENTRADA,'HH24:MI'));
                DBMS_OUTPUT.put_line('F: ' || TO_CHAR(SYSDATE,'HH24:MI'));
                DBMS_OUTPUT.put_line('I: ' || TO_DATE(TO_CHAR(v_ENTRADA,'HH24:MI'),'HH24:MI'));
                DBMS_OUTPUT.put_line('F: ' || TO_DATE(TO_CHAR(SYSDATE,'HH24:MI'),'HH24:MI'));
                v_nCantHrs := TO_NUMBER(PTOVENTA.PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VAR_VAL_HRS_TRAB(TO_DATE(TO_CHAR(SYSDATE,'HH24:MI'),'HH24:MI'),
                                                                                                TO_DATE(TO_CHAR(v_ENTRADA,'HH24:MI'),'HH24:MI'),
                                                                                                60),
                                    '9990.00');
                v_Flag := g_IND_REGULARIZAR;
                IF v_nCantHrs <= v_nCantHrsParam THEN
                   v_Flag := g_IND_SALIDA;
                END IF;
              END IF;
            ELSE
              --ES UN NUEVO DIA CON TODO VACIO
              v_Flag := g_IND_ENTRADA;
            END IF;
          END IF;
        END IF;
      END IF;
    END IF;
  
    RETURN v_Flag;
  
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_DATA_USUARIO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cDni_in IN CHAR) RETURN FarmaCursor IS
    v_curDatos FarmaCursor;
    v_fechaSug DATE;
    v_fechaUlt DATE;
  BEGIN


    SELECT MAX(B.FECHA)
      INTO v_fechaUlt
      FROM PBL_INGRESO_PERSONAL B
     WHERE b.COD_GRUPO_CIA = cCodGrupoCia_in
       AND b.COD_LOCAL = cCodLocal_in
       AND b.DNI = cDni_in;

    v_fechaSug := CTRL_F_FEC_SALIDA_SUG(cCodGrupoCia_in, cCodLocal_in, cDni_in, v_fechaUlt);

    OPEN v_curDatos FOR
      SELECT A.NOM_USU || ' ' || A.APE_PAT || ' ' || A.APE_MAT || 'Ã' ||
             -- dubilluz 16.09.2015
              TO_CHAR(NVL(B.ENTRADA_2, B.ENTRADA), 'DD/MM/YYYY HH24:MI') || 'Ã' ||
              nvl(TO_CHAR(v_fechaSug, 'DD/MM/YYYY'), ' ') || 'Ã' ||
             -- dubilluz 16.09.2015
              nvl(TO_CHAR(v_fechaSug, 'HH24:MI'), ' ') || 'Ã' ||
             --NVL2(B.ENTRADA_2,1,2) || 'Ã' ||
              ((case
                when b.entrada is null then
                 1
                else
                 0
              end) + case
                when b.entrada_2 is null then
                 1
                else
                 0
              end) || 'Ã' || b.dni
        FROM PBL_INGRESO_PERSONAL B,
             pbl_usu_local a
       WHERE b.COD_GRUPO_CIA = cCodGrupoCia_in
         AND b.COD_LOCAL = cCodLocal_in
         AND B.DNI = cDni_in
         AND B.FECHA = v_fechaUlt
         and   b.cod_grupo_cia = a.cod_grupo_cia
         and   b.cod_local = a.cod_local
         and   b.dni = a.dni_usu;

    RETURN v_curDatos;

  END;
--********************************************************************************
  PROCEDURE CTRL_F_UPD_MARC_SALIDA(cCodGrupoCia_in IN CHAR,
                                   cCodCia_in      IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cDni_in         IN CHAR,
                                   vFecRegistro_in IN varchar,
                                   vFecSalida_in   IN varchar,
                                   vHoraSalida_in  IN varchar,
                                   nCodMotivo_in   IN number,
                                   vObservacion_in IN varchar2,
                                   cUsuMod_in      IN CHAR,
                                   vFechAGrabar    IN VARCHAR2)  IS
  v_dDiaTrabajo date;
  v_Tipo        CHAR(2):='02';
  v_Indicador   CHAR(1):='N';
  v_Valor       MAESTRO_DETALLE.VALOR1%TYPE;
  v_NumSol      PBL_SOLICITUD.SEC_REGISTRO%TYPE;
  v_SecUsu      PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE;
  v_FecSalSug   VARCHAR2(20);
  v_FecMask     VARCHAR2(20); 
  v_Hora        CHAR(5); 
  BEGIN

  SELECT M.VALOR1
  INTO v_Valor
  FROM MAESTRO_DETALLE M
  WHERE M.COD_MAES_DET = g_TIPO_SOL_REGULARIZACION; 

  SELECT P.SEC_USU_LOCAL
  INTO v_SecUsu
  FROM PBL_USU_LOCAL P
  WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
    AND P.COD_LOCAL = cCodLocal_in
    AND P.DNI_USU = cDni_in;


    SELECT nvl(MAX(A.FECHA),trunc(sysdate))
    INTO v_dDiaTrabajo
    FROM PBL_INGRESO_PERSONAL A
    WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
     AND A.COD_LOCAL = cCodLocal_in
     AND A.DNI = cDni_in;

    v_Hora := TO_CHAR(PTOVENTA_INGR_PERS.INGR_F_GET_FEC_SALIDA_HOR(cCodGrupoCia_in, cCodLocal_in, cDni_in),'HH24:MI');
    
    v_FecSalSug := TO_CHAR(PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_FEC_SALIDA_SUG(cCodGrupoCia_in, cCodLocal_in, cDni_in, v_dDiaTrabajo),'DD/MM/YYYY HH24:MI');
    
    ptoventa_ingr_pers.INGR_P_REGULARIZAR_SALIDA(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cDni_in,
                                               vFechAGrabar || ':00');

    SELECT TO_CHAR(TO_DATE(TO_CHAR(v_dDiaTrabajo, 'DD/MM/YYYY') || ' ' ||
                      v_Hora,'DD/MM/YYYY HH24:MI'),'DD/MM/YYYY HH24:MI')
    INTO v_FecMask
    FROM DUAL;

   v_NumSol := ptoventa_gest_solicitud.gest_f_graba_solicitud(cCodGrupoCia_in,
                                                              cCodLocal_in,
                                                              cDni_in,
                                                              v_FecMask,
                                                              v_FecSalSug,
                                                              v_Valor,
                                                              nCodMotivo_in,
                                                              '',
                                                              vFecSalida_in || ' ' ||vHoraSalida_in,
                                                              vObservacion_in,
                                                              cDni_in,
                                                              v_SecUsu);
  PTOVENTA_AUTORIZA.AUT_P_ENVIA_MAIL_SOL(v_NumSol);

  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_DEFAULT_PLANTILLA RETURN FarmaCursor IS
    v_curPlantilla FarmaCursor;
  BEGIN
    OPEN v_curPlantilla FOR
      SELECT lpad(ROWNUM, 4, '0') || 'Ã' || r.cod_rol || 'Ã' || ' ' || 'Ã' || ' ' || 'Ã' || ' ' || 'Ã' || ' ' || 'Ã' || ' ' || 'Ã' || ' ' || 'Ã' || ' '
        FROM pbl_rol r
       WHERE r.cod_rol IN ('010', '011')
       ORDER BY rownum asc;
    RETURN v_curPlantilla;
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_DEFAULT_HORARIO(cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cCodPlantilla_in IN CHAR) RETURN FarmaCursor IS
    v_curPlantilla FarmaCursor;
  BEGIN
    OPEN v_curPlantilla FOR
      SELECT p.sec_plantilla || 'Ã' || p.cod_rol || 'Ã' ||
             'N'  || 'Ã' ||
             p.cod_turno1 || 'Ã' ||
             p.cod_turno2 || 'Ã' || p.cod_turno3 || 'Ã' || p.cod_turno4 || 'Ã' ||
             p.cod_turno5 || 'Ã' || p.cod_turno6 || 'Ã' || p.cod_turno7 || 'Ã' ||
             NVL(p.cod_turno_refrigerio, 'N')
        FROM pbl_det_plantilla p
       WHERE p.cod_grupo_cia = cCodGrupoCia_in
         and p.cod_local = cCodLocal_in
         and p.cod_plantilla = cCodPlantilla_in
       ORDER BY p.sec_plantilla asc;
    RETURN v_curPlantilla;
  END;
  
--********************************************************************************
  FUNCTION CTRL_F_GET_LISTA_PLANTILLA_CAB(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR)
    RETURN FarmaCursor IS
    v_curPlantilla FarmaCursor;
  BEGIN
    OPEN v_curPlantilla FOR
      SELECT cab.cod_plantilla || 'Ã' || NVL(cab.desc_corta, ' ') || 'Ã' ||
             TO_CHAR(cab.fec_crea, 'dd/mm/yyyy HH24:MI') || 'Ã' ||
             NVL(cab.usu_crea, ' ') || 'Ã' ||
             NVL(DECODE(cab.est_plantilla,
                        'N',
                        'AUTORIZADO',
                        'C',
                        'AUTORIZADO',
                        'P',
                        'POR AUTORIZAR'),
                 ' ') || 'Ã' ||
             nvl(cab.est_plantilla,'X')
        FROM pbl_cab_plantilla cab
       WHERE cab.cod_grupo_cia = cCodGrupoCia_in
         AND cab.cod_local = cCodLocal_in;
    RETURN v_curPlantilla;
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_LISTA_PLANTILLA_DET(cCodGrupoCia_in  IN CHAR,
                                          cCodLocal_in     IN CHAR,
                                          cCodPlantilla_in IN CHAR)
    RETURN FarmaCursor IS
    v_curPlantilla FarmaCursor;
  BEGIN
    OPEN v_curPlantilla FOR
      SELECT NVL(det.sec_plantilla, ' ') || 'Ã' || det.cod_rol || 'Ã' ||
             'N'  || 'Ã' ||
             NVL(det.cod_turno1, 'N') || 'Ã' || NVL(det.cod_turno2, 'N') || 'Ã' ||
             NVL(det.cod_turno3, 'N') || 'Ã' || NVL(det.cod_turno4, 'N') || 'Ã' ||
             NVL(det.cod_turno5, 'N') || 'Ã' || NVL(det.cod_turno6, 'N') || 'Ã' ||
             NVL(det.cod_turno7, 'N') || 'Ã' || NVL(det.cod_turno_refrigerio, 'N')
        FROM pbl_det_plantilla det, pbl_rol rol
       WHERE det.cod_rol = rol.cod_rol
         AND det.cod_grupo_cia = cCodGrupoCia_in
         AND det.cod_local = cCodLocal_in
         AND det.cod_plantilla = cCodPlantilla_in;
  
    RETURN v_curPlantilla;
  
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_SELEC_LISTA_ROL(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR)
    RETURN FarmaCursor IS
    v_curPlantilla FarmaCursor;
  BEGIN
    OPEN v_curPlantilla FOR
      select '000' || 'Ã' || 'Seleccione Rol'
        from dual
      union
      SELECT DISTINCT (r.cod_rol) || 'Ã' || r.desc_rol
        FROM pbl_rol_usu u, pbl_rol r, pbl_usu_local usu
       WHERE usu.cod_grupo_cia = u.cod_grupo_cia
         AND usu.cod_local = u.cod_local
         AND usu.sec_usu_local = u.sec_usu_local
         AND usu.cod_grupo_cia = cCodGrupoCia_in
         AND usu.cod_local = cCodLocal_in
         AND r.cod_rol = u.cod_rol
         AND u.est_rol_usu = 'A'
         AND usu.est_usu = 'A'
         AND r.ind_plan_hora_vis = 'S'
         AND USU.SEC_USU_LOCAL BETWEEN '001' AND '899';
  
    RETURN v_curPlantilla;
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_SELEC_RANGO_HORAS(cCodCia_in   IN CHAR,
                                        cCodLocal_in IN CHAR) RETURN FarmaCursor IS
    v_curPlantilla FarmaCursor;
  BEGIN
    OPEN v_curPlantilla FOR
      SELECT DATO
        FROM (SELECT 1 v_fila,
                     '000' || 'Ã' || '--:--/--:--' || 'Ã' || '0.0' || 'Ã' || 'S' dato
                FROM DUAL
              UNION
              SELECT 2,
                     t.cod_turno || 'Ã' || t.nom_turno || 'Ã' ||
                     (TRIM(TO_NUMBER(round((CASE
                                             WHEN (t.hora_fin-t.hora_inic) < 0 THEN
                                              (t.hora_fin+1  -t.hora_inic)
                                             ELSE
                                              (t.hora_fin - t.hora_inic)
                                           END) * 24 * 60,
                                           3),
                                     '9999990.000')) -
                     (NVL(t.MINUTOS_REFRIGERIO, 0))) || 'Ã' || 'S' -- minutos rango de v_Hora   from pbl_turno t
                FROM pbl_turno t
               WHERE t.ind_concepto in (g_TIPO_DESCANSO)
              UNION
              SELECT 3,
                     t.cod_turno || 'Ã' || t.nom_turno || 'Ã' ||
                     (TRIM(TO_NUMBER(round((CASE
                                             WHEN t.hora_fin - t.hora_inic < 0 THEN
                                              (t.hora_fin+1  - t.hora_inic)
                                             ELSE
                                              (t.hora_fin - t.hora_inic)
                                           END) * 24 * 60,
                                           3),
                                     '9999990.000')) -
                     (NVL(t.MINUTOS_REFRIGERIO, 0))) || 'Ã' || 'S' -- minutos rango de v_Hora   from pbl_turno t
                FROM pbl_turno t, pbl_turno_local l
               WHERE t.ind_concepto in (g_TIPO_TURNO)
                 and t.cod_grupo_cia = l.cod_grupo_cia
                 and t.cod_turno = l.cod_turno
                 and l.cod_grupo_cia = cCodCia_in
                 and l.cod_local = cCodLocal_in
              union
              SELECT 4,
                     t.cod_turno || 'Ã' || t.nom_turno || 'Ã' ||
                     (TRIM(TO_NUMBER(round((CASE
                                             WHEN (t.hora_fin - t.hora_inic) < 0 THEN
                                              (t.hora_fin+1 - t.hora_inic)
                                             ELSE
                                              (t.hora_fin - t.hora_inic)
                                           END) * 24 * 60,
                                           3),
                                     '9999990.000')) -
                     (NVL(t.MINUTOS_REFRIGERIO, 0))) || 'Ã' || 'N' -- minutos rango de v_Hora   from pbl_turno t
                FROM pbl_turno t
               WHERE t.ind_concepto in
                     (g_TIPO_VACACIONES, g_TIPO_SUBSIDIO, g_TIPO_CESE))
       ORDER BY v_fila ASC;
    RETURN v_curPlantilla;
  END;
--********************************************************************************
  FUNCTION CTRL_F_SAVE_PLANTILLA_CAB(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cDescCorta_in   IN CHAR,
                                     cUsuCrea_in     IN CHAR) RETURN CHAR IS  
    v_CodPlantilla pbl_cab_plantilla.cod_plantilla%type;
    v_CodNumera    varchar(10) := '608';
  BEGIN
    v_CodPlantilla := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.Obtener_Numeracion(cCodGrupoCia_in,
                                                                                             cCodLocal_in,
                                                                                             v_CodNumera),
                                                                                            10,
                                                                                            '0',
                                                                                            'I');
  
    INSERT INTO pbl_cab_plantilla
      (cod_grupo_cia,
       cod_local,
       cod_plantilla,
       desc_corta,
       desc_larga,
       usu_crea)
    VALUES
      (cCodGrupoCia_in,
       cCodLocal_in,
       v_CodPlantilla,
       cdesccorta_in,
       v_CodPlantilla || ' ' || cdesccorta_in,
       cUsuCrea_in);
  
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               v_CodNumera,
                                               cUsuCrea_in);
  
    RETURN v_CodPlantilla;
  
  END;
--********************************************************************************
  procedure CTRL_P_SAVE_PLANTILLA_DET(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cCodPlantilla_in  IN CHAR,
                                      cSecu_in          IN CHAR,
                                      cIdRol_in         IN CHAR,
                                      cDiaRefrigerio_in IN CHAR,
                          	          cDia1_in          IN CHAR,
                                      cDia2_in          IN CHAR,
                                      cDia3_in          IN CHAR,
                                      cDia4_in          IN CHAR,
                                      cDia5_in          IN CHAR,
                                      cDia6_in          IN CHAR,
                                      cDia7_in          IN CHAR,
                                      cUsuCrea_in       IN CHAR,
                                      cIndUltimaFila_in IN CHAR,
                                      nCantHrs_in       IN NUMBER,
                                      cSecUsu_in        IN CHAR) iS
    v_Indicador             char(1);
    v_CantHorasLegalSem     number;
    v_CantHorasReal         number;
    v_MaxHoraSemana         number;
  BEGIN
    INSERT INTO pbl_det_plantilla
      (cod_grupo_cia,
       cod_local,
       cod_plantilla,
       sec_plantilla,
       cod_rol,
       cod_turno_refrigerio,
       cod_turno1,
       cod_turno2,
       cod_turno3,
       cod_turno4,
       cod_turno5,
       cod_turno6,
       cod_turno7,
       num_hora_prog,
       usu_crea)
    VALUES
      (cCodGrupoCia_in,
       cCodLocal_in,
       cCodPlantilla_in,
       csecu_in,
       cidrol_in,
       cDiaRefrigerio_in,
       cdia1_in,
       cdia2_in,
       cdia3_in,
       cdia4_in,
       cdia5_in,
       cdia6_in,
       cdia7_in,
       nCantHrs_in,
       cUsuCrea_in);
  
    v_MaxHoraSemana := CTRL_F_GET_MAX_HORA_SEM;
  
    SELECT CASE
             WHEN V.DIF < 0 THEN
              'N'
             ELSE
              'S'
           END
      INTO v_Indicador
      FROM (SELECT (v_MaxHoraSemana -
                   (CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO1) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO2) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO3) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO4) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO5) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO6) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO7)) / 60) as "DIF"
              FROM PBL_DET_PLANTILLA P
             WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
               AND P.COD_LOCAL = cCodLocal_in
               AND P.COD_PLANTILLA = cCodPlantilla_in
               and p.sec_plantilla = csecu_in) V;
  
    if v_Indicador = 'N' then
    
      v_CantHorasReal := round((CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia1_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia2_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia3_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia4_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia5_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia6_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia7_in)) / 60,
                              2);
    
      RAISE_APPLICATION_ERROR(-20020,
                              'La cantidad de horas no permitidas  en la v_fila ' ||
                              csecu_in||' - Horas : '||v_CantHorasReal);
    end if;
  
    if cIndUltimaFila_in = 'S' then
      -- //SI TODOS LOS REGISTROS TIENE menos DE 48 HORAS LEGALES
      v_CantHorasLegalSem := CTRL_F_GET_HORAS_SEM_LEGAL;
    
      SELECT CASE
               WHEN V.CTD_DIF > 0 THEN
                'N'
               ELSE
                'C'
             END
        INTO v_Indicador
        FROM (select count(1) CTD_DIF
                FROM (SELECT (v_CantHorasLegalSem -
                             (CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_TURNO1) +
                             CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_TURNO2) +
                             CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_TURNO3) +
                             CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_TURNO4) +
                             CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_TURNO5) +
                             CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_TURNO6) +
                             CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_TURNO7)) / 60) DIF
                        FROM PBL_DET_PLANTILLA P
                       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND P.COD_LOCAL = cCodLocal_in
                         AND P.COD_PLANTILLA = cCodPlantilla_in) G
               WHERE G.DIF > 0) V;
    
      UPDATE PBL_CAB_PLANTILLA P
         SET P.EST_PLANTILLA = v_Indicador
       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND P.COD_LOCAL = cCodLocal_in
         AND P.COD_PLANTILLA = cCodPlantilla_in;

       ptoventa_control_asistencia.ctrl_p_valida_aprobacion(cCodGrupoCia_in,
                                                            cCodLocal_in,
                                                            cCodPlantilla_in,
                                                            cSecUsu_in);
         
    end if;
  END;
--********************************************************************************
  FUNCTION CTRL_F_MODIFY_PLANTILLA_CAB(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cCodPlantilla_in IN CHAR,
                                       cDescCorta_in    IN CHAR,
                                       cUsuCrea_in      IN CHAR) RETURN CHAR IS
  
  BEGIN
  
    update pbl_cab_plantilla v
       set desc_corta       = cdesccorta_in,
           desc_larga       = cCodPlantilla_in || ' ' || cdesccorta_in,
           usu_mod          = cUsuCrea_in
     where v.cod_grupo_cia = cCodGrupoCia_in
       and v.cod_local = cCodLocal_in
       and v.cod_plantilla = cCodPlantilla_in;
  
    UPDATE pbl_det_plantilla v
       SET v.ind_proc_modifica = 'S'
     where v.cod_grupo_cia = cCodGrupoCia_in
       and v.cod_local = cCodLocal_in
       and v.cod_plantilla = cCodPlantilla_in;
  
    RETURN cCodPlantilla_in;
  
  END;
--********************************************************************************
  procedure CTRL_P_MODIFY_PLANTILLA_DET(cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in      IN CHAR,
                                        cCodPlantilla_in  IN CHAR,
                                        cSecu_in          IN CHAR,
                                        cIdRol_in         IN CHAR,
                                        cDiaRefrigerio_in IN CHAR,
                                        cDia1_in          IN CHAR,
                                        cDia2_in          IN CHAR,
                                        cDia3_in          IN CHAR,
                                        cDia4_in          IN CHAR,
                                        cDia5_in          IN CHAR,
                                        cDia6_in          IN CHAR,
                                        cDia7_in          IN CHAR,
                                        cUsuCrea_in       IN CHAR,
                                        cIndUltimaFila_in IN CHAR,
                                        nCantHrs_in       IN NUMBER,
                                        cSecUsu_in        IN CHAR) is
    v_Indicador          char(1);
    v_CantHorasLegalSem  number;
    v_CantHorasReal      number;
    v_MaxHoraSemana      number;
    v_Existe             number;
  BEGIN
  
    select count(1)
      into v_Existe
      from pbl_det_plantilla T
     WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
       AND T.COD_LOCAL = cCodLocal_in
       AND T.COD_PLANTILLA = cCodPlantilla_in
       AND T.SEC_PLANTILLA = cSecu_in;
  
    if v_Existe > 0 then
      UPDATE pbl_det_plantilla T
         SET T.IND_PROC_MODIFICA = 'N',
             cod_rol           = cidrol_in,
             t.cod_turno_refrigerio= cDiaRefrigerio_in,
             cod_turno1        = cdia1_in,
             cod_turno2        = cdia2_in,
             cod_turno3        = cdia3_in,
             cod_turno4        = cdia4_in,
             cod_turno5        = cdia5_in,
             cod_turno6        = cdia6_in,
             cod_turno7        = cdia7_in,
             fec_mod           = SYSDATE,
             usu_mod           = cusucrea_in,
             t.num_hora_prog   = nCantHrs_in
       WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
         AND T.COD_LOCAL = cCodLocal_in
         AND T.COD_PLANTILLA = cCodPlantilla_in
         AND T.SEC_PLANTILLA = cSecu_in;
    else
      INSERT INTO pbl_det_plantilla
        (cod_grupo_cia,
         cod_local,
         cod_plantilla,
         sec_plantilla,
         cod_rol,
         cod_turno_refrigerio,
         cod_turno1,
         cod_turno2,
         cod_turno3,
         cod_turno4,
         cod_turno5,
         cod_turno6,
         cod_turno7,
         num_hora_prog)
      VALUES
        (cCodGrupoCia_in,
         cCodLocal_in,
         cCodPlantilla_in,
         csecu_in,
         cidrol_in,
         cDiaRefrigerio_in,
         cdia1_in,
         cdia2_in,
         cdia3_in,
         cdia4_in,
         cdia5_in,
         cdia6_in,
         cdia7_in,
         nCantHrs_in);
    end if;
  
    v_MaxHoraSemana := CTRL_F_GET_MAX_HORA_SEM;
  
    SELECT CASE
             WHEN V.DIF < 0 THEN
              'N'
             ELSE
              'S'
           END
      INTO v_Indicador
      FROM (SELECT (v_MaxHoraSemana -
                   (CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO1) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO2) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO3) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO4) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO5) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO6) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_TURNO7)) / 60) as "DIF"
              FROM PBL_DET_PLANTILLA P
             WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
               AND P.COD_LOCAL = cCodLocal_in
               AND P.COD_PLANTILLA = cCodPlantilla_in
               and p.sec_plantilla = csecu_in) V;
  
    if v_Indicador = 'N' then
    
      v_CantHorasReal := round((CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia1_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia2_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia3_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia4_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia5_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia6_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia7_in)) / 60,
                              2);
    
      RAISE_APPLICATION_ERROR(-20020,
                              'La cantidad de horas no permitidas  en la v_fila ' ||
                              csecu_in||' - Horas : '||v_CantHorasReal);
      --);
    end if;
  
    --ACTUALIZA_HORAS_PLANTILLA(cCodGrupoCia_in, cCodLocal_in,cCodPlantilla_in,cSecu);
    if cIndUltimaFila_in = 'S' then
      delete PBL_DET_PLANTILLA t
       WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
         AND T.COD_LOCAL = cCodLocal_in
         AND T.COD_PLANTILLA = cCodPlantilla_in
         AND T.IND_PROC_MODIFICA = 'S';
    
      -- actualiza isHoraLegal
      -- //SI TODOS LOS REGISTROS TIENE menos DE 48 HORAS LEGALES
      v_CantHorasLegalSem := CTRL_F_GET_HORAS_SEM_LEGAL;
    
      SELECT CASE
               WHEN V.CTD_DIF > 0 THEN
                'N'
               ELSE
                'C'
             END
        INTO v_Indicador
        FROM (
              
              select count(1) CTD_DIF
                FROM (SELECT (v_CantHorasLegalSem -
                              (CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_TURNO1) +
                              CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_TURNO2) +
                              CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_TURNO3) +
                              CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_TURNO4) +
                              CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_TURNO5) +
                              CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_TURNO6) +
                              CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_TURNO7)) / 60) DIF
                         FROM PBL_DET_PLANTILLA P
                        WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                          AND P.COD_LOCAL = cCodLocal_in
                          AND P.COD_PLANTILLA = cCodPlantilla_in) G
               WHERE G.DIF > 0) V;
    
      UPDATE PBL_CAB_PLANTILLA P
         SET P.EST_PLANTILLA = v_Indicador
       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND P.COD_LOCAL = cCodLocal_in
         AND P.COD_PLANTILLA = cCodPlantilla_in;

   ptoventa_control_asistencia.ctrl_p_valida_aprobacion(cCodGrupoCia_in,
                                                        cCodLocal_in,
                                                        cCodPlantilla_in,
                                                        cSecUsu_in);         
                                                        
    end if;
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_HORAS_SEM_LEGAL RETURN NUMBER IS
    v_cantHorasTab INTEGER := 0;
  BEGIN
    SELECT TO_NUMBER(TRIM(X.LLAVE_TAB_GRAL))
      INTO v_cantHorasTab
      FROM PBL_TAB_GRAL X
     WHERE X.ID_TAB_GRAL = 696;
    RETURN v_cantHorasTab;
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_MAX_HORA_SEM RETURN NUMBER IS
    v_cantHorasTab NUMBER := 0;
  BEGIN
    SELECT TO_NUMBER(X.LLAVE_TAB_GRAL)
      INTO v_cantHorasTab
      FROM PBL_TAB_GRAL X
     WHERE X.ID_TAB_GRAL = 695;
    RETURN v_cantHorasTab;
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_HORAS_TURNO(cCodGrupoCia_in IN CHAR, 
                                  cCodTurno_in IN CHAR)
    RETURN NUMBER IS
    v_Hora NUMBER := 0.0;
  BEGIN
    SELECT (CASE
             WHEN (t.hora_fin - t.hora_inic ) < 0 THEN
              (t.hora_fin+1 - t.hora_inic)
             ELSE
              t.hora_fin - t.hora_inic
           END) * 24 * 60
      INTO v_Hora
      FROM pbl_turno t
     WHERE t.cod_grupo_cia = cCodGrupoCia_in
       AND t.cod_turno = cCodTurno_in
       and t.ind_concepto in (g_TIPO_TURNO, g_TIPO_DESCANSO);
    RETURN v_Hora;
  END;
--********************************************************************************
  FUNCTION CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in IN CHAR, 
                                     cCodTurno_in IN CHAR)
    RETURN NUMBER IS
    v_Hora NUMBER:= 0.00;
  
  BEGIN
    --lp 05/09/2015
    SELECT (CASE
             WHEN (t.hora_fin  - t.hora_inic) < 0 THEN
              (t.hora_fin +1  - t.hora_inic )
             ELSE
              (t.hora_fin - t.hora_inic)
           END) * 24 * 60 - nvl(t.MINUTOS_REFRIGERIO, 0)
      INTO v_Hora
      FROM pbl_turno t
     WHERE t.cod_grupo_cia = cCodGrupoCia_in
       AND t.cod_turno = cCodTurno_in;
    RETURN v_Hora;
  END;
--********************************************************************************  
  FUNCTION CTRL_F_SAVE_HORARIO_CAB(cCodGrupoCia_in     IN CHAR,
                                   cCodLocal_in        IN CHAR,
                                   cFecInicio_IN       IN CHAR,
                                   cFecFin_IN          IN CHAR,
                                   cRefCodPlantilla_IN IN CHAR,
                                   cUsuCrea_in         IN CHAR) RETURN CHAR IS
  
    v_CodHorario pbl_cab_horario.cod_horario%type;
    v_CodNumera  varchar(10) := '609';
  BEGIN
    v_CodHorario := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.Obtener_Numeracion(cCodGrupoCia_in,
                                                                                           cCodLocal_in,
                                                                                           v_CodNumera),
                                                          10,
                                                          '0',
                                                          'I');
  
    INSERT INTO pbl_cab_horario
      (cod_grupo_cia,
       cod_local,
       cod_horario,
       fec_inicio,
       fec_fin,
       ref_cod_plantilla,
       usu_crea)
    VALUES
      (cCodGrupoCia_in,
       cCodLocal_in,
       v_CodHorario,
       TO_DATE(cFecInicio_IN, 'dd/mm/yyyy'),
       TO_DATE(cFecFin_IN, 'dd/mm/yyyy'),
       cRefCodPlantilla_IN,
       cUsuCrea_in);
  
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               v_CodNumera,
                                               cUsuCrea_in);
  
    RETURN v_CodHorario;
  
  END;
--********************************************************************************
  procedure CTRL_P_SAVE_HORARIO_DET(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cCodHorario_in  IN CHAR,
                                    cSecHorario_in  IN CHAR,
                                    cIdRol_in       IN CHAR,                               
                                    cSecUsu_in IN CHAR,
                                    cDiaRefrigerio_in  IN CHAR,                                   
                                    cDia1_in   IN CHAR,
                                    cDia2_in   IN CHAR,
                                    cDia3_in   IN CHAR,
                                    cDia4_in   IN CHAR,                               
                                    cDia5_in          IN CHAR,
                                    cDia6_in          IN CHAR,
                                    cDia7_in          IN CHAR,
                                    cUsuCrea_in       IN CHAR,
                                    cIndUltimaFila_in in char,
                                    nCantHrs_in       IN NUMBER) iS
    v_Indicador         char(1);
    v_CantHorasLegalSem number;
    v_CantHorasReal     number;
    v_MaxHoraSemana     number;
  BEGIN
    INSERT INTO pbl_det_horario
      (cod_grupo_cia,
       cod_local,
       cod_horario,
       sec_horario,
       cod_rol,
       sec_usu_local,
       cod_turno_refrigerio,
       cod_dia1,
       cod_dia2,
       cod_dia3,
       cod_dia4,
       cod_dia5,
       cod_dia6,
       cod_dia7,
       num_hora_prog,
       usu_crea)
    VALUES
      (cCodGrupoCia_in,
       cCodLocal_in,
       ccodhorario_in,
       csechorario_in,
       cidrol_in,
       cSecUsu_in,
       cDiaRefrigerio_in,
       cdia1_in,
       cdia2_in,
       cdia3_in,
       cdia4_in,
       cdia5_in,
       cdia6_in,
       cdia7_in,
       nCantHrs_in,
       cUsuCrea_in);
  
    v_MaxHoraSemana := CTRL_F_GET_MAX_HORA_SEM;
  
    SELECT CASE
             WHEN V.DIF < 0 THEN
              'N'
             ELSE
              'S'
           END
      INTO v_Indicador
      FROM (SELECT (v_MaxHoraSemana -
                   (CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA1) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA2) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA3) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA4) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA5) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA6) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA7)) / 60) as "DIF"
              FROM pbl_det_horario P
             WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
               AND P.COD_LOCAL = cCodLocal_in
               AND P.COD_HORARIO = ccodhorario_in
               and p.sec_HORARIO = csechorario_in) V;
  
    if v_Indicador = 'N' then
    
      v_CantHorasReal := round((CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia1_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia2_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia3_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia4_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia5_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia6_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia7_in)) / 60,
                              2);
    
      RAISE_APPLICATION_ERROR(-20020,
                              'La cantidad de horas no permitidas  en la v_fila ' ||
                              csechorario_in||' - Horas : '||v_CantHorasReal);
    end if;
  
    --indicado por Pairazaman 
    --estos campos que actualiza no son necesarios
    --ACTUALIZA_HORAS_PLANTILLA(cCodGrupoCia_in, cCodLocal_in,cCodPlantilla_in,cSecu);
    if cIndUltimaFila_in = 'S' then
      -- actualiza isHoraLegal
      -- //SI TODOS LOS REGISTROS TIENE menos DE 48 HORAS LEGALES
      v_CantHorasLegalSem := CTRL_F_GET_HORAS_SEM_LEGAL;
    
      SELECT CASE
               WHEN V.CTD_DIF > 0 THEN
                'N'
               ELSE
                'C'
             END
        INTO v_Indicador
        FROM (select count(1) CTD_DIF
                FROM (SELECT (v_CantHorasLegalSem -
                             (CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_DIA1) +
                             CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_DIA2) +
                             CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_DIA3) +
                             CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_DIA4) +
                             CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_DIA5) +
                             CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_DIA6) +
                             CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                     P.COD_DIA7)) / 60) DIF
                        FROM pbl_det_horario P
                       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND P.COD_LOCAL = cCodLocal_in
                         AND P.COD_HORARIO = ccodhorario_in) G
               WHERE G.DIF > 0) V;
    
      UPDATE pbl_cab_horario P
         SET P.EST_HORARIO = v_Indicador
       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND P.COD_LOCAL = cCodLocal_in
         AND P.COD_HORARIO = ccodhorario_in;
    end if;
  END;
--********************************************************************************
  FUNCTION CTRL_F_MODIFY_HORARIO_CAB(cCodGrupoCia_in     IN CHAR,
                                     cCodLocal_in        IN CHAR,
                                     cCodHorario_in      IN CHAR,
                                     cRefCodPlantilla_IN IN CHAR,
                                     cUsuCrea_in         IN CHAR) RETURN CHAR IS
  
  BEGIN
  
    update pbl_cab_horario v
       set v.ref_cod_plantilla = cRefCodPlantilla_IN,
           v.usu_mod           = cUsuCrea_in,
           v.fec_mod           = sysdate
     where v.cod_grupo_cia = cCodGrupoCia_in
       and v.cod_local = cCodLocal_in
       and v.cod_horario = cCodHorario_in;
  
    UPDATE pbl_det_horario v
       SET v.Ind_Proc_Mod = 'S'
     where v.cod_grupo_cia = cCodGrupoCia_in
       and v.cod_local = cCodLocal_in
       and v.cod_horario = cCodHorario_in;
  
    RETURN cCodHorario_in;
  
  END;
--********************************************************************************
  procedure CTRL_P_MODIFY_HORARIO_DET(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cCodHorario_in  IN CHAR,
                                      cSecHorario_in  IN CHAR,
                                      cIdRol_in       IN CHAR,                                 
                                      cSecUsu_in IN CHAR,
                                      cDiaRefrigerio_in  IN CHAR,   
                                      cDia1_in   IN CHAR,
                                      cDia2_in   IN CHAR,
                                      cDia3_in   IN CHAR,
                                      cDia4_in   IN CHAR,                                 
                                      cDia5_in          IN CHAR,
                                      cDia6_in          IN CHAR,
                                      cDia7_in          IN CHAR,
                                      cUsuCrea_in       IN CHAR,
                                      cIndUltimaFila_in in char,
                                      nCantHrs_in IN NUMBER) is
    v_Indicador         char(1);
    v_CantHorasLegalSem number;
    v_CantHorasReal     number;
    v_MaxHoraSemana     number;
    v_Existe            number;
  BEGIN
  
    select count(1)
      into v_Existe
      from pbl_det_horario T
     WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
       AND T.COD_LOCAL = cCodLocal_in
       AND T.cod_horario = cCodHorario_in
       AND T.Sec_Horario = cSecHorario_in;
  
    if v_Existe > 0 then
      UPDATE pbl_det_horario T
         SET T.IND_PROC_MOD    = 'N',
             cod_rol           = cidrol_in,
             sec_usu_local     = cSecUsu_in,
             t.cod_turno_refrigerio = cDiaRefrigerio_in,
             cod_DIA1          = cdia1_in,
             cod_DIA2          = cdia2_in,
             cod_DIA3          = cdia3_in,
             cod_DIA4          = cdia4_in,
             cod_DIA5          = cdia5_in,
             cod_DIA6          = cdia6_in,
             cod_DIA7          = cdia7_in,
             fec_mod           = SYSDATE,
             usu_mod           = cusucrea_in,
             t.num_hora_prog   = nCantHrs_in
       WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
         AND T.COD_LOCAL = cCodLocal_in
         AND T.cod_horario = cCodHorario_in
         AND T.Sec_Horario = cSecHorario_in;
    else
      INSERT INTO pbl_det_horario
        (cod_grupo_cia,
         cod_local,
         cod_horario,
         sec_horario,
         cod_rol,
         sec_usu_local,
         cod_turno_refrigerio,
         cod_DIA1,
         cod_DIA2,
         cod_DIA3,
         cod_DIA4,
         cod_DIA5,
         cod_DIA6,
         cod_DIA7,
         usu_crea,
         num_hora_prog)
      VALUES
        (cCodGrupoCia_in,
         cCodLocal_in,
         cCodHorario_in,
         cSecHorario_in,
         cidrol_in,
         cSecUsu_in,
         cDiaRefrigerio_in,
         cdia1_in,
         cdia2_in,
         cdia3_in,
         cdia4_in,
         cdia5_in,
         cdia6_in,
         cdia7_in,
         cusucrea_in,
         nCantHrs_in);
    end if;
  
    v_MaxHoraSemana := CTRL_F_GET_MAX_HORA_SEM;
  
    SELECT CASE
             WHEN V.DIF < 0 THEN
              'N'
             ELSE
              'S'
           END
      INTO v_Indicador
      FROM (SELECT (v_MaxHoraSemana -
                   (CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA1) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA2) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA3) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA4) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA5) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA6) +
                   CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in, P.COD_DIA7)) / 60) as "DIF"
              FROM PBL_DET_HORARIO P
             WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
               AND P.COD_LOCAL = cCodLocal_in
               AND P.COD_HORARIO = cCodHorario_in
               and p.sec_HORARIO = cSecHorario_in) V;
  
    if v_Indicador = 'N' then
    
      v_CantHorasReal := round((CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia1_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia2_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia3_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia4_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia5_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia6_in) +
                              CTRL_F_CALC_MINUTOS_TURNO(cCodGrupoCia_in,
                                                     cdia7_in)) / 60,
                              2);
    
      RAISE_APPLICATION_ERROR(-20020,
                              'La cantidad de horas no permitidas  en la v_fila ' ||
                              cSecHorario_in||' - Horas : '||v_CantHorasReal);
      --);
    end if;
  
    --ACTUALIZA_HORAS_PLANTILLA(cCodGrupoCia_in, cCodLocal_in,cCodPlantilla_in,cSecu);
    if cIndUltimaFila_in = 'S' then
      delete PBL_DET_HORARIO t
       WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
         AND T.COD_LOCAL = cCodLocal_in
         AND T.COD_HORARIO = cCodHorario_in
         AND T.IND_PROC_MOD = 'S';
    
      -- actualiza isHoraLegal
      -- //SI TODOS LOS REGISTROS TIENE menos DE 48 HORAS LEGALES
      v_CantHorasLegalSem := CTRL_F_GET_HORAS_SEM_LEGAL;
    
      SELECT CASE
               WHEN V.CTD_DIF > 0 THEN
                'N'
               ELSE
                'C'
             END
        INTO v_Indicador
        FROM (
              
              select count(1) CTD_DIF
                FROM (SELECT (v_CantHorasLegalSem -
                              (CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_DIA1) +
                              CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_DIA2) +
                              CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_DIA3) +
                              CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_DIA4) +
                              CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_DIA5) +
                              CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_DIA6) +
                              CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                      P.COD_DIA7)) / 60) DIF
                         FROM PBL_DET_HORARIO P
                        WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                          AND P.COD_LOCAL = cCodLocal_in
                          AND P.COD_HORARIO = cCodHorario_in) G
               WHERE G.DIF > 0) V;
    
      UPDATE PBL_CAB_HORARIO P
         SET P.EST_HORARIO = v_Indicador
       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND P.COD_LOCAL = cCodLocal_in
         AND P.COD_HORARIO = cCodHorario_in;
    end if;
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_LISTA_HORARIO_CAB(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR) RETURN FarmaCursor IS
    v_curHorario FarmaCursor;
  BEGIN
    OPEN v_curHorario FOR
      SELECT cab.cod_horario || 'Ã' ||
             TO_CHAR(cab.fec_inicio, 'dd/mm/yyyy ') || 'Ã' ||
             TO_CHAR(cab.fec_fin, 'dd/mm/yyyy ') || 'Ã' ||
             TO_CHAR(cab.fec_crea, 'dd/mm/yyyy HH24:MI:SS') || 'Ã' ||
             CASE 
               WHEN TRUNC(SYSDATE) BETWEEN cab.fec_inicio AND cab.fec_fin THEN
                    'EN PROCESO'
               ELSE
                    'PLANIFICADO'
               END || 'Ã' ||
             nvl(cab.ref_cod_plantilla, ' ')
        FROM pbl_cab_horario cab
       WHERE cab.cod_grupo_cia = cCodGrupoCia_in
         AND cab.cod_local = cCodLocal_in
         AND SYSDATE < cab.fec_fin
       ORDER BY cab.cod_horario DESC;
    RETURN v_curHorario;
  END;
--********************************************************************************  
  FUNCTION CTRL_F_GET_LISTA_HORARIO_DET(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodHorario_IN  IN CHAR) RETURN FarmaCursor
   IS
    v_curHorario FarmaCursor;
  BEGIN
    OPEN v_curHorario FOR
      SELECT NVL(det.sec_horario, ' ') || 'Ã' || det.cod_rol || 'Ã' ||
             det.sec_usu_local || 'Ã' || NVL(det.cod_dia1, 'N') || 'Ã' ||
             NVL(det.cod_dia2, 'N') || 'Ã' || NVL(det.cod_dia3, 'N') || 'Ã' ||
             NVL(det.cod_dia4, 'N') || 'Ã' || NVL(det.cod_dia5, 'N') || 'Ã' ||
             NVL(det.cod_dia6, 'N') || 'Ã' || NVL(det.cod_dia7, 'N') || 'Ã' ||
              NVL(det.cod_turno_refrigerio, 'N')
        FROM pbl_det_horario det
       WHERE det.cod_grupo_cia = cCodGrupoCia_in
         AND det.cod_local = cCodLocal_in
         AND det.cod_horario = cCodHorario_IN;
  
    RETURN v_curHorario;
  end;
--********************************************************************************
  FUNCTION CTRL_F_GET_CMBO_PLANTILLA(cCodGrupoCia_in IN CHAR, 
                                     cCodLocal_in IN CHAR)
    RETURN FarmaCursor IS
    v_curHorario FarmaCursor;
  BEGIN
    OPEN v_curHorario FOR
      SELECT CAB.COD_PLANTILLA || 'Ã' || CAB.DESC_CORTA
        FROM PBL_CAB_PLANTILLA CAB
       WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
         AND CAB.COD_LOCAL = cCodLocal_in
         AND CAB.EST_PLANTILLA != 'P';
    RETURN v_curHorario;
  END;
--********************************************************************************  
  FUNCTION CTRL_F_VALIDA_USU_SOLICITUD(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cSecUsuLocal_in IN CHAR,
                                       cFecInicio_in   IN CHAR,
                                       cFecFin_in      IN CHAR,
                                       cCodDia1_in     IN CHAR,
                                       cCodDia2_in     IN CHAR,
                                       cCodDia3_in     IN CHAR,
                                       cCodDia4_in     IN CHAR,
                                       cCodDia5_in     IN CHAR,
                                       cCodDia6_in     IN CHAR,
                                       cCodDia7_in     IN CHAR) RETURN CHAR IS
    v_MSN_RESPUESTA VARCHAR2(500):= 'N';
    v_DebeValidar  char(1) := 'N';
  BEGIN
    SELECT CASE
             WHEN TIP > 0 THEN
              'S'
             ELSE
              'N'
           END
      into v_DebeValidar
      FROM (SELECT count(nvl((T.IND_CONCEPTO), '-')) TIP
              FROM PBL_TURNO T
             WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
               AND T.COD_TURNO in (cCodDia1_in,
                                   cCodDia2_in,
                                   cCodDia3_in,
                                   cCodDia4_in,
                                   cCodDia5_in,
                                   cCodDia6_in,
                                   cCodDia7_in)
               AND T.IND_CONCEPTO in (g_TIPO_TURNO, g_TIPO_DESCANSO)) V;
  
    IF v_DebeValidar = 'S' THEN
    
      v_MSN_RESPUESTA := CTRL_F_VALIDA_SOL_TIPO(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cSecUsuLocal_in,
                                               g_TIPO_SOLICITUD_VACACIONES,
                                               cFecInicio_in,
                                               cFecFin_in);
    
      v_MSN_RESPUESTA := CTRL_F_VALIDA_SOL_TIPO(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cSecUsuLocal_in,
                                               g_TIPO_SOLICITUD_SUBSIDIO,
                                               cFecInicio_in,
                                               cFecFin_in);
    
      v_MSN_RESPUESTA := CTRL_F_VALIDA_SOL_TIPO(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cSecUsuLocal_in,
                                               g_TIPO_SOLICITUD_CESE,
                                               cFecInicio_in,
                                               cFecFin_in);
    
    END IF;
  
    RETURN v_MSN_RESPUESTA;
  END;  
--********************************************************************************
  FUNCTION CTRL_F_VALIDA_SOL_TIPO(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cSecUsuLocal_in  IN CHAR,
                                   nSecTipoSol_in   IN NUMBER,
                                   cFecInicio_in    IN CHAR,
                                   cFecFin_in       IN CHAR) RETURN VARCHAR2 IS
    v_Dni            CHAR(8);
    v_USUARIO        VARCHAR2(100);
    v_Rpta           varchar2(3000) := 'N';
    v_FechaIni    date := to_date(trim(cFecInicio_in), 'dd/mm/yyyy');
    v_FechaFin    date := to_date(trim(cFecFin_in), 'dd/mm/yyyy');
    v_MinSecRegistro number := 0;
  BEGIN
    if (nSecTipoSol_in = g_TIPO_SOLICITUD_VACACIONES or
       nSecTipoSol_in = g_TIPO_SOLICITUD_SUBSIDIO) then
      SELECT min(p.sec_registro * 1)
        INTO v_MinSecRegistro
        FROM PBL_SOLICITUD P
       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND P.COD_LOCAL = cCodLocal_in
         AND P.SEC_USU_COL = cSecUsuLocal_in
         AND P.cod_mae_tipo = nSecTipoSol_in
         and p.est_solicitud in ('A','G')
         and ((p.FEC_INICIO <= v_FechaIni AND p.FEC_FIN >= v_FechaIni) OR
             (p.FEC_INICIO <= v_FechaFin AND p.FEC_FIN >= v_FechaFin));
    else
      if nSecTipoSol_in = g_TIPO_SOLICITUD_CESE then
        SELECT min(p.sec_registro * 1)
          INTO v_MinSecRegistro
          FROM PBL_SOLICITUD P
         WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
           AND P.COD_LOCAL = cCodLocal_in
           AND P.SEC_USU_COL = cSecUsuLocal_in
           and p.est_solicitud in ('A','G')           
           AND P.cod_mae_tipo = nSecTipoSol_in
           and (v_FechaIni >= p.FEC_INICIO);
      end if;
    end if;
  
    if v_MinSecRegistro > 0 then
    
      SELECT U.DNI_USU, U.NOM_USU || ' ' || U.APE_PAT
        INTO v_Dni, v_USUARIO
        FROM PBL_USU_LOCAL U
       WHERE U.COD_GRUPO_CIA = cCodGrupoCia_in
         AND U.COD_LOCAL = cCodLocal_in
         AND U.SEC_USU_LOCAL = cSecUsuLocal_in;
    
      SELECT case
               when nSecTipoSol_in = g_TIPO_SOLICITUD_VACACIONES or
                    nSecTipoSol_in = g_TIPO_SOLICITUD_SUBSIDIO then
                ('Porque ' || v_USUARIO || (case
                  when nSecTipoSol_in = g_TIPO_SOLICITUD_VACACIONES then
                   ' tiene vacaciones,desde el '
                  when nSecTipoSol_in = g_TIPO_SOLICITUD_VACACIONES then
                   ' tiene subsidios,desde el '
                end) || to_char(S.FEC_INICIO, 'day') || ' ' ||
                to_char(S.FEC_INICIO, 'dd/mm/yyyy') || ' hasta el ' ||
                to_char(S.FEC_FIN, 'day') || ' ' ||
                to_char(S.FEC_FIN, 'dd/mm/yyyy'))
               else
               -- CESADO
                (' ' || v_USUARIO || (' esta cesado,desde el ') ||
                to_char(S.FEC_INICIO, 'day') || ' ' ||
                to_char(S.FEC_INICIO, 'dd/mm/yyyy'))
             end
        INTO v_Rpta
        FROM PBL_SOLICITUD S
       WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
         AND S.COD_LOCAL = cCodLocal_in
         and s.est_solicitud in ('A','G')         
         AND S.SEC_REGISTRO = lpad(v_MinSecRegistro, 10, '0');
    else
      v_Rpta := 'N';
    end if;
  
    if v_Rpta != 'N' then
      RAISE_APPLICATION_ERROR(-20020, v_Rpta);
    end if;
  
    return v_Rpta;
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_FECHA_INI_FIN RETURN FarmaCursor IS
    v_curHorario FarmaCursor;
    v_fecha      DATE;
  BEGIN
    --LP 04/09/2015
    SELECT NVL(max(fec_inicio), SYSDATE - 7) 
    INTO v_fecha 
    from pbl_cab_horario;
    
    OPEN v_curHorario FOR
      SELECT TO_CHAR(v_fecha + (7 - TO_NUMBER(TO_CHAR(v_fecha, 'd'))) + 2,
                     'dd/mm/yyyy') || 'Ã' ||
             TO_CHAR(v_fecha + (7 - TO_NUMBER(TO_CHAR(v_fecha, 'd'))) + 8,
                     'dd/mm/yyyy')
        FROM DUAL;
    RETURN v_curHorario;
  END;
--********************************************************************************
  FUNCTION CTRL_F_CUR_LISTA_USU_ROL(cCodGrupoCia_in IN CHAR, 
                                    cCodLocal_in IN CHAR)
    RETURN FarmaCursor IS
    v_curPlantilla FarmaCursor;
  BEGIN
    OPEN v_curPlantilla FOR
    --LP 05/09/2015
      SELECT '000' || 'Ã' || '000' || 'Ã' || 'Seleccione Rol'
        FROM DUAL
      UNION
      SELECT (r.cod_rol) || 'Ã' || usu.sec_usu_local || 'Ã' ||
             usu.login_usu
        FROM pbl_rol_usu u, pbl_rol r, pbl_usu_local usu
       WHERE usu.cod_grupo_cia = u.cod_grupo_cia
         AND usu.cod_local = u.cod_local
         AND usu.sec_usu_local = u.sec_usu_local
         AND usu.cod_grupo_cia = cCodGrupoCia_in
         AND usu.cod_local = cCodLocal_in
         AND r.cod_rol = u.cod_rol
         AND u.est_rol_usu = 'A'
         AND usu.est_usu = 'A'
         AND u.cod_rol NOT IN ('000', '012', '014', '027')
         AND usu.sec_usu_local BETWEEN '001' AND '899';
    RETURN v_curPlantilla;
  END;
--********************************************************************************
  FUNCTION CTRL_F_VALIDA_EST_HORARIO(cCodGrupoCia_in  IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cCodPlantilla_in IN CHAR) RETURN CHAR IS
    v_flag   CHAR(1):= 'N';
    v_Estado CHAR(1);
  BEGIN
    --lp 05/09/2015
    SELECT C.EST_HORARIO
      INTO v_Estado
      FROM PBL_CAB_HORARIO C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCodLocal_in
       AND C.COD_HORARIO = cCodPlantilla_in;
    IF v_Estado = 'N' THEN
      v_flag := 'S';
    END IF;
    RETURN v_flag;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
      RETURN v_flag;
  END;
--********************************************************************************
  FUNCTION CTRL_F_VALIDA_HORA(cFecEntrada_in IN CHAR, 
                              cFecSalida_in IN CHAR)
    RETURN CHAR IS
    v_flag CHAR(1):= 'S';
  BEGIN
    IF cFecEntrada_in IS NOT NULL THEN
      IF to_date(cFecEntrada_in, 'dd/mm/yyyy HH24:MI') >
         to_date(cFecSalida_in, 'dd/mm/yyyy HH24:MI') THEN
        v_flag := 'N';
      END IF;
      IF to_date(cFecEntrada_in, 'dd/mm/yyyy HH24:MI') =
         to_date(cFecSalida_in, 'dd/mm/yyyy HH24:MI') THEN
        v_flag := 'I';
      END IF;
    END IF;
    RETURN v_flag;
  END;
--********************************************************************************
  FUNCTION CTRL_F_VALIDA_FORMAT_DATE(cFecha_in IN CHAR) RETURN CHAR IS
    v_flag   CHAR(1):= 'N';
    v_Fecha DATE;
  BEGIN
    SELECT TO_DATE(cFecha_in, 'dd/mm/yyyy HH24:MI') INTO v_Fecha FROM DUAL;
    v_flag := 'S';
    RETURN v_flag;
  EXCEPTION
    WHEN OTHERS THEN
      --DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
      DBMS_OUTPUT.put_line('FORMATO NO VALIDO');
      RETURN v_flag;
  END;
--********************************************************************************
  FUNCTION CTRL_F_VALIDA_RANGO_HORAS(cFecEntrada_in  IN CHAR,
                                     cFecSalida_in   IN CHAR,
                                     cCantMarcacion_in IN CHAR) RETURN CHAR IS
    v_flag               CHAR(1);
    v_FecEntrada      DATE;
    v_FecSalida       DATE;
    v_CantMinima        CHAR(10);
    v_CantMaxima        CHAR(10);
    v_CantMinDobleTurno CHAR(10);
  BEGIN
    v_flag := 'N';
    --lp 05/09/2015
    v_FecEntrada := TO_DATE(cFecEntrada_in, 'dd/mm/yyyy HH24:MI');
    v_FecSalida  := TO_DATE(cFecSalida_in, 'dd/mm/yyyy HH24:MI');
    SELECT tab.llave_tab_gral
      INTO v_CantMinima
      FROM pbl_tab_gral tab
     WHERE tab.id_tab_gral = 717; --cantidad de 4 horas 
    SELECT tab.llave_tab_gral
      INTO v_CantMaxima
      FROM pbl_tab_gral tab
     WHERE tab.id_tab_gral = 738; --cantidad de  8 horas
    SELECT tab.llave_tab_gral
      INTO v_CantMinDobleTurno
      FROM pbl_tab_gral tab
     WHERE tab.id_tab_gral = 721; --cantidad minima de 2 horas(doble turno)
    IF cCantMarcacion_in = '1' THEN
      IF ROUND((v_FecSalida - v_FecEntrada) * 24, 4) >= v_CantMinima AND
         ROUND((v_FecSalida - v_FecEntrada) * 24, 4) <= v_CantMaxima THEN
        v_flag := 'S';
      END IF;
    ELSE
      IF ROUND((v_FecSalida - v_FecEntrada) * 24, 4) >=
         v_CantMinDobleTurno AND
         ROUND((v_FecSalida - v_FecEntrada) * 24, 4) <= v_CantMinima THEN
        v_flag := 'S';
      END IF;
    END IF;
    RETURN v_flag;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
      RETURN v_flag;
  END;
--********************************************************************************
  FUNCTION CTRL_F_MSJ_RANGO_HORA(cCantMarcacion_in IN CHAR) RETURN CHAR IS
    v_MSG               VARCHAR2(100);
    v_CantMinima        VARCHAR2(100);
    v_CantMaxima        VARCHAR2(100);
    v_CantMinDobleTurno VARCHAR2(100);
  BEGIN
    v_MSG := 'ERROR AL VALIDAR RANGO';
    SELECT tab.llave_tab_gral
      INTO v_CantMinima
      FROM pbl_tab_gral tab
     WHERE tab.id_tab_gral = 717;
    SELECT tab.llave_tab_gral
      INTO v_CantMaxima
      FROM pbl_tab_gral tab
     WHERE tab.id_tab_gral = 718;
    SELECT tab.llave_tab_gral
      INTO v_CantMinDobleTurno
      FROM pbl_tab_gral tab
     WHERE tab.id_tab_gral = 721;
    IF cCantMarcacion_in = '2' THEN
      v_MSG := ' El rango de horas para su salida debe ser entre: ' ||
               v_CantMinDobleTurno || ' y  ' || v_CantMinima ||
               ' horas.';
    ELSE
      v_MSG := 'El rango de horas para su salida debe ser entre: ' ||
               v_CantMinima || ' y ' || v_CantMaxima || ' horas.';
    END IF;
    RETURN v_MSG;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
      RETURN v_MSG;
  END;
--********************************************************************************
  FUNCTION CTRL_F_FEC_SALIDA_SUG(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in IN CHAR,
                                 cDni_in      IN CHAR,
                                 cFecha_in    IN CHAR) RETURN DATE IS
  
    v_FecSugerida DATE;
  BEGIN
  
    --SI OBTENEMOS EL SECUENCIAL ENTONCES BUSCAMOS LA ULTIMA VENTA.  
    SELECT MAX(CAB.FEC_PED_VTA)
      INTO v_FecSugerida
      FROM VTA_PEDIDO_VTA_CAB CAB
     WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CAB.COD_LOCAL = cCodLocal_in
       AND TRUNC(CAB.FEC_PED_VTA) = cFecha_in
       AND (SEC_USU_LOCAL = (SELECT SEC_USU_LOCAL
                            FROM PBL_USU_LOCAL
                            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                            AND COD_LOCAL = cCodLocal_in
                            AND DNI_USU = cDni_in) 
            OR
            USU_CREA_PED_VTA_CAB = (SELECT LOGIN_USU
                            FROM PBL_USU_LOCAL
                            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                            AND COD_LOCAL = cCodLocal_in
                            AND DNI_USU = cDni_in));    
                               
    IF v_FecSugerida IS NULL THEN
      --fecha de su ultimo cierre
        SELECT MAX(FEC_CREA_MOV_CAJA)
        INTO v_FecSugerida
        FROM CE_MOV_CAJA A
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_LOCAL = cCodLocal_in
        AND TRUNC(FEC_DIA_VTA) = cFecha_in
        AND TIP_MOV_CAJA = 'C'
        AND SEC_USU_LOCAL = (SELECT SEC_USU_LOCAL
                              FROM PBL_USU_LOCAL
                             WHERE COD_LOCAL = cCodLocal_in
                               AND DNI_USU = cDni_in);
    END IF;
    
    IF v_FecSugerida IS NULL THEN
       v_FecSugerida := SYSDATE;
    END IF;
  
    RETURN v_FecSugerida;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
      RETURN v_FecSugerida;
    
  END;
--********************************************************************************
  FUNCTION CTRL_F_VALIDA_MARC_SALIDA RETURN CHAR IS
    v_flag CHAR(1):= 'N';
    v_Ind  CHAR(1);
  BEGIN
    SELECT TAB.LLAVE_TAB_GRAL
      INTO v_Ind
      FROM PBL_TAB_GRAL TAB
     WHERE TAB.ID_TAB_GRAL = 724;
  
    IF v_Ind = 'S' THEN
      v_flag := 'S';
    ELSE
      v_flag := 'N';
    END IF;
    RETURN v_flag;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
      RETURN v_flag;
  END;
--******************************************************************************** 
  FUNCTION CTRL_F_MAX_RANGO_48HRS(cFecEntrada_in  IN CHAR,
                                  cFecSalida_in   IN CHAR,
                                  cCantMarcacion_in IN CHAR) RETURN CHAR IS
    v_flag       CHAR(1):= 'N';
    v_FecEntrada DATE;
    v_FecSalida  DATE;
    v_CantMaxima CHAR(10);
  
  BEGIN
  
    v_FecEntrada := TO_DATE(cFecEntrada_in, 'dd/mm/yyyy HH24:MI');
    v_FecSalida  := TO_DATE(cFecSalida_in, 'dd/mm/yyyy HH24:MI');
  
    SELECT TAB.LLAVE_TAB_GRAL
      INTO v_CantMaxima
      FROM PBL_TAB_GRAL TAB
     WHERE TAB.ID_TAB_GRAL = 718; --cantidad de  8 horas
  
    IF cCantMarcacion_in = '1' THEN
      IF ROUND((v_FecSalida - v_FecEntrada) * 24, 4) > v_CantMaxima THEN
        v_flag := 'S';
      END IF;
    END IF;
  
    RETURN v_flag;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
      RETURN v_flag;
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_TURNO_X_SOL_APR(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cFecIni_in in char,
                                      cFecFin_in in char) RETURN FarmaCursor IS
    v_curTurno FarmaCursor;
  BEGIN
    OPEN v_curTurno FOR
      select v.sec_usu_col|| 'Ã' || 
             (case
               when cod_mae_tipo = g_TIPO_SOLICITUD_VACACIONES  then '248' --VACACIONES  
               when cod_mae_tipo = g_TIPO_SOLICITUD_SUBSIDIO  then '249' --SUBSIDIO  
               when cod_mae_tipo = g_TIPO_SOLICITUD_CESE  then '250' --CESE
             end )|| 'Ã' || 
             to_char(cal.dia,'dd/mm/yyyy')|| 'Ã' || 
             trim(to_char(cal.num_dia,'9990'))
      from   pbl_solicitud v,
             (
              select v1.dia, farma_utility.obtien_num_dia(v1.dia) num_dia
                from (SELECT TRUNC(SYSDATE - 30 * 6) + ROWNUM - 1 DIA
                        FROM LGT_PROD P
                       WHERE ROWNUM <= 365 * 2) v1
             ) cal
      where  v.cod_grupo_cia = cCodGrupoCia_in
      and    v.cod_local = cCodLocal_in
      and    v.est_solicitud in ('A','G')
      and    v.cod_mae_tipo in (g_TIPO_SOLICITUD_VACACIONES, 
                            g_TIPO_SOLICITUD_SUBSIDIO,
                            g_TIPO_SOLICITUD_CESE)
      and    cal.dia between to_date(cFecIni_in,'dd/mm/yyyy') and to_date(cFecFin_in,'dd/mm/yyyy')
      and    cal.DIA between v.fec_inicio and v.fec_fin;

    RETURN v_curTurno;
  END;  
--********************************************************************************
FUNCTION CTRL_F_VALIDA_MARC_USU(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cDni_in IN CHAR)
  RETURN CHAR
--LP 05/09/2015
  IS 
      v_MSN_RESPUESTA VARCHAR2(500) := 'N';
      v_CANT_VACACIONES INTEGER; 
      v_CANT_SUBSIDIO INTEGER;
      v_CANT_CESE INTEGER;
      v_FECHA_INICIO VARCHAR2(60);
      v_FECHA_FIN VARCHAR2(60);
      v_SEC_REGISTRO number;
      v_IndValida varchar2(10);
  BEGIN 
    SELECT LLAVE_TAB_GRAL
    INTO   v_IndValida
    FROM   PBL_TAB_GRAL 
    WHERE  ID_TAB_GRAL = 559;
    
    if v_IndValida = 'S' then
    --VERIFICAMOS SI HAY VACACIONES
    SELECT  
       sum( case
         when cod_mae_tipo = 143  then 1 --VACACIONES  
         else 0
       end )    ,  
       sum(  case
         when cod_mae_tipo = 144  then 1 --SUBSIDIO  
         else 0
       end  )   ,
       sum(  case
         when cod_mae_tipo = 145  then 1 --CESE
         else 0  
       end  )         
      INTO v_CANT_VACACIONES,v_CANT_SUBSIDIO,v_CANT_CESE
      FROM PBL_SOLICITUD P
     WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
       AND P.COD_LOCAL = cCodLocal_in
       AND P.Sec_Usu_Col = (SELECT UL.SEC_USU_LOCAL
                            FROM PBL_USU_LOCAL UL
                            WHERE UL.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND UL.COD_LOCAL = cCodLocal_in
                            AND UL.DNI_USU = cDni_in)
       AND p.est_solicitud in ('A','G')
       and trunc(sysdate) between p.fec_inicio and p.fec_fin;

  IF v_CANT_VACACIONES>0 THEN
  
    SELECT  MIN(P.SEC_REGISTRO) INTO  v_SEC_REGISTRO FROM PBL_SOLICITUD P
     WHERE P.COD_GRUPO_CIA=cCodGrupoCia_in
       AND P.COD_LOCAL=cCodLocal_in
       AND P.Sec_Usu_Col=(SELECT UL.SEC_USU_LOCAL
                            FROM PBL_USU_LOCAL UL
                            WHERE UL.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND UL.COD_LOCAL = cCodLocal_in
                            AND UL.DNI_USU = cDni_in)
       AND p.est_solicitud in ('A','G')
       and cod_mae_tipo = 143
       and trunc(sysdate) between p.fec_inicio and p.fec_fin;
    
    SELECT  to_char(S.FEC_INICIO,'day')||to_char(S.FEC_INICIO,'dd/mm/yyyy'),to_char(S.FEC_FIN,'day')||to_char(S.FEC_FIN,'dd/mm/yyyy')
    INTO v_FECHA_INICIO,v_FECHA_FIN 
    FROM PBL_SOLICITUD S 
    WHERE S.COD_GRUPO_CIA=cCodGrupoCia_in
    AND S.COD_LOCAL=cCodLocal_in
    AND S.SEC_REGISTRO= lpad(v_SEC_REGISTRO,10,'0');
  
     v_MSN_RESPUESTA:='No puede marcar porque presenta vacaciones,desde el '||v_FECHA_INICIO||' hasta el '|| v_FECHA_FIN;
       RETURN  v_MSN_RESPUESTA;
  END IF;
  
  --MENSAJE DE RESPUESTA DE SUBSIDIO
   IF v_CANT_SUBSIDIO>0 THEN
      SELECT  MIN(P.SEC_REGISTRO) INTO  v_SEC_REGISTRO FROM PBL_SOLICITUD P
     WHERE P.COD_GRUPO_CIA=cCodGrupoCia_in
       AND P.COD_LOCAL=cCodLocal_in
       AND P.Sec_Usu_Col=(SELECT UL.SEC_USU_LOCAL
                            FROM PBL_USU_LOCAL UL
                            WHERE UL.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND UL.COD_LOCAL = cCodLocal_in
                            AND UL.DNI_USU = cDni_in)
       AND p.est_solicitud in ('A','G')
       and cod_mae_tipo = 144       
       and trunc(sysdate) between p.fec_inicio and p.fec_fin;
  
      SELECT  trim(to_char(S.FEC_INICIO,'day'))||' '||to_char(S.FEC_INICIO,'dd/mm/yyyy'),trim(to_char(S.FEC_FIN,'day'))||' '||to_char(S.FEC_FIN,'dd/mm/yyyy')
        INTO v_FECHA_INICIO,v_FECHA_FIN 
        FROM PBL_SOLICITUD S 
        WHERE S.COD_GRUPO_CIA=cCodGrupoCia_in
        AND S.COD_LOCAL=cCodLocal_in
        AND S.SEC_REGISTRO= lpad(v_SEC_REGISTRO,10,'0');
  
      v_MSN_RESPUESTA:='No puede marcar porque presenta subsidio,desde el '||v_FECHA_INICIO||' hasta el '|| v_FECHA_FIN;
       RETURN  v_MSN_RESPUESTA;
   END IF;
  
    --MENSAJE DE RESPUESTA DE CESE
     IF v_CANT_CESE > 0 THEN
         SELECT  MIN(P.SEC_REGISTRO) 
         INTO  v_SEC_REGISTRO 
         FROM PBL_SOLICITUD P
       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND P.COD_LOCAL = cCodLocal_in
         AND P.Sec_Usu_Col = (SELECT UL.SEC_USU_LOCAL
                            FROM PBL_USU_LOCAL UL
                            WHERE UL.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND UL.COD_LOCAL = cCodLocal_in
                            AND UL.DNI_USU = cDni_in)
         AND p.est_solicitud in ('A','G')
         and cod_mae_tipo = 145       
         and trunc(sysdate) between p.fec_inicio and p.fec_fin;
                
        SELECT  to_char(S.FEC_INICIO,'day')||to_char(S.FEC_INICIO,'dd/mm/yyyy')
          INTO v_FECHA_INICIO 
          FROM PBL_SOLICITUD S 
          WHERE S.COD_GRUPO_CIA=cCodGrupoCia_in
          AND S.COD_LOCAL=cCodLocal_in
          AND S.SEC_REGISTRO= lpad(v_SEC_REGISTRO,10,'0');
     
        v_MSN_RESPUESTA:='No puede marcar porque Esta cesado,desde el '||v_FECHA_INICIO;
           RETURN  v_MSN_RESPUESTA;
    END IF;
  end if;
   return v_MSN_RESPUESTA;

  END ;  
--********************************************************************************
  FUNCTION CTRL_F_GET_IND_FALTA_TEMP(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     vFecha_in IN VARCHAR) 
  RETURN CHAR IS
  
    v_cant number(2) := 0;
    v_flag char(1) := 'S';
  BEGIN
    
    SELECT count(1)
    INTO v_cant
    FROM LGT_HIST_TEMP_LOC A
    WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND A.COD_LOCAL = cCodLocal_in
    AND TRUNC(A.FEC_CREA_TEMP) = trunc(TO_DATE(nvl(vFecha_in,sysdate),'dd/MM/yyyy'));
    
    IF v_cant > 0 THEN
       v_flag := 'N';
    END IF;
    
    RETURN v_flag;
  
  END;
--********************************************************************************
  FUNCTION CTRL_F_VAR_DATOS_USU(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                vSecUsuLocal_in IN VARCHAR) 
  RETURN varchar2 IS
  
    v_Datos varchar2(3000);
  BEGIN
    
    SELECT u.nom_usu || ' ' ||
           u.ape_pat || ' ' ||
           u.ape_mat
    INTO v_Datos
    FROM pbl_usu_local u
    WHERE u.cod_grupo_cia = cCodGrupoCia_in
    and   u.cod_local = cCodLocal_in
    and   u.sec_usu_local = vSecUsuLocal_in;
    
    
    RETURN v_Datos;
  
  END;
--********************************************************************************
  FUNCTION CTRL_F_VAR_IS_CARGO_VALIDA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      vSecUsuLocal_in IN VARCHAR) 
  RETURN varchar2 IS  
    v_Datos varchar2(10);
  BEGIN
    
    SELECT 
           DECODE(COUNT(1),0,'N','S')
    INTO v_Datos
    FROM pbl_usu_local u,
         CE_MAE_TRAB  M,
         CE_CARGO C
    WHERE u.cod_grupo_cia = cCodGrupoCia_in
    and   u.cod_local = cCodLocal_in
    and   u.sec_usu_local = vSecUsuLocal_in
    AND   U.DNI_USU = M.NUM_DOC_IDEN
    AND   M.COD_CARGO = C.COD_CARGO
    AND   C.ID_VALIDA_HORAS_TRAB = 'S';
        
    RETURN v_Datos;
  
  END;  
--********************************************************************************  
  FUNCTION CTRL_F_VAR_VAL_HRS_TRAB(vHoraFin_in IN date,
                                   vHoraIni_in IN date,
                                   nCantMinRef_in IN NUMBER) 
  RETURN VARCHAR2 IS
  
     v_DiferenciaHoras number(10,2);
     v_Rpta varchar2(20);
  BEGIN
    dbms_output.put_line('INI: ' || vHoraIni_in);
    dbms_output.put_line('FIN: ' || vHoraFin_in);
    
    SELECT /*(
            to_date(v_Fin || vHoraFin_in || ':00','dd/MM/yyyy HH24:MI:SS') - 
            to_date('01/01/2015 ' || vHoraIni_in || ':00','dd/MM/yyyy HH24:MI:SS')
           ) * 24 -
           (nCantMinRef_in / 60)*/
           (CASE WHEN
                to_number((case
                when         
                (vHoraFin_in - vHoraIni_in)<0
                then  
                (vHoraFin_in - vHoraIni_in)
                else
                (vHoraFin_in - vHoraIni_in)
                end) * 24 * 60 - nCantMinRef_in,'9999990.00')/60 >= 0 THEN
                
                to_number((case
                when         
                (vHoraFin_in - vHoraIni_in)<0
                then  
                (vHoraFin_in - vHoraIni_in)
                else
                (vHoraFin_in - vHoraIni_in)
                end) * 24 * 60 - nCantMinRef_in,'9999990.00')/60 ELSE 
                
                (to_number((case
                when         
                (vHoraFin_in - vHoraIni_in)<0
                then  
                (vHoraFin_in - vHoraIni_in)
                else
                (vHoraFin_in - vHoraIni_in)
                end) * 24 * 60 - nCantMinRef_in,'9999990.00')/60) + 24 
              END)
    INTO v_DiferenciaHoras
    FROM dual;
    
    v_Rpta:= trim(to_char(v_DiferenciaHoras,'999,990.00'));

    RETURN v_Rpta;
  
  END;  
--********************************************************************************
  PROCEDURE CTRL_P_SAVE_COPIA_PLANTILLA(cCodGrupoCia_in     IN CHAR,
                                        cCodLocal_in        IN CHAR,
                                        cCodPlantillaRef_in IN CHAR,
                                        cSecUsu_in          IN CHAR,
                                        cUsuCrea_in         IN CHAR) IS  
    v_CodPlantilla pbl_cab_plantilla.cod_plantilla%type;
    vPlantillaRefCab pbl_cab_plantilla%rowtype;

    cursor vCurDetallePlantilla is
    select T.*,
           case
             when 
               RANK() OVER (ORDER BY t.sec_plantilla desc) = 1 then 'S'
             else 'N'
           end tip      
    from   PBL_DET_PLANTILLA t
    where  t.cod_grupo_cia = cCodGrupoCia_in
    and    t.cod_local = cCodLocal_in
    and    t.cod_plantilla = cCodPlantillaRef_in
    order by t.sec_plantilla asc;    
    
    listDetPlantilla vCurDetallePlantilla%ROWTYPE;
  BEGIN
    select *
    into   vPlantillaRefCab
    from   pbl_cab_plantilla p
    where  p.cod_grupo_cia = cCodGrupoCia_in
    and    p.cod_local = cCodLocal_in
    and    p.cod_plantilla = cCodPlantillaRef_in;
  
 v_CodPlantilla := ptoventa_control_asistencia.ctrl_f_save_plantilla_cab(ccodgrupocia_in =>vPlantillaRefCab.Cod_Grupo_Cia,
                                                                   ccodlocal_in => vPlantillaRefCab.Cod_Local,
                                                                   cdesccorta_in => vPlantillaRefCab.Desc_Corta||' copia',
                                                                   cusucrea_in => 'SISTEMAS');
 open vCurDetallePlantilla;
      LOOP
        FETCH vCurDetallePlantilla INTO listDetPlantilla;
        EXIT WHEN vCurDetallePlantilla%NOTFOUND;
       ptoventa_control_asistencia.ctrl_p_save_plantilla_det(ccodgrupocia_in => listDetPlantilla.Cod_Grupo_Cia,
                                                             ccodlocal_in => listDetPlantilla.Cod_Local,
                                                             ccodplantilla_in => v_CodPlantilla,
                                                             csecu_in => listDetPlantilla.Sec_Plantilla,
                                                             cidrol_in => listDetPlantilla.Cod_Rol,
                                                             cdiarefrigerio_in => listDetPlantilla.Cod_Turno_Refrigerio,
                                                             cdia1_in => listDetPlantilla.Cod_Turno1,
                                                             cdia2_in => listDetPlantilla.Cod_Turno2,
                                                             cdia3_in => listDetPlantilla.Cod_Turno3,
                                                             cdia4_in => listDetPlantilla.Cod_Turno4,
                                                             cdia5_in => listDetPlantilla.Cod_Turno5,
                                                             cdia6_in => listDetPlantilla.Cod_Turno6,
                                                             cdia7_in => listDetPlantilla.Cod_Turno7,
                                                             cusucrea_in => cUsuCrea_in,
                                                             cIndUltimaFila_in => listDetPlantilla.Tip,
                                                             nCantHrs_in => listDetPlantilla.Num_Hora_Prog,
                                                             cSecUsu_in => cSecUsu_in);
    
      end loop;
      close vCurDetallePlantilla;

  END;  
--********************************************************************************
  FUNCTION CTRL_P_AVISO_FIN_HORARIO(cCodGrupoCia_in     IN CHAR,
                                     cCodLocal_in        IN CHAR) RETURN VARCHAR IS
     nDiasAntesAviso number;
     vv_filaHorario pbl_cab_horario%rowtype;
     vExiste integer;
     vResultado varchar2(3000) := 'N';
  BEGIN
    
    BEGIN
    select to_number(nvl(trim(LLAVE_TAB_GRAL),'-1'),'999')
    into   nDiasAntesAviso
    from   pbl_tab_gral t
    where  t.id_tab_gral = 569;
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
      nDiasAntesAviso := -1;
    END;
    
    if nDiasAntesAviso > 0  then
      begin
        select *
        INTO   vv_filaHorario
        from   pbl_cab_horario a
        where  trunc(sysdate) between fec_inicio and fec_fin;
        
        if trunc(sysdate) > (vv_filaHorario.Fec_Fin-nDiasAntesAviso) then
          
          select count(1)
          INTO   vExiste
          from   pbl_cab_horario a
          where  trunc(vv_filaHorario.Fec_Fin+1) between fec_inicio and fec_fin;
            
          if vExiste = 0 then   
            vResultado := 'Recuerda que se solo faltan '||vv_filaHorario.Fec_Fin-trunc(sysdate)|| 
             ' para finalizar el horario registrado de la semana.@'||
             'Por favor de planificar con tiempo el horario de la proxima semana.';
          end if;   
           
        end if;
      exception
      when others then
          vResultado := 'N';
      end;
    end if;
  
     return vResultado;
  END;  
--********************************************************************************
FUNCTION CTRL_F_IND_REPITE_VALIDACION(cCodGrupoCia_in     IN CHAR,
                                      cCodLocal_in        IN CHAR) RETURN VARCHAR IS
     vRepiteValidacion CHAR(1) := 'N';
     vv_filaHorario pbl_cab_horario%rowtype;
     vExiste number;
  BEGIN
    BEGIN
    select nvl(trim(LLAVE_TAB_GRAL),'N')
    into   vRepiteValidacion
    from   pbl_tab_gral t
    where  t.id_tab_gral = 570;
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
      vRepiteValidacion := 'N';
    END;
    
    select *
    INTO   vv_filaHorario
    from   pbl_cab_horario a
    where  trunc(sysdate) between fec_inicio and fec_fin;
    
    if trunc(sysdate) = vv_filaHorario.Fec_Fin then
      
          select decode(count(1),0,'S',vRepiteValidacion)
          INTO   vExiste
          from   pbl_cab_horario a
          where  trunc(vv_filaHorario.Fec_Fin+1) between fec_inicio and fec_fin;

    end if;
    
    return vRepiteValidacion;
 END;
--********************************************************************************
FUNCTION CTRL_F_MSJ_FRECUENTE_MARCACION(cCodGrupoCia_in     IN CHAR,
                                        cCodLocal_in        IN CHAR) RETURN VARCHAR IS
  vMsj varchar2(3000);
begin
  select a.desc_larga
  into   vMsj 
  from   pbl_tab_gral a  
  where  a.id_tab_gral = 573 ;
 return vMsj ;
end;
--********************************************************************************
  FUNCTION CTRL_F_GET_LIST_CESE(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR) RETURN FarmaCursor IS
    v_curTurno FarmaCursor;
 CURSOR curUsuRevisa IS  
 SELECT   distinct D.SEC_USU_LOCAL,
          l.nom_usu||' '||l.ape_pat||' '||l.ape_mat datos,
          l.dni_usu
    FROM   PBL_CAB_HORARIO A,
           PBL_DET_HORARIO D,
           pbl_usu_local l
    WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    A.COD_LOCAL = cCodLocal_in
    and    a.fec_fin >= trunc(sysdate-10)
    AND    A.COD_GRUPO_CIA = D.COD_GRUPO_CIA
    AND    A.COD_LOCAL = D.COD_LOCAL
    AND    A.COD_HORARIO = D.COD_HORARIO
    and    d.cod_grupo_cia = l.cod_grupo_cia
    and    d.cod_local = l.cod_local
    and    d.sec_usu_local = l.sec_usu_local;
    
 CURSOR curHorarioUsuRevisa(cSecUsuLocal in char) IS  
 SELECT   distinct D.SEC_USU_LOCAL,a.cod_grupo_cia,a.cod_local,a.cod_horario
    FROM   PBL_CAB_HORARIO A,
           PBL_DET_HORARIO D
    WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    A.COD_LOCAL = cCodLocal_in
    and    a.fec_fin >= trunc(sysdate-10)
    and    d.sec_usu_local = cSecUsuLocal
    AND    A.COD_GRUPO_CIA = D.COD_GRUPO_CIA
    AND    A.COD_LOCAL = D.COD_LOCAL
    AND    A.COD_HORARIO = D.COD_HORARIO;    
    
    v_fila    curUsuRevisa%rowtype;    
    v_filaDos curHorarioUsuRevisa%rowtype; 
    
    v_CadenaFaltaUsu varchar2(3000);
    v_AuxCadena varchar2(100);
    v_ExisteFaltas number;       
       
  BEGIN
    delete TMP_DNI_POR_CESAR;
    OPEN curUsuRevisa;
    LOOP
    --- BEGIN
    FETCH curUsuRevisa INTO v_fila;
     EXIT WHEN curUsuRevisa%NOTFOUND;
       v_CadenaFaltaUsu := '';
       v_ExisteFaltas := -1;
              dbms_output.put_line('v_fila>> '||v_fila.sec_usu_local||' '||v_fila.datos);
              
        OPEN curHorarioUsuRevisa(v_fila.sec_usu_local);
        LOOP
        --- BEGIN
        FETCH curHorarioUsuRevisa INTO v_filaDos;
         EXIT WHEN curHorarioUsuRevisa%NOTFOUND;     
     
                   dbms_output.put_line('v_filaDos>> '||v_filaDos.Cod_Horario||' ');
     
        select replace(EXISTE_DIA_1||EXISTE_DIA_2||EXISTE_DIA_3||EXISTE_DIA_4||EXISTE_DIA_5||EXISTE_DIA_6||EXISTE_DIA_7,'0',null) faltas
        into    v_AuxCadena
        from   (
        select f.sec_usu_local,f.dni_usu,f.datos,f.fec_inicio,f.fec_fin,
               case when f.dia_1 is null then 0 else decode(f.marca_dia_1,0,1,0) end EXISTE_DIA_1,
               case when f.dia_2 is null then 0 else decode(f.marca_dia_2,0,2,0) end EXISTE_DIA_2,
               case when f.dia_3 is null then 0 else decode(f.marca_dia_3,0,3,0) end EXISTE_DIA_3,
               case when f.dia_4 is null then 0 else decode(f.marca_dia_4,0,4,0) end EXISTE_DIA_4,
               case when f.dia_5 is null then 0 else decode(f.marca_dia_5,0,5,0) end EXISTE_DIA_5,
               case when f.dia_6 is null then 0 else decode(f.marca_dia_6,0,6,0) end EXISTE_DIA_6,
               case when f.dia_7 is null then 0 else decode(f.marca_dia_7,0,7,0) end EXISTE_DIA_7                           
        from   (
        select b.sec_usu_local,
               u.dni_usu,
               u.nom_usu||' '||u.ape_pat||' '||u.ape_mat DATOS ,
               a.fec_inicio,a.fec_fin,
               TO_CHAR((case when b.cod_dia1 not in ('250','246','249','248') then a.fec_inicio + 0 else null END),'YYYYMMDD') DIA_1,
               TO_CHAR((case when b.cod_dia2 not in ('250','246','249','248') then a.fec_inicio + 1 else null END),'YYYYMMDD') DIA_2,
               TO_CHAR((case when b.cod_dia3 not in ('250','246','249','248') then a.fec_inicio + 2 else null END),'YYYYMMDD') DIA_3,              
               TO_CHAR((case when b.cod_dia4 not in ('250','246','249','248') then a.fec_inicio + 3 else null END),'YYYYMMDD') DIA_4,
               TO_CHAR((case when b.cod_dia5 not in ('250','246','249','248') then a.fec_inicio + 4 else null END),'YYYYMMDD') DIA_5,
               TO_CHAR((case when b.cod_dia6 not in ('250','246','249','248') then a.fec_inicio + 5 else null END),'YYYYMMDD') DIA_6,
               TO_CHAR((case when b.cod_dia7 not in ('250','246','249','248') then a.fec_inicio + 6 else null END),'YYYYMMDD') DIA_7,
               case
                 when a.fec_inicio + 0 > sysdate then 1 
               else (
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 0
               )
               end marca_dia_1,
               case
                 when a.fec_inicio + 1 > sysdate then 1 
               else (
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 1
               )
               end marca_dia_2,
               case
                 when a.fec_inicio + 2 > sysdate then 1 
               else (
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 2
               ) end marca_dia_3,
               case
                 when a.fec_inicio + 3 > sysdate then 1 
               else(
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 3
               ) end marca_dia_4,
               case
                 when a.fec_inicio + 4 > sysdate then 1 
               else(
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 4
               ) end marca_dia_5,
               case
                 when a.fec_inicio + 5 > sysdate then 1 
               else (
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 5
               ) end marca_dia_6  ,
               case
                 when a.fec_inicio + 6 > sysdate then 1 
               else (
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 6
               )end marca_dia_7  
        from   pbl_cab_horario a,
               pbl_det_horario b,
               pbl_usu_local u
        where  a.cod_grupo_cia = v_filaDos.cod_grupo_cia
        and    a.cod_local = v_filaDos.cod_local
        and    a.cod_horario = v_filaDos.cod_horario
        and    b.sec_usu_local = v_filaDos.sec_usu_local
        and    a.cod_grupo_cia = b.cod_grupo_cia
        and    a.cod_local = b.cod_local
        and    a.cod_horario = b.cod_horario
        and    b.cod_grupo_cia = u.cod_grupo_cia
        and    b.cod_local = u.cod_local
        and    b.sec_usu_local = u.sec_usu_local
        and    a.fec_fin >= trunc(sysdate-10)
               ) f
        where  rownum < 2
        );
        	
       v_CadenaFaltaUsu := v_CadenaFaltaUsu || v_AuxCadena;
     end loop;
     close curHorarioUsuRevisa;
                        dbms_output.put_line('v_filaDos>> '||v_filaDos.Cod_Horario||' >> '||v_CadenaFaltaUsu);
     
     SELECT INSTR(v_CadenaFaltaUsu,'1234')+INSTR(v_CadenaFaltaUsu,'2345')+
            INSTR(v_CadenaFaltaUsu,'3456')+INSTR(v_CadenaFaltaUsu,'4567')+
            INSTR(v_CadenaFaltaUsu,'5671')+INSTR(v_CadenaFaltaUsu,'6712')+
            INSTR(v_CadenaFaltaUsu,'7123')
       into v_ExisteFaltas     
       FROM DUAL;
       dbms_output.put_line('Existe Faltas>> '||v_ExisteFaltas);
       if v_ExisteFaltas > 0 then
         insert into TMP_DNI_POR_CESAR
         (dni_usu,datos_usu)
         values
         (v_fila.dni_usu,v_fila.datos);
       end if;
            
   end loop;
   close curUsuRevisa;
    
   
   -- retorna datos
    OPEN v_curTurno FOR
      SELECT distinct t.dni_usu || 'Ã' || 
             t.datos_usu
        FROM TMP_DNI_POR_CESAR t;
        
    RETURN v_curTurno;
  END;
--********************************************************************************
  FUNCTION CTRL_F_GET_LIST_INASISTENCIA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR) RETURN FarmaCursor IS
    v_curTurno FarmaCursor;
 CURSOR curUsuRevisa IS  
 SELECT   distinct D.SEC_USU_LOCAL,
          l.nom_usu||' '||l.ape_pat||' '||l.ape_mat datos,
          l.dni_usu
    FROM   PBL_CAB_HORARIO A,
           PBL_DET_HORARIO D,
           pbl_usu_local l
    WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    A.COD_LOCAL = cCodLocal_in
    and    a.fec_fin >= trunc(sysdate-10)
    AND    A.COD_GRUPO_CIA = D.COD_GRUPO_CIA
    AND    A.COD_LOCAL = D.COD_LOCAL
    AND    A.COD_HORARIO = D.COD_HORARIO
    and    d.cod_grupo_cia = l.cod_grupo_cia
    and    d.cod_local = l.cod_local
    and    d.sec_usu_local = l.sec_usu_local;
    
 CURSOR curHorarioUsuRevisa(cSecUsuLocal in char) IS  
 SELECT   distinct D.SEC_USU_LOCAL,a.cod_grupo_cia,a.cod_local,a.cod_horario
    FROM   PBL_CAB_HORARIO A,
           PBL_DET_HORARIO D
    WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    A.COD_LOCAL = cCodLocal_in
    and    a.fec_fin >= trunc(sysdate-10)
    and    d.sec_usu_local = cSecUsuLocal
    AND    A.COD_GRUPO_CIA = D.COD_GRUPO_CIA
    AND    A.COD_LOCAL = D.COD_LOCAL
    AND    A.COD_HORARIO = D.COD_HORARIO;    
    
    v_fila    curUsuRevisa%rowtype;    
    v_filaDos curHorarioUsuRevisa%rowtype; 
    
    v_CadenaFaltaUsu varchar2(3000);
    v_AuxCadena varchar2(100);
    v_ExisteFaltas number;       
       
  BEGIN
    delete TMP_DNI_INASISTENCIA;
    OPEN curUsuRevisa;
    LOOP
    --- BEGIN
    FETCH curUsuRevisa INTO v_fila;
     EXIT WHEN curUsuRevisa%NOTFOUND;
       v_CadenaFaltaUsu := '';
       v_ExisteFaltas := -1;
              dbms_output.put_line('v_fila>> '||v_fila.sec_usu_local||' '||v_fila.datos);
              
        OPEN curHorarioUsuRevisa(v_fila.sec_usu_local);
        LOOP
        --- BEGIN
        FETCH curHorarioUsuRevisa INTO v_filaDos;
         EXIT WHEN curHorarioUsuRevisa%NOTFOUND;     
     
                   dbms_output.put_line('v_filaDos>> '||v_filaDos.Cod_Horario||' ');
     
        select replace(EXISTE_DIA_1||EXISTE_DIA_2||EXISTE_DIA_3||EXISTE_DIA_4||EXISTE_DIA_5||EXISTE_DIA_6||EXISTE_DIA_7,'0',null) faltas
        into    v_AuxCadena
        from   (
        select f.sec_usu_local,f.dni_usu,f.datos,f.fec_inicio,f.fec_fin,
               case when f.dia_1 is null then 0 else decode(f.marca_dia_1,0,1,0) end EXISTE_DIA_1,
               case when f.dia_2 is null then 0 else decode(f.marca_dia_2,0,2,0) end EXISTE_DIA_2,
               case when f.dia_3 is null then 0 else decode(f.marca_dia_3,0,3,0) end EXISTE_DIA_3,
               case when f.dia_4 is null then 0 else decode(f.marca_dia_4,0,4,0) end EXISTE_DIA_4,
               case when f.dia_5 is null then 0 else decode(f.marca_dia_5,0,5,0) end EXISTE_DIA_5,
               case when f.dia_6 is null then 0 else decode(f.marca_dia_6,0,6,0) end EXISTE_DIA_6,
               case when f.dia_7 is null then 0 else decode(f.marca_dia_7,0,7,0) end EXISTE_DIA_7                           
        from   (
        select b.sec_usu_local,
               u.dni_usu,
               u.nom_usu||' '||u.ape_pat||' '||u.ape_mat DATOS ,
               a.fec_inicio,a.fec_fin,
               TO_CHAR((case when b.cod_dia1 not in ('250','246','249','248') then a.fec_inicio + 0 else null END),'YYYYMMDD') DIA_1,
               TO_CHAR((case when b.cod_dia2 not in ('250','246','249','248') then a.fec_inicio + 1 else null END),'YYYYMMDD') DIA_2,
               TO_CHAR((case when b.cod_dia3 not in ('250','246','249','248') then a.fec_inicio + 2 else null END),'YYYYMMDD') DIA_3,              
               TO_CHAR((case when b.cod_dia4 not in ('250','246','249','248') then a.fec_inicio + 3 else null END),'YYYYMMDD') DIA_4,
               TO_CHAR((case when b.cod_dia5 not in ('250','246','249','248') then a.fec_inicio + 4 else null END),'YYYYMMDD') DIA_5,
               TO_CHAR((case when b.cod_dia6 not in ('250','246','249','248') then a.fec_inicio + 5 else null END),'YYYYMMDD') DIA_6,
               TO_CHAR((case when b.cod_dia7 not in ('250','246','249','248') then a.fec_inicio + 6 else null END),'YYYYMMDD') DIA_7,
               case
                 when a.fec_inicio + 0 > sysdate then 1 
               else (
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 0
               )
               end marca_dia_1,
               case
                 when a.fec_inicio + 1 > sysdate then 1 
               else (
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 1
               )
               end marca_dia_2,
               case
                 when a.fec_inicio + 2 > sysdate then 1 
               else (
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 2
               ) end marca_dia_3,
               case
                 when a.fec_inicio + 3 > sysdate then 1 
               else(
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 3
               ) end marca_dia_4,
               case
                 when a.fec_inicio + 4 > sysdate then 1 
               else(
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 4
               ) end marca_dia_5,
               case
                 when a.fec_inicio + 5 > sysdate then 1 
               else (
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 5
               ) end marca_dia_6  ,
               case
                 when a.fec_inicio + 6 > sysdate then 1 
               else (
               select count(1)
               from  pbl_ingreso_personal i
               where i.cod_grupo_cia = u.cod_grupo_cia
               and   i.cod_local = u.cod_local
               and   i.dni = u.dni_usu
               and   i.fecha = a.fec_inicio + 6
               )end marca_dia_7  
        from   pbl_cab_horario a,
               pbl_det_horario b,
               pbl_usu_local u
        where  a.cod_grupo_cia = v_filaDos.cod_grupo_cia
        and    a.cod_local = v_filaDos.cod_local
        and    a.cod_horario = v_filaDos.cod_horario
        and    b.sec_usu_local = v_filaDos.sec_usu_local
        and    a.cod_grupo_cia = b.cod_grupo_cia
        and    a.cod_local = b.cod_local
        and    a.cod_horario = b.cod_horario
        and    b.cod_grupo_cia = u.cod_grupo_cia
        and    b.cod_local = u.cod_local
        and    b.sec_usu_local = u.sec_usu_local
        and    a.fec_fin >= trunc(sysdate-10)
               ) f
        where  rownum < 2
        );
        	
       v_CadenaFaltaUsu := v_CadenaFaltaUsu || v_AuxCadena;
     end loop;
     close curHorarioUsuRevisa;
                        dbms_output.put_line('v_filaDos>> '||v_filaDos.Cod_Horario||' >> '||v_CadenaFaltaUsu);
     
     SELECT length(v_CadenaFaltaUsu)
       into v_ExisteFaltas     
       FROM DUAL;
       dbms_output.put_line('Existe Faltas>> '||v_ExisteFaltas);
       if v_ExisteFaltas > 0 then
         insert into TMP_DNI_INASISTENCIA
         (dni_usu,datos_usu, cant_faltas)
         values
         (v_fila.dni_usu,v_fila.datos, v_ExisteFaltas);
       end if;
            
   end loop;
   close curUsuRevisa;
    
   
   -- retorna datos
    OPEN v_curTurno FOR
      SELECT distinct t.dni_usu || 'Ã' || 
             t.datos_usu || 'Ã' ||
             t.cant_faltas
        FROM TMP_DNI_INASISTENCIA t;
        
    RETURN v_curTurno;
  END;
--********************************************************************************
procedure CTRL_P_VALIDA_APROBACION(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cCodPlantilla_in  IN CHAR,
                                   cSecUsu_in        IN CHAR) is
 vExisteMasHoras number;                                   
 v_IdSolicitud pbl_solicitud.sec_registro%type;
 v_Dni      pbl_usu_local.dni_usu%type;
 v_UsuLocal pbl_usu_local.login_usu%type;
BEGIN
      select COUNT(1)
      INTO   vExisteMasHoras
      from  (
      SELECT (PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                                    P.COD_TURNO1) +
             PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                                    P.COD_TURNO2) +
             PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                                    P.COD_TURNO3) +
             PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                                    P.COD_TURNO4) +
             PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                                    P.COD_TURNO5) +
             PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                                    P.COD_TURNO6) +
             PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_CALC_MINUTOS_TURNO(P.COD_GRUPO_CIA,
                                                                    P.COD_TURNO7)) / 60 TIME_FILA
        FROM PBL_DET_PLANTILLA P
        WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   P.COD_LOCAL = cCodLocal_in
        AND   P.COD_PLANTILLA = cCodPlantilla_in
        ) F
      WHERE F.TIME_FILA > 48;  
   /* SE COMENTA PORQUE YA NO SERA NECESARIO, SIN EMBARGO SE DEJARA COMENTADO
   PARA RECORDARLO MIENTRAS NO SE LLEGUE A LA VERSION FINAL.
  if vExisteMasHoras > 0 then
      
     --cambia estado la plantilla lo coloca como PENDIENTE DE APROBAR
      UPDATE PBL_CAB_PLANTILLA P
         SET P.EST_PLANTILLA = 'P'
       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND P.COD_LOCAL = cCodLocal_in
         AND P.COD_PLANTILLA = cCodPlantilla_in;
    
  select u.dni_usu, 
         u.login_usu
  into   v_Dni, v_UsuLocal
  from   pbl_usu_local u
  where  u.cod_grupo_cia = cCodGrupoCia_in
  and    u.cod_local = cCodLocal_in
  and    u.sec_usu_local = cSecUsu_in;
                       
     --CREA SOLICITUD DE 48 HORAS y envia CORREO
   v_IdSolicitud :=  ptoventa_gest_solicitud.gest_f_graba_solicitud(ccodgrupocia_in => cCodGrupoCia_in,
                                                    ccodlocal_in => cCodLocal_in,
                                                    cdni_in => v_Dni,
                                                    cfechainicio_in => TO_CHAR(sysdate,'DD/MM/YYYY'),
                                                    cfechafin_in => null,
                                                    ccodmaetipo_in =>  6,--codigo de maestro detalle
                                                    ccodmaetipo2_in => null,
                                                    vnomarchivo_in => null,
                                                    cfecpropuesta => null,
                                                    vobservacion_in => cCodPlantilla_in,
                                                    vusucreacion_in => v_UsuLocal,
                                                    csecusucrea_in => cSecUsu_in);                                                    
     
    ptoventa_autoriza.aut_p_envia_mail_sol(cnumregsol_in => v_IdSolicitud);
     
  end if;
  */
        
END;
--********************************************************************************
END PTOVENTA_CONTROL_ASISTENCIA;
/
