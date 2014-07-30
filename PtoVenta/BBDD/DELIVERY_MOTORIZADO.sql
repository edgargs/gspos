--------------------------------------------------------
--  DDL for Package DELIVERY_MOTORIZADO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."DELIVERY_MOTORIZADO" AS
 TYPE FarmaCursor IS REF CURSOR;

 EST_MOT_ACTIVO   CHAR(6) :='ACTIVO';
 EST_MOT_INACTIVO CHAR(8) :='INACTIVO';

   --Descripcion: Agrega un nuevo motorizado
  --Fecha       Usuario		       Comentario
  --20/12/2006  Luis Reque       Creacion
  --09/08/2007  DUbilluz         Modificacion
  FUNCTION MOT_AGREGA_MOTORIZADO ( cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cCodNumera_in   IN CHAR,
                                   cNomMot_in      IN CHAR,
                                   cApePatMot_in   IN CHAR,
                                   cApeMatMot_in   IN CHAR,
                                   cDniMot_in      IN CHAR,
                                   cPlacaMot_in    IN CHAR,
                                   cNumNexMot_in   IN CHAR,
                                   cFecNac_in      IN CHAR,
                                   cDirecMot_in    IN CHAR,
                                   cUsuCreaMot_in  IN CHAR,
                                   ----------------------
                                   cAliasMot_in    IN CHAR,
                                   cCodLocalAtencion_in IN CHAR
                                   )
    RETURN CHAR;

  --Descripcion: Actualiza la informacion de un motorizado
  --Fecha       Usuario		       Comentario
  --20/12/2006  Luis Reque       Creacion
  --09/08/2007  Dubilluz         Modificacion
 PROCEDURE MOT_MODIFICA_MOTORIZADO (cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cCodMot_in      IN CHAR,
                                    cNomMot_in      IN CHAR,
                                    cApePatMot_in   IN CHAR,
                                    cApeMatMot_in   IN CHAR,
                                    cDniMot_in      IN CHAR,
                                    cPlacaMot_in    IN CHAR,
                                    cNumNexMot_in   IN CHAR,
                                    cFecNac_in      IN CHAR,
                                    cDirecMot_in    IN CHAR,
                                    cUsuModMot_in   IN CHAR,
                                     ----------------------
                                     cAliasMot_in    IN CHAR,
                                     cCodLocalAtencion_in IN CHAR
                                    );

  --Descripcion: Actualiza el estado de un motorizado
  --Fecha       Usuario		       Comentario
  --20/12/2006  Luis Reque       Creacion

 PROCEDURE MOT_ACTUALIZA_ESTADO_MOT (cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cCodMot_in      IN CHAR,
                                     cEstMot_in      IN CHAR,
                                     cUsuModMot_in   IN CHAR);

  --Descripcion: Obtiene todos los motorizados registrados
  --Fecha       Usuario		       Comentario
  --20/12/2006  Luis Reque       Creacion
  --09/08/2007  Dubilluz         MOdificacion
  --20/07/2014  ERIOS              Se agrega parametro 'cEstado'
 FUNCTION MOT_OBTIENE_MOTORIZADOS (cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
								   cEstado IN CHAR)
 RETURN FarmaCursor;

   --Descripcion: Obtiene los datos de un motorizado especifico
  --Fecha       Usuario		       Comentario
  --21/12/2006  Luis Reque       Creacion

 FUNCTION MOT_OBTIENE_INFO_MOTORIZADO (cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cCodMot_in      IN CHAR)
 RETURN FarmaCursor;

 --BUSCA EL LOCAL QUE SE LE ENVIA EL PARAMETRO
 --FECHA       USUARIO    COMENTARIO
 --09/08/2007  DUBILLUZ   CREACION
 FUNCTION MOT_BUSCA_LOCAL(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in    IN CHAR)

 RETURN FarmaCursor;

END DELIVERY_MOTORIZADO;

/
