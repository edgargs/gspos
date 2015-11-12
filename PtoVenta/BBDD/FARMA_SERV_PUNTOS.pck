CREATE OR REPLACE PACKAGE FARMA_SERV_PUNTOS is

  TYPE FarmaCursor IS REF CURSOR;
  CANT_MAX_MINUTOS_PED_PEND PBL_LOCAL.CANT_MAX_MIN_PED_PENDIENTE%TYPE := 30;
  
  TRX_ENVIADA               CHAR(1) := 'E';
  TRX_DESCARTADA            CHAR(1) := 'D';
  TRX_BLOQUEADA             CHAR(1) := 'B';
  TRX_PENDIENTE             CHAR(1) := 'P';
  
  PEDIDO_COBRADO            CHAR(1) := 'C';
  
  FUNCTION OBTIENE_DATO_LOCAL(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR) RETURN FarmaCursor;
 /* *********************************************************************** */
  FUNCTION F_DNI_USU_LOCAL(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR,
                           cNroPedVta_in   IN CHAR) RETURN VARCHAR2;
 /* *********************************************************************** */
  FUNCTION F_VAR_TARJ_VALIDA(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR,
                             cNumTarj_in     IN CHAR) RETURN VARCHAR2;
 /* *********************************************************************** */
  FUNCTION F_VAR_COD_AUTORIZACION(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR) RETURN VARCHAR2;
 /* *********************************************************************** */                           
  FUNCTION F_VAR_WS_ORBIS(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR) RETURN VARCHAR2;                                                        
 /* *********************************************************************** */
  FUNCTION F_CUR_PEND_AFILIADO(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR) RETURN FarmaCursor;
 /* *********************************************************************** */
  FUNCTION F_CUR_PEND_ANULACION(cCodGrupoCia_in IN CHAR, 
                                cCodLocal_in    IN CHAR) RETURN FarmaCursor;
 /* *********************************************************************** */
  FUNCTION F_CUR_PEND_VENTA(cCodGrupoCia_in IN CHAR, 
                            cCodLocal_in    IN CHAR) RETURN FarmaCursor;                             
 /* *********************************************************************** */                                
  FUNCTION FID_F_GET_APELLIDOS(vNombre_in in varchar2)
    RETURN varchar2; 
 /* *********************************************************************** */                                    
 function FN_NOMBRE_CAMPO(A_CADENA VARCHAR2, TIPO INTEGER DEFAULT 0) RETURN VARCHAR;
 /* *********************************************************************** */                                     
 FUNCTION GET_F_DATOS_CLIENTE(cDniCliente_in PBL_CLIENTE.DNI_CLI%TYPE) 
    RETURN FARMACURSOR;
 /* *********************************************************************** */                                     
  FUNCTION GET_VAR_DNI_CLIENTE(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cNroTarjeta_in  IN CHAR) 
    RETURN VARCHAR2; 
 /* *********************************************************************** */                                           
PROCEDURE P_VTA_OFFLINE(cCodGrupoCia_in   IN CHAR,
                          cCod_Local_in     IN CHAR,
                          cNumPedido_in     IN CHAR,
                          cNumTarjeta_in    IN CHAR,
                          cIndOnline_in     IN CHAR,
                             cIdTrx_in         IN CHAR,
                             cNmAutorizacion_in IN CHAR,
                             cIndCancela in char 
                             ); 
PROCEDURE P_ANULA_OFFLINE(cCodGrupoCia_in   IN CHAR,
                          cCod_Local_in     IN CHAR,
                          cNumPedOriginal_in IN CHAR,
                          cIndOnlineProceso_in in char,
                                cIdTrxAnula_in in char default '.',
                                cNroAutoriza_in in char default '.');
                          
FUNCTION P_AFILIA_OFFLINE(cNroTarjetaPuntos IN FID_TARJETA.COD_TARJETA%TYPE,
                          cNroDocumento_in IN PBL_CLIENTE.DNI_CLI%TYPE,
                          cIndEnvioOrbis  IN CHAR)
    RETURN CHAR;
