CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_ADMIN_FONDO_SEN" is

  -- Author  : JMIRANDA
  -- Created : 25/02/2010 05:50:58 p.m.
  -- Purpose :

  -- Public type declarations
  type FarmaCursor is REF CURSOR;
      FPAGO_FSEN CHAR(5):='00060';
  -- Public constant declarations
  cEst_Activo constant CHAR(1) := 'A';
  cEst_Emitido constant CHAR(1) := 'E';
  cEst_Anulado constant CHAR(1) := 'N';
  cEst_Rechazado constant CHAR(1) := 'R';

  cTipo_Asignacion constant CHAR(1) := 'A';
  cTipo_Devolucion constant CHAR(1) := 'D';

  cRol_Cajero CHAR(3) := '009';
  cRol_Administrador CHAR(3) := '011';

  -----------------
    C_INICIO_MSG VARCHAR2(20000) := '<html>'  ||
                                      '<head>'  ||
                                      '<style type="text/css">'  ||
                                      '.style3 {font-family: Arial, Helvetica, sans-serif}'  ||
                                      '.style8 {font-size: 24; }'  ||
                                      '.style9 {font-size: larger}'  ||
                                      '.style12 {'  ||
                                      'font-family: Arial, Helvetica, sans-serif;'  ||
                                      'font-size: larger;'  ||
                                      'font-weight: bold;'  ||
                                      '}'  ||
                                      '</style>'  ||
                                      '</head>'  ||
                                      '<body>'  ||
                                      '<table width="510"border="0">'  ||
                                      '<tr>'  ||
                                      '<td width="487" align="center" valign="top"><h1>FONDO DE SENCILLO</h1></td>'  ||
                                      '</tr>'  ||
                                      '</table>'  ||
                                      '<table width="504" border="0">';

  C_FIN_MSG VARCHAR2(2000) := '</table>' ||
                                  '</body>' ||
                                  '</html>';
  -----------------



  /** *******************************/
    FUNCTION FONDO_SEN_CUR_LISTA_CAJ_DISP (cCod_Grupo_Cia_in CHAR,
                                      cCod_Local_in CHAR)
    RETURN FarmaCursor;

  /** *******************************/
  FUNCTION FONDO_SEN_F_CUR_LIS_HIST_FON (cCod_Grupo_Cia_in CHAR,
                                      cCod_Local_in CHAR,
                                      cTipo_in CHAR DEFAULT 'T')
    RETURN FarmaCursor;
  /** *******************************/

    FUNCTION FONDO_SEN_F_CUR_LIS_HIST_FE (cCod_Grupo_Cia_in CHAR,
                                      cCod_Local_in CHAR,
                                      cFecha_Ini_in CHAR,
                                      cFecha_Fin_in CHAR,
                                      cTipo_in CHAR DEFAULT 'T')
    RETURN FarmaCursor;
  /** *******************************/
    FUNCTION FONDO_SEN_F_IND_TIENE_FONDO(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cSec_Usu_Caj_in CHAR)
    RETURN CHAR;
  /** *******************************/

    PROCEDURE FONDO_SEN_P_INS_ASIGNA (cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       nMonto_in NUMBER,
                                      csec_usu_destino_in CHAR, --destino
                                       cSEC_USU_origen_in CHAR, --origen
                                       cEstado_in CHAR,
                                       cInd_Tipo_Fondo_in CHAR,
                                       cUsu_Crea_in CHAR,
                                       cIp_in VARCHAR2);
  /** *******************************/
  FUNCTION FONDO_SEN_F_UPD_ACEPTA_FONDO (cCod_Grupo_Cia_in CHAR,
                                         cCod_Local_in CHAR,
                                         cUsu_in CHAR,
                                         cIp_in VARCHAR2,
                                         cSec_Usu_in CHAR)
  RETURN VARCHAR2;

  /** *******************************/
  PROCEDURE FONDO_SEN_P_UPD_RECHAZO_FONDO (cCod_Grupo_Cia_in CHAR,
                                         cCod_Local_in CHAR,
                                         cUsu_in CHAR,
                                         cIp_in VARCHAR2,
                                         cSec_Usu_in CHAR);

  /** *******************************/
  FUNCTION FONDO_SEN_F_GET_MONTO_ASIGNADO (cCod_Grupo_Cia_in CHAR,
                                          cCod_Local_in CHAR,
                                          cSec_Usu_in CHAR
                                          )
  RETURN CHAR;

  /** *******************************/
  PROCEDURE FONDO_SEN_P_UPD_MOV_CAJA (cCod_Grupo_Cia_in CHAR,
                                          cCod_Local_in CHAR,
                                          cSec_usu_in CHAR,
                                          nNumCaj_in CHAR,
                                          cSec_Fondo_Sen_in CHAR,
                                          cUsu_in CHAR,
                                          cIp_in VARCHAR2
                                          );

  /** *******************************/
  FUNCTION FONDO_SEN_F_IND_TIENE_DEV (cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cSec_Usu_Caj_in CHAR,
                                       cSecMovCajaCierre_in VARCHAR2)
  RETURN CHAR;

  /** *******************************/
  FUNCTION FONDO_SEN_F_CUR_LIS_QF (cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR )
  RETURN FarmaCursor;
  /** *******************************/
  PROCEDURE FONDO_SEN_P_INS_DEVOL (cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       nMonto_in NUMBER,
                                       csec_usu_destino_in CHAR, --destino QF
                                       cSEC_USU_origen_in CHAR, --origen  CJ
                                       cEstado_in CHAR,
                                       cInd_Tipo_Fondo_in CHAR,
                                       cUsu_Crea_in CHAR,
                                       cIp_in VARCHAR2,
                                       cSec_Mov_Caja_cierre_in VARCHAR2);

  /** *******************************/
  FUNCTION FONDO_SEN_F_GET_MONTO_DEV (cCod_Grupo_Cia_in CHAR,
                                          cCod_Local_in CHAR,
                                          cSec_Usu_in CHAR,
                                          cSec_Mov_Caja_cierre_in VARCHAR2
                                          )
  RETURN CHAR;

  /** *******************************/
  PROCEDURE FONDO_SEN_P_UPD_DEVOL (cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       nMonto_in NUMBER,
                                       csec_usu_destino_in CHAR, --destino QF
                                       cSEC_USU_origen_in CHAR, --origen  CJ
                                       cUsu_Crea_in CHAR,
                                       cIp_in VARCHAR2,
                                       cSec_Mov_Caja_cierre_in VARCHAR2
                                       );

  /** *******************************/
    FUNCTION FONDO_SEN_F_ANUL_ASIG(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cUsu_Mod_in CHAR,
                                       cIp_in VARCHAR2,
                                       cSec_Fondo_Sen_in VARCHAR2,
                                       cEstado_in CHAR,
                                       cTipo_in CHAR)
     RETURN CHAR;

  /** *******************************/
  FUNCTION FONDO_SEN_F_VAR2_IMP_VOUCHER(cGrupoCia_in  IN CHAR,
                                              cCodLocal_in  IN CHAR,
                                              cSecFondoSen_in IN VARCHAR2) RETURN VARCHAR2;

  /** *******************************/
  PROCEDURE FONDO_SEN_P_ACEPTAR_DEVOL(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cUsu_Mod_in CHAR,
                                       cIp_in VARCHAR2,
                                       cSec_Fondo_Sen_in VARCHAR2);

  /** *******************************/
  FUNCTION FONDO_SEN_F_IND_HABILITADO RETURN CHAR;

  /** *******************************/
  FUNCTION FONDO_SEN_F_IND_VAL_USU_OK(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cSec_Usu_Local_in CHAR)
  RETURN CHAR;
  /** *******************************/

  FUNCTION FONDO_SEN_F_IND_CAJ_DISP(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cSec_Usu_Caj_in CHAR)
  RETURN CHAR;

  /** *******************************/
  FUNCTION FONDO_SEN_F_VAR2_IMP_VOUCHER_2(cCod_Grupo_Cia_in  IN CHAR,
                                              cCod_Local_in  IN CHAR,
                                              cSecMovCajaCierre_in IN VARCHAR2) RETURN VARCHAR2;
  /** *******************************/
    PROCEDURE FONDO_SEN_P_ELIMINA_DEVOL(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cUsu_Crea_in CHAR,
                                       cIp_in VARCHAR2,
                                       cSec_Mov_Caja_cierre_in VARCHAR2);
  /** *******************************/
  PROCEDURE FONDO_SEN_P_ACEPTAR_DEVOL_QF(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cUsu_Mod_in CHAR,
                                       cIp_in VARCHAR2,
                                       cSec_Mov_Caja_cierre_in VARCHAR2);
  /** *******************************/
  FUNCTION FONDO_SEN_P_FONDO_ACEPTADO(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cSec_Mov_Caja_cierre_in VARCHAR2)
                                       RETURN CHAR;

  /** *******************************/
    FUNCTION FONDO_SEN_F_INS_ASIGNA(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       nMonto_in NUMBER,
                                       csec_usu_destino_in CHAR, --destino
                                       cSEC_USU_origen_in CHAR, --origen
                                       cEstado_in CHAR,
                                       cInd_Tipo_Fondo_in CHAR,
                                       cUsu_Crea_in CHAR,
                                       cIp_in VARCHAR2) RETURN VARCHAR2 ;

  /** *******************************/
  FUNCTION FONDO_SEN_F_CUR_LIS_H_CAJ (cCod_Grupo_Cia_in CHAR,
                                      cCod_Local_in CHAR,
                                      cTipo_in CHAR DEFAULT 'T',
                                      cSec_Usu_Caj_in CHAR) RETURN FarmaCursor;
  /** *******************************/
  FUNCTION FONDO_SEN_F_CUR_LIS_H_FEC_CAJ (cCod_Grupo_Cia_in CHAR,
                                      cCod_Local_in CHAR,
                                      cFecha_Ini_in CHAR,
                                      cFecha_Fin_in CHAR,
                                      cTipo_in CHAR DEFAULT 'T',
                                      cSec_Usu_Caj_in CHAR)
  RETURN FarmaCursor ;

  /** *******************************/
  FUNCTION FONDO_SEN_F_CUR_LIS_CAJ_PRIN (cCod_Grupo_Cia_in CHAR,
                                      cCod_Local_in CHAR)
  RETURN FarmaCursor;
  /** *******************************/
  --Descripcion: Obtiene el monto asignado para que sea devuelto por completo
--Fecha       Usuario		 Comentario
--18/06/2010  ASOSA      Creación
FUNCTION FSEN_F_GET_MONTO_ACEP(CCODCIA_IN IN  CHAR,
                             CCODLOCAL_IN IN CHAR,
                             CSECMOVCAJA_IN IN CHAR)
RETURN VARCHAR;

--Descripcion: Determine si abre o no abre caja en caso este activo el fondo de sencillo y si le asignaron o no sencillo
--Fecha       Usuario		 Comentario
--20/06/2010  ASOSA      Creación
FUNCTION FSEN_F_OPEN_OR_NOT_OPEN(CCODCIA_IN IN CHAR,
                                 CCODLOCAL_IN IN CHAR,
                                 CSECUSU_IN IN CHAR)
RETURN CHAR;

