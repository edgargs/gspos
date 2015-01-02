CREATE OR REPLACE PACKAGE PTOVENTA."DELIVERY_MOTORIZADO" AS
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

CREATE OR REPLACE PACKAGE BODY PTOVENTA."DELIVERY_MOTORIZADO" AS

  FUNCTION MOT_AGREGA_MOTORIZADO (cCodGrupoCia_in IN CHAR,
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
  RETURN CHAR
  IS
  v_Temp_CodMot	NUMBER;
	v_CodMot	    CHAR(3);
  BEGIN
    v_Temp_CodMot := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, '606');
    v_CodMot      := Farma_Utility.COMPLETAR_CON_SIMBOLO(TO_CHAR(v_Temp_CodMot), 3, '0', 'I');

    INSERT INTO DEL_MOTORIZADOS(COD_GRUPO_CIA,
                                COD_LOCAL,
                                COD_MOTORIZADO,
                                NOM_MOTORIZADO,
                                APE_PAT_MOTORIZADO,
                                APE_MAT_MOTORIZADO,
                                DNI_MOTORIZADO,
                                PLACA_MOTORIZADO,
                                NUM_NEXTEL,
                                FEC_NAC_MOTORIZADO,
                                DIREC_MOTORIZADO,
                                FEC_CREA_MOTORIZADO,
                                USU_CREA_MOTORIZADO,LOGIN_MOTORIZADO,cod_local_referencia)
    VALUES(cCodGrupoCia_in,
           cCodLocal_in,
           v_CodMot,
           cNomMot_in,
           cApePatMot_in,
           cApeMatMot_in,
           cDniMot_in,
           cPlacaMot_in,
           cNumNexMot_in,
           TO_DATE(cFecNac_in,'DD/MM/YYYY'),
           cDirecMot_in,
           SYSDATE,
           cUsuCreaMot_in,--substr(cNomMot_in,0,1)||cApePatMot_in);
           --AGREGADO
           cAliasMot_in,cCodLocalAtencion_in);

    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               '606',
                                               cUsuCreaMot_in);
    RETURN v_CodMot;
  END MOT_AGREGA_MOTORIZADO;
/**************************************************************************************/

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
                                     )
  IS
  BEGIN

    UPDATE DEL_MOTORIZADOS SET NOM_MOTORIZADO=cNomMot_in,
                               APE_PAT_MOTORIZADO=cApePatMot_in,
                               APE_MAT_MOTORIZADO=cApeMatMot_in,
                               DNI_MOTORIZADO=cDniMot_in,
                               PLACA_MOTORIZADO=cPlacaMot_in,
                               NUM_NEXTEL=cNumNexMot_in,
                               FEC_NAC_MOTORIZADO=cFecNac_in,
                               DIREC_MOTORIZADO=cDirecMot_in,
                               USU_MOD_MOTORIZADO=cUsuModMot_in,
                               FEC_MOD_MOTORIZADO=SYSDATE ,
                               -----
                               LOGIN_MOTORIZADO = cAliasMot_in,
                               cod_local_referencia =cCodLocalAtencion_in
    WHERE COD_GRUPO_CIA  =cCodGrupoCia_in
    AND   COD_LOCAL      =cCodLocal_in
    AND   COD_MOTORIZADO =cCodMot_in;

 END MOT_MODIFICA_MOTORIZADO;