/* ******LTAVARA 25.05.2015************************************************************ */            

  FUNCTION GET_NUM_PEDIDO_ORIGEN(cCodGrupoCia_in   IN CHAR,
                                 cCod_Local_in     IN CHAR,
                                  cNumPedNC IN VARCHAR) RETURN VARCHAR2;
  
  
  PROCEDURE F_VAR_ACTUALIZA_TRX_PEDIDO(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumPedVta_in   IN CHAR,
                                       cEstadoTrx_in   IN CHAR, 
                                       cAnulacion_in   IN CHAR DEFAULT 'N');
END FARMA_SERV_PUNTOS;
/
CREATE OR REPLACE PACKAGE BODY FARMA_SERV_PUNTOS is
  FUNCTION OBTIENE_DATO_LOCAL(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR) RETURN FarmaCursor IS
    farcur FarmaCursor;
  BEGIN
    OPEN farcur FOR
      SELECT NVL(DESC_CORTA_LOCAL, ' ') || 'Ã' || NVL(DESC_LOCAL, ' ') || 'Ã' ||
             NVL(TIP_LOCAL, ' ') || 'Ã' || NVL(TIP_CAJA, ' ') || 'Ã' ||
             NVL(NOM_CIA, ' ') || 'Ã' || NVL(NUM_RUC_CIA, ' ') || 'Ã' ||
             NVL(CANT_MAX_MIN_PED_PENDIENTE, CANT_MAX_MINUTOS_PED_PEND) || 'Ã' ||
             NVL(RUTA_IMPR_REPORTE, ' ') || 'Ã' ||
             NVL(l.Ind_Habilitado, ' ') || 'Ã' ||
             NVL(L.DIREC_LOCAL_CORTA, ' ')
        FROM PBL_LOCAL L, PBL_CIA C
       WHERE L.COD_CIA = C.COD_CIA
         AND L.COD_GRUPO_CIA = cCodGrupoCia_in
         AND L.COD_LOCAL = cCodLocal_in;
    RETURN farcur;
  END;
 /* *********************************************************************** */
  FUNCTION F_DNI_USU_LOCAL(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR,
                           cNroPedVta_in   IN CHAR) RETURN VARCHAR2
  IS
    vDNI_USU pbl_usu_local.dni_usu%type;
    vSecUsuLocal VTA_PEDIDO_VTA_CAB.SEC_USU_LOCAL%TYPE;
  BEGIN
    BEGIN
    SELECT DISTINCT CAB.SEC_USU_LOCAL 
    INTO vSecUsuLocal
    FROM VTA_PEDIDO_VTA_CAB CAB 
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA_ORIGEN = cNroPedVta_in;
    EXCEPTION
      WHEN OTHERS THEN
        BEGIN
        SELECT CAB.SEC_USU_LOCAL 
        INTO vSecUsuLocal
        FROM VTA_PEDIDO_VTA_CAB CAB 
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CAB.COD_LOCAL = cCodLocal_in
        AND CAB.NUM_PED_VTA = cNroPedVta_in;
        EXCEPTION 
          WHEN OTHERS THEN
            vDNI_USU := '99999999';
        END;
    END;
    
    BEGIN
      select trim(u.dni_usu)
      INTO   vDNI_USU
      from  pbl_usu_local u 
      where  u.cod_grupo_cia = cCodGrupoCia_in
      and    u.cod_local     = cCodLocal_in
      and    u.sec_usu_local = vSecUsuLocal;
    EXCEPTION
      WHEN OTHERS THEN
        vDNI_USU := '99999999';
    END;
	RETURN vDNI_USU;
  END;
 /* *********************************************************************** */
  FUNCTION F_VAR_TARJ_VALIDA(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR,
                             cNumTarj_in     IN CHAR) RETURN VARCHAR2
  IS
    nRetorno VARCHAR2(2) := 'N';
  BEGIN

      select decode(count(1),0,'N','S')
      into   nRetorno 
      from   VTA_RANGO_TARJETA  t
      where  t.estado = 'A'
      AND    T.COD_TIPO_TARJETA = 'CP'
      and   cNumTarj_in between t.desde and t.hasta;

	RETURN nRetorno;
  END;