--Descripcion: Determine si se debe dejar dar VB de cajero, en el caso este activo deberia entonces haberlo declarado como forma de pago entrega
--Fecha       Usuario		 Comentario
--21/06/2010  ASOSA      Creación
FUNCTION FSEN_F_DETERMINAR_NECESIDAD(CCOD_CIA_IN IN CHAR,
                                     CCOD_LOCAL_IN IN CHAR,
                                     CSECUSU_IN IN CHAR,
                                     CSEC_MOV_CAJA_IN IN CHAR)
RETURN CHAR;

end PTOVENTA_ADMIN_FONDO_SEN;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_ADMIN_FONDO_SEN" is
/* JMIRANDA 25.02.2010
   FONDO DE SENCILLO
 */
  FUNCTION FONDO_SEN_CUR_LISTA_CAJ_DISP (cCod_Grupo_Cia_in CHAR,
                                      cCod_Local_in CHAR)
  RETURN FarmaCursor IS
   curListaCajeros FarmaCursor;
  BEGIN
    OPEN curListaCajeros FOR
/*      SELECT L.SEC_USU_LOCAL|| 'Ã' || L.NOM_USU || 'Ã' || L.APE_PAT || 'Ã' || L.APE_MAT || 'Ã' || L.LOGIN_USU
        FROM PBL_USU_LOCAL L, PBL_ROL_USU RU
       WHERE L.COD_GRUPO_CIA = cCod_Grupo_Cia_in
         AND L.COD_LOCAL = cCod_Local_in
         AND L.EST_USU = cEst_Activo
         AND L.COD_GRUPO_CIA = RU.COD_GRUPO_CIA
         AND L.COD_LOCAL = RU.COD_LOCAL
         AND RU.COD_ROL = cRol_Cajero
         AND L.SEC_USU_LOCAL = RU.SEC_USU_LOCAL;*/

   SELECT L.SEC_USU_LOCAL || 'Ã' ||
          L.NOM_USU  || 'Ã' ||
          L.APE_PAT  || 'Ã' ||
          L.APE_MAT  || 'Ã' ||
          L.LOGIN_USU

    FROM PBL_USU_LOCAL L, PBL_ROL_USU RU
   WHERE L.COD_GRUPO_CIA = cCod_Grupo_Cia_in
     AND L.COD_LOCAL = cCod_Local_in
     AND L.EST_USU = cEst_Activo
     AND L.COD_GRUPO_CIA = RU.COD_GRUPO_CIA
     AND L.COD_LOCAL = RU.COD_LOCAL
     AND RU.COD_ROL = cRol_Cajero
     AND L.SEC_USU_LOCAL = RU.SEC_USU_LOCAL
     AND L.SEC_USU_LOCAL NOT IN    --TIENE CAJA ABIERTA
     (SELECT SEC_USU_LOCAL FROM
      (  SELECT C.COD_LOCAL, C.SEC_MOV_CAJA, C.SEC_USU_LOCAL FROM ce_mov_caja c
        WHERE c.cod_grupo_cia = cCod_Grupo_Cia_in
        AND c.cod_local = cCod_Local_in
        AND c.TIP_MOV_CAJA = 'A'
        MINUS
        SELECT C1.COD_LOCAL, C1.SEC_MOV_CAJA_ORIGEN, C1.SEC_USU_LOCAL FROM ce_mov_caja c1
        WHERE c1.cod_grupo_cia = cCod_Grupo_Cia_in
        AND c1.cod_local = cCod_Local_in
        AND c1.TIP_MOV_CAJA = 'C'
      )u
     )
     AND L.SEC_USU_LOCAL IN (SELECT cp.sec_usu_local
                              FROM vta_caja_pago cp
                              WHERE cp.cod_grupo_cia = cCod_Grupo_Cia_in
                              AND cp.cod_local = cCod_Local_in
                              AND cp.est_caja_pago = 'A')
     AND l.cod_local||l.sec_usu_local NOT IN
  (
    SELECT cod_local||usu
    FROM
    (--emitidos y aceptados asignados
    SELECT F.COD_LOCAL, F.SEC_FONDO_SEN sec, F.sec_usu_destino usu
      FROM CE_FONDO_SENCILLO F
     WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
     AND F.COD_LOCAL = cCod_Local_in
     AND F.IND_TIPO_FONDO_SEN = 'A'
     AND F.ESTADO IN ('E','A') --emitido, aceptado

    MINUS
    --devuelve
    SELECT f1.cod_local, f1.sec_fondo_sen_origen sec, f1.SEC_USU_origen usu
      FROM ce_fondo_sencillo f1
     WHERE f1.cod_grupo_cia = cCod_Grupo_Cia_in
      AND f1.cod_local = cCod_Local_in
      AND f1.Ind_Tipo_Fondo_Sen = 'D'
      AND f1.estado = 'A'
    /*MINUS
    SELECT F.COD_LOCAL, F.SEC_FONDO_SEN sec, F.sec_usu_destino usu
      FROM CE_FONDO_SENCILLO F
     WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
     AND F.COD_LOCAL = cCod_Local_in
     AND F.IND_TIPO_FONDO_SEN = 'A'
     AND F.ESTADO IN ('E','A') --emitido, aceptado
      */
    )
  );

   RETURN curListaCajeros;
  END;
  /* ****************************************************************/

  FUNCTION FONDO_SEN_F_CUR_LIS_HIST_FON (cCod_Grupo_Cia_in CHAR,
                                      cCod_Local_in CHAR,
                                      cTipo_in CHAR DEFAULT 'T')
  RETURN FarmaCursor IS
   curListaHistorico FarmaCursor;
   vTipo CHAR(1) := '%';
  BEGIN
      IF cTipo_in = 'T' THEN
         vTipo := '%';
      ELSE
         vTipo := cTipo_in;
      END IF;
    OPEN curListaHistorico FOR
    SELECT TO_CHAR(F.FEC_CREA_FONDO_SEN,'DD/MM/YYYY HH24:MI:SS') || 'Ã' ||  --FECHA
           DECODE(F.IND_TIPO_FONDO_SEN,'D','DEVUELVE','A','ASIGNA') || 'Ã' || --TIPO
           (Q1.NOM_USU||' '||Q1.APE_PAT||' '||Q1.APE_MAT) || 'Ã' || --ORIGEN
           (Q2.NOM_USU||' '||Q2.APE_PAT||' '||Q2.APE_MAT) || 'Ã' || --DESTINO
           TO_CHAR(F.MONTO,'9990.00') || 'Ã' || --MONTO
           NVL(DECODE(F.ESTADO,'E','EMITIDO','A','ACEPTADO','R','RECHAZADO','N','ANULADO'),'') || 'Ã' || --ESTADO
           nvl(M.NUM_CAJA_PAGO,0)  || 'Ã' || --CAJA
           nvl(M.NUM_TURNO_CAJA,0) || 'Ã' || --TURNO
           f.sec_fondo_sen || 'Ã' || --sec_fondo
           F.SEC_USU_origen || 'Ã' ||
           F.sec_usu_destino || 'Ã' ||
           F.IND_TIPO_FONDO_SEN || 'Ã' ||
           F.ESTADO EST
    FROM CE_FONDO_SENCILLO F,
         CE_MOV_CAJA M,
     (SELECT L.COD_GRUPO_CIA, L.COD_LOCAL, L.SEC_USU_LOCAL, L.NOM_USU, L.APE_PAT, L.APE_MAT
        FROM PBL_USU_LOCAL L
        WHERE l.cod_grupo_cia = cCod_Grupo_Cia_in AND l.cod_local = cCod_Local_in AND L.EST_USU = 'A') Q2,
     (SELECT L.COD_GRUPO_CIA, L.COD_LOCAL, L.SEC_USU_LOCAL, L.NOM_USU, L.APE_PAT, L.APE_MAT
        FROM PBL_USU_LOCAL L
        WHERE l.cod_grupo_cia = cCod_Grupo_Cia_in AND l.cod_local = cCod_Local_in AND L.EST_USU = 'A') Q1
    WHERE F.cod_grupo_cia = cCod_Grupo_Cia_in
      AND F.cod_local = cCod_Local_in
      AND F.COD_GRUPO_CIA = Q2.COD_GRUPO_CIA
      AND F.COD_LOCAL = Q2.COD_LOCAL
      AND F.COD_GRUPO_CIA = Q1.COD_GRUPO_CIA
      AND F.COD_LOCAL = Q1.COD_LOCAL
      AND F.sec_usu_destino = Q2.SEC_USU_LOCAL
      AND F.SEC_USU_origen = Q1.SEC_USU_LOCAL
      AND F.COD_GRUPO_CIA  = M.COD_GRUPO_CIA (+)
      AND F.COD_LOCAL  = M.COD_LOCAL (+)
      AND F.SEC_MOV_CAJA  = M.SEC_MOV_CAJA (+)
      AND F.FEC_CREA_FONDO_SEN BETWEEN SYSDATE-30
                             AND SYSDATE
      AND F.IND_TIPO_FONDO_SEN LIKE vTipo
    ORDER BY TO_CHAR(F.SEC_FONDO_SEN,'0000000000') DESC;
  RETURN curListaHistorico;
  END;
    /* ****************************************************************/

  FUNCTION FONDO_SEN_F_CUR_LIS_HIST_FE (cCod_Grupo_Cia_in CHAR,
                                      cCod_Local_in CHAR,
                                      cFecha_Ini_in CHAR,
                                      cFecha_Fin_in CHAR,
                                      cTipo_in CHAR DEFAULT 'T')
  RETURN FarmaCursor IS
   curListaHistorico FarmaCursor;
   vTipo CHAR(1) := '%';
  BEGIN
  IF cTipo_in = 'T' THEN
         vTipo := '%';
      ELSE
         vTipo := cTipo_in;
      END IF;
    OPEN curListaHistorico FOR
    SELECT TO_CHAR(F.FEC_CREA_FONDO_SEN,'DD/MM/YYYY HH24:MI:SS') || 'Ã' ||  --FECHA
           DECODE(F.IND_TIPO_FONDO_SEN,'D','DEVUELVE','A','ASIGNA') || 'Ã' || --TIPO
           (Q1.NOM_USU||' '||Q1.APE_PAT||' '||Q1.APE_MAT) || 'Ã' || --REMITE
           (Q2.NOM_USU||' '||Q2.APE_PAT||' '||Q2.APE_MAT) || 'Ã' || --EMITE
           TO_CHAR(F.MONTO,'9990.00') || 'Ã' || --MONTO
           NVL(DECODE(F.ESTADO,'E','EMITIDO','A','ACEPTADO','R','RECHAZADO','N','ANULADO'),'') || 'Ã' || --ESTADO
           nvl(M.NUM_CAJA_PAGO,0)  || 'Ã' || --CAJA
           nvl(M.NUM_TURNO_CAJA,0) || 'Ã' || --TURNO
           f.sec_fondo_sen || 'Ã' || --sec_fondo
           F.SEC_USU_origen || 'Ã' ||
           F.sec_usu_destino || 'Ã' ||
           F.IND_TIPO_FONDO_SEN || 'Ã' ||
           F.ESTADO EST
    FROM CE_FONDO_SENCILLO F,
         CE_MOV_CAJA M,
     (SELECT L.COD_GRUPO_CIA, L.COD_LOCAL, L.SEC_USU_LOCAL, L.NOM_USU, L.APE_PAT, L.APE_MAT
        FROM PBL_USU_LOCAL L
        WHERE l.cod_grupo_cia = cCod_Grupo_Cia_in AND l.cod_local = cCod_Local_in AND L.EST_USU = 'A') Q2,
     (SELECT L.COD_GRUPO_CIA, L.COD_LOCAL, L.SEC_USU_LOCAL, L.NOM_USU, L.APE_PAT, L.APE_MAT
        FROM PBL_USU_LOCAL L
        WHERE l.cod_grupo_cia = cCod_Grupo_Cia_in AND l.cod_local = cCod_Local_in AND L.EST_USU = 'A') Q1
    WHERE F.cod_grupo_cia = cCod_Grupo_Cia_in
      AND F.cod_local = cCod_Local_in
      AND F.COD_GRUPO_CIA = Q2.COD_GRUPO_CIA
      AND F.COD_LOCAL = Q2.COD_LOCAL
      AND F.COD_GRUPO_CIA = Q1.COD_GRUPO_CIA
      AND F.COD_LOCAL = Q1.COD_LOCAL
      AND F.sec_usu_destino = Q2.SEC_USU_LOCAL
      AND F.SEC_USU_origen = Q1.SEC_USU_LOCAL
      AND F.COD_GRUPO_CIA  = M.COD_GRUPO_CIA (+)
      AND F.COD_LOCAL  = M.COD_LOCAL (+)
      AND F.SEC_MOV_CAJA  = M.SEC_MOV_CAJA (+)
      AND F.IND_TIPO_FONDO_SEN LIKE vTipo
      AND F.FEC_CREA_FONDO_SEN BETWEEN TO_DATE(cFecha_Ini_in||' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                             AND TO_DATE(cFecha_Fin_in||' 23:59:59','DD/MM/YYYY HH24:MI:SS')
    ORDER BY TO_CHAR(F.SEC_FONDO_SEN,'0000000000') DESC;

  RETURN curListaHistorico;
  END;

  /* ****************************************************************/

  FUNCTION FONDO_SEN_F_IND_TIENE_FONDO(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cSec_Usu_Caj_in CHAR)
  RETURN CHAR IS
   vIndTiene CHAR(1) := 'N';

   vCantRechazo NUMBER := -1;
   vCantAsignado NUMBER := -1;
  BEGIN


  SELECT COUNT(1)
  INTO   vCantAsignado
  FROM   ce_fondo_sencillo f
  WHERE  f.cod_grupo_cia = cCod_Grupo_Cia_in
  AND    f.cod_local = cCod_Local_in
  AND    f.sec_usu_destino = cSec_Usu_Caj_in
  AND    f.estado = 'E'
  AND    F.IND_TIPO_FONDO_SEN = 'A'
  AND    f.sec_mov_caja IS NULL;

  IF vCantAsignado > 0 THEN
     vIndTiene := 'S';
  END IF;

  RETURN vIndTiene;

  /*
  SELECT COUNT(1) INTO vCantRechazo
    FROM PBL_USU_LOCAL L
   WHERE L.COD_GRUPO_CIA = cCod_Grupo_Cia_in
     AND L.COD_LOCAL = cCod_Local_in
     AND L.SEC_USU_LOCAL = cSec_Usu_Caj_in
     AND L.IND_FONDO_RECHAZA = 'S';

  -- >1
   SELECT COUNT(1) INTO vCantAsignado
    FROM PBL_USU_LOCAL L, PBL_ROL_USU RU
   WHERE L.COD_GRUPO_CIA = cCod_Grupo_Cia_in
     AND L.COD_LOCAL = cCod_Local_in
     AND L.EST_USU = cEst_Activo
     AND L.COD_GRUPO_CIA = RU.COD_GRUPO_CIA
     AND L.COD_LOCAL = RU.COD_LOCAL
     AND RU.COD_ROL = cRol_Cajero
     AND L.SEC_USU_LOCAL = RU.SEC_USU_LOCAL
     AND l.sec_usu_local = cSec_Usu_Caj_in
     AND l.cod_local||l.sec_usu_local IN
    (
  SELECT cod_local||usu
    FROM
    (--emitidos y aceptados asignados
    SELECT F.COD_LOCAL, F.SEC_FONDO_SEN sec, F.sec_usu_destino usu
      FROM CE_FONDO_SENCILLO F
     WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
     AND F.COD_LOCAL = cCod_Local_in
     AND F.IND_TIPO_FONDO_SEN = 'A'
     AND F.ESTADO IN ('E','A') --emitido, aceptado

    MINUS
    --devuelve
    SELECT f1.cod_local, f1.sec_fondo_sen_origen sec, f1.SEC_USU_origen usu
      FROM ce_fondo_sencillo f1
     WHERE f1.cod_grupo_cia = cCod_Grupo_Cia_in
      AND f1.cod_local = cCod_Local_in
      AND f1.Ind_Tipo_Fondo_Sen = 'D'
      AND f1.estado = 'A'
    ));

   IF(vCantRechazo > 0 AND vCantAsignado > 0) THEN
     vIndTiene := 'S';
   ELSIF(vCantRechazo = 0 AND vCantAsignado > 0) THEN
     vIndTiene := 'S';
   ELSIF(vCantRechazo > 0 AND vCantAsignado = 0) THEN
     vIndTiene := 'R';
   END IF;*/


  END;

  /* ****************************************************************/

  PROCEDURE FONDO_SEN_P_INS_ASIGNA(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       nMonto_in NUMBER,
                                       csec_usu_destino_in CHAR, --destino
                                       cSEC_USU_origen_in CHAR, --origen
                                       cEstado_in CHAR,
                                       cInd_Tipo_Fondo_in CHAR,
                                       cUsu_Crea_in CHAR,
                                       cIp_in VARCHAR2)
  IS
   vSec_Fondo_sen CHAR(10);
  BEGIN
   --obtener numera
    vSec_Fondo_sen := Farma_Utility.OBTENER_NUMERACION(cCod_Grupo_Cia_in, cCod_Local_in, '075');
	  vSec_Fondo_sen := Farma_Utility.COMPLETAR_CON_SIMBOLO(vSec_Fondo_sen, 10, 0, 'I');

   INSERT INTO CE_FONDO_SENCILLO
   (cod_grupo_cia, cod_local, sec_fondo_sen,
   monto, sec_usu_destino , SEC_USU_origen,
   estado, ind_tipo_fondo_sen,  usu_crea_fondo_sen, ip_crea_fondo_sen  )
   VALUES
   (cCod_Grupo_Cia_in, cCod_Local_in, vSec_Fondo_sen, --obtiene Sec Fondo
   nMonto_in, csec_usu_destino_in,cSEC_USU_origen_in,
   cEstado_in, cInd_Tipo_Fondo_in, cUsu_Crea_in, cIp_in);

       Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCod_Grupo_Cia_in,
                                               cCod_Local_in,
                                               '075',
                                               cUsu_Crea_in);

  END;

  /* ****************************************************************/

  FUNCTION FONDO_SEN_F_UPD_ACEPTA_FONDO(cCod_Grupo_Cia_in CHAR,
                                         cCod_Local_in CHAR,
                                         cUsu_in CHAR,
                                         cIp_in VARCHAR2,
                                         cSec_Usu_in CHAR)
  RETURN VARCHAR2
  IS
   vSecFondoSen VARCHAR2(10);
  BEGIN
   BEGIN
     SELECT F.SEC_FONDO_SEN INTO vSecFondoSen
       FROM CE_FONDO_SENCILLO F
      WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
        AND F.COD_LOCAL = cCod_Local_in
        AND F.IND_TIPO_FONDO_SEN = 'A'
        AND F.sec_usu_destino = cSec_Usu_in
        AND F.ESTADO = 'E' FOR UPDATE;

     UPDATE CE_FONDO_SENCILLO F
        SET F.ESTADO = 'A',
            F.USU_MOD_FONDO_SEN = cUsu_in,
            F.FEC_MOD_FONDO_SEN = SYSDATE,
            F.IP_MOD_FONDO_SEN = cIp_in
      WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
        AND F.COD_LOCAL = cCod_Local_in
       -- AND F.SEC_FONDO_SEN = cSec_Fondo_Sen_in
        AND F.IND_TIPO_FONDO_SEN = 'A'
        AND F.sec_usu_destino = cSec_Usu_in
        AND F.ESTADO = 'E';
   EXCEPTION
    WHEN NO_DATA_FOUND THEN
       vSecFondoSen := 'N';
   END;

