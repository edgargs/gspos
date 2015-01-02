--------------------------------------------------------
--  DDL for Package PTOVENTA_CONSUMO_AUTOMATICO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CONSUMO_AUTOMATICO" AS

  cCodGrupoCia_in char(3) := '001';
  cCodGrupoInsumo char(03) := '010';
  vseciniajuste   number(6) := 1;
  vcero           number(6) := 0;

  TIP_DOC_KARDEX_AJUSTE CHAR(2) := '03';
  USU_CONSUMO_AUTO      VARCHAR2(15) := 'CONSUMO_AUTO';
  MOT_AJUSTE_AUTO       VARCHAR2(120) := 'SALIDA DE INSUMOS';
  c_COD_MOT_AJUST_AUTO  CHAR(3) := '522'; -- SALIDA DE INSUMOS.

  VALOR_NO CHAR(1) := 'N';
  VALOR_SI CHAR(1) := 'S';

  ESTADO_ACTIVO    CHAR(1) := 'A';
  ESTADO_ACTIVO_NO CHAR(1) := 'I';

  v_num_dia number := 30;

  v_SYSDATE date := sysdate;
  
  error     varchar2(10000):='';
  
  v_usu_cargo varchar2(10) := 'PMATUTE';
  v_correo    varchar2(1000) := 'pyovera@mifarma.com.pe,desarrollo4@mifarma.com.pe';

  /*
  TYPE TAB_COD_PROD IS TABLE OF RT_AJUSTE_PROD INDEX BY BINARY_INTEGER;
  
  TYPE RT_AJUSTE_PROD IS RECORD(
      COD_PROD TMP_LGT_AJUSTE_AUTO.COD_PROD%type ,
      COD_LOCAL TMP_LGT_AJUSTE_AUTO.COD_LOCAL%Type
      );*/
  TYPE TAB_COD_PROD IS TABLE OF TMP_LGT_AJUSTE_AUTO.COD_PROD%TYPE INDEX BY BINARY_INTEGER;

  -- Creación           :  RHERRERA
  -- Motivo             : Lógica del Ajuste automático.
  -- Fecha              : 25.09.2014
  -- Requerimiento      : Ajuste Autoático de Insumos
  -- Jefe Requriemiento : JOLIVA
  -- Usuario interesado : PYOVERA
  ------------------------------------------------
  -- Usu y Fecha Modific.       :
  -- Motivo                     :
  PROCEDURE P_PROCESAR_AJUSTE;
  ----------------------------------FIN-------------------------------------------------

  -- Creación           : RHERRERA
  -- Motivo             : Actualizar o eliminar el ajuste automatico del producto cuando ocurra un
  --                      ajuste realizado por el usuario del FarmaVenta.
  -- Fecha              : 25.09.2014
  -- Requerimiento      : Ajuste Autoático de Insumos
  -- Jefe Requriemiento : JOLIVA
  -- Usuario interesado : PYOVERA
  ------------------------------------------------
  -- Usu y Fecha Modific.       :
  -- Motivo                     :

  PROCEDURE P_LIBERAR_AJUSTE(cCodLocaL CHAR, cCodProd CHAR);

  ----------------------------------FIN-------------------------------------------------

  -- Creación           : RHERRERA
  -- Motivo             : Genera un Correo de alerta, con producto que no cuentan con STOCK
  --                      para ajustar en el LOCAL.
  -- Fecha              : 29.09.2014
  -- Requerimiento      : Ajuste Autoático de Insumos
  -- Jefe Requriemiento : JOLIVA
  -- Usuario interesado : PYOVERA
  ------------------------------------------------
  -- Usu y Fecha Modific.       :
  -- Motivo                     :
  PROCEDURE P_EMAIL_CONSUMO_AUTO(cCodLocal  char,
                                 vFecProce  CHAR,
                                 COD_PROD_T TAB_COD_PROD);
  ----------------------------------FIN-------------------------------------------------

  -- Creación           : RHERRERA
  -- Motivo             : Genera un proceso que actualiza la cantidad mensual del ajusta por
  --                      producto
  -- Fecha              : 29.09.2014
  -- Requerimiento      : Ajuste Autoático de Insumos
  -- Jefe Requriemiento : JOLIVA
  -- Usuario interesado : PYOVERA
  ------------------------------------------------
  -- Usu y Fecha Modific.       :
  -- Motivo                     :
  PROCEDURE P_UPDATE_CANT_AJUSTE(cCodLocal char, cCodProd char);
  ----------------------------------FIN-------------------------------------------------

  -- Creación          : RHERRERA
  -- Motivo             : Inserta un nuevo registro en la tabla LGT_AJUSTE_AUTO
  -- Fecha              : 02.10.2014
  -- Requerimiento      : Ajuste Autoático de Insumos
  -- Jefe Requriemiento : JOLIVA
  -- Usuario interesado : PYOVERA
  ------------------------------------------------
  -- Usu y Fecha Modific.       :
  -- Motivo                     :
  PROCEDURE P_INSERT_AJUSTE_AUTO(cCodLocal    CHAR,
                                 cCodProd     CHAR,
                                 vDescProd    VARCHAR2,
                                 vMaxValor    NUMBER,
                                 vuniDescProd VARCHAR2,
                                 cant         NUMBER default 1);
  ----------------------------------FIN-------------------------------------------------
  -- Creación          : RHERRERA
  -- Motivo             : Alerta de error para tema ajuste automático
  -- Fecha              : 17.10.2014
  -- Requerimiento      : Ajuste Autoático de Insumos
  -- Jefe Requriemiento : JOLIVA
  -- Usuario interesado : PYOVERA
  ------------------------------------------------
  -- Usu y Fecha Modific.       :
  -- Motivo                     : 

  PROCEDURE P_EMAIL_ERROR_AJUSTE(cCodLocal char,
                                 vFecProce CHAR,
                                 error     varchar2);
  ----------------------------------FIN-------------------------------------------------                                 

END; --FIN DEL PACKAGE

/