/* *********************************************************************** */
  FUNCTION F_VAR_COD_AUTORIZACION(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR) RETURN VARCHAR2
  IS
    vKeyOrbis VARCHAR2(4000);
  BEGIN
      select llave_tab_gral
      into   vKeyOrbis
      from   PBL_TAB_GRAL 
      where  id_tab_gral = 410;
	RETURN vKeyOrbis;
  END;     
/* *********************************************************************** */
  FUNCTION F_VAR_WS_ORBIS(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR) RETURN VARCHAR2
  IS
    vWS_Orbis VARCHAR2(4000);
  BEGIN
      select llave_tab_gral
      into   vWS_Orbis
      from   PBL_TAB_GRAL 
      where  id_tab_gral = 409;
	RETURN vWS_Orbis;
  END;         
 /* *********************************************************************** */
FUNCTION F_CUR_PEND_AFILIADO(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR) RETURN FarmaCursor
  IS
    vCur FarmaCursor;
  BEGIN
     OPEN vCur for
      select --T.NUM_PEDIDO,
             --C.NUM_TARJ_PUNTOS ,
             T.DNI_CLI,
             T.COD_TARJETA
      from   FID_TARJETA t
      where  T.IND_ENVIADO_ORBIS = 'P'
      and    t.dni_cli is not null
      order by t.fec_crea_tarjeta ASC;
    RETURN vCur;
    
  END;  
/* *********************************************************************** */  
FUNCTION F_CUR_PEND_ANULACION(cCodGrupoCia_in IN CHAR, 
                              cCodLocal_in    IN CHAR) RETURN FarmaCursor
  IS
    vCur FarmaCursor;
  BEGIN
     OPEN vCur for
      /*select C.NUM_PED_VTA
      from   VTA_PEDIDO_VTA_CAB C
      where  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL = cCodLocal_in
      AND    C.Fec_Ped_Vta >= to_date('01/01/2015','dd/mm/yyyy')
      and    c.est_ped_vta = 'C'
      and    c.num_ped_vta_origen is not null
      AND    C.EST_TRX_ORBIS = 'P'
      AND    C.NUM_TARJ_PUNTOS IS NOT NULL
      ORDER BY C.NUM_PED_VTA ASC;*/
      select DISTINCT C.NUM_PED_VTA_ORIGEN NUM_PED_VTA
      from VTA_PEDIDO_VTA_CAB C
      where C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND C.COD_LOCAL = cCodLocal_in
      AND C.Fec_Ped_Vta >= to_date('01/01/2015', 'dd/mm/yyyy')
      and c.est_ped_vta = PEDIDO_COBRADO
      and c.num_ped_vta_origen is not null
      AND C.EST_TRX_ORBIS = TRX_PENDIENTE
      AND C.NUM_TARJ_PUNTOS IS NOT NULL
      AND C.NUM_PED_VTA_ORIGEN IN (
                  select CAB.NUM_PED_VTA
                  from VTA_PEDIDO_VTA_CAB CAB
                  where CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND CAB.COD_LOCAL = cCodLocal_in
                  AND CAB.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                  and cAB.est_ped_vta = PEDIDO_COBRADO
                  AND CAB.EST_TRX_ORBIS = TRX_ENVIADA
                  );
    RETURN vCur;
    
  END;  
