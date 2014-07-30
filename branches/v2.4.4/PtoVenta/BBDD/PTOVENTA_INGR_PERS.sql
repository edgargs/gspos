--------------------------------------------------------
--  DDL for Package PTOVENTA_INGR_PERS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_INGR_PERS" is

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