/*   UPDATE PBL_USU_LOCAL L
      SET L.IND_FONDO_RECHAZA = 'N'
    WHERE L.COD_GRUPO_CIA = cCod_Grupo_Cia_in
      AND L.COD_LOCAL = cCod_Local_in
      AND L.SEC_USU_LOCAL = cSec_Usu_in;
*/

   RETURN vSecFondoSen;

  END;

  /* ****************************************************************/
  PROCEDURE FONDO_SEN_P_UPD_RECHAZO_FONDO(cCod_Grupo_Cia_in CHAR,
                                         cCod_Local_in CHAR,
                                         cUsu_in CHAR,
                                         cIp_in VARCHAR2,
                                         cSec_Usu_in CHAR)
  IS
  BEGIN

   UPDATE CE_FONDO_SENCILLO F
      SET F.ESTADO = 'R',
          F.USU_MOD_FONDO_SEN = cUsu_in,
          F.FEC_MOD_FONDO_SEN = SYSDATE,
          F.IP_MOD_FONDO_SEN = cIp_in
    WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
      AND F.COD_LOCAL = cCod_Local_in
      AND F.sec_usu_destino = cSec_Usu_in
      AND F.IND_TIPO_FONDO_SEN = 'A'
      AND F.ESTADO = 'E';

/*   UPDATE PBL_USU_LOCAL L
      SET L.IND_FONDO_RECHAZA = 'S'
    WHERE L.COD_GRUPO_CIA = cCod_Grupo_Cia_in
      AND L.COD_LOCAL = cCod_Local_in
      AND L.SEC_USU_LOCAL = cSec_Usu_in;*/
  END;

  /* ****************************************************************/
  FUNCTION FONDO_SEN_F_GET_MONTO_ASIGNADO(cCod_Grupo_Cia_in CHAR,
                                          cCod_Local_in CHAR,
                                          cSec_Usu_in CHAR
                                          )
  RETURN CHAR
  IS
   vMontoAsignado NUMBER := 0;
  BEGIN
     BEGIN
     SELECT F.MONTO INTO vMontoAsignado
       FROM CE_FONDO_SENCILLO F
      WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
        AND F.COD_LOCAL = cCod_Local_in
        AND F.sec_usu_destino = cSec_Usu_in
        AND F.IND_TIPO_FONDO_SEN = 'A'
        AND F.ESTADO = 'E';

     EXCEPTION
       WHEN NO_DATA_FOUND THEN
        vMontoAsignado := -1;

       WHEN OTHERS THEN
        vMontoAsignado := -1;
      END;
      RETURN TO_CHAR(vMontoAsignado,'9990.00');
   END;


  /* ****************************************************************/
  PROCEDURE FONDO_SEN_P_UPD_MOV_CAJA(cCod_Grupo_Cia_in CHAR,
                                          cCod_Local_in CHAR,
                                          cSec_usu_in CHAR,
                                          nNumCaj_in CHAR,
                                          cSec_Fondo_Sen_in CHAR,
                                          cUsu_in CHAR,
                                          cIp_in VARCHAR2
                                          )
  IS
    v_cMovOrig CHAR(10);
	  v_dMaxFec  DATE;
  BEGIN
       SELECT MAX(FEC_DIA_VTA)
       INTO   v_dMaxFec
	     FROM   CE_MOV_CAJA
       WHERE  COD_GRUPO_CIA = cCod_Grupo_Cia_in AND
              COD_LOCAL     = cCod_Local_in   AND
	            NUM_CAJA_PAGO = nNumCaj_in      AND
              TIP_MOV_CAJA  = 'A' AND
              sec_usu_local = cSec_usu_in;

       SELECT NVL(MAX(SEC_MOV_CAJA),'0000000001')
       INTO   v_cMovOrig
       FROM   CE_MOV_CAJA
       WHERE  COD_GRUPO_CIA = cCod_Grupo_Cia_in  AND
              COD_LOCAL     = cCod_Local_in    AND
	            NUM_CAJA_PAGO = nNumCaj_in       AND
              TIP_MOV_CAJA  = 'A' AND
              FEC_DIA_VTA   = v_dMaxFec        AND
              sec_usu_local = cSec_usu_in;