/* *********************************************************************** */    
FUNCTION F_CUR_PEND_VENTA(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR) RETURN FarmaCursor
  IS
    vCur FarmaCursor;
  BEGIN
     OPEN vCur for
      select C.NUM_PED_VTA,
             C.NUM_TARJ_PUNTOS,
             to_char(C.FEC_PED_VTA,'dd/mm/yyyy') as "FEC_PED_VTA",
             TRIM(TO_CHAR(C.VAL_NETO_PED_VTA,'99999990.00')) AS "VAL_NETO_PED_VTA",
             trim(to_char(nvl(c.pt_redimido,0)+
                 	-- SE SUMA LOS PUNTOS EXTRAS >> CONVERSION DE AHORRO sOLES DE CAMPANA A PUNTOS AHORRO 
                  -- 27.03.2015 DUBILLUZ
                  NVL(
                  (
                  -- KMONCADA 11.08.2015 SE AGREGA EL AHORRO DE PACK PARA PTOS EXTRAS
                  SELECT SUM(NVL(D.PTOS_AHORRO,0) + NVL(D.PTOS_AHORRO_PACK,0))
                  FROM   VTA_PEDIDO_VTA_DET D
                  WHERE  D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                  AND    D.COD_LOCAL    = C.COD_LOCAL
                  AND    D.NUM_PED_VTA  = C.NUM_PED_VTA
                  ),0)
                  -- 27.03.2015 DUBILLUZ                  
                 	-- SE SUMA LOS PUNTOS EXTRAS >> CONVERSION DE AHORRO sOLES DE CAMPANA A PUNTOS AHORRO                   
              ,'99999990.00')) as "PTO_REDIMIDO"
      from   VTA_PEDIDO_VTA_CAB C
      where  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL = cCodLocal_in
      AND    C.Fec_Ped_Vta >= to_date('01/01/2015','dd/mm/yyyy')
      and    c.est_ped_vta = 'C'
      and    c.num_ped_vta_origen is null
      and    c.num_tarj_puntos is not null
      AND    C.EST_TRX_ORBIS = 'P'
      ORDER BY C.NUM_PED_VTA ASC;
    RETURN vCur;
    
  END;    
/* ************************************************************************* */
  FUNCTION FID_F_GET_APELLIDOS(vNombre_in in varchar2)
    RETURN varchar2 IS

    apellidos varchar2(100) := '';
     apePaterno varchar2(50) := '';
     apeMaterno varchar2(50) := '';
  BEGIN

     apePaterno := FN_NOMBRE_CAMPO(nvl(vNombre_in,' '), 0);
     apeMaterno := FN_NOMBRE_CAMPO(nvl(vNombre_in,' '), 1);

     apellidos := trim(apePaterno || ' ' || apeMaterno);

    RETURN apellidos;
  END;
/* ************************************************************************* */    
function FN_NOMBRE_CAMPO(A_CADENA VARCHAR2, TIPO INTEGER DEFAULT 0) RETURN VARCHAR
IS
C_CADENA VARCHAR2(2000) := A_CADENA;--TRIM(UPPER(FN_REDUCE(A_CADENA => A_CADENA)));
A_APE_PAT VARCHAR2(2000);
A_APE_MAT VARCHAR2(2000);
A_NOMBRES VARCHAR2(2000);
--C_CADENA
P_POS_I NUMBER := 1;
P_POS_F NUMBER;
P_POS NUMBER := 1;
BEGIN
P_POS_F := INSTR(C_CADENA, ' ', 1, P_POS);
IF P_POS_F = 0 THEN
P_POS_F := LENGTH(C_CADENA);
END IF;
A_APE_PAT := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F));

IF A_APE_PAT IN ( 'DEL', 'LA', 'DE') THEN
P_POS := P_POS + 1;
P_POS_F := INSTR(C_CADENA, ' ', 1, P_POS);
A_APE_PAT := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F));
END IF;

IF A_APE_PAT = 'DE LA' THEN
P_POS := P_POS + 1;
P_POS_F := INSTR(C_CADENA, ' ', 1, P_POS);
A_APE_PAT := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F));
END IF;

----
P_POS := P_POS + 1;
P_POS_I := P_POS_F;
P_POS_F := INSTR(C_CADENA, ' ', 1, P_POS );
IF P_POS_F = 0 THEN
P_POS_F := LENGTH(C_CADENA);
END IF;
A_APE_MAT := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F - LENGTH(A_APE_PAT)));

----------------------------------------------------------------------------------
IF A_APE_MAT IN ( 'DEL', 'LA', 'DE') THEN
P_POS := P_POS + 1;
P_POS_F := INSTR(C_CADENA, ' ', 1, P_POS);
A_APE_MAT := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F - LENGTH(A_APE_PAT)));
END IF;

IF A_APE_MAT = 'DE LA' THEN
P_POS := P_POS + 1;
P_POS_F := INSTR(C_CADENA, ' ', 1, P_POS);
A_APE_MAT := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F - LENGTH(A_APE_PAT)));
END IF;
----------------------------------------------------------------------------------
P_POS_I := P_POS_F;
P_POS_F := LENGTH(C_CADENA);
IF P_POS_I != P_POS_F THEN
A_NOMBRES := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F));
END IF;

