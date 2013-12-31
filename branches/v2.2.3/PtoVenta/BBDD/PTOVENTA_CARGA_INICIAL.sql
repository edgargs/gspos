--------------------------------------------------------
--  DDL for Package PTOVENTA_CARGA_INICIAL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CARGA_INICIAL" is

  -- Author  : JCHAVEZ
  -- Created : 26/10/2009 09:28:16 a.m.
  -- Purpose :


  PROCEDURE CARGA_INICIAL(cCodLocalOrigen_in IN CHAR, cCodLocalNuevo_in IN CHAR,cFechaInicio IN CHAR, cFechaFin IN CHAR);
  --Fecha       Usuario		       Comentario
  --12/01/2010  ASOSA              Modificacion
  PROCEDURE CARGA_INICIAL_REVERTIR(cCodLocalOrigen_in IN CHAR, cCodLocalNuevo_in IN CHAR,cFechaInicio IN CHAR, cFechaFin IN CHAR,cFechaPrueba_in IN CHAR DEFAULT NULL,cTipoRevertir_in IN CHAR DEFAULT NULL,cindcn in char DEFAULT 'N');

  --Descripcion: Deshabilita los constraints de las tablas punto de venta
  --Fecha       Usuario		       Comentario
  --26/10/2009  Jenny Chávez     Creacion
  PROCEDURE CARGA_INICIAL_PASO1;

  --Descripcion: Borra los registros de las tablas
  --Fecha       Usuario		       Comentario
  --26/10/2009  Jenny Chávez     Creacion
  --12/10/2010  Alfredo Sosa     Modificacion
  PROCEDURE CARGA_INICIAL_PASO2(cTipoRevertir_in IN CHAR DEFAULT NULL);

  --Descripcion: Actualiza las tablas para inicializar la información de las tablas para el nuevo local
  --Fecha       Usuario		       Comentario
  --26/10/2009  Jenny Chávez     Creacion
  --12/10/2010  Alfredo Sosa     Modificacion
  PROCEDURE CARGA_INICIAL_PASO3(cCodLocalOrigen_in IN CHAR, cCodLocalNuevo_in IN CHAR,cFechaInicio IN CHAR, cFechaFin IN CHAR,cTipoRevertir_in IN CHAR DEFAULT NULL);

  --Descripcion: Habilita los constraints de las tablas punto de venta
  --Fecha       Usuario		       Comentario
  --26/10/2009  Jenny Chávez     Creacion
  PROCEDURE CARGA_INICIAL_PASO4;

  --Descripcion: Recrea los indices de las tablas
  --Fecha       Usuario		       Comentario
  --26/10/2009  Jenny Chávez     Creacion
  PROCEDURE CARGA_INICIAL_PASO5;

  --Descripcion: Revierte las pruebas
  --Fecha       Usuario		       Comentario
  --16/12/2009  Jenny Chávez     Creacion
  --12/01/2010  Alfedo Sosa      Modificación
  PROCEDURE CARGA_INICIAL_REV_PRUEBA(cCodLocalNuevo_in IN CHAR,cFechaPrueba_in IN CHAR DEFAULT NULL,cTipoRevertir_in IN CHAR DEFAULT NULL,cindcn in char DEFAULT NULL);

  --Descripcion: Envia correo
  --Fecha       Usuario		       Comentario
  --16/12/2009  Jenny Chávez     Creacion
PROCEDURE CARGA_INICIAL_ENVIA_CORREO(cCodLocalNuevo_in IN CHAR, cTipo_in IN CHAR,cResultado IN CHAR);


  --Descripcion: Procesar el revertir pruebas en local nuevo
  --Fecha       Usuario		Comentario
  --17/12/2009  JCHAVEZ     Creación
  --12/01/2010  ASOSA       Modificación
  PROCEDURE CARGA_INICIAL_P_REVERTIR(cCodLocal_in IN CHAR, cindcn in char DEFAULT NULL);


  --Descripcion: Actualiza en 'S' el indicador de revertir en el local nuevo
  --Fecha       Usuario		Comentario
  --17/12/2009  JCHAVEZ     Creación
  PROCEDURE P_ACT_REVERTIR_LOCAL(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                vUsu_in      IN CHAR);
 FUNCTION F_GET_IND_REVERTIR_LOCAL(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR)
                                RETURN CHAR;

  --Descripcion: Graba inicio fin de pruebas en local nuevo
  --Fecha       Usuario		Comentario
  --17/12/2009  JCHAVEZ     Creación
  PROCEDURE P_GRABA_INICIO_FIN_PRUEBA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cTipo           IN CHAR,
                                      cUsuario        IN CHAR);
  FUNCTION  F_NUM_GET_CANT_PRUEBAS(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR)  RETURN NUMBER;

  FUNCTION F_NUM_GET_CANT_PRUEBAS_COMP(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR)  RETURN NUMBER;
  FUNCTION F_GET_CHR_FECHA_INICIO_PRUEBAS(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR)
                                           RETURN CHAR;

  --Descripcion: Valida que no se revierta en local cuando hallan pasado mas de 2 dias
  --Fecha       Usuario		Comentario
  --12/01/2010  ASOSA     Creación
  FUNCTION F_GET_CHR_REVER_VALIDO(cCodCia_in in CHAR,
                                 cCodLocal_in in CHAR)
  RETURN CHAR;


  --Descripcion: Se verifica el indicador de valida proceso de reversion
  --Fecha       Usuario		Comentario
  --19/01/2010  JCORTEZ     Creación
    FUNCTION F_IND_PROCE_REVERTIR(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in     IN CHAR)
    RETURN CHAR;

  --Descripcion: ENVIA CORREO DESDE EL LOCAL CUANDO NO HAY LINEA CON MATRIZ
  --Fecha       Usuario		Comentario
  --  ASOSA     Creación
    PROCEDURE REVERTIR_ENVIAR_CORREO(cCodLocalNuevo_in IN CHAR);

  --Descripcion: Recrea sequence's
  --Fecha       Usuario		       Comentario
  --24/10/2013  ERIOS            Creacion
  PROCEDURE CARGA_INICIAL_PASO6;
  
end PTOVENTA_CARGA_INICIAL;

/