--actualizo el campo de sec_mov_caja en CE_fondo_sencillo
   UPDATE CE_FONDO_SENCILLO F
      SET F.SEC_MOV_CAJA = v_cMovOrig,
          F.USU_MOD_FONDO_SEN = cUsu_in,
          F.FEC_MOD_FONDO_SEN = SYSDATE,
          F.IP_MOD_FONDO_SEN = cIp_in
    WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
      AND F.COD_LOCAL = cCod_Local_in
      AND F.SEC_FONDO_SEN = cSec_Fondo_Sen_in
      AND F.IND_TIPO_FONDO_SEN = 'A'
      AND F.sec_usu_destino = cSec_Usu_in;
  END;

  /* ****************************************************************/
  FUNCTION FONDO_SEN_F_IND_TIENE_DEV(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cSec_Usu_Caj_in CHAR,
                                       cSecMovCajaCierre_in VARCHAR2)
  RETURN CHAR
  IS
   vIndGetDevPendiente CHAR(1) := 'N';
   vCantAceptado NUMBER := -1;
   vCantRechazo NUMBER := -1;
   ce_mov_caja_apertura  ce_mov_caja.sec_mov_caja%TYPE;

   vRespuesta CHAR(1);
   secFondoAsig  CE_FONDO_SENCILLO.SEC_FONDO_SEN%TYPE;
   secFondoDev  CE_FONDO_SENCILLO.SEC_FONDO_SEN%TYPE;
   estado_dev CHAR(1);
  BEGIN

  SELECT m.sec_mov_caja_origen
  INTO   ce_mov_caja_apertura
  FROM   ce_mov_caja m
  WHERE  m.cod_grupo_cia = cCod_Grupo_Cia_in
  AND    m.cod_local = cCod_Local_in
  AND    m.sec_mov_caja = cSecMovCajaCierre_in;
--  AND    m.sec_mov_caja_origen = cSecMovCajaCierre_in;

  BEGIN
      SELECT f.sec_fondo_sen
      INTO   secFondoAsig
      FROM   CE_FONDO_SENCILLO f
      WHERE  f.cod_grupo_cia  = cCod_Grupo_Cia_in
      AND    f.cod_local      = cCod_Local_in
      AND    f.sec_mov_caja   = ce_mov_caja_apertura
      AND    F.ESTADO = 'A'
      AND    F.IND_TIPO_FONDO_SEN = 'A';
  EXCEPTION
  WHEN no_data_found THEN
    secFondoAsig := 'N';
  END;

  IF secFondoAsig = 'N' THEN
     vRespuesta := 'N';
  ELSE
   --si tiene fondo de sencillo asignado y aceptado por el turno

      --consulta si declaro la devolucion del dinero
      BEGIN
            SELECT f.sec_fondo_sen,F.ESTADO
            INTO   secFondoDev,estado_dev
            FROM   CE_FONDO_SENCILLO f
            WHERE  f.cod_grupo_cia  = cCod_Grupo_Cia_in
            AND    f.cod_local      = cCod_Local_in
            AND    f.sec_mov_caja   = cSecMovCajaCierre_in
            AND    f.sec_fondo_sen_origen = secFondoAsig
            AND    F.IND_TIPO_FONDO_SEN = 'D';
      EXCEPTION
      WHEN no_data_found THEN
        secFondoDev := 'N';
        estado_dev := 'N';
      END;

      IF secFondoDev = 'N' THEN
         vRespuesta := 'D'; --- se pedira que el cajero devuelva el sencillo
      ELSE

          IF estado_dev = 'E' THEN
             vRespuesta := 'F'; --- se pedira que comunique con el
                               --- quimico para que confirme su devolucion.
          ELSE
             IF estado_dev = 'R' THEN
                vRespuesta := 'R'; --- se pedira que reingrese el monto de sencillo.
             ELSIF estado_dev = 'A' THEN
                   vRespuesta := 'S';
             END IF;
          END IF;

      END IF;


  END IF;

  RETURN vRespuesta;

  END;

  /* ****************************************************************/
  FUNCTION FONDO_SEN_F_CUR_LIS_QF(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR )
  RETURN FarmaCursor
  IS
   curLista FarmaCursor;
  BEGIN
  OPEN curLista FOR
    SELECT L.SEC_USU_LOCAL || 'Ã' || L.NOM_USU  || ' ' ||  L.APE_PAT  || ' ' ||  L.APE_MAT
      FROM PBL_USU_LOCAL L, PBL_ROL_USU RU
     WHERE L.COD_GRUPO_CIA = cCod_Grupo_Cia_in
       AND L.COD_LOCAL = cCod_Local_in
       AND L.EST_USU = cEst_Activo
       AND L.COD_GRUPO_CIA = RU.COD_GRUPO_CIA
       AND L.COD_LOCAL = RU.COD_LOCAL
       AND RU.COD_ROL = cRol_Administrador
       AND L.SEC_USU_LOCAL = RU.SEC_USU_LOCAL
       AND L.sec_usu_local < 900
       AND L.sec_usu_local NOT IN ('000')
       AND L.SEC_USU_LOCAL = RU.SEC_USU_LOCAL;

  RETURN curLista;
  END;

  /* ****************************************************************/
  PROCEDURE FONDO_SEN_P_INS_DEVOL(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       nMonto_in NUMBER,
                                       csec_usu_destino_in CHAR, --destino QF
                                       cSEC_USU_origen_in CHAR, --origen  CJ
                                       cEstado_in CHAR,
                                       cInd_Tipo_Fondo_in CHAR,
                                       cUsu_Crea_in CHAR,
                                       cIp_in VARCHAR2,
                                       cSec_Mov_Caja_cierre_in VARCHAR2)
  IS
   vSec_Fondo_sen ce_fondo_sencillo.sec_fondo_sen%TYPE;
   vSecFondoSen_Origen ce_fondo_sencillo.sec_fondo_sen_origen%TYPE;
   vSecMovCaja_Ori ce_mov_caja.sec_mov_caja_origen%TYPE;
  BEGIN
    SELECT C.SEC_MOV_CAJA_ORIGEN
      INTO vSecMovCaja_Ori
      FROM CE_MOV_CAJA C
     WHERE C.COD_GRUPO_CIA = cCod_Grupo_Cia_in
       AND C.COD_LOCAL = cCod_Local_in
       AND C.SEC_MOV_CAJA = cSec_Mov_Caja_cierre_in;

    SELECT F.SEC_FONDO_SEN
      INTO vSecFondoSen_Origen
      FROM CE_FONDO_SENCILLO F
     WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
       AND F.COD_LOCAL = cCod_Local_in
       AND F.SEC_MOV_CAJA = vSecMovCaja_Ori;
   --obtener numera
    vSec_Fondo_sen := Farma_Utility.OBTENER_NUMERACION(cCod_Grupo_Cia_in, cCod_Local_in, '075');
	  vSec_Fondo_sen := Farma_Utility.COMPLETAR_CON_SIMBOLO(vSec_Fondo_sen, 10, 0, 'I');

   INSERT INTO CE_FONDO_SENCILLO
   (cod_grupo_cia, cod_local, sec_fondo_sen, sec_mov_caja ,
   monto, sec_usu_destino , SEC_USU_origen,
   estado, sec_fondo_sen_origen , ind_tipo_fondo_sen,  usu_crea_fondo_sen, ip_crea_fondo_sen  )
   VALUES
   (cCod_Grupo_Cia_in, cCod_Local_in, vSec_Fondo_sen, cSec_Mov_Caja_cierre_in ,
   nMonto_in, csec_usu_destino_in,cSEC_USU_origen_in,
   cEstado_in, vSecFondoSen_Origen , cInd_Tipo_Fondo_in, cUsu_Crea_in, cIp_in);

       Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCod_Grupo_Cia_in,
                                               cCod_Local_in,
                                               '075',
                                               cUsu_Crea_in);

  END;

  /* ****************************************************************/
  FUNCTION FONDO_SEN_F_GET_MONTO_DEV(cCod_Grupo_Cia_in CHAR,
                                          cCod_Local_in CHAR,
                                          cSec_Usu_in CHAR,
                                          cSec_Mov_Caja_cierre_in VARCHAR2
                                          )
  RETURN CHAR
  IS
   vMontoAsignado NUMBER := 0;
   vSecFondoSen_Origen ce_fondo_sencillo.sec_fondo_sen_origen%TYPE;
   vSecMovCaja_Ori ce_mov_caja.sec_mov_caja_origen%TYPE;
  BEGIN

    SELECT C.SEC_MOV_CAJA_ORIGEN
      INTO vSecMovCaja_Ori
      FROM CE_MOV_CAJA C
     WHERE C.COD_GRUPO_CIA = cCod_Grupo_Cia_in
       AND C.COD_LOCAL = cCod_Local_in
       AND C.SEC_MOV_CAJA = cSec_Mov_Caja_cierre_in;

    SELECT F.SEC_FONDO_SEN
      INTO vSecFondoSen_Origen
      FROM CE_FONDO_SENCILLO F
     WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
       AND F.COD_LOCAL = cCod_Local_in
       AND F.SEC_MOV_CAJA = vSecMovCaja_Ori
       AND F.IND_TIPO_FONDO_SEN = 'A';

     BEGIN
     SELECT F1.MONTO INTO vMontoAsignado
       FROM CE_FONDO_SENCILLO F1
      WHERE F1.COD_GRUPO_CIA = cCod_Grupo_Cia_in
        AND F1.COD_LOCAL = cCod_Local_in
        AND f1.sec_fondo_sen = vSecFondoSen_Origen
        AND F1.IND_TIPO_FONDO_SEN = 'A'
        AND F1.ESTADO = 'A';

     EXCEPTION
       WHEN NO_DATA_FOUND THEN
        vMontoAsignado := -1;

       WHEN OTHERS THEN
        vMontoAsignado := -1;
      END;
      RETURN TO_CHAR(vMontoAsignado,'9990.00');
   END;

  /* ****************************************************************/
    PROCEDURE FONDO_SEN_P_UPD_DEVOL(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       nMonto_in NUMBER,
                                       csec_usu_destino_in CHAR, --destino QF
                                       cSEC_USU_origen_in CHAR, --origen  CJ
                                       cUsu_Crea_in CHAR,
                                       cIp_in VARCHAR2,
                                       cSec_Mov_Caja_cierre_in VARCHAR2)
  IS
   vSecFondoSen_Origen ce_fondo_sencillo.sec_fondo_sen_origen%TYPE;
   vSecMovCaja_Ori ce_mov_caja.sec_mov_caja_origen%TYPE;
  BEGIN

    SELECT F.SEC_FONDO_SEN
      INTO vSecFondoSen_Origen
      FROM CE_FONDO_SENCILLO F
     WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
       AND F.COD_LOCAL = cCod_Local_in
       AND F.SEC_MOV_CAJA = cSec_Mov_Caja_cierre_in
       AND F.IND_TIPO_FONDO_SEN = 'D'
       AND F.ESTADO = 'R';



    UPDATE CE_FONDO_SENCILLO F
      SET F.MONTO = nMonto_in,
          F.ESTADO = 'E',
          F.SEC_USU_DESTINO = csec_usu_destino_in,
          F.SEC_USU_ORIGEN = cSEC_USU_origen_in,
          F.USU_MOD_FONDO_SEN = cUsu_Crea_in,
          F.IP_MOD_FONDO_SEN = cIp_in
    WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
      AND F.COD_LOCAL = cCod_Local_in
      AND F.SEC_FONDO_SEN = vSecFondoSen_Origen;

  END;

  /* ****************************************************************/
    FUNCTION FONDO_SEN_F_ANUL_ASIG(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cUsu_Mod_in CHAR,
                                       cIp_in VARCHAR2,
                                       cSec_Fondo_Sen_in VARCHAR2,
                                       cEstado_in CHAR,
                                       cTipo_in CHAR)
     RETURN CHAR
    IS
     vRpta CHAR(1):= 'N';
     vSecFondoSen_out VARCHAR2(10);
    BEGIN
      BEGIN
      SELECT SEC_FONDO_SEN
        INTO vSecFondoSen_out
        FROM CE_FONDO_SENCILLO F
       WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
         AND F.COD_LOCAL = cCod_Local_in
         AND F.SEC_FONDO_SEN = cSec_Fondo_Sen_in
         AND F.IND_TIPO_FONDO_SEN = cTipo_in
         AND F.ESTADO = 'E'
         FOR UPDATE;

      UPDATE CE_FONDO_SENCILLO F
         SET F.ESTADO = cEstado_in,
             F.USU_MOD_FONDO_SEN = cUsu_Mod_in,
             F.FEC_MOD_FONDO_SEN = SYSDATE,
             F.IP_MOD_FONDO_SEN = cIp_in
       WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
         AND F.COD_LOCAL = cCod_Local_in
         AND F.SEC_FONDO_SEN = cSec_Fondo_Sen_in
         AND F.IND_TIPO_FONDO_SEN = cTipo_in
         AND F.ESTADO = 'E';
       vRpta := 'S';
       EXCEPTION
        WHEN NO_DATA_FOUND THEN
         vRpta := 'N';
       END;
       RETURN vRpta;
    END;

  /* ****************************************************************/
    FUNCTION FONDO_SEN_F_VAR2_IMP_VOUCHER(cGrupoCia_in  IN CHAR,
                                              cCodLocal_in  IN CHAR,
                                              cSecFondoSen_in IN VARCHAR2) RETURN VARCHAR2
    IS
    vMsg_out VARCHAR2(32767) := '';
    vMsg_1 VARCHAR2(32767) := '';
    vCab_TIPO VARCHAR2(10000) := '';

    vSecUsuOri  CE_FONDO_SENCILLO.SEC_USU_ORIGEN%TYPE;
    vSecUsuDest CE_FONDO_SENCILLO.SEC_USU_DESTINO%TYPE;
    vUsuOriLargo VARCHAR(400);
    vUsuDestLargo VARCHAR(400);
    vEstado CHAR(1);
    vTipo CHAR(1);
    vMonto CE_FONDO_SENCILLO.MONTO%TYPE := 0.00;
    vFechaCrea CE_FONDO_SENCILLO.FEC_CREA_FONDO_SEN%TYPE ;
    vFechaModi CE_FONDO_SENCILLO.FEC_MOD_FONDO_SEN%TYPE ;
    vLocal VARCHAR2(200) := '';
    i NUMBER(7) := 0;

   BEGIN

   SELECT O.COD_LOCAL||' '||O.DESC_CORTA_LOCAL
     INTO vLocal
     FROM PBL_LOCAL O
    WHERE O.COD_GRUPO_CIA = cGrupoCia_in
      AND O.COD_LOCAL = cCodLocal_in;

   SELECT F.SEC_USU_DESTINO, F.SEC_USU_ORIGEN, F.IND_TIPO_FONDO_SEN, F.ESTADO,
          F.MONTO, F.FEC_CREA_FONDO_SEN, F.FEC_MOD_FONDO_SEN
     INTO vSecUsuDest, vSecUsuOri, vTipo, vEstado, vMonto,
          vFechaCrea, vFechaModi
     FROM CE_FONDO_SENCILLO F
    WHERE F.COD_GRUPO_CIA = cGrupoCia_in
      AND F.COD_LOCAL = cCodLocal_in
      AND F.SEC_FONDO_SEN = cSecFondoSen_in;

   SELECT L.NOM_USU ||' '|| L.APE_PAT ||' '|| L.APE_MAT
     INTO vUsuDestLargo
     FROM PBL_USU_LOCAL L
    WHERE L.COD_GRUPO_CIA = cGrupoCia_in
      AND L.COD_LOCAL = cCodLocal_in
      AND L.SEC_USU_LOCAL = vSecUsuDest
      AND L.EST_USU = 'A';

   SELECT L.NOM_USU ||' '|| L.APE_PAT ||' '|| L.APE_MAT
     INTO vUsuOriLargo
     FROM PBL_USU_LOCAL L
    WHERE L.COD_GRUPO_CIA = cGrupoCia_in
      AND L.COD_LOCAL = cCodLocal_in
      AND L.SEC_USU_LOCAL = vSecUsuOri
      AND L.EST_USU = 'A';

    IF vTipo = cTipo_Asignacion THEN
       vCab_Tipo := '<tr>'||
                    '<td align="center"><h2>'||vLocal||'</h2>'||
                    '</td></tr>'||
                    '<tr>'||
                    '<td align="center"><h2>ASIGNACI&Oacute;N</h2>'||
                    '</td></tr>';
       IF vEstado = cEst_Activo THEN
          vMsg_1 := '<tr><td class="style9">'||
                    'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
                    'Yo, '||vUsuOriLargo||', Adm. Local,&nbsp <b>ASIGNO</b> '||