A_APE_PAT := SUBSTR(A_APE_PAT, 1, 20);
A_APE_MAT := SUBSTR(A_APE_MAT, 1, 20);
A_NOMBRES := SUBSTR(A_NOMBRES, 1, 20);
IF TIPO = 0 THEN
   RETURN A_APE_PAT;
END IF;
IF TIPO = 1 THEN
   RETURN A_APE_MAT ;
END IF;
IF TIPO = 2 THEN
   RETURN A_NOMBRES ;
END IF;
EXCEPTION
WHEN OTHERS THEN
NULL;
END;
/* ***************************************************************** */
FUNCTION GET_F_DATOS_CLIENTE(cDniCliente_in PBL_CLIENTE.DNI_CLI%TYPE) 
    RETURN FARMACURSOR IS
    vCliente FARMACURSOR;
  BEGIN
    
    OPEN vCliente FOR
      SELECT NVL(A.DNI_CLI, ' ') DNI,
             NVL(A.NOM_CLI, ' ') NOMBRE,
             NVL(A.APE_PAT_CLI, ' ') APE_PATERNO,
             NVL(A.APE_MAT_CLI, ' ') APE_MATERNO,
             CASE 
               WHEN A.FEC_NAC_CLI IS NULL THEN 
                 ' '
               ELSE
                 TO_CHAR(A.FEC_NAC_CLI,'DD/MM/YYYY') 
             END FECHA_NAC,
             NVL(A.SEXO_CLI, ' ') SEXO,
             NVL(A.DIR_CLI, ' ') DIRECCION,
             NVL(A.FONO_CLI||'', ' ') TELEFONO,
             NVL(A.EMAIL, ' ') CORREO,
             NVL(A.CELL_CLI||'', ' ') CELULAR,
             NVL(A.DEPARTAMENTO, ' ') DEPARTAMENTO,
             NVL(A.PROVINCIA, ' ') PROVINCIA,
             NVL(A.DISTRITO, ' ') DISTRITO,
             NVL(A.TIPO_DIRECCION, ' ') TIPO_DIRECCION,
             NVL(A.REFERENCIAS, ' ') REFERENCIA,
             NVL(A.TIPO_LUGAR, ' ') TIPO_LUGAR
      FROM PBL_CLIENTE A
      WHERE A.DNI_CLI = cDniCliente_in;
    
    RETURN vCliente;
  END;



  FUNCTION GET_VAR_DNI_CLIENTE(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cNroTarjeta_in  IN CHAR) 
    RETURN VARCHAR2 IS
    cDniCliente FID_TARJETA.DNI_CLI%TYPE;
  BEGIN
    BEGIN
      SELECT FID.DNI_CLI
        INTO cDniCliente
        FROM FID_TARJETA FID
       WHERE FID.COD_LOCAL = cCodLocal_in
         AND FID.COD_TARJETA = cNroTarjeta_in;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        cDniCliente := NULL;
    END;
    
    RETURN cDniCliente;
  END;  

  PROCEDURE P_VTA_OFFLINE(cCodGrupoCia_in   IN CHAR,
                          cCod_Local_in     IN CHAR,
                          cNumPedido_in     IN CHAR,
                          cNumTarjeta_in    IN CHAR,
                          cIndOnline_in     IN CHAR,
                             cIdTrx_in         IN CHAR,
                             cNmAutorizacion_in IN CHAR,
                             cIndCancela in char 
                             ) AS

  begin  
   if cIndOnline_in = 'S' and cIndCancela = 'N' then
     update vta_pedido_vta_cab c
     set    c.fec_proc_puntos = sysdate,
            c.est_trx_orbis   = 'E', --  ENVIADO
            c.usu_mod_ped_vta_cab = 'JOB_OFFLINE',
            C.FEC_MOD_PED_VTA_CAB = SYSDATE,
            c.id_transaccion = cIdTrx_in,
            c.numero_autorizacion = cNmAutorizacion_in
     where  c.cod_grupo_cia = cCodGrupoCia_in
     and    c.cod_local = cCod_Local_in
     and    c.num_ped_vta = cNumPedido_in;     
   else
     if cIndCancela = 'S' then
         update vta_pedido_vta_cab c
         set    c.est_trx_orbis   = 'B', --  ENVIADO
                c.usu_mod_ped_vta_cab = 'JOB_OFFLINE',
                C.FEC_MOD_PED_VTA_CAB = SYSDATE
         where  c.cod_grupo_cia = cCodGrupoCia_in
         and    c.cod_local = cCod_Local_in
         and    c.num_ped_vta = cNumPedido_in;          
     end if;       
   end if;
  
  end;  

