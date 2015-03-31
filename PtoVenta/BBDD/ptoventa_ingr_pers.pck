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
  FUNCTION GET_PERSONAL(cCodGrupoCia_in IN CHAR,
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
  PROCEDURE GRABA_REG_PERSONAL(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cDni_in         IN CHAR,
                               cTipo_in        IN CHAR,
                               cCodcia_in      IN CHAR,
                               cCodTrab_in     IN CHAR,
                               cCodHorar_in    IN CHAR);

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
                                 d_dDiaTrabajo   IN DATE);

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
                         cCodLocal_in     IN CHAR,
                         vSecUsu_in       IN CHAR)
  RETURN CHAR;

  --Descripcion: Se obtiene sec por DNI
  --Fecha       Usuario		Comentario
  --11/02/2009  JCORTEZ   Creacion
  FUNCTION GET_SEC_USU_X_DNI(cCodGrupoCia_in  IN CHAR,
                            cCodLocal_in     IN CHAR,
                            cDni             IN CHAR)
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

end;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_INGR_PERS" is

  FUNCTION GET_PERSONAL(cCodGrupoCia_in IN CHAR,
                        cCodLocal_in    IN CHAR,
                        cDni_in         IN CHAR)
  RETURN FarmaCursor
  IS
    vCurLista FarmaCursor;
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

    OPEN vCurLista FOR
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

    RETURN vCurLista;
  END;

  /***************************************************************************/
  --13/11/2007  DUBILLUZ  MODIFICACION
  --26/11/2007  DUBILLUZ  MODIFICACION
  --05/12/2007  DUBILLUZ  MODIFICACION
  PROCEDURE GRABA_REG_PERSONAL(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cDni_in         IN CHAR,
                               cTipo_in        IN CHAR,
                               cCodcia_in      IN CHAR,
                               cCodTrab_in     IN CHAR,
                               cCodHorar_in    IN CHAR)
  AS
    v_dDiaTrabajo DATE;
    v_dHoraSalida DATE;

    v_IndFiscalizado CHAR(1) := 'S';
    --VALOR PARA REVISAR LA SALIDA 2
    --26/11/2007 DUBILLUZ MODIFICACION
    v_dHoraSalida_2  DATE;
    v_dHoraEntrada   DATE;
    v_dHoraEntrada_2 DATE;
	v_cCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
	--ERIOS 04.04.2014 Trabajador por Cia
	SELECT COD_CIA INTO v_cCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;

     --Se valida que el trabajador sea fiscalizado
     IF cCodTrab_in IS NOT NULL THEN
       --ERIOS 03.12.2014 Solucion temporal. Se debe definir los roles a fiscalizar.
       SELECT DISTINCT NVL(IND_FISCALIZADO,'S') INTO v_IndFiscalizado
       FROM CE_MAE_TRAB C