--                    '<b>ASIGNO.</b><br>'||
                    'Al Técnico/Cajero, '||vUsuDestLargo||'.<br>'||
                    'El Importe de: S/. '|| vMonto||'  Nuevos Soles.<br><br>'||
                    'Firma Téc./Cajero:&nbsp; ____________________<br><br>'||
                    'Firma Adm. Local:&nbsp;&nbsp;____________________<br><br>'||
--                    '_________________________________________________'||
                    '&nbsp'||
                    '</td></tr>';
       ELSIF vEstado = cEst_Emitido THEN
          vMsg_1 := '<tr><td class="style9">'||
                    'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
                    'Yo, '||vUsuOriLargo||', Adm. Local,&nbsp <b>ASIGNO</b> '||
--                    '<b>ASIGNO.</b><br>'||
                    'Al Técnico/Cajero, '||vUsuDestLargo||'.<br>'||
                    'El Importe de: S/. '|| vMonto||'  Nuevos Soles.<br><br>'||
                    'Firma Téc./Cajero:&nbsp; ____________________<br><br>'||
                    'Firma Adm. Local:&nbsp;&nbsp;____________________<br><br>'||
--                    '_________________________________________________'||
                    '&nbsp'||
                    '</td></tr>';
       END IF;
    ELSIF vTipo = cTipo_Devolucion THEN
       vCab_Tipo := '<tr>'||
                    '<td align="center"><h2>'||vLocal||'</h2>'||
                    '</td></tr>'||
                    '<tr>'||
                    '<td align="center"><h2>DEVOLUCI&Oacute;N</h2>'||
                    '</td>'||
                    '</tr>';
       IF vEstado = cEst_Activo THEN
          vMsg_1 := '<tr><td class="style9">'||
                    'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
                    'Yo, '||vUsuOriLargo||', Técnico/Cajero,&nbsp <b>DEVUELVO</b> '||
--                    '<b>DEVUELVO.</b><br>'||
                    'Al Adm. Local, '||vUsuDestLargo||'.<br>'||
                    'El Importe de: S/. '|| vMonto||'  Nuevos Soles.<br><br>'||
                    'Firma Téc./Cajero:&nbsp; ____________________<br><br>'||
                    'Firma Adm. Local:&nbsp;&nbsp;____________________<br><br>'||
                    '&nbsp'||
                    '</td></tr>';
       ELSIF vEstado = cEst_Emitido THEN
          vMsg_1 := '<tr><td class="style9">'||
                    'Fecha: '||TO_CHAR(vFechaCrea,'DD/MM/YYYY HH24:MI:SS')||'<br><br>'||
                    'Yo, '||vUsuOriLargo||', Técnico/Cajero,&nbsp <b>DEVUELVO</b> '||
--                    '<b>DEVUELVO.</b><br>'||
                    'Al Adm. Local, '||vUsuDestLargo||'.<br>'||
                    'El Importe de S/. '|| vMonto||'  Nuevos Soles.<br><br>'||
                    'Firma Téc./Cajero:&nbsp; ____________________<br><br>'||
                    'Firma Adm. Local:&nbsp;&nbsp;____________________<br><br>'||
                    --'_________________________________________________'||
                    '&nbsp'||
                    '</td></tr>';
       END IF;

    END IF;

    vMsg_out := C_INICIO_MSG || vCab_Tipo || vMsg_1 ||
              C_FIN_MSG;

    RETURN vMsg_out;

    END;
  /* ****************************************************************/
  PROCEDURE FONDO_SEN_P_ACEPTAR_DEVOL(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cUsu_Mod_in CHAR,
                                       cIp_in VARCHAR2,
                                       cSec_Fondo_Sen_in VARCHAR2)
  IS

  BEGIN
