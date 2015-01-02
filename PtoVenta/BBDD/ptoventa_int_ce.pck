CREATE OR REPLACE PACKAGE PTOVENTA.PTOVENTA_INT_CE is

  ESTADO_ACTIVO		  CHAR(1):='A';
  ESTADO_INACTIVO	  CHAR(1):='I';
  INDICADOR_SI		  CHAR(1):='S';
  INDICADOR_NO		  CHAR(1):='N';
  POS_INICIO		  CHAR(1):='I';

  TIP_MONEDA_SOLES CHAR(2) := '01';
  TIP_MONEDA_DOLARES CHAR(2) := '02';

  INDICADOR_ERROR CHAR(1) := '1';
  INDICADOR_CORRECTO CHAR(1) := '0';

  TIP_MONEDA_SOLES_SAP CHAR(3) := 'PEN';
  TIP_MONEDA_DOLARES_SAP CHAR(3) := 'USD';

  CLASE_DOC_SA CHAR(2) := 'SA';
  CLASE_DOC_DA CHAR(2) := 'DA';
  CLASE_DOC_KO CHAR(2) := 'KO';

  IND_IMPUESTO_S3 CHAR(2) := 'S3';
  IND_IMPUESTO_S0 CHAR(2) := 'S0';

  IND_RECAU_CMR CHAR(2):='01';
  IND_RECAU_CITIBANK_TRJ CHAR(2):='02';
  IND_RECAU_CLARO CHAR(2):='03';
  IND_RECAU_CITIBANK_PRES CHAR(2):='04';
  IND_RECAU_RIPLEY CHAR(2):='07';

  MARCA_MIFARMA CHAR(3) :='001';
  MARCA_FASA CHAR(3) :='002';
  MARCA_BTL CHAR(3) :='003';

  RUC_MIFARMA CHAR(11) :='20512002090';
  RUC_FASA CHAR(11) :='20305354563';
  RUC_BTL CHAR(11) :='20302629219';

  CENTRO_COSTO_CC CHAR(2) := 'CC';

  IND_CODIGO_DEPOSITO CHAR(2) := 'OP';

  NOM_TITULAR_MF CHAR(7) := 'MIFARMA';

  C_C_USU_CREA_INT_CE CHAR(8) := 'SISTEMAS';

  C_C_RT CHAR(2) := 'RT';

  C_C_MF CHAR(2) := 'MF';

  C_C_ERC CHAR(3) := 'ERC';

  TIP_MOV_CIERRE  	   CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='C';



  v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;

  C_C_TARJETAS CE_CUADRATURA.COD_CUADRATURA%TYPE :='000';
  C_C_ANUL_PENDIENTE CE_CUADRATURA.COD_CUADRATURA%TYPE :='001';
  C_C_REG_ANUL_PENDIENTE CE_CUADRATURA.COD_CUADRATURA%TYPE :='002';
  C_C_DEL_PENDIENTES CE_CUADRATURA.COD_CUADRATURA%TYPE :='003';
  C_C_COB_DEL_PENDIENTE CE_CUADRATURA.COD_CUADRATURA%TYPE :='004';
  C_C_ANUL_DEL_PENDIENTE CE_CUADRATURA.COD_CUADRATURA%TYPE :='005';
  C_C_INGRESO_COMP_MANUAL CE_CUADRATURA.COD_CUADRATURA%TYPE :='006';
  C_C_REG_COMP_MANUAL CE_CUADRATURA.COD_CUADRATURA%TYPE :='007';
  C_C_IND_DINERO_FALSO CE_CUADRATURA.COD_CUADRATURA%TYPE :='008';
  C_C_IND_ROBO CE_CUADRATURA.COD_CUADRATURA%TYPE :='009';
  C_C_DEFICIT_ASUMIDO_CAJERO CE_CUADRATURA.COD_CUADRATURA%TYPE :='010';

  C_C_DEPOSITO_VENTA CE_CUADRATURA.COD_CUADRATURA%TYPE :='011';
  C_C_SERVICIOS_BASICOS CE_CUADRATURA.COD_CUADRATURA%TYPE :='012';
  C_C_REEMBOLSO_C_CH CE_CUADRATURA.COD_CUADRATURA%TYPE :='013';
  C_C_PAGO_PLANILLA CE_CUADRATURA.COD_CUADRATURA%TYPE :='014';
  C_C_COTIZA_COMP CE_CUADRATURA.COD_CUADRATURA%TYPE :='015';
  C_C_ENTREGAS_RENDIR CE_CUADRATURA.COD_CUADRATURA%TYPE :='016';
  C_C_ROBO_ASALTO CE_CUADRATURA.COD_CUADRATURA%TYPE :='017';
  C_C_DINERO_FALSO CE_CUADRATURA.COD_CUADRATURA%TYPE :='018';
  C_C_OTROS_DESEMBOLSOS CE_CUADRATURA.COD_CUADRATURA%TYPE :='019';
  C_C_FONDO_SENCILLO CE_CUADRATURA.COD_CUADRATURA%TYPE :='020';
  C_C_DSCT_PERSONAL CE_CUADRATURA.COD_CUADRATURA%TYPE :='021';
  C_C_DEFICIT_ASUMIDO_QF CE_CUADRATURA.COD_CUADRATURA%TYPE :='022';
  C_C_DELIVERY_PERDIDO CE_CUADRATURA.COD_CUADRATURA%TYPE :='023';
  C_C_ADELANTO CE_CUADRATURA.COD_CUADRATURA%TYPE :='024';
  C_C_GRATIFICACION CE_CUADRATURA.COD_CUADRATURA%TYPE :='025';

  C_C_CREDITO_GRAL CE_CUADRATURA.COD_CUADRATURA%TYPE :='026';




  /*********************************************************/

  --11/12/2007 dubilluz modificacion
  PROCEDURE INT_GENERA_ARCHIVO(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in     IN CHAR,
                               vFecCierreDia_in IN CHAR);

  PROCEDURE INT_EJECT_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 000
  PROCEDURE INT_EJECUTA_CUADRATURA_000(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 001
  PROCEDURE INT_EJECUTA_CUADRATURA_001(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 002
  PROCEDURE INT_EJECUTA_CUADRATURA_002(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 003
  PROCEDURE INT_EJECUTA_CUADRATURA_003(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 004
  PROCEDURE INT_EJECUTA_CUADRATURA_004(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 005
  PROCEDURE INT_EJECUTA_CUADRATURA_005(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 006
  PROCEDURE INT_EJECUTA_CUADRATURA_006(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);


  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 007
  PROCEDURE INT_EJECUTA_CUADRATURA_007(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);


  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 008
  PROCEDURE INT_EJECUTA_CUADRATURA_008(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 009
  PROCEDURE INT_EJECUTA_CUADRATURA_009(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 010
  PROCEDURE INT_EJECUTA_CUADRATURA_010(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 011
  --07/12/2007 DUBILLUZ MODIFICACION
  PROCEDURE INT_EJECUTA_CUADRATURA_011(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 012
  PROCEDURE INT_EJECUTA_CUADRATURA_012(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 013
  PROCEDURE INT_EJECUTA_CUADRATURA_013(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 014
  PROCEDURE INT_EJECUTA_CUADRATURA_014(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 016
  PROCEDURE INT_EJECUTA_CUADRATURA_016(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 017
  PROCEDURE INT_EJECUTA_CUADRATURA_017(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 018
  PROCEDURE INT_EJECUTA_CUADRATURA_018(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 019
  PROCEDURE INT_EJECUTA_CUADRATURA_019(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 021
  PROCEDURE INT_EJECUTA_CUADRATURA_021(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 022
  PROCEDURE INT_EJECUTA_CUADRATURA_022(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 023
  PROCEDURE INT_EJECUTA_CUADRATURA_023(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 024
  PROCEDURE INT_EJECUTA_CUADRATURA_024(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 025
  PROCEDURE INT_EJECUTA_CUADRATURA_025(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);


  PROCEDURE INT_GRABA_INTERFACE_CE(cCodGrupoCia_in    IN CHAR,
                                   cCodLocal_in       IN CHAR,
                                   vFecOperacion_in   IN CHAR,
                                   cCodCuadratura_in  IN CHAR,
                                   cSecIntCe_in       IN CHAR,
                                   vClaseDoc_in       IN CHAR,
                                   cFecDocumento_in   IN CHAR,
                                   cFecContable_in    IN CHAR,
                                   vDescReferencia_in IN CHAR,
                                   cDescTextCab_in    IN CHAR,
                                   cTipMoneda_in      IN CHAR,
                                   vClaveCue1_in      IN CHAR,
                                   vCuenta1_in        IN CHAR,
                                   cMarcaImp_in       IN CHAR,
                                   cValImporte_in     IN CHAR,
                                   vDescAsig1_in      IN CHAR,
                                   cDescTextDet1_in   IN CHAR,
                                   cClaveCue2_in      IN CHAR,
                                   vCuenta2_in        IN CHAR,
                                   vCentroCosto_in    IN CHAR,
                                   cIndImp_in         IN CHAR,
                                   cDescTextDet2_in   IN CHAR,
                                   cUsuCreaIntCe_in   IN CHAR,
                                   CME_in             IN CHAR);

  --OBTIENE EL SECUENCIAL PARA EL CAMPO CONTADOR
  FUNCTION CE_GET_SECUENCIAL_INT(cCodGrupoCia_in      IN CHAR,
                                 cCodLocal_in         IN CHAR,
                                 vFecCierreDia_in     IN CHAR)
    RETURN CHAR;

  FUNCTION CE_VALIDA_DATA_INTERFACE(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    vFecCierreDia_in IN CHAR)
    RETURN CHAR;

  PROCEDURE CE_ACTUALIZA_FECHA_PROCESO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR,
                                       vFecProceso_in   IN DATE);

  PROCEDURE CE_ACTUALIZA_FECHA_ARCHIVO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR,
                                       vFecArchivo_in   IN DATE);

  --11/12/2007  dubilluz  modificacion
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        vEnviarOper_in   IN CHAR DEFAULT 'N');

  PROCEDURE INT_REVERTIR_CUADRATURAS_CE(cCodGrupoCia_in  IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        cFecCierreDia_in IN CHAR,
                                        cIdUsu_in        IN CHAR);

  --Descripcion: Envia un mail a Contabilidad indicándole
  --             los proveedores a los que debe generarle Código SAP
  --Fecha       Usuario		    Comentario
  --16/02/2006  Luis Reque    Creación
  PROCEDURE INT_SOLICITUD_COD_SAP (cCodGrupoCia    CHAR);

  --Descripcion: Validara si se grabaron correctamente las interfaces
  --Fecha       Usuario		    Comentario
  --08/12/2007  DUBILLUZ       Creación
  PROCEDURE INT_VALIDA_MONTO_INTERFACE(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cFecCierreDia_in  IN CHAR,
                                       cNombreArchivo_in IN CHAR);

  --OBTIENE EL QERY E INSERTA PARA EL TIPO DE CUADRATURA 026
  --Fecha       Usuario		 Comentario
  --04/08/2008  JOLIVA       Creación
  PROCEDURE INT_EJECUTA_CUADRATURA_026(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);


--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
--LAS NUEVAS CUADRATURAS AGREGADAS ES PARA CONSIDERAR LOS SGTE  - JCALLO - 28.11.2008*-*-*-*-
-- 1.- q las formas de pago declaradas MENOS la suma de los pedidos cobrados ==> AJUSTE  POR REDONDEO
-- 2.- ajustar la suma de forma de pago declaradas en dolares la conversion a soles se haga con el tipo de cambio de SAP.
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

  --OBTIENE EL QUERY E INSERTA PARA EL TIPO DE CUADRATURA 027
  --znviar redondeo por forma de pago declarados menos total de ventas del dia
  --Fecha       Usuario		   Comentario
  --28/11/2008  JCALLO       Creación

  PROCEDURE INT_EJECUTA_CUADRATURA_027(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QUERY E INSERTA PARA EL TIPO DE CUADRATURA 028
  --SUMA DE FORMA DE PAGO DECLARADOS con ajuste del monto de acuerdo al tipo de cambio SAP si la diferencia con el tipo de cambio MIFARMA es POSITIVO.
  --Fecha       Usuario		   Comentario
  --01/12/2008  JCALLO       Creación
  PROCEDURE INT_EJECUTA_CUADRATURA_028(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

  --OBTIENE EL QUERY E INSERTA PARA EL TIPO DE CUADRATURA 029
  --SUMA DE FORMA DE PAGO DECLARADOS con ajuste del monto de acuerdo al tipo de cambio SAP si la diferencia con el tipo de cambio MIFARMA es NEGATIVO.
  --Fecha       Usuario		   Comentario
  --01/12/2008  JCALLO       Creación
  PROCEDURE INT_EJECUTA_CUADRATURA_029(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);


  --OBTIENE EL QUERY E INSERTA PARA EL TIPO DE CUADRATURA 030
  --Enviar diferencia entre los montos de efectivo recaudado y  el efectivo depositado,es decir si  depositan dinero de más porque les sobro, esta diferencia debe ir a la Cta. 7680000000
  --Fecha       Usuario		   Comentario
  --30/12/2008  JCALLO       Creación
  PROCEDURE INT_EJECUTA_CUADRATURA_030(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);


--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
--             FIN DE NUEVAS CUADRATURAS                                      *-*-*-*-
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
--             INICIO DE CUADRATURAS DE RECAUDACIONES                         *-*-*-*-
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
PROCEDURE INT_EJECUTA_CUADRATURA_039(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

PROCEDURE INT_EJECUTA_CUADRATURA_040(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

PROCEDURE INT_EJECUTA_CUADRATURA_556(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR) ;

PROCEDURE INT_EJECUTA_CUADRATURA_557(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

PROCEDURE INT_EJECUTA_CUADRATURA_558(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

PROCEDURE INT_EJECUTA_CUADRATURA_559(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

PROCEDURE INT_EJECUTA_CUADRATURA_560(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

PROCEDURE INT_EJECUTA_CUADRATURA_561(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

PROCEDURE INT_EJECUTA_CUADRATURA_562(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);

PROCEDURE INT_EJECUTA_CUADRATURA_563(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR);
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
--             FIN DE CUADRATURAS DE RECAUDACIONES                         *-*-*-*-
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

end PTOVENTA_INT_CE;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA.PTOVENTA_INT_CE is

  /****************************************************************************/

  PROCEDURE INT_GENERA_ARCHIVO(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in     IN CHAR,
                               vFecCierreDia_in IN CHAR)
  AS
    CURSOR curResumenDia IS
    SELECT COD_LOCAL||
           TO_CHAR(FEC_OPERACION,'yyyyMMdd')||
           SEC_INT_CE||
           COD_CUADRATURA||
           CLASE_DOC||
           TO_CHAR(FEC_DOCUMENTO,'yyyyMMdd')||
           TO_CHAR(FEC_CONTABLE,'yyyyMMdd')||
           DESC_REFERENCIA||
           DESC_TEXTO_CAB||
           TIP_MONEDA||
           VAL_IMPORTE||
           DESC_CLAVE_CUENTA_1||
           DESC_CUENTA_1||
           CME||
           MARCA_IMPUESTO||
           DESC_ASIGNACION_1||
           DESC_TEXTO_DETALLE_1||
           DESC_CLAVE_CUENTA_2||
           DESC_CUENTA_2||
           DESC_CENTRO_COSTO||
           IND_IMPUESTO||
           DESC_TEXTO_DETALLE_2 RESUMEN
    FROM   INT_CAJA_ELECTRONICA ICE
    WHERE  ICE.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    ICE.COD_LOCAL = cCodLocal_in
    AND    ICE.FEC_OPERACION = vFecCierreDia_in
    AND    ICE.VAL_IMPORTE<>0
    ORDER BY COD_GRUPO_CIA,COD_LOCAL,TO_CHAR(FEC_OPERACION,'yyyyMMdd'),COD_CUADRATURA,SEC_INT_CE;

    v_rCurResumenDia curResumenDia%ROWTYPE;
    v_cIndValidacion CHAR(1);
    v_vNombreArchivo VARCHAR2(100);
    v_eControlValidacion EXCEPTION;
    cCodCia  CHAR(3);
  BEGIN
       EXECUTE IMMEDIATE 'ALTER SESSION SET NLS_DATE_FORMAT = ''DD/MM/YYYY HH24:MI:SS'' ';
       EXECUTE IMMEDIATE 'ALTER SESSION SET NLS_NUMERIC_CHARACTERS = ''.,'' ';

      v_cIndValidacion := CE_VALIDA_DATA_INTERFACE(cCodGrupoCia_in,
                                                   cCodLocal_in,
                                                   vFecCierreDia_in);
      IF v_cIndValidacion = INDICADOR_ERROR THEN
         RAISE v_eControlValidacion;
      END IF;

      --DBMS_OUTPUT.PUT_LINE('vFecCierreDia_in : ' || vFecCierreDia_in);
      --DBMS_OUTPUT.PUT_LINE('TO_DATE(vFecCierreDia_in,dd/MM/yyyy) : ' || TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'));
      --DBMS_OUTPUT.PUT_LINE('TO_CHAR(TO_DATE(vFecCierreDia_in,dd/MM/yyyy),yyyyMMdd) : ' || TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'yyyyMMdd'));

      SELECT cod_cia into cCodCia
      FROM Pbl_Local WHERE COD_GRUPO_CIA=cCodGrupoCia_in AND COD_lOCAL=cCodLocal_in;

      IF cCodCia=MARCA_MIFARMA THEN
         v_vNombreArchivo := 'CJE1170'||cCodLocal_in||TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MISS')||'.TXT';
      ELSE
           IF cCodCia=MARCA_FASA THEN
              v_vNombreArchivo := 'CJE1177'||cCodLocal_in||TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MISS')||'.TXT';
           ELSE
               v_vNombreArchivo := 'CJE1174'||cCodLocal_in||TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MISS')||'.TXT';
           END IF;
      END IF;

      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));
      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'INICIO');
      FOR v_rCurResumenDia IN curResumenDia
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurResumenDia.RESUMEN);
      END LOOP;
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'FIN');
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
      DBMS_OUTPUT.PUT_LINE('SE GENERO EL ARCHIVO DE INTERFACE CE CORRECTAMENTE');
      --INICIO ADICION CORREO
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                   'EXITO AL GENERAR ARCHIVO DE CAJA ELECTRONICA: ',
                                   'EXITO',
                                   'EXITO AL GENERAR ARCHIVO DE INTERFACE DE CAJA ELECTRONICA PARA LA FECHA: '||vFecCierreDia_in);/*||'</B>'||
                                   '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR EN LA VALIDACION DE LA INFORMACION.'||'<B>');      */
      --FIN ADICION CORREO
      --ACTUALIZA LA FECHA DE CREACION DEL ARCHIVO
      CE_ACTUALIZA_FECHA_ARCHIVO(cCodGrupoCia_in,
                                 cCodLocal_in,
                                 vFecCierreDia_in,
                                 SYSDATE);

      COMMIT;
      --procedure que valida si se grabo bien los montos
      --11/12/2007 dubilluz creacion
      INT_VALIDA_MONTO_INTERFACE(cCodGrupoCia_in,
                                 cCodLocal_in,
                                 vFecCierreDia_in,
                                 v_vNombreArchivo);

  EXCEPTION
    WHEN v_eControlValidacion THEN
         DBMS_OUTPUT.PUT_LINE('ERROR EN LA VALIDACION DE LA INFORMACION');
         INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                      'ERROR AL GENERAR INTERFACE CAJA ELECTRONICA: ',
                                      'ALERTA',
                                      'ERROR AL GENERAR LA INTERFACE DE CAJA ELECTRONICA PARA LA FECHA: '||vFecCierreDia_in||'</B>'||
                                      '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR EN LA VALIDACION DE LA INFORMACION.'||'<B>');
         ROLLBACK;
    WHEN OTHERS THEN
         DBMS_OUTPUT.PUT_LINE('ERROR AL GENERAR ARCHIVO DE INTERFACE CE - ' || SQLERRM);
         INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                      'ERROR AL GENERAR INTERFACE CAJA ELECTRONICA: ',
                                      'ALERTA',
                                      'ERROR AL GENERAR LA INTERFACE DE CAJA ELECTRONICA PARA LA FECHA: '||vFecCierreDia_in||'</B>'||
                                      '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR AL GENERAR ARCHIVO DE INTERFACE DE CE - ' || SQLERRM ||'<B>');

         ROLLBACK;
  END;

  PROCEDURE INT_EJECT_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR)
  AS
    v_nCant INTEGER;

    CURSOR fechaCierreDia IS
    SELECT TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy') FEC_CIERRE_DIA_VTA
    FROM   CE_CIERRE_DIA_VENTA CD
    WHERE  CD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CD.COD_LOCAL = cCodLocal_in
    -- AND    CD.FEC_CIERRE_DIA_VTA = '30/03/2010'--SYSDATE
    --AND    CD.FEC_CIERRE_DIA_VTA in('06/12/2007','07/12/2007','08/12/2007','09/12/2007')
    AND    CD.IND_VB_CONTABLE = INDICADOR_SI
    AND    CD.FEC_PROCESO IS NULL;
--    AND    CD.FEC_CIERRE_DIA_VTA = TO_DATE('04/08/2009','dd/MM/yyyy');

    CURSOR fechaCierreDiaArchivo IS
    SELECT CD.FEC_CIERRE_DIA_VTA
    FROM   CE_CIERRE_DIA_VENTA CD
    WHERE  CD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CD.COD_LOCAL = cCodLocal_in
    --AND    CD.FEC_CIERRE_DIA_VTA = '06/04/2009'
    -- AND    CD.FEC_CIERRE_DIA_VTA in('06/12/2007','07/12/2007','08/12/2007','09/12/2007')
    --AND    CD.IND_VB_CONTABLE = INDICADOR_SI
    AND    CD.FEC_PROCESO IS NOT NULL
    AND    CD.FEC_ARCHIVO IS NULL;


    CURSOR cuadraturas(p_FechaCierreDia CHAR) IS
    SELECT DISTINCT CC.COD_CUADRATURA
    FROM   CE_CUADRATURA_CAJA CC,
           CE_MOV_CAJA MOV
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = p_FechaCierreDia
    AND    cc.COD_CUADRATURA NOT IN ('032') -- dubilluz 31.08.2010 cotizacion competencia cajero
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    UNION
    SELECT DISTINCT CCD.COD_CUADRATURA
    FROM   CE_CIERRE_DIA_VENTA CDV,
           CE_CUADRATURA_CIERRE_DIA CCD
    WHERE  CDV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CDV.COD_LOCAL = cCodLocal_in
    AND    CDV.FEC_CIERRE_DIA_VTA= p_FechaCierreDia
    AND    CCD.COD_CUADRATURA NOT IN (C_C_COTIZA_COMP, C_C_FONDO_SENCILLO,C_C_SERVICIOS_BASICOS,C_C_REEMBOLSO_C_CH,C_C_OTROS_DESEMBOLSOS)
    AND    CDV.COD_GRUPO_CIA = CCD.COD_GRUPO_CIA
    AND    CDV.COD_LOCAL = CCD.COD_LOCAL
    AND    CDV.FEC_CIERRE_DIA_VTA= CCD.FEC_CIERRE_DIA_VTA
    UNION
    SELECT '000' FROM dual
    UNION
    SELECT '026'FROM DUAL
    WHERE EXISTS
          (
            SELECT 1
            FROM CE_MOV_CAJA MC,
                 CE_FORMA_PAGO_ENTREGA FPE,
                 VTA_FORMA_PAGO FP
            WHERE MC.FEC_DIA_VTA = p_FechaCierreDia
                  AND MC.TIP_MOV_CAJA = TIP_MOV_CIERRE
                  AND FPE.COD_GRUPO_CIA = MC.COD_GRUPO_CIA
                  AND FPE.COD_LOCAL = MC.COD_LOCAL
                  AND FPE.SEC_MOV_CAJA = MC.SEC_MOV_CAJA
                  AND FP.IND_TARJ = INDICADOR_NO
                  AND FP.IND_FORMA_PAGO_EFECTIVO = INDICADOR_NO
                  AND FP.COD_FORMA_PAGO = FPE.COD_FORMA_PAGO
                  AND FPE.EST_FORMA_PAGO_ENT = 'A'
/*
            UNION
            SELECT 1
            FROM VTA_PEDIDO_VTA_CAB C
            WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
              AND C.COD_LOCAL = cCodLocal_in
              AND C.FEC_PED_VTA BETWEEN TO_DATE(p_FechaCierreDia,'dd/MM/yyyy') AND TO_DATE(p_FechaCierreDia,'dd/MM/yyyy') + 1 - 1/24/60/60
              AND C.EST_PED_VTA = 'C'
              AND C.IND_PED_CONVENIO = 'S'
              AND C.IND_CONV_BTL_MF = 'S'
*/
          )

    UNION
    SELECT '011'FROM DUAL
    UNION
    SELECT '027'FROM DUAL
    UNION
    SELECT '028'FROM DUAL
    UNION
    SELECT '029'FROM DUAL
    UNION
    SELECT '030'FROM DUAL
    UNION
    SELECT '039'FROM DUAL
    UNION
    SELECT '040'FROM DUAL
    UNION
    SELECT '556'FROM DUAL
    UNION
    SELECT '557'FROM DUAL
    UNION
    SELECT '558'FROM DUAL
    UNION
    SELECT '559'FROM DUAL
    UNION
    SELECT '560'FROM DUAL
    UNION
    SELECT '561'FROM DUAL
    UNION
    SELECT '562'FROM DUAL
    UNION
    SELECT '563'FROM DUAL
    ORDER BY 1
    ;

  BEGIN

       FOR fechaCierreDia_rec IN fechaCierreDia
	     LOOP
           BEGIN
               v_nCant := 0;
               DBMS_OUTPUT.PUT_LINE('EMPIEZA A GRABAR EL DIA ' || fechaCierreDia_rec.FEC_CIERRE_DIA_VTA);
               FOR cuadraturas_rec IN cuadraturas(fechaCierreDia_rec.FEC_CIERRE_DIA_VTA)
        	     LOOP
                   v_nCant := v_nCant + 1;
                   DBMS_OUTPUT.PUT_LINE('Inicio de Cuadratura: ' || cuadraturas_rec.COD_CUADRATURA);
                   EXECUTE IMMEDIATE 'BEGIN  PTOVENTA_INT_CE.INT_EJECUTA_CUADRATURA_' || cuadraturas_rec.COD_CUADRATURA || '(:1,:2,:3); END;'
                           USING cCodGrupoCia_in,cCodLocal_in,fechaCierreDia_rec.FEC_CIERRE_DIA_VTA;
                   DBMS_OUTPUT.PUT_LINE('GRABO CORRECTAMENTE LA CUADRATURA ' || cuadraturas_rec.COD_CUADRATURA);

        	     END LOOP;
               DBMS_OUTPUT.PUT_LINE('GRABO CORRECTAMENTE EL DIA ' || fechaCierreDia_rec.FEC_CIERRE_DIA_VTA);
               --ACTUALIZA FECHA DE PROCESO DE INFORMACION
               CE_ACTUALIZA_FECHA_PROCESO(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          fechaCierreDia_rec.FEC_CIERRE_DIA_VTA,
                                          SYSDATE);

                  COMMIT;
               /*IF v_nCant > 0 THEN
                  INT_GENERA_ARCHIVO(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(fechaCierreDia_rec.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY'));
               END IF;*/
           EXCEPTION
              WHEN OTHERS THEN
                   DBMS_OUTPUT.PUT_LINE('ERROR AL GENERAR DATA EN TABLA DE INTERFACE - ' || SQLERRM);
                   INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                                'ERROR AL GENERAR INTERFACE CAJA ELECTRONICA: ',
                                                'ALERTA',
                                                'ERROR AL GENERAR LA INTERFACE DE CAJA ELECTRONICA PARA LA FECHA: '||fechaCierreDia_rec.FEC_CIERRE_DIA_VTA ||'</B>'||
                                                '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR AL GENERAR DATA EN TABLA DE INTERFACE- ' || SQLERRM ||'<B>');

                   ROLLBACK;
           END;
	     END LOOP;

      -- Se separo el proceso de generacion de archivo dependiendo de las fechas de gneraciion de proceso.
       FOR fechaCierreDiaArchivo_rec IN fechaCierreDiaArchivo
	     LOOP
           INT_GENERA_ARCHIVO(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(fechaCierreDiaArchivo_rec.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY'));
       END LOOP;

    --GENERA ARCHIVO
    --INT_GET_RESUMEN_DIA(cCodGrupoCia_in, cCodLocal_in,vFecProceso_in);
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('ERROR GENERAL AL GENERAR DATA EN TABLA DE INTERFACE - ' || SQLERRM);
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                   'ERROR AL GENERAR INTERFACE CAJA ELECTRONICA: ',
                                   'ALERTA',
                                   'ERROR AL GENERAR LA INTERFACE DE CAJA ELECTRONICA'||'</B>'||
                                   '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR GENERAL AL GENERAR DATA EN TABLA DE INTERFACE - ' || SQLERRM||'<B>');
      ROLLBACK;
  END;


   PROCEDURE INT_EJECUTA_CUADRATURA_000(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;
    CURSOR movCuadratura IS
    SELECT FPE.COD_GRUPO_CIA COD_GRUPO_CIA,
           FPE.COD_LOCAL COD_LOCAL,
           TO_CHAR(C.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           C_C_TARJETAS TIPO_OPERACION,
           CLASE_DOC_DA CLASE_DOC,
           CASE
           WHEN TD.COD_TIP_DEPOSITO = 'CR' THEN
                TO_CHAR(V1.FECHA,'DD/MM/YYYY')
           WHEN TD.COD_TIP_DEPOSITO = 'DB' THEN
                TO_CHAR(c.fec_dia_vta,'DD/MM/YYYY')
           END FECHA_DOC,
           CASE
           WHEN TD.COD_TIP_DEPOSITO = 'CR' THEN
                TO_CHAR(V1.FECHA,'DD/MM/YYYY')
           WHEN TD.COD_TIP_DEPOSITO = 'DB' THEN
                TO_CHAR(c.fec_dia_vta,'DD/MM/YYYY')
           END FECHA_CON,
           --TO_CHAR(F.FEC_CREA_FORMA_PAGO_ENT,'DD/MM/YYYY') FECHA_DOC,
           --TO_CHAR(F.FEC_CREA_FORMA_PAGO_ENT,'DD/MM/YYYY') FECHA_CON,
           DC.COD_DEPOSITO|| '-' || FPE.COD_LOCAL || '-' || TO_CHAR(C.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           -- 14/02/2008 DUBILLUZ  MODIFICACION
           -- 03/04/2008 DUBILLUZ  MODIFICACION
           CASE
           WHEN FPE.COD_FORMA_PAGO IN (SELECT F.COD_FORMA_PAGO
                                     FROM   VTA_FORMA_PAGO F
                                     WHERE  F.DESC_CORTA_FORMA_PAGO LIKE '%DELIVERY%')
                                     THEN
            DC.COD_DEPOSITO|| '-' || FPE.COD_LOCAL || '-' || TO_CHAR(C.FEC_DIA_VTA,'DDMMYYYY') || '-D'
           ELSE
            DC.COD_DEPOSITO|| '-' || FPE.COD_LOCAL || '-' || TO_CHAR(C.FEC_DIA_VTA,'DDMMYYYY')
           END TEX_CAB,
           DECODE(FPE.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(SUM(FPE.MON_ENTREGA),'999999990.00') IMPORTE,
           TD.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           DC.VAL_CUENTA CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           CASE
           WHEN TD.COD_TIP_DEPOSITO = 'CR' THEN
                TD.COD_TIP_DEPOSITO || '-' || V1.OPERACION
           WHEN TD.COD_TIP_DEPOSITO = 'DB' THEN
                TD.COD_TIP_DEPOSITO || '-' || FPE.NUM_LOTE
           END ASIG_1,
           -- 14/02/2008 DUBILLUZ  MODIFICACION
           -- 03/04/2008 DUBILLUZ  MODIFICACION
           CASE
           WHEN FPE.COD_FORMA_PAGO IN (SELECT F.COD_FORMA_PAGO
                                     FROM   VTA_FORMA_PAGO F
                                     WHERE  F.DESC_CORTA_FORMA_PAGO LIKE '%DELIVERY%')
                                     THEN
            DC.COD_DEPOSITO|| '-' || FPE.COD_LOCAL || '-' || TO_CHAR(C.FEC_DIA_VTA,'DDMMYYYY') || '-D'
           ELSE
            DC.COD_DEPOSITO|| '-' || FPE.COD_LOCAL || '-' || TO_CHAR(C.FEC_DIA_VTA,'DDMMYYYY')
           END TEX_DET_1,
           --DC.COD_DEPOSITO|| '-' || F.COD_LOCAL || '-' || TO_CHAR(C.FEC_DIA_VTA,'DDMMYYYY') TEX_DET_1,
           '11' CLA_CUE_2,
           'CR' CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           -- 14/02/2008 DUBILLUZ  MODIFICACION
           -- 03/04/2008 DUBILLUZ  MODIFICACION
           CASE
           WHEN FPE.COD_FORMA_PAGO IN (SELECT F.COD_FORMA_PAGO
                                     FROM   VTA_FORMA_PAGO F
                                     WHERE  F.DESC_CORTA_FORMA_PAGO LIKE '%DELIVERY%')
                                     THEN
            DC.COD_DEPOSITO|| '-' || FPE.COD_LOCAL || '-' || TO_CHAR(C.FEC_DIA_VTA,'DDMMYYYY') || '-D'
           ELSE
            DC.COD_DEPOSITO|| '-' || FPE.COD_LOCAL || '-' || TO_CHAR(C.FEC_DIA_VTA,'DDMMYYYY')
           END TEX_DET_2
    FROM   CE_FORMA_PAGO_ENTREGA FPE,
           VTA_FORMA_PAGO P,
           CE_MOV_CAJA C,
           PBL_DEPOSITO_CUENTA DC,
           PBL_TIP_DEPOSITO TD,
           (SELECT CD.NUM_OPERACION OPERACION,
                   CD.FEC_OPERACION FECHA,
                   CD.COD_LOCAL LOCAL,
                   CD.COD_GRUPO_CIA CIA,
                   CD.FEC_CIERRE_DIA_VTA,
                   CD.COD_FORMA_PAGO
            FROM   CE_CUADRATURA_CIERRE_DIA CD,
                   VTA_FORMA_PAGO_LOCAL F,
                   VTA_FORMA_PAGO FP,
                   PBL_TIP_DEPOSITO TD
            WHERE  CD.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = cCodLocal_in
            AND    CD.COD_CUADRATURA = C_C_DEPOSITO_VENTA
            AND    CD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
            AND    FP.IND_TARJ = INDICADOR_SI
            AND    CD.COD_GRUPO_CIA = F.COD_GRUPO_CIA
            AND    CD.COD_LOCAL = F.COD_LOCAL
            AND    CD.COD_FORMA_PAGO = F.COD_FORMA_PAGO
            AND    FP.COD_GRUPO_CIA = F.COD_GRUPO_CIA
            AND    FP.COD_FORMA_PAGO = F.COD_FORMA_PAGO
            AND    FP.COD_TIP_DEPOSITO = TD.COD_TIP_DEPOSITO)V1
    WHERE  FPE.COD_GRUPO_CIA = ccodgrupocia_in
    AND    FPE.COD_LOCAL = ccodlocal_in
    AND    FPE.EST_FORMA_PAGO_ENT = 'A'
    AND    P.IND_TARJ = INDICADOR_SI
    AND    FPE.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                              FROM   CE_MOV_CAJA A
                              WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                              AND    A.COD_LOCAL = ccodlocal_in
                              AND    A.FEC_DIA_VTA = vFecCierreDia_in
                              AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
    AND    FPE.COD_GRUPO_CIA = P.COD_GRUPO_CIA
    AND    FPE.COD_LOCAL = C.COD_LOCAL
    AND    FPE.SEC_MOV_CAJA = C.SEC_MOV_CAJA
    AND    FPE.COD_GRUPO_CIA = P.COD_GRUPO_CIA
    AND    FPE.COD_FORMA_PAGO = P.COD_FORMA_PAGO
    AND    P.SEC_DEP_CUENTA = DC.SEC_DEP_CUENTA
    AND    P.COD_TIP_DEPOSITO = TD.COD_TIP_DEPOSITO
    AND    V1.CIA(+) = FPE.COD_GRUPO_CIA
    AND    V1.LOCAL(+) = FPE.COD_LOCAL
    AND    V1.COD_FORMA_PAGO(+) = FPE.COD_FORMA_PAGO
    GROUP BY FPE.COD_GRUPO_CIA,
          FPE.COD_LOCAL,
          C.FEC_DIA_VTA,
          --TO_CHAR(F.FEC_CREA_FORMA_PAGO_ENT,'DD/MM/YYYY'),
          FPE.TIP_MONEDA,
          TD.DESC_CLAVE_CUENTA_1,
          DC.VAL_CUENTA,
          TD.COD_TIP_DEPOSITO,
          DC.COD_DEPOSITO,
          FPE.COD_FORMA_PAGO,
          FPE.NUM_LOTE,
          V1.OPERACION,
          CASE
          WHEN TD.COD_TIP_DEPOSITO = 'CR' THEN
               TO_CHAR(V1.FECHA,'DD/MM/YYYY')
          WHEN TD.COD_TIP_DEPOSITO = 'DB' THEN
               TO_CHAR(c.fec_dia_vta,'DD/MM/YYYY')
          END,
          CASE
          WHEN TD.COD_TIP_DEPOSITO = 'CR' THEN
               TO_CHAR(V1.FECHA,'DD/MM/YYYY')
          WHEN TD.COD_TIP_DEPOSITO = 'DB' THEN
               TO_CHAR(c.fec_dia_vta,'DD/MM/YYYY')
          END;
          --TO_CHAR(V1.FECHA,'DD/MM/YYYY');
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;
  PROCEDURE INT_EJECUTA_CUADRATURA_001(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CC.COD_GRUPO_CIA COD_GRUPO_CIA,
           CC.COD_LOCAL COD_LOCAL,
           --TO_CHAR(MOV.FEC_DIA_VTA,'YYYYMMDD') FECHA,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           CC.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           DECODE(CC.TIP_COMP,'01','BOL','FAC')||'-'||CC.NUM_SERIE_LOCAL||'-'||CC.NUM_COMP_PAGO ASIGNACION,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') TEX_CAB,
           DECODE(CC.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           --CC.MON_MONEDA_ORIGEN IMPORTE,
           TO_CHAR(CC.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CAJA CC,
           CE_CUADRATURA CU,
           CE_MOV_CAJA MOV
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_ANUL_PENDIENTE
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    CC.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CC.COD_CUADRATURA= CU.COD_CUADRATURA;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIGNACION, -- movCuadratura_rec.ASIG_1, MODIFICADO POR JOLIVA 2008-03-14
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;

  /**************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_002(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CC.COD_GRUPO_CIA COD_GRUPO_CIA,
           CC.COD_LOCAL COD_LOCAL,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           CC.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           DECODE(CC.TIP_COMP,'01','BOL','FAC')||'-'||CC.NUM_SERIE_LOCAL||'-'||CC.NUM_COMP_PAGO ASIGNACION,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') TEX_CAB,
           DECODE(CC.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CC.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CAJA CC,
           CE_CUADRATURA CU,
           CE_MOV_CAJA MOV
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_REG_ANUL_PENDIENTE
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    CC.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CC.COD_CUADRATURA= CU.COD_CUADRATURA;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECCIERREDIA_IN);
           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIGNACION, -- movCuadratura_rec.ASIG_1, MODIFICADO POR JOLIVA 2008-03-14
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_003(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CC.COD_GRUPO_CIA COD_GRUPO_CIA,
           CC.COD_LOCAL COD_LOCAL,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           CC.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           DECODE(CC.TIP_COMP,'01','BOL','FAC')||'-'||CC.NUM_SERIE_LOCAL||'-'||CC.NUM_COMP_PAGO ASIGNACION,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') TEX_CAB,
           DECODE(CC.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           --TO_CHAR(CC.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE, antes
           TO_CHAR(CC.Mon_Total ,'999999990.00') IMPORTE, --ASOSA, 21.10.2010
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CAJA CC,
           CE_CUADRATURA CU,
           CE_MOV_CAJA MOV
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_DEL_PENDIENTES
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    CC.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CC.COD_CUADRATURA= CU.COD_CUADRATURA;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECCIERREDIA_IN);
           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIGNACION, -- movCuadratura_rec.ASIG_1, MODIFICADO POR JOLIVA 2008-03-14
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_004(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CC.COD_GRUPO_CIA COD_GRUPO_CIA,
           CC.COD_LOCAL COD_LOCAL,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           CC.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           DECODE(CC.TIP_COMP,'01','BOL','FAC')||'-'||CC.NUM_SERIE_LOCAL||'-'||CC.NUM_COMP_PAGO ASIGNACION,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') TEX_CAB,
           DECODE(CC.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           --TO_CHAR(CC.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE, antes
           TO_CHAR(CC.Mon_Total,'999999990.00') IMPORTE, --ASOSA, 25.10.2010
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CAJA CC,
           CE_CUADRATURA CU,
           CE_MOV_CAJA MOV
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_COB_DEL_PENDIENTE
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    CC.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CC.COD_CUADRATURA= CU.COD_CUADRATURA;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECCIERREDIA_IN);
           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIGNACION, -- movCuadratura_rec.ASIG_1, MODIFICADO POR JOLIVA 2008-03-14
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_005(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CC.COD_GRUPO_CIA COD_GRUPO_CIA,
           CC.COD_LOCAL COD_LOCAL,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           CC.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           DECODE(CC.TIP_COMP,'01','BOL','FAC')||'-'||CC.NUM_SERIE_LOCAL||'-'||CC.NUM_COMP_PAGO ASIGNACION,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') TEX_CAB,
           DECODE(CC.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           --TO_CHAR(CC.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE, antes
           TO_CHAR(CC.Mon_Total,'999999990.00') IMPORTE, --ASOSA, 25.10.2010
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CAJA CC,
           CE_CUADRATURA CU,
           CE_MOV_CAJA MOV
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_ANUL_DEL_PENDIENTE
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    CC.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CC.COD_CUADRATURA= CU.COD_CUADRATURA;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECCIERREDIA_IN);
           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIGNACION, -- movCuadratura_rec.ASIG_1, MODIFICADO POR JOLIVA 2008-03-14
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_006(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CC.COD_GRUPO_CIA COD_GRUPO_CIA,
           CC.COD_LOCAL COD_LOCAL,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           CC.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           DECODE(CC.TIP_COMP,'01','BOL','FAC')||'-'||CC.NUM_SERIE_LOCAL||'-'||CC.NUM_COMP_PAGO ASIGNACION,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') TEX_CAB,
           DECODE(CC.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CC.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CAJA CC,
           CE_CUADRATURA CU,
           CE_MOV_CAJA MOV
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_INGRESO_COMP_MANUAL
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    CC.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CC.COD_CUADRATURA= CU.COD_CUADRATURA;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECCIERREDIA_IN);
           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIGNACION, -- movCuadratura_rec.ASIG_1, MODIFICADO POR JOLIVA 2008-03-14
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_007(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CC.COD_GRUPO_CIA COD_GRUPO_CIA,
           CC.COD_LOCAL COD_LOCAL,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           CC.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           DECODE(CC.TIP_COMP,'01','BOL','FAC')||'-'||CC.NUM_SERIE_LOCAL||'-'||CC.NUM_COMP_PAGO ASIGNACION,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') TEX_CAB,
           DECODE(CC.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CC.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CAJA CC,
           CE_CUADRATURA CU,
           CE_MOV_CAJA MOV
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_REG_COMP_MANUAL
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    CC.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CC.COD_CUADRATURA= CU.COD_CUADRATURA;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECCIERREDIA_IN);
           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIGNACION, -- movCuadratura_rec.ASIG_1, MODIFICADO POR JOLIVA 2008-03-14
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;

  /****************************************************************************/

  /*PROCEDURE INT_EJECUTA_CUADRATURA_008(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CC.COD_GRUPO_CIA COD_GRUPO_CIA,
           CC.COD_LOCAL COD_LOCAL,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           CC.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           ' ' REFERENCIA,
           SUBSTR(TRAB.APE_PAT_TRAB || ',' || TRAB.NOM_TRAB || '/' || CU.DESC_CORTA_CUADRATURA,0,25) TEX_CAB,
           DECODE(CC.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CC.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           TRAB.APE_PAT_TRAB || ',' || TRAB.NOM_TRAB || '/' ||CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           'RH' || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'MON')|| '-' || LOCAL.DESC_CORTA_LOCAL TEX_DET_2
    FROM   CE_CUADRATURA_CAJA CC,
           CE_CUADRATURA CU,
           CE_MOV_CAJA MOV,
           PBL_USU_LOCAL ULOCAL,
           CE_MAE_TRAB TRAB,
           PBL_LOCAL LOCAL
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_IND_DINERO_FALSO
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    CC.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CC.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    MOV.COD_GRUPO_CIA = ULOCAL.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = ULOCAL.COD_LOCAL
    AND    MOV.SEC_USU_LOCAL = ULOCAL.SEC_USU_LOCAL(+)
    AND    ULOCAL.COD_TRAB = TRAB.COD_TRAB(+)
    AND    ULOCAL.COD_CIA = TRAB.COD_CIA (+)
    AND    LOCAL.COD_LOCAL = ULOCAL.COD_LOCAL
    AND    LOCAL.COD_GRUPO_CIA = ULOCAL.COD_GRUPO_CIA;

    CURSOR validaCuadratura IS
    SELECT ULOCAL.COD_CIA,
           ULOCAL.COD_TRAB
    FROM   CE_CUADRATURA_CAJA CC,
           CE_MOV_CAJA MOV,
           PBL_USU_LOCAL ULOCAL
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_IND_DINERO_FALSO
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    MOV.COD_GRUPO_CIA = ULOCAL.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = ULOCAL.COD_LOCAL
    AND    MOV.SEC_USU_LOCAL = ULOCAL.SEC_USU_LOCAL;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.COD_CIA IS NULL OR validaCuadratura_rec.COD_TRAB IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_IND_DINERO_FALSO ||' - DINERO FALSO');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECCIERREDIA_IN);
           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;
*/

  --  FECHA      AUTOR     DESCRIPCION
  --29/08/2007  DUBILLUZ   SE AÑADIO EL DNI Y DATOS DEL TRABAJADOR NO ENCONTRADO
  PROCEDURE INT_EJECUTA_CUADRATURA_008(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CC.COD_GRUPO_CIA COD_GRUPO_CIA,
           CC.COD_LOCAL COD_LOCAL,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           CC.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           ' ' REFERENCIA,
           SUBSTR(TRAB.APE_PAT_TRAB || ',' || TRAB.NOM_TRAB || '/' || CU.DESC_CORTA_CUADRATURA,0,25) TEX_CAB,
           DECODE(CC.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CC.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
-- 2010-01-20: JOLIVA  Se mostrará el DNI en lugar del código del trabajador
--           NVL(TRAB.COD_TRAB_RRHH,' ') ASIG_1,
           ---jquispe 11.04.2011 cambio interfaz para conv mifarma
           NVL(LPAD(TRAB.NUM_DOC_IDEN,10,'0'),' ') ASIG_1,
           TRAB.APE_PAT_TRAB || ',' || TRAB.NOM_TRAB || '/' ||CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           'RH' || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'MON')|| '-' || LOCAL.DESC_CORTA_LOCAL TEX_DET_2
    FROM   CE_CUADRATURA_CAJA CC,
           CE_CUADRATURA CU,
           CE_MOV_CAJA MOV,
           PBL_USU_LOCAL ULOCAL,
           CE_MAE_TRAB TRAB,
           PBL_LOCAL LOCAL
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_IND_DINERO_FALSO
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    CC.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CC.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    MOV.COD_GRUPO_CIA = ULOCAL.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = ULOCAL.COD_LOCAL
    AND    MOV.SEC_USU_LOCAL = ULOCAL.SEC_USU_LOCAL(+)
    AND    ULOCAL.COD_TRAB = TRAB.COD_TRAB(+)
    AND    ULOCAL.COD_CIA = TRAB.COD_CIA (+)
    AND    LOCAL.COD_LOCAL = ULOCAL.COD_LOCAL
    AND    LOCAL.COD_GRUPO_CIA = ULOCAL.COD_GRUPO_CIA;

    CURSOR validaCuadratura IS
    SELECT ULOCAL.COD_CIA,
           ULOCAL.COD_TRAB,
           ----------------------
           --29/08/2007 DUBILLUZ  AGREGADO PARA ENVIAR LOS DATOS EN EL EMAIL
           ULOCAL.SEC_USU_LOCAL  ,
           NVL(ULOCAL.DNI_USU,' ') dni ,
           NVL(ULOCAL.NOM_USU,' ') || ' '|| NVL(ULOCAL.APE_PAT,' ')||'  '|| nvl(ULOCAL.APE_MAT,' ')  datos
           ----------------------
    FROM   CE_CUADRATURA_CAJA CC,
           CE_MOV_CAJA MOV,
           PBL_USU_LOCAL ULOCAL
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_IND_DINERO_FALSO
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    MOV.COD_GRUPO_CIA = ULOCAL.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = ULOCAL.COD_LOCAL
    AND    MOV.SEC_USU_LOCAL = ULOCAL.SEC_USU_LOCAL;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.COD_CIA IS NULL OR validaCuadratura_rec.COD_TRAB IS NULL THEN
              ---se enviara el DNI y Nombre-Apellido del Trabajador
              DBMS_OUTPUT.put_line('TRABAJADOR  ' || validaCuadratura_rec.Sec_Usu_Local  );
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_IND_DINERO_FALSO ||' - DINERO FALSO ' ||
                                      '  Nombre y Apellido  : ' || validaCuadratura_rec.Datos ||
                                      '  DNI : ' || validaCuadratura_rec.Dni  );
              --RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_IND_DINERO_FALSO ||' - DINERO FALSO');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECCIERREDIA_IN);
           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_009(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CC.COD_GRUPO_CIA COD_GRUPO_CIA,
           CC.COD_LOCAL COD_LOCAL,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           CC.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') TEX_CAB,
           DECODE(CC.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CC.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CAJA CC,
           CE_CUADRATURA CU,
           CE_MOV_CAJA MOV
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_IND_ROBO
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    CC.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CC.COD_CUADRATURA= CU.COD_CUADRATURA;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECCIERREDIA_IN);
           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;

  /****************************************************************************/

  /*PROCEDURE INT_EJECUTA_CUADRATURA_010(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CC.COD_GRUPO_CIA COD_GRUPO_CIA,
           CC.COD_LOCAL COD_LOCAL,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           CC.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') TEX_CAB,
           DECODE(CC.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CC.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           TO_CHAR(MOV.FEC_DIA_VTA,'MON') || '-' || LOCAL.DESC_CORTA_LOCAL || '-' || TRAB.APE_PAT_TRAB || ',' || TRAB.NOM_TRAB TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CAJA CC,
           CE_CUADRATURA CU,
           CE_MOV_CAJA MOV,
           PBL_USU_LOCAL ULOCAL,
           CE_MAE_TRAB TRAB,
           PBL_LOCAL LOCAL
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_DEFICIT_ASUMIDO_CAJERO
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    CC.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CC.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    MOV.COD_GRUPO_CIA = ULOCAL.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = ULOCAL.COD_LOCAL
    AND    MOV.SEC_USU_LOCAL = ULOCAL.SEC_USU_LOCAL(+)
    AND    ULOCAL.COD_TRAB = TRAB.COD_TRAB(+)
    AND    ULOCAL.COD_CIA = TRAB.COD_CIA (+)
    AND    LOCAL.COD_LOCAL = ULOCAL.COD_LOCAL
    AND    LOCAL.COD_GRUPO_CIA = ULOCAL.COD_GRUPO_CIA;

    CURSOR validaCuadratura IS
    SELECT ULOCAL.COD_CIA,
           ULOCAL.COD_TRAB
    FROM   CE_CUADRATURA_CAJA CC,
           CE_MOV_CAJA MOV,
           PBL_USU_LOCAL ULOCAL
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_DEFICIT_ASUMIDO_CAJERO
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    MOV.COD_GRUPO_CIA = ULOCAL.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = ULOCAL.COD_LOCAL
    AND    MOV.SEC_USU_LOCAL = ULOCAL.SEC_USU_LOCAL;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.COD_CIA IS NULL OR validaCuadratura_rec.COD_TRAB IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_DEFICIT_ASUMIDO_CAJERO||' - DEFICIT ASUMIDO CAJERO');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECCIERREDIA_IN);
           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;
*/

  --29/08/2007  DUBILLUZ  MODIFICADO
  PROCEDURE INT_EJECUTA_CUADRATURA_010(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CC.COD_GRUPO_CIA COD_GRUPO_CIA,
           CC.COD_LOCAL COD_LOCAL,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           CC.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') TEX_CAB,
           DECODE(CC.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CC.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           --NVL(LPAD(TRAB.NUM_DOC_IDEN,10,'0'),' ') ASIG_1,
           NVL(LPAD(ULOCAL.DNI_USU,10,'0'),' ') ASIG_1,
           TO_CHAR(MOV.FEC_DIA_VTA,'MON') || '-' || LOCAL.COD_LOCAL || '-' || TRAB.APE_PAT_TRAB || ',' || TRAB.NOM_TRAB TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || CC.COD_LOCAL || '-' || TO_CHAR(MOV.FEC_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CAJA CC,
           CE_CUADRATURA CU,
           CE_MOV_CAJA MOV,
           PBL_USU_LOCAL ULOCAL,
           CE_MAE_TRAB TRAB,
           PBL_LOCAL LOCAL
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_DEFICIT_ASUMIDO_CAJERO
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    CC.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CC.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    MOV.COD_GRUPO_CIA = ULOCAL.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = ULOCAL.COD_LOCAL
    AND    MOV.SEC_USU_LOCAL = ULOCAL.SEC_USU_LOCAL(+)
    AND    ULOCAL.COD_TRAB = TRAB.COD_TRAB(+)
    AND    ULOCAL.COD_CIA = TRAB.COD_CIA (+)
    AND    LOCAL.COD_LOCAL = ULOCAL.COD_LOCAL
    AND    LOCAL.COD_GRUPO_CIA = ULOCAL.COD_GRUPO_CIA;

    CURSOR validaCuadratura IS
    SELECT ULOCAL.COD_CIA,
           ULOCAL.COD_TRAB,
           ----------------------
           --29/08/2007 DUBILLUZ  AGREGADO PARA ENVIAR LOS DATOS EN EL EMAIL
           ULOCAL.SEC_USU_LOCAL  ,
           NVL(ULOCAL.DNI_USU,' ') dni ,
           NVL(ULOCAL.NOM_USU,' ') || ' '|| NVL(ULOCAL.APE_PAT,' ')||'  '|| nvl(ULOCAL.APE_MAT,' ')  datos
           ----------------------

    FROM   CE_CUADRATURA_CAJA CC,
           CE_MOV_CAJA MOV,
           PBL_USU_LOCAL ULOCAL
    WHERE  MOV.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    MOV.COD_LOCAL = cCodLocal_in
    AND    MOV.FEC_DIA_VTA = vFecCierreDia_in
    AND    CC.COD_CUADRATURA = C_C_DEFICIT_ASUMIDO_CAJERO
    AND    MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = CC.COD_LOCAL
    AND    MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
    AND    MOV.COD_GRUPO_CIA = ULOCAL.COD_GRUPO_CIA
    AND    MOV.COD_LOCAL = ULOCAL.COD_LOCAL
    AND    MOV.SEC_USU_LOCAL = ULOCAL.SEC_USU_LOCAL;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.COD_CIA IS NULL OR validaCuadratura_rec.COD_TRAB IS NULL THEN
              ---se enviara el DNI y Nombre-Apellido del Trabajador
              DBMS_OUTPUT.put_line('TRABAJADOR  ' || validaCuadratura_rec.Sec_Usu_Local  );
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_DEFICIT_ASUMIDO_CAJERO||' - DEFICIT ASUMIDO CAJERO' ||
                                      '  Nombre y Apellido  : ' || validaCuadratura_rec.Datos ||
                                      '  DNI : ' || validaCuadratura_rec.Dni  );
              --RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_DEFICIT_ASUMIDO_CAJERO||' - DEFICIT ASUMIDO CAJERO');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECCIERREDIA_IN);
           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_011(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_DA CLASE_DOC,
           TO_CHAR(CCD.FEC_OPERACION,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_OPERACION,'DD/MM/YYYY') FECHA_CON,
           DC.COD_DEPOSITO|| '-' || CCD.COD_LOCAL || '-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DDMMYYYY')REFERENCIA,
           DC.COD_DEPOSITO|| '-' || CCD.COD_LOCAL || '-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DDMMYYYY')TEX_CAB    ,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           TD.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           --DC.VAL_CUENTA CUE_1,
           --Se obtiene el numero de cuenta correcto al que debe de ser
           --07.12.2007 DUBILLUZ MODIFICACION
           DECODE(DC.COD_DEPOSITO,C_C_RT,nvl(EFC.NUM_CUENTA_SAP,' '),DC.VAL_CUENTA) CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           CASE
               WHEN TD.COD_TIP_DEPOSITO = IND_CODIGO_DEPOSITO THEN
                    ' '
               ELSE
                   TD.COD_TIP_DEPOSITO || '-' || CCD.NUM_OPERACION
           END ASIG_1,
           CASE
               WHEN TD.COD_TIP_DEPOSITO = IND_CODIGO_DEPOSITO THEN
                    TD.COD_TIP_DEPOSITO || '-' || CCD.NUM_OPERACION || '-' || LOC.DESC_CORTA_LOCAL
               ELSE
                   DC.COD_DEPOSITO|| '-' || CCD.COD_LOCAL || '-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DDMMYYYY')
           END TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           DC.COD_DEPOSITO|| '-' || CCD.COD_LOCAL || '-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DDMMYYYY') TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU,
           VTA_FORMA_PAGO FP,
           PBL_DEPOSITO_CUENTA DC,
           PBL_TIP_DEPOSITO TD,
           PBL_LOCAL LOC,
           CE_ENTIDAD_FINANCIERA_CUENTA EFC
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_DEPOSITO_VENTA
    AND    FP.IND_TARJ = INDICADOR_NO
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    CCD.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
    AND    CCD.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
    AND    FP.SEC_DEP_CUENTA = DC.SEC_DEP_CUENTA
    AND    FP.COD_TIP_DEPOSITO = TD.COD_TIP_DEPOSITO
    AND    CCD.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
    AND    CCD.COD_LOCAL = LOC.COD_LOCAL
    AND    CCD.SEC_ENT_FINAN_CUENTA = EFC.SEC_ENT_FINAN_CUENTA

    UNION ALL

    --SELECT FPE.TIP_MONEDA, fpe.mon_entrega, fpe.mon_entrega_total, S.COD_SOBRE, R.FEC_DIA_VTA
    SELECT DR.COD_GRUPO_CIA COD_GRUPO_CIA,
           DR.COD_LOCAL COD_LOCAL,
           TO_CHAR(DR.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           C.COD_CUADRATURA TIPO_OPERACION,--C_C_DEPOSITO_VENTA
           C.COD_CLASE_DOCUMENTO CLASE_DOC,--CLASE_DOC_DA
           TO_CHAR(DR.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(DR.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           'RT'|| '-' || DR.COD_LOCAL || '-' || TO_CHAR(DR.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           'RT'|| '-' || DR.COD_LOCAL || '-' || TO_CHAR(DR.FEC_DIA_VTA,'DDMMYYYY') TEX_CAB,
           DECODE(FPE.TIP_MONEDA,/*TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP*/'01','PEN','USD') MONEDA,
           TO_CHAR(FPE.MON_ENTREGA,'999999990.00') IMPORTE,
           '40' CLA_CUE_1,--DESC_CLAVE_CUENTA_1
           CP.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           s.Cod_Local||'-'||trim(S.COD_SOBRE) ASIG_1,
           'RT'|| '-'||S.COD_LOCAL||'-' || TRIM(S.COD_SOBRE)||'-'||
           NVL( (SELECT US.COD_TRAB_RRHH FROM PBL_USU_LOCAL US WHERE US.COD_GRUPO_CIA = DR.COD_GRUPO_CIA AND US.COD_LOCAL = DR.COD_LOCAL AND US.LOGIN_USU = S.USU_CREA_SOBRE),' ') TEX_DET_1,
           '11' CLA_CUE_2,
           C.Desc_Cuenta_2  CUE_2,--CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           'RT'|| '-'||S.COD_LOCAL||'-' || TRIM(S.COD_SOBRE)||'-'||
           NVL( (SELECT US.COD_TRAB_RRHH FROM PBL_USU_LOCAL US WHERE US.COD_GRUPO_CIA = DR.COD_GRUPO_CIA AND US.COD_LOCAL = DR.COD_LOCAL AND US.LOGIN_USU = S.USU_CREA_SOBRE),' ') TEX_DET_2

    FROM  ce_forma_pago_entrega fpe,
          ce_dia_remito DR,
          ce_sobre s,
--          CE_ENTIDAD_FINANCIERA_CUENTA EF,
          CE_CUADRATURA C,
          CE_CUADRATURA CP
    WHERE fpe.cod_grupo_cia = s.cod_grupo_cia
    AND   fpe.cod_local = s.cod_local
    AND   fpe.sec_mov_caja = s.sec_mov_caja
    AND   fpe.sec_forma_pago_entrega = s.sec_forma_pago_entrega
    AND   fpe.est_forma_pago_ent = 'A'
    AND   DR.fec_dia_vta = s.fec_dia_vta
    AND   DR.cod_grupo_cia = s.cod_grupo_cia
    AND   DR.cod_local = s.cod_local
    --POR TIPO DE MONEDA LA RESPECTIVA CUENTA
--    AND  EF.COD_ENTIDAD_FINANCIERA = '006'--ENTIDAD FINANCIERA PROSEGUR QUE ESTARIA MAL
--    AND  EF.TIP_MONEDA             = FPE.TIP_MONEDA

    AND  C.COD_GRUPO_CIA  = S.COD_GRUPO_CIA
    AND  C.COD_CUADRATURA = '011'
    --CUADRATURA 031
    AND  CP.COD_GRUPO_CIA = S.COD_GRUPO_CIA
    AND  CP.COD_CUADRATURA = '031'

    AND  DR.COD_GRUPO_CIA = cCodGrupoCia_in
    AND  DR.COD_LOCAL   = cCodLocal_in
    AND  DR.fec_dia_vta = vFecCierreDia_in
    -- cambio de sobres
    -- dubilluz 10.08.2010
    -- INICIO
    AND   'S' = (
                 SELECT NVL(L.IND_PROSEGUR,'N')
                 FROM   PBL_LOCAL L
                 WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND    L.COD_LOCAL     = cCodLocal_in
                 )
    -- FIN

    ;--to_date('31/08/2008','dd/mm/yyyy');


    CURSOR validaCuadratura IS
    SELECT CCD.NUM_OPERACION,
           CCD.COD_FORMA_PAGO
    FROM   CE_CUADRATURA_CIERRE_DIA CCD
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_DEPOSITO_VENTA;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.NUM_OPERACION IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el número de operación en cuadratura ' || C_C_DEPOSITO_VENTA||' - DEPOSITO POR VENTA');
           ELSIF validaCuadratura_rec.COD_FORMA_PAGO IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar la forma de pago en cuadratura ' || C_C_DEPOSITO_VENTA||' - DEPOSITO POR VENTA');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_012(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_KO CLASE_DOC,
           TO_CHAR(CCD.FEC_EMISION,'DD/MM/YYYY') FECHA_DOC,
           CASE
               WHEN CCD.FEC_OPERACION < CCD.FEC_VENCIMIENTO THEN
                    TO_CHAR(CCD.FEC_OPERACION,'DD/MM/YYYY')
               ELSE
                   TO_CHAR(CCD.FEC_VENCIMIENTO,'DD/MM/YYYY')
           END FECHA_CON,
           CCD.NUM_REFERENCIA REFERENCIA,
           ' ' TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CCD.COD_PROVEEDOR CUE_1,
           ' ' CME,
           DECODE(CCD.NOM_TITULAR_SERVICIO,NOM_TITULAR_MF,'X',' ') IMPUESTO,
           ' ' ASIG_1,
           CCD.COD_LOCAL || '-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DDMMYYYY') || '-' || SUBSTR(CCD.DESC_MOTIVO,0,36) TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CSE.DESC_CUENTA_2 CUE_2,
           CENTRO_COSTO_CC CEN_COSTO,
           DECODE(CCD.NOM_TITULAR_SERVICIO,NOM_TITULAR_MF,IND_IMPUESTO_S3,IND_IMPUESTO_S0) IND_IMP,
           CCD.COD_LOCAL || '-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU,
           CE_SERVICIO CSE
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_SERVICIOS_BASICOS
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    CCD.COD_SERVICIO = CSE.COD_SERVICIO;

    CURSOR validaCuadratura IS
    SELECT CCD.NUM_REFERENCIA
    FROM   CE_CUADRATURA_CIERRE_DIA CCD
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_SERVICIOS_BASICOS;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.NUM_REFERENCIA IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el número de referencia en cuadratura ' || C_C_SERVICIOS_BASICOS||' - SERVICIOS BASICOS');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_013(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           ' ' REFERENCIA,
           ' ' TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           CCD.COD_LOCAL || '-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DDMMYYYY') TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           CCD.COD_LOCAL || '-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DDMMYYYY') TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_REEMBOLSO_C_CH
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_014(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           ' ' REFERENCIA,
           SUBSTR('2Q-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL || '-' ||
           TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB,0,25) TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           '2Q-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL || '-' ||
           TRA.NOM_TRAB || ',' || TRA.APE_PAT_TRAB TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           '2Q-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL || '-' ||
           TRA.NOM_TRAB || ',' || TRA.APE_PAT_TRAB TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU,
           CE_MAE_TRAB TRA,
           PBL_LOCAL LOC
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_PAGO_PLANILLA
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    CCD.COD_CIA = TRA.COD_CIA
    AND    CCD.COD_TRAB = TRA.COD_TRAB
    AND    CCD.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
    AND    CCD.COD_LOCAL = LOC.COD_LOCAL;

    CURSOR validaCuadratura IS
    SELECT CCD.COD_CIA,
           CCD.COD_TRAB
    FROM   CE_CUADRATURA_CIERRE_DIA CCD
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_PAGO_PLANILLA;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.COD_CIA IS NULL OR validaCuadratura_rec.COD_TRAB IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_PAGO_PLANILLA||' - PAGO DE PLANILLA');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/

/*  PROCEDURE INT_EJECUTA_CUADRATURA_016(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           SUBSTR(C_C_ERC || '-' || CCD.DESC_MOTIVO || '-' ||C_C_MF , 0,16)  REFERENCIA,
           SUBSTR(C_C_ERC || '-' || TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB || '-' || C_C_MF,0,25) TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           NVL(TRA.COD_SAP,' ') CUE_1,
           'V' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           C_C_ERC || '-' || TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB || '-' || C_C_MF TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_ERC || '-' || TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB || '-' || C_C_MF TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU,
           CE_MAE_TRAB TRA
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_ENTREGAS_RENDIR
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    CCD.COD_CIA = TRA.COD_CIA
    AND    CCD.COD_TRAB = TRA.COD_TRAB;

    CURSOR validaCuadratura IS
    SELECT CCD.COD_CIA,
           CCD.COD_TRAB
    FROM   CE_CUADRATURA_CIERRE_DIA CCD
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_ENTREGAS_RENDIR;

    CURSOR validaCuadratura2 IS
    SELECT TRIM(TRA.COD_SAP) COD_SAP
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_MAE_TRAB TRA
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_ENTREGAS_RENDIR
    AND    CCD.COD_CIA = TRA.COD_CIA
    AND    CCD.COD_TRAB = TRA.COD_TRAB;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.COD_CIA IS NULL OR validaCuadratura_rec.COD_TRAB IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_ENTREGAS_RENDIR||' - ENTREGAS A RENDIR');
           END IF;
       END LOOP;

       FOR validaCuadratura2_rec IN validaCuadratura2
       LOOP
           IF validaCuadratura2_rec.COD_SAP IS NULL THEN
              RAISE_APPLICATION_ERROR(-20051,'No se puede determinar el codigo de trabajador SAP en cuadratura ' || C_C_ENTREGAS_RENDIR||' - ENTREGAS A RENDIR');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;
*/

  PROCEDURE INT_EJECUTA_CUADRATURA_016(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           SUBSTR(C_C_ERC || '-' || CCD.DESC_MOTIVO || '-' ||C_C_MF , 0,16)  REFERENCIA,
           SUBSTR(C_C_ERC || '-' || TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB || '-' || C_C_MF,0,25) TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           NVL(TRA.COD_SAP,' ') CUE_1,
           'V' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           C_C_ERC || '-' || TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB || '-' || C_C_MF TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_ERC || '-' || TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB || '-' || C_C_MF TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU,
           CE_MAE_TRAB TRA
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_ENTREGAS_RENDIR
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    CCD.COD_CIA = TRA.COD_CIA
    AND    CCD.COD_TRAB = TRA.COD_TRAB;

    CURSOR validaCuadratura IS
    SELECT CCD.COD_CIA,
           CCD.COD_TRAB
    FROM   CE_CUADRATURA_CIERRE_DIA CCD
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_ENTREGAS_RENDIR;

    CURSOR validaCuadratura2 IS
    SELECT TRIM(TRA.COD_SAP) COD_SAP,
           -------------------------------------
           --28/08/2007  DUBILLUZ  MODIFICACION
           TRA.NUM_DOC_IDEN  DNI,
           NVL(TRA.NOM_TRAB,' ') || ' '||NVL(TRA.APE_PAT_TRAB,' ') || '  ' || NVL(TRA.APE_MAT_TRAB,' ') DATOS
           -------------------------------------
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_MAE_TRAB TRA
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_ENTREGAS_RENDIR
    AND    CCD.COD_CIA = TRA.COD_CIA
    AND    CCD.COD_TRAB = TRA.COD_TRAB;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.COD_CIA IS NULL OR validaCuadratura_rec.COD_TRAB IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_ENTREGAS_RENDIR||' - ENTREGAS A RENDIR');
           END IF;
       END LOOP;

       FOR validaCuadratura2_rec IN validaCuadratura2
       LOOP
           IF validaCuadratura2_rec.COD_SAP IS NULL THEN
              RAISE_APPLICATION_ERROR(-20051,'No se puede determinar el codigo de trabajador SAP en cuadratura ' || C_C_ENTREGAS_RENDIR||' - ENTREGAS A RENDIR' ||
              '  NOMBRE Y APELLIDO : ' ||  validaCuadratura2_rec.Datos  ||
              '  DNI : ' ||  validaCuadratura2_rec.Dni );
              --RAISE_APPLICATION_ERROR(-20051,'No se puede determinar el codigo de trabajador SAP en cuadratura ' || C_C_ENTREGAS_RENDIR||' - ENTREGAS A RENDIR');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_017(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           ' ' REFERENCIA,
           ' ' TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_ROBO_ASALTO
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_018(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           ' ' REFERENCIA,
           SUBSTR(TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB || '/' || CU.DESC_CORTA_CUADRATURA,0,25) TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_PARCIAL,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
-- 2010-01-20: JOLIVA  Se mostrará el DNI en lugar del código del trabajador
--           NVL(TRAB.COD_TRAB_RRHH,' ') ASIG_1,
           ---jquispe 11.04.2011 cambio interfaz para conv mifarma
           NVL(LPAD(TRA.NUM_DOC_IDEN,10,'0'),' ') ASIG_1,
           TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           'RH-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU,
           CE_MAE_TRAB TRA,
           PBL_LOCAL LOC
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_DINERO_FALSO
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    CCD.COD_CIA = TRA.COD_CIA
    AND    CCD.COD_TRAB = TRA.COD_TRAB
    AND    CCD.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
    AND    CCD.COD_LOCAL = LOC.COD_LOCAL;

    CURSOR validaCuadratura IS
    SELECT CCD.COD_CIA,
           CCD.COD_TRAB
    FROM   CE_CUADRATURA_CIERRE_DIA CCD
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_DINERO_FALSO;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.COD_CIA IS NULL OR validaCuadratura_rec.COD_TRAB IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_DINERO_FALSO||' - DINERO FALSO');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_019(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           DSU.CLASE_DOC CLASE_DOC,
           TO_CHAR(CCD.FEC_EMISION,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_OPERACION,'DD/MM/YYYY') FECHA_CON,
           DSU.COD_SUNAT || '-' || CCD.NUM_REFERENCIA REFERENCIA,
           ' ' TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           NVL(MP.COD_SAP,' ') CUE_1,
           ' ' CME,
           DECODE(DSU.IND_IMPUESTO,IND_IMPUESTO_S3,'X',' ') IMPUESTO,
           ' ' ASIG_1,
           CCD.COD_LOCAL || '-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DDMMYYYY') || '-' || SUBSTR(CCD.DESC_MOTIVO,0,36) TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           CENTRO_COSTO_CC CEN_COSTO,
           DSU.IND_IMPUESTO IND_IMP,
           CCD.COD_LOCAL || '-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DDMMYYYY') || '/' || CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU,
           PBL_DOCUMENTO_SUNAT DSU,
           CE_MAE_PROV MP
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_OTROS_DESEMBOLSOS
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    CCD.TIP_DOCUMENTO = DSU.TIP_COMPROBANTE_MF
    AND    CCD.DESC_RUC = MP.DESC_RUC(+);

    CURSOR validaCuadratura IS
    SELECT CCD.NUM_REFERENCIA
    FROM   CE_CUADRATURA_CIERRE_DIA CCD
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_OTROS_DESEMBOLSOS;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.NUM_REFERENCIA IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el numero de referencia en cuadratura ' || C_C_OTROS_DESEMBOLSOS||' - OTROS DESEMBOLSOS');
           END IF;
       END LOOP;


       --VALIDANDO LA CUADRATURA 019
       FOR movCuadratura_rec IN movCuadratura
       LOOP
           IF TRIM(movCuadratura_rec.CUE_1) IS NULL THEN
              --DBMS_OUTPUT.PUT_LINE('NO PASÓ LA VALIDACION DE LA CUADRATURA 019');
              --v_cIndResult := INDICADOR_ERROR;
              --RETURN v_cIndResult;
              RAISE_APPLICATION_ERROR(-20051,'No se puede determinar el codigo SAP del acreedor en cuadratura ' || C_C_OTROS_DESEMBOLSOS||' - OTROS DESEMBOLSOS. POR FAVOR VERIFIQUE EL CIERRE DE DIA Y ACTUALICE EL MAESTRO DE PROVEEDORES EN EL SISTEMA DE ADM CENTRAL PARA EL PROXIMO PROCESO.');
           END IF;
       END LOOP;


       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_021(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           ' ' REFERENCIA,
           CU.DESC_CORTA_CUADRATURA TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_PARCIAL,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
-- 2010-01-20: JOLIVA  Se mostrará el DNI en lugar del código del trabajador
--           NVL(TRA.COD_TRAB_RRHH,' ') ASIG_1,
           ---jquispe 11.04.2011 cambio interfaz para conv mifarma
           NVL(LPAD(TRA.NUM_DOC_IDEN,10,'0'), ' ') ASIG_1,
           substr(TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB || '/' || LOC.DESC_CORTA_LOCAL,0,50) TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           'RH-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU,
           CE_MAE_TRAB TRA,
           PBL_LOCAL LOC
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_DSCT_PERSONAL
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    CCD.COD_CIA = TRA.COD_CIA
    AND    CCD.COD_TRAB = TRA.COD_TRAB
    AND    CCD.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
    AND    CCD.COD_LOCAL = LOC.COD_LOCAL;

    CURSOR validaCuadratura IS
    SELECT CCD.COD_CIA,
           CCD.COD_TRAB
    FROM   CE_CUADRATURA_CIERRE_DIA CCD
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_DSCT_PERSONAL;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.COD_CIA IS NULL OR validaCuadratura_rec.COD_TRAB IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_DSCT_PERSONAL||' - DESCUENTO A PERSONAL');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_022(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           ' ' REFERENCIA,
           CU.DESC_CORTA_CUADRATURA TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
-- 2010-01-20: JOLIVA  Se mostrará el DNI en lugar del código del trabajador
--           NVL(TRAB.COD_TRAB_RRHH,' ') ASIG_1,
           NVL(LPAD(TRAB.NUM_DOC_IDEN,10,'0'),' ') ASIG_1,
           SUBSTR('RH-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL || '-' ||
           TRAB.APE_PAT_TRAB || ',' || TRAB.NOM_TRAB,0,50) TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           'RH-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL || '/' ||
           CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU,
           PBL_LOCAL LOC,
           CE_MAE_TRAB TRAB,
           CE_CIERRE_DIA_VENTA CDV,
           PBL_USU_LOCAL USU
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_DEFICIT_ASUMIDO_QF
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    CCD.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
    AND    CCD.COD_LOCAL = LOC.COD_LOCAL
    AND    CCD.COD_GRUPO_CIA = CDV.COD_GRUPO_CIA
    AND    CCD.COD_LOCAL = CDV.COD_LOCAL
    AND    CCD.FEC_CIERRE_DIA_VTA = CDV.FEC_CIERRE_DIA_VTA
    AND    CDV.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
    AND    CDV.COD_LOCAL = USU.COD_LOCAL
    AND    CDV.SEC_USU_LOCAL_VB = USU.SEC_USU_LOCAL
    AND    USU.COD_CIA = TRAB.COD_CIA
    AND    USU.COD_TRAB = TRAB.COD_TRAB;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_023(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           ' ' REFERENCIA,
           CU.DESC_CORTA_CUADRATURA TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_PERDIDO,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           C_C_RT || '-' || CCD.COD_LOCAL || '-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DDMMYYYY') || '/' ||
           CU.DESC_CORTA_CUADRATURA TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || CCD.COD_LOCAL || '-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DDMMYYYY') || '/' ||
           CU.DESC_CORTA_CUADRATURA TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU,
           PBL_LOCAL LOC
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_DELIVERY_PERDIDO
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    CCD.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
    AND    CCD.COD_LOCAL = LOC.COD_LOCAL;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_024(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           ' ' REFERENCIA,
           SUBSTR('1Q-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL || '-' ||
           TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB,0,25) TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           SUBSTR('1Q-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL || '-' ||
           TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB,0,25) TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           SUBSTR('1Q-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL || '-' ||
           TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB,0,25) TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU,
           CE_MAE_TRAB TRA,
           PBL_LOCAL LOC
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_ADELANTO
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    CCD.COD_CIA = TRA.COD_CIA
    AND    CCD.COD_TRAB = TRA.COD_TRAB
    AND    CCD.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
    AND    CCD.COD_LOCAL = LOC.COD_LOCAL;

    CURSOR validaCuadratura IS
    SELECT CCD.COD_CIA,
           CCD.COD_TRAB
    FROM   CE_CUADRATURA_CIERRE_DIA CCD
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_ADELANTO;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.COD_CIA IS NULL OR validaCuadratura_rec.COD_TRAB IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_ADELANTO||' - ADELANTO');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/
  PROCEDURE INT_EJECUTA_CUADRATURA_025(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT CCD.COD_GRUPO_CIA COD_GRUPO_CIA,
           CCD.COD_LOCAL COD_LOCAL,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
           CCD.COD_CUADRATURA TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           ' ' REFERENCIA,
           SUBSTR('GRATIF-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL || '-' ||
           TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB,0,25) TEX_CAB,
           DECODE(CCD.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CCD.MON_MONEDA_ORIGEN,'999999990.00') IMPORTE,
           CU.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CU.DESC_CUENTA_1 CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           SUBSTR('GRATIF-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL || '-' ||
           TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB,0,25) TEX_DET_1,
           CU.DESC_CLAVE_CUENTA_2 CLA_CUE_2,
           CU.DESC_CUENTA_2 CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           SUBSTR('GRATIF-' || TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'MON') || '-' || LOC.DESC_CORTA_LOCAL || '-' ||
           TRA.APE_PAT_TRAB || ',' || TRA.NOM_TRAB,0,25) TEX_DET_2
    FROM   CE_CUADRATURA_CIERRE_DIA CCD,
           CE_CUADRATURA CU,
           CE_MAE_TRAB TRA,
           PBL_LOCAL LOC
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_GRATIFICACION
    AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
    AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
    AND    CCD.COD_CIA = TRA.COD_CIA
    AND    CCD.COD_TRAB = TRA.COD_TRAB
    AND    CCD.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
    AND    CCD.COD_LOCAL = LOC.COD_LOCAL;

    CURSOR validaCuadratura IS
    SELECT CCD.COD_CIA,
           CCD.COD_TRAB
    FROM   CE_CUADRATURA_CIERRE_DIA CCD
    WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
    AND    CCD.COD_LOCAL = cCodLocal_in
    AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
    AND    CCD.COD_CUADRATURA = C_C_GRATIFICACION;
  BEGIN
       FOR validaCuadratura_rec IN validaCuadratura
       LOOP
           IF validaCuadratura_rec.COD_CIA IS NULL OR validaCuadratura_rec.COD_TRAB IS NULL THEN
              RAISE_APPLICATION_ERROR(-20050,'No se puede determinar el codigo de trabajador en cuadratura ' || C_C_GRATIFICACION||' - PAGO GRATIFICACION');
           END IF;
       END LOOP;

       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

  /****************************************************************************/


  PROCEDURE INT_GRABA_INTERFACE_CE(cCodGrupoCia_in    IN CHAR,
                                   cCodLocal_in       IN CHAR,
                                   vFecOperacion_in   IN CHAR,
                                   cCodCuadratura_in  IN CHAR,
                                   cSecIntCe_in       IN CHAR,
                                   vClaseDoc_in       IN CHAR,
                                   cFecDocumento_in   IN CHAR,
                                   cFecContable_in    IN CHAR,
                                   vDescReferencia_in IN CHAR,
                                   cDescTextCab_in    IN CHAR,
                                   cTipMoneda_in      IN CHAR,
                                   vClaveCue1_in      IN CHAR,
                                   vCuenta1_in        IN CHAR,
                                   cMarcaImp_in       IN CHAR,
                                   cValImporte_in     IN CHAR,
                                   vDescAsig1_in      IN CHAR,
                                   cDescTextDet1_in   IN CHAR,
                                   cClaveCue2_in      IN CHAR,
                                   vCuenta2_in        IN CHAR,
                                   vCentroCosto_in    IN CHAR,
                                   cIndImp_in         IN CHAR,
                                   cDescTextDet2_in   IN CHAR,
                                   cUsuCreaIntCe_in   IN CHAR,
                                   CME_in             IN CHAR)
    IS
  v_Desc_Texto_Cabecera Int_Caja_Electronica.Desc_Texto_Cab%TYPE ;
  v_Desc_Texto_Detalle1 Int_Caja_Electronica.Desc_Texto_Detalle_1%TYPE ;
  v_Desc_Texto_Detalle2 Int_Caja_Electronica.Desc_Texto_Detalle_2%TYPE ;

  BEGIN

  v_Desc_Texto_Cabecera := REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(cDescTextCab_in,'Ñ','N'),'ñ','n'),'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ü','U'),'ü','u');
  v_Desc_Texto_Detalle1 := substr(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(cDescTextDet1_in,'Ñ','N'),'ñ','n'),'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ü','U'),'ü','u'),1,50);
  v_Desc_Texto_Detalle2 := substr(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(cDescTextDet2_in,'Ñ','N'),'ñ','n'),'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ü','U'),'ü','u'),1,50);

      INSERT INTO INT_CAJA_ELECTRONICA(COD_GRUPO_CIA,
                                       COD_LOCAL,
                                       FEC_OPERACION,
                                       COD_CUADRATURA,
                                       SEC_INT_CE,
                                       CLASE_DOC,
                                       FEC_DOCUMENTO,
                                       FEC_CONTABLE,
                                       DESC_REFERENCIA,
                                       DESC_TEXTO_CAB,
                                       TIP_MONEDA,
                                       DESC_CLAVE_CUENTA_1,
                                       DESC_CUENTA_1,
                                       MARCA_IMPUESTO,
                                       VAL_IMPORTE,
                                       DESC_ASIGNACION_1,
                                       DESC_TEXTO_DETALLE_1,
                                       DESC_CLAVE_CUENTA_2,
                                       DESC_CUENTA_2,
                                       DESC_CENTRO_COSTO,
                                       IND_IMPUESTO,
                                       DESC_TEXTO_DETALLE_2,
                                       USU_CREA_INT_CE,
                                       CME)
                                VALUES(cCodGrupoCia_in,
                                       cCodLocal_in,
                                       TO_DATE(vFecOperacion_in,'dd/MM/yyyy'),
                                       cCodCuadratura_in,
                                       cSecIntCe_in,
                                       vClaseDoc_in,
                                       TO_DATE(cFecDocumento_in,'dd/MM/yyyy'),
                                       TO_DATE(cFecContable_in,'dd/MM/yyyy'),
                                       vDescReferencia_in,
                                       v_Desc_Texto_Cabecera,
                                       cTipMoneda_in,
                                       vClaveCue1_in,
                                       vCuenta1_in,
                                       cMarcaImp_in,
                                       cValImporte_in,
                                       vDescAsig1_in,
                                       v_Desc_Texto_Detalle1,
                                       cClaveCue2_in,
                                       vCuenta2_in,
                                       vCentroCosto_in,
                                       cIndImp_in,
                                       v_Desc_Texto_Detalle2,
                                       cUsuCreaIntCe_in,
                                       CME_in);

  END;

  /****************************************************************************/

  FUNCTION CE_GET_SECUENCIAL_INT(cCodGrupoCia_in      IN CHAR,
                                 cCodLocal_in         IN CHAR,
                                 vFecCierreDia_in     IN CHAR)
    RETURN CHAR
  IS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;
  BEGIN
       SELECT FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(COUNT(ICE.SEC_INT_CE) + 1, 3, '0', POS_INICIO)
       INTO   v_cSecIntCe
       FROM   INT_CAJA_ELECTRONICA ICE
       WHERE  ICE.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    ICE.COD_LOCAL = cCodLocal_in
       AND    ICE.FEC_OPERACION = TO_DATE(vFecCierreDia_in,'dd/MM/yyyy');
    RETURN v_cSecIntCe;
  END;

  /****************************************************************************/

  FUNCTION CE_VALIDA_DATA_INTERFACE(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    vFecCierreDia_in IN CHAR)
    RETURN CHAR
  IS
    v_cIndResult    CHAR(1);
    v_nCanFechaArchivo NUMBER;
    CURSOR curValida019 IS
    SELECT ICE.DESC_CUENTA_1,
           ICE.DESC_CUENTA_2
    FROM   INT_CAJA_ELECTRONICA ICE
    WHERE  ICE.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    ICE.COD_LOCAL = cCodLocal_in
    AND    ICE.FEC_OPERACION = vFecCierreDia_in
    AND    ICE.COD_CUADRATURA = C_C_OTROS_DESEMBOLSOS;

    CURSOR curValida026 IS
    SELECT ICE.DESC_CUENTA_1,
           ICE.DESC_CUENTA_2
    FROM   INT_CAJA_ELECTRONICA ICE
    WHERE  ICE.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    ICE.COD_LOCAL = cCodLocal_in
    AND    ICE.FEC_OPERACION = vFecCierreDia_in
    AND    ICE.COD_CUADRATURA = C_C_CREDITO_GRAL;
  BEGIN
       v_cIndResult := INDICADOR_CORRECTO;--indica que no hay error

       --VALIDANDO QUE NO EXISTA ARCHIVO CREADO PARA LA FECHA DADA
       SELECT COUNT(*)
       INTO   v_nCanFechaArchivo
       FROM   CE_CIERRE_DIA_VENTA CCD
       WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CCD.COD_LOCAL = cCodLocal_in
       AND    CCD.FEC_CIERRE_DIA_VTA = vFecCierreDia_in
       AND    CCD.FEC_ARCHIVO IS NOT NULL;

       IF v_nCanFechaArchivo > 0 THEN
          DBMS_OUTPUT.PUT_LINE('NO PASÓ LA VALIDACION DE LA FECHA DE ARCHIVO');
          v_cIndResult := INDICADOR_ERROR;
          RETURN v_cIndResult;
       END IF;

       --VALIDANDO LA CUADRATURA 019
       FOR v_rCurValida019 IN curValida019
       LOOP
           IF TRIM(v_rCurValida019.DESC_CUENTA_1) IS NULL OR TRIM(v_rCurValida019.DESC_CUENTA_2) IS NULL THEN
              DBMS_OUTPUT.PUT_LINE('NO PASÓ LA VALIDACION DE LA CUADRATURA 019');
              v_cIndResult := INDICADOR_ERROR;
              RETURN v_cIndResult;
           END IF;
       END LOOP;

       --VALIDANDO LA CUADRATURA 026
       FOR v_rCurValida026 IN curValida026
       LOOP
           IF TRIM(v_rCurValida026.DESC_CUENTA_1) IS NULL OR  TRIM(v_rCurValida026.DESC_CUENTA_1) ='SIN_CCLIE'   THEN
              DBMS_OUTPUT.PUT_LINE('NO PASÓ LA VALIDACION DE LA CUADRATURA 026');
              v_cIndResult := INDICADOR_ERROR;
              RETURN v_cIndResult;
           END IF;
       END LOOP;

    RETURN v_cIndResult;
  END;

  /****************************************************************************/

  PROCEDURE CE_ACTUALIZA_FECHA_PROCESO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR,
                                       vFecProceso_in   IN DATE)
  IS
  BEGIN
       UPDATE CE_CIERRE_DIA_VENTA
       SET    USU_MOD_CIERRE_DIA = C_C_USU_CREA_INT_CE, FEC_MOD_CIERRE_DIA = SYSDATE,
              FEC_PROCESO = vFecProceso_in
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    FEC_CIERRE_DIA_VTA = vFecCierreDia_in;
  END;

  /****************************************************************************/

  PROCEDURE CE_ACTUALIZA_FECHA_ARCHIVO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR,
                                       vFecArchivo_in   IN DATE)
  IS
  BEGIN
       UPDATE CE_CIERRE_DIA_VENTA
       SET    USU_MOD_CIERRE_DIA = C_C_USU_CREA_INT_CE, FEC_MOD_CIERRE_DIA = SYSDATE,
              FEC_ARCHIVO = vFecArchivo_in
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    FEC_CIERRE_DIA_VTA = vFecCierreDia_in;
  END;

  /****************************************************************************/

  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        vEnviarOper_in   IN CHAR DEFAULT 'N')
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_INTER_CE;
    CCReceiverAddress VARCHAR2(120) := NULL;
    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN
     --SE VALIDARA A DONDE ENVIARA SI EXISTA UN ERROR EN LOS MONTOS
     IF vEnviarOper_in = INDICADOR_SI THEN
        ReceiverAddress:= '';
        ReceiverAddress := FARMA_EMAIL.GET_RECEIVER_ADDRESS_VL_INT_CE;
     END IF;

    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,--'VIAJERO EXITOSO: '||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             CCReceiverAddress,
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;

  /****************************************************************************/

  PROCEDURE INT_REVERTIR_CUADRATURAS_CE(cCodGrupoCia_in  IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        cFecCierreDia_in IN CHAR,
                                        cIdUsu_in        IN CHAR)
  AS
  BEGIN
    --BORRA LAS CUADRATURAS EN LA INTERFAZ
    DELETE INT_CAJA_ELECTRONICA
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    FEC_OPERACION = cFecCierreDia_in;

    --REVIERTE EL PROCESO DE LA INTERFAZ
    UPDATE CE_CIERRE_DIA_VENTA
    SET    USU_MOD_CIERRE_DIA = cIdUsu_in, FEC_MOD_CIERRE_DIA = SYSDATE,
           FEC_PROCESO = NULL,
           FEC_ARCHIVO = NULL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    FEC_CIERRE_DIA_VTA = cFecCierreDia_in;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('EXITO AL REVERTIR INT CE. LOCAL:'||cCodLocal_in||' FECHA: '||cFecCierreDia_in);
    INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                'EXITO AL REVERTIR DATA INTERFACE CAJA ELECTRONICA: ',
                                'EXITO',
                                'EXITO AL REVERTIR INT CE. LOCAL:'||cCodLocal_in||' FECHA: '||cFecCierreDia_in||'</B>'||
                                '<BR> <I>EXITO:</I> <BR>'||'EXITO AL REVERTIR DATA DE CAJA ELECTRONICA.'||'<B>');
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('ERROR AL REVERTIR INT CE. '||SQLERRM);
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                  'ERROR AL REVERTIR DATA INTERFACE CAJA ELECTRONICA: ',
                                  'ALERTA',
                                  'ERROR AL REVERTIR LA DATA DE CAJA ELECTRONICA PARA LA FECHA: '||cFecCierreDia_in||'</B>'||
                                  '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR AL REVERTIR INT CE.'|| SQLERRM ||'<B>');
  END;

/***************************************************************************************/

  PROCEDURE INT_SOLICITUD_COD_SAP (cCodGrupoCia    CHAR)
  AS
  CURSOR cur_mail IS
          SELECT    LOC.COD_LOCAL,
                    LOC.DESC_CORTA_LOCAL,
                    CCD.FEC_CIERRE_DIA_VTA,
                    CCD.DESC_RUC,
                    CCD.DESC_RAZON_SOCIAL
          FROM      CE_CIERRE_DIA_VENTA CDV,
                    PBL_LOCAL LOC,
                    CE_CUADRATURA_CIERRE_DIA CCD,
                    (SELECT DISTINCT ICE.COD_GRUPO_CIA,
                            ICE.COD_LOCAL,
                            ICE.FEC_OPERACION
                     FROM   INT_CAJA_ELECTRONICA ICE
                     WHERE  ICE.COD_GRUPO_CIA  = cCodGrupoCia
                     AND    ICE.COD_CUADRATURA = C_C_OTROS_DESEMBOLSOS
                     AND    TRIM(ICE.DESC_CUENTA_1) IS NULL) V1
          WHERE     CCD.COD_GRUPO_CIA         = cCodGrupoCia
          AND       CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
          AND       CCD.COD_CUADRATURA        = C_C_OTROS_DESEMBOLSOS
          AND       CDV.IND_VB_CIERRE_DIA     = INDICADOR_SI
          AND       CDV.COD_GRUPO_CIA         = LOC.COD_GRUPO_CIA
          AND       CDV.COD_LOCAL             = LOC.COD_LOCAL
          AND       CDV.COD_GRUPO_CIA         = CCD.COD_GRUPO_CIA
          AND       CDV.COD_LOCAL             = CCD.COD_LOCAL
          AND       CCD.FEC_CIERRE_DIA_VTA    = CDV.FEC_CIERRE_DIA_VTA
          AND       CDV.COD_GRUPO_CIA         = V1.COD_GRUPO_CIA
          AND       CDV.COD_LOCAL             = V1.COD_LOCAL
          AND       CDV.FEC_CIERRE_DIA_VTA    = V1.FEC_OPERACION
          ORDER BY  LOC.COD_LOCAL,
                    CCD.FEC_CIERRE_DIA_VTA;

    V_COD_LOCAL   CHAR(3);
    V_DESC_LOCAL  CHAR(30);
    V_FEC_CIERRE  CHAR(10);
    V_RUC         CHAR(15);
    V_RAZON_SOC   CHAR(30);
    V_MENSAJE     VARCHAR2(32767) := '<html>'||
                                      '<style>'||
                                      '.texto{'||
                                      	'font-family:Verdana, Arial, Helvetica, sans-serif;'||
                                      	'font-size:12px;'||
                                      	'color:#000000;'||
                                      '}'||
                                      '</style><br>'||
                                      '<table width="635" height="57" border="1" cellspacing="0" class="texto">'||
                                      '<tr bgcolor="#FF9900">'||
                                        '<td width="85" height="28"><div align="center"><b>FECHA</b></div></td>'||
                                        '<td width="220"><div align="center"><b>LOCAL</b></div></td>'||
                                        '<td width="121"><div align="center"><b>RUC</b></div></td>'||
                                        '<td width="201"><div align="center"><b>RAZON SOCIAL</b></div></td>'||
                                      '</tr>';
    BEGIN

      OPEN cur_mail ;
      LOOP
        FETCH cur_mail INTO V_COD_LOCAL, V_DESC_LOCAL, V_FEC_CIERRE, V_RUC, V_RAZON_SOC;
        EXIT WHEN cur_mail%NOTFOUND;
              V_MENSAJE:= V_MENSAJE||
                          '<tr>'||
                            '<td><div align="center">'|| V_FEC_CIERRE ||'</div></td>'||
                            '<td><div align="center">'|| V_COD_LOCAL ||' - '|| V_DESC_LOCAL ||'</div></td>'||
                            '<td><div align="center">'|| V_RUC ||'</div></td>'||
                            '<td><div align="center">'|| V_RAZON_SOC ||'</div></td>'||
                          '</tr>';
      END LOOP ;
      V_MENSAJE:=V_MENSAJE||'</table></html>';

      IF cur_mail%ROWCOUNT > 0 THEN
        FARMA_EMAIL.envia_correo('Matriz - Interface de CE '||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                       FARMA_EMAIL.GET_RECEIVER_ADDRESS_INTER_CE,--'erosillo, pameghino, inunez, jhuaranga, mpinedo, cmaldonado', --Destinatario: erosillo
                       'Solicitud de Código SAP',
                       'ATENCIÓN',
                       'Srta. Esther Rosillo Pinillos, se le solicita los Códigos de Proveedor SAP para los siguientes proveedores:<BR>'||
                       V_MENSAJE||'<br>En caso existan, por favor, haga caso omiso de este mail.',
                       'operador',--OPERADOR: operador
                       FARMA_EMAIL.GET_EMAIL_SERVER,
                       true);
      END IF;
      CLOSE cur_mail ;
    END;

/***************************************************************************************/
  PROCEDURE INT_VALIDA_MONTO_INTERFACE(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cFecCierreDia_in  IN CHAR,
                                       cNombreArchivo_in IN CHAR)
  IS
   v_resultado CHAR(1);
   v_suma_interface NUMBER := 0;
   v_suma_calculada NUMBER := 0;
   v_ind_dar_commit CHAR(1);

   vAsunto_in  VARCHAR2(3000);
   vTitulo_in  VARCHAR2(1000);
   vMensaje_in VARCHAR2(3000);
   vComentario VARCHAR2(1000);

   mesg_body       VARCHAR2(32767);
   v_vDescLocal    VARCHAR2(120);
   ReceiverAddress VARCHAR2(30);
  BEGIN
    BEGIN
      ---CALCULANDO EL MONTO QUE SUMA EL DIA EN EL LOCAL
      SELECT ROUND(SUM(RES.IMPORTE),1) IMPORTE_TOTAL
      INTO   v_suma_calculada
      FROM
      (SELECT CC.COD_GRUPO_CIA CIA,CC.COD_LOCAL LOCAL,
              TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
              CC.COD_CUADRATURA ,sum(cc.MON_MONEDA_ORIGEN) IMPORTE
      FROM    CE_CUADRATURA_CAJA CC,
              CE_MOV_CAJA MOV
      WHERE   MOV.COD_GRUPO_CIA = cCodGrupoCia_in
      AND     CC.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
      AND     MOV.COD_LOCAL     = cCodLocal_in
      AND     MOV.FEC_DIA_VTA   = cFecCierreDia_in
      AND     MOV.COD_GRUPO_CIA = CC.COD_GRUPO_CIA
      AND     MOV.COD_LOCAL = CC.COD_LOCAL
      AND     MOV.SEC_MOV_CAJA = CC.SEC_MOV_CAJA
      group by CC.COD_GRUPO_CIA,CC.COD_LOCAL,CC.COD_CUADRATURA,TO_CHAR(MOV.FEC_DIA_VTA,'DD/MM/YYYY')
      UNION
      SELECT CCD.COD_GRUPO_CIA CIA,CCD.COD_LOCAL LOCAL,
             TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
             CCD.COD_CUADRATURA,sum(ccd.MON_MONEDA_ORIGEN) IMPORTE
      FROM   CE_CIERRE_DIA_VENTA CDV,
             CE_CUADRATURA_CIERRE_DIA CCD
      WHERE  CDV.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
      AND    CDV.COD_LOCAL = cCodLocal_in
      AND    CDV.FEC_CIERRE_DIA_VTA= cFecCierreDia_in
      AND    CCD.COD_CUADRATURA NOT IN (C_C_COTIZA_COMP, C_C_FONDO_SENCILLO,C_C_SERVICIOS_BASICOS,
                                        C_C_REEMBOLSO_C_CH,C_C_OTROS_DESEMBOLSOS,C_C_DEPOSITO_VENTA)
      AND    CDV.COD_GRUPO_CIA = CCD.COD_GRUPO_CIA
      AND    CDV.COD_LOCAL = CCD.COD_LOCAL
      AND    CDV.FEC_CIERRE_DIA_VTA= CCD.FEC_CIERRE_DIA_VTA
      group by CCD.COD_CUADRATURA,CCD.COD_GRUPO_CIA,CCD.COD_LOCAL,TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY')
      UNION
      SELECT CCD.COD_GRUPO_CIA ,
             CCD.COD_LOCAL LOCAL,
             TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY') FECHA,
             '011', sum(ccd.MON_MONEDA_ORIGEN)IMPORTE
      FROM   CE_CUADRATURA_CIERRE_DIA CCD,
             CE_CUADRATURA CU,
             VTA_FORMA_PAGO FP
      WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    CCD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
      AND    CCD.COD_LOCAL = cCodLocal_in
      AND    CCD.FEC_CIERRE_DIA_VTA = cFecCierreDia_in
      AND    CCD.COD_CUADRATURA = C_C_DEPOSITO_VENTA
      AND    FP.IND_TARJ = INDICADOR_NO
      AND    CCD.COD_GRUPO_CIA = CU.COD_GRUPO_CIA
      AND    CCD.COD_CUADRATURA= CU.COD_CUADRATURA
      AND    CCD.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
      AND    CCD.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
      group by CCD.COD_GRUPO_CIA,CCD.COD_LOCAL,TO_CHAR(CCD.FEC_CIERRE_DIA_VTA,'DD/MM/YYYY')
      UNION
      SELECT F.COD_GRUPO_CIA CIA,F.COD_LOCAL LOCAL,
             TO_CHAR(C.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
             '000' TIPO_OPERACION, SUM(F.MON_ENTREGA) IMPORTE
      FROM   CE_FORMA_PAGO_ENTREGA F,
             VTA_FORMA_PAGO P,
             CE_MOV_CAJA C,
             (SELECT CD.NUM_OPERACION OPERACION,
                     CD.FEC_OPERACION FECHA,
                     CD.COD_LOCAL LOCAL,
                     CD.COD_GRUPO_CIA CIA,
                     CD.FEC_CIERRE_DIA_VTA,
                     CD.COD_FORMA_PAGO
              FROM   CE_CUADRATURA_CIERRE_DIA CD,
                     VTA_FORMA_PAGO_LOCAL F,
                     VTA_FORMA_PAGO FP,
                     PBL_TIP_DEPOSITO TD
              WHERE  CD.COD_GRUPO_CIA = cCodGrupoCia_in
              AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
              AND    CD.COD_LOCAL     = cCodLocal_in
              AND    CD.COD_CUADRATURA     = C_C_DEPOSITO_VENTA
              AND    CD.FEC_CIERRE_DIA_VTA = cFecCierreDia_in
              AND    FP.IND_TARJ = INDICADOR_SI
              AND    CD.COD_GRUPO_CIA    = F.COD_GRUPO_CIA
              AND    CD.COD_LOCAL        = F.COD_LOCAL
              AND    CD.COD_FORMA_PAGO   = F.COD_FORMA_PAGO
              AND    FP.COD_GRUPO_CIA    = F.COD_GRUPO_CIA
              AND    FP.COD_FORMA_PAGO   = F.COD_FORMA_PAGO
              AND    FP.COD_TIP_DEPOSITO = TD.COD_TIP_DEPOSITO)V1
      WHERE  F.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    F.COD_LOCAL = cCodLocal_in
      AND    F.EST_FORMA_PAGO_ENT = 'A'
      AND    P.IND_TARJ  =INDICADOR_SI
      AND    F.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                FROM   CE_MOV_CAJA A
                                WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND    A.COD_LOCAL = cCodLocal_in
                                AND    A.FEC_DIA_VTA = cFecCierreDia_in
                                AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
      AND    F.COD_GRUPO_CIA  = P.COD_GRUPO_CIA
      AND    F.COD_LOCAL      = C.COD_LOCAL
      AND    F.SEC_MOV_CAJA   = C.SEC_MOV_CAJA
      AND    F.COD_GRUPO_CIA  = P.COD_GRUPO_CIA
      AND    F.COD_FORMA_PAGO = P.COD_FORMA_PAGO
      AND    V1.CIA(+)   = F.COD_GRUPO_CIA
      AND    V1.LOCAL(+) = F.COD_LOCAL
      AND    V1.COD_FORMA_PAGO(+) = F.COD_FORMA_PAGO
      GROUP BY F.COD_GRUPO_CIA,F.COD_LOCAL,TO_CHAR(C.FEC_DIA_VTA,'DD/MM/YYYY')) RES
      GROUP BY  RES.CIA,RES.LOCAL,RES.FECHA;

      --CALCULANDO EL MONTO GRABADO EN LA INTERFACE
      SELECT ROUND(SUM(TO_NUMBER(trim(I.VAL_IMPORTE),'99999999999990.00')),1) IMPORTE_TOTAL_I
      INTO   v_suma_interface
      FROM   INT_CAJA_ELECTRONICA I
      WHERE  I.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    I.COD_LOCAL     = cCodLocal_in
      AND    I.FEC_OPERACION = cFecCierreDia_in
      GROUP  BY I.COD_GRUPO_CIA,I.COD_LOCAL,I.FEC_OPERACION;


      v_suma_interface := trunc(v_suma_interface);
      v_suma_calculada := trunc(v_suma_calculada);

        IF v_suma_interface = v_suma_calculada  THEN
            v_resultado:= INDICADOR_SI;
        ELSIF v_suma_interface != v_suma_calculada  THEN
            v_resultado:= INDICADOR_NO;
        END IF;
      EXCEPTION
      WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR AL VALIDAR LOS MONTOS DE INTERFACE.'||SQLERRM);
            v_resultado:= INDICADOR_NO;
      END;

   vComentario := 'NOTA: SE GENERO EL ARCHIVO INTERFACE "'||cNombreArchivo_in||'".';
   vAsunto_in  := 'ERROR EN LOS MONTOS DE CAJA ELECTRONICA:';
   vTitulo_in  := 'ALERTA';
   vMensaje_in := 'ERROR AL GENERAR LA INTERFACE DE CAJA ELECTRONICA PARA LA FECHA: '
                   ||cFecCierreDia_in ||' </B> '||
                   '<BR> <I>VERIFIQUE:</I> <BR>'||
                   'ERROR AL GENERAR DATA EN TABLA DE INTERFACE<B> <BR>'||
                   'MONTO CALCULADO DEL DIA : S/.' ||v_suma_calculada || '<BR>' ||
                   'MONTO DE INTERFACE: S/.' ||v_suma_interface || '<BR>' ||
                   vComentario;

     IF v_resultado = INDICADOR_NO THEN
       INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in ,
                                    cCodLocal_in    ,
                                    vAsunto_in      ,
                                    vTitulo_in      ,
                                    vMensaje_in     ,
                                    INDICADOR_SI  );
     END IF;
  END;

  /****************************************************************************/

  PROCEDURE INT_EJECUTA_CUADRATURA_026(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura(cCia CHAR) IS

    SELECT
           F.COD_GRUPO_CIA COD_GRUPO_CIA,
           F.COD_LOCAL COD_LOCAL,
           TO_CHAR(C.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           C_C_CREDITO_GRAL TIPO_OPERACION,
           CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(C.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(C.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           ' ' REFERENCIA,
           SUBSTR(P.DESC_CORTA_FORMA_PAGO,1,25) TEX_CAB,
           DECODE(F.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(SUM(F.MON_ENTREGA),'999999990.00') IMPORTE,
           TD.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           DC.VAL_CUENTA CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
           ' ' ASIG_1,
           C_C_RT || '-' || F.COD_LOCAL || '-' || TO_CHAR(C.FEC_DIA_VTA,'DDMMYYYY') || '/' || 'CREDITOS' TEX_DET_1,
           '11' CLA_CUE_2,
           'CR' CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || F.COD_LOCAL || '-' || TO_CHAR(C.FEC_DIA_VTA,'DDMMYYYY') || '/' || 'CREDITOS' TEX_DET_2
    FROM   CE_FORMA_PAGO_ENTREGA F,
           VTA_FORMA_PAGO P,
           CE_MOV_CAJA C,
           PBL_DEPOSITO_CUENTA DC,
           PBL_TIP_DEPOSITO TD
    WHERE  F.COD_GRUPO_CIA = ccodgrupocia_in
    AND    F.COD_LOCAL = ccodlocal_in
    AND    F.EST_FORMA_PAGO_ENT = 'A'
    AND    P.IND_TARJ = INDICADOR_NO
    AND    P.IND_FORMA_PAGO_EFECTIVO = INDICADOR_NO
    AND    P.SEC_DEP_CUENTA IS NOT NULL
    AND    F.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                              FROM   CE_MOV_CAJA A
                              WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                              AND    A.COD_LOCAL = ccodlocal_in
                              AND    A.FEC_DIA_VTA = TO_DATE(vFecCierreDia_in,'dd/MM/yyyy')
                              AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
    AND    F.COD_GRUPO_CIA = P.COD_GRUPO_CIA
    AND    F.COD_LOCAL = C.COD_LOCAL
    AND    F.SEC_MOV_CAJA = C.SEC_MOV_CAJA
    AND    F.COD_GRUPO_CIA = P.COD_GRUPO_CIA
    AND    F.COD_FORMA_PAGO = P.COD_FORMA_PAGO
    AND    P.SEC_DEP_CUENTA = DC.SEC_DEP_CUENTA
    AND    P.COD_TIP_DEPOSITO = TD.COD_TIP_DEPOSITO
-- 2010-07-15 JOLIVA: AGREGA FORMA DE PAGO CIFARMA
--    AND    F.COD_FORMA_PAGO NOT IN ('00047','00025','00051')
    AND    F.COD_FORMA_PAGO NOT IN ('00055', '00047','00025','00051','00050')

    GROUP BY
           F.COD_GRUPO_CIA,
           F.COD_LOCAL,
           TO_CHAR(C.FEC_DIA_VTA,'DD/MM/YYYY'),
           C_C_CREDITO_GRAL,
           CLASE_DOC_SA,
           TO_CHAR(C.FEC_DIA_VTA,'DD/MM/YYYY'),
           TO_CHAR(C.FEC_DIA_VTA,'DD/MM/YYYY'),
           ' ',
           SUBSTR(P.DESC_CORTA_FORMA_PAGO,1,25),
           DECODE(F.TIP_MONEDA,TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP),
           TD.DESC_CLAVE_CUENTA_1,
           DC.VAL_CUENTA,
           ' ',
           ' ',
           ' ',
           C_C_RT || '-' || F.COD_LOCAL || '-' || TO_CHAR(C.FEC_DIA_VTA,'DDMMYYYY') || '/' || 'CREDITOS',
           '11',
           'CR',
           ' ',
           ' '

 /*   UNION ALL

    SELECT F.COD_GRUPO_CIA COD_GRUPO_CIA,
           F.COD_LOCAL COD_LOCAL,
           TO_CHAR(CE.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           C_C_CREDITO_GRAL TIPO_OPERACION,--C_C_CREDITO_GRAL ,
           CLASE_DOC_SA CLASE_DOC,--CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CE.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CE.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           'CE' || '-' || F.COD_LOCAL || '-' || TO_CHAR(CE.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           SUBSTR(FP.DESC_CORTA_FORMA_PAGO,1,25) TEX_CAB,
           DECODE(F.TIP_MONEDA,'01','PEN','USD') MONEDA,--TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999999990.00') IMPORTE,

-- 2013-06-21 JOLIVA: SE MODIFICA DE ACUERDO A LA NUEVA LOGICA DE CONVENIOS MF:
--           TD.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CASE
               WHEN FP.COD_FORMA_PAGO = '00047' THEN '40' -- CONVENIO TRABAJADORES DE MIFARMA
               ELSE                                  '01' -- TD.DESC_CLAVE_CUENTA_1                -- OTROS CONVENIOS
           END                                            CLA_CUE_1,
-- 2013-06-21 JOLIVA: SE MODIFICA DE ACUERDO A LA NUEVA LOGICA DE CONVENIOS MF:
--           DC.VAL_CUENTA CUE_1,
           CASE
               WHEN FP.COD_FORMA_PAGO = '00047' THEN DC.VAL_CUENTA -- CONVENIO TRABAJADORES DE MIFARMA
               WHEN FP.COD_FORMA_PAGO = '00025' THEN '125144'            -- QS
               WHEN FP.COD_FORMA_PAGO = '00055' THEN '125000'            -- CIFARMA
               WHEN FP.COD_FORMA_PAGO = '00051' THEN '125044'            -- SANCELA

\*
               ELSE
                    CASE
						-- EMISION DE COMPROBANTE
                         WHEN F.IM_TOTAL_PAGO >= 0 THEN (SELECT CP.COD_CLIENTE_SAP FROM VTA_COMP_PAGO CP WHERE CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA AND CP.COD_LOCAL = C.COD_LOCAL AND CP.NUM_PED_VTA = C.NUM_PED_VTA AND CP.COD_CLIENTE_SAP IS NOT NULL)
						 -- ANULACIONES DE COMPROBANTES
                         ELSE (SELECT CP.COD_CLIENTE_SAP FROM VTA_COMP_PAGO CP WHERE CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA AND CP.COD_LOCAL = C.COD_LOCAL AND CP.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN AND CP.COD_CLIENTE_SAP IS NOT NULL)
                    END
*\
           END                                            CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
-- 2010-01-20: JOLIVA  Para convenio Mifarma se mostrará el DNI en lugar del código del trabajador
--           CC.COD_TRAB_CONV ASIG_1,
-- 2010-02-10: JOLIVA Para el convenio QS y Sancela se enviará el DNI en lugar del código de trabajador
-- 2010-10-21: JOLIVA Se modifica para que obtenga el código de trabajador en el caso de anulaciones
-- 2013-06-21 JOLIVA: SE MODIFICA DE ACUERDO A LA NUEVA LOGICA DE CONVENIOS MF:
\*
           CASE
                WHEN TRIM(FP.COD_FORMA_PAGO) IN ('00055') THEN NVL(lpad(CONV.NUM_DOC_IDEN,11,'0'),
                                                                            LPAD((SELECT RE.NUM_DOC_IDEN
                                                                           FROM CON_PED_VTA_CLI RE
                                                                           WHERE RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                                                           AND RE.COD_LOCAL = C.COD_LOCAL
                                                                           AND RE.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                                                                           AND RE.COD_CONVENIO=C.COD_CONVENIO),11,'0')
                                                                            )
                ---jquispe 11.04.2011 cambio interfaz para conv mifarma
                WHEN TRIM(FP.COD_FORMA_PAGO) IN ('00047') THEN NVL(lpad(CONV.NUM_DOC_IDEN,10,'0'),
                                                                            LPAD((SELECT RE.NUM_DOC_IDEN
                                                                           FROM CON_PED_VTA_CLI RE
                                                                           WHERE RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                                                           AND RE.COD_LOCAL = C.COD_LOCAL
                                                                           AND RE.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                                                                           AND RE.COD_CONVENIO=C.COD_CONVENIO),10,'0')
                                                                            )
                WHEN TRIM(FP.COD_FORMA_PAGO) IN ('00025' , '00051') THEN NVL(LPAD(CC.COD_TRAB_CONV,11,'0'),
                                                                           LPAD((SELECT RE.NUM_DOC_IDEN
                                                                           FROM CON_PED_VTA_CLI RE
                                                                           WHERE RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                                                           AND RE.COD_LOCAL = C.COD_LOCAL
                                                                           AND RE.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                                                                           AND RE.COD_CONVENIO=C.COD_CONVENIO),11,'0')
                                                                           )
                WHEN TRIM(CC.COD_TRAB_CONV) IS NULL THEN NVL((SELECT COD_TRAB_EMPRESA FROM CON_PED_VTA_CLI CCC WHERE CCC.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN), CC.COD_TRAB_CONV)
                ELSE TRIM(CC.COD_TRAB_CONV)
           END "ASIG_1",
*\
           CASE
               WHEN FP.COD_FORMA_PAGO = '00047' THEN
                                                     NVL(LPAD(CONV.NUM_DOC_IDEN,10,'0'),
                                                                            LPAD((SELECT RE.NUM_DOC_IDEN
                                                                           FROM CON_PED_VTA_CLI RE
                                                                           WHERE RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                                                           AND RE.COD_LOCAL = C.COD_LOCAL
                                                                           AND RE.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                                                                           AND RE.COD_CONVENIO=C.COD_CONVENIO),10,'0')
                                                                            )               -- CONVENIO TRABAJADORES DE MIFARMA
               ELSE 'CR-Venta a credito'
           END                                            ASIG_1,
-- 2013-06-25 JOLIVA: SE CAMBIA TEX_DET_1 POR NUMERO DE COMPROBANTE SAP
--           C_C_RT || '-' || F.COD_LOCAL || '-' || TO_CHAR(CE.FEC_DIA_VTA,'DDMMYYYY') || '/' || FP.DESC_CORTA_FORMA_PAGO TEX_DET_1,
           (
                  DECODE(CP.TIP_COMP_PAGO,
                         '02', '01',       -- FACTURA
                         '01', '03',       -- BOLETA
                         '05', '12',       -- TICKET BOLETA
                         'ND'              -- SIN DEFINIR
                         ) ||'-'|| LPAD(SUBSTR(CP.NUM_COMP_PAGO,1,3),5,'0') ||'-'|| SUBSTR(CP.NUM_COMP_PAGO,-7)
           ) TEX_DET_1,
           '11' CLA_CUE_2,
           'CR' CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || F.COD_LOCAL || '-' || TO_CHAR(CE.FEC_DIA_VTA,'DDMMYYYY') || '/' || 'CREDITOS' TEX_DET_2
    FROM VTA_PEDIDO_VTA_CAB C,
         VTA_FORMA_PAGO_PEDIDO F,
         VTA_FORMA_PAGO FP,
         CON_PED_VTA_CLI CONV,
         CON_CLI_CONV CC,
         CON_MAE_CLIENTE MC,
         CE_MOV_CAJA CE,
         PBL_DEPOSITO_CUENTA DC,
         PBL_TIP_DEPOSITO TD,
         VTA_COMP_PAGO CP
    WHERE C.COD_GRUPO_CIA = F.COD_GRUPO_CIA
      AND C.COD_LOCAL     = F.COD_LOCAL
      AND C.NUM_PED_VTA   = F.NUM_PED_VTA

-- 2010-10-21: JOLIVA Se comenta la siguiente línea para que considere anulaciones (se agregan outer joins en lineas de abajo
--      AND C.IND_PEDIDO_ANUL = 'N' --ASOSA, 21.10.2010 - comentado porque deberia devolver el credito
      AND C.EST_PED_VTA     = 'C'

-- 2010-07-15 JOLIVA: AGREGA FORMA DE PAGO CIFARMA
--      AND F.COD_FORMA_PAGO  IN ('00047','00025','00051')--conv mifarma, quimica 100% , sancela
      AND F.COD_FORMA_PAGO  IN ('00055', '00047','00025','00051')--conv mifarma, quimica 100% , sancela

      AND C.COD_GRUPO_CIA = CE.COD_GRUPO_CIA
      AND C.COD_LOCAL     = CE.COD_LOCAL
      AND C.SEC_MOV_CAJA  = CE.SEC_MOV_CAJA

      AND F.COD_GRUPO_CIA = ccodgrupocia_in
      AND F.COD_LOCAL     = ccodlocal_in
      AND CE.FEC_DIA_VTA  = TO_DATE(vFecCierreDia_in,'dd/MM/yyyy')--fecha dia venta

      AND CONV.COD_GRUPO_CIA(+) = C.COD_GRUPO_CIA
      AND CONV.COD_LOCAL(+)     = C.COD_LOCAL
      AND CONV.NUM_PED_VTA(+)   = C.NUM_PED_VTA

      AND CONV.COD_CONVENIO  = CC.COD_CONVENIO(+)
      AND CONV.COD_CLI       = CC.COD_CLI(+)

      AND F.COD_GRUPO_CIA    = FP.COD_GRUPO_CIA
      AND F.COD_FORMA_PAGO   = FP.COD_FORMA_PAGO

      AND FP.SEC_DEP_CUENTA = DC.SEC_DEP_CUENTA
      AND FP.COD_TIP_DEPOSITO = TD.COD_TIP_DEPOSITO

      AND MC.COD_CLI(+)          = CC.COD_CLI

      AND CP.COD_GRUPO_CIA       = C.COD_GRUPO_CIA
      AND CP.COD_LOCAL           = C.COD_LOCAL
      AND CP.NUM_PED_VTA         = C.NUM_PED_VTA

      AND NVL(C.IND_CONV_BTL_MF,'N') = 'N'

    UNION ALL

    SELECT F.COD_GRUPO_CIA COD_GRUPO_CIA,
           F.COD_LOCAL COD_LOCAL,
           TO_CHAR(CE.FEC_DIA_VTA,'DD/MM/YYYY') FECHA,
           C_C_CREDITO_GRAL TIPO_OPERACION,--C_C_CREDITO_GRAL ,
           CLASE_DOC_SA CLASE_DOC,--CLASE_DOC_SA CLASE_DOC,
           TO_CHAR(CE.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_DOC,
           TO_CHAR(CE.FEC_DIA_VTA,'DD/MM/YYYY') FECHA_CON,
           'CE' || '-' || F.COD_LOCAL || '-' || TO_CHAR(CE.FEC_DIA_VTA,'DDMMYYYY') REFERENCIA,
           SUBSTR(FP.DESC_CORTA_FORMA_PAGO,1,25) TEX_CAB,
           DECODE(F.TIP_MONEDA,'01','PEN','USD') MONEDA,--TIP_MONEDA_SOLES,TIP_MONEDA_SOLES_SAP,TIP_MONEDA_DOLARES_SAP) MONEDA,
           TO_CHAR(-1 * (CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO),'999999990.00') IMPORTE,

-- 2013-06-21 JOLIVA: SE MODIFICA DE ACUERDO A LA NUEVA LOGICA DE CONVENIOS MF:
--           TD.DESC_CLAVE_CUENTA_1 CLA_CUE_1,
           CASE
               WHEN FP.COD_FORMA_PAGO = '00047' THEN '40' -- CONVENIO TRABAJADORES DE MIFARMA
               ELSE                                  '01' -- TD.DESC_CLAVE_CUENTA_1                -- OTROS CONVENIOS
           END                                            CLA_CUE_1,
-- 2013-06-21 JOLIVA: SE MODIFICA DE ACUERDO A LA NUEVA LOGICA DE CONVENIOS MF:
--           DC.VAL_CUENTA CUE_1,
           CASE
               WHEN FP.COD_FORMA_PAGO = '00047' THEN DC.VAL_CUENTA -- CONVENIO TRABAJADORES DE MIFARMA
               WHEN FP.COD_FORMA_PAGO = '00025' THEN '125144'            -- QS
               WHEN FP.COD_FORMA_PAGO = '00055' THEN '125000'            -- CIFARMA
               WHEN FP.COD_FORMA_PAGO = '00051' THEN '125044'            -- SANCELA

\*
               ELSE
                    CASE
						-- EMISION DE COMPROBANTE
                         WHEN F.IM_TOTAL_PAGO >= 0 THEN (SELECT CP.COD_CLIENTE_SAP FROM VTA_COMP_PAGO CP WHERE CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA AND CP.COD_LOCAL = C.COD_LOCAL AND CP.NUM_PED_VTA = C.NUM_PED_VTA AND CP.COD_CLIENTE_SAP IS NOT NULL)
						 -- ANULACIONES DE COMPROBANTES
                         ELSE (SELECT CP.COD_CLIENTE_SAP FROM VTA_COMP_PAGO CP WHERE CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA AND CP.COD_LOCAL = C.COD_LOCAL AND CP.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN AND CP.COD_CLIENTE_SAP IS NOT NULL)
                    END
*\
           END                                            CUE_1,
           ' ' CME,
           ' ' IMPUESTO,
-- 2010-01-20: JOLIVA  Para convenio Mifarma se mostrará el DNI en lugar del código del trabajador
--           CC.COD_TRAB_CONV ASIG_1,
-- 2010-02-10: JOLIVA Para el convenio QS y Sancela se enviará el DNI en lugar del código de trabajador
-- 2010-10-21: JOLIVA Se modifica para que obtenga el código de trabajador en el caso de anulaciones
-- 2013-06-21 JOLIVA: SE MODIFICA DE ACUERDO A LA NUEVA LOGICA DE CONVENIOS MF:
\*
           CASE
                WHEN TRIM(FP.COD_FORMA_PAGO) IN ('00055') THEN NVL(lpad(CONV.NUM_DOC_IDEN,11,'0'),
                                                                            LPAD((SELECT RE.NUM_DOC_IDEN
                                                                           FROM CON_PED_VTA_CLI RE
                                                                           WHERE RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                                                           AND RE.COD_LOCAL = C.COD_LOCAL
                                                                           AND RE.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                                                                           AND RE.COD_CONVENIO=C.COD_CONVENIO),11,'0')
                                                                            )
                ---jquispe 11.04.2011 cambio interfaz para conv mifarma
                WHEN TRIM(FP.COD_FORMA_PAGO) IN ('00047') THEN NVL(lpad(CONV.NUM_DOC_IDEN,10,'0'),
                                                                            LPAD((SELECT RE.NUM_DOC_IDEN
                                                                           FROM CON_PED_VTA_CLI RE
                                                                           WHERE RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                                                           AND RE.COD_LOCAL = C.COD_LOCAL
                                                                           AND RE.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                                                                           AND RE.COD_CONVENIO=C.COD_CONVENIO),10,'0')
                                                                            )
                WHEN TRIM(FP.COD_FORMA_PAGO) IN ('00025' , '00051') THEN NVL(LPAD(CC.COD_TRAB_CONV,11,'0'),
                                                                           LPAD((SELECT RE.NUM_DOC_IDEN
                                                                           FROM CON_PED_VTA_CLI RE
                                                                           WHERE RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                                                           AND RE.COD_LOCAL = C.COD_LOCAL
                                                                           AND RE.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                                                                           AND RE.COD_CONVENIO=C.COD_CONVENIO),11,'0')
                                                                           )
                WHEN TRIM(CC.COD_TRAB_CONV) IS NULL THEN NVL((SELECT COD_TRAB_EMPRESA FROM CON_PED_VTA_CLI CCC WHERE CCC.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN), CC.COD_TRAB_CONV)
                ELSE TRIM(CC.COD_TRAB_CONV)
           END "ASIG_1",
*\
           CASE
               WHEN FP.COD_FORMA_PAGO = '00047' THEN
                                                     NVL(LPAD(CONV.NUM_DOC_IDEN,10,'0'),
                                                                            LPAD((SELECT RE.NUM_DOC_IDEN
                                                                           FROM CON_PED_VTA_CLI RE
                                                                           WHERE RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                                                           AND RE.COD_LOCAL = C.COD_LOCAL
                                                                           AND RE.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                                                                           AND RE.COD_CONVENIO=C.COD_CONVENIO),10,'0')
                                                                            )               -- CONVENIO TRABAJADORES DE MIFARMA
               ELSE 'CR-Venta a credito'
           END                                            ASIG_1,
-- 2013-06-25 JOLIVA: SE CAMBIA TEX_DET_1 POR NUMERO DE COMPROBANTE SAP
--           C_C_RT || '-' || F.COD_LOCAL || '-' || TO_CHAR(CE.FEC_DIA_VTA,'DDMMYYYY') || '/' || FP.DESC_CORTA_FORMA_PAGO TEX_DET_1,
           (
                  DECODE(CP.TIP_COMP_PAGO,
                         '02', '01',       -- FACTURA
                         '01', '03',       -- BOLETA
                         '05', '12',       -- TICKET BOLETA
                         'ND'              -- SIN DEFINIR
                         ) ||'-'|| LPAD(SUBSTR(CP.NUM_COMP_PAGO,1,3),5,'0') ||'-'|| SUBSTR(CP.NUM_COMP_PAGO,-7)
           ) TEX_DET_1,
           '11' CLA_CUE_2,
           'CR' CUE_2,
           ' ' CEN_COSTO,
           ' ' IND_IMP,
           C_C_RT || '-' || F.COD_LOCAL || '-' || TO_CHAR(CE.FEC_DIA_VTA,'DDMMYYYY') || '/' || 'CREDITOS' TEX_DET_2
    FROM VTA_PEDIDO_VTA_CAB C,
         VTA_FORMA_PAGO_PEDIDO F,
         VTA_FORMA_PAGO FP,
         CON_PED_VTA_CLI CONV,
         CON_CLI_CONV CC,
         CON_MAE_CLIENTE MC,
         CE_MOV_CAJA CE,
         PBL_DEPOSITO_CUENTA DC,
         PBL_TIP_DEPOSITO TD,
         VTA_COMP_PAGO CP
    WHERE C.COD_GRUPO_CIA = F.COD_GRUPO_CIA
      AND C.COD_LOCAL     = F.COD_LOCAL
      AND C.NUM_PED_VTA   = F.NUM_PED_VTA

-- 2010-10-21: JOLIVA Se comenta la siguiente línea para que considere anulaciones (se agregan outer joins en lineas de abajo
--      AND C.IND_PEDIDO_ANUL = 'N' --ASOSA, 21.10.2010 - comentado porque deberia devolver el credito
      AND C.EST_PED_VTA     = 'C'

-- 2010-07-15 JOLIVA: AGREGA FORMA DE PAGO CIFARMA
--      AND F.COD_FORMA_PAGO  IN ('00047','00025','00051')--conv mifarma, quimica 100% , sancela
      AND F.COD_FORMA_PAGO  IN ('00055', '00047','00025','00051')--conv mifarma, quimica 100% , sancela

      AND C.COD_GRUPO_CIA = CE.COD_GRUPO_CIA
      AND C.COD_LOCAL     = CE.COD_LOCAL
      AND C.SEC_MOV_CAJA  = CE.SEC_MOV_CAJA

      AND F.COD_GRUPO_CIA = ccodgrupocia_in
      AND F.COD_LOCAL     = ccodlocal_in
      AND CE.FEC_DIA_VTA  = TO_DATE(vFecCierreDia_in,'dd/MM/yyyy')--fecha dia venta

      AND CONV.COD_GRUPO_CIA(+) = C.COD_GRUPO_CIA
      AND CONV.COD_LOCAL(+)     = C.COD_LOCAL
      AND CONV.NUM_PED_VTA(+)   = C.NUM_PED_VTA

      AND CONV.COD_CONVENIO  = CC.COD_CONVENIO(+)
      AND CONV.COD_CLI       = CC.COD_CLI(+)

      AND F.COD_GRUPO_CIA    = FP.COD_GRUPO_CIA
      AND F.COD_FORMA_PAGO   = FP.COD_FORMA_PAGO

      AND FP.SEC_DEP_CUENTA = DC.SEC_DEP_CUENTA
      AND FP.COD_TIP_DEPOSITO = TD.COD_TIP_DEPOSITO

      AND MC.COD_CLI(+)          = CC.COD_CLI

      AND CP.COD_GRUPO_CIA       = C.COD_GRUPO_CIA
      AND CP.COD_LOCAL           = C.COD_LOCAL
      AND CP.NUM_PEDIDO_ANUL     = C.NUM_PED_VTA

      AND NVL(C.IND_CONV_BTL_MF,'N') = 'N'
*/
-- 2013-07-24 JOLIVA: SE AGREGAN COMPROBANTES POR CONVENIOS BTL
      UNION ALL

      SELECT
            C.COD_GRUPO_CIA,
            C.COD_LOCAL,
            TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY') FECHA,
            C_C_CREDITO_GRAL TIPO_OPERACION,
            CLASE_DOC_SA CLASE_DOC,
            TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY') FECHA_DOC,
            TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY') FECHA_CON,
            'CE' || '-' || C.COD_LOCAL || '-' || TO_CHAR(C.FEC_PED_VTA,'DDMMYYYY') REFERENCIA,
            ' ' TEX_CAB,
            'PEN'                  MONEDA,
            TO_CHAR(1 * (CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO),'999999990.00') IMPORTE,
            /* CASE
                 WHEN C.COD_CONVENIO = '0000000025' THEN '40' -- CONVENIO TRABAJADORES DE MIFARMA
                 ELSE                                    '01' -- OTROS CONVENIOS
             END                                           CLA_CUE_1,*/
             CASE cCia
             WHEN MARCA_MIFARMA THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_MIFARMA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '40' -- CONVENIO TRABAJADORES DE MIFARMA
                                    ELSE '01'
                                    END
             WHEN MARCA_BTL     THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_BTL AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '40' -- CONVENIO TRABAJADORES DE BTL
                                    ELSE '01'
                                    END
             WHEN MARCA_FASA    THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_FASA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '40' -- CONVENIO TRABAJADORES DE FASA
                                    ELSE '01'
                                    END
             END CLA_CUE_1,
             /*CASE
                 WHEN C.COD_CONVENIO = '0000000025' THEN '1410100009' -- CONVENIO TRABAJADORES DE MIFARMA
                 ELSE                                    CP.COD_CLIENTE_SAP
             END                                            CUE_1,*/
             CASE cCia
             WHEN MARCA_MIFARMA THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_MIFARMA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '1410100009' -- CONVENIO TRABAJADORES DE MIFARMA
                                    ELSE CP.COD_CLIENTE_SAP
                                    END
             WHEN MARCA_BTL     THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_BTL AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '1410100009' -- CONVENIO TRABAJADORES DE BTL
                                    ELSE CP.COD_CLIENTE_SAP
                                    END
             WHEN MARCA_FASA    THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_FASA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '1410100009' -- CONVENIO TRABAJADORES DE FASA
                                    ELSE CP.COD_CLIENTE_SAP
                                    END
             END CUE_1,
             ' ' CME,
             ' ' IMPUESTO,
             /*CASE
                 WHEN C.COD_CONVENIO = '0000000025' THEN
                                                       NVL(LPAD(CONV.NUM_DOC_IDEN,10,'0'),
                                                                              LPAD((SELECT RE.NUM_DOC_IDEN
                                                                             FROM CON_PED_VTA_CLI RE
                                                                             WHERE RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                                                             AND RE.COD_LOCAL = C.COD_LOCAL
                                                                             AND RE.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                                                                             AND RE.COD_CONVENIO=C.COD_CONVENIO),10,'0')
                                                                              )               -- CONVENIO TRABAJADORES DE MIFARMA
                 ELSE 'CR-Venta a credito'
             END                                            ASIG_1,*/
             CASE cCia
             WHEN MARCA_MIFARMA THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_MIFARMA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN -- CONVENIO TRABAJADORES DE MIFARMA
                                         LPAD((SELECT TRIM(DNI) FROM CON_BENEFICIARIO WHERE COD_CONVENIO=C.COD_CONVENIO  AND COD_CLIENTE=C.COD_CLI_CONV),10,'0')
                                    ELSE 'CR-Venta a credito'
                                    END
             WHEN MARCA_BTL     THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_BTL AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03'     THEN -- CONVENIO TRABAJADORES DE BTL
                                         LPAD((SELECT TRIM(DNI) FROM CON_BENEFICIARIO WHERE COD_CONVENIO=C.COD_CONVENIO  AND COD_CLIENTE=C.COD_CLI_CONV),10,'0')
                                    ELSE 'CR-Venta a credito'
                                    END
             WHEN MARCA_FASA    THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_FASA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03'    THEN -- CONVENIO TRABAJADORES DE FASA
                                         LPAD((SELECT TRIM(DNI) FROM CON_BENEFICIARIO WHERE COD_CONVENIO=C.COD_CONVENIO  AND COD_CLIENTE=C.COD_CLI_CONV),10,'0')
                                    ELSE 'CR-Venta a credito'
                                    END
             END ASIG_1,
             (
                    DECODE(CP.TIP_COMP_PAGO,
                           '02', '01',       -- FACTURA
                           '01', '03',       -- BOLETA
                           '05', '12',       -- TICKET BOLETA
                           'ND'              -- SIN DEFINIR
                           ) ||'-'|| LPAD(SUBSTR(CP.NUM_COMP_PAGO,1,3),5,'0') ||'-'|| SUBSTR(CP.NUM_COMP_PAGO,-7)
             ) TEX_DET_1,
             '11' CLA_CUE_2,
             'CR' CUE_2,
             ' ' CEN_COSTO,
             ' ' IND_IMP,
             C_C_RT || '-' || C.COD_LOCAL || '-' || TO_CHAR(C.FEC_PED_VTA,'DDMMYYYY') || '/' || 'CREDITOS' TEX_DET_2
      FROM VTA_PEDIDO_VTA_CAB C,
           VTA_COMP_PAGO CP,
           CON_PED_VTA_CLI CONV,
           MAE_CONVENIO MC
      WHERE C.COD_GRUPO_CIA = ccodgrupocia_in
        AND C.COD_LOCAL = ccodlocal_in
        AND C.FEC_PED_VTA BETWEEN TO_DATE(vFecCierreDia_in,'dd/MM/yyyy') AND TO_DATE(vFecCierreDia_in,'dd/MM/yyyy') + 1 - 1/24/60/60
        AND C.EST_PED_VTA = 'C'
        AND C.IND_PED_CONVENIO = 'S'
        AND C.IND_CONV_BTL_MF = 'S'
        AND CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
        AND CP.COD_LOCAL = C.COD_LOCAL
        AND CP.NUM_PED_VTA = C.NUM_PED_VTA
        AND CP.IND_COMP_CREDITO = 'S'
        AND CP.TIP_COMP_PAGO != '03'

        AND CONV.COD_GRUPO_CIA(+) = C.COD_GRUPO_CIA
        AND CONV.COD_LOCAL(+)     = C.COD_LOCAL
        AND CONV.NUM_PED_VTA(+)   = C.NUM_PED_VTA

        AND MC.COD_CONVENIO(+)    = C.COD_CONVENIO


      UNION ALL

      SELECT
            C.COD_GRUPO_CIA,
            C.COD_LOCAL,
            TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY') FECHA,
            C_C_CREDITO_GRAL TIPO_OPERACION,
            CLASE_DOC_SA CLASE_DOC,
            TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY') FECHA_DOC,
            TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY') FECHA_CON,
            'CE' || '-' || C.COD_LOCAL || '-' || TO_CHAR(C.FEC_PED_VTA,'DDMMYYYY') REFERENCIA,
            ' ' TEX_CAB,
            'PEN'                  MONEDA,
            TO_CHAR(-1 * (CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO),'999999990.00') IMPORTE,
            /* CASE
                 WHEN C.COD_CONVENIO = '0000000025' THEN '40' -- CONVENIO TRABAJADORES DE MIFARMA
                 ELSE                                    '01' -- TD.DESC_CLAVE_CUENTA_1                -- OTROS CONVENIOS
             END                                            CLA_CUE_1,*/
             CASE cCia
             WHEN MARCA_MIFARMA THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_MIFARMA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '40' -- CONVENIO TRABAJADORES DE MIFARMA
                                    ELSE '01'
                                    END
             WHEN MARCA_BTL     THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_BTL AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '40' -- CONVENIO TRABAJADORES DE BTL
                                    ELSE '01'
                                    END
             WHEN MARCA_FASA    THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_FASA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '40' -- CONVENIO TRABAJADORES DE FASA
                                    ELSE '01'
                                    END
             END CLA_CUE_1,
            /* CASE
                 WHEN C.COD_CONVENIO = '0000000025' THEN '1410100009' -- CONVENIO TRABAJADORES DE MIFARMA
                 ELSE                                    CP.COD_CLIENTE_SAP
             END                                            CUE_1,*/
             CASE cCia
             WHEN MARCA_MIFARMA THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_MIFARMA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '1410100009' -- CONVENIO TRABAJADORES DE MIFARMA
                                    ELSE CP.COD_CLIENTE_SAP
                                    END
             WHEN MARCA_BTL     THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_BTL AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '1410100009' -- CONVENIO TRABAJADORES DE BTL
                                    ELSE CP.COD_CLIENTE_SAP
                                    END
             WHEN MARCA_FASA    THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_FASA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '1410100009' -- CONVENIO TRABAJADORES DE FASA
                                    ELSE CP.COD_CLIENTE_SAP
                                    END
             END CUE_1,
             ' ' CME,
             ' ' IMPUESTO,
            /* CASE
                 WHEN C.COD_CONVENIO = '0000000025' THEN
                                                       NVL(LPAD(CONV.NUM_DOC_IDEN,10,'0'),
                                                                              LPAD((SELECT RE.NUM_DOC_IDEN
                                                                             FROM CON_PED_VTA_CLI RE
                                                                             WHERE RE.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                                                             AND RE.COD_LOCAL = C.COD_LOCAL
                                                                             AND RE.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                                                                             AND RE.COD_CONVENIO=C.COD_CONVENIO),10,'0')
                                                                              )               -- CONVENIO TRABAJADORES DE MIFARMA
                 ELSE 'CR-Venta a credito'
             END                                            ASIG_1,*/
             CASE cCia
             WHEN MARCA_MIFARMA THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_MIFARMA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN -- CONVENIO TRABAJADORES DE MIFARMA
                                         LPAD((SELECT TRIM(DNI) FROM CON_BENEFICIARIO WHERE COD_CONVENIO=C.COD_CONVENIO  AND COD_CLIENTE=--C.COD_CLI_CONV
                                         (SELECT DISTINCT COD_CLI_CONV FROM VTA_PEDIDO_VTA_CAB WHERE COD_GRUPO_CIA=C.COD_GRUPO_CIA AND COD_LOCAL=C.COD_LOCAL AND NUM_PED_VTA=C.NUM_PED_VTA_ORIGEN)
                                         ),10,'0')
                                    ELSE 'CR-Venta a credito'
                                    END
             WHEN MARCA_BTL     THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_BTL AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03'     THEN -- CONVENIO TRABAJADORES DE BTL
                                         LPAD((SELECT TRIM(DNI) FROM CON_BENEFICIARIO WHERE COD_CONVENIO=C.COD_CONVENIO  AND COD_CLIENTE=--C.COD_CLI_CONV
                                         (SELECT DISTINCT COD_CLI_CONV FROM VTA_PEDIDO_VTA_CAB WHERE COD_GRUPO_CIA=C.COD_GRUPO_CIA AND COD_LOCAL=C.COD_LOCAL AND NUM_PED_VTA=C.NUM_PED_VTA_ORIGEN)
                                         ),10,'0')
                                    ELSE 'CR-Venta a credito'
                                    END
             WHEN MARCA_FASA    THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_FASA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03'    THEN -- CONVENIO TRABAJADORES DE FASA
                                         LPAD((SELECT TRIM(DNI) FROM CON_BENEFICIARIO WHERE COD_CONVENIO=C.COD_CONVENIO  AND COD_CLIENTE=--C.COD_CLI_CONV
                                         (SELECT DISTINCT COD_CLI_CONV FROM VTA_PEDIDO_VTA_CAB WHERE COD_GRUPO_CIA=C.COD_GRUPO_CIA AND COD_LOCAL=C.COD_LOCAL AND NUM_PED_VTA=C.NUM_PED_VTA_ORIGEN)
                                         ),10,'0')
                                    ELSE 'CR-Venta a credito'
                                    END
             END ASIG_1,
             (
                    DECODE(CP.TIP_COMP_PAGO,
                           '02', '01',       -- FACTURA
                           '01', '03',       -- BOLETA
                           '05', '12',       -- TICKET BOLETA
                           'ND'              -- SIN DEFINIR
                           ) ||'-'|| LPAD(SUBSTR(CP.NUM_COMP_PAGO,1,3),5,'0') ||'-'|| SUBSTR(CP.NUM_COMP_PAGO,-7)
             ) TEX_DET_1,
             '11' CLA_CUE_2,
             'CR' CUE_2,
             ' ' CEN_COSTO,
             ' ' IND_IMP,
             C_C_RT || '-' || C.COD_LOCAL || '-' || TO_CHAR(C.FEC_PED_VTA,'DDMMYYYY') || '/' || 'CREDITOS' TEX_DET_2
      FROM VTA_PEDIDO_VTA_CAB C,
           VTA_COMP_PAGO CP,
           CON_PED_VTA_CLI CONV,
           MAE_CONVENIO MC
      WHERE C.COD_GRUPO_CIA = ccodgrupocia_in
        AND C.COD_LOCAL = ccodlocal_in
        AND C.FEC_PED_VTA BETWEEN TO_DATE(vFecCierreDia_in,'dd/MM/yyyy') AND TO_DATE(vFecCierreDia_in,'dd/MM/yyyy') + 1 - 1/24/60/60
        AND C.EST_PED_VTA = 'C'
        AND C.IND_PED_CONVENIO = 'S'
        AND C.IND_CONV_BTL_MF = 'S'
        AND CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
        AND CP.COD_LOCAL = C.COD_LOCAL
        AND CP.NUM_PEDIDO_ANUL = C.NUM_PED_VTA
        AND CP.IND_COMP_CREDITO = 'S'
        AND CP.TIP_COMP_PAGO != '03'

        AND CONV.COD_GRUPO_CIA(+) = C.COD_GRUPO_CIA
        AND CONV.COD_LOCAL(+)     = C.COD_LOCAL
        AND CONV.NUM_PED_VTA(+)   = C.NUM_PED_VTA

        AND MC.COD_CONVENIO(+)    = C.COD_CONVENIO

      UNION ALL

      -- NOTAS DE CREDITO POR CONVENIOS BTL
      SELECT
            C.COD_GRUPO_CIA,
            C.COD_LOCAL,
            TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY') FECHA,
            C_C_CREDITO_GRAL TIPO_OPERACION,
            CLASE_DOC_SA CLASE_DOC,
            TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY') FECHA_DOC,
            TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY') FECHA_CON,
            'CE' || '-' || C.COD_LOCAL || '-' || TO_CHAR(C.FEC_PED_VTA,'DDMMYYYY') REFERENCIA,
            ' ' TEX_CAB,
            'PEN'                  MONEDA,
            TO_CHAR((CP_NC.VAL_NETO_COMP_PAGO + CP_NC.VAL_REDONDEO_COMP_PAGO),'999999990.00') IMPORTE,
            /* CASE
                 WHEN ORIG.COD_CONVENIO = '0000000025' THEN '40' -- CONVENIO TRABAJADORES DE MIFARMA
                 ELSE                                    '01' -- TD.DESC_CLAVE_CUENTA_1                -- OTROS CONVENIOS
             END                                            CLA_CUE_1,*/
             CASE cCia
             WHEN MARCA_MIFARMA THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_MIFARMA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '40' -- CONVENIO TRABAJADORES DE MIFARMA
                                    ELSE '01'
                                    END
             WHEN MARCA_BTL     THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_BTL AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '40' -- CONVENIO TRABAJADORES DE BTL
                                    ELSE '01'
                                    END
             WHEN MARCA_FASA    THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_FASA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '40' -- CONVENIO TRABAJADORES DE FASA
                                    ELSE '01'
                                    END
             END CLA_CUE_1,
           /*  CASE
                 WHEN ORIG.COD_CONVENIO = '0000000025' THEN '1410100009' -- CONVENIO TRABAJADORES DE MIFARMA
                 ELSE                                    CP_ORIG.COD_CLIENTE_SAP
             END                                            CUE_1,*/
             CASE cCia
             WHEN MARCA_MIFARMA THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_MIFARMA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '1410100009' -- CONVENIO TRABAJADORES DE MIFARMA
                                    ELSE DECODE(C.TIP_PED_VTA,'03',CP_NC.COD_CLIENTE_SAP,CP_ORIG.COD_CLIENTE_SAP)--CP_ORIG.COD_CLIENTE_SAP
                                    END
             WHEN MARCA_BTL     THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_BTL AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '1410100009' -- CONVENIO TRABAJADORES DE BTL
                                    ELSE DECODE(C.TIP_PED_VTA,'03',CP_NC.COD_CLIENTE_SAP,CP_ORIG.COD_CLIENTE_SAP)--CP_ORIG.COD_CLIENTE_SAP
                                    END
             WHEN MARCA_FASA    THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_FASA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN '1410100009' -- CONVENIO TRABAJADORES DE FASA
                                    ELSE DECODE(C.TIP_PED_VTA,'03',CP_NC.COD_CLIENTE_SAP,CP_ORIG.COD_CLIENTE_SAP)--CP_ORIG.COD_CLIENTE_SAP
                                    END
             END CUE_1,
             ' ' CME,
             ' ' IMPUESTO,
           /*  CASE
                 WHEN ORIG.COD_CONVENIO = '0000000025' THEN
                                                       NVL(LPAD(CONV_ORIG.NUM_DOC_IDEN,10,'0'),
                                                                              LPAD((SELECT RE.NUM_DOC_IDEN
                                                                             FROM CON_PED_VTA_CLI RE
                                                                             WHERE RE.COD_GRUPO_CIA = ORIG.COD_GRUPO_CIA
                                                                             AND RE.COD_LOCAL = ORIG.COD_LOCAL
                                                                             AND RE.NUM_PED_VTA = ORIG.NUM_PED_VTA_ORIGEN
                                                                             AND RE.COD_CONVENIO=ORIG.COD_CONVENIO),10,'0')
                                                                              )               -- CONVENIO TRABAJADORES DE MIFARMA
                 ELSE 'CR-Venta a credito'
             END                                            ASIG_1,*/
             CASE cCia
             WHEN MARCA_MIFARMA THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_MIFARMA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03' THEN -- CONVENIO TRABAJADORES DE MIFARMA
                                         LPAD((SELECT TRIM(DNI) FROM CON_BENEFICIARIO WHERE COD_CONVENIO=C.COD_CONVENIO  AND COD_CLIENTE=ORIG.COD_CLI_CONV),10,'0')
                                    ELSE 'CR-Venta a credito'
                                    END
             WHEN MARCA_BTL     THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_BTL AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03'     THEN -- CONVENIO TRABAJADORES DE BTL
                                         LPAD((SELECT TRIM(DNI) FROM CON_BENEFICIARIO WHERE COD_CONVENIO=C.COD_CONVENIO  AND COD_CLIENTE=ORIG.COD_CLI_CONV),10,'0')
                                    ELSE 'CR-Venta a credito'
                                    END
             WHEN MARCA_FASA    THEN
                                    CASE
                                    WHEN MC.TMP_RUC = RUC_FASA AND MC.COD_TIPO_CONVENIO=3 AND C.TIP_PED_VTA<>'03'    THEN -- CONVENIO TRABAJADORES DE FASA
                                         LPAD((SELECT TRIM(DNI) FROM CON_BENEFICIARIO WHERE COD_CONVENIO=C.COD_CONVENIO  AND COD_CLIENTE=ORIG.COD_CLI_CONV),10,'0')
                                    ELSE 'CR-Venta a credito'
                                    END
             END ASIG_1,
             (
                    DECODE(CP_ORIG.TIP_COMP_PAGO,
                           '02', '01',       -- FACTURA
                           '01', '03',       -- BOLETA
                           '05', '12',       -- TICKET BOLETA
                           'ND'              -- SIN DEFINIR
                           ) ||'-'|| LPAD(SUBSTR(CP_ORIG.NUM_COMP_PAGO,1,3),5,'0') ||'-'|| SUBSTR(CP_ORIG.NUM_COMP_PAGO,-7)
             ) TEX_DET_1,
             '11' CLA_CUE_2,
             'CR' CUE_2,
             ' ' CEN_COSTO,
             ' ' IND_IMP,
             C_C_RT || '-' || C.COD_LOCAL || '-' || TO_CHAR(C.FEC_PED_VTA,'DDMMYYYY') || '/' || 'CREDITOS' TEX_DET_2
      FROM VTA_PEDIDO_VTA_CAB C,
           VTA_PEDIDO_VTA_CAB ORIG,
           VTA_COMP_PAGO CP_ORIG,
           CON_PED_VTA_CLI CONV_ORIG,
           VTA_COMP_PAGO CP_NC,
           MAE_CONVENIO MC
      WHERE C.COD_GRUPO_CIA = ccodgrupocia_in
        AND C.COD_LOCAL = ccodlocal_in
        AND C.FEC_PED_VTA BETWEEN TO_DATE(vFecCierreDia_in,'dd/MM/yyyy') AND TO_DATE(vFecCierreDia_in,'dd/MM/yyyy') + 1 - 1/24/60/60
        AND C.EST_PED_VTA = 'C'

        AND MC.COD_CONVENIO(+)    = C.COD_CONVENIO

        AND EXISTS
            (
             SELECT 1
             FROM VTA_COMP_PAGO CP
             WHERE 1 = 1
              AND CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
              AND CP.COD_LOCAL = C.COD_LOCAL
              AND CP.NUM_PED_VTA = C.NUM_PED_VTA
              AND CP.TIP_COMP_PAGO = '04'
            )
        AND ORIG.COD_GRUPO_CIA = C.COD_GRUPO_CIA
        AND ORIG.COD_LOCAL = C.COD_LOCAL
        AND ORIG.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
        AND ORIG.IND_PED_CONVENIO = 'S'
        AND ORIG.IND_CONV_BTL_MF = 'S'

        AND CP_ORIG.COD_GRUPO_CIA = ORIG.COD_GRUPO_CIA
        AND CP_ORIG.COD_LOCAL = ORIG.COD_LOCAL
        AND CP_ORIG.NUM_PED_VTA = ORIG.NUM_PED_VTA
        AND CP_ORIG.IND_COMP_CREDITO = 'S'
        AND CP_ORIG.TIP_COMP_PAGO != '03'

        AND CONV_ORIG.COD_GRUPO_CIA(+) = ORIG.COD_GRUPO_CIA
        AND CONV_ORIG.COD_LOCAL(+)     = ORIG.COD_LOCAL
        AND CONV_ORIG.NUM_PED_VTA(+)   = ORIG.NUM_PED_VTA

        AND CP_NC.COD_GRUPO_CIA = C.COD_GRUPO_CIA
        AND CP_NC.COD_LOCAL = C.COD_LOCAL
        AND CP_NC.NUM_PED_VTA = C.NUM_PED_VTA
        AND CP_NC.IND_AFECTA_KARDEX = 'S'
      ;

      cCodCia  CHAR(3);

  BEGIN
       DBMS_OUTPUT.PUT_LINE('1. vFecCierreDia_in=' || vFecCierreDia_in);

       SELECT cod_cia into cCodCia
       FROM Pbl_Local WHERE COD_GRUPO_CIA=cCodGrupoCia_in AND COD_lOCAL=cCodLocal_in;

       FOR movCuadratura_rec IN movCuadratura(cCodCia)
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);
	     END LOOP;
  END;

/***************************************************************************************/

--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
--LAS NUEVAS CUADRATURAS AGREGADAS ES PARA CONSIDERAR LOS SGTE  - JCALLO - 28.11.2008*-*-*-*-
-- 1.- q las formas de pago declaradas MENOS la suma de los pedidos cobrados ==> AJUSTE  POR REDONDEO
-- 2.- ajustar la suma de forma de pago declaradas en dolares la conversion a soles se haga con el tipo de cambio de SAP.
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

  --OBTIENE EL QUERY E INSERTA PARA EL TIPO DE CUADRATURA 027
  --Enviar redondeo por forma de pago declarados menos total de ventas del dia
  --Fecha       Usuario		   Comentario
  --28/11/2008  JCALLO       Creación
  PROCEDURE INT_EJECUTA_CUADRATURA_027(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)

  AS

  nSumVenta number:=0;
  nSumRecaudacion number:=0;
  nSumFormaPagoDecl number:=0;
  nSumCuadraturasCaja number:=0;

  nRedondeo number:=0;

  v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;


  vClaseDoc   CE_CUADRATURA.COD_CLASE_DOCUMENTO%TYPE;
  vClaveCta1  CE_CUADRATURA.DESC_CLAVE_CUENTA_1%TYPE;
  vCuenta1    CE_CUADRATURA.DESC_CUENTA_1%TYPE;
  vClaveCta2  CE_CUADRATURA.DESC_CLAVE_CUENTA_2%TYPE;
  vCuenta2    CE_CUADRATURA.DESC_CUENTA_2%TYPE;
  vCentroCosto INT_CAJA_ELECTRONICA.DESC_CENTRO_COSTO%TYPE;

  BEGIN
      --**-*-*-* SUMA DE MONTOS DE PEDIDOS COBRADOS -*-*-*-*-*--

         SELECT
                NVL(SUM(IM_TOTAL_PAGO),0) into nSumRecaudacion
         FROM RCD_RECAUDACION_CAB CAB
                WHERE CAB.COD_GRUPO_CIA = '001'
                AND CAB.COD_LOCAL = cCodLocal_in
                AND CAB.EST_RCD = 'C'
                AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                            TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                                             AND TIPO_RCD !='06';



      SELECT SUM(c.val_neto_ped_vta) into nSumVenta
         FROM vta_pedido_vta_cab c
         WHERE C.COD_GRUPO_CIA = '001'
           AND C.COD_LOCAL = cCodLocal_in--'017'
           AND C.EST_PED_VTA = 'C'
           --AND C.FEC_PED_VTA BETWEEN TO_DATE(vFecCierreDia_in||' 00:00:00','dd/MM/yyyy HH24:MI:SS')  AND TO_DATE(vFecCierreDia_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS');
           AND C.FEC_PED_VTA BETWEEN TO_DATE(vFecCierreDia_in,'DD/MM/YYYY') AND TO_DATE(vFecCierreDia_in,'DD/MM/YYYY') + 1 - 1/24/60/60;
           --*-+--+-+--AND C.FEC_PED_VTA BETWEEN to_date('25/11/2008', 'dd/MM/yyyy') AND+--+-
           ---+-+-     to_date('26/11/2008', 'dd/MM/yyyy');*-*-*-*-*-

      DBMS_OUTPUT.PUT_LINE('nSumVenta : '||nSumVenta);

       DBMS_OUTPUT.PUT_LINE('nSumRecaudacion : '||nSumRecaudacion);

      --**-*-*-* SUMA DE FORMA DE PAGOS DECLARADOS -*-*-*-*-*-*--
      SELECT decode(SUM(CD.MON_ENTREGA_TOTAL),null,0,SUM(CD.MON_ENTREGA_TOTAL))
             --SUM(CD.MON_ENTREGA_TOTAL)
      into nSumFormaPagoDecl
         FROM CE_FORMA_PAGO_ENTREGA CD, CE_MOV_CAJA MC
         WHERE CD.COD_GRUPO_CIA = cCodGrupoCia_in
           and CD.COD_LOCAL = cCodLocal_in--'017'
           AND CD.EST_FORMA_PAGO_ENT = 'A'
           AND MC.COD_GRUPO_CIA = CD.COD_GRUPO_CIA
           AND MC.COD_LOCAL = CD.COD_LOCAL
           AND MC.SEC_MOV_CAJA = CD.SEC_MOV_CAJA
           AND MC.FEC_CIERRE_DIA_VTA = TO_DATE(vFecCierreDia_in,'DD/MM/YYYY');

           DBMS_OUTPUT.PUT_LINE('nSumFormaPagoDecl : '||nSumFormaPagoDecl);

      --**-*-*-* SUMA DE CUADRATURAS DECLARADOS POR LOS CAJEROS -*-*-*-*-*-*--

      SELECT NVL(SUM(CJ.MON_TOTAL*CE.VAL_SIGNO),0) into nSumCuadraturasCaja
      FROM  CE_CUADRATURA_CAJA CJ, CE_MOV_CAJA M, CE_CUADRATURA CE
      WHERE CJ.COD_GRUPO_CIA = M.COD_GRUPO_CIA
      AND   CJ.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
      AND   CJ.COD_LOCAL     = M.COD_LOCAL
      AND   CJ.SEC_MOV_CAJA  = M.SEC_MOV_CAJA
      AND   M.FEC_DIA_VTA    = TO_DATE(vFecCierreDia_in,'DD/MM/YYYY')
      AND   CJ.COD_GRUPO_CIA = CE.COD_GRUPO_CIA
      AND   CJ.COD_CUADRATURA = CE.COD_CUADRATURA;

      DBMS_OUTPUT.PUT_LINE('nSumCuadraturasCaja : '||nSumCuadraturasCaja);

      nRedondeo := nSumFormaPagoDecl + nSumCuadraturasCaja - (nSumVenta+nSumRecaudacion);

      DBMS_OUTPUT.PUT_LINE('nRedondeo : '||nRedondeo);

      IF(nRedondeo >= 0) THEN--NO EMITIR ERROR AL GENERAR ARCHICO SI EL REDONDEO ES MAYOR O IGUAL A CERO

          IF(nRedondeo > 0) THEN--INSERTAR CUADRATURA DE REDONDEO SOLO SI EL REDONDEO ES MAYOR CERO

            ---*-*-* insertando la cuadratura en la tabla de INTERFACE DE CAJA ELECTRONICA *-*-*-*-*
            v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

            ---*-*-*-*OBTENIENDO DATOS DE LA CUADRATURA 27*-*-*-*-
            SELECT NVL(CE.COD_CLASE_DOCUMENTO,' '), NVL(CE.DESC_CLAVE_CUENTA_1,' '), NVL(CE.DESC_CUENTA_1,' '), NVL(CE.DESC_CLAVE_CUENTA_2,' '), NVL(CE.DESC_CUENTA_2,' ')
            INTO   vClaseDoc, vClaveCta1, vCuenta1, vClaveCta2, vCuenta2
            FROM CE_CUADRATURA CE
            WHERE CE.COD_GRUPO_CIA = '001'
            AND   CE.COD_CUADRATURA = '027';

            BEGIN
                SELECT NVL(CECO_LOCAL,' ')
                INTO  vCentroCosto
                FROM AUX_CECO_SAP_LOCAL
                WHERE COD_LOCAL=cCodLocal_in;
            EXCEPTION
                     WHEN OTHERS THEN
                                     vCentroCosto:=' ';
            END;

            IF vCentroCosto IS NULL THEN
               vCentroCosto:=' ';
            END IF;

            --*-*-*-*obteniendo la descripcion de la cuadratura--*-*-*---

            INT_GRABA_INTERFACE_CE('001',--cCodGrupoCia_in    IN CHAR,
                                  cCodLocal_in,--cCodLocal_in       IN CHAR,
                                  vFecCierreDia_in,--vFecOperacion_in   IN CHAR,
                                  '027',--cCodCuadratura_in  IN CHAR,
                                  v_cSecIntCe,  --cSecIntCe_in       IN CHAR,
                                  vClaseDoc,--vClaseDoc_in       IN CHAR,
                                  vFecCierreDia_in,--cFecDocumento_in   IN CHAR,
                                  vFecCierreDia_in,--cFecContable_in    IN CHAR,
                                   ' ',--vDescReferencia_in IN CHAR,
                                   ' ',--cDescTextCab_in    IN CHAR,
                                   TIP_MONEDA_SOLES_SAP,--cTipMoneda_in      IN CHAR,
                                   vClaveCta1,--vClaveCue1_in      IN CHAR,
                                   vCuenta1,--vCuenta1_in        IN CHAR,
                                   ' ',--cMarcaImp_in       IN CHAR,
                                   TO_CHAR(nRedondeo,'999999990.00'),--cValImporte_in     IN CHAR,
                                   ' ',--vDescAsig1_in      IN CHAR,
                                   cCodLocal_in||'-'||TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'DDMMYYYY'),--cDescTextDet1_in   IN CHAR,
                                   vClaveCta2,--cClaveCue2_in      IN CHAR,
                                   vCuenta2,--vCuenta2_in        IN CHAR,
                                   --' ',--vCentroCosto_in    IN CHAR,
                                   vCentroCosto,--vCentroCosto_in    IN CHAR,
                                   ' ',--cIndImp_in         IN CHAR,
                                   C_C_RT ||'-'||cCodLocal_in||'-'||TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'DDMMYYYY'),--cDescTextDet2_in   IN CHAR,
                                   C_C_USU_CREA_INT_CE,--cUsuCreaIntCe_in   IN CHAR,
                                   ' '--CME_in             IN CHAR)
                                  );
          END IF;--FIN DE INSERTAR REGISTRO DE LA CUADRATURA DE REDONDEO

      ELSE -- SI EL REDONDEO ES MENOR A CERO GENERAR ERROR Y NO GENERAR LA INTERFACE
          RAISE_APPLICATION_ERROR(-20050,'<BR>el valor del redondeo es NEGATIVO. <br>suma de forma pago declarados: <b>'||TO_CHAR(nSumFormaPagoDecl,'999999990.00')
          ||'</b> <br> suma de cuadraturas declarados : <b>'||TO_CHAR(nSumCuadraturasCaja,'999999990.00')
          ||'</b> <br><br> suma total de ventas cobrados : <b>'||TO_CHAR(nSumVenta,'999999990.00')
          ||'</b> <br> Redondeo: <b>'||TO_CHAR(nRedondeo,'999999990.00')||'</b>');
      END IF;

  END;

  --OBTIENE EL QUERY E INSERTA PARA EL TIPO DE CUADRATURA 028
  --SUMA DE FORMA DE PAGO DECLARADOS con ajuste del monto de acuerdo al tipo de cambio SAP.
  --Fecha       Usuario		   Comentario
  --28/11/2008  JCALLO       Creación
  PROCEDURE INT_EJECUTA_CUADRATURA_028(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)

  AS

  nCantTipCambioNulos number:=0;--cantidad de registros que tienen tipo de cambio nulo
  nDiferencia number:=0;
  v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

  vClaseDoc   CE_CUADRATURA.COD_CLASE_DOCUMENTO%TYPE;
  vClaveCta1  CE_CUADRATURA.DESC_CLAVE_CUENTA_1%TYPE;
  vCuenta1    CE_CUADRATURA.DESC_CUENTA_1%TYPE;
  vClaveCta2  CE_CUADRATURA.DESC_CLAVE_CUENTA_2%TYPE;
  vCuenta2    CE_CUADRATURA.DESC_CUENTA_2%TYPE;
  v_eControlValidacion EXCEPTION;

  CURSOR cr1  (ccod_local     char,dFecCierreDia  date) IS
  SELECT (--para locales sin remito
           SELECT P.VAL_TIPO_CAMBIO
           FROM ptoventa.CE_TIP_CAMBIO_SAP P
           WHERE P.FEC_TIPO_CAMBIO = ( SELECT MAX(FEC_TIPO_CAMBIO)
                                       FROM ptoventa.CE_TIP_CAMBIO_SAP S
                                       WHERE S.FEC_TIPO_CAMBIO <= trunc(CD.FEC_OPERACION)
                                     )
           AND ( SELECT MAX(S.FEC_CREA_TIPO_CAMBIO)
           FROM ptoventa.CE_TIP_CAMBIO_SAP S
           ) >= trunc(CD.FEC_OPERACION))*CD.MON_MONEDA_ORIGEN AS TOTAL
  FROM CE_CUADRATURA_CIERRE_DIA CD
  WHERE CD.COD_GRUPO_CIA=cCodGrupoCia_in
  AND   CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
  AND   CD.COD_LOCAL=cCodLocal_in
  AND   CD.COD_CUADRATURA = '011'
  AND   CD.TIP_MONEDA = '02'
  AND   CD.FEC_CIERRE_DIA_VTA = trunc(dFecCierreDia)
  UNION ALL
  SELECT --JCORTEZ 03.08.09 Se considera formas de pago entrega relacionadas
         --el sobre de remito. (ciertos locales)
          (SELECT P.VAL_TIPO_CAMBIO
           FROM ptoventa.CE_TIP_CAMBIO_SAP P
           WHERE P.FEC_TIPO_CAMBIO = ( SELECT MAX(FEC_TIPO_CAMBIO)
                                       FROM ptoventa.CE_TIP_CAMBIO_SAP S
                                       WHERE S.FEC_TIPO_CAMBIO <= trunc(S.FEC_DIA_VTA)
                                     )
           AND ( SELECT MAX(S.FEC_CREA_TIPO_CAMBIO)
           FROM ptoventa.CE_TIP_CAMBIO_SAP S
           ) >= trunc(S.FEC_DIA_VTA)
          )*FPE.MON_ENTREGA AS TOTAL
  FROM CE_FORMA_PAGO_ENTREGA FPE,
       CE_SOBRE S
  WHERE  FPE.COD_GRUPO_CIA=cCodGrupoCia_in
    AND S.FEC_DIA_VTA= trunc(dFecCierreDia)
    AND S.COD_LOCAL=ccod_local
    AND S.ESTADO=ESTADO_ACTIVO
    AND FPE.COD_FORMA_PAGO = '00002'
    -- cambio de sobres
    -- dubilluz 10.08.2010
    -- INICIO
    AND   'S' = (
                 SELECT NVL(L.IND_PROSEGUR,'N')
                 FROM   PBL_LOCAL L
                 WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND    L.COD_LOCAL     = ccod_local
                 )
    -- FIN
    AND FPE.COD_GRUPO_CIA=S.COD_GRUPO_CIA
    AND FPE.COD_LOCAL=S.COD_LOCAL
    AND FPE.SEC_MOV_CAJA = S.SEC_MOV_CAJA
    AND FPE.SEC_FORMA_PAGO_ENTREGA = S.SEC_FORMA_PAGO_ENTREGA;

  r1 cr1%rowtype;
  --*-*-*-**-*

  nSumaSap number:=0;
  nSumaRecSap number:=0;
  nSumaMF number:=0;
  nSumaRecMF number:=0;
  nCantRec number:=0;

  BEGIN


         SELECT COUNT(1) INTO nCantRec
                  FROM RCD_RECAUDACION_CAB CAB
                  WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND CAB.COD_LOCAL = cCodLocal_in
                  AND CAB.EST_RCD = 'C'
                  AND CAB.TIP_MONEDA='02'
                  AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                  TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                  AND TIPO_RCD !='06';


       IF nCantRec>0 THEN
           SELECT
                  SUM(TOTAL*TIPO_CAMBIO) INTO nSumaRecSap
           FROM (
                      SELECT NVL(IM_TOTAL,0) TOTAL,
                            (SELECT P.VAL_TIPO_CAMBIO
                            FROM ptoventa.CE_TIP_CAMBIO_SAP P
                            WHERE P.FEC_TIPO_CAMBIO = ( SELECT MAX(FEC_TIPO_CAMBIO)
                            FROM ptoventa.CE_TIP_CAMBIO_SAP S
                            WHERE S.FEC_TIPO_CAMBIO <= trunc(CAB.FEC_CREA_RECAU_PAGO)
                            )
                            AND ( SELECT MAX(S.FEC_CREA_TIPO_CAMBIO)
                            FROM ptoventa.CE_TIP_CAMBIO_SAP S
                            ) >= trunc(CAB.FEC_CREA_RECAU_PAGO)
                            ) TIPO_CAMBIO
                      FROM RCD_RECAUDACION_CAB CAB
                      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND CAB.COD_LOCAL = cCodLocal_in
                      AND CAB.EST_RCD = 'C'
                      AND CAB.TIP_MONEDA='02'
                      AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                      TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                      AND TIPO_RCD !='06'
                );

           IF nSumaRecSap IS NULL THEN
              DBMS_OUTPUT.PUT_LINE('FALTA TIPO DE CAMBIO DEL SAP');
              RAISE v_eControlValidacion;
           END IF;
       END IF;

       OPEN cr1(cCodLocal_in ,TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'));--CURSOR DE DEPOSITOS DECLARADOS EN DOLARES
       LOOP FETCH cr1 INTO r1;
            EXIT WHEN cr1%NOTFOUND;

            DBMS_OUTPUT.PUT_LINE('r1.TOTAL : '||r1.TOTAL);

            IF r1.TOTAL IS NULL THEN
                RAISE_APPLICATION_ERROR(-20055,'<BR>No se tiene el tipo de cambio SAP para alguna(s) fecha(s) de deposito declarado(s) para la generacion de la interfase de caja electronica del dia '||to_char(vFecCierreDia_in,'dd/mm/yyyy'));
            ELSE
                nSumaSap:= nSumaSap + r1.TOTAL;
            END IF;

       END LOOP;


       IF (nSumaSap IS NULL OR nSumaSap = 0) and nSumaRecSap=0 THEN
          DBMS_OUTPUT.PUT_LINE('NO HAY NINGUN REGISTRO DE DEPOSITO DECLARADO EN DOLARES');
          RETURN;
       END IF;

       DBMS_OUTPUT.PUT_LINE('nSumaSap : '||nSumaSap);
       DBMS_OUTPUT.PUT_LINE('nSumaRecSap : '||nSumaRecSap);

      --*-*-*--OBTENIENDO LA SUMA TOTAL DE TIPO CAMBIO MIFARMA--*--*-*-*-**
      SELECT SUM(V1.TOTAL) INTO nSumaMF
       FROM (--para local sin remito (soles)
        SELECT NVL(SUM(CD.MON_TOTAL),0) AS TOTAL
         FROM CE_CUADRATURA_CIERRE_DIA CD
         WHERE CD.COD_CUADRATURA = '011'
          AND  CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
          AND  CD.TIP_MONEDA = '02'
          AND  CD.FEC_CIERRE_DIA_VTA = TO_DATE(vFecCierreDia_in,'dd/MM/yyyy')
          AND  CD.COD_LOCAL = cCodLocal_in
        UNION --JCORTEZ 03.08.09 para local con remito, considerando el total calculado. (soles)
        SELECT NVL(SUM(FPE.MON_ENTREGA_TOTAL),0) AS TOTAL
         FROM CE_FORMA_PAGO_ENTREGA FPE,
              CE_SOBRE S
        WHERE  FPE.COD_GRUPO_CIA=cCodGrupoCia_in
          AND S.FEC_DIA_VTA=  TO_DATE(vFecCierreDia_in,'dd/MM/yyyy')
          AND S.COD_LOCAL=cCodLocal_in
          AND S.ESTADO=ESTADO_ACTIVO
          AND FPE.COD_FORMA_PAGO = '00002'
          -- cambio de sobres
          -- dubilluz 10.08.2010
          -- INICIO
          AND   'S' = (
                       SELECT NVL(L.IND_PROSEGUR,'N')
                       FROM   PBL_LOCAL L
                       WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND    L.COD_LOCAL     = cCodLocal_in
                       )
          -- FIN
          AND FPE.COD_GRUPO_CIA=S.COD_GRUPO_CIA
          AND FPE.COD_LOCAL=S.COD_LOCAL
          AND FPE.SEC_MOV_CAJA = S.SEC_MOV_CAJA
          AND FPE.SEC_FORMA_PAGO_ENTREGA = S.SEC_FORMA_PAGO_ENTREGA)V1;



         SELECT
                NVL(SUM(IM_TOTAL_PAGO),0) into nSumaRecMF
         FROM RCD_RECAUDACION_CAB CAB
                WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                AND CAB.COD_LOCAL = cCodLocal_in
                AND CAB.EST_RCD = 'C'
                AND CAB.TIP_MONEDA='02'
                AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                            TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                                             AND TIPO_RCD !='06';


      DBMS_OUTPUT.PUT_LINE('nSumaMF : '||nSumaMF);
      DBMS_OUTPUT.PUT_LINE('nSumaRecMF : '||nSumaRecMF);

      --nDiferencia := (nSumaRecSap+nSumaSap) - (nSumaMF+nSumaRecMF);
       nDiferencia := ((-1*nSumaRecSap)+nSumaSap) - (nSumaMF+(nSumaRecMF*-1));


      DBMS_OUTPUT.PUT_LINE('nDiferencia : '||nDiferencia);

      --*-*-*-*INSERTANDO LA CUADRATURA EN LA TABLA DE INTERFACE DE CAJA ELECTRONICA*-*-*-*-

      IF ( nDiferencia <0 ) THEN--si la diferencia es NEGATIVO tonces se generaria ganancia con el tipo de cambio SAP
        --valor igual a cero no se envia --julio lo definio

          --*-*-*-*OBTENIENDO DATOS DE LA CUADRATURA 28**-*---*-*-
          SELECT NVL(CE.COD_CLASE_DOCUMENTO,' '), NVL(CE.DESC_CLAVE_CUENTA_1,' '), NVL(CE.DESC_CUENTA_1,' '), NVL(CE.DESC_CLAVE_CUENTA_2,' '), NVL(CE.DESC_CUENTA_2,' ')
          INTO   vClaseDoc, vClaveCta1, vCuenta1, vClaveCta2, vCuenta2
          FROM CE_CUADRATURA CE
          WHERE CE.COD_GRUPO_CIA = '001'
          AND   CE.COD_CUADRATURA = '028';


          --*-*-*- insertando la cuadratura en la tablA de INTERFACE DE CAJA ELECTRONICA *-*-*-*-*
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE('001',--cCodGrupoCia_in    IN CHAR,
                                  cCodLocal_in,--cCodLocal_in       IN CHAR,
                                  vFecCierreDia_in,--vFecOperacion_in   IN CHAR,
                                  '028',--cCodCuadratura_in  IN CHAR,
                                  v_cSecIntCe,  --cSecIntCe_in       IN CHAR,
                                  vClaseDoc,--vClaseDoc_in       IN CHAR,
                                  vFecCierreDia_in,--cFecDocumento_in   IN CHAR,
                                  vFecCierreDia_in,--cFecContable_in    IN CHAR,
                                   ' ',--vDescReferencia_in IN CHAR,
                                   ' ',--cDescTextCab_in    IN CHAR,
                                   TIP_MONEDA_SOLES_SAP,--cTipMoneda_in      IN CHAR,
                                   vClaveCta1,--vClaveCue1_in      IN CHAR,
                                   vCuenta1,--vCuenta1_in        IN CHAR,
                                   ' ',--cMarcaImp_in       IN CHAR,
                                   TO_CHAR(nDiferencia * -1,'999999990.00'),--cValImporte_in     IN CHAR,
                                   ' ',--vDescAsig1_in      IN CHAR,
                                   cCodLocal_in||'-'||TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'DDMMYYYY'),--cDescTextDet1_in   IN CHAR,
                                   vClaveCta2,--cClaveCue2_in      IN CHAR,
                                   vCuenta2,--vCuenta2_in        IN CHAR,
                                   ' ',--vCentroCosto_in    IN CHAR,
                                   ' ',--cIndImp_in         IN CHAR,
                                   C_C_RT ||'-'||cCodLocal_in||'-'||TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'DDMMYYYY'),--cDescTextDet2_in   IN CHAR,
                                   C_C_USU_CREA_INT_CE,--cUsuCreaIntCe_in   IN CHAR,
                                   ' '--CME_in             IN CHAR)
                                  );



        END IF;

     EXCEPTION
        WHEN  OTHERS THEN
        RAISE_APPLICATION_ERROR(-20055,'<BR>No se tiene el tipo de cambio SAP para la interfase de caja electronica del dia '||to_char(vFecCierreDia_in,'dd/mm/yyyy'));
  END;

  --OBTIENE EL QUERY E INSERTA PARA EL TIPO DE CUADRATURA 029
  --SUMA DE FORMA DE PAGO DECLARADOS con ajuste del monto de acuerdo al tipo de cambio SAP.
  --Fecha       Usuario		   Comentario
  --28/11/2008  JCALLO       Creación
  PROCEDURE INT_EJECUTA_CUADRATURA_029(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)

  AS
  nCantTipCambioNulos number:=0;--cantidad de registros que tienen tipo de cambio nulo
  nDiferencia number:=0;
  v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

  vClaseDoc   CE_CUADRATURA.COD_CLASE_DOCUMENTO%TYPE;
  vClaveCta1  CE_CUADRATURA.DESC_CLAVE_CUENTA_1%TYPE;
  vCuenta1    CE_CUADRATURA.DESC_CUENTA_1%TYPE;
  vClaveCta2  CE_CUADRATURA.DESC_CLAVE_CUENTA_2%TYPE;
  vCuenta2    CE_CUADRATURA.DESC_CUENTA_2%TYPE;
  v_eControlValidacion EXCEPTION;

  CURSOR cr1  (ccod_local     char,dFecCierreDia  date) IS
  SELECT (--para locales sin remito
           SELECT P.VAL_TIPO_CAMBIO
           FROM ptoventa.CE_TIP_CAMBIO_SAP P
           WHERE P.FEC_TIPO_CAMBIO = ( SELECT MAX(FEC_TIPO_CAMBIO)
                                       FROM ptoventa.CE_TIP_CAMBIO_SAP S
                                       WHERE S.FEC_TIPO_CAMBIO <= trunc(CD.FEC_OPERACION)
                                     )
           AND ( SELECT MAX(S.FEC_CREA_TIPO_CAMBIO)
           FROM ptoventa.CE_TIP_CAMBIO_SAP S
           ) >= trunc(CD.FEC_OPERACION))*CD.MON_MONEDA_ORIGEN AS TOTAL
  FROM CE_CUADRATURA_CIERRE_DIA CD
  WHERE CD.COD_GRUPO_CIA=cCodGrupoCia_in
  AND   CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
  AND   CD.COD_LOCAL=cCodLocal_in
  AND   CD.COD_CUADRATURA = '011'
  AND   CD.TIP_MONEDA = '02'
  AND   CD.FEC_CIERRE_DIA_VTA = trunc(dFecCierreDia)
  UNION ALL
  SELECT --JCORTEZ 03.08.09 Se considera formas de pago entrega relacionadas
         --el sobre de remito. (ciertos locales)
          (SELECT P.VAL_TIPO_CAMBIO
           FROM ptoventa.CE_TIP_CAMBIO_SAP P
           WHERE P.FEC_TIPO_CAMBIO = ( SELECT MAX(FEC_TIPO_CAMBIO)
                                       FROM ptoventa.CE_TIP_CAMBIO_SAP S
                                       WHERE S.FEC_TIPO_CAMBIO <= trunc(S.FEC_DIA_VTA)
                                     )
           AND ( SELECT MAX(S.FEC_CREA_TIPO_CAMBIO)
           FROM ptoventa.CE_TIP_CAMBIO_SAP S
           ) >= trunc(S.FEC_DIA_VTA)
          )*FPE.MON_ENTREGA AS TOTAL
  FROM CE_FORMA_PAGO_ENTREGA FPE,
       CE_SOBRE S
  WHERE  FPE.COD_GRUPO_CIA=cCodGrupoCia_in
    AND S.FEC_DIA_VTA= trunc(dFecCierreDia)
    AND S.COD_LOCAL=ccod_local
    AND S.ESTADO=ESTADO_ACTIVO
    AND FPE.COD_FORMA_PAGO = '00002'
    -- cambio de sobres
    -- dubilluz 10.08.2010
    -- INICIO
    AND   'S' = (
                 SELECT NVL(L.IND_PROSEGUR,'N')
                 FROM   PBL_LOCAL L
                 WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND    L.COD_LOCAL     = ccod_local
                 )
    -- FIN
    AND FPE.COD_GRUPO_CIA=S.COD_GRUPO_CIA
    AND FPE.COD_LOCAL=S.COD_LOCAL
    AND FPE.SEC_MOV_CAJA = S.SEC_MOV_CAJA
    AND FPE.SEC_FORMA_PAGO_ENTREGA = S.SEC_FORMA_PAGO_ENTREGA;

  r1 cr1%rowtype;
  --*-*-*-**-*

  nSumaSap number:=0;
  nSumaRecSap number:=0;
  nSumaMF number:=0;
  nSumaRecMF number:=0;
  nCantRec number:=0;

  BEGIN

        SELECT COUNT(1) INTO nCantRec
                  FROM RCD_RECAUDACION_CAB CAB
                  WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND CAB.COD_LOCAL = cCodLocal_in
                  AND CAB.EST_RCD = 'C'
                  AND CAB.TIP_MONEDA='02'
                  AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                  TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                  AND TIPO_RCD !='06';

       IF nCantRec>0 THEN
           SELECT
                  SUM(TOTAL*TIPO_CAMBIO) INTO nSumaRecSap
           FROM (
                      SELECT NVL(IM_TOTAL,0) TOTAL,
                            (SELECT P.VAL_TIPO_CAMBIO
                            FROM ptoventa.CE_TIP_CAMBIO_SAP P
                            WHERE P.FEC_TIPO_CAMBIO = ( SELECT MAX(FEC_TIPO_CAMBIO)
                            FROM ptoventa.CE_TIP_CAMBIO_SAP S
                            WHERE S.FEC_TIPO_CAMBIO <= trunc(CAB.FEC_CREA_RECAU_PAGO)
                            )
                            AND ( SELECT MAX(S.FEC_CREA_TIPO_CAMBIO)
                            FROM ptoventa.CE_TIP_CAMBIO_SAP S
                            ) >= trunc(CAB.FEC_CREA_RECAU_PAGO)
                            ) TIPO_CAMBIO
                      FROM RCD_RECAUDACION_CAB CAB
                      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND CAB.COD_LOCAL = cCodLocal_in
                      AND CAB.EST_RCD = 'C'
                      AND CAB.TIP_MONEDA='02'
                      AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                      TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                      AND TIPO_RCD !='06'
                );

           IF nSumaRecSap IS NULL THEN
              DBMS_OUTPUT.PUT_LINE('FALTA TIPO DE CAMBIO DEL SAP');
              raise v_eControlValidacion;
           END IF;
       END IF;

       OPEN cr1(cCodLocal_in ,TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'));--CURSOR DE DEPOSITOS DECLARADOS EN DOLARES
       LOOP FETCH cr1 INTO r1;
            EXIT WHEN cr1%NOTFOUND;

            DBMS_OUTPUT.PUT_LINE('r1.TOTAL : '||r1.TOTAL);

            IF r1.TOTAL IS NULL THEN
                RAISE_APPLICATION_ERROR(-20055,'<BR>No se tiene el tipo de cambio SAP para alguna(s) fecha(s) de deposito declarado(s) para la generacion de la interfase de caja electronica del dia '||to_char(vFecCierreDia_in,'dd/mm/yyyy'));
            ELSE
                nSumaSap:= nSumaSap + r1.TOTAL;
            END IF;

       END LOOP;


       IF (nSumaSap IS NULL OR nSumaSap = 0) AND nSumaRecSap=0 THEN
          DBMS_OUTPUT.PUT_LINE('NO HAY NINGUN REGISTRO DE DEPOSITO DECLARADO EN DOLARES');
          RETURN;
       END IF;


       DBMS_OUTPUT.PUT_LINE('nSumaSap : '||nSumaSap);
       DBMS_OUTPUT.PUT_LINE('nSumaRecSap : '||nSumaRecSap);

      --*-*-*--OBTENIENDO LA SUMA TOTAL DE TIPO CAMBIO MIFARMA--*--*-*-*-**
       SELECT SUM(V1.TOTAL) INTO nSumaMF
       FROM (--para local sin remito (soles)
         SELECT NVL(SUM(CD.MON_TOTAL),0) AS TOTAL
         FROM CE_CUADRATURA_CIERRE_DIA CD
         WHERE CD.COD_CUADRATURA = '011'
          AND  CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
          AND  CD.TIP_MONEDA = '02'
          AND  CD.FEC_CIERRE_DIA_VTA = TO_DATE(vFecCierreDia_in,'dd/MM/yyyy')
          AND  CD.COD_LOCAL = cCodLocal_in
        UNION --JCORTEZ 03.08.09 para local con remito, considerando el total calculado. (soles)
        SELECT NVL(SUM(FPE.MON_ENTREGA_TOTAL),0) AS TOTAL
         FROM CE_FORMA_PAGO_ENTREGA FPE,
              CE_SOBRE S
        WHERE  FPE.COD_GRUPO_CIA=cCodGrupoCia_in
          AND S.FEC_DIA_VTA=  TO_DATE(vFecCierreDia_in,'dd/MM/yyyy')
          AND S.COD_LOCAL=cCodLocal_in
          AND S.ESTADO=ESTADO_ACTIVO
          AND FPE.COD_FORMA_PAGO = '00002'
          -- cambio de sobres
          -- dubilluz 10.08.2010
          -- INICIO
          AND   'S' = (
          		 SELECT NVL(L.IND_PROSEGUR,'N')
          		 FROM   PBL_LOCAL L
          		 WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
          		 AND    L.COD_LOCAL     = cCodLocal_in
          		 )
          -- FIN
          AND FPE.COD_GRUPO_CIA=S.COD_GRUPO_CIA
          AND FPE.COD_LOCAL=S.COD_LOCAL
          AND FPE.SEC_MOV_CAJA = S.SEC_MOV_CAJA
          AND FPE.SEC_FORMA_PAGO_ENTREGA = S.SEC_FORMA_PAGO_ENTREGA)V1;


           SELECT
                NVL(SUM(IM_TOTAL_PAGO),0) into nSumaRecMF
         FROM RCD_RECAUDACION_CAB CAB
                WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                AND CAB.COD_LOCAL = cCodLocal_in
                AND CAB.EST_RCD = 'C'
                AND CAB.TIP_MONEDA='02'
                AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                            TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                                             AND TIPO_RCD !='06';

      DBMS_OUTPUT.PUT_LINE('nSumaMF : '||nSumaMF);
      DBMS_OUTPUT.PUT_LINE('nSumaRecMF : '||nSumaRecMF);


      nDiferencia := ((-1*nSumaRecSap)+nSumaSap) - (nSumaMF+(nSumaRecMF*-1));

      DBMS_OUTPUT.PUT_LINE('nDiferencia : '||nDiferencia);

      --*-*-*-*INSERTANDO LA CUADRATURA EN LA TABLA DE INTERFACE DE CAJA ELECTRONICA*-*-*-*-

      IF ( nDiferencia > 0 ) THEN--si la diferencia es NEGATIVO tonces se generaria ganancia con el tipo de cambio SAP
        --valor igual a cero no se envia --julio lo definio

          --*-*-*-*OBTENIENDO DATOS DE LA CUADRATURA 28**-*---*-*-
          SELECT NVL(CE.COD_CLASE_DOCUMENTO,' '), NVL(CE.DESC_CLAVE_CUENTA_1,' '), NVL(CE.DESC_CUENTA_1,' '), NVL(CE.DESC_CLAVE_CUENTA_2,' '), NVL(CE.DESC_CUENTA_2,' ')
          INTO   vClaseDoc, vClaveCta1, vCuenta1, vClaveCta2, vCuenta2
          FROM CE_CUADRATURA CE
          WHERE CE.COD_GRUPO_CIA = '001'
          AND   CE.COD_CUADRATURA = '029';


          --*-*-*- insertando la cuadratura en la tablA de INTERFACE DE CAJA ELECTRONICA *-*-*-*-*
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE('001',--cCodGrupoCia_in    IN CHAR,
                                  cCodLocal_in,--cCodLocal_in       IN CHAR,
                                  vFecCierreDia_in,--vFecOperacion_in   IN CHAR,
                                  '029',--cCodCuadratura_in  IN CHAR,
                                  v_cSecIntCe,  --cSecIntCe_in       IN CHAR,
                                  vClaseDoc,--vClaseDoc_in       IN CHAR,
                                  vFecCierreDia_in,--cFecDocumento_in   IN CHAR,
                                  vFecCierreDia_in,--cFecContable_in    IN CHAR,
                                   ' ',--vDescReferencia_in IN CHAR,
                                   ' ',--cDescTextCab_in    IN CHAR,
                                   TIP_MONEDA_SOLES_SAP,--cTipMoneda_in      IN CHAR,
                                   vClaveCta1,--vClaveCue1_in      IN CHAR,
                                   vCuenta1,--vCuenta1_in        IN CHAR,
                                   ' ',--cMarcaImp_in       IN CHAR,
                                   TO_CHAR(nDiferencia,'999999990.00'),--cValImporte_in     IN CHAR,
                                   ' ',--vDescAsig1_in      IN CHAR,
                                   cCodLocal_in||'-'||TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'DDMMYYYY'),--cDescTextDet1_in   IN CHAR,
                                   vClaveCta2,--cClaveCue2_in      IN CHAR,
                                   vCuenta2,--vCuenta2_in        IN CHAR,
                                   ' ',--vCentroCosto_in    IN CHAR,
                                   ' ',--cIndImp_in         IN CHAR,
                                   C_C_RT ||'-'||cCodLocal_in||'-'||TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'DDMMYYYY'),--cDescTextDet2_in   IN CHAR,
                                   C_C_USU_CREA_INT_CE,--cUsuCreaIntCe_in   IN CHAR,
                                   ' '--CME_in             IN CHAR)
                                  );



        END IF;

     EXCEPTION
        WHEN  OTHERS THEN
        RAISE_APPLICATION_ERROR(-20055,'<BR>No se tiene el tipo de cambio SAP para la generacion de la interfase de caja electronica del dia '||to_char(vFecCierreDia_in,'dd/mm/yyyy'));
        --DBMS_OUTPUT.PUT_LINE('ERROR DESCONOCIDO :');
  END;


  --OBTIENE EL QUERY E INSERTA PARA EL TIPO DE CUADRATURA 030 --OTROS INGRESOS
  --Enviar diferencia entre los montos de efectivo recaudado y  el efectivo depositado,es decir si  depositan dinero de más porque les sobro, esta diferencia debe ir a la Cta. 7680000000
  --Fecha       Usuario		   Comentario
  --30/12/2008  JCALLO       Creación
  PROCEDURE INT_EJECUTA_CUADRATURA_030(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)

  AS

  nSumEfecRecaudado number:=0;
  nSumEfecRendido   number:=0;
  nDiferencia       number:=0;

  v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;


  vClaseDoc   CE_CUADRATURA.COD_CLASE_DOCUMENTO%TYPE;
  vClaveCta1  CE_CUADRATURA.DESC_CLAVE_CUENTA_1%TYPE;
  vCuenta1    CE_CUADRATURA.DESC_CUENTA_1%TYPE;
  vClaveCta2  CE_CUADRATURA.DESC_CLAVE_CUENTA_2%TYPE;
  vCuenta2    CE_CUADRATURA.DESC_CUENTA_2%TYPE;

  BEGIN
      --**-*-*-* SUMA de montos en efectivo recaudados -*-*-*-*-*--
      SELECT nvl(SUM(FPE.MON_ENTREGA_TOTAL),0) into nSumEfecRecaudado
      FROM CE_FORMA_PAGO_ENTREGA FPE, CE_MOV_CAJA MC, VTA_FORMA_PAGO FP
      WHERE FPE.COD_GRUPO_CIA = cCodGrupoCia_in
      AND FPE.COD_LOCAL = cCodLocal_in
      AND FPE.EST_FORMA_PAGO_ENT = 'A'
      AND MC.COD_GRUPO_CIA = FPE.COD_GRUPO_CIA
      AND MC.COD_LOCAL = FPE.COD_LOCAL
      AND MC.SEC_MOV_CAJA = FPE.SEC_MOV_CAJA
      AND MC.FEC_CIERRE_DIA_VTA = TO_DATE(vFecCierreDia_in,'dd/MM/yyyy')
      AND FPE.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
      AND FPE.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
      AND FP.IND_FORMA_PAGO_EFECTIVO = INDICADOR_SI;

      DBMS_OUTPUT.PUT_LINE('nSumEfecRecaudado : '||nSumEfecRecaudado);

      --**-*-*-* EFECTIVO RENDIDO -*-*-*-*-*-*--
      SELECT NVL( SUM( DECODE(CCD.COD_CUADRATURA,C_C_DELIVERY_PERDIDO,CCD.MON_PERDIDO_TOTAL,
                      DECODE(CCD.MON_PARCIAL,
                             CCD.MON_TOTAL,
                             CCD.MON_TOTAL,
                             CCD.MON_PARCIAL * DECODE(CCD.TIP_MONEDA,
                             TIP_MONEDA_DOLARES,DV.TIP_CAMBIO_CIERRE_DIA,1)))
                ) ,0) INTO nSumEfecRendido

        FROM   CE_CUADRATURA_CIERRE_DIA CCD,
               CE_CUADRATURA C,
               CE_CIERRE_DIA_VENTA DV
        WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CCD.COD_LOCAL = cCodLocal_in
        AND    CCD.EST_CUADRAT_C_DIA='A'
        AND    CCD.FEC_CIERRE_DIA_VTA = TO_DATE(vFecCierreDia_in,'dd/MM/yyyy')
        AND    CCD.COD_GRUPO_CIA = C.COD_GRUPO_CIA
        AND    CCD.COD_CUADRATURA = C.COD_CUADRATURA
        AND    DV.COD_GRUPO_CIA = CCD.COD_GRUPO_CIA
        AND    DV.COD_LOCAL = CCD.COD_LOCAL
        AND    DV.FEC_CIERRE_DIA_VTA = CCD.FEC_CIERRE_DIA_VTA;

      DBMS_OUTPUT.PUT_LINE('nSumEfecRendido : '||nSumEfecRendido);

      nDiferencia := nSumEfecRendido - nSumEfecRecaudado;

      DBMS_OUTPUT.PUT_LINE('nDiferencia : '||nDiferencia);

      IF(nDiferencia > 0) THEN--INSERTAR CUADRATURA otros ingresos

            ---*-*-* insertando la cuadratura en la tabla de INTERFACE DE CAJA ELECTRONICA *-*-*-*-*
            v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

            ---*-*-*-*OBTENIENDO DATOS DE LA CUADRATURA 27*-*-*-*-
            SELECT NVL(CE.COD_CLASE_DOCUMENTO,' '), NVL(CE.DESC_CLAVE_CUENTA_1,' '), NVL(CE.DESC_CUENTA_1,' '), NVL(CE.DESC_CLAVE_CUENTA_2,' '), NVL(CE.DESC_CUENTA_2,' ')
            INTO   vClaseDoc, vClaveCta1, vCuenta1, vClaveCta2, vCuenta2
            FROM  CE_CUADRATURA CE
            WHERE CE.COD_GRUPO_CIA = '001'
            AND   CE.COD_CUADRATURA = '030';

            --*-*-*-*obteniendo la descripcion de la cuadratura--*-*-*---

            INT_GRABA_INTERFACE_CE('001',--cCodGrupoCia_in    IN CHAR,
                                  cCodLocal_in,--cCodLocal_in       IN CHAR,
                                  vFecCierreDia_in,--vFecOperacion_in   IN CHAR,
                                  '030',--cCodCuadratura_in  IN CHAR,
                                  v_cSecIntCe,  --cSecIntCe_in       IN CHAR,
                                  vClaseDoc,--vClaseDoc_in       IN CHAR,
                                  vFecCierreDia_in,--cFecDocumento_in   IN CHAR,
                                  vFecCierreDia_in,--cFecContable_in    IN CHAR,
                                   ' ',--vDescReferencia_in IN CHAR,
                                   ' ',--cDescTextCab_in    IN CHAR,
                                   TIP_MONEDA_SOLES_SAP,--cTipMoneda_in      IN CHAR,
                                   vClaveCta1,--vClaveCue1_in      IN CHAR,
                                   vCuenta1,--vCuenta1_in        IN CHAR,
                                   ' ',--cMarcaImp_in       IN CHAR,
                                   TO_CHAR(nDiferencia,'999999990.00'),--cValImporte_in     IN CHAR,
                                   ' ',--vDescAsig1_in      IN CHAR,
                                   cCodLocal_in||'-'||TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'DDMMYYYY'),--cDescTextDet1_in   IN CHAR,
                                   vClaveCta2,--cClaveCue2_in      IN CHAR,
                                   vCuenta2,--vCuenta2_in        IN CHAR,
                                   ' ',--vCentroCosto_in    IN CHAR,
                                   ' ',--cIndImp_in         IN CHAR,
                                   C_C_RT ||'-'||cCodLocal_in||'-'||TO_CHAR(TO_DATE(vFecCierreDia_in,'dd/MM/yyyy'),'DDMMYYYY'),--cDescTextDet2_in   IN CHAR,
                                   C_C_USU_CREA_INT_CE,--cUsuCreaIntCe_in   IN CHAR,
                                   ' '--CME_in             IN CHAR)
                                  );
       END IF;--FIN DE INSERTAR REGISTRO DE LA CUADRATURA DE REDONDEO


  END;
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
--             FIN DE NUEVAS CUADRATURAS                                      *-*-*-*-
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
--             INICIO DE CUADRATURAS DE RECAUDACIONES                         *-*-*-*-
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

--CUADRATURA 039 - EXTORNO CLARO
PROCEDURE INT_EJECUTA_CUADRATURA_039(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT
          CAB.COD_GRUPO_CIA,
          CAB.COD_LOCAL,
          TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA,
          '039' TIPO_OPERACION ,
          (SELECT COD_CLASE_DOCUMENTO from CE_CUADRATURA where COD_CUADRATURA='039')  Clase_Doc,
          TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_DOC,
          TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_CON,
          'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') REFERENCIA,
          'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_CAB,
          CASE CAB.TIP_MONEDA
          WHEN '01' THEN 'PEN'
          WHEN '02' THEN 'USD'
          END MONEDA,
          (SELECT desc_clave_cuenta_1 FROM CE_CUADRATURA WHERE COD_CUADRATURA='039' ) CLA_CUE_1,
          (SELECT desc_cuenta_1 FROM CE_CUADRATURA WHERE cod_cuadratura='039') CUE_1,
          ' ' IMPUESTO ,
          TO_CHAR(ABS(CAB.IM_TOTAL),'999999990.00') IMPORTE,
          'CL'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') ASIG_1,
          SUBSTR('RT'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY')||'/'||(SELECT DESC_CUADRATURA FROM ce_cuadratura WHERE COD_CUADRATURA='039'),1,50)   TEX_DET_1,
          (SELECT desc_clave_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='039') CLA_CUE_2,
          (SELECT desc_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='039') CUE_2,
          ' ' CEN_COSTO,
          ' ' IND_IMP,
          'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_DET_2 ,
          ' ' CME
          FROM RCD_RECAUDACION_CAB CAB
          WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
          AND CAB.COD_LOCAL = cCodLocal_in
          AND CAB.EST_RCD = 'C'
          AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                      TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
          and CAB.cod_autorizacion is not null
          and CAB.cod_recau_anul_ref is not null
          AND CAB.TIPO_RCD=IND_RECAU_CLARO;


  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;


  --CUADRATURA 040 - PAGO CLARO
  PROCEDURE INT_EJECUTA_CUADRATURA_040(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT
        CAB.COD_GRUPO_CIA,
        CAB.COD_LOCAL,
        TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA,
        '040' TIPO_OPERACION ,
        (SELECT COD_CLASE_DOCUMENTO from CE_CUADRATURA where COD_CUADRATURA='040')  Clase_Doc,
        TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_DOC,
        TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_CON,
        'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') REFERENCIA,
        'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_CAB,
        CASE CAB.TIP_MONEDA
        WHEN '01' THEN 'PEN'
        WHEN '02' THEN 'USD'
        END MONEDA,
        (SELECT desc_clave_cuenta_1 FROM CE_CUADRATURA WHERE COD_CUADRATURA='040' ) CLA_CUE_1,
        (SELECT desc_cuenta_1 FROM CE_CUADRATURA WHERE cod_cuadratura='040') CUE_1,
        ' ' IMPUESTO ,
        TO_CHAR(ABS(CAB.IM_TOTAL),'999999990.00') IMPORTE,
        'CL'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') ASIG_1,
        SUBSTR('RT'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY')||'/'||(SELECT DESC_CUADRATURA FROM ce_cuadratura WHERE COD_CUADRATURA='040'),1,50)   TEX_DET_1,
        (SELECT desc_clave_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='040') CLA_CUE_2,
        (SELECT desc_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='040') CUE_2,
        ' ' CEN_COSTO,
        ' ' IND_IMP,
        'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_DET_2 ,
        ' ' CME
        FROM RCD_RECAUDACION_CAB CAB
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CAB.COD_LOCAL = cCodLocal_in
        AND CAB.EST_RCD = 'C'
        AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                    TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND CAB.COD_RECAU_ANUL_REF IS NULL
        --AND CAB.IND_ANUL IS NULL
        AND CAB.TIPO_RCD=IND_RECAU_CLARO;

  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;


--CUADRATURA 040 - RECAUDACION CITIBANK
PROCEDURE INT_EJECUTA_CUADRATURA_556(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
    SELECT
          CAB.COD_GRUPO_CIA,
          CAB.COD_LOCAL,
          TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA,
          '040' TIPO_OPERACION ,
          (SELECT COD_CLASE_DOCUMENTO from CE_CUADRATURA where COD_CUADRATURA='556')  Clase_Doc,
          TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_DOC,
          TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_CON,
          'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') REFERENCIA,
          'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_CAB,
          CASE CAB.TIP_MONEDA
          WHEN '01' THEN 'PEN'
          WHEN '02' THEN 'USD'
          END MONEDA,
          (SELECT desc_clave_cuenta_1 FROM CE_CUADRATURA WHERE COD_CUADRATURA='556' ) CLA_CUE_1,
          (SELECT desc_cuenta_1 FROM CE_CUADRATURA WHERE cod_cuadratura='556') CUE_1,
          ' ' IMPUESTO ,
          TO_CHAR(ABS(CAB.IM_TOTAL),'999999990.00') IMPORTE,
          'CR'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') ASIG_1,
          SUBSTR('RT'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY')||'/'||(SELECT DESC_CUADRATURA FROM ce_cuadratura WHERE COD_CUADRATURA='556'),1,50)   TEX_DET_1,
          (SELECT desc_clave_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='556') CLA_CUE_2,
          (SELECT desc_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='556') CUE_2,
          ' ' CEN_COSTO,
          ' ' IND_IMP,
          'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_DET_2 ,
          ' ' CME
          FROM RCD_RECAUDACION_CAB CAB
          WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
          AND CAB.COD_LOCAL = cCodLocal_in
          AND CAB.EST_RCD = 'C'
          AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                      TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
          AND CAB.COD_RECAU_ANUL_REF IS NULL
          --AND CAB.IND_ANUL IS NULL
          AND CAB.TIPO_RCD=IND_RECAU_CITIBANK_TRJ;

  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;

--CUADRATURA 039 - REVERSA RECAUDACION CITIBANK
PROCEDURE INT_EJECUTA_CUADRATURA_557(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
           SELECT
                  CAB.COD_GRUPO_CIA,
                  CAB.COD_LOCAL,
                  TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA,
                  '039' TIPO_OPERACION ,
                  (SELECT COD_CLASE_DOCUMENTO from CE_CUADRATURA where COD_CUADRATURA='557')  Clase_Doc,
                  TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_DOC,
                  TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_CON,
                  'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') REFERENCIA,
                  'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_CAB,
                  CASE CAB.TIP_MONEDA
                  WHEN '01' THEN 'PEN'
                  WHEN '02' THEN 'USD'
                  END MONEDA,
                  (SELECT desc_clave_cuenta_1 FROM CE_CUADRATURA WHERE COD_CUADRATURA='557' ) CLA_CUE_1,
                  (SELECT desc_cuenta_1 FROM CE_CUADRATURA WHERE cod_cuadratura='557') CUE_1,
                  ' ' IMPUESTO ,
                  TO_CHAR(ABS(CAB.IM_TOTAL),'999999990.00') IMPORTE,
                  'CR'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') ASIG_1,
                  SUBSTR('RT'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY')||'/'||(SELECT DESC_CUADRATURA FROM ce_cuadratura WHERE COD_CUADRATURA='557'),1,50)   TEX_DET_1,
                  (SELECT desc_clave_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='557') CLA_CUE_2,
                  (SELECT desc_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='557') CUE_2,
                  ' ' CEN_COSTO,
                  ' ' IND_IMP,
                  'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_DET_2 ,
                  ' ' CME
                  FROM RCD_RECAUDACION_CAB CAB
                  WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND CAB.COD_LOCAL = cCodLocal_in
                  AND CAB.EST_RCD = 'C'
                  AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                              TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                  and CAB.cod_autorizacion is not null
                  and CAB.cod_recau_anul_ref is not null
                  AND CAB.TIPO_RCD=IND_RECAU_CITIBANK_TRJ;

  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;



  --CUADRATURA 040 - PAGO RIPLEY
  PROCEDURE INT_EJECUTA_CUADRATURA_558(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
          SELECT
                CAB.COD_GRUPO_CIA,
                CAB.COD_LOCAL,
                TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA,
                '040' TIPO_OPERACION ,
                (SELECT COD_CLASE_DOCUMENTO from CE_CUADRATURA where COD_CUADRATURA='558')  Clase_Doc,
                TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_DOC,
                TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_CON,
                'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') REFERENCIA,
                'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_CAB,
                CASE CAB.TIP_MONEDA
                WHEN '01' THEN 'PEN'
                WHEN '02' THEN 'USD'
                END MONEDA,
                (SELECT desc_clave_cuenta_1 FROM CE_CUADRATURA WHERE COD_CUADRATURA='558' ) CLA_CUE_1,
                (SELECT desc_cuenta_1 FROM CE_CUADRATURA WHERE cod_cuadratura='558') CUE_1,
                ' ' IMPUESTO ,
                TO_CHAR(ABS(CAB.IM_TOTAL),'999999990.00') IMPORTE,
                'RP'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') ASIG_1,
                SUBSTR('RT'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY')||'/'||(SELECT DESC_CUADRATURA FROM ce_cuadratura WHERE COD_CUADRATURA='558'),1,50)   TEX_DET_1,
                (SELECT desc_clave_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='558') CLA_CUE_2,
                (SELECT desc_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='558') CUE_2,
                ' ' CEN_COSTO,
                ' ' IND_IMP,
                'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_DET_2 ,
                ' ' CME
                FROM RCD_RECAUDACION_CAB CAB
                WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                AND CAB.COD_LOCAL = cCodLocal_in
                AND CAB.EST_RCD = 'C'
                AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                            TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                AND CAB.COD_RECAU_ANUL_REF IS NULL
                --AND CAB.IND_ANUL IS NULL
                AND CAB.TIPO_RCD=IND_RECAU_RIPLEY;
  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;



--CUADRATURA 039 - ANULA PAGO RIPLEY
PROCEDURE INT_EJECUTA_CUADRATURA_559(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
           SELECT
                  CAB.COD_GRUPO_CIA,
                  CAB.COD_LOCAL,
                  TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA,
                  '039' TIPO_OPERACION ,
                  (SELECT COD_CLASE_DOCUMENTO from CE_CUADRATURA where COD_CUADRATURA='559')  Clase_Doc,
                  TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_DOC,
                  TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_CON,
                  'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') REFERENCIA,
                  'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_CAB,
                  CASE CAB.TIP_MONEDA
                  WHEN '01' THEN 'PEN'
                  WHEN '02' THEN 'USD'
                  END MONEDA,
                  (SELECT desc_clave_cuenta_1 FROM CE_CUADRATURA WHERE COD_CUADRATURA='559' ) CLA_CUE_1,
                  (SELECT desc_cuenta_1 FROM CE_CUADRATURA WHERE cod_cuadratura='559') CUE_1,
                  ' ' IMPUESTO ,
                  TO_CHAR(ABS(CAB.IM_TOTAL),'999999990.00') IMPORTE,
                  'RP'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') ASIG_1,
                  SUBSTR('RT'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY')||'/'||(SELECT DESC_CUADRATURA FROM ce_cuadratura WHERE COD_CUADRATURA='559'),1,50)   TEX_DET_1,
                  (SELECT desc_clave_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='559') CLA_CUE_2,
                  (SELECT desc_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='559') CUE_2,
                  ' ' CEN_COSTO,
                  ' ' IND_IMP,
                  'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_DET_2 ,
                  ' ' CME
                  FROM RCD_RECAUDACION_CAB CAB
                  WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND CAB.COD_LOCAL = cCodLocal_in
                  AND CAB.EST_RCD = 'C'
                  AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                              TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                  and CAB.cod_autorizacion is not null
                  and CAB.cod_recau_anul_ref is not null
                  AND CAB.TIPO_RCD=IND_RECAU_RIPLEY;

  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;



  --CUADRATURA 040 - PAGO PRESTAMOS CITIBANK
  PROCEDURE INT_EJECUTA_CUADRATURA_560(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
          SELECT
                CAB.COD_GRUPO_CIA,
                CAB.COD_LOCAL,
                TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA,
                '040' TIPO_OPERACION ,
                (SELECT COD_CLASE_DOCUMENTO from CE_CUADRATURA where COD_CUADRATURA='560')  Clase_Doc,
                TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_DOC,
                TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_CON,
                'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') REFERENCIA,
                'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_CAB,
                CASE CAB.TIP_MONEDA
                WHEN '01' THEN 'PEN'
                WHEN '02' THEN 'USD'
                END MONEDA,
                (SELECT desc_clave_cuenta_1 FROM CE_CUADRATURA WHERE COD_CUADRATURA='560' ) CLA_CUE_1,
                (SELECT desc_cuenta_1 FROM CE_CUADRATURA WHERE cod_cuadratura='560') CUE_1,
                ' ' IMPUESTO ,
                TO_CHAR(ABS(CAB.IM_TOTAL),'999999990.00') IMPORTE,
                'CP'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') ASIG_1,
                SUBSTR('RT'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY')||'/'||(SELECT DESC_CUADRATURA FROM ce_cuadratura WHERE COD_CUADRATURA='560'),1,50)   TEX_DET_1,
                (SELECT desc_clave_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='560') CLA_CUE_2,
                (SELECT desc_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='560') CUE_2,
                ' ' CEN_COSTO,
                ' ' IND_IMP,
                'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_DET_2 ,
                ' ' CME
                FROM RCD_RECAUDACION_CAB CAB
                WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                AND CAB.COD_LOCAL = cCodLocal_in
                AND CAB.EST_RCD = 'C'
                AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                            TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                AND CAB.COD_RECAU_ANUL_REF IS NULL
                --AND CAB.IND_ANUL IS NULL
                AND CAB.TIPO_RCD=IND_RECAU_CITIBANK_PRES;

  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;


--CUADRATURA 039 - ANULA PAGO PRESTAMOS CITIBANK
PROCEDURE INT_EJECUTA_CUADRATURA_561(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
           SELECT
                  CAB.COD_GRUPO_CIA,
                  CAB.COD_LOCAL,
                  TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA,
                  '039' TIPO_OPERACION ,
                  (SELECT COD_CLASE_DOCUMENTO from CE_CUADRATURA where COD_CUADRATURA='561')  Clase_Doc,
                  TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_DOC,
                  TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_CON,
                  'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') REFERENCIA,
                  'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_CAB,
                  CASE CAB.TIP_MONEDA
                  WHEN '01' THEN 'PEN'
                  WHEN '02' THEN 'USD'
                  END MONEDA,
                  (SELECT desc_clave_cuenta_1 FROM CE_CUADRATURA WHERE COD_CUADRATURA='561' ) CLA_CUE_1,
                  (SELECT desc_cuenta_1 FROM CE_CUADRATURA WHERE cod_cuadratura='561') CUE_1,
                  ' ' IMPUESTO ,
                  TO_CHAR(ABS(CAB.IM_TOTAL),'999999990.00') IMPORTE,
                  'CP'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') ASIG_1,
                  SUBSTR('RT'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY')||'/'||(SELECT DESC_CUADRATURA FROM ce_cuadratura WHERE COD_CUADRATURA='561'),1,50)   TEX_DET_1,
                  (SELECT desc_clave_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='561') CLA_CUE_2,
                  (SELECT desc_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='561') CUE_2,
                  ' ' CEN_COSTO,
                  ' ' IND_IMP,
                  'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_DET_2 ,
                  ' ' CME
                  FROM RCD_RECAUDACION_CAB CAB
                  WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND CAB.COD_LOCAL = cCodLocal_in
                  AND CAB.EST_RCD = 'C'
                  AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                              TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                  and CAB.cod_autorizacion is not null
                  and CAB.cod_recau_anul_ref is not null
                  AND CAB.TIPO_RCD=IND_RECAU_CITIBANK_PRES;

  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;


--CUADRATURA 040 - PAGO DE CMR
 PROCEDURE INT_EJECUTA_CUADRATURA_562(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
          SELECT
                CAB.COD_GRUPO_CIA,
                CAB.COD_LOCAL,
                TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA,
                '040' TIPO_OPERACION ,
                (SELECT COD_CLASE_DOCUMENTO from CE_CUADRATURA where COD_CUADRATURA='562')  Clase_Doc,
                TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_DOC,
                TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_CON,
                'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') REFERENCIA,
                'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_CAB,
                CASE CAB.TIP_MONEDA
                WHEN '01' THEN 'PEN'
                WHEN '02' THEN 'USD'
                END MONEDA,
                (SELECT desc_clave_cuenta_1 FROM CE_CUADRATURA WHERE COD_CUADRATURA='562' ) CLA_CUE_1,
                (SELECT desc_cuenta_1 FROM CE_CUADRATURA WHERE cod_cuadratura='562') CUE_1,
                ' ' IMPUESTO ,
                TO_CHAR(ABS(CAB.IM_TOTAL),'999999990.00') IMPORTE,
                'CM'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') ASIG_1,
                SUBSTR('RT'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY')||'/'||(SELECT DESC_CUADRATURA FROM ce_cuadratura WHERE COD_CUADRATURA='562'),1,50)   TEX_DET_1,
                (SELECT desc_clave_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='562') CLA_CUE_2,
                (SELECT desc_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='562') CUE_2,
                ' ' CEN_COSTO,
                ' ' IND_IMP,
                'IG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_DET_2 ,
                ' ' CME
                FROM RCD_RECAUDACION_CAB CAB
                WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                AND CAB.COD_LOCAL = cCodLocal_in
                AND CAB.EST_RCD = 'C'
                AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                            TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                AND CAB.COD_RECAU_ANUL_REF IS NULL
                --AND CAB.IND_ANUL IS NULL
                AND CAB.TIPO_RCD=IND_RECAU_CMR;

  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;


--CUADRATURA 039 - REVERSA DE CMR
PROCEDURE INT_EJECUTA_CUADRATURA_563(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       vFecCierreDia_in IN CHAR)
  AS
    v_cSecIntCe INT_CAJA_ELECTRONICA.SEC_INT_CE%TYPE;

    CURSOR movCuadratura IS
           SELECT
                 CAB.COD_GRUPO_CIA,
                  CAB.COD_LOCAL,
                  TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA,
                  '039' TIPO_OPERACION ,
                  (SELECT COD_CLASE_DOCUMENTO from CE_CUADRATURA where COD_CUADRATURA='563')  Clase_Doc,
                  TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_DOC,
                  TO_CHAR(CAB.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') FECHA_CON,
                  'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') REFERENCIA,
                  'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_CAB,
                  CASE CAB.TIP_MONEDA
                  WHEN '01' THEN 'PEN'
                  WHEN '02' THEN 'USD'
                  END MONEDA,
                  (SELECT desc_clave_cuenta_1 FROM CE_CUADRATURA WHERE COD_CUADRATURA='563' ) CLA_CUE_1,
                  (SELECT desc_cuenta_1 FROM CE_CUADRATURA WHERE cod_cuadratura='563') CUE_1,
                  ' ' IMPUESTO ,
                  TO_CHAR(ABS(CAB.IM_TOTAL),'999999990.00') IMPORTE,
                  'CM'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') ASIG_1,
                  SUBSTR('RT'||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY')||'/'||(SELECT DESC_CUADRATURA FROM ce_cuadratura WHERE COD_CUADRATURA='563'),1,50)   TEX_DET_1,
                  (SELECT desc_clave_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='563') CLA_CUE_2,
                  (SELECT desc_cuenta_2 FROM ce_cuadratura WHERE cod_cuadratura='563') CUE_2,
                  ' ' CEN_COSTO,
                  ' ' IND_IMP,
                  'EG' ||'-'||CAB.COD_LOCAL||'-'||to_char(CAB.FEC_CREA_RECAU_PAGO,'DDMMYYYY') TEX_DET_2 ,
                  ' ' CME
                  FROM RCD_RECAUDACION_CAB CAB
                  WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND CAB.COD_LOCAL = cCodLocal_in
                  AND CAB.EST_RCD = 'C'
                  AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFecCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                              TO_DATE(vFecCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                  and CAB.cod_autorizacion is not null
                  and CAB.cod_recau_anul_ref is not null
                  AND CAB.TIPO_RCD=IND_RECAU_CMR;

  BEGIN
       FOR movCuadratura_rec IN movCuadratura
	     LOOP
           v_cSecIntCe:= CE_GET_SECUENCIAL_INT(cCodGrupoCia_in, cCodLocal_in, vFecCierreDia_in);

           INT_GRABA_INTERFACE_CE(movCuadratura_rec.COD_GRUPO_CIA,
                                  movCuadratura_rec.COD_LOCAL,
                                  movCuadratura_rec.FECHA,
                                  movCuadratura_rec.TIPO_OPERACION,
                                  v_cSecIntCe,
                                  movCuadratura_rec.CLASE_DOC,
                                  movCuadratura_rec.FECHA_DOC,
                                  movCuadratura_rec.FECHA_CON,
                                  movCuadratura_rec.REFERENCIA,
                                  movCuadratura_rec.TEX_CAB,
                                  movCuadratura_rec.MONEDA,
                                  movCuadratura_rec.CLA_CUE_1,
                                  movCuadratura_rec.CUE_1,
                                  movCuadratura_rec.IMPUESTO,
                                  movCuadratura_rec.IMPORTE,
                                  movCuadratura_rec.ASIG_1,
                                  movCuadratura_rec.TEX_DET_1,
                                  movCuadratura_rec.CLA_CUE_2,
                                  movCuadratura_rec.CUE_2,
                                  movCuadratura_rec.CEN_COSTO,
                                  movCuadratura_rec.IND_IMP,
                                  movCuadratura_rec.TEX_DET_2,
                                  C_C_USU_CREA_INT_CE,
                                  movCuadratura_rec.CME);

	     END LOOP;
  END;



--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
--             FIN DE CUADRATURAS DE RECAUDACIONES                         *-*-*-*-
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-


end PTOVENTA_INT_CE;
/

