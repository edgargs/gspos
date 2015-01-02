--------------------------------------------------------
--  DDL for Package PTOVENTA_INT_CE
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_INT_CE" is

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