/*
    SELECT *
    FROM  CE_FONDO_SENCILLO F
    WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
      AND F.COD_LOCAL = cCod_Local_in
      AND F.SEC_FONDO_SEN = cSec_Fondo_Sen_in;*/

   UPDATE CE_FONDO_SENCILLO F
      SET F.ESTADO = 'A',
          F.USU_MOD_FONDO_SEN = cUsu_Mod_in,
          F.IP_MOD_FONDO_SEN = cIp_in
    WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
      AND F.COD_LOCAL = cCod_Local_in
      AND F.SEC_FONDO_SEN = cSec_Fondo_Sen_in
      AND F.IND_TIPO_FONDO_SEN = 'D';

  END;

  /* ****************************************************************/
  FUNCTION FONDO_SEN_F_IND_HABILITADO
  RETURN CHAR
   IS
   vIndFondo CHAR(1) := 'N';
  BEGIN
   BEGIN
   SELECT L.LLAVE_TAB_GRAL INTO vIndFondo
     FROM PBL_TAB_GRAL L
    WHERE L.ID_TAB_GRAL = '341';
   EXCEPTION
    WHEN NO_DATA_FOUND THEN
     vIndFondo := 'E';
   END;
   RETURN vIndFondo;
  END;

  /* ****************************************************************/
  FUNCTION FONDO_SEN_F_IND_VAL_USU_OK(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cSec_Usu_Local_in CHAR)
  RETURN CHAR
  IS
   vRpta CHAR(1) := 'N';
   vCant NUMBER := 0;
  BEGIN
   BEGIN
    SELECT COUNT(1) INTO vCant
        FROM PBL_USU_LOCAL L, PBL_ROL_USU RU
       WHERE L.COD_GRUPO_CIA = cCod_Grupo_Cia_in
         AND L.COD_LOCAL = cCod_Local_in
         AND L.EST_USU = cEst_Activo
         AND L.COD_GRUPO_CIA = RU.COD_GRUPO_CIA
         AND L.COD_LOCAL = RU.COD_LOCAL
         AND RU.COD_ROL = cRol_Administrador
         AND L.SEC_USU_LOCAL = RU.SEC_USU_LOCAL
         AND L.SEC_USU_LOCAL = cSec_Usu_Local_in;

         IF vCant > 0 THEN
          vRpta := 'S';
         END IF;
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
       vCant := -1;
       vRpta := To_CHar(vCant,'0');
   END;

   RETURN vRpta;
  END;


   /* ****************************************************************/
   FUNCTION FONDO_SEN_F_IND_CAJ_DISP(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cSec_Usu_Caj_in CHAR)
   RETURN CHAR
   IS
    vCantApertura NUMBER := 0;
    vRpta CHAR(1) := 'N';

   BEGIN
     BEGIN

     SELECT COUNT(1) INTO vCantApertura
     FROM
          ( SELECT C.COD_LOCAL, C.SEC_MOV_CAJA, C.SEC_USU_LOCAL FROM ce_mov_caja c
            WHERE c.cod_grupo_cia = cCod_Grupo_Cia_in
            AND c.cod_local = cCod_Local_in
            AND c.TIP_MOV_CAJA = 'A'
            AND C.SEC_USU_LOCAL = cSec_Usu_Caj_in
            MINUS
            SELECT C1.COD_LOCAL, C1.SEC_MOV_CAJA_ORIGEN, C1.SEC_USU_LOCAL FROM ce_mov_caja c1
            WHERE c1.cod_grupo_cia = cCod_Grupo_Cia_in
            AND c1.cod_local = cCod_Local_in
            AND c1.TIP_MOV_CAJA = 'C'
            AND C1.SEC_USU_LOCAL = cSec_Usu_Caj_in
          )
   /*  AND C.SEC_USU_LOCAL IN (SELECT cp.sec_usu_local
                              FROM vta_caja_pago cp
                              WHERE cp.cod_grupo_cia = cCod_Grupo_Cia_in
                              AND cp.cod_local = cCod_Local_in
                              AND cp.est_caja_pago = 'A') */
          ;

         IF vCantApertura > 0 THEN
            vRpta := 'S';
         END IF;

     EXCEPTION
       WHEN NO_DATA_FOUND THEN
        vRpta := 'N';
     END;
     RETURN vRpta;
   END;

   /* ****************************************************************/
   FUNCTION FONDO_SEN_F_VAR2_IMP_VOUCHER_2(cCod_Grupo_Cia_in  IN CHAR,
                                              cCod_Local_in  IN CHAR,
                                              cSecMovCajaCierre_in IN VARCHAR2) RETURN VARCHAR2
   IS
      vSecFondoSen_Origen ce_fondo_sencillo.sec_fondo_sen_origen%TYPE;
      vMsg_out VARCHAR2(32767) := '';
      nCantidad number;
   BEGIN

    SELECT count(1)
      INTO nCantidad
      FROM CE_FONDO_SENCILLO F
     WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
       AND F.COD_LOCAL = cCod_Local_in
       AND F.SEC_MOV_CAJA = cSecMovCajaCierre_in
       AND F.IND_TIPO_FONDO_SEN = 'D';

   if nCantidad > 0 then

    SELECT F.SEC_FONDO_SEN
      INTO vSecFondoSen_Origen
      FROM CE_FONDO_SENCILLO F
     WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
       AND F.COD_LOCAL = cCod_Local_in
       AND F.SEC_MOV_CAJA = cSecMovCajaCierre_in
       AND F.IND_TIPO_FONDO_SEN = 'D';
--       AND F.ESTADO = 'E';

       vMsg_out := PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_VAR2_IMP_VOUCHER(cCod_Grupo_Cia_in,
                                              cCod_Local_in,
                                              vSecFondoSen_Origen);
    else
        vMsg_out := 'N';
    end if;

    RETURN vMsg_out;

   END;
  /* ****************************************************************/
    PROCEDURE FONDO_SEN_P_ELIMINA_DEVOL(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cUsu_Crea_in CHAR,
                                       cIp_in VARCHAR2,
                                       cSec_Mov_Caja_cierre_in VARCHAR2)
  IS
   vSecFondoSen_Origen ce_fondo_sencillo.sec_fondo_sen_origen%TYPE;
   vSecMovCaja_Ori ce_mov_caja.sec_mov_caja_origen%TYPE;
  BEGIN

    SELECT F.SEC_FONDO_SEN
      INTO vSecFondoSen_Origen
      FROM CE_FONDO_SENCILLO F
     WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
       AND F.COD_LOCAL = cCod_Local_in
       AND F.SEC_MOV_CAJA = cSec_Mov_Caja_cierre_in
       AND F.IND_TIPO_FONDO_SEN = 'D';
      -- AND F.ESTADO = 'E';
--inserta el sencillo borrado.
INSERT INTO CE_FONDO_SENCILLO_BORRADO
 ( cod_grupo_cia, cod_local , sec_fondo_sen , sec_mov_caja , monto ,
   sec_usu_destino , sec_usu_origen , estado , sec_fondo_sen_origen,
   ind_tipo_fondo_sen , usu_crea_fondo_sen, fec_crea_fondo_sen  , ip_crea_fondo_sen ,
   usu_mod_fondo_sen , fec_mod_fondo_sen , ip_mod_fondo_sen  )
SELECT F.COD_GRUPO_CIA, F.COD_LOCAL, F.SEC_FONDO_SEN, F.SEC_MOV_CAJA, F.MONTO,
    F.SEC_USU_DESTINO, F.SEC_USU_ORIGEN, F.ESTADO, F.SEC_FONDO_SEN_ORIGEN,
    F.IND_TIPO_FONDO_SEN, F.USU_CREA_FONDO_SEN, F.FEC_CREA_FONDO_SEN, F.IP_CREA_FONDO_SEN,
    cUsu_Crea_in, SYSDATE, cIp_in
     FROM CE_FONDO_SENCILLO F
    WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
      AND F.COD_LOCAL = cCod_Local_in
      AND F.SEC_FONDO_SEN = vSecFondoSen_Origen;
--elimina el sencillo
   DELETE CE_FONDO_SENCILLO F1
    WHERE F1.COD_GRUPO_CIA = cCod_Grupo_Cia_in
      AND F1.COD_LOCAL = cCod_Local_in
      AND F1.SEC_FONDO_SEN = vSecFondoSen_Origen;

  END;
---------------------------------------------
  PROCEDURE FONDO_SEN_P_ACEPTAR_DEVOL_QF(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cUsu_Mod_in CHAR,
                                       cIp_in VARCHAR2,
                                       cSec_Mov_Caja_cierre_in VARCHAR2)
  IS
   vSecFondoSen_Origen ce_fondo_sencillo.sec_fondo_sen%TYPE;
  BEGIN

    SELECT F.SEC_FONDO_SEN
      INTO vSecFondoSen_Origen
      FROM CE_FONDO_SENCILLO F
     WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
       AND F.COD_LOCAL = cCod_Local_in
       AND F.SEC_MOV_CAJA = cSec_Mov_Caja_cierre_in
       AND F.IND_TIPO_FONDO_SEN = 'D'
       AND F.ESTADO = 'E';

   UPDATE CE_FONDO_SENCILLO F
      SET F.ESTADO = 'A',
          F.USU_MOD_FONDO_SEN = cUsu_Mod_in,
          F.IP_MOD_FONDO_SEN = cIp_in
    WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
      AND F.COD_LOCAL = cCod_Local_in
      AND F.SEC_FONDO_SEN = vSecFondoSen_Origen
      AND F.IND_TIPO_FONDO_SEN = 'D';

  END;
---------------------------------------------
  FUNCTION FONDO_SEN_P_FONDO_ACEPTADO(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       cSec_Mov_Caja_cierre_in VARCHAR2)
                                       RETURN CHAR
  IS
   vSecFondoSen ce_fondo_sencillo.sec_fondo_sen%TYPE;
   vCant NUMBER := 0;
   vRpta CHAR(1) := 'N';
  BEGIN
    BEGIN
    SELECT COUNT(1)
      INTO vCant
      FROM CE_FONDO_SENCILLO F
     WHERE F.COD_GRUPO_CIA = cCod_Grupo_Cia_in
       AND F.COD_LOCAL = cCod_Local_in
       AND F.SEC_MOV_CAJA = cSec_Mov_Caja_cierre_in
       AND F.IND_TIPO_FONDO_SEN = 'D'
       AND F.ESTADO = 'A';

       IF (vCant > 0) THEN
         vRpta := 'S';
       END IF;
    EXCEPTION
      WHEN no_data_found THEN
         vRpta := 'N';
    END;
    RETURN vRpta;
  END;

---------------------------------------------
    FUNCTION FONDO_SEN_F_INS_ASIGNA(cCod_Grupo_Cia_in CHAR,
                                       cCod_Local_in CHAR ,
                                       nMonto_in NUMBER,
                                       csec_usu_destino_in CHAR, --destino
                                       cSEC_USU_origen_in CHAR, --origen
                                       cEstado_in CHAR,
                                       cInd_Tipo_Fondo_in CHAR,
                                       cUsu_Crea_in CHAR,
                                       cIp_in VARCHAR2) RETURN VARCHAR2
  IS
   vSec_Fondo_sen CHAR(10);
  BEGIN
   --obtener numera
    vSec_Fondo_sen := Farma_Utility.OBTENER_NUMERACION(cCod_Grupo_Cia_in, cCod_Local_in, '075');
	  vSec_Fondo_sen := Farma_Utility.COMPLETAR_CON_SIMBOLO(vSec_Fondo_sen, 10, 0, 'I');

   INSERT INTO CE_FONDO_SENCILLO
   (cod_grupo_cia, cod_local, sec_fondo_sen,
   monto, sec_usu_destino , SEC_USU_origen,
   estado, ind_tipo_fondo_sen,  usu_crea_fondo_sen, ip_crea_fondo_sen  )
   VALUES
   (cCod_Grupo_Cia_in, cCod_Local_in, vSec_Fondo_sen, --obtiene Sec Fondo
   nMonto_in, csec_usu_destino_in,cSEC_USU_origen_in,
   cEstado_in, cInd_Tipo_Fondo_in, cUsu_Crea_in, cIp_in);

       Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCod_Grupo_Cia_in,
                                               cCod_Local_in,
                                               '075',
                                               cUsu_Crea_in);

   RETURN vSec_Fondo_sen;

  END;

