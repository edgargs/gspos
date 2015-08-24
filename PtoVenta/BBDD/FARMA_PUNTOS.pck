CREATE OR REPLACE PACKAGE FARMA_PUNTOS is

  TYPE FarmaCursor IS REF CURSOR;
     V_SEPARADOR varchar2(10) := 'ä';    
  
  -- KMONCADA INICIO 27.04.2015
  TIP_VTA_MESON                  CHAR(2) := '01';
  TIP_VTA_DELIVERY               CHAR(2) := '02';
  TIP_VTA_MAYORISTA              CHAR(2) := '03';
  IND_EQUI_ENTERO                CHAR(2) := '10';
  IND_EQUI_FRACCION              CHAR(2) := '20';
  TRUNCATE_DESACTIVADO           CHAR(1) := '0';
  TRUNCATE_CABACERA              CHAR(1) := '1';
  TRUNCATE_DETALLE               CHAR(1) := '2';
  REDIME_NO_ACUMULA              CHAR(1) := '0';
  REDIME_RESTO_ACUMULA           CHAR(1) := '1';
  REDIME_SI_ACUMULA              CHAR(1) := '2';
  IND_SI                         CHAR(1) := 'S';
  IND_NO                         CHAR(1) := 'N';
  IND_ACTIVO                     CHAR(1) := 'A';
  DEFAULT_REQUIERE_TARJETA       CHAR(1) := 'I';
  
  TAB_IND_PANTALLA_BONIFICADO    INTEGER := 459;
  TAB_IND_DOC_BONIFICADO         INTEGER := 460;
  TAB_IND_LECTOR_TARJETA_PISTOLA INTEGER := 464;
  TAB_IND_REDONDEO_CALCULO_PTS   INTEGER := 473;
  TAB_IND_TEXTO_AHORRO           INTEGER := 467;
  TAB_IND_DEFAULT_PTS            INTEGER := 468;
  TAB_IND_DEFAULT_PTS_REDIME     INTEGER := 482;
  TAB_IND_ACUMULACION_REDIME     INTEGER := 492;
  TAB_IND_CALCULO_CON_IGV        INTEGER := 493;
  TAB_IND_CALCULO_PTO_AHORRO     INTEGER := 484;
  TAB_IND_TIPO_ALGORITMO_REDIME  INTEGER := 691;
  TAB_IND_MULTIPLO_REDIME        INTEGER := 682;
  TAB_IND_ACUMULA_PROD_REGALO    INTEGER := 684;
  TAB_IND_ACUMULA_PROD_DSCTO     INTEGER := 683;
  TAB_IND_MSJ_SIN_TARJETA        INTEGER := 681;
  TAB_IND_REQUIERTE_TARJETA      INTEGER := 685;
  TAB_IND_NO_PTOS_POR_PROM_DSCTO INTEGER := 708;
  TAB_IND_MSJ_TARJ_ADICIONAL     INTEGER := 528;
  TAB_IND_VALIDA_DOC_TARJ_ADIC   INTEGER := 526;
  TAB_IND_MSJ_PTOS_PANT_COBRO    INTEGER := 529;
  TAB_IND_BLOQ_ADIC_TARJ_ORBIS   INTEGER := 530;
  TAB_IND_TIEMPO_INICIO_PTOS     INTEGER := 527;
  TAB_IND_QUIMICO_BLOQUEA_TARJ   INTEGER := 715;
  
  -- KMONCADA FIN 27.04.2015
  
 /* *********************************************************************** */
  FUNCTION F_DNI_USU_LOCAL(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR,
                           cSecUsuLocal_in IN CHAR) RETURN VARCHAR2;
 /* *********************************************************************** */
  FUNCTION F_VAR_TARJ_VALIDA(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR,
                             cNumTarj_in     IN CHAR) RETURN VARCHAR2;
 /* *********************************************************************** */
  FUNCTION F_VAR_IND_ACT_PUNTOS(cCodGrupoCia_in IN CHAR, 
                                cCodLocal_in    IN CHAR) RETURN VARCHAR2;     
 /* *********************************************************************** */                                
  FUNCTION F_VAR_IND_IMPR_LOGO(cCodGrupoCia_in IN CHAR, 
                               cCodLocal_in    IN CHAR) RETURN VARCHAR2;                            
 /* *********************************************************************** */
  FUNCTION F_VAR_COD_AUTORIZACION(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR) RETURN VARCHAR2;
 /* *********************************************************************** */                           
  FUNCTION F_VAR_WS_ORBIS(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR) RETURN VARCHAR2;          
 /* *********************************************************************** */                                            
 FUNCTION F_VAR_TIME_OUT_ORBIS(cCodGrupoCia_in IN CHAR, 
                               cCodLocal_in    IN CHAR) RETURN VARCHAR2;                           
 /* *********************************************************************** */
  FUNCTION F_DIAS_HIST_RECUPERAR(cCodGrupoCia_in IN CHAR, 
                                 cCodLocal_in    IN CHAR) RETURN VARCHAR2;
 /* *********************************************************************** */                                                                  
  FUNCTION F_VAR_MSJ_VOUCHER(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR) RETURN VARCHAR2;                                 
 /* *********************************************************************** */                                 
  FUNCTION F_CTD_PED_X_DIAS_ATRAS(cCodGrupoCia_in IN CHAR, 
                                  cCodLocal_in    IN CHAR) RETURN integer;                                 
 /* *********************************************************************** */
  FUNCTION F_CTD_PED_X_DIA_REC(cCodGrupoCia_in IN CHAR, 
                               cCodLocal_in    IN CHAR) RETURN integer;
 /* *********************************************************************** */
  FUNCTION F_CTD_PED_X_MES_REC(cCodGrupoCia_in IN CHAR, 
                               cCodLocal_in    IN CHAR) RETURN integer;
 /* *********************************************************************** */
/*   FUNCTION IMP_SALDO_TARJETA(cCodGrupoCia_in 	IN CHAR,
                              cCodLocal_in    	IN CHAR,
                              cNombres_in       in char,
                              cTarj_in          IN CHAR,
                              cPuntosAcum_in    IN char)
  RETURN FarmaCursor;*/
 /* *********************************************************************** */
/*FUNCTION IMP_RECUPERA_PUNTOS(cCodGrupoCia_in 	IN CHAR,
                              cCodLocal_in    	IN CHAR,
                              cNombres_in       in char,
                              cDNI_in           in char,
                              cTarj_in          IN CHAR,
                              cPuntosAcum_in    IN char,
                              cIndOnline        in char,
                              cPuntosSaldo_in   IN char,
                              cNumPedVta_in    IN char)
  RETURN FarmaCursor;*/  
 /* *********************************************************************** */
  FUNCTION F_GET_PERMITE_RECUPERA(cCodGrupoCia_in   IN CHAR,
                                  cCod_Local_in     IN CHAR,
                                  cTipo_Comp_in     IN CHAR,
                                  cMonto_Neto_in    IN CHAR,
                                  cNum_Comp_Pago_in IN CHAR,
                                  cFec_Ped_Vta_in   IN CHAR,
                                  cNumTarjeta_in    IN CHAR
                                  )
  RETURN VARCHAR2; 
 /* *********************************************************************** */
PROCEDURE P_ASOCIA_TARJ_PED(cCodGrupoCia_in   IN CHAR,
                              cCod_Local_in     IN CHAR,
                              cNumPedido_in     IN CHAR,
                             cNumTarjeta_in    IN CHAR,
                             cIndOnline_in     IN CHAR,
                             cIdTrx_in         IN CHAR,
                             cNmAutorizacion_in IN CHAR);
 /* *********************************************************************** */
FUNCTION F_CUR_PED_ASOC_TARJ_PED(cCodGrupoCia_in IN CHAR, 
                               cCodLocal_in    IN CHAR,
                               cNumPedVta_in   IN CHAR) RETURN FarmaCursor;
 /* *********************************************************************** */
  FUNCTION F_VAR_PED_ASOC_X_MAS_1(cCodGrupoCia_in IN CHAR, 
                               cCodLocal_in    IN CHAR,
                               cNumPedVta_in   IN CHAR) RETURN VARCHAR2    ;  
 /* *********************************************************************** */
 FUNCTION F_VAR_PED_PROC_ORBIS(cCodGrupoCia_in IN CHAR, 
                               cCodLocal_in    IN CHAR,
                               cNumPedVta_in   IN CHAR) RETURN VARCHAR2;
 /* *********************************************************************** */ 
  FUNCTION F_VAR_PED_REDIMIO(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR,
                             cNumPedVta_in   IN CHAR) RETURN VARCHAR2; 
 /* *********************************************************************** */
FUNCTION F_VAR_PED_PUNTOS_ACUM(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR,
                             cNumPedVta_in   IN CHAR) RETURN VARCHAR2;                                                         
 /* *********************************************************************** */
  PROCEDURE P_REVERT_ACUM_PUNTOS(cCodGrupoCia_in   IN CHAR,
                                cCod_Local_in     IN CHAR,
                                cNumPedido_in     IN CHAR);
 /* *********************************************************************** */
  FUNCTION F_CUR_DET_RECUPERA(cCodGrupoCia_in IN CHAR, 
                              cCodLocal_in    IN CHAR,
                              cNumPedVta_in   IN CHAR) RETURN FarmaCursor;
 /* *********************************************************************** */
  FUNCTION F_VAR_PED_ACTUA_PUNTOS(cCodGrupoCia_in IN CHAR, 
                                  cCodLocal_in    IN CHAR,
                                  cNumPedVta_in   IN CHAR) RETURN VARCHAR2;                                                                                          
 /* *********************************************************************** */
FUNCTION F_CUR_DET_ANULA(cCodGrupoCia_in IN CHAR, 
                         cCodLocal_in    IN CHAR,
                         cNumPedVta_in   IN CHAR) RETURN FarmaCursor;
 /* *********************************************************************** */                         
FUNCTION F_VAR_IS_PTO_CERO(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR,
                           cNumPedVta_in   IN CHAR) RETURN VARCHAR2;
 /* *********************************************************************** */
FUNCTION F_VAR_IS_PTO_NULO(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR,
                           cNumPedVta_in   IN CHAR) RETURN VARCHAR2;
 /* *********************************************************************** */                           
FUNCTION F_VAR_IS_PTO_MAYOR_CERO(cCodGrupoCia_in IN CHAR, 
                                cCodLocal_in    IN CHAR,
                                cNumPedVta_in   IN CHAR) RETURN VARCHAR2;
 /* *********************************************************************** */                           
FUNCTION F_VAR_IS_CTD_PTO_PED(cCodGrupoCia_in IN CHAR, 
                              cCodLocal_in    IN CHAR,
                              cNumPedVta_in   IN CHAR) RETURN VARCHAR2;  
 /* *********************************************************************** */                                                         
FUNCTION F_VAR_IS_BONIFICADO(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR,
                             cNumPedVta_in   IN CHAR) RETURN VARCHAR2;
 /* *********************************************************************** */                                                                                      
FUNCTION F_VAR_GET_DATA_TRANSAC_ORBIS(cCodGrupoCia_in IN CHAR, 
                                      cCodLocal_in    IN CHAR,
                                      cNumPedVta_in   IN CHAR) RETURN FarmaCursor;
 /* *********************************************************************** */
PROCEDURE P_SAVE_TRX_ANULA_ORBIS(cCodGrupoCia_in   IN CHAR,
                                cCod_Local_in     IN CHAR,
                                cNumPedOriginal_in IN CHAR,
                                cIndOnlineProceso_in in char,
                                cIdTrxAnula_in in char,
                                cNroAutoriza_in in char) ;                                                       
 /* *********************************************************************** */   