-- 2014-09-28 JOLIVA: SE PERMITE MARCAR ASISTENCIA EN CUALQUIER LOCAL DE LA CADENA
--		WHERE  C.COD_CIA = v_cCodCia
		WHERE  1 = 1 -- C.COD_CIA = v_cCodCia
		--AND C.COD_TRAB
		AND C.EST_TRAB = 'A'
		AND C.NUM_DOC_IDEN = cDni_in;
     END IF;

    IF v_IndFiscalizado = 'N' THEN
     RAISE_APPLICATION_ERROR(-20001,'Usted no puede registrarse, ya que no es un trabajador fiscalizado');
    END IF;

       IF cTipo_in = '01' THEN  -- Si se desea registrar ENTRADA
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
                (COD_GRUPO_CIA,COD_LOCAL,DNI,FECHA,COD_CIA, COD_TRAB,COD_HORARIO,USU_CREA, ENTRADA)
                VALUES
                (cCodGrupoCia_in,cCodLocal_in,cDni_in,TRUNC(SYSDATE),cCodcia_in, cCodTrab_in,cCodHorar_in,cDni_in,SYSDATE);
             ELSE -- Ya existe una ENTRADA para el día de hoy
                -- MENSAJE DE ERROR
                --RAISE_APPLICATION_ERROR(-20201,'Usted ya registró su Salida el día de hoy no puede volver a ingresar');
                GRABA_REG_PERSONAL_2(cCodGrupoCia_in,cCodLocal_in,
                                     cDni_in ,cTipo_in ,v_dDiaTrabajo);
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

             IF v_dDiaTrabajo < TRUNC(SYSDATE-1) THEN  -- Si la última fecha registrada fue ANTES DE AYER
                BEGIN
                  INSERT INTO PBL_INGRESO_PERSONAL
                  (COD_GRUPO_CIA,COD_LOCAL,DNI,FECHA,COD_CIA, COD_TRAB,COD_HORARIO,USU_CREA, SALIDA)
                  VALUES
                  (cCodGrupoCia_in,cCodLocal_in,cDni_in,TRUNC(SYSDATE),cCodcia_in, cCodTrab_in,cCodHorar_in,cDni_in,SYSDATE);
                END;
             ELSE -- Si la última fecha registrada fue AYER u HOY
                BEGIN
                     -- Se verifica si ya se grabó la SALIDA de esa día
                     SELECT I.SALIDA INTO v_dHoraSalida
                     FROM PBL_INGRESO_PERSONAL I
                     WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND I.COD_LOCAL = cCodLocal_in
                       AND I.DNI = cDni_in
                       AND I.FECHA = v_dDiaTrabajo;

                     --Verifica la salida 2
                     SELECT I.SALIDA_2 INTO v_dHoraSalida_2
                     FROM PBL_INGRESO_PERSONAL I
                     WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND I.COD_LOCAL = cCodLocal_in
                       AND I.DNI = cDni_in
                       AND I.FECHA = v_dDiaTrabajo;

                     SELECT I.ENTRADA INTO v_dHoraEntrada
                     FROM PBL_INGRESO_PERSONAL I
                     WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND I.COD_LOCAL = cCodLocal_in
                       AND I.DNI = cDni_in
                       AND I.FECHA = v_dDiaTrabajo;

                     SELECT I.ENTRADA_2 INTO v_dHoraEntrada_2
                     FROM PBL_INGRESO_PERSONAL I
                     WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND I.COD_LOCAL = cCodLocal_in
                       AND I.DNI = cDni_in
                       AND I.FECHA = v_dDiaTrabajo;


                     IF (v_dHoraSalida_2 IS NULL AND v_dHoraEntrada_2 IS NOT NULL ) THEN
                            UPDATE PBL_INGRESO_PERSONAL I
                            SET I.SALIDA_2 = SYSDATE,
                                I.USU_MOD = cDni_in,
                                I.FEC_MOD = SYSDATE
                            WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND I.COD_LOCAL = cCodLocal_in
                                  AND I.DNI = cDni_in
                                  AND I.FECHA = v_dDiaTrabajo;
                     ELSIF (v_dHoraSalida IS NULL AND v_dHoraEntrada IS NOT NULL
                            AND v_dHoraSalida_2 IS NULL)  THEN
                        BEGIN
                            UPDATE PBL_INGRESO_PERSONAL I
                            SET I.SALIDA = SYSDATE,
                                I.USU_MOD = cDni_in,
                                I.FEC_MOD = SYSDATE
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
                                                     cDni_in ,cTipo_in ,v_dDiaTrabajo);
                             ELSE
                                INSERT INTO PBL_INGRESO_PERSONAL
                                (COD_GRUPO_CIA,COD_LOCAL,DNI,FECHA,COD_CIA, COD_TRAB,COD_HORARIO,USU_CREA, SALIDA)
                                VALUES
                                (cCodGrupoCia_in,cCodLocal_in,cDni_in,TRUNC(SYSDATE),cCodcia_in, cCodTrab_in,cCodHorar_in,cDni_in,SYSDATE);
                             END IF;
                        END;
                     END IF;
                END;
             END IF;
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
    curLista FarmaCursor;
  BEGIN
    OPEN curLista FOR
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
    RETURN curLista;
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
      SELECT DECODE(COUNT(*),0,'N','S')
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
                                 d_dDiaTrabajo   IN DATE)
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

      IF cTipo_in = '01' THEN
         if (v_dHoraEntrada IS NOT NULL AND v_dHoraEntrada_2 IS NOT NULL) then

         RAISE_APPLICATION_ERROR(-20002,'Usted ya registró su salida el día de hoy.');

         end if;
         UPDATE PBL_INGRESO_PERSONAL R
         SET    R.ENTRADA_2 = SYSDATE,
                R.USU_MOD = cDni_in,
                R.FEC_MOD = SYSDATE
         WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    R.COD_LOCAL = cCodLocal_in
         AND    R.DNI = cDni_in
         AND    R.FECHA = d_dDiaTrabajo
         AND    R.ENTRADA_2 IS NULL;

      ELSE
         UPDATE PBL_INGRESO_PERSONAL R
         SET    R.SALIDA_2 = SYSDATE,
                R.USU_MOD  = cDni_in,
                R.FEC_MOD  = SYSDATE
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
    cValor CHAR(1);
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

    SELECT COUNT(*) INTO nCant1
    FROM PBL_ROL_USU X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia
    AND X.COD_LOCAL=cCodLocal
    AND X.SEC_USU_LOCAL=cSecUsu
    AND X.COD_ROL=ROL;

    SELECT COUNT(*) INTO nCant2
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
    vresultado  CHAR(1);
    vcontador   NUMBER;
    BEGIN

    BEGIN
    SELECT COUNT(*) INTO vcontador
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
      vresultado := 'S';
    ELSE
      vresultado := 'N';
    END IF;
    RETURN vresultado;

    END;


     /***************************************************************************/
  FUNCTION VERIFICA_INGR_TEMP_USU(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   vSecUsu_in       IN CHAR)
    RETURN CHAR
    IS
    vresultado  CHAR(1);
    vcontador   NUMBER;
    vcontador2   NUMBER;
    HoraInicio   VARCHAR2(2);
    HoraFin      VARCHAR2(2);
    DniX  VARCHAR2(20);
    aux3  VARCHAR2(20);
    AUX NUMBER;

    ind CHAR(1);
    BEGIN

    SELECT TRIM(A.DESC_CORTA),TRIM(A.DESC_LARGA) INTO HoraInicio,HoraFin
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL=267;

 DBMS_OUTPUT.put_line('vSecUsu_in: '||vSecUsu_in);

    SELECT COUNT(*) INTO vcontador
    FROM LGT_HIST_TEMP_LOC Y
    WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
    AND Y.COD_LOCAL=cCodLocal_in
    -- AND Y.SEC_USU_LOCAL=vSecUsu_in
    AND TRUNC(Y.FEC_CREA_TEMP)=TRUNC(SYSDATE);

    DBMS_OUTPUT.put_line('vcontador: '||vcontador);

    SELECT Y.DNI_USU INTO DniX
    FROM PBL_USU_LOCAL Y
    WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
    AND Y.COD_LOCAL=cCodLocal_in
    AND Y.SEC_USU_LOCAL=vSecUsu_in;

    AUX:=0;

    SELECT COUNT(1) INTO AUX
    FROM PBL_INGRESO_PERSONAL X
    WHERE X.COD_GRUPO_CIA =cCodGrupoCia_in
    AND X.COD_LOCAL       =cCodLocal_in
    AND X.DNI             =trim(DniX)
    AND TRUNC(X.FEC_CREA) =TRUNC(SYSDATE)
    --AND X.ENTRADA < trunc(SYSDATE)+to_number(HoraInicio,'99')/24
    AND TO_DATE(TO_CHAR(X.ENTRADA,'HH24:MI'),'HH24:MI')<trunc(SYSDATE)+to_number(HoraInicio,'99')/24;


        DBMS_OUTPUT.put_line(aux);
        DBMS_OUTPUT.put_line(TO_CHAR(SYSDATE,'YYYYMMDD')||HoraInicio);
        DBMS_OUTPUT.put_line('DniX: '||DniX);

    SELECT CASE WHEN TO_CHAR(SYSDATE,'HH24MISS') BETWEEN '000000' AND '015959' THEN 'S' ELSE 'N' END INTO ind FROM DUAL;

   SELECT COUNT(*) INTO vcontador2
    FROM LGT_HIST_TEMP_LOC Y
    WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
    AND Y.COD_LOCAL=cCodLocal_in
    AND TRUNC(Y.FEC_CREA_TEMP)=TRUNC(SYSDATE-1);

    DBMS_OUTPUT.put_line('ind: '||ind);
        DBMS_OUTPUT.put_line('vcontador2: '||vcontador2);

    IF vcontador > 0 THEN
      vresultado := 'S';
    ELSIF ind='S' AND vcontador2>0 THEN --si el dia anterior no marco y sale pasada las 12 en ragno de 1 a 2 am
       vresultado := 'S';
    ELSE
      vresultado := 'N';
    END IF;



    RETURN vresultado;

    END;
      /***************************************************************************/
   FUNCTION GET_SEC_USU_X_DNI(cCodGrupoCia_in  IN CHAR,
                              cCodLocal_in     IN CHAR,
                              cDni             IN CHAR)
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
						AND C.NUM_DOC_IDEN = cDni);
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
    vresultado  CHAR(1);
    vcontador   NUMBER;
    BEGIN
    SELECT COUNT(*) INTO vcontador
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
         vresultado := 'S';
       ELSE
        vresultado := 'N';
        END IF;

     END;


    RETURN vresultado;

    END;

END;
/

