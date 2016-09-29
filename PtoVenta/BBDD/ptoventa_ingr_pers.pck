CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_INGR_PERS" is

  TYPE FarmaCursor IS REF CURSOR;
  g_cTipoEntrada CONSTANT CHAR(2) := '01';
  g_cTipoSalida CONSTANT CHAR(2) := '02';

  C_COD_GRUPO_CIA             CHAR(3) := '001';
  C_COD_LOCAL                 CHAR(3) := '009';

  vNombreDirectorio           VARCHAR2(15) := 'DIR_SAP_EMP';
  vNombreArchivo              VARCHAR2(50) := 'CONTROL_INGRESO'||
                                              TO_CHAR(SYSDATE,'dd')||'-'||
                                              TO_CHAR(SYSDATE,'mm')||'-'||
                                              TO_CHAR(SYSDATE,'yyyy')||'.txt';

  ARCHIVO_TEXTO      UTL_FILE.FILE_TYPE;


  --Descripcion: Retorna datos del personal.
  --Fecha       Usuario		Comentario
  --14/09/2007  ERIOS     Creacion
  --23/11/2007  dubilluz  modificacion
  --06/12/2007  dubilluz  modificacion
  FUNCTION GET_PERSONAL_2(cCodGrupoCia_in IN CHAR,
                        cCodLocal_in    IN CHAR,
                        cDni_in         IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Verifica e inserta el registro del personal.
  --Fecha       Usuario		Comentario
  --14/09/2007  ERIOS     Creacion
  --02/09/2007  JCORTEZ   MODIFICACION
  --13/11/2007  dubilluz  modificacion
  --26/11/2007  DUBILLUZ  MODIFICACION
  --05/12/2007  DUBILLUZ  MODIFICACION
  --03/03/2015  CHUANES   MODIFICACION
  PROCEDURE GRABA_REG_PERSONAL(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cDni_in         IN CHAR,
                               cTipo_in        IN CHAR,
                               cCodcia_in      IN CHAR,
                               cCodTrab_in     IN CHAR,
                               cCodHorar_in    IN CHAR,
                               cIndicador_in    IN CHAR);
  --13/11/2007  DUBILLUZ  MODIFICACION
  --23/11/2007  DUBILLUZ  MODIFICACION
  --26/11/2007  DUBILLUZ  MODIFICACION
  FUNCTION GET_LISTA_REGISTROS(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Inserta registro.
  --Fecha       Usuario		Comentario
  --14/09/2007  ERIOS     Creacion
  PROCEDURE INSERTA_REGISTRO(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cDni_in         IN CHAR,
                             dDiaTrabajo_in  IN DATE,
                             cCodcia_in      IN CHAR,
                             cCodTrab_in     IN CHAR,
                             cCodHorar_in    IN CHAR);

  --Descripcion: Actualiza entrada.
  --Fecha       Usuario		Comentario
  --14/09/2007  ERIOS     Creacion
  PROCEDURE ACTUALIZA_INGRESO(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cDni_in         IN CHAR,
                              dDiaTrabajo_in  IN DATE,
                              dHoraReg_in     IN DATE);
  --Descripcion: Actualiza salida.
  --Fecha       Usuario		Comentario
  --14/09/2007  ERIOS     Creacion
  PROCEDURE ACTUALIZA_SALIDA(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cDni_in         IN CHAR,
                             dDiaTrabajo_in  IN DATE,
                             dHoraReg_in     IN DATE);

  --Descripcion: Verifica entrada.
  --Fecha       Usuario		Comentario
  --14/09/2007  ERIOS     Creacion
  FUNCTION VERIFICA_INGRESO(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cDni_in         IN CHAR,
                            dDiaTrabajo_in  IN DATE)
  RETURN CHAR;

  --Descripcion: Calculo valores.
  --Fecha       Usuario		Comentario
  --17/09/2007  ERIOS     Creacion
  /*PROCEDURE CALCULA_DATOS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  dDiaTrabajo_in IN DATE,cIndRecalculo_in IN CHAR DEFAULT 'N',
  vIdUsu_in IN VARCHAR2 DEFAULT 'PCK_CAL_DAT');*/

  --Descripcion: Actualiza los valores calculados.
  --Fecha       Usuario		Comentario
  --19/09/2007  ERIOS     Creacion
 /* PROCEDURE ACTUALIZA_DATOS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);
*/

  --Descripcion: Se valida en envio de corre por numero de tardanzas por trabajador
  --Fecha       Usuario		Comentario
  --28/09/2007  JCORTEZ     Creacion
 /* PROCEDURE GET_VALIDA_TARDANZA(cDNI_in         IN CHAR);
*/


  --Descripcion: Se envia el correo deseado eviando el formato. y parametros
  --Fecha       Usuario		Comentario
  --28/09/2007  ERIOS     Creacion
  /*PROCEDURE ENVIA_CORREO_INFORMACION(cCodGrupoCia_in  IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     vAsunto_in       IN CHAR,
                                     vTitulo_in       IN CHAR,
                                     vMensaje_in      IN CHAR,
                                     v_EmailTrab      IN CHAR,
                                     v_EmailJefe      IN CHAR,
                                     v_Tardanzas      IN NUMBER);*/

  --Descripcion: calcula valores por trabajador
  --Fecha       Usuario		Comentario
  --28/09/2007  JCORTEZ     Creacion
   /*PROCEDURE CALCULA_DATOS_TRAB(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in IN CHAR,
                           dDiaTrabajo_in IN DATE,
                           cIndRecalculo_in IN CHAR DEFAULT 'N',
                           vDni_in IN CHAR,
                           vIdUsu_in IN VARCHAR2 DEFAULT 'PCK_CAL_DAT');
*/
  --Descripcion: se corre el proceso luego del ingreso del trabajador
  --Fecha       Usuario		Comentario
  --28/09/2007  JCORTEZ     Creacion
 /* PROCEDURE ACTUALIZA_DATOS_TRABAJADOR(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cDni_in IN CHAR);*/

  --Descripcion: se verifica la existencia de registro de un trabajador
  --Fecha       Usuario		Comentario
  --03/09/2007  JCORTEZ   Creacion
  --23/11/2007  DUBILLUZ  MODIFICACION
  --05/12/2007   DUBILLUZ  MODIFICACION
  --06/12/2007   DUBILLUZ  MODIFICACION
  FUNCTION TRA_EXIST_REGISTRO(cCodGrupoCia_in IN CHAR,
                              cDni_in         IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: se valida la salida cuando intente registrar entrada por segudan vez
  --Fecha       Usuario		Comentario
  --03/09/2007  JCORTEZ   Creacion
  FUNCTION TRA_VALIDA_SALIDA(cCodGrupoCia_in IN CHAR,
                             cDni_in         IN CHAR)
  RETURN FarmaCursor;

  --Graba entrada y/o salido 2
  --27/03/2008 dubilluz  modificacion
  PROCEDURE GRABA_REG_PERSONAL_2(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cDni_in         IN CHAR,
                                 cTipo_in        IN CHAR,
                                 d_dDiaTrabajo   IN DATE,
                                  cIndicador        IN CHAR);

  --Descripcion: Se lista el historico de temperaturas
  --Fecha       Usuario		Comentario
  --11/02/2009  JCORTEZ   Creacion
   FUNCTION LISTA_HISTORICO_TEMP(cCodGrupoCia IN CHAR,
                                vCodLocal_in  IN CHAR)
   RETURN FarmaCursor;


  --Descripcion: Se ingresa datos de temperatura
  --Fecha       Usuario		Comentario
  --11/02/2009  JCORTEZ   Creacion
  PROCEDURE IND_INGRESA_TEMP(cCodGrupoCia  IN CHAR,
                            cCodLocal     IN CHAR,
                            cUsuCrea         IN CHAR,
                            cSecUsu          IN CHAR,
                            nValVta          IN NUMBER,
                            nValAlmacen      IN NUMBER,
                            nValRefrig       IN NUMBER);

  --Descripcion: Se obtiene nuevo secuencial
  --Fecha       Usuario		Comentario
  --11/02/2009  JCORTEZ   Creacion
  FUNCTION PROM_NUEVO_SECUENCIAL(cCodGrupoCia  IN CHAR,
  			                   cCodLocal IN CHAR)
  RETURN NUMBER;


  --Descripcion: Se valida rol del usuario
  --Fecha       Usuario		Comentario
  --11/02/2009  JCORTEZ   Creacion
  FUNCTION VERIFICA_ROL_USU(cCodGrupoCia_in  IN CHAR,
                         cCodLocal_in     IN CHAR,
                         vSecUsu_in       IN CHAR,
                         cCodRol_in       IN CHAR)
  RETURN CHAR;

  --Descripcion: Se valida si existen registro de ingreso
  --Fecha       Usuario		Comentario
  --11/02/2009  JCORTEZ   Creacion
  FUNCTION VERIFICA_INGR_TEMP_USU(cCodGrupoCia_in  IN CHAR,
                         cCodLocal_in     IN CHAR)
  RETURN CHAR;

  --Descripcion: Se obtiene sec por DNI
  --Fecha       Usuario		Comentario
  --11/02/2009  JCORTEZ   Creacion
   FUNCTION GET_SEC_USU_X_DNI(cCodGrupoCia_in  IN CHAR,
                              cCodLocal_in     IN CHAR,
                              cDni_in          IN CHAR)
   RETURN CHAR;

  --Descripcion: Se lista historico por fechas
  --Fecha       Usuario		Comentario
  --11/02/2009  JCORTEZ   Creacion
  FUNCTION LISTA_HIST_FILTRO(cCodGrupoCia IN CHAR,
                                    vCodLocal_in IN CHAR,
                                    cFecIni_in  IN CHAR,
                                    cFecFin_in  IN CHAR)
  RETURN FarmaCursor;



     --Descripcion: Se valida rol del trabajador de local
  --Fecha       Usuario		Comentario
  --25/02/2009  ASOLIS   Creacion
  FUNCTION VERIFICA_ROL_TRAB_LOCAL(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                    vSecUsu_in      IN CHAR,
                                    cCodRol_inCaj   IN CHAR,
                                    cCodRol_inVend  IN CHAR,
                                    cCodRol_inAdmL  IN CHAR)
  RETURN CHAR;

--***************************************************************************  
  --Descripcion: Indica si la marcacion de control de ingreso permite manual o electronico.
  --Fecha       Usuario		Comentario
  --03/03/2015  CHUANES   Creacion
--***************************************************************************  
    FUNCTION INGR_F_IND_MARCACION RETURN CHAR;

--***************************************************************************  
  --Descripcion: Lista la marcación  solo del usuario que marco su ingreso o salida
  --Fecha       Usuario		Comentario
  --20/07/2015  CHUANES   Creacion
--***************************************************************************    
  FUNCTION INGR_F_GET_LISTA_REG_DNI(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cDni_in IN CHAR)
  RETURN FarmaCursor;

--***************************************************************************  
  --Descripcion: Evalua si es administrador del local
  --Fecha       Usuario		Comentario
  --30/07/2015  CHUANES   Creacion
--***************************************************************************      
  FUNCTION INGR_F_ADMINISTRADOR_LOCAL(cCodGrupoCia_in IN CHAR,
                                      cCodCia_in      IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cDni_in IN CHAR)
  RETURN CHAR;

--***************************************************************************  
  --Descripcion: Suguiere el tipo de marcación en control de ingreso
  --Fecha       Usuario		Comentario
  --30/07/2015  CHUANES   Creacion
--***************************************************************************   
   FUNCTION INGR_F_GET_PERSONAL(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cDni_in         IN CHAR)
   RETURN FarmaCursor;

--***************************************************************************  
  --Descripcion: Mensaje de bienvenida de la nueva pantalla de marcacion de asistencia.
  --Fecha       Usuario		Comentario
  --27/08/2015  CHUANES   Creacion
  --03/12/2015  ASOSA   Modificacion
--***************************************************************************   
  FUNCTION  INGR_F_MSG_BIENVENIDA(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in       IN CHAR,
                                  cDni_in            IN CHAR)
  RETURN VARCHAR2;

--***************************************************************************  
  --Descripcion: Mensaje de saludo
  --Fecha       Usuario		Comentario
  --27/08/2015  CHUANES   Creacion
--***************************************************************************   
  FUNCTION  INGR_F_MSG_SALUDO RETURN VARCHAR2;

--***************************************************************************  
  --Descripcion: Indica si se puede ingresar DNI digitando
  --Fecha       Usuario		Comentario
  --03/12/2015  ASOSA     Creacion
--***************************************************************************      
  FUNCTION INGR_F_GET_IND_DNI_ING RETURN CHAR;
  
  --Descripcion: Indica si esta activa la marcacion de huella
  --Fecha       Usuario		Comentario
  --03/12/2015  ASOSA     Creacion
--***************************************************************************      
  FUNCTION INGR_F_GET_IND_HUELLA RETURN CHAR;
  
--***************************************************************************  
  --Descripcion: Obtener hora de sistema
  --Fecha       Usuario		Comentario
  --27/08/2015  CHUANES   Creacion
--*************************************************************************** 
  FUNCTION INGR_F_GET_HORA_SIST RETURN CHAR;

--***************************************************************************  
  --Descripcion: Cuando debe mostrar la pantalla de marcacion de entrada.
  --Fecha       Usuario		Comentario
  --27/08/2015  CHUANES   Creacion
--*************************************************************************** 
  FUNCTION INGR_F_MARCAR_ENTRADA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR, 
                                 cSecUsuLocal_in IN CHAR)
  RETURN CHAR;

--***************************************************************************  
  --Descripcion: VERIFICA SI EL USUARIO LOGEADO ES EL QUE ESTA REALIZANDO LA MARCACION
  --Fecha       Usuario		Comentario
  --27/08/2015  CHUANES   Creacion
--***************************************************************************   
  FUNCTION INGR_F_USU_LOGEADO(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR, 
                             cSecUsuLocal_in IN CHAR,
                             cDni_in IN CHAR)
  RETURN CHAR;

--***************************************************************************  
  --Descripcion: VERIFICA SI EL USUARIO LOGEADO ES EL QUE ESTA REALIZANDO LA MARCACION
  --Fecha       Usuario		Comentario
  --09/09/2015  CHUANES   Creacion
--***************************************************************************
  FUNCTION INGR_F_IND_ACT_MARCACION RETURN CHAR;

--***************************************************************************  
  --Descripcion: Obtiene la hora planificada del ultimo registro de ingreso
  --Fecha       Usuario		Comentario
  --22/10/2015  EMAQUERA  Creacion
--***************************************************************************
  FUNCTION INGR_F_GET_FEC_SALIDA_HOR(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cDni_in         IN CHAR)
  RETURN DATE ;
  
--***************************************************************************  
  --Descripcion: Obtiene el indicador para ver que mensaje mostrar
  --Fecha       Usuario		Comentario
  --22/10/2015  EMAQUERA  Creacion
  --19/11/2015  ASOSA     Modificacion
--***************************************************************************
  FUNCTION INGR_F_GET_IND_MSJE(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cDni_in         IN CHAR)
  RETURN CHAR;


--***************************************************************************  
  --Descripcion: Inserta o Actualiza la Entrada y/o Salida
  --Fecha       Usuario		Comentario
  --26/10/2015  EMAQUERA  Creacion
  --19/11/2015  ASOSA     Modificacion
--***************************************************************************   
    PROCEDURE INGR_P_REGULARIZAR_SALIDA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cDni_in         IN CHAR,
                                      vFecHrSalida_in IN varchar2);
--***************************************************************************  
  --Descripcion: Determina si un turno es de un dia para otro
  --Fecha       Usuario		Comentario
  --19/11/2015  ASOSA     CREACION
--***************************************************************************   
   FUNCTION INGR_F_GET_IND_DIA_SIG(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cDni_in         IN CHAR)
    return char;
end;
/
CREATE OR REPLACE PACKAGE BODY "PTOVENTA_INGR_PERS" is

  FUNCTION GET_PERSONAL_2(cCodGrupoCia_in IN CHAR,
                        cCodLocal_in    IN CHAR,
                        cDni_in         IN CHAR)
  RETURN FarmaCursor
  IS
    v_CurLista FarmaCursor;
    v_dDiaTrabajo DATE;
    v_cTipo CHAR(2);
   -- v_dHoraSalida DATE;
	v_cCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN

       -- Obtengo la última fecha registrada para el trabajador
       SELECT NVL(MAX(I.FECHA),TRUNC(SYSDATE-10)) INTO v_dDiaTrabajo
       FROM PBL_INGRESO_PERSONAL I
       WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
       AND   I.COD_LOCAL = cCodLocal_in
       AND   I.DNI       = cDni_in;

       -- Si el último registro fue ayer u hoy, me fijo si grabó entrada o salida
       IF v_dDiaTrabajo >= TRUNC(SYSDATE-1) THEN
          BEGIN
           -- Obtengo el valor de SALIDA para el registro encontrado
           SELECT CASE WHEN (I.SALIDA IS NULL and I.ENTRADA IS NOT NULL and I.SALIDA_2 IS NULL) THEN '02' -- Sugiero SALIDA
                       WHEN (I.SALIDA IS NOT NULL and I.ENTRADA_2 IS NULL) THEN '01'
                       WHEN (I.SALIDA IS NOT NULL and I.ENTRADA_2 IS NOT NULL and I.SALIDA_2 IS NULL) THEN '02'
                       WHEN (I.SALIDA_2 IS NOT NULL) THEN '01'
                       WHEN (I.SALIDA_2 IS NOT NULL AND I.SALIDA IS NULL) THEN '01'

                       ELSE '01' -- Sugiero ENTRADA
                  END INTO v_cTipo
           FROM  PBL_INGRESO_PERSONAL I
           WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
           AND   I.COD_LOCAL     = cCodLocal_in
           AND   I.DNI   = cDni_in
           AND   I.FECHA = v_dDiaTrabajo;
          END;
       ELSE
          BEGIN
               v_cTipo := '01';
          END;
       END IF;

	--ERIOS 04.04.2014 Trabajador por Cia
	SELECT COD_CIA INTO v_cCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;

    OPEN v_CurLista FOR
    SELECT NOM_TRAB||' '||APE_PAT_TRAB||' '||APE_MAT_TRAB ||'Ã'||
           C.COD_CIA ||'Ã'||
           C.COD_TRAB ||'Ã'||
           v_cTipo ||'Ã'||
           nvl(C.IND_FISCALIZADO,' ')
    FROM   CE_MAE_TRAB C
-- 2014-09-28 JOLIVA: SE PERMITE MARCAR ASISTENCIA EN CUALQUIER LOCAL DE LA CADENA
--    WHERE  C.COD_CIA = v_cCodCia
    WHERE 1= 1 -- C.COD_CIA = v_cCodCia
	--AND C.COD_TRAB
	AND C.EST_TRAB = 'A'
	AND C.NUM_DOC_IDEN = cDni_in;

    RETURN v_CurLista;
  END;

  /***************************************************************************/
  --13/11/2007  DUBILLUZ  MODIFICACION
  --26/11/2007  DUBILLUZ  MODIFICACION
  --05/12/2007  DUBILLUZ  MODIFICACION
  --03/03/2015  CHUANES   MODIFICACION

  PROCEDURE GRABA_REG_PERSONAL(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cDni_in         IN CHAR,
                               cTipo_in        IN CHAR,
                               cCodcia_in      IN CHAR,
                               cCodTrab_in     IN CHAR,
                               cCodHorar_in    IN CHAR,
                               cIndicador_in    IN CHAR)
  AS
    v_dDiaTrabajo DATE;

    v_IndFiscalizado CHAR(1) := 'S';
    --VALOR PARA REVISAR LA SALIDA 2
    --26/11/2007 DUBILLUZ MODIFICACION
    --19/10/2015 EMAQUERA MODIFICACION
    v_RegIngreso     PBL_INGRESO_PERSONAL%ROWTYPE;
--    v_CodHorario     PBL_CAB_HORARIO.COD_HORARIO%TYPE;
--    v_CodTurno       PBL_TURNO.COD_TURNO%TYPE;
--    v_SecUsu         PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE;
--    v_SQL            VARCHAR2(1000);
--    v_MinHorario     NUMBER(6);
--    v_MinUltVta      NUMBER(6);
--    v_FecUltVta      VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE;
--    v_Salida         VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE;
    v_cCodCia PBL_LOCAL.COD_CIA%TYPE;
    cTipo_in2 CHAR(1) := '';
  BEGIN
  --INI ASOSA - 24/09/2015 - CTRLASIST
  IF cTipo_in = '01' THEN
     cTipo_in2 := PTOVENTA.PTOVENTA_CONTROL_ASISTENCIA.g_IND_ENTRADA;
  ELSIF cTipo_in = '02' THEN
     cTipo_in2 := PTOVENTA.PTOVENTA_CONTROL_ASISTENCIA.g_IND_SALIDA;
  END IF;
  IF LENGTH(cTipo_in) = 1 THEN
     cTipo_in2 := cTipo_in;
  END IF;
  --FIN ASOSA - 24/09/2015 - CTRLASIST
  --ERIOS 04.04.2014 Trabajador por Cia
  SELECT COD_CIA INTO v_cCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;

     --Se valida que el trabajador sea fiscalizado
     IF cCodTrab_in IS NOT NULL THEN
       --ERIOS 03.12.2014 Solucion temporal. Se debe definir los roles a fiscalizar.
       SELECT DISTINCT NVL(IND_FISCALIZADO,'S') INTO v_IndFiscalizado
       FROM CE_MAE_TRAB C
-- 2014-09-28 JOLIVA: SE PERMITE MARCAR ASISTENCIA EN CUALQUIER LOCAL DE LA CADENA
--    WHERE  C.COD_CIA = v_cCodCia
    WHERE  1 = 1 -- C.COD_CIA = v_cCodCia
    --AND C.COD_TRAB
    AND C.EST_TRAB = 'A'
    AND C.NUM_DOC_IDEN = cDni_in;
     END IF;

    IF v_IndFiscalizado = 'N' THEN
     RAISE_APPLICATION_ERROR(-20001,'Usted no puede registrarse, ya que no es un trabajador fiscalizado');
    END IF;

       IF cTipo_in2 = PTOVENTA_CONTROL_ASISTENCIA.g_IND_ENTRADA THEN  -- Si se desea registrar ENTRADA
          BEGIN
             -- Se obtiene la última fecha registrada en el Sistema
             SELECT NVL(MAX(I.FECHA),TRUNC(SYSDATE-10)) INTO v_dDiaTrabajo
             FROM PBL_INGRESO_PERSONAL I
             WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
               AND I.COD_LOCAL = cCodLocal_in
               AND I.DNI = cDni_in;

             IF v_dDiaTrabajo != TRUNC(SYSDATE) THEN  -- Si la última fecha registrada NO es HOY
                -- Graba la ENTRADA para el día de hoy
                INSERT INTO PBL_INGRESO_PERSONAL
                (COD_GRUPO_CIA,COD_LOCAL,DNI,FECHA,COD_CIA, COD_TRAB,COD_HORARIO,USU_CREA, ENTRADA,IND_ENTRADA_1)
                VALUES
                (cCodGrupoCia_in,cCodLocal_in,cDni_in,TRUNC(SYSDATE),cCodcia_in, cCodTrab_in,cCodHorar_in,cDni_in,SYSDATE,cIndicador_in);
             ELSE -- Ya existe una ENTRADA para el día de hoy
                -- MENSAJE DE ERROR
                --RAISE_APPLICATION_ERROR(-20201,'Usted ya registró su Salida el día de hoy no puede volver a ingresar');
                GRABA_REG_PERSONAL_2(cCodGrupoCia_in,cCodLocal_in,
                                     cDni_in ,cTipo_in2 ,v_dDiaTrabajo,cIndicador_in);
             END IF;
          END;
       ELSE  -- Si se desea registrar SALIDA
          BEGIN
             -- Se obtiene la última fecha registrada en el Sistema
             SELECT NVL(MAX(I.FECHA),TRUNC(SYSDATE-10)) INTO v_dDiaTrabajo
             FROM PBL_INGRESO_PERSONAL I
             WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
               AND I.COD_LOCAL = cCodLocal_in
               AND I.DNI = cDni_in;
/*
             IF v_dDiaTrabajo < TRUNC(SYSDATE-1) THEN  -- Si la última fecha registrada fue ANTES DE AYER
                BEGIN
                  INSERT INTO PBL_INGRESO_PERSONAL
                  (COD_GRUPO_CIA,COD_LOCAL,DNI,FECHA,COD_CIA, COD_TRAB,COD_HORARIO,USU_CREA, SALIDA)
                  VALUES
                  (cCodGrupoCia_in,cCodLocal_in,cDni_in,TRUNC(SYSDATE),cCodcia_in, cCodTrab_in,cCodHorar_in,cDni_in,SYSDATE);
                END;
             ELSE -- Si la última fecha registrada fue AYER u HOY
               */
                BEGIN
                     -- Se verifica si ya se grabó la SALIDA de esa día
                     SELECT I.* INTO v_RegIngreso
                     FROM PBL_INGRESO_PERSONAL I
                     WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND I.COD_LOCAL = cCodLocal_in
                       AND I.DNI = cDni_in
                       AND I.FECHA = v_dDiaTrabajo;


                     IF (v_RegIngreso.Salida_2 IS NULL AND v_RegIngreso.Entrada_2 IS NOT NULL ) THEN
                            UPDATE PBL_INGRESO_PERSONAL I
                            SET I.SALIDA_2 = SYSDATE,
                                I.USU_MOD = cDni_in,
                                I.FEC_MOD = SYSDATE,
                                I.IND_SALIDA_2=cIndicador_in--CHUANES 03.03.2015
                            WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND I.COD_LOCAL = cCodLocal_in
                                  AND I.DNI = cDni_in
                                  AND I.FECHA = v_dDiaTrabajo;
                     ELSIF (v_RegIngreso.Salida IS NULL AND v_RegIngreso.Entrada IS NOT NULL
                            AND v_RegIngreso.Salida_2 IS NULL)  THEN
                        BEGIN
                            UPDATE PBL_INGRESO_PERSONAL I
                            SET I.SALIDA =  SYSDATE,
                                I.USU_MOD = cDni_in,
                                I.FEC_MOD = SYSDATE,
                                I.IND_SALIDA_1=cIndicador_in--CHUANES 03.03.2015
                            WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND I.COD_LOCAL = cCodLocal_in
                                  AND I.DNI = cDni_in
                                  AND I.FECHA = v_dDiaTrabajo;
                        END;
                      ELSE
                        BEGIN
                             IF v_dDiaTrabajo = TRUNC(SYSDATE) THEN  -- Si la última fecha registrada fue HOY
                                --RAISE_APPLICATION_ERROR(-20002,'Usted ya registró su salida el día de hoy.');
                                GRABA_REG_PERSONAL_2(cCodGrupoCia_in,cCodLocal_in,
                                                     cDni_in ,cTipo_in2 ,v_dDiaTrabajo,cIndicador_in);
                             ELSE
                                INSERT INTO PBL_INGRESO_PERSONAL
                                (COD_GRUPO_CIA,COD_LOCAL,DNI,FECHA,COD_CIA, COD_TRAB,COD_HORARIO,USU_CREA, SALIDA)
                                VALUES
                                (cCodGrupoCia_in,cCodLocal_in,cDni_in,TRUNC(SYSDATE),cCodcia_in, cCodTrab_in,cCodHorar_in,cDni_in,SYSDATE);
                             END IF;
                        END;
                     END IF;
                END;
             --END IF;
          END;

       END IF;

  /*

         --Se valida que el trabajador sea fiscalizado
         IF cCodTrab_in IS NOT NULL THEN
           SELECT NVL(IND_FISCALIZADO,'S') INTO v_IndFiscalizado
           FROM CE_MAE_TRAB WHERE NUM_DOC_IDEN=cDni_in;
         END IF;


          IF VERIFICA_INGRESO(cCodGrupoCia_in,cCodLocal_in,
                              cDni_in,v_dDiaTrabajo) = 'N' THEN


            INSERTA_REGISTRO(cCodGrupoCia_in,cCodLocal_in,
            cDni_in,v_dDiaTrabajo,cCodcia_in,cCodTrab_in,
            cCodHorar_in);
          END IF;

          ACTUALIZA_SALIDA(cCodGrupoCia_in,cCodLocal_in,
          cDni_in,v_dDiaTrabajo,v_dHoraReg);

        END IF;
        ELSE
         RAISE_APPLICATION_ERROR(-20001,'Usted no puede registrarse, ya que no es un trabajador fiscalizado');
        END IF;

    EXCEPTION
             WHEN  DUP_VAL_ON_INDEX  then
                   RAISE_APPLICATION_ERROR(-20002,'NO puede ingresar su entrada.');
*/


  END;
  /***************************************************************************/
  --13/11/2007  DUBILLUZ  MODIFICACION
  --23/11/2007  DUBILLUZ  MODIFICACION
  --26/11/2007  DUBILLUZ  MODIFICACION
  FUNCTION GET_LISTA_REGISTROS(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR)
  RETURN FarmaCursor
  IS
    v_curLista FarmaCursor;
  BEGIN
    OPEN v_curLista FOR
      SELECT P.DNI|| 'Ã' ||
             NOM_TRAB||' '||APE_PAT_TRAB||' '||APE_MAT_TRAB || 'Ã' ||
             NVL(TO_CHAR(P.ENTRADA,'HH24:MI:SS'),' ')|| 'Ã' ||
             NVL(TO_CHAR(P.SALIDA,'HH24:MI:SS'),' ')|| 'Ã' ||
             --añadido
             --23/11/2007 dubilluz modificacion
             NVL(TO_CHAR(P.ENTRADA_2,'HH24:MI:SS'),' ')|| 'Ã' ||
             NVL(TO_CHAR(P.SALIDA_2,'HH24:MI:SS'),' ')|| 'Ã' ||
             ---fin
             TO_CHAR(P.FEC_CREA,'yyyyMMddHH24MISS')
      FROM   PBL_INGRESO_PERSONAL P,
             CE_MAE_TRAB C
      WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    P.COD_LOCAL = cCodLocal_in
      AND    P.FECHA     = TRUNC(SYSDATE)
      AND    P.COD_CIA   = C.COD_CIA(+)
      AND    P.COD_TRAB  = C.COD_TRAB(+)
	  AND C.EST_TRAB = 'A'
    UNION
    SELECT  P.DNI || 'Ã' ||
            NOM_TRAB||' '||APE_PAT_TRAB||' '||APE_MAT_TRAB  || 'Ã' ||
            NVL(TO_CHAR(P.ENTRADA,'HH24:MI:SS  dd/MM'),' ') || 'Ã' ||
            NVL(TO_CHAR(P.SALIDA,'HH24:MI:SS   dd/MM'),' ') || 'Ã' ||
             --añadido
             --23/11/2007 dubilluz modificacion
             NVL(TO_CHAR(P.ENTRADA_2,'HH24:MI:SS  dd/MM'),' ')|| 'Ã' ||
             NVL(TO_CHAR(P.SALIDA_2,'HH24:MI:SS   dd/MM'),' ')|| 'Ã' ||
             ---fin
            TO_CHAR(P.FEC_CREA,'yyyyMMddHH24MISS')
            FROM PBL_INGRESO_PERSONAL P,
                 CE_MAE_TRAB C
            WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND   P.COD_LOCAL = cCodLocal_in
            AND   P.COD_CIA   = C.COD_CIA(+)
            AND   P.COD_TRAB  = C.COD_TRAB(+)
			AND C.EST_TRAB = 'A'
            AND   P.FECHA     = TRUNC(SYSDATE-1)
            --AND P.SALIDA IS NULL
            AND P.DNI NOT IN (SELECT P.DNI
                              FROM   PBL_INGRESO_PERSONAL P
                              WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND    P.COD_LOCAL = cCodLocal_in
                              AND    P.FECHA     = TRUNC(SYSDATE));
    RETURN v_curLista;
  END;
  /***************************************************************************/
  PROCEDURE INSERTA_REGISTRO(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cDni_in         IN CHAR,
                             dDiaTrabajo_in  IN DATE,
                             cCodcia_in      IN CHAR,
                             cCodTrab_in     IN CHAR,
                             cCodHorar_in    IN CHAR)
  AS
  BEGIN
    INSERT INTO PBL_INGRESO_PERSONAL(COD_GRUPO_CIA,COD_LOCAL,DNI,FECHA,COD_CIA,
                                     COD_TRAB,COD_HORARIO,USU_CREA)
    VALUES(cCodGrupoCia_in,cCodLocal_in,cDni_in,dDiaTrabajo_in,cCodcia_in,
           cCodTrab_in,cCodHorar_in,cDni_in);
  END;
  /***************************************************************************/
  PROCEDURE ACTUALIZA_INGRESO(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cDni_in         IN CHAR,
                              dDiaTrabajo_in  IN DATE,
                              dHoraReg_in     IN DATE)
  AS
  BEGIN
    UPDATE PBL_INGRESO_PERSONAL I
    SET I.ENTRADA = dHoraReg_in,
        I.USU_MOD = cDni_in,
        I.FEC_MOD = SYSDATE
    WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
          AND I.COD_LOCAL = cCodLocal_in
          AND I.DNI = cDni_in
          AND I.FECHA = dDiaTrabajo_in;
  END;
  /***************************************************************************/
  PROCEDURE ACTUALIZA_SALIDA(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cDni_in         IN CHAR,
                             dDiaTrabajo_in  IN DATE,
                             dHoraReg_in     IN DATE)
  AS
  BEGIN
    UPDATE PBL_INGRESO_PERSONAL I
    SET I.SALIDA  = dHoraReg_in,
        I.USU_MOD = cDni_in,
        I.FEC_MOD = SYSDATE
    WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   I.COD_LOCAL     = cCodLocal_in
    AND   I.DNI   = cDni_in
    AND   I.FECHA = dDiaTrabajo_in;
  END;
  /***************************************************************************/
  FUNCTION VERIFICA_INGRESO(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cDni_in         IN CHAR,
                            dDiaTrabajo_in  IN DATE)
  RETURN CHAR
  IS
   v_cVal CHAR(1);
  BEGIN
      --LP 05/09/2015
      SELECT DECODE(COUNT(1),0,'N','S')
        INTO v_cVal
      FROM PBL_INGRESO_PERSONAL I
      WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
      AND   I.COD_LOCAL     = cCodLocal_in
      AND   I.DNI   = cDni_in
      AND   I.FECHA = dDiaTrabajo_in
      AND   I.ENTRADA IS NOT NULL
      AND   I.SALIDA  IS NULL;
    RETURN v_cVal;
  END;

  /* SE VERIFICA LA EXISTENCIA DEL REGISTRO DEL TRABAJADOR EN EL DIA********************************************************************** */
  --23/11/2007  DUBILLUZ  MODIFICACION
   FUNCTION TRA_EXIST_REGISTRO(cCodGrupoCia_in IN CHAR,
                                cDni_in        IN CHAR)
  RETURN FarmaCursor
  IS
    CURADM FarmaCursor;
  BEGIN

    OPEN CURADM FOR

       /*SELECT COD_TRAB || 'Ã' ||
              ENTRADA || 'Ã' ||
              SALIDA
       FROM PBL_INGRESO_PERSONAL
       WHERE COD_GRUPO_CIA=cCodGrupoCia_in
       AND DNI=cDni_in AND FECHA=TRUNC(SYSDATE) AND
       ENTRADA IS NOT NULL AND SALIDA IS NOT NULL;*/
        SELECT COD_TRAB || 'Ã' ||
               ENTRADA || 'Ã' ||
               SALIDA
        FROM   PBL_INGRESO_PERSONAL
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    DNI           = cDni_in
        AND    FECHA         =  TRUNC(SYSDATE)
        AND    SALIDA_2  IS NOT NULL;

      RETURN CURADM ;
  END;

   /* VALIDA LA SALIDA ********************************************************************** */
   FUNCTION TRA_VALIDA_SALIDA(cCodGrupoCia_in IN CHAR,
                                cDni_in       IN CHAR)
  RETURN FarmaCursor
  IS
    CURADM FarmaCursor;
  BEGIN

    OPEN CURADM FOR

       SELECT COD_TRAB || 'Ã' ||
              ENTRADA  || 'Ã' ||
              SALIDA
       FROM   PBL_INGRESO_PERSONAL
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    DNI   = cDni_in
       AND    FECHA = TRUNC(SYSDATE)
       AND    ENTRADA IS NOT NULL
       AND    SALIDA IS NULL;

      RETURN CURADM ;
  END;

/*********************************************************************** */
  PROCEDURE GRABA_REG_PERSONAL_2(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cDni_in         IN CHAR,
                                 cTipo_in        IN CHAR,
                                 d_dDiaTrabajo   IN DATE,
                                 cIndicador        IN CHAR)
  AS
    v_dHoraEntrada   DATE;
    v_dHoraEntrada_2 DATE;
  BEGIN

       SELECT I.ENTRADA INTO v_dHoraEntrada
       FROM PBL_INGRESO_PERSONAL I
       WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
         AND I.COD_LOCAL = cCodLocal_in
         AND I.DNI = cDni_in
         AND I.FECHA = d_dDiaTrabajo;

       SELECT I.ENTRADA_2 INTO v_dHoraEntrada_2
       FROM PBL_INGRESO_PERSONAL I
       WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
         AND I.COD_LOCAL = cCodLocal_in
         AND I.DNI = cDni_in
         AND I.FECHA = d_dDiaTrabajo;

      IF cTipo_in = PTOVENTA_CONTROL_ASISTENCIA.g_IND_ENTRADA THEN
         if (v_dHoraEntrada IS NOT NULL AND v_dHoraEntrada_2 IS NOT NULL) then

         RAISE_APPLICATION_ERROR(-20002,'Usted ya registró su salida el día de hoy.');

         end if;
         UPDATE PBL_INGRESO_PERSONAL R
         SET    R.ENTRADA_2 = SYSDATE,
                R.USU_MOD = cDni_in,
                R.FEC_MOD = SYSDATE,
                R.IND_ENTRADA_2=cIndicador--CHUANES 03.03.2015
         WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    R.COD_LOCAL = cCodLocal_in
         AND    R.DNI = cDni_in
         AND    R.FECHA = d_dDiaTrabajo
         AND    R.ENTRADA_2 IS NULL;

      ELSE
         UPDATE PBL_INGRESO_PERSONAL R
         SET    R.SALIDA_2 = SYSDATE,
                R.USU_MOD  = cDni_in,
                R.FEC_MOD  = SYSDATE,
                R.IND_SALIDA_2=cIndicador--CHUANES 03.03.2015
         WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    R.COD_LOCAL     = cCodLocal_in
         AND    R.DNI   = cDni_in
         AND    R.FECHA = d_dDiaTrabajo;
      END IF;
  END;


  /************************************************************************************/
   FUNCTION LISTA_HISTORICO_TEMP(cCodGrupoCia IN CHAR,
                                vCodLocal_in  IN CHAR)
   RETURN FarmaCursor
   IS
     mifcur FarmaCursor;
   BEGIN
     OPEN mifcur FOR
    SELECT X.SEC_TEMP_LOC|| 'Ã' ||
           TO_CHAR(X.FEC_CREA_TEMP,'DD/MM/YYYY HH24:MI:SS')|| 'Ã' ||
           X.USU_CREA_TEMP|| 'Ã' ||
           TO_CHAR(X.TEMP_AREA_VTA,'999999.99')|| 'Ã' ||
           TO_CHAR(X.TEMP_ALMACEN,'999999.99')|| 'Ã' ||
           TO_CHAR(X.TEMP_REFRIG,'999999.99')
    FROM LGT_HIST_TEMP_LOC X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia
    AND X.COD_LOCAL=vCodLocal_in
    AND TRUNC(X.FEC_CREA_TEMP) BETWEEN TRUNC(SYSDATE-1) AND TRUNC(SYSDATE);
      RETURN mifcur;
    END;

    /*********************************************************************************/
    PROCEDURE IND_INGRESA_TEMP(cCodGrupoCia  IN CHAR,
                                cCodLocal     IN CHAR,
                                cUsuCrea         IN CHAR,
                                cSecUsu          IN CHAR,
                                nValVta          IN NUMBER,
                                nValAlmacen      IN NUMBER,
                                nValRefrig       IN NUMBER)
    IS

    v_Sec NUMBER;
    nCant1 NUMBER:=0;
    nCant2 NUMBER:=0;
    --cValor CHAR(1);
    cantAux NUMBER:=0;
    ROL  CHAR(3):='011';
    HoraInicio   VARCHAR2(2);
    HoraFin      VARCHAR2(2);
    dire         VARCHAR2(30);
    zona    VTA_LOCAL_X_ZONA.COD_ZONA_VTA%TYPE;
    emailzona    VTA_ZONA_VTA.EMAIL_JEFE_ZONA%TYPE;
    mesg_body VARCHAR2(4000);

    BEGIN

    SELECT TRIM(A.DESC_CORTA),TRIM(A.DESC_LARGA) INTO HoraInicio,HoraFin
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL=267;
    --lp 05/09/2015
    SELECT COUNT(1) INTO nCant1
    FROM PBL_ROL_USU X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia
    AND X.COD_LOCAL=cCodLocal
    AND X.SEC_USU_LOCAL=cSecUsu
    AND X.COD_ROL=ROL;
    --lp 05/09/2015
    SELECT COUNT(1) INTO nCant2
    FROM LGT_HIST_TEMP_LOC Y
    WHERE Y.COD_GRUPO_CIA=cCodGrupoCia
    AND Y.COD_LOCAL=cCodLocal
    AND Y.SEC_USU_LOCAL=cSecUsu
    AND Y.FEC_CREA_TEMP BETWEEN TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD')||HoraInicio,'YYYYMMDDHH24')
                        AND TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD')||HoraFin,'YYYYMMDDHH24');

    IF(nCant1>0)THEN
     /*IF(SYSDATE>=TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD')||HoraInicio,'YYYYMMDDHH24')
                 AND SYSDATE<=TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD')||HoraFin,'YYYYMMDDHH24'))THEN*/

       IF(nCant2<=2)THEN --max 2 por dia
         v_Sec:=PROM_NUEVO_SECUENCIAL(cCodGrupoCia,cCodLocal);

         /*SELECT TRIM(A.COD_ZONA_VTA) INTO zona
         FROM VTA_LOCAL_X_ZONA A
         WHERE A.COD_GRUPO_CIA=cCodGrupoCia
         AND A.COD_LOCAL=cCodLocal;

         SELECT TRIM(B.EMAIL_JEFE_ZONA) INTO emailzona
         FROM VTA_ZONA_VTA B
         WHERE B.COD_GRUPO_CIA
         AND B.COD_ZONA_VTA=zona;

         emailzona:='AESCATE';*/

             SELECT TRIM(A.LLAVE_TAB_GRAL)  INTO dire
			FROM PBL_TAB_GRAL A
			WHERE A.ID_TAB_GRAL=265;

        IF(SYSDATE<TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD')||HoraInicio,'YYYYMMDDHH24')
                 OR SYSDATE>TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD')||HoraFin,'YYYYMMDDHH24'))THEN

          --     DBMS_OUTPUT.put_line('ENTRO XXX');
          mesg_body := 'SE INGRESO TEMPERATURA FUERA DEL RANGO DE HORAS PERMITIDO. HORA INGRESO '|| TO_CHAR(SYSDATE,'DD/MM/YYYY HH24:MI:SS');
          FARMA_UTILITY.envia_correo(cCodGrupoCia,cCodLocal,
                                         dire,--||emailzona,
                                         'ALERTA EL INGRESAR TEMPERATURAS ',
                                         'ALERTA',
                                         mesg_body,
                                         '');
        END IF;

         IF(nValVta>25) THEN
         mesg_body :='SE SUPERO LOS 25 GRADOOS AREA VENTAS , VALOR INGRESADO : '||nValVta;

        /*  FARMA_UTILITY.envia_correo(cCodGrupoCia,cCodLocal,
                                         'JCORTEZ;'||emailzona,
                                         'ALERTA EL INGRESAR TEMPERATURA ',
                                         'VERIFIQUE',
                                         mesg_body,
                                         '');         */
         END IF;

        IF(nValAlmacen>25) THEN
         mesg_body := mesg_body||'<BR>'||' SE SUPERO LOS 25 GRADOS AREA ALMACEN, VALOR INGRESADO : '||nValAlmacen;
        /* FARMA_UTILITY.envia_correo(cCodGrupoCia,cCodLocal,
                                         'JCORTEZ;'||emailzona,
                                         'ALERTA EL INGRESAR TEMPERATURA AREA ALMACEN ',
                                         'VERIFIQUE',
                                         mesg_body,
                                         '');*/
         END IF;

         IF(nValRefrig>8) THEN
         mesg_body := mesg_body||'<BR>'||' SE SUPERO LOS 8 GRADOS REFRIGERADOR, VALOR INGRESADO : '||nValRefrig;
         /* FARMA_UTILITY.envia_correo(cCodGrupoCia,cCodLocal,
                                         'JCORTEZ;'||emailzona,
                                         'ALERTA EL INGRESAR TEMPERATURA REFRIGERADOR ',
                                         'VERIFIQUE',
                                         mesg_body,
                                         '');*/
         END IF;

          IF(nValVta>25 OR nValAlmacen>25 OR  nValRefrig>8) THEN
                    FARMA_UTILITY.envia_correo(cCodGrupoCia,cCodLocal,
                                         dire,--||emailzona,
                                         'ALERTA EL INGRESAR TEMPERATURAS ',
                                         'ALERTA',
                                         mesg_body,
                                         '');
          END IF;


            INSERT INTO LGT_HIST_TEMP_LOC (COD_GRUPO_CIA,COD_LOCAL,SEC_TEMP_LOC,TEMP_AREA_VTA,TEMP_ALMACEN,TEMP_REFRIG,
            USU_CREA_TEMP,FEC_MOD_TEMP,USU_MOD_TEMP,SEC_USU_LOCAL,FEC_CREA_TEMP)
            VALUES( cCodGrupoCia,cCodLocal,v_Sec,nValVta,nValAlmacen,nValRefrig,cUsuCrea,NULL,NULL,cSecUsu,
            CASE WHEN TO_CHAR(SYSDATE,'HH24MISS') BETWEEN '000000' AND '015959'
             THEN TO_DATE(TO_CHAR(SYSDATE-1,'DD/MM/YYYY')||' 23:59:59','DD/MM/YYYY HH24:MI:SS')
              ELSE SYSDATE END);
       ELSE
            RAISE_APPLICATION_ERROR(-20002, 'Usted ya registro 2 ingresos para el dia de hoy.');
       END IF;
     /*ELSE
      RAISE_APPLICATION_ERROR(-20003, 'Usted no esta dentro del rango de horas permitido.');
     END IF;*/
    ELSE
          RAISE_APPLICATION_ERROR(-20001, 'Usted no cuenta con el rol adecuado.');
    END IF;

    END;

    /**********************************************************************************************/
      FUNCTION PROM_NUEVO_SECUENCIAL(cCodGrupoCia  IN CHAR,
      	 					                   cCodLocal IN CHAR)
      RETURN NUMBER
      IS
      v_secGenerado NUMBER;
      BEGIN

       	  SELECT NVL(TO_NUMBER(MAX(SEC_TEMP_LOC))+1,1)
      	  INTO   v_secGenerado
      	  FROM   LGT_HIST_TEMP_LOC
      	  WHERE  COD_GRUPO_CIA = cCodGrupoCia
          AND COD_LOCAL = cCodLocal;

      RETURN v_secGenerado;
      END;

  /***************************************************************************/
   FUNCTION VERIFICA_ROL_USU(cCodGrupoCia_in  IN CHAR,
                             cCodLocal_in     IN CHAR,
                             vSecUsu_in       IN CHAR,
                             cCodRol_in       IN CHAR)
    RETURN CHAR
    IS
    v_resultado  CHAR(1);
    vcontador   NUMBER;
    BEGIN

    BEGIN
      --lp 05/09/2015
    SELECT COUNT(1) INTO vcontador
    FROM  PBL_ROL_USU X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.COD_LOCAL=cCodLocal_in
    AND X.SEC_USU_LOCAL=vSecUsu_in
    AND X.COD_ROL=cCodRol_in;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
           vcontador :=0;
    END;

    IF vcontador > 0 THEN
      v_resultado := 'S';
    ELSE
      v_resultado := 'N';
    END IF;
    RETURN v_resultado;

    END;


     /***************************************************************************/
  FUNCTION VERIFICA_INGR_TEMP_USU(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR)
    RETURN CHAR
    IS
    v_resultado  CHAR(1);
    vcontador   NUMBER;
    vcontador2   NUMBER;
    HoraInicio   VARCHAR2(2);
    HoraFin      VARCHAR2(2);
    aux3  VARCHAR2(20);

    ind CHAR(1);
    BEGIN

    SELECT TRIM(A.DESC_CORTA),TRIM(A.DESC_LARGA) INTO HoraInicio,HoraFin
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL=267;

    --lp 05/09/2015
    SELECT COUNT(1) INTO vcontador
    FROM LGT_HIST_TEMP_LOC Y
    WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
    AND Y.COD_LOCAL=cCodLocal_in
    -- AND Y.SEC_USU_LOCAL=vSecUsu_in
    AND TRUNC(Y.FEC_CREA_TEMP)=TRUNC(SYSDATE);

    DBMS_OUTPUT.put_line('vcontador: '||vcontador);

        DBMS_OUTPUT.put_line(TO_CHAR(SYSDATE,'YYYYMMDD')||HoraInicio);

    SELECT CASE WHEN TO_CHAR(SYSDATE,'HH24MISS') BETWEEN '000000' AND '015959' THEN 'S' ELSE 'N' END INTO ind FROM DUAL;
   --lp 05/09/2015
   SELECT COUNT(1) INTO vcontador2
    FROM LGT_HIST_TEMP_LOC Y
    WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
    AND Y.COD_LOCAL=cCodLocal_in
    AND TRUNC(Y.FEC_CREA_TEMP)=TRUNC(SYSDATE-1);

    DBMS_OUTPUT.put_line('ind: '||ind);
        DBMS_OUTPUT.put_line('vcontador2: '||vcontador2);

    IF vcontador > 0 THEN
      v_resultado := 'S';
    ELSIF ind='S' AND vcontador2>0 THEN --si el dia anterior no marco y sale pasada las 12 en ragno de 1 a 2 am
       v_resultado := 'S';
    ELSE
      v_resultado := 'N';
    END IF;



    RETURN v_resultado;

    END;
      /***************************************************************************/
   FUNCTION GET_SEC_USU_X_DNI(cCodGrupoCia_in  IN CHAR,
                              cCodLocal_in     IN CHAR,
                              cDni_in          IN CHAR)
    RETURN CHAR
    IS
    SecUsuLocal CHAR(3);
		v_cCodCia PBL_LOCAL.COD_CIA%TYPE;
    BEGIN

    BEGIN

	--ERIOS 04.04.2014 Trabajador por Cia
	SELECT COD_CIA INTO v_cCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;

    SELECT Y.SEC_USU_LOCAL INTO SecUsuLocal
    FROM PBL_USU_LOCAL Y
    WHERE   Y.COD_GRUPO_CIA=cCodGrupoCia_in
    AND Y.COD_LOCAL=cCodLocal_in
    AND Y.COD_TRAB IN (SELECT NVL(C.COD_TRAB,'00000')
                       FROM CE_MAE_TRAB C
-- 2014-09-28 JOLIVA: SE PERMITE MARCAR ASISTENCIA EN CUALQUIER LOCAL DE LA CADENA
--						WHERE  C.COD_CIA = v_cCodCia
						WHERE 1 = 1 -- C.COD_CIA = v_cCodCia
						--AND C.COD_TRAB
						AND C.EST_TRAB = 'A'
						AND C.NUM_DOC_IDEN = cDni_in);
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
           SecUsuLocal :='';
    END;

    RETURN SecUsuLocal;

    END;

    /*************************************************************************************/
    FUNCTION LISTA_HIST_FILTRO(cCodGrupoCia IN CHAR,
                                    vCodLocal_in IN CHAR,
                                    cFecIni_in  IN CHAR,
                                    cFecFin_in  IN CHAR)
    RETURN FarmaCursor
   IS
     mifcur FarmaCursor;
   BEGIN
     OPEN mifcur FOR
    SELECT X.SEC_TEMP_LOC|| 'Ã' ||
           TO_CHAR(X.FEC_CREA_TEMP,'DD/MM/YYYY HH24:MI:SS')|| 'Ã' ||
           X.USU_CREA_TEMP|| 'Ã' ||
           TO_CHAR(X.TEMP_AREA_VTA,'999999.99')|| 'Ã' ||
           TO_CHAR(X.TEMP_ALMACEN,'999999.99')|| 'Ã' ||
           TO_CHAR(X.TEMP_REFRIG,'999999.99')
    FROM LGT_HIST_TEMP_LOC X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia
    AND X.COD_LOCAL=vCodLocal_in
    AND  X.FEC_CREA_TEMP BETWEEN TO_DATE(cFecIni_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                          AND TO_DATE(cFecFin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');
      RETURN mifcur;
      END;

/**************************************************************************************************/


            --Descripcion: Se valida rol del trabajador de local
  --Fecha       Usuario		Comentario
  --25/02/2009  ASOLIS   Creacion
  FUNCTION VERIFICA_ROL_TRAB_LOCAL(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                    vSecUsu_in      IN CHAR,
                                   cCodRol_inCaj    IN CHAR,
                                    cCodRol_inVend  IN CHAR,
                                    cCodRol_inAdmL  IN CHAR)
  RETURN CHAR

    IS
    v_resultado  CHAR(1);
    vcontador   NUMBER;
    BEGIN
      --lp 05/09/2015
    SELECT COUNT(1) INTO vcontador
    FROM  PBL_ROL_USU X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.COD_LOCAL=cCodLocal_in
    AND X.SEC_USU_LOCAL=vSecUsu_in
    AND (X.COD_ROL=cCodRol_inCaj OR X.COD_ROL=cCodRol_inVend OR X.COD_ROL=cCodRol_inAdmL);
   /* EXCEPTION
      WHEN NO_DATA_FOUND THEN
           vcontador :=0;*/

     BEGIN

       IF vcontador > 0 THEN
         v_resultado := 'S';
       ELSE
        v_resultado := 'N';
        END IF;

     END;


    RETURN v_resultado;

    END;
    
--*************************************************************************************
    FUNCTION INGR_F_IND_MARCACION
    RETURN CHAR    
    IS
    v_resultado  CHAR(1);    
    BEGIN
      SELECT TAB.LLAVE_TAB_GRAL 
      INTO v_resultado  
      FROM PBL_TAB_GRAL TAB 
      WHERE TAB.ID_TAB_GRAL='662'
      AND TAB.COD_TAB_GRAL='ID_MARCA_HORA';
      RETURN TRIM(v_resultado);
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
      v_resultado :='N';
      RETURN TRIM(v_resultado);
    END;
--*************************************************************************************
  FUNCTION INGR_F_GET_LISTA_REG_DNI(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cDni_in IN CHAR)
  RETURN FarmaCursor
  IS
    v_curLista FarmaCursor;
  BEGIN
    OPEN v_curLista FOR
      SELECT P.DNI|| 'Ã' ||
             NOM_TRAB||' '||APE_PAT_TRAB||' '||APE_MAT_TRAB || 'Ã' ||
             NVL(TO_CHAR(P.ENTRADA,'HH24:MI:SS'),' ')|| 'Ã' ||
             NVL(TO_CHAR(P.SALIDA,'HH24:MI:SS'),' ')|| 'Ã' ||
             NVL(TO_CHAR(P.ENTRADA_2,'HH24:MI:SS'),' ')|| 'Ã' ||
             NVL(TO_CHAR(P.SALIDA_2,'HH24:MI:SS'),' ')|| 'Ã' ||
             TO_CHAR(P.FEC_CREA,'yyyyMMddHH24MISS')
      FROM   PBL_INGRESO_PERSONAL P,CE_MAE_TRAB C
      WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    P.COD_LOCAL = cCodLocal_in
      AND    P.FECHA     = TRUNC(SYSDATE)
      AND    P.COD_CIA   = C.COD_CIA(+)
      AND    P.COD_TRAB  = C.COD_TRAB(+)
      AND    P.DNI=cDni_in
	  AND C.EST_TRAB = 'A'
    UNION
    SELECT  P.DNI || 'Ã' ||
            NOM_TRAB||' '||APE_PAT_TRAB||' '||APE_MAT_TRAB  || 'Ã' ||
            NVL(TO_CHAR(P.ENTRADA,'HH24:MI:SS  dd/MM'),' ') || 'Ã' ||
            NVL(TO_CHAR(P.SALIDA,'HH24:MI:SS   dd/MM'),' ') || 'Ã' ||
            NVL(TO_CHAR(P.ENTRADA_2,'HH24:MI:SS  dd/MM'),' ')|| 'Ã' ||
            NVL(TO_CHAR(P.SALIDA_2,'HH24:MI:SS   dd/MM'),' ')|| 'Ã' ||
            TO_CHAR(P.FEC_CREA,'yyyyMMddHH24MISS')
            FROM PBL_INGRESO_PERSONAL P, CE_MAE_TRAB C
            WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND   P.COD_LOCAL = cCodLocal_in
            AND   P.COD_CIA   = C.COD_CIA(+)
            AND   P.COD_TRAB  = C.COD_TRAB(+)
			AND C.EST_TRAB = 'A'
            AND   P.FECHA     = TRUNC(SYSDATE-1)
             AND    P.DNI=cDni_in
            AND P.DNI NOT IN (SELECT P.DNI
                              FROM   PBL_INGRESO_PERSONAL P
                              WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND    P.COD_LOCAL = cCodLocal_in
                              AND    P.FECHA     = TRUNC(SYSDATE));
    RETURN v_curLista;
  END; 
--*************************************************************************************  
  FUNCTION INGR_F_ADMINISTRADOR_LOCAL(cCodGrupoCia_in IN CHAR,
                                      cCodCia_in      IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cDni_in IN CHAR)
  RETURN CHAR
    IS
    v_CodRol CHAR(4);
    v_FLAG  CHAR(1):='N';
   
    BEGIN
    
    SELECT  ROL.COD_ROL 
    INTO v_CodRol 
    FROM PBL_USU_LOCAL  USU ,PBL_ROL_USU ROL
    WHERE USU.COD_GRUPO_CIA=ROL.COD_GRUPO_CIA
    AND USU.COD_LOCAL=ROL.COD_LOCAL
    AND USU.SEC_USU_LOCAL=ROL.SEC_USU_LOCAL
    AND USU.COD_GRUPO_CIA=cCodGrupoCia_in
    AND USU.COD_CIA=cCodCia_in
    AND USU.COD_LOCAL=cCodLocal_in
    AND USU.DNI_USU=cDni_in
    AND ROL.COD_ROL='011';
 
    IF v_CodRol='011'   THEN
    v_FLAG:='S';
    END IF;
    RETURN v_FLAG;
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
     
    RETURN v_FLAG;

    END;
--*************************************************************************************  
    FUNCTION INGR_F_GET_PERSONAL(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cDni_in         IN CHAR)
    RETURN FarmaCursor
    IS
      v_CurLista FarmaCursor;
      v_dDiaTrabajo DATE;
      v_cTipo CHAR(2);
      v_Entrada DATE;
      v_Salida  DATE;
      v_Entrada_2  DATE;
      v_Salida_2 DATE;
  	  v_cCodCia PBL_LOCAL.COD_CIA%TYPE;
      v_Parametro INTEGER; 
      v_CantMarcaciones INTEGER;                                          
    BEGIN
    v_CantMarcaciones:=0;
     --OBTENGO LA ULTIMA FECHA REGISTRADA POR EL TRABAJADOR
         SELECT NVL(MAX(I.FECHA),TRUNC(SYSDATE-10)) INTO v_dDiaTrabajo
         FROM PBL_INGRESO_PERSONAL I
         WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
         AND   I.COD_LOCAL = cCodLocal_in
         AND   I.DNI       = cDni_in;
  --VERFICAMOS EXISTE ALGUNA MARCACIÓN DEL USAUARIO 
       SELECT COUNT(I.DNI) INTO v_CantMarcaciones
         FROM PBL_INGRESO_PERSONAL I
         WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
         AND   I.COD_LOCAL = cCodLocal_in
         AND   I.DNI       = cDni_in;
     IF v_CantMarcaciones>0  THEN    
       SELECT  I.ENTRADA,I.SALIDA,I.ENTRADA_2,I.SALIDA_2 
       INTO v_Entrada,v_Salida,v_Entrada_2,v_Salida_2 
       FROM PBL_INGRESO_PERSONAL I
       WHERE I.COD_GRUPO_CIA=cCodGrupoCia_in
       AND I.COD_LOCAL=cCodLocal_in
       AND I.DNI=cDni_in
       AND I.FECHA= v_dDiaTrabajo;
     END IF;
     
     SELECT  TAB.LLAVE_TAB_GRAL 
     INTO v_Parametro   
     FROM PBL_TAB_GRAL TAB 
     WHERE TAB.ID_TAB_GRAL=722;   
    
     IF ( v_dDiaTrabajo  IS NOT NULL AND v_CantMarcaciones>0) THEN       
        IF v_dDiaTrabajo= TRUNC(SYSDATE)  THEN     
           IF (v_Salida IS NULL AND  v_Entrada IS NOT NULL AND v_Salida_2 IS NULL) THEN
             v_cTipo:='02';
           ELSIF v_Salida IS NOT NULL AND v_Entrada_2 IS NULL  THEN
            v_cTipo:='01';
           ELSIF  v_Salida IS NOT NULL AND v_Entrada_2 IS NOT NULL AND v_Salida_2 IS NULL THEN 
            v_cTipo:='02';
           ELSIF v_Salida_2 IS NOT NULL THEN
            v_cTipo:='01';
           ELSE   
            v_cTipo:='01';
           END IF;
     
     
       ELSE  --SI ES AYER O ANTES DE AYER O UNA FECHA ANTIGUA
         IF ( v_Entrada IS NOT NULL AND v_Salida IS NULL  AND v_Entrada_2  IS NULL  AND v_Salida_2 IS NULL )THEN--POSIBLE AMANECIDA
            IF  (SYSDATE - v_Entrada)*24>v_Parametro  THEN-- YA PASARON MAS DE LAS  8 AM DEL DIA SIGUIENTE
                v_cTipo:='01';--ENTRADA(SON MAS DE LAS  8 AM ENTONCES SE LE SUGIERE MARCAR ENTRADA)
            ELSE
                v_cTipo:='02';--SALIDA(SON ANTES DE LAS  8 AM SE LE SUGIERE MARCAR SALIDA  )
            END IF;
         ELSIF ( v_Entrada_2 IS NOT NULL AND v_Salida_2 IS NULL) THEN --POSIBLE AMANECIDA
             IF (SYSDATE- v_Entrada_2)*24> v_Parametro  THEN -- YA PASARON MAS DE LAS  8 AM DEL DIA SIGUIENTE
               v_cTipo:='01';--ENTRADA(SON MAS DE LAS  8 AM ENTONCES SE LE SUGIERE MARCAR ENTRADA)
             ELSE
               v_cTipo:='02';--SALIDA(SON ANTES DE LAS  8 AM SE LE SUGIERE MARCAR SALIDA  )
             END IF;
         ELSE --CUANDO NO ES AMANECIDA
          v_cTipo:='01';--SE LE SUGIERE MARCAR ENTRADA AL DIA SIGUIENTE
         END IF;
       END IF;
     ELSE-- SI NUNCA REALIZO UNA MARCACIÓN O MARCA POR PRIMERA VEZ
       v_cTipo:='01';--ENTRADA
     END IF; 

    --ERIOS 04.04.2014 Trabajador por Cia
    SELECT COD_CIA 
    INTO v_cCodCia 
    FROM PBL_LOCAL 
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in 
    AND COD_LOCAL = cCodLocal_in;

    OPEN v_CurLista FOR
    SELECT NOM_TRAB||' '||APE_PAT_TRAB||' '||APE_MAT_TRAB ||'Ã'||
           C.COD_CIA ||'Ã'||
           C.COD_TRAB ||'Ã'||
           v_cTipo ||'Ã'||
           nvl(C.IND_FISCALIZADO,' ') ||'Ã'||
           -- KMONCADA 07.12.2015 SE AGREGA SEC USUARIO
           NVL((
               SELECT USU.SEC_USU_LOCAL
               FROM PTOVENTA.PBL_USU_LOCAL USU
               WHERE USU.COD_GRUPO_CIA = cCodGrupoCia_in
               AND USU.COD_LOCAL = cCodLocal_in
               AND USU.DNI_USU = C.NUM_DOC_IDEN
           ),'0') SEC_USU
    FROM   CE_MAE_TRAB C
-- 2014-09-28 JOLIVA: SE PERMITE MARCAR ASISTENCIA EN CUALQUIER LOCAL DE LA CADENA
--    WHERE  C.COD_CIA = v_cCodCia
    WHERE 1= 1 -- C.COD_CIA = v_cCodCia
	--AND C.COD_TRAB
	AND C.EST_TRAB = 'A'
	AND C.NUM_DOC_IDEN = cDni_in;

    RETURN v_CurLista;
  END;
--*************************************************************************************  
  FUNCTION  INGR_F_MSG_BIENVENIDA(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in       IN CHAR,
                                  cDni_in    IN CHAR)
  RETURN VARCHAR2
  IS
  v_FORMAMARCACION VARCHAR2(50); 
  v_MSGFINAL VARCHAR2(500);
  
  BEGIN

    SELECT  t.desc_corta 
    into v_FORMAMARCACION  
    FROM PBL_TAB_GRAL t 
    WHERE t.ID_TAB_GRAL=733;
  
  SELECT  'Sr:(a) '||
          U.APE_PAT_TRAB ||' '||
          U.Ape_Mat_Trab || 
          INGR_F_MSG_SALUDO ||
          ', gracias por ingresar su ' ||
          v_FORMAMARCACION 
  INTO v_MSGFINAL 
  FROM CE_MAE_TRAB U
  WHERE U.Num_Doc_Iden = cDni_in
  AND U.EST_TRAB = 'A';
  
  RETURN v_MSGFINAL;
  END;
--*************************************************************************************  
  FUNCTION  INGR_F_MSG_SALUDO
  RETURN VARCHAR2  
  IS
  v_Saludos VARCHAR2(50);  
  BEGIN
  
    IF 5>TO_NUMBER(TO_CHAR(SYSDATE,'HH24')) and TO_NUMBER(TO_CHAR(SYSDATE,'HH24'))<12 THEN
      v_Saludos:=' buenos dias';
    ELSIF  TO_NUMBER(TO_CHAR(SYSDATE,'HH24'))>=12 and TO_NUMBER(TO_CHAR(SYSDATE,'HH24')) <18 THEN
      v_Saludos:=' buenas tardes';
    ELSIF TO_NUMBER(TO_CHAR(SYSDATE,'HH24'))>=18 and TO_NUMBER(TO_CHAR(SYSDATE,'HH24'))<24 THEN
      v_Saludos:=' buenas noches';
    ELSE
      v_Saludos:=' buenos dias';
    END IF;
    RETURN v_Saludos;  
  END;
--*************************************************************************************  
  FUNCTION INGR_F_GET_IND_DNI_ING
  RETURN CHAR
  IS
  
    v_IND CHAR(1) := 'N';
    v_IND_DNI CHAR(1);
  
  BEGIN
    
    SELECT T.DESC_CORTA 
    INTO v_IND_DNI  
    FROM PBL_TAB_GRAL T 
    WHERE T.ID_TAB_GRAL = 735;--S activo dni
    
    IF v_IND_DNI = 'S' THEN
       v_IND := 'S';
    END IF;
    
    RETURN v_IND;
  END;
  
--*************************************************************************************  
  FUNCTION INGR_F_GET_IND_HUELLA
  RETURN CHAR
  IS
  v_IND CHAR(1) := 'N';
  v_IND_HUELLA CHAR(1);
  BEGIN
    
    SELECT  T.DESC_CORTA 
    INTO v_IND_HUELLA  
    FROM PBL_TAB_GRAL T 
    WHERE T.ID_TAB_GRAL=736;-- S activo huella digital
    
    IF v_IND_HUELLA='S'  THEN
       v_IND := 'S';
    END IF;
    RETURN v_IND;
  END;
    
--*************************************************************************************  
  FUNCTION INGR_F_GET_HORA_SIST
  RETURN CHAR
  IS
  v_HoraSistema CHAR(24);
  BEGIN
    SELECT  TO_CHAR(SYSDATE,'dd/mm/yyyy HH24:MI:SS') 
    INTO v_HoraSistema 
    FROM DUAL;
    RETURN v_HoraSistema;  
  END;
--*************************************************************************************  
  FUNCTION INGR_F_MARCAR_ENTRADA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR, 
                                 cSecUsuLocal_in IN CHAR)
  RETURN CHAR
  IS
  v_INDICADOR CHAR(1):='N';
  v_ENTRADA DATE;
  v_ENTRADA2 DATE;
  v_SALIDA DATE;
  v_SALIDA2 DATE;
  v_dDiaTrabajo DATE;
  v_DNI CHAR(8);
  v_CantMarcaciones INTEGER;
  BEGIN
  --IBTENEMOS EL DNI
  SELECT  U.DNI_USU INTO v_DNI FROM  PBL_USU_LOCAL U WHERE U.COD_GRUPO_CIA=cCodGrupoCia_in
  AND U.COD_LOCAL=cCodLocal_in
  AND U.SEC_USU_LOCAL= cSecUsuLocal_in;
  
   --OBTENGO LA ULTIMA FECHA REGISTRADA POR EL TRABAJADOR
   SELECT NVL(MAX(I.FECHA),TRUNC(SYSDATE-10)) INTO v_dDiaTrabajo 
   FROM PBL_INGRESO_PERSONAL I
   WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
   AND   I.COD_LOCAL = cCodLocal_in
   AND   I.DNI       = v_DNI;
  
 --VEFICAMOS SI ES DIFERENTE DE NULLO
 IF v_DNI IS NOT NULL THEN
  SELECT  I.ENTRADA,I.SALIDA,I.ENTRADA_2,I.SALIDA_2 INTO
  v_ENTRADA ,
  v_SALIDA ,
  v_ENTRADA2,
  v_SALIDA2 
  FROM PBL_INGRESO_PERSONAL I
  WHERE I.COD_GRUPO_CIA=cCodGrupoCia_in
  AND I.COD_LOCAL=cCodLocal_in
  AND I.DNI=v_DNI
  and I.FECHA=TRUNC(v_dDiaTrabajo);
  --VERIFICAMOS LA CANTIDAD DE MARCACIONES DEL USUARIO
  SELECT COUNT(I.DNI) INTO v_CantMarcaciones
  FROM PBL_INGRESO_PERSONAL I
  WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
  AND   I.COD_LOCAL = cCodLocal_in
  AND   I.DNI       = v_DNI;
   IF( v_dDiaTrabajo  IS NOT NULL AND  v_CantMarcaciones>0) THEN
      IF  v_dDiaTrabajo  =TRUNC(SYSDATE) THEN
       IF  v_SALIDA IS NOT NULL AND v_ENTRADA2 IS NULL THEN
         v_INDICADOR:='S';
        END IF;
      ELSE
      --ES AYER
       v_INDICADOR:='S';
      END IF;
   ELSE
   --NUNCA MARCO SE LE OBLIGA A MARCAR ENTRADA
   v_INDICADOR:='S';
   END IF;
  END IF;       
  RETURN v_INDICADOR;
  
  END;
--*************************************************************************************  
  FUNCTION INGR_F_USU_LOGEADO(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR, 
                             cSecUsuLocal_in IN CHAR,
                             cDni_in IN CHAR)
  RETURN CHAR
  IS
  v_INDICADOR CHAR(1):='N';
  v_SecUsuLocal CHAR(4);
  BEGIN
   SELECT  P.SEC_USU_LOCAL 
   INTO v_SecUsuLocal 
   FROM PBL_USU_LOCAL P
   WHERE P.COD_GRUPO_CIA=cCodGrupoCia_in
   AND P.COD_LOCAL=cCodLocal_in
   AND P.DNI_USU=cDni_in;
     
    IF  cSecUsuLocal_in=v_SecUsuLocal THEN
    v_INDICADOR:='S';
   END IF;
  RETURN v_INDICADOR;
  END;
--*************************************************************************************  
  FUNCTION INGR_F_IND_ACT_MARCACION
  RETURN CHAR
  IS  
  v_INDICADOR CHAR(1):='N';
  BEGIN
    SELECT  TAB.DESC_CORTA 
    INTO v_INDICADOR  
    FROM PBL_TAB_GRAL TAB 
    WHERE TAB.ID_TAB_GRAL=737;
 
     RETURN v_INDICADOR;
  EXCEPTION
   WHEN OTHERS THEN
    DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
    RETURN v_INDICADOR;  
  END ;
--*************************************************************************************  
  FUNCTION INGR_F_GET_FEC_SALIDA_HOR(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cDni_in         IN CHAR)
  RETURN DATE
  IS  
  v_dDiaTrabajo DATE;
  v_SQL         VARCHAR2(5000);
  v_CodHorario  PBL_CAB_HORARIO.COD_HORARIO%TYPE;
  v_CodTurno    PBL_TURNO.COD_TURNO%TYPE;
  v_SecUsu      PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE;
  v_Hora        date;
  v_dsalida      date;
  BEGIN
     SELECT nvl(MAX(A.FECHA),trunc(sysdate))
      INTO v_dDiaTrabajo
      FROM PBL_INGRESO_PERSONAL A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.DNI = cDni_in;
        
    -- OBTENER SEC_USU_LOCAL
    SELECT SEC_USU_LOCAL 
    INTO v_SecUsu
    FROM PBL_USU_LOCAL
    WHERE DNI_USU=cDni_in;
    -- OBTENER CODIGO DE HORARIO
    SELECT COD_HORARIO 
    INTO v_CodHorario
    FROM PBL_CAB_HORARIO
    WHERE FEC_INICIO<=v_dDiaTrabajo
    AND FEC_FIN >=v_dDiaTrabajo;
    --OBTENER LA SALIDA
    SELECT A.SALIDA 
      INTO V_Dsalida
      FROM PBL_INGRESO_PERSONAL A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.DNI = cDni_in
       AND A.FECHA = v_dDiaTrabajo;
    -- OBTENER CODIGO DE TURNO *DINAMICO*
    begin
    V_SQL:='INSERT INTO TURNO_TMP ' ||
           'SELECT COD_DIA'||TO_CHAR(v_dDiaTrabajo-1,'D')||'
            FROM PBL_DET_HORARIO
            WHERE COD_HORARIO='''||v_CodHorario||'''
              AND SEC_USU_LOCAL='''||v_SecUsu||'''';

    EXECUTE IMMEDIATE V_SQL;
    
    IF V_Dsalida IS NULL THEN
        SELECT MIN(A.COD_TURNO)
        INTO v_CodTurno
        FROM PBL_TURNO A
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_TURNO IN (SELECT COD_TURNO
                           FROM TURNO_TMP);
    ELSE
        SELECT MAX(A.COD_TURNO)
        INTO v_CodTurno
        FROM PBL_TURNO A
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_TURNO IN (SELECT COD_TURNO
                           FROM TURNO_TMP);
    END IF;

    COMMIT;
    end;
    SELECT A.HORA_FIN
    INTO v_Hora
    FROM PBL_TURNO A
    WHERE COD_TURNO=v_CodTurno;    
    
    RETURN v_Hora; 
  EXCEPTION
   WHEN OTHERS THEN
    DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
    RETURN NULL;
  END ;  
--*************************************************************************************  
  FUNCTION INGR_F_GET_IND_MSJE(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cDni_in         IN CHAR)
  RETURN CHAR
  IS
  v_FecUltTrans   DATE;
  v_HoraPlaneada  DATE;
  v_vFechaPlaneada varchar2(50);
  v_dDiaTrabajo DATE;
  v_MinHoraPlaneada     NUMBER(6);
  v_MinUltTrans   NUMBER(6);
  v_IndMsje     CHAR(1);
  v_DifMin      NUMBER(6);
  v_DifMin2      NUMBER(6);
  v_Minutos     NUMBER(8);
  NOT_EXIST_HORARIO EXCEPTION;
  v_vMsg varchar2(500);
  v_vFechaSal varchar2(50);
  flag char(1) := 'N';
  BEGIN

    SELECT TO_NUMBER(LLAVE_TAB_GRAL)
    INTO v_Minutos
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL=567;

    SELECT nvl(MAX(A.FECHA), sysdate)
    INTO v_dDiaTrabajo
    FROM PBL_INGRESO_PERSONAL A
    WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
     AND A.COD_LOCAL = cCodLocal_in
     AND A.DNI = cDni_in;
           
    v_FecUltTrans := PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_FEC_SALIDA_SUG(cCodGrupoCia_in, cCodLocal_in, cDni_in, v_dDiaTrabajo);
    v_HoraPlaneada := INGR_F_GET_FEC_SALIDA_HOR(cCodGrupoCia_in, cCodLocal_in, cDni_in);
    
    IF TRIM(v_HoraPlaneada) IS NULL THEN
      RAISE NOT_EXIST_HORARIO;
    END IF;
    
      
    --HALLO LA FECHA PLANEADA SIN TENER EN CUENTA UN TURNO QUE TERMINA AL DIA SIGUIENTE
    v_vFechaPlaneada := to_char(v_dDiaTrabajo,'dd/MM/yyyy') || 
                        ' ' ||
                        to_char(v_HoraPlaneada,'HH24:MI');

    --HALLO LAS HORAS SIN TENER EN CUENTA UN TURNO QUE TERMINA AL DIA SIGUIENTE
    SELECT (SUBSTR(TO_CHAR(v_HoraPlaneada,'HH24:MI'), 0, 2)*60) + 
           SUBSTR(TO_CHAR(v_HoraPlaneada,'HH24:MI'), -2) 
    INTO v_MinHoraPlaneada
    FROM DUAL;
    
    SELECT (SUBSTR(TO_CHAR(v_FecUltTrans,'HH24:MI'),0,2)*60) +
           (SUBSTR(TO_CHAR(v_FecUltTrans,'HH24:MI'),-2))
    INTO v_MinUltTrans
    FROM DUAL;
    --HALLO LA DIFERENCIA DE HORAS SIN TENER EN CUENTA UN TURNO QUE TERMINA AL DIA SIGUIENTE
    v_DifMin := v_MinHoraPlaneada - v_MinUltTrans;
    --HALLO LA CANT DE HORAS SIN TENER EN CUENTA UN TURNO QUE TERMINA AL DIA SIGUIENTE
    v_DifMin2 := v_MinHoraPlaneada - v_MinUltTrans;
    --SI LA DIFERENCIA ES MENOR ES PORQUE TERMINA AL DIA SIGUIENTE Y HAY QUE SUMAR 1 DIA 
    --A LA HORA DE SALIDA PLANEADA PARA QUE SEA REAL PUESTO QUE LOS TURNO EN SI NO TIENEN 
    --DIA SINO HORA
    flag := PTOVENTA_INGR_PERS.INGR_F_GET_IND_DIA_SIG(cCodGrupoCia_in, cCodLocal_in, cDni_in);
    IF (flag = 'S') THEN
        v_vFechaPlaneada := to_char(v_dDiaTrabajo + 1,'dd/MM/yyyy') || 
                        ' ' ||
                        to_char(v_HoraPlaneada,'HH24:MI');
        --HALLO LA DIFERENCIA DE HORAS TENIENDO EN CUENTA UN TURNO QUE TERMINA AL DIA SIGUIENTE
        v_DifMin2 := to_number(PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VAR_VAL_HRS_TRAB(
                        TO_DATE('01/01/2015 ' || TO_CHAR(v_HoraPlaneada,'HH24:MI'),'DD/MM/YYYY HH24:MI'),
                        TO_DATE('01/01/2015 ' || TO_CHAR(v_FecUltTrans,'HH24:MI'),'DD/MM/YYYY HH24:MI'),
                                                                       0),
                                                                       '999990.00') * 60;
    END IF;
    if v_DifMin2 < 0 then
        v_DifMin2 := v_DifMin2 * -1;
    end if;
    --8:00 - 5:00 (MINUTOS 30MIN DE UMBRAL)
    IF (to_date(v_vFechaPlaneada,'dd/MM/yyyy HH24:MI:SS') >= v_FecUltTrans) AND 
       (v_DifMin2 <= v_Minutos) THEN --8:00 - 4:50 (LA ULTIMA TRANSACCION)
      
      v_IndMsje := '1';
      --fecha actual xq puede que no tenga ni movimientos de caja ni ventas
      v_vMsg := 'Se tomara la hora de su última transacción o fecha actual: ' || 
               to_char(v_FecUltTrans,'dd/MM/yyyy HH24:MI');
      v_vFechaSal := to_char(v_FecUltTrans,'dd/MM/yyyy HH24:MI');
      
    ELSIF (to_date(v_vFechaPlaneada,'dd/MM/yyyy HH24:MI:SS') < v_FecUltTrans) AND 
          (v_DifMin2 <= v_Minutos) THEN --8:00 - 5:10 (LO PLANEADO)
      
      v_IndMsje:='2';
      v_vMsg := 'Se tomara la hora planificada: ' ||
               v_vFechaPlaneada;
      v_vFechaSal := v_vFechaPlaneada;
      
    ELSIF (to_date(v_vFechaPlaneada,'dd/MM/yyyy HH24:MI:SS') >= v_FecUltTrans) AND 
          (v_DifMin2 > v_Minutos) THEN --8:00 - 4:20 (LA ULTIMA TRANSACCION)
      
      v_IndMsje:='3';
      v_vMsg := 'Se tomara la hora de su última transacción o fecha actual: ' || 
               to_char(v_FecUltTrans,'dd/MM/yyyy HH24:MI');
      v_vFechaSal := to_char(v_FecUltTrans,'dd/MM/yyyy HH24:MI');
      
    ELSIF (to_date(v_vFechaPlaneada,'dd/MM/yyyy HH24:MI:SS') < v_FecUltTrans) AND 
          (v_DifMin2 > v_Minutos) THEN --8:00 - 5:40 (LO PLANEADO)
      
      v_IndMsje:='4';
      v_vMsg := 'Se tomara la hora planificada: ' ||
               v_vFechaPlaneada;
      v_vFechaSal := v_vFechaPlaneada;
      
    ELSE 
      v_IndMsje:='0';
    END IF;
    RETURN v_IndMsje || '°°°' || v_vFechaSal || '°°°' || v_vMsg;
  EXCEPTION
    WHEN NOT_EXIST_HORARIO THEN
      v_IndMsje:='9';
      v_vFechaSal := to_char(v_dDiaTrabajo,'dd/MM/yyyy');
      v_vMsg := 'El colaborador no tiene un horario asignado. Verificar por favor.';
      RETURN v_IndMsje || '°°°' || v_vFechaSal || '°°°' || v_vMsg;
  END;

--*************************************************************************************   
  PROCEDURE INGR_P_REGULARIZAR_SALIDA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cDni_in         IN CHAR,
                                      vFecHrSalida_in IN varchar2)
  AS

  v_dDiaTrabajo    DATE;
  v_RegIngreso     PBL_INGRESO_PERSONAL%ROWTYPE;
  NOT_EXIST_HORARIO EXCEPTION;
  
  BEGIN
    
      SELECT NVL(MAX(I.FECHA),SYSDATE) 
      INTO v_dDiaTrabajo
      FROM PBL_INGRESO_PERSONAL I
      WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
      AND I.COD_LOCAL = cCodLocal_in
      AND I.DNI = cDni_in;
      
          SELECT I.* INTO v_RegIngreso
          FROM PBL_INGRESO_PERSONAL I
          WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
            AND I.COD_LOCAL = cCodLocal_in
            AND I.DNI = cDni_in
            AND I.FECHA = v_dDiaTrabajo;

           IF (v_RegIngreso.Salida_2 IS NULL AND 
              v_RegIngreso.Entrada_2 IS NOT NULL AND
              v_RegIngreso.Salida IS NOT NULL AND 
              v_RegIngreso.Entrada IS NOT NULL) THEN
              
                  UPDATE PBL_INGRESO_PERSONAL I
                  SET I.SALIDA_2 = TO_DATE(vFecHrSalida_in,'dd/MM/yyyy HH24:MI:SS'),
                      I.USU_MOD = cDni_in,
                      I.FEC_MOD = SYSDATE,
                      I.IND_SALIDA_2 = 'N'
                  WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND I.COD_LOCAL = cCodLocal_in
                        AND I.DNI = cDni_in
                        AND I.FECHA = v_dDiaTrabajo;
                        
           ELSIF (v_RegIngreso.Salida_2 IS NULL AND 
              v_RegIngreso.Entrada_2 IS NULL AND
              v_RegIngreso.Salida IS NULL AND 
              v_RegIngreso.Entrada IS NOT NULL)  THEN

                  UPDATE PBL_INGRESO_PERSONAL I
                  SET I.SALIDA =  TO_DATE(vFecHrSalida_in,'dd/MM/yyyy HH24:MI:SS'),
                      I.USU_MOD = cDni_in,
                      I.FEC_MOD = SYSDATE,
                      I.IND_SALIDA_1 = 'N'
                  WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND I.COD_LOCAL = cCodLocal_in
                        AND I.DNI = cDni_in
                        AND I.FECHA = v_dDiaTrabajo;
            END IF;
  END;
--*************************************************************************************     
  FUNCTION INGR_F_GET_IND_DIA_SIG(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cDni_in         IN CHAR)
  RETURN CHAR
  IS  
  v_dDiaTrabajo DATE;
  v_SQL         VARCHAR2(5000);
  v_CodHorario  PBL_CAB_HORARIO.COD_HORARIO%TYPE;
  v_CodTurno    PBL_TURNO.COD_TURNO%TYPE;
  v_SecUsu      PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE;
  v_HoraI        date;
  v_HoraF        date;
  v_dsalida      date;
  FLAG CHAR(1) := 'N';
  BEGIN
     SELECT nvl(MAX(A.FECHA),trunc(sysdate))
      INTO v_dDiaTrabajo
      FROM PBL_INGRESO_PERSONAL A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.DNI = cDni_in;
        
    -- OBTENER SEC_USU_LOCAL
    SELECT SEC_USU_LOCAL 
    INTO v_SecUsu
    FROM PBL_USU_LOCAL
    WHERE DNI_USU=cDni_in;
    -- OBTENER CODIGO DE HORARIO
    SELECT COD_HORARIO 
    INTO v_CodHorario
    FROM PBL_CAB_HORARIO
    WHERE FEC_INICIO<=v_dDiaTrabajo
    AND FEC_FIN >=v_dDiaTrabajo;
    --OBTENER LA SALIDA
    SELECT A.SALIDA 
      INTO V_Dsalida
      FROM PBL_INGRESO_PERSONAL A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.DNI = cDni_in
       AND A.FECHA = v_dDiaTrabajo;
    -- OBTENER CODIGO DE TURNO *DINAMICO*
    begin
    V_SQL:='INSERT INTO TURNO_TMP ' ||
           'SELECT COD_DIA'||TO_CHAR(v_dDiaTrabajo-1,'D')||'
            FROM PBL_DET_HORARIO
            WHERE COD_HORARIO='''||v_CodHorario||'''
              AND SEC_USU_LOCAL='''||v_SecUsu||'''';

    EXECUTE IMMEDIATE V_SQL;
    
    IF V_Dsalida IS NULL THEN
        SELECT MIN(A.COD_TURNO)
        INTO v_CodTurno
        FROM PBL_TURNO A
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_TURNO IN (SELECT COD_TURNO
                           FROM TURNO_TMP);
    ELSE
        SELECT MAX(A.COD_TURNO)
        INTO v_CodTurno
        FROM PBL_TURNO A
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_TURNO IN (SELECT COD_TURNO
                           FROM TURNO_TMP);
    END IF;

    COMMIT;
    end;
    SELECT A.HORA_INIC, A.HORA_FIN
    INTO v_HoraI, v_HoraF
    FROM PBL_TURNO A
    WHERE COD_TURNO=v_CodTurno;    
    
    if v_HoraF < v_HoraI then
         flag := 'S';
    end if;
    
    RETURN FLAG; 
  EXCEPTION
   WHEN OTHERS THEN
    DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
    RETURN NULL;
  END ;
--*************************************************************************************     
  END;
/