PROCEDURE  P_DESCARTA_TRX_ANULA_ORBIS(cCodGrupoCia_in   IN CHAR,
                                cCod_Local_in     IN CHAR,
                                cNumPedOriginal_in IN CHAR,
                                cIndDescarta_in in char,
                                cIndDescartaOrigen_in in char DEFAULT 'S') ;
                                     

  FUNCTION IMP_LISTA_TIPOS_COMP_RECUPERA(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor;
  
  -- KMONCADA INICIO 27.04.2015
  /**
  DETERMINA LOS PUNTOS A ACUMULAR POR CADA PRODUCTO DEL PEDIDO, SEGUN PARAMETROS DEL PROGRAMA
  DE PUNTOS.
  ----------------------------------------------------------------------------------------------
  ACCION               FECHA      USUARIO    DETALLE
  ----------------------------------------------------------------------------------------------
  CREACION             -          KMONCADA   CREACION
  MODIFICACION         -          KMONCADA   CAMBIO DE TRUNCAMIENTO DE PTOS OBTENIDOS
                                             POR CABECERA, DETALLE.
  MODIFICACION         -          KMONCADA   PTOS PARA EL CASO DE PRODUCTOS QUE BONIFICAN Y 
                                             OBTIENEN PTOS
  MODIFICACION         -          KMONCADA   OBTENCION DE PUNTOS SEGUN CATEGORIA O CODIGO O 
                                             VALORES DEFECTO
  MODIFICACION         -          KMONCADA   CALCULO DE PUNTOS POR PORCENTAJE O VALORES SOLES 
                                             X PTOS
  MODIFICACION         -          KMONCADA   CALCULO DE PUNTOS CON MONTOS SIN IGV O NO 
  MODIFICACION         27.03.2015 KMONCADA   RE-CALCULO DE PUNTOS LUEGO DE REDIMIR PTOS 
                                             SEGUN TAB_GRAL TOTAL/PARCIAL/NULO
  */
  PROCEDURE F_I_CALCULA_PUNTOS(cCodGrupoCia_in    IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                               cCodLocal_in       IN VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                               cNumPedVta_in      IN VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                               cRecalculoPtos_in  IN CHAR DEFAULT 'N',
                               cIndRedime_in      IN CHAR DEFAULT 'N',
                               vValCopago_in     IN NUMBER DEFAULT -1);
  /**
  DETERMINA LISTADO DE PRODUCTOS QUE SE ENVIARAN AL PROGRAMA DE PUNTOS, INCLUYENDO O NO PRODUCTOS
  BONIFICADOS
  ----------------------------------------------------------------------------------------------
  ACCION               FECHA      USUARIO    DETALLE
  ----------------------------------------------------------------------------------------------
  CREACION             -          KMONCADA   CREACION
  MODIFICACION         -          KMONCADA   ENVIO DE CANTIDAD PARA TODOS LOS PRODUCTOS SIN EXCEPCION
  MODIFICACION         -          KMONCADA   LISTA DE PRODUCTOS SIN BONIFICADOS SI FALLA QUOTE
  MODIFICACION         26.03.2015 KMONCADA   CAMBIO DE ESTRUCTURA DE LISTA DE PRODUCTOS
  */
  FUNCTION F_CUR_V_LISTA_PRODUCTO(cCodGrupoCia_in   IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                                  cCodLocal_in      IN VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                                  cNumPedVta_in     IN VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE, 
                                  cConBonificado_in IN CHAR DEFAULT 'S',
                                  cFlag_in          IN CHAR DEFAULT 'N',
                                  cIndAnulaPed_in   IN CHAR DEFAULT 'N')
    RETURN FARMACURSOR;
  
  /**
  OBTIENE INDICADOR PARA MOSTRAR LA PANTALLA DE SELECCION DE PRODUCTOS BONIFICADOS
  ----------------------------------------------------------------------------------------------
  ACCION               FECHA      USUARIO    DETALLE
  ----------------------------------------------------------------------------------------------
  CREACION             -          KMONCADA   CREACION
  */
  FUNCTION F_CHAR_IND_PANTALLA_BONIFICA 
    RETURN CHAR;
    
  /**
  OBTIENE INDICADOR DE LOS DOCUMENTOS QUE SE SOLICITARAN PARA REALIZAR BONIFICACION
  ----------------------------------------------------------------------------------------------
  ACCION               FECHA      USUARIO    DETALLE
  ----------------------------------------------------------------------------------------------
  CREACION             -          KMONCADA   CREACION
  */
  FUNCTION F_CHAR_IND_DOC_BONIFICA
    RETURN CHAR;
    
  FUNCTION F_CHAR_TIEMPO_MAX_LECTORA
    RETURN CHAR;
    
  FUNCTION F_LST_MONTOS_REDENCION(cCodGrupoCia_in IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                                  cCodLocal_in    IN VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                                  cNumPedVta_in   IN VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE)
    RETURN FARMACURSOR;
    
  FUNCTION F_LST_VALIDA_BONIFICADOS(cCodGrupoCia_in IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                               cCodLocal_in    IN VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                               cNumPedVta_in   IN VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                               cListaBonifica_in IN VARCHAR2)
    RETURN FARMACURSOR;
    
  FUNCTION F_N_CALCULA_REDONDEO(nD_in IN number, 
                                nDec_in IN integer)
    RETURN NUMBER;
    
  FUNCTION F_CHAR_REDONDEO_CALCULO_PTOS 
    RETURN CHAR;
  
  FUNCTION F_TEXTO_AHORRO
    RETURN CHAR;
  
  FUNCTION F_IND_ACUMULA_REDIME
    RETURN CHAR;
  
  FUNCTION F_IND_CALCULO_CON_IGV
    RETURN CHAR;
    
  FUNCTION F_VARCHAR_MSJ_SIN_TARJETA
    RETURN VARCHAR2;
    
  /**
  REALIZA LA INTERPRETACION DE LA RESPUESTA DEL QUOTE, DETERMINA PRODUCTOS PROMOCIONES, BONIFICADOS
  ASI COMO LA CANTIDAD DE CADA UNO DE ELLOS.
  ----------------------------------------------------------------------------------------------
  ACCION               FECHA      USUARIO    DETALLE
  ----------------------------------------------------------------------------------------------
  CREACION             -          KMONCADA   CREACION
  */
  FUNCTION F_CHAR_OPERA_QUOTE (cCodGrupoCia_in  IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                               cCodLocal_in     IN VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                               cNumPedVta_in    IN VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                               cLstProdFinal_in IN VARCHAR2)
    RETURN CHAR;
  
  FUNCTION F_VCHAR_OBTIENE_MODIFICADOS(cCodGrupoCia_in IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                      cCodLocal_in    IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                      cNumPedVta_in   IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE)
    RETURN VARCHAR;
  
  FUNCTION F_INT_CANTIDAD_PROMOCIONES(cCodGrupoCia_in IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                      cCodLocal_in    IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                      cNumPedVta_in   IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE)
    RETURN INTEGER;
  FUNCTION F_CHAR_AGREGA_PROMO_BONIF(cCodGrupoCia_in       IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                     cCodLocal_in          IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                     cNumPedVta_in         IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                                     cNumPedVtaOrigen_in   IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE)
    RETURN CHAR;
  FUNCTION F_IND_REQUIERE_TARJETA
    RETURN CHAR;
  -- KMONCADA FIN 27.04.2015
  
  FUNCTION F_CHAR_EXISTE_TARJETA_CLIENTE(cNroDocumento_in IN FID_TARJETA.DNI_CLI%TYPE,
                                         cNroTarjeta_in   IN FID_TARJETA.COD_TARJETA%TYPE)
    RETURN CHAR;
  
  FUNCTION F_IS_TARJ_VALIDA_OTRO_PROGRAMA(cNumTarj_in       IN CHAR,
                                          cIncluidoPtos_in  IN CHAR DEFAULT 'N') 
  RETURN VARCHAR2;
  
  FUNCTION IMP_SALDO_TARJETA(cCodGrupoCia_in 	IN CHAR,
                              cCodLocal_in    	IN CHAR,
                              cNombres_in       in char,
                              cTarj_in          IN CHAR,
                              cPuntosAcum_in    IN char)
  RETURN FarmaCursor;
  
  FUNCTION IMP_RECUPERA_PUNTOS(cCodGrupoCia_in 	IN CHAR,
                              cCodLocal_in    	IN CHAR,
                              cNombres_in       in char,
                              cDNI_in           in char,
                              cTarj_in          IN CHAR,
                              cPuntosAcum_in    IN char,
                              cIndOnline        in char,
                              cPuntosSaldo_in   IN char,
                              cNumPedVta_in    IN char)
  RETURN FarmaCursor;
  
  FUNCTION F_VARCHAR_MSJ_TARJ_ADICIONAL
    RETURN VARCHAR2;
    
  FUNCTION F_CHAR_VALIDA_DOC_TARJ_ADICION
    RETURN VARCHAR2;
    
  FUNCTION F_CHAR_MSJ_PTOS_PANTALLA_COBRO
    RETURN VARCHAR2;
    
  FUNCTION F_CHAR_GET_MSJ_TIEMPO_AHORRO
    RETURN VARCHAR2;
    
  PROCEDURE P_RECHAZO_AFILIACION_PTOS(cCodGrupoCia_in    IN CHAR,
                                      cNumDocumento_in   IN CHAR,
                                      cNumTarjeta_in     IN CHAR,
                                      cSecMovCaja_in     IN CHAR);
  
  FUNCTION F_EVALUA_TARJETA(cNumTarj_in     IN CHAR,
                            cTipoTarjeta_in IN CHAR) 
  RETURN CHAR;

  FUNCTION F_IND_ADICIONA_TARJ_ORBIS
  RETURN CHAR;
  
  FUNCTION F_GET_CALCULO_PAGO_CLIENTE(cCodGrupoCia_in    IN CHAR,
                                      cCodLocal_in       IN CHAR,
                                      cNumPedVta_in      IN CHAR,
                                      cMontoPago_in      IN NUMBER,
                                      nValorSelCopago_in IN NUMBER DEFAULT -1)
  RETURN NUMBER;
  
  FUNCTION F_IS_TARJETA_ASOCIADA(cNroDocumento_in IN FID_TARJETA.DNI_CLI%TYPE,
                                 cNroTarjeta_in   IN FID_TARJETA.COD_TARJETA%TYPE)
    RETURN CHAR;
  FUNCTION F_CLIENTE_AFILIADO_PTOS(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cNroDocumento_in IN VARCHAR2
                                  )
    RETURN CHAR;
  FUNCTION F_VAR_IS_CTD_PTO_EXTRAS(cCodGrupoCia_in IN CHAR, 
                                   cCodLocal_in    IN CHAR,
                                   cNumPedVta_in   IN CHAR) 
  RETURN VARCHAR2;
  
  FUNCTION F_VAR_QUIMICO_BLOQUEO_TARJ
    RETURN CHAR;
END FARMA_PUNTOS;
/
CREATE OR REPLACE PACKAGE BODY FARMA_PUNTOS is
 /* *********************************************************************** */
  FUNCTION F_DNI_USU_LOCAL(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR,
                           cSecUsuLocal_in IN CHAR) RETURN VARCHAR2
  IS
    vDNI_USU pbl_usu_local.dni_usu%type;
  BEGIN
    select trim(u.dni_usu)
    into   vDNI_USU
    from  pbl_usu_local u 
    where  u.cod_grupo_cia = cCodGrupoCia_in
    and    u.cod_local     = cCodLocal_in
    and    u.sec_usu_local = cSecUsuLocal_in;

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
  FUNCTION F_VAR_IND_ACT_PUNTOS(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR) RETURN VARCHAR2 IS
    nRetorno VARCHAR2(2);
  BEGIN
    begin
      select l.ind_puntos
        into nRetorno
        from pbl_local l
       where l.cod_grupo_cia = cCodGrupoCia_in
         and l.cod_local = cCodLocal_in;
    exception
      when no_data_found then
        nRetorno := 'N';
    end;
  
    RETURN nRetorno;
  END;
/* *********************************************************************** */
FUNCTION F_VAR_IND_IMPR_LOGO(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR) RETURN VARCHAR2
  IS
    nRetorno VARCHAR2(2);
  BEGIN
  begin  
    SELECT A.DESC_CORTA
    INTO   nRetorno 
    FROM   pbl_tab_gral A
    WHERE  A.ID_TAB_GRAL = 655;
  exception
    when no_data_found then
      nRetorno  := 'S';
  end;    
    
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
 FUNCTION F_VAR_TIME_OUT_ORBIS(cCodGrupoCia_in IN CHAR, 
                               cCodLocal_in    IN CHAR) RETURN VARCHAR2
  IS
    vWS_Orbis VARCHAR2(4000);
  BEGIN
      select llave_tab_gral
      into   vWS_Orbis
      from   PBL_TAB_GRAL 
      where  id_tab_gral = 461;
	RETURN vWS_Orbis;
  END;       
 /* *********************************************************************** */
  FUNCTION F_DIAS_HIST_RECUPERAR(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR) RETURN VARCHAR2
  IS
    vKeyOrbis VARCHAR2(4000);
  BEGIN
    vKeyOrbis := 'acv12@%&%%';
	RETURN vKeyOrbis;
  END; 
 /* *********************************************************************** */  
  FUNCTION F_VAR_MSJ_VOUCHER(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR) RETURN VARCHAR2
    IS
      vWS_Orbis VARCHAR2(4000);
    BEGIN
        select a.llave_tab_gral
        into   vWS_Orbis 
        from   pbl_tab_gral  a
        where  id_tab_gral = 661;
    RETURN vWS_Orbis;
  END;     
 /* *********************************************************************** */
  FUNCTION F_CTD_PED_X_DIAS_ATRAS(cCodGrupoCia_in IN CHAR, 
                                  cCodLocal_in    IN CHAR) RETURN integer
  IS
    vValor NUMBER(5);
  BEGIN
    
  begin
    SELECT to_number(A.DESC_CORTA)
    INTO   vValor 
    FROM   pbl_tab_gral A
    WHERE  A.ID_TAB_GRAL = 656;
  exception
    when no_data_found then
      vValor  := 2;
  end;        
    
	RETURN vValor;
  END;         
 /* *********************************************************************** */
  FUNCTION F_CTD_PED_X_DIA_REC(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR) RETURN integer
  IS
    vValor NUMBER(5);
  BEGIN
    
  begin
    SELECT to_number(A.DESC_CORTA)
    INTO   vValor 
    FROM   pbl_tab_gral A
    WHERE  A.ID_TAB_GRAL = 653;
  exception
    when no_data_found then
      vValor  := 999;
  end;        
    
	RETURN vValor;
  END;      
 /* *********************************************************************** */
  FUNCTION F_CTD_PED_X_MES_REC(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR) RETURN integer
  IS
    vValor NUMBER(5);
  BEGIN
    
  begin
    SELECT to_number(A.DESC_CORTA)
    INTO   vValor 
    FROM   pbl_tab_gral A
    WHERE  A.ID_TAB_GRAL = 654;
  exception
    when no_data_found then
      vValor  := 999;
  end;            
    
	RETURN vValor;
  END;      
 /* *********************************************************************** */      
   /*FUNCTION IMP_SALDO_TARJETA(cCodGrupoCia_in 	IN CHAR,
                              cCodLocal_in    	IN CHAR,
                              cNombres_in       in char,
                              cTarj_in          IN CHAR,
                              cPuntosAcum_in    IN char)
  RETURN FarmaCursor
  IS
    curDataCupon FarmaCursor;
    vIdDoc DOC_FARMA_PRINT_DET.ID_DOC%type;
    vIpPc  DOC_FARMA_PRINT_DET.IP_PC%type;    
    vCod_trab varchar2(100):= '';
    vFecha    varchar2(100):= '';
    vlocal    varchar2(100):= '';
    vTipoNumComprobante       VARCHAR2(20) := '';
    vNumPedVta                VARCHAR2(10) := '';
    cFilaCamp VTA_CAMPANA_CUPON%rowtype;
     ctd int;

     \*V_SEPARADOR varchar2(10) := 'ä';
     V_BOLD_I varchar2(10) := 'ÃiÃ';
     V_BOLD_F varchar2(10) := 'ÃfÃ';     *\
  BEGIN
    \* *************************************************************************** *\                 
    vIdDoc := farma_printer.F_CREA_DOC(farma_printer.T_SIZE_UNO, 
                                       farma_printer.A_IZQ, 
                                       farma_printer.BOLD_A, 
                                       farma_printer.ESPACIADO_I, 
                                       farma_printer.INVERTIDO_COLOR_I);
    vIpPc  := farma_printer.F_VAR_IP_SESS;
    \* *************************************************************************** *\    
    -- el logo se imprimira parametrizado al tab gral   
    if (farma_puntos.f_var_ind_impr_logo(cCodGrupoCia_in,cCodLocal_in) = 'S') then
       farma_printer.P_LOGO_MARCA(vIdDoc,vIpPc,cCodGrupoCia_in,cCodGrupoCia_in);    
    end if;
    ------------------------------------------------------------------------------- 
    farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_DOS, 
                            farma_printer.A_CENTRO, 
                            farma_printer.BOLD_I, 
                            farma_printer.ESPACIADO_A, 
                            farma_printer.INVERTIDO_COLOR_I,
                            'Saldo');
    farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_TRES, 
                            farma_printer.A_CENTRO, 
                            farma_printer.BOLD_A, 
                            farma_printer.ESPACIADO_I, 
                            farma_printer.INVERTIDO_COLOR_I,
                            trim(cNombres_in));
    farma_printer.P_LINEA_BLANCO(vIdDoc,vIpPc);                           
    farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_TRES, 
                            farma_printer.A_CENTRO, 
                            farma_printer.BOLD_I, 
                            farma_printer.ESPACIADO_I, 
                            farma_printer.INVERTIDO_COLOR_I,
                            trim(cTarj_in));         
    farma_printer.P_LINEA_BLANCO(vIdDoc,vIpPc);
    farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_DOS, 
                            farma_printer.A_IZQ, 
                            farma_printer.BOLD_I, 
                            farma_printer.ESPACIADO_A, 
                            farma_printer.INVERTIDO_COLOR_I,
                            FARMA_PRINTER.BOL_INI||'Fecha :'||FARMA_PRINTER.BOL_FIN||FARMA_PRINTER.SEP_PALABRA||
                            TO_CHAR(SYSDATE,'DD/MM/YYYY')||
                            lpad(' ',10,' ')||V_SEPARADOR||
                            FARMA_PRINTER.BOL_INI||'Hora :' ||FARMA_PRINTER.BOL_FIN||FARMA_PRINTER.SEP_PALABRA||
                            TO_CHAR(SYSDATE,'HH24:MI:SS')
                            );
   farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_DOS, 
                            farma_printer.A_IZQ, 
                            farma_printer.BOLD_I, 
                            farma_printer.ESPACIADO_A, 
                            farma_printer.INVERTIDO_COLOR_I,
                            FARMA_PRINTER.BOL_INI||'Puntos Acumulados : '||FARMA_PRINTER.BOL_FIN||FARMA_PRINTER.SEP_PALABRA||
                            cPuntosAcum_in
                            );         
   farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_DOS, 
                            farma_printer.A_IZQ, 
                            farma_printer.BOLD_I, 
                            farma_printer.ESPACIADO_A, 
                            farma_printer.INVERTIDO_COLOR_I,
                            TRIM(farma_puntos.f_var_msj_voucher(cCodGrupoCia_in,cCodLocal_in))
                            );                                

    -------------------------------------------------------------------------------                             
    curDataCupon :=  farma_printer.f_cur_data_doc(vIdDoc,vIpPc);    
    
    return curDataCupon;
    
  end;*/ 
  /* ********************************************************************************* */
/*FUNCTION IMP_RECUPERA_PUNTOS(cCodGrupoCia_in 	IN CHAR,
                              cCodLocal_in    	IN CHAR,
                              cNombres_in       in char,
                              cDNI_in           in char,
                              cTarj_in          IN CHAR,
                              cPuntosAcum_in    IN char,
                              cIndOnline        in char,
                              cPuntosSaldo_in   IN char,
                              cNumPedVta_in    IN char)
  RETURN FarmaCursor  
  IS 
    curDataCupon FarmaCursor;
    vIdDoc DOC_FARMA_PRINT_DET.ID_DOC%type;
    vIpPc  DOC_FARMA_PRINT_DET.IP_PC%type;    
    vCod_trab varchar2(100):= '';
    vFecha    varchar2(100):= '';
    vlocal    varchar2(100):= '';
    vTipoNumComprobante       VARCHAR2(20) := '';
    vNumPedVta                VARCHAR2(10) := '';
    cFilaCamp VTA_CAMPANA_CUPON%rowtype;
     ctd int;
     vMsjOffline pbl_tab_gral.desc_larga%type;

    off_cDNI     varchar2(20);
    off_cNombres varchar2(5000);
         
  BEGIN
    \* *************************************************************************** *\                 
    vIdDoc := farma_printer.F_CREA_DOC(farma_printer.T_SIZE_UNO, 
                                       farma_printer.A_IZQ, 
                                       farma_printer.BOLD_A, 
                                       farma_printer.ESPACIADO_I, 
                                       farma_printer.INVERTIDO_COLOR_I);
    vIpPc  := farma_printer.F_VAR_IP_SESS;
    \* *************************************************************************** *\    
    -- el logo se imprimira parametrizado al tab gral   
    if (farma_puntos.f_var_ind_impr_logo(cCodGrupoCia_in,cCodLocal_in) = 'S') then
       farma_printer.P_LOGO_MARCA(vIdDoc,vIpPc,cCodGrupoCia_in,cCodGrupoCia_in);    
    end if;
    ------------------------------------------------------------------------------- 
    farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_DOS, 
                            farma_printer.A_CENTRO, 
                            farma_printer.BOLD_I, 
                            farma_printer.ESPACIADO_A, 
                            farma_printer.INVERTIDO_COLOR_I,
                            'Constancia de Recuperacion de puntos');

    if cIndOnline = 'S' then                             
    farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_TRES, 
                            farma_printer.A_CENTRO, 
                            farma_printer.BOLD_A, 
                            farma_printer.ESPACIADO_I, 
                            farma_printer.INVERTIDO_COLOR_I,
                            trim(cDNI_in));                            
    farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_TRES, 
                            farma_printer.A_CENTRO, 
                            farma_printer.BOLD_A, 
                            farma_printer.ESPACIADO_I, 
                            farma_printer.INVERTIDO_COLOR_I,
                            trim(cNombres_in));
    else
    
    begin
    select t.dni_cli,p.nom_cli ||' '|| p.ape_pat_cli||' '||p.ape_mat_cli
    into   off_cDNI,off_cNombres
    from   fid_tarjeta t,
           pbl_cliente p
    where  t.cod_tarjeta = replace(cTarj_in,'*','0')
    and    t.dni_cli is not null
    and    t.dni_cli = p.dni_cli;
    --and 1 = 2;
    
    farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_TRES, 
                            farma_printer.A_CENTRO, 
                            farma_printer.BOLD_A, 
                            farma_printer.ESPACIADO_I, 
                            farma_printer.INVERTIDO_COLOR_I,
                            trim(off_cDNI));
    farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_TRES, 
                            farma_printer.A_CENTRO, 
                            farma_printer.BOLD_A, 
                            farma_printer.ESPACIADO_I, 
                            farma_printer.INVERTIDO_COLOR_I,
                            trim(off_cNombres));
    
    exception
    when others then 
      null;         
    end;
    
                      
                            
    end if;                            
                            
    farma_printer.P_LINEA_BLANCO(vIdDoc,vIpPc);                            
    farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_TRES, 
                            farma_printer.A_CENTRO, 
                            farma_printer.BOLD_A, 
                            farma_printer.ESPACIADO_I, 
                            farma_printer.INVERTIDO_COLOR_I,
                            trim(cTarj_in));         
    farma_printer.P_LINEA_BLANCO(vIdDoc,vIpPc);
    farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_DOS, 
                            farma_printer.A_IZQ, 
                            farma_printer.BOLD_I, 
                            farma_printer.ESPACIADO_A, 
                            farma_printer.INVERTIDO_COLOR_I,
                            FARMA_PRINTER.BOL_INI ||'Fecha :'||FARMA_PRINTER.BOL_FIN||
                            FARMA_PRINTER.SEP_PALABRA||
                            TO_CHAR(SYSDATE,'DD/MM/YYYY')||
                            lpad(' ',10,' ')||FARMA_PRINTER.SEP_PALABRA||
                            FARMA_PRINTER.BOL_INI ||'Hora :' ||FARMA_PRINTER.BOL_FIN||                            
                            FARMA_PRINTER.SEP_PALABRA||
                            TO_CHAR(SYSDATE,'HH24:MI:SS')
                            );
   farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_DOS, 
                            farma_printer.A_IZQ, 
                            farma_printer.BOLD_I, 
                            farma_printer.ESPACIADO_A, 
                            farma_printer.INVERTIDO_COLOR_I,
                            FARMA_PRINTER.BOL_INI ||'Puntos Acumulados : '||FARMA_PRINTER.BOL_FIN||
                            FARMA_PRINTER.SEP_PALABRA||
                            cPuntosAcum_in
                            );
   if length(trim(cPuntosSaldo_in)) > 0 and cIndOnline = 'S' then 
   farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_DOS, 
                            farma_printer.A_IZQ, 
                            farma_printer.BOLD_I, 
                            farma_printer.ESPACIADO_A, 
                            farma_printer.INVERTIDO_COLOR_I,
                            FARMA_PRINTER.BOL_INI ||'Saldo Actual : '||FARMA_PRINTER.BOL_FIN||
                            FARMA_PRINTER.SEP_PALABRA||
                            cPuntosSaldo_in
                            );
   end if;

     farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_DOS, 
                            farma_printer.A_IZQ, 
                            farma_printer.BOLD_I, 
                            farma_printer.ESPACIADO_A, 
                            farma_printer.INVERTIDO_COLOR_I,
                            FARMA_PRINTER.BOL_INI ||'Comprobante(s):'||FARMA_PRINTER.BOL_FIN||
                            FARMA_PRINTER.SEP_PALABRA
                            );                            
     for lista in (
                  select 
                         case
                           when p.cod_tip_proc_pago = 1 then 
                                Substrb( p.num_comp_pago_e,0,4)||'-'|| Substrb( p.num_comp_pago_e,5) 
                           else Substrb( p.num_comp_pago,0,3)||'-'|| Substrb( p.num_comp_pago,4) 
                          end  COMPROBANTE
                  from   vta_comp_pago p
                  where  p.cod_grupo_cia = cCodGrupoCia_in
                  and    p.cod_local = cCodLocal_in
                  and    p.num_ped_vta = cNumPedVta_in
                  )loop                            
      farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_DOS, 
                            farma_printer.A_IZQ, 
                            farma_printer.BOLD_I, 
                            farma_printer.ESPACIADO_A, 
                            farma_printer.INVERTIDO_COLOR_I,
                            '- '||
                            lista.comprobante
                            );                            
     end loop;                       
   
                               
    farma_printer.P_LINEA_BLANCO(vIdDoc,vIpPc);
       
   farma_printer.P_CADENA (vIdDoc,vIpPc,
                            farma_printer.T_SIZE_DOS, 
                            farma_printer.A_IZQ, 
                            farma_printer.BOLD_I, 
                            farma_printer.ESPACIADO_A, 
                            farma_printer.INVERTIDO_COLOR_I,
                            TRIM(farma_puntos.f_var_msj_voucher(cCodGrupoCia_in,cCodLocal_in))
                            );      
    -- muestra mensaje de si fue offline
    if cIndOnline = 'N' then

    begin
    SELECT a.desc_larga
    INTO   vMsjOffline
    FROM   pbl_tab_gral A
    WHERE  A.ID_TAB_GRAL = 458;
    exception
    when others then
      vMsjOffline := 'N';
    end;  
      
    if vMsjOffline != 'N' and length(vMsjOffline) > 0 then
    farma_printer.P_CADENA (vIdDoc,vIpPc,
                                farma_printer.T_SIZE_DOS, 
                                farma_printer.A_IZQ, 
                                farma_printer.BOLD_I, 
                                farma_printer.ESPACIADO_A, 
                                farma_printer.INVERTIDO_COLOR_I,
                                vMsjOffline
                                );
     end if;                           
    end if;                                
                                                        
    -------------------------------------------------------------------------------                             
    curDataCupon :=  farma_printer.f_cur_data_doc(vIdDoc,vIpPc);    
    
    return curDataCupon;
  end;*/     
  /* ********************************************************************************* */
  FUNCTION F_GET_PERMITE_RECUPERA(cCodGrupoCia_in   IN CHAR,
                                  cCod_Local_in     IN CHAR,
                                  cTipo_Comp_in     IN CHAR,
                                  cMonto_Neto_in    IN CHAR,
                                  cNum_Comp_Pago_in IN CHAR,
                                  cFec_Ped_Vta_in   IN CHAR,
                                  cNumTarjeta_in    IN CHAR)
  RETURN VARCHAR2
  IS
  vRpta VARCHAR2(300) := 'N';
  nPedxDia integer := 0;
  nPedxMes integer := 0;
  nDiaAtras integer := 0;
  nPedAsocTarjeta number :=  0;
  
  vCtdRecu_x_Dia_act number:=0;
  vCtdRecu_x_mes_act number:=0;  
  
  vDiasTranscurridos integer;
  
  vIndVtaPunto VTA_PEDIDO_VTA_CAB.EST_TRX_ORBIS%type;
  vNumTarj     VTA_PEDIDO_VTA_CAB.NUM_TARJ_PUNTOS%type;
    
  vExisteNC number;
 
  BEGIN
  Select NUM_PED_VTA
  INTO   vRpta
  FROM   (SELECT C.NUM_PED_VTA ,
                 to_char(C.VAL_NETO_PED_VTA, '9999999.00'),
                 to_char(C.FEC_PED_VTA, 'dd/mm/YYYY')
            FROM ptoventa.VTA_PEDIDO_VTA_CAB C
           WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
             AND C.COD_LOCAL = cCod_Local_in
             and c.fec_ped_vta between to_date(cFec_Ped_Vta_in,'dd/mm/yyyy') and 
                                       to_date(cFec_Ped_Vta_in,'dd/mm/yyyy')+1-1/24/60/60
             AND exists
           (select 1
                    FROM ptoventa.VTA_COMP_PAGO CP
                   WHERE CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                     AND CP.COD_LOCAL = C.COD_LOCAL
                     and CP.NUM_PED_VTA = C.NUM_PED_VTA
                     AND CP.TIP_COMP_PAGO = cTipo_Comp_in
                     and (nvl(CP.COD_TIP_PROC_PAGO, '0') = '1' and
                         cp.NUM_COMP_PAGO_E = cNum_Comp_Pago_in)
                     AND CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO =
                         to_number(cMonto_Neto_in, '999999.000'))
          union
          SELECT C.NUM_PED_VTA ,
                 to_char(C.VAL_NETO_PED_VTA, '9999999.00') ,
                 to_char(C.FEC_PED_VTA, 'dd/mm/YYYY')
            FROM ptoventa.VTA_PEDIDO_VTA_CAB C
           WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
             AND C.COD_LOCAL = cCod_Local_in
             and c.fec_ped_vta between to_date(cFec_Ped_Vta_in,'dd/mm/yyyy') and 
                                       to_date(cFec_Ped_Vta_in,'dd/mm/yyyy')+1-1/24/60/60
             AND exists
           (select 1
                    FROM ptoventa.VTA_COMP_PAGO CP
                   WHERE CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                     AND CP.COD_LOCAL = C.COD_LOCAL
                     and CP.NUM_PED_VTA = C.NUM_PED_VTA
                     AND CP.TIP_COMP_PAGO = cTipo_Comp_in
                        -- kmoncada 27.01.2015
                     and (nvl(CP.COD_TIP_PROC_PAGO, '0') <> '1' and
                         cp.NUM_COMP_PAGO = cNum_Comp_Pago_in)
                     AND CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO =
                         to_number(cMonto_Neto_in, '999999.000')));
                         
  IF vRpta IS NOT NULL AND LENGTH(vRpta) = 10 THEN
    
    select nvl(T.EST_TRX_ORBIS,'N'),nvl(T.NUM_TARJ_PUNTOS,'N')
    INTO   vIndVtaPunto,vNumTarj
    --into   nPedAsocTarjeta
    from   VTA_PEDIDO_VTA_CAB t 
    where  t.cod_grupo_cia = cCodGrupoCia_in
    and    t.cod_local  = cCod_Local_in
    and    t.num_ped_vta =  vRpta;
    
    --if nPedAsocTarjeta > 0 then
    if vIndVtaPunto = 'N' and vNumTarj = 'N' then
       -- se revisa las cantidad de recuperados  
        select count(1)
        into   vCtdRecu_x_mes_act
        from   fid_tarjeta_pedido t 
        where  t.cod_grupo_cia = cCodGrupoCia_in
        and    t.cod_local  = cCod_Local_in
        and    t.cod_tarjeta =  cNumTarjeta_in
        and    t.fec_crea_tarjeta_pedido >= trunc(sysdate,'MM')
        and    t.fec_recuperado Is not null;

        select count(1)
        into   vCtdRecu_x_Dia_act
        from   fid_tarjeta_pedido t 
        where  t.cod_grupo_cia = cCodGrupoCia_in
        and    t.cod_local  = cCod_Local_in
        and    t.cod_tarjeta =  cNumTarjeta_in
        and    t.fec_crea_tarjeta_pedido >= trunc(sysdate)
        and    t.fec_recuperado Is not null;  

        select trunc(sysdate - c.fec_ped_vta)
        into   vDiasTranscurridos
        from   vta_pedido_vta_cab c 
        where  c.cod_grupo_cia = cCodGrupoCia_in
        and    c.cod_local  = cCod_Local_in
        and    c.num_ped_vta =  vRpta;
        
        select count(1)
        into   vExisteNC
        from   vta_pedido_vta_cab c ,
               vta_pedido_vta_cab nc
        where  c.cod_grupo_cia = cCodGrupoCia_in
        and    c.cod_local  = cCod_Local_in
        and    c.num_ped_vta =  vRpta
        and    nc.cod_grupo_cia = cCodGrupoCia_in
        and    nc.cod_local = cCod_Local_in
        and    nc.est_ped_vta = 'C'
        and    nc.fec_ped_vta >= c.fec_ped_vta
        and    nc.cod_grupo_cia = c.cod_grupo_cia
        and    nc.cod_local = c.cod_local
        and    nc.num_ped_vta_origen = c.num_ped_vta;
              

        nDiaAtras := F_CTD_PED_X_DIAS_ATRAS(cCodGrupoCia_in,cCod_Local_in);
        nPedxDia := F_CTD_PED_X_DIA_REC(cCodGrupoCia_in,cCod_Local_in);
        nPedxMes := F_CTD_PED_X_MES_REC(cCodGrupoCia_in,cCod_Local_in);            
        
        if vDiasTranscurridos >nDiaAtras then
           RAISE_APPLICATION_ERROR(-20130,'Excede la cantidad de días permitidos para recuperar');
        end if;
       
        if (vCtdRecu_x_Dia_act+1) > nPedxDia then
           RAISE_APPLICATION_ERROR(-20150,'Excede la cantidad máxima recuperación por día permitida.');
        end if;

        if (vCtdRecu_x_mes_act+1) > nPedxMes then
           RAISE_APPLICATION_ERROR(-20140,'Excede la cantidad máxima recuperación por mes permitida.');
        end if;    
        
        if vExisteNC > 0 then
           RAISE_APPLICATION_ERROR(-20140,'El pedido se encuentra anulado.');
        end if;  
        

        -- si pasa todas las validaciones el pedido se va retornar
    else        
      -- Pedido ya Asociado a Tarjeta
      RAISE_APPLICATION_ERROR(-20180,'El pedido no se puede recuperar mas de una vez');
    end if;
    
    return vRpta;
  else
      RAISE_APPLICATION_ERROR(-20170,'El pedido ingresado no existe.');
  END IF; 
                           
  RETURN vRpta;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
       RAISE_APPLICATION_ERROR(-20190,'El pedido ingresado no existe.');
  END;
  /* *************************************************************************** */
  PROCEDURE P_ASOCIA_TARJ_PED(cCodGrupoCia_in   IN CHAR,
                              cCod_Local_in     IN CHAR,
                              cNumPedido_in     IN CHAR,
                             cNumTarjeta_in    IN CHAR,
                             cIndOnline_in     IN CHAR,
                             cIdTrx_in         IN CHAR,
                             cNmAutorizacion_in IN CHAR
                             ) AS
   nExisteTarjeta  NUMBER(9) := 0;
  begin  
    
   update vta_pedido_vta_cab c
   set    c.num_tarj_puntos = cNumTarjeta_in,
          c.fec_proc_puntos = null,
          c.est_trx_orbis   = 'P', --  PENDIENTE DE PROCESAR
          c.usu_mod_ped_vta_cab = 'RECUPERACION',
          C.FEC_MOD_PED_VTA_CAB = SYSDATE,
          c.id_transaccion = cIdTrx_in,
          c.numero_autorizacion = cNmAutorizacion_in,
          C.DNI_CLI = (
                      SELECT V.DNI_CLI
                      from   fid_tarjeta v
                      where  v.cod_tarjeta = cNumTarjeta_in
                      )
   where  c.cod_grupo_cia = cCodGrupoCia_in
   and    c.cod_local = cCod_Local_in
   and    c.num_ped_vta = cNumPedido_in;
   
   if cIndOnline_in = 'S' then
     update vta_pedido_vta_cab c
     set    c.fec_proc_puntos = sysdate,
            c.est_trx_orbis   = 'E', --  ENVIADO
            c.usu_mod_ped_vta_cab = 'RECUPERACION',
            C.FEC_MOD_PED_VTA_CAB = SYSDATE,
          c.id_transaccion = cIdTrx_in,
          c.numero_autorizacion = cNmAutorizacion_in
     where  c.cod_grupo_cia = cCodGrupoCia_in
     and    c.cod_local = cCod_Local_in
     and    c.num_ped_vta = cNumPedido_in;     
   end if;
  
    select COUNT(1)
    INTO   nExisteTarjeta
    from   Fid_Tarjeta_Pedido t
    where  t.cod_grupo_cia = cCodGrupoCia_in
    and    t.cod_local = cCod_Local_in
    and    t.num_pedido = cNumPedido_in;
  
    IF nExisteTarjeta = 0 THEN
      insert into Fid_Tarjeta_Pedido t
      (cod_grupo_cia, cod_local, num_pedido, dni_cli, cant_dcto, usu_crea_tarjeta_pedido, fec_crea_tarjeta_pedido, 
      usu_mod_tarjeta_pedido, fec_mod_tarjeta_pedido, cod_tarjeta, fec_recuperado)
      select cCodGrupoCia_in,cCod_Local_in,cNumPedido_in,v.dni_cli,0,'RECUPERACION',SYSDATE,
      NULL,NULL,V.COD_TARJETA,SYSDATE
      from   fid_tarjeta v
      where  v.cod_tarjeta = cNumTarjeta_in;
    END IF;
  end;  
  /* *************************************************************************** */  
  FUNCTION F_CUR_PED_ASOC_TARJ_PED(cCodGrupoCia_in IN CHAR, 
                               cCodLocal_in    IN CHAR,
                               cNumPedVta_in   IN CHAR) RETURN FarmaCursor
  IS
    vCur FarmaCursor;
  BEGIN
     OPEN vCur for
      select T.NUM_PEDIDO,
             C.NUM_TARJ_PUNTOS ,
             T.DNI_CLI,
             T.COD_TARJETA
      from   VTA_PEDIDO_VTA_CAB C,
             FID_TARJETA_PEDIDO  t
      where  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL = cCodLocal_in
      AND    C.NUM_PED_VTA = cNumPedVta_in
      ---  todo proceso que se opero en ORBIS debe  de tener este campo llno
      ---  ya que es el se envia en la transaccion con orbis
      AND    C.NUM_TARJ_PUNTOS IS NOT NULL
      AND    C.COD_GRUPO_CIA = T.COD_GRUPO_CIA
      AND    C.COD_LOCAL = T.COD_LOCAL
      AND    C.NUM_PED_VTA = T.NUM_PEDIDO;
    RETURN vCur;
    
  END;
 /* *********************************************************************** */   
  FUNCTION F_VAR_PED_ASOC_X_MAS_1(cCodGrupoCia_in IN CHAR, 
                               cCodLocal_in    IN CHAR,
                               cNumPedVta_in   IN CHAR) RETURN VARCHAR2
  IS
    vRetorno varchar2(1) := 'N';
  BEGIN
     select decode(sum(decode(nvl(d.ind_prod_mas_1,'N'),'S',1,0)),0,'N','S')
     into   vRetorno
     from   vta_pedido_vta_det d
     where  d.cod_grupo_cia =  cCodGrupoCia_in
     and    d.cod_local = cCodLocal_in
     and    d.num_ped_vta =  cNumPedVta_in;
     
    return vRetorno;
    
  END; 
 /* *********************************************************************** */   
  FUNCTION F_VAR_PED_PROC_ORBIS(cCodGrupoCia_in IN CHAR, 
                               cCodLocal_in    IN CHAR,
                               cNumPedVta_in   IN CHAR) RETURN VARCHAR2
  IS
    vRetorno varchar2(1) := 'N';
  BEGIN
     select 
            CASE 
             WHEN C.FEC_PROC_PUNTOS IS NOT NULL THEN 'S'
             ELSE 'N'
            END
     into   vRetorno
     from   vta_pedido_vta_CAB C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in;
     
    return vRetorno;
    
  END;   
 /* *********************************************************************** */   
  FUNCTION F_VAR_PED_REDIMIO(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR,
                             cNumPedVta_in   IN CHAR) RETURN VARCHAR2
  IS
    vRetorno varchar2(1) := 'N';
    vCantRedimio VTA_PEDIDO_VTA_CAB.PT_REDIMIDO%TYPE;
    vCantAhorro VTA_PEDIDO_VTA_DET.PTOS_AHORRO%TYPE;
  BEGIN
    
    SELECT NVL(C.PT_REDIMIDO,0)
    INTO vCantRedimio
    FROM VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   C.COD_LOCAL = cCodLocal_in
    AND   C.NUM_PED_VTA = cNumPedVta_in;
    
    SELECT SUM(NVL(C.PTOS_AHORRO,0))
    INTO vCantAhorro
    FROM VTA_PEDIDO_VTA_DET C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   C.COD_LOCAL = cCodLocal_in
    AND   C.NUM_PED_VTA = cNumPedVta_in;
    
    --IF  (vCantRedimio + vCantAhorro) > 0 THEN
    -- KMONCADA 23.07.2015 SE VALIDARA SOLO PTOS REDIMIDOS Y NO DESCUENTOS
    IF  (vCantRedimio) > 0 THEN
      vRetorno := 'S';
    ELSE
      vRetorno := 'N';
    END IF;
     /*select 
            CASE 
             WHEN nvl(c.pt_redimido,0) > 0 THEN 'S'
             ELSE 'N'
            END
     into   vRetorno
     from   vta_pedido_vta_CAB C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in;*/
     
    return vRetorno;
    
  END;     
 /* *********************************************************************** */     
FUNCTION F_VAR_PED_PUNTOS_ACUM(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR,
                             cNumPedVta_in   IN CHAR) RETURN VARCHAR2
  IS
    vRetorno varchar2(1) := 'N';
  BEGIN
     select 
            CASE 
             WHEN nvl(c.Pt_Acumulado,0) > 0 THEN 'S'
             ELSE 'N'
            END
     into   vRetorno
     from   vta_pedido_vta_CAB C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in;
     
    return vRetorno;    
  END;   
 /* ********************************************************************** */  
  PROCEDURE P_REVERT_ACUM_PUNTOS(cCodGrupoCia_in   IN CHAR,
                                cCod_Local_in     IN CHAR,
                                cNumPedido_in     IN CHAR) AS
  begin
    UPDATE VTA_PEDIDO_VTA_CAB C
    SET    C.EST_TRX_ORBIS = 'N'
    WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    C.COD_LOCAL = cCod_Local_in
    AND    C.NUM_PED_VTA = cNumPedido_in;
    
    UPDATE VTA_PEDIDO_VTA_DET D
    SET    D.CTD_PUNTOS_ACUM = NULL,
           D.COD_PROD_PUNTOS = NULL,
           D.IND_PROD_MAS_1  = NULL
    WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    D.COD_LOCAL = cCod_Local_in
    AND    D.NUM_PED_VTA = cNumPedido_in;
  end;   
/* ********************************************************************** */  
FUNCTION F_CUR_DET_RECUPERA(cCodGrupoCia_in IN CHAR, 
                            cCodLocal_in    IN CHAR,
                            cNumPedVta_in   IN CHAR) RETURN FarmaCursor
  IS
    vCur FarmaCursor;
    vIndVtaPuntos_in char(1);
  BEGIN
  
  select nvl(c.EST_TRX_ORBIS,'N')
  into   vIndVtaPuntos_in
  from   vta_pedido_vta_cab c
  where  c.cod_grupo_cia = cCodGrupoCia_in
  and    c.cod_local = cCodLocal_in
  and    c.num_ped_vta = cNumPedVta_in;

  if vIndVtaPuntos_in = 'P' then
     /*
      OPEN vCur for
      select nvl(t.cod_prod_puntos,T.COD_PROD) || ',' ||
             trim(to_char(round(T.CANT_ATENDIDA/t.val_frac,2),'999999990.00')) || ',' ||
             trim(to_char(NVL(t.ctd_puntos_acum,0),'999999990.00')) || ',' ||
             trim(to_char(t.val_prec_total,'999999990.00'))
      from   VTA_PEDIDO_VTA_DET  t
      where  T.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    T.COD_LOCAL = cCodLocal_in
      AND    t.Num_Ped_Vta = cNumPedVta_in;
     */
      vCur := f_cur_v_lista_producto(cCodGrupoCia_in,
                                                      cCodLocal_in,
                                                      cNumPedVta_in,
                                                      'S',
                                                      'N',
                                                      'N');
  else
     OPEN vCur for
      select T.COD_PROD || ',' ||
             trim(to_char(round(T.CANT_ATENDIDA/t.val_frac,2),'999999990.00')) || ',' ||
             trim(to_char(NVL(t.ctd_puntos_acum,0),'999999990.00')) || ',' ||
             trim(to_char(t.val_prec_total,'999999990.00'))
      from   VTA_PEDIDO_VTA_DET  t
      where  T.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    T.COD_LOCAL = cCodLocal_in
      AND    t.Num_Ped_Vta = cNumPedVta_in
      and    1=2;
  end if;    
      
    RETURN vCur;
    
  END;
/* ********************************************************************** */  
FUNCTION F_VAR_PED_ACTUA_PUNTOS(cCodGrupoCia_in IN CHAR, 
                                cCodLocal_in    IN CHAR,
                                cNumPedVta_in   IN CHAR) RETURN VARCHAR2
  IS
    vRetorno varchar2(1) := 'N';
  BEGIN
     select 
             CASE
               WHEN NVL(C.EST_TRX_ORBIS,'N') = 'N' THEN 'N'
               WHEN NVL(C.EST_TRX_ORBIS,'N') = 'P' THEN 'S'
               WHEN NVL(C.EST_TRX_ORBIS,'N') = 'E' THEN 'S'
               WHEN NVL(C.EST_TRX_ORBIS,'N') = 'D' THEN 'N'
                ELSE  'N'   
             END     
     into   vRetorno
     from   vta_pedido_vta_CAB C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in;
     
    return vRetorno;
    
  END;     
/* ********************************************************************** */    
FUNCTION F_CUR_DET_ANULA(cCodGrupoCia_in IN CHAR, 
                         cCodLocal_in    IN CHAR,
                         cNumPedVta_in   IN CHAR) RETURN FarmaCursor
  IS
    vCur FarmaCursor;
  BEGIN
  
     /*
      OPEN vCur for
      select T.COD_PROD || ',' ||
             trim(to_char(round(T.CANT_ATENDIDA/t.val_frac,2),'999999990.00'))
      from   VTA_PEDIDO_VTA_DET  t
      where  T.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    T.COD_LOCAL = cCodLocal_in
      AND    t.Num_Ped_Vta = cNumPedVta_in;*/

      vCur := f_cur_v_lista_producto(cCodGrupoCia_in,
                                                       cCodLocal_in,
                                                       cNumPedVta_in,
                                                       'S',
                                                       'N',
                                                       'S');
      
    RETURN vCur;
    
END;  
/* ********************************************************************** */  
FUNCTION F_VAR_IS_PTO_CERO(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR,
                           cNumPedVta_in   IN CHAR) RETURN VARCHAR2
  IS
    vRetorno varchar2(1) := 'N';
    vCtd_deta number(9)  := 0;
    vCtd_NULL number(9):= 0;
    vCtd_not_NULL number(9):= 0; 
    vCtd_CERO     number(9):= 0; 
  BEGIN
  
     select COUNT(1)
     into   vCtd_NULL
     from   vta_pedido_vta_DET C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in
     and    c.ctd_puntos_acum is null;

     select COUNT(1)
     into   vCtd_not_NULL
     from   vta_pedido_vta_DET C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in
     and    nvl(c.ctd_puntos_acum,0) > 0;
     
     select COUNT(1)
     into   vCtd_CERO
     from   vta_pedido_vta_DET C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in
     and    c.ctd_puntos_acum = 0;     

     select COUNT(1)
     into   vCtd_deta
     from   vta_pedido_vta_DET C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in;
     
     
     if vCtd_CERO = vCtd_deta  then
        vRetorno := 'S';
     else
        vRetorno := 'N' ;  
     end if; 
     

    return vRetorno;
    
  END;     
/* ********************************************************************** */  
FUNCTION F_VAR_IS_PTO_NULO(cCodGrupoCia_in IN CHAR, 
                           cCodLocal_in    IN CHAR,
                           cNumPedVta_in   IN CHAR) RETURN VARCHAR2
  IS
    vRetorno varchar2(1) := 'N';
    vCtd_deta number(9)  := 0;
    vCtd_NULL number(9):= 0;
    vCtd_not_NULL number(9):= 0; 
    vCtd_CERO     number(9):= 0; 
  BEGIN
     select COUNT(1)
     into   vCtd_NULL
     from   vta_pedido_vta_DET C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in
     and    c.ctd_puntos_acum is null;

     select COUNT(1)
     into   vCtd_not_NULL
     from   vta_pedido_vta_DET C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in
     and    nvl(c.ctd_puntos_acum,0) > 0;
     
     select COUNT(1)
     into   vCtd_CERO
     from   vta_pedido_vta_DET C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in
     and    c.ctd_puntos_acum = 0;     

     select COUNT(1)
     into   vCtd_deta
     from   vta_pedido_vta_DET C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in;
     
     
     if vCtd_NULL = vCtd_deta  then
        vRetorno := 'S';
     else
        vRetorno := 'N' ;  
     end if; 
     

    return vRetorno;
    
  END;   
/* ********************************************************************** */  
FUNCTION F_VAR_IS_PTO_MAYOR_CERO(cCodGrupoCia_in IN CHAR, 
                                cCodLocal_in    IN CHAR,
                                cNumPedVta_in   IN CHAR) RETURN VARCHAR2
  IS
    vRetorno varchar2(1) := 'N';
    vSumnotnull number(9)  := 0;
  BEGIN
     select SUM(C.CTD_PUNTOS_ACUM)
     into   vSumnotnull
     from   vta_pedido_vta_DET C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in
     and    c.ctd_puntos_acum is NOT null;
     
     
     if vSumnotnull > 0  then
        vRetorno := 'S';
     else
        vRetorno := 'N' ;  
     end if; 
     

    return vRetorno;
    
  END; 
/* ********************************************************************** */    
FUNCTION F_VAR_IS_CTD_PTO_PED(cCodGrupoCia_in IN CHAR, 
                              cCodLocal_in    IN CHAR,
                              cNumPedVta_in   IN CHAR) RETURN VARCHAR2
  IS
    vRetorno varchar2(1) := 'N';
    vSumnotnull number(9)  := 0;
  BEGIN
     select SUM(NVL(C.CTD_PUNTOS_ACUM,0))
     into   vSumnotnull
     from   vta_pedido_vta_DET C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in;

    return TRIM(TO_CHAR(vSumnotnull,'999999990.00'));
    
  END; 
/* *********************************************************************** */         
FUNCTION F_VAR_IS_BONIFICADO(cCodGrupoCia_in IN CHAR, 
                             cCodLocal_in    IN CHAR,
                             cNumPedVta_in   IN CHAR) RETURN VARCHAR2
  IS
    vRetorno varchar2(1) := 'N';
    vIndBonificado number(9);
  BEGIN
     select count(1)
     into   vIndBonificado
     from   vta_pedido_vta_DET C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in
     and    NVL(c.ind_bonificado,'N') = 'S';
     
     
     if vIndBonificado > 0  then
        vRetorno := 'S';
     else
        vRetorno := 'N' ;  
     end if; 
     

    return vRetorno;
    
  END; 
/* *********************************************************************** */
FUNCTION F_VAR_GET_DATA_TRANSAC_ORBIS(cCodGrupoCia_in IN CHAR, 
                                      cCodLocal_in    IN CHAR,
                                      cNumPedVta_in   IN CHAR) RETURN FarmaCursor
  IS
    vCur FarmaCursor;
  BEGIN
     OPEN vCur for    
     select nvl(D.ID_TRANSACCION,' ') as "ID_TRANSACCION",
            nvl(D.NUMERO_AUTORIZACION,' ') as "NUMERO_AUTORIZACION",
            to_char(nvl(D.PT_INICIAL,0),'99999990.00') as "CTD_PUNTO_INI",
            to_char(nvl(D.PT_ACUMULADO,0),'99999990.00')as "CTD_PUNTO_ACUM",
            to_char(nvl(D.PT_REDIMIDO,0),'99999990.00')as "CTD_PUNTO_REDI",
            to_char(nvl(D.PT_TOTAL,0),'99999990.00') as "CTD_PUNTO_TOT",
            to_char(D.FEC_PED_VTA,'dd/mm/yyyy') as "FEC_PED_VTA",
            to_char(D.VAL_NETO_PED_VTA,'99999990.00') as "NETO_PED_VTA"
     from   VTA_PEDIDO_VTA_CAB d
     where  d.cod_grupo_cia =  cCodGrupoCia_in
     and    d.cod_local = cCodLocal_in
     and    d.num_ped_vta =  cNumPedVta_in;
     
    return vCur;
END;
/* *********************************************************************** */
PROCEDURE P_SAVE_TRX_ANULA_ORBIS(cCodGrupoCia_in   IN CHAR,
                                cCod_Local_in     IN CHAR,
                                cNumPedOriginal_in IN CHAR,
                                cIndOnlineProceso_in in char,
                                cIdTrxAnula_in in char,
                                cNroAutoriza_in in char
                                ) AS
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
                  c.est_trx_orbis = 'E'
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    C.COD_LOCAL = cCod_Local_in
          --AND    C.EST_PED_VTA = 'C'
          AND    C.FEC_PED_VTA >= TRUNC(vRowCab.Fec_Ped_Vta)
          and    c.Num_Ped_Vta_Origen = cNumPedOriginal_in;
      end if;
    end if;  
    
  end;    
/* *********************************************************************** */  
PROCEDURE P_DESCARTA_TRX_ANULA_ORBIS(cCodGrupoCia_in   IN CHAR,
                                cCod_Local_in     IN CHAR,
                                cNumPedOriginal_in IN CHAR,
                                cIndDescarta_in in char,
                                cIndDescartaOrigen_in in char DEFAULT 'S') AS
  vRowCab VTA_PEDIDO_VTA_CAB%rowtype;
  V_EST_TRX_ORBIS VTA_PEDIDO_VTA_CAB.EST_TRX_ORBIS%TYPE;
  
  begin
    
  SELECT C.*
  into   vRowCab
  FROM   VTA_PEDIDO_VTA_CAB C
  WHERE  C.COD_GRUPO_CIA =  cCodGrupoCia_in
  AND    C.COD_LOCAL = cCod_Local_in
  AND    C.NUM_PED_VTA = cNumPedOriginal_in;
  
  if cIndDescarta_in = 'S' AND cIndDescartaOrigen_in ='S'  then
       -- si esl ORIGINAL es NO ENVIADA A ORBIS
       -- no se proceso el pedido original
       -- entonces la NC o Anulacion TAMPOCO DE EVNIARSE A ORBIS
        UPDATE vta_pedido_vta_cab C
        SET     c.id_transaccion  = null,--vRowCab.Id_Transaccion, 
                c.numero_autorizacion = null,--vRowCab.Numero_Autorizacion, 
                c.fec_proc_puntos = null, 
                c.est_trx_orbis = 'D'
        WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    C.COD_LOCAL = cCod_Local_in
        --AND    C.EST_PED_VTA = 'C'
        AND    C.FEC_PED_VTA >= TRUNC(vRowCab.Fec_Ped_Vta)
        and    c.Num_Ped_Vta_Origen = cNumPedOriginal_in;

        -- 07.05.2015
        -- dubilluz 
        UPDATE vta_pedido_vta_cab C
        SET     c.id_transaccion  = null,--vRowCab.Id_Transaccion, 
                c.numero_autorizacion = null,--vRowCab.Numero_Autorizacion, 
                c.fec_proc_puntos = null, 
                c.est_trx_orbis = 'D'
        WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    C.COD_LOCAL = cCod_Local_in
        and    c.num_ped_vta = cNumPedOriginal_in
        AND    C.EST_PED_VTA = 'C';
        -- 07.05.2015
        -- dubilluz         
        
        
    -- DESCARTA NC, NO DESCARTA ORIGEN    
    ELSIF cIndDescarta_in = 'S' AND cIndDescartaOrigen_in ='N'  then
       --SOLO DESCARTO NC
              --VALIDA PEDIDO ORIGEN EL ESTADO COBRADO Y ESTADO ORBIS ENVIADO 
              
              SELECT NVL(C.EST_TRX_ORBIS,'N')
              INTO V_EST_TRX_ORBIS
              FROM vta_pedido_vta_cab C
              WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
              AND    C.COD_LOCAL = cCod_Local_in
              and    c.num_ped_vta = cNumPedOriginal_in
              AND    C.EST_PED_VTA = 'C'
              AND    C.EST_TRX_ORBIS='E';

             IF V_EST_TRX_ORBIS='E' THEN   
              UPDATE vta_pedido_vta_cab C
              SET     c.id_transaccion  = null,--vRowCab.Id_Transaccion, 
                      c.numero_autorizacion = null,--vRowCab.Numero_Autorizacion, 
                      c.fec_proc_puntos = null, 
                      c.est_trx_orbis = 'D'
              WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
              AND    C.COD_LOCAL = cCod_Local_in
              --AND    C.EST_PED_VTA = 'C'
              AND    C.FEC_PED_VTA >= TRUNC(vRowCab.Fec_Ped_Vta)
              and    c.Num_Ped_Vta_Origen = cNumPedOriginal_in;
             END IF;
                     
    end IF;     
    
  end;   
  
  FUNCTION IMP_LISTA_TIPOS_COMP_RECUPERA(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
		  SELECT TIP_COMP    || 'Ã' ||
             DESC_COMP
      FROM   VTA_TIP_COMP
      WHERE  COD_GRUPO_CIA     = cCodGrupoCia_in AND
			       IND_NECESITA_IMPR = 'S'
             and   tip_comp != '03';
      RETURN curVta;
  END;   
  
  -- KMONCADA 27.04.2015 INICIO
  PROCEDURE F_I_CALCULA_PUNTOS(cCodGrupoCia_in   IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                               cCodLocal_in      IN VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                               cNumPedVta_in     IN VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                               cRecalculoPtos_in IN CHAR DEFAULT 'N',
                               cIndRedime_in     IN CHAR DEFAULT 'N',
                               vValCopago_in     IN NUMBER DEFAULT -1) IS

    CURSOR curDetallePtos (cCodGrupoCia CHAR, cCodLocal CHAR, cNumPedVta CHAR) IS
      SELECT DETALLE.*
      FROM (
            SELECT 
              PRODUCTOS.*,
              -- SEGUN DEFINICION DEL PROGRAMA DE PUNTOS
              -- SE BUSCA EL PRODUCTO, PRIMERO POR CATEGORIA SI NO ESTA POR CODIGO DE PROD SI NO SE APLICA VALORES POR DEFECTO
              RANK() OVER(PARTITION BY PRODUCTOS.COD_PROD ORDER BY DECODE(PRODUCTOS.ORIGEN, 'CAT', 1, 'PRO', 2, 'DEF', 3)) INDICADOR
            FROM (
                  -- CONSULTA PRODUCTOS EN LA TABLA DE PUNTOS POR CATEGORIA
                  SELECT 'CAT' ORIGEN,
                         DET.COD_CAMP_CUPON,
                         DET.COD_LOCAL,
                         DET.NUM_PED_VTA,
                         DET.SEC_PED_VTA_DET,
                         DET.COD_PROD,
                         --DET.VAL_PREC_TOTAL,
                         F_GET_CALCULO_PAGO_CLIENTE(cCodGrupoCia_in => cCodGrupoCia_in,
                                                    cCodLocal_in => cCodLocal_in,
                                                    cNumPedVta_in => cNumPedVta_in,
                                                    cMontoPago_in => DET.VAL_PREC_TOTAL,
                                                    nValorSelCopago_in => vValCopago_in) VAL_PREC_TOTAL,
                         DET.IND_BONIFICADO,
                         DET.IND_PROD_MAS_1,
                         NVL(DET.VAL_IGV,0) IGV,
                         CAT.PT_ACUMULA,
                         CAT.MONEDA_ACUMULA,
                         CAT.SOLO_ACUMULA,
                         CAT.IND_MESON,
                         CAT.IND_CONV_MESON,
                         CAT.IND_DELIVERY,
                         CAT.IND_CONV_DELIVERY,
                         CAT.IND_MAYORISTA,
                         CAT.PORCENTAJE PTO_PORCENTAJE,
                         CAB.TIP_PED_VTA TIP_VTA,
                         NVL(CAB.IND_CONV_BTL_MF, IND_NO) IND_CONVENIO,
                         -- KMONCADA 2015.03.27 CALCULO DE MONTO AHORRO EN PUNTOS 
                         ROUND((CASE 
                           WHEN TAB_AHORRO.MONEDA_AHORRO = 0 THEN 
                             0
                           ELSE 
                             (((DET.AHORRO - NVL(DET.AHORRO_PUNTOS,0))/TAB_AHORRO.MONEDA_AHORRO)*TAB_AHORRO.PT_AHORRO) 
                         END),0) PTO_AHORRO,
                         CASE 
                           WHEN NVL(DET.IND_BONIFICADO,'N') = 'N' AND DET.VAL_PREC_TOTAL = 0 THEN
                             'S'
                           ELSE 
                             'N' 
                         END ES_REGALO,
                         CASE 
                           WHEN DET.COD_CAMP_CUPON IS NOT NULL AND DET.AHORRO_CAMP > 0 THEN
                             'S'
                           ELSE
                             'N'
                         END ES_DSCTO
                    FROM  VTA_PEDIDO_VTA_DET DET
                    
                    JOIN VTA_PEDIDO_VTA_CAB CAB
                      ON CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                     AND CAB.COD_LOCAL = DET.COD_LOCAL
                     AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
                    
                    JOIN LGT_PROD P
                      ON P.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                     AND P.COD_PROD = DET.COD_PROD
                    
                    JOIN VTA_PUNTOS_CATEGORIA CAT
                      ON CAT.COD_CATEGORIA = P.COD_GRUPO_REP_EDMUNDO
                         
                         -- KMONCADA 2015.03.27 VALORES PARA AHORRO EN PUNTOS
                         ,
                         (SELECT TO_NUMBER(TRIM(NVL(A.LLAVE_TAB_GRAL,0)), '999990.00') PT_AHORRO,
                                 TO_NUMBER(TRIM(NVL(A.DESC_CORTA,0)), '999990.00') MONEDA_AHORRO
                          FROM PBL_TAB_GRAL A
                          WHERE A.ID_TAB_GRAL = TAB_IND_CALCULO_PTO_AHORRO) TAB_AHORRO   
                              
                   WHERE DET.COD_GRUPO_CIA = cCodGrupoCia
                     AND DET.COD_LOCAL = cCodLocal
                     AND DET.NUM_PED_VTA = cNumPedVta
                     -- SOLO CONSIDERA PRODUCTOS QUE EN EL PEDIDO NO SON BONIFICADOS O PROMOCIONES POR PUNTOS
                     AND NVL(DET.IND_BONIFICADO, IND_NO) <> IND_SI  
                     AND CAT.ESTADO = IND_ACTIVO
                  UNION
                  -- CONSULTA PRODUCTOS EN LA TABLA DE PUNTOS POR CODIGO DE PRODUCTO            
                  SELECT 'PRO' ORIGEN,
                         DET.COD_CAMP_CUPON,
                         DET.COD_LOCAL,
                         DET.NUM_PED_VTA,
                         DET.SEC_PED_VTA_DET,
                         DET.COD_PROD,
                         --DET.VAL_PREC_TOTAL,
                         F_GET_CALCULO_PAGO_CLIENTE(cCodGrupoCia_in => cCodGrupoCia_in,
                                                    cCodLocal_in => cCodLocal_in,
                                                    cNumPedVta_in => cNumPedVta_in,
                                                    cMontoPago_in => DET.VAL_PREC_TOTAL,
                                                    nValorSelCopago_in => vValCopago_in) VAL_PREC_TOTAL,
                         DET.IND_BONIFICADO,
                         DET.IND_PROD_MAS_1,
                         NVL(DET.VAL_IGV,0) IGV,
                         PROD.PT_ACUMULA,
                         PROD.MONEDA_ACUMULA,
                         PROD.SOLO_ACUMULA,
                         PROD.IND_MESON,
                         PROD.IND_CONV_MESON,
                         PROD.IND_DELIVERY,
                         PROD.IND_CONV_DELIVERY,
                         PROD.IND_MAYORISTA,
                         PROD.PORCENTAJE PTO_PORCENTAJE,
                         CAB.TIP_PED_VTA TIP_VTA,
                         NVL(CAB.IND_CONV_BTL_MF, 'N') IND_CONVENIO,
                         -- KMONCADA 2015.03.27 CALCULO DE MONTO AHORRO EN PUNTOS 
                         ROUND((CASE 
                           WHEN TAB_AHORRO.MONEDA_AHORRO = 0 THEN 
                             0
                           ELSE 
                             (((DET.AHORRO - NVL(DET.AHORRO_PUNTOS,0))/TAB_AHORRO.MONEDA_AHORRO)*TAB_AHORRO.PT_AHORRO) 
                         END),0) PTO_AHORRO,
                         CASE 
                           WHEN NVL(DET.IND_BONIFICADO,'N') = 'N' AND DET.VAL_PREC_TOTAL = 0 THEN
                             'S'
                           ELSE 
                             'N' 
                         END ES_REGALO,
                         CASE 
                           WHEN DET.COD_CAMP_CUPON IS NOT NULL AND DET.AHORRO_CAMP > 0 THEN
                             'S'
                           ELSE
                             'N'
                         END ES_DSCTO
                              
                    FROM  VTA_PEDIDO_VTA_DET DET
                    
                    JOIN VTA_PEDIDO_VTA_CAB CAB
                      ON CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                     AND CAB.COD_LOCAL = DET.COD_LOCAL
                     AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
                    
                    JOIN VTA_PUNTOS_PRODUCTO PROD
                      ON PROD.COD_PROD = DET.COD_PROD
                         
                         -- KMONCADA 2015.03.27 VALORES PARA AHORRO EN PUNTOS
                         ,
                         (SELECT TO_NUMBER(TRIM(NVL(A.LLAVE_TAB_GRAL,0)), '999990.00') PT_AHORRO,
                                 TO_NUMBER(TRIM(NVL(A.DESC_CORTA,0)), '999990.00') MONEDA_AHORRO
                          FROM PBL_TAB_GRAL A
                          WHERE A.ID_TAB_GRAL = TAB_IND_CALCULO_PTO_AHORRO) TAB_AHORRO
                              
                   WHERE DET.COD_GRUPO_CIA = cCodGrupoCia
                     AND DET.COD_LOCAL = cCodLocal
                     AND DET.NUM_PED_VTA = cNumPedVta
                     -- SOLO CONSIDERA PRODUCTOS QUE EN EL PEDIDO NO SON BONIFICADOS O PROMOCIONES POR PUNTOS
                     AND NVL(DET.IND_BONIFICADO, IND_NO) <> IND_SI
                     AND PROD.ESTADO = IND_ACTIVO

                  UNION
                  -- CONSULTA DE VALORES POR DEFAULT PARA CALCULO PUNTOS
                  SELECT 'DEF' ORIGEN,
                         DET.COD_CAMP_CUPON,
                         DET.COD_LOCAL,
                         DET.NUM_PED_VTA,
                         DET.SEC_PED_VTA_DET,
                         DET.COD_PROD,
                         --DET.VAL_PREC_TOTAL,
                         F_GET_CALCULO_PAGO_CLIENTE(cCodGrupoCia_in => cCodGrupoCia_in,
                                                    cCodLocal_in => cCodLocal_in,
                                                    cNumPedVta_in => cNumPedVta_in,
                                                    cMontoPago_in => DET.VAL_PREC_TOTAL,
                                                    nValorSelCopago_in => vValCopago_in) VAL_PREC_TOTAL,
                         DET.IND_BONIFICADO,
                         DET.IND_PROD_MAS_1,
                         NVL(DET.VAL_IGV,0) IGV,  
                         TAB_GRAL.PT_ACUMULA,
                         TAB_GRAL.MONEDA,
                         IND_NO SOLO_ACUMULA,
                         DECODE(CAB.TIP_PED_VTA, TIP_VTA_MESON, IND_SI, IND_NO) IND_MESON,
                         NVL(CAB.IND_CONV_BTL_MF, IND_NO) IND_CONV_MESON,
                         DECODE(CAB.TIP_PED_VTA, TIP_VTA_DELIVERY, IND_SI, IND_NO) IND_DELIVERY,
                         NVL(CAB.IND_CONV_BTL_MF, IND_NO) IND_CONV_DELIVERY,
                         DECODE(CAB.TIP_PED_VTA, TIP_VTA_MAYORISTA, IND_SI, IND_NO) IND_MAYORISTA,
                         TAB_GRAL.PORCENTAJE PTO_PORCENTAJE,
                         CAB.TIP_PED_VTA TIP_VTA,
                         NVL(CAB.IND_CONV_BTL_MF, IND_NO) IND_CONVENIO,
                         -- KMONCADA 2015.03.27 CALCULO DE MONTO AHORRO EN PUNTOS 
                         ROUND((CASE
                                  WHEN TAB_AHORRO.MONEDA_AHORRO = 0 THEN 
                                    0
                                  ELSE 
                                    (((DET.AHORRO - NVL(DET.AHORRO_PUNTOS,0))/TAB_AHORRO.MONEDA_AHORRO)*TAB_AHORRO.PT_AHORRO) 
                                END),0) PTO_AHORRO,
                         CASE 
                           WHEN NVL(DET.IND_BONIFICADO,'N') = 'N' AND DET.VAL_PREC_TOTAL = 0 THEN
                             'S'
                           ELSE 
                             'N' 
                         END ES_REGALO,
                         CASE 
                           WHEN DET.COD_CAMP_CUPON IS NOT NULL AND DET.AHORRO_CAMP > 0 THEN
                             'S'
                           ELSE
                             'N'
                         END ES_DSCTO

                    FROM VTA_PEDIDO_VTA_DET DET
                    
                    JOIN VTA_PEDIDO_VTA_CAB CAB
                      ON CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                     AND CAB.COD_LOCAL = DET.COD_LOCAL
                     AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
                         
                         -- VALORES DE CALCULO DE PUNTOS POR DEFECTO
                         ,
                         (SELECT CASE 
                                   WHEN TO_NUMBER(TRIM(A.COD_APL), '999990.00') = -1 THEN 
                                     NULL 
                                   ELSE 
                                     TO_NUMBER(TRIM(A.COD_APL), '999990.00') 
                                 END PORCENTAJE,
                                 TO_NUMBER(TRIM(A.LLAVE_TAB_GRAL), '999990.00') PT_ACUMULA,
                                 TO_NUMBER(TRIM(A.DESC_CORTA), '999990.00') MONEDA
                           FROM PBL_TAB_GRAL A
                           WHERE A.ID_TAB_GRAL = TAB_IND_DEFAULT_PTS) TAB_GRAL
                         
                         -- KMONCADA 2015.03.27 VALORES PARA AHORRO EN PUNTOS
                         ,
                         (SELECT TO_NUMBER(TRIM(NVL(A.LLAVE_TAB_GRAL,0)), '999990.00') PT_AHORRO,
                                 TO_NUMBER(TRIM(NVL(A.DESC_CORTA,0)), '999990.00') MONEDA_AHORRO
                          FROM PBL_TAB_GRAL A
                          WHERE A.ID_TAB_GRAL = TAB_IND_CALCULO_PTO_AHORRO) TAB_AHORRO
                   
                   WHERE DET.COD_GRUPO_CIA = cCodGrupoCia
                     AND DET.COD_LOCAL = cCodLocal
                     AND DET.NUM_PED_VTA = cNumPedVta
                     -- SOLO CONSIDERA PRODUCTOS QUE EN EL PEDIDO NO SON BONIFICADOS O PROMOCIONES POR PUNTOS
                     AND NVL(DET.IND_BONIFICADO, IND_NO) <> IND_SI
                 
                 ) PRODUCTOS
           ) DETALLE
     -- SOLO SE CONSIDERA LA PRIMERA COINCIDENCIA SEGUN LA LOGICA DE CALCULO DE PUNTOS
     WHERE DETALLE.INDICADOR = 1
     -- EVALUA SI APLICA EL CALCULO DE PUNTOS SEGUN EL TIPO DE CANAL DE VENTA
     AND ((DETALLE.IND_MESON = 'S' AND
                 (DETALLE.TIP_VTA = TIP_VTA_MESON AND
                 (DETALLE.IND_CONV_MESON = 'S' OR
                 DETALLE.IND_CONV_MESON = DETALLE.IND_CONVENIO)))
              OR (DETALLE.IND_DELIVERY = 'S' AND
                 (DETALLE.TIP_VTA = TIP_VTA_DELIVERY AND
                 (DETALLE.IND_CONV_DELIVERY = 'S' OR
                 DETALLE.IND_CONV_DELIVERY = DETALLE.IND_CONVENIO)))
              OR (DETALLE.IND_MAYORISTA = 'S' AND DETALLE.TIP_VTA = TIP_VTA_MAYORISTA))
     -- EN EL CASO DE SER UN PRODUCTO QUE ACUMULA X+1 EVALUA SI PERMITE ACUMULAR PTOS TAMBIEN
     AND (NVL(DETALLE.IND_PROD_MAS_1, IND_NO) = IND_NO OR
                 (NVL(DETALLE.IND_PROD_MAS_1, 'N') = 'S' AND
                 NVL(DETALLE.SOLO_ACUMULA, 'N') = 'N'))
     --KMONCADA 19.05.2015 VALIDA QUE NO SE ENCUENTRE COMO PRODUCTO EXONERADO DE PTOS
     AND DETALLE.COD_PROD NOT IN (SELECT X.COD_PROD 
                                  FROM VTA_AUX_PTOS_EXCLUYE X 
                                  WHERE X.COD_GRUPO_CIA = cCodGrupoCia
                                  AND X.IND_ACUMULA = 'S')
     ORDER BY DETALLE.SEC_PED_VTA_DET ASC;

    vTotalPuntos       NUMBER(9, 3) := 0;
    vPtoProducto       NUMBER(9, 3) := 0;
    vIndicadorRedondeo CHAR(1);
    ptoMultiplica      VTA_CAMPANA_CUPON.PT_MULTIPLICA%TYPE;
    fila               curDetallePtos%ROWTYPE;
    vIndRedimeAcumula  CHAR(1);
    isContinua         CHAR(1) := 'S';
    vInicializa        CHAR(1) := 'S';
    vIncluyeIGV        CHAR(1) := 'S';
    vMonto             VTA_PEDIDO_VTA_DET.VAL_PREC_TOTAL%TYPE;
    vPtoAhorro         NUMBER(9,3) := 0;
    vCantProdRegalo    INTEGER;
    vCantProdDsct      INTEGER;
    vAcumulaConRegalo  CHAR(1);
    vAcumulaConDscto   CHAR(1);
    vRecalculoPtos     CHAR(1);
    vCalculoPtoDsctoProm CHAR(1);
  BEGIN
    vRecalculoPtos := cRecalculoPtos_in;
    -- OBTIENE INDICADOR QUE PERMITE ACUMULAR PTO SI HAY PRODUCTO REGALO A: NO PERMITE I:PERMITE
    BEGIN 
      SELECT TRIM(GRAL.LLAVE_TAB_GRAL)
      INTO vAcumulaConRegalo
      FROM PBL_TAB_GRAL GRAL
      WHERE GRAL.ID_TAB_GRAL = TAB_IND_ACUMULA_PROD_REGALO;
    EXCEPTION 
      WHEN NO_DATA_FOUND THEN
        vAcumulaConRegalo := 'I';
    END;
    
    -- OBTIENE INDICADOR QUE PERMITE ACUMULAR PTO SI HAY PRODUCTO CON DESCUENTO A: NO PERMITE I:PERMITE
    BEGIN 
      SELECT TRIM(GRAL.LLAVE_TAB_GRAL)
      INTO vAcumulaConDscto
      FROM PBL_TAB_GRAL GRAL
      WHERE GRAL.ID_TAB_GRAL = TAB_IND_ACUMULA_PROD_DSCTO;
    EXCEPTION 
      WHEN NO_DATA_FOUND THEN
        vAcumulaConDscto := 'I';
    END;
    
    BEGIN 
      SELECT TRIM(GRAL.LLAVE_TAB_GRAL)
      INTO vCalculoPtoDsctoProm
      FROM PBL_TAB_GRAL GRAL
      WHERE GRAL.ID_TAB_GRAL = TAB_IND_NO_PTOS_POR_PROM_DSCTO;
    EXCEPTION 
      WHEN NO_DATA_FOUND THEN
        vAcumulaConDscto := 'C';
    END;
    
    -- OBTIENE CANTIDAD DE PRODUCTO EN PROMOCIONES DE REGALO
    SELECT COUNT(1)
    INTO vCantProdRegalo
    FROM VTA_PEDIDO_VTA_DET DET
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DET.COD_LOCAL = cCodLocal_in
    AND DET.NUM_PED_VTA = cNumPedVta_in
    AND NVL(DET.IND_BONIFICADO,'N') = 'N'
    AND DET.VAL_PREC_TOTAL = 0;
    
    -- OBTIENE CANTIDAD DE PRODUCTO EN PROMOCIONES DE REGALO
    SELECT COUNT(1)
    INTO vCantProdDsct
    FROM VTA_PEDIDO_VTA_DET DET
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DET.COD_LOCAL = cCodLocal_in
    AND DET.NUM_PED_VTA = cNumPedVta_in
    AND DET.COD_CAMP_CUPON IS NOT NULL
    AND DET.AHORRO > 0;
    
    IF vCalculoPtoDsctoProm = 'C' THEN
      IF vAcumulaConRegalo = 'A' AND vCantProdRegalo > 0 THEN
        isContinua := 'N';
        vInicializa := 'S';
        vRecalculoPtos := 'N';
      END IF;
      
      IF vAcumulaConDscto = 'A' AND vCantProdDsct > 0 THEN
        isContinua := 'N';
        vInicializa := 'S';
        vRecalculoPtos := 'N';
      END IF;
    END IF;
    
    -- KMONCADA 13.05.2015 REGISTRA LOS PUNTOS DE AHORRO PARA TODOS LOS CASOS
    OPEN curDetallePtos(cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in);
      LOOP
        FETCH curDetallePtos
          INTO fila;
        EXIT WHEN curDetallePtos%NOTFOUND;
        -- GRABA POR PRODUCTO LOS PUNTOS ACUMULADOS Y LOS PTOS DE AHORRO
        UPDATE VTA_PEDIDO_VTA_DET A
           SET A.PTOS_AHORRO = FILA.PTO_AHORRO,
			   A.FEC_MOD_PED_VTA_DET = SYSDATE
         WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
           AND A.COD_LOCAL = cCodLocal_in
           AND A.NUM_PED_VTA = cNumPedVta_in
           AND A.SEC_PED_VTA_DET = FILA.SEC_PED_VTA_DET
           AND A.COD_PROD = FILA.COD_PROD;

      END LOOP;
      CLOSE curDetallePtos;
    
    -- PARA EL CASO DE RE-CALCULO DE PUNTOS POR MODIFICACIONES EN MONTOS VENTA O CANTIDADES DE PRODUCTOS
    IF vRecalculoPtos = IND_SI THEN
      -- EN CASO DE RE-CALCULO DE PUNTOS LUEGO DE REDIMIR PUNTOS EN LA VENTA.
      IF cIndRedime_in = IND_SI THEN
        -- OBTIENE INDICADOR QUE DETERMINA CALCULO DE PUNTO LUEGO DE REDIMIR.
        vIndRedimeAcumula := F_IND_ACUMULA_REDIME;
        -- SI REDIME ACUMULA PUNTOS INCLUYENDO LOS MONTOS REDIMIDOS 
        IF vIndRedimeAcumula = REDIME_SI_ACUMULA THEN
          isContinua := IND_NO;
          vInicializa := IND_NO;
        -- SI REDIME ACUMULA PUNTOS RESTANDO LOS MONTOS REDIMIDOS
        ELSIF vIndRedimeAcumula = REDIME_RESTO_ACUMULA THEN
          isContinua := IND_SI;
          vInicializa := IND_SI;
        -- SI REDIME NO ACUMULA PUNTOS
        ELSIF vIndRedimeAcumula = REDIME_NO_ACUMULA THEN
          isContinua := IND_NO;
          vInicializa := IND_SI;
        END IF;
      ELSE
      -- SOLO PARA EL CASO DE RE-CALCULO DE PUNTOS 
        isContinua := IND_SI;
        vInicializa := IND_SI;
      END IF;
    END IF;
    
    IF vInicializa = IND_SI THEN
      -- INICIALIZA LOS PUNTOS DE TODOS LOS PRODUCTOS DEL PEDIDO.
      UPDATE VTA_PEDIDO_VTA_DET A
         SET A.CTD_PUNTOS_ACUM = NULL
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.NUM_PED_VTA = cNumPedVta_in;
      
      -- INICIALIZA LA CABECERA DEL PEDIDO
      UPDATE VTA_PEDIDO_VTA_CAB  A
         SET A.PT_ACUMULADO  = 0,
             A.PT_TOTAL      = A.PT_INICIAL - NVL(A.PT_REDIMIDO,0)
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.NUM_PED_VTA = cNumPedVta_in;

    END IF;
    
    -- INICIA EL CALCULO DE PUNTOS
    IF isContinua = IND_SI THEN
      -- OBTIENE INDICADOR PARA DETERMINAR EL REDONDEO DE LOS PUNTOS OBTENIDOS
      vIndicadorRedondeo := F_CHAR_REDONDEO_CALCULO_PTOS;
      -- OBTIENE INDICADOR PARA DETERMINAR SI SE CALCULA PUNTOS INCLUYENDO 
      -- IGV O NO EN EL MONTO DE VENTA
      vIncluyeIGV := F_IND_CALCULO_CON_IGV;

      OPEN curDetallePtos(cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in);
      LOOP
        FETCH curDetallePtos
          INTO fila;
        EXIT WHEN curDetallePtos%NOTFOUND;
        -- OPERA SEGUN INDICADOR DE IGV
        vMonto := FILA.VAL_PREC_TOTAL;
        IF vIncluyeIGV = IND_NO THEN
          vMonto := ROUND((FILA.VAL_PREC_TOTAL / ((100 + FILA.IGV)/100)),3);
        END IF;
        
        -- CALCULA PUNTOS SEGUN VALOR PORCENTAJE EN MATRIZ DE PUNTOS POR CATEGORIA/PRODUCTO/DEFAULT
        -- CASO CONTRARIO TOMA VALORES DE EQUIVALENCIA PTO -> SOLES.
        
        IF vAcumulaConRegalo = 'A' AND FILA.ES_REGALO = 'S' AND vCalculoPtoDsctoProm = 'D' THEN
          vPtoProducto := 0;
        ELSE 
          IF vAcumulaConDscto = 'A' AND FILA.ES_DSCTO = 'S' AND vCalculoPtoDsctoProm = 'D' THEN
            vPtoProducto := 0;
          ELSE
            IF FILA.PTO_PORCENTAJE IS NULL THEN
              vPtoProducto := ROUND(((vMonto / FILA.MONEDA_ACUMULA) * FILA.PT_ACUMULA), 3);
            ELSE
              vPtoProducto := ROUND( vMonto * (FILA.PTO_PORCENTAJE/100), 3);
            END IF;
          END IF;
        END IF;
      
        DBMS_OUTPUT.PUT_LINE('--------------------------------------' ||
                             'PROD ' || FILA.COD_PROD || CHR(13) || 'PRECIO ' ||
                             vMonto || CHR(13) || 'SOLES X PTO ' ||
                             FILA.MONEDA_ACUMULA || CHR(13) || 'PT ACUMULA ' ||
                             FILA.PT_ACUMULA || CHR(13) || 'SUBTOTAL ' ||
                             vPtoProducto||' x '||TRUNC(vPtoProducto)||CHR(13)||
                             'PTOS DE AHORRO '||FILA.PTO_AHORRO);
      
        -- CONSULTA CAMPAÑA SI TIENE BONIFICACION ADICIONAL EN PUNTOS.
        BEGIN
          SELECT NVL(A.PT_MULTIPLICA, 1)
            INTO ptoMultiplica
            FROM VTA_CAMPANA_CUPON A
           WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
             AND A.COD_CAMP_CUPON = FILA.COD_CAMP_CUPON;
        EXCEPTION
          WHEN OTHERS THEN
            ptoMultiplica := 1;
        END;
        -- OPERA SEGUN BONIFICACION POR CAMPAÑAS
        vPtoProducto := ROUND((vPtoProducto * ptoMultiplica), 3);
      
        -- GRABA POR PRODUCTO LOS PUNTOS ACUMULADOS Y LOS PTOS DE AHORRO
        UPDATE VTA_PEDIDO_VTA_DET A
           SET A.CTD_PUNTOS_ACUM = CASE
                                     WHEN vIndicadorRedondeo = TRUNCATE_DESACTIVADO THEN
                                       vPtoProducto
                                     ELSE
                                       TRUNC(vPtoProducto)
                                   END/*,
               A.PTOS_AHORRO = FILA.PTO_AHORRO*/
                                   
         WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
           AND A.COD_LOCAL = cCodLocal_in
           AND A.NUM_PED_VTA = cNumPedVta_in
           AND A.SEC_PED_VTA_DET = FILA.SEC_PED_VTA_DET
           AND A.COD_PROD = FILA.COD_PROD;
      
        -- APLICA TRUNCAMIENTO SEGUN TAB_GRAL -> CASO DETALLE
        IF vIndicadorRedondeo = TRUNCATE_DETALLE THEN
          vPtoProducto := TRUNC(vPtoProducto);
        END IF;
      
        vTotalPuntos := vTotalPuntos + vPtoProducto;
      END LOOP;
      CLOSE curDetallePtos;
      
      -- APLICA TRUNCAMIENTO SEGUN TAB_GRAL -> CASO CABECERA
      IF vIndicadorRedondeo = TRUNCATE_CABACERA THEN
        vTotalPuntos := TRUNC(vTotalPuntos);
      END IF;

      -- ACTUALIZA EL TOTAL DE PUNTOS OBTENIDOS EN CABECERA DE PEDIDO
      UPDATE VTA_PEDIDO_VTA_CAB A
         SET A.PT_ACUMULADO  = vTotalPuntos,
             A.PT_TOTAL      = A.PT_INICIAL + vTotalPuntos - NVL(A.PT_REDIMIDO,0),
             A.EST_TRX_ORBIS = 'P'
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.NUM_PED_VTA = cNumPedVta_in;

      -- REALIZA CUADRE DE TOTAL DE PUNTOS CON EL DETALLE DE PEDIDO
      UPDATE VTA_PEDIDO_VTA_DET A
         SET A.CTD_PUNTOS_ACUM = A.CTD_PUNTOS_ACUM +
                                 NVL((SELECT (CAB.PT_ACUMULADO -
                                            SUM(DET.CTD_PUNTOS_ACUM))
                                       FROM VTA_PEDIDO_VTA_CAB CAB,
                                            VTA_PEDIDO_VTA_DET DET
                                      WHERE CAB.COD_GRUPO_CIA =
                                            DET.COD_GRUPO_CIA
                                        AND CAB.COD_LOCAL = DET.COD_LOCAL
                                        AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
                                        AND CAB.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                                        AND CAB.COD_LOCAL = A.COD_LOCAL
                                        AND CAB.NUM_PED_VTA = A.NUM_PED_VTA
                                      GROUP BY CAB.PT_ACUMULADO),
                                     0)
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.NUM_PED_VTA = cNumPedVta_in
         AND A.SEC_PED_VTA_DET = 1;


      DBMS_OUTPUT.put_line('TOTAL DE PRODUCTOS ACUMULAR -->' || vTotalPuntos ||
                           ' - TRUNC --> ' || TRUNC(vTotalPuntos));
    END IF;

  END;
  
  FUNCTION F_CUR_V_LISTA_PRODUCTO(cCodGrupoCia_in   IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                                  cCodLocal_in      IN VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                                  cNumPedVta_in     IN VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE, 
                                  cConBonificado_in IN CHAR DEFAULT 'S',
                                  cFlag_in          IN CHAR DEFAULT 'N',
                                  cIndAnulaPed_in   IN CHAR DEFAULT 'N'
                                  )
    RETURN FARMACURSOR IS
    pCadena             VARCHAR2(5000);
    vCodProdEquivalente VTA_PEDIDO_VTA_DET.COD_PROD_PUNTOS%TYPE;
    vCantidad           NUMBER(9,3);
    vImporte            VTA_PEDIDO_VTA_DET.VAL_PREC_TOTAL%TYPE;
    cQuote              FARMACURSOR;
    vIndConvenio        CHAR(1):='N';
    
    CURSOR curDetallePed IS
      SELECT DET.COD_PROD,
             SUM(ABS(NVL(DET.CTD_PUNTOS_ACUM,0))) CTD_PUNTOS_ACUM,
             DET.IND_PROD_MAS_1,
             DET.COD_PROD_PUNTOS,
             SUM(DET.CANT_ATENDIDA) CANT_ATENDIDA,
             DET.VAL_FRAC,
             SUM(DET.VAL_PREC_TOTAL) VAL_PREC_TOTAL,
             B.VAL_MAX_FRAC,
             DET.VAL_FRAC_LOCAL,
             SUM(ABS(NVL(DET.PTOS_AHORRO,0))) PTOS_AHORRO
        FROM VTA_PEDIDO_VTA_DET DET, 
             LGT_PROD B
       WHERE DET.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND DET.COD_PROD      = B.COD_PROD
         AND DET.COD_GRUPO_CIA = cCodGrupoCia_in
         AND DET.COD_LOCAL     = cCodLocal_in
         AND DET.NUM_PED_VTA   = cNumPedVta_in
       GROUP BY DET.COD_PROD,
                DET.IND_PROD_MAS_1,
                DET.COD_PROD_PUNTOS,
                DET.VAL_FRAC,
                B.VAL_MAX_FRAC,
                DET.VAL_FRAC_LOCAL;

    filaCursor curDetallePed%ROWTYPE;
  BEGIN
    -- INDICADOR DE CONVENIO DEL PEDIDO
    SELECT NVL(A.IND_CONV_BTL_MF,'N')
    INTO vIndConvenio
    FROM VTA_PEDIDO_VTA_CAB A
    WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND A.COD_LOCAL       = cCodLocal_in
    AND A.NUM_PED_VTA     = cNumPedVta_in;
    
    OPEN curDetallePed;
      LOOP
        FETCH curDetallePed INTO filaCursor;
        EXIT WHEN curDetallePed%NOTFOUND;

        DBMS_OUTPUT.put_line('*********************'||CHR(13)||
                           'COD_PRODUCTO --> ' || filaCursor.COD_PROD || CHR(13) || 
                           'PUNTOS--> ' || filaCursor.CTD_PUNTOS_ACUM || CHR(13) || 
                           'X+1--> ' || filaCursor.IND_PROD_MAS_1 || CHR(13) ||
                           'INDICADOR X+1 -->' || filaCursor.IND_PROD_MAS_1 || CHR(13) || 
                           'PROD EQUI -->' || filaCursor.COD_PROD_PUNTOS || CHR(13) ||
                           'CANT_ATENDIDA --> '||filaCursor.CANT_ATENDIDA || CHR(13) ||
                           'VAL_FRAC_LOCAL --> '||filaCursor.VAL_FRAC_LOCAL || CHR(13) ||
                           'VAL_FRAC --> ' || filaCursor.VAL_FRAC || CHR(13) ||
                           'VAL_FRAC_MAX --> '||filaCursor.VAL_MAX_FRAC||CHR(13)||
                           'PTOS_AHORRO -->'||filaCursor.PTOS_AHORRO);
        
        -- NO CONSIDERA LOS CODIGOS DE PRODUCTOS QUE NO ACUMULAN PUNTOS PERO SI BONIFICAN
        -- +0 N / 0 N / +0 S
        IF NOT (NVL(filaCursor.CTD_PUNTOS_ACUM, 0) = 0 AND NVL(filaCursor.IND_PROD_MAS_1, 'N') = 'S') THEN 
          -- SI EL PRODUCTO PERTENECE A PROGRAMA X+1 Y ACUMULA PUNTOS SE ENVIA CANTIDAD PRODUCTOS CERO(0)
          -- +0 S
          /*
          IF NVL(filaCursor.IND_PROD_MAS_1, 'N') = 'S' AND NVL(filaCursor.CTD_PUNTOS_ACUM, 0) > 0 THEN
            0
          ELSE
            vCantidad := ((filaCursor.CANT_ATENDIDA * filaCursor.VAL_FRAC_LOCAL) / filaCursor.VAL_FRAC);
          END IF;
          */
          -- SE ENVIARA CANTIDAD DE PRODUCTOS EN TODOS LOS CASOS
          vCantidad := ((filaCursor.CANT_ATENDIDA * filaCursor.VAL_FRAC_LOCAL) / filaCursor.VAL_FRAC);
        
          IF LENGTH(TRIM(pCadena)) != 0 THEN
            pCadena := pCadena || '@';
          END IF;
          
          -- PARA EL CASO DE LISTA DE PRODUCTO DE ANULACION DE PEDIDO SE ENVIAN CODIGO Y CANTIDAD 
          IF cIndAnulaPed_in = 'N' THEN
            /*
            pCadena := pCadena || 
                         filaCursor.COD_PROD || ',' ||  -- CODIGO DE PROD
                         TRIM(TO_CHAR(NVL(vCantidad,0),'9999990.00')) || ',' || -- CANTIDAD DE PROD
                         TRIM(TO_CHAR(NVL(filaCursor.CTD_PUNTOS_ACUM, 0), '99999990.00')) || ',' || -- PUNTOS ACUMULADOS
                         TRIM(TO_CHAR(NVL(filaCursor.VAL_PREC_TOTAL,0), '99999990.00')); -- IMPORTE
            */
            -- SE MODIFICA LISTA DE PRODUCTO PARA ENVIO A ORBIS
            pCadena := pCadena || 
                       filaCursor.COD_PROD || ',' || -- CODIGO DE PROD
                       TRIM(TO_CHAR(NVL(vCantidad,0),'9999990.00')) || ',' || -- CANTIDAD DE PROD
                       '1' || ',' || -- PRECIO UNITARIO
                       TRIM(TO_CHAR(NVL(filaCursor.VAL_PREC_TOTAL,0), '99999990.00')) || ',' || -- IMPORTE TOTAL PROD
                       TRIM(TO_CHAR(NVL(filaCursor.CTD_PUNTOS_ACUM, 0)+NVL(filaCursor.PTOS_AHORRO, 0), '99999990.00')) || ',' || -- PUNTOS ACUMULADO + PTOS EXTRA
                       TRIM(TO_CHAR(NVL(filaCursor.PTOS_AHORRO, 0), '99999990.00')); -- PUNTOS EXTRA
          ELSE
            pCadena := pCadena || filaCursor.COD_PROD || ',' || TRIM(TO_CHAR(NVL(vCantidad,0),'9999990.00'));
          END IF;           
        END IF;

        -- PEDIDOS DE CONVENIO NO PARTICIPAN EN X+1
        IF vIndConvenio = 'N' AND cConBonificado_in = 'S' THEN
          -- PARA EL CASO DE PRODUCTOS QUE PARTICIPAN EN X+1 SE OBTIENE SU CODIGO EQUIVALENTE DEL PROGRAMA
          IF NVL(filaCursor.IND_PROD_MAS_1, 'N') = 'S' THEN
            vCodProdEquivalente := TRIM(filaCursor.COD_PROD_PUNTOS);
            -- CALCULO DE CANTIDAD DEL PRODUCTO 
            -- PRODUCTO PARTICIPA COMO ENTERO
            IF SUBSTR(vCodProdEquivalente, 7, 2) = IND_EQUI_ENTERO THEN
              DBMS_OUTPUT.put_line('ENTERO');
              vCantidad := (filaCursor.CANT_ATENDIDA  / filaCursor.VAL_FRAC);
            -- PRODUCTO PARTICIPA COMO FRACCION EN SU MAXIMO FRACCIONABLE 
            ELSIF SUBSTR(vCodProdEquivalente, 7, 2) = IND_EQUI_FRACCION THEN
              DBMS_OUTPUT.put_line('FRACC');
              vCantidad := (filaCursor.CANT_ATENDIDA  / filaCursor.VAL_FRAC) * filaCursor.VAL_MAX_FRAC;
            END IF;
            
            -- EN CASO DE BONIFICADOS SI PRODUCTO ORIGINAL NO ACUMULO PUNTOS SE ENVIA PRECIO TOTAL VTA
            -- CASO CONTRARIO SE ENVIA 0.
            IF NVL(filaCursor.CTD_PUNTOS_ACUM, 0) = 0 THEN
              vImporte := filaCursor.VAL_PREC_TOTAL;
            ELSE
              vImporte := 0;
            END IF;
          
            IF LENGTH(TRIM(pCadena)) != 0 THEN
              pCadena := pCadena || '@';
            END IF;
            
            -- PARA EL CASO DE LISTA DE PRODUCTO DE ANULACION DE PEDIDO SE ENVIAN CODIGO Y CANTIDAD 
            IF cIndAnulaPed_in = 'N' THEN
              /*
              pCadena := pCadena || 
                         filaCursor.COD_PROD_PUNTOS || ',' ||  -- CODIGO DE PROD
                         TRIM(TO_CHAR(NVL(vCantidad,0),'9999990.00')) || ',' || -- CANTIDAD DE PROD
                         '0' || ',' ||  -- PUNTOS ACUMULADOS
                         TRIM(TO_CHAR(NVL(vImporte,0), '9999990.00')); -- IMPORTE TOTAL PROD
              */
              -- SE MODIFICA LISTA DE PRODUCTO PARA ENVIO A ORBIS
              pCadena := pCadena || 
                         filaCursor.COD_PROD_PUNTOS || ',' || -- CODIGO DE PROD
                         TRIM(TO_CHAR(NVL(vCantidad,0),'9999990.00')) || ',' || -- CANTIDAD DE PROD
                         '0' || ',' || -- PRECIO UNITARIO
                         TRIM(TO_CHAR(NVL(vImporte,0), '9999990.00')) || ',' || -- IMPORTE TOTAL PROD
                         '0' || ',' ||  -- PUNTOS ACUMULADO + PTOS EXTRA
                         '0'; -- PUNTOS EXTRA
                         
            ELSE
              pCadena := pCadena || filaCursor.COD_PROD_PUNTOS || ',' || TRIM(TO_CHAR(NVL(vCantidad,0),'9999990.00')) ;
            END IF;  
          END IF;
        
        END IF;
      END LOOP;
    CLOSE curDetallePed;
    -- FORMATO DE DEVOLUCION DE CURSOR N: UNA COLUMNA CON VALORES SEPARDOS POR , S: MATRIZ
    IF cFlag_in = 'N' THEN
       OPEN cQuote FOR
         SELECT EXTRACTVALUE(xt.column_value, 'e') VAL
                FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                                       REPLACE((pCadena),
                                                               '@',
                                                               '</e><e>') ||
                                                       '</e></coll>'),
                                               '/coll/e'))) xt;/*) a;*/
    ELSE
      OPEN cQuote FOR
        /*
        SELECT SUBSTR(val, 1, INSTR(val, ',', 1, 1) - 1) ARTICULO,
               SUBSTR(val, INSTR(val, ',', 1, 1) + 1, INSTR(val, ',', 1, 2) - INSTR(val, ',', 1, 1) - 1) CANTIDAD,
               SUBSTR(val,INSTR(val, ',', 1, 2) + 1, INSTR(val, ',', 1, 3) - INSTR(val, ',', 1, 2) - 1) PUNTOS,
               SUBSTR(val, INSTR(val, ',', 1, 3) + 1) IMPORTE
        */
        SELECT TRIM(SUBSTR(val, 1, INSTR(val, ',', 1, 1) - 1)) ARTICULO,
               TRIM(SUBSTR(val, INSTR(val, ',', 1, 1) + 1, INSTR(val, ',', 1, 2) - INSTR(val, ',', 1, 1) - 1)) CANTIDAD,
               TRIM(SUBSTR(val, INSTR(val, ',', 1, 2) + 1, INSTR(val, ',', 1, 3) - INSTR(val, ',', 1, 2) - 1)) PRECIO_UNIT,
               TRIM(SUBSTR(val, INSTR(val, ',', 1, 3) + 1, INSTR(val, ',', 1, 4) - INSTR(val, ',', 1, 3) - 1)) IMPORTE,
               TRIM(SUBSTR(val, INSTR(val, ',', 1, 4) + 1, INSTR(val, ',', 1, 5) - INSTR(val, ',', 1, 4) - 1)) PUNTOS,
               TRIM(SUBSTR(val, INSTR(val, ',', 1, 5) + 1)) PUNTOS_AHORRO
        FROM (
               SELECT EXTRACTVALUE(xt.column_value, 'e') VAL
               FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                           REPLACE((pCadena),
                                                   '@',
                                                   '</e><e>') ||
                                           '</e></coll>'),
                                   '/coll/e'))) xt) a;
    END IF;
    DBMS_OUTPUT.put_line(pCadena);
    RETURN cQuote;
  END;
  
  FUNCTION F_CHAR_IND_PANTALLA_BONIFICA 
    RETURN CHAR IS
    vIndicador CHAR(1);
  BEGIN
    
    SELECT TRIM(A.DESC_CORTA)
    INTO vIndicador
    FROM PBL_TAB_GRAL A 
    WHERE A.ID_TAB_GRAL = TAB_IND_PANTALLA_BONIFICADO;
    
    RETURN vIndicador;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        RETURN 'N';
  END;
  
  FUNCTION F_CHAR_IND_DOC_BONIFICA
    RETURN CHAR IS
    vIndicador CHAR(1);
  BEGIN
    BEGIN 
      SELECT TRIM(A.DESC_CORTA)
      INTO vIndicador
      FROM PBL_TAB_GRAL A 
      WHERE A.ID_TAB_GRAL = TAB_IND_DOC_BONIFICADO;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        vIndicador := 'N';
    END;
    RETURN vIndicador;
  END;
  
  FUNCTION F_CHAR_TIEMPO_MAX_LECTORA
    RETURN CHAR IS
    vIndicador PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    BEGIN 
      SELECT TRIM(A.DESC_CORTA)
      INTO vIndicador
      FROM PBL_TAB_GRAL A 
      WHERE A.ID_TAB_GRAL = TAB_IND_LECTOR_TARJETA_PISTOLA;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        vIndicador := '200';
    END;
    RETURN vIndicador;
  END;
  
  FUNCTION F_LST_MONTOS_REDENCION(cCodGrupoCia_in IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                                  cCodLocal_in    IN VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                                  cNumPedVta_in   IN VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE)
    RETURN FARMACURSOR IS
    vCursor             FARMACURSOR;
    vPtosInicial        VTA_PEDIDO_VTA_CAB.PT_INICIAL%TYPE;
    vValNetoPed         VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    vNuevoAlgoritmo     CHAR(1);
    vMultiplo           NUMBER(9);
    vPrioridadCategoria INTEGER;
    vPrioridadProducto  INTEGER;
    vPrioridadDefault   INTEGER;
  BEGIN
    
    SELECT NVL(CAB.PT_INICIAL,0), CAB.VAL_NETO_PED_VTA
    INTO vPtosInicial, vValNetoPed
    FROM VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;
    
    SELECT TRIM(W.LLAVE_TAB_GRAL) 
    INTO vNuevoAlgoritmo 
    FROM PBL_TAB_GRAL W 
    WHERE W.ID_TAB_GRAL = TAB_IND_TIPO_ALGORITMO_REDIME;
    
    SELECT TO_NUMBER(TRIM(T.LLAVE_TAB_GRAL))
    INTO vMultiplo
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = TAB_IND_MULTIPLO_REDIME;
    
    SELECT TO_NUMBER(SUBSTR(VAL, 1, INSTR(VAL, ',', 1, 1) - 1), '999') CAT,
           TO_NUMBER(SUBSTR(VAL,
                            INSTR(VAL, ',', 1, 1) + 1,
                            INSTR(VAL, ',', 1, 2) - INSTR(VAL, ',', 1, 1) - 1),
                     '999') PROD,
           TO_NUMBER(SUBSTR(VAL, INSTR(val, ',', 1, 2) + 1), '999') DEF
    INTO   vPrioridadCategoria,
           vPrioridadProducto,
           vPrioridadDefault
      FROM (select A.LLAVE_TAB_GRAL VAL
              from pbl_tab_gral a
             where a.id_tab_gral = 692) VAL;

    
    OPEN vCursor FOR
      SELECT 
        TRIM(TO_CHAR(
          CASE 
            WHEN MOD(REDIME.PTOS_MAXIMO,vMultiplo)=0 THEN 
              REDIME.PTOS_MAXIMO
            WHEN REDIME.PTOS_MAXIMO < vMultiplo THEN
              0
            WHEN REDIME.PTOS_MAXIMO > vMultiplo THEN
              TRUNC(REDIME.PTOS_MAXIMO / vMultiplo) * vMultiplo
            ELSE 
              0
          END 
        , '9999990.00')) PTOS_MAXIMO,
        TRIM(TO_CHAR(
          CASE 
            WHEN MOD(REDIME.PTOS_MAXIMO,vMultiplo)=0 THEN 
              REDIME.MONTO_MAXIMO
            WHEN REDIME.PTOS_MAXIMO < vMultiplo THEN
              0
            WHEN REDIME.PTOS_MAXIMO > vMultiplo THEN
              ((TRUNC(REDIME.PTOS_MAXIMO / vMultiplo) * vMultiplo) * REDIME.MONTO_MAXIMO) / REDIME.PTOS_MAXIMO
            ELSE 
              0
          END 
        , '9999990.00')) MONTO_MAXIMO
      FROM (
        SELECT /*TRIM(TO_CHAR(nvl(SUM(PTOS_MAXIMO), 0), '9999990.00')) PTOS_MAXIMO,
               TRIM(TO_CHAR(NVL(SUM(MONTO_MAXIMO), 0), '9999990.00')) MONTO_MAXIMO,*/
               
               --TRIM(TO_CHAR(
               CASE
                 WHEN vNuevoAlgoritmo = '0' THEN
                   CASE 
                     WHEN NVL(SUM(PTOS_MAXIMO), 0) > vPtosInicial THEN
                       vPtosInicial
                     WHEN vPtosInicial = 0 THEN
                       0
                     ELSE
                       NVL(SUM(PTOS_MAXIMO), 0)
                   END
                 ELSE
                   NVL(SUM(PTOS_MAXIMO), 0)
               END /*, '9999990.00'))*/ PTOS_MAXIMO,
               
               --TRIM(TO_CHAR(
               CASE
                 WHEN vNuevoAlgoritmo = '0' THEN
                   CASE 
                     WHEN NVL(SUM(PTOS_MAXIMO), 0) > vPtosInicial THEN
                       (vPtosInicial * NVL(SUM(MONTO_MAXIMO), 0)) / NVL(SUM(PTOS_MAXIMO), 0)
                     WHEN vPtosInicial = 0 THEN
                       0
                     ELSE
                       NVL(SUM(MONTO_MAXIMO), 0)
                   END
                 ELSE
                   NVL(SUM(MONTO_MAXIMO), 0)
               END /*, '9999990.00'))*/ MONTO_MAXIMO
               
        FROM (SELECT CASE
                       WHEN A.INDICA = '1' THEN -- TRUNCATE CABECERA
                         TRUNC(SUM(CASE 
                                     WHEN A.INDICA = '0' THEN -- TRUNCATE DEFAULT
                                       PTOS_MAXIMO
                                     ELSE
                                       TRUNC(PTOS_MAXIMO)
                                   END))
                       ELSE
                         SUM(CASE
                               WHEN A.INDICA = '0' THEN
                                 PTOS_MAXIMO
                               ELSE
                                 TRUNC(PTOS_MAXIMO)
                               END)
                       END PTOS_MAXIMO,
                     SUM(MONTO_MAXIMO) MONTO_MAXIMO
          
              FROM (SELECT F_CHAR_REDONDEO_CALCULO_PTOS INDICA FROM DUAL) A,
                   (SELECT CASE 
                             WHEN VTA_MANUAL = 'S' THEN
                               0
                             WHEN vNuevoAlgoritmo = '0' THEN
                               (NVL(DETALLE.VAL_PREC_TOTAL,0) * DETALLE.PT_REDIME) / NVL(DETALLE.MONEDA_REDIME,1)
                             WHEN DETALLE.PTO_PORCENTAJE IS NOT NULL THEN
                               (NVL(DETALLE.VAL_PREC_TOTAL,0) * (NVL(DETALLE.PTO_PORCENTAJE,0) / 100))
                             WHEN NVL(DETALLE.PT_REDIME, 0) > 0 THEN
                               (NVL(DETALLE.VAL_PREC_TOTAL,0) / DETALLE.MONEDA_REDIME)
                             ELSE
                               0
                            END PTOS_MAXIMO,
                         
                            CASE
                              WHEN VTA_MANUAL = 'S' THEN
                                0
                              WHEN vNuevoAlgoritmo = '0' THEN
                                NVL(DETALLE.VAL_PREC_TOTAL,0)
                              WHEN DETALLE.PTO_PORCENTAJE IS NOT NULL THEN
                                (NVL(DETALLE.VAL_PREC_TOTAL,0) * (NVL(DETALLE.PTO_PORCENTAJE,0) / 100))
                              WHEN NVL(DETALLE.PT_REDIME, 0) > 0 THEN
                                ((NVL(DETALLE.VAL_PREC_TOTAL,0) * NVL(DETALLE.PT_REDIME,0)) / DETALLE.MONEDA_REDIME)
                              ELSE
                                0
                            END MONTO_MAXIMO
                 
                    FROM 
                    
                    (SELECT PRODUCTOS.*,
                                 RANK() OVER(PARTITION BY PRODUCTOS.COD_PROD ORDER BY DECODE(PRODUCTOS.ORIGEN, 'CAT', vPrioridadCategoria, 'PRO', vPrioridadProducto, 'DEF', vPrioridadDefault)) INDICADOR
                
                          FROM ( SELECT 'CAT' ORIGEN,
                                 DET.COD_LOCAL,
                                 DET.NUM_PED_VTA,
                                 DET.SEC_PED_VTA_DET,
                                 DET.COD_PROD,
                                 DET.VAL_PREC_TOTAL,
                                 DET.IND_BONIFICADO,
                                 DET.IND_PROD_MAS_1,
                                 CAT.PT_REDIME,
                                 CAT.MONEDA_REDIME,
                                 CAT.IND_MESON,
                                 --CAT.IND_CONV_MESON,
                                 -- KMONCADA 03.07.2015 TEMPORALMENTE LOS CONVENIOS NO REDIMEN PTOS
                                 'N' IND_CONV_MESON,
                                 CAT.IND_DELIVERY,
                                 CAT.IND_CONV_DELIVERY,
                                 CAT.IND_MAYORISTA,
                                 CAT.PORCENTAJE PTO_PORCENTAJE,
                                 CAB.TIP_PED_VTA TIP_VTA,
                                 NVL(CAB.IND_CONV_BTL_MF, 'N') IND_CONVENIO,
                                 NVL(CAB.IND_COMP_MANUAL,'N') VTA_MANUAL
                        FROM  VTA_PEDIDO_VTA_DET DET
                        JOIN VTA_PEDIDO_VTA_CAB CAB
                          ON CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                         AND CAB.COD_LOCAL = DET.COD_LOCAL
                         AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
                        JOIN LGT_PROD P
                          ON P.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                         AND P.COD_PROD = DET.COD_PROD
                        JOIN VTA_PUNTOS_CATEGORIA CAT
                          ON CAT.COD_CATEGORIA = P.COD_GRUPO_REP_EDMUNDO
                       WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND DET.COD_LOCAL = cCodLocal_in
                         AND DET.NUM_PED_VTA = cNumPedVta_in
                         AND NVL(DET.IND_BONIFICADO, 'N') <> 'S'
                         AND CAT.ESTADO = 'A'
                      UNION
                        
                      SELECT 'PRO' ORIGEN,
                             DET.COD_LOCAL,
                             DET.NUM_PED_VTA,
                             DET.SEC_PED_VTA_DET,
                             DET.COD_PROD,
                             DET.VAL_PREC_TOTAL,
                             DET.IND_BONIFICADO,
                             DET.IND_PROD_MAS_1,
                             PROD.PT_REDIME,
                             PROD.MONEDA_REDIME,
                             PROD.IND_MESON,
                             --PROD.IND_CONV_MESON,
                             -- KMONCADA 03.07.2015 TEMPORALMENTE LOS CONVENIOS NO REDIMEN PTOS
                             'N' IND_CONV_MESON,
                             PROD.IND_DELIVERY,
                             PROD.IND_CONV_DELIVERY,
                             PROD.IND_MAYORISTA,
                             PROD.PORCENTAJE PTO_PORCENTAJE,
                             CAB.TIP_PED_VTA TIP_VTA,
                             NVL(CAB.IND_CONV_BTL_MF, 'N') IND_CONVENIO,
                             NVL(CAB.IND_COMP_MANUAL,'N') VTA_MANUAL
                        
                        FROM  VTA_PEDIDO_VTA_DET DET
                        JOIN VTA_PEDIDO_VTA_CAB CAB
                          ON CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                         AND CAB.COD_LOCAL = DET.COD_LOCAL
                         AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
                          
                        JOIN VTA_PUNTOS_PRODUCTO PROD
                          ON PROD.COD_PROD = DET.COD_PROD
                          
                       WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND DET.COD_LOCAL = cCodLocal_in
                         AND DET.NUM_PED_VTA = cNumPedVta_in
                              
                         AND NVL(DET.IND_BONIFICADO, 'N') <> 'S'
                         AND PROD.ESTADO = 'A'
                        
                      UNION
                        
                      SELECT 'DEF' ORIGEN,
                             DET.COD_LOCAL,
                             DET.NUM_PED_VTA,
                             DET.SEC_PED_VTA_DET,
                             DET.COD_PROD,
                             DET.VAL_PREC_TOTAL,
                             DET.IND_BONIFICADO,
                             DET.IND_PROD_MAS_1,
                               
                             TAB_GRAL.PT_REDIME,
                             TAB_GRAL.MONEDA_REDIME,
                               
                               
                             DECODE(CAB.TIP_PED_VTA, '01', 'S', 'N') IND_MESON,
                             --NVL(CAB.IND_CONV_BTL_MF, 'N') IND_CONV_MESON,
                             -- KMONCADA 03.07.2015 TEMPORALMENTE LOS CONVENIOS NO REDIMEN PTOS
                             'N' IND_CONV_MESON,
                             DECODE(CAB.TIP_PED_VTA, '02', 'S', 'N') IND_DELIVERY,
                             NVL(CAB.IND_CONV_BTL_MF, 'N') IND_CONV_DELIVERY,
                             DECODE(CAB.TIP_PED_VTA, '03', 'S', 'N') IND_MAYORISTA,
                             TAB_GRAL.PORCENTAJE PTO_PORCENTAJE,
                             CAB.TIP_PED_VTA TIP_VTA,
                             NVL(CAB.IND_CONV_BTL_MF, 'N') IND_CONVENIO,
                             NVL(CAB.IND_COMP_MANUAL,'N') VTA_MANUAL
                        
                        FROM VTA_PEDIDO_VTA_DET DET
                        JOIN VTA_PEDIDO_VTA_CAB CAB
                          ON CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                         AND CAB.COD_LOCAL = DET.COD_LOCAL
                         AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA,
                       (SELECT CASE 
                                 WHEN TO_NUMBER(TRIM(A.COD_APL), '999990.00') = -1 THEN 
                                   NULL 
                                 ELSE 
                                   TO_NUMBER(TRIM(A.COD_APL), '999990.00') 
                               END PORCENTAJE,
                               TO_NUMBER(TRIM(A.LLAVE_TAB_GRAL), '999990.00') PT_REDIME,
                               TO_NUMBER(TRIM(A.DESC_CORTA), '999990.00') MONEDA_REDIME
                        FROM PBL_TAB_GRAL A
                        WHERE A.ID_TAB_GRAL = TAB_IND_DEFAULT_PTS_REDIME) TAB_GRAL -- PTOS
                        
                       WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND DET.COD_LOCAL = cCodLocal_in
                         AND DET.NUM_PED_VTA = cNumPedVta_in
                              
                         AND NVL(DET.IND_BONIFICADO, 'N') <> 'S') PRODUCTOS) DETALLE
       WHERE DETALLE.INDICADOR = 1
       AND ((DETALLE.IND_MESON = 'S' AND
                   (DETALLE.TIP_VTA = TIP_VTA_MESON AND
                   (DETALLE.IND_CONV_MESON = 'S' OR
                   DETALLE.IND_CONV_MESON = DETALLE.IND_CONVENIO)))
                OR (DETALLE.IND_DELIVERY = 'S' AND
                   (DETALLE.TIP_VTA = TIP_VTA_DELIVERY AND
                   (DETALLE.IND_CONV_DELIVERY = 'S' OR
                   DETALLE.IND_CONV_DELIVERY = DETALLE.IND_CONVENIO)))
                OR (DETALLE.IND_MAYORISTA = 'S' AND DETALLE.TIP_VTA = TIP_VTA_MAYORISTA))
       --KMONCADA 19.05.2015 VALIDA QUE NO SE ENCUENTRE COMO PRODUCTO EXONERADO DE PTOS
       AND DETALLE.COD_PROD NOT IN (SELECT X.COD_PROD 
                                    FROM VTA_AUX_PTOS_EXCLUYE X 
                                    WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND X.IND_REDIME = 'S')
       ORDER BY DETALLE.SEC_PED_VTA_DET ASC))
     ) REDIME
     ;
    RETURN vCursor;
  END;
  
  FUNCTION F_LST_VALIDA_BONIFICADOS(cCodGrupoCia_in IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                               cCodLocal_in         IN VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                               cNumPedVta_in        IN VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                               cListaBonifica_in    IN VARCHAR2)
    RETURN FARMACURSOR IS
    

    CURSOR vRsptaQuote IS
      SELECT SUBSTR(VAL, 1, INSTR(VAL, ',', 1, 1) - 1) COD_PROD,
             SUBSTR(VAL, INSTR(VAL, ',', 1, 1) + 1, INSTR(VAL, ',', 1, 2) - INSTR(VAL, ',', 1, 1) - 1) CANT_PROD,
  --         substr(val, instr(val, ',', 1, 2) + 1, instr(val, ',', 1, 3) - instr(val, ',', 1, 2) - 1) X,
             SUBSTR(VAL, INSTR(VAL, ',', 1, 3) + 1, INSTR(VAL, ',', 1, 4) - INSTR(VAL, ',', 1, 3) - 1) CANT_BONIF
  --         substr(val, instr(val, ',', 1, 4) + 1) Y
      FROM (
             SELECT EXTRACTVALUE(xt.column_value, 'e') VAL
             FROM TABLE(
                    XMLSEQUENCE(
                      EXTRACT(
                        XMLTYPE('<coll><e>' ||REPLACE((TRIM(cListaBonifica_in)),'@','</e><e>') ||
                               '</e></coll>'),
                       '/coll/e'))) xt);
     
    vCursor            FarmaCursor;
    vCursorBonificado  FarmaCursor;
    vCodProd           VARCHAR2(50);
    vCantidad          VARCHAR2(50);
    vPuntos            VARCHAR2(50);
    vImporte           VARCHAR2(50);
    vPrecioUnit        VARCHAR2(50);
    vPtosAhorro        VARCHAR2(50);
    fila               vRsptaQuote%rowtype;
    vCorrelativo       INTEGER := 1;

  BEGIN
    -- BORRA DATOS ANTERIORES
    DELETE FROM AUX_VALIDA_QUOTE_DET
    WHERE cod_grupo_cia = cCodGrupoCia_in 
    AND cod_local       = cCodLocal_in 
    AND num_ped_vta     = cNumPedVta_in;
    
    -- INSERTA LOS PRODUCTOS ORIGINALES DE LA RESPUESTA DE QUOTE
    OPEN vRsptaQuote;
      LOOP
      FETCH vRsptaQuote into fila;
      EXIT WHEN vRsptaQuote%NOTFOUND;
        INSERT INTO AUX_VALIDA_QUOTE_DET (VAL_FRAC, COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, SECUENCIAL, COD_PROD, CANTIDAD_AQ, CANTIDAD_DQ, CANT_BONIF)
        SELECT -- DETERMINA EL VALOR DE FRACCION DEL PRODUCTO
               CASE
                 WHEN SUBSTR(FILA.COD_PROD, 7, 2) = IND_EQUI_ENTERO THEN
                   1
                 WHEN SUBSTR(FILA.COD_PROD, 7, 2) = IND_EQUI_FRACCION THEN
                   A.VAL_MAX_FRAC
                 WHEN LENGTH(TRIM(FILA.COD_PROD)) = 6 THEN
                   B.VAL_FRAC_LOCAL
                 ELSE
                   0
               END,
               cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in, vCorrelativo, FILA.COD_PROD, 0, TO_NUMBER(fila.CANT_PROD, '9999990.00'), 0
        FROM LGT_PROD A, 
             LGT_PROD_LOCAL B
        WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
        AND B.COD_LOCAL       = cCodLocal_in
        AND B.COD_PROD        = SUBSTR(FILA.COD_PROD, 0, 6)
        AND A.COD_GRUPO_CIA   = B.COD_GRUPO_CIA
        AND A.COD_PROD        = B.COD_PROD
        AND TO_NUMBER(fila.CANT_PROD, '9999990.00') > 0;
        
        -- CUANDO REALIZA INSERCION AUMENTA EL CORRELATIVO
        IF NOT SQL%NOTFOUND THEN
          vCorrelativo := vCorrelativo +1;
        END IF;
     END LOOP;
    CLOSE vRsptaQuote;

    -- OBTIENE LA LISTA DE PRODUCTOS QUE SE ENVIO AL QUOTE EN FORMATO DE MATRIZ
    vCursor := F_CUR_V_LISTA_PRODUCTO(cCodGrupoCia_in => cCodGrupoCia_in, 
                                                        cCodLocal_in => cCodLocal_in, 
                                                        cNumPedVta_in => cNumPedVta_in,
                                                        cConBonificado_in => 'S',
                                                        cFlag_in => 'S');
    LOOP
      -- OBTIENE LOS DATOS DE LOS CAMPOS DE LA FILA
      FETCH vCursor INTO vCodProd, vCantidad, vPrecioUnit, vImporte, vPuntos , vPtosAhorro;
      EXIT WHEN vCursor%NOTFOUND;
        UPDATE AUX_VALIDA_QUOTE_DET A
        SET  A.CANTIDAD_AQ = TO_NUMBER(vCantidad,'9999999.99')
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_LOCAL       = cCodLocal_in
        AND A.NUM_PED_VTA     = cNumPedVta_in
        AND A.COD_PROD        = vCodProd;
          
        IF SQL%NOTFOUND THEN
          INSERT INTO AUX_VALIDA_QUOTE_DET (VAL_FRAC,COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, SECUENCIAL, COD_PROD, CANTIDAD_AQ, CANTIDAD_DQ) 
          SELECT CASE
               WHEN SUBSTR(vCodProd, 7, 2) = '10' THEN
                 1
               WHEN SUBSTR(vCodProd, 7, 2) = '20' THEN
                 A.VAL_MAX_FRAC
               WHEN LENGTH(TRIM(vCodProd))=6 THEN
                 B.VAL_FRAC_LOCAL
               ELSE
                 0
             END,
             cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in, vCorrelativo, TRIM(vCodProd), TO_NUMBER(vCantidad,'9999999.99'),0
          FROM LGT_PROD A, LGT_PROD_LOCAL B
          WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
          AND B.COD_LOCAL = cCodLocal_in
          AND B.COD_PROD = SUBSTR(vCodProd, 0, 6)
          AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
          AND A.COD_PROD = B.COD_PROD
          AND TO_NUMBER(vCantidad, '9999999.99') > 0;
            
--            ();
          vCorrelativo := vCorrelativo + 1;
        END IF;
     END LOOP;
    CLOSE vCursor;
    
    -- DETERMINAR DIFERENCIA ENTRE CANTIDADES PROMOCIONES DE PRODUCTOS
    UPDATE AUX_VALIDA_QUOTE_DET A
    SET  A.CANT_PROMOCION = A.CANTIDAD_AQ - A.CANTIDAD_DQ
    WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND A.COD_LOCAL = cCodLocal_in
    AND A.NUM_PED_VTA = cNumPedVta_in;
    
    -- INSERTA RESPUESTA DE QUOTE PRODUCTOS BONIFICADOS
    open vRsptaQuote;
        LOOP
        fetch vRsptaQuote into fila;
        exit when vRsptaQuote%NOTFOUND;
          IF TO_NUMBER(FILA.CANT_BONIF,'9999999.99')!= 0 THEN
            INSERT INTO AUX_VALIDA_QUOTE_DET (VAL_FRAC, COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, SECUENCIAL, COD_PROD, CANTIDAD_AQ, CANTIDAD_DQ, CANT_BONIF) 
            SELECT CASE
                     WHEN SUBSTR(FILA.COD_PROD, 7, 2) = '10' THEN
                       1
                     WHEN SUBSTR(FILA.COD_PROD, 7, 2) = '20' THEN
                       A.VAL_MAX_FRAC
                     WHEN LENGTH(TRIM(FILA.COD_PROD))=6 THEN
                       B.VAL_FRAC_LOCAL
                     ELSE
                       0
                   END,
                   cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in, vCorrelativo, FILA.COD_PROD, 0, 0, TO_NUMBER(FILA.CANT_BONIF,'9999999.99')
            FROM LGT_PROD A, LGT_PROD_LOCAL B
            WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
            AND B.COD_LOCAL = cCodLocal_in
            AND B.COD_PROD = SUBSTR(FILA.COD_PROD, 0, 6)
            AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
            AND A.COD_PROD = B.COD_PROD;
            
            IF NOT SQL%NOTFOUND THEN
              vCorrelativo := vCorrelativo +1;
            END IF;
       
          END IF;
     END LOOP;
    CLOSE vRsptaQuote;
    
 
    
    UPDATE AUX_VALIDA_QUOTE_DET QUOTE
       SET QUOTE.CANT_BONIF_LOCAL =
           (SELECT CASE
                     WHEN MOD(QUOTE.CANT_BONIF * PROD_LOCAL.VAL_FRAC_LOCAL,
                              QUOTE.VAL_FRAC) = 0 THEN
                      ((QUOTE.CANT_BONIF * PROD_LOCAL.VAL_FRAC_LOCAL) / QUOTE.VAL_FRAC) -
                      NVL((
                      SELECT ((SUM(A.CANT_PROMOCION) * PROD_LOCAL.VAL_FRAC_LOCAL) / QUOTE.VAL_FRAC)
                      FROM AUX_VALIDA_QUOTE_DET A
                      WHERE A.COD_GRUPO_CIA = QUOTE.COD_GRUPO_CIA
                      AND A.COD_LOCAL = QUOTE.COD_LOCAL
                      AND A.NUM_PED_VTA = QUOTE.NUM_PED_VTA
                      AND A.COD_PROD = QUOTE.COD_PROD
                      
                      ),0)
                     ELSE
                      -99
                   END
              FROM LGT_PROD_LOCAL PROD_LOCAL
             WHERE PROD_LOCAL.COD_GRUPO_CIA = QUOTE.COD_GRUPO_CIA
               AND PROD_LOCAL.COD_LOCAL = QUOTE.COD_LOCAL
               AND PROD_LOCAL.COD_PROD = SUBSTR(QUOTE.COD_PROD, 0, 6))  
               
     WHERE QUOTE.COD_GRUPO_CIA = cCodGrupoCia_in
       AND QUOTE.COD_LOCAL = cCodLocal_in
       AND QUOTE.NUM_PED_VTA = cNumPedVta_in
       AND QUOTE.CANT_BONIF > 0;
     
    DELETE FROM AUX_VALIDA_QUOTE_DET QUOTE
    WHERE QUOTE.COD_GRUPO_CIA = cCodGrupoCia_in
    AND QUOTE.COD_LOCAL = cCodLocal_in
    AND QUOTE.NUM_PED_VTA = cNumPedVta_in
    AND QUOTE.CANT_BONIF > 0
    AND QUOTE.CANT_BONIF_LOCAL = 0;
    
    UPDATE AUX_VALIDA_QUOTE_DET QUOTE
    SET QUOTE.CANT_BONIF_LOCAL = 0
    WHERE QUOTE.COD_GRUPO_CIA = cCodGrupoCia_in
    AND QUOTE.COD_LOCAL = cCodLocal_in
    AND QUOTE.NUM_PED_VTA = cNumPedVta_in
    AND QUOTE.CANT_BONIF_LOCAL = -99;
    
    -- DETERMINA MENSAJE
    
    UPDATE AUX_VALIDA_QUOTE_DET QUOTE
    SET QUOTE.MENSAJE_BONIF = CASE 
                                WHEN NVL(QUOTE.CANT_PROMOCION,0) > 0 THEN  
                                  'PROMOCION: GRATIS ('||QUOTE.CANT_PROMOCION ||')'
                                WHEN NVL(QUOTE.CANT_BONIF_LOCAL,0) > 0 THEN
                                  'BONIFICACION: PUEDE LLEVAR ('||QUOTE.CANT_BONIF_LOCAL ||')'
                              END
    WHERE QUOTE.COD_GRUPO_CIA = cCodGrupoCia_in
    AND QUOTE.COD_LOCAL = cCodLocal_in
    AND QUOTE.NUM_PED_VTA = cNumPedVta_in
    AND (QUOTE.CANT_BONIF>0 OR NVL(QUOTE.CANT_PROMOCION,0) > 0);
                                
    OPEN vCursorBonificado FOR
      SELECT '0'|| 'Ã' ||
             SUBSTR(QUOTE.COD_PROD,0,6) || 'Ã' ||  -- 1
           PROD.DESC_PROD || 'Ã' || -- 2
           NVL(CASE
             WHEN QUOTE.VAL_FRAC = 1 THEN PROD.DESC_UNID_PRESENT
             ELSE PROD_LOCAL.UNID_VTA 
           END,' ') || 'Ã' || -- 3
           LAB.NOM_LAB || 'Ã' ||  -- 4
           CASE 
             WHEN NVL(QUOTE.CANT_PROMOCION,0)>0 THEN 
               QUOTE.CANT_PROMOCION 
             ELSE 
               QUOTE.CANT_BONIF_LOCAL 
           END || 'Ã' ||  -- 5
           PROD_LOCAL.IND_PROD_CONG || 'Ã' ||  -- 6
           PROD_LOCAL.IND_PROD_HABIL_VTA || 'Ã' ||  -- 7
           QUOTE.VAL_FRAC || 'Ã' ||  -- 8
           PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||  -- 9
           CASE 
             WHEN NVL(QUOTE.CANT_PROMOCION,0)>0 THEN 
               ''||QUOTE.CANT_PROMOCION 
             ELSE 
               '0' 
           END || 'Ã' || -- 10
           QUOTE.SECUENCIAL || 'Ã' || -- 11
           QUOTE.COD_PROD || 'Ã' ||-- 12
           CASE 
             WHEN NVL(QUOTE.CANT_PROMOCION,0)>0 THEN 
               'S' 
             ELSE 
               'N' 
           END || 'Ã' || -- 13
           NVL(QUOTE.MENSAJE_BONIF || ' DE '||PROD.DESC_PROD,' ') -- 14

      FROM AUX_VALIDA_QUOTE_DET QUOTE,
           LGT_PROD_LOCAL PROD_LOCAL,
           LGT_PROD       PROD,
           LGT_LAB        LAB
      WHERE QUOTE.COD_GRUPO_CIA = cCodGrupoCia_in
      AND QUOTE.COD_LOCAL = cCodLocal_in
      AND QUOTE.NUM_PED_VTA = cNumPedVta_in
      AND (QUOTE.CANT_BONIF>0 OR NVL(QUOTE.CANT_PROMOCION,0) > 0)
      AND PROD_LOCAL.COD_GRUPO_CIA = QUOTE.COD_GRUPO_CIA
      AND PROD_LOCAL.COD_LOCAL = QUOTE.COD_LOCAL
      AND PROD_LOCAL.COD_PROD = SUBSTR(QUOTE.COD_PROD,0,6)
      AND PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
      AND PROD.COD_PROD = PROD_LOCAL.COD_PROD
      AND LAB.COD_LAB = PROD.COD_LAB
      ;
    
    RETURN vCursorBonificado;
  END;
  
  FUNCTION F_N_CALCULA_REDONDEO(nD_in IN number, 
                                nDec_in IN integer)
    RETURN NUMBER IS
    nD number(10,3) := 1561.520;
    nDec integer := 2;
  
    aux   number(10, 3);
    resto number(10, 3);
    signo integer := 1;
  BEGIN
    nD := nD_in;
    nDec := nDec_in;
    if nD < 0 then
      signo := -1;
    end if;

    nD  := nD * signo;
    aux := round((power(10, nDec) * nD), 2);
    dbms_output.put_line(aux);
    resto := mod(aux, 10);
    dbms_output.put_line(aux);
    aux := trunc(aux / 10);
    dbms_output.put_line(aux);
    if resto < 5 then
      resto := 0;
    else
      resto := 5;
    end if;

    nD := (aux * 10 + resto) / power(10, nDec);
    nD := nD * signo;
    dbms_output.put_line(nD);
    RETURN nD;
  END;

  FUNCTION F_CHAR_REDONDEO_CALCULO_PTOS 
    RETURN CHAR IS
    vIndicador CHAR(1);
  BEGIN
    SELECT TRIM(A.LLAVE_TAB_GRAL)
    INTO vIndicador
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL = TAB_IND_REDONDEO_CALCULO_PTS;
    RETURN vIndicador;
  EXCEPTION 
    WHEN OTHERS THEN
      RETURN TRUNCATE_DESACTIVADO;
  END;
  
  FUNCTION F_TEXTO_AHORRO
    RETURN CHAR IS
    vTexto VARCHAR2(50);
  BEGIN
    SELECT TRIM(A.LLAVE_TAB_GRAL)
    INTO vTexto
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL = TAB_IND_TEXTO_AHORRO;
    RETURN vTexto;
  EXCEPTION 
    WHEN OTHERS THEN
      RETURN ' ';
  END;
  
  FUNCTION F_IND_ACUMULA_REDIME
    RETURN CHAR IS
    vIndicador CHAR(1);
  BEGIN
    SELECT TRIM(A.LLAVE_TAB_GRAL) 
    INTO   vIndicador
    FROM   PBL_TAB_GRAL A 
    WHERE  A.ID_TAB_GRAL = TAB_IND_ACUMULACION_REDIME;
    RETURN vIndicador;
  EXCEPTION 
    WHEN OTHERS THEN
      RETURN REDIME_SI_ACUMULA;
  END;
  
  FUNCTION F_IND_CALCULO_CON_IGV
    RETURN CHAR IS
    vIndicador CHAR(1);
  BEGIN
    SELECT TRIM(A.LLAVE_TAB_GRAL) 
    INTO   vIndicador
    FROM   PBL_TAB_GRAL A 
    WHERE  A.ID_TAB_GRAL = TAB_IND_CALCULO_CON_IGV;
    RETURN vIndicador;
  EXCEPTION 
    WHEN OTHERS THEN
      RETURN IND_SI;
  END;
  
  FUNCTION F_VARCHAR_MSJ_SIN_TARJETA
    RETURN VARCHAR2 IS
    vMensaje VARCHAR2(500);
  BEGIN
    SELECT TRIM(A.LLAVE_TAB_GRAL) 
    INTO   vMensaje
    FROM   PBL_TAB_GRAL A 
    WHERE  A.ID_TAB_GRAL = TAB_IND_MSJ_SIN_TARJETA;
    RETURN vMensaje;
  EXCEPTION 
    WHEN OTHERS THEN
      RETURN ' ';
  END;
  
  FUNCTION F_CHAR_OPERA_QUOTE (cCodGrupoCia_in  IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                               cCodLocal_in     IN VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                               cNumPedVta_in    IN VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                               cLstProdFinal_in IN VARCHAR2)
    RETURN CHAR IS
    cSecUsun        VTA_PEDIDO_VTA_DET.SEC_USU_LOCAL%TYPE;
    cLogin          VTA_PEDIDO_VTA_DET.USU_CREA_PED_VTA_DET%TYPE;
    nSecuencia      INTEGER;
    vCursor         FarmaCursor;
    vCantDiferencia INTEGER;
  BEGIN
    -- BORRA REGISTRO GRABADOS ANTERIORMENTE 
    DELETE FROM TMP_VTA_PEDIDO_VTA_DET DET
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
       AND DET.COD_LOCAL = cCodLocal_in
       AND DET.NUM_PED_VTA = cNumPedVta_in;
       
    insert into tmp_vta_pedido_vta_det
      (cod_grupo_cia, cod_local, num_ped_vta, sec_ped_vta_det, cod_prod, cant_atendida, val_prec_vta, val_prec_total, porc_dcto_1, porc_dcto_2, porc_dcto_3, porc_dcto_total, est_ped_vta_det, val_total_bono, val_frac, sec_comp_pago, sec_usu_local, usu_crea_ped_vta_det, fec_crea_ped_vta_det, usu_mod_ped_vta_det, fec_mod_ped_vta_det, val_prec_lista, val_igv, unid_vta, ind_exonerado_igv, sec_grupo_impr, cant_usada_nc, sec_comp_pago_origen, num_lote_prod, fec_proceso_guia_rd, desc_num_tel_rec, val_num_trace, val_cod_aprobacion, desc_num_tarj_virtual, val_num_pin, fec_vencimiento_lote, val_prec_public, ind_calculo_max_min, fec_exclusion, fecha_tx, hora_tx, cod_prom, ind_origen_prod, val_frac_local, cant_frac_local, cant_xdia_tra, cant_dias_tra, ind_zan, val_prec_prom, datos_imp_virtual, cod_camp_cupon, ahorro, porc_dcto_calc, porc_zan, ind_prom_automatico, ahorro_pack, porc_dcto_calc_pack, cod_grupo_rep, cod_grupo_rep_edmundo, sec_respaldo_stk, sec_comp_pago_benef, sec_comp_pago_empre, num_comp_pago, ahorro_conv, val_prec_total_empre, val_prec_total_benef, tip_clien_convenio, cod_tip_afec_igv_e, cod_tip_prec_vta_e, val_prec_vta_unit_e, cant_unid_vdd_e, val_vta_item_e, val_total_igv_item_e, val_total_desc_item_e, desc_item_e, val_vta_unit_item_e, dni_rimac, ctd_puntos_acum, ind_prod_mas_1, ind_bonificado, cod_prod_puntos)
    select
      cod_grupo_cia, cod_local, num_ped_vta, sec_ped_vta_det, cod_prod, cant_atendida, val_prec_vta, val_prec_total, porc_dcto_1, porc_dcto_2, porc_dcto_3, porc_dcto_total, est_ped_vta_det, val_total_bono, val_frac, sec_comp_pago, sec_usu_local, usu_crea_ped_vta_det, fec_crea_ped_vta_det, usu_mod_ped_vta_det, fec_mod_ped_vta_det, val_prec_lista, val_igv, unid_vta, ind_exonerado_igv, sec_grupo_impr, cant_usada_nc, sec_comp_pago_origen, num_lote_prod, fec_proceso_guia_rd, desc_num_tel_rec, val_num_trace, val_cod_aprobacion, desc_num_tarj_virtual, val_num_pin, fec_vencimiento_lote, val_prec_public, ind_calculo_max_min, fec_exclusion, fecha_tx, hora_tx, cod_prom, ind_origen_prod, val_frac_local, cant_frac_local, cant_xdia_tra, cant_dias_tra, ind_zan, val_prec_prom, datos_imp_virtual, cod_camp_cupon, ahorro, porc_dcto_calc, porc_zan, ind_prom_automatico, ahorro_pack, porc_dcto_calc_pack, cod_grupo_rep, cod_grupo_rep_edmundo, sec_respaldo_stk, sec_comp_pago_benef, sec_comp_pago_empre, num_comp_pago, ahorro_conv, val_prec_total_empre, val_prec_total_benef, tip_clien_convenio, cod_tip_afec_igv_e, cod_tip_prec_vta_e, val_prec_vta_unit_e, cant_unid_vdd_e, val_vta_item_e, val_total_igv_item_e, val_total_desc_item_e, desc_item_e, val_vta_unit_item_e, dni_rimac, ctd_puntos_acum, ind_prod_mas_1, ind_bonificado, cod_prod_puntos
    from vta_pedido_vta_det det
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
       AND DET.COD_LOCAL = cCodLocal_in
       AND DET.NUM_PED_VTA = cNumPedVta_in;

    
    SELECT DET.SEC_USU_LOCAL, DET.USU_CREA_PED_VTA_DET
      INTO cSecUsun, cLogin
      FROM TMP_VTA_PEDIDO_VTA_DET DET
     WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
       AND DET.COD_LOCAL = cCodLocal_in
       AND DET.NUM_PED_VTA = cNumPedVta_in
       AND DET.SEC_PED_VTA_DET = 1;

    SELECT COUNT(1)
      INTO nSecuencia
      FROM TMP_VTA_PEDIDO_VTA_DET DET
     WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
       AND DET.COD_LOCAL = cCodLocal_in
       AND DET.NUM_PED_VTA = cNumPedVta_in;

    vCursor := F_LST_VALIDA_BONIFICADOS(cCodGrupoCia_in   => cCodGrupoCia_in,
                                                          cCodLocal_in      => cCodLocal_in,
                                                          cNumPedVta_in     => cNumPedVta_in,
                                                          cListaBonifica_in => cLstProdFinal_in);
    
    -- AGREGA LOS BONIFICADOS PRECIO O
    INSERT INTO TMP_VTA_PEDIDO_VTA_DET
      (COD_GRUPO_CIA,
       COD_LOCAL,
       NUM_PED_VTA,
       SEC_PED_VTA_DET,
       COD_PROD,
       CANT_ATENDIDA,
       VAL_PREC_VTA,
       VAL_PREC_TOTAL,
       PORC_DCTO_1,
       PORC_DCTO_2,
       PORC_DCTO_3,
       PORC_DCTO_TOTAL,
       VAL_TOTAL_BONO,
       VAL_FRAC,
       SEC_USU_LOCAL,
       USU_CREA_PED_VTA_DET,
       VAL_PREC_LISTA,
       VAL_IGV,
       UNID_VTA,
       IND_EXONERADO_IGV,
       VAL_PREC_PUBLIC,
       IND_ORIGEN_PROD,
       VAL_FRAC_LOCAL,
       CANT_FRAC_LOCAL,
       COD_PROM,
       IND_PROM_AUTOMATICO,
       IND_BONIFICADO)
    -- nCantNueva : 1  cSecUsu_in   cLogin_in
      SELECT DET.COD_GRUPO_CIA,
             DET.COD_LOCAL,
             DET.NUM_PED_VTA,
             nSecuencia + ROWNUM,
             SUBSTR(DET.COD_PROD, 0, 6),
             CASE 
               WHEN DET.CANT_PROMOCION IS NOT NULL THEN
                 DET.CANT_PROMOCION
               ELSE
                 DET.CANT_BONIF_LOCAL
             END,
             0,
             0,
             PROD_LOCAL.PORC_DCTO_1,
             PROD_LOCAL.PORC_DCTO_2,
             PROD_LOCAL.PORC_DCTO_3,
             PROD_LOCAL.PORC_DCTO_1 + PROD_LOCAL.PORC_DCTO_2 +
             PROD_LOCAL.PORC_DCTO_3,
             DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,
                    'N',
                    PROD.VAL_BONO_VIG,
                    (PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL)),
             PROD_LOCAL.VAL_FRAC_LOCAL,
             cSecUsun,
             cLogin,
             0,
             IGV.PORC_IGV,
             DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,
                    'N',
                    PROD.DESC_UNID_PRESENT,
                    PROD_LOCAL.Unid_Vta),
             DECODE(IGV.PORC_IGV, 0, 'S', 'N'),
             PROD_LOCAL.VAL_PREC_VTA,
             null,
             PROD_LOCAL.VAL_FRAC_LOCAL,
             CASE 
               WHEN DET.CANT_PROMOCION IS NOT NULL THEN
                 DET.CANT_PROMOCION
               ELSE
                 DET.CANT_BONIF_LOCAL
             END,
             null,
             'S',
             'S'
        FROM AUX_VALIDA_QUOTE_DET DET,
             LGT_PROD             PROD,
             LGT_PROD_LOCAL       PROD_LOCAL,
             pbl_igv              igv
       WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
         AND DET.COD_LOCAL = cCodLocal_in
         AND DET.NUM_PED_VTA = cNumPedVta_in
         AND (DET.CANT_BONIF > 0 OR DET.CANT_PROMOCION>0)
         AND PROD_LOCAL.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
         AND PROD_LOCAL.COD_LOCAL = DET.COD_LOCAL
         AND PROD_LOCAL.COD_PROD = SUBSTR(DET.COD_PROD, 0, 6)
         AND PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
         AND PROD.COD_PROD = PROD_LOCAL.COD_PROD
         AND PROD.COD_IGV = IGV.COD_IGV;
         COMMIT;

    --  CONSULTA DIFERENCIAS DE CANTIDADES DE PRODUCTOS A COBRAR
    SELECT COUNT(1)
      INTO vCantDiferencia
      FROM AUX_VALIDA_QUOTE_DET DET
     WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
       AND DET.COD_LOCAL = cCodLocal_in
       AND DET.NUM_PED_VTA = cNumPedVta_in
       AND NVL(DET.CANT_BONIF,0) = 0
       AND DET.CANTIDAD_AQ <> DET.CANTIDAD_DQ;
    
    IF vCantDiferencia > 0 THEN 
      FOR LISTA IN(
            SELECT A.COD_GRUPO_CIA, A.COD_LOCAL, A.NUM_PED_VTA, A.VAL_PREC_VTA, DET.COD_PROD, A.SEC_PED_VTA_DET,
                   DET.CANTIDAD_DQ, (DET.CANTIDAD_DQ * A.VAL_PREC_VTA)
             FROM  TMP_VTA_PEDIDO_VTA_DET A,
                   AUX_VALIDA_QUOTE_DET DET
            WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
              AND A.COD_LOCAL = cCodLocal_in
              AND A.NUM_PED_VTA = cNumPedVta_in
              AND NVL(A.IND_BONIFICADO, 'N') <> 'S'
              AND DET.COD_GRUPO_CIA = A.COD_GRUPO_CIA
              AND DET.COD_LOCAL = A.COD_LOCAL
              AND DET.NUM_PED_VTA = A.NUM_PED_VTA
              AND SUBSTR(DET.COD_PROD, 0, 6) = A.COD_PROD
              AND NVL(DET.CANT_BONIF,0) = 0
              AND DET.CANTIDAD_AQ <> DET.CANTIDAD_DQ
        )LOOP
           
           DBMS_OUTPUT.PUT_LINE(LISTA.COD_PROD||' - '||LISTA.NUM_PED_VTA||' - '|| LISTA.CANTIDAD_DQ ||' - '|| LISTA.VAL_PREC_VTA||CHR(13));
           
           UPDATE TMP_VTA_PEDIDO_VTA_DET A 
           SET A.CANT_ATENDIDA = LISTA.CANTIDAD_DQ
               -- ,A.VAL_PREC_TOTAL = (LISTA.CANTIDAD_DQ * LISTA.VAL_PREC_VTA)
               ,A.IND_CAMBIO_CANT = 'S'
           WHERE A.COD_GRUPO_CIA = LISTA.COD_GRUPO_CIA
           AND A.COD_LOCAL = LISTA.COD_LOCAL
           AND A.NUM_PED_VTA = cNumPedVta_in
           AND A.SEC_PED_VTA_DET = LISTA.SEC_PED_VTA_DET;
           
        END LOOP;
 /*       
       UPDATE vta_pedido_vta_det A 
       SET(A.CANT_ATENDIDA, A.VAL_PREC_TOTAL) = (
                                                 SELECT DET.CANTIDAD_DQ,
                                                        (DET.CANTIDAD_DQ * A.VAL_PREC_VTA)
                                                   FROM AUX_VALIDA_QUOTE_DET DET
                                                  WHERE DET.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                                                    AND DET.COD_LOCAL = A.COD_LOCAL
                                                    AND DET.NUM_PED_VTA = A.NUM_PED_VTA
                                                    AND SUBSTR(DET.COD_PROD, 0, 6) = A.COD_PROD
                                                    AND DET.CANT_BONIF = 0
                                                    AND DET.CANTIDAD_AQ <> DET.CANTIDAD_DQ
       ) -- , A.VAL_PREC_TOTAL = A.CANT_ATENDIDA * A.VAL_PREC_VTA
          \*,
          SET A.IND_CAMBIO_CANT = 'S'*\
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.NUM_PED_VTA = cNumPedVta_in
       AND NVL(A.IND_BONIFICADO, 'N') <> 'S';*/

    END IF;

    -- ACTUALIZA CANTIDAD DE ITEMS DE PEDIDO Y VALOR NETO DEL PEDIDO
/*    UPDATE VTA_PEDIDO_VTA_CAB CAB 
    SET(CAB.CANT_ITEMS_PED_VTA, CAB.VAL_NETO_PED_VTA) = (
                        SELECT COUNT(1), SUM(DET.VAL_PREC_TOTAL)
                          FROM VTA_PEDIDO_VTA_DET DET
                         WHERE DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                           AND DET.COD_LOCAL = CAB.COD_LOCAL
                           AND DET.NUM_PED_VTA = CAB.NUM_PED_VTA)
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;


    -- PROCESA REDONDEO DEL PEDIDO
    UPDATE VTA_PEDIDO_VTA_CAB CAB 
    SET CAB.VAL_REDONDEO_PED_VTA = CAB.VAL_NETO_PED_VTA - F_N_CALCULA_REDONDEO(CAB.VAL_NETO_PED_VTA,2), 
        CAB.VAL_NETO_PED_VTA = F_N_CALCULA_REDONDEO(CAB.VAL_NETO_PED_VTA,2) 
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in 
    AND CAB.COD_LOCAL = cCodLocal_in 
    AND CAB.NUM_PED_VTA = cNumPedVta_in 
    AND NVL(CAB.IND_CONV_BTL_MF, 'N') <> 'S';*/
    /*
    -- RECALCULO DE PUNTOS
    F_I_CALCULA_PUNTOS(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);
    */
    RETURN 'S';
  END;
    
  FUNCTION F_VCHAR_OBTIENE_MODIFICADOS(cCodGrupoCia_in IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                       cCodLocal_in    IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                       cNumPedVta_in   IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE)
    RETURN VARCHAR IS
    vResultado VARCHAR2(200) := ' ';
    CURSOR vDetalle IS
      SELECT DET.COD_PROD ||','||DET.CANT_ATENDIDA linea
      FROM TMP_VTA_PEDIDO_VTA_DET DET
      WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND DET.COD_LOCAL = cCodLocal_in
      AND DET.NUM_PED_VTA = cNumPedVta_in
      AND DET.IND_CAMBIO_CANT = 'S';
    fila vDetalle%ROWTYPE;
  BEGIN
    OPEN vDetalle;
    LOOP
      FETCH vDetalle INTO fila;
      EXIT WHEN vDetalle%NOTFOUND;
        IF LENGTH(TRIM(vResultado)) > 0 THEN
          vResultado := vResultado ||'@';
        END IF;
        vResultado := vResultado || fila.linea;
    END LOOP;
    CLOSE vDetalle;
    RETURN vResultado;
  END;
  
  FUNCTION F_INT_CANTIDAD_PROMOCIONES(cCodGrupoCia_in IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                      cCodLocal_in    IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                      cNumPedVta_in   IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE)
    RETURN INTEGER IS
    vCantidad INTEGER := 0;
  BEGIN
    SELECT COUNT(1)
    INTO vCantidad
    FROM TMP_VTA_PEDIDO_VTA_DET DET
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DET.COD_LOCAL = cCodLocal_in
    AND DET.NUM_PED_VTA = cNumPedVta_in
    AND DET.IND_CAMBIO_CANT = 'S';
    RETURN vCantidad;
  END;
  

    
  FUNCTION F_CHAR_AGREGA_PROMO_BONIF(cCodGrupoCia_in       IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                     cCodLocal_in          IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                     cNumPedVta_in         IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                                     cNumPedVtaOrigen_in   IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE)
    RETURN CHAR IS
    nSecuencia INTEGER := 0;
    
  BEGIN
   
    SELECT COUNT(1)
      INTO nSecuencia
      FROM VTA_PEDIDO_VTA_DET DET
     WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
       AND DET.COD_LOCAL = cCodLocal_in
       AND DET.NUM_PED_VTA = cNumPedVta_in;
    
    INSERT INTO VTA_PEDIDO_VTA_DET
      (COD_GRUPO_CIA,
       COD_LOCAL,
       NUM_PED_VTA,
       SEC_PED_VTA_DET,
       COD_PROD,
       CANT_ATENDIDA,
       VAL_PREC_VTA,
       VAL_PREC_TOTAL,
       PORC_DCTO_1,
       PORC_DCTO_2,
       PORC_DCTO_3,
       PORC_DCTO_TOTAL,
       VAL_TOTAL_BONO,
       VAL_FRAC,
       SEC_USU_LOCAL,
       USU_CREA_PED_VTA_DET,
       VAL_PREC_LISTA,
       VAL_IGV,
       UNID_VTA,
       IND_EXONERADO_IGV,
       VAL_PREC_PUBLIC,
       IND_ORIGEN_PROD,
       VAL_FRAC_LOCAL,
       CANT_FRAC_LOCAL,
       COD_PROM,
       IND_PROM_AUTOMATICO,
       IND_BONIFICADO)
    
    SELECT
    COD_GRUPO_CIA,
       COD_LOCAL,
       cNumPedVta_in,
       nSecuencia + ROWNUM,
       COD_PROD,
       CANT_ATENDIDA,
       VAL_PREC_VTA,
       VAL_PREC_TOTAL,
       PORC_DCTO_1,
       PORC_DCTO_2,
       PORC_DCTO_3,
       PORC_DCTO_TOTAL,
       VAL_TOTAL_BONO,
       VAL_FRAC,
       SEC_USU_LOCAL,
       USU_CREA_PED_VTA_DET,
       VAL_PREC_LISTA,
       VAL_IGV,
       UNID_VTA,
       IND_EXONERADO_IGV,
       VAL_PREC_PUBLIC,
       IND_ORIGEN_PROD,
       VAL_FRAC_LOCAL,
       CANT_FRAC_LOCAL,
       COD_PROM,
       IND_PROM_AUTOMATICO,
       IND_BONIFICADO
    FROM TMP_VTA_PEDIDO_VTA_DET DET
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DET.COD_LOCAL = cCodLocal_in
    AND DET.NUM_PED_VTA = cNumPedVtaOrigen_in
    AND DET.IND_BONIFICADO = 'S';
    
    UPDATE VTA_PEDIDO_VTA_CAB CAB 
    SET CAB.CANT_ITEMS_PED_VTA = (
                        SELECT COUNT(1)
                          FROM VTA_PEDIDO_VTA_DET DET
                         WHERE DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                           AND DET.COD_LOCAL = CAB.COD_LOCAL
                           AND DET.NUM_PED_VTA = CAB.NUM_PED_VTA)
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;
    
    RETURN 'S';
  END;
  
  FUNCTION F_IND_REQUIERE_TARJETA
    RETURN CHAR IS
    vIndicador CHAR(1);
  BEGIN
    SELECT TRIM(A.LLAVE_TAB_GRAL) 
    INTO   vIndicador
    FROM   PBL_TAB_GRAL A 
    WHERE  A.ID_TAB_GRAL = TAB_IND_REQUIERTE_TARJETA;
    RETURN vIndicador;
  EXCEPTION 
    WHEN OTHERS THEN
      RETURN DEFAULT_REQUIERE_TARJETA;
  END;
  
  -- KMONCADA 27.04.2015
  
  FUNCTION F_CHAR_EXISTE_TARJETA_CLIENTE(cNroDocumento_in IN FID_TARJETA.DNI_CLI%TYPE,
                                         cNroTarjeta_in   IN FID_TARJETA.COD_TARJETA%TYPE)
    RETURN CHAR IS
    vCantidadExiste INTEGER;
    nNroDocumento VARCHAR2(15);
    nContador INTEGER := 0; 
  BEGIN
    SELECT COUNT(1)
    INTO vCantidadExiste 
    FROM FID_TARJETA 
    WHERE /*DNI_CLI = cNroDocumento_in
    AND */COD_TARJETA = cNroTarjeta_in;
    
    IF vCantidadExiste = 0 THEN
      RETURN 'N';
    ELSE
      -- KMONCADA 02.07.2015 VALIDA SI TARJETA ES UNA PROMOCION CONVENIO
      -- PARA MODIFICAR EL DOCUMENTO DE IDENTIDAD
      /*select count(1)
      into nContador
      from PTOVENTA.vta_rango_tarjeta
      WHERE DESDE <= substr(cNroTarjeta_in, 1, 12)
      AND HASTA >= substr(cNroTarjeta_in, 1, 12)
      AND COD_TIPO_TARJETA = 'TC';*/
      if nContador <> 0 then
        nNroDocumento := 'D' || cNroDocumento_in || 'C';
      ELSE
        nNroDocumento := cNroDocumento_in;
      end if;

      SELECT COUNT(1)
      INTO vCantidadExiste 
      FROM FID_TARJETA 
      WHERE DNI_CLI = nNroDocumento
      AND COD_TARJETA = cNroTarjeta_in;
      IF vCantidadExiste = 1 THEN
        RETURN 'N';
      ELSE
        RETURN 'S';
      END IF; 
    END IF;
  END;
  
  FUNCTION F_IS_TARJ_VALIDA_OTRO_PROGRAMA(cNumTarj_in       IN CHAR,
                                          cIncluidoPtos_in  IN CHAR DEFAULT 'N') 
  RETURN VARCHAR2 IS
    nRetorno  VARCHAR2(2) := 'N';
    vCantidad INTEGER;
  BEGIN
  
    SELECT COUNT(1)
      INTO vCantidad
      FROM VTA_RANGO_TARJETA T
     WHERE T.ESTADO = 'A'
       AND T.COD_TIPO_TARJETA <> 'CP'
       AND cNumTarj_in BETWEEN T.DESDE AND T.HASTA;
  
    IF vCantidad > 0 THEN
      nRetorno := 'S';
      IF cIncluidoPtos_in = 'S' THEN
        SELECT COUNT(1)
        INTO vCantidad
        FROM VTA_RANGO_TARJETA T
        WHERE T.ESTADO = 'A'
        AND T.COD_TIPO_TARJETA = 'CP'
        AND cNumTarj_in BETWEEN T.DESDE AND T.HASTA;
        
        IF vCantidad > 0 THEN
          nRetorno := 'S';
        ELSE
          nRetorno := 'N';
        END IF;
      END IF;
    END IF;
  
    RETURN nRetorno;
  END;
  
  
  -------------
  
  FUNCTION IMP_SALDO_TARJETA(cCodGrupoCia_in 	IN CHAR,
                              cCodLocal_in    	IN CHAR,
                              cNombres_in       in char,
                              cTarj_in          IN CHAR,
                              cPuntosAcum_in    IN char)
  RETURN FarmaCursor
  IS
    curDataCupon FarmaCursor;
    vIdDoc IMPRESION_TERMICA.ID_DOC%type;
    vIpPc  IMPRESION_TERMICA.IP_PC%type;    
    vReturn INTEGER;
  BEGIN
                 
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc  := FARMA_PRINTER.F_GET_IP_SESS;
    
    -- el logo se imprimira parametrizado al tab gral   
    IF (FARMA_PUNTOS.F_VAR_IND_IMPR_LOGO(cCodGrupoCia_in,cCodLocal_in) = 'S') THEN
       FARMA_PRINTER.P_AGREGA_LOGO_MARCA(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vCodGrupoCia => cCodGrupoCia_in,
                                              vCodLocal_in => cCodLocal_in);    
    END IF;

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => 'Saldo',
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => trim(cNombres_in),
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_2,
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc,vIpPc);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => trim(cTarj_in),
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_2,
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN);

    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc,vIpPc);
    
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => 'Fecha : ',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                              vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
                                              
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => TO_CHAR(SYSDATE,'DD/MM/YYYY')||LPAD(' ',9,' '),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
                                              
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => 'Hora : ',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                              vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => TO_CHAR(SYSDATE,'HH24:MI:SS'),
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                      vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);

    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => 'Puntos Acumulados : ',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                              vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => cPuntosAcum_in,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                      vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
                                      
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => TRIM(farma_puntos.f_var_msj_voucher(cCodGrupoCia_in,cCodLocal_in)),
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                      vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
                                
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc,vIpPc);
  
    curDataCupon :=  FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc,vIpPc);    
    
    RETURN curDataCupon;
  END; 
  
  FUNCTION IMP_RECUPERA_PUNTOS(cCodGrupoCia_in 	IN CHAR,
                              cCodLocal_in    	IN CHAR,
                              cNombres_in       in char,
                              cDNI_in           in char,
                              cTarj_in          IN CHAR,
                              cPuntosAcum_in    IN char,
                              cIndOnline        in char,
                              cPuntosSaldo_in   IN char,
                              cNumPedVta_in    IN char)
  RETURN FarmaCursor  
  IS 
    curDataCupon FarmaCursor;
    vIdDoc IMPRESION_TERMICA.ID_DOC%type;
    vIpPc  IMPRESION_TERMICA.IP_PC%type;    
    vCod_trab varchar2(100):= '';
    vFecha    varchar2(100):= '';
    vlocal    varchar2(100):= '';
    vTipoNumComprobante       VARCHAR2(20) := '';
    vNumPedVta                VARCHAR2(10) := '';
    cFilaCamp VTA_CAMPANA_CUPON%rowtype;
     ctd int;
     vMsjOffline pbl_tab_gral.desc_larga%type;

    off_cDNI     varchar2(20);
    off_cNombres varchar2(5000);
         
  BEGIN
                 
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc  := FARMA_PRINTER.F_GET_IP_SESS;
    
    -- el logo se imprimira parametrizado al tab gral   
    IF (FARMA_PUNTOS.F_VAR_IND_IMPR_LOGO(cCodGrupoCia_in,cCodLocal_in) = 'S') THEN
       FARMA_PRINTER.P_AGREGA_LOGO_MARCA(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vCodGrupoCia => cCodGrupoCia_in,
                                              vCodLocal_in => cCodLocal_in);    
    END IF;

    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => 'Constancia de Recuperacion de puntos',
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                      vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
    
    IF cIndOnline = 'S' THEN
      FARMA_PRINTER.P_AGREGA_TEXTO (vIdDoc_in => vIdDoc,
                                         vIpPc_in => vIpPc,
                                         vValor_in => trim(cDNI_in),
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_2,
                                         vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    
      FARMA_PRINTER.P_AGREGA_TEXTO (vIdDoc_in => vIdDoc,
                                         vIpPc_in => vIpPc,
                                         vValor_in => trim(cNombres_in),
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_2,
                                         vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);
                                         
    ELSE
    
      BEGIN
      SELECT t.dni_cli,p.nom_cli ||' '|| p.ape_pat_cli||' '||p.ape_mat_cli
      INTO   off_cDNI,off_cNombres
      FROM   fid_tarjeta t,
             pbl_cliente p
      WHERE  t.cod_tarjeta = REPLACE(cTarj_in,'*','0')
      AND    t.dni_cli is not null
      AND    t.dni_cli = p.dni_cli;
      --and 1 = 2;
      
      FARMA_PRINTER.P_AGREGA_TEXTO (vIdDoc_in => vIdDoc,
                                         vIpPc_in => vIpPc,
                                         vValor_in => trim(off_cDNI),
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_2,
                                         vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);
      
      FARMA_PRINTER.P_AGREGA_TEXTO (vIdDoc_in => vIdDoc,
                                         vIpPc_in => vIpPc,
                                         vValor_in => trim(off_cNombres),
                                         vTamanio_in => FARMA_PRINTER.TAMANIO_2,
                                         vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT);
      
      EXCEPTION
      WHEN OTHERS THEN 
        NULL;         
      END;
    END IF;
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
    
    FARMA_PRINTER.P_AGREGA_TEXTO (vIdDoc_in => vIdDoc,
                                       vIpPc_in => vIpPc,
                                       vValor_in => trim(cTarj_in),
                                       vTamanio_in => FARMA_PRINTER.TAMANIO_3,
                                       vAlineado_in => FARMA_PRINTER.ALING_CEN);

    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);

    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => 'Fecha : ',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                              vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
                                              
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => TO_CHAR(SYSDATE,'DD/MM/YYYY')||LPAD(' ',10,' '),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
                                              
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => 'Hora : ',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                              vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => TO_CHAR(SYSDATE,'HH24:MI:SS'),
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                      vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);

    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => 'Puntos Acumulados : ',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                              vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => cPuntosAcum_in,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                      vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);

    IF LENGTH(TRIM(cPuntosSaldo_in)) > 0 AND cIndOnline = 'S' THEN
      FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => 'Saldo Actual : ',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                              vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
    
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                       vIpPc_in => vIpPc,
                                       vValor_in => cPuntosSaldo_in,
                                       vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                       vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                       vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);

    END IF;
   
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                     vIpPc_in => vIpPc,
                                     vValor_in => 'Comprobante(s): ',
                                     vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                     vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                     vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                     vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);

    FOR lista in (
                  select 
                         case
                           when p.cod_tip_proc_pago = 1 then 
                                Substrb( p.num_comp_pago_e,0,4)||'-'|| Substrb( p.num_comp_pago_e,5) 
                           else Substrb( p.num_comp_pago,0,3)||'-'|| Substrb( p.num_comp_pago,4) 
                          end  COMPROBANTE
                  from   vta_comp_pago p
                  where  p.cod_grupo_cia = cCodGrupoCia_in
                  and    p.cod_local = cCodLocal_in
                  and    p.num_ped_vta = cNumPedVta_in
                  )loop
                  
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                        vIpPc_in => vIpPc,
                                        vValor_in => '- ' || lista.comprobante,
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    END LOOP;                       
   
                               
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                        vIpPc_in => vIpPc,
                                        vValor_in => TRIM(farma_puntos.f_var_msj_voucher(cCodGrupoCia_in,cCodLocal_in)),
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                        vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);   
    
    -- muestra mensaje de si fue offline
    IF cIndOnline = 'N' THEN

      BEGIN
        SELECT a.desc_larga
        INTO   vMsjOffline
        FROM   pbl_tab_gral A
        WHERE  A.ID_TAB_GRAL = 458;
      EXCEPTION
      WHEN OTHERS THEN
        vMsjOffline := 'N';
      END;
        
      IF vMsjOffline != 'N' AND length(vMsjOffline) > 0 THEN
        FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                        vIpPc_in => vIpPc,
                                        vValor_in => vMsjOffline,
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                        vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
       END IF;                           
    END IF;                                
                                                        
    -------------------------------------------------------------------------------                             
    curDataCupon :=  FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc,
                                                                   vIpPc_in => vIpPc);
    
    RETURN curDataCupon;
  end;
  
  FUNCTION F_VARCHAR_MSJ_TARJ_ADICIONAL
    RETURN VARCHAR2 IS
    vMensaje PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE;
  BEGIN
    BEGIN
      SELECT LLAVE_TAB_GRAL
      INTO vMensaje
      FROM PBL_TAB_GRAL
      WHERE ID_TAB_GRAL = TAB_IND_MSJ_TARJ_ADICIONAL;
    EXCEPTION
      WHEN OTHERS THEN
        vMensaje := ' ';
    END;
    RETURN vMensaje;
  END;
  
  FUNCTION F_CHAR_VALIDA_DOC_TARJ_ADICION
    RETURN VARCHAR2 IS
    vMensaje PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE;
  BEGIN
    BEGIN
      SELECT LLAVE_TAB_GRAL
      INTO vMensaje
      FROM PBL_TAB_GRAL
      WHERE ID_TAB_GRAL = TAB_IND_VALIDA_DOC_TARJ_ADIC;
    EXCEPTION
      WHEN OTHERS THEN
        vMensaje := ' ';
    END;
    RETURN vMensaje;
  END;
  
  FUNCTION F_CHAR_MSJ_PTOS_PANTALLA_COBRO
    RETURN VARCHAR2 IS
    vMensaje PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE;
  BEGIN
    BEGIN
      SELECT LLAVE_TAB_GRAL
      INTO vMensaje
      FROM PBL_TAB_GRAL
      WHERE ID_TAB_GRAL = TAB_IND_MSJ_PTOS_PANT_COBRO;
    EXCEPTION
      WHEN OTHERS THEN
        vMensaje := ' ';
    END;
    RETURN vMensaje;
  END;
  
  FUNCTION F_CHAR_GET_MSJ_TIEMPO_AHORRO
    RETURN VARCHAR2 IS
    vMensaje VARCHAR2(100);
  BEGIN
    BEGIN
      SELECT 
      CASE
         WHEN A.MES <= 1 THEN 'en el ultimo mes'
         WHEN A.MES >= 2 AND A.MES <= 11 THEN 'en los ultimos ' || A.MES || ' meses'
         WHEN A.MES >= 12 THEN 'en los ultimos 12 meses'
       END
       INTO vMensaje
      FROM (SELECT MONTHS_BETWEEN(TO_DATE('01' || TO_CHAR(SYSDATE, 'MMYYYY'), 'DD/MM/YYYY'),
                                  TO_DATE('01' ||
                                          (SELECT A.LLAVE_TAB_GRAL
                                             FROM PBL_TAB_GRAL A
                                            WHERE A.ID_TAB_GRAL = TAB_IND_TIEMPO_INICIO_PTOS),
                                          'DD/MM/YYYY')) MES
              FROM DUAL) A;

    EXCEPTION 
      WHEN OTHERS THEN
        vMensaje := 'en los ultimos meses';
    END;
    RETURN vMensaje;
  END;
  
  PROCEDURE P_RECHAZO_AFILIACION_PTOS(cCodGrupoCia_in    IN CHAR,
                                      cNumDocumento_in   IN CHAR,
                                      cNumTarjeta_in     IN CHAR,
                                      cSecMovCaja_in     IN CHAR)
  IS
  
  BEGIN
    UPDATE FID_TARJETA A
    SET A.DNI_CLI = NULL,
        A.COD_LOCAL = NULL,
        A.IND_PUNTOS = NULL,
        A.IND_ENVIADO_ORBIS = NULL
    WHERE A.COD_TARJETA = cNumTarjeta_in;
    
    DELETE FROM CE_MOV_CAJA_TARJETA A
    WHERE A.SEC_MOV_CAJA = cSecMovCaja_in
    AND A.COD_TARJETA = cNumTarjeta_in;
  END;
  
  
  FUNCTION F_EVALUA_TARJETA(cNumTarj_in     IN CHAR,
                            cTipoTarjeta_in IN CHAR) 
  RETURN CHAR IS
    nRetorno  CHAR(1) := 'N';
    vCantidad INTEGER;
  BEGIN
  
    SELECT COUNT(1)
      INTO vCantidad
      FROM VTA_RANGO_TARJETA T
     WHERE T.ESTADO = 'A'
       AND T.COD_TIPO_TARJETA = cTipoTarjeta_in
       AND cNumTarj_in BETWEEN T.DESDE AND T.HASTA;
  
    IF vCantidad > 0 THEN
      nRetorno := 'S';
    END IF;
    
    RETURN nRetorno;
  END;
  
  FUNCTION F_IND_ADICIONA_TARJ_ORBIS
    RETURN CHAR IS
    vIndicador CHAR(1);
  BEGIN
    SELECT TRIM(A.LLAVE_TAB_GRAL) 
    INTO   vIndicador
    FROM   PBL_TAB_GRAL A 
    WHERE  A.ID_TAB_GRAL = TAB_IND_BLOQ_ADIC_TARJ_ORBIS;
    RETURN vIndicador;
  EXCEPTION 
    WHEN OTHERS THEN
      RETURN 'N';
  END;
  
  FUNCTION F_GET_CALCULO_PAGO_CLIENTE(cCodGrupoCia_in    IN CHAR,
                                      cCodLocal_in       IN CHAR,
                                      cNumPedVta_in      IN CHAR,
                                      cMontoPago_in      IN NUMBER,
                                      nValorSelCopago_in IN NUMBER DEFAULT -1)
    RETURN NUMBER IS
    vResp             varchar2(3000);
    vPct_beneficiario mae_convenio.pct_beneficiario%TYPE;
    vPct_empresa      mae_convenio.pct_empresa%TYPE;

    curEscala           FarmaCursor;
    vMontoNeto          NUMBER(9, 2);
    vMontoCreditoEmpre  VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_Flg_Tipo_Convenio MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE;

    v_nCopago   FLOAT;
    v_nPctBenef mae_convenio.pct_beneficiario%TYPE;
    v_nPctEmp   mae_convenio.pct_empresa%TYPE;
    
    vCodConvenio VARCHAR2(10);
    vMontoTotalPedido VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;

  BEGIN
    
    SELECT NVL(CAB.COD_CONVENIO,'N'),
           CAB.VAL_NETO_PED_VTA
    INTO vCodConvenio,
         vMontoNeto
    FROM VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;
 
    IF vCodConvenio = 'N' THEN
      RETURN cMontoPago_in;
    END IF;
  
    SELECT CONV.PCT_BENEFICIARIO,
           CONV.PCT_EMPRESA,
           CONV.COD_TIPO_CONVENIO
    INTO vPct_beneficiario, 
         vPct_empresa, 
         v_Flg_Tipo_Convenio
    FROM MAE_CONVENIO CONV
    WHERE CONV.COD_CONVENIO = vCodConvenio;

    IF nValorSelCopago_in != -1 THEN
      vPct_beneficiario := nValorSelCopago_in;
      vPct_empresa      := 100 - nValorSelCopago_in;
    ELSE
