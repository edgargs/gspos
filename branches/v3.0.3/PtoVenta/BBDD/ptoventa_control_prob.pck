CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CONTROL_PROB" is

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

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CONTROL_PROB" is

  FUNCTION CONTROL_PROB_OBTIENE_REGISTROS(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cFecIni_in      IN CHAR,
                                          cFecFin_in      IN CHAR) RETURN FarmaCursor
  IS
    curControl FarmaCursor;
  BEGIN
       OPEN curControl FOR
         SELECT    TO_CHAR(PROB.FEC_CONTROL,'dd/MM/yyyy')  || 'Ã' ||
                   PROB.USU_CREA_CONTROL                   || 'Ã' ||
                   PROB.CANT_TINT                          || 'Ã' ||
                   PROB.CANT_REC                           || 'Ã' ||
                   PROB.CANT_ATEN
         FROM      PBL_CONTROL_PROBISA PROB
         WHERE     PROB.COD_GRUPO_CIA  = cCodGrupoCia_in
         AND       PROB.COD_LOCAL      = cCodLocal_in
         AND       TO_DATE(PROB.FEC_CONTROL,'dd/MM/yy')
                   BETWEEN TO_DATE(cFecIni_in,'dd/MM/yyyy')
                   AND     TO_DATE(cFecFin_in,'dd/MM/yyyy');
       RETURN curControl;

  END CONTROL_PROB_OBTIENE_REGISTROS;

----------------------------------------------------------------------------------

  PROCEDURE CONTROL_PROB_AGREGA_CONTROL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        nCantTint_in    IN NUMBER,
                                        nCantRec_in     IN NUMBER,
                                        nCantAten_in    IN NUMBER,
                                        cUsuCrea_in     IN CHAR,
                                        cFechaControl   IN CHAR)
  IS
  BEGIN
       INSERT INTO PBL_CONTROL_PROBISA(COD_GRUPO_CIA,COD_LOCAL,CANT_TINT,CANT_REC,CANT_ATEN,FEC_CREA_CONTROL,USU_CREA_CONTROL,FEC_CONTROL)
              VALUES(cCodGrupoCia_in,cCodLocal_in,nCantTint_in,nCantRec_in,nCantAten_in,SYSDATE,cUsuCrea_in,to_date(cFechaControl,'dd/MM/yyyy'));
       exception
         when dup_val_on_index then
           RAISE_APPLICATION_ERROR(-20200,'Ya existe un registro para el dia ingresado');


  END CONTROL_PROB_AGREGA_CONTROL;

----------------------------------------------------------------------------------

  PROCEDURE CONTROL_PROB_ACTUALIZA_CONTROL(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR,
                                           cFecControl_in  IN CHAR,
                                           nCantTint_in    IN NUMBER,
                                           nCantRec_in     IN NUMBER,
                                           nCantAten_in    IN NUMBER,
                                           cUsuModif_in    IN CHAR)
  IS
  BEGIN
       UPDATE PBL_CONTROL_PROBISA SET CANT_TINT = nCantTint_in,
                                      CANT_REC  = nCantRec_in,
                                      CANT_ATEN = nCantAten_in,
                                      USU_MOD_CONTROL = cUsuModif_in,
                                      FEC_MOD_CONTROL = SYSDATE
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND   COD_LOCAL     = cCodLocal_in
       AND   TO_CHAR(FEC_CONTROL,'dd/MM/yyyy') = cFecControl_in;

  END CONTROL_PROB_ACTUALIZA_CONTROL;

----------------------------------------------------------------------------------

  FUNCTION CONTROL_VERIFICA_FEC_MAX(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR) RETURN CHAR
  IS
    vMaxFecha CHAR(10);
  BEGIN
       SELECT TO_CHAR(MAX(PROB.FEC_CONTROL),'dd/MM/yyyy') INTO vMaxFecha
       FROM   PBL_CONTROL_PROBISA PROB
       WHERE  PROB.COD_GRUPO_CIA  = cCodGrupoCia_in
       AND    PROB.COD_LOCAL      = cCodLocal_in;

       IF vMaxFecha = TO_CHAR(SYSDATE,'dd/MM/yyyy') THEN
          RETURN 'S';
       ELSE
           RETURN 'N';
       END IF;
  END CONTROL_VERIFICA_FEC_MAX;
end PTOVENTA_CONTROL_PROB;
/