PROCEDURE P_ANULA_OFFLINE(cCodGrupoCia_in   IN CHAR,
                          cCod_Local_in     IN CHAR,
                          cNumPedOriginal_in IN CHAR,
                          cIndOnlineProceso_in in char,
                                cIdTrxAnula_in in char default '.',
                                cNroAutoriza_in in char default '.') AS
  /*vRowCab VTA_PEDIDO_VTA_CAB%rowtype;
  begin
    
  SELECT C.*
  into   vRowCab
  FROM   VTA_PEDIDO_VTA_CAB C
  WHERE  C.COD_GRUPO_CIA =  cCodGrupoCia_in
  AND    C.COD_LOCAL = cCod_Local_in
  AND    C.NUM_PED_VTA = cNumPedOriginal_in;
  

      if cIndOnlineProceso_in = 'S' then
          UPDATE vta_pedido_vta_cab C
          SET     c.fec_proc_puntos = sysdate, 
                  c.est_trx_orbis = 'E',
                  C.USU_MOD_PED_VTA_CAB = 'JOB_OFFLINE'
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    C.COD_LOCAL = cCod_Local_in
          AND    C.FEC_PED_VTA >= TRUNC(vRowCab.Fec_Ped_Vta)
          and    c.Num_Ped_Vta_Origen = cNumPedOriginal_in;
      end if;
    */
vRowCab VTA_PEDIDO_VTA_CAB%rowtype;
  begin
    
  SELECT C.*
  into   vRowCab
  FROM   VTA_PEDIDO_VTA_CAB C
  WHERE  C.COD_GRUPO_CIA =  cCodGrupoCia_in
  AND    C.COD_LOCAL = cCod_Local_in
  AND    C.NUM_PED_VTA = cNumPedOriginal_in;
  
    UPDATE vta_pedido_vta_cab C
    SET     c.id_transaccion  = cIdTrxAnula_in,--vRowCab.Id_Transaccion, 
            c.numero_autorizacion = cNroAutoriza_in,--vRowCab.Numero_Autorizacion, 
            c.fec_proc_puntos = null, 
            c.num_tarj_puntos = vRowCab.Num_Tarj_Puntos, 
            c.fec_mod_ped_vta_cab=SYSDATE,
            c.est_trx_orbis = 'P'
    WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    C.COD_LOCAL = cCod_Local_in
    --AND    C.EST_PED_VTA = 'C'
    AND    C.FEC_PED_VTA >= TRUNC(vRowCab.Fec_Ped_Vta)
    and    c.Num_Ped_Vta_Origen = cNumPedOriginal_in;
    
    --- agregar el descartado ojo
    -- veo el estado pedido ORIGINAL 
    if vRowCab.Fec_Proc_Puntos is null then
       -- si esl ORIGINAL es NO ENVIADA A ORBIS
       -- no se proceso el pedido original
       -- entonces la NC o Anulacion TAMPOCO DE EVNIARSE A ORBIS
        UPDATE vta_pedido_vta_cab C
        SET     c.id_transaccion  = cIdTrxAnula_in,--vRowCab.Id_Transaccion, 
                c.numero_autorizacion = cNroAutoriza_in,--vRowCab.Numero_Autorizacion, 
                c.fec_proc_puntos = null, 
                c.num_tarj_puntos = vRowCab.Num_Tarj_Puntos,
                c.fec_mod_ped_vta_cab=SYSDATE, 
                c.est_trx_orbis = 'D'
        WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    C.COD_LOCAL = cCod_Local_in
        --AND    C.EST_PED_VTA = 'C'
        AND    C.FEC_PED_VTA >= TRUNC(vRowCab.Fec_Ped_Vta)
        and    c.Num_Ped_Vta_Origen = cNumPedOriginal_in;

        UPDATE vta_pedido_vta_cab C
        SET     c.id_transaccion  = null,
                c.numero_autorizacion = null,
                c.fec_proc_puntos = null, 
                c.num_tarj_puntos = vRowCab.Num_Tarj_Puntos ,
                c.fec_mod_ped_vta_cab=SYSDATE,
                c.est_trx_orbis = 'D'
        WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    C.COD_LOCAL = cCod_Local_in
        and    c.Num_Ped_Vta = cNumPedOriginal_in;        
       -- AMBAS SE DESCARTAN
    
    --- DEBE DESCARTAR el pedido origen y la NC
    else
      if cIndOnlineProceso_in = 'S' then
          UPDATE vta_pedido_vta_cab C
          SET     c.fec_proc_puntos = sysdate, 
                  c.fec_mod_ped_vta_cab=SYSDATE,
                  c.est_trx_orbis = 'E'
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    C.COD_LOCAL = cCod_Local_in
          --AND    C.EST_PED_VTA = 'C'
          AND    C.FEC_PED_VTA >= TRUNC(vRowCab.Fec_Ped_Vta)
          and    c.Num_Ped_Vta_Origen = cNumPedOriginal_in;
      end if;
    end if;  
        
  end;   