--      vMontoNeto := TO_NUMBER(montoTotalPedido,'999999.99');
      v_nCopago  := PTOVENTA_CONV_BTLMF.FN_CALCULA_COPAGO(vCodConvenio, vMontoNeto, v_nPctBenef, v_nPctEmp);
      IF v_nCopago > -1 THEN
        vPct_beneficiario := v_nPctBenef;
        vPct_empresa      := v_nPctEmp;
      END IF;
    END IF;
    
    vPct_beneficiario := ROUND((NVL(vPct_beneficiario,0)/100),3);
    
    RETURN  ROUND((cMontoPago_in * vPct_beneficiario),3);
  END;
  
  FUNCTION F_IS_TARJETA_ASOCIADA(cNroDocumento_in IN FID_TARJETA.DNI_CLI%TYPE,
                                 cNroTarjeta_in   IN FID_TARJETA.COD_TARJETA%TYPE)
    RETURN CHAR IS
    vCantidadExiste INTEGER;
    nContador INTEGER := 0;
    nNroDocumento VARCHAR2(20);
  BEGIN
    nNroDocumento := cNroDocumento_in;
    -- KMONCADA 02.07.2015 VALIDA SI TARJETA ES UNA PROMOCION CONVENIO
    -- PARA MODIFICAR EL DOCUMENTO DE IDENTIDAD
    /*select count(1)
    into nContador
    from PTOVENTA.vta_rango_tarjeta
   WHERE DESDE <= substr(cNroTarjeta_in, 1, 12)
     AND HASTA >= substr(cNroTarjeta_in, 1, 12)
     AND COD_TIPO_TARJETA = 'TC';*/
    if nContador <> 0 then
       IF ( LENGTH(nNroDocumento) = 8 OR LENGTH(nNroDocumento) = 9 )THEN
         nNroDocumento := 'D' || nNroDocumento || 'C';
       END IF;
    end if;
  
    SELECT COUNT(1)
    INTO vCantidadExiste 
    FROM FID_TARJETA A
    WHERE A.DNI_CLI = nNroDocumento
    AND A.COD_TARJETA = cNroTarjeta_in
    AND NVL(A.IND_PUNTOS,'N') = 'S';
    IF vCantidadExiste = 0 THEN
      RETURN 'N';
    ELSE
      RETURN 'S';
    END IF; 
  END;
  
  FUNCTION F_CLIENTE_AFILIADO_PTOS(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cNroDocumento_in IN VARCHAR2
                                  )
  RETURN CHAR IS
    cantidad INTEGER := 0;
  BEGIN
    SELECT COUNT(1) 
    INTO cantidad
    FROM FID_TARJETA A 
    WHERE A.DNI_CLI = cNroDocumento_in
    AND A.COD_TARJETA IS NOT NULL
    --AND FARMA_PUNTOS.F_VAR_TARJ_VALIDA(cCodGrupoCia_in,cCodLocal_in,A.COD_TARJETA)='S';
    AND NVL(A.IND_PUNTOS,'N') = 'S'
    AND NVL(A.IND_ENVIADO_ORBIS,'N') IN ('E','P');

    IF cantidad = 0 THEN
      RETURN 'N';
    ELSE 
      RETURN 'S';
    END IF;
  END;
  
  /* ********************************************************************** */    
  FUNCTION F_VAR_IS_CTD_PTO_EXTRAS(cCodGrupoCia_in IN CHAR, 
                                   cCodLocal_in    IN CHAR,
                                   cNumPedVta_in   IN CHAR) 
  RETURN VARCHAR2
  IS
    vRetorno varchar2(1) := 'N';
    vSumnotnull number(9)  := 0;
  BEGIN
     select SUM(NVL(C.PTOS_AHORRO,0))
     into   vSumnotnull
     from   vta_pedido_vta_DET C
     where  C.cod_grupo_cia =  cCodGrupoCia_in
     and    C.cod_local = cCodLocal_in
     and    C.num_ped_vta =  cNumPedVta_in;

    return TRIM(TO_CHAR(vSumnotnull,'999999990.00'));
    
  END; 
  
  FUNCTION F_VAR_QUIMICO_BLOQUEO_TARJ
    RETURN CHAR 
  IS
    vIndicador CHAR(1);
  BEGIN
    SELECT TRIM(A.LLAVE_TAB_GRAL)
    INTO vIndicador
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL = TAB_IND_QUIMICO_BLOQUEA_TARJ;
    RETURN vIndicador;
  EXCEPTION
    WHEN OTHERS THEN
      RETURN 'S';
  END;
END FARMA_PUNTOS;
/