---------------------------------------------
  FUNCTION FONDO_SEN_F_CUR_LIS_H_CAJ (cCod_Grupo_Cia_in CHAR,
                                      cCod_Local_in CHAR,
                                      cTipo_in CHAR DEFAULT 'T',
                                      cSec_Usu_Caj_in CHAR)
  RETURN FarmaCursor IS
   curListaHistorico FarmaCursor;
   vTipo CHAR(1) := '%';
  BEGIN
      IF cTipo_in = 'T' THEN
         vTipo := '%';
      ELSE
         vTipo := cTipo_in;
      END IF;
    OPEN curListaHistorico FOR
    SELECT TO_CHAR(F.FEC_CREA_FONDO_SEN,'DD/MM/YYYY HH24:MI:SS') || 'Ã' ||  --FECHA
           DECODE(F.IND_TIPO_FONDO_SEN,'D','DEVUELVE','A','ASIGNA') || 'Ã' || --TIPO
           (Q1.NOM_USU||' '||Q1.APE_PAT||' '||Q1.APE_MAT) || 'Ã' || --ORIGEN
           (Q2.NOM_USU||' '||Q2.APE_PAT||' '||Q2.APE_MAT) || 'Ã' || --DESTINO
           TO_CHAR(F.MONTO,'9990.00') || 'Ã' || --MONTO
           NVL(DECODE(F.ESTADO,'E','EMITIDO','A','ACEPTADO','R','RECHAZADO','N','ANULADO'),'') || 'Ã' || --ESTADO
           nvl(M.NUM_CAJA_PAGO,0)  || 'Ã' || --CAJA
           nvl(M.NUM_TURNO_CAJA,0) || 'Ã' || --TURNO
           f.sec_fondo_sen || 'Ã' || --sec_fondo
           F.SEC_USU_origen || 'Ã' ||
           F.sec_usu_destino || 'Ã' ||
           F.IND_TIPO_FONDO_SEN || 'Ã' ||
           F.ESTADO EST
    FROM CE_FONDO_SENCILLO F,
         CE_MOV_CAJA M,
     (SELECT L.COD_GRUPO_CIA, L.COD_LOCAL, L.SEC_USU_LOCAL, L.NOM_USU, L.APE_PAT, L.APE_MAT
        FROM PBL_USU_LOCAL L
        WHERE l.cod_grupo_cia = cCod_Grupo_Cia_in AND l.cod_local = cCod_Local_in AND L.EST_USU = 'A') Q2,
     (SELECT L.COD_GRUPO_CIA, L.COD_LOCAL, L.SEC_USU_LOCAL, L.NOM_USU, L.APE_PAT, L.APE_MAT
        FROM PBL_USU_LOCAL L
        WHERE l.cod_grupo_cia = cCod_Grupo_Cia_in AND l.cod_local = cCod_Local_in AND L.EST_USU = 'A') Q1
    WHERE F.cod_grupo_cia = cCod_Grupo_Cia_in
      AND F.cod_local = cCod_Local_in
      AND F.COD_GRUPO_CIA = Q2.COD_GRUPO_CIA
      AND F.COD_LOCAL = Q2.COD_LOCAL
      AND F.COD_GRUPO_CIA = Q1.COD_GRUPO_CIA
      AND F.COD_LOCAL = Q1.COD_LOCAL
      AND F.sec_usu_destino = Q2.SEC_USU_LOCAL
      AND F.SEC_USU_origen = Q1.SEC_USU_LOCAL
      AND F.COD_GRUPO_CIA  = M.COD_GRUPO_CIA (+)
      AND F.COD_LOCAL  = M.COD_LOCAL (+)
      AND F.SEC_MOV_CAJA  = M.SEC_MOV_CAJA (+)
      AND F.FEC_CREA_FONDO_SEN BETWEEN SYSDATE-30
                             AND SYSDATE
      AND F.IND_TIPO_FONDO_SEN LIKE vTipo
      AND (F.SEC_USU_DESTINO = cSec_Usu_Caj_in OR F.SEC_USU_ORIGEN = cSec_Usu_Caj_in)
    ORDER BY to_char(f.sec_fondo_sen,'0000000000') DESC;
  RETURN curListaHistorico;
  END;

    /* ****************************************************************/

  FUNCTION FONDO_SEN_F_CUR_LIS_H_FEC_CAJ (cCod_Grupo_Cia_in CHAR,
                                      cCod_Local_in CHAR,
                                      cFecha_Ini_in CHAR,
                                      cFecha_Fin_in CHAR,
                                      cTipo_in CHAR DEFAULT 'T',
                                      cSec_Usu_Caj_in CHAR)
  RETURN FarmaCursor IS
   curListaHistorico FarmaCursor;
   vTipo CHAR(1) := '%';
  BEGIN
  IF cTipo_in = 'T' THEN
         vTipo := '%';
      ELSE
         vTipo := cTipo_in;
      END IF;
    OPEN curListaHistorico FOR
    SELECT TO_CHAR(F.FEC_CREA_FONDO_SEN,'DD/MM/YYYY HH24:MI:SS') || 'Ã' ||  --FECHA
           DECODE(F.IND_TIPO_FONDO_SEN,'D','DEVUELVE','A','ASIGNA') || 'Ã' || --TIPO
           (Q1.NOM_USU||' '||Q1.APE_PAT||' '||Q1.APE_MAT) || 'Ã' || --REMITE
           (Q2.NOM_USU||' '||Q2.APE_PAT||' '||Q2.APE_MAT) || 'Ã' || --EMITE
           TO_CHAR(F.MONTO,'9990.00') || 'Ã' || --MONTO
           NVL(DECODE(F.ESTADO,'E','EMITIDO','A','ACEPTADO','R','RECHAZADO','N','ANULADO'),'') || 'Ã' || --ESTADO
           nvl(M.NUM_CAJA_PAGO,0)  || 'Ã' || --CAJA
           nvl(M.NUM_TURNO_CAJA,0) || 'Ã' || --TURNO
           f.sec_fondo_sen || 'Ã' || --sec_fondo
           F.SEC_USU_origen || 'Ã' ||
           F.sec_usu_destino || 'Ã' ||
           F.IND_TIPO_FONDO_SEN || 'Ã' ||
           F.ESTADO EST
    FROM CE_FONDO_SENCILLO F,
         CE_MOV_CAJA M,
     (SELECT L.COD_GRUPO_CIA, L.COD_LOCAL, L.SEC_USU_LOCAL, L.NOM_USU, L.APE_PAT, L.APE_MAT
        FROM PBL_USU_LOCAL L
        WHERE l.cod_grupo_cia = cCod_Grupo_Cia_in AND l.cod_local = cCod_Local_in AND L.EST_USU = 'A') Q2,
     (SELECT L.COD_GRUPO_CIA, L.COD_LOCAL, L.SEC_USU_LOCAL, L.NOM_USU, L.APE_PAT, L.APE_MAT
        FROM PBL_USU_LOCAL L
        WHERE l.cod_grupo_cia = cCod_Grupo_Cia_in AND l.cod_local = cCod_Local_in AND L.EST_USU = 'A') Q1
    WHERE F.cod_grupo_cia = cCod_Grupo_Cia_in
      AND F.cod_local = cCod_Local_in
      AND F.COD_GRUPO_CIA = Q2.COD_GRUPO_CIA
      AND F.COD_LOCAL = Q2.COD_LOCAL
      AND F.COD_GRUPO_CIA = Q1.COD_GRUPO_CIA
      AND F.COD_LOCAL = Q1.COD_LOCAL
      AND F.sec_usu_destino = Q2.SEC_USU_LOCAL
      AND F.SEC_USU_origen = Q1.SEC_USU_LOCAL
      AND F.COD_GRUPO_CIA  = M.COD_GRUPO_CIA (+)
      AND F.COD_LOCAL  = M.COD_LOCAL (+)
      AND F.SEC_MOV_CAJA  = M.SEC_MOV_CAJA (+)
      AND F.IND_TIPO_FONDO_SEN LIKE vTipo
      AND F.FEC_CREA_FONDO_SEN BETWEEN TO_DATE(cFecha_Ini_in||' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                             AND TO_DATE(cFecha_Fin_in||' 23:59:59','DD/MM/YYYY HH24:MI:SS')
      AND ((F.SEC_USU_DESTINO = cSec_Usu_Caj_in AND F.IND_TIPO_FONDO_SEN = 'A')
          OR (F.SEC_USU_ORIGEN = cSec_Usu_Caj_in AND F.IND_TIPO_FONDO_SEN = 'D') )
    ORDER BY to_char(f.sec_fondo_sen,'0000000000') DESC;

  RETURN curListaHistorico;
  END;

/* ****************************************************************/

  FUNCTION FONDO_SEN_F_CUR_LIS_CAJ_PRIN (cCod_Grupo_Cia_in CHAR,
                                      cCod_Local_in CHAR)
  RETURN FarmaCursor IS
   curListaCajero FarmaCursor;
  BEGIN
    OPEN curListaCajero FOR

SELECT NVL(UC.NOM_USU||' '||UC.APE_PAT||' '||UC.APE_MAT,' ') || 'Ã' || -- NOMBRE_COMPLETO
       nvl(decode(vg.ind_devuelto,'S','DEVUELTO',vg.estado_fondo),' ') || 'Ã' || --ESTADO_FINAL
       nvl(vg.monto,0) || 'Ã' ||
       nvl(VG.num_caja_pago,0) || 'Ã' ||
       nvl(VG.num_turno_caja,0) || 'Ã' ||
       nvl(TO_CHAR(VG.FEC_CREA_FONDO_SEN,'DD/MM/YYYY HH24:MI:SS'),' ') || 'Ã' ||
       NVL((
       SELECT UQ.LOGIN_USU
       FROM   PBL_USU_LOCAL UQ
       WHERE  UQ.COD_GRUPO_CIA = VG.COD_GRUPO_CIA
       AND    UQ.COD_LOCAL = VG.COD_LOCAL
       AND    UQ.SEC_USU_LOCAL = VG.SEC_USU_CREA
       ),' ') || 'Ã' || --Qf_ASIGNO
       UC.SEC_USU_LOCAL   || 'Ã' ||    -- 7
       UC.NOM_USU  || 'Ã' ||   -- 8
       UC.APE_PAT  || 'Ã' ||   --9
       UC.APE_MAT  || 'Ã' ||   --10
       UC.LOGIN_USU    || 'Ã' ||    --11
       vg.ESTADO       --12
FROM   VTA_CAJA_PAGO CP,
       PBL_USU_LOCAL UC,
       (
            SELECT fc.cod_grupo_cia,fc.cod_local,FC.IND_TIPO_FONDO_SEN, DECODE(FC.ESTADO,'A','ACEPTADO','E','ASIGNADO','N','ANULADO') ESTADO_FONDO,
                   FC.MONTO,FC.SEC_USU_ORIGEN SEC_USU_CREA,FC.SEC_USU_DESTINO SEC_USU_RECIBE,
                   FC.SEC_MOV_CAJA,FC.SEC_FONDO_SEN,FC.SEC_FONDO_SEN_ORIGEN,
                   DECODE((SELECT COUNT(1)
                    FROM   CE_FONDO_SENCILLO FD
                    WHERE  FD.COD_GRUPO_CIA = FC.COD_GRUPO_CIA
                    AND    FD.COD_LOCAL = FC.COD_LOCAL
                    AND    FD.SEC_FONDO_SEN_ORIGEN = FC.SEC_FONDO_SEN
                   ),0,'N','S') IND_DEVUELTO,
                   FC.SEC_MOV_CAJA S_MOV_CAJA_APERTURA,
                   FC.FEC_CREA_FONDO_SEN,
                   mc.num_caja_pago,mc.num_turno_caja,
                   FC.ESTADO
            FROM   CE_FONDO_SENCILLO FC,
                   ce_mov_caja mc
            WHERE  FC.COD_GRUPO_CIA = cCod_Grupo_Cia_in
            AND    FC.COD_LOCAL = cCod_Local_in
            AND    FC.IND_TIPO_FONDO_SEN = 'A'
            AND    FC.ESTADO != 'N'
            AND    FC.SEC_FONDO_SEN NOT IN (
                               SELECT DEV.SEC_FONDO_SEN_ORIGEN
                               FROM   CE_FONDO_SENCILLO DEV
                               WHERE  DEV.COD_GRUPO_CIA = FC.COD_GRUPO_CIA
                               AND    DEV.COD_LOCAL  = FC.COD_LOCAL
                               AND    DEV.IND_TIPO_FONDO_SEN = 'D'
                               AND    DEV.ESTADO = 'A'  -- E ASIGNADO LA DEVOLUCION , A APROBADO LA DEVOL
                               )
            and    mc.cod_grupo_cia(+) = FC.cod_grupo_cia
            and    mc.cod_local(+) = FC.cod_local
            and    mc.sec_mov_caja(+) = FC.SEC_MOV_CAJA
       )vg
WHERE  CP.EST_CAJA_PAGO = 'A'
AND    UC.EST_USU = 'A'
AND    CP.COD_GRUPO_CIA = cCod_Grupo_Cia_in
AND    CP.COD_LOCAL = cCod_Local_in
AND    UC.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
AND    UC.COD_LOCAL = CP.COD_LOCAL
AND    UC.SEC_USU_LOCAL = CP.SEC_USU_LOCAL
and    uC.cod_grupo_cia = vg.cod_grupo_cia(+)
and    uC.cod_local = vg.cod_local(+)
and    uC.sec_usu_local = vg.SEC_USU_RECIBE(+)
ORDER BY 1;
/* SELECT L.NOM_USU ||' '|| L.APE_PAT ||' '|| L.APE_MAT || 'Ã' ||
            NVL(Q.ESTADO,' ')|| 'Ã' || -- ESTADO
            NVL(Q.MONTO,0)|| 'Ã' || -- MONTO
            NVL(Q.SEC_MOV_CAJA,0)|| 'Ã' || -- CAJA
            NVL(Q.NUM_TURNO_CAJA,0)|| 'Ã' || -- TURNO
            NVL(Q.FEC_CREA_FONDO_SEN,NULL)|| 'Ã' || -- FECHA CREACION
            NVL(DECODE(Q.IND_TIPO_FONDO_SEN,'A',Q.SEC_USU_ORIGEN,'D',Q.SEC_USU_DESTINO),' ')|| 'Ã' || -- ADM LOCAL
            L.SEC_USU_LOCAL
       FROM PBL_USU_LOCAL L, PBL_ROL_USU RU,
       --
            (SELECT F.COD_GRUPO_CIA,
                   F.COD_LOCAL,
                   F.SEC_USU_DESTINO,
                   F.SEC_USU_ORIGEN,
                   F.ESTADO "COD_ESTADO",
                   DECODE(F.ESTADO,'A','ACEPTADO','E','EMITIDO','N','ANULADO') "ESTADO",
                   F.MONTO,
                   DECODE(F.IND_TIPO_FONDO_SEN,'A','ASIGNA','D','DEVOLUCION') "TIPO",
                   F.IND_TIPO_FONDO_SEN,
                   F.SEC_MOV_CAJA,
                   M.NUM_TURNO_CAJA,
                   F.FEC_CREA_FONDO_SEN
              FROM CE_FONDO_SENCILLO F, CE_MOV_CAJA M,
              (
              --FONDO DE SENCILLO ASIGNADO
              SELECT A.COD_GRUPO_CIA,
                     A.COD_LOCAL,
                     A.SEC_USU_DESTINO "SEC_USU",
                     A.SEC_FONDO_SEN
                FROM CE_FONDO_SENCILLO A
               WHERE A.COD_GRUPO_CIA = cCod_Grupo_Cia_in
                 AND A.COD_LOCAL = cCod_Local_in
                 AND A.IND_TIPO_FONDO_SEN = 'A' --ASIGNA
                 AND A.ESTADO NOT IN ('N')
                 AND A.FEC_CREA_FONDO_SEN BETWEEN TRUNC(SYSDATE -30) AND SYSDATE
              MINUS
              --FONDO DE SENCILLO DEVOLUCION
              SELECT B.COD_GRUPO_CIA,
                     B.COD_LOCAL,
                     B.SEC_USU_ORIGEN "SEC_USU",
                     B.SEC_FONDO_SEN_ORIGEN "SEC_FONDO_SEN"
                FROM CE_FONDO_SENCILLO B
               WHERE B.COD_GRUPO_CIA = cCod_Grupo_Cia_in
                 AND B.COD_LOCAL = cCod_Local_in
                 AND B.IND_TIPO_FONDO_SEN = 'D'
                 AND B.ESTADO IN ('A')
                 AND B.FEC_CREA_FONDO_SEN BETWEEN TRUNC(SYSDATE -31) AND SYSDATE
              ) Q1
             WHERE  F.COD_GRUPO_CIA = Q1.COD_GRUPO_CIA
               AND  F.COD_LOCAL = Q1.COD_LOCAL
               AND  (F.SEC_USU_DESTINO = Q1.SEC_USU OR F.SEC_USU_ORIGEN = Q1.SEC_USU)
               AND F.COD_GRUPO_CIA  = M.COD_GRUPO_CIA (+)
               AND F.COD_LOCAL  = M.COD_LOCAL (+)
               AND F.SEC_MOV_CAJA  = M.SEC_MOV_CAJA (+)
            ) Q
      WHERE L.COD_GRUPO_CIA = cCod_Grupo_Cia_in
        AND L.COD_LOCAL = cCod_Local_in
        AND L.COD_GRUPO_CIA = RU.COD_GRUPO_CIA
        AND L.COD_LOCAL = RU.COD_LOCAL
        AND L.SEC_USU_LOCAL = RU.SEC_USU_LOCAL
        AND RU.COD_ROL = '009' --cajero
        AND l.est_usu = 'A'
        AND L.SEC_USU_LOCAL IN (SELECT cp.sec_usu_local --TIENE CAJA ASIGNADA
                                  FROM vta_caja_pago cp
                                 WHERE cp.cod_grupo_cia = cCod_Grupo_Cia_in
                                   AND cp.cod_local = cCod_Local_in
                                   AND cp.est_caja_pago = 'A')
        AND  L.COD_GRUPO_CIA  =  Q.COD_GRUPO_CIA (+)
        AND  L.COD_LOCAL  = Q.COD_LOCAL (+)
        AND (L.SEC_USU_LOCAL = Q.SEC_USU_DESTINO OR  L.SEC_USU_LOCAL = Q.SEC_USU_ORIGEN )
        ORDER BY 1;  */

      RETURN curListaCajero;
  END;
FUNCTION FSEN_F_GET_MONTO_ACEP(CCODCIA_IN IN  CHAR,
                             CCODLOCAL_IN IN CHAR,
                             CSECMOVCAJA_IN IN CHAR)
RETURN VARCHAR
IS
MONTO_OUT NUMBER(6,2):=0;
SECUENCIAL CHAR(10):='';
BEGIN
    SELECT NVL(A.SEC_MOV_CAJA_ORIGEN,'NO HAY') INTO SECUENCIAL
    FROM CE_MOV_CAJA A
    WHERE A.SEC_MOV_CAJA=CSECMOVCAJA_IN
    AND A.COD_GRUPO_CIA=CCODCIA_IN
    AND A.COD_LOCAL=CCODLOCAL_IN;

    SELECT NVL(C.MONTO,0) INTO MONTO_OUT
    FROM ce_fondo_sencillo c
    WHERE c.sec_mov_caja=SECUENCIAL
    AND c.ind_tipo_fondo_sen='A'
    AND c.estado='A'
    AND C.COD_GRUPO_CIA=CCODCIA_IN
    AND C.COD_LOCAL=CCODLOCAL_IN;

    RETURN ''||MONTO_OUT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
    MONTO_OUT :=0;
    RETURN ''||MONTO_OUT;
END;

/**********************************************************************************************************************************************************/

FUNCTION FSEN_F_OPEN_OR_NOT_OPEN(CCODCIA_IN IN CHAR,
                                 CCODLOCAL_IN IN CHAR,
                                 CSECUSU_IN IN CHAR)
RETURN CHAR
IS
cant NUMBER(3):=0;
indFSEN CHAR(1);
ind CHAR(1):='';
BEGIN
    SELECT COUNT(*) INTO cant
    FROM ce_fondo_sencillo a
    WHERE a.sec_usu_destino=CSECUSU_IN
    AND a.cod_grupo_cia=CCODCIA_IN
    AND a.cod_local=CCODLOCAL_IN
    AND a.estado='E' --emitido
    AND a.Ind_Tipo_Fondo_Sen='A'--asignación
    AND a.sec_mov_caja IS NULL; --porq fue hecho cuando aun no aperturaba caja

    SELECT nvl(trim(b.llave_tab_gral),'N') INTO indFSEN
    FROM pbl_tab_gral b
    WHERE --b.cod_grupo_cia=CCODCIA_IN
    --AND
    b.id_tab_gral='341';

    IF (cant = 1 AND indFSEN='S') OR (cant=0 AND indFSEN='N') THEN
       ind := 'S';
    ELSE
       ind := 'N';
    END IF;
    RETURN ind;
END;

/**********************************************************************************************************************************************************/

FUNCTION FSEN_F_DETERMINAR_NECESIDAD(CCOD_CIA_IN IN CHAR,
                                     CCOD_LOCAL_IN IN CHAR,
                                     CSECUSU_IN IN CHAR,
                                     CSEC_MOV_CAJA_IN IN CHAR)
RETURN CHAR
IS
cant NUMBER(3):=0;
indFSEN CHAR(1);
ind CHAR(1):='';
BEGIN
    SELECT nvl(trim(b.llave_tab_gral),'N') INTO indFSEN
    FROM pbl_tab_gral b
    WHERE --b.cod_grupo_cia=CCOD_CIA_IN
    --AND
    b.id_tab_gral='341';

    SELECT COUNT(*) INTO cant
    FROM ce_forma_pago_entrega a, ce_mov_caja b
    WHERE a.cod_grupo_cia=b.cod_grupo_cia
    AND a.cod_local=b.cod_local
    AND a.sec_mov_caja=b.sec_mov_caja
    AND a.cod_grupo_cia=CCOD_CIA_IN
    AND a.cod_local=CCOD_LOCAL_IN
    AND b.tip_mov_caja='C'
    AND b.sec_usu_local=CSECUSU_IN
    AND b.sec_mov_caja=CSEC_MOV_CAJA_IN
    AND a.cod_forma_pago=FPAGO_FSEN
    AND a.est_forma_pago_ent='A';

    IF (indFSEN='S' AND cant>0) OR (indFSEN='N' AND cant=0) THEN
       ind := 'S';
    ELSE
       ind := 'N';
    END IF;

    RETURN ind;
END;
END PTOVENTA_ADMIN_FONDO_SEN;
/