FUNCTION P_AFILIA_OFFLINE(cNroTarjetaPuntos IN FID_TARJETA.COD_TARJETA%TYPE,
                          cNroDocumento_in IN PBL_CLIENTE.DNI_CLI%TYPE,
                          cIndEnvioOrbis  IN CHAR)
    RETURN CHAR IS
    PRAGMA AUTONOMOUS_TRANSACTION;
  BEGIN
    
    UPDATE PBL_CLIENTE A
    SET A.IND_ENVIADO_ORBIS = cIndEnvioOrbis,
        A.FEC_MOD_CLIENTE=SYSDATE
    WHERE A.DNI_CLI = cNroDocumento_in;
    
    UPDATE FID_TARJETA A
       SET A.IND_ENVIADO_ORBIS = cIndEnvioOrbis,
            A.FEC_MOD_TARJETA=SYSDATE
     WHERE A.COD_TARJETA = cNroTarjetaPuntos
       AND A.DNI_CLI = cNroDocumento_in;
    COMMIT;
    
    RETURN 'S';
  END;   
 /* *********************************************************************** */
  FUNCTION GET_NUM_PEDIDO_ORIGEN(cCodGrupoCia_in   IN CHAR,
                                 cCod_Local_in     IN CHAR,
                                  cNumPedNC IN VARCHAR) RETURN VARCHAR2
  IS
    nPedOrigen VTA_PEDIDO_VTA_CAB.Num_Ped_Vta_Origen%type default '0';
  BEGIN

 SELECT NVL(C.NUM_PED_VTA_ORIGEN,0)
  into   nPedOrigen
  FROM   VTA_PEDIDO_VTA_CAB C
  WHERE  C.COD_GRUPO_CIA =  cCodGrupoCia_in
  AND    C.COD_LOCAL = cCod_Local_in
  AND    C.NUM_PED_VTA = cNumPedNC;

  RETURN nPedOrigen;
  END;
  
  PROCEDURE F_VAR_ACTUALIZA_TRX_PEDIDO(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumPedVta_in   IN CHAR,
                                       cEstadoTrx_in   IN CHAR, 
                                       cAnulacion_in   IN CHAR DEFAULT 'N')
  IS
  BEGIN
    IF cAnulacion_in = 'N' THEN
      UPDATE VTA_PEDIDO_VTA_CAB CAB
      SET CAB.EST_TRX_ORBIS = cEstadoTrx_in
      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND CAB.COD_LOCAL = cCodLocal_in
      AND CAB.NUM_PED_VTA = cNumPedVta_in;
    ELSE
      UPDATE VTA_PEDIDO_VTA_CAB CAB
      SET CAB.EST_TRX_ORBIS = cEstadoTrx_in
      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND CAB.COD_LOCAL = cCodLocal_in
      AND CAB.NUM_PED_VTA_ORIGEN = cNumPedVta_in;
    END IF;
  END;

END FARMA_SERV_PUNTOS;
/