/**********************************************************************************/

 PROCEDURE MOT_ACTUALIZA_ESTADO_MOT (cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cCodMot_in      IN CHAR,
                                     cEstMot_in      IN CHAR,
                                     cUsuModMot_in   IN CHAR)
  IS
  vEstMot CHAR(1);
  BEGIN

      IF cEstMot_in=EST_MOT_ACTIVO THEN
         vEstMot:='I';
      ELSE
         vEstMot:='A';
      END IF;

      UPDATE DEL_MOTORIZADOS SET EST_MOTORIZADO=vEstMot,
                                 USU_MOD_MOTORIZADO=cUsuModMot_in,
                                 FEC_MOD_MOTORIZADO=SYSDATE
      WHERE COD_GRUPO_CIA  =cCodGrupoCia_in
      AND   COD_LOCAL      =cCodLocal_in
      AND   COD_MOTORIZADO =cCodMot_in;

  END MOT_ACTUALIZA_ESTADO_MOT;

  --Descripcion: Obtiene todos los motorizados registrados
  --Fecha       Usuario		       Comentario
  --20/12/2006  Luis Reque       Creacion
  --09/08/2007  Dubilluz         MOdificacion
  --20/07/2014  ERIOS              Se agrega parametro 'cEstado'
 FUNCTION MOT_OBTIENE_MOTORIZADOS (cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
								   cEstado IN CHAR)
 RETURN FarmaCursor
 IS
   curMot FarmaCursor;
 BEGIN
      OPEN curMot FOR
        SELECT MOT.COD_MOTORIZADO                                         || 'Ã' ||
               MOT.APE_PAT_MOTORIZADO                                     || 'Ã' ||
               NVL(MOT.APE_MAT_MOTORIZADO,' ')                            || 'Ã' ||
               MOT.NOM_MOTORIZADO                                         || 'Ã' ||
               MOT.DNI_MOTORIZADO                                         || 'Ã' ||
               DECODE(MOT.EST_MOTORIZADO,'A','ACTIVO','INACTIVO')         || 'Ã' ||
               --AGREGADO
               nvl(MOT.LOGIN_MOTORIZADO,' ')                              || 'Ã' ||
               nvl(MOT.COD_LOCAL_REFERENCIA,' ')                          || 'Ã' ||
               nvl((Select nvl(L.DESC_CORTA_LOCAL,' ')
                 from  pbl_local l
                 where l.cod_grupo_cia = cCodGrupoCia_in and
                       l.cod_local = nvl(MOT.COD_LOCAL_REFERENCIA,' ')
               ),' ')

        FROM   DEL_MOTORIZADOS MOT

        WHERE  MOT.COD_GRUPO_CIA  = cCodGrupoCia_in
        AND    MOT.COD_LOCAL      = cCodLocal_in
		and MOT.EST_MOTORIZADO LIKE cEstado ;
      RETURN curMot;
 END MOT_OBTIENE_MOTORIZADOS;

 FUNCTION MOT_OBTIENE_INFO_MOTORIZADO (cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cCodMot_in      IN CHAR)
 RETURN FarmaCursor
 IS
   curMot FarmaCursor;
 BEGIN
      OPEN curMot FOR
        SELECT MOT.COD_MOTORIZADO                                         || 'Ã' ||
               MOT.NOM_MOTORIZADO                                         || 'Ã' ||
               MOT.APE_PAT_MOTORIZADO                                     || 'Ã' ||
               NVL(MOT.APE_MAT_MOTORIZADO,' ')                            || 'Ã' ||
               MOT.DNI_MOTORIZADO                                         || 'Ã' ||
               NVL(MOT.PLACA_MOTORIZADO,' ')                              || 'Ã' ||
               NVL(MOT.NUM_NEXTEL,' ')                                    || 'Ã' ||
               NVL(TO_CHAR(MOT.FEC_NAC_MOTORIZADO,'dd/MM/yyyy'),' ')      || 'Ã' ||
               NVL(MOT.DIREC_MOTORIZADO,' ')
        FROM   DEL_MOTORIZADOS MOT
        WHERE  MOT.COD_GRUPO_CIA  =cCodGrupoCia_in
        AND    MOT.COD_LOCAL      =cCodLocal_in
        AND    MOT.COD_MOTORIZADO =cCodMot_in;
      RETURN curMot;

 END MOT_OBTIENE_INFO_MOTORIZADO;

 --BUSCA EL LOCAL QUE SE LE ENVIA EL PARAMETRO
 --FECHA       USUARIO    COMENTARIO
 --09/08/2007  DUBILLUZ   CREACION
 FUNCTION MOT_BUSCA_LOCAL(cCodGrupoCia_in IN CHAR,
                      cCodLocal_in    IN CHAR)
 RETURN FarmaCursor
 IS
   curlocal FarmaCursor;
 BEGIN
      OPEN curlocal FOR
        SELECT  L.COD_LOCAL  || 'Ã' ||
                L.DESC_CORTA_LOCAL
        FROM   PBL_LOCAL L
        WHERE  L.COD_GRUPO_CIA  =cCodGrupoCia_in
        AND    L.COD_LOCAL      =cCodLocal_in;
      RETURN curlocal;

 END MOT_BUSCA_LOCAL;


END DELIVERY_MOTORIZADO;
/

