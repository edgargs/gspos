--------------------------------------------------------
--  DDL for Package PTOVENTA_CONTROL_PROB
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CONTROL_PROB" is

  -- Author  : LREQUE
  -- Created : 23/04/2007 19:35:14

  -- Public type declarations
  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Obtiene los registros de control
  --Fecha       Usuario		Comentario
  --23/04/2007  Luis Reque     Creación
  FUNCTION CONTROL_PROB_OBTIENE_REGISTROS(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cFecIni_in      IN CHAR,
                                          cFecFin_in      IN CHAR) RETURN FarmaCursor;

  --Descripcion: Agrega un nuevo registro
  --Fecha       Usuario		Comentario
  --23/04/2007  Luis Reque     Creación
  PROCEDURE CONTROL_PROB_AGREGA_CONTROL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        nCantTint_in    IN NUMBER,
                                        nCantRec_in     IN NUMBER,
                                        nCantAten_in    IN NUMBER,
                                        cUsuCrea_in     IN CHAR,
                                        cFechaControl   IN CHAR);

  --Descripcion: Modifica un registro existente
  --Fecha       Usuario		Comentario
  --23/04/2007  Luis Reque     Creación
  PROCEDURE CONTROL_PROB_ACTUALIZA_CONTROL(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR,
                                           cFecControl_in  IN CHAR,
                                           nCantTint_in    IN NUMBER,
                                           nCantRec_in     IN NUMBER,
                                           nCantAten_in    IN NUMBER,
                                           cUsuModif_in    IN CHAR);

  --Descripcion: Verifica la fecha maxima del control registrado
  --Fecha       Usuario		Comentario
  --23/04/2007  Luis Reque     Creación
  FUNCTION CONTROL_VERIFICA_FEC_MAX(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR) RETURN CHAR;

end PTOVENTA_CONTROL_PROB;

/
