--------------------------------------------------------
--  DDL for Package PTOVENTA_ADMIN_FONDO_SEN
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_ADMIN_FONDO_SEN" is

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
